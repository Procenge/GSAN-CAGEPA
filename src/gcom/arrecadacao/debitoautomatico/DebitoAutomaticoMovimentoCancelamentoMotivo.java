/**
 * 
 */

package gcom.arrecadacao.debitoautomatico;

import gcom.util.tabelaauxiliar.abreviada.TabelaAuxiliarAbreviada;

/**
 * @author Bruno Ferreira dos Santos
 * @date 17/08/2011
 */
public class DebitoAutomaticoMovimentoCancelamentoMotivo
				extends TabelaAuxiliarAbreviada {

	public final static Integer CANCELAMENTO_CONTA = 1;

	public final static Integer INCLUSAO_CONTA_REVISAO = 2;

	public final static Integer RETIRADA_CONTA_REVISAO = 3;

	public DebitoAutomaticoMovimentoCancelamentoMotivo() {

	}

	public DebitoAutomaticoMovimentoCancelamentoMotivo(Integer id) {

		this.id = id;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		// TODO Auto-generated method stub
		return null;
	}

}
