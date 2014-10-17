/**
 * 
 */

package gcom.atendimentopublico.registroatendimento;

import gcom.util.tabelaauxiliar.abreviada.TabelaAuxiliarAbreviada;

/**
 * @author Bruno Ferreira dos Santos
 */
public class MotivoTramite
				extends TabelaAuxiliarAbreviada {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

}
