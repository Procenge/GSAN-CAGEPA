/**
 * 
 */

package gcom.arrecadacao.debitoautomatico;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * @author bferreira
 */
public class FiltroDebitoAutomaticoMovimento
				extends Filtro
				implements Serializable {

	/**
	 * 
	 */
	public FiltroDebitoAutomaticoMovimento() {

		// TODO Auto-generated constructor stub
	}

	public final static String NUMERO_SEQ_ARQ_ENVIADO = "numeroSequenciaArquivoEnviado";

	public final static String BANCO = "debitoAutomatico.agencia.banco.id";

	public final static String NUMERO_SEQ_ARQ_RETORNO = "numeroSequenciaArquivoRecebido";

	public final static String GUIA_PAGAMENTO_PRESTACAO = "guiaPagamento";

	public final static String GUIA_PAGAMENTO_PRESTACAO_ID = "guiaPagamento.id";

	public final static String DEBITO_AUTOMATICO = "debitoAutomatico";

	public final static String FATURAMENTO_GRUPO = "faturamentoGrupo";

	public final static String NUMERO_PRESTACAO = "numeroPrestacao";

}
