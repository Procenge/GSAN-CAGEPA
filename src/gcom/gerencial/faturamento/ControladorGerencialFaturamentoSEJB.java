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

package gcom.gerencial.faturamento;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.batch.UnidadeProcessamento;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ControladorImovelLocal;
import gcom.cadastro.imovel.ControladorImovelLocalHome;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.imovel.ImovelSubcategoria;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.cobranca.DocumentoTipo;
import gcom.faturamento.FaturamentoSituacaoMotivo;
import gcom.faturamento.FaturamentoSituacaoTipo;
import gcom.faturamento.ResumoFaturamentoSimulacao;
import gcom.faturamento.ResumoFaturamentoSituacaoEspecial;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.gerencial.atendimentopublico.ligacaoagua.GLigacaoAguaPerfil;
import gcom.gerencial.atendimentopublico.ligacaoagua.GLigacaoAguaSituacao;
import gcom.gerencial.atendimentopublico.ligacaoesgoto.GLigacaoEsgotoPerfil;
import gcom.gerencial.atendimentopublico.ligacaoesgoto.GLigacaoEsgotoSituacao;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.gerencial.cadastro.GEmpresa;
import gcom.gerencial.cadastro.cliente.GClienteTipo;
import gcom.gerencial.cadastro.cliente.GEsferaPoder;
import gcom.gerencial.cadastro.imovel.GCategoria;
import gcom.gerencial.cadastro.imovel.GImovelPerfil;
import gcom.gerencial.cadastro.imovel.GSubcategoria;
import gcom.gerencial.cadastro.localidade.GGerenciaRegional;
import gcom.gerencial.cadastro.localidade.GLocalidade;
import gcom.gerencial.cadastro.localidade.GQuadra;
import gcom.gerencial.cadastro.localidade.GSetorComercial;
import gcom.gerencial.cadastro.localidade.GUnidadeNegocio;
import gcom.gerencial.cobranca.GDocumentoTipo;
import gcom.gerencial.cobranca.IRepositorioGerencialCobranca;
import gcom.gerencial.cobranca.RepositorioGerencialCobrancaHBM;
import gcom.gerencial.faturamento.bean.ResumoFaturamentoAguaEsgotoHelper;
import gcom.gerencial.faturamento.bean.ResumoFaturamentoCreditosHelper;
import gcom.gerencial.faturamento.bean.ResumoFaturamentoCreditosSetores;
import gcom.gerencial.faturamento.bean.ResumoFaturamentoDebitosACobrarHelper;
import gcom.gerencial.faturamento.bean.ResumoFaturamentoGuiaPagamentoHelper;
import gcom.gerencial.faturamento.bean.ResumoFaturamentoImpostosHelper;
import gcom.gerencial.faturamento.bean.ResumoFaturamentoOutrosHelper;
import gcom.gerencial.faturamento.bean.ResumoFaturamentoSituacaoEspecialHelper;
import gcom.gerencial.faturamento.bean.ResumoReFaturamentoHelper;
import gcom.gerencial.faturamento.credito.GCreditoOrigem;
import gcom.gerencial.faturamento.credito.GCreditoTipo;
import gcom.gerencial.faturamento.debito.GDebitoTipo;
import gcom.gerencial.financeiro.GFinanciamentoTipo;
import gcom.gerencial.financeiro.lancamento.GLancamentoItemContabil;
import gcom.gerencial.micromedicao.GRota;
import gcom.micromedicao.ControladorMicromedicaoLocal;
import gcom.micromedicao.ControladorMicromedicaoLocalHome;
import gcom.micromedicao.Rota;
import gcom.util.ConstantesJNDI;
import gcom.util.ControladorException;
import gcom.util.ControladorUtilLocal;
import gcom.util.ControladorUtilLocalHome;
import gcom.util.ErroRepositorioException;
import gcom.util.ServiceLocator;
import gcom.util.ServiceLocatorException;
import gcom.util.SistemaException;
import gcom.util.Util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * @author Thiago Toscano
 * @created 19/04/2006
 */
public class ControladorGerencialFaturamentoSEJB
				implements SessionBean {

	private static final long serialVersionUID = 1L;

	private IRepositorioGerencialCobranca repositorioGerencialCobranca = null;

	private IRepositorioGerencialFaturamento repositorioGerencial = null;

	// private IRepositorioUtil repositorioUtil = null;

	private IRepositorioGerencialFaturamento repositorioGerencialFaturamento = null;

	SessionContext sessionContext;

	/**
	 * < <Descrição do método>>
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

		// repositorioUtil = RepositorioUtilHBM.getInstancia();
		repositorioGerencial = RepositorioGerencialFaturamentoHBM.getInstancia();
		repositorioGerencialFaturamento = RepositorioGerencialFaturamentoHBM.getInstancia();
		repositorioGerencialCobranca = RepositorioGerencialCobrancaHBM.getInstancia();
	}

	/**
	 * Retorna o valor do ControladorMicromedicao
	 * 
	 * @author Leonardo Regis
	 * @date 20/07/2006
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
	 * Retorna o valor de controladorImovel
	 * 
	 * @return O valor de controladorImovel
	 */
	private ControladorImovelLocal getControladorImovel(){

		ControladorImovelLocalHome localHome = null;
		ControladorImovelLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorImovelLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_IMOVEL_SEJB);
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

	/**
	 * Retorna o valor de controladorUtil
	 * 
	 * @return O valor de controladorUtil
	 */
	private ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;

		// pega a instáncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
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

	/**
	 * < <Descrição do método>>
	 */
	public void ejbRemove(){

	}

	/**
	 * < <Descrição do método>>
	 */
	public void ejbActivate(){

	}

	/**
	 * < <Descrição do método>>
	 */
	public void ejbPassivate(){

	}

	/**
	 * Seta o valor de sessionContext
	 * 
	 * @param sessionContext
	 *            O novo valor de sessionContext
	 */
	public void setSessionContext(SessionContext sessionContext){

		this.sessionContext = sessionContext;
	}

	/**
	 * CASO DE USO: CONSULTAR RESUMO DE SITUACAO ESPECIAL DE FATURAMENTO AUTOR:
	 * TIAGO MORENO RODRIGUES
	 * DATA: 26/05/2006
	 */

	Collection<ResumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper> resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper = null;

	public Collection<ResumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper> recuperaResumoSituacaoEspecialFaturamento(
					Integer[] idSituacaoTipo, Integer[] idSituacaoMotivo) throws ControladorException{

		// ResumoFaturamentoSituacaoEspecialConsultaFinalHelper RFSEFinal =
		// null;
		try{

			if(idSituacaoTipo != null){
				if(idSituacaoTipo.length == 1 && idSituacaoTipo[0] == 0){
					idSituacaoTipo = null;
				}
			}

			if(idSituacaoMotivo != null){
				if(idSituacaoMotivo.length == 1 && idSituacaoMotivo[0] == -1){
					idSituacaoMotivo = null;
				}
			}

			resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper = this.repositorioGerencialFaturamento
							.pesquisarResumoFaturamentoSituacaoEspecialConsultaGerenciaRegionalHelper(idSituacaoTipo, idSituacaoMotivo);

			for(Iterator iter = resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper.iterator(); iter.hasNext();){

				ResumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper RFSEConsultaGerenciaRegHelper = (ResumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper) iter
								.next();

				Collection<ResumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper> resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper = this.repositorioGerencialFaturamento
								.pesquisarResumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper(
												RFSEConsultaGerenciaRegHelper.getIdGerenciaRegional(), idSituacaoTipo, idSituacaoMotivo);

				RFSEConsultaGerenciaRegHelper
								.setResumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper(resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper);

				BigDecimal totalPercentualGerencia = new BigDecimal("0.00");
				BigDecimal totalFatEstimadoGerencia = new BigDecimal("0.00");
				Integer totalQtLigacoesGerencia = new Integer("0");

				for(ResumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper2 : resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper){

					Collection<ResumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper> resumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper = this.repositorioGerencialFaturamento
									.pesquisarResumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper(
													RFSEConsultaGerenciaRegHelper.getIdGerenciaRegional(),
													resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper2.getIdUnidadeNegocio(),
													idSituacaoTipo, idSituacaoMotivo);

					resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper2
									.setResumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper(resumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper);

					BigDecimal totalPercentualUnidadeNegocio = new BigDecimal("0.00");
					BigDecimal totalFatEstimadoUnidadeNegocio = new BigDecimal("0.00");
					Integer totalQtLigacoesUnidadeNegocio = new Integer("0");

					for(Iterator iterator = resumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper.iterator(); iterator.hasNext();){

						ResumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper RFSEConsultaLocalidadeHelper = (ResumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper) iterator
										.next();

						Collection<ResumoFaturamentoSituacaoEspecialConsultaSitTipoHelper> resumoFaturamentoSituacaoEspecialConsultaSitTipoHelper = this.repositorioGerencialFaturamento
										.pesquisarResumoFaturamentoSituacaoEspecialConsultaSitTipoHelper(
														RFSEConsultaGerenciaRegHelper.getIdGerenciaRegional(),
														RFSEConsultaLocalidadeHelper.getIdLocalidade(), idSituacaoTipo, idSituacaoMotivo);

						RFSEConsultaLocalidadeHelper
										.setResumoFaturamentoSituacaoEspecialConsultaSitTipoHelper(resumoFaturamentoSituacaoEspecialConsultaSitTipoHelper);

						BigDecimal totalPercentualLocalidade = new BigDecimal("0.00");
						BigDecimal totalFatEstimadoLocalidade = new BigDecimal("0.00");
						Integer totalQtLigacoesLocalidade = new Integer("0");

						for(Iterator iterator2 = resumoFaturamentoSituacaoEspecialConsultaSitTipoHelper.iterator(); iterator2.hasNext();){
							ResumoFaturamentoSituacaoEspecialConsultaSitTipoHelper RFSEConsultaSitTipoHelper = (ResumoFaturamentoSituacaoEspecialConsultaSitTipoHelper) iterator2
											.next();
							Collection<ResumoFaturamentoSituacaoEspecialConsultaMotivoHelper> resumoFaturamentoSituacaoEspecialConsultaMotivoHelper = this.repositorioGerencialFaturamento
											.pesquisarResumoFaturamentoSituacaoEspecialConsultaMotivoHelper(
															RFSEConsultaGerenciaRegHelper.getIdGerenciaRegional(),
															RFSEConsultaLocalidadeHelper.getIdLocalidade(),
															RFSEConsultaSitTipoHelper.getIdSituacaoTipo(), idSituacaoTipo, idSituacaoMotivo);
							RFSEConsultaSitTipoHelper
											.setResumoFaturamentoSituacaoEspecialConsultaMotivoHelper(resumoFaturamentoSituacaoEspecialConsultaMotivoHelper);
							BigDecimal totalPercentualSitTipo = new BigDecimal("0.00");
							BigDecimal totalFatEstimadoSitTipo = new BigDecimal("0.00");
							Integer totalQtLigacoesSitTipo = new Integer("0");
							for(Iterator iterator3 = resumoFaturamentoSituacaoEspecialConsultaMotivoHelper.iterator(); iterator3.hasNext();){
								ResumoFaturamentoSituacaoEspecialConsultaMotivoHelper RFSEConsultaMotivoHelper = (ResumoFaturamentoSituacaoEspecialConsultaMotivoHelper) iterator3
												.next();

								// Calculando o Faturamento Estimado por Motivo

								Integer anoMesInicio = RFSEConsultaMotivoHelper.getAnoMesInicio() - 1;
								Integer localidade = RFSEConsultaLocalidadeHelper.getIdLocalidade();
								Collection<ResumoFaturamentoSituacaoEspecialConsultaFatEstimadoHelper> resumoFaturamentoSituacaoEspecialConsultaFatEstimadoHelper = this.repositorioGerencialFaturamento
												.pesquisarResumoFaturamentoSituacaoEspecialConsultaFatEstimadoHelper(anoMesInicio,
																localidade);
								BigDecimal fatEstimado = (BigDecimal) resumoFaturamentoSituacaoEspecialConsultaFatEstimadoHelper.iterator()
												.next().getFaturamentoEstimado().setScale(2, RoundingMode.HALF_UP);
								String fatEstimadoFormatado = Util.formatarMoedaReal(fatEstimado);
								RFSEConsultaMotivoHelper.setFaturamentoEstimado(fatEstimado);
								RFSEConsultaMotivoHelper.setValorFaturamentoEstimadoFormatado(fatEstimadoFormatado);
								if(fatEstimado != null){
									totalFatEstimadoSitTipo = totalFatEstimadoSitTipo.add(fatEstimado);
								}

								// Calculando a Qt de Ligacoes por Motivo
								Integer anoMesInicioReal = RFSEConsultaMotivoHelper.getAnoMesInicio();
								Collection<ResumoFaturamentoSituacaoEspecialConsultaQtLigacoesHelper> resumoFaturamentoSituacaoEspecialConsultaQtLigacoesHelper = this.repositorioGerencialFaturamento
												.pesquisarResumoFaturamentoSituacaoEspecialConsultaQtLigacoesHelper(anoMesInicioReal,
																localidade);
								Integer qtLigacoes = (Integer) resumoFaturamentoSituacaoEspecialConsultaQtLigacoesHelper.iterator().next()
												.getQtLigacoes();
								RFSEConsultaMotivoHelper.setQtLigacoes(qtLigacoes);
								if(qtLigacoes != null){
									totalQtLigacoesSitTipo = totalQtLigacoesSitTipo + qtLigacoes;
								}

								BigDecimal qtParalizada = new BigDecimal(RFSEConsultaMotivoHelper.getQtParalisada());

								// calculando o percentual
								BigDecimal i = new BigDecimal("100");
								BigDecimal percentual = new BigDecimal("0.00");
								if(qtParalizada != null && qtLigacoes != null && qtLigacoes != 0){
									BigDecimal qtLigacoesBigDecimal = new BigDecimal(qtLigacoes);
									// percentual = (qtParalizada.multiply(i));
									// percentual = percentual.divide(qtLigacoesBigDecimal, 2, RoundingMode.HALF_UP);

									percentual = qtParalizada.divide(qtLigacoesBigDecimal, 2, RoundingMode.HALF_UP);
									percentual = percentual.multiply(i);

								}

								RFSEConsultaMotivoHelper.setPercentual(percentual);
								// if (percentual != null) {
								// totalPercentualSitTipo = totalPercentualSitTipo
								// .add(percentual).setScale(2,
								// RoundingMode.HALF_UP);
								// }
							}
							// total fat estimado Situacao Tipo
							RFSEConsultaSitTipoHelper.setTotalFatEstimadoSitTipo(totalFatEstimadoSitTipo);
							totalFatEstimadoLocalidade = totalFatEstimadoLocalidade.add(totalFatEstimadoSitTipo).setScale(2,
											RoundingMode.HALF_UP);

							// total Qt ligacoes
							RFSEConsultaSitTipoHelper.setTotalQtLigacoesSitTipo(totalQtLigacoesSitTipo);
							totalQtLigacoesLocalidade = totalQtLigacoesLocalidade + totalQtLigacoesSitTipo;

							// total percentual Situacao Tipo

							BigDecimal qtParalizadaSitTipo = new BigDecimal(RFSEConsultaSitTipoHelper.getTotalSituacaoTipo());
							BigDecimal i = new BigDecimal("100");
							if(qtParalizadaSitTipo != null && totalQtLigacoesSitTipo != null && totalQtLigacoesSitTipo != 0){
								// totalPercentualSitTipo = (qtParalizadaSitTipo.multiply(i));
								// totalPercentualSitTipo = totalPercentualSitTipo.divide(new BigDecimal(totalQtLigacoesSitTipo), 2,
								// RoundingMode.HALF_UP);

								totalPercentualSitTipo = qtParalizadaSitTipo.divide(new BigDecimal(totalQtLigacoesSitTipo), 2,
												RoundingMode.HALF_UP);
								totalPercentualSitTipo = totalPercentualSitTipo.multiply(i);

							}

							RFSEConsultaSitTipoHelper.setTotalPercentualSitTipo(totalPercentualSitTipo);
							// totalPercentualLocalidade = totalPercentualLocalidade
							// .add(totalPercentualSitTipo).setScale(2,
							// RoundingMode.HALF_UP);

						}
						// total fat estimado Localidade
						RFSEConsultaLocalidadeHelper.setTotalFatEstimadoLocalidade(totalFatEstimadoLocalidade);
						totalFatEstimadoUnidadeNegocio = totalFatEstimadoUnidadeNegocio.add(totalFatEstimadoLocalidade).setScale(2,
										RoundingMode.HALF_UP);

						// total ligacoes Localidade
						RFSEConsultaLocalidadeHelper.setTotalQtLigacoesLocalidade(totalQtLigacoesLocalidade);
						totalQtLigacoesUnidadeNegocio = totalQtLigacoesUnidadeNegocio + totalQtLigacoesLocalidade;

						// total percentual Localidade

						BigDecimal qtParalizadaLocalidade = new BigDecimal(RFSEConsultaLocalidadeHelper.getTotalLocalidade());
						BigDecimal i = new BigDecimal("100");
						if(qtParalizadaLocalidade != null && totalQtLigacoesLocalidade != null && totalQtLigacoesLocalidade != 0){
							// totalPercentualLocalidade = (qtParalizadaLocalidade.multiply(i));
							// totalPercentualLocalidade = totalPercentualLocalidade.divide(new BigDecimal(totalQtLigacoesLocalidade), 2,
							// RoundingMode.HALF_UP);

							totalPercentualLocalidade = qtParalizadaLocalidade.divide(new BigDecimal(totalQtLigacoesLocalidade), 2,
											RoundingMode.HALF_UP);
							totalPercentualLocalidade = totalPercentualLocalidade.multiply(i);

						}

						RFSEConsultaLocalidadeHelper.setTotalPercentualLocalidade(totalPercentualLocalidade);
						// totalPercentualGerencia = totalPercentualGerencia.add(
						// totalPercentualLocalidade).setScale(2,
						// RoundingMode.HALF_UP);
					}


					// Total fat estimado UnidadeNegocio
					resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper2
									.setTotalFatEstimadoUnidadeNegocio(totalFatEstimadoUnidadeNegocio);
					totalFatEstimadoGerencia = totalFatEstimadoGerencia.add(totalFatEstimadoUnidadeNegocio).setScale(2,
									RoundingMode.HALF_UP);

					// Total ligacoes UnidadeNegocio
					resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper2
									.setTotalQtLigacoesUnidadeNegocio(totalQtLigacoesUnidadeNegocio);
					totalQtLigacoesGerencia = totalQtLigacoesGerencia + totalQtLigacoesUnidadeNegocio;

					// Total percentual UnidadeNegocio
					BigDecimal qtParalizadaUnidadeNegocio = new BigDecimal(
									resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper2.getTotalUnidadeNegocio());
					BigDecimal percentual = new BigDecimal("100");
					if(qtParalizadaUnidadeNegocio != null && totalQtLigacoesUnidadeNegocio != null && totalQtLigacoesUnidadeNegocio != 0){
						// totalPercentualUnidadeNegocio = (qtParalizadaUnidadeNegocio.multiply(percentual));
						// totalPercentualUnidadeNegocio = totalPercentualUnidadeNegocio.divide(new BigDecimal(totalQtLigacoesUnidadeNegocio),
						// 2, RoundingMode.HALF_UP);

						totalPercentualUnidadeNegocio = qtParalizadaUnidadeNegocio.divide(new BigDecimal(totalQtLigacoesUnidadeNegocio),
										2, RoundingMode.HALF_UP);
						totalPercentualUnidadeNegocio = totalPercentualUnidadeNegocio.multiply(percentual);
					}

					resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper2
									.setTotalPercentualUnidadeNegocio(totalPercentualUnidadeNegocio);
				}
				// total percentual Gerencia
				RFSEConsultaGerenciaRegHelper.setTotalFatEstimadoGerencia(totalFatEstimadoGerencia);

				// total das ligacoes Gerencia
				RFSEConsultaGerenciaRegHelper.setTotalQtLigacoesGerencia(totalQtLigacoesGerencia);

				// total percentual Gerencia

				BigDecimal qtParalizadaGerencia = new BigDecimal(RFSEConsultaGerenciaRegHelper.getTotalGerenciaRegional());
				BigDecimal i = new BigDecimal("100");
				if(qtParalizadaGerencia != null && totalQtLigacoesGerencia != null && totalQtLigacoesGerencia != 0){
					// totalPercentualGerencia = (qtParalizadaGerencia.multiply(i));
					// totalPercentualGerencia = totalPercentualGerencia.divide(new BigDecimal(totalQtLigacoesGerencia), 2,
					// RoundingMode.HALF_UP);

					totalPercentualGerencia = qtParalizadaGerencia.divide(new BigDecimal(totalQtLigacoesGerencia), 2,
									RoundingMode.HALF_UP);
					totalPercentualGerencia = totalPercentualGerencia.multiply(i);

				}

				RFSEConsultaGerenciaRegHelper.setTotalPercentualGerencia(totalPercentualGerencia);

			}
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		return this.resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper;
	}

	/**
	 * Método que gera o resumo Resumo Situacao Especial Faturamento
	 * [UC0341]
	 * 
	 * @author Thiago Toscano
	 * @date 19/04/2006
	 */
	public void gerarResumoSituacaoEspecialFaturamento(int idLocalidade, int idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;

		// -------------------------
		//
		// Registrar o início do processamento da Unidade de
		// Processamento
		// do Batch
		//
		// -------------------------
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.LOCALIDADE, idLocalidade);

		try{
			this.repositorioGerencial.excluirTodosResumoFaturamentoSituacaoEspecial(idLocalidade);

			List<ResumoFaturamentoSituacaoEspecialHelper> listaSimplificada = new ArrayList();
			List<ResumoFaturamentoSituacaoEspecial> listaResumoLigacoesEconomia = new ArrayList();

			List imoveisResumoLigacaoEconomias = this.repositorioGerencial.getResumoFaturamentoSituacaoEspecialHelper(idLocalidade);
			// pra cada objeto obter a categoria e o indicador de existência de
			// hidrômetro
			// caso ja tenha um igual soma a quantidade de economias e a
			// quantidade de ligacoes
			for(int i = 0; i < imoveisResumoLigacaoEconomias.size(); i++){
				Object obj = imoveisResumoLigacaoEconomias.get(i);

				// if (imoveisResumoLigacaoEconomias != null &&
				// imoveisResumoLigacaoEconomias.get(0) != null) {
				// Object obj = imoveisResumoLigacaoEconomias.get(0);

				if(obj instanceof Object[]){
					Object[] retorno = (Object[]) obj;

					ResumoFaturamentoSituacaoEspecialHelper helper = new ResumoFaturamentoSituacaoEspecialHelper((Integer) retorno[0],
									(Integer) retorno[1], (Integer) retorno[2], (Integer) retorno[3], (Integer) retorno[4],
									(Integer) retorno[5], (Integer) retorno[6], (Integer) retorno[7], (Integer) retorno[8],
									(Integer) retorno[9], (Integer) retorno[10], null, (Integer) retorno[11], (Integer) retorno[12],
									(Integer) retorno[13], (Integer) retorno[14], (Integer) retorno[15], (Integer) retorno[16]);

					Integer idImovel = helper.getIdImovel();

					// pesquisando a categoria
					// [UC0306] - Obtter principal categoria do imóvel
					Categoria categoria = null;
					categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);

					if(categoria != null){
						helper.setIdCategoria(categoria.getId());
					}
					// helper.setIdCategoria(new Integer(1));

					// se ja existe um objeto igual a ele entao soma as ligacoes
					// e as economias no ja existente
					// um objeto eh igual ao outro se ele tem todos as
					// informacos iguals ( excecao idImovel, quantidadeEconomia,
					// quantidadeLigacoes)
					if(listaSimplificada.contains(helper)){
						int posicao = listaSimplificada.indexOf(helper);
						ResumoFaturamentoSituacaoEspecialHelper jaCadastrado = (ResumoFaturamentoSituacaoEspecialHelper) listaSimplificada
										.get(posicao);

						jaCadastrado.setQuantidadeImovel(jaCadastrado.getQuantidadeImovel() + 1);
					}else{
						listaSimplificada.add(helper);
					}
				}
			}

			/**
			 * para todoas as ImovelResumoLigacaoEconomiaHelper cria
			 * ResumoLigacoesEconomia
			 */
			for(int i = 0; i < listaSimplificada.size(); i++){
				ResumoFaturamentoSituacaoEspecialHelper helper = (ResumoFaturamentoSituacaoEspecialHelper) listaSimplificada.get(i);

				// Integer anoMesReferencia = Util
				// .getAnoMesComoInteger(new Date());

				Integer codigoSetorComercial = null;
				if(helper.getCodigoSetorComercial() != null){
					codigoSetorComercial = (helper.getCodigoSetorComercial());
				}

				Integer numeroQuadra = null;
				if(helper.getNumeroQuadra() != null){
					numeroQuadra = (helper.getNumeroQuadra());
				}

				FaturamentoSituacaoTipo faturamentoSituacaoTipo = null;
				if(helper.getIdEspecialFaturamento() != null){
					faturamentoSituacaoTipo = new FaturamentoSituacaoTipo();
					faturamentoSituacaoTipo.setId(helper.getIdEspecialFaturamento());
				}

				FaturamentoSituacaoMotivo faturamentoSituacaoMotivo = null;
				if(helper.getIdMotivoSituacaoEspecialFatauramento() != null){
					faturamentoSituacaoMotivo = new FaturamentoSituacaoMotivo();
					faturamentoSituacaoMotivo.setId(helper.getIdMotivoSituacaoEspecialFatauramento());
				}

				Integer anoMesInicioSituacaoEspecial = null;
				if(helper.getAnoMesInicioSituacaoEspecial() != null){
					anoMesInicioSituacaoEspecial = (helper.getAnoMesInicioSituacaoEspecial());
				}

				Integer anoMesFinalSituacaoEspecial = null;
				if(helper.getAnoMesFinalSituacaoEspecial() != null){
					anoMesFinalSituacaoEspecial = (helper.getAnoMesFinalSituacaoEspecial());
				}

				int quantidadeImovel = (helper.getQuantidadeImovel());

				GerenciaRegional gerenciaRegional = null;
				if(helper.getIdGerenciaRegional() != null){
					gerenciaRegional = new GerenciaRegional();
					gerenciaRegional.setId(helper.getIdGerenciaRegional());
				}

				Localidade localidade = null;
				if(helper.getIdLocalidade() != null){
					localidade = new Localidade();
					localidade.setId(helper.getIdLocalidade());
				}

				SetorComercial setorComercial = null;
				if(helper.getIdSetorComercial() != null){
					setorComercial = new SetorComercial();
					setorComercial.setId(helper.getIdSetorComercial());
				}

				Rota rota = null;
				if(helper.getIdRota() != null){
					rota = new Rota();
					rota.setId(helper.getIdRota());
				}

				Quadra quadra = null;
				if(helper.getIdQuadra() != null){
					quadra = new Quadra();
					quadra.setId(helper.getIdQuadra());
				}

				ImovelPerfil imovelPerfil = null;
				if(helper.getIdPerfilImovel() != null){
					imovelPerfil = new ImovelPerfil();
					imovelPerfil.setId(helper.getIdPerfilImovel());
				}

				LigacaoAguaSituacao ligacaoAguaSituacao = null;
				if(helper.getIdSituacaoLigacaoAgua() != null){
					ligacaoAguaSituacao = new LigacaoAguaSituacao();
					ligacaoAguaSituacao.setId(helper.getIdSituacaoLigacaoAgua());
				}

				LigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;
				if(helper.getIdSituacaoLigacaoEsgoto() != null){
					ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
					ligacaoEsgotoSituacao.setId(helper.getIdSituacaoLigacaoEsgoto());
				}

				Categoria categoria = null;
				if(helper.getIdCategoria() != null){
					categoria = new Categoria();
					categoria.setId(helper.getIdCategoria());
				}

				EsferaPoder esferaPoder = null;
				if(helper.getIdEsfera() != null){
					esferaPoder = new EsferaPoder();
					esferaPoder.setId(helper.getIdEsfera());
				}

				UnidadeNegocio unidadeNegocio = null;
				if(helper.getIdUnidadeNegocio() != null){
					unidadeNegocio = new UnidadeNegocio();
					unidadeNegocio.setId(helper.getIdUnidadeNegocio());
				}

				ResumoFaturamentoSituacaoEspecial resumo = new ResumoFaturamentoSituacaoEspecial(codigoSetorComercial, numeroQuadra,
								anoMesInicioSituacaoEspecial, anoMesFinalSituacaoEspecial, quantidadeImovel, new Date(), gerenciaRegional,
								localidade, setorComercial, rota, quadra, imovelPerfil, ligacaoAguaSituacao, ligacaoEsgotoSituacao,
								categoria, esferaPoder, faturamentoSituacaoTipo, faturamentoSituacaoMotivo, unidadeNegocio);

				// ResumoFaturamentoSituacaoEspecialHelper
				// resumoLigacoesEconomia = new
				// ResumoFaturamentoSituacaoEspecialHelper(anoMesReferencia,
				// codigoSetorComercial, numeroQuadra, indicadorHidrometro,
				// quantidadeLigacoes,
				// quantidadeEconomias, gerenciaRegional, localidade,
				// setorComercial, rota, quadra, imovelPerfil,
				// ligacaoAguaSituacao, ligacaoEsgotoSituacao, categoria,
				// esferaPoder);

				listaResumoLigacoesEconomia.add(resumo);
			}

			this.repositorioGerencial.inserirResumoFaturamentoSituacaoEspecial(listaResumoLigacoesEconomia);

			// --------------------------------------------------------
			//
			// Registrar o fim da execução da Unidade de Processamento
			//
			// --------------------------------------------------------
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception ex){
			// Este catch serve para interceptar qualquer exceção que o processo
			// batch venha a lançar e garantir que a unidade de processamento do
			// batch será atualizada com o erro ocorrido

			ex.printStackTrace();
			// sessionContext.setRollbackOnly();

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);

			throw new EJBException(ex);
		}
	}

	private ControladorBatchLocal getControladorBatch(){

		ControladorBatchLocalHome localHome = null;
		ControladorBatchLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorBatchLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_BATCH_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	/**
	 * Este caso de uso permite consultar o resumo da análise de faturamento,
	 * com a opção de impressão da consulta. Dependendo da opção de totalização
	 * sempre é gerado o relatório, sem a feração da consulta.
	 * [UC0339] Consultar Resumo da Análise Faturamento
	 * consultarResumoAnaliseFaturamento
	 * 
	 * @author Fernanda Paiva
	 * @date 31/05/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ControladorException
	 */
	public List consultarResumoAnaliseFaturamento(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ControladorException{

		try{

			// [FS0001] Verificar existência de dados para o ano/mês de
			// referência retornado
			String resumo = ResumoFaturamentoSimulacao.class.getName();
			Integer countResumoAnalise = repositorioGerencialCobranca.verificarExistenciaAnoMesReferenciaResumo(
							informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia(), resumo);
			if(countResumoAnalise == null || countResumoAnalise == 0){
				throw new ControladorException("atencao.nao_existe_resumo_analise_faturamento", null, Util
								.formatarAnoMesParaMesAno(informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia()));
			}

			List retorno = repositorioGerencialFaturamento.consultarResumoAnaliseFaturamento(informarDadosGeracaoRelatorioConsultaHelper);

			// [FS0007] Nenhum registro encontrado
			if(retorno == null || retorno.isEmpty()){
				throw new ControladorException("atencao.pesquisa.nenhumresultado");
			}

			return retorno;

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Método que gera o resumo do Faturamento
	 * [UC0571] - Gerar Resumo do Faturamento
	 * 
	 * @author Marcio Roberto
	 * @param idLocalidade
	 * @param anoMes
	 * @date 12/05/2007
	 */
	public void gerarResumoFaturamentoAguaEsgoto(int idSetor, int idFuncionalidadeIniciada, int anoMes) throws ControladorException{

		int idUnidadeIniciada = 0;

		// -------------------------
		//
		// Registrar o início do processamento da Unidade de
		// Processamento
		// do Batch
		//
		// -------------------------
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.SETOR_COMERCIAL, idSetor);
		try{
			// Listas de Controle
			List<ResumoFaturamentoAguaEsgotoHelper> listaSimplificadaFaturamentoAguaEsgoto = new ArrayList();
			List<UnResumoFaturamento> listaResumoFaturamentoAguaEsgoto = new ArrayList();
			List<ResumoFaturamentoCreditosSetores> listaSimplificadaFaturamentoCreditosSetores = new ArrayList();
			// Indices da paginacao 1111
			int indice = 0;
			int qtRegistros = 200;
			// flag da paginacao
			boolean flagTerminou = false;
			// contador de paginacao(informativo no debug)
			int count = 0;
			// inicio do processamento
			UnResumoFaturamento resumoFaturamentoAguaEsgotoGrava = null;
			while(!flagTerminou){

				count++;

				List resumo = this.repositorioGerencialFaturamento.getContasResumoFaturamentoAguaEsgoto(idSetor, anoMes, indice,
								qtRegistros);

				// Resumo Faturamento Outros
				// --------------------------------------------------------------------
				this.gerarResumoFaturamentoOutros(idSetor, anoMes, indice, qtRegistros, resumo);

				// Resumo Faturamento Impostos
				// ------------------------------------------------------------------
				this.gerarResumoFaturamentoImpostos(idSetor, anoMes, indice, qtRegistros, resumo);

				// credito
				// --------------------------------------------------------------------------------------
				ResumoFaturamentoCreditosSetores listaSetores = new ResumoFaturamentoCreditosSetores(idSetor);
				if(!listaSimplificadaFaturamentoCreditosSetores.contains(listaSetores)){

					// Resumo Faturamento Guia de Pagamento
					// ---------------------------------------------------------
					this.gerarResumoFaturamentoGuiaPagamento(idSetor, anoMes);

					// Resumo Faturamento Debitos a Cobrar
					// ----------------------------------------------------------
					this.gerarResumoFaturamentoDebitosACobrar(idSetor, anoMes);

					// Resumo Faturamento Creditos
					this.gerarResumoFaturamentoCreditos(idSetor, anoMes, indice, qtRegistros, resumo);

					listaSimplificadaFaturamentoCreditosSetores.add(listaSetores);
				}

				List resumoFaturamentoAguaEsgoto = this.repositorioGerencialFaturamento.getResumoFaturamentoAguaEsgoto(idSetor, anoMes,
								indice, qtRegistros);

				if(qtRegistros > resumoFaturamentoAguaEsgoto.size()){
					flagTerminou = true;
				}else{
					indice = indice + qtRegistros;
				}

				for(int i = 0; i < resumoFaturamentoAguaEsgoto.size(); i++){

					Object obj = resumoFaturamentoAguaEsgoto.get(i);

					System.out.println(count + " / " + i);

					if(obj instanceof Object[]){
						Object[] retorno = (Object[]) obj;

						Integer quantidadeEconomias = new Integer((Short) retorno[23]);
						if(quantidadeEconomias == null){
							quantidadeEconomias = 0;
						}

						Integer idEmpresa = (Integer) retorno[22];
						if(idEmpresa == null){
							idEmpresa = 0;
						}

						// valorAgua
						BigDecimal valorAgua = new BigDecimal(0);
						valorAgua = (BigDecimal) retorno[18];
						if(valorAgua == null){
							valorAgua = new BigDecimal(0);
						}

						// valorEsgoto
						BigDecimal valorEsgoto = new BigDecimal(0);
						valorEsgoto = (BigDecimal) retorno[19];
						if(valorEsgoto == null){
							valorEsgoto = new BigDecimal(0);
						}

						// Consumo Tarifa
						Integer consumoTarifa = (Integer) retorno[24];
						if(consumoTarifa == null){
							consumoTarifa = 0;
						}

						Imovel imovelTemp = new Imovel();
						imovelTemp.setId((Integer) retorno[01]);
						ConsumoTarifa consumo = new ConsumoTarifa();
						consumo.setId(consumoTarifa);
						imovelTemp.setConsumoTarifa(consumo);
						Integer valorMinimo = getControladorMicromedicao().obterConsumoMinimoLigacao(imovelTemp, null);
						// consumoAgua
						Integer consumoAgua = 0;
						if(valorAgua.doubleValue() > 0){
							consumoAgua = (Integer) retorno[16];
							if(consumoAgua == null){
								consumoAgua = 0;
							}
							if(consumoAgua < valorMinimo){
								consumoAgua = valorMinimo;
							}
						}

						// consumoEsgoto
						Integer consumoEsgoto = 0;
						if(valorEsgoto.doubleValue() > 0){
							consumoEsgoto = (Integer) retorno[17];
							if(consumoEsgoto == null){
								consumoEsgoto = 0;
							}
							if(consumoEsgoto < valorMinimo){
								consumoEsgoto = valorMinimo;
							}
						}

						Integer anoMesRef = (Integer) retorno[20];

						// Montamos um objeto de resumo, com as informacoes do
						// retorno
						ResumoFaturamentoAguaEsgotoHelper helper = new ResumoFaturamentoAguaEsgotoHelper((Integer) retorno[1], // Imovel
										(Integer) retorno[2], // Gerencia Regional
										(Integer) retorno[3], // Unidade de negocio
										(Integer) retorno[4], // Elo
										(Integer) retorno[5], // Localidade
										(Integer) retorno[6], // Id Setor Comercial
										(Integer) retorno[7], // id Rota
										(Integer) retorno[8], // Id Quadra
										(Integer) retorno[9], // Codigo do Setor Comercial
										(Integer) retorno[10], // Numero da quadra
										(Integer) retorno[11], // Perfil do imovel
										(Integer) retorno[12], // Situacao da ligacao da agua
										(Integer) retorno[13], // Situacao da ligacao do esgoto
										(Integer) retorno[14], // Perfil da ligacao do agua
										(Integer) retorno[15]);// Perfil da ligacao do esgoto

						// Consumo Tarifa
						helper.setConsumoTarifa(consumoTarifa);

						// Grupo de Faturamento
						Integer grupoFaturamento = (Integer) retorno[25];
						if(grupoFaturamento == null){
							grupoFaturamento = 0;
						}
						helper.setGrupoFaturamento(grupoFaturamento);

						// Pesquisamos a esfera de poder do cliente responsavel
						helper.setIdEsfera(this.repositorioGerencialFaturamento.pesquisarEsferaPoderClienteResponsavelImovel(helper
										.getIdImovel()));
						// Pesquisamos o tipo de cliente responsavel do imovel
						helper.setIdTipoClienteResponsavel(this.repositorioGerencialFaturamento
										.pesquisarTipoClienteClienteResponsavelImovel(helper.getIdImovel()));
						// Empresa
						helper.setGempresa(idEmpresa);

						// pesquisando a categoria
						// [UC0306] - Obtter principal categoria do imóvel
						Integer idImovel = (Integer) retorno[1]; // Codigo do imovel que esta sendo
						// processado
						Categoria categoria = null;
						categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);
						if(categoria != null){
							helper.setIdCategoria(categoria.getId());

							// Pesquisando a principal subcategoria
							ImovelSubcategoria subcategoria = this.getControladorImovel().obterPrincipalSubcategoria(categoria.getId(),
											idImovel);

							if(subcategoria != null){
								helper.setIdSubCategoria(subcategoria.getComp_id().getSubcategoria().getId());
							}
						}

						// [UC0307] - Obter Indicador de Existência de Hidrômetro
						String indicadorHidrometroString = new Integer(getControladorImovel().obterIndicadorExistenciaHidrometroImovel(
										idImovel)).toString();
						Short indicadorHidrometro = new Short(indicadorHidrometroString);
						// Caso indicador de hidrômetro esteja nulo
						// Seta 2(dois) = NÃO no indicador de
						// hidrômetro
						if(indicadorHidrometro == null){
							indicadorHidrometro = new Short("2");
						}
						helper.setIndicadorHidrometro(indicadorHidrometro);

						// Verificamos se a esfera de poder foi encontrada
						// [FS0002] Verificar existencia de cliente responsavel
						if(helper.getIdEsfera().equals(0)){
							Imovel imovel = new Imovel();
							imovel.setId(idImovel);
							Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
							if(clienteTemp != null){
								helper.setIdEsfera(clienteTemp.getClienteTipo().getEsferaPoder().getId());
							}
						}
						if(helper.getIdTipoClienteResponsavel().equals(0)){
							Imovel imovel = new Imovel();
							imovel.setId(idImovel);
							Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
							if(clienteTemp != null){
								helper.setIdTipoClienteResponsavel(clienteTemp.getClienteTipo().getId());
							}
						}

						// se ja existe um objeto igual a ele entao soma os
						// valores e as quantidades ja existentes.
						// um objeto eh igual ao outro se ele tem todos as
						// informacos iguals
						if(listaSimplificadaFaturamentoAguaEsgoto.contains(helper)){
							int posicao = listaSimplificadaFaturamentoAguaEsgoto.indexOf(helper);
							ResumoFaturamentoAguaEsgotoHelper jaCadastrado = (ResumoFaturamentoAguaEsgotoHelper) listaSimplificadaFaturamentoAguaEsgoto
											.get(posicao);
							// Somatorios

							// Consumo Água
							jaCadastrado.setConsumoAgua(jaCadastrado.getConsumoAgua() + consumoAgua);

							// Consumo Esgoto
							jaCadastrado.setConsumoEsgoto(jaCadastrado.getConsumoEsgoto() + consumoEsgoto);

							// Valor Agua
							jaCadastrado.setValorAgua(jaCadastrado.getValorAgua().add(valorAgua));

							// Valor Agua
							jaCadastrado.setValorEsgoto(jaCadastrado.getValorEsgoto().add(valorEsgoto));

							// Quantidade de Contas
							jaCadastrado.setQuantidadeFaturamento(jaCadastrado.getQuantidadeFaturamento() + 1);

							// Quantidade de Economias
							jaCadastrado.setQuantidadeEconomias(jaCadastrado.getQuantidadeEconomias() + quantidadeEconomias);

							// AnoMesReferencia
							jaCadastrado.setAnoMesReferencia(jaCadastrado.getAnoMesReferencia());

						}else{
							// Somatorios

							// Consumo Agua
							helper.setConsumoAgua(consumoAgua); // helper.getConsumoAgua().intValue()
							// +

							// Consumo Esgoto
							helper.setConsumoEsgoto(consumoEsgoto);

							// Valor Agua
							helper.setValorAgua(valorAgua); // helper.getValorAgua().add(valorAgua));

							// Valor Agua
							helper.setValorEsgoto(valorEsgoto); // helper.getValorEsgoto().add());

							// Quantidade Faturamento
							helper.setQuantidadeFaturamento(1); // helper.getQuantidadeFaturamento()+1);

							// Quantidade Faturamento
							helper.setQuantidadeEconomias(quantidadeEconomias); // helper.getQuantidadeFaturamento()+1);

							// AnoMesReferencia
							helper.setAnoMesReferencia(anoMesRef);

							listaSimplificadaFaturamentoAguaEsgoto.add(helper);
						}
					}
				}
			}// do while
			/**
			 * para todas os ResumoFaturamentoAguaEsgotoHelper cria
			 * ResumoFaturamentoAguaEsgoto
			 */
			// for lista simplificada
			System.out.println("inicio inserindo dados");
			System.out.println("======================================================================================");

			for(int i = 0; i < listaSimplificadaFaturamentoAguaEsgoto.size(); i++){
				ResumoFaturamentoAguaEsgotoHelper helper = (ResumoFaturamentoAguaEsgotoHelper) listaSimplificadaFaturamentoAguaEsgoto
								.get(i);

				// Montamos todo o agrupamento necessario
				// Mes ano de referencia
				Integer anoMesReferencia = helper.getAnoMesReferencia();

				// Gerencia regional
				GGerenciaRegional gerenciaRegional = null;
				if(helper.getIdGerenciaRegional() != null){
					gerenciaRegional = new GGerenciaRegional();
					gerenciaRegional.setId(helper.getIdGerenciaRegional());
				}

				// Unidade de Negocio
				GUnidadeNegocio unidadeNegocio = null;
				if(helper.getIdUnidadeNegocio() != null){
					unidadeNegocio = new GUnidadeNegocio();
					unidadeNegocio.setId(helper.getIdUnidadeNegocio());
				}

				// Localidade
				GLocalidade localidade = null;
				if(helper.getIdLocalidade() != null){
					localidade = new GLocalidade();
					localidade.setId(helper.getIdLocalidade());
				}

				// Elo
				GLocalidade elo = null;
				if(helper.getIdElo() != null){
					elo = new GLocalidade();
					elo.setId(helper.getIdElo());
				}

				// Setor comercial
				GSetorComercial setorComercial = null;
				if(helper.getIdSetorComercial() != null){
					setorComercial = new GSetorComercial();
					setorComercial.setId(helper.getIdSetorComercial());
				}

				// Quadra
				GQuadra quadra = null;
				if(helper.getIdQuadra() != null){
					quadra = new GQuadra();
					quadra.setId(helper.getIdQuadra());
				}

				// Codigo do setor comercial
				Integer codigoSetorComercial = null;
				if(helper.getCodigoSetorComercial() != null){
					codigoSetorComercial = (helper.getCodigoSetorComercial());
				}

				// Numero da quadra
				Integer numeroQuadra = null;
				if(helper.getNumeroQuadra() != null){
					numeroQuadra = (helper.getNumeroQuadra());
				}

				// Perfil do imovel
				GImovelPerfil imovelPerfil = null;
				if(helper.getIdPerfilImovel() != null){
					imovelPerfil = new GImovelPerfil();
					imovelPerfil.setId(helper.getIdPerfilImovel());
				}

				// Esfera de poder do cliente responsavel
				GEsferaPoder esferaPoder = null;
				if(helper.getIdEsfera() != null){
					esferaPoder = new GEsferaPoder();
					esferaPoder.setId(helper.getIdEsfera());
				}

				// Tipo do cliente responsavel helper.getIdTipoClienteResponsavel()
				GClienteTipo clienteTipo = null;
				if(helper.getIdTipoClienteResponsavel() != null){
					clienteTipo = new GClienteTipo();
					clienteTipo.setId(1);
				}

				// Situacao da ligacao de agua
				GLigacaoAguaSituacao ligacaoAguaSituacao = null;
				if(helper.getIdSituacaoLigacaoAgua() != null){
					ligacaoAguaSituacao = new GLigacaoAguaSituacao();
					ligacaoAguaSituacao.setId(helper.getIdSituacaoLigacaoAgua());
				}

				// Situacao da ligacao de esgoto
				GLigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;
				if(helper.getIdSituacaoLigacaoEsgoto() != null){
					ligacaoEsgotoSituacao = new GLigacaoEsgotoSituacao();
					ligacaoEsgotoSituacao.setId(helper.getIdSituacaoLigacaoEsgoto());
				}

				// Categoria
				GCategoria categoria = null;
				if(helper.getIdCategoria() != null){
					categoria = new GCategoria();
					categoria.setId(helper.getIdCategoria());
				}

				// Subcategoria
				GSubcategoria subcategoria = null;
				if(helper.getIdSubCategoria() != null){
					subcategoria = new GSubcategoria();
					subcategoria.setId(helper.getIdSubCategoria());
				}

				// Perfil da ligacao de agua
				GLigacaoAguaPerfil perfilLigacaoAgua = null;
				if(helper.getIdPerfilLigacaoAgua() != null){
					perfilLigacaoAgua = new GLigacaoAguaPerfil();
					perfilLigacaoAgua.setId(helper.getIdPerfilLigacaoAgua());
				}

				// Perfil da ligacao de esgoto
				GLigacaoEsgotoPerfil perfilLigacaoEsgoto = null;
				if(helper.getIdPerfilLigacaoEsgoto() != null){
					perfilLigacaoEsgoto = new GLigacaoEsgotoPerfil();
					perfilLigacaoEsgoto.setId(helper.getIdPerfilLigacaoEsgoto());
				}

				// Rota
				GRota rota = null;
				if(helper.getIdRota() != null){
					rota = new GRota();
					rota.setId(helper.getIdRota());
				}

				// Volume Faturado Agua
				Integer volumeFaturadoAgua = helper.getConsumoAgua();
				if(volumeFaturadoAgua == null){
					volumeFaturadoAgua = 0;
				}
				// Volume Faturado Esgoto
				Integer volumeFaturadoEsgoto = helper.getConsumoEsgoto();
				if(volumeFaturadoEsgoto == null){
					volumeFaturadoEsgoto = 0;
				}
				// Valor Faturado Agua
				BigDecimal valorFaturadoAgua = helper.getValorAgua();
				if(valorFaturadoAgua == null){
					valorFaturadoAgua = new BigDecimal(0);
				}
				// Valor Faturado Esgoto
				BigDecimal valorFaturadoEsgoto = helper.getValorEsgoto();
				if(valorFaturadoEsgoto == null){
					valorFaturadoEsgoto = new BigDecimal(0);
				}
				// quantidade de contas emitidas
				Integer quantidadeFaturamento = helper.getQuantidadeFaturamento();

				// quantidade de Economias
				Integer quantidadeEconomias = helper.getQuantidadeEconomias();

				// Ultima Alteracao
				Date ultimaAlteracao = new Date();

				// id Conta
				Integer idConta = helper.getIdConta();

				// Empresa
				GEmpresa empresa = null;
				if(helper.getGempresa() != null){
					empresa = new GEmpresa();
					empresa.setId(helper.getGempresa());
				}

				// Consumo Tarifa
				GConsumoTarifa gerConsumoTarifa = null;
				if(helper.getConsumoTarifa() != null){
					gerConsumoTarifa = new GConsumoTarifa();
					gerConsumoTarifa.setId(helper.getConsumoTarifa());
				}

				// Grupo Faturamento
				GFaturamentoGrupo gerFaturamentoGrupo = null;
				if(helper.getGrupoFaturamento() != null){
					gerFaturamentoGrupo = new GFaturamentoGrupo();
					gerFaturamentoGrupo.setId(helper.getGrupoFaturamento());
				}

				// Tipo Documento
				GDocumentoTipo gerDocumentoTipo = new GDocumentoTipo();
				gerDocumentoTipo.setId(1);

				Short indicadorHidrometro = helper.getIndicadorHidrometro();

				// Criamos um resumo do FaturamentoAguaEsgoto
				resumoFaturamentoAguaEsgotoGrava = new UnResumoFaturamento(anoMesReferencia, gerenciaRegional, unidadeNegocio, localidade,
								setorComercial, quadra, codigoSetorComercial, numeroQuadra, imovelPerfil, esferaPoder, clienteTipo,
								ligacaoAguaSituacao, ligacaoEsgotoSituacao, categoria, subcategoria, perfilLigacaoAgua,
								perfilLigacaoEsgoto, volumeFaturadoAgua, volumeFaturadoEsgoto, valorFaturadoAgua, valorFaturadoEsgoto,
								quantidadeFaturamento, quantidadeEconomias, ultimaAlteracao, elo, rota, empresa, gerConsumoTarifa,
								gerFaturamentoGrupo, indicadorHidrometro, new BigDecimal(0), gerDocumentoTipo);

				// Adicionamos na tabela ResumoFaturamentoOutros
				this.getControladorBatch().inserirObjetoParaBatchGerencial(resumoFaturamentoAguaEsgotoGrava);
				// listaResumoFaturamentoAguaEsgoto.add(resumoFaturamentoAguaEsgotoGrava);
			}// do for lista simplificada
			System.out.println("======================================================================================");
			System.out.println("final montagem dos dados");

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);
		}catch(Exception ex){
			// Este catch serve para interceptar qualquer exceção que o processo
			// batch venha a lançar e garantir que a unidade de processamento do
			// batch será atualizada com o erro ocorrido

			System.out.println(" ERRO NO SETOR " + idSetor + " PROCESSANDO RESUMO FATURAMENTO AGUA ESGOTO");

			ex.printStackTrace();
			sessionContext.setRollbackOnly();

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);

			throw new EJBException(ex);
		}
	}

	// ReFaturamento
	/**
	 * Método que gera o resumo do ReFaturamento
	 * [UC0565] - Gerar Resumo do ReFaturamento
	 * 
	 * @author Marcio Roberto
	 * @param idSetor
	 * @param anoMes
	 * @date 12/06/2007
	 */
	public void gerarResumoReFaturamento(int idSetor, int idFuncionalidadeIniciada, int anoMes) throws ControladorException{

		int idUnidadeIniciada = 0;

		// -------------------------
		//
		// Registrar o início do processamento da Unidade de
		// Processamento
		// do Batch
		//
		// -------------------------
		// idUnidadeIniciada = getControladorBatch()
		// .iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
		// UnidadeProcessamento.SETOR_COMERCIAL, idSetor);

		try{
			// Listas de Controle
			List<ResumoReFaturamentoHelper> listaSimplificadaReFaturamento = new ArrayList();
			List<UnResumoFaturamento> listaResumoFaturamentoAguaEsgoto = new ArrayList();
			// Indices da paginacao
			int indice = 0;
			int qtRegistros = 500;
			// flag da paginacao
			boolean flagTerminou = false;
			// contador de paginacao(informativo no debug)
			int count = 0;
			// inicio do processamento
			while(!flagTerminou){

				count++;
				List resumoFaturamentoAguaEsgoto = this.repositorioGerencialFaturamento.getResumoFaturamentoAguaEsgoto(idSetor, anoMes,
								indice, qtRegistros);
				if(qtRegistros > resumoFaturamentoAguaEsgoto.size()){
					flagTerminou = true;
				}else{
					indice = indice + qtRegistros;
				}

				for(int i = 0; i < resumoFaturamentoAguaEsgoto.size(); i++){

					Object obj = resumoFaturamentoAguaEsgoto.get(i);

					System.out.println(count + " / " + i);

					if(obj instanceof Object[]){
						Object[] retorno = (Object[]) obj;

						Integer tipoConta = (Integer) retorno[21];
						Integer idEmpresa = (Integer) retorno[22];

						if(idEmpresa == null){
							idEmpresa = 0;
						}

						// consumoAgua
						Integer consumoAgua = (Integer) retorno[16];
						if(consumoAgua == null){
							consumoAgua = 0;
						}

						// consumoEsgoto
						Integer consumoEsgoto = (Integer) retorno[17];
						if(consumoEsgoto == null){
							consumoEsgoto = 0;
						}

						// valorAgua
						BigDecimal valorAgua = (BigDecimal) retorno[18];
						if(valorAgua == null){
							valorAgua = new BigDecimal(0);
						}

						// valorEsgoto
						BigDecimal valorEsgoto = (BigDecimal) retorno[19];
						if(valorEsgoto == null){
							valorEsgoto = new BigDecimal(0);
						}

						Integer anoMesRef = (Integer) retorno[20];

						// Montamos um objeto de resumo, com as informacoes do
						// retorno
						ResumoReFaturamentoHelper helper = new ResumoReFaturamentoHelper((Integer) retorno[1], // Imovel
										(Integer) retorno[2], // Gerencia Regional
										(Integer) retorno[3], // Unidade de negocio
										(Integer) retorno[4], // Elo
										(Integer) retorno[5], // Localidade
										(Integer) retorno[6], // Id Setor Comercial
										(Integer) retorno[7], // id Rota
										(Integer) retorno[8], // Id Quadra
										(Integer) retorno[9], // Codigo do Setor Comercial
										(Integer) retorno[10], // Numero da quadra
										(Integer) retorno[11], // Perfil do imovel
										(Integer) retorno[12], // Situacao da ligacao da agua
										(Integer) retorno[13], // Situacao da ligacao do esgoto
										(Integer) retorno[14], // Perfil da ligacao do agua
										(Integer) retorno[15]);// Perfil da ligacao do esgoto

						// id da conta
						helper.setIdConta((Integer) retorno[0]);

						// Pesquisamos a esfera de poder do cliente responsavel
						helper.setIdEsfera(this.repositorioGerencialFaturamento.pesquisarEsferaPoderClienteResponsavelImovel(helper
										.getIdImovel()));
						// Pesquisamos o tipo de cliente responsavel do imovel
						helper.setIdTipoClienteResponsavel(this.repositorioGerencialFaturamento
										.pesquisarTipoClienteClienteResponsavelImovel(helper.getIdImovel()));
						// Empresa
						helper.setGempresa(idEmpresa);

						// pesquisando a categoria
						// [UC0306] - Obtter principal categoria do imóvel
						Integer idImovel = (Integer) retorno[1]; // Codigo do imovel que esta sendo
						// processado
						Categoria categoria = null;
						categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);
						if(categoria != null){
							helper.setIdCategoria(categoria.getId());

							// Pesquisando a principal subcategoria
							ImovelSubcategoria subcategoria = this.getControladorImovel().obterPrincipalSubcategoria(categoria.getId(),
											idImovel);

							if(subcategoria != null){
								helper.setIdSubCategoria(subcategoria.getComp_id().getSubcategoria().getId());
							}
						}

						// Verificamos se a esfera de poder foi encontrada
						// [FS0002] Verificar existencia de cliente responsavel
						if(helper.getIdEsfera().equals(0)){
							Imovel imovel = new Imovel();
							imovel.setId(idImovel);
							Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
							if(clienteTemp != null){
								helper.setIdEsfera(clienteTemp.getClienteTipo().getEsferaPoder().getId());
							}
						}
						if(helper.getIdTipoClienteResponsavel().equals(0)){
							Imovel imovel = new Imovel();
							imovel.setId(idImovel);
							Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
							if(clienteTemp != null){
								helper.setIdTipoClienteResponsavel(clienteTemp.getClienteTipo().getId());
							}
						}

						// se ja existe um objeto igual a ele entao soma os
						// valores e as quantidades ja existentes.
						// um objeto eh igual ao outro se ele tem todos as
						// informacos iguals
						if(listaSimplificadaReFaturamento.contains(helper)){
							int posicao = listaSimplificadaReFaturamento.indexOf(helper);
							ResumoReFaturamentoHelper jaCadastrado = (ResumoReFaturamentoHelper) listaSimplificadaReFaturamento
											.get(posicao);
							// Somatorios

							// Retificada
							if(tipoConta == 1){
								jaCadastrado.setQtContasRetificadas(jaCadastrado.getQtContasRetificadas() + 1);
							}
							// Incluidas
							if(tipoConta == 2){
								jaCadastrado.setQtContasIncluidas(jaCadastrado.getQtContasIncluidas() + 1);
								jaCadastrado.setVlIncluidoAgua(jaCadastrado.getVlIncluidoAgua().add(valorAgua));
								jaCadastrado.setVoIncludoAgua(jaCadastrado.getVoIncludoAgua() + consumoAgua);
							}
							// Canceladas
							if(tipoConta == 3){
								jaCadastrado.setQtContasCanceladas(jaCadastrado.getQtContasCanceladas() + 1);
								jaCadastrado.setVlCanceladoAgua(jaCadastrado.getVlCanceladoAgua().add(valorAgua));
								jaCadastrado.setVoCanceladoAgua(jaCadastrado.getVoCanceladoAgua() + consumoAgua);
							}
							// AnoMesReferencia
							jaCadastrado.setAnoMesReferencia(jaCadastrado.getAnoMesReferencia());
						}else{
							// Somatorios

							// Retificada
							if(tipoConta == 1){
								helper.setQtContasRetificadas(helper.getQtContasRetificadas() + 1);
							}
							// Incluidas
							if(tipoConta == 2){
								helper.setQtContasIncluidas(helper.getQtContasIncluidas() + 1);
								helper.setVlIncluidoAgua(helper.getVlIncluidoAgua().add(valorAgua));
								helper.setVoIncludoAgua(helper.getVoIncludoAgua() + consumoAgua);
							}
							// Canceladas
							if(tipoConta == 3){
								helper.setQtContasCanceladas(helper.getQtContasCanceladas() + 1);
								helper.setVlCanceladoAgua(helper.getVlCanceladoAgua().add(valorAgua));
								helper.setVoCanceladoAgua(helper.getVoCanceladoAgua() + consumoAgua);
							}

							// AnoMesReferencia
							helper.setAnoMesReferencia(anoMesRef);

							listaSimplificadaReFaturamento.add(helper);
						}
						// } catch (Exception e) {
						// e.printStackTrace();
						// }

					}
				}
			}// do while
			/**
			 * para todas os ResumoFaturamentoAguaEsgotoHelper cria
			 * ResumoFaturamentoAguaEsgoto
			 */
			// for lista simplificada
			System.out.println("inicio inserindo dados");
			System.out.println("======================================================================================");
			for(int i = 0; i < listaSimplificadaReFaturamento.size(); i++){
				ResumoReFaturamentoHelper helper = (ResumoReFaturamentoHelper) listaSimplificadaReFaturamento.get(i);

				// Montamos todo o agrupamento necessario
				// Mes ano de referencia
				Integer anoMesReferencia = helper.getAnoMesReferencia();

				// Gerencia regional
				GGerenciaRegional gerenciaRegional = null;
				if(helper.getIdGerenciaRegional() != null){
					gerenciaRegional = new GGerenciaRegional();
					gerenciaRegional.setId(helper.getIdGerenciaRegional());
				}

				// Unidade de Negocio
				GUnidadeNegocio unidadeNegocio = null;
				if(helper.getIdUnidadeNegocio() != null){
					unidadeNegocio = new GUnidadeNegocio();
					unidadeNegocio.setId(helper.getIdUnidadeNegocio());
				}

				// Localidade
				GLocalidade localidade = null;
				if(helper.getIdLocalidade() != null){
					localidade = new GLocalidade();
					localidade.setId(helper.getIdLocalidade());
				}

				// Elo
				GLocalidade elo = null;
				if(helper.getIdElo() != null){
					elo = new GLocalidade();
					elo.setId(helper.getIdElo());
				}

				// Setor comercial
				GSetorComercial setorComercial = null;
				if(helper.getIdSetorComercial() != null){
					setorComercial = new GSetorComercial();
					setorComercial.setId(helper.getIdSetorComercial());
				}

				// Quadra
				GQuadra quadra = null;
				if(helper.getIdQuadra() != null){
					quadra = new GQuadra();
					quadra.setId(helper.getIdQuadra());
				}

				// Codigo do setor comercial
				Integer codigoSetorComercial = null;
				if(helper.getCodigoSetorComercial() != null){
					codigoSetorComercial = (helper.getCodigoSetorComercial());
				}

				// Numero da quadra
				Integer numeroQuadra = null;
				if(helper.getNumeroQuadra() != null){
					numeroQuadra = (helper.getNumeroQuadra());
				}

				// Perfil do imovel
				GImovelPerfil imovelPerfil = null;
				if(helper.getIdPerfilImovel() != null){
					imovelPerfil = new GImovelPerfil();
					imovelPerfil.setId(helper.getIdPerfilImovel());
				}

				// Esfera de poder do cliente responsavel
				GEsferaPoder esferaPoder = null;
				if(helper.getIdEsfera() != null){
					esferaPoder = new GEsferaPoder();
					esferaPoder.setId(helper.getIdEsfera());
				}

				// Tipo do cliente responsavel helper.getIdTipoClienteResponsavel()
				GClienteTipo clienteTipo = null;
				if(helper.getIdTipoClienteResponsavel() != null){
					clienteTipo = new GClienteTipo();
					clienteTipo.setId(1);
				}

				// Situacao da ligacao de agua
				GLigacaoAguaSituacao ligacaoAguaSituacao = null;
				if(helper.getIdSituacaoLigacaoAgua() != null){
					ligacaoAguaSituacao = new GLigacaoAguaSituacao();
					ligacaoAguaSituacao.setId(helper.getIdSituacaoLigacaoAgua());
				}

				// Situacao da ligacao de esgoto
				GLigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;
				if(helper.getIdSituacaoLigacaoEsgoto() != null){
					ligacaoEsgotoSituacao = new GLigacaoEsgotoSituacao();
					ligacaoEsgotoSituacao.setId(helper.getIdSituacaoLigacaoEsgoto());
				}

				// Categoria
				GCategoria categoria = null;
				if(helper.getIdCategoria() != null){
					categoria = new GCategoria();
					categoria.setId(helper.getIdCategoria());
				}

				// Subcategoria
				GSubcategoria subcategoria = null;
				if(helper.getIdSubCategoria() != null){
					subcategoria = new GSubcategoria();
					subcategoria.setId(helper.getIdSubCategoria());
				}

				// Perfil da ligacao de agua
				GLigacaoAguaPerfil perfilLigacaoAgua = null;
				if(helper.getIdPerfilLigacaoAgua() != null){
					perfilLigacaoAgua = new GLigacaoAguaPerfil();
					perfilLigacaoAgua.setId(helper.getIdPerfilLigacaoAgua());
				}

				// Perfil da ligacao de esgoto
				GLigacaoEsgotoPerfil perfilLigacaoEsgoto = null;
				if(helper.getIdPerfilLigacaoEsgoto() != null){
					perfilLigacaoEsgoto = new GLigacaoEsgotoPerfil();
					perfilLigacaoEsgoto.setId(helper.getIdPerfilLigacaoEsgoto());
				}

				// Rota
				GRota rota = null;
				if(helper.getIdRota() != null){
					rota = new GRota();
					rota.setId(helper.getIdRota());
				}

				Integer qtContasRetificadasInserir = helper.getQtContasRetificadas();

				Integer qtContasCanceladasInserir = helper.getQtContasCanceladas();
				BigDecimal vlCanceladoAguaInserir = helper.getVlCanceladoAgua();
				BigDecimal vlCanceladoEsgotoInserir = helper.getVlCanceladoEsgoto();
				Integer voCanceladoAguaInserir = helper.getVoCanceladoAgua();
				Integer voCanceladoEsgotoInserir = helper.getVoCanceladoEsgoto();

				Integer qtContasIncluidasInserir = helper.getQtContasIncluidas();
				BigDecimal vlIncluidasAguaInserir = helper.getVlIncluidoAgua();
				BigDecimal vlIncluidasEsgotoInserir = helper.getVlIncluidoEsgoto();
				Integer voIncluidasAguaInserir = helper.getVoIncludoAgua();
				Integer voIncluidasEsgotoInserir = helper.getVoIncluidoEsgoto();

				// Ultima Alteracao
				Date ultimaAlteracao = new Date();

				// id Conta
				Integer idConta = helper.getIdConta();

				// Criamos um resumo do FaturamentoAguaEsgoto (***UFA***)
				UnResumoRefaturamentoAguaEsgoto resumoReFaturamentoAguaEsgotoGrava = new UnResumoRefaturamentoAguaEsgoto(anoMesReferencia,
								gerenciaRegional, unidadeNegocio, localidade, setorComercial, quadra, setorComercial.getId(), quadra
												.getId(), imovelPerfil, esferaPoder, clienteTipo, ligacaoAguaSituacao,
								ligacaoEsgotoSituacao, categoria, subcategoria, perfilLigacaoAgua, perfilLigacaoEsgoto,
								qtContasRetificadasInserir, qtContasCanceladasInserir, vlCanceladoAguaInserir, vlCanceladoEsgotoInserir,
								voCanceladoAguaInserir, voCanceladoEsgotoInserir, qtContasIncluidasInserir, vlIncluidasAguaInserir,
								vlIncluidasEsgotoInserir, voIncluidasAguaInserir, voIncluidasEsgotoInserir, ultimaAlteracao, elo, rota);

				// Adicionamos na tabela ResumoFaturamentoAguaEsgoto
				this.getControladorBatch().inserirObjetoParaBatchGerencial(resumoReFaturamentoAguaEsgotoGrava);

				Integer idResumoReFaturamentoGerado = (Integer) resumoReFaturamentoAguaEsgotoGrava.getId();

				// ResumoFaturamentoOutros
				List resumoDebitoCobrado = this.repositorioGerencialFaturamento.getPesquisaDebitoCobrado(idConta, 1, 1);

				for(int c = 0; c < resumoDebitoCobrado.size(); c++){
					Object obj = resumoDebitoCobrado.get(c);
					if(obj instanceof Object[]){
						Object[] retorno = (Object[]) obj;

						Integer financiamentoTipo = (Integer) retorno[0];
						if(financiamentoTipo == null){
							financiamentoTipo = 0;
						}

						Integer lictId = (Integer) retorno[1];
						if(lictId == null){
							lictId = 0;
						}

						// Valor dos Documentos Faturados
						BigDecimal valorDocumentosFaturados = (BigDecimal) retorno[2];

						// Quantidade dos Documentos Faturados
						Integer quantidadeDocumentosFaturados = (Integer) retorno[3];

						// Financiamento Tipo
						GFinanciamentoTipo gerFinanciamentoTipo = null;
						if(financiamentoTipo != null){
							gerFinanciamentoTipo = new GFinanciamentoTipo();
							gerFinanciamentoTipo.setId(financiamentoTipo);
						}

						Integer tipoDocumento = DocumentoTipo.CONTA;

						// Lancamento item Contabil
						GLancamentoItemContabil gerLancamentoItemContabil = null;
						if(lictId != null){
							gerLancamentoItemContabil = new GLancamentoItemContabil();
							gerLancamentoItemContabil.setId(lictId);
						}

						// Tipo de Documento
						GDocumentoTipo gerDocumentoTipo = null;
						if(tipoDocumento != null){
							gerDocumentoTipo = new GDocumentoTipo();
							gerDocumentoTipo.setId(tipoDocumento);
						}

						// unResumo Faturamento Agua Esgoto
						UnResumoRefaturamentoAguaEsgoto resumoReFaturamentoAguaEsgotoInsertOutros = null;
						if(idResumoReFaturamentoGerado != null){
							resumoReFaturamentoAguaEsgotoInsertOutros = new UnResumoRefaturamentoAguaEsgoto();
							resumoReFaturamentoAguaEsgotoInsertOutros.setId(idResumoReFaturamentoGerado);
						}
					}
				}

				// ResumoFaturamentoOutros
				List resumoCreditoRealizado = this.repositorioGerencialFaturamento.getPesquisaCreditoRealizado(idConta, idConta);

				// for credito
				for(int c = 0; c < resumoCreditoRealizado.size(); c++){
					Object obj = resumoCreditoRealizado.get(c);
					if(obj instanceof Object[]){
						Object[] retorno = (Object[]) obj;

						// Credito Origem
						Integer creditoOrigem = (Integer) retorno[0];
						if(creditoOrigem == null){
							creditoOrigem = 0;
						}

						// Lancamento Item Contabil
						Integer lictId = (Integer) retorno[1];
						if(lictId == null){
							lictId = 0;
						}

						// Valor dos Documentos Faturados
						BigDecimal valorDocumentosFaturados = (BigDecimal) retorno[2];

						// Quantidade dos Documentos Faturados
						Integer quantidadeDocumentosFaturados = (Integer) retorno[3];

						// unResumo Faturamento Agua Esgoto
						UnResumoRefaturamentoAguaEsgoto resumoReFaturamentoAguaEsgotoInsertOutros = null;
						if(idResumoReFaturamentoGerado != null){
							resumoReFaturamentoAguaEsgotoInsertOutros = new UnResumoRefaturamentoAguaEsgoto();
							resumoReFaturamentoAguaEsgotoInsertOutros.setId(idResumoReFaturamentoGerado);
						}

						// Tipo de Documento
						GCreditoOrigem gCreditoOrigem = null;
						if(creditoOrigem != null){
							gCreditoOrigem = new GCreditoOrigem();
							gCreditoOrigem.setId(creditoOrigem);
						}

						// Lancamento item Contabil
						GLancamentoItemContabil gerLancamentoItemContabil = null;
						if(lictId != null){
							gerLancamentoItemContabil = new GLancamentoItemContabil();
							gerLancamentoItemContabil.setId(lictId);
						}

						UnResumoRefaturamentoCreditoPK unResumoReFaturamentoCreditoPK = new UnResumoRefaturamentoCreditoPK();
						unResumoReFaturamentoCreditoPK.setIdCreditoOrigem(gCreditoOrigem.getId());
						unResumoReFaturamentoCreditoPK.setIdLancamentoItemContabil(gerLancamentoItemContabil.getId());
						unResumoReFaturamentoCreditoPK.setIdResumoRefaturamentoAguaEsgoto(idResumoReFaturamentoGerado);
						// Objeto de resumoFaturamentoCredito
						UnResumoRefaturamentoCredito resumoReFaturamentoCredito = new UnResumoRefaturamentoCredito(
										unResumoReFaturamentoCreditoPK, valorDocumentosFaturados, quantidadeDocumentosFaturados
														.shortValue(), ultimaAlteracao, gerLancamentoItemContabil, gCreditoOrigem,
										resumoReFaturamentoAguaEsgotoInsertOutros);

						System.out.println("Inseriu em credito");
						// Adicionamos na tabela ResumoFaturamentoOutros
						this.getControladorBatch().inserirObjetoParaBatchGerencial(resumoReFaturamentoCredito);
					}
				}// do for credito
			}// do for lista simplificada
			System.out.println("======================================================================================");
			System.out.println("final inserirndo dados");

			// getControladorBatch().encerrarUnidadeProcessamentoBatch(
			// idUnidadeIniciada, false);
		}catch(Exception ex){
			// Este catch serve para interceptar qualquer exceção que o processo
			// batch venha a lançar e garantir que a unidade de processamento do
			// batch será atualizada com o erro ocorrido

			System.out.println(" ERRO NO SETOR " + idSetor);

			ex.printStackTrace();
			sessionContext.setRollbackOnly();

			// getControladorBatch().encerrarUnidadeProcessamentoBatch(
			// idUnidadeIniciada, true);

			throw new EJBException(ex);
		}
	}

	/**
	 * gerarResumoFaturamentoOutros
	 * Marcio Roberto - 03/07/2007
	 * 
	 * @param idSetor
	 * @param anoMes
	 * @param indice
	 * @param qtRegistros
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public void gerarResumoFaturamentoOutros(int idSetor, int anoMes, int indice, int qtRegistros, List resumo)
					throws ControladorException, ErroRepositorioException{

		try{

			System.out.println("processando resumo faturamento outros ");

			List<ResumoFaturamentoOutrosHelper> listaSimplificadaFaturamentoOutros = new ArrayList();
			UnResumoFaturamento resumoFaturamentoAguaEsgotoGrava = null;

			List resumoContasFaturamentoAguaEsgoto = resumo;
			// this.repositorioGerencialFaturamento
			// .getContasResumoFaturamentoAguaEsgoto( idSetor, anoMes, indice, qtRegistros);

			for(int c = 0; c < resumoContasFaturamentoAguaEsgoto.size(); c++){
				Object obj = resumoContasFaturamentoAguaEsgoto.get(c);
				Integer idContaResumo = 0;
				if(obj instanceof Object[]){
					Object[] retorno = (Object[]) obj;
					idContaResumo = (Integer) retorno[0];

					Integer idImovelOutros = (Integer) retorno[1];

					Integer idEmpresaOutros = (Integer) retorno[22];

					// ResumoFaturamentoOutros
					List resumoDebitoCobrado = this.repositorioGerencialFaturamento.getPesquisaDebitoCobrado(idContaResumo, idImovelOutros,
									anoMes);

					for(int y = 0; y < resumoDebitoCobrado.size(); y++){
						Object objOutros = resumoDebitoCobrado.get(y);
						if(obj instanceof Object[]){
							Object[] retornoOutros = (Object[]) objOutros;
							ResumoFaturamentoOutrosHelper helperOutros = new ResumoFaturamentoOutrosHelper((Integer) retorno[1], // Imovel
											(Integer) retorno[2], // Gerencia Regional
											(Integer) retorno[3], // Unidade de negocio
											(Integer) retorno[4], // Elo
											(Integer) retorno[5], // Localidade
											(Integer) retorno[6], // Id Setor Comercial
											(Integer) retorno[7], // id Rota
											(Integer) retorno[8], // Id Quadra
											(Integer) retorno[9], // Codigo do Setor Comercial
											(Integer) retorno[10], // Numero da quadra
											(Integer) retorno[11], // Perfil do imovel
											(Integer) retorno[12], // Situacao da ligacao da agua
											(Integer) retorno[13], // Situacao da ligacao do esgoto
											(Integer) retorno[14], // Perfil da ligacao do agua
											(Integer) retorno[15]);// Perfil da ligacao do esgoto

							Integer anoMesReferencia = (Integer) retorno[20];

							// Consumo Tarifa.
							Integer consumoTarifa = (Integer) retorno[23];
							if(consumoTarifa == null){
								consumoTarifa = 0;
							}
							helperOutros.setConsumoTarifa(consumoTarifa);

							// Grupo de Faturamento
							Integer grupoFaturamento = (Integer) retorno[24];
							if(grupoFaturamento == null){
								grupoFaturamento = 0;
							}
							helperOutros.setGrupoFaturamento(grupoFaturamento);

							// Pesquisamos a esfera de poder do cliente
							helperOutros.setIdEsfera(this.repositorioGerencialFaturamento
											.pesquisarEsferaPoderClienteResponsavelImovel(helperOutros.getIdImovel()));
							// Pesquisamos o tipo de cliente responsavel do
							helperOutros.setIdTipoClienteResponsavel(this.repositorioGerencialFaturamento
											.pesquisarTipoClienteClienteResponsavelImovel(helperOutros.getIdImovel()));
							// Empresa
							helperOutros.setGempresa(idEmpresaOutros);
							// Categoria
							Categoria categoriaOutros = null;
							categoriaOutros = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovelOutros);
							if(categoriaOutros != null){
								helperOutros.setIdCategoria(categoriaOutros.getId());
								// Pesquisando a principal subcategoria
								ImovelSubcategoria subcategoriaOutros = this.getControladorImovel().obterPrincipalSubcategoria(
												categoriaOutros.getId(), idImovelOutros);
								if(subcategoriaOutros != null){
									helperOutros.setIdSubCategoria(subcategoriaOutros.getComp_id().getSubcategoria().getId());
								}
							}
							// Verificamos se a esfera de poder foi encontrada
							if(helperOutros.getIdEsfera().equals(0)){
								Imovel imovel = new Imovel();
								imovel.setId(idImovelOutros);
								Cliente clienteTempOutros = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
								if(clienteTempOutros != null){
									helperOutros.setIdEsfera(clienteTempOutros.getClienteTipo().getEsferaPoder().getId());
								}
							}
							// Verificar existencia de cliente responsavel
							if(helperOutros.getIdTipoClienteResponsavel().equals(0)){
								Imovel imovel = new Imovel();
								imovel.setId(idImovelOutros);
								Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
								if(clienteTemp != null){
									helperOutros.setIdTipoClienteResponsavel(clienteTemp.getClienteTipo().getId());
								}
							}

							// [UC0307] - Obter Indicador de Existência de Hidrômetro
							String indicadorHidrometroString = new Integer(getControladorImovel().obterIndicadorExistenciaHidrometroImovel(
											idImovelOutros)).toString();
							Short indicadorHidrometro = new Short(indicadorHidrometroString);
							// Caso indicador de hidrômetro esteja nulo
							// Seta 2(dois) = NÃO no indicador de
							// hidrômetro
							if(indicadorHidrometro == null){
								indicadorHidrometro = new Short("2");
							}
							helperOutros.setIndicadorHidrometro(indicadorHidrometro);

							// Campos referentes ao "resumoDebitoCobrado"
							// ///////////////////////////////////////
							Integer financiamentoTipo = (Integer) retornoOutros[0];
							if(financiamentoTipo == null){
								financiamentoTipo = 0;
							}
							helperOutros.setIdFinanciamentoTipo(financiamentoTipo);

							Integer documentoTipo = (Integer) retornoOutros[1];
							if(documentoTipo == null){
								documentoTipo = 0;
							}
							helperOutros.setIdDocumentoTipo(documentoTipo);

							Integer lictId = (Integer) retornoOutros[2];
							if(lictId == null){
								lictId = 0;
							}
							helperOutros.setLictId(lictId);

							// Valor dos Documentos Faturados
							BigDecimal valorDocumentosFaturados = (BigDecimal) retornoOutros[3];
							if(valorDocumentosFaturados == null){
								valorDocumentosFaturados = new BigDecimal(0);
							}
							// Quantidade dos Documentos Faturados
							Integer quantidadeDocumentosFaturados = (Integer) retornoOutros[4];
							if(quantidadeDocumentosFaturados == null){
								quantidadeDocumentosFaturados = 0;
							}
							// ///////////////////////////////////////////////////////////////////////////////////
							// informacos iguals
							if(listaSimplificadaFaturamentoOutros.contains(helperOutros)){
								int posicaoOutros = listaSimplificadaFaturamentoOutros.indexOf(helperOutros);
								ResumoFaturamentoOutrosHelper jaCadastradoOutros = (ResumoFaturamentoOutrosHelper) listaSimplificadaFaturamentoOutros
												.get(posicaoOutros);
								// Somatorios

								// documentos faturados
								jaCadastradoOutros.setQuantidadeDocumentosFaturados(jaCadastradoOutros.getQuantidadeDocumentosFaturados()
												+ quantidadeDocumentosFaturados);

								// Valor Documentos faturados
								jaCadastradoOutros.setValorDocumentosFaturados(jaCadastradoOutros.getValorDocumentosFaturados().add(
												valorDocumentosFaturados));
							}else{
								// Somatorios

								// documentos faturados
								helperOutros.setQuantidadeDocumentosFaturados(quantidadeDocumentosFaturados);

								// Valor Documentos faturados
								helperOutros.setValorDocumentosFaturados(valorDocumentosFaturados);

								helperOutros.setAnoMesReferencia(anoMesReferencia);

								listaSimplificadaFaturamentoOutros.add(helperOutros);
							}
						}// if instance of de outros
					}// for de outros
				}// if instance of de contas
			}// for de contas

			for(int i = 0; i < listaSimplificadaFaturamentoOutros.size(); i++){
				ResumoFaturamentoOutrosHelper helper = (ResumoFaturamentoOutrosHelper) listaSimplificadaFaturamentoOutros.get(i);

				// Montamos todo o agrupamento necessario
				// Mes ano de referencia
				Integer anoMesReferencia = helper.getAnoMesReferencia();

				// Gerencia regional
				GGerenciaRegional gerenciaRegional = null;
				if(helper.getIdGerenciaRegional() != null){
					gerenciaRegional = new GGerenciaRegional();
					gerenciaRegional.setId(helper.getIdGerenciaRegional());
				}

				// Unidade de Negocio
				GUnidadeNegocio unidadeNegocio = null;
				if(helper.getIdUnidadeNegocio() != null){
					unidadeNegocio = new GUnidadeNegocio();
					unidadeNegocio.setId(helper.getIdUnidadeNegocio());
				}

				// Localidade
				GLocalidade localidade = null;
				if(helper.getIdLocalidade() != null){
					localidade = new GLocalidade();
					localidade.setId(helper.getIdLocalidade());
				}

				// Elo
				GLocalidade elo = null;
				if(helper.getIdElo() != null){
					elo = new GLocalidade();
					elo.setId(helper.getIdElo());
				}

				// Setor comercial
				GSetorComercial setorComercial = null;
				if(helper.getIdSetorComercial() != null){
					setorComercial = new GSetorComercial();
					setorComercial.setId(helper.getIdSetorComercial());
				}

				// Quadra
				GQuadra quadra = null;
				if(helper.getIdQuadra() != null){
					quadra = new GQuadra();
					quadra.setId(helper.getIdQuadra());
				}

				// Codigo do setor comercial
				Integer codigoSetorComercial = null;
				if(helper.getCodigoSetorComercial() != null){
					codigoSetorComercial = (helper.getCodigoSetorComercial());
				}

				// Numero da quadra
				Integer numeroQuadra = null;
				if(helper.getNumeroQuadra() != null){
					numeroQuadra = (helper.getNumeroQuadra());
				}

				// Perfil do imovel
				GImovelPerfil imovelPerfil = null;
				if(helper.getIdPerfilImovel() != null){
					imovelPerfil = new GImovelPerfil();
					imovelPerfil.setId(helper.getIdPerfilImovel());
				}

				// Esfera de poder do cliente responsavel
				GEsferaPoder esferaPoder = null;
				if(helper.getIdEsfera() != null){
					esferaPoder = new GEsferaPoder();
					esferaPoder.setId(helper.getIdEsfera());
				}

				// Tipo do cliente responsavel helper.getIdTipoClienteResponsavel()
				GClienteTipo clienteTipo = null;
				if(helper.getIdTipoClienteResponsavel() != null){
					clienteTipo = new GClienteTipo();
					clienteTipo.setId(1);
				}

				// Situacao da ligacao de agua
				GLigacaoAguaSituacao ligacaoAguaSituacao = null;
				if(helper.getIdSituacaoLigacaoAgua() != null){
					ligacaoAguaSituacao = new GLigacaoAguaSituacao();
					ligacaoAguaSituacao.setId(helper.getIdSituacaoLigacaoAgua());
				}

				// Situacao da ligacao de esgoto
				GLigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;
				if(helper.getIdSituacaoLigacaoEsgoto() != null){
					ligacaoEsgotoSituacao = new GLigacaoEsgotoSituacao();
					ligacaoEsgotoSituacao.setId(helper.getIdSituacaoLigacaoEsgoto());
				}

				// Categoria
				GCategoria categoria = null;
				if(helper.getIdCategoria() != null){
					categoria = new GCategoria();
					categoria.setId(helper.getIdCategoria());
				}

				// Subcategoria
				GSubcategoria subcategoria = null;
				if(helper.getIdSubCategoria() != null){
					subcategoria = new GSubcategoria();
					subcategoria.setId(helper.getIdSubCategoria());
				}

				// Perfil da ligacao de agua
				GLigacaoAguaPerfil perfilLigacaoAgua = null;
				if(helper.getIdPerfilLigacaoAgua() != null){
					perfilLigacaoAgua = new GLigacaoAguaPerfil();
					perfilLigacaoAgua.setId(helper.getIdPerfilLigacaoAgua());
				}

				// Perfil da ligacao de esgoto
				GLigacaoEsgotoPerfil perfilLigacaoEsgoto = null;
				if(helper.getIdPerfilLigacaoEsgoto() != null){
					perfilLigacaoEsgoto = new GLigacaoEsgotoPerfil();
					perfilLigacaoEsgoto.setId(helper.getIdPerfilLigacaoEsgoto());
				}

				// Rota
				GRota rota = null;
				if(helper.getIdRota() != null){
					rota = new GRota();
					rota.setId(helper.getIdRota());
				}

				// Ultima Alteracao
				Date ultimaAlteracao = new Date();

				// Empresa
				GEmpresa empresa = null;
				if(helper.getGempresa() != null){
					empresa = new GEmpresa();
					empresa.setId(helper.getGempresa());
				}

				// Valor dos Documentos Faturados
				BigDecimal valorDocumentosFaturados = (BigDecimal) helper.getValorDocumentosFaturados();

				// Quantidade dos Documentos Faturados
				Integer quantidadeDocumentosFaturados = helper.getQuantidadeDocumentosFaturados();

				Integer financiamentoTipo = helper.getIdFinanciamentoTipo();
				Integer lictId = helper.getLictId();
				Integer tipoDocumento = helper.getIdDocumentoTipo(); // DocumentoTipo.CONTA;

				// Financiamento Tipo
				GFinanciamentoTipo gerFinanciamentoTipo = null;
				if(financiamentoTipo != null){
					gerFinanciamentoTipo = new GFinanciamentoTipo();
					gerFinanciamentoTipo.setId(financiamentoTipo);
				}

				// Lancamento item Contabil
				GLancamentoItemContabil gerLancamentoItemContabil = null;
				if(lictId != null){
					gerLancamentoItemContabil = new GLancamentoItemContabil();
					gerLancamentoItemContabil.setId(lictId);
				}

				// Tipo de Documento
				GDocumentoTipo gerDocumentoTipo = null;
				if(tipoDocumento != null){
					gerDocumentoTipo = new GDocumentoTipo();
					gerDocumentoTipo.setId(tipoDocumento);
				}

				Short indicadorHidrometro = helper.getIndicadorHidrometro();

				// Consumo Tarifa
				GConsumoTarifa gerConsumoTarifa = null;
				if(helper.getConsumoTarifa() != null){
					gerConsumoTarifa = new GConsumoTarifa();
					gerConsumoTarifa.setId(helper.getConsumoTarifa());
				}

				// Grupo Faturamento
				GFaturamentoGrupo gerFaturamentoGrupo = null;
				if(helper.getGrupoFaturamento() != null){
					gerFaturamentoGrupo = new GFaturamentoGrupo();
					gerFaturamentoGrupo.setId(helper.getGrupoFaturamento());
				}
				// constuctor
				resumoFaturamentoAguaEsgotoGrava = new UnResumoFaturamento(anoMesReferencia, gerenciaRegional, unidadeNegocio, localidade,
								setorComercial, quadra, codigoSetorComercial, numeroQuadra, imovelPerfil, esferaPoder, clienteTipo,
								ligacaoAguaSituacao, ligacaoEsgotoSituacao, categoria, subcategoria, perfilLigacaoAgua,
								perfilLigacaoEsgoto, 0, 0, new BigDecimal(0), new BigDecimal(0), 0, gerDocumentoTipo, gerFinanciamentoTipo,
								gerLancamentoItemContabil, valorDocumentosFaturados, quantidadeDocumentosFaturados.shortValue(),
								ultimaAlteracao, elo, rota, empresa, indicadorHidrometro, new BigDecimal(0), gerConsumoTarifa,
								gerFaturamentoGrupo);

				// Adicionamos na tabela ResumoFaturamentoOutros
				this.getControladorBatch().inserirObjetoParaBatchGerencial(resumoFaturamentoAguaEsgotoGrava);
				System.out.println("gravando objeto de OUTROS");
			}// do for lista simplificada

		}catch(Exception ex){
			System.out.println(" ERRO NO SETOR " + idSetor + " PROCESSANDO RESUMO FATURAMENTO OUTROS");
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new EJBException(ex);
		}
	}

	/**
	 * gerarResumoFaturamentoCreditos
	 * Marcio Roberto - 03/07/2007
	 * 
	 * @param idSetor
	 * @param anoMes
	 * @param indice
	 * @param qtRegistros
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public void gerarResumoFaturamentoCreditos(int idSetor, int anoMes, int indice, int qtRegistros, List resumo)
					throws ControladorException, ErroRepositorioException{

		try{

			System.out.println("processando resumo faturamento creditos ");

			List<ResumoFaturamentoCreditosHelper> listaSimplificadaFaturamentoCreditos = new ArrayList();
			List<ResumoFaturamentoCreditosSetores> listaSimplificadaFaturamentoCreditosSetores = new ArrayList();
			UnResumoFaturamento resumoFaturamentoAguaEsgotoGrava = null;

			List resumoContasFaturamentoAguaEsgoto = resumo;
			// this.repositorioGerencialFaturamento
			// .getContasResumoFaturamentoAguaEsgoto( idSetor, anoMes, indice, qtRegistros);

			for(int c = 0; c < resumoContasFaturamentoAguaEsgoto.size(); c++){
				Object obj = resumoContasFaturamentoAguaEsgoto.get(c);
				Integer idContaResumo = 0;
				if(obj instanceof Object[]){
					Object[] retorno = (Object[]) obj;
					idContaResumo = (Integer) retorno[0];

					Integer idImovelCreditos = (Integer) retorno[1];

					Integer idEmpresaCreditos = (Integer) retorno[22];

					ResumoFaturamentoCreditosSetores listaSetores = new ResumoFaturamentoCreditosSetores(idSetor);
					// 2222
					if(!listaSimplificadaFaturamentoCreditosSetores.contains(listaSetores)){

						// ResumoFaturamentoCreditos
						List resumoCreditoRealizado = this.repositorioGerencialFaturamento.getPesquisaCreditoRealizado(idSetor, anoMes);

						for(int y = 0; y < resumoCreditoRealizado.size(); y++){// 1111
							Object objCreditos = resumoCreditoRealizado.get(y);
							if(obj instanceof Object[]){
								Object[] retornoCreditos = (Object[]) objCreditos;
								ResumoFaturamentoCreditosHelper helperCreditos = new ResumoFaturamentoCreditosHelper((Integer) retorno[1], // Imovel
												(Integer) retorno[2], // Gerencia Regional
												(Integer) retorno[3], // Unidade de negocio
												(Integer) retorno[4], // Elo
												(Integer) retorno[5], // Localidade
												(Integer) retorno[6], // Id Setor Comercial
												(Integer) retorno[7], // id Rota
												(Integer) retorno[8], // Id Quadra
												(Integer) retorno[9], // Codigo do Setor Comercial
												(Integer) retorno[10], // Numero da quadra
												(Integer) retorno[11], // Perfil do imovel
												(Integer) retorno[12], // Situacao da ligacao da
												// agua
												(Integer) retorno[13], // Situacao da ligacao do
												// esgoto
												(Integer) retorno[14], // Perfil da ligacao do agua
												(Integer) retorno[15]);// Perfil da ligacao do
								// esgoto
								System.out.println("***************entrou em credito**********************");
								Integer anoMesReferencia = (Integer) retorno[20];

								// Consumo Tarifa.
								Integer consumoTarifa = (Integer) retorno[23];
								if(consumoTarifa == null){
									consumoTarifa = 0;
								}
								helperCreditos.setConsumoTarifa(consumoTarifa);

								// Grupo de Faturamento
								Integer grupoFaturamento = (Integer) retorno[24];
								if(grupoFaturamento == null){
									grupoFaturamento = 0;
								}
								helperCreditos.setGrupoFaturamento(grupoFaturamento);

								// Pesquisamos a esfera de poder do cliente
								helperCreditos.setIdEsfera(this.repositorioGerencialFaturamento
												.pesquisarEsferaPoderClienteResponsavelImovel(helperCreditos.getIdImovel()));
								// Pesquisamos o tipo de cliente responsavel do
								helperCreditos.setIdTipoClienteResponsavel(this.repositorioGerencialFaturamento
												.pesquisarTipoClienteClienteResponsavelImovel(helperCreditos.getIdImovel()));
								// Empresa
								helperCreditos.setGempresa(idEmpresaCreditos);
								// Categoria
								Categoria categoriaCreditos = null;
								categoriaCreditos = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovelCreditos);
								if(categoriaCreditos != null){
									helperCreditos.setIdCategoria(categoriaCreditos.getId());
									// Pesquisando a principal subcategoria
									ImovelSubcategoria subcategoriaCreditos = this.getControladorImovel().obterPrincipalSubcategoria(
													categoriaCreditos.getId(), idImovelCreditos);
									if(subcategoriaCreditos != null){
										helperCreditos.setIdSubCategoria(subcategoriaCreditos.getComp_id().getSubcategoria().getId());
									}
								}
								// Verificamos se a esfera de poder foi encontrada
								if(helperCreditos.getIdEsfera().equals(0)){
									Imovel imovel = new Imovel();
									imovel.setId(idImovelCreditos);
									Cliente clienteTempCreditos = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
									if(clienteTempCreditos != null){
										helperCreditos.setIdEsfera(clienteTempCreditos.getClienteTipo().getEsferaPoder().getId());
									}
								}
								// Verificar existencia de cliente responsavel
								if(helperCreditos.getIdTipoClienteResponsavel().equals(0)){
									Imovel imovel = new Imovel();
									imovel.setId(idImovelCreditos);
									Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
									if(clienteTemp != null){
										helperCreditos.setIdTipoClienteResponsavel(clienteTemp.getClienteTipo().getId());
									}
								}

								// Campos referentes ao "resumoCreditoRealizado"
								// ///////////////////////////////////////
								// Credito Origem
								Integer creditoOrigem = 0;
								creditoOrigem = (Integer) retornoCreditos[0];
								// if(creditoOrigem == null){
								// creditoOrigem = 0;
								// }
								helperCreditos.setCreditoOrigem(creditoOrigem);

								// TipoCreditoOrigem
								Integer tipoCredito = 0;
								tipoCredito = (Integer) retornoCreditos[4];
								// if(tipoCredito == null){
								// tipoCredito = 0;
								// }
								helperCreditos.setTipoCredito(tipoCredito);

								// Lancamento Item Contabil
								Integer lictId = 0;
								lictId = (Integer) retornoCreditos[1];
								// if(lictId == null){
								// lictId = 0;
								// }
								helperCreditos.setLictId(lictId);

								// [UC0307] - Obter Indicador de Existência de Hidrômetro
								String indicadorHidrometroString = new Integer(getControladorImovel()
												.obterIndicadorExistenciaHidrometroImovel(idImovelCreditos)).toString();
								Short indicadorHidrometro = new Short(indicadorHidrometroString);
								// Caso indicador de hidrômetro esteja nulo
								// Seta 2(dois) = NÃO no indicador de
								// hidrômetro
								if(indicadorHidrometro == null){
									indicadorHidrometro = new Short("2");
								}
								helperCreditos.setIndicadorHidrometro(indicadorHidrometro);

								// Valor dos Documentos Faturados
								BigDecimal valorDocumentosFaturados = new BigDecimal(0);
								valorDocumentosFaturados = (BigDecimal) retornoCreditos[2];

								// Quantidade dos Documentos Faturados
								Integer quantidadeDocumentosFaturados = 0;
								quantidadeDocumentosFaturados = (Integer) retornoCreditos[3];

								// Tipo de Documento
								GCreditoOrigem gCreditoOrigem = null;
								// if(creditoOrigem != null){
								gCreditoOrigem = new GCreditoOrigem();
								gCreditoOrigem.setId(creditoOrigem);
								// }

								// Lancamento item Contabil
								GLancamentoItemContabil gerLancamentoItemContabil = null;
								// if(lictId != null){
								gerLancamentoItemContabil = new GLancamentoItemContabil();
								gerLancamentoItemContabil.setId(lictId);
								// }
								// ///////////////////////////////////////////////////////////////////////////////////
								// informacos iguals
								if(listaSimplificadaFaturamentoCreditos.contains(helperCreditos)){
									int posicaoCreditos = listaSimplificadaFaturamentoCreditos.indexOf(helperCreditos);
									ResumoFaturamentoCreditosHelper jaCadastradoCreditos = (ResumoFaturamentoCreditosHelper) listaSimplificadaFaturamentoCreditos
													.get(posicaoCreditos);
									// Somatorios

									// documentos faturados
									jaCadastradoCreditos.setQuantidadeDocumentosFaturados(jaCadastradoCreditos
													.getQuantidadeDocumentosFaturados()
													+ quantidadeDocumentosFaturados);

									// Valor Documentos faturados
									jaCadastradoCreditos.setValorDocumentosFaturados(jaCadastradoCreditos.getValorDocumentosFaturados()
													.add(valorDocumentosFaturados));
								}else{
									// Somatorios

									// documentos faturados
									helperCreditos.setQuantidadeDocumentosFaturados(quantidadeDocumentosFaturados);

									// Valor Documentos faturados
									helperCreditos.setValorDocumentosFaturados(valorDocumentosFaturados);

									helperCreditos.setAnoMesReferencia(anoMesReferencia);

									listaSimplificadaFaturamentoCreditos.add(helperCreditos);
								}
							}// if instance of de Creditos
						}// for de Creditos
						listaSimplificadaFaturamentoCreditosSetores.add(listaSetores);
					}
				}// if instance of de contas
			}// for de contas

			for(int i = 0; i < listaSimplificadaFaturamentoCreditos.size(); i++){
				ResumoFaturamentoCreditosHelper helper = (ResumoFaturamentoCreditosHelper) listaSimplificadaFaturamentoCreditos.get(i);

				// Montamos todo o agrupamento necessario
				// Mes ano de referencia
				Integer anoMesReferencia = helper.getAnoMesReferencia();

				// Gerencia regional
				GGerenciaRegional gerenciaRegional = null;
				if(helper.getIdGerenciaRegional() != null){
					gerenciaRegional = new GGerenciaRegional();
					gerenciaRegional.setId(helper.getIdGerenciaRegional());
				}

				// Unidade de Negocio
				GUnidadeNegocio unidadeNegocio = null;
				if(helper.getIdUnidadeNegocio() != null){
					unidadeNegocio = new GUnidadeNegocio();
					unidadeNegocio.setId(helper.getIdUnidadeNegocio());
				}

				// Localidade
				GLocalidade localidade = null;
				if(helper.getIdLocalidade() != null){
					localidade = new GLocalidade();
					localidade.setId(helper.getIdLocalidade());
				}

				// Elo
				GLocalidade elo = null;
				if(helper.getIdElo() != null){
					elo = new GLocalidade();
					elo.setId(helper.getIdElo());
				}

				// Setor comercial
				GSetorComercial setorComercial = null;
				if(helper.getIdSetorComercial() != null){
					setorComercial = new GSetorComercial();
					setorComercial.setId(helper.getIdSetorComercial());
				}

				// Quadra
				GQuadra quadra = null;
				if(helper.getIdQuadra() != null){
					quadra = new GQuadra();
					quadra.setId(helper.getIdQuadra());
				}

				// Codigo do setor comercial
				Integer codigoSetorComercial = null;
				if(helper.getCodigoSetorComercial() != null){
					codigoSetorComercial = (helper.getCodigoSetorComercial());
				}

				// Numero da quadra
				Integer numeroQuadra = null;
				if(helper.getNumeroQuadra() != null){
					numeroQuadra = (helper.getNumeroQuadra());
				}

				// Perfil do imovel
				GImovelPerfil imovelPerfil = null;
				if(helper.getIdPerfilImovel() != null){
					imovelPerfil = new GImovelPerfil();
					imovelPerfil.setId(helper.getIdPerfilImovel());
				}

				// Esfera de poder do cliente responsavel
				GEsferaPoder esferaPoder = null;
				if(helper.getIdEsfera() != null){
					esferaPoder = new GEsferaPoder();
					esferaPoder.setId(helper.getIdEsfera());
				}

				// Tipo do cliente responsavel helper.getIdTipoClienteResponsavel()
				GClienteTipo clienteTipo = null;
				if(helper.getIdTipoClienteResponsavel() != null){
					clienteTipo = new GClienteTipo();
					clienteTipo.setId(1);
				}

				// Situacao da ligacao de agua
				GLigacaoAguaSituacao ligacaoAguaSituacao = null;
				if(helper.getIdSituacaoLigacaoAgua() != null){
					ligacaoAguaSituacao = new GLigacaoAguaSituacao();
					ligacaoAguaSituacao.setId(helper.getIdSituacaoLigacaoAgua());
				}

				// Situacao da ligacao de esgoto
				GLigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;
				if(helper.getIdSituacaoLigacaoEsgoto() != null){
					ligacaoEsgotoSituacao = new GLigacaoEsgotoSituacao();
					ligacaoEsgotoSituacao.setId(helper.getIdSituacaoLigacaoEsgoto());
				}

				// Categoria
				GCategoria categoria = null;
				if(helper.getIdCategoria() != null){
					categoria = new GCategoria();
					categoria.setId(helper.getIdCategoria());
				}

				// Subcategoria
				GSubcategoria subcategoria = null;
				if(helper.getIdSubCategoria() != null){
					subcategoria = new GSubcategoria();
					subcategoria.setId(helper.getIdSubCategoria());
				}

				// Perfil da ligacao de agua
				GLigacaoAguaPerfil perfilLigacaoAgua = null;
				if(helper.getIdPerfilLigacaoAgua() != null){
					perfilLigacaoAgua = new GLigacaoAguaPerfil();
					perfilLigacaoAgua.setId(helper.getIdPerfilLigacaoAgua());
				}

				// Perfil da ligacao de esgoto
				GLigacaoEsgotoPerfil perfilLigacaoEsgoto = null;
				if(helper.getIdPerfilLigacaoEsgoto() != null){
					perfilLigacaoEsgoto = new GLigacaoEsgotoPerfil();
					perfilLigacaoEsgoto.setId(helper.getIdPerfilLigacaoEsgoto());
				}

				// Rota
				GRota rota = null;
				if(helper.getIdRota() != null){
					rota = new GRota();
					rota.setId(helper.getIdRota());
				}

				// Ultima Alteracao
				Date ultimaAlteracao = new Date();

				// Empresa
				GEmpresa empresa = null;
				if(helper.getGempresa() != null){
					empresa = new GEmpresa();
					empresa.setId(helper.getGempresa());
				}

				// Valor dos Documentos Faturados
				BigDecimal valorDocumentosFaturados = (BigDecimal) helper.getValorDocumentosFaturados();

				// Quantidade dos Documentos Faturados
				Integer quantidadeDocumentosFaturados = helper.getQuantidadeDocumentosFaturados();

				Integer creditoOrigem = helper.getCreditoOrigem();

				Integer lictId = helper.getLictId();

				// Tipo de Documento
				GCreditoOrigem gCreditoOrigem = null;
				// if(creditoOrigem != null){
				gCreditoOrigem = new GCreditoOrigem();
				gCreditoOrigem.setId(creditoOrigem);
				// }

				Integer creditoTipo = helper.getTipoCredito();

				// Tipo de Documento
				GCreditoTipo gCreditoTipo = null;
				// if(creditoTipo != null){
				gCreditoTipo = new GCreditoTipo();
				gCreditoTipo.setId(creditoTipo);
				// }

				// Lancamento item Contabil
				GLancamentoItemContabil gerLancamentoItemContabil = null;
				// if(lictId != null){
				gerLancamentoItemContabil = new GLancamentoItemContabil();
				gerLancamentoItemContabil.setId(lictId);
				// }

				Short indicadorHidrometro = helper.getIndicadorHidrometro();

				// Consumo Tarifa
				GConsumoTarifa gerConsumoTarifa = null;
				if(helper.getConsumoTarifa() != null){
					gerConsumoTarifa = new GConsumoTarifa();
					gerConsumoTarifa.setId(helper.getConsumoTarifa());
				}

				// Grupo Faturamento
				GFaturamentoGrupo gerFaturamentoGrupo = null;
				if(helper.getGrupoFaturamento() != null){
					gerFaturamentoGrupo = new GFaturamentoGrupo();
					gerFaturamentoGrupo.setId(helper.getGrupoFaturamento());
				}

				GDocumentoTipo gerDocumentoTipo = new GDocumentoTipo();
				gerDocumentoTipo.setId(1);

				resumoFaturamentoAguaEsgotoGrava = new UnResumoFaturamento(anoMesReferencia, gerenciaRegional, unidadeNegocio, localidade,
								setorComercial, quadra, codigoSetorComercial, numeroQuadra, imovelPerfil, esferaPoder, clienteTipo,
								ligacaoAguaSituacao, ligacaoEsgotoSituacao, categoria, subcategoria, perfilLigacaoAgua,
								perfilLigacaoEsgoto, 0, 0, new BigDecimal(0), new BigDecimal(0), 0, gCreditoOrigem,
								gerLancamentoItemContabil, valorDocumentosFaturados, quantidadeDocumentosFaturados, ultimaAlteracao, elo,
								rota, empresa, gCreditoTipo, indicadorHidrometro, new BigDecimal(0), gerConsumoTarifa, gerFaturamentoGrupo,
								gerDocumentoTipo);

				// Adicionamos na tabela ResumoFaturamentoCreditos
				this.getControladorBatch().inserirObjetoParaBatchGerencial(resumoFaturamentoAguaEsgotoGrava);
				System.out.println("gravando objeto de CREDITOS");
			}// do for lista simplificada

		}catch(Exception ex){
			System.out.println(" ERRO NO SETOR " + idSetor + " PROCESSANDO RESUMO FATURAMENTO CREDITOS");
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new EJBException(ex);
		}
	}

	public Collection pesquisarIdsSetores() throws ControladorException{

		try{
			return repositorioGerencialFaturamento.pesquisarIdsSetores();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * gerarResumoFaturamentoDebitoACobrar
	 * Marcio Roberto - 11/07/2007
	 * 
	 * @param idSetor
	 * @param anoMes
	 * @param indice
	 * @param qtRegistros
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public void gerarResumoFaturamentoDebitosACobrar(int idSetor, int anoMes) throws ControladorException, ErroRepositorioException{

		try{

			System.out.println("processando resumo faturamento debitos a cobrar ");

			List<ResumoFaturamentoDebitosACobrarHelper> listaSimplificadaFaturamentoDebitosACobrar = new ArrayList();
			UnResumoFaturamento resumoFaturamentoAguaEsgotoGrava = null;

			List resumoDebitoACobrar = this.repositorioGerencialFaturamento.getPesquisaDebitoACobrar(idSetor, anoMes);

			Integer total = resumoDebitoACobrar.size();
			Integer reg = 0;

			for(int y = 0; y < resumoDebitoACobrar.size(); y++){
				Object objDebitoACobrar = resumoDebitoACobrar.get(y);

				if(objDebitoACobrar instanceof Object[]){
					Object[] retorno = (Object[]) objDebitoACobrar;

					Integer idImovelDebitosACobrar = (Integer) retorno[26];
					System.out.println("processando: " + reg + " de: " + total + " Debitos a cobrar do setor = " + idSetor
									+ "  do  Imovel = " + idImovelDebitosACobrar);
					reg++;
					Integer idEmpresaDebitosACobrar = (Integer) retorno[18];

					ResumoFaturamentoDebitosACobrarHelper helperDebitosACobrar = new ResumoFaturamentoDebitosACobrarHelper(
									(Integer) retorno[26],// Imovel
									(Integer) retorno[3], // Gerencia Regional
									(Integer) retorno[4], // Unidade de negocio
									(Integer) retorno[5], // Elo
									(Integer) retorno[6], // Localidade
									(Integer) retorno[7], // Id Setor Comercial
									(Integer) retorno[8], // id Rota
									(Integer) retorno[9], // Id Quadra
									(Integer) retorno[10], // Codigo do Setor Comercial
									(Integer) retorno[11], // Numero da quadra
									(Integer) retorno[12], // Perfil do imovel
									(Integer) retorno[13], // Situacao da ligacao da agua
									(Integer) retorno[14], // Situacao da ligacao do esgoto
									(Integer) retorno[15], // Perfil da ligacao do agua
									(Integer) retorno[16]);// Perfil da ligacao do esgoto

					Integer anoMesReferencia = anoMes;

					// Consumo Tarifa.
					Integer consumoTarifa = (Integer) retorno[19];
					if(consumoTarifa == null){
						consumoTarifa = 0;
					}
					helperDebitosACobrar.setConsumoTarifa(consumoTarifa);

					// Grupo de Faturamento
					Integer grupoFaturamento = (Integer) retorno[20];
					if(grupoFaturamento == null){
						grupoFaturamento = 0;
					}
					helperDebitosACobrar.setGrupoFaturamento(grupoFaturamento);

					// Pesquisamos a esfera de poder do cliente
					helperDebitosACobrar.setIdEsfera(this.repositorioGerencialFaturamento
									.pesquisarEsferaPoderClienteResponsavelImovel(idImovelDebitosACobrar));

					// Pesquisamos o tipo de cliente responsavel
					helperDebitosACobrar.setIdTipoClienteResponsavel(this.repositorioGerencialFaturamento
									.pesquisarTipoClienteClienteResponsavelImovel(idImovelDebitosACobrar));
					// Empresa
					helperDebitosACobrar.setGempresa(idEmpresaDebitosACobrar);

					// Categoria
					Categoria categoriaDebitosACobrar = null;
					categoriaDebitosACobrar = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovelDebitosACobrar);
					if(categoriaDebitosACobrar != null){
						helperDebitosACobrar.setIdCategoria(categoriaDebitosACobrar.getId());
						// Pesquisando a principal subcategoria
						ImovelSubcategoria subcategoriaDebitosACobrar = this.getControladorImovel().obterPrincipalSubcategoria(
										categoriaDebitosACobrar.getId(), idImovelDebitosACobrar);
						if(subcategoriaDebitosACobrar != null){
							helperDebitosACobrar.setIdSubCategoria(subcategoriaDebitosACobrar.getComp_id().getSubcategoria().getId());
						}
					}
					// Verificamos se a esfera de poder foi encontrada
					if(helperDebitosACobrar.getIdEsfera().equals(0)){
						Imovel imovel = new Imovel();
						imovel.setId(idImovelDebitosACobrar);
						Cliente clienteTempDebitosACobrar = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
						if(clienteTempDebitosACobrar != null){
							helperDebitosACobrar.setIdEsfera(clienteTempDebitosACobrar.getClienteTipo().getEsferaPoder().getId());
						}
					}

					// Verificar existencia de cliente responsavel
					if(helperDebitosACobrar.getIdTipoClienteResponsavel().equals(0)){
						Imovel imovel = new Imovel();
						imovel.setId(idImovelDebitosACobrar);
						Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
						if(clienteTemp != null){
							helperDebitosACobrar.setIdTipoClienteResponsavel(clienteTemp.getClienteTipo().getId());
						}
					}

					// [UC0307] - Obter Indicador de Existência de Hidrômetro
					String indicadorHidrometroString = new Integer(getControladorImovel().obterIndicadorExistenciaHidrometroImovel(
									idImovelDebitosACobrar)).toString();
					Short indicadorHidrometro = new Short(indicadorHidrometroString);
					// Caso indicador de hidrômetro esteja nulo
					// Seta 2(dois) = NÃO no indicador de
					// hidrômetro
					if(indicadorHidrometro == null){
						indicadorHidrometro = new Short("2");
					}
					helperDebitosACobrar.setIndicadorHidrometro(indicadorHidrometro);

					// Campos referentes ao "resumoDebitoCobrado"
					// ///////////////////////////////////////
					Integer financiamentoTipo = (Integer) retorno[0];
					if(financiamentoTipo == null){
						financiamentoTipo = 0;
					}
					helperDebitosACobrar.setIdFinanciamentoTipo(financiamentoTipo);

					Integer lictId = (Integer) retorno[2];
					if(lictId == null){
						lictId = 0;
					}
					helperDebitosACobrar.setLictId(lictId);

					Integer documentoTipo = (Integer) retorno[1];
					if(documentoTipo == null){
						documentoTipo = 0;
					}
					helperDebitosACobrar.setIdDocumentoTipo(documentoTipo);

					Integer debitoTipo = (Integer) retorno[25];
					if(debitoTipo == null){
						debitoTipo = 0;
					}
					helperDebitosACobrar.setDebitoTipo(debitoTipo);

					// Valor dos Financiamentos Incluidos Debitos a Cobrar
					BigDecimal valorFinanciamentosIncluidos = (BigDecimal) retorno[21];
					if(valorFinanciamentosIncluidos == null){
						valorFinanciamentosIncluidos = new BigDecimal(0);
					}

					// Valor dos Financiamentos Cancelados Debitos a Cobrar
					BigDecimal valorFinanciamentosCancelados = (BigDecimal) retorno[23];
					if(valorFinanciamentosCancelados == null){
						valorFinanciamentosCancelados = new BigDecimal(0);
					}

					// ///////////////////////////////////////////////////////////////////////////////////
					// informacos iguals
					if(listaSimplificadaFaturamentoDebitosACobrar.contains(helperDebitosACobrar)){
						int posicaoDebitosACobrar = listaSimplificadaFaturamentoDebitosACobrar.indexOf(helperDebitosACobrar);
						ResumoFaturamentoDebitosACobrarHelper jaCadastradoOutros = (ResumoFaturamentoDebitosACobrarHelper) listaSimplificadaFaturamentoDebitosACobrar
										.get(posicaoDebitosACobrar);

						// Valor dos Financiamentos Incluidos Debitos a Cobrar
						jaCadastradoOutros.setValorFinanciamentosIncluidos(jaCadastradoOutros.getValorFinanciamentosIncluidos().add(
										valorFinanciamentosIncluidos));

						// Valor dos Financiamentos Cancelados Debitos a Cobrar
						jaCadastradoOutros.setValorFinanciamentosCancelados(jaCadastradoOutros.getValorFinanciamentosCancelados().add(
										valorFinanciamentosCancelados));

					}else{
						// Somatorios

						// Valor dos Financiamentos Incluidos Debitos a Cobrar
						helperDebitosACobrar.setValorFinanciamentosIncluidos(valorFinanciamentosIncluidos);

						// Valor dos Financiamentos Cancelados Debitos a Cobrar
						helperDebitosACobrar.setValorFinanciamentosCancelados(valorFinanciamentosCancelados);

						helperDebitosACobrar.setAnoMesReferencia(anoMesReferencia);

						listaSimplificadaFaturamentoDebitosACobrar.add(helperDebitosACobrar);
					}
				}// if instance of de debito a cobrar
			}// for de debito a cobrar

			for(int i = 0; i < listaSimplificadaFaturamentoDebitosACobrar.size(); i++){
				ResumoFaturamentoDebitosACobrarHelper helper = (ResumoFaturamentoDebitosACobrarHelper) listaSimplificadaFaturamentoDebitosACobrar
								.get(i);

				// Montamos todo o agrupamento necessario
				// Mes ano de referencia
				Integer anoMesReferencia = helper.getAnoMesReferencia();

				// Gerencia regional
				GGerenciaRegional gerenciaRegional = null;
				if(helper.getIdGerenciaRegional() != null){
					gerenciaRegional = new GGerenciaRegional();
					gerenciaRegional.setId(helper.getIdGerenciaRegional());
				}

				// Unidade de Negocio
				GUnidadeNegocio unidadeNegocio = null;
				if(helper.getIdUnidadeNegocio() != null){
					unidadeNegocio = new GUnidadeNegocio();
					unidadeNegocio.setId(helper.getIdUnidadeNegocio());
				}

				// Localidade
				GLocalidade localidade = null;
				if(helper.getIdLocalidade() != null){
					localidade = new GLocalidade();
					localidade.setId(helper.getIdLocalidade());
				}

				// Elo
				GLocalidade elo = null;
				if(helper.getIdElo() != null){
					elo = new GLocalidade();
					elo.setId(helper.getIdElo());
				}

				// Setor comercial
				GSetorComercial setorComercial = null;
				if(helper.getIdSetorComercial() != null){
					setorComercial = new GSetorComercial();
					setorComercial.setId(helper.getIdSetorComercial());
				}

				// Quadra
				GQuadra quadra = null;
				if(helper.getIdQuadra() != null){
					quadra = new GQuadra();
					quadra.setId(helper.getIdQuadra());
				}

				// Codigo do setor comercial
				Integer codigoSetorComercial = null;
				if(helper.getCodigoSetorComercial() != null){
					codigoSetorComercial = (helper.getCodigoSetorComercial());
				}

				// Numero da quadra
				Integer numeroQuadra = null;
				if(helper.getNumeroQuadra() != null){
					numeroQuadra = (helper.getNumeroQuadra());
				}

				// Perfil do imovel
				GImovelPerfil imovelPerfil = null;
				if(helper.getIdPerfilImovel() != null){
					imovelPerfil = new GImovelPerfil();
					imovelPerfil.setId(helper.getIdPerfilImovel());
				}

				// Esfera de poder do cliente responsavel
				GEsferaPoder esferaPoder = null;
				if(helper.getIdEsfera() != null){
					esferaPoder = new GEsferaPoder();
					esferaPoder.setId(helper.getIdEsfera());
				}

				// Tipo do cliente responsavel helper.getIdTipoClienteResponsavel()
				GClienteTipo clienteTipo = null;
				if(helper.getIdTipoClienteResponsavel() != null){
					clienteTipo = new GClienteTipo();
					clienteTipo.setId(1);
				}

				// Situacao da ligacao de agua
				GLigacaoAguaSituacao ligacaoAguaSituacao = null;
				if(helper.getIdSituacaoLigacaoAgua() != null){
					ligacaoAguaSituacao = new GLigacaoAguaSituacao();
					ligacaoAguaSituacao.setId(helper.getIdSituacaoLigacaoAgua());
				}

				// Situacao da ligacao de esgoto
				GLigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;
				if(helper.getIdSituacaoLigacaoEsgoto() != null){
					ligacaoEsgotoSituacao = new GLigacaoEsgotoSituacao();
					ligacaoEsgotoSituacao.setId(helper.getIdSituacaoLigacaoEsgoto());
				}

				// Categoria
				GCategoria categoria = null;
				if(helper.getIdCategoria() != null){
					categoria = new GCategoria();
					categoria.setId(helper.getIdCategoria());
				}

				// Subcategoria
				GSubcategoria subcategoria = null;
				if(helper.getIdSubCategoria() != null){
					subcategoria = new GSubcategoria();
					subcategoria.setId(helper.getIdSubCategoria());
				}

				// Perfil da ligacao de agua
				GLigacaoAguaPerfil perfilLigacaoAgua = null;
				if(helper.getIdPerfilLigacaoAgua() != null){
					perfilLigacaoAgua = new GLigacaoAguaPerfil();
					perfilLigacaoAgua.setId(helper.getIdPerfilLigacaoAgua());
				}

				// Perfil da ligacao de esgoto
				GLigacaoEsgotoPerfil perfilLigacaoEsgoto = null;
				if(helper.getIdPerfilLigacaoEsgoto() != null){
					perfilLigacaoEsgoto = new GLigacaoEsgotoPerfil();
					perfilLigacaoEsgoto.setId(helper.getIdPerfilLigacaoEsgoto());
				}

				// Rota
				GRota rota = null;
				if(helper.getIdRota() != null){
					rota = new GRota();
					rota.setId(helper.getIdRota());
				}

				// Ultima Alteracao
				Date ultimaAlteracao = new Date();

				// Empresa
				GEmpresa empresa = null;
				if(helper.getGempresa() != null){
					empresa = new GEmpresa();
					empresa.setId(helper.getGempresa());
				}

				GDebitoTipo gerDebitoTipo = null;
				if(helper.getDebitoTipo() != null){
					gerDebitoTipo = new GDebitoTipo();
					gerDebitoTipo.setId(helper.getDebitoTipo());
				}

				// Valor dos Documentos incluidos
				BigDecimal valorFinanciamentosIncluidos = (BigDecimal) helper.getValorFinanciamentosIncluidos();

				// Valor dos Documentos incluidos
				BigDecimal valorFinanciamentosCancelados = (BigDecimal) helper.getValorFinanciamentosCancelados();

				Integer financiamentoTipo = helper.getIdFinanciamentoTipo();
				Integer lictId = helper.getLictId();
				Integer tipoDocumento = helper.getIdDocumentoTipo();

				// Financiamento Tipo
				GFinanciamentoTipo gerFinanciamentoTipo = null;
				if(financiamentoTipo != null){
					gerFinanciamentoTipo = new GFinanciamentoTipo();
					gerFinanciamentoTipo.setId(financiamentoTipo);
				}

				// Lancamento item Contabil
				GLancamentoItemContabil gerLancamentoItemContabil = null;
				if(lictId != null){
					gerLancamentoItemContabil = new GLancamentoItemContabil();
					gerLancamentoItemContabil.setId(lictId);
				}

				// Tipo de Documento
				GDocumentoTipo gerDocumentoTipo = null;
				if(tipoDocumento != null){
					gerDocumentoTipo = new GDocumentoTipo();
					gerDocumentoTipo.setId(tipoDocumento);
				}

				Short indicadorHidrometro = helper.getIndicadorHidrometro();

				// Consumo Tarifa
				GConsumoTarifa gerConsumoTarifa = null;
				if(helper.getConsumoTarifa() != null){
					gerConsumoTarifa = new GConsumoTarifa();
					gerConsumoTarifa.setId(helper.getConsumoTarifa());
				}

				// Grupo Faturamento
				GFaturamentoGrupo gerFaturamentoGrupo = null;
				if(helper.getGrupoFaturamento() != null){
					gerFaturamentoGrupo = new GFaturamentoGrupo();
					gerFaturamentoGrupo.setId(helper.getGrupoFaturamento());
				}

				resumoFaturamentoAguaEsgotoGrava = new UnResumoFaturamento(anoMesReferencia, gerenciaRegional, unidadeNegocio, localidade,
								setorComercial, quadra, codigoSetorComercial, numeroQuadra, imovelPerfil, esferaPoder, clienteTipo,
								ligacaoAguaSituacao, ligacaoEsgotoSituacao, categoria, subcategoria, perfilLigacaoAgua,
								perfilLigacaoEsgoto, 0, 0, new BigDecimal(0), new BigDecimal(0), 0, gerDocumentoTipo, gerFinanciamentoTipo,
								gerLancamentoItemContabil, new BigDecimal(0), null, ultimaAlteracao, elo, rota, empresa,
								valorFinanciamentosIncluidos, valorFinanciamentosCancelados, gerDebitoTipo, indicadorHidrometro,
								new BigDecimal(0), gerConsumoTarifa, gerFaturamentoGrupo);

				// Adicionamos na tabela ResumoFaturamentoOutros
				this.getControladorBatch().inserirObjetoParaBatchGerencial(resumoFaturamentoAguaEsgotoGrava);
				System.out.println("gravando objeto de DEBITO A COBRAR");
			}// do for lista simplificada

		}catch(Exception ex){
			System.out.println(" ERRO NO SETOR " + idSetor + " PROCESSANDO RESUMO FATURAMENTO DEBITO A COBRAR");
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new EJBException(ex);
		}
	}

	/**
	 * gerarResumoFaturamentoImpostos
	 * Roberto Barbalho - 28/08/2007
	 * 
	 * @param idSetor
	 * @param anoMes
	 * @param indice
	 * @param qtRegistros
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public void gerarResumoFaturamentoImpostos(int idSetor, int anoMes, int indice, int qtRegistros, List resumo)
					throws ControladorException, ErroRepositorioException{

		try{
			System.out.println("processando resumo faturamento impostos ");
			List<ResumoFaturamentoImpostosHelper> listaSimplificadaFaturamentoImpostos = new ArrayList();
			UnResumoFaturamento resumoFaturamentoAguaEsgotoGrava = null;

			List resumoContasFaturamentoAguaEsgoto = resumo;
			// this.repositorioGerencialFaturamento
			// .getContasResumoFaturamentoAguaEsgoto( idSetor, anoMes, indice, qtRegistros);

			for(int c = 0; c < resumoContasFaturamentoAguaEsgoto.size(); c++){
				Object obj = resumoContasFaturamentoAguaEsgoto.get(c);
				Integer idContaResumo = 0;
				if(obj instanceof Object[]){
					Object[] retorno = (Object[]) obj;
					idContaResumo = (Integer) retorno[0];

					Integer idImovelImpostos = (Integer) retorno[1];

					Integer idEmpresaImpostos = (Integer) retorno[22];

					// ResumoFaturamentoOutros
					List resumoImpostos = this.repositorioGerencialFaturamento.getPesquisaImpostos(idContaResumo);

					for(int y = 0; y < resumoImpostos.size(); y++){
						Object objImpostos = resumoImpostos.get(y);
						if(obj instanceof Object[]){
							Object[] retornoImpostos = (Object[]) objImpostos;
							ResumoFaturamentoImpostosHelper helperImpostos = new ResumoFaturamentoImpostosHelper((Integer) retorno[1], // Imovel
											(Integer) retorno[2], // Gerencia Regional
											(Integer) retorno[3], // Unidade de negocio
											(Integer) retorno[4], // Elo
											(Integer) retorno[5], // Localidade
											(Integer) retorno[6], // Id Setor Comercial
											(Integer) retorno[7], // id Rota
											(Integer) retorno[8], // Id Quadra
											(Integer) retorno[9], // Codigo do Setor Comercial
											(Integer) retorno[10], // Numero da quadra
											(Integer) retorno[11], // Perfil do imovel
											(Integer) retorno[12], // Situacao da ligacao da agua
											(Integer) retorno[13], // Situacao da ligacao do esgoto
											(Integer) retorno[14], // Perfil da ligacao do agua
											(Integer) retorno[15]);// Perfil da ligacao do esgoto

							Integer anoMesReferencia = (Integer) retorno[20];

							// Consumo Tarifa.
							Integer consumoTarifa = (Integer) retorno[23];
							if(consumoTarifa == null){
								consumoTarifa = 0;
							}
							helperImpostos.setConsumoTarifa(consumoTarifa);

							// Grupo de Faturamento
							Integer grupoFaturamento = (Integer) retorno[24];
							if(grupoFaturamento == null){
								grupoFaturamento = 0;
							}
							helperImpostos.setGrupoFaturamento(grupoFaturamento);

							// Pesquisamos a esfera de poder do cliente
							helperImpostos.setIdEsfera(this.repositorioGerencialFaturamento
											.pesquisarEsferaPoderClienteResponsavelImovel(helperImpostos.getIdImovel()));
							// Pesquisamos o tipo de cliente responsavel do
							helperImpostos.setIdTipoClienteResponsavel(this.repositorioGerencialFaturamento
											.pesquisarTipoClienteClienteResponsavelImovel(helperImpostos.getIdImovel()));
							// Empresa
							helperImpostos.setGempresa(idEmpresaImpostos);
							// Categoria
							Categoria categoriaImpostos = null;
							categoriaImpostos = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovelImpostos);
							if(categoriaImpostos != null){
								helperImpostos.setIdCategoria(categoriaImpostos.getId());
								// Pesquisando a principal subcategoria
								ImovelSubcategoria subcategoriaImpostos = this.getControladorImovel().obterPrincipalSubcategoria(
												categoriaImpostos.getId(), idImovelImpostos);
								if(subcategoriaImpostos != null){
									helperImpostos.setIdSubCategoria(subcategoriaImpostos.getComp_id().getSubcategoria().getId());
								}
							}
							// Verificamos se a esfera de poder foi encontrada
							if(helperImpostos.getIdEsfera().equals(0)){
								Imovel imovel = new Imovel();
								imovel.setId(idImovelImpostos);
								Cliente clienteTempImpostos = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
								if(clienteTempImpostos != null){
									helperImpostos.setIdEsfera(clienteTempImpostos.getClienteTipo().getEsferaPoder().getId());
								}
							}
							// Verificar existencia de cliente responsavel
							if(helperImpostos.getIdTipoClienteResponsavel().equals(0)){
								Imovel imovel = new Imovel();
								imovel.setId(idImovelImpostos);
								Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
								if(clienteTemp != null){
									helperImpostos.setIdTipoClienteResponsavel(clienteTemp.getClienteTipo().getId());
								}
							}

							// [UC0307] - Obter Indicador de Existência de Hidrômetro
							String indicadorHidrometroString = new Integer(getControladorImovel().obterIndicadorExistenciaHidrometroImovel(
											idImovelImpostos)).toString();
							Short indicadorHidrometro = new Short(indicadorHidrometroString);
							// Caso indicador de hidrômetro esteja nulo
							// Seta 2(dois) = NÃO no indicador de
							// hidrômetro
							if(indicadorHidrometro == null){
								indicadorHidrometro = new Short("2");
							}
							helperImpostos.setIndicadorHidrometro(indicadorHidrometro);

							Integer impostoTipo = (Integer) retornoImpostos[0];
							if(impostoTipo == null){
								impostoTipo = 0;
							}
							helperImpostos.setImpostoTipo(impostoTipo);

							BigDecimal valorImpostos = (BigDecimal) retornoImpostos[1];
							if(valorImpostos == null){
								valorImpostos = new BigDecimal(0);
							}

							// Valor dos Documentos Faturados
							BigDecimal valorDocumentosFaturados = new BigDecimal(0);
							if(valorDocumentosFaturados == null){
								valorDocumentosFaturados = new BigDecimal(0);
							}
							// Quantidade dos Documentos Faturados
							Integer quantidadeDocumentosFaturados = 0;
							if(quantidadeDocumentosFaturados == null){
								quantidadeDocumentosFaturados = 0;
							}
							// ///////////////////////////////////////////////////////////////////////////////////
							// informacos iguals

							if(listaSimplificadaFaturamentoImpostos.contains(helperImpostos)){
								int posicaoImpostos = listaSimplificadaFaturamentoImpostos.indexOf(helperImpostos);
								ResumoFaturamentoImpostosHelper jaCadastradoImpostos = (ResumoFaturamentoImpostosHelper) listaSimplificadaFaturamentoImpostos
												.get(posicaoImpostos);
								// Somatorios

								// Valor Impostos
								jaCadastradoImpostos.setValorImpostos(valorImpostos);

							}else{
								// Somatorios

								// Valor Impostos
								helperImpostos.setValorImpostos(valorImpostos);

								helperImpostos.setAnoMesReferencia(anoMesReferencia);

								listaSimplificadaFaturamentoImpostos.add(helperImpostos);
							}
						}// if instance of de outros
					}// for de outros
				}// if instance of de contas
			}// for de contas

			for(int i = 0; i < listaSimplificadaFaturamentoImpostos.size(); i++){
				ResumoFaturamentoImpostosHelper helper = (ResumoFaturamentoImpostosHelper) listaSimplificadaFaturamentoImpostos.get(i);

				// Montamos todo o agrupamento necessario
				// Mes ano de referencia
				Integer anoMesReferencia = helper.getAnoMesReferencia();

				// Gerencia regional
				GGerenciaRegional gerenciaRegional = null;
				if(helper.getIdGerenciaRegional() != null){
					gerenciaRegional = new GGerenciaRegional();
					gerenciaRegional.setId(helper.getIdGerenciaRegional());
				}

				// Unidade de Negocio
				GUnidadeNegocio unidadeNegocio = null;
				if(helper.getIdUnidadeNegocio() != null){
					unidadeNegocio = new GUnidadeNegocio();
					unidadeNegocio.setId(helper.getIdUnidadeNegocio());
				}

				// Localidade
				GLocalidade localidade = null;
				if(helper.getIdLocalidade() != null){
					localidade = new GLocalidade();
					localidade.setId(helper.getIdLocalidade());
				}

				// Elo
				GLocalidade elo = null;
				if(helper.getIdElo() != null){
					elo = new GLocalidade();
					elo.setId(helper.getIdElo());
				}

				// Setor comercial
				GSetorComercial setorComercial = null;
				if(helper.getIdSetorComercial() != null){
					setorComercial = new GSetorComercial();
					setorComercial.setId(helper.getIdSetorComercial());
				}

				// Quadra
				GQuadra quadra = null;
				if(helper.getIdQuadra() != null){
					quadra = new GQuadra();
					quadra.setId(helper.getIdQuadra());
				}

				// Codigo do setor comercial
				Integer codigoSetorComercial = null;
				if(helper.getCodigoSetorComercial() != null){
					codigoSetorComercial = (helper.getCodigoSetorComercial());
				}

				// Numero da quadra
				Integer numeroQuadra = null;
				if(helper.getNumeroQuadra() != null){
					numeroQuadra = (helper.getNumeroQuadra());
				}

				// Perfil do imovel
				GImovelPerfil imovelPerfil = null;
				if(helper.getIdPerfilImovel() != null){
					imovelPerfil = new GImovelPerfil();
					imovelPerfil.setId(helper.getIdPerfilImovel());
				}

				// Esfera de poder do cliente responsavel
				GEsferaPoder esferaPoder = null;
				if(helper.getIdEsfera() != null){
					esferaPoder = new GEsferaPoder();
					esferaPoder.setId(helper.getIdEsfera());
				}

				// Tipo do cliente responsavel helper.getIdTipoClienteResponsavel()
				GClienteTipo clienteTipo = null;
				if(helper.getIdTipoClienteResponsavel() != null){
					clienteTipo = new GClienteTipo();
					clienteTipo.setId(1);
				}

				// Situacao da ligacao de agua
				GLigacaoAguaSituacao ligacaoAguaSituacao = null;
				if(helper.getIdSituacaoLigacaoAgua() != null){
					ligacaoAguaSituacao = new GLigacaoAguaSituacao();
					ligacaoAguaSituacao.setId(helper.getIdSituacaoLigacaoAgua());
				}

				// Situacao da ligacao de esgoto
				GLigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;
				if(helper.getIdSituacaoLigacaoEsgoto() != null){
					ligacaoEsgotoSituacao = new GLigacaoEsgotoSituacao();
					ligacaoEsgotoSituacao.setId(helper.getIdSituacaoLigacaoEsgoto());
				}

				// Categoria
				GCategoria categoria = null;
				if(helper.getIdCategoria() != null){
					categoria = new GCategoria();
					categoria.setId(helper.getIdCategoria());
				}

				// Subcategoria
				GSubcategoria subcategoria = null;
				if(helper.getIdSubCategoria() != null){
					subcategoria = new GSubcategoria();
					subcategoria.setId(helper.getIdSubCategoria());
				}

				// Perfil da ligacao de agua
				GLigacaoAguaPerfil perfilLigacaoAgua = null;
				if(helper.getIdPerfilLigacaoAgua() != null){
					perfilLigacaoAgua = new GLigacaoAguaPerfil();
					perfilLigacaoAgua.setId(helper.getIdPerfilLigacaoAgua());
				}

				// Perfil da ligacao de esgoto
				GLigacaoEsgotoPerfil perfilLigacaoEsgoto = null;
				if(helper.getIdPerfilLigacaoEsgoto() != null){
					perfilLigacaoEsgoto = new GLigacaoEsgotoPerfil();
					perfilLigacaoEsgoto.setId(helper.getIdPerfilLigacaoEsgoto());
				}

				// Rota
				GRota rota = null;
				if(helper.getIdRota() != null){
					rota = new GRota();
					rota.setId(helper.getIdRota());
				}

				// Ultima Alteracao
				Date ultimaAlteracao = new Date();

				// Empresa
				GEmpresa empresa = null;
				if(helper.getGempresa() != null){
					empresa = new GEmpresa();
					empresa.setId(helper.getGempresa());
				}

				// Imposto Tipo
				GImpostoTipo impostoTipo = null;
				if(helper.getImpostoTipo() != null){
					impostoTipo = new GImpostoTipo();
					impostoTipo.setId(helper.getImpostoTipo());
				}

				// Valor Impostos
				BigDecimal valorImpostos = helper.getValorImpostos();

				// Valor dos Documentos Faturados
				BigDecimal valorDocumentosFaturados = (BigDecimal) helper.getValorDocumentosFaturados();

				// Quantidade dos Documentos Faturados
				Integer quantidadeDocumentosFaturados = helper.getQuantidadeDocumentosFaturados();

				Short indicadorHidrometro = helper.getIndicadorHidrometro();

				// Consumo Tarifa
				GConsumoTarifa gerConsumoTarifa = null;
				if(helper.getConsumoTarifa() != null){
					gerConsumoTarifa = new GConsumoTarifa();
					gerConsumoTarifa.setId(helper.getConsumoTarifa());
				}

				// Grupo Faturamento
				GFaturamentoGrupo gerFaturamentoGrupo = null;
				if(helper.getGrupoFaturamento() != null){
					gerFaturamentoGrupo = new GFaturamentoGrupo();
					gerFaturamentoGrupo.setId(helper.getGrupoFaturamento());
				}

				GDocumentoTipo gerDocumentoTipo = new GDocumentoTipo();
				gerDocumentoTipo.setId(1);

				resumoFaturamentoAguaEsgotoGrava = new UnResumoFaturamento(anoMesReferencia, gerenciaRegional, unidadeNegocio, localidade,
								setorComercial, quadra, codigoSetorComercial, numeroQuadra, imovelPerfil, esferaPoder, clienteTipo,
								ligacaoAguaSituacao, ligacaoEsgotoSituacao, categoria, subcategoria, perfilLigacaoAgua,
								perfilLigacaoEsgoto, 0, 0, new BigDecimal(0), new BigDecimal(0), 0, valorDocumentosFaturados,
								quantidadeDocumentosFaturados.shortValue(), ultimaAlteracao, elo, rota, empresa, impostoTipo,
								valorImpostos, indicadorHidrometro, gerConsumoTarifa, gerFaturamentoGrupo, gerDocumentoTipo);

				// Adicionamos na tabela ResumoFaturamentoOutros
				this.getControladorBatch().inserirObjetoParaBatchGerencial(resumoFaturamentoAguaEsgotoGrava);
				System.out.println("gravando objeto de IMPOSTOS");
			}// do for lista simplificada

		}catch(Exception ex){
			System.out.println(" ERRO NO SETOR " + idSetor + " PROCESSANDO RESUMO FATURAMENTO IMPOSTOS");
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new EJBException(ex);
		}
	}

	/**
	 * gerarResumoFaturamentoGuiaPagamento
	 * Marcio Roberto - 06/09/2007
	 * 
	 * @param idSetor
	 * @param anoMes
	 * @param indice
	 * @param qtRegistros
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public void gerarResumoFaturamentoGuiaPagamento(int idSetor, int anoMes) throws ControladorException, ErroRepositorioException{

		try{

			System.out.println("processando resumo faturamento Guias de Pagamento ");

			List<ResumoFaturamentoGuiaPagamentoHelper> listaSimplificadaFaturamentoGuiaPagamento = new ArrayList();
			UnResumoFaturamento resumoFaturamentoAguaEsgotoGrava = null;

			List resumoGuiaPagamento = this.repositorioGerencialFaturamento.getPesquisaGuiaPagamento(idSetor, anoMes);

			Integer total = resumoGuiaPagamento.size();
			Integer reg = 0;

			for(int y = 0; y < resumoGuiaPagamento.size(); y++){
				Object objGuiaPagamento = resumoGuiaPagamento.get(y);

				if(objGuiaPagamento instanceof Object[]){
					Object[] retorno = (Object[]) objGuiaPagamento;

					Integer idImovelGuiaPagamento = (Integer) retorno[24];
					System.out.println("processando: " + reg + " de: " + total + " Guias de Pagamento do setor = " + idSetor
									+ "  do  Imovel = " + idImovelGuiaPagamento);
					reg++;
					Integer idEmpresaGuiaPagamento = (Integer) retorno[18];

					ResumoFaturamentoGuiaPagamentoHelper helperGuiaPagamento = new ResumoFaturamentoGuiaPagamentoHelper(
									(Integer) retorno[24],// Imovel
									(Integer) retorno[3], // Gerencia Regional
									(Integer) retorno[4], // Unidade de negocio
									(Integer) retorno[5], // Elo
									(Integer) retorno[6], // Localidade
									(Integer) retorno[7], // Id Setor Comercial
									(Integer) retorno[8], // id Rota
									(Integer) retorno[9], // Id Quadra
									(Integer) retorno[10], // Codigo do Setor Comercial
									(Integer) retorno[11], // Numero da quadra
									(Integer) retorno[12], // Perfil do imovel
									(Integer) retorno[13], // Situacao da ligacao da agua
									(Integer) retorno[14], // Situacao da ligacao do esgoto
									(Integer) retorno[15], // Perfil da ligacao do agua
									(Integer) retorno[16]);// Perfil da ligacao do esgoto

					Integer anoMesReferencia = anoMes;

					// Consumo Tarifa.
					Integer consumoTarifa = (Integer) retorno[19];
					if(consumoTarifa == null){
						consumoTarifa = 0;
					}
					helperGuiaPagamento.setConsumoTarifa(consumoTarifa);

					// Grupo de Faturamento
					Integer grupoFaturamento = (Integer) retorno[20];
					if(grupoFaturamento == null){
						grupoFaturamento = 0;
					}
					helperGuiaPagamento.setGrupoFaturamento(grupoFaturamento);

					// Pesquisamos a esfera de poder do cliente
					helperGuiaPagamento.setIdEsfera(this.repositorioGerencialFaturamento
									.pesquisarEsferaPoderClienteResponsavelImovel(idImovelGuiaPagamento));

					// Pesquisamos o tipo de cliente responsavel
					helperGuiaPagamento.setIdTipoClienteResponsavel(this.repositorioGerencialFaturamento
									.pesquisarTipoClienteClienteResponsavelImovel(idImovelGuiaPagamento));
					// Empresa
					helperGuiaPagamento.setGempresa(idEmpresaGuiaPagamento);

					// Categoria
					Categoria categoriaGuiaPagamento = null;
					categoriaGuiaPagamento = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovelGuiaPagamento);
					if(categoriaGuiaPagamento != null){
						helperGuiaPagamento.setIdCategoria(categoriaGuiaPagamento.getId());
						// Pesquisando a principal subcategoria
						ImovelSubcategoria subCategoriaGuiaPagamento = this.getControladorImovel().obterPrincipalSubcategoria(
										categoriaGuiaPagamento.getId(), idImovelGuiaPagamento);
						if(subCategoriaGuiaPagamento != null){
							helperGuiaPagamento.setIdSubCategoria(subCategoriaGuiaPagamento.getComp_id().getSubcategoria().getId());
						}
					}
					// Verificamos se a esfera de poder foi encontrada
					if(helperGuiaPagamento.getIdEsfera().equals(0)){
						Imovel imovel = new Imovel();
						imovel.setId(idImovelGuiaPagamento);
						Cliente clienteTempGuiaPagamento = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
						if(clienteTempGuiaPagamento != null){
							helperGuiaPagamento.setIdEsfera(clienteTempGuiaPagamento.getClienteTipo().getEsferaPoder().getId());
						}
					}

					// Verificar existencia de cliente responsavel
					if(helperGuiaPagamento.getIdTipoClienteResponsavel().equals(0)){
						Imovel imovel = new Imovel();
						imovel.setId(idImovelGuiaPagamento);
						Cliente clienteTemp = this.getControladorImovel().consultarClienteUsuarioImovel(imovel);
						if(clienteTemp != null){
							helperGuiaPagamento.setIdTipoClienteResponsavel(clienteTemp.getClienteTipo().getId());
						}
					}

					// [UC0307] - Obter Indicador de Existência de Hidrômetro
					String indicadorHidrometroString = new Integer(getControladorImovel().obterIndicadorExistenciaHidrometroImovel(
									idImovelGuiaPagamento)).toString();
					Short indicadorHidrometro = new Short(indicadorHidrometroString);
					// Caso indicador de hidrômetro esteja nulo
					// Seta 2(dois) = NÃO no indicador de
					// hidrômetro
					if(indicadorHidrometro == null){
						indicadorHidrometro = new Short("2");
					}
					helperGuiaPagamento.setIndicadorHidrometro(indicadorHidrometro);

					// Campos referentes ao "resumoDebitoCobrado"
					// ///////////////////////////////////////
					Integer financiamentoTipo = (Integer) retorno[0];
					if(financiamentoTipo == null){
						financiamentoTipo = 0;
					}
					helperGuiaPagamento.setIdFinanciamentoTipo(financiamentoTipo);

					Integer lictId = (Integer) retorno[2];
					if(lictId == null){
						lictId = 0;
					}
					helperGuiaPagamento.setLictId(lictId);

					Integer documentoTipo = (Integer) retorno[1];
					if(documentoTipo == null){
						documentoTipo = 0;
					}
					helperGuiaPagamento.setIdDocumentoTipo(documentoTipo);

					Integer debitoTipo = (Integer) retorno[23];
					if(debitoTipo == null){
						debitoTipo = 0;
					}
					helperGuiaPagamento.setDebitoTipo(debitoTipo);

					// Valor dos Documentos Faturados bobobo
					BigDecimal valorDocumentosFaturados = (BigDecimal) retorno[21];
					if(valorDocumentosFaturados == null){
						valorDocumentosFaturados = new BigDecimal(0);
					}
					System.out.println((retorno[22]).getClass().getName());
					// Quantidade dos Documentos Faturados
					Integer quantidadeDocumentosFaturados = (Integer) retorno[22];
					if(quantidadeDocumentosFaturados == null){
						quantidadeDocumentosFaturados = 0;
					}

					// ///////////////////////////////////////////////////////////////////////////////////
					// informacos iguals
					if(listaSimplificadaFaturamentoGuiaPagamento.contains(helperGuiaPagamento)){
						int posicaoGuiaPagamento = listaSimplificadaFaturamentoGuiaPagamento.indexOf(helperGuiaPagamento);
						ResumoFaturamentoGuiaPagamentoHelper jaCadastradoGuiaPagamento = (ResumoFaturamentoGuiaPagamentoHelper) listaSimplificadaFaturamentoGuiaPagamento
										.get(posicaoGuiaPagamento);

						// documentos faturados
						jaCadastradoGuiaPagamento.setQuantidadeDocumentosFaturados(jaCadastradoGuiaPagamento
										.getQuantidadeDocumentosFaturados()
										+ quantidadeDocumentosFaturados);

						// Valor Documentos faturados
						jaCadastradoGuiaPagamento.setValorDocumentosFaturados(jaCadastradoGuiaPagamento.getValorDocumentosFaturados().add(
										valorDocumentosFaturados));

					}else{
						// Somatorios

						// Valor dos Financiamentos Incluidos Debitos a Cobrar
						helperGuiaPagamento.setQuantidadeDocumentosFaturados(quantidadeDocumentosFaturados);

						// Valor dos Financiamentos Cancelados Debitos a Cobrar
						helperGuiaPagamento.setValorDocumentosFaturados(valorDocumentosFaturados);

						helperGuiaPagamento.setAnoMesReferencia(anoMesReferencia);

						listaSimplificadaFaturamentoGuiaPagamento.add(helperGuiaPagamento);
					}
				}// if instance of de debito a cobrar
			}// for de debito a cobrar

			for(int i = 0; i < listaSimplificadaFaturamentoGuiaPagamento.size(); i++){
				ResumoFaturamentoGuiaPagamentoHelper helper = (ResumoFaturamentoGuiaPagamentoHelper) listaSimplificadaFaturamentoGuiaPagamento
								.get(i);

				// Montamos todo o agrupamento necessario
				// Mes ano de referencia
				Integer anoMesReferencia = helper.getAnoMesReferencia();

				// Gerencia regional
				GGerenciaRegional gerenciaRegional = null;
				if(helper.getIdGerenciaRegional() != null){
					gerenciaRegional = new GGerenciaRegional();
					gerenciaRegional.setId(helper.getIdGerenciaRegional());
				}

				// Unidade de Negocio
				GUnidadeNegocio unidadeNegocio = null;
				if(helper.getIdUnidadeNegocio() != null){
					unidadeNegocio = new GUnidadeNegocio();
					unidadeNegocio.setId(helper.getIdUnidadeNegocio());
				}

				// Localidade
				GLocalidade localidade = null;
				if(helper.getIdLocalidade() != null){
					localidade = new GLocalidade();
					localidade.setId(helper.getIdLocalidade());
				}

				// Elo
				GLocalidade elo = null;
				if(helper.getIdElo() != null){
					elo = new GLocalidade();
					elo.setId(helper.getIdElo());
				}

				// Setor comercial
				GSetorComercial setorComercial = null;
				if(helper.getIdSetorComercial() != null){
					setorComercial = new GSetorComercial();
					setorComercial.setId(helper.getIdSetorComercial());
				}

				// Quadra
				GQuadra quadra = null;
				if(helper.getIdQuadra() != null){
					quadra = new GQuadra();
					quadra.setId(helper.getIdQuadra());
				}

				// Codigo do setor comercial
				Integer codigoSetorComercial = null;
				if(helper.getCodigoSetorComercial() != null){
					codigoSetorComercial = (helper.getCodigoSetorComercial());
				}

				// Numero da quadra
				Integer numeroQuadra = null;
				if(helper.getNumeroQuadra() != null){
					numeroQuadra = (helper.getNumeroQuadra());
				}

				// Perfil do imovel
				GImovelPerfil imovelPerfil = null;
				if(helper.getIdPerfilImovel() != null){
					imovelPerfil = new GImovelPerfil();
					imovelPerfil.setId(helper.getIdPerfilImovel());
				}

				// Esfera de poder do cliente responsavel
				GEsferaPoder esferaPoder = null;
				if(helper.getIdEsfera() != null){
					esferaPoder = new GEsferaPoder();
					esferaPoder.setId(helper.getIdEsfera());
				}

				// Tipo do cliente responsavel helper.getIdTipoClienteResponsavel()
				GClienteTipo clienteTipo = null;
				if(helper.getIdTipoClienteResponsavel() != null){
					clienteTipo = new GClienteTipo();
					clienteTipo.setId(1);
				}

				// Situacao da ligacao de agua
				GLigacaoAguaSituacao ligacaoAguaSituacao = null;
				if(helper.getIdSituacaoLigacaoAgua() != null){
					ligacaoAguaSituacao = new GLigacaoAguaSituacao();
					ligacaoAguaSituacao.setId(helper.getIdSituacaoLigacaoAgua());
				}

				// Situacao da ligacao de esgoto
				GLigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;
				if(helper.getIdSituacaoLigacaoEsgoto() != null){
					ligacaoEsgotoSituacao = new GLigacaoEsgotoSituacao();
					ligacaoEsgotoSituacao.setId(helper.getIdSituacaoLigacaoEsgoto());
				}

				// Categoria
				GCategoria categoria = null;
				if(helper.getIdCategoria() != null){
					categoria = new GCategoria();
					categoria.setId(helper.getIdCategoria());
				}

				// Subcategoria
				GSubcategoria subcategoria = null;
				if(helper.getIdSubCategoria() != null){
					subcategoria = new GSubcategoria();
					subcategoria.setId(helper.getIdSubCategoria());
				}

				// Perfil da ligacao de agua
				GLigacaoAguaPerfil perfilLigacaoAgua = null;
				if(helper.getIdPerfilLigacaoAgua() != null){
					perfilLigacaoAgua = new GLigacaoAguaPerfil();
					perfilLigacaoAgua.setId(helper.getIdPerfilLigacaoAgua());
				}

				// Perfil da ligacao de esgoto
				GLigacaoEsgotoPerfil perfilLigacaoEsgoto = null;
				if(helper.getIdPerfilLigacaoEsgoto() != null){
					perfilLigacaoEsgoto = new GLigacaoEsgotoPerfil();
					perfilLigacaoEsgoto.setId(helper.getIdPerfilLigacaoEsgoto());
				}

				// Rota
				GRota rota = null;
				if(helper.getIdRota() != null){
					rota = new GRota();
					rota.setId(helper.getIdRota());
				}

				// Ultima Alteracao
				Date ultimaAlteracao = new Date();

				// Empresa
				GEmpresa empresa = null;
				if(helper.getGempresa() != null){
					empresa = new GEmpresa();
					empresa.setId(helper.getGempresa());
				}

				GDebitoTipo gerDebitoTipo = null;
				if(helper.getDebitoTipo() != null){
					gerDebitoTipo = new GDebitoTipo();
					gerDebitoTipo.setId(helper.getDebitoTipo());
				}

				// Valor dos Documentos Faturados
				BigDecimal valorDocumentosFaturados = (BigDecimal) helper.getValorDocumentosFaturados();

				// Quantidade dos Documentos Faturados
				Integer quantidadeDocumentosFaturados = helper.getQuantidadeDocumentosFaturados();

				Integer financiamentoTipo = helper.getIdFinanciamentoTipo();
				Integer lictId = helper.getLictId();
				Integer tipoDocumento = helper.getIdDocumentoTipo();

				// Financiamento Tipo
				GFinanciamentoTipo gerFinanciamentoTipo = null;
				if(financiamentoTipo != null){
					gerFinanciamentoTipo = new GFinanciamentoTipo();
					gerFinanciamentoTipo.setId(financiamentoTipo);
				}

				// Lancamento item Contabil
				GLancamentoItemContabil gerLancamentoItemContabil = null;
				if(lictId != null){
					gerLancamentoItemContabil = new GLancamentoItemContabil();
					gerLancamentoItemContabil.setId(lictId);
				}

				// Tipo de Documento
				GDocumentoTipo gerDocumentoTipo = null;
				if(tipoDocumento != null){
					gerDocumentoTipo = new GDocumentoTipo();
					gerDocumentoTipo.setId(tipoDocumento);
				}

				Short indicadorHidrometro = helper.getIndicadorHidrometro();

				// Consumo Tarifa
				GConsumoTarifa gerConsumoTarifa = null;
				if(helper.getConsumoTarifa() != null){
					gerConsumoTarifa = new GConsumoTarifa();
					gerConsumoTarifa.setId(helper.getConsumoTarifa());
				}

				// Grupo Faturamento
				GFaturamentoGrupo gerFaturamentoGrupo = null;
				if(helper.getGrupoFaturamento() != null){
					gerFaturamentoGrupo = new GFaturamentoGrupo();
					gerFaturamentoGrupo.setId(helper.getGrupoFaturamento());
				}
				resumoFaturamentoAguaEsgotoGrava = new UnResumoFaturamento(anoMesReferencia, gerenciaRegional, unidadeNegocio, localidade,
								setorComercial, quadra, codigoSetorComercial, numeroQuadra, imovelPerfil, esferaPoder, clienteTipo,
								ligacaoAguaSituacao, ligacaoEsgotoSituacao, categoria, subcategoria, perfilLigacaoAgua,
								perfilLigacaoEsgoto, 0, 0, new BigDecimal(0), new BigDecimal(0), 0, gerDocumentoTipo, gerFinanciamentoTipo,
								gerLancamentoItemContabil, valorDocumentosFaturados, quantidadeDocumentosFaturados.shortValue(),
								ultimaAlteracao, elo, rota, empresa, new BigDecimal(0), new BigDecimal(0), gerDebitoTipo,
								indicadorHidrometro, new BigDecimal(0), gerConsumoTarifa, gerFaturamentoGrupo);

				// Adicionamos na tabela ResumoFaturamentoOutros
				this.getControladorBatch().inserirObjetoParaBatchGerencial(resumoFaturamentoAguaEsgotoGrava);
				System.out.println("gravando objeto de GUIA DE PAGAMENTO");
			}// do for lista simplificada

		}catch(Exception ex){
			System.out.println(" ERRO NO SETOR " + idSetor + " PROCESSANDO RESUMO FATURAMENTO GUIA DE PAGAMENTO");
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new EJBException(ex);
		}
	}

}
