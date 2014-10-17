
package br.com.procenge.geradorrelatorio.impl.validador;

import gcom.util.Util;

import java.util.HashMap;
import java.util.Map;

import br.com.procenge.geradorrelatorio.api.ValidadorParametros;

/**
 * Classe que irá converter e validar os parâmetros para o relatório de Débito por Responsável.
 * 
 * @author Ricardo Rodrigues
 * @date 13/04/2012
 */
public class DebitoResponsavelValidadorImpl
				implements ValidadorParametros {

	public static final String P_CLIENTE_INICIAL = "P_CLI_INI";

	public static final String P_CLIENTE_FINAL = "P_CLI_FIN";

	public static final String P_MOVITO_REVISAO = "P_CD_MOT_REV";

	public static final String P_TIPO_CONTAS_REVISAO = "P_TP_CTA_REV";

	public static final String P_REFERENCIA_INICIAL = "P_AM_REF_INI";

	public static final String P_REFERENCIA_FINAL = "P_AM_REF_FIN";

	public static final String P_TIPO_CLIENTE_INICIAL = "P_TP_CLI_INI";

	public static final String P_TIPO_CLIENTE_FINAL = "P_TP_CLI_FIN";

	public static final String P_TIPO_RELATORIO = "P_TP_RELATORIO";

	public static final String P_RESPONSABILIDADE = "P_TP_RESP";

	/**
	 * Método que converte os parâmetros para o rpt.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 13/04/2012
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

		this.converterAnoMesInicialFinal(parametros);
		String anoMesInicial = String.valueOf(String.valueOf(parametros.get(P_REFERENCIA_INICIAL)));
		String anoMesFinal = String.valueOf(String.valueOf(parametros.get(P_REFERENCIA_FINAL)));
		if(anoMesInicial != null || anoMesFinal != null){
			Util
							.atribuirValoresInicialFinalOuFinalInicial(anoMesInicial, anoMesFinal, P_REFERENCIA_INICIAL,
											P_REFERENCIA_FINAL, parametros);
		}

		this.atribuirValoresDefaults(parametros);

		parametrosConvertidos.put(P_CLIENTE_FINAL, parametros.get(P_CLIENTE_FINAL));
		parametrosConvertidos.put(P_CLIENTE_INICIAL, parametros.get(P_CLIENTE_INICIAL));
		parametrosConvertidos.put(P_MOVITO_REVISAO, parametros.get(P_MOVITO_REVISAO));
		parametrosConvertidos.put(P_REFERENCIA_FINAL, parametros.get(P_REFERENCIA_FINAL));
		parametrosConvertidos.put(P_REFERENCIA_INICIAL, parametros.get(P_REFERENCIA_INICIAL));
		parametrosConvertidos.put(P_RESPONSABILIDADE, parametros.get(P_RESPONSABILIDADE));
		parametrosConvertidos.put(P_TIPO_CLIENTE_FINAL, parametros.get(P_TIPO_CLIENTE_FINAL));
		parametrosConvertidos.put(P_TIPO_CLIENTE_INICIAL, parametros.get(P_TIPO_CLIENTE_INICIAL));
		parametrosConvertidos.put(P_TIPO_CONTAS_REVISAO, parametros.get(P_TIPO_CONTAS_REVISAO));
		parametrosConvertidos.put(P_TIPO_RELATORIO, parametros.get(P_TIPO_RELATORIO));

		return parametrosConvertidos;
	}

	/**
	 * Método que valida os parâmetros para o rpt.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 13/04/2012
	 */
	public Map<String, String> validar(Map<String, Object> parametros){

		Map<String, String> erros = new HashMap<String, String>();

		String anoMesInicial = String.valueOf(String.valueOf(parametros.get(P_REFERENCIA_INICIAL)));
		String anoMesFinal = String.valueOf(String.valueOf(parametros.get(P_REFERENCIA_FINAL)));

		this.validarClientes(parametros, erros);
		this.validarTipoClientes(parametros, erros);
		if(!anoMesFinal.equals("0") || !anoMesInicial.equals("0")){
			this.validarAnoMeses(parametros, erros);
		}
		this.validarMotivoRevisao(parametros, erros);

		return erros;
	}

	/**
	 * Método que valida o motivo de revisão.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 17/04/2012
	 * @param parametros
	 * @param erros
	 * @return
	 */
	private Map<String, String> validarMotivoRevisao(Map<String, Object> parametros, Map<String, String> erros){

		if(parametros.containsKey(P_TIPO_CONTAS_REVISAO) && parametros.containsKey(P_MOVITO_REVISAO)){
			String tipoContaRevisao = String.valueOf(parametros.get(P_TIPO_CONTAS_REVISAO));
			String motivoRevisao = String.valueOf(parametros.get(P_MOVITO_REVISAO));

			if(tipoContaRevisao.equals("S") && motivoRevisao.equals("-1")){
				erros.put(P_MOVITO_REVISAO, "Selecione um motivo da revisão");
			}
		}

		return erros;
	}

	/**
	 * Método que atribui valores defaults para os parâmetros.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 17/04/2012
	 * @param parametros
	 */
	private void atribuirValoresDefaults(Map<String, Object> parametros){

		String tipoRelatorio = parametros.get(P_TIPO_RELATORIO).toString();
		String tipoContasRevisao = parametros.get(P_TIPO_CONTAS_REVISAO).toString();
		String responsabilidade = parametros.get(P_RESPONSABILIDADE).toString();
		String anoMesInicial = String.valueOf(String.valueOf(parametros.get(P_REFERENCIA_INICIAL)));
		String anoMesFinal = String.valueOf(String.valueOf(parametros.get(P_REFERENCIA_FINAL)));
		String tipoClienteInicial = parametros.get(P_TIPO_CLIENTE_INICIAL).toString();
		String tipoClienteFinal = parametros.get(P_TIPO_CLIENTE_FINAL).toString();
		String clienteInicial = parametros.get(P_CLIENTE_INICIAL).toString();
		String clienteFinal = parametros.get(P_CLIENTE_FINAL).toString();
		String motivoContaRevisao = parametros.get(P_MOVITO_REVISAO).toString();

		if(Util.isVazioOuBranco(tipoRelatorio)){
			tipoRelatorio = "A";
			parametros.put(P_TIPO_RELATORIO, tipoRelatorio);
		}
		if(Util.isVazioOuBranco(tipoContasRevisao)){
			tipoContasRevisao = "N";
			parametros.put(P_TIPO_CONTAS_REVISAO, tipoContasRevisao);
		}
		if(Util.isVazioOuBranco(responsabilidade)){
			responsabilidade = "D";
			parametros.put(P_RESPONSABILIDADE, responsabilidade);
		}
		if(Util.isVazioOuBranco(anoMesInicial) && Util.isVazioOuBranco(anoMesFinal)){
			anoMesInicial = "0";
			anoMesFinal = "0";
			parametros.put(P_REFERENCIA_FINAL, anoMesFinal);
			parametros.put(P_REFERENCIA_INICIAL, anoMesInicial);
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
		if(tipoContasRevisao.equals("N")){
			if((Util.isVazioOuBranco(motivoContaRevisao)) || (motivoContaRevisao.equals("-1"))){
				motivoContaRevisao = "0";
				parametros.put(P_MOVITO_REVISAO, motivoContaRevisao);
			}
		}

	}

	/**
	 * Método que valida os anos e meses.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 17/04/2012
	 * @param parametros
	 * @param erros
	 * @return
	 */
	private Map<String, String> validarAnoMeses(Map<String, Object> parametros, Map<String, String> erros){

		if(parametros.containsKey(P_REFERENCIA_FINAL) && parametros.containsKey(P_REFERENCIA_INICIAL)){
			String anoMesFinal = String.valueOf(parametros.get(P_REFERENCIA_FINAL));
			String anoMesInicial = String.valueOf(parametros.get(P_REFERENCIA_INICIAL));

			if(!Util.validarStringNumerica(anoMesFinal) && !Util.validarNumeroMaiorQueZERO(anoMesFinal)){
				erros.put(P_REFERENCIA_FINAL, "Mês e ano final devem ser inteiros e positivos");
			}
			if(!Util.validarStringNumerica(anoMesInicial) && !Util.validarNumeroMaiorQueZERO(anoMesInicial)){
				erros.put(P_REFERENCIA_INICIAL, "Mês e ano inicial devem ser inteiros e positivos");
			}
			if(Util.validarAnoMesSemBarra(anoMesFinal)){
				erros.put(P_REFERENCIA_FINAL, "Mês e ano final inválido");
			}
			if(Util.validarAnoMesSemBarra(anoMesInicial)){
				erros.put(P_REFERENCIA_INICIAL, "Mês e ano inicial inválido");
			}
			if(anoMesInicial != null && anoMesFinal != null){
				boolean valido = Util.compararAnoMesReferencia(anoMesInicial, anoMesFinal, ">");
				if(valido){
					erros.put(P_REFERENCIA_INICIAL, "O ano e mês inicial não pode ser maior que o ano e mês final");
				}
			}

		}

		return erros;
	}

	/**
	 * Método que valida os tipos de clientes.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 13/04/2012
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
	 * @author Ricardo Rodrigues
	 * @date 13/04/2012
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

	/**
	 * Método que converte os anos e meses inicial e final.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 17/04/2012
	 * @param parametros
	 */
	private void converterAnoMesInicialFinal(Map<String, Object> parametros){

		if(parametros.containsKey(P_REFERENCIA_INICIAL) && parametros.containsKey(P_REFERENCIA_FINAL)){
			String pReferenciaFinal = String.valueOf(parametros.get(P_REFERENCIA_FINAL));
			String pReferenciaInicial = String.valueOf(parametros.get(P_REFERENCIA_INICIAL));
			if(pReferenciaFinal != null && !Util.isVazioOuBranco(pReferenciaFinal)){
				pReferenciaFinal = Util.formatarMesAnoParaAnoMesSemBarra(pReferenciaFinal);

				parametros.put(P_REFERENCIA_FINAL, pReferenciaFinal);
			}
			if(pReferenciaInicial != null && !Util.isVazioOuBranco(pReferenciaInicial)){
				pReferenciaInicial = Util.formatarMesAnoParaAnoMesSemBarra(pReferenciaInicial);

				parametros.put(P_REFERENCIA_INICIAL, pReferenciaInicial);
			}
		}

	}

}
