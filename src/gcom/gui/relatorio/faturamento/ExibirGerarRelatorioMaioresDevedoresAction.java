/**
 * 
 */
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

package gcom.gui.relatorio.faturamento;

import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.cliente.FiltroClienteTipo;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
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
 * Action que faz a exibi��o da tela para o usu�rio setar os par�metros
 * necess�rio para a gera��o do relat�rio
 * Gerar Relat�rio Maiores Devedores
 * 
 * @author Victon Malcolm
 * @since 12/11/2013
 */
public class ExibirGerarRelatorioMaioresDevedoresAction
				extends GcomAction {

	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirGerarMaioresDevedores");
		// Flag indicando que o usu�rio fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");
		GerarRelatorioMaioresDevedoresActionForm form = (GerarRelatorioMaioresDevedoresActionForm) actionForm;
		// Pesquisar Localidade Inicial
		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && (objetoConsulta.trim().equals("1"))){

			// Faz a consulta de Localidade
			this.pesquisarLocalidade(form, httpServletRequest);
		}

		this.pesquisarClienteTipo(form, httpServletRequest);

		return retorno;

	}

	private void pesquisarLocalidade(GerarRelatorioMaioresDevedoresActionForm form, HttpServletRequest httpServletRequest){

		Integer loca_i = Integer.parseInt(form.getLocalidade());

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, loca_i));

		// Recupera Localidade
		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());
		HttpSession sessao = httpServletRequest.getSession();
		sessao.removeAttribute("localidadeEncontrada");
		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){
			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);
			this.preencherForm(form, localidade.getId().toString(), localidade.getDescricao());
			sessao.setAttribute("localidadeEncontrada", "true");
		}else{
			form.setLocalidade("");
			form.setNomeLocalidade("Localidade inexistente");
		}
	}
	
	
	private void pesquisarClienteTipo(GerarRelatorioMaioresDevedoresActionForm form, HttpServletRequest httpServletRequest){

		
		FiltroClienteTipo filtroClienteTipo = new FiltroClienteTipo();
		HttpSession sessao = httpServletRequest.getSession();

		// Recupera cliente tipo
		Collection colecaoClienteTipo = this.getFachada().pesquisar(filtroClienteTipo, ClienteTipo.class.getName());
		sessao.setAttribute("colecaoClienteTipo", colecaoClienteTipo);
		

	}

	

	private void preencherForm(GerarRelatorioMaioresDevedoresActionForm form, String localidade, String nomeLocalidade){

		form.setLocalidade(localidade);
		form.setNomeLocalidade(nomeLocalidade);
	}

}
