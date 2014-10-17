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

package gcom.gui.faturamento.consumofaixaareacategoria;

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.fachada.Fachada;
import gcom.faturamento.consumofaixaareacategoria.ConsumoFaixaAreaCategoria;
import gcom.faturamento.consumofaixaareacategoria.FiltroConsumoFaixaAreaCategoria;
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
 * [UC3006] MANTER Consumo por Faixa de Área e Categoria [SB0001]Atualizar Consumo por Faixa de Área
 * e Categoria
 * 
 * @author Ailton Sousa
 * @date 02/03/2011
 */
public class ExibirAtualizarConsumoFaixaAreaCategoriaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarConsumoFaixaAreaCategoria");

		AtualizarConsumoFaixaAreaCategoriaActionForm atualizarConsumoFaixaAreaCategoriaActionForm = (AtualizarConsumoFaixaAreaCategoriaActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		FiltroCategoria filtroCategoria = new FiltroCategoria();

		filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroCategoria.setCampoOrderBy(FiltroCategoria.DESCRICAO);

		Collection colecaoCategoria = fachada.pesquisar(filtroCategoria, Categoria.class.getName());

		httpServletRequest.setAttribute("colecaoCategoria", colecaoCategoria);

		ConsumoFaixaAreaCategoria consumoFaixaAreaCategoria = null;

		String idConsumoFaixaAreaCategoria = null;

		if(httpServletRequest.getParameter("idConsumoFaixaAreaCategoria") != null){
			// tela do manter
			idConsumoFaixaAreaCategoria = (String) httpServletRequest.getParameter("idConsumoFaixaAreaCategoria");
			sessao.setAttribute("idConsumoFaixaAreaCategoria", idConsumoFaixaAreaCategoria);
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirManterConsumoFaixaAreaCategoriaAction.do");
		}else if(sessao.getAttribute("idConsumoFaixaAreaCategoria") != null){
			// tela do filtrar
			idConsumoFaixaAreaCategoria = (String) sessao.getAttribute("idConsumoFaixaAreaCategoria");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarConsumoFaixaAreaCategoriaAction.do");
		}else if(httpServletRequest.getParameter("idRegistroInseridoAtualizar") != null){
			// link na tela de sucesso do inserir sistema esgoto
			idConsumoFaixaAreaCategoria = (String) httpServletRequest.getParameter("idRegistroInseridoAtualizar");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarConsumoFaixaAreaCategoriaAction.do?menu=sim");
		}

		if(idConsumoFaixaAreaCategoria == null){

			if(sessao.getAttribute("idRegistroAtualizar") != null){
				consumoFaixaAreaCategoria = (ConsumoFaixaAreaCategoria) sessao.getAttribute("idRegistroAtualizar");
			}else{
				idConsumoFaixaAreaCategoria = (String) httpServletRequest.getParameter("idConsumoFaixaAreaCategoria").toString();
			}
		}

		// Inicio da parte que verifica se vem da página de CONSUMO_FAIXA_AREA_CATEGORIA_manter.jsp
		if(consumoFaixaAreaCategoria == null){

			if(idConsumoFaixaAreaCategoria != null && !idConsumoFaixaAreaCategoria.equals("")){

				FiltroConsumoFaixaAreaCategoria filtroConsumoFaixaAreaCategoria = new FiltroConsumoFaixaAreaCategoria();

				filtroConsumoFaixaAreaCategoria.adicionarCaminhoParaCarregamentoEntidade("categoria");

				filtroConsumoFaixaAreaCategoria.adicionarParametro(new ParametroSimples(FiltroConsumoFaixaAreaCategoria.ID,
								idConsumoFaixaAreaCategoria));

				Collection colecaoConsumoFaixaAreaCategoria = fachada.pesquisar(filtroConsumoFaixaAreaCategoria,
								ConsumoFaixaAreaCategoria.class.getName());

				if(colecaoConsumoFaixaAreaCategoria != null && !colecaoConsumoFaixaAreaCategoria.isEmpty()){

					consumoFaixaAreaCategoria = (ConsumoFaixaAreaCategoria) Util.retonarObjetoDeColecao(colecaoConsumoFaixaAreaCategoria);

				}
			}
		}

		// O Consumo por Faixa de Área e Categoria foi encontrado
		atualizarConsumoFaixaAreaCategoriaActionForm.setCategoria(consumoFaixaAreaCategoria.getCategoria().getId().toString());

		atualizarConsumoFaixaAreaCategoriaActionForm.setFaixaInicialArea(consumoFaixaAreaCategoria.getFaixaInicialArea().toString());

		atualizarConsumoFaixaAreaCategoriaActionForm.setFaixaFinalArea(consumoFaixaAreaCategoria.getFaixaFinalArea().toString());

		atualizarConsumoFaixaAreaCategoriaActionForm.setConsumoEstimadoArea(consumoFaixaAreaCategoria.getConsumoEstimadoArea().toString());

		atualizarConsumoFaixaAreaCategoriaActionForm.setIndicadorUso("" + consumoFaixaAreaCategoria.getIndicadorUso());

		atualizarConsumoFaixaAreaCategoriaActionForm.setUltimaAlteracao(Util.formatarDataComHora(consumoFaixaAreaCategoria
						.getUltimaAlteracao()));

		sessao.setAttribute("consumoFaixaAreaCategoria", consumoFaixaAreaCategoria);

		httpServletRequest.setAttribute("idConsumoFaixaAreaCategoria", idConsumoFaixaAreaCategoria);

		// Fim da parte que verifica se vem da página de CONSUMO_FAIXA_AREA_CATEGORIA_manter.jsp

		return retorno;
	}

}
