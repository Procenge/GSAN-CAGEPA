/**
 * 
 */

package gcom.batch.arrecadacao;

import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaBatch;
import gcom.tarefa.TarefaException;
import gcom.util.ConstantesJNDI;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.Collection;

/**
 * @author Bruno Ferreira dos Santos
 * @date 11/10/2011
 */
public class TarefaBatchAtualizarPDDParaEncerrarArrecadacao
				extends TarefaBatch {

	/**
	 * @param usuario
	 * @param idFuncionalidadeIniciada
	 */
	public TarefaBatchAtualizarPDDParaEncerrarArrecadacao(Usuario usuario, int idFuncionalidadeIniciada) {

		super(usuario, idFuncionalidadeIniciada);
		// TODO Auto-generated constructor stub
	}

	@Deprecated
	public TarefaBatchAtualizarPDDParaEncerrarArrecadacao() {

		super(null, 0);
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.TarefaBatch#pesquisarTodasUnidadeProcessamentoBatch()
	 */
	@Override
	protected Collection<Object> pesquisarTodasUnidadeProcessamentoBatch(){

		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.TarefaBatch#pesquisarTodasUnidadeProcessamentoReinicioBatch()
	 */
	@Override
	protected Collection<Object> pesquisarTodasUnidadeProcessamentoReinicioBatch(){

		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#agendarTarefaBatch()
	 */
	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("BatchAtualizarPDDParaEncerrarArrecadacaoMDB", this);

	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#executar()
	 */
	@Override
	public Object executar() throws TarefaException{

		enviarMensagemControladorBatch(ConstantesJNDI.BATCH_ATUALIZAR_PDD_PARA_ENCERRAR_ARRECADACAO_MDB, new Object[] {this
						.getIdFuncionalidadeIniciada(), null});

		return null;
	}

}
