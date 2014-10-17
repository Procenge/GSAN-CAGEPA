package gcom.batch.arrecadacao;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;

public class BatchGerarRelatorioAcompanhamentoMovimentoArrecadadoresMDB
				implements MessageDrivenBean, MessageListener {

	public BatchGerarRelatorioAcompanhamentoMovimentoArrecadadoresMDB() {

		super();
	}

	public void ejbCreate(){

	}

	public void onMessage(Message arg0){

		// TODO Auto-generated method stub

	}

	public void ejbRemove() throws EJBException{

		// TODO Auto-generated method stub

	}

	public void setMessageDrivenContext(MessageDrivenContext arg0) throws EJBException{

		// TODO Auto-generated method stub

	}

}
