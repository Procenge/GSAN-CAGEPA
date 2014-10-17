
package gcom.batch.cobranca;

import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaBatch;
import gcom.tarefa.TarefaException;
import gcom.util.ConstantesJNDI;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.Collection;

/**
 * [UC3089] Atualizar Situação Débito e da Ação dos Avisos Corte e Corte Individual
 * 
 * @author Hebert Falcão
 * @date 14/12/2012
 */
public class TarefaBatchAtualizarSituacaoDebitoEDaAcaoDosAvisosCorteECorteIndividual
				extends TarefaBatch {

	private static final long serialVersionUID = 1L;

	public TarefaBatchAtualizarSituacaoDebitoEDaAcaoDosAvisosCorteECorteIndividual(Usuario usuario, int idFuncionalidadeIniciada) {

		super(usuario, idFuncionalidadeIniciada);
	}

	@Deprecated
	public TarefaBatchAtualizarSituacaoDebitoEDaAcaoDosAvisosCorteECorteIndividual() {

		super(null, 0);
	}

	public Object executar() throws TarefaException{

		Integer idProcessoIniciado = (Integer) getParametro("idProcessoIniciado");

		enviarMensagemControladorBatch(ConstantesJNDI.BATCH_ATUALIZAR_SITUACAO_DEBITO_E_DA_ACAO_DOS_AVISOS_CORTE_E_CORTE_INDIVIDUAL_MDB,
						new Object[] {this.getIdFuncionalidadeIniciada(), idProcessoIniciado});

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

		AgendadorTarefas.agendarTarefa("AtualizarSituacaoDebitoEDaAcaoDosAvisosCorteECorteIndividualBatch", this);
	}

}
