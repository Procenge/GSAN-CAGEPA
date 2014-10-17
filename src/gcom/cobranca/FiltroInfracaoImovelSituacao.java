
package gcom.cobranca;

import gcom.util.filtro.Filtro;

/**
 * @author anishimura
 * @date fevereiro/2011
 */
public class FiltroInfracaoImovelSituacao
				extends Filtro {

	private static final long serialVersionUID = 1L;

	public static final String ID = "id";

	public static final String DESCRICAO = "descricao";

	public static final String INDICADOR_USO = "indicadorUso";

	public static final String DESCRICAOABREVIADA = "descricaoAbreviada";

	public FiltroInfracaoImovelSituacao() {

	}

	public FiltroInfracaoImovelSituacao(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}
}
