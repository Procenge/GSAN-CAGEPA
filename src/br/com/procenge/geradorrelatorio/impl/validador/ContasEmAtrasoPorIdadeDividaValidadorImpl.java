
package br.com.procenge.geradorrelatorio.impl.validador;

import gcom.util.Util;

import java.util.HashMap;
import java.util.Map;

import br.com.procenge.geradorrelatorio.api.ValidadorParametros;

/**
 * Classe que valida os parâmetros da tela para gerar o relatório de espelho de cadastro.
 * 
 * @author Luciano Galvão
 * @date 03/04/2012
 */
public class ContasEmAtrasoPorIdadeDividaValidadorImpl
				implements ValidadorParametros {

	public static final String P_LOCALIDADE_FINAL = "P_LOC_FIN";

	public static final String P_LOCALIDADE_INICIAL = "P_LOC_INI";

	public static final String P_REFERENCIA_DEBITO = "P_AM_REF";

	public static final String P_REGIONAL_FINAL = "P_GR_FIN";

	public static final String P_REGIONAL_INICIAL = "P_GR_INI";

	public static final String P_SETOR_FATURAMENTO_FINAL = "P_SET_FIN";

	public static final String P_SETOR_FATURAMENTO_INICIAL = "P_SET_INI";

	public static final String P_TIPO_RELATORIO = "P_TP_RELATORIO";

	private static final String VALOR_NAO_INFORMADO = "0";

	private static final String VALOR_MINIMO = "0";

	private static final String VALOR_MAXIMO = "999999";

	private static final String MENOS_UM = "-1";

	private static final String TIPO_GERAL = "1";

	private static final String TIPO_PARTICULAR = "2";

	private static final String TIPO_PUBLICO = "3";

	private static final String VALOR_TIPO_GERAL = "1";

	private static final String VALOR_TIPO_PARTICULAR = "2";

	private static final String VALOR_TIPO_PUBLICO = "3";

	public static final String P_UNIDADE_NEGOCIO_FINAL = "P_UNE_FIN";

	public static final String P_UNIDADE_NEGOCIO_INICIAL = "P_UNE_INI";

	/**
	 * Método que converte todos os parâmetros.
	 * 
	 * @author Luciano Galvão
	 * @date 03/04/2012
	 */
	public Map<String, Object> converter(Map<String, Object> parametros){

		Map<String, Object> parametrosConvertidos = new HashMap<String, Object>();

		// Converte os parâmetros
		this.converterRegionais(parametros, parametrosConvertidos);
		this.converterLocalidades(parametros, parametrosConvertidos);
		this.converterSetoresFaturamento(parametros, parametrosConvertidos);
		this.converterReferenciaDebito(parametros, parametrosConvertidos);
		this.converterTipoRelatorio(parametros, parametrosConvertidos);
		this.converterUnidadeNegocio(parametros, parametrosConvertidos);

		return parametrosConvertidos;
	}

	/**
	 * Método que valida todos os parâmetros.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 30/03/2012
	 */
	public Map<String, String> validar(Map<String, Object> parametros){

		Map<String, String> erros = new HashMap<String, String>();

		String idRegionalInicial = (String) parametros.get(P_REGIONAL_INICIAL);
		String idRegionalFinal = (String) parametros.get(P_REGIONAL_FINAL);
		String idLocalidadeInicial = (String) parametros.get(P_LOCALIDADE_INICIAL);
		String idLocalidadeFinal = (String) parametros.get(P_LOCALIDADE_FINAL);
		String idSetorFaturamentoInicial = (String) parametros.get(P_SETOR_FATURAMENTO_INICIAL);
		String idSetorFaturamentoFinal = (String) parametros.get(P_SETOR_FATURAMENTO_FINAL);
		String referenciaDebito = (String) parametros.get(P_REFERENCIA_DEBITO);
		String tipoRelatorio = (String) parametros.get(P_SETOR_FATURAMENTO_FINAL);
		String idUnidadeNegocioInicial = (String) parametros.get(P_UNIDADE_NEGOCIO_INICIAL);
		String idUnidadeNegocioFinal = (String) parametros.get(P_UNIDADE_NEGOCIO_FINAL);

		// Valida os intervalos de valores
		validarIntervalo(idRegionalInicial, idRegionalFinal, erros, P_REGIONAL_INICIAL, P_REGIONAL_FINAL, "Regional");
		validarIntervalo(idLocalidadeInicial, idLocalidadeFinal, erros, P_LOCALIDADE_INICIAL, P_LOCALIDADE_FINAL, "Localidade");
		validarIntervalo(idSetorFaturamentoInicial, idSetorFaturamentoFinal, erros, P_SETOR_FATURAMENTO_INICIAL, P_SETOR_FATURAMENTO_FINAL,
						"Setor Comercial");
		validarIntervalo(idUnidadeNegocioInicial, idUnidadeNegocioFinal, erros, P_UNIDADE_NEGOCIO_INICIAL, P_UNIDADE_NEGOCIO_FINAL,
						"Unidade de Negocio");

		if((referenciaDebito == null) || (!Util.validarAnoMesDiaSemBarra(referenciaDebito))){
			erros.put(P_TIPO_RELATORIO, "Informe a Referência de Débito no formato AAAAMM");
		}

		if(tipoRelatorio == null){
			erros.put(P_TIPO_RELATORIO, "Informe o Tipo de Relatório");
		}

		return erros;
	}

	/**
	 * Valida um intervalo de valores
	 * 
	 * @author Luciano Galvão
	 * @date 02/04/2012
	 * @param erros
	 * @param valorInicial
	 * @param valorFinal
	 */
	private void validarIntervalo(String valorInicial, String valorFinal, Map<String, String> erros, String nomeParametroInicial,
					String nomeParametroFinal, String nomeEntidade){

		if(!Util.isNumero(valorInicial, false, 0)){
			erros.put(nomeParametroInicial, nomeEntidade + " Inicial deve ser numérico.");

		}else if(!Util.isNumero(valorFinal, false, 0)){
			erros.put(nomeParametroFinal, nomeEntidade + " Final deve ser numérico.");

		}else{

			// Se o valor inicial está vazio
			if(valorInicial.equals(VALOR_NAO_INFORMADO)){
				// retorna erro
				erros.put(nomeParametroInicial, nomeEntidade + " Inicial deve ser preenchido.");
			}

			// Se o valor inicial está vazio
			if(valorFinal.equals(VALOR_NAO_INFORMADO)){
				// retorna erro
				erros.put(nomeParametroFinal, nomeEntidade + " Final deve ser preenchido.");
			}

			if(!valorInicial.equals(VALOR_NAO_INFORMADO) && !valorFinal.equals(VALOR_NAO_INFORMADO)){

				Integer valorInicialInt = Integer.parseInt(valorInicial);
				Integer valorFinalInt = Integer.parseInt(valorFinal);

				// Se o valor final é menor que o inicial
				if(valorFinalInt.compareTo(valorInicialInt) < 0){
					erros
									.put(nomeParametroFinal, nomeEntidade + " Final deve ser maior ou igual ao valor de " + nomeEntidade
													+ " Inicial.");
				}
			}
		}
	}

	private void converterRegionais(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		// Pega os valores em String
		String idRegionalInicial = Util.converterObjetoParaString(parametros.get(P_REGIONAL_INICIAL));
		String idRegionalFinal = Util.converterObjetoParaString(parametros.get(P_REGIONAL_FINAL));

		// Valida se não está nulo
		if(Util.isVazioOuBranco(idRegionalInicial) || idRegionalInicial.equals(MENOS_UM)){
			idRegionalInicial = VALOR_NAO_INFORMADO;
		}
		if(Util.isVazioOuBranco(idRegionalFinal) || idRegionalFinal.equals(MENOS_UM)){
			idRegionalFinal = VALOR_NAO_INFORMADO;
		}

		parametrosConvertidos.put(P_REGIONAL_INICIAL, idRegionalInicial);
		parametrosConvertidos.put(P_REGIONAL_FINAL, idRegionalFinal);
	}

	private void converterLocalidades(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		// Pega os valores em String
		String idLocalidadeInicial = Util.converterObjetoParaString(parametros.get(P_LOCALIDADE_INICIAL));
		String idLocalidadeFinal = Util.converterObjetoParaString(parametros.get(P_LOCALIDADE_FINAL));

		// Atribui o valor padrão. Se o parâmetro não for informado, será passado este valor
		if(Util.isVazioOuBranco(idLocalidadeInicial) || idLocalidadeInicial.equals(MENOS_UM)){
			idLocalidadeInicial = VALOR_MINIMO;
		}
		if(Util.isVazioOuBranco(idLocalidadeFinal) || idLocalidadeInicial.equals(MENOS_UM)){
			idLocalidadeFinal = VALOR_MAXIMO;
		}

		parametrosConvertidos.put(P_LOCALIDADE_INICIAL, idLocalidadeInicial);
		parametrosConvertidos.put(P_LOCALIDADE_FINAL, idLocalidadeFinal);
	}

	private void converterSetoresFaturamento(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		String idSetorFaturamentoInicial = Util.converterObjetoParaString(parametros.get(P_SETOR_FATURAMENTO_INICIAL));
		String idSetorFaturamentoFinal = Util.converterObjetoParaString(parametros.get(P_SETOR_FATURAMENTO_FINAL));

		// Verifica se foi preenchido
		if(Util.isVazioOuBranco(idSetorFaturamentoInicial) || idSetorFaturamentoInicial.equals(MENOS_UM)){
			idSetorFaturamentoInicial = VALOR_MINIMO;
		}

		if(Util.isVazioOuBranco(idSetorFaturamentoFinal) || idSetorFaturamentoFinal.equals(MENOS_UM)){
			idSetorFaturamentoFinal = VALOR_MAXIMO;
		}

		parametrosConvertidos.put(P_SETOR_FATURAMENTO_INICIAL, idSetorFaturamentoInicial);
		parametrosConvertidos.put(P_SETOR_FATURAMENTO_FINAL, idSetorFaturamentoFinal);
	}

	private void converterTipoRelatorio(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		String tipoRelatorio = null;

		// Pega os valores em String
		String tipoRelatorioStr = Util.converterObjetoParaString(parametros.get(P_TIPO_RELATORIO));

		// Valida se não está nulo
		if(!Util.isVazioOuBranco(tipoRelatorioStr)){

			// Atribui o valor correspondente ao tipo de relatório
			// Se for tipo de relatório igual a GERAL, o valor correspondente é "" (vazio)
			if(tipoRelatorioStr.equals(TIPO_GERAL)){
				tipoRelatorio = VALOR_TIPO_GERAL;
			}else if(tipoRelatorioStr.equals(TIPO_PARTICULAR)){
				tipoRelatorio = VALOR_TIPO_PARTICULAR;
			}else if(tipoRelatorioStr.equals(TIPO_PUBLICO)){
				tipoRelatorio = VALOR_TIPO_PUBLICO;
			}
		}

		parametrosConvertidos.put(P_TIPO_RELATORIO, tipoRelatorio);
	}

	/**
	 * Método para converter dados do parâmetro de mês e ano para ano e mês.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 27/03/2012
	 * @param parametros
	 */
	private void converterReferenciaDebito(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		String referenciaDebitoStr = String.valueOf(parametros.get(P_REFERENCIA_DEBITO));
		String referenciaDebito = null;

		if(!Util.isVazioOuBranco(referenciaDebitoStr)){
			referenciaDebito = Util.formatarMesAnoParaAnoMesSemBarra(referenciaDebitoStr);
		}

		parametrosConvertidos.put(P_REFERENCIA_DEBITO, referenciaDebito);
	}

	private void converterUnidadeNegocio(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		// Pega os valores em String
		String idUnidadeNegocioInicial = Util.converterObjetoParaString(parametros.get(P_UNIDADE_NEGOCIO_INICIAL));
		String idUnidadeNegocioFinal = Util.converterObjetoParaString(parametros.get(P_UNIDADE_NEGOCIO_FINAL));

		// Valida se não está nulo
		if(Util.isVazioOuBranco(idUnidadeNegocioInicial) || idUnidadeNegocioInicial.equals(MENOS_UM)){
			idUnidadeNegocioInicial = VALOR_NAO_INFORMADO;
		}
		if(Util.isVazioOuBranco(idUnidadeNegocioFinal) || idUnidadeNegocioFinal.equals(MENOS_UM)){
			idUnidadeNegocioFinal = VALOR_NAO_INFORMADO;
		}

		parametrosConvertidos.put(P_UNIDADE_NEGOCIO_INICIAL, idUnidadeNegocioInicial);
		parametrosConvertidos.put(P_UNIDADE_NEGOCIO_FINAL, idUnidadeNegocioFinal);
	}
}
