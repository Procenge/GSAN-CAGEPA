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

package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 * @created 25 de Maio de 2004
 */
public class ExibirInserirImovelSubCategoriaAction
				extends GcomAction {

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws ControladorException{

		ActionForward retorno = actionMapping.findForward("inserirImovelSubCategoria");

		// obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		// Parâmtro utilizado para definir se na aba de subcategoria o botão CADASTRO IMOVEL CONSUMO
		// FAIXA AREA CATG será exibido ou não.
		String indicadorImovelConsumoFaixaAreaCatg = ParametroCadastro.P_INDICADOR_IMOVEL_CONSUMO_FAIXA_AREA_CATG.executar();

		// Obtém o action form
		DynaValidatorForm inserirImovelActionForm = (DynaValidatorForm) actionForm;

		Collection colecaoImovelSubCategorias = null;

		// Obtém a fachada
		Fachada fachada = Fachada.getInstancia();

		// Criação das coleções
		Collection categorias = null;
		Collection subCategorias = null;

		String botaoAdicionar = httpServletRequest.getParameter("botaoAdicionar");

		if(botaoAdicionar == null && httpServletRequest.getParameter("confirmado") != null){
			botaoAdicionar = (String) sessao.getAttribute("botaoAdicionar");
		}else{
			sessao.removeAttribute("botaoAdicionar");
		}

		// Testa se a coleçao ja esta na sessao
		if(sessao.getAttribute("colecaoImovelSubCategorias") != null){
			colecaoImovelSubCategorias = (Collection) sessao.getAttribute("colecaoImovelSubCategorias");
		}else{
			colecaoImovelSubCategorias = new ArrayList();
		}

		if(botaoAdicionar != null && !botaoAdicionar.equals("")){
			short quantidadeEconomia = new Short(inserirImovelActionForm.get("quantidadeEconomia").toString()).shortValue();

			FiltroCategoria filtroCategoria = new FiltroCategoria();
			filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, inserirImovelActionForm.get("idCategoria")));

			Collection colecaoCategoria = fachada.pesquisar(filtroCategoria, Categoria.class.getName());
			Categoria categoriaNaBase = (Categoria) Util.retonarObjetoDeColecao(colecaoCategoria);

			if(categoriaNaBase.getQuantidadeMaximoEconomiasValidacao() < new Integer(quantidadeEconomia)){
				if(httpServletRequest.getParameter("confirmado") == null){

					httpServletRequest.setAttribute("destino", "4");
					sessao.setAttribute("botaoAdicionar", botaoAdicionar);
					retorno = montarPaginaConfirmacaoWizard("atencao.usuario.limite_ultrapassado_economias_validacao", httpServletRequest,
									actionMapping, "" + categoriaNaBase.getQuantidadeMaximoEconomiasValidacao());

				}else{
					if(httpServletRequest.getParameter("confirmado").equalsIgnoreCase("ok")){
						adicionaObjetoColecao(inserirImovelActionForm, sessao, colecaoImovelSubCategorias, quantidadeEconomia,
										categoriaNaBase);
						sessao.removeAttribute(botaoAdicionar);
					}
				}
			}else{
				adicionaObjetoColecao(inserirImovelActionForm, sessao, colecaoImovelSubCategorias, quantidadeEconomia, categoriaNaBase);
			}
		}else{
			// qualquer coisa que não seja adicionar passa por aqui

			Boolean indicadorContratoConsumo = (Boolean) inserirImovelActionForm.get("indicadorContratoConsumo");

			String atualizarIndicador = httpServletRequest.getParameter("atualizarIndicador");
			if(atualizarIndicador != null && atualizarIndicador.equals("S")){
				// clicou em contrato de consumo

				if(indicadorContratoConsumo != null && indicadorContratoConsumo){
					FiltroCategoria filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, String
									.valueOf(ConstantesSistema.INDICADOR_CONTRATO_CONSUMO_CATEGORIA)));
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					Categoria categoria = (Categoria) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroCategoria, Categoria.class
									.getName()));

					if(categoria != null){
						inserirImovelActionForm.set("idCategoria", categoria.getId().toString());
						inserirImovelActionForm.set("textoSelecionadoCategoria", categoria.getDescricao());
					}else{
						throw new ActionServletException("atencao.pesquisa_inexistente", null, "Categoria");
					}

					FiltroSubCategoria filtroSubCategoria = new FiltroSubCategoria();
					filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CATEGORIA_ID, String
									.valueOf(ConstantesSistema.INDICADOR_CONTRATO_CONSUMO_CATEGORIA)));
					filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CODIGO, String
									.valueOf(ConstantesSistema.INDICADOR_CONTRATO_CONSUMO_SUBCATEGORIA)));
					filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					Subcategoria subCategoria = (Subcategoria) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroSubCategoria,
									Subcategoria.class.getName()));

					if(subCategoria != null){
						inserirImovelActionForm.set("idSubCategoria", subCategoria.getId().toString());
						inserirImovelActionForm.set("textoSelecionadoSubCategoria", subCategoria.getDescricao());
					}else{
						throw new ActionServletException("atencao.pesquisa_inexistente", null, "Subcategoria");
					}

					inserirImovelActionForm.set("quantidadeEconomia", "");
				}else{
					inserirImovelActionForm.set("idCategoria", String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
					inserirImovelActionForm.set("idSubCategoria", String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
					inserirImovelActionForm.set("quantidadeEconomia", "");
				}

				// limpa as subcategorias
				colecaoImovelSubCategorias.clear();

				// limpa os grupos de usuários
				sessao.setAttribute("colecaoImovelConsumoFaixaAreaCategoria", null);
			}else{
				if(colecaoImovelSubCategorias != null && !colecaoImovelSubCategorias.isEmpty() && indicadorContratoConsumo != null
								&& indicadorContratoConsumo){
					inserirImovelActionForm.set("idCategoria", String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
					inserirImovelActionForm.set("idSubCategoria", String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
					inserirImovelActionForm.set("quantidadeEconomia", "");
				}
			}

			if(atualizarIndicador == null && inserirImovelActionForm.get("idCategoria") != null
							&& !inserirImovelActionForm.get("idCategoria").equals("-1")
							&& !inserirImovelActionForm.get("idCategoria").equals("")){
				// clicou em categoria

				String idCategoria = (String) inserirImovelActionForm.get("idCategoria");
				String idSubCategoria = (String) inserirImovelActionForm.get("idSubCategoria");

				// Fitro SubCategoria
				FiltroSubCategoria filtroSubCategoria = new FiltroSubCategoria(FiltroSubCategoria.DESCRICAO);
				filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CATEGORIA_ID, idCategoria));
				filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				subCategorias = fachada.pesquisar(filtroSubCategoria, Subcategoria.class.getName());

				// a coleção de subCategorias é obrigatório
				if(subCategorias == null || subCategorias.equals("")){
					throw new ActionServletException("atencao.naocadastrado", null, "sub categoria");
				}else{

					if(subCategorias.isEmpty()){
						throw new ActionServletException("atencao.categoria.sub_categoria.inexistente");
					}else{

						Iterator isubCategorias = subCategorias.iterator();
						while(isubCategorias.hasNext()){
							Subcategoria sub = (Subcategoria) isubCategorias.next();

							if(sub.getId().toString().equals(idSubCategoria)){
								inserirImovelActionForm.set("textoSelecionadoSubCategoria", sub.getDescricao());
							}
						}

						sessao.setAttribute("subCategorias", subCategorias);
					}

				}
			}else if(inserirImovelActionForm.get("idCategoria").toString().trim().equalsIgnoreCase("-1")
							&& !inserirImovelActionForm.get("idSubCategoria").toString().trim().equalsIgnoreCase("-1")
							&& (httpServletRequest.getParameter("subCategoriaEscolhida") != null && httpServletRequest.getParameter(
											"subCategoriaEscolhida").trim().equalsIgnoreCase("1"))){
				// Serve para setar a categoria quando o usuario selecionar só a sub categoria

				FiltroSubCategoria filtroSubCategoria = new FiltroSubCategoria();
				filtroSubCategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroSubCategoria.CATEGORIA);
				filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.ID, inserirImovelActionForm.get(
								"idSubCategoria").toString()));

				Collection subCategoriasTeste = fachada.pesquisar(filtroSubCategoria, Subcategoria.class.getName());

				Subcategoria subCategoria = (Subcategoria) subCategoriasTeste.iterator().next();

				// Limpa filtro
				filtroSubCategoria.limparListaParametros();
				filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CATEGORIA_ID, subCategoria.getCategoria()
								.getId()));
				subCategorias = fachada.pesquisar(filtroSubCategoria, Subcategoria.class.getName());
				sessao.setAttribute("subCategorias", subCategorias);
				// Seta a categoria
				inserirImovelActionForm.set("idCategoria", subCategoria.getCategoria().getId().toString());
				inserirImovelActionForm.set("textoSelecionadoCategoria", subCategoria.getCategoria().getDescricao());
			}else if(inserirImovelActionForm.get("idCategoria").equals("") || inserirImovelActionForm.get("idCategoria").equals("-1")
							|| inserirImovelActionForm.get("idCategoria") == null){
				// primiera vez na tela, carregar as coleções de categoria e subcategoria

				// Filtro Categoria
				FiltroCategoria filtroCategoria = new FiltroCategoria(FiltroCategoria.DESCRICAO);
				filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				categorias = fachada.pesquisar(filtroCategoria, Categoria.class.getName());

				// a coleção de categorias é obrigatório
				if(categorias == null || categorias.equals("")){
					throw new ActionServletException("atencao.naocadastrado", null, "categoria");
				}else{
					if(inserirImovelActionForm.get("textoSelecionadoCategoria") == null
									|| inserirImovelActionForm.get("textoSelecionadoCategoria").equals("")){
						inserirImovelActionForm.set("textoSelecionadoCategoria", ((Categoria) ((List) categorias).get(0)).getDescricao());
					}else{
						inserirImovelActionForm.set("textoSelecionadoCategoria", "");
					}

					// Fitro SubCategoria
					FiltroSubCategoria filtroSubCategoria = new FiltroSubCategoria(FiltroSubCategoria.DESCRICAO);
					filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					// presquisa todas as subcategorias
					subCategorias = fachada.pesquisar(filtroSubCategoria, Subcategoria.class.getName());

					// a coleção de categorias é obrigatório
					if(subCategorias == null || subCategorias.equals("")){
						throw new ActionServletException("atencao.naocadastrado", null, "sub categoria");
					}else{
						if(inserirImovelActionForm.get("textoSelecionadoSubCategoria") == null
										|| inserirImovelActionForm.get("textoSelecionadoSubCategoria").equals("")){
							inserirImovelActionForm.set("textoSelecionadoSubCategoria", ((Subcategoria) ((List) subCategorias).get(0))
											.getDescricao());
						}else{
							inserirImovelActionForm.set("textoSelecionadoSubCategoria", "");
							inserirImovelActionForm.set("idSubCategoria", "");

						}

					}

					// Envia os objetos no request
					sessao.setAttribute("categorias", categorias);
					sessao.setAttribute("subCategorias", subCategorias);

				}
			}

		}

		sessao.setAttribute("indicadorImovelConsumoFaixaAreaCatg", indicadorImovelConsumoFaixaAreaCatg);
		sessao.setAttribute("colecaoImovelSubCategorias", colecaoImovelSubCategorias);

		return retorno;
	}

	private void adicionaObjetoColecao(DynaValidatorForm inserirImovelActionForm, HttpSession sessao,
					Collection colecaoImovelSubCategorias, short quantidadeEconomia, Categoria categoria){

		// Cria objeto SubCategoria
		Subcategoria subCategoria = new Subcategoria();

		subCategoria.setId(new Integer(inserirImovelActionForm.get("idSubCategoria").toString()));

		// recupera a descricao da subCategoria
		subCategoria.setDescricao((String) inserirImovelActionForm.get("textoSelecionadoSubCategoria"));

		subCategoria.setCategoria(categoria);

		ImovelSubcategoriaPK imovelSubcategoriaPK = new ImovelSubcategoriaPK(null, subCategoria);

		// Cria objeto ImovelSubCategoria
		ImovelSubcategoria imovelSubCategoria = new ImovelSubcategoria(imovelSubcategoriaPK, quantidadeEconomia, new Date());
		imovelSubCategoria.setCategoria(categoria);

		if(!colecaoImovelSubCategorias.contains(imovelSubCategoria)){

			colecaoImovelSubCategorias.add(imovelSubCategoria);

			inserirImovelActionForm.set("idSubCategoriaImovel", imovelSubCategoria.getComp_id().getSubcategoria().getId().toString());

			// manda para a sessão a coleção
			sessao.setAttribute("colecaoImovelSubCategorias", colecaoImovelSubCategorias);
		}else{
			throw new ActionServletException("atencao.ja_cadastradado.sub_categoria");
		}

		inserirImovelActionForm.set("quantidadeEconomia", null);

		Boolean indicadorContratoConsumo = (Boolean) inserirImovelActionForm.get("indicadorContratoConsumo");

		if(indicadorContratoConsumo != null && indicadorContratoConsumo){
			inserirImovelActionForm.set("idCategoria", String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
			inserirImovelActionForm.set("idSubCategoria", String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
			inserirImovelActionForm.set("quantidadeEconomia", "");
		}

		sessao.removeAttribute("botaoAdicionar");

	}

}
