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
import gcom.faturamento.bean.EmitirHistogramaAguaEsgotoEconomiaDetalheHelper;
import gcom.faturamento.bean.EmitirHistogramaAguaEsgotoEconomiaHelper;
import gcom.faturamento.bean.FiltrarEmitirHistogramaAguaEconomiaHelper;
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
 * classe responsável por criar o relatório de histograma de ligação de agua por economia
 * 
 * @author Rafael Pinto / Rafael Correa
 * @created 18/06/2007
 */
public class RelatorioHistogramaAguaEconomia
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioHistogramaAguaEconomia(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_EMITIR_HISTOGRAMA_AGUA_ECONOMIA);
	}

	@Deprecated
	public RelatorioHistogramaAguaEconomia() {

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

		FiltrarEmitirHistogramaAguaEconomiaHelper filtrarEmitirHistogramaAguaEconomiaHelper = (FiltrarEmitirHistogramaAguaEconomiaHelper) getParametro("filtrarEmitirHistogramaAguaEconomiaHelper");

		filtrarEmitirHistogramaAguaEconomiaHelper.setIdFuncionalidadeIniciada(idFuncionalidadeIniciada);

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioHistogramaAguaEconomiaBean relatorioHistogramaAguaEconomiaBean = null;

		Collection<EmitirHistogramaAguaEsgotoEconomiaHelper> colecao = fachada
						.pesquisarEmitirHistogramaAguaEconomia(filtrarEmitirHistogramaAguaEconomiaHelper);

		// se a coleção de parâmetros da analise não for vazia
		if(colecao != null && !colecao.isEmpty()){

			// coloca a coleção de parâmetros da analise no iterator
			Iterator colecaoIterator = colecao.iterator();

			// Total Geral de Consumo Medio Medido
			BigDecimal totalGeralConsumoMedioMedido = BigDecimal.ZERO;
			// Total Geral de Consumo Medio Nao Medido
			BigDecimal totalGeralConsumoMedioNaoMedido = BigDecimal.ZERO;

			// laço para criar a coleção de parâmetros da analise
			while(colecaoIterator.hasNext()){

				EmitirHistogramaAguaEsgotoEconomiaHelper emitirHistogramaAguaEconomiaHelper = (EmitirHistogramaAguaEsgotoEconomiaHelper) colecaoIterator
								.next();

				String opcaoTotalizacao = emitirHistogramaAguaEconomiaHelper.getOpcaoTotalizacao();
				String descricao = emitirHistogramaAguaEconomiaHelper.getDescricaoCategoria();
				String descricaoTarifa = emitirHistogramaAguaEconomiaHelper.getDescricaoTarifa();

				Collection colecaoDetalhe = emitirHistogramaAguaEconomiaHelper.getColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe();

				String economiasMedido = null;
				String consumoMedioMedido = null;
				String consumoExcedenteMedido = null;
				String volumeConsumoMedido = null;
				String volumeFaturadoMedido = null;
				String receitaMedido = null;
				String ligacoesMedido = null;

				String economiasNaoMedido = null;
				String consumoMedioNaoMedido = null;
				String consumoExcedenteNaoMedido = null;
				String volumeConsumoNaoMedido = null;
				String volumeFaturadoNaoMedido = null;
				String receitaNaoMedido = null;
				String ligacoesNaoMedido = null;

				// Total Consumo Medio Medido
				BigDecimal totalConsumoMedioMedido = BigDecimal.ZERO;
				// Total Consumo Medio Nao Medido
				BigDecimal totalConsumoMedioNaoMedido = BigDecimal.ZERO;

				NumberFormat formato = NumberFormat.getInstance(new Locale("pt", "BR"));

				if(colecaoDetalhe != null && !colecaoDetalhe.isEmpty()){

					Iterator colecaoDetalheIterator = colecaoDetalhe.iterator();

					while(colecaoDetalheIterator.hasNext()){

						EmitirHistogramaAguaEsgotoEconomiaDetalheHelper detalhe = (EmitirHistogramaAguaEsgotoEconomiaDetalheHelper) colecaoDetalheIterator
										.next();

						String faixa = detalhe.getDescricaoFaixa();

						economiasMedido = formato.format(detalhe.getEconomiasMedido());

						if(detalhe.getConsumoMedioMedido() != null){
							consumoMedioMedido = Util.formatarMoedaReal(detalhe.getConsumoMedioMedido());
						}
						if(detalhe.getConsumoExcedenteMedido() != null){
							consumoExcedenteMedido = ("" + detalhe.getConsumoExcedenteMedido()).replace(".", ",");
						}

						volumeConsumoMedido = formato.format(detalhe.getVolumeConsumoFaixaMedido());
						volumeFaturadoMedido = formato.format(detalhe.getVolumeFaturadoFaixaMedido());
						receitaMedido = Util.formatarMoedaReal(detalhe.getReceitaMedido());
						ligacoesMedido = formato.format(detalhe.getLigacoesMedido());

						economiasNaoMedido = formato.format(detalhe.getEconomiasNaoMedido());

						if(detalhe.getConsumoMedioNaoMedido() != null){
							consumoMedioNaoMedido = Util.formatarMoedaReal(detalhe.getConsumoMedioNaoMedido());
						}
						if(detalhe.getConsumoExcedenteNaoMedido() != null){
							consumoExcedenteNaoMedido = ("" + detalhe.getConsumoExcedenteNaoMedido()).replace(".", ",");
						}

						volumeConsumoNaoMedido = formato.format(detalhe.getVolumeConsumoFaixaNaoMedido());
						volumeFaturadoNaoMedido = formato.format(detalhe.getVolumeFaturadoFaixaNaoMedido());
						receitaNaoMedido = Util.formatarMoedaReal(detalhe.getReceitaNaoMedido());
						ligacoesNaoMedido = formato.format(detalhe.getLigacoesNaoMedido());

						totalConsumoMedioMedido = totalConsumoMedioMedido.add(Util.formatarMoedaRealparaBigDecimal(consumoMedioMedido));

						totalConsumoMedioNaoMedido = totalConsumoMedioNaoMedido.add(Util
										.formatarMoedaRealparaBigDecimal(consumoMedioNaoMedido));

						relatorioHistogramaAguaEconomiaBean = new RelatorioHistogramaAguaEconomiaBean(
						// Opção de Totalização
										opcaoTotalizacao,
										// Descrição
										descricao,
										// Descrição da Faixa
										faixa,
										// Número de Economias
										economiasMedido,
										// Consumo Medio para Medido
										consumoMedioMedido,
										// Consumo Excedente para Medido
										consumoExcedenteMedido,
										// Volume Consumo para Medido
										volumeConsumoMedido,
										// Volume Faturado para Medido
										volumeFaturadoMedido,
										// Receita para Medido
										receitaMedido,
										// Número de Economias para Não Medido
										economiasNaoMedido,
										// Consumo Medio para Não Medido
										consumoMedioNaoMedido,
										// Consumo Excedente para Não Medido
										consumoExcedenteNaoMedido,
										// Volume Consumo para Não Medido
										volumeConsumoNaoMedido,
										// Volume Faturado para Não Medido
										volumeFaturadoNaoMedido,
										// Receita para Não Medido
										receitaNaoMedido,
										// Descricao Tarifa
										descricaoTarifa,
										// Ligacoes Medido
										ligacoesMedido,
										// Ligacoes Nao Medido
										ligacoesNaoMedido);

						// adiciona o bean a coleção
						relatorioBeans.add(relatorioHistogramaAguaEconomiaBean);

					}

				}

				String faixa = emitirHistogramaAguaEconomiaHelper.getDescricaoFaixa();

				economiasMedido = formato.format(emitirHistogramaAguaEconomiaHelper.getTotalEconomiasMedido());
				volumeConsumoMedido = formato.format(emitirHistogramaAguaEconomiaHelper.getTotalVolumeConsumoMedido());
				volumeFaturadoMedido = formato.format(emitirHistogramaAguaEconomiaHelper.getTotalVolumeFaturadoMedido());
				receitaMedido = Util.formatarMoedaReal(emitirHistogramaAguaEconomiaHelper.getTotalReceitaMedido());
				ligacoesMedido = formato.format(emitirHistogramaAguaEconomiaHelper.getTotalLigacoesMedido());

				economiasNaoMedido = formato.format(emitirHistogramaAguaEconomiaHelper.getTotalEconomiasNaoMedido());
				volumeConsumoNaoMedido = formato.format(emitirHistogramaAguaEconomiaHelper.getTotalVolumeConsumoNaoMedido());
				volumeFaturadoNaoMedido = formato.format(emitirHistogramaAguaEconomiaHelper.getTotalVolumeFaturadoNaoMedido());
				receitaNaoMedido = Util.formatarMoedaReal(emitirHistogramaAguaEconomiaHelper.getTotalReceitaNaoMedido());
				ligacoesNaoMedido = formato.format(emitirHistogramaAguaEconomiaHelper.getTotalLigacoesNaoMedido());

				totalGeralConsumoMedioMedido = totalGeralConsumoMedioMedido.add(totalConsumoMedioMedido);
				totalGeralConsumoMedioNaoMedido = totalGeralConsumoMedioNaoMedido.add(totalConsumoMedioNaoMedido);

				relatorioHistogramaAguaEconomiaBean = new RelatorioHistogramaAguaEconomiaBean(
				// Opção de Totalização
								opcaoTotalizacao,
								// Descrição
								descricao,
								// Descrição da Faixa
								faixa,
								// Número de Economias
								economiasMedido,
								// Consumo Medio para Medido
								faixa != "TOTAL GERAL" ? Util.formatarMoedaReal(totalConsumoMedioMedido) : Util
												.formatarMoedaReal(totalGeralConsumoMedioMedido),
								// Consumo Excedente para Medido
								null,
								// Volume Consumo para Medido
								volumeConsumoMedido,
								// Volume Faturado para Medido
								volumeFaturadoMedido,
								// Receita para Medido
								receitaMedido,
								// Número de Economias para Não Medido
								economiasNaoMedido,
								// Consumo Medio para Não Medido
								faixa != "TOTAL GERAL" ? Util.formatarMoedaReal(totalConsumoMedioNaoMedido) : Util
												.formatarMoedaReal(totalGeralConsumoMedioNaoMedido),
								// Consumo Excedente para Não Medido
								null,
								// Volume Consumo para Não Medido
								volumeConsumoNaoMedido,
								// Volume Faturado para Não Medido
								volumeFaturadoNaoMedido,
								// Receita para Não Medido
								receitaNaoMedido,
								// Descricao Tarifa
								descricaoTarifa,
								// Ligacoes Medido
								ligacoesMedido,
								// Ligacoes Nao Medido
								ligacoesNaoMedido);

				// adiciona o bean a coleção
				relatorioBeans.add(relatorioHistogramaAguaEconomiaBean);

			}
		}
		// __________________________________________________________________

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		// adiciona o laudo da análise

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("anoMes", Util.formatarAnoMesParaMesAno(filtrarEmitirHistogramaAguaEconomiaHelper.getMesAnoFaturamento()));
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_EMITIR_HISTOGRAMA_AGUA_ECONOMIA, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.HISTOGRAMA_AGUA_POR_ECONOMIA, idFuncionalidadeIniciada, null);
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

		AgendadorTarefas.agendarTarefa("RelatorioHistogramaAguaEconomia", this);

	}

}