/**
 * 
 */
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

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacaoMensagem;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author Ailton Sousa
 * @date 23/02/2011
 */
public class FiltrarMensagemTipoSolicitacaoEspecificacaoAction
				extends GcomAction {

	/**
	 * [UC3003] Manter Mensagem Tipo Solicita��o Especificacao - Filtrar Mensagem Tipo de
	 * Solicita��o com Especifica��es
	 * Este caso de uso cria um filtro que ser� usado na pesquisa de Mensagem Tipo de Solicita��o
	 * com Especifica��es
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

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Set no mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirManterMensagemTipoSolicitacaoEspecificacaoAction");

		FiltrarMensagemTipoSolicitacaoEspecificacaoActionForm form = (FiltrarMensagemTipoSolicitacaoEspecificacaoActionForm) actionForm;

		// Recupera todos os campos da p�gina para ser colocada no filtro
		// posteriormente
		String descricaoMensagem = form.getDescricaoMensagem();
		String indicadorUso = form.getIndicadorUso();
		String tipoPesquisa = form.getTipoPesquisa();

		// Indicador Atualizar
		String indicadorAtualizar = httpServletRequest.getParameter("indicadorAtualizar");

		if(indicadorAtualizar != null && !indicadorAtualizar.equals("")){
			sessao.setAttribute("indicadorAtualizar", indicadorAtualizar);
		}else{
			sessao.removeAttribute("indicadorAtualizar");
		}

		FiltroSolicitacaoTipoEspecificacaoMensagem filtro = new FiltroSolicitacaoTipoEspecificacaoMensagem(
						FiltroSolicitacaoTipoEspecificacaoMensagem.DESCRICAO);

		boolean peloMenosUmParametroInformado = false;

		// Descri��o da Mensagem
		if(descricaoMensagem != null && !descricaoMensagem.equalsIgnoreCase("")){

			peloMenosUmParametroInformado = true;

			if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){

				filtro.adicionarParametro(new ComparacaoTextoCompleto(FiltroSolicitacaoTipoEspecificacaoMensagem.DESCRICAO,
								descricaoMensagem));
			}else{

				filtro.adicionarParametro(new ComparacaoTexto(FiltroSolicitacaoTipoEspecificacaoMensagem.DESCRICAO, descricaoMensagem));
			}
		}

		// Situacao da Mensagem
		if(indicadorUso != null && !indicadorUso.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

			filtro.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacaoMensagem.INDICADOR_USO, indicadorUso));
		}

		// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		// Manda o filtro pela sessao para o ExibirManterSistemaEsgotoAction
		sessao.setAttribute("filtroSolicitacaoTipoEspecificacaoMensagemSessao", filtro);

		return retorno;
	}
}
