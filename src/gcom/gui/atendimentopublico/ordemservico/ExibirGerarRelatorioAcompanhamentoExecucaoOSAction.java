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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.Equipe;
import gcom.atendimentopublico.ordemservico.FiltroEquipe;
import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0450] Pesquisar Ordem Servico - Exibir
 * 
 * @author Leonardo Regis
 * @date 04/09/2006
 */
public class ExibirGerarRelatorioAcompanhamentoExecucaoOSAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirAcompanhamentoExecucaoOS");

		HttpSession sessao = httpServletRequest.getSession(false);

		String relatorioTipo = httpServletRequest.getParameter("tipoRelatorio");

		// Form
		GerarRelatorioAcompanhamentoExecucaoOSActionForm gerarRelatorioAcompanhamentoExecucaoOSActionForm = (GerarRelatorioAcompanhamentoExecucaoOSActionForm) actionForm;

		if(relatorioTipo != null){
			gerarRelatorioAcompanhamentoExecucaoOSActionForm.setRelatorioTipo(relatorioTipo);
		}else{
			relatorioTipo = gerarRelatorioAcompanhamentoExecucaoOSActionForm.getRelatorioTipo();
		}

		gerarRelatorioAcompanhamentoExecucaoOSActionForm.setSituacaoOrdemServico(ConstantesSistema.SITUACAO_REFERENCIA_ENCERRADA + "");

		gerarRelatorioAcompanhamentoExecucaoOSActionForm.setOrigemServico(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO + "");

		if(relatorioTipo != null){
			if(relatorioTipo.equals(GerarRelatorioAcompanhamentoExecucaoOSActionForm.ACOMPANHAMENTO_EXECUCAO_OS)){

				gerarRelatorioAcompanhamentoExecucaoOSActionForm.setTitulo("Filtra"
								+ "r Relatório de Acompanhamento de Execução de Ordem de Serviço");

			}else if(relatorioTipo.equals(GerarRelatorioAcompanhamentoExecucaoOSActionForm.PRODUTIVIDADE_EQUIPES)){

				gerarRelatorioAcompanhamentoExecucaoOSActionForm.setTitulo("Filtrar Relatório de Produtividade de Equipes");

			}else if(relatorioTipo.equals(GerarRelatorioAcompanhamentoExecucaoOSActionForm.NAO_EXECUTADA_EQUIPES)){

				gerarRelatorioAcompanhamentoExecucaoOSActionForm
								.setTitulo("Filtrar Relatório de Ordens de Serviço Não Executada por Equipes");

			}else if(relatorioTipo.equals(GerarRelatorioAcompanhamentoExecucaoOSActionForm.ENCERRADAS)){

				gerarRelatorioAcompanhamentoExecucaoOSActionForm.setTitulo("Filtrar Relatório de Ordens de Serviço Encerradas");

			}
		}else{
			gerarRelatorioAcompanhamentoExecucaoOSActionForm
							.setTitulo("Filtrar Relatório de Acompanhamento de Execução de Ordem de Serviço");
		}

		if(gerarRelatorioAcompanhamentoExecucaoOSActionForm.getIdLocalidadeFiltro() != null
						&& !gerarRelatorioAcompanhamentoExecucaoOSActionForm.getIdLocalidadeFiltro().equals("")){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Integer
							.valueOf(gerarRelatorioAcompanhamentoExecucaoOSActionForm.getIdLocalidadeFiltro())));
			Localidade localidade = null;

			Iterator it = getFachada().pesquisar(filtroLocalidade, Localidade.class.getName()).iterator();

			if(it.hasNext()){
				localidade = (Localidade) it.next();
			}

			if(localidade != null){
				gerarRelatorioAcompanhamentoExecucaoOSActionForm.setIdLocalidadeFiltro(localidade.getId().toString());
				gerarRelatorioAcompanhamentoExecucaoOSActionForm.setLocalidadeDescricaoFiltro(localidade.getDescricao());
				httpServletRequest.setAttribute("idLocalidadeNaoEncontrada", "true");
			}else{
				gerarRelatorioAcompanhamentoExecucaoOSActionForm.setIdLocalidadeFiltro("");
				httpServletRequest.setAttribute("idLocalidadeNaoEncontrada", "exception");
				gerarRelatorioAcompanhamentoExecucaoOSActionForm.setLocalidadeDescricaoFiltro("Localidade inexistente");
			}
		}

		if(httpServletRequest.getParameter("menu") != null){

			if(gerarRelatorioAcompanhamentoExecucaoOSActionForm.getRelatorioTipo() != null
							&& !gerarRelatorioAcompanhamentoExecucaoOSActionForm.getRelatorioTipo().equals("3")
							&& !gerarRelatorioAcompanhamentoExecucaoOSActionForm.getRelatorioTipo().equals("4")){
				gerarRelatorioAcompanhamentoExecucaoOSActionForm.setSituacaoOrdemServico("");
			}

			gerarRelatorioAcompanhamentoExecucaoOSActionForm.setOrigemServico(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString());
			gerarRelatorioAcompanhamentoExecucaoOSActionForm.setTipoOrdenacao("1");
			Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
			if(usuario != null){
				gerarRelatorioAcompanhamentoExecucaoOSActionForm.setIdUnidadeAtual(usuario.getUnidadeOrganizacional().getId().toString());
				gerarRelatorioAcompanhamentoExecucaoOSActionForm
								.setDescricaoUnidadeAtual(usuario.getUnidadeOrganizacional().getDescricao());
			}
		}

		// Flag indicando que o usuário fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		// [UC0376] - Pesquisar Unidade
		if((objetoConsulta != null && !objetoConsulta.trim().equals("") && objetoConsulta.trim().equals("1"))
						|| (objetoConsulta != null && !objetoConsulta.trim().equals("") && objetoConsulta.trim().equals("2"))
						|| (objetoConsulta != null && !objetoConsulta.trim().equals("") && objetoConsulta.trim().equals("3"))){

			// Faz a consulta de Cliente
			this.pesquisarUnidadeOrganizacional(gerarRelatorioAcompanhamentoExecucaoOSActionForm, objetoConsulta);
		}

		// [UC0075] - Pesquisar Equipe
		if((objetoConsulta != null && !objetoConsulta.trim().equals("") && objetoConsulta.trim().equals("4"))
						|| (objetoConsulta != null && !objetoConsulta.trim().equals("") && objetoConsulta.trim().equals("5"))){

			// Faz a consulta de Equipe
			this.pesquisarEquipe(gerarRelatorioAcompanhamentoExecucaoOSActionForm, objetoConsulta);
		}

		if((objetoConsulta != null && !objetoConsulta.trim().equals("") && objetoConsulta.trim().equals("6"))){

			// Faz a consulta de Localidade
			this.pesquisarLocalidade(gerarRelatorioAcompanhamentoExecucaoOSActionForm, objetoConsulta);
		}

		if(httpServletRequest.getParameter("tipoConsulta") != null && !httpServletRequest.getParameter("tipoConsulta").equals("")){

			String id = httpServletRequest.getParameter("idCampoEnviarDados");
			String descricao = httpServletRequest.getParameter("descricaoCampoEnviarDados");

			if(httpServletRequest.getParameter("tipoConsulta").equals("unidadeOrganizacional")){

				if(sessao.getAttribute("tipoUnidade").equals("unidadeAtendimento")){
					gerarRelatorioAcompanhamentoExecucaoOSActionForm.setIdUnidadeAtendimento(id);
					gerarRelatorioAcompanhamentoExecucaoOSActionForm.setDescricaoUnidadeAtendimento(descricao);

				}else if(sessao.getAttribute("tipoUnidade").equals("unidadeAtual")){
					gerarRelatorioAcompanhamentoExecucaoOSActionForm.setIdUnidadeAtual(id);
					gerarRelatorioAcompanhamentoExecucaoOSActionForm.setDescricaoUnidadeAtual(descricao);

				}else{
					gerarRelatorioAcompanhamentoExecucaoOSActionForm.setIdUnidadeEncerramento(id);
					gerarRelatorioAcompanhamentoExecucaoOSActionForm.setDescricaoUnidadeEncerramento(descricao);
				}

			}else if(httpServletRequest.getParameter("tipoConsulta").equals("equipe")){

				if(sessao.getAttribute("equipe").equals("equipeProgramacao")){
					gerarRelatorioAcompanhamentoExecucaoOSActionForm.setIdEquipeProgramacao(id);
					gerarRelatorioAcompanhamentoExecucaoOSActionForm.setDescricaoEquipeProgramacao(descricao);
				}else{
					gerarRelatorioAcompanhamentoExecucaoOSActionForm.setIdEquipeExecucao(id);
					gerarRelatorioAcompanhamentoExecucaoOSActionForm.setDescricaoEquipeExecucao(descricao);
				}
			}
		}

		// Monta a colecao de tipos Servicos
		this.pesquisarTipoServico(httpServletRequest);

		// Seta os request´s encontrados
		this.setaRequest(httpServletRequest, gerarRelatorioAcompanhamentoExecucaoOSActionForm);

		// if
		// (httpServletRequest.getParameter("caminhoRetornoTelaPesquisaOrdemServico")
		// != null) {
		//			
		// sessao.setAttribute("caminhoRetornoTelaPesquisaOrdemServico",
		// httpServletRequest.getParameter("caminhoRetornoTelaPesquisaOrdemServico"));
		//			
		// }

		httpServletRequest.setAttribute("origemServico", gerarRelatorioAcompanhamentoExecucaoOSActionForm.getOrigemServico());
		httpServletRequest.setAttribute("situacaoOS", gerarRelatorioAcompanhamentoExecucaoOSActionForm.getSituacaoOrdemServico());

		return retorno;
	}

	/**
	 * Pesquisa Unidade Organizacional
	 * 
	 * @author Rafael Corrêa
	 * @date 30/10/2006
	 */
	private void pesquisarUnidadeOrganizacional(GerarRelatorioAcompanhamentoExecucaoOSActionForm form, String objetoConsulta){

		FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

		Integer idUnidade = null;

		if(objetoConsulta.equals("1")){
			idUnidade = Integer.valueOf(form.getIdUnidadeAtendimento());
		}else if(objetoConsulta.equals("2")){
			idUnidade = Integer.valueOf(form.getIdUnidadeAtual());
		}else if(objetoConsulta.equals("3")){
			idUnidade = Integer.valueOf(form.getIdUnidadeEncerramento());
		}

		filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidade));

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoUnidade = Fachada.getInstancia().pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoUnidade != null && !colecaoUnidade.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidade);

			if(objetoConsulta.equals("1")){

				form.setIdUnidadeAtendimento(unidadeOrganizacional.getId().toString());
				form.setDescricaoUnidadeAtendimento(unidadeOrganizacional.getDescricao());

			}else if(objetoConsulta.equals("2")){

				form.setIdUnidadeAtual(unidadeOrganizacional.getId().toString());
				form.setDescricaoUnidadeAtual(unidadeOrganizacional.getDescricao());

			}else{

				form.setIdUnidadeEncerramento(unidadeOrganizacional.getId().toString());
				form.setDescricaoUnidadeEncerramento(unidadeOrganizacional.getDescricao());

			}

		}else{
			if(objetoConsulta.equals("1")){

				form.setIdUnidadeAtendimento("");
				form.setDescricaoUnidadeAtendimento("Unidade de Atendimento inexistente");

			}else if(objetoConsulta.equals("2")){

				form.setIdUnidadeAtual("");
				form.setDescricaoUnidadeAtual("Unidade Atual inexistente");

			}else{

				form.setIdUnidadeEncerramento("");
				form.setDescricaoUnidadeEncerramento("Unidade Encerramento inexistente");

			}
		}
	}

	/**
	 * Pesquisa Unidade Organizacional
	 * 
	 * @author Rafael Corrêa
	 * @date 30/10/2006
	 */
	private void pesquisarEquipe(GerarRelatorioAcompanhamentoExecucaoOSActionForm form, String objetoConsulta){

		FiltroEquipe filtroEquipe = new FiltroEquipe();

		Integer idEquipe = null;

		if(objetoConsulta.equals("4")){
			idEquipe = Integer.valueOf(form.getIdEquipeProgramacao());
		}else{
			idEquipe = Integer.valueOf(form.getIdEquipeExecucao());
		}

		filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.ID, idEquipe));

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoEquipe = Fachada.getInstancia().pesquisar(filtroEquipe, Equipe.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoEquipe != null && !colecaoEquipe.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			Equipe equipe = (Equipe) Util.retonarObjetoDeColecao(colecaoEquipe);

			if(objetoConsulta.equals("4")){

				form.setIdEquipeProgramacao(equipe.getId().toString());
				form.setDescricaoEquipeProgramacao(equipe.getNome());

			}else{

				form.setIdEquipeExecucao(equipe.getId().toString());
				form.setDescricaoEquipeExecucao(equipe.getNome());

			}

		}else{
			if(objetoConsulta.equals("4")){

				form.setIdEquipeProgramacao("");
				form.setDescricaoEquipeProgramacao("Equipe de Programação inexistente");

			}else{

				form.setIdEquipeExecucao("");
				form.setDescricaoEquipeExecucao("Equipe de Execucao inexistente");

			}
		}
	}

	private void pesquisarLocalidade(GerarRelatorioAcompanhamentoExecucaoOSActionForm gerarRelatorioAcompanhamento, String objetoConsulta){

		Fachada fachada = Fachada.getInstancia();

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

		// Recebe o valor do campo localidadeOrigemID do formulário.
		String localidadeID = (String) gerarRelatorioAcompanhamento.getIdLocalidadeFiltro();

		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeID));

		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna localidade
		Collection<Localidade> colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
			// Localidade nao encontrada
			// Limpa os campos localidadeOrigemID e nomeLocalidadeOrigem do
			// formulário
			gerarRelatorioAcompanhamento.setIdLocalidadeFiltro("");
			gerarRelatorioAcompanhamento.setLocalidadeDescricaoFiltro("Localidade inexistente");
		}else{
			Localidade objetoLocalidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);
			gerarRelatorioAcompanhamento.setIdLocalidadeFiltro(String.valueOf(objetoLocalidade.getId()));
			gerarRelatorioAcompanhamento.setLocalidadeDescricaoFiltro(objetoLocalidade.getDescricao());
		}
	}

	/**
	 * Pesquisa Tipo Servico
	 * 
	 * @author Rafael Pinto
	 * @date 17/08/2006
	 */
	private void pesquisarTipoServico(HttpServletRequest httpServletRequest){

		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();

		filtroServicoTipo.setConsultaSemLimites(true);
		filtroServicoTipo.setCampoOrderBy(FiltroServicoTipo.DESCRICAO);

		Collection colecaoTipoServico = Fachada.getInstancia().pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

		if(colecaoTipoServico == null || colecaoTipoServico.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Tipo de Serviço");
		}else{
			httpServletRequest.setAttribute("colecaoTipoServico", colecaoTipoServico);
		}
	}

	/**
	 * Seta os request com os id encontrados
	 * 
	 * @author Rafael Pinto
	 * @date 16/08/2006
	 */
	private void setaRequest(HttpServletRequest httpServletRequest, GerarRelatorioAcompanhamentoExecucaoOSActionForm form){

		// Unidade Atendimento
		if(form.getIdUnidadeAtendimento() != null && !form.getIdUnidadeAtendimento().equals("")
						&& form.getDescricaoUnidadeAtendimento() != null && !form.getDescricaoUnidadeAtendimento().equals("")){

			httpServletRequest.setAttribute("unidadeAtendimentoEncontrada", "true");
		}

		// Unidade Atual
		if(form.getIdUnidadeAtual() != null && !form.getIdUnidadeAtual().equals("") && form.getDescricaoUnidadeAtual() != null
						&& !form.getDescricaoUnidadeAtual().equals("")){

			httpServletRequest.setAttribute("unidadeAtualEncontrada", "true");
		}

		// Unidade Encerramento
		if(form.getIdUnidadeEncerramento() != null && !form.getIdUnidadeEncerramento().equals("")
						&& form.getDescricaoUnidadeEncerramento() != null && !form.getDescricaoUnidadeEncerramento().equals("")){

			httpServletRequest.setAttribute("unidadeEncerramentoEncontrada", "true");
		}

		// Equipe Programação
		if(form.getIdEquipeProgramacao() != null && !form.getIdEquipeProgramacao().equals("")
						&& form.getDescricaoEquipeProgramacao() != null && !form.getDescricaoEquipeProgramacao().equals("")){

			httpServletRequest.setAttribute("equipeProgramacaoEncontrada", "true");
		}

		// Equipe Execução
		if(form.getIdEquipeExecucao() != null && !form.getIdEquipeExecucao().equals("") && form.getDescricaoEquipeExecucao() != null
						&& !form.getDescricaoEquipeExecucao().equals("")){

			httpServletRequest.setAttribute("equipeExecucaoEncontrada", "true");
		}

	}

}