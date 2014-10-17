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
 *
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

package gcom.gui.operacional.localidadedadooperacional;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author isilva
 * @date 26/01/2011
 */
public class ExibirInserirLocalidadeDadoOperacionalAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirLocalidadeDadoOperacional");
		InserirLocalidadeDadoOperacionalActionForm form = (InserirLocalidadeDadoOperacionalActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		// Pesquisa Enter
		if(form.getIdLocalidade() != null && !"".equalsIgnoreCase(form.getIdLocalidade())){
			pesquisarLocalidade(form, httpServletRequest);
		}

		// Carregar a data corrente do sistema
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		Calendar dataCorrente = new GregorianCalendar();

		// Data Corrente
		httpServletRequest.setAttribute("dataAtual", formatoData.format(dataCorrente.getTime()));

		return retorno;
	}

	/**
	 * Pesquisa Localidade pelo Enter
	 * 
	 * @author isilva
	 * @date 26/01/2011
	 * @param form
	 * @param httpServletRequest
	 */
	private void pesquisarLocalidade(InserirLocalidadeDadoOperacionalActionForm form, HttpServletRequest httpServletRequest){

		String idLocalidade = form.getIdLocalidade();

		if(idLocalidade != null && !idLocalidade.toString().trim().equalsIgnoreCase("")){

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.limparListaParametros();

			// coloca parametro no filtro
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(idLocalidade)));

			// cria a colecao para receber a pesquisa
			Collection localidades = new HashSet();
			localidades = Fachada.getInstancia().pesquisar(filtroLocalidade, Localidade.class.getName());

			if(localidades != null && !localidades.isEmpty()){
				// O cliente foi encontrado
				form.setIdLocalidade(Util.adicionarZerosEsquedaNumero(3, new Integer(((Localidade) ((List) localidades).get(0)).getId()
								.toString()).toString()));
				form.setDescricaoLocalidade(((Localidade) ((List) localidades).get(0)).getDescricao());
			}else{
				httpServletRequest.setAttribute("codigoLocalidadeNaoEncontrada", "true");
				form.setIdLocalidade("");
				httpServletRequest.setAttribute("nomeCampo", "idLocalidade");
			}
		}

	}
}
