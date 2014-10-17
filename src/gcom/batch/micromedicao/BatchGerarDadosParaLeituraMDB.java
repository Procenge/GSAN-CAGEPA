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
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
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

package gcom.batch.micromedicao;

import gcom.batch.FiltroFuncionalidadeIniciada;
import gcom.batch.FuncionalidadeIniciada;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.faturamento.ControladorFaturamentoLocal;
import gcom.faturamento.ControladorFaturamentoLocalHome;
import gcom.faturamento.FaturamentoGrupoCronogramaMensal;
import gcom.faturamento.FiltroFaturamentoGrupoCronogramaMensal;
import gcom.micromedicao.ControladorMicromedicaoLocal;
import gcom.micromedicao.ControladorMicromedicaoLocalHome;
import gcom.micromedicao.Rota;
import gcom.micromedicao.leitura.LeituraTipo;
import gcom.util.*;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class BatchGerarDadosParaLeituraMDB
				implements MessageDrivenBean, MessageListener {

	private static final long serialVersionUID = 1L;

	public BatchGerarDadosParaLeituraMDB() {

		super();
	}

	public void setMessageDrivenContext(MessageDrivenContext ctx) throws EJBException{

	}

	public void ejbRemove() throws EJBException{

	}

	/**
	 * @author eduardo henrique
	 * @date 09/12/2008
	 *       Inclusão da geração de relatório de Leitura para Rotas do Tipo Celular.
	 * @author eduardo henrique
	 * @date 06/02/2009
	 *       Alteração na forma de geração das rotas tipo Celular/Mobile para Geração Automatica do
	 *       Relatorio de Leitura Convencional
	 */
	public void onMessage(Message message){

		if(message instanceof ObjectMessage){

			ObjectMessage objectMessage = (ObjectMessage) message;
			try{

				Collection colecaoRota = (Collection) ((Object[]) objectMessage.getObject())[0];

				Integer anoMesCorrente = (Integer) ((Object[]) objectMessage.getObject())[1];
				Integer idGrupoFaturamento = (Integer) ((Object[]) objectMessage.getObject())[2];
				Integer idFuncionalidadeIniciada = (Integer) ((Object[]) objectMessage.getObject())[3];
				Date dataPrevistaExecucaoAtividade = (Date) ((Object[]) objectMessage.getObject())[4];

				// Obtém o usuário da Geração pela Funcionalidade Iniciada
				FiltroFuncionalidadeIniciada filtroFuncionalidade = new FiltroFuncionalidadeIniciada();
				filtroFuncionalidade.adicionarCaminhoParaCarregamentoEntidade("processoIniciado");
				filtroFuncionalidade.adicionarCaminhoParaCarregamentoEntidade("processoFuncionalidade");
				filtroFuncionalidade.adicionarCaminhoParaCarregamentoEntidade("processoFuncionalidade.funcionalidade");
				filtroFuncionalidade.adicionarParametro(new ParametroSimples(FiltroFuncionalidadeIniciada.ID, idFuncionalidadeIniciada));
				Collection colecaoFuncionalidade = getControladorUtil().pesquisar(filtroFuncionalidade,
								FuncionalidadeIniciada.class.getName());
				FuncionalidadeIniciada funcionalidade = (FuncionalidadeIniciada) Util.retonarObjetoDeColecao(colecaoFuncionalidade);

				FiltroFaturamentoGrupoCronogramaMensal filtroFaturamentoGrupoCronogramaMensal = new FiltroFaturamentoGrupoCronogramaMensal();
				filtroFaturamentoGrupoCronogramaMensal.adicionarParametro(new ParametroSimples(
								FiltroFaturamentoGrupoCronogramaMensal.ANO_MES_REFERENCIA, anoMesCorrente));
				filtroFaturamentoGrupoCronogramaMensal.adicionarParametro(new ParametroSimples(
								FiltroFaturamentoGrupoCronogramaMensal.ID_FATURAMENTO_GRUPO, idGrupoFaturamento));
				filtroFaturamentoGrupoCronogramaMensal
								.adicionarCaminhoParaCarregamentoEntidade(FiltroFaturamentoGrupoCronogramaMensal.FATURAMENTO_GRUPO);

				FaturamentoGrupoCronogramaMensal faturamentoGrupoCronogramaMensal = (FaturamentoGrupoCronogramaMensal) Util
								.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroFaturamentoGrupoCronogramaMensal,
												FaturamentoGrupoCronogramaMensal.class.getName()));

				Iterator iterator = colecaoRota.iterator();

				Collection colecaoConvencionalAndMobile = new ArrayList();
				Collection colecaoMicroColetor = new ArrayList();
				Collection colecaoRotasFaturamentoImediato = new ArrayList();

				while(iterator.hasNext()){

					Rota rota = (Rota) iterator.next();

					// Caso o tipo de leitura seja convencional ou celular mobile
					if(rota.getLeituraTipo().getId().intValue() == LeituraTipo.CONVENCIONAL.intValue()
									|| rota.getLeituraTipo().getId().intValue() == LeituraTipo.CELULAR_MOBILE.intValue()){

						colecaoConvencionalAndMobile.add(rota);

					}else if(rota.getLeituraTipo().getId().intValue() == LeituraTipo.MICROCOLETOR.intValue()){

						colecaoMicroColetor.add(rota);
					}else{

						colecaoRotasFaturamentoImediato.add(rota);
					}

				}

				Collection collLeituraTipo = null;

				// [SB0002 - Gerar Relação]
				if(!colecaoConvencionalAndMobile.isEmpty()){

					collLeituraTipo = new ArrayList();
					collLeituraTipo.add(LeituraTipo.CONVENCIONAL);
					collLeituraTipo.add(LeituraTipo.CELULAR_MOBILE);

					if(SistemaParametro.INDICADOR_EMPRESA_DESO.equals(Short.valueOf(ConstantesConfig.getIdEmpresa()))){
						this.getControladorMicromedicao().gerarDadosPorLeituraConvencionalPdf(colecaoConvencionalAndMobile, anoMesCorrente,
										idGrupoFaturamento, funcionalidade, dataPrevistaExecucaoAtividade, collLeituraTipo);
					}else{
						this.getControladorMicromedicao().gerarDadosPorLeituraConvencional(colecaoConvencionalAndMobile, anoMesCorrente,
										idGrupoFaturamento, funcionalidade, dataPrevistaExecucaoAtividade, collLeituraTipo);
					}
				}

				// [SB0001 - Gerar Arquivo Convencional]
				if(!colecaoMicroColetor.isEmpty()){

					collLeituraTipo = new ArrayList();
					collLeituraTipo.add(LeituraTipo.MICROCOLETOR);

					this.getControladorMicromedicao().gerarDadosPorLeituraMicroColetor(colecaoMicroColetor, anoMesCorrente,
									idGrupoFaturamento, funcionalidade, dataPrevistaExecucaoAtividade, collLeituraTipo);
				}

				// [UC0084 - Gerar Faturamento Imediato]
				if(!colecaoRotasFaturamentoImediato.isEmpty()){

					collLeituraTipo = new ArrayList();
					collLeituraTipo.add(LeituraTipo.LEITURA_E_ENTRADA_SIMULTANEA);

					this.getControladorFaturamento().gerarFaturamentoImediato(colecaoRotasFaturamentoImediato, anoMesCorrente,
									idGrupoFaturamento, funcionalidade, collLeituraTipo);
				}

				// Lógica incluida para resolver a falta do encerramento do faturamento em ADA
				this.getControladorFaturamento().atualizarImoveisSituacaoEspecialFaturamentoFinalizadaPorGrupo(anoMesCorrente,
								idGrupoFaturamento);

				// Atualiza a tabela FATURAMENTO_ATIVIDADE_CRONOGRAMA
				this.getControladorFaturamento().atualizarFaturamentoAtividadeCronograma(faturamentoGrupoCronogramaMensal);

				// Enviar notificação de proximidade do encerramento do contrato de demanda de
				// consumo
				this.getControladorFaturamento().enviarEmailImoveisContratoDemandaConsumoAVencer(idGrupoFaturamento, anoMesCorrente);

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

	/**
	 * Retorna o valor de controladorUtil
	 * 
	 * @return O valor de controladorUtil
	 */
	protected ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;

		// pega a instância do ServiceLocator.
		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
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

	// this.enviarMensagemControladorBatch(
	// MetodosBatch.ENDERECO_INSERIR_CEP_IMPORTADOS,
	// ConstantesJNDI.QUEUE_CONTROLADOR_FATURAMENTO_MDB,
	// new Object[] { cepsImportados });

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 */
	public void ejbCreate(){

	}

	/**
	 * Retorna o valor do Controlador Faturamento
	 */
	private ControladorFaturamentoLocal getControladorFaturamento(){

		ControladorFaturamentoLocalHome localHome = null;
		ControladorFaturamentoLocal local = null;

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorFaturamentoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_FATURAMENTO_SEJB);
			local = localHome.create();
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

		return local;
	}

}
