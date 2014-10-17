
package gcom.arrecadacao.debitoautomatico;

import gcom.util.filtro.Filtro;

public class FiltroDebitoAutomatico
				extends Filtro {

	public FiltroDebitoAutomatico() {

	}

	public FiltroDebitoAutomatico(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;

	}

	public final static String IMOVEL_ID = "imovel.id";

	public final static String DATA_EXCLUSAO = "dataExclusao";

	public final static String ID = "id";

	public final static String IMOVEL = "imovel";

	public final static String BANCO = "agencia.banco";

	public final static String AGENCIA = "agencia";

}
