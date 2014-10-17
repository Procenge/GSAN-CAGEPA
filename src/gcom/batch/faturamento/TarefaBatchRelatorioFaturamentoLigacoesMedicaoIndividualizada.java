
package gcom.batch.faturamento;

import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaBatch;
import gcom.tarefa.TarefaException;
import gcom.util.ConstantesJNDI;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.Collection;

/**
 * @author Ado Rocha
 * @date 07/11/2013
 */
public class TarefaBatchRelatorioFaturamentoLigacoesMedicaoIndividualizada
				extends TarefaBatch {

	public TarefaBatchRelatorioFaturamentoLigacoesMedicaoIndividualizada() {

		super(null, 0);
	}

	public TarefaBatchRelatorioFaturamentoLigacoesMedicaoIndividualizada(Usuario usuario, int idFuncionalidadeIniciada) {

		super(usuario, idFuncionalidadeIniciada);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Collection<Object> pesquisarTodasUnidadeProcessamentoBatch(){

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Collection<Object> pesquisarTodasUnidadeProcessamentoReinicioBatch(){

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object executar() throws TarefaException{

		enviarMensagemControladorBatch(ConstantesJNDI.BATCH_RELATORIO_FATURAMENTO_LIGACOES_MEDICAO_INDIVIDUALIZADA_MDB,
						new Object[] {this.getIdFuncionalidadeIniciada()});

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioFaturamentoLigacoesMedicaoIndividualizada", this);

	}

}
