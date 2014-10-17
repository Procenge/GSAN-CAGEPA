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

package gcom.gui.operacional;

import gcom.atendimentopublico.ordemservico.FiltroMaterialRedeAgua;
import gcom.atendimentopublico.ordemservico.MaterialRedeAgua;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.operacional.DadoDistritoOperacional;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.FiltroDadoDistritoOperacional;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * MANTER DISTRITO OPERACIONAL
 * 
 * @author P�ricles Tavares
 * @date 28/03/2011
 */

public class ExibirManterDadoDistritoOperacionalAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterDadoDistritoOperacionalAction");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		DistritoOperacional distritoOperacional = (DistritoOperacional) sessao.getAttribute("distritoOperacional");

		Collection colecaoDistritoOperacional = null;

		TreeSet<DadoDistritoOperacional> dadosDistritoOperacional = (TreeSet<DadoDistritoOperacional>) sessao
						.getAttribute("dadosDistritoOperacional");

		if(dadosDistritoOperacional == null){
			FiltroDadoDistritoOperacional filtroDadoDistritoOperacional = new FiltroDadoDistritoOperacional();
			filtroDadoDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDadoDistritoOperacional.DISTRITO_OPERACIONAL_ID,
							distritoOperacional.getId()));
			dadosDistritoOperacional = new TreeSet<DadoDistritoOperacional>(fachada.pesquisar(filtroDadoDistritoOperacional,
							DadoDistritoOperacional.class.getName()));
		}
		FiltroMaterialRedeAgua filtroMaterialAgua = new FiltroMaterialRedeAgua(FiltroMaterialRedeAgua.ID);
		filtroMaterialAgua.adicionarParametro(new ParametroSimples(FiltroMaterialRedeAgua.INDICADOR_USO, ConstantesSistema.SIM));
		Collection<MaterialRedeAgua> colecaoMaterialRedeAgua = fachada.pesquisar(filtroMaterialAgua, MaterialRedeAgua.class.getName());
		sessao.setAttribute("dadosDistritoOperacional", dadosDistritoOperacional);
		sessao.setAttribute("colecaoMaterialRedeAgua", colecaoMaterialRedeAgua);

		ManterDadoDistritoOperacionalActionForm manterDadoDistritoOperacionalActionForm = (ManterDadoDistritoOperacionalActionForm) actionForm;

		manterDadoDistritoOperacionalActionForm.limparForm();

		return retorno;

	}
}