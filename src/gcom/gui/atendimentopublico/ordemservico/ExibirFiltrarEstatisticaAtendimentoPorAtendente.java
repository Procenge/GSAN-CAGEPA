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
 * Ivan Sérgio Virginio da Silva Júnior
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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0732] Filtro para Relatorio Estatística de Atendimento por Atendente
 * 
 * @author isilva
 * @date 23/03/2011
 */
public class ExibirFiltrarEstatisticaAtendimentoPorAtendente
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirFiltrarEstatisticaAtendimentoPorAtendente");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		// Form
		GerarRelatorioEstatisticaAtendimentoPorAtendenteActionForm gerarRelatorioAcompanhamento = (GerarRelatorioEstatisticaAtendimentoPorAtendenteActionForm) actionForm;

		gerarRelatorioAcompanhamento.setTipoPesquisa("0");

		// Tipo Solicitação
		getSolicitacaoTipoCollection(sessao, fachada);

		// Especificação
		if(gerarRelatorioAcompanhamento.getTipoSolicitacao() != null && gerarRelatorioAcompanhamento.getTipoSolicitacao().length > 0){

			getSolicitacaoTipoEspecificacao(sessao, fachada, gerarRelatorioAcompanhamento);
		}else{

			gerarRelatorioAcompanhamento.setSelectedTipoSolicitacaoSize("0");
		}

		// Unidade de Atendimento/Atual
		getUnidade(gerarRelatorioAcompanhamento, httpServletRequest, 1);

		// Unidade Superior
		if(gerarRelatorioAcompanhamento.getUnidadeSuperiorId() != null && !gerarRelatorioAcompanhamento.getUnidadeSuperiorId().equals("")){

			getUnidade(gerarRelatorioAcompanhamento, httpServletRequest, 2);
		}

		return retorno;
	}

	/**
	 * Carrega coleção de solicitação tipo
	 * 
	 * @author Anderson Italo
	 * @date 17/03/2011
	 * @param sessao
	 * @param fachada
	 */
	private void getSolicitacaoTipoCollection(HttpSession sessao, Fachada fachada){

		// Filtra Solicitação Tipo
		FiltroSolicitacaoTipo filtroSolicitacaoTipo = new FiltroSolicitacaoTipo();
		filtroSolicitacaoTipo.setCampoOrderBy(FiltroSolicitacaoTipo.DESCRICAO);

		Collection colecaoSolicitacaoTipo = fachada.pesquisar(filtroSolicitacaoTipo, SolicitacaoTipo.class.getName());

		if(colecaoSolicitacaoTipo != null && !colecaoSolicitacaoTipo.isEmpty()){

			sessao.setAttribute("colecaoTipoSolicitacao", colecaoSolicitacaoTipo);
		}else{

			throw new ActionServletException("atencao.naocadastrado", null, "Especificação");
		}
	}

	/**
	 * Carrega as especificações dos tipos de solicitação selecionados.
	 * 
	 * @author Anderson Italo
	 * @date 17/03/2011
	 * @param sessao
	 * @param fachada
	 */
	private void getSolicitacaoTipoEspecificacao(HttpSession sessao, Fachada fachada,
					GerarRelatorioEstatisticaAtendimentoPorAtendenteActionForm formulario){

		String[] solicitacaoTipo = (String[]) formulario.getTipoSolicitacao();
		formulario.setSelectedTipoSolicitacaoSize(solicitacaoTipo.length + "");

		Collection colecaoSolicitacaoTipoEspecificacao = null;

		for(int i = 0; i < solicitacaoTipo.length; i++){

			// Filtra Solicitação Tipo Especificação
			FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
			filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(
							FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ID, solicitacaoTipo[i]));
			filtroSolicitacaoTipoEspecificacao.setCampoOrderBy(FiltroSolicitacaoTipoEspecificacao.DESCRICAO);
			filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("solicitacaoTipo");

			Collection colecaoRetorno = fachada.pesquisar(filtroSolicitacaoTipoEspecificacao, SolicitacaoTipoEspecificacao.class.getName());

			if(colecaoRetorno != null && !colecaoRetorno.isEmpty()){

				if(colecaoSolicitacaoTipoEspecificacao == null){
					colecaoSolicitacaoTipoEspecificacao = colecaoRetorno;
				}else{
					colecaoSolicitacaoTipoEspecificacao.addAll(colecaoRetorno);
				}
			}
		}

		if(colecaoSolicitacaoTipoEspecificacao != null && !colecaoSolicitacaoTipoEspecificacao.isEmpty()){
			sessao.setAttribute("colecaoEspecificacao", colecaoSolicitacaoTipoEspecificacao);
		}else{
			sessao.setAttribute("colecaoEspecificacao", new ArrayList());
		}
	}

	/**
	 * Recupera Unidade de Atendimento/Atual/Superior
	 * Verificar existência de unidades subordinadas
	 * 
	 * @author Anderson Italo
	 * @date 17/03/2011
	 * @param formulario
	 * @param fachada
	 * @param tipo
	 */
	private void getUnidade(GerarRelatorioEstatisticaAtendimentoPorAtendenteActionForm formulario, HttpServletRequest httpServletRequest,
					int tipo){

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		if(tipo == 1){

			// Unidade de Atendimento/Atual
			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroUnidadeOrganizacional.setCampoOrderBy(FiltroUnidadeOrganizacional.DESCRICAO);

			Collection colecaoUnidadeAtendimento = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
			filtroUnidadeOrganizacional.setCampoOrderBy(FiltroUnidadeOrganizacional.DESCRICAO);

			Collection colecaoUnidadeAtual = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			if(colecaoUnidadeAtendimento != null && !colecaoUnidadeAtendimento.isEmpty()){

				sessao.setAttribute("colecaoUnidadeAtendimento", colecaoUnidadeAtendimento);
				sessao.setAttribute("colecaoUnidadeAtual", colecaoUnidadeAtual);
			}else{

				sessao.setAttribute("colecaoUnidadeAtendimento", new ArrayList());
				sessao.setAttribute("colecaoUnidadeAtual", new ArrayList());
			}

		}else{

			// Unidade Superior
			UnidadeOrganizacional unidadeOrganizacional = new UnidadeOrganizacional();
			unidadeOrganizacional.setId(new Integer(formulario.getUnidadeSuperiorId()));

			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, formulario
							.getUnidadeSuperiorId()));
			Collection colecaoUnidadeOrganizacional = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			if(colecaoUnidadeOrganizacional != null && !colecaoUnidadeOrganizacional.isEmpty()){

				unidadeOrganizacional = (UnidadeOrganizacional) colecaoUnidadeOrganizacional.iterator().next();
				formulario.setUnidadeSuperiorDescricao(unidadeOrganizacional.getDescricao());
			}else{

				formulario.setUnidadeSuperiorDescricao("Unidade Inexistente");
			}

			if(formulario.getUnidadeSuperiorDescricao().equalsIgnoreCase("Unidade Inexistente")){

				sessao.removeAttribute("unidadeSuperiorEncontrada");
				formulario.setUnidadeSuperiorId("");
			}else{

				// Verificar existência de unidades subordinadas
				fachada.verificarExistenciaUnidadesSubordinadas(unidadeOrganizacional);
				sessao.setAttribute("unidadeSuperiorEncontrada", "true");
			}
		}
	}
}