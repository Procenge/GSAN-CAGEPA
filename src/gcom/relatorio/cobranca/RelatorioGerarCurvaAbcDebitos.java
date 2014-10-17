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

package gcom.relatorio.cobranca;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.FiltroSubCategoria;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cadastro.imovel.bean.GerarCurvaAbcDebitosHelper;
import gcom.cadastro.sistemaparametro.SistemaParametro;
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
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * [UC0622] - Gerar Curva ABC de Debitos
 * 
 * @author Ivan Sérgio
 * @date 07/08/2007
 */
public class RelatorioGerarCurvaAbcDebitos
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da classe RelatorioGerarCurvaAbcDebitos
	 */
	public RelatorioGerarCurvaAbcDebitos(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_GERAR_CURVA_ABC_DEBITOS);
	}

	@Deprecated
	public RelatorioGerarCurvaAbcDebitos() {

		super(null, "");
	}

	/**
	 * <Breve descrição sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * <Breve descrição sobre o subfluxo>
	 * <Identificador e nome do subfluxo>
	 * <Breve descrição sobre o fluxo secundário>
	 * <Identificador e nome do fluxo secundário>
	 * 
	 * @author Ivan Sérgio
	 * @date 07/08/2007
	 * @return
	 * @throws TarefaException
	 */
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		// Parametros
		String classificacao = (String) getParametro("classificacao");
		String referenciaCobrancaInicial = (String) getParametro("referenciaCobrancaInicial");
		String referenciaCobrancaFinal = (String) getParametro("referenciaCobrancaFinal");
		String indicadorImovelMedicaoIndividualizada = (String) getParametro("indicadorImovelMedicaoIndividualizada");
		String indicadorImovelParalizacaoFaturamentoCobranca = (String) getParametro("indicadorImovelParalizacaoFaturamentoCobranca");

		// Localidade
		String gerenciaRegional = (String) getParametro("idGerenciaRegional");
		String idUnidadeNegocio = (String) getParametro("idUnidadeNegocio");
		String idLocalidadeInicial = (String) getParametro("idLocalidadeInicial");
		String idLocalidadeFinal = (String) getParametro("idLocalidadeFinal");
		String idSetorComercialInicial = (String) getParametro("idSetorComercialInicial");
		String idSetorComercialFinal = (String) getParametro("idSetorComercialFinal");

		// Ligacoes Agua/Esgoto
		String[] situacaoLigacaoAgua = (String[]) getParametro("situacaoLigacaoAgua");
		String[] situacaoLigacaoEsgoto = (String[]) getParametro("situacaoLigacaoEsgoto");
		String intervaloMesesCortadoSuprimidoInicial = (String) getParametro("intervaloMesesCortadoSuprimidoInicial");
		String intervaloMesesCortadoSuprimidoFinal = (String) getParametro("intervaloMesesCortadoSuprimidoFinal");
		String intervaloConsumoMinimoFixadoEsgotoInicial = (String) getParametro("intervaloConsumoMinimoFixadoEsgotoInicial");
		String intervaloConsumoMinimoFixadoEsgotoFinal = (String) getParametro("intervaloConsumoMinimoFixadoEsgotoFinal");
		String indicadorMedicao = (String) getParametro("indicadorMedicao");
		String idTipoMedicao = (String) getParametro("idTipoMedicao");

		// Caracteristicas
		String idPerfilImovel = (String) getParametro("idPerfilImovel");
		String idTipoCategoria = (String) getParametro("idTipoCategoria");
		String[] categoria = (String[]) getParametro("categoria");
		String idSubCategoria = (String) getParametro("idSubCategoria");

		// Debito
		String valorMinimoDebito = (String) getParametro("valorMinimoDebito");
		String intervaloQuantidadeDocumentosInicial = (String) getParametro("intervaloQuantidadeDocumentosInicial");
		String intervaloQuantidadeDocumentosFinal = (String) getParametro("intervaloQuantidadeDocumentosFinal");
		String indicadorPagamentosNaoClassificados = (String) getParametro("indicadorPagamentosNaoClassificados");

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoDadosRelatorio = fachada.gerarCurvaAbcDebitos(classificacao, referenciaCobrancaInicial, referenciaCobrancaFinal,
						indicadorImovelMedicaoIndividualizada, indicadorImovelParalizacaoFaturamentoCobranca, gerenciaRegional,
						idUnidadeNegocio, idLocalidadeInicial, idLocalidadeFinal, idSetorComercialInicial, idSetorComercialFinal,
						situacaoLigacaoAgua, situacaoLigacaoEsgoto, intervaloMesesCortadoSuprimidoInicial,
						intervaloMesesCortadoSuprimidoFinal, intervaloConsumoMinimoFixadoEsgotoInicial,
						intervaloConsumoMinimoFixadoEsgotoFinal, indicadorMedicao, idTipoMedicao, idPerfilImovel, idTipoCategoria,
						categoria, idSubCategoria, valorMinimoDebito, intervaloQuantidadeDocumentosInicial,
						intervaloQuantidadeDocumentosFinal, indicadorPagamentosNaoClassificados);

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		byte[] retorno = null;

		// bean do relatorio
		RelatorioGerarCurvaAbcDebitosBean relatorioGerarCurvaAbcDebitosBean = null;

		// dados para o relatorio
		if(!Util.isVazioOrNulo(colecaoDadosRelatorio)){

			List<GerarCurvaAbcDebitosHelper> listaSimplificada = new ArrayList();
			GerarCurvaAbcDebitosHelper jaCadastrado = null;
			GerarCurvaAbcDebitosHelper gerarCurvaAbcDebitosHelper = null;
			Integer posicao = 0;
			Integer totalLigacoes = 0;
			Integer qtdLigacoesPorClassificacao = 0;
			BigDecimal totalValorLigacoes = new BigDecimal(0);

			Iterator iColecaoDadosRelatorio = colecaoDadosRelatorio.iterator();

			// Recupera as Faixas de Valores de Debitos
			Collection colecaoDebitoFaixaValores = fachada.pesquisarDebitoFaixaValores(0, null);

			if(colecaoDebitoFaixaValores != null & !colecaoDebitoFaixaValores.isEmpty()){
				Iterator iColecaoDebitoFaixaValores = null;
				Object[] dadosDebitoFaixaValores = null;
				Object[] dadosRelatorio = null;

				// Usado para fazer o controle da classificacao(Quebra)
				Integer idClassificacao = 0;
				Integer idClassificacaoAnterior = 0;

				Integer idFaixa = null;
				BigDecimal faixaInicial = null;
				BigDecimal faixaFinal = null;

				Integer idGerenciaRegional = 0;
				String nomeGerenciaRegional = null;
				Integer idLocalidade = 0;
				String nomeLocalidade = null;
				Integer idSetorComercial = 0;
				Integer codigoSetorComercial = 0;
				String nomeSetorComercial = null;

				List listaBeans = new ArrayList();

				// Usado para fazer o controle da ultima quebra
				boolean fazQuebra = false;

				while(iColecaoDadosRelatorio.hasNext()){
					dadosRelatorio = (Object[]) iColecaoDadosRelatorio.next();

					// Recupera o id do objeto de acordo com a classificacao selecionada
					// Gerencia; Localidade; Setor
					if(classificacao.equalsIgnoreCase("REGIONAL")){
						// Id da Gerencia Regional
						idClassificacao = (Integer) dadosRelatorio[6];
					}else if(classificacao.equalsIgnoreCase("LOCAL")){
						// Id da Localidade
						idClassificacao = (Integer) dadosRelatorio[8];
					}else if(classificacao.equalsIgnoreCase("SETORCOMERCIAL")){
						// Id do Setor Comercial
						idClassificacao = (Integer) dadosRelatorio[10];
					}else if(classificacao.equalsIgnoreCase("UNIDADENEGOCIO")){
						// Id da Unidade de Negócio
						idClassificacao = (Integer) dadosRelatorio[15];
					}

					// Faz a quebra de acordo com classificacao
					if(!idClassificacao.equals(idClassificacaoAnterior) & !idClassificacaoAnterior.equals(0)){
						// Faz o acumulado
						relatorioGerarCurvaAbcDebitosBean = new RelatorioGerarCurvaAbcDebitosBean();
						listaBeans = quebrarPorClassificacao(listaSimplificada, totalLigacoes, totalValorLigacoes,
										qtdLigacoesPorClassificacao, idGerenciaRegional, nomeGerenciaRegional, idLocalidade,
										nomeLocalidade, idSetorComercial, codigoSetorComercial, nomeSetorComercial,
										referenciaCobrancaInicial, referenciaCobrancaFinal, categoria, idTipoCategoria, idSubCategoria,
										classificacao, intervaloMesesCortadoSuprimidoInicial, intervaloMesesCortadoSuprimidoFinal,
										situacaoLigacaoAgua, idClassificacaoAnterior);

						Iterator iListaBeans = listaBeans.iterator();

						while(iListaBeans.hasNext()){
							relatorioGerarCurvaAbcDebitosBean = (RelatorioGerarCurvaAbcDebitosBean) iListaBeans.next();

							relatorioBeans.add(relatorioGerarCurvaAbcDebitosBean);
						}

						listaSimplificada.clear();
						totalLigacoes = 0;
						qtdLigacoesPorClassificacao = 0;
						totalValorLigacoes = new BigDecimal(0);
						fazQuebra = false;
					}

					if(listaSimplificada.isEmpty()){
						// Preenche os Helpers com todas as faixas para iniciar os acumulados
						iColecaoDebitoFaixaValores = colecaoDebitoFaixaValores.iterator();
						while(iColecaoDebitoFaixaValores.hasNext()){
							dadosDebitoFaixaValores = (Object[]) iColecaoDebitoFaixaValores.next();

							idFaixa = (Integer) dadosDebitoFaixaValores[0];
							faixaInicial = (BigDecimal) dadosDebitoFaixaValores[1];
							faixaFinal = (BigDecimal) dadosDebitoFaixaValores[2];

							gerarCurvaAbcDebitosHelper = new GerarCurvaAbcDebitosHelper(idFaixa, faixaInicial, faixaFinal);

							listaSimplificada.add(gerarCurvaAbcDebitosHelper);
						}
					}

					// Preenche o Helper com os outros dados
					gerarCurvaAbcDebitosHelper = new GerarCurvaAbcDebitosHelper((BigDecimal) dadosRelatorio[0], // Faixa
									// Inicial
									(BigDecimal) dadosRelatorio[1], // Faixa Final
									(Integer) dadosRelatorio[2], // Id Faixa
									(Integer) dadosRelatorio[3], // Quantidade Ligacoes
									(BigDecimal) dadosRelatorio[4], // Valor
									(Integer) dadosRelatorio[6], // Id da Gerencia
									(String) dadosRelatorio[7], // Nome da Gerencia
									(Integer) dadosRelatorio[8], // Id da Localidade
									(String) dadosRelatorio[9], // Nome da Localidade
									(Integer) dadosRelatorio[10], // Id do Setor Comercial
									(Integer) dadosRelatorio[11], // Codigo do Setor Comercial
									(String) dadosRelatorio[12]); // Nome do Setor Comercial

					// Faz os acumulados
					if(listaSimplificada.contains(gerarCurvaAbcDebitosHelper)){
						posicao = listaSimplificada.indexOf(gerarCurvaAbcDebitosHelper);

						jaCadastrado = (GerarCurvaAbcDebitosHelper) listaSimplificada.get(posicao);

						// Ligacoes
						jaCadastrado.setQuantidadeLigacoes((jaCadastrado.getQuantidadeLigacoes() + gerarCurvaAbcDebitosHelper
										.getQuantidadeLigacoes()));

						// Valor
						jaCadastrado.setValor(jaCadastrado.getValor().add(gerarCurvaAbcDebitosHelper.getValor()));

						// Id da Gerencia
						if(jaCadastrado.getIdGerenciaRegional() == null){
							jaCadastrado.setIdGerenciaRegional(gerarCurvaAbcDebitosHelper.getIdGerenciaRegional());
							idGerenciaRegional = jaCadastrado.getIdGerenciaRegional();

							// Nome da Gerencia
							jaCadastrado.setNomeGerenciaRegional(gerarCurvaAbcDebitosHelper.getNomeGerenciaRegional());
							nomeGerenciaRegional = jaCadastrado.getNomeGerenciaRegional();
						}

						// Id Localidade
						if(jaCadastrado.getIdLocalidade() == null){
							jaCadastrado.setIdLocalidade(gerarCurvaAbcDebitosHelper.getIdLocalidade());
							idLocalidade = jaCadastrado.getIdLocalidade();

							// Nome da Localidade
							jaCadastrado.setNomeLocalidade(gerarCurvaAbcDebitosHelper.getNomeLocalidade());
							nomeLocalidade = jaCadastrado.getNomeLocalidade();
						}

						// Id Setor Comercial
						if(jaCadastrado.getIdSetorComercial() == null){
							jaCadastrado.setIdSetorComercial(gerarCurvaAbcDebitosHelper.getIdSetorComercial());
							idSetorComercial = jaCadastrado.getIdSetorComercial();

							// Codigo Setor Comercial
							jaCadastrado.setCodigoSetorComercial(gerarCurvaAbcDebitosHelper.getCodigoSetorComercial());
							codigoSetorComercial = jaCadastrado.getCodigoSetorComercial();

							// Nome Setor Comercial
							jaCadastrado.setNomeSetorComercial(gerarCurvaAbcDebitosHelper.getNomeSetorComercial());
							nomeSetorComercial = jaCadastrado.getNomeSetorComercial();
						}

						// Valor Medio
						BigDecimal qtdLigacoes = new BigDecimal(jaCadastrado.getQuantidadeLigacoes());
						if(!jaCadastrado.getQuantidadeLigacoes().equals(0) && !jaCadastrado.getValor().equals(0)){
							jaCadastrado.setValorMedio(jaCadastrado.getValor().divide(qtdLigacoes, 2, RoundingMode.HALF_UP));
						}else{
							jaCadastrado.setValorMedio(qtdLigacoes);
						}

						// Quantidade de Ligacoes por Classificacao
						qtdLigacoesPorClassificacao += gerarCurvaAbcDebitosHelper.getQuantidadeLigacoes();
						// Total geral das Ligacoes
						totalLigacoes += jaCadastrado.getQuantidadeLigacoes();
						// Total geral dos Valores
						totalValorLigacoes = totalValorLigacoes.add(gerarCurvaAbcDebitosHelper.getValor());
					}

					idClassificacaoAnterior = idClassificacao;
					fazQuebra = true;
				}

				// Faz o acumulado da ultima quebra
				if(fazQuebra){
					relatorioGerarCurvaAbcDebitosBean = new RelatorioGerarCurvaAbcDebitosBean();
					listaBeans = quebrarPorClassificacao(listaSimplificada, totalLigacoes, totalValorLigacoes, qtdLigacoesPorClassificacao,
									idGerenciaRegional, nomeGerenciaRegional, idLocalidade, nomeLocalidade, idSetorComercial,
									codigoSetorComercial, nomeSetorComercial, referenciaCobrancaInicial, referenciaCobrancaFinal,
									categoria, idTipoCategoria, idSubCategoria, classificacao, intervaloMesesCortadoSuprimidoInicial,
									intervaloMesesCortadoSuprimidoFinal, situacaoLigacaoAgua, idClassificacaoAnterior);

					Iterator iListaBeans = listaBeans.iterator();
					while(iListaBeans.hasNext()){
						relatorioGerarCurvaAbcDebitosBean = (RelatorioGerarCurvaAbcDebitosBean) iListaBeans.next();

						relatorioBeans.add(relatorioGerarCurvaAbcDebitosBean);
					}
				}
			}

		}

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		// Parâmetros do relatório
		Map parametros = new HashMap();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		RelatorioDataSource ds = new RelatorioDataSource((List) relatorioBeans);

		// exporta o relatório em pdf e retorna o array de bytes
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_GERAR_CURVA_ABC_DEBITOS, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.GERAR_CURVA_ABC_DEBITOS, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;
	}

	/**
	 * Usado para efetivar as quebras do relatorio
	 * [UC0621] Gerar Curva ABC de Debitos
	 * 
	 * @author Ivan Sérgio
	 * @date 25/09/2007
	 */
	public List quebrarPorClassificacao(List listaSimplificada, Integer totalLigacoes, BigDecimal totalValorLigacoes,
					Integer qtdLigacoesPorClassificacao, Integer idGerenciaRegional, String nomeGerenciaRegional, Integer idLocalidade,
					String nomeLocalidade, Integer idSetorComercial, Integer codigoSetorComercial, String nomeSetorComercial,
					String referenciaCobrancaIncial, String referenciaCobrancaFinal, String[] categoria, String idTipoCategoria,
					String idSubCategoria, String classificacao, String intervaloMesesCortadoSuprimidoInicial,
					String intervaloMesesCortadoSuprimidoFinal, String[] situacaoLigacaoAgua, Integer idClassificacao){

		RelatorioGerarCurvaAbcDebitosBean relatorioGerarCurvaAbcDebitosBean = new RelatorioGerarCurvaAbcDebitosBean();

		Fachada fachada = Fachada.getInstancia();

		// Faz o acumulado
		List listaAcumulada = new ArrayList();
		List listaBeans = new ArrayList();

		listaAcumulada = acumularCurvaAbcDebitos(listaSimplificada, totalLigacoes, totalValorLigacoes, qtdLigacoesPorClassificacao,
						idGerenciaRegional, nomeGerenciaRegional, idLocalidade, nomeLocalidade, idSetorComercial, codigoSetorComercial,
						nomeSetorComercial);

		// Recupera a Descricao da Categoria
		String descricaoCategoria = "";
		if(categoria != null){
			if(categoria.length == 1){
				if(!categoria[0].trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){

					FiltroCategoria filtroCategoria = new FiltroCategoria();
					filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, categoria[0]));

					Collection collectionImovelCategoria = fachada.pesquisar(filtroCategoria, Categoria.class.getName());

					if(collectionImovelCategoria != null && !collectionImovelCategoria.isEmpty()){
						Iterator icollectionImovelCategoria = collectionImovelCategoria.iterator();

						Categoria categoria2 = (Categoria) icollectionImovelCategoria.next();

						descricaoCategoria = categoria2.getDescricao();
					}
				}
			}else if(idTipoCategoria.equals("1") && categoria.length == 0){
				descricaoCategoria = "PARTICULAR";
			}else{
				descricaoCategoria = "GERAL";
			}
		}

		// Recupera a Descricao da SubCategoria
		String descricaoSubCategoria = "";
		if(idSubCategoria != null){
			if(idSubCategoria != null){
				if(!idSubCategoria.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){

					FiltroSubCategoria filtroSubCategoria = new FiltroSubCategoria();
					filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idSubCategoria));

					Collection collectionImovelSubcategoria = fachada.pesquisar(filtroSubCategoria, Subcategoria.class.getName());

					if(collectionImovelSubcategoria != null && !collectionImovelSubcategoria.isEmpty()){
						Iterator iCollectionImovelSubcategoria = collectionImovelSubcategoria.iterator();

						Subcategoria subcategoria = (Subcategoria) iCollectionImovelSubcategoria.next();

						descricaoSubCategoria = " - " + subcategoria.getDescricao();
					}
				}
			}
		}

		String situacao = "";
		String intervaloMeses = "";

		if(intervaloMesesCortadoSuprimidoInicial != null & intervaloMesesCortadoSuprimidoFinal != null){

			// Verifica a Situacao de Agua para Cortado/Suprimido
			if(situacaoLigacaoAgua != null){
				if(situacaoLigacaoAgua.length >= 1){
					Integer situacaoAgua = null;

					for(int i = 0; i < situacaoLigacaoAgua.length; i++){
						if(!situacaoLigacaoAgua[i].trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){

							situacaoAgua = new Integer(situacaoLigacaoAgua[i]);

							// Verifica se a situacao é Cortado/Suprimido e
							// se o intervalo de meses foi informado
							if((situacaoAgua.equals(LigacaoAguaSituacao.CORTADO)) || (situacaoAgua.equals(LigacaoAguaSituacao.SUPRIMIDO))){

								FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();

								filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID,
												situacaoAgua));

								Collection collectionLigacaoAguaSituacao = fachada.pesquisar(filtroLigacaoAguaSituacao,
												LigacaoAguaSituacao.class.getName());

								if(collectionLigacaoAguaSituacao != null && !collectionLigacaoAguaSituacao.isEmpty()){

									Iterator iCollectionLigacaoAguaSituacao = collectionLigacaoAguaSituacao.iterator();

									LigacaoAguaSituacao ligacaoAguaSituacao = (LigacaoAguaSituacao) iCollectionLigacaoAguaSituacao.next();

									situacao += ligacaoAguaSituacao.getDescricao() + "/";
								}
							}
						}
					}

					// Retira o ultimo "/" da situacao
					if(situacao != ""){
						situacao = situacao.substring(0, (situacao.length() - 1));

						intervaloMeses = intervaloMesesCortadoSuprimidoInicial + " A " + intervaloMesesCortadoSuprimidoFinal + " MESES";
					}
				}
			}
		}

		// Preenche o Bean com as Faixas já calculadas
		GerarCurvaAbcDebitosHelper helper = null;
		Iterator iListaAcumulada = listaAcumulada.iterator();
		BigDecimal percentual = new BigDecimal(100.00).setScale(2);

		while(iListaAcumulada.hasNext()){
			helper = (GerarCurvaAbcDebitosHelper) iListaAcumulada.next();

			// Passa as informações para o Bean
			relatorioGerarCurvaAbcDebitosBean = new RelatorioGerarCurvaAbcDebitosBean();
			relatorioGerarCurvaAbcDebitosBean.setCategoria(descricaoCategoria);
			relatorioGerarCurvaAbcDebitosBean.setSubCategoria(descricaoSubCategoria);
			relatorioGerarCurvaAbcDebitosBean.setIntervaloMeses(intervaloMeses);
			relatorioGerarCurvaAbcDebitosBean.setSituacaoAgua(situacao);

			relatorioGerarCurvaAbcDebitosBean.setReferenciaCobrancaInicial(referenciaCobrancaIncial);
			relatorioGerarCurvaAbcDebitosBean.setReferenciaCobrancaFinal(referenciaCobrancaFinal);
			relatorioGerarCurvaAbcDebitosBean.setClassificacao(classificacao);
			relatorioGerarCurvaAbcDebitosBean.setIdGerenciaRegional("" + idGerenciaRegional);
			relatorioGerarCurvaAbcDebitosBean.setNomeGerenciaRegional(nomeGerenciaRegional);
			relatorioGerarCurvaAbcDebitosBean.setIdLocalidade("" + idLocalidade);
			relatorioGerarCurvaAbcDebitosBean.setNomeLocalidade(nomeLocalidade);
			relatorioGerarCurvaAbcDebitosBean.setIdSetorComercial("" + idSetorComercial);
			relatorioGerarCurvaAbcDebitosBean.setCodigoSetorComercial("" + codigoSetorComercial);
			relatorioGerarCurvaAbcDebitosBean.setNomeSetorComercial(nomeSetorComercial);

			// Dados das Faixas
			relatorioGerarCurvaAbcDebitosBean.setFaixaInicial(helper.getFaixaInicial());
			relatorioGerarCurvaAbcDebitosBean.setFaixaFinal(helper.getFaixaFinal());
			relatorioGerarCurvaAbcDebitosBean.setIdFaixa(helper.getIdFaixa());
			relatorioGerarCurvaAbcDebitosBean.setQuantidadeLigacoes(helper.getQuantidadeLigacoes());

			// Fecha os acumulados em 100%
			if(iListaAcumulada.hasNext()){
				if(helper.getPercLigacoesAcumulado().compareTo(percentual) > 0){
					relatorioGerarCurvaAbcDebitosBean.setPercLigacoesAcumulado(percentual);
				}else{
					relatorioGerarCurvaAbcDebitosBean.setPercLigacoesAcumulado(helper.getPercLigacoesAcumulado());
				}

				if(helper.getPercValorAcumulado().compareTo(percentual) > 0){
					relatorioGerarCurvaAbcDebitosBean.setPercValorAcumulado(percentual);
				}else{
					relatorioGerarCurvaAbcDebitosBean.setPercValorAcumulado(helper.getPercValorAcumulado());
				}
			}else{
				relatorioGerarCurvaAbcDebitosBean.setPercLigacoesAcumulado(percentual);
				relatorioGerarCurvaAbcDebitosBean.setPercValorAcumulado(percentual);
			}

			relatorioGerarCurvaAbcDebitosBean.setPercLigacoes(helper.getPercLigacoes());
			relatorioGerarCurvaAbcDebitosBean.setLigacoesAcumuladas(helper.getLigacoesAcumuladas());
			relatorioGerarCurvaAbcDebitosBean.setValor(helper.getValor());
			relatorioGerarCurvaAbcDebitosBean.setPercValor(helper.getPercValor());
			relatorioGerarCurvaAbcDebitosBean.setValorAcumulado(helper.getValorAcumulado());
			relatorioGerarCurvaAbcDebitosBean.setValorMedio(helper.getValorMedio());
			relatorioGerarCurvaAbcDebitosBean.setIdClassificacao(idClassificacao.toString());

			listaBeans.add(relatorioGerarCurvaAbcDebitosBean);
		}

		return listaBeans;
	}

	/**
	 * Faz os acumulados das Faixas Valores
	 * [UC0621] Gerar Curva ABC de Debitos
	 * 
	 * @author Ivan Sérgio
	 * @date 24/09/2007
	 */
	public List acumularCurvaAbcDebitos(List listaCurvaAbcDebito, Integer totalLigacoes, BigDecimal totalValorLigacoes,
					Integer qtdLigacoesPorClassificacao, Integer idGerenciaRegional, String nomeGerenciaRegional, Integer idLocalidade,
					String nomeLocalidade, Integer idSetorComercial, Integer codigoSetorComercial, String nomeSetorComercial){

		List<GerarCurvaAbcDebitosHelper> listAcumulada = new ArrayList();

		if(listaCurvaAbcDebito != null & !listaCurvaAbcDebito.isEmpty()){
			GerarCurvaAbcDebitosHelper helper = null;

			Iterator iListaCurvaAbcDebito = listaCurvaAbcDebito.iterator();

			Integer quantidadeLigacoesAnterior = 0;
			BigDecimal percLigacosAcumuladoAnterior = new BigDecimal(0.00);
			BigDecimal valorAnterior = new BigDecimal(0.00);
			BigDecimal percValorAnterior = new BigDecimal(0.00);

			while(iListaCurvaAbcDebito.hasNext()){
				helper = (GerarCurvaAbcDebitosHelper) iListaCurvaAbcDebito.next();

				if(!helper.getQuantidadeLigacoes().equals(0) && !totalLigacoes.equals(0)){
					// Perc (1) - Perc das Ligacoes
					BigDecimal qtdLigacoes = new BigDecimal(helper.getQuantidadeLigacoes());
					BigDecimal porcento = new BigDecimal(100).setScale(2);
					// BigDecimal totalGeralLigacoes = new BigDecimal(totalLigacoes);
					BigDecimal totalGeralLigacoes = new BigDecimal(qtdLigacoesPorClassificacao);

					BigDecimal percLigacoes = qtdLigacoes.multiply(porcento);
					percLigacoes = percLigacoes.divide(totalGeralLigacoes, 2, RoundingMode.HALF_UP);

					// BigDecimal percLigacoes = new BigDecimal(( (helper.getQuantidadeLigacoes() *
					// 100) /
					// totalLigacoes ));
					helper.setPercLigacoes(percLigacoes);

					// Ligacoes Acumuladas
					helper.setLigacoesAcumuladas((helper.getQuantidadeLigacoes() + quantidadeLigacoesAnterior));
					// quantidadeLigacoesAnterior = helper.getQuantidadeLigacoes();
					quantidadeLigacoesAnterior = helper.getLigacoesAcumuladas();

					// Perc Acum (1) - Perc Acumulado por faixa
					helper.setPercLigacoesAcumulado(helper.getPercLigacoes().add(percLigacosAcumuladoAnterior));
					percLigacosAcumuladoAnterior = helper.getPercLigacoesAcumulado();

					// Perc (2) - Perc dos Debitos
					if(!helper.getValor().equals(0) && !totalValorLigacoes.equals(0)){
						BigDecimal percDebitos = (BigDecimal) (helper.getValor().multiply(porcento)).setScale(2);
						percDebitos = percDebitos.divide(totalValorLigacoes, 2, RoundingMode.HALF_UP);

						helper.setPercValor(percDebitos);
					}

					// Valores Acumulados
					helper.setValorAcumulado(helper.getValor().add(valorAnterior));
					valorAnterior = helper.getValorAcumulado();

					// Perc Acum (2) - Somatorio do Perc (2)
					helper.setPercValorAcumulado(helper.getPercValor().add(percValorAnterior));
					percValorAnterior = helper.getPercValorAcumulado();

				}else{
					// Perc (1) - Perc das Ligacoes
					BigDecimal percLigacoes = new BigDecimal(0.00).setScale(2);
					helper.setPercLigacoes(percLigacoes);

					// Ligacoes Acumuladas
					helper.setLigacoesAcumuladas(quantidadeLigacoesAnterior);

					// Perc Acum (1) - Perc Acumulado por faixa
					helper.setPercLigacoesAcumulado(percLigacosAcumuladoAnterior);

					// Perc (2) - Perc dos Debitos
					BigDecimal percDebitos = new BigDecimal(0.00).setScale(2);
					helper.setPercValor(percDebitos);

					// Valores Acumulados
					helper.setValorAcumulado(valorAnterior);

					// Perc Acum (2) - Somatorio do Perc (2)
					helper.setPercValorAcumulado(percValorAnterior);

					// Id da Gerencia Regional
					helper.setIdGerenciaRegional(idGerenciaRegional);
					// Nome da Gerencia Regional
					helper.setNomeGerenciaRegional(nomeGerenciaRegional);

					// Id da Localidade
					helper.setIdLocalidade(idLocalidade);
					// Nome da Localidade
					helper.setNomeLocalidade(nomeLocalidade);

					// Id do Setor Comercial
					helper.setIdSetorComercial(idSetorComercial);
					// Codigo do Setor Comercial
					helper.setCodigoSetorComercial(codigoSetorComercial);
					// Nome do Setor Comercial
					helper.setNomeSetorComercial(nomeSetorComercial);
				}

				/*
				 * System.out.print(helper.getFaixaInicial() + "  |  " +
				 * helper.getFaixaFinal() + "  |  " +
				 * helper.getQuantidadeLigacoes() + "  |  " +
				 * helper.getPercLigacoes() + "  |  " +
				 * helper.getLigacoesAcumuladas() + "  |  " +
				 * helper.getPercLigacoesAcumulado() + "  |  " +
				 * helper.getValor() + "  |  " +
				 * helper.getPercValor() + "  |  " +
				 * helper.getValorAcumulado() + "  |  " +
				 * helper.getPercValorAcumulado() + "  |  " +
				 * helper.getValorMedio());
				 */
				listAcumulada.add(helper);
			}
		}

		return listAcumulada;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		final String classificacao = (String) getParametro("classificacao");
		final String indicadorImovelMedicaoIndividualizada = (String) getParametro("indicadorImovelMedicaoIndividualizada");
		final String indicadorImovelParalizacaoFaturamentoCobranca = (String) getParametro("indicadorImovelParalizacaoFaturamentoCobranca");
		final String[] gerenciaRegional = (String[]) getParametro("gerenciaRegional");
		final String idLocalidadeInicial = (String) getParametro("idLocalidadeInicial");
		final String idLocalidadeFinal = (String) getParametro("idLocalidadeFinal");
		final String idSetorComercialInicial = (String) getParametro("idSetorComercialInicial");
		final String idSetorComercialFinal = (String) getParametro("idSetorComercialFinal");
		final String[] situacaoLigacaoAgua = (String[]) getParametro("situacaoLigacaoAgua");
		final String[] situacaoLigacaoEsgoto = (String[]) getParametro("situacaoLigacaoEsgoto");
		final String intervaloConsumoMinimoFixadoEsgotoInicial = (String) getParametro("intervaloConsumoMinimoFixadoEsgotoInicial");
		final String intervaloConsumoMinimoFixadoEsgotoFinal = (String) getParametro("intervaloConsumoMinimoFixadoEsgotoFinal");
		final String indicadorMedicao = (String) getParametro("indicadorMedicao");
		final String idTipoMedicao = (String) getParametro("idTipoMedicao");
		final String idPerfilImovel = (String) getParametro("idPerfilImovel");
		final String idTipoCategoria = (String) getParametro("idTipoCategoria");
		final String[] categoria = (String[]) getParametro("categoria");
		final String idSubCategoria = (String) getParametro("idSubCategoria");

		Integer retorno = Fachada.getInstancia().gerarCurvaAbcDebitosCount(classificacao, indicadorImovelMedicaoIndividualizada,
						indicadorImovelParalizacaoFaturamentoCobranca, gerenciaRegional, idLocalidadeInicial, idLocalidadeFinal,
						idSetorComercialInicial, idSetorComercialFinal, situacaoLigacaoAgua, situacaoLigacaoEsgoto,
						intervaloConsumoMinimoFixadoEsgotoInicial, intervaloConsumoMinimoFixadoEsgotoFinal, indicadorMedicao,
						idTipoMedicao, idPerfilImovel, idTipoCategoria, categoria, idSubCategoria);

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioGerarCurvaAbcDebitos", this);
	}
}
