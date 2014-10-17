
package gcom.atendimentopublico.ordemservico;

import gcom.util.filtro.Filtro;

/**
 * Descrição da classe
 * 
 * @author Ailton Sousa
 * @date 23/03/2011
 */
public class FiltroMaterialRedeEsgoto
				extends Filtro {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroModulo object
	 */
	public FiltroMaterialRedeEsgoto() {

	}

	/**
	 * Constructor for the FiltroSistemaAbastecimento object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroMaterialRedeEsgoto(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	/**
	 * Descricao do Material
	 */
	public final static String DESCRICAO = "descricao";

	/**
	 * Descricao Abreviada do Material
	 */
	public final static String DESCRICAO_ABREVIADA = "descricaoAbreviada";

	/**
	 * Indicador de Uso do Material
	 */
	public final static String INDICADOR_USO = "indicadorUso";

}
