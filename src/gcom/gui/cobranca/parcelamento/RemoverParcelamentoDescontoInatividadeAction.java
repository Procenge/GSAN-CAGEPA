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

package gcom.gui.cobranca.parcelamento;

import gcom.cobranca.parcelamento.ParcelamentoDescontoAntiguidade;
import gcom.cobranca.parcelamento.ParcelamentoDescontoInatividade;
import gcom.gui.GcomAction;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action de remover um objeto do tipo ParcelamentoDescontoInatividade
 * da collectionParcelamentoDescontoInatividade
 * 
 * @author Vivianne Sousa
 * @created 09/05/2006
 */
public class RemoverParcelamentoDescontoInatividadeAction
				extends GcomAction {

	/**
	 * @author Vivianne Sousa
	 * @date 09/05/2006
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		HttpSession sessao = httpServletRequest.getSession(false);

		ActionForward retorno = actionMapping.findForward("atualizarPerfilRemoverParcelamentoDescontoInatividade");
		if(sessao.getAttribute("UseCase") != null && sessao.getAttribute("UseCase").equals("INSERIRPERFIL")){
			retorno = actionMapping.findForward("inserirPerfilRemoverParcelamentoDescontoInatividade");
		}

		String quantidadeMaximaMesesInatividade = httpServletRequest.getParameter("quantidadeMaximaMesesInat");

		if(quantidadeMaximaMesesInatividade != null && !quantidadeMaximaMesesInatividade.equalsIgnoreCase("")
						&& sessao.getAttribute("collectionParcelamentoDescontoInatividade") != null){

			Collection collectionParcelamentoDescontoInatividade = (Collection) sessao
							.getAttribute("collectionParcelamentoDescontoInatividade");

			Collection collectionParcelamentoDescontoInatividadeLinhaRemovidas = null;
			if(sessao.getAttribute("collectionParcelamentoDescontoInatividadeLinhaRemovidas") != null
							&& !sessao.getAttribute("collectionParcelamentoDescontoInatividadeLinhaRemovidas").equals("")){
				collectionParcelamentoDescontoInatividadeLinhaRemovidas = (Collection) sessao
								.getAttribute("collectionParcelamentoDescontoInatividadeLinhaRemovidas");
			}else{
				collectionParcelamentoDescontoInatividadeLinhaRemovidas = new ArrayList();
			}

			ParcelamentoDescontoInatividade parcelamentoDescontoInatividade = null;
			ParcelamentoDescontoInatividade parcelamentoDescontoInatividadeExcluir = null;
			Iterator iterator = collectionParcelamentoDescontoInatividade.iterator();

			while(iterator.hasNext()){
				parcelamentoDescontoInatividade = (ParcelamentoDescontoInatividade) iterator.next();

				// procura na cole��o o parcelamentoDescontoInatividade que tem a
				// quantidadeMaximaMesesInatividade selecionada
				if(parcelamentoDescontoInatividade.getQuantidadeMaximaMesesInatividade().equals(
								new Integer(quantidadeMaximaMesesInatividade))){
					parcelamentoDescontoInatividadeExcluir = parcelamentoDescontoInatividade;
					collectionParcelamentoDescontoInatividadeLinhaRemovidas.add(parcelamentoDescontoInatividade);
				}
			}

			collectionParcelamentoDescontoInatividade.remove(parcelamentoDescontoInatividadeExcluir);
			sessao.setAttribute("collectionParcelamentoDescontoInatividade", collectionParcelamentoDescontoInatividade);
			sessao.setAttribute("collectionParcelamentoDescontoInatividadeLinhaRemovidas",
							collectionParcelamentoDescontoInatividadeLinhaRemovidas);

		}

		atualizaColecoesNaSessao(sessao, httpServletRequest);
		return retorno;
	}

	private void atualizaColecoesNaSessao(HttpSession sessao, HttpServletRequest httpServletRequest){

		// collectionParcelamentoDescontoAntiguidade
		if(sessao.getAttribute("collectionParcelamentoDescontoAntiguidade") != null
						&& !sessao.getAttribute("collectionParcelamentoDescontoAntiguidade").equals("")){

			Collection collectionParcelamentoDescontoAntiguidade = (Collection) sessao
							.getAttribute("collectionParcelamentoDescontoAntiguidade");
			// cria as vari�veis para recuperar os par�metros do request e jogar
			// no objeto ParcelamentoDescontoAntiguidade
			String vlSemRestAntiguidade = null;
			String vlComRestAntiguidade = null;
			String vlDescontoAtivo = null;

			Iterator iteratorParcelamentoDescontoAntiguidade = collectionParcelamentoDescontoAntiguidade.iterator();

			while(iteratorParcelamentoDescontoAntiguidade.hasNext()){
				ParcelamentoDescontoAntiguidade parcelamentoDescontoAntiguidade = (ParcelamentoDescontoAntiguidade) iteratorParcelamentoDescontoAntiguidade
								.next();
				long valorTempo = parcelamentoDescontoAntiguidade.getUltimaAlteracao().getTime();
				vlSemRestAntiguidade = (String) httpServletRequest.getParameter("vlSemRestAntiguidade" + valorTempo);
				vlComRestAntiguidade = httpServletRequest.getParameter("vlComRestAntiguidade" + valorTempo);
				vlDescontoAtivo = httpServletRequest.getParameter("vlDescontoAtivo" + valorTempo);

				// inseri essas vari�veis no objeto ParcelamentoDescontoAntiguidade
				BigDecimal percentualDescontoSemRestabelecimentoAntiguidade = null;
				if(vlSemRestAntiguidade != null && !vlSemRestAntiguidade.equals("")){
					percentualDescontoSemRestabelecimentoAntiguidade = Util.formatarMoedaRealparaBigDecimal(vlSemRestAntiguidade);
					parcelamentoDescontoAntiguidade
									.setPercentualDescontoSemRestabelecimento(percentualDescontoSemRestabelecimentoAntiguidade);
				}

				BigDecimal percentualDescontoComRestabelecimentoAntiguidade = null;
				if(vlComRestAntiguidade != null && !vlComRestAntiguidade.equals("")){
					percentualDescontoComRestabelecimentoAntiguidade = Util.formatarMoedaRealparaBigDecimal(vlComRestAntiguidade);
					parcelamentoDescontoAntiguidade
									.setPercentualDescontoComRestabelecimento(percentualDescontoComRestabelecimentoAntiguidade);
				}

				BigDecimal percentualDescontoAtivoAntiguidade = null;
				if(vlDescontoAtivo != null && !vlDescontoAtivo.equals("")){
					percentualDescontoAtivoAntiguidade = Util.formatarMoedaRealparaBigDecimal(vlDescontoAtivo);
					parcelamentoDescontoAntiguidade.setPercentualDescontoAtivo(percentualDescontoAtivoAntiguidade);
				}

			}

		}

		// collectionParcelamentoDescontoInatividade
		if(sessao.getAttribute("collectionParcelamentoDescontoInatividade") != null
						&& !sessao.getAttribute("collectionParcelamentoDescontoInatividade").equals("")){

			Collection collectionParcelamentoDescontoInatividade = (Collection) sessao
							.getAttribute("collectionParcelamentoDescontoInatividade");
			// cria as vari�veis para recuperar os par�metros do request e jogar
			// no objeto ParcelamentoDescontoInatividade
			String vlSemRestInatividade = null;
			String vlJurosMoraSemRestInatividade = null;
			String vlMultaSemRestInatividade = null;
			String vlComRestInatividade = null;
			String vlJurosMoraComRestInatividade = null;
			String vlMultaComRestInatividade = null;

			Iterator iteratorParcelamentoDescontoInatividade = collectionParcelamentoDescontoInatividade.iterator();

			while(iteratorParcelamentoDescontoInatividade.hasNext()){
				ParcelamentoDescontoInatividade parcelamentoDescontoInatividade = (ParcelamentoDescontoInatividade) iteratorParcelamentoDescontoInatividade
								.next();
				long valorTempo = parcelamentoDescontoInatividade.getUltimaAlteracao().getTime();
				vlSemRestInatividade = (String) httpServletRequest.getParameter("vlSemRestInatividade" + valorTempo);
				vlJurosMoraSemRestInatividade = (String) httpServletRequest.getParameter("vlJurosMoraSemRestInatividade" + valorTempo);
				vlMultaSemRestInatividade = (String) httpServletRequest.getParameter("vlMultaSemRestInatividade" + valorTempo);
				vlComRestInatividade = httpServletRequest.getParameter("vlComRestInatividade" + valorTempo);
				vlJurosMoraComRestInatividade = (String) httpServletRequest.getParameter("vlJurosMoraComRestInatividade" + valorTempo);
				vlMultaComRestInatividade = (String) httpServletRequest.getParameter("vlMultaComRestInatividade" + valorTempo);

				// insere essas vari�veis no objeto ParcelamentoDescontoInatividade
				BigDecimal percentualDescontoSemRestabelecimentoInatividade = null;
				if(vlSemRestInatividade != null && !vlSemRestInatividade.equals("")){
					percentualDescontoSemRestabelecimentoInatividade = Util.formatarMoedaRealparaBigDecimal(vlSemRestInatividade);
				}

				BigDecimal percentualDescontoJurosMoraSemRestabelecimentoInatividade = null;
				if(vlJurosMoraSemRestInatividade != null && !vlJurosMoraSemRestInatividade.equals("")){
					percentualDescontoJurosMoraSemRestabelecimentoInatividade = Util
									.formatarMoedaRealparaBigDecimal(vlJurosMoraSemRestInatividade);
				}

				BigDecimal percentualDescontoMultaSemRestabelecimentoInatividade = null;
				if(vlMultaSemRestInatividade != null && !vlMultaSemRestInatividade.equals("")){
					percentualDescontoMultaSemRestabelecimentoInatividade = Util.formatarMoedaRealparaBigDecimal(vlMultaSemRestInatividade);
				}

				BigDecimal percentualDescontoComRestabelecimentoInatividade = null;
				if(vlComRestInatividade != null && !vlComRestInatividade.equals("")){
					percentualDescontoComRestabelecimentoInatividade = Util.formatarMoedaRealparaBigDecimal(vlComRestInatividade);
				}

				BigDecimal percentualDescontoJurosMoraComRestabelecimentoInatividade = null;
				if(vlJurosMoraComRestInatividade != null && !vlJurosMoraComRestInatividade.equals("")){
					percentualDescontoJurosMoraComRestabelecimentoInatividade = Util
									.formatarMoedaRealparaBigDecimal(vlJurosMoraComRestInatividade);
				}

				BigDecimal percentualDescontoMultaComRestabelecimentoInatividade = null;
				if(vlMultaComRestInatividade != null && !vlMultaComRestInatividade.equals("")){
					percentualDescontoMultaComRestabelecimentoInatividade = Util.formatarMoedaRealparaBigDecimal(vlMultaComRestInatividade);
				}

				parcelamentoDescontoInatividade.setPercentualDescontoSemRestabelecimento(percentualDescontoSemRestabelecimentoInatividade);
				parcelamentoDescontoInatividade
								.setPercentualDescontoJurosMoraSemRestabelecimento(percentualDescontoJurosMoraSemRestabelecimentoInatividade);
				parcelamentoDescontoInatividade
								.setPercentualDescontoMultaSemRestabelecimento(percentualDescontoMultaSemRestabelecimentoInatividade);
				parcelamentoDescontoInatividade.setPercentualDescontoComRestabelecimento(percentualDescontoComRestabelecimentoInatividade);
				parcelamentoDescontoInatividade
								.setPercentualDescontoJurosMoraComRestabelecimento(percentualDescontoJurosMoraComRestabelecimentoInatividade);
				parcelamentoDescontoInatividade
								.setPercentualDescontoMultaComRestabelecimento(percentualDescontoMultaComRestabelecimentoInatividade);
			}
		}
	}

}