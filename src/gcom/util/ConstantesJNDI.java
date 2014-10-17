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

package gcom.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * ConstantesJDNI e uma classe que contem os nomes contidos no JNDI
 * 
 * @author Administrador
 */

public class ConstantesJNDI {

	/**
	 * Description of the Field
	 */

	public final static String NOME_ARQUIVO_PROPRIEDADES = "constantes_jndi.properties";

	// UTIL

	/**
	 * Description of the Field
	 */

	public static String FUNCOES_EJB = "";

	/**
	 * Description of the Field
	 */

	public static String CONTROLADOR_TABELA_AUXILIAR_SEJB = "";

	/**
	 * Description of the Field
	 */
	public static String CONTROLADOR_UTIL_SEJB = "";

	/**
	 * Description of the Field
	 */
	public static String CONTROLADOR_ENDERECO_SEJB = "";

	/**
	 * Description of the Field
	 */
	public static String CONTROLADOR_CLIENTE_SEJB = "";

	/**
	 * Description of the Field
	 */
	public static String CONTROLADOR_IMOVEL_SEJB = "";

	public static String CONTROLADOR_MICROMEDICAO_SEJB = "";

	public static String CONTROLADOR_COBRANCA_SEJB = "";

	public static String CONTROLADOR_PARCELAMENTO_SEJB = "";

	public static String CONTROLADOR_COBRANCA_ORDEM_CORTE_SEJB = "";

	public static String CONTROLADOR_COBRANCA_AVISO_DEBITO_SEJB = "";

	public static String CONTROLADOR_FINANCEIRO_SEJB = "";

	public static String CONTROLADOR_LOCALIDADE_SEJB = "";

	public static String CONTROLADOR_GEOGRAFICO_SEJB = "";

	public static String CONTROLADOR_FATURAMENTO_SEJB = "";

	public static String CONTROLADOR_TARIFA_SOCIAL_SEJB = "";

	public static String QUEUE_CONTROLADOR_FATURAMENTO_MDB = "";

	public static String QUEUE_CONTROLADOR_ARRECADACAO_MDB = "";

	public static String QUEUE_CONTROLADOR_MICROMEDICAO_MDB = "";

	public static String QUEUE_CONTROLADOR_COBRANCA_MDB = "";

	public static String CONTROLADOR_ARRECADACAO_SEJB = "";

	public static String CONTROLADOR_ACESSO_SEJB = "";

	public static String CONTROLADOR_TRANSACAO_SEJB = "";

	public static String CONTROLADOR_GERENCIAL_CADASTRO_SEJB = "";

	public static String CONTROLADOR_GERENCIAL_FATURAMENTO_SEJB = "";

	public static String CONTROLADOR_GERENCIAL_COBRANCA_SEJB = "";

	public static String CONTROLADOR_USUARIO_SEJB = "";

	public static String CONTROLADOR_GERENCIAL_SEJB = "";

	public static String CONTROLADOR_GERENCIAL_MICROMEDICAO_SEJB = "";

	public static String CONTROLADOR_GERENCIAL_ARRECADACAO_SEJB = "";

	public static String CONTROLADOR_ATENDIMENTO_PUBLICO_SEJB = "";

	public static String CONTROLADOR_UNIDADE_SEJB = "";

	public static String CONTROLADOR_REGISTRO_ATENDIMENTO_SEJB = "";

	public static String CONTROLADOR_ORDEM_SERVICO_SEJB = "";

	public static String CONSISTIR_LEITURAS_CALCULAR_CONSUMOS_MDB = "";

	public static String CONTROLADOR_BATCH_SEJB = "";

	public static String CONTROLADOR_LIGACAO_ESGOTO_SEJB = "";

	public static String CONTROLADOR_LIGACAO_AGUA_SEJB = "";

	public static String CONTROLADOR_PERMISSAO_ESPECIAL_SEJB = "";

	public static String CONTROLADOR_OPERACIONAL_SEJB = "";

	public static String CONTROLADOR_SPC_SERASA_SEJB = "";

	public static String CONTROLADOR_INTEGRACAO_PIRAMIDE_SEJB = "";

	public static String CONTROLADOR_INTEGRACAO_CAGEPA_FATURAMENTO_SEJB = "";

	public static String CONTROLADOR_INTEGRACAO_ACQUAGIS_SEJB = "";

	public static String BATCH_CONSISTIR_LEITURAS_CALCULAR_CONSUMOS_MDB = "";

	public static String BATCH_GERAR_FATURAMENTO_IMEDIATO_MDB = "";

	public static String BATCH_REGISTRAR_FATURAMENTO_IMEDIATO_MDB = "";

	public static String BATCH_GERAR_ARQUIVO_TEXTO_FATURAMENTO_IMEDIATO_MDB = "";

	public static String BATCH_GERAR_DADOS_PARA_LEITURA_MDB = "";

	public static String BATCH_EFETUAR_RATEIO_CONSUMO_MDB = "";

	public static String BATCH_VERIFICAR_ANORMALIDADES_CONSUMO_MDB = "";

	public static String BATCH_FATURAR_GRUPO_FATURAMENTO_MDB = "";

	public static String BATCH_SIMULAR_FATURAMENTO_MDB = "";

	public static String BATCH_EMITIR_CONTAS_MDB = "";

	public static String BATCH_GERAR_DEBITO_A_COBRAR_DE_ACRESCIMOS_POR_IMPONTUALIDADE_MDB = "";

	public static String BATCH_GERAR_TAXA_ENTREGA_OUTRO_ENDERECO_MDB = "";

	public static String BATCH_GERAR_DADOS_DIARIOS_ARRECADACAO_MDB = "";

	public static String BATCH_GERAR_ATIVIDADE_ACAO_COBRANCA_MDB = "";

	public static String BATCH_EMITIR_BOLETIM_CADASTRO_MDB = "";

	public static String CONTROLADOR_CADASTRO_SEJB = "";

	public static String BATCH_ENCERRAR_ARRECADACAO_MES_MDB = "";

	public static String BATCH_GERAR_DEBITOS_COBRAR_DOACAO_MDB = "";

	public static String BATCH_ENCERRAR_FATURAMENTO_MES_MDB = "";

	public static String BATCH_GERAR_RESUMO_SITUACAO_ESPECIAL_FATURAMENTO_MDB = "";

	public static String BATCH_GERAR_RESUMO_LIGACOES_ECONOMIAS_MDB = "";

	public static String BATCH_GERAR_RESUMO_ACOES_COBRANCA_CRONOGRAMA_MDB = "";

	public static String BATCH_INSERIR_RESUMO_ACAO_COBRANCA_CRONOGRAMA_MDB = "";

	public static String BATCH_INSERIR_RESUMO_ACAO_COBRANCA_EVENTUAL_MDB = "";

	public static String BATCH_GERAR_RESUMO_ACOES_COBRANCA_EVENTUAL_MDB = "";

	public static String BATCH_EMITIR_CONTAS_ORGAO_PUBLICO = "";

	public static String BATCH_GERAR_RESUMO_ANORMALIDADES_MDB = "";

	public static String BATCH_GERAR_RESUMO_SITUACAO_ESPECIAL_COBRANCA_MDB = "";

	public static String BATCH_EMITIR_EXTRATO_CONSUMO_IMOVEL_CONDOMINIO_MDB = "";

	public static String BATCH_GERAR_FATURA_CLIENTE_RESPONSAVEL_MDB = "";

	public static String BATCH_GERAR_HISTORICO_PARA_ENCERRAR_ARRECADACAO_MES_MDB = "";

	public static String BATCH_GERAR_HISTORICO_PARA_ENCERRAR_FATURAMENTO_MES_MDB = "";

	public static String BATCH_DESFAZER_PARCELAMENTO_POR_ENTRADA_NAO_PAGA_MDB = "";

	public static String BATCH_GERAR_HISTORICO_CONTA_MDB = "";

	public static String BATCH_GERAR_RESUMO_INSTALACOES_HIDROMETROS_MDB = "";

	public static String BATCH_GERAR_RESUMO_LEITURA_ANORMALIDADE_MDB = "";

	public static String BATCH_GERAR_RESUMO_ARRECADACAO_MDB = "";

	public static String BATCH_GERAR_RESUMO_PARCELAMENTO_MDB = "";

	public static String CONTROLADOR_GERENCIAL_IMOVEL_SEJB = "";

	public static String BATCH_GERAR_LANCAMENTOS_CONTABEIS_FATURAMENTO_MDB = "";

	public static String BATCH_GERAR_LANCAMENTOS_CONTABEIS_ARRECADACAO_MDB = "";

	public static String BATCH_GERAR_RESUMO_FATURAMENTO_AGUA_ESGOTO_MDB = "";

	public static String BATCH_GERAR_RESUMO_DEVEDORES_DUVIDOSOS_MDB = "";

	public static String BATCH_GERAR_LANCAMENTOS_CONTABEIS_DEVEDORES_DUVIDOSOS_MDB = "";

	public static String BATCH_GERAR_RESUMO_PENDENCIA = "";

	public static String BATCH_GERAR_ARQUIVO_TEXTO_PARA_LEITURISTA = "";

	public static String BATCH_EXECUTAR_COMANDO_NEGATIVACAO_MDB = "";

	public static String BATCH_GERAR_RESUMO_DIARIO_NEGATIVACAO_MDB = "";

	public static String BATCH_ATUALIZAR_LIGACAO_AGUA_LIGADO_ANALISE_PARA_LIGADO_MDB = "";

	public static String BATCH_ATUALIZAR_NUMERO_EXECUCAO_RESUMO_NEGATIVACAO_MDB = "";

	public static String BATCH_GERAR_MOVIMENTO_EXCLUSAO_NEGATIVACAO_MDB = "";

	public static String BATCH_DETERMINAR_CONFIRMACAO_NEGATIVACAO_MDB = "";

	public static String BATCH_GERAR_MOVIMENTO_RETORNO_NEGATIVACAO_MDB = "";

	public static String BATCH_GERAR_RESUMO_FATURAMENTO_MDB = "";

	public static String BATCH_GERAR_RESUMO_NEGATIVACAO_MDB = "";

	public static String BATCH_ACOMPANHAR_PAGAMENTO_DO_PARCELAMENTO_MDB = "";

	public static String BATCH_GERAR_CARTAS_DE_FINAL_DE_ANO_MDB = "";

	public static String BATCH_EMITIR_CARTAS_DE_FINAL_DE_ANO_MDB = "";

	public static String BATCH_EFETUAR_BAIXA_ORDENS_SERVICO_COBRANCA_MDB = "";

	public static String BATCH_EMITIR_DOCUMENTO_COBRANCA_MDB = "";

	public static String BATCH_GERAR_AVISO_CORTE_MDB = "";

	public static String BATCH_EMITIR_RELACAO_DOCUMENTOS_COBRANCA = "";

	// public static String BATCH_GERAR_MOVIMENTO_EXCLUSAO_EM_LOTE_NEGATIVACAO_MDB = "";

	public static String BATCH_GERAR_PROVISAO_DEVEDORES_DUVIDOSOS_MDB = "";

	public static String CONTROLADOR_CONTABIL_SEJB = "";

	public static String BATCH_GERAR_QUADRO_COMPARATIVO_FAT_ARREC_MDB = "";

	// ESQUEMA DOS CONTROLADORES
	// =====================================================================================================
	public static String CONTROLADOR_FATURAMENTO_COMPESA_SEJB = "";

	public static String CONTROLADOR_FATURAMENTO_CAERN_SEJB = "";

	public static String CONTROLADOR_FATURAMENTO_CAER_SEJB = "";

	public static String CONTROLADOR_FATURAMENTO_ADA_SEJB = "";

	// =====================================================================================================

	// ESQUEMA DOS CONTROLADORES
	// =====================================================================================================
	public static String CONTROLADOR_MICROMEDICAO_COMPESA_SEJB = "";

	public static String CONTROLADOR_MICROMEDICAO_CAERN_SEJB = "";

	public static String CONTROLADOR_MICROMEDICAO_CAER_SEJB = "";

	public static String CONTROLADOR_MICROMEDICAO_ADA_SEJB = "";

	// =====================================================================================================

	// ESQUEMA DOS CONTROLADORES
	// =====================================================================================================
	public static String CONTROLADOR_ARRECADACAO_COMPESA_SEJB = "";

	public static String CONTROLADOR_ARRECADACAO_CAERN_SEJB = "";

	public static String CONTROLADOR_ARRECADACAO_CAER_SEJB = "";

	public static String CONTROLADOR_ARRECADACAO_ADA_SEJB = "";

	// =====================================================================================================

	// ESQUEMA DOS CONTROLADORES
	// =====================================================================================================
	public static String CONTROLADOR_COBRANCA_COMPESA_SEJB = "";

	public static String CONTROLADOR_COBRANCA_CAERN_SEJB = "";

	public static String CONTROLADOR_COBRANCA_CAER_SEJB = "";

	public static String CONTROLADOR_COBRANCA_ADA_SEJB = "";

	// =====================================================================================================

	// ESQUEMA DOS CONTROLADORES
	// =====================================================================================================
	public static String CONTROLADOR_FINANCEIRO_COMPESA_SEJB = "";

	public static String CONTROLADOR_FINANCEIRO_CAERN_SEJB = "";

	public static String CONTROLADOR_FINANCEIRO_CAER_SEJB = "";

	public static String CONTROLADOR_FINANCEIRO_ADA_SEJB = "";

	// =====================================================================================================
	public static String CONTROLADOR_HISTOGRAMA_SEJB = "";

	// =====================================================================================================
	public static String BATCH_GERAR_RESUMO_CONSUMO_AGUA_MDB = "";

	public static String BATCH_GERAR_RESUMO_HIDROMETRO_MDB = "";

	public static String BATCH_GERAR_RESUMO_HISTOGRAMA_AGUA_ESGOTO = "";

	public static String BATCH_GERAR_RESUMO_REGISTRO_ATENDIMENTO_MDB = "";

	public static String CONTROLADOR_RELATORIO_FATURAMENTO_SEJB = "";

	public static String BATCH_GERAR_RESUMO_METAS_MDB = "";

	public static String BATCH_GERAR_RESUMO_METAS_ACUMULADO_MDB = "";

	public static String BATCH_GERAR_RESUMO_COLETA_ESGOTO_MDB = "";

	public static String BATCH_GERAR_CONTAS_A_RECEBER_CONTABIL_MDB = "";

	public static String BATCH_IDENTIFICAR_COBRANCA_BANCARIA_COM_NEGOCIACAO_MDB = "";

	public static String BATCH_GERAR_MULTA_POR_DESCUMPRIMENTO_PARCELAMENTO_MDB = "";

	public static String BATCH_REGISTRAR_MOVIMENTO_ARRECADORES_MDB = "";

	public static String BATCH_GERAR_ENVIAR_RELATORIO_RESUMO_MOVIMENTO_ARRECADACAO_MDB = "";

	public static String BATCH_GERAR_ARQUIVO_AGENCIA_VIRTUAL_WEB_MDB = "";

	public static String BATCH_RETIRAR_CONTA_REVISAO_PRAZO_VENCIDO_MDB = "";

	public static String BATCH_GERAR_RESUMO_ANORMALIDADE_CONSUMO_MDB = "";

	public static String BATCH_GERAR_ARQUIVO_AGENCIA_VIRTUAL_LOGRADOUROS_MDB = "";

	public static String BATCH_GERAR_ARQUIVO_AGENCIA_VIRTUAL_QUITACAO_DEBITOS_MDB = "";

	public static String BATCH_GERAR_ARQUIVO_AGENCIA_VIRTUAL_IMOVEIS_MDB = "";

	public static String BATCH_GERAR_ARQUIVO_AGENCIA_VIRTUAL_DEBITOS_MDB = "";

	public static String BATCH_CONCATENAR_ARQUIVOS_AGENCIA_VIRTUAL_MDB = "";

	public static String BATCH_GERAR_INTEGRACAO_RETENCAO_MDB = "";

	public static String BATCH_GERAR_INTEGRACAO_CONTABIL_MDB = "";

	public static String BATCH_GERAR_INTEGRACAO_CONTABIL_AJUSTE_TEMP_MDB = "";

	public static String BATCH_GERAR_INTEGRACAO_DEFERIMENTO_MDB = "";

	public static String BATCH_ATUALIZAR_SITUACAO_DEBITO_E_DA_ACAO_DOS_AVISOS_CORTE_E_CORTE_INDIVIDUAL_MDB = "";

	public static String BATCH_ENCERRAR_FATURAMENTO_GERAR_RESUMO_LIGACOES_ECONOMIAS_MDB = "";

	public static String BATCH_GERAR_PROVISAO_RECEITA_MDB = "";

	public static String BATCH_CLASSIFICAR_LOTE_PAGAMENTOS_NAO_CLASSIFICADOS_MDB = "";

	public static String BATCH_GERAR_INTEGRACAO_SPED_PIS_COFINS_MDB = "";

	// =====================================================================================================
	// ENCERRAR ARRECADACAO
	// =====================================================================================================
	public static String BATCH_ATUALIZAR_PDD_PARA_ENCERRAR_ARRECADACAO_MDB = "";

	public static String BATCH_ENCERRAR_ARRECADACAO_MDB = "";

	// =====================================================================================================
	// DECLARACAO ANUAL QUITACAO DEBITOS
	// =====================================================================================================
	public static String BATCH_GERAR_DECLARACAO_ANUAL_QUITACAO_DEBITOS_MDB = "";

	public static String BATCH_EMITIR_DECLARACAO_ANUAL_QUITACAO_DEBITOS_MDB = "";

	// =====================================================================================================
	// AJUSTE NA REMUNERAÇÃO POR COBRANÇA ADMINISTRATIVA
	// =====================================================================================================
	public static String BATCH_MARCAR_ITENS_REMUNERAVEIS_COBRANCA_ADMINISTRATIVA_MDB = "";

	public static String BATCH_GERAR_INTEGRACAO_ACQUAGIS_CONTA_ATUALIZADA_MDB = "";

	public static String BATCH_EFETIVAR_ALTERACAO_INSCRICAO_IMOVEL_MDB = "";

	public static String BATCH_CANCELAR_AVISO_DE_CORTE_PENDENTE_MDB = "";

	public static String BATCH_RELATORIO_FATURAMENTO_LIGACOES_MEDICAO_INDIVIDUALIZADA_MDB = "";

	public static String BATCH_RELATORIO_ACOMP_MOV_ARRECADADORES_MDB = "";

	public static String BATCH_AJUSTAR_ARRECADADOR_MOVIMENTO_ITEM_MDB = "";

	public static String BATCH_COMANDAR_PRESCRICAO_DEBITOS_MDB = "";

	public static String BATCH_COMANDAR_PRESCRICAO_AUTOMATICA_DEBITOS_MDB = "";

	static{

		inicializarPropriedades();

	}

	/**
	 * Inicializa as propriedades da classes ConstantesJNDI
	 */

	public static void inicializarPropriedades(){

		Properties propriedades = new Properties();

		InputStream stream;

		try{

			ClassLoader classLoader = ClassLoader.getSystemClassLoader();

			stream = classLoader.getResourceAsStream(NOME_ARQUIVO_PROPRIEDADES);

			// if system class loader not found try the this class classLoader

			if(stream == null){

				stream = gcom.util.ConstantesJNDI.class.getClassLoader().getResourceAsStream(NOME_ARQUIVO_PROPRIEDADES);

			}

			if(stream == null){

				stream = gcom.util.ConstantesJNDI.class.getResourceAsStream(NOME_ARQUIVO_PROPRIEDADES);

			}

			propriedades.load(stream);
			// UTIL

			// FUNCOES_EJB = propriedades.getProperty("funcoesEJB");

			// Gerencail Cadastro
			CONTROLADOR_USUARIO_SEJB = propriedades.getProperty("ControladorUsuario");

			// Gerencail Cadastro
			CONTROLADOR_GERENCIAL_CADASTRO_SEJB = propriedades.getProperty("ControladorGerencialCadastro");

			// Gerencail Cobranca
			CONTROLADOR_GERENCIAL_COBRANCA_SEJB = propriedades.getProperty("ControladorGerencialCobranca");

			// Gerencail Faturamento
			CONTROLADOR_GERENCIAL_FATURAMENTO_SEJB = propriedades.getProperty("ControladorGerencialFaturamento");

			// Gerencail Imóvel
			CONTROLADOR_GERENCIAL_IMOVEL_SEJB = propriedades.getProperty("ControladorGerencialImovel");

			// Cobranca
			CONTROLADOR_COBRANCA_SEJB = propriedades.getProperty("ControladorCobranca");

			// Parcelamento
			CONTROLADOR_PARCELAMENTO_SEJB = propriedades.getProperty("ControladorParcelamento");

			// Cobranca Ordem Corte
			CONTROLADOR_COBRANCA_ORDEM_CORTE_SEJB = propriedades.getProperty("ControladorCobrancaOrdemCorte");

			// Cobranca Aviso Debito
			CONTROLADOR_COBRANCA_AVISO_DEBITO_SEJB = propriedades.getProperty("ControladorCobrancaAvisoDebito");

			// Financeiro
			CONTROLADOR_FINANCEIRO_SEJB = propriedades.getProperty("ControladorFinanceiro");

			// Gerencial
			CONTROLADOR_GERENCIAL_SEJB = propriedades.getProperty("ControladorGerencial");

			CONTROLADOR_ACESSO_SEJB = propriedades.getProperty("ControladorAcesso");

			CONTROLADOR_TRANSACAO_SEJB = propriedades.getProperty("ControladorTransacao");

			CONTROLADOR_TABELA_AUXILIAR_SEJB = propriedades.getProperty("ControladorTabelaAuxiliar");

			CONTROLADOR_UTIL_SEJB = propriedades.getProperty("ControladorUtil");

			CONTROLADOR_ENDERECO_SEJB = propriedades.getProperty("ControladorEndereco");

			CONTROLADOR_CLIENTE_SEJB = propriedades.getProperty("ControladorCliente");

			CONTROLADOR_IMOVEL_SEJB = propriedades.getProperty("ControladorImovel");

			CONTROLADOR_MICROMEDICAO_SEJB = propriedades.getProperty("ControladorMicromedicao");

			CONTROLADOR_LOCALIDADE_SEJB = propriedades.getProperty("ControladorLocalidade");

			CONTROLADOR_GEOGRAFICO_SEJB = propriedades.getProperty("ControladorGeografico");

			CONTROLADOR_FATURAMENTO_SEJB = propriedades.getProperty("ControladorFaturamento");

			CONTROLADOR_TARIFA_SOCIAL_SEJB = propriedades.getProperty("ControladorTarifaSocial");

			QUEUE_CONTROLADOR_FATURAMENTO_MDB = propriedades.getProperty("QueueControladorFaturamento");

			QUEUE_CONTROLADOR_ARRECADACAO_MDB = propriedades.getProperty("QueueControladorArrecadacao");

			QUEUE_CONTROLADOR_MICROMEDICAO_MDB = propriedades.getProperty("QueueControladorMicromedicao");

			QUEUE_CONTROLADOR_COBRANCA_MDB = propriedades.getProperty("QueueControladorCobranca");

			CONTROLADOR_ARRECADACAO_SEJB = propriedades.getProperty("ControladorArrecadacao");

			CONTROLADOR_GERENCIAL_MICROMEDICAO_SEJB = propriedades.getProperty("ControladorGerencialMicromedicao");

			CONTROLADOR_GERENCIAL_ARRECADACAO_SEJB = propriedades.getProperty("ControladorGerencialArrecadacao");

			CONTROLADOR_ATENDIMENTO_PUBLICO_SEJB = propriedades.getProperty("ControladorAtendimentoPublico");

			CONTROLADOR_BATCH_SEJB = propriedades.getProperty("ControladorBatch");

			CONTROLADOR_UNIDADE_SEJB = propriedades.getProperty("ControladorUnidade");

			CONTROLADOR_REGISTRO_ATENDIMENTO_SEJB = propriedades.getProperty("ControladorRegistroAtendimento");

			CONTROLADOR_ORDEM_SERVICO_SEJB = propriedades.getProperty("ControladorOrdemServico");

			CONTROLADOR_LIGACAO_ESGOTO_SEJB = propriedades.getProperty("ControladorLigacaoEsgoto");

			CONTROLADOR_LIGACAO_AGUA_SEJB = propriedades.getProperty("ControladorLigacaoAgua");

			CONTROLADOR_SPC_SERASA_SEJB = propriedades.getProperty("ControladorSpcSerasa");

			BATCH_CONSISTIR_LEITURAS_CALCULAR_CONSUMOS_MDB = propriedades.getProperty("QueueBatchConsistirLeiturasCalcularConsumosMDB");

			BATCH_GERAR_FATURAMENTO_IMEDIATO_MDB = propriedades.getProperty("QueueBatchGerarFaturamentoImediatoMDB");

			BATCH_REGISTRAR_FATURAMENTO_IMEDIATO_MDB = propriedades.getProperty("QueueBatchRegistrarFaturamentoImediatoMDB");

			BATCH_GERAR_ARQUIVO_TEXTO_FATURAMENTO_IMEDIATO_MDB = propriedades
							.getProperty("QueueBatchGerarArquivoTextoFaturamentoImediatoMDB");

			BATCH_GERAR_DADOS_PARA_LEITURA_MDB = propriedades.getProperty("QueueBatchGerarDadosParaLeituraMDB");

			BATCH_FATURAR_GRUPO_FATURAMENTO_MDB = propriedades.getProperty("QueueBatchFaturarGrupoFaturamentoMDB");

			BATCH_SIMULAR_FATURAMENTO_MDB = propriedades.getProperty("QueueBatchSimularFaturamentoMDB");

			BATCH_EFETUAR_RATEIO_CONSUMO_MDB = propriedades.getProperty("QueueBatchEfetuarRateioConsumoMDB");

			BATCH_VERIFICAR_ANORMALIDADES_CONSUMO_MDB = propriedades.getProperty("QueueBatchVerificarAnormalidadesConsumoMDB");

			BATCH_GERAR_DEBITO_A_COBRAR_DE_ACRESCIMOS_POR_IMPONTUALIDADE_MDB = propriedades
							.getProperty("QueueBatchGerarDebitoACobrarDeAcrescimoPorImpontualidadeMDB");

			BATCH_GERAR_TAXA_ENTREGA_OUTRO_ENDERECO_MDB = propriedades.getProperty("QueueBatchGerarTaxaEntregaOutroEnderecoMDB");

			BATCH_GERAR_DADOS_DIARIOS_ARRECADACAO_MDB = propriedades.getProperty("QueueBatchGerarDadosDiariosArrecadacaoMDB");

			BATCH_GERAR_ATIVIDADE_ACAO_COBRANCA_MDB = propriedades.getProperty("QueueBatchGerarAtividadeAcaoCobrancaMDB");

			BATCH_EMITIR_BOLETIM_CADASTRO_MDB = propriedades.getProperty("QueueBatchEmitirBoletimCadastroMDB");

			BATCH_EMITIR_CONTAS_MDB = propriedades.getProperty("QueueBatchEmitirContasMDB");

			CONTROLADOR_BATCH_SEJB = propriedades.getProperty("ControladorBatch");

			CONTROLADOR_PERMISSAO_ESPECIAL_SEJB = propriedades.getProperty("ControladorPermissaoEspecial");

			CONTROLADOR_OPERACIONAL_SEJB = propriedades.getProperty("ControladorOperacional");

			CONTROLADOR_CADASTRO_SEJB = propriedades.getProperty("ControladorCadastro");

			BATCH_ENCERRAR_ARRECADACAO_MES_MDB = propriedades.getProperty("QueueBatchEncerrarArrecadacaoMesMDB");

			BATCH_GERAR_DEBITOS_COBRAR_DOACAO_MDB = propriedades.getProperty("QueueBatchGerarDebitoACobrarDoacaoMDB");

			BATCH_ENCERRAR_FATURAMENTO_MES_MDB = propriedades.getProperty("QueueBatchEncerrarFaturamentoMesMDB");

			BATCH_GERAR_RESUMO_LIGACOES_ECONOMIAS_MDB = propriedades.getProperty("QueueBatchGerarResumoLigacoesEconomiasMDB");

			BATCH_GERAR_RESUMO_SITUACAO_ESPECIAL_FATURAMENTO_MDB = propriedades
							.getProperty("QueueBatchGerarResumoSituacaoEspecialFaturamentoMDB");

			BATCH_GERAR_RESUMO_ACOES_COBRANCA_CRONOGRAMA_MDB = propriedades.getProperty("QueueBatchGerarResumoAcoesCobrancaCronogramaMDB");

			BATCH_INSERIR_RESUMO_ACAO_COBRANCA_CRONOGRAMA_MDB = propriedades
							.getProperty("QueueBatchInserirResumoAcoesCobrancaCronogramaMDB");

			BATCH_INSERIR_RESUMO_ACAO_COBRANCA_EVENTUAL_MDB = propriedades.getProperty("QueueBatchInserirResumoAcoesCobrancaEventualMDB");

			BATCH_GERAR_RESUMO_ACOES_COBRANCA_EVENTUAL_MDB = propriedades.getProperty("QueueBatchGerarResumoAcoesCobrancaEventualMDB");

			BATCH_GERAR_RESUMO_ANORMALIDADES_MDB = propriedades.getProperty("QueueBatchGerarResumoAnormalidadesMDB");

			BATCH_GERAR_RESUMO_CONSUMO_AGUA_MDB = propriedades.getProperty("QueueBatchGerarResumoConsumoAguaMDB");

			BATCH_GERAR_RESUMO_HIDROMETRO_MDB = propriedades.getProperty("QueueBatchGerarResumoHidrometroMDB");

			BATCH_GERAR_RESUMO_HISTOGRAMA_AGUA_ESGOTO = propriedades.getProperty("QueueBatchGerarResumoHistogramaAguaEsgotoMDB");

			BATCH_GERAR_RESUMO_SITUACAO_ESPECIAL_COBRANCA_MDB = propriedades
							.getProperty("QueueBatchGerarResumoSituacaoEspecialCobrancaMDB");

			BATCH_EMITIR_EXTRATO_CONSUMO_IMOVEL_CONDOMINIO_MDB = propriedades
							.getProperty("QueueBatchEmitirExtratoConsumoImovelCondominioMDB");

			BATCH_GERAR_FATURA_CLIENTE_RESPONSAVEL_MDB = propriedades.getProperty("QueueBatchGerarFaturaClienteResponsavelMDB");

			BATCH_GERAR_HISTORICO_PARA_ENCERRAR_ARRECADACAO_MES_MDB = propriedades
							.getProperty("QueueBatchGerarHistoricoParaEncerrarArrecadacaoMesMDB");

			BATCH_GERAR_HISTORICO_PARA_ENCERRAR_FATURAMENTO_MES_MDB = propriedades
							.getProperty("QueueBatchGerarHistoricoParaEncerrarFaturamentoMesMDB");

			BATCH_DESFAZER_PARCELAMENTO_POR_ENTRADA_NAO_PAGA_MDB = propriedades
							.getProperty("QueueBatchDesfazerParcelamentoPorEntradaNaoPagaMDB");

			BATCH_GERAR_HISTORICO_CONTA_MDB = propriedades.getProperty("QueueBatchGerarHistoricoContaMDB");

			BATCH_GERAR_RESUMO_INSTALACOES_HIDROMETROS_MDB = propriedades.getProperty("QueueBatchGerarResumoInstalacoesHidrometrosMDB");

			BATCH_GERAR_RESUMO_LEITURA_ANORMALIDADE_MDB = propriedades

			.getProperty("QueueBatchGerarResumoLeituraAnormalidadeMDB");

			BATCH_GERAR_RESUMO_ARRECADACAO_MDB = propriedades.getProperty("QueueBatchGerarResumoArrecadacaoMDB");

			BATCH_GERAR_RESUMO_PARCELAMENTO_MDB = propriedades.getProperty("QueueBatchGerarResumoParcelamentoMDB");

			BATCH_GERAR_LANCAMENTOS_CONTABEIS_FATURAMENTO_MDB = propriedades

			.getProperty("QueueBatchGerarLancamentosContabeisFaturamentoMDB");

			BATCH_GERAR_LANCAMENTOS_CONTABEIS_ARRECADACAO_MDB = propriedades
							.getProperty("QueueBatchGerarLancamentosContabeisArrecadacaoMDB");

			BATCH_GERAR_RESUMO_FATURAMENTO_AGUA_ESGOTO_MDB = propriedades.getProperty("QueueBatchGerarResumoFaturamentoAguaEsgotoMDB");

			BATCH_GERAR_RESUMO_REGISTRO_ATENDIMENTO_MDB = propriedades.getProperty("QueueBatchGerarResumoRegistroAtendimentoMDB");

			BATCH_EMITIR_CONTAS_ORGAO_PUBLICO = propriedades.getProperty("QueueBatchEmitirContasOrgaoPublicoMDB");

			BATCH_GERAR_RESUMO_METAS_MDB = propriedades.getProperty("QueueBatchGerarResumoMetasMDB");

			BATCH_GERAR_RESUMO_METAS_ACUMULADO_MDB = propriedades.getProperty("QueueBatchGerarResumoMetasAcumuladoMDB");

			BATCH_EFETUAR_BAIXA_ORDENS_SERVICO_COBRANCA_MDB = propriedades.getProperty("QueueBatchEfetuarBaixaOrdensServicoCobrancaMDB");

			BATCH_GERAR_RESUMO_DEVEDORES_DUVIDOSOS_MDB = propriedades.getProperty("QueueBatchGerarResumoDevedoresDuvidososMDB");

			BATCH_GERAR_LANCAMENTOS_CONTABEIS_DEVEDORES_DUVIDOSOS_MDB = propriedades
							.getProperty("QueueBatchGerarLancamentosContabeisDevedoresDuvidososMDB");

			BATCH_GERAR_RESUMO_PENDENCIA = propriedades.getProperty("QueueBatchGerarResumoPendenciaMDB");

			BATCH_GERAR_ARQUIVO_TEXTO_PARA_LEITURISTA = propriedades.getProperty("QueueBatchGerarArquivoTextoParaLeituristaMDB");

			BATCH_GERAR_RESUMO_COLETA_ESGOTO_MDB = propriedades.getProperty("QueueBatchGerarResumoColetaEsgotoMDB");

			BATCH_GERAR_CONTAS_A_RECEBER_CONTABIL_MDB = propriedades.getProperty("QueueBatchGerarContasAReceberContabilMDB");

			BATCH_EMITIR_DOCUMENTO_COBRANCA_MDB = propriedades.getProperty("QueueBatchEmitirDocumentoCobrancaMDB");

			BATCH_GERAR_AVISO_CORTE_MDB = propriedades.getProperty("QueueBatchGerarAvisoCorteMDB");

			BATCH_EMITIR_RELACAO_DOCUMENTOS_COBRANCA = propriedades.getProperty("QueueBatchEmitirRelacaoDocumentosCobrancaMDB");

			BATCH_GERAR_QUADRO_COMPARATIVO_FAT_ARREC_MDB = propriedades.getProperty("QueueBatchGerarQuadroComparativoFatEArrecMDB");

			CONTROLADOR_CONTABIL_SEJB = propriedades.getProperty("ControladorContabil");

			// ESQUEMA DOS CONTROLADORES
			// =====================================================================================================
			CONTROLADOR_FATURAMENTO_COMPESA_SEJB = propriedades.getProperty("ControladorFaturamentoCOMPESA");

			CONTROLADOR_FATURAMENTO_CAER_SEJB = propriedades.getProperty("ControladorFaturamentoCAER");

			CONTROLADOR_FATURAMENTO_CAERN_SEJB = propriedades.getProperty("ControladorFaturamentoCAERN");
			// =====================================================================================================

			// ESQUEMA DOS CONTROLADORES
			// =====================================================================================================
			CONTROLADOR_MICROMEDICAO_COMPESA_SEJB = propriedades.getProperty("ControladorMicromedicaoCOMPESA");

			CONTROLADOR_MICROMEDICAO_CAER_SEJB = propriedades.getProperty("ControladorMicromedicaoCAER");

			CONTROLADOR_MICROMEDICAO_CAERN_SEJB = propriedades.getProperty("ControladorMicromedicaoCAERN");
			// =====================================================================================================

			// ESQUEMA DOS CONTROLADORES
			// =====================================================================================================
			CONTROLADOR_ARRECADACAO_COMPESA_SEJB = propriedades.getProperty("ControladorArrecadacaoCOMPESA");

			CONTROLADOR_ARRECADACAO_CAER_SEJB = propriedades.getProperty("ControladorArrecadacaoCAER");

			CONTROLADOR_ARRECADACAO_CAERN_SEJB = propriedades.getProperty("ControladorArrecadacaoCAERN");
			// =====================================================================================================

			// ESQUEMA DOS CONTROLADORES
			// =====================================================================================================
			CONTROLADOR_COBRANCA_COMPESA_SEJB = propriedades.getProperty("ControladorCobrancaCOMPESA");

			CONTROLADOR_COBRANCA_CAER_SEJB = propriedades.getProperty("ControladorCobrancaCAER");

			CONTROLADOR_COBRANCA_CAERN_SEJB = propriedades.getProperty("ControladorCobrancaCAERN");
			// =====================================================================================================

			// ESQUEMA DOS CONTROLADORES
			// =====================================================================================================
			CONTROLADOR_FINANCEIRO_COMPESA_SEJB = propriedades.getProperty("ControladorFinanceiroCOMPESA");

			CONTROLADOR_FINANCEIRO_CAER_SEJB = propriedades.getProperty("ControladorFinanceiroCAER");

			CONTROLADOR_FINANCEIRO_CAERN_SEJB = propriedades.getProperty("ControladorFinanceiroCAERN");
			// =====================================================================================================

			CONTROLADOR_RELATORIO_FATURAMENTO_SEJB = propriedades.getProperty("ControladorRelatorioFaturamento");

			CONTROLADOR_HISTOGRAMA_SEJB = propriedades.getProperty("ControladorHistograma");

			BATCH_GERAR_RESUMO_DIARIO_NEGATIVACAO_MDB = propriedades.getProperty("QueueBatchGerarResumoDiarioNegativacaoMDB");

			BATCH_EXECUTAR_COMANDO_NEGATIVACAO_MDB = propriedades.getProperty("QueueExecutarComandoNegativacaoMDB");

			BATCH_ATUALIZAR_NUMERO_EXECUCAO_RESUMO_NEGATIVACAO_MDB = propriedades
							.getProperty("QueueBatchAtualizarNumeroExecucaoResumoNegativacaoMDB");

			BATCH_GERAR_MOVIMENTO_EXCLUSAO_NEGATIVACAO_MDB = propriedades.getProperty("QueueBatchGerarMovimentoExclusaoNegativacaoMDB");

			BATCH_DETERMINAR_CONFIRMACAO_NEGATIVACAO_MDB = propriedades.getProperty("QueueBatchDeterminarConfirmacaoNegativacaoMDB");

			BATCH_GERAR_MOVIMENTO_RETORNO_NEGATIVACAO_MDB = propriedades.getProperty("QueueBatchGerarMovimentoRetornoNegativacaoMDB");

			BATCH_IDENTIFICAR_COBRANCA_BANCARIA_COM_NEGOCIACAO_MDB = propriedades
							.getProperty("QueueBatchIdentificarCobrancaBancariaComNegociacaoMDB");

			// BATCH_GERAR_MOVIMENTO_EXCLUSAO_EM_LOTE_NEGATIVACAO_MDB =
			// propriedades.getProperty("QueueBatchGerarMovimentoExclusaoEmLoteNegativacaoMDB");

			CONTROLADOR_HISTOGRAMA_SEJB = propriedades.getProperty("ControladorHistograma");

			BATCH_GERAR_PROVISAO_DEVEDORES_DUVIDOSOS_MDB = propriedades.getProperty("QueueBatchProvisaoDevedoresDuvidososMDB");

			BATCH_GERAR_MULTA_POR_DESCUMPRIMENTO_PARCELAMENTO_MDB = propriedades
							.getProperty("QueueBatchGerarMultaPorDescumprimentoParcelamentoMDB");

			BATCH_REGISTRAR_MOVIMENTO_ARRECADORES_MDB = propriedades.getProperty("QueueBatchRegistrarMovimentoArrecadadoresMDB");

			BATCH_GERAR_ENVIAR_RELATORIO_RESUMO_MOVIMENTO_ARRECADACAO_MDB = propriedades
							.getProperty("QueueBatchGerarEnviarRelatorioResumoMovimentoArrecadacaoMDB");

			BATCH_GERAR_ARQUIVO_AGENCIA_VIRTUAL_WEB_MDB = propriedades.getProperty("QueueBatchGerarArquivoAgenciaVirtualWebMDB");

			BATCH_RETIRAR_CONTA_REVISAO_PRAZO_VENCIDO_MDB = propriedades.getProperty("QueueBatchRetirarContaRevisaoPrazoVencidoMDB");

			BATCH_GERAR_RESUMO_ANORMALIDADE_CONSUMO_MDB = propriedades.getProperty("QueueBatchGerarResumoAnormalidadeConsumoMDB");

			BATCH_GERAR_ARQUIVO_AGENCIA_VIRTUAL_LOGRADOUROS_MDB = propriedades
							.getProperty("QueueBatchGerarArquivoAgenciaVirtualLogradourosMDB");

			BATCH_GERAR_ARQUIVO_AGENCIA_VIRTUAL_QUITACAO_DEBITOS_MDB = propriedades
							.getProperty("QueueBatchGerarArquivoAgenciaVirtualQuitacaoDebitosMDB");

			BATCH_GERAR_ARQUIVO_AGENCIA_VIRTUAL_IMOVEIS_MDB = propriedades.getProperty("QueueBatchGerarArquivoAgenciaVirtualImoveisMDB");

			BATCH_GERAR_ARQUIVO_AGENCIA_VIRTUAL_DEBITOS_MDB = propriedades.getProperty("QueueBatchGerarArquivoAgenciaVirtualDebitosMDB");

			BATCH_CONCATENAR_ARQUIVOS_AGENCIA_VIRTUAL_MDB = propriedades.getProperty("QueueBatchConcatenarArquivosAgenciaVirtualMDB");

			BATCH_GERAR_INTEGRACAO_RETENCAO_MDB = propriedades.getProperty("QueueBatchGerarIntegracaoRetencaoMDB");

			BATCH_GERAR_INTEGRACAO_DEFERIMENTO_MDB = propriedades.getProperty("QueueBatchGerarIntegracaoDeferimentoMDB");

			// Integracao
			CONTROLADOR_INTEGRACAO_PIRAMIDE_SEJB = propriedades.getProperty("ControladorIntegracaoPiramide");

			BATCH_GERAR_INTEGRACAO_CONTABIL_MDB = propriedades.getProperty("QueueBatchGerarIntegracaoContabilMDB");

			BATCH_GERAR_INTEGRACAO_CONTABIL_AJUSTE_TEMP_MDB = propriedades.getProperty("QueueBatchGerarIntegracaoContabilAjusteTempMDB");

			BATCH_ATUALIZAR_SITUACAO_DEBITO_E_DA_ACAO_DOS_AVISOS_CORTE_E_CORTE_INDIVIDUAL_MDB = propriedades
							.getProperty("QueueBatchAtualizarSituacaoDebitoEDaAcaoDosAvisosCorteECorteIndividualMDB");

			CONTROLADOR_INTEGRACAO_CAGEPA_FATURAMENTO_SEJB = propriedades.getProperty("ControladorIntegracaoCagepaFaturamento");

			BATCH_ENCERRAR_FATURAMENTO_GERAR_RESUMO_LIGACOES_ECONOMIAS_MDB = propriedades
							.getProperty("QueueBatchEncerrarFaturamentoGerarResumoLigacoesEconomiasMDB");

			BATCH_GERAR_PROVISAO_RECEITA_MDB = propriedades.getProperty("QueueBatchGerarProvisaoReceitaMDB");

			// =====================================================================================================
			// ENCERRAR ARRECADACAO
			// =====================================================================================================
			BATCH_ATUALIZAR_PDD_PARA_ENCERRAR_ARRECADACAO_MDB = propriedades
							.getProperty("QueueBatchAtualizarPDDParaEncerrarArrecadacaoMDB");

			BATCH_ENCERRAR_ARRECADACAO_MDB = propriedades.getProperty("QueueBatchEncerrarArrecadacaoMDB");

			BATCH_CLASSIFICAR_LOTE_PAGAMENTOS_NAO_CLASSIFICADOS_MDB = propriedades
							.getProperty("QueueBatchClassificarLotePagamentosNaoClassificadosMDB");

			BATCH_GERAR_INTEGRACAO_SPED_PIS_COFINS_MDB = propriedades.getProperty("QueueBatchGerarIntegracaoSpedPisCofinsMDB");

			BATCH_GERAR_DECLARACAO_ANUAL_QUITACAO_DEBITOS_MDB = propriedades
							.getProperty("QueueBatchGerarDeclaracaoAnualQuitacaoDebitosMDB");

			BATCH_EMITIR_DECLARACAO_ANUAL_QUITACAO_DEBITOS_MDB = propriedades
							.getProperty("QueueBatchEmitirDeclaracaoAnualQuitacaoDebitosMDB");

			BATCH_MARCAR_ITENS_REMUNERAVEIS_COBRANCA_ADMINISTRATIVA_MDB = propriedades
							.getProperty("QueueBatchMarcarItensRemuneraveisCobrancaAdministrativaMDB");

			CONTROLADOR_INTEGRACAO_ACQUAGIS_SEJB = propriedades.getProperty("ControladorIntegracaoAcquaGis");

			BATCH_GERAR_INTEGRACAO_ACQUAGIS_CONTA_ATUALIZADA_MDB = propriedades
							.getProperty("QueueBatchGerarIntegracaoAcquaGisContaAtualizadaMDB");

			BATCH_EFETIVAR_ALTERACAO_INSCRICAO_IMOVEL_MDB = propriedades.getProperty("QueueBatchEfetivarAlteracaoInscricaoImovelMDB");

			BATCH_CANCELAR_AVISO_DE_CORTE_PENDENTE_MDB = propriedades.getProperty("QueueBatchCancelarAvisoDeCortePendenteMDB");

			BATCH_RELATORIO_FATURAMENTO_LIGACOES_MEDICAO_INDIVIDUALIZADA_MDB = propriedades
							.getProperty("QueueRelatorioFaturamentoLigacoesMedicaoIndividualizadaMDB");

			BATCH_RELATORIO_ACOMP_MOV_ARRECADADORES_MDB = propriedades.getProperty("QueueRelatorioAcompanhamentoMovimentoArrecadadoresMDB");

			BATCH_AJUSTAR_ARRECADADOR_MOVIMENTO_ITEM_MDB = propriedades.getProperty("QueueBatchAjustarArrecadadorMovimentoItemMDB");

			BATCH_COMANDAR_PRESCRICAO_DEBITOS_MDB = propriedades.getProperty("QueueBatchComandarPrescricaoDebitosMDB");

			BATCH_COMANDAR_PRESCRICAO_AUTOMATICA_DEBITOS_MDB = propriedades.getProperty("QueueBatchComandarPrescricaoAutomaticaDebitosMDB");

			stream.close();

		}catch(Exception ex){

			ex.printStackTrace();

		}

	}

}