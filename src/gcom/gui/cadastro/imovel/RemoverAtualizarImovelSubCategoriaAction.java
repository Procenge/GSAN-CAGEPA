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
import gcom.cadastro.imovel.ImovelConsumoFaixaAreaCategoria;
import gcom.cadastro.imovel.ImovelSubcategoria;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;

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
 * Action que remove a o objeto selecionado de sub categoria em Manter Imovel
 * 
 * @author Rafael Santos
 * @created 11/02/2006
 */
public class RemoverAtualizarImovelSubCategoriaAction
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
					HttpServletResponse httpServletResponse){

		// Prepara o retorno da A��o
		ActionForward retorno = actionMapping.findForward("inserirImovelSubCategoria");

		// Obt�m a inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		// DynaValidatorActionForm inserirImovelActionForm = (DynaValidatorActionForm) actionForm;

		// Cria variaveis
		Collection colecaoImovelSubCategorias = (Collection) sessao.getAttribute("colecaoImovelSubCategorias");

		Collection colecaoImovelSubcategoriasRemovidas = (Collection) sessao.getAttribute("colecaoImovelSubcategoriasRemoviadas");
		if(colecaoImovelSubcategoriasRemovidas == null){
			colecaoImovelSubcategoriasRemovidas = new ArrayList();
		}

		ImovelSubcategoria imovelSubcategoria = null;

		String indicadorContratoConsumoStr = httpServletRequest.getParameter("indicadorContratoConsumo");

		boolean indicadorContratoConsumo = false;

		if(colecaoImovelSubCategorias != null && !colecaoImovelSubCategorias.isEmpty() && indicadorContratoConsumoStr != null
						&& !indicadorContratoConsumoStr.trim().equals("")){
			indicadorContratoConsumo = Boolean.parseBoolean(indicadorContratoConsumoStr);
		}

		// Obt�m os ids de remo��o
		String[] removerImovelSubCategoria = httpServletRequest.getParameterValues("removerImovelSubCategoria");

		if(colecaoImovelSubCategorias != null && !colecaoImovelSubCategorias.equals("")){
			Usuario usuario = this.getUsuarioLogado(httpServletRequest);

			boolean temPermissao = this.getFachada().verificarPermissaoEspecial(PermissaoEspecial.REMOVER_CATEGORIA_NAO_RESIDENCIAL_IMOVEL,
							usuario);

			Iterator imovelSubcategoriaIterator = colecaoImovelSubCategorias.iterator();
			Collection colecaoImovelSubCategoriasOriginal = (Collection) sessao.getAttribute("colecaoImovelSubCategoriasOriginal");
			while(imovelSubcategoriaIterator.hasNext()){
				imovelSubcategoria = (ImovelSubcategoria) imovelSubcategoriaIterator.next();

				for(int i = 0; i < removerImovelSubCategoria.length; i++){

					if(ExibirAtualizarImovelSubCategoriaAction.obterTimestampIdImovelSubcategoria(imovelSubcategoria) == new Long(
									removerImovelSubCategoria[i]).longValue()){

						Categoria categoria = imovelSubcategoria.getComp_id().getSubcategoria().getCategoria();
						boolean podeRemover = true;
						if(!temPermissao && categoria.getId().intValue() != Categoria.RESIDENCIAL_INT){
							throw new ActionServletException("atencao.permissao_remover_categoria_imovel");
						}

						if(podeRemover){
							if(colecaoImovelSubCategorias.contains(imovelSubcategoria)){
								if(!colecaoImovelSubcategoriasRemovidas.contains(imovelSubcategoria)
												&& (colecaoImovelSubCategoriasOriginal != null && colecaoImovelSubCategoriasOriginal
																.contains(imovelSubcategoria))){
									colecaoImovelSubcategoriasRemovidas.add(imovelSubcategoria);
								}
								imovelSubcategoriaIterator.remove();
								if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_DESO)){
									if(verificarExclusaoConsumoFaixaAreaCategoria(colecaoImovelSubCategorias, imovelSubcategoria
													.getComp_id().getSubcategoria().getCategoria())){
										sessao.setAttribute("colecaoImovelConsumoFaixaAreaCategoria", removerConsumoFaixaAreaCategoria(
														sessao, imovelSubcategoria, indicadorContratoConsumo));
										httpServletRequest.setAttribute("atualizar", "false");
									}
								}
							}
						}
					}
				}
			}

			sessao.setAttribute("colecaoImovelSubcategoriasRemoviadas", colecaoImovelSubcategoriasRemovidas);
		}

		return retorno;
	}

	private Collection removerConsumoFaixaAreaCategoria(HttpSession sessao, ImovelSubcategoria imovelSubcategoria,
					boolean indicadorContratoConsumo){

		Collection colecaoConsumoFaixaAreaCategoria = (Collection) sessao.getAttribute("colecaoImovelConsumoFaixaAreaCategoria");

		if(colecaoConsumoFaixaAreaCategoria != null && !colecaoConsumoFaixaAreaCategoria.isEmpty()){
			Iterator iColecaoConsumoFaixaAreaCategoria = colecaoConsumoFaixaAreaCategoria.iterator();
			ImovelConsumoFaixaAreaCategoria imovelConsumoFaixaAreaCategoria = null;
			while(iColecaoConsumoFaixaAreaCategoria.hasNext()){
				imovelConsumoFaixaAreaCategoria = (ImovelConsumoFaixaAreaCategoria) iColecaoConsumoFaixaAreaCategoria.next();
				if(imovelConsumoFaixaAreaCategoria.getCategoria().getId().equals(
								imovelSubcategoria.getComp_id().getSubcategoria().getCategoria().getId())
								|| indicadorContratoConsumo){
					iColecaoConsumoFaixaAreaCategoria.remove();
				}
			}
		}

		return colecaoConsumoFaixaAreaCategoria;
	}

	private boolean verificarExclusaoConsumoFaixaAreaCategoria(Collection colecaoImovelSubCategorias, Categoria categoria){

		boolean retorno = true;
		Iterator imovelSubcategoriaIterator = colecaoImovelSubCategorias.iterator();
		ImovelSubcategoria imovelSubcategoria = null;
		while(imovelSubcategoriaIterator.hasNext()){
			imovelSubcategoria = (ImovelSubcategoria) imovelSubcategoriaIterator.next();
			if(categoria.getId().equals(imovelSubcategoria.getComp_id().getSubcategoria().getCategoria().getId())){
				retorno = false;
				break;
			}
		}
		return retorno;
	}

}