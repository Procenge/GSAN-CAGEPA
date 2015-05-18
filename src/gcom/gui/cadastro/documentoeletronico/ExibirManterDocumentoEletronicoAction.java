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
 * 
 * GSANPCG
 * Virg�nia Melo
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

package gcom.gui.cadastro.documentoeletronico;

import gcom.cadastro.atendimento.DocumentoEletronico;
import gcom.cadastro.atendimento.FiltroDocumentoEletronico;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroNulo;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Gicevalter Couto
 * @date 04/10/2014
 */

public class ExibirManterDocumentoEletronicoAction
				extends GcomAction {



	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterDocumentoEletronico");

		HttpSession sessao = httpServletRequest.getSession(false);


		// Recupera o filtro passado pelo FiltrarFuncionalidadeAction para ser efetuada pesquisa
		FiltroDocumentoEletronico filtroDocumentoEletronico = (FiltroDocumentoEletronico) sessao.getAttribute("filtroDocumentoEletronico");

		filtroDocumentoEletronico.adicionarParametro(new ParametroNulo(FiltroDocumentoEletronico.ID_ATENDIMENTO_DOCUMENTACAO));
		filtroDocumentoEletronico.adicionarCaminhoParaCarregamentoEntidade(FiltroDocumentoEletronico.ATENDIMENTO_DOCUMENTO_TIPO);
		filtroDocumentoEletronico.adicionarCaminhoParaCarregamentoEntidade(FiltroDocumentoEletronico.IMOVEL);
		filtroDocumentoEletronico.adicionarCaminhoParaCarregamentoEntidade(FiltroDocumentoEletronico.CLIENTE);

		// Se for preciso carregar outra entidade usar
		// "filtroDocumentoEletronico.adicionarCaminhoParaCarregamentoEntidade"

		// Aciona o controle de pagina��o para que sejam pesquisados apenas os registros que
		// aparecem na p�gina
		Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroDocumentoEletronico, DocumentoEletronico.class.getName());
		Collection colecaoDocumentoEletronico = (Collection) resultado.get("colecaoRetorno");
		retorno = (ActionForward) resultado.get("destinoActionForward");


		if(colecaoDocumentoEletronico != null && !colecaoDocumentoEletronico.isEmpty()){

			/*
			 * Verifica se a cole��o cont�m apenas um objeto, se est� retornando
			 * da pagina��o (devido ao esquema de pagina��o de 10 em 10 faz uma
			 * nova busca), evitando, assim, que caso haja 11 elementos no
			 * retorno da pesquisa e o usu�rio selecione o link para ir para a
			 * segunda p�gina ele n�o v� para tela de atualizar.
			 */

			if(colecaoDocumentoEletronico.size() == 1
							&& (httpServletRequest.getParameter("page.offset") == null || httpServletRequest.getParameter("page.offset")
											.equals("1"))){

				/*
				 * Verifica se o usu�rio marcou o checkbox de atualizar no jsp DocumentoEletronico_filtrar.
				 * Caso todas as condi��es sejam verdadeiras seta o retorno para o
				 * ExibirAtualizarFuncionarioAction e em caso negativo manda
				 * a cole��o pelo request.
				 */

				if(httpServletRequest.getAttribute("consultar") != null){
					retorno = actionMapping.findForward("consultarDocumentoEletronico");
					DocumentoEletronico DocumentoEletronico = (DocumentoEletronico) colecaoDocumentoEletronico.iterator().next();
					sessao.setAttribute("objetoDocumentoEletronico", DocumentoEletronico);

				}else{
					httpServletRequest.setAttribute("colecaoDocumentoEletronico", colecaoDocumentoEletronico);
				}
			}else{
				httpServletRequest.setAttribute("colecaoDocumentoEletronico", colecaoDocumentoEletronico);
			}
		}else{

			// Nenhuma DocumentoEletronico cadastrada
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");

		}

		return retorno;
	}
}
