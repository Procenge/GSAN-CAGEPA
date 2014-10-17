
package gcom.batch.faturamento;

import gcom.faturamento.FaturamentoGrupo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaBatch;
import gcom.tarefa.TarefaException;
import gcom.util.ConstantesJNDI;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.Collection;

/**
 * Cancelar Aviso de Corte Pendente
 * 
 * @author Hebert Falcão
 * @date 11/09/2013
 */
public class TarefaBatchCancelarAvisoDeCortePendente
				extends TarefaBatch {

	private static final long serialVersionUID = 1L;

	public TarefaBatchCancelarAvisoDeCortePendente(Usuario usuario, int idFuncionalidadeIniciada) {

		super(usuario, idFuncionalidadeIniciada);
	}

	@Deprecated
	public TarefaBatchCancelarAvisoDeCortePendente() {

		super(null, 0);
	}

	public Object executar() throws TarefaException{

		FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) getParametro("faturamentoGrupo");

		enviarMensagemControladorBatch(ConstantesJNDI.BATCH_CANCELAR_AVISO_DE_CORTE_PENDENTE_MDB,
						new Object[] {this.getIdFuncionalidadeIniciada(), faturamentoGrupo});

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

		AgendadorTarefas.agendarTarefa("CancelarAvisoDeCortePendente", this);
	}

}
