
package gcom.cadastro.cliente;

import gcom.util.filtro.Filtro;

public class FiltroClienteResponsavel
				extends Filtro {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public FiltroClienteResponsavel() {

	}

	/**
	 * Constructor for the FiltroClienteResponsavel object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroClienteResponsavel(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Id cliente responsavel
	 */
	public final static String ID = "id";

	public final static String CLIENTE = "cliente";

	public final static String CLIENTE_ID = "cliente.id";

	public final static String INDMULTA = "indMulta";

	public final static String INDJUROS = "indJuros";

	public final static String INDCORRECAO = "indCorrecao";

	public final static String INDIMPOSTOFEDERAL = "indImpostoFederal";

	public final static String AGENCIA = "agencia";

	public final static String AGENCIA_ID = "agencia.id";

	public final static String CONTABANCARIA = "contaBancaria";

	public final static String ULTIMAALTERACAO = "ultimaAlteracao";

	public final static String INDICADOR_USO = "indUso";
}
