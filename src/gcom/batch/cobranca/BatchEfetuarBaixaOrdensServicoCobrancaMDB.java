
package gcom.batch.cobranca;

import gcom.cobranca.ControladorCobrancaLocal;
import gcom.cobranca.ControladorCobrancaLocalHome;
import gcom.seguranca.acesso.usuario.Usuario;
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

public class BatchEfetuarBaixaOrdensServicoCobrancaMDB
				implements MessageDrivenBean, MessageListener {

	private static final long serialVersionUID = 1L;

	public BatchEfetuarBaixaOrdensServicoCobrancaMDB() {

		super();
	}

	public void onMessage(Message arg0){

		// TODO Auto-generated method stub

	}

	public void ejbCreate(){

	}

	public void ejbRemove() throws EJBException{

		// TODO Auto-generated method stub

	}

	public void setMessageDrivenContext(MessageDrivenContext message) throws EJBException{

		if(message instanceof ObjectMessage){

			ObjectMessage objectMessage = (ObjectMessage) message;
			try{

				String arquivo = (String) ((Object[]) objectMessage.getObject())[0];
				Usuario usuario = (Usuario) ((Object[]) objectMessage.getObject())[1];

				this.getControladorCobranca().efetuarBaixaOrdensServicoCobranca(arquivo, usuario);

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

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorCobrancaLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas à
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

}
