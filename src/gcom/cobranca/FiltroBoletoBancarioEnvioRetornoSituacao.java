
package gcom.cobranca;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

public class FiltroBoletoBancarioEnvioRetornoSituacao
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public FiltroBoletoBancarioEnvioRetornoSituacao() {

	}

	public FiltroBoletoBancarioEnvioRetornoSituacao(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String ID = "id";

	public final static String INDICADOR_CONTAS_EM_REVISAO = "indicadorRetirarContasRevisao";

	public final static String CODIGO_ATUALIZAR_SITUACAO_COBRANCA = "codigoAtualizarSituacaoCobranca";

	public final static String BOLETO_BANCARIO_LANCAMENTO_ENVIO_ID = "boletoBancarioLancamentoEnvio.id";

	public final static String BOLETO_BANCARIO_LANCAMENTO_ENVIO = "boletoBancarioLancamentoEnvio";

	public final static String BOLETO_BANCARIO_LANCAMENTO_RETORNO_ID = "boletoBancarioLancamentoRetorno.id";

	public final static String BOLETO_BANCARIO_LANCAMENTO_RETORNO = "boletoBancarioLancamentoRetorno";

	public final static String BOLETO_BANCARIO_SITUACAO_ATUAL_ID = "situacaoAtual.id";

	public final static String BOLETO_BANCARIO_SITUACAO_ATUAL = "situacaoAtual";
}
