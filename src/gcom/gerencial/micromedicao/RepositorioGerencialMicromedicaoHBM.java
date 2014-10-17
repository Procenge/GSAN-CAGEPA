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

package gcom.gerencial.micromedicao;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.micromedicao.consumo.LigacaoTipo;
import gcom.relatorio.gerencial.micromedicao.GeradorSqlResumoAnormalidadeConsumo;
import gcom.relatorio.gerencial.micromedicao.GeradorSqlResumoAnormalidadeLeitura;
import gcom.relatorio.gerencial.micromedicao.RelatorioResumoAnormalidadeLeituraBean;
import gcom.util.*;

import java.util.*;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 */
public class RepositorioGerencialMicromedicaoHBM
				implements IRepositorioGerencialMicromedicao {

	private static IRepositorioGerencialMicromedicao instancia;

	/**
	 * Construtor da classe RepositorioMicromedicaoHBM
	 */
	private RepositorioGerencialMicromedicaoHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static IRepositorioGerencialMicromedicao getInstancia(){

		if(instancia == null){
			instancia = new RepositorioGerencialMicromedicaoHBM();
		}

		return instancia;
	}
	
	/**
	 * Método que Verificar Existência de Dados para o Ano/Mês de Referência do Faturamento.
	 * 
	 * @author Josenildo Neves
	 * @date 24/07/2012
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Boolean verificaExistenciaDadosParaAnoMesRefFaturamentoResumoAnormalidadeLeitura(String anoMes)
					throws ErroRepositorioException{

		boolean retorno = false;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		String selectCount = "select count(resumoAnormalidade.id) from "
						+ " gcom.micromedicao.ResumoAnormalidadeLeitura resumoAnormalidade "
						+ " where resumoAnormalidade.anoMesReferencia = " + anoMes;
		// + " and resumoAnormalidade.localidade.id = " + idLocalidade;

		Long qtdResumoAnormalidadeLeitura = (Long) session.createQuery(selectCount).setMaxResults(1).uniqueResult();

		if(qtdResumoAnormalidadeLeitura != null && qtdResumoAnormalidadeLeitura.intValue() > 0){
			retorno = true;
		}

		return retorno;
	}

	/**
	 * Método que consulta os ResumoAnormalidadeHelper
	 * 
	 * @author Flávio Cordeiro
	 * @date 17/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getResumoAnormalidadeHelper(String anoMes) throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

//		String selectCount = "select count(resumoAnormalidade.id) from "
//						+ " gcom.micromedicao.ResumoAnormalidadeLeitura resumoAnormalidade "
//						+ " where resumoAnormalidade.anoMesReferencia = " + anoMes + " and resumoAnormalidade.localidade.id = "
//						+ idLocalidade;
//
//		Long qtdResumoAnormalidadeLeitura = (Long) session.createQuery(selectCount).setMaxResults(1).uniqueResult()/*
//																													 * list(
//																													 * )
//																													 */;
//
//		// if(colecaoCount == null || colecaoCount.isEmpty()){
//		if(qtdResumoAnormalidadeLeitura > ConstantesSistema.ZERO){
//			throw new ErroRepositorioException("atencao.resumo_mes_ja_gerado");
		// }

		try{

			/*
			 * String selectCount = "select count(resumoAnormalidade.id) from " + "
			 * gcom.micromedicao.ResumoAnormalidadeLeitura as resumoAnormalidade " + "
			 * where resumoAnormalidade.anoMesReferencia = " + anoMes;
			 * Collection colecaoCount =
			 * session.createQuery(selectCount).list();
			 * if (colecaoCount == null || colecaoCount.isEmpty()) { throw new
			 * ErroRepositorioException( "atencao.resumo_mes_ja_gerado"); }
			 *//**
			 * Se o tipo de ligacao for igual a Ligacao Agua o objeto vai
			 * ter a ligacaoAgua.id e nao vai ter nenhum dos valores q sejam
			 * ligados ou dependam da tabela imovel, já se a ligação for do
			 * tipo poço o unico atrivuto q estara nulo será ligacaoAgua.id
			 */

			String sql = " select imovel2_.imov_id as idImovel,"
							+ " ligacaoagu1_.lagu_id as idImovelLigacaoAgua,"
							+ " leituraano3_.ltan_id as idLeitura,"
							+ " gerenciare6_.greg_id as idGerenciaRegional,"
							+ " localidade5_.loca_id as idLocalidade, setorcomer7_.stcm_id as idSetorComercial,"
							+ " setorcomer7_.stcm_cdsetorcomercial as cdSetorComercial, quadra8_.qdra_id as idQuadra, quadra8_.qdra_nnquadra as numeroQuadra,"
							+ " rota9_.rota_id as idRota, imovelperf12_.iper_id as idPerfilImovel, ligacaoagu10_.last_id as idLigacaoAgua,"
							+ " ligacaoesg11_.lest_id as idLigacaoEsgoto,"
							+ " (select esferapode17_.epod_id from"
							+ " cliente_imovel clienteimo13_"
							+ " inner join cliente_relacao_tipo clienterel14_ on clienteimo13_.crtp_id=clienterel14_.crtp_id"
							+ " inner join cliente cliente15_ on clienteimo13_.clie_id=cliente15_.clie_id"
							+ " inner join cliente_tipo clientetip16_ on cliente15_.cltp_id=clientetip16_.cltp_id"
							+ " inner join esfera_poder esferapode17_ on clientetip16_.epod_id=esferapode17_.epod_id"
							+ " where imovel2_.imov_id=clienteimo13_.imov_id and clienterel14_.crtp_id=3 and (clienteimo13_.clim_dtrelacaofim is null)) esfera_poder,"
							+ " medicaotip4_.medt_id as idTipoMedicao,"
							+ " leituraano3_.ltan_dsleituraanormalidade as dsLeituraAnormalidade, localidade5_.loca_cdelo as cdLocalidadeElo, "
							+ " localidade5_.uneg_id as idUnidadeNegocio "
							+ " from medicao_historico medicaohis0_"
							+ " left outer join ligacao_agua ligacaoagu1_ on medicaohis0_.lagu_id=ligacaoagu1_.lagu_id"
							+ " left outer join imovel imovel2_ on ligacaoagu1_.lagu_id=imovel2_.imov_id"
							+ " left outer join localidade localidade5_ on imovel2_.loca_id=localidade5_.loca_id"
							+ " left outer join gerencia_regional gerenciare6_ on localidade5_.greg_id=gerenciare6_.greg_id"
							+ " left outer join setor_comercial setorcomer7_ on imovel2_.stcm_id=setorcomer7_.stcm_id"
							+ " left outer join quadra quadra8_ on imovel2_.qdra_id=quadra8_.qdra_id"
							+ " left outer join rota rota9_ on imovel2_.rota_id=rota9_.rota_id"
							+ " left outer join ligacao_agua_situacao ligacaoagu10_ on imovel2_.last_id=ligacaoagu10_.last_id"
							+ " left outer join ligacao_esgoto_situacao ligacaoesg11_ on imovel2_.lest_id=ligacaoesg11_.lest_id"
							+ " left outer join imovel_perfil imovelperf12_ on imovel2_.iper_id=imovelperf12_.iper_id"
							+ " left outer join leitura_anormalidade leituraano3_ on medicaohis0_.ltan_idleituraanormalidadefatu=leituraano3_.ltan_id"
							+ " left outer join medicao_tipo medicaotip4_ on medicaohis0_.medt_id=medicaotip4_.medt_id"
							+ " where medicaohis0_.mdhi_amleitura=" + anoMes // 
							// + " and localidade5_.loca_id =" + idLocalidade //
							+ " order by 1";

			retorno = session.createSQLQuery(sql).//
							addScalar("idImovel", Hibernate.INTEGER).//
							addScalar("idImovelLigacaoAgua", Hibernate.INTEGER).//
							addScalar("idLeitura", Hibernate.INTEGER).//
							addScalar("idGerenciaRegional", Hibernate.INTEGER).//
							addScalar("idLocalidade", Hibernate.INTEGER).//
							addScalar("idSetorComercial", Hibernate.INTEGER).//
							addScalar("cdSetorComercial", Hibernate.INTEGER).//
							addScalar("idQuadra", Hibernate.INTEGER).//
							addScalar("numeroQuadra", Hibernate.INTEGER).//
							addScalar("idRota", Hibernate.INTEGER).//
							addScalar("idPerfilImovel", Hibernate.INTEGER).//
							addScalar("idLigacaoAgua", Hibernate.INTEGER).//
							addScalar("idLigacaoEsgoto", Hibernate.INTEGER).//
							addScalar("esfera_poder", Hibernate.INTEGER).//
							addScalar("idTipoMedicao", Hibernate.INTEGER).//
							addScalar("dsLeituraAnormalidade", Hibernate.STRING).//
							addScalar("cdLocalidadeElo", Hibernate.INTEGER).//
							addScalar("idUnidadeNegocio", Hibernate.INTEGER).//
							list();

			// erro no hibernate
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

	public Object[] pesquisarImovelPorIdLigacaoAgua(Integer idLigacaoAgua) throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;

		Object[] imovel = null;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{
			/**
			 * Se o tipo de ligacao for igual a Ligacao Agua o objeto vai ter a
			 * ligacaoAgua.id e nao vai ter nenhum dos valores q sejam ligados
			 * ou dependam da tabela imovel, já se a ligação for do tipo poço o
			 * unico atrivuto q estara nulo será ligacaoAgua.id
			 */

			String sql = " select imovel.imov_id as idImovel," //
							+ " gerenciaRegional.greg_id as idGerenciaRegional,"//
							+ " localidade.loca_id as idLocalidade,"//
							+ " setorComercial.stcm_id as idSetorComercial,"//
							+ " setorcomercial.stcm_cdsetorcomercial as cdSetorComercial,"//
							+ " quadra.qdra_id as idQuadra,"//
							+ " quadra.qdra_nnquadra as numeroQuadra,"//
							+ " rota.rota_id as idRota,"//
							+ " imovelPerfil.iper_id as idPerfilImovel,"//
							+ " (select esferaPoder.epod_id from cliente_Imovel clienteImoveis" //
							+ "   left join cliente_Relacao_Tipo clienteRelacaoTipo on clienterelacaotipo.crtp_id = clienteimoveis.crtp_id"//
							+ " left join cliente cliente on cliente.clie_id = clienteimoveis.clie_id"//
							+ " left join cliente_Tipo clienteTipo on clientetipo.cltp_id = cliente.cltp_id"//
							+ " left join esfera_Poder esferaPoder on clientetipo.epod_id = esferaPoder.epod_id"//
							+ " where clienteImoveis.imov_id = imovel.imov_id and clienteRelacaoTipo.crtp_id = 3 "//
							+ " and clienteimoveis.clim_dtrelacaofim is null) as esferaPoder,"//
							+ " ligacaoAguaSituacao.last_id as idLigacaoAgua,"//
							+ " ligacaoEsgotoSituacao.lest_id as idLigacaoEsgoto, "//
							+ " localidade.uneg_id as idUnidadeNegocio " //
							+ " from Imovel imovel"//
							+ " left join localidade localidade on imovel.loca_id = localidade.loca_id"//
							+ " left join gerencia_Regional gerenciaRegional on localidade.greg_id = gerenciaRegional.greg_id"//
							+ " left join setor_Comercial setorComercial on setorComercial.stcm_id = imovel.stcm_id"//
							+ " left join quadra quadra on imovel.qdra_id = quadra.qdra_id"//
							+ " left join rota rota on imovel.rota_id = rota.rota_id"//
							+ " left join ligacao_Agua_Situacao ligacaoAguaSituacao on ligacaoAguaSituacao.last_id = imovel.last_id"//
							+ " left join ligacao_Esgoto_Situacao ligacaoEsgotoSituacao on ligacaoEsgotoSituacao.lest_id = imovel.lest_id"//
							+ " left join imovel_Perfil imovelPerfil on imovel.iper_id = imovelPerfil.iper_id"//
							+ " where imovel.imov_id = " + idLigacaoAgua;//

			retorno = session.createSQLQuery(sql).addScalar("idImovel", Hibernate.INTEGER).addScalar("idGerenciaRegional",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER).addScalar("idSetorComercial", //
							Hibernate.INTEGER).addScalar("cdSetorComercial", Hibernate.INTEGER).addScalar("idQuadra", Hibernate.INTEGER)
							.addScalar("numeroQuadra", Hibernate.INTEGER).addScalar("idRota", Hibernate.INTEGER)//
							.addScalar("idPerfilImovel", Hibernate.INTEGER).addScalar("esferaPoder", Hibernate.INTEGER)//
							.addScalar("idLigacaoAgua", Hibernate.INTEGER).addScalar("idLigacaoEsgoto", Hibernate.INTEGER)
							.addScalar("idUnidadeNegocio", Hibernate.INTEGER).list();

			imovel = (Object[]) retorno.iterator().next();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção de atividades pesquisada(s)
		return imovel;
	}

	/**
	 * Método que Verificar Existência de Dados para o Ano/Mês de Referência do Faturamento.
	 * 
	 * @author Josenildo Neves
	 * @date 24/07/2012
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Boolean verificaExistenciaDadosParaAnoMesRefFaturamentoResumoAnormalidadeConsumo(String anoMes) throws ErroRepositorioException{

		boolean retorno = false;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		String selectCount = "select count(resumoAnormalidade.id) from "
						+ "	gcom.micromedicao.consumo.ResumoAnormalidadeConsumo resumoAnormalidade "
						+ "	where resumoAnormalidade.anoMesReferencia = " + anoMes;

		Long qtdResumoAnormalidadeConsumo = (Long) session.createQuery(selectCount).setMaxResults(1).uniqueResult();

		if(qtdResumoAnormalidadeConsumo > ConstantesSistema.ZERO){
			retorno = true;
		}

		return retorno;
	}

	/**
	 * Método que consulta os ResumoAnormalidadeHelper
	 * 
	 * @author Flávio Cordeiro
	 * @date 17/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getResumoAnormalidadeConsumoHelper(String anoMes) throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		// String selectCount = "select count(resumoAnormalidade.id) from "
		// + "	gcom.micromedicao.consumo.ResumoAnormalidadeConsumo resumoAnormalidade "
		// + "	where resumoAnormalidade.anoMesReferencia = " + anoMes;
		//
		// Long qtdResumoAnormalidadeConsumo = (Long) session.createQuery(selectCount).setMaxResults(1).uniqueResult()/*
		// * list(
		// * )
		// */;
		//
		// // if(colecaoCount == null || colecaoCount.isEmpty()){
		// if(qtdResumoAnormalidadeConsumo > ConstantesSistema.ZERO){
		// throw new ErroRepositorioException("atencao.resumo_mes_ja_gerado");
		// }

		try{
			String hql = " select imovel1_.imov_id as idImovel, gerenciare5_.greg_id as idGerenciaRegional,"
							+ " localidade4_.loca_id as idLocalidade, setorcomer6_.stcm_id as idSetorComercial, rota8_.rota_id as idRota,"
							+ " quadra7_.qdra_id as idQuadra, setorcomer6_.stcm_cdsetorcomercial as cdSetorComercial, quadra7_.qdra_nnquadra as numeroQuadra,"
							+ " imovelperf11_.iper_id as idPerfilImovel, ligacaoagu9_.last_id as idLigacaoAgua, ligacaoesg10_.lest_id as idLigacaoEsgoto,"
							+ " (select esferapode16_.epod_id from"
							+ " cliente_imovel clienteimo12_"
							+ " left outer join cliente_relacao_tipo clienterel13_ on clienteimo12_.crtp_id=clienterel13_.crtp_id"
							+ " left outer join cliente cliente14_ on clienteimo12_.clie_id=cliente14_.clie_id"
							+ " left outer join cliente_tipo clientetip15_ on cliente14_.cltp_id=clientetip15_.cltp_id"
							+ " left outer join esfera_poder esferapode16_ on clientetip15_.epod_id=esferapode16_.epod_id"
							+ " where imovel1_.imov_id=clienteimo12_.imov_id and clienterel13_.crtp_id=3 and (clienteimo12_.clim_dtrelacaofim is null)) esfera_poder"
							+ " ," + " consumoano2_.csan_id as idConsumoAnormalidade,"
							+ " ligacaotip3_.lgti_id as idLigacaoTipo, localidade4_.loca_cdelo as cdLocalidadeElo, "
							+ " localidade4_.uneg_id as idUnidadeNegocio "
							+ " from consumo_historico consumohis0_"
							+ " left outer join imovel imovel1_ on consumohis0_.imov_id=imovel1_.imov_id"
							+ " left outer join localidade localidade4_ on imovel1_.loca_id=localidade4_.loca_id"
							+ " left outer join gerencia_regional gerenciare5_ on localidade4_.greg_id=gerenciare5_.greg_id"
							+ " left outer join setor_comercial setorcomer6_ on imovel1_.stcm_id=setorcomer6_.stcm_id"
							+ " left outer join quadra quadra7_ on imovel1_.qdra_id=quadra7_.qdra_id"
							+ " left outer join rota rota8_ on imovel1_.rota_id=rota8_.rota_id"
							+ " left outer join ligacao_agua_situacao ligacaoagu9_ on imovel1_.last_id=ligacaoagu9_.last_id"
							+ " left outer join ligacao_esgoto_situacao ligacaoesg10_ on imovel1_.lest_id=ligacaoesg10_.lest_id"
							+ " left outer join imovel_perfil imovelperf11_ on imovel1_.iper_id=imovelperf11_.iper_id"
							+ " left outer join consumo_anormalidade consumoano2_ on consumohis0_.csan_id=consumoano2_.csan_id"
							+ " left outer join ligacao_tipo ligacaotip3_ on consumohis0_.lgti_id=ligacaotip3_.lgti_id"
							+ " where consumohis0_.cshi_amfaturamento= " + anoMes;

			retorno = session.createSQLQuery(hql).addScalar("idImovel", Hibernate.INTEGER).addScalar("idGerenciaRegional",
							Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER)
							.addScalar("idSetorComercial", Hibernate.INTEGER).addScalar("idRota", Hibernate.INTEGER).addScalar(
											"cdSetorComercial", Hibernate.INTEGER).addScalar("idQuadra", Hibernate.INTEGER).addScalar(
											"numeroQuadra", Hibernate.INTEGER).addScalar("idPerfilImovel", Hibernate.INTEGER).addScalar(
											"idLigacaoAgua", Hibernate.INTEGER).addScalar("idLigacaoEsgoto", Hibernate.INTEGER).addScalar(
											"esfera_poder", Hibernate.INTEGER).addScalar("idConsumoAnormalidade", Hibernate.INTEGER)
							.addScalar("idLigacaoTipo", Hibernate.INTEGER).addScalar("cdLocalidadeElo", Hibernate.INTEGER)
							.addScalar("idUnidadeNegocio", Hibernate.INTEGER).list();

			// erro no hibernate
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
	 * [UC0344] - Consultar Resumo de Anormalidade
	 * 
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @author Pitang
	 * @date 26/06/2012
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List consultarResumoAnormalidadeLeitura(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper,
					Integer tipoLigacao)
					throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = new ArrayList();
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		// A query abaixo realiza uma consulta a tabela de
		// ResumoAnormalidadeLeitura
		try{
			GeradorSqlResumoAnormalidadeLeitura geradorSqlRelatorio = new GeradorSqlResumoAnormalidadeLeitura(
							informarDadosGeracaoRelatorioConsultaHelper);

			String sqlGeradoAgua = geradorSqlRelatorio.montarComandoSQLResumoAnormalidade(tipoLigacao);

			List listAgua = executarSQLAnormalidadesAtribuindoCamposScalar(session, sqlGeradoAgua,
							informarDadosGeracaoRelatorioConsultaHelper);

			retorno.addAll(listAgua);
			// erro no hibernate
		}catch(HibernateException e){
			e.printStackTrace();
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção com os resultados da pesquisa
		return retorno;

	}

	private List executarSQLAnormalidadesAtribuindoCamposScalar(Session session, String sqlGeradoAgua,
					InformarDadosGeracaoRelatorioConsultaHelper dadosConsulta){

		SQLQuery sqlQuery = session.createSQLQuery(sqlGeradoAgua);
		sqlQuery //
						.addScalar("campo", Hibernate.STRING) //
						.addScalar("codigo", Hibernate.INTEGER)//
						.addScalar("descricao", Hibernate.STRING) //
						.addScalar("quantidade", Hibernate.INTEGER).addScalar("idGerencia", Hibernate.INTEGER)//
						.addScalar("descricaoGerencia", Hibernate.STRING)//
						.addScalar("idLocalidade", Hibernate.INTEGER)//
						.addScalar("descricaoLocalidade", Hibernate.STRING)//
						.addScalar("idSetorComercial", Hibernate.INTEGER)//
						.addScalar("descricaoSetorComercial", Hibernate.STRING)//
						.addScalar("idQuadra", Hibernate.INTEGER)//
						.addScalar("descricaoQuadra", Hibernate.STRING)//
						.addScalar("idElo", Hibernate.INTEGER)//
						.addScalar("descricaoElo", Hibernate.STRING)//
						.addScalar("idUni", Hibernate.INTEGER)//
						.addScalar("nomeUni", Hibernate.STRING);//

		if(ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO == dadosConsulta.getOpcaoTotalizacao()
						|| ConstantesSistema.CODIGO_GRUPO_FATURAMENTO == dadosConsulta.getOpcaoTotalizacao()){
			sqlQuery.addScalar("idGrupoFaturamento", Hibernate.INTEGER); //
		}

		return sqlQuery.list(); // LISTAR
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * 
	 * gcom.gerencial.micromedicao.IRepositorioGerencialMicromedicao#consultarTotalResumoSemAnormalidade
	 * (int, gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper,
	 * gcom.gerencial.micromedicao.ResumoAnormalidadeConsultaHelper,
	 * gcom.relatorio.gerencial.micromedicao.RelatorioResumoAnormalidadeLeituraBean)
	 */
	public Integer consultarTotalResumoSemAnormalidade(int opcaoTotalizacao,
					InformarDadosGeracaoRelatorioConsultaHelper dadosParametrosConsulta, ResumoAnormalidadeConsultaHelper itemAnterior,
					RelatorioResumoAnormalidadeLeituraBean bean) throws ErroRepositorioException{

		// cria a coleção de retorno
		Integer retorno = 0;
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		// A query abaixo realiza uma consulta a tabela de
		// ResumoAnormalidadeLeitura
		try{
			GeradorSqlResumoAnormalidadeLeitura sqlLeitura = new GeradorSqlResumoAnormalidadeLeitura(dadosParametrosConsulta, itemAnterior);
			String totalSQLSemAnormalidade = sqlLeitura.montarComandoSQLResumoSemAnormalidade(opcaoTotalizacao, bean,
							dadosParametrosConsulta);

			SQLQuery sqlQuery = session.createSQLQuery(totalSQLSemAnormalidade);

			retorno = ((Number) sqlQuery.uniqueResult()).intValue();
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

	public String criarCondicionaisResumos(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper,
					String nomeColunaTabela){

		String sql = " ";
		// boolean existeWhere = false;
		// A partir daqui sera montanda a parte dos condicionais da query
		// estas condicionais serão usadas se necessarias, o q determina seus
		// usos
		// são os parametros que veem carregados no objeto
		// InformarDadosGeracaoRelatorioConsultaHelper
		// que é recebido do caso de uso [UC0304] Informar Dados para Geração de
		// Relatorio ou COnsulta
		if(informarDadosGeracaoRelatorioConsultaHelper != null){

			// Inicio Parametros simples
			if(informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia().toString().equals("")){
				sql = sql + "re." + nomeColunaTabela + "_amreferencia = "
								+ informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia() + " and ";
				// existeWhere = true;
			}

			// if(informarDadosGeracaoRelatorioConsultaHelper.getFaturamentoGrupo()
			// != null
			// &&
			// informarDadosGeracaoRelatorioConsultaHelper.getFaturamentoGrupo().getId()
			// != null){
			// sql = sql + " re." + +"_id = "
			// +
			// informarDadosGeracaoRelatorioConsultaHelper.getFaturamentoGrupo().getId()
			// + " and ";
			// existeWhere = true;
			// }

			if(informarDadosGeracaoRelatorioConsultaHelper.getGerenciaRegional() != null
							&& informarDadosGeracaoRelatorioConsultaHelper.getGerenciaRegional().getId() != null){
				sql = sql + " re.greg_id = " + informarDadosGeracaoRelatorioConsultaHelper.getGerenciaRegional().getId() + " and ";
				// existeWhere = true;
			}

			// if(informarDadosGeracaoRelatorioConsultaHelper.getEloPolo() !=
			// null
			// &&
			// informarDadosGeracaoRelatorioConsultaHelper.getEloPolo().getId()
			// != null){
			// sql = sql + " eloPolo.loca_id = "
			// +
			// informarDadosGeracaoRelatorioConsultaHelper.getEloPolo().getId()
			// + " and ";
			// existeWhere = true;
			// }

			if(informarDadosGeracaoRelatorioConsultaHelper.getLocalidade() != null
							&& informarDadosGeracaoRelatorioConsultaHelper.getLocalidade().getId() != null){
				sql = sql + " re.loca_id = " + informarDadosGeracaoRelatorioConsultaHelper.getLocalidade().getId() + " and ";
				// existeWhere = true;
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getSetorComercial() != null
							&& informarDadosGeracaoRelatorioConsultaHelper.getSetorComercial().getId() != null){
				sql = sql + " re.stcm_id = " + informarDadosGeracaoRelatorioConsultaHelper.getSetorComercial().getId() + " and ";
				// existeWhere = true;
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getQuadra() != null
							&& informarDadosGeracaoRelatorioConsultaHelper.getQuadra().getId() != null){
				sql = sql + " re.qdra_id = " + informarDadosGeracaoRelatorioConsultaHelper.getQuadra().getId() + " and ";
				// existeWhere = true;
			}

			// Inicio de parametros por colecão
			// sera lida a colecao e montado um IN() a partis dos id extraidos
			// de cada objeto da colecao.
			if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoImovelPerfil() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoImovelPerfil().isEmpty()){

				Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoImovelPerfil().iterator();
				ImovelPerfil imovelPerfil = null;

				sql = sql + " re.iper_id in (";
				while(iterator.hasNext()){
					imovelPerfil = (ImovelPerfil) iterator.next();
					sql = sql + imovelPerfil.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") and ";
				// existeWhere = true;
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoAguaSituacao() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoAguaSituacao().isEmpty()){

				Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoAguaSituacao().iterator();
				LigacaoAguaSituacao ligacaoAguaSituacao = null;

				sql = sql + " re.last_id in (";
				while(iterator.hasNext()){
					ligacaoAguaSituacao = (LigacaoAguaSituacao) iterator.next();
					sql = sql + ligacaoAguaSituacao.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") and ";
				// existeWhere = true;
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoEsgotoSituacao() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoEsgotoSituacao().isEmpty()){

				Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoEsgotoSituacao().iterator();
				LigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;

				sql = sql + " re.lest_id in (";
				while(iterator.hasNext()){
					ligacaoEsgotoSituacao = (LigacaoEsgotoSituacao) iterator.next();
					sql = sql + ligacaoEsgotoSituacao.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") and ";
				// existeWhere = true;
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoCategoria() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoCategoria().isEmpty()){

				Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoCategoria().iterator();
				Categoria categoria = null;

				sql = sql + " re.catg_id in (";
				while(iterator.hasNext()){
					categoria = (Categoria) iterator.next();
					sql = sql + categoria.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") and ";
				// existeWhere = true;
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoEsferaPoder() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoEsferaPoder().isEmpty()){

				Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoEsferaPoder().iterator();
				EsferaPoder esferaPoder = null;

				sql = sql + " re.epod_id in (";
				while(iterator.hasNext()){
					esferaPoder = (EsferaPoder) iterator.next();
					sql = sql + esferaPoder.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") and ";
				// existeWhere = true;
			}
		}

		// if(existeWhere){
		// sql = " where " + sql;
		// }
		// retira o " and " q fica sobrando no final da query
		sql = Util.formatarHQL(sql, 4);

		return sql;
	}

	/**
	 * Gera o resumo das instações de hidrômetro para o ano/mês de referência da
	 * arrecadação.
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
	 * Verificar existência de dados para o ano/mês de referência da arrecadação
	 * do Resumo das instalações de hidrômetros.
	 * [FS0001 – Verificar existência de dados para o ano/mês de referência da
	 * arrecadação]
	 * 
	 * @author Pedro Alexandre
	 * @date 24/04/2007
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaResumoInstalacaoHidrometroParaAnoMesReferenciaArrecadacao(Integer anoMesReferenciaFaturamento,
					Integer idSetorComercial) throws ErroRepositorioException{

		// flag indicando a existência de dados
		boolean retorno = false;

		// Cria a varável que vai armazenar a coleção de retorno da pesquisa
		Collection colecaoRetorno = null;

		// Cria uma instância da sessão
		Session session = HibernateUtil.getSessionGerencial();

		// Cria a variável que vai conter o hql
		String consulta = "";

		try{

			// Cria o hql de pesquisa
			consulta = "from UnResumoInstalacaoHidrometro reih "
							+ "where reih.referencia = :anoMesReferenciaFaturamento and reih.gerSetorComercial.id = :idSetorComercial";

			// Executa o hql
			colecaoRetorno = session.createQuery(consulta).setInteger("anoMesReferenciaFaturamento", anoMesReferenciaFaturamento)
							.setInteger("idSetorComercial", idSetorComercial).list();

			/**
			 * Caso a coleção de retorno não esteja vazia, retorna a flag
			 * indicando que já existe daddos no resumo das instações de
			 * hidrometro para o ano/mês de referência da arrecadação atual.
			 */
			if(colecaoRetorno != null && !colecaoRetorno.isEmpty()){
				retorno = true;
			}

		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
	 * Pesquisa os dados do setor comercial.
	 * 
	 * @author Pedro Alexandre
	 * @date 24/04/2007
	 * @param idSetorComercial
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosSetorComercial(Integer idSetorComercial) throws ErroRepositorioException{

		Object[] dadosSetorComercial = null;

		Session session = HibernateUtil.getSessionGerencial();

		try{

			String hql = "select " + "gerenciaRegional.id, " + "unidadeNegocio.id, " + "elo.id, " + "localidade.id, " + "0, " + "0, "
							+ "0, " + "setorComercial.codigo " + "from " + "GSetorComercial setorComercial "
							+ "left join setorComercial.gerLocalidade localidade " + "left join localidade.gerLocalidade elo "
							+ "left join elo.gerUnidadeNegocio unidadeNegocio "
							+ "left join unidadeNegocio.gerGerenciaRegional gerenciaRegional "
							// + "left join setorComercial.gerMunicipio as municipio "
							// + "left join municipio.gerMicrorregiao as microrregiao "
							// + "left join microrregiao.gerRegiao as regiao "
							+ "where setorComercial.id = :idSetorComercial";

			dadosSetorComercial = (Object[]) session.createQuery(hql).setInteger("idSetorComercial", idSetorComercial).uniqueResult();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção de atividades pesquisada(s)
		return dadosSetorComercial;
	}

	/**
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
	 * Pesquisa a coleção de ids de quadras para o setor comercial informado.
	 * 
	 * @author Pedro Alexandre
	 * @date 24/04/2007
	 * @param idSetorComercial
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarDadosQuadrasPorSetorComercial(Integer idSetorComercial, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "select qdra_id as quadra, rota_id as rota from quadra qdra "
							+ "where stcm_id = :idSetorComercial and "
							+ "qdra_id in (select distinct qdra_id from imovel "
							+ "where imov_id in ( "
							+ "select distinct imov_id from hidrometro_instalacao_hist "
							+ "as hidi where (hidi_dtretiradahidrometro is null or hidi_dtretiradahidrometro between :dataInicial and :dataFinal) "
							+ "union "
							+ "select distinct lagu_id from hidrometro_instalacao_hist "
							+ "as hidi where (hidi_dtretiradahidrometro is null or hidi_dtretiradahidrometro between :dataInicial and :dataFinal )  "
							+ ")" + ")";

			retorno = session.createSQLQuery(consulta).addScalar("quadra", Hibernate.INTEGER).addScalar("rota", Hibernate.INTEGER)
							.setInteger("idSetorComercial", idSetorComercial).setDate("dataInicial", dataInicial).setDate("dataFinal",
											dataFinal).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
	 * Pesquisar dados do imóvel pela quadra.
	 * 
	 * @author Pedro Alexandre
	 * @date 26/04/2007
	 * @param idSetorComercial
	 * @param anoMesFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarDadosImovelResumoInstalacaoHidrometroPorQuadra(Integer idSetorComercial, Date dataInicio,
					Date dataFim) throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";
		try{

			consulta = "select "
							+ "imovel.imov_id as imovid, "
							+ "imovelperfil.iper_id as iperid, "
							+ "ligacaoaguasituacao.last_id as lastid, "
							+ "ligacaoesgotosituacao.lest_id as lestid, "
							+ "ligacaoaguaperfil.lapf_id as lapfid, "
							+ "ligacaoesgoto.lepf_id as lepfid, "
							+ "quadra.qdra_id as idquadra, "
							+ "quadra.qdra_nnquadra as numeroQuadra, "
							+ "imovel.rota_id as rotaid, "
							+ "case when (hidrometro.lagu_id is not null and hidrometro.hidi_dtretiradahidrometro is null) then 1 else 2 end as ramal, "
							+ "case when (hidrometro.imov_id is not null and hidrometro.hidi_dtretiradahidrometro is null) then 1 else 2 end as poco, "
							+ "hidrometro.imov_id as hidrometroimov , "
							+ "hidrometro.lagu_id as hidrometrolagu , "
							+ "hidrometro.hidi_icinstalacaosubstituicao as indicador , "
							+ "to_char(hidrometro.hidi_dtinstalacaohidrometrosistema,'YYYYMM') as anomesinstalacao, "
							+ "to_char(hidrometro.hidi_dtretiradahidrometro,'YYYYMM') as anomesretirada, "
							+ "imovel.hidi_id as idhidrimov, "
							+ "ligacaoagua.hidi_id as idhidrlagu "
							+ "from "
							+ "imovel imovel "
							+ "inner join setor_comercial setor on imovel.stcm_id=setor.stcm_id "
							+ "inner join quadra quadra on imovel.qdra_id=quadra.qdra_id and quadra.stcm_id = :idSetorComercial "
							+ "inner join imovel_perfil imovelperfil on imovel.iper_id=imovelperfil.iper_id "
							+ "inner join ligacao_agua_situacao ligacaoaguasituacao on imovel.last_id=ligacaoaguasituacao.last_id "
							+ "inner join ligacao_esgoto_situacao ligacaoesgotosituacao on imovel.lest_id=ligacaoesgotosituacao.lest_id "
							+ "left outer join ligacao_agua ligacaoagua on imovel.imov_id=ligacaoagua.lagu_id "
							+ "left outer join ligacao_agua_perfil ligacaoaguaperfil on ligacaoagua.lapf_id=ligacaoaguaperfil.lapf_id "
							+ "left outer join ligacao_esgoto ligacaoesgoto on imovel.imov_id=ligacaoesgoto.lesg_id "
							+ "left outer join ligacao_esgoto_perfil ligacaoesgotoperfil on ligacaoesgoto.lepf_id=ligacaoesgotoperfil.lepf_id "
							+ "left join hidrometro_instalacao_hist hidrometro on (imovel.imov_id=hidrometro.imov_id or imovel.imov_id = hidrometro.lagu_id) "
							+ "where setor.stcm_id=:idSetorComercial "
							+ " and imovel.imov_id in (select distinct(imov_id) from hidrometro_instalacao_hist "
							+ "where (hidi_dtretiradahidrometro is null or hidi_dtretiradahidrometro between :dataInicio and :dataFim) "
							+ "union	" + "select distinct(lagu_id) from hidrometro_instalacao_hist "
							+ "where (hidi_dtretiradahidrometro is null or hidi_dtretiradahidrometro between :dataInicio and :dataFim) "
							+ ")";

			retorno = session.createSQLQuery(consulta).addScalar("imovid", Hibernate.INTEGER).addScalar("iperid", Hibernate.INTEGER)
							.addScalar("lastid", Hibernate.INTEGER).addScalar("lestid", Hibernate.INTEGER).addScalar("lapfid",
											Hibernate.INTEGER).addScalar("lepfid", Hibernate.INTEGER).addScalar("idquadra",
											Hibernate.INTEGER).addScalar("numeroQuadra", Hibernate.INTEGER).addScalar("rotaid",
											Hibernate.INTEGER).addScalar("ramal", Hibernate.INTEGER).addScalar("poco", Hibernate.INTEGER)

							.addScalar("hidrometroimov", Hibernate.INTEGER).addScalar("hidrometrolagu", Hibernate.INTEGER).addScalar(
											"indicador", Hibernate.SHORT).addScalar("anomesinstalacao", Hibernate.STRING).addScalar(
											"anomesretirada", Hibernate.STRING).addScalar("idhidrimov", Hibernate.INTEGER).addScalar(
											"idhidrlagu", Hibernate.INTEGER)

							.setInteger("idSetorComercial", idSetorComercial).setDate("dataInicio", dataInicio).setDate("dataFim", dataFim)
							.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0564] Gerar Resumo das Instalações de Hidrômetros
	 * Pesquisa os dados do cliente responsável do imóvel informado.
	 * 
	 * @author Pedro Alexandre
	 * @date 02/05/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosClienteResponsavelPorImovel(Integer idImovel) throws ErroRepositorioException{

		Object[] retorno = null;

		// Cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			// Monta o hql
			consulta = "select epod.id, cltp.id " + "from ClienteImovel clim " + "inner join clim.imovel imov "
							+ "inner join clim.cliente clie " + "inner join clie.clienteTipo cltp " + "inner join cltp.esferaPoder epod "
							+ "inner join clim.clienteRelacaoTipo crtp "
							+ "where imov.id = :idImovel and crtp.id = :idTipoRelacao and clim.dataFimRelacao is null ";

			// Executa o hql
			retorno = (Object[]) session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("idTipoRelacao",
							ClienteRelacaoTipo.RESPONSAVEL).uniqueResult();

			// Erro na consulta
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// Retorna a coleção pesquisada
		return retorno;
	}

	/**
	 * [UC0564] Gerar Resumo das Instalações de Hidrômetros
	 * Pesquisa os dados da instalação do hidrômetro no histórico
	 * para o imóvel informado.
	 * 
	 * @author Pedro Alexandre
	 * @date 02/05/2007
	 * @param idImovel
	 * @param anoMesFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDadosHidrometroInstalacaoHistoricoPorImovel(Integer idImovel, Date dataInicio, Date dataFim)
					throws ErroRepositorioException{

		Collection retorno = null;

		// Cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			// Monta o hql
			consulta = "select imov.id, lagu.id, "
							+ "concat(substring(hidi.dataImplantacaoSistema,1,4), substring(hidi.dataImplantacaoSistema,6,2)), "
							+ "concat(substring(hidi.dataRetirada,1,4), substring(hidi.dataRetirada,6,2)), "
							+ "hidi.indicadorInstalcaoSubstituicao, " + "imov.hidrometroInstalacaoHistorico.id, "
							+ "lagu.hidrometroInstalacaoHistorico.id " + "from HidrometroInstalacaoHistorico hidi "
							+ "left join hidi.imovel imov " + "left join hidi.ligacaoAgua lagu "
							+ "where (imov.id = :idImovel or lagu.id = :idImovel) and "
							+ "((hidi.dataImplantacaoSistema between :dataInicio and :dataFim) or "
							+ "(hidi.dataRetirada between :dataInicio and :dataFim)) ";

			// Executa o hql
			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel).setDate("dataInicio", dataInicio).setDate("dataFim",
							dataFim).list();

			// Erro na consulta
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// Retorna a coleção pesquisada
		return retorno;
	}

	/**
	 * [UC0564] Gerar Resumo das Instalações de Hidrômetros
	 * Pesquisa os ids dos setores comercias dos imóveis
	 * que tem hidrometro instalado no histórico
	 * 
	 * @author Pedro Alexandre
	 * @date 08/05/2007
	 * @param anoMesFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarIdsSetorComercialParaGerarResumoInstalacaoHidrometro(Integer anoMesFaturamento)
					throws ErroRepositorioException{

		Collection<Integer> retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "select distinct stcm_id as setorComercial " + "from imovel "
							+ "where imov_id in (select distinct imov_id from hidrometro_instalacao_hist hidi "
							+ "where hidi.hidi_dtretiradahidrometro is null " + "union "
							+ "select distinct lagu_id from hidrometro_instalacao_hist hidi "
							+ "where hidi.hidi_dtretiradahidrometro is null " + ") ";

			retorno = session.createSQLQuery(consulta).addScalar("setorComercial", Hibernate.INTEGER).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Método que retona uma lista de objeto xxxxx que representa o
	 * UC0551 - Gerar Resumo Leitura Anormalidade
	 * 
	 * @author Ivan Sérgio
	 * @date 23/04/2007, 28/05/2007, 21/06/2007, 27/07/2007, 08/08/2007
	 * @Alteracao: 28/05/2007 - Troca do parametro Localidade para Setor Comercial;
	 * @Alteracao: 21/06/2007 - LTAN_ID pode ser Null;
	 *             Duas quebras novas: Empresa(EMPR_ID), Situacao Leitura(LTST_ID);
	 * @Alteracao: 27/07/2007 - Trocar o parametro AMREFERENCIAARRECADACAO para
	 *             AMREFERENCIAFATURAMENTO;
	 *             Alteracao na forma de consulta para o campo LAST_ID. Agora por CONTA ou IMOVEL;
	 * @Alteracao: 08/08/2007 - Alteracao na forma de consulta dos principais campos. Agora por
	 *             CONTA ou IMOVEL;
	 * @param setorComercial
	 * @param anoMesLeitura
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getImoveisResumoLeituraAnormalidade(int setorComercial, int anoMesLeitura) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String sql = " SELECT imovel.imov_id, "
							+ "	0 as lapf_id, "
							+ "	ligacao_esgoto.lepf_id, "
							+ "	medicao_historico.medt_id, "
							+

							"	case when (ltan_idleituraanormalidadefatu is not null) then "
							+ "		medicao_historico.ltan_idleituraanormalidadefatu "
							+ "	else "
							+ "		0 "
							+ "	end as ltan_idleituraanormalidadefatu, "
							+

							// "	setor_comercial.stcm_cdsetorcomercial, " +
							// "	quadra.qdra_nnquadra, " +
							"	rota.empr_id, "
							+ "	medicao_historico.ltst_idleiturasituacaoatual, "
							+ "	medicao_historico.mdhi_id "
							+ // Campo utilizado para evitar o group by do union
							"FROM medicao_historico medicao_historico "
							+ "	INNER JOIN imovel imovel ON imovel.imov_id = medicao_historico.imov_id "
							+ "	INNER JOIN localidade localidade ON localidade.loca_id = imovel.loca_id "
							+ "	INNER JOIN quadra quadra ON quadra.qdra_id = imovel.qdra_id "
							+ "	INNER JOIN rota rota ON rota.rota_id = imovel.rota_id "
							+ "	INNER JOIN ligacao_esgoto ligacao_esgoto ON ligacao_esgoto.lesg_id = medicao_historico.imov_id "
							+ "	INNER JOIN setor_comercial setor_comercial ON setor_comercial.stcm_id = imovel.stcm_id "
							+ "WHERE medicao_historico.mdhi_amleitura = " + anoMesLeitura + " " + "   AND quadra.stcm_id = "
							+ setorComercial + " " + "	AND medicao_historico.medt_id = " + LigacaoTipo.LIGACAO_ESGOTO + " ";

			String sqlSegundo = "SELECT imovel.imov_id, "
							+ "	ligacao_agua.lapf_id, "
							+ "	0 AS lepf_id, "
							+ "	medicao_historico.medt_id, "
							+

							"	case when (ltan_idleituraanormalidadefatu is not null) then "
							+ "		medicao_historico.ltan_idleituraanormalidadefatu "
							+ "	else "
							+ "		0 "
							+ "	end as ltan_idleituraanormalidadefatu, "
							+

							// "	setor_comercial.stcm_cdsetorcomercial, " +
							// "	quadra.qdra_nnquadra, " +
							"	rota.empr_id, "
							+ "	medicao_historico.ltst_idleiturasituacaoatual, "
							+ "	medicao_historico.mdhi_id "
							+ // Campo utilizado para evitar o group by do union
							"FROM medicao_historico medicao_historico "
							+ "	INNER JOIN imovel imovel ON imovel.imov_id = medicao_historico.lagu_id "
							+ "	INNER JOIN localidade localidade ON localidade.loca_id = imovel.loca_id "
							+ "	INNER JOIN quadra quadra ON quadra.qdra_id = imovel.qdra_id "
							+ "	INNER JOIN rota rota ON rota.rota_id = imovel.rota_id "
							+ "	INNER JOIN ligacao_agua ligacao_agua ON ligacao_agua.lagu_id = medicao_historico.lagu_id "
							+ "	INNER JOIN setor_comercial setor_comercial ON setor_comercial.stcm_id = imovel.stcm_id "
							+ "WHERE medicao_historico.mdhi_amleitura = " + anoMesLeitura + " " + "	AND quadra.stcm_id = " + setorComercial
							+ " " + "	AND medicao_historico.medt_id = " + LigacaoTipo.LIGACAO_AGUA + " ";

			sql = sql + " UNION " + sqlSegundo;

			// System.out.print("------------------->   " + sql);

			retorno = session.createSQLQuery(sql).addScalar("imov_id", Hibernate.INTEGER)
			// .addScalar("greg_id", Hibernate.INTEGER)
							// .addScalar("uneg_id", Hibernate.INTEGER)
							// .addScalar("loca_cdelo", Hibernate.INTEGER)
							// .addScalar("loca_id", Hibernate.INTEGER)
							// .addScalar("stcm_id", Hibernate.INTEGER)
							// .addScalar("rota_id", Hibernate.INTEGER)
							// .addScalar("qdra_id", Hibernate.INTEGER)
							// .addScalar("iper_id", Hibernate.INTEGER)
							// .addScalar("lest_id", Hibernate.INTEGER)
							.addScalar("lapf_id", Hibernate.INTEGER).addScalar("lepf_id", Hibernate.INTEGER).addScalar("medt_id",
											Hibernate.INTEGER).addScalar("ltan_idleituraanormalidadefatu", Hibernate.INTEGER)
							// .addScalar("stcm_cdsetorcomercial", Hibernate.INTEGER)
							// .addScalar("qdra_nnquadra", Hibernate.INTEGER)
							.addScalar("empr_id", Hibernate.INTEGER).addScalar("ltst_idleiturasituacaoatual", Hibernate.INTEGER).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [FS0003] - Verificar Existencia de conta para o mes de faturamento
	 * Metodo utilizado para auxiliar o [UC0551 - Gerar Resumo Leitura Anormalidade]
	 * para recuperar o valo da Situacao da Ligacao de Agua.
	 * 
	 * @author Ivan Sérgio
	 * @date 27/07/2007, 08/08/2007
	 * @alteracao - Receber os outros campos da getImoveisResumoLeituraAnormalidade;
	 * @throws ErroRepositorioException
	 * @return List
	 */
	public List pesquisarLeituraAnormalidadeComplementar(Integer imovel, Integer dataFaturamento) throws ErroRepositorioException{

		List retorno = null;
		Session session = HibernateUtil.getSession();
		String hql = "";

		try{
			hql = "select " + "	0 as tipo, " + "	conta.id, " + "	conta.localidade.gerenciaRegional.id, "
							+ "	conta.localidade.unidadeNegocio.id, " + "	conta.localidade.localidade.id, " + "	conta.localidade.id, "
							+ "	conta.quadraConta.setorComercial.id, " + "	conta.quadraConta.rota.id, " + "	conta.quadraConta.id, "
							+ "	conta.imovelPerfil.id, " + "	conta.ligacaoAguaSituacao.id, " + "	conta.ligacaoEsgotoSituacao.id, "
							+ "	conta.codigoSetorComercial, " + "	conta.quadra, " + "	conta.consumoTarifa.id, "
							+ "	conta.quadraConta.rota.faturamentoGrupo.id " + "from " + "	gcom.faturamento.conta.Conta conta " + "where "
							+ "	conta.imovel.id = :imovel and " + "	conta.referencia = :anoMesReferenciaFaturamento and "
							+ "	(conta.debitoCreditoSituacaoAtual.id = 0 or " + "	conta.debitoCreditoSituacaoAnterior.id = 0)";

			retorno = session.createQuery(hql).setInteger("imovel", imovel).setInteger("anoMesReferenciaFaturamento", dataFaturamento)
							.list();

			// Caso nao exista conta para o ano/mes de faturamento
			// utilizar last_id de imovel
			if(retorno == null || retorno.isEmpty()){
				hql = "select " + "	1 as tipo, " + "	imovel.id, " + "	imovel.localidade.gerenciaRegional.id, "
								+ "	imovel.localidade.unidadeNegocio.id, " + "	imovel.localidade.localidade.id, "
								+ "	imovel.localidade.id, " + "	imovel.quadra.setorComercial.id, " + "	imovel.rota.id, "
								+ "	imovel.quadra.id, " + "	imovel.imovelPerfil.id, " + "	imovel.ligacaoAguaSituacao.id, "
								+ "	imovel.ligacaoEsgotoSituacao.id, " + "	imovel.setorComercial.codigo, "
								+ "	imovel.quadra.numeroQuadra, " + "	imovel.consumoTarifa.id, " + "	imovel.rota.faturamentoGrupo.id "
								+ "from " + "	gcom.cadastro.imovel.Imovel imovel " + "where " + "	imovel.id = :imovel";

				retorno = session.createQuery(hql).setInteger("imovel", imovel).list();
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Método que retona uma lista de objeto xxxxx que representa o agrupamento
	 * id do imovel id gerencia regional id licalidade id do setor comercial
	 * codigo do setor comercial id rota id guradra numero da quadra id perfil
	 * do imovel id situacao da ligacao de agua id situacao da ligacao de esgoto
	 * principal categoria do imovel esfera de poder do cliente responsavel
	 * indicador de existencia de hidrometro
	 * 
	 * @author Thiago Tenório, Ivan Sérgio
	 * @date 25/04/2007, 08/08/2007
	 * @alteracao: Consultar por Situacao do Hidrometro diferente de Instalado;
	 *             Dois campos adicionados a quebra: Motivo Baixa e Classe Metrologica;
	 * @param idLocalidade
	 *            id da localida a ser pesquisada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection getHidrometrosResumoHidrometro(Integer idMarca, int indice, int qtRegistros) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String hql = "SELECT hidroMotivoBaixa.id, "
							+ "    hidroLocalArmazenagem.id, "
							+ "    hidroTipo.id, "
							+ // 4
							"    hidroSituacao.id, "
							+ // 8
							"    hidrometro.anoFabricacao, "
							+ // 5
							"    hidroMarca.id, "
							+ // 3
							"    hidroDiametro.id, "
							+ // 1
							"    hidroCapacidade.id, "
							+ // 2
							"    hidrometro.indicadorMacromedidor, "
							+ // 6
							"    count(hidrometro.id), "
							+ //
							"	 hidroMotivoBaixa.id, " + "	 hidroClasseMetrologica.id " + "  FROM " + "   Hidrometro hidrometro "
							+ "  left join hidrometro.hidrometroLocalArmazenagem hidroLocalArmazenagem "
							+ "  inner join hidrometro.hidrometroTipo hidroTipo "
							+ "  inner join hidrometro.hidrometroSituacao hidroSituacao "
							+ "  inner join hidrometro.hidrometroMarca hidroMarca "
							+ "  inner join hidrometro.hidrometroDiametro hidroDiametro "
							+ "  inner join hidrometro.hidrometroCapacidade hidroCapacidade "
							+ "  inner join hidrometro.hidrometroClasseMetrologica hidroClasseMetrologica "
							+ "  left join hidrometro.hidrometroMotivoBaixa hidroMotivoBaixa "
							+

							" WHERE "
							+
							// "  hidroMotivoBaixa.id is null and hidroMarca.id = :idMarca " +
							"  hidroSituacao.id <> 1 and hidroMarca.id = :idMarca " +

							" group by hidroMotivoBaixa.id, hidroLocalArmazenagem.id, hidroTipo.id, hidroSituacao.id, "
							+ " hidrometro.anoFabricacao, hidroMarca.id, hidroDiametro.id, hidroCapacidade.id, "
							+ " hidrometro.indicadorMacromedidor, hidroMotivoBaixa.id, hidroClasseMetrologica.id ";

			retorno = session.createQuery(hql).setInteger("idMarca", idMarca).setFirstResult(indice).setMaxResults(qtRegistros).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Método que insere em batch uma lista de ResumoRegistroAtendimento
	 * [UC0586] - Gerar Resumo Resgistro Atendimento
	 * 
	 * @author Thiago Tenório
	 * @date 30/04/2007
	 * @param listaResumoRegistroAtendimento
	 * @throws ErroRepositorioException
	 */
	public void inserirResumoHidrometroBatch(List<UnResumoHidrometro> listaResumoHidrometro) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		if(listaResumoHidrometro != null && !listaResumoHidrometro.isEmpty()){
			Iterator it = listaResumoHidrometro.iterator();
			int i = 1;
			try{
				while(it.hasNext()){

					Object obj = it.next();

					session.save(obj);

					if(i % 50 == 0 || !it.hasNext()){
						// 20, same as the JDBC batch size
						// flush a batch of inserts and release memory:
						session.flush();
						session.clear();
					}
					i++;
				}

				session.flush();
				session.clear();

			}finally{
				HibernateUtil.closeSession(session);
			}
		}
	}

	/**
	 * [UC0269] - Consultar Resumo dos Registro de Atendimentos
	 * 
	 * @author Thiago Tenório
	 * @date 25/04/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List consultarResumoHidrometro(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{

			GeradorSqlRelatorio geradorSqlRelatorio = new GeradorSqlRelatorio(GeradorSqlRelatorio.RESUMO_HIDROMETRO,
							informarDadosGeracaoRelatorioConsultaHelper);

			String sql = geradorSqlRelatorio.sqlNivelUm(geradorSqlRelatorio.getNomeCampoFixo(), geradorSqlRelatorio.getNomeTabelaFixo(),
							geradorSqlRelatorio.getNomeTabelaFixoTotal(), "'"
											+ informarDadosGeracaoRelatorioConsultaHelper.getDescricaoOpcaoTotalizacao() + "'", "", "", "",
							false);

			// faz a pesquisa
			retorno = session.createSQLQuery(sql).addScalar("estado", Hibernate.STRING).addScalar("idGerencia", Hibernate.INTEGER)
							.addScalar("descricaoGerencia", Hibernate.STRING).addScalar("idElo", Hibernate.INTEGER).addScalar(
											"descricaoElo", Hibernate.STRING).addScalar("idLocalidade", Hibernate.INTEGER).addScalar(
											"descricaoLocalidade", Hibernate.STRING).addScalar("idSetorComercial", Hibernate.INTEGER)
							.addScalar("descricaoSetorComercial", Hibernate.STRING).addScalar("idQuadra", Hibernate.INTEGER).addScalar(
											"descricaoQuadra", Hibernate.STRING).addScalar("idLigacaoAguaSituacao", Hibernate.INTEGER)
							.addScalar("descricaoLigacaoAguaSituacao", Hibernate.STRING).addScalar("idLigacaoEsgotoSituacao",
											Hibernate.INTEGER).addScalar("descricaoLigacaoEsgotoSituacao", Hibernate.STRING).addScalar(
											"idCategoria", Hibernate.INTEGER).list();

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
	 * calcula a quantidade de hidrometro instalados atualmente ramal de água
	 * [UC0564] Gerar Resumo das Instalações de Hidrômetro
	 * 
	 * @author Pedro Alexandre
	 * @date 08/08/2007
	 * @param idLigacaoAgua
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarQuantidadeHidrometroInstalacaoRamalAgua(Integer idLigacaoAgua) throws ErroRepositorioException{

		Collection retorno = null;

		Integer qtd = 0;

		// Cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			// Monta o hql
			consulta = "select hidi " + "from HidrometroInstalacaoHistorico hidi " + "left join hidi.ligacaoAgua lagu "
							+ "where lagu.id = :idLigacaoAgua and " + "hidi.dataRetirada is null ";

			// Executa o hql
			retorno = session.createQuery(consulta).setInteger("idLigacaoAgua", idLigacaoAgua).list();

			if(retorno != null){
				qtd = retorno.size();
			}
			// Erro na consulta
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// Retorna a coleção pesquisada
		return qtd;
	}

	/**
	 * calcula a quantidade de hidrometro instalados atualmente no poço
	 * [UC0564] Gerar Resumo das Instalações de Hidrômetro
	 * 
	 * @author Pedro Alexandre
	 * @date 08/08/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarQuantidadeHidrometroInstalacaoPoco(Integer idImovel) throws ErroRepositorioException{

		Collection retorno = null;

		Integer qtd = 0;

		// Cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			// Monta o hql
			consulta = "select hidi " + "from HidrometroInstalacaoHistorico hidi " + "left join hidi.imovel imov "
							+ "where imov.id = :idImovel and " + "hidi.dataRetirada is null ";

			// Executa o hql
			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel).list();

			if(retorno != null){
				qtd = retorno.size();
			}
			// Erro na consulta
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// Retorna a coleção pesquisada
		return qtd;
	}

	/**
	 * Método que retorna uma lista de registros de anormalidades de consulmo filtrados pelo tipo de
	 * ligacao (Agua / Esgoto) <br>
	 * [UC0344] Consultar Resumo de Anormalidade
	 * Consulta na tabela resumoAnormalidadeConsumo os regitros conforme os
	 * parametros passados pelo [UC0304] Informar Dados para geração de
	 * Relatório ou Consulta
	 * 
	 * @date 27/06/2012
	 * @author Marlos Ribeiro
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @param tipoLigacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List consultarResumoAnormalidadeConsumo(
					InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper, Integer tipoLigacao)
					throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = new ArrayList();
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		// A query abaixo realiza uma consulta a tabela de
		// ResumoAnormalidadeLeitura
		try{
			GeradorSqlResumoAnormalidadeConsumo geradorSqlRelatorio = new GeradorSqlResumoAnormalidadeConsumo(
							informarDadosGeracaoRelatorioConsultaHelper);

			String sqlGerado = geradorSqlRelatorio.montarComandoSQLResumoAnormalidade(tipoLigacao);

			List listResumos = executarSQLAnormalidadesAtribuindoCamposScalar(session, sqlGerado,
							informarDadosGeracaoRelatorioConsultaHelper);

			retorno.addAll(listResumos);
			// erro no hibernate
		}catch(HibernateException e){
			e.printStackTrace();
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção com os resultados da pesquisa
		return retorno;
	}
}