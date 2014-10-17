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
 * Yara Taciane de Souza
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

package gcom.gui.cobranca.spcserasa;

import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.NegativadorMovimentoReg;
import gcom.cobranca.NegativadorMovimentoRegItem;
import gcom.gui.GcomAction;
import gcom.spcserasa.FiltroNegativadorMovimentoRegItem;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0674] Pesquisar Movimento Negativador
 * 
 * @author Yara Taciane
 * @date 27/12/2007
 */
public class ExibirConsultarNegativadorMovimentoRegItemPopupAction
				extends GcomAction {

	/**
	 * [UC0438] Este caso de uso efetua pesquisa de Movimento do Negativador
	 * 
	 * @author Yara Taciane
	 * @date 03/08/2006
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		HttpSession sessao = httpServletRequest.getSession(false);

		ActionForward retorno = actionMapping.findForward("negativadorMovimentoRegItemConsultarPopup");

		ConsultarNegativadorMovimentoRegItemPopupActionForm form = (ConsultarNegativadorMovimentoRegItemPopupActionForm) actionForm;

		NegativadorMovimentoReg negativadorMovimentoReg = (NegativadorMovimentoReg) sessao.getAttribute("negativadorMovimentoReg");

		if(negativadorMovimentoReg != null && !negativadorMovimentoReg.equals("")){

			form.setNegativador(negativadorMovimentoReg.getNegativadorMovimento().getNegativador().getCliente().getNome());
			// if(negativadorMovimentoReg.getImovel()!= null){
			// form.setMatriculaImovel(negativadorMovimentoReg.getImovel().getId().toString());
			// }
			// if(negativadorMovimentoReg.getImovel()!= null &&
			// negativadorMovimentoReg.getImovel().getInscricaoFormatada()!= null){
			// form.setInscricao(negativadorMovimentoReg.getImovel().getInscricaoFormatada());
			// }

			// situacao da ligacao da agua
			// situacao da ligacao esgoto

			FiltroNegativadorMovimentoRegItem filtroNegativadorMovimentoRegItem = new FiltroNegativadorMovimentoRegItem();
			filtroNegativadorMovimentoRegItem.adicionarParametro(new ParametroSimples(
							FiltroNegativadorMovimentoRegItem.NEGATIVADOR_MOVIMENTO_REG_ID, Integer
											.valueOf(negativadorMovimentoReg.getId())));

			filtroNegativadorMovimentoRegItem.adicionarParametro(new ParametroSimples(FiltroNegativadorMovimentoRegItem.DOCUMENTO_TIPO_ID,
							Integer.valueOf(DocumentoTipo.CONTA)));

			filtroNegativadorMovimentoRegItem.adicionarCaminhoParaCarregamentoEntidade("negativadorMovimentoReg");
			filtroNegativadorMovimentoRegItem.adicionarCaminhoParaCarregamentoEntidade("negativadorMovimentoReg.imovel");

			filtroNegativadorMovimentoRegItem.adicionarCaminhoParaCarregamentoEntidade("guiaPagamentoGeral");
			filtroNegativadorMovimentoRegItem.adicionarCaminhoParaCarregamentoEntidade("documentoTipo");
			filtroNegativadorMovimentoRegItem.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeral");
			filtroNegativadorMovimentoRegItem
							.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeral.debitoACobrar.debitoCreditoSituacaoAtual");
			filtroNegativadorMovimentoRegItem
							.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeral.debitoACobrarHistorico.debitoCreditoSituacaoAtual");
			filtroNegativadorMovimentoRegItem.adicionarCaminhoParaCarregamentoEntidade("contaGeral");
			filtroNegativadorMovimentoRegItem.adicionarCaminhoParaCarregamentoEntidade("contaGeral.conta");
			filtroNegativadorMovimentoRegItem.adicionarCaminhoParaCarregamentoEntidade("contaGeral.contaHistorico");
			filtroNegativadorMovimentoRegItem.adicionarCaminhoParaCarregamentoEntidade("cobrancaDebitoSituacao");
			filtroNegativadorMovimentoRegItem.adicionarCaminhoParaCarregamentoEntidade("cobrancaDebitoSituacaoAposExclusao");

			Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroNegativadorMovimentoRegItem,
							NegativadorMovimentoRegItem.class.getName());

			Collection<NegativadorMovimentoRegItem> collNegativadorMovimentoRegItem = (Collection) resultado.get("colecaoRetorno");

			sessao.setAttribute("collNegativadorMovimentoRegItem", collNegativadorMovimentoRegItem);

			// -----------------------------------------------------------------------------------------------
			FiltroNegativadorMovimentoRegItem filtroNMRIGuiaPagamento = new FiltroNegativadorMovimentoRegItem();

			filtroNMRIGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroNegativadorMovimentoRegItem.NEGATIVADOR_MOVIMENTO_REG_ID,
							negativadorMovimentoReg.getId()));
			filtroNMRIGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroNegativadorMovimentoRegItem.DOCUMENTO_TIPO_ID,
							DocumentoTipo.GUIA_PAGAMENTO));

			filtroNMRIGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("negativadorMovimentoReg");
			filtroNMRIGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("negativadorMovimentoReg.imovel");
			filtroNMRIGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("guiaPagamentoGeral");
			filtroNMRIGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("guiaPagamentoGeral.guiaPagamento");
			filtroNMRIGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("guiaPagamentoGeral.guiaPagamentoHistorico");
			filtroNMRIGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("guiaPagamentoGeral.guiaPagamento.debitoCreditoSituacaoAtual");
			filtroNMRIGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("documentoTipo");
			filtroNMRIGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeral");
			filtroNMRIGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeral.debitoACobrar.debitoCreditoSituacaoAtual");
			filtroNMRIGuiaPagamento
							.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeral.debitoACobrarHistorico.debitoCreditoSituacaoAtual");

			Map resultadoNMIGuiaPagamento = controlarPaginacao(httpServletRequest, retorno, filtroNMRIGuiaPagamento,
							NegativadorMovimentoRegItem.class.getName());

			Collection<NegativadorMovimentoRegItem> collNMIGuiaPagamento = (Collection) resultadoNMIGuiaPagamento.get("colecaoRetorno");

			sessao.setAttribute("collNegativadorMovimentoRegItem2", collNMIGuiaPagamento);

		}

		return retorno;
	}

}
