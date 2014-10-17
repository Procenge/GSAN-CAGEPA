/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 * 
 * GSANPCG
 * Vitor Hora
 */

package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * [UC3019] Identificar Cobran�a com Negocia��o
 * RelatorioOcorrenciasProcessoDiarioCobrancaBancaria
 * 
 * @author Carlos Chrystian
 * @date 14/03/2013
 */
public class RelatorioOcorrenciasProcessoDiarioCobrancaBancaria
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioOcorrenciasProcessoDiarioCobrancaBancaria(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_OCORRENCIAS_PROCESSO_DIARIO_COBRANCA_BANCARIA);
	}

	@Deprecated
	public RelatorioOcorrenciasProcessoDiarioCobrancaBancaria() {

		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param bairros
	 *            Description of the Parameter
	 * @param bairroParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */
	public Object executar() throws TarefaException{

		// valor de retorno
		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		Collection<RelatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper> colecao = (Collection<RelatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper>) getParametro("colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancaria");

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		// se a cole��o de par�metros da analise n�o for vazia
		if(colecao != null && !colecao.isEmpty()){
			RelatorioOcorrenciasProcessoDiarioCobrancaBancariaBean relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean = null;
			RelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean = null;
			RelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean = null;
			RelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean = null;

			Collection<RelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosHelper> colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosHelper = null;
			Collection<RelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean> colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean = null;
			Collection<RelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosHelper> colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosHelper = null;
			Collection<RelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean> colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean = null;
			Collection<RelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper> colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper = null;
			Collection<RelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean> colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean = null;

			String descricaoOcorrencia = "";

			for(RelatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper relatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper : colecao){
				// Bean do relat�rio a ser gerado
				relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean = new RelatorioOcorrenciasProcessoDiarioCobrancaBancariaBean();
				descricaoOcorrencia = "";

				switch(relatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper.getOcorrencia().intValue()){
					case 1:
						// Incluir a descri��o da ocorr�ncia no Bean
						descricaoOcorrencia = relatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper.getDescricaoOcorrencia();
						relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean.setDescricaoOcorrencia(descricaoOcorrencia);

						// Recupera a cole��o de boletos do Helper
						colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosHelper = relatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper
										.getColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletos();
						colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean = new ArrayList<RelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean>();

						for(RelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosHelper relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosHelper : colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosHelper){
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean = new RelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean();
							// Inclui o sequencial do boleto no Bean
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean
											.setOcorrenciaSequencialBoleto(relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosHelper
															.getOcorrenciaSequencialBoleto());

							// ---- Recupera os boletos gerados do Helper ----
							colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosHelper = relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosHelper
											.getColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGerados();
							colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean = new ArrayList<RelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean>();

							for(RelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosHelper relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosHelper : colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosHelper){
								relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean = new RelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean();
								// Inclui o boleto geradao no Bean
								String boletoGerado = relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosHelper
												.getBoletoGerado();
								relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean.setBoletoGerado(boletoGerado);

								// Inclui o Bean na cole��o de boletos gerados
								colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean
												.add(relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean);
							}

							// Inclui a cole��o de boletos no Bean
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean
											.setColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean(colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean);

							// Inclui o pedido de baixa do boleto
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean
											.setOcorrenciaPedidoBaixa(relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosHelper
															.getOcorrenciaPedidoBaixa());

							// Inclui a cole��o de beans de boletos na cole��o que ser� incluida no
							// Bean principal
							colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean
											.add(relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean);
						}

						// Incluir a cole��o de beans no Bean principal
						relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean
										.setColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean(colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean);

						break;

					case 2:
						// Incluir a descri��o da ocorr�ncia no Bean
						descricaoOcorrencia = relatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper.getDescricaoOcorrencia();
						relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean.setDescricaoOcorrencia(descricaoOcorrencia);

						// Recupera a cole��o de boletos do Helper
						colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosHelper = relatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper
										.getColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletos();
						colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean = new ArrayList<RelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean>();

						for(RelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosHelper relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosHelper : colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosHelper){
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean = new RelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean();
							// Inclui o sequencial do boleto no Bean
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean
											.setOcorrenciaSequencialBoleto(relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosHelper
															.getOcorrenciaSequencialBoleto());

							// ---- Recupera os boletos gerados do Helper ----
							colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosHelper = relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosHelper
											.getColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGerados();
							colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean = new ArrayList<RelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean>();

							for(RelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosHelper relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosHelper : colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosHelper){
								relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean = new RelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean();
								// Inclui o boleto geradao no Bean
								String boletoGerado = relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosHelper
												.getBoletoGerado();
								relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean.setBoletoGerado(boletoGerado);

								// Inclui o Bean na cole��o de boletos gerados
								colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean
												.add(relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean);
							}

							// Inclui a cole��o de boletos no Bean
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean
											.setColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean(colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean);

							// Inclui o pedido de baixa do boleto
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean
											.setOcorrenciaPedidoBaixa(relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosHelper
															.getOcorrenciaPedidoBaixa());

							// Inclui a cole��o de beans de boletos na cole��o que ser� incluida no
							// Bean principal
							colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean
											.add(relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean);
						}

						// Incluir a cole��o de beans no Bean principal
						relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean
										.setColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean(colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean);

						break;

					case 3:
						// Incluir a descri��o da ocorr�ncia no Bean
						descricaoOcorrencia = relatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper.getDescricaoOcorrencia();
						relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean.setDescricaoOcorrencia(descricaoOcorrencia);

						// ---- Recupera os pedidos de baixa cole��o do Relat�rio Bean ----
						colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper = relatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper
										.getColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixa();
						colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean = new ArrayList<RelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean>();

						for(RelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper : colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper){
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean = new RelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean();
							// Inclui a ocorr�ncia de pedido de baixa no Bean
							String ocorrenciaPedidoBaixa = relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper
											.getOcorrenciaPedidoBaixa();
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean
											.setOcorrenciaPedidoBaixa(ocorrenciaPedidoBaixa);

							colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean
											.add(relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean);
						}

						// Incluir a cole��o de beans no Bean principal
						relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean
										.setColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean(colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean);

						break;

					case 4:
						// Incluir a descri��o da ocorr�ncia no Bean
						descricaoOcorrencia = relatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper.getDescricaoOcorrencia();
						relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean.setDescricaoOcorrencia(descricaoOcorrencia);

						// ---- Recupera os pedidos de baixa cole��o do Relat�rio Bean ----
						colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper = relatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper
										.getColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixa();
						colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean = new ArrayList<RelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean>();

						for(RelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper : colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper){
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean = new RelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean();
							// Inclui a ocorr�ncia de pedido de baixa no Bean
							String ocorrenciaPedidoBaixa = relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper
											.getOcorrenciaPedidoBaixa();
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean
											.setOcorrenciaPedidoBaixa(ocorrenciaPedidoBaixa);

							colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean
											.add(relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean);
						}

						// Incluir a cole��o de beans no Bean principal
						relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean
										.setColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean(colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean);

						break;

					case 5:
						// Incluir a descri��o da ocorr�ncia no Bean
						descricaoOcorrencia = relatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper.getDescricaoOcorrencia();
						relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean.setDescricaoOcorrencia(descricaoOcorrencia);

						// ---- Recupera os pedidos de baixa cole��o do Relat�rio Bean ----
						colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper = relatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper
										.getColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixa();
						colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean = new ArrayList<RelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean>();

						for(RelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper : colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper){
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean = new RelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean();
							// Inclui a ocorr�ncia de pedido de baixa no Bean
							String ocorrenciaPedidoBaixa = relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper
											.getOcorrenciaPedidoBaixa();
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean
											.setOcorrenciaPedidoBaixa(ocorrenciaPedidoBaixa);

							colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean
											.add(relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean);
						}

						// Incluir a cole��o de beans no Bean principal
						relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean
										.setColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean(colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean);

						break;

					default:
						break;
				}

				relatorioBeans.add(relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean);
			}
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_OCORRENCIAS_PROCESSO_DIARIO_COBRANCA_BANCARIA, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_OCORRENCIAS_PROCESSO_DIARIO_COBRANCA_BANCARIA,
							idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioOcorrenciasProcessoDiarioCobrancaBancaria", this);

	}

}