
package gcom.cobranca;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * Filtro da entidade CobrancaNegociacaoAtendimento
 * 
 * @author Hebert Falcão
 * @date 25/11/2011
 */
public class FiltroCobrancaNegociacaoAtendimento
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroCobrancaNegociacaoAtendimento object
	 */
	public FiltroCobrancaNegociacaoAtendimento() {

	}

	/**
	 * Constructor for the FiltroCobrancaNegociacaoAtendimento object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroCobrancaNegociacaoAtendimento(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String ID = "id";

	public final static String COBRANCA_DOCUMENTO = "cobrancaDocumento";

	public final static String COBRANCA_DOCUMENTO_ID = "cobrancaDocumento.id";

	public final static String PRE_PARCELAMENTO = "preParcelamento";

	public final static String PRE_PARCELAMENTO_ID = "preParcelamento.id";

	public final static String IMOVEL = "imovel";

	public final static String IMOVEL_ID = "imovel.id";

	public final static String REGISTRO_ATENDIMENTO = "registroAtendimento";

	public final static String REGISTRO_ATENDIMENTO_ID = "registroAtendimento.id";

	public final static String NUMERO_CPF = "numeroCpf";

	public final static String EMAIL = "email";

	public final static String DATA_VENCIMENTO = "dataVencimento";

}
