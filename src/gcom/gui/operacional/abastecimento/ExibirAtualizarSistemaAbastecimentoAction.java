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

import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.FiltroSistemaAbastecimento;
import gcom.operacional.SistemaAbastecimento;
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
 * [UCXXXX] Manter Sistema Abastecimento
 * [SB0001 – Atualizar Sistema de Abastecimento]
 * 
 * @author Anderson Italo
 * @date 01/02/2011
 */
public class ExibirAtualizarSistemaAbastecimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("sistemaAbastecimentoAtualizar");

		AtualizarSistemaAbastecimentoActionForm form = (AtualizarSistemaAbastecimentoActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		String id = null;

		if(httpServletRequest.getParameter("idRegistroAtualizacao") != null){
			id = httpServletRequest.getParameter("idRegistroAtualizacao");
		}else{
			id = ((Integer) sessao.getAttribute("idRegistroAtualizacao")).toString();
		}

		if(httpServletRequest.getParameter("manter") != null){
			sessao.setAttribute("manter", true);
		}else if(httpServletRequest.getParameter("filtrar") != null){
			sessao.removeAttribute("manter");
		}

		SistemaAbastecimento sistemaAbastecimento = new SistemaAbastecimento();

		if(id != null && !id.trim().equals("") && Integer.parseInt(id) > 0){

			FiltroSistemaAbastecimento filtroSistemaAbastecimento = new FiltroSistemaAbastecimento();
			filtroSistemaAbastecimento.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");

			filtroSistemaAbastecimento.adicionarParametro(new ParametroSimples(FiltroSistemaAbastecimento.ID, id));

			Collection colecaoSistemaAbastecimento = fachada.pesquisar(filtroSistemaAbastecimento, SistemaAbastecimento.class.getName());

			if(colecaoSistemaAbastecimento != null && !colecaoSistemaAbastecimento.isEmpty()){

				sistemaAbastecimento = (SistemaAbastecimento) Util.retonarObjetoDeColecao(colecaoSistemaAbastecimento);

			}

			if(sistemaAbastecimento.getId() != null && !sistemaAbastecimento.getId().equals("")){
				form.setId(sistemaAbastecimento.getId().toString());
			}

			if(sistemaAbastecimento.getCodigo() != null && !sistemaAbastecimento.getCodigo().equals("")){
				form.setCodigo(sistemaAbastecimento.getCodigo().toString());
			}

			if(sistemaAbastecimento.getDescricao() != null && !sistemaAbastecimento.getDescricao().equals("")){
				form.setDescricao(sistemaAbastecimento.getDescricao());
			}

			form.setIndicadorUso(sistemaAbastecimento.getIndicadorUso());

			if(sistemaAbastecimento.getGerenciaRegional() != null){
				if(sistemaAbastecimento.getGerenciaRegional().getId() != null
								&& !sistemaAbastecimento.getGerenciaRegional().getId().equals("")){
					form.setGerenciaRegional(sistemaAbastecimento.getGerenciaRegional().getId().toString());
				}
			}

			if(sistemaAbastecimento.getDescricaoAbreviada() != null && !sistemaAbastecimento.getDescricaoAbreviada().equals("")){
				form.setDescricaoAbreviada(sistemaAbastecimento.getDescricaoAbreviada());
			}

			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional(FiltroGerenciaRegional.ID);
			filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.INDICADOR_USO, ConstantesSistema.SIM));
			Collection<GerenciaRegional> gerenciasRegionais = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());
			// [FS0001] - Verificar existência de dados
			if(gerenciasRegionais == null || gerenciasRegionais.isEmpty()){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Gerência Regional");
			}
			httpServletRequest.getSession().setAttribute("gerenciasRegionais", gerenciasRegionais);

			sessao.setAttribute("atualizarSistemaAbastecimento", sistemaAbastecimento);

			if(sessao.getAttribute("colecaoSistemaAbastecimento") != null){
				sessao.setAttribute("caminhoRetornoVoltar", "/gsan/filtrarSistemaAbastecimentoAction.do");
			}else{
				sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarSistemaAbastecimentoAction.do");
			}

		}

		return retorno;
	}
}