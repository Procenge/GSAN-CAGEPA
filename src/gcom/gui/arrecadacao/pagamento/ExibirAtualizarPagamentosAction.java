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

import gcom.arrecadacao.pagamento.*;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.FiltroDocumentoTipo;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaHistorico;
import gcom.faturamento.credito.CreditoOrigem;
import gcom.faturamento.credito.CreditoTipo;
import gcom.faturamento.credito.CreditoTipoOrigem;
import gcom.faturamento.credito.FiltroCreditoTipoOrigem;
import gcom.faturamento.debito.*;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

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
 * Action que inicializa a página de atualizar pagamentoa
 * 
 * @author Pedro Alexandre
 * @date 22/03/2006
 */
public class ExibirAtualizarPagamentosAction
				extends GcomAction {

	/**
	 * @author Pedro Alexandre
	 * @date 22/03/2006
	 * @authot Saulo Lima
	 * @date 05/11/2008
	 *       Adicionar o campo numeroPrestacao
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno para a página de atualizar pagamento
		final ActionForward retorno = actionMapping.findForward("atualizarPagamento");

		// Cria uma instância da fachada
		final Fachada fachada = Fachada.getInstancia();

		// Cria uma instância da sessão
		final HttpSession sessao = httpServletRequest.getSession(false);

		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy"/* ,Constantes.LOCALE_PT_BR */);
		Calendar dataCorrente = new GregorianCalendar();

		// Data Corrente
		httpServletRequest.setAttribute("dataAtual", formatoData.format(dataCorrente.getTime()));

		// Recupera o código do pagamento que vai ser atualizado
		// String codigoPagamento = httpServletRequest.getParameter("idRegistroAtualizacao");

		// Cria a variável que vai armazenar o pagamento para ser atualizado
		// Pagamento pagamento = (Pagamento) sessao.getAttribute("pagamento");

		// recupera o form de manter pagamentos
		ManterPagamentoActionForm manterPagamentoActionForm = (ManterPagamentoActionForm) actionForm;

		// Cria o filtro de tipo de documento, e seta no filtro quais os tipo de documentos
		// necessários
		// para pesquisar os tipos de documento de conta, guia de pagamento e débito a cobrar
		FiltroDocumentoTipo filtroDocumentoTipo = new FiltroDocumentoTipo();
		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.ID, DocumentoTipo.CONTA,
						ParametroSimples.CONECTOR_OR));
		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.ID, DocumentoTipo.GUIA_PAGAMENTO,
						ParametroSimples.CONECTOR_OR));
		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.ID, DocumentoTipo.DEBITO_A_COBRAR,
						ParametroSimples.CONECTOR_OR));
		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.ID, DocumentoTipo.CARTA_OPCAO_PARCELAMENTO));
		Collection<DocumentoTipo> colecaoDocumentoTipo = fachada.pesquisar(filtroDocumentoTipo, DocumentoTipo.class.getName());

		// [FS0002] - Verificar existência de dados
		// Caso a coleção de tipo de documento estiver nula ou vazia, levanta uma
		// exceção para o usuário indicando que nenhum tipo de documento está cadastrado
		// Caso contrário manda os tipos de documentos pesquisados pela sessão
		if(colecaoDocumentoTipo == null || colecaoDocumentoTipo.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Tipo de Documento");
		}else{
			sessao.setAttribute("colecaoDocumentoTipo", colecaoDocumentoTipo);
		}

		String idPagamento = null;
		if(httpServletRequest.getParameter("reloadPage") == null || httpServletRequest.getParameter("reloadPage").equals("")){
			// Recupera o id de Pagamento que vai ser atualizado
			if(httpServletRequest.getParameter("idRegistroInseridoAtualizar") != null){
				idPagamento = httpServletRequest.getParameter("idRegistroInseridoAtualizar");
				// Definindo a volta do botão Voltar p Filtrar Pagamento
				sessao.setAttribute("voltar", "filtrar");
				sessao.setAttribute("idRegistroAtualizacao", idPagamento);
			}else if(httpServletRequest.getParameter("idRegistroAtualizacao") == null){
				idPagamento = String.valueOf(sessao.getAttribute("idRegistroAtualizacao"));
				// Definindo a volta do botão Voltar p Filtrar Pagamento
				sessao.setAttribute("voltar", "filtrar");
			}else if(httpServletRequest.getParameter("idRegistroAtualizacao") != null){
				idPagamento = httpServletRequest.getParameter("idRegistroAtualizacao");
				// Definindo a volta do botão Voltar p Manter Pagamento
				sessao.setAttribute("voltar", "manter");
				sessao.setAttribute("idRegistroAtualizacao", idPagamento);
			}

			exibirPagamento(idPagamento, manterPagamentoActionForm, fachada, sessao, httpServletRequest);

		}else{
			idPagamento = (String) sessao.getAttribute("idRegistroAtualizacao");
		}

		if(httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer").equalsIgnoreCase("S")){

			// -------------- bt DESFAZER ---------------
			exibirPagamento(idPagamento, manterPagamentoActionForm, fachada, sessao, httpServletRequest);

		}else{

			// -------Parte que trata do código quando o usuário tecla enter
			// if (httpServletRequest.getParameter("reloadPage") != null &&
			// !httpServletRequest.getParameter("reloadPage").equals("")) {

			// [FS0003] - Verificar existência da localidade
			// Recupera o código da localidade digitado pelo usuário
			String codigoLocalidadeDigitadoEnter = (String) manterPagamentoActionForm.getIdLocalidade();

			// Caso o código da localidade informado não estiver vazio
			if(codigoLocalidadeDigitadoEnter != null && !codigoLocalidadeDigitadoEnter.trim().equalsIgnoreCase("")){

				// Recupera a localidade informada pelo usuário
				Localidade localidadeEncontrada = fachada.pesquisarLocalidadeDigitada(Integer.valueOf(codigoLocalidadeDigitadoEnter));

				// Caso a localidade informada pelo usuário esteja cadastrada no sistema Seta os
				// dados da localidade no form
				// Caso contrário seta as informações da localidade para vazio e indica ao usuário
				// que a localidade não existe
				if(localidadeEncontrada == null){
					manterPagamentoActionForm.setIdLocalidade("");
					httpServletRequest.setAttribute("idLocalidadeNaoEncontrada", "exception");
					manterPagamentoActionForm.setDescricaoLocalidade("Localidade inexistente");
				}else{
					manterPagamentoActionForm.setIdLocalidade(String.valueOf(localidadeEncontrada.getId()));
					manterPagamentoActionForm.setDescricaoLocalidade(localidadeEncontrada.getDescricao());
					httpServletRequest.setAttribute("idLocalidadeNaoEncontrada", "true");
				}
			}

			// [FS0004] - Verificar existência da matrícula do imóvel
			String codigoImovelDigitadoEnter = (String) manterPagamentoActionForm.getIdImovel();

			// Caso o código do imóvel informado não estiver vazio
			if(codigoImovelDigitadoEnter != null && !codigoImovelDigitadoEnter.trim().equalsIgnoreCase("")){

				// Recupera o imóvel informado pelo usuário
				Imovel imovelEncontrado = fachada.pesquisarImovelDigitado(Integer.valueOf(codigoImovelDigitadoEnter));

				// Caso o imóvel informado pelo usuário esteja cadastrado no sistema Seta os dados o
				// imóvel no form
				// Caso contrário seta as informações o imóvel para vazio e indica ao usuário que o
				// imóvel informado não existe
				if(imovelEncontrado == null){
					manterPagamentoActionForm.setIdImovel("");
					httpServletRequest.setAttribute("idImovelNaoEncontrado", "exception");
					manterPagamentoActionForm.setDescricaoImovel("Matrícula inexistente");
				}else{
					manterPagamentoActionForm.setIdImovel(String.valueOf(imovelEncontrado.getId()));
					manterPagamentoActionForm.setDescricaoImovel(String.valueOf(imovelEncontrado.getInscricaoFormatada()));
					httpServletRequest.setAttribute("idImovelNaoEncontrado", "true");

					// Recupera a localidade do imóvel,caso o mesmo exista na base
					Localidade localidadeImovel = imovelEncontrado.getLocalidade();

					// Caso o usuário tenha informado a localidade
					if(codigoLocalidadeDigitadoEnter == null || codigoLocalidadeDigitadoEnter.trim().equalsIgnoreCase("")){
						manterPagamentoActionForm.setIdLocalidade(String.valueOf(localidadeImovel.getId()));
						manterPagamentoActionForm.setDescricaoLocalidade(localidadeImovel.getDescricao());
						httpServletRequest.setAttribute("idLocalidadeNaoEncontrada", "true");
					}else{
						// [FS0005] - Verificar localidade da matrícula do imóvel
						if(!fachada.verificarLocalidadeMatriculaImovel(codigoLocalidadeDigitadoEnter, imovelEncontrado)){
							manterPagamentoActionForm.setIdImovel("");
							httpServletRequest.setAttribute("idImovelNaoEncontrado", "exception");
							manterPagamentoActionForm.setDescricaoImovel("A Localidade da Matrícula " + localidadeImovel.getId()
											+ " é diferente da localidade informada " + codigoLocalidadeDigitadoEnter);
						}
					}
				}
			}

			// [FS0007] - Verificar existência do código do cliente
			String codigoClienteDigitadoEnter = (String) manterPagamentoActionForm.getIdCliente();

			// Recupera a metrícula do imóvel e o código do cliente do form de manter pagamento
			String codigoImovel = codigoImovelDigitadoEnter;
			String codigoCliente = (String) manterPagamentoActionForm.getIdCliente();

			// Caso o usuário tenha informado o código do cliente
			if(codigoClienteDigitadoEnter != null && !codigoClienteDigitadoEnter.trim().equalsIgnoreCase("")){

				// Recupera o cliente ,caso o mesmo exista na base
				Cliente clienteEncontrado = fachada.pesquisarClienteDigitado(Integer.valueOf(codigoClienteDigitadoEnter));

				// Caso o cliente esteja cadastrado no sistema Seta no form todos os dados do
				// cliente
				// Caso contrário seta os dados do cliente para vazio e informa que o cliente não
				// existe
				if(clienteEncontrado == null){
					manterPagamentoActionForm.setIdCliente("");
					httpServletRequest.setAttribute("idClienteNaoEncontrado", "exception");
					manterPagamentoActionForm.setNomeCliente("Código inexistente");
				}else{
					manterPagamentoActionForm.setIdCliente(String.valueOf(clienteEncontrado.getId()));
					manterPagamentoActionForm.setNomeCliente(clienteEncontrado.getNome());
					httpServletRequest.setAttribute("idClienteNaoEncontrado", "true");
				}
			}

			// [FS0008] - Verificar existência da conta
			String referenciaContaDigitadoEnter = (String) manterPagamentoActionForm.getReferenciaConta();

			// Caso o usuário tenha informado a referência da conta
			if(referenciaContaDigitadoEnter != null && !referenciaContaDigitadoEnter.trim().equalsIgnoreCase("")){

				// Caso o usuário não tenha informado a matrícula do imóvel
				// Levanta uma exceção para o usuário indicado que ele não informou a matrícula do
				// imóvel
				if(codigoImovel == null || codigoImovel.trim().equalsIgnoreCase("")){

					throw new ActionServletException("atencao.naoinformado", null, "Imóvel");
				}

				// Recupera a conta do imóvel com a referência informada
				Conta contaEncontrada = null;
				
				// Verifica se o mês/ano é valido, pois em alguns casos
				// a referência é informada errada no movimento arrecadador
				if(Util.validarMesAno(referenciaContaDigitadoEnter)){
					contaEncontrada = fachada.pesquisarContaDigitada(codigoImovel, referenciaContaDigitadoEnter);	
				}

				// Caso a conta esteja cadastrada no sistema Seta todas as informações da conta no
				// form
				// Caso contrário seta as informações da conta para nulo e indica ao usuário que não
				// existe conta para o imóvel
				// informadocom a referência indicada
				if(contaEncontrada != null){
					manterPagamentoActionForm.setReferenciaConta(String.valueOf(referenciaContaDigitadoEnter));
					manterPagamentoActionForm.setDescricaoReferenciaConta(String.valueOf(contaEncontrada.getValorTotalConta()));
					// manterPagamentoActionForm.setValorPagamento("" +
					// contaEncontrada.getValorTotalConta());
					httpServletRequest.setAttribute("referenciaContaNaoEncontrada", "true");

				}else{
					ContaHistorico contaHistorico = null;

					if(Util.validarMesAno(referenciaContaDigitadoEnter)){
						contaHistorico = fachada.pesquisarContaHistoricoDigitada(codigoImovel, referenciaContaDigitadoEnter);
					}

					if(contaHistorico != null){
						// Essa verificação é necessária porque o objeto Pagamento está associado a
						// Conta e não a ContaGeral, mas a tabela PAGAMENTO está associada a
						// CONTA_GERAL. Nesse caso quando um pagameno que tinha uma conta em
						// histórico e era realizado a baixa forçada, o sistema estava transferindo
						// o pagamento sem o Id da Conta.

						manterPagamentoActionForm.setReferenciaConta(String.valueOf(referenciaContaDigitadoEnter));
						manterPagamentoActionForm.setDescricaoReferenciaConta(String.valueOf(contaHistorico.getValorTotal()));
						httpServletRequest.setAttribute("referenciaContaNaoEncontrada", "true");
					}else{
						manterPagamentoActionForm.setReferenciaConta(String.valueOf(referenciaContaDigitadoEnter));
						httpServletRequest.setAttribute("referenciaContaNaoEncontrada", "exception");
						manterPagamentoActionForm.setDescricaoReferenciaConta("Não há Conta com a referência "
										+ referenciaContaDigitadoEnter + " para o imóvel " + codigoImovel);
						// manterPagamentoActionForm.setValorPagamento("");
					}
				}
			}

			// [FS0022] - Verificar existência da guia de pagamento
			String codigoGuiaPagamentoDigitadoEnter = (String) manterPagamentoActionForm.getIdGuiaPagamento();
			String numeroPrestacao = (String) manterPagamentoActionForm.getNumeroPrestacao();

			// Caso o usuário tenha informado o código da guia de pagamento
			if(codigoGuiaPagamentoDigitadoEnter != null && !codigoGuiaPagamentoDigitadoEnter.trim().equalsIgnoreCase("")){

				// Caso o usuário não tenha informado a matrícula do imóvel
				// Levanta uma exceção para o usuário indicado que ele não informou a matrícula do
				// imóvel
				if((codigoImovel == null && codigoCliente == null)
								|| (codigoImovel.trim().equalsIgnoreCase("") && codigoCliente.trim().equalsIgnoreCase(""))){
					throw new ActionServletException("atencao.naoinformado", null, "Imóvel ou Cliente");
				}

				// [FS0025] - Verificar preenchimento da prestação - Parte 1
				// Caso tenha informado a guia e não informado o número da prestação
				if(numeroPrestacao == null || numeroPrestacao.trim().equals("")){
					throw new ActionServletException("atencao.informe_campo", null, "Número da Prestação");
				}

				// Pesquisa a guia de pagamento para o imóvel informado
				GuiaPagamento guiaPagamentoEncontrada = fachada.pesquisarGuiaPagamentoDigitada(codigoImovel, codigoCliente,
								codigoGuiaPagamentoDigitadoEnter);

				// Caso a guia de pagamento esteja cadastrada no sistema Seta os dados da guia de
				// pagamento no form
				// Caso contrário seta os dados da guia para nulo e informa ao usuário que não
				// existe guia de pagamento cadastrada no sistema
				if(guiaPagamentoEncontrada != null){
					FiltroGuiaPagamentoPrestacao filtroGuiaPagamentoPrestacao = new FiltroGuiaPagamentoPrestacao();
					filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacao.GUIA_PAGAMENTO_ID,
									guiaPagamentoEncontrada.getId()));
					filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacao.NUMERO_PRESTACAO,
									Integer.valueOf(numeroPrestacao)));
					filtroGuiaPagamentoPrestacao.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoPrestacao.DEBITO_TIPO);

					Collection<GuiaPagamentoPrestacao> colecaoGuiaPagamentoPrestacao = fachada.pesquisar(filtroGuiaPagamentoPrestacao,
									GuiaPagamentoPrestacao.class.getName());

					BigDecimal valorGuiaPrestacao = new BigDecimal("0.00");
					String descricao = "";
					if(colecaoGuiaPagamentoPrestacao != null && !colecaoGuiaPagamentoPrestacao.isEmpty()){
						// caso guia tenha apenas 1 "tipo de débito" pega-se o valor e a descrição
						if(colecaoGuiaPagamentoPrestacao.size() == 1){
							GuiaPagamentoPrestacao guiaPagamentoPrestacao = colecaoGuiaPagamentoPrestacao.iterator().next();
							valorGuiaPrestacao = guiaPagamentoPrestacao.getValorPrestacao();
							descricao = guiaPagamentoPrestacao.getDebitoTipo().getDescricao();
							// caso guia tenha mais de 1 "tipo de débito" pega-se a soma dos valores
						}else{
							Iterator<GuiaPagamentoPrestacao> iterator = colecaoGuiaPagamentoPrestacao.iterator();
							while(iterator.hasNext()){
								GuiaPagamentoPrestacao guiaPagamentoPrestacao = iterator.next();
								valorGuiaPrestacao = valorGuiaPrestacao.add(guiaPagamentoPrestacao.getValorPrestacao());
							}
						}
					}else{
						throw new ActionServletException("atencao.pesquisa_inexistente", null, "Guia de Pagamento");
					}

					manterPagamentoActionForm.setIdGuiaPagamento(String.valueOf(guiaPagamentoEncontrada.getId()));
					manterPagamentoActionForm.setDescricaoGuiaPagamento(descricao);
					manterPagamentoActionForm.setValorGuiaPagamento(String.valueOf(Util.formatarMoedaReal(valorGuiaPrestacao)));
					// manterPagamentoActionForm.setValorPagamento(Util.formatarMoedaReal(valorGuiaPrestacao));
					httpServletRequest.setAttribute("idGuiaPagamentoNaoEncontrado", "true");

					// Seta os dados da localidade
					manterPagamentoActionForm.setIdLocalidade(String.valueOf(guiaPagamentoEncontrada.getLocalidade().getId()));
					manterPagamentoActionForm.setDescricaoLocalidade(guiaPagamentoEncontrada.getLocalidade().getDescricao());
					httpServletRequest.setAttribute("idLocalidadeNaoEncontrada", "true");

					// Seta o número da prestação
					manterPagamentoActionForm.setNumeroPrestacao(numeroPrestacao);

				}else{
					manterPagamentoActionForm.setIdGuiaPagamento("");
					manterPagamentoActionForm.setDescricaoGuiaPagamento("Guia de Pagamento inexistente");
					manterPagamentoActionForm.setValorGuiaPagamento("");
					// manterPagamentoActionForm.setValorPagamento("");
					httpServletRequest.setAttribute("idGuiaPagamentoNaoEncontrado", "exception");
				}
			}else{
				String debitoACobrarTMP = (String) manterPagamentoActionForm.getIdDebitoACobrar();
				if(debitoACobrarTMP == null || debitoACobrarTMP.equals("")){
					// [FS0025] - Verificar preenchimento da prestação - Parte 2
					// Caso não tenha informado a guia mas tenha informado a prestação
					if(numeroPrestacao != null && !numeroPrestacao.trim().equals("")){
						throw new ActionServletException("atencao.numero_prestacao.sem.guia_pagamento");
					}
				}
			}

			// [FS0024] - Verificar existência do débito a cobrar
			String codigoDebitoACobrarDigitadoEnter = (String) manterPagamentoActionForm.getIdDebitoACobrar();

			// Caso o usuário tenha informado o código do débito a cobrar
			if(codigoDebitoACobrarDigitadoEnter != null && !codigoDebitoACobrarDigitadoEnter.trim().equalsIgnoreCase("")){

				// Caso o usuário não tenha informado a matrícula do imóvel
				// Levanta uma exceção para o usuário indicado que ele não informou a matrícula do
				// imóvel
				if(codigoImovel == null || codigoImovel.trim().equalsIgnoreCase("")){
					throw new ActionServletException("atencao.naoinformado", null, "Imóvel");
				}

				DebitoACobrar debitoACobrarEncontrado = null;

				try{

					// Pesquisa o débito a cobrar para o imóvel informado
					debitoACobrarEncontrado = fachada.pesquisarDebitoACobrarDigitado(codigoImovel, codigoDebitoACobrarDigitadoEnter);

					// Caso o débito a cobrar esteja cadastrado no sistema Seta os dados do débito a
					// cobrar no form
					// Caso contrário seta os dados do débito para nulo e informa ao usuário que não
					// existe débito a cobrar cadastrado no sistema
					manterPagamentoActionForm.setIdDebitoACobrar(String.valueOf(debitoACobrarEncontrado.getId()));
					manterPagamentoActionForm.setDescricaoDebitoACobrar(String.valueOf(debitoACobrarEncontrado.getDebitoTipo()
									.getDescricao()));
					manterPagamentoActionForm.setValorDebitoACobrar(String.valueOf(debitoACobrarEncontrado.getValorDebito()));
					httpServletRequest.setAttribute("idDebitoACobrarNaoEncontrado", "true");

				}catch(Exception e){

				}

				if(debitoACobrarEncontrado == null){

					DebitoACobrarHistorico debitoACobrarDigitadoH = null;

					// Cria o filtro de débito a cobrar e seta todos os parâmetros para
					// pesquisar o débito a cobrar do imóvel
					FiltroDebitoACobrarHistorico filtroDebitoACobrarHistorico = new FiltroDebitoACobrarHistorico();
					filtroDebitoACobrarHistorico.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.IMOVEL_ID, codigoImovel));
					filtroDebitoACobrarHistorico.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.ID,
									codigoDebitoACobrarDigitadoEnter));
					filtroDebitoACobrarHistorico.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");
					filtroDebitoACobrarHistorico.adicionarCaminhoParaCarregamentoEntidade("localidade");
					filtroDebitoACobrarHistorico.adicionarCaminhoParaCarregamentoEntidade("imovel");
					//						
					Collection colecaoDebitoACobrar = fachada.pesquisar(filtroDebitoACobrarHistorico, DebitoACobrarHistorico.class
									.getName());
					if(colecaoDebitoACobrar == null || colecaoDebitoACobrar.isEmpty()){

						manterPagamentoActionForm.setIdDebitoACobrar("");
						manterPagamentoActionForm.setDescricaoDebitoACobrar("Débito a Cobrar inexistente");
						manterPagamentoActionForm.setDescricaoDebitoACobrar("");
						httpServletRequest.setAttribute("idDebitoACobrarNaoEncontrado", "exception");

					}else{
						debitoACobrarDigitadoH = (DebitoACobrarHistorico) Util.retonarObjetoDeColecao(colecaoDebitoACobrar);

						manterPagamentoActionForm.setIdDebitoACobrar(String.valueOf(debitoACobrarDigitadoH.getId()));
						manterPagamentoActionForm.setDescricaoDebitoACobrar(String.valueOf(debitoACobrarDigitadoH.getDebitoTipo()
										.getDescricao()));
						manterPagamentoActionForm.setValorDebitoACobrar(String.valueOf(debitoACobrarDigitadoH.getValorDebito()));
						httpServletRequest.setAttribute("idDebitoACobrarNaoEncontrado", "true");
					}
				}
			}

			// }

			// [FS0020] - Verificar existência do tipo de débito
			String codigoTipoDebitoDigitadoEnter = (String) manterPagamentoActionForm.getIdTipoDebito();

			// Caso o usuário tenha informado o código do tipo de débito
			if(codigoTipoDebitoDigitadoEnter != null && !codigoTipoDebitoDigitadoEnter.trim().equalsIgnoreCase("")){

				// Recupera o tipo de débito ,caso o mesmo exista na base
				DebitoTipo tipoDebitoEncontrado = fachada.pesquisarTipoDebitoDigitado(Integer.valueOf(codigoTipoDebitoDigitadoEnter));

				// Caso o tipo de débito esteja cadastrado no sistema Seta no form todos os dados do
				// tipo de débito
				// Caso contrário seta os dados do tipo de débito para vazio e informa que o tipo de
				// débito não existe
				if(tipoDebitoEncontrado != null){
					manterPagamentoActionForm.setIdTipoDebito(String.valueOf(tipoDebitoEncontrado.getId()));
					manterPagamentoActionForm.setDescricaoTipoDebito(tipoDebitoEncontrado.getDescricao());
					httpServletRequest.setAttribute("idTipoDebitoNaoEncontrado", "true");

				}else{
					manterPagamentoActionForm.setIdTipoDebito("");
					httpServletRequest.setAttribute("idTipoDebitoNaoEncontrado", "exception");
					manterPagamentoActionForm.setDescricaoTipoDebito("Tipo de Débito inexistente");
				}
			}

			// }
		}
		// -------Parte que trata do código quando o usuário tecla enter

		// Seta na sessão o form de pagamento e o pagamento que vai ser atualizado
		sessao.setAttribute("ManterPagamentoActionForm", manterPagamentoActionForm);
		// sessao.setAttribute("pagamento", pagamento);
		
		// Retorna o mapeamento contido na variável retorno
		return retorno;
	}

	private void exibirPagamento(String idPagamento, ManterPagamentoActionForm manterPagamentoActionForm, Fachada fachada,
					HttpSession sessao, HttpServletRequest httpServletRequest){

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		Pagamento pagamento = null;
		PagamentoSituacao pagamentoSituacaoAtual = null;

		if(idPagamento != null && !idPagamento.equalsIgnoreCase("")){

			// Cria o filtro de pagamento e seta o código do pagamento para ser atualizado no filtro
			// e indica quais objetos devem ser retornados pela pesquisa
			FiltroPagamento filtroPagamento = new FiltroPagamento();
			filtroPagamento.adicionarParametro(new ParametroSimples(FiltroPagamento.ID, idPagamento));

			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("documentoTipo");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("localidade");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.localidade");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.quadra");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("cliente");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("avisoBancario");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("arrecadacaoForma");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("avisoBancario.arrecadador.cliente");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("conta");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("guiaPagamentoGeral");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("debitoACobrar");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("debitoACobrar.debitoTipo");
			filtroPagamento.adicionarCaminhoParaCarregamentoEntidade("pagamentoSituacaoAtual");

			// Pesquisa o pagamento no sistema com os parâmetros informados no filtro
			Collection colecaoPagamentos = fachada.pesquisar(filtroPagamento, Pagamento.class.getName());

			// Caso a pesquisa tenha retornado o pagamento
			if(colecaoPagamentos != null && !colecaoPagamentos.isEmpty()){
				// Recupera da coleção o pagamento que vai ser atualizado
				pagamento = (Pagamento) Util.retonarObjetoDeColecao(colecaoPagamentos);

				// Cria a variável que vai armazenar a coleção de situações atuais de pagamento
				Collection<PagamentoSituacao> colecaoSituacaoAtualPagamento = null;

				// Recupera a situação atual e anterior do pagamento
				pagamentoSituacaoAtual = pagamento.getPagamentoSituacaoAtual();

				// Caso a situação atual do pagamento esteja preenchida
				if(pagamentoSituacaoAtual != null){
					System.out.println(pagamentoSituacaoAtual.getId());

					// Caso a situação atual do pagamento seja igual a "Fatura
					// Inexistente" ou "Pagamento em Duplicidade" ou igual a valor em excesso
					if(pagamentoSituacaoAtual.getId().equals(PagamentoSituacao.FATURA_INEXISTENTE)
									|| pagamentoSituacaoAtual.getId().equals(PagamentoSituacao.PAGAMENTO_EM_DUPLICIDADE)
									|| pagamentoSituacaoAtual.getId().equals(PagamentoSituacao.VALOR_EM_EXCESSO)
									|| pagamentoSituacaoAtual.getId().equals(PagamentoSituacao.PAGAMENTO_DUPLICADO)
									|| pagamentoSituacaoAtual.getId().equals(PagamentoSituacao.PAGAMENTO_A_MAIOR)
									|| pagamentoSituacaoAtual.getId().equals(PagamentoSituacao.PAGAMENTO_A_MENOR)
									|| pagamentoSituacaoAtual.getId().equals(PagamentoSituacao.PAGAMENTO_DOC_PAGO)
									|| pagamentoSituacaoAtual.getId().equals(PagamentoSituacao.PAGAMENTO_DOC_CANCELADO)
									|| pagamentoSituacaoAtual.getId().equals(PagamentoSituacao.PAGAMENTO_DOC_PARCELADO)

					){

						// Cria o filtro de situação de pagamento e seta no filtro para retornar
						// somente a situação igual a valor a baixar
						FiltroPagamentoSituacao filtroPagamentoSituacao = new FiltroPagamentoSituacao();
						filtroPagamentoSituacao.adicionarParametro(new ParametroSimples(FiltroPagamentoSituacao.CODIGO,
										PagamentoSituacao.VALOR_A_BAIXAR));
						colecaoSituacaoAtualPagamento = fachada.pesquisar(filtroPagamentoSituacao, PagamentoSituacao.class.getName());

						// [FS0002] - Verificar existência de dados Caso a situação de valor a
						// baixar não esteja
						// cadastrada no sistema levante uma exceção para o usuário indicando que a
						// situação não está cadastrada
						if(colecaoSituacaoAtualPagamento == null || colecaoSituacaoAtualPagamento.isEmpty()){
							throw new ActionServletException("atencao.naocadastrado", null, "Situação de Pagamento");
						}
					}
				}

				// Seta na sessãoa coleção de situação de pagamentos, para o campo de situação de
				// pagamento atual
				sessao.setAttribute("colecaoSituacaoAtualPagamento", colecaoSituacaoAtualPagamento);

				// Seta no form os dados de aviso bancário
				manterPagamentoActionForm.setIdAvisoBancario(String.valueOf(pagamento.getAvisoBancario().getId()));
				if(pagamento.getAvisoBancario().getArrecadador().getIndicadorCaixaEmpresa().equals(ConstantesSistema.SIM)){
					manterPagamentoActionForm.setHabilitaBotaoAutenticarPagamento(ConstantesSistema.SIM.toString());
				}else{
					manterPagamentoActionForm.setHabilitaBotaoAutenticarPagamento(ConstantesSistema.NAO.toString());
				}
				manterPagamentoActionForm.setCodigoAgenteArrecadador(String.valueOf(pagamento.getAvisoBancario().getArrecadador()
								.getCodigoAgente()));
				manterPagamentoActionForm.setDataLancamentoAviso(Util.formatarData(pagamento.getAvisoBancario().getDataLancamento()));
				manterPagamentoActionForm.setNumeroSequencialAviso(String.valueOf(pagamento.getAvisoBancario().getNumeroSequencial()));

				// Seta no form os dados de arrecadação
				if(pagamento.getArrecadacaoForma() != null){
					manterPagamentoActionForm.setIdFormaArrecadacao(String.valueOf(pagamento.getArrecadacaoForma().getId()));
					manterPagamentoActionForm.setDescricaoFormaArrecadacao(String.valueOf(pagamento.getArrecadacaoForma().getDescricao()));
				}

				// Seta no form os dados de tipo de documento
				manterPagamentoActionForm.setIdTipoDocumento(String.valueOf(pagamento.getDocumentoTipo().getId()));

				// Seta no form os dados de localidade
				if(pagamento.getLocalidade() != null){
					manterPagamentoActionForm.setIdLocalidade(String.valueOf(pagamento.getLocalidade().getId()));
					manterPagamentoActionForm.setDescricaoLocalidade(pagamento.getLocalidade().getDescricao());
				}

				// Seta no form os dados de imóvel
				if(pagamento.getImovel() != null){
					manterPagamentoActionForm.setIdImovel(String.valueOf(pagamento.getImovel().getId()));
					manterPagamentoActionForm.setDescricaoImovel(String.valueOf(pagamento.getImovel().getInscricaoFormatada()));
				}

				// Seta no form os dados de cliente
				if(pagamento.getCliente() != null){
					manterPagamentoActionForm.setIdCliente(String.valueOf(pagamento.getCliente().getId()));
					manterPagamentoActionForm.setNomeCliente(pagamento.getCliente().getNome());
				}

				// Seta no form os dados de referência da conta
				if(pagamento.getAnoMesReferenciaPagamento() != null && pagamento.getAnoMesReferenciaPagamento() != 0){
					manterPagamentoActionForm.setReferenciaConta(Util.formatarAnoMesParaMesAno(pagamento.getAnoMesReferenciaPagamento()));
				}

				// Seta no form os dados da guia de pagamento
				if(pagamento.getGuiaPagamentoGeral() != null && pagamento.getGuiaPagamentoGeral().getGuiaPagamento() != null){

					FiltroGuiaPagamentoPrestacao filtroGuiaPagamentoPrestacao = new FiltroGuiaPagamentoPrestacao();
					filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacao.GUIA_PAGAMENTO_ID,
									pagamento.getGuiaPagamentoGeral().getGuiaPagamento().getId()));
					filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacao.NUMERO_PRESTACAO,
									pagamento.getNumeroPrestacao()));
					filtroGuiaPagamentoPrestacao.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoPrestacao.DEBITO_TIPO);

					Collection<GuiaPagamentoPrestacao> colecaoGuiaPagamentoPrestacao = fachada.pesquisar(filtroGuiaPagamentoPrestacao,
									GuiaPagamentoPrestacao.class.getName());

					BigDecimal valorGuiaPrestacao = new BigDecimal("0.00");
					String descricao = "";
					if(colecaoGuiaPagamentoPrestacao != null && !colecaoGuiaPagamentoPrestacao.isEmpty()){
						// caso guia tenha apenas 1 "tipo de débito" pega-se o valor e a descrição
						if(colecaoGuiaPagamentoPrestacao.size() == 1){
							GuiaPagamentoPrestacao guiaPagamentoPrestacao = colecaoGuiaPagamentoPrestacao.iterator().next();
							valorGuiaPrestacao = guiaPagamentoPrestacao.getValorPrestacao();
							descricao = guiaPagamentoPrestacao.getDebitoTipo().getDescricao();
							// caso guia tenha mais de 1 "tipo de débito" pega-se a soma dos valores
						}else{
							Iterator<GuiaPagamentoPrestacao> iterator = colecaoGuiaPagamentoPrestacao.iterator();
							while(iterator.hasNext()){
								GuiaPagamentoPrestacao guiaPagamentoPrestacao = iterator.next();
								valorGuiaPrestacao = valorGuiaPrestacao.add(guiaPagamentoPrestacao.getValorPrestacao());
							}
						}
					}else{
						throw new ActionServletException("atencao.pesquisa_inexistente", null, "Guia de Pagamento");
					}

					manterPagamentoActionForm.setIdGuiaPagamento(String.valueOf(pagamento.getGuiaPagamentoGeral().getGuiaPagamento()
									.getId()));
					manterPagamentoActionForm.setDescricaoGuiaPagamento(descricao);
					manterPagamentoActionForm.setValorGuiaPagamento(String.valueOf(Util.formatarMoedaReal(valorGuiaPrestacao)));
					httpServletRequest.setAttribute("idGuiaPagamentoNaoEncontrado", "true");

					// Seta o número da prestação
					if(pagamento.getNumeroPrestacao() != null){
						manterPagamentoActionForm.setNumeroPrestacao(String.valueOf(pagamento.getNumeroPrestacao()));
					}else{
						manterPagamentoActionForm.setNumeroPrestacao("");
					}

				}

				// Seta no form os dados do débito a cobrar
				if(pagamento.getDebitoACobrar() != null){
					manterPagamentoActionForm.setIdDebitoACobrar(String.valueOf(pagamento.getDebitoACobrar().getId()));

					// se o pagamento estiver referenciando um debitoACobrar que esteja no
					// historico, haverá um erro.
					// TODO Modificar depois. Saulo Lima 25/09/09 (POG)
					if(pagamento.getDebitoACobrar() != null){
						try{
							pagamento.getDebitoACobrar().getDebitoTipo().getId();
						}catch(Exception e){
							Integer idDebitoACobrar = pagamento.getDebitoACobrar().getId();

							FiltroDebitoACobrarHistorico filtroDebitoACobrarHistorico = new FiltroDebitoACobrarHistorico();
							filtroDebitoACobrarHistorico.adicionarParametro(new ParametroSimples(FiltroDebitoACobrarHistorico.ID,
											idDebitoACobrar));
							filtroDebitoACobrarHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoACobrarHistorico.DEBITO_TIPO);
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

					if(pagamento.getDebitoACobrar().getDebitoTipo() != null){
						manterPagamentoActionForm.setDescricaoDebitoACobrar(String.valueOf(pagamento.getDebitoACobrar().getDebitoTipo()
										.getDescricao()));
					}
					manterPagamentoActionForm.setValorDebitoACobrar(String.valueOf(Util.formatarMoedaReal(pagamento.getDebitoACobrar()
									.getValorDebito())));
					if(pagamento.getNumeroPrestacao() != null){
						manterPagamentoActionForm.setNumeroPrestacao(String.valueOf(pagamento.getNumeroPrestacao()));
					}else{
						manterPagamentoActionForm.setNumeroPrestacao("");
					}
				}

				if((pagamento.getGuiaPagamentoGeral() == null || pagamento.getGuiaPagamentoGeral().getGuiaPagamento() == null)
								&& pagamento.getDebitoACobrar() == null && pagamento.getAnoMesReferenciaPagamento() == null){

					if(pagamento.getDebitoTipo() != null){
						manterPagamentoActionForm.setIdTipoDebito(String.valueOf(pagamento.getDebitoTipo().getId()));
						manterPagamentoActionForm.setDescricaoTipoDebito(pagamento.getDebitoTipo().getDescricao());
					}
				}

				// Seta no form a data de pagamento
				manterPagamentoActionForm.setDataPagamento(Util.formatarData(pagamento.getDataPagamento()));

				// Seta no form o valor de pagamento
				// manterPagamentoActionForm.setValorPagamento("" + pagamento.getValorPagamento());

				// Seta no form os dados da situação atual do pagamento
				if(pagamento.getPagamentoSituacaoAtual() != null){
					manterPagamentoActionForm.setIdSituacaoAtualPagamento(String.valueOf(pagamento.getPagamentoSituacaoAtual().getId()));
				}

				if(pagamento.getAvisoBancario().getArrecadador() != null
								&& pagamento.getAvisoBancario().getArrecadador().getCliente() != null){
					manterPagamentoActionForm.setNomeClienteArrecadador(pagamento.getAvisoBancario().getArrecadador().getCliente()
									.getNome());
				}
				manterPagamentoActionForm.setUltimaAlteracaoPagamento(Util.formatarData(pagamento.getUltimaAlteracao()));

			}else{
				Collection historico = fachada.pesquisarPagamentoHistoricoPeloId(Integer.valueOf(idPagamento));
				if(historico == null || historico.isEmpty()){
					throw new ActionServletException("atencao.pagamento.nao.encontrado");
				}else{
					throw new ActionServletException("atencao.pagamento.em.historico");
				}
			}
		}

		// Cria a flag que vai indicar se o campo de valor de pagamento vai estar habilitado ou não
		String habilitarValorPagamento = null;

		manterPagamentoActionForm.setValorPagamento(Util.formataBigDecimal(pagamento.getValorPagamento(), 2, true));

		// Caso a situação atual e a anterior da guia não estiverem preenchidas
		// indica na flag que o campo do valor de pagamento vai estar habilitado na página de
		// atualizar
		// Caso contrário indica na flag que o campo do valor de pagamento NÃO vai estar habilitado
		// na página de atualizar
		// Retirado por ordem de aryed,por Sávio Luiz data:22/03/2007
		/*
		 * if ((pagamentoSituacaoAtual == null) && (pagamentoSituacaoAnterior == null)) {
		 */
		habilitarValorPagamento = "true";
		/*
		 * } else { habilitarValorPagamento = "false"; }
		 */

		// Seta no request a flag que indica se o campo de valor de pagamento vai estar habilitado
		// ou não
		sessao.setAttribute("habilitarValorPagamento", habilitarValorPagamento);

		sessao.setAttribute("pagamento", pagamento); // ?????
		
		// Seta valor padrão para gerarDevolucaoValores
		manterPagamentoActionForm.setGerarDevolucaoValores(ConstantesSistema.NAO.toString());

		// Seta a verificação da geração de devolução dos valores
		this.verificaGerarDevolucaoValores(manterPagamentoActionForm, fachada, pagamento, usuarioLogado);

		// Seta na sessãoa coleção de tipo de credito, para o campo de colecaoCreditoTipo
		this.retornaColecaoCreditoTipo(fachada, sessao);

	}

	/**
	 * [UC0266] Manter Pagamentos
	 * [SB0001] - Atualizar Pagamento
	 * Item 1.13 - Gerar devolução de valores
	 * 
	 * @return
	 */
	private void verificaGerarDevolucaoValores(ManterPagamentoActionForm manterPagamentoActionForm, Fachada fachada, Pagamento pagamento,
					Usuario usuarioLogado){

		manterPagamentoActionForm.setHabilitarGerarDevolucoesValores(false);

		String situacaoPagamentoGerarDevolucao = null;

		try{
			situacaoPagamentoGerarDevolucao = ParametroFaturamento.P_IDS_SITUACAO_PAGAMENTO_GERAR_DEVOLUCAO.executar();

		}catch(ControladorException e){
			throw new ActionServletException("atencao.sistemaparametro_inexistente", "P_IDS_SITUACAO_PAGAMENTO_GERAR_DEVOLUCAO");
		}

		String[] situacaoPagamentoGerarDevolucaoVetor = situacaoPagamentoGerarDevolucao.split(",");

		for(int i = 0; i < situacaoPagamentoGerarDevolucaoVetor.length; i++){
			String gerarDevolucao = situacaoPagamentoGerarDevolucaoVetor[i];

			if(Util.isNaoNuloBrancoZero(pagamento.getPagamentoSituacaoAtual())){
				if(pagamento.getPagamentoSituacaoAtual().getId().equals(Integer.valueOf(gerarDevolucao))){

					boolean temPermissaoEspecial = fachada.verificarPermissaoEspecial(PermissaoEspecial.GERAR_DEVOLUCAO_DE_VALORES,
									usuarioLogado);

					if(temPermissaoEspecial == ConstantesSistema.SENHA_ESPECIAL){

						manterPagamentoActionForm.setHabilitarGerarDevolucoesValores(true);
						manterPagamentoActionForm.setSituacaoPagamentoOriginal(pagamento.getPagamentoSituacaoAtual());
					}
				}
			}
		}

	}

	/**
	 * [UC0266] Manter Pagamentos
	 * [SB0001] - Atualizar Pagamento
	 * Item 1.14 - Tipo de Crédito
	 * 
	 * @return
	 */
	private void retornaColecaoCreditoTipo(Fachada fachada, HttpSession sessao){

		Collection<CreditoTipo> colecaoCreditoTipo = null;

		FiltroCreditoTipoOrigem filtroCreditoTipoOrigem = new FiltroCreditoTipoOrigem();
		filtroCreditoTipoOrigem.adicionarParametro(new ParametroSimples(FiltroCreditoTipoOrigem.CREDITO_ORIGEM_ID,
						CreditoOrigem.CONTAS_PAGAS_EM_DUPLICIDADE_EXCESSO));
		filtroCreditoTipoOrigem.adicionarCaminhoParaCarregamentoEntidade(FiltroCreditoTipoOrigem.CREDITO_TIPO);

		Collection colecaoCreditoTipoOrigem = fachada.pesquisar(filtroCreditoTipoOrigem, CreditoTipoOrigem.class.getName());
		
		if(!Util.isVazioOrNulo(colecaoCreditoTipoOrigem)){
			colecaoCreditoTipo = new ArrayList<CreditoTipo>();
			for(Object object : colecaoCreditoTipoOrigem){
				CreditoTipoOrigem creditoTipoOrigem = (CreditoTipoOrigem) object;

				colecaoCreditoTipo.add(creditoTipoOrigem.getCreditoTipo());
			}
			sessao.setAttribute("colecaoCreditoTipo", colecaoCreditoTipo);
		}

	}


}
