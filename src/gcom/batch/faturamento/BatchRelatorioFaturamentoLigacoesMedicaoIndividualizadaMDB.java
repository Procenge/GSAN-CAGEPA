
package gcom.batch.faturamento;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author Ado Rocha
 * @date 07/11/2013
 */
public class BatchRelatorioFaturamentoLigacoesMedicaoIndividualizadaMDB
				implements MessageDrivenBean, MessageListener {


	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 */
	public void ejbCreate(){

	}

	public BatchRelatorioFaturamentoLigacoesMedicaoIndividualizadaMDB() {

		super();
	}

	public void onMessage(Message message){

	}

	/**
	 * {@inheritDoc}
	 */
	public void ejbRemove() throws EJBException{

		// Metodo vazio
	}

	/**
	 * {@inheritDoc}
	 */
	public void setMessageDrivenContext(MessageDrivenContext arg0) throws EJBException{

		// Metodo vazio
	}

}
