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
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio Virginio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
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
 * [UC0732] Filtro para Relatorio Estat�stica de Atendimento por Atendente
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

		// Tipo Solicita��o
		getSolicitacaoTipoCollection(sessao, fachada);

		// Especifica��o
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
	 * Carrega cole��o de solicita��o tipo
	 * 
	 * @author Anderson Italo
	 * @date 17/03/2011
	 * @param sessao
	 * @param fachada
	 */
	private void getSolicitacaoTipoCollection(HttpSession sessao, Fachada fachada){

		// Filtra Solicita��o Tipo
		FiltroSolicitacaoTipo filtroSolicitacaoTipo = new FiltroSolicitacaoTipo();
		filtroSolicitacaoTipo.setCampoOrderBy(FiltroSolicitacaoTipo.DESCRICAO);

		Collection colecaoSolicitacaoTipo = fachada.pesquisar(filtroSolicitacaoTipo, SolicitacaoTipo.class.getName());

		if(colecaoSolicitacaoTipo != null && !colecaoSolicitacaoTipo.isEmpty()){

			sessao.setAttribute("colecaoTipoSolicitacao", colecaoSolicitacaoTipo);
		}else{

			throw new ActionServletException("atencao.naocadastrado", null, "Especifica��o");
		}
	}

	/**
	 * Carrega as especifica��es dos tipos de solicita��o selecionados.
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

			// Filtra Solicita��o Tipo Especifica��o
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
	 * Verificar exist�ncia de unidades subordinadas
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

				// Verificar exist�ncia de unidades subordinadas
				fachada.verificarExistenciaUnidadesSubordinadas(unidadeOrganizacional);
				sessao.setAttribute("unidadeSuperiorEncontrada", "true");
			}
		}
	}
}