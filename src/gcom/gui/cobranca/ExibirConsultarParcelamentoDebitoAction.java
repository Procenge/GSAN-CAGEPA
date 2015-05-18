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

import gcom.arrecadacao.pagamento.FiltroGuiaPagamento;
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaForma;
import gcom.cobranca.parcelamento.*;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.FiltroConta;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.gui.GcomAction;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class ExibirConsultarParcelamentoDebitoAction
				extends GcomAction {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @author Saulo Lima
	 * @date 26/01/2009
	 *       Altera��o para pegar o cliente usu�rio do imovel que tenha dataFimRelacao igual a nulo
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta a a��o de retorno
		ActionForward retorno = actionMapping.findForward("consultarParcelamentoDebito");

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		ParcelamentoDebitoActionForm parcelamentoDebitoActionForm = (ParcelamentoDebitoActionForm) actionForm;

		// Obt�m a facahda
		Fachada fachada = Fachada.getInstancia();

		Collection<Integer> idsContaEP = new ArrayList<Integer>();

		// Pega os codigos que o usuario digitou para a pesquisa direta de imovel
		String codigoImovel = httpServletRequest.getParameter("codigoImovel");
		String codigoParcelamento = httpServletRequest.getParameter("codigoParcelamento");
		sessao.setAttribute("idParcelamento", codigoParcelamento);

		if(codigoImovel != null && !codigoImovel.trim().equals("")){

			// Alterado por Raphael Rossiter em 24/01/2007

			// Pesquisa o imovel na base
			FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, codigoImovel));
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID,
							ClienteRelacaoTipo.USUARIO));
			filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial.municipio.unidadeFederacao");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroBairro.bairro");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.cep");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.quadra");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.imovelPerfil");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.ligacaoAguaSituacao");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.ligacaoEsgotoSituacao");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente.clienteTipo");
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("clienteRelacaoTipo");

			Collection<ClienteImovel> imovelPesquisado = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

			// Se nenhum imovel for encontrado a mensagem � enviada para a p�gina
			if(imovelPesquisado != null && imovelPesquisado.isEmpty()){
				httpServletRequest.setAttribute("enderecoFormatado", "Matr�cula Inexistente".toUpperCase());
				parcelamentoDebitoActionForm.setInscricao("");
				parcelamentoDebitoActionForm.setNomeCliente("");
				parcelamentoDebitoActionForm.setCpfCnpj("");
				parcelamentoDebitoActionForm.setSituacaoAgua("");
				parcelamentoDebitoActionForm.setSituacaoEsgoto("");
				parcelamentoDebitoActionForm.setImovelPerfil("");
			}
			// obtem o imovel pesquisado
			if(imovelPesquisado != null && !imovelPesquisado.isEmpty()){

				ClienteImovel dadosImovel = (ClienteImovel) ((List) imovelPesquisado).get(0);

				// O endere�o foi encontrado
				if(dadosImovel.getImovel().getId() != null){
					parcelamentoDebitoActionForm.setCodigoImovel("" + dadosImovel.getImovel().getId());
				}
				if(dadosImovel.getImovel().getInscricaoFormatada() != null){
					parcelamentoDebitoActionForm.setInscricao("" + dadosImovel.getImovel().getInscricaoFormatada());
				}
				if(dadosImovel.getImovel().getLigacaoAguaSituacao() != null){
					parcelamentoDebitoActionForm.setSituacaoAgua("" + dadosImovel.getImovel().getLigacaoAguaSituacao().getDescricao());
				}
				if(dadosImovel.getImovel().getLigacaoEsgotoSituacao() != null){
					parcelamentoDebitoActionForm.setSituacaoEsgoto("" + dadosImovel.getImovel().getLigacaoEsgotoSituacao().getDescricao());
				}
				if(dadosImovel.getCliente().getNome() != null){
					parcelamentoDebitoActionForm.setNomeCliente("" + dadosImovel.getCliente().getNome());
				}
				if(dadosImovel.getImovel().getImovelPerfil() != null){
					parcelamentoDebitoActionForm.setImovelPerfil("" + dadosImovel.getImovel().getImovelPerfil().getDescricao());
				}
				if(dadosImovel.getCliente().getClienteTipo().getIndicadorPessoaFisicaJuridica() == 1){
					if(dadosImovel.getCliente().getCpfFormatado() != null){
						parcelamentoDebitoActionForm.setCpfCnpj("" + dadosImovel.getCliente().getCpfFormatado());
					}
				}else{
					if(dadosImovel.getCliente().getCnpjFormatado() != null){
						parcelamentoDebitoActionForm.setCpfCnpj("" + dadosImovel.getCliente().getCnpjFormatado());
					}
				}
				if(dadosImovel.getCliente().getClienteTipo().getIndicadorPessoaFisicaJuridica() != null){
					sessao.setAttribute("indicadorPessoaFisicaJuridica", ""
									+ dadosImovel.getCliente().getClienteTipo().getIndicadorPessoaFisicaJuridica());
				}
				if(dadosImovel.getImovel().getNumeroParcelamento() != null){
					parcelamentoDebitoActionForm.setParcelamento("" + dadosImovel.getImovel().getNumeroParcelamento());
				}
				if(dadosImovel.getImovel().getNumeroReparcelamento() != null){
					parcelamentoDebitoActionForm.setReparcelamento("" + dadosImovel.getImovel().getNumeroReparcelamento());
				}
				if(dadosImovel.getImovel().getNumeroReparcelamentoConsecutivos() != null){
					parcelamentoDebitoActionForm.setReparcelamentoConsecutivo(""
									+ dadosImovel.getImovel().getNumeroReparcelamentoConsecutivos());
				}
				// Manda a colecao pelo request
				httpServletRequest.setAttribute("imovelPesquisado", imovelPesquisado);
				String enderecoFormatado = "";
				try{
					enderecoFormatado = fachada.pesquisarEnderecoFormatado(Integer.valueOf(codigoImovel));
				}catch(NumberFormatException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch(ControladorException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				httpServletRequest.setAttribute("enderecoFormatado", enderecoFormatado);

				FiltroParcelamento filtroParcelamento = new FiltroParcelamento();

				filtroParcelamento.adicionarParametro(new ParametroSimples(FiltroParcelamento.ID, codigoParcelamento));

				filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("parcelamentoSituacao");

				filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("usuario");

				filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("funcionario");

				filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("cobrancaForma");

				filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("cliente");

				Collection<Parcelamento> colecaoParcelamento = fachada.pesquisar(filtroParcelamento, Parcelamento.class.getName());

				SistemaParametro sistemaParametro = null;

				if(colecaoParcelamento != null && !colecaoParcelamento.isEmpty()){
					httpServletRequest.setAttribute("colecaoParcelamento", colecaoParcelamento);

					Iterator iteratorParcelamento = colecaoParcelamento.iterator();
					while(iteratorParcelamento.hasNext()){

						Parcelamento parcelamento = (Parcelamento) iteratorParcelamento.next();

						// Retorna o �nico objeto da tabela sistemaParametro
						sistemaParametro = fachada.pesquisarParametrosDoSistema();

						if((parcelamento.getAnoMesReferenciaFaturamento().equals(new Integer(sistemaParametro.getAnoMesFaturamento())))
										&& parcelamento.getParcelamentoSituacao().getId().intValue() == ParcelamentoSituacao.NORMAL
														.intValue()){

							FiltroParcelamentoMotivoDesfazer filtroParcelamentoMotivoDesfazer = new FiltroParcelamentoMotivoDesfazer();
							Collection<ParcelamentoMotivoDesfazer> collectionParcelamentoMotivoDesfazer = fachada.pesquisar(
											filtroParcelamentoMotivoDesfazer, ParcelamentoMotivoDesfazer.class.getName());

							httpServletRequest.setAttribute("collectionParcelamentoMotivoDesfazer", collectionParcelamentoMotivoDesfazer);
						}

						if(parcelamento.getCobrancaForma().getId().equals(CobrancaForma.COBRANCA_EM_CONTA)){
							// D�bito a Cobrar
							Collection colecaoClienteDebitoACobrar = fachada.pesquisarClienteDebitoACobrar(dadosImovel.getCliente());
							httpServletRequest.setAttribute("colecaoClienteDebitoACobrar", colecaoClienteDebitoACobrar);
						}else{
							// Guia de Pagamento
							Collection colecaoClienteGuiaPagamento = fachada.pesquisarClienteGuiaPagamento(dadosImovel.getCliente());
							httpServletRequest.setAttribute("colecaoClienteGuiaPagamento", colecaoClienteGuiaPagamento);
						}

					}
				}

			}

			FiltroParcelamento filtroParcelamento = new FiltroParcelamento();

			filtroParcelamento.adicionarParametro(new ParametroSimples(FiltroParcelamento.ID, codigoParcelamento));

			filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("parcelamentoSituacao");
			filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("usuario");
			filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("funcionario");
			filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("cobrancaForma");
			filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("cliente");

			Collection<Parcelamento> colecaoParcelamento = fachada.pesquisar(filtroParcelamento, Parcelamento.class.getName());



			if(colecaoParcelamento != null && !colecaoParcelamento.isEmpty()){
				httpServletRequest.setAttribute("colecaoParcelamento", colecaoParcelamento);

				Iterator iteratorParcelamento = colecaoParcelamento.iterator();
				while(iteratorParcelamento.hasNext()){

					Parcelamento parcelamento = (Parcelamento) iteratorParcelamento.next();
					
					if((parcelamento.getValorSucumbenciaAnterior() != null && parcelamento.getValorSucumbenciaAnterior().compareTo(
									BigDecimal.ZERO) != 0)
									|| (parcelamento.getValorSucumbenciaAtual() != null && parcelamento.getValorSucumbenciaAtual()
													.compareTo(BigDecimal.ZERO) != 0)){
						httpServletRequest.setAttribute("visualizarSucumbencia", "S");
						
						parcelamentoDebitoActionForm.setValorSucumbenciaAnterior(Util.formatarMoedaReal(parcelamento.getValorSucumbenciaAnterior()));

						parcelamentoDebitoActionForm.setValorSucumbenciaAtual(Util.formatarMoedaReal(parcelamento
										.getValorSucumbenciaAtual()));
						parcelamentoDebitoActionForm.setValorAcrescimosSucumbenciaAnterior(Util.formatarMoedaReal(parcelamento
										.getValorJurosMoraSucumbenciaAnterior().add(
														parcelamento.getValorAtualizacaoMonetariaSucumbenciaAnterior())));
						parcelamentoDebitoActionForm.setValorDiligencias(Util.formatarMoedaReal(parcelamento.getValorDiligencias()));
						parcelamentoDebitoActionForm.setNumeroParcelasSucumbencia(parcelamento.getNumeroParcelasSucumbencia().toString());
						parcelamentoDebitoActionForm.setValorDiligencias(Util.formatarMoedaReal(parcelamento.getValorDiligencias()));

					}else{
						httpServletRequest.removeAttribute("visualizarSucumbencia");
					}
						
						
					if(parcelamento.getCliente() != null && parcelamento.getCliente().getNome() != null){
						parcelamentoDebitoActionForm.setNomeClienteResponsavel(parcelamento.getCliente().getNome());
					}

					if(parcelamento.getParcelamentoSituacao().getId().intValue() == ParcelamentoSituacao.DESFEITO.intValue()){
						parcelamentoDebitoActionForm.setDataParcelamentoDesfeito(Util.formatarData(parcelamento.getUltimaAlteracao()));
					}else{
						parcelamentoDebitoActionForm.setDataParcelamentoDesfeito("");
					}
					
					// Atribuir Valor na Sessao para saber qual tela ser� a de retorno dos Jsps de
					// Testesmunha, Dadoscomplementares e Execu��o Fiscal
					sessao.setAttribute("TermoParcelamentoConsultar", "True");
					sessao.setAttribute("idImovel", codigoImovel);
					sessao.removeAttribute("idBoletoBancario");
					sessao.removeAttribute("TermoParcelamentoPreview");
					sessao.removeAttribute("parcelamentoTermoTestemunhas");
					sessao.removeAttribute("parcelamentoDadosTermo");

					try{
						Short indicadorParcelamentoExecucaoFiscal = fachada.verificarParcelamentoExecucaoFiscal(parcelamento);
						if(indicadorParcelamentoExecucaoFiscal.equals(Short.valueOf("1"))){
							sessao.setAttribute("indicadorParcelamentoExecucaoFiscal", 'S');
						}else{
							sessao.removeAttribute("indicadorParcelamentoExecucaoFiscal");
						}
					}catch(NumberFormatException e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if(fachada.podeDesfazer(parcelamento)){
							FiltroParcelamentoMotivoDesfazer filtroParcelamentoMotivoDesfazer = new FiltroParcelamentoMotivoDesfazer();
							Collection<ParcelamentoMotivoDesfazer> collectionParcelamentoMotivoDesfazer = fachada.pesquisar(
											filtroParcelamentoMotivoDesfazer, ParcelamentoMotivoDesfazer.class.getName());

							httpServletRequest.setAttribute("collectionParcelamentoMotivoDesfazer", collectionParcelamentoMotivoDesfazer);

					}

							// Verifica se a entrada do parcelamento tenha sido atrav�s de contas
							// marcadas como EP
							FiltroConta filtroConta = new FiltroConta();
							filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.IMOVEL_ID, codigoImovel));

							filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.PARCELAMENTO_ID, codigoParcelamento));

							Collection colecaoConta2 = fachada.pesquisar(filtroConta, Conta.class.getName());

							if(colecaoConta2 != null && !colecaoConta2.isEmpty()){

								Iterator iteratorConta = colecaoConta2.iterator();

								while(iteratorConta.hasNext()){

									Conta conta = null;

									conta = (Conta) iteratorConta.next();

									if((conta.getDebitoCreditoSituacaoAtual().getId().intValue() == DebitoCreditoSituacao.NORMAL.intValue())
													|| (conta.getDebitoCreditoSituacaoAtual().getId().intValue() == DebitoCreditoSituacao.RETIFICADA
																	.intValue())
													|| (conta.getDebitoCreditoSituacaoAtual().getId().intValue() == DebitoCreditoSituacao.INCLUIDA
																	.intValue())){

										idsContaEP.add(conta.getId());

									}
								}
							}



					}

				}

		}

		FiltroParcelamentoConfiguracaoPrestacao filtroParcelamentoConfiguracaoPrestacao = new FiltroParcelamentoConfiguracaoPrestacao();
		filtroParcelamentoConfiguracaoPrestacao.adicionarParametro(new ParametroSimples(
						FiltroParcelamentoConfiguracaoPrestacao.PARCELAMENTO_ID, Integer.valueOf(codigoParcelamento)));
		filtroParcelamentoConfiguracaoPrestacao.setCampoOrderBy(FiltroParcelamentoConfiguracaoPrestacao.NUMERO_SEQUENCIA);

		Collection<ParcelamentoConfiguracaoPrestacao> colecaoParcelamentoConfiguracaoPrestacao = fachada.pesquisar(
						filtroParcelamentoConfiguracaoPrestacao, ParcelamentoConfiguracaoPrestacao.class.getName());

		if(!Util.isVazioOrNulo(colecaoParcelamentoConfiguracaoPrestacao)){

			sessao.setAttribute("colecaoParcelamentoConfiguracaoPrestacao", colecaoParcelamentoConfiguracaoPrestacao);
		}

		FiltroGuiaPagamento filtroGuiaPagamento = new FiltroGuiaPagamento();
		filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.PARCELAMENTO_ID, Integer
						.valueOf(codigoParcelamento)));

		Collection collectionGuiaPagamento = fachada.pesquisar(filtroGuiaPagamento, GuiaPagamento.class.getName());

		if(collectionGuiaPagamento != null && !collectionGuiaPagamento.isEmpty()){
			sessao.setAttribute("btImprimirGuiaPagamentoEntrada", 1);
		}else{
			sessao.removeAttribute("btImprimirGuiaPagamentoEntrada");
		}

		sessao.setAttribute("idsContaEP", idsContaEP);



		return retorno;
	}

}
