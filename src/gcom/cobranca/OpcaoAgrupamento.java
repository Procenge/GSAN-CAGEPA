
package gcom.cobranca;

public enum OpcaoAgrupamento {
	GERENCIA_REGIONAL_LOCALIDADE, PERIODO_MENSAL, PERIODO_ANUAL, CATEGORIA, GRUPO_FATURAMENTO;

	public static OpcaoAgrupamento valuesOf(String idAgrupamento){

		if(idAgrupamento == null){
			return null;
		}
		for(OpcaoAgrupamento agrupamento : values()){
			if(agrupamento.toString().equals(idAgrupamento)){
				return agrupamento;
			}

		}
		return null;

	}

}
