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

package gcom.gui.arrecadacao.debitoautomatico;

import gcom.arrecadacao.debitoautomatico.DebitoAutomaticoMovimentoHelper;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action de filtrar o histórico de débitos automáticos do imóvel.
 * 
 * @author alopes
 * @Date 22/07/2013
 */
public class ConsultarHistoricoMovimentoDebitoAutomaticoFiltrarAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("consultarHistoricoMovimentoDebitoAutomaticoFiltrar");

		ConsultarHistoricoMovimentoDebitoAutomaticoFiltrarActionForm form = (ConsultarHistoricoMovimentoDebitoAutomaticoFiltrarActionForm) actionForm;

		if(form.getMatriculaImovel() != null && !form.getMatriculaImovel().equals("")){
			getImovel(form, httpServletRequest);
		}else{
			limparFormulario(form, httpServletRequest);
			form.setInscricaoImovel("");
			form.setMatriculaImovel("");
			form.setSituacaoAgua("");
			form.setSituacaoEsgoto("");
		}

		return retorno;
	}

	private void limparFormulario(ConsultarHistoricoMovimentoDebitoAutomaticoFiltrarActionForm form, HttpServletRequest httpServletRequest){

		HttpSession sessao = httpServletRequest.getSession();

		sessao.setAttribute("imovelClientes", null);

		sessao.setAttribute("colecaoDebitosAutomaticos", null);
		sessao.setAttribute("colecaoDebitoAutoMovConta", null);
		sessao.setAttribute("colecaoDebitoAutoMovGuia", null);

	}

	private void getImovel(ConsultarHistoricoMovimentoDebitoAutomaticoFiltrarActionForm form, HttpServletRequest httpServletRequest){

		Fachada fachada = Fachada.getInstancia();
		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, form.getMatriculaImovel()));
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");
		// Recupera Imóvel
		Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

		HttpSession sessao = httpServletRequest.getSession();
		if(colecaoImovel != null && !colecaoImovel.isEmpty()){

			sessao.setAttribute("inscricaoImovelEncontrada", "true");
			Imovel imovel = (Imovel) colecaoImovel.iterator().next();
			form.setInscricaoImovel(imovel.getInscricaoFormatada());
			form.setSituacaoAgua(imovel.getLigacaoAguaSituacao().getDescricao());
			form.setSituacaoEsgoto(imovel.getLigacaoEsgotoSituacao().getDescricao());

			Collection clientesImovel = fachada.pesquisarClientesImovel(Integer.valueOf(imovel.getId()));
			sessao.setAttribute("imovelClientes", clientesImovel);

			// debitos automaticos
			Collection colecaoDebitosAutomaticos = fachada.pesquisarDebitosAutomaticosImovel(imovel.getId());

			sessao.setAttribute("colecaoDebitosAutomaticos", colecaoDebitosAutomaticos);

			// Mov. Hist. Conta
			Collection<DebitoAutomaticoMovimentoHelper> colecaoDebitoAutoMovConta = fachada.pesquisarDebitoAutomaticoMovimentoPorImovel(
							imovel.getId(), true);
			sessao.setAttribute("colecaoDebitoAutoMovConta", colecaoDebitoAutoMovConta);

			// Mov. Hist. Guia
			Collection<DebitoAutomaticoMovimentoHelper> colecaoDebitoAutoMovGuia = fachada.pesquisarDebitoAutomaticoMovimentoPorImovel(
							imovel.getId(), false);
			sessao.setAttribute("colecaoDebitoAutoMovGuia", colecaoDebitoAutoMovGuia);

		}else{

			sessao.removeAttribute("inscricaoImovelEncontrada");
			form.setMatriculaImovel("");
			form.setSituacaoAgua("");
			form.setSituacaoEsgoto("");

			form.setInscricaoImovel("Matrícula inexistente");

			limparFormulario(form, httpServletRequest);
		}

	}

}
