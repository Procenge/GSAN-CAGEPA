
package br.com.procenge.geradorrelatorio.impl.validador;

import gcom.util.Util;

import java.util.HashMap;
import java.util.Map;

import br.com.procenge.geradorrelatorio.api.ValidadorParametros;

public class FaturamentoTarifaSocialValidadorImpl
				implements ValidadorParametros {

	private static final String PARAMETRO_P_AM_FATURAMENTO = "P_AM_FATURAMENTO";

	private static final String PARAMETRO_P_LC_IM = "P_LC_IM";

	public Map<String, String> validar(Map<String, Object> parametros){

		Map<String, String> erros = new HashMap<String, String>();

		String anoMesFaturamento = (String) parametros.get(PARAMETRO_P_AM_FATURAMENTO);
		String idLocalidade = (String) parametros.get(PARAMETRO_P_LC_IM);
		if(anoMesFaturamento == null || anoMesFaturamento.length() <= 0){
			erros.put(PARAMETRO_P_AM_FATURAMENTO, "Digite o Ano e M�s.");
		}
		if(Util.validarAnoMesSemBarra(anoMesFaturamento)){
			erros.put(PARAMETRO_P_AM_FATURAMENTO, "Ano e M�s est� no formato incorreto.");
		}

		if(!Util.isVazioOuBranco(idLocalidade)){
			if(!Util.validarStringNumerica(idLocalidade)){
				erros.put(PARAMETRO_P_LC_IM, "Localidade deve ser um n�mero.");
			}
		}else{
			idLocalidade = "-1";
			parametros.put(PARAMETRO_P_LC_IM, idLocalidade);
		}
		return erros;
	}

	public Map<String, Object> converter(Map<String, Object> parametros){

		Map<String, Object> parametrosConvertidos = new HashMap<String, Object>();
		this.converterDadosAnoMes(parametros);

		parametrosConvertidos.put(PARAMETRO_P_AM_FATURAMENTO, parametros.get(PARAMETRO_P_AM_FATURAMENTO));
		parametrosConvertidos.put(PARAMETRO_P_LC_IM, parametros.get(PARAMETRO_P_LC_IM));

		return parametrosConvertidos;
	}

	/**
	 * M�todo para converter dados do par�metro de m�s e ano para ano e m�s.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 27/03/2012
	 * @param parametros
	 */
	private void converterDadosAnoMes(Map<String, Object> parametros){

		if(parametros.containsKey(PARAMETRO_P_AM_FATURAMENTO)){
			String pReferenciaAnoMesFaturamento = String.valueOf(parametros.get(PARAMETRO_P_AM_FATURAMENTO));
			if(pReferenciaAnoMesFaturamento != null){
				pReferenciaAnoMesFaturamento = Util.formatarMesAnoParaAnoMesSemBarra(pReferenciaAnoMesFaturamento);

				parametros.put(PARAMETRO_P_AM_FATURAMENTO, pReferenciaAnoMesFaturamento);
			}
		}
	}

}
