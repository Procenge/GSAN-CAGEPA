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

package gcom.gui.operacional.bacia;

import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.FiltroBacia;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
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
 * @author isilva
 * @date 28/01/2011
 */
public class FiltrarBaciaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Obtém a instância da fachada
		// Fachada fachada = Fachada.getInstancia();

		ActionForward retorno = actionMapping.findForward("exibirManterBaciaAction");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarBaciaActionForm pesquisarFiltrarBaciaActionForm = (FiltrarBaciaActionForm) actionForm;

		FiltroBacia filtroBacia = new FiltroBacia();

		// Objetos que serã retornados pelo hibernate
		filtroBacia.setCampoOrderBy(FiltroBacia.SUBSISTEMA_ESGOTO_SISTEMA_ESGOTO_ID, FiltroBacia.SUBSISTEMA_ESGOTO_ID, FiltroBacia.CODIGO);
		filtroBacia.adicionarCaminhoParaCarregamentoEntidade(FiltroBacia.SUBSISTEMA_ESGOTO_SISTEMA_ESGOTO);

		String codigo = pesquisarFiltrarBaciaActionForm.getCodigo();
		String descricao = pesquisarFiltrarBaciaActionForm.getDescricao();
		String tipoPesquisaDescricao = (String) pesquisarFiltrarBaciaActionForm.getTipoPesquisaDescricao();
		String descricaoAbreviada = pesquisarFiltrarBaciaActionForm.getDescricaoAbreviada();
		String tipoPesquisaDescricaoAbreviada = (String) pesquisarFiltrarBaciaActionForm.getTipoPesquisaDescricaoAbreviada();
		String idSistemaEsgoto = pesquisarFiltrarBaciaActionForm.getIdSistemaEsgoto();
		String idSubsistemaEsgoto = pesquisarFiltrarBaciaActionForm.getIdSubsistemaEsgoto();
		String indicadorUso = pesquisarFiltrarBaciaActionForm.getIndicadorUso();

		// 1 check --- null uncheck
		String indicadorAtualizar = httpServletRequest.getParameter("indicadorAtualizar");

		boolean peloMenosUmParametroInformado = false;

		if(!Util.isVazioOuBranco(codigo)){
			peloMenosUmParametroInformado = true;
			filtroBacia.adicionarParametro(new ParametroSimples(FiltroBacia.CODIGO, Integer.valueOf(codigo)));
		}

		if(!Util.isVazioOuBranco(descricao)){

			peloMenosUmParametroInformado = true;

			if(tipoPesquisaDescricao != null && tipoPesquisaDescricao.equalsIgnoreCase(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
				filtroBacia.adicionarParametro(new ComparacaoTextoCompleto(FiltroEmpresa.DESCRICAO, descricao));
			}else{
				filtroBacia.adicionarParametro(new ComparacaoTexto(FiltroEmpresa.DESCRICAO, descricao));
			}
		}

		if(!Util.isVazioOuBranco(descricaoAbreviada)){

			peloMenosUmParametroInformado = true;

			if(tipoPesquisaDescricaoAbreviada != null
							&& tipoPesquisaDescricaoAbreviada.equalsIgnoreCase(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
				filtroBacia.adicionarParametro(new ComparacaoTextoCompleto(FiltroEmpresa.DESCRICAO, descricaoAbreviada));
			}else{
				filtroBacia.adicionarParametro(new ComparacaoTexto(FiltroEmpresa.DESCRICAO, descricaoAbreviada));
			}
		}

		if(!Util.isVazioOuBranco(idSistemaEsgoto) && !("" + ConstantesSistema.NUMERO_NAO_INFORMADO).equalsIgnoreCase(idSistemaEsgoto)){
			peloMenosUmParametroInformado = true;
			filtroBacia.adicionarParametro(new ParametroSimples(FiltroBacia.SUBSISTEMA_ESGOTO_SISTEMA_ESGOTO_ID, Integer
							.valueOf(idSistemaEsgoto)));
		}

		if(!Util.isVazioOuBranco(idSubsistemaEsgoto) && !("" + ConstantesSistema.NUMERO_NAO_INFORMADO).equalsIgnoreCase(idSubsistemaEsgoto)){
			peloMenosUmParametroInformado = true;
			filtroBacia.adicionarParametro(new ParametroSimples(FiltroBacia.SUBSISTEMA_ESGOTO_ID, Integer.valueOf(idSubsistemaEsgoto)));
		}

		if(!Util.isVazioOuBranco(indicadorUso) && !indicadorUso.equalsIgnoreCase(String.valueOf(ConstantesSistema.TODOS))){
			peloMenosUmParametroInformado = true;
			if(indicadorUso.equalsIgnoreCase(String.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO))){
				filtroBacia.adicionarParametro(new ParametroSimples(FiltroBacia.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			}else{
				filtroBacia.adicionarParametro(new ParametroSimples(FiltroBacia.INDICADORUSO, ConstantesSistema.INDICADOR_USO_DESATIVO));
			}
		}

		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		sessao.setAttribute("filtroBacia", filtroBacia);
		sessao.setAttribute("indicadorAtualizar", indicadorAtualizar);
		sessao.setAttribute("voltar", "manter");

		// devolve o mapeamento de retorno
		return retorno;
	}

}
