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

package gcom.gui.faturamento.consumotarifa;

import gcom.faturamento.consumotarifa.ConsumoTarifaFaixa;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.faturamento.consumotarifa.bean.CategoriaFaixaConsumoTarifaHelper;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 * @author eduardo henrique
 * @date 08/07/2008
 *       Altera��o da formata��o do valor para gera��o do Bigdecimal com 4 casas decimais
 */
public class ManterCategoriaFaixaConsumoTarifaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterCategoriaFaixaConsumoTarifa");

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessaoFaixa = httpServletRequest.getSession(false);
		// Variavel para testar se o campo naum obrigatorio esta vazio

		String inTarifaEsgotoPropria = null;

		// se recuperou no request, seta na sess�o
		if(httpServletRequest.getParameter("indicadorTarifaEsgotoPropria") != null){
			inTarifaEsgotoPropria = httpServletRequest.getParameter("indicadorTarifaEsgotoPropria");
		}else{
			inTarifaEsgotoPropria = (String) sessaoFaixa.getAttribute("indicadorTarifaEsgotoPropria");
		}

		// se o indicador for SIM (1), seta na sess�o
		if(inTarifaEsgotoPropria != null && inTarifaEsgotoPropria.equals(ConstantesSistema.SIM.toString())){
			sessaoFaixa.setAttribute("indicadorTarifaEsgotoPropria", ConstantesSistema.SIM.toString());
		}else{
			sessaoFaixa.removeAttribute("indicadorTarifaEsgotoPropria");
		}

		InserirCategoriaFaixaConsumoTarifaActionForm inserirCategoriaFaixaConsumoTarifaActionForm = (InserirCategoriaFaixaConsumoTarifaActionForm) actionForm;
		Collection colecaoFaixa = (Collection) sessaoFaixa.getAttribute("colecaoFaixa");

		Integer consumoMin = new Integer((String) inserirCategoriaFaixaConsumoTarifaActionForm.getLimiteSuperiorFaixa());

		if(consumoMin.toString().equalsIgnoreCase("99999999")){
			retorno = actionMapping.findForward("manterCategoriaConsumoTarifa");
		}

		if(colecaoFaixa == null){
			colecaoFaixa = new ArrayList();
		}
		ConsumoTarifaFaixa consumoTarifaFaixa = new ConsumoTarifaFaixa();

		Integer i = 0;
		Object[] teste = colecaoFaixa.toArray();
		if(teste.length > 0){
			ConsumoTarifaFaixa consumoTarifaFaixa2 = (ConsumoTarifaFaixa) teste[teste.length - 1];
			if(colecaoFaixa != null){
				i = consumoTarifaFaixa2.getNumeroConsumoFaixaIFim();
			}
		}

		BigDecimal consumoMinimo = new BigDecimal((String) sessaoFaixa.getAttribute("consumoMinimo"));

		Integer limiteSuperFaixa = new Integer(inserirCategoriaFaixaConsumoTarifaActionForm.getLimiteSuperiorFaixa());

		// boleana para saber se a faixa esta entre as faixas ou n�o
		boolean entre = false;
		if(!colecaoFaixa.isEmpty()){

			Iterator colecaoFaixaIterator = colecaoFaixa.iterator();
			boolean existeUltimaFaixa = false;
			boolean existeFaixa = false;
			while(colecaoFaixaIterator.hasNext()){
				ConsumoTarifaFaixa consumoTarifaFaixaLista = (ConsumoTarifaFaixa) colecaoFaixaIterator.next();

				if((consumoTarifaFaixaLista.getNumeroConsumoFaixaIFim().toString().equals(limiteSuperFaixa.toString()))
								&& (consumoTarifaFaixaLista.getNumeroConsumoFaixaIFim().toString().equals("99999999"))){
					existeUltimaFaixa = true;
				}

				if(consumoTarifaFaixaLista.getNumeroConsumoFaixaIFim().toString().equals(limiteSuperFaixa.toString())){
					existeFaixa = true;
				}

				if(existeUltimaFaixa){
					throw new ActionServletException("atencao.faixa_limite_superior_existe");
				}
				if(existeFaixa){
					throw new ActionServletException("atencao.faixa.existente");
				}

			}

			ConsumoTarifaFaixa consumoTarifaFaixaAnteiror = null;
			int indice = 0;
			Iterator colecaoFaixaIt = colecaoFaixa.iterator();
			while(colecaoFaixaIt.hasNext()){
				ConsumoTarifaFaixa consumoTarifaFaixaAtual = (ConsumoTarifaFaixa) colecaoFaixaIt.next();
				/*
				 * if(valorM.compareTo(consumoTarifaFaixa.getValorConsumoTarifa()) <
				 * 0){ throw new ActionServletException(
				 * "atencao.valor_m3_menor2"); }
				 */

				// Ana Breda pediu a retirada da atualiza��o
				/*
				 * if (consumoMin.compareTo(consumoTarifaFaixa2
				 * .getNumeroConsumoFaixaIFim()) <= 0) {
				 * throw new ActionServletException(
				 * "atencao.valor_consumoMinimo_menor2");
				 * }
				 */

				// / if (i){
				// throw new ActionServletException(
				// "atencao.faixa_limite_superior_existe");
				// }

				if(indice == 0){// isso significa que primeira ira analisar o consumo minimo em
					// rela��o a faixa informada
					// signifa que o valor a ser inserido esta entre o consumo minimo e a primeira
					// faixa
					if((consumoMin.intValue() > consumoMinimo.intValue())
									&& (consumoTarifaFaixaAtual.getNumeroConsumoFaixaIFim().intValue() > limiteSuperFaixa.intValue())){
						entre = true;

						if(consumoMinimo.intValue() > 0){

							consumoTarifaFaixa.setNumeroConsumoFaixaInicio(new Integer(consumoMinimo.intValue() + 1));
						}else{

							consumoTarifaFaixa.setNumeroConsumoFaixaInicio(consumoMinimo.intValue());
						}

						consumoTarifaFaixa.setNumeroConsumoFaixaIFim(limiteSuperFaixa);
						consumoTarifaFaixa.setValorConsumoTarifa(Util.formatarMoedaRealparaBigDecimal(
										inserirCategoriaFaixaConsumoTarifaActionForm.getValorM3Faixa(), 4));
						consumoTarifaFaixa.setValorUsoEsgotoTarifa(Util.formatarMoedaRealparaBigDecimal(
										inserirCategoriaFaixaConsumoTarifaActionForm.getValorM3FaixaEsgoto(), 4));
						consumoTarifaFaixa.setUltimaAlteracao(new Date());

						consumoTarifaFaixaAtual.setNumeroConsumoFaixaInicio(new Integer(limiteSuperFaixa + 1));

						// colecaoFaixa.add(consumoTarifaFaixa);

					}
				}else{

					if((consumoTarifaFaixaAnteiror.getNumeroConsumoFaixaIFim().intValue() < limiteSuperFaixa.intValue())
									&& (consumoTarifaFaixaAtual.getNumeroConsumoFaixaIFim().intValue() > limiteSuperFaixa.intValue())){
						entre = true;

						consumoTarifaFaixa.setNumeroConsumoFaixaInicio(new Integer(consumoTarifaFaixaAnteiror.getNumeroConsumoFaixaIFim()
										.intValue() + 1));
						consumoTarifaFaixa.setNumeroConsumoFaixaIFim(limiteSuperFaixa);
						consumoTarifaFaixa.setValorConsumoTarifa(Util.formatarMoedaRealparaBigDecimal(
										inserirCategoriaFaixaConsumoTarifaActionForm.getValorM3Faixa(), 4));
						consumoTarifaFaixa.setUltimaAlteracao(new Date());

						consumoTarifaFaixaAtual.setNumeroConsumoFaixaInicio(new Integer(limiteSuperFaixa + 1));

					}

				}
				indice++;
				consumoTarifaFaixaAnteiror = consumoTarifaFaixaAtual;
			}

		}

		String pPermitirLimiteSuperiorFaixaIgualMinimo = null;

		try{
			pPermitirLimiteSuperiorFaixaIgualMinimo = (String) ParametroFaturamento.P_PERMITIR_LIMITE_SUPERIOR_FAIXA_IGUAL_MINIMO
							.executar();
		}catch(ControladorException e){
			throw new ActionServletException("atencao.sistemaparametro_inexistente", "P_PERMITIR_LIMITE_SUPERIOR_FAIXA_IGUAL_MINIMO");
		}

		if(pPermitirLimiteSuperiorFaixaIgualMinimo != null
						&& pPermitirLimiteSuperiorFaixaIgualMinimo.equals(ConstantesSistema.SIM.toString())){
			if(consumoMinimo.compareTo(new BigDecimal(inserirCategoriaFaixaConsumoTarifaActionForm.getLimiteSuperiorFaixa())) > 0){
				throw new ActionServletException("atencao.valor_consumoMinimo_menor_ou_igual");
			}
		}else{
			if(consumoMinimo.compareTo(new BigDecimal(inserirCategoriaFaixaConsumoTarifaActionForm.getLimiteSuperiorFaixa())) >= 0){
				throw new ActionServletException("atencao.valor_consumoMinimo_menor");
			}
		}

		// coloca a faixa entre as faixas existente

		if(!entre){
			consumoTarifaFaixa.setNumeroConsumoFaixaInicio(new Integer(i + 1));
			consumoTarifaFaixa
							.setNumeroConsumoFaixaIFim(new Integer(inserirCategoriaFaixaConsumoTarifaActionForm.getLimiteSuperiorFaixa()));
			consumoTarifaFaixa.setValorConsumoTarifa(Util.formatarMoedaRealparaBigDecimal(inserirCategoriaFaixaConsumoTarifaActionForm
							.getValorM3Faixa(), 4));
			consumoTarifaFaixa.setValorUsoEsgotoTarifa(Util.formatarMoedaRealparaBigDecimal(
							inserirCategoriaFaixaConsumoTarifaActionForm.getValorM3FaixaEsgoto(), 4));
			consumoTarifaFaixa.setUltimaAlteracao(new Date());

		}

		colecaoFaixa.add(consumoTarifaFaixa);

		CategoriaFaixaConsumoTarifaHelper categoriaFaixaConsumoTarifaHelperAtualizacao = (CategoriaFaixaConsumoTarifaHelper) sessaoFaixa
						.getAttribute("categoriaFaixaConsumoTarifaHelperAtualizacao");

		// Organizar a cole��o

		/*
		 * Collections.sort((List) colecaoFaixa, new Comparator() { public int
		 * compare(Object a, Object b) { Integer consumo1 =
		 * ((ConsumoTarifaFaixa) a) .getNumeroConsumoFaixaIFim(); Integer
		 * consumo2 = ((ConsumoTarifaFaixa) b) .getNumeroConsumoFaixaIFim();
		 * return consumo1.compareTo(consumo2);
		 * } });
		 */

		if(categoriaFaixaConsumoTarifaHelperAtualizacao != null){

			categoriaFaixaConsumoTarifaHelperAtualizacao.setColecaoFaixas(colecaoFaixa);
		}

		List listColecaoFaixa = new ArrayList();
		listColecaoFaixa.addAll(colecaoFaixa);

		Collections.sort(listColecaoFaixa, new Comparator() {

			public int compare(Object a, Object b){

				Integer codigo1 = ((ConsumoTarifaFaixa) a).getNumeroConsumoFaixaIFim();
				Integer codigo2 = ((ConsumoTarifaFaixa) b).getNumeroConsumoFaixaIFim();

				return codigo1.compareTo(codigo2);
			}
		});

		sessaoFaixa.setAttribute("colecaoFaixa", listColecaoFaixa);

		if(httpServletRequest.getParameter("limpaForm") != null){
			inserirCategoriaFaixaConsumoTarifaActionForm.setLimiteSuperiorFaixa("");
			inserirCategoriaFaixaConsumoTarifaActionForm.setValorM3Faixa("");
		}

		return retorno;

	}
}