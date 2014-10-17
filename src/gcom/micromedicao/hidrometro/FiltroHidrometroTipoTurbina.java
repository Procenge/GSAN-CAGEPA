
package gcom.micromedicao.hidrometro;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * Filtro - Tipos de Turbina de Hidrômetro
 * 
 * @author Hebert Falcão
 * @date 21/04/2012
 */
public class FiltroHidrometroTipoTurbina
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public FiltroHidrometroTipoTurbina(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public FiltroHidrometroTipoTurbina() {

	}

	public final static String ID = "id";

	public final static String CODIGO = "codigo";

	public final static String DESCRICAO = "descricao";

	public final static String DESCRICAOABREVIADA = "descricaoAbreviada";

	public final static String INDICADOR_USO = "indicadorUso";

}
