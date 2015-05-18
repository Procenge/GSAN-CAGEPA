
package br.com.procenge.geradorrelatorio.impl.validador;

import gcom.util.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.procenge.geradorrelatorio.api.ValidadorParametros;

public class RelatorioDeferimentoPeriodoValidadorImpl
				implements ValidadorParametros {

	private static final String PARAMETRO_MES_ANO_REFERENCIA = "mesAnoReferencia";

	private static final String PARAMETRO_DATA_FIM_DEFERIMENTO = "dataFimDeferimento";

	public Map<String, String> validar(Map<String, Object> parametros){

		Map<String, String> erros = new HashMap<String, String>();

		String mesAnoReferencia = (String) parametros.get(PARAMETRO_MES_ANO_REFERENCIA);

		if(Util.isVazioOuBranco(mesAnoReferencia)){

			erros.put(PARAMETRO_MES_ANO_REFERENCIA, "Mês/Ano");
		}

		String dataFimDeferimento = (String) parametros.get(PARAMETRO_DATA_FIM_DEFERIMENTO);

		if(Util.isVazioOuBranco(dataFimDeferimento)){

			erros.put(PARAMETRO_DATA_FIM_DEFERIMENTO, "Data Fim Deferimento");
		}

		return erros;
	}

	public Map<String, Object> converter(Map<String, Object> parametros){

		this.converterDadosAnoMes(parametros);

		return parametros;
	}

	private void converterDadosAnoMes(Map<String, Object> parametros){

		String pReferenciaAnoMes = String.valueOf(parametros.get(PARAMETRO_MES_ANO_REFERENCIA));

		if(!Util.isVazioOuBranco(pReferenciaAnoMes)){

			pReferenciaAnoMes = Util.formatarMesAnoParaAnoMesSemBarra(pReferenciaAnoMes);

			parametros.put(PARAMETRO_MES_ANO_REFERENCIA, String.valueOf(pReferenciaAnoMes));

			// Obtém o mês
			int mesReferencia = Util.obterMes(Integer.parseInt(pReferenciaAnoMes));
			// Obtém o ano
			int anoReferencia = Util.obterAno(Integer.parseInt(pReferenciaAnoMes));
			// Obtém o ultimo dia do mês/ano
			int ultimoDiaMes = Integer.valueOf(Util.obterUltimoDiaMes(mesReferencia, anoReferencia));

			// Data Fim Deferimento
			Date pDataFimDeferimento = Util.criarData(ultimoDiaMes, mesReferencia, anoReferencia);

			parametros.put(PARAMETRO_DATA_FIM_DEFERIMENTO, pDataFimDeferimento);

		}
	}

}
