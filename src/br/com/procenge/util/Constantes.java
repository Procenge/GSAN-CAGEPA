
package br.com.procenge.util;

import java.util.Locale;
import java.util.ResourceBundle;

public final class Constantes {

	/**
	 * RESOURCE_BUNDLE
	 */
	public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("gcom.properties.application");

	/**
	 * LOCALE_PT_BR
	 */
	public static final Locale LOCALE_PT_BR = new Locale("pt", "BR");

	/**
	 * FORMATO_DATA_BR
	 */
	public static final String FORMATO_DATA_BR = "dd/MM/yyyy";

	/**
	 * FORMATO_DATA_HORA_BR
	 */
	public static final String FORMATO_DATA_HORA_BR = "dd/MM/yyyy HH:mm:ss";

	/**
	 * FORMATO_DATA_HORA_US 2008-01-01 00:00:00
	 */
	public static final String FORMATO_DATA_HORA_US = "yyyy-MM-dd HH:mm:ss";

	/**
	 * FORMATO_DATA_BR MES/ANO
	 */
	public static final String FORMATO_DATA_BR_MES_ANO = "MM/yyyy";

	/**
	 * FORMATO_DATA_BR FORMATO_DATA_BR_MES_EXT_ANO/ANO
	 */
	public static final String FORMATO_DATA_BR_MES_EXT_ANO = "MMM/yyyy";

	/**
	 * FORMATO_HORA
	 */
	public static final String FORMATO_HORA = "HH:mm";

	/**
	 * EXPRESSAO_REGULAR_EMAIL
	 */
	public static final String EXPRESSAO_REGULAR_EMAIL = ".+@.+\\.[a-z-0-9]+";

	/**
	 * EXPRESSAO_REGULAR_NUMEROS
	 */
	public static final String EXPRESSAO_REGULAR_NUMEROS = "[0-9]*";

	/**
	 * EXPRESSAO_REGULAR_HORA
	 */
	public static final String EXPRESSAO_REGULAR_HORA = "[0-9\\u003a]*";

	/**
	 * ERRO_DUPLA_SUBMISSAO
	 */
	public static final String ERRO_DUPLA_SUBMISSAO = "ERRO_DUPLA_SUBMISSAO";

	/**
	 * ERRO_REG_INEXISTENTE
	 */
	public static final String ERRO_REG_INEXISTENTE = "ERRO_REG_INEXISTENTE";

	/**
	 * SUCESSO_ENTIDADE_ALTERADA
	 */
	public static final String SUCESSO_ENTIDADE_ALTERADA = "SUCESSO_ENTIDADE_ALTERADA";

	/**
	 * ENTIDADE_ROTULO_PARAMETRO_SISTEMA
	 */
	public static final String ENTIDADE_ROTULO_PARAMETRO_SISTEMA = "ENTIDADE_ROTULO_PARAMETRO_SISTEMA";

	/**
	 * STRING_VIRGULA_ESPACO
	 */
	public static final String STRING_VIRGULA_ESPACO = ", ";

	/**
	 * ERRO_NEGOCIO_CAMPOS_OBRIGATORIOS
	 */
	public static final String ERRO_NEGOCIO_CAMPOS_OBRIGATORIOS = "ERRO_NEGOCIO_CAMPOS_OBRIGATORIOS";

	/**
	 * PARAMETRO_SISTEMA_ROTULO_CODIGO
	 */
	public static final String PARAMETRO_SISTEMA_ROTULO_CODIGO = "PARAMETRO_SISTEMA_ROTULO_CODIGO";

	/**
	 * PARAMETRO_SISTEMA_ROTULO_VALOR
	 */
	public static final String PARAMETRO_SISTEMA_ROTULO_VALOR = "PARAMETRO_SISTEMA_ROTULO_VALOR";

	/**
	 * PARAMETRO_SISTEMA_ROTULO_DESCRICAO
	 */
	public static final String PARAMETRO_SISTEMA_ROTULO_DESCRICAO = "PARAMETRO_SISTEMA_ROTULO_DESCRICAO";

	/**
	 * PARAMETRO_SISTEMA_ROTULO_TIPO
	 */
	public static final String PARAMETRO_SISTEMA_ROTULO_TIPO = "PARAMETRO_SISTEMA_ROTULO_TIPO";

	/**
	 * PARAMETRO_SISTEMA_EMITIR_AVISO_CORTE_ARQUIVO_TXT
	 */
	public static final String PARAMETRO_SISTEMA_EMITIR_AVISO_CORTE_ARQUIVO_TXT = "emitirAvisoCorteArquivoTXT";

	/**
	 * PARAMETRO_SISTEMA_EMITIR_ORDEM_CORTE_ARQUIVO_TXT
	 */
	public static final String PARAMETRO_SISTEMA_EMITIR_ORDEM_CORTE_ARQUIVO_TXT = "emitirOrdemCorteArquivoTXT";

	/**
	 * ERRO_DADOS_INVALIDOS
	 */
	public static final String ERRO_DADOS_INVALIDOS = "ERRO_DADOS_INVALIDOS";

	/**
	 * ERRO_FORMATO_INVALIDO
	 */
	public static final String ERRO_FORMATO_INVALIDO = "ERRO_FORMATO_INVALIDO";

	/**
	 * ERRO_REG_DUPLICIDADE
	 */
	public static final String ERRO_REG_DUPLICIDADE = "ERRO_REG_DUPLICIDADE";

	/**
	 * SERVICO_ASSOCIADO_ROTULO_DATA_PROGRAMACAO
	 */
	public static final String SERVICO_ASSOCIADO_ROTULO_DATA_PROGRAMACAO = "SERVICO_ASSOCIADO_ROTULO_DATA_PROGRAMACAO";
}
