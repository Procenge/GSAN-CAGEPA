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
 * [UC3019] Identificar Cobrança com Negociação
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
	 * < <Descrição do método>>
	 * 
	 * @param bairros
	 *            Description of the Parameter
	 * @param bairroParametros
	 *            Description of the Parameter
	 * @return Descrição do retorno
	 * @exception RelatorioVazioException
	 *                Descrição da exceção
	 */
	public Object executar() throws TarefaException{

		// valor de retorno
		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		Collection<RelatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper> colecao = (Collection<RelatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper>) getParametro("colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancaria");

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		// se a coleção de parâmetros da analise não for vazia
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
				// Bean do relatório a ser gerado
				relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean = new RelatorioOcorrenciasProcessoDiarioCobrancaBancariaBean();
				descricaoOcorrencia = "";

				switch(relatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper.getOcorrencia().intValue()){
					case 1:
						// Incluir a descrição da ocorrência no Bean
						descricaoOcorrencia = relatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper.getDescricaoOcorrencia();
						relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean.setDescricaoOcorrencia(descricaoOcorrencia);

						// Recupera a coleção de boletos do Helper
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

								// Inclui o Bean na coleção de boletos gerados
								colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean
												.add(relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean);
							}

							// Inclui a coleção de boletos no Bean
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean
											.setColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean(colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean);

							// Inclui o pedido de baixa do boleto
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean
											.setOcorrenciaPedidoBaixa(relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosHelper
															.getOcorrenciaPedidoBaixa());

							// Inclui a coleção de beans de boletos na coleção que será incluida no
							// Bean principal
							colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean
											.add(relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean);
						}

						// Incluir a coleção de beans no Bean principal
						relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean
										.setColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean(colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean);

						break;

					case 2:
						// Incluir a descrição da ocorrência no Bean
						descricaoOcorrencia = relatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper.getDescricaoOcorrencia();
						relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean.setDescricaoOcorrencia(descricaoOcorrencia);

						// Recupera a coleção de boletos do Helper
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

								// Inclui o Bean na coleção de boletos gerados
								colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean
												.add(relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean);
							}

							// Inclui a coleção de boletos no Bean
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean
											.setColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean(colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosGeradosBean);

							// Inclui o pedido de baixa do boleto
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean
											.setOcorrenciaPedidoBaixa(relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosHelper
															.getOcorrenciaPedidoBaixa());

							// Inclui a coleção de beans de boletos na coleção que será incluida no
							// Bean principal
							colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean
											.add(relatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean);
						}

						// Incluir a coleção de beans no Bean principal
						relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean
										.setColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean(colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaBoletosBean);

						break;

					case 3:
						// Incluir a descrição da ocorrência no Bean
						descricaoOcorrencia = relatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper.getDescricaoOcorrencia();
						relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean.setDescricaoOcorrencia(descricaoOcorrencia);

						// ---- Recupera os pedidos de baixa coleção do Relatório Bean ----
						colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper = relatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper
										.getColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixa();
						colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean = new ArrayList<RelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean>();

						for(RelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper : colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper){
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean = new RelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean();
							// Inclui a ocorrência de pedido de baixa no Bean
							String ocorrenciaPedidoBaixa = relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper
											.getOcorrenciaPedidoBaixa();
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean
											.setOcorrenciaPedidoBaixa(ocorrenciaPedidoBaixa);

							colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean
											.add(relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean);
						}

						// Incluir a coleção de beans no Bean principal
						relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean
										.setColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean(colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean);

						break;

					case 4:
						// Incluir a descrição da ocorrência no Bean
						descricaoOcorrencia = relatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper.getDescricaoOcorrencia();
						relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean.setDescricaoOcorrencia(descricaoOcorrencia);

						// ---- Recupera os pedidos de baixa coleção do Relatório Bean ----
						colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper = relatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper
										.getColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixa();
						colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean = new ArrayList<RelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean>();

						for(RelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper : colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper){
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean = new RelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean();
							// Inclui a ocorrência de pedido de baixa no Bean
							String ocorrenciaPedidoBaixa = relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper
											.getOcorrenciaPedidoBaixa();
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean
											.setOcorrenciaPedidoBaixa(ocorrenciaPedidoBaixa);

							colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean
											.add(relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean);
						}

						// Incluir a coleção de beans no Bean principal
						relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean
										.setColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean(colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean);

						break;

					case 5:
						// Incluir a descrição da ocorrência no Bean
						descricaoOcorrencia = relatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper.getDescricaoOcorrencia();
						relatorioOcorrenciasProcessoDiarioCobrancaBancariaBean.setDescricaoOcorrencia(descricaoOcorrencia);

						// ---- Recupera os pedidos de baixa coleção do Relatório Bean ----
						colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper = relatorioOcorrenciasProcessoDiarioCobrancaBancariaHelper
										.getColecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixa();
						colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean = new ArrayList<RelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean>();

						for(RelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper : colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper){
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean = new RelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean();
							// Inclui a ocorrência de pedido de baixa no Bean
							String ocorrenciaPedidoBaixa = relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaHelper
											.getOcorrenciaPedidoBaixa();
							relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean
											.setOcorrenciaPedidoBaixa(ocorrenciaPedidoBaixa);

							colecaoRelatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean
											.add(relatorioOcorrenciasProcessoDiarioCobrancaBancariaPedidoBaixaBean);
						}

						// Incluir a coleção de beans no Bean principal
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

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		// adiciona o laudo da análise

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_OCORRENCIAS_PROCESSO_DIARIO_COBRANCA_BANCARIA, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_OCORRENCIAS_PROCESSO_DIARIO_COBRANCA_BANCARIA,
							idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
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