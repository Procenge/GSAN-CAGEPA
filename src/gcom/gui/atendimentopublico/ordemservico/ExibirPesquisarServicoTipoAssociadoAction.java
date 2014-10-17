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
 * Saulo Lima
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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.EventoGeracao;
import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.FormaEncerramento;
import gcom.atendimentopublico.ordemservico.FormaGeracao;
import gcom.atendimentopublico.ordemservico.FormaProgramacao;
import gcom.atendimentopublico.ordemservico.FormaSelecaoEquipe;
import gcom.atendimentopublico.ordemservico.FormaTramite;
import gcom.atendimentopublico.ordemservico.ServicoAssociado;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cadastro.localidade.FiltroZeis;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirPesquisarServicoTipoAssociadoAction
				extends GcomAction {

	/**
	 * Exibir Adicionar Serviço Associado;
	 * [UC0000]
	 * TODO: Em Andamento.
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirServicoTipoAssociadoPopup");
		PesquisarServicoTipoAssociadoActionForm form = (PesquisarServicoTipoAssociadoActionForm) actionForm;
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		String operacao = httpServletRequest.getParameter("operacao");

		if(operacao != null){
			sessao.setAttribute("operacao", operacao);
		}else{
			if(sessao.getAttribute("operacao") != null){
				operacao = (String) sessao.getAttribute("operacao");
			}
		}

		if(operacao != null){
			sessao.setAttribute("operacao", operacao);

			if(operacao.equals("adicionar")){
				form.limparForm();
			}else if(operacao.equals("editar")){

				String idServicoTipoAssocioado = null;

				if(httpServletRequest.getParameter("idServicoTipoAssociado") != null){
					idServicoTipoAssocioado = httpServletRequest.getParameter("idServicoTipoAssociado");
				}else{
					idServicoTipoAssocioado = form.getIdServicoTipo();
				}

				if(idServicoTipoAssocioado == null){
					throw new ActionServletException("atencao.informe_campo", null, "Tipo de Serviço");
				}

				Collection<ServicoAssociado> colecaoServicoAssociado = null;
				if(sessao.getAttribute("colecaoServicoAssociado") != null){
					colecaoServicoAssociado = (ArrayList<ServicoAssociado>) sessao.getAttribute("colecaoServicoAssociado");
				}else{
					throw new ActionServletException("atencao.campo.informado", null, "Serviço Associado");
				}

				if(colecaoServicoAssociado == null || colecaoServicoAssociado.isEmpty()){
					throw new ActionServletException("atencao.campo.informado", null, "Serviço Associado");
				}else{

					ServicoAssociado servicoAssociadoEditar = null;
					for(ServicoAssociado servicoAssociado : colecaoServicoAssociado){
						if(servicoAssociado.getServicoTipoAssociado().getId().toString().equals(idServicoTipoAssocioado)){
							servicoAssociadoEditar = servicoAssociado;
						}
					}

					if(servicoAssociadoEditar == null){
						throw new ActionServletException("atencao.campo.invalido", null, "Tipo de Serviço");
					}else{
						form.preencherForm(servicoAssociadoEditar);
						httpServletRequest.setAttribute("idServicoAssociadoEditar", servicoAssociadoEditar.getServicoTipoAssociado()
										.getId().toString());
					}
				}
			}
		}

		// flag utilizada para não consultar o objeto duas vezes.
		boolean pesquisouServicoTipo = false;
		boolean pesquisouUnidade = false;

		String forward = null;
		String tipoConsulta = httpServletRequest.getParameter("tipoConsulta");
		if(httpServletRequest.getParameter("forward") != null){
			forward = getRealForward(httpServletRequest.getParameter("forward"));
			retorno = actionMapping.findForward(forward);
		}else if(tipoConsulta != null && !tipoConsulta.trim().equals("")){
			if(tipoConsulta.equals("tipoServico")){
				pesquisouServicoTipo = true;
				form.setIdServicoTipo(httpServletRequest.getParameter("idCampoEnviarDados"));
				form.setDescricaoServicoTipo(httpServletRequest.getParameter("descricaoCampoEnviarDados"));
				retorno = actionMapping.findForward("exibirServicoTipoAssociadoPopup");
			}else if(tipoConsulta.equals("unidadeAtendimento")){
				pesquisouUnidade = true;
				form.setIdUnidadeDestino(httpServletRequest.getParameter("idCampoEnviarDados"));
				form.setDescricaoUnidadeDestino(httpServletRequest.getParameter("descricaoCampoEnviarDados"));
				retorno = actionMapping.findForward("exibirServicoTipoAssociadoPopup");
			}
		}

		String idServicoTipo = form.getIdServicoTipo();
		if(idServicoTipo != null && !idServicoTipo.trim().equals("") && !pesquisouServicoTipo){
			FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, idServicoTipo));

			Collection<ServicoTipo> colecaoServicoTipo = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());
			if(colecaoServicoTipo != null && !colecaoServicoTipo.isEmpty()){
				ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(colecaoServicoTipo);
				form.setDescricaoServicoTipo(servicoTipo.getDescricao());
			}else{
				form.setIdServicoTipo(null);
				form.setDescricaoServicoTipo("Tipo de Serviço inexistente");
			}
		}

		String idUnidadeDestino = form.getIdUnidadeDestino();
		if(idUnidadeDestino != null && !idUnidadeDestino.trim().equals("") && !pesquisouUnidade){
			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeDestino));

			Collection<UnidadeOrganizacional> colecaoUnidadeDestino = fachada.pesquisar(filtroUnidadeOrganizacional,
							UnidadeOrganizacional.class.getName());
			if(colecaoUnidadeDestino != null && !colecaoUnidadeDestino.isEmpty()){
				UnidadeOrganizacional unidadeDestino = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeDestino);
				form.setDescricaoUnidadeDestino(unidadeDestino.getDescricao());
			}else{
				form.setIdUnidadeDestino(null);
				form.setDescricaoUnidadeDestino("Unidade de Destino inexistente");
			}
		}

		FiltroZeis filtroZeis = new FiltroZeis();

		Collection<FormaGeracao> colecaoFormaGeracao = fachada.pesquisar(filtroZeis, FormaGeracao.class.getName());
		Collection<FormaTramite> colecaoFormaTramite = fachada.pesquisar(filtroZeis, FormaTramite.class.getName());
		Collection<EventoGeracao> colecaoEventoGeracao = fachada.pesquisar(filtroZeis, EventoGeracao.class.getName());
		Collection<FormaProgramacao> colecaoFormaProgramacao = fachada.pesquisar(filtroZeis, FormaProgramacao.class.getName());
		Collection<FormaEncerramento> colecaoFormaEncerramento = fachada.pesquisar(filtroZeis, FormaEncerramento.class.getName());
		Collection<FormaSelecaoEquipe> colecaoFormaSelecaoEquipe = fachada.pesquisar(filtroZeis, FormaSelecaoEquipe.class.getName());

		httpServletRequest.setAttribute("colecaoFormaGeracao", colecaoFormaGeracao);
		httpServletRequest.setAttribute("colecaoFormaTramite", colecaoFormaTramite);
		httpServletRequest.setAttribute("colecaoEventoGeracao", colecaoEventoGeracao);
		httpServletRequest.setAttribute("colecaoFormaProgramacao", colecaoFormaProgramacao);
		httpServletRequest.setAttribute("colecaoFormaEncerramento", colecaoFormaEncerramento);
		httpServletRequest.setAttribute("colecaoFormaSelecaoEquipe", colecaoFormaSelecaoEquipe);

		return retorno;

	}

	private String getRealForward(String upper){

		String forward = "";
		if("exibirServicoTipoAssociadoPopup".toUpperCase().equals(upper.toUpperCase())){
			forward = "exibirServicoTipoAssociadoPopup";
			// } else if ("exibirGerarOrdemServico".toUpperCase().equals(upper.toUpperCase())) {
			// forward = "exibirGerarOrdemServico";
		}else{
			throw new IllegalArgumentException();
		}
		return forward;
	}

}
