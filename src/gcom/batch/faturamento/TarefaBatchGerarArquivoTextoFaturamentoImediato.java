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

import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaBatch;
import gcom.tarefa.TarefaException;
import gcom.util.ConstantesJNDI;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.Collection;

/**
 * [UC3012] -Gerar Arquivo Texto Faturamento Imediato
 * 
 * @author Yara Souza
 * @date 06/10/2011
 */
public class TarefaBatchGerarArquivoTextoFaturamentoImediato
				extends TarefaBatch {

	/**
	 * @param usuario
	 * @param idFuncionalidadeIniciada
	 */
	public TarefaBatchGerarArquivoTextoFaturamentoImediato(Usuario usuario, int idFuncionalidadeIniciada) {

		super(usuario, idFuncionalidadeIniciada);
		// TODO Auto-generated constructor stub
	}

	@Deprecated
	public TarefaBatchGerarArquivoTextoFaturamentoImediato() {

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

		AgendadorTarefas.agendarTarefa("GerarArquivoTextoFaturamentoImediatoBatch", this);

	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#executar()
	 */
	@Override
	public Object executar() throws TarefaException{

		Integer anoMesFaturamentoGrupo = (Integer) getParametro("anoMesFaturamentoGrupo");

		Integer idFaturamentoGrupo = (Integer) getParametro("idFaturamentoGrupo");

		Collection colecaoRotasParaExecucao = (Collection) getParametro("rotas");

		// Iterator iterator = colecaoRotasParaExecucao.iterator();

		// while(iterator.hasNext()){
		// Object[] array = (Object[]) iterator.next();
		//
		// Rota rota = (Rota) array[1];
		//
		// System.out.println("Rota " + rota.getId() +
		// "*********************************************************");

		enviarMensagemControladorBatch(ConstantesJNDI.BATCH_GERAR_ARQUIVO_TEXTO_FATURAMENTO_IMEDIATO_MDB,
						new Object[] {colecaoRotasParaExecucao, anoMesFaturamentoGrupo, idFaturamentoGrupo, this
										.getIdFuncionalidadeIniciada()});
		// }

		return null;
	}

}
