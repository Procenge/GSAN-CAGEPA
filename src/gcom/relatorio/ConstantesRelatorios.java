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

package gcom.relatorio;

import gcom.util.SistemaException;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Interface que contém os caminhos dos arquivos compilados dos relatórios
 * 
 * @author Sávio Luiz
 */
public class ConstantesRelatorios {

	public static final String RELATORIO_BAIRRO_MANTER = "/relatorioManterBairro.jasper";

	public static final String RELATORIO_SUBBACIA_MANTER = "/relatorioManterSubBacia.jasper";

	public static final String RELATORIO_ZONA_ABASTECIMENTO_MANTER = "/relatorioManterZonaAbastecimento.jasper";

	public static final String RELATORIO_UNIDADE_OPERACIONAL_MANTER = "/relatorioManterUnidadeOperacional.jasper";

	public static final String RELATORIO_MUNICIPIO_MANTER = "/relatorioManterMunicipio.jasper";

	public static final String RELATORIO_LOGRADOURO_MANTER = "/relatorioManterLogradouro.jasper";

	public static final String RELATORIO_LOCALIDADE_MANTER = "/relatorioManterLocalidade.jasper";

	public static final String RELATORIO_SETOR_COMERCIAL_MANTER = "/relatorioManterSetorComercial.jasper";

	public static final String RELATORIO_QUADRA_MANTER = "/relatorioManterQuadra1.jasper";

	public static final String RELATORIO_TARIFA_SOCIAL_MANTER = "/relatorioManterTipoCartaoTarifaSocial.jasper";

	public static final String RELATORIO_HIDROMETRO_MANTER = "/relatorioManterHidrometro.jasper";

	public static final String RELATORIO_CLIENTE_MANTER = "/relatorioManterCliente.jasper";

	public static final String RELATORIO_CLIENTE_OUTROS_CRITERIOS_MANTER = "/relatorioManterClienteOutrosCriterios.jasper";

	public static final String RELATORIO_IMOVEL_MANTER = "/relatorioManterImovel.jasper";

	public static final String RELATORIO_IMOVEL_ENDERECO = "/relatorioImovelEndereco.jasper";

	public static final String RELATORIO_IMOVEL_ENDERECO_COM_INSCRICAO = "/relatorioImovelEnderecoComInscricao.jasper";

	public static final String RELATORIO_CADASTRO_CONSUMIDORES_INSCRICAO = "/relatorioCadastroConsumidoresInscricao.jasper";

	public static final String RELATORIO_IMOVEL_OUTROS_CRITERIOS_MANTER = "/relatorioManterImovelOutrosCriterios.jasper";

	public static final String RELATORIO_DADOS_ECONOMIA_IMOVEL = "/relatorioManterDadosEconomiasImovel.jasper";

	public static final String RELATORIO_DADOS_TARIFA_SOCIAL = "/relatorioManterDadosTarifaSocial.jasper";

	public static final String RELATORIO_OPERACAO = "/relatorioOperacao.jasper";

	public static final String RELATORIO_OPERACAO_CONSULTAR = "/relatorioConsultarOperacao.jasper";

	public static final String RELATORIO_OPERACAO_CONSULTAR_DETALHADO_ARRAY = "/relatorioArrayDetalharConsultaOperacao.jasper";

	public static final String RELATORIO_OPERACAO_CONSULTAR_DETALHADO_ARRAY_2 = "/relatorioArrayDetalharConsultaOperacao2.jasper";

	public static final String RELATORIO_RESUMO_FATURAMENTO = "/relatorioResumoFaturamento.jasper";

	public static final String RELATORIO_REGISTRAR_LEITURAS_ANORMALIDADES = "/relatorioRegistrarLeiturasAnormalidades.jasper";

	public static final String RELATORIO_MOVIMENTO_DEBITO_AUTOMATICO_BANCO = "/relatorioMovimentoDebitoAutomaticoBanco.jasper";

	public static final String RELATORIO_ACOMPANHAMENTO_FATURAMENTO = "/relatorioAcompanhamentoFaturamento.jasper";

	public static final String RELATORIO_DEVOLUCAO = "/relatorioDevolucoes.jasper";

	public static final String RELATORIO_RESUMO_ARRECADACAO = "/relatorioResumoArrecadacao.jasper";

	public static final String RELATORIO_GERAR_RELACAO_DEBITOS = "/relatorioGerarRelacaoDebitosResumido.jasper";

	public static final String RELATORIO_CONTAS_RECEBER_VALORES_CORRIGIDOS = "/relatorioGerarContasReceberValoresCorrigidos.jasper";

	public static final String RELATORIO_TOTAL_CONTAS_EMITIDAS_LOCALIDADE = "/relatorioTotalContasEmitidasLocalidade.jasper";

	public static final String RELATORIO_RESUMO_ANORMALIDADE_LEITURA = "/relatorioResumoAnormalidadeLeitura.jasper";

	public static final String RELATORIO_RESUMO_ANORMALIDADE_CONSUMO = "/relatorioResumoAnormalidadesConsumo.jasper";

	public static final String RELATORIO_RESUMO_FATURAMENTO_SITUACAO_ESPECIAL = "/relatorioResumoFaturamentoSituacaoEspecial.jasper";

	public static final String RELATORIO_ANALISE_FATURAMENTO = "/relatorioAnaliseFaturamento.jasper";

	public static final String RELATORIO_PAGAMENTO = "/relatorioPagamentos.jasper";

	public static final String RELATORIO_PAGAMENTO_TOTALIZADO_POR_DATA = "/relatorioPagamentosTotalizadoPorData.jasper";

	public static final String RELATORIO_PAGAMENTO_CONSULTAR_IMOVEL = "/relatorioPagamentosConsultarImovel.jasper";

	public static final String RELATORIO_PAGAMENTO_CONSULTAR_AVISO_BANCARIO = "/relatorioPagamentosConsultarAvisoBancario.jasper";

	public static final String RELATORIO_SUBCATEGORIA_MANTER = "/relatorioManterSubCategoria.jasper";

	public static final String RELATORIO_ROTA_MANTER = "/relatorioManterRota.jasper";

	public static final String RELATORIO_CATEGORIA_MANTER = "/relatorioManterCategoria.jasper";

	public static final String RELATORIO_CRONOGRAMA_FATURAMENTO_MANTER = "/relatorioManterCronogramaFaturamento.jasper";

	public static final String RELATORIO_MENSAGEM_CONTA_MANTER = "/relatorioManterMensagemConta.jasper";

	public static final String RELATORIO_CRONOGRAMA_COBRANCA_MANTER = "/relatorioManterCronogramaCobranca.jasper";

	public static final String RELATORIO_RESOLUCAO_DIRETORIA_MANTER = "/relatorioManterResolucaoDiretoria.jasper";

	public static final String RELATORIO_CRITERIO_COBRANCA_MANTER = "/relatorioManterCriterioCobranca.jasper";

	public static final String RELATORIO_PERFIL_PARCELAMENTO_MANTER = "/relatorioManterPerfilParcelamento.jasper";

	public static final String RELATORIO_AVISO_BANCARIO_MANTER = "/relatorioManterAvisoBancario.jasper";

	public static final String RELATORIO_MOVIMENTO_ARRECADADOR_MANTER = "/relatorioManterMovimentoArrecadadores.jasper";

	public static final String RELATORIO_GUIA_DEVOLUCAO_MANTER = "/relatorioManterGuiaDevolucao.jasper";

	public static final String RELATORIO_EXTRATO_DEBITO = "/relatorioExtratoDebito.jasper";

	public static final String RELATORIO_CONSULTAR_REGISTRO_ATENDIMENTO = "/relatorioConsultarRegistroAtendimento.jasper";

	public static final String RELATORIO_2_VIA_CONTA = "/relatorio2ViaConta.jasper";

	public static final String RELATORIO_PARCELAMENTO = "/relatorioParcelamento.jasper";

	public static final String RELATORIO_PARCELAMENTO_CAER = "/relatorioParcelamentoCaer.jasper";

	public static final String RELATORIO_GUIA_PAGAMENTO_EMITIR = "/relatorioEmitirGuiaPagamento.jasper";

	public static final String RELATORIO_GUIA_DEVOLUCAO = "/relatorioGuiaDevolucao.jasper";

	public static final String RELATORIO_ROTEIRO_PROGRAMACAO_LAYOUT_PAISAGEM = "/relatorioRoteiroProgramacaoLayoutPaisagem.jasper";

	public static final String RELATORIO_ROTEIRO_PROGRAMACAO_LAYOUT_RETRATO = "/relatorioRoteiroProgramacaoLayoutRetrato.jasper";

	public static final String RELATORIO_CONSULTAR_REGISTRO_ATENDIMENTO_VIA_CLIENTE = "/relatorioConsultarRegistroAtendimentoViaCliente.jasper";

	public static final String RELATORIO_ORDEM_SERVICO = "/relatorioOrdemServico.jasper";

	public static final String RELATORIO_ORDEM_SERVICO_PADRAO_COM_OCORRENCIA = "/relatorioOrdemServicoPadraoComOcorrencia.jasper";

	public static final String RELATORIO_ORDEM_SERVICO_MODELO_2 = "/relatorioOrdemServicoModelo2.jasper";

	public static final String RELATORIO_ORDEM_SERVICO_CAERN = "/relatorioOrdemServicoCAERN.jasper";

	public static final String RELATORIO_NUMERACAO_RA_MANUAL = "/relatorioGerarNumeracaoRAManual.jasper";

	public static final String RELATORIO_ACOMPANHAMENTO_EXECUCAO_OS = "/relatorioAcompanhamentoExecucaoOS.jasper";

	public static final String RELATORIO_PRODUTIVIDADE_EQUIPE = "/relatorioProdutividadeEquipe.jasper";

	public static final String RELATORIO_OS_NAO_EXECUTADA_EQUIPE = "/relatorioOSNaoExecutadaPorEquipe.jasper";

	public static final String RELATORIO_MOVIMENTO_ARRECADADOR = "/relatorioRegistrarMovimentoArrecadadores.jasper";

	public static final String RELATORIO_FATURAMENTO_LIGACOES_MEDICAO_INDIVIDUALIZADA = "/relatorioFaturamentoLigacoesMedicaoIndividualizada.jasper";

	public static final String RELATORIO_CONTAS_EMITIDAS = "/relatorioContasEmitidas.jasper";

	public static final String RELATORIO_MAPA_CONTROLE_CONTA = "/relatorioMapaControleConta.jasper";

	public static final String RELATORIO_MEDICAO_CONSUMO_LIGACAO_AGUA = "/relatorioMedicaoConsumoLigacaoAgua.jasper";

	public static final String RELATORIO_RESUMO_CONTA_LOCALIDADE = "/relatorioResumoContaLocalidade.jasper";

	public static final String RELATORIO_RESUMO_IMOVEL_MICROMEDICAO = "/relatorioResumoImovelMicromedicao.jasper";

	public static final String RELATORIO_2_VIA_CONTA_TIPO_2 = "/relatorio2ViaContaTipo2.jasper";

	public static final String RELATORIO_CONSULTAR_DEBITOS = "/relatorioConsultarDebitos.jasper";

	public static final String RELATORIO_CONSULTAR_DEBITOS_RESUMIDO = "/relatorioConsultarDebitosResumido.jasper";

	public static final String RELATORIO_CONSULTAR_DEBITOS_CLIENTE = "/relatorioConsultarDebitosCliente.jasper";

	public static final String RELATORIO_CONSULTAR_DEBITOS_CLIENTE_ENDERECO = "/relatorioConsultarDebitosClienteEndereco.jasper";

	public static final String RELATORIO_ACOMPANHAMENTO_MOVIMENTO_ARRECADADORES = "/relatorioAcompanhamentoMovimentoArrecadadores.jasper";

	public static final String RELATORIO_ACOMPANHAMENTO_MOVIMENTO_ARRECADADORES_DIVIDA = "/relatorioAcompanhamentoMovimentoArrecadadoresDivida.jasper";

	public static final String RELATORIO_COMPARATIVOS_LEITURAS_E_ANORMALIDADES = "/relatorioComparativoLeiturasEAnormalidades.jasper";

	public static final String RELATORIO_EXTRATO_DEBITO_CLIENTE = "/relatorioExtratoDebitoCliente.jasper";

	public static final String RELATORIO_CONTRATO_PRESTACAO_SERVICO = "/relatorioContratoPrestacaoServico.jasper";

	public static final String RELATORIO_CONTRATO_PRESTACAO_SERVICO_CAERD = "/relatorioContratoPrestacaoServicoCAERD.jasper";

	public static final String RELATORIO_CONTRATO_PRESTACAO_SERVICO_JURIDICO = "/relatorioContratoPrestacaoServicoJuridico.jasper";

	public static final String RELATORIO_CONSUMO_TARIFA_MANTER = "/relatorioManterConsumoTarifa.jasper";

	public static final String RELATORIO_ANALITICO_FATURAMENTO = "/relatorioAnaliticoFaturamento.jasper";

	public static final String RELATORIO_EMITIR_PROTOCOLO_DOCUMENTO_COBRANCA = "/relatorioEmitirProtocoloDocumentoCobranca.jasper";

	public static final String RELATORIO_CLIENTES_ESPECIAIS = "/relatorioClientesEspeciais.jasper";

	public static final String RELATORIO_RELACAO_PARCELAMENTO = "/relatorioRelacaoParcelamento.jasper";

	public static final String RELATORIO_EMITIR_HISTOGRAMA_AGUA_LIGACAO = "/relatorioHistogramaAguaLigacao.jasper";

	public static final String RELATORIO_FAIXAS_FALSAS_LEITURA = "/relatorioFaixasFalsasLeitura.jasper";

	public static final String RELATORIO_EMITIR_HISTOGRAMA_AGUA_ECONOMIA = "/relatorioHistogramaAguaEconomia.jasper";

	public static final String RELATORIO_ACOMPANHAMENTO_MOVIMENTO_ARRECADADORES_POR_NSA = "/relatorioAcompanhamentoMovimentoArrecadadoresPorNSA.jasper";

	public static final String RELATORIO_CONTA_TIPO_2 = "/relatorioContaTipo2.jasper";

	public static final String RELATORIO_RESUMO_DEVEDORES_DUVIDOSOS = "/relatorioResumoDevedoresDuvidosos.jasper";

	public static final String REAVISO_DE_DEBITO = "/relatorioReavisoDeDebito.jasper";

	public static final String RELATORIO_ORDEM_CORTE_ORIGINAL = "/relatorioOrdemCorteOriginal.jasper";

	public static final String RELATORIO_FATURA_CLIENTE_RESPONSAVEL = "/relatorioFaturaClienteResponsavel.jasper";

	public static final String RELATORIO_RELACAO_ANALITICA_FATURAS = "/relatorioRelacaoAnaliticaFaturas.jasper";

	public static final String RELATORIO_GERAR_DADOS_PARA_LEITURA = "/relatorioGerarDadosParaLeitura.jasper";

	public static final String BOLETIM_CADASTRO = "boletimCadastro";

	public static final String RELATORIO_BOLETIM_CADASTRO_MODELO_2 = "/relatorioBoletimCadastroModelo2.jasper";

	public static final String RELATORIO_VOLUMES_FATURADOS = "/relatorioVolumesFaturados.jasper";

	public static final String RELATORIO_VOLUMES_FATURADOS_RESUMIDO = "/relatorioVolumesFaturadosResumido.jasper";

	public static final String RELATORIO_CONTAS_EM_REVISAO = "/relatorioContasRevisao.jasper";

	public static final String RELATORIO_ANALITICO_CONTAS = "/relatorioAnaliticoContas.jasper";

	public static final String RELATORIO_MANTER_ORDEM_SERVICO = "/relatorioMaterOrdemServico.jasper";

	public static final String RELATORIO_CONTAS_EM_REVISAO_RESUMIDO = "/relatorioContasRevisaoResumido.jasper";

	public static final String RELATORIO_ANORMALIDADE_CONSUMO = "/relatorioAnormalidadeConsumoELeitura.jasper";

	public static final String RELATORIO_PADRAO_BATCH = "/relatorioPadraoBatch.jasper";

	public static final String RELATORIO_GERAR_INDICE_ACRESCIMOS_IMPONTUALIDADE = "/relatorioGerarIndicesAcrescimosImpontualidade.jasper";

	public static final String RELATORIO_GERAR_CURVA_ABC_DEBITOS = "/relatorioGerarCurvaABCDebitos.jasper";

	public static final String RELATORIO_GERAR_QUALIDADE_AGUA = "/relatorioQualidadeAgua.jasper";

	public static final String RELATORIO_EMITIR_HISTOGRAMA_ESGOTO_LIGACAO = "/relatorioHistogramaEsgotoLigacao.jasper";

	public static final String RELATORIO_EMITIR_HISTOGRAMA_ESGOTO_ECONOMIA = "/relatorioHistogramaEsgotoEconomia.jasper";

	public static final String RELATORIO_ANALISE_CONSUMO = "/relatorioAnaliseConsumo.jasper";

	public static final String RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA = "/relatorioEmitirOrdemServicoSeletiva.jasper";

	public static final String RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_1 = "/relatorioEmitirOrdemServicoSeletivaModelo1.jasper";

	public static final String RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_2 = "/relatorioEmitirOrdemServicoSeletivaModelo2.jasper";

	public static final String RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_3 = "/relatorioEmitirOrdemServicoSeletivaModelo3.jasper";

	public static final String RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_4 = "/relatorioEmitirOrdemServicoSeletivaModelo4.jasper";

	public static final String RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_5 = "/relatorioEmitirOrdemServicoSeletivaModelo5.jasper";

	public static final String RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_6 = "/relatorioEmitirOrdemServicoSeletivaModelo6.jasper";

	public static final String RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_7 = "/relatorioEmitirOrdemServicoSeletivaModelo7.jasper";

	public static final String RELATORIO_RELACAO_SINTETICA_FATURAS = "/relatorioRelacaoSinteticaFaturas.jasper";

	public static final String RELATORIO_ORCAMENTO_SINP = "/relatorioOrcamentoSINP.jasper";

	public static final String RELATORIO_IMOVEIS_SITUACAO_LIGACAO_AGUA = "/relatorioImoveisSituacaoLigacaoAgua.jasper";

	public static final String RELATORIO_IMOVEIS_FATURAS_ATRASO = "/relatorioImoveisFaturasAtraso.jasper";

	public static final String RELATORIO_RELACAO_ORDENS_SERVICO_ENCERRADAS_PENDENTES = "/relatorioRelacaoOrdensServicoEncerradasPendentes.jasper";

	public static final String RELATORIO_IMOVEIS_CONSUMO_MEDIO = "/relatorioImoveisConsumoMedio.jasper";

	public static final String RELATORIO_IMOVEIS_ULTIMOS_CONSUMOS_AGUA = "/relatorioImoveisUltimosConsumosAgua.jasper";

	public static final String RELATORIO_ORDEM_FISCALIZACAO = "/relatorioOrdemFiscalizacao.jasper";

	public static final String RELATORIO_EVOLUCAO_CONTAS_A_RECEBER_CONTABIL = "/relatorioEvolucaoContasAReceberContabil.jasper";

	public static final String RELATORIO_QUADRO_METAS_ACUMULADO = "/relatorioQuadroMetasAcumulado.jasper";

	public static final String RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_SUGESTAO = "/relatorioEmitirOrdemServicoSeletivaSugestao.jasper";

	public static final String RELATORIO_RESUMO_ORDENS_SERVICO_ENCERRADAS_PENDENTES = "/relatorioResumoOrdensServicoEncerradasPendentes.jasper";

	public static final String RELATORIO_IMOVEIS_ATIVOS_NAO_MEDIDOS = "/relatorioImoveisAtivosNaoMedidos.jasper";

	public static final String RELATORIO_IMOVEIS_EXCLUIDOS = "/relatorioImoveisExcluidos.jasper";

	public static final String RELATORIO_IMOVEIS_TIPO_CONSUMO = "/relatorioImoveisTipoConsumo.jasper";

	public static final String RELATORIO_IMOVEIS_FATURAS_RECENTES_DIA_FATURAS_ANTIGAS_ATRASO = "/relatorioImoveisFaturasRecentesDiaFaturasAntigasAtraso.jasper";

	public static final String RELATORIO_RESUMO_LEITURA_FATURAMENTO_IMEDIATO = "/relatorioResumoLeituraFaturamentoImediato.jasper";

	public static final String RELATORIO_RESUMO_OCORRENCIAS_FATURAMENTO_IMEDIATO = "/relatorioResumoOcorrenciasFaturamentoImediato.jasper";

	public static final String RELATORIO_CERTIDAO_NEGATIVA_MODELO_1 = "/relatorioCertidaoNegativaModelo1.jasper";

	public static final String RELATORIO_ANALISE_CONSUMO_EXCEL = "/relatorioAnaliseConsumoTemplate.xls";

	public static final String RELATORIO_CERTIDAO_NEGATIVA_CAEMA = "/relatorioCertidaoNegativaCaema.jasper";

	public static final String RELATORIO_CERTIDAO_NEGATIVA_COM_EFEITO_POSITIVO = "/relatorioCertidaoNegativaComEfeitoPositivo.jasper";

	public static final String RELATORIO_CERTIDAO_NEGATIVA_CLIENTE = "/relatorioCertidaoNegativaCliente.jasper";

	public static final String RELATORIO_MANTER_NEGATIVADOR = "/relatorioManterNegativador.jasper";

	public static final String RELATORIO_MANTER_NEGATIVADOR_REGISTRO_TIPO = "/relatorioManterNegativadorRegistroTipo.jasper";

	public static final String RELATORIO_MANTER_NEGATIVADOR_RETORNO_MOTIVO = "/relatorioManterNegativadorRetornoMotivo.jasper";

	public static final String RELATORIO_MANTER_NEGATIVADOR_EXCLUSAO_MOTIVO = "/relatorioManterNegativadorExclusaoMotivo.jasper";

	public static final String RELATORIO_MANTER_NEGATIVADOR_CONTRATO = "/relatorioManterNegativadorContrato.jasper";

	public static final String RELATORIO_ACOMPANHAMENTO_CLIENTES_NEGATIVADOS = "/relatorioAcompanhamentoClientesNegativados.jasper";

	public static final String RELATORIO_NEGATIVACOES_EXCLUIDAS = "/relatorioNegativacoesExcluidas.jasper";

	public static final String RELATORIO_RELACAO_ORDENS_SERVICO_CONCLUIDAS = "/relatorioRelacaoOrdensServicoConcluidas.jasper";

	public static final String RELATORIO_MANTER_USUARIO = "/relatorioManterUsuario.jasper";

	public static final String RELATORIO_CONTAS_BAIXADAS_CONTABILMENTE = "/relatorioContasBaixadasContabilmente.jasper";

	public static final String RELATORIO_CONTAS_CANCELADAS = "/relatorioContasCanceladas.jasper";

	public static final String RELATORIO_CONTAS_RETIFICADAS = "/relatorioContasRetificadas.jasper";

	public static final String RELATORIO_CONTAS_CANCELADAS_SINTETICO = "/relatorioContasCanceladasSintetico.jasper";

	public static final String RELATORIO_CONTAS_RETIFICADAS_SINTETICO = "/relatorioContasRetificadasSintetico.jasper";

	public static final String RELATORIO_AVISO_ANORMALIDADE = "/relatorioAvisoAnormalidade.jasper";

	public static final String RELATORIO_GERAR_DADOS_LEITURA = "/relatorioGerarDadosParaLeitura.jasper";

	public static final String RELATORIO_NEGATIVADOR_RESULTADO_SIMULACAO = "/relatorioNegativadorResultadoSimulacao.jasper";

	public static final String RELATORIO_RELACAO_SERVICO_ACOMPANHAMENTO_REPAVIMENTACAO = "/relatorioRelacaoServicosAcompanhamentoRepavimentacao.jasper";

	public static final String RELATORIO_BOLETIM_ORDENS_SERVICO_CONCLUIDAS = "/relatorioBoletimOrdensServicoConcluidas.jasper";

	public static final String RELATORIO_MANTER_MOTIVO_CORTE = "/relatorioManterMotivoCorte.jasper";

	public static final String RELATORIO_MANTER_ARRECADACAO_FORMA = "/relatorioManterArrecadacaoForma.jasper";

	public static final String RELATORIO_MANTER_ATIVIDADE = "/relatorioManterAtividade.jasper";

	public static final String RELATORIO_MANTER_LIGACAO_AGUA_SITUACAO = "/relatorioManterLigacaoAguaSituacao.jasper";

	public static final String RELATORIO_MANTER_HIDROMETRO_DIAMETRO = "/relatorioManterHidrometroDiametro.jasper";

	public static final String RELATORIO_MANTER_ZONA_PRESSAO = "/relatorioManterZonaPressao.jasper";

	public static final String RELATORIO_MANTER_FATURAMENTO_GRUPO = "/relatorioManterFaturamentoGrupo.jasper";

	public static final String RELATORIO_MANTER_ANORMALIDADE_CONSUMO = "/relatorioManterAnormalidadeConsumo.jasper";

	public static final String RELATORIO_MANTER_PRODUCAO_AGUA = "/relatorioManterProducaoAgua.jasper";

	public static final String RELATORIO_MANTER_ALTERACAO_TIPO = "/relatorioManterAlteracaoTipo.jasper";

	public static final String RELATORIO_FATURAS_AGRUPADAS = "/relatorioFaturasAgrupadas.jasper";

	public static final String RELATORIO_PROTOCOLO_ENTREGA_FATURA = "/relatorioProtocoloEntregaFatura.jasper";

	public static final String RELATORIO_MANTER_SISTEMA_ESGOTO_TRATAMENTO_TIPO = "/relatorioManterSistemaEsgotoTratamentoTipo.jasper";

	public static final String RELATORIO_GESTAO_SERVICOS_UPA = "/relatorioGestaoServicosUPA.jasper";

	public static final String RELATORIO_RESUMO_SOLICITACOES_RA_POR_UNIDADE = "/relatorioResumoSolicitacoesRAPorUnidade.jasper";

	public static final String RELATORIO_VOLUMES_CONSUMIDOS_NAO_FATURADOS = "/relatorioVolumesConsumidosNaoFaturados.jasper";

	public static final String RELATORIO_ANALISE_ARRECADACAO = "/relatorioAnaliseArrecadacao.jasper";

	public static final String RELATORIO_PARAMETROS_CONTABEIS_FATURAMENTO = "/relatorioParametrosContabeisFaturamento.jasper";

	public static final String RELATORIO_PARAMETROS_CONTABEIS_ARRECADACAO = "/relatorioParametrosContabeisArrecadacao.jasper";

	public static final String RELATORIO_ANALISE_AVISOS_BANCARIOS = "/relatorioAnaliseAvisosBancarios.jasper";

	public static final String RELATORIO_TRANSFERENCIAS_CONSULTAR = "/relatorioConsultarTransferencias.jasper";

	public static final String RELATORIO_POSICAO_FATURAMENTO = "/relatorioPosicaoFaturamento.jasper";

	public static final String RELATORIO_AUTO_INFRACAO = "/relatorioAutoInfracao.jasper";

	public static final String RELATORIO_AVISO_BANCARIO_POR_CONTA_CORRENTE = "/relatorioAvisoBancarioPorContaCorrente.jasper";

	public static final String RELATORIO_CONSULTAR_MOVIMENTACAO_HIDROMETRO = "/relatorioConsultarMovimentacaoHidrometro.jasper";

	public static final String RELATORIO_GESTAO_SOLICITACOES_RA_POR_CHEFIA = "/relatorioGestaoSolicitacoesRAPorChefia.jasper";

	public static final String RELATORIO_FILTRAR_REGISTRO_ATENDIMENTO = "/relatorioFiltrarRegistroAtendimento.jasper";

	public static final String RELATORIO_FILTRAR_ORDEM_SERVICO = "/relatorioFiltrarOrdemServico.jasper";

	public static final String RELATORIO_PERFIL_LIGACAO_ESGOTO_MANTER = "/relatorioManterPerfilLigacaoEsgoto.jasper";

	public static final String RELATORIO_ROTAS_ONLINE_POR_EMPRESA = "/relatorioRotasOnlinePorEmpresa.jasper";

	public static final String RELATORIO_TIPO_DEBITO_MANTER = "/relatorioManterTipoDebito.jasper";

	public static final String RELATORIO_TIPO_PERFIL_SERVICO_MANTER = "/relatorioManterTipoPerfilServico.jasper";

	public static final String RELATORIO_MANTER_MOVIMENTO_ARRECADADORES_ITENS = "/relatorioManterMovimentoArrecadadoresItens.jasper";

	public static final String RELATORIO_PAGAMENTOS_CONTAS_COBRANCA_EMPRESA = "/relatorioPagamentosContasCobrancaEmpresa.jasper";

	public static final String RELATORIO_PAGAMENTOS_CONTAS_COBRANCA_EMPRESA_SINTETICO = "/relatorioPagamentosContasCobrancaEmpresaSintetico.jasper";

	public static final String RELATORIO_ANALISE_IMOVEL_CORPORATIVO_GRANDE = "/relatorioAnaliseImovelCorporativoGrande.jasper";

	public static final String RELATORIO_GUIA_PAGAMENTO_EM_ATRASO = "/relatorioGuiaPagamentoEmAtraso.jasper";

	public static final String RELATORIO_RELACAO_PARCELAMENTO_ANALITICO = "/relatorioRelacaoParcelamentoAnalitico.jasper";

	public static final String RELATORIO_MANTER_SERVICO_TIPO = "/relatorioManterServicoTipo.jasper";

	public static final String RELATORIO_IMOVEIS_COM_ACORDO = "/relatorioImoveisComAcordo.jasper";

	public static final String RELATORIO_RESUMO_DISTRITO_OPERACIONAL = "/relatorioResumoDistritoOperacional.jasper";

	public static final String RELATORIO_RESUMO_ZONA_ABASTECIMENTO = "/relatorioResumoZonaAbastecimento.jasper";

	public static final String RELATORIO_AUTOS_INFRACAO_MANTER = "/relatorioManterAutosInfracao.jasper";

	public static final String RELATORIO_UNIDADE_NEGOCIO_MANTER = "/relatorioManterUnidadeNegocio.jasper";

	public static final String RELATORIO_BOLETIM_CUSTO_ATUALIZACAO_CADASTRAL = "/relatorioBoletimCustoAtualizacaoCadastral.jasper";

	public static final String RELATORIO_FILTRAR_REGISTRO_ATENDIMENTO_COORDENADAS_SEM_LOGRADOURO = "/relatorioFiltrarRegistroAtendimentoCoordenadasSemLogradouro.jasper";

	public static final String RELATORIO_IMPOSTOS_POR_CLIENTE_RESPONSAVEL = "/relatorioRelacaoImpostosPorClienteResponsavel.jasper";

	public static final String RELATORIO_ERROS_MOVIMENTOS_CONTA_PRE_FATURADAS = "relatorioErrosMovimentosContaPreFaturadas.jasper";

	public static final String RELATORIO_RESUMO_LEITURAS_ANORMALIDADE_IMPRESSAO_SIMULTANEA = "relatorioResumoLeiturasAnormalidadesRegistradas.jasper";

	public static final String RELATORIO_MANTER_DEBITO_AUTOMATICO = "/relatorioManterDebitoAutomatico.jasper";

	public static final String RELATORIO_MANTER_CONTRATO_ARRECADADOR = "/relatorioManterContratoArrecadador.jasper";

	public static final String RELATORIO_MANTER_AGENCIA_BANCARIA = "/relatorioManterAgenciaBancaria.jasper";

	public static final String RELATORIO_MANTER_ARRECADADOR = "/relatorioManterArrecadador.jasper";

	public static final String RELATORIO_CONTAS = "/relatorioConta2.jasper";

	public static final String RELATORIO_NOTIFICACAO_DEBITO = "/relatorioNotificacaoDebito.jasper";

	public static final String RELATORIO_NOTIFICACAO_DEBITO_COSANPA = "/relatorioNotificacaoDebitoCosanpa.jasper";

	public static final String RELATORIO_ANALISAR_METAS_CICLO = "/relatorioAnalisarMetasCiclo.jasper";

	public static final String RELATORIO_COBRANCA_GRUPO_MANTER = "/relatorioManterCobrancaGrupo.jasper";

	public static final String RELATORIO_ACOMPANHAMENTO_ACOES_COBRANCA = "/relatorioAcompanhamentoAcoesCobranca.jasper";

	public static final String RELATORIO_FILTRAR_DOCUMENTO_COBRANCA = "/relatorioFiltrarDocumentoCobranca.jasper";

	public static final String RELATORIO_SUPRESSOES_RELIGACOES_REESTABELECIMENTOS = "/relatorioSupressoesReligacoesReestabelecimentos.jasper";

	public static final String RELATORIO_IMOVEIS_RELACIONADOS_CLIENTE = "/relatorioImoveisRelacionadosCliente.jasper";

	public static final String RELATORIO_CLIENTES_RELACIONADOS_IMOVEL = "/relatorioClientesRelacionadosImovel.jasper";

	public static final String RELATORIO_PROJETO_MANTER = "/relatorioProjetoManter.jasper";

	public static final String RELATORIO_DADOS_CADASTRAIS_IMOVEL = "/relatorioDadosCadastraisImovel.jasper";

	public static final String RELATORIO_DADOS_COMPLEMENTARES_IMOVEL = "/relatorioDadosComplementaresImovel.jasper";

	public static final String RELATORIO_DADOS_ANALISE_MEDICAO_CONSUMO_IMOVEL = "/relatorioDadosAnaliseMedicaoConsumoImovel.jasper";

	public static final String RELATORIO_HISTORICO_FATURAMENTO_IMOVEL = "/relatorioHistoricoFaturamentoImovel.jasper";

	public static final String RELATORIO_COMANDO_DOCUMENTO_COBRANCA_COSANPA = "/relatorioComandoDocumentoCobrancaCosanpa.jasper";

	public static final String RELATORIO_HISTORICO_MEDICAO_POCO = "/relatorioHistoricoMedicaoPoco.jasper";

	public static final String RELATORIO_ACOMPANHAMENTO_LEITURISTA = "/relatorioAcompanhamentoLeiturista.jasper";

	public static final String RELATORIO_JUROS_MULTAS_DEBITOS_CANCELADOS = "/relatorioJurosMultasDebitosCancelados.jasper";

	public static final String RELATORIO_DEBITO_COBRADO_CONTA = "/relatorioValorDebitoConsultarImovel.jasper";

	public static final String RELATORIO_COMANDO_DOCUMENTO_COBRANCA = "/relatorioComandoDocumentoCobranca.jasper";

	public static final String RELATORIO_ACRESCIMOS_POR_IMPONTUALIDADE = "/relatorioAcrescimoPorImpontualidade.jasper";

	public static final String RELATORIO_ANORMALIDADE_LEITURA_PERIODO = "/relatorioAnormalidadeLeituraPeriodo.jasper";

	public static final String RELATORIO_AVISO_DEBITO = "/relatorioAvisoDebito.jasper";

	public static final String RELATORIO_AVISO_DEBITO_MODELO3 = "/relatorioAvisoDebitoModelo3.jasper";

	public static final String RELATORIO_AVISO_CORTE = "/relatorioAvisoCorte.jasper";

	public static final String RELATORIO_TRANSFERENCIA_TERMO_ASSUNCAO_MODELO_1 = "/relatorioTransferenciaTermoAssuncaoModelo1.jasper";

	public static final String RELATORIO_AVISO_CORTE_ARQUIVO_TXT = "/relatorioAvisoCorteArquivoTxt.txt";

	public static final String RELATORIO_ORDEM_CORTE_ARQUIVO_TXT = "/relatorioOrdemCorteArquivoTxt.txt";

	public static final String RELATORIO_ORDEM_CORTE_ARQUIVO_PDF_MODELO_4 = "/relatorioOrdemCorteModelo4.jasper";

	public static final String RELATORIO_ORDEM_CORTE_ARQUIVO_PDF_MODELO_5 = "/relatorioOrdemCorteModelo5.jasper";

	public static final String RELATORIO_TELECOBRANCA = "/relatorioTeleCobranca.csv";

	public static final String RELATORIO_JURIDICO = "/relatorioJuridico.csv";

	public static final String RELATORIO_NEGATIVACAO_SPC_SERASA = "/relatorioNegativacaoSpcSerasa.txt";

	public static final String RELATORIO_EXCLUSAO_NEGATIVACAO_SPC_SERASA = "/relatorioExclusaoNegativacaoSpcSerasa.txt";

	public static final String RELATORIO_ORDEM_SERVICO_MANUT_RAMAL_ESGOTO = "/relatorioManutencaoRedeRamalEsgoto.jasper";

	public static final String RELATORIO_VERIFICACAO_LIMPEZA = "/relatorioVerificacaoLimpeza.jasper";

	public static final String RELATORIO_DESOBSTRUCAO_RAMAL_ESGOTO = "/relatorioDesobstrucaoRedeRamalEsgoto.jasper";

	public static final String RELATORIO_INSTALACAO_HIDROMETRO = "/relatorioInstalacaoHidrometro.jasper";

	public static final String RELATORIO_SUBSTITUICAO_HIDROMETRO = "/relatorioSubstituicaoHidrometro.jasper";

	public static final String RELATORIO_VERIFICACAO_LIGACAO_AGUA = "/relatorioVerificacaoLigacaoAguaCCI.jasper";

	public static final String RELATORIO_FECHAMENTO_COBRANCA = "/relatorioFechamentoCobranca.xls";

	public static final String RELATORIO_EFICIENCIA_COBRANCA = "/relatorioEficienciaCobrancaTemplate.xls";

	public static final String RELATORIO_ACOMPANHAMENTO_EXECUCAO_SERVICO_COBRANCA = "/relatorioAcompanhamentoExecucaoServicoCobrancaTemplate.xls";

	public static final String RELATORIO_PRODUTIVIDADE_MENSAL_EXECUCAO_SERVICO = "/relatorioProdutividadeMensalExecucaoServicoTemplate.xls";

	public static final String RELATORIO_IMOVEL_POR_ACAO_COBRANCA = "/relatorioImovelPorAcaoCobrancaTemplate.xls";

	public static final String RELATORIO_RELIGACAO_POR_IMOVEL = "/relatorioReligacaoPorImovelTemplate.xls";

	public static final String RELATORIO_ACOMPANHAMENTO_ACAO = "/relatorioAcompanhamentoAcaoTemplate.xls";

	public static final String RELATORIO_ACOMPANHAMENTO_RELIGACOES_ESPECIAIS = "/relatorioRelacaoImoveisReligacaoEspecialDiaTemplate.xls";

	public static final String RELATORIO_ORDEM_CORTE = "/relatorioOrdemCorte.jasper";

	public static final String RELATORIO_FISCALIZACAO_ORDEM_CORTE = "/relatorioOrdemFiscalizacaoCorte.jasper";

	public static final String RELATORIO_FISCALIZACAO_ORDEM_SUPRESSAO = "/relatorioOrdemFiscalizacaoSupressao.jasper";

	public static final String RELATORIO_CONTROLE_DOCUMENTOS_ARRECADACAO_SINTETICO = "/relatorioControleDocumentosArrecadacaoSintetico.jasper";

	public static final String RELATORIO_CONTROLE_DOCUMENTOS_ARRECADACAO_ANALITICO = "/relatorioControleDocumentosArrecadacaoAnalitico.jasper";

	public static final String RELATORIO_LIQUIDOS_RECEBIVEIS_ANALITICO = "/relatorioLiquidosRecebiveisAnalitico.jasper";

	public static final String RELATORIO_LIQUIDOS_RECEBIVEIS_SINTETICO = "/relatorioLiquidosRecebiveisSintetico.jasper";

	// UC01101 - Emitir Carta com Opção de parcelamento
	public static final String RELATORIO_CARTA_OPCAO_PARCELAMENTO = "/relatorioCartaOpcoesParcelamento.jasper";

	// CRC 89962
	public static final String RELATORIO_GERAR_COMPROVANTES_LEITURA = "/relatorioRelacaoComprovantesLeitura.jasper";

	/*
	 * Esta constante foi criada para possibilitar a geração de Relatório de Ordem de Serviço
	 * Genéricos
	 * O .jasper não existe.
	 * Depois ver como corrigir isso
	 */
	public static final String RELATORIO_ORDEM_SERVICO_COBRANCA = "/relatorioOrdemOrdemServicoCobranca.jasper";

	public static final String RELATORIO_LOGRADOURO_GERAL = "/relatorioLogradouroGeral.jasper";

	public static final String RELATORIO_SUBSISTEMA_ESGOTO_MANTER = "/relatorioManterSubsistemaEsgoto.jasper";

	public static final String RELATORIO_SISTEMA_ESGOTAMENTO_MANTER = "/relatorioManterSistemaEsgotamento.jasper";

	// public static final String RELATORIO_LOCALIDADE_DADOS_OPERACIONAIS_MANTER =
	// "/relatorioManterLocalidadeDadoOperacional.jasper";

	public static final String RELATORIO_SISTEMA_ABASTECIMENTO_MANTER = "/relatorioManterSistemaAbastecimento.jasper";

	public static final String RELATORIO_SETOR_ABASTECIMENTO_MANTER = "/relatorioManterSetorAbastecimento.jasper";

	public static final String RELATORIO_BACIA_MANTER = "/relatorioManterBacia.jasper";

	public static final String RELATORIO_MICROMEDIDORES = "/relatorioMicromedidores.jasper";

	public static final String RELATORIO_MACROMEDIDORES_MICROMEDIDOS_ASSOCIADOS_ULTIMOS_CONSUMOS = "/relatorioMacromedidoresMicromedidosAssociadosUltimosConsumos.jasper";

	public static final String RELATORIO_GRANDES_CONSUMIDORES = "/relatorioGrandesConsumidores.jasper";

	public static final String RELATORIO_USUARIO_DEBITO_AUTOMATICO = "/relatorioUsuarioDebitoAutomatico.jasper";

	public static final String RELATORIO_CONSUMO_FAIXA_AREA_CATEGORIA_MANTER = "/relatorioManterConsumoFaixaAreaCategoria.jasper";

	public static final String RELATORIO_RESUMO_REGISTRO_ATENDIMENTO = "/relatorioResumoRegistroAtendimento.jasper";

	public static final String RELATORIO_RESUMO_REGISTRO_ATENDIMENTO_SUBTOTALIZADOR = "/relatorioResumoRegistroAtendimentoSubTotalizador.jasper";

	public static final String RELATORIO_GESTAO_REGISTRO_ATENDIMENTO_ANALITICO = "/relatorioGestaoRegistroAtendimentoAnalitico.jasper";

	public static final String RELATORIO_GESTAO_REGISTRO_ATENDIMENTO_SINTETICO = "/relatorioGestaoRegistroAtendimentoSintetico.jasper";

	public static final String RELATORIO_ESTATISTICA_ATENDIMENTO_POR_ATENDENTE = "/relatorioEstatisticaAtendimentoPorAtendente.jasper";

	public static final String RELATORIO_FERIADO_MANTER = "/relatorioManterFeriado.jasper";

	public static final String RELATORIO_PRODUTIVIDADE_EQUIPES = "/relatorioProdutividadeEquipe.jasper";

	public static final String RELATORIO_ORDEM_SERVICO_ESTRUTURA = "relatorioOrdemServicoEstrutura.jasper";

	public static final String RELATORIO_ORDEM_SERVICO_AFERICAO_HIDROMETRO = "relatorioOrdemServicoAfericaoHidrometro.jasper";

	public static final String RELATORIO_ORDEM_SERVICO_LIGACAO_AGUA = "relatorioOrdemServicoLigacaoAgua.jasper";

	public static final String RELATORIO_ORDEM_SERVICO_RELIGACAO_MEDICAO_INDIV = "relatorioOrdemServicoReligacaoMedicaoIndiv.jasper";

	public static final String RELATORIO_ORDEM_SERVICO_SUBSTITUICAO_HIDROMETRO = "relatorioOrdemServicoSubstituicaoHidrometro.jasper";

	public static final String RELATORIO_ORDEM_SERVICO_VAZAMENTO_REDE = "relatorioOrdemServicoVazamentoRede.jasper";

	public static final String RELATORIO_ORDEM_SERVICO_VERIFICAR_DADO_CADASTRAL = "relatorioOrdemServicoVerificarDadosCadastrais.jasper";

	public static final String RELATORIO_ORDEM_SERVICO_SOLICITACAO_BENEFICIO = "relatorioOrdemServicoSolicitacaoBeneficio.jasper";

	public static final String RELATORIO_ORDEM_SERVICO_VISITA_TARIFA_SOCIAL = "relatorioOrdemServicoVisitaTarifaSocial.jasper";

	public static final String RELATORIO_ORDEM_SERVICO_CORTE_POR_DEBITOS = "relatorioOrdemServicoCortePorDebitos.jasper";

	public static final String RELATORIO_RESUMO_ORDENS_SERVICO_ENCERRADAS = "/relatorioResumoOrdensServicoEncerradas.jasper";

	public static final String RELATORIO_RESUMO_ORDENS_SERVICO_PENDENTES = "/relatorioResumoOrdensServicoPendentes.jasper";

	public static final String RELATORIO_ANORMALIDADES_LEITURAS = "/relatorioAnormalidadesLeituras.jasper";

	public static final String RELATORIO_RESUMO_FATURAMENTO_IMEDIATO = "/relatorioResumoFaturamentoImediato.jasper";

	public static final String RELATORIO_DADOS_FATURAMENTO_IMEDIATO = "/relatorioDadosFaturamentoImediato.txt";

	public static final String RELATORIO_TABELAS_FATURAMENTO_IMEDIATO = "/relatorioTabelasFaturamentoImediato.txt";

	public static final String RELATORIO_PROCESSAR_ARQUIVO_INFORMAR_ANORMALIDADES_LEITURA = "/relatorioProcessarArquivoInformarAnormalidadesLeituras.jasper";

	public static final String RELATORIO_GERACAO_CARTAS_COBRANCA_BANCARIA = "/relatorioGeracaoCartasCobrancaBancaria.txt";

	public static final String RELATORIO_IMOVEIS_ACAO_COBRANCA = "/relatorioImoveisAcaoCobranca.csv";

	public static final String RELATORIO_MOVIMENTO_COBRANCA_BANCARIA = "/relatorioMovimentoCobrancaBancaria.jasper";

	public static final String RELATORIO_BOLETOS_BANCARIOS = "/relatorioBoletosBancarios.jasper";

	public static final String RELATORIO_EMITIR_RELACAO_DOCUMENTOS_COBRANCA = "/relatorioEmitirRelacaoDocumentosCobranca.jasper";

	public static final String RELATORIO_GUIA_PAGAMENTO_MANTER = "/relatorioManterGuiaPagamento.jasper";

	public static final String RELATORIO_IMOVEIS_POR_QUADRA = "/relatorioImoveisPorQuadra.jasper";

	public static final String RELATORIO_RELACAO_IMOVEIS_COM_MUDANCA_DA_QUADRA = "/relatorioRelacaoImoveisComMudancaDaQuadra.jasper";

	public static final String RELATORIO_CONTAS_PRE_FATURADAS = "/relatorioContasPreFaturadas.jasper";

	public static final String RELATORIO_AVISO_E_ORDEM_CORTE_INDIVIDUAL = "/relatorioAvisoCorteIndividual.jasper";

	public static final String RELATORIO_AVISO_E_ORDEM_CORTE_INDIVIDUAL_1 = "/relatorioAvisoCorteIndividual1.jasper";

	public static final String RELATORIO_AVISO_E_ORDEM_CORTE_INDIVIDUAL_1_DESO = "/relatorioAvisoCorteIndividualDESO1.jasper";

	public static final String RELATORIO_AVISO_E_ORDEM_CORTE_INDIVIDUAL_2 = "/relatorioAvisoCorteIndividual2.jasper";

	public static final String RELATORIO_AVISO_E_ORDEM_CORTE_INDIVIDUAL_2_DESO = "/relatorioAvisoCorteIndividualDESO2.jasper";

	public static final String RELATORIO_AVISO_E_ORDEM_CORTE_INDIVIDUAL_1_E_2 = "/relatorioAvisoCorteIndividual1e2.jasper";

	public static final String RELATORIO_AVISO_E_ORDEM_CORTE_INDIVIDUAL_3 = "/relatorioAvisoCorteIndividual3.jasper";

	public static final String RELATORIO_GERACAO_ORDEM_SERVICO_TXT = "/relatorioGeracaoOrdemServico.txt";

	public static final String RELATORIO_ARQUIVO_AGENCIA_VIRTUAL_IMOVEIS = "/relatorioAgenciaVirtualImoveis.txt";

	public static final String RELATORIO_ARQUIVO_AGENCIA_VIRTUAL_DEBITOS = "/relatorioAgenciaVirtualDebitos.txt";

	public static final String RELATORIO_ARQUIVO_AGENCIA_VIRTUAL_QUITACAO_DEBITOS = "/relatorioAgenciaVirtualQuitDebitos.txt";

	public static final String RELATORIO_ARQUIVO_AGENCIA_VIRTUAL_LOGRADOUROS = "/relatorioAgenciaVirtualLogradouros.txt";

	public static final String RELATORIO_DADOS_TABELAS_FATURAMENTO_IMEDIATO = "/relatorioDadosTabelasFaturamentoImediato.txt";

	public static final String RELATORIO_OCORRENCIA_GERACAO_PRE_FATURAMENTO = "/relatorioOcorrenciaGeracaoPreFaturamento.jasper";

	public static final String RELATORIO_OCORRENCIA_GERACAO_PRE_FATURAMENTO_RESUMO = "/relatorioOcorrenciaGeracaoPreFaturamentoResumo.jasper";

	public static final String RELATORIO_CERTIDAO_NEGATIVA_MODELO_2 = "/relatorioCertidaoNegativaModelo2.jasper";

	public static final String RELATORIO_CERTIDAO_NEGATIVA_MODELO_3 = "/relatorioCertidaoNegativaModelo3.jasper";

	public static final String RELATORIO_RESUMO_LIGACOES_ECONOMIA = "/relatorioResumoLigacoesEconomiaGCS.jasper";

	public static final String RELATORIO_ARQUIVO_FATURAMENTO_CONVENCIONAL = "/relatorioArquivoFaturamentoConvencional.txt";

	public static final String RELATORIO_POSICAO_DEBITO_NEGATIVACAO_LEGADO_CASAL = "/relatorioPosicaoDebitoNegativacaoLegadoCasal.jasper";

	public static final String RELATORIO_POSICAO_DEBITO_NEGATIVACAO_LEGADO_CAGEPA = "/relatorioPosicaoDebitoNegativacaoLegadoCagepa.jasper";

	public static final String RELATORIO_ESTATISTICO_REGISTRO_ATENDIMENTO = "/relatorioEstatisticoRegistroAtendimento.jasper";

	public static final String RELATORIO_REPOSICAO_PAVIMENTO = "/relatorioReposicaoPavimento.jasper";

	public static final String RELATORIO_REMUNERACAO_COBRANCA_ADMINISTRATIVA = "/relatorioRemuneracaoCobrancaAdministrativa.jasper";

	public static final String RELATORIO_REMUNERACAO_COBRANCA_ADMINISTRATIVA_MODELO_2 = "/relatorioRemuneracaoCobrancaAdministrativaModelo2.jasper";

	public static final String RELATORIO_DEBITO_POR_RESPONSAVEL_ANALITICO = "/relatorioDebitoPorResponsavelAnalitico.jasper";

	public static final String RELATORIO_OPERACAO_MANTER = "/relatorioManterOperacao.jasper";

	public static final String RELATORIO_PARCELAMENTO_PRC = "/relatorioParcelamentoPRC.jasper";

	public static final String RELATORIO_POSICAO_CONTAS_A_RECEBER_CONTABIL = "/relatorioPosicaoContasAReceberContabil.jasper";

	public static final String RELATORIO_CONTAS_A_RECEBER_CORRIGIDO_CATEGORIAS = "/relatorioContasAReceberCorrigidoCategorias.jasper";

	public static final String RELATORIO_SALDO_CONTAS_A_RECEBER_CONTABIL = "/relatorioSaldoContasAReceberContabil.jasper";

	public static final String RELATORIO_SALDO_CONTAS_A_RECEBER_CONTABIL_MODELO_1 = "/relatorioSaldoContasAReceberContabilModelo1.jasper";

	public static final String RELATORIO_SALDO_CONTAS_A_RECEBER_CONTABIL_MODELO_2 = "/relatorioSaldoContasAReceberContabilModelo2.jasper";

	public static final String RELATORIO_SALDO_CONTAS_A_RECEBER_CONTABIL_MODELO_3 = "/relatorioSaldoContasAReceberContabilModelo3.jasper";

	public static final String RELATORIO_SALDO_CONTAS_A_RECEBER_CONTABIL_MODELO_4 = "/relatorioSaldoContasAReceberContabilModelo4.jasper";

	public static final String RELATORIO_CLASSIFICAR_LOTE_PAGAMENTO_NAO_CLASSIFICADO = "/relatorioClassificarLotePagamentosNaoClassificados.jasper";

	public static final String RELATORIO_RELACAO_IMOVEIS_EM_COBRANCA_ADMINISTRATIVA = "/relatorioImoveisEmCobrancaAdministrativa.jasper";

	public static final String RELATORIO_ANALITICO_RELACAO_IMOVEIS_EM_COBRANCA_ADMINISTRATIVA = "/relatorioAnaliticoImoveisEmCobrancaAdministrativa.jasper";

	public static final String RELATORIO_OCORRENCIAS_PROCESSO_DIARIO_COBRANCA_BANCARIA = "/relatorioOcorrenciasProcessoDiarioCobrancaBancaria.jasper";

	public static final String RELATORIO_EMITIR_HISTOGRAMA_AGUA_ESGOTO_ECONOMIA_TXT = "/relatorioHistogramaAguaEsgotoEconomia.txt";

	public static final String RELATORIO_ORDEM_SERVICO_FISCALIZACAO_MODELO_2 = "/relatorioOrdemServicoFiscalizacaoModelo2.jasper";

	public static final String RELATORIO_ORDEM_SERVICO_CARTA_HIDROMETRO_MODELO_1 = "/relatorioOrdemServicoFiscalizacaoDadosUltimosConsumos.jasper";

	public static final String RELATORIO_ARQUIVO_DECLARACAO_ANUAL_QUITACAO_DEBITOS = "/relatorioArquivoDeclaracaoAnualQuitacaoDebitos.jasper";

	public static final String RELATORIO_ARQUIVO_DECLARACAO_ANUAL_QUITACAO_DEBITOS_MODELO_2 = "/relatorioArquivoDeclaracaoAnualQuitacaoDebitosModelo2.jasper";

	public static final String RELATORIO_ARQUIVO_DECLARACAO_ANUAL_QUITACAO_DEBITOS_MODELO_3 = "/relatorioArquivoDeclaracaoAnualQuitacaoDebitosModelo3.jasper";

	public static final String RELATORIO_QUADRO_COMPARATIVO_FATURAMENTO_ARRECADACAO = "/relatorioQuadroComparativoFaturamentoArrecadacao.jasper";

	public static final String RELATORIO_AVISO_CORTE_ARQUIVO_PDF_MODELO_5 = "/relatorioAvisoCorteModelo5.jasper";

	public static final String RELATORIO_AVISO_CORTE_ARQUIVO_PDF_MODELO_4 = "/relatorioAvisoCorteModelo4.jasper";

	public static final String RELATORIO_MANTER_CEP = "/relatorioManterCep.jasper";

	public static final String RELATORIO_HIDROMETRO_MOVIMENTACAO = "/relatorioHidrometroMovimentacao.jasper";

	public static final String RELATORIO_PARECER_ENCERRAMENTO_OS = "/relatorioParecerEncerramentoOS.jasper";

	public static final String RELATORIO_ARQUIVO_RELIGACAO_AUTOMATICA_CONSUMIDOR = "/relatorioArquivoReligacaoAutomaticaConsumidor.txt";

	public static final String RELATORIO_CONTA_MODELO_2 = "/relatorioContaModelo2.jasper";

	public static final String RELATORIO_IMOVEIS_INSCRICAO_ALTERADA = "/relatorioImoveisInscricaoAlterada.jasper";

	public static final String RELATORIO_CONTAS_RETIRADAS_REVISAO = "/relatorioContasRetiradasRevisao.jasper";

	public static final String RELATORIO_CONTAS_BLOQUEADAS_ANALISE = "/relatorioContasBloqueadasAnalise.jasper";

	public static final String RELATORIO_COMPROVANTES_DA_ARRECADACAO_POR_RECEBEDOR_SINTETICO = "/relatorioComprovantesDaArrecadacaoPorRecebedorSintetico.jasper";

	public static final String RELATORIO_COMPROVANTES_DA_ARRECADACAO_POR_RECEBEDOR_ANALITICO = "/relatorioComprovantesDaArrecadacaoPorRecebedorAnalitico.jasper";

	public static final String RELATORIO_COMPROVANTE_INSCRICAO_CAMPANHA_PREMIACAO = "/relatorioComprovanteInscricaoCampanhaPremiacaoModelo1.jasper";

	public static final String RELATORIO_PREMIACOES_CAMPANHA_PREMIACAO = "/relatorioPremiacoesCampanhaPremiacao.jasper";

	public static final String RELATORIO_FATURAMENTO_CONSUMO_DIRETO_INDIRETO_ESTADUAL = "/relatorioFaturamentoConsumoDiretoIndiretoEstadual.jasper";

	public static final String RELATORIO_SITUACAO_DOS_AVISOS_BANCARIOS = "/relatorioSituacaoDosAvisosBancarios.jasper";

	public static final String RELATORIO_ATUALIZACAO_CADASTRAL_COLETOR_DADOS = "/relatorioAtualizacaoCadastralColetorDados.jasper";

	public static final String RELATORIO_AUDITORIA_LEITURA = "/relatorioAuditoriaLeitura.jasper";

	public static final String RELATORIO_COMANDO_OS_SELETIVA = "/relatorioComandoOsSeletiva.jasper";

	public static final String RELATORIO_DADOS_COMANDO_OS_SELETIVA = "/relatorioDadosComandoOsSeletiva.jasper";

	public static final String RELATORIO_AVISO_DEBITO_MODELO_2 = "/relatorioAvisoDebitoModelo2.jasper";

	public static final String RELATORIO_MAIORES_CONSUMIDORES = "/relatorioMaioresConsumidores.jasper";

	public static final String RELATORIO_MAIORES_DEVEDORES = "/relatorioMaioresDevedores.jasper";

	public static final String RELATORIO_IMOVEIS_LIGACAO_CORTADA_COM_CONSUMO = "/relatorioImoveisLigacaoCortadaComConsumo.jasper";

	public static final String RELATORIO_QUADRO_HIDROMETROS = "/relatorioQuadroHidrometros.jasper";

	public static final String RELATORIO_QUADRO_HIDROMETROS_ANO_INSTALACAO = "/relatorioQuadroHidrometrosAnoInstalacao.jasper";

	public static final String RELATORIO_QUADRO_HIDROMETROS_SITUACAO = "/relatorioQuadroHidrometrosSituacao.jasper";

	public static final String RELATORIO_ORDENS_SERVICO_ENCERRADAS_DENTRO_FORA_PRAZO = "/relatorioResumoOrdensServicoEncerradasDentroForaPrazo.jasper";

	public static final String RELATORIO_MATERIAIS_APLICADOS = "/relatorioMateriaisAplicados.jasper";

	public static final String RELATORIO_CONTRATO_DEMANDA = "/relatorioContratoDemanda.jasper";

	public static final String RELATORIO_CONTRATO_DEMANDA_CONSUMO = "/relatorioContratoDemandaConsumo.jasper";

	public static final String RELATORIO_REGISTRO_ATENDIMENTO_COM_PROCESSO_ADM_JUD = "/relatorioRegistroAtendimentoComProcessoAdmJud.jasper";

	public static final String RELATORIO_DEBITOS_PRESCRITOS = "/relatorioDebitosPrescritos.jasper";

	public static final String RELATORIO_ACOMPANHAMENTO_DEBITOS_PRESCRITOS = "/relatorioAcompanhamentoDebitosPrescritos.jasper";

	public static final String RELATORIO_ESTATISTICO_ATENDIMENTO_POR_RACA_COR = "/relatorioEstatisticoAtendimentoPorRacaCor.jasper";

	public static final String RELATORIO_CONTA_MODELO_3 = "/relatorioContaModelo3.jasper";

	public static final String RELATORIO_DADOS_IMOVEIS_COM_CONTA_EM_ATRASO = "/relatorioDadosImoveisComContaEmAtraso.txt";

	public static final String RELATORIO_SITUACAO_ESPECIAL_FATURAMENTO = "/relatorioSituacaoEspecialFaturamento.jasper";

	public static final String RELATORIO_ORDEM_CORTE_CAERD = "/relatorioOrdemCorteCAERD.jasper";

	public static final String RELATORIO_MANTER_ATIVIDADE_ECONOMICA = "/relatorioManterAtividadeEconomica.jasper";

	public static final String RELATORIO_CERTIDAO_POSITIVA_MODELO_1 = "/relatorioCertidaoPositivaModelo1.jasper";

	public static final String RELATORIO_CERTIDAO_NEGATIVA_MODELO_4 = "/relatorioCertidaoNegativaModelo4.jasper";

	public static final String RELATORIO_CERTIDAO_NEGATIVA_COM_EFEITO_POSITIVA_MODELO_4 = "/relatorioCertidaoNegativaComEfeitoPositivaModelo4.jasper";

	public static final String RELATORIO_CONTAS_RECALCULADAS = "/relatorioContasRecalculadas.jasper";

	public static final Object RELATORIO_ORDEM_SERVICO_BASE_COM_SUBRELATORIOS = "relatorioOrdemServicoBaseComSubrelatorios.jasper";

	public static final String SUBRELATORIO_ORDEM_SERVICO_LIGACAO_AGUA = "subrelatorioOrdemServicoLigacaoAgua.jasper";

	public static final String SUBRELATORIO_ORDEM_SERVICO_LIGACAO_ESGOTO = "subrelatorioOrdemServicoLigacaoEsgoto.jasper";

	public static final String SUBRELATORIO_ORDEM_SERVICO_MANUTENCAO_DRENAGEM = "subrelatorioOrdemServicoManutencaoDrenagem.jasper";

	public static final String SUBRELATORIO_ORDEM_SERVICO_MANUTENCAO_ESGOTO = "subrelatorioOrdemServicoManutencaoEsgoto.jasper";

	public static final String SUBRELATORIO_ORDEM_SERVICO_MANUTENCAO_AGUA = "subrelatorioOrdemServicoManutencaoAgua.jasper";

	public static final String SUBRELATORIO_ORDEM_SERVICO_REDE_AGUA = "subrelatorioOrdemServicoRedeAgua.jasper";

	public static final String SUBRELATORIO_ORDEM_SERVICO_REDE_ESGOTO = "subrelatorioOrdemServicoRedeEsgoto.jasper";

	public static final String SUBRELATORIO_ORDEM_SERVICO_REPAROS_ASFALTO = "subrelatorioOrdemServicoReparosAsfalto.jasper";

	public static final String SUBRELATORIO_ORDEM_SERVICO_REPAROS_ATERRO_LIMP = "subrelatorioOrdemServicoReparosAterroLimp.jasper";

	public static final String SUBRELATORIO_ORDEM_SERVICO_REPAROS_CALCADA = "subrelatorioOrdemServicoReparosCalcada.jasper";

	public static final String SUBRELATORIO_ORDEM_SERVICO_HIDROMETRIA = "subrelatorioOrdemServicoHidrometria.jasper";

	public static final String RELATORIO_RESUMO_ORDENS_SERVICO_DOC_COB = "/relatorioResumoOrdensServicoDocCob.jasper";

	private static Map<String, URL> relatorios = new HashMap();

	static{

		Field[] campos = ConstantesRelatorios.class.getFields();

		for(int i = 0; i < campos.length; i++){
			Field campo = campos[i];
			try{
				if(!campo.getName().equals("relatorios")){
					inicializarPropriedades((String) campo.get(null));
				}

			}catch(IllegalArgumentException e){
				e.printStackTrace();
				throw new SistemaException();
			}catch(IllegalAccessException e){
				e.printStackTrace();
				throw new SistemaException();
			}

		}

	}

	/**
	 * Inicializa as propriedades da classes ConstantesJNDI
	 */

	public static void inicializarPropriedades(String nomeArquivo){

		// InputStream stream;
		URL url;

		try{

			ClassLoader classLoader = ClassLoader.getSystemClassLoader();

			url = classLoader.getResource(nomeArquivo);

			// if system class loader not found try the this class classLoader

			if(url == null){
				url = ConstantesRelatorios.class.getClassLoader().getResource(nomeArquivo);
			}

			if(url == null){
				url = ConstantesRelatorios.class.getResource(nomeArquivo);
			}

			relatorios.put(nomeArquivo, url);

		}catch(Exception ex){

			ex.printStackTrace();

		}

	}

	public static URL getURLRelatorio(String nomeRelatorio){

		return relatorios.get(nomeRelatorio);

	}
}
