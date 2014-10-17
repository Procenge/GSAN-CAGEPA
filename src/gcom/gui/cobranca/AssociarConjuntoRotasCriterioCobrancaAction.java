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

import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaCriterio;
import gcom.cobranca.RotaAcaoCriterio;
import gcom.cobranca.RotaAcaoCriterioPK;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.Rota;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author compesa
 * @created 29 de Junho de 2004
 */
public class AssociarConjuntoRotasCriterioCobrancaAction
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
		// HttpSession sessao = httpServletRequest.getSession(false);
		// Usuario usuarioLogado = (Usuario)sessao.getAttribute(Usuario.USUARIO_LOGADO);

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
		String idCriterioCobranca = form.getIdCriterioCobranca();

		Collection rotas = null;

		if(idGrupoCobranca != null && !idGrupoCobranca.equals("-1")){
			rotas = fachada.pesquisarRotasSemCriterioIntervaloGrupo(idGrupoCobranca, idCobrancaAcao);

		}else if(rotaInicial != null && !(rotaInicial.equals("") || rotaInicial.equals("-1")) && rotaFinal != null
						&& !(rotaFinal.equals("") || rotaFinal.equals("-1"))){
			rotas = fachada.pesquisarRotasSemCriterio(codigoSetorInicial, rotaInicial, rotaFinal, idLocalidadeInicial, idCobrancaAcao);

		}else if(codigoSetorInicial != null && !codigoSetorInicial.equals("") && codigoSetorFinal != null && !codigoSetorFinal.equals("")){
			rotas = fachada.pesquisarRotasSemCriterioIntervaloSetor(codigoSetorInicial, codigoSetorFinal, idLocalidadeInicial,
							idCobrancaAcao);

		}else if(idLocalidadeInicial != null && !idLocalidadeInicial.equals("") && idLocalidadeFinal != null
						&& !idLocalidadeFinal.equals("")){
			rotas = fachada.pesquisarRotasSemCriterioIntervaloLocalidade(idLocalidadeInicial, idLocalidadeFinal, idCobrancaAcao);

		}else if(idUnidadeNegocio != null && !idUnidadeNegocio.equals("-1")){
			rotas = fachada.pesquisarRotasSemCriterioIntervaloUnidadeNegocio(idUnidadeNegocio, idCobrancaAcao);

		}else if(idGerencialRegional != null && !idGerencialRegional.equals("-1")){
			rotas = fachada.pesquisarRotasSemCriterioIntervaloGerencia(idGerencialRegional, idCobrancaAcao);
		}

		if(!rotas.isEmpty()){
			Iterator iterator = rotas.iterator();
			Integer id = null;
			Rota rota = null;
			CobrancaAcao cobrancaAcao = null;
			CobrancaCriterio cobrancaCriterio = null;
			RotaAcaoCriterio rotaAcaoCriterio = null;
			while(iterator.hasNext()){
				id = (Integer) iterator.next();
				// -----
				rota = new Rota();
				rota.setId(id);
				cobrancaAcao = new CobrancaAcao();
				cobrancaAcao.setId(Integer.valueOf(idCobrancaAcao));
				cobrancaCriterio = new CobrancaCriterio();
				cobrancaCriterio.setId(Integer.valueOf(idCriterioCobranca));
				// -----
				rotaAcaoCriterio = new RotaAcaoCriterio();
				rotaAcaoCriterio.setRota(rota);
				rotaAcaoCriterio.setCobrancaAcao(cobrancaAcao);
				rotaAcaoCriterio.setCobrancaCriterio(cobrancaCriterio);
				rotaAcaoCriterio.setUltimaAlteracao(new Date());

				RotaAcaoCriterioPK rotaAcaoCriterioPK = new RotaAcaoCriterioPK();
				rotaAcaoCriterioPK.setRotaId(rota.getId());
				rotaAcaoCriterioPK.setCobrancaAcaoId(cobrancaAcao.getId());
				rotaAcaoCriterio.setComp_id(rotaAcaoCriterioPK);

				fachada.inserir(rotaAcaoCriterio);
			}

		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "rota sem associação");
		}

		// montando página de sucesso
		montarPaginaSucesso(httpServletRequest, "Rotas associdas ao Critério de Cobrança com sucesso.",
						"Associar/Desassociar outras Rotas", "exibirAssociarRotasCriterioCobrancaAction.do?menu=sim");

		return retorno;
	}
}
