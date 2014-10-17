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
 * GSANPCG
 * Vitor Hora
 * Saulo Lima
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

package gcom.relatorio.cobranca.parcelamento;

import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.batch.Relatorio;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.cobranca.FiltroIndicesAcrescimosImpontualidade;
import gcom.cobranca.IndicesAcrescimosImpontualidade;
import gcom.cobranca.bean.ParcelamentoRelatorioHelper;
import gcom.cobranca.parcelamento.FiltroParcelamento;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.cobranca.parcelamento.ParcelamentoItem;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * classe respons�vel por criar o relat�rio de bairro manter de �gua
 * 
 * @author S�vio Luiz
 * @created 11 de Julho de 2005
 * @author Saulo Lima
 * @date 24/07/2009
 *       Novos campos para exibir numera��o das Guias
 */
public class RelatorioParcelamento
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioParcelamento(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_PARCELAMENTO);
	}

	@Deprecated
	public RelatorioParcelamento() {

		super(null, "");
	}

	/**
	 * <<Descri��o do m�todo>>
	 * 
	 * @author Saulo Lima
	 * @date 03/02/2009
	 *       Corre��es para exibir no relat�rio:
	 *       1. A taxa de juros (em vez do valor);
	 *       2. Colocar "<nome> respons�vel pelo parcelamento do im�vel" (em vez de verificar o tipo
	 *       no .jrxml)
	 * @param bairros
	 * @param bairroParametros
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 */
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Integer idParcelamento = (Integer) getParametro("idParcelamento");
		SistemaParametro sistemaParametro = (SistemaParametro) getParametro("sistemaParametro");
		UnidadeOrganizacional unidadeUsuario = (UnidadeOrganizacional) getParametro("unidadeUsuario");
		Usuario usuario = (Usuario) getParametro("usuario");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		String descricaoUnidadeUsuario = "";
		if(unidadeUsuario != null && unidadeUsuario.getDescricao() != null){
			descricaoUnidadeUsuario = unidadeUsuario.getDescricao();
		}

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioParcelamentosBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioParcelamentoBean relatorioParcelamentoBean = null;

		ParcelamentoRelatorioHelper parcelamentoRelatorioHelper = fachada.pesquisarParcelamentoRelatorio(idParcelamento);

		String idFuncionario = "";
		if(parcelamentoRelatorioHelper.getIdFuncionario() != null){
			idFuncionario = parcelamentoRelatorioHelper.getIdFuncionario().toString();
		}else if(usuario.getFuncionario() != null){
			idFuncionario = usuario.getFuncionario().getId().toString();
		}

		// Descricao Localidade
		String descricaoLocalidade = "";
		if(parcelamentoRelatorioHelper.getDescricaoLocalidade() != null){
			descricaoLocalidade = parcelamentoRelatorioHelper.getDescricaoLocalidade();
		}

		// Descricao colecao ano mes ref
		String colecaoAnoMesReferencia = "";
		if(parcelamentoRelatorioHelper.getColecaoAnoMesReferencia() != null){
			colecaoAnoMesReferencia = parcelamentoRelatorioHelper.getColecaoAnoMesReferencia();
		}

		// Descricao colecao ano mes ref sobra
		String colecaoAnoMesReferenciaSobra = "";
		if(parcelamentoRelatorioHelper.getColecaoAnoMesReferenciaSobra() != null){
			colecaoAnoMesReferenciaSobra = parcelamentoRelatorioHelper.getColecaoAnoMesReferenciaSobra();
		}

		// DetalhamentoGuiasPrestacoes
		String detalhamentoGuiasPrestacoes = "";
		if(parcelamentoRelatorioHelper.getDetalhamentoGuiasPrestacoes() != null){
			detalhamentoGuiasPrestacoes = parcelamentoRelatorioHelper.getDetalhamentoGuiasPrestacoes();
		}

		// DetalhamentoGuiasPrestacoesSobra
		String detalhamentoGuiasPrestacoesSobra = "";
		if(parcelamentoRelatorioHelper.getDetalhamentoGuiasPrestacoesSobra() != null){
			detalhamentoGuiasPrestacoesSobra = parcelamentoRelatorioHelper.getDetalhamentoGuiasPrestacoesSobra();
		}

		String taxaMulta = "0";

		FiltroParcelamento filtroParcelamento = new FiltroParcelamento();
		filtroParcelamento.adicionarParametro(new ParametroSimples(FiltroParcelamento.ID, new Integer(idParcelamento)));
		Collection<Parcelamento> colecaoParcelamento = fachada.pesquisar(filtroParcelamento, Parcelamento.class.getName());
		Parcelamento parcelamento = null;
		if(colecaoParcelamento != null && !colecaoParcelamento.isEmpty()){
			parcelamento = colecaoParcelamento.iterator().next();
		}

		if(parcelamento != null){
			FiltroIndicesAcrescimosImpontualidade filtroIndicesAcrescimosImpontualidade = new FiltroIndicesAcrescimosImpontualidade();
			filtroIndicesAcrescimosImpontualidade.adicionarParametro(new ParametroSimples(
							FiltroIndicesAcrescimosImpontualidade.ANO_MES_REFERENCIA, parcelamento.getAnoMesReferenciaFaturamento()));
			Collection<IndicesAcrescimosImpontualidade> colecaoIndicesAcrescimosImpontualidade = fachada.pesquisar(
							filtroIndicesAcrescimosImpontualidade, IndicesAcrescimosImpontualidade.class.getName());
			IndicesAcrescimosImpontualidade indicesAcrescimosImpontualidade = null;
			if(colecaoIndicesAcrescimosImpontualidade != null && !colecaoIndicesAcrescimosImpontualidade.isEmpty()){
				indicesAcrescimosImpontualidade = colecaoIndicesAcrescimosImpontualidade.iterator().next();
			}
			if(indicesAcrescimosImpontualidade != null){
				if(indicesAcrescimosImpontualidade.getPercentualMulta() != null){
					taxaMulta = indicesAcrescimosImpontualidade.getPercentualMulta().toString();
				}
			}
		}

		// se a cole��o de par�metros da analise n�o for vazia
		if(parcelamentoRelatorioHelper != null){

			// Pega a Data Atual formatada da seguinte forma: dd de m�s(por extenso) de aaaa
			// Ex: 23 de maio de 1985
			DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
			String dataCorrente = df.format(new Date());

			String idImovel = parcelamentoRelatorioHelper.getIdImovel().toString();
			String idCliente = parcelamentoRelatorioHelper.getIdCliente().toString();

			FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, idImovel));
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_ID, idCliente));

			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_RELACAO_TIPO);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.IMOVEL);

			Collection colecaoClienteImovel = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

			String tipoCliente = "0";

			for(Iterator iterator = colecaoClienteImovel.iterator(); iterator.hasNext();){
				ClienteImovel clienteImovel = (ClienteImovel) iterator.next();
				if(clienteImovel.getClienteRelacaoTipo() != null
								&& !clienteImovel.getClienteRelacaoTipo().getId().toString().trim().equals("")){
					tipoCliente = clienteImovel.getClienteRelacaoTipo().getId().toString();
				}

			}

			String nomeCliente = "";

			if(parcelamentoRelatorioHelper.getNomeClienteParcelamento() != null
							&& !parcelamentoRelatorioHelper.getNomeClienteParcelamento().equals("")){

				nomeCliente = parcelamentoRelatorioHelper.getNomeClienteParcelamento();
			}else{
				nomeCliente = parcelamentoRelatorioHelper.getNomeCliente();
			}

			String cpfCnpjCliente = "";
			if(parcelamentoRelatorioHelper.getCpfClienteParcelamento() != null
							&& !parcelamentoRelatorioHelper.getCpfClienteParcelamento().equals("")){

				cpfCnpjCliente = parcelamentoRelatorioHelper.getCpfClienteParcelamento();
			}else{
				cpfCnpjCliente = parcelamentoRelatorioHelper.getCpfCnpj();
			}

			String rgCliente = "";
			if(parcelamentoRelatorioHelper.getRgClienteParcelamento() == null
							|| parcelamentoRelatorioHelper.getRgClienteParcelamento().equals("")){
				rgCliente = parcelamentoRelatorioHelper.getRgCliente();
			}else{
				rgCliente = parcelamentoRelatorioHelper.getRgClienteParcelamento();
			}

			Collection colecaoRelatorioParcelamentoDetalhamentosBeans = new ArrayList();

			Collection colecaoRelatorioParcelamentoItens = fachada.pesquisarParcelamentoItemPorIdParcelamentoRelatorio(idParcelamento);

			if(colecaoRelatorioParcelamentoItens != null && !colecaoRelatorioParcelamentoItens.isEmpty()){
				Iterator colecaoRelatorioParcelamentoItensIterator = colecaoRelatorioParcelamentoItens.iterator();

				RelatorioParcelamentoDetalhamentoBean relatorioParcelamentoDetalhamentoBean = null;

				BigDecimal totalFaturas = new BigDecimal("0.00");

				BigDecimal totalServicos = new BigDecimal("0.00");

				BigDecimal totalGuias = new BigDecimal("0.00");

				BigDecimal totalCreditos = new BigDecimal("0.00");

				ParcelamentoItem parcelamentoItem = null;
				ParcelamentoItem parcelamentoItem2 = null;

				Object tipoAnterior = null;
				Object tipoAtual = null;

				while(colecaoRelatorioParcelamentoItensIterator.hasNext()){

					if(tipoAnterior == null){
						tipoAnterior = new Conta();
					}else{
						if(parcelamentoItem.getContaGeral().getConta().getReferencia() != 0){
							tipoAnterior = parcelamentoItem.getContaGeral().getConta();
						}else if(parcelamentoItem.getDebitoACobrarGeral().getDebitoACobrar().getDebitoTipo().getId() != null){
							tipoAnterior = parcelamentoItem.getDebitoACobrarGeral().getDebitoACobrar();
						}else if(parcelamentoItem.getGuiaPagamentoGeral().getGuiaPagamento().getId() != null){
							tipoAnterior = parcelamentoItem.getGuiaPagamentoGeral().getGuiaPagamento();
						}else{
							tipoAnterior = parcelamentoItem.getCreditoARealizarGeral().getCreditoARealizar();
						}
					}

					if(parcelamentoItem2 != null && parcelamentoItem.getContaGeral().getConta().getReferencia() != 0){
						parcelamentoItem = parcelamentoItem2;
						parcelamentoItem2 = null;
					}else{

						parcelamentoItem = (ParcelamentoItem) colecaoRelatorioParcelamentoItensIterator.next();
					}

					if(parcelamentoItem.getContaGeral().getConta().getReferencia() != 0){
						tipoAtual = parcelamentoItem.getContaGeral().getConta();
					}else if(parcelamentoItem.getDebitoACobrarGeral().getDebitoACobrar().getDebitoTipo().getId() != null){
						tipoAtual = parcelamentoItem.getDebitoACobrarGeral().getDebitoACobrar();
					}else if(parcelamentoItem.getGuiaPagamentoGeral().getGuiaPagamento().getId() != null){
						tipoAtual = parcelamentoItem.getGuiaPagamentoGeral().getGuiaPagamento();
					}else{
						tipoAtual = parcelamentoItem.getCreditoARealizarGeral().getCreditoARealizar().getCreditoTipo();
					}

					if(tipoAnterior instanceof Conta){
						if(!(tipoAtual instanceof Conta)){
							relatorioParcelamentoDetalhamentoBean = new RelatorioParcelamentoDetalhamentoBean(

							// Matr�cula do Im�vel
											idImovel,

											// Nome do Cliente
											nomeCliente,

											// Faturas em Aberto
											// Refer�ncia 1
											"TOTAL",

											// Valor Fatura 1
											"",

											// Refer�ncia 2
											"",

											// Valor Fatura 2
											Util.formatarMoedaReal(totalFaturas),

											// Servi�os a Cobrar
											// C�digo
											"",

											// Descri��o
											"",

											// Valor
											"",

											// Guias de Pagamento
											// N�mero
											"",

											// Descri��o
											"",

											// Valor
											"",

											// Cr�ditos a Realizar
											// C�digo
											"",

											// Descri��o
											"",

											// Valor
											""

							);

							// adiciona o bean a cole��o
							colecaoRelatorioParcelamentoDetalhamentosBeans.add(relatorioParcelamentoDetalhamentoBean);
						}
					}else if(tipoAnterior instanceof DebitoACobrar){
						if(!(tipoAtual instanceof DebitoACobrar)){
							relatorioParcelamentoDetalhamentoBean = new RelatorioParcelamentoDetalhamentoBean(

							// Matr�cula do Im�vel
											idImovel,

											// Nome do Cliente
											nomeCliente,

											// Faturas em Aberto
											// Refer�ncia 1
											"",

											// Valor Fatura 1
											"",

											// Refer�ncia 2
											"",

											// Valor Fatura 2
											"",

											// Servi�os a Cobrar
											// C�digo
											"TOTAL",

											// Descri��o
											"",

											// Valor
											"",

											// Guias de Pagamento
											// N�mero
											"",

											// Descri��o
											"",

											// Valor
											"",

											// Cr�ditos a Realizar
											// C�digo
											"",

											// Descri��o
											"",

											// Valor
											""

							);

							// adiciona o bean a cole��o
							colecaoRelatorioParcelamentoDetalhamentosBeans.add(relatorioParcelamentoDetalhamentoBean);
						}
					}else if(tipoAnterior instanceof GuiaPagamento){
						if(!(tipoAtual instanceof GuiaPagamento)){
							relatorioParcelamentoDetalhamentoBean = new RelatorioParcelamentoDetalhamentoBean(

							// Matr�cula do Im�vel
											idImovel,

											// Nome do Cliente
											nomeCliente,

											// Faturas em Aberto
											// Refer�ncia 1
											"",

											// Valor Fatura 1
											"",

											// Refer�ncia 2
											"",

											// Valor Fatura 2
											"",

											// Servi�os a Cobrar
											// C�digo
											"",

											// Descri��o
											"",

											// Valor
											"",

											// Guias de Pagamento
											// N�mero
											"TOTAL",

											// Descri��o
											"",

											// Valor
											Util.formatarMoedaReal(totalGuias),

											// Cr�ditos a Realizar
											// C�digo
											"",

											// Descri��o
											"",

											// Valor
											""

							);

							// adiciona o bean a cole��o
							colecaoRelatorioParcelamentoDetalhamentosBeans.add(relatorioParcelamentoDetalhamentoBean);
						}
					}

					if(parcelamentoItem.getContaGeral().getConta().getReferencia() != 0){

						Conta conta = parcelamentoItem.getContaGeral().getConta();

						totalFaturas = totalFaturas.add(conta.getValorTotal());

						if(colecaoRelatorioParcelamentoItensIterator.hasNext()){

							parcelamentoItem2 = (ParcelamentoItem) colecaoRelatorioParcelamentoItensIterator.next();

							if(parcelamentoItem2.getContaGeral().getConta().getReferencia() != 0){

								Conta conta2 = parcelamentoItem2.getContaGeral().getConta();

								parcelamentoItem2 = null;

								totalFaturas = totalFaturas.add(conta2.getValorTotal());

								relatorioParcelamentoDetalhamentoBean = new RelatorioParcelamentoDetalhamentoBean(

								// Matr�cula do Im�vel
												idImovel,

												// Nome do Cliente
												nomeCliente,

												// Faturas em Aberto
												// Refer�ncia 1
												conta.getFormatarAnoMesParaMesAno(),

												// Valor Fatura 1
												Util.formatarMoedaReal(conta.getValorTotal()),

												// Refer�ncia 2
												conta2.getFormatarAnoMesParaMesAno(),

												// Valor Fatura 2
												Util.formatarMoedaReal(conta2.getValorTotal()),

												// Servi�os a Cobrar
												// C�digo
												"",

												// Descri��o
												"",

												// Valor
												"",

												// Guias de Pagamento
												// N�mero
												"",

												// Descri��o
												"",

												// Valor
												"",

												// Cr�ditos a Realizar
												// C�digo
												"",

												// Descri��o
												"",

												// Valor
												""

								);

								// adiciona o bean a cole��o
								colecaoRelatorioParcelamentoDetalhamentosBeans.add(relatorioParcelamentoDetalhamentoBean);

							}else{
								relatorioParcelamentoDetalhamentoBean = new RelatorioParcelamentoDetalhamentoBean(

								// Matr�cula do Im�vel
												idImovel,

												// Nome do Cliente
												nomeCliente,

												// Faturas em Aberto
												// Refer�ncia 1
												conta.getFormatarAnoMesParaMesAno(),

												// Valor Fatura 1
												Util.formatarMoedaReal(conta.getValorTotal()),

												// Refer�ncia 2
												"",

												// Valor Fatura 2
												"",

												// Servi�os a Cobrar
												// C�digo
												"",

												// Descri��o
												"",

												// Valor
												"",

												// Guias de Pagamento
												// N�mero
												"",

												// Descri��o
												"",

												// Valor
												"",

												// Cr�ditos a Realizar
												// C�digo
												"",

												// Descri��o
												"",

												// Valor
												""

								);

								// adiciona o bean a cole��o
								colecaoRelatorioParcelamentoDetalhamentosBeans.add(relatorioParcelamentoDetalhamentoBean);

							}
						}else{
							relatorioParcelamentoDetalhamentoBean = new RelatorioParcelamentoDetalhamentoBean(

							// Matr�cula do Im�vel
											idImovel,

											// Nome do Cliente
											nomeCliente,

											// Faturas em Aberto
											// Refer�ncia 1
											conta.getFormatarAnoMesParaMesAno(),

											// Valor Fatura 1
											Util.formatarMoedaReal(conta.getValorTotal()),

											// Refer�ncia 2
											"",

											// Valor Fatura 2
											"",

											// Servi�os a Cobrar
											// C�digo
											"",

											// Descri��o
											"",

											// Valor
											"",

											// Guias de Pagamento
											// N�mero
											"",

											// Descri��o
											"",

											// Valor
											"",

											// Cr�ditos a Realizar
											// C�digo
											"",

											// Descri��o
											"",

											// Valor
											""

							);

							// adiciona o bean a cole��o
							colecaoRelatorioParcelamentoDetalhamentosBeans.add(relatorioParcelamentoDetalhamentoBean);
						}

					}else if(parcelamentoItem.getDebitoACobrarGeral().getDebitoACobrar().getDebitoTipo().getId() != null){
						DebitoACobrar debitoACobrar = (DebitoACobrar) parcelamentoItem.getDebitoACobrarGeral().getDebitoACobrar();
						totalServicos = totalServicos.add(debitoACobrar.getValorTotal());

						relatorioParcelamentoDetalhamentoBean = new RelatorioParcelamentoDetalhamentoBean(

						// Matr�cula do Im�vel
										idImovel,

										// Nome do Cliente
										nomeCliente,

										// Faturas em Aberto
										// Refer�ncia 1
										"",

										// Valor Fatura 1
										"",

										// Refer�ncia 2
										"",

										// Valor Fatura 2
										"",

										// Servi�os a Cobrar
										// C�digo
										debitoACobrar.getDebitoTipo().getId().toString(),

										// Descri��o
										debitoACobrar.getDebitoTipo().getDescricao(),

										// Valor
										Util.formatarMoedaReal(debitoACobrar.getValorTotal()),

										// Guias de Pagamento
										// N�mero
										"",

										// Descri��o
										"",

										// Valor
										"",

										// Cr�ditos a Realizar
										// C�digo
										"",

										// Descri��o
										"",

										// Valor
										""

						);

						// adiciona o bean a cole��o
						colecaoRelatorioParcelamentoDetalhamentosBeans.add(relatorioParcelamentoDetalhamentoBean);

					}else if(parcelamentoItem.getGuiaPagamentoGeral().getGuiaPagamento().getId() != null){
						GuiaPagamento guiaPagamento = (GuiaPagamento) parcelamentoItem.getGuiaPagamentoGeral().getGuiaPagamento();

						totalGuias = totalGuias.add(guiaPagamento.getValorDebito());

						relatorioParcelamentoDetalhamentoBean = new RelatorioParcelamentoDetalhamentoBean(

						// Matr�cula do Im�vel
										idImovel,

										// Nome do Cliente
										nomeCliente,

										// Faturas em Aberto
										// Refer�ncia 1
										"",

										// Valor Fatura 1
										"",

										// Refer�ncia 2
										"",

										// Valor Fatura 2
										"",

										// Servi�os a Cobrar
										// C�digo
										"",

										// Descri��o
										"",

										// Valor
										"",

										// Guias de Pagamento
										// N�mero
										guiaPagamento.getId().toString(),

										// Descri��o
										"",// guiaPagamento.getDebitoTipo().getDescricao(),

										// Valor
										Util.formatarMoedaReal(guiaPagamento.getValorDebito()),

										// Cr�ditos a Realizar
										// C�digo
										"",

										// Descri��o
										"",

										// Valor
										""

						);

						// adiciona o bean a cole��o
						colecaoRelatorioParcelamentoDetalhamentosBeans.add(relatorioParcelamentoDetalhamentoBean);
					}else if(parcelamentoItem.getCreditoARealizarGeral().getCreditoARealizar().getCreditoTipo().getId() != null){
						CreditoARealizar creditoARealizar = (CreditoARealizar) parcelamentoItem.getCreditoARealizarGeral()
										.getCreditoARealizar();

						totalCreditos = totalCreditos.add(creditoARealizar.getValorTotal());

						relatorioParcelamentoDetalhamentoBean = new RelatorioParcelamentoDetalhamentoBean(

						// Matr�cula do Im�vel
										idImovel,

										// Nome do Cliente
										nomeCliente,

										// Faturas em Aberto
										// Refer�ncia 1
										"",

										// Valor Fatura 1
										"",

										// Refer�ncia 2
										"",

										// Valor Fatura 2
										"",

										// Servi�os a Cobrar
										// C�digo
										"",

										// Descri��o
										"",

										// Valor
										"",

										// Guias de Pagamento
										// N�mero
										"",

										// Descri��o
										"",

										// Valor
										"",

										// Cr�ditos a Realizar
										// C�digo
										creditoARealizar.getCreditoTipo().getId().toString(),

										// Descri��o
										creditoARealizar.getCreditoTipo().getDescricao(),

										// Valor
										Util.formatarMoedaReal(creditoARealizar.getValorTotal())

						);

						// adiciona o bean a cole��o
						colecaoRelatorioParcelamentoDetalhamentosBeans.add(relatorioParcelamentoDetalhamentoBean);

					}

				}

				if(tipoAtual instanceof Conta){
					relatorioParcelamentoDetalhamentoBean = new RelatorioParcelamentoDetalhamentoBean(

					// Matr�cula do Im�vel
									idImovel,

									// Nome do Cliente
									nomeCliente,

									// Faturas em Aberto
									// Refer�ncia 1
									"TOTAL",

									// Valor Fatura 1
									"",

									// Refer�ncia 2
									"",

									// Valor Fatura 2
									Util.formatarMoedaReal(totalFaturas),

									// Servi�os a Cobrar
									// C�digo
									"",

									// Descri��o
									"",

									// Valor
									"",

									// Guias de Pagamento
									// N�mero
									"",

									// Descri��o
									"",

									// Valor
									"",

									// Cr�ditos a Realizar
									// C�digo
									"",

									// Descri��o
									"",

									// Valor
									""

					);

					// adiciona o bean a cole��o
					colecaoRelatorioParcelamentoDetalhamentosBeans.add(relatorioParcelamentoDetalhamentoBean);
				}else if(tipoAtual instanceof DebitoACobrar){
					relatorioParcelamentoDetalhamentoBean = new RelatorioParcelamentoDetalhamentoBean(

					// Matr�cula do Im�vel
									idImovel,

									// Nome do Cliente
									nomeCliente,

									// Faturas em Aberto
									// Refer�ncia 1
									"",

									// Valor Fatura 1
									"",

									// Refer�ncia 2
									"",

									// Valor Fatura 2
									"",

									// Servi�os a Cobrar
									// C�digo
									"TOTAL",

									// Descri��o
									"",

									// Valor
									Util.formatarMoedaReal(totalServicos),

									// Guias de Pagamento
									// N�mero
									"",

									// Descri��o
									"",

									// Valor
									"",

									// Cr�ditos a Realizar
									// C�digo
									"",

									// Descri��o
									"",

									// Valor
									""

					);

					// adiciona o bean a cole��o
					colecaoRelatorioParcelamentoDetalhamentosBeans.add(relatorioParcelamentoDetalhamentoBean);
				}else if(tipoAtual instanceof GuiaPagamento){
					relatorioParcelamentoDetalhamentoBean = new RelatorioParcelamentoDetalhamentoBean(

					// Matr�cula do Im�vel
									idImovel,

									// Nome do Cliente
									nomeCliente,

									// Faturas em Aberto
									// Refer�ncia 1
									"",

									// Valor Fatura 1
									"",

									// Refer�ncia 2
									"",

									// Valor Fatura 2
									"",

									// Servi�os a Cobrar
									// C�digo
									"",

									// Descri��o
									"",

									// Valor
									"",

									// Guias de Pagamento
									// N�mero
									"TOTAL",

									// Descri��o
									"",

									// Valor
									Util.formatarMoedaReal(totalGuias),

									// Cr�ditos a Realizar
									// C�digo
									"",

									// Descri��o
									"",

									// Valor
									""

					);

					// adiciona o bean a cole��o
					colecaoRelatorioParcelamentoDetalhamentosBeans.add(relatorioParcelamentoDetalhamentoBean);
				}else if(tipoAtual instanceof CreditoARealizar){

					relatorioParcelamentoDetalhamentoBean = new RelatorioParcelamentoDetalhamentoBean(

					// Matr�cula do Im�vel
									idImovel,

									// Nome do Cliente
									nomeCliente,

									// Faturas em Aberto
									// Refer�ncia 1
									"",

									// Valor Fatura 1
									"",

									// Refer�ncia 2
									"",

									// Valor Fatura 2
									"",

									// Servi�os a Cobrar
									// C�digo
									"",

									// Descri��o
									"",

									// Valor
									"",

									// Guias de Pagamento
									// N�mero
									"",

									// Descri��o
									"",

									// Valor
									"",

									// Cr�ditos a Realizar
									// C�digo
									"TOTAL",

									// Descri��o
									"",

									// Valor
									Util.formatarMoedaReal(totalCreditos)

					);

					// adiciona o bean a cole��o
					colecaoRelatorioParcelamentoDetalhamentosBeans.add(relatorioParcelamentoDetalhamentoBean);
				}

			}

			// Cria o Bean da Primeira P�gina setando o valor de P�gina para
			// 1 para ser comparado no relat�rio e s� imprimir os dados da
			// Primeira P�gina

			relatorioParcelamentoBean = new RelatorioParcelamentoBean(

			// Matr�cula do Im�vel
							parcelamentoRelatorioHelper.getIdImovel().toString(),

							// Nome Cliente
							nomeCliente,

							// Endere�o
							parcelamentoRelatorioHelper.getEndereco(),

							// CPF/CNPJ
							cpfCnpjCliente,

							// Telefone
							parcelamentoRelatorioHelper.getTelefone(),

							// Fax
							sistemaParametro.getNumeroFax(),

							// Data Parcelamento
							Util.formatarData(parcelamentoRelatorioHelper.getDataParcelamento()),

							// D�bitos
							// Faturas em Aberto
							Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorFaturasEmAberto()),

							// Servi�os a Cobrar
							Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorServicosACobrar()),

							// Atualiza��o Monet�ria
							Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorAtualizacaoMonetaria()),

							// Taxa Juros
							parcelamentoRelatorioHelper.getTaxaJuros(),

							// Taxa Juros por Extenso
							// Util.numero(Long.parseLong(parcelamentoRelatorioHelper.getTaxaJuros())),
							Util.numero(new Double(parcelamentoRelatorioHelper.getTaxaJuros().replace(",", ".")).longValue()),

							// Taxa Multa
							taxaMulta,

							// Taxa Multa por Extenso
							Util.numero(new Double(taxaMulta.replace(",", ".")).longValue()),

							// Guia Pagamento
							Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorGuiaPagamento()),

							// Parcelamento a Cobrar
							Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorParcelamentoACobrar()),

							// Total D�bitos
							Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorTotalDebitos()),

							// Total D�bitos Extenso
							Util.valorExtenso(parcelamentoRelatorioHelper.getValorTotalDebitos()),

							// Descontos
							// Desconto de Acr�scimos
							Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorDescontoAcrescimo()),

							// Descontos de Antiguidade
							Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorDescontoAntiguidade()),

							// Desconto de Inatividade
							Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorDescontoInatividade()),

							// Cr�ditos a Realizar
							Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorCreditosRealizar()),

							// Total Descontos
							Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorTotalDescontos()),

							// Valor a Ser Negociado
							Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorASerNegociado()),

							// Condi��es de Negocia��o
							// Valor da Entrada
							Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorEntrada()),

							Util.valorExtenso(parcelamentoRelatorioHelper.getValorEntrada()),

							// N�mero de Parcelas
							parcelamentoRelatorioHelper.getNumeroParcelas().toString(),

							// N�mero de Parcelas Extenso
							Util.numero(new Long(parcelamentoRelatorioHelper.getNumeroParcelas().toString())),

							// Valor da Parcela
							Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorParcela()),

							// Valor a Ser Parcelado
							Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorASerParcelado()),

							// Solicita��o de Restabelecimento
							parcelamentoRelatorioHelper.getSolicitacaoRestabelecimento(),

							// Munic�pio e Data
							"",

							// Id do Parcelamento
							"",

							// Unidade do Usu�rio
							"",

							// Id do Funcion�rio
							"",

							// Descricao Localidade
							descricaoLocalidade,

							// colecao ano mes ref
							colecaoAnoMesReferencia,

							// Colecao ano mes ref sobra
							colecaoAnoMesReferenciaSobra,

							detalhamentoGuiasPrestacoes, detalhamentoGuiasPrestacoesSobra,

							// Nome do Cliente do Parcelamento
							"",

							// Cpf do Cliente do Parcelamento
							"",

							// P�gina
							"1",

							// Cole��o de Detalhamentos
							colecaoRelatorioParcelamentoDetalhamentosBeans,

							// codigo da empresa
							sistemaParametro.getCodigoEmpresaFebraban().toString(),

							// rgCliente
							"",

							// nome usuario
							"",

							// matricula usuario
							"",

							// municipio
							"",

							// imovel dia vencimento
							Short.toString(parcelamentoRelatorioHelper.getImovelDiaVencimento() == null ? new Short("0")
											: parcelamentoRelatorioHelper.getImovelDiaVencimento()),

							// inicio parcelamento
							"",
							// final parcelamento
							"", tipoCliente,

							// Valor Juros/Mora
							Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorJurosMora()),

							// Valor Juros/Mora Extenso
							Util.numero(parcelamentoRelatorioHelper.getValorJurosMora().longValue()),

							// Desconto de San��es Regulamentares
							Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorDescontoSancoesRegulamentares()),

							// Desconto Tarifa Social
							Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorDescontoTarifaSocial()),

							// Data Vencimento Entrada Parcelamento
							parcelamentoRelatorioHelper.getDataEntradaParcelamento() == null ? Util.formatarData(new Date()) : Util
											.formatarData(parcelamentoRelatorioHelper.getDataEntradaParcelamento()),

							// RG cliente
							rgCliente);

			// adiciona o bean a cole��o
			relatorioParcelamentosBeans.add(relatorioParcelamentoBean);
			/*
			 * Comentado por estar adicionando duas vezes o objeto 'relatorioParcelamentoBean' �
			 * cole��o.
			 * Saulo Lima 03/02/2009
			 * relatorioParcelamentosBeans.add(relatorioParcelamentoBean);
			 */

			// Cria o Bean da Segunda P�gina setando o valor de P�gina para
			// 2 para ser comparado no relat�rio e s� imprimir os dados da
			// Segunda P�gina

			/*
			 * if(sistemaParametro.getCodigoEmpresaFebraban().equals(SistemaParametro.CODIGO_EMPRESA_FEBRABAN_CAERN
			 * )
			 * ||sistemaParametro.getCodigoEmpresaFebraban().equals(SistemaParametro.
			 * CODIGO_EMPRESA_FEBRABAN_CAER)){
			 * String rgCliente = "";
			 * if (parcelamentoRelatorioHelper.getRgClienteParcelamento()!= null
			 * && !parcelamentoRelatorioHelper.getRgClienteParcelamento().equals("")){
			 * rgCliente = parcelamentoRelatorioHelper.getRgClienteParcelamento();
			 * }else if(parcelamentoRelatorioHelper.getRgCliente() != null){
			 * if(parcelamentoRelatorioHelper.getDescOrgaoExpRgCliente() != null &&
			 * parcelamentoRelatorioHelper.getSiglaUnidadeFederacaoRgCliente() != null){
			 * rgCliente = parcelamentoRelatorioHelper.getRgCliente() + "-" +
			 * parcelamentoRelatorioHelper.getDescOrgaoExpRgCliente().trim() + "/" +
			 * parcelamentoRelatorioHelper.getSiglaUnidadeFederacaoRgCliente();
			 * }else{
			 * rgCliente = parcelamentoRelatorioHelper.getRgCliente();
			 * }
			 * }
			 * relatorioParcelamentoBean = new RelatorioParcelamentoBean(
			 * //Matr�cula do Im�vel
			 * parcelamentoRelatorioHelper.getIdImovel().toString(),
			 * // Nome Cliente
			 * nomeCliente,
			 * // Endere�o
			 * parcelamentoRelatorioHelper.getEndereco(),
			 * // CPF/CNPJ
			 * cpfCnpjCliente,
			 * // Telefone
			 * "",
			 * // fax
			 * "",
			 * // Data Parcelamento
			 * "",
			 * // D�bitos
			 * // Faturas em Aberto
			 * "",
			 * // Servi�os a Cobrar
			 * "",
			 * // Atualiza��o Monet�ria
			 * "",
			 * // Juros/Mora
			 * "",
			 * // Juros/Mora Extenso
			 * "",
			 * // Multa
			 * "",
			 * // Guia Pagamento
			 * "",
			 * // Parcelamento a Cobrar
			 * "",
			 * // Total D�bitos
			 * "",
			 * // Total D�bitos Extenso
			 * "",
			 * // Descontos
			 * // Desconto de Acr�scimos
			 * "",
			 * // Descontos de Antiguidade
			 * "",
			 * // Desconto de Inatividade
			 * "",
			 * // Cr�ditos a Realizar
			 * "",
			 * // Total Descontos
			 * "",
			 * // Valor a Ser Negociado
			 * Util.formatarMoedaReal(parcelamentoRelatorioHelper
			 * .getValorASerNegociado()),
			 * // Condi��es de Negocia��o
			 * // Valor da Entrada
			 * Util.formatarMoedaReal(parcelamentoRelatorioHelper.getValorEntrada()),
			 * //Valor entrada por extenso
			 * Util.valorExtenso(parcelamentoRelatorioHelper.getValorEntrada()),
			 * // N�mero de Parcelas
			 * parcelamentoRelatorioHelper.getNumeroParcelas().toString(),
			 * // N�mero de Parcelas Extenso
			 * Util.numero(new Long(parcelamentoRelatorioHelper.getNumeroParcelas().toString())),
			 * // Valor da Parcela
			 * Util.formatarMoedaReal(parcelamentoRelatorioHelper
			 * .getValorParcela()),
			 * // Valor a Ser Parcelado
			 * Util.formatarMoedaReal(parcelamentoRelatorioHelper
			 * .getValorASerParcelado()),
			 * // Solicita��o de Restabelecimento
			 * "",
			 * // Munic�pio e Data
			 * parcelamentoRelatorioHelper.getNomeMunicipio() + ", "
			 * + dataCorrente,
			 * // Id do Parcelamento
			 * idParcelamento.toString(),
			 * // Unidade do Usu�rio
			 * descricaoUnidadeUsuario,
			 * // Id do Funcion�rio
			 * idFuncionario,
			 * // Descricao Localidade
			 * descricaoLocalidade,
			 * //Colecao ano mes ref
			 * colecaoAnoMesReferencia,
			 * //Colecao ano mes ref sobra
			 * colecaoAnoMesReferenciaSobra,
			 * // Nome do Cliente do Parcelamento
			 * parcelamentoRelatorioHelper.getNomeClienteParcelamento(),
			 * // Cpf do Cliente do Parcelamento
			 * parcelamentoRelatorioHelper.getCpfClienteParcelamento(),
			 * // P�gina
			 * "2",
			 * // Cole��o de Detalhamentos
			 * colecaoRelatorioParcelamentoDetalhamentosBeans,
			 * //codigo da empresa
			 * sistemaParametro.getCodigoEmpresaFebraban().toString(),
			 * //Rg
			 * rgCliente,
			 * //nome usuario
			 * usuario.getNomeUsuario(),
			 * //matricula usuario
			 * usuario.getLogin(),
			 * parcelamentoRelatorioHelper.getNomeMunicipio(),
			 * parcelamentoRelatorioHelper.getMesAnoInicioParcelamento(),
			 * parcelamentoRelatorioHelper.getMesAnoFinalParcelamento(),
			 * "","","","");
			 * }else{
			 * relatorioParcelamentoBean = new RelatorioParcelamentoBean(
			 * // Matr�cula do Im�vel
			 * "",
			 * // Nome Cliente
			 * "",
			 * // Endere�o
			 * "",
			 * // CPF/CNPJ
			 * cpfCnpjCliente,
			 * // Telefone
			 * "",
			 * // Fax
			 * "",
			 * // Data Parcelamento
			 * "",
			 * // D�bitos
			 * // Faturas em Aberto
			 * "",
			 * // Servi�os a Cobrar
			 * "",
			 * // Atualiza��o Monet�ria
			 * "",
			 * // Juros/Mora
			 * "",
			 * // Juros/Mora Extenso
			 * "",
			 * // Multa
			 * "",
			 * // Guia Pagamento
			 * "",
			 * // Parcelamento a Cobrar
			 * "",
			 * // Total D�bitos
			 * "",
			 * // Total D�bitos Extenso
			 * "",
			 * // Descontos
			 * // Desconto de Acr�scimos
			 * "",
			 * // Descontos de Antiguidade
			 * "",
			 * // Desconto de Inatividade
			 * "",
			 * // Cr�ditos a Realizar
			 * "",
			 * // Total Descontos
			 * "",
			 * // Valor a Ser Negociado
			 * "",
			 * // Condi��es de Negocia��o
			 * // Valor da Entrada
			 * "",
			 * //Valor Entrada Extenso
			 * "",
			 * // N�mero de Parcelas
			 * parcelamentoRelatorioHelper.getNumeroParcelas().toString(),
			 * // N�mero de Parcelas Extenso
			 * Util.numero(new Long(parcelamentoRelatorioHelper.getNumeroParcelas().toString())),
			 * // Valor da Parcela
			 * "",
			 * // Valor a Ser Parcelado
			 * "",
			 * // Solicita��o de Restabelecimento
			 * "",
			 * // Munic�pio e Data
			 * parcelamentoRelatorioHelper.getNomeMunicipio() + ", "
			 * + dataCorrente,
			 * // Id do Parcelamento
			 * idParcelamento.toString(),
			 * // Unidade do Usu�rio
			 * descricaoUnidadeUsuario,
			 * // Id do Funcion�rio
			 * idFuncionario,
			 * // Descricao Localidade
			 * descricaoLocalidade,
			 * //Colecao ano mes ref
			 * colecaoAnoMesReferencia,
			 * //Colecao ano mes ref sobra
			 * colecaoAnoMesReferenciaSobra,
			 * // Nome do Cliente do Parcelamento
			 * parcelamentoRelatorioHelper.getNomeClienteParcelamento(),
			 * // Cpf do Cliente do Parcelamento
			 * parcelamentoRelatorioHelper.getCpfClienteParcelamento(),
			 * // P�gina
			 * "2",
			 * // Cole��o de Detalhamentos
			 * colecaoRelatorioParcelamentoDetalhamentosBeans,
			 * //codigo da empresa
			 * sistemaParametro.getCodigoEmpresaFebraban().toString(),
			 * //rgCliente
			 * "",
			 * //nome usuario
			 * "",
			 * //matricula usuario
			 * "",
			 * //municipio
			 * "",
			 * //inicio parcelamento
			 * "",
			 * //final parcelamento
			 * "",
			 * "","","","");
			 * }
			 * // adiciona o bean a cole��o
			 * //relatorioParcelamentosBeans.add(relatorioParcelamentoBean);
			 * // Cria o Bean da Terceira P�gina setando o valor de P�gina para
			 * // 3 para ser comparado no relat�rio e s� imprimir os dados da
			 * // Terceira P�gina ou seja a cole��o de detalhamento
			 * relatorioParcelamentoBean = new RelatorioParcelamentoBean(
			 * // Matr�cula do Im�vel
			 * parcelamentoRelatorioHelper.getIdImovel().toString(),
			 * // Nome Cliente
			 * nomeCliente,
			 * // Endere�o
			 * "",
			 * // CPF/CNPJ
			 * "",
			 * // Telefone
			 * "",
			 * // Fax
			 * "",
			 * // Data Parcelamento
			 * "",
			 * // D�bitos
			 * // Faturas em Aberto
			 * "",
			 * // Servi�os a Cobrar
			 * "",
			 * // Atualiza��o Monet�ria
			 * "",
			 * // Juros/Mora
			 * "",
			 * // Juros/Mora Extenso
			 * "",
			 * // Multa
			 * "",
			 * // Guia Pagamento
			 * "",
			 * // Parcelamento a Cobrar
			 * "",
			 * // Total D�bitos
			 * "",
			 * // Total D�bitos Extenso
			 * "",
			 * // Descontos
			 * // Desconto de Acr�scimos
			 * "",
			 * // Descontos de Antiguidade
			 * "",
			 * // Desconto de Inatividade
			 * "",
			 * // Cr�ditos a Realizar
			 * "",
			 * // Total Descontos
			 * "",
			 * // Valor a Ser Negociado
			 * "",
			 * // Condi��es de Negocia��o
			 * // Valor da Entrada
			 * "",
			 * //Valor Entrada Extenso
			 * "",
			 * // N�mero de Parcelas
			 * parcelamentoRelatorioHelper.getNumeroParcelas().toString(),
			 * // N�mero de Parcelas Extenso
			 * Util.numero(new Long(parcelamentoRelatorioHelper.getNumeroParcelas().toString())),
			 * // Valor da Parcela
			 * "",
			 * // Valor a Ser Parcelado
			 * "",
			 * // Solicita��o de Restabelecimento
			 * "",
			 * // Munic�pio e Data
			 * "",
			 * // Id do Parcelamento
			 * "",
			 * // Unidade do Usu�rio
			 * "",
			 * // Id do Funcion�rio
			 * "",
			 * // Descricao Localidade
			 * descricaoLocalidade,
			 * //Colecao ano mes ref
			 * colecaoAnoMesReferencia,
			 * //Colecao ano mes ref sobra
			 * colecaoAnoMesReferenciaSobra,
			 * // Nome do Cliente do Parcelamento
			 * "",
			 * // Cpf do Cliente do Parcelamento
			 * "",
			 * // P�gina
			 * "3",
			 * // Cole��o de Detalhamentos
			 * colecaoRelatorioParcelamentoDetalhamentosBeans,
			 * //codigo da empresa
			 * sistemaParametro.getCodigoEmpresaFebraban().toString(),
			 * //rgCliente
			 * "",
			 * //nome usuario
			 * "",
			 * //matricula usuario
			 * "",
			 * //municipio
			 * "",
			 * //inicio parcelamento
			 * "",
			 * //final parcelamento
			 * "",
			 * "","","","");
			 * // adiciona o bean a cole��o
			 * relatorioParcelamentosBeans.add(relatorioParcelamentoBean);
			 */
		}

		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("dia", Util.retornaDescricaoMes(Util.getDiaMes(new Date())));
		parametros.put("mesPortugues", Util.retornaDescricaoMes(Util.getMes(new Date())));
		parametros.put("ano", Util.retornaDescricaoMes(Util.getAno(new Date())));

		// Empresa
		if(sistemaParametro.getNomeEmpresa() != null){

			parametros.put("empresa", sistemaParametro.getNomeEmpresa());

		}else{
			parametros.put("empresa", "");
		}

		// Endereco Empresa
		String enderecoEmpresa = "";

		boolean virgula = false;

		// Adiciona o logradouro
		/*
		 * FiltroLogradouro filtroLogradouro = new FiltroLogradouro();
		 * filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID,
		 * sistemaParametro.getLogradouro().getId()));
		 * Collection logradouros = fachada.pesquisar(filtroLogradouro, Logradouro.class.getName());
		 * Logradouro logradouro = new Logradouro();
		 * if (logradouros != null && !logradouros.isEmpty()) {
		 * logradouro = (Logradouro) logradouros.iterator().next();
		 * if (sistemaParametro.getLogradouro() != null && !logradouro.getNome().equals("")) {
		 * enderecoEmpresa = sistemaParametro.getLogradouro().getNome();
		 * virgula = true;
		 * }
		 * }
		 */
		if(sistemaParametro.getLogradouro() != null){
			Logradouro logradouro = sistemaParametro.getLogradouro();
			if(logradouro.getNome() != null && !logradouro.getNome().equals("")){
				enderecoEmpresa = sistemaParametro.getLogradouro().getNome();
				virgula = true;
			}
		}

		// Adiciona o n�mero
		if(sistemaParametro.getNumeroImovel() != null){
			enderecoEmpresa += (virgula == true ? ", " : "") + sistemaParametro.getNumeroImovel();
			virgula = true;
		}

		// Adiciona o complemento
		if(sistemaParametro.getComplementoEndereco() != null){
			enderecoEmpresa += (virgula == true ? ", " : "") + sistemaParametro.getComplementoEndereco();
			virgula = true;
		}

		// Adiciona o bairro
		if(sistemaParametro.getBairro() != null){
			enderecoEmpresa += (virgula == true ? ", " : "") + sistemaParametro.getBairro().getNome();
			virgula = true;
		}

		// Adiciona o bairro
		if(sistemaParametro.getCep() != null){
			enderecoEmpresa += (virgula == true ? ", Cep: " : "") + sistemaParametro.getCep().getCepFormatado();
		}

		parametros.put("enderecoEmpresa", enderecoEmpresa);

		// CNPJ da Empresa
		if(sistemaParametro.getCnpjEmpresa() != null){

			String cnpjFormatado = sistemaParametro.getCnpjEmpresa().toString();
			String zeros = "";

			if(cnpjFormatado != null){

				for(int a = 0; a < (14 - cnpjFormatado.length()); a++){
					zeros = zeros.concat("0");
				}
				// concatena os zeros ao numero
				// caso o numero seja diferente de nulo
				cnpjFormatado = zeros.concat(cnpjFormatado);

				cnpjFormatado = cnpjFormatado.substring(0, 2) + "." + cnpjFormatado.substring(2, 5) + "." + cnpjFormatado.substring(5, 8)
								+ "/" + cnpjFormatado.substring(8, 12) + "-" + cnpjFormatado.substring(12, 14);
			}

			parametros.put("cnpj", cnpjFormatado);

		}else{
			parametros.put("cnpj", "");
		}

		// Inscri��o Estadual da Companhia de �gua
		if(sistemaParametro.getCodigoEmpresaFebraban() != null){

			parametros.put("inscricaoEstadual", "");

		}else{
			parametros.put("inscricaoEstadual", "");
		}

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioParcelamentosBeans);

		if(sistemaParametro.getCodigoEmpresaFebraban().equals(SistemaParametro.CODIGO_EMPRESA_FEBRABAN_CAER)){
			retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_PARCELAMENTO_CAER, parametros, ds, tipoFormatoRelatorio);
		}else{
			retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_PARCELAMENTO, parametros, ds, tipoFormatoRelatorio);
		}

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.PARCELAMENTO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 1;

		return retorno;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioParcelamento", this);
	}
}