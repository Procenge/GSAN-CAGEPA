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
 * Ailton Francisco de Sousa Junior
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

package gcom.gui.operacional.abastecimento;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.SubsistemaEsgoto;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0524] INSERIR SUBSISTEMA ESGOTO
 * 
 * @author Ailton Sousa
 * @date 26/01/2011
 */

public class InserirSubsistemaEsgotoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		InserirSubsistemaEsgotoActionForm inserirSubsistemaEsgotoActionForm = (InserirSubsistemaEsgotoActionForm) actionForm;

		// Valida formulário
		validarSubsistemaEsgoto(inserirSubsistemaEsgotoActionForm);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		SubsistemaEsgoto subsistemaEsgoto = new SubsistemaEsgoto();

		// Atualiza a entidade com os valores do formulário
		inserirSubsistemaEsgotoActionForm.setFormValues(subsistemaEsgoto);

		// Inserir na base de dados Sistema de Esgoto
		int cdSubsistemaEsgoto = fachada.inserirSubsistemaEsgoto(subsistemaEsgoto, usuarioLogado);

		sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarSubsistemaEsgotoAction.do");

		// Montar a página de sucesso
		montarPaginaSucesso(httpServletRequest, "Subsistema de Esgoto de código " + cdSubsistemaEsgoto + " inserido com sucesso.",
						"Inserir outro Subsistema de Esgoto", "exibirInserirSubSistemaEsgotoAction.do?menu=sim",
						"exibirAtualizarSubsistemaEsgotoAction.do?idRegistroInseridoAtualizar=" + cdSubsistemaEsgoto,
						"Atualizar Subsistema de Esgoto");

		sessao.removeAttribute("InserirSubsistemaEsgotoActionForm");

		return retorno;
	}

	/**
	 * @author isilva
	 * @param form
	 * @throws ActionServletException
	 */
	private void validarSubsistemaEsgoto(InserirSubsistemaEsgotoActionForm form) throws ActionServletException{

		if(Util.isVazioOuBranco(form.getCodigo())){
			throw new ActionServletException("atencao.required", null, "Código");
		}

		if(Util.isVazioOuBranco(form.getDescricaoSubsistemaEsgoto())){
			throw new ActionServletException("atencao.required", null, "Descrição");
		}

		if(Util.isVazioOuBranco(form.getSistemaEsgoto())
						|| form.getSistemaEsgoto().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ActionServletException("atencao.required", null, "Sistema de Esgoto");
		}

		if(Util.isVazioOuBranco(form.getLocalidade()) || form.getLocalidade().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ActionServletException("atencao.required", null, "Localidade");
		}

		if(Util.isVazioOuBranco(form.getEsgotoTratamentoTipo())
						|| form.getEsgotoTratamentoTipo().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ActionServletException("atencao.required", null, "Tipo de Tratamento");
		}
	}

}
