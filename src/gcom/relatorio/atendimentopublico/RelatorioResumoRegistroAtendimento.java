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
 * [UC0XXX] Relat�rio Resumo Registro de Atendimento
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

		// obt�m os par�metros passados
		FiltrarRegistroAtendimentoHelper filtroRA = (FiltrarRegistroAtendimentoHelper) getParametro("filtroRA");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// monta a cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();
		List relatorioBeansFinal = new ArrayList();
		List relatorioBeansTotalizadores = new ArrayList();
		List relatorioBeansTotalizadoresFinal = new ArrayList();
		Fachada fachada = Fachada.getInstancia();

		// obt�m a cole��o com dados do detalhe do relat�rio
		List colecaoDadosRelatorio = (List) fachada.pesquisaRelatorioResumoRA(filtroRA, 1);
		// obt�m a cole��o com dados dos totalizadores do relat�rio
		List colecaoDadosTotalizadoresRelatorio = (List) fachada.pesquisaRelatorioResumoRA(filtroRA, 2);

		if(colecaoDadosRelatorio == null || colecaoDadosRelatorio.isEmpty()){
			// N�o existem dados para a exibi��o do relat�rio.
			throw new ActionServletException("atencao.relatorio.vazio");
		}

		// processamento dos dados dos totalizadores do relat�rio
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

			// Id da Especifica��o
			bean.setIdEspecificacaoTotal(dados[0].toString());

			// Descri��o da Especifica��o
			bean.setDescricaoEspecificacaoTotal(dados[1].toString());

			// Id do Tipo da Solicita��o
			bean.setIdTipoSolicitacaoTotal(dados[2].toString());

			// Descri��o do Tipo da Solicita��o
			bean.setDescricaoTipoSolicitacaoTotal(dados[3].toString());

			// Quantidade por especifica��o
			bean.setTotalPorEspecificacao(dados[4].toString());

			// armazena totais de quebra por tipo de solicita��o por unidade de atendimento
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

		// preenche os valores dos percentuais dos tipo de solicita��o
		// com rela��o ao total e percentuais das especifica��es com
		// rela��o ao tipo de solicita��o
		for(int i = 0; i < relatorioBeansTotalizadores.size(); i++){

			RelatorioResumoRegistroAtendimentoBeanHelper bean = (RelatorioResumoRegistroAtendimentoBeanHelper) relatorioBeansTotalizadores
							.get(i);

			BigDecimal quantidadeDoTipoSolicitacao = new BigDecimal(idTipoQuantidade.get(bean.getIdTipoSolicitacaoTotal()));

			// totaliza percentual por tipo de solicita��o
			if(bean.getImprimirTotalizadorTipoSolicitacao() != null){

				BigDecimal quantidadeTotal = new BigDecimal(totalGeral.toString());

				// regra de 3 para determinar percentual por tipo de solicita��o
				bean.setTotalPorTipoSolicitacao(quantidadeDoTipoSolicitacao.toEngineeringString());
				quantidadeDoTipoSolicitacao = quantidadeDoTipoSolicitacao.multiply(new BigDecimal("100"));
				BigDecimal percentualPorTipoSolicitacaoTotal = quantidadeDoTipoSolicitacao.divide(quantidadeTotal, 2,
								BigDecimal.ROUND_HALF_UP);
				bean.setPercentualPorTipoSolicitacaoTotal(Util.formataBigDecimal(percentualPorTipoSolicitacaoTotal, 2, false));
			}

			// totaliza percentual por especifica��o
			BigDecimal quantidadeDaEspecificacao = new BigDecimal(bean.getTotalPorEspecificacao());

			quantidadeDoTipoSolicitacao = new BigDecimal(idTipoQuantidade.get(bean.getIdTipoSolicitacaoTotal()));

			// regra de 3 para determinar percentual por tipo de solicita��o
			quantidadeDaEspecificacao = quantidadeDaEspecificacao.multiply(new BigDecimal("100"));
			BigDecimal percentualPorEspecificacao = quantidadeDaEspecificacao.divide(quantidadeDoTipoSolicitacao, 2,
							BigDecimal.ROUND_HALF_UP);
			bean.setPercentualPorEspecificacaoTotal(Util.formataBigDecimal(percentualPorEspecificacao, 2, false));

			relatorioBeansTotalizadoresFinal.add(bean);
		}

		// processamento dos dados do detalhe do relat�rio
		List listaRelatorioHelper = new ArrayList<RelatorioResumoRegistroAtendimentoHelper>();

		for(int i = 0; i < colecaoDadosRelatorio.size(); i++){

			Object[] dados = (Object[]) colecaoDadosRelatorio.get(i);

			RelatorioResumoRegistroAtendimentoBean bean = new RelatorioResumoRegistroAtendimentoBean();

			// Cabe�alho

			// Id da Unidade de Atendimento
			bean.setIdUnidadeAtendimento(dados[4].toString());

			// Descricao da Unidade de Atendimento
			bean.setDescricaoUnidadeAtendimento(dados[5].toString());

			// Linha Detalhe

			// Id da Especifica��o
			bean.setIdEspecificacao(dados[0].toString());

			// Descri��o da Especifica��o
			bean.setDescricaoEspecificacao(dados[1].toString());

			// Id do Tipo da Solicita��o
			bean.setIdTipoSolicitacao(dados[2].toString());

			// Descri��o do Tipo da Solicita��o
			bean.setDescricaoTipoSolicitacao(dados[3].toString());

			// Quantidade por especifica��o
			bean.setQuantidadePorEspecificacao(dados[6].toString());

			// armazena totais de quebra por tipo de solicita��o por unidade de atendimento
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

		// preenche os valores dos percentuais das especifica��es com rela��o
		// ao tipo de solicita��o por unidade de atendimento e armazena os
		// totais dos tipo de solicita��o por unidade de atendimento
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

					// totaliza percentual por especifica��o
					if(helper.getQuantidadePorTipo() != null){

						BigDecimal quantidadeDaEspecificacao = new BigDecimal(bean.getQuantidadePorEspecificacao());

						// regra de 3 para determinar percentual por especifica��o
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

		// preenche os valores dos percentuais dos tipo de solicita��o
		// com rela��o a unidade de atendimento
		RelatorioResumoRegistroAtendimentoBean beanTotalizador = null;
		for(int i = 0; i < relatorioBeansFinal.size(); i++){

			RelatorioResumoRegistroAtendimentoBean bean = (RelatorioResumoRegistroAtendimentoBean) relatorioBeans.get(i);

			String qtdUnidade = idUnidadeQuantidade.get(bean.getIdUnidadeAtendimento());
			BigDecimal quantidadeDoTipoSolicitacao = new BigDecimal(bean.getQuantidadePorTipoSolicitacao());

			// totaliza percentual por tipo de solicita��o
			BigDecimal quantidadeDaUnidadeAtendimento = new BigDecimal(qtdUnidade);

			// regra de 3 para determinar percentual por tipo de solicita��o
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

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// Situa��o
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

			situacao = "Sem Local de Ocorr�ncia";
		}

		// Per�odo de Atendimento
		String periodoAtendimento = "";
		if(filtroRA.getDataAtendimentoInicial() != null && filtroRA.getDataAtendimentoFinal() != null){

			periodoAtendimento = Util.formatarData(filtroRA.getDataAtendimentoInicial()) + " a "
							+ Util.formatarData(filtroRA.getDataAtendimentoFinal());
		}

		// Per�odo de Encerramento
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

		// adiciona os par�metros do relat�rio
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("situacao", situacao);
		parametros.put("periodoAtendimento", periodoAtendimento);
		parametros.put("periodoEncerramento", periodoEncerramento);
		parametros.put("unidadeSuperior", unidadeSuperior);

		if(tipoFormatoRelatorio == TIPO_PDF){
			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeansFinal);

		// processa a gera��o do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_RESUMO_REGISTRO_ATENDIMENTO, parametros, ds, tipoFormatoRelatorio);

		// retorna o relat�rio gerado
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