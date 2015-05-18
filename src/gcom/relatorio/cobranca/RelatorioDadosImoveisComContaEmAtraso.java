
package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.CalcularAcrescimoPorImpontualidadeHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.relatorio.ConstantesRelatorios;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.ZipUtil;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.io.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

/**
 * @author Anderson Italo
 * @date 13/03/2014
 */
public class RelatorioDadosImoveisComContaEmAtraso
				extends TarefaRelatorio {

	private static Logger log = Logger.getLogger(RelatorioDadosImoveisComContaEmAtraso.class);

	public RelatorioDadosImoveisComContaEmAtraso(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_DADOS_IMOVEIS_COM_CONTA_EM_ATRASO);
	}

	public RelatorioDadosImoveisComContaEmAtraso() {

		super(null, "");
	}

	private static final long serialVersionUID = 1L;

	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		byte[] retorno = null;

		try{

			log.info("Início Processamento Relatório Dados Imóveis com Conta Em Atraso");
			StringBuffer arquivoImoveisPublicos = new StringBuffer();
			StringBuffer arquivoImoveisParticulares = new StringBuffer();
			Fachada fachada = Fachada.getInstancia();
			SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

			int anoMesReferenciaAtualMenos10Anos = Util.subtrairAnoAnoMesReferencia(sistemaParametro.getAnoMesArrecadacao(), 10);

			for(int j = 1; j < 3; j++){

				List<Object[]> colecaoDadosImoveisComContaEmAtraso = null;
				if(j == 1){

					colecaoDadosImoveisComContaEmAtraso = fachada.pesquisarDadosImoveisComContaEmAtraso(true);
					log.info("Quatidade de registros de imóveis públicos Encontrados: " + colecaoDadosImoveisComContaEmAtraso.size());
				}else{

					colecaoDadosImoveisComContaEmAtraso = fachada.pesquisarDadosImoveisComContaEmAtraso(false);
					log.info("Quatidade de registros de imóveis particulares Encontrados: " + colecaoDadosImoveisComContaEmAtraso.size());
				}

				StringBuffer arquivoImoveis = new StringBuffer();

				// Totalizadores por Localidade
				BigDecimal valorTotalLocalidadeAtrasoAte30Dias = BigDecimal.ZERO;
				BigDecimal valorTotalLocalidadeAtraso31A90Dias = BigDecimal.ZERO;
				// BigDecimal valorTotalLocalidadeAtraso61A90Dias = BigDecimal.ZERO;
				BigDecimal valorTotalLocalidadeAtraso91A180Dias = BigDecimal.ZERO;
				BigDecimal valorTotalLocalidadeAtraso181A365Dias = BigDecimal.ZERO;
				// BigDecimal valorTotalLocalidadeAtraso151A180Dias = BigDecimal.ZERO;
				BigDecimal valorTotalLocalidadeAtraso366DiasA5Anos = BigDecimal.ZERO;
				BigDecimal valorTotalLocalidadeAtrasoDe5A10Anos = BigDecimal.ZERO;
				BigDecimal valorTotalLocalidadeImovel = BigDecimal.ZERO;
				BigDecimal valorTotalLocalidadeAtrasoCorrigidoImovel = BigDecimal.ZERO;

				BigDecimal valorTotalHistoricoAte10Anos = BigDecimal.ZERO;

				Integer quantidadeTotalLocalidadeAtrasoAte30Dias = 0;
				Integer quantidadeTotalLocalidadeAtraso31A90Dias = 0;
				// Integer quantidadeTotalLocalidadeAtraso61A90Dias = 0;
				Integer quantidadeTotalLocalidadeAtraso91A180Dias = 0;
				Integer quantidadeTotalLocalidadeAtraso181A365Dias = 0;
				// Integer quantidadeTotalLocalidadeAtraso366A180Dias = 0;
				Integer quantidadeTotalLocalidadeAtraso366DiasA5Anos = 0;
				Integer quantidadeTotalLocalidadeAtrasoDe5A10Anos = 0;
				Integer quantidadeTotalLocalidadeImovel = 0;

				// Totalizadores Gerais
				BigDecimal valorTotalGeralAtrasoAte30Dias = BigDecimal.ZERO;
				BigDecimal valorTotalGeralAtraso31A90Dias = BigDecimal.ZERO;
				// BigDecimal valorTotalGeralAtraso61A90Dias = BigDecimal.ZERO;
				BigDecimal valorTotalGeralAtraso91A180Dias = BigDecimal.ZERO;
				BigDecimal valorTotalGeralAtraso181A365Dias = BigDecimal.ZERO;
				// BigDecimal valorTotalGeralAtraso366A180Dias = BigDecimal.ZERO;
				BigDecimal valorTotalGeralAtraso366DiasA5Anos = BigDecimal.ZERO;
				BigDecimal valorTotalGeralAtrasoDe5A10Anos = BigDecimal.ZERO;

				BigDecimal valorTotalGeralImovel = BigDecimal.ZERO;
				BigDecimal valorTotalGeralAtrasoCorrigidoImovel = BigDecimal.ZERO;

				Integer quantidadeTotalGeralAtrasoAte30Dias = 0;
				Integer quantidadeTotalGeralAtraso31A90Dias = 0;
				// Integer quantidadeTotalGeralAtraso61A90Dias = 0;
				Integer quantidadeTotalGeralAtraso91A180Dias = 0;
				Integer quantidadeTotalGeralAtraso181A365Dias = 0;
				// Integer quantidadeTotalGeralAtraso151A180Dias = 0;
				Integer quantidadeTotalGeralAtraso366DiasA5Anos = 0;
				Integer quantidadeTotalGeralAtrasoDe5A10Anos = 0;
				Integer quantidadeTotalGeralImovel = 0;
				Collection<Conta> colecaoContasAtrasoPorImovel = null;

				for(int i = 0; i < colecaoDadosImoveisComContaEmAtraso.size(); i++){

					Object[] linhaRetonada = colecaoDadosImoveisComContaEmAtraso.get(i);
					Object[] linhaRetonadaProxima = null;

					if(i < (colecaoDadosImoveisComContaEmAtraso.size() - 1)){

						linhaRetonadaProxima = colecaoDadosImoveisComContaEmAtraso.get(i + 1);
					}

					// Caso seja a primeira linha imprimir o cabeçário do relatório e da quebra por
					// localidade
					if(i == 0){

						if(j == 1){

							arquivoImoveis.append(Util.completaString(
											"RELAÇÃO DE IMÓVEIS PÚBLICOS COM CONTA EM ATRASO EMITIDA EM "
															+ Util.formatarDataComHora(new Date()), 100));
						}else{

							arquivoImoveis.append(Util.completaString("RELAÇÃO DE IMÓVEIS PARTICULARES COM CONTA EM ATRASO EMITIDA EM "
											+ Util.formatarDataComHora(new Date()), 100));
						}

						arquivoImoveis.append(System.getProperty("line.separator"));

						// Localidade
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Imóveis da Localidade: " + linhaRetonada[1].toString()
										+ "-" + linhaRetonada[2].toString(), " ", 80));

						arquivoImoveis.append(System.getProperty("line.separator"));

						// Inscrição Formatada
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Inscrição", " ", 20));

						// Matrícula
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Matrícula", " ", 12));

						// Situação de Cobrança
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Situação de Cobrança", " ", 50));

						// Usuário possui CPF/CNPJ
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Usuário Possui CPF/CNPJ", " ", 25));

						// Vl. Atraso até 30 Dias
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Atraso até 30 Dias", " ", 25));

						// Vl. Atraso 31 a 60 Dias
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Atraso 31 a 90 Dias", " ", 26));

						// // Vl. Atraso 61 a 90 Dias
						// arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Atraso 61 a 90 Dias",
						// " ", 26));

						// Vl. Atraso 91 a 120 Dias
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Atraso 91 a 180 Dias", " ", 27));

						// Vl. Atraso 121 a 150 Dias
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Atraso 181 a 365 Dias", " ", 28));

						// Vl. Atraso 151 a 180 Dias
						// arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Atraso 151 a 180 Dias",
						// " ", 28));

						// Vl. Atraso 181 Dias a 5 Anos
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Atraso 366 Dias a 5 Anos", " ", 31));

						// Vl. Atraso 5 a 10 Anos
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Atraso 5 a 10 Anos", " ", 25));

						// Vl. Histórico Total Até 10 Anos
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Histórico Total Até 10 Anos", " ", 36));

						// Vl. Histórico Total Até 10 Anos Corrigido
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Histórico Total Até 10 Anos Corrigido", " ", 46));

						// Vl. Total Histórico Atraso Imóvel
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Total Histórico Atraso Imóvel", " ", 36));

						// Vl. Total Corrigido Atraso Imóvel
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Total Corrigido Atraso Imóvel", " ", 36));

						// Qtd. Atraso até 30 Dias
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Qtd. Atraso até 30 Dias", " ", 25));

						// Qtd. Atraso 31 a 60 Dias
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Qtd. Atraso 31 a 90 Dias", " ", 26));

						// Qtd. Atraso 61 a 90 Dias
						// arquivoImoveis.append(Util.completarStringComValorEsquerda("Qtd. Atraso 61 a 90 Dias",
						// " ", 26));

						// Qtd. Atraso 91 a 120 Dias
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Qtd. Atraso 91 a 180 Dias", " ", 27));

						// Qtd. Atraso 121 a 150 Dias
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Qtd. Atraso 181 a 365 Dias", " ", 28));

						// Qtd. Atraso 151 a 180 Dias
						// arquivoImoveis.append(Util.completarStringComValorEsquerda("Qtd. Atraso 151 a 180 Dias",
						// " ", 28));

						// Qtd. Atraso 181 Dias a 5 Anos
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Qtd. Atraso 366 Dias a 5 Anos", " ", 31));

						// Qtd. Atraso 5 a 10 Anos
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Qtd. Atraso 5 a 10 Anos", " ", 25));

						// Qtd. Total Atraso Imóvel
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Qtd. Total Atraso Imóvel", " ", 26));

						arquivoImoveis.append(System.getProperty("line.separator"));
					}

					// Inscrição Formatada
					arquivoImoveis.append(Util.completarStringComValorEsquerda(linhaRetonada[0].toString(), " ", 20));

					// Matrícula
					arquivoImoveis.append(Util.completarStringComValorEsquerda(linhaRetonada[3].toString(), " ", 12));

					// Situação de Cobrança
					if(linhaRetonada[4] != null){

						arquivoImoveis.append(Util.completarStringComValorEsquerda(linhaRetonada[4].toString(), " ", 50));
					}else{

						arquivoImoveis.append(Util.completarStringComValorEsquerda("", " ", 50));
					}

					// Usuário possui CPF/CNPJ
					arquivoImoveis.append(Util.completarStringComValorEsquerda(linhaRetonada[5].toString(), " ", 25));

					// Val. Atraso até 30 Dias
					arquivoImoveis.append(Util.completarStringComValorEsquerda(
									Util.formatarMoedaReal(new BigDecimal(linhaRetonada[6].toString())), " ", 25));

					// Val. Atraso 31 a 90 Dias
					arquivoImoveis.append(Util.completarStringComValorEsquerda(
									Util.formatarMoedaReal(new BigDecimal(linhaRetonada[7].toString())), " ", 26));

					// // Val. Atraso 61 a 90 Dias
					// arquivoImoveis.append(Util.completarStringComValorEsquerda(
					// Util.formatarMoedaReal(new BigDecimal(linhaRetonada[8].toString())), " ",
					// 26));

					// Val. Atraso 91 a 180 Dias
					arquivoImoveis.append(Util.completarStringComValorEsquerda(
									Util.formatarMoedaReal(new BigDecimal(linhaRetonada[8].toString())), " ", 27));

					// // Val. Atraso 121 a 150 Dias
					// arquivoImoveis.append(Util.completarStringComValorEsquerda(
					// Util.formatarMoedaReal(new BigDecimal(linhaRetonada[10].toString())), " ",
					// 28));

					// Val. Atraso 181 a 365 Dias
					arquivoImoveis.append(Util.completarStringComValorEsquerda(
									Util.formatarMoedaReal(new BigDecimal(linhaRetonada[9].toString())), " ", 28));

					// Val. Atraso 366 Dias a 5 Anos
					arquivoImoveis.append(Util.completarStringComValorEsquerda(
									Util.formatarMoedaReal(new BigDecimal(linhaRetonada[10].toString())), " ", 31));

					// Val. Atraso 5 a 10 Anos
					arquivoImoveis.append(Util.completarStringComValorEsquerda(
									Util.formatarMoedaReal(new BigDecimal(linhaRetonada[11].toString())), " ", 25));

					// Val. Total Histórico Atraso Imóvel
					arquivoImoveis.append(Util.completarStringComValorEsquerda(
									Util.formatarMoedaReal(new BigDecimal(linhaRetonada[12].toString())), " ", 36));

					colecaoContasAtrasoPorImovel = fachada.pesquisarContasEmAtrasoPorImovel(Util
									.obterInteger(linhaRetonada[3].toString()));

					// [UC0216] Calcular Acrescimo por Impontualidade
					BigDecimal valorTotalCorrigidoImovel = new BigDecimal(linhaRetonada[12].toString());

					// Valor Histórico Até 10 Anos Corrigido
					BigDecimal valorTotalHistoricoAte10AnosCorrigido = BigDecimal.ZERO;

					for(Conta conta : colecaoContasAtrasoPorImovel){

						// Calcula o valor das multas cobradas para a conta
						BigDecimal valorMultasCobradas = fachada.pesquisarValorMultasCobradas(conta.getId());

						CalcularAcrescimoPorImpontualidadeHelper calcularAcrescimoPorImpontualidade = null;

						calcularAcrescimoPorImpontualidade = fachada.calcularAcrescimoPorImpontualidade(conta.getReferencia(),
										conta.getDataVencimentoConta(), null, conta.getValorTotalContaBigDecimal(), valorMultasCobradas,
										conta.getIndicadorCobrancaMulta(), sistemaParametro.getAnoMesArrecadacao().toString(),
										conta.getId(), ConstantesSistema.SIM, ConstantesSistema.SIM, ConstantesSistema.SIM);

						if(calcularAcrescimoPorImpontualidade != null){

							// adiciona valor de multa
							valorTotalCorrigidoImovel = valorTotalCorrigidoImovel.add(calcularAcrescimoPorImpontualidade
											.getValorMulta());

							// adiciona valor de juros mora
							valorTotalCorrigidoImovel = valorTotalCorrigidoImovel.add(calcularAcrescimoPorImpontualidade
											.getValorJurosMora());

							// adiciona valor de atualizacao monetaria
							valorTotalCorrigidoImovel = valorTotalCorrigidoImovel.add(calcularAcrescimoPorImpontualidade
											.getValorAtualizacaoMonetaria());

							// Verifica se a referencia da conta está com no máximo 10 anos de
							// diferença
							if(conta.getAnoMesReferenciaConta() >= anoMesReferenciaAtualMenos10Anos){
								valorTotalHistoricoAte10AnosCorrigido = valorTotalHistoricoAte10AnosCorrigido
												.add(calcularAcrescimoPorImpontualidade.getValorMulta())
												.add(calcularAcrescimoPorImpontualidade.getValorJurosMora())
												.add(calcularAcrescimoPorImpontualidade.getValorAtualizacaoMonetaria());
							}
						}
					}

					// Val. Total Corrigido Atraso Imóvel
					arquivoImoveis.append(Util.completarStringComValorEsquerda(Util.formatarMoedaReal(valorTotalCorrigidoImovel), " ", 36));

					// Qtd. Atraso até 30 Dias
					arquivoImoveis.append(Util.completarStringComValorEsquerda(linhaRetonada[13].toString(), " ", 25));

					// Qtd. Atraso 31 a 90 Dias
					arquivoImoveis.append(Util.completarStringComValorEsquerda(linhaRetonada[14].toString(), " ", 26));

					// // Qtd. Atraso 61 a 90 Dias
					// arquivoImoveis.append(Util.completarStringComValorEsquerda(linhaRetonada[17].toString(),
					// " ", 26));

					// Qtd. Atraso 91 a 180 Dias
					arquivoImoveis.append(Util.completarStringComValorEsquerda(linhaRetonada[15].toString(), " ", 27));

					// Qtd. Atraso 181 a 365 Dias
					arquivoImoveis.append(Util.completarStringComValorEsquerda(linhaRetonada[16].toString(), " ", 28));

					// // Qtd. Atraso 151 a 180 Dias
					// arquivoImoveis.append(Util.completarStringComValorEsquerda(linhaRetonada[20].toString(),
					// " ", 28));

					// Qtd. Atraso 366 Dias a 5 Anos
					arquivoImoveis.append(Util.completarStringComValorEsquerda(linhaRetonada[17].toString(), " ", 31));

					// Qtd. Atraso 5 a 10 Anos
					arquivoImoveis.append(Util.completarStringComValorEsquerda(linhaRetonada[18].toString(), " ", 25));

					// Qtd. Total Atraso Imóvel
					arquivoImoveis.append(Util.completarStringComValorEsquerda(linhaRetonada[19].toString(), " ", 26));

					arquivoImoveis.append(System.getProperty("line.separator"));

					// Acumula Totalização Por Localidade
					valorTotalLocalidadeAtrasoAte30Dias = valorTotalLocalidadeAtrasoAte30Dias.add(new BigDecimal(linhaRetonada[6]
									.toString()));
					valorTotalLocalidadeAtraso31A90Dias = valorTotalLocalidadeAtraso31A90Dias.add(new BigDecimal(linhaRetonada[7]
									.toString()));
					// valorTotalLocalidadeAtraso61A90Dias =
					// valorTotalLocalidadeAtraso61A90Dias.add(new BigDecimal(linhaRetonada[8]
					// .toString()));
					valorTotalLocalidadeAtraso91A180Dias = valorTotalLocalidadeAtraso91A180Dias.add(new BigDecimal(linhaRetonada[8]
									.toString()));
					valorTotalLocalidadeAtraso181A365Dias = valorTotalLocalidadeAtraso181A365Dias.add(new BigDecimal(linhaRetonada[9]
									.toString()));
					// valorTotalLocalidadeAtraso151A180Dias =
					// valorTotalLocalidadeAtraso151A180Dias.add(new BigDecimal(linhaRetonada[11]
					// .toString()));
					valorTotalLocalidadeAtraso366DiasA5Anos = valorTotalLocalidadeAtraso366DiasA5Anos.add(new BigDecimal(linhaRetonada[10]
									.toString()));
					valorTotalLocalidadeAtrasoDe5A10Anos = valorTotalLocalidadeAtrasoDe5A10Anos.add(new BigDecimal(linhaRetonada[11]
									.toString()));

					// Valor Total Histórico Até 10 Anos
					valorTotalHistoricoAte10Anos = valorTotalLocalidadeAtrasoAte30Dias.add(valorTotalLocalidadeAtraso31A90Dias)
									.add(valorTotalLocalidadeAtraso91A180Dias).add(valorTotalLocalidadeAtraso181A365Dias)
									.add(valorTotalLocalidadeAtraso366DiasA5Anos).add(valorTotalLocalidadeAtrasoDe5A10Anos);

					valorTotalLocalidadeImovel = valorTotalLocalidadeImovel.add(new BigDecimal(linhaRetonada[12].toString()));

					valorTotalLocalidadeAtrasoCorrigidoImovel = valorTotalLocalidadeAtrasoCorrigidoImovel.add(valorTotalCorrigidoImovel);

					quantidadeTotalLocalidadeAtrasoAte30Dias = quantidadeTotalLocalidadeAtrasoAte30Dias.intValue()
									+ Util.obterInteger(linhaRetonada[13].toString()).intValue();
					quantidadeTotalLocalidadeAtraso31A90Dias = quantidadeTotalLocalidadeAtraso31A90Dias.intValue()
									+ Util.obterInteger(linhaRetonada[14].toString()).intValue();
					// quantidadeTotalLocalidadeAtraso61A90Dias =
					// quantidadeTotalLocalidadeAtraso61A90Dias.intValue()
					// + Util.obterInteger(linhaRetonada[17].toString()).intValue();
					quantidadeTotalLocalidadeAtraso91A180Dias = quantidadeTotalLocalidadeAtraso91A180Dias.intValue()
									+ Util.obterInteger(linhaRetonada[15].toString()).intValue();
					quantidadeTotalLocalidadeAtraso181A365Dias = quantidadeTotalLocalidadeAtraso181A365Dias.intValue()
									+ Util.obterInteger(linhaRetonada[16].toString()).intValue();
					// quantidadeTotalLocalidadeAtraso151A180Dias =
					// quantidadeTotalLocalidadeAtraso151A180Dias.intValue()
					// + Util.obterInteger(linhaRetonada[20].toString()).intValue();
					quantidadeTotalLocalidadeAtraso366DiasA5Anos = quantidadeTotalLocalidadeAtraso366DiasA5Anos.intValue()
									+ Util.obterInteger(linhaRetonada[17].toString()).intValue();
					quantidadeTotalLocalidadeAtrasoDe5A10Anos = quantidadeTotalLocalidadeAtrasoDe5A10Anos.intValue()
									+ Util.obterInteger(linhaRetonada[18].toString()).intValue();
					quantidadeTotalLocalidadeImovel = quantidadeTotalLocalidadeImovel.intValue()
									+ Util.obterInteger(linhaRetonada[19].toString()).intValue();

					if((i == (colecaoDadosImoveisComContaEmAtraso.size() - 1))
									|| !linhaRetonada[1].toString().equals(linhaRetonadaProxima[1].toString())){

						// Totais Localidade
						arquivoImoveis.append(Util.completarStringComValorEsquerda("Totais da Localidade:" + linhaRetonada[1].toString()
										+ "-" + linhaRetonada[2].toString(), " ", 80));

						// Completa com 27 brancos
						arquivoImoveis.append(Util.completaString("", 27));

						// Val. Atraso até 30 Dias
						arquivoImoveis.append(Util.completarStringComValorEsquerda(
										Util.formatarMoedaReal(valorTotalLocalidadeAtrasoAte30Dias), " ", 25));

						// Val. Atraso 31 a 60 Dias
						arquivoImoveis.append(Util.completarStringComValorEsquerda(
										Util.formatarMoedaReal(valorTotalLocalidadeAtraso31A90Dias), " ", 26));

						// // Val. Atraso 61 a 90 Dias
						// arquivoImoveis.append(Util.completarStringComValorEsquerda(Util.formatarMoedaReal(valorTotalLocalidadeAtraso61A90Dias),
						// " ", 26));

						// Val. Atraso 91 a 120 Dias
						arquivoImoveis.append(Util.completarStringComValorEsquerda(
										Util.formatarMoedaReal(valorTotalLocalidadeAtraso91A180Dias), " ", 27));

						// Val. Atraso 121 a 150 Dias
						arquivoImoveis.append(Util.completarStringComValorEsquerda(
										Util.formatarMoedaReal(valorTotalLocalidadeAtraso181A365Dias), " ", 28));

						// // Val. Atraso 151 a 180 Dias
						// arquivoImoveis.append(Util.completarStringComValorEsquerda(Util.formatarMoedaReal(valorTotalLocalidadeAtraso151A180Dias),
						// " ", 28));

						// Val. Atraso 181 Dias a 5 Anos
						arquivoImoveis.append(Util.completarStringComValorEsquerda(
										Util.formatarMoedaReal(valorTotalLocalidadeAtraso366DiasA5Anos), " ", 31));

						// Val. Atraso 5 a 10 Anos
						arquivoImoveis.append(Util.completarStringComValorEsquerda(
										Util.formatarMoedaReal(valorTotalLocalidadeAtrasoDe5A10Anos), " ", 25));

						// Val. Histórico Total Até 10 Anos
						arquivoImoveis.append(Util.completarStringComValorEsquerda(Util.formatarMoedaReal(valorTotalHistoricoAte10Anos),
										" ", 36));

						// Val. Histórico Total Até 10 Anos Corrigido
						arquivoImoveis.append(Util.completarStringComValorEsquerda(
										Util.formatarMoedaReal(valorTotalHistoricoAte10AnosCorrigido), " ", 46));

						// Val. Total Histórico Atraso Imóvel
						arquivoImoveis.append(Util.completarStringComValorEsquerda(Util.formatarMoedaReal(valorTotalLocalidadeImovel), " ",
										36));

						// Val. Total Corrigido Atraso Imóvel
						arquivoImoveis.append(Util.completarStringComValorEsquerda(
										Util.formatarMoedaReal(valorTotalLocalidadeAtrasoCorrigidoImovel), " ", 36));

						// Qtd. Atraso até 30 Dias
						arquivoImoveis.append(Util.completarStringComValorEsquerda(quantidadeTotalLocalidadeAtrasoAte30Dias.toString(),
										" ", 25));

						// Qtd. Atraso 31 a 60 Dias
						arquivoImoveis.append(Util.completarStringComValorEsquerda(quantidadeTotalLocalidadeAtraso31A90Dias.toString(),
										" ", 26));

						// // Qtd. Atraso 61 a 90 Dias
						// arquivoImoveis.append(Util.completarStringComValorEsquerda(quantidadeTotalLocalidadeAtraso61A90Dias.toString(),
						// " ", 26));

						// Qtd. Atraso 91 a 120 Dias
						arquivoImoveis.append(Util.completarStringComValorEsquerda(quantidadeTotalLocalidadeAtraso91A180Dias.toString(),
										" ", 27));

						// Qtd. Atraso 121 a 150 Dias
						arquivoImoveis.append(Util.completarStringComValorEsquerda(quantidadeTotalLocalidadeAtraso181A365Dias.toString(),
										" ", 28));

						// // Qtd. Atraso 151 a 180 Dias
						// arquivoImoveis.append(Util.completarStringComValorEsquerda(quantidadeTotalLocalidadeAtraso151A180Dias.toString(),
						// " ", 28));

						// Qtd. Atraso 181 Dias a 5 Anos
						arquivoImoveis.append(Util.completarStringComValorEsquerda(quantidadeTotalLocalidadeAtraso366DiasA5Anos.toString(),
										" ", 31));

						// Qtd. Atraso 5 a 10 Anos
						arquivoImoveis.append(Util.completarStringComValorEsquerda(quantidadeTotalLocalidadeAtrasoDe5A10Anos.toString(),
										" ", 25));

						// Qtd. Total Atraso Imóvel
						arquivoImoveis.append(Util.completarStringComValorEsquerda(quantidadeTotalLocalidadeImovel.toString(), " ", 26));

						// Acumula Totalização Geral
						valorTotalGeralAtrasoAte30Dias = valorTotalGeralAtrasoAte30Dias.add(valorTotalLocalidadeAtrasoAte30Dias);
						valorTotalGeralAtraso31A90Dias = valorTotalGeralAtraso31A90Dias.add(valorTotalLocalidadeAtraso31A90Dias);
						// valorTotalGeralAtraso61A90Dias =
						// valorTotalGeralAtraso61A90Dias.add(valorTotalLocalidadeAtraso61A90Dias);
						valorTotalGeralAtraso91A180Dias = valorTotalGeralAtraso91A180Dias.add(valorTotalLocalidadeAtraso91A180Dias);
						valorTotalGeralAtraso181A365Dias = valorTotalGeralAtraso181A365Dias.add(valorTotalLocalidadeAtraso181A365Dias);
						// valorTotalGeralAtraso151A180Dias =
						// valorTotalGeralAtraso151A180Dias.add(valorTotalLocalidadeAtraso151A180Dias);
						valorTotalGeralAtraso366DiasA5Anos = valorTotalGeralAtraso366DiasA5Anos
										.add(valorTotalLocalidadeAtraso366DiasA5Anos);
						valorTotalGeralAtrasoDe5A10Anos = valorTotalGeralAtrasoDe5A10Anos.add(valorTotalLocalidadeAtrasoDe5A10Anos);
						valorTotalGeralImovel = valorTotalGeralImovel.add(valorTotalLocalidadeImovel);
						valorTotalGeralAtrasoCorrigidoImovel = valorTotalGeralAtrasoCorrigidoImovel
										.add(valorTotalLocalidadeAtrasoCorrigidoImovel);

						quantidadeTotalGeralAtrasoAte30Dias = quantidadeTotalGeralAtrasoAte30Dias.intValue()
										+ quantidadeTotalLocalidadeAtrasoAte30Dias.intValue();
						quantidadeTotalGeralAtraso31A90Dias = quantidadeTotalGeralAtraso31A90Dias.intValue()
										+ quantidadeTotalLocalidadeAtraso31A90Dias.intValue();
						// quantidadeTotalGeralAtraso61A90Dias =
						// quantidadeTotalGeralAtraso61A90Dias.intValue() +
						// quantidadeTotalLocalidadeAtraso61A90Dias.intValue();
						quantidadeTotalGeralAtraso91A180Dias = quantidadeTotalGeralAtraso91A180Dias.intValue()
										+ quantidadeTotalLocalidadeAtraso91A180Dias.intValue();
						quantidadeTotalGeralAtraso181A365Dias = quantidadeTotalGeralAtraso181A365Dias.intValue()
										+ quantidadeTotalLocalidadeAtraso181A365Dias.intValue();
						// quantidadeTotalGeralAtraso151A180Dias =
						// quantidadeTotalGeralAtraso151A180Dias.intValue() +
						// quantidadeTotalLocalidadeAtraso151A180Dias.intValue();
						quantidadeTotalGeralAtraso366DiasA5Anos = quantidadeTotalGeralAtraso366DiasA5Anos.intValue()
										+ quantidadeTotalLocalidadeAtraso366DiasA5Anos.intValue();
						quantidadeTotalGeralAtrasoDe5A10Anos = quantidadeTotalGeralAtrasoDe5A10Anos.intValue()
										+ quantidadeTotalLocalidadeAtrasoDe5A10Anos.intValue();
						quantidadeTotalGeralImovel = quantidadeTotalGeralImovel.intValue() + quantidadeTotalLocalidadeImovel.intValue();

						// Zera os totalizadores da localidade
						valorTotalLocalidadeAtrasoAte30Dias = BigDecimal.ZERO;
						valorTotalLocalidadeAtraso31A90Dias = BigDecimal.ZERO;
						// valorTotalLocalidadeAtraso61A90Dias = BigDecimal.ZERO;
						valorTotalLocalidadeAtraso91A180Dias = BigDecimal.ZERO;
						valorTotalLocalidadeAtraso181A365Dias = BigDecimal.ZERO;
						// valorTotalLocalidadeAtraso151A180Dias = BigDecimal.ZERO;
						valorTotalLocalidadeAtraso366DiasA5Anos = BigDecimal.ZERO;
						valorTotalLocalidadeAtrasoDe5A10Anos = BigDecimal.ZERO;
						valorTotalLocalidadeImovel = BigDecimal.ZERO;
						valorTotalLocalidadeAtrasoCorrigidoImovel = BigDecimal.ZERO;
						quantidadeTotalLocalidadeAtrasoAte30Dias = 0;
						quantidadeTotalLocalidadeAtraso31A90Dias = 0;
						// quantidadeTotalLocalidadeAtraso61A90Dias = 0;
						quantidadeTotalLocalidadeAtraso91A180Dias = 0;
						quantidadeTotalLocalidadeAtraso181A365Dias = 0;
						// quantidadeTotalLocalidadeAtraso151A180Dias = 0;
						quantidadeTotalLocalidadeAtraso366DiasA5Anos = 0;
						quantidadeTotalLocalidadeAtrasoDe5A10Anos = 0;
						quantidadeTotalLocalidadeImovel = 0;

						valorTotalHistoricoAte10Anos = BigDecimal.ZERO;

						arquivoImoveis.append(System.getProperty("line.separator"));

						if((i == (colecaoDadosImoveisComContaEmAtraso.size() - 1))){

							// Totais Gerais
							if(j == 1){
								arquivoImoveis.append(Util.completarStringComValorEsquerda("Totais Gerais dos Imóveis Públicos:", " ", 80));
							}else{

								arquivoImoveis.append(Util.completarStringComValorEsquerda("Totais Gerais dos Imóveis Particulares:", " ",
												80));
							}

							// Completa com 27 brancos
							arquivoImoveis.append(Util.completaString("", 27));

							// Val. Atraso até 30 Dias
							arquivoImoveis.append(Util.completarStringComValorEsquerda(
											Util.formatarMoedaReal(valorTotalGeralAtrasoAte30Dias), " ", 25));

							// Val. Atraso 31 a 60 Dias
							arquivoImoveis.append(Util.completarStringComValorEsquerda(
											Util.formatarMoedaReal(valorTotalGeralAtraso31A90Dias), " ", 26));

							// Val. Atraso 61 a 90 Dias
							// arquivoImoveis.append(Util.completarStringComValorEsquerda(Util.formatarMoedaReal(valorTotalGeralAtraso61A90Dias),
							// " ", 26));

							// Val. Atraso 91 a 120 Dias
							arquivoImoveis.append(Util.completarStringComValorEsquerda(
											Util.formatarMoedaReal(valorTotalGeralAtraso91A180Dias), " ", 27));

							// Val. Atraso 121 a 150 Dias
							arquivoImoveis.append(Util.completarStringComValorEsquerda(
											Util.formatarMoedaReal(valorTotalGeralAtraso181A365Dias), " ", 28));
							//
							// // Val. Atraso 151 a 180 Dias
							// arquivoImoveis.append(Util.completarStringComValorEsquerda(Util.formatarMoedaReal(valorTotalGeralAtraso151A180Dias),
							// " ", 28));

							// Val. Atraso 181 Dias a 5 Anos
							arquivoImoveis.append(Util.completarStringComValorEsquerda(
											Util.formatarMoedaReal(valorTotalGeralAtraso366DiasA5Anos), " ", 31));

							// Val. Atraso 5 a 10 Anos
							arquivoImoveis.append(Util.completarStringComValorEsquerda(
											Util.formatarMoedaReal(valorTotalGeralAtrasoDe5A10Anos), " ", 25));

							// Val. Total Histórico Até 10 Anos
							arquivoImoveis.append(Util.completarStringComValorEsquerda(
											Util.formatarMoedaReal(valorTotalHistoricoAte10Anos), " ", 36));

							// Val. Total Histórico Até 10 Anos Corrigido
							arquivoImoveis.append(Util.completarStringComValorEsquerda(
											Util.formatarMoedaReal(valorTotalHistoricoAte10AnosCorrigido), " ", 46));

							// Val. Total Histórico Atraso Imóvel
							arquivoImoveis.append(Util.completarStringComValorEsquerda(Util.formatarMoedaReal(valorTotalGeralImovel), " ",
											36));

							// Val. Total Corrigido Atraso Imóvel
							arquivoImoveis.append(Util.completarStringComValorEsquerda(
											Util.formatarMoedaReal(valorTotalGeralAtrasoCorrigidoImovel), " ", 36));

							// Qtd. Atraso até 30 Dias
							arquivoImoveis.append(Util.completarStringComValorEsquerda(quantidadeTotalGeralAtrasoAte30Dias.toString(), " ",
											25));

							// Qtd. Atraso 31 a 60 Dias
							arquivoImoveis.append(Util.completarStringComValorEsquerda(quantidadeTotalGeralAtraso31A90Dias.toString(), " ",
											26));

							// // Qtd. Atraso 61 a 90 Dias
							// arquivoImoveis.append(Util.completarStringComValorEsquerda(quantidadeTotalGeralAtraso61A90Dias.toString(),
							// " ", 26));

							// Qtd. Atraso 91 a 120 Dias
							arquivoImoveis.append(Util.completarStringComValorEsquerda(quantidadeTotalGeralAtraso91A180Dias.toString(),
											" ", 27));

							// Qtd. Atraso 121 a 150 Dias
							arquivoImoveis.append(Util.completarStringComValorEsquerda(quantidadeTotalGeralAtraso181A365Dias.toString(),
											" ", 28));
							//
							// // Qtd. Atraso 151 a 180 Dias
							// arquivoImoveis.append(Util.completarStringComValorEsquerda(quantidadeTotalGeralAtraso151A180Dias.toString(),
							// " ", 28));

							// Qtd. Atraso 181 Dias a 5 Anos
							arquivoImoveis.append(Util.completarStringComValorEsquerda(quantidadeTotalGeralAtraso366DiasA5Anos.toString(),
											" ", 31));

							// Qtd. Atraso 5 a 10 Anos
							arquivoImoveis.append(Util.completarStringComValorEsquerda(quantidadeTotalGeralAtrasoDe5A10Anos.toString(),
											" ", 25));

							// Qtd. Total Atraso Imóvel
							arquivoImoveis.append(Util.completarStringComValorEsquerda(quantidadeTotalGeralImovel.toString(), " ", 26));

							arquivoImoveis.append(System.getProperty("line.separator"));
						}else{

							// Localidade
							arquivoImoveis.append(Util.completarStringComValorEsquerda(
											"Imóveis da Localidade: " + linhaRetonadaProxima[1].toString() + "-"
															+ linhaRetonadaProxima[2].toString(), " ", 100));

							arquivoImoveis.append(System.getProperty("line.separator"));

							// Inscrição Formatada
							arquivoImoveis.append(Util.completarStringComValorEsquerda("Inscrição", " ", 20));

							// Matrícula
							arquivoImoveis.append(Util.completarStringComValorEsquerda("Matrícula", " ", 12));

							// Situação de Cobrança
							arquivoImoveis.append(Util.completarStringComValorEsquerda("Situação de Cobrança", " ", 50));

							// Usuário possui CPF/CNPJ
							arquivoImoveis.append(Util.completarStringComValorEsquerda("Usuário Possui CPF/CNPJ", " ", 25));

							// Vl. Atraso até 30 Dias
							arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Atraso até 30 Dias", " ", 25));

							// Vl. Atraso 31 a 60 Dias
							arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Atraso 31 a 90 Dias", " ", 26));

							// // Vl. Atraso 61 a 90 Dias
							// arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Atraso 61 a 90 Dias",
							// " ", 26));

							// Vl. Atraso 91 a 120 Dias
							arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Atraso 91 a 180 Dias", " ", 27));
							//
							// // Vl. Atraso 121 a 150 Dias
							// arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Atraso 121 a 150 Dias",
							// " ", 28));

							// Vl. Atraso 151 a 180 Dias
							arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Atraso 181 a 365 Dias", " ", 28));

							// Vl. Atraso 181 Dias a 5 Anos
							arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Atraso 366 Dias a 5 Anos", " ", 31));

							// Vl. Atraso 5 a 10 Anos
							arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Atraso 5 a 10 Anos", " ", 25));

							// Vl. Histórico Total Até 10 Anos
							arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Histórico Total Até 10 Anos", " ", 36));

							// Vl. Histórico Total Até 10 Anos Corrigido
							arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Histórico Total Até 10 Anos Corrigido", " ",
											46));

							// Vl. Total Histórico Atraso Imóvel
							arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Total Histórico Atraso Imóvel", " ", 36));

							// Vl. Total Corrigido Atraso Imóvel
							arquivoImoveis.append(Util.completarStringComValorEsquerda("Val. Total Corrigido Atraso Imóvel", " ", 36));

							// Qtd. Atraso até 30 Dias
							arquivoImoveis.append(Util.completarStringComValorEsquerda("Qtd. Atraso até 30 Dias", " ", 25));

							// Qtd. Atraso 31 a 60 Dias
							arquivoImoveis.append(Util.completarStringComValorEsquerda("Qtd. Atraso 31 a 90 Dias", " ", 26));

							// // Qtd. Atraso 61 a 90 Dias
							// arquivoImoveis.append(Util.completarStringComValorEsquerda("Qtd. Atraso 61 a 90 Dias",
							// " ", 26));

							// Qtd. Atraso 91 a 120 Dias
							arquivoImoveis.append(Util.completarStringComValorEsquerda("Qtd. Atraso 91 a 180 Dias", " ", 27));

							// Qtd. Atraso 121 a 150 Dias
							arquivoImoveis.append(Util.completarStringComValorEsquerda("Qtd. Atraso 181 a 365 Dias", " ", 28));

							// // Qtd. Atraso 151 a 180 Dias
							// arquivoImoveis.append(Util.completarStringComValorEsquerda("Qtd. Atraso 151 a 180 Dias",
							// " ", 28));

							// Qtd. Atraso 181 Dias a 5 Anos
							arquivoImoveis.append(Util.completarStringComValorEsquerda("Qtd. Atraso 366 Dias a 5 Anos", " ", 31));

							// Qtd. Atraso 5 a 10 Anos
							arquivoImoveis.append(Util.completarStringComValorEsquerda("Qtd. Atraso 5 a 10 Anos", " ", 25));

							// Qtd. Total Atraso Imóvel
							arquivoImoveis.append(Util.completarStringComValorEsquerda("Qtd. Total Atraso Imóvel", " ", 26));

							arquivoImoveis.append(System.getProperty("line.separator"));
						}
					}

					if(j == 1){

						log.info("Quantidade de Imóveis Públicos Já Processados: " + (i + 1));
					}else{

						log.info("Quantidade de Imóveis Particulares Já Processados: " + (i + 1));
					}
				}

				if(j == 1){

					arquivoImoveisPublicos = arquivoImoveis;
				}else{

					arquivoImoveisParticulares = arquivoImoveis;
				}
			}

			retorno = this.getBytesFromFileZip(arquivoImoveisPublicos,
							"relacao_imoveis_publicos_com_conta_em_atraso_" + Util.formatarDataSemBarra(new Date()) + ".txt",
							arquivoImoveisParticulares,
							"relacao_imoveis_particulares_com_conta_em_atraso_" + Util.formatarDataSemBarra(new Date()) + ".txt");

			this.persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_DADOS_IMOVEIS_COM_CONTA_EM_ATRASO, idFuncionalidadeIniciada,
							"rel_imoveis_conta_em_atraso_" + Util.formatarDataSemBarra(new Date()));
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}catch(IOException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}catch(Exception e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno;
	}

	private byte[] getBytesFromFileZip(StringBuffer conteudoArquivoPublicos, String nomeArquivoPublicos,
					StringBuffer conteudoArquivoParticulares, String nomeArquivoParticulares) throws IOException, Exception{

		File compactado = new File("arquivoImoveisComContaAtraso" + Util.formatarDataSemBarra(new Date()) + ".zip");
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(compactado));

		File arquivoPublicos = new File(nomeArquivoPublicos);

		BufferedWriter outPublicos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivoPublicos.getAbsolutePath())));
		outPublicos.write(conteudoArquivoPublicos.toString());
		outPublicos.flush();
		outPublicos.close();

		File arquivoParticulares = new File(nomeArquivoParticulares);

		BufferedWriter outParticulares = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
						arquivoParticulares.getAbsolutePath())));
		outParticulares.write(conteudoArquivoParticulares.toString());
		outParticulares.flush();
		outParticulares.close();

		ZipUtil.adicionarArquivos(zos, arquivoPublicos, arquivoParticulares);

		zos.close();

		arquivoPublicos.delete();
		arquivoParticulares.delete();

		byte[] retorno = this.getBytesFromFile(compactado);

		compactado.delete();

		return retorno;
	}

	@SuppressWarnings("resource")
	private byte[] getBytesFromFile(File file) throws IOException{

		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while(offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0){

			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if(offset < bytes.length){

			throw new IOException("Could not completely read file " + file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioDadosImoveisComContaEmAtraso", this);
	}

}