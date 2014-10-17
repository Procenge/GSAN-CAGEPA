
package gcom.cadastro.unidade;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * Filtro da entidade UnidadeOrganizacionalHierarquia
 * 
 * @author Hebert Falcão
 * @date 08/04/2011
 */
public class FiltroUnidadeOrganizacionalHierarquia
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroUnidadeOrganizacional object
	 */
	public FiltroUnidadeOrganizacionalHierarquia() {

	}

	/**
	 * Constructor for the FiltroCliente object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroUnidadeOrganizacionalHierarquia(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String ID = "id";

	public final static String UNIDADE_ID = "unidade.id";

	public final static String UNIDADE = "unidade";

	public final static String UNIDADE_SUPERIOR_ID = "unidadeSuperior.id";

	public final static String UNIDADE_SUPERIOR = "unidadeSuperior";

}
