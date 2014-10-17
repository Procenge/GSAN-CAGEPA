
package gcom.batch.arrecadacao;

import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaBatch;
import gcom.tarefa.TarefaException;
import gcom.util.ConstantesJNDI;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.Collection;

/**
 * Ajustar arrecacador movimento item - código de barras inválido
 * 
 * @author Hebert Falcão
 * @date 11/12/2013
 */
public class TarefaBatchAjustarArrecadadorMovimentoItem
				extends TarefaBatch {

	private static final long serialVersionUID = 1L;

	public TarefaBatchAjustarArrecadadorMovimentoItem(Usuario usuario, int idFuncionalidadeIniciada) {

		super(usuario, idFuncionalidadeIniciada);
	}

	@Deprecated
	public TarefaBatchAjustarArrecadadorMovimentoItem() {

		super(null, 0);
	}

	public Object executar() throws TarefaException{

		enviarMensagemControladorBatch(ConstantesJNDI.BATCH_AJUSTAR_ARRECADADOR_MOVIMENTO_ITEM_MDB,
						new Object[] {this.getIdFuncionalidadeIniciada()});

		return null;
	}

	@Override
	public Collection pesquisarTodasUnidadeProcessamentoBatch(){

		return null;
	}

	@Override
	public Collection pesquisarTodasUnidadeProcessamentoReinicioBatch(){

		return null;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("AjustarArrecadadorMovimentoItem", this);
	}

}
