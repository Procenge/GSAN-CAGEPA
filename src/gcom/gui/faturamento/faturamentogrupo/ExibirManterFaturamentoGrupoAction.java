/**
 * 
 */
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
 * 
 * GSANPCG
 * Virgínia Melo
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

package gcom.gui.faturamento.faturamentogrupo;

import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirManterFaturamentoGrupoAction
				extends GcomAction {

	/**
	 * Manter Grupo de Faturamento
	 * 
	 * @author Hiroshi Gonçalves
	 * @date 05/02/2014
	 */

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterFaturamentoGrupo");

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroFaturamentoGrupo filtro = (FiltroFaturamentoGrupo) sessao.getAttribute("filtroFaturamentoGrupo");

		// Se for preciso carregar outra entidade usar
		// "filtroFaturamentoGrupo.adicionarCaminhoParaCarregamentoEntidade"

		// Aciona o controle de paginação para que sejam pesquisados apenas os registros que
		// aparecem na página
		Map resultado = controlarPaginacao(httpServletRequest, retorno, filtro, FaturamentoGrupo.class.getName());
		Collection colecaoFaturamentoGrupo = (Collection) resultado.get("colecaoRetorno");
		retorno = (ActionForward) resultado.get("destinoActionForward");

		/*
		 * Verifica se a coleção retornada pela pesquisa é nula, em caso
		 * afirmativo comunica ao usuário que não existe nenhuma
		 * fuuncionalidade cadastrada para a pesquisa efetuada e em caso
		 * negativo e se atender a algumas condições seta o retorno para o
		 * ExibirAtualizarFuncionalidadeAction, se não atender manda a
		 * coleção pelo request para ser recuperado e exibido pelo jsp.
		 */

		if(colecaoFaturamentoGrupo != null && !colecaoFaturamentoGrupo.isEmpty()){

			/*
			 * Verifica se a coleção contém apenas um objeto, se está retornando
			 * da paginação (devido ao esquema de paginação de 10 em 10 faz uma
			 * nova busca), evitando, assim, que caso haja 11 elementos no
			 * retorno da pesquisa e o usuário selecione o link para ir para a
			 * segunda página ele não vá para tela de atualizar.
			 */

			if(colecaoFaturamentoGrupo.size() == 1
							&& (httpServletRequest.getParameter("page.offset") == null || httpServletRequest.getParameter("page.offset")
											.equals("1"))){

				/*
				 * Verifica se o usuário marcou o checkbox de atualizar no jsp
				 * faturamentoGrupo_filtrar.
				 * Caso todas as condições sejam verdadeiras seta o retorno para o
				 * ExibirAtualizarFuncionarioAction e em caso negativo manda
				 * a coleção pelo request.
				 */

				if(httpServletRequest.getParameter("checkAtualizar") != null){
					retorno = actionMapping.findForward("atualizarFaturamentoGrupo");
					FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) colecaoFaturamentoGrupo.iterator()
									.next();
					sessao.setAttribute("objetoFaturamentoGrupo", faturamentoGrupo);

				}else{
					httpServletRequest.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);
				}
			}else{
				httpServletRequest.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);
			}
		}else{

			// Nenhum grupo de faturamento cadastrado
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");

		}

		if(httpServletRequest.getAttribute("voltar") == null){
			sessao.setAttribute("manter", "manter");
		}else{
			sessao.removeAttribute("manter");
		}

		return retorno;
	}
}
