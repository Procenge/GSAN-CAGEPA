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

package gcom.gui.operacional.bacia;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.Bacia;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author isilva
 * @date 03/02/2011
 */
public class AtualizarBaciaAction
				extends GcomAction {

	// Obtém a instância da fachada
	Fachada fachada = Fachada.getInstancia();

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		Collection<Bacia> colecaoEnderecos = (Collection<Bacia>) sessao.getAttribute("colecaoEnderecos");

		AtualizarBaciaActionForm form = (AtualizarBaciaActionForm) actionForm;

		// Valida o Formulário
		this.validarForm(form);

		Bacia bacia = (Bacia) sessao.getAttribute("bacia");
		bacia = form.setBaciaValues(bacia, colecaoEnderecos);

		fachada.atualizarBacia(bacia, usuario);

		montarPaginaSucesso(httpServletRequest, "Bacia de código " + form.getCodigo() + " atualizada com sucesso.",
						"Realizar outra Manutenção de Bacia", "exibirFiltrarBaciaAction.do?menu=sim");

		sessao.removeAttribute("baciaID");
		sessao.removeAttribute("bacia");

		// devolve o mapeamento de retorno
		return retorno;
	}

	/**
	 * @param form
	 * @return
	 */
	private void validarForm(AtualizarBaciaActionForm form){

		// *********************** Valida Referência *************************
		if(Util.isVazioOuBranco(form.getCodigo())){
			throw new ActionServletException("atencao.required", null, "Código");
		}

		try{
			Integer.valueOf(form.getCodigo());
		}catch(Exception e){
			e.printStackTrace();
			throw new ActionServletException("atencao.campo.informado.invalido", null, "Código");
		}

		if(Util.isVazioOuBranco(form.getDescricao())){
			throw new ActionServletException("atencao.required", null, "Decrição");
		}

		// if (Util.isVazioOuBranco(form.getDescricaoAbreviada())) {
		// throw new ActionServletException("atencao.required", null, "Decrição Abreviada");
		// }

		if(Util.isVazioOuBranco(form.getIdSistemaEsgoto())
						|| ("" + ConstantesSistema.NUMERO_NAO_INFORMADO).equalsIgnoreCase(form.getIdSistemaEsgoto())){
			throw new ActionServletException("atencao.required", null, "Sistema de Esgoto");
		}

		try{
			Integer.valueOf(form.getIdSistemaEsgoto());
		}catch(Exception e){
			e.printStackTrace();
			throw new ActionServletException("atencao.campo.informado.invalido", null, "Sistema de Esgoto");
		}

		if(Util.isVazioOuBranco(form.getIdSubsistemaEsgoto())
						|| ("" + ConstantesSistema.NUMERO_NAO_INFORMADO).equalsIgnoreCase(form.getIdSubsistemaEsgoto())){
			throw new ActionServletException("atencao.required", null, "Subsistema de Esgoto");
		}

		try{
			Integer.valueOf(form.getIdSubsistemaEsgoto());
		}catch(Exception e){
			e.printStackTrace();
			throw new ActionServletException("atencao.campo.informado.invalido", null, "Subsistema de Esgoto");
		}

		if(Util.isVazioOuBranco(form.getIndicadorUso())){
			throw new ActionServletException("atencao.campo.informado.invalido", null, "Indicador de Uso");
		}
	}
}
