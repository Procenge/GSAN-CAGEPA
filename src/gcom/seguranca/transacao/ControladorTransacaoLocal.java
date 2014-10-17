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

package gcom.seguranca.transacao;

import gcom.interceptor.ObjetoTransacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.util.ControladorException;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Declaração pública de serviços do Session Bean de ControladorTransacao
 * 
 * @author Thiago Toscano
 * @created 09 de Fevereiro de 2005
 */
public interface ControladorTransacaoLocal
				extends javax.ejb.EJBLocalObject {

	/**
	 * Método que registra uma operacao ao sistema
	 * 
	 * @param operacaoEfetuada
	 * @param tabelaLinhaAlteracao
	 * @param tabelaLinhaColunaAlteracoes
	 * @throws ControladorException
	 */
	public void inserirOperacaoEfetuada(Collection usuariosAcaoUsuarioHelp, OperacaoEfetuada operacaoEfetuada,
					TabelaLinhaAlteracao tabelaLinhaAlteracao, Collection<TabelaLinhaColunaAlteracao> tabelaLinhaColunaAlteracoes,
					String nomeObjeto) throws ControladorException;

	/**
	 * Método que consulta os usuario alteracao de uma determinada operacao com as restricoes
	 * passadas
	 * 
	 * @param idOperacao
	 * @param idUsuario
	 * @param dataInicial
	 * @param dataFinal
	 * @param horaInicial
	 * @param hotaFinal
	 * @param idTabela
	 * @param idTabelaColuna
	 * @param id1
	 * @param numeroPagina
	 * @return
	 * @throws ControladorException
	 * @author Rômulo Aurélio / Rafael Correa
	 * @date 26/04/2007
	 */
	public Collection pesquisarUsuarioAlteracaoDasOperacoesEfetuadasHql(Integer idUsuarioAcao, String[] idOperacoes, String idUsuario,
					Date dataInicial, Date dataFinal, Date horaInicial, Date horaFinal, Hashtable<Integer, String> argumentos, Integer id1,
					Integer numeroPagina) throws ControladorException;

	// Método que retorna a colecao de alterações de acordo com a chave da entidade passada.
	public Collection pesquisarEntidadeOperacoesEfetuadasHql(Integer chaveEntidade, Integer numeroPagina) throws ControladorException;

	public Integer pesquisarUsuarioAlteracaoDasOperacoesEfetuadasHqlCount(Integer idUsuarioAcao, String[] idOperacoes, String idUsuario,
					Date dataInicial, Date dataFinal, Date horaInicial, Date horaFinal, Hashtable<Integer, String> argumentos, Integer id1)
					throws ControladorException;

	public Collection pesquisarUsuarioAlteracaoDasOperacoesEfetuadasHqlRelatorio(Integer idUsuarioAcao, String[] idOperacoes,
					String idUsuario, Date dataInicial, Date dataFinal, Date horaInicial, Date horaFinal,
					Hashtable<Integer, String> argumentos, Integer id1) throws ControladorException;

	public void registrarTransacao(ObjetoTransacao objetoTransacao) throws ControladorException;

	public HashMap consultarResumoInformacoesOperacaoEfetuada(OperacaoEfetuada operacaoEfetuada, int idItemAnalisado)
					throws ControladorException;

	public void ordenarTabelaLinhaColunaAlteracao(Collection linhas, int idOperacao) throws ControladorException;

	/**
	 * Pesquisar Argumento
	 * 
	 * @author Saulo Lima
	 * @date 25/10/2012
	 * @param idOperacao
	 * @param nomeObjeto
	 * @return idArgumento
	 * @exception ControladorException
	 */
	public Integer pesquisarArgumento(Integer idOperacao, String nomeObjeto) throws ControladorException;

	/**
	 * [UC3124] - Auditoria de Cancelamento/Revisão de Contas
	 * 
	 * @author Ado Rocha
	 * @date 20/01/2014
	 */
	public Collection pesquisarAuditoriaRevisaoCancelamentoContaFuncionario(Integer idFuncionario, Integer dataInicial, Integer dataFinal)
					throws ControladorException;

	/**
	 * [UC3124] - Auditoria de Cancelamento/Revisão de Contas
	 * 
	 * @author Ado Rocha
	 * @date 20/01/2014
	 */
	public Collection pesquisarAuditoriaRevisaoCancelamentoContaUsuario(Integer idUsuario, Integer dataInicial, Integer dataFinal)
					throws ControladorException;

	/**
	 * [UC3123] - Auditoria de Retificação/Implantação de Contas
	 * 
	 * @author Ado Rocha
	 * @date 20/01/2014
	 */
	public Collection pesquisarRetificacaoImplantacaoContaFuncionario(Integer idFuncionario, Integer dataInicial, Integer dataFinal)
					throws ControladorException;

	/**
	 * [UC3123] - Auditoria de Retificação/Implantação de Contas
	 * 
	 * @author Ado Rocha
	 * @date 20/01/2014
	 */
	public Collection pesquisarRetificacaoImplantacaoContaUsuario(Integer idUsuario, Integer dataInicial, Integer dataFinal)
					throws ControladorException;

	/**
	 * [UC3125] - Auditoria de Parcelamento de Contas
	 * 
	 * @author Ado Rocha
	 * @date 20/01/2014
	 */
	public Collection pesquisarParcelamentoContaFuncionario(Integer idFuncionario, Date dataInicial, Date dataFinal)
					throws ControladorException;

	/**
	 * [UC3125] - Auditoria de Parcelamento de Contas
	 * 
	 * @author Ado Rocha
	 * @date 20/01/2014
	 */
	public Collection pesquisarParcelamentoContaUsuario(Integer idUsuario, Date dataInicial, Date dataFinal)
					throws ControladorException;


	/**
	 * [UC3122] Auditoria de Alterações de Clientes
	 * 
	 * @author Eduardo Oliveira
	 * @throws ControladorException
	 * @date 01/03/2014
	 */
	public Collection consultarAlteracoesClientesFuncionario(Integer idFuncionario, Date dataInicial, Date dataFinal)
					throws ControladorException;

	/**
	 * [UC3122] Auditoria de Alterações de Clientes
	 * 
	 * @author Eduardo Oliveira
	 * @throws ControladorException
	 * @date 01/03/2014
	 */
	public Collection consultarAlteracoesClientesUsuario(Integer idUsuario, Date dataInicial, Date dataFinal) throws ControladorException;

	/**
	 * [UC3127] - Auditoria de Transferencia de Debitos
	 * [SB0001] - Apresenta Dados da Auditoria
	 * Consulta por Funcionário
	 * 
	 * @author Eduardo Oliveira
	 * @date 01/02/2014
	 */
	public Collection consultarAuditoriaTransferenciaDebitosFuncionario(Integer idFuncionario, Date dataInicial, Date dataFinal)
					throws ControladorException;

	/**
	 * [UC3127] - Auditoria de Transferencia de Debitos
	 * [SB0001] - Apresenta Dados da Auditoria
	 * Consulta por usuario
	 * 
	 * @author Eduardo Oliveira
	 * @date 01/02/2014
	 */
	public Collection consultarAuditoriaTransferenciaDebitosUsuario(Integer idUsuarioOrigem, Integer idUsuarioDestino, Date dataInicial,
					Date dataFinal) throws ControladorException;

	/**
	 * [UC3126] Auditoria de Serviços a Cobrar
	 * 
	 * @author Eduardo Oliveira
	 * @date 10/03/2014
	 */
	public Collection consultarServicosACobrarUsuario(Integer idUsuario, Date dataInicial, Date dataFinal)
					throws ControladorException;

	/**
	 * [UC3126] Auditoria de Serviços a Cobrar
	 * 
	 * @author Eduardo Oliveira
	 * @date 10/03/2014
	 */
	public Collection consultarServicosACobrarFuncionario(Integer idFuncionario, Date dataInicial, Date dataFinal)
					throws ControladorException;
}
