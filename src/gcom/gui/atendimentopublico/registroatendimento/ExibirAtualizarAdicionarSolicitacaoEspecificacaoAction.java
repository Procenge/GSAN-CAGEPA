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

import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.registroatendimento.*;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteRelacaoTipo;
import gcom.cadastro.sistemaparametro.SistemaParametro;
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
 * Descrição da classe
 * 
 * @author Rômulo Aurélio
 * @date 13/11/2006
 */
public class ExibirAtualizarAdicionarSolicitacaoEspecificacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		// Set no mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("atualizarAdicionarSolicitacaoEspecificacao");

		AtualizarAdicionarSolicitacaoEspecificacaoActionForm atualizarAdicionarSolicitacaoEspecificacaoActionForm = (AtualizarAdicionarSolicitacaoEspecificacaoActionForm) actionForm;

		if(!Util.isVazioOuBranco((String) httpServletRequest.getAttribute("limparCampoAnterior"))
						&& (httpServletRequest.getAttribute("limparCampoAnterior").toString().equalsIgnoreCase("true"))){
			httpServletRequest.setAttribute("limparCampoAnterior", "");
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdSolicitacaoTipoEspecificacaoMensagem("");
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setDescricaoSolicitacaoTipoEspecificacaoMensagem("");
		}

		// Limpando IdSituacaoImovel do form
		atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdSituacaoImovel("");

		//
		// String idSolicitacaoTipo = (String) sessao
		// .getAttribute("idSolicitacaoTipo");

		if(sessao.getAttribute("atualizarAdicionarSolicitacaoEspecificacaoActionForm") != null){

			atualizarAdicionarSolicitacaoEspecificacaoActionForm = (AtualizarAdicionarSolicitacaoEspecificacaoActionForm) sessao
							.getAttribute("atualizarAdicionarSolicitacaoEspecificacaoActionForm");

		}

		Set colecaoEspecificacaoServicoTipo = null;

		if(httpServletRequest.getParameter("posicao") != null){
			String posicao = (String) httpServletRequest.getParameter("posicao");

			sessao.setAttribute("posicao", posicao);
			sessao.setAttribute("posicaoComponente", Integer.valueOf(posicao));

			sessao.removeAttribute("colecaoEspecificacaoServicoTipo");

		}

		Collection colecaoSolicitacaoTipoEspecificacao = (Collection) sessao.getAttribute("colecaoSolicitacaoTipoEspecificacao");

		Fachada fachada = Fachada.getInstancia();

		if(httpServletRequest.getParameter("inserir") != null){
			sessao.removeAttribute("atualizar");
			sessao.removeAttribute("colecaoEspecificacaoServicoTipo");
		}

		if(httpServletRequest.getParameter("atualizar") != null){

			httpServletRequest.setAttribute("atualizar", true);
			sessao.setAttribute("atualizar", true);
		}

		if(sessao.getAttribute("atualizar") != null && httpServletRequest.getParameter("objetoConsulta") == null){

			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setCabecalho("Atualizar");
			httpServletRequest.setAttribute("atualizar", true);

			sessao.removeAttribute("inserir");

			if(colecaoSolicitacaoTipoEspecificacao != null && !colecaoSolicitacaoTipoEspecificacao.isEmpty()){

				Integer posicaoComponente = null;

				if(sessao.getAttribute("posicaoComponente") != null){
					posicaoComponente = (Integer) sessao.getAttribute("posicaoComponente");
				}else{
					posicaoComponente = 0;
				}

				sessao.removeAttribute("posicao");
				sessao.setAttribute("posicaoComponente", posicaoComponente);

				int index = 0;

				Iterator colecaoSolicitacaoTipoEspecificacaoIterator = colecaoSolicitacaoTipoEspecificacao.iterator();

				while(colecaoSolicitacaoTipoEspecificacaoIterator.hasNext()){

					index++;

					SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) colecaoSolicitacaoTipoEspecificacaoIterator
									.next();

					if(index == posicaoComponente){

						if(sessao.getAttribute("atualizarAdicionarSolicitacaoEspecificacaoActionForm") == null){

							sessao.setAttribute("idSolicitacaoEspecificacao", solicitacaoTipoEspecificacao.getId());

							atualizarAdicionarSolicitacaoEspecificacaoActionForm.setDescricaoSolicitacao(solicitacaoTipoEspecificacao
											.getDescricao());
							if(solicitacaoTipoEspecificacao.getHorasPrazo() == null){
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setPrazoPrevisaoAtendimento("");
							}else{
								atualizarAdicionarSolicitacaoEspecificacaoActionForm
												.setPrazoPrevisaoAtendimento(solicitacaoTipoEspecificacao.getHorasPrazo().toString());
							}

							atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorPavimentoCalcada(String
											.valueOf(solicitacaoTipoEspecificacao.getIndicadorPavimentoCalcada()));

							if(solicitacaoTipoEspecificacao.getIndicadorLigacaoAgua() != null){
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorLigacaoAgua(solicitacaoTipoEspecificacao
												.getIndicadorLigacaoAgua().toString());
							}else{
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorLigacaoAgua("");
							}
							atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorPavimentoRua(String
											.valueOf(solicitacaoTipoEspecificacao.getIndicadorPavimentoRua()));
							if(solicitacaoTipoEspecificacao.getIndicadorCobrancaMaterial() != null){
								atualizarAdicionarSolicitacaoEspecificacaoActionForm
												.setIndicadorCobrancaMaterial(solicitacaoTipoEspecificacao.getIndicadorCobrancaMaterial()
																.toString());
							}else{
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorCobrancaMaterial("");
							}
							if(solicitacaoTipoEspecificacao.getIndicadorParecerEncerramento() != null){
								atualizarAdicionarSolicitacaoEspecificacaoActionForm
												.setIndicadorParecerEncerramento(solicitacaoTipoEspecificacao
																.getIndicadorParecerEncerramento().toString());
							}else{
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorParecerEncerramento("");
							}
							if(solicitacaoTipoEspecificacao.getIndicadorGeracaoDebito() != null){
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorGerarDebito(solicitacaoTipoEspecificacao
												.getIndicadorGeracaoDebito().toString());
							}else{
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorGerarDebito("");
							}

							atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorGerarOrdemServico(solicitacaoTipoEspecificacao
											.getIndicadorGeracaoOrdemServico() + "");

							if(solicitacaoTipoEspecificacao.getIndicadorGeracaoCredito() != null){
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorGerarCredito(solicitacaoTipoEspecificacao
												.getIndicadorGeracaoCredito().toString());
							}else{
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorGerarCredito("");
							}
							if(solicitacaoTipoEspecificacao.getIndicadorCliente() != null){
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorCliente(solicitacaoTipoEspecificacao
												.getIndicadorCliente().toString());
							}else{
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorCliente("");
							}

							if(solicitacaoTipoEspecificacao.getIndicadorVerificarDebito() != null){
								atualizarAdicionarSolicitacaoEspecificacaoActionForm
												.setIndicadorVerificarDebito(solicitacaoTipoEspecificacao.getIndicadorVerificarDebito()
																.toString());

								if(solicitacaoTipoEspecificacao.getIndicadorTipoVerificarDebito() != null){

									atualizarAdicionarSolicitacaoEspecificacaoActionForm
													.setIndicadorTipoVerificarDebito(solicitacaoTipoEspecificacao
																	.getIndicadorTipoVerificarDebito().toString());

									if(solicitacaoTipoEspecificacao.getIndicadorTipoVerificarDebito().toString()
													.equals(SolicitacaoTipoEspecificacao.INDICADOR_DEBITOS_IMOVEIS.toString())
													|| solicitacaoTipoEspecificacao
																	.getIndicadorTipoVerificarDebito()
																	.toString()
																	.equals(SolicitacaoTipoEspecificacao.INDICADOR_DEBITOS_AMBOS.toString())){

										if(solicitacaoTipoEspecificacao.getIndicadorConsiderarApenasDebitoTitularAtual() != null){

											atualizarAdicionarSolicitacaoEspecificacaoActionForm
															.setIndicadorConsiderarApenasDebitoTitularAtual(solicitacaoTipoEspecificacao
																			.getIndicadorConsiderarApenasDebitoTitularAtual().toString());
										}else{

											atualizarAdicionarSolicitacaoEspecificacaoActionForm
															.setIndicadorConsiderarApenasDebitoTitularAtual(ConstantesSistema.NAO
																			.toString());
										}

										if(solicitacaoTipoEspecificacao.getClienteRelacaoTipo() != null){

											atualizarAdicionarSolicitacaoEspecificacaoActionForm
															.setIdClienteRelacaoTipo(solicitacaoTipoEspecificacao.getClienteRelacaoTipo()
																			.getId().toString());
										}else{

											atualizarAdicionarSolicitacaoEspecificacaoActionForm
															.setIdClienteRelacaoTipo(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING);
										}
									}else{

										atualizarAdicionarSolicitacaoEspecificacaoActionForm
														.setIndicadorConsiderarApenasDebitoTitularAtual(ConstantesSistema.NAO.toString());
										atualizarAdicionarSolicitacaoEspecificacaoActionForm
														.setIdClienteRelacaoTipo(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING);
									}
								}else{

									atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorTipoVerificarDebito("3");
								}
							}else{

								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorVerificarDebito("");
								atualizarAdicionarSolicitacaoEspecificacaoActionForm
												.setIndicadorConsiderarApenasDebitoTitularAtual(ConstantesSistema.NAO.toString());
								atualizarAdicionarSolicitacaoEspecificacaoActionForm
												.setIdClienteRelacaoTipo(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING);
							}

							if(solicitacaoTipoEspecificacao.getIndicadorMatricula() != null){
								atualizarAdicionarSolicitacaoEspecificacaoActionForm
												.setIndicadorMatriculaImovel(solicitacaoTipoEspecificacao.getIndicadorMatricula()
																.toString());
							}else{
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorMatriculaImovel("");
							}

							if(solicitacaoTipoEspecificacao.getIndicadorColetor() != null){
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorColetor(solicitacaoTipoEspecificacao
												.getIndicadorColetor().toString());
							}else{
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorColetor("");
							}

							if(solicitacaoTipoEspecificacao.getIndicadorObrigatoriedadeRgRA() != null){
								atualizarAdicionarSolicitacaoEspecificacaoActionForm
												.setIndicadorObrigatoriedadeRgRA(solicitacaoTipoEspecificacao
																.getIndicadorObrigatoriedadeRgRA().toString());
							}else{
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorObrigatoriedadeRgRA("");
							}

							if(solicitacaoTipoEspecificacao.getIndicadorObrigatoriedadeCpfCnpjRA() != null){
								atualizarAdicionarSolicitacaoEspecificacaoActionForm
												.setIndicadorObrigatoriedadeCpfCnpjRA(solicitacaoTipoEspecificacao
																.getIndicadorObrigatoriedadeCpfCnpjRA().toString());
							}else{
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorObrigatoriedadeCpfCnpjRA("");
							}

							if(solicitacaoTipoEspecificacao.getIndicadorCalculoDataPrevistaAtendimento() != null){
								atualizarAdicionarSolicitacaoEspecificacaoActionForm
												.setIndicadorCalculoDataPrevistaAtendimento(solicitacaoTipoEspecificacao
																.getIndicadorCalculoDataPrevistaAtendimento().toString());
							}else{
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorCalculoDataPrevistaAtendimento("");
							}

							if(solicitacaoTipoEspecificacao.getEspecificacaoImovelSituacao() != null
											&& !solicitacaoTipoEspecificacao.getEspecificacaoImovelSituacao().equals("")){
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdSituacaoImovel(solicitacaoTipoEspecificacao
												.getEspecificacaoImovelSituacao().getId().toString());
							}

							atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorGeraOrdemServico(""
											+ solicitacaoTipoEspecificacao.getIndicadorGeracaoOrdemServico());

							if(!Util.isVazioOuBranco(solicitacaoTipoEspecificacao.getServicoTipo())){
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdServicoOS(solicitacaoTipoEspecificacao
												.getServicoTipo().getId().toString());
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setDescricaoServicoOS(solicitacaoTipoEspecificacao
												.getServicoTipo().getDescricao());
							}

							if(solicitacaoTipoEspecificacao.getSolicitacaoTipoEspecificacaoMensagem() != null){
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdSolicitacaoTipoEspecificacaoMensagem(String
												.valueOf(solicitacaoTipoEspecificacao.getSolicitacaoTipoEspecificacaoMensagem().getId()));
								atualizarAdicionarSolicitacaoEspecificacaoActionForm
												.setDescricaoSolicitacaoTipoEspecificacaoMensagem(solicitacaoTipoEspecificacao
																.getSolicitacaoTipoEspecificacaoMensagem().getDescricao());
							}

							if(getParametroCompanhia(httpServletRequest) == SistemaParametro.INDICADOR_EMPRESA_DESO.shortValue()){
								if(solicitacaoTipoEspecificacao.getIndicadorContaEmRevisao() != null){
									atualizarAdicionarSolicitacaoEspecificacaoActionForm
													.setIndicadorContaEmRevisao(solicitacaoTipoEspecificacao.getIndicadorContaEmRevisao()
																	.toString());
									if(solicitacaoTipoEspecificacao.getIndicadorMensagemAlertaRevisao() != null){
										atualizarAdicionarSolicitacaoEspecificacaoActionForm
														.setIndicadorMensagemAlertaRevisao(solicitacaoTipoEspecificacao
																		.getIndicadorMensagemAlertaRevisao().toString());
									}
								}
							}

						}

						if(sessao.getAttribute("colecaoEspecificacaoServicoTipo") != null){
							colecaoEspecificacaoServicoTipo = (Set) sessao.getAttribute("colecaoEspecificacaoServicoTipo");
							solicitacaoTipoEspecificacao.setEspecificacaoServicoTipos(colecaoEspecificacaoServicoTipo);
						}

						colecaoEspecificacaoServicoTipo = solicitacaoTipoEspecificacao.getEspecificacaoServicoTipos();

						httpServletRequest.setAttribute("colecaoEspecificacaoServicoTipo", colecaoEspecificacaoServicoTipo);

						sessao.setAttribute("colecaoEspecificacaoServicoTipo", colecaoEspecificacaoServicoTipo);

						FiltroEspecificacaoImovelSituacao filtroEspecificacaoImovelSituacao = new FiltroEspecificacaoImovelSituacao();
						Collection colecaoImovelSituacao = fachada.pesquisar(filtroEspecificacaoImovelSituacao,
										EspecificacaoImovelSituacao.class.getName());
						httpServletRequest.setAttribute("colecaoImovelSituacao", colecaoImovelSituacao);

					}
				}

			}
		}else if(httpServletRequest.getParameter("objetoConsulta") == null){
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setCabecalho("Inserir");

			sessao.removeAttribute("atualizar");

			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = null;

			if(sessao.getAttribute("solicitacaoTipoEspecificacao") == null){

				solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();

			}else{
				solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) sessao.getAttribute("solicitacaoTipoEspecificacao");
			}

			if(sessao.getAttribute("atualizarAdicionarSolicitacaoEspecificacaoActionForm") == null){
				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setDescricaoSolicitacao(solicitacaoTipoEspecificacao.getDescricao());
				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setPrazoPrevisaoAtendimento(""
								+ solicitacaoTipoEspecificacao.getHorasPrazo());

				String indicadorCalculoDataPrevistaAtendimentoStr = "";
				Short indicadorCalculoDataPrevistaAtendimento = solicitacaoTipoEspecificacao.getIndicadorCalculoDataPrevistaAtendimento();

				if(indicadorCalculoDataPrevistaAtendimento != null){
					indicadorCalculoDataPrevistaAtendimentoStr = Short.toString(indicadorCalculoDataPrevistaAtendimento);
				}

				atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.setIndicadorCalculoDataPrevistaAtendimento(indicadorCalculoDataPrevistaAtendimentoStr);

				short indicadorPavimentoCalcada = solicitacaoTipoEspecificacao.getIndicadorPavimentoCalcada();
				atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.setIndicadorPavimentoCalcada(Short.toString(indicadorPavimentoCalcada));

				String indicadorLigacaoAguaStr = "";
				Short indicadorLigacaoAgua = solicitacaoTipoEspecificacao.getIndicadorLigacaoAgua();

				if(indicadorLigacaoAgua != null){
					indicadorLigacaoAguaStr = Short.toString(indicadorLigacaoAgua);
				}

				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorLigacaoAgua(indicadorLigacaoAguaStr);

				short indicadorPavimentoRua = solicitacaoTipoEspecificacao.getIndicadorPavimentoRua();
				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorPavimentoRua(Short.toString(indicadorPavimentoRua));

				String indicadorCobrancaMaterialStr = "";
				Integer indicadorCobrancaMaterial = solicitacaoTipoEspecificacao.getIndicadorCobrancaMaterial();

				if(indicadorCobrancaMaterial != null){
					indicadorCobrancaMaterialStr = Integer.toString(indicadorCobrancaMaterial);
				}

				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorCobrancaMaterial(indicadorCobrancaMaterialStr);

				String indicadorParecerEncerramentoStr = "";
				Integer indicadorParecerEncerramento = solicitacaoTipoEspecificacao.getIndicadorParecerEncerramento();

				if(indicadorParecerEncerramento != null){
					indicadorParecerEncerramentoStr = Integer.toString(indicadorParecerEncerramento);
				}

				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorParecerEncerramento(indicadorParecerEncerramentoStr);

				String indicadorGeracaoDebitoStr = "";
				Integer indicadorGeracaoDebito = solicitacaoTipoEspecificacao.getIndicadorGeracaoDebito();

				if(indicadorGeracaoDebito != null){
					indicadorGeracaoDebitoStr = Integer.toString(indicadorGeracaoDebito);
				}

				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorGerarDebito(indicadorGeracaoDebitoStr);

				String indicadorGeracaoCreditoStr = "";
				Integer indicadorGeracaoCredito = solicitacaoTipoEspecificacao.getIndicadorGeracaoCredito();

				if(indicadorGeracaoCredito != null){
					indicadorGeracaoCreditoStr = Integer.toString(indicadorGeracaoCredito);
				}

				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorGerarCredito(indicadorGeracaoCreditoStr);

				String indicadorClienteStr = "";
				Short indicadorCliente = solicitacaoTipoEspecificacao.getIndicadorCliente();

				if(indicadorCliente != null){
					indicadorClienteStr = Short.toString(indicadorCliente);
				}

				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorCliente(indicadorClienteStr);

				String indicadorVerificarDebitoStr = "";
				Short indicadorVerificarDebito = solicitacaoTipoEspecificacao.getIndicadorVerificarDebito();

				if(indicadorVerificarDebito != null){
					indicadorVerificarDebitoStr = Short.toString(indicadorVerificarDebito);
				}

				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorVerificarDebito(indicadorVerificarDebitoStr);

				String indicadorTipoVerificarDebitoStr = "";
				Short indicadorTipoVerificarDebito = solicitacaoTipoEspecificacao.getIndicadorTipoVerificarDebito();

				if(indicadorTipoVerificarDebito != null){
					indicadorTipoVerificarDebitoStr = Short.toString(indicadorTipoVerificarDebito);
				}

				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorTipoVerificarDebito(indicadorTipoVerificarDebitoStr);

				if(solicitacaoTipoEspecificacao.getIndicadorConsiderarApenasDebitoTitularAtual() != null){

					atualizarAdicionarSolicitacaoEspecificacaoActionForm
									.setIndicadorConsiderarApenasDebitoTitularAtual(solicitacaoTipoEspecificacao
													.getIndicadorConsiderarApenasDebitoTitularAtual().toString());
				}else{

					atualizarAdicionarSolicitacaoEspecificacaoActionForm
									.setIndicadorConsiderarApenasDebitoTitularAtual(ConstantesSistema.NAO.toString());
				}

				if(solicitacaoTipoEspecificacao.getClienteRelacaoTipo() != null){

					atualizarAdicionarSolicitacaoEspecificacaoActionForm
									.setIndicadorConsiderarApenasDebitoTitularAtual(solicitacaoTipoEspecificacao.getClienteRelacaoTipo()
													.getId().toString());
				}else{

					atualizarAdicionarSolicitacaoEspecificacaoActionForm
									.setIndicadorConsiderarApenasDebitoTitularAtual(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING);
				}

				String indicadorColetorStr = "";
				Short indicadorColetor = solicitacaoTipoEspecificacao.getIndicadorColetor();

				if(indicadorColetor != null){
					indicadorColetorStr = Short.toString(indicadorColetor);
				}

				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorColetor(indicadorColetorStr);

				String indicadorObrigatoriedadeRgRAStr = "";
				Short indicadorObrigatoriedadeRgRA = solicitacaoTipoEspecificacao.getIndicadorObrigatoriedadeRgRA();

				if(indicadorObrigatoriedadeRgRA != null){
					indicadorObrigatoriedadeRgRAStr = Short.toString(indicadorObrigatoriedadeRgRA);
				}

				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorObrigatoriedadeRgRA(indicadorObrigatoriedadeRgRAStr);

				String indicadorObrigatoriedadeCpfCnpjRAStr = "";
				Short indicadorObrigatoriedadeCpfCnpjRA = solicitacaoTipoEspecificacao.getIndicadorObrigatoriedadeCpfCnpjRA();

				if(indicadorObrigatoriedadeCpfCnpjRA != null){
					indicadorObrigatoriedadeCpfCnpjRAStr = Short.toString(indicadorObrigatoriedadeCpfCnpjRA);
				}

				atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.setIndicadorObrigatoriedadeCpfCnpjRA(indicadorObrigatoriedadeCpfCnpjRAStr);

				String indicadorMatriculaStr = "";
				Integer indicadorMatricula = solicitacaoTipoEspecificacao.getIndicadorMatricula();

				if(indicadorMatricula != null){
					indicadorMatriculaStr = Integer.toString(indicadorMatricula);
				}

				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorMatriculaImovel(indicadorMatriculaStr);

				EspecificacaoImovelSituacao especificacaoImovelSituacao = solicitacaoTipoEspecificacao.getEspecificacaoImovelSituacao();

				if(especificacaoImovelSituacao != null && !especificacaoImovelSituacao.equals("")){
					atualizarAdicionarSolicitacaoEspecificacaoActionForm
									.setIdSituacaoImovel(especificacaoImovelSituacao.getId().toString());
				}

				short indicadorGeracaoOrdemServico = solicitacaoTipoEspecificacao.getIndicadorGeracaoOrdemServico();
				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorGeraOrdemServico("" + indicadorGeracaoOrdemServico);

				ServicoTipo servicoTipo = solicitacaoTipoEspecificacao.getServicoTipo();

				if(servicoTipo != null && !servicoTipo.equals("")){
					atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdServicoOS(servicoTipo.getId().toString());
					atualizarAdicionarSolicitacaoEspecificacaoActionForm.setDescricaoServicoOS(servicoTipo.getDescricao());
				}
			}
			if(sessao.getAttribute("colecaoEspecificacaoServicoTipo") != null){

				colecaoEspecificacaoServicoTipo = (Set) sessao.getAttribute("colecaoEspecificacaoServicoTipo");
				solicitacaoTipoEspecificacao.setEspecificacaoServicoTipos(colecaoEspecificacaoServicoTipo);

			}
			colecaoEspecificacaoServicoTipo = solicitacaoTipoEspecificacao.getEspecificacaoServicoTipos();

			if(httpServletRequest.getParameter("adicionar") != null){

				colecaoEspecificacaoServicoTipo = null;
			}
			httpServletRequest.setAttribute("colecaoEspecificacaoServicoTipo", colecaoEspecificacaoServicoTipo);

		}

		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("desfazer"))){

			Integer idSolicitacaoEspecificacao = (Integer) sessao.getAttribute("idSolicitacaoEspecificacao");

			FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
			filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("solicitacaoTipo");
			filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("servicoTipo");
			filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("especificacaoImovelSituacao");
			filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("especificacaoServicoTipos");
			filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID,
							idSolicitacaoEspecificacao.toString()));
			Collection colecaoSolicitacaoTipoEspecificacaoDesfazer = fachada.pesquisar(filtroSolicitacaoTipoEspecificacao,
							SolicitacaoTipoEspecificacao.class.getName());

			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) colecaoSolicitacaoTipoEspecificacaoDesfazer
							.iterator().next();

			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setDescricaoSolicitacao(solicitacaoTipoEspecificacao.getDescricao());
			if(solicitacaoTipoEspecificacao.getHorasPrazo() == null){
				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setPrazoPrevisaoAtendimento("");
			}else{
				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setPrazoPrevisaoAtendimento(solicitacaoTipoEspecificacao
								.getHorasPrazo().toString());
			}
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorPavimentoCalcada(String.valueOf(solicitacaoTipoEspecificacao
							.getIndicadorPavimentoCalcada()));
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorLigacaoAgua(solicitacaoTipoEspecificacao
							.getIndicadorLigacaoAgua().toString());
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorPavimentoRua(String.valueOf(solicitacaoTipoEspecificacao
							.getIndicadorPavimentoRua()));
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorCobrancaMaterial(solicitacaoTipoEspecificacao
							.getIndicadorCobrancaMaterial().toString());
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorParecerEncerramento(solicitacaoTipoEspecificacao
							.getIndicadorParecerEncerramento().toString());
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorGerarDebito(solicitacaoTipoEspecificacao
							.getIndicadorGeracaoDebito().toString());
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorGerarCredito(solicitacaoTipoEspecificacao
							.getIndicadorGeracaoCredito().toString());
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorCliente(solicitacaoTipoEspecificacao.getIndicadorCliente()
							.toString());
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorVerificarDebito(solicitacaoTipoEspecificacao
							.getIndicadorVerificarDebito().toString());

			atualizarAdicionarSolicitacaoEspecificacaoActionForm
							.setIndicadorConsiderarApenasDebitoTitularAtual(solicitacaoTipoEspecificacao
											.getIndicadorConsiderarApenasDebitoTitularAtual().toString());

			if(solicitacaoTipoEspecificacao.getClienteRelacaoTipo() != null){

				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdClienteRelacaoTipo(solicitacaoTipoEspecificacao
								.getClienteRelacaoTipo().getId().toString());
			}else{

				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdClienteRelacaoTipo(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING);
			}

			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorMatriculaImovel(solicitacaoTipoEspecificacao
							.getIndicadorMatricula().toString());
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorColetor(solicitacaoTipoEspecificacao.getIndicadorColetor()
							.toString());
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorObrigatoriedadeRgRA(solicitacaoTipoEspecificacao
							.getIndicadorObrigatoriedadeRgRA().toString());
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorObrigatoriedadeCpfCnpjRA(solicitacaoTipoEspecificacao
							.getIndicadorObrigatoriedadeCpfCnpjRA().toString());

			if(!Util.isVazioOuBranco(solicitacaoTipoEspecificacao.getEspecificacaoImovelSituacao())){
				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdSituacaoImovel(solicitacaoTipoEspecificacao
								.getEspecificacaoImovelSituacao().getId().toString());
			}

			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorGeraOrdemServico(""
							+ solicitacaoTipoEspecificacao.getIndicadorGeracaoOrdemServico());

			if(!Util.isVazioOuBranco(solicitacaoTipoEspecificacao.getServicoTipo())){
				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdServicoOS(solicitacaoTipoEspecificacao.getServicoTipo().getId()
								.toString());
				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setDescricaoServicoOS(solicitacaoTipoEspecificacao.getServicoTipo()
								.getDescricao().toString());
			}

			colecaoEspecificacaoServicoTipo = solicitacaoTipoEspecificacao.getEspecificacaoServicoTipos();

			httpServletRequest.setAttribute("colecaoEspecificacaoServicoTipo", colecaoEspecificacaoServicoTipo);

			sessao.setAttribute("colecaoEspecificacaoServicoTipo", colecaoEspecificacaoServicoTipo);

			FiltroEspecificacaoImovelSituacao filtroEspecificacaoImovelSituacao = new FiltroEspecificacaoImovelSituacao();
			Collection colecaoImovelSituacao = fachada.pesquisar(filtroEspecificacaoImovelSituacao, EspecificacaoImovelSituacao.class
							.getName());
			httpServletRequest.setAttribute("colecaoImovelSituacao", colecaoImovelSituacao);

		}
		// caso exista o parametro então limpa a sessão e o form
		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("limpaSessao"))){

			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setDescricaoSolicitacao("");
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setPrazoPrevisaoAtendimento("");
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorPavimentoCalcada("");
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorLigacaoAgua("");
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorPavimentoRua("");
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorCobrancaMaterial("");
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorParecerEncerramento("");
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorGerarDebito("");
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorGerarCredito("");
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorCliente("");
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorMatriculaImovel("");
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdSituacaoImovel("");
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorGeraOrdemServico("");
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdServicoOS("");
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setDescricaoServicoOS("");
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorContaEmRevisao(null);
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorMensagemAlertaRevisao(null);
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorObrigatoriedadeRgRA("");
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorObrigatoriedadeCpfCnpjRA("");
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorConsiderarApenasDebitoTitularAtual(ConstantesSistema.NAO
							.toString());
			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdClienteRelacaoTipo(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING);

			sessao.removeAttribute("colecaoEspecificacaoServicoTipo");
		}

		// Verifica se o tipoConsulta é diferente de nulo ou vazio.
		// Nesse caso é quando um o retorno da consulta vem para o action ao inves de ir
		// direto para o jsp
		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("tipoConsulta"))){

			// verifica se retornou da pesquisa de tipo de serviço
			if(httpServletRequest.getParameter("tipoConsulta").equals("tipoServico")){
				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdServicoOS(httpServletRequest.getParameter("idCampoEnviarDados"));
				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setDescricaoServicoOS(httpServletRequest
								.getParameter("descricaoCampoEnviarDados"));

			}else if(httpServletRequest.getParameter("tipoConsulta").equals("solicitacaoTipoEspecificacaoMensagem")){
				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdSolicitacaoTipoEspecificacaoMensagem(httpServletRequest
								.getParameter("idCampoEnviarDados"));
				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setDescricaoSolicitacaoTipoEspecificacaoMensagem(httpServletRequest
								.getParameter("descricaoCampoEnviarDados"));
			}
		}
		// -------Parte que trata do código quando o usuário tecla enter
		String idTipoServicoOS = (String) atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdServicoOS();
		String descricaoServicoOS = atualizarAdicionarSolicitacaoEspecificacaoActionForm.getDescricaoServicoOS();

		// Verifica se o código foi digitado pela primeira vez ou se foi modificado
		if(!Util.isVazioOuBranco(idTipoServicoOS) && (Util.isVazioOuBranco(descricaoServicoOS == null))){

			FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, idTipoServicoOS));
			Collection servicoTipoEncontrado = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

			if(servicoTipoEncontrado != null && !servicoTipoEncontrado.isEmpty()){

				// [SF0003] - Validar Tipo Serviço
				fachada.verificarServicoTipoReferencia(Integer.valueOf(idTipoServicoOS));

				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdServicoOS(((ServicoTipo) ((List) servicoTipoEncontrado).get(0))
								.getId().toString());
				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setDescricaoServicoOS(((ServicoTipo) ((List) servicoTipoEncontrado)
								.get(0)).getDescricao());
				httpServletRequest.setAttribute("idServicoOSNaoEncontrado", "true");
				httpServletRequest.setAttribute("nomeCampo", "adicionarTipoServico");

			}else{

				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdServicoOS("");
				httpServletRequest.setAttribute("nomeCampo", "idServicoOS");
				httpServletRequest.setAttribute("idServicoOSNaoEncontrado", "exception");
				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setDescricaoServicoOS("Tipo Serviço Inexistente");

			}

		}

		// Verifica se o código foi digitado pela primeira vez ou se foi
		// modificado
		if(!Util.isVazioOuBranco(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdSolicitacaoTipoEspecificacaoMensagem())){

			FiltroSolicitacaoTipoEspecificacaoMensagem filtroSolicitacaoTipoEspecificacaoMensagem = new FiltroSolicitacaoTipoEspecificacaoMensagem();
			filtroSolicitacaoTipoEspecificacaoMensagem.adicionarParametro(new ParametroSimples(
							FiltroSolicitacaoTipoEspecificacaoMensagem.ID, Integer
											.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
															.getIdSolicitacaoTipoEspecificacaoMensagem())));
			filtroSolicitacaoTipoEspecificacaoMensagem.adicionarParametro(new ParametroSimples(
							FiltroSolicitacaoTipoEspecificacaoMensagem.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

			SolicitacaoTipoEspecificacaoMensagem objetoSolicitacaoTipoEspecificacaoMensagem = (SolicitacaoTipoEspecificacaoMensagem) Util
							.retonarObjetoDeColecao(fachada.pesquisar(filtroSolicitacaoTipoEspecificacaoMensagem,
											SolicitacaoTipoEspecificacaoMensagem.class.getName()));

			if(objetoSolicitacaoTipoEspecificacaoMensagem != null){
				atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.setDescricaoSolicitacaoTipoEspecificacaoMensagem(objetoSolicitacaoTipoEspecificacaoMensagem.getDescricao());
				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdSolicitacaoTipoEspecificacaoMensagem(String
								.valueOf(objetoSolicitacaoTipoEspecificacaoMensagem.getId()));
				httpServletRequest.removeAttribute("idSolicitacaoTipoEspecificacaoMensagemNaoEncontrado");
			}else{
				atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIdSolicitacaoTipoEspecificacaoMensagem("");
				httpServletRequest.setAttribute("nomeCampo", "idSolicitacaoTipoEspecificacaoMensagem");
				httpServletRequest.setAttribute("idSolicitacaoTipoEspecificacaoMensagemNaoEncontrado", "exception");
				atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.setDescricaoSolicitacaoTipoEspecificacaoMensagem("Mensagem Automática Padrão Inexistente");
			}

		}

		if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorConsiderarApenasDebitoTitularAtual() == null
						|| atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorConsiderarApenasDebitoTitularAtual().equals("")){

			atualizarAdicionarSolicitacaoEspecificacaoActionForm.setIndicadorConsiderarApenasDebitoTitularAtual(ConstantesSistema.NAO
							.toString());
		}

		FiltroClienteRelacaoTipo filtroClienteRelacaoTipo = new FiltroClienteRelacaoTipo();

		filtroClienteRelacaoTipo.adicionarParametro(new ParametroSimples(FiltroClienteRelacaoTipo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroClienteRelacaoTipo.setCampoOrderBy(FiltroClienteRelacaoTipo.DESCRICAO);
		List<ClienteRelacaoTipo> colecaoClienteRelacaoTipo = (List<ClienteRelacaoTipo>) fachada.pesquisar(filtroClienteRelacaoTipo,
						ClienteRelacaoTipo.class.getName());

		if(Util.isVazioOrNulo(colecaoClienteRelacaoTipo)){

			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Tipo da Relação do Cliente");
		}else{

			ClienteRelacaoTipo clienteRelacaoTipo = new ClienteRelacaoTipo("Cliente com Nome na Conta",
							ConstantesSistema.INDICADOR_USO_DESATIVO, new Date());
			clienteRelacaoTipo.setId(ConstantesSistema.NUMERO_NAO_INFORMADO);
			colecaoClienteRelacaoTipo.add(clienteRelacaoTipo);

			// Ordenar a coleção por mais de um campo
			List sortFields = new ArrayList();
			sortFields.add(new BeanComparator("descricao"));

			ComparatorChain multiSort = new ComparatorChain(sortFields);
			Collections.sort(colecaoClienteRelacaoTipo, multiSort);

			sessao.setAttribute("colecaoClienteRelacaoTipo", colecaoClienteRelacaoTipo);
		}

		FiltroEspecificacaoImovelSituacao filtroEspecificacaoImovelSituacao = new FiltroEspecificacaoImovelSituacao();
		Collection colecaoImovelSituacao = fachada
						.pesquisar(filtroEspecificacaoImovelSituacao, EspecificacaoImovelSituacao.class.getName());
		sessao.setAttribute("colecaoImovelSituacao", colecaoImovelSituacao);

		// -------Fim da Parte que trata do código quando o usuário
		// tecla
		// enter

		// remove o retorno da
		// solicitação_especificação_tipo_servico_adicionar_popup.jsp
		sessao.removeAttribute("retornarTelaPopup");
		sessao.removeAttribute("caminhoRetornoTelaPesquisaUnidadeOrganizacional");
		sessao.removeAttribute("caminhoRetornoTelaPesquisaTipoServico");
		sessao.removeAttribute("caminhoRetornoTelaPesquisaMensagemTipoSolicitacaoEspecificacao");
		sessao.removeAttribute("atualizarAdicionarSolicitacaoEspecificacaoActionForm");

		return retorno;
	}
}
