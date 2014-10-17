
package br.com.procenge.geradorrelatorio.impl.validador;

import gcom.util.Util;

import java.util.HashMap;
import java.util.Map;

import br.com.procenge.geradorrelatorio.api.ValidadorParametros;

/**
 * Classe que valida os parâmetros da tela para gerar o relatório de espelho de cadastro.
 * 
 * @author Carlos Chrystian
 * @date 19/09/2012
 */
public class RelatorioConsumidoresOcorrenciaLeiturasValidadorImpl
				implements ValidadorParametros {

	public static final String P_AM_REFERENCIA = "p_nAmRef";

	public static final String P_REGIONAL = "p_nRegiao";

	public static final String P_UNIDADE_NEGOCIO = "p_nUnidNegocio";

	public static final String P_UNIDADE_FEDERACAO = "p_nUF";

	public static final String P_GRUPO_FATURAMENTO = "p_nGrupo";

	public static final String P_LOCALIDADE_VINCULADA = "p_nLocVinculada";

	public static final String P_LOCALIDADE = "p_nLocalidade";

	public static final String P_CD_SETOR_COMERCIAL = "p_nCodSetor";

	public static final String P_QUADRA = "p_nQuadra";

	private static final Integer VALOR_NAO_INFORMADO = Integer.valueOf(0);

	private static final String MENOS_UM = "-1";

	private static final String P_EXIBIR_TOTAL_SETOR = "p_sExibirTotalSetor";

	private static final String P_EXIBIR_TOTAL_LOCALIDADE = "p_sExibirTotalLocalidade";

	private static final String P_EXIBIR_TOTAL_LOCA_VINCULADA = "p_sExibirTotalLocaVinculada";

	private static final String P_EXIBIR_TOTAL_UNID_NEGOCIO = "p_sExibirTotalUnidNegocio";

	private static final String P_EXIBIR_TOTAL_REGIAO = "p_sExibirTotalRegiao";

	private static final String P_EXIBIR_TOTAL_GRUPO = "p_sExibirTotalGrupo";

	private static final String P_EXIBIR_TOTAL_ESTADO = "p_sExibirTotalEstado";

	private static final String P_EXIBIR_TOTAL_QUADRA = "p_sExibirTotalQuadra";

	/**
	 * Método que valida os parâmetros para o rpt.
	 * 
	 * @author Carlos Chrystian
	 * @date 19/09/2012
	 */
	public Map<String, String> validar(Map<String, Object> parametros){

		Map<String, String> erros = new HashMap<String, String>();

		// Validar se o parametro é uma data anoMes
		String anoMes = (String) parametros.get(P_AM_REFERENCIA);
		if(Util.isVazioOuBranco(anoMes)){
			erros.put(P_AM_REFERENCIA, "Parâmetro Ano / mês obrigatório.");
		}

		return erros;
	}

	/**
	 * Método que converte todos os parâmetros.
	 * 
	 * @author Carlos Chrystian
	 * @date 19/09/2012
	 */
	public Map<String, Object> converter(Map<String, Object> parametros){

		Map<String, Object> parametrosConvertidos = new HashMap<String, Object>();

		// Atribui valores padrões aos paramêtros que não estão na tela.
		this.atribuirValoresDefaults(parametrosConvertidos);

		// Converte os parâmetros
		this.converterAnoMesReferencia(parametros, parametrosConvertidos);
		this.converterRegional(parametros, parametrosConvertidos);
		this.converterUnidadeNegocio(parametros, parametrosConvertidos);
		this.converterUnidadeFederacao(parametros, parametrosConvertidos);
		this.converterGrupoFaturamento(parametros, parametrosConvertidos);
		this.converterLocalidadeVinculada(parametros, parametrosConvertidos);
		this.converterLocalidade(parametros, parametrosConvertidos);
		this.converterSetorComercial(parametros, parametrosConvertidos);
		this.converterQuadra(parametros, parametrosConvertidos);

		return parametrosConvertidos;
	}

	/**
	 * Método que converte Reginal.
	 * 
	 * @author Carlos Chrystian
	 * @date 19/09/2012
	 */
	private void atribuirValoresDefaults(Map<String, Object> parametros){

		String exibirTotal = "N";

		parametros.put(P_EXIBIR_TOTAL_SETOR, exibirTotal);

		parametros.put(P_EXIBIR_TOTAL_LOCALIDADE, exibirTotal);

		parametros.put(P_EXIBIR_TOTAL_LOCA_VINCULADA, exibirTotal);

		parametros.put(P_EXIBIR_TOTAL_UNID_NEGOCIO, exibirTotal);

		parametros.put(P_EXIBIR_TOTAL_REGIAO, exibirTotal);

		parametros.put(P_EXIBIR_TOTAL_GRUPO, exibirTotal);

		parametros.put(P_EXIBIR_TOTAL_ESTADO, exibirTotal);

		parametros.put(P_EXIBIR_TOTAL_QUADRA, exibirTotal);

	}

	/**
	 * Método que converte Reginal.
	 * 
	 * @author Carlos Chrystian
	 * @date 19/09/2012
	 */
	private void converterAnoMesReferencia(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		// Pega os valores em String
		String anoMesReferenciaString = Util.converterObjetoParaString(parametros.get(P_AM_REFERENCIA));
		// Atribui o valor padrão. Se o parâmetro não for informado, será passado este valor
		Integer anoMesReferencia = new Integer("0");

		// Valida se não está nulo
		if(!Util.isVazioOuBranco(anoMesReferenciaString)){
			anoMesReferencia = Util.converterStringParaInteger(Util.formatarMesAnoParaAnoMesSemBarra(anoMesReferenciaString));
		}

		parametrosConvertidos.put(P_AM_REFERENCIA, anoMesReferencia);

	}

	/**
	 * Método que converte Reginal.
	 * 
	 * @author Carlos Chrystian
	 * @date 19/09/2012
	 */
	private void converterRegional(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		// Pega os valores em String
		String idRegionalString = Util.converterObjetoParaString(parametros.get(P_REGIONAL));
		// Atribui o valor padrão. Se o parâmetro não for informado, será passado este valor
		Integer idRegional = VALOR_NAO_INFORMADO;

		// Valida se não está nulo
		if(!Util.isVazioOuBranco(idRegionalString) && !idRegionalString.equals(MENOS_UM)){
			idRegional = Util.converterStringParaInteger(idRegionalString);
		}

		parametrosConvertidos.put(P_REGIONAL, idRegional);

	}

	/**
	 * Método que converte Unidade de Negócio.
	 * 
	 * @author Carlos Chrystian
	 * @date 19/09/2012
	 */
	private void converterUnidadeNegocio(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		// Pega os valores em String
		String idUnidadeNegocioString = Util.converterObjetoParaString(parametros.get(P_UNIDADE_NEGOCIO));

		Integer idUnidadeNegocio = VALOR_NAO_INFORMADO;

		// Atribui o valor padrão. Se o parâmetro não for informado, será passado este valor
		if(!Util.isVazioOuBranco(idUnidadeNegocioString) && !idUnidadeNegocioString.equals(MENOS_UM)){
			idUnidadeNegocio = Util.converterStringParaInteger(idUnidadeNegocioString);
		}

		parametrosConvertidos.put(P_UNIDADE_NEGOCIO, idUnidadeNegocio);

	}

	/**
	 * Método que converte Unidade de Negócio.
	 * 
	 * @author Carlos Chrystian
	 * @date 19/09/2012
	 */
	private void converterUnidadeFederacao(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		// Pega os valores em String
		String idUnidadeFederacaoString = Util.converterObjetoParaString(parametros.get(P_UNIDADE_FEDERACAO));

		Integer idUnidadeFederacao = VALOR_NAO_INFORMADO;

		// Atribui o valor padrão. Se o parâmetro não for informado, será passado este valor
		if(!Util.isVazioOuBranco(idUnidadeFederacaoString) && !idUnidadeFederacaoString.equals(MENOS_UM)){
			idUnidadeFederacao = Util.converterStringParaInteger(idUnidadeFederacaoString);
		}

		parametrosConvertidos.put(P_UNIDADE_FEDERACAO, idUnidadeFederacao);

	}

	/**
	 * Método que converte Grupo Faturamento.
	 * 
	 * @author Carlos Chrystian
	 * @date 19/09/2012
	 */
	private void converterGrupoFaturamento(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		// Pega os valores em String
		String idGrupoFaturamentoString = Util.converterObjetoParaString(parametros.get(P_GRUPO_FATURAMENTO));

		// Atribui o valor padrão. Se o parâmetro não for informado, será passado este valor
		Integer idGrupoFaturamento = VALOR_NAO_INFORMADO;

		// Valida se não está nulo
		if(!Util.isVazioOuBranco(idGrupoFaturamentoString) && !idGrupoFaturamentoString.equals(MENOS_UM)){
			// Converte os dados de String para Integer
			idGrupoFaturamento = Util.converterStringParaInteger(idGrupoFaturamentoString);
		}

		parametrosConvertidos.put(P_GRUPO_FATURAMENTO, idGrupoFaturamento);
	}

	/**
	 * Método que converte Localidade Vinculada.
	 * 
	 * @author Carlos Chrystian
	 * @date 19/09/2012
	 */
	private void converterLocalidadeVinculada(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		// Pega os valores em String
		String idLocalidadeVinculadaString = Util.converterObjetoParaString(parametros.get(P_LOCALIDADE_VINCULADA));

		Integer idLocalidadeVinculada = VALOR_NAO_INFORMADO;

		// Atribui o valor padrão. Se o parâmetro não for informado, será passado este valor
		if(!Util.isVazioOuBranco(idLocalidadeVinculadaString)){
			idLocalidadeVinculada = Util.converterStringParaInteger(idLocalidadeVinculadaString);
		}

		parametrosConvertidos.put(P_LOCALIDADE_VINCULADA, idLocalidadeVinculada);
	}

	/**
	 * Método que converte Localidade.
	 * 
	 * @author Carlos Chrystian
	 * @date 19/09/2012
	 */
	private void converterLocalidade(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		// Pega os valores em String
		String idLocalidadeString = Util.converterObjetoParaString(parametros.get(P_LOCALIDADE));

		Integer idLocalidade = VALOR_NAO_INFORMADO;

		// Atribui o valor padrão. Se o parâmetro não for informado, será passado este valor
		if(!Util.isVazioOuBranco(idLocalidadeString)){
			idLocalidade = Util.converterStringParaInteger(idLocalidadeString);
		}

		parametrosConvertidos.put(P_LOCALIDADE, idLocalidade);
	}

	/**
	 * Método que converte Setor Comercial.
	 * 
	 * @author Carlos Chrystian
	 * @date 19/09/2012
	 */
	private void converterSetorComercial(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		String idSetorComercialString = Util.converterObjetoParaString(parametros.get(P_CD_SETOR_COMERCIAL));

		// Atribui o valor padrão. Se o parâmetro não for informado, será passado este valor
		Integer idSetorComercial = VALOR_NAO_INFORMADO;

		// Verifica se foi preenchido
		if(!Util.isVazioOuBranco(idSetorComercialString)){
			idSetorComercial = Util.converterStringParaInteger(idSetorComercialString);
		}

		parametrosConvertidos.put(P_CD_SETOR_COMERCIAL, idSetorComercial);

	}

	/**
	 * Método que converte Quadra.
	 * 
	 * @author Carlos Chrystian
	 * @date 19/09/2012
	 */
	private void converterQuadra(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		// Pega os valores em String
		String idQuadraString = Util.converterObjetoParaString(parametros.get(P_QUADRA));

		// Atribui o valor padrão. Se o parâmetro não for informado, será passado este valor
		Integer idQuadra = VALOR_NAO_INFORMADO;

		if(!Util.isVazioOuBranco(idQuadraString)){
			idQuadra = Util.converterStringParaInteger(idQuadraString);
		}

		parametrosConvertidos.put(P_QUADRA, idQuadra);

	}

}
