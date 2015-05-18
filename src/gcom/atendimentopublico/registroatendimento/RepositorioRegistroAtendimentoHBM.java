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

package gcom.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.OrdemServicoProgramacao;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipoReferencia;
import gcom.atendimentopublico.registroatendimento.bean.FiltrarRegistroAtendimentoHelper;
import gcom.atendimentopublico.registroatendimento.bean.FiltrarRegistroAtendimentoIncompletoHelper;
import gcom.atendimentopublico.registroatendimento.bean.ServicoAssociadoValorHelper;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.OrgaoExpedidorRg;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.gerencial.atendimentopublico.registroatendimento.UnResumoRegistroAtendimento;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.gui.atendimentopublico.ordemservico.GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm;
import gcom.integracao.acquagis.DadosAcquaGis;
import gcom.operacional.*;
import gcom.relatorio.atendimentopublico.*;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.*;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.dialect.Dialect;

public class RepositorioRegistroAtendimentoHBM
				implements IRepositorioRegistroAtendimento {

	private static IRepositorioRegistroAtendimento instancia;

	/**
	 * Construtor da classe RepositorioAcessoHBM
	 */

	private RepositorioRegistroAtendimentoHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static IRepositorioRegistroAtendimento getInstancia(){

		if(instancia == null){
			instancia = new RepositorioRegistroAtendimentoHBM();
		}

		return instancia;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso exista registro de atendimento pendente para o imóvel com a mesma
	 * especificação (existe ocorrência na tabela REGISTRO_ATENDIMENTO com
	 * IMOV_ID=Matrícula do Imóvel e STEP_ID=Id da Especificação selecionada e
	 * RGAT_CDSITUACAO=1).
	 * [FS0020] - Verificar existência de registro de atendimento para o imóvel
	 * com a mesma especificação
	 * 
	 * @author Raphael Rossiter
	 * @date 31/07/2006
	 * @param idImovel
	 *            ,
	 *            idSolicitacaoTipoEspecificacao
	 * @return RegistroAtendimento
	 * @throws ErroRepositorioException
	 */
	public RegistroAtendimento verificarExistenciaRAImovelMesmaEspecificacao(Integer idImovel, Integer idSolicitacaoTipoEspecificacao)
					throws ErroRepositorioException{

		RegistroAtendimento retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT rgat " + "FROM RegistroAtendimento rgat " + "INNER JOIN FETCH rgat.imovel imov  "
							+ "INNER JOIN FETCH rgat.solicitacaoTipoEspecificacao step  "
							+ "WHERE imov.id = :idImovel AND step.id = :idSolicitacaoTipoespecificacao " + "AND rgat.codigoSituacao = 1 ";

			retorno = (RegistroAtendimento) session.createQuery(consulta).setInteger("idImovel", idImovel)
							.setInteger("idSolicitacaoTipoespecificacao", idSolicitacaoTipoEspecificacao).setMaxResults(1).uniqueResult();

			/*
			 * if (retorno != null) { Hibernate.initialize(retorno.getImovel());
			 * Hibernate.initialize(retorno.getSolicitacaoTipoEspecificacao()); }
			 */

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * Caso exista registro de atendimento pendente para o imóvel com a mesma
	 * especificaçao (existe ocorrência na tabela REGISTRO_ATENDIMENTO com
	 * IMOV_ID=Matrícula do Imóvel e STEP_ID=Id da Especificaçao selecionada e
	 * RGAT_CDSITUACAO=1 e RA_ID<> ID da RA que está sendo atualizado).
	 * [FS0020] - Verificar existência de registro de atendimento para o imóvel
	 * com a mesma especificaçao
	 * 
	 * @author Sávio Luiz
	 * @date 21/08/2006
	 * @param idImovel
	 *            ,
	 *            idSolicitacaoTipoEspecificacao
	 * @return RegistroAtendimento
	 * @throws ErroRepositorioException
	 */
	public RegistroAtendimento verificarExistenciaRAAtualizarImovelMesmaEspecificacao(Integer idImovel, Integer idRA,
					Integer idSolicitacaoTipoEspecificacao) throws ErroRepositorioException{

		RegistroAtendimento retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT rgat " + "FROM RegistroAtendimento rgat " + "INNER JOIN FETCH rgat.imovel imov  "
							+ "INNER JOIN FETCH rgat.solicitacaoTipoEspecificacao step  "
							+ "WHERE imov.id = :idImovel AND step.id = :idSolicitacaoTipoespecificacao "
							+ "AND rgat.codigoSituacao = 1 AND rgat.id <> :idRA";

			retorno = (RegistroAtendimento) session.createQuery(consulta).setInteger("idImovel", idImovel)
							.setInteger("idSolicitacaoTipoespecificacao", idSolicitacaoTipoEspecificacao).setInteger("idRA", idRA)
							.setMaxResults(1).uniqueResult();

			/*
			 * if (retorno != null) { Hibernate.initialize(retorno.getImovel());
			 * Hibernate.initialize(retorno.getSolicitacaoTipoEspecificacao()); }
			 */

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0424] Consultar Registro Atendimento
	 * Retorno o Tramite mais atual a partir
	 * 
	 * @author Rafael Pinto
	 * @date 10/08/2006
	 * @param idRA
	 * @return Tramite
	 * @throws ErroRepositorioException
	 */
	public Tramite recuperarTramiteMaisAtualPorRA(Integer idRA) throws ErroRepositorioException{

		Tramite retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			DetachedCriteria maxData = DetachedCriteria.forClass(Tramite.class).setProjection(Property.forName("dataTramite").max())
							.add(Expression.eq("registroAtendimento.id", idRA));

			Criteria criteria = session.createCriteria(Tramite.class);
			criteria.setFetchMode("unidadeOrganizacionalDestino", FetchMode.JOIN);
			criteria.setFetchMode("motivoTramite", FetchMode.JOIN);
			criteria.setFetchMode("unidadeOrganizacionalOrigem", FetchMode.JOIN);
			criteria.setFetchMode("usuarioResponsavel", FetchMode.JOIN);
			criteria.add(Property.forName("dataTramite").eq(maxData));
			criteria.add(Expression.eq("registroAtendimento.id", idRA));
			criteria.setMaxResults(1);

			retorno = (Tramite) criteria.uniqueResult();

			/*
			 * if (retorno != null) {
			 * Hibernate.initialize(retorno.getUnidadeOrganizacionalDestino());
			 * Hibernate.initialize(retorno.getUnidadeOrganizacionalOrigem());
			 * Hibernate.initialize(retorno.getUsuarioResponsavel()); }
			 */

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programaçao de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 06/09/2006
	 */
	public Tramite recuperarTramiteMaisAtualPorUnidadeDestino(Integer idUnidade) throws ErroRepositorioException{

		Tramite retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			DetachedCriteria maxData = DetachedCriteria.forClass(Tramite.class).setProjection(Property.forName("dataTramite").max())
							.add(Expression.eq("unidadeOrganizacionalDestino.id", idUnidade));

			Criteria criteria = session.createCriteria(Tramite.class);
			criteria.add(Property.forName("dataTramite").eq(maxData));
			criteria.add(Expression.eq("unidadeOrganizacionalDestino.id", idUnidade));
			criteria.setFetchMode("registroAtendimento", FetchMode.JOIN);
			criteria.setMaxResults(1);

			retorno = (Tramite) criteria.uniqueResult();

			/*
			 * if (retorno != null) {
			 * Hibernate.initialize(retorno.getRegistroAtendimento()); }
			 */

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso exista registro de atendimento pendente para o imóvel (existe
	 * ocorrência na tabela REGISTRO_ATENDIMENTO com IMOV_ID=Matrícula do Imóvel
	 * e RGAT_CDSITUACAO=1)
	 * [SB0021] Verifica Existência de Registro de Atendimento Pendente para o
	 * Imóvel
	 * 
	 * @author Raphael Rossiter
	 * @date 31/07/2006
	 * @param idImovel
	 * @return quantidadeRA
	 * @throws ErroRepositorioException
	 */
	public Integer verificaExistenciaRAPendenteImovel(Integer idImovel) throws ErroRepositorioException{

		Integer retorno = 0;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT COUNT(*) " + "FROM RegistroAtendimento rgat " + "INNER JOIN rgat.imovel imov  "
							+ "WHERE imov.id = :idImovel AND rgat.codigoSituacao = 1 ";

			retorno = ((Number) session.createQuery(consulta).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Definir a unidade destino a partir da localidade informada/selecionada
	 * (UNID_ID e UNID_DSUNIDADE da tabela UNIDADE_ORGANIZACIONAL com
	 * UNID_ICTRAMITE=1 e UNID_ID=UNID_ID da tabela LOCALIDADE_SOLIC_TIPO_GRUPO
	 * com LOCA_ID=Id da Localidade e SOTG_ID=SOTG_ID da tabela SOLICITACAO_TIPO
	 * com SOTP_ID=Id do Tipo de Solicitação selecionado).
	 * [SB0005] Define Unidade Destino da Localidade
	 * 
	 * @author Raphael Rossiter
	 * @date 04/08/2006
	 * @param idLocalidade
	 *            ,
	 *            idSolicitacaoTipo
	 * @return UnidadeDestino
	 * @throws ErroRepositorioException
	 */
	public UnidadeOrganizacional definirUnidadeDestinoLocalidade(Integer idLocalidade, Integer idSolicitacaoTipo)
					throws ErroRepositorioException{

		UnidadeOrganizacional retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT lstg.unidadeOrganizacional " + "FROM LocalidadeSolicTipoGrupo lstg, SolicitacaoTipo sotp "
							+ "INNER JOIN lstg.localidade loca  " + "INNER JOIN lstg.unidadeOrganizacional unid  "
							+ "INNER JOIN lstg.solicitacaoTipoGrupo sotg "
							+ "WHERE loca.id = :idLocalidade AND sotp.solicitacaoTipoGrupo.id = sotg.id "
							+ "AND sotp.id = :idSolicitacaoTipo AND unid.indicadorTramite = 1 ";

			retorno = (UnidadeOrganizacional) session.createQuery(consulta).setInteger("idLocalidade", idLocalidade)
							.setInteger("idSolicitacaoTipo", idSolicitacaoTipo).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0406] Filtrar Registro de Atendimento
	 * Caso exista registro de atendimento abertos para a unidade de atendimento
	 * informada (existe ocorrência na tabela REGISTRO_ATENDIMENTO com
	 * REGISTRO_ATENDIMENTO_UNIDADE=Id da Unidade de Atendimento e ATTP_ID=1 -
	 * ABRIR/REGISTRAR)
	 * 
	 * @author Leonardo Regis
	 * @date 07/08/2006
	 * @param unidadeOrganizacional
	 * @return Collection<RegistroAtendimento>
	 * @throws ControladorException
	 */
	public Collection<Integer> recuperaRAPorUnidadeAtendimento(UnidadeOrganizacional unidadeOrganizacional) throws ErroRepositorioException{

		Collection<Integer> retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT rgat.id " + "FROM RegistroAtendimentoUnidade rau " + "INNER JOIN rau.atendimentoRelacaoTipo art  "
							+ "INNER JOIN rau.registroAtendimento rgat  " + "INNER JOIN rau.unidadeOrganizacional unid  "
							+ "WHERE unid.id = :idUnidade AND art.id = :idAtendimentoTipo ";

			retornoConsulta = session.createQuery(consulta).setInteger("idUnidade", unidadeOrganizacional.getId())
							.setInteger("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0406] Filtrar Registro de Atendimento
	 * Caso exista registro de atendimento que estão na unidade atual informada
	 * (existe ocorrência na tabela REGISTRO_ATENDIMENTO com TRAMITE=Id da
	 * Unidade Atual e maior TRAM_TMTRAMITE)
	 * 
	 * @author Leonardo Regis
	 * @date 07/08/2006
	 * @param unidadeOrganizacional
	 * @return Collection<RegistroAtendimento>
	 * @throws ControladorException
	 */
	public Collection<Integer> recuperaRAPorUnidadeAtual(UnidadeOrganizacional unidadeOrganizacional) throws ErroRepositorioException{

		Collection<Integer> retorno = null;
		Collection<Object[]> retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT rgat.id, max(tram.dataTramite) " + "FROM Tramite tram " + "INNER JOIN tram.registroAtendimento rgat  "
							+ "INNER JOIN tram.unidadeOrganizacionalDestino unid  " + "WHERE unid.id = :idUnidade " + "GROUP BY rgat.id";

			retornoConsulta = session.createQuery(consulta).setInteger("idUnidade", unidadeOrganizacional.getId()).list();

			if(!retornoConsulta.isEmpty()){
				retorno = new ArrayList();
				for(Iterator iter = retornoConsulta.iterator(); iter.hasNext();){
					Object[] element = (Object[]) iter.next();
					retorno.add((Integer) element[0]);
				}
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * @author ahgl
	 * @param filtroRA
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> filtrarRAWebService(FiltrarRegistroAtendimentoHelper filtroRA)
					throws ErroRepositorioException{

		Collection<Object[]> retornoConsulta = null;

		Session session = HibernateUtil.getSession();

		SQLQuery query = null;

		try{

			StringBuilder sql = new StringBuilder("SELECT distinct ra.rgat_id as idRA, "); // 0
			sql.append(" ste.step_id as idEspecificacao, "); // 1
			sql.append(" ra.rgat_cdsituacao as codigoSituacaoRA, "); // 2
			sql.append(" ra.RGAT_NNCOORDENADALESTE as coordenadaLeste, "); // 3
			sql.append(" ra.RGAT_NNCOORDENADANORTE as coordenadaNorte, "); // 4
			sql.append(" loca.LOCA_ID as idLocalidade, "); // 5
			sql.append(" sc.stcm_cdsetorcomercial as codSetorComercial, "); // 6
			sql.append(" quadra.qdra_nnquadra as numeroQuadra, "); // 7
			sql.append(" im.IMOV_NNLOTE as numeroLote, "); // 8
			sql.append(" im.IMOV_NNSUBLOTE as numeroSubLote,"); // 9
			sql.append(" sto.sotp_id as codigoSolicitacaoTipo,"); // 10
			sql.append(" sto.sotp_dssolicitacaotipo as descricaoSolicitacaoTipo,"); // 11
			sql.append(" ste.step_dssolicitacaotipoespecifi as dsSolicitacaoTipoEspecifi, "); // 12
			sql.append(" ra.imov_id as imovel "); // 13

			sql.append(" FROM registro_atendimento ra ");
			sql.append(" LEFT JOIN localidade loca on ra.loca_id = loca.loca_id ");
			sql.append(" LEFT JOIN imovel im on ra.imov_id = im.imov_id ");
			sql.append(" LEFT JOIN SETOR_COMERCIAL sc on sc.stcm_id = im.stcm_id ");
			sql.append(" LEFT JOIN QUADRA quadra on quadra.qdra_id = im.qdra_id ");
			sql.append(" LEFT JOIN ordem_servico os on ra.rgat_id = os.rgat_id ");
			sql.append(" INNER JOIN ordem_servico_unidade osu on osu.orse_id = os.orse_id and osu.attp_id = 1 ");
			sql.append(" INNER JOIN solicitacao_tipo_especificacao ste on ra.step_id = ste.step_id  ");
			sql.append(" INNER JOIN solicitacao_tipo sto on sto.sotp_id = ste.sotp_id  ");

			if(Util.isNaoNuloBrancoZero(filtroRA.getUnidadeAtual().getId())
							|| Util.isNaoNuloBrancoZero(filtroRA.getUnidadeExecutora().getId())){
				sql.append(" LEFT JOIN tramite tram on ra.rgat_id = tram.rgat_id ");
			}

			sql.append("WHERE");

			sql.append(" ra.rgat_tmregistroatendimento >= :dataInicial   ");
			sql.append(" AND ra.rgat_tmregistroatendimento <= :dataFinal   ");

			if(Util.isNaoNuloBrancoZero(filtroRA.getUnidadeAtual().getId())){
				sql.append(" AND ( tram.unid_iddestino = " + filtroRA.getUnidadeAtual().getId());
				sql.append("  		AND  tram.tram_id in (select max(tr.tram_id) from tramite tr  where tr.rgat_id = ra.rgat_id) ");
				sql.append("  	 ) ");
			}

			if(Util.isNaoNuloBrancoZero(filtroRA.getUnidadeExecutora().getId())){
				sql.append(" AND ( tram.unid_iddestino = " + filtroRA.getUnidadeExecutora().getId());
				sql.append("  		AND  tram.tram_id in (select max(tr.tram_id) from tramite tr  where tr.rgat_id = ra.rgat_id) ");
				sql.append("  	 ) ");
			}


			if(Util.isNaoNuloBrancoZero(filtroRA.getColecaoSituacoes())){
				sql.append(" and ra.RGAT_CDSITUACAO in (:idsColecaoSituacao)  ");
			}

			if(Util.isNaoNuloBrancoZero(filtroRA.getColecaoTipoSolicitacaoEspecificacao())){
				sql.append(" and ra.STEP_ID in (:idsEspecificacao)  ");
			}

			sql.append(" and ra.rgat_nncoordenadanorte BETWEEN (:norteBaixa) AND (:norteAlta)  ");
			sql.append(" and ra.rgat_nncoordenadaleste BETWEEN (:lesteEsquerda) AND (:lesteDireita)  ");

			query = session.createSQLQuery(sql.toString());

			query.setTimestamp("dataInicial", Util.formatarDataInicial(filtroRA.getDataAtendimentoInicial()));
			query.setTimestamp("dataFinal", Util.formatarDataFinal(filtroRA.getDataAtendimentoFinal()));

			query.setBigDecimal("norteBaixa", filtroRA.getCoordenadaNorteBaixa());
			query.setBigDecimal("norteAlta", filtroRA.getCoordenadaNorteAlta());

			query.setBigDecimal("lesteEsquerda", filtroRA.getCoordenadaLesteEsquerda());
			query.setBigDecimal("lesteDireita", filtroRA.getCoordenadaLesteDireita());

			if(Util.isNaoNuloBrancoZero(filtroRA.getColecaoSituacoes())){
				query.setParameterList("idsColecaoSituacao", filtroRA.getColecaoSituacoes());
			}

			if(Util.isNaoNuloBrancoZero(filtroRA.getColecaoTipoSolicitacaoEspecificacao())){
				query.setParameterList("idsEspecificacao", filtroRA.getColecaoTipoSolicitacaoEspecificacao());
			}

			query.addScalar("idRA", Hibernate.INTEGER);
			query.addScalar("idEspecificacao", Hibernate.INTEGER);
			query.addScalar("codigoSituacaoRA", Hibernate.INTEGER);
			query.addScalar("coordenadaLeste", Hibernate.BIG_DECIMAL);
			query.addScalar("coordenadaNorte", Hibernate.BIG_DECIMAL);
			query.addScalar("idLocalidade", Hibernate.INTEGER);
			query.addScalar("codSetorComercial", Hibernate.INTEGER);
			query.addScalar("numeroQuadra", Hibernate.INTEGER);
			query.addScalar("numeroLote", Hibernate.SHORT);
			query.addScalar("numeroSubLote", Hibernate.SHORT);
			query.addScalar("codigoSolicitacaoTipo", Hibernate.INTEGER);
			query.addScalar("descricaoSolicitacaoTipo", Hibernate.STRING);
			query.addScalar("dsSolicitacaoTipoEspecifi", Hibernate.STRING);
			query.addScalar("imovel", Hibernate.INTEGER);

			retornoConsulta = query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC3096] AcquaGIS Detalhe da RA
	 * 
	 * @return Objeto JSON
	 * @throws ErroRepositorioException
	 */
	public Object[] buscarDetalheRAWebService(Integer numeroRA) throws ErroRepositorioException{

		Object[] retornoConsulta = null;

		Session session = HibernateUtil.getSession();

		SQLQuery query = null;

		try{

			StringBuilder sql = new StringBuilder("select ra.RGAT_ID as idRA,  ");
			sql.append("  ra.RGAT_DSOBSERVACAO as obsercacao, ");
			sql.append("  ra.RGAT_DSPONTOREFERENCIA as pontoReferencia, ");
			sql.append("  b.BAIR_NMBAIRRO as nomeBairro, ");
			sql.append("  ra.STEP_ID as codigoEspecificacao, ");
			sql.append("  ra.RGAT_NNCOORDENADALESTE as coordenadaLeste, ");
			sql.append("  ra.RGAT_NNCOORDENADANORTE as coordenadaNorte, ");
			sql.append("  ra.RGAT_TMREGISTROATENDIMENTO as dataAtendimento, ");
			sql.append("  ste.STEP_DSSOLICITACAOTIPOESPECIFI as descricaoEspecificacao, ");
			sql.append("  sf.SOFO_CDDDD as DDD, ");
			sql.append("  sf.SOFO_NNFONE as telefoneContato, ");
			sql.append("  loca.LOCA_NMLOCALIDADE as nomeLocalidade, ");
			sql.append("  im.imov_id as matricula, ");
			sql.append("  muni.MUNI_NMMUNICIPIO as municipio, ");
			sql.append("  loca.LOCA_ID as idLocalidade,   ");
			sql.append("  sc.stcm_cdsetorcomercial as codSetorComercial,   ");
			sql.append("  quadra.qdra_nnquadra as numeroQuadra,  ");
			sql.append("  im.IMOV_NNLOTE as numeroLote,  ");
			sql.append("  im.IMOV_NNSUBLOTE as numeroSubLote, ");
			sql.append("  ras.CLIE_ID as idCliente, ");
			sql.append("  (select cliente.CLIE_NMCLIENTE from Cliente cliente where cliente.CLIE_ID = ras.CLIE_ID and RASO_ICSOLICITANTEPRINCIPAL = 1 ) as nomeCliente, ");
			sql.append(" sto.sotp_id as codigoSolicitacaoTipo,");
			sql.append(" sto.sotp_dssolicitacaotipo as descricaoSolicitacaoTipo ");

			sql.append("   FROM REGISTRO_ATENDIMENTO ra   ");
			sql.append("   left join BAIRRO_AREA ba on ba.BRAR_ID = ra.BRAR_ID  ");
			sql.append("   left join BAIRRO b on b.BAIR_ID = ba.BAIR_ID  ");
			sql.append("   left join SOLICITACAO_TIPO_ESPECIFICACAO ste on ste.STEP_ID = ra.STEP_ID  ");
			sql.append("   left join REGISTRO_ATENDIMENTO_SOLCTT ras on ras.RGAT_ID = ra.RGAT_ID  ");
			sql.append("   left join SOLICITANTE_FONE sf on sf.RASO_ID = ras.RASO_ID   ");
			sql.append("   LEFT JOIN localidade loca on ra.loca_id = loca.loca_id   ");
			sql.append("   LEFT JOIN imovel im on ra.imov_id = im.imov_id   ");
			sql.append("   LEFT JOIN SETOR_COMERCIAL sc on sc.stcm_id = ra.stcm_id   ");
			sql.append("   LEFT JOIN QUADRA quadra on quadra.qdra_id = ra.qdra_id   ");
			sql.append("   left join MUNICIPIO muni on muni.MUNI_ID = b.MUNI_ID  ");
			sql.append("   LEFT JOIN solicitacao_tipo sto on sto.sotp_id = ste.sotp_id  ");

			sql.append("  WHERE  ");

			sql.append(" ra.rgat_id = " + numeroRA);
			query = session.createSQLQuery(sql.toString());

			query.addScalar("idRA", Hibernate.INTEGER); // 0
			query.addScalar("obsercacao", Hibernate.STRING); // 1
			query.addScalar("pontoReferencia", Hibernate.STRING); // 2
			query.addScalar("nomeBairro", Hibernate.STRING); // 3

			query.addScalar("codigoEspecificacao", Hibernate.INTEGER); // 4
			query.addScalar("coordenadaLeste", Hibernate.BIG_DECIMAL); // 5
			query.addScalar("coordenadaNorte", Hibernate.BIG_DECIMAL); // 6

			query.addScalar("dataAtendimento", Hibernate.TIMESTAMP); // 7
			query.addScalar("descricaoEspecificacao", Hibernate.STRING); // 8
			query.addScalar("DDD", Hibernate.SHORT);// 9
			query.addScalar("telefoneContato", Hibernate.STRING); // 10
			query.addScalar("nomeLocalidade", Hibernate.STRING); // 11
			query.addScalar("matricula", Hibernate.INTEGER); // 12

			query.addScalar("municipio", Hibernate.STRING); // 13
			query.addScalar("idLocalidade", Hibernate.INTEGER); // 14
			query.addScalar("codSetorComercial", Hibernate.INTEGER); // 15
			query.addScalar("numeroQuadra", Hibernate.INTEGER); // 16
			query.addScalar("numeroLote", Hibernate.SHORT); // 17
			query.addScalar("numeroSubLote", Hibernate.SHORT); // 18

			query.addScalar("idCliente", Hibernate.INTEGER); // 19
			query.addScalar("nomeCliente", Hibernate.STRING); // 20

			query.addScalar("codigoSolicitacaoTipo", Hibernate.INTEGER); // 21
			query.addScalar("descricaoSolicitacaoTipo", Hibernate.STRING); // 22

			retornoConsulta = (Object[]) query.uniqueResult();


		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0406] Filtrar Registro de Atendimento
	 * [SB004] Selecionar Registro de Atendimento por Município [SB005]
	 * Selecionar Registro de Atendimento por Bairro [SB006] Selecionar Registro
	 * de Atendimento por Logradouro
	 * 
	 * @author Leonardo Regis
	 * @date 07/08/2006
	 * @author eduardo henrique
	 * @date 10/03/2009 - Inclusão de filtros de Datas de Previsão Inicial e Final de Atendimento
	 * @author Andre Nishimura
	 * @date 27/04/2009 - Remoçao de clausula GroupBy de consulta sem funçao agregadora.
	 * @author Ailton Sousa
	 * @date 11/04/2011 - Acréscimo de Novas Opções no Filtro - Situações: Reativados e Reiterados
	 * @param filtroRA
	 * @return Collection<RegistroAtendimento>
	 * @throws ErroRepositorioException
	 */
	public Collection<RegistroAtendimento> filtrarRA(FiltrarRegistroAtendimentoHelper filtroRA) throws ErroRepositorioException{

		Collection<RegistroAtendimento> retorno = null;
		Collection<Object[]> retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();

		String consulta = "";
		Query query = null;
		Map parameters = new HashMap();
		try{

			consulta = "SELECT distinct ra.rgat_id as idRA, ste.step_id as idEspecificacao, "
							+ "ste.step_dssolicitacaotipoespecifi as descricaoEspecificacao, "
							+ "ra.rgat_tmregistroatendimento as dataRA, ra.rgat_tmencerramento as dataEncerramentoRA, "
							+ "ra.rgat_cdsituacao as codigoSituacaoRA, loca.loca_nmlocalidade as descricaoLocalidade, "
							+ "ra.rgat_dtprevistaatual as dataPrevistaRA, ra.rgat_idreativacao as idReativacao, "
							+ "usuario.usur_nmlogin as nomeUsuario, usuario.usur_id as idUsuario, "
							+ "ra.rgat_qtreiteracoes as qtReiteracoes, ra.rgat_tmultimareiteracao as dtReiteracao, "
							+ "(select count(*) from guia_pagamento gp  where gp.rgat_id = ra.rgat_id) as qtd_guias_pendentes "
							+ "FROM registro_atendimento ra " + "LEFT JOIN localidade loca on ra.loca_id = loca.loca_id "
							+ "LEFT JOIN imovel im on ra.imov_id = im.imov_id "
							+ "LEFT JOIN logradouro_bairro lb on im.lgbr_id = lb.lgbr_id "
							+ "LEFT JOIN logradouro lo on lb.logr_id = lo.logr_id " + "LEFT JOIN bairro ba on lb.bair_id = ba.bair_id "
							+ "LEFT JOIN municipio mu on  ba.muni_id = mu.muni_id "
							+ "LEFT JOIN logradouro_bairro lbra on ra.lgbr_id = lbra.lgbr_id "
							+ "LEFT JOIN bairro bara on lbra.bair_id = bara.bair_id "
							+ "LEFT JOIN logradouro lora on lbra.logr_id = lora.logr_id "
							+ "LEFT JOIN municipio mura on  bara.muni_id = mura.muni_id "
							+ "LEFT JOIN registro_atendimento_unidade rau on ra.rgat_id = rau.rgat_id "
							+ "LEFT JOIN usuario usuario on usuario.usur_id = rau.usur_id "
							+ "LEFT JOIN bairro_area baa on ra.brar_id = baa.brar_id "
							+ "LEFT JOIN bairro barea on baa.bair_id = barea.bair_id "
							+ "LEFT JOIN municipio muarea on  barea.muni_id = muarea.muni_id ";

			// Unidade Atual ou Unidade Superior
			if((filtroRA.getUnidadeAtual() != null || filtroRA.getUnidadeSuperior() != null)
							|| (filtroRA.getDataTramiteInicial() != null && filtroRA.getDataTramiteFinal() != null)){

				consulta += "LEFT JOIN tramite tram on ra.rgat_id = tram.rgat_id ";
			}

			// Motivo de Reativação
			if(filtroRA.getRegistroAtendimento() != null
							&& filtroRA.getRegistroAtendimento().getRaMotivoReativacao() != null
							&& filtroRA.getRegistroAtendimento().getRaMotivoReativacao().getId() != null
							&& filtroRA.getRegistroAtendimento().getRaMotivoReativacao().getId().intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){

				consulta += "LEFT JOIN ra_motivo_reativacao ramor on ra.rmrv_id = ramor.rmrv_id ";
			}

			// Cpf, CNPJ, Nome Solicitante ou Codigo Cliente
			if((filtroRA.getCpf() != null && !filtroRA.getCpf().equals(""))
							|| (filtroRA.getCnpj() != null && !filtroRA.getCnpj().equals(""))
							|| (filtroRA.getNomeSolicitante() != null && !filtroRA.getNomeSolicitante().equals(""))
							|| (filtroRA.getCodigoCliente() != null && !filtroRA.getCodigoCliente().equals(""))){

				consulta += "LEFT JOIN registro_atendimento_solctt ras on ra.rgat_id = ras.rgat_id "
								+ "LEFT JOIN cliente cli on cli.clie_id = ras.clie_id ";
			}

			// Gerada pela Unidade Atual
			if(!Util.isVazioOuBranco(filtroRA.getIndicadorGeradaPelaUnidadeAtual())){
				if(!filtroRA.getIndicadorGeradaPelaUnidadeAtual().equals(ConstantesSistema.TODOS.toString())){
					consulta += "INNER JOIN ordem_servico os on ra.rgat_id = os.rgat_id "
									+ "INNER JOIN ordem_servico_unidade osu on osu.orse_id = os.orse_id and osu.attp_id = 1 ";
				}
			}

			// Tipo da Solicitaçao e Especificação
			consulta += "INNER JOIN solicitacao_tipo_especificacao ste on ra.step_id = ste.step_id "
							+ "INNER JOIN solicitacao_tipo st on ste.sotp_id = st.sotp_id " + "WHERE 1=1 ";
			// Número do RA
			if(filtroRA.getRegistroAtendimento().getId() != null){
				consulta += " AND ra.rgat_id = (:idRA) ";
				parameters.put("idRA", filtroRA.getRegistroAtendimento().getId());
			}

			// Número manual
			if(filtroRA.getRegistroAtendimento().getManual() != null){
				consulta += " AND ra.rgat_idmanual = (:manual) ";
				parameters.put("manual", filtroRA.getRegistroAtendimento().getManual());
			}

			// Matrícula do Imóvel
			if(filtroRA.getRegistroAtendimento().getImovel() != null){
				consulta += " AND im.imov_id = (:idImovel) ";
				parameters.put("idImovel", filtroRA.getRegistroAtendimento().getImovel().getId());
			}

			// Situação
			if(Short.valueOf(filtroRA.getRegistroAtendimento().getCodigoSituacao()).intValue() != RegistroAtendimento.SITUACAO_TODOS){

				// Caso seja igual a "500", é para consultar pelos RAs Reativados.
				// Caso seja igual a "501", é para consultar pelos RAs Reiterados.
				if(Short.valueOf(filtroRA.getRegistroAtendimento().getCodigoSituacao()).equals(Short.valueOf("500"))){

					consulta += " AND ra.rgat_idreativacao IS NOT NULL ";

					// Motivo de Reativação
					if(filtroRA.getRegistroAtendimento().getRaMotivoReativacao().getId() != null
									&& filtroRA.getRegistroAtendimento().getRaMotivoReativacao().getId().intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){

						consulta += " AND ra.rmrv_id = (:idMotivoReativacao) ";
						parameters.put("idMotivoReativacao", filtroRA.getRegistroAtendimento().getRaMotivoReativacao().getId());
					}
					// Matrícula do Atendente
					if(filtroRA.getMatriculaAtendenteId() != null){

						consulta += " AND usuario.usur_id = (:idAtendente) ";
						parameters.put("idAtendente", Integer.valueOf(filtroRA.getMatriculaAtendenteId()));
					}

				}else if(Short.valueOf(filtroRA.getRegistroAtendimento().getCodigoSituacao()).equals(Short.valueOf("501"))){

					consulta += "AND ra.rgat_cdsituacao = 1 AND ra.rgat_qtreiteracoes IS NOT NULL";
				}else{

					consulta += " AND ra.rgat_cdsituacao = (:idSituacao) ";
					parameters.put("idSituacao", filtroRA.getRegistroAtendimento().getCodigoSituacao());
				}
			}

			// Ordem de Serviço Gerada
			if(!Util.isVazioOuBranco(filtroRA.getIndicadorOrdemServicoGerada())){
				if(!filtroRA.getIndicadorOrdemServicoGerada().equals(ConstantesSistema.TODOS.toString())){
					if(filtroRA.getIndicadorOrdemServicoGerada().equals(ConstantesSistema.SIM.toString())){
						consulta += " AND exists (select * from ordem_servico os where os.rgat_id = ra.rgat_id)";
					}else{
						consulta += " AND not exists (select * from ordem_servico os where os.rgat_id = ra.rgat_id)";
					}
				}
			}

			// Gerada pela Unidade Atual
			if(!Util.isVazioOuBranco(filtroRA.getIndicadorGeradaPelaUnidadeAtual())){
				if(!filtroRA.getIndicadorGeradaPelaUnidadeAtual().equals(ConstantesSistema.TODOS.toString())){
					if(filtroRA.getIndicadorGeradaPelaUnidadeAtual().equals(ConstantesSistema.SIM.toString())){
						consulta += " AND osu.unid_id = (:idUnidadeAtual) ";
					}else{
						consulta += " AND osu.unid_id <> (:idUnidadeAtual) ";
					}
					parameters.put("idUnidadeAtual", filtroRA.getUnidadeAtual().getId());
				}
			}

			// Indicador Processo Administrativo / Judicial
			if(!Util.isVazioOuBranco(filtroRA.getIndicadorProcessoAdmJud())){
				if(!filtroRA.getIndicadorProcessoAdmJud().equals(ConstantesSistema.TODOS.toString())){
					if(filtroRA.getIndicadorProcessoAdmJud().equals(ConstantesSistema.SIM.toString())){
						consulta += " AND ra.rgat_icprocessoadmjud = " + ConstantesSistema.SIM;
					}else{
						consulta += " AND ra.rgat_icprocessoadmjud = " + ConstantesSistema.NAO;
					}
				}
			}

			// Especificação
			if(filtroRA.getColecaoTipoSolicitacaoEspecificacao() != null && !filtroRA.getColecaoTipoSolicitacaoEspecificacao().isEmpty()){

				consulta += " AND ste.step_id in (:idSolicitacaoTipoEspecificacao) ";
				parameters.put("idSolicitacaoTipoEspecificacao", filtroRA.getColecaoTipoSolicitacaoEspecificacao());
			}

			// Tipo de Solicitação
			if(filtroRA.getColecaoTipoSolicitacao() != null && !filtroRA.getColecaoTipoSolicitacao().isEmpty()){

				consulta += " AND st.sotp_id in (:idSolicitacaoTipo) ";
				parameters.put("idSolicitacaoTipo", filtroRA.getColecaoTipoSolicitacao());
			}

			// Período de Atendimento
			if(filtroRA.getDataAtendimentoInicial() != null && filtroRA.getDataAtendimentoFinal() != null){

				consulta += " AND ra.rgat_tmregistroatendimento BETWEEN (:dataAtendimentoInicial) AND (:dataAtendimentoFinal) ";
				parameters.put("dataAtendimentoInicial", filtroRA.getDataAtendimentoInicial());
				parameters.put("dataAtendimentoFinal", filtroRA.getDataAtendimentoFinal());
			}

			// Período de Encerramento
			if(filtroRA.getDataEncerramentoInicial() != null && filtroRA.getDataEncerramentoFinal() != null){

				consulta += " AND ra.rgat_tmencerramento BETWEEN (:dataEncerramentoInicial) AND (:dataEncerramentoFinal) ";
				parameters.put("dataEncerramentoInicial", filtroRA.getDataEncerramentoInicial());
				parameters.put("dataEncerramentoFinal", filtroRA.getDataEncerramentoFinal());
			}

			// Período de Previsão de Atendimento
			if(filtroRA.getDataPrevisaoAtendimentoInicial() != null && filtroRA.getDataPrevisaoAtendimentoFinal() != null){

				consulta += " AND ra.rgat_dtprevistaatual BETWEEN (:dataPrevistaAtendimentoInicial) AND (:dataPrevistaAtendimentoFinal) ";
				parameters.put("dataPrevistaAtendimentoInicial", filtroRA.getDataPrevisaoAtendimentoInicial());
				parameters.put("dataPrevistaAtendimentoFinal", filtroRA.getDataPrevisaoAtendimentoFinal());
			}

			// Período de Tramite
			if(filtroRA.getDataTramiteInicial() != null && filtroRA.getDataTramiteFinal() != null){

				consulta += " AND tram.tram_tmtramite BETWEEN (:dataTramiteInicial) AND (:dataTramiteFinal) ";
				parameters.put("dataTramiteInicial", filtroRA.getDataTramiteInicial());
				parameters.put("dataTramiteFinal", filtroRA.getDataTramiteFinal());
			}

			// [SB004] Selecionar Registro de Atendimento por Município
			if(filtroRA.getMunicipioId() != null && !filtroRA.getMunicipioId().equals("")){
				// 1.3 Município por Bairro Área
				consulta += " AND (muarea.muni_id = :municipioId ";
				// 1.2 Município por Imóvel
				consulta += " OR mu.muni_id = :municipioId ";
				// 1.1 Município por Logradouro Bairro
				consulta += " OR mura.muni_id = :municipioId) ";
				parameters.put("municipioId", Integer.valueOf(filtroRA.getMunicipioId()));
			}

			// [SB005] Selecionar Registro de Atendimento por Bairro
			if(filtroRA.getBairroCodigo() != null && !filtroRA.getBairroCodigo().equals("")){

				// 1.3 Bairro por Bairro Área
				consulta += " AND (barea.bair_cdbairro = :bairroCodigo ";
				// 1.2 Bairro por Imóvel
				consulta += " OR ba.bair_cdbairro = :bairroCodigo ";
				// 1.1 Bairro por Logradouro Bairro
				consulta += " OR bara.bair_cdbairro = :bairroCodigo) ";

				parameters.put("bairroCodigo", Integer.valueOf(filtroRA.getBairroCodigo()));

				if(filtroRA.getRegistroAtendimento().getBairroArea() != null){

					if(filtroRA.getRegistroAtendimento().getBairroArea().getId().intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){

						consulta += " AND baa.brar_id = :bairroAreaId ";
						parameters.put("bairroAreaId", filtroRA.getRegistroAtendimento().getBairroArea().getId());
					}
				}
			}

			// [SB006] Selecionar Registro de Atendimento por Logradouro
			if(filtroRA.getLogradouroId() != null && !filtroRA.getLogradouroId().equals("")){
				// 1.2 Logradouro por Imóvel
				consulta += " AND (lo.logr_id = :logradouroId ";
				// 1.1 Logradouro por Logradouro Bairro
				consulta += " OR lora.logr_id = :logradouroId) ";
				parameters.put("logradouroId", Integer.valueOf(filtroRA.getLogradouroId()));
			}

			// Unidade de Atendimento
			if(filtroRA.getUnidadeAtendimento() != null){

				consulta += " AND rau.unid_id = (:idUnidadeAtendimento) ";
				consulta += " AND rau.attp_id = (:idAtendimentoTipo) ";
				parameters.put("idUnidadeAtendimento", filtroRA.getUnidadeAtendimento().getId());
				parameters.put("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR);
			}else{
				consulta += " AND rau.attp_id = (:idAtendimentoTipo) ";
				parameters.put("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR);
			}

			// (Usuário)
			if(filtroRA.getRegistroAtendimentoUnidade() != null){

				consulta += " AND usuario.usur_nmlogin = (:loginUsuario) ";
				parameters.put("loginUsuario", filtroRA.getRegistroAtendimentoUnidade().getUsuario().getLogin());
			}

			// UnidadeAtual
			if(filtroRA.getUnidadeAtual() != null
							&& (filtroRA.getIndicadorGeradaPelaUnidadeAtual() == null || filtroRA.getIndicadorGeradaPelaUnidadeAtual()
											.equals(ConstantesSistema.TODOS.toString()))){

				consulta += " AND tram.unid_iddestino = (:idUnidadeAtual) "
								+ " AND tram.tram_id in (select max(tr.tram_id) from tramite tr  " + " where tr.rgat_id = ra.rgat_id)";

				parameters.put("idUnidadeAtual", filtroRA.getUnidadeAtual().getId());
			}

			// UnidadeSuperior
			if(filtroRA.getUnidadeSuperior() != null){

				consulta += " AND tram.unid_iddestino = (:idUnidadeSuperior) ";
				parameters.put("idUnidadeSuperior", filtroRA.getUnidadeSuperior().getId());
			}

			// Nome Solicitante
			if(filtroRA.getNomeSolicitante() != null && !filtroRA.getNomeSolicitante().equals("")){

				consulta += " AND UPPER(ras.raso_nmsolicitante) like (:nomeSolicitante) ";
				if(filtroRA.isContem()){

					parameters.put("nomeSolicitante", "%" + filtroRA.getNomeSolicitante().toUpperCase() + "%");
				}else if(filtroRA.isIniciado()){

					parameters.put("nomeSolicitante", filtroRA.getNomeSolicitante().toUpperCase() + "%");
				}
			}

			// Codigo Cliente
			if(filtroRA.getCodigoCliente() != null && !filtroRA.getCodigoCliente().equals("")){

				consulta += " AND ras.clie_id = (:idCliente) ";

				parameters.put("idCliente", filtroRA.getCodigoCliente());
			}

			// Cpf cliente
			if(filtroRA.getCpf() != null && !filtroRA.getCpf().equals("")){

				consulta += " AND cli.clie_nncpf = (:cpf)";
				parameters.put("cpf", filtroRA.getCpf());
			}

			// Cnpj cliente
			if(filtroRA.getCnpj() != null && !filtroRA.getCnpj().equals("")){

				consulta += " AND cli.clie_nncnpj = (:cnpj)";
				parameters.put("cnpj", filtroRA.getCnpj());
			}

			if(filtroRA.getIndicadorRaVencidas() != null && !filtroRA.getIndicadorRaVencidas().equals(ConstantesSistema.TODOS.toString())){
				if(filtroRA.getIndicadorRaVencidas().equals(ConstantesSistema.SIM.toString())){
					consulta += " AND (exists (select * from guia_pagamento gp , guia_pagamento_prestacao gpp where gp.gpag_id = gpp.gpag_id and gp.rgat_id = ra.rgat_id and gppr_dtvencimento < :dataAtual)";
					consulta += "  OR  exists (select * from guia_pagamento_historico gph , guia_pagamento_prestacao_hist gpph where gph.gpag_id = gpph.gpag_id and gph.rgat_id = ra.rgat_id and gpph_dtvencimento < :dataAtual))";
				}else{
					consulta += " AND (exists (select * from guia_pagamento gp , guia_pagamento_prestacao gpp where gp.gpag_id = gpp.gpag_id and gp.rgat_id = ra.rgat_id and gppr_dtvencimento >= :dataAtual)";
					consulta += "  OR  exists (select * from guia_pagamento_historico gph , guia_pagamento_prestacao_hist gpph where gph.gpag_id = gpph.gpag_id and gph.rgat_id = ra.rgat_id and gpph_dtvencimento >= :dataAtual))";
				}

				SimpleDateFormat formatData = new SimpleDateFormat("dd-MM-yyyy");
				String strDate = formatData.format(new Date());
				Date dataAtual;
				try{
					dataAtual = formatData.parse(strDate);
				}catch(ParseException e){
					throw new ErroRepositorioException(e);
				}

				parameters.put("dataAtual", dataAtual);
			}

			if(filtroRA.getIndicadorRaPagamento() != null && !filtroRA.getIndicadorRaPagamento().equals(ConstantesSistema.TODOS.toString())){
				if(filtroRA.getIndicadorRaPagamento().equals(ConstantesSistema.SIM.toString())){
					consulta += " AND (exists (select * from guia_pagamento gp , pagamento pg where gp.gpag_id = pg.gpag_id and gp.rgat_id = ra.rgat_id)";
					consulta += "  OR  exists (select * from guia_pagamento gp , pagamento_historico pgh where gp.gpag_id = pgh.gpag_id and gp.rgat_id = ra.rgat_id)";
					consulta += "  OR  exists (select * from guia_pagamento_historico gph , pagamento pg where gph.gpag_id = pg.gpag_id and gph.rgat_id = ra.rgat_id)";
					consulta += "  OR  exists (select * from guia_pagamento_historico gph , pagamento_historico pgh where gph.gpag_id = pgh.gpag_id and gph.rgat_id = ra.rgat_id))";
				}else{
					consulta += " AND (not exists (select * from guia_pagamento gp , pagamento pg where gp.gpag_id = pg.gpag_id and gp.rgat_id = ra.rgat_id)";
					consulta += "  AND not exists (select * from guia_pagamento gp , pagamento_historico pgh where gp.gpag_id = pgh.gpag_id and gp.rgat_id = ra.rgat_id)";
					consulta += "  AND not exists (select * from guia_pagamento_historico gph , pagamento pg where gph.gpag_id = pg.gpag_id and gph.rgat_id = ra.rgat_id)";
					consulta += "  AND not exists (select * from guia_pagamento_historico gph , pagamento_historico pgh where gph.gpag_id = pgh.gpag_id and gph.rgat_id = ra.rgat_id))";
				}
			}

			if(filtroRA.getIndicadorRaDevolucao() != null && !filtroRA.getIndicadorRaDevolucao().equals(ConstantesSistema.TODOS.toString())){
				if(filtroRA.getIndicadorRaDevolucao().equals(ConstantesSistema.SIM.toString())){
					consulta += " AND (exists (select * from credito_a_realizar cr where cr.rgat_id = ra.rgat_id)";
					consulta += "  OR  exists (select * from credito_a_realizar_historico crh where crh.rgat_id = ra.rgat_id)";
					consulta += "  OR  exists (select * from guia_devolucao gdv where gdv.rgat_id = ra.rgat_id))";
				}else{
					consulta += " AND (not exists (select * from credito_a_realizar cr where cr.rgat_id = ra.rgat_id)";
					consulta += "  AND not exists (select * from credito_a_realizar_historico crh where crh.rgat_id = ra.rgat_id)";
					consulta += "  AND not exists (select * from guia_devolucao gdv where gdv.rgat_id = ra.rgat_id))";
				}
			}

			consulta += " ORDER BY ra.rgat_id ";

			query = session.createSQLQuery(consulta).addScalar("idRA", Hibernate.INTEGER).addScalar("idEspecificacao", Hibernate.INTEGER)
							.addScalar("descricaoEspecificacao", Hibernate.STRING).addScalar("dataRA", Hibernate.TIMESTAMP)
							.addScalar("dataEncerramentoRA", Hibernate.TIMESTAMP).addScalar("codigoSituacaoRA", Hibernate.SHORT)
							.addScalar("descricaoLocalidade", Hibernate.STRING).addScalar("dataPrevistaRA", Hibernate.TIMESTAMP)
							.addScalar("idReativacao", Hibernate.INTEGER).addScalar("nomeUsuario", Hibernate.STRING)
							.addScalar("idUsuario", Hibernate.INTEGER).addScalar("qtReiteracoes", Hibernate.SHORT)
							.addScalar("dtReiteracao", Hibernate.TIMESTAMP).addScalar("qtd_guias_pendentes", Hibernate.INTEGER);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){

				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Set){

					Set setList = (HashSet) parameters.get(key);
					query.setParameterList(key, setList);
				}else if(parameters.get(key) instanceof Collection){

					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof Date){

					Date data = (Date) parameters.get(key);
					query.setTimestamp(key, data);
				}else{

					query.setParameter(key, parameters.get(key));
				}

			}

			if(filtroRA.getNumeroPagina().intValue() == -1){
				retornoConsulta = query.list();
			}else{
				retornoConsulta = query.setFirstResult(10 * filtroRA.getNumeroPagina()).setMaxResults(10).list();
			}
			if(!retornoConsulta.isEmpty()){
				retorno = new ArrayList();
				RegistroAtendimento ra = null;
				SolicitacaoTipoEspecificacao step = null;
				Localidade loca = null;
				RegistroAtendimento raAuxiliar = null;
				RegistroAtendimentoUnidade raUnidade = null;
				Usuario usuario = null;
				for(Iterator iter = retornoConsulta.iterator(); iter.hasNext();){
					Object[] element = (Object[]) iter.next();
					ra = new RegistroAtendimento();
					ra.setId((Integer) element[0]);
					step = new SolicitacaoTipoEspecificacao();
					step.setId((Integer) element[1]);
					step.setDescricao((String) element[2]);
					ra.setSolicitacaoTipoEspecificacao(step);
					ra.setRegistroAtendimento((Date) element[3]);
					ra.setDataEncerramento((Date) element[4]);
					ra.setCodigoSituacao(((Short) element[5]).shortValue());
					loca = new Localidade();
					loca.setDescricao((String) element[6]);
					ra.setLocalidade(loca);
					ra.setDataPrevistaAtual((Date) element[7]);
					raAuxiliar = new RegistroAtendimento();
					raAuxiliar.setId((Integer) element[8]);
					ra.setRegistroAtendimentoReativacao(raAuxiliar);
					usuario = new Usuario();
					usuario.setLogin((String) element[9]);
					usuario.setId((Integer) element[10]);
					raUnidade = new RegistroAtendimentoUnidade();
					raUnidade.setRegistroAtendimento(raAuxiliar);
					raUnidade.setUsuario(usuario);
					ra.setRegistroAtendimentoUnidade(raUnidade);
					ra.setQuantidadeReiteracao((Short) element[11]);
					ra.setUltimaReiteracao((Date) element[12]);
					ra.setQuantidadeGuiasPendentes((Integer) element[13]);
					retorno.add(ra);
				}
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0406] Filtrar Registro de Atendimento
	 * [SB004] Selecionar Registro de Atendimento por Município [SB005] Selecionar Registro de
	 * Atendimento por Bairro [SB006] Selecionar Registro de
	 * Atendimento por Logradouro
	 * 
	 * @author Leonardo Regis
	 * @date 07/08/2006
	 * @param filtroRA
	 * @return Collection<RegistroAtendimento>
	 * @throws ErroRepositorioException
	 */
	public Integer filtrarRATamanho(FiltrarRegistroAtendimentoHelper filtroRA) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";
		Query query = null;
		Map parameters = new HashMap();
		try{

			if(filtroRA.getUnidadeAtual() == null && filtroRA.getUnidadeSuperior() == null){
				consulta = "SELECT count(distinct ra.rgat_id)  as tamanho " + "FROM registro_atendimento ra "
								+ "LEFT JOIN localidade loca on ra.loca_id = loca.loca_id ";
			}else{

				consulta = "SELECT count(distinct ra.rgat_id) as tamanho " + "FROM registro_atendimento ra "
								+ "LEFT JOIN localidade loca on ra.loca_id = loca.loca_id "
								+ "LEFT JOIN tramite tram on ra.rgat_id = tram.rgat_id ";
			}

			consulta = consulta + "LEFT JOIN imovel im on ra.imov_id = im.imov_id "
							+ "LEFT JOIN logradouro_bairro lb on im.lgbr_id = lb.lgbr_id "
							+ "LEFT JOIN logradouro lo on lb.logr_id = lo.logr_id " + "LEFT JOIN bairro ba on lb.bair_id = ba.bair_id "
							+ "LEFT JOIN municipio mu on  ba.muni_id = mu.muni_id ";

			consulta = consulta + "LEFT JOIN logradouro_bairro lbra on ra.lgbr_id = lbra.lgbr_id "
							+ "LEFT JOIN bairro bara on lbra.bair_id = bara.bair_id "
							+ "LEFT JOIN logradouro lora on lbra.logr_id = lora.logr_id "
							+ "LEFT JOIN municipio mura on  bara.muni_id = mura.muni_id ";

			// Registro Atendimento Unidade
			if(filtroRA.getUnidadeAtendimento() != null || filtroRA.getRegistroAtendimentoUnidade() != null){
				consulta = consulta + "LEFT JOIN registro_atendimento_unidade rau on ra.rgat_id = rau.rgat_id ";
			}

			// Área do Bairro
			consulta = consulta + "LEFT JOIN bairro_area baa on ra.brar_id = baa.brar_id "
							+ "LEFT JOIN bairro barea on baa.bair_id = barea.bair_id "
							+ "LEFT JOIN municipio muarea on  barea.muni_id = muarea.muni_id ";

			// Tipo da Solicitaçao e Especificação
			consulta = consulta + "INNER JOIN solicitacao_tipo_especificacao ste on ra.step_id = ste.step_id "
							+ "INNER JOIN solicitacao_tipo st on ste.sotp_id = st.sotp_id " + "WHERE 1=1 ";

			if(filtroRA.getRegistroAtendimento().getId() != null){
				consulta += " AND ra.rgat_id = (:idRA) ";
				parameters.put("idRA", filtroRA.getRegistroAtendimento().getId());
			}
			if(filtroRA.getRegistroAtendimento().getManual() != null){
				consulta += " AND ra.rgat_idmanual = (:manual) ";
				parameters.put("manual", filtroRA.getRegistroAtendimento().getManual());
			}
			if(filtroRA.getRegistroAtendimento().getImovel() != null){
				consulta += " AND im.imov_id = (:idImovel) ";
				parameters.put("idImovel", filtroRA.getRegistroAtendimento().getImovel().getId());
			}
			if(Short.valueOf(filtroRA.getRegistroAtendimento().getCodigoSituacao()).intValue() != RegistroAtendimento.SITUACAO_TODOS){
				consulta += " AND ra.rgat_cdsituacao = (:idSituacao) ";
				parameters.put("idSituacao", filtroRA.getRegistroAtendimento().getCodigoSituacao());
			}

			if(filtroRA.getColecaoTipoSolicitacaoEspecificacao() != null && !filtroRA.getColecaoTipoSolicitacaoEspecificacao().isEmpty()){
				consulta += " AND ste.step_id in (:idSolicitacaoTipoEspecificacao) ";
				parameters.put("idSolicitacaoTipoEspecificacao", filtroRA.getColecaoTipoSolicitacaoEspecificacao());
			}
			// ------------------------------------------------------

			if(filtroRA.getColecaoTipoSolicitacao() != null && !filtroRA.getColecaoTipoSolicitacao().isEmpty()){
				consulta += " AND st.sotp_id in (:idSolicitacaoTipo) ";
				parameters.put("idSolicitacaoTipo", filtroRA.getColecaoTipoSolicitacao());
			}

			// ------------------------------------------------------

			if(filtroRA.getDataAtendimentoInicial() != null && filtroRA.getDataAtendimentoFinal() != null){
				consulta += " AND ra.rgat_tmregistroatendimento BETWEEN (:dataInicial) AND (:dataFinal) ";
				parameters.put("dataInicial", filtroRA.getDataAtendimentoInicial());
				parameters.put("dataFinal", filtroRA.getDataAtendimentoFinal());
			}
			if(filtroRA.getDataEncerramentoInicial() != null && filtroRA.getDataEncerramentoFinal() != null){
				consulta += " AND ra.rgat_tmencerramento BETWEEN (:dataInicial) AND (:dataFinal) ";
				parameters.put("dataInicial", filtroRA.getDataEncerramentoInicial());
				parameters.put("dataFinal", filtroRA.getDataEncerramentoFinal());
			}
			// [SB004] Selecionar Registro de Atendimento por Município
			if(filtroRA.getMunicipioId() != null && !filtroRA.getMunicipioId().equals("")){
				// 1.3 Município por Bairro Área
				consulta += " AND (muarea.muni_id = :municipioId ";
				// 1.2 Município por Imóvel
				consulta += " OR mu.muni_id = :municipioId ";
				// 1.1 Município por Logradouro Bairro
				consulta += " OR mura.muni_id = :municipioId) ";
				parameters.put("municipioId", Integer.valueOf(filtroRA.getMunicipioId()));
			}

			// [SB005] Selecionar Registro de Atendimento por Bairro
			if(filtroRA.getBairroId() == null || filtroRA.getBairroId().equals("")){
				// Caso venha informado apenas o codigo do bairro
				if(filtroRA.getBairroCodigo() != null && !filtroRA.getBairroCodigo().equals("")){

					// 1.3 Bairro por Bairro Área
					consulta += " AND (barea.bair_cdbairro = :bairroCodigo ";
					// 1.2 Bairro por Imóvel
					consulta += " OR ba.bair_cdbairro = :bairroCodigo ";
					// 1.1 Bairro por Logradouro Bairro
					consulta += " OR bara.bair_cdbairro = :bairroCodigo) ";

					parameters.put("bairroCodigo", Integer.valueOf(filtroRA.getBairroCodigo()));

					if(filtroRA.getRegistroAtendimento().getBairroArea() != null){
						if(filtroRA.getRegistroAtendimento().getBairroArea().getId().intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
							consulta += " AND baa.brar_id = :bairroAreaId ";
							parameters.put("bairroAreaId", filtroRA.getRegistroAtendimento().getBairroArea().getId());
						}
					}
				}
			}else{
				// 1.3 Bairro por Bairro Área
				consulta += " AND (barea.bair_id = :bairroId ";
				// 1.2 Bairro por Imóvel
				consulta += " OR ba.bair_id = :bairroId ";
				// 1.1 Bairro por Logradouro Bairro
				consulta += " OR bara.bair_id = :bairroId) ";
				parameters.put("bairroId", Integer.valueOf(filtroRA.getBairroId()));

				if(filtroRA.getRegistroAtendimento().getBairroArea() != null){
					if(filtroRA.getRegistroAtendimento().getBairroArea().getId().intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
						consulta += " AND baa.brar_id = :bairroAreaId ";
						parameters.put("bairroAreaId", filtroRA.getRegistroAtendimento().getBairroArea().getId());
					}
				}
			}

			// [SB006] Selecionar Registro de Atendimento por Logradouro
			if(filtroRA.getLogradouroId() != null && !filtroRA.getLogradouroId().equals("")){
				// 1.2 Logradouro por Imóvel
				consulta += " AND (lo.logr_id = :logradouroId ";
				// 1.1 Logradouro por Logradouro Bairro
				consulta += " OR lora.logr_id = :logradouroId) ";
				parameters.put("logradouroId", Integer.valueOf(filtroRA.getLogradouroId()));
			}

			// UnidadeAtendimento
			if(filtroRA.getUnidadeAtendimento() != null){

				consulta += " AND rau.unid_id = (:idUnidadeAtendimento) ";
				consulta += " AND rau.attp_id = (:idAtendimentoTipo) ";
				parameters.put("idUnidadeAtendimento", filtroRA.getUnidadeAtendimento().getId());
				parameters.put("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR);
			}

			// RegistroAtendimentoUnidade (Usuário)
			if(filtroRA.getRegistroAtendimentoUnidade() != null){

				consulta += " AND rau.usur_id = (:idUsuario) ";
				parameters.put("idUsuario", filtroRA.getRegistroAtendimentoUnidade().getUsuario().getId());
			}

			// UnidadeAtual
			if(filtroRA.getUnidadeAtual() != null){

				consulta += " AND tram.unid_iddestino = (:idUnidadeAtual) "
								+ " AND tram.tram_tmtramite in (select max(tr.tram_tmtramite) from tramite tr  "
								+ " where tr.rgat_id = ra.rgat_id)";

				parameters.put("idUnidadeAtual", filtroRA.getUnidadeAtual().getId());
			}

			// UnidadeSuperior
			if(filtroRA.getUnidadeSuperior() != null){

				consulta += " AND tram.unid_iddestino = (:idUnidadeSuperior) ";
				parameters.put("idUnidadeSuperior", filtroRA.getUnidadeSuperior().getId());
			}

			query = session.createQuery(consulta);
			query = session.createSQLQuery(consulta).addScalar("tamanho", Hibernate.INTEGER);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Set){
					Set setList = (HashSet) parameters.get(key);
					query.setParameterList(key, setList);
				}else if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}

			retorno = (Integer) query.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Definir a unidade destino a partir da divisão de esgoto
	 * informada/selecionada (UNID_ID e UNID_DSUNIDADE da tabela
	 * UNIDADE_ORGANIZACIONAL com UNID_ICTRAMITE=1 e UNID_ID=UNID_ID da tabela
	 * DIVISAO_ESGOTO com DVES_ID=Id da divisão selecionada)
	 * [SB0007] Define Unidade Destino da Divisão de Esgoto
	 * 
	 * @author Raphael Rossiter
	 * @date 08/08/2006
	 * @param idDivisaoEsgoto
	 * @return UnidadeDestino
	 * @throws ErroRepositorioException
	 */
	public UnidadeOrganizacional definirUnidadeDestinoDivisaoEsgoto(Integer idDivisaoEsgoto) throws ErroRepositorioException{

		UnidadeOrganizacional retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT dves.unidadeOrganizacional " + "FROM DivisaoEsgoto dves " + "INNER JOIN dves.unidadeOrganizacional unid  "
							+ "WHERE dves.id = :idDivisaoEsgoto AND unid.indicadorTramite = 1 ";

			retorno = (UnidadeOrganizacional) session.createQuery(consulta).setInteger("idDivisaoEsgoto", idDivisaoEsgoto).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso o Tipo de Solicitaçao seja relativo a área de esgoto (SOTG_ICESGOTO
	 * da tabela SOLICITACAO_TIPO_GRUPO com o valor correspondente a um para
	 * SOTG_ID=SOTG_ID da tabela SOLICITACAO_TIPO com SOTP_ID=Id do Tipo de
	 * Solicitação selecionado). Caso a quadra esteja preenchida, obter a
	 * divisão de esgoto da quadra (DVES_ID e DVES_DSDIVISAOESGOTO da tabela
	 * DIVISAO_ESGOTO com DVES_ID=DVES_ID da tabela SISTEMA_ESGOTO com
	 * SESG_ID=SESG_ID da tabela BACIA com BACI_ID=BACI_ID da tabela QUADRA com
	 * QDRA_ID=Id da quadra informada/selecionada).
	 * [SB0006] Obtém Divisão de Esgoto
	 * 
	 * @author Raphael Rossiter
	 * @date 07/08/2006
	 * @param solicitacaoTipoRelativoAreaEsgoto
	 *            ,
	 *            idQuadra
	 * @return UnidadeOrganizacional
	 * @throws ErroRepositorioException
	 */
	public DivisaoEsgoto obterDivisaoEsgoto(Integer idQuadra) throws ErroRepositorioException{

		DivisaoEsgoto retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT dves " + "FROM Quadra qdra " + "INNER JOIN qdra.bacia baci  " + "INNER JOIN baci.sistemaEsgoto sesg  "
							+ "INNER JOIN sesg.divisaoEsgoto dves " + "WHERE qdra.id = :idQuadra ";

			retorno = (DivisaoEsgoto) session.createQuery(consulta).setInteger("idQuadra", idQuadra).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso tenha informado a quadra e a mesma não pertença a divisão de esgoto
	 * informada (Id da divisão de esgoto é diferente de DVES_ID da tabela
	 * QUADRA com QDRA_ID=Id da quadra informada)
	 * [SB0006] Obtém Divisão de Esgoto
	 * 
	 * @author Raphael Rossiter
	 * @date 08/08/2006
	 * @param idQuadra
	 *            ,
	 *            idDivisaoEsgoto
	 * @return DivisaoEsgoto
	 * @throws ErroRepositorioException
	 */
	public DivisaoEsgoto verificarCompatibilidadeDivisaoEsgotoQuadra(Integer idQuadra, Integer idDivisaoEsgoto)
					throws ErroRepositorioException{

		DivisaoEsgoto retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT dves " + "FROM Quadra qdra " + "INNER JOIN qdra.bacia baci  " + "INNER JOIN baci.sistemaEsgoto sesg  "
							+ "INNER JOIN sesg.divisaoEsgoto dves " + "WHERE qdra.id = :idQuadra AND dves.id = :idDivisaoEsgoto ";

			retorno = (DivisaoEsgoto) session.createQuery(consulta).setInteger("idQuadra", idQuadra)
							.setInteger("idDivisaoEsgoto", idDivisaoEsgoto).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso tenha informado o setor comercial sem a quadra e o setor comercial
	 * não pertençaa a divisão de esgoto informada (Id da divisão de esgoto é
	 * diferente de todos os DVES_ID da tabela QUADRA com STCM_ID=Id do setor
	 * comercial informado).
	 * [SB0006] Obtém Divisão de Esgoto
	 * 
	 * @author Raphael Rossiter
	 * @date 08/08/2006
	 * @param idSetorComercial
	 *            ,
	 *            idDivisaoEsgoto
	 * @return DivisaoEsgoto
	 * @throws ErroRepositorioException
	 */
	public DivisaoEsgoto verificarCompatibilidadeDivisaoEsgotoSetor(Integer idSetorComercial, Integer idDivisaoEsgoto)
					throws ErroRepositorioException{

		DivisaoEsgoto retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT dves " + "FROM Quadra qdra " + "INNER JOIN qdra.setorComercial stcm " + "INNER JOIN qdra.bacia baci  "
							+ "INNER JOIN baci.sistemaEsgoto sesg  " + "INNER JOIN sesg.divisaoEsgoto dves "
							+ "WHERE stcm.id = :idSetorComercial AND dves.id = :idDivisaoEsgoto ";

			retorno = (DivisaoEsgoto) session.createQuery(consulta).setInteger("idSetorComercial", idSetorComercial)
							.setInteger("idDivisaoEsgoto", idDivisaoEsgoto).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso tenha informado a localidade sem o setor comercial e a localidade
	 * não pertença a divisão de esgoto informada (Id da divisãoo de esgoto é
	 * diferente de todos os DVES_ID da tabela QUADRA com STCM_ID=STCM_ID da
	 * tabela SETOR_COMERCIAL com LOCA_ID=Id da localidade informada).
	 * [SB0006] Obtém Divisão de Esgoto
	 * 
	 * @author Raphael Rossiter
	 * @date 07/08/2006
	 * @param idLocalidade
	 *            ,
	 *            idDivisaoEsgoto
	 * @return DivisaoEsgoto
	 * @throws ErroRepositorioException
	 */
	public DivisaoEsgoto verificarCompatibilidadeDivisaoEsgotoLocalidade(Integer idLocalidade, Integer idDivisaoEsgoto)
					throws ErroRepositorioException{

		DivisaoEsgoto retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT dves " + "FROM Quadra qdra " + "INNER JOIN qdra.setorComercial stcm " + "INNER JOIN stcm.localidade loca "
							+ "INNER JOIN qdra.bacia baci  " + "INNER JOIN baci.sistemaEsgoto sesg  "
							+ "INNER JOIN sesg.divisaoEsgoto dves " + "WHERE loca.id = :idLocalidade AND dves.id = :idDivisaoEsgoto ";

			retorno = (DivisaoEsgoto) session.createQuery(consulta).setInteger("idLocalidade", idLocalidade)
							.setInteger("idDivisaoEsgoto", idDivisaoEsgoto).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0399] Inserir Tipo de Solicitaão com Especificações
	 * Verifica se o serviço tipo tem como sreviço automatico geração
	 * automática.
	 * [SF0003] Validar Tipo de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 08/08/2006
	 * @param idServicoTipo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer verificarServicoTipoReferencia(Integer idServicoTipo) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT servTipo.id " + "FROM ServicoTipo servTipo " + "LEFT JOIN servTipo.servicoTipoReferencia servTipoRef "
							+ "WHERE servTipo.id =:idServicoTipo AND " + "servTipoRef.id is not null "
							+ "AND servTipoRef.indicadorExistenciaOsReferencia = :indicadorExistencia ";

			retorno = (Integer) session.createQuery(consulta).setInteger("idServicoTipo", idServicoTipo)
							.setShort("indicadorExistencia", ServicoTipoReferencia.INDICADOR_EXISTENCIA_OS_REFERENCIA_ATIVO)
							.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0399] Inserir Tipo de Solicitação com Especificões
	 * Verificar descrição do tipo de solicitação com especificação e indicador
	 * uso ativo se já inserido na base .
	 * [SF0001] Verificar existencia da descrição
	 * 
	 * @author Sávio Luiz
	 * @date 08/08/2006
	 * @param idServicoTipo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaDescricaoTipoSolicitacao(String descricaoTipoSolicitacao) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT solicitacaoTipo.id " + "FROM SolicitacaoTipo solicitacaoTipo "
							+ "WHERE solicitacaoTipo.descricao =:descricaoTipoSolicitacao AND "
							+ "solicitacaoTipo.indicadorUso =:indicadorUso";

			retorno = (Integer) session.createQuery(consulta).setString("descricaoTipoSolicitacao", descricaoTipoSolicitacao)
							.setShort("indicadorUso", SolicitacaoTipo.INDICADOR_USO_ATIVO).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0020] Verifica Situação do Imóvel e Especificação
	 * 
	 * @author Raphael Rossiter
	 * @date 07/08/2006
	 * @param idSolicitacaoTipoEspecificacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection verificarSituacaoImovelEspecificacao(Integer idSolicitacaoTipoEspecificacao) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT eisc.ligacaoAguaSituacao.id, eisc.ligacaoEsgotoSituacao.id, "
							+ "eisc.indicadorHidrometroLigacaoAgua, eisc.indicadorHidrometroPoco, " + "step.descricao "
							+ "FROM SolicitacaoTipoEspecificacao step, EspecificacaoImovSitCriterio eisc "
							+ "INNER JOIN step.especificacaoImovelSituacao esim " + "WHERE step.id = :idSolicitacaoTipoEspecificacao AND "
							+ "esim.id = eisc.especificacaoImovelSituacao.id ";

			retorno = session.createQuery(consulta).setInteger("idSolicitacaoTipoEspecificacao", idSolicitacaoTipoEspecificacao).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0418] Obter Unidade Atual do RA
	 * verifica a unidade atual do registro de atendimento pelo último trâmite
	 * efetuado
	 * 
	 * @author Ana Maria
	 * @date 04/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public UnidadeOrganizacional verificaUnidadeAtualRA(Integer idRegistroAtendimento) throws ErroRepositorioException{

		UnidadeOrganizacional retorno = null;

		Session session = HibernateUtil.getSession();
		Tramite tramitex = null;
		try{

			Criteria criteria = session.createCriteria(Tramite.class);
			criteria.createAlias("registroAtendimento", "registroAtendimento");
			criteria.add(Restrictions.eq("registroAtendimento.id", idRegistroAtendimento));
			criteria.setProjection(Projections.max("dataTramite"));

			Date dataTramite = (Date) criteria.uniqueResult();

			if(dataTramite != null){

				Criteria criteriax = session.createCriteria(Tramite.class);
				criteriax.createAlias("registroAtendimento", "registroAtendimento");
				criteriax.add(Restrictions.eq("registroAtendimento.id", idRegistroAtendimento));
				criteriax.add(Restrictions.eq("dataTramite", dataTramite));


				tramitex = (Tramite) criteriax.setMaxResults(1).uniqueResult();

			}

			if(tramitex != null){

				criteria = session.createCriteria(Tramite.class);
				criteria.createAlias("unidadeOrganizacionalDestino", "unidadeOrganizacionalDestino", Criteria.LEFT_JOIN);
				criteria.createAlias("unidadeOrganizacionalDestino.unidadeTipo", "unidadeTipo", Criteria.LEFT_JOIN);
				criteria.createAlias("unidadeOrganizacionalDestino.unidadeCentralizadora", "unidadeCentralizadora", Criteria.LEFT_JOIN);
				criteria.add(Restrictions.idEq(tramitex.getId()));

				Tramite tramite = (Tramite) criteria.uniqueResult();

				retorno = tramite.getUnidadeOrganizacionalDestino();

			}

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	/**
	 * [UC0419] Obter Indicador de Autorização para Manutenção do RA
	 * Verifica a unidade superior da unidade do RA
	 * 
	 * @author Ana Maria
	 * @date 08/08/2006
	 * @param idUnidade
	 * @throws ControladorException
	 */
	public Integer verificaUnidadeSuperiorUnidade(Integer idUnidade) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select unidadeSuperior.id " + " from UnidadeOrganizacional unid "
			// + " inner join unid.unidadeSuperior unsu "
							+ " where unid.id = :idUnidade";

			retorno = (Integer) session.createQuery(consulta).setInteger("idUnidade", idUnidade).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0420] Obter Descrição da situação do RA
	 * verifica a situaçao(RGAT_CDSITUACAO) do registro de atendimento
	 * 
	 * @author Ana Maria
	 * @date 03/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Short verificaSituacaoRA(Integer idRegistroAtendimento) throws ErroRepositorioException{

		Short retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select rgat.codigoSituacao " + "from RegistroAtendimento rgat " + "where rgat.id = :idRegistroAtendimento ";

			retorno = (Short) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0421] Obter Unidade de Atendimento do RA ou [UC0434] Obter Unidade de
	 * Encerramento do RA
	 * Verifica a unidade de atendimento do registro de atendimento
	 * 
	 * @author Ana Maria
	 * @date 04/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public UnidadeOrganizacional verificaUnidadeAtendimentoRA(Integer idRegistroAtendimento, Integer idAtendimentoRelacaoTipo)
					throws ErroRepositorioException{

		UnidadeOrganizacional retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select raun.unidadeOrganizacional " + "from RegistroAtendimentoUnidade raun "
							+ "inner join raun.registroAtendimento rgat " + "inner join raun.atendimentoRelacaoTipo attp "
							+ "where rgat.id = :idRegistroAtendimento and attp.id = :idAtendimentoRelacaoTipo";

			retorno = (UnidadeOrganizacional) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento)
							.setInteger("idAtendimentoRelacaoTipo", idAtendimentoRelacaoTipo).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0422] Obter Endereço da Ocorrência do RA
	 * Verifica existência do logradouro(lgbr_id) e do imovel no registro de
	 * atendimento
	 * 
	 * @author Ana Maria
	 * @date 07/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Object[] verificaEnderecoOcorrenciaRA(Integer idRegistroAtendimento) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select lgbr.id, imov.id " //
							+ "from RegistroAtendimento rgat " //
							+ "left join rgat.logradouroBairro lgbr " //
							+ "left join rgat.imovel imov " //
							+ "where rgat.id = :idRegistroAtendimento"; //

			retorno = (Object[]) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0423] Obter Endereço do Solicitante do RA
	 * Verifica existência do logradouro(lgbr_id) e do cliente no registro de
	 * atendimento solicitante
	 * 
	 * @author Ana Maria
	 * @date 07/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Object[] verificaEnderecoRASolicitante(Integer idRegistroAtendimentoSolicitante) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select lgbr.id, clie.id " + "from RegistroAtendimentoSolicitante raso  " + "left join raso.logradouroBairro lgbr "
							+ "left join raso.cliente clie " + "where raso.id = :idRegistroAtendimentoSolicitante";

			retorno = (Object[]) session.createQuery(consulta)
							.setInteger("idRegistroAtendimentoSolicitante", idRegistroAtendimentoSolicitante).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0433] Obter Registro de Atendimento Associado
	 * Verifica duplicidade no registro de atendimento
	 * 
	 * @author Ana Maria
	 * @date 08/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public RegistroAtendimento verificaDuplicidadeRegistroAtendimento(Integer idRegistroAtendimento) throws ErroRepositorioException{

		RegistroAtendimento retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select rgat.registroAtendimentoDuplicidade " + " from RegistroAtendimento rgat "
							+ " where rgat.id = :idRegistroAtendimento ";

			retorno = (RegistroAtendimento) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento)
							.setMaxResults(1).uniqueResult();

			/*
			 * if (retorno != null) {
			 * Hibernate.initialize(retorno.getRaMotivoReativacao()); }
			 */

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0433] Obter Registro de Atendimento Associado
	 * Verifica se o registro de atendimento foi reativado
	 * 
	 * @author Ana Maria
	 * @date 08/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public RegistroAtendimento verificaRegistroAtendimentoReativado(Integer idRegistroAtendimento) throws ErroRepositorioException{

		RegistroAtendimento retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select rgat " + " from RegistroAtendimento rgat " + " inner join rgat.registroAtendimentoReativacao rart "
							+ " left join fetch rgat.raMotivoReativacao " + " where rart.id = :idRegistroAtendimento ";

			retorno = (RegistroAtendimento) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento)
							.setMaxResults(1).uniqueResult();

			/*
			 * if (retorno != null) {
			 * Hibernate.initialize(retorno.getRaMotivoReativacao()); }
			 */

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0433] Obter Registro de Atendimento Associado
	 * Verifica se o registro de atendimento é reativação de outro
	 * 
	 * @author Ana Maria
	 * @date 08/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public RegistroAtendimento verificaRegistroAtendimentoReativacao(Integer idRegistroAtendimento) throws ErroRepositorioException{

		RegistroAtendimento retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select rgat.registroAtendimentoReativacao " + " from RegistroAtendimento rgat "
							+ " where rgat.id = :idRegistroAtendimento ";

			retorno = (RegistroAtendimento) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento)
							.setMaxResults(1).uniqueResult();

			/*
			 * if (retorno != null) {
			 * Hibernate.initialize(retorno.getRaMotivoReativacao()); }
			 */

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0445] Obter Nome do Solicitante do RA
	 * Pesquisa o registro de atendimento solicitante
	 * 
	 * @author Ana Maria
	 * @date 09/08/2006
	 * @param idRegistroAtendimentoSolicitante
	 * @throws ControladorException
	 */
	public Object[] pesquisarRegistroAtendimentoSolicitante(Integer idRegistroAtendimentoSolicitante) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select raso.solicitante, " // 0
							+ " clie.id, " // 1
							+ " clie.nome,"// 2
							+ " unid.id," // 3
							+ " func.nome "// 4
							+ " from RegistroAtendimentoSolicitante raso "
							+ " left join raso.cliente clie "
							+ " left join raso.unidadeOrganizacional unid "
							+ " left join raso.funcionario func "
							+ " where raso.id = :idRegistroAtendimentoSolicitante ";

			retorno = (Object[]) session.createQuery(consulta)
							.setInteger("idRegistroAtendimentoSolicitante", idRegistroAtendimentoSolicitante).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0419] Obter Indicador de Autorização para Manutenção do RA
	 * Pesquisar unidade organizacional e o indicador da unidade de central de
	 * atendimento ao cliente
	 * 
	 * @author Ana Maria
	 * @date 09/08/2006
	 * @param idUsuario
	 * @throws ControladorException
	 */
	public Object[] pesquisarUnidadeOrganizacionalUsuario(Integer idUsuario) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select unid.id," // 0
							+ " unid.indicadorCentralAtendimento"// 1
							+ " from Usuario usur" + " left join usur.unidadeOrganizacional unid" + " where usur.id =  :idUsuario ";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idUsuario", idUsuario).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * Pesquisar os parametros para atualizar o registro atendimento escolhido
	 * pelo usuário
	 * 
	 * @author Sávio Luiz
	 * @date 11/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Object[] pesquisarParmsRegistroAtendimento(Integer idRegistroAtendimento) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select regAtendimento.id," // 0
							+ "regAtendimento.indicadorAtendimentoOnline,"// 1
							+ "regAtendimento.registroAtendimento,"// 2
							+ "regAtendimento.dataInicioEspera,"// 3
							+ "regAtendimento.dataFimEspera,"// 4
							+ "meioSolic.id,"// 5
							+ "solicTipo.id,"// 6
							+ "solicTipoEspecificacao.id,"// 7
							+ "regAtendimento.dataPrevistaAtual,"// 8
							+ "regAtendimento.observacao, "// 9
							+ "imov.id, "// 10
							+ "regAtendimento.pontoReferencia, "// 11
							+ "baiArea.id, "// 12
							+ "munic.id, "// 13
							+ "munic.nome, "// 14
							+ "bairr.id, "// 15
							+ "bairr.codigo, "// 16
							+ "bairr.nome, "// 17
							+ "loc.id, "// 18
							+ "loc.descricao, "// 19
							+ "setComerc.id, "// 20
							+ "setComerc.codigo, "// 21
							+ "setComerc.descricao, "// 22
							+ "quad.id, "// 23
							+ "quad.numeroQuadra, "// 24
							+ "divEsgoto.id, "// 25
							+ "locOco.id, "// 26
							+ "pavRua.id, "// 27
							+ "pavCalcada.id, "// 28
							+ "regAtendimento.descricaoLocalOcorrencia, "// 29
							+ "solicTipoEspecificacao.indicadorMatricula, "// 30
							+ "solicTipoEspecificacao.indicadorPavimentoRua, "// 31
							+ "solicTipoEspecificacao.indicadorPavimentoCalcada, "// 32
							+ "solicTipo.indicadorFaltaAgua, "// 33
							+ "regAtendimento.ultimaAlteracao, "// 34
							+ "regAtendimento.manual,  "// 35
							+ "servTipo.id, " // 36
							+ "regAtendimento.senhaAtendimento, " // 37
							+ "regAtendimento.indicadorProcessoAdmJud, " // 38
							+ "regAtendimento.numeroProcessoAgencia " // 39
							+ " FROM RegistroAtendimento regAtendimento "
							+ " INNER JOIN regAtendimento.meioSolicitacao meioSolic "
							+ " INNER JOIN regAtendimento.solicitacaoTipoEspecificacao solicTipoEspecificacao "
							+ " INNER JOIN solicTipoEspecificacao.solicitacaoTipo solicTipo "
							+ " LEFT JOIN solicTipoEspecificacao.servicoTipo servTipo "
							+ " LEFT JOIN regAtendimento.imovel imov "
							+ " LEFT JOIN regAtendimento.bairroArea baiArea"
							+ " LEFT JOIN baiArea.bairro bairr"
							+ " LEFT JOIN bairr.municipio munic "
							+ " LEFT JOIN regAtendimento.localidade loc "
							+ " LEFT JOIN regAtendimento.setorComercial setComerc "
							+ " LEFT JOIN regAtendimento.quadra quad "
							+ " LEFT JOIN regAtendimento.divisaoEsgoto divEsgoto"
							+ " LEFT JOIN regAtendimento.localOcorrencia locOco"
							+ " LEFT JOIN regAtendimento.divisaoEsgoto divEsgoto"
							+ " LEFT JOIN regAtendimento.pavimentoRua pavRua"
							+ " LEFT JOIN regAtendimento.pavimentoCalcada pavCalcada"
							+ " WHERE regAtendimento.id =  :idRegistroAtendimento ";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * Verificar existencia ordem de Serviço para o registro atendimento
	 * pesquisado
	 * 
	 * @author Sávio Luiz
	 * @date 11/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Integer verificarOrdemServicoParaRA(Integer idRegistroAtendimento) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select ordemServ.id " + "FROM OrdemServico ordemServ " + "INNER JOIN ordemServ.registroAtendimento regAtendimento "
							+ "WHERE regAtendimento.id =  :idRegistroAtendimento ";

			retorno = (Integer) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0446] Consultar Trâmites
	 * Retorna a Coleção de Tramites do registro de atendimento
	 * 
	 * @author Leonardo Regis
	 * @date 11/08/2006
	 * @param idRA
	 * @return Collection<Tramite>
	 * @throws ControladorException
	 */
	public Collection<Tramite> obterTramitesRA(Integer idRA) throws ErroRepositorioException{

		Collection<Tramite> retorno = null;
		Collection<Object[]> retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " SELECT tram.id, " // 0
							+ "  tram.dataTramite, " // 1
							+ "  unid.id, " // 2
							+ "  unid.descricao,  " // 3
							+ "  user.id, " // 4
							+ "  user.nomeUsuario, " // 5
							+ "  moti.id, " // 6
							+ "  moti.descricao " // 7
							+ " FROM Tramite tram "
							+ " INNER JOIN tram.registroAtendimento rgat  "
							+ " INNER JOIN tram.unidadeOrganizacionalDestino unid  "
							+ " INNER JOIN tram.usuarioResponsavel user "
							+ " LEFT JOIN tram.motivoTramite moti  " + " WHERE rgat.id = :idRA " + " ORDER BY tram.dataTramite ";

			retornoConsulta = session.createQuery(consulta).setInteger("idRA", idRA).list();

			if(!retornoConsulta.isEmpty()){
				retorno = new ArrayList();
				Tramite tramite = null;
				UnidadeOrganizacional unidade = null;
				MotivoTramite motivoTramite = null;
				Usuario usuario = null;
				for(Iterator iter = retornoConsulta.iterator(); iter.hasNext();){
					Object[] element = (Object[]) iter.next();
					tramite = new Tramite();
					tramite.setId((Integer) element[0]);
					tramite.setDataTramite((Date) element[1]);
					unidade = new UnidadeOrganizacional();
					unidade.setId((Integer) element[2]);
					unidade.setDescricao((String) element[3]);
					tramite.setUnidadeOrganizacionalDestino(unidade);
					usuario = new Usuario();
					usuario.setId((Integer) element[4]);
					usuario.setNomeUsuario((String) element[5]);

					if(element[6] != null && !element[6].toString().equals("")){
						motivoTramite = new MotivoTramite();
						motivoTramite.setId((Integer) element[6]);
						motivoTramite.setDescricao((String) element[7]);
						tramite.setMotivoTramite(motivoTramite);
					}

					tramite.setUsuarioResponsavel(usuario);
					retorno.add(tramite);
				}
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0447] Consultar RA Solicitantes
	 * Retorna a Coleção de registro de atendimento solicitantes
	 * 
	 * @author Rafael Pinto
	 * @date 14/08/2006
	 * @param idRA
	 * @return Collection<RegistroAtendimentoSolicitante>
	 * @throws ControladorException
	 */
	public Collection<RegistroAtendimentoSolicitante> obterRASolicitante(Integer idRA) throws ErroRepositorioException{

		Collection<RegistroAtendimentoSolicitante> retorno = null;
		Collection<ArrayList> retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT RASolicitante.id," // 0
							+ " RASolicitante.pontoReferencia," // 1
							+ " RASolicitante.indicadorSolicitantePrincipal," // 2
							+ " unid.id," // 3
							+ " unid.descricao," // 4
							+ " clie.id," // 5
							+ " clie.nome," // 6
							+ " func.id," // 7
							+ " func.nome," // 8
							+ " RASolicitante.solicitante," // 9
							+ " RASolicitante.numeroCpf," // 10
							+ " RASolicitante.numeroCnpj," // 11
							+ " RASolicitante.numeroRG," // 12
							+ " RASolicitante.orgaoExpedidorRg.id," // 13
							+ " RASolicitante.unidadeFederacaoRG.id " // 14
							+ " FROM RegistroAtendimentoSolicitante RASolicitante"
							+ " INNER JOIN RASolicitante.registroAtendimento rgat"
							+ " LEFT JOIN RASolicitante.unidadeOrganizacional unid"
							+ " LEFT JOIN RASolicitante.cliente clie"
							+ " LEFT JOIN RASolicitante.funcionario func"
							+ " WHERE rgat.id = :idRA"
							+ " ORDER BY RASolicitante.indicadorSolicitantePrincipal,RASolicitante.id ";

			retornoConsulta = session.createQuery(consulta).setInteger("idRA", idRA).list();

			if(!retornoConsulta.isEmpty()){

				retorno = new ArrayList();

				RegistroAtendimentoSolicitante registroAtendimentoSolicitante = null;
				UnidadeOrganizacional unidade = null;
				Cliente cliente = null;
				Funcionario funcionario = null;
				OrgaoExpedidorRg orgaoExpedidorRg = null;
				UnidadeFederacao unidadeFederacao = null;

				for(Iterator iter = retornoConsulta.iterator(); iter.hasNext();){

					Object[] element = (Object[]) iter.next();

					registroAtendimentoSolicitante = new RegistroAtendimentoSolicitante();
					registroAtendimentoSolicitante.setID((Integer) element[0]);
					registroAtendimentoSolicitante.setPontoReferencia((String) element[1]);
					registroAtendimentoSolicitante.setIndicadorSolicitantePrincipal((Short) element[2]);

					if(element[3] != null){
						unidade = new UnidadeOrganizacional();
						unidade.setId((Integer) element[3]);
						unidade.setDescricao((String) element[4]);
					}

					if(element[5] != null){
						cliente = new Cliente();
						cliente.setId((Integer) element[5]);
						cliente.setNome((String) element[6]);

					}

					if(element[7] != null){
						funcionario = new Funcionario();
						funcionario.setId((Integer) element[7]);
						funcionario.setNome((String) element[8]);
					}

					registroAtendimentoSolicitante.setNumeroCpf((String) element[10]);
					registroAtendimentoSolicitante.setNumeroCnpj((String) element[11]);
					registroAtendimentoSolicitante.setNumeroRG((String) element[12]);

					if(element[13] != null){
						orgaoExpedidorRg = new OrgaoExpedidorRg();
						orgaoExpedidorRg.setId((Integer) element[13]);
					}

					if(element[14] != null){
						unidadeFederacao = new UnidadeFederacao();
						unidadeFederacao.setId((Integer) element[14]);
					}

					registroAtendimentoSolicitante.setOrgaoExpedidorRg(orgaoExpedidorRg);
					registroAtendimentoSolicitante.setUnidadeFederacaoRG(unidadeFederacao);
					registroAtendimentoSolicitante.setUnidadeOrganizacional(unidade);
					registroAtendimentoSolicitante.setCliente(cliente);
					registroAtendimentoSolicitante.setFuncionario(funcionario);
					registroAtendimentoSolicitante.setSolicitante((String) element[9]);

					retorno.add(registroAtendimentoSolicitante);

					registroAtendimentoSolicitante = null;
					unidade = null;
					cliente = null;
					funcionario = null;
					orgaoExpedidorRg = null;
					unidadeFederacao = null;
				}
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * [UC0431] Consultar Ordens de Serviço do Registro Atendimento
	 * Retorna a Coleção de OS's do registro de atendimento
	 * 
	 * @author Leonardo Regis
	 * @date 11/08/2006
	 * @param idRA
	 * @return Collection<OrdemServico>
	 * @throws ControladorException
	 */
	public Collection<OrdemServico> obterOSRA(Integer idRA) throws ErroRepositorioException{

		Collection<OrdemServico> retorno = null;
		Collection<Object[]> retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " SELECT os.id, " // 0
							+ "  os.dataGeracao, " // 1
							+ "  st.id, " // 2
							+ "  st.descricao,  " // 3
							+ "  os.situacao, " // 4
							+ "  st.indicadorTerceirizado, " // 5
							+ "  osr.id " // 6
							+ " FROM OrdemServico os " + " LEFT  JOIN os.osReferencia osr "
							+ " INNER JOIN os.registroAtendimento rgat  "
							+ " INNER JOIN os.servicoTipo st  " + " WHERE rgat.id = :idRA " + " ORDER BY os.id ";

			retornoConsulta = session.createQuery(consulta).setInteger("idRA", idRA).list();

			if(!retornoConsulta.isEmpty()){
				retorno = new ArrayList();
				OrdemServico os = null;
				ServicoTipo servicoTipo = null;
				OrdemServico osr = null;
				for(Iterator iter = retornoConsulta.iterator(); iter.hasNext();){
					Object[] element = (Object[]) iter.next();
					os = new OrdemServico();
					os.setId((Integer) element[0]);
					os.setDataGeracao((Date) element[1]);
					servicoTipo = new ServicoTipo();
					servicoTipo.setId((Integer) element[2]);
					servicoTipo.setDescricao((String) element[3]);
					servicoTipo.setIndicadorTerceirizado((Short) element[5]);
					os.setServicoTipo(servicoTipo);
					os.setSituacao((Short) element[4]);
					if(element[6] != null){
						osr = new OrdemServico();
						osr.setId((Integer) element[6]);
					}
					os.setOsReferencia(osr);
					retorno.add(os);
				}
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * verificar duplicidade do registro de atendimento e código situação
	 * [FS0012] - Verificar possibilidade de atualização do registro de
	 * atendimento
	 * 
	 * @author Sávio Luiz
	 * @date 14/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Object[] verificarParmsRA(Integer idRegistroAtendimento) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select regAtendDuplicidade.id," // 0
							+ "regAtendimento.codigoSituacao" // 1
							+ " FROM RegistroAtendimento regAtendimento "
							+ " LEFT JOIN regAtendimento.registroAtendimentoDuplicidade regAtendDuplicidade "
							+ " WHERE regAtendimento.id =  :idRegistroAtendimento ";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * verificar existencai da ordem de servico Programação para o RA
	 * [FS0012] - Verificar possibilidade de atualização do registro de
	 * atendimento
	 * 
	 * @author Sávio Luiz
	 * @date 14/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Integer verificarExistenciaOrdemServicoProgramacaoRA(Integer idRegistroAtendimento) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select ordemServProg.id " + "FROM OrdemServicoProgramacao ordemServProg "
							+ "LEFT JOIN ordemServProg.ordemServico ordemServ " + "LEFT JOIN ordemServ.registroAtendimento regAtendimento "
							+ "WHERE regAtendimento.id =  :idRegistroAtendimento AND " + "ordemServ.situacao <> :codigoSituacao AND "
							+ "ordemServProg.indicadorAtivo = :indAtivo";

			retorno = (Integer) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento)
							.setShort("codigoSituacao", OrdemServico.SITUACAO_ENCERRADO)
							.setShort("indAtivo", OrdemServicoProgramacao.INDICADOR_ATIVO).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0409] Obter Indicador de existência de Hidrômetro na Ligação de Água e
	 * no Poço
	 * Pesquisar a situação e o indicador de existência de Hidrômetro na Ligação
	 * de Água e no poço
	 * 
	 * @author Ana Maria
	 * @date 09/08/2006
	 * @param idImovel
	 * @throws ControladorException
	 */
	public Object[] pesquisarHidrometroImovel(Integer idImovel) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select last.id," // 0
							+ " lest.id," // 1
							+ " hidi.id," // 2
							+ " hilg.id "// 3
							+ " from Imovel imov"
							+ " inner join imov.ligacaoAguaSituacao last"
							+ " inner join imov.ligacaoEsgotoSituacao lest"
							+ " left join imov.hidrometroInstalacaoHistorico hidi with (hidi.dataRetirada is null) "
							+ " left join imov.ligacaoAgua lagu"
							+ " left join lagu.hidrometroInstalacaoHistorico hilg with (hilg.dataRetirada is null) "
							+ " where imov.id = :idImovel";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * Pesquisar os parametros para saber como está a situacao do registro de
	 * antendimento
	 * 
	 * @author Sávio Luiz
	 * @date 15/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Short pesquisarCdSituacaoRegistroAtendimento(Integer idRegistroAtendimento) throws ErroRepositorioException{

		Short retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select regAtendimento.codigoSituacao " + " FROM RegistroAtendimento regAtendimento "
							+ " WHERE regAtendimento.id =  :idRegistroAtendimento ";

			retorno = (Short) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * Pesquisar se o Imóvel é descritivo
	 * 
	 * @author Sávio Luiz
	 * @date 15/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Integer pesquisarImovelDescritivo(Integer idImovel) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select logBairro.id " + " FROM Imovel imov " + " INNER JOIN imov.logradouroBairro logBairro"
							+ " WHERE imov.id =  :idImovel ";

			retorno = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0430] Gerar Ordem de Serviço
	 * 
	 * @author lms
	 * @date 17/08/2006
	 */
	public RegistroAtendimento pesquisarRegistroAtendimento(Integer id) throws ErroRepositorioException{

		RegistroAtendimento retorno = null;
		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT rgat " + "FROM RegistroAtendimento rgat " + "left join fetch rgat.solicitacaoTipoEspecificacao stes "
							+ "left join fetch stes.solicitacaoTipo sotp " + "left join fetch stes.especificacaoServicoTipos estp "
							+ "left join fetch rgat.quadra qdr " + "left join fetch rgat.logradouroBairro logbr "
							+ "left join fetch logbr.logradouro " + "left join fetch logbr.logradouro.logradouroTitulo "
							+ "left join fetch logbr.logradouro.logradouroTipo " + "left join fetch logbr.bairro " + "WHERE rgat.id = "
							+ id;

			retorno = (RegistroAtendimento) session.createQuery(consulta).uniqueResult();

			/*
			 * if (retorno != null) {
			 * Hibernate.initialize(retorno.getSolicitacaoTipoEspecificacao());
			 * Hibernate.initialize(retorno.getSolicitacaoTipoEspecificacao()
			 * .getSolicitacaoTipo());
			 * Hibernate.initialize(retorno.getSolicitacaoTipoEspecificacao()
			 * .getEspecificacaoServicoTipos()); Collection collection = retorno
			 * .getSolicitacaoTipoEspecificacao()
			 * .getEspecificacaoServicoTipos(); for (Iterator iter =
			 * collection.iterator(); iter.hasNext();) {
			 * EspecificacaoServicoTipo est = (EspecificacaoServicoTipo) iter
			 * .next(); Hibernate.initialize(est.getServicoTipo()); } }
			 */

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * REGISTRO_ATENDIMENTO com IMOV_ID=nulo e RGAT_CDSITUACAO=1 e
	 * LGBR_ID=LGBR_ID do Endereço da Ocorrência e LGCP_ID=LGCP_ID do Endereço
	 * da Ocorrência e STEP_ID=Id da especificação selecionada e STEP_ID=STEP_ID
	 * da tabela SOLICITACAO_TIPO_ESPECIFICACAO com SOTP_ID=SOTP_ID da tabela
	 * SOLICITACAO_TIPO para SOTP_ICFALTAAGUA com o valor correspondente a dois
	 * [SB0008] Verifica existência de Registro de Atendimento Pendente para o
	 * Local da Ocorrência
	 * 
	 * @author Raphael Rossiter
	 * @date 15/08/2006
	 * @param idSolicitacaoTipoEspecificacao
	 *            ,
	 *            idLogradouroCep, idLogradouroBairro
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection verificaExistenciaRAPendenteLocalOcorrencia(Integer idSolicitacaoTipoEspecificacao, Integer idLogradouroCep,
					Integer idLogradouroBairro) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT logradouro.nome,"
							+ // 0
							" logradouroTipo.descricaoAbreviada,"
							+ // 1
							" logradouroTitulo.descricaoAbreviada,"
							+ // 2
							" bairro.id,"
							+ // 3
							" bairro.nome,"
							+ // 4
							" municipio.id,"
							+ // 5
							" municipio.nome,"
							+ // 6
							" unidadeFederacao.id,"
							+ // 7
							" unidadeFederacao.sigla,"
							+ // 8
							" step.descricao, "
							+ // 9
							" cep.cepId,"
							+ // 10
							" cep.logradouro,"
							+ // 11
							" cep.descricaoTipoLogradouro,"
							+ // 12
							" cep.bairro,"
							+ // 13
							" cep.municipio,"
							+ // 14
							" cep.sigla, "
							+ // 15
							" cep.codigo, "
							+ // 16
							" bairro.codigo, "
							+ // 17
							" rgat.complementoEndereco,"
							+ // 18
							" logradouro.id,"
							+ // 19
							" logradouroCep.id,"
							+ // 20
							" logradouroBairro.id,"
							+ // 21
							" logradouroTipo.descricao,"
							+ // 22
							" logradouroTitulo.descricao, "
							+ // 23
							" step.id " // 24
							+ "FROM RegistroAtendimento rgat " + "INNER JOIN rgat.solicitacaoTipoEspecificacao step "
							+ "INNER JOIN step.solicitacaoTipo sotp " + "left join rgat.logradouroCep logradouroCep "
							+ "left join logradouroCep.cep cep " + "left join logradouroCep.logradouro logradouro "
							+ "left join logradouro.logradouroTipo logradouroTipo "
							+ "left join logradouro.logradouroTitulo logradouroTitulo "
							+ "left join rgat.logradouroBairro logradouroBairro " + "left join logradouroBairro.bairro bairro "
							+ "left join bairro.municipio municipio " + "left join municipio.unidadeFederacao unidadeFederacao "
							+ "WHERE rgat.codigoSituacao = 1 "
							+ "AND logradouroBairro.id = :idLogradouroBairro AND logradouroCep.id = :idLogradouroCep "
							+ "AND step.id = :idSolicitacaoTipoEspecificacao AND sotp.indicadorFaltaAgua = 2";

			retorno = (Collection) session.createQuery(consulta).setInteger("idLogradouroBairro", idLogradouroBairro)
							.setInteger("idLogradouroCep", idLogradouroCep)
							.setInteger("idSolicitacaoTipoEspecificacao", idSolicitacaoTipoEspecificacao).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * REGISTRO_ATENDIMENTO com IMOV_ID=nulo e RGAT_CDSITUACAO=1 e
	 * LGBR_ID=LGBR_ID do Endereço da Ocorrência e LGCP_ID=LGCP_ID do Endereço
	 * da Ocorrência e STEP_ID=Id da especificação selecionada e STEP_ID=STEP_ID
	 * da tabela SOLICITACAO_TIPO_ESPECIFICACAO com SOTP_ID=SOTP_ID da tabela
	 * SOLICITACAO_TIPO para SOTP_ICFALTAAGUA com o valor correspondente a dois
	 * 
	 * @author Raphael Rossiter
	 * @date 16/08/2006
	 * @param idSolicitacaoTipoEspecificacao
	 *            ,
	 *            idLogradouroCep, idLogradouroBairro
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarRAPendenteLocalOcorrencia(Integer idSolicitacaoTipoEspecificacao, Integer idLogradouroCep,
					Integer idLogradouroBairro) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT logradouro.nome,"
							+ // 0
							" logradouroTipo.descricaoAbreviada,"
							+ // 1
							" logradouroTitulo.descricaoAbreviada,"
							+ // 2
							" bairro.id,"
							+ // 3
							" bairro.nome,"
							+ // 4
							" municipio.id,"
							+ // 5
							" municipio.nome,"
							+ // 6
							" unidadeFederacao.id,"
							+ // 7
							" unidadeFederacao.sigla,"
							+ // 8
							" step.descricao, "
							+ // 9
							" cep.cepId,"
							+ // 10
							" cep.logradouro,"
							+ // 11
							" cep.descricaoTipoLogradouro,"
							+ // 12
							" cep.bairro,"
							+ // 13
							" cep.municipio,"
							+ // 14
							" cep.sigla, "
							+ // 15
							" cep.codigo, "
							+ // 16
							" bairro.codigo, "
							+ // 17
							" rgat.complementoEndereco,"
							+ // 18
							" logradouro.id,"
							+ // 19
							" logradouroCep.id,"
							+ // 20
							" logradouroBairro.id,"
							+ // 21
							" logradouroTipo.descricao,"
							+ // 22
							" logradouroTitulo.descricao, "
							+ // 23
							" step.id, "
							+ // 24
							" sotp.id, "
							+ // 25
							" sotp.descricao, "
							+ // 26
							" rgat.id, "
							+ // 27
							" rgat.pontoReferencia, "
							+ // 28
							" rgat.registroAtendimento " // 29
							+ "FROM RegistroAtendimento rgat " + "INNER JOIN rgat.solicitacaoTipoEspecificacao step "
							+ "INNER JOIN step.solicitacaoTipo sotp " + "left join rgat.logradouroCep logradouroCep "
							+ "left join logradouroCep.cep cep " + "left join logradouroCep.logradouro logradouro "
							+ "left join logradouro.logradouroTipo logradouroTipo "
							+ "left join logradouro.logradouroTitulo logradouroTitulo "
							+ "left join rgat.logradouroBairro logradouroBairro " + "left join logradouroBairro.bairro bairro "
							+ "left join bairro.municipio municipio " + "left join municipio.unidadeFederacao unidadeFederacao "
							+ "WHERE rgat.codigoSituacao = 1 "
							+ "AND logradouroBairro.id = :idLogradouroBairro AND logradouroCep.id = :idLogradouroCep "
							+ "AND step.id = :idSolicitacaoTipoEspecificacao AND sotp.indicadorFaltaAgua = 2";

			retorno = (Collection) session.createQuery(consulta).setInteger("idLogradouroBairro", idLogradouroBairro)
							.setInteger("idLogradouroCep", idLogradouroCep)
							.setInteger("idSolicitacaoTipoEspecificacao", idSolicitacaoTipoEspecificacao).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0452] Obter Dados do Registro Atendimento
	 * Pesquisar dados do registro de atendimento
	 * 
	 * @author Ana Maria
	 * @date 14/08/2006
	 * @author Saulo Lima
	 * @date 07/01/2009
	 *       Adicionados os campos de Ligação de Água
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Object[] pesquisarDadosRegistroAtendimento(Integer idRegistroAtendimento) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select rgat.id," // 0
							+ " rgat.codigoSituacao," // 1
							+ " step.id, step.descricao," // 2,3
							+ " sotp.id, sotp.descricao," // 4,5
							+ " meso.id, meso.descricao," // 6,7
							+ " imov.id," // 8
							+ " loca.id," // 9
							+ " stcm.codigo," // 10
							+ " qdra.numeroQuadra," // 11
							+ " imov.lote," // 12
							+ " imov.subLote," // 13
							+ " rgat.registroAtendimento," // 14
							+ " rgat.dataPrevistaAtual," // 15
							+ " rgat.dataEncerramento," // 16
							+ " amen.id, amen.descricao," // 17, 18
							+ " rgat.pontoReferencia," // 19
							+ " brar.id,brar.nome,"// 20,21
							+ " bair.id, bair.nome,"// 22,23
							+ " dves.id, dves.descricao," // 24,25
							+ " lcrg.id, scrg.codigo, qdrg.numeroQuadra, " // 26,27,28
							+ " sotp.indicadorTarifaSocial, " // 29
							+ " rgat.ultimaAlteracao, " // 30
							+ " step.indicadorCliente, step.indicadorParecerEncerramento, " // 31,
							// 32
							+ " rgat.logradouroBairro.id, rgat.logradouroCep.id," // 33, 34
							+ " rgat.complementoEndereco," // 35
							+ " localOcorrencia.id, pavimentoRua.id, " // 36, 37
							+ " pavimentoCalcada.id, rgat.descricaoLocalOcorrencia, " // 38, 39
							+ " scrg.id, qdrg.id, "// 40, 41
							+ " muni.id, muni.nome, "// 42, 43
							+ " raMotivoReativacao.id, raMotivoReativacao.descricao, "// 44, 45
							+ " rgat.dataPrevistaOriginal, " // 46
							+ " rgat.observacao, " // 47
							+ " rgat.indicadorAtendimentoOnline, " // 48
							+ " rgat.dataInicioEspera, " // 49
							+ " rgat.dataFimEspera, " // 50
							+ " lcrg.descricao, " // 51
							+ " localOcorrencia.descricao, "// 52
							+ " pavimentoRua.descricao, " // 53
							+ " pavimentoCalcada.descricao, " // 54
							+ " scrg.descricao, " // 55
							+ " rgat.quantidadeReiteracao, " // 56
							+ " rgat.ultimaReiteracao, " // 57
							+ " rgat.parecerEncerramento, "// 58
							+ " last.id, last.descricao, "// 59, 60
							+ " lest.id, lest.descricao, "// 61, 62
							+ " step.indicadorGeracaoDebito, "// 63
							+ " step.indicadorGeracaoCredito, "// 64
							+ " rgat.manual, "// 65
							+ " amen.indicadorExecucao, " // 66
							+ " rota.codigo, " // 67
							+ " imov.numeroSequencialRota, " // 68
							+ " imov.numeroImovel, " // 69
							+ " rgat.senhaAtendimento, " // 70
							+ " ligacaoAgua.id, " // 71
							+ " corteTipo.id, " // 72
							+ " supressaoTipo.id, " // 73
							+ " rgat.reiteracao, " // 74
							+ " rotaImovel.id, " // 75
							+ " imov.numeroSegmento, " // 76
							+ " rgat.indicadorProcessoAdmJud, " // 77
							+ " rgat.numeroProcessoAgencia " // 78
							+ " from RegistroAtendimentoSolicitante ras "
							+ " left join ras.registroAtendimento rgat "
							+ " left join rgat.solicitacaoTipoEspecificacao step"
							+ " left join step.solicitacaoTipo sotp"
							+ " left join rgat.meioSolicitacao meso" + " left join rgat.imovel imov"
							+ " left join imov.localidade loca"
							+ " left join imov.setorComercial stcm" + " left join imov.quadra qdra"
							+ " left join qdra.rota rota"
							+ " left join imov.ligacaoAguaSituacao last"
							+ " left join imov.ligacaoEsgotoSituacao lest"
							+ " left join rgat.atendimentoMotivoEncerramento amen"
							+ " left join rgat.bairroArea brar"
							+ " left join brar.bairro bair" + " left join bair.municipio muni"
							+ " left join rgat.divisaoEsgoto dves"
							+ " left join rgat.localidade lcrg" + " left join rgat.setorComercial scrg"
							+ " left join rgat.quadra qdrg"
							+ " left join rgat.raMotivoReativacao raMotivoReativacao"
							+ " left join rgat.pavimentoRua pavimentoRua"
							+ " left join rgat.pavimentoCalcada pavimentoCalcada"
							+ " left join rgat.localOcorrencia localOcorrencia"
							+ " left join imov.ligacaoAgua ligacaoAgua"
							+ " left join ligacaoAgua.corteTipo corteTipo"
							+ " left join ligacaoAgua.supressaoTipo supressaoTipo" + " left join imov.rota rotaImovel"

							+ " where rgat.id  = :idRegistroAtendimento";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0452] Obter Dados do Registro Atendimento
	 * Pesquisar dados do registro de atendimento solicitante
	 * 
	 * @author Ana Maria
	 * @date 15/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Object[] pesquisarDadosRASolicitante(Integer idRegistroAtendimento) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select clie.id, clie.nome, " // 0,1
							+ " unid.id, unid.descricao," // 2,3
							+ " raso.solicitante, raso.id," // 4, 5
							+ " raso.clienteTipo, raso.numeroCpf," // 6, 7
							+ " raso.numeroRG, raso.orgaoExpedidorRg," // 8, 9
							+ " raso.unidadeFederacaoRG, raso.numeroCnpj" // 10, 11
							+ " from RegistroAtendimentoSolicitante raso"
							+ " left join raso.registroAtendimento rgat"
							+ " left join raso.cliente clie"
							+ " left join raso.unidadeOrganizacional unid"
							+ " left join raso.orgaoExpedidorRg"
							+ " left join raso.unidadeFederacaoRG"
							+ " where rgat.id  = :idRegistroAtendimento and raso.indicadorSolicitantePrincipal = :indicadorSolicitantePrincipal";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento)
							.setInteger("indicadorSolicitantePrincipal", 1).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0408] Atualizar Registro Atendimento
	 * Pesquisar Indicador falta de agua da RA
	 * 
	 * @author Sávio Luiz
	 * @date 16/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Object[] pesquisarIndicadorFaltaAguaRA(Integer idEspecificacao) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select sotp.indicadorFaltaAgua, " + "step.indicadorMatricula " + " from SolicitacaoTipoEspecificacao step"
							+ " left join step.solicitacaoTipo sotp" + " where step.id  = :idEspecificacao";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idEspecificacao", idEspecificacao).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0408] Atualizar Registro Atendimento
	 * [SB0018] Verificar Programação de Abastecimento e/ou Manutenção
	 * 
	 * @author Sávio Luiz
	 * @date 16/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Integer verificarOcorrenciaAbastecimentoProgramacao(Date dataAbastecimentoRA, Integer idBairroArea, Integer idBairro)
					throws ErroRepositorioException{

		Integer retorno = null;

		Integer amAtendimentoRA = Util.recuperaAnoMesDaData(dataAbastecimentoRA);

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select abastProgramacao.id " + " from AbastecimentoProgramacao abastProgramacao"
							+ " LEFT JOIN abastProgramacao.bairroArea baiArea" + " LEFT JOIN abastProgramacao.bairro bai"
							+ " LEFT JOIN bai.municipio municBairro" + " LEFT JOIN abastProgramacao.municipio municAbastProgramacao"
							+ " LEFT JOIN baiArea.distritoOperacional disOpeBaiArea"
							+ " LEFT JOIN abastProgramacao.distritoOperacional disOpeAbastProgramacao"
							+ " LEFT JOIN abastProgramacao.zonaAbastecimento zonaAbasAbastProgramacao"
							+ " LEFT JOIN abastProgramacao.setorAbastecimento setorAbasAbastProgramacao"
							+ " LEFT JOIN abastProgramacao.sistemaAbastecimento sistAbasAbastProgramacao"
							+ " where abastProgramacao.anoMesReferencia = :amAtendimentoRA AND"
							+ " ((abastProgramacao.dataInicio < :dataAbastecimentoRA AND "
							+ " abastProgramacao.dataFim > :dataAbastecimentoRA) OR"
							+ "(abastProgramacao.dataInicio = :dataAbastecimentoRA AND"
							+ " abastProgramacao.horaInicio <= :horaAtendimento AND" + " abastProgramacao.horaFim >= :horaAtendimento ) OR"
							+ "(abastProgramacao.dataFim = :dataAbastecimentoRA AND"
							+ " abastProgramacao.horaInicio <= :horaAtendimento AND"
							+ " abastProgramacao.horaFim >= :horaAtendimento)) AND "
							+ " (baiArea.id = :idBairroArea OR bai.id = :idBairro OR "
							+ " (bai.id = :idBairro AND municBairro.id = municAbastProgramacao.id) OR"
							+ " (baiArea.id = :idBairroArea AND disOpeBaiArea.id = disOpeAbastProgramacao.id) OR"
							+ " (baiArea.id = :idBairroArea) OR" + " (baiArea.id = :idBairroArea) OR" + " (baiArea.id = :idBairroArea))";

			Date horaAtendimento = dataAbastecimentoRA;

			retorno = (Integer) session.createQuery(consulta).setDate("dataAbastecimentoRA", dataAbastecimentoRA)
							.setInteger("amAtendimentoRA", amAtendimentoRA).setTime("horaAtendimento", horaAtendimento)
							.setInteger("idBairroArea", idBairroArea).setInteger("idBairro", idBairro).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0452] Obter Dados do Registro Atendimento
	 * [SB0018] Verificar Programação de Abastecimento e/ou Manutenção
	 * 
	 * @author Sávio Luiz
	 * @date 16/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public String pesquisarNomeBairroArea(Integer idBairroArea) throws ErroRepositorioException{

		String retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select bairroArea.nome " + " from BairroArea bairroArea" + " Where bairroArea.id = :idBairroArea";
			retorno = (String) session.createQuery(consulta).setInteger("idBairroArea", idBairroArea).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * [UC0366] Inserir Registro Atendimento
	 * 
	 * @author Raphael Rossiter
	 * @date 09/02/2007
	 * @param idSolicitacaoTipoEspecificacao
	 * @throws ControladorException
	 */
	public String pesquisarDescricaoSolicitacaoTipoEspecificacao(Integer idSolicitacaoTipoEspecificacao) throws ErroRepositorioException{

		String retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " SELECT step.descricao" + " FROM SolicitacaoTipoEspecificacao step"
							+ " WHERE step.id = :idSolicitacaoTipoEspecificacao ";

			retorno = (String) session.createQuery(consulta).setInteger("idSolicitacaoTipoEspecificacao", idSolicitacaoTipoEspecificacao)
							.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * [UC0452] Obter Dados do Registro Atendimento
	 * [SB0018] Verificar Programação de Abastecimento e/ou Manutenção
	 * 
	 * @author Sávio Luiz
	 * @date 16/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Integer verificarOcorrenciaManutencaoProgramacao(Date dataAbastecimentoRA, Integer idBairroArea, Integer idBairro)
					throws ErroRepositorioException{

		Integer retorno = null;

		Integer amAtendimentoRA = Util.recuperaAnoMesDaData(dataAbastecimentoRA);

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select manutProgramacao.id " + " from ManutencaoProgramacao manutProgramacao"
							+ " LEFT JOIN manutProgramacao.bairroArea baiArea" + " LEFT JOIN manutProgramacao.bairro bai"
							+ " LEFT JOIN bai.municipio municBairro" + " LEFT JOIN manutProgramacao.municipio municManuProgramacao"
							+ " LEFT JOIN baiArea.distritoOperacional disOpeBaiArea"
							+ " LEFT JOIN manutProgramacao.distritoOperacional disOpeManuProgramacao"
							+ " LEFT JOIN manutProgramacao.zonaAbastecimento zonaAbasManuProgramacao"
							+ " LEFT JOIN manutProgramacao.setorAbastecimento setorAbasManuProgramacao"
							+ " LEFT JOIN manutProgramacao.sistemaAbastecimento sistAbasManuProgramacao"
							+ " where manutProgramacao.anoMesReferencia = :amAtendimentoRA AND"
							+ " ((manutProgramacao.dataInicio < :dataAbastecimentoRA AND "
							+ " manutProgramacao.dataFim > :dataAbastecimentoRA) OR"
							+ "(manutProgramacao.dataInicio = :dataAbastecimentoRA AND"
							+ " manutProgramacao.horaInicio <= :horaAtendimento AND" + " manutProgramacao.horaFim >= :horaAtendimento ) OR"
							+ "(manutProgramacao.dataFim = :dataAbastecimentoRA AND"
							+ " manutProgramacao.horaInicio <= :horaAtendimento AND"
							+ " manutProgramacao.horaFim >= :horaAtendimento)) AND "
							+ " (baiArea.id = :idBairroArea OR bai.id = :idBairro OR "
							+ " (bai.id = :idBairro AND municBairro.id = municManuProgramacao.id) OR"
							+ " (baiArea.id = :idBairroArea AND disOpeBaiArea.id = disOpeManuProgramacao.id) OR"
							+ " (baiArea.id = :idBairroArea) OR" + " (baiArea.id = :idBairroArea) OR" + " (baiArea.id = :idBairroArea))";

			Date horaAtendimento = dataAbastecimentoRA;

			retorno = (Integer) session.createQuery(consulta).setDate("dataAbastecimentoRA", dataAbastecimentoRA)
							.setInteger("amAtendimentoRA", amAtendimentoRA).setTime("horaAtendimento", horaAtendimento)
							.setInteger("idBairroArea", idBairroArea).setInteger("idBairro", idBairro).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0408] Atualizar Registro Atendimento
	 * Pesquisar Indicador falta de agua da RA
	 * 
	 * @author Sávio Luiz
	 * @date 16/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Collection pesquisarRAAreaBairro(Integer idRegistroAtendimento, Integer idBairroArea, Integer idEspecificacao)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select new gcom.atendimentopublico.registroatendimento.bean.ExibirRAFaltaAguaImovelHelper"
							+ "(rgat.id,rgat.imovel.id,rgat.registroAtendimento) " + " from RegistroAtendimento rgat"
							+ " left join rgat.bairroArea baiArea" + " left join rgat.solicitacaoTipoEspecificacao step"
							+ " where baiArea.id  = :idBairroArea AND" + " rgat.codigoSituacao = :codSituacao AND"
							+ " rgat.id <> :idRegistroAtendimento AND" + " step.id = :idEspecificacao";

			retorno = session.createQuery(consulta).setInteger("idBairroArea", idBairroArea)
							.setInteger("idRegistroAtendimento", idRegistroAtendimento)
							.setShort("codSituacao", RegistroAtendimento.SITUACAO_PENDENTE).setInteger("idEspecificacao", idEspecificacao)
							.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro Atendimento
	 * Pesquisar Indicador falta de agua da RA
	 * 
	 * @author Raphael Rossiter
	 * @date 16/08/2006
	 * @param idRegistroAtendimento
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarRAAreaBairroInserir(Integer idBairroArea, Integer idEspecificacao) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select new gcom.atendimentopublico.registroatendimento.bean.ExibirRAFaltaAguaImovelHelper"
							+ "(rgat.id,rgat.imovel.id,rgat.registroAtendimento) " + " from RegistroAtendimento rgat"
							+ " left join rgat.bairroArea baiArea" + " left join rgat.solicitacaoTipoEspecificacao step"
							+ " where baiArea.id  = :idBairroArea AND" + " rgat.codigoSituacao = :codSituacao AND"
							+ " step.id = :idEspecificacao";

			retorno = session.createQuery(consulta).setInteger("idBairroArea", idBairroArea)
							.setShort("codSituacao", RegistroAtendimento.SITUACAO_PENDENTE).setInteger("idEspecificacao", idEspecificacao)
							.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0408] Atualizar Registro Atendimento
	 * Pesquisar id do AtendimentoEncerramentoMotivo
	 * 
	 * @author Sávio Luiz
	 * @date 16/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Integer pesquisarIdAtendimentoEncerramentoMotivo() throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select atenMotEncerramento.id " + " from RegistroAtendimento rgat"
							+ " left join rgat.atendimentoMotivoEncerramento atenMotEncerramento"
							+ " where atenMotEncerramento.indicadorDuplicidade  = :indicadorDuplicidade";

			retorno = (Integer) session.createQuery(consulta)
							.setInteger("indicadorDuplicidade", AtendimentoMotivoEncerramento.INDICADOR_DUPLICIDADE_ATIVO).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0408] Atualizar Registro Atendimento
	 * Pesquisar o id do registro atendimento para a area bairro especifica
	 * 
	 * @author Sávio Luiz
	 * @date 16/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Integer pesquisarRAAreaBairroFaltaAguaImovel(Integer idBairroArea) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select rgat.id " + " from RegistroAtendimento rgat" + " left join rgat.bairroArea baiArea"
							+ " left join rgat.solicitacaoTipoEspecificacao step" + " left join step.solicitacaoTipo sotp"
							+ " where baiArea.id  = :idBairroArea AND" + " rgat.codigoSituacao = :codSituacao AND"
							+ " step.indicadorMatricula = :indicadorMatricula AND" + " sotp.indicadorFaltaAgua = :indicadorFalAgua ";

			retorno = (Integer) session.createQuery(consulta).setInteger("idBairroArea", idBairroArea)
							.setShort("codSituacao", RegistroAtendimento.SITUACAO_PENDENTE)
							.setInteger("indicadorMatricula", SolicitacaoTipoEspecificacao.INDICADOR_MATRICULA_NAO_OBRIGATORIO)
							.setShort("indicadorFalAgua", SolicitacaoTipo.INDICADOR_USO_ATIVO).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0408] Atualizar Registro Atendimento
	 * Pesquisar a descrição da solicitacao do tipo de especificação
	 * 
	 * @author Sávio Luiz
	 * @date 16/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public String descricaoSolTipoEspecAguaGeneralizada(Integer idSolicitacaoTipoEspecificacao) throws ErroRepositorioException{

		String retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select step.descricao " + " from SolicitacaoTipoEspecificacao step"
							+ " where step.id  = :idSolicitacaoTipoEspecificacao";

			retorno = (String) session.createQuery(consulta).setInteger("idSolicitacaoTipoEspecificacao", idSolicitacaoTipoEspecificacao)
							.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0408] Atualizar Registro Atendimento
	 * Pesquisar a descrição da solicitacao do tipo de especificação
	 * 
	 * @author Sávio Luiz
	 * @date 16/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public String descricaoSolTipoEspecFaltaAguaGeneralizada() throws ErroRepositorioException{

		String retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select step.descricao " + " from SolicitacaoTipoEspecificacao step" + " left join step.solicitacaoTipo solTipo"
							+ " where step.indicadorMatricula  = :indMatricula AND" + " solTipo.indicadorFaltaAgua = :indFaltaAgua";

			retorno = (String) session.createQuery(consulta)
							.setInteger("indMatricula", SolicitacaoTipoEspecificacao.INDICADOR_MATRICULA_NAO_OBRIGATORIO)
							.setShort("indFaltaAgua", SolicitacaoTipo.INDICADOR_FALTA_AGUA_SIM).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0408] Atualizar Registro Atendimento
	 * Pesquisar a descrição da solicitacao do tipo de especificação
	 * 
	 * @author Sávio Luiz
	 * @date 16/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public String descricaoSolTipoEspecAguaImovel(Integer idSolicitacaoTipoEspecificacao) throws ErroRepositorioException{

		String retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select step.descricao " + " from SolicitacaoTipoEspecificacao step" + " left join step.solicitacaoTipo sotp"
							+ " where step.id  = :idSolicitacaoTipoEspecificacao AND"
							+ " step.indicadorMatricula = :indicadorMatricula AND" + " sotp.indicadorFaltaAgua = :indicadorFalAgua";

			retorno = (String) session.createQuery(consulta).setInteger("idSolicitacaoTipoEspecificacao", idSolicitacaoTipoEspecificacao)
							.setInteger("indicadorMatricula", SolicitacaoTipoEspecificacao.INDICADOR_MATRICULA_OBRIGATORIO)
							.setShort("indicadorFalAgua", SolicitacaoTipo.INDICADOR_USO_ATIVO).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0408] Atualizar Registro Atendimento
	 * Pesquisar parms registro atendimento e jogando o objeto helper
	 * 
	 * @author Sávio Luiz
	 * @date 16/08/2006
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Object[] pesquisarParmsRAFaltaAguaImovel(Integer idRegistroAtendimento) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select sotp.id,sotp.descricao,step.id,step.descricao,"// 0,1,2,3
							+ " bai.codigo,bai.nome,baiArea.id,baiArea.nome "// 4,5,6,7
							+ " from RegistroAtendimento rgat"
							+ " left join rgat.solicitacaoTipoEspecificacao step"
							+ " left join step.solicitacaoTipo sotp"
							+ " left join rgat.bairroArea baiArea"
							+ " left join baiArea.bairro bai" + " left join rgat.imovel imov" + " where rgat.id  = :idRegistroAtendimento";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0413] Pesquisar Tipo de Serviço
	 * select a.svtp_id from ATENDIMENTOPUBLICO.SERVICO_TIPO A,
	 * ATENDIMENTOPUBLICO.SERVICO_TIPO_ATIVIDADE B,
	 * ATENDIMENTOPUBLICO.SERVICO_TIPO_MATERIAL C WHERE A.SVTP_DSSERVICOTIPO
	 * LIKE '%DESC%' AND A.SVTP_DSABREVIADO LIKE '%DESC%' AND (A.SVTP_VLSERVICO >=
	 * 000000 AND A.SVTP_VLSERVICO <= 99999) AND A.SVTP_ICPAVIMENTO = 1 OU 2 and
	 * A.SVTP_ICATUALIZACOMERCIAL = 1 OU 2 AND A.SVTP_ICTERCEIRIZADO = 1 OU 2
	 * AND A.SVTP_CDSERVICOTIPO = ("O" OR "C") AND (A.SVTP_NNTEMPOMEDIOEXECUCAO >=
	 * 0000 AND A.SVTP_NNTEMPOMEDIOEXECUCAO <= 9999) AND DBTP_ID = ID INFORMADO
	 * AND AND CRTP_ID = ID INFORMADO AND STSG_ID = ID INFORMADO AND STRF_ID =
	 * ID INFORMADO AND STPR_ID = ID INFORMADO AND A.SVTP_ID = B.SVTP_ID AND
	 * B.ATIV_ID IN (ID's INFORMADOS) AND A.SVTP_ID = C.SVTP_ID AND C.MATE_ID IN
	 * (ID's INFORMADOS)
	 * 
	 * @author Leandro Cavalcanti
	 * @date 17/08/2006
	 */
	public Collection<ServicoTipo> filtrarST(ServicoTipo servicoTipo, Collection colecaoAtividades, Collection colecaoMaterial,
					String valorServicoInicial, String valorServicoFinal, String tempoMedioExecucaoInicial, String tempoMedioExecucaoFinal)
					throws ErroRepositorioException{

		Collection<ServicoTipo> retorno = null;
		Session session = HibernateUtil.getSession();

		String consulta = "";
		Query query = null;
		Map parameters = new HashMap();

		try{

			consulta = "SELECT svtp.id,svtp.descricao," + "svtp.servicoTipoPrioridade.descricao," + "svtp.indicadorAtualizaComercial,"
							+ "svtp.indicadorPavimento," + "svtp.indicadorTerceirizado" + " FROM ServicoTipo svtp  "
							+ " LEFT JOIN svtp.servicoTipoSubgrupo subg " + " LEFT JOIN svtp.debitoTipo tpdb"
							+ " LEFT JOIN svtp.creditoTipo tpcd " + " LEFT JOIN svtp.servicoTipoPrioridade tppri "
							+ " LEFT JOIN svtp.servicoTipoReferencia tpref  " + " LEFT JOIN svtp.servicoPerfilTipo perftp " + " WHERE 1=1 ";

			if(servicoTipo.getDescricao() != null && !servicoTipo.getDescricao().equals("")){
				consulta += " AND svtp.descricao LIKE %:descricao% AND svtp.descricao LIKE :descricaoAbreviada";
				parameters.put("descricao", servicoTipo.getDescricao());
				parameters.put("descricaoAbreviada", servicoTipo.getDescricao());
			}
			if(servicoTipo.getDescricaoAbreviada() != null && !servicoTipo.getDescricaoAbreviada().equals("")){
				consulta += " AND svtp.descricaoAbreviada LIKE :descricao% AND svtp.descricaoAbreviada LIKE :descricaoAbreviada%";
				parameters.put("descricao", servicoTipo.getDescricaoAbreviada());
				parameters.put("descricaoAbreviada", servicoTipo.getDescricaoAbreviada());
			}

			if(servicoTipo.getServicoTipoSubgrupo() != null && !servicoTipo.getServicoTipoSubgrupo().equals("")){
				consulta += " AND subg.id = (:idSubg) ";
				parameters.put("idSubg", servicoTipo.getServicoTipoSubgrupo().getId());
			}

			if(Short.valueOf(servicoTipo.getIndicadorPavimento()) != 0){
				consulta += " AND indpv.id = (:idIndpv) ";
				parameters.put("idIndpv", servicoTipo.getIndicadorPavimento());
			}

			if(!valorServicoInicial.equalsIgnoreCase("") && !valorServicoFinal.equalsIgnoreCase("")){
				consulta += " AND svtp.valor BETWEEN (:valorInicial) AND (:valorFinal) ";
				parameters.put("valorInicial", valorServicoInicial);
				parameters.put("valorFinal", valorServicoFinal);
			}

			if(Short.valueOf(servicoTipo.getIndicadorAtualizaComercial()) != 0){

				if(servicoTipo.getIndicadorAtualizaComercial() == 1){
					consulta += " AND indac.id in (1,3) ";

				}else if(servicoTipo.getIndicadorAtualizaComercial() == 2){
					consulta += " AND indac.id = 1 ";

				}else if(servicoTipo.getIndicadorAtualizaComercial() == 3){
					consulta += " AND indac.id = 3 ";

				}else if(servicoTipo.getIndicadorAtualizaComercial() == 4){
					consulta += " AND indac.id in (1,2,3)  ";
				}else if(servicoTipo.getIndicadorAtualizaComercial() == 5){
					consulta += " AND indac.id = 2 ";
				}
			}

			if(servicoTipo.getCodigoServicoTipo() != null && !servicoTipo.getCodigoServicoTipo().equals("")){
				consulta += " AND codsvtp.id = (:codSvtp) ";
				parameters.put("codSvtp", servicoTipo.getCodigoServicoTipo());
			}

			if(!tempoMedioExecucaoInicial.equalsIgnoreCase("") && tempoMedioExecucaoFinal.equalsIgnoreCase("")){
				consulta += " AND svtp.tempmedio BETWEEN (:tempoInicial) AND (:tempoFinal) ";
				parameters.put("tempoInicial", tempoMedioExecucaoInicial);
				parameters.put("tempoFinal", tempoMedioExecucaoFinal);

			}else if(!tempoMedioExecucaoInicial.equalsIgnoreCase("") && tempoMedioExecucaoFinal.equalsIgnoreCase("")){
				consulta += " AND svtp.tempmedio BETWEEN (:tempoInicial) AND (:tempoFinal) ";
				parameters.put("tempoInicial", tempoMedioExecucaoInicial);
				parameters.put("tempoFinal", 9999);
			}else if(!tempoMedioExecucaoFinal.equalsIgnoreCase("") && tempoMedioExecucaoInicial.equalsIgnoreCase("")){
				consulta += " AND svtp.tempmedio BETWEEN (:tempoInicial) AND (:tempoFinal) ";
				parameters.put("tempoInicial", 0000);
				parameters.put("tempoFinal", tempoMedioExecucaoFinal);
			}
			if(servicoTipo.getDebitoTipo() != null){
				if(servicoTipo.getDebitoTipo().getId() != null && !servicoTipo.getDebitoTipo().getId().equals("")){
					consulta += " AND tpdb.id = (:idDeb) ";
					parameters.put("idDeb", servicoTipo.getDebitoTipo().getId());
				}
			}
			if(servicoTipo.getCreditoTipo() != null){
				if(servicoTipo.getCreditoTipo().getId() != null && !servicoTipo.getCreditoTipo().getId().equals("")){
					consulta += " AND tpcd.id = (:idTpcd) ";
					parameters.put("idTpcd", servicoTipo.getCreditoTipo().getId());
				}
			}
			if(servicoTipo.getServicoTipoPrioridade() != null){
				if(servicoTipo.getServicoTipoPrioridade().getId() != null && !servicoTipo.getServicoTipoPrioridade().getId().equals("")){
					consulta += " AND tppri.id = (:idTpcd) ";
					parameters.put("idTpcd", servicoTipo.getServicoTipoPrioridade().getId());
				}
			}
			if(servicoTipo.getServicoPerfilTipo() != null){
				if(servicoTipo.getServicoPerfilTipo().getId() != null && !servicoTipo.getServicoPerfilTipo().getId().equals("")){
					consulta += " AND perftp.id = (:idPerf) ";
					parameters.put("idPerf", servicoTipo.getServicoPerfilTipo().getId());
				}
			}
			if(servicoTipo.getServicoTipoReferencia() != null){
				if(servicoTipo.getServicoTipoReferencia().getId() != null && servicoTipo.getServicoTipoReferencia().getId().equals("")){
					consulta += " AND tpref.id = (:idRef) ";
					parameters.put("idRef", servicoTipo.getServicoTipoReferencia().getId());
				}
			}

			if(colecaoMaterial != null && !colecaoMaterial.isEmpty()){
				consulta += " AND srvtpmat.id in (:idMat) ";
				parameters.put("idMat", colecaoMaterial);
			}

			if(colecaoAtividades != null && !colecaoAtividades.isEmpty()){
				consulta += " AND srvtpatv.id in (:idAt) ";
				parameters.put("idAt", colecaoAtividades);
			}

			query = session.createQuery(consulta);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Set){
					Set setList = (HashSet) parameters.get(key);
					query.setParameterList(key, setList);
				}else if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}

			retorno = query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * [UC0427] Tramitar Registro Atendimento
	 * [FS009] atualização realizada por outro usuário
	 * 
	 * @author Leonardo Regis
	 * @date 22/08/2006
	 * @param idRegistroAtendimento
	 * @return dataAtual
	 * @throws ErroRepositorioException
	 */
	public Date verificarConcorrenciaRA(Integer idRegistroAtendimento) throws ErroRepositorioException{

		Date retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{
			consulta = " select rgat.ultimaAlteracao" // 0
							+ " from RegistroAtendimento rgat" + " where rgat.id  = :idRegistroAtendimento";

			retorno = (Date) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso esteja adicionando um novo solicitante e o cliente já seja um
	 * solicitante do registro de atendimento (existe Ocorrência na tabela
	 * REGISTRO_ATENDIMENTO_SOLICITANTE com RGAT_ID=RGAT_ID do registro de
	 * atendimento selecionado e CLIE_ID=Id do Cliente informado).
	 * [FS0012] Verificar existência do cliente solicitante
	 * 
	 * @author Raphael Rossiter
	 * @date 21/08/2006
	 * @param idRegistroAtendimento
	 *            ,
	 *            idCliente
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaClienteSolicitante(Integer idRegistroAtendimento, Integer idCliente) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select COUNT(*)" + " from RegistroAtendimentoSolicitante raso" + " left join raso.registroAtendimento rgat"
							+ " left join raso.cliente clie" + " where rgat.id  = :idRegistroAtendimento AND clie.id = :idCliente";

			retorno = ((Number) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento)
							.setInteger("idCliente", idCliente).setMaxResults(1).uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Caso esteja adicionando um novo solicitante e a unidade já seja um
	 * solicitante do registro de atendimento (existe Ocorrência na tabela
	 * REGISTRO_ATENDIMENTO_SOLICITANTE com RGAT_ID=RGAT_ID do registro de
	 * atendimento selecionado e UNID_ID=Id da Unidade informada).
	 * [FS0026] Verificar existência da unidade solicitante
	 * 
	 * @author Raphael Rossiter
	 * @date 23/08/2006
	 * @param idRegistroAtendimento
	 *            ,
	 *            idUnidade
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaUnidadeSolicitante(Integer idRegistroAtendimento, Integer idUnidade) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select COUNT(*)" + " from RegistroAtendimentoSolicitante raso" + " left join raso.registroAtendimento rgat"
							+ " left join raso.unidadeOrganizacional unid"
							+ " where rgat.id  = :idRegistroAtendimento AND unid.id = :idUnidade";

			retorno = ((Number) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento)
							.setInteger("idUnidade", idUnidade).setMaxResults(1).uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0435] Encerrar Registro de Atendimento
	 * Verificar maior data de encerramento das ordens de Serviço de um RA
	 * 
	 * @author Leonardo Regis
	 * @date 26/08/2006
	 * @param idRegistroAtendimento
	 * @return Maior Data de Encerramento da OS de um RA
	 * @throws ControladorException
	 */
	public Date obterMaiorDataEncerramentoOSRegistroAtendimento(Integer idRegistroAtendimento) throws ErroRepositorioException{

		Date retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = "";
		try{
			consulta = " SELECT max(os.dataEncerramento) " + " FROM OrdemServico os " + " INNER JOIN os.registroAtendimento rgat  "
							+ " WHERE rgat.id = :idRA " + " AND os.situacao = :situacao";

			retorno = (Date) session.createQuery(consulta).setInteger("idRA", idRegistroAtendimento)
							.setShort("situacao", OrdemServico.SITUACAO_ENCERRADO).setMaxResults(1).uniqueResult();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0435] Encerrar Registro de Atendimento
	 * Permite encerrar o ra atualizando a tabela REGISTRO_ATENDIMENTO
	 * 
	 * @author Leonardo Regis
	 * @date 29/08/2006
	 * @param registroAtendimento
	 */
	public void encerrarRegistroAtendimento(RegistroAtendimento registroAtendimento) throws ErroRepositorioException{

		// TODO Rodrigo Oliveira Remover syout testeLentidaoRA
		Date dataInicio = new Date();

		Session session = HibernateUtil.getSession();
		Query query = null;
		String encerrarRA;
		try{

			encerrarRA = " UPDATE RegistroAtendimento " + " SET atendimentoMotivoEncerramento.id = :motivoEncerramentoId, "
							+ " codigoSituacao = :situacao, ";
			if(registroAtendimento.getRegistroAtendimentoDuplicidade() != null){
				encerrarRA += "registroAtendimentoDuplicidade.id = :raDuplicidadeId, ";
			}
			encerrarRA += " dataEncerramento = :dataEncerramento, " + " parecerEncerramento = :parecerEncerramento, "
							+ " ultimaAlteracao = :dataUltimaAlteracao " + " WHERE id = :registroAtendimentoId";

			query = session.createQuery(encerrarRA)
							.setInteger("motivoEncerramentoId", registroAtendimento.getAtendimentoMotivoEncerramento().getId())
							.setShort("situacao", RegistroAtendimento.SITUACAO_ENCERRADO)
							.setTimestamp("dataEncerramento", registroAtendimento.getDataEncerramento())
							.setString("parecerEncerramento", registroAtendimento.getParecerEncerramento())
							.setTimestamp("dataUltimaAlteracao", new Date())
							.setInteger("registroAtendimentoId", registroAtendimento.getId());
			if(registroAtendimento.getRegistroAtendimentoDuplicidade() != null){
				query.setInteger("raDuplicidadeId", registroAtendimento.getRegistroAtendimentoDuplicidade().getId());
			}
			query.executeUpdate();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		// TODO Rodrigo Oliveira Remover syout testeLentidaoRA
		System.out.println("testeLentidaoRA ## " + " ## Fim do método RepositorioRegistroAtendimentoHBM -> encerrarRegistroAtendimento: "
						+ Util.diferencaSegundos(dataInicio, new Date()) + " s ");
		System.out.println("testeLentidaoRA Número RA: " + registroAtendimento.getId());
	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * pesquisar o registro atendimento unidade com o id do registro atendimento
	 * e o id do atendimento relação tipo igual a abrir/registrar
	 * 
	 * @author Sávio Luiz
	 * @date 30/08/2006
	 * @param idRegistroAtendimento
	 * @throws ErroRepositorioException
	 */
	public RegistroAtendimentoUnidade pesquisarRAUnidade(Integer idRegistroAtendimento) throws ErroRepositorioException{

		RegistroAtendimentoUnidade retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select rau" + " from RegistroAtendimentoUnidade rau" + " left join rau.atendimentoRelacaoTipo atenRelTipo"
							+ " left join rau.registroAtendimento rgat" + " where rgat.id  = :idRegistroAtendimento AND"
							+ " atenRelTipo.id = :idAtenRelacaoTipo";

			retorno = (RegistroAtendimentoUnidade) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento)
							.setInteger("idAtenRelacaoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * pesquisar o registro atendimento unidade com o id do registro atendimento
	 * e o id do atendimento relação tipo igual a abrir/registrar
	 * 
	 * @author Sávio Luiz
	 * @date 30/08/2006
	 * @param idRegistroAtendimento
	 * @throws ErroRepositorioException
	 */
	public void removerSolicitanteFone(Integer idRegistroAtendimentoSolicitante) throws ErroRepositorioException{

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		// Cria a variável que vai conter o hql
		String consulta;

		try{

			// Constroi o hql para remover os dados diários da arrecadação
			// referentes ao ano/mês de arrecadação atual
			consulta = "delete gcom.atendimentopublico.registroatendimento.SolicitanteFone solFone where solFone.registroAtendimentoSolicitante.id = :idRegistroAtendimentoSolicitante";

			// Executa o hql
			session.createQuery(consulta).setInteger("idRegistroAtendimentoSolicitante", idRegistroAtendimentoSolicitante).executeUpdate();

			// Erro no hibernate
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sesão com o hibernate
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * [UC0426] Reativar Registro de Atendimento
	 * 
	 * @author Ana Maria
	 * @date 24/08/2006
	 * @param especificacao
	 * @return UnidadeOrganizacional
	 * @throws ControladorException
	 */
	// public UnidadeOrganizacional obterUnidadeDestinoEspecificacao(
	// Integer especificacao) throws ErroRepositorioException {
	//
	// UnidadeOrganizacional retorno = null;
	//
	// Session session = HibernateUtil.getSession();
	//
	// String consulta = "";
	//
	// try {
	// consulta = " select unid "
	// + " from SolicitacaoTipoEspecificacao step "
	// + " inner join step.unidadeOrganizacional unid "
	// + " where step.id = :especificacao";
	//
	// retorno = (UnidadeOrganizacional) session.createQuery(consulta)
	// .setInteger("especificacao", especificacao)
	// .setMaxResults(1).uniqueResult();
	//
	// } catch (HibernateException e) {
	// throw new ErroRepositorioException(e, "Erro no Hibernate");
	// } finally {
	// HibernateUtil.closeSession(session);
	// }
	//
	// return retorno;
	// }

	/**
	 * [UC0426] Reativar Registro de Atendimento
	 * 
	 * @author Ana Maria
	 * @date 24/08/2006
	 * @param idRaSolicitante
	 * @return RegistroAtendimentoSolicitante
	 * @throws ControladorException
	 */
	public RegistroAtendimentoSolicitante obterRegistroAtendimentoSolicitante(Integer idRaSolicitante) throws ErroRepositorioException{

		RegistroAtendimentoSolicitante retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select raso " + " from RegistroAtendimentoSolicitante raso" + " where raso.id = :idRaSolicitante";

			retorno = (RegistroAtendimentoSolicitante) session.createQuery(consulta).setInteger("idRaSolicitante", idRaSolicitante)
							.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0426] Reativar Registro de Atendimento
	 * 
	 * @author Ana Maria
	 * @date 29/08/2006
	 * @param idRaSolicitante
	 * @return RegistroAtendimentoSolicitante
	 * @throws ControladorException
	 */
	public Collection pesquisarFoneSolicitante(Integer idRaSolicitante) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select sofo " + " from SolicitanteFone sofo " + " inner join sofo.registroAtendimentoSolicitante raso "
							+ " where raso.id = :idRaSolicitante";

			retorno = session.createQuery(consulta).setInteger("idRaSolicitante", idRaSolicitante).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * SOTP_ICFALTAAGUA da tabela SOLICITACAO_TIPO com o valor correspondente a
	 * um e STEP_ICMATRICULA com o valor correspondente a dois na tabela
	 * SOLICITACAO_TIPO_ESPECIFICACAO).
	 * 
	 * @author Raphael Rossiter
	 * @date 29/08/2006
	 * @return idEspecificacao, idTipo
	 * @throws ControladorException
	 */
	public Collection pesquisarTipoEspecificacaoFaltaAguaGeneralizada() throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " SELECT sotp.id, step.id " + " FROM SolicitacaoTipoEspecificacao step " + " INNER JOIN step.solicitacaoTipo sotp "
							+ " WHERE step.indicadorMatricula = 2 AND sotp.indicadorFaltaAgua = 1 order by step.id";

			retorno = session.createQuery(consulta).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * pesquisa os fones do regsitro atendimento solicitante
	 * 
	 * @author Sávio Luiz
	 * @date 05/09/2006
	 * @return idRASolicitante
	 * @throws ControladorException
	 */
	public Collection pesquisarParmsFoneRegistroAtendimentoSolicitante(Integer idRASolicitante) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " SELECT solFone.ddd, solFone.fone,solFone.ramal,solFone.indicadorPadrao,ft.id,ft.descricao,solFone.ultimaAlteracao "
							+ " FROM SolicitanteFone solFone "
							+ " INNER JOIN solFone.foneTipo ft "
							+ " WHERE solFone.registroAtendimentoSolicitante.id = :idRASolicitante";

			retorno = session.createQuery(consulta).setInteger("idRASolicitante", idRASolicitante).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0408] Inserir Registro de Atendimento
	 * Caso esteja adicionando um novo solicitante e o cliente já seja um
	 * solicitante do registro de atendimento (existe Ocorrência na tabela
	 * REGISTRO_ATENDIMENTO_SOLICITANTE com RGAT_ID=RGAT_ID do registro de
	 * atendimento selecionado e CLIE_ID=Id do Cliente informado e o registro
	 * atendimento solicitante for diferente do que está sendo atualizado).
	 * [FS0027] Verificar existência do cliente solicitante
	 * 
	 * @author Sávio Luiz
	 * @date 21/08/2006
	 * @param idRegistroAtendimento
	 *            ,
	 *            idCliente
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaClienteSolicitanteAtualizar(Integer idRegistroAtendimento, Integer idCliente,
					Integer idRegistroAtendimentoSolicitante) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select COUNT(*)" + " from RegistroAtendimentoSolicitante raso" + " left join raso.registroAtendimento rgat"
							+ " left join raso.cliente clie" + " where rgat.id  = :idRegistroAtendimento AND clie.id = :idCliente AND "
							+ " raso.id <> :idRegistroAtendimentoSolicitante";

			retorno = ((Number) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento)
							.setInteger("idCliente", idCliente)
							.setInteger("idRegistroAtendimentoSolicitante", idRegistroAtendimentoSolicitante).setMaxResults(1)
							.uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0408] Inserir Registro de Atendimento
	 * Caso esteja adicionando um novo solicitante e a unidade já seja um
	 * solicitante do registro de atendimento (existe Ocorrência na tabela
	 * REGISTRO_ATENDIMENTO_SOLICITANTE com RGAT_ID=RGAT_ID do registro de
	 * atendimento selecionado e UNID_ID=Id da Unidade informada e RASO_ID<>id
	 * do Registro atendimento solicitante).
	 * [FS0018] Verificar existência da unidade solicitante
	 * 
	 * @author Sávio Luiz
	 * @date 07/09/2006
	 * @param idRegistroAtendimento
	 *            ,
	 *            idUnidade
	 * @throws ControladorException
	 */
	public Integer verificarExistenciaUnidadeSolicitanteAtualizar(Integer idRegistroAtendimento, Integer idUnidade, Integer idRASolicitante)
					throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select COUNT(*)" + " from RegistroAtendimentoSolicitante raso" + " left join raso.registroAtendimento rgat"
							+ " left join raso.unidadeOrganizacional unid"
							+ " where rgat.id  = :idRegistroAtendimento AND unid.id = :idUnidade AND" + " raso.id <> :idRASolicitante";

			retorno = ((Number) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento)
							.setInteger("idUnidade", idUnidade).setInteger("idRASolicitante", idRASolicitante).setMaxResults(1)
							.uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 13/09/2006
	 * @param idRa
	 * @return dataPrevisaoAtual
	 * @throws ErroRepositorioException
	 */
	public Date obterDataAgenciaReguladoraPrevisaoAtual(Integer idRa) throws ErroRepositorioException{

		Date retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " SELECT raDados.dataPrevisaoAtual FROM RaDadosAgenciaReguladora raDados "
							+ " WHERE raDados.registroAtendimento.id = :idRa";

			retorno = (Date) session.createQuery(consulta).setInteger("idRa", idRa).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Consultar os registros de atendimento do Imovel [UC0472] Consultar Imovel
	 * 
	 * @author Rafael Santos
	 * @date 25/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection consultarRegistroAtendimentoImovel(Integer idImovel, String situacao) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{
			consulta = "SELECT registroAtendimento.id, "
							+ // 0
							" solicitacaoTipo.descricao, " // 1

							+ " solicitacaoTipoEspecificacao.descricao, " // 2

							+ " registroAtendimento.registroAtendimento " // 3

							+ "from RegistroAtendimento registroAtendimento "

							+ "left join registroAtendimento.solicitacaoTipoEspecificacao solicitacaoTipoEspecificacao "
							+ "left join solicitacaoTipoEspecificacao.solicitacaoTipo solicitacaoTipo "

							+ "where registroAtendimento.imovel.id = :idImovel ";

			if(situacao != null){
				consulta = consulta + " and registroAtendimento.codigoSituacao = :codigoSituacao ";
			}

			Query query = session.createQuery(consulta);

			query.setInteger("idImovel", idImovel.intValue());

			// colocar a situacao se houver
			if(situacao != null){
				query.setInteger("codigoSituacao", Integer.valueOf(situacao));
			}

			retorno = query.list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * REGISTRO_ATENDIMENTO com RGAT_CDSITUACAO=1 e BRAR_ID=Id da Área do Bairro
	 * selecionada e STEP_ID=STEP_ID da tabela SOLICITACAO_TIPO_ESPECIFICACAO
	 * com STEP_ICMATRICULA com o valor correspondente a um e SOTP_ID=SOTP_ID da
	 * tabela SOLICITACAO_TIPO para SOTP_ICFALTAAGUA com o valor correspondente
	 * a um.
	 * [SB0025] Verifica Registro de Atendimento de Água Generalizada
	 * 
	 * @author Raphael Rossiter
	 * @date 16/08/2006
	 * @param idBairroArea
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarRAFaltaAguaGeneralizada(Integer idBairroArea) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT logradouro.nome,"
							+ // 0
							" logradouroTipo.descricaoAbreviada,"
							+ // 1
							" logradouroTitulo.descricaoAbreviada,"
							+ // 2
							" bairro.id,"
							+ // 3
							" bairro.nome,"
							+ // 4
							" municipio.id,"
							+ // 5
							" municipio.nome,"
							+ // 6
							" unidadeFederacao.id,"
							+ // 7
							" unidadeFederacao.sigla,"
							+ // 8
							" step.descricao, "
							+ // 9
							" cep.cepId,"
							+ // 10
							" cep.logradouro,"
							+ // 11
							" cep.descricaoTipoLogradouro,"
							+ // 12
							" cep.bairro,"
							+ // 13
							" cep.municipio,"
							+ // 14
							" cep.sigla, "
							+ // 15
							" cep.codigo, "
							+ // 16
							" bairro.codigo, "
							+ // 17
							" rgat.complementoEndereco,"
							+ // 18
							" logradouro.id,"
							+ // 19
							" logradouroCep.id,"
							+ // 20
							" logradouroBairro.id,"
							+ // 21
							" logradouroTipo.descricao,"
							+ // 22
							" logradouroTitulo.descricao, "
							+ // 23
							" step.id, "
							+ // 24
							" sotp.id, "
							+ // 25
							" sotp.descricao, "
							+ // 26
							" rgat.id, "
							+ // 27
							" rgat.pontoReferencia, "
							+ // 28
							" rgat.registroAtendimento, "
							+ // 29
							" brar.id, "
							+ // 30
							" brar.nome, "
							+ // 31
							" bair.codigo, "
							+ // 32
							" bair.nome, "
							+ // 33
							" imovel.id " // 34
							+ "FROM RegistroAtendimento rgat " + "INNER JOIN rgat.bairroArea brar " + "INNER JOIN brar.bairro bair "
							+ "INNER JOIN rgat.solicitacaoTipoEspecificacao step " + "INNER JOIN step.solicitacaoTipo sotp "
							+ "left join rgat.imovel imovel " + "left join rgat.logradouroCep logradouroCep "
							+ "left join logradouroCep.cep cep " + "left join logradouroCep.logradouro logradouro "
							+ "left join logradouro.logradouroTipo logradouroTipo "
							+ "left join logradouro.logradouroTitulo logradouroTitulo "
							+ "left join rgat.logradouroBairro logradouroBairro " + "left join logradouroBairro.bairro bairro "
							+ "left join bairro.municipio municipio " + "left join municipio.unidadeFederacao unidadeFederacao "
							+ "WHERE brar.id = :idBairroArea AND rgat.codigoSituacao = 1 "
							+ "AND step.indicadorMatricula = 2 AND sotp.indicadorFaltaAgua = 1";

			retorno = (Collection) session.createQuery(consulta).setInteger("idBairroArea", idBairroArea).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0015] Verifica Registro de Atendimento de Água Generalizada
	 * 
	 * @author Raphael Rossiter
	 * @date 16/08/2006
	 * @param idImovel
	 *            ,
	 *            idEspecificacao, dataReativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarRAEncerradoImovel(Integer idImovel, Integer idEspecificacao, Date dataReativacao)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT logradouro.nome,"
							+ // 0
							" logradouroTipo.descricaoAbreviada,"
							+ // 1
							" logradouroTitulo.descricaoAbreviada,"
							+ // 2
							" bairro.id,"
							+ // 3
							" bairro.nome,"
							+ // 4
							" municipio.id,"
							+ // 5
							" municipio.nome,"
							+ // 6
							" unidadeFederacao.id,"
							+ // 7
							" unidadeFederacao.sigla,"
							+ // 8
							" step.descricao, "
							+ // 9
							" cep.cepId,"
							+ // 10
							" cep.logradouro,"
							+ // 11
							" cep.descricaoTipoLogradouro,"
							+ // 12
							" cep.bairro,"
							+ // 13
							" cep.municipio,"
							+ // 14
							" cep.sigla, "
							+ // 15
							" cep.codigo, "
							+ // 16
							" bairro.codigo, "
							+ // 17
							" rgat.complementoEndereco,"
							+ // 18
							" logradouro.id,"
							+ // 19
							" logradouroCep.id,"
							+ // 20
							" logradouroBairro.id,"
							+ // 21
							" logradouroTipo.descricao,"
							+ // 22
							" logradouroTitulo.descricao, "
							+ // 23
							" step.id, "
							+ // 24
							" sotp.id, "
							+ // 25
							" sotp.descricao, "
							+ // 26
							" rgat.id, "
							+ // 27
							" rgat.pontoReferencia, "
							+ // 28
							" rgat.registroAtendimento, "
							+ // 29
							" imovel.id, "
							+ // 30
							" loca.id, "
							+ // 31
							" stcm.codigo, "
							+ // 32
							" qdra.numeroQuadra, "
							+ // 33
							" imovel.lote, "
							+ // 34
							" imovel.subLote, "
							+ // 35
							" rgat.dataEncerramento, "
							+ // 36
							" amen.id, "
							+ // 37
							" amen.descricao, "
							+ // 38
							" imovel.numeroImovel, "
							+ // 39
							" imovel.complementoEndereco " // 40
							+ "FROM RegistroAtendimento rgat " + "INNER JOIN rgat.imovel imovel "
							+ "INNER JOIN rgat.solicitacaoTipoEspecificacao step " + "INNER JOIN step.solicitacaoTipo sotp "
							+ "left join imovel.logradouroCep logradouroCep " + "left join logradouroCep.cep cep "
							+ "left join logradouroCep.logradouro logradouro " + "left join logradouro.logradouroTipo logradouroTipo "
							+ "left join logradouro.logradouroTitulo logradouroTitulo "
							+ "left join imovel.logradouroBairro logradouroBairro " + "left join logradouroBairro.bairro bairro "
							+ "left join bairro.municipio municipio " + "left join municipio.unidadeFederacao unidadeFederacao "
							+ "left join imovel.localidade loca " + "left join imovel.setorComercial stcm "
							+ "left join imovel.quadra qdra " + "left join rgat.atendimentoMotivoEncerramento amen "
							+ "WHERE imovel.id = :idImovel AND rgat.codigoSituacao = 2 "
							+ "AND step.id = :idEspecificacao AND rgat.dataEncerramento >= :dataReativacao ";

			retorno = (Collection) session.createQuery(consulta).setInteger("idImovel", idImovel)
							.setInteger("idEspecificacao", idEspecificacao).setTimestamp("dataReativacao", dataReativacao).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [SB0015] Verifica Registro de Atendimento de Água Generalizada
	 * 
	 * @author Raphael Rossiter
	 * @date 16/08/2006
	 * @param idLogradouroBairro
	 *            ,
	 *            idLogradouroCep, idEspecificacao, dataReativacao
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarRAEncerradoLocalOcorrencia(Integer idLogradouroBairro, Integer idLogradouroCep, Integer idEspecificacao,
					Date dataReativacao) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT logradouro.nome,"
							+ // 0
							" logradouroTipo.descricaoAbreviada,"
							+ // 1
							" logradouroTitulo.descricaoAbreviada,"
							+ // 2
							" bairro.id,"
							+ // 3
							" bairro.nome,"
							+ // 4
							" municipio.id,"
							+ // 5
							" municipio.nome,"
							+ // 6
							" unidadeFederacao.id,"
							+ // 7
							" unidadeFederacao.sigla,"
							+ // 8
							" step.descricao, "
							+ // 9
							" cep.cepId,"
							+ // 10
							" cep.logradouro,"
							+ // 11
							" cep.descricaoTipoLogradouro,"
							+ // 12
							" cep.bairro,"
							+ // 13
							" cep.municipio,"
							+ // 14
							" cep.sigla, "
							+ // 15
							" cep.codigo, "
							+ // 16
							" bairro.codigo, "
							+ // 17
							" rgat.complementoEndereco,"
							+ // 18
							" logradouro.id,"
							+ // 19
							" logradouroCep.id,"
							+ // 20
							" logradouroBairro.id,"
							+ // 21
							" logradouroTipo.descricao,"
							+ // 22
							" logradouroTitulo.descricao, "
							+ // 23
							" step.id, "
							+ // 24
							" sotp.id, "
							+ // 25
							" sotp.descricao, "
							+ // 26
							" rgat.id, "
							+ // 27
							" rgat.pontoReferencia, "
							+ // 28
							" rgat.registroAtendimento, "
							+ // 29
							" rgat.dataEncerramento, "
							+ // 30
							" amen.id, "
							+ // 31
							" amen.descricao " // 32
							+ "FROM RegistroAtendimento rgat " + "INNER JOIN rgat.solicitacaoTipoEspecificacao step "
							+ "INNER JOIN step.solicitacaoTipo sotp " + "left join rgat.logradouroCep logradouroCep "
							+ "left join logradouroCep.cep cep " + "left join logradouroCep.logradouro logradouro "
							+ "left join logradouro.logradouroTipo logradouroTipo "
							+ "left join logradouro.logradouroTitulo logradouroTitulo "
							+ "left join rgat.logradouroBairro logradouroBairro " + "left join logradouroBairro.bairro bairro "
							+ "left join bairro.municipio municipio " + "left join municipio.unidadeFederacao unidadeFederacao "
							+ "left join rgat.atendimentoMotivoEncerramento amen "
							+ "WHERE rgat.imovel.id IS NULL AND rgat.codigoSituacao = 2 "
							+ "AND logradouroCep.id = :idLogradouroCep AND logradouroBairro.id = :idLogradouroBairro "
							+ "AND step.id = :idEspecificacao AND rgat.dataEncerramento >= :dataReativacao "
							+ "AND sotp.indicadorFaltaAgua = 2 ";

			retorno = (Collection) session.createQuery(consulta).setInteger("idLogradouroCep", idLogradouroCep)
							.setInteger("idLogradouroBairro", idLogradouroBairro).setInteger("idEspecificacao", idEspecificacao)
							.setTimestamp("dataReativacao", dataReativacao).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisa os dados do Registro Atendimento Solicitante para o Relatório de
	 * OS
	 * 
	 * @author Rafael Corrêa
	 * @date 17/10/2006
	 * @param idRegistroAtendimento
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosRegistroAtendimentoSolicitanteRelatorioOS(Integer idRegistroAtendimento) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT cliente.id, cliente.nome, " // 0,1
							+ "unid.id, unid.descricao, " // 2,3
							+ "func.id, func.nome, " // 4,5
							+ "raso.solicitante " // 6
							+ "FROM RegistroAtendimentoSolicitante raso "
							+ "INNER JOIN raso.registroAtendimento ra "
							+ "LEFT JOIN raso.cliente cliente "
							+ "LEFT JOIN raso.unidadeOrganizacional unid "
							+ "LEFT JOIN raso.funcionario func "
							+ "WHERE ra.id =  :idRegistroAtendimento AND "
							+ "raso.indicadorSolicitantePrincipal = " + RegistroAtendimentoSolicitante.INDICADOR_PRINCIPAL;

			retorno = (Object[]) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0458] - Imprimir Ordem de Serviço
	 * Pesquisa o telefone principal do Registro Atendimento Solicitante para o
	 * Relatório de OS
	 * 
	 * @author Rafael Corrêa
	 * @date 17/10/2006
	 * @param idRegistroAtendimento
	 * @throws ErroRepositorioException
	 */

	public Object[] pesquisarSolicitanteFonePrincipal(Integer idRegistroAtendimento) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT sf.ddd, sf.fone, sf.ramal " + "FROM SolicitanteFone sf "
							+ "INNER JOIN sf.registroAtendimentoSolicitante raso " + "INNER JOIN raso.registroAtendimento ra "
							+ "WHERE ra.id =  :idRegistroAtendimento AND " + "raso.indicadorSolicitantePrincipal = "
							+ RegistroAtendimentoSolicitante.INDICADOR_PRINCIPAL + " AND " + "sf.indicadorPadrao = "
							+ SolicitanteFone.INDICADOR_FONE_PADRAO;

			retorno = (Object[]) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0482] - Obter Endereço Abreviado da Ocorrência do RA
	 * Pesquisa os dados necessários do RA para verificar como o Endereço será
	 * obtido
	 * 
	 * @author Rafael Corrêa
	 * @date 18/10/2006
	 * @param idRegistroAtendimento
	 * @throws ErroRepositorioException
	 */

	public Object[] obterLogradouroBairroImovelRegistroAtendimento(Integer idRA) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT ra.logradouroBairro.id, ra.imovel.id FROM RegistroAtendimento ra "
			// + "LEFT JOIN ra.logradouroBairro logrbairro "
			// + "LEFT JOIN ra.imovel imov "
							+ "WHERE ra.id =  :idRA";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idRA", idRA).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0482] - Obter Endereço Abreviado da Ocorrência do RA
	 * Pesquisa o Endereco Descritivo do RA
	 * 
	 * @author Rafael Corrêa
	 * @date 18/10/2006
	 * @param idRegistroAtendimento
	 * @throws ErroRepositorioException
	 */

	public Object[] obterEnderecoDescritivoRA(Integer idRA) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT raenddesc.descricao, raenddesc.descricaoBairro FROM RaEnderecoDescritivo raenddesc "
			// + "INNER JOIN raenddesc.registroAtendimento ra "
							+ "WHERE raenddesc.registroAtendimento.id =  :idRA";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idRA", idRA).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0014] Manter Imóvel
	 * [FS0006] - Verificar existência de RA verifica se existe para o Imóvel RA
	 * encerrada por execução com especificação da solicitação que permita a
	 * Manutenção do Imóvel
	 * 
	 * @author Vivianne Sousa
	 * @date 20/10/2006
	 * @param idImovel
	 *            ,
	 *            idSolicitacaoTipoEspecificacao
	 * @return RegistroAtendimento
	 * @throws ErroRepositorioException
	 */
	public RegistroAtendimento verificarExistenciaRAManutencaoImovel(Integer idImovel, Integer idSolicitacaoTipoEspecificacao)
					throws ErroRepositorioException{

		RegistroAtendimento retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT rgat " + "FROM RegistroAtendimento rgat " + "INNER JOIN FETCH rgat.imovel imov  "
							+ "INNER JOIN FETCH rgat.solicitacaoTipoEspecificacao step  "
							+ "INNER JOIN rgat.atendimentoMotivoEncerramento amen  "
							+ "WHERE imov.id = :idImovel AND step.id = :idSolicitacaoTipoespecificacao "
							+ "AND rgat.codigoSituacao = 1 AND amen.id = 1 ";

			retorno = (RegistroAtendimento) session.createQuery(consulta).setInteger("idImovel", idImovel)
							.setInteger("idSolicitacaoTipoespecificacao", idSolicitacaoTipoEspecificacao).setMaxResults(1).uniqueResult();

			/*
			 * if (retorno != null) { Hibernate.initialize(retorno.getImovel());
			 * Hibernate.initialize(retorno.getSolicitacaoTipoEspecificacao()); }
			 */

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [FS0042] Verifica Número informado Imóvel
	 * 
	 * @author Raphael Rossiter
	 * @date 07/11/2006
	 * @param ultimoRAManual
	 * @return quantidadeRA
	 * @throws ErroRepositorioException
	 */
	public Integer verificaNumeracaoRAManualInformada(Integer ultimoRAManual) throws ErroRepositorioException{

		Integer retorno = 0;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT rgat.id " + "FROM RegistroAtendimento rgat " + "WHERE rgat.manual = :ultimoRAManual ";

			retorno = (Integer) session.createQuery(consulta).setInteger("ultimoRAManual", ultimoRAManual).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Cria a query de acordo com os parâmetros de pesquisa informados pelo
	 * usuário
	 * [UC0492] - Gerar Relatório Acompanhamento de execução de Ordem de Serviço
	 * 
	 * @author Rafael Corrêa
	 * @date 01/11/06
	 * @param idImovel
	 * @param idCliente
	 * @param idTipoRelacao
	 * @param localidadeInicial
	 * @param localidadeFinal
	 * @param idAvisoBancario
	 * @param idArrecadador
	 * @param periodoArrecadacaoInicial
	 * @param periodoArrecadacaoFinal
	 * @param periodoPagamentoInicio
	 * @param periodoPagamentoFim
	 * @param dataPagamentoInicial
	 * @param dataPagamentoFinal
	 * @param idsPagamentosSituacoes
	 * @param idsDebitosTipos
	 * @param idsArrecadacaoForma
	 * @param idsDocumentosTipos
	 * @return Collection
	 */
	public Collection pesquisarRAGerarRelatorioGestaoSolicitacaoRAPorUnidade(Date periodoAtendimentoInicial, Date periodoAtendimentoFinal,
					String situacaoRA, String[] idsSolicitacoesTipos, String[] idsEspecificacoes, String idUnidade,
					String idUnidadeSuperior, String idMunicipio, String codigoBairro, String tipoOrdenacao)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT DISTINCT solTpEsp.step_id as idSolTpEsp, " // 0
							+ " solTpEsp.step_dssolicitacaotipoespecifi as descricaoSolTpEsp, " // 1
							+ " solTpEsp.step_nnhorasprazo as horasPrazo, " // 2
							+ " tramite.unid_iddestino as idUnidade, " // 3
							+ " ra.rgat_id as idRA, " // 4
							+ " ra.rgat_tmencerramento as dataEncerramento, " // 5
							+ " ra.amen_id as idAtendMotEnc, " // 6
							+ " ra.rgat_dtprevistaatual as dataPrevista, " // 7
							+ " unidadeAtendimento.unid_iccentralatendimento as indCentralAtendimento " // 8
							+ " FROM " + " registro_atendimento ra " + " INNER JOIN "
							+ " solicitacao_tipo_especificacao solTpEsp "
							+ " on ra.step_id = solTpEsp.step_id " + " INNER JOIN "
							+ " solicitacao_tipo solTp "
							+ " on solTpEsp.sotp_id = solTp.sotp_id " + " LEFT OUTER JOIN "
							+ " imovel imovel "
							+ " on ra.imov_id = imovel.imov_id " + " LEFT OUTER JOIN "
							+ " logradouro_bairro logrBairroImovel "
							+ " on imovel.lgbr_id = logrBairroImovel.lgbr_id " + " LEFT OUTER JOIN "
							+ " bairro bairroImovel "
							+ " on logrBairroImovel.bair_id = bairroImovel.bair_id " + " LEFT OUTER JOIN "
							+ " municipio municipioImovel "
							+ " on bairroImovel.muni_id = municipioImovel.muni_id "
							+ " LEFT OUTER JOIN "
							+ " logradouro_bairro logrBairroRA " + " on ra.lgbr_id = logrBairroRA.lgbr_id "
							+ " LEFT OUTER JOIN "
							+ " bairro bairroRA " + " on logrBairroRA.bair_id = bairroRA.bair_id "
							+ " LEFT OUTER JOIN "
							+ " municipio municipioRA " + " on bairroRA.muni_id = municipioRA.muni_id "
							+ " LEFT OUTER JOIN "
							+ " bairro_area areaBairroRA " + " on ra.brar_id = areaBairroRA.brar_id "
							+ " LEFT OUTER JOIN "
							+ " bairro bairroAreaBairro " + " on areaBairroRA.bair_id = bairroAreaBairro.bair_id "
							+ " LEFT OUTER JOIN "
							+ " municipio municipioAreaBairro "
							+ " on bairroAreaBairro.muni_id = municipioAreaBairro.muni_id "
							+ " LEFT OUTER JOIN " + " tramite tramite " + " on tramite.rgat_id = ra.rgat_id "
							+ " LEFT OUTER JOIN "
							+ " registro_atendimento_unidade raUnidade "
							+ " on raUnidade.rgat_id = ra.rgat_id and "
							+ " raUnidade.attp_id = " + AtendimentoRelacaoTipo.ABRIR_REGISTRAR
							+ " LEFT OUTER JOIN "
							+ " unidade_organizacional unidadeAtendimento " + " on raUnidade.unid_id = unidadeAtendimento.unid_id ";

			consulta = consulta
							+ criarCondicionaisRAGerarRelatorioGestaoRA(periodoAtendimentoInicial, periodoAtendimentoFinal, situacaoRA,
											idsSolicitacoesTipos, idsEspecificacoes, idUnidade, idUnidadeSuperior, idMunicipio,
											codigoBairro);

			if(tipoOrdenacao != null && tipoOrdenacao.equals("1")){
				consulta = consulta + " order by servTp.svtp_id, ra.rgat_id ";
			}

			retorno = session.createSQLQuery(consulta).addScalar("idSolTpEsp", Hibernate.INTEGER)
							.addScalar("descricaoSolTpEsp", Hibernate.STRING).addScalar("horasPrazo", Hibernate.INTEGER)
							.addScalar("idUnidade", Hibernate.INTEGER).addScalar("idRA", Hibernate.INTEGER)
							.addScalar("dataEncerramento", Hibernate.TIMESTAMP).addScalar("idAtendMotEnc", Hibernate.INTEGER)
							.addScalar("dataPrevista", Hibernate.DATE).addScalar("indCentralAtendimento", Hibernate.SHORT).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Cria as condicionais de acordo com os parâmetros de pesquisa informados
	 * pelo usuário
	 * [UC0499] - Gerar Relatório de Gestão de Solicitações de RA por
	 * Unidade/Chefia
	 * 
	 * @author Rafael Corrêa
	 * @date 01/11/06
	 * @param idImovel
	 * @param idCliente
	 * @param idTipoRelacao
	 * @param localidadeInicial
	 * @param localidadeFinal
	 * @param idAvisoBancario
	 * @param idArrecadador
	 * @param periodoArrecadacaoInicial
	 * @param periodoArrecadacaoFinal
	 * @param periodoPagamentoInicio
	 * @param periodoPagamentoFim
	 * @param dataPagamentoInicial
	 * @param dataPagamentoFinal
	 * @param idsPagamentosSituacoes
	 * @param idsDebitosTipos
	 * @param idsArrecadacaoForma
	 * @param idsDocumentosTipos
	 * @return String
	 */
	public String criarCondicionaisRAGerarRelatorioGestaoRA(Date periodoAtendimentoInicial, Date periodoAtendimentoFinal,
					String situacaoRA, String[] idsSolicitacoesTipos, String[] idsEspecificacoes, String idUnidade,
					String idUnidadeSuperior, String idMunicipio, String codigoBairro){

		String sql = " WHERE ";

		if(periodoAtendimentoInicial != null && !periodoAtendimentoFinal.equals("")){
			String data1 = Util.recuperaDataInvertida(periodoAtendimentoInicial);

			if(data1 != null && !data1.equals("") && data1.trim().length() == 8){

				data1 = data1.substring(0, 4) + "-" + data1.substring(4, 6) + "-" + data1.substring(6, 8);
			}
			sql = sql + " ra.rgat_tmregistroatendimento >= '" + data1 + "' and ";
		}

		if(periodoAtendimentoFinal != null && !periodoAtendimentoFinal.equals("")){
			String data2 = Util.recuperaDataInvertida(periodoAtendimentoFinal);

			if(data2 != null && !data2.equals("") && data2.trim().length() == 8){

				data2 = data2.substring(0, 4) + "-" + data2.substring(4, 6) + "-" + data2.substring(6, 8);
			}
			sql = sql + " ra.rgat_tmregistroatendimento <= '" + data2 + "' and ";
		}

		if(situacaoRA != null && !situacaoRA.equals("")){
			sql = sql + " ra.rgat_cdsituacao = " + situacaoRA + " and ";
		}

		if(idsSolicitacoesTipos != null && !idsSolicitacoesTipos.equals("")
						&& !idsSolicitacoesTipos[0].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			String valoresIn = "";
			for(int i = 0; i < idsSolicitacoesTipos.length; i++){
				if(!idsSolicitacoesTipos[i].equals("")){
					valoresIn = valoresIn + idsSolicitacoesTipos[i] + ",";
				}
			}
			if(!valoresIn.equals("")){
				sql = sql + " solTp.sotp_id in (" + valoresIn;
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") and ";
			}
		}

		if(idsEspecificacoes != null && !idsEspecificacoes.equals("")
						&& !idsEspecificacoes[0].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			String valoresIn = "";
			for(int i = 0; i < idsEspecificacoes.length; i++){
				if(!idsEspecificacoes[i].equals("")){
					valoresIn = valoresIn + idsEspecificacoes[i] + ",";
				}
			}
			if(!valoresIn.equals("")){
				sql = sql + " solTpEsp.step_id in (" + valoresIn;
				sql = Util.formatarHQL(sql, 1);
				sql = sql + ") and ";
			}
		}

		if(idUnidade != null && !idUnidade.equals("")){
			sql = sql + " ra.rgat_id in (SELECT tr.rgat_id from tramite tr "
							+ " INNER JOIN registro_atendimento ra on ra.rgat_id = tr.rgat_id " + " WHERE tr.unid_iddestino = " + idUnidade
							+ " and tr.tram_tmtramite in (SELECT max(tr.tram_tmtramite) FROM tramite tr "
							+ " WHERE ra.rgat_id = tr.rgat_id)) " + " and ";
		}

		if(idUnidadeSuperior != null && !idUnidadeSuperior.equals("")){
			sql = sql + " ra.rgat_id in (SELECT tr.rgat_id from tramite tr "
							+ " INNER JOIN registro_atendimento ra on ra.rgat_id = tr.rgat_id " + " WHERE tr.unid_iddestino = "
							+ idUnidadeSuperior + " or tr.unid_iddestino in (SELECT unidade.unid_id "
							+ " FROM unidade_organizacional unidade WHERE unidade.unid_idsuperior = " + idUnidadeSuperior + ") "
							+ " and tr.tram_tmtramite in (SELECT max(tr.tram_tmtramite) FROM tramite tr "
							+ " WHERE ra.rgat_id = tr.rgat_id)) " + " and ";
		}

		if(idMunicipio != null && !idMunicipio.equals("")){
			sql = sql + " ((municipioRA.muni_id = " + idMunicipio + ")"
							+ " or (logrBairroRA.lgbr_id is null and municipioImovel.muni_id = " + idMunicipio + ")"
							+ " or (municipioAreaBairro.muni_id = " + idMunicipio + "))" + " and ";
		}

		if(codigoBairro != null && !codigoBairro.equals("")){
			sql = sql + " ((bairroRA.bair_cdbairro = " + codigoBairro + ")"
							+ " or (logrBairroRA.lgbr_id is null and bairroImovel.bair_cdbairro = " + codigoBairro + ")"
							+ " or (bairroAreaBairro.bair_cdbairro = " + codigoBairro + "))" + " and ";
		}

		// retira o " and " q fica sobrando no final da query
		sql = Util.formatarHQL(sql, 4);

		return sql;
	}

	/**
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Pesquisa o registro de atendimento fazendo os carregamentos necessários
	 * 
	 * @author Rafael Corrêa
	 * @date 03/01/2007
	 * @param idRegistroAtendimento
	 * @return registroAtendimento
	 * @throws ErroRepositorioException
	 */
	public RegistroAtendimento pesquisarRegistroAtendimentoTarifaSocial(Integer idRegistroAtendimento) throws ErroRepositorioException{

		RegistroAtendimento registroAtendimento = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT registroAtendimento " + " FROM RegistroAtendimento registroAtendimento "
							+ " INNER JOIN FETCH registroAtendimento.solicitacaoTipoEspecificacao step "
							+ " INNER JOIN FETCH step.solicitacaoTipo solTp " + " LEFT JOIN FETCH registroAtendimento.imovel imov "
							+ " LEFT JOIN FETCH registroAtendimento.atendimentoMotivoEncerramento atendMotEnc "
							+ " WHERE registroAtendimento.id = :idRegistroAtendimento ";

			registroAtendimento = (RegistroAtendimento) session.createQuery(consulta)
							.setInteger("idRegistroAtendimento", idRegistroAtendimento).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return registroAtendimento;
	}

	/**
	 * [UC0408] Atualizar Registro de Atendimento
	 * 
	 * @author Sávio Luiz
	 * @date 12/01/2007
	 * @param idImovel
	 *            ,
	 *            idSolicitacaoTipoEspecificacao
	 * @return RegistroAtendimento
	 * @throws ErroRepositorioException
	 */
	public Integer verificarMesmaRA(Integer idImovel, Integer idRA) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT rgat.id " + "FROM RegistroAtendimento rgat " + "INNER JOIN rgat.imovel imov  "
							+ "WHERE imov.id = :idImovel AND rgat.id = :idRA";

			retorno = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("idRA", idRA).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC00503]Tramitar Conjunto de Registro de Atendimento
	 * 
	 * @author Ana Maria
	 * @date 16/01/2007
	 */
	public Short verificarIndicadorTarifaSocialRA(Integer idRA) throws ErroRepositorioException{

		Short retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select sotp.indicadorTarifaSocial" + " from RegistroAtendimento rgat"
							+ " inner join rgat.solicitacaoTipoEspecificacao step" + " inner join step.solicitacaoTipo sotp"
							+ " where rgat_id = :idRA";

			retorno = (Short) session.createQuery(consulta).setInteger("idRA", idRA).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC00503]Tramitar Conjunto de Registro de Atendimento
	 * 
	 * @author Ana Maria
	 * @date 16/01/2007
	 */
	public Short verificarIndicadorTarifaSocialUsuario(Integer idUsuario) throws ErroRepositorioException{

		Short retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select unid.indicadorTarifaSocial" + " from Usuario usua" + " inner join usua.unidadeOrganizacional unid"
							+ " where usua.id = :idUsuario";

			retorno = (Short) session.createQuery(consulta).setInteger("idUsuario", idUsuario).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Atualiza logradouroBairro de um ou mais imóveis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroBairro(LogradouroBairro logradouroBairroAntigo, LogradouroBairro logradouroBairroNovo)
					throws ErroRepositorioException{

		String consulta = "";

		Session session = HibernateUtil.getSession();

		try{

			consulta = "UPDATE gcom.atendimentopublico.registroatendimento.RegistroAtendimento SET "
							+ "lgbr_id = :idLogradouroBairroNovo, rgat_tmultimaalteracao = :ultimaAlteracao "
							+ "WHERE lgbr_id = :idLogradouroBairroAntigo ";

			session.createQuery(consulta).setInteger("idLogradouroBairroNovo", logradouroBairroNovo.getId())
							.setTimestamp("ultimaAlteracao", new Date())
							.setInteger("idLogradouroBairroAntigo", logradouroBairroAntigo.getId()).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Atualiza logradouroCep de um ou mais imóveis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroCep(LogradouroCep logradouroCepAntigo, LogradouroCep logradouroCepNovo) throws ErroRepositorioException{

		String consulta = "";

		Session session = HibernateUtil.getSession();

		try{

			consulta = "UPDATE gcom.atendimentopublico.registroatendimento.RegistroAtendimento SET "
							+ "lgcp_id = :idLogradouroCepNovo, rgat_tmultimaalteracao = :ultimaAlteracao "
							+ "WHERE lgcp_id = :idLogradouroCepAntigo ";

			session.createQuery(consulta).setInteger("idLogradouroCepNovo", logradouroCepNovo.getId())
							.setTimestamp("ultimaAlteracao", new Date()).setInteger("idLogradouroCepAntigo", logradouroCepAntigo.getId())
							.executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Atualiza logradouroBairro de um ou mais imóveis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroBairroSolicitante(LogradouroBairro logradouroBairroAntigo, LogradouroBairro logradouroBairroNovo)
					throws ErroRepositorioException{

		String consulta = "";

		Session session = HibernateUtil.getSession();

		try{

			consulta = "UPDATE gcom.atendimentopublico.registroatendimento.RegistroAtendimentoSolicitante SET "
							+ "lgbr_id = :idLogradouroBairroNovo, raso_tmultimaalteracao = :ultimaAlteracao "
							+ "WHERE lgbr_id = :idLogradouroBairroAntigo ";

			session.createQuery(consulta).setInteger("idLogradouroBairroNovo", logradouroBairroNovo.getId())
							.setTimestamp("ultimaAlteracao", new Date())
							.setInteger("idLogradouroBairroAntigo", logradouroBairroAntigo.getId()).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Atualiza logradouroCep de um ou mais imóveis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroCepSolicitante(LogradouroCep logradouroCepAntigo, LogradouroCep logradouroCepNovo)
					throws ErroRepositorioException{

		String consulta = "";

		Session session = HibernateUtil.getSession();

		try{

			consulta = "UPDATE gcom.atendimentopublico.registroatendimento.RegistroAtendimentoSolicitante SET "
							+ "lgcp_id = :idLogradouroCepNovo, raso_tmultimaalteracao = :ultimaAlteracao "
							+ "WHERE lgcp_id = :idLogradouroCepAntigo ";

			session.createQuery(consulta).setInteger("idLogradouroCepNovo", logradouroCepNovo.getId())
							.setTimestamp("ultimaAlteracao", new Date()).setInteger("idLogradouroCepAntigo", logradouroCepAntigo.getId())
							.executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * UC?? - ????????
	 * 
	 * @author Rômulo Aurélio Filho
	 * @date 20/03/2007
	 * @descricao O método retorna um objeto com a maior data de Tramite
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Tramite pesquisarUltimaDataTramite(Integer idRA) throws ErroRepositorioException{

		Tramite tramite = null;

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = "SELECT tram " + " FROM Tramite tram " + " WHERE tram.registroAtendimento.id = :idRA "
							+ " ORDER BY tram.dataTramite desc";

			tramite = (Tramite) session.createQuery(consulta).setInteger("idRA", idRA).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return tramite;
	}

	/**
	 * Consultar Observacao Registro Atendimento Solicitacao da CAER
	 * 
	 * @author Rafael Pinto
	 * @date 14/03/2007
	 */
	public Collection pesquisarObservacaoRegistroAtendimento(Integer matriculaImovel, Date dataInicialAtendimento, Date dataFinalAtendimento)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();

		String consulta = "";
		Map parameters = new HashMap();
		Query query = null;

		try{
			consulta = "SELECT ra.id,ra.registroAtendimento,ra.observacao " + "FROM RegistroAtendimento ra "
							+ "WHERE ra.imovel.id = :idImovel " + "AND ra.observacao IS NOT NULL ";

			parameters.put("idImovel", matriculaImovel);

			if(dataInicialAtendimento != null && dataFinalAtendimento != null){
				consulta += "AND ra.registroAtendimento BETWEEN (:dataInicial) AND (:dataFinal) ";

				parameters.put("dataInicial", Util.formatarDataInicial(dataInicialAtendimento));
				parameters.put("dataFinal", Util.formatarDataFinal(dataFinalAtendimento));
			}

			consulta = consulta + "ORDER BY ra.registroAtendimento DESC";

			query = session.createQuery(consulta);

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Set){
					Set setList = (HashSet) parameters.get(key);
					query.setParameterList(key, setList);
				}else if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{
					query.setParameter(key, parameters.get(key));
				}

			}

			retorno = query.list();

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
	 * @author Thiago Tenório
	 * @date 25/04/2007
	 * @param idLocalidade
	 *            id da localida a ser pesquisada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection getImoveisResumoRegistroAtendimento(int idLocalidade, Integer anoMesReferencia, Integer dtAtual)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		// dtAtual = 20070228;

		try{
			String hql = "select distinct"
							+ "	imo.id, "
							+ "	case when ( not imo is null ) then "
							+ "		locPorImo.gerenciaRegional.id "
							+ "	else "
							+ "		locPorRa.gerenciaRegional.id "
							+ "	end as greg_id, "
							+

							"	case when ( not imo is null ) then "
							+ "		locPorImo.unidadeNegocio.id "
							+ "	else "
							+ "		locPorRa.unidadeNegocio.id "
							+ "	end as uneg_id, "
							+

							"	case when ( not imo is null ) then "
							+ "		locPorImo.id "
							+ " 	else "
							+ "		locPorRa.id "
							+ "	end as loca_id, "
							+

							"	case when ( not imo is null ) then "
							+ "		locPorImo.localidade.id "
							+ "	else "
							+ "		locPorRa.localidade.id "
							+ "	end as loca_cdelo, "
							+

							"	case when ( not imo is null ) then "
							+ "		scom.id "
							+ "	else "
							+ "		null "
							+ "	end as stcm_id, "
							+

							"	case when ( not imo is null ) then "
							+ "		quad.rota.id "
							+ "	else "
							+ "		null "
							+ "	end as rota_id, "
							+

							"	case when ( not imo is null ) then "
							+ "		quad.id "
							+ "	else "
							+ "		null "
							+ "	end as qdra_id, "
							+

							"	case when ( not imo is null ) then "
							+ "		scom.codigo "
							+ "	else "
							+ "		null "
							+ "	end as rera_cdsetorcomercial, "
							+

							"	case when ( not imo is null ) then "
							+ "		quad.numeroQuadra "
							+ "	else "
							+ "		null "
							+ "	end as rera_nnquadra, "
							+

							"	case when ( not imo is null ) then "
							+ "		imo.imovelPerfil.id "
							+ "	else "
							+ "		null "
							+ "	end as iper_id, "
							+

							"	case when ( not imo is null ) then "
							+ "		imo.ligacaoAguaSituacao.id "
							+ "	else "
							+ "		null "
							+ "	end as last_id, "
							+

							"	case when ( not imo is null ) then "
							+ "		imo.ligacaoEsgotoSituacao.id "
							+ "	else "
							+ "		null "
							+ "	end as lest_id, "
							+

							"	case when ( not imo is null ) then "
							+ "		plAgua.ligacaoAguaPerfil.id "
							+ "	else "
							+ "		null "
							+ "	end as lapf_id, "
							+

							"	case when ( not imo is null ) then "
							+ "		plEsgoto.ligacaoEsgotoPerfil.id "
							+ "	else "
							+ "		null "
							+ "	end as lepf_id, "
							+

							"	ra.indicadorAtendimentoOnline, "
							+ "	soltipo.id, "
							+ "	solTipEsp.id, "
							+ "	ra.meioSolicitacao.id, "
							+

							"	case when (cast((substring(ra.registroAtendimento, 1, 4) || "
							+ "			         substring(ra.registroAtendimento, 6, 2)) as integer) = :anoMesReferencia) then "
							+ "		1 "
							+ "	else "
							+ "		0 "
							+ "	end as qtGeradas, "
							+

							"	case when ( ra.codigoSituacao = 1 ) then "
							+ "		case when ( cast(substring(ra.dataPrevistaOriginal, 1, 4) || "
							+ "						 substring(ra.dataPrevistaOriginal, 6, 2) || "
							+
							// "		 				 substring(ra.dataPrevistaOriginal, 9, 2) as integer) >= :dtAtual ) then "
							// +
							"		 				 substring(ra.dataPrevistaOriginal, 9, 2) as integer) >= (select logradouro.id from gcom.cadastro.sistemaparametro.SistemaParametro) ) then "
							+

							"			1 "
							+ "		else "
							+ "			0 "
							+ "		end "
							+ "	end as qtRaPendenteNoPrazo, "
							+

							"	case when ( ra.codigoSituacao = 1 ) then "
							+ "		case when ( cast(substring(ra.dataPrevistaOriginal, 1, 4) || "
							+ "					     substring(ra.dataPrevistaOriginal, 6, 2) || "
							+
							// "						 substring(ra.dataPrevistaOriginal, 9, 2) as integer) < :dtAtual ) then "
							// +
							"						 substring(ra.dataPrevistaOriginal, 9, 2) as integer) < (select logradouro.id from gcom.cadastro.sistemaparametro.SistemaParametro) ) then "
							+

							"			1 " + "		else " + "			0 " + "		end " + "	end as qtRaPendenteForaPrazo, " +

							"	case when ( ra.codigoSituacao = 2 ) then "
							+ "		case when ( cast(ra.dataEncerramento as date) <= ra.dataPrevistaOriginal ) then " + "			1 " + "		else "
							+ "			0 " + "		end " + "	end as qtraEncerradaNoPrazo, " +

							"	case when ( ra.codigoSituacao = 2 ) then "
							+ "		case when ( cast(ra.dataEncerramento as date) > ra.dataPrevistaOriginal ) then " + "			1 " + "		else "
							+ "			0 " + "		end " + "	end as qtraEncerradaForaPrazo, " +

							"	case when ( ra.codigoSituacao = 3 ) then " + "		1 " + "	else " + "		0 " + "	end as qtBloqueada, " + "	ra.id "
							+ "FROM " + "	gcom.atendimentopublico.registroatendimento.RegistroAtendimento ra "
							+ "	inner join ra.solicitacaoTipoEspecificacao solTipEsp " + "	inner join solTipEsp.solicitacaoTipo soltipo "
							+ "	left join ra.localidade locPorRa " + "	left join ra.imovel imo " + "	left join imo.localidade locPorImo "
							+ "	left join imo.quadra quad " + "	left join imo.setorComercial scom " + "	left join imo.ligacaoAgua plAgua "
							+ "	left join imo.ligacaoEsgoto plEsgoto " + "	left join ra.registroAtendimentoUnidades rauni " + "WHERE "
							+ "	locPorRa.id = :idLocalidade and "
							+ "	substring(ra.registroAtendimento,1,4) || substring(ra.registroAtendimento,6,2) = :anoMesReferencia";

			retorno = session.createQuery(hql).setInteger("idLocalidade", idLocalidade)
							.setString("anoMesReferencia", anoMesReferencia.toString()).list();
			// .setInteger("dtAtual", dtAtual).list();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Método que insere em batch uma lista de ResumoRegistroAtendimento
	 * [UC0275] - Gerar Resumo Resgistro Atendimento
	 * 
	 * @author Thiago Tenório
	 * @date 30/04/2007
	 * @param listaResumoRegistroAtendimento
	 * @throws ErroRepositorioException
	 */
	public void inserirResumoRegistroAtendimentoBatch(List<UnResumoRegistroAtendimento> listaResumoRegistroAtendimento)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		if(listaResumoRegistroAtendimento != null && !listaResumoRegistroAtendimento.isEmpty()){
			Iterator it = listaResumoRegistroAtendimento.iterator();
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
	public List consultarResumoRegistroAtendimento(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ErroRepositorioException{

		// cria a coleção de retorno
		List retorno = null;
		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{

			GeradorSqlRelatorio geradorSqlRelatorio = new GeradorSqlRelatorio(GeradorSqlRelatorio.RESUMO_REGISTRO_ATENDIMENTO,
							informarDadosGeracaoRelatorioConsultaHelper);

			String sql = geradorSqlRelatorio.sqlNivelUm(geradorSqlRelatorio.getNomeCampoFixo(), geradorSqlRelatorio.getNomeTabelaFixo(),
							geradorSqlRelatorio.getNomeTabelaFixoTotal(),
							"'" + informarDadosGeracaoRelatorioConsultaHelper.getDescricaoOpcaoTotalizacao() + "'", "", "", "", false);

			// faz a pesquisa
			retorno = session.createSQLQuery(sql).addScalar("estado", Hibernate.STRING).addScalar("idGerencia", Hibernate.INTEGER)
							.addScalar("descricaoGerencia", Hibernate.STRING).addScalar("idElo", Hibernate.INTEGER)
							.addScalar("descricaoElo", Hibernate.STRING).addScalar("idLocalidade", Hibernate.INTEGER)
							.addScalar("descricaoLocalidade", Hibernate.STRING).addScalar("idSetorComercial", Hibernate.INTEGER)
							.addScalar("descricaoSetorComercial", Hibernate.STRING).addScalar("idQuadra", Hibernate.INTEGER)
							.addScalar("descricaoQuadra", Hibernate.STRING).addScalar("idLigacaoAguaSituacao", Hibernate.INTEGER)
							.addScalar("descricaoLigacaoAguaSituacao", Hibernate.STRING)
							.addScalar("idLigacaoEsgotoSituacao", Hibernate.INTEGER)
							.addScalar("descricaoLigacaoEsgotoSituacao", Hibernate.STRING).addScalar("idCategoria", Hibernate.INTEGER)
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
	 * [UC0427] Tramitar Registro Atendimento
	 * 
	 * @author Ana Maria
	 * @date 03/05/2007
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Integer pesquisarUnidadeCentralizadoraRa(Integer idRegistroAtendimento) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select unid.unid_idcentralizadora as unidadeCentralizadora"
							+ " from registro_atendimento rgat"
							+ " inner join localidade loca on rgat.loca_id=loca.loca_id"
							+ " inner join solicitacao_tipo_especificacao step on rgat.step_id=step.step_id"
							+ " inner join solicitacao_tipo sotp on step.sotp_id=sotp.sotp_id"
							+ " inner join solicitacao_tipo_grupo sotg on sotp.sotg_id=sotg.sotg_id"
							+ " inner join localidade_solic_tipo_grupo lstg on (loca.loca_id = lstg.loca_id) and (sotg.sotg_id = lstg.sotg_id)"
							+ " inner join unidade_organizacional unid on(unid.unid_id = lstg.unid_id)"
							+ " WHERE rgat.rgat_id =  :idRegistroAtendimento ";

			retorno = (Integer) session.createSQLQuery(consulta).addScalar("unidadeCentralizadora", Hibernate.INTEGER)
							.setInteger("idRegistroAtendimento", idRegistroAtendimento).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Procura a quantidade de dias de prazo
	 * [UC0459] Informar Dados da Agencia Reguladora
	 * 
	 * @author Kássia Albuquerque
	 * @date 19/04/2007
	 */

	public Integer procurarDiasPazo(Integer raId) throws ErroRepositorioException{

		Integer retorno = null;

		String consulta = "";

		Session session = HibernateUtil.getSession();

		try{

			consulta = "select step.horasPrazo " + "from RegistroAtendimento ra " + "inner join ra.solicitacaoTipoEspecificacao step "
							+ "where ra.id = :raId";

			retorno = (Integer) session.createQuery(consulta).setInteger("raId", raId).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Pesquisa a Unidade Solicitante de acordo com a RA
	 * 
	 * @author Ivan Sérgio
	 * @date 17/08/2007
	 */

	public Integer pesquisaUnidadeSolicitacaoRa(Integer idRa) throws ErroRepositorioException{

		Integer retorno = null;
		String hql = "";
		Session session = HibernateUtil.getSession();

		try{
			hql = "select " + "	raUni.unidadeOrganizacional.id " + "from "
							+ "	gcom.atendimentopublico.registroatendimento.RegistroAtendimento ra "
							+ "	inner join ra.registroAtendimentoUnidades raUni " + "where " + "	ra.id = :idRa and "
							+ "	raUni.atendimentoRelacaoTipo.id = 1";

			retorno = (Integer) session.createQuery(hql).setInteger("idRa", idRa).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Pesquisa a Unidade Encerramento de acordo com a RA
	 * 
	 * @author Ivan Sérgio
	 * @date 17/08/2007
	 */

	public Integer pesquisaUnidadeEncerradaRa(Integer idRa) throws ErroRepositorioException{

		Integer retorno = null;
		String hql = "";
		Session session = HibernateUtil.getSession();

		try{
			hql = "select " + "	case when (ra.codigoSituacao = 2) then " + "		raUni.unidadeOrganizacional.id " + "	else " + "		null "
							+ "	end " + "from " + "	gcom.atendimentopublico.registroatendimento.RegistroAtendimento ra "
							+ "	inner join ra.registroAtendimentoUnidades raUni " + "where " + "	ra.id = :idRa and "
							+ "	raUni.atendimentoRelacaoTipo.id = 3";

			retorno = (Integer) session.createQuery(hql).setInteger("idRa", idRa).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Obter Dados RegistroAtendimentoSolicitante.
	 * 
	 * @author Virgínia Melo
	 * @date 05/06/2009
	 * @param idRa
	 * @return RegistroAtendimentoSolicitante
	 * @throws ControladorException
	 */
	public RegistroAtendimentoSolicitante obterRegistroAtendimentoSolicitanteRelatorioOS(Integer idRa) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		RegistroAtendimentoSolicitante retorno = null;

		try{

			Criteria criteria = session.createCriteria(RegistroAtendimentoSolicitante.class)
							.add(Expression.eq("registroAtendimento.id", idRa)).setFetchMode("logradouroBairro", FetchMode.JOIN)
							.setFetchMode("logradouroBairro.logradouro", FetchMode.JOIN)
							.setFetchMode("logradouroBairro.logradouro.logradouroTipo", FetchMode.JOIN)
							.setFetchMode("logradouroBairro.logradouro.logradouroTitulo", FetchMode.JOIN)
							.setFetchMode("logradouroBairro.bairro", FetchMode.JOIN).setFetchMode("unidadeOrganizacional", FetchMode.JOIN)
							.setFetchMode("cliente", FetchMode.JOIN);

			List<RegistroAtendimentoSolicitante> lista = criteria.list();
			if(lista != null && !lista.isEmpty()){
				retorno = lista.get(0);
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * obtêm a ultima sequence
	 * 
	 * @return sequence
	 * @throws ErroRepositorioException
	 */
	public Integer obterSequenceRA() throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{
			Dialect dialect = Dialect.getDialect();

			consulta = dialect.getSequenceNextValString("sq_reg_atndto");

			Number retornoConsulta = (Number) session.createSQLQuery(consulta).uniqueResult();

			if(retornoConsulta != null){
				retorno = retornoConsulta.intValue();
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public Collection listarMotivoAtendimentoIncompleto() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection lista = null;
		try{
			Criteria criteria = session.createCriteria(AtendimentoIncompletoMotivo.class);
			lista = criteria.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return lista;
	}

	/**
	 * Lista RA's que foram reiteradas pelo id de uma RA.
	 * 
	 * @param registroAtendimento
	 * @return colecao de RegistroAtendimento
	 * @throws ControladorException
	 */
	public Collection<RegistroAtendimento> listarRAsReiteradas(RegistroAtendimento registroAtendimento) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection<RegistroAtendimento> listaRA = new ArrayList<RegistroAtendimento>();
		try{
			Criteria criteria = session.createCriteria(RegistroAtendimento.class);
			criteria.setFetchMode("solicitacaoTipoEspecificacao", FetchMode.JOIN);
			criteria.setFetchMode("solicitacaoTipoEspecificacao.solicitacaoTipo", FetchMode.JOIN);
			criteria.setFetchMode("meioSolicitacao", FetchMode.JOIN);
			criteria.add(Restrictions.eq("reiteracao", registroAtendimento.getId()));

			listaRA = (Collection<RegistroAtendimento>) criteria.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return listaRA;
	}

	/**
	 * Lista as duas ultimas RA's que foram reiteradas pelo id de uma RA.
	 * 
	 * @param registroAtendimento
	 * @return colecao de RegistroAtendimento
	 * @throws ControladorException
	 */
	public Collection<RegistroAtendimento> listarDuasUltimasRAsReiteradas(RegistroAtendimento registroAtendimento)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection<RegistroAtendimento> listaRA = new ArrayList<RegistroAtendimento>();
		try{
			Criteria criteria = session.createCriteria(RegistroAtendimento.class);
			criteria.setFetchMode("solicitacaoTipoEspecificacao", FetchMode.JOIN);
			criteria.setFetchMode("solicitacaoTipoEspecificacao.solicitacaoTipo", FetchMode.JOIN);
			criteria.setFetchMode("registroAtendimentoUnidades", FetchMode.JOIN);
			criteria.setFetchMode("registroAtendimentoUnidades.usuario", FetchMode.JOIN);
			criteria.setFetchMode("registroAtendimentoUnidades.usuario.funcionario", FetchMode.JOIN);
			criteria.add(Restrictions.eq("reiteracao", registroAtendimento.getId()));
			criteria.addOrder(Order.desc("ultimaAlteracao"));
			criteria.setMaxResults(2);
			listaRA = (Collection<RegistroAtendimento>) criteria.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return listaRA;
	}

	public Collection<AtendimentoIncompleto> filtrarRegistroAtendimentoIncompleto(FiltrarRegistroAtendimentoIncompletoHelper ra)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection<AtendimentoIncompleto> listaRA = new ArrayList<AtendimentoIncompleto>();
		try{
			Criteria criteria = session.createCriteria(AtendimentoIncompleto.class);
			if(ra.getId() != null && !ra.getId().equals("")){
				criteria.add(Expression.eq("id", ra.getId()));
			}
			if(ra.getDdd() != null && !ra.getDdd().equals("")){
				criteria.add(Expression.eq("ddd", ra.getDdd()));
			}
			if(ra.getFone() != null && !ra.getFone().equals("")){
				criteria.add(Expression.eq("numeroTelefone", ra.getFone()));
			}
			if(ra.getIdCliente() != null && !ra.getIdCliente().equals("")){
				Cliente cliente = new Cliente();
				cliente.setId(ra.getIdCliente());
				criteria.add(Expression.eq("cliente", cliente));
			}
			if(ra.getIdImovel() != null && !ra.getIdImovel().equals("")){
				Imovel imovel = new Imovel();
				imovel.setId(ra.getIdImovel());
				criteria.add(Expression.eq("imovel", imovel));
			}
			if(ra.getIdRegistroAtendimento() != null && !ra.getIdRegistroAtendimento().equals("")){
				RegistroAtendimento registroAtendimento = new RegistroAtendimento();
				registroAtendimento.setId(ra.getIdRegistroAtendimento());
				criteria.add(Expression.eq("registroAtendimento", registroAtendimento));
			}
			if(ra.getIndicadorRetornoChamada() != null && !ra.getIndicadorRetornoChamada().equals("")
							&& !ra.getIndicadorRetornoChamada().equals(ConstantesSistema.TODOS.intValue())){
				criteria.add(Restrictions.eq("indicadorRetornoChamada", Short.valueOf(String.valueOf(ra.getIndicadorRetornoChamada()))));
			}
			if(ra.getNome() != null && !ra.getNome().equals("")){
				if(ra.getTipoPesquisa().compareTo(ConstantesSistema.TIPO_PESQUISA_INICIAL) == 0){
					criteria.add(Expression.like("nomeContato", ra.getNome() + "%"));
				}else{
					criteria.add(Expression.like("nomeContato", "%" + ra.getNome() + "%"));
				}
			}
			if(ra.getUnidadeAtendimento() != null && !ra.getUnidadeAtendimento().equals("")){
				UnidadeOrganizacional unidadeAtendimento = new UnidadeOrganizacional();
				unidadeAtendimento.setId(ra.getUnidadeAtendimento());
				criteria.add(Expression.eq("atendimentoUnidadeOrganizacional", unidadeAtendimento));
			}
			if(ra.getUnidadeRetornoChamada() != null && !ra.getUnidadeRetornoChamada().equals("")){
				UnidadeOrganizacional unidadeRetornoChamada = new UnidadeOrganizacional();
				unidadeRetornoChamada.setId(ra.getUnidadeAtendimento());
				criteria.add(Expression.eq("retornoChamadaUnidadeOrganizacional", unidadeRetornoChamada));
			}
			if(ra.getUsuarioAtendimento() != null && !ra.getUsuarioAtendimento().equals("")){
				Usuario usuarioAtendimento = new Usuario();
				usuarioAtendimento.setId(ra.getUsuarioAtendimento());
				criteria.add(Expression.eq("atendimentoUsuario", usuarioAtendimento));
			}
			if(ra.getUsuarioRetornoChamada() != null && !ra.getUsuarioRetornoChamada().equals("")){
				Usuario usuarioRetornoChamada = new Usuario();
				usuarioRetornoChamada.setId(ra.getUsuarioRetornoChamada());
				criteria.add(Expression.eq("retornoChamadaUsuario", usuarioRetornoChamada));
			}
			if(ra.getColecaoTipoSolicitacaoEspecificacao() != null && !ra.getColecaoTipoSolicitacaoEspecificacao().isEmpty()){
				criteria.add(Restrictions.in("solicitacaoTipoEspecificacao", ra.getColecaoTipoSolicitacaoEspecificacao()));
			}
			if(ra.getAtendimentoIncompletoMotivo() != null && !ra.getAtendimentoIncompletoMotivo().isEmpty()){
				criteria.add(Restrictions.in("atendimentoIncompletoMotivo", ra.getAtendimentoIncompletoMotivo()));
			}
			if(ra.getChamadaInicial() != null && ra.getChamadaFinal() != null){
				criteria.add(Restrictions.between("duracaoChamada", ra.getChamadaInicial(), ra.getChamadaFinal()));
			}
			if(ra.getNumeroPagina() != -1){
				listaRA = (Collection<AtendimentoIncompleto>) criteria.setFirstResult(10 * ra.getNumeroPagina()).list();
			}else{
				listaRA = (Collection<AtendimentoIncompleto>) criteria.list();
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return listaRA;
	}

	public AtendimentoIncompleto pesquisarRAIncompleta(Integer idRAi) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		AtendimentoIncompleto retorno = null;
		try{
			Criteria criteria = session.createCriteria(AtendimentoIncompleto.class);
			criteria.add(Restrictions.eq("id", idRAi));
			retorno = (AtendimentoIncompleto) criteria.uniqueResult();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * UC?? - ????????
	 * 
	 * @author Rômulo Aurélio Filho
	 * @date 20/03/2007
	 * @descricao O método retorna um objeto com a maior data de Tramite
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarTramite(Integer idRA) throws ErroRepositorioException{

		Collection collTramite = new ArrayList();

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = "SELECT tram FROM Tramite tram " + " WHERE tram.registroAtendimento= :idRA)";

			collTramite = (Collection) session.createQuery(consulta).setInteger("idRA", idRA).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return collTramite;
	}

	/**
	 * Permite obter o Tipo de Solicitação de um registro de atendimento
	 * 
	 * @author Ailton Sousa
	 * @date 08/02/2011
	 * @param idRegistroAtendimento
	 * @throws ControladorException
	 */
	public Integer obterTipoSolicitacaoRA(Integer idRegistroAtendimento) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select rgat.solicitacaoTipoEspecificacao.solicitacaoTipo.id " + "from RegistroAtendimento rgat "
							+ "where rgat.id = :idRegistroAtendimento ";

			retorno = (Integer) session.createQuery(consulta).setInteger("idRegistroAtendimento", idRegistroAtendimento).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC3002] Inserir Mensagem Tipo Solicitação Especificação
	 * Verificar descrição da Mensagem do Tipo de solicitação com especificação e indicador uso
	 * ativo se já inserido na base .
	 * [SF0001] – Verificar existencia da descrição
	 * 
	 * @author Ailton Junior
	 * @date 23/02/2011
	 * @param descricaoMensagemTipoSolicitacao
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaDescricaoMensagemTipoSolicitacao(String descricaoMensagemTipoSolicitacao)
					throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT solicitacaoTipoEspecificacaoMensagem.id "
							+ "FROM SolicitacaoTipoEspecificacaoMensagem solicitacaoTipoEspecificacaoMensagem "
							+ "WHERE solicitacaoTipoEspecificacaoMensagem.descricao =:descricaoMensagemTipoSolicitacao AND "
							+ "solicitacaoTipoEspecificacaoMensagem.indicadorUso =:indicadorUso";

			retorno = (Integer) session.createQuery(consulta)
							.setString("descricaoMensagemTipoSolicitacao", descricaoMensagemTipoSolicitacao)
							.setShort("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Verificar se existe o vínculo entre a localidade e o munícipio
	 * 
	 * @author isilva
	 * @date 23/02/2011
	 * @param idLocalidade
	 * @param idMunicipio
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean existeVinculoLocalidadeMunicipio(Integer idLocalidade, Integer idMunicipio) throws ErroRepositorioException{

		boolean retorno = false;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer("");

		try{
			consulta.append("select ");
			consulta.append("loca.id ");
			consulta.append("from Localidade loca ");
			consulta.append("where ");
			consulta.append("loca.id = :idLocalidade and ");
			consulta.append("loca.indicadorUso = :indicadorUsoLocalidade and ");
			consulta.append("loca.municipio.id = :idMunicipio and ");
			consulta.append("loca.municipio.indicadorUso = :indicadorUsoMunicipio ");

			Integer retornoConsulta = (Integer) session.createQuery(consulta.toString()).setInteger("idLocalidade", idLocalidade)
							.setInteger("idMunicipio", idMunicipio)
							.setShort("indicadorUsoLocalidade", ConstantesSistema.INDICADOR_USO_ATIVO)
							.setShort("indicadorUsoMunicipio", ConstantesSistema.INDICADOR_USO_ATIVO).setMaxResults(1).uniqueResult();

			if(retornoConsulta != null && retornoConsulta.intValue() > 0){
				retorno = true;
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0XXX] Relatório Resumo de Registro de Atendimento
	 * Obter dados do Relatório Resumo de Registro de Atendimento pelos
	 * parametros informados no filtro
	 * 
	 * @author Anderson Italo
	 * @date 15/03/2011
	 */
	public Collection pesquisaRelatorioResumoRA(FiltrarRegistroAtendimentoHelper filtroRA, int tipoAgrupamento)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");
		Query query = null;
		Map parameters = new HashMap();

		try{

			if(tipoAgrupamento == 1){

				// caso seja a consulta para preenchimento do detalhe
				consulta.append("SELECT ste.id as idEspecificacao, " + "ste.descricao as descricaoEspecificacao, "
								+ "st.id as idTipoSolicitacao, " + "st.descricao as descricaoTipoSolicitacao, "
								+ "uni.id as idUnidade, uni.descricao as descricaoUnidade, " + "count(ra.id) as totalRAPorEspecificacao "
								+ "FROM RegistroAtendimento ra " + "INNER JOIN ra.solicitacaoTipoEspecificacao ste "
								+ "INNER JOIN ste.solicitacaoTipo st " + "LEFT JOIN ra.registroAtendimentoUnidades rau "
								+ "INNER JOIN rau.unidadeOrganizacional uni ");

				if((filtroRA.getColecaoUnidadeAtual() != null && !filtroRA.getColecaoUnidadeAtual().isEmpty())
								|| filtroRA.getUnidadeSuperior() != null){

					consulta.append("LEFT JOIN ra.tramites tram ");
				}
			}else{

				// caso contrário caso seja a consulta para preenchimento dos totalizadores
				consulta.append("SELECT ste.id as idEspecificacao, " + "ste.descricao as descricaoEspecificacao, "
								+ "st.id as idTipoSolicitacao, " + "st.descricao as descricaoTipoSolicitacao, "
								+ "count(ra.id) as totalRAPorEspecificacao " + "FROM RegistroAtendimento ra "
								+ "INNER JOIN ra.solicitacaoTipoEspecificacao ste " + "INNER JOIN ste.solicitacaoTipo st "
								+ "LEFT JOIN ra.registroAtendimentoUnidades rau " + "INNER JOIN rau.unidadeOrganizacional uni ");

				if((filtroRA.getColecaoUnidadeAtual() != null && !filtroRA.getColecaoUnidadeAtual().isEmpty())
								|| filtroRA.getUnidadeSuperior() != null){

					consulta.append("LEFT JOIN ra.tramites tram ");
				}

			}

			// Verifica parâmetros informados no filtro
			consulta.append(" Where ");
			boolean informouParametro = false;

			// Situação
			if(filtroRA.getCodigoSituacao() != null
							&& Short.valueOf(filtroRA.getCodigoSituacao()).intValue() != RegistroAtendimento.SITUACAO_TODOS){

				// Caso seja igual a "501", é para consultar pelos RAs
				// Reiterados.
				if(Short.valueOf(filtroRA.getCodigoSituacao()).equals(Short.valueOf("501"))){

					consulta.append(" ra.codigoSituacao = :idSituacao AND ra.quantidadeReiteracao IS NOT NULL");
					parameters.put("idSituacao", RegistroAtendimento.SITUACAO_PENDENTE);
					informouParametro = true;
				}else{

					consulta.append(" ra.codigoSituacao = :idSituacao ");
					parameters.put("idSituacao", filtroRA.getCodigoSituacao());
					informouParametro = true;
				}
			}

			// Tipo de Solicitação
			if(filtroRA.getColecaoTipoSolicitacao() != null && !filtroRA.getColecaoTipoSolicitacao().isEmpty()){

				String idsTipo = "";
				Object[] tiposFiltro = filtroRA.getColecaoTipoSolicitacao().toArray();

				for(int i = 0; i < tiposFiltro.length; i++){
					if(i == 0) idsTipo += " " + tiposFiltro[i].toString();
					else idsTipo += ", " + tiposFiltro[i].toString();
				}

				if(informouParametro) consulta.append(" AND st.id in ( " + idsTipo + ") ");
				else consulta.append(" st.id in (" + idsTipo + ") ");

				informouParametro = true;
			}

			// Especificação
			if(filtroRA.getColecaoTipoSolicitacaoEspecificacao() != null && !filtroRA.getColecaoTipoSolicitacaoEspecificacao().isEmpty()){

				String idsEspecificacoes = "";
				Object[] especificacoesFiltro = filtroRA.getColecaoTipoSolicitacaoEspecificacao().toArray();

				for(int i = 0; i < especificacoesFiltro.length; i++){
					if(i == 0) idsEspecificacoes += " " + especificacoesFiltro[i].toString();
					else idsEspecificacoes += ", " + especificacoesFiltro[i].toString();
				}

				if(informouParametro) consulta.append(" AND ste.id in ( " + idsEspecificacoes + ") ");
				else consulta.append(" ste.id in (" + idsEspecificacoes + ") ");

				informouParametro = true;
			}

			// Período de Atendimento
			if(filtroRA.getDataAtendimentoInicial() != null && filtroRA.getDataAtendimentoFinal() != null){

				if(informouParametro){

					consulta.append(" AND ra.registroAtendimento BETWEEN (:dataAtendimentoInicial) " + "AND (:dataAtendimentoFinal) ");
				}else{

					consulta.append(" ra.registroAtendimento BETWEEN (:dataAtendimentoInicial) " + "AND (:dataAtendimentoFinal) ");
				}

				parameters.put("dataAtendimentoInicial", filtroRA.getDataAtendimentoInicial());
				parameters.put("dataAtendimentoFinal", filtroRA.getDataAtendimentoFinal());
				informouParametro = true;
			}

			// Período de Encerramento
			if(filtroRA.getDataEncerramentoInicial() != null && filtroRA.getDataEncerramentoFinal() != null){

				if(informouParametro){

					consulta.append(" AND ra.dataEncerramento BETWEEN (:dataEncerramentoInicial) " + "AND (:dataEncerramentoFinal) ");
				}else{

					consulta.append(" ra.dataEncerramento BETWEEN (:dataEncerramentoInicial) " + "AND (:dataEncerramentoFinal) ");
				}

				parameters.put("dataEncerramentoInicial", filtroRA.getDataEncerramentoInicial());
				parameters.put("dataEncerramentoFinal", filtroRA.getDataEncerramentoFinal());
				informouParametro = true;
			}

			// Unidade de Atendimento
			if(filtroRA.getColecaoUnidadeAtendimento() != null && !filtroRA.getColecaoUnidadeAtendimento().isEmpty()){

				String idsUnidades = "";
				Object[] unidadesFiltro = filtroRA.getColecaoUnidadeAtendimento().toArray();

				for(int i = 0; i < unidadesFiltro.length; i++){
					if(i == 0) idsUnidades += " " + unidadesFiltro[i].toString();
					else idsUnidades += ", " + unidadesFiltro[i].toString();
				}

				if(informouParametro) consulta.append(" AND rau.unidadeOrganizacional.id in (" + idsUnidades + ")");
				else consulta.append(" rau.unidadeOrganizacional.id in (" + idsUnidades + ")");

				informouParametro = true;
			}

			// Unidade Atual
			if(filtroRA.getColecaoUnidadeAtual() != null && !filtroRA.getColecaoUnidadeAtual().isEmpty()){

				String idsUnidades = "";
				Object[] unidadesFiltro = filtroRA.getColecaoUnidadeAtual().toArray();

				for(int i = 0; i < unidadesFiltro.length; i++){
					if(i == 0) idsUnidades += " " + unidadesFiltro[i].toString();
					else idsUnidades += ", " + unidadesFiltro[i].toString();
				}

				if(informouParametro) consulta.append(" AND tram.unidadeOrganizacionalDestino.id in (" + idsUnidades + ")");
				else consulta.append(" tram.unidadeOrganizacionalDestino.id in (" + idsUnidades + ")");

				consulta.append(" AND tram.dataTramite in (select max(tr.dataTramite) from Tramite tr  "
								+ " where tr.registroAtendimento.id = ra.id)");
				informouParametro = true;
			}

			// Unidade Superior ou Chefia
			if(filtroRA.getUnidadeSuperior() != null){

				String idsUnidades = "";
				Object[] unidadesFiltro = filtroRA.getColecaoUnidadesSubordinadas().toArray();

				for(int i = 0; i < unidadesFiltro.length; i++){
					if(i == 0) idsUnidades += " " + unidadesFiltro[i].toString();
					else idsUnidades += ", " + unidadesFiltro[i].toString();
				}

				if(informouParametro) consulta.append(" AND tram.unidadeOrganizacionalDestino.id in (" + idsUnidades + ")");
				else consulta.append(" tram.unidadeOrganizacionalDestino.id in (" + idsUnidades + ")");

				consulta.append(" AND tram.dataTramite in (select max(tr.dataTramite) from Tramite tr  "
								+ " where tr.registroAtendimento.id = ra.id)");

				informouParametro = true;
			}

			if(informouParametro){
				consulta.append(" AND rau.atendimentoRelacaoTipo.id = :idAtendimentoTipo ");
				parameters.put("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR);
			}else{
				informouParametro = true;
				consulta.append(" rau.atendimentoRelacaoTipo.id = :idAtendimentoTipo ");
				parameters.put("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR);
			}

			if(tipoAgrupamento == 1){

				consulta.append(" GROUP BY uni.id, uni.descricao, st.id, st.descricao," + " ste.id, ste.descricao "
								+ " ORDER BY uni.id, uni.descricao, st.id, st.descricao," + " ste.id, ste.descricao ");
			}else{

				consulta.append(" GROUP BY st.id, st.descricao," + " ste.id, ste.descricao " + " ORDER BY st.id, st.descricao,"
								+ " ste.id, ste.descricao ");
			}

			StringBuffer consultaFinal = new StringBuffer("");
			if(!informouParametro){

				consultaFinal.append(consulta.toString().replace("Where", ""));
			}else{

				consultaFinal.append(consulta.toString());
			}

			query = session.createQuery(consultaFinal.toString());

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();

			while(iterMap.hasNext()){

				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Set){
					Set setList = (HashSet) parameters.get(key);
					query.setParameterList(key, setList);
				}else if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof Date){
					Date data = (Date) parameters.get(key);
					query.setTimestamp(key, data);
				}else{
					query.setParameter(key, parameters.get(key));
				}
			}

			retorno = query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0XXX] Relatório Gestão de Registro de Atendimento
	 * Obter dados do Relatório de Gestão de Registro de Atendimento pelos
	 * parametros informados no filtro
	 * 
	 * @author Anderson Italo
	 * @param tipoRelatorio
	 *            (0 == Sintético e 1 == Analítico),
	 *            FiltrarRegistroAtendimentoHelper filtroRA
	 * @date 28/03/2011
	 */
	public Collection pesquisaRelatorioGestaoRA(FiltrarRegistroAtendimentoHelper filtroRA, int tipoRelatorio)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");
		StringBuffer condicoes = new StringBuffer("");
		;
		Query query = null;
		Map parameters = new HashMap();

		try{

			consulta.append(this.retornaQueryRelatorioGestaoRA(filtroRA, tipoRelatorio));
			condicoes.append(" Where ");
			boolean informouParametro = false;

			// Situação
			if(filtroRA.getCodigoSituacao() != null
							&& Short.valueOf(filtroRA.getCodigoSituacao()).intValue() != RegistroAtendimento.SITUACAO_TODOS){

				// Caso seja igual a "501", é para consultar pelos RAs
				// Reiterados.
				if(Short.valueOf(filtroRA.getCodigoSituacao()).equals(Short.valueOf("501"))){

					condicoes.append(" ra.rgat_cdsituacao = 1 AND rgat_qtreiteracoes IS NOT NULL");
				}else{

					condicoes.append(" ra.rgat_cdsituacao = :idSituacao ");
					parameters.put("idSituacao", filtroRA.getCodigoSituacao());
				}

				informouParametro = true;
			}

			// Tipo de Solicitação
			if(filtroRA.getColecaoTipoSolicitacao() != null && !filtroRA.getColecaoTipoSolicitacao().isEmpty()){

				String idsTipo = "";
				Object[] tiposFiltro = filtroRA.getColecaoTipoSolicitacao().toArray();

				for(int i = 0; i < tiposFiltro.length; i++){
					if(i == 0) idsTipo += " " + tiposFiltro[i].toString();
					else idsTipo += ", " + tiposFiltro[i].toString();
				}

				if(informouParametro) condicoes.append(" AND st.sotp_id in ( " + idsTipo + ") ");
				else condicoes.append(" st.sotp_id in (" + idsTipo + ") ");

				informouParametro = true;
			}

			// Especificação
			if(filtroRA.getColecaoTipoSolicitacaoEspecificacao() != null && !filtroRA.getColecaoTipoSolicitacaoEspecificacao().isEmpty()){

				String idsEspecificacoes = "";
				Object[] especificacoesFiltro = filtroRA.getColecaoTipoSolicitacaoEspecificacao().toArray();

				for(int i = 0; i < especificacoesFiltro.length; i++){
					if(i == 0) idsEspecificacoes += " " + especificacoesFiltro[i].toString();
					else idsEspecificacoes += ", " + especificacoesFiltro[i].toString();
				}

				if(informouParametro) condicoes.append(" AND ste.step_id in ( " + idsEspecificacoes + ") ");
				else condicoes.append(" ste.step_id in (" + idsEspecificacoes + ") ");

				informouParametro = true;
			}

			// Período de Atendimento
			if(filtroRA.getDataAtendimentoInicial() != null && filtroRA.getDataAtendimentoFinal() != null){

				if(informouParametro){

					condicoes.append(" AND ra.rgat_tmregistroatendimento BETWEEN (:dataAtendimentoInicial) ");
					condicoes.append("AND (:dataAtendimentoFinal) ");
				}else{

					condicoes.append(" ra.rgat_tmregistroatendimento BETWEEN (:dataAtendimentoInicial) ");
					condicoes.append("AND (:dataAtendimentoFinal) ");
				}

				parameters.put("dataAtendimentoInicial", filtroRA.getDataAtendimentoInicial());
				parameters.put("dataAtendimentoFinal", filtroRA.getDataAtendimentoFinal());
				informouParametro = true;
			}

			// Período de Encerramento
			if(filtroRA.getDataEncerramentoInicial() != null && filtroRA.getDataEncerramentoFinal() != null){

				if(informouParametro){

					condicoes.append(" AND ra.rgat_tmencerramento BETWEEN (:dataEncerramentoInicial) ");
					condicoes.append("AND (:dataEncerramentoFinal) ");
				}else{

					condicoes.append(" ra.rgat_tmencerramento BETWEEN (:dataEncerramentoInicial) ");
					condicoes.append("AND (:dataEncerramentoFinal) ");
				}

				parameters.put("dataEncerramentoInicial", filtroRA.getDataEncerramentoInicial());
				parameters.put("dataEncerramentoFinal", filtroRA.getDataEncerramentoFinal());
				informouParametro = true;
			}

			// Unidade de Atendimento
			if(filtroRA.getColecaoUnidadeAtendimento() != null && !filtroRA.getColecaoUnidadeAtendimento().isEmpty()){

				String idsUnidades = "";
				Object[] unidadesFiltro = filtroRA.getColecaoUnidadeAtendimento().toArray();

				for(int i = 0; i < unidadesFiltro.length; i++){
					if(i == 0) idsUnidades += " " + unidadesFiltro[i].toString();
					else idsUnidades += ", " + unidadesFiltro[i].toString();
				}

				if(informouParametro) condicoes.append(" AND rau.unid_id in (" + idsUnidades + ")");
				else condicoes.append(" rau.unid_id in (" + idsUnidades + ")");

				informouParametro = true;
			}

			// Unidade Atual
			if(filtroRA.getColecaoUnidadeAtual() != null && !filtroRA.getColecaoUnidadeAtual().isEmpty()){

				String idsUnidades = "";
				Object[] unidadesFiltro = filtroRA.getColecaoUnidadeAtual().toArray();

				for(int i = 0; i < unidadesFiltro.length; i++){
					if(i == 0) idsUnidades += " " + unidadesFiltro[i].toString();
					else idsUnidades += ", " + unidadesFiltro[i].toString();
				}

				if(informouParametro) condicoes.append(" AND tram.unid_iddestino in (" + idsUnidades + ")");
				else condicoes.append(" tram.unid_iddestino in (" + idsUnidades + ")");

				condicoes.append(" AND tram.tram_tmtramite in (select max(tr.tram_tmtramite) from tramite tr  ");
				condicoes.append(" where tr.rgat_id = ra.rgat_id)");
				informouParametro = true;
			}

			// Unidade Superior ou Chefia
			if(filtroRA.getUnidadeSuperior() != null){

				String idsUnidades = "";
				Object[] unidadesFiltro = filtroRA.getColecaoUnidadesSubordinadas().toArray();

				for(int i = 0; i < unidadesFiltro.length; i++){
					if(i == 0) idsUnidades += " " + unidadesFiltro[i].toString();
					else idsUnidades += ", " + unidadesFiltro[i].toString();
				}

				if(informouParametro) condicoes.append(" AND tram.unid_iddestino in (" + idsUnidades + ")");
				else condicoes.append(" tram.unid_iddestino in (" + idsUnidades + ")");

				condicoes.append(" AND tram.tram_tmtramite in (select max(tr.tram_tmtramite) from tramite tr  ");
				condicoes.append(" where tr.rgat_id = ra.rgat_id)");
				informouParametro = true;
			}

			if(informouParametro) consulta.append(condicoes.toString());

			// caso seja analítico
			if(tipoRelatorio == 1){

				consulta.append(" GROUP BY uni.unid_id, uni.unid_dsunidade, uni.unid_dssiglaunidade, st.sotp_id, st.sotp_dssolicitacaotipo,");
				consulta.append(" ste.step_id, ste.step_dssolicitacaotipoespecifi ");
				consulta.append(" ORDER BY uni.unid_id, uni.unid_dsunidade, uni.unid_dssiglaunidade, st.sotp_id, st.sotp_dssolicitacaotipo,");
				consulta.append(" ste.step_id, ste.step_dssolicitacaotipoespecifi ");
			}else{

				// caso contrário é sintético
				consulta.append(" GROUP BY uni.unid_id, uni.unid_dsunidade, uni.unid_dssiglaunidade");
				consulta.append(" ORDER BY uni.unid_id, uni.unid_dsunidade, uni.unid_dssiglaunidade");
			}

			if(tipoRelatorio == 1){

				query = session.createSQLQuery(consulta.toString()).addScalar("idEspecificacao", Hibernate.INTEGER)
								.addScalar("descricaoEspecificacao", Hibernate.STRING).addScalar("idTipoSolicitacao", Hibernate.INTEGER)
								.addScalar("descricaoTipoSolicitacao", Hibernate.STRING).addScalar("idUnidade", Hibernate.INTEGER)
								.addScalar("descricaoUnidade", Hibernate.STRING).addScalar("qtdSolicitada", Hibernate.INTEGER)
								.addScalar("qtdAtendido", Hibernate.INTEGER).addScalar("qtdAtendidoNP", Hibernate.INTEGER)
								.addScalar("qtdAtendidoFP", Hibernate.INTEGER).addScalar("qtdSolicitadaOrigem", Hibernate.INTEGER)
								.addScalar("qtdAtendidoOrigem", Hibernate.INTEGER).addScalar("qtdAtendidoNPOrigem", Hibernate.INTEGER)
								.addScalar("qtdAtendidoFPOrigem", Hibernate.INTEGER).addScalar("qtdSolicitadaOutros", Hibernate.INTEGER)
								.addScalar("qtdAtendidoOutros", Hibernate.INTEGER).addScalar("qtdAtendidoNPOutros", Hibernate.INTEGER)
								.addScalar("qtdAtendidoFPOutros", Hibernate.INTEGER).addScalar("qtdResidualPrazo", Hibernate.INTEGER)
								.addScalar("qtdResidualPrazoOrigem", Hibernate.INTEGER)
								.addScalar("qtdResidualPrazoOutros", Hibernate.INTEGER)
								.addScalar("qtdResidualForaPrazo", Hibernate.INTEGER)
								.addScalar("qtdResidualForaPrazoOrigem", Hibernate.INTEGER)
								.addScalar("qtdResidualForaPrazoOutros", Hibernate.INTEGER).addScalar("prazoPadraoExec", Hibernate.INTEGER)
								.addScalar("prazoMedioExec", Hibernate.DOUBLE).addScalar("prazoMedioExecOrigem", Hibernate.DOUBLE)
								.addScalar("prazoMedioExecOutros", Hibernate.DOUBLE).addScalar("atrasoMedioExec", Hibernate.DOUBLE)
								.addScalar("atrasoMedioExecOrigem", Hibernate.DOUBLE).addScalar("atrasoMedioExecOutros", Hibernate.DOUBLE)
								.addScalar("siglaUnidade", Hibernate.STRING);
			}else{

				query = session.createSQLQuery(consulta.toString()).addScalar("idUnidade", Hibernate.INTEGER)
								.addScalar("descricaoUnidade", Hibernate.STRING).addScalar("qtdSolicitada", Hibernate.INTEGER)
								.addScalar("qtdAtendido", Hibernate.INTEGER).addScalar("qtdAtendidoNP", Hibernate.INTEGER)
								.addScalar("qtdAtendidoFP", Hibernate.INTEGER).addScalar("qtdSolicitadaOrigem", Hibernate.INTEGER)
								.addScalar("qtdAtendidoOrigem", Hibernate.INTEGER).addScalar("qtdAtendidoNPOrigem", Hibernate.INTEGER)
								.addScalar("qtdAtendidoFPOrigem", Hibernate.INTEGER).addScalar("qtdSolicitadaOutros", Hibernate.INTEGER)
								.addScalar("qtdAtendidoOutros", Hibernate.INTEGER).addScalar("qtdAtendidoNPOutros", Hibernate.INTEGER)
								.addScalar("qtdAtendidoFPOutros", Hibernate.INTEGER).addScalar("qtdResidualPrazo", Hibernate.INTEGER)
								.addScalar("qtdResidualPrazoOrigem", Hibernate.INTEGER)
								.addScalar("qtdResidualPrazoOutros", Hibernate.INTEGER)
								.addScalar("qtdResidualForaPrazo", Hibernate.INTEGER)
								.addScalar("qtdResidualForaPrazoOrigem", Hibernate.INTEGER)
								.addScalar("qtdResidualForaPrazoOutros", Hibernate.INTEGER).addScalar("siglaUnidade", Hibernate.STRING);
			}

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();

			while(iterMap.hasNext()){

				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Set){
					Set setList = (HashSet) parameters.get(key);
					query.setParameterList(key, setList);
				}else if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof Date){
					Date data = (Date) parameters.get(key);
					query.setTimestamp(key, data);
				}else{
					query.setParameter(key, parameters.get(key));
				}
			}

			retorno = query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0XXX] Relatório Gestão de Registro de Atendimento
	 * Obter dados da consulta sem as condições.
	 * 
	 * @author Anderson Italo
	 * @date 28/03/2011
	 * @param tipoRelatorio
	 *            (0 == Sintético e 1 == Analítico),
	 *            FiltrarRegistroAtendimentoHelper filtroRA
	 */
	private String retornaQueryRelatorioGestaoRA(FiltrarRegistroAtendimentoHelper filtroRA, int tipoRelatorio){

		StringBuffer retorno = new StringBuffer("");

		// caso seja sintético
		if(tipoRelatorio == 0){

			retorno.append("SELECT ");
			retorno.append("uni.unid_id as idUnidade, ");
			retorno.append("uni.unid_dsunidade as descricaoUnidade, ");
			retorno.append("count(distinct(ra.rgat_id)) as qtdSolicitada, ");
			retorno.append("sum((case when (ra.RGAT_TMENCERRAMENTO is not null and rau.attp_id = 1 and ra.RGAT_CDSITUACAO = 2) then 1 else 0 end)) as qtdAtendido, ");
			retorno.append("sum((case when (ra.RGAT_TMENCERRAMENTO is not null and rau.attp_id = 1 and ra.RGAT_CDSITUACAO = 2 and ra.RGAT_TMENCERRAMENTO <= ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdAtendidoNP, ");
			retorno.append("sum((case when (ra.RGAT_TMENCERRAMENTO is not null and rau.attp_id = 1 and ra.RGAT_CDSITUACAO = 2 and ra.RGAT_TMENCERRAMENTO > ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdAtendidoFP, ");
			retorno.append("sum((case when uni.UNID_ICCENTRALATENDIMENTO = 1 then 1 else 0 end)) as qtdSolicitadaOrigem, ");
			retorno.append("sum((case when (ra.RGAT_TMENCERRAMENTO is not null and rau.attp_id = 1 and uni.UNID_ICCENTRALATENDIMENTO = 1 and ra.RGAT_CDSITUACAO = 2) then 1 else 0 end)) as qtdAtendidoOrigem, ");
			retorno.append("sum((case when (ra.RGAT_TMENCERRAMENTO is not null and rau.attp_id = 1 and uni.UNID_ICCENTRALATENDIMENTO = 1 and ra.RGAT_CDSITUACAO = 2 and ra.RGAT_TMENCERRAMENTO <= ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdAtendidoNPOrigem, ");
			retorno.append("sum((case when (ra.RGAT_TMENCERRAMENTO is not null and rau.attp_id = 1 and uni.UNID_ICCENTRALATENDIMENTO = 1 and ra.RGAT_CDSITUACAO = 2 and ra.RGAT_TMENCERRAMENTO > ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdAtendidoFPOrigem, ");
			retorno.append("sum((case when uni.UNID_ICCENTRALATENDIMENTO = 2 then 1 else 0 end)) as qtdSolicitadaOutros, ");
			retorno.append("sum((case when (ra.RGAT_TMENCERRAMENTO is not null and rau.attp_id = 1 and uni.UNID_ICCENTRALATENDIMENTO = 2 and ra.RGAT_CDSITUACAO = 2) then 1 else 0 end)) as qtdAtendidoOutros, ");
			retorno.append("sum((case when (ra.RGAT_TMENCERRAMENTO is not null and rau.attp_id = 1 and uni.UNID_ICCENTRALATENDIMENTO = 2 and ra.RGAT_CDSITUACAO = 2 and ra.RGAT_TMENCERRAMENTO <= ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdAtendidoNPOutros, ");
			retorno.append("sum((case when (ra.RGAT_TMENCERRAMENTO is not null and rau.attp_id = 1 and uni.UNID_ICCENTRALATENDIMENTO = 2 and ra.RGAT_CDSITUACAO = 2 and ra.RGAT_TMENCERRAMENTO > ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdAtendidoFPOutros, ");
			retorno.append("sum((case when (ra.RGAT_CDSITUACAO = 1 and current_timestamp <= ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdResidualPrazo, ");
			retorno.append("sum((case when (uni.UNID_ICCENTRALATENDIMENTO = 1 and ra.RGAT_CDSITUACAO = 1 and current_timestamp <= ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdResidualPrazoOrigem, ");
			retorno.append("sum((case when (uni.UNID_ICCENTRALATENDIMENTO = 2 and ra.RGAT_CDSITUACAO = 1 and current_timestamp <= ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdResidualPrazoOutros, ");
			retorno.append("sum((case when (ra.RGAT_CDSITUACAO = 1 and current_timestamp > ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdResidualForaPrazo, ");
			retorno.append("sum((case when (uni.UNID_ICCENTRALATENDIMENTO = 1 and ra.RGAT_CDSITUACAO = 1 and current_timestamp > ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdResidualForaPrazoOrigem, ");
			retorno.append("sum((case when (uni.UNID_ICCENTRALATENDIMENTO = 2 and ra.RGAT_CDSITUACAO = 1 and current_timestamp > ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdResidualForaPrazoOutros, ");
			retorno.append("uni.unid_dssiglaunidade as siglaUnidade ");
			retorno.append("FROM registro_atendimento ra ");
			retorno.append("LEFT JOIN registro_atendimento_unidade rau on (ra.rgat_id = rau.rgat_id and rau.attp_id=1) ");
			retorno.append("INNER JOIN unidade_organizacional uni on rau.unid_id = uni.unid_id ");

		}else{
			// caso contrário é analítico
			retorno.append("SELECT ste.step_id as idEspecificacao, ste.step_dssolicitacaotipoespecifi as descricaoEspecificacao, st.sotp_id as idTipoSolicitacao, ");
			retorno.append("st.sotp_dssolicitacaotipo as descricaoTipoSolicitacao, ");
			retorno.append("uni.unid_id as idUnidade, ");
			retorno.append("uni.unid_dsunidade as descricaoUnidade, ");
			retorno.append("count(distinct(ra.rgat_id)) as qtdSolicitada, ");
			retorno.append("sum((case when (ra.RGAT_TMENCERRAMENTO is not null and rau.attp_id = 1 and ra.RGAT_CDSITUACAO = 2) then 1 else 0 end)) as qtdAtendido, ");
			retorno.append("sum((case when (ra.RGAT_TMENCERRAMENTO is not null and rau.attp_id = 1 and ra.RGAT_CDSITUACAO = 2 and ra.RGAT_TMENCERRAMENTO <= ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdAtendidoNP, ");
			retorno.append("sum((case when (ra.RGAT_TMENCERRAMENTO is not null and rau.attp_id = 1 and ra.RGAT_CDSITUACAO = 2 and ra.RGAT_TMENCERRAMENTO > ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdAtendidoFP, ");
			retorno.append("sum((case when uni.UNID_ICCENTRALATENDIMENTO = 1 then 1 else 0 end)) as qtdSolicitadaOrigem, ");
			retorno.append("sum((case when (ra.RGAT_TMENCERRAMENTO is not null and rau.attp_id = 1 and uni.UNID_ICCENTRALATENDIMENTO = 1 and ra.RGAT_CDSITUACAO = 2) then 1 else 0 end)) as qtdAtendidoOrigem, ");
			retorno.append("sum((case when (ra.RGAT_TMENCERRAMENTO is not null and rau.attp_id = 1 and uni.UNID_ICCENTRALATENDIMENTO = 1 and ra.RGAT_CDSITUACAO = 2 and ra.RGAT_TMENCERRAMENTO <= ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdAtendidoNPOrigem, ");
			retorno.append("sum((case when (ra.RGAT_TMENCERRAMENTO is not null and rau.attp_id = 1 and uni.UNID_ICCENTRALATENDIMENTO = 1 and ra.RGAT_CDSITUACAO = 2 and ra.RGAT_TMENCERRAMENTO > ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdAtendidoFPOrigem, ");
			retorno.append("sum((case when uni.UNID_ICCENTRALATENDIMENTO = 2 then 1 else 0 end)) as qtdSolicitadaOutros, ");
			retorno.append("sum((case when (ra.RGAT_TMENCERRAMENTO is not null and rau.attp_id = 1 and uni.UNID_ICCENTRALATENDIMENTO = 2 and ra.RGAT_CDSITUACAO = 2) then 1 else 0 end)) as qtdAtendidoOutros, ");
			retorno.append("sum((case when (ra.RGAT_TMENCERRAMENTO is not null and rau.attp_id = 1 and uni.UNID_ICCENTRALATENDIMENTO = 2 and ra.RGAT_CDSITUACAO = 2 and ra.RGAT_TMENCERRAMENTO <= ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdAtendidoNPOutros, ");
			retorno.append("sum((case when (ra.RGAT_TMENCERRAMENTO is not null and rau.attp_id = 1 and uni.UNID_ICCENTRALATENDIMENTO = 2 and ra.RGAT_CDSITUACAO = 2 and ra.RGAT_TMENCERRAMENTO > ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdAtendidoFPOutros, ");
			retorno.append("sum((case when (ra.RGAT_CDSITUACAO = 1 and current_timestamp <= ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdResidualPrazo, ");
			retorno.append("sum((case when (uni.UNID_ICCENTRALATENDIMENTO = 1 and ra.RGAT_CDSITUACAO = 1 and current_timestamp <= ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdResidualPrazoOrigem, ");
			retorno.append("sum((case when (uni.UNID_ICCENTRALATENDIMENTO = 2 and ra.RGAT_CDSITUACAO = 1 and current_timestamp <= ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdResidualPrazoOutros, ");
			retorno.append("sum((case when (ra.RGAT_CDSITUACAO = 1 and current_timestamp > ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdResidualForaPrazo, ");
			retorno.append("sum((case when (uni.UNID_ICCENTRALATENDIMENTO = 1 and ra.RGAT_CDSITUACAO = 1 and current_timestamp > ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdResidualForaPrazoOrigem, ");
			retorno.append("sum((case when (uni.UNID_ICCENTRALATENDIMENTO = 2 and ra.RGAT_CDSITUACAO = 1 and current_timestamp > ra.RGAT_DTPREVISTAATUAL) then 1 else 0 end)) as qtdResidualForaPrazoOutros, ");
			retorno.append("(select ste1.STEP_NNDIAPRAZO from SOLICITACAO_TIPO_ESPECIFICACAO ste1 where ste1.STEP_ID = ste.STEP_ID) as prazoPadraoExec, ");
			// + "-- Prazo médio de execução = Sum(qtdDias das atendidas) / (Qtd Atendida) "
			retorno.append("(case when sum((case when ra.RGAT_CDSITUACAO = 2 then 1 else 0 end)) > 0 then ");
			retorno.append("(sum((case when ra.RGAT_CDSITUACAO = 2 then ");
			retorno.append("(select to_date (to_char(ra.RGAT_TMENCERRAMENTO, 'DD-MM-YYYY'), 'DD-MM-YYYY') ");
			retorno.append("- to_date (to_char(ra.RGAT_TMREGISTROATENDIMENTO, 'DD-MM-YYYY'), 'DD-MM-YYYY') as intervaloDias1 from dual) ");
			retorno.append("else 0 end)) / sum((case when ra.RGAT_CDSITUACAO = 2 then 1 else 0 end))) ");
			retorno.append("else 0 end) as prazoMedioExec, ");
			retorno.append("(case when sum((case when uni.UNID_ICCENTRALATENDIMENTO = 1 and ra.RGAT_CDSITUACAO = 2 then 1 else 0 end)) > 0 then ");
			retorno.append("(sum((case when uni.UNID_ICCENTRALATENDIMENTO = 1 and ra.RGAT_CDSITUACAO = 2 then ");
			retorno.append("(select to_date (to_char(ra.RGAT_TMENCERRAMENTO, 'DD-MM-YYYY'), 'DD-MM-YYYY') ");
			retorno.append("- to_date (to_char(ra.RGAT_TMREGISTROATENDIMENTO, 'DD-MM-YYYY'), 'DD-MM-YYYY') as intervaloDias2 from dual) ");
			retorno.append("else 0 end)) / sum((case when uni.UNID_ICCENTRALATENDIMENTO = 1 and ra.RGAT_CDSITUACAO = 2 then 1 else 0 end))) ");
			retorno.append("else 0 end) as prazoMedioExecOrigem, ");
			retorno.append("(case when sum((case when uni.UNID_ICCENTRALATENDIMENTO = 2 and ra.RGAT_CDSITUACAO = 2 then 1 else 0 end)) > 0 then ");
			retorno.append("(sum((case when uni.UNID_ICCENTRALATENDIMENTO = 2 and ra.RGAT_CDSITUACAO = 2 then ");
			retorno.append("(select to_date (to_char(ra.RGAT_TMENCERRAMENTO, 'DD-MM-YYYY'), 'DD-MM-YYYY') ");
			retorno.append("- to_date (to_char(ra.RGAT_TMREGISTROATENDIMENTO, 'DD-MM-YYYY'), 'DD-MM-YYYY') as intervaloDias3 from dual) ");
			retorno.append("else 0 end)) / sum((case when uni.UNID_ICCENTRALATENDIMENTO = 2 and ra.RGAT_CDSITUACAO = 2 then 1 else 0 end))) ");
			retorno.append("else 0 end) as prazoMedioExecOutros, ");
			// -- Atraso médio de execução = Sum(qtdDias das residuais FP) / (Qtd residual FP)
			retorno.append("(case when sum((case when ra.RGAT_CDSITUACAO = 1 and current_timestamp > ra.RGAT_DTPREVISTAATUAL then 1 else 0 end)) > 0 then ");
			retorno.append("(sum((case when ra.RGAT_CDSITUACAO = 1 and current_timestamp > ra.RGAT_DTPREVISTAATUAL then ");
			retorno.append("(select to_date (to_char(current_timestamp, 'DD-MM-YYYY'), 'DD-MM-YYYY') ");
			retorno.append("- to_date (to_char(ra.RGAT_DTPREVISTAATUAL, 'DD-MM-YYYY'), 'DD-MM-YYYY') as intervaloDias1 from dual) ");
			retorno.append("else 0 end)) / sum((case when ra.RGAT_CDSITUACAO = 1 and current_timestamp > ra.RGAT_DTPREVISTAATUAL then 1 else 0 end))) ");
			retorno.append("else 0 end) as atrasoMedioExec, ");
			retorno.append("(case when sum((case when uni.UNID_ICCENTRALATENDIMENTO = 1 and ra.RGAT_CDSITUACAO = 1 ");
			retorno.append("and current_timestamp > ra.RGAT_DTPREVISTAATUAL then 1 else 0 end)) > 0 then ");
			retorno.append("(sum((case when uni.UNID_ICCENTRALATENDIMENTO = 1 and ra.RGAT_CDSITUACAO = 1 and current_timestamp > ra.RGAT_DTPREVISTAATUAL then ");
			retorno.append("(select to_date (to_char(current_timestamp, 'DD-MM-YYYY'), 'DD-MM-YYYY') ");
			retorno.append("- to_date (to_char(ra.RGAT_DTPREVISTAATUAL, 'DD-MM-YYYY'), 'DD-MM-YYYY') as intervaloDias1 from dual) ");
			retorno.append("else 0 end)) / sum((case when uni.UNID_ICCENTRALATENDIMENTO = 1 and ra.RGAT_CDSITUACAO = 1 and ");
			retorno.append("current_timestamp > ra.RGAT_DTPREVISTAATUAL then 1 else 0 end))) else 0 end) as atrasoMedioExecOrigem, ");
			retorno.append("(case when sum((case when uni.UNID_ICCENTRALATENDIMENTO = 2 and ra.RGAT_CDSITUACAO = 1 ");
			retorno.append("and current_timestamp > ra.RGAT_DTPREVISTAATUAL then 1 else 0 end)) > 0 then ");
			retorno.append("(sum((case when uni.UNID_ICCENTRALATENDIMENTO = 2 and ra.RGAT_CDSITUACAO = 1 and current_timestamp > ra.RGAT_DTPREVISTAATUAL then ");
			retorno.append("(select to_date (to_char(current_timestamp, 'DD-MM-YYYY'), 'DD-MM-YYYY') ");
			retorno.append("- to_date (to_char(ra.RGAT_DTPREVISTAATUAL, 'DD-MM-YYYY'), 'DD-MM-YYYY') as intervaloDias1 from dual) ");
			retorno.append("else 0 end)) / sum((case when uni.UNID_ICCENTRALATENDIMENTO = 2 and ra.RGAT_CDSITUACAO = 1 and ");
			retorno.append("current_timestamp > ra.RGAT_DTPREVISTAATUAL then 1 else 0 end))) else 0 end) as atrasoMedioExecOutros, ");
			retorno.append("uni.unid_dssiglaunidade as siglaUnidade ");
			retorno.append("FROM registro_atendimento ra ");
			retorno.append("INNER JOIN solicitacao_tipo_especificacao ste on ra.step_id = ste.step_id ");
			retorno.append("INNER JOIN solicitacao_tipo st on ste.sotp_id = st.sotp_id ");
			retorno.append("LEFT JOIN registro_atendimento_unidade rau on (ra.rgat_id = rau.rgat_id and rau.attp_id=1) ");
			retorno.append("INNER JOIN unidade_organizacional uni on rau.unid_id = uni.unid_id ");
		}

		if(tipoRelatorio == 0 && filtroRA.getColecaoTipoSolicitacao() != null && !filtroRA.getColecaoTipoSolicitacao().isEmpty()){

			retorno.append("INNER JOIN solicitacao_tipo_especificacao ste on ra.step_id = ste.step_id ");
			retorno.append("INNER JOIN solicitacao_tipo st on ste.sotp_id = st.sotp_id ");

		}

		if(filtroRA.getColecaoUnidadeAtual() != null || filtroRA.getUnidadeSuperior() != null){

			retorno.append("LEFT JOIN tramite tram on ra.rgat_id = tram.rgat_id ");
		}

		return retorno.toString();
	}

	/**
	 * [UC0XXX] Relatório Estatistica Atendimento por Atendente
	 * Obter dados do Relatório Estatistica Atendimento por Atendente pelos parametros informados no
	 * filtro
	 * [0] - idUnidade;
	 * [1] - descricaoUnidade;
	 * [2] - idUsuario;
	 * [3] - nomeUsuario;
	 * [4] - dataRegistroAtendimento;
	 * [5] - ultimaAlteracaoUnidade
	 * 
	 * @author isilva
	 * @date 24/03/2011
	 */
	public Collection pesquisaRelatorioEstatisticaAtendimentoPorAtendente(FiltrarRegistroAtendimentoHelper filtroRA)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");
		Query query = null;
		Map parameters = new HashMap();
		boolean informouParametro = false;

		try{

			consulta.append("SELECT distinct ");
			consulta.append("uni.id as idUnidade, ");
			consulta.append("uni.descricao as descricaoUnidade, ");
			consulta.append("usuario.id as idAtendente, ");
			consulta.append("usuario.nomeUsuario as atendente, ");
			consulta.append("ra.registroAtendimento as dataRegistroAtendimento, ");
			consulta.append("rau.ultimaAlteracao as ultimaAltaracaoRAU ");

			consulta.append("FROM RegistroAtendimento ra ");
			consulta.append("LEFT JOIN ra.registroAtendimentoUnidades rau ");
			consulta.append("INNER JOIN rau.unidadeOrganizacional uni ");
			consulta.append("LEFT JOIN rau.usuario usuario ");

			// Caso informou Unidade Atual ou Superior
			if((filtroRA.getColecaoUnidadeAtual() != null && !filtroRA.getColecaoUnidadeAtual().isEmpty())
							|| filtroRA.getUnidadeSuperior() != null){

				consulta.append("LEFT JOIN ra.tramites tram ");
			}

			// Especificação
			if(filtroRA.getColecaoTipoSolicitacaoEspecificacao() != null && !filtroRA.getColecaoTipoSolicitacaoEspecificacao().isEmpty()){
				consulta.append("INNER JOIN ra.solicitacaoTipoEspecificacao ste ");
			}

			// Tipo de Solicitação
			if(filtroRA.getColecaoTipoSolicitacao() != null && !filtroRA.getColecaoTipoSolicitacao().isEmpty()){

				if(filtroRA.getColecaoTipoSolicitacaoEspecificacao() == null || filtroRA.getColecaoTipoSolicitacaoEspecificacao().isEmpty()){
					consulta.append("INNER JOIN ra.solicitacaoTipoEspecificacao ste ");
				}

				consulta.append("INNER JOIN ste.solicitacaoTipo st ");
			}

			// caso seja a consulta para preenchimento do detalhe
			if(filtroRA.getUnidadeAtual() != null || filtroRA.getUnidadeSuperior() != null){
				consulta.append("LEFT JOIN ra.tramites tram ");
			}

			// Verifica parâmetros informados no filtro
			consulta.append(" Where ");

			consulta.append(" rau.atendimentoRelacaoTipo.id = (:idAtendimentoTipo) AND ");
			parameters.put("idAtendimentoTipo", AtendimentoRelacaoTipo.ABRIR_REGISTRAR);
			informouParametro = true;

			// Situação
			if(filtroRA.getCodigoSituacao() != null
							&& Short.valueOf(filtroRA.getCodigoSituacao()).intValue() != RegistroAtendimento.SITUACAO_TODOS){

				// Caso seja igual a "501", é para consultar pelos RAs
				// Reiterados.
				if(Short.valueOf(filtroRA.getCodigoSituacao()).equals(Short.valueOf("501"))){
					consulta.append(" ra.codigoSituacao = :idSituacao AND ra.quantidadeReiteracao IS NOT NULL AND ");
					parameters.put("idSituacao", RegistroAtendimento.SITUACAO_PENDENTE);
					informouParametro = true;
				}else{
					consulta.append(" ra.codigoSituacao = :idSituacao AND ");
					parameters.put("idSituacao", filtroRA.getCodigoSituacao());
					informouParametro = true;
				}
			}

			// Tipo de Solicitação
			if(filtroRA.getColecaoTipoSolicitacao() != null && !filtroRA.getColecaoTipoSolicitacao().isEmpty()){

				if(!parameters.isEmpty()){
					consulta.append(" st.id in (:idSolicitacaoTipo) AND ");
				}else{
					consulta.append(" st.id in (:idSolicitacaoTipo) AND ");
				}

				parameters.put("idSolicitacaoTipo", filtroRA.getColecaoTipoSolicitacao());
				informouParametro = true;
			}

			// Especificação
			if(filtroRA.getColecaoTipoSolicitacaoEspecificacao() != null && !filtroRA.getColecaoTipoSolicitacaoEspecificacao().isEmpty()){

				if(!parameters.isEmpty()){
					consulta.append(" ste.id in (:idSolicitacaoTipoEspecificacao) AND ");
				}else{
					consulta.append(" ste.id in (:idSolicitacaoTipoEspecificacao) AND ");
				}

				parameters.put("idSolicitacaoTipoEspecificacao", filtroRA.getColecaoTipoSolicitacaoEspecificacao());
				informouParametro = true;
			}

			// Período de Atendimento
			if(filtroRA.getDataAtendimentoInicial() != null && filtroRA.getDataAtendimentoFinal() != null){

				if(!parameters.isEmpty()){
					consulta.append(" ra.registroAtendimento BETWEEN (:dataAtendimentoInicial) AND (:dataAtendimentoFinal) AND ");
				}else{
					consulta.append(" ra.registroAtendimento BETWEEN (:dataAtendimentoInicial) AND (:dataAtendimentoFinal) AND ");
				}

				parameters.put("dataAtendimentoInicial", filtroRA.getDataAtendimentoInicial());
				parameters.put("dataAtendimentoFinal", filtroRA.getDataAtendimentoFinal());
				informouParametro = true;
			}

			// Período de Encerramento
			if(filtroRA.getDataEncerramentoInicial() != null && filtroRA.getDataEncerramentoFinal() != null){

				if(!parameters.isEmpty()){
					consulta.append(" ra.dataEncerramento BETWEEN (:dataEncerramentoInicial) AND (:dataEncerramentoFinal) AND ");
				}else{
					consulta.append(" ra.dataEncerramento BETWEEN (:dataEncerramentoInicial) AND (:dataEncerramentoFinal) AND ");
				}

				parameters.put("dataEncerramentoInicial", filtroRA.getDataEncerramentoInicial());
				parameters.put("dataEncerramentoFinal", filtroRA.getDataEncerramentoFinal());
				informouParametro = true;
			}

			// Unidade de Atendimento
			if(filtroRA.getColecaoUnidadeAtendimento() != null && !filtroRA.getColecaoUnidadeAtendimento().isEmpty()){

				String idsUnidades = "";
				Object[] unidadesFiltro = filtroRA.getColecaoUnidadeAtendimento().toArray();

				for(int i = 0; i < unidadesFiltro.length; i++){
					if(i == 0){
						idsUnidades += " " + unidadesFiltro[i].toString();
					}else{
						idsUnidades += ", " + unidadesFiltro[i].toString();
					}
				}

				if(!parameters.isEmpty()){
					consulta.append(" rau.unidadeOrganizacional.id in (" + idsUnidades + ") AND ");
				}else{
					consulta.append(" rau.unidadeOrganizacional.id in (" + idsUnidades + ") AND ");
				}

				informouParametro = true;
			}

			// Unidade Atual
			if(filtroRA.getColecaoUnidadeAtual() != null && !filtroRA.getColecaoUnidadeAtual().isEmpty()){

				String idsUnidades = "";
				Object[] unidadesFiltro = filtroRA.getColecaoUnidadeAtual().toArray();

				for(int i = 0; i < unidadesFiltro.length; i++){
					if(i == 0){
						idsUnidades += " " + unidadesFiltro[i].toString();
					}else{
						idsUnidades += ", " + unidadesFiltro[i].toString();
					}
				}

				if(!parameters.isEmpty()){
					consulta.append(" tram.unidadeOrganizacionalDestino.id in (" + idsUnidades + ") AND ");
				}else{
					consulta.append(" tram.unidadeOrganizacionalDestino.id in (" + idsUnidades + ") AND ");
				}

				consulta.append(" tram.dataTramite in (select max(tr.dataTramite) from Tramite tr where tr.registroAtendimento.id = ra.id) AND ");
				informouParametro = true;
			}

			// Unidade Superior ou Chefia
			if(filtroRA.getUnidadeSuperior() != null){

				String idsUnidades = "";
				Object[] unidadesFiltro = filtroRA.getColecaoUnidadesSubordinadas().toArray();

				for(int i = 0; i < unidadesFiltro.length; i++){
					if(i == 0){
						idsUnidades += " " + unidadesFiltro[i].toString();
					}else{
						idsUnidades += ", " + unidadesFiltro[i].toString();
					}
				}

				if(!parameters.isEmpty()){
					consulta.append(" tram.unidadeOrganizacionalDestino.id in (" + idsUnidades + ") AND ");
				}else{
					consulta.append(" tram.unidadeOrganizacionalDestino.id in (" + idsUnidades + ") AND ");
				}

				consulta.append(" tram.dataTramite in (select max(tr.dataTramite) from Tramite tr where tr.registroAtendimento.id = ra.id) AND ");

				informouParametro = true;
			}

			// retira o " and " q fica sobrando no final da query
			StringBuffer queryString = new StringBuffer(consulta.substring(0, consulta.length() - 4));

			queryString.append(" GROUP BY uni.id, uni.descricao, usuario.nomeUsuario, usuario.id, rau.ultimaAlteracao, ra.registroAtendimento ");
			queryString.append(" ORDER BY uni.id, uni.descricao, usuario.nomeUsuario, usuario.id, rau.ultimaAlteracao, ra.registroAtendimento ");

			query = session.createQuery(queryString.toString());

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();

			while(iterMap.hasNext()){

				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Set){
					Set setList = (HashSet) parameters.get(key);
					query.setParameterList(key, setList);
				}else if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof Date){
					Date data = (Date) parameters.get(key);
					query.setTimestamp(key, data);
				}else{
					query.setParameter(key, parameters.get(key));
				}
			}

			retorno = query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisar Trâmite por Especificação
	 * 
	 * @author Hebert Falcão
	 * @date 06/04/2011
	 */
	public Collection pesquisarTramiteEspecificacao(EspecificacaoTramite filtro) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		Collection retorno = null;

		StringBuffer consulta = new StringBuffer("");
		Query query = null;
		Map parameters = new HashMap();

		try{
			consulta.append(" SELECT estr.id ");
			consulta.append(" FROM EspecificacaoTramite estr ");
			consulta.append(" LEFT JOIN estr.solicitacaoTipoEspecificacao step ");
			consulta.append(" LEFT JOIN estr.localidade loca ");
			consulta.append(" LEFT JOIN estr.setorComercial stcm ");
			consulta.append(" LEFT JOIN estr.bairro bair ");
			consulta.append(" LEFT JOIN estr.sistemaAbastecimento sabs ");
			consulta.append(" LEFT JOIN estr.distritoOperacional diop ");
			consulta.append(" LEFT JOIN estr.zonaAbastecimento zabs ");
			consulta.append(" LEFT JOIN estr.setorAbastecimento stab ");
			consulta.append(" LEFT JOIN estr.sistemaEsgoto sesg ");
			consulta.append(" LEFT JOIN estr.subsistemaEsgoto sses ");
			consulta.append(" LEFT JOIN estr.bacia baci ");
			consulta.append(" LEFT JOIN estr.subBacia sbac ");
			consulta.append(" LEFT JOIN estr.unidadeOrganizacionalOrigem unido ");
			consulta.append(" LEFT JOIN estr.unidadeOrganizacionalDestino unidd ");
			consulta.append(" WHERE ");

			// Id
			Integer id = filtro.getId();

			if(id != null){
				parameters.put("id", id);

				consulta.append(" estr.id != :id AND ");
			}

			// Solicitação Tipo Especificação
			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = filtro.getSolicitacaoTipoEspecificacao();

			if(solicitacaoTipoEspecificacao != null){
				Integer idSolicitacaoTipoEspecificacao = solicitacaoTipoEspecificacao.getId();
				parameters.put("idSolicitacaoTipoEspecificacao", idSolicitacaoTipoEspecificacao);

				consulta.append(" step.id = :idSolicitacaoTipoEspecificacao AND ");
			}else{
				consulta.append(" step.id IS NULL AND ");
			}

			// Localidade
			Localidade localidade = filtro.getLocalidade();

			if(localidade != null){
				Integer idLocalidade = localidade.getId();
				parameters.put("idLocalidade", idLocalidade);

				consulta.append(" loca.id = :idLocalidade AND ");
			}else{
				consulta.append(" loca.id IS NULL AND ");
			}

			// Setor Comercial
			SetorComercial setorComercial = filtro.getSetorComercial();

			if(setorComercial != null){
				Integer idSetorComercial = setorComercial.getId();
				parameters.put("idSetorComercial", idSetorComercial);

				consulta.append(" stcm.id = :idSetorComercial AND ");
			}else{
				consulta.append(" stcm.id IS NULL AND ");
			}

			// Bairro
			Bairro bairro = filtro.getBairro();

			if(bairro != null){
				Integer idBairro = bairro.getId();
				parameters.put("idBairro", idBairro);

				consulta.append(" bair.id = :idBairro AND ");
			}else{
				consulta.append(" bair.id IS NULL AND ");
			}

			// Sistema de Abastecimento
			SistemaAbastecimento sistemaAbastecimento = filtro.getSistemaAbastecimento();

			if(sistemaAbastecimento != null){
				Integer idSistemaAbastecimento = sistemaAbastecimento.getId();
				parameters.put("idSistemaAbastecimento", idSistemaAbastecimento);

				consulta.append(" sabs.id = :idSistemaAbastecimento AND ");
			}else{
				consulta.append(" sabs.id IS NULL AND ");
			}

			// Distrito Operacional
			DistritoOperacional distritoOperacional = filtro.getDistritoOperacional();

			if(distritoOperacional != null){
				Integer idDistritoOperacional = distritoOperacional.getId();
				parameters.put("idDistritoOperacional", idDistritoOperacional);

				consulta.append(" diop.id = :idDistritoOperacional AND ");
			}else{
				consulta.append(" diop.id IS NULL AND ");
			}

			// Zona de Abastecimento
			ZonaAbastecimento zonaAbastecimento = filtro.getZonaAbastecimento();

			if(zonaAbastecimento != null){
				Integer idZonaAbastecimento = zonaAbastecimento.getId();
				parameters.put("idZonaAbastecimento", idZonaAbastecimento);

				consulta.append(" zabs.id = :idZonaAbastecimento AND ");
			}else{
				consulta.append(" zabs.id IS NULL AND");
			}

			// Setor de Abastecimento
			SetorAbastecimento setorAbastecimento = filtro.getSetorAbastecimento();

			if(setorAbastecimento != null){
				Integer idSetorAbastecimento = setorAbastecimento.getId();
				parameters.put("idSetorAbastecimento", idSetorAbastecimento);

				consulta.append(" stab.id = :idSetorAbastecimento AND ");
			}else{
				consulta.append(" stab.id IS NULL AND ");
			}

			// Sistema de Esgoto
			SistemaEsgoto sistemaEsgoto = filtro.getSistemaEsgoto();

			if(sistemaEsgoto != null){
				Integer idSistemaEsgoto = sistemaEsgoto.getId();
				parameters.put("idSistemaEsgoto", idSistemaEsgoto);

				consulta.append(" sesg.id = :idSistemaEsgoto AND ");
			}else{
				consulta.append(" sesg.id IS NULL AND ");
			}

			// Subsistema de Esgoto
			SubsistemaEsgoto subsistemaEsgoto = filtro.getSubsistemaEsgoto();

			if(subsistemaEsgoto != null){
				Integer idSubsistemaEsgoto = subsistemaEsgoto.getId();
				parameters.put("idSubsistemaEsgoto", idSubsistemaEsgoto);

				consulta.append(" sses.id = :idSubsistemaEsgoto AND ");
			}else{
				consulta.append(" sses.id IS NULL AND ");
			}

			// Bacia
			Bacia bacia = filtro.getBacia();

			if(bacia != null){
				Integer idBacia = bacia.getId();
				parameters.put("idBacia", idBacia);

				consulta.append(" baci.id = :idBacia AND ");
			}else{
				consulta.append(" baci.id IS NULL AND ");
			}

			// SubBacia
			SubBacia subBacia = filtro.getSubBacia();

			if(subBacia != null){
				Integer idSubBacia = subBacia.getId();
				parameters.put("idSubBacia", idSubBacia);

				consulta.append(" sbac.id = :idSubBacia AND ");
			}else{
				consulta.append(" sbac.id IS NULL AND ");
			}

			// Unidade Organizacional de Origem
			UnidadeOrganizacional unidadeOrganizacionalOrigem = filtro.getUnidadeOrganizacionalOrigem();

			if(unidadeOrganizacionalOrigem != null){
				Integer idUnidadeOrganizacionalOrigem = unidadeOrganizacionalOrigem.getId();
				parameters.put("idUnidadeOrganizacionalOrigem", idUnidadeOrganizacionalOrigem);

				consulta.append(" unido.id = :idUnidadeOrganizacionalOrigem AND ");
			}else{
				consulta.append(" unido.id IS NULL AND ");
			}

			// Unidade Organizacional de Destino
			UnidadeOrganizacional unidadeOrganizacionalDestino = filtro.getUnidadeOrganizacionalDestino();

			if(unidadeOrganizacionalDestino != null){
				Integer idUnidadeOrganizacionalDestino = unidadeOrganizacionalDestino.getId();
				parameters.put("idUnidadeOrganizacionalDestino", idUnidadeOrganizacionalDestino);

				consulta.append(" unidd.id = :idUnidadeOrganizacionalDestino AND ");
			}else{
				consulta.append(" unidd.id IS NULL AND ");
			}

			// retira o " and " q fica sobrando no final da query
			StringBuffer queryString = new StringBuffer(consulta.substring(0, consulta.length() - 4));

			query = session.createQuery(queryString.toString());

			Set<String> keys = parameters.keySet();

			for(String key : keys){
				query.setParameter(key, parameters.get(key));
			}

			retorno = query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisar Unidades de Destino por EspecificacaoTramite
	 * 
	 * @author isilva
	 * @date 13/04/2011
	 */
	public Collection pesquisarUnidadeDestinoPorEspecificacao(EspecificacaoTramite especificacaoTramite,
					boolean checarIndicadorPrimeiroTramite) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");
		Query query = null;
		Map parameters = new HashMap();

		try{

			String sCriterio = "";

			consulta.append("select especTramite.unidadeOrganizacionalDestino ");
			consulta.append("from EspecificacaoTramite especTramite ");
			consulta.append(" left join especTramite.unidadeOrganizacionalDestino unidadeOrganizacionalDestino ");
			consulta.append(" left join especTramite.unidadeOrganizacionalOrigem unidadeOrganizacionalOrigem ");
			consulta.append(" left join especTramite.solicitacaoTipoEspecificacao solicitacaoTipoEspecificacao ");
			consulta.append("where ");

			if(especificacaoTramite.getSolicitacaoTipoEspecificacao() != null
							&& especificacaoTramite.getSolicitacaoTipoEspecificacao().getId() != null
							&& especificacaoTramite.getSolicitacaoTipoEspecificacao().getId() != ConstantesSistema.NUMERO_NAO_INFORMADO){
				consulta.append("solicitacaoTipoEspecificacao.id = :idSolicitacaoTipoEspecificacao");
				sCriterio = " and ";
				parameters.put("idSolicitacaoTipoEspecificacao", especificacaoTramite.getSolicitacaoTipoEspecificacao().getId());
			}else{
				consulta.append("solicitacaoTipoEspecificacao is null ");
				sCriterio = " and ";
			}

			if((especificacaoTramite.getLocalidade() != null && especificacaoTramite.getLocalidade().getId() != null && especificacaoTramite
							.getLocalidade().getId() != ConstantesSistema.NUMERO_NAO_INFORMADO)
							&& (especificacaoTramite.getSetorComercial() != null
											&& especificacaoTramite.getSetorComercial().getId() != null && especificacaoTramite
											.getSetorComercial().getId() != ConstantesSistema.NUMERO_NAO_INFORMADO)){

				// Informou Localidade e Setor Comercial

				consulta.append(sCriterio).append("(especTramite.localidade.id = :idLocalidade or especTramite.localidade.id is null) ");
				parameters.put("idLocalidade", especificacaoTramite.getLocalidade().getId());
				sCriterio = " and ";

				consulta.append(sCriterio).append(
								"(especTramite.setorComercial.id = :idSetorComercial or especTramite.setorComercial.id is null) ");
				parameters.put("idSetorComercial", especificacaoTramite.getSetorComercial().getId());

			}else if(especificacaoTramite.getLocalidade() != null && especificacaoTramite.getLocalidade().getId() != null
							&& especificacaoTramite.getLocalidade().getId() != ConstantesSistema.NUMERO_NAO_INFORMADO){

				// Informou só Localidade

				consulta.append(sCriterio).append("(especTramite.localidade.id = :idLocalidade or especTramite.localidade.id is null) ");
				parameters.put("idLocalidade", especificacaoTramite.getLocalidade().getId());
				sCriterio = " and ";

				consulta.append(sCriterio).append("especTramite.setorComercial is null ").append(sCriterio);

			}

			if(especificacaoTramite.getBairro() != null && especificacaoTramite.getBairro().getId() != null
							&& especificacaoTramite.getBairro().getId() != ConstantesSistema.NUMERO_NAO_INFORMADO
							&& especificacaoTramite.getBairro().getMunicipio() != null
							&& especificacaoTramite.getBairro().getMunicipio().getId() != null
							&& especificacaoTramite.getBairro().getMunicipio().getId() != ConstantesSistema.NUMERO_NAO_INFORMADO){

				// Só considera pra pesquisa se informado Bairro e Município

				consulta.append(sCriterio).append("(especTramite.bairro.id = :idBairro or especTramite.bairro.id is null) ");
				parameters.put("idBairro", especificacaoTramite.getBairro().getId());
				sCriterio = " and ";

				consulta.append(sCriterio).append(
								"(especTramite.bairro.municipio.id = :idMunicipio or especTramite.bairro.municipio.id is null) ");
				parameters.put("idMunicipio", especificacaoTramite.getBairro().getMunicipio().getId());
			}

			if(especificacaoTramite.getUnidadeOrganizacionalOrigem() != null
							&& especificacaoTramite.getUnidadeOrganizacionalOrigem().getId() != null
							&& especificacaoTramite.getUnidadeOrganizacionalOrigem().getId() != ConstantesSistema.NUMERO_NAO_INFORMADO){
				consulta.append(sCriterio)
								.append("(unidadeOrganizacionalOrigem.id = :idUnidadeOrganizacionalOrigem or especTramite.unidadeOrganizacionalOrigem.id is null) ");
				parameters.put("idUnidadeOrganizacionalOrigem", especificacaoTramite.getUnidadeOrganizacionalOrigem().getId());
				sCriterio = " and ";
			}

			consulta.append(sCriterio).append("unidadeOrganizacionalDestino.indicadorUso = :indicadorUsoUunidadeOrganizacionalDestino ");
			parameters.put("indicadorUsoUunidadeOrganizacionalDestino", ConstantesSistema.INDICADOR_USO_ATIVO);
			sCriterio = " and ";

			consulta.append(sCriterio).append(
							"unidadeOrganizacionalDestino.indicadorTramite = :indicadorTramiteUunidadeOrganizacionalDestino ");
			parameters.put("indicadorTramiteUunidadeOrganizacionalDestino", ConstantesSistema.SIM);
			sCriterio = " and ";

			if(especificacaoTramite.getIndicadorUso() != null
							&& (especificacaoTramite.getIndicadorUso().intValue() == ConstantesSistema.INDICADOR_USO_DESATIVO.intValue() || especificacaoTramite
											.getIndicadorUso().intValue() == ConstantesSistema.INDICADOR_USO_ATIVO.intValue())){
				consulta.append(sCriterio).append("especTramite.indicadorUso = :indicadorUso   ");
				parameters.put("indicadorUso", especificacaoTramite.getIndicadorUso());
				sCriterio = " and ";
			}

			if(checarIndicadorPrimeiroTramite){
				consulta.append(sCriterio).append(" especTramite.indicadorPrimeiroTramite = :indicadorPrimeiroTramite   ");
				parameters.put("indicadorPrimeiroTramite", ConstantesSistema.SIM);
				sCriterio = " and ";
			}

			if(especificacaoTramite.getUnidadeOrganizacionalDestino() != null
							&& especificacaoTramite.getUnidadeOrganizacionalDestino().getId() != null
							&& especificacaoTramite.getUnidadeOrganizacionalDestino().getId() != ConstantesSistema.NUMERO_NAO_INFORMADO){
				consulta.append(sCriterio).append(" unidadeOrganizacionalDestino.id = :idUnidadeOrganizacionalDestino ");
				parameters.put("idUnidadeOrganizacionalDestino", especificacaoTramite.getUnidadeOrganizacionalDestino().getId());
				sCriterio = " and ";
			}

			StringBuffer queryString = new StringBuffer(consulta);

			queryString.append(" order by  ");
			queryString.append("especTramite.localidade.id, ");
			queryString.append("especTramite.setorComercial.id, ");
			queryString.append("especTramite.bairro.id, ");
			queryString.append("especTramite.unidadeOrganizacionalOrigem.id ");

			query = session.createQuery(queryString.toString());

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();

			while(iterMap.hasNext()){

				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof String){
					String string = (String) parameters.get(key);
					query.setParameter(key, string);
				}else if(parameters.get(key) instanceof Integer){
					Integer integer = (Integer) parameters.get(key);
					query.setParameter(key, integer);
				}else if(parameters.get(key) instanceof Short){
					Short parametroShort = (Short) parameters.get(key);
					query.setParameter(key, parametroShort);
				}else{
					query.setParameter(key, parameters.get(key));
				}
			}

			retorno = query.list();

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisa as Dependências da SolicitacaoTipo (SolicitacaoTipoEspecificacao,
	 * EspecificacaoServicoTipo)
	 * 
	 * @author isilva
	 * @date 23/02/2011
	 * @param idSolicitacaoTipo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarDependenciasSolicitacaoTipo(Integer idSolicitacaoTipo) throws ErroRepositorioException{

		Collection<Object[]> retornoConsulta = new ArrayList();
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{
			consulta.append("select ste, est ");
			consulta.append("from SolicitacaoTipoEspecificacao ste ");
			consulta.append("left join ste.especificacaoServicoTipos est ");
			consulta.append("where ste.solicitacaoTipo.id = :idSolicitacaoTipo ");
			consulta.append("order by ste.solicitacaoTipo.id, ste.id asc ");

			retornoConsulta = session.createQuery(consulta.toString()).setInteger("idSolicitacaoTipo", idSolicitacaoTipo).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * @author Saulo Lima
	 * @date 27/06/2012
	 * @param Integer
	 *            idRA
	 * @return Object[] idLocalidade, idSetorComercial, idBairro
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosLocalizacaoRegistroAtendimento(Integer idRA) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Object[] retorno = null;

		try{
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT a.loca_id, a.stcm_id, b.bair_id ");
			sql.append("FROM registro_atendimento a ");
			sql.append("INNER JOIN logradouro_bairro b on a.lgbr_id = b.lgbr_id ");
			sql.append("WHERE a.rgat_id = :idRA");

			SQLQuery query = session.createSQLQuery(sql.toString());
			query.addScalar("loca_id", Hibernate.INTEGER);
			query.addScalar("stcm_id", Hibernate.INTEGER);
			query.addScalar("bair_id", Hibernate.INTEGER);
			query.setInteger("idRA", idRA);

			Collection colecaoConsulta = query.list();
			retorno = (Object[]) Util.retonarObjetoDeColecao(colecaoConsulta);
		}catch(Exception e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro durante a consulta");
		}finally{

			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Cosulta o total de RA que atendam ao filtro informado.
	 * 
	 * @param parametro
	 *            {@link RelatorioEstatisticoRegistroAtendimentoHelper}
	 * @return
	 */
	public int constularQuantidadeRA(RelatorioEstatisticoRegistroAtendimentoHelper registroAtendimentoHelper, boolean apenasUnidadeSuperior)
					throws ErroRepositorioException{

		StringBuilder builder = new StringBuilder(getConsultaEstatisticaRAQueryBase(registroAtendimentoHelper, apenasUnidadeSuperior));
		builder.insert(0, "SELECT SUM(qtd_ra) qtd_ra_total FROM (");
		builder.append(") TAB1 ");
		String sqlString = builder.toString();
		Session session = HibernateUtil.getSession();
		Integer retorno = 0;
		try{
			SQLQuery query = session.createSQLQuery(sqlString);
			query.addScalar("qtd_ra_total", Hibernate.INTEGER);
			Number uniqueResult = (Number) query.uniqueResult();
			retorno = uniqueResult == null ? 0 : uniqueResult.intValue();
		}catch(Exception e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro durante a consulta");
		}finally{

			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	private String getConsultaEstatisticaRAQueryBase(RelatorioEstatisticoRegistroAtendimentoHelper registroAtendimentoHelper,
					boolean apenasUnidadeSuperior){

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT ");
		builder.append("	UND_SUP.UNID_ID id_unidade_superior, ");
		builder.append("	UND_SUP.UNID_DSUNIDADE unidade_superior,  ");

		if(apenasUnidadeSuperior){
			builder.append("	UND_SUP.UNID_ID id_unidade_atendimento, ");
			builder.append("	UND_SUP.UNID_DSUNIDADE unidade_atendimento,  ");
		}else{
			builder.append("	UND_ATEND.UNID_ID id_unidade_atendimento, ");
			builder.append("	UND_ATEND.UNID_DSUNIDADE unidade_atendimento, ");
		}

		builder.append("	SOL_TIP.SOTP_ID id_solicitacao, ");
		builder.append("	SOL_TIP.SOTP_DSSOLICITACAOTIPO solicitacao, ");
		builder.append("	SOL_TIP_ESPC.STEP_ID id_especificacao, ");
		builder.append("	SOL_TIP_ESPC.STEP_DSSOLICITACAOTIPOESPECIFI especificacao, ");
		builder.append("	COUNT (RA.RGAT_ID) qtd_ra ");
		builder.append(" FROM ");
		builder.append("	REGISTRO_ATENDIMENTO RA ");
		builder.append("	INNER JOIN REGISTRO_ATENDIMENTO_UNIDADE RA_UND ON RA.RGAT_ID = RA_UND.RGAT_ID ");
		builder.append("	INNER JOIN SOLICITACAO_TIPO_ESPECIFICACAO SOL_TIP_ESPC ON SOL_TIP_ESPC.STEP_ID = RA.STEP_ID ");
		builder.append("	INNER JOIN SOLICITACAO_TIPO SOL_TIP ON SOL_TIP_ESPC.SOTP_ID = SOL_TIP.SOTP_ID ");
		builder.append("	LEFT JOIN (SELECT TR.TRAM_ID, TR.RGAT_ID, TR.UNID_IDDESTINO FROM TRAMITE TR INNER JOIN (SELECT t.RGAT_ID,MAX(t.TRAM_TMTRAMITE) TRAM_TMTRAMITE FROM TRAMITE t GROUP BY t.RGAT_ID) t ON (tr.RGAT_ID = t.RGAT_ID AND tr.TRAM_TMTRAMITE = t.TRAM_TMTRAMITE)) TRMT ON RA.RGAT_ID = TRMT.RGAT_ID ");
		builder.append("	INNER JOIN UNIDADE_ORGANIZACIONAL UND_ATEND ON TRMT.UNID_IDDESTINO = UND_ATEND.UNID_ID ");
		builder.append("	LEFT JOIN UNIDADE_ORGANIZACIONAL UND_SUP on UND_ATEND.UNID_IDSUPERIOR = UND_SUP.UNID_ID ");
		builder.append(" WHERE  ");

		if(registroAtendimentoHelper.getSituacao() != null
						&& registroAtendimentoHelper.getSituacao().equals(registroAtendimentoHelper.SITUACAO_PENDENTES)){
			builder.append(" RA.RGAT_CDSITUACAO = 1 AND");
		}else if(registroAtendimentoHelper.getSituacao() != null
						&& registroAtendimentoHelper.getSituacao().equals(registroAtendimentoHelper.SITUACAO_ENCERRADOS)){
			builder.append(" RA.RGAT_CDSITUACAO = 2 AND");
		}else if(registroAtendimentoHelper.getSituacao() != null
						&& registroAtendimentoHelper.getSituacao().equals(registroAtendimentoHelper.SITUACAO_REITERADOS)){
			builder.append(" RA.RGAT_CDSITUACAO = 1 and RA.RGAT_QTREITERACOES IS NOT NULL AND");
		}

		// Filtro de periodo de atendimento
		builder.append(" RA.RGAT_TMREGISTROATENDIMENTO BETWEEN to_date('");
		builder.append(Util.formatarDataAAAAMMDD(registroAtendimentoHelper.getPeriodoAtendimentoInicial()) + " 00:00:00");
		builder.append("', 'yyyymmdd HH24:MI:SS') AND to_date('");
		builder.append(Util.formatarDataAAAAMMDD(registroAtendimentoHelper.getPeriodoAtendimentoFinal()) + " 23:59:59");
		builder.append("', 'yyyymmdd HH24:MI:SS') ");
		// Filtro de periodo de atendimento
		if(registroAtendimentoHelper.getUnidadeSuperior() != null){
			builder.append(" AND UND_SUP.UNID_ID = ");
			builder.append(registroAtendimentoHelper.getUnidadeSuperior().getId());
		}
		if(registroAtendimentoHelper.getUnidadeAtendimento() != null){
			builder.append(" AND UND_ATEND.UNID_ID =");
			builder.append(registroAtendimentoHelper.getUnidadeAtendimento().getId());
		}
		if(registroAtendimentoHelper.getUsuario() != null){
			builder.append(" AND RA_UND.USUR_ID =");
			builder.append(registroAtendimentoHelper.getUsuario().getId());
		}
		if(!Util.isVazioOrNulo(registroAtendimentoHelper.getTiposSolicitacao())){
			builder.append(" AND SOL_TIP.SOTP_ID in (");
			for(SolicitacaoTipo tipoSolicitacao : registroAtendimentoHelper.getTiposSolicitacao()){
				builder.append(tipoSolicitacao.getId());
				builder.append(",");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(")");
		}
		if(!Util.isVazioOrNulo(registroAtendimentoHelper.getEspecificacoes())){
			builder.append(" AND SOL_TIP_ESPC.STEP_ID in (");
			for(SolicitacaoTipoEspecificacao especificacao : registroAtendimentoHelper.getEspecificacoes()){
				builder.append(especificacao.getId());
				builder.append(",");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(")");
		}
		if(registroAtendimentoHelper.getUnidadeAtual() != null){
			builder.append(" AND TRMT.UNID_IDDESTINO = ");
			builder.append(registroAtendimentoHelper.getUnidadeAtual().getId());
		}
		builder.append("GROUP BY  ");
		builder.append("	UND_SUP.UNID_ID, ");
		builder.append("	UND_SUP.UNID_DSUNIDADE,  ");

		if(apenasUnidadeSuperior){
			builder.append("	'0', ");
			builder.append("	'0',  ");
		}else{
			builder.append("	UND_ATEND.UNID_ID, ");
			builder.append("	UND_ATEND.UNID_DSUNIDADE, ");
		}

		builder.append("	SOL_TIP.SOTP_ID, ");
		builder.append("	SOL_TIP.SOTP_DSSOLICITACAOTIPO,  ");
		builder.append("	SOL_TIP_ESPC.STEP_ID, ");
		builder.append("	SOL_TIP_ESPC.STEP_DSSOLICITACAOTIPOESPECIFI ");
		builder.append("ORDER BY ");
		// builder.append("	unidade_superior, ");
		builder.append("	unidade_atendimento, ");
		builder.append("	solicitacao, ");
		builder.append("	especificacao ");

		String sqlString = builder.toString();
		return sqlString;
	}

	/**
	 * Consulta os dados estatisticos de RA filtrados pelo
	 * {@link RelatorioEstatisticoRegistroAtendimentoHelper}
	 * 
	 * @param relatorioEstatisticoRegistroAtendimentoHelper
	 * @return {@link List} de {@link RelatorioEstatisticoRegistroAtendimentoBean}
	 */
	public Collection<Object[]> consultarDadosEstatisticosRegistroAtendimento(
					RelatorioEstatisticoRegistroAtendimentoHelper registroAtendimentoHelper, boolean apenasUnidadeSuperior)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection<Object[]> resultado;
		try{
			String sqlStringBase = getConsultaEstatisticaRAQueryBase(registroAtendimentoHelper, apenasUnidadeSuperior);
			SQLQuery query = session.createSQLQuery(sqlStringBase);
			query.addScalar("id_unidade_superior", Hibernate.INTEGER);
			query.addScalar("unidade_superior", Hibernate.STRING);
			query.addScalar("id_unidade_atendimento", Hibernate.INTEGER);
			query.addScalar("unidade_atendimento", Hibernate.STRING);
			query.addScalar("id_solicitacao", Hibernate.INTEGER);
			query.addScalar("solicitacao", Hibernate.STRING);
			query.addScalar("id_especificacao", Hibernate.INTEGER);
			query.addScalar("especificacao", Hibernate.STRING);
			query.addScalar("qtd_ra", Hibernate.INTEGER);
			resultado = query.list();
		}catch(Exception e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro durante a consulta");
		}finally{

			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return resultado;
	}

	/**
	 * [UC0145] Inserir Conta
	 * [SB0008] - Verificar Exigência RA de Inclusão de Conta Dados Cadastrais Divergentes do Imóvel
	 * 
	 * @author Ado Rocha
	 * @date 31/10/2013
	 * @param idImovel
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean verificaExistenciaRAInclusaoContaDadosCadastraisDivergentesImovel(Integer idImovel) throws ErroRepositorioException{

		Collection resultado = null;

		boolean retorno = false;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT * FROM REGISTRO_ATENDIMENTO RGAT INNER JOIN ESPECIFICACAO_TIPO_VALIDACAO ESTV ON RGAT.STEP_ID = ESTV.STEP_ID WHERE RGAT.IMOV_ID = "
							+ idImovel + " AND ESTV.ESTV_CDCONSTANTE = 'C'";

			resultado = session.createSQLQuery(consulta).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		if(resultado != null && !resultado.isEmpty()){
			retorno = true;
		}

		return retorno;
	}

	public Collection retornarColecaoOrdemServicoImovel(Integer idImovel)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		
		String parametroIdsSubgruposVerificacaoOsAndamento = "";

		try{

			parametroIdsSubgruposVerificacaoOsAndamento = ParametroAtendimentoPublico.P_IDS_SUBGRUPOS_VERIFICACAO_OS_ANDAMENTO.executar();

		}catch(ControladorException e1){
			e1.printStackTrace();
		}

		String consulta = "";

		try{
			consulta = "select * from ordem_servico o " + "inner join servico_tipo s on o.svtp_id = s.svtp_id " + "where o.imov_id = "
							+ idImovel + " and s.stsg_id in (" + parametroIdsSubgruposVerificacaoOsAndamento + ") "
							+ "and o.orse_cdsituacao in (1, 3)";

			retorno = (Collection) session.createSQLQuery(consulta).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * @param dadosAcquaGis
	 * @throws ErroRepositorioException
	 */

	public void atualizarCoordenadasGis(DadosAcquaGis dadosAcquaGis) throws ErroRepositorioException{

		String consulta = "";

		Session session = HibernateUtil.getSession();

		try{

			consulta = "UPDATE gcom.atendimentopublico.registroatendimento.RegistroAtendimento SET "
							+ "rgat_nncoordenadanorte = :nncoordenadanorte, rgat_nncoordenadaleste = :nncoordenadaleste "
							+ "WHERE imov_id = :idImovel ";

			session.createQuery(consulta).setBigDecimal("nncoordenadanorte", Util.formatarMoedaRealparaBigDecimal(dadosAcquaGis.getNumeroCoordenadaNorte(), 8))
							.setBigDecimal("nncoordenadaleste",
											Util.formatarMoedaRealparaBigDecimal(dadosAcquaGis.getNumeroCoordenadaLeste(), 8))
							.setInteger("idImovel", Util.converterStringParaInteger(dadosAcquaGis.getIdImovel())).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Consulta o total de RA que atendam ao filtro informado.
	 * 
	 * @param parametro
	 *            {@link RelatorioRAComProcessoAdmJudHelper}
	 * @return
	 */
	public int consultarQuantidadeRAComProcessoAdmJud(RelatorioRAComProcessoAdmJudHelper registroAtendimentoHelper,
					boolean apenasUnidadeSuperior)
					throws ErroRepositorioException{

		StringBuilder builder = new StringBuilder(getConsultaRAComProcessoAdmJudQueryBase(registroAtendimentoHelper, apenasUnidadeSuperior));
		builder.insert(0, "SELECT SUM(qtd_ra) qtd_ra_total FROM (");
		builder.append(") TAB1 ");
		String sqlString = builder.toString();
		Session session = HibernateUtil.getSession();
		Integer retorno = 0;
		try{
			SQLQuery query = session.createSQLQuery(sqlString);
			query.addScalar("qtd_ra_total", Hibernate.INTEGER);
			query.setTimestamp("periodoAtendimentoInicial",
							Util.formatarDataInicial(registroAtendimentoHelper.getPeriodoAtendimentoInicial()));
			query.setTimestamp("periodoAtendimentoFinal", Util.formatarDataFinal(registroAtendimentoHelper.getPeriodoAtendimentoFinal()));
			Number uniqueResult = (Number) query.uniqueResult();
			retorno = uniqueResult == null ? 0 : uniqueResult.intValue();
		}catch(Exception e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro durante a consulta");
		}finally{

			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Consulta Registro de Atendimento com Processo Adm Jud.
	 * 
	 * @param parametro
	 *            {@link RelatorioRAComProcessoAdmJudHelper}
	 * @return
	 */
	private String getConsultaRAComProcessoAdmJudQueryBase(RelatorioRAComProcessoAdmJudHelper registroAtendimentoHelper,
					boolean apenasUnidadeSuperior){

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT DISTINCT ");
		builder.append("	UND_SUP.UNID_ID id_unidade_superior, ");
		builder.append("	UND_SUP.UNID_DSUNIDADE unidade_superior,  ");

		if(apenasUnidadeSuperior){
			builder.append("	UND_SUP.UNID_ID id_unidade_atendimento, ");
			builder.append("	UND_SUP.UNID_DSUNIDADE unidade_atendimento,  ");
		}else{
			builder.append("	UND_ATEND.UNID_ID id_unidade_atendimento, ");
			builder.append("	UND_ATEND.UNID_DSUNIDADE unidade_atendimento, ");
		}

		builder.append("	SOL_TIP.SOTP_ID id_solicitacao, ");
		builder.append("	SOL_TIP.SOTP_DSSOLICITACAOTIPO solicitacao, ");
		builder.append("	SOL_TIP_ESPC.STEP_ID id_especificacao, ");
		builder.append("	SOL_TIP_ESPC.STEP_DSSOLICITACAOTIPOESPECIFI especificacao, ");
		builder.append("	RA.RGAT_NNPROCESSOAGENCIA numero_processo_agencia, ");
		builder.append("	RA.RGAT_ID numero_ra, ");
		builder.append("	RA.RGAT_CDSITUACAO situacao_ra, ");
		builder.append("	COUNT (RA.RGAT_ID) qtd_ra ");
		builder.append(" FROM ");
		builder.append("	REGISTRO_ATENDIMENTO RA ");
		builder.append("	INNER JOIN REGISTRO_ATENDIMENTO_UNIDADE RA_UND ON RA.RGAT_ID = RA_UND.RGAT_ID ");
		builder.append("	INNER JOIN UNIDADE_ORGANIZACIONAL UND_ATEND ON RA_UND.UNID_ID = UND_ATEND.UNID_ID ");
		builder.append("	LEFT JOIN UNIDADE_ORGANIZACIONAL UND_SUP on UND_ATEND.UNID_IDSUPERIOR = UND_SUP.UNID_ID ");
		builder.append("	INNER JOIN SOLICITACAO_TIPO_ESPECIFICACAO SOL_TIP_ESPC ON SOL_TIP_ESPC.STEP_ID = RA.STEP_ID ");
		builder.append("	INNER JOIN SOLICITACAO_TIPO SOL_TIP ON SOL_TIP_ESPC.SOTP_ID = SOL_TIP.SOTP_ID ");
		builder.append("	LEFT JOIN (SELECT TR.TRAM_ID, TR.RGAT_ID, TR.UNID_IDDESTINO FROM TRAMITE TR INNER JOIN (SELECT t.RGAT_ID,MAX(t.TRAM_TMTRAMITE) TRAM_TMTRAMITE FROM TRAMITE t GROUP BY t.RGAT_ID) t ON (tr.RGAT_ID = t.RGAT_ID AND tr.TRAM_TMTRAMITE = t.TRAM_TMTRAMITE)) TRMT ON RA.RGAT_ID = TRMT.RGAT_ID ");
		builder.append(" WHERE  ");

		builder.append(" RA.RGAT_ICPROCESSOADMJUD = 1 AND");

		if(registroAtendimentoHelper.getSituacao() != null
						&& registroAtendimentoHelper.getSituacao().equals(RelatorioRAComProcessoAdmJudHelper.SITUACAO_PENDENTES)){
			builder.append(" RA.RGAT_CDSITUACAO = 1 AND");
		}else if(registroAtendimentoHelper.getSituacao() != null
						&& registroAtendimentoHelper.getSituacao().equals(RelatorioRAComProcessoAdmJudHelper.SITUACAO_ENCERRADOS)){
			builder.append(" RA.RGAT_CDSITUACAO = 2 AND");
		}else if(registroAtendimentoHelper.getSituacao() != null
						&& registroAtendimentoHelper.getSituacao().equals(RelatorioRAComProcessoAdmJudHelper.SITUACAO_REITERADOS)){
			builder.append(" RA.RGAT_CDSITUACAO = 1 and RA.RGAT_QTREITERACOES IS NOT NULL AND");
		}

		builder.append("	RA_UND.ATTP_ID = " + AtendimentoRelacaoTipo.ABRIR_REGISTRAR);
		// Filtro de periodo de atendimento
		builder.append("	AND RA.RGAT_TMREGISTROATENDIMENTO BETWEEN :periodoAtendimentoInicial AND :periodoAtendimentoFinal ");
		// Filtro de periodo de atendimento
		if(registroAtendimentoHelper.getUnidadeSuperior() != null){
			builder.append(" AND UND_SUP.UNID_ID = ");
			builder.append(registroAtendimentoHelper.getUnidadeSuperior().getId());
		}
		if(registroAtendimentoHelper.getUnidadeAtendimento() != null){
			builder.append(" AND UND_ATEND.UNID_ID =");
			builder.append(registroAtendimentoHelper.getUnidadeAtendimento().getId());
		}
		if(registroAtendimentoHelper.getUsuario() != null){
			builder.append(" AND RA_UND.USUR_ID =");
			builder.append(registroAtendimentoHelper.getUsuario().getId());
		}
		if(!Util.isVazioOrNulo(registroAtendimentoHelper.getTiposSolicitacao())){
			builder.append(" AND SOL_TIP.SOTP_ID in (");
			for(SolicitacaoTipo tipoSolicitacao : registroAtendimentoHelper.getTiposSolicitacao()){
				builder.append(tipoSolicitacao.getId());
				builder.append(",");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(")");
		}
		if(!Util.isVazioOrNulo(registroAtendimentoHelper.getEspecificacoes())){
			builder.append(" AND SOL_TIP_ESPC.STEP_ID in (");
			for(SolicitacaoTipoEspecificacao especificacao : registroAtendimentoHelper.getEspecificacoes()){
				builder.append(especificacao.getId());
				builder.append(",");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(")");
		}
		if(registroAtendimentoHelper.getUnidadeAtual() != null){
			builder.append(" AND TRMT.UNID_IDDESTINO = ");
			builder.append(registroAtendimentoHelper.getUnidadeAtual().getId());
		}
		builder.append("GROUP BY  ");
		builder.append("	UND_SUP.UNID_ID, ");
		builder.append("	UND_SUP.UNID_DSUNIDADE,  ");

		if(apenasUnidadeSuperior){
			builder.append("	'0', ");
			builder.append("	'0',  ");
		}else{
			builder.append("	UND_ATEND.UNID_ID, ");
			builder.append("	UND_ATEND.UNID_DSUNIDADE, ");
		}

		builder.append("	SOL_TIP.SOTP_ID, ");
		builder.append("	SOL_TIP.SOTP_DSSOLICITACAOTIPO,  ");
		builder.append("	SOL_TIP_ESPC.STEP_ID, ");
		builder.append("	SOL_TIP_ESPC.STEP_DSSOLICITACAOTIPOESPECIFI, ");
		builder.append("	RA.RGAT_NNPROCESSOAGENCIA, ");
		builder.append("	RA.RGAT_ID, ");
		builder.append("	RA.RGAT_CDSITUACAO ");
		builder.append("ORDER BY ");
		builder.append("	unidade_atendimento, ");
		builder.append("	solicitacao, ");
		builder.append("	especificacao ");

		String sqlString = builder.toString();
		return sqlString;
	}


	/**
	 * Consulta os dados de RA filtrados pelo {@link RelatorioRAComProcessoAdmJudHelper}
	 * 
	 * @param relatorioRAComProcessoAdmJudHelper
	 * @return {@link List} de {@link RelatorioRAComProcessoAdmJudBean}
	 */
	public Collection<Object[]> consultarDadosRAComProcessoAdmJud(RelatorioRAComProcessoAdmJudHelper registroAtendimentoHelper,
					boolean apenasUnidadeSuperior)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection<Object[]> resultado;
		try{
			String sqlStringBase = getConsultaRAComProcessoAdmJudQueryBase(registroAtendimentoHelper, apenasUnidadeSuperior);
			SQLQuery query = session.createSQLQuery(sqlStringBase);
			query.addScalar("id_unidade_superior", Hibernate.INTEGER);
			query.addScalar("unidade_superior", Hibernate.STRING);
			query.addScalar("id_unidade_atendimento", Hibernate.INTEGER);
			query.addScalar("unidade_atendimento", Hibernate.STRING);
			query.addScalar("id_solicitacao", Hibernate.INTEGER);
			query.addScalar("solicitacao", Hibernate.STRING);
			query.addScalar("id_especificacao", Hibernate.INTEGER);
			query.addScalar("especificacao", Hibernate.STRING);
			query.addScalar("numero_processo_agencia", Hibernate.STRING);
			query.addScalar("numero_ra", Hibernate.INTEGER);
			query.addScalar("situacao_ra", Hibernate.INTEGER);
			query.addScalar("qtd_ra", Hibernate.INTEGER);
			query.setTimestamp("periodoAtendimentoInicial",
							Util.formatarDataInicial(registroAtendimentoHelper.getPeriodoAtendimentoInicial()));
			query.setTimestamp("periodoAtendimentoFinal", Util.formatarDataFinal(registroAtendimentoHelper.getPeriodoAtendimentoFinal()));
			resultado = query.list();
		}catch(Exception e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro durante a consulta");
		}finally{

			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return resultado;
	}

	/**
	 * [UC3136] Relatório Estatístico Atendimento por Raca Cor
	 * 
	 * @author Hiroshi Gonçalves
	 * @date 18/02/2014
	 * @param
	 * @throws ErroRepositorioException
	 */
	public List<RelatorioEstatisticoAtendimentoPorRacaCorBean> pesquisarDadosRelatorioEstatisticoAtendimentoPorRacaCor(GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm form)
					throws ErroRepositorioException{

		List<RelatorioEstatisticoAtendimentoPorRacaCorBean> retorno = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer("");

		try{
			consulta.append(" SELECT new gcom.relatorio.atendimentopublico.RelatorioEstatisticoAtendimentoPorRacaCorBean");
			consulta.append("(e.descricao, count(*)) from RegistroAtendimento a ");
			consulta.append(" inner join a.registroAtendimentoUnidades b ");
			consulta.append(" inner join a.registroAtendimentoSolicitantes c ");
			consulta.append(" inner join c.cliente d ");
			consulta.append(" inner join d.raca e ");
			consulta.append(" inner join a.solicitacaoTipoEspecificacao f ");
			consulta.append(" left  join a.localidade g ");
			consulta.append(" where b.atendimentoRelacaoTipo=1 ");
			
			if(form.getPeriodoAtendimentoInicial() != null && !form.getPeriodoAtendimentoInicial().equals("")){
				consulta.append(" and a.registroAtendimento between :periodoAtendimentoInicial and :periodoAtendimentoFinal ");
			}
			if(form.getUnidadeAtendimento() != null && !form.getUnidadeAtendimento().equals("")){
				consulta.append(" and b.unidadeOrganizacional.id = " + form.getUnidadeAtendimento());
			}
			if(form.getRacaCorSelecionadas() != null && form.getRacaCorSelecionadas().length > 0
							&& !(form.getRacaCorSelecionadas().length == 1 && form.getRacaCorSelecionadas()[0] == -1)){
				consulta.append(" and e.id in (" + Arrays.toString(form.getRacaCorSelecionadas()).replace("[", "").replace("]", "") + ") ");
			}
			if(form.getTipoSolicitacao() != null && form.getTipoSolicitacao().length > 0){
				consulta.append(" and f.solicitacaoTipo.id in ("
								+ Arrays.toString(form.getTipoSolicitacao()).replace("[", "").replace("]", "") + ") ");
			}
			if(form.getEspecificacao() != null && form.getEspecificacao().length > 0){
				consulta.append(" and a.solicitacaoTipoEspecificacao.id in ("
								+ Arrays.toString(form.getEspecificacao()).replace("[", "").replace("]", "") + ") ");
			}
			if(form.getEspecificacao() != null && form.getEspecificacao().length > 0){
				consulta.append(" and a.solicitacaoTipoEspecificacao.id in ("
								+ Arrays.toString(form.getEspecificacao()).replace("[", "").replace("]", "") + ") ");
			}
			if(!Util.isVazioOuBranco(form.getGerenciaRegional())
							&& !form.getGerenciaRegional().equals(ConstantesSistema.VALOR_NAO_INFORMADO)){
				consulta.append(" and a.localidade.gerenciaRegional.id = " + form.getGerenciaRegional());
			}
			if(!Util.isVazioOuBranco(form.getLocalidade())){
				consulta.append(" and a.localidade.id = " + form.getLocalidade());
			}
			consulta.append(" group by e.descricao ");
			consulta.append(" order by e.descricao ");

			Query query = session.createQuery(consulta.toString());

			if(form.getPeriodoAtendimentoInicial() != null && !form.getPeriodoAtendimentoInicial().equals("")){
				query.setTimestamp("periodoAtendimentoInicial",
								Util.formatarDataInicial(Util.converterStringParaData(form.getPeriodoAtendimentoInicial())));
				query.setTimestamp("periodoAtendimentoFinal",
								Util.formatarDataFinal(Util.converterStringParaData(form.getPeriodoAtendimentoFinal())));
			}

			retorno = query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}


	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * [UC0408] Atualizar Registro de Atendimento
	 * 
	 * @author Yara Souza
	 * @date 08/08/2014
	 */
	public List<ServicoAssociadoValorHelper> pesquisarServicoAssociado(Integer idSolicitacaoTipoEspecificacao)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		List<ServicoAssociadoValorHelper> colecaoRetorno = new ArrayList();

		try{
			StringBuffer sql = new StringBuffer();

			sql.append("select tab1.svtp_id,tab1.svtp_dsservicotipo,tab1.svtp_vlservico ");
			sql.append("from ");
			sql.append("( ");
			sql.append("select a.* ");
			sql.append("FROM SERVICO_TIPO A ");
			sql.append("join especificacao_servico_tipo b on a.svtp_id=b.svtp_id and b.step_id = :idSolicitacaoTipoEspecificacao ");
			sql.append("where a.svtp_icuso=1 ");
			sql.append("union all ");
			sql.append("select d.* ");
			sql.append("  from servico_tipo d ");
			sql.append(" JOIN SERVICO_ASSOCIADO E ON E.SVAS_IDASSOCIADO=D.SVTP_ID ");
			sql.append(" join especificacao_servico_tipo g on g.svtp_id=e.svtp_id and g.step_id = :idSolicitacaoTipoEspecificacao ");
			sql.append(" where  d.svtp_icuso=1 ");
			sql.append(") tab1 ");
			sql.append("ORDER BY TAB1.SVTP_ID ");

			SQLQuery query = session.createSQLQuery(sql.toString());
			query.addScalar("svtp_id", Hibernate.INTEGER);
			query.addScalar("svtp_dsservicotipo", Hibernate.STRING);
			query.addScalar("svtp_vlservico", Hibernate.BIG_DECIMAL);
			query.setInteger("idSolicitacaoTipoEspecificacao", idSolicitacaoTipoEspecificacao);

			Collection colecaoConsulta = query.list();
			ServicoAssociadoValorHelper servicoAssociadoValorHelper = null;
			Iterator it = colecaoConsulta.iterator();
			while(it.hasNext()){

				Object[] objConsulta = (Object[]) it.next();
				servicoAssociadoValorHelper = new ServicoAssociadoValorHelper();

				servicoAssociadoValorHelper.setIdServicoTipo((Integer) objConsulta[0]);
				servicoAssociadoValorHelper.setDescricaoServicoTipo((String) objConsulta[1]);
				servicoAssociadoValorHelper.setValorServico((BigDecimal) objConsulta[2]);

				colecaoRetorno.add(servicoAssociadoValorHelper);

			}


		}catch(Exception e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro durante a consulta");
		}finally{

			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return colecaoRetorno;
	}

}
