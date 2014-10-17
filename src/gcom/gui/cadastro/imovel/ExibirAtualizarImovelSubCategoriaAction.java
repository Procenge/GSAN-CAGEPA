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

package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.FiltroSubCategoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelSubcategoria;
import gcom.cadastro.imovel.ImovelSubcategoriaPK;
import gcom.cadastro.imovel.Subcategoria;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Exibir os dados do Imovel referente a Suib Categoria em - Manter Imovel
 * 
 * @author Flavio, Rafael Santos
 * @created 25 de Maio de 2004
 */
public class ExibirAtualizarImovelSubCategoriaAction
				extends GcomAction {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws ControladorException{

		ActionForward retorno = actionMapping.findForward("atualizarImovelSubCategoria");

		// obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		// Par�mtro utilizado para definir se na aba de subcategoria o bot�o CADASTRO IMOVEL CONSUMO
		// FAIXA AREA CATG ser� exibido ou n�o.
		String indicadorImovelConsumoFaixaAreaCatg = ParametroCadastro.P_INDICADOR_IMOVEL_CONSUMO_FAIXA_AREA_CATG.executar();

		// Obt�m o action form
		DynaValidatorForm inserirImovelActionForm = (DynaValidatorForm) actionForm;

		Collection colecaoImovelSubCategorias = null;

		// Obt�m a fachada
		Fachada fachada = Fachada.getInstancia();

		// Cria��o das cole��es
		Collection categorias = new HashSet();
		Collection subCategorias = new HashSet();

		String botaoAdicionar = httpServletRequest.getParameter("botaoAdicionar");

		if(botaoAdicionar == null && httpServletRequest.getParameter("confirmado") != null){
			botaoAdicionar = (String) sessao.getAttribute("botaoAdicionar");
		}else{
			sessao.removeAttribute("botaoAdicionar");
		}

		FiltroSubCategoria filtroSubCategoria = new FiltroSubCategoria(FiltroSubCategoria.DESCRICAO);
		filtroSubCategoria.adicionarCaminhoParaCarregamentoEntidade("categoria");

		Imovel imovel = (Imovel) sessao.getAttribute("imovelAtualizacao");

		if(sessao.getAttribute("categorias") == null){
			FiltroCategoria filtroCategoriaNova = new FiltroCategoria();
			filtroCategoriaNova.adicionarParametro(new ParametroSimples(FiltroCategoria.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			if(imovel.getImovelPerfil().getId().equals(ConstantesSistema.INDICADOR_TARIFA_SOCIAL)){
				filtroCategoriaNova.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO,
								ConstantesSistema.INDICADOR_CATEGORIA_RESIDENCIA));
				filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CATEGORIA_ID,
								ConstantesSistema.INDICADOR_CATEGORIA_RESIDENCIA));
			}

			categorias = fachada.pesquisar(filtroCategoriaNova, Categoria.class.getName());
		}else{
			categorias = (Collection) sessao.getAttribute("categorias");
		}

		// Testa se a cole�ao ja esta na sessao
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

			if(categoriaNaBase.getConsumoMaximoEconomiasValidacao() < new Integer(quantidadeEconomia)){
				if(httpServletRequest.getParameter("confirmado") == null){

					httpServletRequest.setAttribute("destino", "4");
					sessao.setAttribute("botaoAdicionar", botaoAdicionar);
					retorno = montarPaginaConfirmacaoWizard("atencao.usuario.limite_ultrapassado_economias_validacao",
									ConstantesSistema.NOME_WIZARD_ALTERAR_IMOVEL, httpServletRequest, actionMapping, ""
													+ categoriaNaBase.getConsumoMaximoEconomiasValidacao());

				}else{
					if(httpServletRequest.getParameter("confirmado").equalsIgnoreCase("ok")){
						adicionaObjetoColecao(inserirImovelActionForm, sessao, colecaoImovelSubCategorias, quantidadeEconomia, fachada,
										filtroSubCategoria, categoriaNaBase);
						sessao.removeAttribute(botaoAdicionar);
					}
				}
			}else{
				adicionaObjetoColecao(inserirImovelActionForm, sessao, colecaoImovelSubCategorias, quantidadeEconomia, fachada,
								filtroSubCategoria, categoriaNaBase);
			}
		}else{
			// qualquer coisa que n�o seja adicionar passa por aqui

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

					FiltroSubCategoria filtroSubCategoriaInd = new FiltroSubCategoria();
					filtroSubCategoriaInd.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CATEGORIA_ID, String
									.valueOf(ConstantesSistema.INDICADOR_CONTRATO_CONSUMO_CATEGORIA)));
					filtroSubCategoriaInd.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CODIGO, String
									.valueOf(ConstantesSistema.INDICADOR_CONTRATO_CONSUMO_SUBCATEGORIA)));
					filtroSubCategoriaInd.adicionarParametro(new ParametroSimples(FiltroSubCategoria.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					Subcategoria subCategoria = (Subcategoria) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroSubCategoriaInd,
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

				// cole��o de subcategorias removidas
				Collection colecaoImovelSubcategoriasRemovidas = (Collection) sessao.getAttribute("colecaoImovelSubcategoriasRemoviadas");

				if(colecaoImovelSubcategoriasRemovidas == null){
					colecaoImovelSubcategoriasRemovidas = new ArrayList();
				}

				colecaoImovelSubcategoriasRemovidas.addAll(colecaoImovelSubCategorias);

				sessao.setAttribute("colecaoImovelSubcategoriasRemoviadas", colecaoImovelSubcategoriasRemovidas);

				// limpa as subcategorias
				colecaoImovelSubCategorias.clear();

				// guarda as informa��es para o inserir os valores
				// para o caso de troca de categoria somente nao precisar digitar.
				sessao.setAttribute("colecaoImovelConsumoFaixaAreaCategoriaRemovida", sessao
								.getAttribute("colecaoImovelConsumoFaixaAreaCategoria"));

				// limpa os grupos de usu�rios
				sessao.setAttribute("colecaoImovelConsumoFaixaAreaCategoria", null);
			}else{
				if(indicadorContratoConsumo != null && indicadorContratoConsumo && colecaoImovelSubCategorias != null
								&& !colecaoImovelSubCategorias.isEmpty()){
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
				filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CATEGORIA_ID, idCategoria));
				filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				subCategorias = fachada.pesquisar(filtroSubCategoria, Subcategoria.class.getName());

				// a cole��o de subCategorias � obrigat�rio
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
						sessao.setAttribute("categorias", categorias);
					}
				}
			}else if(inserirImovelActionForm.get("idCategoria").toString().trim().equalsIgnoreCase("-1")
							&& !inserirImovelActionForm.get("idSubCategoria").toString().trim().equalsIgnoreCase("-1")
							&& (httpServletRequest.getParameter("subCategoriaEscolhida") != null && httpServletRequest.getParameter(
											"subCategoriaEscolhida").trim().equalsIgnoreCase("1"))){
				// Serve para setar a categoria quando o usuario selecionar s� a sub categoria

				filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.ID, inserirImovelActionForm.get(
								"idSubCategoria").toString()));
				filtroSubCategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroSubCategoria.CATEGORIA);
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
				// primiera vez na tela, carregar as cole��es de categoria e subcategoria

				// Filtro Categoria
				FiltroCategoria filtroCategoria = new FiltroCategoria(FiltroCategoria.DESCRICAO);
				filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				if(imovel.getImovelPerfil().getId().equals(ConstantesSistema.INDICADOR_TARIFA_SOCIAL)){
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO,
									ConstantesSistema.INDICADOR_CATEGORIA_RESIDENCIA));
					filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CATEGORIA_ID,
									ConstantesSistema.INDICADOR_CATEGORIA_RESIDENCIA));
				}

				categorias = fachada.pesquisar(filtroCategoria, Categoria.class.getName());
				// a cole��o de categorias � obrigat�rio
				if(categorias == null || categorias.equals("")){
					throw new ActionServletException("atencao.naocadastrado", null, "categoria");
				}else{
					if(inserirImovelActionForm.get("textoSelecionadoCategoria") == null
									|| inserirImovelActionForm.get("textoSelecionadoCategoria").equals("")){
						inserirImovelActionForm.set("textoSelecionadoCategoria", ((Categoria) ((List) categorias).get(0)).getDescricao());
					}else{
						inserirImovelActionForm.set("textoSelecionadoCategoria", "");
					}

					filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					// presquisa todas as subcategorias
					subCategorias = fachada.pesquisar(filtroSubCategoria, Subcategoria.class.getName());

					// a cole��o de categorias � obrigat�rio
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
					sessao.setAttribute("indicadorImovelConsumoFaixaAreaCatg", indicadorImovelConsumoFaixaAreaCatg);
					sessao.setAttribute("categorias", categorias);
					sessao.setAttribute("subCategorias", subCategorias);

				}
			}
		}

		sessao.setAttribute("colecaoImovelSubCategorias", colecaoImovelSubCategorias);

		return retorno;
	}

	/**
	 * Este m�todo retorna o id tempor�rio baseado no timestamp do objeto para exibi��o na mem�ria,
	 * enquanto o usu�rio estiver inserindo
	 * 
	 * @param objeto
	 * @return
	 */
	public static long obterTimestampIdImovelSubcategoria(ImovelSubcategoria imovelSubcategoria){

		long retorno = 0L;

		Integer idImovel = null;
		if(imovelSubcategoria != null && imovelSubcategoria.getComp_id() != null && imovelSubcategoria.getComp_id().getImovel() != null){
			idImovel = imovelSubcategoria.getComp_id().getImovel().getId();
		}

		Integer idSubcategoria = null;
		if(imovelSubcategoria != null && imovelSubcategoria.getComp_id() != null
						&& imovelSubcategoria.getComp_id().getSubcategoria() != null){
			idSubcategoria = imovelSubcategoria.getComp_id().getSubcategoria().getId();
		}

		Date ultimaAlteracao = imovelSubcategoria.getUltimaAlteracao();

		retorno = ultimaAlteracao.getTime();

		if(idImovel != null){
			retorno = retorno + idImovel;
		}

		if(idSubcategoria != null){
			retorno = retorno + idSubcategoria;
		}

		return retorno;
	}

	private void adicionaObjetoColecao(DynaValidatorForm inserirImovelActionForm, HttpSession sessao,
					Collection colecaoImovelSubCategorias, short quantidadeEconomia, Fachada fachada,
					FiltroSubCategoria filtroSubCategoria, Categoria categoria){

		FiltroCategoria filtroCategoriaSelecionada = new FiltroCategoria();

		filtroCategoriaSelecionada.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, (String) inserirImovelActionForm.get(
						"idCategoria").toString()));

		Collection categoriasSelecionadas = fachada.pesquisar(filtroCategoriaSelecionada, Categoria.class.getName());

		categoriasSelecionadas.iterator().next();

		filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.ID, inserirImovelActionForm.get("idSubCategoria")
						.toString()));
		Collection SubCategoriasDescricao = fachada.pesquisar(filtroSubCategoria, Subcategoria.class.getName());

		// Cria objeto SubCategoria
		Subcategoria subCategoria = (Subcategoria) SubCategoriasDescricao.iterator().next();

		ImovelSubcategoriaPK imovelSubcategoriaPK = new ImovelSubcategoriaPK(null, subCategoria);

		// Cria objeto ImovelSubCategoria
		ImovelSubcategoria imovelSubCategoria = new ImovelSubcategoria(imovelSubcategoriaPK, quantidadeEconomia, new Date());
		imovelSubCategoria.setCategoria(categoria);

		boolean existe = false;

		if(colecaoImovelSubCategorias != null && !colecaoImovelSubCategorias.isEmpty()){
			Iterator icolecaoImovelSubCategorias = colecaoImovelSubCategorias.iterator();
			while(icolecaoImovelSubCategorias.hasNext()){
				ImovelSubcategoria imovelSubcategoria2 = (ImovelSubcategoria) icolecaoImovelSubCategorias.next();
				if(imovelSubcategoria2.getComp_id().getSubcategoria().getId().intValue() == imovelSubCategoria.getComp_id()
								.getSubcategoria().getId().intValue()){
					existe = true;
				}
			}
		}

		if(!existe){

			colecaoImovelSubCategorias.add(imovelSubCategoria);

			inserirImovelActionForm.set("idSubCategoriaImovel", imovelSubCategoria.getComp_id().getSubcategoria().getId().toString());

			// manda para a sess�o a cole��o
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

	}

}
