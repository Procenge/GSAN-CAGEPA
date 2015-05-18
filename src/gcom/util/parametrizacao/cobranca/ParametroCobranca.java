/**
 * 
 */

package gcom.util.parametrizacao.cobranca;

import gcom.util.ControladorException;
import gcom.util.parametrizacao.Parametro;

/**
 * @author bferreira
 */
public class ParametroCobranca
				extends Parametro {

	public static final Parametro P_MOTIVO_REVISAO_COBRANCA_BANCARIA = new ParametroCobranca("P_MOTIVO_REVISAO_COBRANCA_BANCARIA");

	public static final Parametro P_QUANTIDADE_DIAS_PARCELAR_RETROATIVO = new ParametroCobranca("P_QUANTIDADE_DIAS_PARCELAR_RETROATIVO");

	public static final Parametro P_GERAR_CARTAS_COBRANCA_BANCARIA = new ParametroCobranca("P_GERAR_CARTAS_COBRANCA_BANCARIA");

	public static final Parametro P_ENDERECO_SUPERINTENDENCIA_COMERCIAL = new ParametroCobranca("P_ENDERECO_SUPERINTENDENCIA_COMERCIAL");

	public static final Parametro P_LAYOUT_PARCELAMENTO_COBRANCA_BANCARIA = new ParametroCobranca("P_LAYOUT_PARCELAMENTO_COBRANCA_BANCARIA");

	public static final Parametro P_PERMITE_PARCEL_SEM_CONTA = new ParametroCobranca("P_PERMITE_PARCEL_SEM_CONTA");

	public static final Parametro P_DIAS_CARENCIA_PAGAMENTO_ENTRADA_NEGOCIACAO = new ParametroCobranca(
					"P_DIAS_CARENCIA_PAGAMENTO_ENTRADA_NEGOCIACAO");

	public static final Parametro P_PARCELAMENTO_COBRANCA_BANCARIA = new ParametroCobranca("P_PARCELAMENTO_COBRANCA_BANCARIA");

	public static final Parametro P_COBRANCA_PARCELAMENTO_GUIA = new ParametroCobranca("P_COBRANCA_PARCELAMENTO_GUIA");

	public static final Parametro P_NUMERO_DIAS_CALCULO_VENCIMENTO_PARCELA = new ParametroCobranca(
					"P_NUMERO_DIAS_CALCULO_VENCIMENTO_PARCELA");

	public static final Parametro P_COBRANCA_ACAO_COM_ENTREGA_DOCUMENTO = new ParametroCobranca("P_COBRANCA_ACAO_COM_ENTREGA_DOCUMENTO");

	public static final Parametro P_ESPECIFICACAO_NEGOCIACAO_DEBITOS = new ParametroCobranca("P_ESPECIFICACAO_NEGOCIACAO_DEBITOS");

	public static final Parametro P_GERAR_ARQUIVO_TXT_AVISO_CORTE = new ParametroCobranca("P_GERAR_ARQUIVO_TXT_AVISO_CORTE");

	public static final Parametro P_QUANTIDADE_AVISO_CORTE_PAGINA = new ParametroCobranca("P_QUANTIDADE_AVISO_CORTE_PAGINA");

	public static final Parametro P_GERAR_ARQUIVO_TXT_ORDEM_CORTE = new ParametroCobranca("P_GERAR_ARQUIVO_TXT_ORDEM_CORTE");

	public static final Parametro P_QUANTIDADE_ORDEM_CORTE_PAGINA = new ParametroCobranca("P_QUANTIDADE_ORDEM_CORTE_PAGINA");

	public static final Parametro P_CONSIDERAR_DEBITOS_A_COBRAR_PARCELAMENTO = new ParametroCobranca(
					"P_CONSIDERAR_DEBITOS_A_COBRAR_PARCELAMENTO");

	public static final Parametro P_DOCUMENTO_TIPO_CONTROLE_ENTREGA = new ParametroCobranca("P_DOCUMENTO_TIPO_CONTROLE_ENTREGA");

	public static final Parametro P_ARRASTO_PARCELAS_DESCUMPRIMENTO_PARCELAMENTO = new ParametroCobranca(
					"P_ARRASTO_PARCELAS_DESCUMPRIMENTO_PARCELAMENTO");

	public static final Parametro P_QTD_PARCELAS_VENCIDAS_ARRASTO_PARC = new ParametroCobranca("P_QTD_PARCELAS_VENCIDAS_ARRASTO_PARC");

	public static final Parametro P_CALCULA_MULTA_COM_DEBITO_ATUALIZADO = new ParametroCobranca("P_CALCULA_MULTA_COM_DEBITO_ATUALIZADO");

	public static final Parametro P_MAIOR_ANO_MES_CONTA_SEM_ACRESCIMOS = new ParametroCobranca("P_MAIOR_ANO_MES_CONTA_SEM_ACRESCIMOS");

	public static final Parametro P_MODELO_RELATORIO_CERTIDAO_NEGATIVA_DEBITOS = new ParametroCobranca(
					"P_MODELO_RELATORIO_CERTIDAO_NEGATIVA_DEBITOS");

	public static final Parametro P_INDICADOR_GERAR_AVISO_CORTE_FATURAMENTO = new ParametroCobranca(
					"P_INDICADOR_GERAR_AVISO_CORTE_FATURAMENTO");

	// -------------------------------------------------------------------------------------

	public static final Parametro P_QUANTIDADE_DIAS_VENCIMENTO_CONTA_AVISO_CORTE = new ParametroCobranca(
					"P_QUANTIDADE_DIAS_VENCIMENTO_CONTA_AVISO_CORTE");

	public static final Parametro P_VALOR_MINIMO_DEBITOS_ANTERIORES = new ParametroCobranca("P_VALOR_MINIMO_DEBITOS_ANTERIORES");

	public static final Parametro P_FATOR_MULTIPLICACAO_VALOR_DEBITO_AVISO_CORTE = new ParametroCobranca(
					"P_FATOR_MULTIPLICACAO_VALOR_DEBITO_AVISO_CORTE");

	public static final Parametro P_FATOR_MULTIPLICACAO_VALOR_DEBITO_NOTIFICACAO_AMIGAVEL = new ParametroCobranca(
					"P_FATOR_MULTIPLICACAO_VALOR_DEBITO_NOTIFICACAO_AMIGAVEL");

	public static final Parametro P_COBRAR_MULTA_POR_IMPONTUALIDADE = new ParametroCobranca("P_COBRAR_MULTA_POR_IMPONTUALIDADE");

	public static final Parametro P_COBRAR_JUROS_POR_IMPONTUALIDADE = new ParametroCobranca("P_COBRAR_JUROS_POR_IMPONTUALIDADE");

	public static final Parametro P_COBRAR_CORRECAO_POR_IMPONTUALIDADE = new ParametroCobranca("P_COBRAR_CORRECAO_POR_IMPONTUALIDADE");

	public static final Parametro P_PERCENTUAL_ENTRADA_MINIMA_PARCELAMENTO = new ParametroCobranca(
					"P_PERCENTUAL_ENTRADA_MINIMA_PARCELAMENTO");

	public static final Parametro P_ID_CRITERIO_COBRANCA_AVISO_CORTE_FATURAMENTO = new ParametroCobranca(
					"P_ID_CRITERIO_COBRANCA_AVISO_CORTE_FATURAMENTO");

	public static final Parametro P_ID_CRITERIO_COBRANCA_NOTIFICACAO_AMIGAVEL = new ParametroCobranca(
					"P_ID_CRITERIO_COBRANCA_NOTIFICACAO_AMIGAVEL");

	public static final Parametro P_SERVICO_PARCELAMENTO_COBRANCA_ADMINISTRATIVA = new ParametroCobranca(
					"P_SERVICO_PARCELAMENTO_COBRANCA_ADMINISTRATIVA");

	public static final Parametro P_NUMERO_DIAS_DEBITO_VENCIDO_PARA_ARRASTO = new ParametroCobranca(
					"P_NUMERO_DIAS_DEBITO_VENCIDO_PARA_ARRASTO");

	public static final Parametro P_COBRAR_DESCONTOS_MULTAS_ARRASTO = new ParametroCobranca("P_COBRAR_DESCONTOS_MULTAS_ARRASTO");

	public static final Parametro P_DEBITO_TIPO_PENALIZACAO_DESCONTO = new ParametroCobranca("P_DEBITO_TIPO_PENALIZACAO_DESCONTO");

	public static final Parametro P_VERIFICA_PARCELAMENTO_MES_FATURAMENTO_CORRENTE = new ParametroCobranca(
					"P_VERIFICA_PARCELAMENTO_MES_FATURAMENTO_CORRENTE");

	public static final Parametro P_CONSIDERAR_CREDITO_A_REALIZAR_PARCELAMENTO = new ParametroCobranca(
					"P_CONSIDERAR_CREDITO_A_REALIZAR_PARCELAMENTO");

	public static final Parametro P_LISTA_RD_COM_TERMO_PREFERENCIAL_AO_TERMO_COBRANCABANCARIA = new ParametroCobranca(
					"P_LISTA_RD_COM_TERMO_PREFERENCIAL_AO_TERMO_COBRANCABANCARIA");

	public static final Parametro P_CALCULAR_VALOR_PRESTACAO_PARCELAMENTO = new ParametroCobranca("P_CALCULAR_VALOR_PRESTACAO_PARCELAMENTO");

	public static final Parametro P_LISTA_ACOES_COBRANCA_SEM_PRAZO_VALIDADE = new ParametroCobranca(
					"P_LISTA_ACOES_COBRANCA_SEM_PRAZO_VALIDADE");

	public static final Parametro P_EXIBIR_CALCULAR_FATOR_CORRECAO = new ParametroCobranca("P_EXIBIR_CALCULAR_FATOR_CORRECAO");

	public static final Parametro P_ZERAR_VALOR_ACRESCIMO_ATUALIZACAO_TARIFARIA = new ParametroCobranca(
					"P_ZERAR_VALOR_ACRESCIMO_ATUALIZACAO_TARIFARIA");

	public static final Parametro P_PERMITIR_SELECAO_ACRESCIMOS_EXTRATO = new ParametroCobranca("P_PERMITIR_SELECAO_ACRESCIMOS_EXTRATO");

	public static final Parametro P_VALOR_PADRAO_INDICADOR_VALOR_CORRIGIDO_RELATORIO_DEBITO_RESPONSAVEL = new ParametroCobranca(
					"P_VALOR_PADRAO_INDICADOR_VALOR_CORRIGIDO_RELATORIO_DEBITO_RESPONSAVEL");

	public static final Parametro P_INDICADOR_EMISSAO_EXTRATO_DEBITO_SEM_ACRESCIMOS = new ParametroCobranca(
					"P_INDICADOR_EMISSAO_EXTRATO_DEBITO_SEM_ACRESCIMOS");

	public static final Parametro P_MOTIVO_REVISAO_CONSIDERADOS_COBRANCA_ADMINISTRATIVA = new ParametroCobranca(
					"P_MOTIVO_REVISAO_CONSIDERADOS_COBRANCA_ADMINISTRATIVA");

	public static final Parametro P_ANOS_VENCIMENTO_ANTIGO_COBRANCA_ADMINISTRATIVA = new ParametroCobranca(
					"P_ANOS_VENCIMENTO_ANTIGO_COBRANCA_ADMINISTRATIVA");

	public static final Parametro P_LAYOUT_PARCELAMENTO_COBRANCA_ADMINISTRATIVA = new ParametroCobranca(
					"P_LAYOUT_PARCELAMENTO_COBRANCA_ADMINISTRATIVA");

	public static final Parametro P_PERMITIR_CPFCNPJ_MANUAL_PARC_DEBITOS = new ParametroCobranca("P_PERMITIR_CPFCNPJ_MANUAL_PARC_DEBITOS");

	public static final Parametro P_EXIBIR_MSG_ADVERTENCIA_SITUACAO_COBRANCA_PARCELAMENTO = new ParametroCobranca(
					"P_EXIBIR_MSG_ADVERTENCIA_SITUACAO_COBRANCA_PARCELAMENTO");

	public static final Parametro P_PARCELAR_DEBITOS_NAO_VENCIDOS = new ParametroCobranca("P_PARCELAR_DEBITOS_NAO_VENCIDOS");

	public static final Parametro P_CLIENTE_RELACAO_TIPO_NEGATIVACAO = new ParametroCobranca("P_CLIENTE_RELACAO_TIPO_NEGATIVACAO");

	public static final Parametro P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO = new ParametroCobranca(
					"P_EXIBIR_MATRICULA_USUARIO_EFETUOU_PARCELAMENTO");

	public static final Parametro P_SIT_LIGACAO_AGUA_RESTABELECIMENTO = new ParametroCobranca("P_SIT_LIGACAO_AGUA_RESTABELECIMENTO");

	public static final Parametro P_MENSAGEM_EXTRATO_PAGAMENTO_A_VISTA_RD = new ParametroCobranca("P_MENSAGEM_EXTRATO_PAGAMENTO_A_VISTA_RD");

	public static final Parametro P_MENSAGEM_EXTRATO_PAGAMENTO_A_VISTA_TEXTO = new ParametroCobranca("P_MENSAGEM_EXTRATO_PAGAMENTO_A_VISTA_TEXTO");

	public static final Parametro P_GERAR_ARQUIVO_AVISO_DEBITO = new ParametroCobranca("P_GERAR_ARQUIVO_AVISO_DEBITO");

	public static final Parametro P_PERMITE_MANIPULAR_CONTA_RETIDA_COM_RESTRICAO_USUARIO = new ParametroCobranca(
					"P_PERMITE_MANIPULAR_CONTA_RETIDA_COM_RESTRICAO_USUARIO");

	public static final Parametro P_IND_REMUNERACAO_COBRANCA_ADMINISTRATIVA_POR_COMANDO = new ParametroCobranca(
					"P_IND_REMUNERACAO_COBRANCA_ADMINISTRATIVA_POR_COMANDO");
	public static final Parametro P_GERACAO_DADOS_REMUNERACAO_COBRANCA_ADMINISTRATIVA = new ParametroCobranca(
					"P_GERACAO_DADOS_REMUNERACAO_COBRANCA_ADMINISTRATIVA");

	public static final Parametro P_RELATORIO_REMUNERACAO_COBRANCA_ADMINISTRATIVA = new ParametroCobranca(
					"P_RELATORIO_REMUNERACAO_COBRANCA_ADMINISTRATIVA");

	public static final Parametro P_EMITE_EXTRATO_CONTA_PAGA = new ParametroCobranca("P_EMITE_EXTRATO_CONTA_PAGA");

	public static final Parametro P_NUMERO_ANOS_VENCIMENTO_DEBITO_ORGAO_PUBLICO_PARA_PRESCRICAO = new ParametroCobranca(
					"P_NUMERO_ANOS_VENCIMENTO_DEBITO_ORGAO_PUBLICO_PARA_PRESCRICAO");

	public static final Parametro P_NUMERO_ANOS_VENCIMENTO_DEBITO_PARTICULAR_PARA_PRESCRICAO = new ParametroCobranca(
					"P_NUMERO_ANOS_VENCIMENTO_DEBITO_PARTICULAR_PARA_PRESCRICAO");

	public static final Parametro P_LISTA_SITUACOES_COBRANCA_IMPEDEM_PRESCRICAO = new ParametroCobranca(
					"P_LISTA_SITUACOES_COBRANCA_IMPEDEM_PRESCRICAO");

	public static final Parametro P_INDICADOR_CONSIDERAR_DEBITO_PRESCRITO_NO_PARCELAMENTO = new ParametroCobranca(
					"P_INDICADOR_CONSIDERAR_DEBITO_PRESCRITO_NO_PARCELAMENTO");

	public static final Parametro P_INDICADOR_CONSIDERAR_DEBITO_PRESCRITO_NAS_ACOES_DE_COBRANCA = new ParametroCobranca(
					"P_INDICADOR_CONSIDERAR_DEBITO_PRESCRITO_NAS_ACOES_DE_COBRANCA");

	public static final Parametro P_INDICADOR_EXIBIR_DEBITO_PRESCRITO_CONSULTA_DEBITO = new ParametroCobranca(
					"P_INDICADOR_EXIBIR_DEBITO_PRESCRITO_CONSULTA_DEBITO");

	public static final Parametro P_MODELO_CERTIDAO_POSITIVA_DEBITOS = new ParametroCobranca("P_MODELO_CERTIDAO_POSITIVA_DEBITOS");

	public static final Parametro P_EMITE_CERTIDAO_NEGATIVA_EFEITO_POSITIVA = new ParametroCobranca(
					"P_EMITE_CERTIDAO_NEGATIVA_EFEITO_POSITIVA");

	public static Parametro P_MODULO_DIVIDA_ATIVA_ENDERECO = new ParametroCobranca("P_MODULO_DIVIDA_ATIVA_ENDERECO");

	public static final Parametro P_INDICADOR_RETIRADA_COBRANCA_ADMNISTRATIVA_PARCELAMENTO_COM_QUITACAO = new ParametroCobranca(
					"P_INDICADOR_RETIRADA_COBRANCA_ADMNISTRATIVA_PARCELAMENTO_COM_QUITACAO");


	public static final Parametro P_GERAR_CARTA_COBRANCA = new ParametroCobranca("P_GERAR_CARTA_COBRANCA");

	public static final Parametro P_INDICADOR_POSSUI_DIVIDA_ATIVA = new ParametroCobranca("P_INDICADOR_POSSUI_DIVIDA_ATIVA");

	/**
	 * @param codigo
	 */
	public ParametroCobranca(String parametro) {

		super(parametro);
		// TODO Auto-generated constructor stub
	}

	public static boolean isCobrancaBancaria(String idMotivoRevisao){

		String[] idsMotivoRevisao = null;
		try{
			idsMotivoRevisao = ((String) P_MOTIVO_REVISAO_COBRANCA_BANCARIA.executar(ExecutorParametrosCobranca.getInstancia())).split(",");
		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(idsMotivoRevisao != null){

			if(idMotivoRevisao != null){
				for(int i = 0; i < idsMotivoRevisao.length; i++){
					if(idsMotivoRevisao[i].equals(idMotivoRevisao)){
						return true;
					}
				}
			}

		}
		return false;
	}

}
