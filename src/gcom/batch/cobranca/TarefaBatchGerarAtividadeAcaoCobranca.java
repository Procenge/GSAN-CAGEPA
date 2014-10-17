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
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
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

package gcom.batch.cobranca;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.*;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaBatch;
import gcom.tarefa.TarefaException;
import gcom.util.ConstantesJNDI;
import gcom.util.ConstantesSistema;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * Tarefa que manda para batch Gerar Atividade de Ação de Cobrança
 * 
 * @author Rodrigo Silveira
 * @created 17/11/2006
 */
public class TarefaBatchGerarAtividadeAcaoCobranca
				extends TarefaBatch {

	private static final long serialVersionUID = 1L;

	public TarefaBatchGerarAtividadeAcaoCobranca(Usuario usuario, int idFuncionalidadeIniciada) {

		super(usuario, idFuncionalidadeIniciada);
	}

	@Deprecated
	public TarefaBatchGerarAtividadeAcaoCobranca() {

		super(null, 0);
	}

	public Object executar() throws TarefaException{

		CobrancaGrupo grupoCobranca = (CobrancaGrupo) getParametro("grupoCobranca");
		Integer anoMesReferenciaCicloCobranca = (Integer) getParametro("anoMesReferenciaCicloCobranca");
		CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma = (CobrancaAcaoAtividadeCronograma) getParametro("comandoAtividadeAcaoCobranca");
		CobrancaAcaoAtividadeComando comandoAtividadeAcaoComando = (CobrancaAcaoAtividadeComando) getParametro("comandoAtividadeAcaoComando");
		CobrancaAcao acaoCobranca = (CobrancaAcao) getParametro("acaoCobranca");
		CobrancaAtividade atividadeCobranca = (CobrancaAtividade) getParametro("atividadeCobranca");
		Short indicadorCriterio = (Short) getParametro("indicadorCriterio");
		// Collection colecaoRota = (Collection) getParametro("colecaoRotas");
		CobrancaCriterio cobrancaCriterio = (CobrancaCriterio) getParametro("criterioCobranca");
		Cliente cliente = (Cliente) getParametro("cliente");
		Cliente clienteSuperior = (Cliente) getParametro("clienteSuperior");
		ClienteRelacaoTipo clienteRelacaoTipo = (ClienteRelacaoTipo) getParametro("clienteRelacaoTipo");

		Integer anoMesReferenciaInicial = (Integer) getParametro("anoMesReferenciaInicial");
		Integer anoMesReferenciaFinal = (Integer) getParametro("anoMesReferenciaFinal");
		Date dataVencimentoInicial = (Date) getParametro("dataVencimentoInicial");
		Date dataVencimentoFinal = (Date) getParametro("dataVencimentoFinal");
		Date dataAtual = (Date) getParametro("dataAtual");
		Usuario usuario = (Usuario) getParametro("usuario");
		SistemaParametro sistemaParametros = (SistemaParametro) getParametro("sistemaParametros");
		Integer idFaturamentoGrupoCronogramaMensal = (Integer) getParametro("idFaturamentoGrupoCronogramaMensal");

		// --------------------------------------------------------------------------------------------------------------
		// CASO BATCH EM PARALELO POR SETOR COMERCIAL
		// --------------------------------------------------------------------------------------------------------------
		Collection colecaoSetorComercial = (Collection) getParametro(ConstantesSistema.COLECAO_UNIDADES_PROCESSAMENTO_BATCH);

		Iterator iterator = colecaoSetorComercial.iterator();

		while(iterator.hasNext()){
			Integer idSetorComercial = (Integer) iterator.next();
			enviarMensagemControladorBatch(
							ConstantesJNDI.BATCH_GERAR_ATIVIDADE_ACAO_COBRANCA_MDB,
							new Object[] {grupoCobranca, anoMesReferenciaCicloCobranca, cobrancaAcaoAtividadeCronograma, comandoAtividadeAcaoComando, idSetorComercial, acaoCobranca, atividadeCobranca, indicadorCriterio
											.intValue(), cobrancaCriterio, cliente, clienteRelacaoTipo, anoMesReferenciaInicial == null ? ""
											: anoMesReferenciaInicial.toString(), anoMesReferenciaFinal == null ? ""
											: anoMesReferenciaFinal.toString(), dataVencimentoInicial, dataVencimentoFinal, dataAtual, this
											.getIdFuncionalidadeIniciada(), clienteSuperior, usuario, sistemaParametros, idFaturamentoGrupoCronogramaMensal});

		}
		// --------------------------------------------------------------------------------------------------------------

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

		AgendadorTarefas.agendarTarefa("GerarAtividadeAcaoCobrancaBatch", this);
	}

}
