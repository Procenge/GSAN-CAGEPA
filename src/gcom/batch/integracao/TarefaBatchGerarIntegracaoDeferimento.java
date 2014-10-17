/*
 * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
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
 * 
 * GSANPCG
 * Eduardo Henrique
 */
package gcom.batch.integracao;

import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaBatch;
import gcom.tarefa.TarefaException;
import gcom.util.ConstantesJNDI;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.Collection;


/**
 * @author mgrb
 *
 */
public class TarefaBatchGerarIntegracaoDeferimento
				extends TarefaBatch {

	/**
	 * TarefaBatchGerarIntegracaoDeferimento
	 * <p>
	 * Esse método Constroi uma instância de TarefaBatchGerarIntegracaoDeferimento.
	 * </p>
	 * 
	 * @author Marlos Ribeiro
	 * @since 01/10/2012
	 * @deprecated - Somente para uso interno do mecanismo de batch.
	 */
	public TarefaBatchGerarIntegracaoDeferimento() {

		super(null, 0);
	}

	public TarefaBatchGerarIntegracaoDeferimento(Usuario usuario, int idFuncionalidadeIniciada) {

		super(usuario, idFuncionalidadeIniciada);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Collection<Object> pesquisarTodasUnidadeProcessamentoBatch(){
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Collection<Object> pesquisarTodasUnidadeProcessamentoReinicioBatch(){
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object executar() throws TarefaException{

		enviarMensagemControladorBatch(ConstantesJNDI.BATCH_GERAR_INTEGRACAO_DEFERIMENTO_MDB,
						new Object[] {this.getIdFuncionalidadeIniciada()});

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("GerarIntegracaoDeferimentoBatch", this);

	}

}
