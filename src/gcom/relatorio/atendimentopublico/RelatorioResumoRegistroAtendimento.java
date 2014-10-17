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

import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.bean.FiltrarRegistroAtendimentoHelper;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * [UC0XXX] Relatório Resumo Registro de Atendimento
 * 
 * @author Anderson Italo
 * @date 16/03/2011
 */
public class RelatorioResumoRegistroAtendimento
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioResumoRegistroAtendimento(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_RESUMO_REGISTRO_ATENDIMENTO);
	}

	@Deprecated
	public RelatorioResumoRegistroAtendimento() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;

		// obtêm os parâmetros passados
		FiltrarRegistroAtendimentoHelper filtroRA = (FiltrarRegistroAtendimentoHelper) getParametro("filtroRA");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// monta a coleção de beans do relatório
		List relatorioBeans = new ArrayList();
		List relatorioBeansFinal = new ArrayList();
		List relatorioBeansTotalizadores = new ArrayList();
		List relatorioBeansTotalizadoresFinal = new ArrayList();
		Fachada fachada = Fachada.getInstancia();

		// obtém a coleção com dados do detalhe do relatório
		List colecaoDadosRelatorio = (List) fachada.pesquisaRelatorioResumoRA(filtroRA, 1);
		// obtém a coleção com dados dos totalizadores do relatório
		List colecaoDadosTotalizadoresRelatorio = (List) fachada.pesquisaRelatorioResumoRA(filtroRA, 2);

		if(colecaoDadosRelatorio == null || colecaoDadosRelatorio.isEmpty()){
			// Não existem dados para a exibição do relatório.
			throw new ActionServletException("atencao.relatorio.vazio");
		}

		// processamento dos dados dos totalizadores do relatório
		Map<String, String> idTipoQuantidade = new HashMap<String, String>();
		Integer totalGeral = 0;
		for(int i = 0; i < colecaoDadosTotalizadoresRelatorio.size(); i++){

			Object[] dados = (Object[]) colecaoDadosTotalizadoresRelatorio.get(i);
			RelatorioResumoRegistroAtendimentoBeanHelper bean = new RelatorioResumoRegistroAtendimentoBeanHelper();

			if((i == 0) || (i == colecaoDadosTotalizadoresRelatorio.size() - 1)){

				bean.setImprimirTotalizadorTipoSolicitacao("true");
			}else{

				Object[] dadosAux = (Object[]) colecaoDadosTotalizadoresRelatorio.get(i - 1);

				if(!dados[2].toString().equals(dadosAux[2].toString())){
					bean.setImprimirTotalizadorTipoSolicitacao("true");
				}
			}

			// Id da Especificação
			bean.setIdEspecificacaoTotal(dados[0].toString());

			// Descrição da Especificação
			bean.setDescricaoEspecificacaoTotal(dados[1].toString());

			// Id do Tipo da Solicitação
			bean.setIdTipoSolicitacaoTotal(dados[2].toString());

			// Descrição do Tipo da Solicitação
			bean.setDescricaoTipoSolicitacaoTotal(dados[3].toString());

			// Quantidade por especificação
			bean.setTotalPorEspecificacao(dados[4].toString());

			// armazena totais de quebra por tipo de solicitação por unidade de atendimento
			int quantidadePorTipoSolicitacao = 0;
			if(!idTipoQuantidade.isEmpty()){

				if(idTipoQuantidade.containsKey(bean.getIdTipoSolicitacaoTotal())){

					quantidadePorTipoSolicitacao = Integer.valueOf(idTipoQuantidade.get(bean.getIdTipoSolicitacaoTotal())).intValue();
					quantidadePorTipoSolicitacao += Integer.valueOf(bean.getTotalPorEspecificacao());

					idTipoQuantidade.remove(bean.getIdTipoSolicitacaoTotal());
					idTipoQuantidade.put(bean.getIdTipoSolicitacaoTotal(), String.valueOf(quantidadePorTipoSolicitacao));
				}else{

					quantidadePorTipoSolicitacao += Integer.valueOf(bean.getTotalPorEspecificacao());
					idTipoQuantidade.put(bean.getIdTipoSolicitacaoTotal(), String.valueOf(quantidadePorTipoSolicitacao));
				}
			}else{

				quantidadePorTipoSolicitacao += Integer.valueOf(bean.getTotalPorEspecificacao());
				idTipoQuantidade.put(bean.getIdTipoSolicitacaoTotal(), String.valueOf(quantidadePorTipoSolicitacao));
			}

			totalGeral += Integer.valueOf(bean.getTotalPorEspecificacao());

			if(i == (colecaoDadosTotalizadoresRelatorio.size() - 1)){
				bean.setTotalGeral(totalGeral.toString());
				bean.setImprimirTotalizadorGeral("true");
			}

			relatorioBeansTotalizadores.add(bean);
		}

		// preenche os valores dos percentuais dos tipo de solicitação
		// com relação ao total e percentuais das especificações com
		// relação ao tipo de solicitação
		for(int i = 0; i < relatorioBeansTotalizadores.size(); i++){

			RelatorioResumoRegistroAtendimentoBeanHelper bean = (RelatorioResumoRegistroAtendimentoBeanHelper) relatorioBeansTotalizadores
							.get(i);

			BigDecimal quantidadeDoTipoSolicitacao = new BigDecimal(idTipoQuantidade.get(bean.getIdTipoSolicitacaoTotal()));

			// totaliza percentual por tipo de solicitação
			if(bean.getImprimirTotalizadorTipoSolicitacao() != null){

				BigDecimal quantidadeTotal = new BigDecimal(totalGeral.toString());

				// regra de 3 para determinar percentual por tipo de solicitação
				bean.setTotalPorTipoSolicitacao(quantidadeDoTipoSolicitacao.toEngineeringString());
				quantidadeDoTipoSolicitacao = quantidadeDoTipoSolicitacao.multiply(new BigDecimal("100"));
				BigDecimal percentualPorTipoSolicitacaoTotal = quantidadeDoTipoSolicitacao.divide(quantidadeTotal, 2,
								BigDecimal.ROUND_HALF_UP);
				bean.setPercentualPorTipoSolicitacaoTotal(Util.formataBigDecimal(percentualPorTipoSolicitacaoTotal, 2, false));
			}

			// totaliza percentual por especificação
			BigDecimal quantidadeDaEspecificacao = new BigDecimal(bean.getTotalPorEspecificacao());

			quantidadeDoTipoSolicitacao = new BigDecimal(idTipoQuantidade.get(bean.getIdTipoSolicitacaoTotal()));

			// regra de 3 para determinar percentual por tipo de solicitação
			quantidadeDaEspecificacao = quantidadeDaEspecificacao.multiply(new BigDecimal("100"));
			BigDecimal percentualPorEspecificacao = quantidadeDaEspecificacao.divide(quantidadeDoTipoSolicitacao, 2,
							BigDecimal.ROUND_HALF_UP);
			bean.setPercentualPorEspecificacaoTotal(Util.formataBigDecimal(percentualPorEspecificacao, 2, false));

			relatorioBeansTotalizadoresFinal.add(bean);
		}

		// processamento dos dados do detalhe do relatório
		List listaRelatorioHelper = new ArrayList<RelatorioResumoRegistroAtendimentoHelper>();

		for(int i = 0; i < colecaoDadosRelatorio.size(); i++){

			Object[] dados = (Object[]) colecaoDadosRelatorio.get(i);

			RelatorioResumoRegistroAtendimentoBean bean = new RelatorioResumoRegistroAtendimentoBean();

			// Cabeçalho

			// Id da Unidade de Atendimento
			bean.setIdUnidadeAtendimento(dados[4].toString());

			// Descricao da Unidade de Atendimento
			bean.setDescricaoUnidadeAtendimento(dados[5].toString());

			// Linha Detalhe

			// Id da Especificação
			bean.setIdEspecificacao(dados[0].toString());

			// Descrição da Especificação
			bean.setDescricaoEspecificacao(dados[1].toString());

			// Id do Tipo da Solicitação
			bean.setIdTipoSolicitacao(dados[2].toString());

			// Descrição do Tipo da Solicitação
			bean.setDescricaoTipoSolicitacao(dados[3].toString());

			// Quantidade por especificação
			bean.setQuantidadePorEspecificacao(dados[6].toString());

			// armazena totais de quebra por tipo de solicitação por unidade de atendimento
			if(listaRelatorioHelper.size() > 0){

				boolean achouTipo = false;

				for(int j = 0; j < listaRelatorioHelper.size(); j++){

					RelatorioResumoRegistroAtendimentoHelper helper = (RelatorioResumoRegistroAtendimentoHelper) listaRelatorioHelper
									.get(j);

					if(helper.getIdTipoSolicitacao().equals(bean.getIdTipoSolicitacao())
									&& helper.getIdUnidadeAtendimento().equals(bean.getIdUnidadeAtendimento())){

						int quantidadePorTipoSolicitacao = 0;
						achouTipo = true;
						quantidadePorTipoSolicitacao = Integer.valueOf(helper.getQuantidadePorTipo()).intValue();
						quantidadePorTipoSolicitacao += Integer.valueOf(bean.getQuantidadePorEspecificacao());

						helper.setQuantidadePorTipo(String.valueOf(quantidadePorTipoSolicitacao));
						listaRelatorioHelper.set(j, helper);
						break;
					}
				}

				if(!achouTipo){
					RelatorioResumoRegistroAtendimentoHelper helper = new RelatorioResumoRegistroAtendimentoHelper();
					helper.setIdUnidadeAtendimento(bean.getIdUnidadeAtendimento());
					helper.setIdTipoSolicitacao(bean.getIdTipoSolicitacao());
					helper.setQuantidadePorTipo(bean.getQuantidadePorEspecificacao());
					listaRelatorioHelper.add(helper);
				}

			}else{

				RelatorioResumoRegistroAtendimentoHelper helper = new RelatorioResumoRegistroAtendimentoHelper();
				helper.setIdUnidadeAtendimento(bean.getIdUnidadeAtendimento());
				helper.setIdTipoSolicitacao(bean.getIdTipoSolicitacao());
				helper.setQuantidadePorTipo(bean.getQuantidadePorEspecificacao());
				listaRelatorioHelper.add(helper);
			}

			relatorioBeans.add(bean);
		}

		// preenche os valores dos percentuais das especificações com relação
		// ao tipo de solicitação por unidade de atendimento e armazena os
		// totais dos tipo de solicitação por unidade de atendimento
		int quantidadePorUnidade = 0;
		boolean totalizaUnidade = false;
		Map<String, String> idUnidadeQuantidade = new HashMap<String, String>();
		for(int i = 0; i < relatorioBeans.size(); i++){

			RelatorioResumoRegistroAtendimentoBean bean = (RelatorioResumoRegistroAtendimentoBean) relatorioBeans.get(i);
			totalizaUnidade = false;

			if((i == 0) || (i == relatorioBeans.size() - 1)){

				bean.setImprimirTipoSolicitacao("true");

				if((i == relatorioBeans.size() - 1)){

					bean.setImprimirUnidadeAtendimento("true");
					bean.setImprimirTotalizadores("true");
					totalizaUnidade = true;
				}else{

					RelatorioResumoRegistroAtendimentoBean beanAux = (RelatorioResumoRegistroAtendimentoBean) relatorioBeans.get(i + 1);

					beanAux = (RelatorioResumoRegistroAtendimentoBean) relatorioBeans.get(i + 1);

					if(!bean.getIdUnidadeAtendimento().equals(beanAux.getIdUnidadeAtendimento())){

						bean.setImprimirUnidadeAtendimento("true");
						totalizaUnidade = true;
					}
				}
			}else{

				RelatorioResumoRegistroAtendimentoBean beanAux = (RelatorioResumoRegistroAtendimentoBean) relatorioBeans.get(i - 1);

				if(!bean.getIdTipoSolicitacao().equals(beanAux.getIdTipoSolicitacao())){

					bean.setImprimirTipoSolicitacao("true");
				}

				beanAux = (RelatorioResumoRegistroAtendimentoBean) relatorioBeans.get(i + 1);

				if(!bean.getIdUnidadeAtendimento().equals(beanAux.getIdUnidadeAtendimento())){

					bean.setImprimirUnidadeAtendimento("true");
					totalizaUnidade = true;
				}
			}

			for(int j = 0; j < listaRelatorioHelper.size(); j++){

				RelatorioResumoRegistroAtendimentoHelper helper = (RelatorioResumoRegistroAtendimentoHelper) listaRelatorioHelper.get(j);

				boolean encontrouTipo = false;
				if(helper.getIdUnidadeAtendimento().equals(bean.getIdUnidadeAtendimento())
								&& helper.getIdTipoSolicitacao().equals(bean.getIdTipoSolicitacao())){

					BigDecimal quantidadeDoTipoSolicitacao = new BigDecimal(helper.getQuantidadePorTipo());

					// totaliza percentual por especificação
					if(helper.getQuantidadePorTipo() != null){

						BigDecimal quantidadeDaEspecificacao = new BigDecimal(bean.getQuantidadePorEspecificacao());

						// regra de 3 para determinar percentual por especificação
						quantidadeDaEspecificacao = quantidadeDaEspecificacao.multiply(new BigDecimal("100"));
						BigDecimal percentualPorEspecificacao = quantidadeDaEspecificacao.divide(quantidadeDoTipoSolicitacao, 2,
										BigDecimal.ROUND_HALF_UP);
						bean.setPercentualPorEspecificacao(Util.formataBigDecimal(percentualPorEspecificacao, 2, false));
						bean.setQuantidadePorTipoSolicitacao(helper.getQuantidadePorTipo());
						encontrouTipo = true;
					}

					if(encontrouTipo) break;
				}
			}

			if(bean.getQuantidadePorTipoSolicitacao() != null && totalizaUnidade == false){

				quantidadePorUnidade += Integer.valueOf(bean.getQuantidadePorEspecificacao()).intValue();
			}else{

				quantidadePorUnidade += Integer.valueOf(bean.getQuantidadePorEspecificacao()).intValue();
				bean.setQuantidadePorUnidade(String.valueOf(quantidadePorUnidade));
				idUnidadeQuantidade.put(bean.getIdUnidadeAtendimento(), bean.getQuantidadePorUnidade());
				quantidadePorUnidade = 0;
			}

			relatorioBeansFinal.add(bean);

		}

		// preenche os valores dos percentuais dos tipo de solicitação
		// com relação a unidade de atendimento
		RelatorioResumoRegistroAtendimentoBean beanTotalizador = null;
		for(int i = 0; i < relatorioBeansFinal.size(); i++){

			RelatorioResumoRegistroAtendimentoBean bean = (RelatorioResumoRegistroAtendimentoBean) relatorioBeans.get(i);

			String qtdUnidade = idUnidadeQuantidade.get(bean.getIdUnidadeAtendimento());
			BigDecimal quantidadeDoTipoSolicitacao = new BigDecimal(bean.getQuantidadePorTipoSolicitacao());

			// totaliza percentual por tipo de solicitação
			BigDecimal quantidadeDaUnidadeAtendimento = new BigDecimal(qtdUnidade);

			// regra de 3 para determinar percentual por tipo de solicitação
			quantidadeDoTipoSolicitacao = quantidadeDoTipoSolicitacao.multiply(new BigDecimal("100"));
			BigDecimal percentualPorTipoSolicitacao = quantidadeDoTipoSolicitacao.divide(quantidadeDaUnidadeAtendimento, 2,
							BigDecimal.ROUND_HALF_UP);
			bean.setPercentualPorTipoSolicitacao(Util.formataBigDecimal(percentualPorTipoSolicitacao, 2, false));

			relatorioBeansFinal.set(i, bean);

			if(i == (relatorioBeansFinal.size() - 1)){

				beanTotalizador = new RelatorioResumoRegistroAtendimentoBean();
				beanTotalizador.setIdUnidadeAtendimento("0");
				beanTotalizador.setarBeansTotalizadores(relatorioBeansTotalizadoresFinal);
				beanTotalizador.setImprimirTotalizadores("true");
				relatorioBeansFinal.add(beanTotalizador);
				break;
			}
		}

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// Situação
		String situacao = "";
		if(filtroRA.getCodigoSituacao() != null
						&& Short.valueOf(filtroRA.getCodigoSituacao()).intValue() == RegistroAtendimento.SITUACAO_TODOS){

			situacao = "Todos";
		}else if(filtroRA.getCodigoSituacao() != null && Short.valueOf(filtroRA.getCodigoSituacao()).equals(Short.valueOf("501"))){

			situacao = "Reiterados";
		}else if(filtroRA.getCodigoSituacao() != null
						&& Short.valueOf(filtroRA.getCodigoSituacao()).intValue() == RegistroAtendimento.SITUACAO_PENDENTE){

			situacao = "Pendentes";
		}else if(filtroRA.getCodigoSituacao() != null
						&& Short.valueOf(filtroRA.getCodigoSituacao()).intValue() == RegistroAtendimento.SITUACAO_ENCERRADO){

			situacao = "Encerrados";
		}else if(filtroRA.getCodigoSituacao() != null){

			situacao = "Sem Local de Ocorrência";
		}

		// Período de Atendimento
		String periodoAtendimento = "";
		if(filtroRA.getDataAtendimentoInicial() != null && filtroRA.getDataAtendimentoFinal() != null){

			periodoAtendimento = Util.formatarData(filtroRA.getDataAtendimentoInicial()) + " a "
							+ Util.formatarData(filtroRA.getDataAtendimentoFinal());
		}

		// Período de Encerramento
		String periodoEncerramento = "";
		if(filtroRA.getDataEncerramentoInicial() != null && filtroRA.getDataEncerramentoFinal() != null){

			periodoEncerramento = Util.formatarData(filtroRA.getDataEncerramentoInicial()) + " a "
							+ Util.formatarData(filtroRA.getDataEncerramentoFinal());
		}

		// Unidade Superior ou Chefia
		String unidadeSuperior = "";
		if(filtroRA.getUnidadeSuperior() != null){

			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, filtroRA
							.getUnidadeSuperior().getId()));

			Collection colecaoUnidadeOrganizacional = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

			unidadeSuperior += unidadeOrganizacional.getId().toString() + " - " + unidadeOrganizacional.getDescricao();
		}

		// adiciona os parâmetros do relatório
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("situacao", situacao);
		parametros.put("periodoAtendimento", periodoAtendimento);
		parametros.put("periodoEncerramento", periodoEncerramento);
		parametros.put("unidadeSuperior", unidadeSuperior);

		if(tipoFormatoRelatorio == TIPO_PDF){
			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeansFinal);

		// processa a geração do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_RESUMO_REGISTRO_ATENDIMENTO, parametros, ds, tipoFormatoRelatorio);

		// retorna o relatório gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	@Override
	public void agendarTarefaBatch(){

		// TODO Auto-generated method stub
	}

}