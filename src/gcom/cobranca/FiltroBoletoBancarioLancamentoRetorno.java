
package gcom.cobranca;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

public class FiltroBoletoBancarioLancamentoRetorno
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroBoletoBancarioLancamentoRetorno object
	 */
	public FiltroBoletoBancarioLancamentoRetorno() {

	}

	/**
	 * Constructor for the FiltroBoletoBancarioLancamentoRetorno object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroBoletoBancarioLancamentoRetorno(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	/**
	 * Description of the Field
	 */
	public final static String DESCRICAO = "descricaoLancamentoRetorno";

	public final static String INDICADOR_LIQUIDACAO = "indicadorLiquidacao";

	public final static String INDICADOR_USO = "indicadorUso";

	public final static String BOLETO_BANACARIO_TIPO_OCORRENCIA = "boletoBancarioTipoOcorrencia";

	public final static String BOLETO_BANACARIO_TIPO_OCORRENCIA_ID = "boletoBancarioTipoOcorrencia.id";

}
