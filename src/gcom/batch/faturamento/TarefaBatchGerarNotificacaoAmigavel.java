
package gcom.batch.faturamento;

import gcom.faturamento.FaturamentoAtivCronRota;
import gcom.faturamento.FaturamentoAtividade;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FaturamentoGrupoCronogramaMensal;
import gcom.micromedicao.Rota;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaBatch;
import gcom.tarefa.TarefaException;
import gcom.util.ConstantesJNDI;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class TarefaBatchGerarNotificacaoAmigavel
				extends TarefaBatch {

	public TarefaBatchGerarNotificacaoAmigavel(Usuario usuario, int idFuncionalidadeIniciada) {

		super(usuario, idFuncionalidadeIniciada);
	}

	@Deprecated
	public TarefaBatchGerarNotificacaoAmigavel() {

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

		Collection<Rota> colecaoRotas = new ArrayList();

		if(colecaoFaturamentoAtivCronRota != null && !colecaoFaturamentoAtivCronRota.isEmpty()){
			Iterator it = colecaoFaturamentoAtivCronRota.iterator();
			while(it.hasNext()){
				
				Object[] arrayFaturamentoAtivCronRota = (Object[]) it.next();
				FaturamentoAtivCronRota faturamentoAtivCronRotadalista = (FaturamentoAtivCronRota) arrayFaturamentoAtivCronRota[0];
				colecaoRotas.add(faturamentoAtivCronRotadalista.getRota());
			}
		}

		Object[] arrayFaturamentoAtivCronRota = (Object[]) Util.retonarObjetoDeColecao(colecaoFaturamentoAtivCronRota);
		FaturamentoAtivCronRota faturamentoAtivCronRota = (FaturamentoAtivCronRota) arrayFaturamentoAtivCronRota[0];

		this.enviarMensagemControladorBatch(
						ConstantesJNDI.BATCH_GERAR_NOTIFICACAO_AMIGAVEL_MDB,
						new Object[] {colecaoRotas, Collections
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

		AgendadorTarefas.agendarTarefa("CriarNotificacaoAmigavelBatch", this);
	}

}
