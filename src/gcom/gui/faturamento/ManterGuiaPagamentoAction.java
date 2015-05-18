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
 * GSANPCG
 * Eduardo Henrique
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

package gcom.gui.faturamento;

import gcom.arrecadacao.pagamento.FiltroGuiaPagamentoPrestacao;
import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacao;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.gui.faturamento.guiapagamento.AtualizarGuiaPagamentoActionForm;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ManterGuiaPagamentoAction
				extends GcomAction {

	/**
	 * @author eduardo henrique
	 * @date 20/08/2008
	 *       Altera��o para Impress�o de Guia de Pagamento por Presta��o
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		Collection guiasPagamento = (Collection) sessao.getAttribute("guiasPagamentos");

		boolean temPermissaoAtualizarDebitosExecFiscal = fachada.verificarPermissaoEspecial(
						PermissaoEspecial.ATUALIZAR_DEBITOS_EXECUCAO_FISCAL, this.getUsuarioLogado(httpServletRequest));

		// verifica se veio da tela de atualizacao de guia de pagamento
		if(Util.isVazioOuBranco(guiasPagamento)){
			guiasPagamento = (Collection<GuiaPagamentoPrestacaoHelper>) sessao.getAttribute("colecaoGuiasPrestacoes");
		}

		// // Verifica se a guia pode ser cancelada
		// Collection<String> colecao = fachada
		// .pesquisarTipoFinanciamentoDebitoNaoPermiteCancelarGuiaPagamento(((GuiaPagamentoPrestacaoHelper)
		// Util
		// .retonarObjetoDeColecao(guiasPagamento)).getIdGuiaPagamento());

		Collection<String> colecao = null;
		GuiaPagamentoPrestacaoHelper guiaPagamentoHelper = (GuiaPagamentoPrestacaoHelper) Util.retonarObjetoDeColecao(guiasPagamento);
		if(guiaPagamentoHelper != null && guiaPagamentoHelper.getIdGuiaPagamento() != null){
			// Verifica se a guia pode ser cancelada
			colecao = fachada.pesquisarTipoFinanciamentoDebitoNaoPermiteCancelarGuiaPagamento(guiaPagamentoHelper.getIdGuiaPagamento());
		}

		if(!Util.isVazioOrNulo(colecao)){
			String parametroMensagem = "";
			for(String tipoFinanciamento : colecao){
				if(parametroMensagem.equals("")){
					parametroMensagem = tipoFinanciamento;
				}else{
					parametroMensagem = ", " + tipoFinanciamento;
				}
			}
			throw new ActionServletException("atencao.guia_nao_pode_ser_cancelada", parametroMensagem);
		}

		AtualizarGuiaPagamentoActionForm atualizarGuiaPagamentoActionForm = (AtualizarGuiaPagamentoActionForm) actionForm;

		String[] registrosRemocao = atualizarGuiaPagamentoActionForm.getIdRegistrosRemocao();
		String idImovel = atualizarGuiaPagamentoActionForm.getIdImovel();
		String idCliente = atualizarGuiaPagamentoActionForm.getCodigoCliente();

		// [FS0010] � Verificar possibilidade de cancelamento da presta��o da guia

		if(!Util.isVazioOrNulo(registrosRemocao)){
			StringBuffer parametroMensagem = new StringBuffer();
			StringBuffer parametroPrestacaoExecucaoFiscal = new StringBuffer();

			Integer idGuiaPagamentoAux = null;
			Short numeroPrestacaoAux = null;
			String registroAux = null;
			Short idOcorrenciaHistoricoAux = null;

			for(String registro : registrosRemocao){
				for(GuiaPagamentoPrestacaoHelper guiaPagamentoPrestacaoHelper : (Collection<GuiaPagamentoPrestacaoHelper>) guiasPagamento){
					idGuiaPagamentoAux = guiaPagamentoPrestacaoHelper.getIdGuiaPagamento();
					numeroPrestacaoAux = guiaPagamentoPrestacaoHelper.getNumeroPrestacao();

					if(idGuiaPagamentoAux != null && numeroPrestacaoAux != null){
						registroAux = idGuiaPagamentoAux.toString() + numeroPrestacaoAux.toString();
					}

					if(registro.equals(registroAux)){
						idOcorrenciaHistoricoAux = guiaPagamentoPrestacaoHelper.getIdOcorrenciaHistorico();

						if(idOcorrenciaHistoricoAux != null){
							parametroMensagem.append(idGuiaPagamentoAux);
							parametroMensagem.append("/");
							parametroMensagem.append(numeroPrestacaoAux);
							parametroMensagem.append(", ");
						}

						/*
						 * [UC0188] Manter Guia de Pagamento
						 * [SB0011] Verificar Exist�ncia de Conta em Execu��o Fiscal
						 */
						FiltroGuiaPagamentoPrestacao filtroGuiaPagamentoPrestacao = new FiltroGuiaPagamentoPrestacao();
						filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(
										FiltroGuiaPagamentoPrestacao.GUIA_PAGAMENTO_ID, idGuiaPagamentoAux));
						filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacao.NUMERO_PRESTACAO,
										numeroPrestacaoAux));
						filtroGuiaPagamentoPrestacao.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoPrestacao.DEBITO_TIPO);

						Collection<GuiaPagamentoPrestacao> colecaoGuiaPagamentoPrestacao = fachada.pesquisar(filtroGuiaPagamentoPrestacao,
										GuiaPagamentoPrestacao.class.getName());

						GuiaPagamentoValoresHelper guiaPagamentoValores = new GuiaPagamentoValoresHelper();
						guiaPagamentoValores.setGuiaPagamentoPrestacoes(new HashSet<GuiaPagamentoPrestacao>(colecaoGuiaPagamentoPrestacao));
						
						Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores = new ArrayList<GuiaPagamentoValoresHelper>();
						colecaoGuiaPagamentoValores.add(guiaPagamentoValores);
						
						
						if(fachada.verificarExecucaoFiscal(null, colecaoGuiaPagamentoValores, null)
										&& !temPermissaoAtualizarDebitosExecFiscal){
							parametroPrestacaoExecucaoFiscal.append(idGuiaPagamentoAux);
							parametroPrestacaoExecucaoFiscal.append("/");
							parametroPrestacaoExecucaoFiscal.append(numeroPrestacaoAux);
							parametroPrestacaoExecucaoFiscal.append(", ");						
						}

						break;
					}

				}
			}

			String parametroMensagemStr = parametroMensagem.toString();

			if(!Util.isVazioOuBranco(parametroMensagemStr)){
				parametroMensagemStr = parametroMensagemStr.substring(0, parametroMensagemStr.length() - 2);

				throw new ActionServletException("atencao.guia.prestacao.no.historico", parametroMensagemStr);
			}

			String parametroMensagemExecFiscal = parametroPrestacaoExecucaoFiscal.toString();

			if(!Util.isVazioOuBranco(parametroMensagemExecFiscal)){
				parametroMensagemExecFiscal = parametroMensagemExecFiscal.substring(0, parametroMensagemExecFiscal.length() - 2);

				throw new ActionServletException("atencao.guia.prestacao.debito.execucao.fiscal", usuario.getNomeUsuario().toString(),
								parametroMensagemExecFiscal);
			}
		}

		/*
		 * GuiaPagamento guiaPagamento = new GuiaPagamento();
		 * Cliente cliente = new Cliente();
		 * if (idCliente != null && !idCliente.equals("")) {
		 * cliente.setId(new Integer(idCliente));
		 * }
		 * guiaPagamento.setCliente(cliente);
		 * Imovel imovel = new Imovel();
		 * ImovelCobrancaSituacao imovelCobrancaSituacao = null;
		 * if (idImovel != null && !idImovel.equals("")) {
		 * imovel.setId(new Integer(idImovel));
		 * imovelCobrancaSituacao = (ImovelCobrancaSituacao) sessao
		 * .getAttribute("imovelCobrancaSituacao");
		 * }
		 * guiaPagamento.setImovel(imovel);
		 */

		/**
		 * alterado por pedro alexandre dia 20/11/2006
		 * Recupera o usu�rio logado para passar no met�do de inserir guia de pagamento
		 * para verificar se o usu�rio tem abrang�ncia para inserir a guia de pagamento
		 * para o im�vel caso a guia de pagamentoseja para o im�vel.
		 */
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		// Monta a colecao com as guias contempladas no checklist
		Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiaPagamentoPrestacaoValidacao = new ArrayList<GuiaPagamentoPrestacaoHelper>();
		Collection<String> listaArray = Arrays.asList(registrosRemocao);
		for(GuiaPagamentoPrestacaoHelper guiaPagamentoPrestacaoHelper : (Collection<GuiaPagamentoPrestacaoHelper>) guiasPagamento){
			if(listaArray.contains(guiaPagamentoPrestacaoHelper.getId().toString())){
				colecaoGuiaPagamentoPrestacaoValidacao.add(guiaPagamentoPrestacaoHelper);
			}
		}
		// [SB0005] � Validar autoriza��o de acesso a presta��o da guia de im�vel
		if(!Util.isVazioOuBranco(idImovel)){
			fachada.validarAutorizacaoAcessoPrestacaoGuiaImovel(usuarioLogado, Integer.valueOf(idImovel),
							colecaoGuiaPagamentoPrestacaoValidacao);
		}

		fachada.manterGuiaPagamento(guiasPagamento, registrosRemocao, usuarioLogado);
		// fachada.manterGuiaPagamento(guiaPagamento, guiasPagamento,registrosRemocao,
		// imovelCobrancaSituacao);

		sessao.removeAttribute("imovelCobrancaSituacao");
		sessao.removeAttribute("guiasPagamentos");
		sessao.removeAttribute("AtualizarGuiaPagamentoActionForm");

		if(idImovel != null && !idImovel.equals("")){

			if(atualizarGuiaPagamentoActionForm.getIdRegistroAtendimento() != null
							&& !atualizarGuiaPagamentoActionForm.getIdRegistroAtendimento().equals("")){
				montarPaginaSucesso(httpServletRequest, registrosRemocao.length + " Parcelas de Guia(s) de Pagamento do im�vel " + idImovel
								+ " cancelada(s) com sucesso.", "Realizar outro Cancelamento de Guia de Pagamento",
								"exibirManterGuiaPagamentoAction.do?menu=sim",
								"filtrarRegistroAtendimentoAction.do?menuPrincipal=sim&menu=sim&idRA="
												+ atualizarGuiaPagamentoActionForm.getIdRegistroAtendimento(),
 "Encerrar RA "
												+ atualizarGuiaPagamentoActionForm.getIdRegistroAtendimento()
												+ " vinculado a Guia de Pagamento.");
			}else{
				montarPaginaSucesso(httpServletRequest, registrosRemocao.length + " Parcelas de Guia(s) de Pagamento do im�vel " + idImovel
								+ " cancelada(s) com sucesso.", "Realizar outro Cancelamento de Guia de Pagamento",
								"exibirManterGuiaPagamentoAction.do?menu=sim");
			}

		}

		if(idCliente != null && !idCliente.equals("")){

			if(atualizarGuiaPagamentoActionForm.getIdRegistroAtendimento() != null
							&& !atualizarGuiaPagamentoActionForm.getIdRegistroAtendimento().equals("")){
				montarPaginaSucesso(httpServletRequest, registrosRemocao.length + " Parcelas de Guia(s) de Pagamento do cliente "
								+ idCliente + " cancelada(s) com sucesso.", "Realizar outro Cancelamento de Guia de Pagamento",
								"exibirManterGuiaPagamentoAction.do?menu=sim",
								"filtrarRegistroAtendimentoAction.do?menuPrincipal=sim&menu=sim&idRA="
												+ atualizarGuiaPagamentoActionForm.getIdRegistroAtendimento(),
 "Encerrar RA "
												+ atualizarGuiaPagamentoActionForm.getIdRegistroAtendimento()
												+ " vinculado a Guia de Pagamento.");

			}else{
				montarPaginaSucesso(httpServletRequest, registrosRemocao.length + " Parcelas de Guia(s) de Pagamento do cliente "
								+ idCliente + " cancelada(s) com sucesso.", "Realizar outro Cancelamento de Guia de Pagamento",
								"exibirManterGuiaPagamentoAction.do?menu=sim");
			}

		}

		return retorno;
	}
}
