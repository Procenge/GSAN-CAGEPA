/*
 * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.atendimentopublico;

import gcom.atendimentopublico.ligacaoagua.FiltroDiametroLigacao;
import gcom.atendimentopublico.ligacaoagua.FiltroRamalLocalInstalacao;
import gcom.atendimentopublico.ligacaoagua.RamalLocalInstalacao;
import gcom.atendimentopublico.ligacaoesgoto.*;
import gcom.atendimentopublico.ordemservico.*;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.consumo.LigacaoTipo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;
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
 * Action responsável pela pre-exibição da pagina de inserir bairro
 * 
 * @author Leandro Cavalcanti
 * @created 20 de Junho de 2006
 */
public class ExibirEfetuarLigacaoEsgotoAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		ActionForward retorno = actionMapping.findForward("efetuarLigacaoEsgoto");

		EfetuarLigacaoEsgotoActionForm ligacaoEsgotoActionForm = (EfetuarLigacaoEsgotoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Pesquisar Funcionario ENTER
		if((ligacaoEsgotoActionForm.getIdFuncionario() != null && !ligacaoEsgotoActionForm.getIdFuncionario().equals(""))
						&& (ligacaoEsgotoActionForm.getDescricaoFuncionario() == null || ligacaoEsgotoActionForm.getDescricaoFuncionario()
										.equals(""))){

			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();

			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, ligacaoEsgotoActionForm.getIdFuncionario()));

			Collection colecaoFuncionario = getFachada().pesquisar(filtroFuncionario, Funcionario.class.getName());

			if(colecaoFuncionario == null || colecaoFuncionario.isEmpty()){

				ligacaoEsgotoActionForm.setIdFuncionario("");
				ligacaoEsgotoActionForm.setDescricaoFuncionario("FUNCIONÁRIO INEXISTENTE");

				httpServletRequest.setAttribute("corFuncionario", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idFuncionario");

			}else{

				Funcionario funcionario = (Funcionario) Util.retonarObjetoDeColecao(colecaoFuncionario);

				ligacaoEsgotoActionForm.setIdFuncionario(funcionario.getId().toString());
				ligacaoEsgotoActionForm.setDescricaoFuncionario(funcionario.getNome());

				httpServletRequest.setAttribute("nomeCampo", "botaoAdicionar");

			}
		}

		// Pesquisar Funcionário POPUP
		String pesquisarFuncionario = httpServletRequest.getParameter("pesquisarFuncionario");

		if(pesquisarFuncionario != null && !pesquisarFuncionario.equalsIgnoreCase("")){
			retorno = actionMapping.findForward("pesquisarFuncionario");
		}

		this.consultaSelectObrigatorio(sessao);

		Boolean veioEncerrarOS = null;

		if(httpServletRequest.getAttribute("veioEncerrarOS") != null){
			veioEncerrarOS = Boolean.TRUE;
		}else{
			if(ligacaoEsgotoActionForm.getVeioEncerrarOS() != null && ligacaoEsgotoActionForm.getVeioEncerrarOS().equalsIgnoreCase("TRUE")){
				veioEncerrarOS = Boolean.TRUE;
			}else{
				veioEncerrarOS = Boolean.FALSE;
			}
		}

		ligacaoEsgotoActionForm.setIndicadorCaixaGordura("2");

		ligacaoEsgotoActionForm.setVeioEncerrarOS("" + veioEncerrarOS);

		// Variavel responsavél pelo preenchimento do imovel no formulário
		String idOrdemServico = null;
		if(httpServletRequest.getAttribute("veioEncerrarOS") != null){
			idOrdemServico = (String) httpServletRequest.getAttribute("veioEncerrarOS");
			ligacaoEsgotoActionForm.setDataLigacao((String) httpServletRequest.getAttribute("dataEncerramento"));
			sessao.setAttribute("caminhoRetornoIntegracaoComercial", httpServletRequest.getAttribute("caminhoRetornoIntegracaoComercial"));

		}else if(ligacaoEsgotoActionForm.getIdOrdemServico() != null){
			idOrdemServico = ligacaoEsgotoActionForm.getIdOrdemServico();
		}

		if(httpServletRequest.getAttribute("semMenu") != null){
			sessao.setAttribute("semMenu", "SIM");
		}else{
			sessao.removeAttribute("semMenu");
		}

		if(idOrdemServico != null && !idOrdemServico.trim().equals("")){

			FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();
			filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, idOrdemServico));

			OrdemServico ordemServico = fachada.recuperaOSPorId(new Integer(idOrdemServico));

			if(ordemServico != null){

				fachada.validarLigacaoEsgotoExibir(ordemServico, veioEncerrarOS);

				sessao.setAttribute("ordemServico", ordemServico);

				ligacaoEsgotoActionForm.setIdOrdemServico(idOrdemServico);
				ligacaoEsgotoActionForm.setNomeOrdemServico(ordemServico.getServicoTipo().getDescricao());

				/*------------Inicio do codigo carregar * dados do Imovél--------------------*/

				Imovel imovel = ordemServico.getRegistroAtendimento().getImovel();
				sessao.setAttribute("imovel", imovel);
				String matriculaImovel = imovel.getId().toString();

				if(imovel != null){

					// Matricula Imóvel
					ligacaoEsgotoActionForm.setMatriculaImovel(imovel.getId().toString());

					// Inscrição Imóvel
					String inscricaoImovel = fachada.pesquisarInscricaoImovel(imovel.getId(), true);

					ligacaoEsgotoActionForm.setInscricaoImovel(inscricaoImovel);

					// Situação da Ligação de Agua
					String situacaoLigacaoAgua = imovel.getLigacaoAguaSituacao().getDescricao();
					ligacaoEsgotoActionForm.setSituacaoLigacaoAgua(situacaoLigacaoAgua);

					// Situação da Ligação de Esgoto
					String situacaoLigacaoEsgoto = imovel.getLigacaoEsgotoSituacao().getDescricao();
					ligacaoEsgotoActionForm.setSituacaoLigacaoEsgoto(situacaoLigacaoEsgoto);

					this.pesquisarCliente(ligacaoEsgotoActionForm, new Integer(matriculaImovel));
				}

				if(ordemServico.getServicoTipo() != null && ordemServico.getServicoTipo().getDebitoTipo() != null){
					ligacaoEsgotoActionForm.setIdTipoDebito(ordemServico.getServicoTipo().getDebitoTipo().getId().toString());
					ligacaoEsgotoActionForm.setDescricaoTipoDebito(ordemServico.getServicoTipo().getDebitoTipo().getDescricao());
				}else{
					ligacaoEsgotoActionForm.setIdTipoDebito("");
					ligacaoEsgotoActionForm.setDescricaoTipoDebito("");
				}

				// [FS0013] - Alteração de Valor
				this.permitirAlteracaoValor(ordemServico.getServicoTipo(), ligacaoEsgotoActionForm);
				String calculaValores = httpServletRequest.getParameter("calculaValores");

				SistemaParametro sistemaParametro = this.getFachada().pesquisarParametrosDoSistema();
				Integer qtdeParcelas = null;
				BigDecimal valorDebito = new BigDecimal(0);

				if(calculaValores != null && calculaValores.equals("S")){

					// [UC0186] - Calcular Prestação
					BigDecimal taxaJurosFinanciamento = null;
					qtdeParcelas = new Integer(ligacaoEsgotoActionForm.getQuantidadeParcelas());

					if(ordemServico.getServicoTipo().getIndicadorCobrarJuros() == ConstantesSistema.SIM.shortValue()
									&& qtdeParcelas.intValue() != 1){

						taxaJurosFinanciamento = sistemaParametro.getPercentualTaxaJurosFinanciamento();
					}else{
						taxaJurosFinanciamento = new BigDecimal(0);
					}

					BigDecimal valorPrestacao = null;
					if(taxaJurosFinanciamento != null){

						valorDebito = new BigDecimal(ligacaoEsgotoActionForm.getValorDebito().replace(",", "."));
						valorDebito = valorDebito.multiply(new BigDecimal(ligacaoEsgotoActionForm.getPercentualCobranca())
										.divide(new BigDecimal("100")));
						valorPrestacao = this.getFachada().calcularPrestacao(taxaJurosFinanciamento, qtdeParcelas, valorDebito,
										new BigDecimal("0.00"));
						valorPrestacao.setScale(2, BigDecimal.ROUND_HALF_UP);
					}

					if(valorPrestacao != null){
						String valorPrestacaoComVirgula = Util.formataBigDecimal(valorPrestacao, 2, true);
						ligacaoEsgotoActionForm.setValorParcelas(valorPrestacaoComVirgula);
					}else{
						ligacaoEsgotoActionForm.setValorParcelas("0,00");
					}

				}else{

					valorDebito = fachada.obterValorDebito(ordemServico.getServicoTipo().getId(), imovel.getId(), new Short(
									LigacaoTipo.LIGACAO_AGUA + ""));
					ligacaoEsgotoActionForm.setValorDebito(Util.formataBigDecimal(valorDebito, 2, true));
				}

				if(ordemServico.getServicoNaoCobrancaMotivo() != null){
					ligacaoEsgotoActionForm.setMotivoNaoCobranca(ordemServico.getServicoNaoCobrancaMotivo().getId().toString());
				}

				if(ordemServico.getPercentualCobranca() != null){
					ligacaoEsgotoActionForm.setPercentualCobranca(ordemServico.getPercentualCobranca().toString());
				}

				if(ordemServico.getDataExecucao() != null){
					ligacaoEsgotoActionForm.setDataLigacao(Util.formatarData(ordemServico.getDataExecucao()));
				}

				// Inscrição do Imovél
				String inscricaoImovel = imovel.getInscricaoFormatada();

				ligacaoEsgotoActionForm.setMatriculaImovel(matriculaImovel);
				ligacaoEsgotoActionForm.setInscricaoImovel(inscricaoImovel);

				// Situação da Ligação de Agua
				String situacaoLigacaoAgua = imovel.getLigacaoAguaSituacao().getDescricao();
				ligacaoEsgotoActionForm.setSituacaoLigacaoAgua(situacaoLigacaoAgua);

				sessao.setAttribute("ligacaoAguaSituacao", imovel.getLigacaoAguaSituacao());

				// Situação da Ligação de Esgoto
				String situacaoLigacaoEsgoto = imovel.getLigacaoEsgotoSituacao().getDescricao();
				ligacaoEsgotoActionForm.setSituacaoLigacaoEsgoto(situacaoLigacaoEsgoto);

				this.pesquisarCliente(ligacaoEsgotoActionForm, new Integer(matriculaImovel));

				/*-------------------- Dados da Ligação ----------------------------*/

				this.consultaSelectObrigatorio(sessao);

				// Carregando campo Percentual de Esgoto
				// Item 4.6
				if(ligacaoEsgotoActionForm.getPerfilLigacao() != null && !ligacaoEsgotoActionForm.getPerfilLigacao().equals("")){

					FiltroLigacaoEsgotoPerfil filtroLigacaoPercentualEsgoto = new FiltroLigacaoEsgotoPerfil();

					filtroLigacaoPercentualEsgoto.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoPerfil.ID,
									ligacaoEsgotoActionForm.getPerfilLigacao()));

					Collection colecaoPercentualEsgoto = fachada.pesquisar(filtroLigacaoPercentualEsgoto, LigacaoEsgotoPerfil.class
									.getName());

					if(colecaoPercentualEsgoto != null && !colecaoPercentualEsgoto.isEmpty()){

						LigacaoEsgotoPerfil percentualEsgotoPerfil = (LigacaoEsgotoPerfil) colecaoPercentualEsgoto.iterator().next();

						String percentualFormatado = percentualEsgotoPerfil.getPercentualEsgotoConsumidaColetada().toString().replace(".",
										",");

						ligacaoEsgotoActionForm.setPercentualEsgoto(percentualFormatado);
					}
				}

				/*
				 * String dataEncerramentoOdServico = Util
				 * .formatarData(ordemServico.getDataEncerramento());
				 * ligacaoEsgotoActionForm
				 * .setDataLigacao(dataEncerramentoOdServico);
				 */

				// -----------------------------------------------------------
				// Verificar permissão especial
				boolean temPermissaoMotivoNaoCobranca = fachada.verificarPermissaoInformarMotivoNaoCobranca(usuarioLogado);
				// -----------------------------------------------------------

				if(temPermissaoMotivoNaoCobranca){
					httpServletRequest.setAttribute("permissaoMotivoNaoCobranca", temPermissaoMotivoNaoCobranca);
				}

				boolean temPermissaoPercentualCobrancaExcedente = fachada
								.verificarPermissaoInformarPercentualCobrancaExcedente(usuarioLogado);
				boolean temPermissaoQuantidadeParcelasExcedente = fachada
								.verificarPermissaoInformarQuantidadeParcelasExcedentes(usuarioLogado);

				if(temPermissaoPercentualCobrancaExcedente){

					httpServletRequest.setAttribute("permissaoPercentualCobrancaExcedente", temPermissaoPercentualCobrancaExcedente);
					httpServletRequest.setAttribute("colecaoPercentualCobranca", fachada.obterPercentuaisCobranca());

				}else{

					ligacaoEsgotoActionForm.setPercentualCobranca("100");
				}

				if(!temPermissaoQuantidadeParcelasExcedente){

					ligacaoEsgotoActionForm.setQuantidadeParcelas("1");
				}else{

					httpServletRequest.setAttribute("temPermissaoQuantidadeParcelasExcedente", temPermissaoQuantidadeParcelasExcedente);
				}
				if(!temPermissaoPercentualCobrancaExcedente && !temPermissaoQuantidadeParcelasExcedente){
					ligacaoEsgotoActionForm.setValorParcelas(Util.formataBigDecimal(valorDebito, 2, true));
				}

			}else{
				ligacaoEsgotoActionForm.setNomeOrdemServico("Ordem de Serviço inexistente");
				ligacaoEsgotoActionForm.setIdOrdemServico("");
				httpServletRequest.setAttribute("OrdemServioInexistente", true);
			}

		}else{

			httpServletRequest.setAttribute("nomeCampo", "idOrdemServico");

			ligacaoEsgotoActionForm.setIdOrdemServico("");
			ligacaoEsgotoActionForm.setMatriculaImovel("");
			ligacaoEsgotoActionForm.setInscricaoImovel("");
			ligacaoEsgotoActionForm.setClienteUsuario("");
			ligacaoEsgotoActionForm.setCpfCnpjCliente("");
			ligacaoEsgotoActionForm.setSituacaoLigacaoAgua("");
			ligacaoEsgotoActionForm.setSituacaoLigacaoEsgoto("");
			ligacaoEsgotoActionForm.setNomeOrdemServico("");
			ligacaoEsgotoActionForm.setIdTipoDebito("");
			ligacaoEsgotoActionForm.setDescricaoTipoDebito("");
			ligacaoEsgotoActionForm.setQuantidadeParcelas("");
			ligacaoEsgotoActionForm.setValorParcelas("");
			ligacaoEsgotoActionForm.setPercentualCobranca("-1");
			ligacaoEsgotoActionForm.setMotivoNaoCobranca("-1");
		}

		// Parâmetro que indica obrigatoriedade do campo Funcionário
		String indicadorObrigatoriedadeFuncionario = null;
		try{
			indicadorObrigatoriedadeFuncionario = ParametroCadastro.P_INDICADOR_OBRIGATORIEDADE_FUNCIONARIO.executar();
		}catch(ControladorException e){
			e.printStackTrace();
		}
		httpServletRequest.setAttribute("indicadorObrigatoriedadeFuncionario", indicadorObrigatoriedadeFuncionario);

		String pPermitirInformarPercentualColeta = null;

		try{

			pPermitirInformarPercentualColeta = (String) ParametroAtendimentoPublico.P_PERMITIR_INFORMAR_PCOLETA_LIGACAO_ESGOTO.executar();
		}catch(ControladorException e){

			throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
		}

		if(pPermitirInformarPercentualColeta.equals(ConstantesSistema.SIM.toString())){

			sessao.setAttribute("pPermitirInformarPercentualColeta", true);
		}else{

			ligacaoEsgotoActionForm.setPercentualColeta("100,00");
		}

		return retorno;
	}

	/*
	 * [FS0013 - Alteração de Valor]
	 * autor: Raphael Rossiter
	 * data: 19/04/2007
	 */
	private void permitirAlteracaoValor(ServicoTipo servicoTipo, EfetuarLigacaoEsgotoActionForm form){

		if(servicoTipo.getIndicadorPermiteAlterarValor() == ConstantesSistema.INDICADOR_USO_ATIVO.shortValue()){

			form.setAlteracaoValor("OK");
		}else{
			form.setAlteracaoValor("");
		}

	}

	/**
	 * Pesquisa Cliente
	 * 
	 * @author Rafael Pinto
	 * @date 22/08/2006
	 */
	private void pesquisarCliente(EfetuarLigacaoEsgotoActionForm ligacaoEsgotoActionForm, Integer matriculaImovel){

		// Filtro para carregar o Cliente
		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, matriculaImovel));

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO, ClienteRelacaoTipo.USUARIO));

		filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));

		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");

		Collection colecaoClienteImovel = Fachada.getInstancia().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

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
			ligacaoEsgotoActionForm.setClienteUsuario(cliente.getNome());
			ligacaoEsgotoActionForm.setCpfCnpjCliente(documento);

		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Cliente");
		}
	}

	/**
	 * Monta os select´s obrigatorios
	 * 
	 * @author Rafael Pinto
	 * @date 22/08/2006
	 */
	private void consultaSelectObrigatorio(HttpSession sessao){

		Fachada fachada = Fachada.getInstancia();

		// Filtro para o campo Diametro Ligação Água
		Collection colecaoDiametroLigacao = (Collection) sessao.getAttribute("colecaoDiametroLigacaoAgua");

		if(colecaoDiametroLigacao == null){

			FiltroDiametroLigacaoEsgoto filtroDiametroLigacaoEsgoto = new FiltroDiametroLigacaoEsgoto();

			filtroDiametroLigacaoEsgoto.adicionarParametro(new ParametroSimples(FiltroDiametroLigacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroDiametroLigacaoEsgoto.setCampoOrderBy(FiltroDiametroLigacao.DESCRICAO);

			colecaoDiametroLigacao = fachada.pesquisar(filtroDiametroLigacaoEsgoto, LigacaoEsgotoDiametro.class.getName());

			if(colecaoDiametroLigacao != null && !colecaoDiametroLigacao.isEmpty()){
				sessao.setAttribute("colecaoDiametroLigacao", colecaoDiametroLigacao);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Diametro da Ligação");
			}
		}

		// Filtro para o campo Material da Ligação
		Collection colecaoMaterialLigacao = (Collection) sessao.getAttribute("colecaoMaterialLigacao");

		if(colecaoMaterialLigacao == null){

			FiltroLigacaoMaterialEsgoto filtroLigacaoMaterialEsgoto = new FiltroLigacaoMaterialEsgoto();
			filtroLigacaoMaterialEsgoto.adicionarParametro(new ParametroSimples(FiltroLigacaoMaterialEsgoto.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroLigacaoMaterialEsgoto.setCampoOrderBy(FiltroLigacaoMaterialEsgoto.DESCRICAO);

			colecaoMaterialLigacao = fachada.pesquisar(filtroLigacaoMaterialEsgoto, LigacaoEsgotoMaterial.class.getName());

			if(colecaoMaterialLigacao != null && !colecaoMaterialLigacao.isEmpty()){
				sessao.setAttribute("colecaoMaterialLigacao", colecaoMaterialLigacao);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Material da Ligação");
			}
		}

		// Filtro para o campo Perfil da Ligação
		Collection colecaoPerfilLigacao = (Collection) sessao.getAttribute("colecaoPerfilLigacao");

		if(colecaoPerfilLigacao == null){

			FiltroLigacaoEsgotoPerfil filtroLigacaoEsgotoPerfil = new FiltroLigacaoEsgotoPerfil();
			filtroLigacaoEsgotoPerfil.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoPerfil.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroLigacaoEsgotoPerfil.setCampoOrderBy(FiltroLigacaoEsgotoPerfil.DESCRICAO);

			colecaoPerfilLigacao = fachada.pesquisar(filtroLigacaoEsgotoPerfil, LigacaoEsgotoPerfil.class.getName());

			if(colecaoPerfilLigacao != null && !colecaoPerfilLigacao.isEmpty()){
				sessao.setAttribute("colecaoPerfilLigacao", colecaoPerfilLigacao);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Perfil de Ligação");
			}
		}

		// Filtro para o campo Ramal Local de Instalacao
		Collection colecaoLocalInstalacaoRamal = (Collection) sessao.getAttribute("colecaoLocalInstalacaoRamal");

		if(colecaoLocalInstalacaoRamal == null){

			FiltroRamalLocalInstalacao filtroRamalLocalInstalacao = new FiltroRamalLocalInstalacao();
			filtroRamalLocalInstalacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoPerfil.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroRamalLocalInstalacao.setCampoOrderBy(FiltroLigacaoEsgotoPerfil.DESCRICAO);

			colecaoLocalInstalacaoRamal = fachada.pesquisar(filtroRamalLocalInstalacao, RamalLocalInstalacao.class.getName());

			if(colecaoLocalInstalacaoRamal != null && !colecaoLocalInstalacaoRamal.isEmpty()){
				sessao.setAttribute("colecaoLocalInstalacaoRamal", colecaoLocalInstalacaoRamal);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Ramal Local de Instalação");
			}
		}

		// Filtro para o campo Motivo nao cobranca
		Collection colecaoNaoCobranca = (Collection) sessao.getAttribute("colecaoNaoCobranca");
		if(colecaoNaoCobranca == null){
			FiltroServicoNaoCobrancaMotivo filtroServicoNaoCobrancaMotivo = new FiltroServicoNaoCobrancaMotivo();

			filtroServicoNaoCobrancaMotivo.setCampoOrderBy(FiltroDebitoTipo.DESCRICAO);

			colecaoNaoCobranca = fachada.pesquisar(filtroServicoNaoCobrancaMotivo, ServicoNaoCobrancaMotivo.class.getName());

			if(colecaoNaoCobranca != null && !colecaoNaoCobranca.isEmpty()){
				sessao.setAttribute("colecaoNaoCobranca", colecaoNaoCobranca);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Motivo da Não Cobrança");
			}
		}

	}
}