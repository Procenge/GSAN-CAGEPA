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

package gcom.gui.relatorio.micromedicao;

import gcom.cadastro.localidade.*;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Ado Rocha
 * @since 03/12/2013
 */
public class ExibirGerarRelatorioQuadroHidrometrosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioQuadroHidrometros");

		GerarRelatorioQuadroHidrometrosActionForm form = (GerarRelatorioQuadroHidrometrosActionForm) actionForm;

		// Flag indicando que o usu�rio fez uma consulta a partir da tecla Enter
		String objetoConsultaStr = httpServletRequest.getParameter("objetoConsulta");

		Integer objetoConsulta = null;
		if(objetoConsultaStr != null){
			objetoConsulta = Integer.valueOf(objetoConsultaStr);
		}

		if(objetoConsulta != null){

			this.pesquisarLocalidade(form);
		}

		this.pesquisarGerenciaRegional(httpServletRequest);
		this.pesquisarUnidadeNegocio(httpServletRequest);


		return retorno;

	}

	/**
	 * @param httpServletRequest
	 */
	private void pesquisarGerenciaRegional(HttpServletRequest httpServletRequest){

		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

		filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.ID);

		filtroGerenciaRegional.setConsultaSemLimites(true);

		Collection colecaoGerenciaRegional = this.getFachada().pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

		if(colecaoGerenciaRegional == null || colecaoGerenciaRegional.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Ger�ncia Regional");
		}else{
			httpServletRequest.setAttribute("colecaoGerenciaRegional", colecaoGerenciaRegional);
		}
	}

	/**
	 * @param httpServletRequest
	 */
	private void pesquisarUnidadeNegocio(HttpServletRequest httpServletRequest){

		FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();

		filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroUnidadeNegocio.setCampoOrderBy(FiltroUnidadeNegocio.ID);
		filtroUnidadeNegocio.setConsultaSemLimites(true);
		Collection colecaoUnidadeNegocio = this.getFachada().pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

		if(colecaoUnidadeNegocio == null || colecaoUnidadeNegocio.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Unidade de Neg�cio");
		}else{
			httpServletRequest.setAttribute("colecaoUnidadeNegocio", colecaoUnidadeNegocio);
		}
	}



	/**
	 * @param form
	 */
	private void pesquisarLocalidade(GerarRelatorioQuadroHidrometrosActionForm form){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, (String) form.getIdLocalidade()));

		// Recupera Localidade
		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			form.setIdLocalidade(localidade.getId().toString());
			form.setDescricaoLocalidade(localidade.getDescricao());

		}else{
			form.setIdLocalidade(null);
			form.setDescricaoLocalidade("Localidade inexistente");
		}

	}


}
