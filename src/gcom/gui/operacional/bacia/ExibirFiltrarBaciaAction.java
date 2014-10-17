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

package gcom.gui.operacional.bacia;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.FiltroSistemaEsgoto;
import gcom.operacional.FiltroSubsistemaEsgoto;
import gcom.operacional.SistemaEsgoto;
import gcom.operacional.SubsistemaEsgoto;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author isilva
 * @date 02/02/2011
 */
public class ExibirFiltrarBaciaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("filtrarBacia");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		if(sessao.getAttribute("consulta") != null){
			sessao.removeAttribute("consulta");
		}

		FiltrarBaciaActionForm pesquisarFiltrarBaciaActionForm = (FiltrarBaciaActionForm) actionForm;

		atualizaCampoCombo(pesquisarFiltrarBaciaActionForm, sessao, httpServletRequest);

		// String objetoConsulta = (String) httpServletRequest.getParameter("objetoConsulta");
		//
		// httpServletRequest.setAttribute("nomeCampo", "idLocalidade");
		//
		// if (!Util.isVazioOuBranco(objetoConsulta)) {
		//
		// String idLocalidade = null;
		//
		// // Localidade
		// FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		//
		// // Recebe o valor do campo localidadeID do formul�rio.
		// idLocalidade = pesquisarFiltrarBaciaActionForm.getIdLocalidade();
		//
		// if (!Util.isVazioOuBranco(idLocalidade)) {
		// filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID,
		// idLocalidade));
		// filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
		// ConstantesSistema.INDICADOR_USO_ATIVO));
		//
		// // Retorna localidade
		// Collection<Localidade> colecaoPesquisa = fachada.pesquisar(filtroLocalidade,
		// Localidade.class.getName());
		//
		// if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
		// // Localidade nao encontrada
		// // Limpa os campos idLocalidade e localidadeNome do
		// // formul�rio
		// httpServletRequest.setAttribute("corLocalidade", "exception");
		// pesquisarFiltrarBaciaActionForm.setIdLocalidade("");
		// pesquisarFiltrarBaciaActionForm.setDescricaoLocalidade("Localidade Inexistente.");
		// httpServletRequest.setAttribute("nomeCampo", "idLocalidade");
		// } else {
		// httpServletRequest.setAttribute("corLocalidade", "valor");
		// Localidade objetoLocalidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);
		// pesquisarFiltrarBaciaActionForm.setIdLocalidade(String.valueOf(objetoLocalidade.getId()));
		// pesquisarFiltrarBaciaActionForm.setDescricaoLocalidade(objetoLocalidade.getDescricao());
		// }
		// }
		// } else {
		//
		// // Limpando o formul�rio
		// pesquisarFiltrarBaciaActionForm.setIdLocalidade("");
		// pesquisarFiltrarBaciaActionForm.setDescricaoLocalidade("");
		// pesquisarFiltrarBaciaActionForm.setIndicadorUso(ConstantesSistema.TODOS.toString());
		// }

		httpServletRequest.removeAttribute("i");

		String primeiraVez = httpServletRequest.getParameter("menu");
		if(primeiraVez != null && !primeiraVez.equals("")){
			sessao.setAttribute("indicadorAtualizar", ConstantesSistema.INDICADOR_USO_ATIVO.toString());
			pesquisarFiltrarBaciaActionForm.setIndicadorUso(ConstantesSistema.TODOS.toString());
		}
		// se voltou da tela de Atualizar Localidade Dado Operacional
		if(sessao.getAttribute("voltar") != null && sessao.getAttribute("voltar").equals("filtrar")){
			sessao.setAttribute("indicadorAtualizar", ConstantesSistema.INDICADOR_USO_ATIVO.toString());
		}
		// devolve o mapeamento de retorno
		return retorno;
	}

	/**
	 * @author isilva
	 * @date 02/02/2011
	 * @param pesquisarFiltrarBaciaActionForm
	 * @param sessao
	 * @param httpServletRequest
	 */
	private void atualizaCampoCombo(FiltrarBaciaActionForm pesquisarFiltrarBaciaActionForm, HttpSession sessao,
					HttpServletRequest httpServletRequest){

		String limparCampos = (String) httpServletRequest.getParameter("limparCampos");
		String objetoConsulta = (String) httpServletRequest.getParameter("objetoConsulta");

		if(objetoConsulta != null && !objetoConsulta.trim().equals("")){
			switch(Integer.parseInt(objetoConsulta)){
				case 1:
					// Recebe o valor do campo Sistema de Esgoto do formul�rio.
					String idSistemaEsgoto = pesquisarFiltrarBaciaActionForm.getIdSistemaEsgoto();

					FiltroSubsistemaEsgoto filtroSubsistemaEsgoto = new FiltroSubsistemaEsgoto();
					filtroSubsistemaEsgoto.setCampoOrderBy(FiltroSubsistemaEsgoto.DESCRICAO);
					filtroSubsistemaEsgoto
									.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.SISTEMAESGOTO_ID, idSistemaEsgoto));
					filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					Collection colecaoSubsistemaEsgoto = this.getFachada().pesquisar(filtroSubsistemaEsgoto,
									SubsistemaEsgoto.class.getName());
					sessao.setAttribute("colecaoSubsistemaEsgoto", colecaoSubsistemaEsgoto);

					break;

				default:
					break;
			}
		}else{
			if(sessao.getAttribute("colecaoSistemaEsgoto") == null){
				FiltroSistemaEsgoto filtroSistemaEsgoto = new FiltroSistemaEsgoto();
				filtroSistemaEsgoto.setCampoOrderBy(FiltroSistemaEsgoto.DESCRICAO);
				filtroSistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSistemaEsgoto.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				Collection colecaoSistemaEsgoto = this.getFachada().pesquisar(filtroSistemaEsgoto, SistemaEsgoto.class.getName());

				if(colecaoSistemaEsgoto == null || colecaoSistemaEsgoto.isEmpty()){
					throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "SISTEMA_ESGOTO");
				}else{
					sessao.setAttribute("colecaoSistemaEsgoto", colecaoSistemaEsgoto);
				}
			}
		}

		// Bot�o Desfazer
		String desfazer = httpServletRequest.getParameter("desfazer");

		if((limparCampos == null || limparCampos.trim().equalsIgnoreCase("")) || (desfazer != null && desfazer.equalsIgnoreCase("S"))){
			// Limpando o formulario
			pesquisarFiltrarBaciaActionForm.setCodigo("");
			pesquisarFiltrarBaciaActionForm.setDescricao("");
			pesquisarFiltrarBaciaActionForm.setDescricaoAbreviada("");

			// Campos do tipo lista no formul�rio
			pesquisarFiltrarBaciaActionForm.setIdSistemaEsgoto("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			pesquisarFiltrarBaciaActionForm.setIdSubsistemaEsgoto("" + ConstantesSistema.NUMERO_NAO_INFORMADO);

			sessao.removeAttribute("colecaoSubsistemaEsgoto");
		}
	}

}
