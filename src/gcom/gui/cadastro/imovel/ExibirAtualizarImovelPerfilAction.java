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

package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.FiltroImovelPerfil;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Victon Santos
 * @date 12/07/2013
 */

public class ExibirAtualizarImovelPerfilAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarImovelPerfil");

		AtualizarImovelPerfilActionForm atualizarImovelPerfilActionForm = (AtualizarImovelPerfilActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		ImovelPerfil imovelPerfil = null;

		String idImovelPerfil = null;

		if(httpServletRequest.getParameter("idImovelPerfil") != null){
			// tela do manter
			idImovelPerfil = (String) httpServletRequest.getParameter("idImovelPerfil");
			sessao.setAttribute("idImovelPerfil", idImovelPerfil);
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirManterImovelPerfilAction.do");
		}else if(sessao.getAttribute("idImovelPerfil") != null){
			// tela do filtrar
			idImovelPerfil = (String) sessao.getAttribute("idImovelPerfil");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarImovelPerfilAction.do");
		}else if(httpServletRequest.getParameter("idRegistroInseridoAtualizar") != null){
			// link na tela de sucesso do inserir sistema esgoto
			idImovelPerfil = (String) httpServletRequest.getParameter("idRegistroInseridoAtualizar");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarImovelPerfilAction.do?menu=sim");
		}

		if(idImovelPerfil == null){

			if(sessao.getAttribute("idRegistroAtualizar") != null){
				imovelPerfil = (ImovelPerfil) sessao.getAttribute("idRegistroAtualizar");
			}else{
				idImovelPerfil = (String) httpServletRequest.getParameter("idImovelPerfil").toString();
			}
		}

		// ------Inicio da parte que verifica se vem da página de sistema_esgoto_manter.jsp
		if(imovelPerfil == null){

			if(idImovelPerfil != null && !idImovelPerfil.equals("")){

				FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();

				filtroImovelPerfil.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.ID, idImovelPerfil));

				Collection colecaoImovelPerfil = fachada.pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName());

				if(colecaoImovelPerfil != null && !colecaoImovelPerfil.isEmpty()){

					imovelPerfil = (ImovelPerfil) Util.retonarObjetoDeColecao(colecaoImovelPerfil);

				}
			}
		}

		// ------ O Tipo do Hidrometro foi encontrada
		atualizarImovelPerfilActionForm.setIdImovelPerfil(imovelPerfil.getId().toString());
		atualizarImovelPerfilActionForm.setDescricaoImovelPerfil(imovelPerfil.getDescricao());
		atualizarImovelPerfilActionForm.setIndicadorGeracaoAutomatica(imovelPerfil.getIndicadorGeracaoAutomatica().toString());
		atualizarImovelPerfilActionForm.setIndicadorUso(imovelPerfil.getIndicadorUso().toString());

		sessao.setAttribute("imovelPerfil", imovelPerfil);

		httpServletRequest.setAttribute("idImovelPerfil", idImovelPerfil);

		// ------ Fim da parte que verifica se vem da página de hidrometro_Tipo_manter.jsp

		return retorno;
	}
}
