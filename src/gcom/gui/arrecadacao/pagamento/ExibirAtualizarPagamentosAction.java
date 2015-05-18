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
 * Action que inicializa a p�gina de atualizar pagamentoa
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

		// Seta o mapeamento de retorno para a p�gina de atualizar pagamento
		final ActionForward retorno = actionMapping.findForward("atualizarPagamento");

		// Cria uma inst�ncia da fachada
		final Fachada fachada = Fachada.getInstancia();

		// Cria uma inst�ncia da sess�o
		final HttpSession sessao = httpServletRequest.getSession(false);

		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy"/* ,Constantes.LOCALE_PT_BR */);
		Calendar dataCorrente = new GregorianCalendar();

		// Data Corrente
		httpServletRequest.setAttribute("dataAtual", formatoData.format(dataCorrente.getTime()));

		// Recupera o c�digo do pagamento que vai ser atualizado
		// String codigoPagamento = httpServletRequest.getParameter("idRegistroAtualizacao");

		// Cria a vari�vel que vai armazenar o pagamento para ser atualizado
		// Pagamento pagamento = (Pagamento) sessao.getAttribute("pagamento");

		// recupera o form de manter pagamentos
		ManterPagamentoActionForm manterPagamentoActionForm = (ManterPagamentoActionForm) actionForm;

		// Cria o filtro de tipo de documento, e seta no filtro quais os tipo de documentos
		// necess�rios
		// para pesquisar os tipos de documento de conta, guia de pagamento e d�bito a cobrar
		FiltroDocumentoTipo filtroDocumentoTipo = new FiltroDocumentoTipo();
		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.ID, DocumentoTipo.CONTA,
						ParametroSimples.CONECTOR_OR));
		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.ID, DocumentoTipo.GUIA_PAGAMENTO,
						ParametroSimples.CONECTOR_OR));
		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.ID, DocumentoTipo.DEBITO_A_COBRAR,
						ParametroSimples.CONECTOR_OR));
		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.ID, DocumentoTipo.CARTA_OPCAO_PARCELAMENTO));
		Collection<DocumentoTipo> colecaoDocumentoTipo = fachada.pesquisar(filtroDocumentoTipo, DocumentoTipo.class.getName());

		// [FS0002] - Verificar exist�ncia de dados
		// Caso a cole��o de tipo de documento estiver nula ou vazia, levanta uma
		// exce��o para o usu�rio indicando que nenhum tipo de documento est� cadastrado
		// Caso contr�rio manda os tipos de documentos pesquisados pela sess�o
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
				// Definindo a volta do bot�o Voltar p Filtrar Pagamento
				sessao.setAttribute("voltar", "filtrar");
				sessao.setAttribute("idRegistroAtualizacao", idPagamento);
			}else if(httpServletRequest.getParameter("idRegistroAtualizacao") == null){
				idPagamento = String.valueOf(sessao.getAttribute("idRegistroAtualizacao"));
				// Definindo a volta do bot�o Voltar p Filtrar Pagamento
				sessao.setAttribute("voltar", "filtrar");
			}else if(httpServletRequest.getParameter("idRegistroAtualizacao") != null){
				idPagamento = httpServletRequest.getParameter("idRegistroAtualizacao");
				// Definindo a volta do bot�o Voltar p Manter Pagamento
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

			// -------Parte que trata do c�digo quando o usu�rio tecla enter
			// if (httpServletRequest.getParameter("reloadPage") != null &&
			// !httpServletRequest.getParameter("reloadPage").equals("")) {

			// [FS0003] - Verificar exist�ncia da localidade
			// Recupera o c�digo da localidade digitado pelo usu�rio
			String codigoLocalidadeDigitadoEnter = (String) manterPagamentoActionForm.getIdLocalidade();

			// Caso o c�digo da localidade informado n�o estiver vazio
			if(codigoLocalidadeDigitadoEnter != null && !codigoLocalidadeDigitadoEnter.trim().equalsIgnoreCase("")){

				// Recupera a localidade informada pelo usu�rio
				Localidade localidadeEncontrada = fachada.pesquisarLocalidadeDigitada(Integer.valueOf(codigoLocalidadeDigitadoEnter));

				// Caso a localidade informada pelo usu�rio esteja cadastrada no sistema Seta os
				// dados da localidade no form
				// Caso contr�rio seta as informa��es da localidade para vazio e indica ao usu�rio
				// que a localidade n�o existe
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

			// [FS0004] - Verificar exist�ncia da matr�cula do im�vel
			String codigoImovelDigitadoEnter = (String) manterPagamentoActionForm.getIdImovel();

			// Caso o c�digo do im�vel informado n�o estiver vazio
			if(codigoImovelDigitadoEnter != null && !codigoImovelDigitadoEnter.trim().equalsIgnoreCase("")){

				// Recupera o im�vel informado pelo usu�rio
				Imovel imovelEncontrado = fachada.pesquisarImovelDigitado(Integer.valueOf(codigoImovelDigitadoEnter));

				// Caso o im�vel informado pelo usu�rio esteja cadastrado no sistema Seta os dados o
				// im�vel no form
				// Caso contr�rio seta as informa��es o im�vel para vazio e indica ao usu�rio que o
				// im�vel informado n�o existe
				if(imovelEncontrado == null){
					manterPagamentoActionForm.setIdImovel("");
					httpServletRequest.setAttribute("idImovelNaoEncontrado", "exception");
					manterPagamentoActionForm.setDescricaoImovel("Matr�cula inexistente");
				}else{
					manterPagamentoActionForm.setIdImovel(String.valueOf(imovelEncontrado.getId()));
					manterPagamentoActionForm.setDescricaoImovel(String.valueOf(imovelEncontrado.getInscricaoFormatada()));
					httpServletRequest.setAttribute("idImovelNaoEncontrado", "true");

					// Recupera a localidade do im�vel,caso o mesmo exista na base
					Localidade localidadeImovel = imovelEncontrado.getLocalidade();

					// Caso o usu�rio tenha informado a localidade
					if(codigoLocalidadeDigitadoEnter == null || codigoLocalidadeDigitadoEnter.trim().equalsIgnoreCase("")){
						manterPagamentoActionForm.setIdLocalidade(String.valueOf(localidadeImovel.getId()));
						manterPagamentoActionForm.setDescricaoLocalidade(localidadeImovel.getDescricao());
						httpServletRequest.setAttribute("idLocalidadeNaoEncontrada", "true");
					}else{
						// [FS0005] - Verificar localidade da matr�cula do im�vel
						if(!fachada.verificarLocalidadeMatriculaImovel(codigoLocalidadeDigitadoEnter, imovelEncontrado)){
							manterPagamentoActionForm.setIdImovel("");
							httpServletRequest.setAttribute("idImovelNaoEncontrado", "exception");
							manterPagamentoActionForm.setDescricaoImovel("A Localidade da Matr�cula " + localidadeImovel.getId()
											+ " � diferente da localidade informada " + codigoLocalidadeDigitadoEnter);
						}
					}
				}
			}

			// [FS0007] - Verificar exist�ncia do c�digo do cliente
			String codigoClienteDigitadoEnter = (String) manterPagamentoActionForm.getIdCliente();

			// Recupera a metr�cula do im�vel e o c�digo do cliente do form de manter pagamento
			String codigoImovel = codigoImovelDigitadoEnter;
			String codigoCliente = (String) manterPagamentoActionForm.getIdCliente();

			// Caso o usu�rio tenha informado o c�digo do cliente
			if(codigoClienteDigitadoEnter != null && !codigoClienteDigitadoEnter.trim().equalsIgnoreCase("")){

				// Recupera o cliente ,caso o mesmo exista na base
				Cliente clienteEncontrado = fachada.pesquisarClienteDigitado(Integer.valueOf(codigoClienteDigitadoEnter));

				// Caso o cliente esteja cadastrado no sistema Seta no form todos os dados do
				// cliente
				// Caso contr�rio seta os dados do cliente para vazio e informa que o cliente n�o
				// existe
				if(clienteEncontrado == null){
					manterPagamentoActionForm.setIdCliente("");
					httpServletRequest.setAttribute("idClienteNaoEncontrado", "exception");
					manterPagamentoActionForm.setNomeCliente("C�digo inexistente");
				}else{
					manterPagamentoActionForm.setIdCliente(String.valueOf(clienteEncontrado.getId()));
					manterPagamentoActionForm.setNomeCliente(clienteEncontrado.getNome());
					httpServletRequest.setAttribute("idClienteNaoEncontrado", "true");
				}
			}

			// [FS0008] - Verificar exist�ncia da conta
			String referenciaContaDigitadoEnter = (String) manterPagamentoActionForm.getReferenciaConta();

			// Caso o usu�rio tenha informado a refer�ncia da conta
			if(referenciaContaDigitadoEnter != null && !referenciaContaDigitadoEnter.trim().equalsIgnoreCase("")){

				// Caso o usu�rio n�o tenha informado a matr�cula do im�vel
				// Levanta uma exce��o para o usu�rio indicado que ele n�o informou a matr�cula do
				// im�vel
				if(codigoImovel == null || codigoImovel.trim().equalsIgnoreCase("")){

					throw new ActionServletException("atencao.naoinformado", null, "Im�vel");
				}

				// Recupera a conta do im�vel com a refer�ncia informada
				Conta contaEncontrada = null;
				
				// Verifica se o m�s/ano � valido, pois em alguns casos
				// a refer�ncia � informada errada no movimento arrecadador
				if(Util.validarMesAno(referenciaContaDigitadoEnter)){
					contaEncontrada = fachada.pesquisarContaDigitada(codigoImovel, referenciaContaDigitadoEnter);	
				}

				// Caso a conta esteja cadastrada no sistema Seta todas as informa��es da conta no
				// form
				// Caso contr�rio seta as informa��es da conta para nulo e indica ao usu�rio que n�o
				// existe conta para o im�vel
				// informadocom a refer�ncia indicada
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
						// Essa verifica��o � necess�ria porque o objeto Pagamento est� associado a
						// Conta e n�o a ContaGeral, mas a tabela PAGAMENTO est� associada a
						// CONTA_GERAL. Nesse caso quando um pagameno que tinha uma conta em
						// hist�rico e era realizado a baixa for�ada, o sistema estava transferindo
						// o pagamento sem o Id da Conta.

						manterPagamentoActionForm.setReferenciaConta(String.valueOf(referenciaContaDigitadoEnter));
						manterPagamentoActionForm.setDescricaoReferenciaConta(String.valueOf(contaHistorico.getValorTotal()));
						httpServletRequest.setAttribute("referenciaContaNaoEncontrada", "true");
					}else{
						manterPagamentoActionForm.setReferenciaConta(String.valueOf(referenciaContaDigitadoEnter));
						httpServletRequest.setAttribute("referenciaContaNaoEncontrada", "exception");
						manterPagamentoActionForm.setDescricaoReferenciaConta("N�o h� Conta com a refer�ncia "
										+ referenciaContaDigitadoEnter + " para o im�vel " + codigoImovel);
						// manterPagamentoActionForm.setValorPagamento("");
					}
				}
			}

			// [FS0022] - Verificar exist�ncia da guia de pagamento
			String codigoGuiaPagamentoDigitadoEnter = (String) manterPagamentoActionForm.getIdGuiaPagamento();
			String numeroPrestacao = (String) manterPagamentoActionForm.getNumeroPrestacao();

			// Caso o usu�rio tenha informado o c�digo da guia de pagamento
			if(codigoGuiaPagamentoDigitadoEnter != null && !codigoGuiaPagamentoDigitadoEnter.trim().equalsIgnoreCase("")){

				// Caso o usu�rio n�o tenha informado a matr�cula do im�vel
				// Levanta uma exce��o para o usu�rio indicado que ele n�o informou a matr�cula do
				// im�vel
				if((codigoImovel == null && codigoCliente == null)
								|| (codigoImovel.trim().equalsIgnoreCase("") && codigoCliente.trim().equalsIgnoreCase(""))){
					throw new ActionServletException("atencao.naoinformado", null, "Im�vel ou Cliente");
				}

				// [FS0025] - Verificar preenchimento da presta��o - Parte 1
				// Caso tenha informado a guia e n�o informado o n�mero da presta��o
				if(numeroPrestacao == null || numeroPrestacao.trim().equals("")){
					throw new ActionServletException("atencao.informe_campo", null, "N�mero da Presta��o");
				}

				// Pesquisa a guia de pagamento para o im�vel informado
				GuiaPagamento guiaPagamentoEncontrada = fachada.pesquisarGuiaPagamentoDigitada(codigoImovel, codigoCliente,
								codigoGuiaPagamentoDigitadoEnter);

				// Caso a guia de pagamento esteja cadastrada no sistema Seta os dados da guia de
				// pagamento no form
				// Caso contr�rio seta os dados da guia para nulo e informa ao usu�rio que n�o
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
						// caso guia tenha apenas 1 "tipo de d�bito" pega-se o valor e a descri��o
						if(colecaoGuiaPagamentoPrestacao.size() == 1){
							GuiaPagamentoPrestacao guiaPagamentoPrestacao = colecaoGuiaPagamentoPrestacao.iterator().next();
							valorGuiaPrestacao = guiaPagamentoPrestacao.getValorPrestacao();
							descricao = guiaPagamentoPrestacao.getDebitoTipo().getDescricao();
							// caso guia tenha mais de 1 "tipo de d�bito" pega-se a soma dos valores
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

					// Seta o n�mero da presta��o
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
					// [FS0025] - Verificar preenchimento da presta��o - Parte 2
					// Caso n�o tenha informado a guia mas tenha informado a presta��o
					if(numeroPrestacao != null && !numeroPrestacao.trim().equals("")){
						throw new ActionServletException("atencao.numero_prestacao.sem.guia_pagamento");
					}
				}
			}

			// [FS0024] - Verificar exist�ncia do d�bito a cobrar
			String codigoDebitoACobrarDigitadoEnter = (String) manterPagamentoActionForm.getIdDebitoACobrar();

			// Caso o usu�rio tenha informado o c�digo do d�bito a cobrar
			if(codigoDebitoACobrarDigitadoEnter != null && !codigoDebitoACobrarDigitadoEnter.trim().equalsIgnoreCase("")){

				// Caso o usu�rio n�o tenha informado a matr�cula do im�vel
				// Levanta uma exce��o para o usu�rio indicado que ele n�o informou a matr�cula do
				// im�vel
				if(codigoImovel == null || codigoImovel.trim().equalsIgnoreCase("")){
					throw new ActionServletException("atencao.naoinformado", null, "Im�vel");
				}

				DebitoACobrar debitoACobrarEncontrado = null;

				try{

					// Pesquisa o d�bito a cobrar para o im�vel informado
					debitoACobrarEncontrado = fachada.pesquisarDebitoACobrarDigitado(codigoImovel, codigoDebitoACobrarDigitadoEnter);

					// Caso o d�bito a cobrar esteja cadastrado no sistema Seta os dados do d�bito a
					// cobrar no form
					// Caso contr�rio seta os dados do d�bito para nulo e informa ao usu�rio que n�o
					// existe d�bito a cobrar cadastrado no sistema
					manterPagamentoActionForm.setIdDebitoACobrar(String.valueOf(debitoACobrarEncontrado.getId()));
					manterPagamentoActionForm.setDescricaoDebitoACobrar(String.valueOf(debitoACobrarEncontrado.getDebitoTipo()
									.getDescricao()));
					manterPagamentoActionForm.setValorDebitoACobrar(String.valueOf(debitoACobrarEncontrado.getValorDebito()));
					httpServletRequest.setAttribute("idDebitoACobrarNaoEncontrado", "true");

				}catch(Exception e){

				}

				if(debitoACobrarEncontrado == null){

					DebitoACobrarHistorico debitoACobrarDigitadoH = null;

					// Cria o filtro de d�bito a cobrar e seta todos os par�metros para
					// pesquisar o d�bito a cobrar do im�vel
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
						manterPagamentoActionForm.setDescricaoDebitoACobrar("D�bito a Cobrar inexistente");
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

			// [FS0020] - Verificar exist�ncia do tipo de d�bito
			String codigoTipoDebitoDigitadoEnter = (String) manterPagamentoActionForm.getIdTipoDebito();

			// Caso o usu�rio tenha informado o c�digo do tipo de d�bito
			if(codigoTipoDebitoDigitadoEnter != null && !codigoTipoDebitoDigitadoEnter.trim().equalsIgnoreCase("")){

				// Recupera o tipo de d�bito ,caso o mesmo exista na base
				DebitoTipo tipoDebitoEncontrado = fachada.pesquisarTipoDebitoDigitado(Integer.valueOf(codigoTipoDebitoDigitadoEnter));

				// Caso o tipo de d�bito esteja cadastrado no sistema Seta no form todos os dados do
				// tipo de d�bito
				// Caso contr�rio seta os dados do tipo de d�bito para vazio e informa que o tipo de
				// d�bito n�o existe
				if(tipoDebitoEncontrado != null){
					manterPagamentoActionForm.setIdTipoDebito(String.valueOf(tipoDebitoEncontrado.getId()));
					manterPagamentoActionForm.setDescricaoTipoDebito(tipoDebitoEncontrado.getDescricao());
					httpServletRequest.setAttribute("idTipoDebitoNaoEncontrado", "true");

				}else{
					manterPagamentoActionForm.setIdTipoDebito("");
					httpServletRequest.setAttribute("idTipoDebitoNaoEncontrado", "exception");
					manterPagamentoActionForm.setDescricaoTipoDebito("Tipo de D�bito inexistente");
				}
			}

			// }
		}
		// -------Parte que trata do c�digo quando o usu�rio tecla enter

		// Seta na sess�o o form de pagamento e o pagamento que vai ser atualizado
		sessao.setAttribute("ManterPagamentoActionForm", manterPagamentoActionForm);
		// sessao.setAttribute("pagamento", pagamento);
		
		// Retorna o mapeamento contido na vari�vel retorno
		return retorno;
	}

	private void exibirPagamento(String idPagamento, ManterPagamentoActionForm manterPagamentoActionForm, Fachada fachada,
					HttpSession sessao, HttpServletRequest httpServletRequest){

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		Pagamento pagamento = null;
		PagamentoSituacao pagamentoSituacaoAtual = null;

		if(idPagamento != null && !idPagamento.equalsIgnoreCase("")){

			// Cria o filtro de pagamento e seta o c�digo do pagamento para ser atualizado no filtro
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

			// Pesquisa o pagamento no sistema com os par�metros informados no filtro
			Collection colecaoPagamentos = fachada.pesquisar(filtroPagamento, Pagamento.class.getName());

			// Caso a pesquisa tenha retornado o pagamento
			if(colecaoPagamentos != null && !colecaoPagamentos.isEmpty()){
				// Recupera da cole��o o pagamento que vai ser atualizado
				pagamento = (Pagamento) Util.retonarObjetoDeColecao(colecaoPagamentos);

				// Cria a vari�vel que vai armazenar a cole��o de situa��es atuais de pagamento
				Collection<PagamentoSituacao> colecaoSituacaoAtualPagamento = null;

				// Recupera a situa��o atual e anterior do pagamento
				pagamentoSituacaoAtual = pagamento.getPagamentoSituacaoAtual();

				// Caso a situa��o atual do pagamento esteja preenchida
				if(pagamentoSituacaoAtual != null){
					System.out.println(pagamentoSituacaoAtual.getId());

					// Caso a situa��o atual do pagamento seja igual a "Fatura
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

						// Cria o filtro de situa��o de pagamento e seta no filtro para retornar
						// somente a situa��o igual a valor a baixar
						FiltroPagamentoSituacao filtroPagamentoSituacao = new FiltroPagamentoSituacao();
						filtroPagamentoSituacao.adicionarParametro(new ParametroSimples(FiltroPagamentoSituacao.CODIGO,
										PagamentoSituacao.VALOR_A_BAIXAR));
						colecaoSituacaoAtualPagamento = fachada.pesquisar(filtroPagamentoSituacao, PagamentoSituacao.class.getName());

						// [FS0002] - Verificar exist�ncia de dados Caso a situa��o de valor a
						// baixar n�o esteja
						// cadastrada no sistema levante uma exce��o para o usu�rio indicando que a
						// situa��o n�o est� cadastrada
						if(colecaoSituacaoAtualPagamento == null || colecaoSituacaoAtualPagamento.isEmpty()){
							throw new ActionServletException("atencao.naocadastrado", null, "Situa��o de Pagamento");
						}
					}
				}

				// Seta na sess�oa cole��o de situa��o de pagamentos, para o campo de situa��o de
				// pagamento atual
				sessao.setAttribute("colecaoSituacaoAtualPagamento", colecaoSituacaoAtualPagamento);

				// Seta no form os dados de aviso banc�rio
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

				// Seta no form os dados de arrecada��o
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

				// Seta no form os dados de im�vel
				if(pagamento.getImovel() != null){
					manterPagamentoActionForm.setIdImovel(String.valueOf(pagamento.getImovel().getId()));
					manterPagamentoActionForm.setDescricaoImovel(String.valueOf(pagamento.getImovel().getInscricaoFormatada()));
				}

				// Seta no form os dados de cliente
				if(pagamento.getCliente() != null){
					manterPagamentoActionForm.setIdCliente(String.valueOf(pagamento.getCliente().getId()));
					manterPagamentoActionForm.setNomeCliente(pagamento.getCliente().getNome());
				}

				// Seta no form os dados de refer�ncia da conta
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
						// caso guia tenha apenas 1 "tipo de d�bito" pega-se o valor e a descri��o
						if(colecaoGuiaPagamentoPrestacao.size() == 1){
							GuiaPagamentoPrestacao guiaPagamentoPrestacao = colecaoGuiaPagamentoPrestacao.iterator().next();
							valorGuiaPrestacao = guiaPagamentoPrestacao.getValorPrestacao();
							descricao = guiaPagamentoPrestacao.getDebitoTipo().getDescricao();
							// caso guia tenha mais de 1 "tipo de d�bito" pega-se a soma dos valores
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

					// Seta o n�mero da presta��o
					if(pagamento.getNumeroPrestacao() != null){
						manterPagamentoActionForm.setNumeroPrestacao(String.valueOf(pagamento.getNumeroPrestacao()));
					}else{
						manterPagamentoActionForm.setNumeroPrestacao("");
					}

				}

				// Seta no form os dados do d�bito a cobrar
				if(pagamento.getDebitoACobrar() != null){
					manterPagamentoActionForm.setIdDebitoACobrar(String.valueOf(pagamento.getDebitoACobrar().getId()));

					// se o pagamento estiver referenciando um debitoACobrar que esteja no
					// historico, haver� um erro.
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

				// Seta no form os dados da situa��o atual do pagamento
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

		// Cria a flag que vai indicar se o campo de valor de pagamento vai estar habilitado ou n�o
		String habilitarValorPagamento = null;

		manterPagamentoActionForm.setValorPagamento(Util.formataBigDecimal(pagamento.getValorPagamento(), 2, true));

		// Caso a situa��o atual e a anterior da guia n�o estiverem preenchidas
		// indica na flag que o campo do valor de pagamento vai estar habilitado na p�gina de
		// atualizar
		// Caso contr�rio indica na flag que o campo do valor de pagamento N�O vai estar habilitado
		// na p�gina de atualizar
		// Retirado por ordem de aryed,por S�vio Luiz data:22/03/2007
		/*
		 * if ((pagamentoSituacaoAtual == null) && (pagamentoSituacaoAnterior == null)) {
		 */
		habilitarValorPagamento = "true";
		/*
		 * } else { habilitarValorPagamento = "false"; }
		 */

		// Seta no request a flag que indica se o campo de valor de pagamento vai estar habilitado
		// ou n�o
		sessao.setAttribute("habilitarValorPagamento", habilitarValorPagamento);

		sessao.setAttribute("pagamento", pagamento); // ?????
		
		// Seta valor padr�o para gerarDevolucaoValores
		manterPagamentoActionForm.setGerarDevolucaoValores(ConstantesSistema.NAO.toString());

		// Seta a verifica��o da gera��o de devolu��o dos valores
		this.verificaGerarDevolucaoValores(manterPagamentoActionForm, fachada, pagamento, usuarioLogado);

		// Seta na sess�oa cole��o de tipo de credito, para o campo de colecaoCreditoTipo
		this.retornaColecaoCreditoTipo(fachada, sessao);

	}

	/**
	 * [UC0266] Manter Pagamentos
	 * [SB0001] - Atualizar Pagamento
	 * Item 1.13 - Gerar devolu��o de valores
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
	 * Item 1.14 - Tipo de Cr�dito
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
