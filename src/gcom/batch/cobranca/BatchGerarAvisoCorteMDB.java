
package gcom.batch.cobranca;

import gcom.cobranca.ControladorCobrancaLocal;
import gcom.cobranca.ControladorCobrancaLocalHome;
import gcom.faturamento.FaturamentoAtivCronRota;
import gcom.faturamento.FaturamentoAtividade;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FaturamentoGrupoCronogramaMensal;
import gcom.util.ConstantesJNDI;
import gcom.util.ControladorException;
import gcom.util.ServiceLocator;
import gcom.util.ServiceLocatorException;
import gcom.util.SistemaException;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * [UC3068] Gerar Aviso Corte Faturamento
 * 
 * @author Hebert Falcão
 * @date 26/08/2012
 */
public class BatchGerarAvisoCorteMDB
				implements MessageDrivenBean, MessageListener {

	public BatchGerarAvisoCorteMDB() {

		super();
	}

	public void setMessageDrivenContext(MessageDrivenContext arg0) throws EJBException{

	}

	public void ejbRemove() throws EJBException{

	}

	public void onMessage(Message message){

		if(message instanceof ObjectMessage){
			ObjectMessage objectMessage = (ObjectMessage) message;

			try{
				this.getControladorCobranca().gerarAvisoCorteFaturamento(
								(Collection<FaturamentoAtivCronRota>) ((Object[]) objectMessage.getObject())[0],
								(Integer) ((Object[]) objectMessage.getObject())[1],
								(FaturamentoGrupo) ((Object[]) objectMessage.getObject())[2],
								(Integer) ((Object[]) objectMessage.getObject())[3],
								(FaturamentoAtividade) ((Object[]) objectMessage.getObject())[4],
								(FaturamentoGrupoCronogramaMensal) ((Object[]) objectMessage.getObject())[5],
								(Integer) ((Object[]) objectMessage.getObject())[6]);
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

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 */
	public void ejbCreate(){

	}

}
