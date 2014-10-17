package gcom.batch.arrecadacao;

import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaBatch;
import gcom.tarefa.TarefaException;
import gcom.util.ConstantesJNDI;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.Collection;

public class TarefaBatchGerarRelatorioAcompanhamentoMovimentoArrecadadores
				extends TarefaBatch {


	public TarefaBatchGerarRelatorioAcompanhamentoMovimentoArrecadadores() {
		super(null, 0);
	}

	public TarefaBatchGerarRelatorioAcompanhamentoMovimentoArrecadadores(Usuario usuario, int idFuncionalidadeIniciada) {

		super(usuario, idFuncionalidadeIniciada);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Collection<Object> pesquisarTodasUnidadeProcessamentoBatch(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Collection<Object> pesquisarTodasUnidadeProcessamentoReinicioBatch(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object executar() throws TarefaException{

		// TODO Auto-generated method stub
		enviarMensagemControladorBatch(ConstantesJNDI.BATCH_RELATORIO_ACOMP_MOV_ARRECADADORES_MDB,
						new Object[] {this.getIdFuncionalidadeIniciada()});
		return null;
	}

	@Override
	public void agendarTarefaBatch(){

		// TODO Auto-generated method stub
		AgendadorTarefas.agendarTarefa("RelatorioAcompanhamentoMovimentoArrecadadores", this);

	}

}
