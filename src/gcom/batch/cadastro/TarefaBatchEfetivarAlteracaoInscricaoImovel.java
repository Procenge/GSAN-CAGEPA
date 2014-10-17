
package gcom.batch.cadastro;

import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaBatch;
import gcom.tarefa.TarefaException;
import gcom.util.ConstantesJNDI;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.Collection;

/**
 * [UC3090] Efetivar Alterar Inscrição de Imóvel
 * 
 * @author Luciano Galvão
 * @date 25/02/2013
 */
public class TarefaBatchEfetivarAlteracaoInscricaoImovel
				extends TarefaBatch {

	private static final long serialVersionUID = 1L;

	public TarefaBatchEfetivarAlteracaoInscricaoImovel(Usuario usuario, int idFuncionalidadeIniciada) {

		super(usuario, idFuncionalidadeIniciada);
	}

	@Deprecated
	public TarefaBatchEfetivarAlteracaoInscricaoImovel() {

		super(null, 0);
	}

	public Object executar() throws TarefaException{

		enviarMensagemControladorBatch(ConstantesJNDI.BATCH_EFETIVAR_ALTERACAO_INSCRICAO_IMOVEL_MDB,
						new Object[] {this.getIdFuncionalidadeIniciada(), this.getUsuario()});

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

		AgendadorTarefas.agendarTarefa("EfetivarAlteracaoInscricaoImovelBatch", this);
	}

}
