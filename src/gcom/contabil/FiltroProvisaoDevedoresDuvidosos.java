
package gcom.contabil;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * Filtro da entidade ProvisaoDevedoresDuvidosos
 * 
 * @author Hugo Lima
 * @date 26/06/2012
 */
public class FiltroProvisaoDevedoresDuvidosos
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroModulo object
	 */
	public FiltroProvisaoDevedoresDuvidosos() {

	}

	/**
	 * Constructor for the FiltroModulo object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroProvisaoDevedoresDuvidosos(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String ID = "id";

	public final static String DATA_BAIXA = "dataBaixa";

	public final static String REFERENCIA_BAIXA = "referenciaBaixa";

	public final static String REFERENCIA_CONTABIL = "referenciaContabil";

	public final static String CONTA = "conta";

	public final static String CONTA_ID = "conta.id";

	public final static String EVENTO_COMERCIAL = "eventoComercial";

	public final static String EVENTO_COMERCIAL_ID = "eventoComercial.id";

	public final static String EVENTO_COMERCIAL_BAIXA_CONTA_PDD = "eventoComercialBaixaContaPDD";

	public final static String EVENTO_COMERCIAL_BAIXA_CONTA_PDD_ID = "eventoComercialBaixaContaPDD.id";

	public final static String PROVISAO_DEVEDORES_DUVIDOSOS_MOTIVO_BAIXA = "provisaoDevedoresDuvidososMotivoBaixa";

	public final static String PROVISAO_DEVEDORES_DUVIDOSOS_MOTIVO_BAIXA_ID = "provisaoDevedoresDuvidososMotivoBaixa.id";

	public final static String CATEGORIA = "categoria";

	public final static String CATEGORIA_ID = "categoria.id";

}
