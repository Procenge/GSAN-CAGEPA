
package br.com.procenge.geradorrelatorio.impl.validador;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.util.Util;

import java.util.HashMap;
import java.util.Map;

import br.com.procenge.geradorrelatorio.api.ValidadorParametros;

/**
 * Classe que valida os relat�rios de Resumo de Arrecada��o e Cobran�a, Inclus�es e Cancelamentos de
 * Faturamento e
 * Resumo de Faturamento.
 * 
 * @author Ricardo Rodrigues
 * @date 02/04/2012
 */
public class TipoRelatorioValidadorImpl
				implements ValidadorParametros {

	// Constantes dos par�metros da tela para o RPT.
	private static final String PARAMETRO_RELATORIO_TIPO = "psRelatorioTipo";

	private static final String PARAMETRO_ANO_MES = "mesAno";

	private static final String PARAMETRO_OPCAO_TOTALIZACAO = "psTipoRelatorio";

	private static final String PARAMETRO_CODIGO_LOCALIDADE = "psCodigoLocalidade";

	private static final String PARAMETRO_GERENCIA_REGIONAL_LOCALIDADE = "gerenciaRegionalporLocalidadeId";

	private static final String PARAMETRO_GERENCIA_REGIONAL = "gerenciaRegionalId";

	private static final String PARAMETRO_CODIGO_GERENCIA = "psCodigoGerencia";

	private static final String PARAMETRO_ANO = "psNumeroAno";

	private static final String PARAMETRO_MES = "psNumeroMes";

	private static final String PARAMETRO_CODIGO_UNIDADE_NEGOCIO = "psCodigoUnidade";

	private static final String PARAMETRO_SITUACAO_RELATORIO = "psSituacaoRelatorio";

	private static final String PARAMETRO_IND_TOTAL = "psIndTotal";

	/**
	 * M�todo para converter os par�metros recebidos da tela.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 02/04/2012
	 * @param parametros
	 */
	public Map<String, Object> converter(Map<String, Object> parametros){

		Map<String, Object> parametrosConvertidos = new HashMap<String, Object>();
		this.atribuirValorSituacaoRelatorio(parametros);
		this.separarAnoMes(parametros);
		this.atribuirValorGerenciaRegional(parametros);
		this.atribuirValorIndTotal(parametros);

		parametrosConvertidos.put(PARAMETRO_RELATORIO_TIPO, parametros.get(PARAMETRO_RELATORIO_TIPO));
		parametrosConvertidos.put(PARAMETRO_ANO, parametros.get(PARAMETRO_ANO));
		parametrosConvertidos.put(PARAMETRO_MES, parametros.get(PARAMETRO_MES));
		parametrosConvertidos.put(PARAMETRO_CODIGO_GERENCIA, parametros.get(PARAMETRO_CODIGO_GERENCIA));
		parametrosConvertidos.put(PARAMETRO_CODIGO_LOCALIDADE, parametros.get(PARAMETRO_CODIGO_LOCALIDADE));
		parametrosConvertidos.put(PARAMETRO_CODIGO_UNIDADE_NEGOCIO, parametros.get(PARAMETRO_CODIGO_UNIDADE_NEGOCIO));
		parametrosConvertidos.put(PARAMETRO_OPCAO_TOTALIZACAO, parametros.get(PARAMETRO_OPCAO_TOTALIZACAO));
		parametrosConvertidos.put(PARAMETRO_SITUACAO_RELATORIO, parametros.get(PARAMETRO_SITUACAO_RELATORIO));
		parametrosConvertidos.put(PARAMETRO_IND_TOTAL, parametros.get(PARAMETRO_IND_TOTAL));

		return parametrosConvertidos;
	}

	private void atribuirValorIndTotal(Map<String, Object> parametros){

		String indTotal = String.valueOf(parametros.get(PARAMETRO_IND_TOTAL));
		indTotal = "n";
		parametros.put(PARAMETRO_IND_TOTAL, indTotal);
	}

	/**
	 * M�todo que valida os par�metros da tela.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 02/04/2012
	 * @param parametros
	 */
	public Map<String, String> validar(Map<String, Object> parametros){

		Map<String, String> erros = new HashMap<String, String>();

		this.validarAnoMesFaturamento(parametros, erros);
		this.validarOpcaoTotalizacao(parametros, erros);
		this.validarCodigoGerencia(parametros, erros);
		this.validarCodigoLocalidade(parametros, erros);
		this.validarUnidadeNegocio(parametros, erros);
		this.validarSituacaoRelatorio(parametros, erros);

		return erros;
	}

	/**
	 * M�todo que valida a situa��o do relat�rio.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 03/04/2012
	 * @param parametros
	 * @param erros
	 * @return
	 */
	private Map<String, String> validarSituacaoRelatorio(Map<String, Object> parametros, Map<String, String> erros){

		if(parametros.containsKey(PARAMETRO_SITUACAO_RELATORIO)){
			String situacaoRelatorio = String.valueOf(parametros.get(PARAMETRO_SITUACAO_RELATORIO));
			if(situacaoRelatorio == null || situacaoRelatorio.equals("") || situacaoRelatorio.length() == 0) erros.put(
							PARAMETRO_SITUACAO_RELATORIO, "Coloque uma situa��o do relat�rio, contate o administrador do sistema.");
		}

		return erros;
	}

	/**
	 * M�todo que atribui valor a situa��o do relat�rio.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 03/04/2012
	 * @param parametros
	 */
	private void atribuirValorSituacaoRelatorio(Map<String, Object> parametros){

		Integer anoMesInteger = null;
		String anoMes = null;
		if(parametros.containsKey(PARAMETRO_ANO_MES)){
			anoMes = Util.formatarMesAnoParaAnoMesSemBarra((String) parametros.get(PARAMETRO_ANO_MES));
			if(anoMes != null){
				anoMesInteger = Util.converterStringParaInteger(anoMes);
				Fachada facade = Fachada.getInstancia();
				// Busco o ano e m�s de refer�ncia da tabela sistema par�metro.
				SistemaParametro sistemaParametro = facade.pesquisarParametrosDoSistema();
				if(sistemaParametro != null && sistemaParametro.getAnoMesArrecadacao() != null
								&& sistemaParametro.getAnoMesArrecadacao().intValue() > 0){
					// Comparo os anos e meses e seto o valor a situa��o do relat�rio.
					if(anoMesInteger.intValue() < sistemaParametro.getAnoMesArrecadacao().intValue()) parametros.put(
									PARAMETRO_SITUACAO_RELATORIO, "DEFINITIVO");
					else if(anoMesInteger.intValue() >= sistemaParametro.getAnoMesArrecadacao().intValue()
									|| anoMesInteger.equals(sistemaParametro.getAnoMesArrecadacao())) parametros.put(
									PARAMETRO_SITUACAO_RELATORIO, "PARCIAL");
				}
			}
		}
	}

	/**
	 * M�todo que valida a unidade de neg�cio.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 02/04/2012
	 * @param parametros
	 * @return
	 */
	private Map<String, String> validarUnidadeNegocio(Map<String, Object> parametros, Map<String, String> erros){

		if(parametros.containsKey(PARAMETRO_OPCAO_TOTALIZACAO) && parametros.containsKey(PARAMETRO_CODIGO_UNIDADE_NEGOCIO)){
			String opcaoTotalizacao = String.valueOf(parametros.get(PARAMETRO_OPCAO_TOTALIZACAO));
			String codigoUnidadeNegocio = String.valueOf(parametros.get(PARAMETRO_CODIGO_UNIDADE_NEGOCIO));

			if((opcaoTotalizacao != null && opcaoTotalizacao.equals("U"))
							&& (codigoUnidadeNegocio == null || codigoUnidadeNegocio.equals("-1"))){
				erros.put(PARAMETRO_CODIGO_UNIDADE_NEGOCIO, "Selecione um tipo de Unidade de Neg�cio");
			}
			if(codigoUnidadeNegocio == null || codigoUnidadeNegocio.equals("-1")){
				codigoUnidadeNegocio = "0";
				parametros.put(PARAMETRO_CODIGO_UNIDADE_NEGOCIO, codigoUnidadeNegocio);
			}
		}

		return erros;
	}

	/**
	 * M�todo que valida o ano e m�s de faturamento.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 02/04/2012
	 * @param parametros
	 * @return
	 */
	private Map<String, String> validarAnoMesFaturamento(Map<String, Object> parametros, Map<String, String> erros){

		if(parametros.containsKey(PARAMETRO_OPCAO_TOTALIZACAO) && parametros.containsKey(PARAMETRO_ANO)
						&& parametros.containsKey(PARAMETRO_MES)){
			String anoMes = String.valueOf(parametros.get(PARAMETRO_ANO)) + String.valueOf(parametros.get(PARAMETRO_MES));

			if(anoMes == null || Util.isVazioOuBranco(anoMes)){
				erros.put(PARAMETRO_ANO, "Digite um m�s ou ano");
			}
			if(!Util.validarStringNumerica(anoMes) && !Util.validarNumeroMaiorQueZERO(anoMes)){
				erros.put(PARAMETRO_ANO, "M�s e ano devem ser inteiros e positivos");
			}
			if(Util.validarAnoMesSemBarra(anoMes)){
				erros.put(PARAMETRO_ANO, "M�s e ano inv�lidos");
			}
		}

		return erros;
	}

	/**
	 * M�todo que valida o c�digo de ger�ncia.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 02/04/2012
	 * @param parametros
	 * @return
	 */
	private Map<String, String> validarCodigoGerencia(Map<String, Object> parametros, Map<String, String> erros){

		if(parametros.containsKey(PARAMETRO_OPCAO_TOTALIZACAO) && parametros.containsKey(PARAMETRO_CODIGO_GERENCIA)){
			String opcaoTotalizacao = String.valueOf(parametros.get(PARAMETRO_OPCAO_TOTALIZACAO));
			String codigoGerencia = String.valueOf(parametros.get(PARAMETRO_CODIGO_GERENCIA));

			if((opcaoTotalizacao != null && (opcaoTotalizacao.equals("G") || opcaoTotalizacao.equals("GL")))
							&& (codigoGerencia == null || codigoGerencia.equals("-1"))){
				erros.put(PARAMETRO_CODIGO_GERENCIA, "Selecione um tipo de Ger�ncia");
			}
			if(codigoGerencia == null || codigoGerencia.equals("-1")){
				codigoGerencia = "0";
				parametros.put(PARAMETRO_CODIGO_GERENCIA, codigoGerencia);
			}
		}

		return erros;
	}

	/**
	 * M�todo que valida a op��o de totaliza��o.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 02/04/2012
	 * @param parametros
	 * @return
	 */
	private Map<String, String> validarOpcaoTotalizacao(Map<String, Object> parametros, Map<String, String> erros){

		if(parametros.containsKey(PARAMETRO_OPCAO_TOTALIZACAO)){
			String opcaoTotalizacao = String.valueOf(parametros.get(PARAMETRO_OPCAO_TOTALIZACAO));
			if(opcaoTotalizacao == null || Util.isVazioOuBranco(opcaoTotalizacao)){
				erros.put(PARAMETRO_OPCAO_TOTALIZACAO, "Selecione uma op��o de totaliza��o");
			}
		}

		return erros;
	}

	/**
	 * M�todo que valida o c�digo da localidade.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 02/04/2012
	 * @param parametros
	 * @return
	 */
	private Map<String, String> validarCodigoLocalidade(Map<String, Object> parametros, Map<String, String> erros){

		if(parametros.containsKey(PARAMETRO_OPCAO_TOTALIZACAO) && parametros.containsKey(PARAMETRO_CODIGO_LOCALIDADE)){
			String opcaoTotalizacao = String.valueOf(parametros.get(PARAMETRO_OPCAO_TOTALIZACAO));
			String codigoLocalidade = String.valueOf(parametros.get(PARAMETRO_CODIGO_LOCALIDADE));

			if((opcaoTotalizacao != null && opcaoTotalizacao.equals("L"))
							&& (codigoLocalidade == null || Util.isVazioOuBranco(codigoLocalidade))){
				erros.put(PARAMETRO_CODIGO_LOCALIDADE, "Digite uma Localidade");
			}
			if((opcaoTotalizacao != null && opcaoTotalizacao.equals("L")) && !Util.validarStringNumerica(codigoLocalidade)
							&& !Util.validarNumeroMaiorQueZERO(codigoLocalidade)){
				erros.put(PARAMETRO_CODIGO_LOCALIDADE, "C�digo de Localidade deve ser Inteiro e positivo");
			}
			if(codigoLocalidade == null || codigoLocalidade.equals("")){
				codigoLocalidade = "0";
				parametros.put(PARAMETRO_CODIGO_LOCALIDADE, codigoLocalidade);
			}
		}

		return erros;
	}

	/**
	 * M�todo que atribui os valores ao C�digo de Ger�ncia dependendo da op��o de ger�ncia
	 * selecionada.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 02/04/2012
	 * @param parametros
	 */
	private void atribuirValorGerenciaRegional(Map<String, Object> parametros){

		// Inicializando vari�veis
		String opcaoTotalizacao = null;
		String gerenciaRegionalLocalidade = null;
		String gerenciaRegional = null;
		if(parametros.containsKey(PARAMETRO_OPCAO_TOTALIZACAO) && parametros.containsKey(PARAMETRO_GERENCIA_REGIONAL_LOCALIDADE)
						&& parametros.containsKey(PARAMETRO_GERENCIA_REGIONAL)){
			// Passando os dados da tela para vari�veis
			opcaoTotalizacao = String.valueOf(parametros.get(PARAMETRO_OPCAO_TOTALIZACAO));
			gerenciaRegionalLocalidade = String.valueOf(parametros.get(PARAMETRO_GERENCIA_REGIONAL_LOCALIDADE));
			gerenciaRegional = String.valueOf(parametros.get(PARAMETRO_GERENCIA_REGIONAL));

			// Atribuindo os valores ao parametro de codigo ger�ncia dependendo da op��o selecionada
			if(opcaoTotalizacao != null && opcaoTotalizacao.equals("G")){
				parametros.put(PARAMETRO_CODIGO_GERENCIA, gerenciaRegional);
			}
			if(opcaoTotalizacao != null && opcaoTotalizacao.equals("GL")){
				parametros.put(PARAMETRO_CODIGO_GERENCIA, gerenciaRegionalLocalidade);
			}
			if(opcaoTotalizacao != null && !opcaoTotalizacao.equals("GL") && !opcaoTotalizacao.equals("G")){
				parametros.put(PARAMETRO_CODIGO_GERENCIA, "-1");
			}
		}

	}

	/**
	 * M�todo que separa o ano e m�s para os par�metros do crystal.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 02/04/2012
	 * @param parametros
	 */
	private void separarAnoMes(Map<String, Object> parametros){

		// Formata mes ano para ano e mes
		String anoMes = Util.formatarMesAnoParaAnoMesSemBarra((String) parametros.get(PARAMETRO_ANO_MES));

		// Separa os valores de ano e mes
		String ano = anoMes.substring(0, 4);
		String mes = anoMes.substring(4, 6);

		// Atribui aos par�metros
		parametros.put(PARAMETRO_ANO, ano);
		parametros.put(PARAMETRO_MES, mes);
	}
}
