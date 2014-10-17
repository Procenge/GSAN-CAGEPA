
package gcom.batch.faturamento;

import gcom.faturamento.FaturamentoAtivCronRota;
import gcom.faturamento.FaturamentoAtividade;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FaturamentoGrupoCronogramaMensal;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaBatch;
import gcom.tarefa.TarefaException;
import gcom.util.ConstantesJNDI;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.Collection;
import java.util.Collections;

public class TarefaBatchGerarAvisoCorte
				extends TarefaBatch {

	public TarefaBatchGerarAvisoCorte(Usuario usuario, int idFuncionalidadeIniciada) {

		super(usuario, idFuncionalidadeIniciada);
	}

	@Deprecated
	public TarefaBatchGerarAvisoCorte() {

		this(null, 0);
	}

	@Override
	public Object executar() throws TarefaException{

		Collection colecaoFaturamentoAtivCronRota = (Collection) getParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH);
		Integer anoMesFaturamentoGrupo = (Integer) getParametro("anoMesFaturamentoGrupo");
		FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) getParametro("faturamentoGrupo");
		FaturamentoAtividade faturamentoAtividade = (FaturamentoAtividade) getParametro("faturamentoAtividade");
		FaturamentoGrupoCronogramaMensal faturamentoGrupoCronogramaMensal = (FaturamentoGrupoCronogramaMensal) getParametro("faturamentoGrupoCronogramaMensal");
		Integer idProcessoIniciado = (Integer) getParametro("idProcessoIniciado");

		Object[] arrayFaturamentoAtivCronRota = (Object[]) Util.retonarObjetoDeColecao(colecaoFaturamentoAtivCronRota);
		FaturamentoAtivCronRota faturamentoAtivCronRota = (FaturamentoAtivCronRota) arrayFaturamentoAtivCronRota[0];

		this.enviarMensagemControladorBatch(ConstantesJNDI.BATCH_GERAR_AVISO_CORTE_MDB, new Object[] {Collections
						.singletonList(faturamentoAtivCronRota), anoMesFaturamentoGrupo, faturamentoGrupo, this
						.getIdFuncionalidadeIniciada(), faturamentoAtividade, faturamentoGrupoCronogramaMensal, idProcessoIniciado});

		return null;
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
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("CriarAvisoCorteBatch", this);
	}

}
