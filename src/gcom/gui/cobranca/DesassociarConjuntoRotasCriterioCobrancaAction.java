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
 * 
 * GSAN - PCG
 * Saulo Vasconcelos de Lima
 */

package gcom.gui.cobranca;

import gcom.cobranca.FiltroRotaAcaoCriterio;
import gcom.cobranca.RotaAcaoCriterio;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author Saulo Lima
 * @created 22 de Julho de 2008
 */
public class DesassociarConjuntoRotasCriterioCobrancaAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AssociarConjuntoRotasCriterioCobrancaActionForm form = (AssociarConjuntoRotasCriterioCobrancaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		String idGrupoCobranca = form.getIdGrupoCobranca();
		String idGerencialRegional = form.getIdGerenciaRegional();
		String idUnidadeNegocio = form.getIdUnidadeNegocio();
		String idLocalidadeInicial = form.getIdLocalidadeInicial();
		String idLocalidadeFinal = form.getIdLocalidadeFinal();
		String codigoSetorInicial = form.getCodigoSetorComercialInicial();
		String codigoSetorFinal = form.getCodigoSetorComercialFinal();
		String rotaInicial = form.getNumeroRotaInicial();
		String rotaFinal = form.getNumeroRotaFinal();
		String idCobrancaAcao = form.getIdAcaoCobranca();
		// String idCriterioCobranca = form.getIdCriterioCobranca();

		Collection rotas = null;

		if(idGrupoCobranca != null && !idGrupoCobranca.equals("-1")){
			rotas = fachada.pesquisarRotasIntervaloGrupo(idGrupoCobranca, idCobrancaAcao);

		}else if(rotaInicial != null && !(rotaInicial.equals("") || rotaInicial.equals("-1")) && rotaFinal != null
						&& !(rotaFinal.equals("") || rotaFinal.equals("-1"))){
			rotas = fachada.pesquisarRotas(codigoSetorInicial, rotaInicial, rotaFinal, idLocalidadeInicial, idCobrancaAcao);

		}else if(codigoSetorInicial != null && !codigoSetorInicial.equals("") && codigoSetorFinal != null && !codigoSetorFinal.equals("")){
			rotas = fachada.pesquisarRotasIntervaloSetor(codigoSetorInicial, codigoSetorFinal, idLocalidadeInicial, idCobrancaAcao);

		}else if(idLocalidadeInicial != null && !idLocalidadeInicial.equals("") && idLocalidadeFinal != null
						&& !idLocalidadeFinal.equals("")){
			rotas = fachada.pesquisarRotasIntervaloLocalidade(idLocalidadeInicial, idLocalidadeFinal, idCobrancaAcao);

		}else if(idUnidadeNegocio != null && !idUnidadeNegocio.equals("-1")){
			rotas = fachada.pesquisarRotasIntervaloUnidadeNegocio(idUnidadeNegocio, idCobrancaAcao);

		}else if(idGerencialRegional != null && !idGerencialRegional.equals("-1")){
			rotas = fachada.pesquisarRotasIntervaloGerencia(idGerencialRegional, idCobrancaAcao);
		}

		if(!rotas.isEmpty()){
			Iterator rotasIterator = rotas.iterator();
			while(rotasIterator.hasNext()){
				Integer rotaId = (Integer) rotasIterator.next();
				FiltroRotaAcaoCriterio filtroRotaAcaoCriterio = new FiltroRotaAcaoCriterio();

				filtroRotaAcaoCriterio.adicionarParametro(new ParametroSimples(FiltroRotaAcaoCriterio.ROTA_ID, rotaId));
				filtroRotaAcaoCriterio.adicionarParametro(new ParametroSimples(FiltroRotaAcaoCriterio.COBRANCA_ACAO_ID, idCobrancaAcao));

				Collection colecaoRAC = fachada.pesquisar(filtroRotaAcaoCriterio, RotaAcaoCriterio.class.getName());

				if(colecaoRAC != null && colecaoRAC.size() != 0){
					Iterator colecaoRACIterator = colecaoRAC.iterator();
					while(colecaoRACIterator.hasNext()){
						RotaAcaoCriterio rotaAcaoCriterio = (RotaAcaoCriterio) colecaoRACIterator.next();
						if(rotaAcaoCriterio != null){
							fachada.remover(rotaAcaoCriterio);
						}
					}
				}
			}

		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "rota sem associação");
		}

		// montando página de sucesso
		montarPaginaSucesso(httpServletRequest, "Rotas desassocidas do Critério de Cobrança com sucesso.",
						"Associar/Desassociar outras Rotas", "exibirAssociarRotasCriterioCobrancaAction.do?menu=sim");

		return retorno;
	}
}
