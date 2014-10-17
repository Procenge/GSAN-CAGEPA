
package br.com.procenge.geradorrelatorio.impl.validador;

import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import br.com.procenge.geradorrelatorio.api.ValidadorParametros;

public class RelatorioContasAtrasoValidadorImpl
				implements ValidadorParametros {

	private static final String PARAMETRO_QTD_CONTAS = "QTD_CONTAS";

	private static final String PARAMETRO_IND_PARCEL_ATRASO = "IND_PARCEL_ATRASO";

	private static final String PARAMETRO_VAL_DEBITO = "VAL_DEBITO";

	private static final String PARAMETRO_GRAND_USU = "GRAND_USU";

	private static final String PARAMETRO_TIPO_RELATORIO = "TIPO_RELATORIO";

	private static final String PARAMETRO_SIT_AGUA = "SIT_AGUA";

	private static final String PARAMETRO_SIT_ESGOTO = "SIT_ESGOTO";

	private static final String PARAMETRO_REG_I = "REG_I";

	private static final String PARAMETRO_REG_F = "REG_F";

	private static final String PARAMETRO_LOCA_I = "LOCA_I";

	private static final String PARAMETRO_LOCA_F = "LOCA_F";

	private static final String PARAMETRO_SETOR_I = "SETOR_I";

	private static final String PARAMETRO_SETOR_F = "SETOR_F";

	private static final String PARAMETRO_QUAD_I = "QUAD_I";

	private static final String PARAMETRO_QUAD_F = "QUAD_F";

	private static final String PARAMETRO_GRUPO_I = "GRUPO_I";

	private static final String PARAMETRO_GRUPO_F = "GRUPO_F";

	private static final String PARAMETRO_ROTA_I = "ROTA_I";

	private static final String PARAMETRO_ROTA_F = "ROTA_F";

	private static final String PARAMETRO_LOTE_I = "LOTE_I";

	private static final String PARAMETRO_LOTE_F = "LOTE_F";

	private static final String PARAMETRO_SLOTE_I = "SLOTE_I";

	private static final String PARAMETRO_SLOTE_F = "SLOTE_F";

	private static final String PARAMETRO_CAT_I = "CAT_I";

	private static final String PARAMETRO_CAT_F = "CAT_F";

	private static final String PARAMETRO_SCAT_I = "SCAT_I";

	private static final String PARAMETRO_SCAT_F = "SCAT_F";

	private static final String PARAMETRO_REF_I = "REF_I";

	private static final String PARAMETRO_REF_F = "REF_F";

	private static final String PARAMETRO_UNE_I = "UNE_I";

	private static final String PARAMETRO_UNE_F = "UNE_F";

	private static final String PARAMETRO_INSC_INI = "p_inscricaoInicial";

	private static final String PARAMETRO_INSC_FIN = "p_inscricaoFinal";

	private static final String PARAMETRO_RESP_I = "RESP_I";

	private static final String PARAMETRO_RESP_F = "RESP_F";

	private static final String P_INDICADOR_ORDENACAO = "p_indicadorOrdenacao";

	public Map<String, String> validar(Map<String, Object> parametros){

		Map<String, String> erros = new HashMap<String, String>();

		String tipoRelatorio = (String) parametros.get(PARAMETRO_TIPO_RELATORIO);
		if(Util.isVazioOuBranco(tipoRelatorio)){
			erros.put(PARAMETRO_TIPO_RELATORIO, "Tipo do relatório");
		}

		Integer referenciaDebitoInicial = (Integer) parametros.get(PARAMETRO_REF_I);
		if(referenciaDebitoInicial == null || referenciaDebitoInicial <= 0){
			erros.put(PARAMETRO_REF_I, "Referência do Débito Inicial");
		}

		Integer referenciaDebitoFinal = (Integer) parametros.get(PARAMETRO_REF_F);
		if(referenciaDebitoFinal == null || referenciaDebitoFinal <= 0){
			erros.put(PARAMETRO_REF_F, "Referência do Débito Final");
		}

		return erros;
	}

	public Map<String, Object> converter(Map<String, Object> parametros){

		parametros.put(PARAMETRO_TIPO_RELATORIO, parametros.get(PARAMETRO_TIPO_RELATORIO));
		parametros.put(PARAMETRO_QTD_CONTAS, parametros.get(PARAMETRO_QTD_CONTAS));

		this.converterDadosAnoMes(parametros);

		this.converterDadosValorDebito(parametros);
		this.converterInscricaoInicialImovel(parametros);
		this.converterInscricaoFinalImovel(parametros);

		this.converterClienteResponsavel(parametros);
		this.converterCategoria(parametros);
		this.converterUnidadeNegocio(parametros);
		this.converterGrupoFaturamento(parametros);
		this.converterGerenciaRegional(parametros);
		this.converterRota(parametros);
		this.converterSubCategoria(parametros);
		this.converterSituacaoAgua(parametros);
		this.converterSituacaoEsgoto(parametros);
		this.converterParametroParcelaAtraso(parametros);
		this.converterGrandeUsuario(parametros);
		this.converterIndicadorOrdenacao(parametros);

		return parametros;
	}

	/**
	 * Converte caso não seja informado ele seleciona o menor e o maior
	 * 
	 * @param parametros
	 */
	private void converterClienteResponsavel(Map<String, Object> parametros){

		String pRespInicial = String.valueOf(parametros.get(PARAMETRO_RESP_I));
		String pRespFinal = String.valueOf(parametros.get(PARAMETRO_RESP_F));

		if(Util.isVazioOuBrancoOuZero(pRespInicial)){
			parametros.put(PARAMETRO_RESP_I, 0);
		}

		if(Util.isVazioOuBrancoOuZero(pRespFinal)){
			parametros.put(PARAMETRO_RESP_F, 0);
		}

		if(Util.isVazioOuBranco(String.valueOf(parametros.get("nomeResponsavelInicial")))){
			parametros.put("nomeResponsavelInicial", "");
		}
		if(Util.isVazioOuBranco(String.valueOf(parametros.get("nomeResponsavelFinal")))){
			parametros.put("nomeResponsavelFinal", "");
		}
	}

	/**
	 * Converte caso não seja informado ele seleciona o menor e o maior
	 * 
	 * @param parametros
	 */
	private void converterCategoria(Map<String, Object> parametros){

		String pCategoriaInicial = String.valueOf(parametros.get(PARAMETRO_CAT_I));
		String pCategoriaFinal = String.valueOf(parametros.get(PARAMETRO_CAT_F));

		if(Util.isVazioOuBrancoOuZero(pCategoriaInicial) || pCategoriaInicial.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			parametros.put(PARAMETRO_CAT_I, 0);
		}

		if(Util.isVazioOuBrancoOuZero(pCategoriaFinal) || pCategoriaFinal.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			parametros.put(PARAMETRO_CAT_F, 0);
		}

	}

	/**
	 * Converte caso não seja informado ele seleciona o menor e o maior
	 * 
	 * @param parametros
	 */
	private void converterUnidadeNegocio(Map<String, Object> parametros){

		String pUnidadeNegocioInicial = String.valueOf(parametros.get(PARAMETRO_UNE_I));
		String pUnidadeNegocioFinal = String.valueOf(parametros.get(PARAMETRO_UNE_F));

		if(Util.isVazioOuBrancoOuZero(pUnidadeNegocioInicial) || pUnidadeNegocioInicial.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)
						|| pUnidadeNegocioInicial.equals("null")){
			parametros.put(PARAMETRO_UNE_I, 0);
		}

		if(Util.isVazioOuBrancoOuZero(pUnidadeNegocioFinal) || pUnidadeNegocioFinal.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)
						|| pUnidadeNegocioFinal.equals("null")){
			parametros.put(PARAMETRO_UNE_F, 0);
		}
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

	/**
	 * Converte caso não seja informado ele seleciona o menor e o maior
	 * 
	 * @param parametros
	 */
	private void converterGerenciaRegional(Map<String, Object> parametros){

		String pGerenciaRegionalInicial = String.valueOf(parametros.get(PARAMETRO_REG_I));
		String pGerenciaRegionalFinal = String.valueOf(parametros.get(PARAMETRO_REG_F));

		if(Util.isVazioOuBrancoOuZero(pGerenciaRegionalInicial)
						|| pGerenciaRegionalInicial.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)
						|| pGerenciaRegionalInicial.equals("null")){
			parametros.put(PARAMETRO_REG_I, 0);
		}

		if(Util.isVazioOuBrancoOuZero(pGerenciaRegionalFinal) || pGerenciaRegionalFinal.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)
						|| pGerenciaRegionalFinal.equals("null")){
			parametros.put(PARAMETRO_REG_F, 0);
		}
	}

	/**
	 * Converte caso não seja informado ele seleciona o menor e o maior
	 * 
	 * @param parametros
	 */
	private void converterRota(Map<String, Object> parametros){

		String pRotaInicial = String.valueOf(parametros.get(PARAMETRO_ROTA_I));
		String pRotaFinal = String.valueOf(parametros.get(PARAMETRO_ROTA_F));

		if(Util.isVazioOuBrancoOuZero(pRotaInicial) || pRotaInicial.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			parametros.put(PARAMETRO_ROTA_I, 0);
		}

		if(Util.isVazioOuBrancoOuZero(pRotaFinal) || pRotaFinal.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			parametros.put(PARAMETRO_ROTA_F, 0);
		}
	}

	/**
	 * Converte caso não seja informado ele seleciona o menor e o maior
	 * 
	 * @param parametros
	 */
	private void converterSubCategoria(Map<String, Object> parametros){

		String pSubCategoriaInicial = String.valueOf(parametros.get(PARAMETRO_SCAT_I));
		String pSubCategoriaFinal = String.valueOf(parametros.get(PARAMETRO_SCAT_F));

		if(Util.isVazioOuBrancoOuZero(pSubCategoriaInicial) || pSubCategoriaInicial.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			parametros.put(PARAMETRO_SCAT_I, 0);
		}

		if(Util.isVazioOuBrancoOuZero(pSubCategoriaFinal) || pSubCategoriaFinal.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			parametros.put(PARAMETRO_SCAT_F, 0);
		}
	}

	/**
	 * Converte caso não seja informado ele seleciona o menor e o maior
	 * 
	 * @param parametros
	 */
	private void converterSituacaoEsgoto(Map<String, Object> parametros){

		String pStEsgoto = String.valueOf(parametros.get(PARAMETRO_SIT_ESGOTO));

		if(Util.isVazioOuBrancoOuZero(pStEsgoto) || pStEsgoto.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			parametros.put(PARAMETRO_SIT_ESGOTO, "0");
		}
	}

	/**
	 * Converte caso não seja informado ele seleciona o menor e o maior
	 * 
	 * @param parametros
	 */
	private void converterSituacaoAgua(Map<String, Object> parametros){

		String pStAgua = String.valueOf(parametros.get(PARAMETRO_SIT_AGUA));

		if(Util.isVazioOuBrancoOuZero(pStAgua) || pStAgua.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			parametros.put(PARAMETRO_SIT_AGUA, "0");
		}
	}

	/**
	 * Converte caso não seja informado ele seleciona o menor e o maior
	 * 
	 * @param parametros
	 */
	private void converterParametroParcelaAtraso(Map<String, Object> parametros){

		String pParcelaAtraso = String.valueOf(parametros.get(PARAMETRO_IND_PARCEL_ATRASO));

		if(Util.isVazioOuBrancoOuZero(pParcelaAtraso) || pParcelaAtraso.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			parametros.put(PARAMETRO_IND_PARCEL_ATRASO, "0");
		}
	}

	/**
	 * Converte caso não seja informado ele seleciona o menor e o maior
	 * 
	 * @param parametros
	 */
	private void converterGrandeUsuario(Map<String, Object> parametros){

		String pGrandeUsuario = String.valueOf(parametros.get(PARAMETRO_GRAND_USU));

		if(Util.isVazioOuBrancoOuZero(pGrandeUsuario) || pGrandeUsuario.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			parametros.put(PARAMETRO_GRAND_USU, "0");
		}
	}

	/**
	 * Método para converter dados do parâmetro de mês e ano para ano e mês.
	 * 
	 * @author Josenildo Neves
	 * @date 11/04/2012
	 * @param parametros
	 */
	private void converterDadosAnoMes(Map<String, Object> parametros){

		String pReferenciaAnoMesDebitoInicial = String.valueOf(parametros.get(PARAMETRO_REF_I));

		if(!Util.isVazioOuBranco(pReferenciaAnoMesDebitoInicial)){
			pReferenciaAnoMesDebitoInicial = Util.formatarMesAnoParaAnoMesSemBarra(pReferenciaAnoMesDebitoInicial);

			parametros.put(PARAMETRO_REF_I, Integer.valueOf(pReferenciaAnoMesDebitoInicial));
		}

		String pReferenciaAnoMesDebitoFinal = String.valueOf(parametros.get(PARAMETRO_REF_F));
		if(!Util.isVazioOuBranco(pReferenciaAnoMesDebitoFinal)){
			pReferenciaAnoMesDebitoFinal = Util.formatarMesAnoParaAnoMesSemBarra(pReferenciaAnoMesDebitoFinal);

			parametros.put(PARAMETRO_REF_F, Integer.valueOf(pReferenciaAnoMesDebitoFinal));
		}
	}

	/**
	 * Método para converter dados do parâmetro valor do débito de String para BigDecimal.
	 * 
	 * @author Josenildo Neves
	 * @date 11/04/2012
	 * @param parametros
	 */
	private void converterDadosValorDebito(Map<String, Object> parametros){

		String pValorDebito = String.valueOf(parametros.get(PARAMETRO_VAL_DEBITO));
		if(!Util.isVazioOuBranco(pValorDebito)){
			BigDecimal pValorDebitoBigDecimal = Util.formatarMoedaRealparaBigDecimal(pValorDebito);

			parametros.put(PARAMETRO_VAL_DEBITO, pValorDebitoBigDecimal.toString());
		}else{
			parametros.put(PARAMETRO_VAL_DEBITO, "0");
		}
	}

	/**
	 * Método para converter dados do parâmetro inscrição do imóvel
	 * 
	 * @author Hugo Lima
	 * @date 17/05/2012
	 * @param parametros
	 */
	private void converterInscricaoInicialImovel(Map<String, Object> parametros){

		String localidade = String.valueOf(parametros.get(PARAMETRO_LOCA_I));
		String codigoSetorComercial = String.valueOf(parametros.get(PARAMETRO_SETOR_I));
		String numeroQuadra = String.valueOf(parametros.get(PARAMETRO_QUAD_I));
		String lote = String.valueOf(parametros.get(PARAMETRO_LOTE_I));
		String subLote = String.valueOf(parametros.get(PARAMETRO_SLOTE_I));

		Imovel imovel = new Imovel();
		imovel.setLocalidade(new Localidade(Integer.valueOf(localidade)));

		SetorComercial setorComercial = new SetorComercial();
		setorComercial.setCodigo(Integer.parseInt(codigoSetorComercial));
		imovel.setSetorComercial(setorComercial);

		Quadra quadra = new Quadra();
		quadra.setNumeroQuadra(Integer.parseInt(numeroQuadra));
		imovel.setQuadra(quadra);

		imovel.setLote(Short.valueOf(lote));
		imovel.setSubLote(Short.valueOf(subLote));

		parametros.put(PARAMETRO_INSC_INI, imovel.getInscricaoFormatada());

		if(Util.isVazioOuBranco(String.valueOf(parametros.get("nomeSetorComercialInicial")))){
			parametros.put("nomeSetorComercialInicial", "");
		}
		if(Util.isVazioOuBranco(String.valueOf(parametros.get("nomeLocalidadeInicial")))){
			parametros.put("nomeLocalidadeInicial", "");
		}
		if(Util.isVazioOuBranco(String.valueOf(parametros.get("setorComercialInicialEncontrado")))){
			parametros.put("setorComercialInicialEncontrado", "");
		}
		if(Util.isVazioOuBranco(String.valueOf(parametros.get("localidadeInicialEncontrada")))){
			parametros.put("localidadeInicialEncontrada", "");
		}
	}

	/**
	 * Método para converter dados do parâmetro inscrição do imóvel
	 * 
	 * @author Hugo Lima
	 * @date 17/05/2012
	 * @param parametros
	 */
	private void converterInscricaoFinalImovel(Map<String, Object> parametros){

		String localidade = String.valueOf(parametros.get(PARAMETRO_LOCA_F));
		String codigoSetorComercial = String.valueOf(parametros.get(PARAMETRO_SETOR_F));
		String numeroQuadra = String.valueOf(parametros.get(PARAMETRO_QUAD_F));
		String lote = String.valueOf(parametros.get(PARAMETRO_LOTE_F));
		String subLote = String.valueOf(parametros.get(PARAMETRO_SLOTE_F));

		if(Util.isVazioOuBranco(localidade) || localidade.equals("0")){
			localidade = "999";
			parametros.put(PARAMETRO_LOCA_F, "999");
		}
		if(Util.isVazioOuBranco(codigoSetorComercial) || codigoSetorComercial.equals("0")){
			codigoSetorComercial = "999";
			parametros.put(PARAMETRO_SETOR_F, "999");
		}
		if(Util.isVazioOuBranco(numeroQuadra) || numeroQuadra.equals("0")){
			numeroQuadra = "9999";
			parametros.put(PARAMETRO_QUAD_F, "9999");
		}
		if(Util.isVazioOuBranco(lote) || lote.equals("0")){
			lote = "9999";
			parametros.put(PARAMETRO_LOTE_F, "9999");
		}
		if(Util.isVazioOuBranco(subLote) || subLote.equals("0")){
			subLote = "999";
			parametros.put(PARAMETRO_SLOTE_F, "999");
		}

		Imovel imovel = new Imovel();
		imovel.setLocalidade(new Localidade(Integer.valueOf(localidade)));

		SetorComercial setorComercial = new SetorComercial();
		setorComercial.setCodigo(Integer.parseInt(codigoSetorComercial));
		imovel.setSetorComercial(setorComercial);

		Quadra quadra = new Quadra();
		quadra.setNumeroQuadra(Integer.parseInt(numeroQuadra));
		imovel.setQuadra(quadra);

		imovel.setLote(Short.valueOf(lote));
		imovel.setSubLote(Short.valueOf(subLote));

		parametros.put(PARAMETRO_INSC_FIN, imovel.getInscricaoFormatada());

		if(Util.isVazioOuBranco(String.valueOf(parametros.get("nomeSetorComercialFinal")))){
			parametros.put("nomeSetorComercialFinal", "");
		}
		if(Util.isVazioOuBranco(String.valueOf(parametros.get("nomeLocalidadeFinal")))){
			parametros.put("nomeLocalidadeFinal", "");
		}
		if(Util.isVazioOuBranco(String.valueOf(parametros.get("setorComercialFinalEncontrado")))){
			parametros.put("setorComercialFinalEncontrado", "");
		}
		if(Util.isVazioOuBranco(String.valueOf(parametros.get("localidadeFinalEncontrada")))){
			parametros.put("localidadeFinalEncontrada", "");
		}
	}

	private void converterIndicadorOrdenacao(Map<String, Object> parametros){

		// Pega os valores em String
		String indicadorOrdenacaoString = Util.converterObjetoParaString(parametros.get(P_INDICADOR_ORDENACAO));

		// Valida se não está nulo
		if(!Util.isVazioOuBranco(indicadorOrdenacaoString)){
			parametros.put(P_INDICADOR_ORDENACAO, indicadorOrdenacaoString);
		}
	}

}
