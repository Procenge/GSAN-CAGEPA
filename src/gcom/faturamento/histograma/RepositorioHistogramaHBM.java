
package gcom.faturamento.histograma;

import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.faturamento.*;
import gcom.faturamento.bean.FiltrarEmitirHistogramaEsgotoEconomiaHelper;
import gcom.faturamento.bean.HistogramaEsgotoEconomiaDTO;
import gcom.gerencial.cadastro.bean.HistogramaAguaEconomiaHelper;
import gcom.gerencial.cadastro.bean.HistogramaAguaLigacaoHelper;
import gcom.gerencial.cadastro.bean.HistogramaEsgotoEconomiaHelper;
import gcom.gerencial.cadastro.bean.HistogramaEsgotoLigacaoHelper;
import gcom.micromedicao.consumo.ConsumoTipo;
import gcom.util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;

public final class RepositorioHistogramaHBM
				implements IRepositorioHistograma {

	private static IRepositorioHistograma instancia;

	/**
	 * Construtor da classe RepositorioHistogramaHBM
	 */
	private RepositorioHistogramaHBM() {

	}

	/**
	 * Retorna o valor de instance
	 * 
	 * @return O valor de instance
	 */
	public static IRepositorioHistograma getInstancia(){

		if(instancia == null){
			instancia = new RepositorioHistogramaHBM();
		}
		return instancia;
	}

	/**
	 * Busca um Histograma determinado por seu "agrupamento de regra de negócio"
	 * 
	 * @author ebandeira
	 * @date 19/08/2009
	 * @param HistogramaAguaEconomiaHelper
	 * @return HistogramaAguaEconomia
	 * @throws ErroRepositorioException
	 */
	public HistogramaAguaEconomia pesquisarHistogramaAguaEconomia(HistogramaAguaEconomiaHelper histogramaAguaEconomiaHelper)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		HistogramaAguaEconomia histogramaAguaEconomia = null;
		try{
			StringBuilder hql = new StringBuilder(" from HistogramaAguaEconomia hae where hae.localidade.id = :idLocalidade ");
			hql.append(" and hae.gerenciaRegional.id = :idGerenciaRegional ");
			hql.append(" and hae.unidadeNegocio.id = :idUnidadeNegocio ");
			hql.append(" and hae.localidadeElo.id = :idElo ");
			hql.append(" and hae.setorComercial.id = :idSetorComercial ");
			hql.append(" and hae.quadra.id = :idQuadra ");
			hql.append(" and hae.categoriaTipo.id = :idTipoCategoria ");
			hql.append(" and hae.categoria.id = :idCategoria ");
			hql.append(" and hae.consumoTarifa.id = :idConsumoTarifa ");
			hql.append(" and hae.imovelPerfil.id = :idPerfilImovel ");
			hql.append(" and hae.esferaPoder.id = :idEsferaPoder ");
			hql.append(" and hae.ligacaoAguaSituacao.id = :idSituacaoLigacaoAgua ");
			hql.append(" and hae.indicadorConsumoReal = :idConsumoReal ");
			hql.append(" and hae.indicadorHidrometro = :idExistenciaHidrometro ");
			hql.append(" and hae.indicadorPoco = :idExistenciaPoco ");
			hql.append(" and hae.indicadorVolFixadoAgua = :idExistenciaVolumeFixoAgua ");
			hql.append(" and hae.quantidadeConsumo = :quantidadeConsumo ");
			hql.append(" and hae.anoMesReferencia = :anoMesReferencia ");

			Query query = session.createQuery(hql.toString());
			query.setProperties(histogramaAguaEconomiaHelper);
			query.setLockMode("hae", LockMode.WRITE);

			histogramaAguaEconomia = (HistogramaAguaEconomia) query.uniqueResult();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return histogramaAguaEconomia;
	}

	/**
	 * Busca um Histograma determinado por seu "agrupamento de regra de negócio"
	 * 
	 * @author Saulo Lima, Virgínia Melo
	 * @date 24/08/2009, 26/08/2009
	 * @param FiltroAgrupamentoHistograma
	 * @return HistogramaAguaLigacao
	 * @throws ErroRepositorioException
	 */
	public HistogramaAguaLigacao pesquisarHistogramaAguaLigacao(HistogramaAguaLigacaoHelper histogramaAguaLigacaoHelper)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		HistogramaAguaLigacao histogramaAguaLigacao = null;
		try{

			StringBuilder hql = new StringBuilder("FROM HistogramaAguaLigacao hal WHERE hal.localidade.id = :idLocalidade");
			hql.append(" AND hal.gerenciaRegional.id = :idGerenciaRegional");
			hql.append(" AND hal.unidadeNegocio.id = :idUnidadeNegocio");
			hql.append(" AND hal.localidadeElo.id = :idElo");
			hql.append(" AND hal.setorComercial.id = :idSetorComercial");
			hql.append(" AND hal.quadra.id = :idQuadra");
			hql.append(" AND hal.categoriaTipo.id = :idTipoCategoria");
			hql.append(" AND hal.categoria.id = :idCategoria");
			hql.append(" AND hal.indicadorLigacaoMista = :idLigacaoMista");
			hql.append(" AND hal.imovelPerfil.id = :idPerfilImovel");
			hql.append(" AND hal.esferaPoder.id = :idEsferaPoder");
			hql.append(" AND hal.ligacaoAguaSituacao.id = :idSituacaoLigacaoAgua");
			hql.append(" AND hal.indicadorConsumoReal = :idConsumoReal");
			hql.append(" AND hal.consumoTarifa.id = :idConsumoTarifa");
			hql.append(" AND hal.indicadorHidrometro = :idExistenciaHidrometro");
			hql.append(" AND hal.indicadorPoco = :idExistenciaPoco");
			hql.append(" AND hal.indicadorVolFixadoAgua = :idExistenciaVolumeFixoAgua");
			hql.append(" AND hal.quantidadeConsumo = :quantidadeConsumo");
			hql.append(" AND hal.anoMesReferencia = :anoMesReferencia ");

			Query query = session.createQuery(hql.toString());
			query.setProperties(histogramaAguaLigacaoHelper);
			query.setLockMode("hal", LockMode.WRITE);

			histogramaAguaLigacao = (HistogramaAguaLigacao) query.uniqueResult();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return histogramaAguaLigacao;
	}

	/**
	 * Busca um Histograma determinado por seu "agrupamento de regra de negócio"
	 * 
	 * @author Virgínia Melo
	 * @date 26/08/2009
	 * @param FiltroAgrupamentoHistograma
	 * @return HistogramaEsgotoLigacao
	 * @throws ErroRepositorioException
	 */
	public HistogramaEsgotoLigacao pesquisarHistogramaEsgotoLigacao(HistogramaEsgotoLigacaoHelper histogramaEsgotoLigacaoHelper)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		HistogramaEsgotoLigacao histogramaEsgotoLigacao = null;
		try{

			StringBuilder hql = new StringBuilder("FROM HistogramaEsgotoLigacao hel WHERE hel.localidade.id = :idLocalidade");
			hql.append(" AND hel.gerenciaRegional.id = :idGerenciaRegional");
			hql.append(" AND hel.unidadeNegocio.id = :idUnidadeNegocio");
			hql.append(" AND hel.localidadeElo.id = :idElo");
			hql.append(" AND hel.setorComercial.id = :idSetorComercial");
			hql.append(" AND hel.quadra.id = :idQuadra");
			hql.append(" AND hel.categoriaTipo.id = :idTipoCategoria");
			hql.append(" AND hel.categoria.id = :idCategoria");
			hql.append(" AND hel.indicadorLigacaoMista = :idLigacaoMista");
			hql.append(" AND hel.consumoTarifa.id = :idConsumoTarifa");
			hql.append(" AND hel.imovelPerfil.id = :idPerfilImovel");
			hql.append(" AND hel.esferaPoder.id = :idEsferaPoder");
			hql.append(" AND hel.ligacaoEsgotoSituacao.id = :idSituacaoLigacaoEsgoto");
			hql.append(" AND hel.indicadorConsumoReal = :idConsumoReal");
			hql.append(" AND hel.indicadorHidrometro = :idExistenciaHidrometro");
			hql.append(" AND hel.indicadorPoco = :idExistenciaPoco");
			hql.append(" AND hel.indicadorVolumeFixadoEsgoto = :idExistenciaVolumeFixoEsgoto");
			hql.append(" AND hel.percentualEsgoto = :percentualColetaEsgoto");
			hql.append(" AND hel.quantidadeConsumo = :quantidadeConsumo");
			hql.append(" AND hel.referencia = :anoMesReferencia ");

			Query query = session.createQuery(hql.toString());
			query.setProperties(histogramaEsgotoLigacaoHelper);
			query.setLockMode("hel", LockMode.WRITE);

			histogramaEsgotoLigacao = (HistogramaEsgotoLigacao) query.uniqueResult();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return histogramaEsgotoLigacao;
	}

	/**
	 * Busca um Histograma determinado por seu "agrupamento de regra de negócio"
	 * 
	 * @author Virgínia Melo
	 * @date 26/08/2009
	 * @param histogramaEsgotoEconomiaHelper
	 * @return HistogramaEsgotoEconomia
	 * @throws ErroRepositorioException
	 */
	public HistogramaEsgotoEconomia pesquisarHistogramaEsgotoEconomia(HistogramaEsgotoEconomiaHelper histogramaEsgotoEconomiaHelper)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		HistogramaEsgotoEconomia histogramaEsgotoEconomia = null;
		try{

			StringBuilder hql = new StringBuilder("FROM HistogramaEsgotoEconomia hee WHERE hee.localidade.id = :idLocalidade");
			hql.append(" AND hee.gerenciaRegional.id = :idGerenciaRegional");
			hql.append(" AND hee.unidadeNegocio.id = :idUnidadeNegocio");
			hql.append(" AND hee.localidadeElo.id = :idElo");
			hql.append(" AND hee.setorComercial.id = :idSetorComercial");
			hql.append(" AND hee.quadra.id = :idQuadra");
			hql.append(" AND hee.categoriaTipo.id = :idTipoCategoria");
			hql.append(" AND hee.categoria.id = :idCategoria");
			hql.append(" AND hee.consumoTarifa.id = :idConsumoTarifa");
			hql.append(" AND hee.imovelPerfil.id = :idPerfilImovel");
			hql.append(" AND hee.esferaPoder.id = :idEsferaPoder");
			hql.append(" AND hee.ligacaoEsgotoSituacao.id = :idSituacaoLigacaoEsgoto");
			hql.append(" AND hee.indicadorConsumoReal = :idConsumoReal");
			hql.append(" AND hee.indicadorHidrometro = :idExistenciaHidrometro");
			hql.append(" AND hee.indicadorPoco = :idExistenciaPoco");
			hql.append(" AND hee.indicadorVolumeFixadoEsgoto = :idExistenciaVolumeFixoEsgoto");
			hql.append(" AND hee.percentualEsgoto = :percentualEsgoto");
			hql.append(" AND hee.quantidadeConsumo = :quantidadeConsumo");
			hql.append(" AND hee.referencia = :anoMesReferencia ");

			Query query = session.createQuery(hql.toString());
			query.setProperties(histogramaEsgotoEconomiaHelper);
			query.setLockMode("hee", LockMode.WRITE);

			histogramaEsgotoEconomia = (HistogramaEsgotoEconomia) query.uniqueResult();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return histogramaEsgotoEconomia;
	}

	/**
	 * @author Andre Nishimura
	 * @date 25/08/2009
	 *       Busca um Esfera Poder de um Cliente Responsavel, ou Usuario, caso o primeiro nao exista
	 * @return EsferaPoder
	 * @throws ErroRepositorioException
	 */
	public EsferaPoder pesquisarEsferaPoderClienteResponsavelImovel(Integer idImovel) throws ErroRepositorioException{

		EsferaPoder retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "  ep " + "from " + "  gcom.cadastro.imovel.Imovel i " + "  inner join i.clienteImoveis ci "
							+ "  inner join ci.cliente c " + "  inner join c.clienteTipo ct " + "  inner join ct.esferaPoder ep "
							+ "  inner join ci.clienteRelacaoTipo crt " + "where "
							+ "  crt.id = :clienteResponsavel and ci.dataFimRelacao is null and i.id =:idImovel";

			retorno = (EsferaPoder) session.createQuery(consulta).setInteger("clienteResponsavel", ClienteRelacaoTipo.RESPONSAVEL)
							.setInteger("idImovel", idImovel).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Método que retorna uma colecao de ContaCategoria com valores preenchidos para geração do
	 * histograma;
	 * 
	 * @param idConta
	 * @return Collection - Coleção de ContaCategoria
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaCategoriaParaHistograma(Integer idConta) throws ErroRepositorioException{

		Collection retorno = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta;

		try{

			consulta = "select contaCategoria " + "from ContaCategoria contaCategoria "
							+ "left join fetch contaCategoria.comp_id.conta conta " + "left join fetch conta.localidade l "
							+ "left join fetch l.gerenciaRegional gr " + "left join fetch l.unidadeNegocio un "
							+ "left join fetch l.localidade elo " + "left join fetch conta.ligacaoAguaSituacao las "
							+ "left join fetch conta.ligacaoEsgotoSituacao les " + "left join fetch conta.quadraConta q "
							+ "left join fetch conta.consumoTarifa ct " + "left join fetch contaCategoria.comp_id.categoria categoria "
							+ "left join fetch categoria.categoriaTipo categoriaT " + "left join fetch q.setorComercial sc "
							+ "left join fetch conta.imovelPerfil ip " + "where conta.id = :idConta ";

			retorno = session.createQuery(consulta).setInteger("idConta", idConta).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Busca um HistogramaServico
	 * 
	 * @author Cinthya Cavalcanti
	 * @date 08/08/2011
	 * @param idLocalidade
	 * @param idSetorComercial
	 * @param idCategoria
	 * @param idDebitoTipo
	 * @return HistogramaServico
	 * @throws ErroRepositorioException
	 */
	public HistogramaServico pesquisarHistogramaServico(Integer idLocalidade, Integer idSetorComercial, Integer idCategoria,
					Integer idDebitoTipo) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		HistogramaServico histogramaServico = null;
		try{

			StringBuilder hql = new StringBuilder("FROM HistogramaServico hse WHERE");

			if(idLocalidade != null){
				hql.append(" hse.localidade.id = :idLocalidade AND ");
			}

			if(idSetorComercial != null){
				hql.append(" hse.setorComercial.id = :idSetorComercial AND ");
			}

			if(idCategoria != null){
				hql.append(" hse.categoria.id = :idCategoria AND ");
			}

			if(idDebitoTipo != null){
				hql.append(" hse.debitoTipo.id = :idDebitoTipo ");
			}

			if(hql.substring(hql.length() - 4, hql.length()).equals("AND ")){
				hql.delete(hql.length() - 4, hql.length());
			}

			Query query = session.createQuery(hql.toString());

			if(idLocalidade != null){
				query.setInteger("idLocalidade", idLocalidade);
			}

			if(idSetorComercial != null){
				query.setInteger("idSetorComercial", idSetorComercial);
			}

			if(idCategoria != null){
				query.setInteger("idCategoria", idCategoria);
			}

			if(idDebitoTipo != null){
				query.setInteger("idDebitoTipo", idDebitoTipo);
			}

			histogramaServico = (HistogramaServico) query.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return histogramaServico;
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<HistogramaEsgotoEconomiaDTO> pesquisarDadosRelatorioHistogramaEsgoto(
					FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro) throws ErroRepositorioException{
		Session session = HibernateUtil.getSession();
		Collection<HistogramaEsgotoEconomiaDTO> dadosRelatorioHistograma = new ArrayList<HistogramaEsgotoEconomiaDTO>();
		try{
			if(OpcaoTotalizacaoEnum.ESTADO.getOpcao() == filtro.getOpcaoTotalizacao()){
				dadosRelatorioHistograma.addAll(RepositorioHistogramaEsgotoEconomiaHelperFactory.getGerador(filtro).executarPesquisa(
								session));
			}else{
				RepositorioHistogramaEsgotoEconomiaHelper repositorioHelper = RepositorioHistogramaEsgotoEconomiaHelperFactory
								.getGerador(filtro);

				List<HistogramaEsgotoEconomiaDTO> dadosRelatorioHistogramaTemp = repositorioHelper.executarPesquisa(session);
				if(!Util.isVazioOrNulo(dadosRelatorioHistogramaTemp)){
					String totalizadorAnterior = null;
					for(HistogramaEsgotoEconomiaDTO histogramaEsgotoEconomiaDTO : dadosRelatorioHistogramaTemp){
						if(Util.isVazioOuBranco(totalizadorAnterior)){
							totalizadorAnterior = histogramaEsgotoEconomiaDTO.getDescricaoOpcaoTotalizacao();
							dadosRelatorioHistograma.add(histogramaEsgotoEconomiaDTO);
						}else if(totalizadorAnterior.equals(histogramaEsgotoEconomiaDTO.getDescricaoOpcaoTotalizacao())){
							dadosRelatorioHistograma.add(histogramaEsgotoEconomiaDTO);
						}else{
							dadosRelatorioHistograma.addAll(repositorioHelper.executarPesquisa(totalizadorAnterior,
											histogramaEsgotoEconomiaDTO.getDescricaoOpcaoTotalizacao(), session));
							dadosRelatorioHistograma.add(histogramaEsgotoEconomiaDTO);
							totalizadorAnterior = histogramaEsgotoEconomiaDTO.getDescricaoOpcaoTotalizacao();
						}
					}
					dadosRelatorioHistograma.addAll(repositorioHelper.executarPesquisa(totalizadorAnterior,
									"00-xx / 00-xx / 00-xx / 00-xx", session));
				}
				if(Arrays.asList(OpcaoTotalizacaoEnum.ESTADO_ELO_POLO.getOpcao(),//
								OpcaoTotalizacaoEnum.ESTADO_GERENCIA_REGIONAL.getOpcao(),//
								OpcaoTotalizacaoEnum.ESTADO_LOCALIDADE.getOpcao(),//
								OpcaoTotalizacaoEnum.ESTADO_UNIDADE_NEGOCIO.getOpcao()).contains(filtro.getOpcaoTotalizacao())){
					filtro.setOpcaoTotalizacao(OpcaoTotalizacaoEnum.ESTADO.getOpcao());
					dadosRelatorioHistograma.addAll(RepositorioHistogramaEsgotoEconomiaHelperFactory.getGerador(filtro).executarPesquisa(
									session));
				}
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return dadosRelatorioHistograma;
	}

	/**
	 * Verifica se o consumo do imóvel é real.
	 * 
	 * @param idImovel
	 *            id do imovel que terá seu consumo verificado
	 * @param referencia
	 *            referencia (ano-mês) da conta
	 * @return 1 se consumo real, 2 se não
	 * @throws ErroRepositorioException
	 */
	public Integer verificarConsumoRealNaReferencia(Integer idImovel, Integer referencia)
					throws ErroRepositorioException{

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
	 * Caso não obtenha retorno em medição histórico, verificar ligação de água..
	 * 
	 * @param idImovel
	 *            - id do imovel
	 * @return 1 se possuir hidrômetro, 2 senï¿½o
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaHidrometro(Integer idImovel, Short funcaoExecutada, Integer referencia)
					throws ErroRepositorioException{

		Object retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			if(funcaoExecutada.equals(ConstantesSistema.GERACAO_HISTOGRAMA_FATURAMENTO)){

				consulta = "select " + "  count(*) " + "from " + "  gcom.micromedicao.medicao.MedicaoHistorico mh " + "where "
								+ "  mh.ligacaoAgua.id = :idImovel and " + "  mh.anoMesReferencia = ( 	select "
								+ "  								anoMesFaturamento " + "  							from "
								+ "  								gcom.cadastro.sistemaparametro.SistemaParametro sp where sp.parmId = "
								+ ConstantesConfig.getIdEmpresa() + " ) ";

				retorno = session.createQuery(consulta).setInteger("idImovel", idImovel).uniqueResult();

				if(retorno != null && ((Number) retorno).intValue() >= 1){
					return 1;
				}else{

					return 2;
				}

			}else{

				consulta = "select " + "  count(*) " + "from " + "  gcom.micromedicao.medicao.MedicaoHistorico mh " + "where "
								+ "  mh.ligacaoAgua.id = :idImovel and " + "  mh.anoMesReferencia = :referencia";

				retorno = session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("referencia", referencia)
								.uniqueResult();

				if(retorno != null && ((Number) retorno).intValue() >= 1){
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
	public Integer verificarExistenciaPoco(Integer idImovel, Short funcaoExecutada, Integer referencia) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;

		try{

			if(funcaoExecutada.equals(ConstantesSistema.GERACAO_HISTOGRAMA_FATURAMENTO)){
				consulta = "select " + "  case when ch.pocoTipo.id is null or ch.pocoTipo.id = 0 then " + "    2 " + "  else " + "    1 "
								+ "  end " + "from " + "  gcom.micromedicao.consumo.ConsumoHistorico ch " + "where "
								+ "  ch.imovel.id = :idImovel and " + "  ch.ligacaoTipo.id = 2 and "
								+ "  ch.referenciaFaturamento = ( 	select " + "  								anoMesFaturamento " + "  							from "
								+ "  								gcom.cadastro.sistemaparametro.SistemaParametro sp where sp.parmId = "
								+ ConstantesConfig.getIdEmpresa() + " )";

				return (Integer) (session.createQuery(consulta).setInteger("idImovel", idImovel).uniqueResult() == null ? 1 : 2);
			}else{
				consulta = "select " + "  case when ch.pocoTipo.id is null or ch.pocoTipo.id = 0 then " + "    2 " + "  else " + "    1 "
								+ "  end " + "from " + "  gcom.micromedicao.consumo.ConsumoHistorico ch " + "where "
								+ "  ch.imovel.id = :idImovel and " + "  ch.ligacaoTipo.id = 2 and "
								+ "  ch.referenciaFaturamento = :referencia";

				return (Integer) (session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("referencia", referencia)
								.uniqueResult() == null ? 1 : 2);

			}



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
	public Integer verificarExistenciaVolumeFixoAgua(Integer idImovel, Short funcaoExecutada, Integer referencia)
					throws ErroRepositorioException{

		Object retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{

			if(funcaoExecutada.equals(ConstantesSistema.GERACAO_HISTOGRAMA_FATURAMENTO)){
				consulta = "select " + "  ch.consumoMinimo " + "from " + "  gcom.micromedicao.consumo.ConsumoHistorico ch " + "where "
								+ "  ch.imovel.id = :idImovel and " + "  ch.ligacaoTipo.id = 1 and "
								+ "  ch.referenciaFaturamento = ( select " + "  									anoMesFaturamento " + "  								from "
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
			}else{
				consulta = "select " + "  ch.consumoMinimo " + "from " + "  gcom.micromedicao.consumo.ConsumoHistorico ch " + "where "
								+ "  ch.imovel.id = :idImovel and " + "  ch.ligacaoTipo.id = 1 and "
								+ "  ch.referenciaFaturamento = :referencia";

				retorno = session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("referencia", referencia)
								.uniqueResult();

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
	public Integer verificarExistenciaVolumeFixoEsgoto(Integer idImovel, Short funcaoExecutada, Integer referencia)
					throws ErroRepositorioException{

		Object retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{

			if(funcaoExecutada.equals(ConstantesSistema.GERACAO_HISTOGRAMA_FATURAMENTO)){
				consulta = "select " + "  ch.consumoMinimo " + "from " + "  gcom.micromedicao.consumo.ConsumoHistorico ch " + "where "
								+ "  ch.imovel.id = :idImovel and " + "  ch.ligacaoTipo.id = 2 and "
								+ "  ch.referenciaFaturamento = ( select " + "  									anoMesFaturamento " + "  								from "
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

			}else{
				consulta = "select " + "  ch.consumoMinimo " + "from " + "  gcom.micromedicao.consumo.ConsumoHistorico ch " + "where "
								+ "  ch.imovel.id = :idImovel and " + "  ch.ligacaoTipo.id = 2 and "
								+ "  ch.referenciaFaturamento = :referencia";

				retorno = session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("referencia", referencia)
								.uniqueResult();

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
	public Float percentualColetaEsgoto(Integer idImovel, Short funcaoExecutada, Integer referencia) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			
			if(funcaoExecutada.equals(ConstantesSistema.GERACAO_HISTOGRAMA_FATURAMENTO)){
				consulta = "select " + "  ch.percentualColeta " + "from " + "  gcom.micromedicao.consumo.ConsumoHistorico ch " + "where "
								+ "  ch.imovel.id = :idImovel and " + "  ch.ligacaoTipo.id = 1 and "
								+ "  ch.referenciaFaturamento = ( select " + "  									anoMesFaturamento " + "  								from "
								+ "  									gcom.cadastro.sistemaparametro.SistemaParametro sp where sp.parmId = "
								+ ConstantesConfig.getIdEmpresa() + " )";

				return (Float) session.createQuery(consulta).setInteger("idImovel", idImovel).uniqueResult();

			}else{
				consulta = "select " + "  ch.percentualColeta " + "from " + "  gcom.micromedicao.consumo.ConsumoHistorico ch " + "where "
								+ "  ch.imovel.id = :idImovel and " + "  ch.ligacaoTipo.id = 1 and "
								+ "  ch.referenciaFaturamento =:referencia";

				return (Float) session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("referencia", referencia)
								.uniqueResult();

			}

		}catch(HibernateException e){
			// levanta a exceï¿½ï¿½o para a prï¿½xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessï¿½o
			HibernateUtil.closeSession(session);
		}
	}


	/**
	 * HistogramaAguaLigacao
	 * 
	 * @param anoMesInicial
	 * @param anoMesFinal
	 * @throws ErroRepositorioException
	 */

	public void deletarHistogramaAguaLigacao(Integer anoMesInicial, Integer anoMesFinal) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			String delete = "delete HistogramaAguaLigacao h where anoMesReferencia between :anoMesInicial and :anoMesFinal ";

			session.createQuery(delete).setInteger("anoMesInicial", anoMesInicial).setInteger("anoMesFinal", anoMesFinal).executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * HistogramaAguaEconomia
	 * 
	 * @param anoMesInicial
	 * @param anoMesFinal
	 * @throws ErroRepositorioException
	 */

	public void deletarHistogramaAguaEconomia(Integer anoMesInicial, Integer anoMesFinal) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			String delete = "delete HistogramaAguaEconomia h where anoMesReferencia between :anoMesInicial and :anoMesFinal ";

			session.createQuery(delete).setInteger("anoMesInicial", anoMesInicial).setInteger("anoMesFinal", anoMesFinal).executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * HistogramaEsgotoLigacao
	 * 
	 * @param anoMesInicial
	 * @param anoMesFinal
	 * @throws ErroRepositorioException
	 */

	public void deletarHistogramaEsgotoLigacao(Integer anoMesInicial, Integer anoMesFinal) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			String delete = "delete HistogramaEsgotoLigacao h where referencia between :anoMesInicial and :anoMesFinal ";

			session.createQuery(delete).setInteger("anoMesInicial", anoMesInicial).setInteger("anoMesFinal", anoMesFinal).executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * HistogramaEsgotoEconomia
	 * 
	 * @param anoMesInicial
	 * @param anoMesFinal
	 * @throws ErroRepositorioException
	 */
	public void deletarHistogramaEsgotoEconomia(Integer anoMesInicial, Integer anoMesFinal) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			String delete = "delete HistogramaEsgotoEconomia h where referencia between :anoMesInicial and :anoMesFinal ";

			session.createQuery(delete).setInteger("anoMesInicial", anoMesInicial).setInteger("anoMesFinal", anoMesFinal).executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

}
