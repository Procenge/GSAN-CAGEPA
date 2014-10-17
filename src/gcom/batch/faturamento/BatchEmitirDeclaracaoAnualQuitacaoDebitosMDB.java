
package gcom.batch.faturamento;

import gcom.faturamento.ControladorFaturamentoLocal;
import gcom.faturamento.ControladorFaturamentoLocalHome;
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
 * [UC3014] Emitir Declaração Anual Quitação Débitos
 * 
 * @author Hebert Falcão
 * @created 27/04/2013
 */
public class BatchEmitirDeclaracaoAnualQuitacaoDebitosMDB
				implements MessageDrivenBean, MessageListener {

	private static final long serialVersionUID = 1L;

	public BatchEmitirDeclaracaoAnualQuitacaoDebitosMDB() {

		super();
	}

	public void setMessageDrivenContext(MessageDrivenContext ctx) throws EJBException{

	}

	public void ejbRemove() throws EJBException{

	}

	public void onMessage(Message message){

		if(message instanceof ObjectMessage){

			ObjectMessage objectMessage = (ObjectMessage) message;
			try{
				this.getControladorFaturamento().emitirDeclaracaoAnualQuitacaoDebitos((Integer) ((Object[]) objectMessage.getObject())[0],
								(Integer) ((Object[]) objectMessage.getObject())[1], null, null,
								(Integer) ((Object[]) objectMessage.getObject())[2]);

			}catch(JMSException e){

				System.out.println("Erro no MDB");
				e.printStackTrace();
			}catch(ControladorException e){

				System.out.println("Erro no MDB");
				e.printStackTrace();
			}
		}

	}

	private ControladorFaturamentoLocal getControladorFaturamento(){

		ControladorFaturamentoLocalHome localHome = null;
		ControladorFaturamentoLocal local = null;
		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorFaturamentoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_FATURAMENTO_SEJB);
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
