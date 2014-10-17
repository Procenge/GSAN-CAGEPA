
package gcom.batch.faturamento;

import gcom.cobranca.ControladorCobrancaLocal;
import gcom.cobranca.ControladorCobrancaLocalHome;
import gcom.faturamento.FaturamentoGrupo;
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
 * Cancelar Aviso de Corte Pendente
 * 
 * @author Hebert Falcão
 * @date 11/09/2013
 */
public class BatchCancelarAvisoDeCortePendenteMDB
				implements MessageDrivenBean, MessageListener {

	private static final long serialVersionUID = 1L;

	public BatchCancelarAvisoDeCortePendenteMDB() {

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
				this.getControladorCobranca().cancelarAvisoDeCortePendente((Integer) ((Object[]) objectMessage.getObject())[0],
								(FaturamentoGrupo) ((Object[]) objectMessage.getObject())[1]);
			}catch(JMSException e){
				System.out.println("Erro no MDB");
				e.printStackTrace();
			}catch(ControladorException e){
				System.out.println("Erro no MDB");
				e.printStackTrace();
			}
		}
	}

	private ControladorCobrancaLocal getControladorCobranca(){

		ControladorCobrancaLocalHome localHome = null;
		ControladorCobrancaLocal local = null;

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorCobrancaLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);

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
