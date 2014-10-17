/**
 * 
 */

package gcom.cobranca.opcaoparcelamento;

import gcom.util.filtro.Filtro;

/**
 * @author Bruno Ferreira dos Santos
 */
public class FiltroPreParcelamento
				extends Filtro {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroImovelEconomia object
	 */
	public FiltroPreParcelamento() {

	}

	/**
	 * Constructor for the FiltroCliente object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroPreParcelamento(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	public final static String IMOVEL_ID = "imovel.id";

	public final static String COBRANCA_FORMA = "cobrancaForma.id";

	public final static String PARCELAMENTO_SITUACAO = "situacaoParcelamento.id";

	public final static String FUNCIONARIO = "funcionario.id";

	public final static String ANO_MES_REFERENCIA_FATURAMENTO = "anoMesReferenciaFaturamento";

	public final static String PARCELAMENTO_PERFIL_ID = "parcelamentoPerfil.id";

	public final static String COBRANCA_DOCUMENTO_ID = "documentoCobranca.id";

}
