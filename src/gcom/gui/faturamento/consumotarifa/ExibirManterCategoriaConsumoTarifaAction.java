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
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gui.faturamento.consumotarifa;

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.FiltroSubCategoria;
import gcom.cadastro.imovel.Subcategoria;
import gcom.fachada.Fachada;
import gcom.faturamento.consumotarifa.ConsumoTarifaFaixa;
import gcom.faturamento.consumotarifa.ConsumoTarifaVigencia;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifaFaixa;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.faturamento.consumotarifa.bean.CategoriaFaixaConsumoTarifaHelper;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirManterCategoriaConsumoTarifaAction
				extends GcomAction {

	/**
	 * @author eduardo henrique
	 * @date 08/07/2008
	 *       Alteração para formatação do valor com 4 casas decimais
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("manterCategoriaConsumoTarifa");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);
		
		InserirCategoriaConsumoTarifaActionForm form = (InserirCategoriaConsumoTarifaActionForm) actionForm;

		if(httpServletRequest.getParameter("form") != null){
			if(httpServletRequest.getParameter("form").equals("exibirManterCategoriaFaixaConsumoTarifaAction")){
				sessao.setAttribute("consumoMinimo", form.getConsumoMinimo());
				return actionMapping.findForward("exibirManterCategoriaFaixaConsumoTarifaAction");
			}
		}

		String indicadorTarifaCosumoPorSubCategoria = null;
		try{
			indicadorTarifaCosumoPorSubCategoria = (String) ParametroFaturamento.P_INDICADOR_TARIFA_CONSUMO_SUBCATEGORIA.executar();
			if(indicadorTarifaCosumoPorSubCategoria.equals(ConstantesSistema.SIM.toString())){
				sessao.setAttribute("indicadorTarifaCosumoPorSubCategoria", "S");
			}else{
				sessao.removeAttribute("indicadorTarifaCosumoPorSubCategoria");
			}

		}catch(ControladorException e){

			throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
		}

		String inTarifaEsgotoPropria = null;
		
		// Atualizando a colecaoVigencia para atualizar os dados da vigência da tela de Manter Consumo Tarifa
		Collection <ConsumoTarifaVigencia> colecaoVigencia = (Collection <ConsumoTarifaVigencia>) sessao.getAttribute("colecaoVigencia");

		// Descrição
		if(httpServletRequest.getParameter("parametroDescricao") != null && !httpServletRequest.getParameter("parametroDescricao").equals("")){

			colecaoVigencia.iterator().next().getConsumoTarifa().setDescricao(httpServletRequest.getParameter("parametroDescricao"));
		}
		
		// Data Vigência
		if(httpServletRequest.getParameter("parametroVigencia") != null && !httpServletRequest.getParameter("parametroVigencia").equals("")){

			colecaoVigencia.iterator().next()
							.setDataVigencia(Util.converterStringParaData((String) httpServletRequest.getParameter("parametroVigencia")));
		}

		// Tarifa de Esgoto Própria
		if(httpServletRequest.getParameter("indicadorTarifaEsgotoPropria") != null
						&& !httpServletRequest.getParameter("indicadorTarifaEsgotoPropria").equals("")){

			colecaoVigencia.iterator().next().getConsumoTarifa()
							.setIcTarifaEsgotoPropria(Short.parseShort(httpServletRequest.getParameter("indicadorTarifaEsgotoPropria")));
		}

		// se recuperou no request, seta na sessão
		if(httpServletRequest.getParameter("indicadorTarifaEsgotoPropria") != null){
			inTarifaEsgotoPropria = httpServletRequest.getParameter("indicadorTarifaEsgotoPropria");
		}else{
			inTarifaEsgotoPropria = (String) sessao.getAttribute("indicadorTarifaEsgotoPropria");
		}

		// se o indicador for SIM (1), seta na sessão
		if(inTarifaEsgotoPropria != null && inTarifaEsgotoPropria.equals(ConstantesSistema.SIM.toString())){
			sessao.setAttribute("indicadorTarifaEsgotoPropria", ConstantesSistema.SIM.toString());
		}else{
			sessao.removeAttribute("indicadorTarifaEsgotoPropria");
		}

		// ----------------FILTRO CATEGORIAS DE ESTABELECIMENTO - PROPULAR
		// DROPDOWN ------
		CategoriaFaixaConsumoTarifaHelper categoriaFaixaConsumoTarifaHelperAtualizacao = null;

		if("sim".equals(httpServletRequest.getParameter("trava"))){
			httpServletRequest.setAttribute("travar", "false");
			sessao.setAttribute("trava", "sim");
			sessao.setAttribute("novaCategoria", "nao");
		}else if((httpServletRequest.getParameter("trava") != null) && !("sim".equals(httpServletRequest.getParameter("trava")))){
			httpServletRequest.setAttribute("travar", "false");
			sessao.setAttribute("novaCategoria", "sim");
		}

		if(httpServletRequest.getParameter("posicao") != null){
			categoriaFaixaConsumoTarifaHelperAtualizacao = pesquisarPosicaoCategoria(Long.parseLong(httpServletRequest
							.getParameter("posicao")), (Collection) sessao.getAttribute("colecaoCategoria"));

			sessao.setAttribute("categoriaFaixaConsumoTarifaHelperAtualizacao", categoriaFaixaConsumoTarifaHelperAtualizacao);
		}

		if(httpServletRequest.getAttribute("parametroVigencia") != null){
			String Vigencia = (String) httpServletRequest.getAttribute("parametroVigencia");
			sessao.setAttribute("Vigencia", Vigencia);
		}

		FiltroCategoria filtroCategoria = new FiltroCategoria();

		// FiltroConsumoTarifaCategoria filtroConsumoTarifaCategoria = new
		// FiltroConsumoTarifaCategoria();

		if((httpServletRequest.getParameter("limpa") != null) && (httpServletRequest.getParameter("limpa").equals("1"))){
			form.setValorTarifaMinima(null);
			form.setConsumoMinimo(null);
			form.setValorTarifaMinimaEsgoto(null);

			form.setSlcCategoria(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
			form.setSlcSubCategoria(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));

			sessao.removeAttribute("consumoMinimo");
			sessao.removeAttribute("colecaoFaixa");
			sessao.setAttribute("novaCategoria", "sim");
		}

		filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroCategoria.setCampoOrderBy(FiltroCategoria.DESCRICAO);

		String pQuantidadeDecimaisValorTarifa = null;

		try{

			pQuantidadeDecimaisValorTarifa = (String) ParametroFaturamento.P_QUANTIDADE_DECIMAIS_VALOR_TARIFA.executar();
		}catch(ControladorException e){

			throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
		}

		if(categoriaFaixaConsumoTarifaHelperAtualizacao != null){
			form.setSlcCategoria(""
							+ categoriaFaixaConsumoTarifaHelperAtualizacao.getConsumoTarifaCategoria().getCategoria().getId());

			if(categoriaFaixaConsumoTarifaHelperAtualizacao.getConsumoTarifaCategoria().getSubCategoria() != null){
				form.setSlcSubCategoria(""
								+ categoriaFaixaConsumoTarifaHelperAtualizacao.getConsumoTarifaCategoria().getSubCategoria().getId());
			}else{
				form.setSlcSubCategoria("");
			}

			form.setConsumoMinimo(""
							+ categoriaFaixaConsumoTarifaHelperAtualizacao.getConsumoTarifaCategoria().getNumeroConsumoMinimo());
			form.setValorTarifaMinima(Util.formatarMoedaReal(
							categoriaFaixaConsumoTarifaHelperAtualizacao.getConsumoTarifaCategoria().getValorTarifaMinima(),
							Util.obterInteger(pQuantidadeDecimaisValorTarifa)));
			form.setValorTarifaMinimaEsgoto(Util.formatarMoedaReal(
							categoriaFaixaConsumoTarifaHelperAtualizacao.getConsumoTarifaCategoria().getValorTarifaMinimaEsgoto(),
							Util.obterInteger(pQuantidadeDecimaisValorTarifa)));

			if(categoriaFaixaConsumoTarifaHelperAtualizacao.getColecaoFaixas() == null
							|| categoriaFaixaConsumoTarifaHelperAtualizacao.getColecaoFaixas().isEmpty()){
				FiltroConsumoTarifaFaixa filtroConsumoTarifaFaixa = new FiltroConsumoTarifaFaixa();
				filtroConsumoTarifaFaixa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifaFaixa.CONSUMO_TARIFA_CATEGORIA_ID,
								categoriaFaixaConsumoTarifaHelperAtualizacao.getConsumoTarifaCategoria().getId()));
				Collection colecaoFaixa = fachada.pesquisar(filtroConsumoTarifaFaixa, ConsumoTarifaFaixa.class.getName());

				List listColecaoFaixa = new ArrayList();
				listColecaoFaixa.addAll(colecaoFaixa);

				Collections.sort((List) listColecaoFaixa, new Comparator() {

					public int compare(Object a, Object b){

						Integer codigo1 = ((ConsumoTarifaFaixa) a).getNumeroConsumoFaixaIFim();
						Integer codigo2 = ((ConsumoTarifaFaixa) b).getNumeroConsumoFaixaIFim();

						return codigo1.compareTo(codigo2);
					}
				});

				sessao.setAttribute("colecaoFaixa", listColecaoFaixa);

			}else{

				Collection colecaoFaixa = categoriaFaixaConsumoTarifaHelperAtualizacao.getColecaoFaixas();

				List listColecaoFaixa = new ArrayList();
				listColecaoFaixa.addAll(colecaoFaixa);

				Collections.sort(listColecaoFaixa, new Comparator() {

					public int compare(Object a, Object b){

						Integer codigo1 = ((ConsumoTarifaFaixa) a).getNumeroConsumoFaixaIFim();
						Integer codigo2 = ((ConsumoTarifaFaixa) b).getNumeroConsumoFaixaIFim();

						return codigo1.compareTo(codigo2);
					}
				});

				sessao.setAttribute("colecaoFaixa", listColecaoFaixa);

			}
		}

		Collection colecaoCategoriaLista = fachada.pesquisar(filtroCategoria, Categoria.class.getName());

		sessao.setAttribute("colecaoCategoriaLista", colecaoCategoriaLista);
		
		Collection<Subcategoria> colecaoSubCategoria = new ArrayList<Subcategoria>();
		if(form.getSlcCategoria() != null && !form.getSlcCategoria().toString().equals("")
						&& !form.getSlcCategoria().toString().equals("-1")){
			FiltroSubCategoria filtroSubCategoria = new FiltroSubCategoria();
			filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CATEGORIA_ID, Integer.valueOf(form
							.getSlcCategoria())));
			filtroSubCategoria.setCampoOrderBy(FiltroSubCategoria.DESCRICAO);

			colecaoSubCategoria = fachada.pesquisar(filtroSubCategoria, Subcategoria.class.getName());
		}

		sessao.setAttribute("colecaoSubCategoria", colecaoSubCategoria);

		return retorno;
	}

	private CategoriaFaixaConsumoTarifaHelper pesquisarPosicaoCategoria(long posicao, Collection colecaoCategoriaSessao){

		CategoriaFaixaConsumoTarifaHelper retorno = null;

		if(colecaoCategoriaSessao != null){

			Iterator colecaoConsumoTarifaCategoriaIterator = colecaoCategoriaSessao.iterator();

			while(colecaoConsumoTarifaCategoriaIterator.hasNext()){
				CategoriaFaixaConsumoTarifaHelper categoriaFaixaConsumoTarifaHelper = (CategoriaFaixaConsumoTarifaHelper) colecaoConsumoTarifaCategoriaIterator
								.next();
				if(obterTimestampIdObjeto(categoriaFaixaConsumoTarifaHelper.getConsumoTarifaCategoria()) == posicao){
					retorno = categoriaFaixaConsumoTarifaHelper;
					break;
				}
			}
		}

		return retorno;
	}
}
