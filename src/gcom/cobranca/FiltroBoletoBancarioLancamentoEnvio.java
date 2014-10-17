
package gcom.cobranca;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * Filtro da entidade BoletoBancarioLancamentoEnvio
 * 
 * @author Hebert Falcão
 * @date 12/10/2011
 */
public class FiltroBoletoBancarioLancamentoEnvio
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the BoletoBancarioLancamentoEnvio object
	 */
	public FiltroBoletoBancarioLancamentoEnvio() {

	}

	/**
	 * Constructor for the BoletoBancarioLancamentoEnvio object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroBoletoBancarioLancamentoEnvio(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String ID = "id";

	public final static String INDICADOR_COMANDO = "indicadorComando";

	public final static String INDICADOR_USO = "indicadorUso";

	public final static String SITUACAO_ATUAL_OBRIGATORIA = "situacaoAtualObrigatoria";

	public final static String DESCRICAO_LANCAMENTO_ENVIO = "descricaoLancamentoEnvio";

}
