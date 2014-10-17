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

package gcom.relatorio.faturamento;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.EmitirHistogramaAguaDetalheHelper;
import gcom.faturamento.bean.EmitirHistogramaAguaHelper;
import gcom.faturamento.bean.FiltrarEmitirHistogramaAguaHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * classe responsável por criar o relatório de histograma de ligação de agua
 * 
 * @author Rafael Pinto / Rafael Correa
 * @created 07/06/2007
 * @author Saulo Lima
 * @date 14/09/2009
 *       Alteração na forma de exibição dos percentuais.
 */
public class RelatorioHistogramaAguaLigacao
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	private static final int CASAS_DECIMAIS_PERCENTUAL = 2;

	public RelatorioHistogramaAguaLigacao(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_EMITIR_HISTOGRAMA_AGUA_LIGACAO);
	}

	@Deprecated
	public RelatorioHistogramaAguaLigacao() {

		super(null, "");
	}

	/**
	 * <<Descrição do método>>
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

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		FiltrarEmitirHistogramaAguaHelper filtrarEmitirHistogramaAguaHelper = (FiltrarEmitirHistogramaAguaHelper) getParametro("filtrarEmitirHistogramaAguaHelper");

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioHistogramaAguaLigacaoBean relatorioHistogramaAguaLigacaoBean = null;

		Collection<EmitirHistogramaAguaHelper> colecao = fachada.pesquisarEmitirHistogramaAgua(filtrarEmitirHistogramaAguaHelper);

		// se a coleção de parâmetros da analise não for vazia
		if(colecao != null && !colecao.isEmpty()){

			// coloca a coleção de parâmetros da analise no iterator
			Iterator<EmitirHistogramaAguaHelper> colecaoIterator = colecao.iterator();

			// laço para criar a coleção de parâmetros da analise
			while(colecaoIterator.hasNext()){

				EmitirHistogramaAguaHelper emitirHistogramaAguaHelper = colecaoIterator.next();

				String opcaoTotalizacao = emitirHistogramaAguaHelper.getOpcaoTotalizacao();
				String descricao = emitirHistogramaAguaHelper.getDescricaoTitulo();

				Collection<EmitirHistogramaAguaDetalheHelper> colecaoDetalhe = emitirHistogramaAguaHelper
								.getColecaoEmitirHistogramaAguaDetalhe();

				String numeroLigacoes = null;
				String percentualParcialLigacao = null;
				String percentualAcumuladoLigacao = null;
				String economias = null;
				String volumeEstimado = null;
				String volumeMedido = null;
				String volumeTotal = null;
				String percentualParcialConsumo = null;
				String percentualAcumuladoConsumo = null;
				String mediaConsumo = null;
				String valorFaturamento = null;
				String percentualParcialFaturamento = null;
				String percentualAcumuladoFaturamento = null;

				NumberFormat formato = NumberFormat.getInstance(new Locale("pt", "BR"));

				if(colecaoDetalhe != null && !colecaoDetalhe.isEmpty()){

					Iterator<EmitirHistogramaAguaDetalheHelper> colecaoDetalheIterator = colecaoDetalhe.iterator();
					while(colecaoDetalheIterator.hasNext()){

						EmitirHistogramaAguaDetalheHelper detalhe = colecaoDetalheIterator.next();

						String categoria = detalhe.getDescricaoCategoria();
						numeroLigacoes = formato.format(detalhe.getQuantidadeLigacoes());

						if(detalhe.getPercentualParcialLigacao() > 100){
							percentualParcialLigacao = Util.formatarPercentual(100,
											RelatorioHistogramaAguaLigacao.CASAS_DECIMAIS_PERCENTUAL);
						}else{
							percentualParcialLigacao = Util.formatarPercentual(detalhe.getPercentualParcialLigacao(),
											RelatorioHistogramaAguaLigacao.CASAS_DECIMAIS_PERCENTUAL);
						}

						percentualAcumuladoLigacao = "";
						if(!descricao.contains("TOTAL")){
							if(detalhe.getPercentualAcumuladoLigacao() > 100){
								percentualAcumuladoLigacao = Util.formatarPercentual(100,
												RelatorioHistogramaAguaLigacao.CASAS_DECIMAIS_PERCENTUAL);
							}else{
								percentualAcumuladoLigacao = Util.formatarPercentual(detalhe.getPercentualAcumuladoLigacao(),
												RelatorioHistogramaAguaLigacao.CASAS_DECIMAIS_PERCENTUAL);
							}
						}

						economias = formato.format(detalhe.getQuantidadeEconomias());
						volumeEstimado = "";
						if(detalhe.getQuantidadeVolumeEstimado() != 0){
							volumeEstimado = formato.format(detalhe.getQuantidadeVolumeEstimado());
						}

						volumeMedido = "";
						if(detalhe.getQuantidadeVolumeMedido() != 0){
							volumeMedido = formato.format(detalhe.getQuantidadeVolumeMedido());
						}

						volumeTotal = formato.format(detalhe.getQuantidadeVolumeTotal());

						percentualParcialConsumo = Util.formatarPercentual(detalhe.getPercentualParcialConsumo(),
										RelatorioHistogramaAguaLigacao.CASAS_DECIMAIS_PERCENTUAL);

						percentualAcumuladoConsumo = "";
						if(!descricao.contains("TOTAL")){
							if(detalhe.getPercentualAcumuladoConsumo() > 100){
								percentualAcumuladoConsumo = Util.formatarPercentual(100,
												RelatorioHistogramaAguaLigacao.CASAS_DECIMAIS_PERCENTUAL);
							}else{
								percentualAcumuladoConsumo = Util.formatarPercentual(detalhe.getPercentualAcumuladoConsumo(),
												RelatorioHistogramaAguaLigacao.CASAS_DECIMAIS_PERCENTUAL);
							}
						}

						mediaConsumo = Util.formatarMoedaReal(new BigDecimal(String.valueOf(detalhe.getMediaConsumo())));

						valorFaturamento = Util.formatarMoedaReal(detalhe.getValorFaturado());

						percentualParcialFaturamento = Util.formatarPercentual(detalhe.getPercentualParcialFaturamento(),
										RelatorioHistogramaAguaLigacao.CASAS_DECIMAIS_PERCENTUAL);

						percentualAcumuladoFaturamento = "";
						if(!descricao.contains("TOTAL")){
							if(detalhe.getPercentualAcumuladoFaturamento() > 100){
								percentualAcumuladoFaturamento = Util.formatarPercentual(100,
												RelatorioHistogramaAguaLigacao.CASAS_DECIMAIS_PERCENTUAL);
							}else{
								percentualAcumuladoFaturamento = Util.formatarPercentual(detalhe.getPercentualAcumuladoFaturamento(),
												RelatorioHistogramaAguaLigacao.CASAS_DECIMAIS_PERCENTUAL);
							}
						}

						relatorioHistogramaAguaLigacaoBean = new RelatorioHistogramaAguaLigacaoBean(
						// Opção de Totalização
										opcaoTotalizacao,
										// Descrição
										descricao,
										// Categoria
										categoria,
										// Número de Ligações
										numeroLigacoes,
										// Percentual Parcial da Ligação
										percentualParcialLigacao,
										// Percentual Acumulado da Ligação
										percentualAcumuladoLigacao,
										// Economias
										economias,
										// Volume Medido
										volumeMedido,
										// Volume Estimado
										volumeEstimado,
										// Volume Total
										volumeTotal,
										// Percentual Parcial do Consumo
										percentualParcialConsumo,
										// Percentual Acumulado do Consumo
										percentualAcumuladoConsumo,
										// Média de Consumo
										mediaConsumo,
										// Valor do Faturamento
										valorFaturamento,
										// Percentual Parcial do Faturamento
										percentualParcialFaturamento,
										// Percentual Acumulado do Faturamento
										percentualAcumuladoFaturamento);

						// adiciona o bean a coleção
						relatorioBeans.add(relatorioHistogramaAguaLigacaoBean);
					}
				}

				numeroLigacoes = formato.format(emitirHistogramaAguaHelper.getTotalQuantidadeLigacoes());
				percentualParcialLigacao = Util.formatarPercentual(emitirHistogramaAguaHelper.getTotalPercentualParcialLigacao(),
								RelatorioHistogramaAguaLigacao.CASAS_DECIMAIS_PERCENTUAL);

				percentualAcumuladoLigacao = "";
				if(!descricao.contains("TOTAL")){
					if(emitirHistogramaAguaHelper.getTotalPercentualAcumuladoLigacao() > 100){
						percentualAcumuladoLigacao = Util.formatarPercentual(100, RelatorioHistogramaAguaLigacao.CASAS_DECIMAIS_PERCENTUAL);
					}else{
						percentualAcumuladoLigacao = Util.formatarPercentual(emitirHistogramaAguaHelper
										.getTotalPercentualAcumuladoLigacao(), RelatorioHistogramaAguaLigacao.CASAS_DECIMAIS_PERCENTUAL);
					}
				}

				economias = formato.format(emitirHistogramaAguaHelper.getTotalQuantidadeEconomias());
				volumeEstimado = "";
				if(emitirHistogramaAguaHelper.getTotalQuantidadeVolumeEstimado() != 0){
					volumeEstimado = formato.format(emitirHistogramaAguaHelper.getTotalQuantidadeVolumeEstimado());
				}

				volumeMedido = "";
				if(emitirHistogramaAguaHelper.getTotalQuantidadeVolumeMedido() != 0){
					volumeMedido = formato.format(emitirHistogramaAguaHelper.getTotalQuantidadeVolumeMedido());
				}

				volumeTotal = formato.format(emitirHistogramaAguaHelper.getTotalQuantidadeVolumeTotal());

				percentualParcialConsumo = Util.formatarPercentual(emitirHistogramaAguaHelper.getTotalPercentualParcialConsumo(),
								RelatorioHistogramaAguaLigacao.CASAS_DECIMAIS_PERCENTUAL);

				percentualAcumuladoConsumo = "";
				if(!descricao.contains("TOTAL")){
					if(emitirHistogramaAguaHelper.getTotalPercentualAcumuladoConsumo() > 100){
						percentualAcumuladoConsumo = Util.formatarPercentual(100, RelatorioHistogramaAguaLigacao.CASAS_DECIMAIS_PERCENTUAL);
					}else{
						percentualAcumuladoConsumo = Util.formatarPercentual(emitirHistogramaAguaHelper
										.getTotalPercentualAcumuladoConsumo(), RelatorioHistogramaAguaLigacao.CASAS_DECIMAIS_PERCENTUAL);
					}
				}

				mediaConsumo = Util.formatarMoedaReal(new BigDecimal(String.valueOf(emitirHistogramaAguaHelper.getTotalMediaConsumo())));

				valorFaturamento = Util.formatarMoedaReal(emitirHistogramaAguaHelper.getTotalValorFaturado());

				percentualParcialFaturamento = Util.formatarPercentual(emitirHistogramaAguaHelper.getTotalPercentualParcialFaturamento(),
								RelatorioHistogramaAguaLigacao.CASAS_DECIMAIS_PERCENTUAL);

				percentualAcumuladoFaturamento = "";
				if(!descricao.contains("TOTAL")){
					if(emitirHistogramaAguaHelper.getTotalPercentualAcumuladoFaturamento() > 100){
						percentualAcumuladoFaturamento = Util.formatarPercentual(100,
										RelatorioHistogramaAguaLigacao.CASAS_DECIMAIS_PERCENTUAL);
					}else{
						percentualAcumuladoFaturamento = Util
										.formatarPercentual(emitirHistogramaAguaHelper.getTotalPercentualAcumuladoFaturamento(),
														RelatorioHistogramaAguaLigacao.CASAS_DECIMAIS_PERCENTUAL);
					}
				}

				relatorioHistogramaAguaLigacaoBean = new RelatorioHistogramaAguaLigacaoBean(
				// Opção de Totalização
								opcaoTotalizacao,
								// Descrição
								descricao,
								// Categoria
								"TOTAL",
								// Número de Ligações
								numeroLigacoes,
								// Percentual Parcial da Ligação
								percentualParcialLigacao,
								// Percentual Acumulado da Ligação
								percentualAcumuladoLigacao,
								// Economias
								economias,
								// Volume Medido
								volumeMedido,
								// Volume Estimado
								volumeEstimado,
								// Volume Total
								volumeTotal,
								// Percentual Parcial do Consumo
								percentualParcialConsumo,
								// Percentual Acumulado do Consumo
								percentualAcumuladoConsumo,
								// Média de Consumo
								mediaConsumo,
								// Valor do Faturamento
								valorFaturamento,
								// Percentual Parcial do Faturamento
								percentualParcialFaturamento,
								// Percentual Acumulado do Faturamento
								percentualAcumuladoFaturamento);

				// adiciona o bean a coleção
				relatorioBeans.add(relatorioHistogramaAguaLigacaoBean);
			}
		}

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());
		parametros.put("anoMes", Util.formatarAnoMesParaMesAno(filtrarEmitirHistogramaAguaHelper.getMesAnoFaturamento()));

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_EMITIR_HISTOGRAMA_AGUA_LIGACAO, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.HISTOGRAMA_AGUA_LIGACAO, idFuncionalidadeIniciada, null);
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

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioHistogramaAguaLigacao", this);

	}

}