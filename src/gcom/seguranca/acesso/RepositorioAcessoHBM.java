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

package gcom.seguranca.acesso;

import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ErroRepositorioException;
import gcom.util.HibernateUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

/**
 * Descrição da classe
 * 
 * @author Administrador
 * @date 13/11/2006
 */
public class RepositorioAcessoHBM
				implements IRepositorioAcesso {

	private static IRepositorioAcesso instancia;

	public RepositorioAcessoHBM() {

	}

	public static IRepositorioAcesso getInstancia(){

		if(instancia == null){
			instancia = new RepositorioAcessoHBM();
		}

		return instancia;
	}

	/**
	 * <Breve descrição sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * <Breve descrição sobre o subfluxo>
	 * <Identificador e nome do subfluxo>
	 * <Breve descrição sobre o fluxo secundário>
	 * <Identificador e nome do fluxo secundário>
	 * 
	 * @author Administrador
	 * @date 13/11/2006
	 * @param consulta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object pesquisarObjetoAbrangencia(String consulta) throws ErroRepositorioException{

		Object retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql

		try{

			retorno = session.createQuery(consulta).uniqueResult();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public void atualizarRegistrarAcessoUsuario(Usuario usuario) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		String consulta = "update Usuario usu " + "set usu.numeroAcessos =:acesso, usu.ultimoAcesso = :ultimo "
						+ "where usu.id = :idUsuario";

		try{

			session.createQuery(consulta).setInteger("acesso", usuario.getNumeroAcessos())
							.setTimestamp("ultimo", usuario.getUltimoAcesso()).setInteger("idUsuario", usuario.getId()).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);

		}

	}

	/**
	 * Método reponsável por verificar a existencia do login do usuário.
	 * 
	 * @author Virgínia Melo
	 * @date 07/07/2009
	 * @param loginUsuario
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaLogin(String loginUsuario) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		boolean retorno = false;

		try{

			Criteria criteria = session.createCriteria(Usuario.class).add(Expression.eq("login", loginUsuario));

			List<Usuario> lista = criteria.list();
			if(lista != null && !lista.isEmpty()){
				retorno = true;
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0197] Filtrar Operações Efetuadas
	 * 
	 * @author Saulo Lima
	 * @date 18/05/2012
	 * @param
	 * @throws ErroRepositorioException
	 */
	public Collection<Funcionalidade> pesquisarFuncionalidadesComOperacaoAuditavel() throws ErroRepositorioException{

		Collection<Funcionalidade> retorno = null;
		Session session = HibernateUtil.getSession();

		String consulta = "SELECT DISTINCT oper.funcionalidade FROM Operacao oper WHERE oper.indicadorAuditoria = "
						+ ConstantesSistema.SIM.toString() + " ORDER BY oper.funcionalidade.descricao";

		try{
			retorno = session.createQuery(consulta).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public Object[] consultarDadosAcessoGcsME(Usuario usuariologado) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Object[] retorno = null;

		String consulta = "select func.func_id, AR.ID_REGIONAL, MA.CODPERFIL" + " from funcionario func "
						+ " inner join SCIADM.ACESSO_REGIONAL AR on func.func_id = AR.ID_USUARIO "
						+ " inner join SCIADM.METACESSO MA on MA.USUARIO = func.FUNC_ID " + " where func.func_id = ?";

		try{

			Connection c = session.connection();
			PreparedStatement statement = c.prepareStatement(consulta);
			statement.setInt(1, usuariologado.getFuncionario().getId());

			ResultSet rs = statement.executeQuery();

			if(rs.next()){
				retorno = new Object[3];
				retorno[0] = rs.getInt(1);
				retorno[1] = rs.getInt(2);
				retorno[2] = rs.getInt(3);
			}

			rs.close();
			statement.close();
			c.close();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
			consulta = null;
		}

		// TODO Auto-generated method stub
		return retorno;
	}

}