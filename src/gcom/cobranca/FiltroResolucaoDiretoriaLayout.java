
package gcom.cobranca;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

public class FiltroResolucaoDiretoriaLayout
				extends Filtro

				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroResolucaoDiretoriaLayout object
	 */
	public FiltroResolucaoDiretoriaLayout() {

	}

	/**
	 * Constructor for the FiltroResolucaoDiretoriaLayout object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroResolucaoDiretoriaLayout(String campoOrderBy) {

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

	public final static String INDICADOR_PADRAO = "indicadorPadrao";

}
