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

import gcom.cadastro.imovel.ImovelSubcategoria;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ControladorException;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class AtualizarImovelSubCategoriaAction
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

		ActionForward retorno = actionMapping.findForward("gerenciadorProcesso");

		// obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		// Par�mtro utilizado para definir se na aba de subcategoria o bot�o CADASTRO IMOVEL CONSUMO
		// FAIXA AREA CATG ser� exibido ou n�o.
		String indicadorImovelConsumoFaixaAreaCatg = "";
		try{
			indicadorImovelConsumoFaixaAreaCatg = ParametroCadastro.P_INDICADOR_IMOVEL_CONSUMO_FAIXA_AREA_CATG.executar();
		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ActionServletException(e.getMessage(), e);
		}

		Collection subCategorias = (Collection) sessao.getAttribute("colecaoImovelSubCategorias");

		if(subCategorias.isEmpty()){
			throw new ActionServletException("atencao.nenhuma_subcategoria");
		}

		// Para cliente DESO = Se n�o houver um Consumo por faixa de area e categoria Informado,
		// levanta exce��o
		// if(getParametroCompanhia(httpServletRequest).equals(SistemaParametro.INDICADOR_EMPRESA_DESO)){

		if(indicadorImovelConsumoFaixaAreaCatg.equals("1")){

			Collection colecaoConsumoFaixaAreaCategoria = null;
			Set<Integer> setCategorias = new TreeSet<Integer>(new TreeSet(new Comparator() {

				public int compare(Object a, Object b){

					Integer v1 = (Integer) a;
					Integer v2 = (Integer) b;

					return v1.compareTo(v2);

				}
			}));

			Iterator it = subCategorias.iterator();

			while(it.hasNext()){
				setCategorias.add((((ImovelSubcategoria) it.next()).getComp_id().getSubcategoria().getCategoria().getId()));
			}

			colecaoConsumoFaixaAreaCategoria = (Collection) sessao.getAttribute("colecaoImovelConsumoFaixaAreaCategoria");

			if(colecaoConsumoFaixaAreaCategoria == null || colecaoConsumoFaixaAreaCategoria.isEmpty()){
				throw new ActionServletException("atencao.nenhum_consumo_faixa_area_categoria");
			}else if(setCategorias.size() > colecaoConsumoFaixaAreaCategoria.size()){
				throw new ActionServletException("atencao.nenhum_consumo_faixa_area_categoria");
			}
		}

		return retorno;
	}
}
