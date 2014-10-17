/**
 * 
 */

package gcom.util.parametrizacao.cobranca.parcelamento;

import gcom.util.parametrizacao.Parametro;

/**
 * @author Luciano Galvão
 */
public class ParametroParcelamento
				extends Parametro {


	public static final Parametro P_TIPO_DEBITO_PARCELAMENTO_CONTA = new ParametroParcelamento("P_TIPO_DEBITO_PARCELAMENTO_CONTA");

	public static final Parametro P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO = new ParametroParcelamento(
					"P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO");

	public static final Parametro P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE = new ParametroParcelamento(
					"P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE");

	public static final Parametro P_TIPO_DEBITO_PARCELAMENTO_FINANCIAMENTO = new ParametroParcelamento(
					"P_TIPO_DEBITO_PARCELAMENTO_FINANCIAMENTO");

	public static final Parametro P_TIPO_DEBITO_PARCELAMENTO_PARCELAMENTO = new ParametroParcelamento("P_TIPO_DEBITO_PARCELAMENTO_PARCELAMENTO");

	public static final Parametro P_TIPO_DEBITO_PARCELAMENTO_JUROS_PARCELAMENTO = new ParametroParcelamento(
					"P_TIPO_DEBITO_PARCELAMENTO_JUROS_PARCELAMENTO");

	public static final Parametro P_TIPO_DEBITO_PARCELAMENTO_CONTA_COBRANCA_ADMINISTRATIVA = new ParametroParcelamento(
					"P_TIPO_DEBITO_PARCELAMENTO_CONTA_COBRANCA_ADMINISTRATIVA");

	public static final Parametro P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE_COBRANCA_ADMINISTRATIVA = new ParametroParcelamento(
					"P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE_COBRANCA_ADMINISTRATIVA");

	public static final Parametro P_TIPO_DEBITO_PARCELAMENTO_JUROS_PARCELAMENTO_COBRANCA_ADMINISTRATIVA = new ParametroParcelamento(
					"P_TIPO_DEBITO_PARCELAMENTO_JUROS_PARCELAMENTO_COBRANCA_ADMINISTRATIVA");

	public static final Parametro P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO_COBRANCA_ADMINISTRATIVA = new ParametroParcelamento(
					"P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO_COBRANCA_ADMINISTRATIVA");

	public static final Parametro P_FINANCIAMENTO_TIPO_PARCELAMENTO = new ParametroParcelamento("P_FINANCIAMENTO_TIPO_PARCELAMENTO");

	public static final Parametro P_SERVICOS_ESPECIAIS_COBRANCA_ADMINISTRATIVA = new ParametroParcelamento(
					"P_SERVICOS_ESPECIAIS_COBRANCA_ADMINISTRATIVA");

	public static final Parametro P_TIPO_DEBITO_PARCELAMENTO_PARCELAMENTO_COBRANCA_ADMINISTRATIVA = new ParametroParcelamento(
					"P_TIPO_DEBITO_PARCELAMENTO_PARCELAMENTO_COBRANCA_ADMINISTRATIVA");

	public static final Parametro P_DEFINICAO_VALORES_PARCELAMENTO_TIPO_DEBITO_ITEM_LANCAMENTO_CONTABIL = new ParametroParcelamento(
					"P_DEFINICAO_VALORES_PARCELAMENTO_TIPO_DEBITO_ITEM_LANCAMENTO_CONTABIL");

	/**
	 * @param codigo
	 */
	public ParametroParcelamento(String parametro) {

		super(parametro);
		// TODO Auto-generated constructor stub
	}

}
