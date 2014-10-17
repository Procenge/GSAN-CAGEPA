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

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class ExibirGerarRelatorioAuditoriaLeituraAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioAuditoriaLeituraAction");

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		DynaActionForm form = (DynaActionForm) actionForm;

		if(sessao.getAttribute("colecaoGrupoFaturamento") == null){
			Collection<FaturamentoGrupo> colecaoGrupoFaturamento = fachada.pesquisarGrupoFatProcessoRetornoImedInicConc();
			
			if(Util.isVazioOrNulo(colecaoGrupoFaturamento)){
				SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

				throw new ActionServletException("atencao.collectionClienteRelacaoTipo_inexistente",
								Util.formatarAnoMesParaMesAno(sistemaParametro.getAnoMesFaturamento()));
			}else{
				sessao.setAttribute("colecaoGrupoFaturamento", colecaoGrupoFaturamento);
			}
		}
		String consultaLocalidade = (String) httpServletRequest.getParameter("consultaLocalidade");
		String consultaSetorComercial = (String) httpServletRequest.getParameter("consultaSetorComercial");
		String consultaRota = (String) httpServletRequest.getParameter("consultaRota");

		if(consultaLocalidade != null && !consultaLocalidade.trim().equalsIgnoreCase("") && (Integer.parseInt(consultaLocalidade)) == 1){
			pesquisarLocalidade(form, httpServletRequest);
		}
		if(consultaSetorComercial != null && !consultaSetorComercial.trim().equalsIgnoreCase("")
						&& (Integer.parseInt(consultaSetorComercial)) == 1){
			pesquisarLocalidade(form, httpServletRequest);
			pesquisarSetorComercial(form, httpServletRequest);
		}
		if(consultaRota != null && !consultaRota.trim().equalsIgnoreCase("")
 && (Integer.parseInt(consultaRota)) == 1){
			pesquisarRota(form, httpServletRequest);
		}

		return retorno;
	}

	private void pesquisarRota(DynaActionForm form, HttpServletRequest httpServletRequest){

		if(form.get("localidadeID") == null || ((String) form.get("localidadeID")).trim().equalsIgnoreCase("")){
			// Limpa os campos setorComercialCD e setorComercialID do formulario
			form.set("setorComercialCD", "");
			form.set("setorComercialID", "");
			form.set("descricaoRota", "Informe Localidade.");
			httpServletRequest.setAttribute("corSetorComercial", "exception");
			httpServletRequest.setAttribute("nomeCampo", "localidadeID");
		}else if(form.get("setorComercialCD") == null || ((String) form.get("setorComercialCD")).trim().equalsIgnoreCase("")){
			form.set("setorComercialCD", "");
			form.set("setorComercialID", "");
			form.set("descricaoRota", "Informe o setor comercial.");
			httpServletRequest.setAttribute("corSetorComercial", "exception");
			httpServletRequest.setAttribute("nomeCampo", "setorComercialCD");
		}else if(form.get("cdRota") == null || ((String) form.get("cdRota")).trim().equalsIgnoreCase("")){
			form.set("cdRota", "");
			form.set("descricaoRota", "Informe a Rota.");
			httpServletRequest.setAttribute("corRota", "exception");
			httpServletRequest.setAttribute("nomeCampo", "cdRota");
		}else{

			FiltroRota filtroRota = new FiltroRota();
			filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL);
			filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.LOCALIDADE);
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, form.get("cdRota")));

			Collection colecaoPesquisa = Fachada.getInstancia().pesquisar(filtroRota, Rota.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Rota nao encontrada

				form.set("cdRota", "");
				form.set("descricaoRota", "Rota Inexistente.");
				httpServletRequest.setAttribute("corRota", "exception");

				httpServletRequest.setAttribute("nomeCampo", "cdRota");
			}else{
				Rota objetoRota = (Rota) Util.retonarObjetoDeColecao(colecaoPesquisa);
				form.set("cdRota", String.valueOf(objetoRota.getCodigo()));
				form.set("descricaoRota", objetoRota.getDescricao());
				httpServletRequest.setAttribute("corRota", "valor");

			}
		}
	}

	private void pesquisarSetorComercial(DynaActionForm form, HttpServletRequest httpServletRequest){

		if(form.get("localidadeID") == null || ((String) form.get("localidadeID")).trim().equalsIgnoreCase("")){
			// Limpa os campos setorComercialCD e setorComercialID do formulario
			form.set("setorComercialCD", "");
			form.set("setorComercialID", "");
			form.set("descricaoSetorComercial", "Informe Localidade.");
			httpServletRequest.setAttribute("corSetorComercial", "exception");
			httpServletRequest.setAttribute("nomeCampo", "localidadeID");
		}else if(form.get("setorComercialCD") == null || ((String) form.get("setorComercialCD")).trim().equalsIgnoreCase("")){
			form.set("setorComercialCD", "");
			form.set("setorComercialID", "");
			form.set("descricaoSetorComercial", "Informe o setor comercial.");
			httpServletRequest.setAttribute("corSetorComercial", "exception");
			httpServletRequest.setAttribute("nomeCampo", "setorComercialCD");
		}else{

			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, form.get("localidadeID")));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, form
							.get("setorComercialCD")));

			// Retorna setorComercial
			Collection colecaoPesquisa = Fachada.getInstancia().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// setorComercial nao encontrado
				// Limpa os campos setorComercialCD e setorComercialID do
				// formulario
				form.set("setorComercialCD", "");
				form.set("setorComercialID", "");
				form.set("descricaoSetorComercial", "Setor comercial Inexistente.");
				httpServletRequest.setAttribute("corSetorComercial", "exception");

				httpServletRequest.setAttribute("nomeCampo", "setorComercialCD");
			}else{
				SetorComercial objetoSetorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoPesquisa);
				form.set("setorComercialCD", String.valueOf(objetoSetorComercial.getCodigo()));
				form.set("setorComercialID", String.valueOf(objetoSetorComercial.getId()));
				form.set("descricaoSetorComercial", objetoSetorComercial.getDescricao());
				httpServletRequest.setAttribute("corSetorComercial", "valor");

				httpServletRequest.setAttribute("nomeCampo", "quadraNM");
			}

		}
	}

	private void pesquisarLocalidade(DynaActionForm form, HttpServletRequest httpServletRequest){

		// Filtro para obter o localidade ativo de id informado
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(Integer.parseInt((String) form
						.get("localidadeID"))), ParametroSimples.CONECTOR_AND));

		// Pesquisa de acordo com os par�metros informados no filtro
		Collection colecaoLocalidade = Fachada.getInstancia().pesquisar(filtroLocalidade, Localidade.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a cole��o
		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			// Obt�m o objeto da cole��o pesquisada
			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			// Exibe o c�digo e a descri��o pesquisa na p�gina
			httpServletRequest.setAttribute("corLocalidade", "valor");
			form.set("localidadeID", localidade.getId().toString());
			form.set("descricaoLocalidade", localidade.getDescricao());
			httpServletRequest.setAttribute("nomeCampo", "localidadeID");
		}else{
			// Exibe mensagem de c�digo inexiste e limpa o campo de c�digo
			httpServletRequest.setAttribute("corLocalidade", "exception");
			form.set("localidadeID", "");
			form.set("descricaoLocalidade", "LOCALIDADE INEXISTENTE");

			httpServletRequest.setAttribute("nomeCampo", "localidadeID");
		}

		// httpServletRequest
	}

}
