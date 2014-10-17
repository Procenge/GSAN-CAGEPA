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

package gcom.gui.cobranca;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author compesa
 * @created 29 de Junho de 2004
 */
public class SelecionarAssociarConjuntoRotasCriterioCobrancaAction
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
		ActionForward retorno = actionMapping.findForward("exibirAssociarRotasCriterioCobrancaSelecionar");

		AssociarConjuntoRotasCriterioCobrancaActionForm form = (AssociarConjuntoRotasCriterioCobrancaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

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

		String[] qtdRotasArray = null;

		String qtdRotas = "";
		String qtdRotasComCriterio = "";
		String qtdRotasSemCriterio = "";

		if(idGrupoCobranca != null && !idGrupoCobranca.equals("-1")){

			qtdRotasArray = fachada.pesquisarQuantidadeRotasIntervaloGrupo(idGrupoCobranca, idCobrancaAcao);

		}else if(rotaInicial != null && !rotaInicial.equals("-1") && rotaFinal != null && !rotaFinal.equals("-1")){

			qtdRotasArray = fachada.pesquisarQuantidadeRotas(codigoSetorInicial, rotaInicial, rotaFinal, idLocalidadeInicial,
							idCobrancaAcao);

		}else if(codigoSetorInicial != null && !codigoSetorInicial.equals("") && codigoSetorFinal != null && !codigoSetorFinal.equals("")){

			qtdRotasArray = fachada.pesquisarQuantidadeRotasIntervaloSetor(codigoSetorInicial, codigoSetorFinal, idLocalidadeInicial,
							idCobrancaAcao);

		}else if(idLocalidadeInicial != null && !idLocalidadeInicial.equals("") && idLocalidadeFinal != null
						&& !idLocalidadeFinal.equals("")){

			qtdRotasArray = fachada.pesquisarQuantidadeRotasIntervaloLocalidade(idLocalidadeInicial, idLocalidadeFinal, idCobrancaAcao);

		}else if(idUnidadeNegocio != null && !idUnidadeNegocio.equals("-1")){

			qtdRotasArray = fachada.pesquisarQuantidadeRotasIntervaloUnidadeNegocio(idUnidadeNegocio, idCobrancaAcao);

		}else if(idGerencialRegional != null && !idGerencialRegional.equals("-1")){

			qtdRotasArray = fachada.pesquisarQuantidadeRotasIntervaloGerencia(idGerencialRegional, idCobrancaAcao);

		}

		if(qtdRotasArray != null){
			qtdRotas = qtdRotasArray[0];
			qtdRotasComCriterio = qtdRotasArray[1];
			qtdRotasSemCriterio = qtdRotasArray[2];
			form.setQtdRotasSelecionadas(qtdRotas);
			form.setQtdRotasComCriterio(qtdRotasComCriterio);
			form.setQtdRotasSemCriterio(qtdRotasSemCriterio);
		}else{
			form.setQtdRotasSelecionadas("0");
			form.setQtdRotasComCriterio("0");
			form.setQtdRotasSemCriterio("0");
		}

		sessao.setAttribute("qtdRotas", form.getQtdRotasSelecionadas());
		sessao.setAttribute("qtdRotasCom", form.getQtdRotasComCriterio());
		sessao.setAttribute("qtdRotasSem", form.getQtdRotasSemCriterio());

		return retorno;
	}
}
