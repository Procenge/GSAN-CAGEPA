package br.com.procenge.geradorrelatorio.impl.validador;

import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.HashMap;
import java.util.Map;

import br.com.procenge.geradorrelatorio.api.ValidadorParametros;

public class RelatorioProvisaoParaPerdasRecebimentoImpl
				implements ValidadorParametros {

	private static final String psNumeroAno = "ANO";

	private static final String psNumeroMes = "MES";

	private static final String psTipoRelatorio = "TipoRelatorio";

	private static final String pnValorMinimo = "VAL_MIN_CTA";

	private static final String pnValorMaximo = "VAL_MAX_CTA";

	private static final String pnDiasMinimo = "MIN_DIAS_ATS";

	private static final String pnDiasMaximo = "MAX_DIAS_ATS";

	private static final String psCodCategoria = "CATG_ID";

	private static final String psCodLocalidade = "LOCA_ID";

	private static final String psCodMunicipio = "MUN_ID";

	private static final String psCodRegional = "REG_ID";




	public Map<String, String> validar(Map<String, Object> parametros){

		Map<String, String> erros = new HashMap<String, String>();

		String ano = (String) parametros.get(psNumeroAno);
		if(Util.isVazioOuBranco(ano)){
			erros.put(psNumeroAno, "Ano");
		}
		String mes = (String) parametros.get(psNumeroMes);
		if(Util.isVazioOuBranco(mes)){
			erros.put(psNumeroMes, "Mês");
		}
		String valMinCta = (String) parametros.get(pnValorMinimo);
		if(Util.isVazioOuBranco(valMinCta)){
			erros.put(pnValorMinimo, "Valor Mínimo da Conta");
		}
		String valMaxCta = (String) parametros.get(pnValorMaximo);
		if(Util.isVazioOuBranco(valMaxCta)){
			erros.put(pnValorMaximo, "Valor Máximo da Conta");
		}
		String minDiasAts = (String) parametros.get(pnDiasMinimo);
		if(Util.isVazioOuBranco(minDiasAts)){
			erros.put(psNumeroAno, "Mínimo de dias de Atraso");
		}
		String maxDiasAts = (String) parametros.get(pnDiasMaximo);
		if(Util.isVazioOuBranco(maxDiasAts)){
			erros.put(psNumeroAno, "Máximo de dias de Atraso");
		}

		return erros;
	}

	public Map<String, Object> converter(Map<String, Object> parametros){


		
		this.converterCategoria(parametros);
		this.converterGerenciaRegional(parametros);

		parametros.put(psNumeroAno, parametros.get(psNumeroAno));
		parametros.put(psNumeroMes, parametros.get(psNumeroMes));
		parametros.put(psTipoRelatorio, parametros.get(psTipoRelatorio));
		parametros.put(pnValorMinimo, Util.formatarStringMoedaRealparaBigDecimal((String) parametros.get(pnValorMinimo), 2));
		parametros.put(pnValorMaximo, Util.formatarStringMoedaRealparaBigDecimal((String) parametros.get(pnValorMaximo), 2));
		parametros.put(pnDiasMinimo, parametros.get(pnDiasMinimo));
		parametros.put(pnDiasMaximo, parametros.get(pnDiasMaximo));
		parametros.put(psCodLocalidade, parametros.get(psCodLocalidade));
		parametros.put(psCodMunicipio, parametros.get(psCodMunicipio));

		
		return parametros;
	}



	/**
	 * Converte caso não seja informado ele seleciona o menor e o maior
	 * 
	 * @param parametros
	 */
	private void converterCategoria(Map<String, Object> parametros){

		String pCategoriaInicial = String.valueOf(parametros.get(psCodCategoria));

		if(Util.isVazioOuBrancoOuZero(pCategoriaInicial) || pCategoriaInicial.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			parametros.put(psCodCategoria, 0);
		}



	}

	/**
	 * Converte caso não seja informado ele seleciona o menor e o maior
	 * 
	 * @param parametros
	 */
	private void converterGerenciaRegional(Map<String, Object> parametros){

		String pGerenciaRegionalInicial = String.valueOf(parametros.get(psCodRegional));

		if(Util.isVazioOuBrancoOuZero(pGerenciaRegionalInicial)
						|| pGerenciaRegionalInicial.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)
						|| pGerenciaRegionalInicial.equals("null")){
			parametros.put(psCodRegional, 0);
		}


	}









}
