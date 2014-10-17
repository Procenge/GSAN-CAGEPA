
package gcom.arrecadacao;

import gcom.util.filtro.Filtro;

public class FiltroAvisoInteligest
				extends Filtro {

	private static final long serialVersionUID = 1L;

	public final static String ID = "comp_id";

	public final static String ANO = "comp_id.ano";

	public final static String NUMERO_AVISO = "comp_id.numeroAviso";

	public final static String CODIGO_TRIBUTO = "comp_id.codigoTributo";

	public final static String NUMERO_LIGACAO = "numeroLigacao";

	public final static String NUMERO_SEQUENCIAL = "numeroSequencial";

	public FiltroAvisoInteligest() {

	}

	public FiltroAvisoInteligest(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

}
