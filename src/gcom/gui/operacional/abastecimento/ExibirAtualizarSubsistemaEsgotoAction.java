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

package gcom.gui.operacional.abastecimento;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.EsgotoTratamentoTipo;
import gcom.operacional.FiltroEsgotoTratamentoTipo;
import gcom.operacional.FiltroSistemaEsgoto;
import gcom.operacional.FiltroSubsistemaEsgoto;
import gcom.operacional.SistemaEsgoto;
import gcom.operacional.SubsistemaEsgoto;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UCXXXX] Manter Subsistema de Esgoto [SB0001]Atualizar Subsistema de Esgoto
 * 
 * @author Ailton Sousa
 * @date 28/01/2011
 */

public class ExibirAtualizarSubsistemaEsgotoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarSubsistemaEsgoto");

		AtualizarSubsistemaEsgotoActionForm atualizarSubsistemaEsgotoActionForm = (AtualizarSubsistemaEsgotoActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		FiltroSistemaEsgoto filtroSistemaEsgoto = new FiltroSistemaEsgoto();

		filtroSistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSistemaEsgoto.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroSistemaEsgoto.setCampoOrderBy(FiltroSistemaEsgoto.DESCRICAO);

		Collection colecaoSistemaEsgoto = fachada.pesquisar(filtroSistemaEsgoto, SistemaEsgoto.class.getName());

		httpServletRequest.setAttribute("colecaoSistemaEsgoto", colecaoSistemaEsgoto);

		SubsistemaEsgoto subsistemaEsgoto = null;

		String idSubsistemaEsgoto = null;

		if(httpServletRequest.getParameter("idSubsistemaEsgoto") != null){
			// tela do manter
			idSubsistemaEsgoto = (String) httpServletRequest.getParameter("idSubsistemaEsgoto");
			sessao.setAttribute("idSubsistemaEsgoto", idSubsistemaEsgoto);
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirManterSubsistemaEsgotoAction.do");
		}else if(sessao.getAttribute("idSubsistemaEsgoto") != null){
			// tela do filtrar
			idSubsistemaEsgoto = (String) sessao.getAttribute("idSubsistemaEsgoto");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarSubsistemaEsgotoAction.do");
		}else if(httpServletRequest.getParameter("idRegistroInseridoAtualizar") != null){
			// link na tela de sucesso do inserir sistema esgoto
			idSubsistemaEsgoto = (String) httpServletRequest.getParameter("idRegistroInseridoAtualizar");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarSubsistemaEsgotoAction.do?menu=sim");
		}

		if(idSubsistemaEsgoto == null){

			if(sessao.getAttribute("idRegistroAtualizar") != null){
				subsistemaEsgoto = (SubsistemaEsgoto) sessao.getAttribute("idRegistroAtualizar");
			}else{
				idSubsistemaEsgoto = (String) httpServletRequest.getParameter("idSubsistemaEsgoto").toString();
			}
		}

		// Inicio da parte que verifica se vem da página de subsistema_esgoto_manter.jsp

		if(subsistemaEsgoto == null){

			if(idSubsistemaEsgoto != null && !idSubsistemaEsgoto.equals("")){

				FiltroSubsistemaEsgoto filtroSubsistemaEsgoto = new FiltroSubsistemaEsgoto();

				filtroSubsistemaEsgoto.adicionarCaminhoParaCarregamentoEntidade("sistemaEsgoto");

				filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.ID, idSubsistemaEsgoto));

				Collection colecaoSubsistemaEsgoto = fachada.pesquisar(filtroSubsistemaEsgoto, SubsistemaEsgoto.class.getName());

				if(colecaoSubsistemaEsgoto != null && !colecaoSubsistemaEsgoto.isEmpty()){

					subsistemaEsgoto = (SubsistemaEsgoto) Util.retonarObjetoDeColecao(colecaoSubsistemaEsgoto);

				}
			}
		}

		// O subsistema de esgoto foi encontrado

		atualizarSubsistemaEsgotoActionForm.setCodigo("" + subsistemaEsgoto.getCodigo());

		atualizarSubsistemaEsgotoActionForm.setDescricaoSubsistemaEsgoto(subsistemaEsgoto.getDescricao());

		atualizarSubsistemaEsgotoActionForm.setDescricaoAbreviada(subsistemaEsgoto.getDescricaoAbreviada());

		atualizarSubsistemaEsgotoActionForm.setSistemaEsgoto(subsistemaEsgoto.getSistemaEsgoto().getId().toString());

		atualizarSubsistemaEsgotoActionForm.setLocalidade(subsistemaEsgoto.getLocalidade().getId().toString());

		atualizarSubsistemaEsgotoActionForm.setEsgotoTratamentoTipo(subsistemaEsgoto.getEsgotoTratamentoTipo().getId().toString());

		atualizarSubsistemaEsgotoActionForm.setIndicadorUso("" + subsistemaEsgoto.getIndicadorUso());

		atualizarSubsistemaEsgotoActionForm.setUltimaAlteracao(Util.formatarDataComHora(subsistemaEsgoto.getUltimaAlteracao()));

		sessao.setAttribute("subsistemaEsgoto", subsistemaEsgoto);

		httpServletRequest.setAttribute("idSubsistemaEsgoto", idSubsistemaEsgoto);

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade(FiltroLocalidade.DESCRICAO);
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.SIM));
		Collection<Localidade> localidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());
		// [FS0001] - Verificar existência de dados
		if(localidades == null || localidades.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Localidade");
		}

		FiltroEsgotoTratamentoTipo filtroEsgotoTratamentoTipo = new FiltroEsgotoTratamentoTipo(FiltroEsgotoTratamentoTipo.DESCRICAO);
		filtroEsgotoTratamentoTipo
						.adicionarParametro(new ParametroSimples(FiltroEsgotoTratamentoTipo.INDICADOR_USO, ConstantesSistema.SIM));
		Collection<EsgotoTratamentoTipo> esgotoTratamentoTipos = fachada.pesquisar(filtroEsgotoTratamentoTipo, EsgotoTratamentoTipo.class
						.getName());

		// [FS0001] - Verificar existência de dados
		if(esgotoTratamentoTipos == null || esgotoTratamentoTipos.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Esgoto Tratamento Tipo");
		}

		httpServletRequest.getSession().setAttribute("localidades", localidades);

		httpServletRequest.getSession().setAttribute("esgotoTratamentoTipos", esgotoTratamentoTipos);

		// Fim da parte que verifica se vem da página de subsistema_esgoto_manter.jsp

		return retorno;
	}

}
