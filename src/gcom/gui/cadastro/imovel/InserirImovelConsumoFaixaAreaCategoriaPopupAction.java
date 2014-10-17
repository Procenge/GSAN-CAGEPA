
package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ImovelConsumoFaixaAreaCategoria;
import gcom.cadastro.imovel.ImovelConsumoFaixaAreaCategoriaPK;
import gcom.fachada.Fachada;
import gcom.faturamento.consumofaixaareacategoria.ConsumoFaixaAreaCategoria;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
public class InserirImovelConsumoFaixaAreaCategoriaPopupAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Obtém a fachada
		Fachada fachada = Fachada.getInstancia();

		// Localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("inserirImovelConsumoFaixaAreaCategoriaPopup");

		// Obtém a instância da sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		// Obtendo as coleções da sessão
		Collection<ImovelConsumoFaixaAreaCategoria> colecaoImovelConsumoFaixaAreaCategoriaAux = (Collection<ImovelConsumoFaixaAreaCategoria>) sessao
						.getAttribute("colecaoImovelConsumoFaixaAreaCategoriaAux");

		// Contraro de consumo
		Boolean indicadorContratoConsumo = (Boolean) sessao.getAttribute("contratoConsumo");

		if(indicadorContratoConsumo == null){
			indicadorContratoConsumo = Boolean.FALSE;
		}

		Collection<ImovelConsumoFaixaAreaCategoria> colecaoImovelConsumoFaixaAreaCategoria = null;

		if(colecaoImovelConsumoFaixaAreaCategoriaAux != null && !colecaoImovelConsumoFaixaAreaCategoriaAux.isEmpty()){
			colecaoImovelConsumoFaixaAreaCategoria = new ArrayList<ImovelConsumoFaixaAreaCategoria>();

			Iterator<ImovelConsumoFaixaAreaCategoria> iteratorImovelConsumoFaixaAreaCategoria = colecaoImovelConsumoFaixaAreaCategoriaAux
							.iterator();
			while(iteratorImovelConsumoFaixaAreaCategoria.hasNext()){
				ImovelConsumoFaixaAreaCategoria imovelConsumoFaixaAreaCategoria = (ImovelConsumoFaixaAreaCategoria) iteratorImovelConsumoFaixaAreaCategoria
								.next();

				Categoria categoria = imovelConsumoFaixaAreaCategoria.getCategoria();
				int idCategoria = categoria.getId();

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

				validacao(imovelConsumoFaixaAreaCategoria, indicadorContratoConsumo);

				if(comprimentoFrente != 0 && comprimentoLado != 0){
					ConsumoFaixaAreaCategoria consumoFaixaAreaCategoria = fachada.pesquisarConsumoFaixaAreaCategoriaPorCategoriaArea(
									idCategoria, areaConstruida);

					if(consumoFaixaAreaCategoria == null){
						throw new ActionServletException("atencao.consumo_faixa_area_categoria.inexistente", categoria.getDescricao(),
										Integer.toString(areaConstruida));
					}else{
						ImovelConsumoFaixaAreaCategoriaPK compId = new ImovelConsumoFaixaAreaCategoriaPK();
						compId.setConsumoFaixaAreaCategoria(consumoFaixaAreaCategoria);
						imovelConsumoFaixaAreaCategoria.setComp_id(compId);
					}

					imovelConsumoFaixaAreaCategoria.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);

					colecaoImovelConsumoFaixaAreaCategoria.add(imovelConsumoFaixaAreaCategoria);
				}
			}
		}

		sessao.setAttribute("closePage", "S");

		sessao.setAttribute("colecaoImovelConsumoFaixaAreaCategoria", colecaoImovelConsumoFaixaAreaCategoria);

		return retorno;
	}

	private void validacao(ImovelConsumoFaixaAreaCategoria imovelConsumoFaixaAreaCategoria, boolean indicadorContratoConsumo){

		Categoria categoria = imovelConsumoFaixaAreaCategoria.getCategoria();

		if(imovelConsumoFaixaAreaCategoria.getComprimentoFrente() != 0 && imovelConsumoFaixaAreaCategoria.getComprimentoLado() == 0){
			throw new ActionServletException("atencao.required.comprimento.lado", null, categoria.getDescricao());
		}

		if(imovelConsumoFaixaAreaCategoria.getComprimentoFrente() == 0 && imovelConsumoFaixaAreaCategoria.getComprimentoLado() != 0){
			throw new ActionServletException("atencao.required.comprimento.frente", null, categoria.getDescricao());
		}

		if((imovelConsumoFaixaAreaCategoria.getComprimentoTestada() != null && imovelConsumoFaixaAreaCategoria.getComprimentoTestada() != 0)
						|| (imovelConsumoFaixaAreaCategoria.getNumeroAndares() != null && imovelConsumoFaixaAreaCategoria
										.getNumeroAndares() != 0)
						|| (imovelConsumoFaixaAreaCategoria.getComprimentoAndar() != null && imovelConsumoFaixaAreaCategoria
										.getComprimentoAndar() != 0)){
			if(imovelConsumoFaixaAreaCategoria.getComprimentoFrente() == 0){
				throw new ActionServletException("atencao.required.comprimento.frente", null, categoria.getDescricao());
			}

			if(imovelConsumoFaixaAreaCategoria.getComprimentoLado() == 0){
				throw new ActionServletException("atencao.required.comprimento.lado", null, categoria.getDescricao());
			}
		}

		// Verificando o indicador de contrato de consumo
		if(indicadorContratoConsumo){
			if(categoria.getId() == ConstantesSistema.INDICADOR_CONTRATO_CONSUMO_CATEGORIA){
				if(imovelConsumoFaixaAreaCategoria.getComprimentoFrente() == 0){
					throw new ActionServletException("atencao.required.comprimento.frente", null, categoria.getDescricao());
				}

				if(imovelConsumoFaixaAreaCategoria.getComprimentoLado() == 0){
					throw new ActionServletException("atencao.required.comprimento.lado", null, categoria.getDescricao());
				}
			}
		}else{
			if(imovelConsumoFaixaAreaCategoria.getComprimentoFrente() == 0){
				throw new ActionServletException("atencao.required.comprimento.frente", null, categoria.getDescricao());
			}

			if(imovelConsumoFaixaAreaCategoria.getComprimentoLado() == 0){
				throw new ActionServletException("atencao.required.comprimento.lado", null, categoria.getDescricao());
			}
		}
	}

}
