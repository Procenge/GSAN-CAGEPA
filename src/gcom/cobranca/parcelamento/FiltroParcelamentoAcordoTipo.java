
package gcom.cobranca.parcelamento;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

public class FiltroParcelamentoAcordoTipo
				extends Filtro

				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroParcelamentoAcordoTipo object
	 */
	public FiltroParcelamentoAcordoTipo() {

	}

	/**
	 * Constructor for the FiltroResolucaoDiretoriaLayout object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroParcelamentoAcordoTipo(String campoOrderBy) {

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

	public final static String INDICADOR_PARCELAMETO_NORMAL = "indicadorParcelamentoNormal";

}
