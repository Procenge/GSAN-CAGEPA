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

package gcom.gui.cadastro.imovel;

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.aviso.AvisoBancario;
import gcom.arrecadacao.pagamento.FiltroGuiaPagamentoHistorico;
import gcom.arrecadacao.pagamento.GuiaPagamentoHistorico;
import gcom.arrecadacao.pagamento.Pagamento;
import gcom.arrecadacao.pagamento.PagamentoHistorico;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.DocumentoTipo;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.*;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoACobrarHistorico;
import gcom.faturamento.debito.FiltroDebitoACobrarHistorico;
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
 * 1° Aba do [UC0472] Pagamentos do Imovel
 * 
 * @author Rafael Santos
 * @since 05/09/2006
 * @author Saulo Lima
 * @date 11/08/2009
 *       Alteração para exibir os pagamentos com DocumentoTipo referente a FATURA_CLIENTE
 */
public class ExibirConsultarImovelPagamentosAction
				extends GcomAction {

	/**
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("consultarImovelPagamentos");

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarImovelActionForm consultarImovelActionForm = (ConsultarImovelActionForm) actionForm;

		// id do imovel da aba dados cadastrais
		String idImovelPagamentos = consultarImovelActionForm.getIdImovelPagamentos();
		String limparForm = httpServletRequest.getParameter("limparForm");
		String indicadorNovo = httpServletRequest.getParameter("indicadorNovo");
		String idImovelPrincipalAba = null;
		if(sessao.getAttribute("idImovelPrincipalAba") != null){
			idImovelPrincipalAba = (String) sessao.getAttribute("idImovelPrincipalAba");
		}

		if(limparForm != null && !limparForm.equals("")){

			// limpar os dados
			httpServletRequest.setAttribute("idImovelPagamentosNaoEncontrado", null);

			sessao.removeAttribute("imovelClientes");

			consultarImovelActionForm.setIdImovelDadosComplementares(null);
			consultarImovelActionForm.setIdImovelDadosCadastrais(null);
			consultarImovelActionForm.setIdImovelAnaliseMedicaoConsumo(null);
			consultarImovelActionForm.setIdImovelHistoricoFaturamento(null);
			consultarImovelActionForm.setIdImovelDebitos(null);
			consultarImovelActionForm.setIdImovelPagamentos(null);
			consultarImovelActionForm.setIdImovelDevolucoesImovel(null);
			consultarImovelActionForm.setIdImovelDocumentosCobranca(null);
			consultarImovelActionForm.setIdImovelParcelamentosDebitos(null);
			consultarImovelActionForm.setIdImovelRegistroAtendimento(null);

			sessao.removeAttribute("imovelPagamentos");
			sessao.removeAttribute("colecaoPagamentosImovelConta");
			sessao.removeAttribute("colecaoPagamentosImovelGuiaPagamento");
			sessao.removeAttribute("colecaoPagamentosImovelDebitoACobrar");
			sessao.removeAttribute("colecaoPagamentosHistoricoImovelConta");
			sessao.removeAttribute("colecaoPagamentosHistoricoImovelGuiaPagamento");
			sessao.removeAttribute("colecaoPagamentosHistoricoImovelDebitoACobrar");

			sessao.removeAttribute("qtdePagContas");
			sessao.removeAttribute("qtdePagGuiaPagamento");
			sessao.removeAttribute("qtdePagDebitoACobrar");

			// sessao.removeAttribute("idImovelPrincipalAba");
			// consultarImovelActionForm.setIdImovelPagamentos(null);
			consultarImovelActionForm.setMatriculaImovelPagamentos(null);
			consultarImovelActionForm.setSituacaoAguaPagamentos(null);
			consultarImovelActionForm.setSituacaoEsgotoPagamentos(null);

			consultarImovelActionForm.setSituacaoCobrancaDadosComplementares(null);

			if(indicadorNovo == null || indicadorNovo.equals("")){
				idImovelPagamentos = null;
				idImovelPrincipalAba = null;
			}

		}

		if((idImovelPagamentos != null && !idImovelPagamentos.equalsIgnoreCase(""))
						|| (idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase(""))){

			if(idImovelPagamentos != null && !idImovelPagamentos.equalsIgnoreCase("")){

				if(idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase("")){

					if(indicadorNovo != null && !indicadorNovo.equals("")){
						consultarImovelActionForm.setIdImovelPagamentos(idImovelPagamentos);

					}else if(!(idImovelPagamentos.equals(idImovelPrincipalAba))){
						consultarImovelActionForm.setIdImovelPagamentos(idImovelPrincipalAba);
						idImovelPagamentos = idImovelPrincipalAba;
					}
				}
			}else if(idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase("")){
				consultarImovelActionForm.setIdImovelRegistroAtendimento(idImovelPrincipalAba);
				idImovelPagamentos = idImovelPrincipalAba;
			}

			// Obtém a instância da Fachada
			Fachada fachada = Fachada.getInstancia();
			Imovel imovel = null;

			// verifica se o objeto imovel é o mesmo ja pesquisado, para não precisar pesquisar mas.
			boolean imovelNovoPesquisado = false;
			if(sessao.getAttribute("imovelPagamentos") != null){
				imovel = (Imovel) sessao.getAttribute("imovelPagamentos");
				if(!(imovel.getId().toString().equals(idImovelPagamentos.trim()))){
					imovel = fachada.consultarImovelHistoricoFaturamento(Integer.valueOf(idImovelPagamentos.trim()));
					imovelNovoPesquisado = true;
				}
			}else{
				imovel = fachada.consultarImovelHistoricoFaturamento(Integer.valueOf(idImovelPagamentos.trim()));
				imovelNovoPesquisado = true;
			}

			if(imovel != null){
				sessao.setAttribute("imovelPagamentos", imovel);
				sessao.setAttribute("idImovelPrincipalAba", imovel.getId().toString());
				consultarImovelActionForm.setIdImovelPagamentos(imovel.getId().toString());

				// caso o imovel pesquisado seja diferente do pesquisado anterior ou seja a primeira
				// vez que se esteja pesquisando
				if(imovelNovoPesquisado){
					// seta na tela a inscrição do imovel
					httpServletRequest.setAttribute("idImovelPagamentosNaoEncontrado", null);

					consultarImovelActionForm.setMatriculaImovelPagamentos(fachada.pesquisarInscricaoImovel(Integer
									.valueOf(idImovelPagamentos.trim()), true));

					// seta a situação de agua
					if(imovel.getLigacaoAguaSituacao() != null){
						consultarImovelActionForm.setSituacaoAguaPagamentos(imovel.getLigacaoAguaSituacao().getDescricao());
					}
					// seta a situação de esgoto
					if(imovel.getLigacaoEsgotoSituacao() != null){
						consultarImovelActionForm.setSituacaoEsgotoPagamentos(imovel.getLigacaoEsgotoSituacao().getDescricao());
					}

					// 1. O sistema seleciona os pagamentos do imóvel
					// (a partir da tabela PAGAMENTO com IMOV_ID = Id do imóvel informado e demais
					// parâmetros de seleção informada)

					// pesquisa utilizada pelo do Consultar Pagamento
					Collection colecaoImoveisPagamentos = fachada.pesquisarPagamentoImovel(idImovelPagamentos.trim(), null, null, null,
									null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
									null);

					Collection colecaoImoveisPagamentosHistorico = fachada.pesquisarPagamentoHistoricoImovel(idImovelPagamentos.trim(),
									null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
									null, null, null, null);

					// Collection<PagamentoHistorico> colecaoImoveisPagamentosHistorico =
					// fachada.pesquisarPagamentosHistoricoConsultaImovel(new
					// Integer(idImovelPagamentos.trim()));

					// Imóvel
					Collection<Pagamento> colecaoPagamentosImovelConta = new ArrayList();
					Collection<Pagamento> colecaoPagamentosImovelGuiaPagamento = new ArrayList();
					Collection<Pagamento> colecaoPagamentosImovelDebitoACobrar = new ArrayList();

					// define as ordenações para Pagamentos de Conta, Guias e Debitos A Cobrar
					ComparatorChain sortPagamentosConta = new ComparatorChain();
					sortPagamentosConta.addComparator(new BeanComparator("anoMesReferenciaPagamento"), true);
					sortPagamentosConta.addComparator(new BeanComparator("dataPagamento"), true);
					sortPagamentosConta.addComparator(new BeanComparator("localidade.id"));
					sortPagamentosConta.addComparator(new BeanComparator("imovel.id"));

					ComparatorChain sortPagamentosGuiasDebitos = new ComparatorChain();
					sortPagamentosGuiasDebitos.addComparator(new BeanComparator("localidade.id"));
					sortPagamentosGuiasDebitos.addComparator(new BeanComparator("imovel.id"));
					sortPagamentosGuiasDebitos.addComparator(new BeanComparator("dataPagamento"), true);

					Integer qtdePagContas = 0;
					Integer qtdePagGuiaPagamento = 0;
					Integer qtdePagDebitoACobrar = 0;

					// Consultar Pagamentos do Imóvel
					if(colecaoImoveisPagamentos != null && !colecaoImoveisPagamentos.isEmpty()){

						Iterator colecaoPagamentoIterator = colecaoImoveisPagamentos.iterator();

						// Divide os pagamentos do imóvel pelo tipo de pagamento
						while(colecaoPagamentoIterator.hasNext()){
							Pagamento pagamento = ((Pagamento) colecaoPagamentoIterator.next());

							if(pagamento.getDocumentoTipo().getId().equals(DocumentoTipo.CONTA)
											|| pagamento.getDocumentoTipo().getId().equals(DocumentoTipo.FATURA_CLIENTE)){

								// se o pagamento estiver referenciando uma conta que esteja no
								// historico, haverá um erro.
								// TODO Modificar depois. Saulo Lima 23/01/09
								// Pq não usar o instanceof
								try{
									pagamento.getConta().getReferencia();
								}catch(Exception e){

									Integer contaGeralId = fachada.pesquisarIdDaContaGeralNoPagamento(pagamento.getId());

									ContaHistorico contaHistorico = null;
									if(contaGeralId != null){
										FiltroContaGeral filtroContaGeral = new FiltroContaGeral();
										filtroContaGeral.adicionarParametro(new ParametroSimples(FiltroContaGeral.ID, contaGeralId));
										Collection<ContaGeral> colecaoContaGeral = fachada.pesquisar(filtroContaGeral, ContaGeral.class
														.getName());

										// Pega a ContaGeral
										ContaGeral contaGeral = null;
										if(colecaoContaGeral != null && !colecaoContaGeral.isEmpty()){
											contaGeral = (ContaGeral) Util.retonarObjetoDeColecao(colecaoContaGeral);

											// Verifica se está no Histórico
											if(contaGeral.getIndicadorHistorico() == ConstantesSistema.SIM.shortValue()){
												FiltroContaHistorico filtroContaHistorico = new FiltroContaHistorico();
												filtroContaHistorico.adicionarParametro(new ParametroSimples(FiltroContaHistorico.ID,
																contaGeral.getId()));

												Collection<ContaHistorico> colecaoContaHistorico = fachada.pesquisar(filtroContaHistorico,
																ContaHistorico.class.getName());

												if(colecaoContaHistorico != null && !colecaoContaHistorico.isEmpty()){
													contaHistorico = (ContaHistorico) Util.retonarObjetoDeColecao(colecaoContaHistorico);
												}
											}
										}
									}

									if(contaHistorico == null){

										if(pagamento.getImovel() != null && pagamento.getImovel().getId() != null
														&& pagamento.getAnoMesReferenciaPagamento() != null){

											FiltroContaHistorico filtroContaHistoricoNovo = new FiltroContaHistorico();
											filtroContaHistoricoNovo.adicionarParametro(new ParametroSimples(
															FiltroContaHistorico.ANO_MES_REFERENCIA, pagamento
																			.getAnoMesReferenciaPagamento()));
											filtroContaHistoricoNovo.adicionarParametro(new ParametroSimples(
															FiltroContaHistorico.IMOVEL_ID, pagamento.getImovel().getId()));

											Collection<ContaHistorico> colecaoContaHistorico = fachada.pesquisar(filtroContaHistoricoNovo,
															ContaHistorico.class.getName());

											if(colecaoContaHistorico != null && !colecaoContaHistorico.isEmpty()){
												contaHistorico = (ContaHistorico) Util.retonarObjetoDeColecao(colecaoContaHistorico);
											}
										}
									}

									if(contaHistorico != null){

										// Setta os valores
										Conta conta = new Conta();
										conta.setId(contaGeralId);
										conta.setReferencia(contaHistorico.getAnoMesReferenciaConta());
										conta.setDebitos(contaHistorico.getValorDebitos());
										conta.setValorAgua(contaHistorico.getValorAgua());
										conta.setValorEsgoto(contaHistorico.getValorEsgoto());
										conta.setValorCreditos(contaHistorico.getValorCreditos());
										conta.setValorImposto(contaHistorico.getValorImposto());

										// Adiciona a "nova" conta ao pagamento
										pagamento.setConta(conta);
									}
								}
								colecaoPagamentosImovelConta.add(pagamento);

							}else if(pagamento.getDocumentoTipo().getId().equals(DocumentoTipo.DEBITO_A_COBRAR)){

								// Se DebitoACobrar não vier com imovel todo preenchido pesquisar da
								// base.
								// TODO Modificar depois. Saulo Lima 28/09/09
								if(pagamento.getDebitoACobrar() != null){
									try{
										pagamento.getDebitoACobrar().getImovel().getId();
									}catch(Exception e){
										Integer idDebitoACobrar = pagamento.getDebitoACobrar().getId();

										FiltroDebitoACobrarHistorico filtroDebitoACobrarHistorico = new FiltroDebitoACobrarHistorico();
										filtroDebitoACobrarHistorico.adicionarParametro(new ParametroSimples(
														FiltroDebitoACobrarHistorico.ID, idDebitoACobrar));
										filtroDebitoACobrarHistorico
														.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoACobrarHistorico.IMOVEL);
										filtroDebitoACobrarHistorico
														.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoACobrarHistorico.DEBITO_TIPO);
										Collection<DebitoACobrarHistorico> colecaoDebitoACobrarHistorico = fachada.pesquisar(
														filtroDebitoACobrarHistorico, DebitoACobrarHistorico.class.getName());

										if(colecaoDebitoACobrarHistorico != null && !colecaoDebitoACobrarHistorico.isEmpty()){
											DebitoACobrarHistorico debitoACobrarHistorico = (DebitoACobrarHistorico) Util
															.retonarObjetoDeColecao(colecaoDebitoACobrarHistorico);
											DebitoACobrar debitoACobrar = new DebitoACobrar();
											debitoACobrar.setId(debitoACobrarHistorico.getId());
											debitoACobrar.setImovel(debitoACobrarHistorico.getImovel());
											debitoACobrar.setDebitoTipo(debitoACobrarHistorico.getDebitoTipo());
											pagamento.setDebitoACobrar(debitoACobrar);
										}
									}
								}

								colecaoPagamentosImovelDebitoACobrar.add(pagamento);
							}else if(pagamento.getDocumentoTipo().getId().equals(DocumentoTipo.GUIA_PAGAMENTO)){
								colecaoPagamentosImovelGuiaPagamento.add(pagamento);
							}
						}

						// Organizar a coleção de Conta
						if(colecaoPagamentosImovelConta != null && !colecaoPagamentosImovelConta.isEmpty()){

							Collections.sort((List) colecaoPagamentosImovelConta, sortPagamentosConta);
							sessao.setAttribute("colecaoPagamentosImovelConta", colecaoPagamentosImovelConta);
							qtdePagContas = colecaoPagamentosImovelConta.size();

						}else{
							sessao.removeAttribute("colecaoPagamentosImovelConta");
						}

						// Organizar a coleção de Guia de Pagamento
						if(colecaoPagamentosImovelGuiaPagamento != null && !colecaoPagamentosImovelGuiaPagamento.isEmpty()){

							Collections.sort((List) colecaoPagamentosImovelGuiaPagamento, sortPagamentosGuiasDebitos);
							sessao.setAttribute("colecaoPagamentosImovelGuiaPagamento", colecaoPagamentosImovelGuiaPagamento);
							qtdePagGuiaPagamento = colecaoPagamentosImovelGuiaPagamento.size();

						}else{
							sessao.removeAttribute("colecaoPagamentosImovelGuiaPagamento");
						}

						// Organizar a coleção de Guia de Pagamento
						if(colecaoPagamentosImovelDebitoACobrar != null && !colecaoPagamentosImovelDebitoACobrar.isEmpty()){

							// Organizar a coleção
							Collections.sort((List) colecaoPagamentosImovelDebitoACobrar, sortPagamentosGuiasDebitos);
							sessao.setAttribute("colecaoPagamentosImovelDebitoACobrar", colecaoPagamentosImovelDebitoACobrar);
							qtdePagDebitoACobrar = colecaoPagamentosImovelDebitoACobrar.size();

						}else{
							sessao.removeAttribute("colecaoPagamentosImovelDebitoACobrar");
						}

					}else{

						sessao.removeAttribute("colecaoPagamentosImovelConta");
						sessao.removeAttribute("colecaoPagamentosImovelGuiaPagamento");
						sessao.removeAttribute("colecaoPagamentosImovelDebitoACobrar");
					}

					// Imóvel - Pagamento Historico
					Collection<PagamentoHistorico> colecaoPagamentosHistoricoImovelConta = new ArrayList();
					Collection<PagamentoHistorico> colecaoPagamentosHistoricoImovelGuiaPagamento = new ArrayList();
					Collection<PagamentoHistorico> colecaoPagamentosHistoricoImovelDebitoACobrar = new ArrayList();

					// Consultar Pagamentos do Imóvel
					if(colecaoImoveisPagamentosHistorico != null && !colecaoImoveisPagamentosHistorico.isEmpty()){

						Iterator colecaoPagamentoHistoricoIterator = colecaoImoveisPagamentosHistorico.iterator();

						// Divide os pagamentos do imóvel pelo tipo de pagamento
						while(colecaoPagamentoHistoricoIterator.hasNext()){
							PagamentoHistorico pagamentoHistorico = ((PagamentoHistorico) colecaoPagamentoHistoricoIterator.next());

							if(pagamentoHistorico.getAvisoBancario() == null){
								AvisoBancario avisoBancario = new AvisoBancario();
								Arrecadador arrecadador = new Arrecadador();
								Cliente cliente = new Cliente();

								String nomeCliente = fachada.pesquisarNomeAgenteArrecadador(pagamentoHistorico.getId());

								if(nomeCliente != null){

									cliente.setNome(nomeCliente);
									arrecadador.setCliente(cliente);
									avisoBancario.setArrecadador(arrecadador);
									pagamentoHistorico.setAvisoBancario(avisoBancario);
								}
							}

							if((pagamentoHistorico.getDocumentoTipo().getId().equals(DocumentoTipo.CONTA) || pagamentoHistorico
											.getDocumentoTipo().getId().equals(DocumentoTipo.FATURA_CLIENTE))){

								colecaoPagamentosHistoricoImovelConta.add(pagamentoHistorico);

							}else if(pagamentoHistorico.getDocumentoTipo().getId().equals(DocumentoTipo.DEBITO_A_COBRAR)){

								// Se DebitoACobrar não vier com imovel todo preenchido pesquisar da
								// base.
								if(pagamentoHistorico.getDebitoACobrar() != null){
									try{
										pagamentoHistorico.getDebitoACobrar().getImovel().getId();
									}catch(Exception e){
										Integer idDebitoACobrar = pagamentoHistorico.getDebitoACobrar().getId();

										FiltroDebitoACobrarHistorico filtroDebitoACobrarHistorico = new FiltroDebitoACobrarHistorico();
										filtroDebitoACobrarHistorico.adicionarParametro(new ParametroSimples(
														FiltroDebitoACobrarHistorico.ID, idDebitoACobrar));
										filtroDebitoACobrarHistorico
														.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoACobrarHistorico.IMOVEL);
										filtroDebitoACobrarHistorico
														.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoACobrarHistorico.DEBITO_TIPO);
										Collection<DebitoACobrarHistorico> colecaoDebitoACobrarHistorico = fachada.pesquisar(
														filtroDebitoACobrarHistorico, DebitoACobrarHistorico.class.getName());

										if(colecaoDebitoACobrarHistorico != null && !colecaoDebitoACobrarHistorico.isEmpty()){
											DebitoACobrarHistorico debitoACobrarHistorico = (DebitoACobrarHistorico) Util
															.retonarObjetoDeColecao(colecaoDebitoACobrarHistorico);
											DebitoACobrar debitoACobrar = new DebitoACobrar();
											debitoACobrar.setId(debitoACobrarHistorico.getId());
											debitoACobrar.setImovel(debitoACobrarHistorico.getImovel());
											debitoACobrar.setDebitoTipo(debitoACobrarHistorico.getDebitoTipo());
											pagamentoHistorico.setDebitoACobrar(debitoACobrar);
										}
									}
								}
								colecaoPagamentosHistoricoImovelDebitoACobrar.add(pagamentoHistorico);

							}else if(pagamentoHistorico.getDocumentoTipo().getId().equals(DocumentoTipo.GUIA_PAGAMENTO)){

								// Se GuiaPagamento não vier com guiaPagamento toda preenchida
								// pesquisar da base.
								if(pagamentoHistorico.getGuiaPagamentoGeral().getGuiaPagamentoHistorico() != null){
									try{
										pagamentoHistorico.getGuiaPagamentoGeral().getGuiaPagamentoHistorico().getValorDebito();
									}catch(Exception e){
										Integer idGuiaPagamento = pagamentoHistorico.getGuiaPagamentoGeral().getGuiaPagamentoHistorico()
														.getId();

										FiltroGuiaPagamentoHistorico filtroGuiaPagamentoHistorico = new FiltroGuiaPagamentoHistorico();
										filtroGuiaPagamentoHistorico.adicionarParametro(new ParametroSimples(
														FiltroGuiaPagamentoHistorico.ID, idGuiaPagamento));
										Collection<GuiaPagamentoHistorico> colecaoGuiaPagamentoHistorico = fachada.pesquisar(
														filtroGuiaPagamentoHistorico, GuiaPagamentoHistorico.class.getName());

										if(colecaoGuiaPagamentoHistorico != null && !colecaoGuiaPagamentoHistorico.isEmpty()){
											GuiaPagamentoHistorico guiaPagamentoHistorico = (GuiaPagamentoHistorico) Util
															.retonarObjetoDeColecao(colecaoGuiaPagamentoHistorico);

											pagamentoHistorico.getGuiaPagamentoGeral().setGuiaPagamentoHistorico(guiaPagamentoHistorico);
										}
									}
								}
								colecaoPagamentosHistoricoImovelGuiaPagamento.add(pagamentoHistorico);
							}
						}

						// Organizar a coleção de Conta
						if(colecaoPagamentosHistoricoImovelConta != null && !colecaoPagamentosHistoricoImovelConta.isEmpty()){

							Collections.sort((List) colecaoPagamentosHistoricoImovelConta, sortPagamentosConta);
							sessao.setAttribute("colecaoPagamentosHistoricoImovelConta", colecaoPagamentosHistoricoImovelConta);
							qtdePagContas = qtdePagContas + colecaoPagamentosHistoricoImovelConta.size();

						}else{
							sessao.removeAttribute("colecaoPagamentosHistoricoImovelConta");
						}

						// Organizar a coleção de Guia de Pagamento
						if(colecaoPagamentosHistoricoImovelGuiaPagamento != null
										&& !colecaoPagamentosHistoricoImovelGuiaPagamento.isEmpty()){

							Collections.sort((List) colecaoPagamentosHistoricoImovelGuiaPagamento, sortPagamentosGuiasDebitos);
							sessao.setAttribute("colecaoPagamentosHistoricoImovelGuiaPagamento",
											colecaoPagamentosHistoricoImovelGuiaPagamento);
							qtdePagGuiaPagamento = qtdePagGuiaPagamento + colecaoPagamentosHistoricoImovelGuiaPagamento.size();

						}else{
							sessao.removeAttribute("colecaoPagamentosHistoricoImovelGuiaPagamento");
						}

						// Organizar a coleção de Débito a Cobrar
						if(colecaoPagamentosHistoricoImovelDebitoACobrar != null
										&& !colecaoPagamentosHistoricoImovelDebitoACobrar.isEmpty()){

							Collections.sort((List) colecaoPagamentosHistoricoImovelDebitoACobrar, sortPagamentosGuiasDebitos);
							sessao.setAttribute("colecaoPagamentosHistoricoImovelDebitoACobrar",
											colecaoPagamentosHistoricoImovelDebitoACobrar);
							qtdePagDebitoACobrar = qtdePagDebitoACobrar + colecaoPagamentosHistoricoImovelDebitoACobrar.size();

						}else{
							sessao.removeAttribute("colecaoPagamentosHistoricoImovelDebitoACobrar");
						}
					}else{
						sessao.removeAttribute("colecaoPagamentosHistoricoImovelConta");
						sessao.removeAttribute("colecaoPagamentosHistoricoImovelGuiaPagamento");
						sessao.removeAttribute("colecaoPagamentosHistoricoImovelDebitoACobrar");
					}

					sessao.setAttribute("qtdePagContas", qtdePagContas);
					sessao.setAttribute("qtdePagGuiaPagamento", qtdePagGuiaPagamento);
					sessao.setAttribute("qtdePagDebitoACobrar", qtdePagDebitoACobrar);

				}
			}else{
				httpServletRequest.setAttribute("idImovelPagamentosNaoEncontrado", "true");
				consultarImovelActionForm.setMatriculaImovelPagamentos("IMÓVEL INEXISTENTE");

				// limpar os dados pesquisados
				sessao.removeAttribute("imovelPagamentos");
				sessao.removeAttribute("colecaoPagamentosImovelConta");
				sessao.removeAttribute("colecaoPagamentosImovelGuiaPagamento");
				sessao.removeAttribute("colecaoPagamentosImovelDebitoACobrar");

				sessao.removeAttribute("colecaoPagamentosHistoricoImovelConta");
				sessao.removeAttribute("colecaoPagamentosHistoricoImovelGuiaPagamento");
				sessao.removeAttribute("colecaoPagamentosHistoricoImovelDebitoACobrar");

				sessao.removeAttribute("qtdePagContas");
				sessao.removeAttribute("qtdePagGuiaPagamento");
				sessao.removeAttribute("qtdePagDebitoACobrar");

				consultarImovelActionForm.setIdImovelPagamentos(null);
				consultarImovelActionForm.setSituacaoAguaPagamentos(null);
				consultarImovelActionForm.setSituacaoEsgotoPagamentos(null);
			}
		}else{
			consultarImovelActionForm.setIdImovelPagamentos(idImovelPagamentos);

			httpServletRequest.setAttribute("idImovelPagamentosNaoEncontrado", null);

			sessao.removeAttribute("imovelPagamentos");
			sessao.removeAttribute("colecaoPagamentosImovelConta");
			sessao.removeAttribute("colecaoPagamentosImovelGuiaPagamento");
			sessao.removeAttribute("colecaoPagamentosImovelDebitoACobrar");
			sessao.removeAttribute("colecaoPagamentosHistoricoImovelConta");
			sessao.removeAttribute("colecaoPagamentosHistoricoImovelGuiaPagamento");
			sessao.removeAttribute("colecaoPagamentosHistoricoImovelDebitoACobrar");

			sessao.removeAttribute("qtdePagContas");
			sessao.removeAttribute("qtdePagGuiaPagamento");
			sessao.removeAttribute("qtdePagDebitoACobrar");

			sessao.removeAttribute("idImovelPrincipalAba");
			consultarImovelActionForm.setMatriculaImovelPagamentos(null);
			consultarImovelActionForm.setSituacaoAguaPagamentos(null);
			consultarImovelActionForm.setSituacaoEsgotoPagamentos(null);
		}

		return retorno;
	}

}
