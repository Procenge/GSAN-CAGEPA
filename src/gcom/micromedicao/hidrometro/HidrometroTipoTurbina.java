
package gcom.micromedicao.hidrometro;

import gcom.util.tabelaauxiliar.abreviada.TabelaAuxiliarAbreviada;

/**
 * Tipos de Turbina de Hidrômetro
 * 
 * @author Hebert Falcão
 * @date 21/04/2012
 */
public class HidrometroTipoTurbina
				extends TabelaAuxiliarAbreviada {

	private static final long serialVersionUID = 1L;

	private char codigo;

	public char getCodigo(){

		return codigo;
	}

	public void setCodigo(char codigo){

		this.codigo = codigo;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

}
