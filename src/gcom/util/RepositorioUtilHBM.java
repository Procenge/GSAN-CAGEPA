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

package gcom.util;

import gcom.cadastro.DbVersaoImplementada;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.MunicipioFeriado;
import gcom.cobranca.NacionalFeriado;
import gcom.interceptor.ObjetoTransacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.GeradorHQLCondicional;
import gcom.util.filtro.PersistenciaUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.*;
import org.hibernate.InstantiationException;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.exception.GenericJDBCException;

/**
 * Repositorio para as fun��es utilit�rias do sistema
 * 
 * @author rodrigo
 */
public class RepositorioUtilHBM
				implements IRepositorioUtil {

	private static RepositorioUtilHBM instancia;

	/**
	 * Construtor da classe RepositorioAcessoHBM
	 */
	protected RepositorioUtilHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static RepositorioUtilHBM getInstancia(){

		if(instancia == null){
			instancia = new RepositorioUtilHBM();
		}

		return instancia;
	}

	/**
	 * Retorna a contagem m�xima de registros de uma determinada classe no
	 * sistema
	 * 
	 * @param classe
	 *            Classe (.class) que ser� feita a contagem
	 * @return N�mero de registros
	 * @exception ErroRepositorioException
	 *                Erro no mecanismo hibernate
	 */

	public int registroMaximo(Class classe) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{

			Query query = session.createQuery("select count(*) from " + classe.getName());

			Integer retorno = ((Number) query.list().iterator().next()).intValue();

			return retorno.intValue();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param filtro
	 *            Descri��o do par�metro
	 * @param pacoteNomeObjeto
	 *            Descri��o do par�metro
	 * @param limite
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public Collection limiteMaximoFiltroPesquisa(Filtro filtro, String pacoteNomeObjeto, int limite) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		Collection resultado = null;

		try{

			resultado = new ArrayList(new CopyOnWriteArraySet(GeradorHQLCondicional.gerarCondicionalQuery(filtro, pacoteNomeObjeto,
							"objeto", "from " + pacoteNomeObjeto + " objeto", session).setMaxResults(limite).list()));

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return resultado;
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param classe
	 *            Descri��o do par�metro
	 * @param atributo
	 *            Descri��o do par�metro
	 * @param parametro1
	 *            Descri��o do par�metro
	 * @param parametro2
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public int valorMaximo(Class classe, String atributo, String parametro1, String parametro2) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			Query query = session.createQuery("select max( tabela." + atributo + ") from " + classe.getName() + " tabela"
							+ " where tabela." + parametro1 + " = " + parametro2);

			Number retorno = ((Number) query.list().iterator().next());

			if(retorno == null || retorno.toString().trim().equalsIgnoreCase("")){
				retorno = Integer.valueOf(0);
			}
			return retorno.intValue();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param classe
	 *            Descri��o do par�metro
	 * @param atributo
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public int valorMaximo(Class classe, String atributo) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			Query query = session.createQuery("select max(" + atributo + ") from " + classe.getName());

			Integer retorno = ((Number) query.list().iterator().next()).intValue();

			return retorno.intValue();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Retorna o �nico registro do SistemaParametros.
	 * 
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public SistemaParametro pesquisarParametrosDoSistema() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		SistemaParametro parametros = (SistemaParametro) session.createQuery("from SistemaParametro")
						.setCacheRegion("SQL_SistemaParametro").setCacheable(true).setMaxResults(1)
						.uniqueResult();
		try{

			if(parametros != null){
				HibernateUtil.initialize(parametros.getLogradouro());
				HibernateUtil.initialize(parametros.getLogradouro().getNome());
				HibernateUtil.initialize(parametros.getLogradouro().getMunicipio().getNome());
				HibernateUtil.initialize(parametros.getLogradouro().getMunicipio().getUnidadeFederacao().getSigla());
				HibernateUtil.initialize(parametros.getBairro());
				HibernateUtil.initialize(parametros.getBairro().getNome());
				HibernateUtil.initialize(parametros.getBairro().getMunicipio().getNome());
				HibernateUtil.initialize(parametros.getBairro().getMunicipio().getUnidadeFederacao().getDescricao());
				HibernateUtil.initialize(parametros.getCep());
				HibernateUtil.initialize(parametros.getConsumoTarifaDefault());
			}

			return parametros;

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	public void inserirColecaoObjetos(Collection<Object> colecaoObjetos) throws ErroRepositorioException{

		// inserirBatch(new ArrayList(colecaoObjetos));

		if(colecaoObjetos != null && !colecaoObjetos.isEmpty()){
			for(Object object : colecaoObjetos){
				inserir(object);
			}
		}
	}

	public void inserirListaObjetos(List colecaoObjetos) throws ErroRepositorioException{

		if(colecaoObjetos != null && !colecaoObjetos.isEmpty()){
			for(Object object : colecaoObjetos){
				inserir(object);
			}
		}
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param objeto
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public Object inserir(Object objeto) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		Object retorno = null;

		try{

			retorno = session.save(objeto);
			session.flush();

			return retorno;
		}catch(GenericJDBCException ex){
			throw new ErroRepositorioException(ex, "Erro no Hibernate");
		}catch(CallbackException e){
			throw new ErroRepositorioException(e, e.getMessage());
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Obtem o pr�ximo valor do sequence do Banco do Imovel ou Cliente
	 * 
	 * @author Rafael Santos
	 * @since 17/11/2006
	 * @param objeto
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public Object obterNextVal(Object objeto) throws ErroRepositorioException{

		Object retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{
			Dialect dialect = Dialect.getDialect();

			if(objeto instanceof Imovel){
				// verifica se o objeto � do tipo Imovel
				consulta = dialect.getSequenceNextValString("sq_imov");
			}else if(objeto instanceof Cliente){
				// verifica se o objeto � do tipo Cliente
				consulta = dialect.getSequenceNextValString("sq_cliente");
			}

			Number retornoConsulta = (Number) session.createSQLQuery(consulta).uniqueResult();

			if(retornoConsulta != null){
				retorno = retornoConsulta.intValue();
			}
		}catch(GenericJDBCException ex){
			throw new ErroRepositorioException(ex, "Erro no Hibernate");
		}catch(CallbackException e){
			throw new ErroRepositorioException(e, e.getMessage());
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param filtro
	 *            Descri��o do par�metro
	 * @param pacoteNomeObjeto
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public Collection pesquisar(Filtro filtro, String pacoteNomeObjeto) throws ErroRepositorioException{

		// cria a cole��o de retorno
		Collection retorno = null;
		// obt�m a sess�o
		Session session = HibernateUtil.getSession();

		try{
			// pesquisa a cole��o de atividades e atribui a vari�vel "retorno"
			// retorno = new ArrayList(new
			// CopyOnWriteArraySet(GeradorHQLCondicional.gerarCondicionalQuery(filtro,
			// "objeto", "from " + pacoteNomeObjeto + " objeto",
			// session).list()));

			int idxPry = pacoteNomeObjeto.lastIndexOf("_$$_");
			if(idxPry != -1) pacoteNomeObjeto = pacoteNomeObjeto.substring(0, idxPry);

			String alias = "objeto";
			StringBuilder query = new StringBuilder();
			query.append(" from ").append(pacoteNomeObjeto).append(" ").append(alias);

			boolean isFieldsIndicated = montarQuery(query, filtro, pacoteNomeObjeto, alias);

			retorno = GeradorHQLCondicional.gerarCondicionalQuery(filtro, pacoteNomeObjeto, alias, query.toString(), session).list();

			query.delete(0, query.length());
			query = null;

			if(isFieldsIndicated){
				retorno = transformarColecaoRetorno(retorno, filtro, pacoteNomeObjeto);
			}

			// parametro usado para determinar se quer inicializar os atributos lazies
			if(filtro.isInitializeLazy()){
				inicializarPropriedadesLazies(retorno);
			}

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

	private Collection transformarColecaoRetorno(Collection colecaoOriginal, Filtro filtro, String pacoteNomeObjeto){

		Class classEntidade = null;
		Collection colecaoRetorno = new ArrayList(colecaoOriginal.size());

		try{
			classEntidade = Class.forName(pacoteNomeObjeto);
		}catch(ClassNotFoundException e){
			throw new SistemaException(e, e.getMessage());
		}
		try{
			if(colecaoOriginal != null && !colecaoOriginal.isEmpty()){

				Object[] valoresCampos = new Object[1];

				for(Iterator iterator = colecaoOriginal.iterator(); iterator.hasNext();){

					Object valoresRetornados = iterator.next();

					if(valoresRetornados instanceof Object[]){
						valoresCampos = (Object[]) valoresRetornados;
					}else{
						valoresCampos[0] = valoresRetornados;
					}

					Object objetoEntidade = classEntidade.newInstance();
					colecaoRetorno.add(objetoEntidade);

					int indice = 0;
					Collection<String> caminhosCampos = filtro.getCampos();

					for(String caminhoCampo : caminhosCampos){
						if(caminhoCampo != null && !caminhoCampo.trim().equals("")){
							try{
								if(caminhoCampo.indexOf(".") != -1){
									Object objeto = objetoEntidade;
									String[] campos = caminhoCampo.split("\\.");
									for(int i = 0; i < campos.length - 1; i++){
										Object valorAtributo = PropertyUtils.getProperty(objeto, campos[i]);
										if(valorAtributo == null){
											Class tipoAtributo = PropertyUtils.getPropertyType(objeto, campos[i]);
											try{
												valorAtributo = tipoAtributo.newInstance();
											}catch(java.lang.InstantiationException e){
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											PropertyUtils.setProperty(objeto, campos[i], valorAtributo);
										}
										objeto = valorAtributo;
									}
									PropertyUtils.setProperty(objeto, campos[campos.length - 1], valoresCampos[indice++]);
								}else{
									PropertyUtils.setProperty(objetoEntidade, caminhoCampo, valoresCampos[indice++]);
								}
							}catch(IllegalAccessException e){
								e.printStackTrace();
							}catch(InvocationTargetException e){
								e.printStackTrace();
							}catch(NoSuchMethodException e){
								e.printStackTrace();
							}
						}
					}
				}
				colecaoOriginal.clear();
				colecaoOriginal = null;
			}
		}catch(InstantiationException e){
			throw new SistemaException(e, e.getMessage());
		}catch(IllegalAccessException e){
			throw new SistemaException(e, e.getMessage());
		}catch(java.lang.InstantiationException e1){
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return colecaoRetorno;
	}

	private boolean montarQuery(StringBuilder query, Filtro filtro, String pacoteNomeObjeto, String alias){

		boolean isFieldsIndicated = false;

		if(!filtro.getCampos().isEmpty()){

			StringBuilder select = new StringBuilder();
			Collection<String> caminhosCampos = filtro.getCampos();

			for(String caminhoCampo : caminhosCampos){
				if(caminhoCampo != null && !caminhoCampo.trim().equals("")){
					if(alias != null && !alias.trim().equals("")) select.append(alias).append(".");
					select.append(caminhoCampo).append(", ");
				}
			}
			if(select.length() > 0){
				isFieldsIndicated = true;
				select.insert(0, "select ").delete(select.length() - 2, select.length());
				query.insert(0, select);
			}
			select.delete(0, select.length());
			select = null;
		}
		return isFieldsIndicated;
	}

	/**
	 * @author jns - 23/07/2010
	 * @param id
	 *            - Id do objeto a ser carregado
	 * @param clazz
	 *            - Classe que representa o tipo do objeto a ser carregado
	 * @param inicializaProxy
	 *            - Flag que indica se a entidade deve ser carregada para a mem�ria ou n�o.
	 *            Se faz necess�rio quado se quer trabalhar com o objeto carregado
	 *            e a sess�o do hibernate j� estiver sido finalizada. Caso o contr�rio,
	 *            a exce��o LazyInitializationException, ser� levantada.
	 * @return Objeto carregado de acordo com os par�metros ou nulo, se o objeto com o Id informado
	 *         n�o existir.
	 */
	public Object pesquisar(Integer id, Class clazz, boolean inicializaProxy) throws ErroRepositorioException{

		Object o = null;
		Session session = HibernateUtil.getSession();
		try{
			o = session.get(clazz, id);
			if(o != null && inicializaProxy){
				Hibernate.initialize(o);
			}
		}catch(Exception e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			session.flush();
			session.close();
		}
		return o;
	}

	/**
	 * Pesquisa um objeto utilizando como exemplo para pesquisa um objeto. Este m�todo retorna uma
	 * cole��o de objetos do mesmo tipo do objeto que foi passado como par�metro e com as
	 * mesmas caracter�sticas.
	 * 
	 * @author Rodrigo Oliveira
	 * @param objeto
	 * @return cole��o de objetos do mesmo tipo do objeto que foi passado como par�metro
	 * @throws ErroRepositorioException
	 */
	public Collection<Object> pesquisar(Object objetoExemplo) throws ErroRepositorioException{

		Collection<Object> retorno = null;
		Session session = HibernateUtil.getSession();

		try{
			Criteria criteria = session.createCriteria(objetoExemplo.getClass().getName());
			criteria.add(Example.create(objetoExemplo));
			retorno = criteria.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Inicializar as propriedades lazies chamando o m�todo initializeLazy de cada objeto
	 * 
	 * @param colecao
	 */
	private void inicializarPropriedadesLazies(Collection colecao){

		for(Iterator iter = colecao.iterator(); iter.hasNext();){
			Object element = iter.next();
			if(element instanceof ObjetoTransacao){
				ObjetoTransacao objTrans = (ObjetoTransacao) element;
				objTrans.initializeLazy();
			}
		}
	}

	/**
	 * Pesquisa um conjunto de entidades atrav�s de um array de c�digo
	 * 
	 * @param filtro
	 *            Descri��o do par�metro
	 * @param pacoteNomeObjeto
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public Collection pesquisar(Collection ids, Filtro filtro, String pacoteNomeObjeto) throws ErroRepositorioException{

		// cria a cole��o de retorno
		Collection retorno = null;
		// obt�m a sess�o
		Session session = HibernateUtil.getSession();

		try{

			int idxPry = pacoteNomeObjeto.lastIndexOf("_$$_");
			if(idxPry != -1) pacoteNomeObjeto = pacoteNomeObjeto.substring(0, idxPry);

			StringBuilder query = new StringBuilder();
			query.append("from ").append(pacoteNomeObjeto).append(" where id IN (:ids)");

			boolean isFieldsIndicated = montarQuery(query, filtro, pacoteNomeObjeto, null);

			// pesquisa a cole��o de atividades e atribui a vari�vel "retorno"
			// retorno = new ArrayList(new CopyOnWriteArraySet(session.createQuery(
			// query.toString()).setParameterList("ids", ids).list()));

			// trata a consulta para n�o deixar trazer mais de 1000 resultados de uma vez.
			if(ids != null && ids.size() > 1000){
				Collection colecaoAuxiliar = new ArrayList<Object>();
				retorno = new ArrayList<Object>();
				for(Object id : ids){
					colecaoAuxiliar.add(id);
					if(colecaoAuxiliar.size() == 1000){
						retorno.addAll(session.createQuery(query.toString()).setParameterList("ids", colecaoAuxiliar).list());
						colecaoAuxiliar.clear();
					}
				}
				if(!colecaoAuxiliar.isEmpty()){
					retorno.addAll(session.createQuery(query.toString()).setParameterList("ids", colecaoAuxiliar).list());
				}
			}else{
				retorno = session.createQuery(query.toString()).setParameterList("ids", ids).list();
			}
			// ---

			if(isFieldsIndicated){
				retorno = transformarColecaoRetorno(retorno, filtro, pacoteNomeObjeto);
			}

			// Carrega os objetos informados no filtro
			if(filtro != null && !filtro.getColecaoCaminhosParaCarregamentoEntidades().isEmpty()){
				PersistenciaUtil.processaObjetosParaCarregamento(filtro.getColecaoCaminhosParaCarregamentoEntidades(), retorno);
			}
			// parametro usado para determinar se quer inicializar os atributos lazies
			if(filtro != null && filtro.isInitializeLazy()){
				inicializarPropriedadesLazies(retorno);
			}

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

	public void atualizarColecaoObjetos(Collection<Object> colecaoObjetos) throws ErroRepositorioException{

		if(colecaoObjetos != null && !colecaoObjetos.isEmpty()){
			for(Object object : colecaoObjetos){
				atualizar(object);
			}
		}
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param objeto
	 *            Descri��o do par�metro
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public void atualizar(Object objeto) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{

			session.update(objeto);
			session.flush();

		}catch(CallbackException e){
			throw new ErroRepositorioException(e, e.getMessage());
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param id
	 *            Descri��o do par�metro
	 * @param pacoteNomeObjeto
	 *            Descri��o do par�metro
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public void remover(int id, String pacoteNomeObjeto, OperacaoEfetuada operacaoEfetuada,
					Collection<UsuarioAcaoUsuarioHelper> acaoUsuarioHelper) throws ErroRepositorioException{

		// obt�m a sess�o
		Session session = HibernateUtil.getSession();

		try{

			Iterator iterator = session.createQuery("from " + pacoteNomeObjeto + " where id = :id").setInteger("id", id).iterate();

			if(!iterator.hasNext()){
				throw new RemocaoRegistroNaoExistenteException();

			}

			while(iterator.hasNext()){
				Object obj = iterator.next();
				if(obj instanceof ObjetoTransacao && operacaoEfetuada != null){
					ObjetoTransacao objetoTransacao = (ObjetoTransacao) obj;
					objetoTransacao.setOperacaoEfetuada(operacaoEfetuada);
					Iterator it = acaoUsuarioHelper.iterator();
					while(it.hasNext()){
						UsuarioAcaoUsuarioHelper helper = (UsuarioAcaoUsuarioHelper) it.next();
						objetoTransacao.adicionarUsuario(helper.getUsuario(), helper.getUsuarioAcao());
					}
				}
				iterator.remove();
			}
			session.flush();
			// restri��es no sistema
		}catch(JDBCException e){
			// e.printStackTrace();
			// levanta a exce��o para a pr�xima camada
			throw new RemocaoInvalidaException(e);
			// erro no hibernate
		}catch(CallbackException e){
			throw new ErroRepositorioException(e, e.getMessage());

		}catch(HibernateException e){
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o
			HibernateUtil.closeSession(session);
		}

	}

	public void removerColecaoObjetos(Collection<Object> colecaoObjetos) throws ErroRepositorioException{

		if(colecaoObjetos != null && !colecaoObjetos.isEmpty()){
			for(Object object : colecaoObjetos){
				remover(object);
			}
		}
	}

	/**
	 * @pram objeto Description of the Parameter
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public void remover(Object objeto) throws ErroRepositorioException{

		// obt�m a sess�o
		Session session = HibernateUtil.getSession();

		try{

			session.delete(objeto);
			session.flush();

			// restri��es no sistema
		}catch(JDBCException e){
			// e.printStackTrace();
			// levanta a exce��o para a pr�xima camada
			throw new RemocaoInvalidaException(e);
			// erro no hibernate
		}catch(CallbackException e){
			throw new ErroRepositorioException(e, e.getMessage());

		}catch(HibernateException e){
			e.printStackTrace();
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * < <Descri��o do m�todo>>
	 */

	/**
	 * Insere ou atualiza um registro na base dependendo da chave
	 * 
	 * @param objeto
	 *            Refer�ncia do objeto a ser inserida
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Erro no mecanismo Hibernate
	 */
	public Object inserirOuAtualizar(Object objeto) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		Object retorno = null;

		try{
			Object retornoMetodo = descobrirIdClasse(objeto);

			if(retornoMetodo == null){
				retorno = session.save(objeto);
			}else{
				session.update(objeto);

			}
			session.flush();

			return retorno;
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate: " + objeto.getClass().getName() + " falhou na inser��o");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	private Object descobrirIdClasse(Object objeto) throws ErroRepositorioException{

		Object retornoMetodo = null;

		try{
			retornoMetodo = objeto.getClass().getMethod("getId", (Class[]) null).invoke(objeto, (Object[]) null);

		}catch(IllegalArgumentException e){
			throw new ErroRepositorioException(e);
		}catch(SecurityException e){
			throw new ErroRepositorioException(e);
		}catch(IllegalAccessException e){
			throw new ErroRepositorioException(e);
		}catch(InvocationTargetException e){
			throw new ErroRepositorioException(e);
		}catch(NoSuchMethodException e){
			try{
				retornoMetodo = objeto.getClass().getMethod("getComp_id", (Class[]) null).invoke(objeto, (Object[]) null);
			}catch(IllegalArgumentException e1){
				throw new ErroRepositorioException(e);
			}catch(SecurityException e1){
				throw new ErroRepositorioException(e);
			}catch(IllegalAccessException e1){
				throw new ErroRepositorioException(e);
			}catch(InvocationTargetException e1){
				throw new ErroRepositorioException(e);
			}catch(NoSuchMethodException e1){
				throw new ErroRepositorioException(e);
			}

		}

		return retornoMetodo;

	}

	/**
	 * Este m�todo de pesquisa serve para localizar qualquer objeto no sistema.
	 * Ele aceita como par�metro um offset que indica a p�gina desejada no
	 * esquema de pagina��o. A pagina��o procura 10 registros de casa vez.
	 * 
	 * @author Rodrigo Silveira
	 * @date 30/03/2006
	 * @param filtro
	 *            Filtro da pesquisa
	 * @param pageOffset
	 *            Indicador da p�gina desejada do esquema de pagina��o
	 * @param pacoteNomeObjeto
	 *            Pacote do objeto
	 * @return Cole��o dos resultados da pesquisa
	 * @throws ErroRepositorioException
	 *             Exce��o do reposit�rio
	 */
	public Collection pesquisar(Filtro filtro, int pageOffset, String pacoteNomeObjeto) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			int idxPry = pacoteNomeObjeto.lastIndexOf("_$$_");
			if(idxPry != -1) pacoteNomeObjeto = pacoteNomeObjeto.substring(0, idxPry);

			String alias = "objeto";
			StringBuilder query = new StringBuilder();
			query.append(" from ").append(pacoteNomeObjeto).append(" ").append(alias);

			boolean isFieldsIndicated = montarQuery(query, filtro, pacoteNomeObjeto, alias);

			// retorno = new ArrayList(new
			// CopyOnWriteArraySet(GeradorHQLCondicional.gerarCondicionalQuery(filtro,
			// pacoteNomeObjeto, "objeto", "from " + pacoteNomeObjeto + " objeto",
			// session).setFirstResult(10 * pageOffset).setMaxResults(10)
			// .list()));

			retorno = GeradorHQLCondicional.gerarCondicionalQuery(filtro, pacoteNomeObjeto, alias, query.toString(), session)
							.setFirstResult(ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONSULTA * pageOffset)
							.setMaxResults(ConstantesSistema.NUMERO_MAXIMO_REGISTROS_CONSULTA).list();

			query.delete(0, query.length());
			query = null;

			if(isFieldsIndicated){
				retorno = transformarColecaoRetorno(retorno, filtro, pacoteNomeObjeto);
			}

			// Carrega os objetos informados no filtro
			if(!filtro.getColecaoCaminhosParaCarregamentoEntidades().isEmpty()){
				PersistenciaUtil.processaObjetosParaCarregamento(filtro.getColecaoCaminhosParaCarregamentoEntidades(), retorno);
			}
			// parametro usado para determinar se quer inicializar os atributos lazies
			if(filtro.isInitializeLazy()){
				inicializarPropriedadesLazies(retorno);
			}

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Informa o n�mero total de registros de uma pesquisa, auxiliando o esquema
	 * de pagina��o
	 * 
	 * @author Rodrigo Silveira
	 * @date 30/03/2006
	 * @param Filtro
	 *            da Pesquisa
	 * @param Pacote
	 *            do objeto pesquisado
	 * @return N�mero de registros da pesquisa
	 * @throws ErroRepositorioException
	 *             Exce��o do reposit�rio
	 */
	public int totalRegistrosPesquisa(Filtro filtro, String pacoteNomeObjeto) throws ErroRepositorioException{

		// cria a cole��o de retorno
		Integer retorno = null;
		// obt�m a sess�o
		Session session = HibernateUtil.getSession();

		try{

			List camposOrderBy = new ArrayList();

			camposOrderBy = filtro.getCamposOrderBy();

			filtro.limparCamposOrderBy();

			filtro.getColecaoCaminhosParaCarregamentoEntidades().clear();

			// pesquisa a cole��o de atividades e atribui a vari�vel "retorno"
			if(filtro.isEntidadeComChaveComposta()){
				retorno = ((Number) GeradorHQLCondicional.gerarCondicionalQuery(filtro, pacoteNomeObjeto, "objeto",
								"select count(*) from " + pacoteNomeObjeto + " objeto", session).uniqueResult()).intValue();
			}else{
				retorno = ((Number) GeradorHQLCondicional.gerarCondicionalQuery(filtro, pacoteNomeObjeto, "objeto",
								"select count(distinct objeto.id) from " + pacoteNomeObjeto + "  objeto", session).uniqueResult())
								.intValue();
			}

			filtro.setCampoOrderBy((String[]) camposOrderBy.toArray(new String[camposOrderBy.size()]));

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
	 * M�todo que insere uma Lista em Batch
	 * inserirBatch
	 * 
	 * @author Roberta Costa
	 * @date 17/05/2006
	 * @param list
	 * @throws ErroRepositorioException
	 */
	public void inserirBatch(List list) throws ErroRepositorioException{

		StatelessSession session = HibernateUtil.getStatelessSession();

		if(list != null && !list.isEmpty()){
			// int i = 1;
			try{
				for(Object obj : list){

					// System.out.println("INSERINDO: " + i + "-" + obj.getClass().getName());
					session.insert(obj);

					/*
					 * if (i % 50 == 0) {
					 * // 20, same as the JDBC batch size
					 * // flush a batch of inserts and release memory:
					 * session.flush();
					 * session.clear();
					 * }
					 * i++;
					 */
				}
				// session.flush();
				// session.clear();
			}catch(HibernateException e){
				// levanta a exce��o para a pr�xima camada
				throw new ErroRepositorioException(e, "Erro no Hibernate");
			}finally{
				HibernateUtil.closeSession(session);
			}
		}
	}

	/**
	 * Recupera a cole��o de feriados nacionais
	 * 
	 * @author Pedro Alexandre
	 * @date 13/09/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<NacionalFeriado> pesquisarFeriadosNacionais() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(NacionalFeriado.class).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * UC?? - ????????
	 * 
	 * @author R�mulo Aur�lio Filho
	 * @date 25/01/2007
	 * @descricao O m�todo retorna um objeto com a maior data de Implementacao
	 *            do Banco e sua ultima alteracao
	 * @return
	 * @throws ErroRepositorioException
	 */

	public DbVersaoImplementada pesquisarDbVersaoImplementada() throws ErroRepositorioException{

		DbVersaoImplementada dbVersaoImplementada = null;

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = "SELECT dbvi " + " FROM DbVersaoImplementada dbvi " + " WHERE dbvi.dataImplementacao = "
							+ " (SELECT MAX(dbvimp.dataImplementacao) " + " FROM DbVersaoImplementada dbvimp)";

			dbVersaoImplementada = (DbVersaoImplementada) session.createQuery(consulta).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return dbVersaoImplementada;
	}

	/**
	 * Retorna o n�mero de Dias �teis entre um per�odo, excluindo Feriado
	 * Nacional, Feriado Municipais e os dias das semana que n�o dever�o ser
	 * considerados dias uteis.
	 * 
	 * @author isilva
	 * @date 20/07/2010
	 * @param municipio
	 * <br>
	 *            Munic�pio, caso n�o seja informado, ser�o considerados todos
	 *            os munic�pios <br>
	 * <br>
	 * @param dataInicio
	 * <br>
	 *            Data inicial <br>
	 * <br>
	 * @param dataFim
	 * <br>
	 *            Data m�xima <br>
	 * <br>
	 * @param diasExcludentes
	 * <br>
	 *            Dias que n�o ser�o considerados dias uteis <br>
	 *            <i>Ex:</i> <br>
	 *            <i>
	 *            Collection<Integer> diasExcludentes = new ArrayList<Integer>();
	 *            diasExcludentes.add(Calendar.SATURDAY);
	 *            diasExcludentes.add(Calendar.SUNDAY);
	 *            </i> <br>
	 * @return
	 * @throws ErroRepositorioException
	 */
	@SuppressWarnings("unchecked")
	public Integer obterQuantidadeDiasUteis(Municipio municipio, Date dataInicio, Date dataFim, Collection<Integer> diasExcludentes)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		/*
		 * extrai a hora o minuto e o segundo da data ex.: Thu May 11 10:12:50
		 * GMT-03:00 2006 o resultado ser� Thu May 11 00:00:00 GMT-03:00 2006
		 */
		dataInicio = Util.formatarDataSemHora(dataInicio);
		dataFim = Util.formatarDataSemHora(dataFim);

		Integer diasUteis = 0;

		Collection<NacionalFeriado> colecaoFeriadoNacional = new ArrayList<NacionalFeriado>();
		Collection<MunicipioFeriado> colecaoFeriadoMunicipal = new ArrayList<MunicipioFeriado>();

		List<Calendar> datasFeriadoMunicipal = new ArrayList<Calendar>();
		List<Calendar> datasFeriadoNacionais = new ArrayList<Calendar>();

		try{

			Criteria criteria = session.createCriteria(MunicipioFeriado.class);

			if(municipio != null){
				criteria.add(Restrictions.eq("municipio", municipio));
			}

			if(dataInicio != null && dataFim != null){
				// criteria.add(Restrictions.between("dataFeriado", dataFeriadoInicio,
				// dataFeriadoFim));

				criteria.add(Restrictions.ge("dataFeriado", dataInicio));
				criteria.add(Restrictions.le("dataFeriado", dataFim));

			}else if(dataInicio != null){
				criteria.add(Restrictions.eq("dataFeriado", dataInicio));
			}else if(dataFim != null){
				criteria.add(Restrictions.eq("dataFeriado", dataFim));
			}

			colecaoFeriadoMunicipal = criteria.list();

			if(colecaoFeriadoMunicipal != null && !colecaoFeriadoMunicipal.isEmpty()){
				for(MunicipioFeriado municipioFeriado : colecaoFeriadoMunicipal){
					Calendar c = Calendar.getInstance();
					c.setTime(municipioFeriado.getDataFeriado());
					datasFeriadoMunicipal.add(c);
				}
			}

			// **********************************************************************

			criteria = session.createCriteria(NacionalFeriado.class);

			if(dataInicio != null && dataFim != null){
				criteria.add(Expression.ge("data", dataInicio));
				criteria.add(Expression.le("data", dataFim));

			}else if(dataInicio != null){
				criteria.add(Restrictions.eq("data", dataInicio));
			}else if(dataFim != null){
				criteria.add(Restrictions.eq("data", dataFim));
			}

			colecaoFeriadoNacional = criteria.list();

			if(colecaoFeriadoNacional != null && !colecaoFeriadoNacional.isEmpty()){
				for(NacionalFeriado nacionalFeriado : colecaoFeriadoNacional){
					Calendar c = Calendar.getInstance();
					c.setTime(nacionalFeriado.getData());
					datasFeriadoNacionais.add(c);
				}
			}

			while(dataInicio.compareTo(dataFim) <= 0){
				if(Util.ehDiaUtil(dataInicio, datasFeriadoNacionais, datasFeriadoMunicipal, diasExcludentes)){
					diasUteis++;
				}

				dataInicio = Util.adicionarNumeroDiasDeUmaData(dataInicio, 1);
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return diasUteis;
	}

	public <T extends Object> T pesquisarPorCodigo(String codigoConstante, Class<T> clazz) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{
			Criteria criteria = session.createCriteria(clazz);
			criteria.add(Restrictions.eq("codigoConstante", codigoConstante));
			return (T) criteria.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Obt�m uma cole��o de registros da tabela relacionada associados a uma determinada
	 * constante
	 * 
	 * @author Anderson Italo
	 * @date 31/07/2012
	 */
	public <T extends Object> T pesquisarColecaoObjetosPorCodigoConstante(String codigoConstante, Class<T> clazz)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{

			Criteria criteria = session.createCriteria(clazz);
			criteria.add(Restrictions.eq("codigoConstante", codigoConstante));
			return (T) criteria.list();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			// fecha a sess�o
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Este m�todo de pesquisa serve para localizar qualquer objeto no sistema.
	 * Ele aceita como par�metro um offset que indica a p�gina desejada no
	 * esquema de pagina��o. Al�m disso tamb�m recebe o n�mero m�ximo de registros
	 * por p�gina da pagina��o.
	 * 
	 * @author Felipe Rosacruz
	 * @date 19/12/2013
	 * @param filtro
	 *            Filtro da pesquisa
	 * @param pageOffset
	 *            Indicador da p�gina desejada do esquema de pagina��o
	 * @param pacoteNomeObjeto
	 *            Pacote do objeto
	 * @param qtdRegistrosPorPagina
	 *            Quantidade m�xima de registros por p�gina
	 * @return Cole��o dos resultados da pesquisa
	 * @throws ControladorException
	 *             Exce��o do controlador
	 */

	public Collection pesquisar(Filtro filtro, int pageOffset, String pacoteNomeObjeto, int qtdRegistrosPorPagina)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			int idxPry = pacoteNomeObjeto.lastIndexOf("_$$_");
			if(idxPry != -1) pacoteNomeObjeto = pacoteNomeObjeto.substring(0, idxPry);

			String alias = "objeto";
			StringBuilder query = new StringBuilder();
			query.append(" from ").append(pacoteNomeObjeto).append(" ").append(alias);

			boolean isFieldsIndicated = montarQuery(query, filtro, pacoteNomeObjeto, alias);

			// retorno = new ArrayList(new
			// CopyOnWriteArraySet(GeradorHQLCondicional.gerarCondicionalQuery(filtro,
			// pacoteNomeObjeto, "objeto", "from " + pacoteNomeObjeto + " objeto",
			// session).setFirstResult(10 * pageOffset).setMaxResults(10)
			// .list()));

			retorno = GeradorHQLCondicional.gerarCondicionalQuery(filtro, pacoteNomeObjeto, alias, query.toString(), session)
							.setFirstResult(qtdRegistrosPorPagina * pageOffset).setMaxResults(qtdRegistrosPorPagina).list();

			query.delete(0, query.length());
			query = null;

			if(isFieldsIndicated){
				retorno = transformarColecaoRetorno(retorno, filtro, pacoteNomeObjeto);
			}

			// Carrega os objetos informados no filtro
			if(!filtro.getColecaoCaminhosParaCarregamentoEntidades().isEmpty()){
				PersistenciaUtil.processaObjetosParaCarregamento(filtro.getColecaoCaminhosParaCarregamentoEntidades(), retorno);
			}
			// parametro usado para determinar se quer inicializar os atributos lazies
			if(filtro.isInitializeLazy()){
				inicializarPropriedadesLazies(retorno);
			}

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Recupera a cole��o de feriados municipais
	 * 
	 * @author Anderson Italo
	 * @date 13/06/2014
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<MunicipioFeriado> pesquisarFeriadosMunicipais() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			return session.createCriteria(MunicipioFeriado.class).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

}
