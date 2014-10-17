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

package gcom.gui.arrecadacao.pagamento;

import gcom.arrecadacao.ArrecadadorMovimento;
import gcom.arrecadacao.aviso.AvisoBancario;
import gcom.arrecadacao.pagamento.FiltroGuiaPagamentoGeral;
import gcom.arrecadacao.pagamento.Pagamento;
import gcom.arrecadacao.pagamento.PagamentoHistorico;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteEndereco;
import gcom.cadastro.cliente.ClienteFone;
import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.DocumentoTipo;
import gcom.fachada.Fachada;
import gcom.faturamento.GuiaPagamentoGeral;
import gcom.faturamento.conta.*;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoACobrarHistorico;
import gcom.faturamento.debito.FiltroDebitoACobrarHistorico;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Permite a consulta de pagamentos de um imóvel ou de um cliente ou de um aviso
 * bancário ou de um movimento arrecadador. Permite também geração do relatório
 * dos pagamentos de um intervalo de localidades
 * [UC0247] Consultar Pagamentos
 * 
 * @author Tiago Moreno, Roberta Costa
 * @date 31/01/2006, 05/05/2003
 * @author Saulo Lima
 * @date 05/01/2009
 *       Correção do recebimento e tratamento das coleções de Pagamento entre a Sessão e a Tela.
 */
public class ExibirConsultarPagamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("consultarClientePagamentos");

		// Instacia a fachada
		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarPagamentoActionForm consultarPagamentoActionForm = (ConsultarPagamentoActionForm) actionForm;
		Collection<Object> colecaoPagamentosAutenticar = new ArrayList<Object>();

		// Imóvel - Pagamento
		Collection<Pagamento> colecaoPagamentosImovelConta = new ArrayList();
		Collection<Pagamento> colecaoPagamentosImovelGuiaPagamento = new ArrayList();
		Collection<Pagamento> colecaoPagamentosImovelDebitoACobrar = new ArrayList();
		// Imóvel - Pagamento Historico
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoImovelConta = new ArrayList();
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoImovelGuiaPagamento = new ArrayList();
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoImovelDebitoACobrar = new ArrayList();

		// Cliente
		Collection<Pagamento> colecaoPagamentosClienteConta = (Collection<Pagamento>) sessao.getAttribute("colecaoPagamentosClienteConta");
		Collection<Pagamento> colecaoPagamentosClienteGuiaPagamento = (Collection<Pagamento>) sessao
						.getAttribute("colecaoPagamentosClienteGuiaPagamento");
		Collection<Pagamento> colecaoPagamentosClienteDebitoACobrar = (Collection<Pagamento>) sessao
						.getAttribute("colecaoPagamentosClienteDebitoACobrar");

		// Cliente - Pagamento Historico
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoClienteConta = (Collection<PagamentoHistorico>) sessao
						.getAttribute("colecaoPagamentosHistoricoClienteConta");
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoClienteGuiaPagamento = (Collection<PagamentoHistorico>) sessao
						.getAttribute("colecaoPagamentosHistoricoClienteGuiaPagamento");
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoClienteDebitoACobrar = (Collection<PagamentoHistorico>) sessao
						.getAttribute("colecaoPagamentosHistoricoClienteDebitoACobrar");

		// Aviso Bancário
		Collection<Pagamento> colecaoPagamentosAvisoBancario = (Collection<Pagamento>) sessao
						.getAttribute("colecaoPagamentosAvisoBancario");
		sessao.removeAttribute("colecaoPagamentosAvisoBancario");
		Collection<Pagamento> colecaoPagamentosAvisoBancarioConta = new ArrayList();
		Collection<Pagamento> colecaoPagamentosAvisoBancarioGuiaPagamento = new ArrayList();
		Collection<Pagamento> colecaoPagamentosAvisoBancarioDebitoACobrar = new ArrayList();

		// Aviso Bancário - Pagamento Historico
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoAvisoBancario = (Collection<PagamentoHistorico>) sessao
						.getAttribute("colecaoPagamentosHistoricoAvisoBancario");
		sessao.removeAttribute("colecaoPagamentosHistoricoAvisoBancario");
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoAvisoBancarioConta = new ArrayList();
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoAvisoBancarioGuiaPagamento = new ArrayList();
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoAvisoBancarioDebitoACobrar = new ArrayList();

		// Movimento Arrecadador
		Collection<Pagamento> colecaoPagamentosMovimentoArrecadador = (Collection<Pagamento>) sessao
						.getAttribute("colecaoPagamentosMovimentoArrecadador");
		sessao.removeAttribute("colecaoPagamentosMovimentoArrecadador");
		Collection<Pagamento> colecaoPagamentosMovimentoArrecadadorConta = new ArrayList();
		Collection<Pagamento> colecaoPagamentosMovimentoArrecadadorGuiaPagamento = new ArrayList();
		Collection<Pagamento> colecaoPagamentosMovimentoArrecadadorDebitoACobrar = new ArrayList();

		// Movimento Arrecadador - Pagamento Historico
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoMovimentoArrecadador = (Collection<PagamentoHistorico>) sessao
						.getAttribute("colecaoPagamentosHistoricoMovimentoArrecadador");
		sessao.removeAttribute("colecaoPagamentosHistoricoMovimentoArrecadador");
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoMovimentoArrecadadorConta = new ArrayList();
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoMovimentoArrecadadorGuiaPagamento = new ArrayList();
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoMovimentoArrecadadorDebitoACobrar = new ArrayList();

		// Variáveis para definir tamanho das tabelas
		// se necessário ou naum scroll
		Integer qtdePagContas = 0;
		Integer qtdePagGuiaPagamento = 0;
		Integer qtdePagDebitoACobrar = 0;

		Integer qtdePagContasAtual = 0;
		Integer qtdePagContasHistorico = 0;
		Integer qtdePagGuiaPagamentoAtual = 0;
		Integer qtdePagGuiaPagamentoHistorico = 0;
		Integer qtdePagDebitoACobrarAtual = 0;
		Integer qtdePagDebitoACobrarHistorico = 0;

		BigDecimal vlPagContasAtual = BigDecimal.ZERO;
		BigDecimal vlPagContasHistorico = BigDecimal.ZERO;
		BigDecimal vlPagGuiaPagamentoAtual = BigDecimal.ZERO;
		BigDecimal vlPagGuiaPagamentoHistorico = BigDecimal.ZERO;
		BigDecimal vlPagDebitoACobrarAtual = BigDecimal.ZERO;
		BigDecimal vlPagDebitoACobrarHistorico = BigDecimal.ZERO;

		// Consultar Pagamentos do Imóvel
		if(sessao.getAttribute("colecaoImoveisPagamentos") != null){

			retorno = actionMapping.findForward("consultarImovelPagamentos");

			Collection<Pagamento> colecaoImoveisPagamentos = (Collection) sessao.getAttribute("colecaoImoveisPagamentos");

			sessao.removeAttribute("colecaoImoveisPagamentos");

			Imovel imovel = (Imovel) sessao.getAttribute("imovel");

			consultarPagamentoActionForm.setInscricao(imovel.getInscricaoFormatada());

			if(imovel.getLigacaoAguaSituacao() != null){
				consultarPagamentoActionForm.setSituacaoLigacaoAgua(imovel.getLigacaoAguaSituacao().getDescricao());
			}
			if(imovel.getLigacaoEsgotoSituacao() != null){
				consultarPagamentoActionForm.setSituacaoLigacaoEsgoto(imovel.getLigacaoEsgotoSituacao().getDescricao());
			}

			Iterator colecaoPagamentoIterator = colecaoImoveisPagamentos.iterator();

			// Divide os pagamentos do imóvel pelo tipo de pagamento
			while(colecaoPagamentoIterator.hasNext()){
				Pagamento pagamento = ((Pagamento) colecaoPagamentoIterator.next());

				if(pagamento.getDocumentoTipo().getId().equals(DocumentoTipo.CONTA)){
					Conta conta = pagamento.getConta();

					// Na base existem registros de pagamento sem contaId e do tipo conta.
					// Nesses casos devemos filtrar a conta pela matricula do imóvel e referencia.
					if(conta != null || (imovel.getId() != null && pagamento.getAnoMesReferenciaPagamento() != null)){
						FiltroConta filtroConta = new FiltroConta();

						if(conta != null){
							filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, conta.getId()));
						}else{
							filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.IMOVEL_ID, imovel.getId()));
							filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.REFERENCIA, pagamento
											.getAnoMesReferenciaPagamento()));
						}

						Collection<Conta> colecaoConta = fachada.pesquisar(filtroConta, Conta.class.getName());

						if(colecaoConta != null && !colecaoConta.isEmpty()){
							conta = (Conta) Util.retonarObjetoDeColecao(colecaoConta);
							pagamento.setConta(conta);

							colecaoPagamentosImovelConta.add(pagamento);
							vlPagContasAtual = vlPagContasAtual.add(pagamento.getValorPagamento());
						}
						preencherColecaoDePagamentosParaAutenticar(colecaoPagamentosAutenticar, pagamento);
					}
				}else if(pagamento.getDocumentoTipo().getId().equals(DocumentoTipo.DEBITO_A_COBRAR)){
					if(pagamento.getDebitoACobrar() != null){
						Integer idDebitoACobrar = pagamento.getDebitoACobrar().getId();

						FiltroDebitoACobrarHistorico filtroDebitoACobrarHistorico = new FiltroDebitoACobrarHistorico();
						filtroDebitoACobrarHistorico.adicionarParametro(new ParametroSimples(FiltroDebitoACobrarHistorico.ID,
										idDebitoACobrar));
						filtroDebitoACobrarHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoACobrarHistorico.IMOVEL);
						filtroDebitoACobrarHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoACobrarHistorico.DEBITO_TIPO);
						Collection<DebitoACobrarHistorico> colecaoDebitoACobrarHistorico = fachada.pesquisar(filtroDebitoACobrarHistorico,
										DebitoACobrarHistorico.class.getName());

						if(colecaoDebitoACobrarHistorico != null && !colecaoDebitoACobrarHistorico.isEmpty()){
							DebitoACobrarHistorico debitoACobrarHistorico = (DebitoACobrarHistorico) Util
											.retonarObjetoDeColecao(colecaoDebitoACobrarHistorico);
							DebitoACobrar debitoACobrar = new DebitoACobrar();
							debitoACobrar.setId(debitoACobrarHistorico.getId());
							debitoACobrar.setImovel(debitoACobrarHistorico.getImovel());
							debitoACobrar.setDebitoTipo(debitoACobrarHistorico.getDebitoTipo());
							debitoACobrar.setValorDebito(debitoACobrarHistorico.getValorDebito());
							debitoACobrar.setNumeroPrestacaoDebito(debitoACobrarHistorico.getPrestacaoDebito());
							debitoACobrar.setNumeroPrestacaoCobradas(debitoACobrarHistorico.getPrestacaoCobradas());
							pagamento.setDebitoACobrar(debitoACobrar);
						}
						preencherColecaoDePagamentosParaAutenticar(colecaoPagamentosAutenticar, pagamento);
					}
					colecaoPagamentosImovelDebitoACobrar.add(pagamento);
					vlPagDebitoACobrarAtual = vlPagDebitoACobrarAtual.add(pagamento.getValorPagamento());
				}else if(pagamento.getDocumentoTipo().getId().equals(DocumentoTipo.GUIA_PAGAMENTO)){
					GuiaPagamentoGeral guiaPagamentoGeral = pagamento.getGuiaPagamentoGeral();

					if(guiaPagamentoGeral != null){
						Integer idGuiaPagamentoGeral = guiaPagamentoGeral.getId();

						FiltroGuiaPagamentoGeral filtroGuiaPagamentoGeral = new FiltroGuiaPagamentoGeral();
						filtroGuiaPagamentoGeral
										.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoGeral.ID, idGuiaPagamentoGeral));
						filtroGuiaPagamentoGeral.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoGeral.GUIA_PAGAMENTO);
						filtroGuiaPagamentoGeral
										.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoGeral.GUIA_PAGAMENTO_HISTORICO);
						filtroGuiaPagamentoGeral
										.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoGeral.PRESTACOES_GUIA_PAGAMENTO);
						filtroGuiaPagamentoGeral
										.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoGeral.PRESTACOES_HISTORICO_GUIA_PAGAMENTO);
						filtroGuiaPagamentoGeral
										.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoGeral.PRESTACOES_HISTORICO_GUIA_PAGAMENTO_HISTORICO);

						Collection<GuiaPagamentoGeral> colecaoGuiaPagamentoGeral = fachada.pesquisar(filtroGuiaPagamentoGeral,
										GuiaPagamentoGeral.class.getName());

						if(colecaoGuiaPagamentoGeral != null && !colecaoGuiaPagamentoGeral.isEmpty()){
							guiaPagamentoGeral = (GuiaPagamentoGeral) Util.retonarObjetoDeColecao(colecaoGuiaPagamentoGeral);
							pagamento.setGuiaPagamentoGeral(guiaPagamentoGeral);

							colecaoPagamentosImovelGuiaPagamento.add(pagamento);
							vlPagGuiaPagamentoAtual = vlPagGuiaPagamentoAtual.add(pagamento.getValorPagamento());
						}
						preencherColecaoDePagamentosParaAutenticar(colecaoPagamentosAutenticar, pagamento);
					}
				}
			}

			// Organizar a coleção de Conta
			if(colecaoPagamentosImovelConta != null && !colecaoPagamentosImovelConta.isEmpty()){
				Collections.sort((List) colecaoPagamentosImovelConta, new Comparator() {

					public int compare(Object a, Object b){

						Integer anoMesReferencia1 = ((Pagamento) a).getAnoMesReferenciaPagamento() == null ? 0 : ((Pagamento) a)
										.getAnoMesReferenciaPagamento();
						Integer anoMesReferencia2 = ((Pagamento) b).getAnoMesReferenciaPagamento() == null ? 0 : ((Pagamento) a)
										.getAnoMesReferenciaPagamento();

						return anoMesReferencia1.compareTo(anoMesReferencia2);

					}
				});
				httpServletRequest.setAttribute("colecaoPagamentosImovelConta", colecaoPagamentosImovelConta);
				qtdePagContasAtual = colecaoPagamentosImovelConta.size();
				qtdePagContas = colecaoPagamentosImovelConta.size();
			}

			// Organizar a coleção de Guia de Pagamento
			if(colecaoPagamentosImovelGuiaPagamento != null && !colecaoPagamentosImovelGuiaPagamento.isEmpty()){
				Collections.sort((List) colecaoPagamentosImovelGuiaPagamento, new Comparator() {

					public int compare(Object a, Object b){

						String tipoDebito1 = ((Pagamento) a).getDebitoTipo() == null ? "" : ((Pagamento) a).getDebitoTipo().getDescricao();
						String tipoDebito2 = ((Pagamento) b).getDebitoTipo() == null ? "" : ((Pagamento) b).getDebitoTipo().getDescricao();

						return tipoDebito1.compareTo(tipoDebito2);

					}
				});
				httpServletRequest.setAttribute("colecaoPagamentosImovelGuiaPagamento", colecaoPagamentosImovelGuiaPagamento);

				qtdePagGuiaPagamentoAtual = colecaoPagamentosImovelGuiaPagamento.size();
				qtdePagGuiaPagamento = colecaoPagamentosImovelGuiaPagamento.size();
			}

			// Organizar a coleção de Débito a Cobrar
			if(colecaoPagamentosImovelDebitoACobrar != null && !colecaoPagamentosImovelDebitoACobrar.isEmpty()){

				// Organizar a coleção
				Collections.sort((List) colecaoPagamentosImovelDebitoACobrar, new Comparator() {

					public int compare(Object a, Object b){

						Integer anoMesReferencia1 = ((Pagamento) a).getAnoMesReferenciaPagamento() == null ? 0 : ((Pagamento) a)
										.getAnoMesReferenciaPagamento();
						Integer anoMesReferencia2 = ((Pagamento) b).getAnoMesReferenciaPagamento() == null ? 0 : ((Pagamento) b)
										.getAnoMesReferenciaPagamento();

						return anoMesReferencia1.compareTo(anoMesReferencia2);

					}
				});
				httpServletRequest.setAttribute("colecaoPagamentosImovelDebitoACobrar", colecaoPagamentosImovelDebitoACobrar);

				qtdePagDebitoACobrarAtual = colecaoPagamentosImovelDebitoACobrar.size();
				qtdePagDebitoACobrar = colecaoPagamentosImovelDebitoACobrar.size();
			}

			// Pesquisa os Clientes do Imovel

			Collection colecaoClienteImovel = fachada.pesquisarClientesImoveisPagamento(imovel.getId());

			if(colecaoClienteImovel != null && !colecaoClienteImovel.isEmpty()){
				httpServletRequest.setAttribute("colecaoClienteImovel", colecaoClienteImovel);
			}
		}

		// Consultar Pagamentos HISTORICOS do Imóvel
		if(sessao.getAttribute("colecaoImoveisPagamentosHistorico") != null){

			retorno = actionMapping.findForward("consultarImovelPagamentos");

			Collection<PagamentoHistorico> colecaoImoveisPagamentosHistorico = (Collection) sessao
							.getAttribute("colecaoImoveisPagamentosHistorico");

			sessao.removeAttribute("colecaoImoveisPagamentosHistorico");

			Imovel imovel = (Imovel) sessao.getAttribute("imovel");

			consultarPagamentoActionForm.setInscricao(imovel.getInscricaoFormatada());

			if(imovel.getLigacaoAguaSituacao() != null){
				consultarPagamentoActionForm.setSituacaoLigacaoAgua(imovel.getLigacaoAguaSituacao().getDescricao());
			}
			if(imovel.getLigacaoEsgotoSituacao() != null){
				consultarPagamentoActionForm.setSituacaoLigacaoEsgoto(imovel.getLigacaoEsgotoSituacao().getDescricao());
			}
			Iterator colecaoPagamentoHistoricoIterator = colecaoImoveisPagamentosHistorico.iterator();

			// Divide os pagamentos do imóvel pelo tipo de pagamento
			while(colecaoPagamentoHistoricoIterator.hasNext()){
				PagamentoHistorico pagamentoHistorico = ((PagamentoHistorico) colecaoPagamentoHistoricoIterator.next());
				preencherColecaoDePagamentosParaAutenticar(colecaoPagamentosAutenticar, pagamentoHistorico);

				if(pagamentoHistorico.getDocumentoTipo().getId().equals(DocumentoTipo.CONTA)){
					colecaoPagamentosHistoricoImovelConta.add(pagamentoHistorico);

					vlPagContasHistorico = vlPagContasHistorico.add(pagamentoHistorico.getValorPagamento());

				}else if(pagamentoHistorico.getDocumentoTipo().getId().equals(DocumentoTipo.DEBITO_A_COBRAR)){
					colecaoPagamentosHistoricoImovelDebitoACobrar.add(pagamentoHistorico);

					vlPagDebitoACobrarHistorico = vlPagDebitoACobrarHistorico.add(pagamentoHistorico.getValorPagamento());

				}else if(pagamentoHistorico.getDocumentoTipo().getId().equals(DocumentoTipo.GUIA_PAGAMENTO)){
					colecaoPagamentosHistoricoImovelGuiaPagamento.add(pagamentoHistorico);

					vlPagGuiaPagamentoHistorico = vlPagGuiaPagamentoHistorico.add(pagamentoHistorico.getValorPagamento());
				}
			}

			// Organizar a coleção de Conta
			if(colecaoPagamentosHistoricoImovelConta != null && !colecaoPagamentosHistoricoImovelConta.isEmpty()){

				httpServletRequest.setAttribute("colecaoPagamentosHistoricoImovelConta", colecaoPagamentosHistoricoImovelConta);

				qtdePagContasHistorico = colecaoPagamentosHistoricoImovelConta.size();
				qtdePagContas = qtdePagContas + colecaoPagamentosHistoricoImovelConta.size();
			}

			// Organizar a coleção de Guia de Pagamento
			if(colecaoPagamentosHistoricoImovelGuiaPagamento != null && !colecaoPagamentosHistoricoImovelGuiaPagamento.isEmpty()){

				httpServletRequest.setAttribute("colecaoPagamentosHistoricoImovelGuiaPagamento",
								colecaoPagamentosHistoricoImovelGuiaPagamento);

				qtdePagGuiaPagamentoHistorico = colecaoPagamentosHistoricoImovelGuiaPagamento.size();
				qtdePagGuiaPagamento = qtdePagGuiaPagamento + colecaoPagamentosHistoricoImovelGuiaPagamento.size();
			}

			// Organizar a coleção de Débito a Cobrar
			if(colecaoPagamentosHistoricoImovelDebitoACobrar != null && !colecaoPagamentosHistoricoImovelDebitoACobrar.isEmpty()){

				httpServletRequest.setAttribute("colecaoPagamentosHistoricoImovelDebitoACobrar",
								colecaoPagamentosHistoricoImovelDebitoACobrar);

				qtdePagDebitoACobrarHistorico = colecaoPagamentosHistoricoImovelDebitoACobrar.size();
				qtdePagDebitoACobrar = qtdePagDebitoACobrar + colecaoPagamentosHistoricoImovelDebitoACobrar.size();
			}
		}

		// Consultar o Pagamento do CLIENTE
		if(sessao.getAttribute("colecaoPagamentosClienteConta") != null
						|| sessao.getAttribute("colecaoPagamentosClienteGuiaPagamento") != null
						|| sessao.getAttribute("colecaoPagamentosClienteDebitoACobrar") != null){

			retorno = actionMapping.findForward("consultarClientePagamentos");

			colecaoPagamentosClienteConta = (Collection) sessao.getAttribute("colecaoPagamentosClienteConta");
			colecaoPagamentosClienteGuiaPagamento = (Collection) sessao.getAttribute("colecaoPagamentosClienteGuiaPagamento");
			colecaoPagamentosClienteDebitoACobrar = (Collection) sessao.getAttribute("colecaoPagamentosClienteDebitoACobrar");

			sessao.removeAttribute("colecaoPagamentosClienteConta");
			sessao.removeAttribute("colecaoPagamentosClienteGuiaPagamento");
			sessao.removeAttribute("colecaoPagamentosClienteDebitoACobrar");

			String idCliente = (String) consultarPagamentoActionForm.getIdCliente();

			Cliente cliente = fachada.pesquisarClientePagamento(Integer.valueOf(idCliente));

			// Pesquisa o endereço de correspondência do cliente pelo seu id
			ClienteEndereco clienteEndereco = fachada.pesquisarClienteEnderecoPagamento(cliente.getId());

			if(clienteEndereco != null){
				sessao.setAttribute("clienteEndereco", clienteEndereco);
			}

			// Pesquisa o telefone padrão do cliente pelo seu id
			ClienteFone clienteFone = fachada.pesquisarClienteFonePagamento(cliente.getId());

			if(clienteFone != null){
				sessao.setAttribute("clienteFone", clienteFone);
			}

			consultarPagamentoActionForm.setNomeCliente(cliente.getNome());

			if(cliente.getClienteTipo() == null || cliente.getClienteTipo().getIndicadorPessoaFisicaJuridica() == null){
				consultarPagamentoActionForm.setCpfCnpj("NÃO INFORMADO");
			}else{
				if(cliente.getClienteTipo().getIndicadorPessoaFisicaJuridica().equals(ClienteTipo.INDICADOR_PESSOA_FISICA)){
					consultarPagamentoActionForm.setCpfCnpj(cliente.getCpfFormatado());
					consultarPagamentoActionForm.setProfissao(cliente.getProfissao() == null ? "" : cliente.getProfissao().getDescricao());
				}else{
					consultarPagamentoActionForm.setCpfCnpj(cliente.getCnpjFormatado());
					consultarPagamentoActionForm.setRamoAtividade(cliente.getRamoAtividade() == null ? "" : cliente.getRamoAtividade()
									.getDescricao());
				}
			}

			if(colecaoPagamentosClienteConta != null){
				httpServletRequest.setAttribute("colecaoPagamentosClienteConta", colecaoPagamentosClienteConta);

				qtdePagContasAtual = colecaoPagamentosClienteConta.size();
				qtdePagContas = colecaoPagamentosClienteConta.size();

				Iterator iteratorPagamentosClienteConta = colecaoPagamentosClienteConta.iterator();

				while(iteratorPagamentosClienteConta.hasNext()){
					Pagamento pagamento = (Pagamento) iteratorPagamentosClienteConta.next();
					preencherColecaoDePagamentosParaAutenticar(colecaoPagamentosAutenticar, pagamento);
					vlPagContasAtual = vlPagContasAtual.add(pagamento.getValorPagamento());
				}
			}
			if(colecaoPagamentosClienteGuiaPagamento != null){
				httpServletRequest.setAttribute("colecaoPagamentosClienteGuiaPagamento", colecaoPagamentosClienteGuiaPagamento);

				qtdePagGuiaPagamentoAtual = colecaoPagamentosClienteGuiaPagamento.size();
				qtdePagGuiaPagamento = colecaoPagamentosClienteGuiaPagamento.size();

				Iterator iteratorPagamentosClienteGuiaPagamento = colecaoPagamentosClienteGuiaPagamento.iterator();

				while(iteratorPagamentosClienteGuiaPagamento.hasNext()){
					Pagamento pagamento = (Pagamento) iteratorPagamentosClienteGuiaPagamento.next();
					preencherColecaoDePagamentosParaAutenticar(colecaoPagamentosAutenticar, pagamento);
					vlPagGuiaPagamentoAtual = vlPagGuiaPagamentoAtual.add(pagamento.getValorPagamento());
				}
			}
			if(colecaoPagamentosClienteDebitoACobrar != null){
				httpServletRequest.setAttribute("colecaoPagamentosClienteDebitoACobrar", colecaoPagamentosClienteDebitoACobrar);

				qtdePagDebitoACobrarAtual = colecaoPagamentosClienteDebitoACobrar.size();
				qtdePagDebitoACobrar = colecaoPagamentosClienteDebitoACobrar.size();

				Iterator iteratorPagamentosClienteDebitoACobrar = colecaoPagamentosClienteDebitoACobrar.iterator();

				while(iteratorPagamentosClienteDebitoACobrar.hasNext()){
					Pagamento pagamento = (Pagamento) iteratorPagamentosClienteDebitoACobrar.next();
					preencherColecaoDePagamentosParaAutenticar(colecaoPagamentosAutenticar, pagamento);
					vlPagDebitoACobrarAtual = vlPagDebitoACobrarAtual.add(pagamento.getValorPagamento());
				}
			}
		}

		// Consultar o Pagamento Historico do CLIENTE
		if(sessao.getAttribute("colecaoPagamentosHistoricoClienteConta") != null
						|| sessao.getAttribute("colecaoPagamentosHistoricoClienteGuiaPagamento") != null
						|| sessao.getAttribute("colecaoPagamentosHistoricoClienteDebitoACobrar") != null){

			retorno = actionMapping.findForward("consultarClientePagamentos");

			colecaoPagamentosHistoricoClienteConta = (Collection) sessao.getAttribute("colecaoPagamentosHistoricoClienteConta");
			colecaoPagamentosHistoricoClienteGuiaPagamento = (Collection) sessao
							.getAttribute("colecaoPagamentosHistoricoClienteGuiaPagamento");
			colecaoPagamentosHistoricoClienteDebitoACobrar = (Collection) sessao
							.getAttribute("colecaoPagamentosHistoricoClienteDebitoACobrar");

			sessao.removeAttribute("colecaoPagamentosHistoricoClienteConta");
			sessao.removeAttribute("colecaoPagamentosHistoricoClienteGuiaPagamento");
			sessao.removeAttribute("colecaoPagamentosHistoricoClienteDebitoACobrar");

			if(colecaoPagamentosHistoricoClienteConta != null){
				httpServletRequest.setAttribute("colecaoPagamentosHistoricoClienteConta", colecaoPagamentosHistoricoClienteConta);

				qtdePagContasHistorico = colecaoPagamentosHistoricoClienteConta.size();
				qtdePagContas = qtdePagContas + colecaoPagamentosHistoricoClienteConta.size();

				Iterator iteratorPagamentosHistoricoClienteConta = colecaoPagamentosHistoricoClienteConta.iterator();

				while(iteratorPagamentosHistoricoClienteConta.hasNext()){
					PagamentoHistorico pagamentoHistorico = (PagamentoHistorico) iteratorPagamentosHistoricoClienteConta.next();
					preencherColecaoDePagamentosParaAutenticar(colecaoPagamentosAutenticar, pagamentoHistorico);
					vlPagContasHistorico = vlPagContasHistorico.add(pagamentoHistorico.getValorPagamento());
				}
			}
			if(colecaoPagamentosHistoricoClienteGuiaPagamento != null){
				httpServletRequest.setAttribute("colecaoPagamentosHistoricoClienteGuiaPagamento",
								colecaoPagamentosHistoricoClienteGuiaPagamento);

				qtdePagGuiaPagamentoHistorico = colecaoPagamentosHistoricoClienteGuiaPagamento.size();
				qtdePagGuiaPagamento = qtdePagGuiaPagamento + colecaoPagamentosHistoricoClienteGuiaPagamento.size();

				Iterator iteratorPagamentosHistoricoClienteGuiaPagamento = colecaoPagamentosHistoricoClienteGuiaPagamento.iterator();

				while(iteratorPagamentosHistoricoClienteGuiaPagamento.hasNext()){
					PagamentoHistorico pagamentoHistorico = (PagamentoHistorico) iteratorPagamentosHistoricoClienteGuiaPagamento.next();
					preencherColecaoDePagamentosParaAutenticar(colecaoPagamentosAutenticar, pagamentoHistorico);
					vlPagGuiaPagamentoHistorico = vlPagGuiaPagamentoHistorico.add(pagamentoHistorico.getValorPagamento());
				}
			}
			if(colecaoPagamentosHistoricoClienteDebitoACobrar != null){
				httpServletRequest.setAttribute("colecaoPagamentosHistoricoClienteDebitoACobrar",
								colecaoPagamentosHistoricoClienteDebitoACobrar);

				qtdePagDebitoACobrarHistorico = colecaoPagamentosHistoricoClienteDebitoACobrar.size();
				qtdePagDebitoACobrar = qtdePagDebitoACobrar + colecaoPagamentosHistoricoClienteDebitoACobrar.size();

				Iterator iteratorPagamentosHistoricoClienteDebitoACobrar = colecaoPagamentosHistoricoClienteDebitoACobrar.iterator();

				while(iteratorPagamentosHistoricoClienteDebitoACobrar.hasNext()){
					PagamentoHistorico pagamentoHistorico = (PagamentoHistorico) iteratorPagamentosHistoricoClienteDebitoACobrar.next();
					preencherColecaoDePagamentosParaAutenticar(colecaoPagamentosAutenticar, pagamentoHistorico);
					vlPagDebitoACobrarHistorico = vlPagDebitoACobrarHistorico.add(pagamentoHistorico.getValorPagamento());
				}
			}

			if(sessao.getAttribute("colecaoPagamentosClienteConta") == null
							&& sessao.getAttribute("colecaoPagamentosClienteGuiaPagamento") == null
							&& sessao.getAttribute("colecaoPagamentosClienteDebitoACobrar") == null){
				String idCliente = (String) consultarPagamentoActionForm.getIdCliente();

				// Pesquisa o cliente
				Cliente cliente = fachada.pesquisarClientePagamento(Integer.valueOf(idCliente));

				// Pesquisa o endereço de correspondência do cliente pelo seu id
				ClienteEndereco clienteEndereco = fachada.pesquisarClienteEnderecoPagamento(cliente.getId());

				if(clienteEndereco != null){
					sessao.setAttribute("clienteEndereco", clienteEndereco);
				}

				// Pesquisa o telefone padrão do cliente pelo seu id
				ClienteFone clienteFone = fachada.pesquisarClienteFonePagamento(cliente.getId());

				if(clienteFone != null){
					sessao.setAttribute("clienteFone", clienteFone);
				}

				consultarPagamentoActionForm.setNomeCliente(cliente.getNome());

				if(cliente.getClienteTipo() == null || cliente.getClienteTipo().getIndicadorPessoaFisicaJuridica() == null){
					consultarPagamentoActionForm.setCpfCnpj("NÃO INFORMADO");
				}else{
					if(cliente.getClienteTipo().getIndicadorPessoaFisicaJuridica().equals(ClienteTipo.INDICADOR_PESSOA_FISICA)){
						consultarPagamentoActionForm.setCpfCnpj(cliente.getCpfFormatado());
						consultarPagamentoActionForm.setProfissao(cliente.getProfissao() == null ? "" : cliente.getProfissao()
										.getDescricao());
					}else{
						consultarPagamentoActionForm.setCpfCnpj(cliente.getCnpjFormatado());
						consultarPagamentoActionForm.setRamoAtividade(cliente.getRamoAtividade() == null ? "" : cliente.getRamoAtividade()
										.getDescricao());
					}
				}
			}
		}

		// Consultar Pagamento do AVISO BANCÁRIO
		if(colecaoPagamentosAvisoBancario != null && !colecaoPagamentosAvisoBancario.isEmpty()){

			retorno = actionMapping.findForward("consultarAvisoBancarioPagamentos");

			AvisoBancario avisoBancario = (AvisoBancario) sessao.getAttribute("avisoBancario");

			consultarPagamentoActionForm.setSequencialAviso(String.valueOf(avisoBancario.getNumeroSequencial()));

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			String dataLancamento = "";
			if(avisoBancario.getDataLancamento() != null && !avisoBancario.getDataLancamento().equals("")){
				dataLancamento = format.format(avisoBancario.getDataLancamento());
			}

			consultarPagamentoActionForm.setDataLancamento(dataLancamento);

			if(avisoBancario.getArrecadador() != null){
				consultarPagamentoActionForm.setIdArrecadador(String.valueOf(avisoBancario.getArrecadador().getCodigoAgente()));
				consultarPagamentoActionForm.setDescricaoArrecadador(avisoBancario.getArrecadador().getCliente().getNome());
			}
			if(avisoBancario.getIndicadorCreditoDebito() == AvisoBancario.INDICADOR_CREDITO.shortValue()){
				consultarPagamentoActionForm.setTipoAviso("CRÉDITO");
			}else{
				consultarPagamentoActionForm.setTipoAviso("DÉBITO");
			}

			Iterator colecaoPagamentoIterator = colecaoPagamentosAvisoBancario.iterator();

			while(colecaoPagamentoIterator.hasNext()){

				Pagamento pagamento = ((Pagamento) colecaoPagamentoIterator.next());
				preencherColecaoDePagamentosParaAutenticar(colecaoPagamentosAutenticar, pagamento);

				if(pagamento.getDocumentoTipo().getId().equals(DocumentoTipo.CONTA)){
					colecaoPagamentosAvisoBancarioConta.add(pagamento);

					vlPagContasAtual = vlPagContasAtual.add(pagamento.getValorPagamento());

				}else if(pagamento.getDocumentoTipo().getId().equals(DocumentoTipo.DEBITO_A_COBRAR)){
					colecaoPagamentosAvisoBancarioDebitoACobrar.add(pagamento);

					vlPagDebitoACobrarAtual = vlPagDebitoACobrarAtual.add(pagamento.getValorPagamento());
				}else if(pagamento.getDocumentoTipo().getId().equals(DocumentoTipo.GUIA_PAGAMENTO)){
					colecaoPagamentosAvisoBancarioGuiaPagamento.add(pagamento);

					vlPagGuiaPagamentoAtual = vlPagGuiaPagamentoAtual.add(pagamento.getValorPagamento());
				}

			}
			if(colecaoPagamentosAvisoBancarioConta != null){
				httpServletRequest.setAttribute("colecaoPagamentosAvisoBancarioConta", colecaoPagamentosAvisoBancarioConta);

				qtdePagContasAtual = colecaoPagamentosAvisoBancarioConta.size();
				qtdePagContas = colecaoPagamentosAvisoBancarioConta.size();
			}
			if(colecaoPagamentosAvisoBancarioGuiaPagamento != null){
				httpServletRequest.setAttribute("colecaoPagamentosAvisoBancarioGuiaPagamento", colecaoPagamentosAvisoBancarioGuiaPagamento);

				qtdePagGuiaPagamentoAtual = colecaoPagamentosAvisoBancarioGuiaPagamento.size();
				qtdePagGuiaPagamento = colecaoPagamentosAvisoBancarioGuiaPagamento.size();
			}
			if(colecaoPagamentosAvisoBancarioDebitoACobrar != null){
				httpServletRequest.setAttribute("colecaoPagamentosAvisoBancarioDebitoACobrar", colecaoPagamentosAvisoBancarioDebitoACobrar);

				qtdePagDebitoACobrarAtual = colecaoPagamentosAvisoBancarioDebitoACobrar.size();
				qtdePagDebitoACobrar = colecaoPagamentosAvisoBancarioDebitoACobrar.size();
			}
		}

		// Consultar Pagamento Historico do AVISO BANCÁRIO
		if(colecaoPagamentosHistoricoAvisoBancario != null && !colecaoPagamentosHistoricoAvisoBancario.isEmpty()){

			retorno = actionMapping.findForward("consultarAvisoBancarioPagamentos");

			AvisoBancario avisoBancario = (AvisoBancario) sessao.getAttribute("avisoBancario");

			consultarPagamentoActionForm.setSequencialAviso(String.valueOf(avisoBancario.getNumeroSequencial()));

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			String dataLancamento = "";
			if(avisoBancario.getDataLancamento() != null && !avisoBancario.getDataLancamento().equals("")){
				dataLancamento = format.format(avisoBancario.getDataLancamento());
			}

			consultarPagamentoActionForm.setDataLancamento(dataLancamento);

			if(avisoBancario.getArrecadador() != null){
				consultarPagamentoActionForm.setIdArrecadador(String.valueOf(avisoBancario.getArrecadador().getCodigoAgente()));
				consultarPagamentoActionForm.setDescricaoArrecadador(avisoBancario.getArrecadador().getCliente().getNome());
			}
			if(avisoBancario.getIndicadorCreditoDebito() == AvisoBancario.INDICADOR_CREDITO.shortValue()){
				consultarPagamentoActionForm.setTipoAviso("CRÉDITO");
			}else{
				consultarPagamentoActionForm.setTipoAviso("DÉBITO");
			}

			Iterator colecaoPagamentoHistoricoIterator = colecaoPagamentosHistoricoAvisoBancario.iterator();

			while(colecaoPagamentoHistoricoIterator.hasNext()){

				PagamentoHistorico pagamentoHistorico = ((PagamentoHistorico) colecaoPagamentoHistoricoIterator.next());

				preencherColecaoDePagamentosParaAutenticar(colecaoPagamentosAutenticar, pagamentoHistorico);

				if(pagamentoHistorico.getDocumentoTipo().getId().equals(DocumentoTipo.CONTA)){
					colecaoPagamentosHistoricoAvisoBancarioConta.add(pagamentoHistorico);

					vlPagContasHistorico = vlPagContasHistorico.add(pagamentoHistorico.getValorPagamento());
				}else if(pagamentoHistorico.getDocumentoTipo().getId().equals(DocumentoTipo.DEBITO_A_COBRAR)){
					colecaoPagamentosHistoricoAvisoBancarioDebitoACobrar.add(pagamentoHistorico);

					vlPagDebitoACobrarHistorico = vlPagDebitoACobrarHistorico.add(pagamentoHistorico.getValorPagamento());
				}else if(pagamentoHistorico.getDocumentoTipo().getId().equals(DocumentoTipo.GUIA_PAGAMENTO)){
					colecaoPagamentosHistoricoAvisoBancarioGuiaPagamento.add(pagamentoHistorico);

					vlPagGuiaPagamentoHistorico = vlPagGuiaPagamentoHistorico.add(pagamentoHistorico.getValorPagamento());
				}

			}
			if(colecaoPagamentosHistoricoAvisoBancarioConta != null){
				httpServletRequest.setAttribute("colecaoPagamentosHistoricoAvisoBancarioConta",
								colecaoPagamentosHistoricoAvisoBancarioConta);

				qtdePagContasHistorico = colecaoPagamentosHistoricoAvisoBancarioConta.size();
				qtdePagContas = qtdePagContas + colecaoPagamentosHistoricoAvisoBancarioConta.size();
			}
			if(colecaoPagamentosHistoricoAvisoBancarioGuiaPagamento != null){
				httpServletRequest.setAttribute("colecaoPagamentosHistoricoAvisoBancarioGuiaPagamento",
								colecaoPagamentosHistoricoAvisoBancarioGuiaPagamento);

				qtdePagGuiaPagamentoHistorico = colecaoPagamentosHistoricoAvisoBancarioGuiaPagamento.size();
				qtdePagGuiaPagamento = qtdePagGuiaPagamento + colecaoPagamentosHistoricoAvisoBancarioGuiaPagamento.size();
			}
			if(colecaoPagamentosHistoricoAvisoBancarioDebitoACobrar != null){
				httpServletRequest.setAttribute("colecaoPagamentosHistoricoAvisoBancarioDebitoACobrar",
								colecaoPagamentosHistoricoAvisoBancarioDebitoACobrar);

				qtdePagDebitoACobrarHistorico = colecaoPagamentosHistoricoAvisoBancarioDebitoACobrar.size();
				qtdePagDebitoACobrar = qtdePagDebitoACobrar + colecaoPagamentosHistoricoAvisoBancarioDebitoACobrar.size();
			}

		}

		if((colecaoPagamentosMovimentoArrecadador != null && !colecaoPagamentosMovimentoArrecadador.isEmpty())
						|| (colecaoPagamentosHistoricoMovimentoArrecadador != null && !colecaoPagamentosHistoricoMovimentoArrecadador
										.isEmpty())){
			ArrecadadorMovimento arrecadadorMovimento = (ArrecadadorMovimento) sessao.getAttribute("arrecadadorMovimento");
			if(arrecadadorMovimento != null){
				consultarPagamentoActionForm.setIdArrecadador(arrecadadorMovimento.getCodigoBanco().toString());
				consultarPagamentoActionForm.setDescricaoArrecadador(arrecadadorMovimento.getNomeBanco().toString());
				consultarPagamentoActionForm.setCodigoRemessa(arrecadadorMovimento.getCodigoRemessa().toString());
				consultarPagamentoActionForm.setIdentificacaoServico(arrecadadorMovimento.getDescricaoIdentificacaoServico());
				consultarPagamentoActionForm.setNumeroSequencial(arrecadadorMovimento.getNumeroSequencialArquivo().toString());
				consultarPagamentoActionForm.setDataGeracao(Util.formatarData(arrecadadorMovimento.getDataGeracao()));
				consultarPagamentoActionForm.setNumeroRegistrosMovimento(arrecadadorMovimento.getNumeroRegistrosMovimento().toString());
				consultarPagamentoActionForm.setValorTotalMovimento(arrecadadorMovimento.getValorTotalMovimento().toString());
				consultarPagamentoActionForm.setDataProcessamento(Util.formatarData(arrecadadorMovimento.getUltimaAlteracao()));
				consultarPagamentoActionForm.setHoraProcessamento(Util.formatarHoraSemData(arrecadadorMovimento.getUltimaAlteracao()));
			}
		}

		// Consultar Pagamento do MOVIMENTO ARRECADADOR
		if(colecaoPagamentosMovimentoArrecadador != null && !colecaoPagamentosMovimentoArrecadador.isEmpty()){

			retorno = actionMapping.findForward("consultarMovimentoArrecadadorPagamentos");

			Iterator colecaoPagamentoIterator = colecaoPagamentosMovimentoArrecadador.iterator();

			while(colecaoPagamentoIterator.hasNext()){

				Pagamento pagamento = ((Pagamento) colecaoPagamentoIterator.next());


				if(pagamento.getDocumentoTipo().getId().equals(DocumentoTipo.CONTA)){

					// se o pagamento estiver referenciando uma conta que esteja no historico,
					// haverá um erro.
					// TODO Modificar depois. Saulo Lima 23/01/09
					try{
						pagamento.getConta().getReferencia();
					}catch(Exception e){
						pagamento.setConta(null);

						Integer contaGeralId = fachada.pesquisarIdDaContaGeralNoPagamento(pagamento.getId());

						ContaHistorico contaHistorico = null;
						if(contaGeralId != null){
							FiltroContaGeral filtroContaGeral = new FiltroContaGeral();
							filtroContaGeral.adicionarParametro(new ParametroSimples(FiltroContaGeral.ID, contaGeralId));
							Collection<ContaGeral> colecaoContaGeral = fachada.pesquisar(filtroContaGeral, ContaGeral.class.getName());

							// Pega a ContaGeral
							ContaGeral contaGeral = null;
							if(colecaoContaGeral != null && !colecaoContaGeral.isEmpty()){
								contaGeral = (ContaGeral) Util.retonarObjetoDeColecao(colecaoContaGeral);

								// Verifica se está no Histórico
								if(contaGeral.getIndicadorHistorico() == ConstantesSistema.SIM.shortValue()){
									FiltroContaHistorico filtroContaHistorico = new FiltroContaHistorico();
									filtroContaHistorico.adicionarParametro(new ParametroSimples(FiltroContaHistorico.ID, contaGeral
													.getId()));

									Collection<ContaHistorico> colecaoContaHistorico = fachada.pesquisar(filtroContaHistorico,
													ContaHistorico.class.getName());

									if(colecaoContaHistorico != null && !colecaoContaHistorico.isEmpty()){
										contaHistorico = (ContaHistorico) Util.retonarObjetoDeColecao(colecaoContaHistorico);
									}
								}
							}
						}
						if(contaHistorico == null){
							FiltroContaHistorico filtroContaHistorico = new FiltroContaHistorico();
							filtroContaHistorico.adicionarParametro(new ParametroSimples(FiltroContaHistorico.ANO_MES_REFERENCIA, pagamento
											.getAnoMesReferenciaPagamento()));
							filtroContaHistorico.adicionarParametro(new ParametroSimples(FiltroContaHistorico.IMOVEL_ID, pagamento
											.getImovel().getId()));

							Collection<ContaHistorico> colecaoContaHistorico = fachada.pesquisar(filtroContaHistorico, ContaHistorico.class
											.getName());

							if(colecaoContaHistorico != null && !colecaoContaHistorico.isEmpty()){
								contaHistorico = (ContaHistorico) Util.retonarObjetoDeColecao(colecaoContaHistorico);
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
					// colecaoPagamentosImovelConta.add(pagamento);

					colecaoPagamentosMovimentoArrecadadorConta.add(pagamento);

					vlPagContasAtual = vlPagContasAtual.add(pagamento.getValorPagamento());
				}else if(pagamento.getDocumentoTipo().getId().equals(DocumentoTipo.DEBITO_A_COBRAR)){
					colecaoPagamentosMovimentoArrecadadorDebitoACobrar.add(pagamento);

					vlPagDebitoACobrarAtual = vlPagDebitoACobrarAtual.add(pagamento.getValorPagamento());
				}else if(pagamento.getDocumentoTipo().getId().equals(DocumentoTipo.GUIA_PAGAMENTO)){
					colecaoPagamentosMovimentoArrecadadorGuiaPagamento.add(pagamento);

					vlPagGuiaPagamentoAtual = vlPagGuiaPagamentoAtual.add(pagamento.getValorPagamento());
				}

				preencherColecaoDePagamentosParaAutenticar(colecaoPagamentosAutenticar, pagamento);

			}

			if(colecaoPagamentosMovimentoArrecadadorConta != null){
				httpServletRequest.setAttribute("colecaoPagamentosMovimentoArrecadadorConta", colecaoPagamentosMovimentoArrecadadorConta);

				qtdePagContasAtual = colecaoPagamentosMovimentoArrecadadorConta.size();
				qtdePagContas = colecaoPagamentosMovimentoArrecadadorConta.size();
			}
			if(colecaoPagamentosMovimentoArrecadadorDebitoACobrar != null){
				httpServletRequest.setAttribute("colecaoPagamentosMovimentoArrecadadorDebitoACobrar",
								colecaoPagamentosMovimentoArrecadadorDebitoACobrar);

				qtdePagGuiaPagamentoAtual = colecaoPagamentosMovimentoArrecadadorDebitoACobrar.size();
				qtdePagGuiaPagamento = colecaoPagamentosMovimentoArrecadadorDebitoACobrar.size();
			}
			if(colecaoPagamentosMovimentoArrecadadorGuiaPagamento != null){
				httpServletRequest.setAttribute("colecaoPagamentosMovimentoArrecadadorGuiaPagamento",
								colecaoPagamentosMovimentoArrecadadorGuiaPagamento);

				qtdePagDebitoACobrarAtual = colecaoPagamentosMovimentoArrecadadorGuiaPagamento.size();
				qtdePagDebitoACobrar = colecaoPagamentosMovimentoArrecadadorGuiaPagamento.size();
			}
		}

		// Consultar Pagamento Historico do MOVIMENTO ARRECADADOR
		if(colecaoPagamentosHistoricoMovimentoArrecadador != null && !colecaoPagamentosHistoricoMovimentoArrecadador.isEmpty()){

			retorno = actionMapping.findForward("consultarMovimentoArrecadadorPagamentos");

			Iterator colecaoPagamentoHistoricoIterator = colecaoPagamentosHistoricoMovimentoArrecadador.iterator();

			while(colecaoPagamentoHistoricoIterator.hasNext()){

				PagamentoHistorico pagamentoHistorico = ((PagamentoHistorico) colecaoPagamentoHistoricoIterator.next());

				if(pagamentoHistorico.getDocumentoTipo().getId().equals(DocumentoTipo.CONTA)){
					colecaoPagamentosHistoricoMovimentoArrecadadorConta.add(pagamentoHistorico);

					vlPagContasHistorico = vlPagContasHistorico.add(pagamentoHistorico.getValorPagamento());
				}else if(pagamentoHistorico.getDocumentoTipo().getId().equals(DocumentoTipo.DEBITO_A_COBRAR)){
					colecaoPagamentosHistoricoMovimentoArrecadadorDebitoACobrar.add(pagamentoHistorico);

					vlPagDebitoACobrarHistorico = vlPagDebitoACobrarHistorico.add(pagamentoHistorico.getValorPagamento());
				}else if(pagamentoHistorico.getDocumentoTipo().getId().equals(DocumentoTipo.GUIA_PAGAMENTO)){
					colecaoPagamentosHistoricoMovimentoArrecadadorGuiaPagamento.add(pagamentoHistorico);

					vlPagGuiaPagamentoHistorico = vlPagGuiaPagamentoHistorico.add(pagamentoHistorico.getValorPagamento());
				}

				preencherColecaoDePagamentosParaAutenticar(colecaoPagamentosAutenticar, pagamentoHistorico);

			}

			if(colecaoPagamentosHistoricoMovimentoArrecadadorConta != null){
				httpServletRequest.setAttribute("colecaoPagamentosHistoricoMovimentoArrecadadorConta",
								colecaoPagamentosHistoricoMovimentoArrecadadorConta);

				qtdePagContasHistorico = colecaoPagamentosHistoricoMovimentoArrecadadorConta.size();
				qtdePagContas = qtdePagContas + colecaoPagamentosHistoricoMovimentoArrecadadorConta.size();
			}
			if(colecaoPagamentosHistoricoMovimentoArrecadadorDebitoACobrar != null){
				httpServletRequest.setAttribute("colecaoPagamentosHistoricoMovimentoArrecadadorDebitoACobrar",
								colecaoPagamentosHistoricoMovimentoArrecadadorDebitoACobrar);

				qtdePagGuiaPagamentoHistorico = colecaoPagamentosHistoricoMovimentoArrecadadorDebitoACobrar.size();
				qtdePagGuiaPagamento = qtdePagGuiaPagamento + colecaoPagamentosHistoricoMovimentoArrecadadorDebitoACobrar.size();
			}
			if(colecaoPagamentosHistoricoMovimentoArrecadadorGuiaPagamento != null){
				httpServletRequest.setAttribute("colecaoPagamentosHistoricoMovimentoArrecadadorGuiaPagamento",
								colecaoPagamentosHistoricoMovimentoArrecadadorGuiaPagamento);

				qtdePagDebitoACobrarHistorico = colecaoPagamentosHistoricoMovimentoArrecadadorGuiaPagamento.size();
				qtdePagDebitoACobrar = qtdePagDebitoACobrar + colecaoPagamentosHistoricoMovimentoArrecadadorGuiaPagamento.size();
			}
		}

		sessao.setAttribute("qtdePagContas", qtdePagContas);
		sessao.setAttribute("qtdePagContasAtual", qtdePagContasAtual);
		sessao.setAttribute("qtdePagContasHistorico", qtdePagContasHistorico);

		sessao.setAttribute("qtdePagGuiaPagamento", qtdePagGuiaPagamento);
		sessao.setAttribute("qtdePagGuiaPagamentoAtual", qtdePagGuiaPagamentoAtual);
		sessao.setAttribute("qtdePagGuiaPagamentoHistorico", qtdePagGuiaPagamentoHistorico);

		sessao.setAttribute("qtdePagDebitoACobrar", qtdePagDebitoACobrar);
		sessao.setAttribute("qtdePagDebitoACobrarAtual", qtdePagDebitoACobrarAtual);
		sessao.setAttribute("qtdePagDebitoACobrarHistorico", qtdePagDebitoACobrarHistorico);

		sessao.setAttribute("vlPagContasAtual", vlPagContasAtual);
		sessao.setAttribute("vlPagContasHistorico", vlPagContasHistorico);
		sessao.setAttribute("vlPagGuiaPagamentoAtual", vlPagGuiaPagamentoAtual);
		sessao.setAttribute("vlPagGuiaPagamentoHistorico", vlPagGuiaPagamentoHistorico);
		sessao.setAttribute("vlPagDebitoACobrarAtual", vlPagDebitoACobrarAtual);
		sessao.setAttribute("vlPagDebitoACobrarHistorico", vlPagDebitoACobrarHistorico);

		if(colecaoPagamentosAutenticar.isEmpty()){
			colecaoPagamentosAutenticar = null;
		}
		sessao.setAttribute("colecaoPagamento", colecaoPagamentosAutenticar);

		return retorno;
	}

	/**
	 * Adiciona o Objeto (Pagamento ou PagamentoHistorico) na coleção a ser tratada pelo fluxo de
	 * autenticar pagamento.
	 * 
	 * @param colecaoPagamentosAutenticar
	 * @param pagamento
	 */
	private void preencherColecaoDePagamentosParaAutenticar(Collection colecaoPagamentosAutenticar, Object pagamento){

		AvisoBancario avisoBancario = null;
		if(pagamento instanceof Pagamento){
			avisoBancario = ((Pagamento) pagamento).getAvisoBancario();
		}else if(pagamento instanceof PagamentoHistorico){
			avisoBancario = ((PagamentoHistorico) pagamento).getAvisoBancario();
		}

		if(Util.isNaoNuloBrancoZero(avisoBancario) && Util.isNaoNuloBrancoZero(avisoBancario.getArrecadador())){
			if(avisoBancario.getArrecadador().getIndicadorCaixaEmpresa().equals(ConstantesSistema.SIM)){
				colecaoPagamentosAutenticar.add(pagamento);
			}
		}
	}
}