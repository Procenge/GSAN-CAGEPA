/**
 * 
 */

package gcom.faturamento.histograma;

import javax.ejb.CreateException;

/**
 * @author ebandeira
 */
public interface ControladorHistogramaLocalHome
				extends javax.ejb.EJBLocalHome {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @return Descri��o do retorno
	 * @exception CreateException
	 *                Descri��o da exce��o
	 */
	public ControladorHistogramaLocal create() throws CreateException;
}
