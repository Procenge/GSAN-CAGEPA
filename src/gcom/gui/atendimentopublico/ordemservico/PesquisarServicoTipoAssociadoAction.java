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
 * Saulo Lima
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
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PesquisarServicoTipoAssociadoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("pesquisarServicoTipoAssociadoAction");
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Inst�ncia do formul�rio que est� sendo utilizado
		PesquisarServicoTipoAssociadoActionForm form = (PesquisarServicoTipoAssociadoActionForm) actionForm;

		// Par�metros recebidos para adicionar um servi�o
		String idServicoTipo = form.getIdServicoTipo();
		String sequencial = form.getSequencial();
		String idEventoGeracao = form.getIdEventoGeracao();
		String idFormaGeracao = form.getIdFormaGeracao();
		String idFormaTramite = form.getIdFormaTramite();
		String idUnidadeDestino = form.getIdUnidadeDestino();
		String idFormaEncerramento = form.getIdFormaEncerramento();
		String idFormaProgramacao = form.getIdFormaProgramacao();
		String idFormaSelecaoEquipe = form.getIdFormaSelecaoEquipe();

		// Gerando o objeto ServicoAssociado que ser� inserido na cole��o
		ServicoAssociado servicoAssociado = new ServicoAssociado();

		// Servi�o tipo que ser� associado
		if(idServicoTipo != null && !idServicoTipo.trim().equals("")){
			FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, idServicoTipo));

			Collection<ServicoTipo> colecaoServicoTipo = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());
			if(colecaoServicoTipo != null && !colecaoServicoTipo.isEmpty()){
				servicoAssociado.setServicoTipoAssociado((ServicoTipo) Util.retonarObjetoDeColecao(colecaoServicoTipo));
			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Tipo de Servi�o");
			}
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Tipo de Servi�o");
		}

		// Sequencia de Gera��o
		servicoAssociado.setSequencial(Integer.valueOf(sequencial));

		// Evento Associado
		EventoGeracao eventoGeracao = new EventoGeracao();
		eventoGeracao.setId(Integer.valueOf(idEventoGeracao));
		servicoAssociado.setEventoGeracao(eventoGeracao);

		// Forma de Gera��o
		FormaGeracao formaGeracao = new FormaGeracao();
		formaGeracao.setId(Integer.valueOf(idFormaGeracao));
		servicoAssociado.setFormaGeracao(formaGeracao);

		// Forma de Tr�mite
		FormaTramite formaTramite = new FormaTramite();
		formaTramite.setId(Integer.valueOf(idFormaTramite));
		servicoAssociado.setFormaTramite(formaTramite);

		// Unidade de Destino
		if(idUnidadeDestino != null && !idUnidadeDestino.trim().equals("")){
			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeDestino));

			Collection<UnidadeOrganizacional> colecaoUnidadeOrganizacional = fachada.pesquisar(filtroUnidadeOrganizacional,
							UnidadeOrganizacional.class.getName());
			if(colecaoUnidadeOrganizacional != null && !colecaoUnidadeOrganizacional.isEmpty()){
				servicoAssociado
								.setUnidadeOrganizacional((UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeOrganizacional));
			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Unidade de Destino");
			}
		}

		// Forma de Tr�mite
		FormaEncerramento formaEncerramento = new FormaEncerramento();
		formaEncerramento.setId(Integer.valueOf(idFormaEncerramento));
		servicoAssociado.setFormaEncerramento(formaEncerramento);

		// Programa��o
		FormaProgramacao formaProgramacao = new FormaProgramacao();
		formaProgramacao.setId(Integer.valueOf(idFormaProgramacao));
		servicoAssociado.setFormaProgramacao(formaProgramacao);

		// Programa��o
		FormaSelecaoEquipe formaSelecaoEquipe = new FormaSelecaoEquipe();
		formaSelecaoEquipe.setId(Integer.valueOf(idFormaSelecaoEquipe));
		servicoAssociado.setFormaSelecaoEquipe(formaSelecaoEquipe);

		// Colocando o objeto gerado na cole��o que ficar� na sess�o
		Collection<ServicoAssociado> colecaoServicoAssociado = null;

		if(sessao.getAttribute("colecaoServicoAssociado") != null){
			colecaoServicoAssociado = (ArrayList<ServicoAssociado>) sessao.getAttribute("colecaoServicoAssociado");
		}else{
			colecaoServicoAssociado = new ArrayList<ServicoAssociado>();
		}

		// Pega o ID do Tipo de Servi�o selecionado para editar
		String idServicoAssociadoEditar = httpServletRequest.getParameter("idServicoAssociadoEditar");

		// Editar
		if(idServicoAssociadoEditar != null && !idServicoAssociadoEditar.trim().equals("")){
			if(colecaoServicoAssociado.isEmpty()){
				throw new ActionServletException("atencao.campo.informado", null, "Servi�o Associado");
			}else{
				for(ServicoAssociado servicoAssociadoTemp : colecaoServicoAssociado){

					// Atualiza o objeto que tiver o mesmo tipo de servi�o do selecionado em tela
					if(servicoAssociadoTemp.getServicoTipoAssociado().getId().toString().equals(idServicoAssociadoEditar)){

						// Caso tenha modificado o tipo do servi�o verifica se j� existe o tipo do
						// servi�o na cole��o
						if(!servicoAssociado.getServicoTipoAssociado().getId().toString().equals(idServicoAssociadoEditar)){
							fachada.validarAdicionarServicoAssociado(colecaoServicoAssociado, servicoAssociado.getServicoTipoAssociado()
											.getId());
						}
						try{
							PropertyUtils.copyProperties(servicoAssociadoTemp, servicoAssociado);
						}catch(IllegalAccessException e){
							throw new ActionServletException("erro.sistema", e);
						}catch(InvocationTargetException e){
							throw new ActionServletException("erro.sistema", e);
						}catch(NoSuchMethodException e){
							throw new ActionServletException("erro.sistema", e);
						}
					}
				}
				sessao.setAttribute("colecaoServicoAssociado", colecaoServicoAssociado);
			}

			// Inserir
		}else{

			if(colecaoServicoAssociado.isEmpty()){

				colecaoServicoAssociado.add(servicoAssociado);

			}else{

				// Verifica se j� existe o tipo do servi�o na cole��o
				fachada.validarAdicionarServicoAssociado(colecaoServicoAssociado, servicoAssociado.getServicoTipoAssociado().getId());

				colecaoServicoAssociado.add(servicoAssociado);

				// Ordenar por um �nico campo
				BeanComparator comparador = new BeanComparator("sequencial");
				Collections.sort((List) colecaoServicoAssociado, comparador);
			}
			sessao.setAttribute("colecaoServicoAssociado", colecaoServicoAssociado);
		}

		httpServletRequest.setAttribute("inclusaoConfirmada", "true");
		return retorno;
	}

}
