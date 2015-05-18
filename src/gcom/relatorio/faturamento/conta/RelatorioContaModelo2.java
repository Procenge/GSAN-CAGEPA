package gcom.relatorio.faturamento.conta;

import gcom.batch.Relatorio;
import gcom.fachada.Fachada;
import gcom.faturamento.ControladorFaturamento;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.bean.EmitirContaTipo2Helper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class RelatorioContaModelo2
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(ControladorFaturamento.class);

	private static final String key = "1";

	public RelatorioContaModelo2(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_CONTA_MODELO_2);
	}

	@Deprecated
	public RelatorioContaModelo2() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		String string = new String();

		synchronized(key){

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		// valor de retorno
		byte[] retorno = null;

		Collection<EmitirContaTipo2Helper> colecaoContaHelper = (Collection) getParametro("colecaoContaHelper");
		FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) getParametro("faturamentoGrupo");
		Integer anoMesReferencia = (Integer) getParametro("anoMesReferencia");
		String descricaoArquivo = (String) getParametro("descricaoArquivo");

		log.info("............Início Relatório Emitir Contas: " + descricaoArquivo);
		
		List<RelatorioContaModelo2Bean> relatorioBeans = Fachada.getInstancia().obterDadosRelatorioEmitirContasModelo2(faturamentoGrupo,
						anoMesReferencia, colecaoContaHelper);

		Map parametros = new HashMap();
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_CONTA_MODELO_2, parametros, ds, tipoFormatoRelatorio);

		try{

			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_CONTA, idFuncionalidadeIniciada, descricaoArquivo);
		}catch(ControladorException e){

			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		log.info("............Fim Relatório Emitir Contas: " + descricaoArquivo);

		return retorno;
		}

	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioContaModelo2", this);
	}

}
