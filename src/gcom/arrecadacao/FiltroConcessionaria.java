
package gcom.arrecadacao;

import gcom.util.filtro.Filtro;

/**
 * Filtro do Objeto Concessionaria
 * 
 * @author Hebert Falcão
 * @date 22/02/2013
 */
public class FiltroConcessionaria
				extends Filtro {

	private static final long serialVersionUID = 1L;

	public FiltroConcessionaria(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public FiltroConcessionaria() {

	}

	public final static Integer VALOR_INDICADOR_EMPRESA_CONCEDENTE = 1;

	public final static String ID = "id";

	public final static String INDICADOR_USO = "indicadorUso";

	public final static String INDICADOR_EMPRESA_CONCEDENTE = "indicadorEmpresaConcedente";

	public final static String NOME = "nome";

	public final static String CODIGO_EMPRESA_FEBRABAN = "codigoEmpresaFebraban";

}
