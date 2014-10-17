
package br.com.procenge.parametrosistema.api;

import br.com.procenge.comum.dominio.api.EntidadeDominio;

public interface TipoParametroSistema
				extends EntidadeDominio {

	int TIPO_ESTATICO = 1;

	String DESCRICAO_TIPO_ESTATICO = "Estático";

	int TIPO_DINAMICO = 2;

	String DESCRICAO_TIPO_DINAMICO = "Dinâmico";

}