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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gerencial.cobranca;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.*;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.gerencial.bean.InformarDadosGeracaoResumoAcaoConsultaEventualHelper;
import gcom.gerencial.bean.InformarDadosGeracaoResumoAcaoConsultaHelper;
import gcom.gerencial.cobranca.bean.ResumoPendenciaContasGerenciaHelper;
import gcom.gerencial.cobranca.bean.ResumoPendenciaCreditoARealizarGerenciaHelper;
import gcom.gerencial.cobranca.bean.ResumoPendenciaDebitosACobrarGerenciaHelper;
import gcom.gerencial.cobranca.bean.ResumoPendenciaGuiasPagamentoGerenciaHelper;
import gcom.util.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.*;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 */
public class RepositorioGerencialCobrancaHBM
				implements IRepositorioGerencialCobranca {

	private static IRepositorioGerencialCobranca instancia;

	/**
	 * Construtor da classe RepositorioMicromedicaoHBM
	 */
	private RepositorioGerencialCobrancaHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static IRepositorioGerencialCobranca getInstancia(){

		if(instancia == null){
			instancia = new RepositorioGerencialCobrancaHBM();
		}

		return instancia;
	}

	/**
	 * Método que consulta os ResumoSituacaoEspecialCobrancaHelper
	 * 
	 * @author Thiago Toscano
	 * @date 15/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getResumoSituacaoEspecialCobrancaHelper(int idLocalidade) throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;
		SQLQuery query = null;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{
			// pesquisa a coleção de atividades e atribui a variável "retorno"
			String sql = " SELECT" //
							+ " DISTINCT(imovel.imov_id) as idImovel," //
							+ " gerenciaRegional.greg_id as idGerenciaRegional," //
							+ " localidade.loca_id as idLocalidade," //
							+ " setorComercial.stcm_id as idSetorComercial," //
							+ " rota.rota_id as idRota," //
							+ " quadra.qdra_id as idQuadra," //
							+ " setorComercial.stcm_cdsetorcomercial as cdSetorComercial," //
							+ " quadra.qdra_nnQuadra as numeroQuadra," //
							+ " imovelPerfil.iper_id as idImovelPerfil," //
							+ " ligacaoAguaSituacao.last_id as idLigacaoAguaSituacao," //
							+ " ligacaoEsgotoSituacao.lest_id as idLigacaoEsgotoSituacao," //
							+ " (select " //
							+ " esferaPoder.epod_id " //
							+ " from cliente_Imovel clienteImoveis" //
							+ " left join cliente_Relacao_Tipo clienteRelacaoTipo on clienterelacaotipo.crtp_id = clienteimoveis.crtp_id" //
							+ " left join cliente cliente on cliente.clie_id = clienteimoveis.clie_id" //
							+ " left join cliente_Tipo clienteTipo on clientetipo.cltp_id = cliente.cltp_id" //
							+ " left join esfera_Poder esferaPoder on clientetipo.epod_id = esferaPoder.epod_id" //
							+ " where clienteImoveis.imov_id = imovel.imov_id and clienteRelacaoTipo.crtp_id = "
							+ ClienteRelacaoTipo.RESPONSAVEL //
							+ " and clienteimoveis.clim_dtrelacaofim is null) as esferaPoder," //
							+ " cobrancaSituacaoTipo.cbsp_id as idCobrancaSituacaoTipo," //
							+ " cobrancaSituacaoMotivo.cbsm_id as idCobrancaSituacaoMotivo," //
							+ " cobrancaSituacaoHistorico.cbsh_amcobrancasituacaoinicio as anoMesCobrancaSitInicio ," //
							+ " cobrancaSituacaoHistorico.cbsh_amcobrancasituacaofim as anoMesCobrancaSitFim ," //
							+ " unidadeNegocio.uneg_id as idUnidadeNegocio" //
							+ " FROM" //
							+ " COBRANCA_SITUACAO_HISTORICO cobrancaSituacaoHistorico " //
							+ " INNER JOIN COBRANCA_SITUACAO_TIPO cobrancaSituacaoTipo ON cobrancaSituacaoHistorico.CBSP_ID = cobrancaSituacaoTipo.CBSP_ID" //
							+ " INNER JOIN COBRANCA_SITUACAO_MOTIVO cobrancaSituacaoMotivo ON cobrancaSituacaoHistorico.CBSM_ID = cobrancaSituacaoMotivo.CBSM_ID" //
							+ " INNER JOIN IMOVEL imovel ON cobrancaSituacaoHistorico.IMOV_ID = imovel.IMOV_ID" //
							+ " INNER JOIN LOCALIDADE localidade on imovel.loca_id = localidade.loca_id " //
							+ " LEFT JOIN UNIDADE_NEGOCIO unidadeNegocio ON localidade.UNEG_ID = unidadeNegocio.UNEG_ID" //
							+ " INNER JOIN GERENCIA_REGIONAL gerenciaRegional on localidade.greg_id = gerenciaRegional.greg_id" //
							+ " INNER JOIN SETOR_COMERCIAL setorComercial on setorComercial.stcm_id = imovel.stcm_id " //
							+ " INNER JOIN QUADRA quadra on imovel.qdra_id = quadra.qdra_id" //
							+ " INNER JOIN ROTA rota on imovel.rota_id = rota.rota_id" //
							+ " INNER JOIN LIGACAO_AGUA_SITUACAO ligacaoAguaSituacao on ligacaoAguaSituacao.last_id = imovel.last_id" //
							+ " INNER JOIN LIGACAO_ESGOTO_SITUACAO ligacaoEsgotoSituacao on ligacaoEsgotoSituacao.lest_id = imovel.lest_id" //
							+ " LEFT JOIN IMOVEL_PERFIL imovelPerfil on imovel.iper_id = imovelPerfil.iper_id" //
							+ " WHERE" //
							+ " cobrancaSituacaoHistorico.cbsh_amcobrancaretirada IS NULL" //
							+ " AND localidade.loca_id = :idLocalidade"; //

			query = session.createSQLQuery(sql);

			query.setInteger("idLocalidade", idLocalidade);

			retorno = query.addScalar("idImovel", Hibernate.INTEGER)
			//
							.addScalar("idGerenciaRegional", Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER)
							//
							.addScalar("idSetorComercial", Hibernate.INTEGER).addScalar("idRota", Hibernate.INTEGER)
							//
							.addScalar("idQuadra", Hibernate.INTEGER).addScalar("cdSetorComercial", Hibernate.INTEGER)
							//
							.addScalar("numeroQuadra", Hibernate.INTEGER).addScalar("idImovelPerfil", Hibernate.INTEGER).addScalar(
											"idLigacaoAguaSituacao", Hibernate.INTEGER).addScalar("idLigacaoEsgotoSituacao",
											Hibernate.INTEGER).addScalar("esferaPoder", Hibernate.INTEGER).addScalar(
											"idCobrancaSituacaoTipo", Hibernate.INTEGER)
							//
							.addScalar("idCobrancaSituacaoMotivo", Hibernate.INTEGER).addScalar("anoMesCobrancaSitInicio",
											Hibernate.INTEGER)//
							.addScalar("anoMesCobrancaSitFim", Hibernate.INTEGER).addScalar("idUnidadeNegocio", Hibernate.INTEGER) //
							.list();

			/*
			 * String hql = " select "
			 * +
			 * // " new " +
			 * // ResumoCobrancaSituacaoEspecialHelper.class.getName() + "
			 * // ( " +
			 * "	distinct (imovel.id), gerenciaRegional.id,  " +
			 * "	localidade.id, setorComercial.id,  "
			 * + "	rota.id, quadra.id, setorComercial.codigo, "
			 * + "	quadra.numeroQuadra, imovelPerfil.id, ligacaoAguaSituacao.id, " +
			 * "	ligacaoEsgotoSituacao.id, "
			 * + "	case when (clienteRelacaoTipo.id = "
			 * + ClienteRelacaoTipo.RESPONSAVEL
			 * + " and"
			 * + " 	clienteImoveis.dataFimRelacao is null and esferaPoder.id is not null)"
			 * + " 	then esferaPoder.id else 0 end,  "
			 * + "	cobrancaSituacaoTipo.id, cobrancaSituacaoMotivo.id, "
			 * + "	cobrancaSituacaoHistorico.anoMesCobrancaSituacaoInicio, "
			 * + "	cobrancaSituacaoHistorico.anoMesCobrancaSituacaoFim "
			 * +
			 * // " ) " +
			 * " from "
			 * + "	gcom.cobranca.CobrancaSituacaoHistorico cobrancaSituacaoHistorico "
			 * + "	inner join cobrancaSituacaoHistorico.cobrancaSituacaoTipo cobrancaSituacaoTipo "
			 * +
			 * "	inner join cobrancaSituacaoHistorico.cobrancaSituacaoMotivo cobrancaSituacaoMotivo "
			 * + "	inner join cobrancaSituacaoHistorico.imovel imovel "
			 * + "	inner join imovel.localidade localidade "
			 * + "	inner join localidade.gerenciaRegional gerenciaRegional "
			 * + "	inner join imovel.setorComercial setorComercial "
			 * + "	inner join imovel.quadra quadra "
			 * + "	inner join imovel.rota rota "
			 * + "	inner join imovel.ligacaoAguaSituacao ligacaoAguaSituacao "
			 * + "	inner join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao "
			 * + " 	inner join imovel.imovelPerfil imovelPerfil "
			 * + " 	inner join imovel.clienteImoveis clienteImoveis "
			 * + " 	left join clienteImoveis.clienteRelacaoTipo clienteRelacaoTipo "
			 * + " 	left join clienteImoveis.cliente cliente "
			 * + " 	left join cliente.clienteTipo clienteTipo "
			 * + " 	left join clienteTipo.esferaPoder esferaPoder "
			 * + " where "
			 * + " localidade.id = :idLocalidade and "
			 * + "	cobrancaSituacaoHistorico.anoMesCobrancaRetirada is null " +
			 * // " and clienteRelacaoTipo.id = " +
			 * // ClienteRelacaoTipo.RESPONSAVEL +
			 * // " and clienteImoveis.dataFimRelacao = is null" +
			 * "";
			 */

			// retorno = session.createQuery(hql).setInteger("idLocalidade", idLocalidade).list();

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
	 * Método que insere o ResumoSituacaoEspecialCobranca em batch
	 * 
	 * @author Thiago Toscano
	 * @date 15/05/2006
	 * @param listResumoFaturamentoSituacaoEspecialHelper
	 * @throws ErroRepositorioException
	 */
	public void inserirResumoSituacaoEspecialCobranca(List<ResumoCobrancaSituacaoEspecial> list) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		if(list != null && !list.isEmpty()){
			Iterator it = list.iterator();
			int i = 1;
			try{
				while(it.hasNext()){

					Object obj = it.next();

					session.save(obj);

					if(i % 50 == 0 || !it.hasNext()){ // 20, same as the
						// JDBC batch size // flush a batch of inserts and release
						// memory:
						session.flush();
						session.clear();
					}
					i++;

				}
			}finally{
				HibernateUtil.closeSession(session);
			}
		}
	}

	/**
	 * Método que exclui todos os ResumoFaturamentoSituacaoEspecial
	 * [CU0346] - Gerar Resumo de Situacao Especial de Cobrança
	 * 
	 * @author Thiago Toscano
	 * @date 15/05/2006
	 * @throws ErroRepositorioException
	 */
	public void excluirTodosResumoCobrancaSituacaoEspecial(Integer idLocalidade) throws ErroRepositorioException{

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{

			session
							.createQuery(
											"delete gcom.cobranca.ResumoCobrancaSituacaoEspecial resumoCobrancaSituacao where resumoCobrancaSituacao.localidade.id = :idLocalidade")
							.setInteger("idLocalidade", idLocalidade).executeUpdate();

			// session.createQuery("delete gcom.cobranca.ResumoCobrancaSituacaoEspecial ")
			// .executeUpdate();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Este caso de uso gera o resumo da pendência
	 * [UC0335] Gerar Resumo da Pendência
	 * Gera a lista de conta da pendência das Contas
	 * gerarResumoPendenciaContas
	 * 
	 * @author Roberta Costa
	 * @date 15/05/2006
	 * @param sistemaParametro
	 * @return retorno
	 * @throws ErroRepositorioException
	 */
	public List getResumoPendenciaContas(SistemaParametro sistemaParametro, Integer idLocalidade, Integer idSetorComercial)
					throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{
			/*
			 * String hql = "select conta.id from Conta as conta " + "where
			 * conta.id not in (select clienteConta.conta.id from ClienteConta
			 * clienteConta) order by conta.id)";
			 */

			// 1. O sistema seleciona as contas pendentes (a partir da tabela
			// CONTA com
			// CNTA_AMREFERENCIAFATURAMENTO da tabela SISTEMA_PARAMETROS) e
			// DCST_IDATUAL com o valor
			// correspondente a normal ou incluída ou retificada) e acumula a
			// quantidade de ligações,
			// a quantidade de documentos e o valor pendente, agrupando por
			// gerência regional, localidade,
			// setor comercial, código do setor comercial, rota, quadra, número
			// da quadra, perfil do imóvel,
			// situação da ligação de água, situação da ligação de esgoto,
			// principal categorai do imóvel,
			// esfera de poder do cliente responsável, indicador de existência
			// de hidromêtro,
			// tipo do documento, referência do documento, tipo de financiamento
			// e indicador de vencido
			// para inserção no Resumo da Pendência(tabela RESUMO_PENDENCIA)
			String hql = " select " + "	distinct (imovel.id), conta.id, gerenciaRegional.id,  "
							+ "	localidade.id, setorComercial.id,  setorComercial.codigo, "
							+ "	rota.id, quadra.id, quadra.numeroQuadra, imovelPerfil.id, "
							+ "	ligacaoAguaSituacao.id, ligacaoEsgotoSituacao.id, " + "	case when (clienteRelacaoTipo.id = "
							+ ClienteRelacaoTipo.RESPONSAVEL
							+ " and"
							+ " 	(clienteImoveis.dataFimRelacao is null))"
							+ " 	then esferaPoder.id else null end,  "
							+ "	documentoTipo.id  "
							+ " from "
							+ "	ClienteConta clienteConta "
							+ "	inner join clienteConta.conta conta "
							+ "	inner join clienteConta.cliente cliente "
							+ "	inner join conta.imovel imovel "
							+ "	inner join imovel.localidade localidade "
							+ "	inner join localidade.gerenciaRegional gerenciaRegional "
							+ "	inner join imovel.setorComercial setorComercial "
							+ "	inner join imovel.quadra quadra "
							+ "	inner join imovel.rota rota "
							+ "	left join imovel.ligacaoAguaSituacao ligacaoAguaSituacao "
							+ "	left join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao "
							+ "	left join conta.documentoTipo documentoTipo "
							+ " 	left join imovel.imovelPerfil imovelPerfil "
							+ " 	left join imovel.clienteImoveis clienteImoveis "
							+ " 	left join clienteImoveis.clienteRelacaoTipo clienteRelacaoTipo "
							+ " 	left join cliente.clienteTipo clienteTipo "
							+ " 	left join clienteTipo.esferaPoder esferaPoder "
							+ " where "
							+ "   (conta.referencia < "
							+ sistemaParametro.getAnoMesFaturamento()
							+ ") and "
							+ "	( conta.debitoCreditoSituacaoAtual.id = "
							+ DebitoCreditoSituacao.NORMAL
							+ " or "
							+ "     conta.debitoCreditoSituacaoAtual.id = "
							+ DebitoCreditoSituacao.INCLUIDA
							+ " or "
							+ "     conta.debitoCreditoSituacaoAtual.id = "
							+ DebitoCreditoSituacao.RETIFICADA
							+ "   ) "
							+ " and (localidade.id = :idLocalidade )" + " and (setorComercial.id = :idSetorComercial)" +
							// " and localidade.id in(62,951,953)" +
							"";

			retorno = session.createQuery(hql).setInteger("idLocalidade", idLocalidade).setInteger("idSetorComercial", idSetorComercial)
							.list();// .setFirstResult(inicioPesquisa).setMaxResults(1000)

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
	 * Este caso de uso gera o resumo da pendência
	 * [UC0335] Gerar Resumo da Pendência
	 * Gera a lista de guias de pagamento da pendência das Contas
	 * getResumoPendenciaGuiasPagamento
	 * 
	 * @author Roberta Costa
	 * @date 17/05/2006
	 * @param sistemaParametro
	 * @return retorno
	 * @throws ErroRepositorioException
	 */
	public List getResumoPendenciaGuiasPagamento(SistemaParametro sistemaParametro, Integer idLocalidade, Integer idSetorComercial)
					throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{
			// 1. O sistema seleciona as guias de pagamento pendentes (a partir
			// da tabela GUIA_PAGAMENTO com
			// GPAG_AMREFERENCIACONTABIL <= ( PARM_AMREFERENCIAFATURAMENTO da
			// tabela SISTEMA_PARAMETROS) e
			// DCST_IDATUAL com o valor correspondente a normal) e acumula a
			// quantidade de ligações,
			// a quantidade de economias e o valor pendente, agrupando por
			// gerência regional, localidade,
			// setor comercial, código do setor comercial, rota, quadra, número
			// da quadra, perfil do imóvel,
			// situação da ligação de água, situação da ligação de esgoto,
			// principal categorai do imóvel,
			// esfera de poder do cliente responsável, indicador de existência
			// de hidromêtro,
			// tipo do documento, referência do documento, tipo de financiamento
			// e indicador de vencido
			// para inserção no Resumo da Pendência(tabela RESUMO_PENDENCIA)
			String hql = " select " + "	distinct (imovel.id), guiaPagamento.id, gerenciaRegional.id, localidade.id, "
							+ "	setorComercial.id, setorComercial.codigo, rota.id, quadra.id,  "
							+ "	quadra.numeroQuadra, imovelPerfil.id, ligacaoAguaSituacao.id, " + "	ligacaoEsgotoSituacao.id, "
							+ "	case when (clienteRelacaoTipo.id = "
							+ ClienteRelacaoTipo.RESPONSAVEL
							+ " and"
							+ " 	(clienteImoveis.dataFimRelacao is null))"
							+ " 	then esferaPoder.id else 0 end,  "
							+ "   imovel.id, documentoTipo.id, financiamentoTipo.id "
							+ " from "
							+ "	GuiaPagamento guiaPagamento"
							+ "	inner join guiaPagamento.cliente cliente "
							+ "	inner join guiaPagamento.imovel imovel "
							+ "	inner join imovel.localidade localidade "
							+ "	inner join localidade.gerenciaRegional gerenciaRegional "
							+ "	inner join imovel.setorComercial setorComercial "
							+ "	inner join imovel.quadra quadra "
							+ "	inner join imovel.rota rota "
							+ "	inner join imovel.ligacaoAguaSituacao ligacaoAguaSituacao "
							+ "	inner join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao "
							+ "	inner join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao "
							+ "   inner join guiaPagamento.documentoTipo documentoTipo "
							+ "   inner join guiaPagamento.financiamentoTipo financiamentoTipo "
							+ " 	left join imovel.imovelPerfil imovelPerfil "
							+ " 	left join imovel.clienteImoveis clienteImoveis "
							+ " 	left join clienteImoveis.clienteRelacaoTipo clienteRelacaoTipo "
							+ " 	left join cliente.clienteTipo clienteTipo "
							+ " 	left join clienteTipo.esferaPoder esferaPoder "
							+ " where "
							+ "   guiaPagamento.anoMesReferenciaContabil <= "
							+ sistemaParametro.getAnoMesFaturamento()
							+ " and "
							+ "	guiaPagamento.debitoCreditoSituacaoAtual.id = "
							+ DebitoCreditoSituacao.NORMAL
							+ "   and (localidade.id = :idLocalidade)" + " 	and (setorComercial.id = :idSetorComercial)" + "";
			retorno = session.createQuery(hql).setInteger("idLocalidade", idLocalidade).setInteger("idSetorComercial", idSetorComercial)
							.list();

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
	 * Este caso de uso permite consultar o resumo da pendência, com a opção de
	 * impressão da consulta. Dependendo da opção de totalização sempre é gerado
	 * o relatório, sem a feração da consulta.
	 * [UC0338] Consultar Resumo da Pendência
	 * Gera a lista de pendências das Contas e Guias de Pagamento
	 * consultarResumoPendencia
	 * 
	 * @author Roberta Costa
	 * @date 24/05/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List consultarResumoPendencia(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ErroRepositorioException{

		// Cria a coleção de retorno
		List retorno = null;

		// Obtém a sessão
		Session session = HibernateUtil.getSession();

		// A query abaixo realiza uma consulta a tabela de ResumoPendencia
		try{
			GeradorSqlRelatorio geradorSqlRelatorio = new GeradorSqlRelatorio(GeradorSqlRelatorio.PENDENCIA,
							informarDadosGeracaoRelatorioConsultaHelper);

			String sql = geradorSqlRelatorio.sqlNivelUmPendencia();

			// Faz a pesquisa
			retorno = session.createSQLQuery(sql).addScalar("estado", Hibernate.STRING).addScalar("tipoCategoria", Hibernate.STRING)
							.addScalar("Categoria", Hibernate.STRING).addScalar("tipoSituacaoAguaEsgoto", Hibernate.STRING).addScalar(
											"anoMesReferencia", Hibernate.INTEGER).addScalar("somatorioLigacoes", Hibernate.INTEGER)
							.addScalar("somatorioDocumentos", Hibernate.INTEGER).addScalar("somatorioDebitos", Hibernate.BIG_DECIMAL)
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

	/**
	 * Este caso de uso permite consultar o resumo da pendência, com a opção de
	 * impressão da consulta. Dependendo da opção de totalização sempre é gerado
	 * o relatório, sem a feração da consulta.
	 * [UC0338] Consultar Resumo da Pendência
	 * Gera a lista de pendências das Contas e Guias de Pagamento
	 * consultarResumoPendencia
	 * 
	 * @author Roberta Costa
	 * @date 24/05/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ErroRepositorioException
	 */
	public String criarCondicionaisResumoPendencia(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper){

		String sql = " ";

		// A partir daqui sera montanda a parte dos condicionais da query
		// estas condicionais serão usadas se necessarias, o q determina seus
		// usos
		// são os parametros que veem carregados no objeto
		// InformarDadosGeracaoRelatorioConsultaHelper
		// que é recebido do caso de uso [UC0304] Informar Dados para Geração de
		// Relatorio ou COnsulta
		if(informarDadosGeracaoRelatorioConsultaHelper != null){
			sql = sql + " where" + " ( documentoTipo.id = " + DocumentoTipo.CONTA + "   or documentoTipo.id = "
							+ DocumentoTipo.GUIA_PAGAMENTO + " ) and ";

			// Inicio Parametros simples
			if(informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia().toString().equals("")){
				sql = sql + " resumoPendencia.anoMesReferencia = " + informarDadosGeracaoRelatorioConsultaHelper.getAnoMesReferencia()
								+ " and ";
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getGerenciaRegional() != null
							&& informarDadosGeracaoRelatorioConsultaHelper.getGerenciaRegional().getId() != null){
				sql = sql + " gerenciaRegional.id = " + informarDadosGeracaoRelatorioConsultaHelper.getGerenciaRegional().getId() + " and ";
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getEloPolo() != null
							&& informarDadosGeracaoRelatorioConsultaHelper.getEloPolo().getId() != null){
				sql = sql + " localidade.localidade.id = " + informarDadosGeracaoRelatorioConsultaHelper.getEloPolo().getId() + " and ";
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getLocalidade() != null
							&& informarDadosGeracaoRelatorioConsultaHelper.getLocalidade().getId() != null){
				sql = sql + " localidade.id = " + informarDadosGeracaoRelatorioConsultaHelper.getLocalidade().getId() + " and ";
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getSetorComercial() != null
							&& informarDadosGeracaoRelatorioConsultaHelper.getSetorComercial().getId() != null){
				sql = sql + " setorComercial.id = " + informarDadosGeracaoRelatorioConsultaHelper.getSetorComercial().getId() + " and ";
			}

			if(informarDadosGeracaoRelatorioConsultaHelper.getQuadra() != null
							&& informarDadosGeracaoRelatorioConsultaHelper.getQuadra().getId() != null){
				sql = sql + " quadra.id = " + informarDadosGeracaoRelatorioConsultaHelper.getQuadra().getId() + " and ";
			}

			// Inicio de parametros por colecão
			// Sera lida a colecao e montado um IN() a partir dos id extraidos
			// de cada objeto da colecao.

			// [FS0002] Verificar retorno de perfis de imóvel
			if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoImovelPerfil() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoImovelPerfil().isEmpty()){

				Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoImovelPerfil().iterator();
				ImovelPerfil imovelPerfil = null;

				sql = sql + " imovelPerfil.id in (";
				while(iterator.hasNext()){
					imovelPerfil = (ImovelPerfil) iterator.next();
					sql = sql + imovelPerfil.getId();
					if(!iterator.hasNext()){
						sql = sql + ",";
					}
				}
				sql = sql + ") and ";
			}

			// [FS0003] Verificar retorno de situações de ligação de água
			if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoAguaSituacao() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoAguaSituacao().isEmpty()){

				Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoAguaSituacao().iterator();
				LigacaoAguaSituacao ligacaoAguaSituacao = null;

				sql = sql + " ligacaoAguaSituacao.id in (";
				while(iterator.hasNext()){
					ligacaoAguaSituacao = (LigacaoAguaSituacao) iterator.next();
					sql = sql + ligacaoAguaSituacao.getId();
					if(!iterator.hasNext()){
						sql = sql + ",";
					}
				}
				sql = sql + ") and ";
			}

			// [FS0004] Verificar retorno de situações de ligação de esgoto
			if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoEsgotoSituacao() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoEsgotoSituacao().isEmpty()){

				Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoLigacaoEsgotoSituacao().iterator();
				LigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;

				sql = sql + " ligacaoEsgotoSituacao.id in (";
				while(iterator.hasNext()){
					ligacaoEsgotoSituacao = (LigacaoEsgotoSituacao) iterator.next();
					sql = sql + ligacaoEsgotoSituacao.getId();
					if(!iterator.hasNext()){
						sql = sql + ",";
					}
				}
				sql = sql + ") and ";
			}

			// [FS0005] Verificar retorno de categorias
			if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoCategoria() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoCategoria().isEmpty()){

				Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoCategoria().iterator();
				Categoria categoria = null;

				sql = sql + " categoria.id in (";
				while(iterator.hasNext()){
					categoria = (Categoria) iterator.next();
					sql = sql + categoria.getId();
					if(!iterator.hasNext()){
						sql = sql + ",";
					}
				}
				sql = sql + ") and ";
			}

			// [FS0006] Verificar retorno de esfera de poder
			if(informarDadosGeracaoRelatorioConsultaHelper.getColecaoEsferaPoder() != null
							&& !informarDadosGeracaoRelatorioConsultaHelper.getColecaoEsferaPoder().isEmpty()){

				Iterator iterator = informarDadosGeracaoRelatorioConsultaHelper.getColecaoEsferaPoder().iterator();
				EsferaPoder esferaPoder = null;

				sql = sql + " esferaPoder.id in (";
				while(iterator.hasNext()){
					esferaPoder = (EsferaPoder) iterator.next();
					sql = sql + esferaPoder.getId();
					if(!iterator.hasNext()){
						sql = sql + ",";
					}
				}
				sql = sql + ") and ";
			}
		}

		// retira o " and " q fica sobrando no final da query
		sql = Util.formatarHQL(sql, 4);

		// Abaixo codigo para ordenacao da consulta
		sql = sql + " order by " + "	gerenciaRegional.nomeAbreviado, " + "	localidade.localidade.descricao, "
						+ "	setorComercial.descricao, " + "	quadra.numeroQuadra, " + "	imovelPerfil.descricao, "
						+ "	esferaPoder.descricao, " + "   categoriaTipo.descricao," + "	categoria.descricao, "
						+ "	ligacaoAguaSituacao.descricao, " + "	ligacaoEsgotoSituacao.descricao, " + "	resumoPendencia.anoMesReferencia, "
						+ "	resumoPendencia.indicadorVencido " + "";

		return sql;
	}

	/**
	 * Este caso de uso permite consultar o resumo da pendência, com a opção de
	 * impressão da consulta. Dependendo da opção de totalização sempre é gerado
	 * o relatório, sem a feração da consulta.
	 * [UC0338] Consultar Resumo da Pendência
	 * Verifica se existe registros para o ano/mês refrência
	 * verificarExistenciaAnoMesReferenciaResumo
	 * 
	 * @author Roberta Costa
	 * @date 24/05/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaAnoMesReferenciaResumo(Integer anoMesReferencia, String resumo) throws ErroRepositorioException{

		// Obtém a sessão
		Session session = HibernateUtil.getSession();

		Integer retorno = null;

		// A query abaixo realiza uma consulta a tabela de ResumoPendencia
		try{
			String sql =
			// "select count(*) from " + objetoResumo + "as resumo " +
			"select count(*) from " + resumo + " resumo " + " where resumo.anoMesReferencia = " + anoMesReferencia + "";
			// Faz a pesquisa
			retorno = ((Number) session.createQuery(sql).setMaxResults(1).list().iterator().next()).intValue();

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

	public Collection pesquisarResumoCobrancaSituacaoEspecialConsultaMotivoHelper(Integer idGerencia, Integer idLocalidade, Integer idTipo,
					Integer[] idSituacaoTipo, Integer[] idSituacaoMotivo) throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;

		// obtém a sessão
		Session session = HibernateUtil.getSession();
		boolean flagIdSituacaoTipo = false;
		boolean flagIdSituacaoMotivo = false;

		try{
			StringBuilder builder = new StringBuilder("select new ");
			builder.append(ResumoCobrancaSituacaoEspecialConsultaMotivoHelper.class.getName());
			builder.append(" ( ");
			builder.append("  cobrancaMotivo.id, ");
			builder.append("  cobrancaMotivo.descricao, ");
			builder.append("  MIN(rfse.anoMesInicioSituacaoEspecial), ");
			builder.append("  MAX(rfse.anoMesFinalSituacaoEspecial), ");
			builder.append("  CASE WHEN (SUM(rfse.quantidadeImovel) is null )  then 0 else SUM(rfse.quantidadeImovel) end");
			builder.append(" ) ");
			builder.append(" FROM ");
			builder.append("   ResumoCobrancaSituacaoEspecial rfse ");
			builder.append("   inner join rfse.gerenciaRegional gerenciaRegional ");
			builder.append("   inner join rfse.localidade localidade ");
			builder.append("   inner join rfse.cobrancaSituacaoTipo cobrancaTipo ");
			builder.append("   inner join rfse.cobrancaSituacaoMotivo cobrancaMotivo ");
			builder.append(" WHERE  ");
			builder.append("   rfse.gerenciaRegional.id = :idGerencia ");
			builder.append("  AND ");
			builder.append("   rfse.localidade.id = :idLocalidade ");
			builder.append("  AND ");
			builder.append("   rfse.cobrancaSituacaoTipo.id = :idTipo ");

			if(idSituacaoTipo != null || idSituacaoMotivo != null){
				if(idSituacaoTipo != null){
					builder.append(" AND cobrancaTipo.id in (:idSituacaoTipo) ");
					flagIdSituacaoTipo = true;
				}

				if(idSituacaoMotivo != null){
					builder.append(" AND cobrancaMotivo.id in (:idSituacaoMotivo) ");
					flagIdSituacaoMotivo = true;
				}
			}
			builder.append("  group by  ");
			builder.append("   cobrancaMotivo.id, cobrancaMotivo.descricao");
			builder.append("  order by ");
			builder.append("   cobrancaMotivo.id ");

			Query createQuery = session.createQuery(builder.toString());
			createQuery.setInteger("idGerencia", idGerencia);
			createQuery.setInteger("idLocalidade", idLocalidade);
			createQuery.setInteger("idTipo", idTipo);

			if(flagIdSituacaoTipo){
				createQuery.setParameterList("idSituacaoTipo", idSituacaoTipo);
			}
			if(flagIdSituacaoMotivo){
				createQuery.setParameterList("idSituacaoMotivo", idSituacaoMotivo);
			}

			retorno = createQuery.list();

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

	public Collection pesquisarResumoCobrancaSituacaoEspecialConsultaSitTipoHelper(Integer idGerencia, Integer idLocalidade,
					Integer[] idSituacaoTipo, Integer[] idSituacaoMotivo) throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;
		boolean flagIdSituacaoTipo = false;
		boolean flagIdSituacaoMotivo = false;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{

			StringBuilder builder = new StringBuilder("select  new ");
			builder.append(ResumoCobrancaSituacaoEspecialConsultaSitTipoHelper.class.getName());
			builder.append(" ( ");
			builder.append("  cobrancaTipo.id, ");
			builder.append("  cobrancaTipo.descricao, ");
			builder.append("  CASE WHEN (SUM(rfse.quantidadeImovel) is null )  then 0 else SUM(rfse.quantidadeImovel) end");
			builder.append("  ) ");
			builder.append(" FROM  ");
			builder.append("  ResumoCobrancaSituacaoEspecial rfse");
			builder.append("  inner join rfse.gerenciaRegional gerenciaRegional  ");
			builder.append("  inner join rfse.localidade localidade  ");
			builder.append("  inner join rfse.cobrancaSituacaoTipo cobrancaTipo  ");
			builder.append("  inner join rfse.cobrancaSituacaoMotivo cobrancaMotivo ");
			builder.append(" WHERE ");
			builder.append(" rfse.gerenciaRegional.id = :idGerencia ");
			builder.append(" AND ");
			builder.append(" rfse.localidade.id = :idLocalidade ");

			if(idSituacaoTipo != null || idSituacaoMotivo != null){
				if(idSituacaoTipo != null){
					builder.append(" AND cobrancaTipo.id in (:idSituacaoTipo) ");
					flagIdSituacaoTipo = true;
				}

				if(idSituacaoMotivo != null){
					builder.append(" AND cobrancaMotivo.id in (:idSituacaoMotivo) ");
					flagIdSituacaoMotivo = true;
				}
			}
			builder.append(" group by ");
			builder.append("  cobrancaTipo.id, cobrancaTipo.descricao ");
			builder.append(" order by cobrancaTipo.id ");

			Query createQuery = session.createQuery(builder.toString());
			createQuery.setInteger("idGerencia", idGerencia);
			createQuery.setInteger("idLocalidade", idLocalidade);

			if(flagIdSituacaoTipo){
				createQuery.setParameterList("idSituacaoTipo", idSituacaoTipo);
			}
			if(flagIdSituacaoMotivo){
				createQuery.setParameterList("idSituacaoMotivo", idSituacaoMotivo);
			}

			retorno = createQuery.list();

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

	public Collection<ResumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper> pesquisarResumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper(
					Integer idGerencia, Integer[] idSituacaoTipo, Integer[] idSituacaoMotivo) throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;

		// obtém a sessão
		Session session = HibernateUtil.getSession();
		boolean flagIdSituacaoTipo = false;
		boolean flagIdSituacaoMotivo = false;

		try{
			StringBuilder builder = new StringBuilder(" SELECT new ");
			builder.append(ResumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper.class.getName());
			builder.append(" ( ");
			builder.append("  unidadeNegocio.id, ");
			builder.append("  unidadeNegocio.nome, ");
			builder.append("  CASE WHEN ( SUM(rfse.quantidadeImovel) is null ) THEN 0 else SUM(rfse.quantidadeImovel) END ");
			builder.append(" ) ");
			builder.append(" FROM ");
			builder.append(" ResumoCobrancaSituacaoEspecial rfse ");
			builder.append("  inner join rfse.gerenciaRegional gerenciaRegional ");
			builder.append("  inner join rfse.localidade localidade ");
			builder.append("  inner join rfse.cobrancaSituacaoTipo cobrancaTipo ");
			builder.append("  inner join rfse.cobrancaSituacaoMotivo cobrancaMotivo ");
			builder.append("  inner join localidade.unidadeNegocio unidadeNegocio ");
			builder.append(" WHERE");
			builder.append("  rfse.gerenciaRegional.id = :idGerencia ");

			if(idSituacaoTipo != null || idSituacaoMotivo != null){
				if(idSituacaoTipo != null){
					builder.append(" AND cobrancaTipo.id in (:idSituacaoTipo) ");
					flagIdSituacaoTipo = true;
				}

				if(idSituacaoMotivo != null){
					builder.append(" AND cobrancaMotivo.id in (:idSituacaoMotivo) ");
					flagIdSituacaoMotivo = true;
				}
			}

			builder.append(" group by");
			builder.append(" unidadeNegocio.id, unidadeNegocio.nome ");
			builder.append(" order by unidadeNegocio.id");

			Query createQuery = session.createQuery(builder.toString());
			createQuery.setInteger("idGerencia", idGerencia);

			if(flagIdSituacaoTipo){
				createQuery.setParameterList("idSituacaoTipo", idSituacaoTipo);
			}
			if(flagIdSituacaoMotivo){
				createQuery.setParameterList("idSituacaoMotivo", idSituacaoMotivo);
			}

			retorno = createQuery.list();

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

	public Collection pesquisarResumoCobrancaSituacaoEspecialConsultaLocalidadeHelper(Integer idUnidade, Integer[] idSituacaoTipo,
					Integer[] idSituacaoMotivo) throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;

		// obtém a sessão
		Session session = HibernateUtil.getSession();
		boolean flagIdSituacaoTipo = false;
		boolean flagIdSituacaoMotivo = false;

		try{
			StringBuilder builder = new StringBuilder(" SELECT new ");
			builder.append(ResumoCobrancaSituacaoEspecialConsultaLocalidadeHelper.class.getName());
			builder.append(" ( ");
			builder.append("  localidade.id, ");
			builder.append("  localidade.descricao, ");
			builder.append("  CASE WHEN ( SUM(rfse.quantidadeImovel) is null ) THEN 0 else SUM(rfse.quantidadeImovel) END ");
			builder.append(" ) ");
			builder.append(" FROM ");
			builder.append(" ResumoCobrancaSituacaoEspecial rfse ");
			builder.append("  inner join rfse.gerenciaRegional gerenciaRegional ");
			builder.append("  inner join rfse.localidade localidade ");
			builder.append("  inner join rfse.cobrancaSituacaoTipo cobrancaTipo ");
			builder.append("  inner join rfse.cobrancaSituacaoMotivo cobrancaMotivo ");
			builder.append(" WHERE");
			builder.append("  rfse.localidade.unidadeNegocio.id = :idUnidade ");

			if(idSituacaoTipo != null || idSituacaoMotivo != null){
				if(idSituacaoTipo != null){
					builder.append(" AND cobrancaTipo.id in (:idSituacaoTipo) ");
					flagIdSituacaoTipo = true;
				}

				if(idSituacaoMotivo != null){
					builder.append(" AND cobrancaMotivo.id in (:idSituacaoMotivo) ");
					flagIdSituacaoMotivo = true;
				}
			}

			builder.append(" group by");
			builder.append(" localidade.id, localidade.descricao ");
			builder.append(" order by localidade.id");

			Query createQuery = session.createQuery(builder.toString());
			createQuery.setInteger("idUnidade", idUnidade);

			if(flagIdSituacaoTipo){
				createQuery.setParameterList("idSituacaoTipo", idSituacaoTipo);
			}
			if(flagIdSituacaoMotivo){
				createQuery.setParameterList("idSituacaoMotivo", idSituacaoMotivo);
			}

			retorno = createQuery.list();

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

	public Collection pesquisarResumoCobrancaSituacaoEspecialConsultaGerenciaRegionalHelper(Integer[] idSituacaoTipo,
					Integer[] idSituacaoMotivo) throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{

			boolean flagIdSituacaoTipo = false;
			boolean flagIdSituacaoMotivo = false;

			StringBuilder builder = new StringBuilder("select new ");
			builder.append(ResumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper.class.getName());
			builder.append(" ( gerenciaRegional.id,");
			builder.append("  gerenciaRegional.nomeAbreviado, ");
			builder.append("  gerenciaRegional.nome, ");
			builder.append("  CASE WHEN ( SUM(rfse.quantidadeImovel) is null ) THEN 0 else SUM(rfse.quantidadeImovel) END ");
			builder.append("  )"); //
			builder.append(" from  ResumoCobrancaSituacaoEspecial rfse");
			builder.append("	 inner join rfse.gerenciaRegional gerenciaRegional "); //
			builder.append("	 inner join rfse.localidade localidade ");
			builder.append("	 inner join rfse.cobrancaSituacaoTipo cobrancaTipo ");
			builder.append("	 inner join rfse.cobrancaSituacaoMotivo cobrancaMotivo "); //

			if(idSituacaoTipo != null || idSituacaoMotivo != null){
				builder.append(" where "); //
				if(idSituacaoTipo != null){
					builder.append("  cobrancaTipo.id in (:idSituacaoTipo) ");
					flagIdSituacaoTipo = true;
				}

				if(idSituacaoMotivo != null){
					if(flagIdSituacaoTipo){
						builder.append(" AND ");
					}
					builder.append(" cobrancaMotivo.id in (:idSituacaoMotivo) ");
					flagIdSituacaoMotivo = true;
				}
			}

			builder.append(" group by");
			builder.append("  gerenciaRegional.id, gerenciaRegional.nomeAbreviado, gerenciaRegional.nome" + "");
			builder.append(" order by gerenciaRegional.id");

			Query createQuery = session.createQuery(builder.toString());

			if(flagIdSituacaoTipo){
				createQuery.setParameterList("idSituacaoTipo", idSituacaoTipo);
			}
			if(flagIdSituacaoMotivo){
				createQuery.setParameterList("idSituacaoMotivo", idSituacaoMotivo);
			}

			retorno = createQuery.list();

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

	public Collection pesquisarResumoCobrancaSituacaoEspecialConsultaFatEstimadoHelper(Integer anoMesReferencia, Integer localidade)
					throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{
			// pesquisa a coleção de atividades e atribui a variável "retorno"
			String hql = " select " + " new " + ResumoCobrancaSituacaoEspecialConsultaFatEstimadoHelper.class.getName() + " ( "
							+ "	SUM(rfsi.valorAgua) ,  SUM(rfsi.valorEsgoto) , SUM(rfsi.valorDebitos) , SUM(rfsi.valorCreditos) " + " ) "
							+ " from " + "	ResumoFaturamentoSimulacao rfsi" + "	inner join rfsi.localidade localidade" + " where "
							+ "	rfsi.anoMesReferencia = :anoMesReferencia AND" + "   rfsi.localidade.id = :localidade " + "";

			retorno = session.createQuery(hql).setInteger("anoMesReferencia", anoMesReferencia).setInteger("localidade", localidade).list();

			if(retorno == null){
				retorno = new ArrayList();
				retorno.add(new ResumoCobrancaSituacaoEspecialConsultaFatEstimadoHelper(new BigDecimal("0.00"), new BigDecimal("0.00"),
								new BigDecimal("0.00"), new BigDecimal("0.00")));
			}

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

	public Collection pesquisarResumoCobrancaSituacaoEspecialConsultaQtLigacoesHelper(Integer anoMesReferencia, Integer localidade)
					throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{// Comentado por Eduardo Henrique para execução do relatório
			// pesquisa a coleção de atividades e atribui a variável "retorno"
			String hql = " select " //
							+ " new " + ResumoCobrancaSituacaoEspecialConsultaQtLigacoesHelper.class.getName()
							+ " ( "
							+ "	case when (SUM(rle.quantidadeLigacoes) is null) then 0 else SUM(rle.quantidadeLigacoes) end" //
							+ " ) " //
							+ " from ResumoLigacoesEconomia rle" //
							+ "	inner join rle.localidade localidade" //
							+ " where " //
							+ " rle.anoMesReferencia = :anoMesReferencia AND" //
							+ " localidade.id = :localidade ";

			retorno = session.createQuery(hql).setInteger("anoMesReferencia", anoMesReferencia).setInteger("localidade", localidade).list();

			if(retorno == null){
				retorno = new ArrayList<ResumoCobrancaSituacaoEspecialConsultaQtLigacoesHelper>();
				retorno.add(new ResumoCobrancaSituacaoEspecialConsultaQtLigacoesHelper(new Integer("0")));
			}

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

	public String criarCondicionaisResumosHQL(InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper){

		String sql = " ";
		/*
		 * A partir daqui sera montanda a parte dos condicionais da query estas
		 * condicionais serão usadas se necessarias, o q determina seus usos são
		 * os parametros que veem carregados no objeto
		 * InformarDadosGeracaoRelatorioConsultaHelper que é recebido do caso de
		 * uso [UC0304] Informar Dados para Geração de Relatorio ou COnsulta
		 */
		if(informarDadosGeracaoResumoAcaoConsultaHelper != null){

			// Inicio Parametros simples
			if(informarDadosGeracaoResumoAcaoConsultaHelper.getAnoMesReferencia() != null
							&& !informarDadosGeracaoResumoAcaoConsultaHelper.getAnoMesReferencia().toString().equals("")){
				sql = sql + " and re.anoMesReferencia = " + informarDadosGeracaoResumoAcaoConsultaHelper.getAnoMesReferencia();
			}

			if(informarDadosGeracaoResumoAcaoConsultaHelper.getEloPolo() != null
							&& informarDadosGeracaoResumoAcaoConsultaHelper.getEloPolo().getId() != null){
				sql = sql + " and re.localidade.localidade.id = " + informarDadosGeracaoResumoAcaoConsultaHelper.getEloPolo().getId();
			}

			if(informarDadosGeracaoResumoAcaoConsultaHelper.getLocalidade() != null
							&& informarDadosGeracaoResumoAcaoConsultaHelper.getLocalidade().getId() != null){
				sql = sql + " and re.localidade.id = " + informarDadosGeracaoResumoAcaoConsultaHelper.getLocalidade().getId();
			}

			if(informarDadosGeracaoResumoAcaoConsultaHelper.getUnidadeNegocio() != null
							&& informarDadosGeracaoResumoAcaoConsultaHelper.getUnidadeNegocio().getId() != null){
				sql = sql + " and re.UnidadeNegocio.id = " + informarDadosGeracaoResumoAcaoConsultaHelper.getUnidadeNegocio().getId();
			}

			if(informarDadosGeracaoResumoAcaoConsultaHelper.getSetorComercial() != null
							&& informarDadosGeracaoResumoAcaoConsultaHelper.getSetorComercial().getId() != null){
				sql = sql + " and re.setorComercial.id = " + informarDadosGeracaoResumoAcaoConsultaHelper.getSetorComercial().getId();
			}

			if(informarDadosGeracaoResumoAcaoConsultaHelper.getQuadra() != null
							&& informarDadosGeracaoResumoAcaoConsultaHelper.getQuadra().getId() != null){
				sql = sql + " and re.quadra.id = " + informarDadosGeracaoResumoAcaoConsultaHelper.getQuadra().getId();
			}

			// Inicio de parametros por colecão
			// sera lida a colecao e montado um IN() a partis dos id extraidos
			// de cada objeto da colecao.

			if(informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoCobrancaGrupo() != null
							&& !informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoCobrancaGrupo().isEmpty()){

				Iterator iterator = informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoCobrancaGrupo().iterator();
				CobrancaGrupo cobrancaGrupo = null;

				sql = sql + " and re.cobrancaGrupo.id in (";
				while(iterator.hasNext()){
					cobrancaGrupo = (CobrancaGrupo) iterator.next();
					sql = sql + cobrancaGrupo.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}

			if(informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoGerenciaRegional() != null
							&& !informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoGerenciaRegional().isEmpty()){

				Iterator iterator = informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoGerenciaRegional().iterator();
				GerenciaRegional gerenciaRegional = null;

				sql = sql + " and re.gerenciaRegional.id in (";
				while(iterator.hasNext()){
					gerenciaRegional = (GerenciaRegional) iterator.next();
					sql = sql + gerenciaRegional.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}
			if(informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoImovelPerfil() != null
							&& !informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoImovelPerfil().isEmpty()){

				Iterator iterator = informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoImovelPerfil().iterator();
				ImovelPerfil imovelPerfil = null;

				sql = sql + " and re.imovelPerfil.id in (";
				while(iterator.hasNext()){
					imovelPerfil = (ImovelPerfil) iterator.next();
					sql = sql + imovelPerfil.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}

			if(informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoLigacaoAguaSituacao() != null
							&& !informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoLigacaoAguaSituacao().isEmpty()){

				Iterator iterator = informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoLigacaoAguaSituacao().iterator();
				LigacaoAguaSituacao ligacaoAguaSituacao = null;

				sql = sql + " and re.ligacaoAguaSituacao.id in (";
				while(iterator.hasNext()){
					ligacaoAguaSituacao = (LigacaoAguaSituacao) iterator.next();
					sql = sql + ligacaoAguaSituacao.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}

			if(informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoLigacaoEsgotoSituacao() != null
							&& !informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoLigacaoEsgotoSituacao().isEmpty()){

				Iterator iterator = informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoLigacaoEsgotoSituacao().iterator();
				LigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;

				sql = sql + " and re.ligacaoEsgotoSituacao.id in (";
				while(iterator.hasNext()){
					ligacaoEsgotoSituacao = (LigacaoEsgotoSituacao) iterator.next();
					sql = sql + ligacaoEsgotoSituacao.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}

			if(informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoCategoria() != null
							&& !informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoCategoria().isEmpty()){

				Iterator iterator = informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoCategoria().iterator();
				Categoria categoria = null;

				sql = sql + " and re.categoria.id in (";
				while(iterator.hasNext()){
					categoria = (Categoria) iterator.next();
					sql = sql + categoria.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}

			if(informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoEsferaPoder() != null
							&& !informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoEsferaPoder().isEmpty()){

				Iterator iterator = informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoEsferaPoder().iterator();
				EsferaPoder esferaPoder = null;

				sql = sql + " and re.esferaPoder.id in (";
				while(iterator.hasNext()){
					esferaPoder = (EsferaPoder) iterator.next();
					sql = sql + esferaPoder.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}
			if(informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoEmpresa() != null
							&& !informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoEmpresa().isEmpty()){

				Iterator iterator = informarDadosGeracaoResumoAcaoConsultaHelper.getColecaoEmpresa().iterator();
				Empresa empresa = null;

				sql = sql + " and re.empresa.id in (";
				while(iterator.hasNext()){
					empresa = (Empresa) iterator.next();
					sql = sql + empresa.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}
		}

		return sql;
	}

	/**
	 * [UC0489] - Consultar Resumo das Ações de Cobrança
	 * Pesquisa as ações de cobrança
	 * 
	 * @author Ana Maria
	 * @date 06/11/2006
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcao(InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select new gcom.gerencial.bean.CobrancaAcaoHelper (cbac.id, cbac.descricaoCobrancaAcao, "
							+ " min(re.realizacaoEmitir), max(re.realizacaoEncerrar), cast(sum(re.quantidadeDocumentos) as int),sum(re.valorDocumentos))"
							+ " from ResumoCobrancaAcao re" + " inner join re.cobrancaAcao cbac" + " where 1 = 1 "
							+ this.criarCondicionaisResumosHQL(informarDadosGeracaoResumoAcaoConsultaHelper)
							+ " group by cbac.id, cbac.descricaoCobrancaAcao" + " order by cbac.id";

			retorno = session.createQuery(consulta).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0489] - Consultar Resumo das Ações de Cobrança
	 * Pesquisa as ações de cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 21/05/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Integer consultarCobrancaAcaoQuantidadeDocumentos(
					InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper, Integer idCobrancaAcao)
					throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select cast(sum(re.quantidadeDocumentos) as int) " + " from ResumoCobrancaAcao re"
							+ " inner join re.cobrancaAcao cbac"
							+ " where cbac.id = :idCobrancaAcao and re.indicadorDefinitivo = :indicadorDefinitivo "
							+ this.criarCondicionaisResumosHQL(informarDadosGeracaoResumoAcaoConsultaHelper);

			retorno = ((Number) session.createQuery(consulta).setInteger("idCobrancaAcao", idCobrancaAcao).setInteger(
							"indicadorDefinitivo", ResumoCobrancaAcao.INIDCADOR_DEFINITIVO).uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0489] - Consultar Resumo das Ações de Cobrança
	 * Pesquisa as situações das ação de cobrança
	 * 
	 * @author Ana Maria
	 * @date 06/11/2006
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoSituacao(
					InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper, Integer idCobrancaAcao)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select new gcom.gerencial.bean.CobrancaAcaoSituacaoHelper(castu.id, castu.descricao, cast(sum(re.quantidadeDocumentos) as int), sum(re.valorDocumentos))"
							+ " from ResumoCobrancaAcao re"
							+ " inner join re.cobrancaAcao cbac"
							+ " inner join re.cobrancaAcaoSituacao castu"
							+ " where  cbac.id = :idCobrancaAcao"
							+ this.criarCondicionaisResumosHQL(informarDadosGeracaoResumoAcaoConsultaHelper)
							+ " group by cbac.id, cbac.descricaoCobrancaAcao, castu.id, castu.descricao" + " order by castu.id";

			retorno = session.createQuery(consulta).setInteger("idCobrancaAcao", idCobrancaAcao).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0489] - Consultar Resumo das Ações de Cobrança
	 * Pesquisa as situações de débito da situação da ação
	 * 
	 * @author Ana Maria
	 * @date 06/11/2006
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoDebito(
					InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper, Integer idCobrancaAcao,
					Integer idCobrancaAcaoSituacao) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select new gcom.gerencial.bean.CobrancaAcaoDebitoHelper (cdst.id, cdst.descricao, cast(sum(re.quantidadeDocumentos) as int),"
							+ " sum(re.valorDocumentos))"
							+ " from ResumoCobrancaAcao re"
							+ " inner join re.cobrancaAcao cbac"
							+ " inner join re.cobrancaAcaoSituacao castu"
							+ " left join re.cobrancaDebitoSituacao cdst"
							+ " where cbac.id = :idCobrancaAcao and castu.id = :idCobrancaAcaoSituacao"
							+ this.criarCondicionaisResumosHQL(informarDadosGeracaoResumoAcaoConsultaHelper)
							+ " group by cdst.id, cdst.descricao" + " order by cdst.id, cdst.descricao";

			retorno = session.createQuery(consulta).setInteger("idCobrancaAcao", idCobrancaAcao).setInteger("idCobrancaAcaoSituacao",
							idCobrancaAcaoSituacao).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0489] - Consultar Resumo das Ações de Cobrança
	 * Pesquisa as situações de débito da situação da ação de acordo com o
	 * indicador antesApos
	 * 
	 * @author Sávio Luiz
	 * @date 06/11/2006
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoDebitoComIndicador(
					InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper, Integer idCobrancaAcao,
					Integer idCobrancaAcaoSituacao, short indicadorAntesApos, Integer idCobrancaAcaoDebito) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select new gcom.gerencial.bean.CobrancaAcaoDebitoHelper (cdst.id, cdst.descricao, cast(sum(re.quantidadeDocumentos) as int),"
							+ " sum(re.valorDocumentos), re.indicadorAntesApos)"
							+ " from ResumoCobrancaAcao re"
							+ " inner join re.cobrancaAcao cbac"
							+ " inner join re.cobrancaAcaoSituacao castu"
							+ " left join re.cobrancaDebitoSituacao cdst"
							+ " where cbac.id = :idCobrancaAcao and castu.id = :idCobrancaAcaoSituacao "

							+ " AND re.indicadorAntesApos = :indicadorAntesApos and cdst = :idCobrancaAcaoDebito "
							+ this.criarCondicionaisResumosHQL(informarDadosGeracaoResumoAcaoConsultaHelper)
							+ " group by cdst.id, cdst.descricao, re.indicadorAntesApos"
							+ " order by cdst.id, cdst.descricao, re.indicadorAntesApos";

			retorno = session.createQuery(consulta).setInteger("idCobrancaAcao", idCobrancaAcao).setInteger("idCobrancaAcaoSituacao",
							idCobrancaAcaoSituacao).setInteger("idCobrancaAcaoDebito", idCobrancaAcaoDebito).setShort("indicadorAntesApos",
							indicadorAntesApos).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0489] - Consultar Resumo das Ações de Cobrança
	 * 
	 * @author Ana Maria
	 * @date 06/11/2006
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoSituacaoPerfilImovel(int anoMesReferencia, Integer idCobrancaAcao,
					Integer idCobrancaAcaoSituacao,
					InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select new gcom.gerencial.bean.CobrancaAcaoPerfilHelper(iper.id, iper.descricao,"
							+ " cast(sum(re.quantidadeDocumentos) as int), sum(re.valorDocumentos))" + " from ResumoCobrancaAcao re"
							+ " inner join re.cobrancaAcao cbac" + " inner join re.cobrancaAcaoSituacao castu"
							+ " inner join re.imovelPerfil iper" + " where cbac.id = :idCobrancaAcao ";
			if(idCobrancaAcaoSituacao != null){
				consulta = consulta + "and castu.id = " + idCobrancaAcaoSituacao + " ";
			}

			consulta = consulta + this.criarCondicionaisResumosHQL(informarDadosGeracaoResumoAcaoConsultaHelper)
							+ " group by iper.id, iper.descricao" + " order by iper.id";

			retorno = session.createQuery(consulta).setInteger("idCobrancaAcao", idCobrancaAcao).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0489] - Consultar Resumo das Ações de Cobrança
	 * 
	 * @author Ana Maria
	 * @date 06/11/2006
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoDebitoPerfilImovel(int anoMesReferencia, Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao,
					Integer idCobrancaAcaoDebito, Short idIndicador,
					InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select new gcom.gerencial.bean.CobrancaAcaoPerfilHelper(iper.id, iper.descricao, "
							+ " cast(sum(re.quantidadeDocumentos) as int), sum(re.valorDocumentos))" + " from ResumoCobrancaAcao re"
							+ " inner join re.cobrancaAcao cbac " + " inner join re.cobrancaAcaoSituacao castu"
							+ " left join re.cobrancaDebitoSituacao cdst" + " inner join re.imovelPerfil iper"
							+ " where cbac.id = :idCobrancaAcao " + " and cdst = :idCobrancaAcaoDebito ";
			if(idIndicador != null){
				consulta += "and  re.indicadorAntesApos = " + idIndicador + " ";
			}else{
				consulta += "and  re.indicadorAntesApos is null ";
			}
			if(idCobrancaAcaoSituacao != null){
				consulta += " and castu.id = " + idCobrancaAcaoSituacao + " ";
			}
			consulta += this.criarCondicionaisResumosHQL(informarDadosGeracaoResumoAcaoConsultaHelper);
			consulta += " group by iper.id, iper.descricao" + " order by iper.id";

			retorno = session.createQuery(consulta).setInteger("idCobrancaAcao", idCobrancaAcao).setInteger("idCobrancaAcaoDebito",
							idCobrancaAcaoDebito).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0489] - Consultar Resumo das Ações de Cobrança
	 * 
	 * @author Ana Maria
	 * @date 06/11/2006
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoSituacaoPerfilImovelIndicador(int anoMesReferencia, Integer idCobrancaAcao,
					Integer idCobrancaAcaoSituacao, Integer idPerfil,
					InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select new gcom.gerencial.bean.CobrancaAcaoPerfilIndicadorHelper(iper.id, iper.descricao,"
							+ " re.indicadorLimite, cast(sum(re.quantidadeDocumentos) as int), sum(re.valorDocumentos))"
							+ " from ResumoCobrancaAcao re" + " inner join re.cobrancaAcao cbac"
							+ " inner join re.cobrancaAcaoSituacao castu" + " inner join re.imovelPerfil iper"
							+ " where cbac.id = :idCobrancaAcao " + " and iper.id = :idPerfil ";
			if(idCobrancaAcaoSituacao != null){
				consulta = consulta + " and castu.id = " + idCobrancaAcaoSituacao + " ";
			}

			consulta = consulta + this.criarCondicionaisResumosHQL(informarDadosGeracaoResumoAcaoConsultaHelper)
							+ " group by iper.id, iper.descricao, re.indicadorLimite" + " order by iper.id";

			retorno = session.createQuery(consulta).setInteger("idCobrancaAcao", idCobrancaAcao).setInteger("idPerfil", idPerfil).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0489] - Consultar Resumo das Ações de Cobrança
	 * 
	 * @author Ana Maria
	 * @date 06/11/2006
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoDebitoPerfilImovelIndicador(int anoMesReferencia, Integer idCobrancaAcao,
					Integer idCobrancaAcaoSituacao, Integer idCobrancaAcaoDebito, Integer idPerfil, Short idIndicador,
					InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsultaHelper)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select new gcom.gerencial.bean.CobrancaAcaoPerfilIndicadorHelper(iper.id, iper.descricao, "
							+ " re.indicadorLimite, cast(sum(re.quantidadeDocumentos) as int), sum(re.valorDocumentos))"
							+ " from ResumoCobrancaAcao re" + " inner join re.cobrancaAcao cbac "
							+ " inner join re.cobrancaAcaoSituacao castu" + " left join re.cobrancaDebitoSituacao cdst"
							+ " inner join re.imovelPerfil iper" + " where cbac.id = :idCobrancaAcao "
							+ " and cdst = :idCobrancaAcaoDebito " + " and iper.id = :idPerfil ";
			if(idIndicador != null){
				consulta += "and  re.indicadorAntesApos = " + idIndicador + " ";
			}else{
				consulta += "and  re.indicadorAntesApos is null ";
			}
			if(idCobrancaAcaoSituacao != null){
				consulta += "and castu.id =" + idCobrancaAcaoSituacao + " ";
			}
			consulta += this.criarCondicionaisResumosHQL(informarDadosGeracaoResumoAcaoConsultaHelper);
			consulta += " group by iper.id, iper.descricao, re.indicadorLimite" + " order by iper.id";

			retorno = session.createQuery(consulta).setInteger("idCobrancaAcao", idCobrancaAcao).setInteger("idCobrancaAcaoDebito",
							idCobrancaAcaoDebito).setInteger("idPerfil", idPerfil).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Exclui os dados Resumo de pendência por ano/mês e localidade
	 * [UC0335] Gerar Resumo da Pendência
	 * 
	 * @author Ana Maria
	 * @date 30/01/2007
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @throws ErroRepositorioException
	 */
	public void excluirResumoPendenciaPorAnoMesLocalidade(int anoMesReferencia, Integer idLocalidade) throws ErroRepositorioException{

		// Cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// Cria a variável que vai conter o hql
		String consulta;

		try{

			// Constroi o hql para remover os dados diários da arrecadação
			// referentes ao ano/mês de arrecadação atual
			consulta = "delete ResumoPendencia rpen where rpen.anoMesReferencia = :anoMesReferencia and rpen.localidade.id = :idLocalidade";

			// Executa o hql
			session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).setInteger("idLocalidade", idLocalidade)
							.executeUpdate();

			// Erro no hibernate
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0617] Consultar Resumo das Ações de Cobrança Eventuais
	 * 
	 * @author Sávio Luiz
	 * @date 25/06/2007
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaResumoEventual(
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ErroRepositorioException{

		// Obtém a sessão
		Session session = HibernateUtil.getSession();

		Integer retorno = null;

		try{

			String consulta = " select count(*)" + " from ResumoCobrancaAcaoEventual re" + " inner join re.cobrancaAcao cbac"
							+ " where 1 = 1 "
							+ this.criarCondicionaisResumosEventuaisHQL(informarDadosGeracaoResumoAcaoConsultaEventualHelper);

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){
				consulta += " and re.tempoRealizacaoEmitir BETWEEN :dataInicial and :dataFinal ";
			}

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){

				retorno = ((Number) session
								.createQuery(consulta)
								.setTimestamp("dataInicial",
												Util.formatarDataInicial(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataInicialEmissao()))
								.setTimestamp("dataFinal",
												Util.formatarDataFinal(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataFinalEmissao())).setMaxResults(1).list().iterator().next())
								.intValue();
			}else{
				retorno = ((Number) session.createQuery(consulta).setMaxResults(1).list().iterator().next()).intValue();
			}

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

	/**
	 * [UC0617] Consultar Resumo das Ações de Cobrança Eventuais
	 * 
	 * @author Sávio Luiz
	 * @date 25/06/2007
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoEventual(
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select new gcom.gerencial.bean.CobrancaAcaoHelper (cbac.id, cbac.descricaoCobrancaAcao, "
							+ " min(re.tempoRealizacaoEmitir), max(re.tempoRealizacaoEncerrar),cast(sum(re.quantidadeDocumentos) as int),sum(re.valorDocumentos))"
							+ " from ResumoCobrancaAcaoEventual re" + " inner join re.cobrancaAcao cbac" + " where 1 = 1 "
							+ this.criarCondicionaisResumosEventuaisHQL(informarDadosGeracaoResumoAcaoConsultaEventualHelper);
			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){
				consulta += " and re.tempoRealizacaoEmitir BETWEEN :dataInicial and :dataFinal ";
			}
			consulta += " group by cbac.id, cbac.descricaoCobrancaAcao" + " order by cbac.id";
			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){

				retorno = session.createQuery(consulta).setTimestamp("dataInicial",
												Util.formatarDataInicial(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataInicialEmissao()))
								.setTimestamp("dataFinal",
												Util.formatarDataFinal(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataFinalEmissao())).list();
			}else{
				retorno = session.createQuery(consulta).list();
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0617] Consultar Resumo das Ações de Cobrança Eventuais
	 * Pesquisa as ações de cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 25/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Integer consultarCobrancaAcaoEventualQuantidadeDocumentos(
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper,
					Integer idCobrancaAcao) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select cast(sum(re.quantidadeDocumentos) as int) " + " from ResumoCobrancaAcaoEventual re"
							+ " inner join re.cobrancaAcao cbac"
							+ " where cbac.id = :idCobrancaAcao and re.indicadorDefinitivo = :indicadorDefinitivo "
							+ this.criarCondicionaisResumosEventuaisHQL(informarDadosGeracaoResumoAcaoConsultaEventualHelper);
			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){
				consulta += " and re.tempoRealizacaoEmitir BETWEEN :dataInicial and :dataFinal ";
				retorno = ((Integer) session
								.createQuery(consulta)
								.setInteger("idCobrancaAcao", idCobrancaAcao)
								.setInteger("indicadorDefinitivo", ResumoCobrancaAcaoEventual.INIDCADOR_DEFINITIVO)
								.setTimestamp("dataInicial",
												Util.formatarDataInicial(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataInicialEmissao()))
								.setTimestamp("dataFinal",
												Util.formatarDataFinal(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataFinalEmissao())).uniqueResult());
			}else{
				retorno = (Integer) session.createQuery(consulta).setInteger("idCobrancaAcao", idCobrancaAcao).setInteger(
								"indicadorDefinitivo", ResumoCobrancaAcaoEventual.INIDCADOR_DEFINITIVO).uniqueResult();
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0617] Consultar Resumo das Ações de Cobrança Eventuais
	 * Pesquisa as ações de cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 26/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoSituacaoEventual(
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper,
					Integer idCobrancaAcao) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select new gcom.gerencial.bean.CobrancaAcaoSituacaoHelper(castu.id, castu.descricao, cast(sum(re.quantidadeDocumentos) as int), sum(re.valorDocumentos))"
							+ " from ResumoCobrancaAcaoEventual re"
							+ " inner join re.cobrancaAcao cbac"
							+ " inner join re.cobrancaAcaoSituacao castu"
							+ " where  cbac.id = :idCobrancaAcao"
							+ this.criarCondicionaisResumosEventuaisHQL(informarDadosGeracaoResumoAcaoConsultaEventualHelper);
			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){
				consulta += " and re.tempoRealizacaoEmitir BETWEEN :dataInicial and :dataFinal ";
			}
			consulta += " group by cbac.id, cbac.descricaoCobrancaAcao, castu.id, castu.descricao" + " order by castu.id";
			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){
				retorno = session
								.createQuery(consulta)
								.setInteger("idCobrancaAcao", idCobrancaAcao)
								.setTimestamp("dataInicial",
												Util.formatarDataInicial(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataInicialEmissao()))
								.setTimestamp("dataFinal",
												Util.formatarDataFinal(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataFinalEmissao())).list();
			}else{
				retorno = session.createQuery(consulta).setInteger("idCobrancaAcao", idCobrancaAcao).list();
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0617] Consultar Resumo das Ações de Cobrança Eventuais
	 * Pesquisa as ações de cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 26/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoDebitoEventual(
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper,
					Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select new gcom.gerencial.bean.CobrancaAcaoDebitoHelper (cdst.id, cdst.descricao, cast(sum(re.quantidadeDocumentos) as int),"
							+ " sum(re.valorDocumentos))"
							+ " from ResumoCobrancaAcaoEventual re"
							+ " inner join re.cobrancaAcao cbac"
							+ " inner join re.cobrancaAcaoSituacao castu"
							+ " left join re.cobrancaDebitoSituacao cdst"
							+ " where cbac.id = :idCobrancaAcao and castu.id = :idCobrancaAcaoSituacao"
							+ this.criarCondicionaisResumosEventuaisHQL(informarDadosGeracaoResumoAcaoConsultaEventualHelper);
			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){
				consulta += " and re.tempoRealizacaoEmitir BETWEEN :dataInicial and :dataFinal ";
			}
			consulta += " group by cdst.id, cdst.descricao" + " order by cdst.id, cdst.descricao";
			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){
				retorno = session.createQuery(consulta).setInteger("idCobrancaAcao", idCobrancaAcao).setInteger("idCobrancaAcaoSituacao",
								idCobrancaAcaoSituacao).setTimestamp("dataInicial",
												Util.formatarDataInicial(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataInicialEmissao()))
								.setTimestamp("dataFinal",
												Util.formatarDataFinal(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataFinalEmissao())).list();
			}else{
				retorno = session.createQuery(consulta).setInteger("idCobrancaAcao", idCobrancaAcao).setInteger("idCobrancaAcaoSituacao",
								idCobrancaAcaoSituacao).list();
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0617] Consultar Resumo das Ações de Cobrança Eventuais
	 * Pesquisa as ações de cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 26/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoEventualSituacaoPerfilImovel(Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao,
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select new gcom.gerencial.bean.CobrancaAcaoPerfilHelper(iper.id, iper.descricao,"
							+ " cast(sum(re.quantidadeDocumentos) as int), sum(re.valorDocumentos))"
							+ " from ResumoCobrancaAcaoEventual re" + " inner join re.cobrancaAcao cbac"
							+ " inner join re.cobrancaAcaoSituacao castu" + " inner join re.imovelPerfil iper"
							+ " where cbac.id = :idCobrancaAcao ";
			if(idCobrancaAcaoSituacao != null){
				consulta = consulta + "and castu.id = " + idCobrancaAcaoSituacao + " ";
			}

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){
				consulta += " and re.tempoRealizacaoEmitir BETWEEN :dataInicial and :dataFinal ";
			}

			consulta = consulta + this.criarCondicionaisResumosEventuaisHQL(informarDadosGeracaoResumoAcaoConsultaEventualHelper)
							+ " group by iper.id, iper.descricao" + " order by iper.id";
			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){
				retorno = session
								.createQuery(consulta)
								.setInteger("idCobrancaAcao", idCobrancaAcao)
								.setTimestamp("dataInicial",
												Util.formatarDataInicial(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataInicialEmissao()))
								.setTimestamp("dataFinal",
												Util.formatarDataFinal(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataFinalEmissao())).list();
			}else{
				retorno = session.createQuery(consulta).setInteger("idCobrancaAcao", idCobrancaAcao).list();
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0617] Consultar Resumo das Ações de Cobrança Eventuais
	 * Pesquisa as ações de cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 26/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoEventualSituacaoPerfilImovelIndicador(Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao,
					Integer idPerfil,
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select new gcom.gerencial.bean.CobrancaAcaoPerfilIndicadorHelper(iper.id, iper.descricao,"
							+ " re.indicadorAcimaLimite, cast(sum(re.quantidadeDocumentos) as int), sum(re.valorDocumentos))"
							+ " from ResumoCobrancaAcaoEventual re" + " inner join re.cobrancaAcao cbac"
							+ " inner join re.cobrancaAcaoSituacao castu" + " inner join re.imovelPerfil iper"
							+ " where cbac.id = :idCobrancaAcao " + " and iper.id = :idPerfil ";
			if(idCobrancaAcaoSituacao != null){
				consulta = consulta + " and castu.id = " + idCobrancaAcaoSituacao + " ";
			}

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){
				consulta += " and re.tempoRealizacaoEmitir BETWEEN :dataInicial and :dataFinal ";
			}

			consulta = consulta + this.criarCondicionaisResumosEventuaisHQL(informarDadosGeracaoResumoAcaoConsultaEventualHelper)
							+ " group by iper.id, iper.descricao, re.indicadorAcimaLimite" + " order by iper.id";
			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){
				retorno = session
								.createQuery(consulta)
								.setInteger("idCobrancaAcao", idCobrancaAcao)
								.setInteger("idPerfil", idPerfil)
								.setTimestamp("dataInicial",
												Util.formatarDataInicial(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataInicialEmissao()))
								.setTimestamp("dataFinal",
												Util.formatarDataFinal(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataFinalEmissao())).list();
			}else{
				retorno = session.createQuery(consulta).setInteger("idCobrancaAcao", idCobrancaAcao).setInteger("idPerfil", idPerfil)
								.list();
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0617] Consultar Resumo das Ações de Cobrança Eventuais
	 * Pesquisa as ações de cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 26/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoEventualDebitoPerfilImovel(Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao,
					Integer idCobrancaAcaoDebito, Short idIndicador,
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select new gcom.gerencial.bean.CobrancaAcaoPerfilHelper(iper.id, iper.descricao, "
							+ " cast(sum(re.quantidadeDocumentos) as int), sum(re.valorDocumentos))"
							+ " from ResumoCobrancaAcaoEventual re" + " inner join re.cobrancaAcao cbac "
							+ " inner join re.cobrancaAcaoSituacao castu" + " left join re.cobrancaDebitoSituacao cdst"
							+ " inner join re.imovelPerfil iper" + " where cbac.id = :idCobrancaAcao "
							+ " and cdst = :idCobrancaAcaoDebito ";
			if(idIndicador != null){
				consulta += "and  re.indicadorAntesApos = " + idIndicador + " ";
			}else{
				consulta += "and  re.indicadorAntesApos is null ";
			}
			if(idCobrancaAcaoSituacao != null){
				consulta += " and castu.id = " + idCobrancaAcaoSituacao + " ";
			}
			consulta += this.criarCondicionaisResumosEventuaisHQL(informarDadosGeracaoResumoAcaoConsultaEventualHelper);

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){
				consulta += " and re.tempoRealizacaoEmitir BETWEEN :dataInicial and :dataFinal ";
			}

			consulta += " group by iper.id, iper.descricao" + " order by iper.id";

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){

				retorno = session
								.createQuery(consulta)
								.setInteger("idCobrancaAcao", idCobrancaAcao)
								.setInteger("idCobrancaAcaoDebito", idCobrancaAcaoDebito)
								.setTimestamp("dataInicial",
												Util.formatarDataInicial(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataInicialEmissao()))
								.setTimestamp("dataFinal",
												Util.formatarDataFinal(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataFinalEmissao())).list();
			}else{
				retorno = session.createQuery(consulta).setInteger("idCobrancaAcao", idCobrancaAcao).setInteger("idCobrancaAcaoDebito",
								idCobrancaAcaoDebito).list();
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0617] Consultar Resumo das Ações de Cobrança Eventuais
	 * Pesquisa as ações de cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 26/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoEventualDebitoPerfilImovelIndicador(Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao,
					Integer idCobrancaAcaoDebito, Integer idPerfil, Short idIndicador,
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select new gcom.gerencial.bean.CobrancaAcaoPerfilIndicadorHelper(iper.id, iper.descricao, "
							+ " re.indicadorAcimaLimite, cast(sum(re.quantidadeDocumentos) as int), sum(re.valorDocumentos))"
							+ " from ResumoCobrancaAcaoEventual re" + " inner join re.cobrancaAcao cbac "
							+ " inner join re.cobrancaAcaoSituacao castu" + " left join re.cobrancaDebitoSituacao cdst"
							+ " inner join re.imovelPerfil iper" + " where cbac.id = :idCobrancaAcao "
							+ " and cdst = :idCobrancaAcaoDebito " + " and iper.id = :idPerfil ";
			if(idIndicador != null){
				consulta += "and  re.indicadorAntesApos = " + idIndicador + " ";
			}else{
				consulta += "and  re.indicadorAntesApos is null ";
			}
			if(idCobrancaAcaoSituacao != null){
				consulta += "and castu.id =" + idCobrancaAcaoSituacao + " ";
			}
			consulta += this.criarCondicionaisResumosEventuaisHQL(informarDadosGeracaoResumoAcaoConsultaEventualHelper);

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){
				consulta += " and re.tempoRealizacaoEmitir BETWEEN :dataInicial and :dataFinal ";
			}
			consulta += " group by iper.id, iper.descricao, re.indicadorAcimaLimite" + " order by iper.id";

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){
				retorno = session.createQuery(consulta).setInteger("idCobrancaAcao", idCobrancaAcao).setInteger("idCobrancaAcaoDebito",
 idCobrancaAcaoDebito)
								.setInteger("idPerfil", idPerfil)
								.setTimestamp("dataInicial",
												Util.formatarDataInicial(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataInicialEmissao()))
								.setTimestamp("dataFinal",
												Util.formatarDataFinal(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataFinalEmissao())).list();
			}else{
				retorno = session.createQuery(consulta).setInteger("idCobrancaAcao", idCobrancaAcao).setInteger("idCobrancaAcaoDebito",
								idCobrancaAcaoDebito).setInteger("idPerfil", idPerfil).list();
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0617] Consultar Resumo das Ações de Cobrança Eventuais
	 * Pesquisa as ações de cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 26/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarCobrancaAcaoEventualDebitoComIndicador(
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper,
					Integer idCobrancaAcao, Integer idCobrancaAcaoSituacao, short indicadorAntesApos, Integer idCobrancaAcaoDebito)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select new gcom.gerencial.bean.CobrancaAcaoDebitoHelper (cdst.id, cdst.descricao, cast(sum(re.quantidadeDocumentos) as int),"
							+ " sum(re.valorDocumentos), re.indicadorAntesApos)"
							+ " from ResumoCobrancaAcaoEventual re"
							+ " inner join re.cobrancaAcao cbac"
							+ " inner join re.cobrancaAcaoSituacao castu"
							+ " left join re.cobrancaDebitoSituacao cdst"
							+ " where cbac.id = :idCobrancaAcao and castu.id = :idCobrancaAcaoSituacao "

							+ " AND re.indicadorAntesApos = :indicadorAntesApos and cdst = :idCobrancaAcaoDebito "
							+ this.criarCondicionaisResumosEventuaisHQL(informarDadosGeracaoResumoAcaoConsultaEventualHelper);
			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){
				consulta += " and re.tempoRealizacaoEmitir BETWEEN :dataInicial and :dataFinal ";
			}
			consulta += " group by cdst.id, cdst.descricao, re.indicadorAntesApos"
							+ " order by cdst.id, cdst.descricao, re.indicadorAntesApos";

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){
				retorno = session.createQuery(consulta).setInteger("idCobrancaAcao", idCobrancaAcao).setInteger("idCobrancaAcaoSituacao",
 idCobrancaAcaoSituacao)
								.setInteger("idCobrancaAcaoDebito", idCobrancaAcaoDebito)
								.setShort("indicadorAntesApos", indicadorAntesApos)
								.setTimestamp("dataInicial",
												Util.formatarDataInicial(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataInicialEmissao()))
								.setTimestamp("dataFinal",
												Util.formatarDataFinal(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataFinalEmissao())).list();
			}else{
				retorno = session.createQuery(consulta).setInteger("idCobrancaAcao", idCobrancaAcao).setInteger("idCobrancaAcaoSituacao",
								idCobrancaAcaoSituacao).setInteger("idCobrancaAcaoDebito", idCobrancaAcaoDebito).setShort(
								"indicadorAntesApos", indicadorAntesApos).list();
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0617] Consultar Resumo das Ações de Cobrança Eventuais
	 * Pesquisa as ações de cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 26/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public String criarCondicionaisResumosEventuaisHQL(
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper){

		String sql = " ";
		/*
		 * A partir daqui sera montanda a parte dos condicionais da query estas
		 * condicionais serão usadas se necessarias, o q determina seus usos são
		 * os parametros que veem carregados no objeto
		 * InformarDadosGeracaoRelatorioConsultaHelper que é recebido do caso de
		 * uso [UC0304] Informar Dados para Geração de Relatorio ou COnsulta
		 */
		if(informarDadosGeracaoResumoAcaoConsultaEventualHelper != null){

			// Inicio Parametros simples
			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getIdCobrancaAcaoAtividadeComando() != null
							&& !informarDadosGeracaoResumoAcaoConsultaEventualHelper.getIdCobrancaAcaoAtividadeComando().toString().equals(
											"")){
				sql = sql + " and re.cobrancaAcaoAtividadeComando.id = "
								+ informarDadosGeracaoResumoAcaoConsultaEventualHelper.getIdCobrancaAcaoAtividadeComando();
			}

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getEloPolo() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getEloPolo().getId() != null){
				sql = sql + " and re.localidade.localidade.id = "
								+ informarDadosGeracaoResumoAcaoConsultaEventualHelper.getEloPolo().getId();
			}

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getLocalidade() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getLocalidade().getId() != null){
				sql = sql + " and re.localidade.id = " + informarDadosGeracaoResumoAcaoConsultaEventualHelper.getLocalidade().getId();
			}

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getSetorComercial() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getSetorComercial().getId() != null){
				sql = sql + " and re.setorComercial.id = "
								+ informarDadosGeracaoResumoAcaoConsultaEventualHelper.getSetorComercial().getId();
			}

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getQuadra() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getQuadra().getId() != null){
				sql = sql + " and re.quadra.id = " + informarDadosGeracaoResumoAcaoConsultaEventualHelper.getQuadra().getId();
			}

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getUnidadeNegocio() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getUnidadeNegocio().getId() != null){
				sql = sql + " and re.unidadeNegocio.id = "
								+ informarDadosGeracaoResumoAcaoConsultaEventualHelper.getUnidadeNegocio().getId();
			}
			// Inicio de parametros por colecão
			// sera lida a colecao e montado um IN() a partis dos id extraidos
			// de cada objeto da colecao.

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoCobrancaAcao() != null
							&& !informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoCobrancaAcao().isEmpty()){

				Iterator iterator = informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoCobrancaAcao().iterator();
				CobrancaAcao cobrancaAcao = null;

				sql = sql + " and re.cobrancaAcao.id in (";
				while(iterator.hasNext()){
					cobrancaAcao = (CobrancaAcao) iterator.next();
					sql = sql + cobrancaAcao.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoCobrancaGrupo() != null
							&& !informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoCobrancaGrupo().isEmpty()){

				Iterator iterator = informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoCobrancaGrupo().iterator();
				CobrancaGrupo cobrancaGrupo = null;

				sql = sql + " and re.cobrancaGrupo.id in (";
				while(iterator.hasNext()){
					cobrancaGrupo = (CobrancaGrupo) iterator.next();
					sql = sql + cobrancaGrupo.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoGerenciaRegional() != null
							&& !informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoGerenciaRegional().isEmpty()){

				Iterator iterator = informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoGerenciaRegional().iterator();
				GerenciaRegional gerenciaRegional = null;

				sql = sql + " and re.gerenciaRegional.id in (";
				while(iterator.hasNext()){
					gerenciaRegional = (GerenciaRegional) iterator.next();
					sql = sql + gerenciaRegional.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}
			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoImovelPerfil() != null
							&& !informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoImovelPerfil().isEmpty()){

				Iterator iterator = informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoImovelPerfil().iterator();
				ImovelPerfil imovelPerfil = null;

				sql = sql + " and re.imovelPerfil.id in (";
				while(iterator.hasNext()){
					imovelPerfil = (ImovelPerfil) iterator.next();
					sql = sql + imovelPerfil.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoLigacaoAguaSituacao() != null
							&& !informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoLigacaoAguaSituacao().isEmpty()){

				Iterator iterator = informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoLigacaoAguaSituacao().iterator();
				LigacaoAguaSituacao ligacaoAguaSituacao = null;

				sql = sql + " and re.ligacaoAguaSituacao.id in (";
				while(iterator.hasNext()){
					ligacaoAguaSituacao = (LigacaoAguaSituacao) iterator.next();
					sql = sql + ligacaoAguaSituacao.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoLigacaoEsgotoSituacao() != null
							&& !informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoLigacaoEsgotoSituacao().isEmpty()){

				Iterator iterator = informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoLigacaoEsgotoSituacao().iterator();
				LigacaoEsgotoSituacao ligacaoEsgotoSituacao = null;

				sql = sql + " and re.ligacaoEsgotoSituacao.id in (";
				while(iterator.hasNext()){
					ligacaoEsgotoSituacao = (LigacaoEsgotoSituacao) iterator.next();
					sql = sql + ligacaoEsgotoSituacao.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoCategoria() != null
							&& !informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoCategoria().isEmpty()){

				Iterator iterator = informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoCategoria().iterator();
				Categoria categoria = null;

				sql = sql + " and re.categoria.id in (";
				while(iterator.hasNext()){
					categoria = (Categoria) iterator.next();
					sql = sql + categoria.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoEsferaPoder() != null
							&& !informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoEsferaPoder().isEmpty()){

				Iterator iterator = informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoEsferaPoder().iterator();
				EsferaPoder esferaPoder = null;

				sql = sql + " and re.esferaPoder.id in (";
				while(iterator.hasNext()){
					esferaPoder = (EsferaPoder) iterator.next();
					sql = sql + esferaPoder.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}
			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoEmpresa() != null
							&& !informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoEmpresa().isEmpty()){

				Iterator iterator = informarDadosGeracaoResumoAcaoConsultaEventualHelper.getColecaoEmpresa().iterator();
				Empresa empresa = null;

				sql = sql + " and re.empresa.id in (";
				while(iterator.hasNext()){
					empresa = (Empresa) iterator.next();
					sql = sql + empresa.getId() + ",";
				}
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") ";
			}
		}

		return sql;
	}

	/**
	 * O sistema seleciona as contas pendentes ( a partir
	 * da tabela CONTA com CNTA_AMREFERENCIACONTA <
	 * PARM_AMREFERENCIAFATURAMENTO da tabela SISTEMA_PARAMETROS
	 * e ( DCST_IDATUAL = 0 ou (DCST_IDATUAL = (1,2) e
	 * CNTA_AMREFERENCIACONTABIL < PARM_AMREFENRECIAFATURAMENTO
	 * ou (DCST_IDATUAL = (3,4,5) e CNTA_AMREFERENCIACONTABIL >
	 * PARM_AMREFERENCIAFATURAMENTO
	 * 
	 * @author Bruno Barrros
	 * @date 19/07/2007
	 * @param idLocalidade
	 *            id da localidade a ser pesquisada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getContasPendentes(int idSetor) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String hql = "select "
							+ "   loc.gerenciaRegional.id, "
							+ // 0
							"   loc.unidadeNegocio.id, "
							+ // 1
							"   loc.localidade.id,  "
							+ // 2
							"   loc.id,  "
							+ // 3
							"   setCom.id,"
							+ // 4
							"   qua.rota.id,  "
							+ // 5
							"   qua.id,  "
							+ // 6
							"   setCom.codigo, "
							+ // 7
							"   qua.numeroQuadra,  "
							+ // 8
							"   imo.imovelPerfil.id,  "
							+ // 9
							"   imo.ligacaoAguaSituacao.id, "
							+ // 10
							"   imo.ligacaoEsgotoSituacao.id,  "
							+ // 11
							"   ligAgua.ligacaoAguaPerfil.id, "
							+ // 12
							"   ligEsgoto.ligacaoEsgotoPerfil.id, "
							+ // 13
							"   case when ( "
							+ // 14
							"       ( imo.ligacaoAguaSituacao.id in (:ligacaoAguaSituacaoLigado, :ligacaoAguaSituacaoCortado) and "
							+ "         hidAgua.id is not null ) or "
							+ "       ( imo.ligacaoEsgotoSituacao.id = :ligacaoEsgotoSituacaoLigado and "
							+ "         hidEsgoto.id is not null ) ) then " + "     1 " + "   else " + "     2 " + "   end, "
							+ "   case when ( ligAgua.numeroConsumoMinimoAgua > 0 ) then "
							+ // 15
							"     1 " + "   else " + "     2 " + "   end, " + "   case when ( ligEsgoto.consumoMinimo > 0 ) then "
							+ // 16
							"     1 " + "   else " + "     2 " + "   end, " + "   con.referencia, "
							+ // 17
							"   case when ( to_char(con.dataVencimentoConta, 'YYYYMM') < "
							+ // 18
							"   			   ( select " + "    			       anoMesArrecadacao " + "			     	 from "
							+ "                  	   SistemaParametro sp where sp.parmId = "
							+ ConstantesConfig.getIdEmpresa()
							+ ") ) then "
							+ "     1 "
							+ "   else "
							+ "     2 "
							+ "   end, "
							+ "   imo.id, "
							+ // 19
							"   con.valorAgua,"
							+ // 20
							"   con.valorEsgoto,"
							+ // 21
							"   con.debitos,"
							+ // 22
							"   con.valorCreditos,"
							+ // 23
							"   con.valorImposto,"
							+ // 24
							"	( select "
							+ // 25
							" 	    anoMesArrecadacao "
							+ "     from "
							+ "       SistemaParametro sp where sp.parmId = "
							+ ConstantesConfig.getIdEmpresa()
							+ " ), "
							+ "   imo.consumoTarifa.id, " // 26
							+ "  case when ( con.id = ( select max(c.id) from gcom.faturamento.conta.Conta c "
							+ "                         inner join c.imovel i  "
							+ "                         where i.id = imo.id )  "
							+ "  ) then 1 else 0 end  "
							+ "  from "
							+ "   gcom.faturamento.conta.Conta con "
							+ "   inner join con.imovel imo "
							+ "   inner join con.localidade loc "
							+ "   inner join con.quadraConta qua "
							+ "   inner join qua.setorComercial setCom "
							+ "   left join imo.ligacaoAgua ligAgua "
							+ "   left join imo.ligacaoEsgoto ligEsgoto "
							+ "   left join ligAgua.hidrometroInstalacaoHistorico hidAgua "
							+ "   left join imo.hidrometroInstalacaoHistorico hidEsgoto "
							+ "  where "
							+ // con.id between :faixaInicialConta and :faixaFinalConta and " +
							"    con.referencia < ( select "
							+ " 			             sp.anoMesFaturamento " // retirado "- 1"
							+ "			           from "
							+ "                         SistemaParametro sp where sp.parmId = "
							+ ConstantesConfig.getIdEmpresa()
							+ " ) and "
							+ "    ( "
							+ "      con.debitoCreditoSituacaoAtual.id = 0 or "
							+ "      ( "
							+ "        con.debitoCreditoSituacaoAtual.id in (1,2) and "
							+ "        con.referenciaContabil < ( select "
							+ "		 			                 sp.anoMesFaturamento"
							+ "			                       from "
							+ "                                     SistemaParametro sp where sp.parmId = "
							+ ConstantesConfig.getIdEmpresa()
							+ " ) "
							+ "      ) or "
							+ "      ( "
							+ "        con.debitoCreditoSituacaoAtual.id in (3,7,5) and "
							+ "        con.referenciaContabil > ( select "
							+ "		 			                   sp.anoMesFaturamento"
							+ "			                         from "
							+ "                                      SistemaParametro sp where sp.parmId = "
							+ ConstantesConfig.getIdEmpresa() + " ) " + "      ) " + "    ) and " + "    setCom.id = :idSetor";

			retorno = session.createQuery(hql).setInteger("ligacaoAguaSituacaoLigado", LigacaoAguaSituacao.LIGADO).setInteger(
							"ligacaoAguaSituacaoCortado", LigacaoAguaSituacao.CORTADO).setInteger("ligacaoEsgotoSituacaoLigado",
							LigacaoEsgotoSituacao.LIGADO).setInteger("idSetor", idSetor)

			// retorno = session.createQuery(hql).setInteger("idSetor", idSetor)
							// .setInteger("faixaInicialConta", faixaInicialConta)
							// .setInteger("faixaFinalConta", faixaFinalConta)
							.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Seleciona as faixas mínima e máxima para a pesquisa de contas pendentes
	 * 
	 * @author Bruno Barros
	 * @date 03/09/2007
	 * @param idLocalidade
	 *            id da localidade a ser pesquisada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] getFaixaContasPendentes(int idLocalidade) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String hql = " select " + "   min( con.id ), max( con.id ) " + " from " + "   Conta con, " + "   SistemaParametro sp"
							+ " where " + "   con.localidade.id = :idLocalidade and "
							+ "   con.referencia < ( sp.anoMesFaturamento - 1 ) and " + "   (  con.debitoCreditoSituacaoAtual = 0 or "
							+ "      ( con.debitoCreditoSituacaoAtual in (1,2) and "
							+ "        con.referenciaContabil < sp.anoMesFaturamento ) or "
							+ "      ( con.debitoCreditoSituacaoAtual in (3,4,5) and "
							+ "        con.referenciaContabil >= sp.anoMesFaturamento ) " + "   ) ";

			retorno = (Object[]) session.createQuery(hql).setInteger("idLocalidade", idLocalidade).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * O sistema seleciona as contas pendentes (a partir
	 * da tabela CONTA com CNTA_AMREFERENCIACONTA < PARM_AMREFERENCIAFATURAMENTO
	 * da tabela SISTEMA_PARAMENTOS e DCST_IDATUAL com valor correspondente a normal
	 * ou incluida ou retificada
	 * 
	 * @author Bruno Barrros
	 * @date 01/08/2007
	 * @param idLocalidade
	 *            id da localidade a ser pesquisada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getContasPendentesPorRegiao(int idSetor) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String hql = "select "
							+ "  reg.id, "
							+ // 1
							"  micr.id, "
							+ // 2
							"  muni.id, "
							+ // 3
							"  bai.id, "
							+ // 4
							"  imo.imovelPerfil.id, "
							+ // 5
							"  imo.ligacaoAguaSituacao.id, "
							+ // 6
							"  imo.ligacaoEsgotoSituacao.id, "
							+ // 7
							"  case when ( "
							+ // 8
							"	( ligAguaSit.id in (:ligacaoAguaSituacaoLigado, :ligacaoAguaSituacaoCortado) and "
							+ "	  ligAgua.hidrometroInstalacaoHistorico is not null ) or "
							+ "	( ligEsgSit.id = :ligacaoEsgotoSituacaoLigado and "
							+ "	  imo.hidrometroInstalacaoHistorico is not null ) ) then " + "	 1 " + "  else " + "    2 " + "  end, "
							+ "  case when ( ligAgua.numeroConsumoMinimoAgua > 0 ) then "
							+ // 9
							"    1 " + "  else " + "    2 " + "  end, " + "  case when ( ligEsgoto.consumoMinimo > 0 ) then "
							+ // 10
							"    1 " + "  else " + "    2 " + "  end, " + "  con.referencia, "
							+ // 11
							"  case when ( to_char(con.dataVencimentoConta, 'YYYYMM') < "
							+ // 12
							"    ( select " + "        anoMesArrecadacao " + "      from "
							+ "        SistemaParametro sp where sp.parmId = "
							+ ConstantesConfig.getIdEmpresa()
							+ " ) ) then "
							+ "     1 "
							+ "  else "
							+ "    2 "
							+ "  end, "
							+ "  con.valorAgua, "
							+ // 13
							"  con.valorEsgoto, "
							+ // 14
							"  con.debitos, "
							+ // 15
							"  con.valorCreditos, "
							+ // 16
							"  con.valorImposto, "
							+ // 17
							"  imo.id, "
							+ // 18
							"from "
							+ "  gcom.faturamento.conta.Conta con "
							+ "  inner join con.imovel imo "
							+ "  inner join imo.logradouroBairro logBa "
							+ "  inner join logBa.bairro bai "
							+ "  inner join bai.municipio muni "
							+ "  inner join muni.microrregiao micr "
							+ "  inner join micr.regiao reg "
							+ "  inner join imo.ligacaoAguaSituacao ligAguaSit "
							+ "  inner join imo.ligacaoEsgotoSituacao ligEsgSit "
							+ "  left join imo.ligacaoAgua ligAgua "
							+ "  left join imo.ligacaoEsgoto ligEsgoto "
							+ "where "
							+ "  referencia < (select "
							+ "  				  anoMesFaturamento "
							+ "  				from "
							+ "  				  gcom.cadastro.sistemaparametro.SistemaParametro sp where sp.parmId = "
							+ ConstantesConfig.getIdEmpresa() + ") and " + "  dcst_idatual in ( 0,1,2 ) and " + "  setCom.id = :idSetor";

			retorno = session.createQuery(hql).setInteger("ligacaoAguaSituacaoLigado", LigacaoAguaSituacao.LIGADO).setInteger(
							"ligacaoAguaSituacaoCortado", LigacaoAguaSituacao.CORTADO).setInteger("ligacaoEsgotoSituacaoLigado",
							LigacaoEsgotoSituacao.LIGADO).setInteger("idSetor", idSetor).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public void inserirPendenciaContasGerencia(Integer anoMesReferencia, ResumoPendenciaContasGerenciaHelper helper)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String insert;

		Connection con = null;
		Statement stmt = null;

		try{

			con = session.connection();
			stmt = con.createStatement();

			insert = "insert into " + " resumo_pendencia ( " + "  rpen_id, " + // 1
							"  rpen_amreferencia, " + // 2
							"  greg_id, " + // 3
							"  uneg_id, " + // 4
							"  loca_cdelo, " + // 5
							"  loca_id, " + // 6
							"  stcm_id, " + // 7
							"  rota_id, " + // 8
							"  qdra_id, " + // 9
							"  rpen_cdsetorcomercial, " + // 10
							"  rpen_nnquadra, " + // 11
							"  iper_id, " + // 12
							"  last_id, " + // 13
							"  lest_id, " + // 14
							"  catg_id, " + // 15
							"  scat_id, " + // 16
							"  epod_id, " + // 17
							"  cltp_id, " + // 18
							"  lapf_id, " + // 19
							"  lepf_id, " + // 20
							"  rpen_ichidrometro, " + // 21
							"  rpen_icvolfixadoagua, " + // 22
							"  rpen_icvolfixadoesgoto, " + // 23
							"  dotp_id, " + // 24
							"  rpen_amreferenciadocumento, " + // 25
							"  rpen_icvencido, " + // 26
							"  rpen_qtligacoes, " + // 27
							"  rpen_qtdocumentos, " + // 28
							"  rpen_vlpendente_agua, " + // 29
							"  rpen_vlpendente_esgoto, " + // 30
							"  rpen_vlpendente_debitos, " + // 31
							"  rpen_vlpendente_creditos, " + // 32
							"  rpen_vlpendente_impostos, " + // 33
							"  rpen_tmultimaalteracao, " + // 34
							"  cstf_id ) " + // 35
							"values ( " + " sq_resumo_pendencia.nextval, " + // 1
							anoMesReferencia + ", " + // 2
							helper.getIdGerenciaRegional() + ", " + // 3
							helper.getIdUnidadeNegocio() + ", " + // 4
							helper.getIdElo() + ", " + // 5
							helper.getIdLocalidade() + ", " + // 6
							helper.getIdSetorComercial() + ", " + // 7
							helper.getIdRota() + ", " + // 8
							helper.getIdQuadra() + ", " + // 9
							helper.getCodigoSetorComercial() + ", " + // 10
							helper.getNumeroQuadra() + ", " + // 11
							helper.getIdPerfilImovel() + ", " + // 12
							helper.getIdSituacaoLigacaoAgua() + ", " + // 13
							helper.getIdSituacaoLigacaoEsgoto() + ", " + // 14
							helper.getIdPrincipalCategoriaImovel() + ", " + // 15
							helper.getIdPrincipalSubCategoriaImovel() + ", " + // 16
							helper.getIdEsferaPoder() + ", " + // 17
							helper.getIdTipoClienteResponsavel() + ", " + // 18
							helper.getIdPerfilLigacaoAgua() + ", " + // 19
							helper.getIdPerfilLigacaoEsgoto() + ", " + // 20
							helper.getIdHidrometro() + ", " + // 21
							helper.getIdVolFixadoAgua() + ", " + // 22
							helper.getIdVolFixadoEsgoto() + ", " + // 23
							helper.getIdTipoDocumento() + ", " + // 24
							helper.getAnoMesReferenciaDocumento() + ", " + // 25
							helper.getIdReferenciaVencimentoConta() + ", " + // 26
							helper.getQuantidadeLigacoes() + ", " + // 27
							helper.getQuantidadeDocumentos() + ", " + // 28
							helper.getValorPendenteAgua() + ", " + // 29
							helper.getValorPendenteEsgoto() + ", " + // 30
							helper.getValorPendenteDebito() + ", " + // 31
							helper.getValorPendenteCredito() + ", " + // 32
							helper.getValorPendenteImposto() + ", " + // 33
							"  current_date, " + // 34
							helper.getIdTipoTarifaConsumo() + " ) "; // 35

			stmt.executeUpdate(insert);

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);

			try{
				stmt.close();
				con.close();
			}catch(SQLException e){
				throw new ErroRepositorioException(e, "Erro ao fechar conexões");
			}
		}
	}

	/**
	 * @author Bruno Barrros
	 * @date 01/08/2007
	 * @param idLocalidade
	 *            id da localidade a ser pesquisada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getGuiasPagamentoGerencia(int idSetor) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String hql = "select "
							+ "  loc.gerenciaRegional.id, "
							+ // 0
							"  loc.unidadeNegocio.id, "
							+ // 1
							"  loc.localidade.id, "
							+ // 2
							"  loc.id, "
							+ // 3
							"  setCom.id, "
							+ // 4
							"  quaImo.rota.id, "
							+ // 5
							"  quaImo.id, "
							+ // 6
							"  setCom.codigo, "
							+ // 7
							"  quaImo.numeroQuadra, "
							+ // 8
							"  imo.imovelPerfil.id, "
							+ // 9
							"  imo.ligacaoAguaSituacao.id, "
							+ // 10
							"  imo.ligacaoEsgotoSituacao.id, "
							+ // 11
							"  ligAguaPerfil.id, "
							+ // 12
							"  ligEsgotoPerfil.id, "
							+ // 13
							"  case when ( "
							+ // 14
							"       ( imo.ligacaoAguaSituacao.id in (:ligacaoAguaSituacaoLigado, :ligacaoAguaSituacaoCortado) and "
							+ "         hidAgua.id is not null ) or "
							+ "       ( imo.ligacaoEsgotoSituacao.id = :ligacaoEsgotoSituacaoLigado and "
							+ "         hidEsgoto.id is not null ) ) then "
							+ "     1 "
							+ "   else "
							+ "     2 "
							+ "   end, "
							+ "  case when ( ligAgua.numeroConsumoMinimoAgua > 0 ) then "
							+ // 15
							"    1 "
							+ "  else "
							+ "    2 "
							+ "  end, "
							+ "  case when ( ligEsgoto.consumoMinimo > 0 ) then "
							+ // 16
							"    1 "
							+ "  else "
							+ "    2 "
							+ "  end, "
							+ "  guia.valorDebito, "
							+ // 17
							"  imo.id, "
							+ // 18
							"  imo.consumoTarifa.id, "
							+ // 19
							"  guia.numeroPrestacaoTotal, "
							+ // 20
							"  guia.id "
							+ // 21
							"from " + "  gcom.arrecadacao.pagamento.GuiaPagamento guia " + "  inner join guia.localidade loc "
							+ "  inner join guia.guiasPagamentoPrestacao prestacao " + "  left join guia.imovel imo "
							+ "  left join imo.quadra quaImo " + "  left join quaImo.setorComercial setCom "
							+ "  left join imo.ligacaoAgua ligAgua " + "  left join ligAgua.ligacaoAguaPerfil ligAguaPerfil "
							+ "  left join imo.ligacaoEsgoto ligEsgoto " + "  left join ligEsgoto.ligacaoEsgotoPerfil ligEsgotoPerfil "
							+ "  left join ligAgua.hidrometroInstalacaoHistorico hidAgua "
							+ "  left join imo.hidrometroInstalacaoHistorico hidEsgoto " + "where "
							+ "  ( ( prestacao.anoMesReferenciaFaturamento < (select " + " sp.anoMesFaturamento " + " from "
							+ " gcom.cadastro.sistemaparametro.SistemaParametro sp where sp.parmId = "
							+ ConstantesConfig.getIdEmpresa()
							+ ") and "
							+ "  prestacao.debitoCreditoSituacao in ( "
							+ DebitoCreditoSituacao.NORMAL
							+ ", "
							+ DebitoCreditoSituacao.INCLUIDA
							+ ", "
							+ DebitoCreditoSituacao.RETIFICADA
							+ " ) ) "
							+ " or ( "
							+ " prestacao.anoMesReferenciaFaturamento >= (select "
							+ "  								     sp.anoMesFaturamento"
							+ "  								   from "
							+ "  	 	  gcom.cadastro.sistemaparametro.SistemaParametro sp where sp.parmId = "
							+ ConstantesConfig.getIdEmpresa()
							+ ") and "
							+ "  prestacao.debitoCreditoSituacao in ( "
							+ DebitoCreditoSituacao.CANCELADA
							+ ", "
							+ DebitoCreditoSituacao.CANCELADA_POR_RETIFICACAO
							+ ", "
							+ DebitoCreditoSituacao.PARCELADA
							+ ", "
							+ DebitoCreditoSituacao.PRESCRITA
							+ " ) "
							+ ") ) and "
							+ "  setCom.id = :idSetor";

			retorno = session.createQuery(hql).setInteger("ligacaoAguaSituacaoLigado", LigacaoAguaSituacao.LIGADO).setInteger(
							"ligacaoAguaSituacaoCortado", LigacaoAguaSituacao.CORTADO).setInteger("ligacaoEsgotoSituacaoLigado",
							LigacaoEsgotoSituacao.LIGADO).setInteger("idSetor", idSetor).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Retorna as prestações de um guia de pagamento
	 * 
	 * @author Luciano Galvão
	 * @date 07/03/2012
	 * @param idGuiaPagamento
	 *            id da guia de pagamento
	 * @throws ErroRepositorioException
	 */
	public List getPrestacoesDeGuiaPagamento(int idGuiaPagamento) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			String hql = "select " + "  prest.anoMesReferenciaFaturamento, "
							+ // 0
							"  prest.financiamentoTipo.id, "
							+ // 1
							"  to_char(prest.dataVencimento, 'YYYYMM') "
							+ // 2
							"from " + "  gcom.arrecadacao.pagamento.GuiaPagamentoPrestacao prest " + " where "
							+ "  prest.comp_id.guiaPagamentoId = :idGuiaPagamento ";

			retorno = session.createQuery(hql).setInteger("idGuiaPagamento", idGuiaPagamento).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public void inserirGuiasPagamentoGerencia(Integer anoMesReferencia, ResumoPendenciaGuiasPagamentoGerenciaHelper helper)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String insert;

		Connection con = null;
		Statement stmt = null;

		try{

			con = session.connection();
			stmt = con.createStatement();

			insert = "insert into " + " resumo_pendencia ( " + "  rpen_id, " + // 1
							"  rpen_amreferencia, " + // 2
							"  greg_id, " + // 3
							"  uneg_id, " + // 4
							"  loca_cdelo, " + // 5
							"  loca_id, " + // 6
							"  stcm_id, " + // 7
							"  rota_id, " + // 8
							"  qdra_id, " + // 9
							"  rpen_cdsetorcomercial, " + // 10
							"  rpen_nnquadra, " + // 11
							"  iper_id, " + // 12
							"  last_id, " + // 13
							"  lest_id, " + // 14
							"  catg_id, " + // 15
							"  scat_id, " + // 16
							"  epod_id, " + // 17
							"  cltp_id, " + // 18
							"  lapf_id, " + // 19
							"  lepf_id, " + // 20
							"  rpen_ichidrometro, " + // 21
							"  rpen_icvolfixadoagua, " + // 22
							"  rpen_icvolfixadoesgoto, " + // 23
							"  dotp_id, " + // 24
							"  rpen_amreferenciadocumento, " + // 25
							"  rpen_icvencido, " + // 26
							"  rpen_qtligacoes, " + // 27
							"  rpen_qtdocumentos, " + // 28
							"  rpen_vlpendente_debitos, " + // 31
							"  rpen_tmultimaalteracao," + // 32
							"  cstf_id," + // 33
							"  fntp_id, " + // 34
							"  rpen_icvalorcurtoprazo ) " + // 35
							"values ( " + " sq_resumo_pendencia.nextval, " + // 1
							anoMesReferencia + ", " + // 2
							helper.getIdGerenciaRegional() + ", " + // 3
							helper.getIdUnidadeNegocio() + ", " + // 4
							helper.getIdElo() + ", " + // 5
							helper.getIdLocalidade() + ", " + // 6
							helper.getIdSetorComercial() + ", " + // 7
							helper.getIdRota() + ", " + // 8
							helper.getIdQuadra() + ", " + // 9
							helper.getCodigoSetorComercial() + ", " + // 10
							helper.getNumeroQuadra() + ", " + // 11
							helper.getIdPerfilImovel() + ", " + // 12
							helper.getIdSituacaoLigacaoAgua() + ", " + // 13
							helper.getIdSituacaoLigacaoEsgoto() + ", " + // 14
							helper.getIdPrincipalCategoriaImovel() + ", " + // 15
							helper.getIdPrincipalSubCategoriaImovel() + ", " + // 16
							helper.getIdEsferaPoder() + ", " + // 17
							helper.getIdTipoClienteResponsavel() + ", " + // 18
							helper.getIdPerfilLigacaoAgua() + ", " + // 19
							helper.getIdPerfilLigacaoEsgoto() + ", " + // 20
							helper.getIdHidrometro() + ", " + // 21
							helper.getIdVolFixadoAgua() + ", " + // 22
							helper.getIdVolFixadoEsgoto() + ", " + // 23
							helper.getIdTipoDocumento() + ", " + // 24
							helper.getAnoMesReferenciaDocumento() + ", " + // 25
							helper.getIdReferenciaVencimentoConta() + ", " + // 26
							helper.getQuantidadeLigacoes() + ", " + // 27
							helper.getQuantidadeDocumentos() + ", " + // 28
							helper.getValorPendenteDebito() + ", " + // 31
							"  current_date, " + // 32
							helper.getIdTipoTarifaConsumo() + ", " + // 33
							helper.getIdTipoFinanciamento() + ", " + // 34
							helper.getIdValorCurtoPrazo() + " ) "; // 35

			stmt.executeUpdate(insert);

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);

			try{
				stmt.close();
				con.close();
			}catch(SQLException e){
				throw new ErroRepositorioException(e, "Erro ao fechar conexões");
			}
		}
	}

	/**
	 * Método que retorna os valores de prestações em GUIA_PAGAMENTO_PRESTACAO
	 * 
	 * @author Luciano Galvão
	 * @date 05/03/2012
	 * @throws ErroRepositorioException
	 */
	public List obterValoresPrestacoesPendentes(Integer idGuiaPagamento) throws ErroRepositorioException{

		List retornoConsulta = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append(" SELECT pp.valorPrestacao ");
			consulta.append(" FROM GuiaPagamentoPrestacao pp ");
			consulta.append(" WHERE pp.comp_id.guiaPagamentoId = :idGuiaPagamento ");

			retornoConsulta = session.createQuery(consulta.toString()).setInteger("idGuiaPagamento", idGuiaPagamento).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * @author Bruno Barrros
	 * @date 06/08/2007
	 * @param idLocalidade
	 *            id da localidade a ser pesquisada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getDebitosACobrarGerencia(int idSetor) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String hql = "select "
							+ "  loc.gerenciaRegional.id, "
							+ // 1
							"  loc.unidadeNegocio.id, "
							+ // 2
							"  loc.localidade.id, "
							+ // 3
							"  loc.id, "
							+ // 4
							"  setCom.id, "
							+ // 5
							"  qua.rota.id, "
							+ // 6
							"  qua.id, "
							+ // 7
							"  setCom.codigo, "
							+ // 8
							"  qua.numeroQuadra, "
							+ // 9
							"  imo.imovelPerfil.id, "
							+ // 10
							"  imo.ligacaoAguaSituacao.id, "
							+ // 11
							"  imo.ligacaoEsgotoSituacao.id, "
							+ // 12
							"  ligAgua.ligacaoAguaPerfil.id, "
							+ // 13
							"  ligEsgoto.ligacaoEsgotoPerfil.id, "
							+ // 14
							"  case when ( "
							+ // 15
							"       ( imo.ligacaoAguaSituacao.id in (:ligacaoAguaSituacaoLigado, :ligacaoAguaSituacaoCortado) and "
							+ "         hidAgua.id is not null ) or "
							+ "       ( imo.ligacaoEsgotoSituacao.id = :ligacaoEsgotoSituacaoLigado and "
							+ "         hidEsgoto.id is not null ) ) then "
							+ "     1 "
							+ "   else "
							+ "     2 "
							+ "   end, "
							+ "  case when ( ligAgua.numeroConsumoMinimoAgua > 0 ) then "
							+ // 16
							"    1 "
							+ "  else "
							+ "    2 "
							+ "  end, "
							+ "  case when ( ligEsgoto.consumoMinimo > 0 ) then "
							+ // 17
							"    1 "
							+ "  else "
							+ "    2 "
							+ "  end, "
							+ "  dc.anoMesReferenciaContabil, "
							+ // 18
							"  finTipo.id, "
							+ // 19
							"  ( dc.valorDebito - ( dc.valorDebito / dc.numeroPrestacaoDebito ) * dc.numeroPrestacaoCobradas ), "
							+ // 20
							"  imo.id, "
							+ // 21
							"  imo.consumoTarifa.id, "
							+ // 22
							" dc.numeroPrestacaoDebito, "
							+ // 23
							" dc.numeroPrestacaoCobradas "
							+ // 24
							"from " + "  gcom.faturamento.debito.DebitoACobrar dc " + "  inner join dc.imovel imo "
							+ "  inner join dc.localidade loc " + "  inner join dc.quadra qua " + "  inner join qua.setorComercial setCom "
							+ "  left join imo.ligacaoAgua ligAgua " + "  left join imo.ligacaoEsgoto ligEsgoto "
							+ "  left join ligAgua.hidrometroInstalacaoHistorico hidAgua "
							+ "  left join imo.hidrometroInstalacaoHistorico hidEsgoto " + "  inner join dc.financiamentoTipo finTipo "
							+ "where " + " ( ( dc.anoMesReferenciaContabil < (select " + "  			    		        sp.anoMesFaturamento"
							+ "  			  					  from " + "  			    				gcom.cadastro.sistemaparametro.SistemaParametro sp where sp.parmId = "
							+ ConstantesConfig.getIdEmpresa()
							+ ") and "
							+ "  dc.debitoCreditoSituacaoAtual in ( "
							+ DebitoCreditoSituacao.NORMAL
							+ " , "
							+ DebitoCreditoSituacao.INCLUIDA
							+ " , "
							+ DebitoCreditoSituacao.RETIFICADA
							+ " ) ) "
							+ " or ( "
							+ " dc.anoMesReferenciaContabil >= (select "
							+ "  			    		        sp.anoMesFaturamento"
							+ "  			  					  from "
							+ "  			    				gcom.cadastro.sistemaparametro.SistemaParametro sp where sp.parmId = "
							+ ConstantesConfig.getIdEmpresa()
							+ ") and "
							+ "  dc.debitoCreditoSituacaoAtual in ( "
							+ DebitoCreditoSituacao.CANCELADA
							+ " , "
							+ DebitoCreditoSituacao.CANCELADA_POR_RETIFICACAO
							+ " , "
							+ DebitoCreditoSituacao.PARCELADA
							+ " , "
							+ DebitoCreditoSituacao.PRESCRITA
							+ " ) "
							+ " and dc.debitoCreditoSituacaoAnterior is null " + ") ) " + " and " + "  setCom.id = :idSetor";

			retorno = session.createQuery(hql).setInteger("ligacaoAguaSituacaoLigado", LigacaoAguaSituacao.LIGADO).setInteger(
							"ligacaoAguaSituacaoCortado", LigacaoAguaSituacao.CORTADO).setInteger("ligacaoEsgotoSituacaoLigado",
							LigacaoEsgotoSituacao.LIGADO).setInteger("idSetor", idSetor).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public void inserirPendendiciaDebitosACobrarGerencia(Integer anoMesReferencia, ResumoPendenciaDebitosACobrarGerenciaHelper helper)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String insert;

		Connection con = null;
		Statement stmt = null;

		try{

			con = session.connection();
			stmt = con.createStatement();

			insert = "insert into " + " resumo_pendencia ( " + "  rpen_id, " + // 1
							"  rpen_amreferencia, " + // 2
							"  greg_id, " + // 3
							"  uneg_id, " + // 4
							"  loca_cdelo, " + // 5
							"  loca_id, " + // 6
							"  stcm_id, " + // 7
							"  rota_id, " + // 8
							"  qdra_id, " + // 9
							"  rpen_cdsetorcomercial, " + // 10
							"  rpen_nnquadra, " + // 11
							"  iper_id, " + // 12
							"  last_id, " + // 13
							"  lest_id, " + // 14
							"  catg_id, " + // 15
							"  scat_id, " + // 16
							"  epod_id, " + // 17
							"  cltp_id, " + // 18
							"  lapf_id, " + // 19
							"  lepf_id, " + // 20
							"  rpen_ichidrometro, " + // 21
							"  rpen_icvolfixadoagua, " + // 22
							"  rpen_icvolfixadoesgoto, " + // 23
							"  dotp_id, " + // 24
							"  rpen_amreferenciadocumento, " + // 25
							"  fntp_id, " + // 26
							"  rpen_qtligacoes, " + // 27
							"  rpen_qtdocumentos, " + // 28
							"  rpen_vlpendente_debitos, " + // 29
							"  rpen_tmultimaalteracao, " + // 30
							"  cstf_id, " + // 31
							"  rpen_icvencido, " + // 32
							"  rpen_icvalorcurtoprazo ) " + // 33
							" values ( " + " sq_resumo_pendencia.nextval, " + // 1
							anoMesReferencia + ", " + // 2
							helper.getIdGerenciaRegional() + ", " + // 3
							helper.getIdUnidadeNegocio() + ", " + // 4
							helper.getIdElo() + ", " + // 5
							helper.getIdLocalidade() + ", " + // 6
							helper.getIdSetorComercial() + ", " + // 7
							helper.getIdRota() + ", " + // 8
							helper.getIdQuadra() + ", " + // 9
							helper.getCodigoSetorComercial() + ", " + // 10
							helper.getNumeroQuadra() + ", " + // 11
							helper.getIdPerfilImovel() + ", " + // 12
							helper.getIdSituacaoLigacaoAgua() + ", " + // 13
							helper.getIdSituacaoLigacaoEsgoto() + ", " + // 14
							helper.getIdPrincipalCategoriaImovel() + ", " + // 15
							helper.getIdPrincipalSubCategoriaImovel() + ", " + // 16
							helper.getIdEsferaPoder() + ", " + // 17
							helper.getIdTipoClienteResponsavel() + ", " + // 18
							helper.getIdPerfilLigacaoAgua() + ", " + // 19
							helper.getIdPerfilLigacaoEsgoto() + ", " + // 20
							helper.getIdHidrometro() + ", " + // 21
							helper.getIdVolFixadoAgua() + ", " + // 22
							helper.getIdVolFixadoEsgoto() + ", " + // 23
							helper.getIdTipoDocumento() + ", " + // 24
							helper.getAnoMesReferenciaDocumento() + ", " + // 25
							helper.getIdTipoFinanciamento() + ", " + // 26
							helper.getQuantidadeLigacoes() + ", " + // 27
							helper.getQuantidadeDocumentos() + ", " + // 28
							helper.getValorPendenteDebito() + ", " + // 29
							"  current_date, " + // 30
							helper.getIdTipoTarifaConsumo() + ", " + // 31
							" 2, " + // 32
							helper.getIdValorCurtoPrazo() + " ) "; // 33

			stmt.executeUpdate(insert);

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);

			try{
				stmt.close();
				con.close();
			}catch(SQLException e){
				throw new ErroRepositorioException(e, "Erro ao fechar conexões");
			}
		}
	}

	/**
	 * @author Bruno Barrros
	 * @date 07/08/2007
	 * @param idLocalidade
	 *            id da localidade a ser pesquisada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getCreditoARealizarGerencia(int idSetor) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String hql = "select "
							+ "  loc.gerenciaRegional.id, "
							+ // 0
							"  loc.unidadeNegocio.id, "
							+ // 1
							"  loc.localidade.id, "
							+ // 2
							"  loc.id, "
							+ // 3
							"  setCom.id, "
							+ // 4
							"  qua.rota.id, "
							+ // 5
							"  qua.id, "
							+ // 6
							"  setCom.codigo, "
							+ // 7
							"  qua.numeroQuadra, "
							+ // 8
							"  imo.imovelPerfil.id, "
							+ // 9
							"  imo.ligacaoAguaSituacao.id, "
							+ // 10
							"  imo.ligacaoEsgotoSituacao.id, "
							+ // 11
							"  ligAgua.ligacaoAguaPerfil.id, "
							+ // 12
							"  ligEsgoto.ligacaoEsgotoPerfil.id, "
							+ // 13
							"  case when ( "
							+ // 14
							"       ( imo.ligacaoAguaSituacao.id in (:ligacaoAguaSituacaoLigado, :ligacaoAguaSituacaoCortado) and "
							+ "         hidAgua.id is not null ) or "
							+ "       ( imo.ligacaoEsgotoSituacao.id = :ligacaoEsgotoSituacaoLigado and "
							+ "         hidEsgoto.id is not null ) ) then "
							+ "     1 "
							+ "   else "
							+ "     2 "
							+ "   end, "
							+ "  case when ( ligAgua.numeroConsumoMinimoAgua > 0 ) then "
							+ // 15
							"    1 "
							+ "  else "
							+ "    2 "
							+ "  end, "
							+ "  case when ( ligEsgoto.consumoMinimo > 0 ) then "
							+ // 16
							"    1 "
							+ "  else "
							+ "    2 "
							+ "  end, "
							+ "  cr.anoMesReferenciaContabil, "
							+ // 17
							"  ( ( cr.valorCredito - (cr.valorCredito / cr.numeroPrestacaoCredito) * cr.numeroPrestacaoRealizada ) + cr.valorResidualMesAnterior ), "
							+ // 18
							"  imo.id, "
							+ // 19
							"  imo.consumoTarifa.id, "
							+ // 20
							" cr.numeroPrestacaoCredito, "
							+ // 21
							" cr.numeroPrestacaoRealizada "
							+ // 22
							"from " + "  gcom.faturamento.credito.CreditoARealizar cr " + "  inner join cr.imovel imo "
							+ "  inner join cr.localidade loc " + "  inner join cr.quadra qua " + "  inner join qua.setorComercial setCom "
							+ "  left join imo.ligacaoAgua ligAgua " + "  left join imo.ligacaoEsgoto ligEsgoto   "
							+ "  left join ligAgua.hidrometroInstalacaoHistorico hidAgua "
							+ "  left join imo.hidrometroInstalacaoHistorico hidEsgoto " + "where "
							+ " ( ( cr.anoMesReferenciaContabil < (select " + " sp.anoMesFaturamento"
							+ " from gcom.cadastro.sistemaparametro.SistemaParametro sp where sp.parmId = "
							+ ConstantesConfig.getIdEmpresa()
							+ ") and "
							+ "  cr.debitoCreditoSituacaoAtual in ( "
							+ DebitoCreditoSituacao.NORMAL
							+ " , "
							+ DebitoCreditoSituacao.INCLUIDA
							+ " , "
							+ DebitoCreditoSituacao.RETIFICADA
							+ " ) "
							+ " ) or ( "
							+ " cr.anoMesReferenciaContabil >= (select "
							+ " sp.anoMesFaturamento"
							+ " from gcom.cadastro.sistemaparametro.SistemaParametro sp where sp.parmId = "
							+ ConstantesConfig.getIdEmpresa()
							+ ") and "
							+ "  cr.debitoCreditoSituacaoAtual in ( "
							+ DebitoCreditoSituacao.CANCELADA
							+ " , "
							+ DebitoCreditoSituacao.CANCELADA_POR_RETIFICACAO
							+ " , "
							+ DebitoCreditoSituacao.PARCELADA
							+ " , "
							+ DebitoCreditoSituacao.PRESCRITA
							+ " ) "
							+ " ) ) and "
							+ "  setCom.id = :idSetor ";

			retorno = session.createQuery(hql).setInteger("ligacaoAguaSituacaoLigado", LigacaoAguaSituacao.LIGADO).setInteger(
							"ligacaoAguaSituacaoCortado", LigacaoAguaSituacao.CORTADO).setInteger("ligacaoEsgotoSituacaoLigado",
							LigacaoEsgotoSituacao.LIGADO).setInteger("idSetor", idSetor).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public void inserirPendendiciaCreditosARealizerGerencia(Integer anoMesReferencia, ResumoPendenciaCreditoARealizarGerenciaHelper helper)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String insert;

		Connection con = null;
		Statement stmt = null;

		try{

			con = session.connection();
			stmt = con.createStatement();

			insert = "insert into " + " resumo_pendencia ( " + "  rpen_id, " + // 1
							"  rpen_amreferencia, " + // 2
							"  greg_id, " + // 3
							"  uneg_id, " + // 4
							"  loca_cdelo, " + // 5
							"  loca_id, " + // 6
							"  stcm_id, " + // 7
							"  rota_id, " + // 8
							"  qdra_id, " + // 9
							"  rpen_cdsetorcomercial, " + // 10
							"  rpen_nnquadra, " + // 11
							"  iper_id, " + // 12
							"  last_id, " + // 13
							"  lest_id, " + // 14
							"  catg_id, " + // 15
							"  scat_id, " + // 16
							"  epod_id, " + // 17
							"  cltp_id, " + // 18
							"  lapf_id, " + // 19
							"  lepf_id, " + // 20
							"  rpen_ichidrometro, " + // 21
							"  rpen_icvolfixadoagua, " + // 22
							"  rpen_icvolfixadoesgoto, " + // 23
							"  dotp_id, " + // 24
							"  rpen_amreferenciadocumento, " + // 25
							"  rpen_qtligacoes, " + // 26
							"  rpen_qtdocumentos, " + // 27
							"  rpen_vlpendente_creditos, " + // 28
							"  rpen_tmultimaalteracao," + // 29
							"  cstf_id, " + // 30
							"  rpen_icvencido, " + // 31
							"  rpen_icvalorcurtoprazo ) " + // 32
							" values ( " + " sq_resumo_pendencia.nextval, " + // 1
							anoMesReferencia + ", " + // 2
							helper.getIdGerenciaRegional() + ", " + // 3
							helper.getIdUnidadeNegocio() + ", " + // 4
							helper.getIdElo() + ", " + // 5
							helper.getIdLocalidade() + ", " + // 6
							helper.getIdSetorComercial() + ", " + // 7
							helper.getIdRota() + ", " + // 8
							helper.getIdQuadra() + ", " + // 9
							helper.getCodigoSetorComercial() + ", " + // 10
							helper.getNumeroQuadra() + ", " + // 11
							helper.getIdPerfilImovel() + ", " + // 12
							helper.getIdSituacaoLigacaoAgua() + ", " + // 13
							helper.getIdSituacaoLigacaoEsgoto() + ", " + // 14
							helper.getIdPrincipalCategoriaImovel() + ", " + // 15
							helper.getIdPrincipalSubCategoriaImovel() + ", " + // 16
							helper.getIdEsferaPoder() + ", " + // 17
							helper.getIdTipoClienteResponsavel() + ", " + // 18
							helper.getIdPerfilLigacaoAgua() + ", " + // 19
							helper.getIdPerfilLigacaoEsgoto() + ", " + // 20
							helper.getIdHidrometro() + ", " + // 21
							helper.getIdVolFixadoAgua() + ", " + // 22
							helper.getIdVolFixadoEsgoto() + ", " + // 23
							helper.getIdTipoDocumento() + ", " + // 24
							helper.getAnoMesReferenciaDocumento() + ", " + // 25
							helper.getQuantidadeLigacoes() + ", " + // 26
							helper.getQuantidadeDocumentos() + ", " + // 27
							helper.getValorPendenteCredito() + ", " + // 28
							" current_date, " + // 29
							helper.getIdTipoTarifaConsumo() + ", " + // 30
							" 2 , " + // 31
							helper.getIdValorCurtoPrazo() + " ) "; // 32

			stmt.executeUpdate(insert);

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(SQLException e){
			throw new ErroRepositorioException(e, "Erro no Insert");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);

			try{
				stmt.close();
				con.close();
			}catch(SQLException e){
				throw new ErroRepositorioException(e, "Erro ao fechar conexões");
			}
		}
	}

	/**
	 * @author Marcio Roberto
	 * @date 07/08/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal getPesquisaDebitoACobrar(int idParc) throws ErroRepositorioException{

		BigDecimal retorno = null;

		Session session = HibernateUtil.getSession();
		try{

			String hql = " select " + "   sum(debitoacobrar.valorDebito) as valordebito " + " from "
							+ "   gcom.faturamento.debito.DebitoACobrar debitoacobrar, " + "   gcom.faturamento.conta.Conta conta "
							+ "   inner join debitoacobrar.parcelamento parc " + " where "
							+ "   debitoacobrar.imovel.id = conta.imovel.id and " + "   parc.id = :idParc " + "   and conta.debitos > 0 ";

			retorno = (BigDecimal) session.createQuery(hql).setInteger("idParc", idParc).setMaxResults(1).uniqueResult();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * @author Marcio Roberto
	 * @date 08/08/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal getPesquisaDebitoACobrarTipos(int idConta, int tipoLancamentoItemContabil) throws ErroRepositorioException{

		BigDecimal retorno = null;

		Session session = HibernateUtil.getSession();
		try{

			String hql = " select " + "   sum(debitoacobrar.valorDebito) as valordebito " + " from "
							+ "   gcom.faturamento.debito.DebitoACobrar debitoacobrar, " + "   gcom.faturamento.conta.Conta conta "
							+ "   inner join debitoacobrar.lancamentoItemContabil lancamentoitemcontabil " + " where "
							+ "   debitoacobrar.imovel.id = conta.imovel.id and " + "   conta.id = :idConta " + "   and conta.debitos > 0 "
							+ "   and debitoacobrar.lancamentoItemContabil.id = :idlancamentoItemContabil ";

			retorno = (BigDecimal) session.createQuery(hql).setInteger("idConta", idConta).setInteger("idlancamentoItemContabil",
							tipoLancamentoItemContabil).setMaxResults(1).uniqueResult();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Collection consultarResumoCobrancaAcaoEventual(Integer idCobrancaAcao,
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = " select new gcom.gerencial.bean.CobrancaAcaoSituacaoHelper(mde.id, mde.descricao, cast(sum(re.quantidadeDocumentos) as int), sum(re.valorDocumentos))"
							+ " from ResumoCobrancaAcaoEventual re"
							+ " inner join re.motivoNaoEntregaDocumento mde"
							+ " inner join re.cobrancaAcao cbac"
							+ " where  cbac.id = :idCobrancaAcao"
							+ this.criarCondicionaisResumosEventuaisHQL(informarDadosGeracaoResumoAcaoConsultaEventualHelper);
			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){
				consulta += " and re.tempoRealizacaoEmitir BETWEEN :dataInicial and :dataFinal ";
			}

			consulta += "group by  mde.id, mde.descricao, re.motivoNaoEntregaDocumento.id" + " order by mde.id";

			if(informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataInicialEmissao() != null
							&& informarDadosGeracaoResumoAcaoConsultaEventualHelper.getDataFinalEmissao() != null){

				retorno = session
								.createQuery(consulta)
								.setInteger("idCobrancaAcao", idCobrancaAcao)
								.setTimestamp("dataInicial",
												Util.formatarDataInicial(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataInicialEmissao()))
								.setTimestamp("dataFinal",
												Util.formatarDataFinal(informarDadosGeracaoResumoAcaoConsultaEventualHelper
																.getDataFinalEmissao())).list();
			}else{
				retorno = session.createQuery(consulta).setInteger("idCobrancaAcao", idCobrancaAcao).list();
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

}
