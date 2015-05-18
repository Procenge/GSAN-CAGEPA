package gcom.batch.cadastro;

import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaBatch;
import gcom.tarefa.TarefaException;
import gcom.util.ConstantesJNDI;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.Collection;


/**
 * <p>
 * [OC1366820]
 * </p>
 * 
 * @author Magno Silveira { @literal <magno.silveira@procenge.com.br> }
 * @since 07/10/2014
 */
public class TarefaBatchSupressaoDefinitivaImoveis
				extends TarefaBatch {

	public TarefaBatchSupressaoDefinitivaImoveis(Usuario usuario, int idFuncionalidadeIniciada) {

		super(usuario, idFuncionalidadeIniciada);
	}

	@Deprecated
	public TarefaBatchSupressaoDefinitivaImoveis() {

		super(null, 0);
	}

	@Override
	protected Collection<Object> pesquisarTodasUnidadeProcessamentoBatch(){

		return null;
	}

	@Override
	protected Collection<Object> pesquisarTodasUnidadeProcessamentoReinicioBatch(){

		return null;
	}

	@Override
	public Object executar() throws TarefaException{
		
		enviarMensagemControladorBatch(ConstantesJNDI.BATCH_SUPRESSAO_DEFINITIVA_IMOVEIS_MDB,
						new Object[] {this.getIdFuncionalidadeIniciada()});
		return null;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("SupressaoDefinitivaImoveis", this);
	}

}
