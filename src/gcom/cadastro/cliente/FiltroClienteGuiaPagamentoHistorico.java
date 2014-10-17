
package gcom.cadastro.cliente;

import gcom.util.filtro.Filtro;

/**
 * Filtro da entidade ClienteGuiaPagamentoHistorico
 * 
 * @author Hebert Falcão
 * @date 19/10/2011
 */
public class FiltroClienteGuiaPagamentoHistorico
				extends Filtro {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroClienteFone object
	 */
	public FiltroClienteGuiaPagamentoHistorico() {

	}

	/**
	 * Constructor for the FiltroClienteFone object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroClienteGuiaPagamentoHistorico(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String ID = "id";

	public final static String GUIA_PAGAMENTO_ID = "guiaPagamentoHistorico.id";

	public final static String CLIENTE_RELACAO_TIPO_ID = "clienteRelacaoTipo.id";

	public final static String CLIENTE = "cliente";

}
