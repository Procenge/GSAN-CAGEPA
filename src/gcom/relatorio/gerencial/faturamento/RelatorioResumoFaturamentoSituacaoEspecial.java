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

		// cole��o de beans do relat�rio
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

								// In�cio da Constru��o do objeto
								// RelatorioDadosEconomiaImovelBean
								// para ser colocado no relat�rio
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

												// Ano M�s Fim
												resumoFaturamentoSituacaoEspecialConsultaMotivoHelper.getFormatarAnoMesParaMesAnoFim(),

												// Ano M�s In�cio
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

												// Percentual Situa��o
												resumoFaturamentoSituacaoEspecialConsultaSitTipoHelper.getTotalPercentualSitTipo() == null ? ""
																: Util.formatarMoedaReal(resumoFaturamentoSituacaoEspecialConsultaSitTipoHelper
																				.getTotalPercentualSitTipo()),

												// Quantidade Im�vel Geral
												resumoFaturamentoSituacaoEspecialConsultaFinalHelper.getTotalQtLigacoesGeral().toString(),

												// Quantidade Im�vel Ger�ncia
												resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper.getTotalQtLigacoesGerencia()
																.toString(),

												// Quantidade Im�vel Localidade
												resumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper.getTotalQtLigacoesLocalidade()
																.toString(),

												// Quantidade Im�vel Motivo
												resumoFaturamentoSituacaoEspecialConsultaMotivoHelper.getQtLigacoes() == null ? "0"
																: resumoFaturamentoSituacaoEspecialConsultaMotivoHelper.getQtLigacoes()
																				.toString(),

												// Quantidade Im�vel Situa��o
												resumoFaturamentoSituacaoEspecialConsultaSitTipoHelper.getTotalQtLigacoesSitTipo()
																.toString(),

												// Quantidade Paralisada Geral
												resumoFaturamentoSituacaoEspecialConsultaFinalHelper.getTotalGeral().toString(),

												// Quantidade Paralisada Ger�ncia
												resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper.getTotalGerenciaRegional()
																.toString(),

												// Quantidade Paralisada Motivo
												resumoFaturamentoSituacaoEspecialConsultaMotivoHelper.getQtParalisada().toString(),

												// Quantidade Paralisada Situa��o
												resumoFaturamentoSituacaoEspecialConsultaSitTipoHelper.getTotalSituacaoTipo().toString(),

												// Quantidade Paralisada Localidade
												resumoFaturamentoSituacaoEspecialConsultaLocalidadeHelper.getTotalLocalidade().toString(),

												// Situa��o
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
																				
												// Quantidade Im�vel Unidade Negocio
												resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper.getTotalQtLigacoesUnidadeNegocio()
																.toString(),
																
												// Quantidade Paralisada Unidade Negocio
												resumoFaturamentoSituacaoEspecialConsultaUnidadeNegocioHelper.getTotalUnidadeNegocio().toString()
												
												);

								// Fim da Constru��o do objeto
								// RelatorioDadosEconomiaImovelBean
								// para ser colocado no relat�rio

								// adiciona o bean a cole��o
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

								// In�cio da Constru��o do objeto
								// RelatorioDadosEconomiaImovelBean
								// para ser colocado no relat�rio
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

																// Ano M�s Fim
																resumoCobrancaSituacaoEspecialConsultaMotivoHelper.getFormatarAnoMesParaMesAnoFim(),

																// Ano M�s In�cio
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

																// Percentual Situa��o
																resumoCobrancaSituacaoEspecialConsultaSitTipoHelper.getTotalPercentualSitTipo() == null ? ""
																				: Util.formatarMoedaReal(resumoCobrancaSituacaoEspecialConsultaSitTipoHelper
																								.getTotalPercentualSitTipo()),

																// Quantidade Im�vel Geral
																resumoCobrancaSituacaoEspecialConsultaFinalHelper.getTotalQtLigacoesGeral().toString(),

																// Quantidade Im�vel Ger�ncia
																resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper.getTotalQtLigacoesGerencia().toString(),

																// Quantidade Im�vel Localidade
																resumoCobrancaSituacaoEspecialConsultaLocalidadeHelper.getTotalQtLigacoesLocalidade()
																				.toString(),

																// Quantidade Im�vel Motivo
																resumoCobrancaSituacaoEspecialConsultaMotivoHelper.getQtLigacoes() == null ? "0"
																				: resumoCobrancaSituacaoEspecialConsultaMotivoHelper.getQtLigacoes().toString(),

																// Quantidade Im�vel Situa��o
																resumoCobrancaSituacaoEspecialConsultaSitTipoHelper.getTotalQtLigacoesSitTipo().toString(),

																// Quantidade Paralisada Geral
																resumoCobrancaSituacaoEspecialConsultaFinalHelper.getTotalGeral().toString(),

																// Quantidade Paralisada Ger�ncia
																resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper.getTotalGerenciaRegional().toString(),

																// Quantidade Paralisada Motivo
																resumoCobrancaSituacaoEspecialConsultaMotivoHelper.getQtParalisada().toString(),

																// Quantidade Paralisada Situa��o
																resumoCobrancaSituacaoEspecialConsultaSitTipoHelper.getTotalSituacaoTipo().toString(),

																// Quantidade Paralisada Localidade
																resumoCobrancaSituacaoEspecialConsultaLocalidadeHelper.getTotalLocalidade().toString(),

																// Situa��o
																resumoCobrancaSituacaoEspecialConsultaSitTipoHelper.getSituacaoTipoDescricao(),
																
																// Cobran�a Estimado Unidade Neg�cio
																resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper.getTotalFatEstimadoUnidadeNegocio() == null ? ""
																				: Util.formatarMoedaReal(resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper
																								.getTotalFatEstimadoUnidadeNegocio()),

												                // Unidade Neg�cio
												                resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper.getDsUnidadeNegocio().toString()
																+ " - "
																+ resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper
																				.getDsUnidadeNegocio(),

																// Percentual Unidade Neg�cio
												                resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper.getTotalPercentualUnidadeNegocio() == null ? ""
												                				: Util.formatarMoedaReal(resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper
												                								.getTotalPercentualUnidadeNegocio()),
																								
																// Quantidade Im�vel Unidade Neg�cio
																resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper.getTotalQtLigacoesUnidadeNegocio()
																				.toString(),
																				
																// Quantidade Paralisada Unidade Neg�cio
																resumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper.getTotalUnidadeNegocio().toString());

								// Fim da Constru��o do objeto
								// RelatorioDadosEconomiaImovelBean
								// para ser colocado no relat�rio

								// adiciona o bean a cole��o
								relatorioBeans.add(relatorioBean);

							}
						}

					}
				}

			}
		}

		// Organizar a cole��o

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

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("nomeRelatorio", nomeRelatorio);

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		RelatorioDataSource ds = new RelatorioDataSource((List) relatorioBeans);

		// exporta o relat�rio em pdf e retorna o array de bytes

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_RESUMO_FATURAMENTO_SITUACAO_ESPECIAL, parametros, ds,
						tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RESUMO_FATURAMENTO_SITUACAO_ESPECIAL, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
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
