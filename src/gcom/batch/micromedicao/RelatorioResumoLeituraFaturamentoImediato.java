
package gcom.batch.micromedicao;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.micromedicao.bean.RelatorioResumoLeituraFaturamentoImediatoHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.micromedicao.RelatorioResumoLeituraAnormalidadesFaturamentoImediatoBean;
import gcom.relatorio.micromedicao.RelatorioResumoLeituraFaturamentoImediatoBean;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;

public class RelatorioResumoLeituraFaturamentoImediato
				extends TarefaRelatorio {

	private static final long serialVersionUID = -6421051804181251492L;

	public RelatorioResumoLeituraFaturamentoImediato(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_RESUMO_LEITURA_FATURAMENTO_IMEDIATO);
	}

	@Deprecated
	public RelatorioResumoLeituraFaturamentoImediato() {

		super(null, "");
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;
		Collection<RelatorioResumoLeituraFaturamentoImediatoHelper> colecaoResumoHelper = (Collection<RelatorioResumoLeituraFaturamentoImediatoHelper>) this
						.getParametro("colecaoResumoHelper");

		if(colecaoResumoHelper != null){
			retorno = colecaoResumoHelper.size();
		}

		return retorno;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa(this.getClass().getName(), this);
	}

	@Override
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Fachada fachada = Fachada.getInstancia();

		// Collection colecaoEmitirContaHelper = (Collection)
		// getParametro("colecaoEmitirContaHelper");
		Collection<RelatorioResumoLeituraFaturamentoImediatoHelper> colecaoResumoHelper = (Collection<RelatorioResumoLeituraFaturamentoImediatoHelper>) this
						.getParametro("colecaoResumoHelper");

		int quantidadeRegistrosComDivergenciaValores = ((Integer) this.getParametro("quantidadeRegistrosComDivergenciaValores")).intValue();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		// valor de retorno
		byte[] retorno = null;

		// Parâmetros do relatório
		Map<String, Object> parametros = new HashMap();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		String nomeEmpresa = (String) this.getParametro("nomeEmpresa");

		if(!Util.isVazioOuBranco(nomeEmpresa)){

			parametros.put("nomeEmpresa", nomeEmpresa);
		}else{

			parametros.put("nomeEmpresa", sistemaParametro.getNomeEmpresa());
		}

		Integer idEmpresa = (Integer) this.getParametro("idEmpresa");
		if(idEmpresa != null){
			parametros.put("idEmpresa", idEmpresa.toString());
		}else{
			parametros.put("idEmpresa", "");
		}

		Integer idGrupoFaturamento = (Integer) this.getParametro("idGrupoFaturamento");
		if(idGrupoFaturamento != null){

			parametros.put("idGrupoFaturamento", idGrupoFaturamento.toString());

			// Ano Mês Referência do Grupo
			FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) fachada.pesquisar(idGrupoFaturamento, FaturamentoGrupo.class);
			parametros.put("referencia", Util.formatarAnoMesSemBarraParaMesAnoComBarra(faturamentoGrupo.getAnoMesReferencia()));
		}else{

			parametros.put("idGrupoFaturamento", "");
		}

		Collection<RelatorioResumoLeituraFaturamentoImediatoBean> colecaoBeanOrdenada = this.inicializarBeanRelatorio(colecaoResumoHelper);

		if(colecaoBeanOrdenada == null || colecaoBeanOrdenada.isEmpty()){

			// Não existem dados para a exibição do relatório.
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}

		// Inicio - Coleção resposável por exibir a descrição e quantidade das anormalidades no
		// subrelatório (relatorioResumoLeituraFaturamentoImediatoAnormalidades)
		Collection<RelatorioResumoLeituraAnormalidadesFaturamentoImediatoBean> colecaoLeituraAnormalidadeBean = new ArrayList<RelatorioResumoLeituraAnormalidadesFaturamentoImediatoBean>();
		Iterator<RelatorioResumoLeituraFaturamentoImediatoBean> iterator = colecaoBeanOrdenada.iterator();
		Map<Integer, Integer> mapAnormalidadeQuantidade = new HashMap<Integer, Integer>();
		Integer quantidadePorAnormalidade = 0;

		while(iterator.hasNext()){

			RelatorioResumoLeituraFaturamentoImediatoBean item = iterator.next();

			if(!Util.isVazioOuBranco(item.getIdAnormalidade())){

				if(mapAnormalidadeQuantidade.get(Util.obterInteger(item.getIdAnormalidade())) == null){

					RelatorioResumoLeituraAnormalidadesFaturamentoImediatoBean bean = new RelatorioResumoLeituraAnormalidadesFaturamentoImediatoBean();

					bean.setIdAnormalidade(Util.obterInteger(item.getIdAnormalidade()));
					bean.setDescricaoAnormalidade(item.getDescricaoAnormalidade());
					bean.setQuantidade("1");
					colecaoLeituraAnormalidadeBean.add(bean);

					mapAnormalidadeQuantidade.put(Util.obterInteger(item.getIdAnormalidade()), Util.obterInteger("1"));
				}else{

					quantidadePorAnormalidade = mapAnormalidadeQuantidade.get(Util.obterInteger(item.getIdAnormalidade()));
					quantidadePorAnormalidade = quantidadePorAnormalidade.intValue() + 1;
					mapAnormalidadeQuantidade.put(Util.obterInteger(item.getIdAnormalidade()), quantidadePorAnormalidade);
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoLeituraAnormalidadeBean)){

			for(RelatorioResumoLeituraAnormalidadesFaturamentoImediatoBean bean : colecaoLeituraAnormalidadeBean){

				bean.setQuantidade(mapAnormalidadeQuantidade.get(bean.getIdAnormalidade()).toString());
			}

			// Ordena a coleção de Anormalidades pelo id
			List sortFields = new ArrayList();

			sortFields.add(new BeanComparator("idAnormalidade"));

			ComparatorChain multiSort = new ComparatorChain(sortFields);
			Collections.sort((List) colecaoLeituraAnormalidadeBean, multiSort);
		}

		JRBeanCollectionDataSource arrayJRDetailFaturamentoImediato;
		ArrayList arrayRelatorioDetailFaturaBean;
		arrayRelatorioDetailFaturaBean = new ArrayList();
		arrayRelatorioDetailFaturaBean.addAll(colecaoLeituraAnormalidadeBean);
		arrayJRDetailFaturamentoImediato = new JRBeanCollectionDataSource(arrayRelatorioDetailFaturaBean);

		parametros.put("arrayJRDetailFaturamentoImediato", arrayJRDetailFaturamentoImediato);
		parametros.put("quantidadeAnormalidades", colecaoLeituraAnormalidadeBean.size());
		parametros.put("quantidadeRegistrosComDivergenciaValores", quantidadeRegistrosComDivergenciaValores);
		// Fim - Coleção resposável por exibir a descrição e quantidade as anormalidades no
		// subrelatório

		RelatorioDataSource ds = new RelatorioDataSource((java.util.List) colecaoBeanOrdenada);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_RESUMO_LEITURA_FATURAMENTO_IMEDIATO, parametros, ds,
						TarefaRelatorio.TIPO_PDF);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_RESUMO_LEITURA_ANORMALIDADE, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;

	}

	private Collection<RelatorioResumoLeituraFaturamentoImediatoBean> inicializarBeanRelatorio(
					Collection<RelatorioResumoLeituraFaturamentoImediatoHelper> colecaoResumoHelper){

		Collection<RelatorioResumoLeituraFaturamentoImediatoBean> colecaoBean = new ArrayList<RelatorioResumoLeituraFaturamentoImediatoBean>();
		Collection<RelatorioResumoLeituraFaturamentoImediatoBean> colecaoBeanOrdenada = new ArrayList<RelatorioResumoLeituraFaturamentoImediatoBean>();

		if(colecaoResumoHelper != null && !colecaoResumoHelper.isEmpty()){
			Iterator<RelatorioResumoLeituraFaturamentoImediatoHelper> iterator = colecaoResumoHelper.iterator();
			while(iterator.hasNext()){
				RelatorioResumoLeituraFaturamentoImediatoHelper resumoHelper = iterator.next();

				String idSetorComercial = null;
				if(resumoHelper.getIdSetorComercial() != null){
					idSetorComercial = "" + resumoHelper.getIdSetorComercial();
				}
				String descricaoSetorComercial = null;
				if(resumoHelper.getDescricaoSetorComercial() != null){
					descricaoSetorComercial = resumoHelper.getDescricaoSetorComercial();
				}
				String idLocalidade = null;
				if(resumoHelper.getIdLocalidade() != null){
					idLocalidade = "" + resumoHelper.getIdLocalidade();
				}
				String descricaoLocalidade = null;
				if(resumoHelper.getDescricaoLocalidade() != null){
					descricaoLocalidade = resumoHelper.getDescricaoLocalidade().trim();
				}
				String idLeitura = null;
				if(resumoHelper.getIdLeitura() != null){
					idLeitura = "" + resumoHelper.getIdLeitura();
				}
				String idAnormalidade = null;
				if(resumoHelper.getIdAnormalidade() != null){
					idAnormalidade = "" + resumoHelper.getIdAnormalidade();
				}
				String descricaoAnormalidade = null;
				if(resumoHelper.getDescricaoAnormalidade() != null){
					descricaoAnormalidade = resumoHelper.getDescricaoAnormalidade().trim();
				}

				RelatorioResumoLeituraFaturamentoImediatoBean resumoBean = new RelatorioResumoLeituraFaturamentoImediatoBean(
								idSetorComercial, descricaoSetorComercial, idLocalidade, descricaoLocalidade, idLeitura, idAnormalidade,
								descricaoAnormalidade);

				resumoBean.setImpressaoOcorrencias("true");

				colecaoBean.add(resumoBean);
			}

			colecaoBeanOrdenada.addAll(colecaoBean);

			// Ordenar por mais de um campo
			List sortFields = new ArrayList();

			sortFields.add(new BeanComparator("idLocalidade"));
			sortFields.add(new BeanComparator("idSetorComercial"));

			ComparatorChain multiSort = new ComparatorChain(sortFields);
			Collections.sort((List) colecaoBeanOrdenada, multiSort);

		}

		return colecaoBeanOrdenada;
	}
}
