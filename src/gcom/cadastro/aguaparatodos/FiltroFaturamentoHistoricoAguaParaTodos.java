/**
 * 
 */

package gcom.cadastro.aguaparatodos;

import gcom.util.filtro.Filtro;

/**
 * @author Bruno Ferreira dos Santos
 */
public class FiltroFaturamentoHistoricoAguaParaTodos
				extends Filtro {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public FiltroFaturamentoHistoricoAguaParaTodos() {

		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor for the FiltroImovelAguaParaTodos object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroFaturamentoHistoricoAguaParaTodos(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Id
	 */
	public final static String ID = "id";

	public final static String IMOVEL_ID = "imovel.id";

	/**
	 * Ano/mes referencia
	 */
	public final static String ANO_MES_REFERENCIA = "anoMesReferencia";

	public final static String DATA_HABILITACAO = "dataHabilitacao";

}
