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
 * < <Descri��o da Classe>>
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
	 * M�todo que consulta os grupos do usu�rio
	 * 
	 * @author S�vio Luiz
	 * @date 27/06/2006
	 */
	public Collection pesquisarGruposUsuario(Integer idUsuario) throws ErroRepositorioException{

		Collection retorno = null;

		// cria uma sess�o com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a vari�vel que vai conter o hql
		String consulta = "";

		try{
			consulta = "select grupo " + "from Grupo grupo " + "where grupo.id in(select grupo.id " + "from UsuarioGrupo usuarioGrupo "
							+ "inner join usuarioGrupo.usuario usuario  " + "inner join usuarioGrupo.grupo grupo "
							+ "where usuario.id = :idUsuario) AND " + "grupo.indicadorUso = :indicadorUso ";

			retorno = session.createQuery(consulta).setInteger("idUsuario", idUsuario.intValue()).setShort("indicadorUso",
							ConstantesSistema.INDICADOR_USO_ATIVO).list();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * M�todo que consulta os grupos do usu�rio
	 * 
	 * @author S�vio Luiz
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

		// cria uma sess�o com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a vari�vel que vai conter o hql
		String consulta = "";

		try{
			consulta = "select grupo " + "from Grupo grupo " + "where grupo.id in (select distinct grupoAcesso.comp_id.grupIdacesso "
							+ "from gcom.seguranca.acesso.GrupoAcesso grupoAcesso "
							+ "where grupoAcesso.comp_id.grupId in (:idsGrupos)) AND " + "grupo.indicadorUso = :indicadorUso ";

			retorno = session.createQuery(consulta).setParameterList("idsGrupos", idsGrupos).setShort("indicadorUso",
							ConstantesSistema.INDICADOR_USO_ATIVO).list();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * M�todo que consulta as abrang�ncias dos usu�rio pelos os ids das
	 * abrang�ncias superiores e com o id da abrang�ncia diferente do id da
	 * abrang�ncia do usu�rio que est� inserindo(usu�rio logado)
	 * 
	 * @author S�vio Luiz
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
		// cria uma sess�o com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a vari�vel que vai conter o hql
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
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Informa o n�mero total de registros de usuario grupo, auxiliando o
	 * esquema de pagina��o
	 * 
	 * @author S�vio Luiz
	 * @date 30/06/2006
	 * @param Filtro
	 *            da Pesquisa
	 * @param Pacote
	 *            do objeto pesquisado
	 * @return N�mero de registros da pesquisa
	 * @throws ErroRepositorioException
	 *             Exce��o do reposit�rio
	 */
	public int totalRegistrosPesquisaUsuarioGrupo(FiltroUsuarioGrupo filtroUsuarioGrupo) throws ErroRepositorioException{

		// cria a cole��o de retorno
		int retorno = 0;
		// obt�m a sess�o
		Session session = HibernateUtil.getSession();

		try{

			List camposOrderBy = new ArrayList();

			camposOrderBy = filtroUsuarioGrupo.getCamposOrderBy();

			filtroUsuarioGrupo.limparCamposOrderBy();

			filtroUsuarioGrupo.getColecaoCaminhosParaCarregamentoEntidades().clear();
			// pesquisa a cole��o de atividades e atribui a vari�vel "retorno"
			retorno = ((Number) GeradorHQLCondicional.gerarCondicionalQuery(filtroUsuarioGrupo,
							"gcom.seguranca.acesso.usuario.UsuarioGrupo", "usuarioGrupo",
							"select count(distinct usuarioGrupo.usuario.id) from UsuarioGrupo as usuarioGrupo", session).uniqueResult())
							.intValue();

			filtroUsuarioGrupo.setCampoOrderBy((String[]) camposOrderBy.toArray(new String[camposOrderBy.size()]));

		}catch(HibernateException e){
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o
			HibernateUtil.closeSession(session);
		}
		// retorna a cole��o de atividades pesquisada(s)
		return retorno;

	}

	/**
	 * Informa o n�mero total de registros de usuario grupo, auxiliando o
	 * esquema de pagina��o
	 * 
	 * @author S�vio Luiz
	 * @date 30/06/2006
	 * @param Filtro
	 *            da Pesquisa
	 * @param Pacote
	 *            do objeto pesquisado
	 * @return N�mero de registros da pesquisa
	 * @throws ErroRepositorioException
	 *             Exce��o do reposit�rio
	 */
	/**
	 * Informa o n�mero total de registros de usuario grupo, auxiliando o
	 * esquema de pagina��o
	 * 
	 * @author S�vio Luiz
	 * @date 30/06/2006
	 * @param Filtro
	 *            da Pesquisa
	 * @param Pacote
	 *            do objeto pesquisado
	 * @return N�mero de registros da pesquisa
	 * @throws ErroRepositorioException
	 *             Exce��o do reposit�rio
	 */
	public Collection pesquisarUsuariosDosGruposUsuarios(FiltroUsuarioGrupo filtroUsuarioGrupo, Integer numeroPagina)
					throws ErroRepositorioException{

		// cria a cole��o de retorno
		Collection retorno = new ArrayList();
		// obt�m a sess�o
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

			// pesquisa a cole��o de atividades e atribui a vari�vel "retorno"

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
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o
			HibernateUtil.closeSession(session);
		}
		// retorna a cole��o de atividades pesquisada(s)
		return retorno;

	}

	/**
	 * M�todo que consulta os grupos funcion�rios opera��es passando os ids dos
	 * grupos
	 * 
	 * @author S�vio Luiz
	 * @date 11/07/2006
	 */

	public Collection pesquisarGruposFuncionalidadesOperacoes(Integer[] idsGrupos) throws ErroRepositorioException{

		Collection retorno = null;

		// cria uma sess�o com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a vari�vel que vai conter o hql
		String consulta = "";

		try{
			consulta = "select distinct operacao " + "from GrupoFuncionalidadeOperacao grupoFuncionalidadeOperacao "
							+ "inner join grupoFuncionalidadeOperacao.grupo grupo "
							+ "inner join grupoFuncionalidadeOperacao.operacao operacao " + "where grupo.id in(:idsGrupos) ";

			retorno = session.createQuery(consulta).setParameterList("idsGrupos", idsGrupos).list();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * M�todo que consulta os grupos funcion�rios opera��es passando os ids dos
	 * grupos e o id da funcionalidade
	 * 
	 * @author S�vio Luiz
	 * @date 11/07/2006
	 */
	public Collection pesquisarGruposFuncionalidadesOperacoesPelaFuncionalidade(Integer[] idsGrupos, Integer idFuncionalidade)
					throws ErroRepositorioException{

		Collection retorno = new ArrayList();

		// cria uma sess�o com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a vari�vel que vai conter o hql
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
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * M�todo que consulta os usu�rios restrin��o passando os ids dos grupos , o
	 * id da funcionalidade e o id do usu�rio
	 * 
	 * @author S�vio Luiz
	 * @date 11/07/2006
	 */
	public Collection pesquisarUsuarioRestrincao(Integer[] idsGrupos, Integer idFuncionalidade, Integer idUsuario)
					throws ErroRepositorioException{

		Collection retorno = new ArrayList();

		// cria uma sess�o com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a vari�vel que vai conter o hql
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
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}


	/**
	 * M�todo que consulta as funcionalidades da(s) funcionalidade(s)
	 * princpial(is)
	 * 
	 * @author S�vio Luiz
	 * @date 12/07/2006
	 */
	public Collection pesquisarFuncionanidadesDependencia(Collection idsFuncionalidades) throws ErroRepositorioException{

		Collection retorno = null;

		// cria uma sess�o com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a vari�vel que vai conter o hql
		String consulta = "";

		try{
			consulta = "select funcDependencia.id " + "from FuncionalidadeDependencia funDepen "
							+ "inner join funDepen.funcionalidade func " + "inner join funDepen.funcionalidadeDependencia funcDependencia "
							+ "where func.id in(:idsFuncionalidades)";

			retorno = session.createQuery(consulta).setParameterList("idsFuncionalidades", idsFuncionalidades).list();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * M�todo que consulta as opera��es da(s) funcionalidade(s)
	 * 
	 * @author S�vio Luiz
	 * @date 12/07/2006
	 */
	public Collection pesquisarOperacoes(Collection idsFuncionalidades) throws ErroRepositorioException{

		Collection retorno = new ArrayList();

		// cria uma sess�o com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a vari�vel que vai conter o hql
		String consulta = "";

		try{
			consulta = "select  op " + "from Operacao op " + "inner join fetch op.funcionalidade func "
							+ "where func.id in(:idsFuncionalidades)";

			retorno = session.createQuery(consulta).setParameterList("idsFuncionalidades", idsFuncionalidades).list();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * M�todo que consulta as permiss�es especiais do usu�rio
	 * 
	 * @author S�vio Luiz
	 * @date 12/07/2006
	 */
	public Collection pesquisarPermissaoEspecialUsuario(Integer idUsuario) throws ErroRepositorioException{

		Collection retorno = null;

		// cria uma sess�o com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a vari�vel que vai conter o hql
		String consulta = "";

		try{
			consulta = "select per from UsuarioPermissaoEspecial upe inner join upe.permissaoEspecial per "
							+ " where upe.comp_id.usuarioId = :idUsuario order by per.descricao ";

			retorno = session.createQuery(consulta).setInteger("idUsuario", idUsuario).list();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * M�todo que consulta as permiss�es especiais do usu�rio com os parametros
	 * das permiss�es de outro usu�rio
	 * 
	 * @author S�vio Luiz
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

		// cria uma sess�o com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a vari�vel que vai conter o hql
		String consulta = "";

		try{
			consulta = "select pe " + "from UsuarioPermissaoEspecial upe " + "inner join upe.usuario usu "
							+ "inner join upe.permissaoEspecial pe" + "where usu.id = :idUsuario and "
							+ "pe.id in(:idsPermissoesEspeciais) order by pe.descricao ";

			retorno = session.createQuery(consulta).setInteger("idUsuario", idUsuario).setParameterList("idsPermissoesEspeciais",
							idsPermissoesEspeciais).list();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * M�todo que consulta as permiss�es especiais do usu�rio sem os parametros
	 * das permiss�es de outro usu�rio.Recupera todas as permiss�es do usuario
	 * que n�o tem a permiss�o de outro usu�rio
	 * 
	 * @author S�vio Luiz
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

		// cria uma sess�o com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a vari�vel que vai conter o hql
		String consulta = "";

		try{
			consulta = "select pe " + "from UsuarioPermissaoEspecial upe " + "inner join upe.usuario usu "
							+ "inner join upe.permissaoEspecial pe" + "where usu.id = :idUsuario and "
							+ "pe.id not in(:idsPermissoesEspeciais) order by pe.descricao ";

			retorno = session.createQuery(consulta).setInteger("idUsuario", idUsuario).setParameterList("idsPermissoesEspeciais",
							idsPermissoesEspeciais).list();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Essa verifica��o � preciso para quando for, [SB0011]- Atualizar Controles
	 * de Acesso no [SB0230]-Manter Usu�rio ,saber que grupos daquela
	 * funcionalidade daquela opera��o ser�o inseridos na tabela
	 * UsuarioGrupoRestrincao
	 * 
	 * @author S�vio Luiz
	 * @date 11/07/2006
	 */
	public Collection pesquisarIdsGruposPelaFuncionalidadeGruposOperacao(Integer[] idsGrupos, Integer idFuncionalidade, Integer idOperacao)
					throws ErroRepositorioException{

		Collection retorno = null;

		// cria uma sess�o com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a vari�vel que vai conter o hql
		String consulta = "";

		try{
			consulta = "select distinct grp.id " + "from GrupoFuncionalidadeOperacao gfo " + "inner join gfo.grupo grp "
							+ "inner join gfo.operacao ope " + "inner join gfo.funcionalidade fun " + "where grp.id in(:idsGrupos) and "
							+ "fun.id = :idFuncionalidade and " + "ope.id = :idOperacao";

			retorno = session.createQuery(consulta).setParameterList("idsGrupos", idsGrupos).setInteger("idFuncionalidade",
							idFuncionalidade).setInteger("idOperacao", idOperacao).list();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * M�todo que verifica a permiss�o do usu�rio a uma determinada funcionalidade
	 * 
	 * @author �talo Almeida
	 * @date 28/06/2013
	 */
	public boolean verificarPermissaoFuncionalidadeUsuario(Integer idUsuario, String descricaoCaminhoOperacao,
					String descricaoCaminhoFuncionalidade)
					throws ErroRepositorioException{

		boolean temPermissao = false;

		// cria uma sess�o com o hibernate
		Session session = HibernateUtil.getSession();

		// cria a vari�vel que vai conter o hql
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
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o com o hibernate
			HibernateUtil.closeSession(session);
		}

		return temPermissao;
	}

}