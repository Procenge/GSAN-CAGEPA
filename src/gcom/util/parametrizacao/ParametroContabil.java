
package gcom.util.parametrizacao;

public class ParametroContabil
				extends Parametro {

	public static final Parametro UNIDADE_CONTABIL_AGRUPAMENTO = new ParametroContabil("UNIDADE_CONTABIL_AGRUPAMENTO");

	public static final Parametro P_HABILITAR_CONTABILIZACAO = new ParametroContabil("P_HABILITAR_CONTABILIZACAO");

	public static final Parametro P_REFERENCIA_CORTE_PDD = new ParametroContabil("P_REFERENCIA_CORTE_PDD");

	public static final Parametro P_REFERENCIA_CONTABIL = new ParametroContabil("P_REFERENCIA_CONTABIL");

	public static final Parametro P_GERAR_INTEGRACAO_CONTABIL = new ParametroContabil("P_GERAR_INTEGRACAO_CONTABIL");

	public static final Parametro P_REFERENCIA_INTEGRACAO_SPED_PIS_COFINS = new ParametroContabil("P_REFERENCIA_INTEGRACAO_SPED_PIS_COFINS");

	public static final Parametro P_DATA_GERACAO_PARA_INTEGRACAO_CONTABIL_AJUSTE = new ParametroContabil(
					"P_DATA_GERACAO_PARA_INTEGRACAO_CONTABIL_AJUSTE");

	public static final Parametro P_CODIGO_ARRECADADOR_INT_CONTABIL = new ParametroContabil("P_CODIGO_ARRECADADOR_INT_CONTABIL");

	public static final Parametro P_CODIGO_LOCALIDADE_INT_CONTABIL = new ParametroContabil("P_CODIGO_LOCALIDADE_INT_CONTABIL");

	private ParametroContabil(String parametro) {

		super(parametro);
	}
}
