/*
 * Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
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

package gcom.gui.operacional.abastecimento;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.FiltroSetorAbastecimento;
import gcom.operacional.SetorAbastecimento;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UCXXXX] Manter Setor Abastecimento
 * 
 * @author Péricles
 * @date 08/02/2011
 */
public class ExibirManterSetorAbastecimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterSetorAbastecimento");

		HttpSession sessao = httpServletRequest.getSession(false);

		Collection<SetorAbastecimento> colecaoSetorAbastecimento = null;

		// Parte da verificação do filtro
		FiltroSetorAbastecimento filtroSetorAbastecimento = (FiltroSetorAbastecimento) sessao.getAttribute("filtroSetorAbastecimento");
		filtroSetorAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroSetorAbastecimento.ZONA_ABASTECIMENTO);
		filtroSetorAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroSetorAbastecimento.SISTEMA_ABASTECIMENTO);
		filtroSetorAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroSetorAbastecimento.ZONA_ABASTECIMENTO_LOCALIDADE);
		filtroSetorAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroSetorAbastecimento.DISTRITO_OPERACIONAL);

		// Verifica se o filtro foi informado pela página de filtragem
		if(sessao.getAttribute("filtroSetorAbastecimento") != null){
			filtroSetorAbastecimento = (FiltroSetorAbastecimento) sessao.getAttribute("filtroSetorAbastecimento");
		}

		if((retorno != null) && (retorno.getName().equalsIgnoreCase("exibirManterSetorAbastecimento"))){

			Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroSetorAbastecimento, SetorAbastecimento.class.getName());
			colecaoSetorAbastecimento = (Collection<SetorAbastecimento>) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");

			// [FS0002] Nenhum registro encontrado
			if(colecaoSetorAbastecimento == null || colecaoSetorAbastecimento.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhumresultado");
			}

			String identificadorAtualizar = (String) sessao.getAttribute("indicadorAtualizar");

			if(colecaoSetorAbastecimento != null && !colecaoSetorAbastecimento.isEmpty() && identificadorAtualizar != null
							&& !identificadorAtualizar.equals("") && colecaoSetorAbastecimento.size() == 1){

				// caso o resultado do filtro só retorne um registro
				// e o check box Atualizar estiver selecionado
				// o sistema não exibe a tela de manter, exibe a de atualizar
				retorno = actionMapping.findForward("exibirAtualizarSetorAbastecimento");
				SetorAbastecimento SetorAbastecimento = (SetorAbastecimento) colecaoSetorAbastecimento.iterator().next();
				sessao.setAttribute("idRegistroAtualizacao", SetorAbastecimento.getId());
				sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarSetorAbastecimentoAction.do");
			}else{

				// sessao.removeAttribute("colecaoSetorAbastecimento");
				sessao.setAttribute("collectionSetorAbastecimento", colecaoSetorAbastecimento);
				sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirManterSetorAbastecimentoAction.do");

			}

		}

		sessao.removeAttribute("tipoPesquisaRetorno");

		return retorno;

	}

}
