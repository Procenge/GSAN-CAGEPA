
package gcom.util.parametrizacao.arrecadacao;

import gcom.util.parametrizacao.Parametro;

public class ParametroArrecadacao
				extends Parametro {

	/*
	 * Método para validar campos do registro código F – retorno do débito automático.
	 */
	public static final Parametro P_VALIDAR_CAMPOS_RETORNO_DEB_AUTOMATICO_CODIGO_F = new ParametroArrecadacao(
					"P_VALIDAR_CAMPOS_RETORNO_DEB_AUTOMATICO_CODIGO_F");

	/*
	 * Método para atualizar dados do débito automático a depender do tipo de documento – retorno do
	 * débito automático.
	 */
	public static final Parametro P_ATUALIZAR_DEB_AUTOMATICO_REGISTRO_CODIGO_F = new ParametroArrecadacao(
					"P_ATUALIZAR_DEB_AUTOMATICO_REGISTRO_CODIGO_F");

	public static final Parametro P_FORMATAR_CAMPO_ENVIO_DEB_AUTOMATICO_CODIGO_E = new ParametroArrecadacao(
					"P_FORMATAR_CAMPO_ENVIO_DEB_AUTOMATICO_CODIGO_E");

	/*
	 * Método para distribuir os pagamentos em código de barras legados dos clientes.
	 */
	public static final Parametro P_DISTRIBUIR_PAGAMENTO_CODIGO_BARRAS_LEGADO = new ParametroArrecadacao(
					"P_DISTRIBUIR_PAGAMENTO_CODIGO_BARRAS_LEGADO");

	/*
	 * Parâmetro para indicar o tamanho do idImóvel do movimento de débito automático, quando conta
	 * for diferente de nulo.
	 */
	public static final Parametro P_NUMERO_DIGITOS_MATRICULA_IMOVEL = new ParametroArrecadacao("P_NUMERO_DIGITOS_MATRICULA_IMOVEL");

	/*
	 * Método para formatar o header do movimento débito automático de acordo com a empresa.
	 */
	public static final Parametro P_FORMATAR_HEADER_DEB_AUTOMATICO = new ParametroArrecadacao("P_FORMATAR_HEADER_DEB_AUTOMATICO");

	/*
	 * Método para formatar o trailler do movimento débito automático de acordo com a empresa.
	 */
	public static final Parametro P_FORMATAR_TRAILLER_DEB_AUTOMATICO = new ParametroArrecadacao("P_FORMATAR_TRAILLER_DEB_AUTOMATICO");

	/*
	 * Parâmetro para identificar o tipo de débito da guia para inclusão do imóvel no programa água
	 * para todos
	 */
	public static final Parametro P_IDENTIFICACAO_DEBITO_TIPO_PROGRAMA_AGUA_PARA_TODOS = new ParametroArrecadacao(
					"P_IDENTIFICACAO_DEBITO_TIPO_PROGRAMA_AGUA_PARA_TODOS");

	/*
	 * Parâmetro para indicar o percentual do desconto concedido ao débito das contas para a
	 * inclusão do imóvel no programa água para todos
	 */
	public static final Parametro P_PERCENTUAL_DESCONTO_PROGRAMA_AGUA_PARA_TODOS = new ParametroArrecadacao(
					"P_PERCENTUAL_DESCONTO_PROGRAMA_AGUA_PARA_TODOS");

	/*
	 * Parâmetro para indicar o valor máximo da guia de pagamento para inclusão do imóvel no
	 * programa água para todos
	 */
	public static final Parametro P_VALOR_MAXIMO_GUIA_PROGRAMA_AGUA_PARA_TODOS = new ParametroArrecadacao(
					"P_VALOR_MAXIMO_GUIA_PROGRAMA_AGUA_PARA_TODOS");

	/*
	 * Parâmetro para indicar o número de dias para cálculo da data de vencimento da guia para
	 * inclusão do imóvel no programa água para todos
	 */
	public static final Parametro P_NUMERO_DIAS_VENCIMENTO_GUIA_PROGRAMA_AGUA_PARA_TODOS = new ParametroArrecadacao(
					"P_NUMERO_DIAS_VENCIMENTO_GUIA_PROGRAMA_AGUA_PARA_TODOS");

	/*
	 * Parâmetro para indicar a identificação do motivo de revisão das contas para inclusão do
	 * imóvel no programa água para todos
	 */
	public static final Parametro P_MOTIVO_REVISAO_PROGRAMA_AGUA_PARA_TODOS = new ParametroArrecadacao(
					"P_MOTIVO_REVISAO_PROGRAMA_AGUA_PARA_TODOS");

	/*
	 * Parâmetro para indicar a identificação do motivo de cancelamento das contas do programa água
	 * para todos em razão do pagamento da NR/Guia
	 */
	public static final Parametro P_MOTIVO_CANCELAMENTO_PROGRAMA_AGUA_PARA_TODOS = new ParametroArrecadacao(
					"P_MOTIVO_CANCELAMENTO_PROGRAMA_AGUA_PARA_TODOS");

	/*
	 * Parâmetro para indicar o número de caracteres para o ID da Guia de Pagamento no Código de
	 * Barras. Será usado para Criação e Leitura (distribuição) do código de barras referente a Guia
	 * (Imóvel ou Cliente).
	 */
	public static final Parametro P_TAMANHO_CAMPO_GUIA_PAGAMENTO_CODIGO_BARRAS = new ParametroArrecadacao(
					"P_TAMANHO_CAMPO_GUIA_PAGAMENTO_CODIGO_BARRAS");

	/*
	 * Parâmetro de permissao de pagamento com o valor original sem considerar imposto deduzidos
	 */
	public static final Parametro P_PERMITE_PAGAMENTO_SEM_DEDUCAO_IMPOSTO = new ParametroArrecadacao(
					"P_PERMITE_PAGAMENTO_SEM_DEDUCAO_IMPOSTO");

	// [UC0229] - Obter Representação Numérica do Código de Barras
	public static final Parametro P_INDICADOR_TRATA_LEGADO = new ParametroArrecadacao("P_INDICADOR_TRATA_LEGADO");

	// [UC0264] – Distribuir Dados do Código de Barras
	public static final Parametro P_INDICADOR_CADASTRO_PREVIO_NOTA_RECEBIMENTO = new ParametroArrecadacao(
					"P_INDICADOR_CADASTRO_PREVIO_NOTA_RECEBIMENTO");

	// [UC0319] – Geração débito Automático para Prefeitura
	public static final Parametro P_GERAR_DEBITO_AUTOMATICO_PREFEITURA = new ParametroArrecadacao("P_GERAR_DEBITO_AUTOMATICO_PREFEITURA");

	// [UC0319] – Geração débito Automático por Grupo Feturamento
	public static final Parametro P_GERAR_DEBITO_AUTOMATICO_POR_GRUPO = new ParametroArrecadacao("P_GERAR_DEBITO_AUTOMATICO_POR_GRUPO");

	// [UC0319] – Geração débito Automático por Referência
	public static final Parametro P_GERAR_DEBITO_AUTOMATICO_POR_REFERENCIA = new ParametroArrecadacao(
					"P_GERAR_DEBITO_AUTOMATICO_POR_REFERENCIA");

	/*
	 * Parâmetro que indica o código do tipo de debito (DEBITO_TIPO)
	 * de parcelametno de contas em cobrança administrativa.
	 */
	public static final Parametro P_SERVICO_PARCELAMENTO_COBRANCA_ADMINISTRATIVA = new ParametroArrecadacao(
					"P_SERVICO_PARCELAMENTO_COBRANCA_ADMINISTRATIVA");

	/*
	 * Parâmetro que indica o conjuto de códigos de tipos de debito
	 * (DEBITO_TIPO) que dão direito a remuneração quanto associadas a contas de clientes em
	 * cobrança administrativa.
	 */
	public static final Parametro P_SERVICOS_ESPECIAIS_COBRANCA_ADMINISTRATIVA = new ParametroArrecadacao(
					"P_SERVICOS_ESPECIAIS_COBRANCA_ADMINISTRATIVA");

	public static final Parametro P_TRATAR_ACRESCIMOS_EMISSAO_DOCUMENTO = new ParametroArrecadacao("P_TRATAR_ACRESCIMOS_EMISSAO_DOCUMENTO");

	public static final Parametro P_REFERENCIA_CONTA_PARA_CALCULO_REMUNERACAO_RELATORIO_PAGAMENTOS = new ParametroArrecadacao(
					"P_REFERENCIA_CONTA_PARA_CALCULO_REMUNERACAO_RELATORIO_PAGAMENTOS");

	/*
	 * Parametrizar Mensagem em Guia de Pagamento
	 */
	public static final Parametro P_MENSAGEM_GUIA_PAGAMENTO = new ParametroArrecadacao("P_MENSAGEM_GUIA_PAGAMENTO");

	public static final Parametro P_FINALIZAR_HEADER_TRAILLER_REGISTRO_TIPO_C_COM_ASTERISCO = new ParametroArrecadacao(
					"P_FINALIZAR_HEADER_TRAILLER_REGISTRO_TIPO_C_COM_ASTERISCO");
	public static final Parametro P_CONTA_MOTIVO_RETIFICACAO_ACRESCIMOS_PAGAMENTO = new ParametroArrecadacao(
					"P_CONTA_MOTIVO_RETIFICACAO_ACRESCIMOS_PAGAMENTO");

	public static final Parametro P_PERMITIR_SELECAO_ACRESCIMOS_EXTRATO = new ParametroArrecadacao("P_PERMITIR_SELECAO_ACRESCIMOS_EXTRATO");

	public static final Parametro P_PRAZO_GERACAO_SERVICO_ESPECIAL_REMUNERAVEL_APOS_RETIRADA_COBRANCA_ADMINISTRATIVA = new ParametroArrecadacao(
					"P_PRAZO_GERACAO_SERVICO_ESPECIAL_REMUNERAVEL_APOS_RETIRADA_COBRANCA_ADMINISTRATIVA");

	public static final Parametro P_ID_ARRECADADOR_PARCELAMENTO_RESPONSAVEL = new ParametroArrecadacao(
					"P_ID_ARRECADADOR_PARCELAMENTO_RESPONSAVEL");

	public static final Parametro P_INDICADOR_CONCESSIONARIA = new ParametroArrecadacao("P_INDICADOR_CONCESSIONARIA");

	// Indica se exibe mensagem de confirmação na inclusão de pagamento não classificado.
	public static final Parametro P_EXIBIR_CONFIRM_INCLUSAO_PAG_NAO_CLASSIFICADO = new ParametroArrecadacao(
					"P_EXIBIR_CONFIRM_INCLUSAO_PAG_NAO_CLASSIFICADO");

	public static final Parametro P_GERACAO_ITENS_REMUNERACAO_COBRANCA_ADMINISTRATIVA = new ParametroArrecadacao(
					"P_GERACAO_ITENS_REMUNERACAO_COBRANCA_ADMINISTRATIVA");

	public static final Parametro P_GERACAO_ACRESCIMOS_COBRANCA_ADMINISTRATIVA = new ParametroArrecadacao(
					"P_GERACAO_ACRESCIMOS_COBRANCA_ADMINISTRATIVA");

	public static final Parametro P_GERACAO_QUADRO_FATURAMENTO_ARRECADACAO = new ParametroArrecadacao(
					"P_GERACAO_QUADRO_FATURAMENTO_ARRECADACAO");

	public static final Parametro P_INCLUIR_AGENCIA_NAO_CADASTRADA_INCLUSAO_DEBITO_AUTOMATICO = new ParametroArrecadacao(
					"P_INCLUIR_AGENCIA_NAO_CADASTRADA_INCLUSAO_DEBITO_AUTOMATICO");

	public static final Parametro P_NUM_DIAS_ESTORNO_DIVIDA_ATIVA = new ParametroArrecadacao("P_NUM_DIAS_ESTORNO_DIVIDA_ATIVA");

	public static final Parametro P_VALOR_MAX_ESTORNO_DIVIDA_ATIVA = new ParametroArrecadacao("P_VALOR_MAX_ESTORNO_DIVIDA_ATIVA");

	public static final Parametro P_REGISTRAR_POR_DIRETORIO = new ParametroArrecadacao("P_REGISTRAR_POR_DIRETORIO");

	private ParametroArrecadacao(String parametro) {

		super(parametro);
	}
}
