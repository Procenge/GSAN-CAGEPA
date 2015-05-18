package br.com.procenge.geradorrelatorio.impl.validador;

import gcom.util.Util;

import java.util.HashMap;
import java.util.Map;

import br.com.procenge.geradorrelatorio.api.ValidadorParametros;


public class RelatorioClassificacaoContabilExecucaoValidadorImpl
				implements ValidadorParametros {

	private static final String pnCDAgenteInicial = "pnCDAgenteInicial";

	private static final String pnCDAgenteFinal = "pnCDAgenteFinal";

	private static final String pdInicial = "pdInicial";

	private static final String pdFinal = "pdFinal";

	private static final String psTipoRelatorio = "psTipoRelatorio";

	private static final String psClasseContabil = "psClasseContabil";

	public Map<String, String> validar(Map<String, Object> parametros){

		Map<String, String> erros = new HashMap<String, String>();

		String psCDAgenteInicial = (String) parametros.get(pnCDAgenteInicial);
		if(Util.isVazioOuBranco(psCDAgenteInicial)){
			erros.put(psCDAgenteInicial, "Arrecador Inicial");
		}
		String psCDAgenteFinal = (String) parametros.get(pnCDAgenteFinal);
		if(Util.isVazioOuBranco(psCDAgenteFinal)){
			erros.put(psCDAgenteFinal, "Arrecadador Final");
		}

		String psInicial = (String) parametros.get(pdInicial);
		if(Util.isVazioOuBranco(psInicial)){
			erros.put(psInicial, "Data de Crédito de Inicio");
		}

		
		String psFinal = (String) parametros.get(pdFinal);
		if(Util.isVazioOuBranco(psFinal)){
			erros.put(psFinal, "Data de Crédito de Final");
		}

		return erros;
	}

	public Map<String, Object> converter(Map<String, Object> parametros){

		parametros.put(pnCDAgenteInicial, parametros.get(pnCDAgenteInicial));
		parametros.put(pnCDAgenteFinal, parametros.get(pnCDAgenteFinal));
		parametros.put(psTipoRelatorio, parametros.get(psTipoRelatorio));
		parametros.put(pdInicial, parametros.get(pdInicial));
		parametros.put(pdFinal, parametros.get(pdFinal));
		parametros.put(psClasseContabil, parametros.get(psClasseContabil));
		
		return parametros;
	}
}
