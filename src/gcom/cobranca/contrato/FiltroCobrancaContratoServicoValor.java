
package gcom.cobranca.contrato;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

public class FiltroCobrancaContratoServicoValor
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public FiltroCobrancaContratoServicoValor(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Construtor da classe FiltroCobrancaContratoServicoValor
	 */
	public FiltroCobrancaContratoServicoValor() {

	}

	public final static String ID = "id";

	public final static String COBRANCA_CONTRATO_REMUNERACAO_ID = "cobrancaContratoRemuneracao.id";

	public final static String COBRANCA_CONTRATO_SERVICO_TIPO = "servicoTipo";

}
