
package gcom.util.parametrizacao.webservice;

import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.parametrizacao.Parametro;

import org.apache.log4j.Logger;

public class ParametrosAgenciaVirtual
				extends Parametro {

	private static final Logger LOGGER = Logger.getLogger(ParametrosAgenciaVirtual.class);

	public static final Parametro P_AV_MEIO_SOLICITACAO = new ParametrosAgenciaVirtual("P_AV_MEIO_SOLICITACAO");

	public static final Parametro P_AV_UNIDADE_ORGANIZACIONAL_ABERTURA = new ParametrosAgenciaVirtual(
					"P_AV_UNIDADE_ORGANIZACIONAL_ABERTURA");

	public static final Parametro P_AV_UNIDADE_ORGANIZACIONAL_DESTINO = new ParametrosAgenciaVirtual("P_AV_UNIDADE_ORGANIZACIONAL_DESTINO");

	public static final Parametro P_AV_USUARIO_ABERTURA = new ParametrosAgenciaVirtual("P_AV_USUARIO_ABERTURA");

	public static final Parametro P_AV_VALIDA_CPF_CNPJ = new ParametrosAgenciaVirtual("P_AV_VALIDA_CPF_CNPJ");

	public static final Parametro P_AV_CONTA_MOTIVO_REVISAO_NAO_EXIBIR_CONTA = new ParametrosAgenciaVirtual(
					"P_AV_CONTA_MOTIVO_REVISAO_NAO_EXIBIR_CONTA");

	public static final Parametro P_AV_CONTA_REVISAO_NAO_EXIBIR = new ParametrosAgenciaVirtual("P_AV_CONTA_REVISAO_NAO_EXIBIR");

	public static final Parametro P_AV_CONTA_MOTIVO_REVISAO_NAO_EMITIR_2_VIA = new ParametrosAgenciaVirtual(
					"P_AV_CONTA_MOTIVO_REVISAO_NAO_EMITIR_2_VIA");

	public static final Parametro P_AV_EMITE_2VIA_CTA_REVISAO = new ParametrosAgenciaVirtual("P_AV_EMITE_2VIA_CTA_REVISAO");

	public static final Parametro P_INDICADOR_MSG_2VIA_CONTA_AGENCIA_VIRTUAL = new ParametrosAgenciaVirtual(
					"P_INDICADOR_MSG_2VIA_CONTA_AGENCIA_VIRTUAL");
	
	
	public static final Parametro P_AV_DIVIDA_ATIVA = new ParametrosAgenciaVirtual("P_AV_DIVIDA_ATIVA");

	protected ParametrosAgenciaVirtual(String codigo) {

		super(codigo);
	}

	public static boolean isCpfCnpjObrigatorio(){

		boolean valorRetorno = false;
		try{
			valorRetorno = ConstantesSistema.SIM.equals(Short.valueOf(P_AV_VALIDA_CPF_CNPJ.executar()));
		}catch(NumberFormatException e){
			LOGGER.error("Erro ao recuperar Parametro 'P_AV_VALIDA_CPF_CNPJ'", e);
			throw new RuntimeException(e);
		}catch(ControladorException e){
			LOGGER.error("Erro ao recuperar Parametro 'P_AV_VALIDA_CPF_CNPJ'", e);
			throw new RuntimeException(e);
		}
		return valorRetorno;
	}

	public static boolean isDividaAtiva(){

		boolean valorRetorno = false;
		try{
			valorRetorno = ConstantesSistema.SIM.equals(Short.valueOf(P_AV_DIVIDA_ATIVA.executar()));

		}catch(NumberFormatException e){
			LOGGER.error("Erro ao recuperar Parametro 'P_AV_DIVIDA_ATIVA'", e);
			throw new RuntimeException(e);
		}catch(ControladorException e){
			LOGGER.error("Erro ao recuperar Parametro 'P_AV_DIVIDA_ATIVA'", e);
			throw new RuntimeException(e);
		}

		valorRetorno = true;

		return valorRetorno;
	}

}
