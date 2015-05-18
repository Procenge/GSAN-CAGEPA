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

import gcom.cadastro.imovel.EntidadeBeneficenteContrato;
import gcom.cadastro.imovel.FiltroEntidadeBeneficenteContrato;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ConectorOr;
import gcom.util.filtro.MaiorQue;
import gcom.util.filtro.ParametroNulo;

import java.util.Calendar;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0389] - Inserir Im�vel Doa��o Action respons�vel pela pre-exibi��o da
 * pagina de inserir ImovelDoacao
 * 
 * @author C�sar Ara�jo
 * @created 22 de agosto de 2006
 */
public class ExibirInserirImovelDoacaoAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		/*** Declara e inicializa vari�veis ***/
		ActionForward retorno = null;
		Fachada fachada = null;
		HttpSession sessao = null;
		ImovelDoacaoActionForm imovelDoacaoActionForm = null;


		/*** Procedimentos b�sicos para execu��o do m�todo ***/
		retorno = actionMapping.findForward("inserirImovelDoacao");
		imovelDoacaoActionForm = (ImovelDoacaoActionForm) actionForm;
		fachada = Fachada.getInstancia();
		sessao = httpServletRequest.getSession(false);

		/*** Define filtro pra pesquisar e alimenta a colecao de entidade beneficente ***/
		FiltroEntidadeBeneficenteContrato filtroEntidadeBeneficenteContrato = new FiltroEntidadeBeneficenteContrato(
						FiltroEntidadeBeneficenteContrato.NOME_CLIENTE_ENTIDADE_BENEFICIENTE);
		filtroEntidadeBeneficenteContrato.adicionarCaminhoParaCarregamentoEntidade(FiltroEntidadeBeneficenteContrato.ENTIDADE_BENEFICIENTE);
		filtroEntidadeBeneficenteContrato
						.adicionarCaminhoParaCarregamentoEntidade(FiltroEntidadeBeneficenteContrato.CLIENTE_ENTIDADE_BENEFICIENTE);

		filtroEntidadeBeneficenteContrato.adicionarParametro(new MaiorQue(FiltroEntidadeBeneficenteContrato.DATA_FIM_CONTRATO, Calendar
						.getInstance().getTime(), ConectorOr.CONECTOR_OR));
		filtroEntidadeBeneficenteContrato.adicionarParametro(new ParametroNulo(FiltroEntidadeBeneficenteContrato.DATA_FIM_CONTRATO));
		Collection<EntidadeBeneficenteContrato> colecaoEntidadeBeneficenteContrato = fachada.pesquisar(filtroEntidadeBeneficenteContrato,
						EntidadeBeneficenteContrato.class
						.getName());

		/*** Valida se a cole��o est� preenchida ***/
		if(colecaoEntidadeBeneficenteContrato != null && colecaoEntidadeBeneficenteContrato.size() == 0){
			throw new ActionServletException("atencao.naocadastrado", null, "Contrato de Entidade Beneficente");
		}

		/*** Seta colecao de entidade beneficente na sess�o ***/
		sessao.setAttribute("colecaoEntidadeBeneficenteContrato", colecaoEntidadeBeneficenteContrato);

		/*** Avalia se o c�digo do im�vel digitado na p�gina � v�lido ***/
		String idImovel = (String) imovelDoacaoActionForm.getIdImovel();
		if(idImovel != null && !idImovel.trim().equals("")){
			String imovelEncontrado = fachada.pesquisarInscricaoImovel(new Integer(idImovel), true);
			if(imovelEncontrado != null && !imovelEncontrado.equalsIgnoreCase("")){
				imovelDoacaoActionForm.setIdImovel(idImovel);
				imovelDoacaoActionForm.setInscricaoImovel(imovelEncontrado);
				httpServletRequest.setAttribute("corInscricao", "#000000");
			}else{
				httpServletRequest.setAttribute("corInscricao", "#ff0000");
				imovelDoacaoActionForm.setInscricaoImovel(ConstantesSistema.CODIGO_IMOVEL_INEXISTENTE);
			}
		}

		return retorno;
	}
}
