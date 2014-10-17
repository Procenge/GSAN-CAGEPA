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

package gcom.gui.relatorio.cadastro.endereco;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

/**
 * [UC0XXX] Gerar Relatório Logradouro Geral
 * 
 * @author Anderson Italo
 * @date 25/01/2011
 */
public class ExibirGerarRelatorioLogradouroGeralAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioLogradouroGeral");

		GerarRelatorioLogradouroGeralActionForm form = (GerarRelatorioLogradouroGeralActionForm) actionForm;

		if(httpServletRequest.getParameter("limparCampos") != null && httpServletRequest.getParameter("limparCampos").equals("true")){

			form.setLocalidadeInicial(null);
			form.setNomeLocalidadeInicial(null);
			form.setLocalidadeFinal(null);
			form.setNomeLocalidadeFinal(null);
		}else{
			// Flag indicando que o usuário fez uma consulta a partir da tecla
			// Enter
			String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

			// Pesquisar Localidade
			if(objetoConsulta != null && !objetoConsulta.trim().equals("")
							&& (objetoConsulta.trim().equals("1") || objetoConsulta.trim().equals("3"))){

				// Faz a consulta de Localidade
				this.pesquisarLocalidade(form, objetoConsulta);
			}

			this.setaRequest(httpServletRequest, form);
		}

		return retorno;
	}

	/**
	 * Pesquisa Localidade ao teclar Enter.
	 * 
	 * @author Anderson Itallo
	 * @date 25/01/2011
	 */
	private void pesquisarLocalidade(GerarRelatorioLogradouroGeralActionForm form, String objetoConsulta){

		Object local = form.getLocalidadeInicial();

		if(!objetoConsulta.trim().equals("1")){
			local = form.getLocalidadeFinal();
		}

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, local));

		// Recupera Localidade
		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			if(objetoConsulta.trim().equals("1")){
				form.setLocalidadeInicial(localidade.getId().toString());
				form.setNomeLocalidadeInicial(localidade.getDescricao());
			}

			form.setLocalidadeFinal(localidade.getId().toString());
			form.setNomeLocalidadeFinal(localidade.getDescricao());

		}else{
			if(objetoConsulta.trim().equals("1")){
				form.setLocalidadeInicial(null);
				form.setNomeLocalidadeInicial("Localidade Inicial inexistente");

				form.setLocalidadeFinal(null);
				form.setNomeLocalidadeFinal(null);
			}else{
				form.setLocalidadeFinal(null);
				form.setNomeLocalidadeFinal("Localidade Final inexistente");
			}
		}
	}

	/**
	 * Seta o atributo no request indicando localidade não encontrada.
	 * 
	 * @author Anderson Italo
	 * @date 25/01/2011
	 */
	private void setaRequest(HttpServletRequest httpServletRequest, GerarRelatorioLogradouroGeralActionForm form){

		boolean validouLocalidadeFinal = false;

		if(form.getLocalidadeInicial() != null && !form.getLocalidadeInicial().equals("") && form.getNomeLocalidadeInicial() != null
						&& !form.getNomeLocalidadeInicial().equals("")){

			httpServletRequest.setAttribute("localidadeInicialEncontrada", "true");

			if(form.getLocalidadeFinal() != null && !form.getLocalidadeFinal().equals("") && form.getNomeLocalidadeFinal() != null
							&& !form.getNomeLocalidadeFinal().equals("")){

				validouLocalidadeFinal = true;
				httpServletRequest.setAttribute("localidadeFinalEncontrada", "true");
			}
		}

		if(form.getLocalidadeFinal() != null && !form.getLocalidadeFinal().equals("") && form.getNomeLocalidadeFinal() != null
						&& !form.getNomeLocalidadeFinal().equals("") && !validouLocalidadeFinal){

			httpServletRequest.setAttribute("localidadeFinalEncontrada", "true");
		}
	}

}
