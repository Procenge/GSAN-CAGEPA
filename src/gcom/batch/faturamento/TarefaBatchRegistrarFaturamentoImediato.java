/* This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 *
 * GSANPCG
 * Eduardo Henrique
 * 
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.batch.faturamento;

import gcom.faturamento.FaturamentoGrupo;
import gcom.micromedicao.Rota;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaBatch;
import gcom.tarefa.TarefaException;
import gcom.util.ConstantesJNDI;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * [UC0088] - Registrar Faturamento Imediato
 * 
 * @author eduardo henrique
 * @date 08/10/2008
 * @since v0.05
 *        Tarefa Batch responsável por realizar um processo de Atualização de Faturamentos Imediatos
 *        de um Grupo Faturamento / Ano-Mês de Referência.
 */
public class TarefaBatchRegistrarFaturamentoImediato
				extends TarefaBatch {

	/**
	 * @param usuario
	 * @param idFuncionalidadeIniciada
	 */
	public TarefaBatchRegistrarFaturamentoImediato(Usuario usuario, int idFuncionalidadeIniciada) {

		super(usuario, idFuncionalidadeIniciada);
		// TODO Auto-generated constructor stub
	}

	@Deprecated
	public TarefaBatchRegistrarFaturamentoImediato() {

		super(null, 0);
	}

	/*
	 * @see gcom.tarefa.TarefaBatch#pesquisarTodasUnidadeProcessamentoBatch()
	 */
	@Override
	protected Collection<Object> pesquisarTodasUnidadeProcessamentoBatch(){

		// TODO Auto-generated method stub
		return null;
	}

	/*
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

		AgendadorTarefas.agendarTarefa("RegistrarFaturamentoImediatoBatch", this);

	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#executar()
	 */
	@Override
	public Object executar() throws TarefaException{

		Integer anoMesFaturamentoGrupo = (Integer) getParametro("anoMesFaturamentoGrupo");

		FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) getParametro("faturamentoGrupo");

		Collection colecaoRotasParaExecucao = (Collection) getParametro("rotas");

		Integer atividade = (Integer) getParametro("atividade");

		Iterator iterator = colecaoRotasParaExecucao.iterator();
		Collection colecaoRotasAposCast = new ArrayList();

		while(iterator.hasNext()){
			Object[] array = (Object[]) iterator.next();

			Rota rota = (Rota) array[1];

			System.out.println("Rota " + rota.getId() + "*********************************************************");

			colecaoRotasAposCast.add(rota);

			// enviarMensagemControladorBatch(ConstantesJNDI.BATCH_REGISTRAR_FATURAMENTO_IMEDIATO_MDB,
			// new Object[] {Collections
			// .singletonList(rota), anoMesFaturamentoGrupo, faturamentoGrupo,
			// this.getIdFuncionalidadeIniciada(), atividade});
		}

		enviarMensagemControladorBatch(
						ConstantesJNDI.BATCH_REGISTRAR_FATURAMENTO_IMEDIATO_MDB,
						new Object[] {colecaoRotasAposCast, anoMesFaturamentoGrupo, faturamentoGrupo, this.getIdFuncionalidadeIniciada(), atividade});

		return null;
	}

}
