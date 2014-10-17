
package gcom.cobranca;

import gcom.util.filtro.Filtro;

/**
 * @author anishimura
 * @date fevereiro/2011
 */
public class FiltroInfracaoPerfil
				extends Filtro {

	public final static String ID = "id";

	public final static String CATEGORIA = "categoria";

	public final static String SUBCATEGORIA = "subcategoria";

	public final static String IMOVEL_PERFIL = "imovelPerfil";

	public final static String INFRACAO_TIPO = "infracaoTipo";

	public final static String CATEGORIA_ID = "categoria.id";

	public final static String SUBCATEGORIA_ID = "subcategoria.id";

	public final static String IMOVEL_PERFIL_ID = "imovelPerfil.id";

	public final static String INFRACAO_TIPO_ID = "infracaoTipo.id";

	public final static String INDICADOR_USO = "indicadorUso";

	public FiltroInfracaoPerfil() {

	}

	public FiltroInfracaoPerfil(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}
}
