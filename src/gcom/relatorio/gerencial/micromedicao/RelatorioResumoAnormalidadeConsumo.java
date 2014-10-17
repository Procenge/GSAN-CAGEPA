
package gcom.relatorio.gerencial.micromedicao;

import gcom.batch.Processo;
import gcom.batch.Relatorio;
import gcom.relatorio.ConstantesRelatorios;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.agendadortarefas.AgendadorTarefas;

/**
 * @author mgrb
 */
public class RelatorioResumoAnormalidadeConsumo
				extends RelatorioResumoAnormalidadeLeitura {

	public RelatorioResumoAnormalidadeConsumo() {

		super(null, ConstantesRelatorios.RELATORIO_RESUMO_ANORMALIDADE_CONSUMO, Processo.GERAR_RELATORIO_RESUMO_ANORMALIDADES_CONSUMO);
	}

	public RelatorioResumoAnormalidadeConsumo(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_RESUMO_ANORMALIDADE_CONSUMO, Processo.GERAR_RELATORIO_RESUMO_ANORMALIDADES_CONSUMO);
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#agendarTarefaBatch()
	 */
	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioResumoAnormalidadeConsumo", this);
	}

	/**
	 * Id do relatório unidade.
	 * 
	 * @return
	 */
	@Override
	protected int getIdRelatorio(){

		return Relatorio.RELATORIO_RESUMO_ANORMALIDADES_CONSUMO;
	}
}
