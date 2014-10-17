
package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ImovelConsumoFaixaAreaCategoria;
import gcom.cadastro.imovel.ImovelConsumoFaixaAreaCategoriaPK;
import gcom.cadastro.imovel.ImovelSubcategoria;
import gcom.cadastro.imovel.ImovelSubcategoriaPK;
import gcom.cadastro.imovel.Subcategoria;
import gcom.fachada.Fachada;
import gcom.faturamento.consumofaixaareacategoria.ConsumoFaixaAreaCategoria;
import gcom.gui.GcomAction;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Hebert Falcão
 * @created 20/01/2010
 */
public class ExibirImovelConsumoFaixaAreaCategoriaPopupAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Obtém a fachada
		Fachada fachada = Fachada.getInstancia();

		// Localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("exibirImovelConsumoFaixaAreaCategoriaPopup");

		// Armazenando o foco do campo
		String parameter = httpServletRequest.getParameter("nomeCampoFoco");
		httpServletRequest.setAttribute("nomeCampoFoco", parameter);

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		Collection<ImovelConsumoFaixaAreaCategoria> colecaoImovelConsumoFaixaAreaCategoriaAux = null;

		// Parâmetro que sinaliza que a área deve ser atualizada
		String atualizarArea = httpServletRequest.getParameter("atualizarArea");

		if("S".equalsIgnoreCase(atualizarArea)){
			// Calculando a área e pesquisando o Consumo por Faixa de Área e Categoria que ela faz
			// parte

			// Obtendo as coleções da sessão
			colecaoImovelConsumoFaixaAreaCategoriaAux = (Collection<ImovelConsumoFaixaAreaCategoria>) sessao
							.getAttribute("colecaoImovelConsumoFaixaAreaCategoriaAux");

			if(colecaoImovelConsumoFaixaAreaCategoriaAux != null && !colecaoImovelConsumoFaixaAreaCategoriaAux.isEmpty()){

				Iterator<ImovelConsumoFaixaAreaCategoria> iteratorImovelConsumoFaixaAreaCategoria = colecaoImovelConsumoFaixaAreaCategoriaAux
								.iterator();
				while(iteratorImovelConsumoFaixaAreaCategoria.hasNext()){
					ImovelConsumoFaixaAreaCategoria imovelConsumoFaixaAreaCategoria = (ImovelConsumoFaixaAreaCategoria) iteratorImovelConsumoFaixaAreaCategoria
									.next();

					int idCategoria = imovelConsumoFaixaAreaCategoria.getCategoria().getId();

					String comprimentoFrenteStr = (String) httpServletRequest.getParameter("comprimentoFrente" + idCategoria);
					String comprimentoLadoStr = (String) httpServletRequest.getParameter("comprimentoLado" + idCategoria);
					String comprimentoTestadaStr = (String) httpServletRequest.getParameter("comprimentoTestada" + idCategoria);
					String numeroAndaresStr = (String) httpServletRequest.getParameter("numeroAndares" + idCategoria);
					String comprimentoAndarStr = (String) httpServletRequest.getParameter("comprimentoAndar" + idCategoria);

					int comprimentoFrente = 0;
					if(!Util.isVazioOuBranco(comprimentoFrenteStr)){
						comprimentoFrente = Integer.valueOf(comprimentoFrenteStr);
					}
					imovelConsumoFaixaAreaCategoria.setComprimentoFrente(comprimentoFrente);

					int comprimentoLado = 0;
					if(!Util.isVazioOuBranco(comprimentoLadoStr)){
						comprimentoLado = Integer.valueOf(comprimentoLadoStr);
					}
					imovelConsumoFaixaAreaCategoria.setComprimentoLado(comprimentoLado);

					Integer comprimentoTestada = null;
					if(!Util.isVazioOuBranco(comprimentoTestadaStr)){
						comprimentoTestada = Integer.valueOf(comprimentoTestadaStr);
					}
					imovelConsumoFaixaAreaCategoria.setComprimentoTestada(comprimentoTestada);

					Integer numeroAndares = null;
					if(!Util.isVazioOuBranco(numeroAndaresStr)){
						numeroAndares = Integer.valueOf(numeroAndaresStr);
					}
					imovelConsumoFaixaAreaCategoria.setNumeroAndares(numeroAndares);

					Integer comprimentoAndar = null;
					if(!Util.isVazioOuBranco(comprimentoAndarStr)){
						comprimentoAndar = Integer.valueOf(comprimentoAndarStr);
					}
					imovelConsumoFaixaAreaCategoria.setComprimentoAndar(comprimentoAndar);

					int areaConstruida = comprimentoFrente * comprimentoLado;
					imovelConsumoFaixaAreaCategoria.setAreaConstruida(areaConstruida);

					// Define o grupo usuário com base na área
					ConsumoFaixaAreaCategoria ConsumoFaixaAreaCategoria = fachada.pesquisarConsumoFaixaAreaCategoriaPorCategoriaArea(
									idCategoria, areaConstruida);

					ImovelConsumoFaixaAreaCategoriaPK compId = new ImovelConsumoFaixaAreaCategoriaPK();
					compId.setConsumoFaixaAreaCategoria(ConsumoFaixaAreaCategoria);
					imovelConsumoFaixaAreaCategoria.setComp_id(compId);
				}
			}
		}else{
			sessao.removeAttribute("closePage");

			// Obtendo as coleções da sessão
			Collection<ImovelSubcategoria> colecaoImovelSubcategorias = (Collection<ImovelSubcategoria>) sessao
							.getAttribute("colecaoImovelSubCategorias");

			Collection<ImovelConsumoFaixaAreaCategoria> colecaoImovelConsumoFaixaAreaCategoriaTemp = (Collection<ImovelConsumoFaixaAreaCategoria>) sessao
							.getAttribute("colecaoImovelConsumoFaixaAreaCategoria");

			// Contrato de consumo
			String indicadorContratoConsumoStr = httpServletRequest.getParameter("indicadorContratoConsumo");

			boolean indicadorContratoConsumo = false;

			if(colecaoImovelSubcategorias != null && !colecaoImovelSubcategorias.isEmpty() && indicadorContratoConsumoStr != null
							&& !indicadorContratoConsumoStr.trim().equals("")){
				indicadorContratoConsumo = Boolean.parseBoolean(indicadorContratoConsumoStr);
			}

			sessao.setAttribute("contratoConsumo", indicadorContratoConsumo);

			if(colecaoImovelSubcategorias != null && !colecaoImovelSubcategorias.isEmpty()){
				if(colecaoImovelConsumoFaixaAreaCategoriaTemp == null){
					colecaoImovelConsumoFaixaAreaCategoriaAux = new ArrayList<ImovelConsumoFaixaAreaCategoria>();
				}else{
					// Criando uma cópia da coleção original
					colecaoImovelConsumoFaixaAreaCategoriaAux = new ArrayList<ImovelConsumoFaixaAreaCategoria>();

					for(ImovelConsumoFaixaAreaCategoria imovelConsumoFaixaAreaCategoria : colecaoImovelConsumoFaixaAreaCategoriaTemp){
						ImovelConsumoFaixaAreaCategoria imovelConsumoFaixaAreaCategoriaCloned = new ImovelConsumoFaixaAreaCategoria();
						imovelConsumoFaixaAreaCategoriaCloned.setAreaConstruida(imovelConsumoFaixaAreaCategoria.getAreaConstruida());
						imovelConsumoFaixaAreaCategoriaCloned.setCategoria(imovelConsumoFaixaAreaCategoria.getCategoria());
						imovelConsumoFaixaAreaCategoriaCloned.setComp_id(imovelConsumoFaixaAreaCategoria.getComp_id());
						imovelConsumoFaixaAreaCategoriaCloned.setComprimentoAndar(imovelConsumoFaixaAreaCategoria.getComprimentoAndar());
						imovelConsumoFaixaAreaCategoriaCloned.setComprimentoFrente(imovelConsumoFaixaAreaCategoria.getComprimentoFrente());
						imovelConsumoFaixaAreaCategoriaCloned.setComprimentoLado(imovelConsumoFaixaAreaCategoria.getComprimentoLado());
						imovelConsumoFaixaAreaCategoriaCloned
										.setComprimentoTestada(imovelConsumoFaixaAreaCategoria.getComprimentoTestada());
						imovelConsumoFaixaAreaCategoriaCloned.setIndicadorUso(imovelConsumoFaixaAreaCategoria.getIndicadorUso());
						imovelConsumoFaixaAreaCategoriaCloned.setNumeroAndares(imovelConsumoFaixaAreaCategoria.getNumeroAndares());
						imovelConsumoFaixaAreaCategoriaCloned.setUltimaAlteracao(imovelConsumoFaixaAreaCategoria.getUltimaAlteracao());

						colecaoImovelConsumoFaixaAreaCategoriaAux.add(imovelConsumoFaixaAreaCategoriaCloned);
					}
				}

				// Verificando as categorias que estão associadas ao imovel
				ArrayList<Integer> indices = new ArrayList<Integer>();

				Iterator<ImovelConsumoFaixaAreaCategoria> iteratorImovelConsumoFaixaAreaCategoria = colecaoImovelConsumoFaixaAreaCategoriaAux
								.iterator();
				while(iteratorImovelConsumoFaixaAreaCategoria.hasNext()){
					ImovelConsumoFaixaAreaCategoria imovelConsumoFaixaAreaCategoria = (ImovelConsumoFaixaAreaCategoria) iteratorImovelConsumoFaixaAreaCategoria
									.next();

					Categoria categoria = imovelConsumoFaixaAreaCategoria.getCategoria();
					indices.add(categoria.getId());
				}

				sessao.setAttribute("imovelConsumoFaixaAreaCategoriaRemovido", this.recuperaConsumoFaixaAreaCategoriaRemovida(sessao));

				ImovelConsumoFaixaAreaCategoria imovelConsumoFaixaAreaCategoriaRemovido = new ImovelConsumoFaixaAreaCategoria();

				imovelConsumoFaixaAreaCategoriaRemovido = (ImovelConsumoFaixaAreaCategoria) sessao
								.getAttribute("imovelConsumoFaixaAreaCategoriaRemovido");

				// Montando a coleção colecaoImovelConsumoFaixaAreaCategoriaAux
				Iterator<ImovelSubcategoria> iteratorImovelSubcategoria = colecaoImovelSubcategorias.iterator();
				while(iteratorImovelSubcategoria.hasNext()){
					ImovelSubcategoria imovelSubcategoria = (ImovelSubcategoria) iteratorImovelSubcategoria.next();

					ImovelSubcategoriaPK compIdImovelSubcategoria = imovelSubcategoria.getComp_id();
					Subcategoria subCategoria = compIdImovelSubcategoria.getSubcategoria();

					Categoria categoria = subCategoria.getCategoria();

					// Verificando se a categoria já existe na coleção
					if(!indices.contains(categoria.getId())){
						indices.add(categoria.getId());

						ImovelConsumoFaixaAreaCategoria imovelConsumoFaixaAreaCategoria = new ImovelConsumoFaixaAreaCategoria();
						imovelConsumoFaixaAreaCategoria.setCategoria(categoria);

						if(!Util.isVazioOuBranco(imovelConsumoFaixaAreaCategoriaRemovido)){
							imovelConsumoFaixaAreaCategoria.setAreaConstruida(imovelConsumoFaixaAreaCategoriaRemovido.getAreaConstruida());
							imovelConsumoFaixaAreaCategoria.setComprimentoAndar(imovelConsumoFaixaAreaCategoriaRemovido
											.getComprimentoAndar());
							imovelConsumoFaixaAreaCategoria.setComprimentoFrente(imovelConsumoFaixaAreaCategoriaRemovido
											.getComprimentoFrente());
							imovelConsumoFaixaAreaCategoria
											.setComprimentoLado(imovelConsumoFaixaAreaCategoriaRemovido.getComprimentoLado());
							imovelConsumoFaixaAreaCategoria.setComprimentoTestada(imovelConsumoFaixaAreaCategoriaRemovido
											.getComprimentoTestada());
							imovelConsumoFaixaAreaCategoria.setNumeroAndares(imovelConsumoFaixaAreaCategoriaRemovido.getNumeroAndares());
						}
						colecaoImovelConsumoFaixaAreaCategoriaAux.add(imovelConsumoFaixaAreaCategoria);
					}
				}

				// Comentado por solicitação da OC769097
				// if(indicadorContratoConsumo){
				// // Adicionar as categorias que faltam
				// FiltroCategoria filtroCategoria = new FiltroCategoria();
				// filtroCategoria.adicionarParametro(new
				// ParametroSimples(FiltroCategoria.INDICADOR_USO,
				// ConstantesSistema.INDICADOR_USO_ATIVO));
				//
				// Collection<Categoria> categorias = fachada.pesquisar(filtroCategoria,
				// Categoria.class.getName());
				// Iterator<Categoria> iteratorCategoria = categorias.iterator();
				//
				// while(iteratorCategoria.hasNext()){
				// Categoria categoria = (Categoria) iteratorCategoria.next();
				//
				// // Verificando se a categoria já existe na coleção
				// if(!indices.contains(categoria.getId())){
				// indices.add(categoria.getId());
				//
				// ImovelConsumoFaixaAreaCategoria imovelConsumoFaixaAreaCategoria = new
				// ImovelConsumoFaixaAreaCategoria();
				// imovelConsumoFaixaAreaCategoria.setCategoria(categoria);
				//
				// colecaoImovelConsumoFaixaAreaCategoriaAux.add(imovelConsumoFaixaAreaCategoria);
				// }
				// }
				// }
			}else{
				colecaoImovelConsumoFaixaAreaCategoriaAux = null;
			}
		}

		sessao.setAttribute("colecaoImovelConsumoFaixaAreaCategoriaAux", colecaoImovelConsumoFaixaAreaCategoriaAux);

		return retorno;
	}

	private ImovelConsumoFaixaAreaCategoria recuperaConsumoFaixaAreaCategoriaRemovida(HttpSession sessao){

		// Armazena os valores inseridos no consumo por faixa de area e categoria
		List<ImovelConsumoFaixaAreaCategoria> colecaoConsumoFaixaAreaCategoria = (List<ImovelConsumoFaixaAreaCategoria>) sessao
						.getAttribute("colecaoImovelConsumoFaixaAreaCategoriaRemovida");

		ImovelConsumoFaixaAreaCategoria imovelConsumoFaixaAreaCategoria = new ImovelConsumoFaixaAreaCategoria();

		if(!Util.isVazioOrNulo(colecaoConsumoFaixaAreaCategoria)){

			imovelConsumoFaixaAreaCategoria = colecaoConsumoFaixaAreaCategoria.get(colecaoConsumoFaixaAreaCategoria.size() - 1);
		}

		return imovelConsumoFaixaAreaCategoria;
	}

}
