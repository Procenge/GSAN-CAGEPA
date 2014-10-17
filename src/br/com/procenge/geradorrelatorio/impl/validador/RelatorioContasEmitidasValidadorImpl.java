
package br.com.procenge.geradorrelatorio.impl.validador;

import gcom.util.Util;

import java.util.HashMap;
import java.util.Map;

import br.com.procenge.geradorrelatorio.api.ValidadorParametros;

public class RelatorioContasEmitidasValidadorImpl
				implements ValidadorParametros {

	// Parâmetros do Relatório de Contas Emitidas por Responsável
	private static final String PARAMETRO_GP_FATURAMENTO = "P_GP_FATURAMENTO";

	private static final String PARAMETRO_AM_REF = "P_AM_REF";

	private static final String PARAMETRO_AM_REFCONSUMO1 = "P_AM_REFCONSUMO1";

	private static final String PARAMETRO_AM_REFCONSUMO2 = "P_AM_REFCONSUMO2";

	private static final String PARAMETRO_AM_REFCONSUMO3 = "P_AM_REFCONSUMO3";

	private static final String PARAMETRO_AM_REFCONSUMO4 = "P_AM_REFCONSUMO4";

	private static final String PARAMETRO_AM_REFCONSUMO5 = "P_AM_REFCONSUMO5";

	private static final String PARAMETRO_AM_REFCONSUMO6 = "P_AM_REFCONSUMO6";

	// Parâmetros do Relatório de Resumo de Contas Emitidas
	private static final String PARAMETRO_RESUMO_GP_FATURAMENTO = "P_GP_FATURAMENTO";

	private static final String PARAMETRO_RESUMO_AM_REF = "P_AM_REF";

	private static final String PARAMETRO_TIPO_RELATORIO = "tipoRelatorio";

	private static final String TIPO_RELATORIO_RESUMO_CONTAS_EMITIDAS = "2";

	public Map<String, String> validar(Map<String, Object> parametros){

		Map<String, String> erros = new HashMap<String, String>();

		String anoMesRef = String.valueOf(parametros.get(PARAMETRO_AM_REF));
		if(Util.isVazioOuBranco(anoMesRef)){
			erros.put(PARAMETRO_AM_REF, "Parâmetro Ano Mês referência");
		}

		String grupoFaturamento = String.valueOf(parametros.get(PARAMETRO_GP_FATURAMENTO));
		if(Util.isVazioOuBranco(grupoFaturamento)){
			erros.put(PARAMETRO_GP_FATURAMENTO, "Parâmetro Grupo Faturamento");
		}

		// Se foi selecionado o tipo de Relatório Resumo de Contas Emitidas, alterar o nome dos
		// parâmetros no Map
		String tipoRelatorio = String.valueOf(parametros.get(PARAMETRO_TIPO_RELATORIO));

		if(!Util.isVazioOuBranco(tipoRelatorio) && (tipoRelatorio.equals(TIPO_RELATORIO_RESUMO_CONTAS_EMITIDAS))){
			alterarParametrosResumoContasEmitidas(parametros);
		}

		return erros;
	}

	public Map<String, Object> converter(Map<String, Object> parametros){

		Map<String, Object> parametrosConvertidos = new HashMap<String, Object>();
		parametrosConvertidos.putAll(parametros);

		// Formatar o parametro para ficar de acordo com o relatorio
		if(parametrosConvertidos.containsKey(PARAMETRO_AM_REF)){
			String pReferencia = String.valueOf(parametrosConvertidos.get(PARAMETRO_AM_REF));
			if(pReferencia != null && !pReferencia.equals("")){
				pReferencia = Util.formatarMesAnoParaAnoMesSemBarra(pReferencia);

				parametrosConvertidos.put(PARAMETRO_AM_REF, pReferencia);
				parametrosConvertidos.put(PARAMETRO_AM_REFCONSUMO6, Util.subtrairMesDoAnoMes(Util.obterInteger(pReferencia), 6));
				parametrosConvertidos.put(PARAMETRO_AM_REFCONSUMO5, Util.subtrairMesDoAnoMes(Util.obterInteger(pReferencia), 5));
				parametrosConvertidos.put(PARAMETRO_AM_REFCONSUMO4, Util.subtrairMesDoAnoMes(Util.obterInteger(pReferencia), 4));
				parametrosConvertidos.put(PARAMETRO_AM_REFCONSUMO3, Util.subtrairMesDoAnoMes(Util.obterInteger(pReferencia), 3));
				parametrosConvertidos.put(PARAMETRO_AM_REFCONSUMO2, Util.subtrairMesDoAnoMes(Util.obterInteger(pReferencia), 2));
				parametrosConvertidos.put(PARAMETRO_AM_REFCONSUMO1, Util.subtrairMesDoAnoMes(Util.obterInteger(pReferencia), 1));
			}else{
				pReferencia = "";

				parametrosConvertidos.put(PARAMETRO_AM_REF, pReferencia);
			}
		}

		return parametrosConvertidos;
	}

	private void alterarParametrosResumoContasEmitidas(Map<String, Object> parametros){

		if(!PARAMETRO_RESUMO_AM_REF.equals(PARAMETRO_AM_REF)){
			parametros.put(PARAMETRO_RESUMO_AM_REF, (Integer.valueOf((String) parametros.get(PARAMETRO_AM_REF))));
			parametros.remove(PARAMETRO_AM_REF);
		}

		if(!PARAMETRO_RESUMO_GP_FATURAMENTO.equals(PARAMETRO_GP_FATURAMENTO)){
			parametros.put(PARAMETRO_RESUMO_GP_FATURAMENTO, (Integer.valueOf((String) parametros.get(PARAMETRO_GP_FATURAMENTO))));
			parametros.remove(PARAMETRO_GP_FATURAMENTO);
		}
	}
}
