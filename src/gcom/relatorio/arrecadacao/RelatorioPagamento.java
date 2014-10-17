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

package gcom.relatorio.arrecadacao;

import gcom.arrecadacao.aviso.bean.AvisoBancarioHelper;
import gcom.arrecadacao.bean.PagamentoRelatorioHelper;
import gcom.arrecadacao.pagamento.FiltroGuiaPagamentoPrestacao;
import gcom.arrecadacao.pagamento.FiltroGuiaPagamentoPrestacaoHistorico;
import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacao;
import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacaoHistorico;
import gcom.arrecadacao.pagamento.Pagamento;
import gcom.arrecadacao.pagamento.PagamentoHistorico;
import gcom.arrecadacao.pagamento.PagamentoSituacao;
import gcom.batch.Relatorio;
import gcom.cadastro.cliente.ClienteImovelSimplificado;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.DocumentoTipo;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.ContaGeral;
import gcom.faturamento.conta.FiltroContaGeral;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.arrecadacao.ParametroArrecadacao;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * classe responsável por criar o relatório de bairro manter de água
 * 
 * @author Sávio Luiz
 * @created 11 de Julho de 2005
 */
public class RelatorioPagamento
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da classe RelatorioAnaliseFisicoQuimicaAgua
	 */
	public RelatorioPagamento(Usuario usuario, String nomeRelatorio) {

		super(usuario, nomeRelatorio);
	}

	@Deprecated
	public RelatorioPagamento() {

		super(null, "");
	}

	/**
	 * <<Descrição do método>>
	 * 
	 * @author Saulo Lima
	 * @date 19/01/2009
	 *       Alteração para chamar duas consultas quando a Opção de Pagamento for "ambos"
	 * @param bairros
	 *            Description of the Parameter
	 * @param bairroParametros
	 *            Description of the Parameter
	 * @return Descrição do retorno
	 * @exception RelatorioVazioException
	 *                Descrição da exceção
	 */
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Fachada fachada = Fachada.getInstancia();

		// ColecaoRetorno pode ser assumir uma coleção de <Pagamento> ou <PagamentoRelatorioHelper>,
		// depende do caso.
		Collection colecaoRetorno = new ArrayList();

		boolean relatorioConsultarImovel = false;
		boolean relatorioConsultarAvisoBancario = false;
		if(getParametro("relatorioConsultarImovel") != null){
			relatorioConsultarImovel = true;
		}

		if(getParametro("relatorioConsultarAvisoBancario") != null){
			relatorioConsultarAvisoBancario = true;
		}

		Pagamento pagamentoParametrosInicial = (Pagamento) getParametro("pagamentoParametrosInicial");
		Pagamento pagamentoParametrosFinal = (Pagamento) getParametro("pagamentoParametrosFinal");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// Parâmetros de Pesquisa
		AvisoBancarioHelper avisoBancarioHelper = (AvisoBancarioHelper) getParametro("avisoBancarioHelper");
		String idImovel = (String) getParametro("idImovel");
		String idCliente = (String) getParametro("idCliente");
		String clienteRelacaoTipo = (String) getParametro("clienteRelacaoTipo");
		String idAvisoBancario = (String) getParametro("idAvisoBancario");
		String idMovimentoArrecadador = (String) getParametro("idMovimentoArrecadador");
		String localidadeInicial = (String) getParametro("localidadeInicial");
		String localidadeFinal = (String) getParametro("localidadeFinal");
		String periodoArrecadacaoInicial = (String) getParametro("periodoArrecadacaoInicial");
		String periodoArrecadacaoFinal = (String) getParametro("periodoArrecadacaoFinal");
		String periodoPagamentoInicial = (String) getParametro("periodoPagamentoInicial");
		String periodoPagamentoFinal = (String) getParametro("periodoPagamentoFinal");
		Date dataPagamentoInicial = (Date) getParametro("dataPagamentoInicial");
		Date dataPagamentoFinal = (Date) getParametro("dataPagamentoFinal");
		String[] idsPagamentoSituacao = (String[]) getParametro("idsPagamentoSituacao");
		String[] idsArrecadacaoForma = (String[]) getParametro("idsArrecadacaoForma");
		String[] idsDocumentoTipo = (String[]) getParametro("idsDocumentoTipo");
		String[] idsDebitoTipo = (String[]) getParametro("idsDebitoTipo");
		String[] idsCategoria = (String[]) getParametro("idsCategoria");
		String opcaoPagamento = (String) getParametro("opcaoPagamento");
		String codigoSetorComercialInicial = (String) getParametro("codigoSetorComercialInicial");
		String codigoSetorComercialFinal = (String) getParametro("codigoSetorComercialFinal");
		String indicadorTotalizarPorDataPagamento = (String) getParametro("indicadorTotalizarPorDataPagamento");
		Collection<Integer> idsArrecadadores = (Collection<Integer>) getParametro("idsArrecadadores");

		if(relatorioConsultarAvisoBancario){

			colecaoRetorno = avisoBancarioHelper.getColecaoPagamentos();

		}else{

			if(idImovel != null && !idImovel.equals("")){

				if(opcaoPagamento != null && opcaoPagamento.equals("ambos")){

					colecaoRetorno = fachada.pesquisarPagamentoImovelAmbosRelatorio(idImovel);

				}else{

					// OK
					colecaoRetorno = fachada.pesquisarPagamentoImovelRelatorio(idImovel, idCliente, clienteRelacaoTipo, localidadeInicial,
									localidadeFinal, idAvisoBancario, idMovimentoArrecadador, periodoArrecadacaoInicial,
									periodoArrecadacaoFinal, periodoPagamentoInicial, periodoPagamentoFinal, dataPagamentoInicial,
									dataPagamentoFinal, idsPagamentoSituacao, idsDebitoTipo, idsArrecadacaoForma, idsDocumentoTipo,
									codigoSetorComercialInicial, codigoSetorComercialFinal, indicadorTotalizarPorDataPagamento,
									idsArrecadadores, idsCategoria);

				}
			}else if(idCliente != null && !idCliente.equals("")){

				// OK
				colecaoRetorno = fachada.pesquisarPagamentoClienteRelatorio(idImovel, idCliente, clienteRelacaoTipo, localidadeInicial,
								localidadeFinal, idAvisoBancario, idMovimentoArrecadador, periodoArrecadacaoInicial,
								periodoArrecadacaoFinal, periodoPagamentoInicial, periodoPagamentoFinal, dataPagamentoInicial,
								dataPagamentoFinal, idsPagamentoSituacao, idsDebitoTipo, idsArrecadacaoForma, idsDocumentoTipo,
								codigoSetorComercialInicial, codigoSetorComercialFinal, indicadorTotalizarPorDataPagamento,
								idsArrecadadores, idsCategoria);

			}else if(idAvisoBancario != null && !idAvisoBancario.equals("")){

				// OK
				colecaoRetorno = fachada.pesquisarPagamentoAvisoBancarioRelatorio(idImovel, idCliente, clienteRelacaoTipo,
								localidadeInicial, localidadeFinal, idAvisoBancario, idMovimentoArrecadador, periodoArrecadacaoInicial,
								periodoArrecadacaoFinal, periodoPagamentoInicial, periodoPagamentoFinal, dataPagamentoInicial,
								dataPagamentoFinal, idsPagamentoSituacao, idsDebitoTipo, idsArrecadacaoForma, idsDocumentoTipo,
								codigoSetorComercialInicial, codigoSetorComercialFinal, indicadorTotalizarPorDataPagamento,
								idsArrecadadores, idsCategoria);

			}else if(idMovimentoArrecadador != null && !idMovimentoArrecadador.equals("")){

				// OK
				colecaoRetorno = fachada.pesquisarPagamentoMovimentoArrecadadorRelatorio(idImovel, idCliente, clienteRelacaoTipo,
								localidadeInicial, localidadeFinal, idAvisoBancario, idMovimentoArrecadador, periodoArrecadacaoInicial,
								periodoArrecadacaoFinal, periodoPagamentoInicial, periodoPagamentoFinal, dataPagamentoInicial,
								dataPagamentoFinal, idsPagamentoSituacao, idsDebitoTipo, idsArrecadacaoForma, idsDocumentoTipo,
								codigoSetorComercialInicial, codigoSetorComercialFinal, indicadorTotalizarPorDataPagamento,
								idsArrecadadores, idsCategoria);

			}else{

				// verifica se é para pesquisar no atual, no historico ou em ambos
				if(opcaoPagamento != null && opcaoPagamento.equals("atual")){

					// OK
					colecaoRetorno = fachada.pesquisarPagamentoLocalidadeRelatorio(idImovel, idCliente, clienteRelacaoTipo,
									localidadeInicial, localidadeFinal, idAvisoBancario, idMovimentoArrecadador, periodoArrecadacaoInicial,
									periodoArrecadacaoFinal, periodoPagamentoInicial, periodoPagamentoFinal, dataPagamentoInicial,
									dataPagamentoFinal, idsPagamentoSituacao, idsDebitoTipo, idsArrecadacaoForma, idsDocumentoTipo,
									codigoSetorComercialInicial, codigoSetorComercialFinal, indicadorTotalizarPorDataPagamento,
									idsArrecadadores, idsCategoria);

				}else if(opcaoPagamento != null && opcaoPagamento.equals("historico")){

					// OK
					colecaoRetorno = fachada.pesquisarPagamentoHistoricoLocalidadeRelatorio(idImovel, idCliente, clienteRelacaoTipo,
									localidadeInicial, localidadeFinal, idAvisoBancario, idMovimentoArrecadador, periodoArrecadacaoInicial,
									periodoArrecadacaoFinal, periodoPagamentoInicial, periodoPagamentoFinal, dataPagamentoInicial,
									dataPagamentoFinal, idsPagamentoSituacao, idsDebitoTipo, idsArrecadacaoForma, idsDocumentoTipo,
									codigoSetorComercialInicial, codigoSetorComercialFinal, indicadorTotalizarPorDataPagamento,
									idsArrecadadores, idsCategoria);

				}else if(opcaoPagamento != null && opcaoPagamento.equals("ambos")){

					// OK
					colecaoRetorno = fachada.pesquisarPagamentoLocalidadeRelatorio(idImovel, idCliente, clienteRelacaoTipo,
									localidadeInicial, localidadeFinal, idAvisoBancario, idMovimentoArrecadador, periodoArrecadacaoInicial,
									periodoArrecadacaoFinal, periodoPagamentoInicial, periodoPagamentoFinal, dataPagamentoInicial,
									dataPagamentoFinal, idsPagamentoSituacao, idsDebitoTipo, idsArrecadacaoForma, idsDocumentoTipo,
									codigoSetorComercialInicial, codigoSetorComercialFinal, indicadorTotalizarPorDataPagamento,
									idsArrecadadores, idsCategoria);

					// OK
					Collection colecaoTempHistorico = fachada.pesquisarPagamentoHistoricoLocalidadeRelatorio(idImovel, idCliente,
									clienteRelacaoTipo, localidadeInicial, localidadeFinal, idAvisoBancario, idMovimentoArrecadador,
									periodoArrecadacaoInicial, periodoArrecadacaoFinal, periodoPagamentoInicial, periodoPagamentoFinal,
									dataPagamentoInicial, dataPagamentoFinal, idsPagamentoSituacao, idsDebitoTipo, idsArrecadacaoForma,
									idsDocumentoTipo, codigoSetorComercialInicial, codigoSetorComercialFinal,
									indicadorTotalizarPorDataPagamento, idsArrecadadores, idsCategoria);

					if(colecaoRetorno != null){
						if(colecaoTempHistorico != null){
							colecaoRetorno.addAll(colecaoTempHistorico);
						}
					}else{
						colecaoRetorno = colecaoTempHistorico;
					}

					// Removido por HQL não suportar UNION
					// colecaoRetorno = fachada.pesquisarPagamentoLocalidadeAmbosRelatorio(idImovel,
					// idCliente, clienteRelacaoTipo, localidadeInicial,
					// localidadeFinal, idAvisoBancario, idMovimentoArrecadador,
					// periodoArrecadacaoInicial, periodoArrecadacaoFinal,
					// periodoPagamentoInicial, periodoPagamentoFinal, dataPagamentoInicial,
					// dataPagamentoFinal, idsPagamentoSituacao,
					// idsDebitoTipo, idsArrecadacaoForma, idsDocumentoTipo);
				}
			}
		}

		Integer pReferenciaContaInt = null;
		String pReferenciaConta = "";

		try{
			pReferenciaConta = ParametroArrecadacao.P_REFERENCIA_CONTA_PARA_CALCULO_REMUNERACAO_RELATORIO_PAGAMENTOS.executar().toString();
			pReferenciaContaInt = Integer.valueOf(pReferenciaConta);
		}catch(ControladorException e){
			throw new TarefaException(e.getMensagem(), e);
		}

		// valor de retorno
		byte[] retorno = null;

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		RelatorioPagamentoBean relatorioBean = null;

		BigDecimal valorRemuneracao = BigDecimal.ZERO;
		Collection<ContaGeral> colecaoContaGeral = null;
		ContaGeral contaGeral = null;
		FiltroContaGeral filtroContaGeral = null;
		Integer idConta = null;
		boolean exibirValorRemuneracao = true;
		short indicadorHistorico = -1;

		// Cria as variáveis que serão usadas futuramente no somatório de alguns
		// valores e para contar o número de devoluções de cada tipo
		int qtdePagamentosClassificadoContas = 0;
		BigDecimal valorPagamentosClassificadoContas = new BigDecimal("0.00");
		int qtdePagamentosDuploExcessoContas = 0;
		BigDecimal valorPagamentosDuploExcessoContas = new BigDecimal("0.00");
		int qtdePagamentosDocumentoInexistenteContas = 0;
		BigDecimal valorPagamentosDocumentoInexistenteContas = new BigDecimal("0.00");
		int qtdePagamentosBaixarValorExcedenteContas = 0;
		BigDecimal valorPagamentosBaixarValorExcedenteContas = new BigDecimal("0.00");
		int qtdePagamentosValorNaoConfereContas = 0;
		BigDecimal valorPagamentosValorNaoConfereContas = new BigDecimal("0.00");
		int qtdePagamentosDuploExcessoDevolvidoContas = 0;
		BigDecimal valorPagamentosDuploExcessoDevolvidoContas = new BigDecimal("0.00");
		int qtdePagamentosContaFaturamentoContas = 0;
		BigDecimal valorPagamentosContaFaturamentoContas = new BigDecimal("0.00");

		int qtdePagamentosClassificadoGuiasPagamento = 0;
		BigDecimal valorPagamentosClassificadoGuiasPagamento = new BigDecimal("0.00");
		int qtdePagamentosDuploExcessoGuiasPagamento = 0;
		BigDecimal valorPagamentosDuploExcessoGuiasPagamento = new BigDecimal("0.00");
		int qtdePagamentosDocumentoInexistenteGuiasPagamento = 0;
		BigDecimal valorPagamentosDocumentoInexistenteGuiasPagamento = new BigDecimal("0.00");
		int qtdePagamentosBaixarValorExcedenteGuiasPagamento = 0;
		BigDecimal valorPagamentosBaixarValorExcedenteGuiasPagamento = new BigDecimal("0.00");
		int qtdePagamentosValorNaoConfereGuiasPagamento = 0;
		BigDecimal valorPagamentosValorNaoConfereGuiasPagamento = new BigDecimal("0.00");
		int qtdePagamentosDuploExcessoDevolvidoGuiasPagamento = 0;
		BigDecimal valorPagamentosDuploExcessoDevolvidoGuiasPagamento = new BigDecimal("0.00");

		int qtdePagamentosClassificadoDebitosACobrar = 0;
		BigDecimal valorPagamentosClassificadoDebitosACobrar = new BigDecimal("0.00");
		int qtdePagamentosDuploExcessoDebitosACobrar = 0;
		BigDecimal valorPagamentosDuploExcessoDebitosACobrar = new BigDecimal("0.00");
		int qtdePagamentosDocumentoInexistenteDebitosACobrar = 0;
		BigDecimal valorPagamentosDocumentoInexistenteDebitosACobrar = new BigDecimal("0.00");
		int qtdePagamentosBaixarValorExcedenteDebitosACobrar = 0;
		BigDecimal valorPagamentosBaixarValorExcedenteDebitosACobrar = new BigDecimal("0.00");
		int qtdePagamentosValorNaoConfereDebitosACobrar = 0;
		BigDecimal valorPagamentosValorNaoConfereDebitosACobrar = new BigDecimal("0.00");
		int qtdePagamentosDuploExcessoDevolvidoDebitosACobrar = 0;
		BigDecimal valorPagamentosDuploExcessoDevolvidoDebitosACobrar = new BigDecimal("0.00");

		int qtdePagamentosClassificado = 0;
		BigDecimal valorPagamentosClassificado = new BigDecimal("0.00");
		int qtdePagamentosDuploExcesso = 0;
		BigDecimal valorPagamentosDuploExcesso = new BigDecimal("0.00");
		int qtdePagamentosDocumentoInexistente = 0;
		BigDecimal valorPagamentosDocumentoInexistente = new BigDecimal("0.00");
		int qtdePagamentosBaixarValorExcedente = 0;
		BigDecimal valorPagamentosBaixarValorExcedente = new BigDecimal("0.00");
		int qtdePagamentosValorNaoConfere = 0;
		BigDecimal valorPagamentosValorNaoConfere = new BigDecimal("0.00");
		int qtdePagamentosDuploExcessoDevolvido = 0;
		BigDecimal valorPagamentosDuploExcessoDevolvido = new BigDecimal("0.00");

		if(relatorioConsultarAvisoBancario){

			if(colecaoRetorno != null && !colecaoRetorno.isEmpty()){
				// coloca a coleção de parâmetros da analise no iterator
				Iterator<Pagamento> colecaoPagamentosIterator = colecaoRetorno.iterator();

				// laço para criar a coleção de parâmetros da analise
				while(colecaoPagamentosIterator.hasNext()){

					Pagamento pagamento = colecaoPagamentosIterator.next();

					String tipoDocumento = "";
					if(pagamento.getDocumentoTipo() != null && pagamento.getDocumentoTipo().getDescricaoAbreviado() != null){
						tipoDocumento = pagamento.getDocumentoTipo().getDescricaoAbreviado();
					}

					String dataPagamento = "";
					if(pagamento.getDataPagamento() != null){
						dataPagamento = Util.formatarData(pagamento.getDataPagamento());
					}

					String localidade = "";
					if(pagamento.getLocalidade() != null && pagamento.getLocalidade().getId() != null){
						localidade = pagamento.getLocalidade().getId().toString();
					}

					String matricula = "";
					if(pagamento.getImovel() != null && pagamento.getImovel().getId() != null){
						matricula = pagamento.getImovel().getId().toString();
					}

					String codigoCliente = "";
					if(pagamento.getCliente() != null && pagamento.getCliente().getId() != null){
						codigoCliente = pagamento.getCliente().getId().toString();
					}

					String referencia = "";
					if(pagamento.getAnoMesReferenciaPagamento() != null){
						referencia = pagamento.getFormatarAnoMesPagamentoParaMesAno();
					}

					String debito = "";
					if(pagamento.getDebitoTipo() != null && pagamento.getDebitoTipo().getId() != null){
						debito = pagamento.getDebitoTipo().getId().toString();
					}

					String valorPagamento = "";
					if(pagamento.getValorPagamento() != null){
						valorPagamento = Util.formatarMoedaReal(pagamento.getValorPagamento());
					}

					String situacaoAtual = "";
					if(pagamento.getPagamentoSituacaoAtual() != null
									&& pagamento.getPagamentoSituacaoAtual().getDescricaoAbreviada() != null){
						situacaoAtual = pagamento.getPagamentoSituacaoAtual().getDescricaoAbreviada();
					}

					String debitoACobrar = "";
					if(pagamento.getDebitoACobrar() != null && pagamento.getDebitoACobrar().getId() != null){
						debitoACobrar = String.valueOf(pagamento.getDebitoACobrar().getId());
					}

					String idGuia = "";
					if(pagamento.getGuiaPagamentoGeral() != null && pagamento.getGuiaPagamentoGeral().getId() != null){
						idGuia = String.valueOf(pagamento.getGuiaPagamentoGeral().getId());
					}

					String nnPrestacao = "";
					if(pagamento.getNumeroPrestacao() != null){
						nnPrestacao = String.valueOf(pagamento.getNumeroPrestacao().intValue());
					}

					String numeroPagamento = "";

					if(pagamento.getConta() != null){
						numeroPagamento = String.valueOf(pagamento.getConta().getId().intValue());
					}

					else if(pagamento.getDebitoACobrar() != null){
						numeroPagamento = String.valueOf(pagamento.getDebitoACobrar().getId().intValue());
					}

					else if(pagamento.getGuiaPagamentoGeral() != null){
						if(!relatorioConsultarAvisoBancario){
							numeroPagamento = String.valueOf(pagamento.getGuiaPagamentoGeral().getId().intValue());
						}else{
							// O numero da guia vem da consulta pesquisarPagamentoAvisoBancario da
							// [UC0255] Pesquisar Pagamentos Aviso Bancario. Uma vez que essa
							// consulta controi a lista de pagamentos dinamicamente o numero da guia
							// pagamento geral encontra-se no objeto guia de pagamento dentro do
							// objeto guia de pagamento geral
							if(pagamento.getGuiaPagamentoGeral().getGuiaPagamento() != null){
								numeroPagamento = String.valueOf(pagamento.getGuiaPagamentoGeral().getGuiaPagamento().getId().intValue());
							}
						}
					}

					if(nnPrestacao != null && nnPrestacao.length() > 0){
						nnPrestacao = "/ " + nnPrestacao;
					}

					relatorioBean = new RelatorioPagamentoBean(

					// Tipo de Documento
									tipoDocumento,

									// Data do Pagamento
									dataPagamento,

									// Localidade
									localidade,

									// Matrícula do Imóvel
									matricula,

									// Código do Cliente
									codigoCliente,

									// Referência do Pagamento
									referencia,

									// Débito
									debito,

									// Valor Pagamento
									valorPagamento,

									// Situação Atual
									situacaoAtual,

									// Indicador Histórico
									false,

									// Numero Prestacao
									nnPrestacao,

									// Numero Pagamento
									numeroPagamento);

					relatorioBean.setIdDebitoACobrar(debitoACobrar);
					relatorioBean.setIdGuiaPagamento(idGuia);

					relatorioBeans.add(relatorioBean);

				}

			}

			Collection colecaoPagamentosHistorico = fachada.pesquisarPagamentoHistoricoAvisoBancario(new Integer(idAvisoBancario));

			if(colecaoPagamentosHistorico != null && !colecaoPagamentosHistorico.isEmpty()){
				// coloca a coleção de parâmetros da analise no iterator
				Iterator colecaoPagamentosHistoricoIterator = colecaoPagamentosHistorico.iterator();

				// laço para criar a coleção de parâmetros da analise
				while(colecaoPagamentosHistoricoIterator.hasNext()){

					PagamentoHistorico pagamentoHistorico = (PagamentoHistorico) colecaoPagamentosHistoricoIterator.next();

					String tipoDocumento = "";
					if(pagamentoHistorico.getDocumentoTipo() != null
									&& pagamentoHistorico.getDocumentoTipo().getDescricaoAbreviado() != null){
						tipoDocumento = pagamentoHistorico.getDocumentoTipo().getDescricaoAbreviado();
					}

					String dataPagamento = "";
					if(pagamentoHistorico.getDataPagamento() != null){
						dataPagamento = Util.formatarData(pagamentoHistorico.getDataPagamento());
					}

					String localidade = "";
					if(pagamentoHistorico.getLocalidade() != null && pagamentoHistorico.getLocalidade().getId() != null){
						localidade = pagamentoHistorico.getLocalidade().getId().toString();
					}

					String matricula = "";
					if(pagamentoHistorico.getImovel() != null && pagamentoHistorico.getImovel().getId() != null){
						matricula = pagamentoHistorico.getImovel().getId().toString();
					}

					String codigoCliente = "";
					if(pagamentoHistorico.getCliente() != null && pagamentoHistorico.getCliente().getId() != null){
						codigoCliente = pagamentoHistorico.getCliente().getId().toString();
					}

					String referencia = "";
					if(pagamentoHistorico.getAnoMesReferenciaPagamento() != null){
						referencia = pagamentoHistorico.getFormatarAnoMesReferenciaPagamento();
					}

					String debito = "";
					if(pagamentoHistorico.getDebitoTipo() != null && pagamentoHistorico.getDebitoTipo().getId() != null){
						debito = pagamentoHistorico.getDebitoTipo().getId().toString();
					}

					String valorPagamento = "";
					if(pagamentoHistorico.getValorPagamento() != null){
						valorPagamento = Util.formatarMoedaReal(pagamentoHistorico.getValorPagamento());
					}

					String situacaoAtual = "";
					if(pagamentoHistorico.getPagamentoSituacaoAtual() != null
									&& pagamentoHistorico.getPagamentoSituacaoAtual().getDescricaoAbreviada() != null){
						situacaoAtual = pagamentoHistorico.getPagamentoSituacaoAtual().getDescricaoAbreviada();
					}

					String debitoACobrar = "";
					if(pagamentoHistorico.getDebitoACobrar() != null && pagamentoHistorico.getDebitoACobrar().getId() != null){
						debitoACobrar = String.valueOf(pagamentoHistorico.getDebitoACobrar().getId());
					}

					String idGuia = "";
					if(pagamentoHistorico.getGuiaPagamentoGeral() != null && pagamentoHistorico.getGuiaPagamentoGeral().getId() != null){
						idGuia = String.valueOf(pagamentoHistorico.getGuiaPagamentoGeral().getId());
					}

					String nnPrestacao = "";
					if(pagamentoHistorico.getNumeroPrestacao() != null){
						nnPrestacao = String.valueOf(pagamentoHistorico.getNumeroPrestacao().intValue());
					}

					String numeroPagamento = "";

					if(pagamentoHistorico.getConta().getId() != null){
						numeroPagamento = String.valueOf(pagamentoHistorico.getConta().getId().intValue());
					}

					else if(pagamentoHistorico.getDebitoACobrar().getId() != null){
						numeroPagamento = String.valueOf(pagamentoHistorico.getDebitoACobrar().getId().intValue());
					}

					else if(pagamentoHistorico.getGuiaPagamentoGeral().getId() != null){
						numeroPagamento = String.valueOf(pagamentoHistorico.getGuiaPagamentoGeral().getId().intValue());
					}

					if(nnPrestacao != null && nnPrestacao.length() > 0){
						nnPrestacao = "/ " + nnPrestacao;
					}

					relatorioBean = new RelatorioPagamentoBean(

					// Tipo de Documento
									tipoDocumento,

									// Data do Pagamento
									dataPagamento,

									// Localidade
									localidade,

									// Matrícula do Imóvel
									matricula,

									// Código do Cliente
									codigoCliente,

									// Referência do Pagamento
									referencia,

									// Débito
									debito,

									// Valor Pagamento
									valorPagamento,

									// Situação Atual
									situacaoAtual,

									// Indicador Histórico
									true,

									// Numero Prestacao
									nnPrestacao,

									// Numero Pagamento
									numeroPagamento);

					relatorioBean.setIdDebitoACobrar(debitoACobrar);
					relatorioBean.setIdGuiaPagamento(idGuia);

					relatorioBeans.add(relatorioBean);

				}

			}

		}else{

			// se a coleção de parâmetros da analise não for vazia
			if(colecaoRetorno != null && !colecaoRetorno.isEmpty()){

				// coloca a coleção de parâmetros da analise no iterator
				Iterator<PagamentoRelatorioHelper> colecaoPagamentosIterator = colecaoRetorno.iterator();

				String dataPagamentoAnterior = "";
				String mesAnoAnterior = "";

				// laço para criar a coleção de parâmetros da analise
				while(colecaoPagamentosIterator.hasNext()){

					PagamentoRelatorioHelper pagamentoRelatorioHelper = colecaoPagamentosIterator.next();
					// (pagamento.getGuiaPagamento() == null && pagamento
					// .getAnoMesReferenciaPagamento() != null)
					// ||

					// Verifica o tipo do documento do pagamento e calcula a
					// quantidade de pagamentos de cada tipo e o somatório do valor dos pagamentos
					Imovel imovel = fachada.pesquisarImovelParaEndereco(pagamentoRelatorioHelper.getIdImovel());
					String dataVencimento = "";
					String enderecoImovel = imovel.getEnderecoFormatado();
					FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
					filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, idImovel));
					filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID,
									ClienteRelacaoTipo.USUARIO));
					filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
					Collection<ClienteImovelSimplificado> colecaoClienteImovel = fachada.pesquisarClienteImovel(filtroClienteImovel);
					if(colecaoClienteImovel != null && !colecaoClienteImovel.isEmpty()){
						pagamentoRelatorioHelper.setNomeCliente(colecaoClienteImovel.iterator().next().getCliente().getNome());
					}

					// Conta
					if(pagamentoRelatorioHelper.getIdDocumentoTipo().equals(DocumentoTipo.CONTA)){

						// Data Vencimento
						dataVencimento = Util.formatarData(pagamentoRelatorioHelper.getDataVencimento());

						// Pagamento Classificado
						if(pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual() == null
										|| pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(
														PagamentoSituacao.PAGAMENTO_CLASSIFICADO)){
							qtdePagamentosClassificadoContas = qtdePagamentosClassificadoContas + 1;
							if(pagamentoRelatorioHelper.getValorPagamento() != null){
								valorPagamentosClassificadoContas = valorPagamentosClassificadoContas.add(pagamentoRelatorioHelper
												.getValorPagamento());
							}
						}

						// Pagamento em Duplicidade / Excesso
						else if(pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(PagamentoSituacao.PAGAMENTO_EM_DUPLICIDADE)
										|| pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(
														PagamentoSituacao.PAGAMENTO_DUPLICADO)
										|| pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual()
														.equals(PagamentoSituacao.VALOR_EM_EXCESSO)
										|| pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(
														PagamentoSituacao.PAGAMENTO_A_MAIOR)){
							qtdePagamentosDuploExcessoContas = qtdePagamentosDuploExcessoContas + 1;
							if(pagamentoRelatorioHelper.getValorPagamento() != null){
								valorPagamentosDuploExcessoContas = valorPagamentosDuploExcessoContas.add(pagamentoRelatorioHelper
												.getValorPagamento());
							}
						}

						// Documento Inexistente
						else if(pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(PagamentoSituacao.DOCUMENTO_INEXISTENTE)){
							qtdePagamentosDocumentoInexistenteContas = qtdePagamentosDocumentoInexistenteContas + 1;
							if(pagamentoRelatorioHelper.getValorPagamento() != null){
								valorPagamentosDocumentoInexistenteContas = valorPagamentosDocumentoInexistenteContas
												.add(pagamentoRelatorioHelper.getValorPagamento());
							}
						}

						// Baixar Valor Excedente
						else if(pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(PagamentoSituacao.VALOR_A_BAIXAR)){
							qtdePagamentosBaixarValorExcedenteContas = qtdePagamentosBaixarValorExcedenteContas + 1;
							if(pagamentoRelatorioHelper.getValorPagamento() != null){
								valorPagamentosBaixarValorExcedenteContas = valorPagamentosBaixarValorExcedenteContas
												.add(pagamentoRelatorioHelper.getValorPagamento());
							}
						}

						// Valor Não Confere
						else if(pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(PagamentoSituacao.VALOR_NAO_CONFERE)
										|| pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(
														PagamentoSituacao.PAGAMENTO_A_MENOR)){
							qtdePagamentosValorNaoConfereContas = qtdePagamentosValorNaoConfereContas + 1;
							if(pagamentoRelatorioHelper.getValorPagamento() != null){
								valorPagamentosValorNaoConfereContas = valorPagamentosValorNaoConfereContas.add(pagamentoRelatorioHelper
												.getValorPagamento());
							}
						}

						// Pagamento Duplo em Excesso Devolvido
						else if(pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(
										PagamentoSituacao.DUPLICIDADE_EXCESSO_DEVOLVIDO)){
							qtdePagamentosDuploExcessoDevolvidoContas = qtdePagamentosDuploExcessoDevolvidoContas + 1;
							if(pagamentoRelatorioHelper.getValorPagamento() != null){
								valorPagamentosDuploExcessoDevolvidoContas = valorPagamentosDuploExcessoDevolvidoContas
												.add(pagamentoRelatorioHelper.getValorPagamento());
							}
						}

						// Conta em Faturamento
						else if(pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual() == null
										|| pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(
														PagamentoSituacao.CONTA_EM_FATURAMENTO)){
							qtdePagamentosContaFaturamentoContas = qtdePagamentosContaFaturamentoContas + 1;
							if(pagamentoRelatorioHelper.getValorPagamento() != null){
								valorPagamentosContaFaturamentoContas = valorPagamentosContaFaturamentoContas.add(pagamentoRelatorioHelper
												.getValorPagamento());
							}
						}
					}

					// Débito a Cobrar
					else if(pagamentoRelatorioHelper.getIdDocumentoTipo().equals(DocumentoTipo.DEBITO_A_COBRAR)){

						// Pagamento Classificado
						if(pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual() == null
										|| pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(
														PagamentoSituacao.PAGAMENTO_CLASSIFICADO)){
							qtdePagamentosClassificadoDebitosACobrar = qtdePagamentosClassificadoDebitosACobrar + 1;
							if(pagamentoRelatorioHelper.getValorPagamento() != null){
								valorPagamentosClassificadoDebitosACobrar = valorPagamentosClassificadoDebitosACobrar
												.add(pagamentoRelatorioHelper.getValorPagamento());
							}
						}

						// Pagamento em Duplicidade / Excesso
						else if(pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(PagamentoSituacao.PAGAMENTO_EM_DUPLICIDADE)
										|| pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(
														PagamentoSituacao.PAGAMENTO_DUPLICADO)
										|| pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual()
														.equals(PagamentoSituacao.VALOR_EM_EXCESSO)
										|| pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(
														PagamentoSituacao.PAGAMENTO_A_MAIOR)){
							qtdePagamentosDuploExcessoDebitosACobrar = qtdePagamentosDuploExcessoDebitosACobrar + 1;
							if(pagamentoRelatorioHelper.getValorPagamento() != null){
								valorPagamentosDuploExcessoDebitosACobrar = valorPagamentosDuploExcessoDebitosACobrar
												.add(pagamentoRelatorioHelper.getValorPagamento());
							}
						}

						// Documento Inexistente
						else if(pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(PagamentoSituacao.DOCUMENTO_INEXISTENTE)){
							qtdePagamentosDocumentoInexistenteDebitosACobrar = qtdePagamentosDocumentoInexistenteDebitosACobrar + 1;
							if(pagamentoRelatorioHelper.getValorPagamento() != null){
								valorPagamentosDocumentoInexistenteDebitosACobrar = valorPagamentosDocumentoInexistenteDebitosACobrar
												.add(pagamentoRelatorioHelper.getValorPagamento());
							}
						}

						// Baixar Valor Excedente
						else if(pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(PagamentoSituacao.VALOR_A_BAIXAR)){
							qtdePagamentosBaixarValorExcedenteDebitosACobrar = qtdePagamentosBaixarValorExcedenteDebitosACobrar + 1;
							if(pagamentoRelatorioHelper.getValorPagamento() != null){
								valorPagamentosBaixarValorExcedenteDebitosACobrar = valorPagamentosBaixarValorExcedenteDebitosACobrar
												.add(pagamentoRelatorioHelper.getValorPagamento());
							}
						}

						// Valor Não Confere
						else if(pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(PagamentoSituacao.VALOR_NAO_CONFERE)
										|| pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(
														PagamentoSituacao.PAGAMENTO_A_MENOR)){
							qtdePagamentosValorNaoConfereDebitosACobrar = qtdePagamentosValorNaoConfereDebitosACobrar + 1;
							if(pagamentoRelatorioHelper.getValorPagamento() != null){
								valorPagamentosValorNaoConfereDebitosACobrar = valorPagamentosValorNaoConfereDebitosACobrar
												.add(pagamentoRelatorioHelper.getValorPagamento());
							}
						}

						// Pagamento Duplo em Excesso Devolvido
						else if(pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(
										PagamentoSituacao.DUPLICIDADE_EXCESSO_DEVOLVIDO)){
							qtdePagamentosDuploExcessoDevolvidoDebitosACobrar = qtdePagamentosDuploExcessoDevolvidoDebitosACobrar + 1;
							if(pagamentoRelatorioHelper.getValorPagamento() != null){
								valorPagamentosDuploExcessoDevolvidoDebitosACobrar = valorPagamentosDuploExcessoDevolvidoDebitosACobrar
												.add(pagamentoRelatorioHelper.getValorPagamento());
							}
						}
					}

					// Guia Pagamento
					else if(pagamentoRelatorioHelper.getIdDocumentoTipo().equals(DocumentoTipo.GUIA_PAGAMENTO)){

						// Data Vencimento
						dataVencimento = "";
						if(pagamentoRelatorioHelper.getIdGuiaPagamento() != null && pagamentoRelatorioHelper.getNumeroPrestacao() != null){
							FiltroGuiaPagamentoPrestacao filtroGuiaPagamentoPrestacao = new FiltroGuiaPagamentoPrestacao();
							filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(
											FiltroGuiaPagamentoPrestacao.GUIA_PAGAMENTO_ID, pagamentoRelatorioHelper.getIdGuiaPagamento()));
							filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(
											FiltroGuiaPagamentoPrestacao.NUMERO_PRESTACAO, pagamentoRelatorioHelper.getNumeroPrestacao()));
							Collection<GuiaPagamentoPrestacao> colecaoGuiaPagamentoPrestacao = fachada.pesquisar(
											filtroGuiaPagamentoPrestacao, GuiaPagamentoPrestacao.class.getName());
							if(colecaoGuiaPagamentoPrestacao != null && !colecaoGuiaPagamentoPrestacao.isEmpty()){
								GuiaPagamentoPrestacao guiaPagamentoPrestacao = colecaoGuiaPagamentoPrestacao.iterator().next();
								dataVencimento = Util.formatarData(guiaPagamentoPrestacao.getDataVencimento());
							}else{
								if(pagamentoRelatorioHelper.getIdGuiaPagamento() != null
												&& !pagamentoRelatorioHelper.getIdGuiaPagamento().equals("")){
									FiltroGuiaPagamentoPrestacaoHistorico filtroGuiaPagamentoPrestacaoHistorico = new FiltroGuiaPagamentoPrestacaoHistorico();
									filtroGuiaPagamentoPrestacaoHistorico.adicionarParametro(new ParametroSimples(
													FiltroGuiaPagamentoPrestacao.GUIA_PAGAMENTO_ID, pagamentoRelatorioHelper
																	.getIdGuiaPagamento()));
									filtroGuiaPagamentoPrestacaoHistorico.adicionarParametro(new ParametroSimples(
													FiltroGuiaPagamentoPrestacao.NUMERO_PRESTACAO, pagamentoRelatorioHelper
																	.getNumeroPrestacao()));
									Collection<GuiaPagamentoPrestacaoHistorico> colecaoGuiaPagamentoPrestacaoHistorico = fachada.pesquisar(
													filtroGuiaPagamentoPrestacaoHistorico, GuiaPagamentoPrestacaoHistorico.class.getName());
									if(colecaoGuiaPagamentoPrestacaoHistorico != null && !colecaoGuiaPagamentoPrestacaoHistorico.isEmpty()){
										GuiaPagamentoPrestacaoHistorico guiaPagamentoPrestacaoHistorico = colecaoGuiaPagamentoPrestacaoHistorico
														.iterator().next();
										dataVencimento = Util.formatarData(guiaPagamentoPrestacaoHistorico.getDataVencimento());
									}
								}
							}
						}

						// Pagamento Classificado
						if(pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual() == null
										|| pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(
														PagamentoSituacao.PAGAMENTO_CLASSIFICADO)){
							qtdePagamentosClassificadoGuiasPagamento = qtdePagamentosClassificadoGuiasPagamento + 1;
							if(pagamentoRelatorioHelper.getValorPagamento() != null){
								valorPagamentosClassificadoGuiasPagamento = valorPagamentosClassificadoGuiasPagamento
												.add(pagamentoRelatorioHelper.getValorPagamento());
							}
						}

						// Pagamento em Duplicidade / Excesso
						else if(pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(PagamentoSituacao.PAGAMENTO_EM_DUPLICIDADE)
										|| pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(
														PagamentoSituacao.PAGAMENTO_DUPLICADO)
										|| pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual()
														.equals(PagamentoSituacao.VALOR_EM_EXCESSO)
										|| pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(
														PagamentoSituacao.PAGAMENTO_A_MAIOR)){
							qtdePagamentosDuploExcessoGuiasPagamento = qtdePagamentosDuploExcessoGuiasPagamento + 1;
							if(pagamentoRelatorioHelper.getValorPagamento() != null){
								valorPagamentosDuploExcessoGuiasPagamento = valorPagamentosDuploExcessoGuiasPagamento
												.add(pagamentoRelatorioHelper.getValorPagamento());
							}
						}

						// Documento Inexistente
						else if(pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(PagamentoSituacao.DOCUMENTO_INEXISTENTE)){
							qtdePagamentosDocumentoInexistenteGuiasPagamento = qtdePagamentosDocumentoInexistenteGuiasPagamento + 1;
							if(pagamentoRelatorioHelper.getValorPagamento() != null){
								valorPagamentosDocumentoInexistenteGuiasPagamento = valorPagamentosDocumentoInexistenteGuiasPagamento
												.add(pagamentoRelatorioHelper.getValorPagamento());
							}
						}

						// Baixar Valor Excedente
						else if(pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(PagamentoSituacao.VALOR_A_BAIXAR)){
							qtdePagamentosBaixarValorExcedenteGuiasPagamento = qtdePagamentosBaixarValorExcedenteGuiasPagamento + 1;
							if(pagamentoRelatorioHelper.getValorPagamento() != null){
								valorPagamentosBaixarValorExcedenteGuiasPagamento = valorPagamentosBaixarValorExcedenteGuiasPagamento
												.add(pagamentoRelatorioHelper.getValorPagamento());
							}
						}

						// Valor Não Confere
						else if(pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(PagamentoSituacao.VALOR_NAO_CONFERE)
										|| pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(
														PagamentoSituacao.PAGAMENTO_A_MENOR)){
							qtdePagamentosValorNaoConfereGuiasPagamento = qtdePagamentosValorNaoConfereGuiasPagamento + 1;
							if(pagamentoRelatorioHelper.getValorPagamento() != null){
								valorPagamentosValorNaoConfereGuiasPagamento = valorPagamentosValorNaoConfereGuiasPagamento
												.add(pagamentoRelatorioHelper.getValorPagamento());
							}
						}

						// Pagamento Duplo em Excesso Devolvido
						else if(pagamentoRelatorioHelper.getIdSituacaoPagamentoAtual().equals(
										PagamentoSituacao.DUPLICIDADE_EXCESSO_DEVOLVIDO)){
							qtdePagamentosDuploExcessoDevolvidoGuiasPagamento = qtdePagamentosDuploExcessoDevolvidoGuiasPagamento + 1;
							if(pagamentoRelatorioHelper.getValorPagamento() != null){
								valorPagamentosDuploExcessoDevolvidoGuiasPagamento = valorPagamentosDuploExcessoDevolvidoGuiasPagamento
												.add(pagamentoRelatorioHelper.getValorPagamento());
							}
						}

					}

					String valorDocumento = Util.formatarMoedaReal(pagamentoRelatorioHelper.getValorTotalDocumento());

					// Cria o objeto que será impresso no relatório setando os
					// campos que serão mostrados e fazendo as verificações para
					// evitar NullPointerException

					if(relatorioConsultarImovel){

						relatorioBean = new RelatorioPagamentoBean(

						// Matrícula do Imóvel ou Código do Cliente
										pagamentoRelatorioHelper.getIdImovel().toString(),

										// Inscricao do Imóvel ou Nome do Cliente
										fachada.pesquisarInscricaoImovel(pagamentoRelatorioHelper.getIdImovel(), true),

										// Data Pagamento
										pagamentoRelatorioHelper.getDataPagamento() == null ? "" : Util
														.formatarData(pagamentoRelatorioHelper.getDataPagamento()),

										// Mês/Ano
										pagamentoRelatorioHelper.getAnoMesReferenciaPagamento() == null ? "" : Util
														.formatarAnoMesParaMesAno(pagamentoRelatorioHelper.getAnoMesReferenciaPagamento()),

										// Tipo de Débito
										pagamentoRelatorioHelper.getDescricaoTipoDebito() == null ? "" : pagamentoRelatorioHelper
														.getDescricaoTipoDebito(),

										// Valor do Documento
										valorDocumento,

										// Valor Pagamento
										pagamentoRelatorioHelper.getValorPagamento() == null ? "" : Util
														.formatarMoedaReal(pagamentoRelatorioHelper.getValorPagamento()),

										// Situação Atual
										pagamentoRelatorioHelper.getDescricaoSituacaoPagamentoAtual() == null ? ""
														: pagamentoRelatorioHelper.getDescricaoSituacaoPagamentoAtual(),

										// Situação Anterior
										pagamentoRelatorioHelper.getDescricaoSituacaoPagamentoAnterior() == null ? ""
														: pagamentoRelatorioHelper.getDescricaoSituacaoPagamentoAnterior(),

										// Tipo Documento
										pagamentoRelatorioHelper.getDescricaoDocumentoTipo() == null ? "" : pagamentoRelatorioHelper
														.getDescricaoDocumentoTipo(),

										// Endereço do Imovel
										enderecoImovel,

										// Data de Vencimento
										dataVencimento,

										// Nome do Cliente
										pagamentoRelatorioHelper.getNomeCliente() == null ? "" : pagamentoRelatorioHelper.getNomeCliente()
														.toString(),

										// ID do DebitoACobrar
										pagamentoRelatorioHelper.getIdDebitoACobrar() == null ? "" : pagamentoRelatorioHelper
														.getIdDebitoACobrar().toString(),

										// ID do GuiaPagamento
										pagamentoRelatorioHelper.getIdGuiaPagamento() == null ? "" : pagamentoRelatorioHelper
														.getIdGuiaPagamento().toString(),

										// Numero Prestação
										pagamentoRelatorioHelper.getNumeroPrestacao() == null ? "" : pagamentoRelatorioHelper
														.getNumeroPrestacao().toString());

					}else{

						relatorioBean = new RelatorioPagamentoBean(

						// Gerência Regional
										pagamentoRelatorioHelper.getIdGerenciaRegional() == null ? "" : pagamentoRelatorioHelper
														.getIdGerenciaRegional()
														+ " - " + pagamentoRelatorioHelper.getNomeGerenciaRegional(),

										// Localidade
										pagamentoRelatorioHelper.getIdLocalidade().toString() + " - "
														+ pagamentoRelatorioHelper.getDescricaoLocalidade(),

										// Matrícula do Imóvel ou Código do Cliente
										pagamentoRelatorioHelper.getIdImovel() == null ? pagamentoRelatorioHelper.getIdCliente().toString()
														: pagamentoRelatorioHelper.getIdImovel().toString(),

										// Inscricao do Imóvel ou Nome do Cliente
										pagamentoRelatorioHelper.getIdImovel() == null ? pagamentoRelatorioHelper.getNomeCliente()
														: fachada.pesquisarInscricaoImovel(pagamentoRelatorioHelper.getIdImovel(), true),

										// Arrecadador
										pagamentoRelatorioHelper.getNomeArrecadador() == null ? "" : pagamentoRelatorioHelper
														.getNomeArrecadador(),

										// Data Pagamento
										pagamentoRelatorioHelper.getDataPagamento() == null ? "" : Util
														.formatarData(pagamentoRelatorioHelper.getDataPagamento()),

										// Mês/Ano
										pagamentoRelatorioHelper.getAnoMesReferenciaPagamento() == null ? "" : Util
														.formatarAnoMesParaMesAno(pagamentoRelatorioHelper.getAnoMesReferenciaPagamento()),

										// Tipo de Débito
										pagamentoRelatorioHelper.getDescricaoTipoDebito() == null ? "" : pagamentoRelatorioHelper
														.getDescricaoTipoDebito(),

										// Valor do Documento
										valorDocumento,

										// Valor Pagamento
										pagamentoRelatorioHelper.getValorPagamento() == null ? "" : Util
														.formatarMoedaReal(pagamentoRelatorioHelper.getValorPagamento()),

										// Situação Atual
										pagamentoRelatorioHelper.getDescricaoSituacaoPagamentoAtual() == null ? ""
														: pagamentoRelatorioHelper.getDescricaoSituacaoPagamentoAtual(),

										// numero da prestação
										pagamentoRelatorioHelper.getNumeroPrestacao() == null ? "" : pagamentoRelatorioHelper
														.getNumeroPrestacao().toString(),

										// Id Debito a Cobrar
										pagamentoRelatorioHelper.getIdDebitoACobrar() == null ? "" : pagamentoRelatorioHelper
														.getIdDebitoACobrar().toString(),

										// Id Guia pagamento
										pagamentoRelatorioHelper.getIdGuiaPagamento() == null ? "" : pagamentoRelatorioHelper
														.getIdGuiaPagamento().toString());

						String dataPagamentoAux = "";

						if(!dataPagamentoAnterior.equals(relatorioBean.getDataPagamento())){
							dataPagamentoAnterior = relatorioBean.getDataPagamento();
							dataPagamentoAux = relatorioBean.getDataPagamento();
						}

						relatorioBean.setDataPagamentoAux(dataPagamentoAux);

						String mesAnoAux = "";

						if(!mesAnoAnterior.equals(relatorioBean.getMesAno())){
							mesAnoAnterior = relatorioBean.getMesAno();
							mesAnoAux = relatorioBean.getMesAno();
						}

						relatorioBean.setMesAnoAux(mesAnoAux);

						String codigoSetorComercialStr = "";
						Integer codigoSetorComercial = pagamentoRelatorioHelper.getCodigoSetorComercial();

						if(codigoSetorComercial != null){
							codigoSetorComercialStr = Integer.toString(codigoSetorComercial);
						}

						relatorioBean.setCodigoSetorComercial(codigoSetorComercialStr);

						valorRemuneracao = BigDecimal.ZERO;

						exibirValorRemuneracao = true;

						if(!Util.isVazioOuBranco(pReferenciaConta) && !pReferenciaConta.equals("0")){
							idConta = pagamentoRelatorioHelper.getIdConta();

							if(pagamentoRelatorioHelper.getIdDocumentoTipo().equals(DocumentoTipo.CONTA) && idConta != null){
								filtroContaGeral = new FiltroContaGeral();
								filtroContaGeral.adicionarParametro(new ParametroSimples(FiltroContaGeral.ID, idConta));

								colecaoContaGeral = fachada.pesquisar(filtroContaGeral, ContaGeral.class.getName());

								if(!Util.isVazioOrNulo(colecaoContaGeral)){
									contaGeral = (ContaGeral) Util.retonarObjetoDeColecao(colecaoContaGeral);

									indicadorHistorico = contaGeral.getIndicadorHistorico();

									if(ConstantesSistema.SIM.equals(indicadorHistorico)){
										valorRemuneracao = fachada.retornarValorRemuneracaoContaHistorico(idConta, pReferenciaContaInt);
									}else{
										valorRemuneracao = fachada.retornarValorRemuneracaoConta(idConta, pReferenciaContaInt);
									}

									if(valorRemuneracao == null){
										valorRemuneracao = BigDecimal.ZERO;
									}
								}
							}
						}else{
							exibirValorRemuneracao = false;
						}

						relatorioBean.setValorRemuneracao(valorRemuneracao);

						relatorioBean.setExibirValorRemuneracao(exibirValorRemuneracao);
					}

					// ID do DebitoACobrar
					relatorioBean.setIdDebitoACobrar(pagamentoRelatorioHelper.getIdDebitoACobrar() == null ? "" : pagamentoRelatorioHelper
									.getIdDebitoACobrar().toString());

					// ID do GuiaPagamento
					relatorioBean.setIdGuiaPagamento(pagamentoRelatorioHelper.getIdGuiaPagamento() == null ? "" : pagamentoRelatorioHelper
									.getIdGuiaPagamento().toString());

					// adiciona o bean a coleção
					relatorioBeans.add(relatorioBean);
				}

			}

		}
		// __________________________________________________________________

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		// adiciona o laudo da análise
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		if(relatorioConsultarAvisoBancario){

			// Arrecadador
			if(avisoBancarioHelper.getCodigoNomeArrecadador() != null){
				parametros.put("arrecadador", avisoBancarioHelper.getCodigoNomeArrecadador());
			}else{
				parametros.put("arrecadador", "");
			}

			// Data do Lançamento
			if(avisoBancarioHelper.getAvisoBancario().getDataLancamento() != null){
				parametros.put("dataLancamento", Util.formatarData(avisoBancarioHelper.getAvisoBancario().getDataLancamento()));
			}else{
				parametros.put("dataLancamento", "");
			}

			// Sequencial
			if(avisoBancarioHelper.getAvisoBancario().getNumeroSequencial() != null){
				parametros.put("sequencial", avisoBancarioHelper.getAvisoBancario().getNumeroSequencial().toString());
			}else{
				parametros.put("sequencial", "");
			}

			// Número do Documento
			if(avisoBancarioHelper.getAvisoBancario().getNumeroDocumento() != 0){
				parametros.put("numeroDocumento", String.valueOf(avisoBancarioHelper.getAvisoBancario().getNumeroDocumento()));
			}else{
				parametros.put("numeroDocumento", "");
			}

		}else{

			// Seta os parâmetros de acordo com o que o usuário digitou ou de acordo
			// com a qtde de devoluções de cada tipo e seus respectivos valores
			if(pagamentoParametrosInicial.getAnoMesReferenciaPagamento() != null){
				parametros.put("periodoAnoMes", Util.formatarAnoMesParaMesAno(pagamentoParametrosInicial.getAnoMesReferenciaPagamento())
								+ " a " + Util.formatarAnoMesParaMesAno(pagamentoParametrosFinal.getAnoMesReferenciaPagamento()));

			}else{
				parametros.put("periodoAnoMes", "");
			}

			if(pagamentoParametrosInicial.getDataPagamento() != null){
				parametros.put("periodoPagamento", Util.formatarData(pagamentoParametrosInicial.getDataPagamento()) + " a "
								+ Util.formatarData(pagamentoParametrosFinal.getDataPagamento()));
			}else{
				parametros.put("periodoPagamento", "");
			}

			// o valor total das devoluções de cada tipo será o valor de todas as
			// devoluções desse tipo presente em cada tipo de documento, assim,
			// adiciona-se os valores a ele para achar o resultado
			qtdePagamentosClassificado = qtdePagamentosClassificado + qtdePagamentosClassificadoContas
							+ qtdePagamentosClassificadoDebitosACobrar + qtdePagamentosClassificadoGuiasPagamento;

			valorPagamentosClassificado = valorPagamentosClassificado.add(valorPagamentosClassificadoContas
							.add(valorPagamentosClassificadoDebitosACobrar.add(valorPagamentosClassificadoGuiasPagamento)));

			qtdePagamentosDuploExcesso = qtdePagamentosDuploExcesso + qtdePagamentosDuploExcessoContas
							+ qtdePagamentosDuploExcessoDebitosACobrar + qtdePagamentosDuploExcessoGuiasPagamento;

			valorPagamentosDuploExcesso = valorPagamentosDuploExcesso.add(valorPagamentosDuploExcessoContas
							.add(valorPagamentosDuploExcessoDebitosACobrar.add(valorPagamentosDuploExcessoGuiasPagamento)));

			qtdePagamentosDocumentoInexistente = qtdePagamentosDocumentoInexistente + qtdePagamentosDocumentoInexistenteContas
							+ qtdePagamentosDocumentoInexistenteDebitosACobrar + qtdePagamentosDocumentoInexistenteGuiasPagamento;

			valorPagamentosDocumentoInexistente = valorPagamentosDocumentoInexistente.add(valorPagamentosDocumentoInexistenteContas
							.add(valorPagamentosDocumentoInexistenteDebitosACobrar.add(valorPagamentosDocumentoInexistenteGuiasPagamento)));

			qtdePagamentosBaixarValorExcedente = qtdePagamentosBaixarValorExcedente + qtdePagamentosBaixarValorExcedenteContas
							+ qtdePagamentosBaixarValorExcedenteDebitosACobrar + qtdePagamentosBaixarValorExcedenteGuiasPagamento;

			valorPagamentosBaixarValorExcedente = valorPagamentosBaixarValorExcedente.add(valorPagamentosBaixarValorExcedenteContas
							.add(valorPagamentosBaixarValorExcedenteDebitosACobrar.add(valorPagamentosBaixarValorExcedenteGuiasPagamento)));

			qtdePagamentosValorNaoConfere = qtdePagamentosValorNaoConfere + qtdePagamentosValorNaoConfereContas
							+ qtdePagamentosValorNaoConfereDebitosACobrar + qtdePagamentosValorNaoConfereGuiasPagamento;

			valorPagamentosValorNaoConfere = valorPagamentosValorNaoConfere.add(valorPagamentosValorNaoConfereContas
							.add(valorPagamentosValorNaoConfereDebitosACobrar.add(valorPagamentosValorNaoConfereGuiasPagamento)));

			qtdePagamentosDuploExcessoDevolvido = qtdePagamentosDuploExcessoDevolvido + qtdePagamentosDuploExcessoDevolvidoContas
							+ qtdePagamentosDuploExcessoDevolvidoDebitosACobrar + qtdePagamentosDuploExcessoDevolvidoGuiasPagamento;

			valorPagamentosDuploExcessoDevolvido = valorPagamentosDuploExcessoDevolvido
							.add(valorPagamentosDuploExcessoDevolvidoContas.add(valorPagamentosDuploExcessoDevolvidoDebitosACobrar
											.add(valorPagamentosDuploExcessoDevolvidoGuiasPagamento)));

			parametros.put("qtdePagamentosClassificadoContas", "" + qtdePagamentosClassificadoContas);
			parametros.put("valorPagamentosClassificadoContas", Util.formatarMoedaReal(valorPagamentosClassificadoContas));
			parametros.put("qtdePagamentosDuploExcessoContas", "" + qtdePagamentosDuploExcessoContas);
			parametros.put("valorPagamentosDuploExcessoContas", Util.formatarMoedaReal(valorPagamentosDuploExcessoContas));
			parametros.put("qtdePagamentosDocumentoInexistenteContas", "" + qtdePagamentosDocumentoInexistenteContas);
			parametros.put("valorPagamentosDocumentoInexistenteContas", Util.formatarMoedaReal(valorPagamentosDocumentoInexistenteContas));
			parametros.put("qtdePagamentosBaixarValorExcedenteContas", "" + qtdePagamentosBaixarValorExcedenteContas);
			parametros.put("valorPagamentosBaixarValorExcedenteContas", Util.formatarMoedaReal(valorPagamentosBaixarValorExcedenteContas));
			parametros.put("qtdePagamentosValorNaoConfereContas", "" + qtdePagamentosValorNaoConfereContas);
			parametros.put("valorPagamentosValorNaoConfereContas", Util.formatarMoedaReal(valorPagamentosValorNaoConfereContas));
			parametros.put("qtdePagamentosDuploExcessoDevolvidoContas", "" + qtdePagamentosDuploExcessoDevolvidoContas);
			parametros
							.put("valorPagamentosDuploExcessoDevolvidoContas", Util
											.formatarMoedaReal(valorPagamentosDuploExcessoDevolvidoContas));
			parametros.put("qtdePagamentosContaFaturamentoContas", "" + qtdePagamentosContaFaturamentoContas);
			parametros.put("valorPagamentosContaFaturamentoContas", Util.formatarMoedaReal(valorPagamentosContaFaturamentoContas));

			parametros.put("qtdePagamentosClassificadoGuiasPagamento", "" + qtdePagamentosClassificadoGuiasPagamento);
			parametros.put("valorPagamentosClassificadoGuiasPagamento", Util.formatarMoedaReal(valorPagamentosClassificadoGuiasPagamento));
			parametros.put("qtdePagamentosDuploExcessoGuiasPagamento", "" + qtdePagamentosDuploExcessoGuiasPagamento);
			parametros.put("valorPagamentosDuploExcessoGuiasPagamento", Util.formatarMoedaReal(valorPagamentosDuploExcessoGuiasPagamento));
			parametros.put("qtdePagamentosDocumentoInexistenteGuiasPagamento", "" + qtdePagamentosDocumentoInexistenteGuiasPagamento);
			parametros.put("valorPagamentosDocumentoInexistenteGuiasPagamento", Util
							.formatarMoedaReal(valorPagamentosDocumentoInexistenteGuiasPagamento));
			parametros.put("qtdePagamentosBaixarValorExcedenteGuiasPagamento", "" + qtdePagamentosBaixarValorExcedenteGuiasPagamento);
			parametros.put("valorPagamentosBaixarValorExcedenteGuiasPagamento", Util
							.formatarMoedaReal(valorPagamentosBaixarValorExcedenteGuiasPagamento));
			parametros.put("qtdePagamentosValorNaoConfereGuiasPagamento", "" + qtdePagamentosValorNaoConfereGuiasPagamento);
			parametros.put("valorPagamentosValorNaoConfereGuiasPagamento", Util
							.formatarMoedaReal(valorPagamentosValorNaoConfereGuiasPagamento));
			parametros.put("qtdePagamentosDuploExcessoDevolvidoGuiasPagamento", "" + qtdePagamentosDuploExcessoDevolvidoGuiasPagamento);
			parametros.put("valorPagamentosDuploExcessoDevolvidoGuiasPagamento", Util
							.formatarMoedaReal(valorPagamentosDuploExcessoDevolvidoGuiasPagamento));

			parametros.put("qtdePagamentosClassificadoDebitosACobrar", "" + qtdePagamentosClassificadoDebitosACobrar);
			parametros.put("valorPagamentosClassificadoDebitosACobrar", Util.formatarMoedaReal(valorPagamentosClassificadoDebitosACobrar));
			parametros.put("qtdePagamentosDuploExcessoDebitosACobrar", "" + qtdePagamentosDuploExcessoDebitosACobrar);
			parametros.put("valorPagamentosDuploExcessoDebitosACobrar", Util.formatarMoedaReal(valorPagamentosDuploExcessoDebitosACobrar));
			parametros.put("qtdePagamentosDocumentoInexistenteDebitosACobrar", "" + qtdePagamentosDocumentoInexistenteDebitosACobrar);
			parametros.put("valorPagamentosDocumentoInexistenteDebitosACobrar", Util
							.formatarMoedaReal(valorPagamentosDocumentoInexistenteDebitosACobrar));
			parametros.put("qtdePagamentosBaixarValorExcedenteDebitosACobrar", "" + qtdePagamentosBaixarValorExcedenteDebitosACobrar);
			parametros.put("valorPagamentosBaixarValorExcedenteDebitosACobrar", Util
							.formatarMoedaReal(valorPagamentosBaixarValorExcedenteDebitosACobrar));
			parametros.put("qtdePagamentosValorNaoConfereDebitosACobrar", "" + qtdePagamentosValorNaoConfereDebitosACobrar);
			parametros.put("valorPagamentosValorNaoConfereDebitosACobrar", Util
							.formatarMoedaReal(valorPagamentosValorNaoConfereDebitosACobrar));
			parametros.put("qtdePagamentosDuploExcessoDevolvidoDebitosACobrar", "" + qtdePagamentosDuploExcessoDevolvidoDebitosACobrar);
			parametros.put("valorPagamentosDuploExcessoDevolvidoDebitosACobrar", Util
							.formatarMoedaReal(valorPagamentosDuploExcessoDevolvidoDebitosACobrar));

			parametros.put("qtdePagamentosClassificado", "" + qtdePagamentosClassificado);
			parametros.put("valorPagamentosClassificado", Util.formatarMoedaReal(valorPagamentosClassificado));
			parametros.put("qtdePagamentosDuploExcesso", "" + qtdePagamentosDuploExcesso);
			parametros.put("valorPagamentosDuploExcesso", Util.formatarMoedaReal(valorPagamentosDuploExcesso));
			parametros.put("qtdePagamentosDocumentoInexistente", "" + qtdePagamentosDocumentoInexistente);
			parametros.put("valorPagamentosDocumentoInexistente", Util.formatarMoedaReal(valorPagamentosDocumentoInexistente));
			parametros.put("qtdePagamentosBaixarValorExcedente", "" + qtdePagamentosBaixarValorExcedente);
			parametros.put("valorPagamentosBaixarValorExcedente", Util.formatarMoedaReal(valorPagamentosBaixarValorExcedente));
			parametros.put("qtdePagamentosValorNaoConfere", "" + qtdePagamentosValorNaoConfere);
			parametros.put("valorPagamentosValorNaoConfere", Util.formatarMoedaReal(valorPagamentosValorNaoConfere));
			parametros.put("qtdePagamentosDuploExcessoDevolvido", "" + qtdePagamentosDuploExcessoDevolvido);
			parametros.put("valorPagamentosDuploExcessoDevolvido", Util.formatarMoedaReal(valorPagamentosDuploExcessoDevolvido));

		}

		Integer relatorioTipo = (Integer) getParametro("relatorioTipo");
		List pagamentos = new ArrayList();
		RelatorioPagamentoBean relatorioPagamentoBean;
		for(Object object : relatorioBeans){

			relatorioPagamentoBean = (RelatorioPagamentoBean) object;

			switch(relatorioTipo){
				case 1:
					if(relatorioPagamentoBean.getIndicadorHistorico()){
						pagamentos.add(relatorioPagamentoBean);
					}
					break;

				case 2:
					if(!relatorioPagamentoBean.getIndicadorHistorico()){
						pagamentos.add(relatorioPagamentoBean);
					}
					break;

				default:
					pagamentos.add(relatorioPagamentoBean);
					break;
			}

		}



		double valorTotal = 0;
		for(Object pagamento : pagamentos.toArray()){


			valorTotal += Double
.parseDouble(((RelatorioPagamentoBean) pagamento).getValorPagamento().replaceAll("\\.", "")
							.replaceAll(",", "."));
		}
		parametros.put("qtdPagamentos", Integer.toString(pagamentos.size()));

		parametros.put("valorTotal", NumberFormat.getCurrencyInstance().format(valorTotal));

		if(Util.isVazioOrNulo(pagamentos)){
			// Não existem dados para a exibição do relatório.
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(pagamentos);

		if(relatorioConsultarImovel){
			retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_PAGAMENTO_CONSULTAR_IMOVEL, parametros, ds, tipoFormatoRelatorio);
		}else if(relatorioConsultarAvisoBancario){

			retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_PAGAMENTO_CONSULTAR_AVISO_BANCARIO, parametros, ds,
							tipoFormatoRelatorio);
		}else if(indicadorTotalizarPorDataPagamento != null && indicadorTotalizarPorDataPagamento.equals(ConstantesSistema.SIM.toString())){
			retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_PAGAMENTO_TOTALIZADO_POR_DATA, parametros, ds,
							tipoFormatoRelatorio);
		}else{
			retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_PAGAMENTO, parametros, ds, tipoFormatoRelatorio);
		}

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.PAGAMENTO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Fachada fachada = Fachada.getInstancia();

		int i = 0;

		// Parâmetros de Pesquisa
		String idImovel = (String) getParametro("idImovel");
		String idCliente = (String) getParametro("idCliente");
		String clienteRelacaoTipo = (String) getParametro("clienteRelacaoTipo");
		String idAvisoBancario = (String) getParametro("idAvisoBancario");
		String idMovimentoArrecadador = (String) getParametro("idMovimentoArrecadador");
		String localidadeInicial = (String) getParametro("localidadeInicial");
		String localidadeFinal = (String) getParametro("localidadeFinal");
		String periodoArrecadacaoInicial = (String) getParametro("periodoArrecadacaoInicial");
		String periodoArrecadacaoFinal = (String) getParametro("periodoArrecadacaoFinal");
		String periodoPagamentoInicial = (String) getParametro("periodoPagamentoInicial");
		String periodoPagamentoFinal = (String) getParametro("periodoPagamentoFinal");
		Date dataPagamentoInicial = (Date) getParametro("dataPagamentoInicial");
		Date dataPagamentoFinal = (Date) getParametro("dataPagamentoFinal");
		String[] idsPagamentoSituacao = (String[]) getParametro("idsPagamentoSituacao");
		String[] idsArrecadacaoForma = (String[]) getParametro("idsArrecadacaoForma");
		String[] idsDocumentoTipo = (String[]) getParametro("idsDocumentoTipo");
		String[] idsDebitoTipo = (String[]) getParametro("idsDebitoTipo");
		String[] idsCategoria = (String[]) getParametro("idsCategoria");
		String codigoSetorComercialInicial = (String) getParametro("codigoSetorComercialInicial");
		String codigoSetorComercialFinal = (String) getParametro("codigoSetorComercialFinal");
		Collection<Integer> idsArrecadadores = (Collection<Integer>) getParametro("idsArrecadadores");

		if(idImovel != null && !idImovel.equals("")){

			// ok
			i = fachada.pesquisarPagamentoImovelCount(idImovel, idCliente, clienteRelacaoTipo, localidadeInicial, localidadeFinal,
							idAvisoBancario, idMovimentoArrecadador, periodoArrecadacaoInicial, periodoArrecadacaoFinal,
							periodoPagamentoInicial, periodoPagamentoFinal, dataPagamentoInicial, dataPagamentoFinal, idsPagamentoSituacao,
							idsDebitoTipo, idsArrecadacaoForma, idsDocumentoTipo, codigoSetorComercialInicial, codigoSetorComercialFinal,
							idsArrecadadores, idsCategoria);

		}else if(idCliente != null && !idCliente.equals("")){

			// OK
			i = fachada.pesquisarPagamentoClienteCount(idImovel, idCliente, clienteRelacaoTipo, localidadeInicial, localidadeFinal,
							idAvisoBancario, idMovimentoArrecadador, periodoArrecadacaoInicial, periodoArrecadacaoFinal,
							periodoPagamentoInicial, periodoPagamentoFinal, dataPagamentoInicial, dataPagamentoFinal, idsPagamentoSituacao,
							idsDebitoTipo, idsArrecadacaoForma, idsDocumentoTipo, codigoSetorComercialInicial, codigoSetorComercialFinal,
							idsArrecadadores, idsCategoria);

		}else if(idAvisoBancario != null && !idAvisoBancario.equals("")){

			// OK
			i = fachada.pesquisarPagamentoAvisoBancarioCount(idImovel, idCliente, clienteRelacaoTipo, localidadeInicial, localidadeFinal,
							idAvisoBancario, idMovimentoArrecadador, periodoArrecadacaoInicial, periodoArrecadacaoFinal,
							periodoPagamentoInicial, periodoPagamentoFinal, dataPagamentoInicial, dataPagamentoFinal, idsPagamentoSituacao,
							idsDebitoTipo, idsArrecadacaoForma, idsDocumentoTipo, codigoSetorComercialInicial, codigoSetorComercialFinal,
							idsArrecadadores, idsCategoria);

		}else if(idMovimentoArrecadador != null && !idMovimentoArrecadador.equals("")){
			// OK
			i = fachada.pesquisarPagamentoMovimentoArrecadadorCount(idImovel, idCliente, clienteRelacaoTipo, localidadeInicial,
							localidadeFinal, idAvisoBancario, idMovimentoArrecadador, periodoArrecadacaoInicial, periodoArrecadacaoFinal,
							periodoPagamentoInicial, periodoPagamentoFinal, dataPagamentoInicial, dataPagamentoFinal, idsPagamentoSituacao,
							idsDebitoTipo, idsArrecadacaoForma, idsDocumentoTipo, codigoSetorComercialInicial, codigoSetorComercialFinal,
							idsArrecadadores, idsCategoria);
		}else{

			i = fachada.pesquisarPagamentoLocalidadeCount(idImovel, idCliente, clienteRelacaoTipo, localidadeInicial, localidadeFinal,
							idAvisoBancario, idMovimentoArrecadador, periodoArrecadacaoInicial, periodoArrecadacaoFinal,
							periodoPagamentoInicial, periodoPagamentoFinal, dataPagamentoInicial, dataPagamentoFinal, idsPagamentoSituacao,
							idsDebitoTipo, idsArrecadacaoForma, idsDocumentoTipo, codigoSetorComercialInicial, codigoSetorComercialFinal,
							idsArrecadadores, idsCategoria);

		}

		return i;

	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioPagamento", this);
	}

}
