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

import gcom.arrecadacao.ArrecadacaoForma;
import gcom.arrecadacao.FiltroAvisoBancario;
import gcom.arrecadacao.aviso.AvisoBancario;
import gcom.arrecadacao.pagamento.*;
import gcom.arrecadacao.pagamento.bean.InserirPagamentoManualHelper;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.FiltroDocumentoTipo;
import gcom.fachada.Fachada;
import gcom.faturamento.GuiaPagamentoGeral;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.FiltroConta;
import gcom.faturamento.debito.*;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorActionForm;

/**
 * Action que inicializa a p�gina de inserir pagamento manual do processo de
 * inserir pagamentos
 * 
 * @author Pedro Alexandre
 * @date 16/02/2006
 */
public class ExibirInserirPagamentosTipoInclusaoManualAction
				extends GcomAction {

	/**
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @author Saulo Lima
	 * @date 13/07/2009
	 *       Corre��es e Inclus�o do campo "Numero de Presta��es"
	 *       Corre��o da atribui��o da guia de pagamento � Pagamento
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno para a p�gina de inserir pagamento manual
		ActionForward retorno = actionMapping.findForward("inserirPagamentosTipoInclusaoManual");

		// Cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Cria uma inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		// recupera o form
		DynaValidatorActionForm pagamentoActionForm = (DynaValidatorActionForm) actionForm;

		/*
		 * Colocado por Raphael Rossiter em 24/09/2007 (Analista: Aryed Lins)
		 * OBJ: Inserir mais de um pagamento por vez
		 */
		String informarPagamento = httpServletRequest.getParameter("informarPagamento");
		String removerPagamento = httpServletRequest.getParameter("removerPagamento");

		// Cria o filtro de tipo de documento, e seta no filtro quais os tipo de documentos
		// necess�rios
		// para pesquisar os tipos de documento de conta, guia de pagamento e d�bito a cobrar
		FiltroDocumentoTipo filtroDocumentoTipo = new FiltroDocumentoTipo();
		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.ID, DocumentoTipo.CONTA,
						ParametroSimples.CONECTOR_OR));
		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.ID, DocumentoTipo.GUIA_PAGAMENTO,
						ParametroSimples.CONECTOR_OR));
		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.ID, DocumentoTipo.DEBITO_A_COBRAR));
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

		// [FS0007] - Verificar exist�ncia da localidade
		// Recupera o c�digo da localidade digitado pelo usu�rio
		String codigoLocalidadeDigitadoEnter = (String) pagamentoActionForm.get("idLocalidade");

		// Caso o c�digo da localidade informado n�o estiver vazio
		if(codigoLocalidadeDigitadoEnter != null && !codigoLocalidadeDigitadoEnter.trim().equalsIgnoreCase("")){

			// Recupera a localidade informada pelo usu�rio
			Localidade localidadeEncontrada = fachada.pesquisarLocalidadeDigitada(Integer.valueOf(codigoLocalidadeDigitadoEnter));

			// Caso a localidade informada pelo usu�rio esteja cadastrada no sistema
			// Seta os dados da localidade no form Caso contr�rio seta as informa��es da localidade
			// para vazio
			// e indica ao usu�rio que a localidade n�o existe
			if(localidadeEncontrada != null){
				pagamentoActionForm.set("idLocalidade", "" + localidadeEncontrada.getId());
				pagamentoActionForm.set("descricaoLocalidade", "" + localidadeEncontrada.getDescricao());
				httpServletRequest.setAttribute("idLocalidadeNaoEncontrada", "true");

			}else{
				pagamentoActionForm.set("idLocalidade", "");
				httpServletRequest.setAttribute("idLocalidadeNaoEncontrada", "exception");
				pagamentoActionForm.set("descricaoLocalidade", "Localidade inexistente");
			}
		}

		// [FS0008] - Verificar exist�ncia da matr�cula do im�vel
		String codigoImovelDigitadoEnter = (String) pagamentoActionForm.get("idImovel");

		// Caso o c�digo do im�vel informado n�o estiver vazio
		if(codigoImovelDigitadoEnter != null && !codigoImovelDigitadoEnter.trim().equalsIgnoreCase("")){

			// Recupera o im�vel informado pelo usu�rio
			Imovel imovelEncontrado = fachada.pesquisarImovelDigitado(Integer.valueOf(codigoImovelDigitadoEnter));
			// Imovel imovelEncontrado = this.pesquisarImovelDigitado(new
			// Integer(codigoImovelDigitadoEnter));

			// Caso o im�vel informado pelo usu�rio esteja cadastrado no sistema Seta os dados o
			// im�vel no form
			// Caso contr�rio seta as informa��es o im�vel para vazio e indica ao usu�rio que o
			// im�vel informado n�o existe
			if(imovelEncontrado != null){
				pagamentoActionForm.set("idImovel", "" + imovelEncontrado.getId());
				pagamentoActionForm.set("descricaoImovel", "" + imovelEncontrado.getInscricaoFormatada());
				httpServletRequest.setAttribute("idImovelNaoEncontrado", "true");

				// Recupera a localidade do im�vel,caso o mesmo exista na base
				Localidade localidadeImovel = imovelEncontrado.getLocalidade();

				// Caso o usu�rio tenha informado a localidade
				if(codigoLocalidadeDigitadoEnter != null && !codigoLocalidadeDigitadoEnter.trim().equalsIgnoreCase("")){

					// [FS0009] - Verificar localidade da matr�cula do im�vel
					if(!fachada.verificarLocalidadeMatriculaImovel(codigoLocalidadeDigitadoEnter, imovelEncontrado)){
						pagamentoActionForm.set("idImovel", "");
						httpServletRequest.setAttribute("idImovelNaoEncontrado", "exception");
						pagamentoActionForm.set("descricaoImovel", "A Localidade da Matr�cula " + localidadeImovel.getId()
										+ " � diferente da localidade informada " + codigoLocalidadeDigitadoEnter);
					}

				}else{
					pagamentoActionForm.set("idLocalidade", "" + localidadeImovel.getId());
					pagamentoActionForm.set("descricaoLocalidade", localidadeImovel.getDescricao());
					httpServletRequest.setAttribute("idLocalidadeNaoEncontrada", "true");
				}
			}else{
				pagamentoActionForm.set("idImovel", "");
				httpServletRequest.setAttribute("idImovelNaoEncontrado", "exception");
				pagamentoActionForm.set("descricaoImovel", "Matr�cula do Im�vel inexistente");

				// limpa todos os campos das pesquisas q dependem de Matricula do imovel
				pagamentoActionForm.set("valorDebitoACobrar", "");

				pagamentoActionForm.set("valorPagamento", "");
				pagamentoActionForm.set("referenciaConta", "");
				pagamentoActionForm.set("descricaoReferenciaConta", "");

				pagamentoActionForm.set("idGuiaPagamento", "");
				pagamentoActionForm.set("descricaoGuiaPagamento", "");
				pagamentoActionForm.set("valorGuiaPagamento", "");
				pagamentoActionForm.set("numeroPrestacao", "");

				pagamentoActionForm.set("idDebitoACobrar", "");
				pagamentoActionForm.set("descricaoDebitoACobrar", "");
			}
		}

		// [FS0011] - Verificar exist�ncia do c�digo do cliente
		String codigoClienteDigitadoEnter = (String) pagamentoActionForm.get("idCliente");

		// Recupera a metr�cula do im�vel e o c�digo do cliente do form de pagamento
		String codigoImovel = (String) pagamentoActionForm.get("idImovel");
		String codigoCliente = (String) pagamentoActionForm.get("idCliente");

		// Caso o usu�rio tenha informado o c�digo do cliente
		if(codigoClienteDigitadoEnter != null && !codigoClienteDigitadoEnter.trim().equalsIgnoreCase("")){

			// Recupera o cliente ,caso o mesmo exista na base
			Cliente clienteEncontrado = fachada.pesquisarClienteDigitado(Integer.valueOf(codigoClienteDigitadoEnter));

			// Caso o cliente esteja cadastrado no sistema Seta no form todos os dados do cliente
			// Caso contr�rio seta os dados do cliente para vazio e informa que o cliente n�o existe
			if(clienteEncontrado != null){
				pagamentoActionForm.set("idCliente", "" + clienteEncontrado.getId());
				pagamentoActionForm.set("nomeCliente", clienteEncontrado.getNome());
				httpServletRequest.setAttribute("idClienteNaoEncontrado", "true");

			}else{
				pagamentoActionForm.set("idCliente", "");
				httpServletRequest.setAttribute("idClienteNaoEncontrado", "exception");
				pagamentoActionForm.set("nomeCliente", "C�digo de Cliente inexistente");

				// limpa todos os campos das pesquisas q dependem de C�digo de Cliente
				pagamentoActionForm.set("idGuiaPagamento", "");
				pagamentoActionForm.set("descricaoGuiaPagamento", "");
				pagamentoActionForm.set("valorGuiaPagamento", "");
				pagamentoActionForm.set("numeroPrestacao", "");
				pagamentoActionForm.set("valorPagamento", "");
			}
		}

		// [FS0012] - Verificar exist�ncia da conta
		String referenciaContaDigitadoEnter = (String) pagamentoActionForm.get("referenciaConta");

		// Caso o usu�rio tenha informado a refer�ncia da conta
		if(referenciaContaDigitadoEnter != null && !referenciaContaDigitadoEnter.trim().equalsIgnoreCase("")){

			// Caso o usu�rio n�o tenha informado a matr�cula do im�vel
			// Levanta uma exce��o para o usu�rio indicado que ele n�o informou a matr�cula do
			// im�vel
			if(codigoImovel == null || codigoImovel.trim().equalsIgnoreCase("")){
				throw new ActionServletException("atencao.required", null, "Matr�cula do Im�vel");
			}

			// Recupera a conta do im�vel com a refer�ncia informada
			Conta contaEncontrada = null;

			// Verifica se o m�s/ano � valido, pois em alguns casos
			// a refer�ncia � informada errada no movimento arrecadador
			if(Util.validarMesAno(referenciaContaDigitadoEnter)){
				contaEncontrada = fachada.pesquisarContaDigitada(codigoImovel, referenciaContaDigitadoEnter);
			}

			// Conta contaEncontrada = this.pesquisarContaDigitada(codigoImovel,
			// referenciaContaDigitadoEnter);

			// Caso a conta esteja cadastrada no sistema Seta todas as informa��es da conta no form
			// Caso contr�rio seta as informa��es da conta para nulo e indica ao usu�rio que n�o
			// existe conta para o im�vel
			// informado com a refer�ncia indicada
			if(contaEncontrada != null){
				pagamentoActionForm.set("referenciaConta", "" + referenciaContaDigitadoEnter);
				pagamentoActionForm.set("descricaoReferenciaConta", "" + Util.formatarMoedaReal(contaEncontrada.getValorTotal()));
				// pagamentoActionForm.set("valorPagamento", "" +
				// Util.formatarMoedaReal(contaEncontrada.getValorTotal()));
				httpServletRequest.setAttribute("referenciaContaNaoEncontrada", "true");

			}else if((informarPagamento == null || informarPagamento.equals(""))
							&& (removerPagamento == null || removerPagamento.equals(""))){

				pagamentoActionForm.set("referenciaConta", "" + referenciaContaDigitadoEnter);

				pagamentoActionForm.set("descricaoReferenciaConta", "");
				pagamentoActionForm.set("valorPagamento", "");
				httpServletRequest.setAttribute("referenciaContaNaoEncontrada", "true");

				/*
				 * httpServletRequest.setAttribute("referenciaContaNaoEncontrada", "exception");
				 * pagamentoActionForm.set("descricaoReferenciaConta",
				 * "N�o h� Conta com a refer�ncia " + referenciaContaDigitadoEnter +
				 * " para o im�vel " + codigoImovel);
				 * //pagamentoActionForm.set("descricaoReferenciaConta","");
				 * pagamentoActionForm.set("valorPagamento", "");
				 * throw new ActionServletException("atencao.referencia.naocadastrada", null, "" +
				 * referenciaContaDigitadoEnter, "" + codigoImovel);
				 */
			}
		}

		// [FS0022] - Verificar exist�ncia da guia de pagamento
		String codigoGuiaPagamentoDigitadoEnter = (String) pagamentoActionForm.get("idGuiaPagamento");
		String numeroPrestacao = (String) pagamentoActionForm.get("numeroPrestacao");

		// Caso o usu�rio tenha informado o c�digo da guia de pagamento
		if(codigoGuiaPagamentoDigitadoEnter != null && !codigoGuiaPagamentoDigitadoEnter.trim().equalsIgnoreCase("")){

			// Caso o usu�rio n�o tenha informado a matr�cula do im�vel
			// Levanta uma exce��o para o usu�rio indicado que ele n�o informou a matr�cula do
			// im�vel
			if(codigoImovel == null || codigoImovel.trim().equalsIgnoreCase("")){
				if(codigoCliente == null || codigoCliente.trim().equalsIgnoreCase("")){
					throw new ActionServletException("atencao.required", null, "Matr�cula do Im�vel ou C�digo do Cliente");
				}
			}

			// Pesquisa a guia de pagamento para o im�vel informado
			GuiaPagamento guiaPagamentoEncontrada = fachada.pesquisarGuiaPagamentoDigitada(codigoImovel, codigoCliente,
							codigoGuiaPagamentoDigitadoEnter);
			// GuiaPagamento guiaPagamentoEncontrada =
			// this.pesquisarGuiaPagamentoDigitada(codigoImovel, codigoCliente,
			// codigoGuiaPagamentoDigitadoEnter);

			// [FS0025] - Verificar preenchimento da presta��o - Parte 1
			// Caso tenha informado a guia e n�o informado o n�mero da presta��o
			if(numeroPrestacao == null || numeroPrestacao.trim().equals("")){
				throw new ActionServletException("atencao.informe_campo", null, "N�mero da Presta��o");
			}

			// Caso a guia de pagamento esteja cadastrada no sistema Seta os dados da guia de
			// pagamento no form
			// Caso contr�rio seta os dados da guia para nulo e informa ao usu�rio que n�o existe
			// guia de pagamento cadastrada no sistema
			if(guiaPagamentoEncontrada != null){

				FiltroGuiaPagamentoPrestacao filtroGuiaPagamentoPrestacao = new FiltroGuiaPagamentoPrestacao();
				filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacao.GUIA_PAGAMENTO_ID,
								guiaPagamentoEncontrada.getId()));
				filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacao.NUMERO_PRESTACAO, Integer
								.valueOf(numeroPrestacao)));
				filtroGuiaPagamentoPrestacao.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoPrestacao.DEBITO_TIPO);

				Collection<GuiaPagamentoPrestacao> colecaoGuiaPagamentoPrestacao = fachada.pesquisar(filtroGuiaPagamentoPrestacao,
								GuiaPagamentoPrestacao.class.getName());

				BigDecimal valorGuiaPrestacao = BigDecimal.ZERO;
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

				pagamentoActionForm.set("idGuiaPagamento", "" + guiaPagamentoEncontrada.getId());
				pagamentoActionForm.set("descricaoGuiaPagamento", descricao);
				pagamentoActionForm.set("valorGuiaPagamento", "" + Util.formatarMoedaReal(valorGuiaPrestacao));
				httpServletRequest.setAttribute("idGuiaPagamentoNaoEncontrado", "true");

				// Seta os dados da localidade
				pagamentoActionForm.set("idLocalidade", "" + guiaPagamentoEncontrada.getLocalidade().getId());
				pagamentoActionForm.set("descricaoLocalidade", guiaPagamentoEncontrada.getLocalidade().getDescricao());
				httpServletRequest.setAttribute("idLocalidadeNaoEncontrada", "true");

				// Seta o n�mero da presta��o
				pagamentoActionForm.set("numeroPrestacao", numeroPrestacao);

			}else{
				pagamentoActionForm.set("idGuiaPagamento", "");
				pagamentoActionForm.set("descricaoGuiaPagamento", "Guia de Pagamento inexistente");
				pagamentoActionForm.set("valorGuiaPagamento", "");
				httpServletRequest.setAttribute("idGuiaPagamentoNaoEncontrado", "exception");
			}
		}else{
			// verifica a presta�ao apenas se for guia
			String debitoACobrar = (String) pagamentoActionForm.get("idDebitoACobrar");
			if(StringUtils.isEmpty(debitoACobrar)){
				// [FS0025] - Verificar preenchimento da presta��o - Parte 2
				// Caso n�o tenha informado a guia mas tenha informado a presta��o
				if(numeroPrestacao != null && !numeroPrestacao.trim().equals("")){
					throw new ActionServletException("atencao.numero_prestacao.sem.guia_pagamento");
				}
			}
		}

		// [FS0024] - Verificar exist�ncia do d�bito a cobrar
		String codigoDebitoACobrarDigitadoEnter = (String) pagamentoActionForm.get("idDebitoACobrar");

		// Caso o usu�rio tenha informado o c�digo do d�bito a cobrar
		if(codigoDebitoACobrarDigitadoEnter != null && !codigoDebitoACobrarDigitadoEnter.trim().equalsIgnoreCase("")){

			// Caso o usu�rio n�o tenha informado a matr�cula do im�vel
			// Levanta uma exce��o para o usu�rio indicado que ele n�o informou a matr�cula do
			// im�vel
			if(codigoImovel == null || codigoImovel.trim().equalsIgnoreCase("")){
				throw new ActionServletException("atencao.required", null, "Matr�cula do Im�vel");

			}

			// Pesquisa o d�bito a cobrar para o im�vel informado
			DebitoACobrar debitoACobrarEncontrado = fachada.pesquisarDebitoACobrarDigitado(codigoImovel, codigoDebitoACobrarDigitadoEnter);
			// DebitoACobrar debitoACobrarEncontrado =
			// this.pesquisarDebitoACobrarDigitado(codigoImovel, codigoDebitoACobrarDigitadoEnter);

			// Caso o d�bito a cobrar esteja cadastrado no sistema Seta os dados do d�bito a cobrar
			// no form
			// Caso contr�rio seta os dados do d�bito para nulo e informa ao usu�rio que n�o existe
			// d�bito a cobrar cadastrado no sistema
			if(debitoACobrarEncontrado != null){

				Integer totalPrestacoes = Integer.valueOf(debitoACobrarEncontrado.getNumeroPrestacaoDebito());
				if(numeroPrestacao != null && !numeroPrestacao.equals("") && Integer.valueOf(numeroPrestacao) != 0
								&& Integer.valueOf(numeroPrestacao) > totalPrestacoes){
					pagamentoActionForm.set("numeroPrestacao", "");
					String mensagem = "um n�mero de presta��o v�lido";
					throw new ActionServletException("atencao.required", null, mensagem);
				}
				pagamentoActionForm.set("numeroPrestacao", numeroPrestacao);
				pagamentoActionForm.set("idDebitoACobrar", "" + debitoACobrarEncontrado.getId());
				pagamentoActionForm.set("descricaoDebitoACobrar", "" + debitoACobrarEncontrado.getDebitoTipo().getDescricao());
				pagamentoActionForm.set("valorDebitoACobrar", "" + Util.formatarMoedaReal(debitoACobrarEncontrado.getValorDebito()));
				httpServletRequest.setAttribute("idDebitoACobrarNaoEncontrado", "true");

			}else{
				pagamentoActionForm.set("idDebitoACobrar", "");
				pagamentoActionForm.set("descricaoDebitoACobrar", "D�bito a Cobrar inexistente");
				pagamentoActionForm.set("descricaoDebitoACobrar", "");
				httpServletRequest.setAttribute("idDebitoACobrarNaoEncontrado", "exception");

			}
		}

		// [FS0020] - Verificar exist�ncia do tipo de d�bito
		String codigoTipoDebitoDigitadoEnter = (String) pagamentoActionForm.get("idTipoDebito");

		// Caso o usu�rio tenha informado o c�digo do tipo de d�bito
		if(codigoTipoDebitoDigitadoEnter != null && !codigoTipoDebitoDigitadoEnter.trim().equalsIgnoreCase("")){

			// Recupera o tipo de d�bito ,caso o mesmo exista na base
			DebitoTipo tipoDebitoEncontrado = fachada.pesquisarTipoDebitoDigitado(Integer.valueOf(codigoTipoDebitoDigitadoEnter));

			// Caso o tipo de d�bito esteja cadastrado no sistema Seta no form todos os dados do
			// tipo de d�bito
			// Caso contr�rio seta os dados do tipo de d�bito para vazio e informa que o tipo de
			// d�bito n�o existe
			if(tipoDebitoEncontrado != null){
				pagamentoActionForm.set("idTipoDebito", "" + tipoDebitoEncontrado.getId());
				pagamentoActionForm.set("descricaoTipoDebito", tipoDebitoEncontrado.getDescricao());
				httpServletRequest.setAttribute("idTipoDebitoNaoEncontrado", "true");

			}else{
				pagamentoActionForm.set("idTipoDebito", "");
				httpServletRequest.setAttribute("idTipoDebitoNaoEncontrado", "exception");
				pagamentoActionForm.set("descricaoTipoDebito", "Tipo de D�bito inexistente");
			}
		}

		/*
		 * Colocado por Raphael Rossiter em 24/09/2007 (Analista: Aryed Lins)
		 * OBJ: Inserir mais de um pagamento por vez
		 */
		if(informarPagamento != null && !informarPagamento.equals("")){

			Collection<InserirPagamentoManualHelper> colecaoPagamentoInserirHelper = (Collection) sessao
							.getAttribute("colecaoPagamentoInserirHelper");

			if(colecaoPagamentoInserirHelper != null && !colecaoPagamentoInserirHelper.isEmpty()){

				// VALIDA E GERA O PAGAMENTO EM MEMORIA
				InserirPagamentoManualHelper pagamentoInformado = this.validarDadosPagamento(pagamentoActionForm, fachada,
								httpServletRequest, colecaoPagamentoInserirHelper.size());

				// VERIFICA SE O PAGAMENTO JA FOI INFORMADO NESSA SESSAO
				this.verificarPagamentoJaInformado(colecaoPagamentoInserirHelper, pagamentoInformado.getPagamento());

				colecaoPagamentoInserirHelper.add(pagamentoInformado);

			}else{

				colecaoPagamentoInserirHelper = new ArrayList();

				// VALIDA E GERA O PAGAMENTO EM MEMORIA
				colecaoPagamentoInserirHelper.add(this.validarDadosPagamento(pagamentoActionForm, fachada, httpServletRequest, 0));

				sessao.setAttribute("colecaoPagamentoInserirHelper", colecaoPagamentoInserirHelper);
			}

			// LIMPA OS CAMPOS DO FORMULARIO PRA RECEBER OS DADOS DO PROXIMO PAGAMENTO
			this.limparDadosPagamento(pagamentoActionForm);
		}

		/*
		 * Colocado por Raphael Rossiter em 24/09/2007 (Analista: Aryed Lins)
		 * OBJ: Remover um pagamento informado
		 */
		if(removerPagamento != null && !removerPagamento.equals("")){

			Collection<InserirPagamentoManualHelper> colecaoPagamentoInserirHelper = (Collection) sessao
							.getAttribute("colecaoPagamentoInserirHelper");

			// REMOVE O PAGAMENTO DA MEMORIA
			this.removerPagamento(colecaoPagamentoInserirHelper, removerPagamento, sessao);

			// LIMPA OS CAMPOS DO FORMULARIO PRA RECEBER OS DADOS DO PROXIMO PAGAMENTO
			this.limparDadosPagamento(pagamentoActionForm);
		}

		return retorno;
	}

	// /**
	// * Este fluxo secund�rio tem como objetivo pesquisar a localidade digitada
	// pelo usu�rio
	// *
	// * [FS0007] - Verificar exist�ncia da localidade
	// *
	// * @author Pedro Alexandre
	// * @date 16/02/2006
	// *
	// * @param idLocalidadeDigitada
	// * @return
	// */
	// private Localidade pesquisarLocalidadeDigitada(Integer
	// idLocalidadeDigitada){
	//    	
	// //Varai�vel que vai armazenar a localidade digitada pelo usu�rio
	// Localidade localidadeDigitada = null;
	//    	
	// //Cria uma inst�ncia da fachada
	// Fachada fachada = Fachada.getInstancia();
	//    	
	// //Pesquisa a localidade informada pelo usu�rio no sistema
	// FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
	// filtroLocalidade.adicionarParametro(new
	// ParametroSimples(FiltroLocalidade.ID, idLocalidadeDigitada));
	// Collection<Localidade> colecaoLocalidade =
	// fachada.pesquisar(filtroLocalidade, Localidade.class.getName());
	//    	
	// //Caso exista a localidade no sistema
	// //Retorna para o usu�rio a localidade retornada pela pesquisa
	// //Caso contr�rio retorna um objeto nulo
	// if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){
	// localidadeDigitada =(Localidade)
	// Util.retonarObjetoDeColecao(colecaoLocalidade);
	// }
	//    	
	// //Retorna a localdiade encontrada ou nulo se n�o existir
	// return localidadeDigitada;
	// }
	//    
	// /**
	// * Este fluxo secund�rio tem como objetivo pesquisar o im�vel digitado
	// pelo usu�rio
	// *
	// * [FS0008] - Verificar exist�ncia da matr�cula do im�vel
	// *
	// * @author Pedro Alexandre
	// * @date 16/02/2006
	// *
	// * @param idImovelDigitado
	// * @return
	// */
	// private Imovel pesquisarImovelDigitado(Integer idImovelDigitado){
	//    	
	// //Cria a vari�vel que vai armazenar o im�vel pesquisado
	// Imovel imovelDigitado = null;
	//   	
	// //Cria uma inst�ncia da fachada
	// Fachada fachada = Fachada.getInstancia();
	//    	
	// //Pesquisa o im�vel informado pelo usu�rio no sistema
	// FiltroImovel filtroImovel = new FiltroImovel();
	// filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
	// filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID,
	// idImovelDigitado));
	// Collection colecaoImovel = fachada.pesquisar(filtroImovel,
	// Imovel.class.getName());
	//    	
	// //Caso exista o im�vel no sistema
	// //Retorna para o usu�rio o im�vel retornado pela pesquisa
	// //Caso contr�rio retorna um objeto nulo
	// if(colecaoImovel != null && !colecaoImovel.isEmpty()){
	// imovelDigitado =(Imovel) Util.retonarObjetoDeColecao(colecaoImovel);
	// }
	//   	
	// //Retorna o im�vel encontrado ou nulo se n�o existir
	// return imovelDigitado;
	// }
	//    
	// /**
	// * Este fluxo secund�rio tem como objetivo pesquisar o cliente digitado
	// pelo usu�rio
	// *
	// * [FS0011] - Verificar exist�ncia do c�digo do cliente
	// *
	// * @author Pedro Alexandre
	// * @date 16/02/2006
	// *
	// * @param idClienteDigitado
	// * @return
	// */
	// private Cliente pesquisarClienteDigitado(Integer idClienteDigitado){
	//    	
	// //Cria a vari�vel que vai armazenar o cliente pesquisado
	// Cliente clienteDigitado = null;
	//    	
	// //Cria uma inst�ncia da fachada
	// Fachada fachada = Fachada.getInstancia();
	//    	
	// //Pesquisa o cliente informado pelo usu�rio no sistema
	// FiltroCliente filtroCliente = new FiltroCliente();
	// filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID,
	// idClienteDigitado));
	// Collection<Cliente> colecaoCliente = fachada.pesquisar(filtroCliente,
	// Cliente.class.getName());
	//    	
	// //Caso exista o cliente no sistema
	// //Retorna para o usu�rio o cliente retornado pela pesquisa
	// //Caso contr�rio retorna um objeto nulo
	// if(colecaoCliente != null && !colecaoCliente.isEmpty()){
	// clienteDigitado =(Cliente) Util.retonarObjetoDeColecao(colecaoCliente);
	// }
	//    	
	// //Retorna o cliente encontrado ou nulo se n�o existir
	// return clienteDigitado;
	// }
	// 
	//
	// /**
	// * Verifica se a localidade informada � a mesma do im�vel informado
	// *
	// * [FS0009] - Verificar localidade da matr�cula do im�vel
	// *
	// * @author Pedro Alexandre
	// * @date 16/02/2006
	// *
	// * @param idLocalidadeInformada
	// * @param imovelInformado
	// * @return
	// */
	// private boolean verificarLocalidadeMatriculaImovel(String
	// idLocalidadeInformada, Imovel imovelInformado){
	//
	// //Recupera a localidade do im�vel
	// Localidade localidadeImovel = imovelInformado.getLocalidade();
	//	 
	// //Caso a localidade informada pelo usu�rio seja a mesma do im�vel
	// //Retorna "true" indicandoque a localidade � a mesma
	// //Caso contr�rio retorna "false" indicando que a localidade � diferente
	// if(idLocalidadeInformada.equalsIgnoreCase(localidadeImovel.getId().toString())
	// ){
	// return true;
	// }else{
	// return false;
	// }
	// }
	// 
	// /**
	// * Pesquisa a conta do im�vel com a refer�ncia informada pelo usu�rio
	// *
	// * [FS0012] - Verificar exist�ncia da conta
	// *
	// * @author Pedro Alexandre
	// * @date 16/02/2006
	// *
	// * @param idImovel
	// * @param referenciaConta
	// * @return
	// */
	// private Conta pesquisarContaDigitada(String idImovel,String
	// referenciaConta){
	// 	
	// //Vari�vel que vai armazenar a conta pesquisada
	// Conta contaDigitada = null;
	// 	
	// //Cria uma inst�ncia da fachada
	// Fachada fachada = Fachada.getInstancia();
	// 	
	// //Formata a refer�ncia da conta informada para o formato (AAAAMM) sem a
	// barra
	// String anoMesConta =
	// Util.formatarMesAnoParaAnoMesSemBarra(referenciaConta);
	//	
	// //Cria o filtro de conta e seta todos os par�metros para pesquisar a
	// conta do im�vel
	// FiltroConta filtroConta = new FiltroConta();
	// filtroConta.adicionarParametro(new
	// ParametroSimples(FiltroConta.IMOVEL_ID,idImovel));
	// filtroConta.adicionarParametro(new
	// ParametroSimples(FiltroConta.REFERENCIA,anoMesConta));
	// filtroConta.adicionarParametro(new
	// ParametroSimples(FiltroConta.DEBITO_CREDITO_SITUACAO_ATUAL,DebitoCreditoSituacao.NORMAL,
	// ParametroSimples.CONECTOR_OR,3));
	// filtroConta.adicionarParametro(new
	// ParametroSimples(FiltroConta.DEBITO_CREDITO_SITUACAO_ATUAL,DebitoCreditoSituacao.RETIFICADA,
	// ParametroSimples.CONECTOR_OR));
	// filtroConta.adicionarParametro(new
	// ParametroSimples(FiltroConta.DEBITO_CREDITO_SITUACAO_ATUAL,DebitoCreditoSituacao.INCLUIDA));
	// Collection colecaoContas = fachada.pesquisar(filtroConta,
	// Conta.class.getName());
	//
	// //Caso exista a conta para a refer�ncia informada cadastrada no sistema
	// //Retorna para o usu�rio a conta retornada pela pesquisa
	// //Caso contr�rio retorna um objeto nulo
	// if(colecaoContas != null && !colecaoContas.isEmpty()){
	// contaDigitada = (Conta)Util.retonarObjetoDeColecao(colecaoContas);
	// }
	// 	
	// //Retorna a conta encontrada ou nulo se n�o existir a conta
	// return contaDigitada;
	// }
	// 
	// /**
	// * Pesquisa o d�bito a cobrar do im�vel informado pelo usu�rio
	// *
	// * [FS0024] - Verificar exist�ncia do d�bito a cobrar
	// *
	// * @author Pedro Alexandre
	// * @date 16/02/2006
	// *
	// * @param idImovel
	// * @param idDebitoACobrar
	// * @return
	// */
	// private DebitoACobrar pesquisarDebitoACobrarDigitado(String
	// idImovel,String idDebitoACobrar){
	//
	// //Cria a vari�vel que vai armazenar o d�bito a cobrar pesquisado
	// DebitoACobrar debitoACobrarDigitado = null;
	//	 	
	// //Cria uma inst�ncia da fachada
	// Fachada fachada = Fachada.getInstancia();
	//	 	
	// //Cria o filtro de d�bito a cobrar e seta todos os par�metros para
	// pesquisar o d�bito a cobrar do im�vel
	// FiltroDebitoACobrar filtroDebitoACobrar = new FiltroDebitoACobrar();
	// filtroDebitoACobrar.adicionarParametro(new
	// ParametroSimples(FiltroDebitoACobrar.IMOVEL_ID,idImovel));
	// filtroDebitoACobrar.adicionarParametro(new
	// ParametroSimples(FiltroDebitoACobrar.ID,idDebitoACobrar));
	// filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");
	// Collection colecaoDebitoACobrar = fachada.pesquisar(filtroDebitoACobrar,
	// DebitoACobrar.class.getName());
	//		
	// //Caso exista o d�bito a cobrar para o im�vel informado cadastrado no
	// sistema
	// //Retorna para o usu�rio o d�bito a cobrar retornado pela pesquisa
	// //Caso contr�rio retorna um objeto nulo
	// if(colecaoDebitoACobrar == null || colecaoDebitoACobrar.isEmpty()){
	// throw new ActionServletException("atencao.naocadastrado",null, "D�bito a
	// Cobrar");
	// }else{
	// debitoACobrarDigitado =
	// (DebitoACobrar)Util.retonarObjetoDeColecao(colecaoDebitoACobrar);
	// }
	//	 	
	// //Retorna o d�bito a cobrar encontrado ou nulo se n�o existir o d�bito a
	// cobrar
	// return debitoACobrarDigitado;
	// }
	//
	//
	// /**
	// * <Breve descri��o sobre o fluxo secund�rio>
	// *
	// * [FS0022] - Verificar exist�ncia da guia de pagamento
	// *
	// * @author Pedro Alexandre
	// * @date 16/02/2006
	// *
	// * @param idImovel
	// * @param idCliente
	// * @param idGuiaPagamento
	// * @return
	// */
	// private GuiaPagamento pesquisarGuiaPagamentoDigitada(String idImovel,
	// String idCliente, String idGuiaPagamento){
	//	
	// //Cria a vari�vel que vai armazenar a guia de pagamento pequisada
	// GuiaPagamento guiaPagamentoDigitada = null;
	//	 	
	// //Cria uma inst�ncia da fachada
	// Fachada fachada = Fachada.getInstancia();
	//	 	
	// //Cria o filtro de guia de pagamento
	// FiltroGuiaPagamento filtroGuiaPagamento = new FiltroGuiaPagamento();
	//		
	// //Caso o usu�rio tenha informado o im�vel, seta o c�digo do im�vel no
	// filtro
	// //Caso contr�rio, o usu�rio tenha informado o cliente seta o c�digo do
	// cliente no filtro
	// //Caso o usu�rio n�o tenha informado nem o im�vel nem o cliente levanta
	// uma exce��o
	// //para o usu�rio informando que tem de informar o cliente ou o im�vel
	// if(idImovel != null && !idImovel.trim().equalsIgnoreCase("")){
	// filtroGuiaPagamento.adicionarParametro(new
	// ParametroSimples(FiltroGuiaPagamento.IMOVEL_ID,idImovel));
	// }else if(idCliente != null || !idCliente.trim().equalsIgnoreCase("")){
	// filtroGuiaPagamento.adicionarParametro(new
	// ParametroSimples(FiltroGuiaPagamento.CLIENTE_ID,idCliente));
	// }else{
	// throw new ActionServletException("atencao.naoinformado", null, "Im�vel ou
	// Cliente");
	// }
	//
	// //Pesquisa a guia de pagamento de acordo com os par�metros no filtro
	// filtroGuiaPagamento.adicionarParametro(new
	// ParametroSimples(FiltroGuiaPagamento.ID,idGuiaPagamento));
	// filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");
	// Collection colecaoGuiaPagamento = fachada.pesquisar(filtroGuiaPagamento,
	// GuiaPagamento.class.getName());
	//		
	// //Caso exista a guia de pagamento para o im�vel ou o cliente informado
	// cadastrado no sistema
	// //Retorna para o usu�rio a guia de pagamento retornada pela pesquisa
	// //Caso contr�rio retorna um objeto nulo
	// if(colecaoGuiaPagamento == null || colecaoGuiaPagamento.isEmpty()){
	// throw new ActionServletException("atencao.naocadastrado",null, "Guia de
	// Pagamento");
	// }else{
	// guiaPagamentoDigitada =
	// (GuiaPagamento)Util.retonarObjetoDeColecao(colecaoGuiaPagamento);
	// }
	//	 	
	// //Retorna a guia de pagamento encontrada ou nulo se n�o existir aa guia
	// de pagamento
	// return guiaPagamentoDigitada;
	// }

	/**
	 * Este fluxo secund�rio tem como objetivo pesquisar o im�vel informado pelo usu�rio
	 * [FS0008] Verificar exist�ncia da matr�cula do im�vel
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idImovel
	 * @return
	 */
	public Imovel verificarExistenciaMatriculaImovel(String idImovel){

		// Cria a vari�vel que vai armazenar o im�vel pesquisado
		Imovel imovelEncontrado = null;

		// Cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Pesquisa o im�vel informado pelo usu�rio no sistema
		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
		Collection colecaoImoveis = fachada.pesquisar(filtroImovel, Imovel.class.getName());

		// Caso exista o im�vel no sistema
		// Retorna para o usu�rio o im�vel retornado pela pesquisa
		// Caso contr�rio retorna um objeto nulo
		if(colecaoImoveis == null || colecaoImoveis.isEmpty()){
			// throw new ActionServletException("atencao.naocadastrado",null,
			// "Matr�cula do im�vel");
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Matr�cula");
		}else{
			imovelEncontrado = (Imovel) Util.retonarObjetoDeColecao(colecaoImoveis);
		}

		// Retorna o im�vel encontrado ou nulo se n�o existir
		return imovelEncontrado;
	}

	/**
	 * Este fluxo secund�rio tem como objetivo pesquisar a localidade digitada pelo usu�rio
	 * [FS0007] Verificar exist�ncia da localidade
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idLocalidade
	 * @return
	 */
	private Localidade verificarExistenciaLocalidade(Integer idLocalidade){

		// Vari�vel que vai armazenar a localidade digitada pelo usu�rio
		Localidade localidadeEncontrada = null;

		// Cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Pesquisa a localidade informada pelo usu�rio no sistema
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));
		Collection<Localidade> colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

		// Caso exista a localidade no sistema
		// Retorna para o usu�rio a localidade retornada pela pesquisa
		// Caso contr�rio retorna um objeto nulo
		if(colecaoLocalidade == null || colecaoLocalidade.isEmpty()){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade");
		}else{
			localidadeEncontrada = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);
		}

		// Retorna a localdiade encontrada ou nulo se n�o existir
		return localidadeEncontrada;
	}

	/**
	 * Verifica se a localidade informada � a mesma do im�vel informado
	 * [FS0009] Verificar localidade da matr�cula do im�vel
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idLocalidade
	 * @param imovel
	 * @return
	 */
	private boolean verificarLocalidadeMatriculaImovel(String idLocalidade, Imovel imovel){

		// Caso o im�vel tenha sido informado
		if(imovel != null){
			// Recupera a localidade do im�vel
			Localidade localidadeImovel = imovel.getLocalidade();

			Integer codigoLocalidade = Integer.valueOf(idLocalidade);

			// Caso a localidade informada pelo usu�rio seja a mesma do im�vel
			// Retorna "true" indicandoque a localidade � a mesma
			// Caso contr�rio retorna "false" indicando que a localidade � diferente
			if(codigoLocalidade.intValue() == localidadeImovel.getId().intValue()){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}

	/**
	 * Verifica se existe conta para o im�vel e refer�ncia informados, caso n�o xista retorna uma
	 * conta nula
	 * [FS0012] Verificar exist�ncia da conta
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param referenciaConta
	 * @param idImovel
	 * @return
	 */
	private Conta verificarExistenciaConta(String referenciaConta, String idImovel){

		// Vari�vel que vai armazenar a conta pesquisada
		Conta contaEncontrada = null;

		// Cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Formata a refer�ncia da conta informada para o formato (AAAAMM) sem a barra
		String anoMesConta = Util.formatarMesAnoParaAnoMesSemBarra(referenciaConta);

		// Cria o filtro de conta e seta todos os par�metros para pesquisar a conta do im�vel
		FiltroConta filtroConta = new FiltroConta();
		filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.IMOVEL_ID, idImovel));
		filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.REFERENCIA, anoMesConta));
		filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.DEBITO_CREDITO_SITUACAO_ATUAL_ID, DebitoCreditoSituacao.NORMAL,
						ParametroSimples.CONECTOR_OR, 3));
		filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.DEBITO_CREDITO_SITUACAO_ATUAL_ID, DebitoCreditoSituacao.RETIFICADA,
						ParametroSimples.CONECTOR_OR));
		filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.DEBITO_CREDITO_SITUACAO_ATUAL_ID, DebitoCreditoSituacao.INCLUIDA));
		Collection colecaoContas = fachada.pesquisar(filtroConta, Conta.class.getName());

		// Caso exista a conta para a refer�ncia informada cadastrada no sistema
		// Retorna para o usu�rio a conta retornada pela pesquisa
		// Caso contr�rio retorna um objeto nulo
		if(colecaoContas != null && !colecaoContas.isEmpty()){
			contaEncontrada = (Conta) Util.retonarObjetoDeColecao(colecaoContas);
		}

		// Retorna a conta encontrada ou nulo se n�o existir a conta
		return contaEncontrada;
	}

	/**
	 * Verifica se o usu�rio preencheu o im�vel ou o cliente, n�o pode existir os doi nem nenhum
	 * [FS0010] Verificar preenchimento do im�vel e do cliente
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idImovel
	 * @param idCliente
	 */
	private void verificarPreeenchimentoImovelECliente(String idImovel, String idCliente){

		// Caso o usu�rio n�o tenha informado o im�vel
		if(idImovel == null || idImovel.trim().equalsIgnoreCase("")){
			// Caso o usu�rio n�o tenha informado o cliente, levanta uma exce��o indicando que o
			// usu�rio n�o informou nem o im�vel nem o cliente
			if(idCliente == null || idCliente.trim().equalsIgnoreCase("")){
				throw new ActionServletException("atencao.naoinformado", null, "Matr�cula do Im�vel ou C�digo do Cliente");
			}
		}else{
			// Caso o usu�rio tenha informado o im�vel e o cliente, levanta uma exce��o para
			// indicando que s� pode informar um dos dois
			if(idCliente != null && !idCliente.trim().equalsIgnoreCase("")){
				throw new ActionServletException("atencao.cliente_imovel_informado");
			}
		}
	}

	/**
	 * Este fluxo secund�rio tem como objetivo verificar a exist�ncia do cliente informado pelo
	 * usu�rio
	 * [FS0011] Verificar exist�ncia do c�digo do cliente
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idCliente
	 * @return
	 */
	private Cliente verificarExistenciaCodigoCliente(Integer idCliente){

		// Cria a vari�vel que vai armazenar o cliente pesquisado
		Cliente clienteEncontrado = null;

		// Cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Pesquisa o cliente informado pelo usu�rio no sistema
		FiltroCliente filtroCliente = new FiltroCliente();
		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente));
		Collection<Cliente> colecaoCliente = fachada.pesquisar(filtroCliente, Cliente.class.getName());

		// Caso exista o cliente no sistema
		// Retorna para o usu�rio o cliente retornado pela pesquisa
		// Caso contr�rio retorna um objeto nulo
		if(colecaoCliente != null && !colecaoCliente.isEmpty()){
			clienteEncontrado = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);
		}else{
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Cliente");
		}

		// Retorna o cliente encontrado ou nulo se n�o existir
		return clienteEncontrado;
	}

	/**
	 * Verifica se o usu�rio informou o c�digo da guia de pagamento e o tipo de d�bito,
	 * s� pode ser informado um dos dois
	 * [FS0021] Verificar preenchimento da guia de pagamento e do tipo de d�bito
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idGuiaPagamento
	 * @param idTipoDebito
	 */
	private void verificarPreeenchimentoGuiaPagamentoETipoDebito(String idGuiaPagamento, String idTipoDebito){

		// Caso o usu�rio n�o informou a guia de pagamento
		if(idGuiaPagamento == null || idGuiaPagamento.trim().equalsIgnoreCase("")){
			// Caso o usu�rio n�o informou o tipo de d�bito, levanta uma exce��o para o us�rio
			// indicando que o usu�rio precisa informar ou a guia ou o tipo de d�bito
			if(idTipoDebito == null || idTipoDebito.trim().equalsIgnoreCase("")){
				throw new ActionServletException("atencao.naoinformado", null, "Guia Pagamento ou Tipo de D�bito");
			}
		}else{
			// Caso o usu�rio informou a guia de pagamento e o tipo de d�bito,
			// levanta uma exce��o para o us�rio indicando que o usu�rio informou a guia e o tipo de
			// d�bito
			if(idTipoDebito != null && !idTipoDebito.trim().equalsIgnoreCase("")){
				throw new ActionServletException("atencao.guia_pagamento_tipo_debito_informado");
			}
		}
	}

	/**
	 * Verifica se existe guia de pagamento como c�digo da guia informado para o cliente ou im�vel
	 * [FS0022] Verificar exist�ncia da guia de pagamento
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idImovel
	 * @param idCliente
	 * @param idGuiaPagamento
	 * @return
	 */
	private GuiaPagamento verificarExistenciaGuiaPagamento(String idImovel, String idCliente, String idGuiaPagamento){

		// Cria a vari�vel que vai armazenar a guiade pagamento pesquisada
		GuiaPagamento guiaPagamentoDigitada = null;

		// Cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Cria o filtro da guia para pesquisar
		FiltroGuiaPagamento filtroGuiaPagamento = new FiltroGuiaPagamento();

		// Caso o usu�rio tenha informado o im�vel, seta o c�digo do im�vel no filtro da guia
		if(idImovel != null && !idImovel.trim().equalsIgnoreCase("")){
			filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.IMOVEL_ID, idImovel));

			// Caso o usu�rio tenha informado o cliente, seta o c�digo do cliente no filtro da guia
		}else if(idCliente != null && !idCliente.trim().equalsIgnoreCase("")){
			filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.CLIENTE_ID, idCliente));

			// Caso n�o tenha informado nem o im�vel e nem o cliente, levanta uma exce��o para o
			// usu�rio
			// indicando que o im�vel ou o cliente tem de ser informados
		}else{
			throw new ActionServletException("atencao.naoinformado", null, "Im�vel ou Cliente");
		}

		// Seta o c�digoda guia no filtro e indica quais objetos para carregar no retorno da
		// pesquisa
		filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.ID, idGuiaPagamento));
		// filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("localidade");

		// Pesquisa a guia de pagamento no sistema
		Collection colecaoGuiaPagamento = fachada.pesquisar(filtroGuiaPagamento, GuiaPagamento.class.getName());

		// Caso n�o exista guia de pagamento cadastrada no sistema, levanta uma exce��o para o
		// usu�rio indicando que a guia de apagamento n�o est� cadastrada
		// Caso contr�rio recupera a guia de pagamento pesquisada
		if(colecaoGuiaPagamento == null || colecaoGuiaPagamento.isEmpty()){
			// throw new ActionServletException("atencao.naocadastrado",null, "Guia de Pagamento");
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Guia de Pagamento");
		}else{
			guiaPagamentoDigitada = (GuiaPagamento) Util.retonarObjetoDeColecao(colecaoGuiaPagamento);
		}

		// Retorna a guia de pagamento pesquisada ou um objeto vazio se a guia n�o existir
		return guiaPagamentoDigitada;
	}

	/**
	 * Verifica se a localidade informada � a mesma da guia de pagamento
	 * [FS0014] Verificar localidade da guia de pagamento
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param guiaPagamento
	 * @param idLocalidade
	 */
	private void verificarLocalidadeGuiaPagamento(GuiaPagamento guiaPagamento, String idLocalidade){

		// Caso o usu�rio tenha informado a localidade
		if(idLocalidade != null && !idLocalidade.trim().equalsIgnoreCase("")){
			// Caso a localidade da guia de pagamento seja diferente da localidade informada
			if(!guiaPagamento.getLocalidade().getId().equals(Integer.valueOf(idLocalidade))){

				// Cria a mensagem que vai ser exibida ao usu�rio
				// e levanta a exce��o
				String mensagem = "A Localidade da Guia de Pagamento " + guiaPagamento.getLocalidade().getId()
								+ " � diferente da Localidade informada " + idLocalidade;
				throw new ActionServletException("atencao.localidade_guia_pagamento_diferente", null, mensagem);
			}
		}
	}

	/**
	 * Este fluxo secund�rio tem como objetivo pesquisar o tipo de d�bito informado pelo usu�rio
	 * [FS0020] Verificar exist�ncia do tipo de d�bito
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idTipoDebito
	 * @return
	 */
	private DebitoTipo verificarExistenciaTipoDebito(Integer idTipoDebito){

		// Vari�vel que vai armazenar o tipo de dp�bito digitado pelo usu�rio
		DebitoTipo tipoDebitoEncontrado = null;

		// Cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Pesquisa o tipo de d�bito informado pelo usu�rio no sistema
		FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
		filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, idTipoDebito));
		Collection<DebitoTipo> colecaoDebitoTipo = fachada.pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());

		// Caso exista o tipo de d�bito no sistema
		// Retorna para o usu�rio o tipo de d�bito retornado pela pesquisa
		// Caso contr�rio retorna um objeto nulo
		if(colecaoDebitoTipo == null || colecaoDebitoTipo.isEmpty()){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Tipo de D�bito");
		}else{
			tipoDebitoEncontrado = (DebitoTipo) Util.retonarObjetoDeColecao(colecaoDebitoTipo);
		}

		// Retorna o tipo de d�bito encontrado ou nulo se n�o existir
		return tipoDebitoEncontrado;
	}

	/**
	 * Verifica a exist�ncia de guia de pagamento com o tipo de d�bito e o im�vel informados
	 * [FS0013] Verificar exist�ncia de guia de pagamento com tipo de d�bito informado
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idGuiaPagamento
	 * @param idImovel
	 */
	private GuiaPagamento verificarExistenciaGuiaPagamentoComTipoDebito(DebitoTipo tipoDebito, String idImovel, String idCliente){

		// Cria a vari�vel que vai armazenar a guia de pagamento pesquisada
		GuiaPagamento guiaPagamento = null;

		// Cria o filtro de guia de pagamento, e seta os par�metros para pesquisar
		FiltroGuiaPagamento filtroGuiaPagamento = new FiltroGuiaPagamento();
		// filtroGuiaPagamento.adicionarParametro(new
		// ParametroSimples(FiltroGuiaPagamento.DEBITO_TIPO_ID, tipoDebito.getId()));
		filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.DEBITO_CREDITO_SITUACAO_ATUAL_ID,
						DebitoCreditoSituacao.NORMAL));

		// Caso o usu�rio tenha informado a matr�cula do im�vel,
		// seta a metr�culo do im�vel no filtro
		// Caso contr�rio seta o c�digodo cliente no filtro
		if(idImovel != null && !idImovel.trim().equalsIgnoreCase("")){
			filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.IMOVEL_ID, idImovel));
		}else{
			filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.CLIENTE_ID, idCliente));
		}

		// Pesquisa as guias de pagamento no sistema
		Collection colecaoGuiaPagamento = Fachada.getInstancia().pesquisar(filtroGuiaPagamento, GuiaPagamento.class.getName());

		// Caso exista guia de pagamento cadastrada no sistema com os par�metros informados no
		// filtro
		if(colecaoGuiaPagamento != null && !colecaoGuiaPagamento.isEmpty()){

			// Caso exista mais que uma guia de pagamento cadastrada
			if(colecaoGuiaPagamento.size() > 1){

				// Cria a vari�vel que vai armazenar a mensagem que vai ser exibida ao usu�rio
				String mensagem = null;

				// Caso a pequisa foi para im�vel
				// Cria a mensagem para im�vel
				// Caso contr�rio cria a mensagem para cliente
				if(idImovel != null && !idImovel.trim().equalsIgnoreCase("")){
					mensagem = "H� mais de uma Guia de Pagamento com o tipo de d�bito " + tipoDebito.getDescricao() + " para o Im�vel "
									+ idImovel + ". Efetue uma pesquisa para selecionar a Guia";
				}else{
					mensagem = "H� mais de uma Guia de Pagamento com o tipo de d�bito " + tipoDebito.getDescricao() + " para o Cliente "
									+ idCliente + ". Efetue uma pesquisa para selecionar a Guia";
				}
				// levanta a exce��o para o usu�rio com a mensagem criada
				throw new ActionServletException("atencao.descricao_concatenada", null, mensagem);

			}else{
				// Caso s� exista apenas uma guia de pagamento cadastrada para o tipo de d�bito
				guiaPagamento = (GuiaPagamento) Util.retonarObjetoDeColecao(colecaoGuiaPagamento);
			}
		}

		// Retorna a guia de pagamento encontrada ou nulo se n�o existir a guia de pagamento
		return guiaPagamento;
	}

	/**
	 * Verifica se o usu�rio informou o c�digo do d�bito a cobrar e o tipo de d�bito,
	 * s� pode ser informado um dos dois
	 * [FS0023] Verificar preenchimento do d�bito a cobrar e do tipo de d�bito
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idDebitoACobrar
	 * @param idTipoDebito
	 */
	private void verificarPreeenchimentoDebitoACobrarETipoDebito(String idDebitoACobrar, String idTipoDebito){

		// Caso o usu�rio n�o informou o d�bito a cobrar
		if(idDebitoACobrar == null || idDebitoACobrar.trim().equalsIgnoreCase("")){
			// Caso o usu�rio n�o informou o tipo de d�bito, levanta uma exce��o para o us�rio
			// indicando que o usu�rio precisa informar ou o d�bito a cobrar ou o tipo de d�bito
			if(idTipoDebito == null || idTipoDebito.trim().equalsIgnoreCase("")){
				throw new ActionServletException("atencao.naoinformado", null, "D�bito a Cobrar ou Tipo de D�bito");
			}
		}else{
			// Caso o usu�rio informou o d�bito a cobrar e o tipo de d�bito, levanta uma exce��o
			// para o us�rio indicando que o usu�rio informou o d�bito a cobrar e o tipo de d�bito
			if(idTipoDebito != null && !idTipoDebito.trim().equalsIgnoreCase("")){
				throw new ActionServletException("atencao.debto_a_cobrar_tipo_debito_informado");
			}
		}
	}

	/**
	 * Verifica se o d�bito a cobrar do im�vel informado pelo usu�rio existe no sistema
	 * [FS0024] Verificar exist�ncia do d�bito a cobrar
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idImovel
	 * @param idDebitoACobrar
	 * @return
	 */
	private DebitoACobrar verificarExistenciaDebitoACobrar(String idImovel, String idDebitoACobrar){

		// Cria a vari�vel que vai armazenar o d�bito a cobrar pesquisado
		DebitoACobrar debitoACobrarDigitado = null;

		// Cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Cria o filtro de d�bito a cobrar e seta todos os par�metros para pesquisar o d�bito a
		// cobrar do im�vel
		FiltroDebitoACobrar filtroDebitoACobrar = new FiltroDebitoACobrar();
		filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.IMOVEL_ID, idImovel));
		filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.ID, idDebitoACobrar));
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");
		Collection colecaoDebitoACobrar = fachada.pesquisar(filtroDebitoACobrar, DebitoACobrar.class.getName());

		// Caso exista o d�bito a cobrar para o im�vel informado cadastrado no sistema
		// Retorna para o usu�rio o d�bito a cobrar retornado pela pesquisa
		// Caso contr�rio retorna um objeto nulo
		if(colecaoDebitoACobrar == null || colecaoDebitoACobrar.isEmpty()){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "D�bito a Cobrar");
		}else{
			debitoACobrarDigitado = (DebitoACobrar) Util.retonarObjetoDeColecao(colecaoDebitoACobrar);
		}

		// Retorna o d�bito a cobrar encontrado ou nulo se n�o existir o d�bito a cobrar
		return debitoACobrarDigitado;
	}

	/**
	 * Verifica se a localidade informada � a mesma do d�bito a cobrar
	 * [FS0017] Verificar localidade do d�bito a cobrar
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param debitoACobrar
	 * @param idLocalidade
	 */
	private void verificarLocalidadeDebitoACobrar(DebitoACobrar debitoACobrar, String idLocalidade){

		// Caso o usu�rio tenha informado a localidade
		if(idLocalidade != null && !idLocalidade.trim().equalsIgnoreCase("")){

			// Caso a localidade do d�bito a cobrar seja diferente da localidade informada
			if(!debitoACobrar.getLocalidade().getId().equals(Integer.valueOf(idLocalidade))){

				// Cria a mensagem que vai ser exibida ao usu�rio
				// e levanta a exce��o
				String mensagem = "A Localidade do D�bito a Cobrar " + debitoACobrar.getLocalidade().getId()
								+ " � diferente da Localidade informada " + idLocalidade;
				throw new ActionServletException("atencao.localidade_debito_a_cobrar_diferente", null, mensagem);
			}
		}
	}

	/**
	 * Verifica a exist�ncia de d�bito a cobrar com o tipo de d�bito e o im�vel informados
	 * [FS0016] Verificar exist�ncia de d�bito a cobrar com tipo de d�bito informado
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idTipoDebito
	 * @param idImovel
	 */
	private DebitoACobrar verificarExistenciaDebitoACobrarComTipoDebito(DebitoTipo tipoDebito, String idImovel){

		// Cria a vari�vel que vai armazenar o d�bito a cobrar pesquisado
		DebitoACobrar debitoACobrar = null;

		// Cria o filtro de d�bito a cobrar, e seta os par�metros para pesquisar
		FiltroDebitoACobrar filtroDebitoACobrar = new FiltroDebitoACobrar();
		filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.DEBITO_TIPO_ID, tipoDebito.getId()));
		filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.IMOVEL_ID, idImovel));
		filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.DEBITO_CREDITO_SITUACAO_ATUAL_ID,
						DebitoCreditoSituacao.NORMAL));
		Collection colecaoDebitoACobrar = Fachada.getInstancia().pesquisar(filtroDebitoACobrar, DebitoACobrar.class.getName());

		// Caso exista d�bito a cobrar cadastrado com o tipo de d�bito informado
		if(colecaoDebitoACobrar != null && !colecaoDebitoACobrar.isEmpty()){

			// Caso exista mais que um d�bito a cobrar cadastrado para o tipo de d�bito
			// Monta a mensagem para o usu�rio e levanta a exce��o
			if(colecaoDebitoACobrar.size() > 1){
				String mensagem = "H� mais de um D�bito a Cobrar com o tipo de d�bito " + tipoDebito.getDescricao() + " para o Im�vel "
								+ idImovel;
				throw new ActionServletException("atencao.descricao_concatenada", null, mensagem);
			}else{
				// Caso s� exista apenas um d�bito a cobrar cadastrado para o tipo de d�bito
				debitoACobrar = (DebitoACobrar) Util.retonarObjetoDeColecao(colecaoDebitoACobrar);
			}
		}

		// Retorna o d�bito a cobrar encontrado ou nulo se n�o existir o d�bito a cobrar
		return debitoACobrar;
	}

	/**
	 * @author Raphael Rossiter
	 * @date 24/09/2007
	 */
	private InserirPagamentoManualHelper validarDadosPagamento(DynaValidatorActionForm pagamentoActionForm, Fachada fachada,
					HttpServletRequest httpServletRequest, Integer idPagamentoMemoria){

		// Cria o objeto que vai armazenar os dados de pagamento
		Pagamento pagamento = new Pagamento();
		InserirPagamentoManualHelper inserirPagamentoManualHelper = new InserirPagamentoManualHelper();

		// [FS0001] - Validar data do pagamento
		// Recupera a data de pagamento e verifica se a data � uma data v�lida
		String dataPagamentoString = (String) pagamentoActionForm.get("dataPagamento");
		Date dataPagamento = null;
		SimpleDateFormat dataFormato = new SimpleDateFormat("dd/MM/yyyy");
		try{
			dataPagamento = dataFormato.parse(dataPagamentoString);
		}catch(ParseException ex){
			throw new ActionServletException("atencao.data_pagamento_invalida");
		}

		// Recupera a forma de arrecadaa��o e pesquisa o objeto no sistema
		String idFormaArrecadacao = (String) pagamentoActionForm.get("idFormaArrecadacao");
		if(idFormaArrecadacao == null || ("".equals(idFormaArrecadacao))){
			throw new ActionServletException("atencao.forma_arrecadacao.naoinformada");
		}

		ArrecadacaoForma arrecadacaoForma = new ArrecadacaoForma();
		arrecadacaoForma.setId(Integer.valueOf(idFormaArrecadacao));

		// Recupera o aviso banc�rio e pequisa o objeto no sistema
		String idAvisoBancario = (String) pagamentoActionForm.get("idAvisoBancario");
		FiltroAvisoBancario filtroAvisoBancario = new FiltroAvisoBancario();
		filtroAvisoBancario.adicionarParametro(new ParametroSimples(FiltroAvisoBancario.ID, idAvisoBancario));
		AvisoBancario avisoBancario = (AvisoBancario) (fachada.pesquisar(filtroAvisoBancario, AvisoBancario.class.getName())).iterator()
						.next();

		// Recupera o valor do pagamento
		String valorTemp = (String) pagamentoActionForm.get("valorPagamento");
		BigDecimal valorPagamento = null;
		if(valorTemp != null && !valorTemp.equals("")){
			valorPagamento = Util.formatarMoedaRealparaBigDecimal(valorTemp);
		}else{
			throw new ActionServletException("atencao.naoinformado", null, "Valor do Pagamento");
		}

		// Recupera o c�digo do im�vel
		String idImovel = (String) pagamentoActionForm.get("idImovel");

		// Cria a vari�vel que vai armazenar o im�vel informado
		Imovel imovel = null;

		// Caso o usu�rio tenha informado o im�vel
		if(idImovel != null && !idImovel.trim().equalsIgnoreCase("")){
			// [FS0008] - Verificar exist�ncia da matr�cula do im�vel
			imovel = this.verificarExistenciaMatriculaImovel(idImovel);
		}

		// Recupera o tipo de documento
		String idTipoDocumento = (String) pagamentoActionForm.get("idTipoDocumento");

		// Recupera o c�digo da localidade
		String idLocalidade = (String) pagamentoActionForm.get("idLocalidade");

		// Cria a vari�vel que vai armazenar a localidade informada
		Localidade localidade = null;

		// Caso o tipo de documento n�o seja conta
		if(!idTipoDocumento.equals(DocumentoTipo.CONTA.toString())){
			if(idLocalidade != null && !idLocalidade.equalsIgnoreCase("")){
				// [FS0007] - Verificar exist�ncia da localidade
				localidade = this.verificarExistenciaLocalidade(Integer.valueOf(idLocalidade));
			}
		}

		// [FS0009] - Verificar localidade da matr�cula do im�vel
		if(!this.verificarLocalidadeMatriculaImovel(idLocalidade, imovel)){
			throw new ActionServletException("atencao.localidade_imovel_diferente", imovel.getLocalidade().getId().toString(), idLocalidade);
		}

		// Recupera o tipo de d�bito
		String idTipoDebito = (String) pagamentoActionForm.get("idTipoDebito");

		// Cria a vari�vel que vai armazenar o tipo de d�bito
		DebitoTipo debitoTipo = null;

		// Formata a refer�ncia do pagamento para o formato AAAAMM
		Integer anoMesReferenciaPagamento = Integer.valueOf(Util.formatarMesAnoParaAnoMesSemBarra(dataPagamentoString.substring(3, 10)));

		// Recupera os par�metros do sistema
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		// cria a vari�vel que vai armazenar a refer�ncia da arrecada��o
		int anoMesReferenciaArrecadacao;

		// Caso a refer�ncia do pagamento seja maior que a refer�ncia da arrecada��o
		// dos par�metros do sistema, atribui a refer�ncia do pagamento a refer�ncia da arrecada��o
		// Caso contr�rio atribui a refer�ncia da arrecada��o do par�metro do sistema a refer�ncia
		// de arrecada��o
		if(anoMesReferenciaPagamento > sistemaParametro.getAnoMesArrecadacao()){
			anoMesReferenciaArrecadacao = anoMesReferenciaPagamento;
		}else{
			anoMesReferenciaArrecadacao = sistemaParametro.getAnoMesArrecadacao();
		}

		// Caso nenhum tipo de documento informado, levanta uma exce��o para o usu�rio
		// indicando que o tipo de documento n�o foi informado
		if(idTipoDocumento == null || idTipoDocumento.equalsIgnoreCase("")){
			throw new ActionServletException("atencao.naoinformado", null, "Tipo de Documento");
		}else{

			// Cria o objeto que vai armazenar o tipo de documento
			DocumentoTipo documentoTipo = new DocumentoTipo();

			// Caso o tipo de documento seja conta
			if(idTipoDocumento.equals(DocumentoTipo.CONTA.toString())){

				// Seta o tipo de documento para conta
				documentoTipo.setId(Integer.valueOf(DocumentoTipo.CONTA));
				documentoTipo.setDescricaoAbreviado(ConstantesSistema.TIPO_PAGAMENTO_CONTA);

				// Recupera a refer�ncia da conta
				String referenciaConta = (String) pagamentoActionForm.get("referenciaConta");

				// [FS0012] - Verificar exist�ncia da conta
				Conta conta = this.verificarExistenciaConta(referenciaConta, idImovel);

				// Formata a refer�ncia da conta para AAAAMM
				Integer anoMesReferencia = Integer.valueOf(Util.formatarMesAnoParaAnoMesSemBarra(referenciaConta));

				// Cria o pagamento para conta
				pagamento.setAnoMesReferenciaPagamento(anoMesReferencia);
				pagamento.setAnoMesReferenciaArrecadacao(anoMesReferenciaArrecadacao);
				pagamento.setValorPagamento(valorPagamento);
				pagamento.setDataPagamento(dataPagamento);
				pagamento.setPagamentoSituacaoAtual(null);
				pagamento.setPagamentoSituacaoAnterior(null);
				pagamento.setDebitoTipo(null);
				pagamento.setConta(conta);
				pagamento.setGuiaPagamentoGeral(new GuiaPagamentoGeral());
				pagamento.getGuiaPagamentoGeral().setGuiaPagamento(null);
				pagamento.setNumeroPrestacao(null);
				pagamento.setDebitoACobrar(null);
				pagamento.setLocalidade(imovel.getLocalidade());
				pagamento.setDocumentoTipo(documentoTipo);
				pagamento.setAvisoBancario(avisoBancario);
				pagamento.setImovel(imovel);
				pagamento.setArrecadadorMovimentoItem(null);
				pagamento.setArrecadacaoForma(arrecadacaoForma);
				pagamento.setUltimaAlteracao(new Date());
				pagamento.setCliente(null);

				// Caso a conta com a refer�ncia informada n�o esteja cadastrada no sistema
				if(conta == null){

					httpServletRequest.setAttribute("msg", "N�o h� Conta com a refer�ncia " + referenciaConta + " para o Im�vel "
									+ idImovel);

					httpServletRequest.setAttribute("pagamento", idPagamentoMemoria.toString());

				}

				inserirPagamentoManualHelper.setIdPagamentoMemoria(idPagamentoMemoria);
				inserirPagamentoManualHelper.setPagamento(pagamento);

				// Caso o tipo de documento seja guia de pagamento
			}else if(idTipoDocumento.equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){

				// Seta o tipo de documento para guia de pagamento
				documentoTipo.setId(Integer.valueOf(DocumentoTipo.GUIA_PAGAMENTO));
				documentoTipo.setDescricaoAbreviado(ConstantesSistema.TIPO_PAGAMENTO_GUIA_PAGAMENTO);

				// Recupera o c�digo do cliente
				String idCliente = (String) pagamentoActionForm.get("idCliente");

				// [FS0010] - Verificar preenchimento do im�vel e do cliente
				this.verificarPreeenchimentoImovelECliente(idImovel, idCliente);

				// Caso o usu�rio tenha informado o cliente
				// Recupera o cliente informado do sistema
				Cliente cliente = null;
				if(idCliente != null && !idCliente.trim().equalsIgnoreCase("")){
					// [FS0011] - Verificar exist�ncia do c�digo do cliente
					cliente = this.verificarExistenciaCodigoCliente(Integer.valueOf(idCliente));
				}

				// Recupera o n�mero da presta��o
				String numeroPrestacaoString = (String) pagamentoActionForm.get("numeroPrestacao");
				Integer numeroPrestacao = null;
				if(numeroPrestacaoString != null && !numeroPrestacaoString.equals("")){
					numeroPrestacao = Integer.valueOf(numeroPrestacaoString);
				}

				// Recupera o c�digo da guia de pagamento
				String idGuiaPagamento = (String) pagamentoActionForm.get("idGuiaPagamento");

				// [FS0021] - Verificar preenchimento da guia de pagamento e do tipo de d�bito
				this.verificarPreeenchimentoGuiaPagamentoETipoDebito(idGuiaPagamento, idTipoDebito);

				// Caso o usu�rio informou a guia de pagamento
				// Recupera a guia de pagamento informada do sistema
				GuiaPagamento guiaPagamento = null;
				if(idGuiaPagamento != null && !idGuiaPagamento.trim().equals("")){
					// [FS0022] - Verificar exist�ncia da guia de pagamento
					guiaPagamento = this.verificarExistenciaGuiaPagamento(idImovel, idCliente, idGuiaPagamento);

					// Caso existaa guia de pagamento, seta o tipo de d�bito da guia de pagamento
					// para o tipo de d�bito do pagamento que vai ser inserido
					// debitoTipo = guiaPagamento.getDebitoTipo();

					// [FS0014] - Verificar localidade da guia de pagamento
					this.verificarLocalidadeGuiaPagamento(guiaPagamento, idLocalidade);
				}

				// Caso o tipo de d�bito tenha sido informado
				if(idTipoDebito != null && !idTipoDebito.trim().equals("")){
					// [FS0020] - Verificar exist�ncia do tipo de d�bito
					debitoTipo = this.verificarExistenciaTipoDebito(Integer.valueOf(idTipoDebito));

					// [FS0013] - Verificar exist�ncia de guia de pagamento com o tipo de d�bito
					// informado
					if(idImovel != null && !idImovel.trim().equalsIgnoreCase("")){
						guiaPagamento = this.verificarExistenciaGuiaPagamentoComTipoDebito(debitoTipo, idImovel, null);
					}

					if(idCliente != null && !idCliente.trim().equalsIgnoreCase("")){
						guiaPagamento = this.verificarExistenciaGuiaPagamentoComTipoDebito(debitoTipo, null, idCliente);
					}

					// Caso n�o exista nenhuma guia de pagamento com o tipo de d�bito informado
					if(guiaPagamento == null){

						if(idImovel != null && !idImovel.trim().equalsIgnoreCase("")){

							httpServletRequest.setAttribute("msg", "N�o h� Guia de Pagamento com o tipo de d�bito "
											+ debitoTipo.getDescricao() + " para o Im�vel " + idImovel);
						}else{
							httpServletRequest.setAttribute("msg", "N�o h� Guia de Pagamento com o tipo de d�bito "
											+ debitoTipo.getDescricao() + " para o Cliente " + idCliente);
						}
					}
				}

				// Cria o pagamento para a guia de pagamento
				pagamento.setAnoMesReferenciaPagamento(null);
				pagamento.setAnoMesReferenciaArrecadacao(anoMesReferenciaArrecadacao);
				pagamento.setValorPagamento(valorPagamento);
				pagamento.setDataPagamento(dataPagamento);
				pagamento.setPagamentoSituacaoAtual(null);
				pagamento.setPagamentoSituacaoAnterior(null);
				pagamento.setDebitoTipo(debitoTipo);
				pagamento.setConta(null);

				GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();
				guiaPagamentoGeral.setId(guiaPagamento.getId());
				guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);

				pagamento.setGuiaPagamentoGeral(guiaPagamentoGeral);
				pagamento.setNumeroPrestacao(numeroPrestacao);
				pagamento.setDebitoACobrar(null);
				pagamento.setLocalidade(localidade);
				pagamento.setDocumentoTipo(documentoTipo);
				pagamento.setAvisoBancario(avisoBancario);
				pagamento.setImovel(imovel);
				pagamento.setArrecadadorMovimentoItem(null);
				pagamento.setArrecadacaoForma(arrecadacaoForma);
				pagamento.setUltimaAlteracao(new Date());
				pagamento.setCliente(cliente);

				httpServletRequest.setAttribute("pagamento", idPagamentoMemoria.toString());
				inserirPagamentoManualHelper.setIdPagamentoMemoria(idPagamentoMemoria);
				inserirPagamentoManualHelper.setPagamento(pagamento);

				// Caso o tipo de documento seja d�bito a cobrar
			}else if(idTipoDocumento.equals(DocumentoTipo.DEBITO_A_COBRAR.toString())){

				// Seta o tipo de documento do pagamento para d�bito a cobrar
				documentoTipo.setId(Integer.valueOf(DocumentoTipo.DEBITO_A_COBRAR));
				documentoTipo.setDescricaoAbreviado(ConstantesSistema.TIPO_PAGAMENTO_DEBITO_A_COBRAR);

				// Recupera o c�digo do d�bito a cobrar
				String idDebitoACobrar = (String) pagamentoActionForm.get("idDebitoACobrar");

				// [FS0024] - Verificar exist�ncia do d�bito a cobrar
				this.verificarPreeenchimentoDebitoACobrarETipoDebito(idDebitoACobrar, idTipoDebito);

				// Caso o usu�rio informou o d�bito a cobrar
				// Recupera o d�bito a cobrar informado do sistema
				DebitoACobrar debitoACobrar = null;
				if(idDebitoACobrar != null && !idDebitoACobrar.trim().equals("")){
					// [FS0023] - Verificar preenchimento do d�bito a cobrar e do tipo de d�bito
					debitoACobrar = this.verificarExistenciaDebitoACobrar(idImovel, idDebitoACobrar);

					// Caso exista o d�bito a cobrar, seta o tipo de d�bito do d�bito a cobrar
					// para o tipo de d�bito do pagamento que vai ser inserido
					debitoTipo = debitoACobrar.getDebitoTipo();

					// [FS0017] - Verificar localidade do d�bito a cobrar
					this.verificarLocalidadeDebitoACobrar(debitoACobrar, idLocalidade);
				}

				// Caso o tipo de d�bito tenha sido informado
				if(idTipoDebito != null && !idTipoDebito.trim().equals("")){
					// [FS0020] - Verificar exist�ncia do tipo de d�bito
					debitoTipo = this.verificarExistenciaTipoDebito(Integer.valueOf(idTipoDebito));

					// [FS0016] - Verificar exist�ncia de d�bito a cobrar com o tipo de d�bito
					debitoACobrar = this.verificarExistenciaDebitoACobrarComTipoDebito(debitoTipo, idImovel);

					// Caso n�o exista nenhum d�bito a cobrar com o tipo de d�bito informado
					if(debitoACobrar == null){

						httpServletRequest.setAttribute("msg", "N�o h� D�bito a Cobrar com o tipo de d�bito " + debitoTipo.getDescricao()
										+ " para o Im�vel " + idImovel);
					}
				}

				// Cria o pagamento para o d�bito a cobrar
				String numeroPrestacaoString = (String) pagamentoActionForm.get("numeroPrestacao");
				Integer numeroPrestacao = null;
				if(numeroPrestacaoString != null && !numeroPrestacaoString.equalsIgnoreCase("")){
					numeroPrestacao = Integer.valueOf(numeroPrestacaoString);
				}
				pagamento.setAnoMesReferenciaPagamento(null);
				pagamento.setAnoMesReferenciaArrecadacao(anoMesReferenciaArrecadacao);
				pagamento.setValorPagamento(valorPagamento);
				pagamento.setDataPagamento(dataPagamento);
				pagamento.setPagamentoSituacaoAtual(null);
				pagamento.setPagamentoSituacaoAnterior(null);
				pagamento.setDebitoTipo(debitoTipo);
				pagamento.setConta(null);
				pagamento.setGuiaPagamentoGeral(new GuiaPagamentoGeral());
				pagamento.getGuiaPagamentoGeral().setGuiaPagamento(null);
				pagamento.setNumeroPrestacao(numeroPrestacao);
				pagamento.setDebitoACobrar(debitoACobrar);
				pagamento.setLocalidade(localidade);
				pagamento.setDocumentoTipo(documentoTipo);
				pagamento.setAvisoBancario(avisoBancario);
				pagamento.setImovel(imovel);
				pagamento.setArrecadadorMovimentoItem(null);
				pagamento.setArrecadacaoForma(arrecadacaoForma);
				pagamento.setUltimaAlteracao(new Date());
				pagamento.setCliente(null);

				httpServletRequest.setAttribute("pagamento", idPagamentoMemoria.toString());
				inserirPagamentoManualHelper.setIdPagamentoMemoria(idPagamentoMemoria);
				inserirPagamentoManualHelper.setPagamento(pagamento);
			}
		}

		return inserirPagamentoManualHelper;
	}

	/**
	 * @author Raphael Rossiter
	 * @date 24/09/2007
	 */
	private void removerPagamento(Collection<InserirPagamentoManualHelper> colecaoInserirPagamentoManualHelper,
					String identificadorPagamento, HttpSession sessao){

		if(colecaoInserirPagamentoManualHelper != null && !colecaoInserirPagamentoManualHelper.isEmpty()){

			Integer idPagamento = new Integer(identificadorPagamento);

			Iterator itPagamento = colecaoInserirPagamentoManualHelper.iterator();
			InserirPagamentoManualHelper pagamento = null;

			while(itPagamento.hasNext()){

				pagamento = (InserirPagamentoManualHelper) itPagamento.next();

				if(pagamento.getIdPagamentoMemoria().intValue() == idPagamento.intValue()){
					itPagamento.remove();
					break;
				}
			}

			if(colecaoInserirPagamentoManualHelper.isEmpty()){
				sessao.removeAttribute("colecaoInserirPagamentoManualHelper");
			}
		}
	}

	/**
	 * @author Raphael Rossiter
	 * @date 24/09/2007
	 */
	private void limparDadosPagamento(DynaValidatorActionForm pagamentoActionForm){

		// pagamentoActionForm.set("idTipoDocumento", "");
		// pagamentoActionForm.set("idLocalidade", "");
		// pagamentoActionForm.set("descricaoLocalidade", "");
		// pagamentoActionForm.set("idImovel", "");
		// pagamentoActionForm.set("descricaoImovel", "");
		pagamentoActionForm.set("idCliente", "");
		pagamentoActionForm.set("nomeCliente", "");
		pagamentoActionForm.set("referenciaConta", "");
		pagamentoActionForm.set("descricaoReferenciaConta", "");
		pagamentoActionForm.set("idGuiaPagamento", "");
		pagamentoActionForm.set("descricaoGuiaPagamento", "");
		pagamentoActionForm.set("valorGuiaPagamento", "");
		pagamentoActionForm.set("numeroPrestacao", "");
		pagamentoActionForm.set("idDebitoACobrar", "");
		pagamentoActionForm.set("descricaoDebitoACobrar", "");
		pagamentoActionForm.set("valorDebitoACobrar", "");
		pagamentoActionForm.set("idTipoDebito", "");
		pagamentoActionForm.set("descricaoTipoDebito", "");
		pagamentoActionForm.set("valorPagamento", "");

	}

	/**
	 * @author Raphael Rossiter
	 * @date 24/09/2007
	 */
	private void verificarPagamentoJaInformado(Collection colecaoInserirPagamentoManualHelper, Pagamento pagamentoInformado){

		boolean informado = true;

		if(colecaoInserirPagamentoManualHelper != null && !colecaoInserirPagamentoManualHelper.isEmpty()){

			Iterator itPagamento = colecaoInserirPagamentoManualHelper.iterator();
			Pagamento pagamentoColecao = null;
			InserirPagamentoManualHelper inserirPagamentoManualHelper = null;

			while(itPagamento.hasNext()){

				informado = true;
				inserirPagamentoManualHelper = (InserirPagamentoManualHelper) itPagamento.next();
				pagamentoColecao = inserirPagamentoManualHelper.getPagamento();

				// TIPO DO DOCUMENTO
				if(!pagamentoColecao.getDocumentoTipo().getId().equals(pagamentoInformado.getDocumentoTipo().getId())){
					informado = false;
				}

				// IMOVEL
				if((pagamentoColecao.getImovel() != null && pagamentoInformado.getImovel() == null)
								|| (pagamentoColecao.getImovel() == null && pagamentoInformado.getImovel() != null)){
					informado = false;
				}else if(pagamentoColecao.getImovel() != null && pagamentoInformado.getImovel() != null
								&& !pagamentoColecao.getImovel().getId().equals(pagamentoInformado.getImovel().getId())){
					informado = false;
				}

				/*
				 * Chegagem realizada para os documentos do tipo GUIA DE PAGAMENTO.
				 */
				if(pagamentoInformado.getDocumentoTipo().getId().equals(DocumentoTipo.GUIA_PAGAMENTO)){

					// Verifica se o pagamento realizado � referente a presta��o informada
					if(pagamentoColecao.getNumeroPrestacao() != null && pagamentoInformado.getNumeroPrestacao() != null
									&& !pagamentoColecao.getNumeroPrestacao().equals(pagamentoInformado.getNumeroPrestacao())){
						informado = false;
					}

					// CLIENTE
					if((pagamentoColecao.getCliente() != null && pagamentoInformado.getCliente() == null)
									|| (pagamentoColecao.getCliente() == null && pagamentoInformado.getCliente() != null)){
						informado = false;
					}else if(pagamentoColecao.getCliente() != null && pagamentoInformado.getCliente() != null
									&& !pagamentoColecao.getCliente().getId().equals(pagamentoInformado.getCliente().getId())){
						informado = false;
					}
				}

				// CONTA
				if((pagamentoColecao.getConta() != null && pagamentoInformado.getConta() == null)
								|| (pagamentoColecao.getConta() == null && pagamentoInformado.getConta() != null)){
					informado = false;
				}else if(pagamentoColecao.getConta() != null && pagamentoInformado.getConta() != null
								&& pagamentoColecao.getConta().getReferencia() != pagamentoInformado.getConta().getReferencia()){
					informado = false;
				}

				// TIPO DO DEBITO
				if((pagamentoColecao.getDebitoTipo() != null && pagamentoInformado.getDebitoTipo() == null)
								|| (pagamentoColecao.getDebitoTipo() == null && pagamentoInformado.getDebitoTipo() != null)){
					informado = false;
				}else if(pagamentoColecao.getDebitoTipo() != null && pagamentoInformado.getDebitoTipo() != null
								&& !pagamentoColecao.getDebitoTipo().getId().equals(pagamentoInformado.getDebitoTipo().getId())){
					informado = false;
				}

				if(informado){
					throw new ActionServletException("atencao.pagamento_ja_informado");
				}
			}
		}
	}

}
