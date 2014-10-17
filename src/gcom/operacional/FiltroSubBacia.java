
package gcom.operacional;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

public class FiltroSubBacia
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroSubBacia object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroSubBacia(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Construtor da classe FiltroQuadra
	 */
	public FiltroSubBacia() {

	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	/**
	 * Description of the Field
	 */
	public final static String BACIA_ID = "bacia.id";

	/**
	 * Description of the Field
	 */
	public final static String BACIA = "bacia";

	/**
	 * Description of the Field
	 */
	public final static String SUBSISTEMA_ESGOTO_ID = "bacia.subsistemaEsgoto.id";

	/**
	 * Description of the Field
	 */
	public final static String SISTEMA_ESGOTO_ID = "bacia.subsistemaEsgoto.sistemaEsgoto.id";

	/**
	 * Description of the Field
	 */
	public final static String INDICADOR_USO = "indicadorUso";

	/**
	 * Description of the Field
	 */
	public static final String CODIGO = "codigo";

	/**
	 * Description of the Field
	 */
	public static final String DESCRICAO = "descricao";

	/**
	 * Description of the Field
	 */
	public static final String DIAMETRO = "diametro";

	/**
	 * Description of the Field
	 */
	public static final String EXTENSAO = "extensao";

	/**
	 * Description of the Field
	 */
	public final static String MATERIAL_REDE_ESGOTO = "materialRedeEsgoto";

	/**
	 * Description of the Field
	 */
	public final static String MATERIAL_REDE_ESGOTO_ID = "materialRedeEsgoto.id";

}
