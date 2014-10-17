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
 * Ailton Francisco de Sousa Junior
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
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.EsgotoTratamentoTipo;
import gcom.operacional.FiltroDivisaoEsgoto;
import gcom.operacional.FiltroEsgotoTratamentoTipo;
import gcom.operacional.FiltroSistemaEsgoto;
import gcom.operacional.SistemaEsgoto;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UCXXXX] INSERIR SUBSISTEMA ESGOTO
 * 
 * @author Ailton Sousa
 * @date 26/01/2011
 */

public class ExibirInserirSubsistemaEsgotoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirSubsistemaEsgoto");

		Fachada fachada = Fachada.getInstancia();

		// [FS0001] - VERIFICAR EXISTENCIA DE DADOS

		// SISTEMA DE ESGOTO

		FiltroSistemaEsgoto filtroSistemaEsgoto = new FiltroSistemaEsgoto();

		filtroSistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroDivisaoEsgoto.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroSistemaEsgoto.setCampoOrderBy(filtroSistemaEsgoto.DESCRICAO);

		Collection<SistemaEsgoto> colecaoSistemaEsgoto = fachada.pesquisar(filtroSistemaEsgoto, SistemaEsgoto.class.getName());

		if(colecaoSistemaEsgoto == null || colecaoSistemaEsgoto.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Sistema de Esgoto");
		}

		httpServletRequest.setAttribute("colecaoSistemaEsgoto", colecaoSistemaEsgoto);

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

		return retorno;
	}
}
