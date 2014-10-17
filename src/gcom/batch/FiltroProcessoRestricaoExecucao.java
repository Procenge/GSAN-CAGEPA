
package gcom.batch;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * O filtro serve para armazenar os critérios de busca de um ProcessoExecucaoRestricao
 * 
 * @author Hugo Lima
 * @created 28/02/2012
 */

public class FiltroProcessoRestricaoExecucao
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public FiltroProcessoRestricaoExecucao() {

	}

	public FiltroProcessoRestricaoExecucao(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String ID = "id";

	public final static String INDICADOR_USO = "indicadorUso";

	public final static String ID_PROCESSO_INICIAR = "processoIniciar.id";

	public final static String ID_PROCESSO_EXECUCAO = "processoExecucao.id";

	public final static String IP = "ip";
}
