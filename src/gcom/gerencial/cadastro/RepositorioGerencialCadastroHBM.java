/*
 * Copyright (C) 2007-2007 the GSAN ï¿½ Sistema Integrado de Gestï¿½o de Serviï¿½os de Saneamento
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
 * Foundation, Inc., 59 Temple Place ï¿½ Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN ï¿½ Sistema Integrado de Gestï¿½o de Serviï¿½os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araï¿½jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Clï¿½udio de Andrade Lira
 * Denys Guimarï¿½es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabï¿½ola Gomes de Araï¿½jo
 * Flï¿½vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Jï¿½nior
 * Homero Sampaio Cavalcanti
 * Ivan Sï¿½rgio da Silva Jï¿½nior
 * Josï¿½ Edmar de Siqueira
 * Josï¿½ Thiago Tenï¿½rio Lopes
 * Kï¿½ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Mï¿½rcio Roberto Batista da Silva
 * Maria de Fï¿½tima Sampaio Leite
 * Micaela Maria Coelho de Araï¿½jo
 * Nelson Mendonï¿½a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrï¿½a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araï¿½jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sï¿½vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa ï¿½ software livre; vocï¿½ pode redistribuï¿½-lo e/ou
 * modificï¿½-lo sob os termos de Licenï¿½a Pï¿½blica Geral GNU, conforme
 * publicada pela Free Software Foundation; versï¿½o 2 da
 * Licenï¿½a.
 * Este programa ï¿½ distribuï¿½do na expectativa de ser ï¿½til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implï¿½cita de
 * COMERCIALIZAï¿½ï¿½O ou de ADEQUAï¿½ï¿½O A QUALQUER PROPï¿½SITO EM
 * PARTICULAR. Consulte a Licenï¿½a Pï¿½blica Geral GNU para obter mais
 * detalhes.
 * Vocï¿½ deve ter recebido uma cï¿½pia da Licenï¿½a Pï¿½blica Geral GNU
 * junto com este programa; se nï¿½o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gerencial.cadastro;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.faturamento.HistogramaAguaEconomia;
import gcom.faturamento.HistogramaAguaLigacao;
import gcom.faturamento.HistogramaEsgotoEconomia;
import gcom.faturamento.HistogramaEsgotoLigacao;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.gerencial.cadastro.bean.*;
import gcom.micromedicao.consumo.ConsumoTipo;
import gcom.micromedicao.consumo.LigacaoTipo;
import gcom.util.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.hibernate.*;
import org.hibernate.transform.Transformers;

/**
 * < <Descriï¿½ï¿½o da Classe>>
 * 
 * @author Administrador
 */
public class RepositorioGerencialCadastroHBM
				implements IRepositorioGerencialCadastro {

	private static IRepositorioGerencialCadastro instancia;

	/**
	 * Construtor da classe RepositorioMicromedicaoHBM
	 */
	private RepositorioGerencialCadastroHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static IRepositorioGerencialCadastro getInstancia(){

		if(instancia == null){
			instancia = new RepositorioGerencialCadastroHBM();
		}

		return instancia;
	}

	/**
	 * Mï¿½todo que retona uma lista de objeto xxxxx que representa o agrupamento
	 * id do imovel id gerencia regional id licalidade id do setor comercial
	 * codigo do setor comercial id rota id guradra numero da quadra id perfil
	 * do imovel id situacao da ligacao de agua id situacao da ligacao de esgoto
	 * principal categoria do imovel esfera de poder do cliente responsavel
	 * indicador de existencia de hidrometro
	 * 
	 * @author Thiago Toscano, Bruno Barrros
	 * @date 19/04/2006, 16/04/2007
	 * @param idSetor
	 *            id do Setor a ser pesquisado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getImoveisResumoLigacaoEconomias(int idSetor) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			StringBuilder sqlImoLigEcon = new StringBuilder();
			sqlImoLigEcon.append("SELECT ");
			sqlImoLigEcon.append(" imo.IMOV_ID idImovel, ");
			sqlImoLigEcon.append(" loc.GREG_ID idGerencia, ");
			sqlImoLigEcon.append(" loc.UNEG_ID idUnidadeNegocio, ");
			sqlImoLigEcon.append(" imo.LOCA_ID idLocalidade, ");
			sqlImoLigEcon.append(" loc.LOCA_CDELO idEloPolo, ");
			sqlImoLigEcon.append(" imo.STCM_ID idSetor, ");
			sqlImoLigEcon.append(" imo.ROTA_ID idRota, ");
			sqlImoLigEcon.append(" imo.QDRA_ID idQuadra, ");
			sqlImoLigEcon.append(" st.STCM_CDSETORCOMERCIAL codigoSetor, ");
			sqlImoLigEcon.append(" quadra.QDRA_NNQUADRA numeroQuadra, ");
			sqlImoLigEcon.append(" imo.IPER_ID perfilImovel, ");
			sqlImoLigEcon.append(" imo.LAST_ID ligacaoAguaSituacao, ");
			sqlImoLigEcon.append(" imo.LEST_ID ligacaoEsgotoSituacao, ");
			sqlImoLigEcon.append(" case when ( la.LAPF_ID is null ) then 0 else la.LAPF_ID end ligacaoAguaPerfil, ");
			sqlImoLigEcon.append(" case when ( le.LEPF_ID is null ) then 0 else le.LEPF_ID end ligacaoEsgotoPerfil, ");
			sqlImoLigEcon.append(" case when ( ");
			sqlImoLigEcon.append("	  (imo.LAST_ID in (:ligacaoAguaSituacaoLigado, :ligacaoAguaSituacaoCortado) and la.HIDI_ID is not null ) or ");
			sqlImoLigEcon.append("    (imo.LEST_ID = :ligacaoEsgotoSituacaoLigado and imo.HIDI_ID is not null ) ) ");
			sqlImoLigEcon.append("	   then 1 else 2 end ligacaoHidrometro, ");
			sqlImoLigEcon.append(" case when (imo.HIDI_ID is not null ) then 1 else 2 end imovelHidrometro, ");
			sqlImoLigEcon.append(" case when (la.LAGU_NNCONSUMOMINIMOAGUA > 0) then 1  else 2 end numeroConsumoMinimoAgua, ");
			sqlImoLigEcon.append(" case when (le.LESG_NNCONSUMOMINIMOESGOTO > 0 ) then 1 else 2 end consumoMinimoEsgoto, ");
			sqlImoLigEcon.append(" case when ( imo.POCO_ID is not null ) then 1 else 2 end pocoTipo, ");
			sqlImoLigEcon.append(" 1 as qtdligacao, ");
			sqlImoLigEcon.append(" imo.IMOV_QTECONOMIA quantidadeEconomias, ");
			sqlImoLigEcon.append(" imo.CSTF_ID consumoTarifa ");
			sqlImoLigEcon.append("FROM ");
			sqlImoLigEcon.append(" IMOVEL imo ");
			sqlImoLigEcon.append(" LEFT JOIN LOCALIDADE loc ON imo.LOCA_ID = loc.LOCA_ID ");
			sqlImoLigEcon.append(" LEFT JOIN SETOR_COMERCIAL st ON imo.STCM_ID = st.STCM_ID ");
			sqlImoLigEcon.append(" LEFT JOIN QUADRA quadra ON imo.QDRA_ID = quadra.QDRA_ID ");
			sqlImoLigEcon.append(" LEFT JOIN LIGACAO_AGUA la ON imo.IMOV_ID = la.LAGU_ID ");
			sqlImoLigEcon.append(" LEFT JOIN LIGACAO_ESGOTO le ON imo.IMOV_ID = le.LESG_ID ");
			sqlImoLigEcon.append("WHERE ");
			sqlImoLigEcon.append(" imo.IMOV_ICEXCLUSAO = 2 AND ");
			sqlImoLigEcon.append(" imo.STCM_ID = :idSetor ");

			SQLQuery sqlQuery = session.createSQLQuery(sqlImoLigEcon.toString());
			sqlQuery//
			.setInteger("ligacaoAguaSituacaoLigado", LigacaoAguaSituacao.LIGADO)//
							.setInteger("ligacaoAguaSituacaoCortado", LigacaoAguaSituacao.CORTADO)//
							.setInteger("ligacaoEsgotoSituacaoLigado", LigacaoEsgotoSituacao.LIGADO)//
							.setInteger("idSetor", idSetor);

			sqlQuery.addScalar("idImovel", Hibernate.INTEGER)//
							.addScalar("idGerencia", Hibernate.INTEGER)//
							.addScalar("idUnidadeNegocio", Hibernate.INTEGER)//
							.addScalar("idLocalidade", Hibernate.INTEGER)//
							.addScalar("idEloPolo", Hibernate.INTEGER)//
							.addScalar("idSetor", Hibernate.INTEGER)//
							.addScalar("idRota", Hibernate.INTEGER)//
							.addScalar("idQuadra", Hibernate.INTEGER)//
							.addScalar("codigoSetor", Hibernate.INTEGER)//
							.addScalar("numeroQuadra", Hibernate.INTEGER)//
							.addScalar("perfilImovel", Hibernate.INTEGER)//
							.addScalar("ligacaoAguaSituacao", Hibernate.INTEGER)//
							.addScalar("ligacaoEsgotoSituacao", Hibernate.INTEGER)//
							.addScalar("ligacaoAguaPerfil", Hibernate.INTEGER)//
							.addScalar("ligacaoEsgotoPerfil", Hibernate.INTEGER)//
							.addScalar("ligacaoHidrometro", Hibernate.INTEGER)//
							.addScalar("imovelHidrometro", Hibernate.INTEGER)//
							.addScalar("numeroConsumoMinimoAgua", Hibernate.INTEGER)//
							.addScalar("consumoMinimoEsgoto", Hibernate.INTEGER)//
							.addScalar("pocoTipo", Hibernate.INTEGER)//
							.addScalar("qtdligacao", Hibernate.INTEGER)//
							.addScalar("quantidadeEconomias", Hibernate.INTEGER)//
							.addScalar("consumoTarifa", Hibernate.INTEGER);
			retorno = sqlQuery.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Mï¿½todo que retona uma lista de objeto xxxxx que representa os dados
	 * iniciais dos imoves selecionados do setor comercial, contendo os dados
	 * iniciais para o agrupamento Histograma de Agua e Esgoto
	 * 
	 * @author Bruno Barros
	 * @date 14/05/2007
	 * @return Coleï¿½ï¿½o de contas
	 * @throws ErroRepositorioException
	 */
	public List getContasHistograma(int idSetor) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String hql = "select "
							+ "  c.id, " // 0
							+ "  l.gerenciaRegional.id, " // 1
							+ "  l.unidadeNegocio.id, " // 2
							+ "  l.localidade.id, "// 3
							+ "  l.id, "// 4
							+ "  q.setorComercial.id, "// 5
							+ "  c.codigoSetorComercial, "// 6
							+ "  q.id, "// 7
							+ "  c.quadra, " // 8
							+ "  c.consumoTarifa.id, "// 9
							+ "  c.imovelPerfil.id, "// 10
							+ "  c.ligacaoAguaSituacao.id, "// 11
							+ "  imo.id," // 12
							+ "  c.ligacaoEsgotoSituacao.id, "// 13
							+ "  c.percentualEsgoto "// 14
							+ "from " + "  Conta c " + "  inner join c.quadraConta q " + "  inner join c.localidade l "
							+ "  inner join c.imovel imo " + "where " + "  c.referencia in ( select "
							+ "                      anoMesFaturamento " + "  					from "
							+ "  					  SistemaParametro sp where sp.parmId = " + ConstantesConfig.getIdEmpresa() + " ) and "
							+ "  ( c.debitoCreditoSituacaoAtual = 0 or " + "    c.debitoCreditoSituacaoAnterior = 0 ) and "
							+ "  q.setorComercial.id = :idSetor";
			// + " l.id in (647, 411, 589, 590)";

			retorno = session.createQuery(hql).setInteger("idSetor", idSetor).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Mï¿½todo que retona uma lista de objeto xxxxx que representa os dados
	 * iniciais dos imoves nï¿½o faturados selecionados do setor comercial, contendo os dados
	 * iniciais para o agrupamento Histograma de Agua e Esgoto
	 * 
	 * @author Bruno Barros
	 * @date 14/05/2007
	 * @return Coleï¿½ï¿½o de contas
	 * @throws ErroRepositorioException
	 */
	public List getConsumoHistoricoImoveisNaoFaturados(int idSetor) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String hql = " select "
							+ "   conHist.imovel.localidade.gerenciaRegional.id, "
							+ // 0
							"   conHist.imovel.localidade.unidadeNegocio.id, "
							+ // 1
							"   conHist.imovel.localidade.localidade.id, "
							+ // 2
							"   conHist.imovel.localidade.id, "
							+ // 3
							"   conHist.imovel.setorComercial.id, "
							+ // 4
							"   conHist.imovel.setorComercial.codigo, "
							+ // 5
							"   conHist.imovel.quadra.id, "
							+ // 6
							"   conHist.imovel.quadra.numeroQuadra, "
							+ // 7
							"   conHist.imovel.consumoTarifa.id, "
							+ // 8
							"   conHist.imovel.imovelPerfil.id, "
							+ // 9
							"   conHist.imovel.ligacaoAguaSituacao.id, "
							+ // 10
							"   conHist.imovel.id, "
							+ // 11
							"   conHist.imovel.ligacaoEsgotoSituacao.id, "
							+ // 12
							"   conHist.imovel.quantidadeEconomias, "
							+ // 13
							"   conHist.ligacaoTipo.id,"
							+ // 14
							"   conHist.numeroConsumoFaturadoMes, "
							+ // 15
							"   conHist.pocoTipo.id, "
							+ // 16
							"   conHist.consumoTipo.id, "
							+ // 17
							"   conHist.imovel.ligacaoEsgoto.percentual "
							+ // 18
							" from " + "   ConsumoHistorico conHist, " + "   SistemaParametro sp " + " where "
							+ "   conHist.referenciaFaturamento = sp.anoMesFaturamento and " + " sp.parmId = "
							+ ConstantesConfig.getIdEmpresa()
							+ " and "
							+ "   conHist.indicadorFaturamento = 1 and "
							+ "   conHist.indicadorImovelCondominio = 2 and "
							+
							// "   conHist.imovel.consumoTarifa.id = 22 and " +
							"   not exists( from Conta con where con.imovel.id = conHist.imovel.id and con.referencia = sp.anoMesFaturamento ) and "
							+
							// "   conHist.imovel.localidade.id  in (647, 411, 589, 590)" +
							"   conHist.imovel.setorComercial.id = :idSetor " + " order by "
							+ "   conHist.imovel.id, conHist.ligacaoTipo.id";

			retorno = session.createQuery(hql).setInteger("idSetor", idSetor).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public List<Categoria> getCategoriasImovelDistintas(int idImovel) throws ErroRepositorioException{

		List<Categoria> retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String hql = " select scat.categoria " + " from " + "   Subcategoria scat " + " where " + "   scat.id in ( "
							+ " 			    select distinct isc.comp_id.subcategoria.id " + " 			    from "
							+ " 			      ImovelSubcategoria isc " + " 			    where " + " 			      isc.comp_id.imovel.id = :idImovel ) ";

			retorno = session.createQuery(hql).setInteger("idImovel", idImovel).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Seleciona todos os historicos de consumo de uma determinada localidade
	 * por imovel
	 * 
	 * @author Bruno Barrros
	 * @date 20/04/2007
	 * @param idLocalidade
	 *            id do Setor a ser pesquisado
	 * @return Colecao com os historicos selecionados
	 * @throws ErroRepositorioException
	 */
	public List getHistoricosConsumoAgua(int idSetor) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			String hql = "select " + "  imovel.id, " + "  case when ( conta <> null ) then " + "    locConta.gerenciaRegional.id "
							+ "  else " + "    locImo.gerenciaRegional.id " + "  end, " + "  case when ( conta <> null ) then "
							+ "    locConta.unidadeNegocio.id " + "  else " + "    locImo.unidadeNegocio.id " + "  end,   "
							+ "  case when ( conta <> null ) then " + "    locConta.id " + "  else " + "    locImo.id " + "  end, "
							+ "  case when ( conta <> null ) then " + "    locConta.localidade.id " + "  else   "
							+ "    locImo.localidade.id " + "  end, " + " case when ( conta <> null ) then " + "   setComCon.id "
							+ " else " + "   setComImo.id " + " end, " + "  case when ( conta <> null ) then " + "    quaCon.rota.id "
							+ "  else " + "    quaImo.rota.id " + "  end, " + "  case when ( conta <> null ) then " + "    quaCon.id "
							+ "  else " + "    quaImo.id " + "  end, " + "  case when ( conta <> null ) then "
							+ "    conta.codigoSetorComercial " + "  else " + "    setComImo.codigo " + "  end, "
							+ "  case when ( conta <> null ) then " + "    quaCon.numeroQuadra " + "  else " + "    quaImo.numeroQuadra "
							+ "  end, " + "  case when ( conta <> null ) then " + "    imoPerfCon.id " + "  else "
							+ "    imovel.imovelPerfil.id " + "  end, " + "  case when ( conta <> null ) then " + "    ligAguaSitCon.id "
							+ "  else " + "    imovel.ligacaoAguaSituacao.id " + "  end, " + "  case when ( conta <> null ) then "
							+ "    ligEsgSitCon.id " + "  else " + "    imovel.ligacaoEsgotoSituacao.id " + "  end, " + "  case when ( "
							+ "    ligAguaImo.ligacaoAguaPerfil.id is null ) then " + "    0 " + "  else "
							+ "    ligAguaImo.ligacaoAguaPerfil.id " + "  end, " + "  case when ( "
							+ "    ligEsgImo.ligacaoEsgotoPerfil.id is null ) then " + "    0 " + "  else "
							+ "    ligEsgImo.ligacaoEsgotoPerfil.id " + "  end, " + "  consumoHistorico.consumoTipo.id, "
							+ "  consumoHistorico.numeroConsumoFaturadoMes, " + "  ligAguaImo.numeroConsumoMinimoAgua, "
							+ "  consumoHistorico.referenciaFaturamento, " + "  consumoHistorico.ligacaoTipo.id " + "from "
							+ "  gcom.micromedicao.consumo.ConsumoHistorico consumoHistorico "
							+ "  inner join consumoHistorico.imovel imovel " + "  inner join imovel.localidade locImo "
							+ "  inner join imovel.quadra quaImo " + "  inner join imovel.setorComercial setComImo "
							+ "  left join imovel.ligacaoAgua ligAguaImo " + "  left join imovel.ligacaoEsgoto ligEsgImo "
							+ "  left join imovel.contas conta with ( conta.referencia = ( select "
							+ "	      	  							       						sistemaParametro.anoMesFaturamento " + "	    								     					from "
							+ "			  							       						gcom.cadastro.sistemaparametro.SistemaParametro sistemaParametro "
							+ "																where sistemaParametro.parmId = " + ConstantesConfig.getIdEmpresa()
							+ " ) and ( conta.debitoCreditoSituacaoAtual = 0 or conta.debitoCreditoSituacaoAnterior = 0 ) ) "
							+ "  left join conta.localidade locConta " + "  left join conta.quadraConta quaCon "
							+ "  left join conta.imovelPerfil imoPerfCon " + "  left join conta.ligacaoAguaSituacao ligAguaSitCon "
							+ "  left join conta.ligacaoEsgotoSituacao ligEsgSitCon " + "  left join quaCon.setorComercial setComCon "
							+ "where " + "  imovel.setorComercial.id = :idSetor and "
							+ "  consumoHistorico.ligacaoTipo.id = :ligacaoTipo and "
							+ "  consumoHistorico.indicadorImovelCondominio <> 1 and "
							+ "  consumoHistorico.referenciaFaturamento = ( select "
							+ "    										    sistemaParametro.anoMesFaturamento " + "    										  from "
							+ "      										    gcom.cadastro.sistemaparametro.SistemaParametro sistemaParametro "
							+ "												where sistemaParametro.parmId = " + ConstantesConfig.getIdEmpresa() + " ) ";

			retorno = session.createQuery(hql).setInteger("idSetor", idSetor).setInteger("ligacaoTipo", LigacaoTipo.LIGACAO_AGUA).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Mï¿½todo que retona uma lista de objeto xxxxx que representa o agrupamento
	 * 
	 * @author Bruno Barrros
	 * @date 19/04/2007
	 * @param idLocalidade
	 *            id da localida a ser pesquisada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getPagamentosResumoArrecadacaoAguaEsgoto(Integer idLocalidade) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String hql = "SELECT "
							+ "  imovel.id, "
							+ "  imovel.localidade.gerenciaRegional.id, "
							+ "  imovel.localidade.unidadeNegocio.id,"
							+ "  imovel.eloAnormalidade.id,"
							+ "  imovel.localidade.id,"
							+ "  imovel.setorComercial.id,"
							+ "  imovel.rota.id,"
							+ "  imovel.quadra.id,"
							+ "  imovel.imovelPerfil.id,"
							+
							// Verificamos a esfera de poder do cliente
							"  CASE WHEN ("
							+ "    clienteRelacaoTipo.id = :clienteResponsavel and"
							+ " 	 clienteImoveis.dataFimRelacao is null and esferaPoder.id is not null ) THEN"
							+ "    esferaPoder.id "
							+ "  ELSE"
							+ "    0 "
							+ "  END, "
							+
							// Verficiamos o tipo do cliente responsavel pelo imovel
							"  CASE WHEN ("
							+ "    clienteRelacaoTipo.id = :clienteResponsavel and"
							+ "    clienteImoveis.dataFimRelacao is null and cliente.clienteTipo.id is not null ) THEN"
							+ "    cliente.clienteTipo.id"
							+ "  ELSE"
							+ "    0"
							+ "  END,"
							+ "  imovel.ligacaoAguaSituacao.id, "
							+ "  imovel.ligacaoEsgotoSituacao.id, "
							+ "  imovel.ligacaoagua.ligacaoAguaPerfil.id, "
							+ "  imovel.ligacaoEsgoto.ligacaoEsgotoPerfil.id, "
							+ "  documentoTipo.id,"
							+ "  pagamentoSituacaoAtual.id,"
							+
							// Verificamos se o mes ano do pagamento e menor do que o
							// mes ano de referencia do
							// pagamento
							"  CASE WHEN ("
							+ "    cast( year( dataPagamento ) || month(pagamento.dataPagamento), Integer ) <"
							+ "    anoMesReferenciaPagamento ) THEN"
							+ "    2"
							+ "  ELSE"
							+ "    1"
							+ "  END,"
							+
							// Realizamos os calculos necessarios para determinar a
							// epoca de pagamento correta
							"  CASE WHEN (" + "    conta is null && " + "    guiaPagamento is null && "
							+ "    debitoACobrar is null ) THEN" + "    9" + "  ELSE" + "    CASE WHEN ("
							+ "      conta is not null ) THEN" + "      CASE WHEN ("
							+ "        dataPagamento <= conta.dataVencimentoConta ) THEN" + "        0" + "      ELSE"
							+ "        CASE WHEN (" + "          dataPagamento > conta.dataVencimentoConta && ) THEN"
							+ "          CASE WHEN" + "            month( dataPagamento ) = month( conta.dataVencimentoConta ) ) THEN"
							+ "            1" + "          ELSE" + "            CASE WHEN ("
							+ "              month( dataPagamento ) = ( month( conta.dataVencimentoConta ) + 1 ) ) THEN"
							+ "              2" + "            ELSE" + "              CASE WHEN ("
							+ "                month( dataPagamento ) = ( month( conta.dataVencimentoConta ) + 2 ) ) THEN"
							+ "                3" + "              ELSE" + "                CASE WHEN ("
							+ "                  month( dataPagamento ) = ( month( conta.dataVencimentoConta ) + 3 ) ) THEN"
							+ "                  4" + "                ELSE" + "                  CASE WHEN ("
							+ "                    month( dataPagamento ) > ( month( conta.dataVencimentoConta ) + 3 ) ) THEN"
							+ "                    5" + "				   END" + "				 END" + "			   END" + "			 END" + "		   END" + "		 ELSE"
							+ "          CASE WHEN (" + "            guiaPagamento is not null ) THEN" + "      	     CASE WHEN ("
							+ "              dataPagamento <= guiaPagamento.dataVencimento ) THEN" + "              0" + "      		 ELSE"
							+ "              CASE WHEN (" + "                dataPagamento > guiaPagamento.dataVencimento && ) THEN"
							+ "                CASE WHEN"
							+ "                  month( dataPagamento ) = month( guiaPagamento.dataVencimento ) ) THEN"
							+ "                  1" + "          	     ELSE" + "                  CASE WHEN ("
							+ "                    month( dataPagamento ) = ( month( guiaPagamento.dataVencimento ) + 1 ) ) THEN"
							+ "                    2" + "                  ELSE" + "                    CASE WHEN ("
							+ "                      month( dataPagamento ) = ( month( guiaPagamento.dataVencimento ) + 2 ) ) THEN"
							+ "                      3" + "                    ELSE" + "                      CASE WHEN ("
							+ "                        month( dataPagamento ) = ( month( guiaPagamento.dataVencimento ) + 3 ) ) THEN"
							+ "                        4" + "                      ELSE" + "                        CASE WHEN ("
							+ "                          month( dataPagamento ) > ( month( guiaPagamento.dataVencimento ) + 3 ) ) THEN"
							+ "                          5" + "				         END" + "				 	   END" + "			         END" + "			       END"
							+ "		         END" + "              END" + "		   ELSE" + "            CASE WHEN ("
							+ "              debitoACobrar is not null ) THEN" + "              0" + "            END" + "          END"
							+ "        END" + "      END" + "    END" + "  END epocaPagamento," + "  imovel.setorComercial.codigo"
							+ "  imovel.quadra.numeroQuadra," + "  conta.valorAgua," + "  conta.valorEsgoto," + "  CASE WHEN ("
							+ "    conta is null && " + "    guiaPagamento is null && " + "    debitoACobrar is null ) THEN"
							+ "    valorPagamento" + "  ELSE" + "    0" + "  END valorNaoIdentificado" + "FROM"
							+ "  gcom.arrecadacao.pagamento.Pagamento pagamento, " + "  left join imovel imovel "
							+ "  left join imovel.clienteImoveis clienteImoveis " + "  left join clienteRelacaoTipo clienteRelacaoTipo "
							+ "  left join clienteTipo.esferaPoder esferaPoder " + "  left join clienteImoveis.cliente cliente "
							+ " WHERE " + " imovel.localidade.id = :idLocalidade ";

			retorno = session
							.createQuery(hql)
							.
							// setInteger("clienteResponsavel",ClienteRelacaoTipo.RESPONSAVEL).
							setInteger("clienteResponsavel", ClienteRelacaoTipo.RESPONSAVEL)
							.setInteger("ligacaoAguaSituacaoLigado", LigacaoAguaSituacao.LIGADO)
							.setInteger("ligacaoAguaSituacaoCortado", LigacaoAguaSituacao.CORTADO)
							.setInteger("ligacaoEsgotoSituacaoLigado", LigacaoEsgotoSituacao.LIGADO)
							.setInteger("idLocalidade", idLocalidade).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Mï¿½todo que retona uma lista de objeto xxxxx que representa o agrupamento
	 * id do imovel, id da regiï¿½o, id da microrregiï¿½o, id do municï¿½pio, id do
	 * bairro, id do perfil do imï¿½vel, esfera de poder, id do tipo de cliente,
	 * id da situaï¿½ï¿½o da ligaï¿½ï¿½o ï¿½gua, id situacao da ligacao de esgoto,
	 * principal categoria do imovel, principal sub categoria do imovel perfil
	 * da ligaï¿½ï¿½o da ï¿½gua, perfil da ligaï¿½ï¿½o do esgoto
	 * 
	 * @author Ivan Sï¿½rgio
	 * @date 19/04/2007
	 * @param idLocalidade
	 *            id da localida a ser pesquisada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getImoveisResumoLigacaoEconomiaRegiao(int idLocalidade) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String hql = "SELECT "
							+ "	imovel.id, "
							+ "	imovel.bairro.municipio.microrregiao.regiao.id, "
							+ "	imovel.bairro.municipio.microrregiao.id, "
							+ "	imovel.bairro.municipio.id, "
							+ "	imovel.bairro.id, "
							+ "	imovel.imovelPerfil.id, "
							+ "	imovel.ligacaoAguaSituacao.id, "
							+ "	imovel.ligacaoEsgotoSituacao.id, "
							+
							// Verificamos a esfera de poder do cliente
							"  	CASE WHEN ("
							+ "   	clienteRelacaoTipo.id = :clienteResponsavel and"
							+ " 		clienteImoveis.dataFimRelacao is null and esferaPoder.id is not null ) THEN"
							+ "   	esferaPoder.id "
							+ "	ELSE"
							+ "   	0 "
							+ "	END, "
							+
							// Verficiamos o tipo do cliente responsavel pelo imovel
							"  	CASE WHEN ("
							+ "   	clienteRelacaoTipo.id = :clienteResponsavel and"
							+ "   	clienteImoveis.dataFimRelacao is null and cliente.clienteTipo.id is not null ) THEN"
							+ "   	cliente.clienteTipo.id"
							+ "	ELSE"
							+ "   	0"
							+ "	END,"
							+ "	imovel.ligacaoagua.ligacaoAguaPerfil.id, "
							+ "	imovel.ligacaoEsgoto.ligacaoEsgotoPerfil.id, "
							+
							// Haverï¿½ hidrometro instalado quando a situaï¿½ï¿½o da agua for
							// [ligada ou cortada] e houver histï¿½rico,
							// ou quando a situaï¿½ï¿½o estiver como ligada e nï¿½o houver
							// histï¿½rico
							"	CASE WHEN ("
							+ "		( ( imovel.ligacaoAguaSituacao.id = :ligacaoAguaSituacaoLigado OR"
							+ "       	imovel.ligacaoAguaSituacao.id = :ligacaoAguaSituacaoCortado ) AND"
							+ "      		imovel.ligacaoAgua.hidrometroInstalacaoHistorico is not null ) OR"
							+ "      	(	imovel.ligacaoEsgotoSituacao.id = :ligacaoEsgotoSituacaoLigado AND "
							+ "			imovel.hidrometroInstalacaoHistorico is not null ) ) THEN"
							+ "		1"
							+ "	ELSE"
							+ "		2"
							+ "	END,"
							+
							// Caso HIDI_ID nao seja nulo, entao marcarmos como tendo
							// hidrometro...
							"  	CASE WHEN ("
							+ "		imovel.hidrometroInstalacaoHistorico is not null ) THEN"
							+ "		1"
							+ "	ELSE"
							+ "		2"
							+ "	END,"
							+
							// Verificamos se o imovel possue volume minimo de agua
							// fixado
							"	CASE WHEN ("
							+ "		imovel.ligacaoAgua.numeroConsumoMinimoAgua > 0 ) THEN"
							+ "		1"
							+ "	ELSE"
							+ "		2"
							+ "	END,"
							+
							// Verificamos se o imovel possue volume minimo de esgoto
							// fixado
							"	CASE WHEN ("
							+ "		imovel.ligacaoEsgoto.consumoMinimo > 0 ) THEN"
							+ "		1"
							+ "	ELSE"
							+ "		2"
							+ "	END,"
							+
							// Verificamos se o imovel tem poco
							"	CASE WHEN ( "
							+ "		imovel.pocoTipo is not null ) THEN"
							+ "		1"
							+ "	ELSE"
							+ "		2"
							+ "	END,"
							+
							// Apos todos os agrupamentos, somamos 1 nas quantidades de
							// ligacao
							"	1 as qtdLigacao," + "	quantidadeEconomias" + "FROM" + "	gcom.cadastro.imovel.Imovel imovel "
							+ "  	left join imovel.clienteImoveis clienteImoveis "
							+ "  	left join clienteImoveis.clienteRelacaoTipo clienteRelacaoTipo "
							+ "  	left join clienteTipo.esferaPoder esferaPoder " + "  	left join clienteImoveis.cliente cliente "
							+ "WHERE " + "	imovel.localidade.id = :idLocalidade ";

			retorno = session
							.createQuery(hql)
							// .setShort("clienteResponsavel",ClienteRelacaoTipo.RESPONSAVEL)
							.setInteger("clienteResponsavel", ClienteRelacaoTipo.RESPONSAVEL)
							.setInteger("ligacaoAguaSituacaoLigado", LigacaoAguaSituacao.LIGADO)
							.setInteger("ligacaoAguaSituacaoCortado", LigacaoAguaSituacao.CORTADO)
							.setInteger("ligacaoEsgotoSituacaoLigado", LigacaoEsgotoSituacao.LIGADO)
							.setInteger("idLocalidade", idLocalidade).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0269] - Consultar Resumo das Ligacoes / Economia
	 * 
	 * @author Thiago Toscano
	 * @date 29/05/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List consultarResumoLigacoesEconomias(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{

			String sql = gerarSQLStringResumoLigacoesEconomia(informarDadosGeracaoRelatorioConsultaHelper);

			// * Solucao emergencial: Retirar os campos constantes do group by da query para
			// compatibilizar com o Postgres
			// * Solucao definitiva (fazer depois): Ajustar o método que monta a query para montar sem
			// os campos constantes no group by
			sql = this.retirarCamposConstantesGroupBy(sql);

			// faz a pesquisa
			retorno = session.createSQLQuery(sql)//
							.addScalar("campo", Hibernate.STRING)// 0
							.addScalar("idGerencia", Hibernate.INTEGER)// 1
							.addScalar("descricaoGerencia", Hibernate.STRING)// 2
							.addScalar("idElo", Hibernate.INTEGER)// 3
							.addScalar("descricaoElo", Hibernate.STRING)// 4
							.addScalar("idLocalidade", Hibernate.INTEGER)// 5
							.addScalar("descricaoLocalidade", Hibernate.STRING)// 6
							.addScalar("idSetorComercial", Hibernate.INTEGER)// 7
							.addScalar("descricaoSetorComercial", Hibernate.STRING)// 8
							.addScalar("idQuadra", Hibernate.INTEGER)// 9
							.addScalar("descricaoQuadra", Hibernate.STRING)// 10
							.addScalar("idLigacaoAguaSituacao", Hibernate.INTEGER)// 11
							.addScalar("descricaoLigacaoAguaSituacao", Hibernate.STRING)// 12
							.addScalar("idLigacaoEsgotoSituacao", Hibernate.INTEGER)// 13
							.addScalar("descricaoLigacaoEsgotoSituacao", Hibernate.STRING)// 14
							.addScalar("idCategoria", Hibernate.INTEGER)// 15
							.addScalar("descricaoCategoria", Hibernate.STRING)// 16
							.addScalar("qtdLigacoesComHidrometro", Hibernate.INTEGER)// 17
							.addScalar("qtdLigacoesSemHidrometro", Hibernate.INTEGER)// 18
							.addScalar("qtdLigacoesTotal", Hibernate.INTEGER)// 19
							.addScalar("qtdEconomiasComHidrometro", Hibernate.INTEGER)// 20
							.addScalar("qtdEconomiasSemHidrometro", Hibernate.INTEGER)// 21
							.addScalar("qtdEconomiasTotal", Hibernate.INTEGER)// 22
							.addScalar("idUnidadeNegocio", Hibernate.INTEGER)// 23
							.addScalar("descricaoUnidadeNegocio", Hibernate.STRING)// 24
							.list();
			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção com os resultados da pesquisa
		return retorno;
	}

	private String gerarSQLStringResumoLigacoesEconomia(
					InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper){

		GeradorSqlRelatorio geradorSqlRelatorio = new GeradorSqlRelatorio(GeradorSqlRelatorio.RESUMO_LIGACOES_ECONOMIAS,
						informarDadosGeracaoRelatorioConsultaHelper);

		String sql = geradorSqlRelatorio.sqlNivelUm(geradorSqlRelatorio.getNomeCampoFixo(), geradorSqlRelatorio.getNomeTabelaFixo(),
						geradorSqlRelatorio.getNomeTabelaFixoTotal(),
						"'" + informarDadosGeracaoRelatorioConsultaHelper.getDescricaoOpcaoTotalizacao() + "'", "", "", "", false);
		return sql;
	}

	public Long maximoIdImovel() throws ErroRepositorioException{

		Long retorno = 0L;

		Session session = HibernateUtil.getSession();

		try{

			String hql = "select max(imovel.id) from Imovel imovel";
			retorno = ((Number) session.createQuery(hql).uniqueResult()).longValue();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Mï¿½todo que retona uma lista de objeto xxxxx que representa o agrupamento
	 * id do imovel id gerencia regional id licalidade id do setor comercial
	 * codigo do setor comercial id rota id guradra numero da quadra id perfil
	 * do imovel id situacao da ligacao de agua id situacao da ligacao de esgoto
	 * principal categoria do imovel esfera de poder do cliente responsavel
	 * indicador de existencia de hidrometro
	 * 
	 * @author Marcio Roberto
	 * @date 04/05/2007
	 * @param idLocalidade
	 *            id da localida a ser pesquisada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getImoveisResumoParcelamento(int idLocalidade, int anoMes) throws ErroRepositorioException{

		List retorno = null;
		Session session = HibernateUtil.getSession();
		try{
			String hql = " select "
							+ "  parc.id, "
							+ "  imo.id, "
							+ "  parc.localidade.gerenciaRegional.id, "
							+ "  parc.localidade.unidadeNegocio.id, "
							+ "  parc.localidade.localidade.id, "
							+ "  parc.localidade.id, "
							+ "  parc.quadra.setorComercial.id, "
							+ "  imo.rota.id, "
							+ "  parc.quadra.id, "
							+ "  parc.codigoSetorComercial, "
							+ "  parc.numeroQuadra, "
							+ "  imo.imovelPerfil.id, "
							+ "  parc.ligacaoAguaSituacao.id, "
							+ "  parc.ligacaoEsgotoSituacao.id, "
							+ "  case when ( "
							+ "    imo.ligacaoAgua.ligacaoAguaPerfil.id is null ) then "
							+ "    0 "
							+ "  else "
							+ "    imo.ligacaoAgua.ligacaoAguaPerfil.id "
							+ "  end, "
							+ "  case when ( "
							+ "    imo.ligacaoEsgoto.ligacaoEsgotoPerfil.id is null ) then "
							+ "    0 "
							+ "  else "
							+ "    imo.ligacaoEsgoto.ligacaoEsgotoPerfil.id "
							+ "  end, "
							+ "  parc.valorConta, "
							+ "  parc.valorGuiaPapagamento, " // 17
							+ "  parc.valorCreditoARealizar, " // 18
							+ "  parc.valorDescontoAcrescimos, " // 19
							+ "  parc.valorDescontoInatividade, " // 20
							+ "  parc.valorDescontoAntiguidade, " // 21
							+ "  (parc.valorPrestacao * parc.numeroPrestacoes) as totalparcelamento, "// 22
							+ "  parc.anoMesReferenciaFaturamento, " // 23
							+ "  parc.valorServicosACobrar, " // 24
							+ "  (parc.valorAtualizacaoMonetaria + parc.valorJurosMora + parc.valorMulta) as debitosACobrarAcrescimos, " // 25
							+ "  parc.valorParcelamentosACobrar, " // 26
							+ "  parc.valorEntrada,  "// 27
							+ "  parc.valorJurosParcelamento, "// 28
							+ "  parc.numeroPrestacoes, "// 29
							+ "  imo.consumoTarifa.id,  "// 30
							+ "  imo.numeroReparcelamentoConsecutivos "// 31
							+ " from " + "  gcom.cobranca.parcelamento.Parcelamento parc " + "  inner join parc.imovel imo "
							+ "  inner join imo.quadra quad " + "  inner join quad.setorComercial stcom "
							+ "  inner join stcom.localidade locali " + "  left join imo.ligacaoAgua ligacaoAgua "
							+ "  left join imo.ligacaoEsgoto ligacaoEsgoto " + " where locali.id = :idLocalidade and "
							+ "  imo.indicadorExclusao = 2 and " + "  parc.parcelamentoSituacao.id = 1 and "
							+ "  parc.anoMesReferenciaFaturamento = :anoMesReferencia ";
			retorno = session.createQuery(hql).setInteger("idLocalidade", idLocalidade).setInteger("anoMesReferencia", anoMes).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * pesquisarObterQuantidadeContas
	 * 
	 * @author Marcio Roberto
	 * @date 07/05/2007
	 * @param parcelamentoId
	 *            id do parcelamento para relacionar-se com parcelamentoItem
	 *            Descriï¿½ï¿½o do parï¿½metro
	 * @return Descriï¿½ï¿½o do retorno
	 * @exception ErroRepositorioException
	 *                Descriï¿½ï¿½o da exceï¿½ï¿½o
	 */
	public Integer pesquisarObterQuantidadeContas(Integer parcelamentoId) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta;
		try{
			consulta = "select count(parcelamentoitem.contaGeral.id) as qtdContas " + "from  ParcelamentoItem parcelamentoitem "
							+ "inner join parcelamentoitem.parcelamento parcelamento "
							+ "inner join parcelamentoitem.contaGeral contageral " + "where parcelamento.id = :parcelamentoId "
							+ "and parcelamentoitem.contaGeral.id is not null ";
			retorno = ((Number) session.createQuery(consulta).setInteger("parcelamentoId", parcelamentoId).setMaxResults(1).uniqueResult())
							.intValue();
			// + "inner join contageral.conta as conta "
			// parcelamento.intValue()).uniqueResult();
		}catch(HibernateException e){
			// levanta a exceï¿½ï¿½o para a prï¿½xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * pesquisarObterQuantidadeGuias
	 * 
	 * @author Marcio Roberto
	 * @date 07/05/2007
	 * @param parcelamentoId
	 *            id do parcelamento para relacionar-se com parcelamentoItem
	 *            Descriï¿½ï¿½o do parï¿½metro
	 * @return Descriï¿½ï¿½o do retorno
	 * @exception ErroRepositorioException
	 *                Descriï¿½ï¿½o da exceï¿½ï¿½o
	 */
	public Integer pesquisarObterQuantidadeGuias(Integer parcelamentoId) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select count(parcelamentoitem.guiaPagamentoGeral.id ) as qtdGuias " + "from  ParcelamentoItem parcelamentoitem "
							+ "inner join parcelamentoitem.parcelamento parcelamento "
							+ "inner join parcelamentoitem.guiaPagamentoGeral guiaPagamentoGeral "
							+ "inner join guiaPagamentoGeral.guiaPagamento guiaPagamento " + "where parcelamento.id = :parcelamentoId "
							+ "and parcelamentoitem.guiaPagamentoGeral.id is not null ";

			retorno = ((Number) session.createQuery(consulta).setInteger("parcelamentoId", parcelamentoId).setMaxResults(1).uniqueResult())
							.intValue();
			// parcelamento.intValue()).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceï¿½ï¿½o para a prï¿½xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * pesquisarObterQuantidadeServicosIndiretos
	 * 
	 * @author Marcio Roberto
	 * @date 07/05/2007
	 * @param parcelamentoId
	 *            id do parcelamento para relacionar-se com parcelamentoItem
	 *            Descriï¿½ï¿½o do parï¿½metro
	 * @return Descriï¿½ï¿½o do retorno
	 * @exception ErroRepositorioException
	 *                Descriï¿½ï¿½o da exceï¿½ï¿½o
	 */
	public Short pesquisarObterQuantidadeServicosIndiretos(Integer parcelamentoId) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = " select count(parcelamentoitem.debitoACobrarGeral.id ) as qtdServicosIndiretos "
							+ " from  ParcelamentoItem parcelamentoitem " + "  inner join parcelamentoitem.parcelamento parcelamento "
							+ "  inner join parcelamentoitem.debitoACobrarGeral debitoCobrarGeral "
							+ "  inner join debitoCobrarGeral.debitoACobrar debitoCobrar " + " where parcelamento.id = :parcelamentoId "
							+ "  and parcelamentoitem.debitoACobrarGeral.id is not null ";

			retorno = ((Number) session.createQuery(consulta).setInteger("parcelamentoId", parcelamentoId).setMaxResults(1).uniqueResult())
							.intValue();
			// parcelamento.intValue()).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceï¿½ï¿½o para a prï¿½xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);
		}

		return retorno.shortValue();
	}

	/**
	 * pesquisarObterValorServicosIndiretos
	 * 
	 * @author Marcio Roberto
	 * @date 07/05/2007
	 * @param parcelamentoId
	 *            id do parcelamento para relacionar-se com parcelamentoItem
	 *            Descriï¿½ï¿½o do parï¿½metro
	 * @return Descriï¿½ï¿½o do retorno
	 * @exception ErroRepositorioException
	 *                Descriï¿½ï¿½o da exceï¿½ï¿½o
	 */
	public BigDecimal pesquisarObterValorServicosIndiretos(Integer parcelamentoId, String condicao) throws ErroRepositorioException{

		BigDecimal retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select sum(debitoacobrar.valorDebito) as valorServicosIndiretos " + "from  ParcelamentoItem parcelamentoitem "
							+ "inner join parcelamentoitem.parcelamento parcelamento "
							+ "inner join parcelamentoitem.debitoACobrarGeral debitoacobrargeral "
							+ "inner join debitoacobrargeral.debitoACobrar debitoacobrar " + "where parcelamento.id = :parcelamentoId "
							+ "and parcelamentoitem.debitoACobrarGeral.id is not null " + "and " + condicao;

			retorno = (BigDecimal) session.createQuery(consulta).setInteger("parcelamentoId", parcelamentoId).setMaxResults(1)
							.uniqueResult();
			// parcelamento.intValue()).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceï¿½ï¿½o para a prï¿½xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * pesquisaQuantidadeCategorias
	 * 
	 * @author Bruno Barros
	 * @date 14/05/2007
	 * @param conta
	 *            id da conta a qual procuraremos as categorias
	 * @return quantidade de categorias
	 * @exception ErroRepositorioException
	 */
	public Integer pesquisaQuantidadeCategorias(Integer conta) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "  count(*) " + "from " + "  gcom.faturamento.conta.ContaCategoria cc "
							+ "  inner join cc.comp_id.conta c " + "where " + "  c.referencia = ( select  "
							+ "                     anoMesFaturamento  " + "   				from   "
							+ "    				  gcom.cadastro.sistemaparametro.SistemaParametro sp where sp.parmId = "
							+ ConstantesConfig.getIdEmpresa() + " ) and   " + "  ( c.debitoCreditoSituacaoAtual = 0 or   "
							+ "    c.debitoCreditoSituacaoAnterior = 0 ) and " + "  c.id = :idConta ";

			retorno = ((Number) session.createQuery(consulta).setInteger("idConta", conta).setMaxResults(1).uniqueResult()).intValue();

		}catch(HibernateException e){
			// levanta a exceï¿½ï¿½o para a prï¿½xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * pesquisarEsferaPoderClienteResponsavelImovel
	 * 
	 * @author Bruno Barros
	 * @date 16/05/2007
	 * @param id
	 *            do imovel a ser pesquisado
	 * @return Esfera de poder do cliente responsavel pelo imovel
	 * @exception ErroRepositorioException
	 */
	public Integer pesquisarEsferaPoderClienteResponsavelImovel(Integer idImovel) throws ErroRepositorioException{

		Object retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "  case when ep.id is not null then " + "    ep.id " + "  else  " + "    0 " + "  end " + "from "
							+ "  gcom.cadastro.imovel.Imovel i " + "  inner join i.clienteImoveis ci " + "  inner join ci.cliente c "
							+ "  inner join c.clienteTipo ct " + "  inner join ct.esferaPoder ep "
							+ "  inner join ci.clienteRelacaoTipo crt " + "where "
							+ "  crt.id = :clienteResponsavel and ci.dataFimRelacao is null and i.id =:idImovel";

			retorno = session.createQuery(consulta).setInteger("clienteResponsavel", ClienteRelacaoTipo.RESPONSAVEL)
							.setInteger("idImovel", idImovel).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceï¿½ï¿½o para a prï¿½xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);
		}

		if(retorno != null){
			return (Integer) retorno;
		}else{
			return 0;
		}
	}

	/**
	 * pesquisarTipoClienteClienteResponsavelImovel
	 * 
	 * @author Bruno Barros
	 * @date 16/05/2007
	 * @param imovel
	 *            a ser pesquisado
	 * @return Tipo de cliente do cliente responsavel pelo imovel
	 * @exception ErroRepositorioException
	 */
	public Integer pesquisarTipoClienteClienteResponsavelImovel(Integer idImovel) throws ErroRepositorioException{

		Object retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "  case when ct.id is not null then " + "    ct.id " + "  else  " + "    0 " + "  end " + "from "
							+ "  gcom.cadastro.imovel.Imovel i " + "  inner join i.clienteImoveis ci " + "  inner join ci.cliente c "
							+ "  inner join c.clienteTipo ct " + "  inner join ci.clienteRelacaoTipo crt " + "where "
							+ "  crt.id = :clienteResponsavel and ci.dataFimRelacao is null and i.id =:idImovel";

			retorno = session.createQuery(consulta).setInteger("clienteResponsavel", ClienteRelacaoTipo.RESPONSAVEL)
							.setInteger("idImovel", idImovel).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceï¿½ï¿½o para a prï¿½xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);
		}

		if(retorno != null){
			return (Integer) retorno;
		}else{
			return 0;
		}
	}

	/**
	 * pesquisaQuantidadeEconomias
	 * 
	 * @author Bruno Barros
	 * @date 14/05/2007
	 * @param idConta
	 *            id da conta a qual procuraremos as categorias
	 * @return quantidade de economias
	 * @exception ErroRepositorioException
	 */
	public Integer pesquisaQuantidadeEconomias(Integer idConta, Integer idCategoria) throws ErroRepositorioException{

		Object retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "  sum( ccat.quantidadeEconomia ) " + "from " + "  gcom.faturamento.conta.ContaCategoria ccat "
							+ "where " + "  ccat.comp_id.conta.id = :idConta and ccat.comp_id.categoria.id = :idCategoria";

			retorno = session.createQuery(consulta).setInteger("idConta", idConta).setInteger("idCategoria", idCategoria).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceï¿½ï¿½o para a prï¿½xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);
		}

		if(retorno != null){
			return new Integer((Short) retorno);
		}else{
			return 0;
		}
	}

	/**
	 * Pesquisar Quantidade Economias
	 * 
	 * @author Saulo Lima, Virgínia Melo
	 * @date 01/09/2009
	 * @param idConta
	 *            - id da conta
	 * @return quantidade de economias
	 * @exception ErroRepositorioException
	 */
	public Integer pesquisarQuantidadeEconomias(Integer idConta) throws ErroRepositorioException{

		Object retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "  sum( ccat.quantidadeEconomia ) " + "from " + "  gcom.faturamento.conta.ContaCategoria ccat "
							+ "where " + "  ccat.comp_id.conta.id = :idConta ";

			retorno = session.createQuery(consulta).setInteger("idConta", idConta).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		if(retorno != null){
			return Integer.valueOf((Short) retorno);
		}else{
			return 0;
		}
	}

	public void inserirResumoConsumoAgua(Integer anoMesReferencia, ResumoConsumoAguaHelper resConsumo) throws ErroRepositorioException{

		Session session = HibernateUtil.getSessionGerencial();
		String insert;

		Connection con = null;
		Statement stmt = null;

		try{

			con = session.connection();
			stmt = con.createStatement();

			insert = "insert into un_resumo_consumo_agua (" + "  reca_id, " + "  uneg_id, " + "  reca_amreferencia, " + "  greg_id, "
							+ "  loca_id, " + "  loca_cdelo, " + "  stcm_id, " + "  qdra_id, " + "  reca_cdsetorcomercial, "
							+ "  rota_id, " + "  reca_nnquadra, " + "  last_id, " + "  lest_id, " + "  catg_id, " + "  scat_id , "
							+ "  epod_id , " + "  cltp_id , " + "  lapf_id , " + "  reca_qteconomias , " + "  lepf_id , "
							+ "  reca_consumoagua , " + "  reca_tmultimaalteracao, " + "  reca_consumoexcedente , "
							+ "  reca_qtligacoes , " + "  iper_id , " + "  cstp_id ," + "  reca_icvolumeexcedente,"
							+ "  reca_ichidrometro," + "  reca_voconsumofaturado )" + "  values ( "
							+ "  nextval('sequence_un_resumo_consumo_agua'), "
							+ resConsumo.getIdUnidadeNegocio()
							+ ", "
							+ anoMesReferencia
							+ ", "
							+ resConsumo.getIdGerenciaRegional()
							+ ", "
							+ resConsumo.getIdLocalidade()
							+ ", "
							+ resConsumo.getIdElo()
							+ ", "
							+ resConsumo.getIdSetorComercial()
							+ ", "
							+ resConsumo.getIdQuadra()
							+ ", "
							+ resConsumo.getCodigoSetorComercial()
							+ ", "
							+ resConsumo.getIdRota()
							+ ", "
							+ resConsumo.getNumeroQuadra()
							+ ", "
							+ resConsumo.getIdLigacaoAguaSituacao()
							+ ", "
							+ resConsumo.getIdLigacaoEsgotoSituacao()
							+ ", "
							+ resConsumo.getIdCategoria()
							+ ", "
							+ resConsumo.getIdSubCategoria()
							+ ", "
							+ resConsumo.getIdEsferaPoder()
							+ ", "
							+ resConsumo.getIdClienteTipo()
							+ ", "
							+ resConsumo.getIdLigacaoAguaPerfil()
							+ ", "
							+ resConsumo.getQuantidadeEconomias()
							+ ", "
							+ resConsumo.getIdLigacaoEsgotoPerfil()
							+ ", "
							+ resConsumo.getQuantidadeConsumoAgua()
							+ ", "
							+ " now(), "
							+ resConsumo.getQuantidadeConsumoAguaExcedente()
							+ ", "
							+ resConsumo.getQuantidadeLigacoes()
							+ ", "
							+ resConsumo.getIdImovelPerfil()
							+ ", "
							+ resConsumo.getIdConsumoTipo()
							+ ", "
							+ resConsumo.getIdVolumeExcedente()
							+ ","
							+ resConsumo.getIdHidrometro()
							+ ","
							+ resConsumo.getVolumeFaturado()
							+ ")";

			stmt.executeUpdate(insert);

			System.out.print("1 - INSERINDO RESUMO CONSUMO AGUA");
		}catch(HibernateException e){
			// levanta a exceï¿½ï¿½o para a prï¿½xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);

			try{
				stmt.close();
				con.close();
			}catch(SQLException e){
				throw new ErroRepositorioException(e, "Erro ao fechar conexï¿½es");
			}
		}
	}

	/**
	 * Verifica se o consumo do imï¿½vel ï¿½ real.
	 * 
	 * @author Bruno Barros
	 * @date 23/05/2007
	 * @param idImovel
	 *            id do imovel que terá seu consumo verificado *
	 * @return 1 se consumo real, 2 se não
	 * @throws ErroRepositorioException
	 */
	public Integer verificarConsumoReal(Integer idImovel) throws ErroRepositorioException{

		Object retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " //
							+ "  ch.consumoTipo.id " //
							+ "from " //
							+ "  gcom.micromedicao.consumo.ConsumoHistorico ch " //
							+ "where " //
							+ "  ch.imovel.id = :idImovel and " //
							+ "  ch.ligacaoTipo.id = 1 and " //
							+ "  ch.referenciaFaturamento = ( select anoMesFaturamento from " //
							+ "  									gcom.cadastro.sistemaparametro.SistemaParametro sp where sp.parmId = " //
							+ ConstantesConfig.getIdEmpresa() + " )";

			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		if(retorno != null){

			Integer idTipo = (Integer) retorno;
			return (idTipo.equals(ConsumoTipo.REAL) ? 1 : 2);

		}else{
			return 2;
		}
	}

	/**
	 * Verifica se o consumo do imóvel é real.
	 * 
	 * @author Bruno Barros
	 * @date 23/05/2007
	 * @author Saulo Lima
	 * @date 19/07/2013
	 *       Colocando a referência
	 * @param idImovel
	 *            id do imovel que terá seu consumo verificado
	 * @param referencia
	 *            referencia (ano-mês) da conta
	 * @return 1 se consumo real, 2 se não
	 * @throws ErroRepositorioException
	 */
	public Integer verificarConsumoRealNaReferencia(Integer idImovel, Integer referencia) throws ErroRepositorioException{

		Object retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT ch.consumoTipo.id " //
							+ "FROM gcom.micromedicao.consumo.ConsumoHistorico ch " //
							+ "WHERE ch.imovel.id = :idImovel AND " //
							+ " ch.ligacaoTipo.id = 1 AND " //
							+ " ch.referenciaFaturamento = :referencia";

			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("referencia", referencia).uniqueResult();

			if(retorno != null){

				Integer idTipo = (Integer) retorno;
				return (idTipo.equals(ConsumoTipo.REAL) ? 1 : 2);
				
			}else{
				return 2;
			}

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Verifica se o imóvel possui hidrometro
	 * 
	 * @author Bruno Barros
	 * @date 23/05/2007
	 * @author Virgínia Melo
	 * @date 08/09/2009
	 *       Caso não obtenha retorno em medição histórico, verificar ligação de água..
	 * @param idImovel
	 *            - id do imovel
	 * @return 1 se possuir hidrômetro, 2 senï¿½o
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaHidrometro(Integer idImovel) throws ErroRepositorioException{

		Object retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "  count(*) " + "from " + "  gcom.micromedicao.medicao.MedicaoHistorico mh " + "where "
							+ "  mh.ligacaoAgua.id = :idImovel and " + "  mh.anoMesReferencia = ( 	select "
							+ "  								anoMesFaturamento " + "  							from "
							+ "  								gcom.cadastro.sistemaparametro.SistemaParametro sp where sp.parmId = "
							+ ConstantesConfig.getIdEmpresa() + " ) ";

			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel).uniqueResult();

			if(retorno != null && ((Number) retorno).intValue() >= 1){
				return 1;
			}else{

				// verifica se imóvel tem hidrômetro
				consulta = "select la.hidrometroInstalacaoHistorico.id  " + "from gcom.atendimentopublico.ligacaoagua.LigacaoAgua la "
								+ "where la.id = :idImovel ";

				retorno = session.createQuery(consulta).setInteger("idImovel", idImovel).uniqueResult();

				if(retorno != null){
					return 1;
				}else{
					return 2;
				}
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Verifica se o imovel possui poço.
	 * 
	 * @author Bruno Barros
	 * @date 23/05/2007
	 * @param idImovel
	 *            id do imovel que terï¿½ seu consumo verificado *
	 * @return 1 se consumo real, 2 senï¿½o
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaPoco(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;

		try{


				consulta = "select " + "  case when ch.pocoTipo.id is null or ch.pocoTipo.id = 0 then " + "    2 " + "  else " + "    1 "
								+ "  end " + "from " + "  gcom.micromedicao.consumo.ConsumoHistorico ch " + "where "
								+ "  ch.imovel.id = :idImovel and " + "  ch.ligacaoTipo.id = 1 and "
								+ "  ch.referenciaFaturamento = ( 	select " + "  								anoMesFaturamento " + "  							from "
								+ "  								gcom.cadastro.sistemaparametro.SistemaParametro sp where sp.parmId = "
								+ ConstantesConfig.getIdEmpresa() + " )";

				return (Integer) (session.createQuery(consulta).setInteger("idImovel", idImovel).uniqueResult() == null ? 1 : 2);

		}catch(HibernateException e){
			// levanta a exceï¿½ï¿½o para a prï¿½xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Verifica se o imovel possui volume fixo de agua
	 * 
	 * @author Bruno Barros
	 * @date 23/05/2007
	 * @param idImovel
	 *            id do imovel que terï¿½ seu volume fixo verificado
	 * @return 1 se consumo exite, 2 senï¿½o
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaVolumeFixoAgua(Integer idImovel) throws ErroRepositorioException{

		Object retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "  ch.consumoMinimo " + "from " + "  gcom.micromedicao.consumo.ConsumoHistorico ch " + "where "
							+ "  ch.imovel.id = :idImovel and " + "  ch.ligacaoTipo.id = 1 and " + "  ch.referenciaFaturamento = ( select "
							+ "  									anoMesFaturamento " + "  								from "
							+ "  									gcom.cadastro.sistemaparametro.SistemaParametro sp where sp.parmId = "
							+ ConstantesConfig.getIdEmpresa() + " )";

			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel).uniqueResult();

			if(retorno != null && (Integer) retorno != 0){
				return 1;
			}else{

				consulta = "select la.numeroConsumoMinimoAgua  " + "from gcom.atendimentopublico.ligacaoagua.LigacaoAgua la "
								+ "where la.id = :idImovel ";

				retorno = session.createQuery(consulta).setInteger("idImovel", idImovel).uniqueResult();

				if(retorno != null && (Integer) retorno != 0){
					return 1;
				}else{
					return 2;
				}
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Verifica se o imovel possui volume fixo de esgoto
	 * 
	 * @author Bruno Barros
	 * @date 23/05/2007
	 * @param idImovel
	 *            id do imovel que terï¿½ seu volume fixo verificado
	 * @return 1 se consumo exite, 2 senï¿½o
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaVolumeFixoEsgoto(Integer idImovel) throws ErroRepositorioException{

		Object retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "  ch.consumoMinimo " + "from " + "  gcom.micromedicao.consumo.ConsumoHistorico ch " + "where "
							+ "  ch.imovel.id = :idImovel and " + "  ch.ligacaoTipo.id = 2 and " + "  ch.referenciaFaturamento = ( select "
							+ "  									anoMesFaturamento " + "  								from "
							+ "  									gcom.cadastro.sistemaparametro.SistemaParametro sp where sp.parmId = "
							+ ConstantesConfig.getIdEmpresa() + " )";

			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel).uniqueResult();

			if(retorno != null && (Integer) retorno != 0){
				return 1;
			}else{

				consulta = "select le.consumoMinimo  " + "from gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgoto le "
								+ "where le.id = :idImovel ";

				retorno = session.createQuery(consulta).setInteger("idImovel", idImovel).uniqueResult();

				if(retorno != null && (Integer) retorno != 0){
					return 1;
				}else{
					return 2;
				}
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Seleciona o percentual de consumo de esgoto
	 * 
	 * @author Bruno Barros
	 * @date 27/07/2007
	 * @param idImovel
	 *            id do imovel que terï¿½ seu volume fixo verificado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Float percentualColetaEsgoto(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "  ch.percentualColeta " + "from " + "  gcom.micromedicao.consumo.ConsumoHistorico ch " + "where "
							+ "  ch.imovel.id = :idImovel and " + "  ch.ligacaoTipo.id = 1 and " + "  ch.referenciaFaturamento = ( select "
							+ "  									anoMesFaturamento " + "  								from "
							+ "  									gcom.cadastro.sistemaparametro.SistemaParametro sp where sp.parmId = "
							+ ConstantesConfig.getIdEmpresa() + " )";

			return (Float) session.createQuery(consulta).setInteger("idImovel", idImovel).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceï¿½ï¿½o para a prï¿½xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);
		}
	}

	public void inserirHistogramaAguaLigacao(Integer anoMesReferencia, HistogramaAguaLigacaoHelper helper) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String insert;

		Connection con = null;
		Statement stmt = null;

		try{

			con = session.connection();
			stmt = con.createStatement();

			insert = "insert into " + "  histograma_agua_ligacao ( " + "  hagl_id, " + "  hagl_amreferencia, " + "  greg_id, "
							+ "  uneg_id, " + "  loca_cdelo, " + "  loca_id, " + "  stcm_id, " + "  hagl_cdsetorcomercial, "
							+ "  qdra_id, " + "  hagl_nnquadra, " + "  cgtp_id, " + "  catg_id, " + "  hagl_icligacaomista, "
							+ "  cstf_id, " + "  iper_id, " + "  epod_id, " + "  last_id, " + "  hagl_icconsumoreal, "
							+ "  hagl_ichidrometro, " + "  hagl_icpoco, " + "  hagl_icvolfixadoagua, " + "  hagl_qtconsumo, "
							+ "  hagl_qtligacao, " + "  hagl_qteconomialigacao, " + "  hagl_vlfaturadoligacao, "
							+ "  hagl_vofaturadoligacao, " + "  hagl_tmultimaalteracao ) " + " values ( "
							+ "  nextval('sq_histograma_agua_ligacao'), "
							+ anoMesReferencia
							+ ", "
							+ helper.getIdGerenciaRegional()
							+ ", "
							+ helper.getIdUnidadeNegocio()
							+ ", "
							+ helper.getIdElo()
							+ ", "
							+ helper.getIdLocalidade()
							+ ", "
							+ helper.getIdSetorComercial()
							+ ", "
							+ helper.getCodigoSetorComercial()
							+ ", "
							+ helper.getIdQuadra()
							+ ", "
							+ helper.getIdNumeroQuadra()
							+ ", "
							+ helper.getIdTipoCategoria()
							+ ", "
							+ helper.getIdCategoria()
							+ ", "
							+ helper.getIdLigacaoMista()
							+ ", "
							+ helper.getIdConsumoTarifa()
							+ ", "
							+ helper.getIdPerfilImovel()
							+ ", "
							+ helper.getIdEsferaPoder()
							+ ", "
							+ helper.getIdSituacaoLigacaoAgua()
							+ ", "
							+ helper.getIdConsumoReal()
							+ ", "
							+ helper.getIdExistenciaHidrometro()
							+ ", "
							+ helper.getIdExistenciaPoco()
							+ ", "
							+ helper.getIdExistenciaVolumeFixoAgua()
							+ ", "
							+ helper.getQuantidadeConsumo()
							+ ", "
							+ helper.getQuantidadeLigacao()
							+ ", "
							+ helper.getQuantidadeEconomiaLigacao()
							+ ", "
							+ helper.getValorFaturadoLigacao() + ", " + helper.getVolumeFaturadoLigacao() + ", " + "now() )";

			stmt.executeUpdate(insert);

			System.out.print("1 - INSERINDO HistogramaAguaLigacaoHelper ");
		}catch(HibernateException e){
			// levanta a exceï¿½ï¿½o para a prï¿½xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);

			try{
				stmt.close();
				con.close();
			}catch(SQLException e){
				throw new ErroRepositorioException(e, "Erro ao fechar conexï¿½es");
			}
		}
	}

	public void inserirHistogramaAguaEconomima(Integer anoMesReferencia, HistogramaAguaEconomiaHelper helper)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String insert;

		Connection con = null;
		Statement stmt = null;

		try{

			con = session.connection();
			stmt = con.createStatement();

			insert = "insert into " + "  histograma_agua_economia ( " + "  hage_id, " + "  hage_amreferencia, " + "  greg_id, "
							+ "  uneg_id, " + "  loca_cdelo, " + "  loca_id, " + "  stcm_id, " + "  hage_cdsetorcomercial, "
							+ "  qdra_id, " + "  hage_nnquadra, " + "  cgtp_id, " + "  catg_id, " + "  cstf_id, " + "  iper_id, "
							+ "  epod_id, " + "  last_id, " + "  hage_icconsumoreal, " + "  hage_ichidrometro, " + "  hage_icpoco, "
							+ "  hage_icvolfixadoagua, " + "  hage_qtconsumo, " + "  hage_qteconomia, " + "  hage_vlfaturadoeconomia, "
							+ "  hage_vofaturadoeconomia, " + "  hage_tmultimaalteracao,  " + "  hage_qtligacao )" + " values ( "
							+ "  nextval('sq_histograma_agua_economia'), "
							+ anoMesReferencia
							+ ", "
							+ helper.getIdGerenciaRegional()
							+ ", "
							+ helper.getIdUnidadeNegocio()
							+ ", "
							+ helper.getIdElo()
							+ ", "
							+ helper.getIdLocalidade()
							+ ", "
							+ helper.getIdSetorComercial()
							+ ", "
							+ helper.getCodigoSetorComercial()
							+ ", "
							+ helper.getIdQuadra()
							+ ", "
							+ helper.getIdNumeroQuadra()
							+ ", "
							+ helper.getIdTipoCategoria()
							+ ", "
							+ helper.getIdCategoria()
							+ ", "
							+ helper.getIdConsumoTarifa()
							+ ", "
							+ helper.getIdPerfilImovel()
							+ ", "
							+ helper.getIdEsferaPoder()
							+ ", "
							+ helper.getIdSituacaoLigacaoAgua()
							+ ", "
							+ helper.getIdConsumoReal()
							+ ", "
							+ helper.getIdExistenciaHidrometro()
							+ ", "
							+ helper.getIdExistenciaPoco()
							+ ", "
							+ helper.getIdExistenciaVolumeFixoAgua()
							+ ", "
							+ helper.getQuantidadeConsumo()
							+ ", "
							+ helper.getQuantidadeEconomia()
							+ ", "
							+ helper.getValorFaturadoEconomia()
							+ ", "
							+ helper.getVolumeFaturadoEconomia()
							+ ", "
							+ "now(), "
							+ helper.getQuantidadeLigacoes() + ")";

			stmt.executeUpdate(insert);

			System.out.print("1 - INSERINDO HistogramaAguaEconomiaHelper ");
		}catch(HibernateException e){
			// levanta a exceï¿½ï¿½o para a prï¿½xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);

			try{
				stmt.close();
				con.close();
			}catch(SQLException e){
				throw new ErroRepositorioException(e, "Erro ao fechar conexï¿½es");
			}
		}
	}

	public void inserirHistogramaEsgotoLigacao(Integer anoMesReferencia, HistogramaEsgotoLigacaoHelper helper)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String insert;

		Connection con = null;
		Statement stmt = null;

		try{

			con = session.connection();
			stmt = con.createStatement();

			insert = "insert into " + "  histograma_esgoto_ligacao ( " + "  hegl_id, " + "  hegl_amreferencia, " + "  greg_id, "
							+ "  uneg_id, " + "  loca_cdelo, " + "  loca_id, " + "  stcm_id, " + "  hegl_cdsetorcomercial, "
							+ "  qdra_id, " + "  hegl_nnquadra, " + "  cgtp_id, " + "  catg_id, " + "  hegl_icligacaomista, "
							+ "  cstf_id, " + "  iper_id, " + "  epod_id, " + "  lest_id, " + "  hegl_icconsumoreal, "
							+ "  hegl_ichidrometro, " + "  hegl_icpoco, " + "  hegl_icvolfixadoesgoto, " + "  hegl_nnpercentualesgoto, "
							+ "  hegl_qtconsumo, " + "  hegl_qtligacao, " + "  hegl_qteconomialigacao, " + "  hegl_vlfaturadoligacao, "
							+ "  hegl_vofaturadoligacao, " + "  hegl_tmultimaalteracao )" + " values ( "
							+ "  nextval('sq_histograma_esgoto_ligacao '), "
							+ anoMesReferencia
							+ ", "
							+ helper.getIdGerenciaRegional()
							+ ", "
							+ helper.getIdUnidadeNegocio()
							+ ", "
							+ helper.getIdElo()
							+ ", "
							+ helper.getIdLocalidade()
							+ ", "
							+ helper.getIdSetorComercial()
							+ ", "
							+ helper.getCodigoSetorComercial()
							+ ", "
							+ helper.getIdQuadra()
							+ ", "
							+ helper.getIdNumeroQuadra()
							+ ", "
							+ helper.getIdTipoCategoria()
							+ ", "
							+ helper.getIdCategoria()
							+ ", "
							+ helper.getIdLigacaoMista()
							+ ", "
							+ helper.getIdConsumoTarifa()
							+ ", "
							+ helper.getIdPerfilImovel()
							+ ", "
							+ helper.getIdEsferaPoder()
							+ ", "
							+ helper.getIdSituacaoLigacaoEsgoto()
							+ ", "
							+ helper.getIdConsumoReal()
							+ ", "
							+ helper.getIdExistenciaHidrometro()
							+ ", "
							+ helper.getIdExistenciaPoco()
							+ ", "
							+ helper.getIdExistenciaVolumeFixoEsgoto()
							+ ", "
							+ helper.getPercentualColetaEsgoto()
							+ ", "
							+ helper.getQuantidadeConsumo()
							+ ", "
							+ helper.getQuantidadeLigacao()
							+ ", "
							+ helper.getQuantidadeEconomiaLigacao()
							+ ", "
							+ helper.getValorFaturadoLigacao()
							+ ", "
							+ helper.getVolumeFaturadoLigacao() + ", " + "now() )";

			stmt.executeUpdate(insert);

			System.out.print("1 - INSERINDO HistogramaEsgotoLigacaoHelper ");
		}catch(HibernateException e){
			// levanta a exceï¿½ï¿½o para a prï¿½xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);

			try{
				stmt.close();
				con.close();
			}catch(SQLException e){
				throw new ErroRepositorioException(e, "Erro ao fechar conexï¿½es");
			}
		}
	}

	public void inserirHistogramaEsgotoEconomia(Integer anoMesReferencia, HistogramaEsgotoEconomiaHelper helper)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String insert;

		Connection con = null;
		Statement stmt = null;

		try{

			con = session.connection();
			stmt = con.createStatement();

			insert = "insert into " + "  histograma_esgoto_economia ( " + "  hege_id, " + "  hege_amreferencia, " + "  greg_id, "
							+ "  uneg_id, " + "  loca_cdelo, " + "  loca_id, " + "  stcm_id, " + "  hege_cdsetorcomercial, "
							+ "  qdra_id, " + "  hege_nnquadra, " + "  cgtp_id, " + "  catg_id, " + "  cstf_id, " + "  iper_id, "
							+ "  epod_id, " + "  lest_id, " + "  hege_icconsumoreal, " + "  hege_ichidrometro, " + "  hege_icpoco, "
							+ "  hege_icvolfixadoesgoto, " + "  hege_nnpercentualesgoto, " + "  hege_qtconsumo, " + "  hege_qteconomia, "
							+ "  hege_vlfaturadoeconomia, " + "  hege_vofaturadoeconomia, " + "  hege_tmultimaalteracao, "
							+ "  hege_qtligacao )" + " values ( " + "  nextval('sq_histograma_esgoto_economia'), "
							+ anoMesReferencia
							+ ", "
							+ helper.getIdGerenciaRegional()
							+ ", "
							+ helper.getIdUnidadeNegocio()
							+ ", "
							+ helper.getIdElo()
							+ ", "
							+ helper.getIdLocalidade()
							+ ", "
							+ helper.getIdSetorComercial()
							+ ", "
							+ helper.getCodigoSetorComercial()
							+ ", "
							+ helper.getIdQuadra()
							+ ", "
							+ helper.getIdNumeroQuadra()
							+ ", "
							+ helper.getIdTipoCategoria()
							+ ", "
							+ helper.getIdCategoria()
							+ ", "
							+ helper.getIdConsumoTarifa()
							+ ", "
							+ helper.getIdPerfilImovel()
							+ ", "
							+ helper.getIdEsferaPoder()
							+ ", "
							+ helper.getIdSituacaoLigacaoEsgoto()
							+ ", "
							+ helper.getIdConsumoReal()
							+ ", "
							+ helper.getIdExistenciaHidrometro()
							+ ", "
							+ helper.getIdExistenciaPoco()
							+ ", "
							+ helper.getIdExistenciaVolumeFixoEsgoto()
							+ ", "
							+ helper.getPercentualEsgoto()
							+ ", "
							+ helper.getQuantidadeConsumo()
							+ ", "
							+ helper.getQuantidadeEconomia()
							+ ", "
							+ helper.getValorFaturadoEconomia()
							+ ", "
							+ helper.getVolumeFaturadoEconomia()
							+ ", "
							+ "now(), "
							+ helper.getQuantidadeLigacoes() + ")";

			stmt.executeUpdate(insert);

			System.out.print("1 - INSERINDO HistogramaEsgotoEconomiaHelper ");
		}catch(HibernateException e){
			// levanta a exceï¿½ï¿½o para a prï¿½xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);

			try{
				stmt.close();
				con.close();
			}catch(SQLException e){
				throw new ErroRepositorioException(e, "Erro ao fechar conexï¿½es");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * gcom.gerencial.cadastro.IRepositorioGerencialCadastro#atualizarHistogramaAguaLigacao(gcom
	 * .faturamento.HistogramaAguaLigacao)
	 */
	public void atualizarHistogramaAguaLigacao(HistogramaAguaLigacao histogramaAguaLigacao) throws ErroRepositorioException{

		Transaction tr = null;
		Session session = HibernateUtil.getSession();
		try{
			tr = session.beginTransaction();
			session.update(histogramaAguaLigacao);
			session.flush();
			tr.commit();
			System.out.print("1 - ATUALIZANDO HistogramaAguaLigacao ");
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * gcom.gerencial.cadastro.IRepositorioGerencialCadastro#atualizarHistogramaAguaEconomia(gcom
	 * .faturamento.HistogramaAguaEconomia)
	 */
	public void atualizarHistogramaAguaEconomia(HistogramaAguaEconomia histogramaAguaEconomia) throws ErroRepositorioException{

		Transaction tr = null;
		Session session = HibernateUtil.getSession();
		try{
			tr = session.beginTransaction();
			session.update(histogramaAguaEconomia);
			session.flush();
			tr.commit();
			System.out.print("1 - ATUALIZANDO HistogramaAguaEconomia ");
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * gcom.gerencial.cadastro.IRepositorioGerencialCadastro#atualizarHistogramaEsgotoLigacao(gcom
	 * .faturamento.HistogramaEsgotoLigacao)
	 */
	public void atualizarHistogramaEsgotoLigacao(HistogramaEsgotoLigacao histogramaEsgotoLigacao) throws ErroRepositorioException{

		Transaction tr = null;
		Session session = HibernateUtil.getSession();
		try{
			tr = session.beginTransaction();
			session.update(histogramaEsgotoLigacao);
			session.flush();
			tr.commit();
			System.out.print("1 - ATUALIZANDO HistogramaEsgotoLigacao ");
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * gcom.gerencial.cadastro.IRepositorioGerencialCadastro#atualizarHistogramaEsgotoEconomia(gcom
	 * .faturamento.HistogramaEsgotoEconomia)
	 */
	public void atualizarHistogramaEsgotoEconomia(HistogramaEsgotoEconomia histogramaEsgotoEconomia) throws ErroRepositorioException{

		Transaction tr = null;
		Session session = HibernateUtil.getSession();
		try{
			tr = session.beginTransaction();
			session.update(histogramaEsgotoEconomia);
			session.flush();
			tr.commit();
			System.out.print("1 - ATUALIZANDO HistogramaEsgotoEconomia ");
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0623] - Gerar Resumo de Metas (CAERN)
	 * Seleciona todos os imï¿½veis, com o ano/mes arrecadaï¿½ï¿½o de sistema
	 * parametros de uma determinada gerï¿½ncia regional por imovel
	 * 
	 * @author Sï¿½vio Luiz
	 * @date 20/07/2007
	 * @param idLocalidade
	 *            id do Setor a ser pesquisado
	 * @return Colecao com os historicos selecionados
	 * @throws ErroRepositorioException
	 */
	public List pesquisarDadosResumoMetas(int idSetor, Date dataInicial, Date dataFinal) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String hql = "select "
							+ "   imovel.id, "// 0
							+ "   imovel.localidade.gerenciaRegional.id, "// 1
							+ "   imovel.localidade.unidadeNegocio.id,  "// 2
							+ "   imovel.localidade.id,  "// 3
							+ "   imovel.localidade.localidade.id, "// 4
							+ "   imovel.setorComercial.id,   "// 5
							+ "   imovel.rota.id,  "// 6
							+ "   imovel.quadra.id,  "// 7
							+ "   imovel.setorComercial.codigo, "// 8
							+ "   imovel.quadra.numeroQuadra,  "// 9
							+ "   imovel.imovelPerfil.id,  "// 10
							+ "   imovel.ligacaoAguaSituacao.id, "// 11
							+ "   imovel.ligacaoEsgotoSituacao.id, "// 12
							+ "   case when ( "
							+ "     imovel.ligacaoAgua.ligacaoAguaPerfil.id is null ) then "
							+ "     0 "
							+ "   else "
							+ "     imovel.ligacaoAgua.ligacaoAguaPerfil.id "
							+ "   end, "// 13
							+ "   case when ( "
							+ "     imovel.ligacaoEsgoto.ligacaoEsgotoPerfil.id is null ) then "
							+ "     0 "
							+ "   else "
							+ "     imovel.ligacaoEsgoto.ligacaoEsgotoPerfil.id "
							+ "   end, "// 14
							+ "ligacaoAgua.dataLigacao, "// 15
							+ "ligacaoAgua.dataSupressao, "// 16
							+ "ligacaoAgua.dataCorte, "// 17
							+ "ligacaoAgua.dataReligacao, "// 18
							+ "ligacaoAgua.dataRestabelecimentoAgua, "// 19
							+ "hidrometroInstalacaoHistorico.id "// 20
							+ "  from gcom.atendimentopublico.ligacaoagua.LigacaoAgua ligacaoAgua, "
							+ "   gcom.cadastro.imovel.Imovel imovel "
							+ "   left join imovel.ligacaoEsgoto ligacaoEsgoto "
							+ "   left join ligacaoAgua.hidrometroInstalacaoHistorico hidrometroInstalacaoHistorico "
							+ " where imovel.id = ligacaoAgua.id and "
							+ "   imovel.setorComercial.id = :idSetor "
							+ " and ("
							+ "(ligacaoAgua.dataLigacao >= :dataInicial and ligacaoAgua.dataLigacao <= :dataFinal) or "
							+ "(ligacaoAgua.dataSupressao >= :dataInicial and ligacaoAgua.dataSupressao <= :dataFinal) or "
							+ "(ligacaoAgua.dataCorte >= :dataInicial and ligacaoAgua.dataCorte <= :dataFinal) or "
							+ "(ligacaoAgua.dataReligacao >= :dataInicial and ligacaoAgua.dataReligacao <= :dataFinal) or "
							+ "(ligacaoAgua.dataRestabelecimentoAgua >= :dataInicial and ligacaoAgua.dataRestabelecimentoAgua <= :dataFinal))";

			retorno = session.createQuery(hql).setInteger("idSetor", idSetor).setDate("dataInicial", dataInicial)
							.setDate("dataFinal", dataFinal).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public void inserirResumoMetas(Integer anoMesReferencia, ResumoMetasHelper resMetas) throws ErroRepositorioException{

		Session session = HibernateUtil.getSessionGerencial();
		String insert;

		Connection con = null;
		Statement stmt = null;

		try{

			con = session.connection();
			stmt = con.createStatement();

			insert = "insert into un_resumo_metas (" + "  remt_id, " + "  uneg_id, " + "  remt_amreferencia, " + "  greg_id, "
							+ "  loca_id, " + "  loca_cdelo, " + "  stcm_id, " + "  qdra_id, " + "  remt_cdsetorcomercial, "
							+ "  rota_id, " + "  remt_nnquadra, " + "  last_id, " + "  lest_id, " + "  catg_id, " + "  scat_id , "
							+ "  epod_id , " + "  cltp_id , " + "  lapf_id , " + "  remt_qteconomias , " + "  lepf_id , "
							+ "  remt_qtligacoescadastradas , " + "  remt_tmultimaalteracao, " + "  remt_qtligacoescortadas , "
							+ "  remt_qtligacoessuprimidas , " + "  remt_qtligacoesativas , " + "  remt_qtligacoesativasdebito3M , "
							+ "  remt_qtligacoesconsumomedido , " + "  remt_qtligacoesconsumonaomedido , "
							+ "  remt_qtligacoesconsumoate5m3 , " + "  remt_qtligacoesconsumomedia , " + "  iper_id , "
							+ "  remt_cdgruposubcat ) " + "  values ( " + "  nextval('sequence_un_resumo_metas'), "
							+ resMetas.getIdUnidadeNegocio()
							+ ", "
							+ anoMesReferencia
							+ ", "
							+ resMetas.getIdGerenciaRegional()
							+ ", "
							+ resMetas.getIdLocalidade()
							+ ", "
							+ resMetas.getIdElo()
							+ ", "
							+ resMetas.getIdSetorComercial()
							+ ", "
							+ resMetas.getIdQuadra()
							+ ", "
							+ resMetas.getCodigoSetorComercial()
							+ ", "
							+ resMetas.getIdRota()
							+ ", "
							+ resMetas.getNumeroQuadra()
							+ ", "
							+ resMetas.getIdLigacaoAguaSituacao()
							+ ", "
							+ resMetas.getIdLigacaoEsgotoSituacao()
							+ ", "
							+ resMetas.getIdCategoria()
							+ ", "
							+ resMetas.getIdSubCategoria()
							+ ", "
							+ resMetas.getIdEsferaPoder()
							+ ", "
							+ resMetas.getIdClienteTipo()
							+ ", "
							+ resMetas.getIdLigacaoAguaPerfil()
							+ ", "
							+ resMetas.getQuantidadeEconomias()
							+ ", "
							+ resMetas.getIdLigacaoEsgotoPerfil()
							+ ", "
							+ resMetas.getQuantidadeLigacoesCadastradas()
							+ ", "
							+ " now(), "
							+ resMetas.getQuantidadeLigacoesCortadas()
							+ ", "
							+ resMetas.getQuantidadeLigacoesSuprimidas()
							+ ", "
							+ resMetas.getQuantidadeLigacoesAtivas()
							+ ", "
							+ resMetas.getQuantidadeLigacoesAtivasDebito3M()
							+ ", "
							+ resMetas.getQuantidadeLigacoesConsumoMedido()
							+ ", "
							+ resMetas.getQuantidadeLigacoesConsumoNaoMedido()
							+ ", "
							+ resMetas.getQuantidadeLigacoesConsumoAte5M3()
							+ ", "
							+ resMetas.getQuantidadeLigacoesConsumoMedio()
							+ ", " + resMetas.getIdImovelPerfil() + " , " + resMetas.getCodigoGrupoSubcategoria() + ") ";

			stmt.executeUpdate(insert);

			System.out.print("1 - INSERINDO RESUMO CONSUMO AGUA");
		}catch(HibernateException e){
			// levanta a exceï¿½ï¿½o para a prï¿½xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);

			try{
				stmt.close();
				con.close();
			}catch(SQLException e){
				throw new ErroRepositorioException(e, "Erro ao fechar conexï¿½es");
			}
		}
	}

	/**
	 * [UC0623] - Gerar Resumo de Metas 2 (CAERN)
	 * Seleciona todos os imï¿½veis, com o ano/mes arrecadaï¿½ï¿½o de sistema
	 * parametros de uma determinada gerï¿½ncia regional por imovel
	 * 
	 * @author Sï¿½vio Luiz
	 * @date 20/07/2007
	 * @param idLocalidade
	 *            id do Setor a ser pesquisado
	 * @return Colecao com os historicos selecionados
	 * @throws ErroRepositorioException
	 */

	public List pesquisarDadosResumoMetasAcumulado(int idSetor)

	throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			String hql = "select "
							+ "   imovel.id, "// 0
							+ "   imovel.localidade.gerenciaRegional.id, "// 1
							+ "   imovel.localidade.unidadeNegocio.id,  "// 2
							+ "   imovel.localidade.id,  "// 3
							+ "   imovel.localidade.localidade.id, "// 4
							+ "   imovel.setorComercial.id,   "// 5
							+ "   imovel.rota.id,  "// 6
							+ "   imovel.quadra.id,  "// 7
							+ "   imovel.setorComercial.codigo, "// 8
							+ "   imovel.quadra.numeroQuadra,  "// 9
							+ "   imovel.imovelPerfil.id,  "// 10
							+ "   imovel.ligacaoAguaSituacao.id, "// 11
							+ "   imovel.ligacaoEsgotoSituacao.id, "// 12
							+ "   case when ( "
							+ "     imovel.ligacaoAgua.ligacaoAguaPerfil.id is null ) then "
							+ "     0 "
							+ "   else "
							+ "     imovel.ligacaoAgua.ligacaoAguaPerfil.id "
							+ "   end, "// 13
							+ "   case when ( "
							+ "     imovel.ligacaoEsgoto.ligacaoEsgotoPerfil.id is null ) then "
							+ "     0 "
							+ "   else "
							+ "     imovel.ligacaoEsgoto.ligacaoEsgotoPerfil.id "
							+ "   end, "// 14
							+ "   case when ( "
							+ "     ligacaoAgua.dataLigacao is null ) then "
							+ "     ligacaoEsgoto.dataLigacao "
							+ "   else "
							+ "     ligacaoAgua.dataLigacao "
							+ "   end, "// 15
							+ "ligacaoAgua.dataSupressao, "// 16
							+ "ligacaoAgua.dataCorte, "// 17
							+ "ligacaoAgua.dataReligacao, "// 18
							+ "ligacaoAgua.dataRestabelecimentoAgua, "// 19
							+ "   case when ( "
							+ "     hidrometroInstalacaoHistorico.id is null ) then "
							+ "     hidrometroInstalacaoHistoricoImov.id "
							+ "   else "
							+ "    hidrometroInstalacaoHistorico.id "
							+ "   end "// 20
							+ "  from gcom.cadastro.imovel.Imovel imovel " + " left join imovel.ligacaoEsgoto ligacaoEsgoto "
							+ " left join imovel.ligacaoAgua ligacaoAgua "
							+ " left join ligacaoAgua.hidrometroInstalacaoHistorico hidrometroInstalacaoHistorico "
							+ " left join imovel.hidrometroInstalacaoHistorico hidrometroInstalacaoHistoricoImov "
							+ " where imovel.setorComercial.id = :idSetor and imovel.indicadorExclusao <> :indicadorExclusao";

			retorno = session.createQuery(hql).setInteger("idSetor", idSetor)

			.setShort("indicadorExclusao", Imovel.IMOVEL_EXCLUIDO)

			.list();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	public void inserirResumoMetasAcumulado(Integer anoMesReferencia, ResumoMetasHelper resMetas) throws ErroRepositorioException{

		Session session = HibernateUtil.getSessionGerencial();
		String insert;

		Connection con = null;
		Statement stmt = null;

		try{

			con = session.connection();
			stmt = con.createStatement();

			insert = "insert into un_resumo_metas_acumulado (" + "  rema_id, " + "  uneg_id, " + "  rema_amreferencia, " + "  greg_id, "
							+ "  loca_id, " + "  loca_cdelo, " + "  stcm_id, " + "  qdra_id, " + "  rema_cdsetorcomercial, "
							+ "  rota_id, " + "  rema_nnquadra, " + "  last_id, " + "  lest_id, " + "  catg_id, " + "  scat_id , "
							+ "  epod_id , " + "  cltp_id , " + "  lapf_id , " + "  rema_qteconomias , " + "  lepf_id , "
							+ "  rema_qtligacoescadastradas , " + "  rema_tmultimaalteracao, " + "  rema_qtligacoescortadas , "
							+ "  rema_qtligacoessuprimidas , " + "  rema_qtligacoesativas , " + "  rema_qtligacoesativasdebito3M , "
							+ "  rema_qtligacoesconsumomedido , " + "  rema_qtligacoesconsumonaomedido , "
							+ "  rema_qtligacoesconsumoate5m3 , " + "  rema_qtligacoesconsumomedia , " + "  iper_id ,"
							+ "  rema_cdgruposubcat ) " + "  values ( " + "  nextval('sequence_un_resumo_metas_acumulado'), "
							+ resMetas.getIdUnidadeNegocio()
							+ ", "
							+ anoMesReferencia
							+ ", "
							+ resMetas.getIdGerenciaRegional()
							+ ", "
							+ resMetas.getIdLocalidade()
							+ ", "
							+ resMetas.getIdElo()
							+ ", "
							+ resMetas.getIdSetorComercial()
							+ ", "
							+ resMetas.getIdQuadra()
							+ ", "
							+ resMetas.getCodigoSetorComercial()
							+ ", "
							+ resMetas.getIdRota()
							+ ", "
							+ resMetas.getNumeroQuadra()
							+ ", "
							+ resMetas.getIdLigacaoAguaSituacao()
							+ ", "
							+ resMetas.getIdLigacaoEsgotoSituacao()
							+ ", "
							+ resMetas.getIdCategoria()
							+ ", "
							+ resMetas.getIdSubCategoria()
							+ ", "
							+ resMetas.getIdEsferaPoder()
							+ ", "
							+ resMetas.getIdClienteTipo()
							+ ", "
							+ resMetas.getIdLigacaoAguaPerfil()
							+ ", "
							+ resMetas.getQuantidadeEconomias()
							+ ", "
							+ resMetas.getIdLigacaoEsgotoPerfil()
							+ ", "
							+ resMetas.getQuantidadeLigacoesCadastradas()
							+ ", "
							+ " now(), "
							+ resMetas.getQuantidadeLigacoesCortadas()
							+ ", "
							+ resMetas.getQuantidadeLigacoesSuprimidas()
							+ ", "
							+ resMetas.getQuantidadeLigacoesAtivas()
							+ ", "
							+ resMetas.getQuantidadeLigacoesAtivasDebito3M()
							+ ", "
							+ resMetas.getQuantidadeLigacoesConsumoMedido()
							+ ", "
							+ resMetas.getQuantidadeLigacoesConsumoNaoMedido()
							+ ", "
							+ resMetas.getQuantidadeLigacoesConsumoAte5M3()
							+ ", "
							+ resMetas.getQuantidadeLigacoesConsumoMedio()
							+ ", " + resMetas.getIdImovelPerfil() + "," + resMetas.getCodigoGrupoSubcategoria() + ") ";

			stmt.executeUpdate(insert);

			System.out.print("1 - INSERINDO RESUMO CONSUMO AGUA");
		}catch(HibernateException e){
			// levanta a exceï¿½ï¿½o para a prï¿½xima camada
			e.printStackTrace();
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(SQLException e){
			e.printStackTrace();
			throw new ErroRepositorioException(e, "Erro no Insert");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);

			try{
				stmt.close();
				con.close();
			}catch(SQLException e){
				throw new ErroRepositorioException(e, "Erro ao fechar conexï¿½es");
			}
		}
	}

	public Integer getConsumoMinimoImovelCategoria(int idImovel, int idCategoria) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String hql = " select " + "   max( ctc.numeroConsumoMinimo ) " + " from " + "   Imovel imo "
							+ "   inner join imo.consumoTarifa ct "
							+ "   inner join ct.consumoTarifaVigencias ctv with( ctv.dataVigencia <= ("
							+ "	select sp.anoMesFaturamento from SistemaParametro sp where sp.parmId = " + ConstantesConfig.getIdEmpresa()
							+ " ) ) " + "   inner join ctv.consumoTarifaCategorias ctc " + " where "
							+ "   imo.id = :idImovel and ctc.categoria.id = :idCategoria ";

			retorno = (Integer) session.createQuery(hql).setInteger("idImovel", idImovel).setInteger("idCategoria", idCategoria)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Seleciona todos os historicos de coleta de uma determinada localidade
	 * por imovel
	 * 
	 * @author Marcio Roberto
	 * @date 20/09/2007
	 * @param idSetor
	 *            id do Setor a ser pesquisado
	 * @return Colecao com os historicos selecionados
	 * @throws ErroRepositorioException
	 */
	public List getHistoricosColetaEsgoto(int idSetor) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			String hql = "select "
							+ "  imovel.id, "
							+ // 00 Imovel
							"  case when ( conta <> null ) then "
							+ // 01 ]
							"    locConta.gerenciaRegional.id "
							+ // 01 |
							"  else "
							+ // 01 +- Gerencia Regional
							"    locImo.gerenciaRegional.id "
							+ // 01 |
							"  end, "
							+ // 01 ]

							"  case when ( conta <> null ) then "
							+ // 02 ]
							"    locConta.unidadeNegocio.id "
							+ // 02 |
							"  else "
							+ // 02 +- Unidade de negocio
							"    locImo.unidadeNegocio.id "
							+ // 02 |
							"  end,   "
							+ // 02 ]

							"  case when ( conta <> null ) then "
							+ // 03 ]
							"    locConta.id "
							+ // 03 |
							"  else "
							+ // 03 +- Localidade
							"    locImo.id "
							+ // 03 |
							"  end, "
							+ // 03 ]

							"  case when ( conta <> null ) then "
							+ // 04 ]
							"    locConta.localidade.id "
							+ // 04 |
							"  else   "
							+ // 04 +- Elo
							"    locImo.localidade.id "
							+ // 04 |
							"  end, "
							+ // 04 ]

							" case when ( conta <> null ) then "
							+ // 05 ]
							"   setComCon.id "
							+ // 05 |
							" else "
							+ // 05 +- Id Setor Comercial
							"   setComImo.id "
							+ // 05 |
							" end, "
							+ // 05 ]

							"  case when ( conta <> null ) then "
							+ // 06 ]
							"    quaCon.rota.id "
							+ // 06 |
							"  else "
							+ // 06 +- id Rota
							"    quaImo.rota.id "
							+ // 06 |
							"  end, "
							+ // 06 ]

							"  case when ( conta <> null ) then "
							+ // 07 ]
							"    quaCon.id "
							+ // 07 |
							"  else "
							+ // 07 +- Id Quadra
							"    quaImo.id "
							+ // 07 |
							"  end, "
							+ // 07 ]

							"  case when ( conta <> null ) then "
							+ // 08 ]
							"    conta.codigoSetorComercial "
							+ // 08 |
							"  else "
							+ // 08 +- Codigo do Setor Comercial
							"    setComImo.codigo "
							+ // 08 |
							"  end, "
							+ // 08 ]

							"  case when ( conta <> null ) then "
							+ // 09 ]
							"    quaCon.numeroQuadra "
							+ // 09 |
							"  else "
							+ // 09 +- Numero da quadra
							"    quaImo.numeroQuadra "
							+ // 09 |
							"  end, "
							+ // 09 ]

							"  case when ( conta <> null ) then "
							+ // 10 ]
							"    imoPerfCon.id "
							+ // 10 |
							"  else "
							+ // 10 +- Perfil do imovel
							"    imovel.imovelPerfil.id "
							+ // 10 |
							"  end, "
							+ // 10 ]

							"  case when ( conta <> null ) then "
							+ // 11 ]
							"    ligAguaSitCon.id "
							+ // 11 |
							"  else "
							+ // 11 +- Situacao da ligacao da agua
							"    imovel.ligacaoAguaSituacao.id "
							+ // 11 |
							"  end, "
							+ // 11 ]

							"  case when ( conta <> null ) then "
							+ // 12 ]
							"    ligEsgSitCon.id "
							+ // 12 |
							"  else "
							+ // 12 +- Situacao da ligacao do esgoto
							"    imovel.ligacaoEsgotoSituacao.id "
							+ // 12 |
							"  end, "
							+ // 12 ]
							"  case when ( "
							+ // 13 ]
							"    ligAguaImo.ligacaoAguaPerfil.id is null ) then "
							+ // 13 |
							"    0 "
							+ // 13 |
							"  else "
							+ // 13 +- Perfil da ligacao de agua
							"    ligAguaImo.ligacaoAguaPerfil.id "
							+ // 13 |
							"  end, "
							+ // 13 ]

							"  case when ( "
							+ // 14 ]
							"    ligEsgImo.ligacaoEsgotoPerfil.id is null ) then "
							+ // 14 |
							"    0 "
							+ // 14 +- Perfil da ligacao de esgoto
							"  else "
							+ // 14 |
							"    ligEsgImo.ligacaoEsgotoPerfil.id "
							+ // 14 |
							"  end, "
							+ // 14 ]

							"  consumoHistorico.consumoTipo.id, "
							+ // 15 |- Tipo Consumo
							"  consumoHistorico.numeroConsumoFaturadoMes, "
							+ // 16 |- Volume Esgoto
							"  consumoHistorico.referenciaFaturamento, "
							+ // 17 |- AnoMes
							"  case when ( ligEsgImo.consumoMinimo > 0 ) then "
							+ // 18 |
							"     1"
							+ "   else"
							+ "     2"
							+ "   end, "
							+ // 18 +- Consumo Minimo

							"  case when ( ligEsgImo.percentual is null ) then "
							+ // 19 |
							"  0.0 "
							+ " else "
							+ " ligEsgImo.percentual "
							+ " end, "
							+ // 19 +- Percentual Coleta

							"   case when ( "
							+ // 20 ]
							"     imovel.hidrometroInstalacaoHistorico is not null ) then "
							+ // 20 |
							"     1 "
							+ // 20 |
							"   else "
							+ // 20 +- Existencia de Hidrometro no Poï¿½o
							"     2 "
							+ // 20 |
							"   end, "
							+ // 20 ]
							"   consumoHistorico.percentualColeta "
							+ // 21 +- Percentual Esgoto
							"from "
							+ "  gcom.micromedicao.consumo.ConsumoHistorico consumoHistorico "
							+ "  inner join consumoHistorico.imovel imovel "
							+ "  inner join imovel.localidade locImo "
							+ "  inner join imovel.quadra quaImo "
							+ "  inner join imovel.setorComercial setComImo "
							+ "  left join imovel.ligacaoAgua ligAguaImo "
							+ "  left join imovel.ligacaoEsgoto ligEsgImo "
							+ "  left join imovel.contas conta with ( conta.referencia = ( 200708 ) and ( conta.debitoCreditoSituacaoAtual = 0 or conta.debitoCreditoSituacaoAnterior = 0 ) ) "
							+ "  left join conta.localidade locConta " + "  left join conta.quadraConta quaCon "
							+ "  left join conta.imovelPerfil imoPerfCon " + "  left join conta.ligacaoAguaSituacao ligAguaSitCon "
							+ "  left join conta.ligacaoEsgotoSituacao ligEsgSitCon " + "  left join quaCon.setorComercial setComCon "
							+ "where " + "  imovel.setorComercial.id = :idSetor and "
							+ "  consumoHistorico.ligacaoTipo.id = :ligacaoTipo and "
							+ "  consumoHistorico.indicadorImovelCondominio <> 1 and "
							+ "  consumoHistorico.referenciaFaturamento = ( 200708 ) ";

			String hq2 = " select "
							+ "    imovel.id, "
							+ // Imovel
							"    locImo.gerenciaRegional.id, "
							+ // Gerencia Regional
							"    locImo.unidadeNegocio.id, "
							+ // Unidade de negocio
							"    locImo.id, "
							+ // Localidade
							"    locImo.localidade.id, "
							+ // Elo
							"    setComImo.id, "
							+ // Id Setor Comercial
							"    quaImo.rota.id, "
							+ // Rota
							"    quaImo.id, "
							+ // Quadra
							"    setComImo.codigo, "
							+ // Codigo do Setor Comercial
							"    quaImo.numeroQuadra, "
							+ // Numero Quadra
							"    imovel.imovelPerfil.id, "
							+ // Perfil do Imovel
							"    imovel.ligacaoAguaSituacao.id, "
							+ // Situacao Ligacao Agua
							"    imovel.ligacaoEsgotoSituacao.id, "
							+ // Situacao Ligacao Esgoto
							"  case when ( "
							+ // 13 ]
							"    ligAguaImo.ligacaoAguaPerfil.id is null ) then "
							+ // 13 |
							"    0 "
							+ // 13 |
							"  else "
							+ // 13 +- Perfil da ligacao de agua
							"    ligAguaImo.ligacaoAguaPerfil.id "
							+ // 13 |
							"  end, "
							+ // 13 ]

							"  case when ( "
							+ // 14 ]
							"    ligEsgImo.ligacaoEsgotoPerfil.id is null ) then "
							+ // 14 |
							"    0 "
							+ // 14 +- Perfil da ligacao de esgoto
							"  else "
							+ // 14 |
							"    ligEsgImo.ligacaoEsgotoPerfil.id "
							+ // 14 |
							"  end, "
							+ // 14 |
							"  consumoHistorico.consumoTipo.id, "
							+ // 15 |- Tipo Consumo
							"  consumoHistorico.numeroConsumoFaturadoMes, "
							+ // 16 |- Volume Esgoto
							"  consumoHistorico.referenciaFaturamento, "
							+ // 17 |- AnoMes
							"  case when ( ligEsgImo.consumoMinimo > 0 ) then "
							+ // 18 |
							"     1"
							+ "   else"
							+ "     2"
							+ "   end, "
							+ // 18 +- Consumo Minimo
							"  case when ( ligEsgImo.percentual is null ) then "
							+ // 19 |
							"  0.0 "
							+ " else "
							+ " ligEsgImo.percentual "
							+ " end, "
							+ // 19 +- Percentual Esgoto
							"   case when ( "
							+ // 20 ]
							"     imovel.hidrometroInstalacaoHistorico is not null ) then "
							+ // 20 |
							"     1 "
							+ // 20 |
							"   else "
							+ // 20 +- Existencia de Hidrometro no Poï¿½o
							"     2 "
							+ // 20 |
							"   end, "
							+ // 20 ]
							"   consumoHistorico.percentualColeta "
							+ // 21 +- Percentual Coleta
							" from " + "  gcom.micromedicao.consumo.ConsumoHistorico consumoHistorico "
							+ "  inner join consumoHistorico.imovel imovel " + "  inner join imovel.localidade locImo "
							+ "  inner join imovel.quadra quaImo " + "  inner join imovel.setorComercial setComImo "
							+ "  left join imovel.ligacaoAgua ligAguaImo " + "  left join imovel.ligacaoEsgoto ligEsgImo "
							+ "  left join imovel.contas conta with ( conta.referencia = ( select "
							+ "    										      sistemaParametro.anoMesFaturamento " + "    										    from "
							+ "      										  gcom.cadastro.sistemaparametro.SistemaParametro sistemaParametro "
							+ "												where sistemaParametro.parmId = " + ConstantesConfig.getIdEmpresa()
							+ " ) and ( conta.debitoCreditoSituacaoAtual = 0 or conta.debitoCreditoSituacaoAnterior = 0 ) ) "
							+ "  left join conta.ligacaoAguaSituacao ligAguaSitCon "
							+ "  left join conta.ligacaoEsgotoSituacao ligEsgSitCon " + " where "
							+ "  imovel.setorComercial.id = :idSetor and " + "  consumoHistorico.ligacaoTipo.id = :ligacaoTipo and "
							+ "  consumoHistorico.indicadorImovelCondominio <> 1 and "
							+ "  consumoHistorico.referenciaFaturamento = (  select "
							+ "    										      sistemaParametro.anoMesFaturamento "
							+ "      										  from gcom.cadastro.sistemaparametro.SistemaParametro sistemaParametro "
							+ "												  where sistemaParametro.parmId = " + ConstantesConfig.getIdEmpresa() + " )";
			retorno = session.createQuery(hq2).setInteger("idSetor", idSetor).setInteger("ligacaoTipo", LigacaoTipo.LIGACAO_ESGOTO).list();

			// select " +
			// "	      	  							       						sistemaParametro.anoMesFaturamento " +
			// "	    								     					from " +
			// " gcom.cadastro.sistemaparametro.SistemaParametro sistemaParametro

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Insere resumo Coleta Esgoto
	 * 
	 * @author Marcio Roberto
	 * @date 20/09/2007
	 * @param idSetor
	 *            id do Setor a ser pesquisado
	 * @return Colecao com os historicos selecionados
	 * @throws ErroRepositorioException
	 */
	public void inserirResumoColetaEsgoto(Integer anoMesReferencia, ResumoColetaEsgotoHelper resConsumo) throws ErroRepositorioException{

		Session session = HibernateUtil.getSessionGerencial();
		String insert;

		Connection con = null;
		Statement stmt = null;

		try{

			con = session.connection();
			stmt = con.createStatement();

			insert = "insert into un_resumo_coleta_esgoto (" + "  rece_id, " // 01
							+ "  uneg_id, " // 02
							+ "  rece_amreferencia, " // 03
							+ "  greg_id, " // 04
							+ "  loca_id, " // 05
							+ "  loca_cdelo, " // 06
							+ "  stcm_id, " // 07
							+ "  qdra_id, " // 08
							+ "  rece_cdsetorcomercial, " // 09
							+ "  rota_id, " // 10
							+ "  rece_nnquadra, " // 11
							+ "  last_id, " // 12
							+ "  lest_id, " // 13
							+ "  catg_id, " // 14
							+ "  scat_id, " // 15
							+ "  epod_id, " // 16
							+ "  cltp_id, " // 17
							+ "  lapf_id, " // 18
							+ "  rece_qteconomias , " // 19
							+ "  lepf_id , " // 20
							+ "  rece_voesgoto   , " // 21
							+ "  rece_tmultimaalteracao, " // 22
							+ "  rece_icvlexcedente, " // 23
							+ "  rece_qtligacoes , " // 24
							+ "  iper_id , " // 25
							+ "  cstp_id ," // 26
							+ "  rece_voexcedente, " // 27
							+ "  rece_icmedidoagua, " // 28
							+ "  rece_icpoco, " // 29
							+ "  rece_icmedidofontealternativa, " // 30
							+ "  rece_pcesgoto, " // 31
							+ "  rece_pccoleta, " // 32
							+ "  rece_icvlminimoesgoto, " // 33
							+ "  rece_vofaturado )" // 34
							+ "  values ( " + "  nextval('sequence_un_resumo_coleta_esgoto'), " // 01
							+ resConsumo.getIdUnidadeNegocio() + ", " // 02
							+ anoMesReferencia + ", " // 03
							+ resConsumo.getIdGerenciaRegional() + ", " // 04
							+ resConsumo.getIdLocalidade() + ", " // 05
							+ resConsumo.getIdElo() + ", " // 06
							+ resConsumo.getIdSetorComercial() + ", " // 07
							+ resConsumo.getIdQuadra() + ", " // 08
							+ resConsumo.getCodigoSetorComercial() + ", " // 09
							+ resConsumo.getIdRota() + ", " // 10
							+ resConsumo.getNumeroQuadra() + ", " // 11
							+ resConsumo.getIdLigacaoAguaSituacao() + ", " // 12
							+ resConsumo.getIdLigacaoEsgotoSituacao() + ", " // 13
							+ resConsumo.getIdCategoria() + ", " // 14
							+ resConsumo.getIdSubCategoria() + ", " // 15
							+ resConsumo.getIdEsferaPoder() + ", " // 16
							+ resConsumo.getIdClienteTipo() + ", " // 17
							+ resConsumo.getIdLigacaoAguaPerfil() + ", " // 18
							+ resConsumo.getQuantidadeEconomias() + ", " // 19
							+ resConsumo.getIdLigacaoEsgotoPerfil() + ", " // 20
							+ resConsumo.getQuantidadeColetaEsgoto() + ", " // 21
							+ " now(), " // 22
							+ resConsumo.getIndicadorColetaEsgotoExcedente() + ", " // 23
							+ resConsumo.getQuantidadeLigacoes() + ", " // 24
							+ resConsumo.getIdImovelPerfil() + ", " // 25
							+ resConsumo.getIdConsumoTipo() + ", " // 26
							+ resConsumo.getVolumeExcedente() + ", " // 27
							+ resConsumo.getIndicadorHidrometro() + ", " // 28
							+ resConsumo.getPoco() + ", " // 29
							+ resConsumo.getMedidoFonteAlternativa() + ", " // 30
							+ resConsumo.getPcEsgoto() + ", " // 31
							+ resConsumo.getPcColeta() + ", " // 32
							+ resConsumo.getIcVlMinimoEsgoto() + "," // 33
							+ resConsumo.getVoFaturado() + " )"; // 34

			System.out.println(insert);
			stmt.executeUpdate(insert);

			System.out.print("1 - INSERINDO RESUMO COLETA ESGOTO ");
		}catch(HibernateException e){
			// levanta a exceï¿½ï¿½o para a prï¿½xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate coleta esgoto ");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert coleta esgoto ");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);

			try{
				stmt.close();
				con.close();
			}catch(SQLException e){
				throw new ErroRepositorioException(e, "Erro ao fechar conexï¿½es");
			}
		}
	}

	/**
	 * Deleta resumo Coleta Esgoto antes de gerar
	 * 
	 * @author Marcio Roberto
	 * @date 27/09/2007
	 * @throws ErroRepositorioException
	 */
	public void deletaResumoColetaEsgoto(Integer anoMesReferencia) throws ErroRepositorioException{

		Connection con = null;
		Statement stmt = null;
		Session session = HibernateUtil.getSessionGerencial();
		String delete;
		try{
			con = session.connection();
			stmt = con.createStatement();
			delete = "delete from un_resumo_coleta_esgoto where rece_amreferencia = " + anoMesReferencia;

			stmt.executeUpdate(delete);
			System.out.print("1 - DELETANDO RESUMO COLETA ESGOTO ");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro ao deletar resumo coleta esgoto");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);
			try{
				stmt.close();
				con.close();
			}catch(SQLException e){
				throw new ErroRepositorioException(e, "Erro ao fechar conexï¿½es");
			}
		}
	}

	/**
	 * Seleciona o percentual de consumo de esgoto
	 * 
	 * @author Marcio Roberto
	 * @date 02/10/2007
	 * @param idImovel
	 *            id do imovel que terï¿½ seu volume fixo verificado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public float percentualEsgoto(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			// + "  ch.percentualColeta "
			consulta = "select " + " case when ( " + "	 ch.percentualColeta is null ) then " + "     0.0 " + "   else "
							+ "     ch.percentualColeta " + "   end " + " from " + "  gcom.micromedicao.consumo.ConsumoHistorico ch "
							+ " where " + "  ch.imovel.id = :idImovel and " + "  ch.ligacaoTipo.id = 2 and "
							+ "  ch.referenciaFaturamento = ( select " + "  									anoMesFaturamento " + "  								from "
							+ "  									gcom.cadastro.sistemaparametro.SistemaParametro sp where sp.parmId = "
							+ ConstantesConfig.getIdEmpresa() + " )";

			return ((Float) session.createQuery(consulta).setInteger("idImovel", idImovel).uniqueResult()).floatValue();

		}catch(HibernateException e){
			// levanta a exceï¿½ï¿½o para a prï¿½xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);
		}
	}

	public Categoria obterPrincipalCategoriaConta(int idConta) throws ErroRepositorioException{

		Categoria retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String hql = " select " + "   cc.comp_id.categoria.id as id " + " from " + "   ContaCategoria cc " + " where "
							+ "   cc.comp_id.conta.id = :idConta " + " group by " + "   cc.comp_id.categoria.id "
							+ " order by sum( cc.quantidadeEconomia ) desc ";

			Integer idCategoria = (Integer) session.createQuery(hql).setInteger("idConta", idConta).setMaxResults(1).uniqueResult();

			retorno = new Categoria();

			retorno.setId(idCategoria);
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Verifica se ja foi gerado o ano mes de referencia de faturamento
	 * para o resumo de consumo de agua
	 * 
	 * @author Bruno Leonardo Rodrigues Barros
	 * @return
	 * @throws ErroRepositorioException
	 *             public boolean resumoConsumoAguaGerado( int anomesreferencia, int idSetor )
	 *             throws ErroRepositorioException{
	 *             Session session = HibernateUtil.getSessionGerencial();
	 *             Object quantidade = null;
	 *             try {
	 *             String hql =
	 *             " select " +
	 *             "   count(*) " +
	 *             " from " +
	 *             "   UnResumoConsumoAgua r" +
	 *             " where " +
	 *             " r.referencia = :anomesreferencia and r.gerSetorComercial.id = :id";
	 *             quantidade = session.createQuery(hql)
	 *             .setInteger( "anomesreferencia", anomesreferencia )
	 *             .setInteger( "id", idSetor ).uniqueResult();
	 *             } catch (HibernateException e) {
	 *             throw new ErroRepositorioException(e, "Erro no Hibernate");
	 *             } finally {
	 *             HibernateUtil.closeSession(session);
	 *             }
	 *             return ( quantidade != null &&
	 *             quantidade instanceof Integer &&
	 *             ( ( Integer ) quantidade ) > 0 );
	 *             }
	 *             /**
	 *             Apaga os dados do consumo de agua gerado para o mes de faturamento
	 * @author Bruno Leonardo Rodrigues Barros
	 * @throws ErroRepositorioException
	 *             public void excluirResumoConsumoAguaJaGerado ( int anomesreferencia, int idSetor
	 *             ) throws ErroRepositorioException{
	 *             Session session = HibernateUtil.getSessionGerencial();
	 *             String insert;
	 *             Connection con = null;
	 *             Statement stmt = null;
	 *             try {
	 *             con = session.connection();
	 *             stmt = con.createStatement();
	 *             String delete =
	 *             " delete from un_resumo_consumo_agua r " +
	 *             " where " +
	 *             " r.reca_amreferencia = " + anomesreferencia + " and r.stcm_id = " + idSetor;
	 *             stmt. executeUpdate(delete);
	 *             } catch (HibernateException e) {
	 *             // levanta a exceï¿½ï¿½o para a prï¿½xima camada
	 *             throw new ErroRepositorioException(e, "Erro no Hibernate");
	 *             } catch (SQLException e) {
	 *             throw new ErroRepositorioException(e, "Erro no delete");
	 *             } finally {
	 *             // fecha a sessï¿½o
	 *             HibernateUtil.closeSession(session);
	 *             try {
	 *             stmt.close();
	 *             con.close();
	 *             } catch (SQLException e) {
	 *             throw new ErroRepositorioException(e, "Erro ao fechar conexï¿½es");
	 *             }
	 *             }
	 *             }
	 *             /**
	 *             Apaga os dados do resumo gerado para o anomes e o setor informado
	 * @author Bruno Leonardo Rodrigues Barros/ Roberto Barbalho
	 * @throws ErroRepositorioException
	 */

	public void excluirResumoGerencial(int anomesreferencia, String resumoGerencial, String resumoCampoAnoMes, int idSetor)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSessionGerencial();

		Connection con = null;
		Statement stmt = null;

		try{

			con = session.connection();
			stmt = con.createStatement();

			String delete = " delete from " + resumoGerencial + " where " + resumoCampoAnoMes + " = " + anomesreferencia
							+ " and stcm_id = " + idSetor;

			stmt.executeUpdate(delete);
		}catch(HibernateException e){
			// levanta a exceï¿½ï¿½o para a prï¿½xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no delete");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);

			try{
				stmt.close();
				con.close();
			}catch(SQLException e){
				throw new ErroRepositorioException(e, "Erro ao fechar conexï¿½es");
			}
		}
	}

	public Integer consultarQtdRegistrosResumoLigacoesEconomias(
					InformarDadosGeracaoRelatorioConsultaHelper dadosGeracaoRelatorioConsultaHelper){

		String sql = gerarSQLStringResumoLigacoesEconomia(dadosGeracaoRelatorioConsultaHelper);

		// * Solucao emergencial: Retirar os campos constantes do group by da query para
		// compatibilizar com o Postgres
		// * Solucao definitiva (fazer depois): Ajustar o método que monta a query para montar sem
		// os campos constantes no group by
		sql = this.retirarCamposConstantesGroupBy(sql);

		sql = "SELECT COUNT(*) count FROM (" + sql + ") TAB";
		Session session = HibernateUtil.getSession();
		Integer retorno = (Integer) session.createSQLQuery(sql)//
						.addScalar("count", Hibernate.INTEGER).uniqueResult();
		return retorno == null ? 0 : retorno;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<DetalheLigacaoEconomiaGCSHelper> consultarDetalhesLigacoesEconomiasGCS(
					InformarDadosGeracaoRelatorioConsultaHelper parametrosPesquisa, Integer paginacao) throws ErroRepositorioException{

		ResumoLigacoesEconomiaSqlBuilder sqlBuilder = new ResumoLigacoesEconomiaSqlBuilder(ResumoLigacoesEconomiaSqlBuilder.RESUMO_DETALHE,
						parametrosPesquisa);

		Session session = HibernateUtil.getSession();
		String sqlQuery = sqlBuilder.getSQLQuery().replaceAll("##PAGINACAO##", "WHERE TOTALIZADOR_ID = " + paginacao);

		// * Solucao emergencial: Retirar os campos constantes do group by da query para
		// compatibilizar com o Postgres
		// * Solucao definitiva (fazer depois): Ajustar o método que monta a query para montar sem
		// os campos constantes no group by
		sqlQuery = this.retirarCamposConstantesGroupBy(sqlQuery);

		SQLQuery query = session.createSQLQuery(sqlQuery);
		query.setResultTransformer(Transformers.aliasToBean(DetalheLigacaoEconomiaGCSHelper.class));
		query.addScalar("totalizadorId", Hibernate.INTEGER)//
						.addScalar("totalizador", Hibernate.STRING)//
						.addScalar("detalheId", Hibernate.INTEGER)//
						.addScalar("detalhe", Hibernate.STRING)//
						.addScalar("categoriaId", Hibernate.INTEGER)//
						.addScalar("categoria", Hibernate.STRING)//
						.addScalar("ligacoesComHidrometro", Hibernate.INTEGER)//
						.addScalar("ligacoesSemHidrometro", Hibernate.INTEGER)//
						.addScalar("economiasComHidrometro", Hibernate.INTEGER)//
						.addScalar("economiasSemHidrometro", Hibernate.INTEGER);//

		List<DetalheLigacaoEconomiaGCSHelper> retorno = query.list();
		session.close();
		return retorno;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<SumarioLigacaoPorCategoriaGCSHelper> consultarSumarioLigacoesPorCategoriaGCS(
					InformarDadosGeracaoRelatorioConsultaHelper parametrosPesquisa) throws ErroRepositorioException{

		ResumoLigacoesEconomiaSqlBuilder sqlBuilder = new ResumoLigacoesEconomiaSqlBuilder(
						ResumoLigacoesEconomiaSqlBuilder.SUMARIO_LIGACOES, parametrosPesquisa);

		Session session = HibernateUtil.getSession();
		SQLQuery query = session.createSQLQuery(sqlBuilder.getSQLQuery());
		query.setResultTransformer(Transformers.aliasToBean(SumarioLigacaoPorCategoriaGCSHelper.class));
		query.addScalar("totalizadorId", Hibernate.INTEGER)//
						.addScalar("totalizador", Hibernate.STRING)//
						.addScalar("ligacaoAguaSituacaoId", Hibernate.INTEGER)//
						.addScalar("ligacaoAguaSituacao", Hibernate.STRING)//
						.addScalar("categoriaId", Hibernate.INTEGER)//
						.addScalar("categoria", Hibernate.STRING)//
						.addScalar("qtdLigacoes", Hibernate.INTEGER)//
						.addScalar("qtdEconomias", Hibernate.INTEGER);//

		List<SumarioLigacaoPorCategoriaGCSHelper> retorno = query.list();
		session.close();
		return retorno;
	}

	private String retirarCamposConstantesGroupBy(String sql){

		// Expressão Regular utilizada para reconhecer os campos CONSTANTES do GROUP BY
		// Exs. de campos constantes: 'ESTADO', '0',
		return sql.replaceAll("[']{1,1}[a-zA-Z0-9-\\s]*[']{1,1}[\\s]*,", "");
	}
}
