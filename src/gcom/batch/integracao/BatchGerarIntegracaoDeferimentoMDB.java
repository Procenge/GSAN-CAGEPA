/*
 * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 * 
 * GSANPCG
 */
package gcom.batch.integracao;

import gcom.util.ControladorException;
import gcom.util.ServiceLocator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;


/**
 * @author mgrb
 *         Batch que inicia a atividade descrita em: [OC827869][UC3067]
 */
public class BatchGerarIntegracaoDeferimentoMDB
				implements MessageDrivenBean, MessageListener {

	private static final Logger LOGGER = Logger.getLogger(BatchGerarIntegracaoDeferimentoMDB.class);


	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 */
	public void ejbCreate(){

	}

	/**
	 * BatchGerarIntegracaoDeferimentoMDB
	 * <p>
	 * Esse método Constroi uma instância de BatchGerarIntegracaoDeferimentoMDB.
	 * </p>
	 * 
	 * @author Marlos Ribeiro
	 * @since 14/09/2012
	 */
	public BatchGerarIntegracaoDeferimentoMDB() {

		super();
	}

	/**
	 * {@inheritDoc}
	 */
	public void onMessage(Message message){

		if(message instanceof ObjectMessage){

			ObjectMessage objectMessage = (ObjectMessage) message;

			try{
				ServiceLocator.getInstancia().getControladorIntegracao()
								.gerarIntegracaoDeferimento((Integer) ((Object[]) objectMessage.getObject())[0]);
			}catch(ControladorException e){
				LOGGER.error("Erro no ControladorIntegracao", e);
			}catch(JMSException e){
				LOGGER.error("Erro no lookup do ControladorIntegracao", e);
			}
		}
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
