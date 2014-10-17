
package gcom.arrecadacao;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * Codigo Barras Layout - Filtro
 * 
 * @author Hebert Falcão
 * @date 18/05/2012
 */
public class FiltroCodigoBarrasLayout
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public FiltroCodigoBarrasLayout() {

	}

	public FiltroCodigoBarrasLayout(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String ID = "id";

	public final static String POSICAO_INICIO = "posicaoInicio";

	public final static String DOCUMENTO_TIPO_ID = "documentoTipo.id";

	public final static String CONCESSIONARIA_ID = "concessionaria.id";

	public final static String CODIGO_BARRAS_CAMPOS_ID = "codigoBarrasCampos.id";

}
