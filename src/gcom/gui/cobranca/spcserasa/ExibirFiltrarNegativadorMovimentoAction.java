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
 * Yara Taciane de Souza
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

package gcom.gui.cobranca.spcserasa;

import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.Negativador;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.spcserasa.FiltroNegativador;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que define o pré-processamento da página de filtrar do Movimento do Negativador
 * 
 * @author Yara Taciane de Souza
 * @created 21/01/2008
 */
public class ExibirFiltrarNegativadorMovimentoAction
				extends GcomAction {

	/**
	 * Este caso de uso permite o filtro de um Movimento do Negativador
	 * [UC0682] Filtrar Movimento do Negativador
	 * 
	 * @author Yara Taciane de Souza
	 * @date 21/01/2008
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("filtrarNegativadorMovimento");

		FiltrarNegativadorMovimentoActionForm filtrarNegativadorMovimentoActionForm = (FiltrarNegativadorMovimentoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		Collection colecaoNegativador = (Collection) sessao.getAttribute("colecaoNegativador");

		if(colecaoNegativador == null){

			FiltroNegativador filtroNegativador = new FiltroNegativador();
			filtroNegativador.setCampoOrderBy(FiltroNegativador.CLIENTE_NOME);
			filtroNegativador.adicionarCaminhoParaCarregamentoEntidade("cliente");
			filtroNegativador.setConsultaSemLimites(true);

			colecaoNegativador = fachada.pesquisar(filtroNegativador, Negativador.class.getName());

			if(colecaoNegativador == null || colecaoNegativador.isEmpty()){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "NEGATIVADOR");
			}else{
				sessao.setAttribute("colecaoNegativador", colecaoNegativador);
			}
		}

		filtrarNegativadorMovimentoActionForm.setTipoMovimento(ConstantesSistema.TODOS.toString());
		filtrarNegativadorMovimentoActionForm.setMovimento(ConstantesSistema.TODOS.toString());
		filtrarNegativadorMovimentoActionForm.setMovimentoRegistro(ConstantesSistema.TODOS.toString());
		filtrarNegativadorMovimentoActionForm.setMovimentoCorrigido(ConstantesSistema.TODOS.toString());

		// se tem id do Imovel entao pega o nome
		if(!"".equals(filtrarNegativadorMovimentoActionForm.getIdImovel()) && filtrarNegativadorMovimentoActionForm.getIdImovel() != null){

			sessao.setAttribute("manterDadosSesao", "false");

			FiltroImovel filtroImovel = new FiltroImovel();

			// coloca parametro no filtro
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, Integer.valueOf(filtrarNegativadorMovimentoActionForm
							.getIdImovel())));
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);

			// pesquisa
			Collection coll = Fachada.getInstancia().pesquisar(filtroImovel, Imovel.class.getName());
			if(coll != null && !coll.isEmpty()){
				// O Imovel foi encontrado
				// inserirImovelFiltrarActionForm.set("idImovel", ((Imovel) ((List)
				// Imovels).get(0)).getId().toString());
				filtrarNegativadorMovimentoActionForm.setNomeImovel(((Imovel) ((List) coll).get(0)).getInscricaoFormatada());
				sessao.setAttribute("nomeImovel", ((Imovel) ((List) coll).get(0)).getInscricaoFormatada());
				sessao.setAttribute("idImovel", filtrarNegativadorMovimentoActionForm.getIdImovel());

				filtrarNegativadorMovimentoActionForm.setImovelNaoEncontrado("false");
			}else{
				filtrarNegativadorMovimentoActionForm.setImovelNaoEncontrado("true");
				filtrarNegativadorMovimentoActionForm.setNomeImovel("");
			}
		}else{
			filtrarNegativadorMovimentoActionForm.setNomeImovel("");
			sessao.removeAttribute("idImovel");
			sessao.removeAttribute("nomeImovel");
		}

		if(httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer").equalsIgnoreCase("S")){

			// -------------- bt DESFAZER ---------------
			filtrarNegativadorMovimentoActionForm.setIdNegativador("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			filtrarNegativadorMovimentoActionForm.setTipoMovimento(ConstantesSistema.TODOS.toString());
			filtrarNegativadorMovimentoActionForm.setNumeroSequencialArquivo("");
			filtrarNegativadorMovimentoActionForm.setDataProcessamentoFinal("");
			filtrarNegativadorMovimentoActionForm.setDataProcessamentoInicial("");
			filtrarNegativadorMovimentoActionForm.setMovimento(ConstantesSistema.TODOS.toString());
			filtrarNegativadorMovimentoActionForm.setMovimentoRegistro(ConstantesSistema.TODOS.toString());
			filtrarNegativadorMovimentoActionForm.setMovimentoCorrigido(ConstantesSistema.TODOS.toString());

			sessao.removeAttribute("caminhoRetornoTelaPesquisa");
		}
		sessao.removeAttribute("caminhoRetornoTelaPesquisa");
		return retorno;
	}
}