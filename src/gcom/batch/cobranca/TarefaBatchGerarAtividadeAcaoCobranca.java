/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
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
 * Tarefa que manda para batch Gerar Atividade de A��o de Cobran�a
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
