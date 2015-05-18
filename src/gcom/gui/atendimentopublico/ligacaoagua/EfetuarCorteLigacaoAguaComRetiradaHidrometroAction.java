package gcom.gui.atendimentopublico.ligacaoagua;

import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
import gcom.atendimentopublico.ligacaoagua.*;
import gcom.atendimentopublico.ligacaoagua.bean.DadosEfetuacaoCorteLigacaoAguaHelper;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.hidrometro.FiltroHidrometroInstalacaoHistorico;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ConstantesSistema;
import gcom.util.FachadaException;
import gcom.util.Util;
import gcom.util.filtro.FiltroParametro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que define o processamento da página de efetuar corte de ligação de água
 * 
 * @author Eduardo Oliveira
 * @date 27/04/2014
 */
public class EfetuarCorteLigacaoAguaComRetiradaHidrometroAction
				extends GcomAction {

	/**
	 * Efetuar Corte Ligação Água com Retirada de Hidrometro
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return forward
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		HttpSession sessao = httpServletRequest.getSession(false);

		EfetuarCorteLigacaoAguaComRetiradaHidrometroActionForm corteLigacaoAguaActionForm = (EfetuarCorteLigacaoAguaComRetiradaHidrometroActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// [UC0107] Registrar Transação
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_EFETUAR_CORTE_AGUA_COM_RETIRADA_HIDROMETRO,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_EFETUAR_CORTE_AGUA_COM_RETIRADA_HIDROMETRO);

		IntegracaoComercialHelper integracaoComercialHelper = new IntegracaoComercialHelper();

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// [UC0107] -Fim- Registrar Transação

		if(corteLigacaoAguaActionForm.getIdOrdemServico() != null && !corteLigacaoAguaActionForm.getIdOrdemServico().equals("")){

			OrdemServico ordemServico = new OrdemServico();
			Imovel imovel = new Imovel();
			LigacaoAgua ligacaoAgua = new LigacaoAgua();
			HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = null;
			if(sessao.getAttribute("ordemServico") != null){
				ordemServico = (OrdemServico) sessao.getAttribute("ordemServico");
			}else{
				ordemServico = this.getFachada().recuperaOSPorId(Integer.valueOf(corteLigacaoAguaActionForm.getIdOrdemServico()));
				sessao.setAttribute("ordemServico", ordemServico);
			}

			// Imovel
			imovel = ordemServico.getImovel();

			// Motivo do Corte
			String idMotivoCorteStr = corteLigacaoAguaActionForm.getMotivoCorte();

			FiltroMotivoCorte filtroMotivoCorte = new FiltroMotivoCorte();
			filtroMotivoCorte.adicionarParametro(new ParametroSimples(FiltroMotivoCorte.ID, idMotivoCorteStr));

			Collection<MotivoCorte> colecaoMotivoCorte = fachada.pesquisar(filtroMotivoCorte, MotivoCorte.class.getName());

			MotivoCorte motivoCorte = (MotivoCorte) Util.retonarObjetoDeColecao(colecaoMotivoCorte);

			Integer indicadorCortePedido = motivoCorte.getIndicadorCortePedido();

			// Situação da Ligação de Água
			Integer idLigacaoAguaSituacao = null;

			if(indicadorCortePedido != null && indicadorCortePedido.equals(Integer.valueOf(ConstantesSistema.SIM))){
				idLigacaoAguaSituacao = LigacaoAguaSituacao.CORTADO_PEDIDO;
			}else{
				idLigacaoAguaSituacao = LigacaoAguaSituacao.CORTADO;
			}

			LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
			ligacaoAguaSituacao.setId(idLigacaoAguaSituacao);
			imovel.setLigacaoAguaSituacao(ligacaoAguaSituacao);

			imovel.setUltimaAlteracao(new Date());

			// Ligação Água
			ligacaoAgua.setId(imovel.getId());

			Date dataCorte = null;

			if(corteLigacaoAguaActionForm.getDataCorte() != null && corteLigacaoAguaActionForm.getDataCorte() != ""){
				dataCorte = Util.converteStringParaDate(corteLigacaoAguaActionForm.getDataCorte(), false);
			}else{
				throw new ActionServletException("atencao.required", null, " Data do Corte");
			}

			ligacaoAgua.setDataCorte(dataCorte);

			if(corteLigacaoAguaActionForm.getNumSeloCorte() != null && !corteLigacaoAguaActionForm.getNumSeloCorte().equals("")){
				ligacaoAgua.setNumeroSeloCorte(Integer.valueOf(corteLigacaoAguaActionForm.getNumSeloCorte()));
			}else{
				ligacaoAgua.setNumeroSeloCorte(null);
			}

			CorteTipo corteTipo = new CorteTipo();
			corteTipo.setId(Integer.valueOf(corteLigacaoAguaActionForm.getTipoCorte()));
			ligacaoAgua.setCorteTipo(corteTipo);

			ligacaoAgua.setMotivoCorte(motivoCorte);
			ligacaoAgua.setUltimaAlteracao(new Date());

			String idFuncionario = corteLigacaoAguaActionForm.getIdFuncionario();

			if(idFuncionario != null && !idFuncionario.equals("")){
				Funcionario func = new Funcionario();
				func.setId(new Integer(idFuncionario));
				ligacaoAgua.setFuncionarioCorte(func);
			}

			if(ligacaoAgua.getMotivoCorte() != null
							&& ligacaoAgua.getMotivoCorte().getIndicadorCorteFaltaPagamento().equals(new Integer("1"))){
				if(ligacaoAgua.getNumeroCorteFaltaPagamento() == null){
					ligacaoAgua.setNumeroCorteFaltaPagamento(1);
				}else{
					ligacaoAgua.setNumeroCorteFaltaPagamento(ligacaoAgua.getNumeroCorteFaltaPagamento() + 1);
				}
			}else if(ligacaoAgua.getMotivoCorte() != null
							&& ligacaoAgua.getMotivoCorte().getIndicadorCorteInfracao().equals(new Integer("1"))){
				if(ligacaoAgua.getNumeroCorteInfracao() == null){
					ligacaoAgua.setNumeroCorteInfracao(1);
				}else{
					ligacaoAgua.setNumeroCorteInfracao(ligacaoAgua.getNumeroCorteInfracao() + 1);
				}
			}else if(ligacaoAgua.getMotivoCorte() != null
							&& ligacaoAgua.getMotivoCorte().getIndicadorCortePedido().equals(new Integer("1"))){
				if(ligacaoAgua.getNumeroCortePedido() == null){
					ligacaoAgua.setNumeroCortePedido(1);
				}else{
					ligacaoAgua.setNumeroCortePedido(ligacaoAgua.getNumeroCortePedido() + 1);
				}
			}

			// Hidrometro Instalação Histórico
			if(imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null){

				// Faz uma busca do historico de instalacao do hidrometro para mostrar na pagina
				FiltroHidrometroInstalacaoHistorico filtroHidrometroInstalacaoHistorico = new FiltroHidrometroInstalacaoHistorico();

				Integer idImovel = imovel.getId();

				Integer idLigacaoAgua = ligacaoAgua.getId();

				filtroHidrometroInstalacaoHistorico.adicionarParametro(new ParametroSimples(FiltroHidrometroInstalacaoHistorico.IMOVEL_ID,
								idImovel, FiltroParametro.CONECTOR_OR));
				filtroHidrometroInstalacaoHistorico.adicionarParametro(new ParametroSimples(
								FiltroHidrometroInstalacaoHistorico.LIGACAO_AGUA_ID, idLigacaoAgua));

				filtroHidrometroInstalacaoHistorico
								.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometroInstalacaoHistorico.MEDICAO_TIPO);
				filtroHidrometroInstalacaoHistorico
								.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometroInstalacaoHistorico.HIDROMETRO_LOCAL_INSTALACAO);
				filtroHidrometroInstalacaoHistorico
								.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometroInstalacaoHistorico.HIDROMETRO_PROTECAO);
				filtroHidrometroInstalacaoHistorico
								.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometroInstalacaoHistorico.HIDROMETRO);

				Collection<HidrometroInstalacaoHistorico> hidrometrosInstalacaoHistorico = fachada.pesquisar(
								filtroHidrometroInstalacaoHistorico, HidrometroInstalacaoHistorico.class.getName());

				hidrometroInstalacaoHistorico = (HidrometroInstalacaoHistorico) Util.retonarObjetoDeColecao(hidrometrosInstalacaoHistorico);

				hidrometroInstalacaoHistorico.setDataRetirada(new Date());

				integracaoComercialHelper.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);
				// Validar Número de Leitura do Corte / Número do Selo de Corte
				if(corteLigacaoAguaActionForm.getNumLeituraCorte() != null && !corteLigacaoAguaActionForm.getNumLeituraCorte().equals("")){
					hidrometroInstalacaoHistorico.setNumeroLeituraCorte(Integer.valueOf(corteLigacaoAguaActionForm.getNumLeituraCorte()));
				}else{
					hidrometroInstalacaoHistorico.setNumeroLeituraCorte(null);
				}
				hidrometroInstalacaoHistorico.setUltimaAlteracao(new Date());
			}
			ligacaoAgua.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);
			imovel.setLigacaoAgua(ligacaoAgua);
			ordemServico.setOperacaoEfetuada(operacaoEfetuada);
			ordemServico.setIndicadorComercialAtualizado(ConstantesSistema.SIM);
			ServicoNaoCobrancaMotivo servicoNaoCobrancaMotivo = null;
			if(corteLigacaoAguaActionForm.getMotivoNaoCobranca() != null
							&& !corteLigacaoAguaActionForm.getMotivoNaoCobranca().equals(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				servicoNaoCobrancaMotivo = new ServicoNaoCobrancaMotivo();
				servicoNaoCobrancaMotivo.setId(Integer.valueOf(corteLigacaoAguaActionForm.getMotivoNaoCobranca()));
			}
			ordemServico.setServicoNaoCobrancaMotivo(servicoNaoCobrancaMotivo);

			BigDecimal valorAtual = new BigDecimal(0);
			if(corteLigacaoAguaActionForm.getValorDebito() != null){
				String valorDebito = corteLigacaoAguaActionForm.getValorDebito().toString().replace(".", "");

				valorDebito = valorDebito.replace(",", ".");

				valorAtual = new BigDecimal(valorDebito);

				ordemServico.setValorAtual(valorAtual);
			}

			if(corteLigacaoAguaActionForm.getPercentualCobranca() != null && !corteLigacaoAguaActionForm.getPercentualCobranca().equals("")){
				ordemServico.setPercentualCobranca(new BigDecimal(corteLigacaoAguaActionForm.getPercentualCobranca()));
			}
			ordemServico.setUltimaAlteracao(new Date());

			// Preenche Helper
			DadosEfetuacaoCorteLigacaoAguaHelper dadosHelper = new DadosEfetuacaoCorteLigacaoAguaHelper();
			dadosHelper.setImovel(imovel);
			dadosHelper.setLigacaoAgua(ligacaoAgua);
			dadosHelper.setHidrometroInstalacaoHistorico(hidrometroInstalacaoHistorico);
			dadosHelper.setOrdemServico(ordemServico);
			if(corteLigacaoAguaActionForm.getVeioEncerrarOS().equalsIgnoreCase("true")){
				dadosHelper.setVeioEncerrarOS(true);
			}else{
				dadosHelper.setVeioEncerrarOS(false);
			}
			if(corteLigacaoAguaActionForm.getQuantidadeParcelas() != null && !corteLigacaoAguaActionForm.getQuantidadeParcelas().equals("")){
				dadosHelper.setQtdeParcelas(Integer.valueOf(corteLigacaoAguaActionForm.getQuantidadeParcelas()).intValue());
			}else{
				dadosHelper.setQtdeParcelas(0);
			}

			integracaoComercialHelper.setDadosEfetuacaoCorteLigacaoAguaHelper(dadosHelper);
			integracaoComercialHelper.setOrdemServico(ordemServico);
			integracaoComercialHelper.setUsuarioLogado(usuario);

			String qtdParcelas = corteLigacaoAguaActionForm.getQuantidadeParcelas();
			integracaoComercialHelper.setQtdParcelas(qtdParcelas);

			if(ordemServico.getServicoTipo().getDebitoTipo() != null && ordemServico.getServicoNaoCobrancaMotivo() == null){
				try{

					fachada.verificarQuantidadeParcelas(integracaoComercialHelper.getUsuarioLogado(),
									Util.obterShort(String.valueOf(dadosHelper.getQtdeParcelas())));
				}catch(FachadaException e){

					String[] parametros = new String[e.getParametroMensagem().size()];
					e.getParametroMensagem().toArray(parametros);
					ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
					ex.setUrlBotaoVoltar("/gsan/exibirEfetuarCorteLigacaoAguaComRetiradaHidrometro.do");
					throw ex;
				}
			}

			// efetuar Corte Ligação de Água

			if(corteLigacaoAguaActionForm.getVeioEncerrarOS().equalsIgnoreCase("FALSE")){
				integracaoComercialHelper.setVeioEncerrarOS(Boolean.FALSE);

				try{

					fachada.efetuarCorteLigacaoAguaComRetiradaHidrometro(integracaoComercialHelper);

				}catch(FachadaException e){

					String[] parametros = new String[e.getParametroMensagem().size()];
					e.getParametroMensagem().toArray(parametros);
					ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
					ex.setUrlBotaoVoltar("/gsan/exibirEfetuarCorteLigacaoAguaComRetiradaHidrometro.do");
					throw ex;

				}
			}else{
				integracaoComercialHelper.setVeioEncerrarOS(Boolean.TRUE);

				sessao.setAttribute("integracaoComercialHelper", integracaoComercialHelper);

				fachada.efetuarCorteLigacaoAguaComRetiradaHidrometro(integracaoComercialHelper);

				if(sessao.getAttribute("semMenu") == null){
					retorno = actionMapping.findForward("encerrarOrdemServicoAction");
				}else{
					retorno = actionMapping.findForward("encerrarOrdemServicoPopupAction");
				}
				sessao.removeAttribute("caminhoRetornoIntegracaoComercial");
			}

			if(retorno.getName().equalsIgnoreCase("telaSucesso")){
				// registrando operacao
				imovel.setOperacaoEfetuada(operacaoEfetuada);
				imovel.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(imovel);

				ligacaoAgua.setOperacaoEfetuada(operacaoEfetuada);
				ligacaoAgua.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(ligacaoAgua);

				if(hidrometroInstalacaoHistorico != null){
					hidrometroInstalacaoHistorico.setOperacaoEfetuada(operacaoEfetuada);
					hidrometroInstalacaoHistorico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					registradorOperacao.registrarOperacao(hidrometroInstalacaoHistorico);
				}

				if(!dadosHelper.isVeioEncerrarOS()){
					ordemServico.setOperacaoEfetuada(operacaoEfetuada);
					ordemServico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					registradorOperacao.registrarOperacao(ordemServico);
				}

				montarPaginaSucesso(httpServletRequest, "Corte de Ligação de Água com Retirada do Hidrômetro do imóvel " + imovel.getId()
								+ " efetuada com Sucesso", "Efetuar outra Corte de Ligação de Água com Retirada do Hidrômetro",
								"exibirEfetuarCorteLigacaoAguaAction.do?menu=sim",
								"exibirAtualizarCorteLigacaoAguaAction.do?idOrdemServico" + ordemServico.getId(),
								"Atualização Corte Ligação de Água com Retirada do Hidrômetro efetuada");
			}

			return retorno;
		}else{
			throw new ActionServletException("atencao.required", null, "Ordem de Serviço");
		}
	}

}
