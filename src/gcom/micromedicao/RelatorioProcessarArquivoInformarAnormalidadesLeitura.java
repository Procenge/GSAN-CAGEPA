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
 * @author P�ricles Tavares
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

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioProcessarArquivoInformarAnormalidadesLeituraBean relatorioBean = null;

		// se a cole��o de par�metros da analise n�o for vazia
		if(erros != null && !erros.isEmpty()){
			Integer totalComProblema = erros.size();
			Integer totalSemProblema = total - totalComProblema;

			for(Integer sequencial : erros.keySet()){
				String listaProblema = (String) erros.get(sequencial);

				relatorioBean = new RelatorioProcessarArquivoInformarAnormalidadesLeituraBean(faturamentoGrupo, empresa, localidade,
								sequencial.toString(), listaProblema, total, totalSemProblema, totalComProblema, "problema");

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			}

		}
		if(advertencias != null && !advertencias.isEmpty()){
			Integer totalComAdvertencia = advertencias.size();
			Integer totalSemAdvertencia = total - advertencias.size();

			for(Integer sequencial : advertencias.keySet()){
				String listaAdvertencia = (String) advertencias.get(sequencial);

				relatorioBean = new RelatorioProcessarArquivoInformarAnormalidadesLeituraBean(faturamentoGrupo, empresa, localidade,
								sequencial.toString(), listaAdvertencia, total, totalSemAdvertencia, totalComAdvertencia, "advert�ncia");

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			}
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_PROCESSAR_ARQUIVO_INFORMAR_ANORMALIDADES_LEITURA, parametros, ds,
						TarefaRelatorio.TIPO_PDF);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_PROCESSAR_ARQUIVO_INFORMAR_ANORMALIDADES_LEITURA,
							idFuncionalidadeIniciada, "Relatorio Informar Anor. Leitura");
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public void agendarTarefaBatch(){

		// TODO Auto-generated method stub

	}

}
