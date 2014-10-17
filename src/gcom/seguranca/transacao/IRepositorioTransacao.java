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

package gcom.seguranca.transacao;

import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;

/**
 * < <Descri��o da Classe>>
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
	 * [UC3124] - Auditoria de Cancelamento/Revis�o de Contas
	 * 
	 * @author Ado Rocha
	 * @date 20/01/2014
	 */
	public Collection pesquisarAuditoriaRevisaoCancelamentoContaFuncionario(Integer idFuncionario, Integer dataInicial, Integer dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3124] - Auditoria de Cancelamento/Revis�o de Contas
	 * 
	 * @author Ado Rocha
	 * @date 20/01/2014
	 */
	public Collection pesquisarAuditoriaRevisaoCancelamentoContaUsuario(Integer idUsuario, Integer dataInicial, Integer dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3123] - Auditoria de Retifica��o/Implanta��o de Contas
	 * 
	 * @author Ado Rocha
	 * @date 20/01/2014
	 */
	public Collection pesquisarRetificacaoImplantacaoContaFuncionario(Integer idFuncionario, Integer dataInicial, Integer dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3123] - Auditoria de Retifica��o/Implanta��o de Contas
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
	 * [UC3122] Auditoria de Altera��es de Clientes
	 * 
	 * @author Eduardo Oliveira
	 * @throws ControladorException
	 * @date 01/03/2014
	 */
	public Collection consultarAlteracoesClientesFuncionario(Integer idFuncionario, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3122] Auditoria de Altera��es de Clientes
	 * 
	 * @author Eduardo Oliveira
	 * @throws ControladorException
	 * @date 01/03/2014
	 */
	public Collection consultarAlteracoesClientesUsuario(Integer idUsuario, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3127] - Auditoria de Transferência de Débitos
	 * [SB0001] �- Apresenta Dados da Auditoria
	 * Consulta por Funcionario
	 * 
	 * @author Eduardo Oliveira
	 * @date 01/02/2014
	 */
	public Collection consultarAuditoriaTransferenciaDebitosFuncionario(Integer idFuncionario, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3127] - Auditoria de Transfer�ncia de D�bitos
	 * [SB0001] -�� Apresenta Dados da Auditoria
	 * Consulta por Usuario
	 * 
	 * @author Eduardo Oliveira
	 * @date 01/02/2014
	 */
	public Collection consultarAuditoriaTransferenciaDebitosUsuario(Integer idUsuarioOrigem, Integer idUsuarioDestino, Date dataFinal,
					Date dataInicial)
					throws ErroRepositorioException;

	/**
	 * [UC3126] Auditoria de Servi�os a Cobrar
	 * 
	 * @author Eduardo Oliveira
	 * @throws ControladorException
	 * @date 08/03/2014
	 */
	public Collection consultarServicosACobrarFuncionario(Integer idFuncionario, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3126] Auditoria de Servi�os a Cobrar
	 * 
	 * @author Eduardo Oliveira
	 * @throws ControladorException
	 * @date 08/03/2014
	 */
	public Collection consultarServicosACobrarUsuario(Integer idUsuario, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException;


}
