
package gcom.cobranca;

import gcom.util.filtro.Filtro;

public class FiltroOrgaoExterno
				extends Filtro {

	private static final long serialVersionUID = 1L;

	public static final String ID = "id";

	public static final String DESCRICAO = "descricao";

	public static final String INDICADOR_USO = "indicadorUso";

	public FiltroOrgaoExterno() {

	}

	public FiltroOrgaoExterno(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

}
