
package br.com.procenge.geradorrelatorio.impl.validador;

import gcom.util.Util;

import java.util.HashMap;
import java.util.Map;

import br.com.procenge.geradorrelatorio.api.ValidadorParametros;

public class RelatorioPddValidadorImpl
				implements ValidadorParametros {

	private static final String PARAMETRO_MESANORELATORIOPDD = "mesAnoRelatorioPdd";

	private static final String PARAMETRO_MESANORELATORIOPDD2 = "mesAnoRelatorioPdd2";

	public Map<String, String> validar(Map<String, Object> parametros){

		Map<String, String> erros = new HashMap<String, String>();

		String data = (String) parametros.get(PARAMETRO_MESANORELATORIOPDD);

		if(Util.isVazioOuBranco(data)){

			erros.put(PARAMETRO_MESANORELATORIOPDD, "Mês/Ano");
		}

		return erros;
	}

	public Map<String, Object> converter(Map<String, Object> parametros){

		this.converterDadosAnoMes(parametros);

		return parametros;
	}

	private void converterDadosAnoMes(Map<String, Object> parametros){

		String pReferenciaAnoMes = String.valueOf(parametros.get(PARAMETRO_MESANORELATORIOPDD));
		String pReferenciaAnoMes2 = pReferenciaAnoMes;

		if(!Util.isVazioOuBranco(pReferenciaAnoMes)){

			pReferenciaAnoMes = Util.formatarMesAnoParaAnoMesSemBarra(pReferenciaAnoMes);
			pReferenciaAnoMes2 = Util.formatarMesAnoParaAnoMesSemBarra(pReferenciaAnoMes2);

			parametros.put(PARAMETRO_MESANORELATORIOPDD, String.valueOf(pReferenciaAnoMes));
			parametros.put(PARAMETRO_MESANORELATORIOPDD2, String.valueOf(pReferenciaAnoMes2));
		}
	}

}
