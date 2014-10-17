
package br.com.procenge.geradorrelatorio.impl.validador;

import gcom.util.Util;

import java.util.HashMap;
import java.util.Map;

import br.com.procenge.geradorrelatorio.api.ValidadorParametros;

public class RelatorioPosicaoDiariaArrecadacaoValidadorImpl
				implements ValidadorParametros {

	private static final String P_AM_REFERENCIA = "P_AM_REFERENCIA";

	private static final String P_MES_ANO_REFERENCIA = "P_MES_ANO_REFERENCIA";

	private static final String P_TIMESTAMP_INICIAL = "P_TIMESTAMP_INICIAL";

	private static final String P_TIMESTAMP_FINAL = "P_TIMESTAMP_FINAL";

	public Map<String, String> validar(Map<String, Object> parametros){

		Map<String, String> erros = new HashMap<String, String>();

		String mesAnoFaturamento = (String) parametros.get(P_MES_ANO_REFERENCIA);

		if(mesAnoFaturamento == null || mesAnoFaturamento.length() <= 0){
			erros.put(P_MES_ANO_REFERENCIA, "Digite o Mês e o Ano.");
		}
		if(Util.validarAnoMesSemBarra(mesAnoFaturamento)){
			erros.put(P_MES_ANO_REFERENCIA, "Mês e Ano está no formato incorreto.");
		}

		return erros;
	}

	public Map<String, Object> converter(Map<String, Object> parametros){

		Map<String, Object> parametrosConvertidos = new HashMap<String, Object>();
		String tsInicial = "";
		String tsFinal = "";
		String maReferencia = "";

		maReferencia = String.valueOf(parametros.get(P_MES_ANO_REFERENCIA));
		tsInicial = "'01/" + maReferencia + " 00:00:00'";
		tsFinal = "'"
						+ Util.obterUltimoDiaMes(Integer.parseInt(maReferencia.substring(0, 2)), Integer.parseInt(maReferencia.substring(3,
										7))) + "/" + maReferencia + " 23:59:59'";

		parametrosConvertidos.put(P_AM_REFERENCIA, Util.formatarMesAnoParaAnoMesSemBarra(maReferencia));
		// parametrosConvertidos.put(P_MES_ANO_REFERENCIA, maReferencia);
		parametrosConvertidos.put(P_TIMESTAMP_INICIAL, tsInicial);
		parametrosConvertidos.put(P_TIMESTAMP_FINAL, tsFinal);

		return parametrosConvertidos;
	}

}
