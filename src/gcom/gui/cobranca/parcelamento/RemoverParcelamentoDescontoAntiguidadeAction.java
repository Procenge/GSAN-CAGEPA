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
 * Action de remover um objeto do tipo ParcelamentoDescontoAntiguidade
 * da collectionParcelamentoDescontoAntiguidade
 * 
 * @author Vivianne Sousa
 * @created 09/05/2006
 */
public class RemoverParcelamentoDescontoAntiguidadeAction
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

		ActionForward retorno = actionMapping.findForward("atualizarPerfilRemoverParcelamentoDescontoAntiguidade");
		if(sessao.getAttribute("UseCase") != null && sessao.getAttribute("UseCase").equals("INSERIRPERFIL")){
			retorno = actionMapping.findForward("inserirPerfilRemoverParcelamentoDescontoAntiguidade");
		}

		String quantidadeMinimaMesesDebito = httpServletRequest.getParameter("quantidadeMinimaMesesDeb");

		if(quantidadeMinimaMesesDebito != null && !quantidadeMinimaMesesDebito.equalsIgnoreCase("")
						&& sessao.getAttribute("collectionParcelamentoDescontoAntiguidade") != null){

			Collection collectionParcelamentoDescontoAntiguidade = (Collection) sessao
							.getAttribute("collectionParcelamentoDescontoAntiguidade");

			Collection collectionParcelamentoDescontoAntiguidadeLinhaRemovidas = null;
			if(sessao.getAttribute("collectionParcelamentoDescontoAntiguidadeLinhaRemovidas") != null
							&& !sessao.getAttribute("collectionParcelamentoDescontoAntiguidadeLinhaRemovidas").equals("")){
				collectionParcelamentoDescontoAntiguidadeLinhaRemovidas = (Collection) sessao
								.getAttribute("collectionParcelamentoDescontoAntiguidadeLinhaRemovidas");
			}else{
				collectionParcelamentoDescontoAntiguidadeLinhaRemovidas = new ArrayList();
			}

			ParcelamentoDescontoAntiguidade parcelamentoDescontoAntiguidade = null;
			ParcelamentoDescontoAntiguidade parcelamentoDescontoAntiguidadeExcluir = null;
			Iterator iterator = collectionParcelamentoDescontoAntiguidade.iterator();

			while(iterator.hasNext()){
				parcelamentoDescontoAntiguidade = (ParcelamentoDescontoAntiguidade) iterator.next();

				// procura na coleção o parcelamentoDescontoAntiguidade que tem a
				// quantidadeMinimaMesesDebito selecionada
				if(parcelamentoDescontoAntiguidade.getQuantidadeMinimaMesesDebito().equals(new Integer(quantidadeMinimaMesesDebito))){
					parcelamentoDescontoAntiguidadeExcluir = parcelamentoDescontoAntiguidade;
					collectionParcelamentoDescontoAntiguidadeLinhaRemovidas.add(parcelamentoDescontoAntiguidade);

				}
			}

			collectionParcelamentoDescontoAntiguidade.remove(parcelamentoDescontoAntiguidadeExcluir);
			sessao.setAttribute("collectionParcelamentoDescontoAntiguidade", collectionParcelamentoDescontoAntiguidade);
			sessao.setAttribute("collectionParcelamentoDescontoAntiguidadeLinhaRemovidas",
							collectionParcelamentoDescontoAntiguidadeLinhaRemovidas);
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
			// cria as variáveis para recuperar os parâmetros do request e jogar
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

				// inseri essas variáveis no objeto ParcelamentoDescontoAntiguidade
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
			// cria as variáveis para recuperar os parâmetros do request e jogar
			// no objeto ParcelamentoDescontoInatividade
			String vlSemRestInatividade = null;
			String vlComRestInatividade = null;

			Iterator iteratorParcelamentoDescontoInatividade = collectionParcelamentoDescontoInatividade.iterator();

			while(iteratorParcelamentoDescontoInatividade.hasNext()){
				ParcelamentoDescontoInatividade parcelamentoDescontoInatividade = (ParcelamentoDescontoInatividade) iteratorParcelamentoDescontoInatividade
								.next();
				long valorTempo = parcelamentoDescontoInatividade.getUltimaAlteracao().getTime();
				vlSemRestInatividade = (String) httpServletRequest.getParameter("vlSemRestInatividade" + valorTempo);
				vlComRestInatividade = httpServletRequest.getParameter("vlComRestInatividade" + valorTempo);

				// insere essas variáveis no objeto ParcelamentoDescontoInatividade
				BigDecimal percentualDescontoSemRestabelecimentoInatividade = null;
				if(vlSemRestInatividade != null && !vlSemRestInatividade.equals("")){
					percentualDescontoSemRestabelecimentoInatividade = Util.formatarMoedaRealparaBigDecimal(vlSemRestInatividade);
				}

				BigDecimal percentualDescontoComRestabelecimentoInatividade = null;
				if(vlComRestInatividade != null && !vlComRestInatividade.equals("")){
					percentualDescontoComRestabelecimentoInatividade = Util.formatarMoedaRealparaBigDecimal(vlComRestInatividade);
				}

				parcelamentoDescontoInatividade.setPercentualDescontoSemRestabelecimento(percentualDescontoSemRestabelecimentoInatividade);
				parcelamentoDescontoInatividade.setPercentualDescontoComRestabelecimento(percentualDescontoComRestabelecimentoInatividade);
			}
		}
	}
}
