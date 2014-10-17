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

package gcom.relatorio.financeiro;

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * [UC3077] Gerar Relatório Posição de Contas a Receber Contábil
 * 
 * @author Anderson Italo
 * @date 20/11/2012
 */
public class RelatorioPosicaoContasAReceberContabil
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioPosicaoContasAReceberContabil(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_POSICAO_CONTAS_A_RECEBER_CONTABIL);
	}

	@Deprecated
	public RelatorioPosicaoContasAReceberContabil() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		FiltroRelatorioPosicaoContasAReceberContabil filtro = (FiltroRelatorioPosicaoContasAReceberContabil) getParametro("filtroRelatorioPosicaoContasAReceberContabil");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		String opcaoTotalizacao = (String) getParametro("opcaoTotalizacao");
		byte[] retorno = null;

		// Parâmetros do relatório
		Map parametros = new HashMap();
		Fachada fachada = Fachada.getInstancia();
		filtro.setOpcaoTotalizacao(opcaoTotalizacao);
		Collection<RelatorioPosicaoContasAReceberContabilBean> colecaoBeans = new ArrayList<RelatorioPosicaoContasAReceberContabilBean>();

		// Varáveis de Tratamentos de Registros
		String idCategoria = null;
		String descricaoCategoria = null;
		String idLocalidade = null;
		String nomeLocalidade = null;
		String idGerencia = null;
		String nomeGerencia = null;
		String tipoLancamento = null;
		BigDecimal valorContasAVencer = BigDecimal.ZERO;
		BigDecimal valorContasAtrasoAte30Dias = BigDecimal.ZERO;
		BigDecimal valorContasAtraso30A60Dias = BigDecimal.ZERO;
		BigDecimal valorContasAtraso60A90Dias = BigDecimal.ZERO;
		BigDecimal valorContasAtrasoMaisDe90Dias = BigDecimal.ZERO;
		BigDecimal valorContasTotal = BigDecimal.ZERO;
		Integer quantidadeContasAVencer = 0;
		Integer quantidadeContasAtrasoAte30Dias = 0;
		Integer quantidadeContasAtraso30A60Dias = 0;
		Integer quantidadeContasAtraso60A90Dias = 0;
		Integer quantidadeContasAtrasoMaisDe90Dias = 0;
		Integer quantidadeContasTotal = 0;
		BigDecimal valorTotalContasLocalidade = BigDecimal.ZERO;
		BigDecimal valorTotalContasGerencia = BigDecimal.ZERO;
		BigDecimal valorTotalContasEstado = BigDecimal.ZERO;
		RelatorioPosicaoContasAReceberContabilBean beanGerencia = null;
		RelatorioPosicaoContasAReceberContabilBean beanEstado = null;
		BigDecimal valorContasAVencerEstado = BigDecimal.ZERO;
		BigDecimal valorContasAtrasoAte30DiasEstado = BigDecimal.ZERO;
		BigDecimal valorContasAtraso30A60DiasEstado = BigDecimal.ZERO;
		BigDecimal valorContasAtraso60A90DiasEstado = BigDecimal.ZERO;
		BigDecimal valorContasAtrasoMaisDe90DiasEstado = BigDecimal.ZERO;
		BigDecimal valorContasTotalEstado = BigDecimal.ZERO;

		BigDecimal valorContasAVencerGerencia = BigDecimal.ZERO;
		BigDecimal valorContasAtrasoAte30DiasGerencia = BigDecimal.ZERO;
		BigDecimal valorContasAtraso30A60DiasGerencia = BigDecimal.ZERO;
		BigDecimal valorContasAtraso60A90DiasGerencia = BigDecimal.ZERO;
		BigDecimal valorContasAtrasoMaisDe90DiasGerencia = BigDecimal.ZERO;
		BigDecimal valorContasTotalGerencia = BigDecimal.ZERO;

		List<Object[]> colecaoDadosRelatorio = fachada.pesquisarContasRelatorioPosicaoContasAReceberContabil(filtro);

		if(!Util.isVazioOrNulo(colecaoDadosRelatorio)){

			for(int i = 0; i < colecaoDadosRelatorio.size(); i++){

				Object[] dadosRelatorio = colecaoDadosRelatorio.get(i);

				RelatorioPosicaoContasAReceberContabilBean bean = new RelatorioPosicaoContasAReceberContabilBean();

				if(opcaoTotalizacao.equals("gerenciaRegionalLocalidade")){

					idGerencia = dadosRelatorio[0].toString();
					nomeGerencia = dadosRelatorio[1].toString();
					idLocalidade = dadosRelatorio[2].toString();
					nomeLocalidade = dadosRelatorio[3].toString();
					idCategoria = dadosRelatorio[4].toString();
					descricaoCategoria = dadosRelatorio[5].toString();
					tipoLancamento = dadosRelatorio[6].toString();
					valorContasAVencer = new BigDecimal(dadosRelatorio[7].toString());
					valorContasAtrasoAte30Dias = new BigDecimal(dadosRelatorio[8].toString());
					valorContasAtraso30A60Dias = new BigDecimal(dadosRelatorio[9].toString());
					valorContasAtraso60A90Dias = new BigDecimal(dadosRelatorio[10].toString());
					valorContasAtrasoMaisDe90Dias = new BigDecimal(dadosRelatorio[11].toString());
					valorContasTotal = new BigDecimal(dadosRelatorio[12].toString());
					quantidadeContasAVencer = Util.obterInteger(dadosRelatorio[13].toString());
					quantidadeContasAtrasoAte30Dias = Util.obterInteger(dadosRelatorio[14].toString());
					quantidadeContasAtraso30A60Dias = Util.obterInteger(dadosRelatorio[15].toString());
					quantidadeContasAtraso60A90Dias = Util.obterInteger(dadosRelatorio[16].toString());
					quantidadeContasAtrasoMaisDe90Dias = Util.obterInteger(dadosRelatorio[17].toString());
					quantidadeContasTotal = Util.obterInteger(dadosRelatorio[18].toString());

				}else if(opcaoTotalizacao.equals("gerenciaRegional") || opcaoTotalizacao.equals("estadoGerencia")){

					idGerencia = dadosRelatorio[0].toString();
					nomeGerencia = dadosRelatorio[1].toString();
					idCategoria = dadosRelatorio[2].toString();
					descricaoCategoria = dadosRelatorio[3].toString();
					tipoLancamento = dadosRelatorio[4].toString();
					valorContasAVencer = new BigDecimal(dadosRelatorio[5].toString());
					valorContasAtrasoAte30Dias = new BigDecimal(dadosRelatorio[6].toString());
					valorContasAtraso30A60Dias = new BigDecimal(dadosRelatorio[7].toString());
					valorContasAtraso60A90Dias = new BigDecimal(dadosRelatorio[8].toString());
					valorContasAtrasoMaisDe90Dias = new BigDecimal(dadosRelatorio[9].toString());
					valorContasTotal = new BigDecimal(dadosRelatorio[10].toString());
					quantidadeContasAVencer = Util.obterInteger(dadosRelatorio[11].toString());
					quantidadeContasAtrasoAte30Dias = Util.obterInteger(dadosRelatorio[12].toString());
					quantidadeContasAtraso30A60Dias = Util.obterInteger(dadosRelatorio[13].toString());
					quantidadeContasAtraso60A90Dias = Util.obterInteger(dadosRelatorio[14].toString());
					quantidadeContasAtrasoMaisDe90Dias = Util.obterInteger(dadosRelatorio[15].toString());
					quantidadeContasTotal = Util.obterInteger(dadosRelatorio[16].toString());
				}else if(opcaoTotalizacao.equals("localidade") || opcaoTotalizacao.equals("estadoLocalidade")){

					idLocalidade = dadosRelatorio[0].toString();
					nomeLocalidade = dadosRelatorio[1].toString();
					idCategoria = dadosRelatorio[2].toString();
					descricaoCategoria = dadosRelatorio[3].toString();
					tipoLancamento = dadosRelatorio[4].toString();
					valorContasAVencer = new BigDecimal(dadosRelatorio[5].toString());
					valorContasAtrasoAte30Dias = new BigDecimal(dadosRelatorio[6].toString());
					valorContasAtraso30A60Dias = new BigDecimal(dadosRelatorio[7].toString());
					valorContasAtraso60A90Dias = new BigDecimal(dadosRelatorio[8].toString());
					valorContasAtrasoMaisDe90Dias = new BigDecimal(dadosRelatorio[9].toString());
					valorContasTotal = new BigDecimal(dadosRelatorio[10].toString());
					quantidadeContasAVencer = Util.obterInteger(dadosRelatorio[11].toString());
					quantidadeContasAtrasoAte30Dias = Util.obterInteger(dadosRelatorio[12].toString());
					quantidadeContasAtraso30A60Dias = Util.obterInteger(dadosRelatorio[13].toString());
					quantidadeContasAtraso60A90Dias = Util.obterInteger(dadosRelatorio[14].toString());
					quantidadeContasAtrasoMaisDe90Dias = Util.obterInteger(dadosRelatorio[15].toString());
					quantidadeContasTotal = Util.obterInteger(dadosRelatorio[16].toString());
				}else{

					idCategoria = dadosRelatorio[0].toString();
					descricaoCategoria = dadosRelatorio[1].toString();
					tipoLancamento = dadosRelatorio[2].toString();
					valorContasAVencer = new BigDecimal(dadosRelatorio[3].toString());
					valorContasAtrasoAte30Dias = new BigDecimal(dadosRelatorio[4].toString());
					valorContasAtraso30A60Dias = new BigDecimal(dadosRelatorio[5].toString());
					valorContasAtraso60A90Dias = new BigDecimal(dadosRelatorio[6].toString());
					valorContasAtrasoMaisDe90Dias = new BigDecimal(dadosRelatorio[7].toString());
					valorContasTotal = new BigDecimal(dadosRelatorio[8].toString());
					quantidadeContasAVencer = Util.obterInteger(dadosRelatorio[9].toString());
					quantidadeContasAtrasoAte30Dias = Util.obterInteger(dadosRelatorio[10].toString());
					quantidadeContasAtraso30A60Dias = Util.obterInteger(dadosRelatorio[11].toString());
					quantidadeContasAtraso60A90Dias = Util.obterInteger(dadosRelatorio[12].toString());
					quantidadeContasAtrasoMaisDe90Dias = Util.obterInteger(dadosRelatorio[13].toString());
					quantidadeContasTotal = Util.obterInteger(dadosRelatorio[14].toString());
				}

				Object[] dadosRelatorioProximoRegistro = null;
				boolean fimColecao = false;

				if(!(i == (colecaoDadosRelatorio.size() - 1))){

					dadosRelatorioProximoRegistro = colecaoDadosRelatorio.get(i + 1);
				}else{

					fimColecao = true;
				}

				bean.setIdGerencia(idGerencia);
				bean.setDescricaoGerencia(nomeGerencia);
				bean.setIdLocalidade(idLocalidade);
				bean.setDescricaoLocalidade(nomeLocalidade);
				bean.setIdCategoria(idCategoria);
				bean.setDescricaoCategoria(descricaoCategoria);
				bean.setTipoLancamento(tipoLancamento);
				bean.setQuantidadeContasAVencer(quantidadeContasAVencer);
				bean.setQuantidadeContasAtrasoAte30Dias(quantidadeContasAtrasoAte30Dias);
				bean.setQuantidadeContasAtraso30A60Dias(quantidadeContasAtraso30A60Dias);
				bean.setQuantidadeContasAtraso60A90Dias(quantidadeContasAtraso60A90Dias);
				bean.setQuantidadeContasAtrasoMaisDe90Dias(quantidadeContasAtrasoMaisDe90Dias);
				bean.setQuantidadeContasTotal(quantidadeContasTotal);
				bean.setValorContasAVencer(valorContasAVencer);
				bean.setValorContasAtrasoAte30Dias(valorContasAtrasoAte30Dias);
				bean.setValorContasAtraso30A60Dias(valorContasAtraso30A60Dias);
				bean.setValorContasAtraso60A90Dias(valorContasAtraso60A90Dias);
				bean.setValorContasAtrasoMaisDe90Dias(valorContasAtrasoMaisDe90Dias);
				bean.setValorContasTotal(valorContasTotal);

				// Acumuladores dos totais de conta para impressão dos débitos a cobrar
				valorTotalContasLocalidade = valorTotalContasLocalidade.add(valorContasTotal);
				valorTotalContasGerencia = valorTotalContasGerencia.add(valorContasTotal);
				valorTotalContasEstado = valorTotalContasEstado.add(valorContasTotal);

				// Acumuladores dos totais de gerencia para imprimir última linha
				valorContasAVencerGerencia = valorContasAVencerGerencia.add(valorContasAVencer);
				valorContasAtrasoAte30DiasGerencia = valorContasAtrasoAte30DiasGerencia.add(valorContasAtrasoAte30Dias);
				valorContasAtraso30A60DiasGerencia = valorContasAtraso30A60DiasGerencia.add(valorContasAtraso30A60Dias);
				valorContasAtraso60A90DiasGerencia = valorContasAtraso60A90DiasGerencia.add(valorContasAtraso60A90Dias);
				valorContasAtrasoMaisDe90DiasGerencia = valorContasAtrasoMaisDe90DiasGerencia.add(valorContasAtrasoMaisDe90Dias);
				valorContasTotalGerencia = valorContasTotalGerencia.add(valorContasTotal);

				// Acumuladores dos totais de gerencia para imprimir última linha
				valorContasAVencerEstado = valorContasAVencerEstado.add(valorContasAVencer);
				valorContasAtrasoAte30DiasEstado = valorContasAtrasoAte30DiasEstado.add(valorContasAtrasoAte30Dias);
				valorContasAtraso30A60DiasEstado = valorContasAtraso30A60DiasEstado.add(valorContasAtraso30A60Dias);
				valorContasAtraso60A90DiasEstado = valorContasAtraso60A90DiasEstado.add(valorContasAtraso60A90Dias);
				valorContasAtrasoMaisDe90DiasEstado = valorContasAtrasoMaisDe90DiasEstado.add(valorContasAtrasoMaisDe90Dias);
				valorContasTotalEstado = valorContasTotalEstado.add(valorContasTotal);

				boolean imprimirTotalizacaoDebitosACobrarGerencia = false;
				boolean imprimirTotalizacaoDebitosACobrarLocalidade = false;
				boolean imprimirTotalizacaoDebitosACobrarEstado = false;

				if(opcaoTotalizacao.equals("gerenciaRegional") || opcaoTotalizacao.equals("gerenciaRegionalLocalidade")
								|| opcaoTotalizacao.equals("estadoGerencia")){

					bean.setImprimirCabecalhoGerencia("true");

					// Caso a próxima gerencia seja diferente imprimir os débitos a cobrar
					if(fimColecao || !idGerencia.equals(dadosRelatorioProximoRegistro[0].toString())){

						imprimirTotalizacaoDebitosACobrarGerencia = true;
					}
				}

				if(opcaoTotalizacao.equals("localidade") || opcaoTotalizacao.equals("estadoLocalidade")
								|| opcaoTotalizacao.equals("gerenciaRegionalLocalidade")){

					bean.setImprimirCabecalhoLocalidade("true");

					if(opcaoTotalizacao.equals("gerenciaRegionalLocalidade")){

						// Caso a próxima localidade seja diferente imprimir os débitos a cobrar
						if(fimColecao || !idLocalidade.equals(dadosRelatorioProximoRegistro[2].toString())){

							imprimirTotalizacaoDebitosACobrarLocalidade = true;
						}
					}else{

						// Caso a próxima localidade seja diferente imprimir os débitos a cobrar
						if(fimColecao || !idLocalidade.equals(dadosRelatorioProximoRegistro[0].toString())){

							imprimirTotalizacaoDebitosACobrarLocalidade = true;
						}
					}
				}

				if(opcaoTotalizacao.equals("estado") || opcaoTotalizacao.equals("estadoLocalidade")
								|| opcaoTotalizacao.equals("estadoGerencia")){

					bean.setImprimirCabecalhoEstado("true");

					// Caso seja o final da coleção imprimir os débitos a cobrar
					if(fimColecao){

						imprimirTotalizacaoDebitosACobrarEstado = true;
					}
				}

				if(imprimirTotalizacaoDebitosACobrarLocalidade){

					// Adiciona Linha de Débitos a Cobrar da Localidade
					totalizarDebitoACobrarLocalidade(fachada, bean, idLocalidade, filtro.getAnoMesReferencia(), valorTotalContasLocalidade);
					valorTotalContasLocalidade = BigDecimal.ZERO;
				}

				boolean adicionarBean = true;
				if(imprimirTotalizacaoDebitosACobrarGerencia){

					if(filtro.getOpcaoTotalizacao().equals("gerenciaRegionalLocalidade")){

						colecaoBeans.add(bean);
						adicionarBean = false;
						valorTotalContasGerencia = BigDecimal.ZERO;

						// Pesquisa os dados para totalização por gerência
						filtro.setOpcaoTotalizacao("gerenciaRegional");
						List<Object[]> colecaoDadosRelatorioGerencia = fachada
										.pesquisarContasRelatorioPosicaoContasAReceberContabil(filtro);

						for(int j = 0; j < colecaoDadosRelatorioGerencia.size(); j++){

							Object[] dadosRelatorioGerencia = colecaoDadosRelatorioGerencia.get(j);
							beanGerencia = new RelatorioPosicaoContasAReceberContabilBean();

							idGerencia = dadosRelatorioGerencia[0].toString();
							nomeGerencia = dadosRelatorioGerencia[1].toString();
							idCategoria = dadosRelatorioGerencia[2].toString();
							descricaoCategoria = dadosRelatorioGerencia[3].toString();
							tipoLancamento = dadosRelatorioGerencia[4].toString();
							valorContasAVencer = new BigDecimal(dadosRelatorioGerencia[5].toString());
							valorContasAtrasoAte30Dias = new BigDecimal(dadosRelatorioGerencia[6].toString());
							valorContasAtraso30A60Dias = new BigDecimal(dadosRelatorioGerencia[7].toString());
							valorContasAtraso60A90Dias = new BigDecimal(dadosRelatorioGerencia[8].toString());
							valorContasAtrasoMaisDe90Dias = new BigDecimal(dadosRelatorioGerencia[9].toString());
							valorContasTotal = new BigDecimal(dadosRelatorioGerencia[10].toString());
							quantidadeContasAVencer = Util.obterInteger(dadosRelatorioGerencia[11].toString());
							quantidadeContasAtrasoAte30Dias = Util.obterInteger(dadosRelatorioGerencia[12].toString());
							quantidadeContasAtraso30A60Dias = Util.obterInteger(dadosRelatorioGerencia[13].toString());
							quantidadeContasAtraso60A90Dias = Util.obterInteger(dadosRelatorioGerencia[14].toString());
							quantidadeContasAtrasoMaisDe90Dias = Util.obterInteger(dadosRelatorioGerencia[15].toString());
							quantidadeContasTotal = Util.obterInteger(dadosRelatorioGerencia[16].toString());

							beanGerencia.setIdGerencia(idGerencia);
							beanGerencia.setDescricaoGerencia(nomeGerencia);
							beanGerencia.setIdLocalidade(String.valueOf(99999));
							beanGerencia.setDescricaoLocalidade(nomeLocalidade);
							beanGerencia.setIdCategoria(idCategoria);
							beanGerencia.setDescricaoCategoria(descricaoCategoria);
							beanGerencia.setTipoLancamento(tipoLancamento);
							beanGerencia.setQuantidadeContasAVencer(quantidadeContasAVencer);
							beanGerencia.setQuantidadeContasAtrasoAte30Dias(quantidadeContasAtrasoAte30Dias);
							beanGerencia.setQuantidadeContasAtraso30A60Dias(quantidadeContasAtraso30A60Dias);
							beanGerencia.setQuantidadeContasAtraso60A90Dias(quantidadeContasAtraso60A90Dias);
							beanGerencia.setQuantidadeContasAtrasoMaisDe90Dias(quantidadeContasAtrasoMaisDe90Dias);
							beanGerencia.setQuantidadeContasTotal(quantidadeContasTotal);
							beanGerencia.setValorContasAVencer(valorContasAVencer);
							beanGerencia.setValorContasAtrasoAte30Dias(valorContasAtrasoAte30Dias);
							beanGerencia.setValorContasAtraso30A60Dias(valorContasAtraso30A60Dias);
							beanGerencia.setValorContasAtraso60A90Dias(valorContasAtraso60A90Dias);
							beanGerencia.setValorContasAtrasoMaisDe90Dias(valorContasAtrasoMaisDe90Dias);
							beanGerencia.setValorContasTotal(valorContasTotal);

							valorTotalContasGerencia = valorTotalContasGerencia.add(valorContasTotal);

							// Força uma nova quebra por gerencia
							beanGerencia.setIdGerencia(String.valueOf(1001));
							beanGerencia.setImprimirCabecalhoGerencia("true");

							if(j == (colecaoDadosRelatorioGerencia.size() - 1)){

								// Adiciona Linha de Total da Gerência
								Collection<SubRelatorioPosicaoContasReceberContabilTotalBean> colecaoBeansSubRelatorioTotal = new ArrayList<SubRelatorioPosicaoContasReceberContabilTotalBean>();
								SubRelatorioPosicaoContasReceberContabilTotalBean beanTotal = new SubRelatorioPosicaoContasReceberContabilTotalBean();
								beanTotal.setDescricao("Total (A) da Reg.");
								beanTotal.setValorContasAVencer(valorContasAVencerGerencia);
								beanTotal.setValorContasAtrasoAte30Dias(valorContasAtrasoAte30DiasGerencia);
								beanTotal.setValorContasAtraso30A60Dias(valorContasAtraso30A60DiasGerencia);
								beanTotal.setValorContasAtraso60A90Dias(valorContasAtraso60A90DiasGerencia);
								beanTotal.setValorContasAtrasoMaisDe90Dias(valorContasAtrasoMaisDe90DiasGerencia);
								beanTotal.setValorContasTotal(valorContasTotalGerencia);
								colecaoBeansSubRelatorioTotal.add(beanTotal);
								beanGerencia.setarBeansTotal(colecaoBeansSubRelatorioTotal);

								// Adiciona Linha de Débitos a Cobrar da Gerencia
								totalizarDebitoACobrarGerencia(fachada, beanGerencia, idGerencia, filtro.getAnoMesReferencia(),
												valorTotalContasGerencia);
							}

							colecaoBeans.add(beanGerencia);
						}
					}else{

						// Adiciona Linha de Total da Gerência
						Collection<SubRelatorioPosicaoContasReceberContabilTotalBean> colecaoBeansSubRelatorioTotal = new ArrayList<SubRelatorioPosicaoContasReceberContabilTotalBean>();
						SubRelatorioPosicaoContasReceberContabilTotalBean beanTotal = new SubRelatorioPosicaoContasReceberContabilTotalBean();
						beanTotal.setDescricao("Total (A) da Reg.");
						beanTotal.setValorContasAVencer(valorContasAVencerGerencia);
						beanTotal.setValorContasAtrasoAte30Dias(valorContasAtrasoAte30DiasGerencia);
						beanTotal.setValorContasAtraso30A60Dias(valorContasAtraso30A60DiasGerencia);
						beanTotal.setValorContasAtraso60A90Dias(valorContasAtraso60A90DiasGerencia);
						beanTotal.setValorContasAtrasoMaisDe90Dias(valorContasAtrasoMaisDe90DiasGerencia);
						beanTotal.setValorContasTotal(valorContasTotalGerencia);
						colecaoBeansSubRelatorioTotal.add(beanTotal);
						bean.setarBeansTotal(colecaoBeansSubRelatorioTotal);

						// Adiciona Linha de Débitos a Cobrar da Gerencia
						totalizarDebitoACobrarGerencia(fachada, bean, idGerencia, filtro.getAnoMesReferencia(), valorTotalContasGerencia);
					}

					valorTotalContasGerencia = BigDecimal.ZERO;

					// Zera os Acumuladores dos totais de estado/gerencis
					valorContasAVencerGerencia = BigDecimal.ZERO;
					valorContasAtrasoAte30DiasGerencia = BigDecimal.ZERO;
					valorContasAtraso30A60DiasGerencia = BigDecimal.ZERO;
					valorContasAtraso60A90DiasGerencia = BigDecimal.ZERO;
					valorContasAtrasoMaisDe90DiasGerencia = BigDecimal.ZERO;
					valorContasTotalGerencia = BigDecimal.ZERO;
				}

				if(imprimirTotalizacaoDebitosACobrarEstado){

					if(filtro.getOpcaoTotalizacao().equals("estadoGerencia") || filtro.getOpcaoTotalizacao().equals("estadoLocalidade")){

						colecaoBeans.add(bean);
						adicionarBean = false;
						valorTotalContasEstado = BigDecimal.ZERO;

						// Pesquisa os dados para totalização por estado
						filtro.setOpcaoTotalizacao("estado");
						List<Object[]> colecaoDadosRelatorioEstado = fachada.pesquisarContasRelatorioPosicaoContasAReceberContabil(filtro);

						for(int j = 0; j < colecaoDadosRelatorioEstado.size(); j++){

							Object[] dadosRelatorioEstado = colecaoDadosRelatorioEstado.get(j);
							beanEstado = new RelatorioPosicaoContasAReceberContabilBean();

							idCategoria = dadosRelatorioEstado[0].toString();
							descricaoCategoria = dadosRelatorioEstado[1].toString();
							tipoLancamento = dadosRelatorioEstado[2].toString();
							valorContasAVencer = new BigDecimal(dadosRelatorioEstado[3].toString());
							valorContasAtrasoAte30Dias = new BigDecimal(dadosRelatorioEstado[4].toString());
							valorContasAtraso30A60Dias = new BigDecimal(dadosRelatorioEstado[5].toString());
							valorContasAtraso60A90Dias = new BigDecimal(dadosRelatorioEstado[6].toString());
							valorContasAtrasoMaisDe90Dias = new BigDecimal(dadosRelatorioEstado[7].toString());
							valorContasTotal = new BigDecimal(dadosRelatorioEstado[8].toString());
							quantidadeContasAVencer = Util.obterInteger(dadosRelatorioEstado[9].toString());
							quantidadeContasAtrasoAte30Dias = Util.obterInteger(dadosRelatorioEstado[10].toString());
							quantidadeContasAtraso30A60Dias = Util.obterInteger(dadosRelatorioEstado[11].toString());
							quantidadeContasAtraso60A90Dias = Util.obterInteger(dadosRelatorioEstado[12].toString());
							quantidadeContasAtrasoMaisDe90Dias = Util.obterInteger(dadosRelatorioEstado[13].toString());
							quantidadeContasTotal = Util.obterInteger(dadosRelatorioEstado[14].toString());

							beanEstado.setIdCategoria(idCategoria);
							beanEstado.setDescricaoCategoria(descricaoCategoria);
							beanEstado.setTipoLancamento(tipoLancamento);
							beanEstado.setQuantidadeContasAVencer(quantidadeContasAVencer);
							beanEstado.setQuantidadeContasAtrasoAte30Dias(quantidadeContasAtrasoAte30Dias);
							beanEstado.setQuantidadeContasAtraso30A60Dias(quantidadeContasAtraso30A60Dias);
							beanEstado.setQuantidadeContasAtraso60A90Dias(quantidadeContasAtraso60A90Dias);
							beanEstado.setQuantidadeContasAtrasoMaisDe90Dias(quantidadeContasAtrasoMaisDe90Dias);
							beanEstado.setQuantidadeContasTotal(quantidadeContasTotal);
							beanEstado.setValorContasAVencer(valorContasAVencer);
							beanEstado.setValorContasAtrasoAte30Dias(valorContasAtrasoAte30Dias);
							beanEstado.setValorContasAtraso30A60Dias(valorContasAtraso30A60Dias);
							beanEstado.setValorContasAtraso60A90Dias(valorContasAtraso60A90Dias);
							beanEstado.setValorContasAtrasoMaisDe90Dias(valorContasAtrasoMaisDe90Dias);
							beanEstado.setValorContasTotal(valorContasTotal);
							valorTotalContasEstado = valorTotalContasEstado.add(valorContasTotal);

							// Força uma nova quebra por estado
							beanEstado.setImprimirCabecalhoEstado("true");

							if(j == (colecaoDadosRelatorioEstado.size() - 1)){

								// Adiciona Linha de Total do Estado
								Collection<SubRelatorioPosicaoContasReceberContabilTotalBean> colecaoBeansSubRelatorioTotal = new ArrayList<SubRelatorioPosicaoContasReceberContabilTotalBean>();
								SubRelatorioPosicaoContasReceberContabilTotalBean beanTotal = new SubRelatorioPosicaoContasReceberContabilTotalBean();
								beanTotal.setDescricao("Total (A) do Est.");
								beanTotal.setValorContasAVencer(valorContasAVencerEstado);
								beanTotal.setValorContasAtrasoAte30Dias(valorContasAtrasoAte30DiasEstado);
								beanTotal.setValorContasAtraso30A60Dias(valorContasAtraso30A60DiasEstado);
								beanTotal.setValorContasAtraso60A90Dias(valorContasAtraso60A90DiasEstado);
								beanTotal.setValorContasAtrasoMaisDe90Dias(valorContasAtrasoMaisDe90DiasEstado);
								beanTotal.setValorContasTotal(valorContasTotalEstado);
								colecaoBeansSubRelatorioTotal.add(beanTotal);
								beanEstado.setarBeansTotal(colecaoBeansSubRelatorioTotal);

								// Adiciona Linha de Débitos a Cobrar do Estado
								totalizarDebitoACobrarEstado(fachada, beanEstado, filtro.getAnoMesReferencia(), valorTotalContasEstado);
							}

							colecaoBeans.add(beanEstado);
						}
					}else{

						// Adiciona Linha de Total do Estado
						Collection<SubRelatorioPosicaoContasReceberContabilTotalBean> colecaoBeansSubRelatorioTotal = new ArrayList<SubRelatorioPosicaoContasReceberContabilTotalBean>();
						SubRelatorioPosicaoContasReceberContabilTotalBean beanTotal = new SubRelatorioPosicaoContasReceberContabilTotalBean();
						beanTotal.setDescricao("Total (A) do Est.");
						beanTotal.setValorContasAVencer(valorContasAVencerEstado);
						beanTotal.setValorContasAtrasoAte30Dias(valorContasAtrasoAte30DiasEstado);
						beanTotal.setValorContasAtraso30A60Dias(valorContasAtraso30A60DiasEstado);
						beanTotal.setValorContasAtraso60A90Dias(valorContasAtraso60A90DiasEstado);
						beanTotal.setValorContasAtrasoMaisDe90Dias(valorContasAtrasoMaisDe90DiasEstado);
						beanTotal.setValorContasTotal(valorContasTotalEstado);
						colecaoBeansSubRelatorioTotal.add(beanTotal);
						bean.setarBeansTotal(colecaoBeansSubRelatorioTotal);

						// Adiciona Linha de Débitos a Cobrar do Estado
						totalizarDebitoACobrarEstado(fachada, bean, filtro.getAnoMesReferencia(), valorTotalContasEstado);
					}
				}

				if(adicionarBean){

					colecaoBeans.add(bean);
				}
			}
		}

		if(opcaoTotalizacao.equals("estado")){

			parametros.put("opcaoTotalizacao", "Estado");
		}else if(opcaoTotalizacao.equals("estadoLocalidade")){

			parametros.put("opcaoTotalizacao", "Estado por Localidade");
		}else if(opcaoTotalizacao.equals("estadoGerencia")){

			parametros.put("opcaoTotalizacao", "Estado  por Gerência Regional");
		}else if(opcaoTotalizacao.equals("gerenciaRegional")){

			parametros.put("opcaoTotalizacao", "Gerência Regional");
		}else if(opcaoTotalizacao.equals("gerenciaRegionalLocalidade")){

			parametros.put("opcaoTotalizacao", "Gerência Regional por Localidade");
		}else if(opcaoTotalizacao.equals("localidade")){

			parametros.put("opcaoTotalizacao", "Localidade");
		}

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("anoMesReferenciaFaturamento", Util.formatarAnoMesParaMesAno(Util.subtrairMesDoAnoMes(sistemaParametro
						.getAnoMesFaturamento(), 1)));

		parametros.put("tipoFormatoRelatorio", "");

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		int mesAtual = Util.getMes(new Date());
		String descricaoMesAtual = Util.retornaDescricaoMes(mesAtual);
		parametros.put("mesAtual", descricaoMesAtual);

		int mesAtualMenosUmMes = Util.getMes(Util.subtrairNumeroDiasDeUmaData(new Date(), 30));
		String descricaoMesAtualMenosUmMes = Util.retornaDescricaoMes(mesAtualMenosUmMes);
		parametros.put("mesAtualMenosUmMes", descricaoMesAtualMenosUmMes);

		int mesAtualMenosDoisMeses = Util.getMes(Util.subtrairNumeroDiasDeUmaData(new Date(), 60));
		String descricaoMesAtualMenosDoisMeses = Util.retornaDescricaoMes(mesAtualMenosDoisMeses);
		parametros.put("mesAtualMenosDoisMeses", descricaoMesAtualMenosDoisMeses);

		int mesAtualMenosTresMeses = Util.getMes(Util.subtrairNumeroDiasDeUmaData(new Date(), 90));
		String descricaoMesAtualMenosTresMeses = Util.retornaDescricaoMes(mesAtualMenosTresMeses);
		parametros.put("mesAtualMenosTresMeses", descricaoMesAtualMenosTresMeses);

		int mesesAnteriores = Util.getMes(Util.subtrairNumeroDiasDeUmaData(new Date(), 120));
		String descricaoMesesAnteriores = Util.retornaDescricaoMes(mesesAnteriores);
		parametros.put("mesesAnteriores", descricaoMesesAnteriores + " e Meses Anteriores");

		RelatorioDataSource ds = new RelatorioDataSource((List) colecaoBeans);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_POSICAO_CONTAS_A_RECEBER_CONTABIL, parametros, ds,
						tipoFormatoRelatorio);

		try{

			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_POSICAO_CONTAS_A_RECEBER_CONTABIL, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		// retorna o relatório gerado
		return retorno;

	}

	private void totalizarDebitoACobrarLocalidade(Fachada fachada, RelatorioPosicaoContasAReceberContabilBean beanTotalizador,
					String idLocalidade, Integer anoMesReferencia, BigDecimal valorContasTotalLocalidade){

		Collection<Object[]> colecaoDebitosLocalidade = fachada.obterDebitosACobrarRelatorioPosicaoContasAReceberContabil(null, Util
						.obterInteger(idLocalidade), anoMesReferencia);
		Collection<SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean> colecaoBeansSubRelatorio = new ArrayList<SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean>();
		SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean beanResidencial = new SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean();
		SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean beanComercial = new SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean();
		SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean beanIndustrial = new SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean();
		SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean beanPublico = new SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean();

		for(Iterator iterator = colecaoDebitosLocalidade.iterator(); iterator.hasNext();){

			Object[] debitoLocalidade = (Object[]) iterator.next();
			Integer idcategoria = Util.obterInteger(debitoLocalidade[1].toString());

			if(idcategoria.equals(Categoria.RESIDENCIAL)){

				beanResidencial.setIdCategoria(Categoria.RESIDENCIAL.toString());
				beanResidencial.setDescricaoCategoria("Residencial");

				if(debitoLocalidade[2].toString().equals("Financiamento")){

					beanResidencial.setValorFinanciamento(new BigDecimal(debitoLocalidade[0].toString()));
				}else{

					beanResidencial.setValorParcelamento(new BigDecimal(debitoLocalidade[0].toString()));
				}
			}else if(idcategoria.equals(Categoria.COMERCIAL)){

				beanComercial.setIdCategoria(Categoria.COMERCIAL.toString());
				beanComercial.setDescricaoCategoria("Comercial");

				if(debitoLocalidade[2].toString().equals("Financiamento")){

					beanComercial.setValorFinanciamento(new BigDecimal(debitoLocalidade[0].toString()));
				}else{

					beanComercial.setValorParcelamento(new BigDecimal(debitoLocalidade[0].toString()));
				}
			}else if(idcategoria.equals(Categoria.INDUSTRIAL)){

				beanIndustrial.setIdCategoria(Categoria.INDUSTRIAL.toString());
				beanIndustrial.setDescricaoCategoria("Industrial");

				if(debitoLocalidade[2].toString().equals("Financiamento")){

					beanIndustrial.setValorFinanciamento(new BigDecimal(debitoLocalidade[0].toString()));
				}else{

					beanIndustrial.setValorParcelamento(new BigDecimal(debitoLocalidade[0].toString()));
				}
			}else{

				beanPublico.setIdCategoria(Categoria.PUBLICO.toString());
				beanPublico.setDescricaoCategoria("Publico");

				if(debitoLocalidade[2].toString().equals("Financiamento")){

					beanPublico.setValorFinanciamento(new BigDecimal(debitoLocalidade[0].toString()));
				}else{

					beanPublico.setValorParcelamento(new BigDecimal(debitoLocalidade[0].toString()));
				}
			}
		}

		// Preenche a coleção de beans do subrelatorio
		if(beanResidencial.getDescricaoCategoria() == null){

			beanResidencial.setDescricaoCategoria("Residencial");
		}

		colecaoBeansSubRelatorio.add(beanResidencial);

		if(beanComercial.getDescricaoCategoria() == null){

			beanComercial.setDescricaoCategoria("Comercial");
		}

		colecaoBeansSubRelatorio.add(beanComercial);

		if(beanIndustrial.getDescricaoCategoria() == null){

			beanIndustrial.setDescricaoCategoria("Industrial");
		}

		colecaoBeansSubRelatorio.add(beanIndustrial);

		if(beanPublico.getDescricaoCategoria() == null){

			beanResidencial.setDescricaoCategoria("publico");
		}

		colecaoBeansSubRelatorio.add(beanPublico);

		// Preenche valor com zero quando nulo e totaliza categoria
		BigDecimal acumularValorDebitos = BigDecimal.ZERO;
		for(SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean beanAux : colecaoBeansSubRelatorio){

			if(beanAux.getValorFinanciamento() == null){

				beanAux.setValorFinanciamento(BigDecimal.ZERO);
			}

			if(beanAux.getValorParcelamento() == null){

				beanAux.setValorParcelamento(BigDecimal.ZERO);
			}

			beanAux.setValorTotal(beanAux.getValorFinanciamento().add(beanAux.getValorParcelamento()));
			acumularValorDebitos = acumularValorDebitos.add(beanAux.getValorTotal());
			beanAux.setTotalGeralContas(valorContasTotalLocalidade);
			beanAux.setTotalGeralDebitos(acumularValorDebitos);
			beanAux.setTotalGeral(beanAux.getTotalGeralContas().add(beanAux.getTotalGeralDebitos()));
		}

		beanTotalizador.setarBeansTotalizadores(colecaoBeansSubRelatorio);
		beanTotalizador.setImprimirDebitosACobrar("true");
	}

	private void totalizarDebitoACobrarGerencia(Fachada fachada, RelatorioPosicaoContasAReceberContabilBean beanTotalizador,
					String idGerencia, Integer anoMesReferencia, BigDecimal valorContasTotalGerencia){

		Collection<Object[]> colecaoDebitosGerencia = fachada.obterDebitosACobrarRelatorioPosicaoContasAReceberContabil(Util
						.obterInteger(idGerencia), null, anoMesReferencia);

		Collection<SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean> colecaoBeansSubRelatorio = new ArrayList<SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean>();
		SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean beanResidencial = new SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean();
		SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean beanComercial = new SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean();
		SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean beanIndustrial = new SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean();
		SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean beanPublico = new SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean();

		for(Iterator iterator = colecaoDebitosGerencia.iterator(); iterator.hasNext();){

			Object[] debitoGerencia = (Object[]) iterator.next();
			Integer idcategoria = Util.obterInteger(debitoGerencia[1].toString());

			if(idcategoria.equals(Categoria.RESIDENCIAL)){

				beanResidencial.setIdCategoria(Categoria.RESIDENCIAL.toString());
				beanResidencial.setDescricaoCategoria("Residencial");

				if(debitoGerencia[2].toString().equals("Financiamento")){

					beanResidencial.setValorFinanciamento(new BigDecimal(debitoGerencia[0].toString()));
				}else{

					beanResidencial.setValorParcelamento(new BigDecimal(debitoGerencia[0].toString()));
				}
			}else if(idcategoria.equals(Categoria.COMERCIAL)){

				beanComercial.setIdCategoria(Categoria.COMERCIAL.toString());
				beanComercial.setDescricaoCategoria("Comercial");

				if(debitoGerencia[2].toString().equals("Financiamento")){

					beanComercial.setValorFinanciamento(new BigDecimal(debitoGerencia[0].toString()));
				}else{

					beanComercial.setValorParcelamento(new BigDecimal(debitoGerencia[0].toString()));
				}
			}else if(idcategoria.equals(Categoria.INDUSTRIAL)){

				beanIndustrial.setIdCategoria(Categoria.INDUSTRIAL.toString());
				beanIndustrial.setDescricaoCategoria("Industrial");

				if(debitoGerencia[2].toString().equals("Financiamento")){

					beanIndustrial.setValorFinanciamento(new BigDecimal(debitoGerencia[0].toString()));
				}else{

					beanIndustrial.setValorParcelamento(new BigDecimal(debitoGerencia[0].toString()));
				}
			}else{

				beanPublico.setIdCategoria(Categoria.PUBLICO.toString());
				beanPublico.setDescricaoCategoria("Publico");

				if(debitoGerencia[2].toString().equals("Financiamento")){

					beanPublico.setValorFinanciamento(new BigDecimal(debitoGerencia[0].toString()));
				}else{

					beanPublico.setValorParcelamento(new BigDecimal(debitoGerencia[0].toString()));
				}
			}
		}

		// Preenche a coleção de beans do subrelatorio
		if(beanResidencial.getDescricaoCategoria() == null){

			beanResidencial.setDescricaoCategoria("Residencial");
		}

		colecaoBeansSubRelatorio.add(beanResidencial);

		if(beanComercial.getDescricaoCategoria() == null){

			beanComercial.setDescricaoCategoria("Comercial");
		}

		colecaoBeansSubRelatorio.add(beanComercial);

		if(beanIndustrial.getDescricaoCategoria() == null){

			beanIndustrial.setDescricaoCategoria("Industrial");
		}

		colecaoBeansSubRelatorio.add(beanIndustrial);

		if(beanPublico.getDescricaoCategoria() == null){

			beanResidencial.setDescricaoCategoria("publico");
		}

		colecaoBeansSubRelatorio.add(beanPublico);

		// Preenche valor com zero quando nulo e totaliza categoria
		BigDecimal acumularValorDebitos = BigDecimal.ZERO;
		for(SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean beanAux : colecaoBeansSubRelatorio){

			if(beanAux.getValorFinanciamento() == null){

				beanAux.setValorFinanciamento(BigDecimal.ZERO);
			}

			if(beanAux.getValorParcelamento() == null){

				beanAux.setValorParcelamento(BigDecimal.ZERO);
			}

			beanAux.setValorTotal(beanAux.getValorFinanciamento().add(beanAux.getValorParcelamento()));
			acumularValorDebitos = acumularValorDebitos.add(beanAux.getValorTotal());
			beanAux.setTotalGeralContas(valorContasTotalGerencia);
			beanAux.setTotalGeralDebitos(acumularValorDebitos);
			beanAux.setTotalGeral(beanAux.getTotalGeralContas().add(beanAux.getTotalGeralDebitos()));
		}

		beanTotalizador.setarBeansTotalizadores(colecaoBeansSubRelatorio);
		beanTotalizador.setImprimirDebitosACobrar("true");
	}

	private void totalizarDebitoACobrarEstado(Fachada fachada, RelatorioPosicaoContasAReceberContabilBean beanTotalizador,
					Integer anoMesReferencia, BigDecimal valorContasTotalEstado){

		Collection<Object[]> colecaoDebitosEstado = fachada.obterDebitosACobrarRelatorioPosicaoContasAReceberContabil(null, null,
						anoMesReferencia);

		Collection<SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean> colecaoBeansSubRelatorio = new ArrayList<SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean>();
		SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean beanResidencial = new SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean();
		SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean beanComercial = new SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean();
		SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean beanIndustrial = new SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean();
		SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean beanPublico = new SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean();

		for(Iterator iterator = colecaoDebitosEstado.iterator(); iterator.hasNext();){

			Object[] debitoEstado = (Object[]) iterator.next();
			Integer idcategoria = Util.obterInteger(debitoEstado[1].toString());

			if(idcategoria.equals(Categoria.RESIDENCIAL)){

				beanResidencial.setIdCategoria(Categoria.RESIDENCIAL.toString());
				beanResidencial.setDescricaoCategoria("Residencial");

				if(debitoEstado[2].toString().equals("Financiamento")){

					beanResidencial.setValorFinanciamento(new BigDecimal(debitoEstado[0].toString()));
				}else{

					beanResidencial.setValorParcelamento(new BigDecimal(debitoEstado[0].toString()));
				}
			}else if(idcategoria.equals(Categoria.COMERCIAL)){

				beanComercial.setIdCategoria(Categoria.COMERCIAL.toString());
				beanComercial.setDescricaoCategoria("Comercial");

				if(debitoEstado[2].toString().equals("Financiamento")){

					beanComercial.setValorFinanciamento(new BigDecimal(debitoEstado[0].toString()));
				}else{

					beanComercial.setValorParcelamento(new BigDecimal(debitoEstado[0].toString()));
				}
			}else if(idcategoria.equals(Categoria.INDUSTRIAL)){

				beanIndustrial.setIdCategoria(Categoria.INDUSTRIAL.toString());
				beanIndustrial.setDescricaoCategoria("Industrial");

				if(debitoEstado[2].toString().equals("Financiamento")){

					beanIndustrial.setValorFinanciamento(new BigDecimal(debitoEstado[0].toString()));
				}else{

					beanIndustrial.setValorParcelamento(new BigDecimal(debitoEstado[0].toString()));
				}
			}else{

				beanPublico.setIdCategoria(Categoria.PUBLICO.toString());
				beanPublico.setDescricaoCategoria("Publico");

				if(debitoEstado[2].toString().equals("Financiamento")){

					beanPublico.setValorFinanciamento(new BigDecimal(debitoEstado[0].toString()));
				}else{

					beanPublico.setValorParcelamento(new BigDecimal(debitoEstado[0].toString()));
				}
			}
		}

		// Preenche a coleção de beans do subrelatorio
		if(beanResidencial.getDescricaoCategoria() == null){

			beanResidencial.setDescricaoCategoria("Residencial");
		}

		colecaoBeansSubRelatorio.add(beanResidencial);

		if(beanComercial.getDescricaoCategoria() == null){

			beanComercial.setDescricaoCategoria("Comercial");
		}

		colecaoBeansSubRelatorio.add(beanComercial);

		if(beanIndustrial.getDescricaoCategoria() == null){

			beanIndustrial.setDescricaoCategoria("Industrial");
		}

		colecaoBeansSubRelatorio.add(beanIndustrial);

		if(beanPublico.getDescricaoCategoria() == null){

			beanResidencial.setDescricaoCategoria("publico");
		}

		colecaoBeansSubRelatorio.add(beanPublico);

		// Preenche valor com zero quando nulo e totaliza categoria
		BigDecimal acumularValorDebitos = BigDecimal.ZERO;
		for(SubRelatorioPosicaoContasReceberContabilDebitosACobrarBean beanAux : colecaoBeansSubRelatorio){

			if(beanAux.getValorFinanciamento() == null){

				beanAux.setValorFinanciamento(BigDecimal.ZERO);
			}

			if(beanAux.getValorParcelamento() == null){

				beanAux.setValorParcelamento(BigDecimal.ZERO);
			}

			beanAux.setValorTotal(beanAux.getValorFinanciamento().add(beanAux.getValorParcelamento()));
			acumularValorDebitos = acumularValorDebitos.add(beanAux.getValorTotal());
			beanAux.setTotalGeralContas(valorContasTotalEstado);
			beanAux.setTotalGeralDebitos(acumularValorDebitos);
			beanAux.setTotalGeral(beanAux.getTotalGeralContas().add(beanAux.getTotalGeralDebitos()));
		}

		beanTotalizador.setarBeansTotalizadores(colecaoBeansSubRelatorio);
		beanTotalizador.setImprimirDebitosACobrar("true");
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		return 1;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioPosicaoContasAReceberContabil", this);
	}
}
