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
 * Ivan Sérgio Virginio da Silva Júnior
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
import gcom.batch.Relatorio;
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
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * classe responsável por criar o relatório de Estatística de Atendimento por Atendente.
 * 
 * @author isilva
 * @created 23/03/2011
 */
public class RelatorioEstatisticaAtendimentoPorAtendente
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioEstatisticaAtendimentoPorAtendente(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_ESTATISTICA_ATENDIMENTO_POR_ATENDENTE);
	}

	@Deprecated
	public RelatorioEstatisticaAtendimentoPorAtendente() {

		super(null, "");
	}

	@SuppressWarnings("unchecked")
	public Object executar() throws TarefaException{

		byte[] retorno = null;
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		// obtêm os parâmetros passados
		FiltrarRegistroAtendimentoHelper filtroRA = (FiltrarRegistroAtendimentoHelper) getParametro("filtroRA");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// monta a coleção de beans do relatório
		List relatorioBeans = new ArrayList();
		Fachada fachada = Fachada.getInstancia();

		// obtém a coleção com dados do detalhe do relatório
		/**
		 * Obter dados do Relatório Estatistica Atendimento por Atendente pelos parametros
		 * informados no filtro
		 * [0] - idUnidade;
		 * [1] - descricaoUnidade;
		 * [2] - idUsuario;
		 * [3] - nomeUsuario;
		 * [4] - dataRegistroAtendimento;
		 * [5] - ultimaAlteracaoUnidade
		 */
		List colecaoDadosRelatorio = (List) fachada.pesquisaRelatorioEstatisticaAtendimentoPorAtendente(filtroRA);

		if(colecaoDadosRelatorio == null || colecaoDadosRelatorio.isEmpty()){
			// Não existem dados para a exibição do relatório.
			throw new ActionServletException("atencao.relatorio.vazio");
		}

		Map<Integer, Collection<EstatisticaAtendimentoPorAtendenteHelper>> mapaEstatisticaAtendimentoPorAtendente = new TreeMap<Integer, Collection<EstatisticaAtendimentoPorAtendenteHelper>>();

		for(int i = 0; i < colecaoDadosRelatorio.size(); i++){

			Object[] dados = (Object[]) colecaoDadosRelatorio.get(i);
			/**
			 * [0] - idUnidade;
			 * [1] - descricaoUnidade;
			 * [2] - idUsuario;
			 * [3] - nomeUsuario;
			 * [4] - dataRegistroAtendimento;
			 * [5] - ultimaAlteracaoUnidade
			 */

			Integer idUnidade = (Integer) dados[0];
			String descricaoUnidade = (String) dados[1];
			Integer idUsuario = (Integer) dados[2];
			String nomeUsuario = (String) dados[3];
			Date dataRegistroAtendimento = (Date) dados[4];
			Date ultimaAlteracaoUnidade = (Date) dados[5];

			EstatisticaAtendimentoPorAtendenteHelper estatisticaAtendimentoPorAtendenteHelper = new EstatisticaAtendimentoPorAtendenteHelper();

			estatisticaAtendimentoPorAtendenteHelper.setIdUnidade(idUnidade);
			estatisticaAtendimentoPorAtendenteHelper.setNomeUnidade(descricaoUnidade);
			estatisticaAtendimentoPorAtendenteHelper.setIdAtendente(idUsuario);
			estatisticaAtendimentoPorAtendenteHelper.setNomeAtendente(nomeUsuario);
			estatisticaAtendimentoPorAtendenteHelper.setDataRegistroAtendimento(dataRegistroAtendimento);
			estatisticaAtendimentoPorAtendenteHelper.setUltimaAlteracaoUnidade(ultimaAlteracaoUnidade);

			addEstatisticaAtendimentoPorAtendenteHelperMap(mapaEstatisticaAtendimentoPorAtendente, idUnidade,
							estatisticaAtendimentoPorAtendenteHelper);
		}

		if(mapaEstatisticaAtendimentoPorAtendente != null && !mapaEstatisticaAtendimentoPorAtendente.isEmpty()){

			Set set = mapaEstatisticaAtendimentoPorAtendente.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				Integer idUnidade = (Integer) iterMap.next();

				Collection<EstatisticaAtendimentoPorAtendenteHelper> novaColecaoEstatisticaAtendimentoPorAtendenteHelper = mapaEstatisticaAtendimentoPorAtendente
								.get(idUnidade);

				// **************** Calculo da Quantidade *************
				Integer quantidadeRAAtendidasAnteriorPorUnidadeTotalParaCalculo = 0;

				for(EstatisticaAtendimentoPorAtendenteHelper estatisticaAtendimentoPorAtendenteHelper : novaColecaoEstatisticaAtendimentoPorAtendenteHelper){
					// *********************** Totalização ***************************************
					// **************** Calculo da Quantidade *************
					quantidadeRAAtendidasAnteriorPorUnidadeTotalParaCalculo = quantidadeRAAtendidasAnteriorPorUnidadeTotalParaCalculo
									.intValue()
									+ estatisticaAtendimentoPorAtendenteHelper.getQuantidadeRAAtendidas().intValue();
					// ****************************************************
				}

				if(novaColecaoEstatisticaAtendimentoPorAtendenteHelper != null
								&& !novaColecaoEstatisticaAtendimentoPorAtendenteHelper.isEmpty()){

					int i = 1;

					// **************** Calculo da Quantidade *************
					Integer quantidadeRAAtendidasAnteriorPorUnidadeTotal = 0;
					BigDecimal minutosPorAtendenteTotal = BigDecimal.ZERO;

					for(EstatisticaAtendimentoPorAtendenteHelper estatisticaAtendimentoPorAtendenteHelper : novaColecaoEstatisticaAtendimentoPorAtendenteHelper){
						RelatorioEstatisticaAtendimentoPorAtendenteBean bean = new RelatorioEstatisticaAtendimentoPorAtendenteBean();
						bean.setIdUnidadeAtendimento(String.valueOf(estatisticaAtendimentoPorAtendenteHelper.getIdUnidade()));
						bean.setDescricaoUnidadeAtendimento(estatisticaAtendimentoPorAtendenteHelper.getNomeUnidade());
						bean.setIdAtendente(String.valueOf(estatisticaAtendimentoPorAtendenteHelper.getIdAtendente()));
						bean.setNomeAtendente(estatisticaAtendimentoPorAtendenteHelper.getNomeAtendente());
						bean.setQuantidadePorAtendente(String.valueOf(estatisticaAtendimentoPorAtendenteHelper.getQuantidadeRAAtendidas()));

						BigDecimal total = BigDecimal.ZERO;

						if(quantidadeRAAtendidasAnteriorPorUnidadeTotalParaCalculo != null
										&& quantidadeRAAtendidasAnteriorPorUnidadeTotalParaCalculo != 0){

							BigDecimal quantidadeRAAtendidas = new BigDecimal(estatisticaAtendimentoPorAtendenteHelper
											.getQuantidadeRAAtendidas());

							total = quantidadeRAAtendidas.divide(new BigDecimal(quantidadeRAAtendidasAnteriorPorUnidadeTotalParaCalculo),
											2, BigDecimal.ROUND_HALF_UP);
						}

						total = total.multiply(new BigDecimal("100.00"), new MathContext(2, RoundingMode.HALF_UP));

						bean.setPercentualPorAtendente(Util.formataBigDecimal(total, 2, true));
						bean.setMinutosPorAtendente(Util.formataBigDecimal(estatisticaAtendimentoPorAtendenteHelper.getMinutoAtendidos(),
										2, true));
						bean.setMediaAtendimento(Util.formataBigDecimal(estatisticaAtendimentoPorAtendenteHelper.getMediaAtendente(), 2,
										true));

						// *********************** Totalização
						// ***************************************

						// **************** Calculo da Quantidade *************
						quantidadeRAAtendidasAnteriorPorUnidadeTotal = quantidadeRAAtendidasAnteriorPorUnidadeTotal.intValue()
										+ estatisticaAtendimentoPorAtendenteHelper.getQuantidadeRAAtendidas().intValue();
						// ****************************************************

						// **************** Calculo dos Minutos ***************
						BigDecimal minuto = estatisticaAtendimentoPorAtendenteHelper.getMinutoAtendidos();
						minutosPorAtendenteTotal = minutosPorAtendenteTotal.add(minuto);
						// ****************************************************

						if(i == novaColecaoEstatisticaAtendimentoPorAtendenteHelper.size()){

							BigDecimal mediaAtendimentoTotal = BigDecimal.ZERO;

							bean.setQuantidadePorAtendenteTotal(String.valueOf(quantidadeRAAtendidasAnteriorPorUnidadeTotal));
							bean.setMinutosPorAtendenteTotal(Util.formataBigDecimal(minutosPorAtendenteTotal, 2, true));

							// **************** Calculo da Média ***************
							if(quantidadeRAAtendidasAnteriorPorUnidadeTotal != null && quantidadeRAAtendidasAnteriorPorUnidadeTotal != 0){

								mediaAtendimentoTotal = minutosPorAtendenteTotal.divide(new BigDecimal(
												quantidadeRAAtendidasAnteriorPorUnidadeTotal), 2, BigDecimal.ROUND_HALF_UP);
							}

							mediaAtendimentoTotal.multiply(new BigDecimal("100.00"), new MathContext(2, RoundingMode.HALF_UP));

							// ****************************************************

							bean.setMediaAtendimentoTotal(Util.formataBigDecimal(mediaAtendimentoTotal, 2, true));

							bean.setPercentualPorAtendenteTotal(Util.formataBigDecimal(new BigDecimal("100"), 2, true));

							bean.setImprimirUnidadeAtendimento("true");
						}

						relatorioBeans.add(bean);
						i++;
					}
				}
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
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// processa a geração do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ESTATISTICA_ATENDIMENTO_POR_ATENDENTE, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_ESTATISTICA_ATENDIMENTO_POR_ATENDENTE, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;
	}

	/**
	 * @author isilva
	 * @param mapa
	 * @param chave
	 * @param estatisticaAtendimentoPorAtendenteHelper
	 */
	private void addEstatisticaAtendimentoPorAtendenteHelperMap(Map<Integer, Collection<EstatisticaAtendimentoPorAtendenteHelper>> mapa,
					Integer chave, EstatisticaAtendimentoPorAtendenteHelper estatisticaAtendimentoPorAtendenteHelper){

		Collection<EstatisticaAtendimentoPorAtendenteHelper> novaColecaoEstatisticaAtendimentoPorAtendenteHelper = new ArrayList<EstatisticaAtendimentoPorAtendenteHelper>();

		if(mapa.get(chave) != null){

			Collection<EstatisticaAtendimentoPorAtendenteHelper> colecaoEstatisticaAtendimentoPorAtendenteHelperAnterior = mapa.get(chave);

			if(colecaoEstatisticaAtendimentoPorAtendenteHelperAnterior != null
							&& !colecaoEstatisticaAtendimentoPorAtendenteHelperAnterior.isEmpty()){

				boolean contem = colecaoEstatisticaAtendimentoPorAtendenteHelperAnterior.contains(estatisticaAtendimentoPorAtendenteHelper);

				if(contem){

					Iterator<EstatisticaAtendimentoPorAtendenteHelper> iteratorEstatisticaAtendimentoPorAtendenteHelperAnterior = colecaoEstatisticaAtendimentoPorAtendenteHelperAnterior
									.iterator();
					while(iteratorEstatisticaAtendimentoPorAtendenteHelperAnterior.hasNext()){

						EstatisticaAtendimentoPorAtendenteHelper estatisticaAtendimentoPorAtendenteHelperAnterior = (EstatisticaAtendimentoPorAtendenteHelper) iteratorEstatisticaAtendimentoPorAtendenteHelperAnterior
										.next();

						if(estatisticaAtendimentoPorAtendenteHelperAnterior.getIdAtendente().intValue() == estatisticaAtendimentoPorAtendenteHelper
										.getIdAtendente().intValue()){

							// **************** Calculo da Quantidade *************
							Integer quantidadeRAAtendidasAnterior = estatisticaAtendimentoPorAtendenteHelperAnterior
											.getQuantidadeRAAtendidas();

							if(quantidadeRAAtendidasAnterior == null){
								quantidadeRAAtendidasAnterior = 0;
							}

							estatisticaAtendimentoPorAtendenteHelperAnterior.setQuantidadeRAAtendidas(quantidadeRAAtendidasAnterior
											.intValue() + 1);
							// ****************************************************

							// **************** Calculo dos Minutos ***************
							BigDecimal minutoAtendidos = Util.calcularDiferencaEntreDatas(estatisticaAtendimentoPorAtendenteHelper
											.getDataRegistroAtendimento(), estatisticaAtendimentoPorAtendenteHelper
											.getUltimaAlteracaoUnidade(), ConstantesSistema.DIFERENCA_MINUTOS);
							BigDecimal minutoAtendidosAnterior = estatisticaAtendimentoPorAtendenteHelperAnterior.getMinutoAtendidos();

							if(minutoAtendidos == null){
								minutoAtendidos = BigDecimal.ZERO;
							}

							if(minutoAtendidosAnterior == null){
								minutoAtendidosAnterior = BigDecimal.ZERO;
							}

							estatisticaAtendimentoPorAtendenteHelperAnterior.setMinutoAtendidos((minutoAtendidos
											.add(minutoAtendidosAnterior)).setScale(2, BigDecimal.ROUND_HALF_UP));
							// ****************************************************

							// **************** Calculo da Média ***************
							if(estatisticaAtendimentoPorAtendenteHelperAnterior.getQuantidadeRAAtendidas() != null
											&& estatisticaAtendimentoPorAtendenteHelperAnterior.getQuantidadeRAAtendidas() != 0){

								BigDecimal mediaAtendente = estatisticaAtendimentoPorAtendenteHelperAnterior.getMinutoAtendidos()
												.divide(
																new BigDecimal(estatisticaAtendimentoPorAtendenteHelperAnterior
																				.getQuantidadeRAAtendidas()), 2, BigDecimal.ROUND_HALF_UP);

								estatisticaAtendimentoPorAtendenteHelperAnterior.setMediaAtendente(mediaAtendente);

							}else{
								estatisticaAtendimentoPorAtendenteHelperAnterior.setMediaAtendente(BigDecimal.ZERO);
							}
							// ****************************************************
						}

					}

				}else{

					// **************** Calculo dos Minutos ***************
					BigDecimal minutoAtendidos = Util.calcularDiferencaEntreDatas(estatisticaAtendimentoPorAtendenteHelper
									.getDataRegistroAtendimento(), estatisticaAtendimentoPorAtendenteHelper.getUltimaAlteracaoUnidade(),
									ConstantesSistema.DIFERENCA_MINUTOS);
					estatisticaAtendimentoPorAtendenteHelper.setMinutoAtendidos(minutoAtendidos.setScale(2, BigDecimal.ROUND_HALF_UP));
					// ****************************************************

					estatisticaAtendimentoPorAtendenteHelper.setQuantidadeRAAtendidas(1);

					// **************** Calculo da Média ***************
					BigDecimal mediaAtendente = estatisticaAtendimentoPorAtendenteHelper.getMinutoAtendidos().divide(
									new BigDecimal(estatisticaAtendimentoPorAtendenteHelper.getQuantidadeRAAtendidas()), 2,
									BigDecimal.ROUND_HALF_UP);

					estatisticaAtendimentoPorAtendenteHelper.setMediaAtendente(mediaAtendente);
					// ****************************************************

					colecaoEstatisticaAtendimentoPorAtendenteHelperAnterior.add(estatisticaAtendimentoPorAtendenteHelper);
				}
			}

			novaColecaoEstatisticaAtendimentoPorAtendenteHelper = colecaoEstatisticaAtendimentoPorAtendenteHelperAnterior;
			mapa.remove(chave);

		}else{

			// **************** Calculo dos Minutos ***************
			BigDecimal minutoAtendidos = Util.calcularDiferencaEntreDatas(estatisticaAtendimentoPorAtendenteHelper
							.getDataRegistroAtendimento(), estatisticaAtendimentoPorAtendenteHelper.getUltimaAlteracaoUnidade(),
							ConstantesSistema.DIFERENCA_MINUTOS);
			estatisticaAtendimentoPorAtendenteHelper.setMinutoAtendidos(minutoAtendidos.setScale(2, BigDecimal.ROUND_HALF_UP));
			// ****************************************************

			estatisticaAtendimentoPorAtendenteHelper.setQuantidadeRAAtendidas(1);

			// **************** Calculo da Média ***************
			BigDecimal mediaAtendente = estatisticaAtendimentoPorAtendenteHelper.getMinutoAtendidos().divide(
							new BigDecimal(estatisticaAtendimentoPorAtendenteHelper.getQuantidadeRAAtendidas()), 2,
							BigDecimal.ROUND_HALF_UP);

			estatisticaAtendimentoPorAtendenteHelper.setMediaAtendente(mediaAtendente);
			// ****************************************************

			novaColecaoEstatisticaAtendimentoPorAtendenteHelper.add(estatisticaAtendimentoPorAtendenteHelper);
		}

		mapa.put(chave, novaColecaoEstatisticaAtendimentoPorAtendenteHelper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int calcularTotalRegistrosRelatorio(){

		// obtêm os parâmetros passados
		FiltrarRegistroAtendimentoHelper filtroRA = (FiltrarRegistroAtendimentoHelper) getParametro("filtroRA");
		Collection resultado = (List) Fachada.getInstancia().pesquisaRelatorioEstatisticaAtendimentoPorAtendente(filtroRA);

		if(resultado != null){
			return resultado.size();
		}else{
			return 0;
		}
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioEstatisticaAtendimentoPorAtendente", this);
	}
}