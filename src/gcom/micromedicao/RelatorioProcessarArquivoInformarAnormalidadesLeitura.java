/**
 * 
 */

package gcom.micromedicao;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Péricles Tavares
 */
public class RelatorioProcessarArquivoInformarAnormalidadesLeitura
				extends TarefaRelatorio {

	public RelatorioProcessarArquivoInformarAnormalidadesLeitura(Usuario usuario, String nomeRelatorio) {

		super(usuario, nomeRelatorio);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.TarefaRelatorio#calcularTotalRegistrosRelatorio()
	 */
	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#executar()
	 */
	@Override
	public Object executar() throws TarefaException{

		// valor de retorno
		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		// ------------------------------------

		String faturamentoGrupo = (String) getParametro("faturamentoGrupo");
		String empresa = (String) getParametro("empresa");
		String localidade = (String) getParametro("localidade");
		Integer total = (Integer) getParametro("total");
		Map<Integer, String> erros = (Map<Integer, String>) getParametro("erros");
		Map<Integer, String> advertencias = (Map<Integer, String>) getParametro("advertencias");

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioProcessarArquivoInformarAnormalidadesLeituraBean relatorioBean = null;

		// se a coleção de parâmetros da analise não for vazia
		if(erros != null && !erros.isEmpty()){
			Integer totalComProblema = erros.size();
			Integer totalSemProblema = total - totalComProblema;

			for(Integer sequencial : erros.keySet()){
				String listaProblema = (String) erros.get(sequencial);

				relatorioBean = new RelatorioProcessarArquivoInformarAnormalidadesLeituraBean(faturamentoGrupo, empresa, localidade,
								sequencial.toString(), listaProblema, total, totalSemProblema, totalComProblema, "problema");

				// adiciona o bean a coleção
				relatorioBeans.add(relatorioBean);
			}

		}
		if(advertencias != null && !advertencias.isEmpty()){
			Integer totalComAdvertencia = advertencias.size();
			Integer totalSemAdvertencia = total - advertencias.size();

			for(Integer sequencial : advertencias.keySet()){
				String listaAdvertencia = (String) advertencias.get(sequencial);

				relatorioBean = new RelatorioProcessarArquivoInformarAnormalidadesLeituraBean(faturamentoGrupo, empresa, localidade,
								sequencial.toString(), listaAdvertencia, total, totalSemAdvertencia, totalComAdvertencia, "advertência");

				// adiciona o bean a coleção
				relatorioBeans.add(relatorioBean);
			}
		}
		// __________________________________________________________________

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		// adiciona o laudo da análise

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_PROCESSAR_ARQUIVO_INFORMAR_ANORMALIDADES_LEITURA, parametros, ds,
						TarefaRelatorio.TIPO_PDF);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_PROCESSAR_ARQUIVO_INFORMAR_ANORMALIDADES_LEITURA,
							idFuncionalidadeIniciada, "Relatorio Informar Anor. Leitura");
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;
	}

	@Override
	public void agendarTarefaBatch(){

		// TODO Auto-generated method stub

	}

}
