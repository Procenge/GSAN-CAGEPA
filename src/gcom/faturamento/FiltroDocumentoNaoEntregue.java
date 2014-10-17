/**
 * GSANPCG
 * 
 * Eduardo Henrique
 * 
 */

package gcom.faturamento;

import gcom.util.filtro.Filtro;

/**
 * @author eduardo henrique
 * @date 11/07/2008
 */
public class FiltroDocumentoNaoEntregue
				extends Filtro {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da classe FiltroOcupacaoTipo
	 */
	public FiltroDocumentoNaoEntregue() {

	}

	public FiltroDocumentoNaoEntregue(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	/**
	 * Description of the Field
	 */
	public final static String INDICADOR_USO = "indicadorUso";

	/**
	 * Description of the Field
	 */
	public final static String CONTA_ID = "contaGeral.conta.id";

}
