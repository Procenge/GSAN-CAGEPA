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

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * classe responsável por criar o Relatório de Contas a receber corrigido por categorias.
 * 
 * @author André Lopes
 */
public class RelatorioContasAReceberCorrigidoCategorias
				extends TarefaRelatorio {

	private static final Logger LOGGER = Logger.getLogger(RelatorioContasAReceberCorrigidoCategorias.class);

	private static final long serialVersionUID = 1L;

	private static final BigDecimal ZERO_REAL = BigDecimal.ZERO;

	private static final String TIPO_QUEBRA_ESTADO = "Estado";

	private static final String TIPO_QUEBRA_UNIDADE_NEGOCIO = "Unidade Negócio";

	private static final String TIPO_QUEBRA_GERENCIA_REGIONAL = "Gerência Regional";

	private static final String TIPO_QUEBRA_LOCALIDADE = "Localidade";

	public RelatorioContasAReceberCorrigidoCategorias(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_CONTAS_A_RECEBER_CORRIGIDO_CATEGORIAS);
	}

	@Deprecated
	public RelatorioContasAReceberCorrigidoCategorias() {

		super(null, ConstantesRelatorios.RELATORIO_CONTAS_A_RECEBER_CORRIGIDO_CATEGORIAS);
	}

	private Collection<RelatorioContasAReceberCorrigidoCategoriasLinhasBean> inicializarBeanRelatorio(int opcaoTotalizador,
					String idUnidadeNegocio, String idGerenciaRegional, String idLocalidade, SistemaParametro sistemaParametro){

		Fachada fachada = Fachada.getInstancia();
		FiltroCategoria filtroCategoria = new FiltroCategoria();
		filtroCategoria.setConsultaSemLimites(true);
		filtroCategoria.setCampoOrderBy(FiltroCategoria.CODIGO);
		filtroCategoria.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection<String> colecaoCategoria = new ArrayList<String>();
		Collection<Categoria> colecaoCategoriaObj = fachada.pesquisar(filtroCategoria, Categoria.class.getName());

		for(Categoria categoria : colecaoCategoriaObj){
			colecaoCategoria.add(categoria.getDescricao());
		}
		List<Integer> agruparPorLocalidade = Arrays.asList(ConstantesSistema.CODIGO_LOCALIDADE, ConstantesSistema.CODIGO_ESTADO_LOCALIDADE,
						ConstantesSistema.CODIGO_GERENCIA_REGIONAL_LOCALIDADE, ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_LOCALIDADE);

		List<Integer> agruparPorGerenciaRegional = Arrays.asList(ConstantesSistema.CODIGO_GERENCIA_REGIONAL,
						ConstantesSistema.CODIGO_ESTADO_GERENCIA_REGIONAL);

		List<Integer> agruparPorUnidadeNegocio = Arrays.asList(ConstantesSistema.CODIGO_UNIDADE_NEGOCIO,
						ConstantesSistema.CODIGO_ESTADO_UNIDADE_NEGOCIO, ConstantesSistema.CODIGO_GERENCIA_REGIONAL_UNIDADE_NEGOCIO);

		Collection<Map<String, RelatorioContasAReceberCorrigidoCategoriasBean>> colecaoDeMapas = new ArrayList<Map<String, RelatorioContasAReceberCorrigidoCategoriasBean>>();

		String subTitulo = "";
		if(agruparPorLocalidade.contains(opcaoTotalizador)){
			LOGGER.info(" Agrupamento por Localidade.");
			if(ConstantesSistema.CODIGO_GERENCIA_REGIONAL_LOCALIDADE == opcaoTotalizador){
				GerenciaRegional gerenciaRegional = fachada.pesquisarObjetoGerenciaRegionalRelatorio(Integer.valueOf(idGerenciaRegional));
				subTitulo = TIPO_QUEBRA_GERENCIA_REGIONAL + ": " + gerenciaRegional.getNome() + ", ";
			}else if(ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_LOCALIDADE == opcaoTotalizador){
				FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
				filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, Integer.valueOf(idUnidadeNegocio)));
				UnidadeNegocio unidadeNegocio = (UnidadeNegocio) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroUnidadeNegocio,
								UnidadeNegocio.class.getName()));
				subTitulo = TIPO_QUEBRA_UNIDADE_NEGOCIO + ": " + unidadeNegocio.getNome() + ", ";
			}

			// Consultar as localidades com os filtros da tela.
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade(FiltroLocalidade.DESCRICAO);
			if(Util.isNaoNuloBrancoZero(idLocalidade)){
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(idLocalidade)));
			}

			if(Util.isNaoNuloBrancoZero(idGerenciaRegional)){
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_GERENCIA, new Integer(idGerenciaRegional)));
			}

			if(Util.isNaoNuloBrancoZero(idUnidadeNegocio)){
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_UNIDADE_NEGOCIO, new Integer(idUnidadeNegocio)));
			}

			Collection<Localidade> localidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			for(Localidade localidade : localidades){
				Map<String, RelatorioContasAReceberCorrigidoCategoriasBean> mapCategoriaBean = inicializarBeanComAgrupamentoPorUnidadeNegOuGerenciaOuLocalidade(
								idUnidadeNegocio, idGerenciaRegional, localidade.getId().toString(), TIPO_QUEBRA_LOCALIDADE,
								localidade.getDescricao(), colecaoCategoria);
				colecaoDeMapas.add(mapCategoriaBean);
			}

		}else if(agruparPorGerenciaRegional.contains(opcaoTotalizador)){
			LOGGER.info("Agrupamento pode Gerencia Regional.");
			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional(FiltroGerenciaRegional.NOME);
			filtroGerenciaRegional.setConsultaSemLimites(true);

			if(Util.isNaoNuloBrancoZero(idGerenciaRegional)){
				filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, new Integer(idGerenciaRegional)));
			}
			Collection<GerenciaRegional> colGerenciaRegionais = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

			for(GerenciaRegional gerenciaRegional : colGerenciaRegionais){
				Map<String, RelatorioContasAReceberCorrigidoCategoriasBean> mapCategoriaBean = inicializarBeanComAgrupamentoPorUnidadeNegOuGerenciaOuLocalidade(
								idUnidadeNegocio, gerenciaRegional.getId().toString(), idLocalidade, TIPO_QUEBRA_GERENCIA_REGIONAL,
								gerenciaRegional.getNomeComId(), colecaoCategoria);
				colecaoDeMapas.add(mapCategoriaBean);
			}

		}else if(agruparPorUnidadeNegocio.contains(opcaoTotalizador)){
			LOGGER.info("Agrupamento por Unidade de Negocio.");
			if(ConstantesSistema.CODIGO_GERENCIA_REGIONAL_UNIDADE_NEGOCIO == opcaoTotalizador){
				GerenciaRegional gerenciaRegional = fachada.pesquisarObjetoGerenciaRegionalRelatorio(Integer.valueOf(idGerenciaRegional));
				subTitulo = TIPO_QUEBRA_GERENCIA_REGIONAL + ": " + gerenciaRegional.getNome() + ", ";
			}

			FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio(FiltroUnidadeNegocio.NOME);
			filtroUnidadeNegocio.setConsultaSemLimites(true);

			if(Util.isNaoNuloBrancoZero(idUnidadeNegocio)){
				filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, new Integer(idUnidadeNegocio)));
			}
			Collection<UnidadeNegocio> colUnidadeNegocio = fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

			for(UnidadeNegocio unidadeNegocio : colUnidadeNegocio){
				Map<String, RelatorioContasAReceberCorrigidoCategoriasBean> mapCategoriaBean = inicializarBeanComAgrupamentoPorUnidadeNegOuGerenciaOuLocalidade(
								unidadeNegocio.getId().toString(), idGerenciaRegional, idLocalidade, TIPO_QUEBRA_UNIDADE_NEGOCIO,
								unidadeNegocio.getNomeComId(), colecaoCategoria);
				colecaoDeMapas.add(mapCategoriaBean);
			}

		}else if(ConstantesSistema.CODIGO_ESTADO == opcaoTotalizador){ // ToDoS
			LOGGER.info("Agrupado por Estado.");
			// Consultar com parâmetros nulos.
			Map<String, RelatorioContasAReceberCorrigidoCategoriasBean> mapCategoriaBean = inicializarBeanComAgrupamentoPorUnidadeNegOuGerenciaOuLocalidade(
							idUnidadeNegocio, idGerenciaRegional, idLocalidade, TIPO_QUEBRA_ESTADO, sistemaParametro.getNomeEstado(),
							colecaoCategoria);
			colecaoDeMapas.add(mapCategoriaBean);

		}

		Collection<RelatorioContasAReceberCorrigidoCategoriasLinhasBean> retorno = new ArrayList<RelatorioContasAReceberCorrigidoCategoriasLinhasBean>();

		for(Map<String, RelatorioContasAReceberCorrigidoCategoriasBean> mapCategoriaBean : colecaoDeMapas){

			// Pode ser ordenado pela categoria.
			for(String categoriaStr : colecaoCategoria){

				RelatorioContasAReceberCorrigidoCategoriasBean bean = mapCategoriaBean.get(categoriaStr);
				if(bean != null){
					RelatorioContasAReceberCorrigidoCategoriasLinhasBean linhaCategoria = new RelatorioContasAReceberCorrigidoCategoriasLinhasBean();
					linhaCategoria.setSubTitulo(subTitulo + bean.getTipoQuebra() + ": " + bean.getValorQuebra());
					linhaCategoria.setCategoria(bean.getCategoria());
					linhaCategoria.setDescricaoDetalhe(" ");
					retorno.add(linhaCategoria);

					LOGGER.info(" --->      Titulo. " + bean.getTipoQuebra() + " - " + bean.getValorQuebra());
					LOGGER.info(" --->      Cat.    " + bean.getCategoria());
					Collection<RelatorioContasAReceberCorrigidoCategoriasDetalhesBean> detalhes = bean.getCategoriaDetalhes();
					for(RelatorioContasAReceberCorrigidoCategoriasDetalhesBean det : detalhes){
						RelatorioContasAReceberCorrigidoCategoriasLinhasBean linha = new RelatorioContasAReceberCorrigidoCategoriasLinhasBean();
						LOGGER.info("Desc.  " + det.getDescricaoDetalhe());
						LOGGER.info("Val.   " + det.getValorConta());
						LOGGER.info("Reaj.  " + det.getValorReajuste());
						LOGGER.info("Total. " + det.getValorTotal());
						LOGGER.info("................................");

						linha.setSubTitulo(subTitulo + bean.getTipoQuebra() + ": " + bean.getValorQuebra());
						linha.setTipoQuebra(bean.getTipoQuebra());
						linha.setValorQuebra(bean.getValorQuebra());
						linha.setCategoria(" ");
						linha.setDescricaoDetalhe(det.getDescricaoDetalhe());
						linha.setValorConta(det.getValorConta());
						linha.setValorReajuste(det.getValorReajuste());
						linha.setValorTotal(det.getValorTotal());
						retorno.add(linha);
					}
					LOGGER.info(" ------- NEW CATEGORY -------- ");
				}
			}
		}

		return retorno;
	}

	private Map<String, RelatorioContasAReceberCorrigidoCategoriasBean> inicializarBeanComAgrupamentoPorUnidadeNegOuGerenciaOuLocalidade(
					String idUnidadeNegocio, String idGerenciaRegional, String idLocalidade, String tipoQuebra, String valorQuebra,
					Collection<String> colecaoCategoria){

		// Categoria, Beans
		Map<String, RelatorioContasAReceberCorrigidoCategoriasBean> mapCategoriaBean = new HashMap<String, RelatorioContasAReceberCorrigidoCategoriasBean>();

		Fachada fachada = Fachada.getInstancia();

		LOGGER.info("Obter Soma Água e Esgoto.");
		Collection<Object[]> aguaEsgotoAVencer = fachada.obterSomatorioAguaEsgoto(idLocalidade, idUnidadeNegocio, idGerenciaRegional);

		// Primeiro água e esgoto e cria os beans dentro do mapa.
		for(String categoria : colecaoCategoria){

			Object[] obj = obterArrayObjetoConsultaPelaCategoria(aguaEsgotoAVencer, categoria);
			RelatorioContasAReceberCorrigidoCategoriasBean bean = criarCategoriasBean(categoria, tipoQuebra, valorQuebra);

			RelatorioContasAReceberCorrigidoCategoriasDetalhesBean detalheAgua = criarDetalheAguaOuEsgoto(obj, true);
			RelatorioContasAReceberCorrigidoCategoriasDetalhesBean detalheEsgoto = criarDetalheAguaOuEsgoto(obj, false);

			bean.getCategoriaDetalhes().add(detalheAgua);
			bean.getCategoriaDetalhes().add(detalheEsgoto);
			mapCategoriaBean.put(categoria, bean);
		}

		// FINAN.COB - Financiado
		LOGGER.info("Calcular Reajuste de Conta. FINAN.COB - Financiado");
		Map<Categoria, BigDecimal> mapReajustesContasFinanciados = fachada.calcularReajustaConta(idLocalidade, idUnidadeNegocio,
						idGerenciaRegional, true);

		Collection<Object[]> debitosCobradosFinanciamento = fachada.obterSomatorioValorDebitosCobradosFinanciamentoOuParcelamento(
						idLocalidade, idUnidadeNegocio, idGerenciaRegional, true);

		addDebitosFinanciadosOuParcelados(mapCategoriaBean, mapReajustesContasFinanciados, debitosCobradosFinanciamento, true,
						colecaoCategoria, tipoQuebra, valorQuebra);

		// PARCE.COB - Parcelado
		LOGGER.info("Calcular Reajuste de Conta. PARCE.COB - Parcelado");
		Map<Categoria, BigDecimal> mapReajustesContasParcelados = fachada.calcularReajustaConta(idLocalidade, idUnidadeNegocio,
						idGerenciaRegional, false);

		Collection<Object[]> debitosCobradosParcelamento = fachada.obterSomatorioValorDebitosCobradosFinanciamentoOuParcelamento(
						idLocalidade, idUnidadeNegocio, idGerenciaRegional, false);

		addDebitosFinanciadosOuParcelados(mapCategoriaBean, mapReajustesContasParcelados, debitosCobradosParcelamento, false,
						colecaoCategoria, tipoQuebra, valorQuebra);

		// PARCELAMENTOS A COBRAR
		LOGGER.info("Calcular PARCELAMENTOS A COBRAR.");
		Collection<Object[]> debitoACobrarParcelamento = fachada.obterSomatorioValorDebitosACobrarFinanciamentoOuParcelamento(idLocalidade,
						idUnidadeNegocio, idGerenciaRegional, false);

		addDebitosACobrarParceladosOuFinanciados(mapCategoriaBean, debitoACobrarParcelamento, colecaoCategoria, true, tipoQuebra,
						valorQuebra);

		// FINANCIAMENTOS A COBRAR
		LOGGER.info("Calcular FINANCIAMENTOS A COBRAR.");
		Collection<Object[]> debitoACobrarFinanciado = fachada.obterSomatorioValorDebitosACobrarFinanciamentoOuParcelamento(idLocalidade,
						idUnidadeNegocio, idGerenciaRegional, true);

		addDebitosACobrarParceladosOuFinanciados(mapCategoriaBean, debitoACobrarFinanciado, colecaoCategoria, false, tipoQuebra,
						valorQuebra);

		return mapCategoriaBean;
	}

	private RelatorioContasAReceberCorrigidoCategoriasDetalhesBean criarDetalheAguaOuEsgoto(Object[] obj, boolean isAgua){

		RelatorioContasAReceberCorrigidoCategoriasDetalhesBean detalheAguaOuEsgoto = new RelatorioContasAReceberCorrigidoCategoriasDetalhesBean();

		BigDecimal valor = ZERO_REAL;

		if(isAgua){
			if(obj != null){
				valor = (BigDecimal) obj[1];
			}
			detalheAguaOuEsgoto.setDescricaoDetalhe("ÁGUA");
		}else{
			if(obj != null){
				valor = (BigDecimal) obj[2];
			}
			detalheAguaOuEsgoto.setDescricaoDetalhe("ESGOTO");
		}

		detalheAguaOuEsgoto.setValorConta(valor);
		detalheAguaOuEsgoto.setValorReajuste(ZERO_REAL);
		detalheAguaOuEsgoto.setValorTotal(valor);
		return detalheAguaOuEsgoto;
	}

	private void addDebitosACobrarParceladosOuFinanciados(Map<String, RelatorioContasAReceberCorrigidoCategoriasBean> mapCategoriaBean,
					Collection<Object[]> debitoACobrarParcelamento, Collection<String> colecaoCategoria, boolean isParcelamento,
					String tipoQuebra, String valorQuebra){

		for(String categoria : colecaoCategoria){

			Object[] obj = obterArrayObjetoConsultaPelaCategoria(debitoACobrarParcelamento, categoria);

			if(mapCategoriaBean.containsKey(categoria)){
				RelatorioContasAReceberCorrigidoCategoriasBean bean = mapCategoriaBean.get(categoria);
				criarDebitosACobrarParceladoOuFinanciado(mapCategoriaBean, obj, categoria, bean, isParcelamento);

			}else{
				RelatorioContasAReceberCorrigidoCategoriasBean bean = criarCategoriasBean(categoria, tipoQuebra, valorQuebra);
				criarDebitosACobrarParceladoOuFinanciado(mapCategoriaBean, obj, categoria, bean, isParcelamento);
			}

		}
	}

	private void criarDebitosACobrarParceladoOuFinanciado(Map<String, RelatorioContasAReceberCorrigidoCategoriasBean> mapCategoriaBean,
					Object[] obj, String categoria, RelatorioContasAReceberCorrigidoCategoriasBean bean, boolean isParcelamento){

		RelatorioContasAReceberCorrigidoCategoriasDetalhesBean detalhe = new RelatorioContasAReceberCorrigidoCategoriasDetalhesBean();
		if(isParcelamento){
			detalhe.setDescricaoDetalhe("PARCELAMETO");
		}else{
			detalhe.setDescricaoDetalhe("FINANCIAMENTO");
		}
		BigDecimal valorConta = ZERO_REAL;
		if(obj != null){
			valorConta = (BigDecimal) obj[1];
		}
		detalhe.setValorConta(valorConta);
		detalhe.setValorReajuste(ZERO_REAL);
		detalhe.setValorTotal(valorConta);
		bean.getCategoriaDetalhes().add(detalhe);
		mapCategoriaBean.put(categoria, bean);
	}

	private void addDebitosFinanciadosOuParcelados(Map<String, RelatorioContasAReceberCorrigidoCategoriasBean> mapCategoriaBean,
					Map<Categoria, BigDecimal> mapReajustesContas, Collection<Object[]> debitosCobradosFinanciamento,
					boolean isFinanciamento, Collection<String> colecaoCategoria, String tipoQuebra, String valorQuebra){

		for(String categoria : colecaoCategoria){

			Object[] obj = obterArrayObjetoConsultaPelaCategoria(debitosCobradosFinanciamento, categoria);

			BigDecimal valorReajuste = obterReajusteDoMapaDeReajustados(mapReajustesContas, categoria);

			if(mapCategoriaBean.containsKey(categoria)){
				RelatorioContasAReceberCorrigidoCategoriasBean bean = mapCategoriaBean.get(categoria);
				addDetalheNoBeans(mapCategoriaBean, obj, categoria, valorReajuste, bean, isFinanciamento);
			}else{
				RelatorioContasAReceberCorrigidoCategoriasBean bean = criarCategoriasBean(categoria, tipoQuebra, valorQuebra);
				addDetalheNoBeans(mapCategoriaBean, obj, categoria, valorReajuste, bean, isFinanciamento);
			}

		}

	}

	private Object[] obterArrayObjetoConsultaPelaCategoria(Collection<Object[]> colecaoObjects, String categoria){

		Object[] obj = null;
		for(Object[] objFor : colecaoObjects){
			if(categoria.equals((String) objFor[0])){
				obj = objFor;
				break;
			}
		}
		return obj;
	}

	private void addDetalheNoBeans(Map<String, RelatorioContasAReceberCorrigidoCategoriasBean> mapCategoriaBean, Object[] obj,
					String categoria, BigDecimal valorReajuste, RelatorioContasAReceberCorrigidoCategoriasBean bean, boolean isFinanciamento){

		RelatorioContasAReceberCorrigidoCategoriasDetalhesBean detalhe = new RelatorioContasAReceberCorrigidoCategoriasDetalhesBean();
		if(isFinanciamento){
			detalhe.setDescricaoDetalhe("FINAN.COB");
		}else{
			detalhe.setDescricaoDetalhe("PARCE.COB");
		}
		BigDecimal valorConta = ZERO_REAL;
		if(obj != null){
			valorConta = (BigDecimal) obj[1];
		}
		detalhe.setValorConta(valorConta);
		detalhe.setValorReajuste(valorReajuste);
		BigDecimal valorTotalBD = valorConta.add(valorReajuste);
		detalhe.setValorTotal(valorTotalBD);
		bean.getCategoriaDetalhes().add(detalhe);
		mapCategoriaBean.put(categoria, bean);
	}

	private BigDecimal obterReajusteDoMapaDeReajustados(Map<Categoria, BigDecimal> mapReajustesContas, String categoria){

		Set<Categoria> caregoriasKeys = mapReajustesContas.keySet();
		BigDecimal valorReajuste = ZERO_REAL;
		for(Categoria c : caregoriasKeys){
			if(c.getDescricao().equals(categoria)){
				valorReajuste = mapReajustesContas.get(c);
				break;
			}
		}
		return valorReajuste;
	}

	private RelatorioContasAReceberCorrigidoCategoriasBean criarCategoriasBean(String categoria, String tipoQuebra, String valorQuebra){

		RelatorioContasAReceberCorrigidoCategoriasBean bean = new RelatorioContasAReceberCorrigidoCategoriasBean();
		bean.setCategoriaDetalhes(new ArrayList<RelatorioContasAReceberCorrigidoCategoriasDetalhesBean>());
		bean.setTipoQuebra(tipoQuebra);
		bean.setValorQuebra(valorQuebra);
		bean.setCategoria(categoria); // Categoria
		return bean;
	}

	/**
	 * Método que executa a tarefa
	 * 
	 * @return Object
	 */
	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		int opcaoTotalizador = (Integer) getParametro("opcaoTotalizador");
		String idUnidadeNegocio = (String) getParametro("unidadeNegocio");
		String idGerenciaRegional = (String) getParametro("gerenciaRegional");
		String idLocalidade = (String) getParametro("localidade");

		// valor de retorno
		byte[] retorno = null;

		Fachada fachada = Fachada.getInstancia();

		// Parâmetros do relatório
		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("anoMesReferencia", "" + Util.retornaDescricaoAnoMes4Digitos("" + sistemaParametro.getAnoMesFaturamento()));
		parametros.put("P_NM_ESTADO", "" + sistemaParametro.getNomeEstado());

		Collection<RelatorioContasAReceberCorrigidoCategoriasLinhasBean> colecaoBean = this.inicializarBeanRelatorio(opcaoTotalizador,
						idUnidadeNegocio, idGerenciaRegional, idLocalidade, sistemaParametro);

		if(colecaoBean == null || colecaoBean.isEmpty()){
			// Não existem dados para a exibição do relatório.
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}

		RelatorioDataSource ds = new RelatorioDataSource((List) colecaoBean);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_CONTAS_A_RECEBER_CORRIGIDO_CATEGORIAS, parametros, ds,
						tipoFormatoRelatorio);

		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_CONTAS_A_RECEBER_CORRIGIDO, idFuncionalidadeIniciada,
							"Relatório de contas a receber corrigido.");
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		// retorna o relatório gerado
		return retorno;

	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 2; // No constantes_execucao_relatorios.pro está 1! sempre vai para batch

		return retorno;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioContasAReceberCorrigidoCategorias", this);
	}
}