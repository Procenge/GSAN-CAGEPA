/**
 * 
 */
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

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.ordemservico.EspecificacaoServicoTipo;
import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.registroatendimento.EspecificacaoImovelSituacao;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descrição da classe
 * 
 * @author Rômulo Aurélio
 * @date 22/11/2006
 */
public class ExibirAtualizarAdicionarSolicitacaoEspecificacaoTipoServicoAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		// Set no mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("atualizarAdicionarSolicitacaoEspecificacaoTipoServico");

		AtualizarAdicionarSolicitacaoEspecificacaoActionForm atualizarAdicionarSolicitacaoEspecificacaoActionForm = (AtualizarAdicionarSolicitacaoEspecificacaoActionForm) actionForm;

		sessao.setAttribute("atualizarAdicionarSolicitacaoEspecificacaoActionForm", atualizarAdicionarSolicitacaoEspecificacaoActionForm);

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		// pega os dados do form para nao ter perdas qdo votarmos a tela
		// anterior

		if(sessao.getAttribute("solicitacaoTipoEspecificacao") == null){

			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();

			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getPrazoPrevisaoAtendimento() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getPrazoPrevisaoAtendimento().equals("")){
				// Prazo de previsão de atendimento
				solicitacaoTipoEspecificacao.setHorasPrazo(Integer.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getPrazoPrevisaoAtendimento()));
			}
			// Descrição da especificação
			solicitacaoTipoEspecificacao.setDescricao(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getDescricaoSolicitacao());

			// Pavimento de calçada obrigatório
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoCalcada() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoCalcada().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorPavimentoCalcada(Short
								.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoCalcada()));
			}
			// Pavimento de rua obrigatório
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoRua() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoRua().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorPavimentoRua(Short.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorPavimentoRua()));
			}

			// refere-se a ligação de agua
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorLigacaoAgua() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorLigacaoAgua().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorLigacaoAgua(Short.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorLigacaoAgua()));
			}
			// Cobrança de material
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCobrancaMaterial() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCobrancaMaterial().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorCobrancaMaterial(Integer
								.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCobrancaMaterial()));
			}
			// Matricula do imóvel obrigatório
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorMatriculaImovel() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorMatriculaImovel().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorMatricula(Integer.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorMatriculaImovel()));
			}
			// Parecer de encerramento obrigatório
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorParecerEncerramento() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorParecerEncerramento().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorParecerEncerramento(Integer
								.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorParecerEncerramento()));
			}
			// Gera débito
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarDebito() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarDebito().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorGeracaoDebito(Integer.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorGerarDebito()));
			}
			// Gera Crédito
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarCredito() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarCredito().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorGeracaoCredito(Integer
								.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarCredito()));
			}

			// id do tipo da solicitação gerada
			solicitacaoTipoEspecificacao.setServicoTipo(null);

			// Gera ordem de serviço
			solicitacaoTipoEspecificacao.setIndicadorGeracaoOrdemServico(ConstantesSistema.SIM);

			// Cliente Obrigatório
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCliente() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCliente().equals("")){

				solicitacaoTipoEspecificacao.setIndicadorCliente(Short.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorCliente()));
			}

			// Situação imovel
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdSituacaoImovel() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdSituacaoImovel().equals("")){
				EspecificacaoImovelSituacao especificacaoImovelSituacao = new EspecificacaoImovelSituacao();
				especificacaoImovelSituacao.setId(Integer.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIdSituacaoImovel()));
				solicitacaoTipoEspecificacao.setEspecificacaoImovelSituacao(especificacaoImovelSituacao);
			}

			Collection<EspecificacaoServicoTipo> colecaoEspecificacaoServicoTipo = (Collection) sessao
							.getAttribute("colecaoEspecificacaoServicoTipo");

			// recupera a coleção de especificacao servico tipo
			if(colecaoEspecificacaoServicoTipo != null && !colecaoEspecificacaoServicoTipo.isEmpty()){

				Collection colecao = new ArrayList();
				colecao.addAll(colecaoEspecificacaoServicoTipo);
				// [SF0004] - Validar Valor Ordem de Serviço 2ª parte
				fachada.verificarOrdemExecucaoForaOrdem(colecao);

				// Ordem de Serviço Automática
				for(EspecificacaoServicoTipo especificacaoServicoTipo : colecaoEspecificacaoServicoTipo){
					Date ultimaAlteracao = especificacaoServicoTipo.getUltimaAlteracao();
					long timeUltimaAlteracao = ultimaAlteracao.getTime();

					String parametroIndicadorGeracaoAutomatica = "indicadorGeracaoAutomatica" + timeUltimaAlteracao;
					String indicadorGeracaoAutomaticaStr = httpServletRequest.getParameter(parametroIndicadorGeracaoAutomatica);

					if(!Util.isVazioOuBranco(indicadorGeracaoAutomaticaStr)){
						Short indicadorGeracaoAutomatica = Short.valueOf(indicadorGeracaoAutomaticaStr);
						especificacaoServicoTipo.setIndicadorGeracaoAutomatica(indicadorGeracaoAutomatica);
					}
				}

				solicitacaoTipoEspecificacao.setEspecificacaoServicoTipos(new HashSet(colecaoEspecificacaoServicoTipo));
				// sessao.removeAttribute("colecaoEspecificacaoServicoTipo");
			}

			// indicador de uso ativo
			solicitacaoTipoEspecificacao.setIndicadorUso(Short.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO));

			SolicitacaoTipo solicitacaoTipo = new SolicitacaoTipo();

			solicitacaoTipo = (SolicitacaoTipo) sessao.getAttribute("solicitacaoTipoAtualizar");

			solicitacaoTipoEspecificacao.setSolicitacaoTipo(solicitacaoTipo);

			sessao.setAttribute("solicitacaoTipoEspecificacao", solicitacaoTipoEspecificacao);
		}

		// caso exista o parametro então limpa a sessão e o form
		if(httpServletRequest.getParameter("limpaSessao") != null && !httpServletRequest.getParameter("limpaSessao").equals("")){

			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setDescricaoTipoServico("");
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdTipoServico("");
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setOrdemExecucao("");

		}

		// recupera o caminho de retorno passado como parametro no jsp que chama
		// essa funcionalidade
		if(httpServletRequest.getParameter("retornarTelaPopup") != null){
			sessao.setAttribute("retornarTelaPopup", httpServletRequest.getParameter("retornarTelaPopup"));

			Collection<EspecificacaoServicoTipo> colecaoEspecificacaoServicoTipo = (Collection) sessao
							.getAttribute("colecaoEspecificacaoServicoTipo");

			// Ordem de Serviço Automática
			if(!Util.isVazioOrNulo(colecaoEspecificacaoServicoTipo)){
				for(EspecificacaoServicoTipo especificacaoServicoTipo : colecaoEspecificacaoServicoTipo){
					Date ultimaAlteracao = especificacaoServicoTipo.getUltimaAlteracao();
					long timeUltimaAlteracao = ultimaAlteracao.getTime();

					String parametroIndicadorGeracaoAutomatica = "indicadorGeracaoAutomatica" + timeUltimaAlteracao;
					String indicadorGeracaoAutomaticaStr = httpServletRequest.getParameter(parametroIndicadorGeracaoAutomatica);

					if(!Util.isVazioOuBranco(indicadorGeracaoAutomaticaStr)){
						Short indicadorGeracaoAutomatica = Short.valueOf(indicadorGeracaoAutomaticaStr);
						especificacaoServicoTipo.setIndicadorGeracaoAutomatica(indicadorGeracaoAutomatica);
					}
				}
			}
		}

		// Verifica se o tipoConsulta é diferente de nulo ou vazio.Nesse caso é
		// quando um o retorno da consulta vem para o action ao inves de ir
		// direto para o jsp
		if(httpServletRequest.getParameter("tipoConsulta") != null && !httpServletRequest.getParameter("tipoConsulta").equals("")){
			// verifica se retornou da pesquisa de tipo de serviço
			if(httpServletRequest.getParameter("tipoConsulta").equals("tipoServico")){

				atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.setIdTipoServico(httpServletRequest.getParameter("idCampoEnviarDados"));

				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setDescricaoTipoServico(httpServletRequest
								.getParameter("descricaoCampoEnviarDados"));

			}
		}

		// -------Parte que trata do código quando o usuário tecla enter
		String idTipoServico = (String) atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdTipoServico();
		String descricaoServico = atualizarAdicionarSolicitacaoEspecificacaoActionForm.getDescricaoTipoServico();

		// Verifica se o código foi digitado pela primeira vez ou se foi
		// modificado
		if(idTipoServico != null && !idTipoServico.trim().equals("") && (descricaoServico == null || descricaoServico.trim().equals(""))){

			FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, idTipoServico));
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection servicoTipoEncontrado = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

			if(servicoTipoEncontrado != null && !servicoTipoEncontrado.isEmpty()){
				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdTipoServico(""
								+ ((ServicoTipo) ((List) servicoTipoEncontrado).get(0)).getId());
				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setDescricaoTipoServico(((ServicoTipo) ((List) servicoTipoEncontrado)
								.get(0)).getDescricao());
				httpServletRequest.setAttribute("idTipoServicoNaoEncontrado", "true");

				httpServletRequest.setAttribute("nomeCampo", "ordemExecucao");

			}else{

				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdTipoServico("");
				httpServletRequest.setAttribute("nomeCampo", "idServicoOS");
				httpServletRequest.setAttribute("idTipoServicoNaoEncontrado", "exception");
				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setDescricaoTipoServico("Tipo Serviço Inexistente");

			}

		}

		sessao.removeAttribute("caminhoRetornoTelaPesquisaTipoServico");

		// -------Fim da Parte que trata do código quando o usuário tecla enter

		return retorno;
	}
}
