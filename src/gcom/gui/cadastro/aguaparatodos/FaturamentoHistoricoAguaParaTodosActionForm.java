/**
 * 
 */

package gcom.gui.cadastro.aguaparatodos;

import java.util.Collection;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * @author Bruno Ferreira dos Santos
 */
public class FaturamentoHistoricoAguaParaTodosActionForm
				extends ValidatorActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Collection colFaturamentoHistorico;

	/**
	 * @param colFaturamentoHistorico
	 *            the colFaturamentoHistorico to set
	 */
	public void setColFaturamentoHistorico(Collection colFaturamentoHistorico){

		this.colFaturamentoHistorico = colFaturamentoHistorico;
	}

	/**
	 * @return the colFaturamentoHistorico
	 */
	public Collection getColFaturamentoHistorico(){

		return colFaturamentoHistorico;
	}
}
