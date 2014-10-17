
package br.com.procenge.geradorrelatorio.impl.validador;

import gcom.util.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.procenge.geradorrelatorio.api.ValidadorParametros;

/**
 * Classe que irá converter e validar os parâmetros para o relatório de Débito por Responsável.
 * 
 * @author Carlos Chrystian
 * @date 16/09/2012
 */
public class TabulacaoPagamentosPorResponsavelValidadorImpl
				implements ValidadorParametros {

	public static final String P_CLIENTE_INICIAL = "P_CLI_INI";

	public static final String P_CLIENTE_FINAL = "P_CLI_FIN";

	public static final String P_DATA_REALIZACAO_INICIAL = "P_DT_REALIZACAO_INI";

	public static final String P_DATA_REALIZACAO_FINAL = "P_DT_REALIZACAO_FIN";

	public static final String P_TIPO_CLIENTE_INICIAL = "P_TP_CLI_INI";

	public static final String P_TIPO_CLIENTE_FINAL = "P_TP_CLI_FIN";

	/**
	 * Método que converte os parâmetros para o rpt.
	 * 
	 * @author Carlos Chrystian
	 * @date 16/09/2012
	 */
	public Map<String, Object> converter(Map<String, Object> parametros){

		Map<String, Object> parametrosConvertidos = new HashMap<String, Object>();
		String clienteInicial = parametros.get(P_CLIENTE_INICIAL).toString();
		String clienteFinal = parametros.get(P_CLIENTE_FINAL).toString();

		String tipoClienteInicial = parametros.get(P_TIPO_CLIENTE_INICIAL).toString();
		String tipoClienteFinal = parametros.get(P_TIPO_CLIENTE_FINAL).toString();

		if(clienteInicial != null || clienteFinal != null){
			Util.atribuirValoresInicialFinalOuFinalInicial(clienteInicial, clienteFinal, P_CLIENTE_INICIAL, P_CLIENTE_FINAL, parametros);
		}

		if(tipoClienteInicial != null || tipoClienteFinal != null){
			Util.atribuirValoresInicialFinalOuFinalInicial(tipoClienteInicial, tipoClienteFinal, P_TIPO_CLIENTE_INICIAL,
							P_TIPO_CLIENTE_FINAL, parametros);
		}

		String dataRealizacaoInicial = String.valueOf(parametros.get(P_DATA_REALIZACAO_INICIAL));
		String dataRealizacaoFinal = String.valueOf(parametros.get(P_DATA_REALIZACAO_FINAL));
		if(dataRealizacaoInicial != null || dataRealizacaoFinal != null){
			Util.atribuirValoresInicialFinalOuFinalInicial(dataRealizacaoInicial, dataRealizacaoFinal, P_DATA_REALIZACAO_INICIAL,
							P_DATA_REALIZACAO_FINAL, parametros);
		}

		this.atribuirValoresDefaults(parametros);

		parametrosConvertidos.put(P_CLIENTE_FINAL, Util.converterStringParaInteger(String.valueOf(parametros.get(P_CLIENTE_FINAL))));
		parametrosConvertidos.put(P_CLIENTE_INICIAL, Util.converterStringParaInteger(String.valueOf(parametros.get(P_CLIENTE_INICIAL))));
		parametrosConvertidos.put(P_DATA_REALIZACAO_FINAL, Util.formatarDataAAAAMMDD(Util.converteStringParaDate((String) parametros
						.get(P_DATA_REALIZACAO_FINAL))));
		parametrosConvertidos.put(P_DATA_REALIZACAO_INICIAL, Util.formatarDataAAAAMMDD(Util.converteStringParaDate((String) parametros
						.get(P_DATA_REALIZACAO_INICIAL))));
		parametrosConvertidos.put(P_TIPO_CLIENTE_FINAL, Util.converterStringParaInteger(String
						.valueOf(parametros.get(P_TIPO_CLIENTE_FINAL))));
		parametrosConvertidos.put(P_TIPO_CLIENTE_INICIAL, Util.converterStringParaInteger(String.valueOf(parametros
						.get(P_TIPO_CLIENTE_INICIAL))));

		return parametrosConvertidos;
	}

	/**
	 * Método que valida os parâmetros para o rpt.
	 * 
	 * @author Carlos Chrystian
	 * @date 16/09/2012
	 */
	public Map<String, String> validar(Map<String, Object> parametros){

		Map<String, String> erros = new HashMap<String, String>();

		String dataRealizacaoInicial = String.valueOf(String.valueOf(parametros.get(P_DATA_REALIZACAO_INICIAL)));
		String dataRealizacaoFinal = String.valueOf(String.valueOf(parametros.get(P_DATA_REALIZACAO_FINAL)));

		this.validarClientes(parametros, erros);
		this.validarTipoClientes(parametros, erros);
		if(!dataRealizacaoFinal.equals("0") || !dataRealizacaoInicial.equals("0")){
			this.validarPeriodoRealizacao(parametros, erros);
		}

		return erros;
	}

	/**
	 * Método que atribui valores defaults para os parâmetros.
	 * 
	 * @author Carlos Chrystian
	 * @date 16/09/2012
	 * @param parametros
	 */
	private void atribuirValoresDefaults(Map<String, Object> parametros){

		String dataRealizacaoInicial = String.valueOf(String.valueOf(parametros.get(P_DATA_REALIZACAO_INICIAL)));
		String dataRealizacaoFinal = String.valueOf(String.valueOf(parametros.get(P_DATA_REALIZACAO_FINAL)));
		String tipoClienteInicial = parametros.get(P_TIPO_CLIENTE_INICIAL).toString();
		String tipoClienteFinal = parametros.get(P_TIPO_CLIENTE_FINAL).toString();
		String clienteInicial = parametros.get(P_CLIENTE_INICIAL).toString();
		String clienteFinal = parametros.get(P_CLIENTE_FINAL).toString();

		if(Util.isVazioOuBranco(dataRealizacaoInicial) && Util.isVazioOuBranco(dataRealizacaoFinal)){
			dataRealizacaoInicial = "0";
			dataRealizacaoFinal = "0";
			parametros.put(P_DATA_REALIZACAO_FINAL, dataRealizacaoFinal);
			parametros.put(P_DATA_REALIZACAO_INICIAL, dataRealizacaoInicial);
		}
		if((Util.isVazioOuBranco(tipoClienteInicial) && Util.isVazioOuBranco(tipoClienteFinal))
						|| (tipoClienteFinal.equals("t") && tipoClienteInicial.equals("t"))){
			tipoClienteInicial = "0";
			tipoClienteFinal = "0";
			parametros.put(P_TIPO_CLIENTE_FINAL, tipoClienteFinal);
			parametros.put(P_TIPO_CLIENTE_INICIAL, tipoClienteInicial);
		}
		if((Util.isVazioOuBranco(clienteInicial) && Util.isVazioOuBranco(clienteFinal))
						|| (clienteFinal.equals("t") && clienteFinal.equals("t"))){
			clienteInicial = "0";
			clienteFinal = "0";
			parametros.put(P_CLIENTE_FINAL, clienteFinal);
			parametros.put(P_CLIENTE_INICIAL, clienteInicial);
		}
	}

	/**
	 * Método que valida o período de realização.
	 * 
	 * @author Carlos Chrystian
	 * @date 16/09/2012
	 * @param parametros
	 * @param erros
	 * @return
	 */
	private Map<String, String> validarPeriodoRealizacao(Map<String, Object> parametros, Map<String, String> erros){

		if(parametros.containsKey(P_DATA_REALIZACAO_FINAL) && parametros.containsKey(P_DATA_REALIZACAO_INICIAL)){
			Date dataRealizacaoFinal = Util.converteStringParaDate(String.valueOf(parametros.get(P_DATA_REALIZACAO_FINAL)));
			Date dataRealizacaoInicial = Util.converteStringParaDate(String.valueOf(parametros.get(P_DATA_REALIZACAO_INICIAL)));

			if(dataRealizacaoInicial != null && dataRealizacaoFinal != null){
				int comparacaoDatas = Util.compararData(dataRealizacaoInicial, dataRealizacaoFinal);
				if(comparacaoDatas == 1){
					erros.put(P_DATA_REALIZACAO_INICIAL, "Data inicial de realização não pode ser maior que a data final de realização.");
				}
			}
		}

		return erros;
	}

	/**
	 * Método que valida os tipos de clientes.
	 * 
	 * @author Carlos Chrystian
	 * @date 16/09/2012
	 * @param parametros
	 * @return
	 */
	private Map<String, String> validarTipoClientes(Map<String, Object> parametros, Map<String, String> erros){

		if(parametros.containsKey(P_TIPO_CLIENTE_FINAL) && parametros.containsKey(P_TIPO_CLIENTE_INICIAL)){
			String tipoClienteFinal = String.valueOf(parametros.get(P_TIPO_CLIENTE_FINAL));
			String tipoClienteInicial = String.valueOf(parametros.get(P_TIPO_CLIENTE_INICIAL));

			if(tipoClienteInicial != null && tipoClienteFinal != null){
				boolean valido = Util.compararInicialEFinal(tipoClienteInicial, tipoClienteFinal, ">");
				if(valido){
					erros.put(P_TIPO_CLIENTE_INICIAL, "O tipo de cliente inicial deve ser menor que o tipo de cliente final");
				}
			}

		}

		return erros;
	}

	/**
	 * Método que valida os clientes.
	 * 
	 * @author Carlos Chrystian
	 * @date 16/09/2012
	 * @param parametros
	 * @return
	 */
	private Map<String, String> validarClientes(Map<String, Object> parametros, Map<String, String> erros){

		if(parametros.containsKey(P_CLIENTE_FINAL) && parametros.containsKey(P_CLIENTE_INICIAL)){
			String clienteFinal = String.valueOf(parametros.get(P_CLIENTE_FINAL));
			String clienteInicial = String.valueOf(parametros.get(P_CLIENTE_INICIAL));

			if(clienteInicial != null && clienteFinal != null){
				boolean valido = Util.compararInicialEFinal(clienteInicial, clienteFinal, ">");
				if(valido){
					erros.put(P_CLIENTE_INICIAL, "O cliente inicial deve ser menor que o cliente final");
				}
			}

		}

		return erros;
	}

}
