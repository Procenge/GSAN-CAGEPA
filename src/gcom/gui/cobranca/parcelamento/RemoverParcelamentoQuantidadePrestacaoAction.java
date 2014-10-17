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

import gcom.cobranca.parcelamento.ParcelamentoQuantidadePrestacao;
import gcom.cobranca.parcelamento.ParcelamentoQuantidadePrestacaoHelper;
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
 * Action de remover um objeto do tipo ParcelamentoQuantidadePrestacao
 * da collectionParcelamentoQuantidadePrestacao
 * 
 * @author Vivianne Sousa
 * @created 10/05/2006
 */
public class RemoverParcelamentoQuantidadePrestacaoAction
				extends GcomAction {

	/**
	 * @author Vivianne Sousa
	 * @date 10/05/2006
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		HttpSession sessao = httpServletRequest.getSession(false);

		// ActionForward retorno =
		// actionMapping.findForward("atualizarPerfilRemoverParcelamentoQuantidadePrestacao");
		// if (sessao.getAttribute("UseCase")!= null &&
		// sessao.getAttribute("UseCase").equals("INSERIRPERFIL")){
		ActionForward retorno = actionMapping.findForward("inserirPerfilRemoverParcelamentoQuantidadePrestacao");
		// }

		atualizaColecaoNaSessao(sessao, httpServletRequest);

		String quantidadeMaximaPrestacao = httpServletRequest.getParameter("qtdeMaxPrestacao");

		if(quantidadeMaximaPrestacao != null && !quantidadeMaximaPrestacao.equalsIgnoreCase("")
						&& sessao.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper") != null){

			Collection collectionParcelamentoQuantidadePrestacaoHelper = (Collection) sessao
							.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper");

			// collectionParcelamentoQuantidadePrestacaoHelperLinhaRemovidas
			Collection collectionParcelamentoQuantidadePrestacaoHelperLinhaRemovidas = null;
			if(sessao.getAttribute("collectionParcelamentoQuantidadePrestacaoHelperLinhaRemovidas") != null
							&& !sessao.getAttribute("collectionParcelamentoQuantidadePrestacaoHelperLinhaRemovidas").equals("")){
				collectionParcelamentoQuantidadePrestacaoHelperLinhaRemovidas = (Collection) sessao
								.getAttribute("collectionParcelamentoQuantidadePrestacaoHelperLinhaRemovidas");
			}else{
				collectionParcelamentoQuantidadePrestacaoHelperLinhaRemovidas = new ArrayList();
			}

			ParcelamentoQuantidadePrestacaoHelper parcelamentoQuantidadePrestacaoHelper = null;
			ParcelamentoQuantidadePrestacaoHelper parcelamentoQuantidadePrestacaoHelperExcluir = null;
			Iterator iterator = collectionParcelamentoQuantidadePrestacaoHelper.iterator();

			while(iterator.hasNext()){
				parcelamentoQuantidadePrestacaoHelper = (ParcelamentoQuantidadePrestacaoHelper) iterator.next();

				// procura na coleção o parcelamentoQuantidadePrestacao que tem a
				// quantidadeMaximaPrestacao selecionada
				if(parcelamentoQuantidadePrestacaoHelper.getParcelamentoQuantidadePrestacao().getQuantidadeMaximaPrestacao().toString()
								.equals(quantidadeMaximaPrestacao)){
					parcelamentoQuantidadePrestacaoHelperExcluir = parcelamentoQuantidadePrestacaoHelper;
					collectionParcelamentoQuantidadePrestacaoHelperLinhaRemovidas.add(parcelamentoQuantidadePrestacaoHelper);
				}
			}

			collectionParcelamentoQuantidadePrestacaoHelper.remove(parcelamentoQuantidadePrestacaoHelperExcluir);
			sessao.setAttribute("collectionParcelamentoQuantidadePrestacaoHelper", collectionParcelamentoQuantidadePrestacaoHelper);
			sessao.setAttribute("collectionParcelamentoQuantidadePrestacaoHelperLinhaRemovidas",
							collectionParcelamentoQuantidadePrestacaoHelperLinhaRemovidas);

		}

		return retorno;
	}

	private void atualizaColecaoNaSessao(HttpSession sessao, HttpServletRequest httpServletRequest){

		// collectionParcelamentoQuantidadePrestacaoHelper
		if(sessao.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper") != null
						&& !sessao.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper").equals("")){

			Collection collectionParcelamentoQuantidadePrestacaoHelper = (Collection) sessao
							.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper");
			// cria as variáveis para recuperar os parâmetros do request e jogar
			// no objeto ParcelamentoQuantidadePrestacao

			Iterator iterator = collectionParcelamentoQuantidadePrestacaoHelper.iterator();

			while(iterator.hasNext()){
				ParcelamentoQuantidadePrestacaoHelper parcelamentoQuantidadePrestacaoHelper = (ParcelamentoQuantidadePrestacaoHelper) iterator
								.next();

				ParcelamentoQuantidadePrestacao parcelamentoQuantidadePrestacao = parcelamentoQuantidadePrestacaoHelper
								.getParcelamentoQuantidadePrestacao();

				long valorTempo = parcelamentoQuantidadePrestacao.getUltimaAlteracao().getTime();

				String indicadorEntradaParcelamento = (String) httpServletRequest.getParameter("indicadorEntradaParcelamento" + valorTempo);
				String vlMinimoEntrada = (String) httpServletRequest.getParameter("vlMinimoEntrada" + valorTempo);
				String txJuros = (String) httpServletRequest.getParameter("txJuros" + valorTempo);
				String percMinEntrada = (String) httpServletRequest.getParameter("percMinEntrada" + valorTempo);
				String percTarMinImovel = (String) httpServletRequest.getParameter("percTarMinImovel" + valorTempo);
				String percVlReparcelado = (String) httpServletRequest.getParameter("percVlReparcelado" + valorTempo);
				String numeroMesesEntreParcelasStr = (String) httpServletRequest.getParameter("numeroMesesEntreParcelas" + valorTempo);
				String numeroParcelasALancarStr = (String) httpServletRequest.getParameter("numeroParcelasALancar" + valorTempo);
				String numeroMesesInicioCobrancaStr = (String) httpServletRequest.getParameter("numeroMesesInicioCobranca" + valorTempo);
				String numeroDiasVencimentoDaEntradaStr = (String) httpServletRequest.getParameter("numeroDiasVencimentoDaEntrada"
								+ valorTempo);

				// insere essas variáveis no objeto ParcelamentoQuantidadePrestacao
				Short indicadorEntradaParcelamentoShort = null;
				if(!Util.isVazioOuBranco(indicadorEntradaParcelamento)){
					indicadorEntradaParcelamentoShort = Short.valueOf(indicadorEntradaParcelamento);
				}

				BigDecimal vlMinimoEntradaBigDecimal = null;
				if(!Util.isVazioOuBranco(vlMinimoEntrada)){
					vlMinimoEntradaBigDecimal = Util.formatarMoedaRealparaBigDecimal(vlMinimoEntrada);
				}

				BigDecimal taxaJuros = null;
				if(!Util.isVazioOuBranco(txJuros)){
					taxaJuros = Util.formatarMoedaRealparaBigDecimal(txJuros);
				}

				BigDecimal percentualMinimoEntrada = null;
				if(!Util.isVazioOuBranco(percMinEntrada)){
					percentualMinimoEntrada = Util.formatarMoedaRealparaBigDecimal(percMinEntrada);
				}

				BigDecimal percentualTarifaMinimaImovel = null;
				if(!Util.isVazioOuBranco(percTarMinImovel)){
					percentualTarifaMinimaImovel = Util.formatarMoedaRealparaBigDecimal(percTarMinImovel);
				}

				BigDecimal percentualValorReparcelado = null;
				if(!Util.isVazioOuBranco(percVlReparcelado)){
					percentualValorReparcelado = Util.formatarMoedaRealparaBigDecimal(percVlReparcelado);
				}

				// Meses entre Parcelas
				Integer numeroMesesEntreParcelas = null;
				if(!Util.isVazioOuBranco(numeroMesesEntreParcelasStr)){
					numeroMesesEntreParcelas = Util.converterStringParaInteger(numeroMesesEntreParcelasStr);
				}

				// Parcelas a Lançar
				Integer numeroParcelasALancar = null;
				if(!Util.isVazioOuBranco(numeroParcelasALancarStr)){
					numeroParcelasALancar = Util.converterStringParaInteger(numeroParcelasALancarStr);
				}

				// Meses para Início da Cobrança
				Integer numeroMesesInicioCobranca = null;
				if(!Util.isVazioOuBranco(numeroMesesInicioCobrancaStr)){
					numeroMesesInicioCobranca = Util.converterStringParaInteger(numeroMesesInicioCobrancaStr);
				}

				// Dias Vencimento da Entrada
				Integer numeroDiasVencimentoDaEntrada = null;
				if(!Util.isVazioOuBranco(numeroDiasVencimentoDaEntradaStr)){
					numeroDiasVencimentoDaEntrada = Util.converterStringParaInteger(numeroDiasVencimentoDaEntradaStr);
				}

				parcelamentoQuantidadePrestacao.setIndicadorEntradaParcelamento(indicadorEntradaParcelamentoShort);
				parcelamentoQuantidadePrestacao.setValorMinimoEntrada(vlMinimoEntradaBigDecimal);
				parcelamentoQuantidadePrestacao.setTaxaJuros(taxaJuros);
				parcelamentoQuantidadePrestacao.setPercentualMinimoEntrada(percentualMinimoEntrada);
				parcelamentoQuantidadePrestacao.setPercentualTarifaMinimaImovel(percentualTarifaMinimaImovel);
				parcelamentoQuantidadePrestacao.setPercentualValorReparcelado(percentualValorReparcelado);
				parcelamentoQuantidadePrestacao.setNumeroMesesEntreParcelas(numeroMesesEntreParcelas);
				parcelamentoQuantidadePrestacao.setNumeroParcelasALancar(numeroParcelasALancar);
				parcelamentoQuantidadePrestacao.setNumeroMesesInicioCobranca(numeroMesesInicioCobranca);
				parcelamentoQuantidadePrestacao.setNumeroDiasVencimentoDaEntrada(numeroDiasVencimentoDaEntrada);

				parcelamentoQuantidadePrestacaoHelper.setParcelamentoQuantidadePrestacao(parcelamentoQuantidadePrestacao);
			}
		}

	}

}
