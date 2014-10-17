
package gcom.relatorio.faturamento;

import gcom.batch.Relatorio;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Relatório de Ocorrência da Geração do Pré-Faturamento - Resumo
 * 
 * @author Hebert Falcão
 * @date 06/04/2012
 */
public class RelatorioOcorrenciaGeracaoPreFatResumo
				extends TarefaRelatorio {

	private static final long serialVersionUID = -6421051804181251492L;

	public RelatorioOcorrenciaGeracaoPreFatResumo(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_OCORRENCIA_GERACAO_PRE_FATURAMENTO_RESUMO);
	}

	@Deprecated
	public RelatorioOcorrenciaGeracaoPreFatResumo() {

		super(null, "");
	}

	public int calcularTotalRegistrosRelatorio(){

		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioOcorrenciaGeracaoPreFaturamentoResumo", this);
	}

	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		Fachada fachada = Fachada.getInstancia();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		String imagemRelatorio = sistemaParametro.getImagemRelatorio();

		String idGrupoFaturamento = (String) this.getParametro("idGrupoFaturamento");

		String anoMesReferencia = (String) this.getParametro("anoMesReferencia");

		HashMap<Integer, RelatorioOcorrenciaGeracaoPreFatResumoHelper> geracaoPreFatResumoHelperMap = (HashMap<Integer, RelatorioOcorrenciaGeracaoPreFatResumoHelper>) this
						.getParametro("geracaoPreFatResumoHelperMap");

		String dataVencimento = (String) this.getParametro("dataVencimento");

		String referenciaTarifa = (String) this.getParametro("referenciaTarifa");

		// Parâmetros do relatório
		Map<String, String> parametros = new HashMap();

		parametros.put("imagem", imagemRelatorio);
		parametros.put("referenciaFaturamento", anoMesReferencia);
		parametros.put("grupoFaturamento", idGrupoFaturamento);
		parametros.put("dataVencimento", dataVencimento);
		parametros.put("referenciaTarifa", referenciaTarifa);
		parametros.put("tipoFormatoRelatorio", "PDF");

		Collection<RelatorioOcorrenciaGeracaoPreFatResumoBean> colecaoBean = this.inicializarBeanRelatorio(geracaoPreFatResumoHelperMap);

		if(Util.isVazioOrNulo(colecaoBean)){
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}

		RelatorioDataSource ds = new RelatorioDataSource((java.util.List) colecaoBean);

		byte[] retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_OCORRENCIA_GERACAO_PRE_FATURAMENTO_RESUMO, parametros, ds,
						TarefaRelatorio.TIPO_PDF);

		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_OCORRENCIA_GERACAO_PRE_FATURAMENTO_RESUMO, idFuncionalidadeIniciada,
							null);
		}catch(ControladorException e){
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno;
	}

	private Collection<RelatorioOcorrenciaGeracaoPreFatResumoBean> inicializarBeanRelatorio(
					HashMap<Integer, RelatorioOcorrenciaGeracaoPreFatResumoHelper> geracaoPreFatResumoHelperMap){

		Fachada fachada = Fachada.getInstancia();

		String setoresProcessados = "";

		Collection<RelatorioOcorrenciaGeracaoPreFatResumoBean> colecaoBean = new ArrayList<RelatorioOcorrenciaGeracaoPreFatResumoBean>();

		RelatorioOcorrenciaGeracaoPreFatResumoHelper geracaoPreFatResumoHelper = null;

		RelatorioOcorrenciaGeracaoPreFatResumoBean bean = null;

		Collection<Integer> colecaoSetorComercial = null;

		// StringBuffer setoresProcessadosAux = null;

		Set<Integer> listaSetorComercial = null;

		StringBuilder strColecaoSetorComercial = null;

		int quantidadeFaturados = 0;
		String quantidadeFaturadosStr = "";

		int quantidadeMedidos = 0;
		String quantidadeMedidosStr = "";

		int quantidadeNaoMedidos = 0;
		String quantidadeNaoMedidosStr = "";

		int quantidadeTipo1 = 0;
		String quantidadeTipo1Str = "";

		int quantidadeTipo2 = 0;
		String quantidadeTipo2Str = "";

		int quantidadeTipo3 = 0;
		String quantidadeTipo3Str = "";

		int quantidadeTipo4 = 0;
		String quantidadeTipo4Str = "";

		int quantidadeTipo5 = 0;
		String quantidadeTipo5Str = "";

		int quantidadeTipo6 = 0;
		String quantidadeTipo6Str = "";

		int quantidadeTipo7 = 0;
		String quantidadeTipo7Str = "";

		BigDecimal valorTotalCreditos = BigDecimal.ZERO;
		BigDecimal valorTotalDebitos = BigDecimal.ZERO;

		Localidade localidade = null;
		String descricaoLocalidade = "";

		for(Integer idLocalidade : geracaoPreFatResumoHelperMap.keySet()){
			geracaoPreFatResumoHelper = geracaoPreFatResumoHelperMap.get(idLocalidade);

			bean = new RelatorioOcorrenciaGeracaoPreFatResumoBean();

			localidade = fachada.pesquisarLocalidadeDigitada(idLocalidade);
			descricaoLocalidade = localidade.getDescricaoComId();
			bean.setLocalidade(descricaoLocalidade);

			colecaoSetorComercial = geracaoPreFatResumoHelper.getColecaoSetorComercial();

			if(!Util.isVazioOrNulo(colecaoSetorComercial)){
				// Collections.sort((List) colecaoSetorComercial);
				listaSetorComercial = new TreeSet<Integer>();

				strColecaoSetorComercial = new StringBuilder();

				for(Integer integer : colecaoSetorComercial){

					listaSetorComercial.add(integer);
					//System.out.println(integer);

				}

				for(Integer integer : listaSetorComercial){

					strColecaoSetorComercial.append(integer);
					strColecaoSetorComercial.append(" ");
				}

				// setoresProcessadosAux = new StringBuffer();
				//
				// for(Integer codigo : colecaoSetorComercial){
				// setoresProcessadosAux.append(codigo + " ");
				// }
				//
				// setoresProcessados = setoresProcessadosAux.toString();

				setoresProcessados = strColecaoSetorComercial.toString();
				//System.out.println(setoresProcessados);
			}else{
				setoresProcessados = "";
			}

			bean.setSetoresProcessados(setoresProcessados);

			quantidadeFaturados = geracaoPreFatResumoHelper.getQuantidadeFaturados();
			quantidadeFaturadosStr = Integer.toString(quantidadeFaturados);
			bean.setQuantidadeFaturados(quantidadeFaturadosStr);

			quantidadeMedidos = geracaoPreFatResumoHelper.getQuantidadeMedidos();
			quantidadeMedidosStr = Integer.toString(quantidadeMedidos);
			bean.setQuantidadeMedidos(quantidadeMedidosStr);

			quantidadeNaoMedidos = geracaoPreFatResumoHelper.getQuantidadeNaoMedidos();
			quantidadeNaoMedidosStr = Integer.toString(quantidadeNaoMedidos);
			bean.setQuantidadeNaoMedidos(quantidadeNaoMedidosStr);

			quantidadeTipo1 = geracaoPreFatResumoHelper.getQuantidadeTipo1();
			quantidadeTipo1Str = Integer.toString(quantidadeTipo1);
			bean.setQuantidadeTipo1(quantidadeTipo1Str);

			quantidadeTipo2 = geracaoPreFatResumoHelper.getQuantidadeTipo2();
			quantidadeTipo2Str = Integer.toString(quantidadeTipo2);
			bean.setQuantidadeTipo2(quantidadeTipo2Str);

			quantidadeTipo3 = geracaoPreFatResumoHelper.getQuantidadeTipo3();
			quantidadeTipo3Str = Integer.toString(quantidadeTipo3);
			bean.setQuantidadeTipo3(quantidadeTipo3Str);

			quantidadeTipo4 = geracaoPreFatResumoHelper.getQuantidadeTipo4();
			quantidadeTipo4Str = Integer.toString(quantidadeTipo4);
			bean.setQuantidadeTipo4(quantidadeTipo4Str);

			quantidadeTipo5 = geracaoPreFatResumoHelper.getQuantidadeTipo5();
			quantidadeTipo5Str = Integer.toString(quantidadeTipo5);
			bean.setQuantidadeTipo5(quantidadeTipo5Str);

			quantidadeTipo6 = geracaoPreFatResumoHelper.getQuantidadeTipo6();
			quantidadeTipo6Str = Integer.toString(quantidadeTipo6);
			bean.setQuantidadeTipo6(quantidadeTipo6Str);

			if(geracaoPreFatResumoHelper.getQuantidadeTipo7() > 0){

				quantidadeTipo7 = geracaoPreFatResumoHelper.getQuantidadeTipo7();
				quantidadeTipo7Str = Integer.toString(quantidadeTipo7);
				bean.setQuantidadeTipo7(quantidadeTipo7Str);
			}

			valorTotalCreditos = geracaoPreFatResumoHelper.getValorTotalCreditos();
			bean.setValorTotalCreditos(valorTotalCreditos);

			valorTotalDebitos = geracaoPreFatResumoHelper.getValorTotalDebitos();
			bean.setValorTotalDebitos(valorTotalDebitos);

			colecaoBean.add(bean);
		}

		return colecaoBean;
	}

}
