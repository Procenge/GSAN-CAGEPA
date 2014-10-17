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
 * @author Andr� Lopes
 */
public class ExibirRelatorioContasAReceberCorrigidoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioContasAReceberCorrigido");
		HttpSession sessao = httpServletRequest.getSession(false);
		FiltrarRelatorioContasAReceberCorrigidoActionForm form = (FiltrarRelatorioContasAReceberCorrigidoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		// GER�NCIA REGIONAL
		preencherGerenciaRegional(sessao, fachada);

		// UNIDADE NEG�CIO
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

		// UNIDADE NEG�CIO
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
				informarDadosGeracaoRelatorioConsultaActionForm.setDescricaoUnidadeNegocio("Unidade Neg�cio inexistente");
				httpServletRequest.setAttribute("nomeCampo", "unidadeNegocio");
				httpServletRequest.setAttribute("corUnidadeNegocio", "exception");

			}else{
				UnidadeNegocio unidadeNegocio = (UnidadeNegocio) Util.retonarObjetoDeColecao(colecaoPesquisa);

				if(unidadeNegocio == null){

					informarDadosGeracaoRelatorioConsultaActionForm.setUnidadeNegocio("");
					informarDadosGeracaoRelatorioConsultaActionForm
									.setDescricaoUnidadeNegocio("C�digo informado n�o � uma Unidade Neg�cio");
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
