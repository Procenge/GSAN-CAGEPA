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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gui.faturamento.consumotarifa;

import gcom.faturamento.consumotarifa.ConsumoTarifaFaixa;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 * @author eduardo henrique
 * @date 08/07/2008
 *       Alteração para Geração do Bigdecimal de Valor a partir de uma String com 4 casas decimais
 */
public class InserirCategoriaFaixaConsumoTarifaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirCategoriaConsumoTarifa");

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessaoFaixa = httpServletRequest.getSession(false);
		// Variavel para testar se o campo naum obrigatorio esta vazio

		InserirCategoriaFaixaConsumoTarifaActionForm inserirCategoriaFaixaConsumoTarifaActionForm = (InserirCategoriaFaixaConsumoTarifaActionForm) actionForm;
		Collection colecaoFaixa = (Collection) sessaoFaixa.getAttribute("colecaoFaixa");

		String valorConsumoMinimo = (String) sessaoFaixa.getAttribute("consumoMinimo");
		String consumoM = inserirCategoriaFaixaConsumoTarifaActionForm.getLimiteSuperiorFaixa();
		if(consumoM.equalsIgnoreCase("99999999")){
			retorno = actionMapping.findForward("inserirCategoriaConsumoTarifa");
		}

		Integer consumoMin = new Integer((String) inserirCategoriaFaixaConsumoTarifaActionForm.getLimiteSuperiorFaixa());

		if(colecaoFaixa == null){
			colecaoFaixa = new ArrayList();
		}else{
			if(!colecaoFaixa.isEmpty()){
				Iterator colecaoFaixaIt = colecaoFaixa.iterator();
				while(colecaoFaixaIt.hasNext()){
					ConsumoTarifaFaixa consumoTarifaFaixa = (ConsumoTarifaFaixa) colecaoFaixaIt.next();
					if(consumoMin.compareTo(consumoTarifaFaixa.getNumeroConsumoFaixaIFim()) < 0){
						throw new ActionServletException("atencao.valor_consumoMinimo_menor2");
					}
				}

			}

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

		BigDecimal valorConsumoMinimoBigDecimal = new BigDecimal(valorConsumoMinimo);
		BigDecimal ConsumoMBigDecimal = new BigDecimal(consumoM);

		String pPermitirLimiteSuperiorFaixaIgualMinimo = null;

		try{
			pPermitirLimiteSuperiorFaixaIgualMinimo = (String) ParametroFaturamento.P_PERMITIR_LIMITE_SUPERIOR_FAIXA_IGUAL_MINIMO
							.executar();
		}catch(ControladorException e){
			throw new ActionServletException("atencao.sistemaparametro_inexistente", "P_PERMITIR_LIMITE_SUPERIOR_FAIXA_IGUAL_MINIMO");
		}

		if(pPermitirLimiteSuperiorFaixaIgualMinimo != null
						&& pPermitirLimiteSuperiorFaixaIgualMinimo.equals(ConstantesSistema.SIM.toString())){
			if(valorConsumoMinimoBigDecimal.compareTo(ConsumoMBigDecimal) > 0){
				throw new ActionServletException("atencao.valor_consumoMinimo_menor_ou_igual");
			}
		}else{
			if(valorConsumoMinimoBigDecimal.compareTo(ConsumoMBigDecimal) >= 0){
				throw new ActionServletException("atencao.valor_consumoMinimo_menor");
			}
		}

		if(i.intValue() > 0){

			consumoTarifaFaixa.setNumeroConsumoFaixaInicio(new Integer(i + 1));
		}else{

			consumoTarifaFaixa.setNumeroConsumoFaixaInicio(0);
		}

		consumoTarifaFaixa.setNumeroConsumoFaixaIFim(new Integer(inserirCategoriaFaixaConsumoTarifaActionForm.getLimiteSuperiorFaixa()));
		consumoTarifaFaixa.setValorConsumoTarifa(Util.formatarMoedaRealparaBigDecimal(inserirCategoriaFaixaConsumoTarifaActionForm
						.getValorM3Faixa(), 4));
		consumoTarifaFaixa.setValorUsoEsgotoTarifa(Util.formatarMoedaRealparaBigDecimal(
						inserirCategoriaFaixaConsumoTarifaActionForm.getValorM3FaixaEsgoto(), 4));
		consumoTarifaFaixa.setUltimaAlteracao(new Date());

		colecaoFaixa.add(consumoTarifaFaixa);

		sessaoFaixa.setAttribute("colecaoFaixa", colecaoFaixa);

		return retorno;

	}

}