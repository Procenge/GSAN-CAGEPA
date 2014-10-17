/**
 * 
 */

package gcom.cadastro.imovel;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * @author Hebert Falcão
 * @created 28 de Janeiro de 2011
 */
public class FiltroImovelConsumoFaixaAreaCategoria
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public FiltroImovelConsumoFaixaAreaCategoria() {

	}

	public FiltroImovelConsumoFaixaAreaCategoria(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String COMP_ID = "comp_id";

	public final static String CONSUMO_FAIXA_AREA_CATEGORIA_ID = "comp_id.consumoFaixaAreaCategoria.id";

	public final static String CONSUMO_FAIXA_AREA_CATEGORIA = "comp_id.consumoFaixaAreaCategoria";

	public final static String IMOVEL_ID = "comp_id.imovel.id";

	public final static String IMOVEL = "comp_id.imovel";

	public final static String CATEGORIA_ID = "categoria.id";

	public final static String CATEGORIA = "categoria";

	public final static String INDICADOR_USO = "indicadorUso";

}
