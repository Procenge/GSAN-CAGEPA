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

import gcom.fachada.Fachada;
import gcom.faturamento.conta.ContaMotivoCancelamento;
import gcom.faturamento.conta.FiltroContaMotivoCancelamento;
import gcom.faturamento.conta.FiltroMotivoCancelamentoConta;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirCancelarConjuntoContaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirCancelarConjuntoConta");

		HttpSession sessao = httpServletRequest.getSession(false);

		// Inst�ncia do formul�rio que est� sendo utilizado
		CancelarContaActionForm cancelarContaActionForm = (CancelarContaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		if(sessao.getAttribute("retornoArquivo") == null){
			Integer anoMes = null;
			if(httpServletRequest.getParameter("mesAno") != null){
				anoMes = Util.formatarMesAnoComBarraParaAnoMes(httpServletRequest.getParameter("mesAno"));
				sessao.setAttribute("anoMes", anoMes);
			}

			Integer anoMesFim = null;
			if(httpServletRequest.getParameter("mesAnoFim") != null){
				anoMesFim = Util.formatarMesAnoComBarraParaAnoMes(httpServletRequest.getParameter("mesAnoFim"));
				sessao.setAttribute("anoMesFim", anoMesFim);
			}
		}

		Date dataVencimentoContaInicio = null;
		Date dataVencimentoContaFim = null;
		Integer idGrupoFaturamento = null;

		// Limpa o indicador de obrigatoriedade da exibi��o do campo RA
		if(sessao.getAttribute("indicadorObrigatoriedadeRA") != null){
			sessao.removeAttribute("indicadorObrigatoriedadeRA");
		}

		// Limpa o motivo de cancelamento da conta selecionado
		if(sessao.getAttribute("colecaoContaMotivoCancelamentoSelecionada") != null){
			sessao.removeAttribute("colecaoContaMotivoCancelamentoSelecionada");
		}

		if(httpServletRequest.getParameter("dataVencimentoContaInicial") != null){

			dataVencimentoContaInicio = Util.converteStringParaDate(httpServletRequest.getParameter("dataVencimentoContaInicial"));
			sessao.setAttribute("dataVencimentoContaInicial", dataVencimentoContaInicio);
		}

		if(httpServletRequest.getParameter("dataVencimentoContaFinal") != null){

			dataVencimentoContaFim = Util.converteStringParaDate(httpServletRequest.getParameter("dataVencimentoContaFinal"));
			sessao.setAttribute("dataVencimentoContaFinal", dataVencimentoContaFim);
		}

		if(httpServletRequest.getParameter("idGrupoFaturamento") != null){

			idGrupoFaturamento = Integer.valueOf(httpServletRequest.getParameter("idGrupoFaturamento"));
			sessao.setAttribute("idGrupoFaturamento", idGrupoFaturamento);
		}

		// Carregar: Lista dos motivos de cancelamento da conta
		if(sessao.getAttribute("colecaoMotivoCancelamentoConta") == null){

			FiltroMotivoCancelamentoConta filtroMotivoCancelamentoConta = new FiltroMotivoCancelamentoConta(
							FiltroMotivoCancelamentoConta.DESCRICAO_MOTIVO_CANCELAMENTO_CONTA);

			filtroMotivoCancelamentoConta.adicionarParametro(new ParametroSimples(FiltroMotivoCancelamentoConta.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoMotivoCancelamentoConta = fachada.pesquisar(filtroMotivoCancelamentoConta, ContaMotivoCancelamento.class
							.getName());

			if(colecaoMotivoCancelamentoConta == null || colecaoMotivoCancelamentoConta.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "MOTIVO_CANCELAMENTO_CONTA");
			}
			// Disponibiliza a cole��o pela sess�o
			sessao.setAttribute("colecaoMotivoCancelamentoConta", colecaoMotivoCancelamentoConta);
		}

		// **************************************

		// Motivo Cancelamento Conta selecinado pelo usu�rio
		if(!Util.isVazioOuBranco(cancelarContaActionForm.getMotivoCancelamentoContaID())){

			FiltroContaMotivoCancelamento filtroContaMotivoCancelamento = new FiltroContaMotivoCancelamento();
			filtroContaMotivoCancelamento.adicionarParametro(new ParametroSimples(FiltroContaMotivoCancelamento.CODIGO,
							cancelarContaActionForm.getMotivoCancelamentoContaID()));

			Collection colecaoContaMotivoCancelamentoSelecionada = fachada.pesquisar(filtroContaMotivoCancelamento,
							ContaMotivoCancelamento.class.getName());

			ContaMotivoCancelamento contaMotivoCancelamentoSelecionada = (ContaMotivoCancelamento) Util
							.retonarObjetoDeColecao(colecaoContaMotivoCancelamentoSelecionada);

			Boolean indicadorObrigatoriedadeRA = Boolean.FALSE;

			if(!Util.isVazioOuBranco(contaMotivoCancelamentoSelecionada)){
				// 2. Caso para o motivo do cancelamento selecionado
				// seja necess�rio informar o registro de atendimento
				if(contaMotivoCancelamentoSelecionada.getIndicadorRegistroAtendimento().equals(ConstantesSistema.SIM)){
					indicadorObrigatoriedadeRA = Boolean.TRUE;

					// Coloca o indicador na sess�o
					sessao.setAttribute("indicadorObrigatoriedadeRA", indicadorObrigatoriedadeRA);
				}
				// Coloca o motivo do cancelamento da conta na sess�o
				sessao.setAttribute("colecaoContaMotivoCancelamentoSelecionada", colecaoContaMotivoCancelamentoSelecionada);
			}
		}

		// // Armazena as contas selecionadas
		// Collection<Conta> colecaoContasSelecionadas = new ArrayList<Conta>();
		//
		// // Carregando contas selecionadas
		// String contaSelected = httpServletRequest.getParameter("idsContasSelecionadas");
		//
		// if(!Util.isVazioOuBranco(contaSelected)){
		// // Contas selecionadas pelo usu�rio
		// String[] arrayIdentificadores = contaSelected.split(",");
		//
		// for(int i = 0; i < arrayIdentificadores.length; i++){
		//
		// String dadosConta = arrayIdentificadores[i];
		// String[] idContaArray = dadosConta.split("-");
		// Integer idConta = new Integer(idContaArray[0]);
		//
		// // [FS0021] Verificar situa��o da conta
		// FiltroConta filtroConta = new FiltroConta();
		// filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.IMOVEL);
		// filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.LOCALIDADE);
		// filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.DEBITO_CREDITO_SITUACAO_ATUAL);
		//
		// filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, idConta));
		// Collection colecaoContas = fachada.pesquisar(filtroConta, Conta.class.getName());
		// Conta contaSelecao = (Conta) colecaoContas.iterator().next();
		//
		// if(contaSelecao != null){
		// if(!fachada.verificarSituacaoContaPermitida(contaSelecao)){
		// throw new ActionServletException("atencao.conta_em_situacao_nao_permitida", contaSelecao
		// .getDebitoCreditoSituacaoAtual().getDescricaoDebitoCreditoSituacao(), "a��o");
		// }
		// }
		//
		// colecaoContasSelecionadas.add(contaSelecao);
		// }
		//
		// sessao.setAttribute("colecaoContasSelecionadas", colecaoContasSelecionadas);
		//
		// }

		// **************************************

		return retorno;
	}

}
