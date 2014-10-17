
package gcom.cobranca;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

public class FiltroBoletoBancarioSituacao
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroBoletoBancario object
	 */
	public FiltroBoletoBancarioSituacao() {

	}

	/**
	 * Constructor for the FiltroBoletoBancario object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroBoletoBancarioSituacao(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	/**
	 * Description of the Field
	 */
	public final static String DESCRICAO = "descricao";

	public final static String INDICADOR_USO = "indicadorUso";
}
