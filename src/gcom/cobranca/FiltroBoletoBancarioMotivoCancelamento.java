
package gcom.cobranca;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

public class FiltroBoletoBancarioMotivoCancelamento
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroCobrancaBoletoBancarioMotivoCancelamento object
	 */
	public FiltroBoletoBancarioMotivoCancelamento() {

	}

	/**
	 * Constructor for the FiltroCobrancaBoletoBancarioMotivoCancelamento object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroBoletoBancarioMotivoCancelamento(String campoOrderBy) {

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
