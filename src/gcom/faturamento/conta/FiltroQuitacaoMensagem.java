
package gcom.faturamento.conta;

import gcom.util.filtro.Filtro;

/**
 * Filtro do objeto Quita��o Mensagem
 * 
 * @author Hebert Falc�o
 * @date 02/05/2011
 */
public class FiltroQuitacaoMensagem
				extends Filtro {

	private static final long serialVersionUID = 1L;

	public final static String ID = "id";

	public final static String MENSAGEM_QUITACAO_01 = "descricaoQuitacaoMensagem01";

	public final static String MENSAGEM_QUITACAO_02 = "descricaoQuitacaoMensagem02";

	public final static String MENSAGEM_QUITACAO_03 = "descricaoQuitacaoMensagem03";

	public final static String INDICADOR_USO = "indicadorUso";

	/**
	 * Construtor da classe FiltroCategoria
	 */
	public FiltroQuitacaoMensagem() {

	}

	/**
	 * Construtor da classe FiltroCategoria
	 * 
	 * @param campoOrderBy
	 *            Descri��o do par�metro
	 */
	public FiltroQuitacaoMensagem(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

}
