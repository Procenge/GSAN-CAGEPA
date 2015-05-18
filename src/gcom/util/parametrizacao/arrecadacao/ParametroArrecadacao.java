
package gcom.util.parametrizacao.arrecadacao;

import gcom.util.parametrizacao.Parametro;

public class ParametroArrecadacao
				extends Parametro {

	/*
	 * M�todo para validar campos do registro c�digo F � retorno do d�bito autom�tico.
	 */
	public static final Parametro P_VALIDAR_CAMPOS_RETORNO_DEB_AUTOMATICO_CODIGO_F = new ParametroArrecadacao(
					"P_VALIDAR_CAMPOS_RETORNO_DEB_AUTOMATICO_CODIGO_F");

	/*
	 * M�todo para atualizar dados do d�bito autom�tico a depender do tipo de documento � retorno do
	 * d�bito autom�tico.
	 */
	public static final Parametro P_ATUALIZAR_DEB_AUTOMATICO_REGISTRO_CODIGO_F = new ParametroArrecadacao(
					"P_ATUALIZAR_DEB_AUTOMATICO_REGISTRO_CODIGO_F");

	public static final Parametro P_FORMATAR_CAMPO_ENVIO_DEB_AUTOMATICO_CODIGO_E = new ParametroArrecadacao(
					"P_FORMATAR_CAMPO_ENVIO_DEB_AUTOMATICO_CODIGO_E");

	/*
	 * M�todo para distribuir os pagamentos em c�digo de barras legados dos clientes.
	 */
	public static final Parametro P_DISTRIBUIR_PAGAMENTO_CODIGO_BARRAS_LEGADO = new ParametroArrecadacao(
					"P_DISTRIBUIR_PAGAMENTO_CODIGO_BARRAS_LEGADO");

	/*
	 * Par�metro para indicar o tamanho do idIm�vel do movimento de d�bito autom�tico, quando conta
	 * for diferente de nulo.
	 */
	public static final Parametro P_NUMERO_DIGITOS_MATRICULA_IMOVEL = new ParametroArrecadacao("P_NUMERO_DIGITOS_MATRICULA_IMOVEL");

	/*
	 * M�todo para formatar o header do movimento d�bito autom�tico de acordo com a empresa.
	 */
	public static final Parametro P_FORMATAR_HEADER_DEB_AUTOMATICO = new ParametroArrecadacao("P_FORMATAR_HEADER_DEB_AUTOMATICO");

	/*
	 * M�todo para formatar o trailler do movimento d�bito autom�tico de acordo com a empresa.
	 */
	public static final Parametro P_FORMATAR_TRAILLER_DEB_AUTOMATICO = new ParametroArrecadacao("P_FORMATAR_TRAILLER_DEB_AUTOMATICO");

	/*
	 * Par�metro para identificar o tipo de d�bito da guia para inclus�o do im�vel no programa �gua
	 * para todos
	 */
	public static final Parametro P_IDENTIFICACAO_DEBITO_TIPO_PROGRAMA_AGUA_PARA_TODOS = new ParametroArrecadacao(
					"P_IDENTIFICACAO_DEBITO_TIPO_PROGRAMA_AGUA_PARA_TODOS");

	/*
	 * Par�metro para indicar o percentual do desconto concedido ao d�bito das contas para a
	 * inclus�o do im�vel no programa �gua para todos
	 */
	public static final Parametro P_PERCENTUAL_DESCONTO_PROGRAMA_AGUA_PARA_TODOS = new ParametroArrecadacao(
					"P_PERCENTUAL_DESCONTO_PROGRAMA_AGUA_PARA_TODOS");

	/*
	 * Par�metro para indicar o valor m�ximo da guia de pagamento para inclus�o do im�vel no
	 * programa �gua para todos
	 */
	public static final Parametro P_VALOR_MAXIMO_GUIA_PROGRAMA_AGUA_PARA_TODOS = new ParametroArrecadacao(
					"P_VALOR_MAXIMO_GUIA_PROGRAMA_AGUA_PARA_TODOS");

	/*
	 * Par�metro para indicar o n�mero de dias para c�lculo da data de vencimento da guia para
	 * inclus�o do im�vel no programa �gua para todos
	 */
	public static final Parametro P_NUMERO_DIAS_VENCIMENTO_GUIA_PROGRAMA_AGUA_PARA_TODOS = new ParametroArrecadacao(
					"P_NUMERO_DIAS_VENCIMENTO_GUIA_PROGRAMA_AGUA_PARA_TODOS");

	/*
	 * Par�metro para indicar a identifica��o do motivo de revis�o das contas para inclus�o do
	 * im�vel no programa �gua para todos
	 */
	public static final Parametro P_MOTIVO_REVISAO_PROGRAMA_AGUA_PARA_TODOS = new ParametroArrecadacao(
					"P_MOTIVO_REVISAO_PROGRAMA_AGUA_PARA_TODOS");

	/*
	 * Par�metro para indicar a identifica��o do motivo de cancelamento das contas do programa �gua
	 * para todos em raz�o do pagamento da NR/Guia
	 */
	public static final Parametro P_MOTIVO_CANCELAMENTO_PROGRAMA_AGUA_PARA_TODOS = new ParametroArrecadacao(
					"P_MOTIVO_CANCELAMENTO_PROGRAMA_AGUA_PARA_TODOS");

	/*
	 * Par�metro para indicar o n�mero de caracteres para o ID da Guia de Pagamento no C�digo de
	 * Barras. Ser� usado para Cria��o e Leitura (distribui��o) do c�digo de barras referente a Guia
	 * (Im�vel ou Cliente).
	 */
	public static final Parametro P_TAMANHO_CAMPO_GUIA_PAGAMENTO_CODIGO_BARRAS = new ParametroArrecadacao(
					"P_TAMANHO_CAMPO_GUIA_PAGAMENTO_CODIGO_BARRAS");

	/*
	 * Par�metro de permissao de pagamento com o valor original sem considerar imposto deduzidos
	 */
	public static final Parametro P_PERMITE_PAGAMENTO_SEM_DEDUCAO_IMPOSTO = new ParametroArrecadacao(
					"P_PERMITE_PAGAMENTO_SEM_DEDUCAO_IMPOSTO");

	// [UC0229] - Obter Representa��o Num�rica do C�digo de Barras
	public static final Parametro P_INDICADOR_TRATA_LEGADO = new ParametroArrecadacao("P_INDICADOR_TRATA_LEGADO");

	// [UC0264] � Distribuir Dados do C�digo de Barras
	public static final Parametro P_INDICADOR_CADASTRO_PREVIO_NOTA_RECEBIMENTO = new ParametroArrecadacao(
					"P_INDICADOR_CADASTRO_PREVIO_NOTA_RECEBIMENTO");

	// [UC0319] � Gera��o d�bito Autom�tico para Prefeitura
	public static final Parametro P_GERAR_DEBITO_AUTOMATICO_PREFEITURA = new ParametroArrecadacao("P_GERAR_DEBITO_AUTOMATICO_PREFEITURA");

	// [UC0319] � Gera��o d�bito Autom�tico por Grupo Feturamento
	public static final Parametro P_GERAR_DEBITO_AUTOMATICO_POR_GRUPO = new ParametroArrecadacao("P_GERAR_DEBITO_AUTOMATICO_POR_GRUPO");

	// [UC0319] � Gera��o d�bito Autom�tico por Refer�ncia
	public static final Parametro P_GERAR_DEBITO_AUTOMATICO_POR_REFERENCIA = new ParametroArrecadacao(
					"P_GERAR_DEBITO_AUTOMATICO_POR_REFERENCIA");

	/*
	 * Par�metro que indica o c�digo do tipo de debito (DEBITO_TIPO)
	 * de parcelametno de contas em cobran�a administrativa.
	 */
	public static final Parametro P_SERVICO_PARCELAMENTO_COBRANCA_ADMINISTRATIVA = new ParametroArrecadacao(
					"P_SERVICO_PARCELAMENTO_COBRANCA_ADMINISTRATIVA");

	/*
	 * Par�metro que indica o conjuto de c�digos de tipos de debito
	 * (DEBITO_TIPO) que d�o direito a remunera��o quanto associadas a contas de clientes em
	 * cobran�a administrativa.
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

	// Indica se exibe mensagem de confirma��o na inclus�o de pagamento n�o classificado.
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
