/**
 * 
 */

package gcom.cadastro.aguaparatodos;

import gcom.util.filtro.Filtro;

/**
 * @author Bruno Ferreira dos Santos
 */
public class FiltroImovelAguaParaTodos
				extends Filtro {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public FiltroImovelAguaParaTodos() {

	}

	/**
	 * Constructor for the FiltroImovelAguaParaTodos object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroImovelAguaParaTodos(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Id
	 */
	public final static String ID = "id";

	public final static String ID_IMOVEL = "idImovel";

	public final static String ID_CONTRIBUINTE = "idContribuinte";

	public final static String CODIGO_SITUACAO = "codigoSituacao";

	public final static String DATA_HABILITACAO = "dataHabilitacao";

}
