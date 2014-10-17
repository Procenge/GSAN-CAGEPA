
package gcom.batch.faturamento;

import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaBatch;
import gcom.tarefa.TarefaException;
import gcom.util.ConstantesJNDI;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.Collection;

/**
 * [UC3014] Emitir Declaração Anual Quitação Débitos
 * 
 * @author Hebert Falcão
 * @created 27/04/2013
 */
public class TarefaBatchEmitirDeclaracaoAnualQuitacaoDebitos
				extends TarefaBatch {

	private static final long serialVersionUID = 1L;

	public TarefaBatchEmitirDeclaracaoAnualQuitacaoDebitos(Usuario usuario, int idFuncionalidadeIniciada) {

		super(usuario, idFuncionalidadeIniciada);
	}

	@Deprecated
	public TarefaBatchEmitirDeclaracaoAnualQuitacaoDebitos() {

		super(null, 0);
	}

	public Object executar() throws TarefaException{

		Integer idFaturamentoGrupo = (Integer) getParametro("idFaturamentoGrupo");
		Integer anoBaseDeclaracaoInformado = (Integer) getParametro("anoBaseDeclaracaoInformado");

		enviarMensagemControladorBatch(ConstantesJNDI.BATCH_EMITIR_DECLARACAO_ANUAL_QUITACAO_DEBITOS_MDB,
						new Object[] {this.getIdFuncionalidadeIniciada(), idFaturamentoGrupo, anoBaseDeclaracaoInformado});

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

		AgendadorTarefas.agendarTarefa("EmitirDeclaracaoAnualQuitacaoDebitosBatch", this);
	}

}
