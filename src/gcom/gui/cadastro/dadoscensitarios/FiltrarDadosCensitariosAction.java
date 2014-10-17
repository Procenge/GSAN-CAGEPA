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
 * [UC0053] Manter Dados Censitários
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
		// caso o usuário tenha selecionado a opção localidade
		if(form.getIndicadorLocalidadeMunicipio().equals(ConstantesSistema.DADOS_CENSITARIOS_LOCALIDADE)){

			sessao.removeAttribute("filtroMunicipioDadosCensitario");

			FiltroLocalidadeDadosCensitario filtroLocalidadeDadosCensitario = new FiltroLocalidadeDadosCensitario();

			// Verificar se o usuário selecionou alguma opção de filtro
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

			// Erro caso o usuário solicite filtrar sem nenhum parâmetro
			if(!peloMenosUmParametroInformado){
				throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
			}

			Collection colecaoLocalidadeDadosCensitarios = fachada.pesquisar(filtroLocalidadeDadosCensitario,
							LocalidadeDadosCensitario.class.getName());

			if(colecaoLocalidadeDadosCensitarios == null || colecaoLocalidadeDadosCensitarios.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Dados Censitários de Localidade");
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

			// caso contrário o usuário informou a opção município
			FiltroMunicipioDadosCensitario filtroMunicipioDadosCensitario = new FiltroMunicipioDadosCensitario();

			// Verificar se o usuário selecionou alguma opção de filtro
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

			// Erro caso o usuário mandou filtrar sem nenhum parâmetro
			if(!peloMenosUmParametroInformado){
				throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
			}

			Collection colecaoMunicipioDadosCensitarios = fachada.pesquisar(filtroMunicipioDadosCensitario, MunicipioDadosCensitario.class
							.getName());

			if(colecaoMunicipioDadosCensitarios == null || colecaoMunicipioDadosCensitarios.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Dados Censitários de Município");
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
