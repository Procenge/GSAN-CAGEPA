
package gcom.relatorio.gerencial.micromedicao;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.micromedicao.bean.AnormalidadeEntidadeControleDetalheHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.micromedicao.RelatorioResumoAnormalidadesConsumoBean;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatorioResumoAnormalidadesConsumo
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioResumoAnormalidadesConsumo() {

		super(null, ConstantesRelatorios.RELATORIO_RESUMO_ANORMALIDADE_CONSUMO);
	}

	public RelatorioResumoAnormalidadesConsumo(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_RESUMO_ANORMALIDADE_CONSUMO);
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		String descricaoArquivo = null;
		if(getParametro("descricaoArquivo") != null){
			descricaoArquivo = (String) getParametro("descricaoArquivo");
		}

		ArrayList<AnormalidadeEntidadeControleDetalheHelper> listaDetalheHelper = (ArrayList<AnormalidadeEntidadeControleDetalheHelper>) getParametro("listaDetalheHelper");
		List<RelatorioResumoAnormalidadesConsumoBean> listaRelatorioBeans = new ArrayList<RelatorioResumoAnormalidadesConsumoBean>();

		for(AnormalidadeEntidadeControleDetalheHelper detalheHelper : listaDetalheHelper){
			listaRelatorioBeans.add(new RelatorioResumoAnormalidadesConsumoBean(detalheHelper));
		}

		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		RelatorioDataSource ds = new RelatorioDataSource(listaRelatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_RESUMO_ANORMALIDADE_CONSUMO, parametros, ds, tipoFormatoRelatorio);

		// Grava o relatório no sistema
		try{
			this.persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_RESUMO_ANORMALIDADES_CONSUMO, idFuncionalidadeIniciada,
							descricaoArquivo);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno;
	}

	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#agendarTarefaBatch()
	 */
	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioResumoAnormalidadesConsumo", this);
	}
}
