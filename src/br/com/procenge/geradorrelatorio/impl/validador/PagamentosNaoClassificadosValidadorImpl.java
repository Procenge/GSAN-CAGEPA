
package br.com.procenge.geradorrelatorio.impl.validador;

import gcom.util.Util;

import java.util.HashMap;
import java.util.Map;

import br.com.procenge.geradorrelatorio.api.ValidadorParametros;

public class PagamentosNaoClassificadosValidadorImpl
				implements ValidadorParametros {

	private static final String PARAMETRO_AGENCIA_DEP_ARRECADACAO_FIN = "P_AG_DP_ARRECADACAO_FIN";

	private static final String PARAMETRO_AGENCIA_DEP_ARRECADACAO_INI = "P_AG_DP_ARRECADACAO_INI";

	private static final String PARAMETRO_BANCO_DEP_ARRECADACAO_FIN = "P_BC_DP_ARRECADACAO_FIN";

	private static final String PARAMETRO_BANCO_DEP_ARRECADACAO_INI = "P_BC_DP_ARRECADACAO_INI";

	private static final String PARAMETRO_SIT_PAGAMENTO_FIN = "P_ST_PG_FIN";

	private static final String PARAMETRO_SIT_PAGAMENTO_INI = "P_ST_PG_INI";

	private static final String PARAMETRO_REF_ARRECADACAO = "P_AM_ARRECADACAO";

	public Map<String, String> validar(Map<String, Object> parametros){

		Map<String, String> erros = new HashMap<String, String>();

		String anoMesArrecadacao = String.valueOf(parametros.get(PARAMETRO_REF_ARRECADACAO));
		if(Util.isVazioOuBranco(anoMesArrecadacao)){
			erros.put(PARAMETRO_REF_ARRECADACAO, "Par�metro Ano M�s Arrecada��o");
		}

		String agenciaDepositoArrecadacaoFinal = String.valueOf(parametros.get(PARAMETRO_AGENCIA_DEP_ARRECADACAO_FIN));
		if(Util.isVazioOuBranco(agenciaDepositoArrecadacaoFinal)){
			erros.put(PARAMETRO_AGENCIA_DEP_ARRECADACAO_FIN, "Par�metro Agencia para Dep�sito da Arrecada��o Final");
		}

		String agenciaDepositoArrecadacaoInicial = String.valueOf(parametros.get(PARAMETRO_AGENCIA_DEP_ARRECADACAO_INI));
		if(Util.isVazioOuBranco(agenciaDepositoArrecadacaoInicial)){
			erros.put(PARAMETRO_AGENCIA_DEP_ARRECADACAO_INI, "Par�metro Agencia para Dep�sito da Arrecada��o Inicial");
		}

		String bancoDepositoArrecadacaoFinal = String.valueOf(parametros.get(PARAMETRO_BANCO_DEP_ARRECADACAO_FIN));
		if(Util.isVazioOuBranco(bancoDepositoArrecadacaoFinal)){
			erros.put(PARAMETRO_BANCO_DEP_ARRECADACAO_FIN, "Par�metro Banco para Dep�sito da Arrecada��o Final");
		}

		String bancoDepositoArrecadacaoInicial = String.valueOf(parametros.get(PARAMETRO_BANCO_DEP_ARRECADACAO_INI));
		if(Util.isVazioOuBranco(bancoDepositoArrecadacaoInicial)){
			erros.put(PARAMETRO_BANCO_DEP_ARRECADACAO_INI, "Par�metro Banco para Dep�sito da Arrecada��o Inicial");
		}

		String situacaoPagamentoFinal = String.valueOf(parametros.get(PARAMETRO_SIT_PAGAMENTO_FIN));
		if(Util.isVazioOuBranco(situacaoPagamentoFinal)){
			erros.put(PARAMETRO_SIT_PAGAMENTO_FIN, "Situa��o do Pagamento Final");
		}

		String situacaoPagamentoInicial = String.valueOf(parametros.get(PARAMETRO_SIT_PAGAMENTO_INI));
		if(Util.isVazioOuBranco(situacaoPagamentoInicial)){
			erros.put(PARAMETRO_SIT_PAGAMENTO_INI, "Situa��o do Pagamento Inicial");
		}

		return erros;
	}

	public Map<String, Object> converter(Map<String, Object> parametros){

		Map<String, Object> parametrosConvertidos = new HashMap<String, Object>();
		parametrosConvertidos.putAll(parametros);

		// Formatar o parametro para ficar de acordo com o relatorio
		if(parametrosConvertidos.containsKey(PARAMETRO_REF_ARRECADACAO)){
			String pReferencia = String.valueOf(parametrosConvertidos.get(PARAMETRO_REF_ARRECADACAO));
			if(pReferencia != null && !pReferencia.equals("")){
				pReferencia = Util.formatarMesAnoParaAnoMesSemBarra(pReferencia);

				parametrosConvertidos.put(PARAMETRO_REF_ARRECADACAO, pReferencia);
			}else{
				pReferencia = "";

				parametrosConvertidos.put(PARAMETRO_REF_ARRECADACAO, pReferencia);
			}
		}

		return parametrosConvertidos;
	}

}
