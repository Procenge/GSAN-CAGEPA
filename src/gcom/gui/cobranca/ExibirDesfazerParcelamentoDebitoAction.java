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

import gcom.arrecadacao.pagamento.*;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.BoletoBancario;
import gcom.cobranca.BoletoBancarioSituacao;
import gcom.cobranca.CobrancaForma;
import gcom.cobranca.FiltroBoletoBancario;
import gcom.cobranca.parcelamento.FiltroParcelamento;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.cobranca.parcelamento.ParcelamentoSituacao;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaHistorico;
import gcom.faturamento.conta.FiltroConta;
import gcom.faturamento.conta.FiltroContaHistorico;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.FiltroDebitoACobrar;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNaoNulo;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cobranca.parcelamento.ParametroParcelamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Desfazer Parcelamento Débito
 * 
 * @author Fernanda Karla
 * @since 11/01/2006
 */
public class ExibirDesfazerParcelamentoDebitoAction
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

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();

		Usuario usuario = getUsuarioLogado(httpServletRequest);

		// Mudar isso quando implementar a parte de segurança
		// HttpSession sessao = httpServletRequest.getSession(false);

		String codigo = httpServletRequest.getParameter("codigoParcelamento").trim();

		String motivo = httpServletRequest.getParameter("motivo").trim();

		// [SB0001] – Verificar possibilidade de desfazer o parcelamento.
		if(this.permiteDesfazerParcelamento(Integer.valueOf(codigo), fachada)){
			// [SB0003 – Validar autorização de acesso ao imóvel em cobrança administrativa pelos
			// usuários da empresa contratante]
			this.validarAcessoImovelCobrancaAdministrativaUsuarioEmpresaContratante(usuario, Integer.valueOf(codigo));
			fachada.desfazerParcelamentosDebito(motivo, Integer.valueOf(codigo), usuario);
		}

		// Monta a página de sucesso
		montarPaginaSucesso(httpServletRequest, "Parcelamento de Débitos desfeito com sucesso.",
						"Realizar outra manutenção de Parcelamento de Débitos", "exibirConsultarListaParcelamentoDebitoAction.do?menu=sim");

		return retorno;
	}

	/**
	 * [UC0252] – Consultar Parcelamentos de Débitos.
	 * [SB0001] – Verificar possibilidade de desfazer o parcelamento.
	 * 
	 * @author Ailton Sousa
	 * @date 13/12/2011
	 * @param idParcelamento
	 * @param fachada
	 * @return
	 */
	public boolean permiteDesfazerParcelamento(Integer idParcelamento, Fachada fachada){

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		FiltroParcelamento filtroParcelamento = new FiltroParcelamento();
		filtroParcelamento.adicionarParametro(new ParametroSimples(FiltroParcelamento.ID, idParcelamento));
		filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("parcelamentoSituacao");
		filtroParcelamento.setInitializeLazy(true);

		Collection<Parcelamento> colecaoParcelamento = fachada.pesquisar(filtroParcelamento, Parcelamento.class.getName());
		Parcelamento parcelamento = (Parcelamento) Util.retonarObjetoDeColecao(colecaoParcelamento);

		Integer anoMesEfetivacaoParcelamento = Util.getAnoMesComoInteger(parcelamento.getParcelamento());
		Integer anoMesArrecadacaoCorrente = sistemaParametro.getAnoMesArrecadacao();

		// 1.1. Caso o mês/ano de efetivação do parcelamento de débitos seja igual ou superior ao
		// mês/ano de arrecadação corrente e o parcelamento esteja com situação normal (MÊS/ANO do
		// PARC_TMPARCELAMENTO da tabela PARCELAMENTO maior ou igual ao PARM_AMREFERENCIAARRECADACAO
		// da tabela SISTEMA_PARAMETROS e PCST_ID com o valor correspondente a normal da tabela
		// PARCELAMENTO_SITUACAO).
		if(anoMesEfetivacaoParcelamento >= anoMesArrecadacaoCorrente
						&& parcelamento.getParcelamentoSituacao().getId().equals(ParcelamentoSituacao.NORMAL)){

			FiltroDebitoACobrar filtroDebitoACobrar = new FiltroDebitoACobrar();
			filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.PARCELAMENTO_ID, idParcelamento));

			// Verifica se todos os debitos nao possuem prestacoes cobradas
			Collection<DebitoACobrar> colecaoDebitoACobrar = fachada.pesquisar(filtroDebitoACobrar, DebitoACobrar.class.getName());
			boolean isPrestacaoCobradas = false;

			// 1.1.1. Caso o parcelamento tenha sido cobrado por meio de débito a cobrar (existe
			// ocorrência na tabela DEBITO_A_COBRAR com PARC_ID=PARC_ID da tabela PARCELAMENTO)
			if(colecaoDebitoACobrar != null && !colecaoDebitoACobrar.isEmpty()){
				Iterator<DebitoACobrar> dadosdebitoACobrar = colecaoDebitoACobrar.iterator();

				while(dadosdebitoACobrar.hasNext()){
					DebitoACobrar debitoACobrar = dadosdebitoACobrar.next();
					if(debitoACobrar.getNumeroPrestacaoCobradas() != 0){
						isPrestacaoCobradas = true;
						break;
					}
				}
				// 1.1.1.1. Caso nenhuma parcela tenha sido cobrada (DBAC_NNPRESTACAOCOBRADAS=0 da
				// tabela DEBITO_A_COBRAR com PARC_ID da tabela PARCELAMENTO)
				if(!isPrestacaoCobradas){
					// 1.1.1.1.1. Caso não exista entrada (PARC_VLENTRADA = 0 ou nulo):
					// 1.1.1.1.1.1. O sistema permite desfazer o parcelamento.
					if(parcelamento.getValorEntrada() != null && !parcelamento.getValorEntrada().equals(BigDecimal.ZERO)
									&& !parcelamento.getValorEntrada().equals(new BigDecimal("0.00"))
									&& parcelamento.getValorEntrada().compareTo(BigDecimal.ZERO) > 0){

						boolean isGuiaNaoPaga = false;
						boolean isGuiaHistoricoNaoPaga = false;
						boolean isContaNaoPaga = false;
						boolean isContaHistoricoNaoPaga = false;

						FiltroGuiaPagamento filtroGuiaPagamento = new FiltroGuiaPagamento();
						filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.PARCELAMENTO_ID, parcelamento
										.getId()));
						filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.NUMERO_PRESTACAO_TOTAL, new Short(
										"1")));
						filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.VALOR_DEBITO, parcelamento
										.getValorEntrada()));

						Collection colecaoGuiaPagamento = getFachada().pesquisar(filtroGuiaPagamento, GuiaPagamento.class.getName());

						// 1.1.1.1.2.1. Caso a entrada seja uma guia e não tenha sido paga.
						if(colecaoGuiaPagamento != null && !colecaoGuiaPagamento.isEmpty()){
							GuiaPagamento guiaPagamento = (GuiaPagamento) Util.retonarObjetoDeColecao(colecaoGuiaPagamento);

							FiltroPagamento filtroPagamento = new FiltroPagamento();
							filtroPagamento.adicionarParametro(new ParametroSimples(FiltroPagamento.GUIA_PAGAMENTO_ID, guiaPagamento
											.getId()));

							Collection colecaoPagamento = getFachada().pesquisar(filtroPagamento, Pagamento.class.getName());
							if(colecaoPagamento == null || colecaoPagamento.isEmpty()){
								isGuiaNaoPaga = true;
							}
						}else{
							isGuiaNaoPaga = true;
						}

						FiltroGuiaPagamentoHistorico filtroGuiaPagamentoHistorico = new FiltroGuiaPagamentoHistorico();
						filtroGuiaPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoHistorico.PARCELAMENTO_ID,
										parcelamento.getId()));
						filtroGuiaPagamentoHistorico.adicionarParametro(new ParametroSimples(
										FiltroGuiaPagamentoHistorico.NUMERO_PRESTACAO_TOTAL, new Short("1")));
						filtroGuiaPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoHistorico.VALOR_DEBITO,
										parcelamento.getValorEntrada()));

						Collection colecaoGuiaPagamentoHistorico = getFachada().pesquisar(filtroGuiaPagamentoHistorico,
										GuiaPagamentoHistorico.class.getName());

						// 1.1.1.1.2.1. Caso a entrada seja uma guia e não tenha sido paga.
						if(colecaoGuiaPagamentoHistorico != null && !colecaoGuiaPagamentoHistorico.isEmpty()){
							GuiaPagamentoHistorico guiaPagamentoHistorico = (GuiaPagamentoHistorico) Util
											.retonarObjetoDeColecao(colecaoGuiaPagamentoHistorico);

							FiltroPagamentoHistorico filtroPagamentoHistorico = new FiltroPagamentoHistorico();
							filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.GUIA_PAGAMENTO_ID,
											guiaPagamentoHistorico.getId()));

							Collection colecaoPagamentoHistorico = getFachada().pesquisar(filtroPagamentoHistorico,
											PagamentoHistorico.class.getName());
							if(colecaoPagamentoHistorico == null || colecaoPagamentoHistorico.isEmpty()){
								isGuiaHistoricoNaoPaga = true;
							}
						}else{
							isGuiaHistoricoNaoPaga = true;
						}

						// Ou caso a entrada seja uma conta e não tenha sido paga.

						FiltroConta filtroConta = new FiltroConta();
						filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.PARCELAMENTO_ID, parcelamento.getId()));

						Collection colecaoConta = getFachada().pesquisar(filtroConta, Conta.class.getName());

						if(colecaoConta != null && !colecaoConta.isEmpty()){
							Iterator itContas = colecaoConta.iterator();
							Conta conta = null;
							BigDecimal totalConta = BigDecimal.ZERO;

							while(itContas.hasNext()){
								conta = (Conta) itContas.next();

								totalConta = conta.getValorAgua().add(conta.getValorEsgoto()).add(conta.getDebitos()).add(totalConta);
								totalConta = totalConta.subtract(conta.getValorCreditos()).subtract(conta.getValorImposto());

								if(parcelamento.getValorEntrada().compareTo(totalConta) == 0){
									FiltroPagamento filtroPagamento = new FiltroPagamento();
									filtroPagamento.adicionarParametro(new ParametroSimples(FiltroPagamento.CONTA_ID, conta.getId()));

									Collection colecaoPagamento = getFachada().pesquisar(filtroPagamento, Pagamento.class.getName());
									if(colecaoPagamento == null || colecaoPagamento.isEmpty()){
										isContaNaoPaga = true;
									}
									break;
								}
							}
						}else{
							isContaNaoPaga = true;
						}

						FiltroContaHistorico filtroContaHistorico = new FiltroContaHistorico();
						filtroContaHistorico.adicionarParametro(new ParametroSimples(FiltroContaHistorico.PARCELAMENTO_ID, parcelamento
										.getId()));

						Collection colecaoContaHistorico = getFachada().pesquisar(filtroContaHistorico, ContaHistorico.class.getName());

						if(colecaoContaHistorico != null && !colecaoContaHistorico.isEmpty()){
							Iterator itContasHistorico = colecaoContaHistorico.iterator();
							ContaHistorico contaHistorico = null;
							BigDecimal totalConta = BigDecimal.ZERO;

							while(itContasHistorico.hasNext()){
								contaHistorico = (ContaHistorico) itContasHistorico.next();

								totalConta = contaHistorico.getValorAgua().add(contaHistorico.getValorEsgoto()).add(
												contaHistorico.getValorDebitos()).add(totalConta);

								totalConta = totalConta.subtract(contaHistorico.getValorCreditos()).subtract(
												contaHistorico.getValorImposto());

								if(parcelamento.getValorEntrada().compareTo(totalConta) == 0){
									FiltroPagamentoHistorico filtroPagamentoHistorico = new FiltroPagamentoHistorico();
									filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.CONTA_ID,
													contaHistorico.getId()));

									Collection colecaoPagamentoHistorico = getFachada().pesquisar(filtroPagamentoHistorico,
													PagamentoHistorico.class.getName());
									if(colecaoPagamentoHistorico == null || colecaoPagamentoHistorico.isEmpty()){
										isContaHistoricoNaoPaga = true;
									}
									break;
								}else{
									isContaHistoricoNaoPaga = true;
								}
							}
						}else{
							isContaHistoricoNaoPaga = true;
						}

						if(isGuiaNaoPaga && isGuiaHistoricoNaoPaga && isContaNaoPaga && isContaHistoricoNaoPaga){
							return true;
						}else if(!isGuiaNaoPaga || !isGuiaHistoricoNaoPaga){
							throw new ActionServletException("atencao.parcelamento.nao.pode.ser.desfeito");
						}else if(!isContaNaoPaga || !isContaHistoricoNaoPaga){
							throw new ActionServletException("atencao.parcelamento.conta.nao.pode.ser.desfeito");
						}

					}else{
						return true;
					}
				}// 1.1.1.2. Caso contrário, ou seja, alguma parcela tenha sido cobrada
				// (DBAC_NNPRESTACAOCOBRADAS diferente de zero da tabela DEBITO_A_COBRAR com
				// PARC_ID da tabela PARCELAMENTO).
				else{
					// 1.1.1.2.1. O sistema não permite desfazer o parcelamento.
					throw new ActionServletException("atencao.parcelamento.cobrado.nao.pode.ser.desfeito");
				}
			}// 1.1.2. Caso contrário (parcelamento não foi cobrado por meio de débito a cobrar)
			else{
				FiltroGuiaPagamento filtroGuiaPagamento = new FiltroGuiaPagamento();
				filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.PARCELAMENTO_ID, parcelamento.getId()));

				Collection colecaoGuiaPagamento = getFachada().pesquisar(filtroGuiaPagamento, GuiaPagamento.class.getName());

				Integer idGuiaPagamento = null;
				if(colecaoGuiaPagamento != null && !colecaoGuiaPagamento.isEmpty()){
					GuiaPagamento guiaPagamento = (GuiaPagamento) Util.retonarObjetoDeColecao(colecaoGuiaPagamento);
					idGuiaPagamento = guiaPagamento.getId();
				}else{
					FiltroGuiaPagamentoHistorico filtroGuiaPagamentoHistorico = new FiltroGuiaPagamentoHistorico();
					filtroGuiaPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoHistorico.PARCELAMENTO_ID,
									parcelamento.getId()));

					Collection colecaoGuiaPagamentoHistorico = getFachada().pesquisar(filtroGuiaPagamentoHistorico,
									GuiaPagamentoHistorico.class.getName());

					if(colecaoGuiaPagamentoHistorico != null && !colecaoGuiaPagamentoHistorico.isEmpty()){
						GuiaPagamentoHistorico guiaPagamentoHistorico = (GuiaPagamentoHistorico) Util
										.retonarObjetoDeColecao(colecaoGuiaPagamentoHistorico);
						idGuiaPagamento = guiaPagamentoHistorico.getId();
					}
				}

				// // 1.1.2.1. Caso o parcelamento tenha sido cobrado por meio de guias de
				// pagamento.
				if(idGuiaPagamento != null){

					// 1.1.2.1.1. Caso alguma guia tenha sido pago (existe registro na tabela
					// PAGAMENTO com GPAG_ID=GPAG_ID ou existe registro na tabela PAGAMENTO_HITORICO
					// com GPAG_ID=GPAG_ID).
					FiltroPagamento filtroPagamento = new FiltroPagamento();
					filtroPagamento.adicionarParametro(new ParametroSimples(FiltroPagamento.GUIA_PAGAMENTO_ID, idGuiaPagamento));

					Collection colecaoPagamento = getFachada().pesquisar(filtroPagamento, Pagamento.class.getName());

					// 1.1.2.1.1.1. O sistema não permite desfazer o parcelamento.
					if(colecaoPagamento != null && !colecaoPagamento.isEmpty()){
						throw new ActionServletException("atencao.parcelamento.nao.pode.ser.desfeito");
					}else{
						FiltroPagamentoHistorico filtroPagamentoHistorico = new FiltroPagamentoHistorico();
						filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.GUIA_PAGAMENTO_ID,
										idGuiaPagamento));

						Collection colecaoPagamentoHistorico = getFachada().pesquisar(filtroPagamentoHistorico,
										PagamentoHistorico.class.getName());

						// 1.1.2.1.1.1. O sistema não permite desfazer o parcelamento.
						if(colecaoPagamentoHistorico != null && !colecaoPagamentoHistorico.isEmpty()){
							throw new ActionServletException("atencao.parcelamento.nao.pode.ser.desfeito");
						}

						// 1.1.2.1.2.1. Caso o parcelamento seja referente à cobrança bancária
						// (existe ocorrência na tabela BOLETO_BANCARIO com PARC_ID=PARC_ID da
						// tabela PARCELAMENTO).
						FiltroBoletoBancario filtroBoletoBancario = new FiltroBoletoBancario();
						filtroBoletoBancario.adicionarParametro(new ParametroSimples(FiltroBoletoBancario.PARCELAMENTO_ID, parcelamento
										.getId()));
						filtroBoletoBancario.adicionarParametro(new ParametroNaoNulo(FiltroBoletoBancario.DOCUMENTOCOBRANCA_ID));
						filtroBoletoBancario.adicionarParametro(new ParametroNulo(FiltroBoletoBancario.BOLETOBANCARIO_ID_ORIGINAL));

						Collection colecaoBoletoBancario = getFachada().pesquisar(filtroBoletoBancario, BoletoBancario.class.getName());

						if(colecaoBoletoBancario != null && !colecaoBoletoBancario.isEmpty()){
							BoletoBancario boletoBancario = (BoletoBancario) Util.retonarObjetoDeColecao(colecaoBoletoBancario);

							// 1.1.2.1.2.1.1. Caso os boletos das parcelas não tenham sido gerados.
							filtroBoletoBancario.limparListaParametros();
							filtroBoletoBancario.adicionarParametro(new ParametroSimples(FiltroBoletoBancario.BOLETOBANCARIO_ID_ORIGINAL,
											boletoBancario.getId()));
							filtroBoletoBancario.adicionarCaminhoParaCarregamentoEntidade("boletoBancarioSituacao");

							colecaoBoletoBancario = getFachada().pesquisar(filtroBoletoBancario, BoletoBancario.class.getName());

							if(colecaoBoletoBancario == null || colecaoBoletoBancario.isEmpty()){
								return true;
							}else{
								Iterator itBoleto = colecaoBoletoBancario.iterator();
								BoletoBancario boletoAux = null;

								while(itBoleto.hasNext()){
									boletoAux = (BoletoBancario) itBoleto.next();

									// 1.1.2.1.2.1.2. Caso contrário, ou seja, os boletos das
									// parcelas tenham sido gerados e não estejam cancelados ou
									// baixados.
									if(boletoAux.getBoletoBancarioSituacao() != null
													&& boletoAux.getBoletoBancarioSituacao().getId() != null
													&& !boletoAux.getBoletoBancarioSituacao().getId().equals(BoletoBancarioSituacao.BAIXA)
													&& !boletoAux.getBoletoBancarioSituacao().getId().equals(
																	BoletoBancarioSituacao.CANCELADO)){
										// 1.1.2.1.2.1.2.1. O sistema não permite desfazer o
										// parcelamento.
										throw new ActionServletException("atencao.parcelamento.boleto.nao.pode.ser.desfeito");
									}

								}
							}

						}// 1.1.2.1.2.2. Caso contrário, ou seja, o parcelamento não seja referente
						// à cobrança bancária.
						else{
							return true;
						}
					}
				}
			}

		}

		return true;
	}

	/**
	 * [SB0003] – Validar autorização de acesso ao imóvel em cobrança administrativa pelos usuários
	 * da empresa contratante
	 * 
	 * @author Hugo Lima
	 * @date 15/08/2012
	 * @param usuario
	 * @param idImovel
	 * @param colecaoContas
	 */
	private void validarAcessoImovelCobrancaAdministrativaUsuarioEmpresaContratante(Usuario usuario, Integer idParcelamento, String teste){

		Collection<Integer> colecaoIdsDebitoACobrarNaoPermitidos = new ArrayList<Integer>();

		// Monta a lista contendo os debitos a cobrar não permitidos
		try{
			colecaoIdsDebitoACobrarNaoPermitidos.add(Integer
							.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA_COBRANCA_ADMINISTRATIVA.executar()));
			colecaoIdsDebitoACobrarNaoPermitidos.add(Integer
							.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO_COBRANCA_ADMINISTRATIVA.executar()));
			colecaoIdsDebitoACobrarNaoPermitidos.add(Integer
							.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE_COBRANCA_ADMINISTRATIVA
											.executar()));
			colecaoIdsDebitoACobrarNaoPermitidos.add(Integer
.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_PARCELAMENTO_COBRANCA_ADMINISTRATIVA
											.executar()));
		}catch(ControladorException e){
			throw new ActionServletException("erro.parametro.sistema", e, e.getMessage());
		}

		// 1. O sistema valida a autorização de acesso ao imóvel em cobrança administrativa pelos
		// usuários da empresa contratante de acordo com as seguintes regras
		FiltroParcelamento filtroParcelamento = new FiltroParcelamento();
		filtroParcelamento.adicionarParametro(new ParametroSimples(FiltroParcelamento.ID, idParcelamento));
		filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("imovel");
		filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("cobrancaForma");
		filtroParcelamento.setInitializeLazy(true);

		Parcelamento parcelamento = (Parcelamento) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroParcelamento,
						Parcelamento.class.getName()));

		Integer idImovel = parcelamento.getImovel().getId();
		Integer idCobrancaForma = parcelamento.getCobrancaForma().getId();

		// 1.1. Caso a forma de cobrança do parcelamento seja "Cobrança em Conta"
		if(idCobrancaForma.equals(CobrancaForma.COBRANCA_EM_CONTA)){
			Collection<DebitoACobrar> colecaoDebitoACobrarItem = Fachada.getInstancia().pesquisarItensDebitosACobrarPorParcelamento(
							idParcelamento);

			// 1.1.1. Caso existam, na lista de débitos a cobrar do parcelamento
			if(!Util.isVazioOrNulo(colecaoDebitoACobrarItem)){

				// débitos a cobrar de cobrança administrativa
				for(DebitoACobrar debitoACobrarItem : colecaoDebitoACobrarItem){
					if(colecaoIdsDebitoACobrarNaoPermitidos.contains(debitoACobrarItem.getDebitoTipo().getId())
									&& Fachada.getInstancia().obterValidacaoAutorizacaoAcessoImovelCobrancaAdministrativa(usuario,
													idImovel, ConstantesSistema.CODIGO_VALIDACAO_USUARIO_EMPRESA_CONTRATANTE).equals(
													ConstantesSistema.NAO)){
						throw new ActionServletException(
										"atencao.usuario_empresa_sem_acesso_imovel_desparcelamento_cobranca_administrativa", idImovel
														.toString(), usuario.getNomeUsuario());
					}
				}

			}

		}else{
			// 1.2. Caso contrário, ou seja, caso a forma de cobrança do parcelamento não seja
			// "Cobrança em Conta"
			Collection<Integer> colecaoIdsDebitosGuiasPagamentoPrestacoesParcelamento = Fachada.getInstancia()
							.obterDebitosGuiasPagamentoPrestacoesParcelamento(idParcelamento);

			// 1.2.1. Caso existam, na lista de guias de pagamento do parcelamento, guias de
			// pagamento de cobrança administrativa (algum dos DBTP_ID da tabela
			// GUIA_PAGAMENTO_PRESTACAO ocorre na lista de tipos de débitos do parcelamento de
			// cobrança administrativa
			if(!Util.isVazioOrNulo(colecaoIdsDebitosGuiasPagamentoPrestacoesParcelamento)){
				for(Integer idDebito : colecaoIdsDebitosGuiasPagamentoPrestacoesParcelamento){
					if(colecaoIdsDebitoACobrarNaoPermitidos.contains(idDebito)
									&& Fachada.getInstancia().obterValidacaoAutorizacaoAcessoImovelCobrancaAdministrativa(usuario,
													idImovel, ConstantesSistema.CODIGO_VALIDACAO_USUARIO_EMPRESA_CONTRATANTE).equals(
													ConstantesSistema.NAO)){
						throw new ActionServletException(
										"atencao.usuario_empresa_sem_acesso_imovel_desparcelamento_cobranca_administrativa", idImovel
														.toString(), usuario.getNomeUsuario());
					}
				}
			}
		}
	}

	/**
	 * [SB0003] – Validar autorização de acesso ao imóvel em cobrança administrativa pelos usuários
	 * da empresa contratante
	 * 
	 * @author Hugo Lima
	 * @date 15/08/2012
	 * @author Saulo Lima
	 * @date 01/08/2013
	 * @param usuario
	 * @param colecaoParcelamento
	 */
	private void validarAcessoImovelCobrancaAdministrativaUsuarioEmpresaContratante(Usuario usuario, Integer idParcelamento){

		FiltroParcelamento filtroParcelamento = new FiltroParcelamento();
		filtroParcelamento.adicionarParametro(new ParametroSimples(FiltroParcelamento.ID, idParcelamento));
		filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("imovel");
		filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("cobrancaForma");
		filtroParcelamento.setInitializeLazy(true);

		Parcelamento parcelamento = (Parcelamento) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroParcelamento,
						Parcelamento.class.getName()));

		Collection<Integer> colecaoIdsDebitoACobrarNaoPermitidos = new ArrayList<Integer>();
		boolean temPermissao = true;

		// Monta a lista contendo os debitos a cobrar não permitidos
		try{
			colecaoIdsDebitoACobrarNaoPermitidos.add(Integer
							.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA_COBRANCA_ADMINISTRATIVA.executar()));
			colecaoIdsDebitoACobrarNaoPermitidos.add(Integer
							.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO_COBRANCA_ADMINISTRATIVA.executar()));
			colecaoIdsDebitoACobrarNaoPermitidos.add(Integer
							.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE_COBRANCA_ADMINISTRATIVA
											.executar()));
			colecaoIdsDebitoACobrarNaoPermitidos
							.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_PARCELAMENTO_COBRANCA_ADMINISTRATIVA
											.executar()));
			colecaoIdsDebitoACobrarNaoPermitidos.add(Integer
							.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_PARCELAMENTO_COBRANCA_ADMINISTRATIVA.executar()));
		}catch(ControladorException e){
			throw new ActionServletException("erro.parametro.sistema", e, e.getMessage());
		}

		Short retornoValidacao = Fachada.getInstancia().obterValidacaoAutorizacaoAcessoImovelCobrancaAdministrativa(usuario,
						parcelamento.getImovel().getId(), ConstantesSistema.CODIGO_VALIDACAO_USUARIO_EMPRESA_CONTRATANTE);

		boolean temPermissaoEspecial = Fachada.getInstancia().verificarPermissaoEspecial(
						PermissaoEspecial.ACESSAR_DADOS_IMOVEL_COBRANCA_ADMINISTRATIVA, usuario);

		Integer idCobrancaForma = parcelamento.getCobrancaForma().getId();

		// 1.1. Caso a forma de cobrança do parcelamento seja "Cobrança em Conta"
		if(idCobrancaForma.equals(CobrancaForma.COBRANCA_EM_CONTA)){
			Collection<DebitoACobrar> colecaoDebitoACobrarItem = Fachada.getInstancia().pesquisarItensDebitosACobrarPorParcelamento(
							parcelamento.getId());

			// 1.1.1. Caso existam, na lista de débitos a cobrar do parcelamento
			if(!Util.isVazioOrNulo(colecaoDebitoACobrarItem)){

				// débitos a cobrar de cobrança administrativa
				for(DebitoACobrar debitoACobrarItem : colecaoDebitoACobrarItem){
					if(colecaoIdsDebitoACobrarNaoPermitidos.contains(debitoACobrarItem.getDebitoTipo().getId())
									&& retornoValidacao.equals(ConstantesSistema.NAO)){
						// 1.1.1.2. Caso o usuário não tenha autorização de acesso ao imóvel
						temPermissao = false;
						break;
					}
				}

				// 1.1.2. Caso contrário, ou seja, não existam, na lista de débitos a cobrar do
				// parcelamento, débitos a cobrar de cobrança administrativa
				if(temPermissao){
					// 1.1.2.1. Caso o usuário logado no sistema não pertença a uma empresa de
					// cobrança administrativa
					if(!Fachada.getInstancia().existeEmpresaCobrancaContrato(usuario.getEmpresa().getId())){

						// 1.1.2.1.1.1. Caso o usuário logado no sistema não possua permissão
						// especial para acesso aos dados do imóvel em cobrança administrativa
						if(!temPermissaoEspecial){

							// 1.1.2.1.1. Caso existam, na lista de débitos a cobrar do
							// parcelamento, débitos a cobrar remuneráveis
							for(DebitoACobrar debitoACobrarItem : colecaoDebitoACobrarItem){
								if(debitoACobrarItem.getIndicadorRemuneraCobrancaAdministrativa().equals(ConstantesSistema.SIM)){
									throw new ActionServletException(
													"atencao.usuario_empresa_sem_acesso_imovel_desparcelamento_cobranca_administrativa_item",
													parcelamento.getImovel().getId().toString(), usuario.getNomeUsuario());
								}
							}
						}
					}
				}
			}

		}else{
			// 1.2. Caso contrário, ou seja, caso a forma de cobrança do parcelamento não seja
			// "Cobrança em Conta"
			Collection<Integer> colecaoIdsDebitosGuiasPagamentoPrestacoesParcelamento = Fachada.getInstancia()
							.obterDebitosGuiasPagamentoPrestacoesParcelamento(parcelamento.getId());

			// 1.2.1. Caso existam, na lista de guias de pagamento do parcelamento
			if(!Util.isVazioOrNulo(colecaoIdsDebitosGuiasPagamentoPrestacoesParcelamento)){
				for(Integer idDebito : colecaoIdsDebitosGuiasPagamentoPrestacoesParcelamento){
					if(colecaoIdsDebitoACobrarNaoPermitidos.contains(idDebito) && retornoValidacao.equals(ConstantesSistema.NAO)){
						// 1.2.1.2. Caso o usuário não tenha autorização de acesso ao imóvel
						temPermissao = false;
						break;
					}
				}

				// Caso contrário, ou seja, não existam, na lista de guias de pagamento do
				// parcelamento, guias de pagamento de cobrança administrativa
				if(temPermissao){
					// 1.2.2.1. Caso o usuário logado no sistema não pertença a uma empresa de
					// cobrança administrativa
					if(!Fachada.getInstancia().existeEmpresaCobrancaContrato(usuario.getEmpresa().getId())){

						// 1.2.2.1.1.1. Caso o usuário logado no sistema não possua permissão
						// especial para acesso aos dados do imóvel em cobrança administrativa
						if(!temPermissaoEspecial){

							FiltroGuiaPagamento filtro = new FiltroGuiaPagamento();
							filtro.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.PARCELAMENTO_ID, parcelamento.getId()));

							Collection<GuiaPagamento> colecaoGuias = Fachada.getInstancia()
											.pesquisar(filtro, GuiaPagamento.class.getName());

							if(!Util.isVazioOrNulo(colecaoGuias)){

								for(GuiaPagamento guiaPagamento : colecaoGuias){

									FiltroGuiaPagamentoPrestacao filtroPrestacao = new FiltroGuiaPagamentoPrestacao();
									filtroPrestacao.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacao.GUIA_PAGAMENTO_ID,
													guiaPagamento.getId()));

									Collection<GuiaPagamentoPrestacao> colecaoGuiaPretacao = Fachada.getInstancia().pesquisar(
													filtroPrestacao, GuiaPagamentoPrestacao.class.getName());

									if(!Util.isVazioOrNulo(colecaoGuiaPretacao)){
										for(GuiaPagamentoPrestacao guiaPagamentoPrestacao : colecaoGuiaPretacao){

											// 1.2.2.1.1. Caso existam, na lista de guias de
											// pagamento do parcelamento
											if(guiaPagamentoPrestacao.getIndicadorRemuneraCobrancaAdministrativa().equals(
															ConstantesSistema.SIM)){
												throw new ActionServletException(
																"atencao.usuario_empresa_sem_acesso_imovel_desparcelamento_cobranca_administrativa_item",
																parcelamento.getImovel().getId().toString(), usuario.getNomeUsuario());
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		if(!temPermissao){
			throw new ActionServletException("atencao.usuario_empresa_sem_acesso_imovel_desparcelamento_cobranca_administrativa",
							parcelamento.getImovel().getId().toString(), usuario.getNomeUsuario());
		}
	}

}
