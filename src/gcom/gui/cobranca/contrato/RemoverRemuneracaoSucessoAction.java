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
 * 
 * GSANPCG
 * Virgínia Melo
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
 * Action responsável pela remoção de uma linha que representa a Remuneração por Sucesso
 * 
 * @author Virgínia Melo
 * @date 21/11/2008
 */
public class RemoverRemuneracaoSucessoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		AtualizarContratoCobrancaActionForm form = (AtualizarContratoCobrancaActionForm) actionForm;

		// Define o caso de uso que receberá o resultado desta remoção
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
	 * Método responsável por realizar a exclusão de uma linha da coleção. - ALTERAR
	 * 
	 * @author Virgínia Melo
	 * @date 21/11/2008
	 * @param posicaoLinha
	 *            Posição da linha que será excluída.
	 * @param sessao
	 *            Sessão
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
	 * Método responsável por realizar a exclusão de uma linha da coleção - INSERIR
	 * 
	 * @author Virgínia Melo
	 * @date 21/11/2008
	 * @param posicaoLinha
	 *            Posição da linha que será excluída.
	 * @param sessao
	 *            Sessão
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
