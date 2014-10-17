
package gcom.cobranca;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * Filtro da entidade BoletoBancarioSituacaoHistorico
 * 
 * @author Hebert Falcão
 * @date 12/10/2011
 */
public class FiltroBoletoBancarioSituacaoHistorico
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroBoletoBancarioSituacaoHistorico object
	 */
	public FiltroBoletoBancarioSituacaoHistorico() {

	}

	/**
	 * Constructor for the FiltroBoletoBancarioSituacaoHistorico object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroBoletoBancarioSituacaoHistorico(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String ID = "id";

	public final static String BOLETO_BANCARIO = "boletoBancario";

	public final static String BOLETO_BANCARIO_ID = "boletoBancario.id";

	public final static String BOLETO_BANCARIO_SITUACAO = "boletoBancarioSituacao";

	public final static String BOLETO_BANCARIO_SITUACAO_ID = "boletoBancarioSituacao.id";

}
