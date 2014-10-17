/**
 * 
 */
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

package gcom.gui.cobranca.campanhapremiacao;

import gcom.cobranca.campanhapremiacao.FiltroCampanhaPremiacao;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.*;

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
public class FiltrarCampanhaPremiacaoAction
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

		ActionForward retorno = actionMapping.findForward("ExibirManterCampanhaPremiacao");

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarCampanhaPremiacaoActionForm form = (FiltrarCampanhaPremiacaoActionForm) actionForm;

		String atualizar = httpServletRequest.getParameter("indicadorAtualizar");

		// /Cria o filtro para consulta
		FiltroCampanhaPremiacao filtroCampanhaPremiacao = new FiltroCampanhaPremiacao();

		if(atualizar != null){


			if(form.getIdCampanha() != null && form.getIdCampanha() != -1){
				filtroCampanhaPremiacao.adicionarParametro(new ParametroSimples("campanhaPremio.campanha.id", form.getIdCampanha()));
			}
			if(form.getDataInicialSorteio() != null && !form.getDataInicialSorteio().equals("")){
				filtroCampanhaPremiacao.adicionarParametro(new MaiorQue("campanhaSorteio.sorteioInicio", Util.converteStringParaDate(form
								.getDataInicialSorteio())));
			}
			if(form.getDataFinalSorteio() != null && !form.getDataFinalSorteio().equals("")){
				filtroCampanhaPremiacao.adicionarParametro(new MenorQue("campanhaSorteio.sorteioFim", Util.converteStringParaDate(form
								.getDataFinalSorteio())));
			}
			if(form.getIdPremio() != null){

				Collection<Integer> colecaoIdspremios = new ArrayList<Integer>();

				for(Integer id : form.getIdPremio()){
					if(id != -1){
						colecaoIdspremios.add(id);
					}
				}
				if(colecaoIdspremios.size() > 0){
					filtroCampanhaPremiacao.adicionarParametro(new ParametroSimplesColecao("campanhaPremio.id", colecaoIdspremios));
				}
			}
			if(form.getPremiacaoTipo() != null && form.getPremiacaoTipo().equals(ConstantesSistema.PREMIACAO_DA_UNIDADE)){
				if(form.getIdGerenciaRegional() != null){
					Collection<Integer> colecaoIdsGerencia = new ArrayList<Integer>();

					for(Integer id : form.getIdGerenciaRegional()){
						if(id != -1){
							colecaoIdsGerencia.add(id);
						}
					}
					if(colecaoIdsGerencia.size() > 0){
						filtroCampanhaPremiacao.adicionarParametro(new ParametroSimplesColecao("campanhaPremio.gerenciaRegional.id",
										colecaoIdsGerencia));
					}
				}
				if(form.getIdUnidadeNegocio() != null){
					Collection<Integer> colecaoIdsUnidade = new ArrayList<Integer>();

					for(Integer id : form.getIdUnidadeNegocio()){
						if(id != -1){
							colecaoIdsUnidade.add(id);
						}
					}
					if(colecaoIdsUnidade.size() > 0){
						filtroCampanhaPremiacao.adicionarParametro(new ParametroSimplesColecao("campanhaPremio.unidadeNegocio.id",
										colecaoIdsUnidade));
					}
				}
				if(form.getIdElo() != null){
					Collection<Integer> colecaoIdsElo = new ArrayList<Integer>();

					for(Integer id : form.getIdElo()){
						if(id != -1){
							colecaoIdsElo.add(id);
						}
					}
					if(colecaoIdsElo.size() > 0){
						filtroCampanhaPremiacao
										.adicionarParametro(new ParametroSimplesColecao("campanhaPremio.eloPremio.id", colecaoIdsElo));
					}
					}
				if(form.getIdLocalidade() != null){
					Collection<Integer> colecaoIdsLocalidade = new ArrayList<Integer>();

					for(Integer id : form.getIdLocalidade()){
						if(id != -1){
							colecaoIdsLocalidade.add(id);
						}
					}
					if(colecaoIdsLocalidade.size() > 0){
						filtroCampanhaPremiacao.adicionarParametro(new ParametroSimplesColecao("campanhaPremio.localidade.id",
										colecaoIdsLocalidade));
					}
					}
			}else if(form.getPremiacaoTipo() != null && form.getPremiacaoTipo().equals(ConstantesSistema.PREMIACAO_GLOBAL)){

				filtroCampanhaPremiacao.adicionarParametro(new ParametroNulo("campanhaPremio.gerenciaRegional.id"));
				filtroCampanhaPremiacao.adicionarParametro(new ParametroNulo("campanhaPremio.unidadeNegocio.id"));
				filtroCampanhaPremiacao.adicionarParametro(new ParametroNulo("campanhaPremio.eloPremio.id"));
				filtroCampanhaPremiacao.adicionarParametro(new ParametroNulo("campanhaPremio.localidade.id"));
				}

			sessao.setAttribute("filtroCampanhaPremiacao", filtroCampanhaPremiacao);
		}
		return retorno;

	}

}
