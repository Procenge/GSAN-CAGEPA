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
 * Ivan S�rgio da Silva J�nior
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

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.registroatendimento.*;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteRelacaoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel pela pre-exibi��o
 * 
 * @author S�vio Luiz
 * @created 28 de Julho de 2006
 */
public class ExibirAdicionarSolicitacaoEspecificacaoAction
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

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Set no mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("adicionarSolicitacaoEspecificacao");

		String consultarUltimaAlteracao = httpServletRequest.getParameter("ultimaAlteracao");

		// Obt�m a inst�ncia da Fachada
		Fachada fachada = Fachada.getInstancia();

		AdicionarSolicitacaoEspecificacaoActionForm adicionarSolicitacaoEspecificacaoActionForm = (AdicionarSolicitacaoEspecificacaoActionForm) actionForm;

		if(!Util.isVazioOuBranco((String) httpServletRequest.getAttribute("limparCampoAnterior"))
						&& "true".equalsIgnoreCase(httpServletRequest.getAttribute("limparCampoAnterior").toString())){
			httpServletRequest.setAttribute("limparCampoAnterior", "");
			adicionarSolicitacaoEspecificacaoActionForm.setIdSolicitacaoTipoEspecificacaoMensagem("");
			adicionarSolicitacaoEspecificacaoActionForm.setDescricaoSolicitacaoTipoEspecificacaoMensagem("");
		}

		if(!Util.isVazioOuBranco(consultarUltimaAlteracao)){
			long ultimaAlteracaoTime = Long.parseLong(consultarUltimaAlteracao);
			Collection colecaoSolicitacaoTipoEspecificacao = (Collection) sessao.getAttribute("colecaoSolicitacaoTipoEspecificacao");
			Iterator iteEspecificacaoServicoTipo = colecaoSolicitacaoTipoEspecificacao.iterator();
			while(iteEspecificacaoServicoTipo.hasNext()){
				SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) iteEspecificacaoServicoTipo
								.next();
				if(solicitacaoTipoEspecificacao.getUltimaAlteracao().getTime() == ultimaAlteracaoTime){
					// recupera os dados do objeto da cole��o
					adicionarSolicitacaoEspecificacaoActionForm.setDescricaoSolicitacao(solicitacaoTipoEspecificacao.getDescricao());
					adicionarSolicitacaoEspecificacaoActionForm.setPrazoPrevisaoAtendimento(solicitacaoTipoEspecificacao.getHorasPrazo()
									.toString());
					adicionarSolicitacaoEspecificacaoActionForm.setIndicadorPavimentoCalcada(String.valueOf(solicitacaoTipoEspecificacao
									.getIndicadorPavimentoCalcada()));
					adicionarSolicitacaoEspecificacaoActionForm.setIndicadorLigacaoAgua(solicitacaoTipoEspecificacao
									.getIndicadorLigacaoAgua().toString());
					adicionarSolicitacaoEspecificacaoActionForm.setIndicadorPavimentoRua(String.valueOf(solicitacaoTipoEspecificacao
									.getIndicadorPavimentoRua()));
					adicionarSolicitacaoEspecificacaoActionForm.setIndicadorCobrancaMaterial(solicitacaoTipoEspecificacao
									.getIndicadorCobrancaMaterial().toString());
					adicionarSolicitacaoEspecificacaoActionForm.setIndicadorParecerEncerramento(solicitacaoTipoEspecificacao
									.getIndicadorParecerEncerramento().toString());
					adicionarSolicitacaoEspecificacaoActionForm.setIndicadorGerarDebito(solicitacaoTipoEspecificacao
									.getIndicadorGeracaoCredito().toString());
					adicionarSolicitacaoEspecificacaoActionForm.setIndicadorGerarCredito(solicitacaoTipoEspecificacao
									.getIndicadorGeracaoCredito().toString());
					adicionarSolicitacaoEspecificacaoActionForm.setIndicadorCliente(solicitacaoTipoEspecificacao.getIndicadorCliente()
									.toString());
					adicionarSolicitacaoEspecificacaoActionForm.setIndicadorVerificarDebito(solicitacaoTipoEspecificacao
									.getIndicadorVerificarDebito().toString());

					if(solicitacaoTipoEspecificacao.getIndicadorTipoVerificarDebito() != null){

						adicionarSolicitacaoEspecificacaoActionForm.setIndicadorTipoVerificarDebito(solicitacaoTipoEspecificacao
										.getIndicadorTipoVerificarDebito().toString());
					}

					if(solicitacaoTipoEspecificacao.getIndicadorConsiderarApenasDebitoTitularAtual() != null){

						adicionarSolicitacaoEspecificacaoActionForm
										.setIndicadorConsiderarApenasDebitoTitularAtual(solicitacaoTipoEspecificacao
														.getIndicadorConsiderarApenasDebitoTitularAtual().toString());
					}else{

						adicionarSolicitacaoEspecificacaoActionForm.setIndicadorConsiderarApenasDebitoTitularAtual(ConstantesSistema.NAO
										.toString());
					}

					if(solicitacaoTipoEspecificacao.getClienteRelacaoTipo() != null){

						adicionarSolicitacaoEspecificacaoActionForm.setIdClienteRelacaoTipo(solicitacaoTipoEspecificacao
										.getClienteRelacaoTipo().getId().toString());
					}else{

						adicionarSolicitacaoEspecificacaoActionForm.setIdClienteRelacaoTipo(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING);
					}

					adicionarSolicitacaoEspecificacaoActionForm.setIndicadorCalculoDataPrevistaAtendimento(solicitacaoTipoEspecificacao
									.getIndicadorCalculoDataPrevistaAtendimento().toString());
					adicionarSolicitacaoEspecificacaoActionForm.setIndicadorMatriculaImovel(solicitacaoTipoEspecificacao
									.getIndicadorMatricula().toString());

					if(!Util.isVazioOuBranco(solicitacaoTipoEspecificacao.getEspecificacaoImovelSituacao())){
						adicionarSolicitacaoEspecificacaoActionForm.setIdSituacaoImovel(solicitacaoTipoEspecificacao
										.getEspecificacaoImovelSituacao().getId().toString());
					}

					adicionarSolicitacaoEspecificacaoActionForm.setIndicadorGeraOrdemServico(String.valueOf(solicitacaoTipoEspecificacao
									.getIndicadorGeracaoOrdemServico()));

					if(solicitacaoTipoEspecificacao.getSolicitacaoTipoEspecificacaoMensagem() != null){
						adicionarSolicitacaoEspecificacaoActionForm.setIdSolicitacaoTipoEspecificacaoMensagem(String
										.valueOf(solicitacaoTipoEspecificacao.getSolicitacaoTipoEspecificacaoMensagem().getId()));
						adicionarSolicitacaoEspecificacaoActionForm
										.setDescricaoSolicitacaoTipoEspecificacaoMensagem(solicitacaoTipoEspecificacao
														.getSolicitacaoTipoEspecificacaoMensagem().getDescricao());
					}

					adicionarSolicitacaoEspecificacaoActionForm.setIndicadorObrigatoriedadeRgRA(solicitacaoTipoEspecificacao
									.getIndicadorObrigatoriedadeRgRA().toString());
					adicionarSolicitacaoEspecificacaoActionForm.setIndicadorObrigatoriedadeCpfCnpjRA(solicitacaoTipoEspecificacao
									.getIndicadorObrigatoriedadeCpfCnpjRA().toString());

					if(!Util.isVazioOuBranco(solicitacaoTipoEspecificacao.getIndicadorContaEmRevisao())){
						adicionarSolicitacaoEspecificacaoActionForm.setIndicadorContaEmRevisao(solicitacaoTipoEspecificacao
										.getIndicadorContaEmRevisao().toString());
					}

					if(solicitacaoTipoEspecificacao.getIndicadorMensagemAlertaRevisao() != null){
						adicionarSolicitacaoEspecificacaoActionForm.setIndicadorMensagemAlertaRevisao(solicitacaoTipoEspecificacao
										.getIndicadorMensagemAlertaRevisao().toString());
					}

					httpServletRequest.setAttribute("colecaoEspecificacaoServicoTipo", solicitacaoTipoEspecificacao
									.getEspecificacaoServicoTipos());

					FiltroEspecificacaoImovelSituacao filtroEspecificacaoImovelSituacao = new FiltroEspecificacaoImovelSituacao();
					Collection colecaoImovelSituacao = fachada.pesquisar(filtroEspecificacaoImovelSituacao,
									EspecificacaoImovelSituacao.class.getName());
					httpServletRequest.setAttribute("colecaoImovelSituacao", colecaoImovelSituacao);
					httpServletRequest.setAttribute("consultaDados", "SIM");
					if(httpServletRequest.getParameter("atualizar") != null){
						httpServletRequest.removeAttribute("consultaDados");
						sessao.setAttribute("atualizar", true);
					}

				}
			}
		}else{

			// caso exista o parametro ent�o limpa a sess�o e o form
			if(!Util.isVazioOuBranco(httpServletRequest.getParameter("limpaSessao"))){

				adicionarSolicitacaoEspecificacaoActionForm.setDescricaoSolicitacao("");
				adicionarSolicitacaoEspecificacaoActionForm.setPrazoPrevisaoAtendimento("");
				adicionarSolicitacaoEspecificacaoActionForm.setIndicadorPavimentoCalcada("");
				adicionarSolicitacaoEspecificacaoActionForm.setIndicadorLigacaoAgua("");
				adicionarSolicitacaoEspecificacaoActionForm.setIndicadorPavimentoRua("");
				adicionarSolicitacaoEspecificacaoActionForm.setIndicadorCobrancaMaterial("");
				adicionarSolicitacaoEspecificacaoActionForm.setIndicadorParecerEncerramento("");
				adicionarSolicitacaoEspecificacaoActionForm.setIndicadorGerarDebito("");
				adicionarSolicitacaoEspecificacaoActionForm.setIndicadorGerarCredito("");
				adicionarSolicitacaoEspecificacaoActionForm.setIndicadorCliente("");
				adicionarSolicitacaoEspecificacaoActionForm.setIndicadorVerificarDebito("");
				adicionarSolicitacaoEspecificacaoActionForm.setIndicadorTipoVerificarDebito("");
				adicionarSolicitacaoEspecificacaoActionForm.setIndicadorConsiderarApenasDebitoTitularAtual("");
				adicionarSolicitacaoEspecificacaoActionForm.setIdClienteRelacaoTipo(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING);
				adicionarSolicitacaoEspecificacaoActionForm.setIndicadorMatriculaImovel("");
				adicionarSolicitacaoEspecificacaoActionForm.setIdSituacaoImovel("");
				adicionarSolicitacaoEspecificacaoActionForm.setIndicadorGeraOrdemServico("");
				adicionarSolicitacaoEspecificacaoActionForm.setIdServicoOS("");
				adicionarSolicitacaoEspecificacaoActionForm.setDescricaoServicoOS("");
				adicionarSolicitacaoEspecificacaoActionForm.setIndicadorObrigatoriedadeRgRA("");
				adicionarSolicitacaoEspecificacaoActionForm.setIndicadorObrigatoriedadeCpfCnpjRA("");
				adicionarSolicitacaoEspecificacaoActionForm.setIndicadorContaEmRevisao("");
				adicionarSolicitacaoEspecificacaoActionForm.setIndicadorMensagemAlertaRevisao("");
				adicionarSolicitacaoEspecificacaoActionForm.setIndicadorColetor("");

				sessao.removeAttribute("colecaoEspecificacaoServicoTipo");

				// ********** limpar dados referentes ao campo de Mensagens Autom�ticas **********
				httpServletRequest.setAttribute("limparCampoAnterior", "");
				adicionarSolicitacaoEspecificacaoActionForm.setIdSolicitacaoTipoEspecificacaoMensagem("");
				adicionarSolicitacaoEspecificacaoActionForm.setDescricaoSolicitacaoTipoEspecificacaoMensagem("");
				// ******************************************************************************
			}

			// Verifica se o tipoConsulta � diferente de nulo ou vazio.Nesse
			// caso �
			// quando um o retorno da consulta vem para o action ao inves de
			// ir
			// direto para o jsp
			if(!Util.isVazioOuBranco(httpServletRequest.getParameter("tipoConsulta"))){
				// verifica se retornou da pesquisa de unidade de tramite
				if(httpServletRequest.getParameter("tipoConsulta").equals("unidadeOrganizacional")){

					adicionarSolicitacaoEspecificacaoActionForm.setIdUnidadeTramitacao(httpServletRequest
									.getParameter("idCampoEnviarDados"));

					adicionarSolicitacaoEspecificacaoActionForm.setDescricaoUnidadeTramitacao(httpServletRequest
									.getParameter("descricaoCampoEnviarDados"));

				}
				// verifica se retornou da pesquisa de tipo de servi�o
				if(httpServletRequest.getParameter("tipoConsulta").equals("tipoServico")){

					adicionarSolicitacaoEspecificacaoActionForm.setIdServicoOS(httpServletRequest.getParameter("idCampoEnviarDados"));

					adicionarSolicitacaoEspecificacaoActionForm.setDescricaoServicoOS(httpServletRequest
									.getParameter("descricaoCampoEnviarDados"));
				}else if(httpServletRequest.getParameter("tipoConsulta").equals("solicitacaoTipoEspecificacaoMensagem")){
					adicionarSolicitacaoEspecificacaoActionForm.setIdSolicitacaoTipoEspecificacaoMensagem(httpServletRequest
									.getParameter("idCampoEnviarDados"));
					adicionarSolicitacaoEspecificacaoActionForm.setDescricaoSolicitacaoTipoEspecificacaoMensagem(httpServletRequest
									.getParameter("descricaoCampoEnviarDados"));
				}
			}

			// -------Parte que trata do c�digo quando o usu�rio tecla enter
			String idTipoServicoOS = (String) adicionarSolicitacaoEspecificacaoActionForm.getIdServicoOS();
			String descricaoServicoOS = adicionarSolicitacaoEspecificacaoActionForm.getDescricaoServicoOS();

			// Verifica se o c�digo foi digitado pela primeira vez ou se foi
			// modificado
			if(!Util.isVazioOuBranco(idTipoServicoOS) && Util.isVazioOuBranco(descricaoServicoOS)){

				// Verifica se o c�digo foi digitado pela primeira vez ou se foi
				// modificado
				if(idTipoServicoOS != null && !idTipoServicoOS.trim().equals("")
								&& (descricaoServicoOS == null || descricaoServicoOS.trim().equals(""))){

					FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();

					filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, idTipoServicoOS));

					Collection servicoTipoEncontrado = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

					if(servicoTipoEncontrado != null && !servicoTipoEncontrado.isEmpty()){

						// [SF0003] - Validar Tipo Servi�o
						fachada.verificarServicoTipoReferencia(Integer.valueOf(idTipoServicoOS));

						adicionarSolicitacaoEspecificacaoActionForm.setIdServicoOS(((ServicoTipo) ((List) servicoTipoEncontrado).get(0))
										.getId().toString());
						adicionarSolicitacaoEspecificacaoActionForm.setDescricaoServicoOS(((ServicoTipo) ((List) servicoTipoEncontrado)
										.get(0)).getDescricao());
						httpServletRequest.setAttribute("idServicoOSNaoEncontrado", "true");

						httpServletRequest.setAttribute("nomeCampo", "adicionarTipoServico");

					}else{

						adicionarSolicitacaoEspecificacaoActionForm.setIdServicoOS("");
						httpServletRequest.setAttribute("nomeCampo", "idServicoOS");
						httpServletRequest.setAttribute("idServicoOSNaoEncontrado", "exception");
						adicionarSolicitacaoEspecificacaoActionForm.setDescricaoServicoOS("Tipo Servi�o Inexistente");

					}
				}

			}

			// Verifica se o c�digo foi digitado pela primeira vez ou se foi
			// modificado
			if(!Util.isVazioOuBranco(adicionarSolicitacaoEspecificacaoActionForm.getIdSolicitacaoTipoEspecificacaoMensagem())){

				FiltroSolicitacaoTipoEspecificacaoMensagem filtroSolicitacaoTipoEspecificacaoMensagem = new FiltroSolicitacaoTipoEspecificacaoMensagem();
				filtroSolicitacaoTipoEspecificacaoMensagem.adicionarParametro(new ParametroSimples(
								FiltroSolicitacaoTipoEspecificacaoMensagem.ID, Integer.valueOf(adicionarSolicitacaoEspecificacaoActionForm
												.getIdSolicitacaoTipoEspecificacaoMensagem())));
				filtroSolicitacaoTipoEspecificacaoMensagem.adicionarParametro(new ParametroSimples(
								FiltroSolicitacaoTipoEspecificacaoMensagem.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

				SolicitacaoTipoEspecificacaoMensagem objetoSolicitacaoTipoEspecificacaoMensagem = (SolicitacaoTipoEspecificacaoMensagem) Util
								.retonarObjetoDeColecao(fachada.pesquisar(filtroSolicitacaoTipoEspecificacaoMensagem,
												SolicitacaoTipoEspecificacaoMensagem.class.getName()));

				if(objetoSolicitacaoTipoEspecificacaoMensagem != null){
					adicionarSolicitacaoEspecificacaoActionForm
									.setDescricaoSolicitacaoTipoEspecificacaoMensagem(objetoSolicitacaoTipoEspecificacaoMensagem
													.getDescricao());
					adicionarSolicitacaoEspecificacaoActionForm.setIdSolicitacaoTipoEspecificacaoMensagem(String
									.valueOf(objetoSolicitacaoTipoEspecificacaoMensagem.getId()));
					httpServletRequest.removeAttribute("idSolicitacaoTipoEspecificacaoMensagemNaoEncontrado");
				}else{
					adicionarSolicitacaoEspecificacaoActionForm.setIdSolicitacaoTipoEspecificacaoMensagem("");
					httpServletRequest.setAttribute("nomeCampo", "idSolicitacaoTipoEspecificacaoMensagem");
					httpServletRequest.setAttribute("idSolicitacaoTipoEspecificacaoMensagemNaoEncontrado", "exception");
					adicionarSolicitacaoEspecificacaoActionForm
									.setDescricaoSolicitacaoTipoEspecificacaoMensagem("Mensagem Autom�tica Padr�o Inexistente");
				}

			}

			if(adicionarSolicitacaoEspecificacaoActionForm.getIndicadorConsiderarApenasDebitoTitularAtual() == null
							|| adicionarSolicitacaoEspecificacaoActionForm.getIndicadorConsiderarApenasDebitoTitularAtual().equals("")){

				adicionarSolicitacaoEspecificacaoActionForm
								.setIndicadorConsiderarApenasDebitoTitularAtual(ConstantesSistema.NAO.toString());
			}

			FiltroClienteRelacaoTipo filtroClienteRelacaoTipo = new FiltroClienteRelacaoTipo();

			filtroClienteRelacaoTipo.adicionarParametro(new ParametroSimples(FiltroClienteRelacaoTipo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroClienteRelacaoTipo.setCampoOrderBy(FiltroClienteRelacaoTipo.DESCRICAO);
			List<ClienteRelacaoTipo> colecaoClienteRelacaoTipo = (List<ClienteRelacaoTipo>) fachada.pesquisar(filtroClienteRelacaoTipo,
							ClienteRelacaoTipo.class.getName());

			if(Util.isVazioOrNulo(colecaoClienteRelacaoTipo)){

				throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Tipo da Rela��o do Cliente");
			}else{

				ClienteRelacaoTipo clienteRelacaoTipo = new ClienteRelacaoTipo("Cliente com Nome na Conta",
								ConstantesSistema.INDICADOR_USO_DESATIVO, new Date());
				clienteRelacaoTipo.setId(ConstantesSistema.NUMERO_NAO_INFORMADO);
				colecaoClienteRelacaoTipo.add(clienteRelacaoTipo);

				// Ordenar a cole��o por mais de um campo
				List sortFields = new ArrayList();
				sortFields.add(new BeanComparator("descricao"));

				ComparatorChain multiSort = new ComparatorChain(sortFields);
				Collections.sort(colecaoClienteRelacaoTipo, multiSort);

				sessao.setAttribute("colecaoClienteRelacaoTipo", colecaoClienteRelacaoTipo);
			}

			FiltroEspecificacaoImovelSituacao filtroEspecificacaoImovelSituacao = new FiltroEspecificacaoImovelSituacao();
			Collection colecaoImovelSituacao = fachada.pesquisar(filtroEspecificacaoImovelSituacao, EspecificacaoImovelSituacao.class
							.getName());
			sessao.setAttribute("colecaoImovelSituacao", colecaoImovelSituacao);

			// -------Fim da Parte que trata do c�digo quando o usu�rio
			// tecla
			// enter

			// remove o retorno da
			// solicita��o_especifica��o_tipo_servico_adicionar_popup.jsp
			sessao.removeAttribute("retornarTelaPopup");
			sessao.removeAttribute("caminhoRetornoTelaPesquisaUnidadeOrganizacional");
			sessao.removeAttribute("caminhoRetornoTelaPesquisaTipoServico");
			sessao.removeAttribute("caminhoRetornoTelaPesquisaMensagemTipoSolicitacaoEspecificacao");
		}

		return retorno;
	}
}
