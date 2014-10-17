
package gcom.arrecadacao;

import gcom.util.filtro.Filtro;

/**
 * Filtro do Objeto ConcessionariaLocalidade
 * 
 * @author Hebert Falcão
 * @date 22/02/2013
 */
public class FiltroConcessionariaLocalidade
				extends Filtro {

	private static final long serialVersionUID = 1L;

	public FiltroConcessionariaLocalidade(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public FiltroConcessionariaLocalidade() {

	}

	public final static String ID = "id";

	public final static String CONCESSIONARIA = "concessionaria";

	public final static String ID_CONCESSIONARIA = "concessionaria.id";

	public final static String LOCALIDADE = "localidade";

	public final static String ID_LOCALIDADE = "localidade.id";

	public final static String INDICADOR_USO_LOCALIDADE = "localidade.indicadorUso";

	public final static String DATA_VIGENCIA_FIM = "dataVigenciaFim";

}
