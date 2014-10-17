/* This file is part of GSAN, an integrated service management system for Sanitation
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
 * GSANPCG
 * Eduardo Henrique
 * 
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
 */

package gcom.batch.faturamento;

import gcom.faturamento.ControladorFaturamentoLocal;
import gcom.faturamento.ControladorFaturamentoLocalHome;
import gcom.faturamento.FaturamentoGrupo;
import gcom.micromedicao.Rota;
import gcom.micromedicao.leitura.LeituraTipo;
import gcom.util.ConstantesJNDI;
import gcom.util.ServiceLocator;
import gcom.util.ServiceLocatorException;
import gcom.util.SistemaException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * [UC0084] - Gerar Faturamento Imediato
 * 
 * @author eduardo henrique
 * @date 17/09/2008
 */
public class BatchGerarFaturamentoImediatoMDB
				implements MessageDrivenBean, MessageListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 */
	public void ejbCreate(){

	}

	/**
	 * 
	 */
	public BatchGerarFaturamentoImediatoMDB() {

		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ejb.MessageDrivenBean#ejbRemove()
	 */
	public void ejbRemove() throws EJBException{

	}

	/*
	 * (non-Javadoc)
	 * @see javax.ejb.MessageDrivenBean#setMessageDrivenContext(javax.ejb.MessageDrivenContext)
	 */
	public void setMessageDrivenContext(MessageDrivenContext ctx) throws EJBException{

	}

	/*
	 * (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message message){

		if(message instanceof ObjectMessage){

			try{
				ObjectMessage objectMessage = (ObjectMessage) message;

				Collection colecaoRota = (Collection) ((Object[]) objectMessage.getObject())[0];

				Integer anoMesCorrente = (Integer) ((Object[]) objectMessage.getObject())[1];

				FaturamentoGrupo grupoFaturamento = (FaturamentoGrupo) ((Object[]) objectMessage.getObject())[2];

				Integer idFuncionalidadeIniciada = (Integer) ((Object[]) objectMessage.getObject())[3];

				Collection<Rota> colecaoRotasFaturamentoImediato = new ArrayList<Rota>();
				Iterator iterator = colecaoRota.iterator();
				while(iterator.hasNext()){
					Rota rota = (Rota) iterator.next();

					if(rota.getLeituraTipo().getId().intValue() == LeituraTipo.CELULAR_MOBILE_ENTRADA_SIMULTANEA
									|| rota.getLeituraTipo().getId().intValue() == LeituraTipo.LEITURA_E_ENTRADA_SIMULTANEA){
						colecaoRotasFaturamentoImediato.add(rota);
					}
				}

				if(!colecaoRotasFaturamentoImediato.isEmpty()){
					// this.getControladorFaturamento().gerarFaturamentoImediatoGrupoFaturamentoAntigo(colecaoRotasFaturamentoImediato,
					// grupoFaturamento, anoMesCorrente, idFuncionalidadeIniciada);
				}

			}catch(JMSException e){
				System.out.println("Erro no MDB");
				e.printStackTrace();
				// }catch(ControladorException e){
				// System.out.println("Erro no MDB");
				// e.printStackTrace();
			}
		}

	}

	/**
	 * Retorna o valor de controladorFaturamento
	 * 
	 * @return O valor de controladorFaturamento
	 */
	private ControladorFaturamentoLocal getControladorFaturamento(){

		ControladorFaturamentoLocalHome localHome = null;
		ControladorFaturamentoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorFaturamentoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_FATURAMENTO_SEJB);
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
