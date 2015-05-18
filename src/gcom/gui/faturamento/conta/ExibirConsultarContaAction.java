/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.faturamento.conta;

import gcom.arrecadacao.debitoautomatico.DebitoAutomatico;
import gcom.cadastro.cliente.ClienteConta;
import gcom.cadastro.funcionario.Funcionario;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.*;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.cadastro.imovel.ConsultarImovelActionForm;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela exibi��o da tela de consultar conta
 * 
 * @author pedro alexandre
 * @created 04 de Janeiro de 2006
 */
public class ExibirConsultarContaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirConsultarConta");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		String limparForm = httpServletRequest.getParameter("limparForm");

		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		// Limpa os indicadores de bloqueio
		sessao.removeAttribute("indicadorEmitir2aViaConta");

		// Removendo cole��es da sess�o
		if(limparForm != null && !limparForm.equalsIgnoreCase("")){
			sessao.removeAttribute("colecaoContaImovel");
		}

		String tipoConsulta = httpServletRequest.getParameter("tipoConsulta");
		String idImovelDadosCadastrais = null;

		sessao.removeAttribute("idContaHistorico");
		sessao.removeAttribute("idConta");

		String nomeFuncionario = "";
		Date dataInclusao = null;
		Date dataRetificacao = null;
		Date dataCancelamento = null;
		Date dataRevisao = null;
		Conta conta = null;

		if(tipoConsulta.equalsIgnoreCase("conta")){

			String idConta = httpServletRequest.getParameter("contaID");

			// vai ser recuperado na gera��o do relat�rio 2� Via da Conta
			sessao.setAttribute("idConta", idConta);

			/*
			 * Pesquisando a conta a partir do id recebido
			 * =====================================================================
			 */

			if(idConta != null && !idConta.equalsIgnoreCase("")){

				// faz a consulta da conta por hql. Fernanda Paiva
				conta = fachada.consultarConta(Integer.valueOf(idConta));

				if(conta == null){
					throw new ActionServletException("atencao.pesquisa.conta.inexistente");
				}

				// Colocando o objeto conta selecionado na sess�o
				sessao.setAttribute("conta", conta);
				sessao.removeAttribute("contaHistorico");

				// ****RETIRADA regra do caso de uso: 18.1.3.1. Caso a conta esteja em revis�o

				// (CNTA_DTREVISAO preenchida), retirar da lista de contas.
				// if(conta.getDataRevisao() == null){
				// httpServletRequest.setAttribute("emitirSegundaVia", 2);
				// }

				// ***ADICIONADA regra:

				// Caso a conta esteja com o motivo de revis�o Cobran�a Banc�ria
				// (CMRV_ID = PASI_VLPARAMETRO da tabela PARAMETRO_SISTEMA com
				// PASI_DSPARAMETRO=�P_MOTIVO_REVISAO_COBRANCA_BANCARIA�)
				// e o usu�rio logado n�o possua permiss�o especial para imprimir conta em revis�o
				// com motivo Cobran�a Banc�ria
				// (existir ocorr�ncia na tabela USUARIO_PERMISSAO_ESPECIAL com
				// USUR_ID=identifica��o do usu�rio e
				// PMEP_ID=xx (IMPRIMIR CONTA EM COBRAN�A BANC�RIA)), retirar conta da lista de
				// contas.

				String parametroMotivoRevisaoCobrancaBancaria = "";
				try{

					parametroMotivoRevisaoCobrancaBancaria = ((String) ParametroCobranca.P_MOTIVO_REVISAO_COBRANCA_BANCARIA.executar());
				}catch(ControladorException e1){

					throw new ActionServletException("atencao.sistemaparametro_inexistente", "P_MOTIVO_REVISAO_COBRANCA_BANCARIA");
				}

				// Caso a conta esteja com o motivo de revis�o Cobran�a Banc�ria
				if(conta.getContaMotivoRevisao() != null
								&& parametroMotivoRevisaoCobrancaBancaria.equals(conta.getContaMotivoRevisao().getId().toString())){

					if(fachada.verificarPermissaoImpressaoContasRevisaoCobrancaBancaria(usuarioLogado, conta)){

						httpServletRequest.setAttribute("emitirSegundaVia", ConstantesSistema.SIM);
					}
				}else{

					httpServletRequest.setAttribute("emitirSegundaVia", ConstantesSistema.SIM);
				}

				if(conta.getDebitoCreditoSituacaoAtual() != null
								&& conta.getDebitoCreditoSituacaoAtual().getId().equals(DebitoCreditoSituacao.PRE_FATURADA)){
					httpServletRequest.removeAttribute("emitirSegundaVia");
				}

				boolean temPermissaoEmitirDocumentoPagavelDebitoPrescrito = fachada
								.verificarPermissaoEmitirDocumentoPagavelDebitoPrescrito(usuarioLogado);

				// [SB0004] - Verificar Conta Prescrita
				if(!temPermissaoEmitirDocumentoPagavelDebitoPrescrito
								&& conta.getDebitoCreditoSituacaoAtual().getId().equals(DebitoCreditoSituacao.PRESCRITA)){

					httpServletRequest.removeAttribute("emitirSegundaVia");
				}

			}else if(sessao.getAttribute("conta") != null){
				conta = (Conta) sessao.getAttribute("conta");
			}else{
				throw new ActionServletException("atencao.pesquisa.conta.inexistente");
			}
			// ====================================================================

			// [SB0001 � Validar autoriza��o de acesso ao im�vel pelos usu�rios das empresas de
			// cobran�a administrativa]
			this.validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(usuarioLogado, sessao, conta);

			// [SB0003 � Verificar D�bito em Cobran�a Administrativa]
			this.verificarDebitoCobrancaAdministrativa(usuarioLogado, sessao, conta);

			idImovelDadosCadastrais = conta.getImovel().getId().toString();

			dataInclusao = conta.getDataInclusao();
			dataRetificacao = conta.getDataRetificacao();
			dataCancelamento = conta.getDataCancelamento();
			dataRevisao = conta.getDataRevisao();

			Collection colecaoContaCategoria, colecaoContaImpostosDeduzidos;

			// Removendo cole��es da sess�o
			if(idConta != null && !idConta.equalsIgnoreCase("")){
				sessao.removeAttribute("colecaoContaCategoria");
				sessao.removeAttribute("colecaoContaImpostosDeduzidos");
				sessao.removeAttribute("contaImpostosDeduzidos");
			}

			/*
			 * Categorias (Carregar cole��o)
			 * ======================================================================
			 */
			if(sessao.getAttribute("colecaoContaCategoria") == null){

				FiltroContaCategoria filtroContaCategoria = new FiltroContaCategoria();
				filtroContaCategoria.adicionarCaminhoParaCarregamentoEntidade("comp_id.conta");
				filtroContaCategoria.adicionarCaminhoParaCarregamentoEntidade("comp_id.categoria");

				filtroContaCategoria.adicionarParametro(new ParametroSimples(FiltroContaCategoria.CONTA_ID, conta.getId()));

				colecaoContaCategoria = fachada.pesquisar(filtroContaCategoria, ContaCategoria.class.getName());

				sessao.setAttribute("colecaoContaCategoria", colecaoContaCategoria);
			}
			// ====================================================================

			/*
			 * Impostos Deduzidos (Pesquisar o registrode impostos deduzidos)
			 * ======================================================================
			 */
			if(sessao.getAttribute("contaImpostosDeduzidos") == null){

				FiltroContaImpostosDeduzidos filtroContaImpostosDeduzidos = new FiltroContaImpostosDeduzidos();

				filtroContaImpostosDeduzidos.adicionarCaminhoParaCarregamentoEntidade("conta");
				filtroContaImpostosDeduzidos.adicionarCaminhoParaCarregamentoEntidade("impostoTipo");
				filtroContaImpostosDeduzidos.adicionarParametro(new ParametroSimples(FiltroContaImpostosDeduzidos.CONTA_ID, conta.getId()));

				colecaoContaImpostosDeduzidos = fachada.pesquisar(filtroContaImpostosDeduzidos, ContaImpostosDeduzidos.class.getName());

				ContaImpostosDeduzidos contaImpostosDeduzidos = (ContaImpostosDeduzidos) Util
								.retonarObjetoDeColecao(colecaoContaImpostosDeduzidos);

				sessao.setAttribute("contaImpostosDeduzidos", contaImpostosDeduzidos);
				sessao.setAttribute("colecaoContaImpostosDeduzidos", colecaoContaImpostosDeduzidos);
			}
			// ====================================================================

			sessao.setAttribute(
							"consultarImovelActionForm",
							pesquisarMedicaoConsumoHistExcecoesApresentaDadosConsultarImovel(conta.getReferencia(),
											Integer.valueOf(idImovelDadosCadastrais), Boolean.TRUE, fachada, actionForm));

			if(conta.getIndicadorDebitoConta() == 1){
				DebitoAutomatico debitoAutomatico = fachada.obterObjetoDebitoAutomatico(Integer.valueOf(idImovelDadosCadastrais));

				if(debitoAutomatico != null){

					sessao.setAttribute("debitoAutomatico", fachada.obterObjetoDebitoAutomatico(Integer.valueOf(idImovelDadosCadastrais)));
				}
			}

			if(conta.getIndicadorExecucaoFiscal().equals(ConstantesSistema.SIM)){
				sessao.setAttribute("statusDividaAtivaConta", "E");
			}else if(conta.getIndicadorDividaAtiva().equals(ConstantesSistema.SIM)){
				sessao.setAttribute("statusDividaAtivaConta", "A");
			}else{
				sessao.setAttribute("statusDividaAtivaConta", "N");
			}

			Usuario usuario = conta.getUsuario();
			nomeFuncionario = obterNomeUsuario(nomeFuncionario, usuario);

		}else if(tipoConsulta.equalsIgnoreCase("contaHistorico")){

			String idContaHistorico = httpServletRequest.getParameter("contaID");

			// vai ser recuperado na gera��o do relat�rio 2� Via da Conta
			sessao.setAttribute("idContaHistorico", idContaHistorico);

			/*
			 * Pesquisando a conta a partir do id recebido
			 * =====================================================================
			 */
			ContaHistorico contaHistorico = null;

			if(idContaHistorico != null && !idContaHistorico.equalsIgnoreCase("")){

				FiltroContaHistorico filtroContaHistorico = new FiltroContaHistorico();

				filtroContaHistorico.adicionarCaminhoParaCarregamentoEntidade("imovel");
				filtroContaHistorico.adicionarCaminhoParaCarregamentoEntidade("debitoCreditoSituacaoAtual");
				filtroContaHistorico.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");
				filtroContaHistorico.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");
				filtroContaHistorico.adicionarCaminhoParaCarregamentoEntidade("motivoNaoEntregaDocumento");
				filtroContaHistorico.adicionarCaminhoParaCarregamentoEntidade("contaMotivoInclusao");
				filtroContaHistorico.adicionarCaminhoParaCarregamentoEntidade("contaMotivoRetificacao");
				filtroContaHistorico.adicionarCaminhoParaCarregamentoEntidade("contaMotivoCancelamento");
				filtroContaHistorico.adicionarCaminhoParaCarregamentoEntidade("contaMotivoRevisao");
				filtroContaHistorico.adicionarCaminhoParaCarregamentoEntidade("consumoTarifa");

				filtroContaHistorico.adicionarParametro(new ParametroSimples(FiltroContaHistorico.ID, idContaHistorico));

				Collection colecaoContaHistorico = fachada.pesquisar(filtroContaHistorico, ContaHistorico.class.getName());

				if(colecaoContaHistorico == null || colecaoContaHistorico.isEmpty()){
					throw new ActionServletException("atencao.pesquisa.conta.inexistente");
				}

				contaHistorico = (ContaHistorico) Util.retonarObjetoDeColecao(colecaoContaHistorico);

				try{
					if(ParametroFaturamento.P_HABILITAR_EMISSAO_SEGUNDA_VIA_CONTA_HISTORICO.executar().equals(
									ConstantesSistema.NAO.toString())){

						sessao.removeAttribute("emitirSegundaVia");
					}else{

						httpServletRequest.setAttribute("emitirSegundaVia", ConstantesSistema.SIM);
					}
				}catch(ControladorException e){
					e.printStackTrace();
				}

				// Colocando o objeto conta selecionado na sess�o
				sessao.setAttribute("contaHistorico", contaHistorico);

				sessao.removeAttribute("conta");
				sessao.removeAttribute("colecaoContaCategoria");
				sessao.removeAttribute("colecaoContaImpostosDeduzidos");
				sessao.removeAttribute("contaImpostosDeduzidos");

				// if(contaHistorico.getDataRevisao() == null){
				// httpServletRequest.setAttribute("emitirSegundaVia", 2);
				// }

			}else if(sessao.getAttribute("contaHistorico") != null){
				contaHistorico = (ContaHistorico) sessao.getAttribute("contaHistorico");
			}else{
				throw new ActionServletException("atencao.pesquisa.conta.inexistente");
			}
			// ====================================================================

			idImovelDadosCadastrais = contaHistorico.getImovel().getId().toString();

			dataInclusao = contaHistorico.getDataInclusao();
			dataRetificacao = contaHistorico.getDataRetificacao();
			dataCancelamento = contaHistorico.getDataCancelamento();
			dataRevisao = contaHistorico.getDataRevisao();

			sessao.setAttribute(
							"consultarImovelActionForm",
							pesquisarMedicaoConsumoHistExcecoesApresentaDadosConsultarImovel(contaHistorico.getAnoMesReferenciaContabil(),
											Integer.valueOf(idImovelDadosCadastrais), Boolean.TRUE, fachada, actionForm));

			if(contaHistorico.getIndicadorDebitoConta() == 1){
				sessao.setAttribute("debitoAutomatico", fachada.obterObjetoDebitoAutomatico(Integer.valueOf(idImovelDadosCadastrais)));
			}

			Usuario usuario = contaHistorico.getUsuario();

			nomeFuncionario = obterNomeUsuario(nomeFuncionario, usuario);

		}

		sessao.removeAttribute("colecaoContaPrescricaoHistorico");

		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("contaID"))){

			// Caso a conta esteja prescrita
			FiltroContaPrescricaoHistorico filtroContaPrescricaoHistorico = new FiltroContaPrescricaoHistorico();
			filtroContaPrescricaoHistorico.adicionarParametro(new ParametroSimples(FiltroContaPrescricaoHistorico.CONTA_GERAL_ID, Util
							.obterInteger(httpServletRequest.getParameter("contaID"))));
			filtroContaPrescricaoHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroContaPrescricaoHistorico.USUARIO);
			filtroContaPrescricaoHistorico.setCampoOrderByDesc(FiltroContaPrescricaoHistorico.DATA_EVENTO);

			Collection<ContaPrescricaoHistorico> colecaoContaPrescricaoHistorico = fachada.pesquisar(filtroContaPrescricaoHistorico,
							ContaPrescricaoHistorico.class.getName());

			if(!Util.isVazioOrNulo(colecaoContaPrescricaoHistorico)){

				sessao.setAttribute("colecaoContaPrescricaoHistorico", colecaoContaPrescricaoHistorico);
			}
		}

		// utilizado para saber se no emitir 2 via de conta, mostrar� o c�digo
		// de barras ou naum
		if(httpServletRequest.getParameter("contaSemCodigoBarras") != null){
			httpServletRequest.setAttribute("contaSemCodigoBarras", 1);
		}

		sessao.removeAttribute("contaSemCodigoBarras");

		// envia uma flag que ser� verificado no cliente_resultado_pesquisa.jsp
		// para saber se ir� usar o enviar dados ou o enviar dados parametros
		if(httpServletRequest.getParameter("caminhoRetornoTelaConsultaConta") != null){
			sessao.setAttribute("caminhoRetornoTelaConsultaConta", httpServletRequest.getParameter("caminhoRetornoTelaConsultaConta"));
		}

		// pesquisar cliente do imovel
		if(!Util.isVazioOuBranco(idImovelDadosCadastrais)){

			// Grid Datas
			if(!Util.isVazioOuBranco(httpServletRequest.getParameter("contaID")) && !Util.isVazioOuBranco(tipoConsulta)){

				String tipo = "";

				if(tipoConsulta.equalsIgnoreCase("conta")){
					tipo = "C";
				}else if(tipoConsulta.equalsIgnoreCase("contaHistorico")){
					tipo = "CH";
				}

				Funcionario funcionario = Fachada.getInstancia().obterFuncionarioExecutouOperacaoEmConta(
								Integer.valueOf(httpServletRequest.getParameter("contaID")), tipo);

				if(funcionario != null){
					nomeFuncionario = funcionario.getId() + " - " + funcionario.getNome();
				}

			}

			// Clientes
			Collection<ClienteConta> clientesConta = null;

			if(!Util.isVazioOuBranco(httpServletRequest.getParameter("contaID"))){
				clientesConta = fachada.pesquisarClientesContaPeloImovelEConta(Util.converterStringParaInteger(idImovelDadosCadastrais),
								Util.converterStringParaInteger(httpServletRequest.getParameter("contaID")));
			}

			sessao.setAttribute("clientesConta", clientesConta);
		}


		FuncionarioContaHelp funcionarioContaHelp = new FuncionarioContaHelp();
		funcionarioContaHelp.verificarFuncionarioUltimaOperacao(nomeFuncionario, dataInclusao, dataRetificacao, dataCancelamento,
						dataRevisao);

		httpServletRequest.setAttribute("funcionarioContaHelp", funcionarioContaHelp);

		if(fachada.existeProcessoExecucaoFiscal().equals(ConstantesSistema.SIM)){
			sessao.setAttribute("exibirDividaAtivaColuna", "S");
		}else{
			sessao.removeAttribute("exibirDividaAtivaColuna");
		}

		return retorno;

	}

	private String obterNomeUsuario(String nomeFuncionario, Usuario usuario){

		if(usuario != null){
			nomeFuncionario = usuario.getId() + " - " + usuario.getNomeUsuario();
		}
		return nomeFuncionario;
	}

	private ConsultarImovelActionForm pesquisarMedicaoConsumoHistExcecoesApresentaDadosConsultarImovel(Integer anoMesReferencia,
					Integer idImovel, boolean ligacaoAgua, Fachada fachada, ActionForm actionForm){

		Object[] parmMedicaoHistorico = null;

		ConsultarImovelActionForm consultarImovelActionForm = new ConsultarImovelActionForm();

		Collection parmsMedicaoHistoricoLigacaoAgua = fachada.pesquisarMedicaoConsumoHistoricoExcecoesApresentaDadosConsultarImovel(
						anoMesReferencia, idImovel, ligacaoAgua);

		if(!parmsMedicaoHistoricoLigacaoAgua.isEmpty()){

			parmMedicaoHistorico = (Object[]) parmsMedicaoHistoricoLigacaoAgua.iterator().next();

			// leitura atual faturamento
			if(parmMedicaoHistorico[10] != null){
				consultarImovelActionForm.setLeituraAtualFaturada(((Integer) parmMedicaoHistorico[10]).toString());
			}else{
				consultarImovelActionForm.setLeituraAtualFaturada("");
			}

			// data leitura atual faturada
			Date dtLeituraAtualFaturada = null;
			if(parmMedicaoHistorico[3] != null){
				dtLeituraAtualFaturada = (Date) parmMedicaoHistorico[3];
			}

			if(dtLeituraAtualFaturada != null){
				consultarImovelActionForm.setDtLeituraFaturada(Util.formatarData(dtLeituraAtualFaturada));
			}else{
				consultarImovelActionForm.setDtLeituraFaturada("");
			}

			// descri��o leitura anormalidade faturamento
			if(parmMedicaoHistorico[13] != null){
				consultarImovelActionForm.setAnormalidadeLeituraFaturada((String) parmMedicaoHistorico[13]);
			}else{
				consultarImovelActionForm.setAnormalidadeLeituraFaturada("");
			}

			// leitura anterior faturamento
			if(parmMedicaoHistorico[5] != null){
				consultarImovelActionForm.setLeituraAnterior(((Integer) parmMedicaoHistorico[5]).toString());
			}else{
				consultarImovelActionForm.setLeituraAnterior("");
			}

			// data leitura anterior
			Date dataLeituraAnterior = null;
			if(parmMedicaoHistorico[2] != null){
				dataLeituraAnterior = (Date) parmMedicaoHistorico[2];
			}

			if(dataLeituraAnterior != null){
				consultarImovelActionForm.setDtLeituraAnterior(Util.formatarData(dataLeituraAnterior));
			}else{
				consultarImovelActionForm.setDtLeituraAnterior("");
			}

			// dias de consumo
			int diasConsumo = 0;
			if(dataLeituraAnterior != null && dtLeituraAtualFaturada != null){
				diasConsumo = gcom.util.Util.obterQuantidadeDiasEntreDuasDatas(dataLeituraAnterior, dtLeituraAtualFaturada);
			}

			consultarImovelActionForm.setDiasConsumo("" + diasConsumo);

			// anormalidade consumo
			if(parmMedicaoHistorico[24] != null){
				consultarImovelActionForm.setAnormalidadeConsumo(((String) parmMedicaoHistorico[24]).toString());
			}else{
				consultarImovelActionForm.setAnormalidadeConsumo("");
			}

			// descri��o leitura situa��o atual
			if(parmMedicaoHistorico[8] != null){
				consultarImovelActionForm.setSituacaoLeituraAtual(((String) parmMedicaoHistorico[8]).toString());
			}else{
				consultarImovelActionForm.setSituacaoLeituraAtual("");
			}

			// tipo de consumo
			if(parmMedicaoHistorico[25] != null){
				consultarImovelActionForm.setConsumoTipo(((String) parmMedicaoHistorico[25]).toString());
			}else{
				consultarImovelActionForm.setConsumoTipo("");
			}

		}
		return consultarImovelActionForm;
	}

	/**
	 * [SB0001] ? Validar autoriza��o de acesso ao im�vel pelos usu�rios das empresas de cobran�a
	 * administrativa
	 * 
	 * @author Hugo Lima
	 * @date 15/08/2012
	 * @param usuario
	 * @param sessao
	 * @param idImovel
	 * @param conta
	 */
	private void validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(Usuario usuario, HttpSession sessao, Conta conta){

		boolean resposta = Fachada.getInstancia().validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(usuario,
						conta.getImovel().getId(), conta);

		if(!resposta){

			sessao.removeAttribute("emitirSegundaVia");
		}else{
			// 1.2.2.2.1. Liberar o bot�o ?Emitir 2�. Via de Conta?.
			sessao.setAttribute("emitirSegundaVia", ConstantesSistema.SIM);
		}
	}

	/**
	 * [SB0003] ? Verificar D�bito em Cobran�a Administrativa
	 * 
	 * @author Hugo Lima
	 * @date 15/08/2012
	 * @param usuario
	 * @param idImovel
	 * @param colecaoContasValoresHelper
	 * @param colecaoGuiaPagamentoValoresHelper
	 */
	private void verificarDebitoCobrancaAdministrativa(Usuario usuario, HttpSession sessao, Conta conta){

		Collection<Integer> colecaoIdsContasRemover = null;
		boolean idsConsultados = false;

		// 1. Caso o usu�rio logado perten�a a uma empresa de cobran�a administrativa
		if(Fachada.getInstancia().existeEmpresaCobrancaContrato(usuario.getEmpresa().getId())){

			// 1.1. Caso existam, na lista de contas selecionadas, contas em cobran�a
			// administrativa
			if(conta.getIndicadorCobrancaAdministrativa().equals(ConstantesSistema.SIM)){

				// Consulta os ids de contas a remover apenas uma vez caso seja
				// constatada a ocorrencia de pelo menos uma conta em cobranca
				// administrativa
				if(!idsConsultados){
					// Pesquisa os ids das contas a serem excluidos
					colecaoIdsContasRemover = Fachada.getInstancia().obterIdsContasCobrancaAdministrativaEmpresaDiferente(
									usuario.getEmpresa().getId(), conta.getImovel().getId());
					idsConsultados = true;
				}

				// Caso o id da conta em cobran�a administrativa exista na cole��o de
				// Ids a serem removidos exclui a conta da cole��o
				if(colecaoIdsContasRemover.contains(conta.getId())){
					sessao.removeAttribute("emitirSegundaVia");
				}
			}
		}

	}
}
