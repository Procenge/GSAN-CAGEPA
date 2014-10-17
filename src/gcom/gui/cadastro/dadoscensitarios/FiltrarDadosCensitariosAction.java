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
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
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
 * @date 11/02/2011
 */
public class FiltrarDadosCensitariosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterDadosCensitarios");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarDadosCensitariosActionForm form = (FiltrarDadosCensitariosActionForm) actionForm;

		// Indicador Atualizar
		String indicadorAtualizar = form.getIndicadorAtualizar();
		if(indicadorAtualizar != null && !indicadorAtualizar.equals("") && indicadorAtualizar.equals("1")){

			sessao.setAttribute("indicadorAtualizar", "1");
		}else{

			sessao.removeAttribute("indicadorAtualizar");
		}

		boolean peloMenosUmParametroInformado = false;
		// caso o usu�rio tenha selecionado a op��o localidade
		if(form.getIndicadorLocalidadeMunicipio().equals(ConstantesSistema.DADOS_CENSITARIOS_LOCALIDADE)){

			sessao.removeAttribute("filtroMunicipioDadosCensitario");

			FiltroLocalidadeDadosCensitario filtroLocalidadeDadosCensitario = new FiltroLocalidadeDadosCensitario();

			// Verificar se o usu�rio selecionou alguma op��o de filtro
			if(form.getCodigoLocalidade() != null && !form.getCodigoLocalidade().equals("")){

				filtroLocalidadeDadosCensitario.adicionarParametro(new ParametroSimples(FiltroLocalidadeDadosCensitario.LOCALIDADE_ID,
								new Integer(form.getCodigoLocalidade())));
				peloMenosUmParametroInformado = true;
			}else{
				throw new ActionServletException("atencao.informe_campo", null, "Localidade");
			}

			if(form.getAnoMesReferencia() != null && !form.getAnoMesReferencia().equals("")){

				filtroLocalidadeDadosCensitario.adicionarParametro(new ParametroSimples(FiltroLocalidadeDadosCensitario.ANO_MES,
								new Integer(form.getAnoMesReferencia())));
				peloMenosUmParametroInformado = true;
			}

			filtroLocalidadeDadosCensitario.setCampoOrderBy(FiltroMunicipioDadosCensitario.ANO_MES);

			// Erro caso o usu�rio solicite filtrar sem nenhum par�metro
			if(!peloMenosUmParametroInformado){
				throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
			}

			Collection colecaoLocalidadeDadosCensitarios = fachada.pesquisar(filtroLocalidadeDadosCensitario,
							LocalidadeDadosCensitario.class.getName());

			if(colecaoLocalidadeDadosCensitarios == null || colecaoLocalidadeDadosCensitarios.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Dados Censit�rios de Localidade");
			}else{
				httpServletRequest.setAttribute("colecaoLocalidadeDadosCensitarios", colecaoLocalidadeDadosCensitarios);
				LocalidadeDadosCensitario localidadeDadosCensitario = new LocalidadeDadosCensitario();
				localidadeDadosCensitario = (LocalidadeDadosCensitario) Util.retonarObjetoDeColecao(colecaoLocalidadeDadosCensitarios);
				String idRegistroAtualizacao = localidadeDadosCensitario.getId().toString();
				sessao.setAttribute("idRegistroAtualizacao", idRegistroAtualizacao);
				sessao.setAttribute("dadosLocalidade", "true");
			}

			sessao.setAttribute("filtroLocalidadeDadosCensitario", filtroLocalidadeDadosCensitario);
			httpServletRequest.setAttribute("filtroLocalidadeDadosCensitario", filtroLocalidadeDadosCensitario);

		}else{

			sessao.removeAttribute("filtroLocalidadeDadosCensitario");

			// caso contr�rio o usu�rio informou a op��o munic�pio
			FiltroMunicipioDadosCensitario filtroMunicipioDadosCensitario = new FiltroMunicipioDadosCensitario();

			// Verificar se o usu�rio selecionou alguma op��o de filtro
			if(form.getCodigoMunicipio() != null && !form.getCodigoMunicipio().equals("")){

				filtroMunicipioDadosCensitario.adicionarParametro(new ParametroSimples(FiltroMunicipioDadosCensitario.MUNICIPIO_ID,
								new Integer(form.getCodigoMunicipio())));
				peloMenosUmParametroInformado = true;
			}else{
				throw new ActionServletException("atencao.informe_campo", null, "Municipio");
			}

			if(form.getAnoMesReferencia() != null && !form.getAnoMesReferencia().equals("")){

				filtroMunicipioDadosCensitario.adicionarParametro(new ParametroSimples(FiltroMunicipioDadosCensitario.ANO_MES, new Integer(
								form.getAnoMesReferencia())));
				peloMenosUmParametroInformado = true;
			}

			filtroMunicipioDadosCensitario.setCampoOrderBy(FiltroMunicipioDadosCensitario.ANO_MES);

			// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
			if(!peloMenosUmParametroInformado){
				throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
			}

			Collection colecaoMunicipioDadosCensitarios = fachada.pesquisar(filtroMunicipioDadosCensitario, MunicipioDadosCensitario.class
							.getName());

			if(colecaoMunicipioDadosCensitarios == null || colecaoMunicipioDadosCensitarios.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Dados Censit�rios de Munic�pio");
			}else{
				httpServletRequest.setAttribute("colecaoMunicipioDadosCensitarios", colecaoMunicipioDadosCensitarios);
				MunicipioDadosCensitario municipioDadosCensitario = new MunicipioDadosCensitario();
				municipioDadosCensitario = (MunicipioDadosCensitario) Util.retonarObjetoDeColecao(colecaoMunicipioDadosCensitarios);
				String idRegistroAtualizacao = municipioDadosCensitario.getId().toString();
				sessao.setAttribute("idRegistroAtualizacao", idRegistroAtualizacao);
				httpServletRequest.setAttribute("idRegistroAtualizacao", idRegistroAtualizacao);
				sessao.setAttribute("dadosLocalidade", "false");
			}

			sessao.setAttribute("filtroMunicipioDadosCensitario", filtroMunicipioDadosCensitario);
			httpServletRequest.setAttribute("filtroMunicipioDadosCensitario", filtroMunicipioDadosCensitario);
		}

		return retorno;
	}

}
