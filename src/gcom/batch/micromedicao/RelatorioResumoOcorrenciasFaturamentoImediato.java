
package gcom.batch.micromedicao;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.micromedicao.RelatorioResumoOcorrenciasFaturamentoImediatoBean;
import gcom.relatorio.micromedicao.RelatorioResumoOcorrenciasFaturamentoImediatoHelper;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;

public class RelatorioResumoOcorrenciasFaturamentoImediato
				extends TarefaRelatorio {

	private static final long serialVersionUID = -6421051804181251492L;

	public RelatorioResumoOcorrenciasFaturamentoImediato(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_RESUMO_OCORRENCIAS_FATURAMENTO_IMEDIATO);
	}

	@Deprecated
	public RelatorioResumoOcorrenciasFaturamentoImediato() {

		super(null, "");
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioResumoOcorrenciasFaturamentoImediato", this);

	}

	@Override
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Fachada fachada = Fachada.getInstancia();

		// Collection<RelatorioResumoOcorrenciasHelper> colecaoResumoHelper =
		// (Collection<RelatorioResumoOcorrenciasHelper>) getParametro("colecaoResumoHelper");
		Collection<RelatorioResumoOcorrenciasFaturamentoImediatoHelper> colecaoResumoHelper = (Collection<RelatorioResumoOcorrenciasFaturamentoImediatoHelper>) getParametro("colecaoResumoHelper");

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		// valor de retorno
		byte[] retorno = null;

		// Parâmetros do relatório
		Map<String, String> parametros = new HashMap();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		if(this.getParametro("idGrupoFaturamento") != null){
			parametros.put("grupoFaturamento", this.getParametro("idGrupoFaturamento").toString());
		}else{
			parametros.put("grupoFaturamento", "");
		}

		if(this.getParametro("idEmpresa") != null){
			parametros.put("idEmpresa", this.getParametro("idEmpresa").toString());
		}else{
			parametros.put("idEmpresa", "");
		}

		if(this.getParametro("nomeEmpresa") != null){
			parametros.put("nomeEmpresa", this.getParametro("nomeEmpresa").toString());
		}else{
			parametros.put("nomeEmpresa", "");
		}

		if(this.getParametro("anoMesReferencia") != null){
			parametros.put("anoMesReferencia", this.getParametro("anoMesReferencia").toString());
		}else{
			parametros.put("anoMesReferencia", "");
		}

		Collection<RelatorioResumoOcorrenciasFaturamentoImediatoBean> colecaoBean = this.inicializarBeanRelatorio(colecaoResumoHelper);

		if(colecaoBean == null || colecaoBean.isEmpty()){
			// Não existem dados para a exibição do relatório.
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}

		RelatorioDataSource ds = new RelatorioDataSource((java.util.List) colecaoBean);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_RESUMO_OCORRENCIAS_FATURAMENTO_IMEDIATO, parametros, ds,
						TarefaRelatorio.TIPO_PDF);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_RESUMO_OCORRENCIAS_FATURAMENTO_IMEDIATO, idFuncionalidadeIniciada,
							null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;

	}

	private Collection<RelatorioResumoOcorrenciasFaturamentoImediatoBean> inicializarBeanRelatorio(
					Collection<RelatorioResumoOcorrenciasFaturamentoImediatoHelper> colecaoResumoHelper){

		Collection<RelatorioResumoOcorrenciasFaturamentoImediatoBean> colecaoBean = new ArrayList<RelatorioResumoOcorrenciasFaturamentoImediatoBean>();
		Collection<RelatorioResumoOcorrenciasFaturamentoImediatoBean> colecaoBeanOrdenada = new ArrayList<RelatorioResumoOcorrenciasFaturamentoImediatoBean>();

		if(colecaoResumoHelper != null && !colecaoResumoHelper.isEmpty()){
			Iterator<RelatorioResumoOcorrenciasFaturamentoImediatoHelper> iterator = colecaoResumoHelper.iterator();

			while(iterator.hasNext()){
				RelatorioResumoOcorrenciasFaturamentoImediatoHelper resumoHelper = iterator.next();
				RelatorioResumoOcorrenciasFaturamentoImediatoBean resumoBean = new RelatorioResumoOcorrenciasFaturamentoImediatoBean(""
								+ resumoHelper.getCodLocalidade(), "" + resumoHelper.getNomeLocalidade(), "" + resumoHelper.getCodImovel(),
								"" + resumoHelper.getNomeCliente(), "" + resumoHelper.getInscricao(), "" + resumoHelper.getSituacaoAgua(),
								"" + resumoHelper.getSituacaoEsgoto(), resumoHelper.getNumeroConsumoFaturadoAgua() != null ? resumoHelper
												.getNumeroConsumoFaturadoAgua().toString() : null, resumoHelper
												.getNumeroConsumoFaturadoEsgoto() != null ? resumoHelper.getNumeroConsumoFaturadoEsgoto()
												.toString() : null, "" + resumoHelper.getErroEncontradoNoProcessamento());

				if(resumoHelper.getValorAguaGsan() != null){
					resumoBean.setValorAguaGsan(resumoHelper.getValorAguaGsan());
				}
				if(resumoHelper.getValorAguaColetor() != null){
					resumoBean.setValorAguaColetor(resumoHelper.getValorAguaColetor());
				}
				if(resumoHelper.getValorEsgotoGsan() != null){
					resumoBean.setValorEsgotoGsan(resumoHelper.getValorEsgotoGsan());
				}
				if(resumoHelper.getValorEsgotoColetor() != null){
					resumoBean.setValorEsgotoColetor(resumoHelper.getValorEsgotoColetor());
				}

				if(resumoHelper.getCategorias() != null){
					resumoBean.setCategorias(resumoHelper.getCategorias());
				}

				colecaoBean.add(resumoBean);
			}
		}

		colecaoBeanOrdenada.addAll(colecaoBean);

		// Ordenação por mais de um campo
		List sortFields = new ArrayList();

		sortFields.add(new BeanComparator("codLocalidade"));
		sortFields.add(new BeanComparator("codImovel"));

		ComparatorChain multiSort = new ComparatorChain(sortFields);
		Collections.sort((List) colecaoBeanOrdenada, multiSort);

		return colecaoBeanOrdenada;
	}
}
