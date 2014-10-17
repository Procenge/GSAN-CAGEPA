/**
 * 
 */

package gcom.cobranca;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * @author bferreira
 */
public class FiltroBoletoBancarioMovimentacao
				extends Filtro
				implements Serializable {

	/**
	 * 
	 */
	public FiltroBoletoBancarioMovimentacao() {

		// TODO Auto-generated constructor stub
	}

	public FiltroBoletoBancarioMovimentacao(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String ID = "id";

	public final static String BOLETO_BANCARIO_MOVIMENTACAO_RETORNO = "boletoBancarioMovimentacaoRetorno";

	public final static String BOLETO_BANCARIO_MOVIMENTACAO_RETORNO_ID = "boletoBancarioMovimentacaoRetorno.id";

	public final static String ARRECADADOR_MOVIMENTO_ITEM = "arrecadadorMovimentoItem";

	public final static String ARRECADADOR_MOVIMENTO_ITEM_ID = "arrecadadorMovimentoItem.id";

	public final static String BOLETO_BANCARIO_LANCAMENTO_ENVIO_ID = "boletoBancarioLancamentoEnvio.id";

	public final static String COBRANCA_DOCUMENTO_ID = "boletoBancario.documentoCobranca.id";

	public final static String DATA_MOVIMENTACAO = "dataMovimentacao";

	public final static String BOLETO_BANCARIO_ID = "boletoBancario.id";

	public final static String BOLETO_BANCARIO = "boletoBancario";

	public final static String BOLETO_BANCARIO_LANCAMENTO_ENVIO = "boletoBancarioLancamentoEnvio";

	public final static String BOLETO_BANCARIO_LANCAMENTO_RETORNO_ID = "boletoBancarioLancamentoRetorno.id";

	public final static String BOLETO_BANCARIO_LANCAMENTO_RETORNO = "boletoBancarioLancamentoRetorno";

	public final static String DATA_MOVIMENTACAO_ORDENACAO_DESC = "dataMovimentacao desc";
}
