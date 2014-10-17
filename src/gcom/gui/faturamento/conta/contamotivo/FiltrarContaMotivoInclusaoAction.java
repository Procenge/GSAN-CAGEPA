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
 * André Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * José Gilberto de França Matos
 * Lucas Daniel Medeiros
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. 
 * Consulte a Licença Pública Geral GNU para obter mais detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.faturamento.conta.contamotivo;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gcom.cadastro.cliente.FiltroClienteTipo;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.ContaMotivoInclusao;
import gcom.faturamento.conta.FiltroContaMotivoInclusao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

public class FiltrarContaMotivoInclusaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterContaMotivoInclusao");

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarContaMotivoInclusaoActionForm filtrarContaMotivoInclusaoActionForm = (FiltrarContaMotivoInclusaoActionForm) actionForm;

		FiltroContaMotivoInclusao filtroContaMotivoInclusao = new FiltroContaMotivoInclusao();

		boolean peloMenosUmParametroInformado = false;

		String descricao = filtrarContaMotivoInclusaoActionForm.getDescricaoMotivoInclusaoConta();

		String tipoPesquisa = filtrarContaMotivoInclusaoActionForm.getTipoPesquisa();

		String indicadorUso = filtrarContaMotivoInclusaoActionForm.getIndicadorUso();

		String atualizar = filtrarContaMotivoInclusaoActionForm.getIndicadorAtualizar();

		httpServletRequest.setAttribute("atualizar", atualizar);

		// Verifica se o campo Descrição do Cliente Tipo foi informado

		if(descricao != null && !descricao.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
				filtroContaMotivoInclusao.adicionarParametro(new ComparacaoTextoCompleto(FiltroContaMotivoInclusao.DESCRICAO, descricao));
			}else{
				filtroContaMotivoInclusao.adicionarParametro(new ComparacaoTexto(FiltroContaMotivoInclusao.DESCRICAO, descricao));
			}
		}
		// Verifica se o campo Indicador de Uso foi informado
		if(indicadorUso != null && !indicadorUso.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			peloMenosUmParametroInformado = true;
			filtroContaMotivoInclusao.adicionarParametro(new ParametroSimples(FiltroContaMotivoInclusao.INDICADOR_USO, indicadorUso));
		}

		// Erro caso o usuário mandou Pesquisar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}
		Fachada fachada = Fachada.getInstancia();
		Collection colecaoContaMotivoInclusao = fachada.pesquisar(filtroContaMotivoInclusao, ContaMotivoInclusao.class.getName());
		// filtroGerenciaRegional.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
		sessao.setAttribute("colecaoContaMotivoInclusao", colecaoContaMotivoInclusao);

		return retorno;
	}
}
