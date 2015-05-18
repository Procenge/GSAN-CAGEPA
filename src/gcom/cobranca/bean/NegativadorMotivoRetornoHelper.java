
package gcom.cobranca.bean;

import gcom.cobranca.NegativadorMovimentoReg;
import gcom.cobranca.NegativadorRetornoMotivo;

import java.io.Serializable;

/**
 * @author ysouza
 */
public class NegativadorMotivoRetornoHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private NegativadorMovimentoReg negativadorMovimentoReg;

	NegativadorRetornoMotivo negativadorRetornoMot;


	public NegativadorMotivoRetornoHelper() {

	}


	public NegativadorMovimentoReg getNegativadorMovimentoReg(){

		return negativadorMovimentoReg;
	}


	public void setNegativadorMovimentoReg(NegativadorMovimentoReg negativadorMovimentoReg){

		this.negativadorMovimentoReg = negativadorMovimentoReg;
	}


	public NegativadorRetornoMotivo getNegativadorRetornoMot(){

		return negativadorRetornoMot;
	}


	public void setNegativadorRetornoMot(NegativadorRetornoMotivo negativadorRetornoMot){

		this.negativadorRetornoMot = negativadorRetornoMot;
	}


}
