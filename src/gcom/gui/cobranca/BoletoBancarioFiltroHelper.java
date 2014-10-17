
package gcom.gui.cobranca;

import gcom.cobranca.BoletoBancario;
import gcom.cobranca.BoletoBancarioSituacaoHistorico;

/**
 * [UC3023] Manter Boleto Bancário
 * Classe facilitadora para o retorno do filtro a ser usado no manter.
 * 
 * @author Hebert Falcão
 * @date 12/10/2011
 */
public class BoletoBancarioFiltroHelper {

	private BoletoBancario boletoBancario;

	private BoletoBancarioSituacaoHistorico boletoBancarioSituacaoHistorico;

	private boolean habilitarCancelamento;

	public BoletoBancario getBoletoBancario(){

		return boletoBancario;
	}

	public void setBoletoBancario(BoletoBancario boletoBancario){

		this.boletoBancario = boletoBancario;
	}

	public BoletoBancarioSituacaoHistorico getBoletoBancarioSituacaoHistorico(){

		return boletoBancarioSituacaoHistorico;
	}

	public void setBoletoBancarioSituacaoHistorico(BoletoBancarioSituacaoHistorico boletoBancarioSituacaoHistorico){

		this.boletoBancarioSituacaoHistorico = boletoBancarioSituacaoHistorico;
	}

	public boolean isHabilitarCancelamento(){

		return habilitarCancelamento;
	}

	public void setHabilitarCancelamento(boolean habilitarCancelamento){

		this.habilitarCancelamento = habilitarCancelamento;
	}

}
