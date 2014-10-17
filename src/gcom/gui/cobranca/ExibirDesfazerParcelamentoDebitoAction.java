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
 * Desfazer Parcelamento D�bito
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

		// Mudar isso quando implementar a parte de seguran�a
		// HttpSession sessao = httpServletRequest.getSession(false);

		String codigo = httpServletRequest.getParameter("codigoParcelamento").trim();

		String motivo = httpServletRequest.getParameter("motivo").trim();

		// [SB0001] � Verificar possibilidade de desfazer o parcelamento.
		if(this.permiteDesfazerParcelamento(Integer.valueOf(codigo), fachada)){
			// [SB0003 � Validar autoriza��o de acesso ao im�vel em cobran�a administrativa pelos
			// usu�rios da empresa contratante]
			this.validarAcessoImovelCobrancaAdministrativaUsuarioEmpresaContratante(usuario, Integer.valueOf(codigo));
			fachada.desfazerParcelamentosDebito(motivo, Integer.valueOf(codigo), usuario);
		}

		// Monta a p�gina de sucesso
		montarPaginaSucesso(httpServletRequest, "Parcelamento de D�bitos desfeito com sucesso.",
						"Realizar outra manuten��o de Parcelamento de D�bitos", "exibirConsultarListaParcelamentoDebitoAction.do?menu=sim");

		return retorno;
	}

	/**
	 * [UC0252] � Consultar Parcelamentos de D�bitos.
	 * [SB0001] � Verificar possibilidade de desfazer o parcelamento.
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

		// 1.1. Caso o m�s/ano de efetiva��o do parcelamento de d�bitos seja igual ou superior ao
		// m�s/ano de arrecada��o corrente e o parcelamento esteja com situa��o normal (M�S/ANO do
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

			// 1.1.1. Caso o parcelamento tenha sido cobrado por meio de d�bito a cobrar (existe
			// ocorr�ncia na tabela DEBITO_A_COBRAR com PARC_ID=PARC_ID da tabela PARCELAMENTO)
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
					// 1.1.1.1.1. Caso n�o exista entrada (PARC_VLENTRADA = 0 ou nulo):
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

						// 1.1.1.1.2.1. Caso a entrada seja uma guia e n�o tenha sido paga.
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

						// 1.1.1.1.2.1. Caso a entrada seja uma guia e n�o tenha sido paga.
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

						// Ou caso a entrada seja uma conta e n�o tenha sido paga.

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
				}// 1.1.1.2. Caso contr�rio, ou seja, alguma parcela tenha sido cobrada
				// (DBAC_NNPRESTACAOCOBRADAS diferente de zero da tabela DEBITO_A_COBRAR com
				// PARC_ID da tabela PARCELAMENTO).
				else{
					// 1.1.1.2.1. O sistema n�o permite desfazer o parcelamento.
					throw new ActionServletException("atencao.parcelamento.cobrado.nao.pode.ser.desfeito");
				}
			}// 1.1.2. Caso contr�rio (parcelamento n�o foi cobrado por meio de d�bito a cobrar)
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

					// 1.1.2.1.1.1. O sistema n�o permite desfazer o parcelamento.
					if(colecaoPagamento != null && !colecaoPagamento.isEmpty()){
						throw new ActionServletException("atencao.parcelamento.nao.pode.ser.desfeito");
					}else{
						FiltroPagamentoHistorico filtroPagamentoHistorico = new FiltroPagamentoHistorico();
						filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.GUIA_PAGAMENTO_ID,
										idGuiaPagamento));

						Collection colecaoPagamentoHistorico = getFachada().pesquisar(filtroPagamentoHistorico,
										PagamentoHistorico.class.getName());

						// 1.1.2.1.1.1. O sistema n�o permite desfazer o parcelamento.
						if(colecaoPagamentoHistorico != null && !colecaoPagamentoHistorico.isEmpty()){
							throw new ActionServletException("atencao.parcelamento.nao.pode.ser.desfeito");
						}

						// 1.1.2.1.2.1. Caso o parcelamento seja referente � cobran�a banc�ria
						// (existe ocorr�ncia na tabela BOLETO_BANCARIO com PARC_ID=PARC_ID da
						// tabela PARCELAMENTO).
						FiltroBoletoBancario filtroBoletoBancario = new FiltroBoletoBancario();
						filtroBoletoBancario.adicionarParametro(new ParametroSimples(FiltroBoletoBancario.PARCELAMENTO_ID, parcelamento
										.getId()));
						filtroBoletoBancario.adicionarParametro(new ParametroNaoNulo(FiltroBoletoBancario.DOCUMENTOCOBRANCA_ID));
						filtroBoletoBancario.adicionarParametro(new ParametroNulo(FiltroBoletoBancario.BOLETOBANCARIO_ID_ORIGINAL));

						Collection colecaoBoletoBancario = getFachada().pesquisar(filtroBoletoBancario, BoletoBancario.class.getName());

						if(colecaoBoletoBancario != null && !colecaoBoletoBancario.isEmpty()){
							BoletoBancario boletoBancario = (BoletoBancario) Util.retonarObjetoDeColecao(colecaoBoletoBancario);

							// 1.1.2.1.2.1.1. Caso os boletos das parcelas n�o tenham sido gerados.
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

									// 1.1.2.1.2.1.2. Caso contr�rio, ou seja, os boletos das
									// parcelas tenham sido gerados e n�o estejam cancelados ou
									// baixados.
									if(boletoAux.getBoletoBancarioSituacao() != null
													&& boletoAux.getBoletoBancarioSituacao().getId() != null
													&& !boletoAux.getBoletoBancarioSituacao().getId().equals(BoletoBancarioSituacao.BAIXA)
													&& !boletoAux.getBoletoBancarioSituacao().getId().equals(
																	BoletoBancarioSituacao.CANCELADO)){
										// 1.1.2.1.2.1.2.1. O sistema n�o permite desfazer o
										// parcelamento.
										throw new ActionServletException("atencao.parcelamento.boleto.nao.pode.ser.desfeito");
									}

								}
							}

						}// 1.1.2.1.2.2. Caso contr�rio, ou seja, o parcelamento n�o seja referente
						// � cobran�a banc�ria.
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
	 * [SB0003] � Validar autoriza��o de acesso ao im�vel em cobran�a administrativa pelos usu�rios
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

		// Monta a lista contendo os debitos a cobrar n�o permitidos
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

		// 1. O sistema valida a autoriza��o de acesso ao im�vel em cobran�a administrativa pelos
		// usu�rios da empresa contratante de acordo com as seguintes regras
		FiltroParcelamento filtroParcelamento = new FiltroParcelamento();
		filtroParcelamento.adicionarParametro(new ParametroSimples(FiltroParcelamento.ID, idParcelamento));
		filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("imovel");
		filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("cobrancaForma");
		filtroParcelamento.setInitializeLazy(true);

		Parcelamento parcelamento = (Parcelamento) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroParcelamento,
						Parcelamento.class.getName()));

		Integer idImovel = parcelamento.getImovel().getId();
		Integer idCobrancaForma = parcelamento.getCobrancaForma().getId();

		// 1.1. Caso a forma de cobran�a do parcelamento seja "Cobran�a em Conta"
		if(idCobrancaForma.equals(CobrancaForma.COBRANCA_EM_CONTA)){
			Collection<DebitoACobrar> colecaoDebitoACobrarItem = Fachada.getInstancia().pesquisarItensDebitosACobrarPorParcelamento(
							idParcelamento);

			// 1.1.1. Caso existam, na lista de d�bitos a cobrar do parcelamento
			if(!Util.isVazioOrNulo(colecaoDebitoACobrarItem)){

				// d�bitos a cobrar de cobran�a administrativa
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
			// 1.2. Caso contr�rio, ou seja, caso a forma de cobran�a do parcelamento n�o seja
			// "Cobran�a em Conta"
			Collection<Integer> colecaoIdsDebitosGuiasPagamentoPrestacoesParcelamento = Fachada.getInstancia()
							.obterDebitosGuiasPagamentoPrestacoesParcelamento(idParcelamento);

			// 1.2.1. Caso existam, na lista de guias de pagamento do parcelamento, guias de
			// pagamento de cobran�a administrativa (algum dos DBTP_ID da tabela
			// GUIA_PAGAMENTO_PRESTACAO ocorre na lista de tipos de d�bitos do parcelamento de
			// cobran�a administrativa
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
	 * [SB0003] � Validar autoriza��o de acesso ao im�vel em cobran�a administrativa pelos usu�rios
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

		// Monta a lista contendo os debitos a cobrar n�o permitidos
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

		// 1.1. Caso a forma de cobran�a do parcelamento seja "Cobran�a em Conta"
		if(idCobrancaForma.equals(CobrancaForma.COBRANCA_EM_CONTA)){
			Collection<DebitoACobrar> colecaoDebitoACobrarItem = Fachada.getInstancia().pesquisarItensDebitosACobrarPorParcelamento(
							parcelamento.getId());

			// 1.1.1. Caso existam, na lista de d�bitos a cobrar do parcelamento
			if(!Util.isVazioOrNulo(colecaoDebitoACobrarItem)){

				// d�bitos a cobrar de cobran�a administrativa
				for(DebitoACobrar debitoACobrarItem : colecaoDebitoACobrarItem){
					if(colecaoIdsDebitoACobrarNaoPermitidos.contains(debitoACobrarItem.getDebitoTipo().getId())
									&& retornoValidacao.equals(ConstantesSistema.NAO)){
						// 1.1.1.2. Caso o usu�rio n�o tenha autoriza��o de acesso ao im�vel
						temPermissao = false;
						break;
					}
				}

				// 1.1.2. Caso contr�rio, ou seja, n�o existam, na lista de d�bitos a cobrar do
				// parcelamento, d�bitos a cobrar de cobran�a administrativa
				if(temPermissao){
					// 1.1.2.1. Caso o usu�rio logado no sistema n�o perten�a a uma empresa de
					// cobran�a administrativa
					if(!Fachada.getInstancia().existeEmpresaCobrancaContrato(usuario.getEmpresa().getId())){

						// 1.1.2.1.1.1. Caso o usu�rio logado no sistema n�o possua permiss�o
						// especial para acesso aos dados do im�vel em cobran�a administrativa
						if(!temPermissaoEspecial){

							// 1.1.2.1.1. Caso existam, na lista de d�bitos a cobrar do
							// parcelamento, d�bitos a cobrar remuner�veis
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
			// 1.2. Caso contr�rio, ou seja, caso a forma de cobran�a do parcelamento n�o seja
			// "Cobran�a em Conta"
			Collection<Integer> colecaoIdsDebitosGuiasPagamentoPrestacoesParcelamento = Fachada.getInstancia()
							.obterDebitosGuiasPagamentoPrestacoesParcelamento(parcelamento.getId());

			// 1.2.1. Caso existam, na lista de guias de pagamento do parcelamento
			if(!Util.isVazioOrNulo(colecaoIdsDebitosGuiasPagamentoPrestacoesParcelamento)){
				for(Integer idDebito : colecaoIdsDebitosGuiasPagamentoPrestacoesParcelamento){
					if(colecaoIdsDebitoACobrarNaoPermitidos.contains(idDebito) && retornoValidacao.equals(ConstantesSistema.NAO)){
						// 1.2.1.2. Caso o usu�rio n�o tenha autoriza��o de acesso ao im�vel
						temPermissao = false;
						break;
					}
				}

				// Caso contr�rio, ou seja, n�o existam, na lista de guias de pagamento do
				// parcelamento, guias de pagamento de cobran�a administrativa
				if(temPermissao){
					// 1.2.2.1. Caso o usu�rio logado no sistema n�o perten�a a uma empresa de
					// cobran�a administrativa
					if(!Fachada.getInstancia().existeEmpresaCobrancaContrato(usuario.getEmpresa().getId())){

						// 1.2.2.1.1.1. Caso o usu�rio logado no sistema n�o possua permiss�o
						// especial para acesso aos dados do im�vel em cobran�a administrativa
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
