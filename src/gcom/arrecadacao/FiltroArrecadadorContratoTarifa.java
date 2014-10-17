/**
 * 
 */

package gcom.arrecadacao;

import gcom.util.filtro.Filtro;

/**
 * @author Bruno Ferreira dos Santos
 */
public class FiltroArrecadadorContratoTarifa
				extends Filtro {

	/**
	 * 
	 */
	public FiltroArrecadadorContratoTarifa() {

		// TODO Auto-generated constructor stub
	}

	public FiltroArrecadadorContratoTarifa(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String ID = "comp_id";

	public final static String ID_FORMA_ARRECADACAO = "comp_id.arrecadacaoFormaId";

	public final static String ID_ARRECADADOR_CONTRATO = "comp_id.arrecadadorContratoId";

}
