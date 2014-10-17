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

		// ------Inicio da parte que verifica se vem da p�gina de sistema_esgoto_manter.jsp
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

		// ------ Fim da parte que verifica se vem da p�gina de hidrometro_Tipo_manter.jsp

		return retorno;
	}
}
