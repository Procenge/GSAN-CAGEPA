/**
 * 
 */

package br.com.procenge.parametrosistema.impl;

import gcom.gui.ActionServletException;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.AtualizacaoInvalidaException;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.RepositorioUtilHBM;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.exception.ConstraintViolationException;

import br.com.procenge.comum.exception.ConcorrenciaException;
import br.com.procenge.comum.exception.NegocioException;
import br.com.procenge.comum.negocio.api.EntidadeNegocio;
import br.com.procenge.comum.negocio.impl.ControladorNegocioImpl;
import br.com.procenge.parametrosistema.api.ControladorParametroSistema;
import br.com.procenge.parametrosistema.api.ParametroSistema;
import br.com.procenge.parametrosistema.api.TipoParametroSistema;
import br.com.procenge.util.SpringBeanLocator;

/**
 * @author gmatos
 */
class ControladorParametroSistemaImpl
				extends ControladorNegocioImpl
				implements ControladorParametroSistema {

	private static final Logger LOG = Logger.getLogger(ControladorParametroSistemaImpl.class);

	private static final String PARAMETRO_QUERY_CODIGO = "codigo";

	private static final String QUERY_OBTER_PARAMETRO_SISTEMA_POR_CODIGO = "obterParametroSistemaPorCodigo";

	private static final String QUERY_CONSULTAR_PARAMETRO_SISTEMA_VALOR = "consultarParametroSistemaValor";

	private static final String QUERY_CONSULTAR_PARAMETRO_SISTEMA = "consultarParametroSistema";

	SessionContext sessionContext;

	public EntidadeNegocio criar(){

		return (EntidadeNegocio) SpringBeanLocator.getInstancia().getBeanPorID(ParametroSistema.BEAN_ID_PARAMETRO_SISTEMA);
	}

	/**
	 * Seta o valor de sessionContext
	 * 
	 * @param sessionContext
	 *            O novo valor de sessionContext
	 */
	public void setSessionContext(SessionContext sessionContext){

		this.sessionContext = sessionContext;
	}

	public List<ParametroSistema> consultarParametroSistema() throws NegocioException{

		Query query = null;

		query = getHibernateTemplate().getSessionFactory().getCurrentSession().getNamedQuery(QUERY_CONSULTAR_PARAMETRO_SISTEMA);

		return (List<ParametroSistema>) query.list();
	}

	public List<ParametroSistemaValor> obterParametroSistemaValor(ParametroSistema paramSistema) throws ControladorException{

		Query query = null;

		query = getHibernateTemplate().getSessionFactory().getCurrentSession().getNamedQuery(QUERY_CONSULTAR_PARAMETRO_SISTEMA_VALOR);
		query.setLong("chavePrimaria", paramSistema.getChavePrimaria());

		return (List<ParametroSistemaValor>) query.list();
	}

	public List<ParametroSistemaValor> obterParametroSistemaValor(Long chavePrimaria) throws ControladorException{

		Query query = null;

		query = getHibernateTemplate().getSessionFactory().getCurrentSession().getNamedQuery(QUERY_CONSULTAR_PARAMETRO_SISTEMA_VALOR);
		query.setLong("chavePrimaria", chavePrimaria);

		return (List<ParametroSistemaValor>) query.list();
	}

	public long inserirParametroSistema(String codigo, String descricao, Object valor) throws NegocioException{

		LOG.info("### inserir parametro: " + codigo + ", '" + descricao + "', " + valor);
		long idParametro = 0;
		Query query = null;
		ParametroSistema parametroSistema = null;

		query = getHibernateTemplate().getSessionFactory().getCurrentSession().getNamedQuery(QUERY_OBTER_PARAMETRO_SISTEMA_POR_CODIGO);

		query.setParameter(PARAMETRO_QUERY_CODIGO, codigo);
		parametroSistema = (ParametroSistema) query.uniqueResult();

		if(parametroSistema == null){
			parametroSistema = (ParametroSistema) SpringBeanLocator.getInstancia().getBeanPorID(ParametroSistema.BEAN_ID_PARAMETRO_SISTEMA);
			TipoParametroSistema tipo = null;

			parametroSistema.setCodigo(codigo);
			parametroSistema.setDescricao(descricao);
			parametroSistema.setUltimaAlteracao(new Date());
			parametroSistema.setUso(1);

			if(valor instanceof String){
				tipo = new TipoParametroSistemaImpl(TipoParametroSistema.TIPO_ESTATICO);
				parametroSistema.setValor(String.valueOf(valor));
			}else{
				Integer id = null;
				Method metodo = null;

				tipo = new TipoParametroSistemaImpl(TipoParametroSistema.TIPO_DINAMICO);

				try{
					metodo = valor.getClass().getMethod("getId");
					id = (Integer) metodo.invoke(valor, new Object[] {});
				}catch(SecurityException e){
					throw new NegocioException(e);
				}catch(NoSuchMethodException e){
					LOG.error("Objeto não possui método getId(): " + e.getMessage());
					throw new NegocioException("Objeto não possui método getId(): " + e.getMessage());
				}catch(IllegalArgumentException e){
					throw new NegocioException(e);
				}catch(IllegalAccessException e){
					throw new NegocioException(e);
				}catch(InvocationTargetException e){
					throw new NegocioException(e);
				}

				parametroSistema.setValor(String.valueOf(id));
				parametroSistema.setClasseEntidade(valor.getClass().getName());
			}

			parametroSistema.setTipoParametroSistema(tipo);

			idParametro = (Long) getHibernateTemplate().getSessionFactory().getCurrentSession().save(parametroSistema);

		}else{
			LOG.error("Parâmetro já está cadastrado: " + codigo);
			throw new NegocioException("Parâmetro já está cadastrado: " + codigo);
		}

		return idParametro;
	}

	public Object obterValorDoParametroPorCodigo(String codigo) throws NegocioException{

		LOG.info("### obterValor parametro: " + codigo);
		ParametroSistema parametro = null;
		Object retorno = null;
		Query query = null;

		query = getHibernateTemplate().getSessionFactory().getCurrentSession().getNamedQuery(QUERY_OBTER_PARAMETRO_SISTEMA_POR_CODIGO);

		query.setParameter(PARAMETRO_QUERY_CODIGO, codigo);
		parametro = (ParametroSistema) query.uniqueResult();

		if(parametro != null){
			retorno = parametro.getValor();
		}else{
			LOG.error("Parâmetro não encontrado: " + codigo);
			throw new NegocioException("Parâmetro não encontrado: " + codigo);
		}
		return retorno;
	}

	public Object obterValorDoParametroPorCodigo(String codigo, Integer idFuncionalidade) throws NegocioException{

		LOG.info("### obterValor parametro: " + codigo);

		ParametroSistema parametro = (ParametroSistema) obterParametroPorCodigo(codigo, idFuncionalidade);

		return parametro.getValor();
	}

	public Class getClasseEntidade(){

		return SpringBeanLocator.getInstancia().getBeanPorID(ParametroSistema.BEAN_ID_PARAMETRO_SISTEMA).getClass();
	}

	public Object obterParametroPorCodigo(String codigo, Integer idFuncionalidade) throws NegocioException{

		// thiagosantos: mudança da forma de realizar a consulta para usar cache.
		StringBuilder consuta = new StringBuilder();

		consuta.append(" select ps.pasi_id as id, ps.pasi_cdparametro as cdpar, ps.pasi_dsparametro as dspar, ");
		consuta.append(" decode(pf.psfn_id, null, ps.pasi_vlparametro, pf.psfn_vlparametro) pasi_vlparametro, ");
		consuta.append(" ps.pasi_nmclasseentidade as cls, ps.pasi_cdtipoparametro as tpar, ps.pasi_nnversao as ver, ");
		consuta.append(" ps.pasi_icuso as uso, ps.pasi_tmultimaalteracao as malt");
		consuta.append(" FROM parametro_sistema ps LEFT JOIN parametro_sist_funcionalidade pf ");
		consuta.append(" ON ps.pasi_id = pf.pasi_id");
		if(idFuncionalidade != null){
			consuta.append(" AND pf.fncd_id = :funcionalidade");
		}else{
			consuta.append(" AND pf.fncd_id is null ");
		}
		consuta.append(" where ps.pasi_cdparametro = :codigo");

		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(consuta.toString());
		query.addScalar("id", Hibernate.LONG);
		query.addScalar("cdpar", Hibernate.STRING);
		query.addScalar("dspar", Hibernate.STRING);
		query.addScalar("pasi_vlparametro", Hibernate.STRING);
		query.addScalar("cls", Hibernate.STRING);
		query.addScalar("tpar", Hibernate.INTEGER);
		query.addScalar("ver", Hibernate.INTEGER);
		query.addScalar("uso", Hibernate.INTEGER);
		query.addScalar("malt", Hibernate.DATE);

		if(idFuncionalidade != null){
			query.setInteger("funcionalidade", idFuncionalidade);
		}
		query.setString("codigo", codigo);
		query.setCacheable(true);
		query.setCacheRegion("SQL_SistemaParametro");
		query.setMaxResults(1);

		Object[] valores = (Object[]) query.uniqueResult();

		if(valores == null){
			LOG.error("Parâmetro não encontrado: " + codigo);
			throw new NegocioException("Parâmetro não encontrado: " + codigo);
		}

		int i = 0;
		ParametroSistema parametro = new ParametroSistemaImpl();
		parametro.setChavePrimaria(((Number) valores[i++]).longValue());
		parametro.setCodigo((String) valores[i++]);
		parametro.setDescricao((String) valores[i++]);
		parametro.setValor((String) valores[i++]);
		parametro.setClasseEntidade((String) valores[i++]);
		parametro.setTipoParametroSistema(new TipoParametroSistemaImpl());
		parametro.getTipoParametroSistema().setCodigo(((Number) valores[i++]).intValue());
		parametro.setVersao(((Number) valores[i++]).intValue());
		parametro.setUso(((Number) valores[i++]).intValue());
		parametro.setUltimaAlteracao((Date) valores[i++]);

		return parametro;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object obterParametroPorCodigo(String codigo) throws NegocioException{

		ParametroSistema parametro = (ParametroSistema) obterParametroPorCodigo(codigo, null);

		if(parametro == null){
			LOG.error("Parâmetro não encontrado: " + codigo);
			throw new NegocioException("Parâmetro não encontrado: " + codigo);
		}

		return parametro;
	}

	public void atualizarValorParametroSistema(String codigo, String valor) throws NegocioException{

		LOG.info("### atualizar valor do parametro: " + codigo + "', " + valor);
		Query query = null;
		ParametroSistema parametroSistema = null;

		query = getHibernateTemplate().getSessionFactory().getCurrentSession().getNamedQuery(QUERY_OBTER_PARAMETRO_SISTEMA_POR_CODIGO);

		query.setParameter(PARAMETRO_QUERY_CODIGO, codigo);
		parametroSistema = (ParametroSistema) query.uniqueResult();

		if(parametroSistema != null){

			if(valor instanceof String){

				parametroSistema.setValor(String.valueOf(valor));
			}else{

				LOG.error("Informe o valor do parâmetro: " + codigo);
				throw new NegocioException("Informe o valor do parâmetro: " + codigo);
			}

			parametroSistema.setUltimaAlteracao(new Date());

			getHibernateTemplate().getSessionFactory().getCurrentSession().update(parametroSistema);

		}else{
			LOG.error("Parâmetro não está cadastrado: " + codigo);
			throw new NegocioException("Parâmetro não está cadastrado: " + codigo);
		}
	}

	/**
	 * @autor Felipe Rosacruz Constantino
	 * @date 23/05/2013
	 * @param paramValor
	 * @param usuarioLogado
	 * @return
	 * @throws ControladorException
	 */
	public void atualizarParametroSistema(ParametroSistema parametroSistema, Usuario usuarioLogado) throws ControladorException{

		try{
			List<ParametroSistema> parametroSistemas = consultarParametroSistema();
			ParametroSistemaImpl parametro = null;

			for(ParametroSistema param : parametroSistemas){
				if(param.getCodigo().equals(parametroSistema.getCodigo())){
					parametro = (ParametroSistemaImpl) param;
					break;
				}
			}

			if(parametro == null){
				throw new AtualizacaoInvalidaException();
			}

			parametro.setDescricao(parametroSistema.getDescricao());
			parametro.setTipoParametroSistema(parametroSistema.getTipoParametroSistema());
			parametro.setValor(parametroSistema.getValor());
			parametro.setUso(parametroSistema.getUso());

			// Registrar transação
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_PARAMETRO_SISTEMA_ALTERAR,
							(int) parametro.getChavePrimaria(), (int) parametro.getChavePrimaria(), new UsuarioAcaoUsuarioHelper(
											usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			registradorOperacao.registrarOperacao(parametro);

			atualizar(parametro);

		}catch(ErroRepositorioException e1){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e1);
		}catch(NegocioException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(ConcorrenciaException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @autor Felipe Rosacruz Constantino
	 * @date 23/05/2013
	 * @param paramValor
	 * @param usuarioLogado
	 * @return
	 * @throws ControladorException
	 */
	public void excluirParametroSistemaValor(ParametroSistemaValor paramValor, Usuario usuarioLogado) throws ControladorException{

		try{
			// Registrar transação
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_PARAMETRO_SISTEMA_VALOR_REMOVER, paramValor
							.getId().intValue(), paramValor.getId().intValue(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
							UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			registradorOperacao.registrarOperacao(paramValor);

			RepositorioUtilHBM.getInstancia().remover(paramValor);

		}catch(ErroRepositorioException e1){
			getHibernateTemplate().getSessionFactory().getCurrentSession().delete(paramValor);
			throw new ControladorException("erro.sistema", e1);
		}
	}

	/**
	 * @autor Felipe Rosacruz Constantino
	 * @date 23/05/2013
	 * @param paramValor
	 * @param usuarioLogado
	 * @return
	 * @throws ControladorException
	 */
	public void inserirParametroSistemaValor(ParametroSistemaValor paramValor, Usuario usuarioLogado) throws ControladorException{

		try{
			// Registrar transação
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_PARAMETRO_SISTEMA_VALOR_INSERIR,
							new UsuarioAcaoUsuarioHelper(usuarioLogado,
							UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			registradorOperacao.registrarOperacao(paramValor);

			RepositorioUtilHBM.getInstancia().inserir(paramValor);

		}catch(ErroRepositorioException e1){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e1);
		}catch(ConstraintViolationException e){
			if(e.getErrorCode() == 1){
				// throw new NegocioException(Constantes.ERRO_REG_DUPLICIDADE);
				throw new ActionServletException("atencao.duplicado.parametro.sistema.valor");
			}
			throw e;
		}

	}

	/**
	 * @autor Victon Malcolm Rodrigues dos Santos
	 * @date 20/05/2013
	 * @param paramValor
	 * @param usuarioLogado
	 * @return
	 * @throws ControladorException
	 * @exception ErroRepositorioException
	 */
	public void atualizarParametroSistemaValor(ParametroSistemaValor paramValor, Usuario usuarioLogado) throws ControladorException{

		try{
			List<ParametroSistemaValor> parametroValorSistemas = obterParametroSistemaValor(paramValor.getParametroSistema().getChavePrimaria());
			ParametroSistemaValor parametro = null;

			for(ParametroSistemaValor param : parametroValorSistemas){
				if(param.getId().equals(paramValor.getId())){
					parametro = (ParametroSistemaValor) param;
					break;
				}
			}

			if(parametro == null){
				throw new AtualizacaoInvalidaException();
			}

			parametro.setDescricao(paramValor.getDescricao());
			// Registrar transação
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_PARAMETRO_SISTEMA_VALOR_ATUALIZAR,
							parametro.getId().intValue(), parametro.getId().intValue(),
							new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			registradorOperacao.registrarOperacao(parametro);

			RepositorioUtilHBM.getInstancia().atualizar(parametro);

		}catch(ErroRepositorioException e1){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e1);
		}

	}

}
