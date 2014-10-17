
package gcom.cobranca;

import gcom.util.filtro.Filtro;

/**
 * Filtro - Resolução Diretoria Parâmetros Pagamento A Vista
 * 
 * @author Hebert Falcão
 * @date 30/10/2012
 */
public class FiltroResolucaoDiretoriaParametrosPagamentoAVista
				extends Filtro {

	private static final long serialVersionUID = 1L;

	public final static String ID = "id";

	public final static String RESOLUCAO_DIRETORIA = "resolucaoDiretoria";

	public final static String RESOLUCAO_DIRETORIA_ID = "resolucaoDiretoria.id";

	public final static String DATA_PAGAMENTO_INICIO = "dataPagamentoInicio";

	public final static String DATA_PAGAMENTO_FINAL = "dataPagamentoFinal";

	/**
	 * Construtor da classe FiltroResolucaoDiretoriaParametrosPagamentoAVista
	 */
	public FiltroResolucaoDiretoriaParametrosPagamentoAVista() {

	}

	/**
	 * Construtor da classe FiltroResolucaoDiretoriaParametrosPagamentoAVista
	 * 
	 * @param campoOrderBy
	 *            Descrição do parâmetro
	 */
	public FiltroResolucaoDiretoriaParametrosPagamentoAVista(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

}