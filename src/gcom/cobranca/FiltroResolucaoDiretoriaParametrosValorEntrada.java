
package gcom.cobranca;

import gcom.util.filtro.Filtro;

/**
 * Filtro - Resolução Diretoria Parâmetros Valor Entrada
 * 
 * @author Hebert Falcão
 * @date 30/10/2012
 */
public class FiltroResolucaoDiretoriaParametrosValorEntrada
				extends Filtro {

	private static final long serialVersionUID = 1L;

	public final static String ID = "id";

	public final static String RESOLUCAO_DIRETORIA = "resolucaoDiretoria";

	public final static String RESOLUCAO_DIRETORIA_ID = "resolucaoDiretoria.id";

	public final static String DATA_NEGOCIACAO_INICIO = "dataNegociacaoInicio";

	public final static String DATA_NEGOCIACAO_FINAL = "dataNegociacaoFinal";

	/**
	 * Construtor da classe FiltroResolucaoDiretoriaParametrosValorEntrada
	 */
	public FiltroResolucaoDiretoriaParametrosValorEntrada() {

	}

	/**
	 * Construtor da classe FiltroResolucaoDiretoriaParametrosValorEntrada
	 * 
	 * @param campoOrderBy
	 *            Descrição do parâmetro
	 */
	public FiltroResolucaoDiretoriaParametrosValorEntrada(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

}