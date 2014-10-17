
package gcom.atendimentopublico.ordemservico;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

public class FiltroOsSeletivaComandoLeituraAnormalidade
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroOrdemServico object
	 */
	public FiltroOsSeletivaComandoLeituraAnormalidade() {

	}

	/**
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroOsSeletivaComandoLeituraAnormalidade(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

}
