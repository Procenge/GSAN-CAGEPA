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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.relatorio.transacao;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.FiltroUsuarioAlteracao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAlteracao;
import gcom.seguranca.transacao.AlteracaoTipo;
import gcom.seguranca.transacao.FiltroTabelaLinhaColunaAlteracao;
import gcom.seguranca.transacao.TabelaLinhaColunaAlteracao;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * classe responsável por criar o relatório de operação consultar
 * 
 * @author Rafael Corrêa
 * @created 06/04/2006
 * @author eduardo Henrique
 * @date 21/05/2008
 *       Incluído filtro de múltipla escolha de operações para visualização de Transações efetuadas.
 *       Filtro Argumento de Pesquisa foi retirado por enquanto, até revisão desta funcionalidade.
 */
public class RelatorioConsultarOperacao
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da classe RelatorioAnaliseFisicoQuimicaAgua
	 */
	public RelatorioConsultarOperacao(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_OPERACAO_CONSULTAR);
	}

	@Deprecated
	public RelatorioConsultarOperacao() {

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
		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Operacao operacao = (Operacao) getParametro("operacao");
		Usuario usuario = (Usuario) getParametro("usuario");
		UsuarioAcao usuarioAcao = (UsuarioAcao) getParametro("usuarioAcao");
		Date periodoRealizacaoInicial = (Date) getParametro("periodoRealizacaoInicial");
		Date periodoRealizacaoFinal = (Date) getParametro("periodoRealizacaoFinal");
		Date horarioRealizacaoInicial = (Date) getParametro("horarioRealizacaoInicial");
		Date horarioRealizacaoFinal = (Date) getParametro("horarioRealizacaoFinal");
		// Integer idTabela = (Integer) getParametro("idTabela");
		// Integer[] idTabelaColuna = (Integer[]) getParametro("idTabelaColuna");
		Integer id1 = (Integer) getParametro("id1");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		String[] idOperacoes = (String[]) getParametro("idOperacoes");
		// String idUsuario = (String) getParametro("idUsuario");
		// Integer idUsuarioAcao = (Integer) getParametro("idUsuarioAcao");

		Integer idOperacaoEfetuada = null;
		if(getParametro("idOperacaoEfetuada") != null && !getParametro("idOperacaoEfetuada").equals("")){
			idOperacaoEfetuada = new Integer(getParametro("idOperacaoEfetuada").toString());
		}
		String periodoRealizacao = "";
		Hashtable<Integer, String> argumentos = (Hashtable) getParametro("argumentos");

		// valor de retorno
		byte[] retorno = null;

		// Instacia a fachada
		Fachada fachada = Fachada.getInstancia();
		Collection colecaoOperacoesEfetuadas = null;

		if(operacao.getId() != null){
			idOperacoes = new String[] {operacao.getId() + ""};
		}
		colecaoOperacoesEfetuadas = fachada.pesquisarUsuarioAlteracaoDasOperacoesEfetuadasHqlRelatorio(usuarioAcao.getId(), idOperacoes,
						usuario.getId() != null ? usuario.getId().toString() : "", periodoRealizacaoInicial, periodoRealizacaoFinal,
						horarioRealizacaoInicial, horarioRealizacaoFinal, argumentos, id1);
		// COMENTADO POR SAVIO
		// colecaoOperacoesEfetuadas = fachada.
		// pesquisarUsuarioAlteracaoDasOperacoesEfetuadasHqlRelatorio(usuarioAcao.getId(),
		// operacao.getId(),
		// usuario.getId(),
		// periodoRealizacaoInicial,periodoRealizacaoFinal,horarioRealizacaoInicial,
		// horarioRealizacaoFinal,idTabela,idTabelaColuna,id1);
		/* (Collection) getParametro("colecaoOperacoesEfetuadas"); */

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		RelatorioConsultarOperacaoBean relatorioBean = null;
		// se a coleção de parâmetros da analise não for vazia
		if(colecaoOperacoesEfetuadas != null && !colecaoOperacoesEfetuadas.isEmpty()){
			// coloca a coleção de parâmetros da analise no iterator
			Iterator colecaoOperacoesEfetuadasIterator = colecaoOperacoesEfetuadas.iterator();

			// laço para criar a coleção de parâmetros da analise
			while(colecaoOperacoesEfetuadasIterator.hasNext()){

				OperacaoEfetuada operacaoEfetuada = (OperacaoEfetuada) colecaoOperacoesEfetuadasIterator.next();

				FiltroUsuarioAlteracao filtroUsuarioAlteracao = new FiltroUsuarioAlteracao();
				filtroUsuarioAlteracao.adicionarParametro(new ParametroSimples(FiltroUsuarioAlteracao.OPERACAO_EFETUADA_ID,
								operacaoEfetuada.getId()));
				filtroUsuarioAlteracao.adicionarCaminhoParaCarregamentoEntidade("usuario");

				Collection colecaoUsuarioAlteracao = fachada.pesquisar(filtroUsuarioAlteracao, UsuarioAlteracao.class.getName());
				String nomeUsuario = "";
				String codigoUsuario = "";

				if(colecaoUsuarioAlteracao != null && !colecaoUsuarioAlteracao.isEmpty()){
					UsuarioAlteracao usuarioAlteracao = (UsuarioAlteracao) colecaoUsuarioAlteracao.iterator().next();
					nomeUsuario = usuarioAlteracao.getUsuario().getNomeUsuario();
					codigoUsuario = usuarioAlteracao.getUsuario().getId().toString();
				}

				String dataFormatada = "";

				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy k:mm:ss");

				dataFormatada = formato.format(operacaoEfetuada.getUltimaAlteracao());

				relatorioBean = new RelatorioConsultarOperacaoBean(
				// Data Realização
								dataFormatada,

								// Nome Operação
								operacaoEfetuada.getOperacao().getDescricao(),

								// Usuário
								nomeUsuario,

								codigoUsuario);
				// --------------------------------------------COMEÇA
				// if(idOperacaoEfetuada != null){
				String[][] outrosDados = operacaoEfetuada.formatarDadosAdicionais();
				if(outrosDados != null){
					Collection listaDestalheInformacoesItem = new ArrayList();
					String descricao = null;
					if(operacao.getArgumentoPesquisa() != null){
						descricao = operacao.getArgumentoPesquisa().getDescricaoColuna();
					}
					String valor = ((Integer) operacaoEfetuada.getArgumentoValor()).toString();
					Object argumento = new RelatorioConsultarOperacaoDetalhe2Bean(descricao, valor);
					listaDestalheInformacoesItem.add(argumento);
					for(int i = 0; i < outrosDados.length; i++){
						Object informacoesItem = new RelatorioConsultarOperacaoDetalhe2Bean(outrosDados[i][0], outrosDados[i][1]);
						listaDestalheInformacoesItem.add(informacoesItem);
					}
					relatorioBean.setDetalheInformacoesItemBean(listaDestalheInformacoesItem);
				}
				// -----------------------------------------
					Collection coll = null;

					FiltroTabelaLinhaColunaAlteracao filtroTabelaLinhaColunaAlteracao = new FiltroTabelaLinhaColunaAlteracao();
					filtroTabelaLinhaColunaAlteracao
									.adicionarCaminhoParaCarregamentoEntidade(FiltroTabelaLinhaColunaAlteracao.TABELA_COLUNA);
					filtroTabelaLinhaColunaAlteracao
									.adicionarCaminhoParaCarregamentoEntidade(FiltroTabelaLinhaColunaAlteracao.TABELA_LINHA_ALTERACAO);
					filtroTabelaLinhaColunaAlteracao.adicionarCaminhoParaCarregamentoEntidade(FiltroTabelaLinhaColunaAlteracao.TABELA);
					filtroTabelaLinhaColunaAlteracao
									.adicionarCaminhoParaCarregamentoEntidade(FiltroTabelaLinhaColunaAlteracao.OPERACAO_EFETUADA);
					filtroTabelaLinhaColunaAlteracao
									.adicionarCaminhoParaCarregamentoEntidade(FiltroTabelaLinhaColunaAlteracao.ALTERACAO_TIPO);
					filtroTabelaLinhaColunaAlteracao.adicionarParametro(new ParametroSimples(
									FiltroTabelaLinhaColunaAlteracao.OPERACAO_EFETUADA_ID, operacaoEfetuada.getId()));
					filtroTabelaLinhaColunaAlteracao.adicionarParametro(new ParametroSimples(
									FiltroTabelaLinhaColunaAlteracao.ALTERACAO_TIPO_ID, AlteracaoTipo.ALTERACAO));
					coll = Fachada.getInstancia().pesquisar(filtroTabelaLinhaColunaAlteracao, TabelaLinhaColunaAlteracao.class.getName());
					if(coll != null && !coll.isEmpty()){
						Collection listaDestalheAlteracao = new ArrayList();
						Iterator colAlteracao = coll.iterator();
						while(colAlteracao.hasNext()){
							TabelaLinhaColunaAlteracao detalheAlteracao = (TabelaLinhaColunaAlteracao) colAlteracao.next();
							Object alteracaoBean = new RelatorioConsultarOperacaoDetalheBean(AlteracaoTipo.ALTERACAO,
											detalheAlteracao.getTabelaColuna().getDescricaoColuna(),
											detalheAlteracao.getConteudoColunaAnterior(), detalheAlteracao.getConteudoColunaAtual(),
											Util.formatarDataComHora(detalheAlteracao.getUltimaAlteracao()));
							listaDestalheAlteracao.add(alteracaoBean);
						}
						relatorioBean.setDetalheAlteracaoBean(listaDestalheAlteracao);
					}

					filtroTabelaLinhaColunaAlteracao = new FiltroTabelaLinhaColunaAlteracao();
					filtroTabelaLinhaColunaAlteracao
									.adicionarCaminhoParaCarregamentoEntidade(FiltroTabelaLinhaColunaAlteracao.TABELA_COLUNA);
					filtroTabelaLinhaColunaAlteracao
									.adicionarCaminhoParaCarregamentoEntidade(FiltroTabelaLinhaColunaAlteracao.TABELA_LINHA_ALTERACAO);
					filtroTabelaLinhaColunaAlteracao.adicionarCaminhoParaCarregamentoEntidade(FiltroTabelaLinhaColunaAlteracao.TABELA);
					filtroTabelaLinhaColunaAlteracao
									.adicionarCaminhoParaCarregamentoEntidade(FiltroTabelaLinhaColunaAlteracao.OPERACAO_EFETUADA);
					filtroTabelaLinhaColunaAlteracao
									.adicionarCaminhoParaCarregamentoEntidade(FiltroTabelaLinhaColunaAlteracao.ALTERACAO_TIPO);
					filtroTabelaLinhaColunaAlteracao.adicionarParametro(new ParametroSimples(
									FiltroTabelaLinhaColunaAlteracao.OPERACAO_EFETUADA_ID, operacaoEfetuada.getId()));
					filtroTabelaLinhaColunaAlteracao.adicionarParametro(new ParametroSimples(
									FiltroTabelaLinhaColunaAlteracao.ALTERACAO_TIPO_ID, AlteracaoTipo.EXCLUSAO));

					Collection exclusoes = Fachada.getInstancia().pesquisar(filtroTabelaLinhaColunaAlteracao,
									TabelaLinhaColunaAlteracao.class.getName());

					if(exclusoes != null && !exclusoes.isEmpty()){

						Collection listaDestalheExclusao = new ArrayList();
						Iterator colExclusoes = exclusoes.iterator();
						while(colExclusoes.hasNext()){
							TabelaLinhaColunaAlteracao detalheExclusao = (TabelaLinhaColunaAlteracao) colExclusoes.next();
							Object exclusaoBean = new RelatorioConsultarOperacaoDetalheBean(AlteracaoTipo.EXCLUSAO,
											detalheExclusao.getTabelaColuna().getDescricaoColuna(),
											detalheExclusao.getConteudoColunaAnterior(), detalheExclusao.getConteudoColunaAtual(),
											Util.formatarDataComHora(detalheExclusao.getUltimaAlteracao()));
							listaDestalheExclusao.add(exclusaoBean);
						}
						relatorioBean.setDetalheExclusaoBean(listaDestalheExclusao);
					}

					filtroTabelaLinhaColunaAlteracao = new FiltroTabelaLinhaColunaAlteracao();
					filtroTabelaLinhaColunaAlteracao.setConsultaSemLimites(true);
					filtroTabelaLinhaColunaAlteracao.setCampoOrderBy("ultimaAlteracao");
					filtroTabelaLinhaColunaAlteracao
									.adicionarCaminhoParaCarregamentoEntidade(FiltroTabelaLinhaColunaAlteracao.TABELA_COLUNA);
					filtroTabelaLinhaColunaAlteracao
									.adicionarCaminhoParaCarregamentoEntidade(FiltroTabelaLinhaColunaAlteracao.TABELA_LINHA_ALTERACAO);
					filtroTabelaLinhaColunaAlteracao.adicionarCaminhoParaCarregamentoEntidade(FiltroTabelaLinhaColunaAlteracao.TABELA);
					filtroTabelaLinhaColunaAlteracao
									.adicionarCaminhoParaCarregamentoEntidade(FiltroTabelaLinhaColunaAlteracao.OPERACAO_EFETUADA);
					filtroTabelaLinhaColunaAlteracao
									.adicionarCaminhoParaCarregamentoEntidade(FiltroTabelaLinhaColunaAlteracao.ALTERACAO_TIPO);
					filtroTabelaLinhaColunaAlteracao.adicionarParametro(new ParametroSimples(
									FiltroTabelaLinhaColunaAlteracao.OPERACAO_EFETUADA_ID, operacaoEfetuada.getId()));
					filtroTabelaLinhaColunaAlteracao.adicionarParametro(new ParametroSimples(
									FiltroTabelaLinhaColunaAlteracao.ALTERACAO_TIPO_ID, AlteracaoTipo.INCLUSAO));

					Collection inclusoes = Fachada.getInstancia().pesquisar(filtroTabelaLinhaColunaAlteracao,
									TabelaLinhaColunaAlteracao.class.getName());

					if(inclusoes != null && !inclusoes.isEmpty()){
						Collection listaDestalheInclusao = new ArrayList();
						Iterator colInclusoes = inclusoes.iterator();
						while(colInclusoes.hasNext()){
							TabelaLinhaColunaAlteracao detalheInclusoes = (TabelaLinhaColunaAlteracao) colInclusoes.next();
							Object inclusoesBean = new RelatorioConsultarOperacaoDetalheBean(AlteracaoTipo.INCLUSAO,
											detalheInclusoes.getTabelaColuna().getDescricaoColuna(),
											detalheInclusoes.getConteudoColunaAnterior(), detalheInclusoes.getConteudoColunaAtual(),
											Util.formatarDataComHora(detalheInclusoes.getUltimaAlteracao()));
							listaDestalheInclusao.add(inclusoesBean);
						}
						relatorioBean.setDetalheInclusaoBean(listaDestalheInclusao);
					}
				// }
				// --------------------------------------------FINALIZA

				relatorioBean.setData(operacaoEfetuada.getUltimaAlteracao());

				if(idOperacaoEfetuada != null && !idOperacaoEfetuada.equals("")){
					if(operacaoEfetuada.getId().equals(idOperacaoEfetuada)){
						// adiciona o bean a coleção
						relatorioBeans.add(relatorioBean);
						break;
					}
				}else{
					// adiciona o bean a coleção
					relatorioBeans.add(relatorioBean);
				}
			}
		}

		if(relatorioBeans != null){

			Collections.sort(relatorioBeans, new Comparator() {

				public int compare(Object o1, Object o2){

					RelatorioConsultarOperacaoBean rel1 = (RelatorioConsultarOperacaoBean) o1;
					RelatorioConsultarOperacaoBean rel2 = (RelatorioConsultarOperacaoBean) o2;

					return rel1.getData().compareTo(rel2.getData());
				}

			});

		}

		// __________________________________________________________________

		// Parâmetros do relatório
		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// adiciona os parâmetros do relatório
		// adiciona o laudo da análise
		parametros.put("idOperacao", operacao.getId() == null ? "" : "" + operacao.getId());

		parametros.put("nomeOperacao", operacao.getDescricao());

		parametros.put("idUsuario", usuario == null ? "" : "" + usuario.getId());

		parametros.put("nomeUsuario", usuario.getNomeUsuario());

		parametros.put("acaoUsuario", usuarioAcao.getDescricao());

		// Monta o período de realização
		if(periodoRealizacaoInicial != null){
			periodoRealizacao = Util.formatarData(periodoRealizacaoInicial);
		}

		if(horarioRealizacaoInicial != null){
			periodoRealizacao += " às " + Util.formatarHoraSemData(horarioRealizacaoInicial);
		}

		if(periodoRealizacaoFinal != null){
			periodoRealizacao += " a " + Util.formatarData(periodoRealizacaoFinal);
		}

		if(horarioRealizacaoFinal != null){
			periodoRealizacao += " às " + Util.formatarHoraSemData(horarioRealizacaoFinal);
		}

		parametros.put("periodoRealizacao", periodoRealizacao);

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_OPERACAO_CONSULTAR, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.CONSULTAR_OPERACAO, idFuncionalidadeIniciada, null);
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

		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioConsultarOperacao", this);
	}
}
