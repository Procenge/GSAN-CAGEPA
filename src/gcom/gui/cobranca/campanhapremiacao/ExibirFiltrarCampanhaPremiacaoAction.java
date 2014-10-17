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

package gcom.gui.cobranca.campanhapremiacao;

import gcom.cadastro.localidade.*;
import gcom.cobranca.campanhapremiacao.Campanha;
import gcom.cobranca.campanhapremiacao.CampanhaPremio;
import gcom.cobranca.campanhapremiacao.FiltroCampanha;
import gcom.cobranca.campanhapremiacao.FiltroCampanhaPremio;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesColecao;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Felipe Rosacruz
 * @date 23/09/2013
 */
public class ExibirFiltrarCampanhaPremiacaoAction
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

		ActionForward retorno = actionMapping.findForward("filtrarCampanhaPremiacaoAction");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarCampanhaPremiacaoActionForm form = (FiltrarCampanhaPremiacaoActionForm) actionForm;

		String indicadorUnidadePremiacao = null;
		String indicadorTemPremiacaoPorUnidade = null;

		// form.setIdLocalidade(null);
		// form.setIdGerenciaRegional(null);
		// form.setIdUnidadeNegocio(null);
		// form.setIdElo(null);

		if(sessao.getAttribute("colecaoCampanhas") == null){
			this.montaColecaoCampanhas(sessao);
		}
		if(sessao.getAttribute("colecaoPremios") == null && form.getIdCampanha() != null){
			this.montaColecaoPremios(sessao, form.getIdCampanha());
		}

		Integer idCampanha = form.getIdCampanha();
		if(idCampanha != null){
			if(idCampanha == -1){
				limpaForm(sessao);
			}else{
				if(sessao.getAttribute("colecaoPremios") == null){
					this.montaColecaoPremios(sessao, idCampanha);
				}

				FiltroCampanha filtroCampanha = new FiltroCampanha();
				filtroCampanha.adicionarParametro(new ParametroSimples(FiltroCampanha.ID, idCampanha));

				Campanha campanhaSelecionada = (Campanha) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroCampanha,
								Campanha.class.getName()));

				// 1.4. Caso a campanha selecionada tenha premia��o por unidade
				if(campanhaSelecionada.getIndicadorUnidadePremiacao() == 1){

					if(form.getPremiacaoTipo() == null){
						form.setPremiacaoTipo(ConstantesSistema.PREMIACAO_AMBAS);
					}

					indicadorTemPremiacaoPorUnidade = ConstantesSistema.ATIVO;
					sessao.setAttribute("indicadorTemPremiacaoPorUnidade", indicadorTemPremiacaoPorUnidade);

					// 1.4.2. Caso o usu�rio tenha selecionado a op��o "Da Unidade" no campo
					// "Premia��o":
					if(form.getPremiacaoTipo().equals(ConstantesSistema.PREMIACAO_DA_UNIDADE)){

						// 1.4.2.1. Caso a unidade de premia��o seja por ger�ncia regional
						if(campanhaSelecionada.getTipoUnidadePremiacao() == 1){

							indicadorUnidadePremiacao = ConstantesSistema.INDICADOR_UNIDADE_PREMIACAO_POR_GERENCIA_REGIONAL;
							
							montaColecaoGerenciaRegional(sessao, campanhaSelecionada);

							// 1.4.2.2. Caso a unidade de premia��o seja por unidade de neg�cio
						}else if(campanhaSelecionada.getTipoUnidadePremiacao() == 2){

							indicadorUnidadePremiacao = ConstantesSistema.INDICADOR_UNIDADE_PREMIACAO_POR_UNIDADE_NEGOCIO;
							
							montaColecaoUnidadeNegocio(sessao, campanhaSelecionada);

							// 1.4.2.3. Caso a unidade de premia��o seja por elo
						}else if(campanhaSelecionada.getTipoUnidadePremiacao() == 3){
							
							indicadorUnidadePremiacao = ConstantesSistema.INDICADOR_UNIDADE_PREMIACAO_POR_ELO;
							
							montaColecaoElo(sessao, campanhaSelecionada);

							// 1.4.2.4. Caso a unidade de premia��o seja por localidade
						}else if(campanhaSelecionada.getTipoUnidadePremiacao() == 4){

							indicadorUnidadePremiacao = ConstantesSistema.INDICADOR_UNIDADE_PREMIACAO_POR_LOCALIDADE;

							montaColecaoLocalidade(sessao, campanhaSelecionada);
						}
						sessao.setAttribute("indicadorUnidadePremiacao", indicadorUnidadePremiacao);

						// 1.4.3. Caso o usu�rio tenha selecionado a op��o "Global" no campo
						// "Premia��o"
					}else if(form.getPremiacaoTipo().equals(ConstantesSistema.PREMIACAO_GLOBAL)){
						sessao.removeAttribute("indicadorUnidadePremiacao");

					}else if(form.getPremiacaoTipo().equals(ConstantesSistema.PREMIACAO_AMBAS)){
						sessao.removeAttribute("indicadorUnidadePremiacao");
					}

				}

			}
		}

		return retorno;

	}

	private void montaColecaoLocalidade(HttpSession sessao, Campanha campanhaSelecionada){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade(FiltroLocalidade.DESCRICAO);

		Collection<CampanhaPremio> colecaoCampanhaPremios = (Collection<CampanhaPremio>) sessao.getAttribute("colecaoPremios");
		Collection<Integer> colecaoId = new ArrayList<Integer>();

		for(CampanhaPremio campanhaPremio : colecaoCampanhaPremios){
			if(campanhaPremio.getLocalidade() != null && campanhaPremio.getLocalidade().getId() != null){
				colecaoId.add(campanhaPremio.getLocalidade().getId());
			}
		}

		Collection<Localidade> colecaoLocalidade = new ArrayList<Localidade>();
		if(!colecaoId.isEmpty()){
			filtroLocalidade.adicionarParametro(new ParametroSimplesColecao(FiltroLocalidade.ID, colecaoId));
			colecaoLocalidade.addAll(Fachada.getInstancia().pesquisar(filtroLocalidade, Localidade.class.getName()));
		}
		sessao.setAttribute("colecaoLocalidade", colecaoLocalidade);

	}

	private void montaColecaoElo(HttpSession sessao, Campanha campanhaSelecionada){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade(FiltroLocalidade.DESCRICAO);
		Collection<Integer> colecaoId = new ArrayList<Integer>();
		
		Collection<CampanhaPremio> colecaoCampanhaPremios = (Collection<CampanhaPremio>) sessao.getAttribute("colecaoPremios");
		for(CampanhaPremio campanhaPremio : colecaoCampanhaPremios){
			if(campanhaPremio.getEloPremio() != null && campanhaPremio.getEloPremio().getId() != null){
				colecaoId.add(campanhaPremio.getEloPremio().getId());

			}
		}
		
		Collection<Localidade> colecaoElo = new ArrayList<Localidade>();
		if(!colecaoId.isEmpty()){
			filtroLocalidade.adicionarParametro(new ParametroSimplesColecao(FiltroLocalidade.ID, colecaoId));
			colecaoElo.addAll(Fachada.getInstancia().pesquisar(filtroLocalidade, Localidade.class.getName()));
		}
		sessao.setAttribute("colecaoElo", colecaoElo);
	}


	private void montaColecaoUnidadeNegocio(HttpSession sessao, Campanha campanhaSelecionada){

		FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio(FiltroUnidadeNegocio.NOME);
		Collection<Integer> colecaoId = new ArrayList<Integer>();
		
		Collection<CampanhaPremio> colecaoCampanhaPremios = (Collection<CampanhaPremio>) sessao.getAttribute("colecaoPremios");
		for(CampanhaPremio campanhaPremio : colecaoCampanhaPremios){
			if(campanhaPremio.getUnidadeNegocio() != null && campanhaPremio.getUnidadeNegocio().getId() != null){
				colecaoId.add(campanhaPremio.getUnidadeNegocio().getId());

			}
		}
		Collection<UnidadeNegocio> colecaoUnidadeNegocio = new ArrayList<UnidadeNegocio>();
		if(!colecaoId.isEmpty()){
			filtroUnidadeNegocio.adicionarParametro(new ParametroSimplesColecao(FiltroUnidadeNegocio.ID, colecaoId));
			colecaoUnidadeNegocio.addAll(Fachada.getInstancia().pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName()));
		}
		sessao.setAttribute("colecaoUnidadeNegocio", colecaoUnidadeNegocio);
	}

	private void montaColecaoGerenciaRegional(HttpSession sessao, Campanha campanhaSelecionada){

		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional(FiltroGerenciaRegional.NOME);
		Collection<Integer> colecaoId = new ArrayList<Integer>();
		
		Collection<CampanhaPremio> colecaoCampanhaPremios = (Collection<CampanhaPremio>) sessao.getAttribute("colecaoPremios");
		for(CampanhaPremio campanhaPremio : colecaoCampanhaPremios){
			if(campanhaPremio.getGerenciaRegional() != null && campanhaPremio.getGerenciaRegional().getId() != null){
				colecaoId.add(campanhaPremio.getGerenciaRegional().getId());
			}
		}
		Collection<GerenciaRegional> colecaoGerenciaRegional = new ArrayList<GerenciaRegional>();
		
		if(!colecaoId.isEmpty()){
			filtroGerenciaRegional.adicionarParametro(new ParametroSimplesColecao(FiltroGerenciaRegional.ID, colecaoId));
			colecaoGerenciaRegional.addAll(Fachada.getInstancia().pesquisar(filtroGerenciaRegional,
						GerenciaRegional.class.getName()));
		}
		sessao.setAttribute("colecaoGerenciaRegional", colecaoGerenciaRegional);
	}

	private void montaColecaoPremios(HttpSession sessao, Integer idCampanha){

		FiltroCampanhaPremio filtroCampanhaPremio = new FiltroCampanhaPremio(FiltroCampanhaPremio.DESCRICAO);
		filtroCampanhaPremio.adicionarParametro(new ParametroSimples(FiltroCampanhaPremio.CAMPANHA_ID, idCampanha));
		filtroCampanhaPremio.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremio.GERENCIA_REGIONAL);
		filtroCampanhaPremio.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremio.UNIDADE_NEGOCIO);
		filtroCampanhaPremio.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaPremio.LOCALIDADE);

		Collection<CampanhaPremio> colecaoPremios = Fachada.getInstancia().pesquisar(filtroCampanhaPremio, CampanhaPremio.class.getName());

		sessao.setAttribute("colecaoPremios", colecaoPremios);

	}

	private void limpaForm(HttpSession sessao){

		sessao.removeAttribute("colecaoPremios");
		sessao.removeAttribute("colecaoGerenciaRegional");
		sessao.removeAttribute("indicadorpremiacaoPorUnidade");
		sessao.removeAttribute("");
	}

	private void montaColecaoCampanhas(HttpSession sessao){

		FiltroCampanha filtroCampanha = new FiltroCampanha(FiltroCampanha.DESCRICAO_TITULO);

		Collection<Campanha> colecaoCampanhas = Fachada.getInstancia().pesquisar(filtroCampanha, Campanha.class.getName());

		sessao.setAttribute("colecaoCampanhas", colecaoCampanhas);
	}

}
