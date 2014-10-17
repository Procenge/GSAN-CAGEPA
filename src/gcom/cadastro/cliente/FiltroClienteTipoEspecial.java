
package gcom.cadastro.cliente;

import gcom.util.filtro.Filtro;

/*
 *  O filtro serve para armazenar os critérios de busca de um cliente
 *
 *  @author     Bruno Ferreira dos Santos
 *  @created    14 de Janeiro de 2011
 */
/**
 * < <Descrição da Classe>>
 * 
 * @author bferreira
 */
public class FiltroClienteTipoEspecial
				extends Filtro {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public FiltroClienteTipoEspecial() {

	}

	/**
	 * Constructor for the FiltroTipoClienteEspecial object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroClienteTipoEspecial(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Id tipo cliente especial
	 */
	public final static String ID = "id";

	/**
	 * Descricao tipo cliente especial
	 */
	public final static String DESCRICAO = "descricao";

	/**
	 * Nome abreviado tipo cliente especial
	 */
	public final static String NOME_ABREVIADO = "nomeAbreviado";
}
