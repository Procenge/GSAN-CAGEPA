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
	 * < <Descrição do método>>
	 * 
	 * @return Descrição do retorno
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public ControladorHistogramaLocal create() throws CreateException;
}
