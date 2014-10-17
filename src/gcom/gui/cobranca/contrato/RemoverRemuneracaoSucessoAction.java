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
 * Virg�nia Melo
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

package gcom.gui.cobranca.contrato;

import gcom.cobranca.contrato.ContratoTipoRemuneracao;
import gcom.gui.GcomAction;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel pela remo��o de uma linha que representa a Remunera��o por Sucesso
 * 
 * @author Virg�nia Melo
 * @date 21/11/2008
 */
public class RemoverRemuneracaoSucessoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		AtualizarContratoCobrancaActionForm form = (AtualizarContratoCobrancaActionForm) actionForm;

		// Define o caso de uso que receber� o resultado desta remo��o
		String mapeamentoStruts = httpServletRequest.getParameter("mapeamento");

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward(mapeamentoStruts);
		HttpSession sessao = httpServletRequest.getSession(false);

		String posicaoLinha = httpServletRequest.getParameter("posicaoLinha");

		// if (mapeamentoStruts.equals("exibirAlterarContrato")) {
		// AtualizarContratoCobrancaActionForm form = (AtualizarContratoCobrancaActionForm)
		// actionForm;
		// this.removerLinhaAlterar(posicaoLinha, form);
		// } else {
		// this.removerLinhaInserir(posicaoLinha, sessao);
		// }

		this.removerLinhaInserir(posicaoLinha, Integer.parseInt(form.getRemuneracaoTipo()), sessao);

		return retorno;
	}

	/**
	 * M�todo respons�vel por realizar a exclus�o de uma linha da cole��o. - ALTERAR
	 * 
	 * @author Virg�nia Melo
	 * @date 21/11/2008
	 * @param posicaoLinha
	 *            Posi��o da linha que ser� exclu�da.
	 * @param sessao
	 *            Sess�o
	 */
	// private void removerLinhaAlterar(String posicaoLinha, AtualizarContratoCobrancaActionForm
	// form){
	//    	
	// List colecaoLinhas = (ArrayList)form.getColecaoRemuneracaoVencimento();
	//    	
	// if ((posicaoLinha != null && !posicaoLinha.equalsIgnoreCase(""))
	// && (colecaoLinhas != null && !colecaoLinhas.equals(""))) {
	//    		
	// colecaoLinhas.remove(Integer.parseInt(posicaoLinha));
	//    		
	// }
	//    	
	// form.setColecaoRemuneracaoVencimento(colecaoLinhas);
	//    	
	// }

	/**
	 * M�todo respons�vel por realizar a exclus�o de uma linha da cole��o - INSERIR
	 * 
	 * @author Virg�nia Melo
	 * @date 21/11/2008
	 * @param posicaoLinha
	 *            Posi��o da linha que ser� exclu�da.
	 * @param sessao
	 *            Sess�o
	 */
	private void removerLinhaInserir(String posicaoLinha, int remuneracaoTipo, HttpSession sessao){

		List colecaoRemuneracao = null;

		if(remuneracaoTipo == ContratoTipoRemuneracao.TIPO_SUCESSO){
			colecaoRemuneracao = (ArrayList) sessao.getAttribute("colecaoRemuneracaoSucesso");
		}else if(remuneracaoTipo == ContratoTipoRemuneracao.TIPO_PRODUTIVIDADE){
			colecaoRemuneracao = (ArrayList) sessao.getAttribute("colecaoRemuneracaoProdutividade");
		}

		if((posicaoLinha != null && !posicaoLinha.equalsIgnoreCase("")) && colecaoRemuneracao != null && !colecaoRemuneracao.isEmpty()){

			try{
				colecaoRemuneracao.remove(Integer.parseInt(posicaoLinha));
			}catch(java.lang.IndexOutOfBoundsException e){
				e.printStackTrace();
				return;
			}

			if(!colecaoRemuneracao.isEmpty()){
				sessao.removeAttribute("existeColecao");
			}
		}else{
			sessao.setAttribute("colecao", 1);
			sessao.removeAttribute("adicionar");
		}
	}
}
