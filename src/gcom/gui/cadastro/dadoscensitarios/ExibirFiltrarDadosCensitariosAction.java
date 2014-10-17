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

package gcom.gui.cadastro.dadoscensitarios;

import gcom.cadastro.dadocensitario.FiltroLocalidadeDadosCensitario;
import gcom.cadastro.dadocensitario.FiltroMunicipioDadosCensitario;
import gcom.cadastro.dadocensitario.LocalidadeDadosCensitario;
import gcom.cadastro.dadocensitario.MunicipioDadosCensitario;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
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
 * [UC0053] Manter Dados Censit�rios
 * 
 * @author Anderson Italo
 * @date 10/02/2011
 */
public class ExibirFiltrarDadosCensitariosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("filtrarDadosCensitarios");

		HttpSession sessao = httpServletRequest.getSession(false);
		FiltrarDadosCensitariosActionForm form = (FiltrarDadosCensitariosActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		boolean encontrouLocalidade = false;
		boolean encontrouMunicipio = false;

		if(form.getIndicadorLocalidadeMunicipio() == null){
			form.setIndicadorLocalidadeMunicipio(ConstantesSistema.DADOS_CENSITARIOS_LOCALIDADE);
		}else if(form.getIndicadorLocalidadeMunicipio().equals(ConstantesSistema.DADOS_CENSITARIOS_LOCALIDADE)){
			form.setCodigoMunicipio(null);
			form.setNomeMunicipio(null);
		}else{
			form.setCodigoLocalidade(null);
			form.setNomeLocalidade(null);
		}

		// Flag indicando que o usu�rio fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && objetoConsulta.trim().equals("1")){

			// Faz a consulta de Localidade
			encontrouLocalidade = this.pesquisarLocalidade(form, objetoConsulta);

		}
		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && objetoConsulta.trim().equals("2")){

			// Faz a consulta de Municipio
			encontrouMunicipio = this.pesquisarMunicipio(form, objetoConsulta);
		}

		// caso tenha pesquisado localidade ou munic�pio a partir do link da lupa
		if(httpServletRequest.getParameter("pesquisarLocalidade") != null
						&& httpServletRequest.getParameter("pesquisarLocalidade").equals("true")){

			encontrouLocalidade = true;
		}else if(httpServletRequest.getParameter("pesquisarMunicipio") != null
						&& httpServletRequest.getParameter("pesquisarMunicipio").equals("true")){

			encontrouMunicipio = true;
		}

		if(encontrouLocalidade){

			FiltroLocalidadeDadosCensitario filtroLocalidadeDadosCensitario = new FiltroLocalidadeDadosCensitario(
							FiltroLocalidadeDadosCensitario.ANO_MES);
			filtroLocalidadeDadosCensitario.adicionarParametro(new ParametroSimples(FiltroLocalidadeDadosCensitario.LOCALIDADE_ID,
							new Integer(form.getCodigoLocalidade())));
			Collection colecaoLocalidadeDadosCensitariosReferencia = fachada.pesquisar(filtroLocalidadeDadosCensitario,
							LocalidadeDadosCensitario.class.getName());

			if(colecaoLocalidadeDadosCensitariosReferencia != null && !colecaoLocalidadeDadosCensitariosReferencia.isEmpty()){
				httpServletRequest.setAttribute("colecaoLocalidadeDadosCensitariosReferencia", colecaoLocalidadeDadosCensitariosReferencia);
				httpServletRequest.setAttribute("colecaoReferencia", true);
			}

		}else if(encontrouMunicipio){
			FiltroMunicipioDadosCensitario filtroMunicipioDadosCensitario = new FiltroMunicipioDadosCensitario();
			filtroMunicipioDadosCensitario.adicionarParametro(new ParametroSimples(FiltroMunicipioDadosCensitario.MUNICIPIO_ID,
							new Integer(form.getCodigoMunicipio())));
			Collection colecaoMunicipioDadosCensitariosReferencia = fachada.pesquisar(filtroMunicipioDadosCensitario,
							MunicipioDadosCensitario.class.getName());

			if(colecaoMunicipioDadosCensitariosReferencia != null && !colecaoMunicipioDadosCensitariosReferencia.isEmpty()){
				httpServletRequest.setAttribute("colecaoMunicipioDadosCensitariosReferencia", colecaoMunicipioDadosCensitariosReferencia);
				httpServletRequest.setAttribute("colecaoReferencia", true);
			}

		}

		// C�digo para checar ou n�o o ATUALIZAR e Passar o valor do Indicador de Uso como "TODOS"
		String primeiraVez = httpServletRequest.getParameter("menu");
		if(primeiraVez != null && !primeiraVez.equals("")){
			sessao.setAttribute("indicadorAtualizar", "1");
			form.setTipoPesquisa(ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());

		}

		this.setaRequest(httpServletRequest, form);

		return retorno;
	}

	/**
	 * Pesquisa Localidade ao teclar Enter.
	 * 
	 * @author Anderson Italo
	 * @date 10/02/2011
	 */
	private boolean pesquisarLocalidade(FiltrarDadosCensitariosActionForm form, String objetoConsulta){

		boolean retorno = false;
		Object local = form.getCodigoLocalidade();

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, local));

		// Recupera Localidade
		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			form.setCodigoLocalidade(localidade.getId().toString());
			form.setNomeLocalidade(localidade.getDescricao());
			retorno = true;

		}else{
			form.setCodigoLocalidade(null);
			form.setNomeLocalidade("Localidade inexistente");
		}

		return retorno;
	}

	/**
	 * Pesquisa Municipio ao teclar Enter.
	 * 
	 * @author Anderson Italo
	 * @date 10/02/2011
	 */
	private boolean pesquisarMunicipio(FiltrarDadosCensitariosActionForm form, String objetoConsulta){

		boolean retorno = false;
		Object codigoMunicipio = form.getCodigoMunicipio();

		FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
		filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, codigoMunicipio));

		// Recupera Municipio
		Collection colecaoMunicipio = this.getFachada().pesquisar(filtroMunicipio, Municipio.class.getName());

		if(colecaoMunicipio != null && !colecaoMunicipio.isEmpty()){

			Municipio municipio = (Municipio) Util.retonarObjetoDeColecao(colecaoMunicipio);
			form.setCodigoMunicipio(municipio.getId().toString());
			form.setNomeMunicipio(municipio.getNome());
			retorno = true;

		}else{
			form.setCodigoMunicipio(null);
			form.setNomeMunicipio("Municipio inexistente");
		}

		return retorno;
	}

	/**
	 * Seta o atributo no request indicando localidade
	 * ou munic�pio n�o encontrada.
	 * 
	 * @author Anderson Italo
	 * @date 10/05/2011
	 */
	private void setaRequest(HttpServletRequest httpServletRequest, FiltrarDadosCensitariosActionForm form){

		if(form.getCodigoLocalidade() != null && !form.getCodigoLocalidade().equals("")){

			httpServletRequest.setAttribute("localidadeEncontrada", "true");
		}

		if(form.getCodigoMunicipio() != null && !form.getCodigoMunicipio().equals("")){

			httpServletRequest.setAttribute("municipioEncontrado", "true");
		}
	}

}