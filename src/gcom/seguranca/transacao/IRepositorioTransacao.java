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

import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 * @created 22 de Julho de 2005
 */
public interface IRepositorioTransacao {

	public Collection pesquisarUsuarioAlteracaoDasOperacoesEfetuadasHql(Integer idUsuarioAcao, String[] idOperacoes, String idUsuario,
					Date dataInicial, Date dataFinal, Date horaInicial, Date horaFinal, Hashtable<Integer, String> argumentos, Integer id1,
					Integer numeroPagina) throws ErroRepositorioException;

	public Integer pesquisarUsuarioAlteracaoDasOperacoesEfetuadasHqlCount(Integer idUsuarioAcao, String[] idOperacoes, String idUsuario,
					Date dataInicial, Date dataFinal, Date horaInicial, Date horaFinal, Hashtable<Integer, String> argumentos, Integer id1)
					throws ErroRepositorioException;

	public Collection pesquisarUsuarioAlteracaoDasOperacoesEfetuadasHqlRelatorio(Integer idUsuarioAcao, String[] idOperacoes,
					String idUsuario, Date dataInicial, Date dataFinal, Date horaInicial, Date horaFinal,
					Hashtable<Integer, String> argumentos, Integer id1) throws ErroRepositorioException;

	public Collection pesquisarOperacaoOrdemExibicao(int[] idTabelaColuna, int idOperacao) throws ErroRepositorioException;

	public Collection pesquisarEntidadeOperacoesEfetuadasHql(Integer chaveEntidade, Integer numeroPagina) throws ErroRepositorioException;

	/**
	 * Pesquisar Argumento
	 * 
	 * @author Saulo Lima
	 * @date 25/10/2012
	 * @param idOperacao
	 * @param nomeObjeto
	 * @return idArgumento
	 * @exception ErroRepositorioException
	 */
	public Integer pesquisarArgumento(Integer idOperacao, String nomeObjeto) throws ErroRepositorioException;

	/**
	 * [UC3124] - Auditoria de Cancelamento/Revisão de Contas
	 * 
	 * @author Ado Rocha
	 * @date 20/01/2014
	 */
	public Collection pesquisarAuditoriaRevisaoCancelamentoContaFuncionario(Integer idFuncionario, Integer dataInicial, Integer dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3124] - Auditoria de Cancelamento/Revisão de Contas
	 * 
	 * @author Ado Rocha
	 * @date 20/01/2014
	 */
	public Collection pesquisarAuditoriaRevisaoCancelamentoContaUsuario(Integer idUsuario, Integer dataInicial, Integer dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3123] - Auditoria de Retificação/Implantação de Contas
	 * 
	 * @author Ado Rocha
	 * @date 20/01/2014
	 */
	public Collection pesquisarRetificacaoImplantacaoContaFuncionario(Integer idFuncionario, Integer dataInicial, Integer dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3123] - Auditoria de Retificação/Implantação de Contas
	 * 
	 * @author Ado Rocha
	 * @date 20/01/2014
	 */
	public Collection pesquisarRetificacaoImplantacaoContaUsuario(Integer idUsuario, Integer dataInicial, Integer dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3125] - Auditoria de Parcelamento de Contas
	 * 
	 * @author Ado Rocha
	 * @date 20/01/2014
	 */
	public Collection pesquisarParcelamentoContaFuncionario(Integer idFuncionario, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3125] - Auditoria de Parcelamento de Contas
	 * 
	 * @author Ado Rocha
	 * @date 20/01/2014
	 */
	public Collection pesquisarParcelamentoContaUsuario(Integer idUsuario, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException;


	/**
	 * [UC3122] Auditoria de Alterações de Clientes
	 * 
	 * @author Eduardo Oliveira
	 * @throws ControladorException
	 * @date 01/03/2014
	 */
	public Collection consultarAlteracoesClientesFuncionario(Integer idFuncionario, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3122] Auditoria de Alterações de Clientes
	 * 
	 * @author Eduardo Oliveira
	 * @throws ControladorException
	 * @date 01/03/2014
	 */
	public Collection consultarAlteracoesClientesUsuario(Integer idUsuario, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3127] - Auditoria de TransferÃªncia de DÃ©bitos
	 * [SB0001] €- Apresenta Dados da Auditoria
	 * Consulta por Funcionario
	 * 
	 * @author Eduardo Oliveira
	 * @date 01/02/2014
	 */
	public Collection consultarAuditoriaTransferenciaDebitosFuncionario(Integer idFuncionario, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3127] - Auditoria de Transferência de Débitos
	 * [SB0001] -€“ Apresenta Dados da Auditoria
	 * Consulta por Usuario
	 * 
	 * @author Eduardo Oliveira
	 * @date 01/02/2014
	 */
	public Collection consultarAuditoriaTransferenciaDebitosUsuario(Integer idUsuarioOrigem, Integer idUsuarioDestino, Date dataFinal,
					Date dataInicial)
					throws ErroRepositorioException;

	/**
	 * [UC3126] Auditoria de Serviços a Cobrar
	 * 
	 * @author Eduardo Oliveira
	 * @throws ControladorException
	 * @date 08/03/2014
	 */
	public Collection consultarServicosACobrarFuncionario(Integer idFuncionario, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3126] Auditoria de Serviços a Cobrar
	 * 
	 * @author Eduardo Oliveira
	 * @throws ControladorException
	 * @date 08/03/2014
	 */
	public Collection consultarServicosACobrarUsuario(Integer idUsuario, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException;


}
