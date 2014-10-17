
package br.com.procenge.comum.negocio.impl;

import gcom.gui.ActionServletException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import br.com.procenge.comum.exception.ConcorrenciaException;
import br.com.procenge.comum.exception.NegocioException;
import br.com.procenge.comum.negocio.api.ControladorNegocio;
import br.com.procenge.comum.negocio.api.EntidadeNegocio;

/**
 * @autor gilberto
 */
public abstract class ControladorNegocioImpl
				extends HibernateDaoSupport
				implements ControladorNegocio {

	private final String ERRO_ENTIDADE_VERSAO_DESATUALIZADA = "ERRO_ENTIDADE_VERSAO_DESATUALIZADA";

	public void atualizar(EntidadeNegocio entidadeNegocio) throws ConcorrenciaException, NegocioException{

		validarDadosDaEntidade(entidadeNegocio);
		// validarVersaoEntidade(entidadeNegocio);
		preAtualizacao(entidadeNegocio);
		// entidadeNegocio.incrementarVersao();
		getHibernateTemplate().getSessionFactory().getCurrentSession().update(entidadeNegocio);
		posAtualizacao(entidadeNegocio);
	}

	public abstract EntidadeNegocio criar();

	public abstract Class getClasseEntidade();

	public long inserir(EntidadeNegocio entidadeNegocio) throws NegocioException{

		Long chavePrimaria = null;

		validarDadosDaEntidade(entidadeNegocio);
		preInsercao(entidadeNegocio);
		try{
			chavePrimaria = (Long) getHibernateTemplate().getSessionFactory().getCurrentSession().save(entidadeNegocio);
			getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
		}catch(ConstraintViolationException e){
			if(e.getErrorCode() == 1){
				// throw new NegocioException(Constantes.ERRO_REG_DUPLICIDADE);
				throw new ActionServletException("atencao.duplicado.parametro.sistema.valor");
			}
			throw e;
		}
		posInsercao(entidadeNegocio);

		return chavePrimaria.longValue();
	}

	public EntidadeNegocio obter(long chavePrimaria) throws NegocioException{

		return this.obter(chavePrimaria, getClasseEntidade(), false);
	}

	public EntidadeNegocio obter(long chavePrimaria, boolean carregarLazy) throws NegocioException{

		return this.obter(chavePrimaria, getClasseEntidade(), carregarLazy);
	}

	public EntidadeNegocio obter(long chavePrimaria, Class classe) throws NegocioException{

		return this.obter(chavePrimaria, classe, false);
	}

	public EntidadeNegocio obter(long chavePrimaria, Class classe, boolean carregarLazy) throws NegocioException{

		EntidadeNegocio entidade = null;

		entidade = (EntidadeNegocio) getHibernateTemplate().getSessionFactory().getCurrentSession().get(classe, chavePrimaria);

		if(entidade == null){
			throw new NegocioException("Entidade não encontrada: " + classe.getName());
		}

		if(carregarLazy){
			entidade.carregarLazy();
		}

		return entidade;
	}

	public void posAtualizacao(EntidadeNegocio entidadeNegocio) throws NegocioException{

	}

	public void posInsercao(EntidadeNegocio entidadeNegocio) throws NegocioException{

	}

	public void posRemocao(EntidadeNegocio entidadeNegocio) throws NegocioException{

	}

	public void preAtualizacao(EntidadeNegocio entidadeNegocio) throws NegocioException{

	}

	public void preInsercao(EntidadeNegocio entidadeNegocio) throws NegocioException{

	}

	public void preRemocao(EntidadeNegocio entidadeNegocio) throws NegocioException{

	}

	public void remover(EntidadeNegocio entidadeNegocio) throws NegocioException{

		preRemocao(entidadeNegocio);
		getHibernateTemplate().getSessionFactory().getCurrentSession().delete(entidadeNegocio);
		posRemocao(entidadeNegocio);
	}

	public void validarDadosDaEntidade(EntidadeNegocio entidadeNegocio) throws NegocioException{

		Map erros = entidadeNegocio.validarDados();
		if(erros != null && !erros.isEmpty()){
			throw new NegocioException(erros);
		}
	}

	public void validarVersaoEntidade(EntidadeNegocio entidadeNegocio) throws ConcorrenciaException, NegocioException{

		Integer versaoAtual = null;
		Query query = null;
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT e.versao FROM ");
		sql.append(getClasseEntidade().getName());
		sql.append(" AS e WHERE e.chavePrimaria = :chavePrimaria");

		query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(sql.toString());

		query.setLong(EntidadeNegocio.ATRIBUTO_CHAVE_PRIMARIA, Long.valueOf(entidadeNegocio.getChavePrimaria()));
		versaoAtual = (Integer) query.uniqueResult();

		if(entidadeNegocio.getVersao() != versaoAtual.intValue()){
			throw new ConcorrenciaException(ERRO_ENTIDADE_VERSAO_DESATUALIZADA, getClasseEntidade().getName());
		}

	}

	protected void carregarLazys(Collection entidades) throws NegocioException{

		EntidadeNegocio entidade = null;
		for(Iterator iterator = entidades.iterator(); iterator.hasNext();){
			entidade = (EntidadeNegocio) iterator.next();
			entidade.carregarLazy();
		}
	}

}
