/**
 * 
 */

package gcom.gui.cobranca.spcserasa;

import gcom.cobranca.NegativacaoImovei;
import gcom.cobranca.NegativadorContrato;
import gcom.cobranca.NegativadorMovimentoReg;

/**
 * @author Bruno Ferreira dos Santos
 */
public class DeterminarConfirmacaoNegativacaoHelper {

	private NegativadorMovimentoReg negativadorMovimentoReg;

	private NegativacaoImovei negativacaoImoveis;

	private NegativadorContrato negativadorContrato;

	public NegativadorMovimentoReg getNegativadorMovimentoReg(){

		return negativadorMovimentoReg;
	}

	public void setNegativadorMovimentoReg(NegativadorMovimentoReg negativadorMovimentoReg){

		this.negativadorMovimentoReg = negativadorMovimentoReg;
	}

	public NegativacaoImovei getNegativacaoImoveis(){

		return negativacaoImoveis;
	}

	public void setNegativacaoImoveis(NegativacaoImovei negativacaoImoveis){

		this.negativacaoImoveis = negativacaoImoveis;
	}

	public NegativadorContrato getNegativadorContrato(){

		return negativadorContrato;
	}

	public void setNegativadorContrato(NegativadorContrato negativadorContrato){

		this.negativadorContrato = negativadorContrato;
	}

}
