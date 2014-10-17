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

import gcom.micromedicao.ControladorMicromedicaoLocal;
import gcom.micromedicao.ControladorMicromedicaoLocalHome;
import gcom.micromedicao.Rota;
import gcom.micromedicao.leitura.LeituraTipo;
import gcom.util.ConstantesJNDI;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.ServiceLocator;
import gcom.util.ServiceLocatorException;
import gcom.util.SistemaException;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

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
 * [UC3012] -Gerar Arquivo Texto Faturamento Imediato
 * 
 * @author Yara Souza
 * @date 06/10/2011
 */
public class BatchGerarArquivoTextoFaturamentoImediatoMDB
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
	public BatchGerarArquivoTextoFaturamentoImediatoMDB() {

		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see javax.ejb.MessageDrivenBean#ejbRemove()
	 */
	public void ejbRemove() throws EJBException{

		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see javax.ejb.MessageDrivenBean#setMessageDrivenContext(javax.ejb.MessageDrivenContext)
	 */
	public void setMessageDrivenContext(MessageDrivenContext ctx) throws EJBException{

		// TODO Auto-generated method stub

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

				Integer idGrupoFaturamento = (Integer) ((Object[]) objectMessage.getObject())[2];

				Integer idFuncionalidadeIniciada = (Integer) ((Object[]) objectMessage.getObject())[3];

				Collection colecaoRotasFaturamentoImediato = new ArrayList();
				Iterator iterator = colecaoRota.iterator();
				Rota rota = null;

				while(iterator.hasNext()){
					Object[] objRota = (Object[]) iterator.next();

					rota = (Rota) objRota[1];

					if(rota.getLeituraTipo().getId().intValue() == LeituraTipo.CELULAR_MOBILE_ENTRADA_SIMULTANEA
									|| rota.getLeituraTipo().getId().intValue() == LeituraTipo.LEITURA_E_ENTRADA_SIMULTANEA){
						colecaoRotasFaturamentoImediato.add(rota);
					}
				}

				Short indicadorGeracaoArquivo = new Short((String) ParametroFaturamento.P_INDICADOR_GERACAO_ARQUIVO_FATURAMENTO_IMEDIATO
								.executar());

				if(indicadorGeracaoArquivo.equals(ConstantesSistema.SIM)){
					// [UC3012 - Gerar Arquivo Texto Faturamento Imediato]
					this.getControladorMicromedicao().gerarArquivoTextoFaturamentoImediato(colecaoRotasFaturamentoImediato, anoMesCorrente,
									idGrupoFaturamento, idFuncionalidadeIniciada);
				}

			}catch(JMSException e){
				System.out.println("Erro no MDB");
				e.printStackTrace();
			}catch(ControladorException e){
				System.out.println("Erro no MDB");
				e.printStackTrace();
			}
		}

	}

	/**
	 * Retorna o valor de controladorMicromedicao
	 * 
	 * @return O valor de controladorMicromedicao
	 */
	private ControladorMicromedicaoLocal getControladorMicromedicao(){

		ControladorMicromedicaoLocalHome localHome = null;
		ControladorMicromedicaoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorMicromedicaoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_MICROMEDICAO_SEJB);
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
