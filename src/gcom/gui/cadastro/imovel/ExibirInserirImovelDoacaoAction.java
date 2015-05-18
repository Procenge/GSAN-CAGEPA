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
 * [UC0389] - Inserir Imóvel Doação Action responsável pela pre-exibição da
 * pagina de inserir ImovelDoacao
 * 
 * @author César Araújo
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

		/*** Declara e inicializa variáveis ***/
		ActionForward retorno = null;
		Fachada fachada = null;
		HttpSession sessao = null;
		ImovelDoacaoActionForm imovelDoacaoActionForm = null;


		/*** Procedimentos básicos para execução do método ***/
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

		/*** Valida se a coleção está preenchida ***/
		if(colecaoEntidadeBeneficenteContrato != null && colecaoEntidadeBeneficenteContrato.size() == 0){
			throw new ActionServletException("atencao.naocadastrado", null, "Contrato de Entidade Beneficente");
		}

		/*** Seta colecao de entidade beneficente na sessão ***/
		sessao.setAttribute("colecaoEntidadeBeneficenteContrato", colecaoEntidadeBeneficenteContrato);

		/*** Avalia se o código do imóvel digitado na página é válido ***/
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
