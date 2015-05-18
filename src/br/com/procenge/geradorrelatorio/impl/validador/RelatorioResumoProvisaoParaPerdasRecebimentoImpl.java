package br.com.procenge.geradorrelatorio.impl.validador;

import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.HashMap;
import java.util.Map;

import br.com.procenge.geradorrelatorio.api.ValidadorParametros;


public class RelatorioResumoProvisaoParaPerdasRecebimentoImpl
				implements ValidadorParametros {

	private static final String psNumeroAno = "psNumeroAno";

	private static final String psNumeroMes = "psNumeroMes";

	private static final String psTipoRelatorio = "psTipoRelatorio";

	private static final String psCodLocalidade = "psCodLocalidade";

	private static final String psCodMunicipio = "psCodMunicipio";

	private static final String psCodRegional = "psCodRegional";




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
				String locaId = (String) parametros.get(psCodLocalidade);
		if(Util.isVazioOuBranco(locaId)){
			erros.put(psCodLocalidade, "Localidade");
		}

		
		String munId = (String) parametros.get(psCodMunicipio);
		if(Util.isVazioOuBranco(munId)){
			erros.put(psCodMunicipio, "Municipio");
		}
		String regId = (String) parametros.get(psCodRegional);
		if(Util.isVazioOuBranco(regId)){
			erros.put(psCodRegional, "Regional");
		}


		return erros;
	}

	public Map<String, Object> converter(Map<String, Object> parametros){


		
		this.converterGerenciaRegional(parametros);

		parametros.put(psNumeroAno, parametros.get(psNumeroAno));
		parametros.put(psNumeroMes, parametros.get(psNumeroMes));
		parametros.put(psTipoRelatorio, parametros.get(psTipoRelatorio));
		parametros.put(psCodLocalidade, parametros.get(psCodLocalidade));
		parametros.put(psCodMunicipio, parametros.get(psCodMunicipio));

		
		return parametros;
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
