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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 *
 * GSANPCG
 * Eduardo Henrique
 * 
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.batch.faturamento;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.faturamento.FaturamentoGrupo;
import gcom.micromedicao.Rota;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaBatch;
import gcom.tarefa.TarefaException;
import gcom.util.ConstantesJNDI;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * [UC0084] - Gerar Faturamento Imediato
 * 
 * @author eduardo henrique
 * @date 17/09/2008
 * @since v0.05
 *        Tarefa Batch respons�vel por realizar um processo de Faturamento Imediato
 *        de um Grupo Faturamento / Ano-M�s de Refer�ncia.
 */
public class TarefaBatchGerarFaturamentoImediato
				extends TarefaBatch {

	/**
	 * @param usuario
	 * @param idFuncionalidadeIniciada
	 */
	public TarefaBatchGerarFaturamentoImediato(Usuario usuario, int idFuncionalidadeIniciada) {

		super(usuario, idFuncionalidadeIniciada);
		// TODO Auto-generated constructor stub
	}

	@Deprecated
	public TarefaBatchGerarFaturamentoImediato() {

		super(null, 0);
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
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("GerarFaturamentoImediatoBatch", this);
	}

	@Override
	public Object executar() throws TarefaException{

		Integer anoMesFaturamentoGrupo = (Integer) getParametro("anoMesFaturamentoGrupo");

		FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) getParametro("faturamentoGrupo");

		Collection colecaoRotasParaExecucao = (Collection) getParametro("rotas");
		for(Iterator iteratorRotas = colecaoRotasParaExecucao.iterator(); iteratorRotas.hasNext();){
			Object[] array = (Object[]) iteratorRotas.next();

			Rota rota = (Rota) array[1];

			System.out.println("Rota " + rota.getId() + "************* Faturamento Imediato");

			enviarMensagemControladorBatch(ConstantesJNDI.BATCH_GERAR_FATURAMENTO_IMEDIATO_MDB, new Object[] {Collections
							.singletonList(rota), anoMesFaturamentoGrupo, faturamentoGrupo, this.getIdFuncionalidadeIniciada()});
		}

		return null;
	}

}