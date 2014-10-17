
package gcom.relatorio.financeiro;

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.CategoriaTipo;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.financeiro.ContaAReceberContabil;
import gcom.financeiro.lancamento.LancamentoItem;
import gcom.financeiro.lancamento.LancamentoTipo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.collections.map.MultiKeyMap;

public class RelatorioSaldoContasAReceberContabil
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public static final String OPCAO_TOTALIZACAO = "opcaoTotalizacao";

	public static final String MES_ANO_REFERENCIA = "mesAno";

	public static final String ANO_MES_REFERENCIA = "anoMes";

	public static final String LOCALIDADE = "localidade";

	public static final String GERENCIA_REGIONAL = "gerenciaRegional";

	public static final String UNIDADE_NEGOCIO = "unidadeNegocio";

	public static final String TOTALIZACAO_POR_ESTADO = "estado";

	public static final String TOTALIZACAO_ESTADO_POR_GERENCIA_REGIONAL = "estadoGerencia";

	public static final String TOTALIZACAO_ESTADO_POR_UNIDADE_NEGOCIO = "estadoUnidadeNegocio";

	public static final String TOTALIZACAO_ESTADO_POR_LOCALIDADE = "estadoLocalidade";

	public static final String TOTALIZACAO_POR_GERENCIA_REGIONAL = "gerenciaRegional";

	public static final String TOTALIZACAO_GERENCIA_REGIONAL_POR_UNIDADE_NEGOCIO = "gerenciaRegionalUnidadeNegocio";

	public static final String TOTALIZACAO_GERENCIAL_REGIONAL_POR_LOCALIDADE = "gerenciaRegionalLocalidade";

	public static final String TOTALIZACAO_UNIDADE_NEGOCIO_POR_LOCALIDADE = "unidadeNegocioLocalidade";

	public static final String TOTALIZACAO_POR_UNIDADE_NEGOCIO = "unidadeNegocio";

	public static final String TOTALIZACAO_POR_LOCALIDADE = "localidade";

	public static final String TIPO_FORMATO_RELATORIO = "tipoFormatoRelatorio";

	public static final String BEANS_RELATORIO = "beans";

	public static final String PARAMETROS_RELATORIO = "parametros";

	public static final String VALOR_ITEM_LANCAMENTO_CAT_PUBLICA = "vlItemLancamentoCatPublica";

	public static final String VALOR_ITEM_LANCAMENTO_CAT_PARTICULAR = "vlItemLancamentoCatParticular";

	public static final String VALOR_ITEM_LANCAMENTO = "vlItemLancamento";

	@Deprecated
	public RelatorioSaldoContasAReceberContabil() {

		super(null, "");

	}

	public RelatorioSaldoContasAReceberContabil(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_SALDO_CONTAS_A_RECEBER_CONTABIL);

	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.TarefaRelatorio#calcularTotalRegistrosRelatorio()
	 */
	public int calcularTotalRegistrosRelatorio(){

		return Fachada.getInstancia().calcularQtdaRegistrosRelatorioSaldoContasAReceberContabil(this.obterFiltroRelatorio());

	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#agendarTarefaBatch()
	 */
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioSaldoContasAReceberContabil", this);

	}

	private Map<String, Object> obterFiltroRelatorio(){

		Map<String, Object> filtro = new HashMap<String, Object>();
		filtro.put(OPCAO_TOTALIZACAO, (String) this.getParametro(OPCAO_TOTALIZACAO));
		filtro.put(ANO_MES_REFERENCIA, Util.formatarMesAnoParaAnoMes((Integer) this.getParametro(MES_ANO_REFERENCIA)));
		filtro.put(LOCALIDADE, (Integer) this.getParametro(LOCALIDADE));
		filtro.put(GERENCIA_REGIONAL, (Integer) this.getParametro(GERENCIA_REGIONAL));
		filtro.put(UNIDADE_NEGOCIO, (Integer) this.getParametro(UNIDADE_NEGOCIO));

		return filtro;

	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#executar()
	 */
	public Object executar() throws TarefaException{

		Map<String, Object> filtro = this.obterFiltroRelatorio();

		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();

		Map<String, Object> parametros = new HashMap<String, Object>();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put(OPCAO_TOTALIZACAO, this.obterDescricaoTotalizacao(filtro));

		parametros.put(MES_ANO_REFERENCIA, Util.formatarAnoMesParaMesAno((Integer) filtro.get(ANO_MES_REFERENCIA)));

		byte[] retorno = this.obterRelatorioSaldoContasAReceberContabil(filtro, parametros, (Integer) this
						.getParametro(TIPO_FORMATO_RELATORIO));

		try{

			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_SALDO_CONTAS_A_RECEBER_CONTABIL, this.getIdFuncionalidadeIniciada(),
							null);

		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno;

	}

	private String obterDescricaoTotalizacao(Map<String, Object> filtro){

		String retorno = "";

		String opcaoTotalizacao = (String) filtro.get(RelatorioSaldoContasAReceberContabil.OPCAO_TOTALIZACAO);

		if(opcaoTotalizacao.equals(TOTALIZACAO_POR_ESTADO)){

			// 4.1.1. Estado (total do estado);
			retorno = "ESTADO";

		}else if(opcaoTotalizacao.equals(TOTALIZACAO_ESTADO_POR_GERENCIA_REGIONAL)){

			// 4.1.2. Estado por Gerência Regional (total de cada gerência e do estado);
			retorno = "ESTADO POR GERÊNCIA REGIONAL";

		}else if(opcaoTotalizacao.equals(TOTALIZACAO_ESTADO_POR_UNIDADE_NEGOCIO)){

			// 4.1.3. Estado por Unidade de Negócio (total de cada unidade de negócio, gerência e do
			// estado);
			retorno = "ESTADO POR UNIDADE DE NEGÓCIO";

		}else if(opcaoTotalizacao.equals(TOTALIZACAO_ESTADO_POR_LOCALIDADE)){

			// 4.1.4. Estado por Localidade (total de cada localidade, unidade de negócio, gerência
			// e do
			// estado);
			retorno = "ESTADO POR LOCALIDADE";

		}else if(opcaoTotalizacao.equals(TOTALIZACAO_POR_GERENCIA_REGIONAL)){

			// 4.1.5. Gerência Regional (total da gerência);
			retorno = "GERÊNCIA REGIONAL";

		}else if(opcaoTotalizacao.equals(TOTALIZACAO_POR_UNIDADE_NEGOCIO)){

			// 4.1.6. Unidade de Negócio (total de cada unidade de negócio e total da gerência);
			retorno = "UNIDADE DE NEGÓCIO";

		}else if(opcaoTotalizacao.equals(TOTALIZACAO_GERENCIA_REGIONAL_POR_UNIDADE_NEGOCIO)){

			// 4.1.8. Unidade de Negócio (total da unidade de negócio);
			retorno = "GERÊNCIA REGIONAL POR UNIDADE DE NEGOCIO";

		}else if(opcaoTotalizacao.equals(TOTALIZACAO_GERENCIAL_REGIONAL_POR_LOCALIDADE)){

			// 4.1.7. Gerência Regional por Localidade (total de cada localidade e da gerência);
			retorno = "GERÊNCIA REGIONAL POR LOCALIDADE";

		}else if(opcaoTotalizacao.equals(TOTALIZACAO_UNIDADE_NEGOCIO_POR_LOCALIDADE)){

			retorno = "UNIDADE DE NEGÓCIO POR LOCALIDADE";

		}else if(opcaoTotalizacao.equals(TOTALIZACAO_POR_LOCALIDADE)){

			// 4.1.9. Localidade (total da localidade).
			retorno = "LOCALIDADE";

		}

		return retorno;

	}

	private byte[] obterRelatorioSaldoContasAReceberContabil(Map<String, Object> filtro, Map<String, Object> parametros,
					Integer tipoFormatoRelatorio){

		// rocket engineering
		String arquivoJasper = null;
		List<ContaAReceberContabil> listContaAReceberContabil = Fachada.getInstancia().obterDadosRelatorioSaldoContasAReceberContabil(
						filtro);

		RelatorioSaldoContasAReceberContabilBean bean = null;
		MultiKeyMap agrupamento = null;

		String opcaoTotalizacao = (String) filtro.get(RelatorioSaldoContasAReceberContabil.OPCAO_TOTALIZACAO);

		if(opcaoTotalizacao.equals(TOTALIZACAO_POR_ESTADO)){

			// 4.1.1. Estado (total do estado);
			agrupamento = this.agruparDadosRelSaldoContasAReceberContabilPorEstado(listContaAReceberContabil);
			bean = this.obterRelatorioPorEstado(agrupamento);
			arquivoJasper = ConstantesRelatorios.RELATORIO_SALDO_CONTAS_A_RECEBER_CONTABIL_MODELO_1;

		}else if(opcaoTotalizacao.equals(TOTALIZACAO_ESTADO_POR_GERENCIA_REGIONAL)){

			// 4.1.2. Estado por Gerência Regional (total de cada gerência e do estado);
			agrupamento = this.agruparDadosRelSaldoContasAReceberContabilPorEstadoGerenciaRegional(listContaAReceberContabil);
			bean = this.obterRelatorioPorEstadoGerenciaRegional(agrupamento);
			arquivoJasper = ConstantesRelatorios.RELATORIO_SALDO_CONTAS_A_RECEBER_CONTABIL_MODELO_2;

		}else if(opcaoTotalizacao.equals(TOTALIZACAO_ESTADO_POR_UNIDADE_NEGOCIO)){

			// 4.1.3. Estado por Unidade de Negócio (total de cada unidade de negócio, gerência e do
			// estado);
			agrupamento = this.agruparDadosRelSaldoContasAReceberContabilPorEstadoUnidadeNegocio(listContaAReceberContabil);
			bean = this.obterRelatorioPorEstadoUnidadeNegocio(agrupamento);
			arquivoJasper = ConstantesRelatorios.RELATORIO_SALDO_CONTAS_A_RECEBER_CONTABIL_MODELO_3;

		}else if(opcaoTotalizacao.equals(TOTALIZACAO_ESTADO_POR_LOCALIDADE)){

			// 4.1.4. Estado por Localidade (total de cada localidade, unidade de negócio, gerência
			// e do
			// estado);
			agrupamento = this.agruparDadosRelSaldoContasAReceberContabilPorEstadoLocalidade(listContaAReceberContabil);
			bean = this.obterRelatorioPorEstadoLocalidade(agrupamento);
			arquivoJasper = ConstantesRelatorios.RELATORIO_SALDO_CONTAS_A_RECEBER_CONTABIL_MODELO_4;

		}else if(opcaoTotalizacao.equals(TOTALIZACAO_POR_GERENCIA_REGIONAL)){

			// 4.1.5. Gerência Regional (total da gerência);
			agrupamento = this.agruparDadosRelSaldoContasAReceberContabilPorGerenciaRegional(listContaAReceberContabil);
			bean = this.obterRelatorioPorGerenciaRegional(agrupamento);
			arquivoJasper = ConstantesRelatorios.RELATORIO_SALDO_CONTAS_A_RECEBER_CONTABIL_MODELO_1;

		}else if(opcaoTotalizacao.equals(TOTALIZACAO_POR_UNIDADE_NEGOCIO)){

			// 4.1.6. Unidade de Negócio (total de cada unidade de negócio e total da gerência);
			agrupamento = this.agruparDadosRelSaldoContasAReceberContabilPorUnidadeNegocio(listContaAReceberContabil);
			bean = this.obterRelatorioPorUnidadeNegocio(agrupamento);
			arquivoJasper = ConstantesRelatorios.RELATORIO_SALDO_CONTAS_A_RECEBER_CONTABIL_MODELO_1;

		}else if(opcaoTotalizacao.equals(TOTALIZACAO_GERENCIA_REGIONAL_POR_UNIDADE_NEGOCIO)){

			// 4.1.8. Unidade de Negócio (total da unidade de negócio);
			agrupamento = this.agruparDadosRelSaldoContasAReceberContabilPorGerenciaRegionalUnidadeNegocio(listContaAReceberContabil);
			bean = this.obterRelatorioPorGerenciaRegionalUnidadeNegocio(agrupamento);
			arquivoJasper = ConstantesRelatorios.RELATORIO_SALDO_CONTAS_A_RECEBER_CONTABIL_MODELO_2;

		}else if(opcaoTotalizacao.equals(TOTALIZACAO_GERENCIAL_REGIONAL_POR_LOCALIDADE)){

			// 4.1.7. Gerência Regional por Localidade (total de cada localidade e da gerência);
			agrupamento = this.agruparDadosRelSaldoContasAReceberContabilPorGerenciaRegionalLocalidade(listContaAReceberContabil);
			bean = this.obterRelatorioPorGerenciaRegionalLocalidade(agrupamento);
			arquivoJasper = ConstantesRelatorios.RELATORIO_SALDO_CONTAS_A_RECEBER_CONTABIL_MODELO_2;

		}else if(opcaoTotalizacao.equals(TOTALIZACAO_UNIDADE_NEGOCIO_POR_LOCALIDADE)){

			agrupamento = this.agruparDadosRelSaldoContasAReceberContabilPorUnidadeNegocioLocalidade(listContaAReceberContabil);
			bean = this.obterRelatorioPorUnidadeNegocioLocalidade(agrupamento);
			arquivoJasper = ConstantesRelatorios.RELATORIO_SALDO_CONTAS_A_RECEBER_CONTABIL_MODELO_2;

		}else if(opcaoTotalizacao.equals(TOTALIZACAO_POR_LOCALIDADE)){

			// 4.1.9. Localidade (total da localidade).
			agrupamento = this.agruparDadosRelSaldoContasAReceberContabilPorLocalidade(listContaAReceberContabil);
			bean = this.obterRelatorioPorLocalidade(agrupamento);
			arquivoJasper = ConstantesRelatorios.RELATORIO_SALDO_CONTAS_A_RECEBER_CONTABIL_MODELO_1;

		}

		List<RelatorioSaldoContasAReceberContabilBean> beans = new ArrayList<RelatorioSaldoContasAReceberContabilBean>();
		beans.add(bean);

		return this.gerarRelatorio(arquivoJasper, parametros, new RelatorioDataSource(beans), (Integer) this
						.getParametro(TIPO_FORMATO_RELATORIO));

	}

	private List<RelatorioSaldoContasAReceberContabilDetalheBean> obterDetalhesRelatorio(List<ContaAReceberContabil> lista){

		List<RelatorioSaldoContasAReceberContabilDetalheBean> detalhes = new ArrayList<RelatorioSaldoContasAReceberContabilDetalheBean>();
		MultiKeyMap agrupamentoSeqLanc = this.agruparDetalhesRelatorioPorSeqLancamento(lista);

		for(Object obj : agrupamentoSeqLanc.keySet()){

			MultiKey keySeqLanc = (MultiKey) obj;

			Integer numeroSequenciaTipoLancamento = (Integer) keySeqLanc.getKey(0);
			String descSequenciaTipoLancamento = (String) keySeqLanc.getKey(1);

			MultiKeyMap agrupamentoItemLancamento = this
							.agruparDetalhesRelatorioPorItemLancamento((List<ContaAReceberContabil>) agrupamentoSeqLanc.get(keySeqLanc));

			RelatorioSaldoContasAReceberContabilDetalheBean detalheTipoLanc = new RelatorioSaldoContasAReceberContabilDetalheBean(
							numeroSequenciaTipoLancamento, descSequenciaTipoLancamento);

			for(Object objDetalheItemLanc : agrupamentoItemLancamento.keySet()){

				MultiKey keyItemLanc = (MultiKey) objDetalheItemLanc;

				Integer idItemLancamento = (Integer) keyItemLanc.getKey(0);
				String descItemLancamento = (String) keyItemLanc.getKey(1);

				Map<String, BigDecimal> valoresItemLancamento = this
								.obterValorItemLancamentoCatParticularPublica((List<ContaAReceberContabil>) agrupamentoItemLancamento
												.get(keyItemLanc));

				RelatorioSaldoContasAReceberContabilDetalheBean detalheItemLanc = new RelatorioSaldoContasAReceberContabilDetalheBean(
								idItemLancamento, descItemLancamento, valoresItemLancamento.get(VALOR_ITEM_LANCAMENTO_CAT_PUBLICA),
								valoresItemLancamento.get(VALOR_ITEM_LANCAMENTO_CAT_PARTICULAR), valoresItemLancamento
												.get(VALOR_ITEM_LANCAMENTO));

				detalheTipoLanc.addDetalhe(detalheItemLanc);

			}

			detalhes.add(detalheTipoLanc);

		}

		return detalhes;

	}

	private Map<String, BigDecimal> obterValorItemLancamentoCatParticularPublica(List<ContaAReceberContabil> lista){

		Map<String, BigDecimal> retorno = new HashMap<String, BigDecimal>();
		BigDecimal vlTotalCatPublica = BigDecimal.ZERO;
		BigDecimal vlTotalCatParticular = BigDecimal.ZERO;

		for(ContaAReceberContabil contaAReceberContabil : lista){

			LancamentoTipo lancamentoTipo = contaAReceberContabil.getLancamentoTipo();
			Categoria categoria = contaAReceberContabil.getCategoria();

			if(lancamentoTipo != null && categoria != null && categoria.getCategoriaTipo() != null){

				if(categoria.getCategoriaTipo().getId().equals(CategoriaTipo.PARTICULAR)){

					vlTotalCatParticular = vlTotalCatParticular.add(contaAReceberContabil.getValorItemLancamento());

				}else if(categoria.getCategoriaTipo().getId().equals(CategoriaTipo.PUBLICO)){

					vlTotalCatPublica = vlTotalCatPublica.add(contaAReceberContabil.getValorItemLancamento());

				}

			}

		}

		retorno.put(VALOR_ITEM_LANCAMENTO_CAT_PUBLICA, vlTotalCatPublica);
		retorno.put(VALOR_ITEM_LANCAMENTO_CAT_PARTICULAR, vlTotalCatParticular);
		retorno.put(VALOR_ITEM_LANCAMENTO, vlTotalCatPublica.add(vlTotalCatParticular));

		return retorno;

	}

	private RelatorioSaldoContasAReceberContabilBean obterRelatorioPorUnidadeNegocioLocalidade(MultiKeyMap agrupamento){

		Integer idUnidadeNegocio = null;
		String descUnidadeNegocio = null;

		RelatorioSaldoContasAReceberContabilBean beanUnidadeNegocio = new RelatorioSaldoContasAReceberContabilBean();

		for(Object obj : agrupamento.keySet()){

			MultiKey key = (MultiKey) obj;

			idUnidadeNegocio = (Integer) key.getKey(0);
			descUnidadeNegocio = (String) key.getKey(1);

			Integer idLocalidade = (Integer) key.getKey(2);
			String descLocalidade = (String) key.getKey(3);

			List<ContaAReceberContabil> list = (List<ContaAReceberContabil>) agrupamento.get(key);

			RelatorioSaldoContasAReceberContabilBean beanLocalidade = new RelatorioSaldoContasAReceberContabilBean();
			beanLocalidade.setIdTotalizador(idLocalidade);
			beanLocalidade.setDescTotalizador(descLocalidade);
			beanLocalidade.addDetalhes(this.obterDetalhesRelatorio(list));

			beanUnidadeNegocio.addDado(beanLocalidade);

		}

		beanUnidadeNegocio.setIdTotalizador(idUnidadeNegocio);
		beanUnidadeNegocio.setDescTotalizador(descUnidadeNegocio);

		return beanUnidadeNegocio;

	}

	private RelatorioSaldoContasAReceberContabilBean obterRelatorioPorEstadoLocalidade(MultiKeyMap agrupamento){

		MultiKeyMap dadosGerencia = MultiKeyMap.decorate(new LinkedMap());
		MultiKeyMap dadosUnidade = MultiKeyMap.decorate(new LinkedMap());

		List<RelatorioSaldoContasAReceberContabilBean> listBean = new ArrayList<RelatorioSaldoContasAReceberContabilBean>();

		for(Object obj : agrupamento.keySet()){

			MultiKey key = (MultiKey) obj;

			List<ContaAReceberContabil> list = (List<ContaAReceberContabil>) agrupamento.get(key);

			ContaAReceberContabil contaAReceberContabil = list.iterator().next();

			Integer idGerencia = (Integer) key.getKey(0);
			String descGerencia = contaAReceberContabil.getGerenciaRegional().getNome();
			Integer idUnidade = (Integer) key.getKey(1);
			String descUnidade = contaAReceberContabil.getUnidadeNegocio().getNome();
			Integer idLocalidade = (Integer) key.getKey(2);
			String descLocalidade = contaAReceberContabil.getLocalidade().getDescricao();

			RelatorioSaldoContasAReceberContabilBean bean = new RelatorioSaldoContasAReceberContabilBean();
			bean.setIdTotalizador(idLocalidade);
			bean.setDescTotalizador(descLocalidade);
			bean.setIdTotalizadorSecundario(idUnidade);
			bean.setDescTotalizadorSecundario(descUnidade);
			bean.setIdTotalizadorTerciario(idGerencia);
			bean.setDescTotalizadorTerciario(descGerencia);
			bean.addDetalhes(this.obterDetalhesRelatorio(list));

			listBean.add(bean);

		}

		for(RelatorioSaldoContasAReceberContabilBean bean : listBean){

			if(dadosUnidade.containsKey(bean.getIdTotalizadorTerciario(), bean.getDescTotalizadorTerciario(), bean
							.getIdTotalizadorSecundario(), bean.getDescTotalizadorSecundario())){

				((List<RelatorioSaldoContasAReceberContabilBean>) dadosUnidade.get(bean.getIdTotalizadorTerciario(), bean
								.getDescTotalizadorTerciario(), bean.getIdTotalizadorSecundario(), bean.getDescTotalizadorSecundario()))
								.add(bean);

			}else{

				List<RelatorioSaldoContasAReceberContabilBean> listBeanSec = new ArrayList<RelatorioSaldoContasAReceberContabilBean>();
				listBeanSec.add(bean);

				dadosUnidade.put(bean.getIdTotalizadorTerciario(), bean.getDescTotalizadorTerciario(), bean.getIdTotalizadorSecundario(),
								bean.getDescTotalizadorSecundario(), listBeanSec);

			}

		}

		RelatorioSaldoContasAReceberContabilBean beanTerc = null;

		for(Object obj : dadosUnidade.keySet()){

			MultiKey key = (MultiKey) obj;

			Integer idGerencia = (Integer) key.getKey(0);
			String descGerencia = (String) key.getKey(1);
			Integer idUnidade = (Integer) key.getKey(2);
			String descUnidade = (String) key.getKey(3);

			RelatorioSaldoContasAReceberContabilBean beanSec = new RelatorioSaldoContasAReceberContabilBean();
			beanSec.setIdTotalizador(idUnidade);
			beanSec.setDescTotalizador(descUnidade);
			beanSec.setIdTotalizadorSecundario(idGerencia);
			beanSec.setDescTotalizadorSecundario(descGerencia);
			beanSec.addDados((List<RelatorioSaldoContasAReceberContabilBean>) dadosUnidade.get(key));

			beanTerc = this.obterInstanciaRelatorioSaldoContasAReceberContabilBean(beanTerc, idGerencia, descGerencia);
			beanTerc.addDado(beanSec);

			if(dadosGerencia.containsKey(idGerencia, descGerencia)){

				((List<RelatorioSaldoContasAReceberContabilBean>) dadosGerencia.get(idGerencia, descGerencia)).add(beanTerc);

			}else{

				List<RelatorioSaldoContasAReceberContabilBean> list = new ArrayList<RelatorioSaldoContasAReceberContabilBean>();
				list.add(beanSec);
				dadosGerencia.put(idGerencia, descGerencia, list);

			}

		}

		RelatorioSaldoContasAReceberContabilBean beanEstado = new RelatorioSaldoContasAReceberContabilBean();
		beanEstado.setDescTotalizador("ESTADO");

		for(Object obj : dadosGerencia.keySet()){

			MultiKey key = (MultiKey) obj;

			Integer idGerencia = (Integer) key.getKey(0);
			String descGerencia = (String) key.getKey(1);

			RelatorioSaldoContasAReceberContabilBean beanGerencia = new RelatorioSaldoContasAReceberContabilBean();
			beanGerencia.setIdTotalizador(idGerencia);
			beanGerencia.setDescTotalizador(descGerencia);

			List<RelatorioSaldoContasAReceberContabilBean> listaBeanUnidadeNegocio = (List<RelatorioSaldoContasAReceberContabilBean>) dadosGerencia
							.get(key);

			beanGerencia.addDados(listaBeanUnidadeNegocio);

			beanEstado.addDado(beanGerencia);

		}

		return beanEstado;

	}

	private RelatorioSaldoContasAReceberContabilBean obterRelatorioPorEstadoUnidadeNegocio(MultiKeyMap agrupamento){

		List<RelatorioSaldoContasAReceberContabilBean> dados = new ArrayList<RelatorioSaldoContasAReceberContabilBean>();
		RelatorioSaldoContasAReceberContabilBean beanGerenciaRegional = null;

		for(Object obj : agrupamento.keySet()){

			MultiKey key = (MultiKey) obj;

			Integer idUnidadeNegocio = (Integer) key.getKey(2);
			String descUnidadeNegocio = (String) key.getKey(3);

			List<ContaAReceberContabil> list = (List<ContaAReceberContabil>) agrupamento.get(key);

			RelatorioSaldoContasAReceberContabilBean beanUnidadeNegocio = new RelatorioSaldoContasAReceberContabilBean();
			beanUnidadeNegocio.setIdTotalizador(idUnidadeNegocio);
			beanUnidadeNegocio.setDescTotalizador(descUnidadeNegocio);
			beanUnidadeNegocio.addDetalhes(this.obterDetalhesRelatorio(list));

			beanGerenciaRegional = this.obterInstanciaRelatorioSaldoContasAReceberContabilBean(beanGerenciaRegional, (Integer) key
							.getKey(0), (String) key.getKey(1));
			beanGerenciaRegional.addDado(beanUnidadeNegocio);

			if(!dados.contains(beanGerenciaRegional)){

				dados.add(beanGerenciaRegional);

			}

		}

		RelatorioSaldoContasAReceberContabilBean beanEstado = new RelatorioSaldoContasAReceberContabilBean();
		beanEstado.setDescTotalizador("ESTADO");
		beanEstado.addDados(dados);

		return beanEstado;

	}

	private RelatorioSaldoContasAReceberContabilBean obterRelatorioPorGerenciaRegionalLocalidade(MultiKeyMap agrupamento){

		String descTotalizadorGerenciaRegional = null;
		Integer idTotalizadoGerenciaRegional = null;

		RelatorioSaldoContasAReceberContabilBean beanGerenciaRegional = new RelatorioSaldoContasAReceberContabilBean();

		for(Object obj : agrupamento.keySet()){

			MultiKey key = (MultiKey) obj;

			idTotalizadoGerenciaRegional = (Integer) key.getKey(0);
			descTotalizadorGerenciaRegional = (String) key.getKey(1);

			Integer idLocalidade = (Integer) key.getKey(2);
			String descLocalidade = (String) key.getKey(3);

			RelatorioSaldoContasAReceberContabilBean beanLocalidade = new RelatorioSaldoContasAReceberContabilBean();
			beanLocalidade.setIdTotalizador(idLocalidade);
			beanLocalidade.setDescTotalizador(descLocalidade);

			List<ContaAReceberContabil> list = (List<ContaAReceberContabil>) agrupamento.get(key);

			beanLocalidade.addDetalhes(this.obterDetalhesRelatorio(list));

			beanGerenciaRegional.addDado(beanLocalidade);

		}

		beanGerenciaRegional.setDescTotalizador(descTotalizadorGerenciaRegional);
		beanGerenciaRegional.setIdTotalizador(idTotalizadoGerenciaRegional);

		return beanGerenciaRegional;

	}

	private RelatorioSaldoContasAReceberContabilBean obterRelatorioPorGerenciaRegionalUnidadeNegocio(MultiKeyMap agrupamento){

		String descTotalizadorGerenciaRegional = null;
		Integer idTotalizadoGerenciaRegional = null;

		RelatorioSaldoContasAReceberContabilBean beanGerenciaRegional = new RelatorioSaldoContasAReceberContabilBean();

		for(Object obj : agrupamento.keySet()){

			MultiKey key = (MultiKey) obj;

			idTotalizadoGerenciaRegional = (Integer) key.getKey(0);
			descTotalizadorGerenciaRegional = (String) key.getKey(1);

			Integer idUnidadeNegocio = (Integer) key.getKey(2);
			String descUnidadeNegocio = (String) key.getKey(3);

			RelatorioSaldoContasAReceberContabilBean beanUnidadeNegocio = new RelatorioSaldoContasAReceberContabilBean();
			beanUnidadeNegocio.setIdTotalizador(idUnidadeNegocio);
			beanUnidadeNegocio.setDescTotalizador(descUnidadeNegocio);

			List<ContaAReceberContabil> list = (List<ContaAReceberContabil>) agrupamento.get(key);

			beanUnidadeNegocio.addDetalhes(this.obterDetalhesRelatorio(list));

			beanGerenciaRegional.addDado(beanUnidadeNegocio);

		}

		beanGerenciaRegional.setDescTotalizador(descTotalizadorGerenciaRegional);
		beanGerenciaRegional.setIdTotalizador(idTotalizadoGerenciaRegional);

		return beanGerenciaRegional;

	}

	private RelatorioSaldoContasAReceberContabilBean obterRelatorioPorEstadoGerenciaRegional(MultiKeyMap agrupamento){

		String descTotalizadorEstado = null;

		RelatorioSaldoContasAReceberContabilBean beanEstado = new RelatorioSaldoContasAReceberContabilBean();

		for(Object obj : agrupamento.keySet()){

			MultiKey key = (MultiKey) obj;
			descTotalizadorEstado = (String) key.getKey(0);

			Integer idGerenciaRegional = (Integer) key.getKey(1);
			String descGerenciaRegional = (String) key.getKey(2);

			RelatorioSaldoContasAReceberContabilBean beanGerenciaRegional = new RelatorioSaldoContasAReceberContabilBean();
			beanGerenciaRegional.setIdTotalizador(idGerenciaRegional);
			beanGerenciaRegional.setDescTotalizador(descGerenciaRegional);

			List<ContaAReceberContabil> list = (List<ContaAReceberContabil>) agrupamento.get(key);

			beanGerenciaRegional.addDetalhes(this.obterDetalhesRelatorio(list));

			beanEstado.addDado(beanGerenciaRegional);

		}

		beanEstado.setDescTotalizador(descTotalizadorEstado);

		return beanEstado;

	}

	private RelatorioSaldoContasAReceberContabilBean obterRelatorioPorGerenciaRegional(MultiKeyMap agrupamento){

		String descTotalizador = null;

		RelatorioSaldoContasAReceberContabilBean rel = new RelatorioSaldoContasAReceberContabilBean();

		for(Object obj : agrupamento.keySet()){

			MultiKey key = (MultiKey) obj;
			descTotalizador = (String) key.getKey(1);

			List<ContaAReceberContabil> list = (List<ContaAReceberContabil>) agrupamento.get(key);

			rel.addDetalhes(this.obterDetalhesRelatorio(list));

		}

		rel.setDescTotalizador(descTotalizador);

		return rel;

	}

	private RelatorioSaldoContasAReceberContabilBean obterRelatorioPorLocalidade(MultiKeyMap agrupamento){

		String descTotalizador = null;

		RelatorioSaldoContasAReceberContabilBean rel = new RelatorioSaldoContasAReceberContabilBean();

		for(Object obj : agrupamento.keySet()){

			MultiKey key = (MultiKey) obj;
			descTotalizador = (String) key.getKey(1);

			List<ContaAReceberContabil> list = (List<ContaAReceberContabil>) agrupamento.get(key);

			rel.addDetalhes(this.obterDetalhesRelatorio(list));

		}

		rel.setDescTotalizador(descTotalizador);

		return rel;

	}

	private RelatorioSaldoContasAReceberContabilBean obterRelatorioPorUnidadeNegocio(MultiKeyMap agrupamento){

		String descTotalizador = null;

		RelatorioSaldoContasAReceberContabilBean rel = new RelatorioSaldoContasAReceberContabilBean();

		for(Object obj : agrupamento.keySet()){

			MultiKey key = (MultiKey) obj;
			descTotalizador = (String) key.getKey(1);

			List<ContaAReceberContabil> list = (List<ContaAReceberContabil>) agrupamento.get(key);

			rel.addDetalhes(this.obterDetalhesRelatorio(list));

		}

		rel.setDescTotalizador(descTotalizador);

		return rel;

	}

	private RelatorioSaldoContasAReceberContabilBean obterRelatorioPorEstado(MultiKeyMap agrupamento){

		String descTotalizador = null;
		RelatorioSaldoContasAReceberContabilBean rel = new RelatorioSaldoContasAReceberContabilBean();

		for(Object obj : agrupamento.keySet()){

			MultiKey key = (MultiKey) obj;
			descTotalizador = (String) key.getKey(0);

			List<ContaAReceberContabil> list = (List<ContaAReceberContabil>) agrupamento.get(key);

			rel.addDetalhes(this.obterDetalhesRelatorio(list));

		}

		rel.setDescTotalizador(descTotalizador);

		return rel;

	}

	private MultiKeyMap agruparDetalhesRelatorioPorSeqLancamento(List<ContaAReceberContabil> listContaAReceberContabil){

		MultiKeyMap retorno = MultiKeyMap.decorate(new LinkedMap());

		for(ContaAReceberContabil contaAReceberContabil : listContaAReceberContabil){

			Integer numeroSequenciaTipoLancamento = contaAReceberContabil.getNumeroSequenciaTipoLancamento();
			String descTipoLancamento = this.obterDescricaoTipoLancamento(numeroSequenciaTipoLancamento);

			if(retorno.containsKey(numeroSequenciaTipoLancamento, descTipoLancamento)){

				((List<ContaAReceberContabil>) retorno.get(numeroSequenciaTipoLancamento, descTipoLancamento)).add(contaAReceberContabil);

			}else{

				List<ContaAReceberContabil> list = new ArrayList<ContaAReceberContabil>();
				list.add(contaAReceberContabil);

				retorno.put(numeroSequenciaTipoLancamento, descTipoLancamento, list);

			}

		}

		return retorno;

	}

	private String obterDescricaoTipoLancamento(Integer numeroSequenciaTipoLancamento){

		String descricao = "";

		switch(numeroSequenciaTipoLancamento){

			case 100:

				descricao = "TOTAL COBRADO NAS CONTAS";
				break;

			case 200:

				descricao = "GUIAS DE PAGAMENTO";
				break;

			case 300:

				descricao = "DEVOLUÇÃO DE VALORES EM CONTA";
				break;

			case 400:

				descricao = "FINANCIAMENTOS A COBRAR";
				break;

			case 500:

				descricao = "PARCELAMENTOS A COBRAR DE CURTO PRAZO";
				break;

			case 600:

				descricao = "PARCELAMENTOS A COBRAR DE LONGO PRAZO";
				break;

			case 700:

				descricao = "CRÉDITOS A REALIZAR";
				break;

			default:
				break;

		}

		return descricao;

	}

	private MultiKeyMap agruparDetalhesRelatorioPorItemLancamento(List<ContaAReceberContabil> listContaAReceberContabil){

		MultiKeyMap retorno = MultiKeyMap.decorate(new LinkedMap());

		for(ContaAReceberContabil contaAReceberContabil : listContaAReceberContabil){

			LancamentoItem lancamentoItem = contaAReceberContabil.getLancamentoItem();

			if(retorno.containsKey(lancamentoItem.getId(), lancamentoItem.getDescricao())){

				((List<ContaAReceberContabil>) retorno.get(lancamentoItem.getId(), lancamentoItem.getDescricao()))
								.add(contaAReceberContabil);

			}else{

				List<ContaAReceberContabil> list = new ArrayList<ContaAReceberContabil>();
				list.add(contaAReceberContabil);

				retorno.put(lancamentoItem.getId(), lancamentoItem.getDescricao(), list);

			}

		}

		return retorno;

	}

	private MultiKeyMap agruparDadosRelSaldoContasAReceberContabilPorLocalidade(List<ContaAReceberContabil> listContaAReceberContabil){

		MultiKeyMap retorno = MultiKeyMap.decorate(new LinkedMap());

		for(ContaAReceberContabil contaAReceberContabil : listContaAReceberContabil){

			Localidade localidade = contaAReceberContabil.getLocalidade();

			if(retorno.containsKey(localidade.getId(), localidade.getDescricao())){

				((List<ContaAReceberContabil>) retorno.get(localidade.getId(), localidade.getDescricao())).add(contaAReceberContabil);

			}else{

				List<ContaAReceberContabil> list = new ArrayList<ContaAReceberContabil>();
				list.add(contaAReceberContabil);

				retorno.put(localidade.getId(), localidade.getDescricao(), list);

			}

		}

		return retorno;

	}

	private MultiKeyMap agruparDadosRelSaldoContasAReceberContabilPorUnidadeNegocioLocalidade(
					List<ContaAReceberContabil> listContaAReceberContabil){

		MultiKeyMap retorno = MultiKeyMap.decorate(new LinkedMap());

		for(ContaAReceberContabil contaAReceberContabil : listContaAReceberContabil){

			UnidadeNegocio unidadeNegocio = contaAReceberContabil.getUnidadeNegocio();
			Localidade localidade = contaAReceberContabil.getLocalidade();

			if(retorno.containsKey(unidadeNegocio.getId(), unidadeNegocio.getNome(), localidade.getId(), localidade.getDescricao())){

				((List<ContaAReceberContabil>) retorno.get(unidadeNegocio.getId(), unidadeNegocio.getNome(), localidade.getId(), localidade
								.getDescricao())).add(contaAReceberContabil);

			}else{

				List<ContaAReceberContabil> list = new ArrayList<ContaAReceberContabil>();
				list.add(contaAReceberContabil);

				retorno.put(unidadeNegocio.getId(), unidadeNegocio.getNome(), localidade.getId(), localidade.getDescricao(), list);

			}

		}

		return retorno;

	}

	private MultiKeyMap agruparDadosRelSaldoContasAReceberContabilPorGerenciaRegionalLocalidade(
					List<ContaAReceberContabil> listContaAReceberContabil){

		MultiKeyMap retorno = MultiKeyMap.decorate(new LinkedMap());

		for(ContaAReceberContabil contaAReceberContabil : listContaAReceberContabil){

			GerenciaRegional gerenciaRegional = contaAReceberContabil.getGerenciaRegional();
			Localidade localidade = contaAReceberContabil.getLocalidade();

			if(retorno.containsKey(gerenciaRegional.getId(), gerenciaRegional.getNome(), localidade.getId(), localidade.getDescricao())){

				((List<ContaAReceberContabil>) retorno.get(gerenciaRegional.getId(), gerenciaRegional.getNome(), localidade.getId(),
								localidade.getDescricao())).add(contaAReceberContabil);

			}else{

				List<ContaAReceberContabil> list = new ArrayList<ContaAReceberContabil>();
				list.add(contaAReceberContabil);

				retorno.put(gerenciaRegional.getId(), gerenciaRegional.getNome(), localidade.getId(), localidade.getDescricao(), list);

			}

		}

		return retorno;

	}

	private MultiKeyMap agruparDadosRelSaldoContasAReceberContabilPorGerenciaRegionalUnidadeNegocio(
					List<ContaAReceberContabil> listContaAReceberContabil){

		MultiKeyMap retorno = MultiKeyMap.decorate(new LinkedMap());

		for(ContaAReceberContabil contaAReceberContabil : listContaAReceberContabil){

			GerenciaRegional gerenciaRegional = contaAReceberContabil.getGerenciaRegional();
			UnidadeNegocio unidadeNegocio = contaAReceberContabil.getUnidadeNegocio();

			if(retorno.containsKey(gerenciaRegional.getId(), gerenciaRegional.getNome(), unidadeNegocio.getId(), unidadeNegocio.getNome())){

				((List<ContaAReceberContabil>) retorno.get(gerenciaRegional.getId(), gerenciaRegional.getNome(), unidadeNegocio.getId(),
								unidadeNegocio.getNome())).add(contaAReceberContabil);

			}else{

				List<ContaAReceberContabil> list = new ArrayList<ContaAReceberContabil>();
				list.add(contaAReceberContabil);

				retorno.put(gerenciaRegional.getId(), gerenciaRegional.getNome(), unidadeNegocio.getId(), unidadeNegocio.getNome(), list);

			}

		}

		return retorno;

	}

	private MultiKeyMap agruparDadosRelSaldoContasAReceberContabilPorUnidadeNegocio(List<ContaAReceberContabil> listContaAReceberContabil){

		MultiKeyMap retorno = MultiKeyMap.decorate(new LinkedMap());

		for(ContaAReceberContabil contaAReceberContabil : listContaAReceberContabil){

			UnidadeNegocio unidadeNegocio = contaAReceberContabil.getUnidadeNegocio();

			if(retorno.containsKey(unidadeNegocio.getId(), unidadeNegocio.getNome())){

				((List<ContaAReceberContabil>) retorno.get(unidadeNegocio.getId(), unidadeNegocio.getNome())).add(contaAReceberContabil);

			}else{

				List<ContaAReceberContabil> list = new ArrayList<ContaAReceberContabil>();
				list.add(contaAReceberContabil);

				retorno.put(unidadeNegocio.getId(), unidadeNegocio.getNome(), list);

			}

		}

		return retorno;

	}

	private MultiKeyMap agruparDadosRelSaldoContasAReceberContabilPorGerenciaRegional(List<ContaAReceberContabil> listContaAReceberContabil){

		MultiKeyMap retorno = MultiKeyMap.decorate(new LinkedMap());

		for(ContaAReceberContabil contaAReceberContabil : listContaAReceberContabil){

			GerenciaRegional gerenciaRegional = contaAReceberContabil.getGerenciaRegional();

			if(retorno.containsKey(gerenciaRegional.getId(), gerenciaRegional.getNome())){

				((List<ContaAReceberContabil>) retorno.get(gerenciaRegional.getId(), gerenciaRegional.getNome()))
								.add(contaAReceberContabil);

			}else{

				List<ContaAReceberContabil> list = new ArrayList<ContaAReceberContabil>();
				list.add(contaAReceberContabil);

				retorno.put(gerenciaRegional.getId(), gerenciaRegional.getNome(), list);

			}

		}

		return retorno;

	}

	private MultiKeyMap agruparDadosRelSaldoContasAReceberContabilPorEstadoLocalidade(List<ContaAReceberContabil> listContaAReceberContabil){

		MultiKeyMap retorno = MultiKeyMap.decorate(new LinkedMap());

		for(ContaAReceberContabil contaAReceberContabil : listContaAReceberContabil){

			GerenciaRegional gerenciaRegional = contaAReceberContabil.getGerenciaRegional();
			UnidadeNegocio unidadeNegocio = contaAReceberContabil.getUnidadeNegocio();
			Localidade localidade = contaAReceberContabil.getLocalidade();

			if(retorno.containsKey(gerenciaRegional.getId(), unidadeNegocio.getId(), localidade.getId())){

				((List<ContaAReceberContabil>) retorno.get(gerenciaRegional.getId(), unidadeNegocio.getId(), localidade.getId()))
								.add(contaAReceberContabil);

			}else{

				List<ContaAReceberContabil> list = new ArrayList<ContaAReceberContabil>();
				list.add(contaAReceberContabil);

				retorno.put(gerenciaRegional.getId(), unidadeNegocio.getId(), localidade.getId(), list);

			}

		}

		return retorno;

	}

	private MultiKeyMap agruparDadosRelSaldoContasAReceberContabilPorEstadoUnidadeNegocio(
					List<ContaAReceberContabil> listContaAReceberContabil){

		MultiKeyMap retorno = MultiKeyMap.decorate(new LinkedMap());

		for(ContaAReceberContabil contaAReceberContabil : listContaAReceberContabil){

			GerenciaRegional gerenciaRegional = contaAReceberContabil.getGerenciaRegional();
			UnidadeNegocio unidadeNegocio = contaAReceberContabil.getUnidadeNegocio();

			if(retorno.containsKey(gerenciaRegional.getId(), gerenciaRegional.getNome(), unidadeNegocio.getId(), unidadeNegocio.getNome())){

				((List<ContaAReceberContabil>) retorno.get(gerenciaRegional.getId(), gerenciaRegional.getNome(), unidadeNegocio.getId(),
								unidadeNegocio.getNome())).add(contaAReceberContabil);

			}else{

				List<ContaAReceberContabil> list = new ArrayList<ContaAReceberContabil>();
				list.add(contaAReceberContabil);

				retorno.put(gerenciaRegional.getId(), gerenciaRegional.getNome(), unidadeNegocio.getId(), unidadeNegocio.getNome(), list);

			}

		}

		return retorno;

	}

	private MultiKeyMap agruparDadosRelSaldoContasAReceberContabilPorEstadoGerenciaRegional(
					List<ContaAReceberContabil> listContaAReceberContabil){

		MultiKeyMap retorno = MultiKeyMap.decorate(new LinkedMap());

		for(ContaAReceberContabil contaAReceberContabil : listContaAReceberContabil){

			GerenciaRegional gerenciaRegional = contaAReceberContabil.getGerenciaRegional();

			if(retorno.containsKey("ESTADO", gerenciaRegional.getId(), gerenciaRegional.getNome())){

				((List<ContaAReceberContabil>) retorno.get("ESTADO", gerenciaRegional.getId(), gerenciaRegional.getNome()))
								.add(contaAReceberContabil);

			}else{

				List<ContaAReceberContabil> list = new ArrayList<ContaAReceberContabil>();
				list.add(contaAReceberContabil);

				retorno.put("ESTADO", gerenciaRegional.getId(), gerenciaRegional.getNome(), list);

			}

		}

		return retorno;

	}

	private MultiKeyMap agruparDadosRelSaldoContasAReceberContabilPorEstado(List<ContaAReceberContabil> listContaAReceberContabil){

		MultiKeyMap retorno = MultiKeyMap.decorate(new LinkedMap());

		retorno.put("ESTADO", "ESTADO", listContaAReceberContabil);

		return retorno;

	}

	private RelatorioSaldoContasAReceberContabilBean obterInstanciaRelatorioSaldoContasAReceberContabilBean(
					RelatorioSaldoContasAReceberContabilBean bean, Integer idBean, String descBean){

		RelatorioSaldoContasAReceberContabilBean retorno = null;

		if(bean != null){

			if(!bean.getIdTotalizador().equals(idBean)){

				RelatorioSaldoContasAReceberContabilBean newBean = new RelatorioSaldoContasAReceberContabilBean();
				newBean.setIdTotalizador(idBean);
				newBean.setDescTotalizador(descBean);

				retorno = newBean;

			}else{

				retorno = bean;

			}

		}else{

			RelatorioSaldoContasAReceberContabilBean newBean = new RelatorioSaldoContasAReceberContabilBean();
			newBean.setIdTotalizador(idBean);
			newBean.setDescTotalizador(descBean);

			retorno = newBean;

		}

		return retorno;

	}

}
