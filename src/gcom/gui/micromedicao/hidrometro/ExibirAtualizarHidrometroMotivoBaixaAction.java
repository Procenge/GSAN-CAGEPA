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

package gcom.gui.micromedicao.hidrometro;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.FiltroHidrometroMotivoBaixa;
import gcom.micromedicao.hidrometro.HidrometroMotivoBaixa;
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
 * [UC] MANTER MotivoBaixa HIDROMETRO
 * 
 * @author Deyverson Santos
 * @date 15/12/2008
 */

public class ExibirAtualizarHidrometroMotivoBaixaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarHidrometroMotivoBaixa");

		AtualizarHidrometroMotivoBaixaActionForm atualizarHidrometroMotivoBaixaActionForm = (AtualizarHidrometroMotivoBaixaActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		HidrometroMotivoBaixa hidrometroMotivoBaixa = null;

		String idHidrometroMotivoBaixa = null;

		if(httpServletRequest.getParameter("idHidrometroMotivoBaixa") != null){
			// tela do manter
			idHidrometroMotivoBaixa = (String) httpServletRequest.getParameter("idHidrometroMotivoBaixa");
			sessao.setAttribute("idHidrometroMotivoBaixa", idHidrometroMotivoBaixa);
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirManterHidrometroMotivoBaixaAction.do");
		}else if(sessao.getAttribute("idHidrometroMotivoBaixa") != null){
			// tela do filtrar
			idHidrometroMotivoBaixa = (String) sessao.getAttribute("idHidrometroMotivoBaixa");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarHidrometroMotivoBaixaAction.do");
		}else if(httpServletRequest.getParameter("idRegistroInseridoAtualizar") != null){
			// link na tela de sucesso do inserir sistema esgoto
			idHidrometroMotivoBaixa = (String) httpServletRequest.getParameter("idRegistroInseridoAtualizar");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarHidrometroMotivoBaixaAction.do?menu=sim");
		}

		if(idHidrometroMotivoBaixa == null){

			if(sessao.getAttribute("idRegistroAtualizar") != null){
				hidrometroMotivoBaixa = (HidrometroMotivoBaixa) sessao.getAttribute("idRegistroAtualizar");
			}else{
				idHidrometroMotivoBaixa = (String) httpServletRequest.getParameter("idHidrometroMotivoBaixa").toString();
			}
		}

		// ------Inicio da parte que verifica se vem da p�gina de sistema_esgoto_manter.jsp
		if(hidrometroMotivoBaixa == null){

			if(idHidrometroMotivoBaixa != null && !idHidrometroMotivoBaixa.equals("")){

				FiltroHidrometroMotivoBaixa filtroHidrometroMotivoBaixa = new FiltroHidrometroMotivoBaixa();

				filtroHidrometroMotivoBaixa
								.adicionarParametro(new ParametroSimples(FiltroHidrometroMotivoBaixa.ID, idHidrometroMotivoBaixa));

				Collection colecaoHidrometroMotivoBaixa = fachada.pesquisar(filtroHidrometroMotivoBaixa,
								HidrometroMotivoBaixa.class.getName());

				if(colecaoHidrometroMotivoBaixa != null && !colecaoHidrometroMotivoBaixa.isEmpty()){

					hidrometroMotivoBaixa = (HidrometroMotivoBaixa) Util.retonarObjetoDeColecao(colecaoHidrometroMotivoBaixa);

				}
			}
		}

		// ------ O MotivoBaixa do Hidrometro foi encontrada
		atualizarHidrometroMotivoBaixaActionForm.setIdHidrometroMotivoBaixa(hidrometroMotivoBaixa.getId().toString());
		atualizarHidrometroMotivoBaixaActionForm.setDescricaoMotivoBaixaHidrometro(hidrometroMotivoBaixa.getDescricao());
		atualizarHidrometroMotivoBaixaActionForm.setIndicadorUso(hidrometroMotivoBaixa.getIndicadorUso().toString());

		sessao.setAttribute("hidrometroMotivoBaixa", hidrometroMotivoBaixa);

		httpServletRequest.setAttribute("idHidrometroMotivoBaixa", idHidrometroMotivoBaixa);

		// ------ Fim da parte que verifica se vem da p�gina de hidrometro_MotivoBaixa_manter.jsp

		return retorno;
	}
}