package br.com.procenge.geradorrelatorio.impl.validador;

import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.HashMap;
import java.util.Map;

import br.com.procenge.geradorrelatorio.api.ValidadorParametros;

public class RelatorioFaturamentoCobradoEmContaValidadorImpl
				implements ValidadorParametros {

	private static final String PARAMETRO_REF = "P_MES_ANO_REFERENCIA";

	private static final String PARAMETRO_LOCA_I = "LOCA_I";

	private static final String PARAMETRO_LOCA_F = "LOCA_F";

	private static final String PARAMETRO_GRUPO_I = "GRUPO_I";

	private static final String PARAMETRO_GRUPO_F = "GRUPO_F";

	public Map<String, String> validar(Map<String, Object> parametros){

		Map<String, String> erros = new HashMap<String, String>();

		Integer referenciaDebitoInicial = (Integer) parametros.get(PARAMETRO_REF);
		if(referenciaDebitoInicial == null || referenciaDebitoInicial <= 0){
			erros.put(PARAMETRO_REF, "Referência do Débito Inicial");
		}

		return erros;
	}

	public Map<String, Object> converter(Map<String, Object> parametros){

		this.converterDadosAnoMes(parametros);
		this.converterGrupoFaturamento(parametros);
		this.converterLocalidade(parametros);

		return parametros;
	}

	/**
	 * Converte caso não seja informado ele seleciona o menor e o maior
	 * 
	 * @param parametros
	 */
	private void converterGrupoFaturamento(Map<String, Object> parametros){

		String pGrupoFaturamentoInicial = String.valueOf(parametros.get(PARAMETRO_GRUPO_I));
		String pGrupoFaturamentoFinal = String.valueOf(parametros.get(PARAMETRO_GRUPO_F));

		if(Util.isVazioOuBrancoOuZero(pGrupoFaturamentoInicial)
						|| pGrupoFaturamentoInicial.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			parametros.put(PARAMETRO_GRUPO_I, 0);
		}

		if(Util.isVazioOuBrancoOuZero(pGrupoFaturamentoFinal) || pGrupoFaturamentoFinal.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			parametros.put(PARAMETRO_GRUPO_F, 0);
		}
	}

	private void converterLocalidade(Map<String, Object> parametros){

		String pLocalidadeInicial = String.valueOf(parametros.get(PARAMETRO_LOCA_I));
		String pLocalidadeFinal = String.valueOf(parametros.get(PARAMETRO_LOCA_F));

		if(Util.isVazioOuBrancoOuZero(pLocalidadeInicial) || pLocalidadeInicial.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			parametros.put(PARAMETRO_LOCA_I, 0);
		}

		if(Util.isVazioOuBrancoOuZero(pLocalidadeFinal) || pLocalidadeFinal.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			parametros.put(PARAMETRO_LOCA_F, 0);
		}
	}

	private void converterDadosAnoMes(Map<String, Object> parametros){

		String pReferenciaAnoMes = String.valueOf(parametros.get(PARAMETRO_REF));

		if(!Util.isVazioOuBranco(pReferenciaAnoMes)){
			pReferenciaAnoMes = Util.formatarMesAnoParaAnoMesSemBarra(pReferenciaAnoMes);

			parametros.put(PARAMETRO_REF, Integer.valueOf(pReferenciaAnoMes));
		}

	}

}
