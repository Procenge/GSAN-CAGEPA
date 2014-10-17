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

package gcom.gui.cobranca;

import gcom.arrecadacao.ArrecadadorContrato;
import gcom.arrecadacao.FiltroArrecadador;
import gcom.arrecadacao.FiltroArrecadadorContrato;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.cliente.FiltroClienteRelacaoTipo;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.localidade.*;
import gcom.cobranca.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroNaoNulo;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0243] Inserir Comando de Ação de Conbrança - Tipo de Comando Cronograma
 * 
 * @author Rafael Santos
 * @since 24/01/2006
 * @author Virgínia Melo
 * @date 06/08/2009
 *       Retirando os campos que não estão mais sendo utilizados:
 *       Período de Referência das Contas -> periodoInicialConta, periodoFinalConta
 *       Período de Vencimento das Contas -> periodoVencimentoContaInicial,
 *       periodoVencimentoContaFinal
 */
public class ExibirInserirComandoAcaoCobrancaEventualCriterioRotaAction
				extends GcomAction {

	private Collection colecaoPesquisa = null;

	private String localidadeID = null;

	// private String gerenciaRegionalID = null;

	private String setorComercialCD = null;

	private HttpSession sessao;

	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirInserirComandoAcaoCobrancaEventualCriterioRota");

		// Mudar isso quando implementar a parte de segurança
		sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		sessao.removeAttribute("colecaoCobrancaGrupo");
		sessao.removeAttribute("colecaoCobrancaAtividade");
		sessao.removeAttribute("colecaoCobrancaAcao");
		sessao.removeAttribute("exibirTituloComandoPrecedente");

		String limparForm = (String) httpServletRequest.getParameter("limparForm");

		String validarCriterio = (String) httpServletRequest.getParameter("validarCriterio");

		String validar = (String) httpServletRequest.getParameter("validar");

		InserirComandoAcaoCobrancaEventualCriterioRotaActionForm inserirComandoAcaoCobrancaEventualCriterioRotaActionForm = (InserirComandoAcaoCobrancaEventualCriterioRotaActionForm) actionForm;

		if(limparForm != null && !limparForm.trim().equalsIgnoreCase("")){
			inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.reset(actionMapping, httpServletRequest);
			inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setIndicadorGerarBoletimCadastro("2");
			inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setIndicadorImoveisDebito("1");
			inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setIndicadorCriterioComando("1");
			inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setIndicadorGerarRelacaoDocumento("1");
			inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setFormatoArquivo(Integer.toString(TarefaRelatorio.TIPO_PDF));

			if(sessao.getAttribute("colecaoRota") != null){
				sessao.removeAttribute("colecaoRota");
			}
		}

		String[] cobrancaAcaoEscolhido = inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaAcao();
		if(validarCriterio == null){

			FiltroCobrancaAcao filtroCobrancaAcaoPrazoExecucao = new FiltroCobrancaAcao();

			if(cobrancaAcaoEscolhido != null){

				filtroCobrancaAcaoPrazoExecucao.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.ID, cobrancaAcaoEscolhido[0]));

				Collection<CobrancaAcao> colecaoCobrancaAcaoPrazoExecucao = fachada.pesquisar(filtroCobrancaAcaoPrazoExecucao,
								CobrancaAcao.class.getName());
				CobrancaAcao cobrancaAcaoPrazoExecucao = (CobrancaAcao) Util.retonarObjetoDeColecao(colecaoCobrancaAcaoPrazoExecucao);

				if(cobrancaAcaoPrazoExecucao != null && cobrancaAcaoPrazoExecucao.getQtdDiasRealizacao() != null
								&& cobrancaAcaoEscolhido.length == ConstantesSistema.SIM){
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setPrazoExecucao(cobrancaAcaoPrazoExecucao
									.getQtdDiasRealizacao().toString());
				}else{
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setPrazoExecucao("");
				}
			}
		}

		// verificação do critério cobrança
		if(inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaCriterio() != null
						&& !inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaCriterio().trim().equalsIgnoreCase("")){

			FiltroCobrancaCriterio filtroCobrancaCriterio = new FiltroCobrancaCriterio();
			filtroCobrancaCriterio.adicionarParametro(new ParametroSimples(FiltroCobrancaCriterio.ID, Integer
							.valueOf(inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaCriterio())));

			Collection<CobrancaCriterio> colecaoCobrancaCriterio = fachada.pesquisar(filtroCobrancaCriterio, CobrancaCriterio.class
							.getName());

			if(Util.isVazioOrNulo(colecaoCobrancaCriterio)){

				httpServletRequest.setAttribute("corCobrancaCriterio", "exception");

				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setCobrancaCriterio("");

				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeCobrancaCriterio("CRITERIO DE COBRANCA INEXISTENTE");

				httpServletRequest.setAttribute("nomeCampo", "cobrancaCriterio");

			}else{
				CobrancaCriterio cobrancaCriterio = (CobrancaCriterio) Util.retonarObjetoDeColecao(colecaoCobrancaCriterio);

				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setCobrancaCriterio(cobrancaCriterio.getId().toString());

				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeCobrancaCriterio(cobrancaCriterio
								.getDescricaoCobrancaCriterio());

			}
		}

		// Arrecadador
		FiltroArrecadadorContrato filtroArrecadadorContrato = new FiltroArrecadadorContrato();

		filtroArrecadadorContrato.adicionarParametro(new ParametroSimples(FiltroArrecadadorContrato.ARRECADADOR_INDICADOR_USO,
						ConstantesSistema.SIM));
		filtroArrecadadorContrato.adicionarParametro(new ParametroNaoNulo(FiltroArrecadadorContrato.CODIGO_CONVENIO_BOLETO_BANCARIO));
		filtroArrecadadorContrato.adicionarParametro(new ParametroNulo(FiltroArrecadadorContrato.DATA_CONTRATO_ENCERRAMENTO));

		// Ordena filtro de arrecadador por id do cliente
		filtroArrecadadorContrato.setCampoOrderBy(FiltroArrecadador.CLIENTE_ID);
		// Inclui a obejeto de cliente no filtro de arrecadador
		filtroArrecadadorContrato.adicionarCaminhoParaCarregamentoEntidade("arrecadador.cliente");
		// Preenche colecao de arrecadador
		Collection<ArrecadadorContrato> colecaoArrecadadorContrato = fachada.pesquisar(filtroArrecadadorContrato, ArrecadadorContrato.class
						.getName());
		if(!Util.isVazioOrNulo(colecaoArrecadadorContrato)){

			FiltroCliente filtroCliente = new FiltroCliente();
			Iterator iteratorColecaoArrecadador = colecaoArrecadadorContrato.iterator();
			Cliente cliente = new Cliente();
			while(iteratorColecaoArrecadador.hasNext()){
				ArrecadadorContrato arrecadadorContrato = (ArrecadadorContrato) iteratorColecaoArrecadador.next();
				cliente = arrecadadorContrato.getArrecadador().getCliente();
				filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, cliente.getId(), ParametroSimples.CONECTOR_OR));
				filtroCliente.setCampoOrderBy(FiltroCliente.NOME);
			}
			Collection colecaoClienteArrecadador = fachada.pesquisar(filtroCliente, Cliente.class.getName());
			sessao.setAttribute("colecaoClienteArrecadador", colecaoClienteArrecadador);
		}

		String limparRota = (String) httpServletRequest.getParameter("limparRota");

		// limpar as rotas
		if(limparRota != null && !limparRota.trim().equalsIgnoreCase("")){
			inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setRotaInicial("");
			sessao.setAttribute("colecaoRota", null);
			inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setRotaInicial(null);
			inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setRotaFinal("");
			inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setRotaFinal(null);
		}

		// obtém o comando de ação precedente selecionado
		CobrancaAcao cobrancaAcao = null;
		if(sessao.getAttribute("acaoCobrancaSelecionada") != null && !sessao.getAttribute("acaoCobrancaSelecionada").equals("")){
			FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();
			filtroCobrancaAcao.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcao.COBRANCA_ACAO_PRECEDENTE);
			filtroCobrancaAcao.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.ID, sessao
							.getAttribute("acaoCobrancaSelecionada")));

			Collection<CobrancaAcao> colecaoCobrancaAcao = fachada.pesquisar(filtroCobrancaAcao, CobrancaAcao.class.getName());

			if(colecaoCobrancaAcao != null && !colecaoCobrancaAcao.isEmpty()){
				cobrancaAcao = (CobrancaAcao) colecaoCobrancaAcao.iterator().next();
			}
		}

		String idCobrancaAcaoAtividadeComando = null;

		if(httpServletRequest.getParameter("tipoConsulta") != null && !httpServletRequest.getParameter("tipoConsulta").equals("")
						&& httpServletRequest.getParameter("idCampoEnviarDados") != null
						&& !httpServletRequest.getParameter("idCampoEnviarDados").equals("")){
			idCobrancaAcaoAtividadeComando = (String) httpServletRequest.getParameter("idCampoEnviarDados");
			sessao.setAttribute("cobrancaAcaoAtividadeComandoPrecedente", httpServletRequest.getParameter("idCampoEnviarDados"));

		}else if(httpServletRequest.getParameter("idCobrancaAcaoAtividadeComando") != null
						&& !httpServletRequest.getParameter("idCobrancaAcaoAtividadeComando").equals("")){
			idCobrancaAcaoAtividadeComando = (String) httpServletRequest.getParameter("idCobrancaAcaoAtividadeComando");
		}

		if(idCobrancaAcaoAtividadeComando != null && !idCobrancaAcaoAtividadeComando.equals("")){

			FiltroCobrancaAcaoAtividadeComando filtroCobrancaAcaoAtividadeComando = new FiltroCobrancaAcaoAtividadeComando();
			filtroCobrancaAcaoAtividadeComando.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcaoAtividadeComando.COBRANCA_ACAO);
			filtroCobrancaAcaoAtividadeComando.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcaoAtividadeComando.COBRANCA_GRUPO);
			filtroCobrancaAcaoAtividadeComando
							.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcaoAtividadeComando.COBRANCA_ATIVIDADE);
			filtroCobrancaAcaoAtividadeComando.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcaoAtividadeComando.USUARIO);
			filtroCobrancaAcaoAtividadeComando
							.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcaoAtividadeComando.GERENCIAL_REGIONAL);
			filtroCobrancaAcaoAtividadeComando
							.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcaoAtividadeComando.LOCALIDADE_INICIAL);
			filtroCobrancaAcaoAtividadeComando.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcaoAtividadeComando.ROTA_INICIAL);
			filtroCobrancaAcaoAtividadeComando.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcaoAtividadeComando.ROTA_FINAL);
			filtroCobrancaAcaoAtividadeComando.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcaoAtividadeComando.CLIENTE);
			filtroCobrancaAcaoAtividadeComando
							.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcaoAtividadeComando.CLIENTE_RELACAO_TIPO);
			filtroCobrancaAcaoAtividadeComando
							.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcaoAtividadeComando.COBRANCA_CRITERIO);
			filtroCobrancaAcaoAtividadeComando.adicionarParametro(new ParametroSimples(FiltroCobrancaAcaoAtividadeComando.ID,
							idCobrancaAcaoAtividadeComando));

			Collection colecaoCobrancaAcaoAtividadeComando = fachada.pesquisar(filtroCobrancaAcaoAtividadeComando,
							CobrancaAcaoAtividadeComando.class.getName());
			if(colecaoCobrancaAcaoAtividadeComando != null && !colecaoCobrancaAcaoAtividadeComando.isEmpty()){
				CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando = (CobrancaAcaoAtividadeComando) colecaoCobrancaAcaoAtividadeComando
								.iterator().next();

				if(httpServletRequest.getParameter("tipoConsulta") != null && !httpServletRequest.getParameter("tipoConsulta").equals("")){

					// remove o parâmetro do botão Selecionar Comando Precedente
					sessao.removeAttribute("caminhoRetornoTelaPesquisaAcaoCobranca");

					// verifica se o comando de ação precedente selecionado está de acordo com a
					// ação
					// precedente
					// selecionada
					if(cobrancaAcao != null){
						if(((cobrancaAcao.getCobrancaAcaoPredecessora() == null || cobrancaAcaoAtividadeComando.getCobrancaAcao() == null) || !cobrancaAcao
										.getCobrancaAcaoPredecessora().getId().equals(
														cobrancaAcaoAtividadeComando.getCobrancaAcao().getId()))){
							throw new ActionServletException("atencao.nao.acao.precedente", cobrancaAcaoAtividadeComando.getCobrancaAcao()
											.getDescricaoCobrancaAcao(), cobrancaAcao.getDescricaoCobrancaAcao());
						}
					}else{
						throw new ActionServletException("atencao.cobranca_acao_nao_informado");
					}

					// verifica se o comando de ação precedente selecionado ainda não foi realizado
					if(cobrancaAcaoAtividadeComando.getRealizacao() == null){
						throw new ActionServletException("atencao.comando.precedente.ainda.nao.realizado");
					}

					// // verifica se o comando de ação precedente selecionado já está associado a
					// // outro
					// // comando
					// FiltroCobrancaAcaoAtividadeComando filtroCobrancaAcaoAtividadeComandoAux =
					// new FiltroCobrancaAcaoAtividadeComando();
					// filtroCobrancaAcaoAtividadeComandoAux.adicionarParametro(new
					// ParametroSimples(
					// FiltroCobrancaAcaoAtividadeComando.PRECEDENTE,
					// idCobrancaAcaoAtividadeComando));
					//
					// Collection colecaoCobrancaAcaoAtividadeComandoAux =
					// fachada.pesquisar(filtroCobrancaAcaoAtividadeComandoAux,
					// CobrancaAcaoAtividadeComando.class.getName());
					//
					// if(colecaoCobrancaAcaoAtividadeComandoAux != null &&
					// !colecaoCobrancaAcaoAtividadeComandoAux.isEmpty()){
					// throw new
					// ActionServletException("atencao.comando.precedente.ja.esta.associado.a.outro.comando");
					// }

					if(cobrancaAcaoAtividadeComando.getDescricaoTitulo() != null){

						String titulo = cobrancaAcaoAtividadeComando.getDescricaoTitulo();

						if(!titulo.equals("") && titulo != null){
							sessao.setAttribute("exibirTituloComandoPrecedente", true);
						}else{
							sessao.removeAttribute("exibirTituloComandoPrecedente");
						}

						if(titulo.length() > 20){
							titulo = cobrancaAcaoAtividadeComando.getDescricaoTitulo().substring(0, 20);
						}

						inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setTituloComandoPrecedente(titulo);
						inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
										.setTituloCompletoComandoPrecedente(cobrancaAcaoAtividadeComando.getDescricaoTitulo());

					}

					// ação cobrança
					if(cobrancaAcao != null){
						String[] acaoCobranca = {cobrancaAcao.getId().toString()};
						inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setCobrancaAcao(acaoCobranca);

					}

					InserirComandoAcaoCobrancaEventualCriterioRotaActionForm.DESABILITARPARAMETROSADICIONAIS = "true";

				}else{

					// ação cobrança
					String[] acaoCobranca = {cobrancaAcaoAtividadeComando.getCobrancaAcao().getId().toString()};
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setCobrancaAcao(acaoCobranca);

					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setTituloComandoPrecedente("");
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setTituloCompletoComandoPrecedente("");

					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setCobrancaAtividade(cobrancaAcaoAtividadeComando
									.getCobrancaAtividade().getId().toString());

					if(cobrancaAcaoAtividadeComando.getDescricaoTitulo() != null){
						inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setTitulo(cobrancaAcaoAtividadeComando
										.getDescricaoTitulo());
					}

					if(cobrancaAcaoAtividadeComando.getDescricaoSolicitacao() != null){
						inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setDescricaoSolicitacao(cobrancaAcaoAtividadeComando
										.getDescricaoSolicitacao());
					}

					if(cobrancaAcaoAtividadeComando.getDataEncerramentoPrevista() != null){
						if(cobrancaAcaoAtividadeComando.getComando() != null){
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setPrazoExecucao(""
											+ Util.obterQuantidadeDiasEntreDuasDatas(cobrancaAcaoAtividadeComando.getComando(),
															cobrancaAcaoAtividadeComando.getDataEncerramentoPrevista()));
						}
					}

					if(cobrancaAcaoAtividadeComando.getQuantidadeMaximaDocumentos() != null){
						inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setQuantidadeMaximaDocumentos(""
										+ cobrancaAcaoAtividadeComando.getQuantidadeMaximaDocumentos());
					}

					if(cobrancaAcaoAtividadeComando.getIndicadorDebito() != null){
						inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setIndicadorImoveisDebito(""
										+ cobrancaAcaoAtividadeComando.getIndicadorDebito());
					}

					InserirComandoAcaoCobrancaEventualCriterioRotaActionForm.DESABILITARPARAMETROSADICIONAIS = "false";
				}

				// cobranca grupo
				if(cobrancaAcaoAtividadeComando.getCobrancaGrupo() == null){
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setCobrancaGrupo("");
				}else{
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setCobrancaGrupo(cobrancaAcaoAtividadeComando
									.getCobrancaGrupo().getId().toString());
				}

				// gerencia regional
				if(cobrancaAcaoAtividadeComando.getGerenciaRegional() == null){
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setGerenciaRegional("");
				}else{
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setGerenciaRegional(cobrancaAcaoAtividadeComando
									.getGerenciaRegional().getId().toString());
				}

				// unidade negocio
				if(cobrancaAcaoAtividadeComando.getUnidadeNegocio() == null){
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setUnidadeNegocio("");
				}else{
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setUnidadeNegocio(cobrancaAcaoAtividadeComando
									.getUnidadeNegocio().getId().toString());
				}

				// localidade inicial
				if(cobrancaAcaoAtividadeComando.getLocalidadeInicial() == null){
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setLocalidadeOrigemID("");
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeLocalidadeOrigem("");
				}else{
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setLocalidadeOrigemID(cobrancaAcaoAtividadeComando
									.getLocalidadeInicial().getId().toString());
					FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
					filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, cobrancaAcaoAtividadeComando
									.getLocalidadeInicial().getId()));
					Collection colecaoLocalidadesIniciais = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

					Localidade localidadeInicial = (Localidade) colecaoLocalidadesIniciais.iterator().next();
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeLocalidadeOrigem(localidadeInicial.getDescricao());
				}
				// localidade final
				if(cobrancaAcaoAtividadeComando.getLocalidadeFinal() == null){
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setLocalidadeDestinoID("");
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeLocalidadeDestino("");
				}else{
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setLocalidadeDestinoID(cobrancaAcaoAtividadeComando
									.getLocalidadeFinal().getId().toString());
					FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
					filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, cobrancaAcaoAtividadeComando
									.getLocalidadeFinal().getId()));
					Collection colecaoLocalidadesFinais = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

					Localidade localidadeFinal = (Localidade) colecaoLocalidadesFinais.iterator().next();
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeLocalidadeDestino(localidadeFinal.getDescricao());
				}

				// setor comercial inicial
				if(cobrancaAcaoAtividadeComando.getCodigoSetorComercialInicial() == null){
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialOrigemCD("");
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeSetorComercialOrigem("");
				}else{
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialOrigemCD(cobrancaAcaoAtividadeComando
									.getCodigoSetorComercialInicial().toString());
					FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
					filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
									cobrancaAcaoAtividadeComando.getCodigoSetorComercialInicial().toString()));

					if(cobrancaAcaoAtividadeComando.getLocalidadeInicial() != null){
						filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.LOCALIDADE,
										cobrancaAcaoAtividadeComando.getLocalidadeInicial()));
					}

					Collection colecaoSetorComercialIniciais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

					SetorComercial setorComercialInicial = (SetorComercial) colecaoSetorComercialIniciais.iterator().next();
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeSetorComercialOrigem(setorComercialInicial
									.getDescricao());
				}

				// setor comercial final
				if(cobrancaAcaoAtividadeComando.getCodigoSetorComercialFinal() == null){
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialDestinoCD("");
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeSetorComercialDestino("");
				}else{
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialDestinoCD(cobrancaAcaoAtividadeComando
									.getCodigoSetorComercialFinal().toString());
					FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
					filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
									cobrancaAcaoAtividadeComando.getCodigoSetorComercialFinal().toString()));

					if(cobrancaAcaoAtividadeComando.getLocalidadeFinal() != null){
						filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.LOCALIDADE,
										cobrancaAcaoAtividadeComando.getLocalidadeFinal()));
					}

					Collection colecaoSetorComercialFinais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

					SetorComercial setorComercialFinal = (SetorComercial) colecaoSetorComercialFinais.iterator().next();
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeSetorComercialDestino(setorComercialFinal
									.getDescricao());
				}
				
				// quadra inicial
				if(cobrancaAcaoAtividadeComando.getNumeroQuadraInicial() == null){
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setQuadraInicial("");
				}else{
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setQuadraInicial(cobrancaAcaoAtividadeComando
									.getNumeroQuadraInicial().toString());
				}
					
				// quadra final
				if(cobrancaAcaoAtividadeComando.getNumeroQuadraFinal() == null){
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setQuadraFinal("");
				}else{
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setQuadraFinal(cobrancaAcaoAtividadeComando
									.getNumeroQuadraFinal().toString());
				}
				

				// rota inicial
				if(cobrancaAcaoAtividadeComando.getRotaInicial() == null){
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setRotaInicial("");
					sessao.setAttribute("colecaoRota", null);
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setRotaInicial(null);
				}else{
					// carregarRota(inserirComandoAcaoCobrancaEventualCriterioRotaActionForm,fachada,cobrancaAcaoAtividadeComando.getCodigoSetorComercialInicial().toString(),inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getLocalidadeOrigemID());
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setRotaInicial(cobrancaAcaoAtividadeComando.getRotaInicial()
									.getCodigo().toString());
				}

				// rota final
				if(cobrancaAcaoAtividadeComando.getRotaFinal() == null){
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setRotaFinal("");
					sessao.setAttribute("colecaoRota", null);
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setRotaFinal(null);
				}else{
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setRotaFinal(cobrancaAcaoAtividadeComando.getRotaFinal()
									.getCodigo().toString());
				}

				// cliente
				if(cobrancaAcaoAtividadeComando.getCliente() == null){
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeCliente("");
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setIdCliente("");
				}else{
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setIdCliente(cobrancaAcaoAtividadeComando.getCliente().getId()
									.toString());
					FiltroCliente filtroCliente = new FiltroCliente();
					filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, cobrancaAcaoAtividadeComando.getCliente()
									.getId().toString()));
					Collection colecaoCliente = fachada.pesquisar(filtroCliente, Cliente.class.getName());
					Cliente cliente = (Cliente) colecaoCliente.iterator().next();
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeCliente(cliente.getNome());
				}

				// tipo relação
				if(cobrancaAcaoAtividadeComando.getClienteRelacaoTipo() == null){
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setClienteRelacaoTipo("");
				}else{
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setClienteRelacaoTipo(cobrancaAcaoAtividadeComando
									.getClienteRelacaoTipo().getId().toString());
				}

				// indicador criterio cobranca
				if(cobrancaAcaoAtividadeComando.getIndicadorCriterio() != null){
					if(cobrancaAcaoAtividadeComando.getIndicadorCriterio().equals(ConstantesSistema.SIM)){
						inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setIndicadorCriterioComando("1");
					}else{
						inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setIndicadorCriterioComando("2");
					}
				}

				// id cobrança criterio
				if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getCobrancaCriterio() != null){
					sessao.setAttribute("idCobrancaCriterio", cobrancaAcaoAtividadeComando.getCobrancaCriterio().getId().toString());
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setCobrancaCriterio(cobrancaAcaoAtividadeComando
									.getCobrancaCriterio().getId().toString());
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeCobrancaCriterio(cobrancaAcaoAtividadeComando
									.getCobrancaCriterio().getDescricaoCobrancaCriterio());
				}

				// indicador de criterio
				if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getIndicadorCriterio() != null){
					if(cobrancaAcaoAtividadeComando.getIndicadorCriterio().shortValue() == 1){
						inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setCobrancaAtividadeIndicadorExecucao("1");
						// manterComandoAcaoCobrancaDetalhesActionForm.setIndicador("Rota");
					}else if(cobrancaAcaoAtividadeComando.getIndicadorCriterio().shortValue() == 2){
						inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setCobrancaAtividadeIndicadorExecucao("2");
						// manterComandoAcaoCobrancaDetalhesActionForm.setIndicador("Comando");
					}
				}

			}

		}// fim do comando ação cobrança pelo request
		else{
			InserirComandoAcaoCobrancaEventualCriterioRotaActionForm.DESABILITARPARAMETROSADICIONAIS = "false";
		}

		// valdiar os criteorios de rota e comando para o usuário selecionar
		if(validarCriterio != null && !validarCriterio.equals("")){

			if(validar != null && validar.equals("Atividade")){// validar a
				// atividade
				// selecionada

				if(inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaAtividade() != null
								&& !inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaAtividade().equals("")){
					FiltroCobrancaAtividade filtroCobrancaAtividade = new FiltroCobrancaAtividade();
					filtroCobrancaAtividade.adicionarParametro(new ParametroSimples(FiltroCobrancaAtividade.ID,
									inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaAtividade()));
					Collection colecaoCobrancaAtividade = fachada.pesquisar(filtroCobrancaAtividade, CobrancaAtividade.class.getName());
					if(colecaoCobrancaAtividade == null || colecaoCobrancaAtividade.isEmpty()){
						inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setCobrancaAtividadeIndicadorExecucao("");
						// httpServletRequest.setAttribute("cobrancaAtividadeIndicadorExecucao","");
					}else{
						CobrancaAtividade cobrancaAtividade = (CobrancaAtividade) colecaoCobrancaAtividade.iterator().next();
						String indicador = null;
						if(cobrancaAtividade.getIndicadorExecucao() == null){
							indicador = "";
						}else{
							indicador = cobrancaAtividade.getIndicadorExecucao().toString();
						}
						inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setCobrancaAtividadeIndicadorExecucao(indicador);
						// httpServletRequest.setAttribute("cobrancaAtividadeIndicadorExecucao",cobrancaAtividade.getIndicadorExecucao().toString());
					}
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
									.setCobrancaAtividade(inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaAtividade());
				}
			}
		}

		// CARREGAR AS COBRANÇAS GRUPO - INICIO
		FiltroCobrancaGrupo filtroCobrancaGrupo = new FiltroCobrancaGrupo();
		if(sessao.getAttribute("colecaoCobrancaGrupo") == null){
			Collection colecaoCobrancaGrupo = (Collection) fachada.pesquisar(filtroCobrancaGrupo, CobrancaGrupo.class.getName());
			filtroCobrancaGrupo.setCampoOrderBy(FiltroCobrancaGrupo.DESCRICAO);

			if(colecaoCobrancaGrupo == null || colecaoCobrancaGrupo.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Tabela Cobrança Grupo");
			}
			// carregar grupo de cobrança
			sessao.setAttribute("colecaoCobrancaGrupo", colecaoCobrancaGrupo);

		}
		// FIM COBRANÇA GRUPO

		// CARREGAR AS COBRANÇAS ATIVIDADE - INICIO

		FiltroCobrancaAtividade filtroCobrancaAtividade = new FiltroCobrancaAtividade();
		if(sessao.getAttribute("colecaoCobrancaAtividade") == null){
			filtroCobrancaAtividade.setCampoOrderBy(FiltroCobrancaAtividade.DESCRICAO);
			filtroCobrancaAtividade.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroCobrancaAtividade.ID,
							CobrancaAtividade.ENCERRAR));
			filtroCobrancaAtividade.adicionarParametro(new ParametroSimples(FiltroCobrancaAtividade.INDICADOR_USO, ConstantesSistema.SIM));
			Collection colecaoCobrancaAtividade = (Collection) fachada
							.pesquisar(filtroCobrancaAtividade, CobrancaAtividade.class.getName());
			if(colecaoCobrancaAtividade == null || colecaoCobrancaAtividade.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Tabela Cobrança Atividade");
			}
			// carregar atividade de cobrança
			sessao.setAttribute("colecaoCobrancaAtividade", colecaoCobrancaAtividade);

		}

		// FIM COBRANÇA ATIVIDADE

		// CARREGAR AS COBRANÇAS ACAO - INICIO

		FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();
		if(sessao.getAttribute("colecaoCobrancaAcao") == null){
			filtroCobrancaAcao.setCampoOrderBy(FiltroCobrancaAcao.DESCRICAO);
			filtroCobrancaAcao.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection colecaoCobrancaAcao = (Collection) fachada.pesquisar(filtroCobrancaAcao, CobrancaAcao.class.getName());

			if(colecaoCobrancaAcao == null || colecaoCobrancaAcao.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Tabela Cobrança Ação");
			}
			// carregar ação de cobrança
			sessao.setAttribute("colecaoCobrancaAcao", colecaoCobrancaAcao);

		}

		// FIM COBRANÇA Ação

		Collection colecaoEmpresa = null;

		// caso a Ação de Cobrança selecionada seja “COBRANÇA ADMINISTRATIVA”,
		// exibir as empresas de cobrança administrativa
		if(cobrancaAcaoEscolhido != null){
			for(int i = 0; i < cobrancaAcaoEscolhido.length; i++){
				if(cobrancaAcaoEscolhido[i].equals(ConstantesSistema.COBRANCA_ADMINISTRATIVA)){
					colecaoEmpresa = fachada.consultarEmpresaCobrancaAdministrativa();
					httpServletRequest.setAttribute("isCobrancaAdministrativa", true);
					break;
				}
			}
		}

		if(colecaoEmpresa == null){
			colecaoEmpresa = new ArrayList();

			FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
			filtroEmpresa.setCampoOrderBy(FiltroEmpresa.DESCRICAO);

			filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna empresa
			colecaoEmpresa = fachada.pesquisar(filtroEmpresa, Empresa.class.getName());
		}

		if(colecaoEmpresa == null || colecaoEmpresa.isEmpty()){

			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Empresa");
		}

		sessao.setAttribute("colecaoEmpresa", colecaoEmpresa);



		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

		filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.NOME_ABREVIADO);
		Collection colecaoGerenciaRegional = (Collection) fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

		if(colecaoGerenciaRegional == null || colecaoGerenciaRegional.isEmpty()){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Tabela Gerência Regional");
		}
		// carregar gerencia regional
		sessao.setAttribute("colecaoGerenciaRegional", colecaoGerenciaRegional);

		FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();

		filtroUnidadeNegocio.setCampoOrderBy(FiltroUnidadeNegocio.NOME_ABREVIADO);
		Collection colecaoUnidadeNegocio = (Collection) fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

		if(colecaoUnidadeNegocio == null || colecaoUnidadeNegocio.isEmpty()){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Tabela Unidade Negócio");
		}
		// carregar gerencia regional
		sessao.setAttribute("colecaoUnidadeNegocio", colecaoUnidadeNegocio);

		FiltroClienteRelacaoTipo filtroClienteRelacaoTipo = new FiltroClienteRelacaoTipo();
		filtroClienteRelacaoTipo.setCampoOrderBy(FiltroClienteRelacaoTipo.DESCRICAO);
		// carrega os cliente relação tipo
		Collection colecaoClienteRelacaoTipo = (Collection) fachada.pesquisar(filtroClienteRelacaoTipo, ClienteRelacaoTipo.class.getName());

		if(colecaoClienteRelacaoTipo == null || colecaoClienteRelacaoTipo.isEmpty()){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Tabela Cliente Relação Tipo");
		}
		// carregar cliente relação tipo
		sessao.setAttribute("colecaoClienteRelacaoTipo", colecaoClienteRelacaoTipo);

		/*
		 * String periodoFinalConta = fachada.pesquisarParametrosDoSistema().getAnoMesArrecadacao()
		 * + "";
		 * if ((inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
		 * .getPeriodoFinalConta() != null &&
		 * inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
		 * .getPeriodoFinalConta().equals(""))
		 * | inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
		 * .getPeriodoFinalConta() == null) {
		 * String ano = periodoFinalConta.substring(0, 4);
		 * String mes = periodoFinalConta.substring(4, 6);
		 * inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
		 * .setPeriodoFinalConta(mes + "/" + ano);
		 * }
		 * if ((inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
		 * .getPeriodoVencimentoContaFinal() != null &&
		 * inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
		 * .getPeriodoVencimentoContaFinal().equals(""))
		 * | inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
		 * .getPeriodoVencimentoContaFinal() == null) {
		 * Calendar calendarNova = Calendar.getInstance();
		 * calendarNova.add(Calendar.MONTH, -1);
		 * String dataNova = "";
		 * dataNova = calendarNova.getActualMaximum(Calendar.DAY_OF_MONTH)
		 * + "";
		 * if (calendarNova.get(Calendar.MONTH) < 9) {
		 * dataNova = dataNova + "/0"
		 * + (calendarNova.get(Calendar.MONTH) + 1);
		 * } else {
		 * dataNova = dataNova + "/"
		 * + (calendarNova.get(Calendar.MONTH) + 1);
		 * }
		 * dataNova = dataNova + "/" + calendarNova.get(Calendar.YEAR);
		 * inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
		 * .setPeriodoVencimentoContaFinal(dataNova);
		 * }
		 */

		String objetoConsulta = (String) httpServletRequest.getParameter("objetoConsulta");

		String inscricaoTipo = (String) httpServletRequest.getParameter("inscricaoTipo");

		if(objetoConsulta != null && !objetoConsulta.trim().equalsIgnoreCase("") && inscricaoTipo != null
						&& !inscricaoTipo.trim().equalsIgnoreCase("")){

			switch(Integer.parseInt(objetoConsulta)){
				// Localidade
				case 1:

					pesquisarLocalidade(inscricaoTipo, inserirComandoAcaoCobrancaEventualCriterioRotaActionForm, fachada,
									httpServletRequest);

					break;
				// Setor Comercial
				case 2:

					pesquisarLocalidade(inscricaoTipo, inserirComandoAcaoCobrancaEventualCriterioRotaActionForm, fachada,
									httpServletRequest);

					pesquisarSetorComercial(inscricaoTipo, inserirComandoAcaoCobrancaEventualCriterioRotaActionForm, fachada,
									httpServletRequest);

					break;
				case 3:
					pesquisarCliente(inscricaoTipo, inserirComandoAcaoCobrancaEventualCriterioRotaActionForm, fachada, httpServletRequest);
					break;

				default:
					break;
			}
		}

		return retorno;
	}

	/**
	 * Pesquisa a Localidade
	 * 
	 * @param inscricaoTipo
	 * @param imovelOutrosCriteriosActionForm
	 * @param fachada
	 * @param httpServletRequest
	 */
	private void pesquisarLocalidade(String inscricaoTipo,
					InserirComandoAcaoCobrancaEventualCriterioRotaActionForm inserirComandoAcaoCobrancaEventualCriterioRotaActionForm,
					Fachada fachada, HttpServletRequest httpServletRequest){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

		if(inscricaoTipo.equalsIgnoreCase("origem")){
			inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setInscricaoTipo("origem");
			// Recebe o valor do campo localidadeOrigemID do formulário.
			localidadeID = (String) inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getLocalidadeOrigemID();

			// / gerenciaRegionalID = (String)
			// inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
			// .getGerenciaRegional();

			if(localidadeID != null && !localidadeID.equals("")){

				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeID));

				// / filtroLocalidade.adicionarParametro(new ParametroSimples(
				// // FiltroLocalidade.ID_GERENCIA, gerenciaRegionalID));

				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna localidade
				colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					// Localidade nao encontrada
					// Limpa os campos localidadeOrigemID e nomeLocalidadeOrigem do
					// formulário
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setLocalidadeOrigemID("");
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeLocalidadeOrigem("Localidade Inexistente");
					httpServletRequest.setAttribute("corLocalidadeOrigem", "exception");
					httpServletRequest.setAttribute("nomeCampo", "localidadeOrigemID");

				}else{
					Localidade objetoLocalidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
									.setLocalidadeOrigemID(String.valueOf(objetoLocalidade.getId()));
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeLocalidadeOrigem(objetoLocalidade.getDescricao());
					httpServletRequest.setAttribute("corLocalidadeOrigem", "valor");

					String localidadeDestinoID = (String) inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getLocalidadeDestinoID();
					// verifica o valor das localidades, origem e final
					if(localidadeDestinoID != null){

						if(localidadeDestinoID.equals("")){
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setLocalidadeDestinoID(String.valueOf(objetoLocalidade
											.getId()));
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeLocalidadeDestino(objetoLocalidade
											.getDescricao());
						}else{
							int localidadeDestino = Integer.valueOf(localidadeDestinoID).intValue();
							int localidadeOrigem = objetoLocalidade.getId().intValue();
							if(localidadeOrigem > localidadeDestino){
								inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setLocalidadeDestinoID(String
												.valueOf(objetoLocalidade.getId()));
								inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeLocalidadeDestino(objetoLocalidade
												.getDescricao());
							}
						}
					}
					httpServletRequest.setAttribute("nomeCampo", "localidadeDestinoID");

				}
			}
		}else{
			// Recebe o valor do campo localidadeDestinoID do formulário.
			localidadeID = (String) inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getLocalidadeDestinoID();

			// / gerenciaRegionalID = (String)
			// inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
			// .getGerenciaRegional();

			if(localidadeID != null && !localidadeID.equals("")){

				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeID));

				// / filtroLocalidade.adicionarParametro(new ParametroSimples(
				// FiltroLocalidade.ID_GERENCIA, gerenciaRegionalID));

				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna localidade
				colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setInscricaoTipo("destino");

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					// Localidade nao encontrada
					// Limpa os campos localidadeDestinoID e nomeLocalidadeDestino
					// do formulário
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setLocalidadeDestinoID("");
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeLocalidadeDestino("Localidade inexistente");
					httpServletRequest.setAttribute("corLocalidadeDestino", "exception");
					httpServletRequest.setAttribute("nomeCampo", "localidadeDestinoID");

				}else{
					Localidade objetoLocalidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);

					int localidadeDestino = objetoLocalidade.getId().intValue();

					String localidade = (String) inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getLocalidadeOrigemID();

					if(localidade == null || localidade.equals("")){
						inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setLocalidadeDestinoID(String.valueOf(objetoLocalidade
										.getId()));
						inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeLocalidadeDestino(objetoLocalidade.getDescricao());
						httpServletRequest.setAttribute("corLocalidadeDestino", "valor");
						httpServletRequest.setAttribute("nomeCampo", "setorComercialOrigemCD");
					}else{

						int localidadeOrigem = Integer.valueOf(localidade).intValue();
						if(localidadeDestino < localidadeOrigem){
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setLocalidadeDestinoID("");
							// inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
							// .setNomeLocalidadeDestino("Loc. Final maior que a
							// Inicial");
							httpServletRequest.setAttribute("mensagem", "Localidae Final menor que o Inicial");
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeLocalidadeDestino("");
							httpServletRequest.setAttribute("corLocalidadeDestino", "valor");

							httpServletRequest.setAttribute("nomeCampo", "localidadeDestinoID");

						}else{
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setLocalidadeDestinoID(String.valueOf(objetoLocalidade
											.getId()));
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeLocalidadeDestino(objetoLocalidade
											.getDescricao());
							httpServletRequest.setAttribute("corLocalidadeDestino", "valor");
							httpServletRequest.setAttribute("nomeCampo", "setorComercialOrigemCD");
						}
					}
				}
			}
		}

	}

	/**
	 * Pesquisa o Setor Comercial
	 * 
	 * @param inscricaoTipo
	 * @param imovelOutrosCriteriosActionForm
	 * @param fachada
	 * @param httpServletRequest
	 */
	private void pesquisarSetorComercial(String inscricaoTipo,
					InserirComandoAcaoCobrancaEventualCriterioRotaActionForm inserirComandoAcaoCobrancaEventualCriterioRotaActionForm,
					Fachada fachada, HttpServletRequest httpServletRequest){

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

		if(inscricaoTipo.equalsIgnoreCase("origem")){
			inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setInscricaoTipo("origem");
			// Recebe o valor do campo localidadeOrigemID do formulário.
			localidadeID = (String) inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getLocalidadeOrigemID();

			// O campo localidadeOrigemID será obrigatório
			if(localidadeID == null || localidadeID.trim().equalsIgnoreCase("")){
				// Limpa o campo setorComercialOrigemCD do formulário
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialOrigemCD("");
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
								.setNomeSetorComercialOrigem("Informe a localidade da inscrição de origem.");
				httpServletRequest.setAttribute("corSetorComercialOrigem", "exception");
				httpServletRequest.setAttribute("nomeCampo", "setorComercialOrigemCD");

			}else{

				setorComercialCD = (String) inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getSetorComercialOrigemCD();

				// Adiciona o id da localidade que está no formulário para compor a pesquisa.
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidadeID));

				// Adiciona o código do setor comercial que esta no formulário para compor a
				// pesquisa.
				filtroSetorComercial
								.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setorComercialCD));

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna setorComercial
				colecaoPesquisa = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					// Setor Comercial nao encontrado
					// Limpa os campos setorComercialOrigemCD,
					// nomeSetorComercialOrigem e setorComercialOrigemID do
					// formulário
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialOrigemCD("");
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialOrigemID("");
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeSetorComercialOrigem("Setor Comercial Inexistente");
					httpServletRequest.setAttribute("corSetorComercialOrigem", "exception");
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setRotaInicial(null);
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setRotaFinal(null);

					httpServletRequest.setAttribute("nomeCampo", "setorComercialOrigemCD");

				}else{
					SetorComercial objetoSetorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoPesquisa);
					// setorComercialID =
					// objetoSetorComercial.getId().toString();
					// setorComercialOrigem
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialOrigemCD(String.valueOf(objetoSetorComercial
									.getCodigo()));
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialOrigemID(String.valueOf(objetoSetorComercial
									.getId()));
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeSetorComercialOrigem(objetoSetorComercial
									.getDescricao());
					httpServletRequest.setAttribute("corSetorComercialOrigem", "valor");

					String setorComercialDestinoCD = (String) inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
									.getSetorComercialDestinoCD();

					// verifica o valor dos setores comerciais, origem e final
					if(setorComercialDestinoCD != null){

						if(setorComercialDestinoCD.equals("")){

							// setorComercialDestino
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialDestinoCD(String
											.valueOf(objetoSetorComercial.getCodigo()));
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialDestinoID(String
											.valueOf(objetoSetorComercial.getId()));
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeSetorComercialDestino(objetoSetorComercial
											.getDescricao());

							// carregarRota(
							// inserirComandoAcaoCobrancaEventualCriterioRotaActionForm,
							// fachada,
							// objetoSetorComercial.getCodigo()+"",localidadeID);

						}else{

							int setorDestino = Integer.valueOf(setorComercialDestinoCD).intValue();
							int setorOrigem = objetoSetorComercial.getCodigo();
							if(setorOrigem > setorDestino){

								// setorComercialDestino
								inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialDestinoCD(String
												.valueOf(objetoSetorComercial.getCodigo()));
								inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialDestinoID(String
												.valueOf(objetoSetorComercial.getId()));
								inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeSetorComercialDestino(objetoSetorComercial
												.getDescricao());

								// carregarRota(
								// inserirComandoAcaoCobrancaEventualCriterioRotaActionForm,
								// fachada,
								// objetoSetorComercial.getCodigo()+"",localidadeID);
							}
						}
						httpServletRequest.setAttribute("nomeCampo", "setorComercialDestinoCD");
					}
				}
			}
		}else{

			inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setInscricaoTipo("destino");

			// Recebe o valor do campo localidadeDestinoID do formulário.
			localidadeID = (String) inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getLocalidadeDestinoID();

			// O campo localidadeOrigem será obrigatório
			if(localidadeID != null && !localidadeID.trim().equalsIgnoreCase("")){

				setorComercialCD = (String) inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getSetorComercialDestinoCD();

				// Adiciona o id da localidade que está no formulário para
				// compor a pesquisa.
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidadeID));

				// Adiciona o código do setor comercial que esta no formulário
				// para compor a pesquisa.
				filtroSetorComercial
								.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setorComercialCD));

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna setorComercial
				colecaoPesquisa = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					// Setor Comercial nao encontrado
					// Limpa os campos setorComercialDestinoCD,
					// nomeSetorComercialDestino e setorComercialDestinoID do
					// formulário
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialDestinoCD("");
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialDestinoID("");
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeSetorComercialDestino("Setor Comercial Inexistente");
					httpServletRequest.setAttribute("corSetorComercialDestino", "exception");
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setRotaInicial(null);
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setRotaFinal(null);
					httpServletRequest.setAttribute("nomeCampo", "setorComercialDestinoCD");
				}else{
					SetorComercial objetoSetorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoPesquisa);

					int setorDestino = objetoSetorComercial.getCodigo();

					String setor = (String) inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getSetorComercialOrigemCD();

					if(setor != null && !setor.equals("")){

						int setorOrigem = Integer.valueOf(setor).intValue();
						if(setorDestino < setorOrigem){

							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialDestinoCD("");
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialDestinoID("");
							// inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
							// .setNomeSetorComercialDestino("Setor Final maior
							// que Inicial");
							httpServletRequest.setAttribute("mensagem", "Setor Comercial Final menor que o Inicial");
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeSetorComercialDestino("");
							httpServletRequest.setAttribute("corSetorComercialDestino", "valor");

							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setRotaInicial(null);
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setRotaFinal(null);
							httpServletRequest.setAttribute("nomeCampo", "setorComercialDestinoCD");

						}else{
							// rota
							// carregarRota(
							// inserirComandoAcaoCobrancaEventualCriterioRotaActionForm,
							// fachada,
							// objetoSetorComercial.getCodigo()+"",localidadeID
							// );

							// setor comercial destino
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialDestinoCD(String
											.valueOf(objetoSetorComercial.getCodigo()));
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialDestinoID(String
											.valueOf(objetoSetorComercial.getId()));
							inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeSetorComercialDestino(objetoSetorComercial
											.getDescricao());
							httpServletRequest.setAttribute("corSetorComercialDestino", "valor");
							httpServletRequest.setAttribute("nomeCampo", "rotaFinal");
						}
					}else{

						// carregarRota(
						// inserirComandoAcaoCobrancaEventualCriterioRotaActionForm,
						// fachada,
						// objetoSetorComercial.getCodigo()+"",localidadeID);

						// setor comercial destino
						inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialDestinoCD(String
										.valueOf(objetoSetorComercial.getCodigo()));
						inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialDestinoID(String
										.valueOf(objetoSetorComercial.getId()));
						inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeSetorComercialDestino(objetoSetorComercial
										.getDescricao());
						httpServletRequest.setAttribute("corSetorComercialDestino", "valor");
						httpServletRequest.setAttribute("nomeCampo", "rotaFinal");

					}
				}
			}else{
				// Limpa o campo setorComercialDestinoCD do formulário
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setSetorComercialDestinoCD("");
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
								.setNomeSetorComercialDestino("Informe a localidade da inscrição de destino.");
				httpServletRequest.setAttribute("corSetorComercialDestino", "exception");
				httpServletRequest.setAttribute("nomeCampo", "setorComercialDestinoCD");

			}
		}
	}

	private void preencherCampoPrazoExecucao(){

		Fachada fachada = Fachada.getInstancia();

	}

	/**
	 * Inicializa a Rota
	 * 
	 * @param inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
	 * @param fachada
	 * @param objetoSetorComercial
	 */
	public void carregarRota(
					InserirComandoAcaoCobrancaEventualCriterioRotaActionForm inserirComandoAcaoCobrancaEventualCriterioRotaActionForm,
					Fachada fachada, String codigoSetorComercial, String idLocalidade){

		FiltroRota filtroRota = new FiltroRota();
		filtroRota.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.LOCALIDADE_ID, idLocalidade));
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_CODIGO, codigoSetorComercial));
		Collection colecaoRota = (Collection) fachada.pesquisar(filtroRota, Rota.class.getName());
		sessao.setAttribute("colecaoRota", colecaoRota);
		inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setRotaInicial("rota");
		inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setRotaFinal("rota");

	}

	/**
	 * Pesquisa o Setor Comercial
	 * 
	 * @param inscricaoTipo
	 * @param imovelOutrosCriteriosActionForm
	 * @param fachada
	 * @param httpServletRequest
	 */
	private void pesquisarCliente(String inscricaoTipo,
					InserirComandoAcaoCobrancaEventualCriterioRotaActionForm inserirComandoAcaoCobrancaEventualCriterioRotaActionForm,
					Fachada fachada, HttpServletRequest httpServletRequest){

		String idCliente = null;
		if(inscricaoTipo != null && inscricaoTipo.equals("superior")){
			idCliente = inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCodigoClienteSuperior();
		}else{
			idCliente = inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getIdCliente();
		}

		// -------Parte que trata do código quando o usuário tecla enter
		// se o id do cliente for diferente de nulo
		if(idCliente != null && !idCliente.toString().trim().equalsIgnoreCase("")){

			FiltroCliente filtroCliente = new FiltroCliente();
			Collection clientes = null;
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, Integer.valueOf(idCliente)));
			clientes = fachada.pesquisar(filtroCliente, Cliente.class.getName());
			if(clientes == null || clientes.isEmpty()){
				if(inscricaoTipo != null && inscricaoTipo.equals("superior")){
					httpServletRequest.setAttribute("codigoClienteSuperiorNaoEncontrado", "true");
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeClienteSuperior("");
					httpServletRequest.setAttribute("nomeCampo", "codigoClienteSuperior");
				}else{
					httpServletRequest.setAttribute("codigoClienteNaoEncontrado", "true");
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeCliente("");
					httpServletRequest.setAttribute("nomeCampo", "idCliente");
				}
			}else{
				// O cliente foi encontrado

				if(inscricaoTipo != null && inscricaoTipo.equals("superior")){
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setCodigoClienteSuperior(((Cliente) ((List) clientes).get(0))
									.getId().toString());
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeClienteSuperior(((Cliente) ((List) clientes).get(0))
									.getNome());
				}else{
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setIdCliente(((Cliente) ((List) clientes).get(0)).getId()
									.toString());
					inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeCliente(((Cliente) ((List) clientes).get(0)).getNome());
				}

				Cliente cliente = new Cliente();

				cliente = (Cliente) clientes.iterator().next();
				sessao.setAttribute("clienteObj", cliente);
			}
		}
	}

	/**
	 * Pesquisa a cobranca criterio
	 * 
	 * @param imovelOutrosCriteriosActionForm
	 * @param fachada
	 * @param httpServletRequest
	 */

	private void pesquisarCobrancaCriterio(
					InserirComandoAcaoCobrancaEventualCriterioRotaActionForm inserirComandoAcaoCobrancaEventualCriterioRotaActionForm,
					Fachada fachada, HttpServletRequest httpServletRequest){

		String idCobrancaCriterio = inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaCriterio();
		// -------Parte que trata do código quando o usuário tecla enter
		// se o id do cliente for diferente de nulo
		if(idCobrancaCriterio != null && !idCobrancaCriterio.toString().trim().equalsIgnoreCase("")){
			FiltroCobrancaCriterio filtroCobrancaCriterio = new FiltroCobrancaCriterio();
			Collection clientes = null;
			filtroCobrancaCriterio.adicionarParametro(new ParametroSimples(FiltroCobrancaCriterio.ID, Integer
							.valueOf(inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.getCobrancaCriterio())));
			Collection<CobrancaCriterio> colecaoCobrancaCriterio = fachada.pesquisar(filtroCobrancaCriterio, CobrancaCriterio.class
							.getName());
			if(Util.isVazioOrNulo(colecaoCobrancaCriterio)){
				throw new ActionServletException("atencao.pesquisa.criterio_inexistente");
			}else{
				CobrancaCriterio cobrancaCriterio = (CobrancaCriterio) colecaoCobrancaCriterio.iterator().next();

				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setCobrancaCriterio(cobrancaCriterio.getId().toString());

				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeCobrancaCriterio(cobrancaCriterio
								.getDescricaoCobrancaCriterio());
			}
			if(clientes == null || clientes.isEmpty()){
				// if(inscricaoTipo != null && inscricaoTipo.equals("superior")){
				httpServletRequest.setAttribute("codigoClienteSuperiorNaoEncontrado", "true");
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeClienteSuperior("");
				httpServletRequest.setAttribute("nomeCampo", "codigoClienteSuperior");
				// }else{
				httpServletRequest.setAttribute("codigoClienteNaoEncontrado", "true");
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeCliente("");
				httpServletRequest.setAttribute("nomeCampo", "idCliente");
				// }
			}else{
				// O cliente foi encontrado
				// if(inscricaoTipo != null && inscricaoTipo.equals("superior")){
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setCodigoClienteSuperior(((Cliente) ((List) clientes).get(0))
								.getId().toString());
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeClienteSuperior(((Cliente) ((List) clientes).get(0))
								.getNome());
				// }else{
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setIdCliente(((Cliente) ((List) clientes).get(0)).getId()
								.toString());
				inserirComandoAcaoCobrancaEventualCriterioRotaActionForm.setNomeCliente(((Cliente) ((List) clientes).get(0)).getNome());
				// }
				Cliente cliente = new Cliente();
				cliente = (Cliente) clientes.iterator().next();
				sessao.setAttribute("clienteObj", cliente);
			}
		}
	}

}
