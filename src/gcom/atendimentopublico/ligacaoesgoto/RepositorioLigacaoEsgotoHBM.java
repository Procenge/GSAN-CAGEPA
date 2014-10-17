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

package gcom.atendimentopublico.ligacaoesgoto;

import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.HibernateUtil;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * Implementa��o do Reposit�rio de Liga��o de Esgoto
 * 
 * @author Leonardo Regis
 * @date 08/09/2006
 */
public class RepositorioLigacaoEsgotoHBM
				implements IRepositorioLigacaoEsgoto {

	private static IRepositorioLigacaoEsgoto instancia;

	/**
	 * Construtor da classe RepositorioLigacaoEsgotoHBM
	 */
	private RepositorioLigacaoEsgotoHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return instancia
	 */
	public static IRepositorioLigacaoEsgoto getInstancia(){

		if(instancia == null){
			instancia = new RepositorioLigacaoEsgotoHBM();
		}
		return instancia;
	}

	/**
	 * [UC0464] Atualizar Volume M�nimo da Liga��o de Esgoto
	 * [SB0001] Atualizar Liga��o de Esgoto.
	 * 
	 * @author Leonardo Regis
	 * @date 22/09/2006
	 * @param ligacaoEsgoto
	 * @throws ErroRepositorioException
	 */
	public void atualizarVolumeMinimoLigacaoEsgoto(LigacaoEsgoto ligacaoEsgoto) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String update;
		try{
			update = "update LigacaoEsgoto set ";

			if(ligacaoEsgoto.getConsumoMinimo() != null){
				update = update + "consumoMinimo = :consumoMinimo, ";
			}else{
				update = update + "consumoMinimo = null, ";
			}

			update = update + "ultimaAlteracao = :dataCorrente " + "where id = :ligacaoEsgotoId";

			if(ligacaoEsgoto.getConsumoMinimo() != null){
				session.createQuery(update).setInteger("consumoMinimo", ligacaoEsgoto.getConsumoMinimo()).setTimestamp("dataCorrente",
								ligacaoEsgoto.getUltimaAlteracao()).setInteger("ligacaoEsgotoId", ligacaoEsgoto.getId()).executeUpdate();
			}else{
				session.createQuery(update).setTimestamp("dataCorrente", ligacaoEsgoto.getUltimaAlteracao()).setInteger("ligacaoEsgotoId",
								ligacaoEsgoto.getId()).executeUpdate();
			}
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscaliza��o
	 * [SB0004] - Calcular Valor de �gua e/ou Esgoto
	 * 
	 * @author S�vio Luiz
	 * @date 04/12/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public BigDecimal recuperarPercentualEsgoto(Integer idLigacaoEsgoto) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;
		BigDecimal retorno = null;
		try{
			consulta = "SELECT ligEsgoto.percentual " + "FROM LigacaoEsgoto ligEsgoto " + "WHERE ligEsgoto.id = :idLigacaoEsgoto";

			retorno = (BigDecimal) session.createQuery(consulta).setInteger("idLigacaoEsgoto", idLigacaoEsgoto).setMaxResults(1)
							.uniqueResult();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public BigDecimal recuperarPercentualEsgotoPerfil(Integer idLigacaoEsgoto) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;
		BigDecimal retorno = null;
		try{
			consulta = "SELECT ligEsgotoPerfil.percentualEsgotoConsumidaColetada " + "FROM LigacaoEsgotoPerfil ligEsgotoPerfil "
							+ "WHERE ligEsgotoPerfil.id = :idLigacaoEsgotoPerfil";

			retorno = (BigDecimal) session.createQuery(consulta).setInteger("idLigacaoEsgotoPerfil", idLigacaoEsgoto)
							.setMaxResults(1).uniqueResult();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0349] Emitir Documento de Cobran�a - Ordem de Fiscaliza��o
	 * 
	 * @author S�vio Luiz
	 * @date 21/12/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer recuperarConsumoMinimoEsgoto(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;
		Integer retorno = null;
		try{
			consulta = "SELECT ligEsgoto.consumoMinimo " + "FROM LigacaoEsgoto ligEsgoto " + "WHERE ligEsgoto.id = :idImovel";

			retorno = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * @author isilva
	 * @date 09/02/2011
	 * @param idImovel
	 * @param situacao
	 * @throws ErroRepositorioException
	 */
	public void atualizarLigacaoEsgotoSituacaoImovel(Integer idImovel, Integer situacao) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		StringBuffer update = new StringBuffer("update Imovel im set ");
		try{

			if(idImovel != null && situacao != null){
				update.append("im.ligacaoEsgotoSituacao.id = :situacaoEsgoto, ");
				update.append("im.ultimaAlteracao = :ultimaAlteracao ");
				update.append("where im.id = :idImovel");

				session.createQuery(update.toString()).setInteger("situacaoEsgoto", situacao).setInteger("idImovel", idImovel)
								.setTimestamp("ultimaAlteracao", new Date()).executeUpdate();
			}

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * @author Ailton Sousa
	 * @date 12/08/2011
	 *       M�todo retorna uma LigacaoEsgoto, buscado por um Id
	 * @param id
	 *            - id da Liga��o [obrigat�rio]
	 * @return
	 * @throws ErroRepositorioException
	 */
	public LigacaoEsgoto pesquisarLigacaoEsgoto(Integer id) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		LigacaoEsgoto retorno = null;

		try{

			String consulta = "FROM LigacaoEsgoto ligEsgoto " + " WHERE ligEsgoto.id = :idLigacaoEsgoto";

			retorno = (LigacaoEsgoto) session.createQuery(consulta).setInteger("idLigacaoEsgoto", id).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * @param id
	 * @return
	 * @throws ErroRepositorioException
	 */
	public LigacaoEsgotoSituacao pesquisarLigacaoEsgotoSituacao(Integer id) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		LigacaoEsgotoSituacao retorno = null;

		try{

			String consulta = "FROM LigacaoEsgotoSituacao ligEsgotoSit WHERE ligEsgotoSit.id = :id";

			retorno = (LigacaoEsgotoSituacao) session.createQuery(consulta).setInteger("id", id).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	public BigDecimal recuperarPercentualColetaEsgoto(Integer idLigacaoEsgoto) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;
		BigDecimal retorno = null;
		try{
			consulta = "SELECT ligEsgoto.percentualAguaConsumidaColetada " + "FROM LigacaoEsgoto ligEsgoto "
							+ "WHERE ligEsgoto.id = :idLigacaoEsgoto";

			retorno = (BigDecimal) session.createQuery(consulta).setInteger("idLigacaoEsgoto", idLigacaoEsgoto).setMaxResults(1)
							.uniqueResult();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0464] Atualizar Perfil da Liga��o de Esgoto
	 * [SB0001] Atualizar Liga��o de Esgoto.
	 * 
	 * @author �talo Almeida
	 * @date 02/08/2013
	 * @param ligacaoEsgoto
	 * @throws ErroRepositorioException
	 */
	public void atualizarPerfilLigacaoEsgoto(LigacaoEsgoto ligacaoEsgoto) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String update;
		try{
			update = "update LigacaoEsgoto set ";

			if(ligacaoEsgoto.getLigacaoEsgotoPerfil() != null){
				update = update + "ligacaoEsgotoPerfil = :ligacaoEsgotoPerfil, ";
				update = update + "percentual = :percentual, ";

			}else{
				update = update + "ligacaoEsgotoPerfil = null, ";
			}

			update = update + "ultimaAlteracao = :dataCorrente " + "where id = :ligacaoEsgotoId";

			if(ligacaoEsgoto.getLigacaoEsgotoPerfil() != null){
				session.createQuery(update).setInteger("ligacaoEsgotoPerfil", ligacaoEsgoto.getLigacaoEsgotoPerfil().getId())
								.setBigDecimal("percentual", ligacaoEsgoto.getPercentual())
								.setTimestamp("dataCorrente", ligacaoEsgoto.getUltimaAlteracao())
								.setInteger("ligacaoEsgotoId", ligacaoEsgoto.getId()).executeUpdate();
			}else{
				session.createQuery(update).setTimestamp("dataCorrente", ligacaoEsgoto.getUltimaAlteracao())
								.setInteger("ligacaoEsgotoId", ligacaoEsgoto.getId()).executeUpdate();
			}
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

}