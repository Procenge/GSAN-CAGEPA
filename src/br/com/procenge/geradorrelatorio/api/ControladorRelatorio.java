
package br.com.procenge.geradorrelatorio.api;

import java.io.ByteArrayInputStream;
import java.util.Map;

public interface ControladorRelatorio {

	String BEAN_ID = "controladorRelatorio";

	int FORMATO_PDF = 1;

	int FORMATO_PLANILHA = 2;

	int FORMATO_DOCUMENTO = 3;

	Relatorio obterRelatorioPorNome(String nome);

	Map<String, String> validarParametros(Relatorio relatorio, Map<String, Object> parametros);

	Map<String, Object> converterParametros(Relatorio relatorio, Map<String, Object> parametros);

	ByteArrayInputStream gerarRelatorio(Relatorio relatorio, Map<String, Object> parametros, int formato);
}
