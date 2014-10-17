
package gcom.batch.contabil;

import gcom.contabil.ControladorContabilLocal;
import gcom.contabil.ControladorContabilLocalHome;
import gcom.util.ConstantesJNDI;
import gcom.util.ControladorException;
import gcom.util.ServiceLocator;
import gcom.util.ServiceLocatorException;
import gcom.util.SistemaException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class BatchProvisaoDevedoresDuvidososMDB
				implements MessageDrivenBean, MessageListener {

	private static final long serialVersionUID = 1L;

	public BatchProvisaoDevedoresDuvidososMDB() {

		super();
	}

	public void ejbRemove() throws EJBException{

		// TODO Auto-generated method stub

	}

	public void setMessageDrivenContext(MessageDrivenContext arg0) throws EJBException{

		// TODO Auto-generated method stub

	}

	public void onMessage(Message message){

		if(message instanceof ObjectMessage){

			try{
				ObjectMessage objectMessage = (ObjectMessage) message;

				System.out.println("....................Enviando mensagem para batch ");
				this.getControladorContabil().provisaoDevedoresDuvidosos((Integer) ((Object[]) objectMessage.getObject())[0]);

			}catch(ControladorException e){
				System.out.println("Erro no MDB");
				e.printStackTrace();
			}catch(JMSException e){
				System.out.println("Erro no MDB");
				e.printStackTrace();
			}
		}

	}

	/**
	 * Retorna a interface remota de ControladorContabil
	 * 
	 * @return A interface remota do controlador de parâmetro
	 */
	private ControladorContabilLocal getControladorContabil(){

		ControladorContabilLocalHome localHome = null;
		ControladorContabilLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorContabilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CONTABIL_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 */
	public void ejbCreate(){

	}

}
