
package br.com.procenge.geradorrelatorio.impl.validador;

import gcom.util.Util;

import java.util.HashMap;
import java.util.Map;

import br.com.procenge.geradorrelatorio.api.ValidadorParametros;

public class RelacaoUsuariosImpostoFederalValidadorImpl
				implements ValidadorParametros {

	private static final String PARAMETRO_P_REFERENCIA = "P_AM_REF";

	public Map<String, String> validar(Map<String, Object> parametros){

		Map<String, String> erros = new HashMap<String, String>();

		// Validar se o parametro é uma data anoMes
		String anoMes = (String) parametros.get(PARAMETRO_P_REFERENCIA);
		if(anoMes == null || Integer.parseInt(anoMes) <= 0){
			erros.put(PARAMETRO_P_REFERENCIA, "Digite algum valor");
		}
		if(Util.validarAnoMesSemBarra(anoMes)){
			erros.put(PARAMETRO_P_REFERENCIA, "Parâmetro Ano e Mês");
		}
		return erros;
	}

	public Map<String, Object> converter(Map<String, Object> parametros){

		Map<String, Object> parametrosConvertidos = new HashMap<String, Object>();
		parametrosConvertidos.putAll(parametros);

		// Formatar o parametro para ficar de acordo com o relatorio
		if(parametrosConvertidos.containsKey(PARAMETRO_P_REFERENCIA)){
			String pReferencia = String.valueOf(parametrosConvertidos.get(PARAMETRO_P_REFERENCIA));
			if(pReferencia != null){
				pReferencia = Util.formatarMesAnoParaAnoMesSemBarra(pReferencia);

				parametrosConvertidos.put(PARAMETRO_P_REFERENCIA, pReferencia);
			}
		}

		return parametrosConvertidos;
	}

}
