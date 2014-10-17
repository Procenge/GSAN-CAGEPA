
package gcom.cobranca;

import gcom.util.filtro.Filtro;

/**
 * Filtro - Resolu��o Diretoria Par�metros Valor Entrada
 * 
 * @author Hebert Falc�o
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
	 *            Descri��o do par�metro
	 */
	public FiltroResolucaoDiretoriaParametrosValorEntrada(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

}