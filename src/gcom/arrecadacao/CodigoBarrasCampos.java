
package gcom.arrecadacao;

import gcom.util.tabelaauxiliar.TabelaAuxiliar;

/**
 * Codigo Barras Campos
 * 
 * @author Hebert Falcão
 * @date 18/05/2012
 */
public class CodigoBarrasCampos
				extends TabelaAuxiliar {

	public static Integer TIPO_DE_PAGAMENTO = 0;

	public static Integer CODIGO_DA_LOCALIDADE = 1;

	public static Integer MATRICULA_DO_IMOVEL = 2;

	public static Integer MES_ANO_REFERENCIA_CONTA = 3;

	public static Integer SEQUENCIAL_DOCUMENTO_COBRANCA = 4;

	public static Integer NUMERO_PRESTACAO_GUIA_PAGAMENTO = 5;

	public static Integer ANO_EMISSAO_GUIA_PAGAMENTO = 6;

	public static Integer CODIGO_TIPO_DOCUMENTO = 7;

	public static Integer CODIGO_CLIENTE_RESPONSAVEL = 8;

	public static Integer CODIGO_TIPO_DEBITO = 9;

	public static Integer DIGITO_VERIFICADOR_DA_CONTA = 10;

	public static Integer SEQUENCIAL_FATURA_CLIENTE_RESPONSAVEL = 11;

	public static Integer IDENTIFICACAO_GUIA_PAGAMENTO = 12;

	public static Integer IDENTIFICACAO_OPCAO_PRE_PARCELAMENTO = 13;

	public static Integer CODIGO_CLIENTE = 14;

	public static Integer INDICADOR_DOCUMENTO_CLIENTE = 15;

	// public static Integer SEQUENCIAL = 16;
	//
	// public static Integer ANO_LANCAMENTO = 17;
	//
	// public static Integer NUMERO_AVISO = 18;
	//
	// public static Integer NUMERO_EMISSAO = 19;
	//
	// public static Integer CODIGO_TRIBUTO = 20;
	//
	// public static Integer VALOR_FIXO = 21;

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

}
