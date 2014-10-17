/**
 * 
 */

package gcom.cobranca.bean;

import gcom.cobranca.opcaoparcelamento.PreParcelamento;
import gcom.cobranca.opcaoparcelamento.PreParcelamentoOpcao;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Bruno Ferreira dos Santos
 */
public class PreParcelamentoHelper {

	private PreParcelamento preParcelamento;

	private Collection<PreParcelamentoOpcao> colecaoPreParcelamentoOpcao = new ArrayList<PreParcelamentoOpcao>();

	/**
	 * @param preParcelamento
	 *            the preParcelamento to set
	 */
	public void setPreParcelamento(PreParcelamento preParcelamento){

		this.preParcelamento = preParcelamento;
	}

	/**
	 * @return the preParcelamento
	 */
	public PreParcelamento getPreParcelamento(){

		return preParcelamento;
	}

	/**
	 * @param colecaoPreParcelamentoOpcao
	 *            the colecaoPreParcelamentoOpcao to set
	 */
	public void setColecaoPreParcelamentoOpcao(Collection<PreParcelamentoOpcao> colecaoPreParcelamentoOpcao){

		this.colecaoPreParcelamentoOpcao = colecaoPreParcelamentoOpcao;
	}

	/**
	 * @return the colecaoPreParcelamentoOpcao
	 */
	public Collection<PreParcelamentoOpcao> getColecaoPreParcelamentoOpcao(){

		return colecaoPreParcelamentoOpcao;
	}

}
