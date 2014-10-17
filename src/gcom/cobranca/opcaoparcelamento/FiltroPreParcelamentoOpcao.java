/**
 * 
 */

package gcom.cobranca.opcaoparcelamento;

import gcom.util.filtro.Filtro;

/**
 * @author Rodrigo Oliveira
 */
public class FiltroPreParcelamentoOpcao
				extends Filtro {

	private static final long serialVersionUID = 1L;

	public FiltroPreParcelamentoOpcao() {

	}

	public FiltroPreParcelamentoOpcao(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String ID = "id";

	public final static String PRE_PARCELAMENTO = "preParcelamento.id";

	public final static String POSICAO_OPCAO = "posicaoOpcao";

}
