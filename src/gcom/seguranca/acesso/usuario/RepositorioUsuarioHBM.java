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

package gcom.seguranca.acesso.usuario;

import gcom.seguranca.acesso.Grupo;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.util.ConstantesSistema;
import gcom.util.ErroRepositorioException;
import gcom.util.HibernateUtil;
import gcom.util.filtro.GeradorHQLCondicional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 * @created 22 de Julho de 2005
 */
public class RepositorioUsuarioHBM
				implements IRepositorioUsuario {

	private static IRepositorioUsuario instancia;

	/**
	 * Constructor for the RepositorioClienteTipoHBM object
	 */
	public RepositorioUsuarioHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static IRepositorioUsuario getInstancia(){

		if(instancia == null){
			instancia = new RepositorioUsuarioHBM();
		}

		return instancia;
	}

	/**
	 * Método que consulta os grupos do usuário
	 * 
	 * @author Sávio Luiz
	 * @date 27/06/2006
	 */
	public Collection pesquisarGruposUsuario(Integer idUsuario) throws ErroRepositorioException{

		Collection retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta = "";

		try{
			consulta = "select grupo " + "from Grupo grupo " + "where grupo.id in(select grupo.id " + "from UsuarioGrupo usuarioGrupo "
							+ "inner join usuarioGrupo.usuario usuario  " + "inner join usuarioGrupo.grupo grupo "
							+ "where usuario.id = :idUsuario) AND " + "grupo.indicadorUso = :indicadorUso ";

			retorno = session.createQuery(consulta).setInteger("idUsuario", idUsuario.intValue()).setShort("indicadorUso",
							ConstantesSistema.INDICADOR_USO_ATIVO).list();

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

	/**
	 * Método que consulta os grupos do usuário
	 * 
	 * @author Sávio Luiz
	 * @date 27/06/2006
	 */
	public Collection pesquisarGruposUsuarioAcesso(Collection colecaoUsuarioGrupos) throws ErroRepositorioException{

		Collection retorno = null;

		Collection idsGrupos = new ArrayList();
		if(colecaoUsuarioGrupos != null){
			Iterator iteUsuarioGrupo = colecaoUsuarioGrupos.iterator();
			while(iteUsuarioGrupo.hasNext()){
				Grupo grupo = (Grupo) iteUsuarioGrupo.next();
				idsGrupos.add(grupo.getId());
			}
		}

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta = "";

		try{
			consulta = "select grupo " + "from Grupo grupo " + "where grupo.id in (select distinct grupoAcesso.comp_id.grupIdacesso "
							+ "from gcom.seguranca.acesso.GrupoAcesso grupoAcesso "
							+ "where grupoAcesso.comp_id.grupId in (:idsGrupos)) AND " + "grupo.indicadorUso = :indicadorUso ";

			retorno = session.createQuery(consulta).setParameterList("idsGrupos", idsGrupos).setShort("indicadorUso",
							ConstantesSistema.INDICADOR_USO_ATIVO).list();

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

	/**
	 * Método que consulta as abrangências dos usuário pelos os ids das
	 * abrangências superiores e com o id da abrangência diferente do id da
	 * abrangência do usuário que está inserindo(usuário logado)
	 * 
	 * @author Sávio Luiz
	 * @date 28/06/2006
	 */
	public Collection pesquisarUsuarioAbrangenciaPorSuperior(Collection colecaoUsuarioAbrangencia, Integer idUsuarioAbrangenciaLogado)
					throws ErroRepositorioException{

		Collection retorno = null;

		Collection idsUsuarioAbrangencia = new ArrayList();
		Iterator usuarioAbrangenciaIterator = colecaoUsuarioAbrangencia.iterator();
		while(usuarioAbrangenciaIterator.hasNext()){
			UsuarioAbrangencia usuarioAbrangencia = (UsuarioAbrangencia) usuarioAbrangenciaIterator.next();
			idsUsuarioAbrangencia.add(usuarioAbrangencia);
		}
		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta = "";

		try{

			consulta = "select usuarioAbrangencia " + "from UsuarioAbrangencia usuarioAbrangencia "
							+ "inner join usuarioAbrangencia.usuarioAbrangenciaSuperior usuarioAbrangenciaSuperior "
							+ "where usuarioAbrangenciaSuperior.id in( :idsUsuarioAbrangencia) and "
							+ "usuarioAbrangencia.id != :idUsuarioAbrangenciaLogado";

			retorno = session.createQuery(consulta).setParameterList("idsUsuarioAbrangencia", idsUsuarioAbrangencia).setInteger(
							"idUsuarioAbrangenciaLogado", idUsuarioAbrangenciaLogado).list();

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

	/**
	 * Informa o número total de registros de usuario grupo, auxiliando o
	 * esquema de paginação
	 * 
	 * @author Sávio Luiz
	 * @date 30/06/2006
	 * @param Filtro
	 *            da Pesquisa
	 * @param Pacote
	 *            do objeto pesquisado
	 * @return Número de registros da pesquisa
	 * @throws ErroRepositorioException
	 *             Exceção do repositório
	 */
	public int totalRegistrosPesquisaUsuarioGrupo(FiltroUsuarioGrupo filtroUsuarioGrupo) throws ErroRepositorioException{

		// cria a coleção de retorno
		int retorno = 0;
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{

			List camposOrderBy = new ArrayList();

			camposOrderBy = filtroUsuarioGrupo.getCamposOrderBy();

			filtroUsuarioGrupo.limparCamposOrderBy();

			filtroUsuarioGrupo.getColecaoCaminhosParaCarregamentoEntidades().clear();
			// pesquisa a coleção de atividades e atribui a variável "retorno"
			retorno = ((Number) GeradorHQLCondicional.gerarCondicionalQuery(filtroUsuarioGrupo,
							"gcom.seguranca.acesso.usuario.UsuarioGrupo", "usuarioGrupo",
							"select count(distinct usuarioGrupo.usuario.id) from UsuarioGrupo as usuarioGrupo", session).uniqueResult())
							.intValue();

			filtroUsuarioGrupo.setCampoOrderBy((String[]) camposOrderBy.toArray(new String[camposOrderBy.size()]));

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção de atividades pesquisada(s)
		return retorno;

	}

	/**
	 * Informa o número total de registros de usuario grupo, auxiliando o
	 * esquema de paginação
	 * 
	 * @author Sávio Luiz
	 * @date 30/06/2006
	 * @param Filtro
	 *            da Pesquisa
	 * @param Pacote
	 *            do objeto pesquisado
	 * @return Número de registros da pesquisa
	 * @throws ErroRepositorioException
	 *             Exceção do repositório
	 */
	/**
	 * Informa o número total de registros de usuario grupo, auxiliando o
	 * esquema de paginação
	 * 
	 * @author Sávio Luiz
	 * @date 30/06/2006
	 * @param Filtro
	 *            da Pesquisa
	 * @param Pacote
	 *            do objeto pesquisado
	 * @return Número de registros da pesquisa
	 * @throws ErroRepositorioException
	 *             Exceção do repositório
	 */
	public Collection pesquisarUsuariosDosGruposUsuarios(FiltroUsuarioGrupo filtroUsuarioGrupo, Integer numeroPagina)
					throws ErroRepositorioException{

		// cria a coleção de retorno
		Collection retorno = new ArrayList();
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{

			List camposOrderBy = new ArrayList();

			camposOrderBy = filtroUsuarioGrupo.getCamposOrderBy();

			// filtroUsuarioGrupo
			// .adicionarCaminhoParaCarregamentoEntidade("usuario");
			filtroUsuarioGrupo.adicionarCaminhoParaCarregamentoEntidade("usuario.unidadeOrganizacional");
			filtroUsuarioGrupo.adicionarCaminhoParaCarregamentoEntidade("usuario.usuarioAbrangencia");
			filtroUsuarioGrupo.adicionarCaminhoParaCarregamentoEntidade("usuario.usuarioSituacao");
			filtroUsuarioGrupo.adicionarCaminhoParaCarregamentoEntidade("usuario.usuarioTipo");
			filtroUsuarioGrupo.adicionarCaminhoParaCarregamentoEntidade("usuario.funcionario.unidadeOrganizacional");

			filtroUsuarioGrupo.limparCamposOrderBy();

			// pesquisa a coleção de atividades e atribui a variável "retorno"

			retorno = new ArrayList(new CopyOnWriteArraySet(GeradorHQLCondicional.gerarCondicionalQuery(

			filtroUsuarioGrupo,

			"gcom.seguranca.acesso.usuario.UsuarioGrupo", "usuarioGrupo",

			"from Usuario as u left join fetch u.usuarioGrupos usuarioGrupo",

			session).setFirstResult(10 * numeroPagina).setMaxResults(10).list()));

			filtroUsuarioGrupo.setCampoOrderBy((String[]) camposOrderBy.toArray(new String[camposOrderBy.size()]));

			// Iterator iterator = colecao.iterator();
			// while (iterator.hasNext()) {
			//
			// Usuario usuario = (Usuario) iterator.next();
			//
			// // carrega todos os objetos
			// Hibernate.initialize(usuario.getUsuarioTipo());
			// Hibernate.initialize(usuario.getUnidadeOrganizacional());
			// Hibernate.initialize(usuario.getUsuarioAbrangencia());
			// Hibernate.initialize(usuario.getUsuarioSituacao());
			//
			// retorno.add(usuario);
			// }

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção de atividades pesquisada(s)
		return retorno;

	}

	/**
	 * Método que consulta os grupos funcionários operações passando os ids dos
	 * grupos
	 * 
	 * @author Sávio Luiz
	 * @date 11/07/2006
	 */

	public Collection pesquisarGruposFuncionalidadesOperacoes(Integer[] idsGrupos) throws ErroRepositorioException{

		Collection retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta = "";

		try{
			consulta = "select distinct operacao " + "from GrupoFuncionalidadeOperacao grupoFuncionalidadeOperacao "
							+ "inner join grupoFuncionalidadeOperacao.grupo grupo "
							+ "inner join grupoFuncionalidadeOperacao.operacao operacao " + "where grupo.id in(:idsGrupos) ";

			retorno = session.createQuery(consulta).setParameterList("idsGrupos", idsGrupos).list();

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

	/**
	 * Método que consulta os grupos funcionários operações passando os ids dos
	 * grupos e o id da funcionalidade
	 * 
	 * @author Sávio Luiz
	 * @date 11/07/2006
	 */
	public Collection pesquisarGruposFuncionalidadesOperacoesPelaFuncionalidade(Integer[] idsGrupos, Integer idFuncionalidade)
					throws ErroRepositorioException{

		Collection retorno = new ArrayList();

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta = "";

		try{
			consulta = "select distinct ope " + "from GrupoFuncionalidadeOperacao gfo " + "inner join gfo.grupo grp "
							+ "inner join gfo.operacao ope " + "inner join gfo.funcionalidade fun "
							+ "left join fetch ope.funcionalidade func " + "where grp.id in(:idsGrupos) and "
							+ "fun.id = :idFuncionalidade ";

			retorno = session.createQuery(consulta).setParameterList("idsGrupos", idsGrupos).setInteger("idFuncionalidade",
							idFuncionalidade).list();

			// while (iterator.hasNext()) {
			// Operacao operacao = (Operacao) iterator.next();
			// Hibernate.initialize(operacao.getFuncionalidade());
			// retorno.add(operacao);
			//
			// }

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

	/**
	 * Método que consulta os usuários restrinção passando os ids dos grupos , o
	 * id da funcionalidade e o id do usuário
	 * 
	 * @author Sávio Luiz
	 * @date 11/07/2006
	 */
	public Collection pesquisarUsuarioRestrincao(Integer[] idsGrupos, Integer idFuncionalidade, Integer idUsuario)
					throws ErroRepositorioException{

		Collection retorno = new ArrayList();

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta = "";

		try{
			consulta = "select distinct ope " + "from UsuarioGrupoRestricao usuarioGrup "
							+ "left join usuarioGrup.grupoFuncionalidadeOperacao grupoFuncOp " + "left join grupoFuncOp.operacao ope "
							+ "left join fetch ope.funcionalidade func " + "where usuarioGrup.comp_id.grupoId in(:idsGrupos) and "
							+ "usuarioGrup.comp_id.funcionalidadeId = :idFuncionalidade and "
							+ "usuarioGrup.comp_id.usuarioId = :idUsuario";

			retorno = new ArrayList(new CopyOnWriteArraySet(session.createQuery(consulta).setParameterList("idsGrupos", idsGrupos)
							.setInteger("idFuncionalidade", idFuncionalidade).setInteger("idUsuario", idUsuario).list()));

			// while (iterator.hasNext()) {
			// Operacao operacao = (Operacao) iterator.next();
			// Hibernate.initialize(operacao.getFuncionalidade());
			// retorno.add(operacao);
			//
			// }

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


	/**
	 * Método que consulta as funcionalidades da(s) funcionalidade(s)
	 * princpial(is)
	 * 
	 * @author Sávio Luiz
	 * @date 12/07/2006
	 */
	public Collection pesquisarFuncionanidadesDependencia(Collection idsFuncionalidades) throws ErroRepositorioException{

		Collection retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta = "";

		try{
			consulta = "select funcDependencia.id " + "from FuncionalidadeDependencia funDepen "
							+ "inner join funDepen.funcionalidade func " + "inner join funDepen.funcionalidadeDependencia funcDependencia "
							+ "where func.id in(:idsFuncionalidades)";

			retorno = session.createQuery(consulta).setParameterList("idsFuncionalidades", idsFuncionalidades).list();

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

	/**
	 * Método que consulta as operações da(s) funcionalidade(s)
	 * 
	 * @author Sávio Luiz
	 * @date 12/07/2006
	 */
	public Collection pesquisarOperacoes(Collection idsFuncionalidades) throws ErroRepositorioException{

		Collection retorno = new ArrayList();

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta = "";

		try{
			consulta = "select  op " + "from Operacao op " + "inner join fetch op.funcionalidade func "
							+ "where func.id in(:idsFuncionalidades)";

			retorno = session.createQuery(consulta).setParameterList("idsFuncionalidades", idsFuncionalidades).list();

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

	/**
	 * Método que consulta as permissões especiais do usuário
	 * 
	 * @author Sávio Luiz
	 * @date 12/07/2006
	 */
	public Collection pesquisarPermissaoEspecialUsuario(Integer idUsuario) throws ErroRepositorioException{

		Collection retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta = "";

		try{
			consulta = "select per from UsuarioPermissaoEspecial upe inner join upe.permissaoEspecial per "
							+ " where upe.comp_id.usuarioId = :idUsuario order by per.descricao ";

			retorno = session.createQuery(consulta).setInteger("idUsuario", idUsuario).list();

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

	/**
	 * Método que consulta as permissões especiais do usuário com os parametros
	 * das permissões de outro usuário
	 * 
	 * @author Sávio Luiz
	 * @date 12/07/2006
	 */
	public Collection pesquisarPermissaoEspecialUsuarioComPermissoes(Integer idUsuario, Collection permissoesEspeciais)
					throws ErroRepositorioException{

		Collection retorno = null;

		Iterator iterator = permissoesEspeciais.iterator();
		Collection idsPermissoesEspeciais = new ArrayList();
		while(iterator.hasNext()){
			PermissaoEspecial permissaoEspecial = (PermissaoEspecial) iterator.next();
			idsPermissoesEspeciais.add(permissaoEspecial.getId());
		}

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta = "";

		try{
			consulta = "select pe " + "from UsuarioPermissaoEspecial upe " + "inner join upe.usuario usu "
							+ "inner join upe.permissaoEspecial pe" + "where usu.id = :idUsuario and "
							+ "pe.id in(:idsPermissoesEspeciais) order by pe.descricao ";

			retorno = session.createQuery(consulta).setInteger("idUsuario", idUsuario).setParameterList("idsPermissoesEspeciais",
							idsPermissoesEspeciais).list();

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

	/**
	 * Método que consulta as permissões especiais do usuário sem os parametros
	 * das permissões de outro usuário.Recupera todas as permissões do usuario
	 * que não tem a permissão de outro usuário
	 * 
	 * @author Sávio Luiz
	 * @date 12/07/2006
	 */
	public Collection pesquisarPermissaoEspecialUsuarioSemPermissoes(Integer idUsuario, Collection permissoesEspeciais)
					throws ErroRepositorioException{

		Iterator iterator = permissoesEspeciais.iterator();
		Collection idsPermissoesEspeciais = new ArrayList();
		while(iterator.hasNext()){
			PermissaoEspecial permissaoEspecial = (PermissaoEspecial) iterator.next();
			idsPermissoesEspeciais.add(permissaoEspecial.getId());
		}

		Collection retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta = "";

		try{
			consulta = "select pe " + "from UsuarioPermissaoEspecial upe " + "inner join upe.usuario usu "
							+ "inner join upe.permissaoEspecial pe" + "where usu.id = :idUsuario and "
							+ "pe.id not in(:idsPermissoesEspeciais) order by pe.descricao ";

			retorno = session.createQuery(consulta).setInteger("idUsuario", idUsuario).setParameterList("idsPermissoesEspeciais",
							idsPermissoesEspeciais).list();

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

	/**
	 * Essa verificação é preciso para quando for, [SB0011]- Atualizar Controles
	 * de Acesso no [SB0230]-Manter Usuário ,saber que grupos daquela
	 * funcionalidade daquela operação serão inseridos na tabela
	 * UsuarioGrupoRestrincao
	 * 
	 * @author Sávio Luiz
	 * @date 11/07/2006
	 */
	public Collection pesquisarIdsGruposPelaFuncionalidadeGruposOperacao(Integer[] idsGrupos, Integer idFuncionalidade, Integer idOperacao)
					throws ErroRepositorioException{

		Collection retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta = "";

		try{
			consulta = "select distinct grp.id " + "from GrupoFuncionalidadeOperacao gfo " + "inner join gfo.grupo grp "
							+ "inner join gfo.operacao ope " + "inner join gfo.funcionalidade fun " + "where grp.id in(:idsGrupos) and "
							+ "fun.id = :idFuncionalidade and " + "ope.id = :idOperacao";

			retorno = session.createQuery(consulta).setParameterList("idsGrupos", idsGrupos).setInteger("idFuncionalidade",
							idFuncionalidade).setInteger("idOperacao", idOperacao).list();

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

	/**
	 * Método que verifica a permissão do usuário a uma determinada funcionalidade
	 * 
	 * @author Ítalo Almeida
	 * @date 28/06/2013
	 */
	public boolean verificarPermissaoFuncionalidadeUsuario(Integer idUsuario, String descricaoCaminhoOperacao,
					String descricaoCaminhoFuncionalidade)
					throws ErroRepositorioException{

		boolean temPermissao = false;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a variável que vai conter o hql
		String consulta = "";

		try{
			consulta = "SELECT o.oper_id FROM usuario_grupo ug "
							+ "INNER JOIN grupo_funcionalidade_operacao gfo ON gfo.grup_id = ug.grup_id "
							+ "INNER JOIN operacao o ON o.oper_id = gfo.oper_id "
							+ "INNER JOIN funcionalidade f ON f.fncd_id = gfo.fncd_id " + "WHERE ug.usur_id = :idUsuario "
							+ "AND o.oper_dscaminhourl = :descricaoCaminhoOperacao "
							+ "AND f.fncd_dscaminhourl = :descricaoCaminhoFuncionalidade";

			Collection retorno = session.createSQLQuery(consulta).setInteger("idUsuario", idUsuario)
							.setString("descricaoCaminhoOperacao", descricaoCaminhoOperacao)
							.setString("descricaoCaminhoFuncionalidade", descricaoCaminhoFuncionalidade).setMaxResults(1).list();

			if(!retorno.isEmpty()){
				temPermissao = true;
			}

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}

		return temPermissao;
	}

}