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

package gcom.relatorio.atendimentopublico;

import gcom.atendimentopublico.ordemservico.Equipe;
import gcom.atendimentopublico.ordemservico.FiltroEquipe;
import gcom.batch.Relatorio;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.*;

/**
 * [UC0XXX] Gerar Relatório Resumo de Ordem de Serviço Encerradas
 * Obter dados para gerar Relatório Resumo de Ordem de Serviço Encerradas
 * 
 * @author Anderson Italo
 * @date 13/05/2011
 */
public class RelatorioResumoOrdensServicoEncerradas
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioResumoOrdensServicoEncerradas(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_RESUMO_ORDENS_SERVICO_ENCERRADAS);
	}

	@Deprecated
	public RelatorioResumoOrdensServicoEncerradas() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;

		// obtêm os parâmetros passados
		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		String origemServico = (String) getParametro("origemServico");
		String situacaoOS = (String) getParametro("situacaoOS");
		String[] idsServicosTipos = (String[]) getParametro("idsServicosTipos");
		String idUnidadeAtendimento = (String) getParametro("idUnidadeAtendimento");
		String idUnidadeAtual = (String) getParametro("idUnidadeAtual");
		String idUnidadeEncerramento = (String) getParametro("idUnidadeEncerramento");
		Date periodoAtendimentoInicial = (Date) getParametro("periodoAtendimentoInicial");
		Date periodoAtendimentoFinal = (Date) getParametro("periodoAtendimentoFinal");
		Date periodoEncerramentoInicial = (Date) getParametro("periodoEncerramentoInicial");
		Date periodoEncerramentoFinal = (Date) getParametro("periodoEncerramentoFinal");
		String idEquipeProgramacao = (String) getParametro("idEquipeProgramacao");
		String idEquipeExecucao = (String) getParametro("idEquipeExecucao");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		String idLocalidade = (String) getParametro("idLocalidade");

		// monta a coleção de beans do relatório
		List relatorioBeans = new ArrayList();
		Fachada fachada = Fachada.getInstancia();

		// obtém a coleção com dados do detalhe do relatório
		List colecaoDadosRelatorio = (List) fachada.pesquisaRelatorioResumoOsEncerradas(origemServico, situacaoOS, idsServicosTipos,
						idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial, periodoAtendimentoFinal,
						periodoEncerramentoInicial, periodoEncerramentoFinal, idEquipeProgramacao, idEquipeExecucao, idLocalidade);

		// processamento dos dados do detalhe do relatório
		Integer qtdEncerradaPorUnidade = 0;
		Integer qtdEncerradaComExecucaoPorUnidade = 0;
		Integer qtdEncerradaOutrosPorUnidade = 0;

		Integer qtdEncerradaNoPrazoPorUnidade = 0;
		Integer qtdEncerradaForaPrazoPorUnidade = 0;
		Map<String, String> idUnidadeQuantidade = new HashMap<String, String>();

		for(int i = 0; i < colecaoDadosRelatorio.size(); i++){

			Object[] dados = (Object[]) colecaoDadosRelatorio.get(i);

			RelatorioResumoOrdensServicoEncerradasBean bean = new RelatorioResumoOrdensServicoEncerradasBean();

			// Cabeçalho

			// Id da Unidade
			bean.setIdUnidade(dados[0].toString());

			// Descricao da Unidade
			bean.setDescricaoUnidade(dados[1].toString());

			// Linha Detalhe

			// Id do Tipo de Serviço
			bean.setIdTipoServico(dados[2].toString());

			// Descrição do Tipo de Serviço
			bean.setDescricaoTipoServico(dados[3].toString());

			// Qtd Encerrada por Tipo de Serviço
			bean.setQtdEncerradaPorTipoServico(dados[4].toString());
			qtdEncerradaPorUnidade += Integer.valueOf(dados[4].toString());

			// Qtd Encerrada Com Execução por Tipo de Serviço
			Integer qtdEncerradaComExecucao = (Integer) dados[5];
			bean.setQtdEncerradaComExecucaoPorTipoServico(qtdEncerradaComExecucao.toString());
			qtdEncerradaComExecucaoPorUnidade += Integer.valueOf(qtdEncerradaComExecucao.toString());

			// Qtd Encerrada Outros por Tipo de Serviço
			bean.setQtdEncerradaOutrosPorTipoServico(dados[6].toString());
			qtdEncerradaOutrosPorUnidade += Integer.valueOf(dados[6].toString());

			// Qtd Encerrada No Prazo por Tipo de Serviço
			Integer qtdNoPrazo = (Integer) dados[7];
			bean.setQtdEncerradaComExecucaoNoPrazoPorTipoServico(qtdNoPrazo.toString());
			qtdEncerradaNoPrazoPorUnidade += qtdNoPrazo;

			// Qtd Encerrada Fora Prazo por Tipo de Serviço
			Integer qtdForaPrazo = (Integer) dados[8];
			bean.setQtdEncerradaComExecucaoForaPrazoPorTipoServico(qtdForaPrazo.toString());
			qtdEncerradaForaPrazoPorUnidade += qtdForaPrazo;

			// %Execucao "No Prazo" e "Fora Prazo"
			if(!qtdEncerradaComExecucao.equals(Integer.valueOf(0))){

				BigDecimal quantidadeEncerrada = new BigDecimal(qtdEncerradaComExecucao);

				BigDecimal percentualNoPrazo = (new BigDecimal(qtdNoPrazo).multiply(new BigDecimal("100"))).divide(quantidadeEncerrada, 2,
								BigDecimal.ROUND_HALF_UP);
				bean.setPercentualEncerradaComExecucaoNoPrazoPorTipoServico(Util.formataBigDecimal(percentualNoPrazo, 2, false));

				BigDecimal percentualForaPrazo = (new BigDecimal(qtdForaPrazo).multiply(new BigDecimal("100"))).divide(quantidadeEncerrada,
								2, BigDecimal.ROUND_HALF_UP);
				bean.setPercentualEncerradaComExecucaoForaPrazoPorTipoServico(Util.formataBigDecimal(percentualForaPrazo, 2, false));

			}else{
				bean.setPercentualEncerradaComExecucaoNoPrazoPorTipoServico("0,00");
				bean.setPercentualEncerradaComExecucaoForaPrazoPorTipoServico("0,00");
			}

			// verifica quebras por unidade e totaliza
			if(i == (colecaoDadosRelatorio.size() - 1)){

				bean.setQtdEncerradaComExecucaoPorUnidade((qtdEncerradaComExecucaoPorUnidade.toString()));
				bean.setQtdEncerradaOutrosPorUnidade(qtdEncerradaOutrosPorUnidade.toString());
				bean.setQtdEncerradaPorUnidade(qtdEncerradaPorUnidade.toString());
				bean.setQtdEncerradaComExecucaoNoPrazoPorUnidade(qtdEncerradaNoPrazoPorUnidade.toString());
				bean.setQtdEncerradaComExecucaoForaPrazoPorUnidade(qtdEncerradaForaPrazoPorUnidade.toString());

				Integer qtdIncluir = 0;

				if(idUnidadeQuantidade.get(bean.getIdUnidade()) != null){

					qtdIncluir += qtdEncerradaPorUnidade + Integer.valueOf(idUnidadeQuantidade.get(bean.getIdUnidade()));
					idUnidadeQuantidade.put(bean.getIdUnidade(), qtdIncluir.toString());
				}else{

					idUnidadeQuantidade.put(bean.getIdUnidade(), qtdEncerradaPorUnidade.toString());
				}

			}else{

				Object[] dadosAux = (Object[]) colecaoDadosRelatorio.get(i + 1);

				if(!dados[0].toString().equals(dadosAux[0].toString())){

					bean.setQtdEncerradaComExecucaoPorUnidade((qtdEncerradaComExecucaoPorUnidade.toString()));
					bean.setQtdEncerradaOutrosPorUnidade(qtdEncerradaOutrosPorUnidade.toString());
					bean.setQtdEncerradaPorUnidade(qtdEncerradaPorUnidade.toString());
					bean.setQtdEncerradaComExecucaoNoPrazoPorUnidade(qtdEncerradaNoPrazoPorUnidade.toString());
					bean.setQtdEncerradaComExecucaoForaPrazoPorUnidade(qtdEncerradaForaPrazoPorUnidade.toString());
					Integer qtdIncluir = 0;

					if(idUnidadeQuantidade.get(bean.getIdUnidade()) != null){

						qtdIncluir += qtdEncerradaPorUnidade + Integer.valueOf(idUnidadeQuantidade.get(bean.getIdUnidade()));
						idUnidadeQuantidade.put(bean.getIdUnidade(), qtdIncluir.toString());
					}else{

						idUnidadeQuantidade.put(bean.getIdUnidade(), qtdEncerradaPorUnidade.toString());
					}

					qtdEncerradaPorUnidade = 0;
					qtdEncerradaComExecucaoPorUnidade = 0;
					qtdEncerradaOutrosPorUnidade = 0;
					qtdEncerradaNoPrazoPorUnidade = 0;
					qtdEncerradaForaPrazoPorUnidade = 0;
				}
			}

			relatorioBeans.add(bean);
		}

		// preenche os valores dos percentuais
		for(int i = 0; i < relatorioBeans.size(); i++){

			RelatorioResumoOrdensServicoEncerradasBean bean = (RelatorioResumoOrdensServicoEncerradasBean) relatorioBeans.get(i);

			// totaliza percentual por tipo de serviço
			BigDecimal percentualPorTipoServico = null;

			if(!bean.getQtdEncerradaPorTipoServico().equals("0")){

				// %TOT.
				BigDecimal quantidadeEncerradaPorUnidade = new BigDecimal(idUnidadeQuantidade.get(bean.getIdUnidade()));
				BigDecimal quantidadeEncerradaPorTipoServico = new BigDecimal(bean.getQtdEncerradaPorTipoServico());
				quantidadeEncerradaPorTipoServico = quantidadeEncerradaPorTipoServico.multiply(new BigDecimal("100"));
				percentualPorTipoServico = quantidadeEncerradaPorTipoServico.divide(quantidadeEncerradaPorUnidade, 2,
								BigDecimal.ROUND_HALF_UP);
				bean.setPercentualPorTipoServico(Util.formataBigDecimal(percentualPorTipoServico, 2, false));

				// %EXEC.
				percentualPorTipoServico = null;
				quantidadeEncerradaPorTipoServico = new BigDecimal(bean.getQtdEncerradaPorTipoServico());
				BigDecimal quantidadeEncComExecPorTipoServico = new BigDecimal(bean.getQtdEncerradaComExecucaoPorTipoServico());
				quantidadeEncComExecPorTipoServico = quantidadeEncComExecPorTipoServico.multiply(new BigDecimal("100"));
				percentualPorTipoServico = quantidadeEncComExecPorTipoServico.divide(quantidadeEncerradaPorTipoServico, 2,
								BigDecimal.ROUND_HALF_UP);
				bean.setPercentualComExecucaoPorTipoServico(Util.formataBigDecimal(percentualPorTipoServico, 2, false));

				// %OUTROS
				percentualPorTipoServico = null;
				BigDecimal quantidadeEncOutrosPorTipoServico = new BigDecimal(bean.getQtdEncerradaOutrosPorTipoServico());
				quantidadeEncOutrosPorTipoServico = quantidadeEncOutrosPorTipoServico.multiply(new BigDecimal("100"));
				percentualPorTipoServico = quantidadeEncOutrosPorTipoServico.divide(quantidadeEncerradaPorTipoServico, 2,
								BigDecimal.ROUND_HALF_UP);
				bean.setPercentualOutrosPorTipoServico(Util.formataBigDecimal(percentualPorTipoServico, 2, false));

			}else{

				bean.setPercentualPorTipoServico("0,00");
				bean.setPercentualComExecucaoPorTipoServico("0,00");
				bean.setPercentualOutrosPorTipoServico("0,00");
			}

			// caso seja a linha da totalização por unidade
			if(bean.getQtdEncerradaPorUnidade() != null){

				// totaliza percentual por unidade
				BigDecimal percentualPorUnidade = null;

				if(!bean.getQtdEncerradaPorUnidade().equals("0")){

					// %TOT.
					BigDecimal quantidadeEncerradaPorUnidade = new BigDecimal(bean.getQtdEncerradaPorUnidade());
					bean.setPercentualPorUnidade("100,00");

					// %EXEC.
					percentualPorUnidade = null;
					BigDecimal quantidadeEncComExecPorUnidade = new BigDecimal(bean.getQtdEncerradaComExecucaoPorUnidade());

					quantidadeEncComExecPorUnidade = quantidadeEncComExecPorUnidade.multiply(new BigDecimal("100"));
					percentualPorUnidade = quantidadeEncComExecPorUnidade
									.divide(quantidadeEncerradaPorUnidade, 2, BigDecimal.ROUND_HALF_UP);
					bean.setPercentualComExecucaoPorUnidade((Util.formataBigDecimal(percentualPorUnidade, 2, false)));

					// %OUTROS
					percentualPorUnidade = null;
					BigDecimal quantidadeEncOutrosPorUnidade = new BigDecimal(bean.getQtdEncerradaOutrosPorUnidade());

					quantidadeEncOutrosPorUnidade = quantidadeEncOutrosPorUnidade.multiply(new BigDecimal("100"));
					percentualPorUnidade = quantidadeEncOutrosPorUnidade.divide(quantidadeEncerradaPorUnidade, 2, BigDecimal.ROUND_HALF_UP);
					bean.setPercentualOutrosPorUnidade((Util.formataBigDecimal(percentualPorUnidade, 2, false)));

					if(!bean.getQtdEncerradaComExecucaoPorUnidade().equals("0")){

						BigDecimal quantidadeEncerradaComExecucaoPorUnidade = new BigDecimal(bean.getQtdEncerradaComExecucaoPorUnidade());

						// %No Prazo
						percentualPorUnidade = null;
						BigDecimal qtdEncerradaComExecucaoNoPrazoPorUnidade = new BigDecimal(
										bean.getQtdEncerradaComExecucaoNoPrazoPorUnidade());

						qtdEncerradaComExecucaoNoPrazoPorUnidade = qtdEncerradaComExecucaoNoPrazoPorUnidade.multiply(new BigDecimal("100"));
						percentualPorUnidade = qtdEncerradaComExecucaoNoPrazoPorUnidade.divide(quantidadeEncerradaComExecucaoPorUnidade, 2,
										BigDecimal.ROUND_HALF_UP);
						bean.setPercentualEncerradaComExecucaoNoPrazoPorUnidade(Util.formataBigDecimal(percentualPorUnidade, 2, false));

						// %Fora Prazo
						percentualPorUnidade = null;
						BigDecimal qtdEncerradaComExecucaoForaPrazoPorUnidade = new BigDecimal(
										bean.getQtdEncerradaComExecucaoForaPrazoPorUnidade());

						qtdEncerradaComExecucaoForaPrazoPorUnidade = qtdEncerradaComExecucaoForaPrazoPorUnidade.multiply(new BigDecimal(
										"100"));
						percentualPorUnidade = qtdEncerradaComExecucaoForaPrazoPorUnidade.divide(quantidadeEncerradaComExecucaoPorUnidade,
										2, BigDecimal.ROUND_HALF_UP);
						bean.setPercentualEncerradaComExecucaoForaPrazoPorUnidade(Util.formataBigDecimal(percentualPorUnidade, 2, false));
					}else{
						bean.setPercentualEncerradaComExecucaoNoPrazoPorUnidade("0,00");
						bean.setPercentualEncerradaComExecucaoForaPrazoPorUnidade("0,00");
					}

				}else{
					bean.setPercentualPorUnidade("0,00");
					bean.setPercentualComExecucaoPorUnidade("0,00");
					bean.setPercentualOutrosPorUnidade("0,00");
					bean.setPercentualEncerradaComExecucaoNoPrazoPorUnidade("0,00");
					bean.setPercentualEncerradaComExecucaoForaPrazoPorUnidade("0,00");
				}
			}

			relatorioBeans.set(i, bean);
		}

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// Período de Encerramento
		String periodoEncerramento = "";
		if(periodoEncerramentoInicial != null && periodoEncerramentoFinal != null){

			periodoEncerramento = "Período de Encerramento: " + Util.formatarData(periodoEncerramentoInicial) + " a "
							+ Util.formatarData(periodoEncerramentoFinal);
		}
		// Período de Atendimento
		String periodoAtendimento = "";
		if(periodoAtendimentoInicial != null && periodoAtendimentoFinal != null){

			periodoAtendimento = "Período de Atendimento: " + Util.formatarData(periodoAtendimentoInicial) + " a "
							+ Util.formatarData(periodoAtendimentoFinal);
		}

		// Unidade Atual
		String unidadeAtual = "";
		if(Util.validarStringNumerica(idUnidadeAtual)){

			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, Integer
							.valueOf(idUnidadeAtual)));

			Collection colecaoUnidadeOrganizacional = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

			unidadeAtual = "Unidade Atual: " + unidadeOrganizacional.getId().toString() + " - " + unidadeOrganizacional.getDescricao();
		}

		// Unidade atendimento
		String unidadeAtendimento = "";
		if(Util.validarStringNumerica(idUnidadeAtendimento)){

			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, Integer
							.valueOf(idUnidadeAtendimento)));

			Collection colecaoUnidadeOrganizacional = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

			unidadeAtendimento = "Unidade Atendimento: " + unidadeOrganizacional.getId().toString() + " - "
							+ unidadeOrganizacional.getDescricao();
		}

		// Unidade Encerramento
		String unidadeEncerramento = "";
		if(Util.validarStringNumerica(idUnidadeEncerramento)){

			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, Integer
							.valueOf(idUnidadeEncerramento)));

			Collection colecaoUnidadeOrganizacional = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

			unidadeEncerramento = "Unidade Encerramento: " + unidadeOrganizacional.getId().toString() + " - "
							+ unidadeOrganizacional.getDescricao();
		}

		// Localidade
		String localidade = "";
		if(Util.validarStringNumerica(idLocalidade)){

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Integer.valueOf(idLocalidade)));

			Collection colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			Localidade local = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			localidade = "Localidade: " + local.getId().toString() + " - " + local.getDescricao();
		}

		String origem = "";
		if(origemServico.equals(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO.toString())){
			parametros.put("tipoUnidadeAgrupamento", "Atendimento");
			origem = "Solicitados";
		}else{
			parametros.put("tipoUnidadeAgrupamento", "Encerramento");
			origem = "Seletivos";
		}

		String situacaoOSStr = "Ambos";

		if(Util.isNaoNuloBrancoZero(situacaoOS)){
			if(ConstantesSistema.SITUACAO_REFERENCIA_ENCERRADA == Integer.valueOf(situacaoOS).intValue()){
				situacaoOSStr = "Encerrados";
			}else if(ConstantesSistema.SITUACAO_REFERENCIA_ENCERRADA == Integer.valueOf(situacaoOS).intValue()){
				situacaoOSStr = "Pendentes";
			}
		}

		String equipeProgramacao = "";
		if(Util.isNaoNuloBrancoZero(idEquipeProgramacao)){
			FiltroEquipe filtroEquipe = new FiltroEquipe();
			filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.ID, Integer.valueOf(idEquipeProgramacao)));

			Collection colecaoEquipe = fachada.pesquisar(filtroEquipe, Equipe.class.getName());

			Equipe equipe = (Equipe) Util.retonarObjetoDeColecao(colecaoEquipe);

			equipeProgramacao = "Equipe de Prog.: " + equipe.getId().toString() + " - " + equipe.getNome();
		}

		String equipeExecucao = "";
		if(Util.isNaoNuloBrancoZero(idEquipeExecucao)){
			FiltroEquipe filtroEquipe = new FiltroEquipe();
			filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.ID, Integer.valueOf(idEquipeExecucao)));

			Collection colecaoEquipe = fachada.pesquisar(filtroEquipe, Equipe.class.getName());

			Equipe equipe = (Equipe) Util.retonarObjetoDeColecao(colecaoEquipe);

			equipeExecucao = "Equipe de Exec.: " + equipe.getId().toString() + " - " + equipe.getNome();
		}

		// adiciona os parâmetros do relatório
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("localidade", localidade);
		parametros.put("periodoEncerramento", periodoEncerramento);
		parametros.put("periodoAtendimento", periodoAtendimento);
		parametros.put("unidadeAtual", unidadeAtual);
		parametros.put("unidadeAtendimento", unidadeAtendimento);
		parametros.put("unidadeEncerramento", unidadeEncerramento);
		parametros.put("origem", "Origem dos Serviços: " + origem);
		parametros.put("situacaoOS", "Sit. da Ordem de Serviço: " + situacaoOSStr);
		parametros.put("equipeProgramacao", equipeProgramacao);
		parametros.put("equipeExecucao", equipeExecucao);

		if(tipoFormatoRelatorio == TIPO_PDF){
			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// processa a geração do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_RESUMO_ORDENS_SERVICO_ENCERRADAS, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_RESUMO_OS_ENCERRADA, idFuncionalidadeIniciada, null);
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

		Fachada fachada = Fachada.getInstancia();

		int retorno = 0;

		String origemServico = (String) getParametro("origemServico");
		String situacaoOS = (String) getParametro("situacaoOS");
		String[] idsServicosTipos = (String[]) getParametro("idsServicosTipos");
		String idUnidadeAtendimento = (String) getParametro("idUnidadeAtendimento");
		String idUnidadeAtual = (String) getParametro("idUnidadeAtual");
		String idUnidadeEncerramento = (String) getParametro("idUnidadeEncerramento");
		Date periodoAtendimentoInicial = (Date) getParametro("periodoAtendimentoInicial");
		Date periodoAtendimentoFinal = (Date) getParametro("periodoAtendimentoFinal");
		Date periodoEncerramentoInicial = (Date) getParametro("periodoEncerramentoInicial");
		Date periodoEncerramentoFinal = (Date) getParametro("periodoEncerramentoFinal");
		String idEquipeProgramacao = (String) getParametro("idEquipeProgramacao");
		String idEquipeExecucao = (String) getParametro("idEquipeExecucao");
		String idLocalidade = (String) getParametro("idLocalidade");

		retorno = fachada.pesquisaTotalRegistrosRelatorioResumoOsEncerradas(origemServico, situacaoOS, idsServicosTipos,
						idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial, periodoAtendimentoFinal,
						periodoEncerramentoInicial, periodoEncerramentoFinal, idEquipeProgramacao, idEquipeExecucao, idLocalidade);

		return retorno;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("Relatorio Resumo de OS Encerradas", this);
	}

}