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

package gcom.gui.relatorio.arrecadacao;

import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.UnidadeNegocio;
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
 * @author André Lopes
 */
public class ExibirRelatorioContasAReceberCorrigidoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioContasAReceberCorrigido");
		HttpSession sessao = httpServletRequest.getSession(false);
		FiltrarRelatorioContasAReceberCorrigidoActionForm form = (FiltrarRelatorioContasAReceberCorrigidoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		// GERÊNCIA REGIONAL
		preencherGerenciaRegional(sessao, fachada);

		// UNIDADE NEGÓCIO
		preencherUnidadeNegocio(httpServletRequest, form, fachada);

		// Localidade
		preencherLocalidade(httpServletRequest, form, fachada);

		return retorno;
	}

	private void preencherGerenciaRegional(HttpSession sessao, Fachada fachada){

		if(sessao.getAttribute("colecaoGerenciaRegional") == null){

			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional(FiltroGerenciaRegional.NOME);
			filtroGerenciaRegional.setConsultaSemLimites(true);

			filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

			if(colecaoGerenciaRegional == null || colecaoGerenciaRegional.isEmpty()){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "GERENCIA_REGIONAL");
			}

			sessao.setAttribute("colecaoGerenciaRegional", colecaoGerenciaRegional);
		}
	}

	private void preencherUnidadeNegocio(HttpServletRequest httpServletRequest,
					FiltrarRelatorioContasAReceberCorrigidoActionForm informarDadosGeracaoRelatorioConsultaActionForm, Fachada fachada){

		// UNIDADE NEGÓCIO
		String pesquisarUnidadeNegocio = httpServletRequest.getParameter("pesquisarUnidadeNegocio");
		Collection colecaoPesquisa = null;

		if(pesquisarUnidadeNegocio != null && !pesquisarUnidadeNegocio.equals("")
						&& informarDadosGeracaoRelatorioConsultaActionForm.getUnidadeNegocio() != null
						&& !informarDadosGeracaoRelatorioConsultaActionForm.getUnidadeNegocio().equals("")){

			FiltroUnidadeNegocio filtro = new FiltroUnidadeNegocio();
			filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, informarDadosGeracaoRelatorioConsultaActionForm
							.getUnidadeNegocio()));

			colecaoPesquisa = fachada.pesquisar(filtro, UnidadeNegocio.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){

				informarDadosGeracaoRelatorioConsultaActionForm.setUnidadeNegocio("");
				informarDadosGeracaoRelatorioConsultaActionForm.setDescricaoUnidadeNegocio("Unidade Negócio inexistente");
				httpServletRequest.setAttribute("nomeCampo", "unidadeNegocio");
				httpServletRequest.setAttribute("corUnidadeNegocio", "exception");

			}else{
				UnidadeNegocio unidadeNegocio = (UnidadeNegocio) Util.retonarObjetoDeColecao(colecaoPesquisa);

				if(unidadeNegocio == null){

					informarDadosGeracaoRelatorioConsultaActionForm.setUnidadeNegocio("");
					informarDadosGeracaoRelatorioConsultaActionForm
									.setDescricaoUnidadeNegocio("Código informado não é uma Unidade Negócio");
					httpServletRequest.setAttribute("nomeCampo", "unidadeNegocio");
					httpServletRequest.setAttribute("corUnidadeNegocio", "exception");

				}else{

					informarDadosGeracaoRelatorioConsultaActionForm.setUnidadeNegocio(String.valueOf(unidadeNegocio.getId()));
					informarDadosGeracaoRelatorioConsultaActionForm.setDescricaoUnidadeNegocio(unidadeNegocio.getNome());
					httpServletRequest.setAttribute("nomeCampo", "perfilImovel");
				}
			}
		}
	}

	private void preencherLocalidade(HttpServletRequest httpServletRequest,
					FiltrarRelatorioContasAReceberCorrigidoActionForm informarDadosGeracaoRelatorioConsultaActionForm, Fachada fachada){


		// Localidade
		Collection colecaoPesquisa;
		String pesquisarLocalidade = httpServletRequest.getParameter("pesquisarLocalidade");

		if(pesquisarLocalidade != null && !pesquisarLocalidade.equals("")
						&& informarDadosGeracaoRelatorioConsultaActionForm.getLocalidade() != null
						&& !informarDadosGeracaoRelatorioConsultaActionForm.getLocalidade().equals("")){

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, informarDadosGeracaoRelatorioConsultaActionForm
							.getLocalidade()));

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){

				informarDadosGeracaoRelatorioConsultaActionForm.setLocalidade("");
				informarDadosGeracaoRelatorioConsultaActionForm.setDescricaoLocalidade("Localidade inexistente");
				httpServletRequest.setAttribute("nomeCampo", "localidade");
				httpServletRequest.setAttribute("corLocalidade", "exception");

			}else{
				Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);

				informarDadosGeracaoRelatorioConsultaActionForm.setLocalidade(String.valueOf(localidade.getId()));
				informarDadosGeracaoRelatorioConsultaActionForm.setDescricaoLocalidade(localidade.getDescricao());
				httpServletRequest.setAttribute("nomeCampo", "setorComercial");
			}
		}
	}
}
