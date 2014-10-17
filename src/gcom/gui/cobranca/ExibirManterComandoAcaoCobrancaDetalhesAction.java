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
 * GSANPCG
 * Eduardo Henrique
 * Virgínia Melo
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
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.*;
import gcom.cobranca.programacobranca.FiltroProgramaCobranca;
import gcom.cobranca.programacobranca.ProgramaCobranca;
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
 * [UC0244] Manter Comando de Ação de Conbrança Visualiza os Dados do Comando
 * Ação Cobrançan para atualizar
 * 
 * @author Rafael Santos
 * @since 24/03/2006
 * @author eduardo henrique
 * @date 01/09/2008
 *       Alterações no [UC0244] para a v0.04
 * @author Virginia Melo
 * @date 10/11/2008
 *       Alterações no [UC0244] para a v0.06
 * @author Virgínia Melo
 * @date 06/08/2009
 *       Recuperar dados de valorLimiteEmissao
 *       Não utilizar mais campos de referencia da conta.
 */
public class ExibirManterComandoAcaoCobrancaDetalhesAction
				extends GcomAction {

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
		ActionForward retorno = actionMapping.findForward("exibirManterComandoAcaoCobrancaDetalhes");

		// Mudar isso quando implementar a parte de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		String validarCriterio = (String) httpServletRequest.getParameter("validarCriterio");

		String validar = (String) httpServletRequest.getParameter("validar");

		String objetoConsulta = (String) httpServletRequest.getParameter("objetoConsulta");

		String inscricaoTipo = (String) httpServletRequest.getParameter("inscricaoTipo");

		String idCobrancaAcaoAtividadeComando = null;
		// String cobrancaAcaoAtividadeComandoPesquisado = (String)
		// httpServletRequest.getParameter("cobrancaAcaoAtividadeComandoPesquisado");
		sessao.removeAttribute("exibirTituloComandoPrecedente");

		ManterComandoAcaoCobrancaDetalhesActionForm manterComandoAcaoCobrancaDetalhesActionForm = (ManterComandoAcaoCobrancaDetalhesActionForm) actionForm;

		String idCobrancaAtividae = manterComandoAcaoCobrancaDetalhesActionForm.getCobrancaAtividade();

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

		String cobrancaAcaoEscolhido = manterComandoAcaoCobrancaDetalhesActionForm.getCobrancaAcao();
		if(validarCriterio != null && validarCriterio.equals("SIM")){

			FiltroCobrancaAcao filtroCobrancaAcaoPrazoExecucao = new FiltroCobrancaAcao();

			if(cobrancaAcaoEscolhido != null){

				filtroCobrancaAcaoPrazoExecucao.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.ID, cobrancaAcaoEscolhido));

				Collection<CobrancaAcao> colecaoCobrancaAcaoPrazoExecucao = fachada.pesquisar(filtroCobrancaAcaoPrazoExecucao,
								CobrancaAcao.class.getName());
				CobrancaAcao cobrancaAcaoPrazoExecucao = (CobrancaAcao) Util.retonarObjetoDeColecao(colecaoCobrancaAcaoPrazoExecucao);

				if(cobrancaAcaoPrazoExecucao != null && cobrancaAcaoPrazoExecucao.getQtdDiasRealizacao() != null){
					manterComandoAcaoCobrancaDetalhesActionForm.setPrazoExecucao(cobrancaAcaoPrazoExecucao.getQtdDiasRealizacao()
									.toString());
				}else{
					manterComandoAcaoCobrancaDetalhesActionForm.setPrazoExecucao("");
				}
			}
		}

		if(httpServletRequest.getParameter("tipoConsulta") != null && !httpServletRequest.getParameter("tipoConsulta").equals("")
						&& httpServletRequest.getParameter("tipoConsulta").equals("cobrancaAcaoAtividadeComando")
						&& httpServletRequest.getParameter("idCampoEnviarDados") != null
						&& !httpServletRequest.getParameter("idCampoEnviarDados").equals("")){
			idCobrancaAcaoAtividadeComando = (String) httpServletRequest.getParameter("idCampoEnviarDados");
			sessao.setAttribute("cobrancaAcaoAtividadeComandoPrecedente", httpServletRequest.getParameter("idCampoEnviarDados"));

		}else if(httpServletRequest.getParameter("idCobrancaAcaoAtividadeComando") != null
						&& !httpServletRequest.getParameter("idCobrancaAcaoAtividadeComando").equals("")){
			idCobrancaAcaoAtividadeComando = (String) httpServletRequest.getParameter("idCobrancaAcaoAtividadeComando");
			sessao.setAttribute("cobrancaAcaoAtividadeComando", httpServletRequest.getParameter("idCobrancaAcaoAtividadeComando"));
		}

		// consultar o cobranca ação atividade comando selecionada
		CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando = fachada
						.consultarCobrancaAcaoAtividadeComando(idCobrancaAcaoAtividadeComando);

		if(cobrancaAcaoAtividadeComando != null && !cobrancaAcaoAtividadeComando.equals("")){
			if(httpServletRequest.getParameter("tipoConsulta") != null && !httpServletRequest.getParameter("tipoConsulta").equals("")
							&& httpServletRequest.getParameter("tipoConsulta").equals("cobrancaAcaoAtividadeComando")){

				// remove o parâmetro do botão Selecionar Comando Precedente
				sessao.removeAttribute("caminhoRetornoTelaPesquisaAcaoCobranca");

				// verifica se o comando de ação precedente selecionado está de acordo com a
				// ação
				// precedente
				// selecionada
				if(cobrancaAcao != null){
					if(((cobrancaAcao.getCobrancaAcaoPredecessora() == null || cobrancaAcaoAtividadeComando.getCobrancaAcao() == null) || !cobrancaAcao
									.getCobrancaAcaoPredecessora().getId().equals(cobrancaAcaoAtividadeComando.getCobrancaAcao().getId()))){
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

				// verifica se o comando de ação precedente selecionado já está associado a
				// outro
				// comando
				FiltroCobrancaAcaoAtividadeComando filtroCobrancaAcaoAtividadeComandoAux = new FiltroCobrancaAcaoAtividadeComando();
				filtroCobrancaAcaoAtividadeComandoAux.adicionarParametro(new ParametroSimples(
								FiltroCobrancaAcaoAtividadeComando.PRECEDENTE, idCobrancaAcaoAtividadeComando));

				Collection colecaoCobrancaAcaoAtividadeComandoAux = fachada.pesquisar(filtroCobrancaAcaoAtividadeComandoAux,
								CobrancaAcaoAtividadeComando.class.getName());

				if(colecaoCobrancaAcaoAtividadeComandoAux != null && !colecaoCobrancaAcaoAtividadeComandoAux.isEmpty()){
					throw new ActionServletException("atencao.comando.precedente.ja.esta.associado.a.outro.comando");
				}

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

					manterComandoAcaoCobrancaDetalhesActionForm.setTituloComandoPrecedente(titulo);
					manterComandoAcaoCobrancaDetalhesActionForm.setTituloCompletoComandoPrecedente(cobrancaAcaoAtividadeComando
									.getDescricaoTitulo());
				}

				// ação cobrança
				if(cobrancaAcao != null){
					manterComandoAcaoCobrancaDetalhesActionForm.setCobrancaAcao(cobrancaAcao.getId().toString());

				}

				ManterComandoAcaoCobrancaDetalhesActionForm.DESABILITARPARAMETROSADICIONAIS = "true";

			}else{
				manterComandoAcaoCobrancaDetalhesActionForm.setCobrancaAcao(cobrancaAcaoAtividadeComando.getCobrancaAcao().getId()
								.toString());
				cobrancaAcaoEscolhido = cobrancaAcaoAtividadeComando.getCobrancaAcao().getId().toString();
				manterComandoAcaoCobrancaDetalhesActionForm.setCobrancaAtividade(cobrancaAcaoAtividadeComando.getCobrancaAtividade()
								.getId().toString());

				// arrecadador
				if(cobrancaAcaoAtividadeComando.getArrecadador() != null){
					manterComandoAcaoCobrancaDetalhesActionForm.setIdClienteCombo(cobrancaAcaoAtividadeComando.getArrecadador()
									.getCliente().getId().toString());
				}

				if(cobrancaAcaoAtividadeComando.getArquivoImoveis() != null){
					manterComandoAcaoCobrancaDetalhesActionForm.setIdentificadorInformacaoArquivo(ConstantesSistema.SIM.toString());
				}else{
					manterComandoAcaoCobrancaDetalhesActionForm.setIdentificadorInformacaoArquivo(ConstantesSistema.NAO.toString());
				}

				// empresa
				if(cobrancaAcaoAtividadeComando.getEmpresa() != null){
					manterComandoAcaoCobrancaDetalhesActionForm.setIdEmpresa(cobrancaAcaoAtividadeComando.getEmpresa().getId().toString());
				}else{
					manterComandoAcaoCobrancaDetalhesActionForm.setIdEmpresa("");
				}

				if(cobrancaAcaoAtividadeComando.getQuantidadeDiasRealizacao() != null){
					manterComandoAcaoCobrancaDetalhesActionForm.setPrazoExecucao(""
									+ cobrancaAcaoAtividadeComando.getQuantidadeDiasRealizacao());

				}

				if(cobrancaAcaoAtividadeComando.getQuantidadeMaximaDocumentos() != null){
					manterComandoAcaoCobrancaDetalhesActionForm.setQuantidadeMaximaDocumentos(""
									+ cobrancaAcaoAtividadeComando.getQuantidadeMaximaDocumentos());
				}

				if(cobrancaAcaoAtividadeComando.getIndicadorBoletim() != null){
					manterComandoAcaoCobrancaDetalhesActionForm.setIndicadorGerarBoletimCadastro(""
									+ cobrancaAcaoAtividadeComando.getIndicadorBoletim());
				}
				if(cobrancaAcaoAtividadeComando.getIndicadorDebito() != null){
					manterComandoAcaoCobrancaDetalhesActionForm.setIndicadorImoveisDebito(""
									+ cobrancaAcaoAtividadeComando.getIndicadorDebito());
				}

				if(cobrancaAcaoAtividadeComando.getRealizacao() != null){
					manterComandoAcaoCobrancaDetalhesActionForm.setDataRealizacao("" + cobrancaAcaoAtividadeComando.getRealizacao());
				}else{
					manterComandoAcaoCobrancaDetalhesActionForm.setDataRealizacao(null);
					ManterComandoAcaoCobrancaDetalhesActionForm.DESABILITARPARAMETROSADICIONAIS = "false";
				}

				if(cobrancaAcaoAtividadeComando.getDescricaoTitulo() != null){
					manterComandoAcaoCobrancaDetalhesActionForm.setTitulo(cobrancaAcaoAtividadeComando.getDescricaoTitulo());
				}

				manterComandoAcaoCobrancaDetalhesActionForm.setTituloComandoPrecedente("");
				manterComandoAcaoCobrancaDetalhesActionForm.setTituloCompletoComandoPrecedente("");

				if(cobrancaAcaoAtividadeComando.getDescricaoSolicitacao() != null){
					manterComandoAcaoCobrancaDetalhesActionForm.setDescricaoSolicitacao(cobrancaAcaoAtividadeComando
									.getDescricaoSolicitacao());
				}

			}
		}else{
			ManterComandoAcaoCobrancaDetalhesActionForm.DESABILITARPARAMETROSADICIONAIS = "false";
		}


		// Carregando empresas
		Collection colecaoEmpresa = null;

		// caso a Ação de Cobrança selecionada seja “COBRANÇA ADMINISTRATIVA”,
		// exibir as empresas de cobrança administrativa
		if(cobrancaAcaoEscolhido != null){
			if(cobrancaAcaoEscolhido.equals(ConstantesSistema.COBRANCA_ADMINISTRATIVA)){
				colecaoEmpresa = fachada.consultarEmpresaCobrancaAdministrativa();
				httpServletRequest.setAttribute("isCobrancaAdministrativa", true);
			}
		}

		if(cobrancaAcaoEscolhido != null){
			if(Integer.valueOf(cobrancaAcaoEscolhido) == CobrancaAcao.COBRANCA_BANCARIA.intValue()){
				// Arrecadador
				FiltroArrecadadorContrato filtroArrecadadorContrato = new FiltroArrecadadorContrato();

				filtroArrecadadorContrato.adicionarParametro(new ParametroSimples(FiltroArrecadadorContrato.ARRECADADOR_INDICADOR_USO,
								ConstantesSistema.SIM));
				filtroArrecadadorContrato
								.adicionarParametro(new ParametroNaoNulo(FiltroArrecadadorContrato.CODIGO_CONVENIO_BOLETO_BANCARIO));
				filtroArrecadadorContrato.adicionarParametro(new ParametroNulo(FiltroArrecadadorContrato.DATA_CONTRATO_ENCERRAMENTO));

				// Ordena filtro de arrecadador por id do cliente
				filtroArrecadadorContrato.setCampoOrderBy(FiltroArrecadador.CLIENTE_ID);
				// Inclui a obejeto de cliente no filtro de arrecadador
				filtroArrecadadorContrato.adicionarCaminhoParaCarregamentoEntidade("arrecadador.cliente");
				// Preenche colecao de arrecadador
				Collection<ArrecadadorContrato> colecaoArrecadadorContrato = fachada.pesquisar(filtroArrecadadorContrato,
								ArrecadadorContrato.class.getName());
				if(colecaoArrecadadorContrato == null || colecaoArrecadadorContrato.isEmpty()){
					throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Arrecadador");
				}else{
					FiltroCliente filtroCliente = new FiltroCliente();
					Iterator iteratorColecaoArrecadador = colecaoArrecadadorContrato.iterator();
					Cliente cliente = new Cliente();
					while(iteratorColecaoArrecadador.hasNext()){
						ArrecadadorContrato arrecadadorContrato = (ArrecadadorContrato) iteratorColecaoArrecadador.next();
						cliente = arrecadadorContrato.getArrecadador().getCliente();
						filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, cliente.getId(),
										ParametroSimples.CONECTOR_OR));
					}
					Collection colecaoClienteArrecadador = fachada.pesquisar(filtroCliente, Cliente.class.getName());
					sessao.setAttribute("colecaoClienteArrecadador", colecaoClienteArrecadador);
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
		}else{
			sessao.setAttribute("colecaoEmpresa", colecaoEmpresa);
		}


		// verificação do critério cobrança
		if(manterComandoAcaoCobrancaDetalhesActionForm.getCobrancaCriterio() != null
						&& !manterComandoAcaoCobrancaDetalhesActionForm.getCobrancaCriterio().trim().equalsIgnoreCase("")){

			FiltroCobrancaCriterio filtroCobrancaCriterio = new FiltroCobrancaCriterio();
			filtroCobrancaCriterio.adicionarParametro(new ParametroSimples(FiltroCobrancaCriterio.ID, Integer
							.valueOf(manterComandoAcaoCobrancaDetalhesActionForm.getCobrancaCriterio())));

			Collection<CobrancaCriterio> colecaoCobrancaCriterio = fachada.pesquisar(filtroCobrancaCriterio, CobrancaCriterio.class
							.getName());

			if(Util.isVazioOrNulo(colecaoCobrancaCriterio)){

				httpServletRequest.setAttribute("corCobrancaCriterio", "exception");

				manterComandoAcaoCobrancaDetalhesActionForm.setCobrancaCriterio("");

				manterComandoAcaoCobrancaDetalhesActionForm.setNomeCobrancaCriterio("CRITERIO DE COBRANCA INEXISTENTE");

				httpServletRequest.setAttribute("nomeCampo", "cobrancaCriterio");

			}else{
				CobrancaCriterio cobrancaCriterio = (CobrancaCriterio) Util.retonarObjetoDeColecao(colecaoCobrancaCriterio);

				manterComandoAcaoCobrancaDetalhesActionForm.setCobrancaCriterio(cobrancaCriterio.getId().toString());

				manterComandoAcaoCobrancaDetalhesActionForm.setNomeCobrancaCriterio(cobrancaCriterio.getDescricaoCobrancaCriterio());

			}
		}

		// carregar os dados na tela da cobrança ação atividade comando
		if(cobrancaAcaoAtividadeComando != null){

			// cobranca grupo
			if(cobrancaAcaoAtividadeComando.getCobrancaGrupo() != null){
				manterComandoAcaoCobrancaDetalhesActionForm.setCobrancaGrupo(cobrancaAcaoAtividadeComando.getCobrancaGrupo().getId()
								.toString());
			}else{
				manterComandoAcaoCobrancaDetalhesActionForm.setCobrancaGrupo("");
			}
			// gerencia regional
			if(cobrancaAcaoAtividadeComando.getGerenciaRegional() != null){
				manterComandoAcaoCobrancaDetalhesActionForm.setGerenciaRegional(cobrancaAcaoAtividadeComando.getGerenciaRegional().getId()
								.toString());
			}else{
				manterComandoAcaoCobrancaDetalhesActionForm.setGerenciaRegional("");
			}
			// unidade Negocio
			if(cobrancaAcaoAtividadeComando.getUnidadeNegocio() != null){
				manterComandoAcaoCobrancaDetalhesActionForm.setUnidadeNegocio(cobrancaAcaoAtividadeComando.getUnidadeNegocio().getId()
								.toString());
			}else{
				manterComandoAcaoCobrancaDetalhesActionForm.setUnidadeNegocio("");
			}

			// localidade inicial
			if(cobrancaAcaoAtividadeComando.getLocalidadeInicial() != null){
				manterComandoAcaoCobrancaDetalhesActionForm.setLocalidadeOrigemID(cobrancaAcaoAtividadeComando.getLocalidadeInicial()
								.getId().toString());
				FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, cobrancaAcaoAtividadeComando
								.getLocalidadeInicial().getId()));
				Collection colecaoLocalidadesIniciais = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

				Localidade localidadeInicial = (Localidade) colecaoLocalidadesIniciais.iterator().next();
				manterComandoAcaoCobrancaDetalhesActionForm.setNomeLocalidadeOrigem(localidadeInicial.getDescricao());
			}else{
				manterComandoAcaoCobrancaDetalhesActionForm.setLocalidadeOrigemID("");
				manterComandoAcaoCobrancaDetalhesActionForm.setNomeLocalidadeOrigem("");
			}
			// localidade final
			if(cobrancaAcaoAtividadeComando.getLocalidadeFinal() != null){
				manterComandoAcaoCobrancaDetalhesActionForm.setLocalidadeDestinoID(cobrancaAcaoAtividadeComando.getLocalidadeFinal()
								.getId().toString());
				FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, cobrancaAcaoAtividadeComando
								.getLocalidadeFinal().getId()));
				Collection colecaoLocalidadesFinais = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

				Localidade localidadeFinal = (Localidade) colecaoLocalidadesFinais.iterator().next();
				manterComandoAcaoCobrancaDetalhesActionForm.setNomeLocalidadeDestino(localidadeFinal.getDescricao());
			}else{
				manterComandoAcaoCobrancaDetalhesActionForm.setLocalidadeDestinoID("");
				manterComandoAcaoCobrancaDetalhesActionForm.setNomeLocalidadeDestino("");
			}
			// setor comercial inicial
			if(cobrancaAcaoAtividadeComando.getCodigoSetorComercialInicial() != null){
				manterComandoAcaoCobrancaDetalhesActionForm.setSetorComercialOrigemCD(cobrancaAcaoAtividadeComando
								.getCodigoSetorComercialInicial().toString());
				FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE,
								cobrancaAcaoAtividadeComando.getLocalidadeInicial().getId()));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
								cobrancaAcaoAtividadeComando.getCodigoSetorComercialInicial().toString()));
				Collection colecaoSetorComercialIniciais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				SetorComercial setorComercialInicial = (SetorComercial) colecaoSetorComercialIniciais.iterator().next();
				manterComandoAcaoCobrancaDetalhesActionForm.setNomeSetorComercialOrigem(setorComercialInicial.getDescricao());
			}else{
				manterComandoAcaoCobrancaDetalhesActionForm.setSetorComercialOrigemCD("");
				manterComandoAcaoCobrancaDetalhesActionForm.setNomeSetorComercialOrigem("");
			}
			// setor comercial final
			if(cobrancaAcaoAtividadeComando.getCodigoSetorComercialFinal() != null){
				manterComandoAcaoCobrancaDetalhesActionForm.setSetorComercialDestinoCD(cobrancaAcaoAtividadeComando
								.getCodigoSetorComercialFinal().toString());
				FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
								cobrancaAcaoAtividadeComando.getCodigoSetorComercialFinal().toString()));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE,
								cobrancaAcaoAtividadeComando.getLocalidadeFinal().getId()));
				Collection colecaoSetorComercialFinais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				SetorComercial setorComercialFinal = (SetorComercial) colecaoSetorComercialFinais.iterator().next();
				manterComandoAcaoCobrancaDetalhesActionForm.setNomeSetorComercialDestino(setorComercialFinal.getDescricao());
			}else{
				manterComandoAcaoCobrancaDetalhesActionForm.setSetorComercialDestinoCD("");
				manterComandoAcaoCobrancaDetalhesActionForm.setNomeSetorComercialDestino("");
			}

			// quadra inicial
			if(cobrancaAcaoAtividadeComando.getNumeroQuadraInicial() == null){
				manterComandoAcaoCobrancaDetalhesActionForm.setQuadraInicial("");
			}else{
				manterComandoAcaoCobrancaDetalhesActionForm.setQuadraInicial(cobrancaAcaoAtividadeComando.getNumeroQuadraInicial()
								.toString());
			}

			// quadra final
			if(cobrancaAcaoAtividadeComando.getNumeroQuadraFinal() == null){
				manterComandoAcaoCobrancaDetalhesActionForm.setQuadraFinal("");
			}else{
				manterComandoAcaoCobrancaDetalhesActionForm.setQuadraFinal(cobrancaAcaoAtividadeComando.getNumeroQuadraFinal()
								.toString());
			}

			boolean carregou = false;
			// rota inicial
			if(cobrancaAcaoAtividadeComando.getRotaInicial() != null){
				// carregarRota(manterComandoAcaoCobrancaDetalhesActionForm,fachada,cobrancaAcaoAtividadeComando.getCodigoSetorComercialInicial().toString());
				manterComandoAcaoCobrancaDetalhesActionForm.setRotaInicial(cobrancaAcaoAtividadeComando.getRotaInicial().getCodigo()
								.toString());
				carregou = true;
			}else{
				// sessao.setAttribute("colecaoRota", null);
				manterComandoAcaoCobrancaDetalhesActionForm.setRotaInicial(null);
			}
			// rota final
			if(cobrancaAcaoAtividadeComando.getRotaFinal() != null){

				manterComandoAcaoCobrancaDetalhesActionForm
								.setRotaFinal(cobrancaAcaoAtividadeComando.getRotaFinal().getCodigo().toString());
			}else{
				// sessao.setAttribute("colecaoRota", null);
				manterComandoAcaoCobrancaDetalhesActionForm.setRotaFinal(null);
			}
			// cliente
			if(cobrancaAcaoAtividadeComando.getCliente() != null){
				manterComandoAcaoCobrancaDetalhesActionForm.setIdCliente(cobrancaAcaoAtividadeComando.getCliente().getId().toString());
				FiltroCliente filtroCliente = new FiltroCliente();
				filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, cobrancaAcaoAtividadeComando.getCliente().getId()
								.toString()));
				Collection colecaoCliente = fachada.pesquisar(filtroCliente, Cliente.class.getName());
				Cliente cliente = (Cliente) colecaoCliente.iterator().next();
				manterComandoAcaoCobrancaDetalhesActionForm.setNomeCliente(cliente.getNome());

			}else{
				manterComandoAcaoCobrancaDetalhesActionForm.setNomeCliente("");
				manterComandoAcaoCobrancaDetalhesActionForm.setIdCliente("");
			}

			// cliente superior
			if(cobrancaAcaoAtividadeComando.getSuperior() != null){
				manterComandoAcaoCobrancaDetalhesActionForm.setCodigoClienteSuperior(cobrancaAcaoAtividadeComando.getSuperior().getId()
								.toString());
				FiltroCliente filtroCliente = new FiltroCliente();
				filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, cobrancaAcaoAtividadeComando.getSuperior().getId()
								.toString()));
				Collection colecaoCliente = fachada.pesquisar(filtroCliente, Cliente.class.getName());
				Cliente cliente = (Cliente) colecaoCliente.iterator().next();
				manterComandoAcaoCobrancaDetalhesActionForm.setNomeClienteSuperior(cliente.getNome());

			}else{
				manterComandoAcaoCobrancaDetalhesActionForm.setNomeClienteSuperior("");
				manterComandoAcaoCobrancaDetalhesActionForm.setCodigoClienteSuperior("");
			}

			// cliente relacao tipo
			if(cobrancaAcaoAtividadeComando.getClienteRelacaoTipo() != null){
				manterComandoAcaoCobrancaDetalhesActionForm.setClienteRelacaoTipo(cobrancaAcaoAtividadeComando.getClienteRelacaoTipo()
								.getId().toString());
			}else{
				manterComandoAcaoCobrancaDetalhesActionForm.setClienteRelacaoTipo("");
			}
			// ano mes conta inicial
			/*
			 * if (cobrancaAcaoAtividadeComando.getAnoMesReferenciaContaInicial() != null) {
			 * manterComandoAcaoCobrancaDetalhesActionForm
			 * .setPeriodoInicialConta(Util
			 * .formatarAnoMesParaMesAno(Util
			 * .adicionarZerosEsquedaNumero(
			 * 6,
			 * cobrancaAcaoAtividadeComando
			 * .getAnoMesReferenciaContaInicial()
			 * .toString())
			 * + ""));
			 * } else {
			 * manterComandoAcaoCobrancaDetalhesActionForm
			 * .setPeriodoInicialConta("");
			 * }
			 * // ano mes conta final
			 * if (cobrancaAcaoAtividadeComando.getAnoMesReferenciaContaFinal() != null) {
			 * manterComandoAcaoCobrancaDetalhesActionForm
			 * .setPeriodoFinalConta(Util
			 * .formatarAnoMesParaMesAno(Util
			 * .adicionarZerosEsquedaNumero(
			 * 6,
			 * cobrancaAcaoAtividadeComando
			 * .getAnoMesReferenciaContaFinal()
			 * .toString())
			 * + ""));
			 * } else {
			 * if (cobrancaAcaoAtividadeComandoPesquisado != null
			 * && cobrancaAcaoAtividadeComandoPesquisado
			 * .equals("true")) {
			 * manterComandoAcaoCobrancaDetalhesActionForm
			 * .setPeriodoFinalConta("");
			 * } else {
			 * // caso não esteja preenchido pelo registro, é preenchido
			 * // com o
			 * // dado do sistema
			 * manterComandoAcaoCobrancaDetalhesActionForm
			 * .setPeriodoFinalConta(fachada
			 * .consultarPeriodoFinalContaCobrancaAcaoAtividadeComando());
			 * }
			 * }
			 * // data vencimento conta inicial
			 * if (cobrancaAcaoAtividadeComando.getDataVencimentoContaInicial() != null) {
			 * manterComandoAcaoCobrancaDetalhesActionForm
			 * .setPeriodoVencimentoContaInicial(Util
			 * .formatarData(cobrancaAcaoAtividadeComando
			 * .getDataVencimentoContaInicial()));
			 * } else {
			 * if (cobrancaAcaoAtividadeComandoPesquisado != null
			 * && cobrancaAcaoAtividadeComandoPesquisado
			 * .equals("true")) {
			 * manterComandoAcaoCobrancaDetalhesActionForm
			 * .setPeriodoVencimentoContaFinal("");
			 * } else {
			 * // caso não esteja preenchido pelo registro, é preenchido
			 * // com o
			 * // dado do sistema
			 * manterComandoAcaoCobrancaDetalhesActionForm
			 * .setPeriodoVencimentoContaFinal(fachada
			 * .consultarPeriodoVencimentoContaFinalCobrancaAcaoAtividadeComando());
			 * }
			 * }
			 * // data vencimento conta final
			 * if (cobrancaAcaoAtividadeComando.getDataVencimentoContaFinal() != null) {
			 * manterComandoAcaoCobrancaDetalhesActionForm
			 * .setPeriodoVencimentoContaFinal(Util
			 * .formatarData(cobrancaAcaoAtividadeComando
			 * .getDataVencimentoContaFinal()));
			 * } else {
			 * manterComandoAcaoCobrancaDetalhesActionForm
			 * .setPeriodoVencimentoContaFinal("");
			 * }
			 */

			// indicador criterio cobranca
			if(cobrancaAcaoAtividadeComando.getIndicadorCriterio() != null){
				if(cobrancaAcaoAtividadeComando.getIndicadorCriterio().equals(ConstantesSistema.SIM)){
					manterComandoAcaoCobrancaDetalhesActionForm.setIndicadorCriterioComando("1");
				}else{
					manterComandoAcaoCobrancaDetalhesActionForm.setIndicadorCriterioComando("2");
				}
			}

			if(cobrancaAcaoAtividadeComando.getCobrancaCriterio() != null){
				manterComandoAcaoCobrancaDetalhesActionForm.setCobrancaCriterio(cobrancaAcaoAtividadeComando.getCobrancaCriterio().getId()
								.toString());
				manterComandoAcaoCobrancaDetalhesActionForm.setNomeCobrancaCriterio(cobrancaAcaoAtividadeComando.getCobrancaCriterio()
								.getDescricaoCobrancaCriterio());
				// sessao.setAttribute("cobrancaAcaoAtividadeComando",
				// cobrancaAcaoAtividadeComando);
			}
		}

		// indicador de criterio
		if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getIndicadorCriterio() != null){
			if(cobrancaAcaoAtividadeComando.getIndicadorCriterio().shortValue() == 1){
				manterComandoAcaoCobrancaDetalhesActionForm.setCobrancaAtividadeIndicadorExecucao("1");
				// manterComandoAcaoCobrancaDetalhesActionForm.setIndicador("Rota");
			}else if(cobrancaAcaoAtividadeComando.getIndicadorCriterio().shortValue() == 2){
				manterComandoAcaoCobrancaDetalhesActionForm.setCobrancaAtividadeIndicadorExecucao("2");
				// manterComandoAcaoCobrancaDetalhesActionForm.setIndicador("Comando");
			}
		}

		if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getIndicadorGerarRelacaoDocumento() != null){
			if(cobrancaAcaoAtividadeComando.getIndicadorGerarRelacaoDocumento().shortValue() == 1){
				manterComandoAcaoCobrancaDetalhesActionForm.setIndicadorGerarRelacaoDocumento("1");
			}else if(cobrancaAcaoAtividadeComando.getIndicadorGerarRelacaoDocumento().shortValue() == 2){
				manterComandoAcaoCobrancaDetalhesActionForm.setIndicadorGerarRelacaoDocumento("2");
			}
		}

		if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getFormatoArquivo() != null){
			manterComandoAcaoCobrancaDetalhesActionForm.setFormatoArquivo(cobrancaAcaoAtividadeComando.getFormatoArquivo().toString());
		}else{
			manterComandoAcaoCobrancaDetalhesActionForm.setFormatoArquivo(Integer.toString(TarefaRelatorio.TIPO_PDF));
		}

		String idComandoSelecionado = httpServletRequest.getParameter("idComandoSelecionado");
		if(idComandoSelecionado != null){
			if(cobrancaAcaoAtividadeComando.getCobrancaCriterio() != null){
				cobrancaAcaoAtividadeComando.getCobrancaCriterio().setId(Integer.valueOf(idComandoSelecionado));
				// sessao.setAttribute("cobrancaAcaoAtividadeComando",
				// cobrancaAcaoAtividadeComando);
			}
		}

		// valdiar os criteorios de rota e comando para o usuário selecionar
		if(validarCriterio != null && !validarCriterio.equals("")){
			// validar a atividade selecionada
			if(validar != null && validar.equals("Atividade")){
				if(idCobrancaAtividae != null && !idCobrancaAtividae.equals("")){

					CobrancaAtividade cobrancaAtividade = fachada.obterCobrancaAtividade(idCobrancaAtividae);

					if(cobrancaAtividade != null){
						if(cobrancaAtividade.getIndicadorExecucao() != null){
							manterComandoAcaoCobrancaDetalhesActionForm.setCobrancaAtividadeIndicadorExecucao(cobrancaAtividade
											.getIndicadorExecucao().toString());
						}else{
							manterComandoAcaoCobrancaDetalhesActionForm.setCobrancaAtividadeIndicadorExecucao("");
						}
					}else{
						manterComandoAcaoCobrancaDetalhesActionForm.setCobrancaAtividadeIndicadorExecucao("");
					}
				}
			}
		}

		// CARREGAR AS COBRANÇAS GRUPO
		if(sessao.getAttribute("colecaoCobrancaGrupo") == null){
			sessao.setAttribute("colecaoCobrancaGrupo", fachada.obterColecaoCobrancaGrupo());
		}

		// CARREGAR AS COBRANÇAS ATIVIDADE
		if(sessao.getAttribute("colecaoCobrancaAtividade") == null){
			sessao.setAttribute("colecaoCobrancaAtividade", fachada.obterColecaoCobrancaAtividade());
		}

		// CARREGAR AS COBRANÇAS ACAO
		if(sessao.getAttribute("colecaoCobrancaAcao") == null){
			sessao.setAttribute("colecaoCobrancaAcao", fachada.obterColecaoCobrancaAcao());
		}

		// CARREGAR AS GERENCIAIS REGIONAIS
		if(sessao.getAttribute("colecaoGerenciaRegional") == null){
			sessao.setAttribute("colecaoGerenciaRegional", fachada.obterColecaoGerenciaRegional());
		}

		// CARREGAR AS UNIDADES NEGOCIOS
		if(sessao.getAttribute("colecaoUnidadeNegocio") == null){
			sessao.setAttribute("colecaoUnidadeNegocio", fachada.obterColecaoUnidadeNegocio());
		}

		// CARREGAR OS CLIENTE RELACAO TIPO
		if(sessao.getAttribute("colecaoClienteRelacaoTipo") == null){
			sessao.setAttribute("colecaoClienteRelacaoTipo", fachada.obterColecaoClienteRelacaoTipo());
		}

		if(objetoConsulta != null && !objetoConsulta.trim().equalsIgnoreCase("") && inscricaoTipo != null
						&& !inscricaoTipo.trim().equalsIgnoreCase("")){

			switch(Integer.parseInt(objetoConsulta)){
				// Localidade
				case 1:

					pesquisarLocalidade(inscricaoTipo, manterComandoAcaoCobrancaDetalhesActionForm, fachada, httpServletRequest);

					break;
				// Setor Comercial
				case 2:

					pesquisarLocalidade(inscricaoTipo, manterComandoAcaoCobrancaDetalhesActionForm, fachada, httpServletRequest);

					pesquisarSetorComercial(inscricaoTipo, manterComandoAcaoCobrancaDetalhesActionForm, fachada, httpServletRequest);

					break;
				case 3:
					pesquisarCliente(inscricaoTipo, manterComandoAcaoCobrancaDetalhesActionForm, fachada, httpServletRequest, sessao);
					break;

				default:
					break;
			}
		}

		// if(cobrancaAcaoAtividadeComando != null){
		// sessao.setAttribute("cobrancaAcaoAtividadeComando", cobrancaAcaoAtividadeComando);
		// }

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
					ManterComandoAcaoCobrancaDetalhesActionForm manterComandoAcaoCobrancaDetalhesActionForm, Fachada fachada,
					HttpServletRequest httpServletRequest){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

		if(inscricaoTipo.equalsIgnoreCase("origem")){
			manterComandoAcaoCobrancaDetalhesActionForm.setInscricaoTipo("origem");
			// Recebe o valor do campo localidadeOrigemID do formulário.
			String localidadeID = (String) manterComandoAcaoCobrancaDetalhesActionForm.getLocalidadeOrigemID();

			if(localidadeID != null && !localidadeID.equals("")){
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeID));

				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna localidade
				Collection colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					// Localidade nao encontrada
					// Limpa os campos localidadeOrigemID e nomeLocalidadeOrigem do
					// formulário
					manterComandoAcaoCobrancaDetalhesActionForm.setLocalidadeOrigemID("");
					manterComandoAcaoCobrancaDetalhesActionForm.setNomeLocalidadeOrigem("Localidade Inexistente");
					httpServletRequest.setAttribute("corLocalidadeOrigem", "exception");
				}else{
					Localidade objetoLocalidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);
					manterComandoAcaoCobrancaDetalhesActionForm.setLocalidadeOrigemID(String.valueOf(objetoLocalidade.getId()));
					manterComandoAcaoCobrancaDetalhesActionForm.setNomeLocalidadeOrigem(objetoLocalidade.getDescricao());
					httpServletRequest.setAttribute("corLocalidadeOrigem", "valor");

					String localidadeDestinoID = (String) manterComandoAcaoCobrancaDetalhesActionForm.getLocalidadeDestinoID();
					// verifica o valor das localidades, origem e final
					if(localidadeDestinoID != null){

						if(localidadeDestinoID.equals("")){
							manterComandoAcaoCobrancaDetalhesActionForm.setLocalidadeDestinoID(String.valueOf(objetoLocalidade.getId()));
							manterComandoAcaoCobrancaDetalhesActionForm.setNomeLocalidadeDestino(objetoLocalidade.getDescricao());
						}else{
							int localidadeDestino = Integer.valueOf(localidadeDestinoID).intValue();
							int localidadeOrigem = objetoLocalidade.getId().intValue();
							// if(localidadeOrigem > localidadeDestino){
							manterComandoAcaoCobrancaDetalhesActionForm.setLocalidadeDestinoID(String.valueOf(objetoLocalidade.getId()));
							manterComandoAcaoCobrancaDetalhesActionForm.setNomeLocalidadeDestino(objetoLocalidade.getDescricao());
							// }
						}
					}
				}
			}

		}else{
			// Recebe o valor do campo localidadeDestinoID do formulário.
			String localidadeID = (String) manterComandoAcaoCobrancaDetalhesActionForm.getLocalidadeDestinoID();

			if(localidadeID != null && !localidadeID.equals("")){
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeID));

				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna localidade
				Collection colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

				manterComandoAcaoCobrancaDetalhesActionForm.setInscricaoTipo("destino");

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					// Localidade nao encontrada
					// Limpa os campos localidadeDestinoID e nomeLocalidadeDestino
					// do formulário
					manterComandoAcaoCobrancaDetalhesActionForm.setLocalidadeDestinoID("");
					manterComandoAcaoCobrancaDetalhesActionForm.setNomeLocalidadeDestino("Localidade inexistente");
					httpServletRequest.setAttribute("corLocalidadeDestino", "exception");
				}else{
					Localidade objetoLocalidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);

					int localidadeDestino = objetoLocalidade.getId().intValue();

					String localidade = (String) manterComandoAcaoCobrancaDetalhesActionForm.getLocalidadeOrigemID();

					if(localidade != null && !localidade.equals("")){

						int localidadeOrigem = Integer.valueOf(localidade).intValue();
						if(localidadeDestino < localidadeOrigem){
							manterComandoAcaoCobrancaDetalhesActionForm.setLocalidadeDestinoID("");
							manterComandoAcaoCobrancaDetalhesActionForm.setNomeLocalidadeDestino("Loc. Final maior que a Inicial");
							httpServletRequest.setAttribute("corLocalidadeDestino", "exception");

						}else{
							manterComandoAcaoCobrancaDetalhesActionForm.setLocalidadeDestinoID(String.valueOf(objetoLocalidade.getId()));
							manterComandoAcaoCobrancaDetalhesActionForm.setNomeLocalidadeDestino(objetoLocalidade.getDescricao());
							httpServletRequest.setAttribute("corLocalidadeDestino", "valor");
						}
					}else{
						manterComandoAcaoCobrancaDetalhesActionForm.setLocalidadeDestinoID(String.valueOf(objetoLocalidade.getId()));
						manterComandoAcaoCobrancaDetalhesActionForm.setNomeLocalidadeDestino(objetoLocalidade.getDescricao());
						httpServletRequest.setAttribute("corLocalidadeDestino", "valor");
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
					ManterComandoAcaoCobrancaDetalhesActionForm manterComandoAcaoCobrancaDetalhesActionForm, Fachada fachada,
					HttpServletRequest httpServletRequest){

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

		if(inscricaoTipo.equalsIgnoreCase("origem")){
			manterComandoAcaoCobrancaDetalhesActionForm.setInscricaoTipo("origem");
			// Recebe o valor do campo localidadeOrigemID do formulário.
			String localidadeID = (String) manterComandoAcaoCobrancaDetalhesActionForm.getLocalidadeOrigemID();

			// O campo localidadeOrigemID será obrigatório
			if(localidadeID != null && !localidadeID.trim().equalsIgnoreCase("")){

				String setorComercialCD = (String) manterComandoAcaoCobrancaDetalhesActionForm.getSetorComercialOrigemCD();

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
				Collection colecaoPesquisa = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					// Setor Comercial nao encontrado
					// Limpa os campos setorComercialOrigemCD,
					// nomeSetorComercialOrigem e setorComercialOrigemID do
					// formulário
					manterComandoAcaoCobrancaDetalhesActionForm.setSetorComercialOrigemCD("");
					manterComandoAcaoCobrancaDetalhesActionForm.setSetorComercialOrigemID("");
					manterComandoAcaoCobrancaDetalhesActionForm.setNomeSetorComercialOrigem("Setor inexistente");
					httpServletRequest.setAttribute("corSetorComercialOrigem", "exception");
					manterComandoAcaoCobrancaDetalhesActionForm.setRotaInicial(null);
					manterComandoAcaoCobrancaDetalhesActionForm.setRotaFinal(null);

				}else{
					SetorComercial objetoSetorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoPesquisa);
					// setorComercialID =
					// objetoSetorComercial.getId().toString();
					// setorComercialOrigem
					manterComandoAcaoCobrancaDetalhesActionForm.setSetorComercialOrigemCD(String.valueOf(objetoSetorComercial.getCodigo()));
					manterComandoAcaoCobrancaDetalhesActionForm.setSetorComercialOrigemID(String.valueOf(objetoSetorComercial.getId()));
					manterComandoAcaoCobrancaDetalhesActionForm.setNomeSetorComercialOrigem(objetoSetorComercial.getDescricao());
					httpServletRequest.setAttribute("corSetorComercialOrigem", "valor");

					String setorComercialDestinoCD = (String) manterComandoAcaoCobrancaDetalhesActionForm.getSetorComercialDestinoCD();

					// verifica o valor dos setores comerciais, origem e final
					if(setorComercialDestinoCD != null){

						if(setorComercialDestinoCD.equals("")){

							// setorComercialDestino
							manterComandoAcaoCobrancaDetalhesActionForm.setSetorComercialDestinoCD(String.valueOf(objetoSetorComercial
											.getCodigo()));
							manterComandoAcaoCobrancaDetalhesActionForm.setSetorComercialDestinoID(String.valueOf(objetoSetorComercial
											.getId()));
							manterComandoAcaoCobrancaDetalhesActionForm.setNomeSetorComercialDestino(objetoSetorComercial.getDescricao());

							Collection colecaRotas = fachada.obterColecaoRota(objetoSetorComercial.getId().toString());

							sessao.setAttribute("colecaoRota", colecaRotas);
						}else{

							int setorDestino = Integer.valueOf(setorComercialDestinoCD).intValue();
							int setorOrigem = objetoSetorComercial.getCodigo();
							if(setorOrigem > setorDestino){

								// setorComercialDestino
								manterComandoAcaoCobrancaDetalhesActionForm.setSetorComercialDestinoCD(String.valueOf(objetoSetorComercial
												.getCodigo()));
								manterComandoAcaoCobrancaDetalhesActionForm.setSetorComercialDestinoID(String.valueOf(objetoSetorComercial
												.getId()));
								manterComandoAcaoCobrancaDetalhesActionForm.setNomeSetorComercialDestino(objetoSetorComercial
												.getDescricao());

								Collection colecaRotas = fachada.obterColecaoRota(objetoSetorComercial.getId().toString());

								sessao.setAttribute("colecaoRota", colecaRotas);
							}
						}
					}
				}
			}else{
				// Limpa o campo setorComercialOrigemCD do formulário
				manterComandoAcaoCobrancaDetalhesActionForm.setSetorComercialOrigemCD("");
				manterComandoAcaoCobrancaDetalhesActionForm.setNomeSetorComercialOrigem("Informe a localidade da inscrição de origem.");
				httpServletRequest.setAttribute("corSetorComercialOrigem", "exception");
			}
		}else{

			manterComandoAcaoCobrancaDetalhesActionForm.setInscricaoTipo("destino");

			// Recebe o valor do campo localidadeDestinoID do formulário.
			String localidadeID = (String) manterComandoAcaoCobrancaDetalhesActionForm.getLocalidadeDestinoID();

			// O campo localidadeOrigem será obrigatório
			if(localidadeID != null && !localidadeID.trim().equalsIgnoreCase("")){

				String setorComercialCD = (String) manterComandoAcaoCobrancaDetalhesActionForm.getSetorComercialDestinoCD();

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
				Collection colecaoPesquisa = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					// Setor Comercial nao encontrado
					// Limpa os campos setorComercialDestinoCD,
					// nomeSetorComercialDestino e setorComercialDestinoID do
					// formulário
					manterComandoAcaoCobrancaDetalhesActionForm.setSetorComercialDestinoCD("");
					manterComandoAcaoCobrancaDetalhesActionForm.setSetorComercialDestinoID("");
					manterComandoAcaoCobrancaDetalhesActionForm.setNomeSetorComercialDestino("Setor comercial inexistente.");
					httpServletRequest.setAttribute("corSetorComercialDestino", "exception");
					manterComandoAcaoCobrancaDetalhesActionForm.setRotaInicial(null);
					manterComandoAcaoCobrancaDetalhesActionForm.setRotaFinal(null);

				}else{
					SetorComercial objetoSetorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoPesquisa);

					int setorDestino = objetoSetorComercial.getCodigo();

					String setor = (String) manterComandoAcaoCobrancaDetalhesActionForm.getSetorComercialOrigemCD();

					if(setor != null && !setor.equals("")){

						int setorOrigem = Integer.valueOf(setor).intValue();
						if(setorDestino < setorOrigem){

							manterComandoAcaoCobrancaDetalhesActionForm.setSetorComercialDestinoCD("");
							manterComandoAcaoCobrancaDetalhesActionForm.setSetorComercialDestinoID("");
							manterComandoAcaoCobrancaDetalhesActionForm.setNomeSetorComercialDestino("Setor Final maior que Inicial");
							httpServletRequest.setAttribute("corSetorComercialDestino", "exception");
							manterComandoAcaoCobrancaDetalhesActionForm.setRotaInicial(null);
							manterComandoAcaoCobrancaDetalhesActionForm.setRotaFinal(null);

						}else{
							// rota
							Collection colecaRotas = fachada.obterColecaoRota(objetoSetorComercial.getId().toString());

							sessao.setAttribute("colecaoRota", colecaRotas);
							manterComandoAcaoCobrancaDetalhesActionForm.setRotaInicial("rota");
							manterComandoAcaoCobrancaDetalhesActionForm.setRotaFinal("rota");

							// setor comercial destino
							manterComandoAcaoCobrancaDetalhesActionForm.setSetorComercialDestinoCD(String.valueOf(objetoSetorComercial
											.getCodigo()));
							manterComandoAcaoCobrancaDetalhesActionForm.setSetorComercialDestinoID(String.valueOf(objetoSetorComercial
											.getId()));
							manterComandoAcaoCobrancaDetalhesActionForm.setNomeSetorComercialDestino(objetoSetorComercial.getDescricao());
							httpServletRequest.setAttribute("corSetorComercialDestino", "valor");
						}
					}else{

						Collection colecaRotas = fachada.obterColecaoRota(objetoSetorComercial.getId().toString());

						sessao.setAttribute("colecaoRota", colecaRotas);
						manterComandoAcaoCobrancaDetalhesActionForm.setRotaInicial("rota");
						manterComandoAcaoCobrancaDetalhesActionForm.setRotaFinal("rota");

						// setor comercial destino
						manterComandoAcaoCobrancaDetalhesActionForm.setSetorComercialDestinoCD(String.valueOf(objetoSetorComercial
										.getCodigo()));
						manterComandoAcaoCobrancaDetalhesActionForm
										.setSetorComercialDestinoID(String.valueOf(objetoSetorComercial.getId()));
						manterComandoAcaoCobrancaDetalhesActionForm.setNomeSetorComercialDestino(objetoSetorComercial.getDescricao());
						httpServletRequest.setAttribute("corSetorComercialDestino", "valor");
					}
				}
			}else{
				// Limpa o campo setorComercialDestinoCD do formulário
				manterComandoAcaoCobrancaDetalhesActionForm.setSetorComercialDestinoCD("");
				manterComandoAcaoCobrancaDetalhesActionForm.setNomeSetorComercialDestino("Informe a localidade da inscrição de destino.");
				httpServletRequest.setAttribute("corSetorComercialDestino", "exception");
			}
		}

	}

	/**
	 * Inicializa a Rota
	 * 
	 * @param fachada
	 * @param sessao
	 *            TODO
	 * @param inserirComandoAcaoCobrancaEventualCriterioRotaActionForm
	 * @param objetoSetorComercial
	 */
	private void carregarRota(ManterComandoAcaoCobrancaDetalhesActionForm manterComandoAcaoCobrancaDetalhesActionForm, Fachada fachada,
					String codigoSetorComercial, HttpSession sessao){

		FiltroRota filtroRota = new FiltroRota();
		filtroRota.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_CODIGO, codigoSetorComercial));
		Collection colecaoRota = (Collection) fachada.pesquisar(filtroRota, Rota.class.getName());
		sessao.setAttribute("colecaoRota", colecaoRota);
		manterComandoAcaoCobrancaDetalhesActionForm.setRotaInicial("rota");
		manterComandoAcaoCobrancaDetalhesActionForm.setRotaFinal("rota");

	}

	/**
	 * pesquisarProgramaCobranca
	 */
	private void pesquisarProgramaCobranca(ManterComandoAcaoCobrancaDetalhesActionForm manterComandoAcaoCobrancaDetalhesActionForm,
					Fachada fachada, HttpServletRequest httpServletRequest){

		if(manterComandoAcaoCobrancaDetalhesActionForm.getIdPrograma() != null
						&& !manterComandoAcaoCobrancaDetalhesActionForm.getIdPrograma().trim().equals("")){

			FiltroProgramaCobranca filtroProgramaCobranca = new FiltroProgramaCobranca();
			filtroProgramaCobranca.adicionarParametro(new ParametroSimples(FiltroProgramaCobranca.ID,
							manterComandoAcaoCobrancaDetalhesActionForm.getIdPrograma()));

			Collection colecaoProgramasCobranca = (Collection) fachada.pesquisar(filtroProgramaCobranca, ProgramaCobranca.class.getName());

			// [FS0019] - Verificar Programa Selcionado
			if(colecaoProgramasCobranca != null && !colecaoProgramasCobranca.isEmpty()){

				Iterator iteratorColecaoProgramaCobranca = colecaoProgramasCobranca.iterator();
				while(iteratorColecaoProgramaCobranca.hasNext()){
					ProgramaCobranca programaCobranca = (ProgramaCobranca) iteratorColecaoProgramaCobranca.next();

					if(programaCobranca == null || programaCobranca.getDataEncerramento() != null){
						throw new ActionServletException("atencao.programa_cobranca_encerrado");
					}
					manterComandoAcaoCobrancaDetalhesActionForm.setIdPrograma(programaCobranca.getId().toString());
					manterComandoAcaoCobrancaDetalhesActionForm.setDescricaoPrograma(programaCobranca.getNome());
				}
			}else{
				httpServletRequest.setAttribute("idProgramaNaoEncontrado", "exception");
				manterComandoAcaoCobrancaDetalhesActionForm.setDescricaoPrograma("PROGRAMA INEXISTENTE");
			}

		}
	}

	/**
	 * PesquisarAcaoCobranca
	 */
	private void pesquisarAcaoCobranca(ManterComandoAcaoCobrancaDetalhesActionForm manterComandoAcaoCobrancaDetalhesActionForm,
					Fachada fachada, HttpServletRequest httpServletRequest){

		if(manterComandoAcaoCobrancaDetalhesActionForm.getIdAcao() != null
						&& !manterComandoAcaoCobrancaDetalhesActionForm.getIdAcao().trim().equals("")){

			FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();
			filtroCobrancaAcao.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.ID, manterComandoAcaoCobrancaDetalhesActionForm
							.getIdAcao()));

			Collection colecaoCobrancasAcao = (Collection) fachada.pesquisar(filtroCobrancaAcao, CobrancaAcao.class.getName());

			// [FS0019] - Verificar Programa Selecionado
			if(colecaoCobrancasAcao == null || colecaoCobrancasAcao.isEmpty()){
				httpServletRequest.setAttribute("idAcaoNaoEncontrado", "exception");
				manterComandoAcaoCobrancaDetalhesActionForm.setDescricaoAcao("AÇÃO DE COBRANÇA INEXISTENTE");
			}else{
				for(Iterator iterator = colecaoCobrancasAcao.iterator(); iterator.hasNext();){
					CobrancaAcao cobrancaAcao = (CobrancaAcao) iterator.next();
					manterComandoAcaoCobrancaDetalhesActionForm.setIdAcao(cobrancaAcao.getId().toString());
					manterComandoAcaoCobrancaDetalhesActionForm.setDescricaoAcao(cobrancaAcao.getDescricaoCobrancaAcao());
				}
			}
		}
	}

	private void pesquisarCliente(String inscricaoTipo,
					ManterComandoAcaoCobrancaDetalhesActionForm manterComandoAcaoCobrancaDetalhesActionForm, Fachada fachada,
					HttpServletRequest httpServletRequest, HttpSession sessao){

		String idCliente = null;
		if(inscricaoTipo != null && inscricaoTipo.equals("superior")){
			idCliente = manterComandoAcaoCobrancaDetalhesActionForm.getCodigoClienteSuperior();
		}else{
			idCliente = manterComandoAcaoCobrancaDetalhesActionForm.getIdCliente();
		}

		// -------Parte que trata do código quando o usuário tecla enter
		// se o id do cliente for diferente de nulo
		if(idCliente != null && !idCliente.toString().trim().equalsIgnoreCase("")){

			FiltroCliente filtroCliente = new FiltroCliente();
			Collection clientes = null;
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, Integer.valueOf(idCliente)));
			clientes = fachada.pesquisar(filtroCliente, Cliente.class.getName());
			if(clientes != null && !clientes.isEmpty()){
				// O cliente foi encontrado
				if(inscricaoTipo != null && inscricaoTipo.equals("superior")){
					manterComandoAcaoCobrancaDetalhesActionForm.setCodigoClienteSuperior(((Cliente) ((List) clientes).get(0)).getId()
									.toString());
					manterComandoAcaoCobrancaDetalhesActionForm.setNomeClienteSuperior(((Cliente) ((List) clientes).get(0)).getNome());
				}else{
					manterComandoAcaoCobrancaDetalhesActionForm.setIdCliente(((Cliente) ((List) clientes).get(0)).getId().toString());
					manterComandoAcaoCobrancaDetalhesActionForm.setNomeCliente(((Cliente) ((List) clientes).get(0)).getNome());
				}

				Cliente cliente = new Cliente();

				cliente = (Cliente) clientes.iterator().next();
				sessao.setAttribute("clienteObj", cliente);
			}else{
				if(inscricaoTipo != null && inscricaoTipo.equals("superior")){
					httpServletRequest.setAttribute("codigoClienteSuperiorNaoEncontrado", "true");
					manterComandoAcaoCobrancaDetalhesActionForm.setNomeClienteSuperior("");
					httpServletRequest.setAttribute("nomeCampo", "codigoClienteSuperior");
				}else{
					httpServletRequest.setAttribute("codigoClienteNaoEncontrado", "true");
					manterComandoAcaoCobrancaDetalhesActionForm.setNomeCliente("");
					httpServletRequest.setAttribute("nomeCampo", "idCliente");
				}
			}
		}
	}
}
