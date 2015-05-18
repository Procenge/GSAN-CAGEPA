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

package gcom.cadastro.unidade;

import gcom.atendimentopublico.registroatendimento.AtendimentoRelacaoTipo;
import gcom.atendimentopublico.registroatendimento.MeioSolicitacao;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.HibernateUtil;
import gcom.util.Util;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.FiltroParametro;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;


public class RepositorioUnidadeHBM
				implements IRepositorioUnidade {

	private static IRepositorioUnidade instancia;

	/**
	 * Construtor da classe RepositorioAcessoHBM
	 */

	private RepositorioUnidadeHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static IRepositorioUnidade getInstancia(){

		if(instancia == null){
			instancia = new RepositorioUnidadeHBM();
		}

		return instancia;
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Obt�m a unidade associada ao usu�rio que estiver efetuando o registro de atendimento
	 * (UNID_ID e UNID_DSUNIDADE da tabela UNIDADE_ORGANIZACIONAL com
	 * UNID_ID=(UNID_ID da tabela USUARIO com USUR_NMLOGIN=
	 * Login do usu�rio que estiver efetuando o registro de atendimento) e UNID_ICABERTURARA=1)
	 * 
	 * @author Raphael Rossiter
	 * @date 25/07/2006
	 * @param login
	 * @return UnidadeOrganizacional
	 * @throws ErroRepositorioException
	 */
	public UnidadeOrganizacional obterUnidadeOrganizacionalAberturaRAAtivoUsuario(String loginUsuario) throws ErroRepositorioException{

		UnidadeOrganizacional retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT usua.unidadeOrganizacional " + "FROM Usuario usua " + "INNER JOIN usua.unidadeOrganizacional unid  "
							+ "LEFT JOIN FETCH unid.meioSolicitacao meso "
							+ "WHERE usua.login = :loginUsuario AND unid.indicadorAberturaRa = 1 ";

			retorno = (UnidadeOrganizacional) session.createQuery(consulta).setString("loginUsuario", loginUsuario).setMaxResults(1)
							.uniqueResult();

			/*
			 * if (retorno != null){
			 * Hibernate.initialize(retorno.getMeioSolicitacao());
			 * }
			 */

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0406] Filtrar Registro de Atendimento
	 * 
	 * @author Leonardo Regis
	 * @date 05/08/2006
	 * @param unidadeOrganizacional
	 * @return Collection<UnidadeOrganizacional> unidades subordinadas
	 * @throws ErroRepositorioException
	 */
	public Collection<UnidadeOrganizacional> recuperarUnidadesSubordinadasPorUnidadeSuperior(UnidadeOrganizacional unidadeOrganizacional)
					throws ErroRepositorioException{

		Collection<UnidadeOrganizacional> retorno = new ArrayList();
		Collection<Integer> retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();

		String consulta = "";
		try{
			consulta = "SELECT uo.id " + "FROM gcom.cadastro.unidade.UnidadeOrganizacional uo "
							+ "WHERE uo.unidadeSuperior.id = :idUnidadeOrganizacional";
			retornoConsulta = session.createQuery(consulta).setInteger("idUnidadeOrganizacional", unidadeOrganizacional.getId()).list();

			if(retornoConsulta.size() > 0){
				retorno = new ArrayList();
				UnidadeOrganizacional unid = null;
				for(Iterator iter = retornoConsulta.iterator(); iter.hasNext();){
					Integer element = (Integer) iter.next();
					unid = new UnidadeOrganizacional();
					unid.setId(element);
					retorno.add(unid);
				}
			}

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC0406] Filtrar Registro de Atendimento
	 * [FS007] Verificar exist�ncia de unidades subordinadas
	 * 
	 * @author Leonardo Regis
	 * @date 05/08/2006
	 * @param unidadeOrganizacional
	 * @return qtde unidades subordinadas
	 * @throws ErroRepositorioException
	 */
	public int consultarTotalUnidadesSubordinadasPorUnidadeSuperior(UnidadeOrganizacional unidadeOrganizacional)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		String consulta = "";
		try{
			consulta = "SELECT count(*) " + "FROM gcom.cadastro.unidade.UnidadeOrganizacional uo "
							+ "WHERE uo.unidadeSuperior.id = :idUnidadeOrganizacional";
			return ((Number) session.createQuery(consulta).setInteger("idUnidadeOrganizacional", unidadeOrganizacional.getId())
							.setMaxResults(1).uniqueResult()).intValue();

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0406] Filtrar Registro de Atendimento
	 * Caso exista registro de atendimento que est�o na unidade atual informada
	 * (existe ocorr�ncia na tabela REGISTRO_ATENDIMENTO com TRAMITE=Id da Unidade Atual
	 * e maior TRAM_TMTRAMITE)
	 * 
	 * @author Leonardo Regis
	 * @date 07/08/2006
	 * @param unidadeOrganizacional
	 * @return RegistroAtendimento
	 * @throws ControladorException
	 */
	public UnidadeOrganizacional recuperaUnidadeAtualPorRA(RegistroAtendimento registroAtendimento) throws ErroRepositorioException{

		UnidadeOrganizacional retorno = null;
		Object[] retornoConsulta = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT unid.id, unid.descricao, max(tram.dataTramite) " + "FROM Tramite tram "
							+ "INNER JOIN tram.registroAtendimento rgat  " + "INNER JOIN tram.unidadeOrganizacionalDestino unid  "
							+ "WHERE rgat.id = :idRA " + "GROUP BY unid.id, unid.descricao";

			retornoConsulta = (Object[]) session.createQuery(consulta).setInteger("idRA", registroAtendimento.getId()).setMaxResults(1)
							.uniqueResult();

			if(retornoConsulta != null){
				retorno = new UnidadeOrganizacional();
				retorno.setId((Integer) retornoConsulta[0]);
				retorno.setDescricao((String) retornoConsulta[1]);
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0426] Reativar Registro de Atendimento
	 * Caso a unidade destino informada n�o possa receber registros de
	 * atendimento (UNID_ICTRAMITE=2 na tabela UNIDADE_ORGANIZACIONAL com
	 * UNID_ID=Id da unidade destino informada).
	 * [FS0013] - Verificar possibilidade de encaminhamento para a unidade
	 * destino
	 * 
	 * @author Ana Maria
	 * @date 03/09/2006
	 * @return idUnidadeDestino
	 * @throws ErroRepositorioException
	 */
	public Short verificaTramiteUnidade(Integer idUnidadeDestino) throws ErroRepositorioException{

		Short retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select unid.indicadorTramite" + " from UnidadeOrganizacional unid" + " where unid.id = :idUnidadeDestino ";

			retorno = (Short) session.createQuery(consulta).setInteger("idUnidadeDestino", idUnidadeDestino).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programa��o de Ordens de Servi�o
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 */
	public Collection<UnidadeOrganizacional> pesquisarUnidadeOrganizacionalPorUnidade(Integer unidadeLotacao)
					throws ErroRepositorioException{

		Collection<UnidadeOrganizacional> retorno = new ArrayList();
		Collection retornoConsulta = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = "SELECT unid.id, unid.descricao, count(os)" + "FROM OrdemServicoUnidade osUnidade "
							+ "INNER JOIN osUnidade.atendimentoRelacaoTipo art  " + "INNER JOIN osUnidade.ordemServico os  "
							+ "INNER JOIN osUnidade.unidadeOrganizacional unid  " + "LEFT JOIN os.registroAtendimento ra  "
							+ "WHERE ra IS NULL " + "AND os.situacao in (1,3) " + "AND unid.id = :unidadeLotacao "
							+ "AND art.id = :idAtendimentoTipo " + "GROUP BY unid.id, unid.descricao";

			retornoConsulta = session.createQuery(consulta).setInteger("unidadeLotacao", unidadeLotacao).setInteger("idAtendimentoTipo",
							AtendimentoRelacaoTipo.ABRIR_REGISTRAR).list();
			
			if(!retornoConsulta.isEmpty()){

				Object[] retornoConsultaArray = retornoConsulta.toArray();
				Object[] itemLista;
				UnidadeOrganizacional unidadeOrganizacional = null;

				for(int i = 0; i < retornoConsultaArray.length; i++){
					itemLista = (Object[]) retornoConsultaArray[i];
					unidadeOrganizacional = new UnidadeOrganizacional();
					unidadeOrganizacional.setId((Integer) itemLista[0]);
					unidadeOrganizacional.setDescricao((String) itemLista[1]);
					unidadeOrganizacional.setQtidadeOs((Integer) itemLista[2]);
					retorno.add(unidadeOrganizacional);
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
	 * Pesquisa a Unidade Organizacional do Usu�rio Logado
	 * 
	 * @author Rafael Corr�a
	 * @date 25/09/2006
	 * @param id
	 * @return UnidadeOrganizacional
	 * @throws ErroRepositorioException
	 */
	public UnidadeOrganizacional pesquisarUnidadeUsuario(Integer idUsuario) throws ErroRepositorioException{

		UnidadeOrganizacional retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "SELECT usua.unidadeOrganizacional " + "FROM Usuario usua " + "WHERE usua.id = :idUsuario";

			retorno = (UnidadeOrganizacional) session.createQuery(consulta).setInteger("idUsuario", idUsuario.intValue()).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0375] Manter Unidade Organizacional
	 * 
	 * @author Ana Maria
	 * @date 24/11/2006
	 * @param unidadeOrganizacional
	 * @throws ErroRepositorioException
	 */
	public void atualizarUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String update;

		Integer unidadeSuperior = null;
		if(unidadeOrganizacional.getUnidadeSuperior() != null){
			unidadeSuperior = unidadeOrganizacional.getUnidadeSuperior().getId().intValue();
		}

		Integer unidCentralizadora = null;
		if(unidadeOrganizacional.getUnidadeCentralizadora() != null){
			unidCentralizadora = unidadeOrganizacional.getUnidadeCentralizadora().getId().intValue();
		}

		String sigla = null;
		if(unidadeOrganizacional.getSigla() != null){
			sigla = unidadeOrganizacional.getSigla();
		}

		Integer idMeioSolicitacao = null;
		MeioSolicitacao meioSolicitacao = unidadeOrganizacional.getMeioSolicitacao();
		if(meioSolicitacao != null){
			idMeioSolicitacao = meioSolicitacao.getId().intValue();
		}
		
		Integer idLocalidade = null;
		Localidade localidade = unidadeOrganizacional.getLocalidade();
		if(localidade != null){
			idLocalidade = localidade.getId();
		}

		Integer idGerenciaRegional = null;
		GerenciaRegional gerenciaRegional = unidadeOrganizacional.getGerenciaRegional();
		if(gerenciaRegional != null){
			idGerenciaRegional = gerenciaRegional.getId();
		}

		String telefone = "";
		if(unidadeOrganizacional.getTelefone() != null){
			telefone = unidadeOrganizacional.getTelefone();
		}

		String ddd = "";
		if(unidadeOrganizacional.getDdd() != null){
			ddd = unidadeOrganizacional.getDdd();
		}

		String ramal = "";
		if(unidadeOrganizacional.getRamal() != null){
			ramal = unidadeOrganizacional.getRamal();
		}

		String fax = "";
		if(unidadeOrganizacional.getFax() != null){
			fax = unidadeOrganizacional.getFax();
		}

		String observacao = "";
		if(unidadeOrganizacional.getObservacao() != null){
			observacao = unidadeOrganizacional.getObservacao();
		}

		try{
			update = " update gcom.cadastro.unidade.UnidadeOrganizacional" + " set unid_idsuperior = " + unidadeSuperior
							+ " ,unid_idcentralizadora = " + unidCentralizadora
							+ " ,unid_icesgoto = :unidaEsgoto,unid_ictramite = :unidTramite, unid_dsunidade = :descricao"
							+ " ,unid_dssiglaunidade = '" + sigla + "'" + " ,empr_id = :idEmpresa, meso_id = " + idMeioSolicitacao
							+ " ,untp_id = :idTipoUnidade, unid_icabertura = :unidAbertura, unid_icuso = :indicadorUso "
 + ", loca_id = "
							+ idLocalidade + ", greg_id =  " + idGerenciaRegional + ", unid_tmultimaalteracao = :datahoracorrente "

							+ ", UNID_NNFONE =  " + telefone + ", UNID_CDDDD =  " + ddd + ", UNID_NNFONERAMAL =  " + ramal
							+ ", UNID_NNFAX =  " + fax + ", UNID_DSOBSERVACAO =  " + observacao
							+ " where unid_id = :id ";

			Query query = session.createQuery(update)
							.setInteger("unidaEsgoto", unidadeOrganizacional.getIndicadorEsgoto())
							.setInteger("unidTramite", unidadeOrganizacional.getIndicadorTramite())
							.setString("descricao", unidadeOrganizacional.getDescricao())
							.setInteger("idEmpresa", unidadeOrganizacional.getEmpresa().getId().intValue())
							.setInteger("idTipoUnidade", unidadeOrganizacional.getUnidadeTipo().getId().intValue())
							.setInteger("unidAbertura", unidadeOrganizacional.getIndicadorAberturaRa())
							.setInteger("indicadorUso", unidadeOrganizacional.getIndicadorUso())
.setDate("datahoracorrente", new Date())
							.setInteger("id", unidadeOrganizacional.getId());

			query.executeUpdate();

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
			// session.close();
		}

	}

	/**
	 * Verificar se a unidade organizacional est� associada
	 * a uma divis�o de esgoto
	 * 
	 * @author Ana Maria
	 * @date 27/11/2006
	 * @param idUnidade
	 * @return String
	 * @throws ErroRepositorioException
	 */
	public String verificarUnidadeEsgoto(Integer idUnidade) throws ErroRepositorioException{

		String retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select div.descricao" + " from DivisaoEsgoto div" + " inner join div.unidadeOrganizacional unid"
							+ " where unid.id = :idUnidade";

			retorno = (String) session.createQuery(consulta).setInteger("idUnidade", idUnidade.intValue()).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Verificar se a unidade organizacional est� associada
	 * a uma especifica��o de solicita��o
	 * 
	 * @author Ana Maria
	 * @date 28/11/2006
	 * @param idUnidade
	 * @return String
	 * @throws ErroRepositorioException
	 */
	// public String verificarUnidadeTramitacao(Integer idUnidade)
	// throws ErroRepositorioException {
	//		
	// String retorno = null;
	//		
	// Session session = HibernateUtil.getSession();
	//		
	// String consulta = "";
	//		
	// try {
	// consulta = " select step.descricao"
	// + " from SolicitacaoTipoEspecificacao step"
	// + " inner join step.unidadeOrganizacional unid"
	// + " where unid.id = :idUnidade";
	//		
	// retorno = (String) session.createQuery(consulta).setInteger("idUnidade",
	// idUnidade.intValue()).setMaxResults(1).uniqueResult();
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
	 * Pesquisar unidade organizacional
	 * 
	 * @author Ana Maria
	 * @date 28/11/2006
	 * @param idUnidade
	 * @return String
	 * @throws ErroRepositorioException
	 */
	public UnidadeOrganizacional pesquisarUnidadeOrganizacional(Integer idUnidade) throws ErroRepositorioException{

		UnidadeOrganizacional retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = " select unid" + " from UnidadeOrganizacional unid" + " inner join fetch unid.empresa empr"
							+ " left join fetch unid.localidade loca" + " inner join fetch unid.unidadeTipo untp"
							+ " left join fetch unid.meioSolicitacao meio" + " left join fetch unid.gerenciaRegional gere"
							+ " left join fetch unid.unidadeCentralizadora unce" + " left join fetch unid.unidadeSuperior unsup"
							+ " where unid.id = :idUnidade";

			retorno = (UnidadeOrganizacional) session.createQuery(consulta).setInteger("idUnidade", idUnidade.intValue()).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0374] Filtrar Unidade Organizacional
	 * Pesquisa as unidades organizacionais com os condicionais informados
	 * filtroUnidadeOrganizacional
	 * 
	 * @author Ana Maria
	 * @date 30/11/2006
	 * @param filtro
	 * @return Collection
	 */
	public Collection pesquisarUnidadeOrganizacionalFiltro(FiltroUnidadeOrganizacional filtroUnidadeOrganizacional, Integer numeroPagina)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection retorno = null;

		Collection unidadeOrganizacionalParametros = filtroUnidadeOrganizacional.getParametros();

		try{
			String sql = "";

			sql = " select unid.id," + " unid.unidadeTipo.descricao, " + " unid.unidadeTipo.nivel," + " unid.descricao, "
							+ " unid.indicadorAberturaRa, " + " unid.indicadorTramite" + " from UnidadeOrganizacional unid" + " where ";

			Iterator iteratorUnidadeOrganizacional = unidadeOrganizacionalParametros.iterator();
			while(iteratorUnidadeOrganizacional.hasNext()){
				FiltroParametro filtroParametro = (FiltroParametro) iteratorUnidadeOrganizacional.next();

				if(filtroParametro instanceof ParametroSimples){
					ParametroSimples parametroSimples = ((ParametroSimples) filtroParametro);

					sql = sql + "unid." + parametroSimples.getNomeAtributo() + " = " + parametroSimples.getValor() + " and ";

				}

				if(filtroParametro instanceof ComparacaoTexto){
					ComparacaoTexto parametroSimples = ((ComparacaoTexto) filtroParametro);

					sql = sql + "unid." + parametroSimples.getNomeAtributo() + " like '" + parametroSimples.getValor() + "' and ";

				}

				if(filtroParametro instanceof ComparacaoTextoCompleto){
					ComparacaoTextoCompleto parametroSimples = ((ComparacaoTextoCompleto) filtroParametro);

					sql = sql + "unid." + parametroSimples.getNomeAtributo() + " like '" + parametroSimples.getValor() + "' and ";

				}

			}

			sql = Util.formatarHQL(sql, 4);
			System.out.println(sql);

			retorno = (Collection) session.createQuery(sql).setFirstResult(10 * numeroPagina).setMaxResults(10).list();

		}catch(HibernateException e){
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o
			HibernateUtil.closeSession(session);
		}
		return retorno;

	}

	public Integer pesquisarUnidadeOrganizacionalFiltroCount(FiltroUnidadeOrganizacional filtroUnidadeOrganizacional)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno = null;

		Collection unidadeOrganizacionalParametros = filtroUnidadeOrganizacional.getParametros();

		try{
			String sql = "";

			sql = " select count(unid.id)" + " from UnidadeOrganizacional unid" + " where ";

			Iterator iteratorUnidadeOrganizacional = unidadeOrganizacionalParametros.iterator();
			while(iteratorUnidadeOrganizacional.hasNext()){
				FiltroParametro filtroParametro = (FiltroParametro) iteratorUnidadeOrganizacional.next();

				if(filtroParametro instanceof ParametroSimples){
					ParametroSimples parametroSimples = ((ParametroSimples) filtroParametro);

					sql = sql + "unid." + parametroSimples.getNomeAtributo() + " = " + parametroSimples.getValor() + " and ";

				}

				if(filtroParametro instanceof ComparacaoTexto){
					ComparacaoTexto parametroSimples = ((ComparacaoTexto) filtroParametro);

					sql = sql + "unid." + parametroSimples.getNomeAtributo() + " like '" + parametroSimples.getValor() + " %' and ";

				}

				if(filtroParametro instanceof ComparacaoTextoCompleto){
					ComparacaoTextoCompleto parametroSimples = ((ComparacaoTextoCompleto) filtroParametro);

					sql = sql + "unid." + parametroSimples.getNomeAtributo() + " like '" + parametroSimples.getValor() + " ' and ";

				}

			}

			sql = Util.formatarHQL(sql, 4);
			System.out.println(sql);

			retorno = ((Number) session.createQuery(sql).uniqueResult()).intValue();

		}catch(HibernateException e){
			// levanta a exce��o para a pr�xima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sess�o
			HibernateUtil.closeSession(session);
		}
		return retorno;

	}

	/**
	 * Pesquisar unidade organizacional por localidade
	 * 
	 * @author S�vio Luiz
	 * @date 03/01/2007
	 * @param idUnidade
	 * @return String
	 * @throws ErroRepositorioException
	 */
	public UnidadeOrganizacional pesquisarUnidadeOrganizacionalLocalidade(Integer idLocalidade) throws ErroRepositorioException{

		UnidadeOrganizacional retorno = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append(" select unid").append(" from UnidadeOrganizacional unid").append(" left join fetch unid.localidade loca")
							.append(" where loca.id = :idLocalidade");

			retorno = (UnidadeOrganizacional) session.createQuery(consulta.toString()).setInteger("idLocalidade", idLocalidade)
							.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisa o id da unidade negocio para a qual a localidade pertence.
	 * [UC0267] Encerrar Arrecada��o do M�s
	 * 
	 * @author Raphael Rossiter
	 * @date 30/05/2007
	 * @param idLocalidade
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarIdUnidadeNegocioParaLocalidade(Integer idLocalidade) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "select uneg.id from Localidade loca " + "left join loca.unidadeNegocio uneg " + "where loca.id = :idLocalidade";

			retorno = (Integer) session.createQuery(consulta).setInteger("idLocalidade", idLocalidade).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}
	
	
	/**
	 * Retorna uma cole��o de unidade organizacional hierarquia que tenham como superior o superior
	 * da unidade passada
	 * 
	 * @author Hebert Falc�o
	 * @date 11/04/2011
	 */
	public Collection<UnidadeOrganizacionalHierarquia> pesquisarUnidadeOrganizacionalHierarquiaSuperior(Integer idUnidade)
					throws ErroRepositorioException{

		Collection<UnidadeOrganizacionalHierarquia> retornoConsulta = new ArrayList<UnidadeOrganizacionalHierarquia>();

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{
			consulta.append("SELECT uhie ");
			consulta.append("FROM UnidadeOrganizacionalHierarquia uhie ");
			consulta.append("WHERE (uhie.unidadeSuperior.id IN (SELECT uhie2.unidadeSuperior.id ");
			consulta.append("                                   FROM UnidadeOrganizacionalHierarquia uhie2 ");
			consulta.append("                                   WHERE uhie2.unidade.id = :idUnidade) ");
			consulta.append("       AND uhie.unidade.id IN (SELECT uhie3.unidade.id ");
			consulta.append("                               FROM UnidadeOrganizacionalHierarquia uhie3 ");
			consulta.append("                               WHERE uhie3.unidadeSuperior.id = :idUnidade)) ");
			consulta.append("   OR uhie.unidade.id = :idUnidade ");

			retornoConsulta = session.createQuery(consulta.toString()).setInteger("idUnidade", idUnidade).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * Retorna uma cole��o de unidade organizacional hierarquia filtrando pela unidade superior
	 * 
	 * @author Hebert Falc�o
	 * @date 11/04/2011
	 */
	public Collection<UnidadeOrganizacionalHierarquia> pesquisarUnidadeOrganizacionalHierarquiaPeloSuperior(Integer idUnidadeSuperior)
					throws ErroRepositorioException{

		Collection<UnidadeOrganizacionalHierarquia> retornoConsulta = new ArrayList<UnidadeOrganizacionalHierarquia>();

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{
			consulta.append("SELECT uhie ");
			consulta.append("FROM UnidadeOrganizacionalHierarquia uhie ");
			consulta.append("LEFT JOIN FETCH uhie.unidade ");
			consulta.append("LEFT JOIN FETCH uhie.unidadeSuperior ");
			consulta.append("WHERE uhie.unidadeSuperior.id = :idUnidadeSuperior ");

			retornoConsulta = session.createQuery(consulta.toString()).setInteger("idUnidadeSuperior", idUnidadeSuperior).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * Retorna uma cole��o de unidade organizacional hierarquia filtrando pela unidade
	 * 
	 * @author Hebert Falc�o
	 * @date 11/04/2011
	 */
	public Collection<UnidadeOrganizacionalHierarquia> pesquisarUnidadeOrganizacionalHierarquiaPelaUnidade(Integer idUnidade)
					throws ErroRepositorioException{

		Collection<UnidadeOrganizacionalHierarquia> retornoConsulta = new ArrayList<UnidadeOrganizacionalHierarquia>();

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{
			consulta.append("SELECT uhie ");
			consulta.append("FROM UnidadeOrganizacionalHierarquia uhie ");
			consulta.append("LEFT JOIN FETCH uhie.unidade ");
			consulta.append("LEFT JOIN FETCH uhie.unidadeSuperior ");
			consulta.append("WHERE uhie.unidade.id = :idUnidade ");

			retornoConsulta = session.createQuery(consulta.toString()).setInteger("idUnidade", idUnidade).list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}
}