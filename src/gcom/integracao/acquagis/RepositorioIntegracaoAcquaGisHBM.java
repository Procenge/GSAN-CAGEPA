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
package gcom.integracao.acquagis;

import gcom.util.ErroRepositorioException;
import gcom.util.HibernateUtil;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * O repositório faz a comunicação com a base de dados através do hibernate.
 * 
 * @author Josenildo Neves
 */
public class RepositorioIntegracaoAcquaGisHBM
				implements IRepositorioIntegracaoAcquaGis {

	private static Logger LOGGER = Logger.getLogger(RepositorioIntegracaoAcquaGisHBM.class);
	private static IRepositorioIntegracaoAcquaGis instancia;

	/**
	 * Retorna o valor de instância
	 * 
	 * @return O valor de instância
	 */
	public static IRepositorioIntegracaoAcquaGis getInstancia(){

		if(instancia == null){
			instancia = new RepositorioIntegracaoAcquaGisHBM();
		}

		return instancia;
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.integracao.acquagis.IRepositorioIntegracaoAcquaGis#excluirContaAtualizada()
	 */
	public void excluirContaAtualizada() throws ErroRepositorioException{

		StringBuilder hql = new StringBuilder();

		hql.append("DELETE CONTA_ATUALIZADA ");

		Session session = HibernateUtil.getSession();
		try{
			Query query = session.createSQLQuery(hql.toString());
			query.executeUpdate();
		}catch(HibernateException e){
			LOGGER.info("Erro no Hibernate - Erro ao excluir dados da tabela conta atualizada");
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}
}
