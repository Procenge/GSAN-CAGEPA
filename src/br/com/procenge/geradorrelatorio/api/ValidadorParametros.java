
package br.com.procenge.geradorrelatorio.api;

import java.util.Map;

public interface ValidadorParametros {

	Map<String, String> validar(Map<String, Object> parametros);

	Map<String, Object> converter(Map<String, Object> parametros);
}
