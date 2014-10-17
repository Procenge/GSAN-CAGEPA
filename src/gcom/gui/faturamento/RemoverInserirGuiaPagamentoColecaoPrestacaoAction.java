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

import gcom.gui.GcomAction;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author eduardo henrique
 */
public class RemoverInserirGuiaPagamentoColecaoPrestacaoAction
				extends GcomAction {

	/**
	 * 
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws Exception{

		ActionForward retorno = actionMapping.findForward("inserirGuiaPagamento");

		// obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		Collection colecaoGuiaPrestacoes = (Collection) sessao.getAttribute("colecaoGuiaPrestacaoHelper");

		if(colecaoGuiaPrestacoes != null){

			String[] codigoPrestacoes = httpServletRequest.getParameterValues("prestacaoRemoverSelecao");

			Iterator iterator = colecaoGuiaPrestacoes.iterator();

			BigDecimal valorTotalGuia = (BigDecimal) sessao.getAttribute("valorTotalGuiaPagamento");

			if(colecaoGuiaPrestacoes.size() == 1){
				sessao.removeAttribute("colecaoGuiaPrestacaoHelper");
			}

			while(iterator.hasNext()){
				GuiaPagamentoPrestacaoHelper guiaPrestacao = (GuiaPagamentoPrestacaoHelper) iterator.next();

				for(int i = 0; i < codigoPrestacoes.length; i++){
					if(obterTimestampIdObjeto(guiaPrestacao) == (Long.parseLong(codigoPrestacoes[i]))){

						String parametro = ParametroFaturamento.P_TIPO_DEBITO_PARCELAMENTO_ORGAO_PUBLICO.executar();

						if(!Util.isVazioOuBranco(parametro) && !parametro.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")
										&& parametro.equals(guiaPrestacao.getId().toString())){
							sessao.setAttribute("exibirNuContratoParcOrgaoPublico", ConstantesSistema.NAO);

						}

						valorTotalGuia = valorTotalGuia.subtract(guiaPrestacao.getValorTipoDebito());

						iterator.remove();
					}
				}
				sessao.setAttribute("valorTotalGuiaPagamento", valorTotalGuia);
			}
		}
		return retorno;
	}
}
