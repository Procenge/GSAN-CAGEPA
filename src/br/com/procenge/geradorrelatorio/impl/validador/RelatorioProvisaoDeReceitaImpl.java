
package br.com.procenge.geradorrelatorio.impl.validador;

import gcom.util.Util;

import java.util.HashMap;
import java.util.Map;

import br.com.procenge.geradorrelatorio.api.ValidadorParametros;

public class RelatorioProvisaoDeReceitaImpl
				implements ValidadorParametros {

	public static final String P_AM_REFERENCIA = "mesAno";

	public Map<String, Object> converter(Map<String, Object> parametros){

		Map<String, Object> parametrosConvertidos = new HashMap<String, Object>();

		this.converterAnoMesReferencia(parametros, parametrosConvertidos);

		return parametrosConvertidos;

	}

	public Map<String, String> validar(Map<String, Object> parametros){

		return new HashMap<String, String>();

	}

	private void converterAnoMesReferencia(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		// Pega os valores em String
		String anoMesReferenciaString = Util.converterObjetoParaString(parametros.get(P_AM_REFERENCIA));
		// Atribui o valor padr�o. Se o par�metro n�o for informado, ser� passado este valor
		Integer anoMesReferencia = new Integer("0");

		// Valida se n�o est� nulo
		if(!Util.isVazioOuBranco(anoMesReferenciaString)){
			anoMesReferencia = Util.converterStringParaInteger(Util.formatarMesAnoParaAnoMesSemBarra(anoMesReferenciaString));
		}

		parametrosConvertidos.put("P_AM_REF", anoMesReferencia);

	}

}
