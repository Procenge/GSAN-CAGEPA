
package gcom.gui.atendimentopublico.ligacaoagua;

import gcom.atendimentopublico.ligacaoagua.CorteTipo;
import gcom.atendimentopublico.ligacaoagua.FiltroCorteTipo;
import gcom.atendimentopublico.ligacaoagua.FiltroMotivoCorte;
import gcom.atendimentopublico.ligacaoagua.MotivoCorte;
import gcom.atendimentopublico.ordemservico.FiltroServicoNaoCobrancaMotivo;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.consumo.LigacaoTipo;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action responsável pela pre-exibição da pagina de efetuar corte de ligação de água com a retirada
 * do hidrometro
 * 
 * @author Eduardo Oliveira
 * @date 14/04/2014
 */
public class ExibirEfetuarCorteLigacaoAguaComRetiradaHidrometroAction
				extends GcomAction {

	/**
	 * [UC0355] Efetuar Corte de Ligação de Água
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return forward
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("efetuarCorteLigacaoAguaComRetiradaHidrometroAction");
		HttpSession sessao = httpServletRequest.getSession(false);
		EfetuarCorteLigacaoAguaComRetiradaHidrometroActionForm form = (EfetuarCorteLigacaoAguaComRetiradaHidrometroActionForm) actionForm;

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		// Pesquisar Funcionario ENTER
		if((form.getIdFuncionario() != null && !form.getIdFuncionario().equals(""))
						&& (form.getDescricaoFuncionario() == null || form.getDescricaoFuncionario().equals(""))){

			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();

			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, form.getIdFuncionario()));

			Collection colecaoFuncionario = getFachada().pesquisar(filtroFuncionario, Funcionario.class.getName());

			if(colecaoFuncionario == null || colecaoFuncionario.isEmpty()){

				form.setIdFuncionario("");
				form.setDescricaoFuncionario("FUNCIONÁRIO INEXISTENTE");

				httpServletRequest.setAttribute("corFuncionario", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idFuncionario");

			}else{

				Funcionario funcionario = (Funcionario) Util.retonarObjetoDeColecao(colecaoFuncionario);

				form.setIdFuncionario(funcionario.getId().toString());
				form.setDescricaoFuncionario(funcionario.getNome());

				httpServletRequest.setAttribute("nomeCampo", "botaoAdicionar");

			}
		}

		// Pesquisar Funcionário POPUP
		String pesquisarFuncionario = httpServletRequest.getParameter("pesquisarFuncionario");

		if(pesquisarFuncionario != null && !pesquisarFuncionario.equalsIgnoreCase("")){
			retorno = actionMapping.findForward("pesquisarFuncionario");
		}

		// Veio de Encerrar OS
		Boolean veioEncerrarOS = null;
		if(httpServletRequest.getAttribute("veioEncerrarOS") != null){
			veioEncerrarOS = Boolean.TRUE;
		}else{
			if(form.getVeioEncerrarOS() != null && !form.getVeioEncerrarOS().equals("")){

				if(form.getVeioEncerrarOS().toLowerCase().equals("true")){
					veioEncerrarOS = Boolean.TRUE;
				}else{
					veioEncerrarOS = Boolean.FALSE;
				}
			}else{
				veioEncerrarOS = Boolean.FALSE;
			}
		}

		// Seta Coleções
		getMotivoCorteCollection(sessao);
		getTipoCorteCollection(sessao);
		getMotivoNaoCobrancaCollection(sessao);

		String idOrdemServico = null;
		if(httpServletRequest.getAttribute("veioEncerrarOS") != null){
			idOrdemServico = (String) httpServletRequest.getAttribute("veioEncerrarOS");
			form.setDataCorte((String) httpServletRequest.getAttribute("dataEncerramento"));
			sessao.setAttribute("caminhoRetornoIntegracaoComercial", httpServletRequest.getAttribute("caminhoRetornoIntegracaoComercial"));

		}else{
			if(form.getIdOrdemServico() != null){
				idOrdemServico = form.getIdOrdemServico();
			}
		}

		if(httpServletRequest.getAttribute("semMenu") != null){
			sessao.setAttribute("semMenu", "SIM");
		}else{
			sessao.removeAttribute("semMenu");
		}

		// Testa OS
		if(idOrdemServico != null && !idOrdemServico.trim().equals("")){
			OrdemServico ordemServico = this.getFachada().recuperaOSPorId(Integer.valueOf(idOrdemServico));
			if(ordemServico != null){

				sessao.setAttribute("ordemServico", ordemServico);

				// Valida Exibição de Corte de Ligação de Água
				this.getFachada().validarExibirCorteLigacaoAguaComRetiradaHidrometro(ordemServico, veioEncerrarOS);
				form.setVeioEncerrarOS("" + veioEncerrarOS);

				// OS
				form.setIdOrdemServico(ordemServico.getId() + "");
				form.setNomeOrdemServico(ordemServico.getServicoTipo().getDescricao());

				// Preencher dados do imovel
				this.preencherDadosImovel(form, ordemServico);

				// Preencher dados do Corte da Ligação
				this.pesquisarDadosCorteLigacao(sessao, form, ordemServico);

				// Preencher dados da Geração
				// Tipo Débito
				if(ordemServico.getServicoTipo().getDebitoTipo() != null){
					form.setIdTipoDebito(ordemServico.getServicoTipo().getDebitoTipo().getId() + "");
					form.setDescricaoTipoDebito(ordemServico.getServicoTipo().getDebitoTipo().getDescricao() + "");
				}else{
					form.setIdTipoDebito("");
					form.setDescricaoTipoDebito("");
				}

				// [FS0013] - Alteração de Valor
				this.permitirAlteracaoValor(ordemServico.getServicoTipo(), form);

				String calculaValores = httpServletRequest.getParameter("calculaValores");

				BigDecimal valorDebito = new BigDecimal(0);
				SistemaParametro sistemaParametro = this.getFachada().pesquisarParametrosDoSistema();
				Integer qtdeParcelas = null;

				if(calculaValores != null && calculaValores.equals("S")){

					// [UC0186] - Calcular Prestação
					BigDecimal taxaJurosFinanciamento = null;
					qtdeParcelas = Integer.valueOf(form.getQuantidadeParcelas());

					if(ordemServico.getServicoTipo().getIndicadorCobrarJuros() == ConstantesSistema.SIM.shortValue()
									&& qtdeParcelas.intValue() != 1){

						taxaJurosFinanciamento = sistemaParametro.getPercentualTaxaJurosFinanciamento();
					}else{
						taxaJurosFinanciamento = new BigDecimal(0);
					}

					BigDecimal valorPrestacao = null;
					if(taxaJurosFinanciamento != null){
						valorDebito = new BigDecimal(form.getValorDebito().replace(",", "."));
						String percentualCobranca = form.getPercentualCobranca();
						if(percentualCobranca.equals("70")){
							valorDebito = valorDebito.multiply(new BigDecimal(0.7));
						}else if(percentualCobranca.equals("50")){
							valorDebito = valorDebito.multiply(new BigDecimal(0.5));
						}
						valorPrestacao = this.getFachada().calcularPrestacao(taxaJurosFinanciamento, qtdeParcelas, valorDebito,
										new BigDecimal("0.00"));
						valorPrestacao.setScale(2, BigDecimal.ROUND_HALF_UP);
					}

					if(valorPrestacao != null){
						String valorPrestacaoComVirgula = Util.formataBigDecimal(valorPrestacao, 2, true);
						form.setValorParcelas(valorPrestacaoComVirgula);
					}else{
						form.setValorParcelas("0,00");
					}

				}else{
					valorDebito = this.getFachada().obterValorDebito(ordemServico.getServicoTipo().getId(),
									ordemServico.getImovel().getId(), Short.valueOf(LigacaoTipo.LIGACAO_AGUA + ""));
					if(valorDebito != null){
						String valorDebitoComVirgula = valorDebito + "";
						form.setValorDebito(valorDebitoComVirgula.replace(".", ","));
					}else{
						form.setValorDebito("0,00");
					}
				}

				form.setQtdeMaxParcelas(this.getFachada().obterQuantidadeParcelasMaxima().toString());
				if(ordemServico.getServicoNaoCobrancaMotivo() != null){
					form.setMotivoNaoCobranca(ordemServico.getServicoNaoCobrancaMotivo().getId().toString());
				}
				if(ordemServico.getServicoNaoCobrancaMotivo() != null){
					if(ordemServico.getPercentualCobranca() != null){
						form.setPercentualCobranca(ordemServico.getPercentualCobranca().toString());
					}else{
						form.setPercentualCobranca("");
					}
				}
				// Verificar permissão especial
				boolean temPermissaoMotivoNaoCobranca = this.getFachada().verificarPermissaoInformarMotivoNaoCobranca(usuarioLogado);

				if(temPermissaoMotivoNaoCobranca){
					httpServletRequest.setAttribute("permissaoMotivoNaoCobranca", temPermissaoMotivoNaoCobranca);
				}else{
					form.setPercentualCobranca("100");
					form.setQuantidadeParcelas("1");
					form.setValorParcelas(Util.formataBigDecimal(valorDebito, 2, true));
				}

				boolean temPermissaoPercentualCobrancaExcedente = this.getFachada().verificarPermissaoInformarPercentualCobrancaExcedente(
								usuarioLogado);
				boolean temPermissaoQuantidadeParcelasExcedente = this.getFachada().verificarPermissaoInformarQuantidadeParcelasExcedentes(
								usuarioLogado);

				if(temPermissaoPercentualCobrancaExcedente){

					httpServletRequest.setAttribute("permissaoPercentualCobrancaExcedente", temPermissaoPercentualCobrancaExcedente);
					httpServletRequest.setAttribute("colecaoPercentualCobranca", this.getFachada().obterPercentuaisCobranca());

				}else{

					form.setPercentualCobranca("100");
				}

				if(!temPermissaoQuantidadeParcelasExcedente){

					form.setQuantidadeParcelas("1");
				}else{

					httpServletRequest.setAttribute("temPermissaoQuantidadeParcelasExcedente", temPermissaoQuantidadeParcelasExcedente);
				}

				if(!temPermissaoPercentualCobrancaExcedente && !temPermissaoQuantidadeParcelasExcedente){
					form.setValorParcelas(Util.formataBigDecimal(valorDebito, 2, true));
				}

				sessao.setAttribute("osEncontrada", "true");
			}else{
				sessao.removeAttribute("osEncontrada");
				sessao.removeAttribute("ordemServico");
				form.setNomeOrdemServico("Ordem de Serviço inexistente");
				form.setIdOrdemServico("");
			}

		}else{
			httpServletRequest.setAttribute("nomeCampo", "idOrdemServico");
			form.reset();
		}

		// Parâmetro que indica obrigatoriedade do campo Funcionário
		String indicadorObrigatoriedadeFuncionario = null;
		try{
			indicadorObrigatoriedadeFuncionario = ParametroCadastro.P_INDICADOR_OBRIGATORIEDADE_FUNCIONARIO.executar();
		}catch(ControladorException e){
			e.printStackTrace();
		}
		httpServletRequest.setAttribute("indicadorObrigatoriedadeFuncionario", indicadorObrigatoriedadeFuncionario);

		return retorno;
	}

	private void permitirAlteracaoValor(ServicoTipo servicoTipo, EfetuarCorteLigacaoAguaComRetiradaHidrometroActionForm form){

		if(servicoTipo.getIndicadorPermiteAlterarValor() == ConstantesSistema.INDICADOR_USO_ATIVO.shortValue()){

			form.setAlteracaoValor("OK");
		}else{
			form.setAlteracaoValor("");
		}

	}

	private void pesquisarDadosCorteLigacao(HttpSession sessao, EfetuarCorteLigacaoAguaComRetiradaHidrometroActionForm form,
					OrdemServico ordemServico){

		// Data Execução
		if(ordemServico.getDataExecucao() != null && !ordemServico.getDataExecucao().equals("")){
			form.setDataCorte(Util.formatarData(ordemServico.getDataExecucao()));
		}

		if(ordemServico.getImovel().getLigacaoAgua().getMotivoCorte() != null){
			form.setMotivoCorte("" + ordemServico.getImovel().getLigacaoAgua().getMotivoCorte().getId());
		}
		// Tipo do Corte
		if(ordemServico.getImovel().getLigacaoAgua().getCorteTipo() != null){
			form.setTipoCorte("" + ordemServico.getImovel().getLigacaoAgua().getCorteTipo().getId());
		}
		// Leitura do Corte
		HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = ordemServico.getImovel().getLigacaoAgua()
						.getHidrometroInstalacaoHistorico();
		if(hidrometroInstalacaoHistorico != null && hidrometroInstalacaoHistorico.getNumeroLeituraCorte() != null){
			form.setNumLeituraCorte("" + hidrometroInstalacaoHistorico.getNumeroLeituraCorte());
		}
		// Número do Selo do Corte
		if(ordemServico.getImovel().getLigacaoAgua().getNumeroSeloCorte() != null){
			form.setNumSeloCorte("" + ordemServico.getImovel().getLigacaoAgua().getNumeroSeloCorte());
		}
	}


	private void preencherDadosImovel(EfetuarCorteLigacaoAguaComRetiradaHidrometroActionForm form, OrdemServico ordemServico){

		Imovel imovel = ordemServico.getImovel();

		// Matricula Imóvel
		form.setMatriculaImovel(imovel.getId().toString());
		// Inscrição Imóvel
		String inscricaoImovel = this.getFachada().pesquisarInscricaoImovel(imovel.getId(), true);
		form.setInscricaoImovel(inscricaoImovel);
		// Situação da Ligação de Agua
		String situacaoLigacaoAgua = imovel.getLigacaoAguaSituacao().getDescricao();
		form.setSituacaoLigacaoAgua(situacaoLigacaoAgua);
		// Situação da Ligação de Esgoto
		String situacaoLigacaoEsgoto = imovel.getLigacaoEsgotoSituacao().getDescricao();
		form.setSituacaoLigacaoEsgoto(situacaoLigacaoEsgoto);
		// Cliente
		this.pesquisarCliente(form, ordemServico);
	}


	private void getMotivoNaoCobrancaCollection(HttpSession sessao){

		// Filtra Motivo da Não Cobrança
		FiltroServicoNaoCobrancaMotivo filtroServicoNaoCobrancaMotivo = new FiltroServicoNaoCobrancaMotivo();
		filtroServicoNaoCobrancaMotivo.setCampoOrderBy(FiltroServicoNaoCobrancaMotivo.DESCRICAO);

		Collection colecaoServicoNaoCobrancaMotivo = this.getFachada().pesquisar(filtroServicoNaoCobrancaMotivo,
						ServicoNaoCobrancaMotivo.class.getName());
		if(colecaoServicoNaoCobrancaMotivo != null && !colecaoServicoNaoCobrancaMotivo.isEmpty()){
			sessao.setAttribute("colecaoNaoCobranca", colecaoServicoNaoCobrancaMotivo);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Motivo Não Cobrança");
		}
	}


	private void pesquisarCliente(EfetuarCorteLigacaoAguaComRetiradaHidrometroActionForm form, OrdemServico ordemServico){

		// Filtro para carregar o Cliente
		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, ordemServico.getImovel().getId()));

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO, ClienteRelacaoTipo.USUARIO));
		filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");

		Collection colecaoClienteImovel = this.getFachada().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());
		if(colecaoClienteImovel != null && !colecaoClienteImovel.isEmpty()){

			ClienteImovel clienteImovel = (ClienteImovel) colecaoClienteImovel.iterator().next();
			Cliente cliente = clienteImovel.getCliente();

			String documento = "";
			if(cliente.getCpf() != null && !cliente.getCpf().equals("")){
				documento = cliente.getCpfFormatado();
			}else{
				documento = cliente.getCnpjFormatado();
			}
			// Cliente Nome/CPF-CNPJ
			form.setClienteUsuario(cliente.getNome());
			form.setCpfCnpjCliente(documento);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Cliente");
		}
	}

	private void getMotivoCorteCollection(HttpSession sessao){

		// Filtro para o campo Motivo do Corte
		FiltroMotivoCorte filtroMotivoCorteLigacaoAgua = new FiltroMotivoCorte();
		filtroMotivoCorteLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroMotivoCorte.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroMotivoCorteLigacaoAgua.setCampoOrderBy(FiltroMotivoCorte.DESCRICAO);

		Collection colecaoMotivoCorteLigacaoAgua = this.getFachada().pesquisar(filtroMotivoCorteLigacaoAgua, MotivoCorte.class.getName());
		if(colecaoMotivoCorteLigacaoAgua != null && !colecaoMotivoCorteLigacaoAgua.isEmpty()){
			sessao.setAttribute("colecaoMotivoCorteLigacaoAgua", colecaoMotivoCorteLigacaoAgua);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Motivo do Corte");
		}
	}

	private void getTipoCorteCollection(HttpSession sessao){

		// Filtro para o campo Motivo do Corte
		FiltroCorteTipo filtroTipoCorteLigacaoAgua = new FiltroCorteTipo();
		filtroTipoCorteLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroCorteTipo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroTipoCorteLigacaoAgua.adicionarParametro(new ParametroSimples(FiltroCorteTipo.INDICADOR_CORTE_ADMINISTRATIVO,
						ConstantesSistema.NAO));
		filtroTipoCorteLigacaoAgua.setCampoOrderBy(FiltroCorteTipo.DESCRICAO);

		Collection colecaoTipoCorteLigacaoAgua = this.getFachada().pesquisar(filtroTipoCorteLigacaoAgua, CorteTipo.class.getName());

		if(colecaoTipoCorteLigacaoAgua != null && !colecaoTipoCorteLigacaoAgua.isEmpty()){
			sessao.setAttribute("colecaoTipoCorteLigacaoAgua", colecaoTipoCorteLigacaoAgua);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Tipo do Corte");
		}
	}
}