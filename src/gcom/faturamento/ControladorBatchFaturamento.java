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

package gcom.faturamento;

import gcom.cadastro.endereco.ControladorEnderecoLocal;
import gcom.cadastro.endereco.ControladorEnderecoLocalHome;
import gcom.cobranca.bean.IntervaloReferenciaHelper;
import gcom.contabil.ControladorContabilLocal;
import gcom.contabil.ControladorContabilLocalHome;
import gcom.micromedicao.Rota;
import gcom.micromedicao.leitura.LeituraTipo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.*;

import java.math.BigDecimal;
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
 * @ejb.bean name="ControladorBatchFaturamento" display-name="Name for
 *           ControladorBatchFaturamento" description="Description for
 *           ControladorBatchFaturamento" destination-type="javax.jms.Queue"
 *           acknowledge-mode="Auto-acknowledge"
 */
public class ControladorBatchFaturamento
				implements MessageDrivenBean, MessageListener {

	private static final long serialVersionUID = 1L;

	public ControladorBatchFaturamento() {

		super();
		// TODO Auto-generated constructor stub
	}

	public void setMessageDrivenContext(MessageDrivenContext ctx) throws EJBException{

		// TODO Auto-generated method stub

	}

	public void ejbRemove() throws EJBException{

		// TODO Auto-generated method stub

	}

	public void onMessage(Message message){

		if(message instanceof ObjectMessage){

			ObjectMessage objectMessage = (ObjectMessage) message;
			try{
				switch(objectMessage.getIntProperty("nomeMetodo")){
					case (MetodosBatch.ENDERECO_INSERIR_CEP_IMPORTADOS): {
						this.getControladorEndereco().inserirCepImportados((Collection) ((Object[]) objectMessage.getObject())[0]);
						break;

					}
					case (MetodosBatch.GERAR_RELATORIO_MAPA_CONTROLE_CONTA): {
						this.getControladorFaturamento().filtrarMapaControleContaRelatorio(
										(Integer) ((Object[]) objectMessage.getObject())[0],
										(String) ((Object[]) objectMessage.getObject())[1],
										(Usuario) ((Object[]) objectMessage.getObject())[2],
										(String) ((Object[]) objectMessage.getObject())[3],
										(String) ((Object[]) objectMessage.getObject())[4]);
						break;

					}
					case (MetodosBatch.REGISTRAR_FATURAMENTO_IMEDIATO): {
						Collection colecaoRota = (Collection) ((Object[]) objectMessage.getObject())[0];

						Collection<Rota> colecaoRotasFaturamentoImediato = new ArrayList<Rota>();
						Iterator iterator = colecaoRota.iterator();
						while(iterator.hasNext()){
							Rota rota = (Rota) iterator.next();

							if(rota.getLeituraTipo().getId().intValue() == LeituraTipo.CELULAR_MOBILE_ENTRADA_SIMULTANEA
											|| rota.getLeituraTipo().getId().intValue() == LeituraTipo.LEITURA_E_ENTRADA_SIMULTANEA){
								colecaoRotasFaturamentoImediato.add(rota);
							}
						}

						this.getControladorFaturamento().registrarFaturamentoImediatoGrupoFaturamento(colecaoRotasFaturamentoImediato,
										(FaturamentoGrupo) ((Object[]) objectMessage.getObject())[2],
										(Integer) ((Object[]) objectMessage.getObject())[1], null,
										(Integer) ((Object[]) objectMessage.getObject())[3]);
						break;

					}
					case (MetodosBatch.GERAR_ARQUIVO_TEXTO_FATURAMENTO): {
						this.getControladorFaturamento().chamarGerarArquivoTextoFaturamento(
										(Integer) ((Object[]) objectMessage.getObject())[0],
										(String) ((Object[]) objectMessage.getObject())[1],
										(Collection) ((Object[]) objectMessage.getObject())[2]);
						break;
					}
					case (MetodosBatch.AJUSTAR_FATURAMENTO_SERVICOS_VALOR_TRUNCADO_DESO): {

						this.getControladorFaturamento().ajustarFaturamentoServicosRetificarContasErradasDeso(
										(Integer) ((Object[]) objectMessage.getObject())[0],
										(Integer) ((Object[]) objectMessage.getObject())[1]);
						break;

					}
					case (MetodosBatch.AJUSTAR_FATURAMENTO_TARIFAS_DESO): {

						this.getControladorFaturamento().ajustarFaturamentoTarifasDeso((Integer) ((Object[]) objectMessage.getObject())[0],
										(Integer) ((Object[]) objectMessage.getObject())[1]);
						break;

					}
					case (MetodosBatch.CANCELAR_DEBITO_A_COBRAR_AJUSTE_FATURAMENTO_DESO): {

						this.getControladorFaturamento().cancelarDebitosRotinaAjusteFaturamentoDeso();

						break;
					}
					case (MetodosBatch.VERIFICAR_FATURAMENTO_IMOVEIS_COM_SERVICO_COBRADO_ERRADO_DESO_RELACAO_UM_E_DOIS): {

						this.getControladorFaturamento().verificarImoveisComErroRelacao1e2(
										(Collection<FaturamentoGrupo>) ((Object[]) objectMessage.getObject())[0],
										(Integer) ((Object[]) objectMessage.getObject())[1]);
						break;
					}
					case (MetodosBatch.VERIFICAR_FATURAMENTO_IMOVEIS_COM_SERVICO_COBRADO_ERRADO_DESO_RELACAO_CINCO): {

						this.getControladorFaturamento().verificarImoveisComErroRelacao5(
										(Collection<FaturamentoGrupo>) ((Object[]) objectMessage.getObject())[0],
										(Integer) ((Object[]) objectMessage.getObject())[1]);
						break;
					}
					case (MetodosBatch.AJUSTAR_FATURAMENTO_CONTA): {

						this.getControladorFaturamento().executarAjusteConta((Integer) ((Object[]) objectMessage.getObject())[0]);
						break;
					}
					case (MetodosBatch.AJUSTAR_PAGAMENTOS_A_MAIOR): {

						this.getControladorFaturamento().executarAjusteBaixarPagamentosAMaior();
						break;
					}
					case (MetodosBatch.AJUSTAR_CONTA_RETIFICAR): {

						this.getControladorFaturamento().executarAjusteContasRetificar((Integer) ((Object[]) objectMessage.getObject())[0]);
						break;
					}
					case (MetodosBatch.AJUSTAR_CONTABILIDADE_ARRECADACAO_DESO): {

						this.getControladorContabil().ajustarContabilidadeArrecadacaoDeso(
										(Integer) ((Object[]) objectMessage.getObject())[0]);
						break;
					}
					case (MetodosBatch.EXECUTAR_CONVERSAO_CONTAS_PARA_GUIA_DE_PAGAMENTO):{

						this.getControladorContabil().ajustarRegistrosContaEGuia((Integer) ((Object[]) objectMessage.getObject())[0],
										(Integer) ((Object[]) objectMessage.getObject())[1],
										(Collection<IntervaloReferenciaHelper>) ((Object[]) objectMessage.getObject())[2],
										(BigDecimal) ((Object[]) objectMessage.getObject())[3]);
						break;
					}
					case (MetodosBatch.CANCELAR_DEBITOS): {

						this.getControladorFaturamento().executarCancelamentoDebitoACobrarRateioCasal();
						break;
					}
					case (MetodosBatch.AJUSTAR_CONTAS_RETIFICAR_RETIRAR_DEBITO_RATEIO_DUPLICADO):{

						this.getControladorFaturamento().executarAjusteRetificarContasRetirarDebitoRateioDuplicado();
						break;
					}
					case (MetodosBatch.INSERIR_DEBITOS): {

						this.getControladorFaturamento().executarAjusteDebitoACobrar();
						break;
					}
					case (MetodosBatch.GERAR_RESUMO_FATURAMENTO_SIMULACAO_AJUSTE_GRUPO_CASAL):{

						this.getControladorFaturamento().gerarResumoFaturamentoSimulacaoAjusteCasal(
										(FaturamentoGrupo) ((Object[]) objectMessage.getObject())[0],
										(Integer) ((Object[]) objectMessage.getObject())[1],
										(Integer) ((Object[]) objectMessage.getObject())[2]);
						break;
					}
					case (MetodosBatch.DESFAZER_PRE_FATURAMENTO_POR_GRUPO_REF): {

						this.getControladorFaturamento().desfazerPreFaturamentoPorGrupoERef();
						break;
					}

					case (MetodosBatch.AJUSTAR_VALOR_DEBITOS_COBRADOS_CASAL):{

						this.getControladorFaturamento().executarAjusteValorDebitoCobradoCasal(
										(Integer) ((Object[]) objectMessage.getObject())[0]);
						break;
					}

					case (MetodosBatch.AJUSTAR_CONTAS_PRE_FAT):{

						this.getControladorFaturamento().executarAjusteHidrometroInstaladoMeioCicloFaturamento(
										(String) ((Object[]) objectMessage.getObject())[0],
										(Integer) ((Object[]) objectMessage.getObject())[1]);
						break;
					}

					case (MetodosBatch.AJUSTAR_CONTAS_HISTORICO):{

						this.getControladorFaturamento().executarAjusteContasEnviadasHistorico(
										(Integer) ((Object[]) objectMessage.getObject())[0],
										(Integer) ((Object[]) objectMessage.getObject())[1],
										(String[]) ((Object[]) objectMessage.getObject())[2]);
						break;
					}

					case (MetodosBatch.AJUSTAR_CONTAS_HISTORICO_RETIDAS_ZERADAS):{

						this.getControladorFaturamento()
										.executarAjusteContasEnviadasHistoricoPreFaturadasComValorZeroIndicadorEmissaoCampo3(
														(String) ((Object[]) objectMessage.getObject())[0]);
						break;
					}

					case (MetodosBatch.AJUSTAR_COORDENADAS_RA):{

						this.getControladorFaturamento().executarAjusteCoordenadasGIS(
										(Collection) ((Object[]) objectMessage.getObject())[0]);
						break;
					}

					case (MetodosBatch.AJUSTAR_MEDIAS_ERRADAS_CONSUMO_HISTORICO):{

						this.getControladorFaturamento().executarAjusteErroCalculoConsumoMedioPercentualColeta(
										(Collection) ((Object[]) objectMessage.getObject())[0]);
						break;
					}

					case (MetodosBatch.REGERAR_HISTOGRAMA):{

						this.getControladorFaturamento().executarRegeracaoHistograma((Integer) ((Object[]) objectMessage.getObject())[0],
										(Integer) ((Object[]) objectMessage.getObject())[1]);
						break;
					}

					case (MetodosBatch.GERAR_DEBITO_CONTA_COM_VALOR_MENOR):{

						this.getControladorFaturamento().gerarDebitoACobrarContaComValorAMenor(
										(Integer) ((Object[]) objectMessage.getObject())[0],
										(String) ((Object[]) objectMessage.getObject())[1]);
						break;
					}

					case (MetodosBatch.AJUSTAR_FAIXAS_CONTAS_ERRADAS):{

						this.getControladorFaturamento().executarAjusteErroGeracaoContaCategoriaConsumoFaixa(
										(Integer) ((Object[]) objectMessage.getObject())[0],
										(String) ((Object[]) objectMessage.getObject())[1]);
						break;
					}

					case (MetodosBatch.AJUSTE_ENVIAR_CONTAS_ZERADAS_PARA_HISTORICO):{

						this.getControladorFaturamento().executarAjusteContaZeradasEnviarHistorico(
										(Integer) ((Object[]) objectMessage.getObject())[0]);
						break;
					}

					case (MetodosBatch.AJUSTE_BATIMENTO_VALORES_CALCULAR_CONTA):{

						this.getControladorFaturamento().executarAjusteVerificarBatimentoCalculoValorConta(
										(Integer) ((Object[]) objectMessage.getObject())[0]);
						break;
					}

					case (MetodosBatch.AJUSTE_CLIENTE_DEBITO_A_COBRAR):{

						this.getControladorFaturamento().executarAjusteClienteDebitoACobrar();
						break;
					}
					case (MetodosBatch.AJUSTE_CONTAS_LOCALIDADE_062_DESO):{
						this.getControladorFaturamento().refaturarContasDESOLocalidade062(
										(Integer) ((Object[]) objectMessage.getObject())[0]);
						break;
					}
					case (MetodosBatch.AJUSTE_CONVERSAO_ACORDO_TAC):{
						this.getControladorFaturamento().executarAjusteConversaoAcordoTac();
						break;
					}

					case (MetodosBatch.GERAR_CREDITO_A_REALIZAR_AJUSTE):{

						this.getControladorFaturamento().gerarCreditoARealizarAjuste((Integer) ((Object[]) objectMessage.getObject())[0],
										(String) ((Object[]) objectMessage.getObject())[1]);
						break;
					}


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
	 * Retorna o valor de controladorEndereco
	 * 
	 * @return O valor de controladorEndereco
	 */
	private ControladorEnderecoLocal getControladorEndereco(){

		ControladorEnderecoLocalHome localHome = null;
		ControladorEnderecoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorEnderecoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ENDERECO_SEJB);
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
	 * Retorna o valor de controladorLocalidade
	 * 
	 * @return O valor de controladorLocalidade
	 */
	private ControladorFaturamentoLocal getControladorFaturamento(){

		ControladorFaturamentoLocalHome localHome = null;
		ControladorFaturamentoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorFaturamentoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_FATURAMENTO_SEJB);
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

	private ControladorContabilLocal getControladorContabil(){

		ControladorContabilLocalHome localHome = null;
		ControladorContabilLocal local = null;

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorContabilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CONTABIL_SEJB);
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

		// TODO Auto-generated method stub
	}
}
