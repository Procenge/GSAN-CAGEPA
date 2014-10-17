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

import gcom.arrecadacao.ArrecadacaoForma;
import gcom.arrecadacao.FiltroAvisoBancario;
import gcom.arrecadacao.aviso.AvisoBancario;
import gcom.arrecadacao.pagamento.*;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cobranca.DocumentoTipo;
import gcom.fachada.Fachada;
import gcom.faturamento.GuiaPagamentoGeral;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaHistorico;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoACobrarHistorico;
import gcom.faturamento.debito.DebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que finaliza a página de atualizar pagamento
 * 
 * @author Pedro Alexandre
 * @created 22/03/2006
 */
public class AtualizarPagamentosAction
				extends GcomAction {

	/**
	 * @author Pedro Alexandre
	 * @date 22/03/2006
	 * @author Saulo Lima, Eduardo Henrique
	 * @date 10/07/2009
	 *       Adicionar o campo numeroPrestacao
	 *       Correcao da atribuição do Nr. da Guia de Pagamento ao Pagamento
	 *       //TODO mover regras para controlador.
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Cria a variável de retorno e seta o mapeamento para a tela de sucesso
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Cria uma instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Cria uma instância da sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		// Recupera o form
		ManterPagamentoActionForm manterPagamentoActionForm = (ManterPagamentoActionForm) actionForm;

		// Recupera o pagamento que vai ser atualizado da sessão
		Pagamento pagamentoAtualizacao = (Pagamento) sessao.getAttribute("pagamento");

		// Recupera a variável para indicar se o usuário apertou o botão de confirmar da tela de
		// confirmação
		String confirmado = httpServletRequest.getParameter("confirmado");

		// Variável que vai armazenar a mensagem de sucesso dependendo se foi um pagamento para
		// cliente ou imóvel
		// String mensagemSucesso = "";

		// [FS0019] - Validar data do pagamento
		// Recupera a data de pagamento e verifica se a data é uma data válida
		String dataPagamentoString = manterPagamentoActionForm.getDataPagamento();
		Date dataPagamento = null;
		SimpleDateFormat dataFormato = new SimpleDateFormat("dd/MM/yyyy");
		try{
			dataPagamento = dataFormato.parse(dataPagamentoString);
		}catch(ParseException ex){
			throw new ActionServletException("atencao.data_pagamento_invalida");
		}

		// Recupera o código da forma de arrecadação e pesquisa o objeto no sistema
		String idFormaArrecadacao = manterPagamentoActionForm.getIdFormaArrecadacao();
		ArrecadacaoForma arrecadacaoForma = new ArrecadacaoForma();
		arrecadacaoForma.setId(Integer.valueOf(idFormaArrecadacao));

		// Recupera o código do aviso bancário e pesquisa o objeto no sistema
		String idAvisoBancario = manterPagamentoActionForm.getIdAvisoBancario();
		FiltroAvisoBancario filtroAvisoBancario = new FiltroAvisoBancario();
		filtroAvisoBancario.adicionarParametro(new ParametroSimples(FiltroAvisoBancario.ID, idAvisoBancario));
		filtroAvisoBancario.adicionarCaminhoParaCarregamentoEntidade(FiltroAvisoBancario.ARRECADADOR);
		AvisoBancario avisoBancario = (AvisoBancario) (fachada.pesquisar(filtroAvisoBancario, AvisoBancario.class.getName())).iterator()
						.next();

		// Recupera o código da situação atual do pagamento
		String idSituacaoAtualPagamento = manterPagamentoActionForm.getIdSituacaoAtualPagamento();

		// cria as variáveis que vão armazenar as situações atual e anterior do pagamento
		PagamentoSituacao situacaoPagamentoAtual = null;
		PagamentoSituacao situacaoPagamentoAnterior = null;

		// Caso a nova situação atual do pagamento tenha sido informada na página
		if(idSituacaoAtualPagamento != null && !idSituacaoAtualPagamento.trim().equalsIgnoreCase("")){

			// Cria o filtro da situação de pagamento e pesquisa a situação informada no sistema
			FiltroPagamentoSituacao filtroPagamentoSituacao = new FiltroPagamentoSituacao();

			/*
			 * Colocado por Raphael Rossiter em 19/10/2007
			 * OBJ:Selecionar apenas a situacao escolhida pelo usuario
			 */
			filtroPagamentoSituacao.adicionarParametro(new ParametroSimples(FiltroPagamentoSituacao.CODIGO, Integer
							.valueOf(idSituacaoAtualPagamento)));

			// Situação atual
			situacaoPagamentoAtual = (PagamentoSituacao) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroPagamentoSituacao,
							PagamentoSituacao.class.getName()));

			// Seta a situação atual do pagamento para a situação anterior
			situacaoPagamentoAnterior = pagamentoAtualizacao.getPagamentoSituacaoAtual();

		}else{
			// Caso a nova situação do pagamento não tenha sido informada
			// o pagamento continua com as mesmas situações inalteradas
			if(pagamentoAtualizacao.getPagamentoSituacaoAtual() != null){
				situacaoPagamentoAtual = pagamentoAtualizacao.getPagamentoSituacaoAtual();
			}

			if(pagamentoAtualizacao.getPagamentoSituacaoAnterior() != null){
				situacaoPagamentoAnterior = pagamentoAtualizacao.getPagamentoSituacaoAnterior();
			}
		}

		// Recupera o valor do pagamento
		BigDecimal valorPagamentoNovo = null;
		if(!Util.isVazioOuBranco(manterPagamentoActionForm.getValorPagamento())
						&& Util.formatarMoedaRealparaBigDecimal(manterPagamentoActionForm.getValorPagamento()).compareTo(BigDecimal.ZERO) > 0){
			valorPagamentoNovo = Util.formatarMoedaRealparaBigDecimal(manterPagamentoActionForm.getValorPagamento());
		}else{
			throw new ActionServletException("atencao.valor_pagamento_invalido");
		}

		// Recupera o código do imóvel
		String idImovel = manterPagamentoActionForm.getIdImovel();

		// Cria a variável que vai armazenar o imóvel informado
		Imovel imovel = null;

		// Caso o usuário tenha informado o imóvel
		if(idImovel != null && !idImovel.trim().equalsIgnoreCase("")){

			// [FS0004] - Verificar existência da matrícula do imóvel
			imovel = fachada.pesquisarImovelDigitado(Integer.valueOf(idImovel));

			if(imovel == null){
				throw new ActionServletException("atencao.naocadastrado", null, "Matrícula do imóvel");
			}
		}

		// Recupera o tipo de documento
		String idTipoDocumento = manterPagamentoActionForm.getIdTipoDocumento();

		// Recupera o código da localidade
		String idLocalidade = manterPagamentoActionForm.getIdLocalidade();

		// Cria a variável que vai armazenar a localidade informada
		Localidade localidade = null;

		// Caso o tipo de documento não seja conta
		if(!idTipoDocumento.equals(DocumentoTipo.CONTA.toString())){
			if(idLocalidade != null && !idLocalidade.equalsIgnoreCase("")){

				// [FS0003] - Verificar existência da localidade
				localidade = fachada.pesquisarLocalidadeDigitada(Integer.valueOf(idLocalidade));
			}
		}

		// Caso o imóvel tenha sido informado verificar se a localidade informada é igual a do
		// imóvel
		if(idImovel != null && !idImovel.equalsIgnoreCase("")){
			if(idLocalidade == null || idLocalidade.equalsIgnoreCase("")){
				idLocalidade = "" + imovel.getLocalidade().getId();
			}else{

				// [FS0005] - Verificar localidade da matrícula do imóvel
				if(!fachada.verificarLocalidadeMatriculaImovel(idLocalidade, imovel)){
					throw new ActionServletException("atencao.localidade_imovel_diferente", imovel.getLocalidade().getId().toString(),
									idLocalidade);
				}
			}
		}

		// Recupera o tipo de débito
		String idTipoDebito = manterPagamentoActionForm.getIdTipoDebito();

		// Cria a variável que vai armazenar o tipo de débito
		DebitoTipo debitoTipo = null;

		// Caso nenhum tipo de documento informado, levanta uma exceção para o usuário
		// indicando que o tipo de documento não foi informado
		if(idTipoDocumento == null || idTipoDocumento.equalsIgnoreCase("")){
			throw new ActionServletException("atencao.naoinformado", null, "Tipo de Documento");
		}else{

			// Cria o objeto que vai armazenar o tipo de documento
			DocumentoTipo documentoTipo = new DocumentoTipo();

			// Caso o tipo de documento seja conta
			if(idTipoDocumento.equals(DocumentoTipo.CONTA.toString())){

				// Seta o tipo de documento para conta
				documentoTipo.setId(Integer.valueOf(DocumentoTipo.CONTA));

				// Recupera a referência da conta
				String referenciaConta = manterPagamentoActionForm.getReferenciaConta();

				// [FS0008] - Verificar existência da conta
				Conta conta = null;

				if(Util.validarMesAno(referenciaConta)){
					conta = fachada.pesquisarContaDigitada(idImovel, referenciaConta);

					if(conta == null){
						ContaHistorico contaHistorico = fachada.pesquisarContaHistoricoDigitada(idImovel, referenciaConta);

						if(contaHistorico != null){
							// Essa verificação é necessária porque o objeto Pagamento está
							// associado a Conta e não a ContaGeral, mas a tabela PAGAMENTO está
							// associada a CONTA_GERAL. Nesse caso quando um pagameno que tinha uma
							// conta em histórico e era realizado a baixa forçada, o sistema estava
							// transferindo o pagamento sem o Id da Conta.

							conta = new Conta();
							conta.setId(contaHistorico.getId());
							conta.setReferencia(contaHistorico.getAnoMesReferenciaConta());
						}
					}
				}

				// Caso a conta com a referência informada não esteja cadastrada no sistema
				if(conta == null){

					// Caso o usuário não tenha passado pela página de confirmação
					if(confirmado == null || !confirmado.trim().equalsIgnoreCase("ok")){

						httpServletRequest.setAttribute("caminhoActionConclusao", "/gsan/atualizarPagamentosAction.do");

						// Monta a página de confirmação para perguntar se o usuário quer atualizar
						// o pagamento mesmo sem a conta existir para a referência e o imóvel
						// informados
						return montarPaginaConfirmacao("atencao.referencia.naocadastrada", httpServletRequest, actionMapping,
										referenciaConta);
					}
				}

				// Monta a menssagem de sucesso
				// mensagemSucesso = "Pagamento para imóvel " + idImovel +
				// " atualizado com sucesso.";

				// [SB0004] Atualiza Pagamento
				// Atualiza o pagamento para conta
				// Formata a referência da conta para AAAAMM
				Integer anoMesReferencia = null;

				if(Util.validarMesAno(referenciaConta)){
					anoMesReferencia = Integer.valueOf(Util.formatarMesAnoParaAnoMesSemBarra(referenciaConta));

					pagamentoAtualizacao.setAnoMesReferenciaPagamento(anoMesReferencia);
				}

				pagamentoAtualizacao.setValorPagamento(valorPagamentoNovo);
				pagamentoAtualizacao.setDataPagamento(dataPagamento);
				pagamentoAtualizacao.setPagamentoSituacaoAtual(situacaoPagamentoAtual);
				pagamentoAtualizacao.setPagamentoSituacaoAnterior(situacaoPagamentoAnterior);
				pagamentoAtualizacao.setDebitoTipo(null);
				pagamentoAtualizacao.setConta(conta);
				pagamentoAtualizacao.setGuiaPagamentoGeral(null);
				pagamentoAtualizacao.setNumeroPrestacao(null);
				pagamentoAtualizacao.setDebitoACobrar(null);
				pagamentoAtualizacao.setLocalidade(imovel.getLocalidade());
				pagamentoAtualizacao.setDocumentoTipo(documentoTipo);
				pagamentoAtualizacao.setImovel(imovel);
				pagamentoAtualizacao.setCliente(null);

				// Caso o tipo de documento seja guia de pagamento
			}else if(idTipoDocumento.equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){

				// Seta o tipo de documento para guia de pagamento
				documentoTipo.setId(Integer.valueOf(DocumentoTipo.GUIA_PAGAMENTO));

				// Recupera o código do cliente
				String idCliente = manterPagamentoActionForm.getIdCliente();

				// [FS0006] - Verificar preenchimento do imóvel e do cliente
				fachada.verificarPreeenchimentoImovelECliente(idImovel, idCliente);

				// Caso o usuário tenha informado o cliente Recupera o cliente informado do sistema
				Cliente cliente = null;

				if(idCliente != null && !idCliente.trim().equalsIgnoreCase("")){
					// [FS0007] - Verificar existência do código do cliente
					cliente = fachada.pesquisarClienteDigitado(Integer.valueOf(idCliente));
				}

				// Recupera o código da guia de pagamento
				String idGuiaPagamento = manterPagamentoActionForm.getIdGuiaPagamento();

				// Recupera o número da prestação
				String numeroPrestacao = manterPagamentoActionForm.getNumeroPrestacao();

				// [FS0021] - Verificar preenchimento da guia de pagamento e do tipo de débito
				fachada.verificarPreeenchimentoGuiaPagamentoETipoDebito(idGuiaPagamento, idTipoDebito);

				// Caso o usuário informou a guia de pagamento Recupera a guia de pagamento
				// informada do sistema
				GuiaPagamento guiaPagamento = null;
				GuiaPagamentoHistorico guiaPagamentoHistorico = null;

				if(idGuiaPagamento != null && !idGuiaPagamento.trim().equals("")){

					// [FS0022] - Verificar existência da guia de pagamento
					guiaPagamento = fachada.pesquisarGuiaPagamentoDigitada(idImovel, idCliente, idGuiaPagamento);

					if(guiaPagamento == null){
						throw new ActionServletException("atencao.pesquisa_inexistente", null, "Guia de Pagamento/Número Prestação");
					}else{

						// [FS0010] - Verificar localidade da guia de pagamento
						fachada.verificarLocalidadeGuiaPagamento(guiaPagamento, idLocalidade);

						FiltroGuiaPagamentoPrestacao filtroGuiaPagamentoPrestacao = new FiltroGuiaPagamentoPrestacao();
						filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(
										FiltroGuiaPagamentoPrestacao.GUIA_PAGAMENTO_ID, guiaPagamento.getId()));
						filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacao.NUMERO_PRESTACAO,
										Integer.valueOf(numeroPrestacao)));

						Collection<GuiaPagamentoPrestacao> colecaoGuiaPagamentoPrestacao = fachada.pesquisar(filtroGuiaPagamentoPrestacao,
										GuiaPagamentoPrestacao.class.getName());

						if(colecaoGuiaPagamentoPrestacao == null || colecaoGuiaPagamentoPrestacao.isEmpty()){
							// Verifica no histórico
							FiltroGuiaPagamentoPrestacaoHistorico filtroGuiaPagamentoPrestacaoHistorico = new FiltroGuiaPagamentoPrestacaoHistorico();
							filtroGuiaPagamentoPrestacaoHistorico.adicionarParametro(new ParametroSimples(
											FiltroGuiaPagamentoPrestacaoHistorico.GUIA_PAGAMENTO_ID, guiaPagamento.getId()));
							filtroGuiaPagamentoPrestacaoHistorico.adicionarParametro(new ParametroSimples(
											FiltroGuiaPagamentoPrestacaoHistorico.NUMERO_PRESTACAO, Integer.valueOf(numeroPrestacao)));

							Collection<GuiaPagamentoPrestacaoHistorico> colecaoGuiaPagamentoPrestacaoHistorico = fachada.pesquisar(
											filtroGuiaPagamentoPrestacaoHistorico, GuiaPagamentoPrestacaoHistorico.class.getName());

							if(colecaoGuiaPagamentoPrestacaoHistorico == null || colecaoGuiaPagamentoPrestacaoHistorico.isEmpty()){
								throw new ActionServletException("atencao.pesquisa_inexistente", null, "Guia de Pagamento");
							}
						}
					}
				}

				// Caso o tipo de débito tenha sido informado
				if(idTipoDebito != null && !idTipoDebito.trim().equals("")){

					// [FS0020] - Verificar existência do tipo de débito
					debitoTipo = fachada.pesquisarTipoDebitoDigitado(Integer.valueOf(idTipoDebito));

					// [FS0009] - Verificar existência de guia de pagamento com o tipo de débito
					// informado
					if(idImovel != null && !idImovel.trim().equalsIgnoreCase("")){
						guiaPagamento = fachada.verificarExistenciaGuiaPagamentoComTipoDebito(debitoTipo, idImovel, null);
						// mensagemSucesso = "Pagamento para imóvel " + idImovel +
						// " atualizado com sucesso.";
					}

					if(idCliente != null && !idCliente.trim().equalsIgnoreCase("")){
						guiaPagamento = fachada.verificarExistenciaGuiaPagamentoComTipoDebito(debitoTipo, null, idCliente);
						// mensagemSucesso = "Pagamento para cliente " + idCliente +
						// "atualizado com sucesso.";
					}

					// Caso a guia de pagamento tenha ido para histórico
					if(Util.isVazioOuBranco(guiaPagamento)){
						guiaPagamentoHistorico = fachada.verificarExistenciaGuiaPagamentoHistoricoComTipoDebito(debitoTipo, idImovel,
										idCliente);

						if(!Util.isVazioOuBranco(guiaPagamentoHistorico)){
							guiaPagamento = new GuiaPagamento();
							guiaPagamento.setId(guiaPagamentoHistorico.getId());
						}
					}

					// Caso não exista nenhuma guia de pagamento com o tipo de débito informado
					if(guiaPagamento == null){
						// Caso o usuário não tenha passado pela página de confirmação do wizard
						if(confirmado == null || !confirmado.trim().equalsIgnoreCase("ok")){
							// Monta a página de confirmação para perguntar se o usuário quer
							// inserir
							// o pagamento mesmo sem existir guia de pagamento para o tipo de débito
							// e o imóvel ou cliente informados
							if(idImovel != null && !idImovel.trim().equalsIgnoreCase("")){
								httpServletRequest.setAttribute("caminhoActionConclusao", "/gsan/atualizarPagamentosAction.do");

								return montarPaginaConfirmacao("atencao.guia_pagamento.naocadastrada", httpServletRequest, actionMapping,
												debitoTipo.getDescricao(), "Imóvel", idImovel);
							}else{
								return montarPaginaConfirmacao("atencao.guia_pagamento.naocadastrada", httpServletRequest, actionMapping,
												debitoTipo.getDescricao(), "Cliente", idCliente);
							}
						}
					}
				}

				// [SB0004] Atualiza Pagamento
				// Atualiza o pagamento para a guia de pagamento
				pagamentoAtualizacao.setAnoMesReferenciaPagamento(0);
				pagamentoAtualizacao.setValorPagamento(valorPagamentoNovo);
				pagamentoAtualizacao.setDataPagamento(dataPagamento);
				pagamentoAtualizacao.setPagamentoSituacaoAtual(situacaoPagamentoAtual);
				pagamentoAtualizacao.setPagamentoSituacaoAnterior(situacaoPagamentoAnterior);
				pagamentoAtualizacao.setDebitoTipo(debitoTipo);
				pagamentoAtualizacao.setConta(null);

				GuiaPagamentoGeral guiaPagamentoGeral = null;

				if(!Util.isVazioOuBranco(guiaPagamento)){
					guiaPagamentoGeral = new GuiaPagamentoGeral();
					guiaPagamentoGeral.setId(guiaPagamento.getId());
					guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);
				}

				pagamentoAtualizacao.setGuiaPagamentoGeral(guiaPagamentoGeral);

				// Número da prestação informado
				if(!Util.isVazioOuBranco(numeroPrestacao)){
					pagamentoAtualizacao.setNumeroPrestacao(Integer.valueOf(numeroPrestacao));
				}else{
					// Recupera o número da prestação do pagamento.
					Integer numeroPrestacaoAtualizado = null;

					if(!Util.isVazioOuBranco(pagamentoAtualizacao.getNumeroPrestacao())){
						numeroPrestacaoAtualizado = pagamentoAtualizacao.getNumeroPrestacao();
					}

					pagamentoAtualizacao.setNumeroPrestacao(numeroPrestacaoAtualizado);
				}

				pagamentoAtualizacao.setDebitoACobrar(null);
				pagamentoAtualizacao.setLocalidade(localidade);
				pagamentoAtualizacao.setDocumentoTipo(documentoTipo);
				pagamentoAtualizacao.setImovel(imovel);
				pagamentoAtualizacao.setCliente(cliente);

				// Caso o tipo de documento seja débito a cobrar
			}else if(idTipoDocumento.equals(DocumentoTipo.DEBITO_A_COBRAR.toString())){

				// Seta o tipo de documento do pagamento para débito a cobrar
				documentoTipo.setId(Integer.valueOf(DocumentoTipo.DEBITO_A_COBRAR));

				// Recupera o código do débito a cobrar
				String idDebitoACobrar = manterPagamentoActionForm.getIdDebitoACobrar();

				// [FS0023] - Verificar preenchimento do débito a cobrar e do tipo de débito
				fachada.verificarPreeenchimentoDebitoACobrarETipoDebito(idDebitoACobrar, idTipoDebito);

				// Caso o usuário informou o débito a cobrar
				// Recupera o débito a cobrar informado do sistema
				DebitoACobrar debitoACobrar = null;
				DebitoACobrarHistorico debitoACobrarHistorico = null;
				if(idDebitoACobrar != null && !idDebitoACobrar.trim().equals("")){

					// [FS0024] - Verificar existência do débito a cobrar
					debitoACobrar = fachada.pesquisarDebitoACobrarDigitado2(idImovel, idDebitoACobrar);
					if(debitoACobrar == null || debitoACobrar.equals("")){
						debitoACobrarHistorico = fachada.pesquisarDebitoACobrarHistoricoDigitado(idImovel, idDebitoACobrar);
						if(debitoACobrarHistorico != null && !debitoACobrarHistorico.equals("")){
							// Caso exista o débito a cobrar, seta o tipo de débito do débito a
							// cobrar
							// para o tipo de débito do pagamento que vai ser inserido
							debitoTipo = debitoACobrarHistorico.getDebitoTipo();
							// [FS0013] - Verificar localidade do débito a cobrar
							fachada.verificarLocalidadeDebitoACobrarHistorico(debitoACobrarHistorico, idLocalidade);
						}
					}else{
						// Caso exista o débito a cobrar, seta o tipo de débito do débito a cobrar
						// para o tipo de débito do pagamento que vai ser inserido
						debitoTipo = debitoACobrar.getDebitoTipo();
						// [FS0013] - Verificar localidade do débito a cobrar
						fachada.verificarLocalidadeDebitoACobrar(debitoACobrar, idLocalidade);
					}
				}

				// Caso o tipo de débito tenha sido informado
				if(idTipoDebito != null && !idTipoDebito.trim().equals("")){
					// [FS0020] - Verificar existência do tipo de débito
					debitoTipo = fachada.pesquisarTipoDebitoDigitado(Integer.valueOf(idTipoDebito));

					// [FS0012] - Verificar existência de débito a cobrar com o
					// tipo de débito
					debitoACobrar = fachada.verificarExistenciaDebitoACobrarComTipoDebito(debitoTipo, idImovel);

					// Caso não exista nenhum débito a cobrar com o tipo de débito informado
					if(debitoACobrar == null){
						// Caso o usuário não tenha passado pela página de confirmação do wizard
						if(confirmado == null || !confirmado.trim().equalsIgnoreCase("ok")){
							httpServletRequest.setAttribute("caminhoActionConclusao", "/gsan/atualizarPagamentosAction.do");

							// Monta a página de confirmação do wizard para perguntar se o usuário
							// quer inserir
							// o pagamento mesmo sem existir débito a cobrar para o tipo de débito e
							// o imóvel informados
							return montarPaginaConfirmacao("atencao.debito_a_cobrar.naocadastrado", httpServletRequest, actionMapping,
											debitoTipo.getDescricao(), idImovel);
						}
					}else{
						debitoACobrarHistorico = fachada.verificarExistenciaDebitoACobrarHistoricoComTipoDebito(debitoTipo, idImovel);

						// Caso não exista nenhum débito a cobrar com o tipo de débito informado
						if(debitoACobrarHistorico == null){
							// Caso o usuário não tenha passado pela página de confirmação do wizard
							if(confirmado == null || !confirmado.trim().equalsIgnoreCase("ok")){
								httpServletRequest.setAttribute("caminhoActionConclusao", "/gsan/atualizarPagamentosAction.do");

								// Monta a página de confirmação do wizard para perguntar se o
								// usuário quer inserir
								// o pagamento mesmo sem existir débito a cobrar para o tipo de
								// débito e o imóvel informados
								return montarPaginaConfirmacao("atencao.debito_a_cobrar.naocadastrado", httpServletRequest, actionMapping,
												debitoTipo.getDescricao(), idImovel);
							}
						}
					}
				}

				// Montar página de sucesso
				// mensagemSucesso = "Pagamento para imóvel " + idImovel +
				// " atualizado com sucesso.";

				// [SB0004] Atualiza Pagamento
				// Atualiza o pagamento para o débito a cobrar
				pagamentoAtualizacao.setAnoMesReferenciaPagamento(0);
				pagamentoAtualizacao.setValorPagamento(valorPagamentoNovo);
				pagamentoAtualizacao.setDataPagamento(dataPagamento);
				// Recupera o número da prestação
				String numeroPrestacao = manterPagamentoActionForm.getNumeroPrestacao();
				if(numeroPrestacao != null && !numeroPrestacao.equalsIgnoreCase("")){
					pagamentoAtualizacao.setNumeroPrestacao(Integer.valueOf(numeroPrestacao));
				}else{
					pagamentoAtualizacao.setNumeroPrestacao(null);
				}

				/*
				 * Alterado por Raphael Rossiter em 19/10/2007
				 * OBS: Alterar as situações anterior e atual também para os documentos do tipo
				 * débito a cobrar
				 */
				pagamentoAtualizacao.setPagamentoSituacaoAtual(situacaoPagamentoAtual);
				pagamentoAtualizacao.setPagamentoSituacaoAnterior(situacaoPagamentoAnterior);
				pagamentoAtualizacao.setDebitoTipo(debitoTipo);
				pagamentoAtualizacao.setConta(null);
				pagamentoAtualizacao.setGuiaPagamentoGeral(null);
				pagamentoAtualizacao.setDebitoACobrar(debitoACobrar);
				pagamentoAtualizacao.setLocalidade(localidade);
				pagamentoAtualizacao.setDocumentoTipo(documentoTipo);
				pagamentoAtualizacao.setImovel(imovel);
				pagamentoAtualizacao.setCliente(null);
			}else{
				// [SB0004] Atualiza Pagamento
				pagamentoAtualizacao.setAnoMesReferenciaPagamento(null);
				pagamentoAtualizacao.setValorPagamento(valorPagamentoNovo);
				pagamentoAtualizacao.setDataPagamento(dataPagamento);
				pagamentoAtualizacao.setPagamentoSituacaoAnterior(situacaoPagamentoAnterior);
				pagamentoAtualizacao.setPagamentoSituacaoAtual(situacaoPagamentoAtual);
				pagamentoAtualizacao.setDebitoTipo(null);
				pagamentoAtualizacao.setConta(null);
				pagamentoAtualizacao.setGuiaPagamentoGeral(null);
				pagamentoAtualizacao.setDebitoACobrar(null);

				documentoTipo.setId(Util.converterStringParaInteger(idTipoDocumento));
				pagamentoAtualizacao.setDocumentoTipo(documentoTipo);

				pagamentoAtualizacao.setImovel(imovel);
				pagamentoAtualizacao.setCliente(null);
				pagamentoAtualizacao.setNumeroPrestacao(null);
			}

			// Valida o campo Tipo Crédito
			this.validarTipoCredito(manterPagamentoActionForm);

			Integer idCreditoTipo = null;

			if(Util.isNaoNuloBrancoZero(manterPagamentoActionForm.getIdCreditoTipo())){
				idCreditoTipo = Integer.valueOf(manterPagamentoActionForm.getIdCreditoTipo());
			}

			// [FS0027] – Verificar agente caixa de empresa
			if(avisoBancario.getArrecadador().getIndicadorCaixaEmpresa().equals(ConstantesSistema.SIM)){
				if(!fachada.isAgenteCaixaDeEmpresaAutorizado(avisoBancario.getArrecadador().getId(), usuarioLogado.getId())){
					throw new ActionServletException("atencao.usuario_nao_autorizado_caixa_empresa");
				}
			}

			// Chama o metódo de atualizar pagamento da fachada, para atualiza o pagamento
			fachada.atualizarPagamento(pagamentoAtualizacao, usuarioLogado, manterPagamentoActionForm.getGerarDevolucaoValores(),
							idCreditoTipo, manterPagamentoActionForm.getSituacaoPagamentoOriginal());

			// Alterado por Sávio Luiz data:16/03/2007

		}

		// Caso o retorno seja para a tela de sucesso, Monta a tela de sucesso
		if(retorno.getName().equalsIgnoreCase("telaSucesso")){
			montarPaginaSucesso(httpServletRequest, "Pagamento atualizado com sucesso.", "Realizar outra Manutenção de Pagamento",
							"exibirFiltrarPagamentoAction.do?tela=manterPagamento&menu=sim");
		}

		// Retorna o mapeamento contido na variável retorno
		return retorno;
	}

	private void validarTipoCredito(ManterPagamentoActionForm form){

		if(form.getGerarDevolucaoValores().equals(ConstantesSistema.SIM + "")
						&& form.getIdCreditoTipo().equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){

			throw new ActionServletException("atencao.creditoTipo_inexistente");

		}

	}
}
