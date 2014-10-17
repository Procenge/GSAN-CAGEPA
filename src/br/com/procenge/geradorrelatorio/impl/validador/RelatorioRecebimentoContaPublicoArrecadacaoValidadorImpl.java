
package br.com.procenge.geradorrelatorio.impl.validador;

import gcom.util.Util;

import java.util.HashMap;
import java.util.Map;

import br.com.procenge.geradorrelatorio.api.ValidadorParametros;

public class RelatorioRecebimentoContaPublicoArrecadacaoValidadorImpl
				implements ValidadorParametros {

	private static final String PARAMETRO_MESANORELATORIO = "mesAnoRelatorio";

	public Map<String, String> validar(Map<String, Object> parametros){

		Map<String, String> erros = new HashMap<String, String>();

		String data = (String) parametros.get(PARAMETRO_MESANORELATORIO);

		if(Util.isVazioOuBranco(data)){

			erros.put(PARAMETRO_MESANORELATORIO, "Mês/Ano");
		}

		return erros;
	}

	public Map<String, Object> converter(Map<String, Object> parametros){

		this.converterDadosAnoMes(parametros);

		return parametros;
	}

	private void converterDadosAnoMes(Map<String, Object> parametros){

		String pReferenciaAnoMes = String.valueOf(parametros.get(PARAMETRO_MESANORELATORIO));

		if(!Util.isVazioOuBranco(pReferenciaAnoMes)){

			pReferenciaAnoMes = Util.formatarMesAnoParaAnoMesSemBarra(pReferenciaAnoMes);

			parametros.put(PARAMETRO_MESANORELATORIO, String.valueOf(pReferenciaAnoMes));
		}
	}

}
