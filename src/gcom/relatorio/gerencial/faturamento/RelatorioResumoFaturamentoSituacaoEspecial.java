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

package gcom.relatorio.gerencial.faturamento;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gerencial.cobranca.ResumoCobrancaSituacaoEspecialConsultaFinalHelper;
import gcom.gerencial.cobranca.ResumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper;
import gcom.gerencial.cobranca.ResumoCobrancaSituacaoEspecialConsultaLocalidadeHelper;
import gcom.gerencial.cobranca.ResumoCobrancaSituacaoEspecialConsultaMotivoHelper;
import gcom.gerencial.cobranca.ResumoCobrancaSituacaoEspecialConsultaSitTipoHelper;
import gcom.gerencial.cobranca.ResumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper;
import gcom.gerencial.faturamento.ResumoFaturamentoSituacaoEspecialConsultaFinalHelper;
import gcom.gerencial.faturamento.ResumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper;
import gcom.gerencial.faturamento.ResumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper;
import gcom.gerencial.faturamento.ResumoFaturamentoSituacaoEspecialConsultaMotivoHelper;
import gcom.gerencial.faturamento.ResumoFaturamentoSituacaoEspecialConsultaSitTipoHelper;
import gcom.gerencial.faturamento.ResumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RelatorioResumoFaturamentoSituacaoEspecial
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da classe RelatorioDadosEconomiaImovel
	 */
	public RelatorioResumoFaturamentoSituacaoEspecial(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_RESUMO_FATURAMENTO_SITUACAO_ESPECIAL);
	}

	@Deprecated
	public RelatorioResumoFaturamentoSituacaoEspecial() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		// valor de retorno
		byte[] retorno = null;

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioResumoFaturamentoSituacaoEspecialBean relatorioBean = null;

		String nomeRelatorio = (String) getParametro("nomeRelatorio");

		if(nomeRelatorio.equalsIgnoreCase("faturamento")){

			ResumoFaturamentoSituacaoEspecialConsultaFinalHelper resumoFaturamentoSituacaoEspecialConsultaFinalHelper = (ResumoFaturamentoSituacaoEspecialConsultaFinalHelper) getParametro("resumoFaturamentoSituacaoEspecialConsultaFinalHelper");

			Iterator resumoFaturamentoSituacaoEspecialConsultaGerenciaHelperIterator = resumoFaturamentoSituacaoEspecialConsultaFinalHelper
							.getResumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper().iterator();

			while(resumoFaturamentoSituacaoEspecialConsultaGerenciaHelperIterator.hasNext()){

				ResumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper = (ResumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper) resumoFaturamentoSituacaoEspecialConsultaGerenciaHelperIterator
								.next();

				Iterator resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelperIterator = resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper
								.getResumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper().iterator();

				while(resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelperIterator.hasNext()){

					ResumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper = (ResumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper) resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelperIterator
									.next();

					Iterator resumoFaturamentoSituacaoEspecialConsultaLocalidadeHelperIterator = resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper
									.getResumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper().iterator();

					while(resumoFaturamentoSituacaoEspecialConsultaLocalidadeHelperIterator.hasNext()){

						ResumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper resumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper = (ResumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper) resumoFaturamentoSituacaoEspecialConsultaLocalidadeHelperIterator
										.next();

						Iterator resumoFaturamentoSituacaoEspecialConsultaSitTipoHelperIterator = resumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper
										.getResumoFaturamentoSituacaoEspecialConsultaSitTipoHelper().iterator();

						while(resumoFaturamentoSituacaoEspecialConsultaSitTipoHelperIterator.hasNext()){

							ResumoFaturamentoSituacaoEspecialConsultaSitTipoHelper resumoFaturamentoSituacaoEspecialConsultaSitTipoHelper = (ResumoFaturamentoSituacaoEspecialConsultaSitTipoHelper) resumoFaturamentoSituacaoEspecialConsultaSitTipoHelperIterator
											.next();

							Iterator resumoFaturamentoSituacaoEspecialConsultaMotivoHelperIterator = resumoFaturamentoSituacaoEspecialConsultaSitTipoHelper
											.getResumoFaturamentoSituacaoEspecialConsultaMotivoHelper().iterator();

							while(resumoFaturamentoSituacaoEspecialConsultaMotivoHelperIterator.hasNext()){

								ResumoFaturamentoSituacaoEspecialConsultaMotivoHelper resumoFaturamentoSituacaoEspecialConsultaMotivoHelper = (ResumoFaturamentoSituacaoEspecialConsultaMotivoHelper) resumoFaturamentoSituacaoEspecialConsultaMotivoHelperIterator
												.next();

								// Início da Construção do objeto
								// RelatorioDadosEconomiaImovelBean
								// para ser colocado no relatório
								relatorioBean = new RelatorioResumoFaturamentoSituacaoEspecialBean(

												// Faturamento Estimado Geral
												resumoFaturamentoSituacaoEspecialConsultaFinalHelper.getTotalFatEstimadoGeral() == null ? ""
																: Util.formatarMoedaReal(resumoFaturamentoSituacaoEspecialConsultaFinalHelper
																				.getTotalFatEstimadoGeral()),

												// //Faturamento Estimado Gerencia
												resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper.getTotalFatEstimadoGerencia() == null ? ""
																: Util.formatarMoedaReal(resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper
																				.getTotalFatEstimadoGerencia()),
																				
												// Faturamento Estimado Localidade
												resumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper.getTotalFatEstimadoLocalidade() == null ? ""
																: Util.formatarMoedaReal(resumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper
																				.getTotalFatEstimadoLocalidade()),

												// Faturamento Estimado Motivo
												resumoFaturamentoSituacaoEspecialConsultaMotivoHelper.getFaturamentoEstimado() == null ? ""
																: Util.formatarMoedaReal(resumoFaturamentoSituacaoEspecialConsultaMotivoHelper
																				.getFaturamentoEstimado()),

												// Faturamento Estimado Situacao
												resumoFaturamentoSituacaoEspecialConsultaSitTipoHelper.getTotalFatEstimadoSitTipo() == null ? ""
																: Util.formatarMoedaReal(resumoFaturamentoSituacaoEspecialConsultaSitTipoHelper
																				.getTotalFatEstimadoSitTipo()),

												// GerenciaRegional
												resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper.getIdGerenciaRegional()
																.toString()
																+ " - "
																+ resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper
																				.getGerenciaRegionalDescricao(),

												// Localidade
												resumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper.getIdLocalidade().toString()
																+ " - "
																+ resumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper
																				.getLocalidadeDescricao(),

												// Ano Mês Fim
												resumoFaturamentoSituacaoEspecialConsultaMotivoHelper.getFormatarAnoMesParaMesAnoFim(),

												// Ano Mês Início
												resumoFaturamentoSituacaoEspecialConsultaMotivoHelper.getFormatarAnoMesParaMesAnoInicio(),

												// Motivo
												resumoFaturamentoSituacaoEspecialConsultaMotivoHelper.getMotivoDescricao(),

												// Percentual Geral
												resumoFaturamentoSituacaoEspecialConsultaFinalHelper.getTotalPercentualGeral() == null ? ""
																: Util.formatarMoedaReal(resumoFaturamentoSituacaoEspecialConsultaFinalHelper
																				.getTotalPercentualGeral()),

												// Percentual Gerencial
												resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper.getTotalPercentualGerencia() == null ? ""
																: Util.formatarMoedaReal(resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper
																				.getTotalPercentualGerencia()),

												// Percentual Localidade
												resumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper.getTotalPercentualLocalidade() == null ? ""
																: Util.formatarMoedaReal(resumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper
																				.getTotalPercentualLocalidade()),

												// Percentual Motivo
												resumoFaturamentoSituacaoEspecialConsultaMotivoHelper.getPercentual() == null ? "" : Util
																.formatarMoedaReal(resumoFaturamentoSituacaoEspecialConsultaMotivoHelper
																				.getPercentual()),

												// Percentual Situação
												resumoFaturamentoSituacaoEspecialConsultaSitTipoHelper.getTotalPercentualSitTipo() == null ? ""
																: Util.formatarMoedaReal(resumoFaturamentoSituacaoEspecialConsultaSitTipoHelper
																				.getTotalPercentualSitTipo()),

												// Quantidade Imóvel Geral
												resumoFaturamentoSituacaoEspecialConsultaFinalHelper.getTotalQtLigacoesGeral().toString(),

												// Quantidade Imóvel Gerência
												resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper.getTotalQtLigacoesGerencia()
																.toString(),

												// Quantidade Imóvel Localidade
												resumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper.getTotalQtLigacoesLocalidade()
																.toString(),

												// Quantidade Imóvel Motivo
												resumoFaturamentoSituacaoEspecialConsultaMotivoHelper.getQtLigacoes() == null ? "0"
																: resumoFaturamentoSituacaoEspecialConsultaMotivoHelper.getQtLigacoes()
																				.toString(),

												// Quantidade Imóvel Situação
												resumoFaturamentoSituacaoEspecialConsultaSitTipoHelper.getTotalQtLigacoesSitTipo()
																.toString(),

												// Quantidade Paralisada Geral
												resumoFaturamentoSituacaoEspecialConsultaFinalHelper.getTotalGeral().toString(),

												// Quantidade Paralisada Gerência
												resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper.getTotalGerenciaRegional()
																.toString(),

												// Quantidade Paralisada Motivo
												resumoFaturamentoSituacaoEspecialConsultaMotivoHelper.getQtParalisada().toString(),

												// Quantidade Paralisada Situação
												resumoFaturamentoSituacaoEspecialConsultaSitTipoHelper.getTotalSituacaoTipo().toString(),

												// Quantidade Paralisada Localidade
												resumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper.getTotalLocalidade().toString(),

												// Situação
												resumoFaturamentoSituacaoEspecialConsultaSitTipoHelper.getSituacaoTipoDescricao(),
												
												// Faturamento Estimado Unidade Negocio
												resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper.getTotalFatEstimadoUnidadeNegocio() == null ? ""
																: Util.formatarMoedaReal(resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper
																				.getTotalFatEstimadoUnidadeNegocio()),
																				
												// Unidade Negocio
												resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper.getDsUnidadeNegocio().toString()
																+ " - "
																+ resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper
																				.getDsUnidadeNegocio(),
																				
												// Percentual Unidade Negocio
												resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper.getTotalPercentualUnidadeNegocio() == null ? ""
																: Util.formatarMoedaReal(resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper
																				.getTotalPercentualUnidadeNegocio()),
																				
												// Quantidade Imóvel Unidade Negocio
												resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper.getTotalQtLigacoesUnidadeNegocio()
																.toString(),
																
												// Quantidade Paralisada Unidade Negocio
												resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper.getTotalUnidadeNegocio().toString()
												
												);

								// Fim da Construção do objeto
								// RelatorioDadosEconomiaImovelBean
								// para ser colocado no relatório

								// adiciona o bean a coleção
								relatorioBeans.add(relatorioBean);

							}
						}
					}
				}
			}

		}else{
			ResumoCobrancaSituacaoEspecialConsultaFinalHelper resumoCobrancaSituacaoEspecialConsultaFinalHelper = (ResumoCobrancaSituacaoEspecialConsultaFinalHelper) getParametro("resumoCobrancaSituacaoEspecialConsultaFinalHelper");

			Iterator resumoCobrancaSituacaoEspecialConsultaGerenciaHelperIterator = resumoCobrancaSituacaoEspecialConsultaFinalHelper
							.getResumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper().iterator();

			while(resumoCobrancaSituacaoEspecialConsultaGerenciaHelperIterator.hasNext()){

				ResumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper = (ResumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper) resumoCobrancaSituacaoEspecialConsultaGerenciaHelperIterator
								.next();

				Iterator resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelperIterator = resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper
								.getResumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper().iterator();

				while(resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelperIterator.hasNext()){

					ResumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper = (ResumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper) resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelperIterator
									.next();

					Iterator resumoCobrancaSituacaoEspecialConsultaLocalidadeHelperIterator = resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper
									.getResumoCobrancaSituacaoEspecialConsultaLocalidadeHelper().iterator();

					while(resumoCobrancaSituacaoEspecialConsultaLocalidadeHelperIterator.hasNext()){

						ResumoCobrancaSituacaoEspecialConsultaLocalidadeHelper resumoCobrancaSituacaoEspecialConsultaLocalidadeHelper = (ResumoCobrancaSituacaoEspecialConsultaLocalidadeHelper) resumoCobrancaSituacaoEspecialConsultaLocalidadeHelperIterator
										.next();

						Iterator resumoCobrancaSituacaoEspecialConsultaSitTipoHelperIterator = resumoCobrancaSituacaoEspecialConsultaLocalidadeHelper
										.getResumoCobrancaSituacaoEspecialConsultaSitTipoHelper().iterator();

						while(resumoCobrancaSituacaoEspecialConsultaSitTipoHelperIterator.hasNext()){

							ResumoCobrancaSituacaoEspecialConsultaSitTipoHelper resumoCobrancaSituacaoEspecialConsultaSitTipoHelper = (ResumoCobrancaSituacaoEspecialConsultaSitTipoHelper) resumoCobrancaSituacaoEspecialConsultaSitTipoHelperIterator
											.next();

							Iterator resumoCobrancaSituacaoEspecialConsultaMotivoHelperIterator = resumoCobrancaSituacaoEspecialConsultaSitTipoHelper
											.getResumoCobrancaSituacaoEspecialConsultaMotivoHelper().iterator();

							while(resumoCobrancaSituacaoEspecialConsultaMotivoHelperIterator.hasNext()){

								ResumoCobrancaSituacaoEspecialConsultaMotivoHelper resumoCobrancaSituacaoEspecialConsultaMotivoHelper = (ResumoCobrancaSituacaoEspecialConsultaMotivoHelper) resumoCobrancaSituacaoEspecialConsultaMotivoHelperIterator
												.next();

								// Início da Construção do objeto
								// RelatorioDadosEconomiaImovelBean
								// para ser colocado no relatório
								relatorioBean = new RelatorioResumoFaturamentoSituacaoEspecialBean(

												// Faturamento Estimado Geral
																resumoCobrancaSituacaoEspecialConsultaFinalHelper.getTotalFatEstimadoGeral() == null ? ""
																				: Util.formatarMoedaReal(resumoCobrancaSituacaoEspecialConsultaFinalHelper
																								.getTotalFatEstimadoGeral()),

																// //Faturamento Estimado Gerencia
																resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper.getTotalFatEstimadoGerencia() == null ? ""
																				: Util
																								.formatarMoedaReal(resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper
																												.getTotalFatEstimadoGerencia()),

																// Faturamento Estimado Motivo
																resumoCobrancaSituacaoEspecialConsultaMotivoHelper.getCobrancaEstimado() == null ? "" : Util
																				.formatarMoedaReal(resumoCobrancaSituacaoEspecialConsultaMotivoHelper
																								.getCobrancaEstimado()),

																// Faturamento Estimado Situacao
																resumoCobrancaSituacaoEspecialConsultaSitTipoHelper.getTotalFatEstimadoSitTipo() == null ? ""
																				: Util.formatarMoedaReal(resumoCobrancaSituacaoEspecialConsultaSitTipoHelper
																								.getTotalFatEstimadoSitTipo()),

																// Faturamento Estimado Situacao
																resumoCobrancaSituacaoEspecialConsultaSitTipoHelper.getTotalFatEstimadoSitTipo() == null ? ""
																				: Util.formatarMoedaReal(resumoCobrancaSituacaoEspecialConsultaSitTipoHelper
																								.getTotalFatEstimadoSitTipo()),

																// GerenciaRegional
																resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper.getIdGerenciaRegional().toString()
																				+ " - "
																				+ resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper
																								.getGerenciaRegionalDescricao(),

																// Localidade
																resumoCobrancaSituacaoEspecialConsultaLocalidadeHelper.getIdLocalidade().toString()
																				+ " - "
																				+ resumoCobrancaSituacaoEspecialConsultaLocalidadeHelper
																								.getLocalidadeDescricao(),

																// Ano Mês Fim
																resumoCobrancaSituacaoEspecialConsultaMotivoHelper.getFormatarAnoMesParaMesAnoFim(),

																// Ano Mês Início
																resumoCobrancaSituacaoEspecialConsultaMotivoHelper.getFormatarAnoMesParaMesAnoInicio(),

																// Motivo
																resumoCobrancaSituacaoEspecialConsultaMotivoHelper.getMotivoDescricao(),

																// Percentual Geral
																resumoCobrancaSituacaoEspecialConsultaFinalHelper.getTotalPercentualGeral() == null ? "" : 
																				Util.formatarMoedaReal(resumoCobrancaSituacaoEspecialConsultaFinalHelper
																								.getTotalPercentualGeral()),

																// Percentual Gerencial
																resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper.getTotalPercentualGerencia() == null ? ""
																				: Util.formatarMoedaReal(resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper
																												.getTotalPercentualGerencia()),

																// Percentual Localidade
																resumoCobrancaSituacaoEspecialConsultaLocalidadeHelper.getTotalPercentualLocalidade() == null ? ""
																				: Util.formatarMoedaReal(resumoCobrancaSituacaoEspecialConsultaLocalidadeHelper
																								.getTotalPercentualLocalidade()),

																// Percentual Motivo
																resumoCobrancaSituacaoEspecialConsultaMotivoHelper.getPercentual() == null ? "" : 
																				Util.formatarMoedaReal(resumoCobrancaSituacaoEspecialConsultaMotivoHelper
																								.getPercentual()),

																// Percentual Situação
																resumoCobrancaSituacaoEspecialConsultaSitTipoHelper.getTotalPercentualSitTipo() == null ? ""
																				: Util.formatarMoedaReal(resumoCobrancaSituacaoEspecialConsultaSitTipoHelper
																								.getTotalPercentualSitTipo()),

																// Quantidade Imóvel Geral
																resumoCobrancaSituacaoEspecialConsultaFinalHelper.getTotalQtLigacoesGeral().toString(),

																// Quantidade Imóvel Gerência
																resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper.getTotalQtLigacoesGerencia().toString(),

																// Quantidade Imóvel Localidade
																resumoCobrancaSituacaoEspecialConsultaLocalidadeHelper.getTotalQtLigacoesLocalidade()
																				.toString(),

																// Quantidade Imóvel Motivo
																resumoCobrancaSituacaoEspecialConsultaMotivoHelper.getQtLigacoes() == null ? "0"
																				: resumoCobrancaSituacaoEspecialConsultaMotivoHelper.getQtLigacoes().toString(),

																// Quantidade Imóvel Situação
																resumoCobrancaSituacaoEspecialConsultaSitTipoHelper.getTotalQtLigacoesSitTipo().toString(),

																// Quantidade Paralisada Geral
																resumoCobrancaSituacaoEspecialConsultaFinalHelper.getTotalGeral().toString(),

																// Quantidade Paralisada Gerência
																resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper.getTotalGerenciaRegional().toString(),

																// Quantidade Paralisada Motivo
																resumoCobrancaSituacaoEspecialConsultaMotivoHelper.getQtParalisada().toString(),

																// Quantidade Paralisada Situação
																resumoCobrancaSituacaoEspecialConsultaSitTipoHelper.getTotalSituacaoTipo().toString(),

																// Quantidade Paralisada Localidade
																resumoCobrancaSituacaoEspecialConsultaLocalidadeHelper.getTotalLocalidade().toString(),

																// Situação
																resumoCobrancaSituacaoEspecialConsultaSitTipoHelper.getSituacaoTipoDescricao(),
																
																// Cobrança Estimado Unidade Negócio
																resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper.getTotalFatEstimadoUnidadeNegocio() == null ? ""
																				: Util.formatarMoedaReal(resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper
																								.getTotalFatEstimadoUnidadeNegocio()),

												                // Unidade Negócio
												                resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper.getDsUnidadeNegocio().toString()
																+ " - "
																+ resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper
																				.getDsUnidadeNegocio(),

																// Percentual Unidade Negócio
												                resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper.getTotalPercentualUnidadeNegocio() == null ? ""
												                				: Util.formatarMoedaReal(resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper
												                								.getTotalPercentualUnidadeNegocio()),
																								
																// Quantidade Imóvel Unidade Negócio
																resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper.getTotalQtLigacoesUnidadeNegocio()
																				.toString(),
																				
																// Quantidade Paralisada Unidade Negócio
																resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper.getTotalUnidadeNegocio().toString());

								// Fim da Construção do objeto
								// RelatorioDadosEconomiaImovelBean
								// para ser colocado no relatório

								// adiciona o bean a coleção
								relatorioBeans.add(relatorioBean);

							}
						}

					}
				}

			}
		}

		// Organizar a coleção

		// Collections.sort((List) relatorioBeans, new Comparator() {
		// public int compare(Object a, Object b) {
		// String chaveRegistro1 = ((RelatorioDadosEconomiaImovelBean) a)
		// .getNomeClienteUsuario() == null ?
		// ((RelatorioDadosEconomiaImovelBean) a)
		// .getMatricula()
		// + ((RelatorioDadosEconomiaImovelBean) a)
		// .getSubcategoria() + " "
		// : ((RelatorioDadosEconomiaImovelBean) a)
		// .getIdSetorComercial()
		// + ((RelatorioDadosEconomiaImovelBean) a)
		// .getIdLocalidade()
		// + ((RelatorioDadosEconomiaImovelBean) a)
		// .getIdGerenciaRegional()
		// + ((RelatorioDadosEconomiaImovelBean) a)
		// .getMatricula()
		// + ((RelatorioDadosEconomiaImovelBean) a)
		// .getSubcategoria()
		// + ((RelatorioDadosEconomiaImovelBean) a)
		// .getNomeClienteUsuario()
		// + ((RelatorioDadosEconomiaImovelBean) a)
		// .getNomeCliente()
		// + ((RelatorioDadosEconomiaImovelBean) a)
		// .getTipoRelacao();
		// String chaveRegistro2 = ((RelatorioDadosEconomiaImovelBean) b)
		// .getNomeClienteUsuario() == null ?
		// ((RelatorioDadosEconomiaImovelBean) b)
		// .getMatricula()
		// + ((RelatorioDadosEconomiaImovelBean) b)
		// .getSubcategoria() + " "
		// : ((RelatorioDadosEconomiaImovelBean) b)
		// .getIdSetorComercial()
		// + ((RelatorioDadosEconomiaImovelBean) b)
		// .getIdLocalidade()
		// + ((RelatorioDadosEconomiaImovelBean) b)
		// .getIdGerenciaRegional()
		// + ((RelatorioDadosEconomiaImovelBean) b)
		// .getMatricula()
		// + ((RelatorioDadosEconomiaImovelBean) b)
		// .getSubcategoria()
		// + ((RelatorioDadosEconomiaImovelBean) b)
		// .getNomeClienteUsuario()
		// + ((RelatorioDadosEconomiaImovelBean) b)
		// .getNomeCliente()
		// + ((RelatorioDadosEconomiaImovelBean) b)
		// .getTipoRelacao();
		//
		// return chaveRegistro1.compareTo(chaveRegistro2);
		//
		// }
		// });

		// Parâmetros do relatório
		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("nomeRelatorio", nomeRelatorio);

		// adiciona os parâmetros do relatório
		// adiciona o laudo da análise
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		RelatorioDataSource ds = new RelatorioDataSource((List) relatorioBeans);

		// exporta o relatório em pdf e retorna o array de bytes

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_RESUMO_FATURAMENTO_SITUACAO_ESPECIAL, parametros, ds,
						tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RESUMO_FATURAMENTO_SITUACAO_ESPECIAL, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		// if (getParametro("resumoFaturamentoSituacaoEspecialConsultaFinalHelper") != null
		// && getParametro("resumoFaturamentoSituacaoEspecialConsultaFinalHelper") instanceof
		// Collection) {
		// retorno = ((Collection)
		// getParametro("resumoFaturamentoSituacaoEspecialConsultaFinalHelper")).size();
		// } else {
		// retorno = ((Collection)
		// getParametro("resumoCobrancaSituacaoEspecialConsultaFinalHelper")).size();
		// }
		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioResumoFaturamentoSituacaoEspecial", this);
	}
}
