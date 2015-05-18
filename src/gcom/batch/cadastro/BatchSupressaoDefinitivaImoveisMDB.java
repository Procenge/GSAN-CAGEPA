
package gcom.batch.cadastro;

import gcom.cadastro.imovel.ControladorImovelLocal;
import gcom.cadastro.imovel.ControladorImovelLocalHome;
import gcom.util.*;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * <p>
 * [OC1366820] [NF] Desenvolver nova rotina batch para exclusão dos imóveis suprimidos total sem
 * débito.
 * </p>
 * 
 * @author Magno Silveira { @literal <magno.silveira@procenge.com.br> }
 * @since 07/10/2014
 */
public class BatchSupressaoDefinitivaImoveisMDB
				implements MessageDrivenBean, MessageListener {

	private static final long serialVersionUID = 1L;

	public BatchSupressaoDefinitivaImoveisMDB() {

		super();
	}

	public void onMessage(Message message){

		try{

			if(message instanceof ObjectMessage){
				ObjectMessage objectMessage = (ObjectMessage) message;

				this.getControladorImovel().atualizarImoveisComSupressaoDefinitiva(
								(Integer) ((Object[]) objectMessage.getObject())[0]);
			}

		}catch(JMSException e){
			e.printStackTrace();
		}catch(ControladorException e){
			e.printStackTrace();
		}
	}

	public void ejbRemove() throws EJBException{

	}

	public void setMessageDrivenContext(MessageDrivenContext arg0) throws EJBException{

	}

	private ControladorImovelLocal getControladorImovel(){

		ControladorImovelLocalHome localHome = null;
		ControladorImovelLocal local = null;

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorImovelLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_IMOVEL_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}



	public void ejbCreate(){

	}

}
