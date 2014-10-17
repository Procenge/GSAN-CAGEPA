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
 * GSANPCG
 * Vitor Hora
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

package gcom.cadastro.imovel;

import gcom.arrecadacao.pagamento.Pagamento;
import gcom.arrecadacao.pagamento.PagamentoHistorico;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.FiscalizacaoSituacaoAgua;
import gcom.atendimentopublico.ordemservico.FiscalizacaoSituacaoEsgoto;
import gcom.cadastro.cliente.*;
import gcom.cadastro.cliente.bean.ClienteImovelEconomiaHelper;
import gcom.cadastro.endereco.Cep;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.bean.*;
import gcom.cobranca.*;
import gcom.cobranca.bean.EmitirDocumentoCobrancaBoletimCadastroHelper;
import gcom.cobranca.bean.SituacaoEspecialCobrancaHelper;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FaturamentoSituacaoHistorico;
import gcom.faturamento.FaturamentoSituacaoTipo;
import gcom.faturamento.bean.SituacaoEspecialFaturamentoHelper;
import gcom.faturamento.consumofaixaareacategoria.ConsumoFaixaAreaCategoria;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.gui.faturamento.bean.FiltrarImovelInserirManterContaHelper;
import gcom.micromedicao.Rota;
import gcom.micromedicao.consumo.LigacaoTipo;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.relatorio.cadastro.cliente.FiltrarRelatorioClientesEspeciaisHelper;
import gcom.relatorio.cadastro.imovel.FiltrarRelatorioBoletimCadastroHelper;
import gcom.util.*;
import gcom.util.filtro.GeradorHQLCondicional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import org.hibernate.*;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * Descrição da classe
 * 
 * @author Administrador
 * @date 18/03/2006
 */

public class RepositorioImovelHBM
				implements IRepositorioImovel {

	private static IRepositorioImovel instancia;

	/**
	 * Construtor da classe RepositorioImovelHBM
	 */

	private RepositorioImovelHBM() {

	}

	/**
	 * Retorna o valor de instance
	 * 
	 * @return O valor de instance
	 */

	public static IRepositorioImovel getInstancia(){

		if(instancia == null){

			instancia = new RepositorioImovelHBM();

		}

		return instancia;

	}

	/**
	 * Inseri um imovel na base
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */

	public void inserirImovel(Imovel imovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{

			session.save(imovel);

			session.flush();

		}catch(HibernateException e){

			e.printStackTrace();

			throw new ErroRepositorioException("Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

			// session.close();

		}

	}

	/**
	 * altera um imovel na base
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */

	public void atualizarImovel(Imovel imovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{

			session.update(imovel);

			session.flush();

		}catch(HibernateException e){

			e.printStackTrace();

			throw new ErroRepositorioException("Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

			// session.close();

		}

	}

	/**
	 * Pesquisa de Imovel na base
	 * 
	 * @param filtroImovel
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */

	public Collection pesquisarImovel(FiltroImovel filtroImovel)

	throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			retorno = GeradorHQLCondicional.gerarQueryCriteriaExpression(

			session, filtroImovel, Imovel.class);

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	/**
	 * UC-0355] - Efetuar Corte de Ligaçã de Àgua [SB001] Atualizar Imóvel
	 * Campos LEST_ID e IMOV _ TMULTIMAALTERACAO
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioExceptions
	 *                Descrição da exceção
	 * @date 07/07/2006
	 * @author Leandro Cavalcanti
	 * @param imovel
	 * @throws ErroRepositorioException
	 */

	public void atualizarImovelLigacaoAgua(Imovel imovel,

	Integer idLigacaoAguaSituacao) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		String update;

		try{

			// Atualizar os campos LAST_ID e IMOV _ TMULTIMAALTERACAO

			update = "update gcom.cadastro.imovel.Imovel set "

			+ "last_id = :ligSitAguaId, imov_tmultimaalteracao = :datahoracorrente "

			+ "where imov_id = :imovelId ";

			session.createQuery(update).setInteger("imovelId",

			imovel.getId().intValue()).setInteger("ligSitAguaId",

			idLigacaoAguaSituacao).setDate("datahoracorrente",

			new Date()).executeUpdate();

			// session.save(imovel);

			// session.flush();

		}catch(HibernateException e){

			e.printStackTrace();

			throw new ErroRepositorioException("Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

			// session.close();

		}

	}

	/**
	 * altera um imovel na base
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioExceptions
	 *                Descrição da exceção
	 * @date 14/06/2006
	 * @author Leandro Cavalcanti
	 * @param imovel
	 * @throws ErroRepositorioException
	 */

	public void atualizarImovelExecucaoOrdemServicoLigacaoAgua(Imovel imovel,

	LigacaoAguaSituacao situacaoAgua) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		String update;

		try{

			// Atualizar os campos LAST_ID e IMOV _ TMULTIMAALTERACAO

			update = "update gcom.cadastro.imovel.Imovel set "

			+ "last_id = :ligSitAguaId, imov_tmultimaalteracao = :datahoracorrente "

			+ "where imov_id = :imovelId";

			session.createQuery(update).setInteger("imovelId",

			imovel.getId().intValue()).setInteger("ligSitAguaId",

			situacaoAgua.getId()).setTimestamp("datahoracorrente",

			new Date()).executeUpdate();

			// session.save(imovel);

			session.flush();

			// //item 1.3

			// /*if(imovel.getLigacaoEsgotoSituacao()!= null &&

			// imovel.getLigacaoEsgotoSituacao().getId().intValue() ==

			// LigacaoEsgotoSituacao.LIG_FORA_DE_USO.intValue()){

			// */

			// //Atualizar os compos LAST_ID e IMOV _ TMULTIMAALTERACAO

			// String updateImovel = "update gcom.cadastro.imovel.Imovel set "

			// +"lest_id = :ligSitEsgotoId, imov_tmultimaalteracao =

			// :datahoracorrente, "

			// +"where imovel.id = :imovelId";

			//

			// session.createQuery(updateImovel)

			// .setInteger("imovelId",imovel.getId().intValue())

			// .setInteger("ligSitEsgotoId",situacaoAgua.getId())

			// .setDate("datahoracorrente", new Date()).executeUpdate();

			//

			// session.save(imovel);

			// session.flush();

			//

		}catch(HibernateException e){

			e.printStackTrace();

			throw new ErroRepositorioException("Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

			// session.close();

		}

	}

	/**
	 * altera um imovel na base
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 * @date 27/06/2006
	 * @author Leandro Cavalcanti
	 * @param imovel
	 * @throws ErroRepositorioException
	 */

	public void atualizarImovelExecucaoOrdemServicoLigacaoEsgoto(Imovel imovel,

	LigacaoEsgotoSituacao situacaoEsgoto)

	throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		String update;

		try{

			// Atualizar os campos LAST_ID e IMOV _ TMULTIMAALTERACAO

			update = "update gcom.cadastro.imovel.Imovel set "

			+ "lest_id = :ligSitEsgId, imov_tmultimaalteracao = :datahoracorrente "

			+ "where imov_id = :imovelId";

			session.createQuery(update).setInteger("imovelId",

			imovel.getId().intValue()).setInteger("ligSitEsgId",

			situacaoEsgoto.getId()).setTimestamp("datahoracorrente",

			new Date()).executeUpdate();

			// session.save(imovel);

			session.flush();

		}catch(HibernateException e){

			e.printStackTrace();

			throw new ErroRepositorioException("Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

			// session.close();

		}

	}

	/**
	 * Remove um cliente imovel economia
	 * 
	 * @param id
	 *            Description of the Parameter
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */

	public void removerClienteImovelEconomia(Integer id)

	throws ErroRepositorioException{

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			// remove uma cliente imovel na base da base

			Iterator iterator = session.createQuery(

			"from gcom.cadastro.cliente.ClienteImovelEconomia "

			+ "where imovelEconomia = :clienteImovelEconomia "

			+ "and dataFimRelacao is null").setInteger(

			"clienteImovelEconomia", id).iterate();

			while(iterator.hasNext()){

				iterator.next();

				iterator.remove();

			}

			session.flush();

			// restrições no sistema

		}catch(JDBCException e){

			// e.printStackTrace();

			// levanta a exceção para a próxima camada

			throw new RemocaoInvalidaException(e);

			// erro no hibernate

		}catch(HibernateException e){

			e.printStackTrace();

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate: ");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param objeto
	 *            Descrição do parâmetro
	 * @param condicional
	 *            Descrição do parâmetro
	 * @param id
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */

	public void removerTodos(String objeto, String condicional, Integer id)

	throws ErroRepositorioException{

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			Iterator iterator = session.createQuery(

			"select distinct objeto from " + objeto

			+ " objeto where objeto." + condicional + "= "

			+ id).iterate();

			while(iterator.hasNext()){

				iterator.next();

				iterator.remove();

			}

			session.flush();

			// restrições no sistema

		}catch(JDBCException e){

			// e.printStackTrace();

			// levanta a exceção para a próxima camada

			throw new RemocaoInvalidaException(e);

			// erro no hibernate

		}catch(HibernateException e){

			e.printStackTrace();

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate: ");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

	}

	/**
	 * Pesquisa uma coleção de imóveis com uma query especifica
	 * 
	 * @param idLocalidade
	 *            parametros para a consulta
	 * @param idSetorComercial
	 *            parametros para a consulta
	 * @param idQuadra
	 *            parametros para a consulta
	 * @param lote
	 *            Descrição do parâmetro
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */

	public Collection pesquisarImovel(Integer idLocalidade,

	Integer idSetorComercial, Integer idQuadra, Short lote,

	int indicadorExclusao) throws ErroRepositorioException{

		// cria a coleção de retorno

		Collection retorno = null;

		// Query

		String consulta;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			// pesquisa a coleção de atividades e atribui a variável "retorno"

			consulta = "SELECT imovel.id, imovel.localidade, "

			+ "imovel.setorComercial, imovel.quadra, "

			+ "imovel.lote, imovel.subLote, rota.id, ftgr.id, "

			+ "ftst.id " + "FROM gcom.cadastro.imovel.Imovel  imovel "

			+ "INNER JOIN imovel.quadra quadra "

			+ "LEFT JOIN imovel.faturamentoSituacaoTipo ftst "

			+ "INNER JOIN imovel.rota rota "

			+ "INNER JOIN rota.faturamentoGrupo ftgr ";

			consulta = consulta

			+ "where (imovel.indicadorExclusao IS NULL or imovel.indicadorExclusao != "

			+ indicadorExclusao + ")";

			if(idLocalidade != null){

				consulta = consulta + "and imovel.localidade.id = "

				+ idLocalidade.intValue();

			}
			if(idSetorComercial != null){
				consulta = consulta + "and imovel.setorComercial.id = " + idSetorComercial.intValue();
			}

			if(idQuadra != null && idSetorComercial != null){

				consulta = consulta + " and imovel.quadra.id = "

				+ idQuadra.intValue();

			}else if(idQuadra != null && idSetorComercial == null){

				consulta = consulta + "and imovel.quadra.id = "

				+ idQuadra.intValue();

			}

			if(lote != null && idQuadra != null){

				consulta = consulta + " and imovel.lote = " + lote.intValue();

			}else if(lote != null && idQuadra == null){

				consulta = consulta + "and imovel.lote = " + lote.intValue();

			}

			retorno = session.createQuery(consulta).list();

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
	 * Atualiza apenas os dados (Localidade, Setor, Quadra e lote) do imóvel
	 * 
	 * @author Luciano Galvao
	 * @date 25/02/2013
	 */
	public void atualizarImovelInscricao(Integer imovelId, Integer localidadeId, Integer setorComercialId, Integer codigoSetorComercial,
					Integer quadraId, Integer numeroQuadra, short lote, short subLote, short testadaLote, Integer rotaId)
					throws ErroRepositorioException{

		// Query

		String update;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			// Atualiza apenas os dados da inscrição(Localidade, Setor, Quadra e lote) do imóvel
			update = "update gcom.cadastro.imovel.Imovel set "
							+ "loca_id = :loca, stcm_id = :stcm, stcm_cdsetorcomercial = :cdst, qdra_id = :qdra, qdra_nnquadra = :nqdr, "
							+ "imov_nnlote = :nnlote, imov_nnsublote = :nnsublote, imov_nntestadalote = :nntestadalote, rota_id = :rota, "
							+ "imov_tmultimaalteracao = :datahoracorrente where imov_id = :imov";

			session.createQuery(update).setInteger("loca", localidadeId).setInteger("stcm", setorComercialId)
							.setInteger("cdst", codigoSetorComercial).setInteger("qdra", quadraId).setInteger("nqdr", numeroQuadra)
							.setShort("nnlote", lote).setShort("nnsublote", subLote).setShort("nntestadalote", testadaLote)
							.setInteger("rota", rotaId).setTimestamp("datahoracorrente", new Date()).setInteger("imov", imovelId)
							.executeUpdate();

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
	 * < <Descrição do método>>
	 * 
	 * @param imovelSubcategoria
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */

	public void atualizarImovelSubCategoria(

	ImovelSubcategoria imovelSubcategoria)

	throws ErroRepositorioException{

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			session.saveOrUpdate(imovelSubcategoria);

			session.flush();

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

	}

	/**
	 * [UC0064 – Obter Quantidade Economias]
	 * 
	 * @param imovel
	 * @return Object
	 * @exception ErroRepositorioException
	 */
	public Object pesquisarObterQuantidadeEconomias(Imovel imovel) throws ErroRepositorioException{

		Object retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{

			consulta.append("select sum(imsc.quantidadeEconomias) ");
			consulta.append("from gcom.cadastro.imovel.ImovelSubcategoria imsc ");
			consulta.append("inner join imsc.comp_id.imovel ");
			consulta.append("where imsc.comp_id.imovel.id = :imovelId ");

			retorno = session.createQuery(consulta.toString()).setInteger("imovelId", imovel.getId().intValue()).uniqueResult();

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
	 * Retorna o cep do imóvel
	 * 
	 * @param imovel
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 */

	public Cep pesquisarCepImovel(Imovel imovel)

	throws ErroRepositorioException{

		Cep retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta;

		try{

			consulta = "select cep " + "from Imovel imov " + "inner join imov.logradouroCep.cep cep " + "where imov.id = :imovelId ";

			retorno = (Cep) session.createQuery(consulta).setInteger("imovelId", imovel.getId().intValue()).setMaxResults(1).uniqueResult();

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
	 * < <Descrição do método>>
	 * 
	 * @author eduardo henrique
	 * @date 03/09/2009
	 *       Alteracao na consulta para busca da CategoriaTipo
	 * @param imovel
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */

	public Collection pesquisarObterQuantidadeEconomiasCategoria(Integer imovel, boolean construirObjetoCompleto)

	throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		SQLQuery sqlQuery = null;

		try{
			
			if(construirObjetoCompleto) {
				
				consulta.append("select c.catg_id, c.catg_dscategoria, c.catg_nnconsumoestouro, ");
				// 0,1,2,
				
				consulta.append("c.catg_nnvezesmediaestouro, sum(isb.imsb_qteconomia) as qteconomia, ");
				// 3,4
				
				consulta.append("isb.imov_id, ");
				// 5
				
				consulta.append("c.catg_nnconsumoalto, ");
				// 6
				
				consulta.append("c.catg_nnmediabaixoconsumo, ");
				// 7
				
				consulta.append("c.catg_nnvezesmediaaltoconsumo, ");
				// 8
				
				consulta.append("c.catg_pcmediabaixoconsumo, ");
				// 9
				
				consulta.append("c.catg_dsabreviado, ");
				// 10
				
				consulta.append("c.catg_nnconsumomaximoec, ");
				// 11
				
				consulta.append("c.catg_iccobrancaacrescimos, ");
				// 12
				
				consulta.append("ctipo.cgtp_id, ");
				// 13
				
				consulta.append("c.catg_nnconsumomedioecomes, ");
				// 14
				
				consulta.append("c.catg_nnconsumominimo, ");
				// 15
				
				consulta.append("c.catg_nnmaxeconomiasvalidacao, ");
				// 16
				
				consulta.append("c.catg_nnconsumoviradahd, ");
				// 17
				
				consulta.append("c.catg_nnvezesmediaviradahd, ");
				// 18
				
				consulta.append("c.catg_icvalidanormalviradahd ");
				// 19
				
				consulta.append("from imovel_subcategoria isb ");
				consulta.append("inner join subcategoria sb on sb.scat_id = isb.scat_id ");
				consulta.append("inner join categoria c on c.catg_id = sb.catg_id ");
				consulta.append("left join categoria_tipo ctipo on ctipo.cgtp_id = c.cgtp_id ");
				consulta.append("where isb.imov_id = :imovelId ");
				consulta.append("group by c.catg_id, c.catg_dscategoria, c.catg_nnconsumoestouro, ");
				consulta.append("c.catg_nnvezesmediaestouro, isb.imov_id, c.catg_nnconsumoalto, c.catg_nnmediabaixoconsumo, ");
				consulta.append("c.catg_nnvezesmediaaltoconsumo, c.catg_pcmediabaixoconsumo, c.catg_dsabreviado, ");
				consulta.append("c.catg_nnconsumomaximoec, c.catg_iccobrancaacrescimos, ctipo.cgtp_id, c.catg_nnconsumomedioecomes, ");
				consulta.append("c.catg_nnconsumominimo, c.catg_nnmaxeconomiasvalidacao, c.catg_nnconsumoviradahd, ");
				consulta.append("c.catg_nnvezesmediaviradahd, c.catg_icvalidanormalviradahd order by c.catg_id ");
				
				sqlQuery = session.createSQLQuery(consulta.toString()).addScalar("catg_id", Hibernate.INTEGER) //
								.addScalar("catg_dscategoria", Hibernate.STRING) //
								.addScalar("catg_nnconsumoestouro", Hibernate.INTEGER) //
								.addScalar("catg_nnvezesmediaestouro", Hibernate.BIG_DECIMAL) //
								.addScalar("qteconomia", Hibernate.INTEGER) //
								.addScalar("imov_id", Hibernate.INTEGER) //
								.addScalar("catg_nnconsumoalto", Hibernate.INTEGER) //
								.addScalar("catg_nnmediabaixoconsumo", Hibernate.INTEGER) //
								.addScalar("catg_nnvezesmediaaltoconsumo", Hibernate.BIG_DECIMAL) //
								.addScalar("catg_pcmediabaixoconsumo", Hibernate.BIG_DECIMAL) //
								.addScalar("catg_dsabreviado", Hibernate.STRING) //
								.addScalar("catg_nnconsumomaximoec", Hibernate.INTEGER) //
								.addScalar("catg_iccobrancaacrescimos", Hibernate.SHORT) //
								.addScalar("cgtp_id", Hibernate.INTEGER) //
								.addScalar("catg_nnconsumomedioecomes", Hibernate.INTEGER) //
								.addScalar("catg_nnconsumominimo", Hibernate.INTEGER) //
								.addScalar("catg_nnmaxeconomiasvalidacao", Hibernate.INTEGER) //
								.addScalar("catg_nnconsumoviradahd", Hibernate.INTEGER) //
								.addScalar("catg_nnvezesmediaviradahd", Hibernate.INTEGER) //
								.addScalar("catg_icvalidanormalviradahd", Hibernate.SHORT);
				
			}else{
				
				consulta.append("select isb.catg_id, sum(isb.imsb_qteconomia) as qteconomia ");
				consulta.append("from imovel_subcategoria isb ");
				consulta.append("where isb.imov_id = :imovelId ");
				consulta.append("group by isb.catg_id ");
				consulta.append("order by isb.catg_id");

				sqlQuery = session.createSQLQuery(consulta.toString()) //
								.addScalar("catg_id", Hibernate.INTEGER) //
								.addScalar("qteconomia", Hibernate.INTEGER);
			}

			retorno = sqlQuery.setInteger("imovelId", imovel.intValue()).list();


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
	 * @author Saulo Lima
	 * @date 29/01/2009
	 *       Correção por motivo de performace: Removido o "having" e colocado o "where"
	 */
	public Collection obterQuantidadeEconomiasCategoria(Integer conta) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			consulta
							.append("select c.id, c.descricao, c.consumoEstouro, ")
							.append("c.vezesMediaEstouro, sum(isb.quantidadeEconomia), ")
							.append("isb.comp_id.conta.id, ")
							.append("c.consumoAlto, ")
							.append("c.mediaBaixoConsumo, ")
							.append("c.vezesMediaAltoConsumo, ")
							.append("c.porcentagemMediaBaixoConsumo, ")
							.append("c.descricaoAbreviada ")
							.append("from ContaCategoria isb ")
							.append("inner join isb.comp_id.conta sb ")
							.append("inner join isb.comp_id.categoria c ")
							.append("where isb.comp_id.conta.id = :contaId ")
							// + "inner join sb.categoria c "
							.append(
											"group by c.id, c.descricao, c.consumoEstouro, c.vezesMediaEstouro, isb.comp_id.conta.id, c.consumoAlto, ")
							.append("c.mediaBaixoConsumo, c.vezesMediaAltoConsumo, c.porcentagemMediaBaixoConsumo, c.descricaoAbreviada");
			// + " having isb.comp_id.conta.id = :contaId ";

			retorno = session.createQuery(consulta.toString()).setInteger("contaId", conta.intValue()).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Pesquisa uma coleção de imóveis com uma query especifica
	 * 
	 * @param idLocalidade
	 * @param idSetorComercial
	 * @param idQuadra
	 * @param lote
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 */
	public Collection pesquisarImovelParametrosClienteImovel(Integer idLocalidade, Integer idSetorComercial, Integer idQuadra, Short lote)
					throws ErroRepositorioException{

		// cria a coleção de retorno
		Collection retorno = null;

		// Query
		String consulta;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{
			// pesquisa a coleção de atividades e atribui a variável "retorno"
			consulta = "select new gcom.cadastro.imovel.Imovel(imovel.id, imovel.localidade, "
							+ "imovel.setorComercial, imovel.quadra, imovel.lote, imovel.subLote, "
							+ "imovel.faturamentoSituacaoTipo, imovel.cobrancaSituacao) "
							+ "from gcom.cadastro.imovel.Imovel imovel left join " + "imovel.faturamentoSituacaoTipo left join "
							+ "imovel.cobrancaSituacao ";

			if(idLocalidade != null){
				consulta = consulta + "where imovel.localidade.id = " + idLocalidade.intValue();
			}

			if(idSetorComercial != null && idLocalidade != null){
				consulta = consulta + " and imovel.setorComercial.id = " + idSetorComercial.intValue();
			}else if(idSetorComercial != null && idLocalidade == null){
				consulta = consulta + "where imovel.setorComercial.id = " + idSetorComercial.intValue();
			}

			if(idQuadra != null && idSetorComercial != null){
				consulta = consulta + " and imovel.quadra.id = " + idQuadra.intValue();
			}else if(idQuadra != null && idSetorComercial == null){
				consulta = consulta + "where imovel.quadra.id = " + idQuadra.intValue();
			}

			if(lote != null && idQuadra != null){
				consulta = consulta + " and imovel.lote = " + lote.intValue();
			}else if(lote != null && idQuadra == null){
				consulta = consulta + "where imovel.lote = " + lote.intValue();
			}

			retorno = session.createQuery(consulta).list();

			// erro no hibernate
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		// retorna a coleção de atividades pesquisada(s)
		return retorno;
	}

	/**
	 * <<Descrição do método>>
	 * 
	 * @param imovel
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 */
	public Collection<Imovel> pesquisarImovelParametrosClienteImovel(FiltroClienteImovel filtroClienteImovel)
					throws ErroRepositorioException{

		Collection<Imovel> retorno = null;
		Session session = HibernateUtil.getSession();

		try{

			retorno = new ArrayList(new CopyOnWriteArraySet<Imovel>(GeradorHQLCondicional.gerarCondicionalQuery(filtroClienteImovel,
							"gcom.cadastro.imovel.Imovel", "imovel",
							"from gcom.cadastro.imovel.Imovel imovel join imovel.medicaoHistoricos medicaoHistoricos", session).list()));

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Collection obterDescricoesCategoriaImovel(Imovel imovel)

	throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = "select isc.comp_id.subcategoria.categoria.id," + // 0

							"isc.comp_id.subcategoria.categoria.descricao," + // 1

							"isc.comp_id.subcategoria.categoria.descricaoAbreviada "// 2

							+ "from ImovelSubcategoria isc "

							+ "left join isc.comp_id.subcategoria.categoria "

							+ "where isc.comp_id.imovel.id = :idImovel";

			retorno = session.createQuery(consulta).setInteger("idImovel",

			imovel.getId().intValue()).list();

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
	 * [UC0164] - filtrar imovel outros criterios --- pesquisa
	 * 
	 * @author eduardo henrique
	 * @date 24/04/2008
	 *       obs : Código do método abaixo estava comentado.
	 */

	public Collection pesquisarImovelOutrosCriterios(FiltrarImovelOutrosCriteriosHelper filtrarImovelOutrosCriteriosHelper)

	throws ErroRepositorioException{

		Collection retorno = null;

		Collection idsImovel = null;

		String selectImovel = "";

		String condicionais = "";

		if(filtrarImovelOutrosCriteriosHelper.getTipoRelatorio().trim()

		.equalsIgnoreCase("RelatorioImoveis")){
			// a segunda coluna não será considerada

			selectImovel = "select distinct im.id, sc.id, lo.id, gr.id from ClienteImovel ci"

			+ " inner join ci.clienteRelacaoTipo crt"

			+ " inner join ci.imovel im"

			+ " inner join im.localidade lo"

			+ " inner join lo.gerenciaRegional gr"

			+ " inner join im.setorComercial sc"

			+ " inner join im.quadra qu"

			+ " left join sc.municipio mu"

			+ " left join qu.bairro ba"

			+ " left join im.cep cep"

			+ " left join im.logradouro lg"

			+ " inner join ci.cliente cl"

			+ " left join cl.clienteTipo ct"

			+ " left join im.imovelCondominio ic"

			+ " left join im.imovelPrincipal ipri"

			+ " left join im.nomeConta nc"

			+ " left join im.ligacaoAguaSituacao las"

			+ " left join im.ligacaoEsgotoSituacao les"

			+ " left join im.imovelPerfil ip"

			+ " left join im.pocoTipo pt"

			+ " left join im.faturamentoSituacaoTipo ft"

			+ " left join im.cobrancaSituacaoTipo cst"

			+ " left join im.eloAnormalidade ea"

			+ " left join im.cadastroOcorrencia co"

			+ " left join im.areaConstruidaFaixa acf"

			+ " left join rota rotaImovel on rotaImovel.rota_id = im.rota_id "

			+ " left join im.consumoTarifa ct where ";

		}else if(filtrarImovelOutrosCriteriosHelper.getTipoRelatorio().trim()

		.equalsIgnoreCase("RelatorioEconomia")){
			// a segunda coluna não será considerada

			selectImovel = "select distinct im.id, sc.id, lo.id, gr.id from ClienteImovelEconomia ci"

			+ " inner join ci.clienteRelacaoTipo crt"

			+ " inner join ci.imovelEconomia.imovelSubcategoria.comp_id.imovel im"

			+ " inner join im.localidade lo"

			+ " inner join lo.gerenciaRegional gr"

			+ " inner join im.setorComercial sc"

			+ " inner join im.quadra qu"

			+ " inner join sc.municipio mu"

			+ " inner join qu.bairro ba"

			+ " left join im.cep cep"

			+ " left join im.logradouro lg"

			+ " inner join ci.cliente cl"

			+ " inner join cl.clienteTipo ct"

			+ " left join im.imovelCondominio ic"

			+ " left join im.imovelPrincipal ipri"

			+ " left join im.nomeConta nc"

			+ " inner join im.ligacaoAguaSituacao las"

			+ " inner join im.ligacaoEsgotoSituacao les"

			+ " inner join im.imovelPerfil ip"

			+ " inner join im.pocoTipo pt"

			+ " left join im.faturamentoSituacaoTipo ft"

			+ " left join im.cobrancaSituacaoTipo cst"

			+ " left join im.eloAnormalidade ea"

			+ " left join im.cadastroOcorrencia co"

			+ " left join im.areaConstruidaFaixa acf"

			+ " left join rota rotaImovel on rotaImovel.rota_id = im.rota_id "

			+ " left join im.consumoTarifa ct where ";

		}else if(filtrarImovelOutrosCriteriosHelper.getTipoRelatorio().trim()

		.equalsIgnoreCase("RelatorioTarifaSocial")

		|| filtrarImovelOutrosCriteriosHelper.getTipoRelatorio().trim()

		.equalsIgnoreCase("consultarTarifaExcluida")){

			selectImovel = "select distinct im.id, sc.id, lo.id, gr.id from TarifaSocialDadoEconomia tsde"

			+ " left join tsde.imovelEconomia ie"

			+ " inner join tsde.imovel im"

			+ " inner join im.localidade lo"

			+ " inner join lo.gerenciaRegional gr"

			+ " inner join im.setorComercial sc"

			+ " inner join im.quadra qu"

			+ " inner join sc.municipio mu"

			+ " inner join im.ligacaoAguaSituacao las"

			+ " inner join im.ligacaoEsgotoSituacao les"

			+ " inner join im.imovelPerfil ip"

			+ " inner join im.pocoTipo pt"

			+ " left join im.logradouroCep.logradouro lg"

			+ " left join im.logradouroCep.cep cep "

			+ " left join im.logradouroBairro.bairro ba "

			+ " left join im.imovelCondominio ic"

			+ " left join im.imovelPrincipal ipri"

			+ " left join im.faturamentoSituacaoTipo ft"

			+ " left join im.cobrancaSituacaoTipo cst"

			+ " left join im.eloAnormalidade ea"

			+ " left join im.cadastroOcorrencia co"

			+ " left join im.areaConstruidaFaixa acf"

			+ " left join im.rota rotaImovel "

			+ " left join im.consumoTarifa ct";

		}else if(filtrarImovelOutrosCriteriosHelper.getTipoRelatorio().trim()

		.equalsIgnoreCase("GerarRelacaoDebito")){
			// a segunda coluna não será considerada

			selectImovel = "select distinct im.id, lo.id, -1, -1 from ClienteImovel ci"

			+ " inner join ci.imovel im"

			+ " left join im.rota rotaImovel "

			+ " inner join im.localidade l";
		}

		condicionais = this.criaCondicionaisHqlImovelOutrosCriterios(filtrarImovelOutrosCriteriosHelper);

		String hqlConcatenado = selectImovel;
		String hqlFinal = hqlConcatenado;
		if(!condicionais.equals("")){
			hqlConcatenado = hqlConcatenado + " where " + condicionais;
			hqlFinal = Util.formatarHQL(hqlConcatenado, 4);
		}
		hqlFinal += " order by 4,3,2,1";

		Session session = HibernateUtil.getSession();

		try{

			idsImovel = session.createQuery(hqlFinal).list();

			if(filtrarImovelOutrosCriteriosHelper.getTipoRelatorio().trim().equalsIgnoreCase("RelatorioTarifaSocial")
							|| filtrarImovelOutrosCriteriosHelper.getTipoRelatorio().trim().equalsIgnoreCase("consultarTarifaExcluida")){

				if(!idsImovel.isEmpty()){
					retorno = iterarPesquisaIdsImoveisTarifaSocial(idsImovel);
				}
			}
		}catch(HibernateException e){ // levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;
	}

	// filtro outros criterios - carregamento de tarifa social
	public Collection iterarPesquisaIdsImoveisTarifaSocial(Collection idsImovel) throws ErroRepositorioException{

		Collection retorno = new ArrayList();

		Object[] arrayImoveis = null;

		Iterator iteratorImoveis = idsImovel.iterator();

		Session session = HibernateUtil.getSession();

		try{

			while(iteratorImoveis.hasNext()){

				arrayImoveis = (Object[]) iteratorImoveis.next();

				Integer idImovel = (Integer) arrayImoveis[0];

				// ---- referente a imovel

				String consultaImovel = "select" + " gr.id," // 0

								+ " gr.nome,"// 1

								+ " lo.id,"// 2

								+ " lo.descricao,"// 3

								+ " sc.codigo," // 4

								+ " sc.descricao,"// 5

								+ " im.id," // 6

								+ " mu.id," // 7

								+ " mu.nome," // 8

								// ---------Campos de Bairro

								+ " ba.codigo," // 9

								+ " ba.nome," // 10

								// ---------Campos de logradouro

								+ " lg.nome," // 11

								+ " lgtitulo.descricaoAbreviada," // 12

								+ " lgtipo.descricaoAbreviada," // 13

								// ---------Campos de quadra

								+ " qu.numeroQuadra," // 14

								// ---------Campos de cep

								+ " cep.codigo," // 15

								+ " cep.logradouro," // 16

								+ " cep.bairro," // 17

								+ " cep.municipio,"// 18

								+ " cep.descricaoTipoLogradouro,"// 19

								+ " cep.sigla,"// 20

								+ " tsde.dataImplantacao,"// 21

								+ " tsde.dataExclusao,"// 22

								+ " tsme.descricao," // 23

								+ " tsde.dataRecadastramento,"// 24

								+ " tsde.quantidadeRecadastramento"// 25

								+ " from TarifaSocialDadoEconomia tsde "

								+ " inner join tsde.imovel im"

								+ " left join im.localidade lo"

								+ " left join im.setorComercial sc"

								+ " left join im.quadra qu"

								+ " left join im.logradouroCep.logradouro lg"

								+ " left join sc.municipio mu"

								+ " left join im.logradouroCep.cep cep "

								+ " left join im.logradouroBairro.bairro ba "

								+ " left join lg.logradouroTipo lgtipo"

								+ " left join lg.logradouroTitulo lgtitulo"

								+ " left join lo.gerenciaRegional gr"

								+ " left join tsde.tarifaSocialExclusaoMotivo tsme"

								+ " where im.id = " + idImovel;

				// ------------tarifa social dados economia

				String consultaTarifaEconomia = "select"

				+ " tsde.numeroCartaoProgramaSocial," // 0

								+ " tsct.descricao,"// 1

								+ " tsde.dataValidadeCartao,"// 2

								+ " tsde.numeroMesesAdesao,"// 3

								+ " tsde.consumoCelpe," // 4

								+ " tsde.valorRendaFamiliar,"// 5

								+ " rt.descricao," // 6

								+ " ie.areaConstruida,"// 7

								+ " ie.numeroIptu," // 8

								+ " ie.numeroCelpe,"// 9

								+ " tsde.quantidadeRecadastramento" // 10

								+ " from TarifaSocialDadoEconomia tsde"

								+ " left join tsde.tarifaSocialCartaoTipo tsct"

								+ " left join tsde.rendaTipo rt"

								+ " left join tsde.imovelEconomia ie"

								+ " left join ie.imovelSubcategoria isub"

								+ " where tsde.imovel = " + idImovel;

				// ------------Cliente Imovel

				String consultaClienteImovel = "select" + " cl.nome," // 0

								+ " cl.cpf," // 1

								+ " rt.descricao," // 2

								+ " cl.rg," // 3

								+ " cl.dataEmissaoRg," // 4

								+ " org.descricaoAbreviada," // 5

								+ " uf.sigla" // 6

								+ " from ClienteImovel ci" + " inner join ci.imovel im"

								+ " inner join ci.cliente cl"

								+ " inner join ci.clienteRelacaoTipo rt"

								+ " left join cl.orgaoExpedidorRg org"

								+ " left join cl.unidadeFederacao uf"

								+ " where im.id =" + idImovel;

				Collection colecaoImovel = session.createQuery(consultaImovel).list();

				Collection colecaoTarifaEconomia = session.createQuery(consultaTarifaEconomia).list();

				Collection colecaoClienteImovel = session.createQuery(consultaClienteImovel).list();

				ImovelRelatorioHelper imovelRelatorioHelper = new ImovelRelatorioHelper();

				if(!colecaoImovel.isEmpty()){
					Object[] arrayImovel = (Object[]) colecaoImovel.iterator().next();

					imovelRelatorioHelper.setIdGerenciaRegional(arrayImovel[0] != null
									&& !arrayImovel[0].toString().trim().equalsIgnoreCase("") ? (Integer) arrayImovel[0] : null);

					imovelRelatorioHelper.setDescricaoGerenciaRegional(arrayImovel[1] != null
									&& !arrayImovel[1].toString().trim().equalsIgnoreCase("") ? (String) arrayImovel[1] : null);

					imovelRelatorioHelper
									.setIdLocalidade(arrayImovel[2] != null && !arrayImovel[2].toString().trim().equalsIgnoreCase("") ? (Integer) arrayImovel[2]
													: null);

					imovelRelatorioHelper.setDescricaoLocalidade(arrayImovel[3] != null
									&& !arrayImovel[3].toString().trim().equalsIgnoreCase("") ? (String) arrayImovel[3] : null);

					imovelRelatorioHelper.setCodigoSetorComercial(arrayImovel[4] != null
									&& !arrayImovel[4].toString().trim().equalsIgnoreCase("") ? (Integer) arrayImovel[4] : null);

					imovelRelatorioHelper.setDescricaoSetorComercial(arrayImovel[5] != null
									&& !arrayImovel[5].toString().trim().equalsIgnoreCase("") ? (String) arrayImovel[5] : null);

					imovelRelatorioHelper.setMatriculaImovel(arrayImovel[6] != null
									&& !arrayImovel[6].toString().trim().equalsIgnoreCase("") ? (Integer) arrayImovel[6] : null);

					imovelRelatorioHelper
									.setIdMunicipio(arrayImovel[7] != null && !arrayImovel[7].toString().trim().equalsIgnoreCase("") ? (Integer) arrayImovel[7]
													: null);

					imovelRelatorioHelper
									.setNomeMunicipio(arrayImovel[8] != null && !arrayImovel[8].toString().trim().equalsIgnoreCase("") ? (String) arrayImovel[8]
													: null);

					imovelRelatorioHelper
									.setIdBairro(arrayImovel[9] != null && !arrayImovel[9].toString().trim().equalsIgnoreCase("") ? (Integer) arrayImovel[9]
													: null);

					imovelRelatorioHelper
									.setNomeBairro(arrayImovel[10] != null && !arrayImovel[10].toString().trim().equalsIgnoreCase("") ? (String) arrayImovel[10]
													: null);

					imovelRelatorioHelper.setNomeLogradouro(arrayImovel[11] != null
									&& !arrayImovel[11].toString().trim().equalsIgnoreCase("") ? (String) arrayImovel[11] : null);

					imovelRelatorioHelper.setTituloLogradouro(arrayImovel[12] != null
									&& !arrayImovel[12].toString().trim().equalsIgnoreCase("") ? (String) arrayImovel[12] : null);

					imovelRelatorioHelper.setTipoLogradouro(arrayImovel[13] != null
									&& !arrayImovel[13].toString().trim().equalsIgnoreCase("") ? (String) arrayImovel[13] : null);

					imovelRelatorioHelper.setNumeroQuadra(arrayImovel[14] != null
									&& !arrayImovel[14].toString().trim().equalsIgnoreCase("") ? (Integer) arrayImovel[14] : null);

					imovelRelatorioHelper
									.setCepCodigo(arrayImovel[15] != null && !arrayImovel[15].toString().trim().equalsIgnoreCase("") ? (Integer) arrayImovel[15]
													: null);

					imovelRelatorioHelper.setCepLogradouro(arrayImovel[16] != null
									&& !arrayImovel[16].toString().trim().equalsIgnoreCase("") ? (String) arrayImovel[16] : null);

					imovelRelatorioHelper
									.setCepBairro(arrayImovel[17] != null && !arrayImovel[17].toString().trim().equalsIgnoreCase("") ? (String) arrayImovel[17]
													: null);

					imovelRelatorioHelper.setCepMunicipio(arrayImovel[18] != null
									&& !arrayImovel[18].toString().trim().equalsIgnoreCase("") ? (String) arrayImovel[18] : null);

					imovelRelatorioHelper.setCepTipoLogradouro(arrayImovel[19] != null
									&& !arrayImovel[19].toString().trim().equalsIgnoreCase("") ? (String) arrayImovel[19] : null);

					imovelRelatorioHelper
									.setCepSigla(arrayImovel[20] != null && !arrayImovel[20].toString().trim().equalsIgnoreCase("") ? (String) arrayImovel[20]
													: null);

					imovelRelatorioHelper.setDataImplantacao(arrayImovel[21] != null
									&& !arrayImovel[21].toString().trim().equalsIgnoreCase("") ? (Date) arrayImovel[21] : null);

					imovelRelatorioHelper.setDataExclusao(arrayImovel[22] != null
									&& !arrayImovel[22].toString().trim().equalsIgnoreCase("") ? (Date) arrayImovel[22] : null);

					imovelRelatorioHelper.setMotivoExclusao(arrayImovel[23] != null
									&& !arrayImovel[23].toString().trim().equalsIgnoreCase("") ? (String) arrayImovel[23] : null);

					imovelRelatorioHelper.setUltimoRecadastramento(arrayImovel[24] != null
									&& !arrayImovel[24].toString().trim().equalsIgnoreCase("") ? (Date) arrayImovel[24] : null);

					imovelRelatorioHelper
									.setNumeroRecadastramento(arrayImovel[25] != null
													&& !arrayImovel[25].toString().trim().equalsIgnoreCase("") ? ((Short) arrayImovel[25])
													.shortValue() : 0);

				}

				if(!colecaoTarifaEconomia.isEmpty()){

					Object[] arrayTarifaEconomia = null;

					Iterator iterator = colecaoTarifaEconomia.iterator();

					ImovelRelatorioHelper imovelRelatorioHelperTarifa = null;

					Collection tarifasEconomias = new ArrayList();

					while(iterator.hasNext()){

						arrayTarifaEconomia = (Object[]) iterator.next();

						imovelRelatorioHelperTarifa = new ImovelRelatorioHelper();

						imovelRelatorioHelperTarifa.setNumeroCartaoTarifaSocial(arrayTarifaEconomia[0] != null
										&& !arrayTarifaEconomia[0].toString().trim().equalsIgnoreCase("") ? (Long) arrayTarifaEconomia[0]
										: null);

						imovelRelatorioHelperTarifa.setTipoCartaoTarifaSocial(arrayTarifaEconomia[1] != null
										&& !arrayTarifaEconomia[1].toString().trim().equalsIgnoreCase("") ? (String) arrayTarifaEconomia[1]
										: null);

						imovelRelatorioHelperTarifa.setValidadeCartao(arrayTarifaEconomia[2] != null
										&& !arrayTarifaEconomia[2].toString().trim().equalsIgnoreCase("") ? (Date) arrayTarifaEconomia[2]
										: null);

						imovelRelatorioHelperTarifa
										.setNumeroMesesAdesao(arrayTarifaEconomia[3] != null
														&& !arrayTarifaEconomia[3].toString().trim().equalsIgnoreCase("") ? ((Short) arrayTarifaEconomia[3])
														.shortValue()
														: 0);

						imovelRelatorioHelperTarifa
										.setConsumoCelpe(arrayTarifaEconomia[4] != null
														&& !arrayTarifaEconomia[4].toString().trim().equalsIgnoreCase("") ? (Integer) arrayTarifaEconomia[4]
														: null);

						imovelRelatorioHelperTarifa
										.setValorRendaFamiliar(arrayTarifaEconomia[5] != null
														&& !arrayTarifaEconomia[5].toString().trim().equalsIgnoreCase("") ? (BigDecimal) arrayTarifaEconomia[5]
														: null);

						imovelRelatorioHelperTarifa.setRendaTipo(arrayTarifaEconomia[6] != null
										&& !arrayTarifaEconomia[6].toString().trim().equalsIgnoreCase("") ? (String) arrayTarifaEconomia[6]
										: null);

						imovelRelatorioHelperTarifa.setAreaConstruida(arrayTarifaEconomia[7] != null
										&& !arrayTarifaEconomia[7].toString().trim().equalsIgnoreCase("") ? new BigDecimal(
										arrayTarifaEconomia[7].toString()).shortValue() : 0);

						imovelRelatorioHelperTarifa
										.setNumeroIptu(arrayTarifaEconomia[8] != null
														&& !arrayTarifaEconomia[8].toString().trim().equalsIgnoreCase("") ? (BigDecimal) arrayTarifaEconomia[8]
														: null);

						imovelRelatorioHelperTarifa.setNumeroCelpe(arrayTarifaEconomia[9] != null
										&& !arrayTarifaEconomia[9].toString().trim().equalsIgnoreCase("") ? (Long) arrayTarifaEconomia[9]
										: null);

						imovelRelatorioHelperTarifa
										.setNumeroRecadastramento(arrayTarifaEconomia[10] != null
														&& !arrayTarifaEconomia[10].toString().trim().equalsIgnoreCase("") ? ((Short) arrayTarifaEconomia[10])
														.shortValue()
														: 0);

						tarifasEconomias.add(imovelRelatorioHelperTarifa);

					}

					imovelRelatorioHelper.setTarifasEconomias(tarifasEconomias);

				}

				if(!colecaoClienteImovel.isEmpty()){

					Object[] arrayCliente = null;

					Iterator iteratorCliente = colecaoClienteImovel.iterator();

					ImovelRelatorioHelper imovelRelatorioHelperCliente = null;

					Collection clientes = new ArrayList();

					while(iteratorCliente.hasNext()){

						arrayCliente = (Object[]) iteratorCliente.next();

						imovelRelatorioHelperCliente = new ImovelRelatorioHelper();

						imovelRelatorioHelperCliente.setClienteNome(arrayCliente[0] != null
										&& !arrayCliente[0].toString().trim().equalsIgnoreCase("") ? (String) arrayCliente[0] : null);

						imovelRelatorioHelperCliente.setClienteCpf(arrayCliente[1] != null
										&& !arrayCliente[1].toString().trim().equalsIgnoreCase("") ? (String) arrayCliente[1] : null);

						imovelRelatorioHelperCliente.setClienteRelacaoTipo(arrayCliente[2] != null
										&& !arrayCliente[2].toString().trim().equalsIgnoreCase("") ? (String) arrayCliente[2] : null);

						imovelRelatorioHelperCliente.setClienteRg(arrayCliente[3] != null
										&& !arrayCliente[3].toString().trim().equalsIgnoreCase("") ? (String) arrayCliente[3] : null);

						imovelRelatorioHelperCliente.setClienteDataEmissaoOrgaoRg(arrayCliente[4] != null
										&& !arrayCliente[4].toString().trim().equalsIgnoreCase("") ? (Date) arrayCliente[4] : null);

						imovelRelatorioHelperCliente.setClienteEmissaoOrgaoRg(arrayCliente[5] != null
										&& !arrayCliente[5].toString().trim().equalsIgnoreCase("") ? (String) arrayCliente[5] : null);

						imovelRelatorioHelperCliente.setClienteUf(arrayCliente[6] != null
										&& !arrayCliente[6].toString().trim().equalsIgnoreCase("") ? (String) arrayCliente[6] : null);

						clientes.add(imovelRelatorioHelperCliente);

					}

					imovelRelatorioHelper.setClientes(clientes);

				}

				retorno.add(imovelRelatorioHelper);

			}

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
	 * Metodo para continuar a pesquisa utilizando os ids de imoveis filtrados
	 */

	public Collection interarPesquisarIdsImoveis(Collection idsImovel)

	throws ErroRepositorioException{

		Collection colecaoImovelRelatorioHelper = new ArrayList();

		Collection colecaoligacaoAgua = new ArrayList();

		Collection colecaoligacaoEsgoto = new ArrayList();

		Collection colecaoConsumoHistorico = new ArrayList();

		Collection colecaoImovel = new ArrayList();

		Collection colecaoSubcategoria = new ArrayList();

		Collection colecaoClienteUsuarioResponsavel = new ArrayList();

		Iterator iterator = idsImovel.iterator();

		String idImovel = "";

		Session session = HibernateUtil.getSession();

		Integer arrayImovel = null;

		try{

			while(iterator.hasNext()){

				arrayImovel = (Integer) iterator.next();

				idImovel = arrayImovel.toString();

				// ------------------Referente a Imovel

				String consultaImovel = "select"

				// ------Campos de imovel

								+ " im.id," // 0

								+ " im.lote," // 1

								+ " im.subLote," // 2

								+ " im.volumeReservatorioSuperior," // 3

								+ " im.volumeReservatorioInferior," // 4

								+ " im.volumePiscina," // 5

								+ " im.numeroMorador," // 6

								+ " im.numeroPontosUtilizacao," // 7

								+ " im.indicadorImovelCondominio," // 8

								+ " im.areaConstruida," // 9

								// ---------Campos de imovel Principal

								+ " ipri.id," // 10

								// ---------Campos de imovel Condominio

								+ " ic.id," // 11

								// ---------Campos de Cliente

								+ " cl.id," // 12

								+ " cl.nome," // 13

								// ---------Campos de Cliente relacao tipo

								+ " crt.descricao," // 14

								// ---------Campos localidade

								+ " lo.id," // 15

								+ " lo.descricao," // 16

								// ---------Campos Setor comercial

								+ " sc.codigo," // 17

								+ " sc.descricao," // 18

								// ---------Campos de Municipio

								+ " mu.id," // 19

								+ " mu.nome," // 20

								// ---------Campos de Bairro

								+ " ba.codigo," // 21

								+ " ba.nome," // 22

								// ---------Campos de logradouro

								+ " lg.nome," // 23

								+ " lgtitulo.descricaoAbreviada," // 24

								+ " lgtipo.descricaoAbreviada," // 25

								// ---------Campos de quadra

								+ " qu.numeroQuadra," // 26

								// ---------Campos de cep

								+ " cep.codigo," // 27

								+ " cep.logradouro," // 28

								+ " cep.bairro," // 29

								+ " cep.municipio," // 30

								+ " cep.descricaoTipoLogradouro," // 31

								+ " cep.sigla," // 32

								// ---------Campos de ligacao agua situacao

								+ " las.descricao," // 33

								// ---------Campos de ligacao esgoto situacao

								+ " les.descricao," // 34

								// ---------Campos de pavimento calcada

								+ " pavc.descricao," // 35

								// ---------Campos de pavimento rua

								+ " pavr.descricao," // 36

								// ---------Campos de despejo

								+ "desp.descricaoAbreviada," // 37

								// ---------Campos de poço tipo

								+ " pt.descricao," // 38

								// ---------Campos Hidrometro Poco(hidrometro historico

								// imovel)

								+ " hid.numero," // 39

								+ " hid.anoFabricacao," // 40

								+ " hic.descricaoAbreviada," // 41

								+ " him.descricaoAbreviada," // 42

								+ " hidiametro.descricaoAbreviada," // 43

								+ " hit.descricaoAbreviada," // 44

								+ " hih.dataInstalacao," // 45

								+ " hli.descricaoAbreviada," // 46

								+ " hip.descricaoAbreviada," // 47

								+ " hih.indicadorExistenciaCavalete," // 48

								+ " gr.nomeAbreviado,"// 49

								+ " gr.id"// 50

								// ---------Continua select

								+ " from ClienteImovel ci"

								+ " inner join ci.clienteRelacaoTipo crt"

								+ " inner join ci.imovel im"

								+ " inner join im.localidade lo"

								+ " inner join im.setorComercial sc"

								+ " inner join im.quadra qu"

								+ " left outer join im.logradouroBairro logradouroBairro "

								+ " left outer join logradouroBairro.bairro ba "

								+ " left outer join ba.municipio mu "

								// + " left join sc.municipio mu"

								// + " inner join qu.bairro ba"

								+ " left join im.pavimentoCalcada pavc"

								+ " left join im.pavimentoRua pavr"

								+ " left join im.despejo desp"

								+ " inner join lo.gerenciaRegional gr"

								+ " left  join im.logradouroCep logradouroCep "

								+ " left  join logradouroCep.cep cep "

								+ " left  join logradouroCep.logradouro lg "

								// + " left join im.cep cep"

								// + " left join im.logradouro lg"

								+ " left join lg.logradouroTipo lgtipo"

								+ " left join lg.logradouroTitulo lgtitulo"

								+ " inner join ci.cliente cl"

								+ " left join cl.clienteTipo ct"

								+ " left join im.imovelCondominio ic"

								+ " left join im.imovelPrincipal ipri"

								// + " left join im.nomeConta nc"

								+ " left join im.ligacaoAguaSituacao las"

								+ " left join im.ligacaoEsgotoSituacao les"

								+ " left join im.imovelPerfil ip"

								+ " left join im.pocoTipo pt"

								+ " left join im.faturamentoSituacaoTipo ft"

								+ " left join im.cobrancaSituacaoTipo cst"

								+ " left join im.eloAnormalidade ea"

								+ " left join im.cadastroOcorrencia co"

								+ " left join im.areaConstruidaFaixa acf"

								+ " left join im.hidrometroInstalacaoHistorico hih"

								+ " left join hih.hidrometro hid"

								+ " left join hid.hidrometroCapacidade hic"

								+ " left join hid.hidrometroMarca him"

								+ " left join hid.hidrometroDiametro hidiametro"

								+ " left join hid.hidrometroTipo hit"

								+ " left join hih.hidrometroLocalInstalacao hli"

								+ " left join hih.hidrometroProtecao hip"

								+ " left join im.consumoTarifa ct where im.id = "

								+ idImovel;

				// ------------Referente a consumo historico

				String consultaConsumoHistorico = "select"

				+ " ch.consumoMedio," // 0

								+ " im.id"// 1

								+ " from ConsumoHistorico ch"

								+ " inner join ch.imovel im" + " where im.id = "

								+ idImovel;

				// ------------Referente a ligacao agua

				String consultaLigacaoAgua = "select" + " la.dataLigacao," // 0

								+ " lad.descricao," // 1

								+ " lam.descricao," // 2

								+ " la.numeroConsumoMinimoAgua," // 3

								// --------Campos Hidrometro Instalacao

								+ " hid.numero," // 0

								+ " hid.anoFabricacao," // 1

								+ " hic.descricaoAbreviada," // 2

								+ " him.descricaoAbreviada," // 3

								+ " hidiametro.descricaoAbreviada," // 4

								+ " hit.descricaoAbreviada," // 5

								+ " hih.dataInstalacao," // 6

								+ " hli.descricaoAbreviada," // 7

								+ " hip.descricaoAbreviada," // 8

								+ " hih.indicadorExistenciaCavalete" // 9

								// --------Continuação select

								+ " from LigacaoAgua la"

								+ " left join la.hidrometroInstalacaoHistorico hih"

								+ " left join hih.hidrometro hid"

								+ " left join hid.hidrometroCapacidade hic"

								+ " left join hid.hidrometroMarca him"

								+ " left join hid.hidrometroDiametro hidiametro"

								+ " left join hid.hidrometroTipo hit"

								+ " left join hih.hidrometroLocalInstalacao hli"

								+ " left join hih.hidrometroProtecao hip"

								+ " left join la.ligacaoAguaDiametro lad"

								+ " left join la.ligacaoAguaMaterial lam"

								+ " where la.id = " + idImovel;

				// ------------Referente a ligacao esgoto

				String consultaLigacaoEsgoto = "select" + " le.dataLigacao," // 0

								+ " led.descricao," // 1

								+ " lem.descricao," // 2

								+ " le.percentualAguaConsumidaColetada," // 3

								+ " le.percentual," // 4

								+ " le.consumoMinimo" // 5

								+ " from LigacaoEsgoto le"

								+ " left join le.ligacaoEsgotoDiametro led"

								+ " left join le.ligacaoEsgotoMaterial lem"

								+ " where le.id = " + idImovel;

				// --------------Referente a subcategorias

				String consultaSubcategoria = "select" + " sub.descricao,"

				+ " isc.quantidadeEconomias"

				+ " from ImovelSubcategoria isc"

				+ " inner join isc.comp_id.imovel im"

				+ " inner join isc.comp_id.subcategoria sub"

				+ " where im.id = " + idImovel;

				// --- Clientes Usuario e responsavel

				String consultaClienteUsuarioResponsavel = "select" + " cl.id,"// 0

								+ " cl.nome,"// 1

								+ " crt.id"// 2

								+ " from ClienteImovel ci"

								+ " inner join ci.cliente cl"

								+ " inner join ci.clienteRelacaoTipo crt"

								+ " inner join ci.imovel im" + " where im.id = "

								+ idImovel + " and (crt.id = "

								+ ClienteRelacaoTipo.USUARIO + " or crt.id = "

								+ ClienteRelacaoTipo.RESPONSAVEL + ")" + " and im.id ="

								+ idImovel;

				// ----preenche colecao imovel

				colecaoImovel = session.createQuery(consultaImovel).list();

				// ----preenche colecao ligacao agua

				colecaoligacaoAgua = session.createQuery(consultaLigacaoAgua)

				.list();

				// ----preenche colecao ligacao esgoto

				colecaoligacaoEsgoto = session.createQuery(

				consultaLigacaoEsgoto).list();

				// ----preenche colecao consumo historico

				colecaoConsumoHistorico = session.createQuery(

				consultaConsumoHistorico).list();

				// ----preenche colecao subcategorias

				colecaoSubcategoria = session.createQuery(consultaSubcategoria)

				.list();

				colecaoClienteUsuarioResponsavel = session.createQuery(

				consultaClienteUsuarioResponsavel).list();

				colecaoImovelRelatorioHelper.add(carregaIomvelRelatorioHelper(

				colecaoImovel, colecaoligacaoAgua,

				colecaoligacaoEsgoto, colecaoConsumoHistorico,

				colecaoSubcategoria, colecaoClienteUsuarioResponsavel));

			}

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

		return colecaoImovelRelatorioHelper;

	}

	// filtrar imovel outros criterios - carrega colecao de clientes

	public Collection popularClienteUsuarioResponsavel(

	Collection colecaoClienteUsuarioResponsavel){

		Object[] arrayCliente = null;

		ClienteImovel clienteImovel = null;

		Cliente cliente = null;

		ClienteRelacaoTipo clienteRelacaoTipo = null;

		Collection retorno = new ArrayList();

		Iterator iterator = colecaoClienteUsuarioResponsavel.iterator();

		while(!iterator.hasNext()){

			clienteImovel = new ClienteImovel();

			cliente = new Cliente();

			clienteRelacaoTipo = new ClienteRelacaoTipo();

			arrayCliente = (Object[]) iterator.next();

			cliente

			.setId(arrayCliente[0] != null

			&& !arrayCliente[0].toString().trim()

			.equalsIgnoreCase("") ? (Integer) arrayCliente[0]

			: null);

			cliente

			.setNome(arrayCliente[1] != null

			&& !arrayCliente[1].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayCliente[1]

			: null);

			clienteRelacaoTipo

			.setId(arrayCliente[2] != null

			&& !arrayCliente[2].toString().trim()

			.equalsIgnoreCase("") ? (Integer) arrayCliente[2]

			: null);

			clienteImovel.setCliente(cliente);

			clienteImovel.setClienteRelacaoTipo(clienteRelacaoTipo);

			retorno.add(clienteImovel);

		}

		return null;

	}

	// filtro imove outros criterios - carrega objetos interando as colecoes

	public Collection iterarPesquisaIdImoveisEconomias(Collection idImoveis)

	throws ErroRepositorioException{

		String idImovel = "";

		Collection retorno = new ArrayList();

		Iterator iterator = idImoveis.iterator();

		String consultaImovel = "";

		// String consultaClienteImovelEconomia = "";

		String consultaSubcategroias = "";

		String consultaImovelEconomia = "";

		Collection colecaoSubcategorias = null;

		Collection colecaoImovelEconomia = null;

		Collection colecaoImovel = null;

		// ---colecoes para os retornos dos metodos

		Collection retornoImovelEconomia = null;

		Collection retornoSubcategoria = new ArrayList();

		Object[] arrayImovel = null;

		Object[] arraySubcategorias = null;

		// Object[] arrayImovelEconomia = null;

		Session session = HibernateUtil.getSession();

		try{

			ImovelRelatorioHelper imovelRelatorioHelper = null;

			while(iterator.hasNext()){

				Integer idarrayImovel = (Integer) iterator.next();

				retornoSubcategoria = new ArrayList();

				idImovel = idarrayImovel.toString();

				consultaImovel = "select" + " im.id,"// 0

								+ " gr.nomeAbreviado,"// 1

								+ " lo.id,"// 2

								+ " lo.descricao,"// 3

								+ " sc.codigo,"// 4

								+ " sc.descricao,"// 5

								// ---------Campos de Municipio

								+ " mu.id," // 6

								+ " mu.nome," // 7

								// ---------Campos de Bairro

								+ " ba.codigo," // 8

								+ " ba.nome," // 9

								// ---------Campos de logradouro

								+ " lg.nome," // 10

								+ " lgtitulo.descricaoAbreviada," // 11

								+ " lgtipo.descricaoAbreviada," // 12

								// ---------Campos de quadra

								+ " qu.numeroQuadra," // 13

								// ---------Campos de cep

								+ " cep.codigo," // 14

								+ " cep.logradouro," // 15

								+ " cep.bairro," // 16

								+ " cep.municipio," // 17

								+ " cep.descricaoTipoLogradouro," // 18

								+ " cep.sigla" // 19

								+ " from Imovel im" + " inner join im.localidade lo"

								+ " inner join im.setorComercial sc"

								+ " inner join im.quadra qu"

								+ " inner join sc.municipio mu"

								+ " inner join qu.bairro ba"

								+ " left join im.logradouro lg"

								+ " left join lg.logradouroTipo lgtipo"

								+ " left join lg.logradouroTitulo lgtitulo"

								+ " left join im.cep cep"

								+ " inner join lo.gerenciaRegional gr"

								+ " where im.id = " + idImovel;

				// ----preenche imovel

				colecaoImovel = session.createQuery(consultaImovel).list();

				if(!colecaoImovel.isEmpty()){

					imovelRelatorioHelper = new ImovelRelatorioHelper();

					arrayImovel = (Object[]) colecaoImovel.iterator().next();

					imovelRelatorioHelper

					.setMatriculaImovel(arrayImovel[0] != null

					&& !arrayImovel[0].toString().trim()

					.equalsIgnoreCase("") ? (Integer) arrayImovel[0]

					: null);

					imovelRelatorioHelper

					.setDescricaoGerenciaRegional(arrayImovel[1] != null

					&& !arrayImovel[1].toString().trim()

					.equalsIgnoreCase("") ? (String) arrayImovel[1]

					: null);

					imovelRelatorioHelper

					.setIdLocalidade(arrayImovel[2] != null

					&& !arrayImovel[2].toString().trim()

					.equalsIgnoreCase("") ? (Integer) arrayImovel[2]

					: null);

					imovelRelatorioHelper

					.setDescricaoLocalidade(arrayImovel[3] != null

					&& !arrayImovel[3].toString().trim()

					.equalsIgnoreCase("") ? (String) arrayImovel[3]

					: null);

					imovelRelatorioHelper

					.setCodigoSetorComercial(arrayImovel[4] != null

					&& !arrayImovel[4].toString().trim()

					.equalsIgnoreCase("") ? (Integer) arrayImovel[4]

					: null);

					imovelRelatorioHelper

					.setDescricaoSetorComercial(arrayImovel[5] != null

					&& !arrayImovel[5].toString().trim()

					.equalsIgnoreCase("") ? (String) arrayImovel[5]

					: null);

					imovelRelatorioHelper

					.setIdMunicipio(arrayImovel[6] != null

					&& !arrayImovel[6].toString().trim()

					.equalsIgnoreCase("") ? (Integer) arrayImovel[6]

					: null);

					imovelRelatorioHelper

					.setNomeMunicipio(arrayImovel[7] != null

					&& !arrayImovel[7].toString().trim()

					.equalsIgnoreCase("") ? (String) arrayImovel[7]

					: null);

					imovelRelatorioHelper

					.setIdBairro(arrayImovel[8] != null

					&& !arrayImovel[8].toString().trim()

					.equalsIgnoreCase("") ? (Integer) arrayImovel[8]

					: null);

					imovelRelatorioHelper

					.setNomeBairro(arrayImovel[9] != null

					&& !arrayImovel[9].toString().trim()

					.equalsIgnoreCase("") ? (String) arrayImovel[9]

					: null);

					imovelRelatorioHelper

					.setNomeLogradouro(arrayImovel[10] != null

					&& !arrayImovel[10].toString().trim()

					.equalsIgnoreCase("") ? (String) arrayImovel[10]

					: null);

					imovelRelatorioHelper

					.setTituloLogradouro(arrayImovel[11] != null

					&& !arrayImovel[11].toString().trim()

					.equalsIgnoreCase("") ? (String) arrayImovel[11]

					: null);

					imovelRelatorioHelper

					.setTipoLogradouro(arrayImovel[12] != null

					&& !arrayImovel[12].toString().trim()

					.equalsIgnoreCase("") ? (String) arrayImovel[12]

					: null);

					imovelRelatorioHelper

					.setNumeroQuadra(arrayImovel[13] != null

					&& !arrayImovel[13].toString().trim()

					.equalsIgnoreCase("") ? (Integer) arrayImovel[13]

					: null);

					imovelRelatorioHelper

					.setCepCodigo(arrayImovel[14] != null

					&& !arrayImovel[14].toString().trim()

					.equalsIgnoreCase("") ? (Integer) arrayImovel[14]

					: null);

					imovelRelatorioHelper

					.setCepLogradouro(arrayImovel[15] != null

					&& !arrayImovel[15].toString().trim()

					.equalsIgnoreCase("") ? (String) arrayImovel[15]

					: null);

					imovelRelatorioHelper

					.setCepBairro(arrayImovel[16] != null

					&& !arrayImovel[16].toString().trim()

					.equalsIgnoreCase("") ? (String) arrayImovel[16]

					: null);

					imovelRelatorioHelper

					.setCepMunicipio(arrayImovel[17] != null

					&& !arrayImovel[17].toString().trim()

					.equalsIgnoreCase("") ? (String) arrayImovel[17]

					: null);

					imovelRelatorioHelper

					.setCepTipoLogradouro(arrayImovel[18] != null

					&& !arrayImovel[18].toString().trim()

					.equalsIgnoreCase("") ? (String) arrayImovel[18]

					: null);

					imovelRelatorioHelper

					.setCepSigla(arrayImovel[19] != null

					&& !arrayImovel[19].toString().trim()

					.equalsIgnoreCase("") ? (String) arrayImovel[19]

					: null);

					// ----Subcategorias

					consultaSubcategroias = "select" + " su.id,"// 0

									+ " su.descricao,"// 1

									+ " ca.descricao,"// 2

									+ " isub.quantidadeEconomias"// 3

									+ " from ImovelSubcategoria isub"

									+ " inner join isub.comp_id.subcategoria su"

									+ " inner join isub.comp_id.imovel im"

									+ " inner join su.categoria ca" + " where im.id = "

									+ idImovel;

					colecaoSubcategorias = session.createQuery(

					consultaSubcategroias).list();

					if(!colecaoSubcategorias.isEmpty()){

						Iterator iteratorSubcategoria = colecaoSubcategorias

						.iterator();

						ImovelSubcategoriaHelper imovelSubcategoriaHelper = null;

						while(iteratorSubcategoria.hasNext()){

							retornoImovelEconomia = new ArrayList();

							arraySubcategorias = (Object[]) iteratorSubcategoria

							.next();

							// ---popula imovelEconomiaHelper

							imovelSubcategoriaHelper = new ImovelSubcategoriaHelper();

							imovelSubcategoriaHelper

							.setSubcategoria(arraySubcategorias[1] != null

							&& !arraySubcategorias[1]

							.toString().trim()

							.equalsIgnoreCase("") ? (String) arraySubcategorias[1]

							: null);

							imovelSubcategoriaHelper

							.setCategoria(arraySubcategorias[2] != null

							&& !arraySubcategorias[2]

							.toString().trim()

							.equalsIgnoreCase("") ? (String) arraySubcategorias[2]

							: null);

							imovelSubcategoriaHelper

							.setQuantidadeEconomias(arraySubcategorias[3] != null

							&& !arraySubcategorias[3]

							.toString().trim()

							.equalsIgnoreCase("") ? ((Short) arraySubcategorias[3])

							.shortValue()

							: 0);

							// ----ImovelClienteImovel

							consultaImovelEconomia = "select"

							+ " ie.id,"// 0

											+ " ie.numeroPontosUtilizacao,"// 1

											+ " ie.complementoEndereco,"// 2

											+ " ie.numeroMorador,"// 3

											+ " ie.numeroIptu,"// 4

											+ " ie.numeroCelpe,"// 5

											+ " ie.areaConstruida,"// 6

											+ " ac.volumeMenorFaixa,"// 7

											+ " ac.volumeMaiorFaixa"// 8

											+ " from ImovelEconomia ie"

											+ " left join ie.areaConstruidaFaixa ac"

											+ " inner join ie.imovelSubcategoria isub"

											+ " inner join isub.comp_id.imovel im"

											+ " inner join isub.comp_id.subcategoria su"

											+ " where im.id = " + idImovel + " and"

											+ " su.id = "

											+ arraySubcategorias[0].toString();

							// ----preenche colecao clienteImovelEconomia

							colecaoImovelEconomia = session.createQuery(

							consultaImovelEconomia).list();

							// --- metodo para carregar objeto e setalo na

							// colecao do superior

							retornoImovelEconomia = popularImovelEconomia(

							colecaoImovelEconomia, idImovel);

							imovelSubcategoriaHelper

							.setColecaoImovelEconomia(retornoImovelEconomia);

							retornoSubcategoria.add(imovelSubcategoriaHelper);

						}

						imovelRelatorioHelper

						.setSubcategorias(retornoSubcategoria);

					}

				}

				retorno.add(imovelRelatorioHelper);

			}

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	// filtrar imovel outros criterios - carrega cliente imovel

	public Collection populaClienteImovelRelatorioImovel(

	Collection colecaoClienteImovelEconomia){

		Collection retorno = new ArrayList();

		ClienteImovelEconomiaHelper clienteImovelEconomiaHelper = null;

		Iterator iterator = colecaoClienteImovelEconomia.iterator();

		Object[] arrayClienteImovelEconomia = null;

		while(iterator.hasNext()){

			clienteImovelEconomiaHelper = new ClienteImovelEconomiaHelper();

			arrayClienteImovelEconomia = (Object[]) iterator.next();

			clienteImovelEconomiaHelper

			.setClienteNome(arrayClienteImovelEconomia[0] != null

			&& !arrayClienteImovelEconomia[0].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayClienteImovelEconomia[0]

			: null);

			clienteImovelEconomiaHelper

			.setRelacaoTipo(arrayClienteImovelEconomia[1] != null

			&& !arrayClienteImovelEconomia[1].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayClienteImovelEconomia[1]

			: null);

			clienteImovelEconomiaHelper

			.setCpf(arrayClienteImovelEconomia[2] != null

			&& !arrayClienteImovelEconomia[2].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayClienteImovelEconomia[2]

			: null);

			clienteImovelEconomiaHelper

			.setCnpj(arrayClienteImovelEconomia[3] != null

			&& !arrayClienteImovelEconomia[3].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayClienteImovelEconomia[3]

			: null);

			clienteImovelEconomiaHelper

			.setRelacaoDataInicio(arrayClienteImovelEconomia[4] != null

			&& !arrayClienteImovelEconomia[4].toString().trim()

			.equalsIgnoreCase("") ? (Date) arrayClienteImovelEconomia[4]

			: null);

			clienteImovelEconomiaHelper

			.setRelacaoDataFim(arrayClienteImovelEconomia[5] != null

			&& !arrayClienteImovelEconomia[5].toString().trim()

			.equalsIgnoreCase("") ? (Date) arrayClienteImovelEconomia[5]

			: null);

			clienteImovelEconomiaHelper

			.setMotivoFimRelacao(arrayClienteImovelEconomia[6] != null

			&& !arrayClienteImovelEconomia[6].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayClienteImovelEconomia[6]

			: null);

			retorno.add(clienteImovelEconomiaHelper);

		}

		return retorno;

	}

	// filtrar imovel outros criterios - carrega imovel economia

	public Collection popularImovelEconomia(Collection colecaoImovelEconomia,

	String idImovel) throws ErroRepositorioException{

		Collection retorno = new ArrayList();

		Iterator iterator = colecaoImovelEconomia.iterator();

		Object[] arrayImovelEconomia = null;

		ImovelEconomiaHelper imovelEconomiaHelper = null;

		String consultaClienteImovelEconomia = "";

		Collection colecaoClienteImovelEconomia = new ArrayList();

		Collection retornoClienteImovelEconomia = new ArrayList();

		while(iterator.hasNext()){

			imovelEconomiaHelper = new ImovelEconomiaHelper();

			arrayImovelEconomia = (Object[]) iterator.next();

			imovelEconomiaHelper

			.setNumeroPontosUtilizacao(arrayImovelEconomia[1] != null

			&& !arrayImovelEconomia[1].toString().trim()

			.equalsIgnoreCase("") ? ((Short) arrayImovelEconomia[1])

			.shortValue()

			: 0);

			imovelEconomiaHelper

			.setComplementoEndereco(arrayImovelEconomia[2] != null

			&& !arrayImovelEconomia[2].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovelEconomia[2]

			: null);

			imovelEconomiaHelper

			.setNumeroMoradores(arrayImovelEconomia[3] != null

			&& !arrayImovelEconomia[3].toString().trim()

			.equalsIgnoreCase("") ? ((Short) arrayImovelEconomia[3])

			.shortValue()

			: 0);

			imovelEconomiaHelper

			.setNumeroIptu(arrayImovelEconomia[4] != null

			&& !arrayImovelEconomia[4].toString().trim()

			.equalsIgnoreCase("") ? (BigDecimal) arrayImovelEconomia[4]

			: null);

			imovelEconomiaHelper

			.setNumeroContratoCelpe(arrayImovelEconomia[5] != null

			&& !arrayImovelEconomia[5].toString().trim()

			.equalsIgnoreCase("") ? (Long) arrayImovelEconomia[5]

			: null);

			imovelEconomiaHelper

			.setAreaConstruida(arrayImovelEconomia[6] != null

			&& !arrayImovelEconomia[6].toString().trim()

			.equalsIgnoreCase("") ? ((Short) arrayImovelEconomia[6])

			.shortValue()

			: 0);

			consultaClienteImovelEconomia = "select"

			+ " cl.nome," // 0

							+ " crt.descricao," // 1

							+ " cl.cpf," // 2

							+ " cl.cnpj," // 3

							+ " cie.dataInicioRelacao," // 4

							+ " cie.dataFimRelacao," // 5

							+ " cifrm.descricao" // 6

							+ " from ClienteImovelEconomia cie" // 7

							+ " inner join cie.cliente cl"

							+ " inner join cie.imovelEconomia.imovelSubcategoria.comp_id.imovel im"

							+ " inner join cie.clienteRelacaoTipo crt"

							+ " left join cie.clienteImovelFimRelacaoMotivo cifrm"

							+ " left join cie.imovelEconomia ie" + " where im.id = "

							+ idImovel + " and" + " ie.id = "

							+ arrayImovelEconomia[0].toString();

			Session session = HibernateUtil.getSession();

			try{

				colecaoClienteImovelEconomia = session.createQuery(

				consultaClienteImovelEconomia).list();

				// ---- popula objeto e coloca-o na colecao

				retornoClienteImovelEconomia = populaClienteImovelRelatorioImovel(colecaoClienteImovelEconomia);

				imovelEconomiaHelper

				.setClienteImovelEconomiaHelper(retornoClienteImovelEconomia);

				retorno.add(imovelEconomiaHelper);

			}catch(HibernateException e){

				// levanta a exceção para a próxima camada

				throw new ErroRepositorioException(e, "Erro no Hibernate");

			}finally{

				// fecha a sessão

				HibernateUtil.closeSession(session);

			}

		}

		return retorno;

	}

	// --filtrar imovel outros criterios - carrega objeto helper para relatorio

	public ImovelRelatorioHelper carregaIomvelRelatorioHelper(

	Collection colecaoImovel, Collection colecaoLigacaoAgua,

	Collection colecaoLigacaoEsgoto,

	Collection colecaoConsumoHistorico, Collection colecaoSubcategoria,

	Collection colecaoClienteUsuarioResponsavel){

		ImovelRelatorioHelper imovelRelatorioHelper = new ImovelRelatorioHelper();

		// ------imoveis

		if(!colecaoImovel.isEmpty()){

			Object[] arrayImovel = (Object[]) colecaoImovel.iterator().next();

			imovelRelatorioHelper
							.setMatriculaImovel(arrayImovel[0] != null && !arrayImovel[0].toString().trim().equalsIgnoreCase("") ? (Integer) arrayImovel[0]
											: null);

			imovelRelatorioHelper

			.setNumeroLote(arrayImovel[1] != null

			&& !arrayImovel[1].toString().trim()

			.equalsIgnoreCase("") ? ((Short) arrayImovel[1])

			.shortValue()

			: 0);

			imovelRelatorioHelper

			.setNumeroSubLote(arrayImovel[2] != null

			&& !arrayImovel[2].toString().trim()

			.equalsIgnoreCase("") ? ((Short) arrayImovel[2])

			.shortValue()

			: 0);

			imovelRelatorioHelper

			.setVolumeReservatorioSuperior(arrayImovel[3] != null

			&& !arrayImovel[3].toString().trim()

			.equalsIgnoreCase("") ? (BigDecimal) arrayImovel[3]

			: null);

			imovelRelatorioHelper

			.setVolumeReservatorioInferior(arrayImovel[4] != null

			&& !arrayImovel[4].toString().trim()

			.equalsIgnoreCase("") ? (BigDecimal) arrayImovel[4]

			: null);

			imovelRelatorioHelper

			.setVolumePiscina(arrayImovel[5] != null

			&& !arrayImovel[5].toString().trim()

			.equalsIgnoreCase("") ? (BigDecimal) arrayImovel[5]

			: null);

			imovelRelatorioHelper

			.setNumeroMoradores(arrayImovel[6] != null

			&& !arrayImovel[6].toString().trim()

			.equalsIgnoreCase("") ? ((Short) arrayImovel[6])

			.shortValue()

			: 0);

			imovelRelatorioHelper

			.setNumeroPontosUtilzacao(arrayImovel[7] != null

			&& !arrayImovel[7].toString().trim()

			.equalsIgnoreCase("") ? ((Short) arrayImovel[7])

			.shortValue()

			: 0);

			imovelRelatorioHelper

			.setIndicadorImovelCondominio(arrayImovel[8] != null

			&& !arrayImovel[8].toString().trim()

			.equalsIgnoreCase("") ? ((Short) arrayImovel[8])

			.shortValue()

			: 0);

			if(arrayImovel[8] != null

			&& !arrayImovel[8].toString().trim().equalsIgnoreCase("")){

				if(imovelRelatorioHelper.getIndicadorImovelCondominio() == 1){

					imovelRelatorioHelper

					.setIndicadorImovelCondominioDescricao("Sim");

				}else{

					imovelRelatorioHelper

					.setIndicadorImovelCondominioDescricao("Não");

				}

			}

			imovelRelatorioHelper

			.setAreaConstruida(arrayImovel[9] != null

			&& !arrayImovel[9].toString().trim()

			.equalsIgnoreCase("") ? ((Short) arrayImovel[9])

			.shortValue()

			: null);

			imovelRelatorioHelper

			.setMatriculaImovelPrincipal(arrayImovel[10] != null

			&& !arrayImovel[10].toString().trim()

			.equalsIgnoreCase("") ? (Integer) arrayImovel[10]

			: null);

			imovelRelatorioHelper

			.setMatriculaImovelCondominio(arrayImovel[11] != null

			&& !arrayImovel[11].toString().trim()

			.equalsIgnoreCase("") ? (Integer) arrayImovel[11]

			: null);

			imovelRelatorioHelper

			.setClienteId(arrayImovel[12] != null

			&& !arrayImovel[12].toString().trim()

			.equalsIgnoreCase("") ? (Integer) arrayImovel[12]

			: null);

			imovelRelatorioHelper

			.setClienteNome(arrayImovel[13] != null

			&& !arrayImovel[13].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[13]

			: null);

			imovelRelatorioHelper

			.setClienteRelacaoTipo(arrayImovel[14] != null

			&& !arrayImovel[14].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[14]

			: null);

			imovelRelatorioHelper

			.setIdLocalidade(arrayImovel[15] != null

			&& !arrayImovel[15].toString().trim()

			.equalsIgnoreCase("") ? (Integer) arrayImovel[15]

			: null);

			imovelRelatorioHelper

			.setDescricaoLocalidade(arrayImovel[16] != null

			&& !arrayImovel[16].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[16]

			: null);

			imovelRelatorioHelper

			.setCodigoSetorComercial(arrayImovel[17] != null

			&& !arrayImovel[17].toString().trim()

			.equalsIgnoreCase("") ? (Integer) arrayImovel[17]

			: null);

			imovelRelatorioHelper

			.setDescricaoSetorComercial(arrayImovel[18] != null

			&& !arrayImovel[18].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[18]

			: null);

			imovelRelatorioHelper

			.setIdMunicipio(arrayImovel[19] != null

			&& !arrayImovel[19].toString().trim()

			.equalsIgnoreCase("") ? Integer.valueOf(

			arrayImovel[19].toString()) : null);

			imovelRelatorioHelper

			.setNomeMunicipio(arrayImovel[20] != null

			&& !arrayImovel[20].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[20]

			: null);

			imovelRelatorioHelper

			.setIdBairro(arrayImovel[21] != null

			&& !arrayImovel[21].toString().trim()

			.equalsIgnoreCase("") ? (Integer) arrayImovel[21]

			: null);

			imovelRelatorioHelper

			.setNomeBairro(arrayImovel[22] != null

			&& !arrayImovel[22].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[22]

			: null);

			imovelRelatorioHelper

			.setNomeLogradouro(arrayImovel[23] != null

			&& !arrayImovel[23].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[23]

			: null);

			imovelRelatorioHelper

			.setTituloLogradouro(arrayImovel[24] != null

			&& !arrayImovel[24].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[24]

			: null);

			imovelRelatorioHelper

			.setTipoLogradouro(arrayImovel[25] != null

			&& !arrayImovel[25].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[25]

			: null);

			imovelRelatorioHelper

			.setNumeroQuadra(arrayImovel[26] != null

			&& !arrayImovel[26].toString().trim()

			.equalsIgnoreCase("") ? (Integer) arrayImovel[26]

			: null);

			imovelRelatorioHelper

			.setCepCodigo(arrayImovel[27] != null

			&& !arrayImovel[27].toString().trim()

			.equalsIgnoreCase("") ? (Integer) arrayImovel[27]

			: null);

			imovelRelatorioHelper

			.setCepLogradouro(arrayImovel[28] != null

			&& !arrayImovel[28].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[28]

			: null);

			imovelRelatorioHelper

			.setCepBairro(arrayImovel[29] != null

			&& !arrayImovel[29].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[29]

			: null);

			imovelRelatorioHelper

			.setCepMunicipio(arrayImovel[30] != null

			&& !arrayImovel[30].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[30]

			: null);

			imovelRelatorioHelper

			.setCepTipoLogradouro(arrayImovel[31] != null

			&& !arrayImovel[31].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[31]

			: null);

			imovelRelatorioHelper

			.setCepSigla(arrayImovel[32] != null

			&& !arrayImovel[32].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[32]

			: null);

			imovelRelatorioHelper

			.setLigacaoAguaSituacao(arrayImovel[33] != null

			&& !arrayImovel[33].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[33]

			: null);

			imovelRelatorioHelper

			.setLigacaoEsgotoSituacao(arrayImovel[34] != null

			&& !arrayImovel[34].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[34]

			: null);

			imovelRelatorioHelper

			.setTipoPavimentoCalcada(arrayImovel[35] != null

			&& !arrayImovel[35].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[35]

			: null);

			imovelRelatorioHelper

			.setTipoPavimentoRua(arrayImovel[36] != null

			&& !arrayImovel[36].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[36]

			: null);

			imovelRelatorioHelper

			.setTipoDespejo(arrayImovel[37] != null

			&& !arrayImovel[37].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[37]

			: null);

			imovelRelatorioHelper

			.setDescricaoTipoPoco(arrayImovel[38] != null

			&& !arrayImovel[38].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[38]

			: null);

			imovelRelatorioHelper

			.setNumeroHidrometroPoco(arrayImovel[39] != null

			&& !arrayImovel[39].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[39]

			: null);

			imovelRelatorioHelper

			.setAnoFabricacaoHidrometroPoco(arrayImovel[40] != null

			&& !arrayImovel[40].toString().trim()

			.equalsIgnoreCase("") ? ((Short) arrayImovel[40])

			.shortValue()

			: 0);

			imovelRelatorioHelper

			.setCapacidadeHidrometroPoco(arrayImovel[41] != null

			&& !arrayImovel[41].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[41]

			: null);

			imovelRelatorioHelper

			.setMarcaHidrometroPoco(arrayImovel[42] != null

			&& !arrayImovel[42].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[42]

			: null);

			imovelRelatorioHelper

			.setDiametroHidrometroPoco(arrayImovel[43] != null

			&& !arrayImovel[43].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[43]

			: null);

			imovelRelatorioHelper

			.setTipoHidrometroPoco(arrayImovel[44] != null

			&& !arrayImovel[44].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[44]

			: null);

			imovelRelatorioHelper

			.setDataInstalacaoHidrometroPoco(arrayImovel[45] != null

			&& !arrayImovel[45].toString().trim()

			.equalsIgnoreCase("") ? (Date) arrayImovel[45]

			: null);

			imovelRelatorioHelper

			.setLocalIstalacaoHidrometroPoco(arrayImovel[46] != null

			&& !arrayImovel[46].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[46]

			: null);

			imovelRelatorioHelper

			.setTipoProtecaoHidrometroPoco(arrayImovel[47] != null

			&& !arrayImovel[47].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[47]

			: null);

			imovelRelatorioHelper

			.setIndicadorExistenciaCavaletePoco(arrayImovel[48] != null

			&& !arrayImovel[48].toString().trim()

			.equalsIgnoreCase("") ? ((Short) arrayImovel[48])

			.shortValue()

			: 0);

			if(imovelRelatorioHelper.getIndicadorExistenciaCavaletePoco() == 1){

				imovelRelatorioHelper

				.setIndicadorExistenciaCavaletePocoDescricao("Sim");

			}else{

				imovelRelatorioHelper

				.setIndicadorExistenciaCavaletePocoDescricao("Não");

			}

			imovelRelatorioHelper

			.setDescricaoGerenciaRegional(arrayImovel[49] != null

			&& !arrayImovel[49].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayImovel[48]

			: null);

			imovelRelatorioHelper

			.setIdGerenciaRegional(arrayImovel[49] != null

			&& !arrayImovel[49].toString().trim()

			.equalsIgnoreCase("") ? (Integer) arrayImovel[48]

			: null);

		}

		// ------ligacao agua

		if(!colecaoLigacaoAgua.isEmpty()){

			Object[] arrayLigacaoAgua = (Object[]) colecaoLigacaoAgua

			.iterator().next();

			imovelRelatorioHelper

			.setDataLigacaoAgua(arrayLigacaoAgua[0] != null

			&& !arrayLigacaoAgua[0].toString().trim()

			.equalsIgnoreCase("") ? (Date) arrayLigacaoAgua[0]

			: null);

			imovelRelatorioHelper

			.setDiametroLigacaoAgua(arrayLigacaoAgua[1] != null

			&& !arrayLigacaoAgua[1].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayLigacaoAgua[1]

			: null);

			imovelRelatorioHelper

			.setMaterialLigacaoAgua(arrayLigacaoAgua[2] != null

			&& !arrayLigacaoAgua[2].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayLigacaoAgua[2]

			: null);

			imovelRelatorioHelper

			.setConsumoMinimoFixadoAgua(arrayLigacaoAgua[3] != null

			&& !arrayLigacaoAgua[3].toString().trim()

			.equalsIgnoreCase("") ? (Integer) arrayLigacaoAgua[3]

			: null);

			imovelRelatorioHelper

			.setNumeroHidrometroAgua(arrayLigacaoAgua[4] != null

			&& !arrayLigacaoAgua[4].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayLigacaoAgua[4]

			: null);

			imovelRelatorioHelper

			.setAnoFabricaocaHidrometroAgua(arrayLigacaoAgua[5] != null

			&& !arrayLigacaoAgua[5].toString().trim()

			.equalsIgnoreCase("") ? ((Short) arrayLigacaoAgua[5])

			.shortValue()

			: 0);

			imovelRelatorioHelper

			.setCapacidadeHidrometroAgua(arrayLigacaoAgua[6] != null

			&& !arrayLigacaoAgua[6].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayLigacaoAgua[6]

			: null);

			imovelRelatorioHelper

			.setMarcaHidrometroAgua(arrayLigacaoAgua[7] != null

			&& !arrayLigacaoAgua[7].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayLigacaoAgua[7]

			: null);

			imovelRelatorioHelper

			.setDiametroHidrometroAgua(arrayLigacaoAgua[8] != null

			&& !arrayLigacaoAgua[8].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayLigacaoAgua[8]

			: null);

			imovelRelatorioHelper

			.setTipoHidrometroAgua(arrayLigacaoAgua[9] != null

			&& !arrayLigacaoAgua[9].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayLigacaoAgua[9]

			: null);

			imovelRelatorioHelper

			.setDataInstalacaoHidrometroAgua(arrayLigacaoAgua[10] != null

			&& !arrayLigacaoAgua[10].toString().trim()

			.equalsIgnoreCase("") ? (Date) arrayLigacaoAgua[10]

			: null);

			imovelRelatorioHelper

			.setLocalInstalacaoHidrometroAgua(arrayLigacaoAgua[11] != null

			&& !arrayLigacaoAgua[11].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayLigacaoAgua[11]

			: null);

			imovelRelatorioHelper

			.setTipoProtecaoHidrometroAgua(arrayLigacaoAgua[12] != null

			&& !arrayLigacaoAgua[12].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayLigacaoAgua[12]

			: null);

			imovelRelatorioHelper

			.setIndicadorExistenciaCavaleteAgua(arrayLigacaoAgua[13] != null

			&& !arrayLigacaoAgua[13].toString().trim()

			.equalsIgnoreCase("") ? ((Short) arrayLigacaoAgua[13])

			.shortValue()

			: 0);

			if(imovelRelatorioHelper.getIndicadorExistenciaCavaleteAgua() == 1){

				imovelRelatorioHelper

				.setIndicadorExistenciaCavaleteAguaDescricao("Sim");

			}else{

				imovelRelatorioHelper

				.setIndicadorExistenciaCavaleteAguaDescricao("Não");

			}

		}

		// ------ligacao esgoto

		if(!colecaoLigacaoEsgoto.isEmpty()){

			Object[] arrayLigacaoEsgoto = (Object[]) colecaoLigacaoEsgoto

			.iterator().next();

			imovelRelatorioHelper

			.setDataLigacaoEsgoto(arrayLigacaoEsgoto[0] != null

			&& !arrayLigacaoEsgoto[0].toString().trim()

			.equalsIgnoreCase("") ? (Date) arrayLigacaoEsgoto[0]

			: null);

			imovelRelatorioHelper

			.setDiametroLigacaoEsgoto(arrayLigacaoEsgoto[1] != null

			&& !arrayLigacaoEsgoto[1].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayLigacaoEsgoto[1]

			: null);

			imovelRelatorioHelper

			.setMaterialLigacaoEsgoto(arrayLigacaoEsgoto[2] != null

			&& !arrayLigacaoEsgoto[2].toString().trim()

			.equalsIgnoreCase("") ? (String) arrayLigacaoEsgoto[2]

			: null);

			imovelRelatorioHelper

			.setPercentualAguaConsumidaColetada(arrayLigacaoEsgoto[3] != null

			&& !arrayLigacaoEsgoto[3].toString().trim()

			.equalsIgnoreCase("") ? (BigDecimal) arrayLigacaoEsgoto[3]

			: null);

			imovelRelatorioHelper

			.setPercentual(arrayLigacaoEsgoto[4] != null

			&& !arrayLigacaoEsgoto[4].toString().trim()

			.equalsIgnoreCase("") ? (BigDecimal) arrayLigacaoEsgoto[4]

			: null);

			imovelRelatorioHelper

			.setConsumoMinimoFixadoLigacaoEsgoto(arrayLigacaoEsgoto[5] != null

			&& !arrayLigacaoEsgoto[5].toString().trim()

			.equalsIgnoreCase("") ? (Integer) arrayLigacaoEsgoto[5]

			: null);

		}

		// ------consumo historico

		if(!colecaoConsumoHistorico.isEmpty()){

			Object[] arrayConsumoHistorico = (Object[]) colecaoConsumoHistorico

			.iterator().next();

			imovelRelatorioHelper

			.setConsumoMedioImovel(arrayConsumoHistorico[0] != null

			&& !arrayConsumoHistorico[0].toString().trim()

			.equalsIgnoreCase("") ? (Integer) arrayConsumoHistorico[0]

			: null);

		}

		// ------subcategoria

		String[] arraySubcatgegoriasQtdEconomias = new String[colecaoSubcategoria

		.size()];

		if(!colecaoSubcategoria.isEmpty()){

			Iterator iterator = colecaoSubcategoria.iterator();

			int i = 0;

			while(iterator.hasNext()){

				Object[] arraySubcategoria = (Object[]) iterator.next();

				arraySubcatgegoriasQtdEconomias[i] = arraySubcategoria[0]

				.toString()

				+ "/" + arraySubcategoria[1].toString();

				i++;

			}

			imovelRelatorioHelper.setSubcategoriaQtdEconomia(arraySubcatgegoriasQtdEconomias);

		}

		imovelRelatorioHelper.setClienteUsuarioResponsavel(popularClienteUsuarioResponsavel(colecaoClienteUsuarioResponsavel));

		return imovelRelatorioHelper;

	}

	/**
	 * [UC0156] Informar Situacao Especial Faturamento
	 * 
	 * @author Rhawi Dantas
	 * @created 09/01/2006
	 * @author eduardo henrique
	 * @date 09/01/2009
	 *       Alteração para inclusão de mais um filtro , nome de Bairro. Correção para a Quadra e
	 *       Setor,
	 *       que recebe o código da mesma e não o Id.
	 */

	public Collection pesquisarImovelSituacaoEspecialFaturamento(String valor,
					SituacaoEspecialFaturamentoHelper situacaoEspecialFaturamentoHelper) throws ErroRepositorioException{

		String consulta = null;
		boolean divideConsulta = false;
		// Escolha de que tipo de consulta será
		if(valor.equals("COM")){
			consulta = "select imovel.id from Imovel imovel "
							+ "INNER JOIN imovel.localidade lo "
							+ "INNER JOIN imovel.setorComercial sc "
							+ "INNER JOIN imovel.quadra qu "
							+ "INNER JOIN qu.rota rota "
							+ "LEFT JOIN imovel.faturamentoSituacaoTipo fst "
							+ "where fst.id is not null and (imovel.indicadorExclusao <> :idExclusao OR imovel.indicadorExclusao is null) and ";
		}else{
			consulta = "select imovel.id from Imovel imovel " + "inner join imovel.localidade lo " + "inner join imovel.setorComercial sc "
							+ "inner join imovel.quadra qu " + "INNER JOIN qu.rota rota " + "left join imovel.faturamentoSituacaoTipo fst "
							+ "where fst.id is null and (imovel.indicadorExclusao <> :idExclusao OR imovel.indicadorExclusao is null) and ";
		}
		String idImovel = situacaoEspecialFaturamentoHelper.getIdImovel();
		String idsImoveis = situacaoEspecialFaturamentoHelper.getIdsImoveis();
		String idLocalidadeOrigem = situacaoEspecialFaturamentoHelper.getLocalidadeOrigemID();
		String idLocalidadeDestino = situacaoEspecialFaturamentoHelper.getLocalidadeDestinoID();
		String setorComercialOrigemID = situacaoEspecialFaturamentoHelper.getSetorComercialOrigemID();
		String setorComercialDestinoID = situacaoEspecialFaturamentoHelper.getSetorComercialDestinoID();
		String quadraOrigemID = situacaoEspecialFaturamentoHelper.getQuadraOrigemID();
		String quadraDestinoID = situacaoEspecialFaturamentoHelper.getQuadraDestinoID();
		String loteOrigem = situacaoEspecialFaturamentoHelper.getLoteOrigem();
		String loteDestino = situacaoEspecialFaturamentoHelper.getLoteDestino();
		String subLoteOrigem = situacaoEspecialFaturamentoHelper.getSubloteOrigem();
		String subLoteDestino = situacaoEspecialFaturamentoHelper.getSubloteDestino();
		String codigoRotaInicial = situacaoEspecialFaturamentoHelper.getCodigoRotaInicial();
		String codigoRotaFinal = situacaoEspecialFaturamentoHelper.getCodigoRotaFinal();
		String sequencialRotaInicial = situacaoEspecialFaturamentoHelper.getSequencialRotaInicial();
		String sequencialRotaFinal = situacaoEspecialFaturamentoHelper.getSequencialRotaFinal();
		String nomeBairro = situacaoEspecialFaturamentoHelper.getNomeBairro();

		if(idImovel != null && !idImovel.equals("")){
			consulta += " imovel.id = " + idImovel + " and";
		}

		if(nomeBairro != null && !nomeBairro.trim().equals("")){
			consulta += " imovel.logradouroBairro.bairro.nome like '%" + nomeBairro + "%' and ";
		}

		if(idLocalidadeOrigem != null && !idLocalidadeOrigem.equalsIgnoreCase("") && idLocalidadeDestino != null
						&& !idLocalidadeDestino.equalsIgnoreCase("")){
			consulta += " lo.id between " + idLocalidadeOrigem + " and " + idLocalidadeDestino + " and";
		}

		if(setorComercialOrigemID != null && !setorComercialOrigemID.equalsIgnoreCase("") && setorComercialDestinoID != null
						&& !setorComercialDestinoID.equalsIgnoreCase("")){
			consulta += " sc.id between " + setorComercialOrigemID + " and " + setorComercialDestinoID + " and";
		}

		if(quadraOrigemID != null && !quadraOrigemID.equalsIgnoreCase("") && quadraDestinoID != null
						&& !quadraDestinoID.equalsIgnoreCase("")){
			consulta += " qu.numeroQuadra between " + quadraOrigemID + " and " + quadraDestinoID + " and";
		}

		if(loteOrigem != null && !loteOrigem.equalsIgnoreCase("") && loteDestino != null && !loteDestino.equalsIgnoreCase("")){
			consulta += " imovel.lote between " + loteOrigem + " and " + loteDestino + " and";
		}

		if(subLoteOrigem != null && !subLoteOrigem.equalsIgnoreCase("") && subLoteDestino != null && !subLoteDestino.equalsIgnoreCase("")){
			consulta += " imovel.subLote between " + subLoteOrigem + " and " + subLoteDestino + " and";
		}

		if((codigoRotaInicial != null && !codigoRotaInicial.equals("")) && (codigoRotaFinal != null && !codigoRotaFinal.equals(""))){
			consulta = consulta + " rota.codigo between " + codigoRotaInicial + " and " + codigoRotaFinal + " and";
		}

		if((sequencialRotaInicial != null && !sequencialRotaInicial.equals(""))
						&& (sequencialRotaFinal != null && !sequencialRotaFinal.equals(""))){
			consulta = consulta + " imovel.numeroSequencialRota between " + sequencialRotaInicial + " and " + sequencialRotaFinal + " and";
		}

		// Consulta
		if(idsImoveis != null && !idsImoveis.equals("")){
			if(idsImoveis.split(",").length > 100){
				divideConsulta = true;
			}else{
				consulta += " imovel.id in ( " + idsImoveis + ") and";
			}
		}

		Session session = HibernateUtil.getSession();
		try{
			if(divideConsulta){
				Collection retorno = new ArrayList();
				String[] idsImoveisSplit = idsImoveis.split(",");
				int qtdIteracoes = 0;
				String consultaIteracao = consulta + " imovel.id in (";
				for(String item : idsImoveisSplit){
					qtdIteracoes++;
					consultaIteracao = consultaIteracao + item + ",";
					if(qtdIteracoes == 100){
						consultaIteracao = consultaIteracao.substring(0, consultaIteracao.length() - 1) + " ) and ";
						retorno.addAll(session.createQuery(Util.formatarHQL(consultaIteracao, 4)).setShort("idExclusao",
										Imovel.IMOVEL_EXCLUIDO).list());
						qtdIteracoes = 0;
						consultaIteracao = consulta + " imovel.id in (";
					}
				}
				if(qtdIteracoes > 0){
					consultaIteracao = consultaIteracao.substring(0, consultaIteracao.length() - 1) + " ) and ";
					retorno.addAll(session.createQuery(Util.formatarHQL(consultaIteracao, 4))
									.setShort("idExclusao", Imovel.IMOVEL_EXCLUIDO).list());
				}
				return retorno;
			}

			return session.createQuery(Util.formatarHQL(consulta, 4)).setShort("idExclusao", Imovel.IMOVEL_EXCLUIDO).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0177] Informar Situacao Especial de Cobrança
	 * 
	 * @author Sávio Luiz
	 * @created 07/03/2006
	 */

	public Collection pesquisarImovelSituacaoEspecialCobranca(String valor,

	SituacaoEspecialCobrancaHelper situacaoEspecialCobrancaHelper)

	throws ErroRepositorioException{

		String consulta = null;

		// Escolha de que tipo de consulta será

		if(valor.equals("COM"))

		consulta = "select imovel.id from Imovel imovel "

		+ "inner join imovel.localidade lo "

		+ "inner join imovel.setorComercial sc "

		+ "inner join imovel.quadra qu "

		+ "INNER JOIN imovel.rota rota "

		+ "left join imovel.cobrancaSituacaoTipo cst "

		+ "where cst.id is not null and (imovel.indicadorExclusao <> :idExclusao OR imovel.indicadorExclusao is null) and ";

		else

		consulta = "select imovel.id from Imovel imovel "

		+ "inner join imovel.localidade lo "

		+ "inner join imovel.setorComercial sc "

		+ "inner join imovel.quadra qu "

		+ "INNER JOIN imovel.rota rota "

		+ "left join imovel.cobrancaSituacaoTipo cst "

		+ "where cst.id is null and (imovel.indicadorExclusao <> :idExclusao OR imovel.indicadorExclusao is null) and ";

		String idImovel = situacaoEspecialCobrancaHelper.getIdImovel();

		String idLocalidadeOrigem = situacaoEspecialCobrancaHelper

		.getLocalidadeOrigemID();

		String idLocalidadeDestino = situacaoEspecialCobrancaHelper

		.getLocalidadeDestinoID();

		String setorComercialOrigemID = situacaoEspecialCobrancaHelper

		.getSetorComercialOrigemID();

		String setorComercialDestinoID = situacaoEspecialCobrancaHelper

		.getSetorComercialDestinoID();

		String quadraOrigemID = situacaoEspecialCobrancaHelper

		.getQuadraOrigemID();

		String quadraDestinoID = situacaoEspecialCobrancaHelper

		.getQuadraDestinoID();

		String loteOrigem = situacaoEspecialCobrancaHelper.getLoteOrigem();

		String loteDestino = situacaoEspecialCobrancaHelper.getLoteDestino();

		String subLoteOrigem = situacaoEspecialCobrancaHelper

		.getSubloteOrigem();

		String subLoteDestino = situacaoEspecialCobrancaHelper

		.getSubloteDestino();

		String codigoRotaInicial = situacaoEspecialCobrancaHelper

		.getCodigoRotaInicial();

		String codigoRotaFinal = situacaoEspecialCobrancaHelper

		.getCodigoRotaFinal();

		String sequencialRotaInicial = situacaoEspecialCobrancaHelper

		.getSequencialRotaInicial();

		String sequencialRotaFinal = situacaoEspecialCobrancaHelper

		.getSequencialRotaFinal();

		if(!idImovel.equalsIgnoreCase("") && !idImovel.equalsIgnoreCase(""))

		consulta += " imovel.id = " + idImovel + " and";

		if(!idLocalidadeOrigem.equalsIgnoreCase("")

		&& !idLocalidadeDestino.equalsIgnoreCase(""))

		consulta += " lo.id between " + idLocalidadeOrigem + " and "

		+ idLocalidadeDestino + " and";

		if(!setorComercialOrigemID.equalsIgnoreCase("")

		&& !setorComercialOrigemID.equalsIgnoreCase(""))

		consulta += " sc.id between " + setorComercialOrigemID + " and "

		+ setorComercialDestinoID + " and";

		if(!quadraOrigemID.equalsIgnoreCase("")

		&& !quadraOrigemID.equalsIgnoreCase(""))

		consulta += " qu.id between " + quadraOrigemID + " and "

		+ quadraDestinoID + " and";

		if(!loteOrigem.equalsIgnoreCase("")

		&& !loteOrigem.equalsIgnoreCase(""))

		consulta += " imovel.lote between " + loteOrigem + " and "

		+ loteDestino + " and";

		if(!subLoteOrigem.equalsIgnoreCase("")

		&& !subLoteOrigem.equalsIgnoreCase(""))

		consulta += " imovel.subLote between " + subLoteOrigem + " and "

		+ subLoteDestino + " and";

		if((codigoRotaInicial != null && !codigoRotaInicial.equals(""))

		&& (codigoRotaFinal != null && !codigoRotaFinal.equals(""))){

			consulta = consulta + " rota.codigo between " + codigoRotaInicial

			+ " and " + codigoRotaFinal + " and";

		}

		if((sequencialRotaInicial != null && !sequencialRotaInicial.equals(""))

		&& (sequencialRotaFinal != null && !sequencialRotaFinal

		.equals(""))){

			consulta = consulta + " imovel.numeroSequencialRota between "

			+ sequencialRotaInicial + " and " + sequencialRotaFinal

			+ " and";

		}

		// Consulta
		Session session = HibernateUtil.getSession();

		try{

			return session.createQuery(Util.formatarHQL(consulta, 4)).setShort(

			"idExclusao", Imovel.IMOVEL_EXCLUIDO).list();

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

	}

	public Integer verificarExistenciaImovel(Integer idImovel)

	throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append("select count(imovel.id) ")

			.append("from Imovel imovel ")

			.append("where imovel.id = :idImovel and (imovel.indicadorExclusao is null OR imovel.indicadorExclusao <> :idExclusao)");

			retorno = ((Number) session.createQuery(consulta.toString()).setInteger(

			"idImovel", idImovel.intValue()).setShort("idExclusao",

			Imovel.IMOVEL_EXCLUIDO).setMaxResults(1).uniqueResult()).intValue();

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	public Integer recuperarMatriculaImovel(Integer idImovel)

	throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = "select imovel.id "

			+ "from Imovel imovel "

			+ "where imovel.id = :idImovel and (imovel.indicadorExclusao is null OR imovel.indicadorExclusao <> :idExclusao)";

			retorno = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setShort("idExclusao",
							Imovel.IMOVEL_EXCLUIDO).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	// --------------rhawi e flavio

	public String criaCondicionaisHqlImovelOutrosCriterios(

	FiltrarImovelOutrosCriteriosHelper filtrarImovelOutrosCriteriosHelper){

		String retorno = "";

		String situacaoCobrancaID = filtrarImovelOutrosCriteriosHelper.getSituacaoCobranca();

		// Ligacao de Agua Esgoto Consumo

		String intervaloMediaMinimaHidrometroInicial = filtrarImovelOutrosCriteriosHelper.getIntervaloMediaMinimaHidrometroInicio();

		String intervaloMediaMinimaHidrometroFinal = filtrarImovelOutrosCriteriosHelper.getIntervaloMediaMinimaHidrometroFinal();

		String medicaoTipo = filtrarImovelOutrosCriteriosHelper.getTipoMedicao();

		String intervaloMediaMinimaImovelInicio = filtrarImovelOutrosCriteriosHelper.getIntervaloMediaMinimaImovelInicio();

		String intervaloMediaMinimaImovelFinal = filtrarImovelOutrosCriteriosHelper.getIntervaloMediaMinimaImovelFinal();

		// Dados Faturamento Cobranca

		String idClienteTipo = filtrarImovelOutrosCriteriosHelper.getIdClienteTipo();

		String idLocalidadeOrigem = filtrarImovelOutrosCriteriosHelper.getLocalidadeOrigemID();

		String idLocalidadeDestino = filtrarImovelOutrosCriteriosHelper.getLocalidadeDestinoID();

		String idGerenciaRegional = filtrarImovelOutrosCriteriosHelper.getIdGerenciaRegional();

		String setorComercialOrigemID = filtrarImovelOutrosCriteriosHelper.getSetorComercialOrigemID();

		String setorComercialDestinoID = filtrarImovelOutrosCriteriosHelper.getSetorComercialDestinoID();

		String quadraOrigemID = filtrarImovelOutrosCriteriosHelper.getQuadraOrigemID();

		String quadraDestinoID = filtrarImovelOutrosCriteriosHelper.getQuadraDestinoID();

		String idMunicipio = filtrarImovelOutrosCriteriosHelper.getIdMunicipio();

		String idBairro = filtrarImovelOutrosCriteriosHelper.getIdBairro();

		String CEP = filtrarImovelOutrosCriteriosHelper.getCEP();

		String idLogradouro = filtrarImovelOutrosCriteriosHelper.getIdLogradouro();

		String idCliente = filtrarImovelOutrosCriteriosHelper.getIdCliente();

		String idImovelCondominio = filtrarImovelOutrosCriteriosHelper.getIdImovelCondominio();

		String idImovelPrincipal = filtrarImovelOutrosCriteriosHelper.getIdImovelPrincipal();

		String situacaoAgua = filtrarImovelOutrosCriteriosHelper.getSituacaoAgua();

		String situacaoLigacaoEsgoto = filtrarImovelOutrosCriteriosHelper.getSituacaoLigacaoEsgoto();

		String perfilImovel = filtrarImovelOutrosCriteriosHelper.getPerfilImovel();

		String tipoPoco = filtrarImovelOutrosCriteriosHelper.getTipoPoco();

		String tipoSituacaoEspecialFaturamento = filtrarImovelOutrosCriteriosHelper.getTipoSituacaoEspecialFaturamento();

		String situacaoCobranca = filtrarImovelOutrosCriteriosHelper.getSituacaoCobranca();

		String tipoSituacaoEspecialCobranca = filtrarImovelOutrosCriteriosHelper.getTipoSituacaoEspecialCobranca();

		String anormalidadeElo = filtrarImovelOutrosCriteriosHelper.getAnormalidadeElo();

		String ocorrenciaCadastro = filtrarImovelOutrosCriteriosHelper.getOcorrenciaCadastro();

		String tarifaConsumo = filtrarImovelOutrosCriteriosHelper.getTarifaConsumo();

		String diaVencimento = filtrarImovelOutrosCriteriosHelper.getDiaVencimentoAlternativo();

		String numeroPontosInicial = filtrarImovelOutrosCriteriosHelper.getNumeroPontosInicial();

		String numeroPontosFinal = filtrarImovelOutrosCriteriosHelper.getNumeroPontosFinal();

		String numeroMoradoresInicial = filtrarImovelOutrosCriteriosHelper.getNumeroMoradoresInicial();

		String numeroMoradoresFinal = filtrarImovelOutrosCriteriosHelper.getNumeroMoradoresFinal();

		String areaConstruidaInicial = filtrarImovelOutrosCriteriosHelper.getAreaConstruidaInicial();

		String areaConstruidaFinal = filtrarImovelOutrosCriteriosHelper.getAreaConstruidaFinal();

		String areaConstruidaFaixa = filtrarImovelOutrosCriteriosHelper.getAreaConstruidaFaixa();

		String pocoTipo = filtrarImovelOutrosCriteriosHelper.getTipoPoco();

		// Dados Tarifa Social
		String indicadorImovelTarifaSocial = filtrarImovelOutrosCriteriosHelper.getIndicadorImovelTarifaSocial();

		String dataInicioImplantacao = Util.formatarData(filtrarImovelOutrosCriteriosHelper.getDataInicioImplantacao());

		String dataFimImplantacao = Util.formatarData(filtrarImovelOutrosCriteriosHelper.getDataFimImplantacao());

		String dataInicioExclusao = Util.formatarData(filtrarImovelOutrosCriteriosHelper.getDataInicioExclusao());

		String dataFimExclusao = Util.formatarData(filtrarImovelOutrosCriteriosHelper.getDataFimExclusao());

		String idMotivoExclusao = filtrarImovelOutrosCriteriosHelper.getIdMotivoExclusao();

		String dataInicioValidadeCartao = Util.formatarData(filtrarImovelOutrosCriteriosHelper.getDataInicioValidadeCartao());

		String dataFimValidadeCartao = Util.formatarData(filtrarImovelOutrosCriteriosHelper.getDataFimValidadeCartao());

		String mesInicioAdesao = filtrarImovelOutrosCriteriosHelper.getMesInicioAdesao();

		String mesFimAdesao = filtrarImovelOutrosCriteriosHelper.getMesFimAdesao();

		String tarifaSocialCartaoTipoId = filtrarImovelOutrosCriteriosHelper.getTarifaSocialCartaoTipoId();

		String tarifaSocialRendaTipoId = filtrarImovelOutrosCriteriosHelper.getTarifaSocialRendaTipoId();

		BigDecimal rendaInicial = Util.isVazioOuBranco(filtrarImovelOutrosCriteriosHelper.getRendaInicial()) ? null : Util
						.formatarMoedaRealparaBigDecimal(filtrarImovelOutrosCriteriosHelper.getRendaInicial());

		BigDecimal rendaFinal = Util.isVazioOuBranco(filtrarImovelOutrosCriteriosHelper.getRendaInicial()) ? null : Util
						.formatarMoedaRealparaBigDecimal(filtrarImovelOutrosCriteriosHelper.getRendaFinal());

		String celpeInicial = filtrarImovelOutrosCriteriosHelper.getCelpeInicial();

		String celpeFinal = filtrarImovelOutrosCriteriosHelper.getCelpeFinal();

		String dataInicioRecadastramento = Util.formatarData(filtrarImovelOutrosCriteriosHelper.getDataInicioRecadastramento());

		String dataFimRecadastramento = Util.formatarData(filtrarImovelOutrosCriteriosHelper.getDataFimRecadastramento());

		String recadastramentoNumeroInicial = filtrarImovelOutrosCriteriosHelper.getRecadastramentoNumeroInicial();

		String recadastramentoNumeroFinal = filtrarImovelOutrosCriteriosHelper.getRecadastramentoNumeroFinal();

		String composicaoHql = "";

		if(idGerenciaRegional != null && !idGerenciaRegional.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")
						&& !idGerenciaRegional.equals("")) composicaoHql += " gr.id = " + idGerenciaRegional + " and ";

		if(idLocalidadeOrigem != null && !idLocalidadeDestino.equalsIgnoreCase("")) composicaoHql += " lo.id between " + idLocalidadeOrigem
						+ " and " + idLocalidadeDestino + " and";

		if(setorComercialOrigemID != null && !setorComercialOrigemID.equalsIgnoreCase("")) composicaoHql += " sc.id between "
						+ setorComercialOrigemID + " and " + setorComercialDestinoID + " and";

		if(quadraOrigemID != null && !quadraOrigemID.equalsIgnoreCase("")) composicaoHql += " qu.id between " + quadraOrigemID + " and "
						+ quadraDestinoID + " and";

		// Rota
		if(!Util.isVazioOuBranco(filtrarImovelOutrosCriteriosHelper.getCdRotaInicial())
						&& !Util.isVazioOuBranco(filtrarImovelOutrosCriteriosHelper.getCdRotaFinal())){
			composicaoHql += " rotaImovel.rota_cdrota between " + filtrarImovelOutrosCriteriosHelper.getCdRotaInicial() + " and "
							+ filtrarImovelOutrosCriteriosHelper.getCdRotaFinal() + " and ";
		}

		// Segmento
		if(!Util.isVazioOuBranco(filtrarImovelOutrosCriteriosHelper.getSegmentoInicial())
						&& !Util.isVazioOuBranco(filtrarImovelOutrosCriteriosHelper.getSegmentoFinal())){
			composicaoHql += " im.imov_nnsegmento between " + filtrarImovelOutrosCriteriosHelper.getSegmentoInicial() + " and "
							+ filtrarImovelOutrosCriteriosHelper.getSegmentoFinal() + " and ";
		}

		// Lote
		if(!Util.isVazioOuBranco(filtrarImovelOutrosCriteriosHelper.getLoteOrigem())
						&& !Util.isVazioOuBranco(filtrarImovelOutrosCriteriosHelper.getLoteDestino())){
			composicaoHql += " im.imov_nnlote between " + filtrarImovelOutrosCriteriosHelper.getLoteOrigem() + " and "
							+ filtrarImovelOutrosCriteriosHelper.getLoteDestino() + " and ";
		}

		// Sublote
		if(!Util.isVazioOuBranco(filtrarImovelOutrosCriteriosHelper.getSubloteInicial())
						&& !Util.isVazioOuBranco(filtrarImovelOutrosCriteriosHelper.getSubloteFinal())){
			composicaoHql += " im.imov_nnsublote between " + filtrarImovelOutrosCriteriosHelper.getSubloteInicial() + " and "
							+ filtrarImovelOutrosCriteriosHelper.getSubloteFinal() + " and ";
		}

		if(idMunicipio != null && !idMunicipio.equalsIgnoreCase("")){
			composicaoHql += " mu.id = " + idMunicipio + " and ";
		}

		if(idBairro != null && !idBairro.equalsIgnoreCase("")){
			composicaoHql += " ba.id = " + idBairro + " and";
		}

		if(CEP != null && !CEP.equalsIgnoreCase("")){
			composicaoHql += " cep.id = " + CEP + " and ";
		}

		if(idLogradouro != null && !idLogradouro.equalsIgnoreCase("")){
			composicaoHql += " lg.id = " + idLogradouro + " and ";
		}

		if(idImovelCondominio != null && !idImovelCondominio.equalsIgnoreCase("")){
			composicaoHql += " ic.id = " + idImovelCondominio + " and";
		}

		if(idImovelPrincipal != null && !idImovelPrincipal.equalsIgnoreCase("")){
			composicaoHql += " ipri.id = " + idImovelPrincipal + " and";
		}

		if(situacaoAgua != null && !situacaoAgua.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "") && !situacaoAgua.equals("")){
			composicaoHql += " las.id = " + situacaoAgua + " and";
		}

		if(situacaoLigacaoEsgoto != null && !situacaoLigacaoEsgoto.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")
						&& !situacaoLigacaoEsgoto.equals("")){
			composicaoHql += " les.id = " + situacaoLigacaoEsgoto + " and";
		}

		if(perfilImovel != null && !perfilImovel.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "") && !perfilImovel.equals("")){
			composicaoHql += " ip.id = " + perfilImovel + " and";
		}

		if(tipoPoco != null && !tipoPoco.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "") && !tipoPoco.equals("")){
			composicaoHql += " pt.id = " + tipoPoco + " and";
		}

		if(tipoSituacaoEspecialFaturamento != null && !tipoSituacaoEspecialFaturamento.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")
						&& !tipoSituacaoEspecialFaturamento.equals("")){
			composicaoHql += " ft.id = " + tipoSituacaoEspecialFaturamento;
		}

		if(situacaoCobranca != null && !situacaoCobranca.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")
						&& !situacaoCobranca.equals("")){
			composicaoHql += " sc.id = " + situacaoCobranca + " and";
		}

		if(!diaVencimento.equalsIgnoreCase("") && diaVencimento.equalsIgnoreCase("sim")){
			composicaoHql += " and im.diaVencimento is not NULL and";
		}

		if(tipoSituacaoEspecialCobranca != null && !tipoSituacaoEspecialCobranca.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")
						&& !tipoSituacaoEspecialCobranca.equals("")){
			composicaoHql += " cst.id = " + tipoSituacaoEspecialCobranca + " and";
		}

		if(anormalidadeElo != null && !anormalidadeElo.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "") && !anormalidadeElo.equals("")){
			composicaoHql += " ea.id = " + anormalidadeElo + " and";
		}

		if(ocorrenciaCadastro != null && !ocorrenciaCadastro.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")
						&& !ocorrenciaCadastro.equals("")){
			composicaoHql += " co.id = " + ocorrenciaCadastro + " and";
		}

		if(tarifaConsumo != null && !tarifaConsumo.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "") && !tarifaConsumo.equals("")){
			composicaoHql += " ct.id = " + tarifaConsumo + " and";
		}

		String clienteTipo = "";

		String cliente = "";

		if(filtrarImovelOutrosCriteriosHelper.getTipoRelatorio().trim().equalsIgnoreCase("RelatorioTarifaSocial")){

			if(idClienteTipo != null && !idClienteTipo.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "") && !idClienteTipo.equals("")){
				clienteTipo = " im.id in (select im.id from ClienteImovelEconomia cie " + " inner join cie.cliente cl "
								+ " where cl.clienteTipo.id = " + idClienteTipo + ") and";
			}

			if(idCliente != null && !idCliente.equalsIgnoreCase("")
							&& !idCliente.equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
				cliente = " im.id in (select im.id from ClienteImovelEconomia cie " + " inner join cie.cliente cl " + " where cl.id = "
								+ idCliente + ") and";
			}

		}else if(filtrarImovelOutrosCriteriosHelper.getTipoRelatorio().trim().equalsIgnoreCase("consultarTarifaExcluida")){
			composicaoHql += " tsd.dataExclusao is not NULL and";

			if(idClienteTipo != null && !idClienteTipo.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "") && !idClienteTipo.equals("")){
				clienteTipo = " im.id in (select im.id from ClienteImovelEconomia cie " + " inner join cie.cliente cl "
								+ " where cl.clienteTipo.id = " + idClienteTipo + ") and";
			}

			if(idCliente != null && !idCliente.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "") && !idCliente.equals("")){
				cliente = " im.id in (select im.id from ClienteImovelEconomia cie " + " inner join cie.cliente cl " + " where cl.id = "
								+ idCliente + ") and";
			}

		}else{

			if(idClienteTipo != null && !idClienteTipo.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "") && !idClienteTipo.equals("")){
				composicaoHql += " ct.id = " + idClienteTipo + "ci.dataFimRelacao is NULL" + " and";
			}

			if(idCliente != null && !idCliente.equals("") && !idCliente.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
				composicaoHql += " cl.id = " + idCliente + " and ci.dataFimRelacao is NULL" + " and ";
			}

		}

		String subSelectImovelCobrancaSituacao = "";

		// Inicio Situacao Cobranca

		if(situacaoCobrancaID != null && !situacaoCobrancaID.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")
						&& !situacaoCobrancaID.equals("")){
			subSelectImovelCobrancaSituacao = " im.id in (select im.id from ImovelCobrancaSituacao ics " + " inner join ics.imovel im "
							+ " inner join ics.cobrancaSituacao cs " + " where cs.id = " + situacaoCobrancaID
							+ " and ics.dataRetiradaCobranca is null) and";
		}

		// Fim Situacao Cobranca

		// Select Medicao Historico

		String subSelectMedicaoHistorico = "";

		if(((intervaloMediaMinimaHidrometroInicial != null) && (!intervaloMediaMinimaHidrometroInicial.equals("")))
						&& ((intervaloMediaMinimaHidrometroFinal != null) && (!intervaloMediaMinimaHidrometroFinal.equals("")))
						&& (medicaoTipo != null && !medicaoTipo.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "") && !medicaoTipo
										.equals(""))
						&& (intervaloMediaMinimaImovelInicio != null && !intervaloMediaMinimaImovelInicio.equals(""))
						&& (intervaloMediaMinimaImovelFinal != null && !intervaloMediaMinimaImovelFinal.equals(""))){
			subSelectMedicaoHistorico = " im.id in " + "	(select la.id from MedicaoHistorico mh " + "	inner join mh.ligacaoAgua la "
							+ "	inner join mh.medicaoTipo mt " + "	where mh.consumoMedioHidrometro " + "	between  "
							+ intervaloMediaMinimaHidrometroInicial + "   and " + intervaloMediaMinimaHidrometroFinal + "	and mt.id = "
							+ medicaoTipo + "	and la.numeroConsumoMinimoAgua " + "	between " + intervaloMediaMinimaImovelInicio + "   and"
							+ intervaloMediaMinimaImovelFinal + ") and";
		}

		if(numeroPontosInicial != null && !numeroPontosInicial.trim().equalsIgnoreCase("") && numeroPontosFinal != null
						&& !numeroPontosFinal.trim().equalsIgnoreCase("")){
			composicaoHql += " im.numeroPontosUtilizacao between " + numeroPontosInicial + " and " + numeroPontosFinal + " and";
		}

		if(numeroMoradoresInicial != null && !numeroMoradoresInicial.trim().equalsIgnoreCase("") && numeroMoradoresFinal != null
						&& !numeroMoradoresFinal.trim().equalsIgnoreCase("")){
			composicaoHql += " im.numeroMorador between " + numeroMoradoresInicial + " and " + numeroMoradoresFinal + " and";
		}

		if(areaConstruidaInicial != null && !areaConstruidaInicial.trim().equalsIgnoreCase("") && areaConstruidaFinal != null
						&& !areaConstruidaFinal.trim().equalsIgnoreCase("")){
			composicaoHql += " im.areaConstruida between " + areaConstruidaInicial + " and " + areaConstruidaFinal + " and";
		}

		if(areaConstruidaFaixa != null && !areaConstruidaFaixa.trim().equalsIgnoreCase("")
						&& !areaConstruidaFaixa.trim().equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			composicaoHql += " acf.id = " + areaConstruidaFaixa + " and";
		}

		if(pocoTipo != null && !pocoTipo.trim().equalsIgnoreCase("")
						&& !pocoTipo.trim().equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			composicaoHql += " pt.id = " + pocoTipo + " and";
		}

		// Dados Informados Tarifa Social
		if(!Util.isVazioOuBranco(indicadorImovelTarifaSocial)){
			if(indicadorImovelTarifaSocial.equals("1")){
				composicaoHql += " tsde.dataExclusao is null and";
			}

			if(indicadorImovelTarifaSocial.equals("2")){
				composicaoHql += " tsde.dataExclusao <> null and";
			}
		}

		if(!Util.isVazioOuBranco(dataInicioImplantacao) && !Util.isVazioOuBranco(dataFimImplantacao)){
			composicaoHql += " tsde.dataImplantacao between " + dataInicioImplantacao + " and " + dataFimImplantacao + " and";
		}

		if(!Util.isVazioOuBranco(dataInicioExclusao) && !Util.isVazioOuBranco(dataFimExclusao)){
			composicaoHql += " tsde.dataExclusao between " + dataInicioExclusao + " and " + dataFimExclusao + " and";
		}

		if(!Util.isVazioOuBranco(idMotivoExclusao) && !idMotivoExclusao.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			composicaoHql += " tsde.tarifaSocialExclusaoMotivo.id = " + idMotivoExclusao + " and";
		}

		if(!Util.isVazioOuBranco(dataInicioValidadeCartao) && !Util.isVazioOuBranco(dataFimValidadeCartao)){
			composicaoHql += " tsde.dataValidadeCartao between " + dataInicioValidadeCartao + " and " + dataFimValidadeCartao + " and";
		}

		if(!Util.isVazioOuBranco(mesInicioAdesao) && !Util.isVazioOuBranco(mesFimAdesao)){
			composicaoHql += " tsde.numeroMesesAdesao between " + mesInicioAdesao + " and " + mesFimAdesao + " and";
		}

		if(!Util.isVazioOuBranco(tarifaSocialCartaoTipoId) && !tarifaSocialCartaoTipoId.equals("-1")){
			composicaoHql += " tsde.tarifaSocialCartaoTipo.id = " + tarifaSocialCartaoTipoId + " and";
		}

		if(!Util.isVazioOuBranco(tarifaSocialRendaTipoId) && !tarifaSocialRendaTipoId.equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			composicaoHql += " tsde.rendaTipo.id = " + tarifaSocialRendaTipoId + " and";
		}

		if(!Util.isVazioOuBranco(rendaInicial) && !Util.isVazioOuBranco(rendaFinal)){
			composicaoHql += " tsde.valorRendaFamiliar between " + rendaInicial + " and " + rendaFinal + " and";
		}

		if(!Util.isVazioOuBranco(celpeInicial) && !Util.isVazioOuBranco(celpeFinal)){
			composicaoHql += " tsde.consumoCelpe between " + celpeInicial + " and " + celpeFinal + " and";
		}

		if(!Util.isVazioOuBranco(dataInicioRecadastramento) && !Util.isVazioOuBranco(dataFimRecadastramento)){
			composicaoHql += " tsde.dataRecadastramento between " + dataInicioRecadastramento + " and " + dataFimRecadastramento + " and";
		}

		if(!Util.isVazioOuBranco(recadastramentoNumeroInicial) && !Util.isVazioOuBranco(recadastramentoNumeroFinal)){
			composicaoHql += " tsde.quantidadeRecadastramento between " + recadastramentoNumeroInicial + " and "
							+ recadastramentoNumeroFinal + " and";
		}

		retorno = composicaoHql + subSelectImovelCobrancaSituacao + subSelectMedicaoHistorico;

		if(filtrarImovelOutrosCriteriosHelper.getTipoRelatorio().trim().equalsIgnoreCase("RelatorioTarifaSocial")){
			retorno = retorno + clienteTipo + cliente;
		}

		return retorno;

	}

	// ----------------------- rhawi

	public Integer validarMesAnoReferencia(

	SituacaoEspecialFaturamentoHelper situacaoEspecialFaturamentoHelper)

	throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			String consulta = null;

			// Escolha de que tipo de consulta será

			if(situacaoEspecialFaturamentoHelper != null

			&& !situacaoEspecialFaturamentoHelper.getIdImovel().equals(""))

			consulta = "select max(fg.anoMesReferencia) from Imovel im"

			+ " inner join im.localidade lo"

			+ " inner join im.setorComercial sc"

			+ " inner join im.quadra qu"

			+ " inner join qu.rota rt"

			+ " inner join rt.faturamentoGrupo fg"

			+ " where im.id = "

			+ situacaoEspecialFaturamentoHelper.getIdImovel()

			+ " and"

			+ " (im.indicadorExclusao <> :idExclusao OR im.indicadorExclusao is null) and ";

			else{

				consulta = "select max(fg.anoMesReferencia) from Imovel im"

				+ " inner join im.localidade lo"

				+ " inner join im.setorComercial sc"

				+ " inner join im.quadra qu"

				+ " inner join qu.rota rt"

				+ " inner join rt.faturamentoGrupo fg"

				+ " where (im.indicadorExclusao <> :idExclusao OR im.indicadorExclusao is null) and ";

				// String idImovel =

				// situacaoEspecialFaturamentoHelper.getIdImovel();

				String idLocalidadeOrigem = situacaoEspecialFaturamentoHelper

				.getLocalidadeOrigemID();

				String idLocalidadeDestino = situacaoEspecialFaturamentoHelper

				.getLocalidadeDestinoID();

				String setorComercialOrigemID = situacaoEspecialFaturamentoHelper

				.getSetorComercialOrigemID();

				String setorComercialDestinoID = situacaoEspecialFaturamentoHelper

				.getSetorComercialDestinoID();

				String quadraOrigemID = situacaoEspecialFaturamentoHelper

				.getQuadraOrigemID();

				String quadraDestinoID = situacaoEspecialFaturamentoHelper

				.getQuadraDestinoID();

				String loteOrigem = situacaoEspecialFaturamentoHelper

				.getLoteOrigem();

				String loteDestino = situacaoEspecialFaturamentoHelper

				.getLoteDestino();

				String subLoteOrigem = situacaoEspecialFaturamentoHelper

				.getSubloteOrigem();

				String subLoteDestino = situacaoEspecialFaturamentoHelper

				.getSubloteDestino();

				/*
				 * if (!idImovel.equalsIgnoreCase("") &&
				 * !idImovel.equalsIgnoreCase("")) consulta += "imovel.id " +
				 * idImovel;
				 */

				if(idLocalidadeOrigem != null && !idLocalidadeOrigem.equals("")

				&& idLocalidadeDestino != null

				&& !idLocalidadeDestino.equals(""))

				consulta += " lo.id between " + idLocalidadeOrigem + " and "

				+ idLocalidadeDestino + " and ";

				if(setorComercialOrigemID != null

				&& !setorComercialOrigemID.equalsIgnoreCase("")

				&& !setorComercialOrigemID.equalsIgnoreCase(""))

				consulta += "sc.id between " + setorComercialOrigemID + " and "

				+ setorComercialDestinoID + " and ";

				if(!quadraOrigemID.equalsIgnoreCase("")

				&& !quadraOrigemID.equalsIgnoreCase(""))

				consulta += "qu.id between " + quadraOrigemID + " and "

				+ quadraDestinoID + " and ";

				if(!loteOrigem.equalsIgnoreCase("")

				&& !loteOrigem.equalsIgnoreCase(""))

				consulta += "im.lote between " + loteOrigem + " and "

				+ loteDestino + " and ";

				if(!subLoteOrigem.equalsIgnoreCase("")

				&& !subLoteOrigem.equalsIgnoreCase(""))

				consulta += "im.subLote between " + subLoteOrigem + " and "

				+ subLoteDestino + " and ";

			}

			// Consulta

			Integer retorno = (Integer) (session.createQuery(

			Util.formatarHQL(consulta, 4)).setShort("idExclusao",

			Imovel.IMOVEL_EXCLUIDO).uniqueResult());

			if(retorno == null){
				return 0;
			}

			return retorno;

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

	}

	/**
	 * Pesquisa o maior ano mes de referencia da tabela de faturamento grupo
	 * [UC0177] Informar Situacao Especial de Cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 18/03/2006
	 * @param situacaoEspecialCobrancaHelper
	 * @return
	 */

	public Integer validarMesAnoReferenciaCobranca(

	SituacaoEspecialCobrancaHelper situacaoEspecialCobrancaHelper)

	throws ErroRepositorioException{

		String consulta = null;

		// Escolha de que tipo de consulta será

		if(situacaoEspecialCobrancaHelper != null

		&& !situacaoEspecialCobrancaHelper.getIdImovel().equals(""))

		consulta = "select max(cg.anoMesReferencia) from Imovel im"

		+ " inner join im.localidade lo"

		+ " inner join im.setorComercial sc"

		+ " inner join im.quadra qu"

		+ " inner join im.rota rt"

		+ " inner join rt.cobrancaGrupo cg"

		+ " where im.id = "

		+ situacaoEspecialCobrancaHelper.getIdImovel()

		+ " and"

		+ " (im.indicadorExclusao <> :idExclusao OR im.indicadorExclusao is null) and";

		else{

			consulta = "select max(cg.anoMesReferencia) from Imovel im"

			+ " inner join im.localidade lo"

			+ " inner join im.setorComercial sc"

			+ " inner join im.quadra qu"

			+ " inner join im.rota rt"

			+ " inner join rt.cobrancaGrupo cg"

			+ " where (im.indicadorExclusao <> :idExclusao OR im.indicadorExclusao is null) and ";

			// String idImovel =

			// situacaoEspecialFaturamentoHelper.getIdImovel();

			String idLocalidadeOrigem = situacaoEspecialCobrancaHelper

			.getLocalidadeOrigemID();

			String idLocalidadeDestino = situacaoEspecialCobrancaHelper

			.getLocalidadeDestinoID();

			String setorComercialOrigemID = situacaoEspecialCobrancaHelper

			.getSetorComercialOrigemID();

			String setorComercialDestinoID = situacaoEspecialCobrancaHelper

			.getSetorComercialDestinoID();

			String quadraOrigemID = situacaoEspecialCobrancaHelper

			.getQuadraOrigemID();

			String quadraDestinoID = situacaoEspecialCobrancaHelper

			.getQuadraDestinoID();

			String loteOrigem = situacaoEspecialCobrancaHelper.getLoteOrigem();

			String loteDestino = situacaoEspecialCobrancaHelper

			.getLoteDestino();

			String subLoteOrigem = situacaoEspecialCobrancaHelper

			.getSubloteOrigem();

			String subLoteDestino = situacaoEspecialCobrancaHelper

			.getSubloteDestino();

			if(idLocalidadeOrigem != null && !idLocalidadeOrigem.equals("")

			&& idLocalidadeDestino != null

			&& !idLocalidadeDestino.equals(""))

			consulta += " lo.id between " + idLocalidadeOrigem + " and "

			+ idLocalidadeDestino + " and ";

			if(setorComercialOrigemID != null

			&& !setorComercialOrigemID.equalsIgnoreCase("")

			&& !setorComercialOrigemID.equalsIgnoreCase(""))

			consulta += "sc.id between " + setorComercialOrigemID + " and "

			+ setorComercialDestinoID + " and ";

			if(!quadraOrigemID.equalsIgnoreCase("")

			&& !quadraOrigemID.equalsIgnoreCase(""))

			consulta += "qu.id between " + quadraOrigemID + " and "

			+ quadraDestinoID + " and ";

			if(!loteOrigem.equalsIgnoreCase("")

			&& !loteOrigem.equalsIgnoreCase(""))

			consulta += "im.lote between " + loteOrigem + " and "

			+ loteDestino + " and ";

			if(!subLoteOrigem.equalsIgnoreCase("")

			&& !subLoteOrigem.equalsIgnoreCase(""))

			consulta += "im.subLote between " + subLoteOrigem + " and "

			+ subLoteDestino + " and ";

		}

		// Consulta

		Session session = HibernateUtil.getSession();

		try{

			Integer retorno = null;

			if(!Util.isVazioOuBranco(((Number) session.createQuery(Util.formatarHQL(consulta, 4)).setShort("idExclusao",
							Imovel.IMOVEL_EXCLUIDO).uniqueResult()))){
				retorno = ((Number) session.createQuery(Util.formatarHQL(consulta, 4)).setShort("idExclusao", Imovel.IMOVEL_EXCLUIDO)
								.uniqueResult()).intValue();
			}

			return retorno;

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

	}

	/**
	 * Atualiza os ids do faturamento situação tipo da tabela imóvel com o id do
	 * faturamento escolhido pelo usuario
	 * [UC0156] Informar Situacao Especial de Faturamento
	 * 
	 * @author Sávio Luiz
	 * @date 18/03/2006
	 * @param situacaoEspecialCobrancaHelper
	 * @return
	 * @author Andre R Nishimura
	 * @date 24/04/2009
	 *       Elaboraçao de tratamento para casos em que a coleçao de imoveis vier com mais de mil
	 *       itens, evitando uma SQLException
	 */

	public void atualizarFaturamentoSituacaoTipo(Collection colecaoIdsImoveis, Integer idFaturamentoTipo) throws ErroRepositorioException{

		String consulta = "";
		Session session = HibernateUtil.getSession();

		try{

			if(colecaoIdsImoveis != null && !colecaoIdsImoveis.isEmpty()){

				consulta = "update gcom.cadastro.imovel.Imovel set "
								+ "ftst_id = :idFaturamentoSituacao,imov_tmultimaalteracao = :ultimaAlteracao where imov_id IN (:ids)";

				if(colecaoIdsImoveis.size() < 1000){
					session.createQuery(consulta).setInteger("idFaturamentoSituacao", idFaturamentoTipo.intValue())
									.setParameterList("ids", colecaoIdsImoveis).setTimestamp("ultimaAlteracao", new Date()).executeUpdate();

				}else{

					Iterator iterator = colecaoIdsImoveis.iterator();
					Collection temp = new ArrayList();
					while(iterator.hasNext()){
						while(temp.size() != 100 && iterator.hasNext()){
							temp.add(iterator.next());
						}
						session.createQuery(consulta).setInteger("idFaturamentoSituacao", idFaturamentoTipo.intValue())
										.setParameterList("ids", temp).setTimestamp("ultimaAlteracao", new Date()).executeUpdate();
						temp.clear();
					}
				}
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
	 * Atualiza o id da cobrança situação tipo da tabela imóvel com o id da
	 * situação escolhido pelo usuario
	 * [UC0177] Informar Situacao Especial de Cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 18/03/2006
	 * @param situacaoEspecialCobrancaHelper
	 * @return
	 */

	public void atualizarCobrancaSituacaoTipo(Collection colecaoIdsImoveis,

	Integer idCobrancaTipo) throws ErroRepositorioException{

		String consulta = "";

		Session session = HibernateUtil.getSession();

		try{

			if(colecaoIdsImoveis != null && !colecaoIdsImoveis.isEmpty()){

				consulta = "update gcom.cadastro.imovel.Imovel set "

				+ "cbsp_id = :idCobrancaSituacao,imov_tmultimaalteracao = :ultimaAlteracao where imov_id IN (:ids)";

				session.createQuery(consulta).setInteger("idCobrancaSituacao",

				idCobrancaTipo.intValue()).setParameterList("ids",

				colecaoIdsImoveis).setTimestamp("ultimaAlteracao",

				new Date()).executeUpdate();

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
	 * Seta para null o id da cobrança situação tipo da tabela imóvel
	 * [UC0156] Informar Situacao Especial Faturamento
	 * 
	 * @author Sávio Luiz
	 * @date 18/03/2006
	 * @param situacaoEspecialCobrancaHelper
	 * @return
	 */

	public void retirarSituacaoEspecialFaturamento(Collection colecaoIdsImoveis)

	throws ErroRepositorioException{

		String consulta = "";

		Session session = HibernateUtil.getSession();

		try{

			if(colecaoIdsImoveis != null && !colecaoIdsImoveis.isEmpty()){

				consulta = "update gcom.cadastro.imovel.Imovel set "

				+ "ftst_id = null,imov_tmultimaalteracao = :ultimaAlteracao where imov_id IN (:ids)";

				session.createQuery(consulta).setParameterList("ids",

				colecaoIdsImoveis).setTimestamp("ultimaAlteracao",

				new Date()).executeUpdate();

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
	 * Seta para null o id da cobrança situação tipo da tabela imóvel
	 * [UC0177] Informar Situacao Especial de Cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 18/03/2006
	 * @param situacaoEspecialCobrancaHelper
	 * @return
	 */

	public void retirarSituacaoEspecialCobranca(Collection colecaoIdsImoveis)

	throws ErroRepositorioException{

		String consulta = "";

		Session session = HibernateUtil.getSession();

		try{

			if(colecaoIdsImoveis != null && !colecaoIdsImoveis.isEmpty()){

				consulta = "update gcom.cadastro.imovel.Imovel set "

				+ "cbsp_id = null,imov_tmultimaalteracao = :ultimaAlteracao where imov_id IN (:ids)";

				session.createQuery(consulta).setParameterList("ids",

				colecaoIdsImoveis).setTimestamp("ultimaAlteracao",

				new Date()).executeUpdate();

			}

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

	}

	public Collection<Integer> pesquisarImoveisIds(Integer rotaId)

	throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = "select im.id from Imovel im "

			+ "inner join im.quadra qd "

			+ "inner join qd.rota rota where rota.id = :rotaId and im.indicadorImovelCondominio <> 1";

			retorno = session.createQuery(consulta).setInteger("rotaId",

			rotaId.intValue()).list();

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
	 * Consulta se uma conta já existe no histórico como parcelada, evitando assim a geração da
	 * conta de novo
	 * 
	 * @param imovelId
	 * @param anoMesReferencia
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object pesquisarImovelIdComContaHistoricoParcelado(Integer imovelId, Integer anoMesReferencia) throws ErroRepositorioException{

		Object retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = "select ct.id from ContaHistorico ct "

			+ "inner join ct.imovel im "

			+ "where im.id = :imovelId and ct.anoMesReferenciaConta = :anoMesReferencia"

			+ " and ct.debitoCreditoSituacaoAtual.id  = " + DebitoCreditoSituacao.PARCELADA;

			retorno = session.createQuery(consulta).setInteger("imovelId",

			imovelId.intValue()).setInteger("anoMesReferencia",

			anoMesReferencia).setMaxResults(1).uniqueResult();

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
	 * [UC0101] - Consistir Leituras e Calcular Consumos
	 * [FS0005] - Verificar existência da conta
	 * 
	 * @author Anderson Italo
	 * @date 02/08/2011
	 * @return Object
	 * @exception ErroRepositorioException
	 */
	public Object pesquisarImovelIdComConta(Integer imovelId, Integer anoMesReferencia) throws ErroRepositorioException{

		Object retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append("select ct.id from Conta ct ");
			consulta.append("inner join ct.imovel im ");
			consulta.append("where im.id = :imovelId and ct.referencia = :anoMesReferencia");

			retorno = session.createQuery(consulta.toString()).setInteger("imovelId", imovelId.intValue()).setInteger("anoMesReferencia",
							anoMesReferencia).setMaxResults(1).uniqueResult();
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
	 * Obtém o indicador de existência de hidrômetro para o imóvel, caso exista
	 * retorna 1(um) indicando SIM caso contrário retorna 2(dois) indicando NÃO
	 * [UC0307] Obter Indicador de Existência de Hidrômetro no Imóvel
	 * 
	 * @author Thiago Toscano
	 * @date 02/06/2006
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 *             Alterado conforme OC0857313
	 */

	public Integer obterIndicadorExistenciaHidrometroImovel(Integer idImovel)

	throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = " 	select "

			+ "		case when "

			+ "		(((imovel.ligacaoAguaSituacao.indicadorFaturamentoSituacao = "

			// + LigacaoAguaSituacao.LIGADO

							// + " 			or imovel.ligacaoAguaSituacao.id = "

							// + LigacaoAguaSituacao.CORTADO

							+ LigacaoAguaSituacao.FATURAMENTO_ATIVO

							+ ") "

							+ "		and ligacaoAgua.hidrometroInstalacaoHistorico is not null)  "

							+ "		or  "

							+ "		(imovel.ligacaoEsgotoSituacao.indicadorFaturamentoSituacao = "

							// + LigacaoEsgotoSituacao.LIGADO

							+ LigacaoEsgotoSituacao.FATURAMENTO_ATIVO

							+ " and imovel.hidrometroInstalacaoHistorico is not null)) "

							+ "		then 1 else 2 end "

							+ "	from  "

							+ "		gcom.cadastro.imovel.Imovel imovel "

							+ "		left join imovel.ligacaoAgua ligacaoAgua "

							+ "		inner join imovel.hidrometroInstalacaoHistorico hidrInstHist1 "

							+ "		inner join ligacaoAgua.hidrometroInstalacaoHistorico hidrInstHist2 "

							+ "		left join imovel.ligacaoAguaSituacao ligacaoAguaSituacao "

							+ "		left join imovel.ligacaoEsgoto ligacaoEsgoto "

							+ "		left join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao "

							+ "	where  " + "		imovel.id = :idImovel ";

			retorno = (Integer) session.createQuery(consulta).setInteger(

			"idImovel", idImovel.intValue()).uniqueResult();

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	// /**

	// *

	// * Gerar Relatório de Imóvel Outros Critérios

	// *

	// * @author Rafael Corrêa

	// * @date 24/07/2006

	// *

	// */

	// public Collection gerarRelatorioImovelOutrosCriterios(

	// String idImovelCondominio, String idImovelPrincipal,

	// String idNomeConta, String idSituacaoLigacaoAgua,

	// String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,

	// String idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto,

	// String consumoMinimoFinalEsgoto,

	// String intervaloValorPercentualEsgotoInicial,

	// String intervaloValorPercentualEsgotoFinal,

	// String intervaloMediaMinimaImovelInicial,

	// String intervaloMediaMinimaImovelFinal,

	// String intervaloMediaMinimaHidrometroInicial,

	// String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,

	// String idPocoTipo, String idFaturamentoSituacaoTipo,

	// String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,

	// String idEloAnormalidade, String areaConstruidaInicial,

	// String areaConstruidaFinal, String idCadastroOcorrencia,

	// String idConsumoTarifa, String idGerenciaRegional,

	// String idLocalidadeInicial, String idLocalidadeFinal,

	// String setorComercialInicial, String setorComercialFinal,

	// String quadraInicial, String quadraFinal, String loteOrigem,

	// String loteDestno, String cep, String logradouro, String bairro,

	// String municipio, String idTipoMedicao, String indicadorMedicao,

	// String idSubCategoria, String idCategoria,

	// String quantidadeEconomiasInicial, String quantidadeEconomiasFinal,

	// String diaVencimento, String idCliente, String idClienteTipo,

	// String idClienteRelacaoTipo, String numeroPontosInicial,

	// String numeroPontosFinal, String numeroMoradoresInicial,

	// String numeroMoradoresFinal, String idAreaConstruidaFaixa)

	// throws ErroRepositorioException {

	//

	// Collection retorno = null;

	//

	// Session session = HibernateUtil.getSession();

	// String consulta;

	//

	// try {

	// consulta = "select distinct "

	// + "gerenciaRegional.id,"

	// + "gerenciaRegional.nomeAbreviado,"

	// + "localidade.id,"

	// + "localidade.descricao,"

	// + "imovel.id,"

	// + "imovel.quantidadeEconomias,"

	// + "setorComercial.codigo,"

	// + "setorComercial.descricao,"

	// + "quadra.numeroQuadra,"

	// + "imovel.lote,"

	// + "imovel.subLote,"

	// + "ligacaoAguaSituacao.descricaoAbreviado,"

	// + "ligacaoEsgotoSituacao.descricaoAbreviado,"

	// + "ligacaoEsgoto.percentual,"

	// + "ligacaoEsgoto.dataLigacao, "

	// + "ligacaoAgua.dataLigacao, "

	// //OK

	// + "clienteUsuario.id, "

	// + "clienteUsuario.nome, "

	// + "clienteResponsavel.id, "

	// + "clienteResponsavel.nome, "

	//

	// + "logradouro.nome, "

	// + "logradouroTipo.descricao, "

	// + "logradouroTitulo.descricao, "

	// + "cep.codigo, "

	// + "enderecoReferencia.descricao, "

	// + "imovel.complementoEndereco, "

	// + "imovel.numeroImovel, "

	// + "bairro.nome, "

	// + "municipio.nome, "

	// + "unidadeFederacao.sigla, "

	//

	// + "imovel.indicadorImovelCondominio, "

	// + "imovel.numeroMorador, "

	// + "imovelCondominio.id, "

	// + "imovelPrincipal.id, "

	// + "imovel.numeroPontosUtilizacao, "

	// + "imovelPerfil.descricao, "

	// + "areaConstruidaFaixa.maiorFaixa, "

	// + "areaConstruidaFaixa.menorFaixa, "

	// + "imovel.areaConstruida, "

	// + "pavimentoCalcada.descricao, "

	// + "pavimentoRua.descricao, "

	// + "despejo.descricao, "

	// + "reservatorioVolumeFaixaSuperior.volumeMenorFaixa, "

	// + "reservatorioVolumeFaixaSuperior.volumeMaiorFaixa, "

	//

	// + "imovel.volumeReservatorioSuperior, "

	//

	// + "reservatorioVolumeFaixaInferior.volumeMenorFaixa, "

	// + "reservatorioVolumeFaixaInferior.volumeMaiorFaixa, "

	// + "imovel.volumeReservatorioInferior, "

	// + "piscinaVolumeFaixa.volumeMenorFaixa, "

	// + "piscinaVolumeFaixa.volumeMaiorFaixa, "

	// + "imovel.volumePiscina, "

	// + "pocoTipo.descricao, "

	//

	// + "ligacaoAguaDiametro.descricao, "

	// + "ligacaoAguaMaterial.descricao, "

	// + "ligacaoEsgotoDiametro.descricao, "

	// + "ligacaoEsgotoMaterial.descricao, "

	// + "ligacaoEsgoto.consumoMinimo, "

	// + "ligacaoEsgoto.percentualAguaConsumidaColetada, "

	// + "ligacaoEsgoto.percentual, "

	//

	// + "hidrometroAgua.numero, "

	// + "hidrometroAgua.anoFabricacao, "

	// + "hidrometroCapacidadeAgua.descricao, "

	// + "hidrometroMarcaAgua.descricao, "

	// + "hidrometroDiametroAgua.descricao, "

	// + "hidrometroTipoAgua.descricao, "

	// + "hidrometroInstalacaoHistorico.dataInstalacao, "

	// + "hidrometroLocalInstalacaoAgua.descricao, "

	// + "hidrometroProtecaoAgua.descricao, "

	// + "hidrometroInstalacaoHistorico.indicadorExistenciaCavalete, "

	//

	// + "hidrometroEsgoto.numero, "

	// + "hidrometroEsgoto.anoFabricacao, "

	// + "hidrometroCapacidadeEsgoto.descricao, "

	// + "hidrometroMarcaEsgoto.descricao, "

	// + "hidrometroDiametroEsgoto.descricao, "

	// + "hidrometroTipoEsgoto.descricao, "

	// + "hidrometroInstalacaoHistoricoImovel.dataInstalacao, "

	// + "hidrometroLocalInstalacaoEsgoto.descricao, "

	// + "hidrometroProtecaoEsgoto.descricao, "

	// + "hidrometroInstalacaoHistoricoImovel.indicadorExistenciaCavalete, "

	//

	// + "ligacaoAgua.numeroConsumoMinimoAgua, "

	// + "ligacaoEsgoto.consumoMinimo "

	//

	// + " from ImovelSubcategoria imovelSubcategoria "

	// + " inner join imovelSubcategoria.comp_id.imovel imovel "

	// + " left join imovelSubcategoria.comp_id.subcategoria subcategoria "

	// + " left join subcategoria.categoria categoria "

	// + " inner join imovel.localidade localidade "

	// + " inner join localidade.gerenciaRegional gerenciaRegional "

	// + " inner join imovel.setorComercial setorComercial "

	// + " left join imovel.logradouroBairro logradouroBairro "

	// + " left join logradouroBairro.bairro bairro "

	// + " left join bairro.municipio municipio "

	// + " left join logradouroBairro.bairro bairro "

	// + " inner join imovel.quadra quadra "

	// + " left join imovel.logradouroCep logradouroCep "

	// + " left join logradouroCep.cep cep "

	// + " left join imovel.logradouroCep logradouroCep "

	// + " left join logradouroCep.logradouro logradouro "

	// + " left join imovel.imovelCondominio imovelCondominio "

	// + " left join imovel.imovelPrincipal imovelPrincipal "

	// + " left join imovel.nomeConta nomeConta "

	//

	// + " left join imovel.ligacaoAguaSituacao ligacaoAguaSituacao "

	// + " left join imovel.ligacaoAgua ligacaoAgua "

	//

	// + " left join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao "

	// + " left join imovel.ligacaoEsgoto ligacaoEsgoto "

	//

	// + " left join imovel.imovelPerfil imovelPerfil "

	// + " left join imovel.pocoTipo pocoTipo "

	// + " left join imovel.faturamentoTipo faturamentoTipo "

	// + " left join imovel.cobrancaSituacaoTipo cobrancaSituacaoTipo "

	// + " left join imovel.faturamentoSituacaoTipo faturamentoSituacaoTipo "

	// + " left join imovel.eloAnormalidade eloAnormalidade "

	// + " left join imovel.cadastroOcorrencia cadastroOcorrencia "

	// + " left join imovel.areaConstruidaFaixa areaConstruidaFaixa "

	// + " left join imovel.consumoTarifa consumoTarifa "

	// + " left join ligacaoAgua.hidrometroInstalacaoHistorico

	// hidrometroInstalacaoHistorico "

	// + " left join imovel.hidrometroInstalacaoHistorico

	// hidrometroInstalacaoHistoricoImovel "

	// + " left join imovel.consumosHistoricos consumosHistorico "

	// + " left join imovel.medicaoHistoricos medicaoHistorico "

	//

	//

	//

	// //FIM QUERY RAFAEL SANTOS

	//

	// + " left join logradouro.logradouroTitulo logradouroTitulo "

	// + " left join logradouro.logradouroTipo logradouroTipo "

	// + " left join imovel.enderecoReferencia enderecoReferencia "

	// + " left join municipio.unidadeFederacao unidadeFederacao "

	// + " left join imovel.reservatorioVolumeFaixaSuperior

	// reservatorioVolumeFaixaSuperior "

	// + " left join imovel.reservatorioVolumeFaixaInferior

	// reservatorioVolumeFaixaInferior "

	// + " left join imovel.piscinaVolumeFaixa piscinaVolumeFaixa "

	// + " left join imovel.pavimentoCalcada pavimentoCalcada "

	// + " left join imovel.pavimentoRua pavimentoRua "

	// + " left join imovel.despejo despejo "

	// + " left join ligacaoAgua.ligacaoAguaDiametro ligacaoAguaDiametro "

	// + " left join ligacaoAgua.ligacaoAguaMaterial ligacaoAguaMaterial "

	// + " left join ligacaoEsgoto.ligacaoEsgotoDiametro ligacaoEsgotoDiametro "

	// + " left join ligacaoEsgoto.ligacaoEsgotoMaterial ligacaoEsgotoMaterial "

	// + " left join hidrometroInstalacaoHistorico.hidrometro hidrometroAgua "

	// + " left join hidrometroAgua.hidrometroCapacidade

	// hidrometroCapacidadeAgua "

	// + " left join hidrometroAgua.hidrometroMarca hidrometroMarcaAgua "

	// + " left join hidrometroAgua.hidrometroDiametro hidrometroDiametroAgua "

	// + " left join hidrometroAgua.hidrometroTipo hidrometroTipoAgua "

	// + " left join hidrometroInstalacaoHistorico.hidrometroLocalInstalacao

	// hidrometroLocalInstalacaoAgua "

	// + " left join hidrometroInstalacaoHistorico.hidrometroProtecao

	// hidrometroProtecaoAgua "

	// + " left join hidrometroInstalacaoHistoricoImovel.hidrometro

	// hidrometroEsgoto "

	// + " left join hidrometroEsgoto.hidrometroCapacidade

	// hidrometroCapacidadeEsgoto "

	// + " left join hidrometroEsgoto.hidrometroMarca hidrometroMarcaEsgoto "

	// + " left join hidrometroEsgoto.hidrometroDiametro

	// hidrometroDiametroEsgoto "

	// + " left join hidrometroEsgoto.hidrometroTipo hidrometroTipoEsgoto "

	// + " left join

	// hidrometroInstalacaoHistoricoImovel.hidrometroLocalInstalacao

	// hidrometroLocalInstalacaoEsgoto "

	// + " left join hidrometroInstalacaoHistoricoImovel.hidrometroProtecao

	// hidrometroProtecaoEsgoto "

	//

	//

	// //REVER

	// + " left join imovel.clienteImoveis clienteImoveis "

	// + " left join clienteImoveis.clienteRelacaoTipo clienteRelacaoTipo "

	// + " left join clienteImoveis.cliente cliente "

	// + " left join cliente.clienteTipo clienteTipo "

	// //+ " left join imovel.clienteImoveis clienteImoveisUsuario "

	//

	// + " left outer join imovel.clienteImoveis clienteImoveisUsuario with

	// (clienteImoveisUsuario.clienteRelacaoTipo.id = "

	// + ClienteRelacaoTipo.USUARIO.toString()

	// + ") and clienteImoveisUsuario.dataFimRelacao is null "

	// + " left outer join clienteImoveisUsuario.cliente clienteUsuario "

	// +

	//

	// " left outer join imovel.clienteImoveis clienteImoveisReposanvel with

	// (clienteImoveisReposanvel.clienteRelacaoTipo.id = "

	// + ClienteRelacaoTipo.RESPONSAVEL.toString()

	// + ") and clienteImoveisReposanvel.dataFimRelacao is null "

	// + " left outer join clienteImoveisReposanvel.cliente clienteResponsavel

	// ";

	//

	// consulta = consulta

	// + montarCondicaoWhereFiltrarImovelOutrosCriterio(

	// idImovelCondominio, idImovelPrincipal, idNomeConta,

	// idSituacaoLigacaoAgua, consumoMinimoInicialAgua,

	// consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto,

	// consumoMinimoInicialEsgoto,

	// consumoMinimoFinalEsgoto,

	// intervaloValorPercentualEsgotoInicial,

	// intervaloValorPercentualEsgotoFinal,

	// intervaloMediaMinimaImovelInicial,

	// intervaloMediaMinimaImovelFinal,

	// intervaloMediaMinimaHidrometroInicial,

	// intervaloMediaMinimaHidrometroFinal,

	// idImovelPerfil, idPocoTipo,

	// idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo,

	// idSituacaoEspecialCobranca, idEloAnormalidade,

	// areaConstruidaInicial, areaConstruidaFinal,

	// idCadastroOcorrencia, idConsumoTarifa,

	// idGerenciaRegional, idLocalidadeInicial,

	// idLocalidadeFinal, setorComercialInicial,

	// setorComercialFinal, quadraInicial, quadraFinal,

	// loteOrigem, loteDestno, cep, logradouro, bairro,

	// municipio, idTipoMedicao, indicadorMedicao,

	// idSubCategoria, idCategoria,

	// quantidadeEconomiasInicial,

	// quantidadeEconomiasFinal, diaVencimento, idCliente,

	// idClienteTipo, idClienteRelacaoTipo,

	// numeroPontosInicial, numeroPontosFinal,

	// numeroMoradoresInicial, numeroMoradoresFinal,

	// idAreaConstruidaFaixa);

	//

	// /*

	// * # COLOCANDO O VALOR NAS CONDIÇÕES#

	// */

	//

	// /*

	// * consulta = consulta + " consumosHistorico.referenciaFaturamento = " +

	// * "(select max(consumoHistorico.referenciaFaturamento) from

	// * ConsumoHistorico consumoHistorico " + " left join

	// * consumoHistorico.imovel imovelConsumoHistorico " + "where

	// * imovelConsumoHistorico.id = imovel.id) and ";

	// */

	//

	// Query query = session.createQuery(consulta.substring(0, (consulta

	// .length() - 5)));

	//

	// informarDadosQueryFiltrarImovelOutrosCriterio(query,

	// idImovelCondominio, idImovelPrincipal, idNomeConta,

	// idSituacaoLigacaoAgua, consumoMinimoInicialAgua,

	// consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto,

	// consumoMinimoInicialEsgoto, consumoMinimoFinalEsgoto,

	// intervaloValorPercentualEsgotoInicial,

	// intervaloValorPercentualEsgotoFinal,

	// intervaloMediaMinimaImovelInicial,

	// intervaloMediaMinimaImovelFinal,

	// intervaloMediaMinimaHidrometroInicial,

	// intervaloMediaMinimaHidrometroFinal, idImovelPerfil,

	// idPocoTipo, idFaturamentoSituacaoTipo,

	// idCobrancaSituacaoTipo, idSituacaoEspecialCobranca,

	// idEloAnormalidade, areaConstruidaInicial,

	// areaConstruidaFinal, idCadastroOcorrencia, idConsumoTarifa,

	// idGerenciaRegional, idLocalidadeInicial, idLocalidadeFinal,

	// setorComercialInicial, setorComercialFinal, quadraInicial,

	// quadraFinal, loteOrigem, loteDestno, cep, logradouro,

	// bairro, municipio, idTipoMedicao, indicadorMedicao,

	// idSubCategoria, idCategoria, quantidadeEconomiasInicial,

	// quantidadeEconomiasFinal, diaVencimento, idCliente,

	// idClienteTipo, idClienteRelacaoTipo, numeroPontosInicial,

	// numeroPontosFinal, numeroMoradoresInicial,

	// numeroMoradoresFinal, idAreaConstruidaFaixa);

	//

	// retorno = query.list();

	//

	// } catch (HibernateException e) {

	// // levanta a exceção para a próxima camada

	// throw new ErroRepositorioException(e, "Erro no Hibernate");

	// } finally {

	// // fecha a sessão

	// HibernateUtil.closeSession(session);

	// }

	//

	// return retorno;

	// }

	/**
	 * [UC0164]Gerar Relatório de Imóvel Outros Critérios
	 * Monta a Condição do where do Filtrar Imoveis Outros Criterios
	 * 
	 * @author Rafael Santos
	 * @date 24/07/2006
	 * @deprecated
	 */
	public String montarCondicaoWhereFiltrarImovelOutrosCriterio(String idImovelCondominio, String idImovelPrincipal, String idNomeConta,
					String idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,
					String intervaloValorPercentualEsgotoInicial, String intervaloValorPercentualEsgotoFinal,
					String intervaloMediaMinimaImovelInicial, String intervaloMediaMinimaImovelFinal,
					String intervaloMediaMinimaHidrometroInicial, String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,
					String idPocoTipo, String idFaturamentoSituacaoTipo, String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,
					String idEloAnormalidade, String areaConstruidaInicial, String areaConstruidaFinal, String idCadastroOcorrencia,
					String idConsumoTarifa, String idGerenciaRegional, String idLocalidadeInicial, String idLocalidadeFinal,
					String setorComercialInicial, String setorComercialFinal, String quadraInicial, String quadraFinal, String loteOrigem,
					String loteDestno, String cep, String logradouro, String bairro, String municipio, String idTipoMedicao,
					String indicadorMedicao, String idSubCategoria, String idCategoria, String quantidadeEconomiasInicial,
					String quantidadeEconomiasFinal, String diaVencimento, String idCliente, String idClienteTipo,
					String idClienteRelacaoTipo, String numeroPontosInicial, String numeroPontosFinal, String numeroMoradoresInicial,
					String numeroMoradoresFinal, String idAreaConstruidaFaixa){

		String consulta = "";

		/*
		 * ## CONDIÇÕES ##
		 */

		consulta = consulta + " where imovel.indicadorExclusao != " + Imovel.IMOVEL_EXCLUIDO + " and  ";

		// cliente
		if(idCliente != null && !idCliente.equals("")
						&& !idCliente.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " cliente.id = :idCliente  and  ";
		}

		// cliente tipo
		if(idClienteTipo != null && !idClienteTipo.equals("")
						&& !idClienteTipo.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " clienteTipo.id = :idClienteTipo  and  ";
		}

		// cliente relacao tipo
		if(idClienteRelacaoTipo != null
						&& !idClienteRelacaoTipo.equals("")
						&& !idClienteRelacaoTipo.trim()
										.equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " clienteRelacaoTipo.id = :idClienteRelacaoTipo  and  ";
		}

		// gerencia regional

		if(idGerenciaRegional != null && !idGerenciaRegional.equals("")
						&& !idGerenciaRegional.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " gerenciaRegional.id = :idGerenciaRegional and ";
		}

		// localidade inicial e final

		if(((idLocalidadeInicial != null && !idLocalidadeInicial.equals("") && !idLocalidadeInicial.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())) && (idLocalidadeFinal != null
						&& !idLocalidadeFinal.equals("") && !idLocalidadeFinal.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())))){
			consulta = consulta + " localidade.id >= :idLocalidadeInicial and localidade.id <= :idLocalidadeFinal  and  ";
		}

		// setor comercial inicial e final
		if(((setorComercialInicial != null && !setorComercialInicial.equals("") && !setorComercialInicial.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())) && (setorComercialFinal != null
						&& !setorComercialFinal.equals("") && !setorComercialFinal.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())))){
			consulta = consulta
							+ " setorComercial.codigo >= :setorComercialInicial and setorComercial.codigo <= :setorComercialFinal  and  ";
		}

		// quadra
		if((quadraInicial != null && !quadraInicial.equals("") && !quadraInicial.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (quadraFinal != null && !quadraFinal.equals("") && !quadraFinal.trim().equalsIgnoreCase(
										Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			consulta = consulta + " quadra.numeroQuadra >= :quadraInicial and quadra.numeroQuadra <= :quadraFinal and  ";
		}

		// lote
		if((loteOrigem != null && !loteOrigem.equals("") && !loteOrigem.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (loteDestno != null && !loteDestno.equals("") && !loteDestno.trim().equalsIgnoreCase(
										Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			consulta = consulta + " imovel.lote >= :loteOrigem  and  imovel.lote <= :loteDestino";
		}

		// cep
		if(cep != null && !cep.equals("")
						&& !cep.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " cep.codigo = :cep  and  ";
		}

		// logradouro
		if(logradouro != null && !logradouro.equals("")
						&& !logradouro.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " logradouro.id = :logradouro  and  ";
		}

		// bairro
		if(bairro != null && !bairro.equals("")
						&& !bairro.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " bairro.codigo = :bairro  and  ";
		}

		// municipio
		if(municipio != null && !municipio.equals("")
						&& !municipio.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " municipio.id = :municipio  and  ";
		}

		// consumo minimo agua inicial e final
		if((consumoMinimoInicialAgua != null && !consumoMinimoInicialAgua.equals("") && !consumoMinimoInicialAgua.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (consumoMinimoFinalAgua != null && !consumoMinimoFinalAgua.equals("") && !consumoMinimoFinalAgua.trim()
										.equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			consulta = consulta
							+ "ligacaoAgua.numeroConsumoMinimoAgua >= :consumoMinimoInicialAgua and ligacaoAgua.numeroConsumoMinimoAgua <= :consumoMinimoFinalAgua  and  ";
		}

		// consumo minimo esgoto inicial e final
		if((consumoMinimoInicialEsgoto != null && !consumoMinimoInicialEsgoto.equals("") && !consumoMinimoInicialEsgoto.trim()
						.equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (consumoMinimoFinalEsgoto != null && !consumoMinimoFinalEsgoto.equals("") && !consumoMinimoFinalEsgoto.trim()
										.equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			consulta = consulta
							+ "ligacaoEsgoto.consumoMinimo >= :consumoMinimoInicialEsgoto and ligacaoEsgoto.consumoMinimo <= :consumoMinimoFinalEsgoto  and  ";
		}

		// percentual esgoto inicial e final
		if((intervaloValorPercentualEsgotoInicial != null && !intervaloValorPercentualEsgotoInicial.equals("") && !intervaloValorPercentualEsgotoInicial
						.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (intervaloValorPercentualEsgotoFinal != null && !intervaloValorPercentualEsgotoFinal.equals("") && !intervaloValorPercentualEsgotoFinal
										.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			consulta = consulta
							+ "ligacaoEsgoto.percentual >= :intervaloValorPercentualEsgotoInicial and ligacaoEsgoto.percentual <= :intervaloValorPercentualEsgotoFinal  and  ";
		}

		// indicador medição
		if(indicadorMedicao != null && indicadorMedicao.equals("comMedicao")){
			// tipo medicao
			if(idTipoMedicao != null && idTipoMedicao.equals(MedicaoTipo.LIGACAO_AGUA.toString())){
				consulta = consulta + "hidrometroInstalacaoHistorico.id is not null  and  ";
			}else if(idTipoMedicao != null && idTipoMedicao.equals(MedicaoTipo.POCO.toString())){
				consulta = consulta + "hidrometroInstalacaoHistoricoImovel.id is not null and  ";
			}else{
				consulta = consulta + "(hidrometroInstalacaoHistorico.id is not null or ";
				consulta = consulta + "hidrometroInstalacaoHistoricoImovel.id is not null) and  ";
			}

		}else if(indicadorMedicao != null && indicadorMedicao.equals("semMedicao")){
			consulta = consulta + "(hidrometroInstalacaoHistorico.id is null and";
			consulta = consulta + "hidrometroInstalacaoHistoricoImovel.id is null) and  ";
		}

		// imovel condominio
		if(idImovelCondominio != null && !idImovelCondominio.equals("")
						&& !idImovelCondominio.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " imovelCondominio.id = :idImovelCondominio ";
		}

		// imovel principal
		if(idImovelPrincipal != null && !idImovelPrincipal.equals("")
						&& !idImovelPrincipal.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " imovelPrincipal.id: = idImovelPrincipal ";
		}

		// nome conta
		// if (idNomeConta != null
		// && !idNomeConta.equals("")
		// && !idNomeConta.trim().equalsIgnoreCase(
		// new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)
		// .toString())) {
		// consulta = consulta + " nomeConta.id = :idNomeConta and ";
		// }

		// situação da ligação de agua
		if(idSituacaoLigacaoAgua != null
						&& !idSituacaoLigacaoAgua.equals("")
						&& !idSituacaoLigacaoAgua.trim().equalsIgnoreCase(
										Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " ligacaoAguaSituacao.id = :idSituacaoLigacaoAgua  and  ";
		}

		// situação ligação de esgoto
		if(idSituacaoLigacaoEsgoto != null
						&& !idSituacaoLigacaoEsgoto.equals("")
						&& !idSituacaoLigacaoEsgoto.trim().equalsIgnoreCase(
										Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " ligacaoEsgotoSituacao.id = :idSituacaoLigacaoEsgoto  and  ";
		}

		// imovel Perfil
		if(idImovelPerfil != null && !idImovelPerfil.equals("")
						&& !idImovelPerfil.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " imovelPerfil.id = :idImovelPerfil  and  ";
		}

		// poço tipo
		if(idPocoTipo != null && !idPocoTipo.equals("")
						&& !idPocoTipo.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " pocoTipo.id = :idPocoTipo  and  ";
		}

		// faturamento situacao tipo
		if(idFaturamentoSituacaoTipo != null
						&& !idFaturamentoSituacaoTipo.equals("")
						&& !idFaturamentoSituacaoTipo.trim().equalsIgnoreCase(
										Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " faturamentoTipo.id = :idFaturamentoSituacaoTipo  and  ";
		}

		// cobranca situacao tipo
		if(idCobrancaSituacaoTipo != null
						&& !idCobrancaSituacaoTipo.equals("")
						&& !idCobrancaSituacaoTipo.trim().equalsIgnoreCase(
										Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " cobrancaSituacaoTipo.id = :idCobrancaSituacaoTipo  and  ";
		}

		// Situacao Especial Cobranca
		if(idSituacaoEspecialCobranca != null
						&& !idSituacaoEspecialCobranca.equals("")
						&& !idSituacaoEspecialCobranca.trim().equalsIgnoreCase(
										Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " faturamentoSituacaoTipo.id = :idSituacaoEspecialCobranca ";
		}

		// elo anormalidade
		if(idEloAnormalidade != null && !idEloAnormalidade.equals("")
						&& !idEloAnormalidade.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + "eloAnormalidade.id = :idEloAnormalidade  and  ";
		}

		// cadastro ocorrencia
		if(idCadastroOcorrencia != null
						&& !idCadastroOcorrencia.equals("")
						&& !idCadastroOcorrencia.trim()
										.equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " cadastroOcorrencia.id = :idCadastroOcorrencia  and  ";
		}

		// area construida inicial e final
		if((areaConstruidaInicial != null && !areaConstruidaInicial.equals("") && !areaConstruidaInicial.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (areaConstruidaFinal != null && !areaConstruidaFinal.equals("") && !areaConstruidaFinal.trim().equalsIgnoreCase(
										Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			consulta = consulta
							+ " imovel.areaConstruida >= :areaConstruidaInicial and imovel.areaConstruida <= :areaConstruidaFinal  and  ";
		}

		// consumo tarifa
		if(idConsumoTarifa != null && !idConsumoTarifa.equals("")
						&& !idConsumoTarifa.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " consumoTarifa.id = :idConsumoTarifa   and  ";
		}

		// intervalo Media Minima Imovel Inicial e Final
		if((intervaloMediaMinimaImovelInicial != null && !intervaloMediaMinimaImovelInicial.equals("") && !intervaloMediaMinimaImovelInicial
						.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (intervaloMediaMinimaImovelFinal != null && !intervaloMediaMinimaImovelFinal.equals("") && !intervaloMediaMinimaImovelFinal
										.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			consulta = consulta
							+ " consumosHistorico.consumoMedio >= :intervaloMediaMinimaImovelInicial and consumosHistorico.consumoMedio <= :intervaloMediaMinimaImovelFinal  and  ";
		}

		// intervalo MediaMinima Hidrometro Inicial e Final
		if((intervaloMediaMinimaHidrometroInicial != null && !intervaloMediaMinimaHidrometroInicial.equals("") && !intervaloMediaMinimaHidrometroInicial
						.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (intervaloMediaMinimaHidrometroFinal != null && !intervaloMediaMinimaHidrometroFinal.equals("") && !intervaloMediaMinimaHidrometroFinal
										.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			consulta = consulta
							+ " medicaoHistorico.consumoMedioHidrometro >= :intervaloMediaMinimaHidrometroInicial and medicaoHistorico.consumoMedioHidrometro <= :intervaloMediaMinimaHidrometroFinal  and  ";
		}

		// categoria
		if(idCategoria != null && !idCategoria.equals("")
						&& !idCategoria.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " categoria.id = :idCategoria  and  ";
		}

		// sub categoria
		if(idSubCategoria != null && !idSubCategoria.equals("")
						&& !idSubCategoria.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " subCategoria.id = :idSubCategoria  and  ";
		}

		// quantidade economias inicial e final
		if((quantidadeEconomiasInicial != null && !quantidadeEconomiasInicial.equals("") && !quantidadeEconomiasInicial.trim()
						.equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (quantidadeEconomiasFinal != null && !quantidadeEconomiasFinal.equals("") && !quantidadeEconomiasFinal.trim()
										.equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			consulta = consulta
							+ "imovelSubcategoria.quantidadeEconomias >= :quantidadeEconomiasInicial and imovelSubcategoria.quantidadeEconomias <= :quantidadeEconomiasFinal  and  ";
		}

		// dia Vencimento
		if(diaVencimento != null && !diaVencimento.equals("")
						&& !diaVencimento.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			if(diaVencimento.equals("1")){// sim
				consulta = consulta + " imovel.diaVencimento  is not null ";
			}
		}

		// numero prontos inicial e final
		if((numeroPontosInicial != null && !numeroPontosInicial.equals("") && !numeroPontosInicial.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (numeroPontosFinal != null && !numeroPontosFinal.equals("") && !numeroPontosFinal.trim().equalsIgnoreCase(
										Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			consulta = consulta
							+ " imove.numeroPontosUtilizacao >= :numeroPontosInicial and imovel.numeroPontosUtilizacao <= :numeroPontosFinal and ";
		}

		// numero moradores inicial e final
		if((numeroMoradoresInicial != null && !numeroMoradoresInicial.equals("") && !numeroMoradoresInicial.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (numeroMoradoresFinal != null && !numeroMoradoresFinal.equals("") && !numeroMoradoresFinal.trim()
										.equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			consulta = consulta + " imovel.numeroMorador >= :numeroMoradoresInicial and imovel.numeroMorador <= :numeroMoradoresFinal and ";
		}

		// area construida faixa
		if(idAreaConstruidaFaixa != null
						&& !idAreaConstruidaFaixa.equals("")
						&& !idAreaConstruidaFaixa.trim().equalsIgnoreCase(
										Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			consulta = consulta + " areaConstruidaFaixa.id = :idAreaConstruidaFaixa and ";
		}

		return consulta;
	}

	/**
	 * [Uc0164]Gerar Relatório de Imóvel Outros Critérios
	 * Monta a Query do Filtrar Imoveis Outros Criterios
	 * 
	 * @author Rafael Santos
	 * @date 24/07/2006
	 */
	public void informarDadosQueryFiltrarImovelOutrosCriterio(SQLQuery query, String idImovelCondominio, String idImovelPrincipal,
					String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String[] idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,
					String intervaloValorPercentualEsgotoInicial, String intervaloValorPercentualEsgotoFinal,
					String intervaloMediaMinimaImovelInicial, String intervaloMediaMinimaImovelFinal,
					String intervaloMediaMinimaHidrometroInicial, String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,
					String idPocoTipo, String idFaturamentoSituacaoTipo, String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,
					String idEloAnormalidade, String areaConstruidaInicial, String areaConstruidaFinal, String idCadastroOcorrencia,
					String idConsumoTarifa, String idGerenciaRegional, String idLocalidadeInicial, String idLocalidadeFinal,
					String setorComercialInicial, String setorComercialFinal, String quadraInicial, String quadraFinal, String loteOrigem,
					String loteDestno, String cep, String logradouro, String bairro, String municipio, String idTipoMedicao,
					String indicadorMedicao, String idSubCategoria, String idCategoria, String quantidadeEconomiasInicial,
					String quantidadeEconomiasFinal, String diaVencimento, String idCliente, String idClienteTipo,
					String idClienteRelacaoTipo, String numeroPontosInicial, String numeroPontosFinal, String numeroMoradoresInicial,
					String numeroMoradoresFinal, String idAreaConstruidaFaixa, String idUnidadeNegocio, String cdRotaInicial,
					String cdRotaFinal, String sequencialRotaInicial, String sequencialRotaFinal, String segmentoInicial,
					String segmentoFinal, String subloteInicial, String subloteFinal){

		// gerencia regional
		if(idGerenciaRegional != null && !idGerenciaRegional.equals("")
						&& !idGerenciaRegional.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("idGerenciaRegional", Integer.valueOf(idGerenciaRegional).intValue());
		}

		// unidade negocio
		if(idUnidadeNegocio != null && !idUnidadeNegocio.equals("")
						&& !idUnidadeNegocio.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("idUnidadeNegocio", Integer.valueOf(idUnidadeNegocio).intValue());
		}

		// localidade inicial e final
		if(((idLocalidadeInicial != null && !idLocalidadeInicial.equals("") && !idLocalidadeInicial.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())) && (idLocalidadeFinal != null
						&& !idLocalidadeFinal.equals("") && !idLocalidadeFinal.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())))){
			query.setInteger("idLocalidadeInicial", Integer.valueOf(idLocalidadeInicial).intValue());
			query.setInteger("idLocalidadeFinal", Integer.valueOf(idLocalidadeFinal).intValue());
		}

		// setor comercial inicial e final
		if(((setorComercialInicial != null && !setorComercialInicial.equals("") && !setorComercialInicial.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())) && (setorComercialFinal != null
						&& !setorComercialFinal.equals("") && !setorComercialFinal.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())))){
			query.setInteger("setorComercialInicial", Integer.valueOf(setorComercialInicial).intValue());
			query.setInteger("setorComercialFinal", Integer.valueOf(setorComercialFinal).intValue());
		}

		// quadra inicial e final
		if((quadraInicial != null && !quadraInicial.equals("") && !quadraInicial.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (quadraFinal != null && !quadraFinal.equals("") && !quadraFinal.trim().equalsIgnoreCase(
										Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			query.setInteger("quadraInicial", Integer.valueOf(quadraInicial).intValue());
			query.setInteger("quadraFinal", Integer.valueOf(quadraFinal).intValue());
		}

		/*
		 * Alterado por Sávio Luiz Data: 14/09/2007 (Analista:Rosana) Inserir os parametros de
		 * código de rota e sequencial de rota
		 */

		// código de rota inicial e final
		if((cdRotaInicial != null && !cdRotaInicial.equals("") && !cdRotaInicial.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (cdRotaFinal != null && !cdRotaFinal.equals("") && !cdRotaFinal.trim().equalsIgnoreCase(
										Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			query.setShort("cdRotaInicial", Short.valueOf(cdRotaInicial));
			query.setShort("cdRotaFinal", Short.valueOf(cdRotaFinal));
		}

		// sequencial de rota inicial e final
		if((sequencialRotaInicial != null && !sequencialRotaInicial.equals("") && !sequencialRotaInicial.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (sequencialRotaFinal != null && !sequencialRotaFinal.equals("") && !sequencialRotaFinal.trim().equalsIgnoreCase(
										Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			query.setInteger("sequencialRotaInicial", Integer.valueOf(sequencialRotaInicial).intValue());
			query.setInteger("sequencialRotaFinal", Integer.valueOf(sequencialRotaFinal).intValue());
		}

		// segmento inicial e final
		if((segmentoInicial != null && !segmentoInicial.equals("") && !segmentoInicial.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (segmentoFinal != null && !segmentoFinal.equals("") && !segmentoFinal.trim().equalsIgnoreCase(
										Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){

			query.setShort("segmentoInicial", Short.valueOf(segmentoInicial));
			query.setShort("segmentoFinal", Short.valueOf(segmentoFinal));
		}

		// lote
		if((loteOrigem != null && !loteOrigem.equals("") && !loteOrigem.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (loteDestno != null && !loteDestno.equals("") && !loteDestno.trim().equalsIgnoreCase(
										Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){

			query.setInteger("loteOrigem", Integer.valueOf(loteOrigem).intValue());
			query.setInteger("loteDestino", Integer.valueOf(loteDestno).intValue());
		}
		// Sublote
		if(!Util.isVazioOuBranco(subloteInicial) && !Util.isVazioOuBranco(subloteFinal)){

			query.setShort("subloteInicial", Short.valueOf(subloteInicial));
			query.setShort("subloteFinal", Short.valueOf(subloteFinal));
		}

		// cep
		if(cep != null && !cep.equals("")
						&& !cep.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("cep", new Integer(cep).intValue());
		}

		// logradouro
		if(logradouro != null && !logradouro.equals("")
						&& !logradouro.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("logradouro", Integer.valueOf(logradouro).intValue());
		}

		// bairro
		if(bairro != null && !bairro.equals("")
						&& !bairro.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("bairro", Integer.valueOf(bairro).intValue());
		}

		// municipio
		if(municipio != null && !municipio.equals("")
						&& !municipio.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("municipio", Integer.valueOf(municipio).intValue());
		}

		// consumo minimo agua inicial e final
		if((consumoMinimoInicialAgua != null && !consumoMinimoInicialAgua.equals("") && !consumoMinimoInicialAgua.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (consumoMinimoFinalAgua != null && !consumoMinimoFinalAgua.equals("") && !consumoMinimoFinalAgua.trim()
										.equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			query.setInteger("consumoMinimoInicialAgua", Integer.valueOf(consumoMinimoInicialAgua).intValue());
			query.setInteger("consumoMinimoFinalAgua", Integer.valueOf(consumoMinimoFinalAgua).intValue());
		}

		// consumo minimo esgoto inicial e final
		if((consumoMinimoInicialEsgoto != null && !consumoMinimoInicialEsgoto.equals("") && !consumoMinimoInicialEsgoto.trim()
						.equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (consumoMinimoFinalEsgoto != null && !consumoMinimoFinalEsgoto.equals("") && !consumoMinimoFinalEsgoto.trim()
										.equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			query.setInteger("consumoMinimoInicialEsgoto", Integer.valueOf(consumoMinimoInicialEsgoto).intValue());
			query.setInteger("consumoMinimoFinalEsgoto", Integer.valueOf(consumoMinimoFinalEsgoto).intValue());
		}

		// percentual esgoto inicial e final
		if((intervaloValorPercentualEsgotoInicial != null && !intervaloValorPercentualEsgotoInicial.equals("") && !intervaloValorPercentualEsgotoInicial
						.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (intervaloValorPercentualEsgotoFinal != null && !intervaloValorPercentualEsgotoFinal.equals("") && !intervaloValorPercentualEsgotoFinal
										.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			query.setInteger("intervaloValorPercentualEsgotoInicial", new BigDecimal(intervaloValorPercentualEsgotoInicial).intValue());
			query.setInteger("intervaloValorPercentualEsgotoFinal", new BigDecimal(intervaloValorPercentualEsgotoFinal).intValue());
		}

		// imovel condominio
		if(idImovelCondominio != null && !idImovelCondominio.equals("")
						&& !idImovelCondominio.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("idImovelCondominio", Integer.valueOf(idImovelCondominio).intValue());
		}

		// imovel principal
		if(idImovelPrincipal != null && !idImovelPrincipal.equals("")
						&& !idImovelPrincipal.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("idImovelPrincipal", Integer.valueOf(idImovelPrincipal).intValue());
		}

		// Situacao Ligacao Agua
		if(idSituacaoLigacaoAgua != null
 && !idSituacaoLigacaoAgua.equals("")){
			query.setParameterList("idSituacaoLigacaoAgua", idSituacaoLigacaoAgua);
		}

		// situação ligação de esgoto
		if(idSituacaoLigacaoEsgoto != null
 && !idSituacaoLigacaoEsgoto.equals("")){
			query.setParameterList("idSituacaoLigacaoEsgoto", idSituacaoLigacaoEsgoto);
		}

		// imovel Perfil
		if(idImovelPerfil != null && !idImovelPerfil.equals("")
						&& !idImovelPerfil.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("idImovelPerfil", Integer.valueOf(idImovelPerfil).intValue());
		}

		// poço tipo
		if(idPocoTipo != null && !idPocoTipo.equals("")
						&& !idPocoTipo.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("idPocoTipo", Integer.valueOf(idPocoTipo).intValue());
		}

		// faturamento situacao tipo
		if(idFaturamentoSituacaoTipo != null
						&& !idFaturamentoSituacaoTipo.equals("")
						&& !idFaturamentoSituacaoTipo.trim().equalsIgnoreCase(
										Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("idFaturamentoSituacaoTipo", Integer.valueOf(idFaturamentoSituacaoTipo).intValue());
		}

		// cobranca situacao tipo
		if(idCobrancaSituacaoTipo != null
						&& !idCobrancaSituacaoTipo.equals("")
						&& !idCobrancaSituacaoTipo.trim().equalsIgnoreCase(
										Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("idCobrancaSituacaoTipo", Integer.valueOf(idCobrancaSituacaoTipo).intValue());
		}

		// Situacao Especial Cobranca
		if(idSituacaoEspecialCobranca != null
						&& !idSituacaoEspecialCobranca.equals("")
						&& !idSituacaoEspecialCobranca.trim().equalsIgnoreCase(
										Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("idSituacaoEspecialCobranca", Integer.valueOf(idSituacaoEspecialCobranca).intValue());
		}

		// elo anormalidade
		if(idEloAnormalidade != null && !idEloAnormalidade.equals("")
						&& !idEloAnormalidade.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("idEloAnormalidade", Integer.valueOf(idEloAnormalidade).intValue());
		}

		// cadastro ocorrencia
		if(idCadastroOcorrencia != null
						&& !idCadastroOcorrencia.equals("")
						&& !idCadastroOcorrencia.trim()
										.equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("idCadastroOcorrencia", Integer.valueOf(idCadastroOcorrencia).intValue());
		}

		// area construida inicial e final
		if((areaConstruidaInicial != null && !areaConstruidaInicial.equals("") && !areaConstruidaInicial.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (areaConstruidaFinal != null && !areaConstruidaFinal.equals("") && !areaConstruidaFinal.trim().equalsIgnoreCase(
										Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			query.setBigDecimal("areaConstruidaInicial", Util.formatarMoedaRealparaBigDecimal(areaConstruidaInicial));
			query.setBigDecimal("areaConstruidaFinal", Util.formatarMoedaRealparaBigDecimal(areaConstruidaFinal));
		}

		// consumo tarifa
		if(idConsumoTarifa != null && !idConsumoTarifa.equals("")
						&& !idConsumoTarifa.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("idConsumoTarifa", Integer.valueOf(idConsumoTarifa).intValue());
		}

		// intervalo Media Minima Imovel Inicial e Final
		if((intervaloMediaMinimaImovelInicial != null && !intervaloMediaMinimaImovelInicial.equals("") && !intervaloMediaMinimaImovelInicial
						.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (intervaloMediaMinimaImovelFinal != null && !intervaloMediaMinimaImovelFinal.equals("") && !intervaloMediaMinimaImovelFinal
										.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			query.setInteger("intervaloMediaMinimaImovelInicial", Integer.valueOf(intervaloMediaMinimaImovelInicial).intValue());
			query.setInteger("intervaloMediaMinimaImovelFinal", Integer.valueOf(intervaloMediaMinimaImovelFinal).intValue());
		}

		// intervalo MediaMinima Hidrometro Inicial e Final
		if((intervaloMediaMinimaHidrometroInicial != null && !intervaloMediaMinimaHidrometroInicial.equals("") && !intervaloMediaMinimaHidrometroInicial
						.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (intervaloMediaMinimaHidrometroFinal != null && !intervaloMediaMinimaHidrometroFinal.equals("") && !intervaloMediaMinimaHidrometroFinal
										.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			query.setInteger("intervaloMediaMinimaHidrometroInicial", Integer.valueOf(intervaloMediaMinimaHidrometroInicial).intValue());
			query.setInteger("intervaloMediaMinimaHidrometroFinal", Integer.valueOf(intervaloMediaMinimaHidrometroFinal).intValue());
		}

		// quantidade economias inicial e final
		if((quantidadeEconomiasInicial != null && !quantidadeEconomiasInicial.equals("") && !quantidadeEconomiasInicial.trim()
						.equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (quantidadeEconomiasFinal != null && !quantidadeEconomiasFinal.equals("") && !quantidadeEconomiasFinal.trim()
										.equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			query.setShort("quantidadeEconomiasInicial", Short.valueOf(quantidadeEconomiasInicial).shortValue());
			query.setShort("quantidadeEconomiasFinal", Short.valueOf(quantidadeEconomiasFinal).shortValue());
		}

		// categoria
		if(idCategoria != null && !idCategoria.equals("")
						&& !idCategoria.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("idCategoria", Integer.valueOf(idCategoria).intValue());
		}

		// sub categoria
		if(idSubCategoria != null && !idSubCategoria.equals("")
						&& !idSubCategoria.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("idSubCategoria", Integer.valueOf(idSubCategoria).intValue());
		}

		// numero prontos inicial e final
		if((numeroPontosInicial != null && !numeroPontosInicial.equals("") && !numeroPontosInicial.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (numeroPontosFinal != null && !numeroPontosFinal.equals("") && !numeroPontosFinal.trim().equalsIgnoreCase(
										Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			query.setShort("numeroPontosInicial", Short.valueOf(numeroPontosInicial).shortValue());
			query.setShort("numeroPontosFinal", Short.valueOf(numeroPontosFinal).shortValue());
		}

		// numero moradores inicial e final

		if((numeroMoradoresInicial != null && !numeroMoradoresInicial.equals("") && !numeroMoradoresInicial.trim().equalsIgnoreCase(
						Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))
						&& (numeroMoradoresFinal != null && !numeroMoradoresFinal.equals("") && !numeroMoradoresFinal.trim()
										.equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){
			query.setShort("numeroMoradoresInicial", Short.valueOf(numeroMoradoresInicial).shortValue());
			query.setShort("numeroMoradoresFinal", Short.valueOf(numeroMoradoresFinal).shortValue());
		}

		// area construida faixa
		if(idAreaConstruidaFaixa != null
						&& !idAreaConstruidaFaixa.equals("")
						&& !idAreaConstruidaFaixa.trim().equalsIgnoreCase(
										Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("idAreaConstruidaFaixa", Integer.valueOf(idAreaConstruidaFaixa).intValue());
		}

		// cliente
		if(idCliente != null && !idCliente.equals("")
						&& !idCliente.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("idCliente", Integer.valueOf(idCliente).intValue());
		}

		// cliente tipo
		if(idClienteTipo != null && !idClienteTipo.equals("")
						&& !idClienteTipo.trim().equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("idClienteTipo", Integer.valueOf(idClienteTipo).intValue());
		}

		// cliente relacao tipo
		if(idClienteRelacaoTipo != null
						&& !idClienteRelacaoTipo.equals("")
						&& !idClienteRelacaoTipo.trim()
										.equalsIgnoreCase(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
			query.setInteger("idClienteRelacaoTipo", Integer.valueOf(idClienteRelacaoTipo).intValue());
		}
	}

	public Collection pesquisarSubcategoriasImovelRelatorio(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection retorno = null;
		try{
			String consultaSubcategoria = "select" + " sub.descricao," + " isc.quantidadeEconomias" + " from ImovelSubcategoria isc"
							+ " inner join isc.comp_id.imovel im" + " inner join isc.comp_id.subcategoria sub" + " where im.id = "
							+ idImovel;
			retorno = session.createQuery(consultaSubcategoria).list();

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
			// session.close();
		}

		return retorno;
	}

	/**
	 * [UC0164] Filtrar Imoveis por Outros Criterios
	 * Filtra para saber a quantidade de imoveis antes de executar o filtro
	 * 
	 * @author Rafael Santos
	 * @date 01/08/2006
	 */

	public Integer obterQuantidadadeRelacaoImoveisDebitos(

	String idImovelCondominio, String idImovelPrincipal,

	String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua,

	String consumoMinimoFinalAgua, String[] idSituacaoLigacaoEsgoto,

	String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,

	String intervaloValorPercentualEsgotoInicial,

	String intervaloValorPercentualEsgotoFinal,

	String intervaloMediaMinimaImovelInicial,

	String intervaloMediaMinimaImovelFinal,

	String intervaloMediaMinimaHidrometroInicial,

	String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,

	String idPocoTipo, String idFaturamentoSituacaoTipo,

	String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,

	String idEloAnormalidade, String areaConstruidaInicial,

	String areaConstruidaFinal, String idCadastroOcorrencia,

	String idConsumoTarifa, String idGerenciaRegional,

	String idLocalidadeInicial, String idLocalidadeFinal,

	String setorComercialInicial, String setorComercialFinal,

	String quadraInicial, String quadraFinal, String loteOrigem,

	String loteDestno, String cep, String logradouro, String bairro,

	String municipio, String idTipoMedicao, String indicadorMedicao,

	String idSubCategoria, String idCategoria,

	String quantidadeEconomiasInicial, String quantidadeEconomiasFinal,

	String diaVencimento, String idCliente, String idClienteTipo,

	String idClienteRelacaoTipo, String numeroPontosInicial,

	String numeroPontosFinal, String numeroMoradoresInicial,

	String numeroMoradoresFinal, String idAreaConstruidaFaixa,

	String idUnidadeNegocio, Integer relatorio, String cdRotaInicial, String cdRotaFinal, String sequencialRotaInicial,
					String sequencialRotaFinal, String segmentoInicial, String segmentoFinal, String subloteInicial, String subloteFinal)

	throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta;

		try{

			consulta = "select count(distinct imovel.imov_id) as quantidade "

			+

			// From

							"from imovel_subcategoria imovelSubcategoria "

							+

							"inner join imovel on imovelSubcategoria.imov_id = imovel.imov_id "

							+ "inner join localidade on imovel.loca_id = localidade.loca_id "

							+ "inner join gerencia_regional on localidade.greg_id = gerencia_regional.greg_id "

							+ "inner join setor_comercial on imovel.stcm_id = setor_comercial.stcm_id "

							+

							// Logradouro Bairro

							"left join logradouro_bairro on imovel.lgbr_id = logradouro_bairro.lgbr_id "

							+ "left join bairro on logradouro_bairro.bair_id = bairro.bair_id "

							+ "left join municipio on bairro.muni_id = municipio.muni_id "

							+ "inner join quadra on imovel.qdra_id = quadra.qdra_id "

							+ "inner join rota on imovel.rota_id = rota.rota_id "

							+

							// Logradouro Cep

							"left join logradouro_cep on imovel.lgcp_id = logradouro_cep.lgcp_id "

							+ "left join cep on logradouro_cep.cep_id = cep.cep_id "

							+ "left join logradouro on logradouro_cep.logr_id = logradouro.logr_id "

							+

							// AGUA

							"left join ligacao_agua_situacao on imovel.last_id = ligacao_agua_situacao.last_id "

							+ "left join ligacao_agua on imovel.imov_id = ligacao_agua.lagu_id "

							+

							// ESGOTO

							"left join ligacao_esgoto_situacao on imovel.lest_id = ligacao_esgoto_situacao.lest_id "

							+ "left join ligacao_esgoto on imovel.imov_id = ligacao_esgoto.lesg_id "

							+

							"left join imovel_perfil on imovel.iper_id = imovel_perfil.iper_id "

							+

							"left join poco_tipo on imovel.poco_id = poco_tipo.poco_id "

							+

							"left join area_construida_faixa on imovel.acon_id = area_construida_faixa.acon_id "

							+

							// AGUA

							"left join hidrometro_instalacao_hist on ligacao_agua.hidi_id = hidrometro_instalacao_hist.hidi_id "

							+

							// ESGOTO

							"left join hidrometro_instalacao_hist hidrom_inst_hist_esgoto on imovel.hidi_id =  hidrom_inst_hist_esgoto.hidi_id "

							// Rota do Imóvel
							+ "left join rota rotaImovel on rotaImovel.rota_id = imovel.rota_id ";

			consulta = consulta

			+ montarInnerJoinFiltrarImoveisOutrosCriterios(

			intervaloMediaMinimaImovelInicial,

			intervaloMediaMinimaImovelFinal,

			intervaloMediaMinimaHidrometroInicial,

			intervaloMediaMinimaHidrometroFinal,

			idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo,

			idSituacaoEspecialCobranca, idEloAnormalidade,

			idCadastroOcorrencia, idConsumoTarifa,

			idTipoMedicao, indicadorMedicao, idSubCategoria,

			idCategoria, idCliente, idClienteTipo,

			idClienteRelacaoTipo, relatorio);

			consulta = consulta

							+ montarCondicaoSQLWhereFiltrarImovelOutrosCriterio(

							idImovelCondominio, idImovelPrincipal,

							idSituacaoLigacaoAgua, consumoMinimoInicialAgua,

							consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto,

							consumoMinimoInicialEsgoto,

							consumoMinimoFinalEsgoto,

							intervaloValorPercentualEsgotoInicial,

							intervaloValorPercentualEsgotoFinal,

							intervaloMediaMinimaImovelInicial,

							intervaloMediaMinimaImovelFinal,

							intervaloMediaMinimaHidrometroInicial,

							intervaloMediaMinimaHidrometroFinal,

							idImovelPerfil, idPocoTipo,

							idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo,

							idSituacaoEspecialCobranca, idEloAnormalidade,

							areaConstruidaInicial, areaConstruidaFinal,

							idCadastroOcorrencia, idConsumoTarifa,

							idGerenciaRegional, idLocalidadeInicial,

							idLocalidadeFinal, setorComercialInicial,

							setorComercialFinal, quadraInicial, quadraFinal,

							loteOrigem, loteDestno, cep, logradouro, bairro,

							municipio, idTipoMedicao, indicadorMedicao,

							idSubCategoria, idCategoria,

							quantidadeEconomiasInicial,

							quantidadeEconomiasFinal, diaVencimento, idCliente,

							idClienteTipo, idClienteRelacaoTipo,

							numeroPontosInicial, numeroPontosFinal,

							numeroMoradoresInicial, numeroMoradoresFinal,

							idAreaConstruidaFaixa, idUnidadeNegocio, relatorio, cdRotaInicial, cdRotaFinal, sequencialRotaInicial,
											sequencialRotaFinal, segmentoInicial, segmentoFinal, subloteInicial, subloteFinal);

			/*
			 * # COLOCANDO O VALOR NAS CONDIÇÕES#
			 */

			SQLQuery query = session.createSQLQuery(consulta.substring(0,

			(consulta.length() - 5)));

			informarDadosQueryFiltrarImovelOutrosCriterio(query,

			idImovelCondominio, idImovelPrincipal,

			idSituacaoLigacaoAgua, consumoMinimoInicialAgua,

			consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto,

			consumoMinimoInicialEsgoto, consumoMinimoFinalEsgoto,

			intervaloValorPercentualEsgotoInicial,

			intervaloValorPercentualEsgotoFinal,

			intervaloMediaMinimaImovelInicial,

			intervaloMediaMinimaImovelFinal,

			intervaloMediaMinimaHidrometroInicial,

			intervaloMediaMinimaHidrometroFinal, idImovelPerfil,

			idPocoTipo, idFaturamentoSituacaoTipo,

			idCobrancaSituacaoTipo, idSituacaoEspecialCobranca,

			idEloAnormalidade, areaConstruidaInicial,

			areaConstruidaFinal, idCadastroOcorrencia, idConsumoTarifa,

			idGerenciaRegional, idLocalidadeInicial, idLocalidadeFinal,

			setorComercialInicial, setorComercialFinal, quadraInicial,

			quadraFinal, loteOrigem, loteDestno, cep, logradouro,

			bairro, municipio, idTipoMedicao, indicadorMedicao,

			idSubCategoria, idCategoria, quantidadeEconomiasInicial,

			quantidadeEconomiasFinal, diaVencimento, idCliente,

			idClienteTipo, idClienteRelacaoTipo, numeroPontosInicial,

			numeroPontosFinal, numeroMoradoresInicial,

			numeroMoradoresFinal, idAreaConstruidaFaixa,

			idUnidadeNegocio, cdRotaInicial, cdRotaFinal, sequencialRotaInicial, sequencialRotaFinal, segmentoInicial, segmentoFinal,
							subloteInicial, subloteFinal);

			retorno = (Integer) query

			.addScalar("quantidade", Hibernate.INTEGER).uniqueResult();

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
	 * [UC0164]Gerar Relatório de Imóvel Outros Critérios
	 * Monta a Condição do where do Filtrar Imoveis Outros Criterios
	 * 
	 * @author Rafael Santos
	 * @date 24/07/2006
	 */

	private String montarCondicaoSQLWhereFiltrarImovelOutrosCriterio(

	String idImovelCondominio, String idImovelPrincipal,

	String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua,

	String consumoMinimoFinalAgua, String[] idSituacaoLigacaoEsgoto,

	String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,

	String intervaloValorPercentualEsgotoInicial,

	String intervaloValorPercentualEsgotoFinal,

	String intervaloMediaMinimaImovelInicial,

	String intervaloMediaMinimaImovelFinal,

	String intervaloMediaMinimaHidrometroInicial,

	String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,

	String idPocoTipo, String idFaturamentoSituacaoTipo,

	String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,

	String idEloAnormalidade, String areaConstruidaInicial,

	String areaConstruidaFinal, String idCadastroOcorrencia,

	String idConsumoTarifa, String idGerenciaRegional,

	String idLocalidadeInicial, String idLocalidadeFinal,

	String setorComercialInicial, String setorComercialFinal,

	String quadraInicial, String quadraFinal, String loteOrigem,

	String loteDestno, String cep, String logradouro, String bairro,

	String municipio, String idTipoMedicao, String indicadorMedicao,

	String idSubCategoria, String idCategoria,

	String quantidadeEconomiasInicial, String quantidadeEconomiasFinal,

	String diaVencimento, String idCliente, String idClienteTipo,

	String idClienteRelacaoTipo, String numeroPontosInicial,

	String numeroPontosFinal, String numeroMoradoresInicial,

	String numeroMoradoresFinal, String idAreaConstruidaFaixa,

	String idUnidadeNegocio, Integer relatorio, String cdRotaInicial, String cdRotaFinal, String sequencialRotaInicial,
					String sequencialRotaFinal, String segmentoInicial, String segmentoFinal, String subloteInicial, String subloteFinal){

		String consulta = "";

		/*
		 * ## CONDIÇÕES ##
		 */

		consulta = consulta + " where imovel.imov_icexclusao != "

		+ Imovel.IMOVEL_EXCLUIDO + " and  ";

		// cliente

		if(idCliente != null

		&& !idCliente.equals("")

		&& !idCliente.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			consulta = consulta + " cliente.clie_id = :idCliente  and  ";

		}

		// cliente tipo

		if(idClienteTipo != null

		&& !idClienteTipo.equals("")

		&& !idClienteTipo.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			consulta = consulta + " cliente.cltp_id = :idClienteTipo  and  ";

		}

		// cliente relacao tipo

		if(idClienteRelacaoTipo != null

		&& !idClienteRelacaoTipo.equals("")

		&& !idClienteRelacaoTipo.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			// Relatorio de Economias do Imovel

			if(relatorio != null

			&& relatorio.intValue() == ConstantesSistema.GERAR_RELATORIO_DADOS_ECONOMIA_IMOVEL

			.intValue()){

				consulta = consulta

				+ " cliente_imovel_economia.crtp_id = :idClienteRelacaoTipo  and  ";

			}else{

				consulta = consulta

				+ " cliente_imovel.crtp_id = :idClienteRelacaoTipo  and  ";

			}

		}

		// gerencia regional

		if(idGerenciaRegional != null

		&& !idGerenciaRegional.equals("")

		&& !idGerenciaRegional.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			consulta = consulta

			+ " gerencia_regional.greg_id = :idGerenciaRegional and ";

		}

		// unidade negocio

		if(idUnidadeNegocio != null

		&& !idUnidadeNegocio.equals("")

		&& !idUnidadeNegocio.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			consulta = consulta

			+ " localidade.uneg_id = :idUnidadeNegocio and ";

		}

		// localidade inicial e final

		if(((idLocalidadeInicial != null && !idLocalidadeInicial.equals("") && !idLocalidadeInicial

		.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())) && (idLocalidadeFinal != null

		&& !idLocalidadeFinal.equals("") && !idLocalidadeFinal.trim()

		.equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())))){

			consulta = consulta

			+ " localidade.loca_id >= :idLocalidadeInicial and localidade.loca_id <= :idLocalidadeFinal  and  ";

		}

		// setor comercial inicial e final

		if(((setorComercialInicial != null

		&& !setorComercialInicial.equals("") && !setorComercialInicial

		.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())) && (setorComercialFinal != null

		&& !setorComercialFinal.equals("") && !setorComercialFinal

		.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())))){

			consulta = consulta

							+ " setor_comercial.stcm_cdsetorcomercial >= :setorComercialInicial and setor_comercial.stcm_cdsetorcomercial <= :setorComercialFinal  and  ";

		}

		// quadra

		if((quadraInicial != null && !quadraInicial.equals("") && !quadraInicial

		.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString()))

		&& (quadraFinal != null && !quadraFinal.equals("") && !quadraFinal

		.trim().equalsIgnoreCase(

		new Integer(

		ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString()))){

			consulta = consulta

			+ " quadra.qdra_nnquadra >= :quadraInicial and quadra.qdra_nnquadra <= :quadraFinal and  ";

		}

		/*
		 * Alterado por Sávio Luiz Data: 14/09/2007 (Analista:Rosana) Inserir os
		 * parametros de código de rota e sequencial de rota
		 */

		// Rota
		if(!Util.isVazioOuBranco(cdRotaInicial) && !Util.isVazioOuBranco(cdRotaFinal)){

			consulta += " rotaImovel.rota_id >= :cdRotaInicial and rotaImovel.rota_id <= :cdRotaFinal  and  ";
		}

		// Sequencial da Rota
		if(!Util.isVazioOuBranco(sequencialRotaInicial) && !Util.isVazioOuBranco(sequencialRotaFinal)){

			consulta += " imovel.imov_nnsequencialrota >= :sequencialRotaInicial and imovel.imov_nnsequencialrota <= :sequencialRotaFinal  and  ";
		}

		// Segmento
		if(!Util.isVazioOuBranco(segmentoInicial) && !Util.isVazioOuBranco(segmentoFinal)){

			consulta += " imovel.imov_nnsegmento >= :segmentoInicial and imovel.imov_nnsegmento <= :segmentoFinal  and  ";
		}

		// Lote
		if(!Util.isVazioOuBranco(loteOrigem) && !Util.isVazioOuBranco(loteDestno)){

			consulta += " imovel.imov_nnlote >= :loteOrigem  and  imovel.imov_nnlote <= :loteDestino and ";

		}

		// Sublote
		if(!Util.isVazioOuBranco(subloteInicial) && !Util.isVazioOuBranco(subloteFinal)){

			consulta += " imovel.imov_nnsublote >= :subloteInicial  and  imovel.imov_nnsublote <= :subloteFinal and ";
		}

		// cep

		if(cep != null

		&& !cep.equals("")

		&& !cep.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			consulta = consulta + " cep.cep_cdcep = :cep  and  ";

		}

		// logradouro

		if(logradouro != null

		&& !logradouro.equals("")

		&& !logradouro.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			consulta = consulta + " logradouro.logr_id = :logradouro  and  ";

		}

		// bairro

		if(bairro != null

		&& !bairro.equals("")

		&& !bairro.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			consulta = consulta + " bairro.bair_cdbairro = :bairro  and  ";

		}

		// municipio

		if(municipio != null

		&& !municipio.equals("")

		&& !municipio.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			consulta = consulta + " municipio.muni_id = :municipio  and  ";

		}

		// consumo minimo agua inicial e final

		if((consumoMinimoInicialAgua != null

		&& !consumoMinimoInicialAgua.equals("") && !consumoMinimoInicialAgua

		.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString()))

		&& (consumoMinimoFinalAgua != null

		&& !consumoMinimoFinalAgua.equals("") && !consumoMinimoFinalAgua

		.trim().equalsIgnoreCase(

		new Integer(

		ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString()))){

			consulta = consulta

							+ " ligacao_agua.lagu_nnconsumominimoagua >= :consumoMinimoInicialAgua and ligacao_agua.lagu_nnconsumominimoagua <= :consumoMinimoFinalAgua  and  ";

		}

		// consumo minimo esgoto inicial e final

		if((consumoMinimoInicialEsgoto != null

		&& !consumoMinimoInicialEsgoto.equals("") && !consumoMinimoInicialEsgoto

		.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString()))

		&& (consumoMinimoFinalEsgoto != null

		&& !consumoMinimoFinalEsgoto.equals("") && !consumoMinimoFinalEsgoto

		.trim().equalsIgnoreCase(

		new Integer(

		ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString()))){

			consulta = consulta

							+ " ligacao_esgoto.lesg_nnconsumominimoesgoto >= :consumoMinimoInicialEsgoto and ligacao_esgoto.lesg_nnconsumominimoesgoto <= :consumoMinimoFinalEsgoto  and  ";

		}

		// percentual esgoto inicial e final

		if((intervaloValorPercentualEsgotoInicial != null

		&& !intervaloValorPercentualEsgotoInicial.equals("") && !intervaloValorPercentualEsgotoInicial

		.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString()))

		&& (intervaloValorPercentualEsgotoFinal != null

		&& !intervaloValorPercentualEsgotoFinal.equals("") && !intervaloValorPercentualEsgotoFinal

		.trim().equalsIgnoreCase(

		new Integer(

		ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString()))){

			consulta = consulta

							+ " ligacao_esgoto.lesg_pcesgoto >= :intervaloValorPercentualEsgotoInicial and ligacao_esgoto.lesg_pcesgoto <= :intervaloValorPercentualEsgotoFinal  and  ";

		}

		// indicador medição

		if(indicadorMedicao != null && indicadorMedicao.equals("comMedicao")){

			// tipo medicao

			if(idTipoMedicao != null

			&& idTipoMedicao

			.equals(MedicaoTipo.LIGACAO_AGUA.toString())){

				consulta = consulta

				+ " hidrometro_instalacao_hist.hidi_id is not null  and  ";

			}else if(idTipoMedicao != null

			&& idTipoMedicao.equals(MedicaoTipo.POCO.toString())){

				consulta = consulta

				+ " hidrometro_instalacao_hist.hidi_id is not null and  ";

			}else{

				consulta = consulta

				+ " (hidrometro_instalacao_hist.hidi_id is not null or ";

				consulta = consulta

				+ " hidrometro_instalacao_hist.hidi_id is not null) and  ";

			}

		}else if(indicadorMedicao != null

		&& indicadorMedicao.equals("semMedicao")){

			consulta = consulta

			+ " (hidrometro_instalacao_hist.hidi_id is null and";

			consulta = consulta

			+ " hidrom_inst_hist_esgoto.hidi_id is null) and  ";

		}

		// imovel condominio

		if(idImovelCondominio != null

		&& !idImovelCondominio.equals("")

		&& !idImovelCondominio.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			consulta = consulta

			+ " imovel.imov_idimovelcondominio = :idImovelCondominio  and  ";

		}

		// imovel principal

		if(idImovelPrincipal != null

		&& !idImovelPrincipal.equals("")

		&& !idImovelPrincipal.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			consulta = consulta

			+ " imovel.imov_idimovelprincipal = :idImovelPrincipal  and  ";

		}

		// situação da ligação de agua

		if(idSituacaoLigacaoAgua != null

		&& !idSituacaoLigacaoAgua.equals("")){

			consulta = consulta

			+ " ligacao_agua_situacao.last_id in (:idSituacaoLigacaoAgua)  and  ";

		}

		// situação ligação de esgoto

		if(idSituacaoLigacaoEsgoto != null

		&& !idSituacaoLigacaoEsgoto.equals("")

		){

			consulta = consulta

			+ " ligacao_esgoto_situacao.lest_id in (:idSituacaoLigacaoEsgoto)  and  ";

		}

		// imovel Perfil

		if(idImovelPerfil != null

		&& !idImovelPerfil.equals("")

		&& !idImovelPerfil.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			consulta = consulta

			+ " imovel_perfil.iper_id = :idImovelPerfil  and  ";

		}

		// poço tipo

		if(idPocoTipo != null

		&& !idPocoTipo.equals("")

		&& !idPocoTipo.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			consulta = consulta + " imovel.poco_id = :idPocoTipo  and  ";

		}

		// faturamento situacao tipo

		if(idFaturamentoSituacaoTipo != null

		&& !idFaturamentoSituacaoTipo.equals("")

		&& !idFaturamentoSituacaoTipo.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			consulta = consulta

			+ " faturamento_situacao_tipo.ftst_id = :idFaturamentoSituacaoTipo  and  ";

		}

		// Situacao Especial Cobranca

		if(idSituacaoEspecialCobranca != null

		&& !idSituacaoEspecialCobranca.equals("")

		&& !idSituacaoEspecialCobranca.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			consulta = consulta

			+ " cobranca_situacao_tipo.cbsp_id = :idSituacaoEspecialCobranca  and  ";

		}

		// cobranca situacao tipo

		if(idCobrancaSituacaoTipo != null

		&& !idCobrancaSituacaoTipo.equals("")

		&& !idCobrancaSituacaoTipo.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			consulta = consulta

			+ " cobranca_situacao.cbst_id = :idCobrancaSituacaoTipo  and  ";

		}

		// elo anormalidade

		if(idEloAnormalidade != null

		&& !idEloAnormalidade.equals("")

		&& !idEloAnormalidade.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			consulta = consulta

			+ " elo_anormalidade.eanm_id = :idEloAnormalidade  and  ";

		}

		// cadastro ocorrencia

		if(idCadastroOcorrencia != null

		&& !idCadastroOcorrencia.equals("")

		&& !idCadastroOcorrencia.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			consulta = consulta

			+ " cadastro_ocorrencia.cocr_id = :idCadastroOcorrencia  and  ";

		}

		// consumo tarifa

		if(idConsumoTarifa != null

		&& !idConsumoTarifa.equals("")

		&& !idConsumoTarifa.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			consulta = consulta

			+ " consumo_tarifa.cstf_id = :idConsumoTarifa   and  ";

		}

		// intervalo Media Minima Imovel Inicial e Final

		if((intervaloMediaMinimaImovelInicial != null

		&& !intervaloMediaMinimaImovelInicial.equals("") && !intervaloMediaMinimaImovelInicial

		.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString()))

		&& (intervaloMediaMinimaImovelFinal != null

		&& !intervaloMediaMinimaImovelFinal.equals("") && !intervaloMediaMinimaImovelFinal

		.trim().equalsIgnoreCase(

		new Integer(

		ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString()))){

			consulta = consulta

							+ " consumo_historico.cshi_nnconsumomedio >= :intervaloMediaMinimaImovelInicial and consumo_historico.cshi_nnconsumomedio <= :intervaloMediaMinimaImovelFinal  and  ";

		}

		// intervalo MediaMinima Hidrometro Inicial e Final

		if((intervaloMediaMinimaHidrometroInicial != null

		&& !intervaloMediaMinimaHidrometroInicial.equals("") && !intervaloMediaMinimaHidrometroInicial

		.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString()))

		&& (intervaloMediaMinimaHidrometroFinal != null

		&& !intervaloMediaMinimaHidrometroFinal.equals("") && !intervaloMediaMinimaHidrometroFinal

		.trim().equalsIgnoreCase(

		new Integer(

		ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString()))){

			if(idTipoMedicao != null && !idTipoMedicao.equals("")){

				if(idTipoMedicao.equals(MedicaoTipo.LIGACAO_AGUA.toString())){

					consulta = consulta

									+ " medicao_historico.mdhi_nnconsumomediohidrometro >= :intervaloMediaMinimaHidrometroInicial and medicao_historico.mdhi_nnconsumomediohidrometro <= :intervaloMediaMinimaHidrometroFinal  and  ";

				}else if(idTipoMedicao.equals(MedicaoTipo.POCO.toString())){

					consulta = consulta

									+ " medicao_historico_poco.mdhi_nnconsumomediohidrometro >= :intervaloMediaMinimaHidrometroInicial and medicao_historico_poco.mdhi_nnconsumomediohidrometro <= :intervaloMediaMinimaHidrometroFinal  and  ";

				}

			}

		}

		// categoria

		if(idCategoria != null

		&& !idCategoria.equals("")

		&& !idCategoria.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			consulta = consulta + " categoria.catg_id = :idCategoria  and  ";

		}

		// sub categoria

		if(idSubCategoria != null

		&& !idSubCategoria.equals("")

		&& !idSubCategoria.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			consulta = consulta

			+ " subCategoria.scat_id = :idSubCategoria  and  ";

		}

		// Relatorio de Economias do Imovel

		if(relatorio != null

		&& relatorio.intValue() == ConstantesSistema.GERAR_RELATORIO_DADOS_ECONOMIA_IMOVEL

		.intValue()){

			// numero prontos inicial e final

			if((numeroPontosInicial != null && !numeroPontosInicial.equals("") && !numeroPontosInicial

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))

			&& (numeroPontosFinal != null

			&& !numeroPontosFinal.equals("") && !numeroPontosFinal

			.trim()

			.equalsIgnoreCase(

			new Integer(

			ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta

								+ " imovel_economia.imec_nnpontosutilizacao >= :numeroPontosInicial and imovel_economia.imec_nnpontosutilizacao <= :numeroPontosFinal and ";

			}

			// numero moradores inicial e final

			if((numeroMoradoresInicial != null

			&& !numeroMoradoresInicial.equals("") && !numeroMoradoresInicial

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))

			&& (numeroMoradoresFinal != null

			&& !numeroMoradoresFinal.equals("") && !numeroMoradoresFinal

			.trim()

			.equalsIgnoreCase(

			new Integer(

			ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta

								+ " imovel_economia.imec_nnmorador >= :numeroMoradoresInicial and imovel_economia.imec_nnmorador <= :numeroMoradoresFinal and ";

			}

			// area construida faixa

			if(idAreaConstruidaFaixa != null

			&& !idAreaConstruidaFaixa.equals("")

			&& !idAreaConstruidaFaixa.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta

				+ " imovel_economia.acon_id = :idAreaConstruidaFaixa and ";

			}

			// area construida inicial e final

			if((areaConstruidaInicial != null

			&& !areaConstruidaInicial.equals("") && !areaConstruidaInicial

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))

			&& (areaConstruidaFinal != null

			&& !areaConstruidaFinal.equals("") && !areaConstruidaFinal

			.trim()

			.equalsIgnoreCase(

			new Integer(

			ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta

								+ " imovel_economia.imec_nnareaconstruida >= :areaConstruidaInicial and imovel_economia.imec_nnareaconstruida <= :areaConstruidaFinal  and  ";

			}

		}else{

			// numero prontos inicial e final

			if((numeroPontosInicial != null && !numeroPontosInicial.equals("") && !numeroPontosInicial

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))

			&& (numeroPontosFinal != null

			&& !numeroPontosFinal.equals("") && !numeroPontosFinal

			.trim()

			.equalsIgnoreCase(

			new Integer(

			ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta

				+ " imovel.imov_nnpontosutilizacao >= :numeroPontosInicial and imovel.imov_nnpontosutilizacao <= :numeroPontosFinal and ";

			}

			// numero moradores inicial e final

			if((numeroMoradoresInicial != null

			&& !numeroMoradoresInicial.equals("") && !numeroMoradoresInicial

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))

			&& (numeroMoradoresFinal != null

			&& !numeroMoradoresFinal.equals("") && !numeroMoradoresFinal

			.trim()

			.equalsIgnoreCase(

			new Integer(

			ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta

				+ " imovel.imov_nnmorador >= :numeroMoradoresInicial and imovel.imov_nnmorador <= :numeroMoradoresFinal and ";

			}

			// area construida faixa

			if(idAreaConstruidaFaixa != null

			&& !idAreaConstruidaFaixa.equals("")

			&& !idAreaConstruidaFaixa.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta

				+ " imovel.acon_id = :idAreaConstruidaFaixa and ";

			}

			// area construida inicial e final

			if((areaConstruidaInicial != null

			&& !areaConstruidaInicial.equals("") && !areaConstruidaInicial

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))

			&& (areaConstruidaFinal != null

			&& !areaConstruidaFinal.equals("") && !areaConstruidaFinal

			.trim()

			.equalsIgnoreCase(

			new Integer(

			ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta

				+ " imovel.imov_nnareaconstruida >= :areaConstruidaInicial and imovel.imov_nnareaconstruida <= :areaConstruidaFinal  and  ";

			}

		}

		// quantidade economias inicial e final

		if((quantidadeEconomiasInicial != null

		&& !quantidadeEconomiasInicial.equals("") && !quantidadeEconomiasInicial

		.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString()))

		&& (quantidadeEconomiasFinal != null

		&& !quantidadeEconomiasFinal.equals("") && !quantidadeEconomiasFinal

		.trim().equalsIgnoreCase(

		new Integer(

		ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString()))){

			consulta = consulta

			+ "imovel.imov_qteconomia >= :quantidadeEconomiasInicial and imovel.imov_qteconomia <= :quantidadeEconomiasFinal  and  ";

		}

		// dia Vencimento

		if(diaVencimento != null

		&& !diaVencimento.equals("")

		&& !diaVencimento.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			if(diaVencimento.equals("1")){// sim

				consulta = consulta

				+ " imovel.imov_ddvencimento  is not null and ";

			}

		}

		return consulta;

	}

	/**
	 * Gerar Relatório de Imóvel Outros Critérios
	 * 
	 * @author Rafael Corrêa,Rafael Santos
	 * @date 24/07/2006,01/08/2006
	 */

	public Collection gerarRelatorioImovelOutrosCriterios(

	String idImovelCondominio, String idImovelPrincipal,

	String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua,

	String consumoMinimoFinalAgua, String[] idSituacaoLigacaoEsgoto,

	String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,

	String intervaloValorPercentualEsgotoInicial,

	String intervaloValorPercentualEsgotoFinal,

	String intervaloMediaMinimaImovelInicial,

	String intervaloMediaMinimaImovelFinal,

	String intervaloMediaMinimaHidrometroInicial,

	String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,

	String idPocoTipo, String idFaturamentoSituacaoTipo,

	String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,

	String idEloAnormalidade, String areaConstruidaInicial,

	String areaConstruidaFinal, String idCadastroOcorrencia,

	String idConsumoTarifa, String idGerenciaRegional,

	String idLocalidadeInicial, String idLocalidadeFinal,

	String setorComercialInicial, String setorComercialFinal,

	String quadraInicial, String quadraFinal, String loteOrigem,

	String loteDestno, String cep, String logradouro, String bairro,

	String municipio, String idTipoMedicao, String indicadorMedicao,

	String idSubCategoria, String idCategoria,

	String quantidadeEconomiasInicial, String quantidadeEconomiasFinal,

	String diaVencimento, String idCliente, String idClienteTipo,

	String idClienteRelacaoTipo, String numeroPontosInicial,

	String numeroPontosFinal, String numeroMoradoresInicial,

	String numeroMoradoresFinal, String idAreaConstruidaFaixa,

	String idUnidadeNegocio, String cdRotaInicial, String cdRotaFinal, String sequencialRotaInicial, String sequencialRotaFinal,
					String segmentoInicial, String segmentoFinal, String subloteInicial, String subloteFinal)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = consulta
							+ "select distinct "
							+ "gerencia_regional.greg_id, "
							+ "gerencia_regional.greg_nmabreviado, "
							+ "localidade.loca_id, "
							+ "localidade.loca_nmlocalidade, "
							+ "imovel.imov_id, "
							+ "imovel.imov_qteconomia, "
							+ "setor_comercial.stcm_cdsetorcomercial, "
							+ "setor_comercial.stcm_nmsetorcomercial, "
							+ "quadra.qdra_nnquadra, "
							+ "imovel.imov_nnlote, "
							+ "imovel.imov_nnsublote, "
							+ "ligacao_agua_situacao.last_dsabreviado, "
							+ "ligacao_esgoto_situacao.lest_dsabreviado, "
							+ "ligacao_esgoto.lesg_pcesgoto, "
							+ "ligacao_esgoto.lesg_dtligacao, "
							+ "ligacao_agua.lagu_dtligacaoagua, "
							+

							// Informações do Cliente Usuasrio e Resposanvel
							"cliente_usuario.clie_id as idClienteUsuario, "
							+ "cliente_usuario.clie_nmcliente as nomeClienteUsuario, "
							+ "cliente_responsavel.clie_id as idClienteResponsavel, "
							+ "cliente_responsavel.clie_nmcliente as nomeClienteResponsavel, "
							+ "logradouro.logr_nmlogradouro, "
							+ "logradouro_tipo.lgtp_dslogradourotipo, "
							+ "logradouro_titulo.lgtt_dslogradourotitulo, "
							+ "cep.cep_cdcep, "
							+ "endereco_referencia.edrf_dsenderecoreferencia, "
							+ "imovel.imov_dscomplementoendereco, "
							+ "imovel.imov_nnimovel, "
							+ "bairro.bair_nmbairro, "
							+ "municipio.muni_nmmunicipio, "
							+ "unidade_federacao.unfe_dsufsigla, "
							+ "imovel.imov_icimovelcondominio, "
							+ "imovel.imov_nnmorador, "
							+ "imovel.imov_idimovelcondominio, "
							+ "imovel.imov_idimovelprincipal, "
							+ "imovel.imov_nnpontosutilizacao, "
							+ "imovel_perfil.iper_dsimovelperfil, "
							+ "area_construida_faixa.acon_nnmaiorfaixa, "
							+ "area_construida_faixa.acon_nnmenorfaixa, "
							+ "imovel.imov_nnareaconstruida, "
							+ "pavimento_calcada.pcal_dspavimentocalcada, "
							+ "pavimento_rua.prua_dspavimentorua, "
							+ "despejo.depj_dsdespejo, "
							+ "reservatorio_volume_faixa.resv_vomenorfaixa, "
							+ "reservatorio_volume_faixa.resv_vomaiorfaixa, "
							+ "imovel.imov_voreservatoriosuperior, "
							+ "reservatorio_volume_faixa_inf.resv_vomenorfaixa, "
							+ "reservatorio_volume_faixa_inf.resv_vomaiorfaixa, "
							+ "imovel.imov_voreservatorioinferior, "
							+ "piscina_volume_faixa.pisc_vomenorfaixa, "
							+ "piscina_volume_faixa.pisc_vomaiorfaixa, "
							+ "imovel.imov_vopiscina, "
							+ "poco_tipo.poco_dspocotipo, "
							+ "ligacao_agua_diametro.lagd_dsligacaoaguadiametro as descLigAguaDiametroAgua, "
							+ "ligacao_agua_material.lagm_dsligacaoaguamaterial as descLigAguaMaterialAgua, "
							+ "ligacao_esgoto_diametro.legd_dsligacaoesgotodiametro as descLigEsgotoDiametroEsgoto, "
							+ "ligacao_esgoto_material.legm_dsligacaoesgotomaterial as descLigESgotoMaterialEsgoto, "
							+ "ligacao_esgoto.lesg_nnconsumominimoesgoto, "
							+ "ligacao_esgoto.lesg_pccoleta, "
							+ "ligacao_esgoto.lesg_pcesgoto, "
							+

							// Agua
							"hidrometro.hidr_nnhidrometro as idHidrometroAgua, "
							+ "hidrometro.hidr_nnanofabricacao as anoFabricancaoHidrometroAgua, "
							+ "hidrometro_capacidade.hicp_dshidrometrocapacidade as descHidromCapacidadeAgua, "
							+ "hidrometro_marca.himc_dshidrometromarca as descHidromMarcaAgua, "
							+ "hidrometro_diametro.hidm_dshidrometrodiametro as descHidromDiametroAgua, "
							+ "hidrometro_tipo.hitp_dshidrometrotipo as descHidromTipoAgua, "
							+ "hidrometro_instalacao_hist.hidi_dtinstalacaohidrometro as dataHidromInstHistoricoAgua, "
							+ "hidrometro_local_instalacao.hili_dshidrometrolocalinstalac as descHidromLocalInstAgua, "
							+ "hidrometro_protecao.hipr_dshidrometroprotecao as descHidromProtecaoAgua, "
							+ "hidrometro_instalacao_hist.hidi_iccavalete as indHidromInstHistoricoAgua, "
							+

							// Esgoto
							"hidrometro_esgoto.hidr_nnhidrometro as idHidrometroEsgoto, "
							+ "hidrometro_esgoto.hidr_nnanofabricacao as anoFabricacaoHidrometroEsgoto, "
							+ "hidrometro_capacidade_esgoto.hicp_dshidrometrocapacidade as descHidromCapacidadeEsgoto, "
							+ "hidrometro_marca_esgoto.himc_dshidrometromarca as descHidromMarcaEsgoto, "
							+ "hidrometro_diametro_esgoto.hidm_dshidrometrodiametro as descHidromDiametroEsgoto, "
							+ "hidrometro_tipo_esgoto.hitp_dshidrometrotipo as descHidromTipoEsgoto, "
							+ "hidrom_inst_hist_esgoto.hidi_dtinstalacaohidrometro as dataHidromInstHistoricoEsgoto, "
							+ "hidrometro_local_instalac_esg.hili_dshidrometrolocalinstalac as descHidtromLocalInstEsgoto, "
							+ "hidrometro_protecao_esgoto.hipr_dshidrometroprotecao as descHidromProtecaoEsgoto, "
							+ "hidrom_inst_hist_esgoto.hidi_iccavalete as indHidromInstEsgoto, "
							+

							// Ligacao Agua
							"ligacao_agua.lagu_nnconsumominimoagua, "
							+

							// Ligacao Esgoto
							"ligacao_esgoto.lesg_nnconsumominimoesgoto, "
							+

							// Jardim
							"imovel.imov_icjardim, "
							+

							// Rota
							"rota.rota_cdrota, "
							+

							// Sequencial Rota
							"imovel.imov_nnsequencialrota, "
							+

							// Logradouro
							"logradouro.logr_id, "

							+

							// Segmento
							"imovel.imov_nnsegmento, "

							+ // Rota do Imóvel
							"imovel.rota_id, "

							+ // id unidade negocio
							"unidadeNegocio.uneg_id, "

							+ // nome unidade negocio
							"unidadeNegocio.uneg_nmunidadenegocio "

							+

							// From
							"from imovel_subcategoria imovelSubcategoria "
							+ "inner join imovel on imovelSubcategoria.imov_id = imovel.imov_id "
							+ "inner join localidade on imovel.loca_id = localidade.loca_id "
							+ "inner join unidade_negocio unidadeNegocio on localidade.uneg_id = unidadeNegocio.uneg_id "
							+ "inner join gerencia_regional on localidade.greg_id = gerencia_regional.greg_id "
							+ "inner join setor_comercial on imovel.stcm_id = setor_comercial.stcm_id "
							+

							// Logradouro Bairro

							"left join logradouro_bairro on imovel.lgbr_id = logradouro_bairro.lgbr_id "
							+ "left join bairro on logradouro_bairro.bair_id = bairro.bair_id "
							+ "left join municipio on bairro.muni_id = municipio.muni_id "
							+ "inner join quadra on imovel.qdra_id = quadra.qdra_id "
							+ "inner join rota on imovel.rota_id = rota.rota_id "
							+

							// Logradouro Cep
							"left join logradouro_cep on imovel.lgcp_id = logradouro_cep.lgcp_id "
							+ "left join cep on logradouro_cep.cep_id = cep.cep_id "
							+ "left join logradouro on logradouro_cep.logr_id = logradouro.logr_id "
							+

							// AGUA
							"left join ligacao_agua_situacao on imovel.last_id = ligacao_agua_situacao.last_id "
							+ "left join ligacao_agua on imovel.imov_id = ligacao_agua.lagu_id "

							+

							// ESGOTO
							"left join ligacao_esgoto_situacao on imovel.lest_id = ligacao_esgoto_situacao.lest_id "
							+ "left join ligacao_esgoto on imovel.imov_id = ligacao_esgoto.lesg_id "
							+ "left join imovel_perfil on imovel.iper_id = imovel_perfil.iper_id "
							+ "left join poco_tipo on imovel.poco_id = poco_tipo.poco_id "
							+ "left join area_construida_faixa on imovel.acon_id = area_construida_faixa.acon_id "
							+

							// Cliente Usuario
							"left outer join cliente_imovel cliente_imovel_usuario on imovel.imov_id = cliente_imovel_usuario.imov_id  "
							+ " and (cliente_imovel_usuario.crtp_id=2 and cliente_imovel_usuario.clim_dtrelacaofim is null) "
							+ "left outer join cliente cliente_usuario on cliente_imovel_usuario.clie_id = cliente_usuario.clie_id "
							+

							// Cliente Resposanvel
							"left outer join cliente_imovel  cliente_imovel_responsavel on imovel.imov_id = cliente_imovel_responsavel.imov_id "
							+ " and (cliente_imovel_responsavel.crtp_id=3 and cliente_imovel_responsavel.clim_dtrelacaofim is null) "
							+ "left outer join cliente  cliente_responsavel on cliente_imovel_responsavel.clie_id = cliente_responsavel.clie_id "
							+

							// AGUA
							"left join hidrometro_instalacao_hist on ligacao_agua.hidi_id =  hidrometro_instalacao_hist.hidi_id "
							+

							// ESGOTO
							"left join hidrometro_instalacao_hist  hidrom_inst_hist_esgoto on imovel.hidi_id =  hidrom_inst_hist_esgoto.hidi_id "
							+

							// Relacionamento para o Relatorio de Imovel
							"left join logradouro_titulo on logradouro.lgtt_id = logradouro_titulo.lgtt_id "
							+ "left join logradouro_tipo on logradouro.lgtp_id = logradouro_tipo.lgtp_id "
							+ "left join endereco_referencia on imovel.edrf_id = endereco_referencia.edrf_id "
							+ "left join unidade_federacao on municipio.unfe_id = unidade_federacao.unfe_id "
							+ "left join reservatorio_volume_faixa on imovel.resv_idreservatoriosuperior = reservatorio_volume_faixa.resv_id "
							+ "left join reservatorio_volume_faixa reservatorio_volume_faixa_inf on imovel.resv_idreservatorioinferior = reservatorio_volume_faixa_inf.resv_id "
							+ "left join piscina_volume_faixa on imovel.pisc_id = piscina_volume_faixa.pisc_id "
							+ "left join pavimento_calcada on imovel.pcal_id = pavimento_calcada.pcal_id "
							+ "left join pavimento_rua on imovel.prua_id = pavimento_rua.prua_id "
							+ "left join despejo on imovel.depj_id = despejo.depj_id "
							+

							// AGUA
							"left join ligacao_agua_diametro on ligacao_agua.lagd_id = ligacao_agua_diametro.lagd_id "
							+ "left join ligacao_agua_material on ligacao_agua.lagm_id = ligacao_agua_material.lagm_id "
							+

							// ESGOTO
							"left join ligacao_esgoto_diametro on ligacao_esgoto.legd_id = ligacao_esgoto_diametro.legd_id "
							+ "left join ligacao_esgoto_material on ligacao_esgoto.legm_id = ligacao_esgoto_material.legm_id "
							+

							// AGUA
							"left join hidrometro on hidrometro_instalacao_hist.hidr_id = hidrometro.hidr_id "
							+ "left join hidrometro_capacidade on hidrometro.hicp_id = hidrometro_capacidade.hicp_id "
							+ "left join hidrometro_marca on hidrometro.himc_id = hidrometro_marca.himc_id "
							+ "left join hidrometro_diametro on hidrometro.hidm_id = hidrometro_diametro.hidm_id "
							+ "left join hidrometro_tipo on hidrometro.hitp_id = hidrometro_tipo.hitp_id "
							+ "left join hidrometro_local_instalacao on hidrometro_instalacao_hist.hili_id = hidrometro_local_instalacao.hili_id "
							+ "left join hidrometro_protecao on hidrometro_instalacao_hist.hipr_id = hidrometro_protecao.hipr_id "
							+

							// ESGOTO
							"left join hidrometro  hidrometro_esgoto on hidrom_inst_hist_esgoto.hidr_id = hidrometro_esgoto.hidr_id "
							+ "left join hidrometro_capacidade  hidrometro_capacidade_esgoto on hidrometro_esgoto.hicp_id = hidrometro_capacidade_esgoto.hicp_id "
							+ "left join hidrometro_marca  hidrometro_marca_esgoto on hidrometro_esgoto.himc_id = hidrometro_marca_esgoto.himc_id "
							+ "left join hidrometro_diametro  hidrometro_diametro_esgoto on hidrometro_esgoto.hidm_id = hidrometro_diametro_esgoto.hidm_id "
							+ "left join hidrometro_tipo  hidrometro_tipo_esgoto on hidrometro_esgoto.hitp_id = hidrometro_tipo_esgoto.hitp_id "
							+ "left join hidrometro_local_instalacao  hidrometro_local_instalac_esg on hidrom_inst_hist_esgoto.hili_id = hidrometro_local_instalacao.hili_id "
							+ "left join hidrometro_protecao  hidrometro_protecao_esgoto on hidrom_inst_hist_esgoto.hipr_id = hidrometro_protecao_esgoto.hipr_id "

							// Rota do Imóvel
							+ "left join rota rotaImovel on rotaImovel.rota_id = imovel.rota_id ";

			consulta = consulta
							+ montarInnerJoinFiltrarImoveisOutrosCriterios(intervaloMediaMinimaImovelInicial,
											intervaloMediaMinimaImovelFinal, intervaloMediaMinimaHidrometroInicial,
											intervaloMediaMinimaHidrometroFinal, idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo,
											idSituacaoEspecialCobranca, idEloAnormalidade, idCadastroOcorrencia, idConsumoTarifa,
											idTipoMedicao, indicadorMedicao, idSubCategoria, idCategoria, idCliente, idClienteTipo,
											idClienteRelacaoTipo, ConstantesSistema.GERAR_RELATORIO_IMOVEL);

			consulta = consulta
							+ montarCondicaoSQLWhereFiltrarImovelOutrosCriterio(idImovelCondominio, idImovelPrincipal,
											idSituacaoLigacaoAgua, consumoMinimoInicialAgua, consumoMinimoFinalAgua,
											idSituacaoLigacaoEsgoto, consumoMinimoInicialEsgoto, consumoMinimoFinalEsgoto,
											intervaloValorPercentualEsgotoInicial, intervaloValorPercentualEsgotoFinal,
											intervaloMediaMinimaImovelInicial, intervaloMediaMinimaImovelFinal,
											intervaloMediaMinimaHidrometroInicial, intervaloMediaMinimaHidrometroFinal, idImovelPerfil,
											idPocoTipo, idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo, idSituacaoEspecialCobranca,
											idEloAnormalidade, areaConstruidaInicial, areaConstruidaFinal, idCadastroOcorrencia,
											idConsumoTarifa, idGerenciaRegional, idLocalidadeInicial, idLocalidadeFinal,
											setorComercialInicial, setorComercialFinal, quadraInicial, quadraFinal, loteOrigem, loteDestno,
											cep, logradouro, bairro, municipio, idTipoMedicao, indicadorMedicao, idSubCategoria,
											idCategoria, quantidadeEconomiasInicial, quantidadeEconomiasFinal, diaVencimento, idCliente,
											idClienteTipo, idClienteRelacaoTipo, numeroPontosInicial, numeroPontosFinal,
											numeroMoradoresInicial, numeroMoradoresFinal, idAreaConstruidaFaixa, idUnidadeNegocio,
											ConstantesSistema.GERAR_RELATORIO_IMOVEL, cdRotaInicial, cdRotaFinal, sequencialRotaInicial,
											sequencialRotaFinal, segmentoInicial, segmentoFinal, subloteInicial, subloteFinal);

			/*
			 * # COLOCANDO O VALOR NAS CONDIÇÕES#
			 */

			SQLQuery query = session.createSQLQuery(consulta.substring(0, (consulta.length() - 5)));
			informarDadosQueryFiltrarImovelOutrosCriterio(query, idImovelCondominio, idImovelPrincipal, idSituacaoLigacaoAgua,
							consumoMinimoInicialAgua, consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto, consumoMinimoInicialEsgoto,
							consumoMinimoFinalEsgoto, intervaloValorPercentualEsgotoInicial, intervaloValorPercentualEsgotoFinal,
							intervaloMediaMinimaImovelInicial, intervaloMediaMinimaImovelFinal, intervaloMediaMinimaHidrometroInicial,
							intervaloMediaMinimaHidrometroFinal, idImovelPerfil, idPocoTipo, idFaturamentoSituacaoTipo,
							idCobrancaSituacaoTipo, idSituacaoEspecialCobranca, idEloAnormalidade, areaConstruidaInicial,
							areaConstruidaFinal, idCadastroOcorrencia, idConsumoTarifa, idGerenciaRegional, idLocalidadeInicial,
							idLocalidadeFinal, setorComercialInicial, setorComercialFinal, quadraInicial, quadraFinal, loteOrigem,
							loteDestno, cep, logradouro, bairro, municipio, idTipoMedicao, indicadorMedicao, idSubCategoria, idCategoria,
							quantidadeEconomiasInicial, quantidadeEconomiasFinal, diaVencimento, idCliente, idClienteTipo,
							idClienteRelacaoTipo, numeroPontosInicial, numeroPontosFinal, numeroMoradoresInicial, numeroMoradoresFinal,
							idAreaConstruidaFaixa, idUnidadeNegocio, cdRotaInicial, cdRotaFinal, sequencialRotaInicial,
							sequencialRotaFinal, segmentoInicial, segmentoFinal, subloteInicial, subloteFinal);

			retorno = query.addScalar("greg_id", Hibernate.INTEGER).addScalar("greg_nmabreviado", Hibernate.STRING).addScalar("loca_id",
							Hibernate.INTEGER).addScalar("loca_nmlocalidade", Hibernate.STRING).addScalar("imov_id", Hibernate.INTEGER)
							.addScalar("imov_qteconomia", Hibernate.SHORT).addScalar("stcm_cdsetorcomercial", Hibernate.INTEGER).addScalar(
											"stcm_nmsetorcomercial", Hibernate.STRING).addScalar("qdra_nnquadra", Hibernate.INTEGER)
							.addScalar("imov_nnlote", Hibernate.SHORT).addScalar("imov_nnsublote", Hibernate.SHORT).addScalar(
											"last_dsabreviado", Hibernate.STRING).addScalar("lest_dsabreviado", Hibernate.STRING)
							.addScalar("lesg_pcesgoto", Hibernate.BIG_DECIMAL).addScalar("lesg_dtligacao", Hibernate.DATE).addScalar(
											"lagu_dtligacaoagua", Hibernate.DATE).addScalar("idClienteUsuario", Hibernate.INTEGER)
							.addScalar("nomeClienteUsuario", Hibernate.STRING).addScalar("idClienteResponsavel", Hibernate.INTEGER)
							.addScalar("nomeClienteResponsavel", Hibernate.STRING).addScalar("logr_nmlogradouro", Hibernate.STRING)
							.addScalar("lgtp_dslogradourotipo", Hibernate.STRING).addScalar("lgtt_dslogradourotitulo", Hibernate.STRING)
							.addScalar("cep_cdcep", Hibernate.INTEGER).addScalar("edrf_dsenderecoreferencia", Hibernate.STRING).addScalar(
											"imov_dscomplementoendereco", Hibernate.STRING).addScalar("imov_nnimovel", Hibernate.STRING)
							.addScalar("bair_nmbairro", Hibernate.STRING).addScalar("muni_nmmunicipio", Hibernate.STRING).addScalar(
											"unfe_dsufsigla", Hibernate.STRING).addScalar("imov_icimovelcondominio", Hibernate.INTEGER)
							.addScalar("imov_nnmorador", Hibernate.INTEGER).addScalar("imov_idimovelcondominio", Hibernate.INTEGER)
							.addScalar("imov_idimovelprincipal", Hibernate.INTEGER).addScalar("imov_nnpontosutilizacao", Hibernate.INTEGER)
							.addScalar("iper_dsimovelperfil", Hibernate.STRING).addScalar("acon_nnmaiorfaixa", Hibernate.INTEGER)
							.addScalar("acon_nnmenorfaixa", Hibernate.INTEGER).addScalar("imov_nnareaconstruida", Hibernate.BIG_DECIMAL)
							.addScalar("pcal_dspavimentocalcada", Hibernate.STRING).addScalar("prua_dspavimentorua", Hibernate.STRING)
							.addScalar("depj_dsdespejo", Hibernate.STRING).addScalar("resv_vomenorfaixa", Hibernate.BIG_DECIMAL).addScalar(
											"resv_vomaiorfaixa", Hibernate.BIG_DECIMAL).addScalar("imov_voreservatoriosuperior",
											Hibernate.BIG_DECIMAL).addScalar("resv_vomenorfaixa", Hibernate.BIG_DECIMAL).addScalar(
											"resv_vomaiorfaixa", Hibernate.BIG_DECIMAL).addScalar("imov_voreservatorioinferior",
											Hibernate.BIG_DECIMAL).addScalar("pisc_vomenorfaixa", Hibernate.BIG_DECIMAL).addScalar(
											"pisc_vomaiorfaixa", Hibernate.BIG_DECIMAL).addScalar("imov_vopiscina", Hibernate.BIG_DECIMAL)
							.addScalar("poco_dspocotipo", Hibernate.STRING).addScalar("descLigAguaDiametroAgua", Hibernate.STRING)
							.addScalar("descLigAguaMaterialAgua", Hibernate.STRING).addScalar("descLigEsgotoDiametroEsgoto",
											Hibernate.STRING).addScalar("descLigESgotoMaterialEsgoto", Hibernate.STRING).addScalar(
											"lesg_nnconsumominimoesgoto", Hibernate.INTEGER).addScalar("lesg_pccoleta",
											Hibernate.BIG_DECIMAL).addScalar("lesg_pcesgoto", Hibernate.BIG_DECIMAL).addScalar(
											"idHidrometroAgua", Hibernate.STRING).addScalar("anoFabricancaoHidrometroAgua",
											Hibernate.INTEGER).addScalar("descHidromCapacidadeAgua", Hibernate.STRING).addScalar(
											"descHidromMarcaAgua", Hibernate.STRING).addScalar("descHidromDiametroAgua", Hibernate.STRING)
							.addScalar("descHidromTipoAgua", Hibernate.STRING).addScalar("dataHidromInstHistoricoAgua", Hibernate.DATE)
							.addScalar("descHidromLocalInstAgua", Hibernate.STRING).addScalar("descHidromProtecaoAgua", Hibernate.STRING)
							.addScalar("indHidromInstHistoricoAgua", Hibernate.INTEGER).addScalar("idHidrometroEsgoto", Hibernate.STRING)
							.addScalar("anoFabricacaoHidrometroEsgoto", Hibernate.INTEGER).addScalar("descHidromCapacidadeEsgoto",
											Hibernate.STRING).addScalar("descHidromMarcaEsgoto", Hibernate.STRING).addScalar(
											"descHidromDiametroEsgoto", Hibernate.STRING).addScalar("descHidromTipoEsgoto",
											Hibernate.STRING).addScalar("dataHidromInstHistoricoEsgoto", Hibernate.DATE).addScalar(
											"descHidtromLocalInstEsgoto", Hibernate.STRING).addScalar("descHidromProtecaoEsgoto",
											Hibernate.STRING).addScalar("indHidromInstEsgoto", Hibernate.INTEGER).addScalar(
											"lagu_nnconsumominimoagua", Hibernate.INTEGER).addScalar("lesg_nnconsumominimoesgoto",
											Hibernate.INTEGER).addScalar("imov_icjardim", Hibernate.SHORT).addScalar("rota_cdrota",
											Hibernate.SHORT).addScalar("imov_nnsequencialrota", Hibernate.INTEGER).addScalar("logr_id",
											Hibernate.INTEGER).addScalar("imov_nnsegmento", Hibernate.SHORT).addScalar("rota_id",
											Hibernate.INTEGER).addScalar("uneg_id", Hibernate.INTEGER).addScalar("uneg_nmunidadenegocio",
											Hibernate.STRING).list();

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
	 * O método abaixo realiza uma pesquisa em imovel e retorna os campos
	 * necessários para a criação da inscrição para exibição.
	 * acima no controlador será montada a inscrição
	 */

	public Object[] pesquisarInscricaoImovel(Integer idImovel)

	throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		Object[] retorno = null;

		try{

			String sql = "select loc.loca_id as idLocalidade, "

			+ " sc.stcm_cdsetorcomercial as codigoSetorComercial, "

			+ " qd.qdra_nnquadra as numeroQuadra, "

			+ "	im.imov_nnlote as lote, "

			+ "	im.imov_nnsublote as subLote, "

			+ "	im.rota_id as rota, "

			+ "	im.imov_nnsegmento as segmento  "

			+ " from imovel im, "

			+ " localidade loc, "

			+ " setor_comercial sc, " + " quadra qd "

			+ " where loc.loca_id = im.loca_id and "

			+ "	im.stcm_id = sc.stcm_id and "

			+ "	im.qdra_id = qd.qdra_id and " + "	im.imov_id = "

			+ idImovel + "	and im.imov_icexclusao = "

			+ ConstantesSistema.INDICADOR_IMOVEL_ATIVO;

			Collection colecaoConsulta = session.createSQLQuery(sql).addScalar(

			"idLocalidade", Hibernate.INTEGER).addScalar(

			"codigoSetorComercial", Hibernate.INTEGER).addScalar(

			"numeroQuadra", Hibernate.INTEGER).addScalar("lote",

			Hibernate.SHORT).addScalar("subLote", Hibernate.SHORT).addScalar("rota", Hibernate.INTEGER).addScalar("segmento",
							Hibernate.SHORT)

			.list();

			retorno = (Object[]) Util.retonarObjetoDeColecao(colecaoConsulta);

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
	 * O método abaixo realiza uma pesquisa em imovel e retorna os campos
	 * necessários para a criação da inscrição para exibição.
	 * acima no controlador será montada a inscrição
	 */

	public Object[] pesquisarLocalidadeSetorImovel(Integer idImovel)

	throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		Object[] retorno = null;

		try{

			String sql = "select loc.loca_nmlocalidade as descricaoLocalidade, "

			+ " sc.stcm_cdsetorcomercial as codigoSetorComercial "

			+ " from imovel im, "

			+ " localidade loc, "

			+ " setor_comercial sc "

			+ " where loc.loca_id = im.loca_id and "

			+ "	im.stcm_id = sc.stcm_id and "

			+ "	im.imov_id = "

			+ idImovel + "	and im.imov_icexclusao = "

			+ ConstantesSistema.INDICADOR_IMOVEL_ATIVO;

			Collection colecaoConsulta = session.createSQLQuery(sql).addScalar(

			"descricaoLocalidade", Hibernate.STRING).addScalar(

			"codigoSetorComercial", Hibernate.INTEGER)

			.list();

			retorno = (Object[]) Util.retonarObjetoDeColecao(colecaoConsulta);

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
	 * Gerar Relatório de Dados de Economias do Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 02/08/2006
	 */

	public Collection gerarRelatorioDadosEconomiasImovelOutrosCriterios(

	String idImovelCondominio, String idImovelPrincipal,

	String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua,

	String consumoMinimoFinalAgua, String[] idSituacaoLigacaoEsgoto,

	String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,

	String intervaloValorPercentualEsgotoInicial,

	String intervaloValorPercentualEsgotoFinal,

	String intervaloMediaMinimaImovelInicial,

	String intervaloMediaMinimaImovelFinal,

	String intervaloMediaMinimaHidrometroInicial,

	String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,

	String idPocoTipo, String idFaturamentoSituacaoTipo,

	String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,

	String idEloAnormalidade, String areaConstruidaInicial,

	String areaConstruidaFinal, String idCadastroOcorrencia,

	String idConsumoTarifa, String idGerenciaRegional,

	String idLocalidadeInicial, String idLocalidadeFinal,

	String setorComercialInicial, String setorComercialFinal,

	String quadraInicial, String quadraFinal, String loteOrigem,

	String loteDestno, String cep, String logradouro, String bairro,

	String municipio, String idTipoMedicao, String indicadorMedicao,

	String idSubCategoria, String idCategoria,

	String quantidadeEconomiasInicial, String quantidadeEconomiasFinal,

	String diaVencimento, String idCliente, String idClienteTipo,

	String idClienteRelacaoTipo, String numeroPontosInicial,

	String numeroPontosFinal, String numeroMoradoresInicial,

	String numeroMoradoresFinal, String idAreaConstruidaFaixa,

	String idUnidadeNegocio, String segmentoInicial, String segmentoFinal, String rotaInicial, String rotaFinal,
					String sequencialRotaInicial, String sequencialRotaFinal, String subloteInicial, String subloteFinal)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = consulta

			+ "select distinct "

			+

			" imovel.imov_id,"

			+ " gerencia_regional.greg_id, "

			+ " gerencia_regional.greg_nmabreviado, "

			+ " localidade.loca_id ,"

			+ " localidade.loca_nmlocalidade, "

			+ " setor_comercial.stcm_cdsetorcomercial, "

			+ " setor_comercial.stcm_nmsetorcomercial "

			+

			// From

							"from imovel_subcategoria imovelSubcategoria "

							+

							"inner join imovel on imovelSubcategoria.imov_id = imovel.imov_id "

							+ "inner join localidade on imovel.loca_id = localidade.loca_id "

							+ "inner join gerencia_regional on localidade.greg_id = gerencia_regional.greg_id "

							+ "inner join setor_comercial on imovel.stcm_id = setor_comercial.stcm_id "

							+

							// Logradouro Bairro

							"left join logradouro_bairro on imovel.lgbr_id = logradouro_bairro.lgbr_id "

							+ "left join bairro on logradouro_bairro.bair_id = bairro.bair_id "

							+ "left join municipio on bairro.muni_id = municipio.muni_id "

							+ "inner join quadra on imovel.qdra_id = quadra.qdra_id "

							+

							// Logradouro Cep

							"left join logradouro_cep on imovel.lgcp_id = logradouro_cep.lgcp_id "

							+ "left join cep on logradouro_cep.cep_id = cep.cep_id "

							+ "left join logradouro on logradouro_cep.logr_id = logradouro.logr_id "

							+

							// AGUA

							"left join ligacao_agua_situacao on imovel.last_id = ligacao_agua_situacao.last_id "

							+ "left join ligacao_agua on imovel.imov_id = ligacao_agua.lagu_id "

							+

							// ESGOTO

							"left join ligacao_esgoto_situacao on imovel.lest_id = ligacao_esgoto_situacao.lest_id "

							+ "left join ligacao_esgoto on imovel.imov_id = ligacao_esgoto.lesg_id "

							+

							"left join imovel_perfil on imovel.iper_id = imovel_perfil.iper_id "

							+

							"left join poco_tipo on imovel.poco_id = poco_tipo.poco_id "

							+

							"left join area_construida_faixa on imovel.acon_id = area_construida_faixa.acon_id "

							+

							// AGUA

							"left join hidrometro_instalacao_hist on ligacao_agua.hidi_id =  hidrometro_instalacao_hist.hidi_id "

							+

							// ESGOTO

							"left join hidrometro_instalacao_hist  hidrom_inst_hist_esgoto on imovel.hidi_id =  hidrom_inst_hist_esgoto.hidi_id "

							// Rota do Imóvel
							+ "left join rota rotaImovel on rotaImovel.rota_id = imovel.rota_id ";

			consulta = consulta

			+ montarInnerJoinFiltrarImoveisOutrosCriterios(

			intervaloMediaMinimaImovelInicial,

			intervaloMediaMinimaImovelFinal,

			intervaloMediaMinimaHidrometroInicial,

			intervaloMediaMinimaHidrometroFinal,

			idFaturamentoSituacaoTipo,

			idCobrancaSituacaoTipo,

			idSituacaoEspecialCobranca,

			idEloAnormalidade,

			idCadastroOcorrencia,

			idConsumoTarifa,

			idTipoMedicao,

			indicadorMedicao,

			idSubCategoria,

			idCategoria,

			idCliente,

			idClienteTipo,

			idClienteRelacaoTipo,

			ConstantesSistema.GERAR_RELATORIO_DADOS_ECONOMIA_IMOVEL);

			consulta = consulta

							+ montarCondicaoSQLWhereFiltrarImovelOutrosCriterio(

							idImovelCondominio,

							idImovelPrincipal,

							idSituacaoLigacaoAgua,

							consumoMinimoInicialAgua,

							consumoMinimoFinalAgua,

							idSituacaoLigacaoEsgoto,

							consumoMinimoInicialEsgoto,

							consumoMinimoFinalEsgoto,

							intervaloValorPercentualEsgotoInicial,

							intervaloValorPercentualEsgotoFinal,

							intervaloMediaMinimaImovelInicial,

							intervaloMediaMinimaImovelFinal,

							intervaloMediaMinimaHidrometroInicial,

							intervaloMediaMinimaHidrometroFinal,

							idImovelPerfil,

							idPocoTipo,

							idFaturamentoSituacaoTipo,

							idCobrancaSituacaoTipo,

							idSituacaoEspecialCobranca,

							idEloAnormalidade,

							areaConstruidaInicial,

							areaConstruidaFinal,

							idCadastroOcorrencia,

							idConsumoTarifa,

							idGerenciaRegional,

							idLocalidadeInicial,

							idLocalidadeFinal,

							setorComercialInicial,

							setorComercialFinal,

							quadraInicial,

							quadraFinal,

							loteOrigem,

							loteDestno,

							cep,

							logradouro,

							bairro,

							municipio,

							idTipoMedicao,

							indicadorMedicao,

							idSubCategoria,

							idCategoria,

							quantidadeEconomiasInicial,

							quantidadeEconomiasFinal,

							diaVencimento,

							idCliente,

							idClienteTipo,

							idClienteRelacaoTipo,

							numeroPontosInicial,

							numeroPontosFinal,

							numeroMoradoresInicial,

							numeroMoradoresFinal,

							idAreaConstruidaFaixa,

							idUnidadeNegocio,

							ConstantesSistema.GERAR_RELATORIO_DADOS_ECONOMIA_IMOVEL, rotaInicial, rotaFinal, sequencialRotaInicial,
											sequencialRotaFinal, segmentoInicial, segmentoFinal, subloteInicial, subloteFinal);

			/*
			 * # COLOCANDO O VALOR NAS CONDIÇÕES#
			 */

			SQLQuery query = session.createSQLQuery(consulta.substring(0,

			(consulta.length() - 5)));

			informarDadosQueryFiltrarImovelOutrosCriterio(query,

			idImovelCondominio, idImovelPrincipal,

			idSituacaoLigacaoAgua, consumoMinimoInicialAgua,

			consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto,

			consumoMinimoInicialEsgoto, consumoMinimoFinalEsgoto,

			intervaloValorPercentualEsgotoInicial,

			intervaloValorPercentualEsgotoFinal,

			intervaloMediaMinimaImovelInicial,

			intervaloMediaMinimaImovelFinal,

			intervaloMediaMinimaHidrometroInicial,

			intervaloMediaMinimaHidrometroFinal, idImovelPerfil,

			idPocoTipo, idFaturamentoSituacaoTipo,

			idCobrancaSituacaoTipo, idSituacaoEspecialCobranca,

			idEloAnormalidade, areaConstruidaInicial,

			areaConstruidaFinal, idCadastroOcorrencia, idConsumoTarifa,

			idGerenciaRegional, idLocalidadeInicial, idLocalidadeFinal,

			setorComercialInicial, setorComercialFinal, quadraInicial,

			quadraFinal, loteOrigem, loteDestno, cep, logradouro,

			bairro, municipio, idTipoMedicao, indicadorMedicao,

			idSubCategoria, idCategoria, quantidadeEconomiasInicial,

			quantidadeEconomiasFinal, diaVencimento, idCliente,

			idClienteTipo, idClienteRelacaoTipo, numeroPontosInicial,

			numeroPontosFinal, numeroMoradoresInicial,

			numeroMoradoresFinal, idAreaConstruidaFaixa,

			idUnidadeNegocio, rotaInicial, rotaFinal, sequencialRotaInicial, sequencialRotaFinal, segmentoInicial, segmentoFinal,
							subloteInicial, subloteFinal);

			retorno = query.addScalar("imov_id", Hibernate.INTEGER).addScalar(

			"greg_id", Hibernate.INTEGER).addScalar("greg_nmabreviado",

			Hibernate.STRING).addScalar("loca_id", Hibernate.INTEGER)

			.addScalar("loca_nmlocalidade", Hibernate.STRING)

			.addScalar("stcm_cdsetorcomercial", Hibernate.INTEGER)

			.addScalar("stcm_nmsetorcomercial", Hibernate.STRING)

			.list();

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
	 * Obtem os dados da Subcategoria do Imovel para o Relatorio de Dados
	 * Economias para o Imovel
	 * 
	 * @author Rafael Santos
	 * @date 02/08/2006
	 */

	public Collection gerarRelatorioDadosEconomiasImovelSubcategoria(

	String idImovel) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = consulta

			+ "select distinct "

			+ " subcategoria.scat_dssubcategoria, "

			+ " categoria.catg_dscategoria, "

			+ " imovelSubcategoria.imsb_qteconomia, "

			+ " subcategoria.scat_id, "

			+ " categoria.catg_dsabreviado "

			+ "from imovel_subcategoria imovelSubcategoria "

			+ "left join imovel on imovelSubcategoria.imov_id = imovel.imov_id "

			+ "left join subcategoria on imovelSubcategoria.scat_id = subcategoria.scat_id "

			+ "left join categoria on subcategoria.catg_id = categoria.catg_id";

			consulta = consulta + " where imovel.imov_id = :idImovel ";

			SQLQuery query = session.createSQLQuery(consulta);

			query.setInteger("idImovel", new Integer(idImovel).intValue());

			retorno = query.addScalar("scat_dssubcategoria", Hibernate.STRING)

			.addScalar("catg_dscategoria", Hibernate.STRING).addScalar(

			"imsb_qteconomia", Hibernate.SHORT).addScalar(

			"scat_id", Hibernate.INTEGER).addScalar("catg_dsabreviado", Hibernate.STRING)

			.list();

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
	 * Obtem os dados do Imovel Economia do Imovel para o Relatorio de Dados
	 * Economias para o Imovel
	 * 
	 * @author Rafael Santos
	 * @date 02/08/2006
	 */

	public Collection gerarRelatorioDadosEconomiasImovelEconomia(

	String idImovel, String idSubCategoria)

	throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = consulta

			+ "select distinct "

			+ " imovelEconomia.imec_dscomplementoendereco, "

			+ " imovelEconomia.imec_nnpontosutilizacao, "

			+ " imovelEconomia.imec_nnmorador, "

			+ " imovelEconomia.imec_nniptu, "

			+ " imovelEconomia.imec_nncontratoenergia, "

			+ " imovelEconomia.imec_nnareaconstruida, "

			+ " imovelEconomia.imec_id "

			+

			" from imovel_economia imovelEconomia "

			+ " left join imovel on imovelEconomia.imov_id = imovel.imov_id "

			+ " left join imovel_subcategoria on imovelEconomia.scat_id = imovel_subcategoria.scat_id ";

			consulta = consulta

			+ " where imovelEconomia.imov_id = :idImovel and imovelEconomia.scat_id = :idSubcategoria ";

			SQLQuery query = session.createSQLQuery(consulta);

			query.setInteger("idImovel", new Integer(idImovel).intValue());

			query.setInteger("idSubcategoria", new Integer(idSubCategoria)

			.intValue());

			retorno = query.addScalar("imec_dscomplementoendereco",

			Hibernate.STRING).addScalar("imec_nnpontosutilizacao",

			Hibernate.SHORT).addScalar("imec_nnmorador",

			Hibernate.SHORT).addScalar("imec_nniptu",

			Hibernate.BIG_DECIMAL).addScalar("imec_nncontratoenergia",

			Hibernate.LONG).addScalar("imec_nnareaconstruida",

			Hibernate.BIG_DECIMAL).addScalar("imec_id",

			Hibernate.INTEGER)

			.list();

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
	 * Obtem os dados do Cliente Imovel Economia do Imovel para o Relatorio de
	 * Dados Economias para o Imovel
	 * 
	 * @author Rafael Santos
	 * @date 02/08/2006
	 */

	public Collection gerarRelatorioDadosEconomiasImovelClienteEconomia(

	String idImovelEconomia) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = consulta

			+ "select distinct "

			+ " cliente.clie_nmcliente, "

			+ " cliente_relacao_tipo.crtp_dsclienterelacaotipo, "

			+ " cliente.clie_nncpf, "

			+ " cliente.clie_nncnpj, "

			+ " clienteImovelEconomia.cime_dtrelacaoinicio, "

			+ " clienteImovelEconomia.cime_dtrelacaofim, "

			+ " cliente_imovel_fim_relc_motv.cifr_dsfimrelacaomotivo "

			+ " from cliente_imovel_economia clienteImovelEconomia "

			+ " left join imovel_economia on clienteImovelEconomia.imec_id = imovel_economia.imec_id "

			+ " left join cliente on clienteImovelEconomia.clie_id = cliente.clie_id "

			+ " left join cliente_relacao_tipo on clienteImovelEconomia.crtp_id = cliente_relacao_tipo.crtp_id "

			+ " left join cliente_imovel_fim_relc_motv on clienteImovelEconomia.cifr_id = cliente_imovel_fim_relc_motv.cifr_id ";

			consulta = consulta

			+ " where clienteImovelEconomia.imec_id = :idImovelEconomia ";

			SQLQuery query = session.createSQLQuery(consulta);

			query.setInteger("idImovelEconomia", new Integer(idImovelEconomia)

			.intValue());

			retorno = query.addScalar("clie_nmcliente", Hibernate.STRING)

			.addScalar("crtp_dsclienterelacaotipo", Hibernate.STRING)

			.addScalar("clie_nncpf", Hibernate.STRING).addScalar(

			"clie_nncnpj", Hibernate.STRING).addScalar(

			"cime_dtrelacaoinicio", Hibernate.DATE).addScalar(

			"cime_dtrelacaofim", Hibernate.DATE).addScalar(

			"cifr_dsfimrelacaomotivo", Hibernate.STRING).list();

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
	 * Esse método é usado para fzazer uma pesquisa na tabela imóvel e confirmar
	 * se o id passado é de um imóvel excluído(idExclusao)
	 * Flávio Cordeiro
	 */

	public Boolean confirmarImovelExcluido(Integer idImovel)

	throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		boolean retorno = false;

		try{

			String sql = "select im.imov_id as idImovel "

			+ " from imovel im " + " where im.imov_id = "

			+ idImovel + " and im.imov_icexclusao = "

			+ ConstantesSistema.INDICADOR_IMOVEL_EXCLUIDO;

			Collection colecaoConsulta = session.createSQLQuery(sql).addScalar(

			"idImovel", Hibernate.INTEGER).list();

			if(!colecaoConsulta.isEmpty()){

				retorno = true;

			}else{

				retorno = false;

			}

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
	 * [UC164] Filtrar Imoveis Outros Criterios
	 * Monta os inner join da query de acordo com os parametros informados
	 * 
	 * @author Rafael Santos
	 * @date 03/08/2006
	 * @return
	 */

	public String montarInnerJoinFiltrarImoveisOutrosCriterios(

	String intervaloMediaMinimaImovelInicial,

	String intervaloMediaMinimaImovelFinal,

	String intervaloMediaMinimaHidrometroInicial,

	String intervaloMediaMinimaHidrometroFinal,

	String idFaturamentoSituacaoTipo, String idCobrancaSituacaoTipo,

	String idSituacaoEspecialCobranca, String idEloAnormalidade,

	String idCadastroOcorrencia, String idConsumoTarifa,

	String idTipoMedicao, String indicadorMedicao,

	String idSubCategoria, String idCategoria, String idCliente,

	String idClienteTipo, String idClienteRelacaoTipo, Integer relatorio){

		String innerJoin = "";

		// Relatorio de Economias do Imovel

		if(relatorio != null

		&& relatorio.intValue() == ConstantesSistema.GERAR_RELATORIO_DADOS_ECONOMIA_IMOVEL

		.intValue()){

			innerJoin = innerJoin

			+ " left join imovel_economia on imovel.imov_id = imovel_economia.imov_id ";

		}

		// faturamento situacao tipo

		if(idFaturamentoSituacaoTipo != null

		&& !idFaturamentoSituacaoTipo.equals("")

		&& !idFaturamentoSituacaoTipo.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			innerJoin = innerJoin

			+ " left join faturamento_situacao_tipo on imovel.ftst_id = faturamento_situacao_tipo.ftst_id ";

		}

		// Situacao Especial Cobranca

		if(idSituacaoEspecialCobranca != null

		&& !idSituacaoEspecialCobranca.equals("")

		&& !idSituacaoEspecialCobranca.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			innerJoin = innerJoin

			+ " left join cobranca_situacao_tipo on imovel.cbsp_id = cobranca_situacao_tipo.cbsp_id ";

		}

		// cobranca situacao tipo

		if(idCobrancaSituacaoTipo != null

		&& !idCobrancaSituacaoTipo.equals("")

		&& !idCobrancaSituacaoTipo.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			innerJoin = innerJoin

			+ " left join cobranca_situacao on imovel.cbst_id = cobranca_situacao.cbst_id ";

		}

		// elo anormalidade

		if(idEloAnormalidade != null

		&& !idEloAnormalidade.equals("")

		&& !idEloAnormalidade.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			innerJoin = innerJoin

			+ " left join elo_anormalidade on imovel.eanm_id = elo_anormalidade.eanm_id ";

		}

		// cadastro ocorrencia

		if(idCadastroOcorrencia != null

		&& !idCadastroOcorrencia.equals("")

		&& !idCadastroOcorrencia.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			innerJoin = innerJoin

			+ " left join cadastro_ocorrencia on imovel.cocr_id = cadastro_ocorrencia.cocr_id ";

		}

		// consumo tarifa

		if(idConsumoTarifa != null

		&& !idConsumoTarifa.equals("")

		&& !idConsumoTarifa.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			innerJoin = innerJoin

			+ " left join consumo_tarifa on imovel.cstf_id = consumo_tarifa.cstf_id ";

		}

		// sub categoria

		if(idSubCategoria != null

		&& !idSubCategoria.equals("")

		&& !idSubCategoria.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			innerJoin = innerJoin

			+ " left join subcategoria on imovelSubcategoria.scat_id = subcategoria.scat_id ";

		}

		// categoria

		if(idCategoria != null

		&& !idCategoria.equals("")

		&& !idCategoria.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			if(!(idSubCategoria != null && !idSubCategoria.equals("") && !idSubCategoria

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				innerJoin = innerJoin

				+ " left join subcategoria on imovelSubcategoria.scat_id = subcategoria.scat_id ";

			}

			innerJoin = innerJoin

			+ " left join categoria on subcategoria.catg_id = categoria.catg_id ";

		}

		// cliente imovel

		if(((idClienteRelacaoTipo != null && !idClienteRelacaoTipo.equals("") && !idClienteRelacaoTipo

		.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())))

		|| ((idCliente != null && !idCliente.equals("") && !idCliente

		.trim().equalsIgnoreCase(

		new Integer(

		ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())))){

			// Relatorio de Economias do Imovel

			if(relatorio != null

			&& relatorio.intValue() == ConstantesSistema.GERAR_RELATORIO_DADOS_ECONOMIA_IMOVEL

			.intValue()){

				innerJoin = innerJoin

								+ " left join cliente_imovel_economia on imovel_economia.imec_id = cliente_imovel_economia.imec_id and cliente_imovel_economia.cime_dtrelacaofim is null ";

			}else{

				innerJoin = innerJoin

				+ " left join cliente_imovel on imovel.imov_id = cliente_imovel.imov_id  and cliente_imovel.clim_dtrelacaofim is null ";

			}

		}

		// cliente relacao tipo

		if(idClienteRelacaoTipo != null

		&& !idClienteRelacaoTipo.equals("")

		&& !idClienteRelacaoTipo.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			// Relatorio de Economias do Imovel

			if(relatorio != null

			&& relatorio.intValue() == ConstantesSistema.GERAR_RELATORIO_DADOS_ECONOMIA_IMOVEL

			.intValue()){

				innerJoin = innerJoin

				+ " left join cliente_relacao_tipo on cliente_imovel_economia.crtp_id = cliente_relacao_tipo.crtp_id ";

			}else{

				innerJoin = innerJoin

				+ " left join cliente_relacao_tipo on cliente_imovel.crtp_id = cliente_relacao_tipo.crtp_id ";

			}

		}

		// cliente

		if(idCliente != null

		&& !idCliente.equals("")

		&& !idCliente.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			// Relatorio de Economias do Imovel

			if(relatorio != null

			&& relatorio.intValue() == ConstantesSistema.GERAR_RELATORIO_DADOS_ECONOMIA_IMOVEL

			.intValue()){

				innerJoin = innerJoin

				+ " left join cliente on cliente_imovel_economia.clie_id = cliente.clie_id ";

			}else{

				innerJoin = innerJoin

				+ " left join cliente on cliente_imovel.clie_id = cliente.clie_id ";

			}

		}

		// cliente tipo

		if(idClienteTipo != null

		&& !idClienteTipo.equals("")

		&& !idClienteTipo.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString())){

			if(!(idCliente != null && !idCliente.equals("") && !idCliente

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				// Relatorio de Economias do Imovel

				if(relatorio != null

				&& relatorio.intValue() == ConstantesSistema.GERAR_RELATORIO_DADOS_ECONOMIA_IMOVEL

				.intValue()){

					innerJoin = innerJoin

									+ " left join cliente_imovel_economia on imovel_economia.imec_id = cliente_imovel_economia.imec_id  and cliente_imovel_economia.cime_dtrelacaofim is null ";

					innerJoin = innerJoin

					+ " left join cliente on cliente_imovel_economia.clie_id = cliente.clie_id ";

				}else{

					innerJoin = innerJoin

									+ " left join cliente_imovel on imovel.imov_id = cliente_imovel.imov_id   and cliente_imovel.clim_dtrelacaofim is null ";

					innerJoin = innerJoin

					+ " left join cliente on cliente_imovel.clie_id = cliente.clie_id ";

				}

			}

			innerJoin = innerJoin

			+ " left join cliente_tipo on cliente.cltp_id = cliente.cltp_id ";

		}

		// intervalo Media Minima Imovel Inicial e Final

		if((intervaloMediaMinimaImovelInicial != null

		&& !intervaloMediaMinimaImovelInicial.equals("") && !intervaloMediaMinimaImovelInicial

		.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString()))

		&& (intervaloMediaMinimaImovelFinal != null

		&& !intervaloMediaMinimaImovelFinal.equals("") && !intervaloMediaMinimaImovelFinal

		.trim().equalsIgnoreCase(

		new Integer(

		ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString()))){

			innerJoin = innerJoin

			+ " left join consumo_historico on imovel.imov_id = consumo_historico.imov_id ";

		}

		// intervalo MediaMinima Hidrometro Inicial e Final

		if((intervaloMediaMinimaHidrometroInicial != null

		&& !intervaloMediaMinimaHidrometroInicial.equals("") && !intervaloMediaMinimaHidrometroInicial

		.trim().equalsIgnoreCase(

		new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString()))

		&& (intervaloMediaMinimaHidrometroFinal != null

		&& !intervaloMediaMinimaHidrometroFinal.equals("") && !intervaloMediaMinimaHidrometroFinal

		.trim().equalsIgnoreCase(

		new Integer(

		ConstantesSistema.NUMERO_NAO_INFORMADO)

		.toString()))){

			if(idTipoMedicao != null && !idTipoMedicao.equals("")){

				if(idTipoMedicao.equals(MedicaoTipo.LIGACAO_AGUA.toString())){

					innerJoin = innerJoin

					+ " left join medicao_historico on imovel.imov_id = medicao_historico.lagu_id ";

				}else if(idTipoMedicao.equals(MedicaoTipo.POCO.toString())){

					innerJoin = innerJoin

					+ " left join medicao_historico medicao_historico_poco on imovel.imov_id = medicao_historico_poco.imov_id ";

				}

			}

		}

		return innerJoin;

	}

	/**
	 * Permite pesquisar entidade beneficente [UC0389] Inserir Autorização para
	 * Doação Mensal
	 * 
	 * @author César Araújo
	 * @date 30/08/2006
	 * @param filtroEntidadeBeneficente
	 *            -
	 *            Filtro com os valores para pesquisa
	 * @return Collection<EntidadeBeneficente> - Coleção de entidade(s)
	 *         beneficente(s)
	 * @throws ErroRepositorioException
	 */
	public Collection<EntidadeBeneficente> pesquisarEntidadeBeneficente(FiltroEntidadeBeneficente filtroEntidadeBeneficente)
					throws ErroRepositorioException{

		/** * Declara varáveis locais ** */

		Collection retorno = null;

		Session session = null;

		/** * Obtém a instância da sessão ** */

		session = HibernateUtil.getSession();

		try{

			/**
			 * * executa o método para pesquisa de pesquisa de Entidade
			 * Beneficente
			 */
			retorno = GeradorHQLCondicional.gerarQueryCriteriaExpression(session, filtroEntidadeBeneficente, EntidadeBeneficente.class);

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	/**
	 * Permite pesquisar imóvel doação [UC0389] Inserir Autorização para Doação
	 * Mensal
	 * 
	 * @author César Araújo
	 * @date 30/08/2006
	 * @param filtroImoveldoacao
	 *            -
	 *            Filtro com os valores para pesquisa
	 * @return Collection<ImovelDoacao> - Coleção de imóvei(s) doação
	 * @throws ErroRepositorioException
	 */
	public Collection<ImovelDoacao> pesquisarImovelDoacao(FiltroImovelDoacao filtroImovelDoacao) throws ErroRepositorioException{

		/** * Declara variáveis locais ** */

		Collection<ImovelDoacao> retorno = null;

		Session session = null;

		/** * Obtém a sessão ** */

		session = HibernateUtil.getSession();

		try{

			/** * executa o método para pesquisa de pesquisa de Imovel Doação ** */

			retorno = new ArrayList(

			new CopyOnWriteArraySet<ImovelDoacao>(

			GeradorHQLCondicional

			.gerarCondicionalQuery(

			filtroImovelDoacao,

			"gcom.cadastro.imovel.ImovelDoacao", "imovelDoacao",

			"from gcom.cadastro.imovel.ImovelDoacao as imovelDoacao",

			session).list()));

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	/**
	 * Permite atualizar as informações referentes ao imóvel doação [UC0390]
	 * Manter Autorização para Doação Mensal
	 * 
	 * @author César Araújo
	 * @date 30/08/2006
	 * @param imovelDoacao
	 *            -
	 *            instância de imóvel doação que servirá de base para a
	 *            atualição
	 * @throws ErroRepositorioException
	 */

	public void atualizarImovelDoacao(ImovelDoacao imovelDoacao)

	throws ErroRepositorioException{

		/** * Declara a variável local ** */

		Session session = null;

		/** * Obtém a sessão ** */

		session = HibernateUtil.getSession();

		try{

			/** * Atualiza as informações do imóvel doação ** */

			session.update(imovelDoacao);

			session.flush();

		}catch(HibernateException e){

			e.printStackTrace();

			throw new ErroRepositorioException("Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

	}

	/**
	 * Pesquisa um imóvel a partir do seu id.Retorna os dados que compõem a
	 * inscrição e o endereço do mesmo
	 * 
	 * @author Raphael Rossiter
	 * @date 01/08/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */

	public Collection pesquisarImovelRegistroAtendimento(Integer idImovel)

	throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = null;

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

							" enderecoReferencia.descricaoAbreviada,"

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

							" imovel.numeroImovel,"

							+ // 17

							" imovel.complementoEndereco,"

							+ // 18

							" logradouro.id,"

							+ // 19

							" logradouroCep.id,"

							+ // 20

							" logradouroBairro.id,"

							+ // 21

							" logradouroTipo.descricao,"

							+ // 22

							" logradouroTitulo.descricao,"

							+ // 23

							" enderecoReferencia.descricao, "

							+ // 24

							" localidade.id, "

							+ // 25

							" setorComercial.codigo, "

							+ // 26

							" quadra.numeroQuadra, "

							+ // 27

							" imovel.lote, "

							+ // 28

							" imovel.subLote, "

							+ // 29

							" divisaoEsgoto.id, "

							+ // 30

							" pavimentoRua.id, "

							+ // 31

							" pavimentoCalcada.id, "

							+ // 32

							" localidade.descricao, "

							+ // 33

							" setorComercial.descricao, "

							+ // 34

							" setorComercial.id, "

							+ // 35

							" quadra.id, "

							+ // 36

							" bairro.codigo, "

							+ // 37

							" ligacaoAguaSituacao.id, "

							+ // 38

							" ligacaoAguaSituacao.descricao, "

							+ // 39

							" ligacaoEsgotoSituacao.id, "

							+ // 40

							" ligacaoEsgotoSituacao.descricao, "

							+ // 41

							" enderecoReferencia.id, "

							+ // 42

							" imovelPerfil.id, "

							+ // 43

							" rotaImovel.id, "

							+ // 44

							" imovel.numeroSegmento, "

							// 45

							+ " imovel.coordenadaX, "

							// 46
							+ " imovel.coordenadaY "

							// 47

							+ "from Imovel imovel "

							+ "left join imovel.logradouroCep logradouroCep "

							+ "left join logradouroCep.cep cep "

							+ "left join logradouroCep.logradouro logradouro "

							+ "left join logradouro.logradouroTipo logradouroTipo "

							+ "left join logradouro.logradouroTitulo logradouroTitulo "

							+ "left join imovel.logradouroBairro logradouroBairro "

							+ "left join logradouroBairro.bairro bairro "

							+ "left join bairro.municipio municipio "

							+ "left join municipio.unidadeFederacao unidadeFederacao "

							+ "left join imovel.enderecoReferencia enderecoReferencia "

							+ "left join imovel.localidade localidade "

							+ "left join imovel.setorComercial setorComercial "

							+ "left join imovel.quadra quadra "

							+ "left join quadra.bacia bacia "

							+ "left join bacia.sistemaEsgoto sistemaEsgoto "

							+ "left join sistemaEsgoto.divisaoEsgoto divisaoEsgoto "

							+ "left join imovel.pavimentoRua pavimentoRua "

							+ "left join imovel.pavimentoCalcada pavimentoCalcada "

							+ "left join imovel.ligacaoAguaSituacao ligacaoAguaSituacao "

							+ "left join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao "

							+ "left join imovel.imovelPerfil imovelPerfil "

							+ "left join imovel.rota rotaImovel "

							+ "where imovel.id = :idImovel and imovel.indicadorExclusao != 1";

			retorno = session.createQuery(consulta).setInteger("idImovel",

			idImovel.intValue()).list();

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
	 * Consutlar os Dados Cadastrais do Imovel [UC0472] Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 07/09/2006
	 * @author eduardo henrique
	 * @date 11/11/2008
	 *       Alteração no método para retorno do campo Blob de foto do Imóvel.
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */

	public Collection consultarImovelDadosCadastrais(Integer idImovel) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = "SELECT ligacaoAguaSituacao.descricao, "

			+ // 0

							" ligacaoEsgotoSituacao.descricao, "

							+ // 1

							" imovelPerfil.descricao, "

							+ // 2

							" despejo.descricao, "

							+ // 3

							" imovel.areaConstruida, "

							+ // 4

							" areaConstruidaFaixa.volumeMenorFaixa, "

							+ // 5

							" areaConstruidaFaixa.volumeMaiorFaixa, "

							+ // 6

							" imovel.testadaLote, "

							+ // 7

							" imovel.volumeReservatorioInferior, "

							+ // 8

							" reservatorioVolumeFaixaInferior.volumeMenorFaixa, "

							+ // 9

							" reservatorioVolumeFaixaInferior.volumeMaiorFaixa, "

							+ // 10

							" imovel.volumeReservatorioSuperior, "

							+ // 11

							" reservatorioVolumeFaixaSuperior.volumeMenorFaixa, "

							+ // 12

							" reservatorioVolumeFaixaSuperior.volumeMaiorFaixa, "

							+ // 13

							" imovel.volumePiscina, "

							+ // 14

							" piscinaVolumeFaixa.volumeMenorFaixa, "

							+ // 15

							" piscinaVolumeFaixa.volumeMaiorFaixa, "

							+ // 16

							" fonteAbastecimento.descricao, "

							+ // 17

							" pocoTipo.descricao, "

							+ // 18

							" distritoOperacional.descricao, "

							+ // 19

							" pavimentoRua.descricao, "

							+ // 20

							" pavimentoCalcada.descricao, "

							+ // 21

							" imovel.numeroIptu, "

							+ // 22

							" imovel.numeroCelpe, "

							+ // 23

							" imovel.coordenadaX, "

							+ // 24

							" imovel.coordenadaY, "

							+ // 25

							" cadastroOcorrencia.descricao, "

							+ // 26

							" eloAnormalidade.descricao, "

							+ // 27

							" imovel.indicadorImovelCondominio, "

							+ // 28

							" imovelCondominio.id, "

							+ // 29

							" imovelPrincipal.id, "

							+ // 30

							" imovel.numeroPontosUtilizacao, "

							+ // 31

							" imovel.numeroMorador, "

							+ // 32

							" imovel.indicadorJardim, "

							+ // 33

							" bacia.descricao, "

							+ // 34

							" cobrancaSituacao.descricao, "

							+ // 35

							" divisaoEsgoto.descricao, "

							+ // 36

							" esgotamento.descricao, "

							+ // 37

							" padraoConstrucao.descricao, "

							+ // 38

							" imovel.numeroQuarto, "

							+ // 39

							" imovel.numeroBanheiro, "

							+ // 40

							" rendaFamiliarFaixa.menorFaixa, "

							+ // 41

							" rendaFamiliarFaixa.maiorFaixa, "

							+ // 42

							" imovel.numeroAdulto, "

							+ // 43

							" imovel.numeroCrianca, "

							+ // 44
							" imovel.numeroMoradorTrabalhador, "

							+ // 45
							" imovel.fotoFachada, "

							+ // 46
							" imovel.imovelContaEnvio, "

							+ // 47
							" imovel.indicadorContratoConsumo, "

							+ // 48
							" quadra.numeroQuadra, "

							+ // 49
							" baciaDoImovel.descricao, "

							+ // 50
							" subBaciaDoImovel.descricao, "

							+ // 51
							" sistemaAbastecimento.descricao, "

							+ // 52
							" setorAbastecimento.descricao, "

							+ // 53
							" distritoOperacionalImovel.descricao, "

							+ // 55
							" imovel.complementoEndereco "

							+ "from Imovel imovel "

							+ "left join imovel.imovelPerfil imovelPerfil "

							+ "left join imovel.despejo despejo "

							+ "left join imovel.areaConstruidaFaixa areaConstruidaFaixa "

							+ "left join imovel.reservatorioVolumeFaixaInferior reservatorioVolumeFaixaInferior "

							+ "left join imovel.reservatorioVolumeFaixaSuperior reservatorioVolumeFaixaSuperior "

							+ "left join imovel.piscinaVolumeFaixa piscinaVolumeFaixa "

							+ "left join imovel.fonteAbastecimento fonteAbastecimento "

							+ "left join imovel.quadra quadra "

							+ "left join imovel.distritoOperacional distritoOperacionalImovel "

							+ "left join quadra.distritoOperacional distritoOperacional "

							+ "left join quadra.bacia bacia "

							+ "left join bacia.sistemaEsgoto sistemaEsgoto "

							+ "left join sistemaEsgoto.divisaoEsgoto divisaoEsgoto "

							+ "left join imovel.pavimentoRua pavimentoRua "

							+ "left join imovel.pavimentoCalcada pavimentoCalcada "

							+ "left join imovel.cadastroOcorrencia cadastroOcorrencia "

							+ "left join imovel.eloAnormalidade eloAnormalidade "

							+ "left join imovel.imovelCondominio imovelCondominio "

							+ "left join imovel.imovelPrincipal imovelPrincipal "

							+ "left join imovel.pocoTipo pocoTipo "

							+ "left join imovel.ligacaoAguaSituacao ligacaoAguaSituacao "

							+ "left join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao "

							+ "left join imovel.cobrancaSituacao cobrancaSituacao "

							+ "left join imovel.esgotamento esgotamento "

							+ "left join imovel.padraoConstrucao padraoConstrucao "

							+ "left join imovel.rendaFamiliarFaixa rendaFamiliarFaixa "

							+ "left join imovel.imovelContaEnvio imovelContaEnvio "

							+ "left join imovel.setorAbastecimento setorAbastecimento "

							+ "left join setorAbastecimento.sistemaAbastecimento sistemaAbastecimento "

							+ "left join imovel.subBacia subBaciaDoImovel "

							+ "left join subBaciaDoImovel.bacia baciaDoImovel "

							+ "where imovel.id = :idImovel and imovel.indicadorExclusao != 1";

			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).list();

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
	 * Pesquisa a coleção de clientes do imovel [UC0472] Consultar Imovel
	 * 
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */

	public Collection pesquisarClientesImovel(Integer idImovel)

	throws ErroRepositorioException{

		// cria a coleção de retorno

		Collection retorno = null;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		String consulta = null;

		// pesquisa a coleção de atividades e atribui a variável "retorno"

		try{

			consulta = "SELECT cliente.id,"

			+ // 0

							"cliente.nome,"

							+ // 1

							"clienteRelacaoTipo.descricao,"

							+ // 2

							"clienteImovel.dataInicioRelacao,"

							+ // 3

							"clienteFone.telefone,"

							+ // 4

							"cliente.cnpj,"

							+ // 5

							"cliente.cpf, "

							+ // 6

							"clienteFone.ddd, "

							+ // 7

							"clienteRelacaoTipo.id, "

							+ // 8

							"clienteImovel.id, "

							+ // 9

							"cliente.email, "

							+ // 10

							"clienteTipo.id, "

							+ // 11

							"clienteTipo.indicadorPessoaFisicaJuridica, "

							+ // 12

							"cliente.rg "

							+ // 13

							"from ClienteImovel clienteImovel "

							+

							// " left join clienteImovel.imovel imovel "

							"left join clienteImovel.cliente cliente "

							+ "left join clienteImovel.clienteRelacaoTipo clienteRelacaoTipo "

							+ "left join cliente.clienteFones clienteFone with(clienteFone.indicadorTelefonePadrao = 1) "

							+ "left join cliente.clienteTipo clienteTipo "

							+ "where clienteImovel.imovel.id = :idImovel and clienteImovel.dataFimRelacao is null";

			retorno = session.createQuery(consulta).setInteger("idImovel",

			idImovel.intValue()).list();

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
	 * Pesquisa a coleção de categorias do imovel [UC0472] Consultar Imovel
	 * 
	 * @author Rafael Santos
	 * @since 07/09/2006
	 * @param filtroClienteImovel
	 *            parametros para a consulta
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */

	public Collection pesquisarCategoriasImovel(Integer idImovel)

	throws ErroRepositorioException{

		// cria a coleção de retorno

		Collection retorno = null;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		String consulta = null;

		// pesquisa a coleção de atividades e atribui a variável "retorno"

		try{

			consulta = "select categoria.descricao,"

			+ // 0

							"subcategoria.descricao,"

							+ // 1

							"imovelSubcategoria.quantidadeEconomias, "

							+ // 2

							"categoria "

							+ // 3

							"from ImovelSubcategoria imovelSubcategoria "

							+ "left join imovelSubcategoria.comp_id.subcategoria subcategoria "

							+ "left join subcategoria.categoria categoria "

							+ "where imovelSubcategoria.comp_id.imovel.id = :idImovel ";

			retorno = session.createQuery(consulta).setInteger("idImovel",

			idImovel.intValue()).list();

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
	 * [UC0475] Consultar Perfil Imovel
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param idImovel
	 * @return Perfil do Imóvel
	 * @exception ErroRepositorioException
	 */

	public ImovelPerfil obterImovelPerfil(Integer idImovel)

	throws ErroRepositorioException{

		ImovelPerfil retorno = null;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = "SELECT i.imovelPerfil " + "FROM Imovel i "

			+ "WHERE i.id = :idImovel ";

			retorno = (ImovelPerfil) session.createQuery(consulta).setInteger(

			"idImovel", idImovel).setMaxResults(1).uniqueResult();

			// erro no hibernate

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

		// retorna o perfil do imóvel

		return retorno;

	}

	/**
	 * Consultar os Dados Complementares do Imovel
	 * [UC0473] Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection consultarImovelDadosComplementares(Integer idImovel) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{

			consulta = "SELECT ligacaoAguaSituacao.descricao, "
							+ // 0
							"ligacaoEsgotoSituacao.descricao, "
							+ // 1
							"consumoTarifa.descricao, "
							+ // 2
							"imovel.numeroRetificacao, "
							+ // 3
							"imovel.numeroParcelamento, "
							+ // 4
							"imovel.numeroReparcelamento, "
							+ // 5
							"imovel.numeroReparcelamentoConsecutivos, "
							+ // 6
							"cobrancaSituacao.descricao, "
							+ // 7
							"cadastroOcorrencia.descricao, "
							+ // 8
							"eloAnormalidade.descricao, "
							+ // 9
							"funcionario.id, "
							+ // 10
							"funcionario.nome, "
							+ // 11
							"ligacaoAgua.numeroCorte, "
							+ // 12
							"ligacaoAgua.numeroCorteAdministrativo, "
							+ // 13
							"ligacaoAgua.numeroSupressaoAgua, "
							+ // 14
							"consumoTarifaTemporaria.descricao, "
							+ // 15
							"imovel.dataValidadeTarifaTemporaria "
							+ // 16
							"FROM Imovel imovel " + "left join imovel.consumoTarifa consumoTarifa "
							+ "left join imovel.cobrancaSituacao cobrancaSituacao "
							+ "left join imovel.cadastroOcorrencia cadastroOcorrencia "
							+ "left join imovel.eloAnormalidade eloAnormalidade "
							+ "left join imovel.ligacaoAguaSituacao ligacaoAguaSituacao "
							+ "left join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao " + "left join imovel.funcionario funcionario "
							+ "left join imovel.ligacaoAgua ligacaoAgua "
							+ "left join imovel.consumoTarifaTemporaria consumoTarifaTemporaria "
							+ "where imovel.id = :idImovel and imovel.indicadorExclusao != 1";

			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Pesquisa a coleção de vencimento alternativos do imovel [UC0473]
	 * Consultar Imovel Dados Complementares
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */

	public Collection pesquisarVencimentoAlternativoImovel(Integer idImovel)

	throws ErroRepositorioException{

		// cria a coleção de retorno

		Collection retorno = null;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		String consulta = null;

		// pesquisa a coleção de atividades e atribui a variável "retorno"

		try{

			consulta = "SELECT vencimentoAlternativo.dateVencimento," + // 0

							"vencimentoAlternativo.dataImplantacao," + // 1

							"vencimentoAlternativo.dateExclusao " + // 2

							"from VencimentoAlternativo vencimentoAlternativo "

							+ "where vencimentoAlternativo.imovel.id = :idImovel ";

			retorno = session.createQuery(consulta).setInteger("idImovel",

			idImovel.intValue()).list();

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

	public Collection pesquisarSituacoesCobrancaImovel(Integer idImovel)

	throws ErroRepositorioException{

		// cria a coleção de retorno

		Collection retorno = null;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		String consulta = null;

		// pesquisa a coleção de situaçoes e atribui a variável "retorno"

		try{

			consulta = "select imovelCobSit.id, "
							+ // 0
							"imovelCobSit.cobrancaSituacao.descricao, "
							+ // 1
							"imovelCobSit.anoMesReferenciaInicio , "
							+ // 2
							"imovelCobSit.anoMesReferenciaFinal, "
							+ // 3
							"imovelCobSit.dataImplantacaoCobranca, "
							+ // 4
							"imovelCobSit.dataRetiradaCobranca, "
							+ // 5
							"imovelCobSit.cliente.id, "
							+ // 6
							"imovelCobSit.escritorio.nome, "
							+ // 7
							"imovelCobSit.advogado.nome "
							+ // 8

							"from ImovelCobrancaSituacao imovelCobSit " + "left join imovelCobSit.escritorio "
							+ "left join imovelCobSit.advogado " +

							"where imovelCobSit.imovel.id = :idImovel ";

			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).list();

			// erro no hibernate

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

		// retorna a coleção de situações pesquisada(s)

		return retorno;

	}

	/**
	 * Pesquisa a coleção de Debitos Automaticos do imovel [UC0473] Consultar
	 * Imovel Dados Complementares
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */

	public Collection pesquisarDebitosAutomaticosImovel(Integer idImovel)

	throws ErroRepositorioException{

		// cria a coleção de retorno

		Collection retorno = null;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		String consulta = null;

		// pesquisa a coleção de atividades e atribui a variável "retorno"

		try{

			consulta = "SELECT banco.descricaoAbreviada," + // 0

							"agencia.codigoAgencia," + // 1

							"debitoAutomatico.identificacaoClienteBanco, " + // 2

							"debitoAutomatico.dataOpcaoDebitoContaCorrente, " + // 3

							"debitoAutomatico.dataInclusaoNovoDebitoAutomatico, " + // 4

							"debitoAutomatico.dataExclusao " + // 5

							"from DebitoAutomatico debitoAutomatico "

							+ "left join debitoAutomatico.agencia agencia "

							+ "left join agencia.banco banco "

							+ "where debitoAutomatico.imovel.id = :idImovel ";

			retorno = session.createQuery(consulta).setInteger("idImovel",

			idImovel.intValue()).list();

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
	 * Pesquisa a coleção de Faturamento Situação Historico do Imovel [UC0473]
	 * Consultar Imovel Dados Complementares
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */

	public Collection pesquisarFaturamentosSituacaoHistorico(Integer idImovel)

	throws ErroRepositorioException{

		// cria a coleção de retorno

		Collection retorno = null;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		String consulta = null;

		// pesquisa a coleção de atividades e atribui a variável "retorno"

		try{

			consulta = "SELECT faturamentoSituacaoTipo.id,"

			+ // 0

							"faturamentoSituacaoTipo.descricao,"

							+ // 1

							"faturamentoSituacaoMotivo.id,"

							+ // 2

							"faturamentoSituacaoMotivo.descricao,"

							+ // 3

							"faturamentoSituacaoHistorico.anoMesFaturamentoSituacaoInicio, "

							+ // 4

							"faturamentoSituacaoHistorico.anoMesFaturamentoSituacaoFim, "

							+ // 5

							"faturamentoSituacaoHistorico.anoMesFaturamentoRetirada, "

							+ // 6

							"usario.nomeUsuario "

							+ // 7

							"from FaturamentoSituacaoHistorico faturamentoSituacaoHistorico "

							+ "left join faturamentoSituacaoHistorico.faturamentoSituacaoTipo faturamentoSituacaoTipo "

							+ "left join faturamentoSituacaoHistorico.faturamentoSituacaoMotivo faturamentoSituacaoMotivo "

							+ "left join faturamentoSituacaoHistorico.usuario usario "

							+ "where faturamentoSituacaoHistorico.imovel.id = :idImovel ";

			retorno = session.createQuery(consulta).setInteger("idImovel",

			idImovel.intValue()).list();

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
	 * Pesquisa a coleção de Faturamento Situação Historico do Imovel [UC0473]
	 * Consultar Imovel Dados Complementares
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */

	public Collection pesquisarFaturamentoSituacaoHistorico(Integer idImovel)

	throws ErroRepositorioException{

		// cria a coleção de retorno

		Collection retorno = null;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		String consulta = null;

		// pesquisa a coleção de atividades e atribui a variável "retorno"

		try{

			consulta = "SELECT faturamentoSituacaoHistorico from FaturamentoSituacaoHistorico faturamentoSituacaoHistorico "

			+ "left join faturamentoSituacaoHistorico.faturamentoSituacaoTipo faturamentoSituacaoTipo "

			+ "left join faturamentoSituacaoHistorico.faturamentoSituacaoMotivo faturamentoSituacaoMotivo "

			+ "left join faturamentoSituacaoHistorico.usuario usario "

			+ "where faturamentoSituacaoHistorico.imovel.id = :idImovel "

			+ "and faturamentoSituacaoHistorico.anoMesFaturamentoRetirada = null "

			+ "and faturamentoSituacaoHistorico.faturamentoSituacaoTipo.id = "

			+ FaturamentoSituacaoTipo.PERCENTUAL_DE_ESGOTO;

			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).list();

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
	 * Pesquisa a coleção de Faturamento Situação Historico do Imovel Ativos [UC0156]
	 * 
	 * @author Saulo Lima
	 * @date 22/08/2008
	 * @param Integer
	 *            idFaturamentoSituacaoTipo
	 * @return Collection
	 *         FaruramentoSituacaoHistorico
	 * @exception ErroRepositorioException
	 */
	public Collection pesquisarFaturamentosSituacaoHistoricoAtivos(SituacaoEspecialFaturamentoHelper situacaoEspecialFaturamentoHelper)
					throws ErroRepositorioException{

		// cria a coleção de retorno
		Collection<FaturamentoSituacaoHistorico> retorno = null;
		// obtém a sessão
		Session session = HibernateUtil.getSession();
		String consulta = null;
		boolean divideConsulta = false;

		// pesquisa a coleção de atividades e atribui a variável "retorno"
		try{
			consulta = "SELECT faturamentoSituacaoHistorico FROM FaturamentoSituacaoHistorico faturamentoSituacaoHistorico"
							+ " inner join fetch faturamentoSituacaoHistorico.faturamentoSituacaoTipo"
							+ " inner join fetch faturamentoSituacaoHistorico.faturamentoSituacaoMotivo"
							+ " inner join faturamentoSituacaoHistorico.imovel imovel "
							+ " INNER JOIN imovel.localidade lo "
							+ " INNER JOIN imovel.setorComercial sc "
							+ " INNER JOIN imovel.quadra qu "
							+ " INNER JOIN qu.rota rota "
							+ " where faturamentoSituacaoHistorico.faturamentoSituacaoTipo.id =:idFaturamentoSituacaoTipo AND"
							+ " faturamentoSituacaoHistorico.faturamentoSituacaoMotivo.id =:idFaturamentoSituacaoMotivo AND "
							+ " faturamentoSituacaoHistorico.anoMesFaturamentoSituacaoInicio >=:anoMesFaturamentoSituacaoInicio AND "
							+ " faturamentoSituacaoHistorico.anoMesFaturamentoSituacaoFim <=:anoMesFaturamentoSituacaoFim AND "
							+ " faturamentoSituacaoHistorico.anoMesFaturamentoRetirada is null AND ";

			String idImovel = situacaoEspecialFaturamentoHelper.getIdImovel();
			String idsImoveis = situacaoEspecialFaturamentoHelper.getIdsImoveis();
			String idLocalidadeOrigem = situacaoEspecialFaturamentoHelper.getLocalidadeOrigemID();
			String idLocalidadeDestino = situacaoEspecialFaturamentoHelper.getLocalidadeDestinoID();
			String setorComercialOrigemID = situacaoEspecialFaturamentoHelper.getSetorComercialOrigemID();
			String setorComercialDestinoID = situacaoEspecialFaturamentoHelper.getSetorComercialDestinoID();
			String quadraOrigemID = situacaoEspecialFaturamentoHelper.getQuadraOrigemID();
			String quadraDestinoID = situacaoEspecialFaturamentoHelper.getQuadraDestinoID();
			String loteOrigem = situacaoEspecialFaturamentoHelper.getLoteOrigem();
			String loteDestino = situacaoEspecialFaturamentoHelper.getLoteDestino();
			String subLoteOrigem = situacaoEspecialFaturamentoHelper.getSubloteOrigem();
			String subLoteDestino = situacaoEspecialFaturamentoHelper.getSubloteDestino();
			String codigoRotaInicial = situacaoEspecialFaturamentoHelper.getCodigoRotaInicial();
			String codigoRotaFinal = situacaoEspecialFaturamentoHelper.getCodigoRotaFinal();
			String sequencialRotaInicial = situacaoEspecialFaturamentoHelper.getSequencialRotaInicial();
			String sequencialRotaFinal = situacaoEspecialFaturamentoHelper.getSequencialRotaFinal();
			String nomeBairro = situacaoEspecialFaturamentoHelper.getNomeBairro();
			int idFaturamentoSituacaoTipo = Integer.parseInt(situacaoEspecialFaturamentoHelper.getIdFaturamentoSituacaoTipo());
			int idFaturamentoSituacaoMotivo = Integer.parseInt(situacaoEspecialFaturamentoHelper.getIdFaturamentoSituacaoMotivo());
			int anoMesFaturamentoSituacaoInicio = Util.formatarMesAnoComBarraParaAnoMes(situacaoEspecialFaturamentoHelper
							.getMesAnoReferenciaFaturamentoInicial());
			int anoMesFaturamentoSituacaoFim = Util.formatarMesAnoComBarraParaAnoMes(situacaoEspecialFaturamentoHelper
							.getMesAnoReferenciaFaturamentoFinal());
			
			String volume = situacaoEspecialFaturamentoHelper.getVolume();

			String percentualEsgoto = situacaoEspecialFaturamentoHelper.getPercentualEsgoto();

			if(idImovel != null && !idImovel.equals("")){
				consulta += " imovel.id = " + idImovel + " and";
			}

			if(volume != null && !volume.equals("")){
				consulta += " faturamentoSituacaoHistorico.volume = " + volume + " and";
			}

			if(percentualEsgoto != null && !percentualEsgoto.equals("")){
				consulta += " faturamentoSituacaoHistorico.percentualEsgoto = " + percentualEsgoto + " and";
			}

			if(nomeBairro != null && !nomeBairro.trim().equals("")){
				consulta += " imovel.logradouroBairro.bairro.nome like '%" + nomeBairro + "%' and ";
			}

			if(idLocalidadeOrigem != null && !idLocalidadeOrigem.equalsIgnoreCase("") && idLocalidadeDestino != null
							&& !idLocalidadeDestino.equalsIgnoreCase("")){
				consulta += " lo.id between " + idLocalidadeOrigem + " and " + idLocalidadeDestino + " and";
			}

			if(setorComercialOrigemID != null && !setorComercialOrigemID.equalsIgnoreCase("") && setorComercialDestinoID != null
							&& !setorComercialDestinoID.equalsIgnoreCase("")){
				consulta += " sc.id between " + setorComercialOrigemID + " and " + setorComercialDestinoID + " and";
			}

			if(quadraOrigemID != null && !quadraOrigemID.equalsIgnoreCase("") && quadraDestinoID != null
							&& !quadraDestinoID.equalsIgnoreCase("")){
				consulta += " qu.numeroQuadra between " + quadraOrigemID + " and " + quadraDestinoID + " and";
			}

			if(loteOrigem != null && !loteOrigem.equalsIgnoreCase("") && loteDestino != null && !loteDestino.equalsIgnoreCase("")){
				consulta += " imovel.lote between " + loteOrigem + " and " + loteDestino + " and";
			}

			if(subLoteOrigem != null && !subLoteOrigem.equalsIgnoreCase("") && subLoteDestino != null
							&& !subLoteDestino.equalsIgnoreCase("")){
				consulta += " imovel.subLote between " + subLoteOrigem + " and " + subLoteDestino + " and";
			}

			if((codigoRotaInicial != null && !codigoRotaInicial.equals("")) && (codigoRotaFinal != null && !codigoRotaFinal.equals(""))){
				consulta = consulta + " rota.codigo between " + codigoRotaInicial + " and " + codigoRotaFinal + " and";
			}

			if((sequencialRotaInicial != null && !sequencialRotaInicial.equals(""))
							&& (sequencialRotaFinal != null && !sequencialRotaFinal.equals(""))){
				consulta = consulta + " imovel.numeroSequencialRota between " + sequencialRotaInicial + " and " + sequencialRotaFinal
								+ " and ";
			}

			// Consulta
			if(idsImoveis != null && !idsImoveis.equals("")){
				if(idsImoveis.split(",").length > 100){
					divideConsulta = true;
				}else{
					consulta += " imovel.id in ( " + idsImoveis + ") and";
				}
			}

			retorno = session.createQuery(Util.formatarHQL(consulta, 4)).setInteger("idFaturamentoSituacaoTipo", idFaturamentoSituacaoTipo)
							.setInteger("idFaturamentoSituacaoMotivo", idFaturamentoSituacaoMotivo)
							.setInteger("anoMesFaturamentoSituacaoInicio", anoMesFaturamentoSituacaoInicio)
							.setInteger("anoMesFaturamentoSituacaoFim", anoMesFaturamentoSituacaoFim).list();

			Hibernate.initialize("Imovel");
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
	 * Pesquisa a coleção de cobranças Situação Historico do Imovel [UC0473]
	 * Consultar Imovel Dados Complementares
	 * 
	 * @author Rafael Santos
	 * @date 11/09/2006
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */

	public Collection pesquisarCobrancasSituacaoHistorico(Integer idImovel)

	throws ErroRepositorioException{

		// cria a coleção de retorno

		Collection retorno = null;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		String consulta = null;

		// pesquisa a coleção de atividades e atribui a variável "retorno"

		try{

			consulta = "SELECT cobrancaSituacaoTipo.descricao,"

			+ // 0

							"cobrancaSituacaoMotivo.descricao,"

							+ // 1

							"cobrancaSituacaoHistorico.anoMesCobrancaSituacaoInicio, "

							+ // 2

							"cobrancaSituacaoHistorico.anoMesCobrancaSituacaoFim, "

							+ // 3

							"cobrancaSituacaoHistorico.anoMesCobrancaRetirada, "

							+ // 4

							"usarioInclusao.nomeUsuario, "

							+ // 5

							"cobrancaSituacaoHistorico.dataHoraInclusao, "

							+ // 6

							"usarioExclusao.nomeUsuario, "

							+ // 7

							"cobrancaSituacaoHistorico.dataHoraExclusao, "

							+ // 8

							"cobrancaSituacaoTipo.id,"

							+ // 9

							"cobrancaSituacaoMotivo.id "

							+ // 10

							"from CobrancaSituacaoHistorico cobrancaSituacaoHistorico "

							+ "left join cobrancaSituacaoHistorico.cobrancaSituacaoTipo cobrancaSituacaoTipo "

							+ "left join cobrancaSituacaoHistorico.cobrancaSituacaoMotivo cobrancaSituacaoMotivo "

							+ "left join cobrancaSituacaoHistorico.usuarioLogadoInclusao usarioInclusao "

							+ "left join cobrancaSituacaoHistorico.usuarioLogadoExclusao usarioExclusao "

							+ "where cobrancaSituacaoHistorico.imovel.id = :idImovel ";

			retorno = session.createQuery(consulta).setInteger("idImovel",

			idImovel.intValue()).list();

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
	 * Consutlar os Dados de Analise da Medição e Consumo do Imovel [UC0473]
	 * Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 12/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */

	public Collection consultarImovelAnaliseMedicaoConsumo(Integer idImovel)

	throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = "SELECT ligacaoAguaSituacao.descricao, " + " ligacaoEsgotoSituacao.descricao, "
							+ " imovel.hidrometroInstalacaoHistorico.id, " + " ligacaoAgua.hidrometroInstalacaoHistorico.id, "
							+ " imovel.numeroSegmento " + " from Imovel imovel "
							+ "left join imovel.ligacaoAguaSituacao ligacaoAguaSituacao "
							+ "left join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao " + "left join imovel.ligacaoAgua ligacaoAgua "
							+ "where imovel.id = :idImovel and imovel.indicadorExclusao != 1";

			retorno = session.createQuery(consulta).setInteger("idImovel",

			idImovel.intValue()).list();

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	// ----------Savio

	public void atualizarImovelLeituraAnormalidade(

	Map<Integer, MedicaoHistorico> mapAtualizarLeituraAnormalidadeImovel,

	Date dataAtual) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{
			Collection colecaoIdsImoveis = mapAtualizarLeituraAnormalidadeImovel.keySet();

			Iterator iteIdsImoveis = colecaoIdsImoveis.iterator();

			while(iteIdsImoveis.hasNext()){

				Integer idImovel = (Integer) iteIdsImoveis.next();

				MedicaoHistorico medicaoHistoricoTxt = mapAtualizarLeituraAnormalidadeImovel.get(idImovel);

				String atualizarImovel = null;

				Integer idLeituraAnormalidade = null;

				if(medicaoHistoricoTxt.getLeituraAnormalidadeInformada() != null

				&& !medicaoHistoricoTxt.getLeituraAnormalidadeInformada().equals("")){

					idLeituraAnormalidade = medicaoHistoricoTxt

					.getLeituraAnormalidadeInformada().getId();

					atualizarImovel = "update gcom.cadastro.imovel.Imovel "

					+ "set ltan_id = :idLeituraAnormalidade,imov_tmultimaalteracao = :ultimaAlteracao where imov_id = :idImovel";

					session.createQuery(atualizarImovel).setInteger(

					"idLeituraAnormalidade",

					(Integer) idLeituraAnormalidade).setInteger(

					"idImovel", idImovel).setTimestamp(

					"ultimaAlteracao", dataAtual).executeUpdate();

				}else{

					atualizarImovel = "update gcom.cadastro.imovel.Imovel "

					+ "set ltan_id is null,imov_tmultimaalteracao = :ultimaAlteracao where imov_id = :idImovel";

					session.createQuery(atualizarImovel).setInteger("idImovel",

					idImovel)

					.setTimestamp("ultimaAlteracao", dataAtual)

					.executeUpdate();

				}

			}

		}catch(Exception e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

			// } catch (HibernateException e) {

			// levanta a exceção para a próxima camada

			// throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

	}

	/**
	 * Consutlar os Dados do Historico de Faturamento [UC0473] Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */

	public Collection consultarImovelHistoricoFaturamento(Integer idImovel)

	throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = "SELECT ligacaoAguaSituacao.descricao, "

			+ // 0

							" ligacaoEsgotoSituacao.descricao "

							+ // 1

							"from Imovel imovel "

							+ "left join imovel.ligacaoAguaSituacao ligacaoAguaSituacao "

							+ "left join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao "

							+ "where imovel.id = :idImovel and imovel.indicadorExclusao != 1";

			retorno = session.createQuery(consulta).setInteger("idImovel",

			idImovel.intValue()).list();

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
	 * Consutlar o cliente usuário do Imovel [UC0473] Consultar Imóvel
	 * 
	 * @author Bruno Barros, Ivan Sérgio
	 * @date 27/04/2007, 21/11/2007
	 * @alteracao: Adicionado o ClienteFone
	 * @param imovel
	 * @return Cliente
	 * @throws ErroRepositorioException
	 */

	public Cliente consultarClienteUsuarioImovel(Imovel imovel)

	throws ErroRepositorioException{

		Cliente resultadoConsultar = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append("SELECT cli ")

			.append("from ClienteImovel cliimo ")

			.append("left join cliimo.cliente cli ")

			.append("left join fetch cli.clienteFones cliFones ")

			.append("left join fetch cli.clienteTipo cliTipo ")

			.append("left join fetch cliTipo.esferaPoder esfPoder ")

			.append("where cliimo.imovel.id = :idImovel and cliimo.imovel.indicadorExclusao != 1 and ")

			.append("cliimo.clienteRelacaoTipo.id = :idClienteUsuario and cliimo.dataFimRelacao is null  ");

			resultadoConsultar = (Cliente) session.createQuery(consulta.toString())

			.setInteger("idImovel", imovel.getId()).setShort(

			"idClienteUsuario",

			ClienteRelacaoTipo.USUARIO.shortValue())

			.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

		return resultadoConsultar;

	}

	/**
	 * Consutlar o cliente usuário do Imovel [UC0473] Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */

	public String consultarClienteUsuarioImovel(Integer idImovel)

	throws ErroRepositorioException{

		String retorno = null;

		Object resultadoConsultar = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append("SELECT cliente.nome ")

			.append("from ClienteImovel clienteImovel ")

			.append("left join clienteImovel.cliente cliente ")

			.append("where clienteImovel.imovel.id = :idImovel and clienteImovel.imovel.indicadorExclusao != 1 and ")

			.append("clienteImovel.clienteRelacaoTipo.id = :idClienteUsuario and clienteImovel.dataFimRelacao is null ");

			resultadoConsultar = session.createQuery(consulta.toString()).setInteger(

			"idImovel", idImovel.intValue())

			.setShort("idClienteUsuario",

			ClienteRelacaoTipo.USUARIO.shortValue())

			.uniqueResult();

			if(resultadoConsultar != null){

				retorno = (String) resultadoConsultar;

			}

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
	 * Consutlar as contas do Imovel [UC0473] Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @author eduardo henrique
	 * @date 02/10/2008
	 *       Alteração para opção de Ordenação da Referências das Contas
	 * @author Saulo Lima
	 * @date 10/08/2009
	 *       Novo atributo de retorno: valorImposto
	 * @param idImovel
	 * @param ordemAscendente
	 *            true -> ASC ; false -> DESC
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection consultarContasImovel(Integer idImovel, boolean ordemAscendente) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection retorno;
		String consulta;

		try{

			consulta = "SELECT conta.id, " + "conta.referencia, " + "conta.dataVencimentoConta, " + "conta.valorAgua, "
							+ "conta.valorEsgoto, " + "conta.debitos, " + "conta.valorCreditos, "
							+ "debitoCreditoSituacaoAtual.descricaoAbreviada, " + "contaMotivoRevisao.id, " + "conta.valorImposto, "
							+ "debitoCreditoSituacaoAtual.id "
							+ "FROM Conta conta " + "LEFT JOIN conta.debitoCreditoSituacaoAtual debitoCreditoSituacaoAtual "
							+ "LEFT JOIN conta.contaMotivoRevisao contaMotivoRevisao " + "WHERE conta.imovel.id = :idImovel ";

			if(ordemAscendente){
				consulta += "ORDER BY conta.referencia";
			}else{
				consulta += "ORDER BY conta.referencia DESC";
			}

			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).list();

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
	 * Consutlar as contas Historicos do Imovel [UC0473] Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 13/09/2006
	 * @author Saulo Lima
	 * @date 10/08/2009
	 *       Novo atributo de retorno: valorImposto
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection consultarContasHistoricosImovel(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection retorno;
		String consulta;

		try{

			consulta = "SELECT contaHistorico.id, " + "contaHistorico.anoMesReferenciaConta, " + "contaHistorico.dataVencimentoConta, "
							+ "contaHistorico.valorAgua, " + "contaHistorico.valorEsgoto, " + "contaHistorico.valorDebitos, "
							+ "contaHistorico.valorCreditos, " + "debitoCreditoSituacaoAtual.descricaoAbreviada, "
							+ "contaMotivoRevisao.id, " + "contaHistorico.valorImposto, " + "debitoCreditoSituacaoAtual.id "
							+ "FROM ContaHistorico contaHistorico "
							+ "LEFT JOIN contaHistorico.debitoCreditoSituacaoAtual debitoCreditoSituacaoAtual "
							+ "LEFT JOIN contaHistorico.contaMotivoRevisao contaMotivoRevisao "
							+ "WHERE contaHistorico.imovel.id = :idImovel " + " ORDER BY contaHistorico.anoMesReferenciaConta DESC ";

			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).list();

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
	 * Consultar os dados de parcelamentos do Imovel [UC0473] Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 20/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */

	public Collection consultarParcelamentosDebitosImovel(Integer idImovel)

	throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = "SELECT ligacaoAguaSituacao.descricao, "

			+ // 0

							" ligacaoEsgotoSituacao.descricao, " // 1

							+ " imovel.numeroParcelamento, " // 2

							+ " imovel.numeroReparcelamento, " // 3

							+ " imovel.numeroReparcelamentoConsecutivos " // 4

							+ //

							"from Imovel imovel "

							+ "left join imovel.ligacaoAguaSituacao ligacaoAguaSituacao "

							+ "left join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao "

							+ "where imovel.id = :idImovel and imovel.indicadorExclusao != 1";

			retorno = session.createQuery(consulta).setInteger("idImovel",

			idImovel.intValue()).list();

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
	 * 
	 * @author Raphael Rossiter
	 * @date 27/09/2006
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */

	public Cliente pesquisarClienteUsuarioImovel(Integer idImovel) throws ErroRepositorioException{

		Cliente retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuilder consulta = new StringBuilder();

		try{

			consulta.append("select cli ");
			consulta.append("from Cliente cli ");
			consulta.append("inner join fetch cli.clienteTipo cliTip ");
			consulta.append("where exists (select 1 from ClienteImovel cliImov where ");
			consulta.append("cliImov.imovel.id = :idImovel ");
			consulta.append("and cliImov.cliente.id = cli.id ");
			consulta.append("and cliImov.clienteRelacaoTipo.id = :tipoRelacao and cliImov.dataFimRelacao is null) ");

			retorno = (Cliente) session.createQuery(consulta.toString()).setInteger("idImovel", idImovel)
							.setInteger("tipoRelacao", ClienteRelacaoTipo.USUARIO).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0146] Manter Conta
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 11/08/2011
	 * @param idImovel
	 * @return Collection
	 * @throws ControladorException
	 */
	public Cliente pesquisarClienteResponsavelImovel(Integer idImovel) throws ErroRepositorioException{

		Cliente retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuilder consulta = new StringBuilder();

		try{

			consulta.append("select cli ");
			consulta.append("from Cliente cli ");
			consulta.append("inner join fetch cli.clienteTipo cliTip ");
			consulta.append("where exists (select 1 from ClienteImovel cliImov where ");
			consulta.append("cliImov.imovel.id = :idImovel ");
			consulta.append("and cliImov.cliente.id = cli.id ");
			consulta.append("and cliImov.clienteRelacaoTipo.id = :tipoRelacao and cliImov.dataFimRelacao is null) ");

			retorno = (Cliente) session.createQuery(consulta.toString()).setInteger("idImovel", idImovel)
							.setInteger("tipoRelacao", ClienteRelacaoTipo.RESPONSAVEL).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Atualiza apenas os dados (numeroParcelamento,
	 * numeroParcelamentoConsecutivo, numeroReparcelamentoConsecutivo) do imóvel
	 * 
	 * @param imovel
	 *            parametros para a consulta
	 *            Author: Fernanda Paiva
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */

	public void atualizarDadosImovel(Integer codigoImovel,

	Integer numeroParcelamento,

	Integer numeroReparcelamentoConsecutivo,

	Integer numeroReparcelamento) throws ErroRepositorioException{

		// Query

		String update;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			update = "update gcom.cadastro.imovel.Imovel set "

			+ "imov_nnparcelamento = :numeroParcelamento, imov_nnreparcelamento = :numeroReparcelamento, "

			+ "imov_nnreparcelamentoconsecuti = :numeroReparcelamentoConsecutivo "

			+ "where imov_id = :codigoImovel";

			session.createQuery(update).setInteger("codigoImovel",

			codigoImovel.intValue()).setInteger("numeroParcelamento",

			numeroParcelamento.intValue()).setInteger(

			"numeroReparcelamentoConsecutivo",

			numeroReparcelamentoConsecutivo.intValue()).setInteger(

			"numeroReparcelamento", numeroReparcelamento.intValue())

			.executeUpdate();

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
	 * Permite Pesquisar as categorias do Imóvel [UC0394] Gerar Débitos a Cobrar
	 * de Doações
	 * 
	 * @author César Araújo
	 * @date 10/09/2006
	 * @param Imovel
	 *            imovel - objeto imovel
	 * @return Collection<Categoria> - Coleção de categorias
	 * @throws ErroRepositorioException
	 */

	public Collection<Categoria> pesquisarCategoriasImovel(Imovel imovel)

	throws ErroRepositorioException{

		// cria a coleção de retorno

		Collection retorno = null;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		String consulta = null;

		// pesquisa a coleção de categorias e atribui a variável "retorno"

		try{

			consulta = "select "

			+ "  categoria "

			+ "from "

			+ "  ImovelSubcategoria imovelSubcategoria "

			+ "    left join imovelSubcategoria.comp_id.subcategoria subcategoria "

			+ "    left join subcategoria.categoria categoria "

			+ "where "

			+ "  imovelSubcategoria.comp_id.imovel.id = :idImovel ";

			retorno = session.createQuery(consulta).setInteger("idImovel",

			imovel.getId().intValue()).list();

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
	 * [UC0488] - Informar Retorno Ordem de Fiscalização [SB0002] Atualizar
	 * Imóvel/Ligação de Água
	 * 
	 * @date 14/11/2006
	 * @author Sávio Luiz
	 * @param imovel
	 * @throws ErroRepositorioException
	 */

	public void atualizarImovelLigacaoAguaEsgoto(Integer idImovel,

	Integer idLigacaoAguaSituacao, Integer idLigacaoEsgotoSituacao)

	throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		String update;

		try{

			update = "update gcom.cadastro.imovel.Imovel set "

			+ "imov_tmultimaalteracao = :datahoracorrente";

			if(idLigacaoEsgotoSituacao != null

			&& !idLigacaoEsgotoSituacao.equals("")){

				update = update + ",lest_id = " + idLigacaoEsgotoSituacao;

			}

			if(idLigacaoAguaSituacao != null

			&& !idLigacaoAguaSituacao.equals("")){

				update = update + ",last_id = " + idLigacaoAguaSituacao;

			}

			update = update + " where imov_id = :imovelId ";

			session.createQuery(update).setInteger("imovelId", idImovel)

			.setTimestamp("datahoracorrente", new Date())

			.executeUpdate();

		}catch(HibernateException e){

			e.printStackTrace();

			throw new ErroRepositorioException("Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

	}

	/**
	 * [UC0488] - Informar Retorno Ordem de Fiscalização [SB0002] Atualizar
	 * Imóvel/Ligação de Água
	 * 
	 * @date 14/11/2006
	 * @author Sávio Luiz
	 * @param imovel
	 * @throws ErroRepositorioException
	 */

	public void atualizarLigacaoAgua(Integer idLigacaoAgua,

	Integer consumoMinimo, short indiacadorConsumoFixado)

	throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		String update;

		try{

			update = "update gcom.atendimentopublico.ligacaoagua.LigacaoAgua set "

			+ "lagu_tmultimaalteracao = :datahoracorrente,lagu_dtligacaoagua = :dataAtual ";

			if(indiacadorConsumoFixado == FiscalizacaoSituacaoAgua.INDICADOR_SIM){

				if(consumoMinimo != null && !consumoMinimo.equals("")){

					update = update + "lagu_nnconsumominimoagua ="

					+ consumoMinimo;

				}

			}

			update = update + "where lagu_id = :idLigacaoAgua";

			session.createQuery(update).setInteger("idLigacaoAgua",

			idLigacaoAgua).setTimestamp("datahoracorrente", new Date())

			.setDate("dataAtual", new Date()).executeUpdate();

		}catch(HibernateException e){

			e.printStackTrace();

			throw new ErroRepositorioException("Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

	}

	/**
	 * [UC0488] - Informar Retorno Ordem de Fiscalização [SB0002] Atualizar
	 * Imóvel/Ligação de Água
	 * 
	 * @date 14/11/2006
	 * @author Sávio Luiz
	 * @param imovel
	 * @throws ErroRepositorioException
	 */

	public void atualizarLigacaoEsgoto(Integer idLigacaoEsgoto,

	Integer consumoMinimo, short indicadorVolumeFixado)

	throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		String update;

		try{

			update = "gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgoto set "

			+ "lesg_tmultimaalteracao = :datahoracorrente,lesg_dtligacao = :dataAtual ";

			if(indicadorVolumeFixado == FiscalizacaoSituacaoEsgoto.INDICADOR_SIM){

				if(consumoMinimo != null && !consumoMinimo.equals("")){

					update = update + "lesg_nnconsumominimoesgoto ="

					+ consumoMinimo;

				}

			}

			update = update + "where lesg_id = :idLigacaoEsgoto";

			session.createQuery(update).setInteger("idLigacaoEsgoto",

			idLigacaoEsgoto).setTimestamp("datahoracorrente",

			new Date()).setDate("dataAtual", new Date())

			.executeUpdate();

		}catch(HibernateException e){

			e.printStackTrace();

			throw new ErroRepositorioException("Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

	}

	/**
	 * [UC0488] - Informar Retorno Ordem de Fiscalização
	 * 
	 * @date 20/11/2006
	 * @author Sávio Luiz
	 * @param imovel
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarConsumoTarifa(Integer idImovel)

	throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta;

		try{

			consulta = "select consumoTarifa.id "

			+ "from gcom.cadastro.imovel.Imovel imovel "

			+ "left join imovel.consumoTarifa consumoTarifa "

			+ "where imovel.id = :imovelId ";

			retorno = (Integer) session.createQuery(consulta).setInteger(

			"imovelId", idImovel).setMaxResults(1).uniqueResult();

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
	 * Filtrar o Imovel pelos parametros informados
	 * 
	 * @author Rafael Santos
	 * @date 24/11/2006
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarImovel(String idImovel, String idLocalidade,

	String codigoSetorComercial, String numeroQuadra, String idHidrometroHistInst, String lote,

	String subLote, String codigoCliente, String idMunicipio,

	String cep, String idBairro, String idLogradouro,

	boolean pesquisarImovelManterVinculo,

	boolean pesquisarImovelCondominio, Integer numeroPagina)

	throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = "select distinct logradouro.nome," + // 0

							" logradouroTipo.descricaoAbreviada," + // 1

							" logradouroTitulo.descricaoAbreviada," + // 2

							" bairro.id," + // 3

							" bairro.nome," + // 4

							" municipio.id," + // 5

							" municipio.nome," + // 6

							" unidadeFederacao.id," + // 7

							" unidadeFederacao.sigla," + // 8

							" enderecoReferencia.descricaoAbreviada," + // 9

							" cep.cepId," + // 10

							" cep.logradouro," + // 11

							" cep.descricaoTipoLogradouro," + // 12

							" cep.bairro," + // 13

							" cep.municipio," + // 14

							" cep.sigla, " + // 15

							" cep.codigo, " + // 16

							" imovel.numeroImovel," + // 17

							" imovel.complementoEndereco," + // 18

							" logradouro.id," + // 19

							" logradouroCep.id," + // 20

							" logradouroBairro.id," + // 21

							" logradouroTipo.descricao," + // 22

							" logradouroTitulo.descricao," + // 23

							" enderecoReferencia.descricao, " + // 24

							" imovel.id, "; // 25

			if(pesquisarImovelManterVinculo){

				consulta = consulta + " cliente.nome, "; // 26

			}

			consulta = consulta + " imovel.lote, " + // 27

							" imovel.subLote, " + // 28

							" localidade.id, " + // 29

							" setorComercial.codigo, " + // 30

							" quadra.numeroQuadra, " + // 31

							" imovel.ultimaAlteracao, " + // 32

							" imovel.hidrometroInstalacaoHistorico, " + // 33

							" rotaImovel.id, " + // 34

							" imovel.numeroSegmento " + // 35
							"from ClienteImovel clienteImovel "

							/*
							 * ## JOIN ##
							 */

							// join necessários
							+ "left join clienteImovel.imovel imovel "

							+ " inner join imovel.quadra quadra "

							+ " left join imovel.hidrometroInstalacaoHistorico hidrometroInstalacaoHistorico "

							+ " inner join imovel.localidade localidade "

							+ " inner join imovel.setorComercial setorComercial "

							+ "left join imovel.logradouroCep logradouroCep "

							+ "left join logradouroCep.cep cep "

							+ "left join logradouroCep.logradouro logradouro "

							+ "left join logradouro.logradouroTipo logradouroTipo "

							+ "left join logradouro.logradouroTitulo logradouroTitulo "

							+ "left join imovel.logradouroBairro logradouroBairro "

							+ "left join logradouroBairro.bairro bairro "

							+ "left join bairro.municipio municipio "

							+ "left join municipio.unidadeFederacao unidadeFederacao "

							+ "left join imovel.enderecoReferencia enderecoReferencia "

							+ "left join imovel.rota rotaImovel ";

			// join facultativos

			// localidade

			if((idLocalidade != null && !idLocalidade.equals("") && !idLocalidade

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				// consulta = consulta + " inner join imovel.localidade

				// localidade ";

			}

			// setor comercial

			if((codigoSetorComercial != null

			&& !codigoSetorComercial.equals("") && !codigoSetorComercial

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				// consulta = consulta + " inner join imovel.setorComercial

				// setorComercial ";

			}

			// quadra

			if((numeroQuadra != null && !numeroQuadra.equals("") && !numeroQuadra

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				// consulta = consulta + " inner join imovel.quadra quadra ";

			}

			// hidrometro

			if((idHidrometroHistInst != null && !idHidrometroHistInst.equals("") && !idHidrometroHistInst

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				// consulta = consulta + " inner join imovel.quadra quadra ";

			}

			// cliente

			if(pesquisarImovelManterVinculo){

				consulta = consulta

				+ " left outer join imovel.clienteImoveis clienteImoveisUsuario with "

				+ "(clienteImoveisUsuario.clienteRelacaoTipo.id = "

				+ ClienteRelacaoTipo.USUARIO.toString()

				+ ") and clienteImoveisUsuario.dataFimRelacao is null "

				+ " left outer join clienteImoveisUsuario.cliente cliente ";

			}else{

				consulta = consulta

				+ " left join clienteImovel.cliente cliente ";

				// + " left join clienteImoveis.clienteRelacaoTipo

				// clienteRelacaoTipo "

				// + " left join clienteImoveis.cliente cliente ";

			}

			/*
			 * ## CONDIÇÕES ##
			 */

			consulta = consulta + " where imovel.indicadorExclusao != "

			+ Imovel.IMOVEL_EXCLUIDO + " and  ";

			// pesquisar imovel condominio

			if(pesquisarImovelCondominio){

				consulta = consulta + " imovel.indicadorImovelCondominio = "

				+ Imovel.IMOVEL_CONDOMINIO + "  and  ";

			}

			// imovel

			if(idImovel != null

			&& !idImovel.equals("")

			&& !idImovel.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " imovel.id = :idImovel  and  ";

			}

			// localidade

			if((idLocalidade != null && !idLocalidade.equals("") && !idLocalidade

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta + " localidade.id = :idLocalidade  and  ";

			}

			// setor comercial

			if((codigoSetorComercial != null

			&& !codigoSetorComercial.equals("") && !codigoSetorComercial

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta

				+ " setorComercial.codigo = :codigoSetorComercial  and  ";

			}

			// quadra

			if((numeroQuadra != null && !numeroQuadra.equals("") && !numeroQuadra

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta

				+ " quadra.numeroQuadra = :numeroQuadra and  ";

			}

			// hidrometro

			if((idHidrometroHistInst != null && !idHidrometroHistInst.equals("") && !idHidrometroHistInst

			.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){

				consulta = consulta

				+ " (imovel.hidrometroInstalacaoHistorico = :idHidrometroHistInst "
								+ " or imovel.id in (select hidrHist.ligacaoAgua.id from HidrometroInstalacaoHistorico hidrHist "
								+ " inner join hidrHist.ligacaoAgua ligAgua " + " where hidrHist.id = :idHidrometroHistInst " + " )) and  "
								+ " imovel.hidrometroInstalacaoHistorico.dataRetirada is null and ";

			}

			// lote

			if((lote != null && !lote.equals("") && !lote.trim()

			.equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta + " imovel.lote = :lote  and  ";

			}

			// sublote

			if((subLote != null && !subLote.equals("") && !subLote.trim()

			.equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta + " imovel.subLote = :subLote  and  ";

			}

			// cliente

			if(codigoCliente != null

			&& !codigoCliente.equals("")

			&& !codigoCliente.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " cliente.id = :codigoCliente  and  ";

			}

			// municipio

			if(idMunicipio != null

			&& !idMunicipio.equals("")

			&& !idMunicipio.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " municipio.id = :idMunicipio  and  ";

			}

			// cep

			if(cep != null

			&& !cep.equals("")

			&& !cep.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " cep.codigo = :cep  and  ";

			}

			// bairro

			if(idBairro != null

			&& !idBairro.equals("")

			&& !idBairro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " bairro.codigo = :idBairro  and  ";

			}

			// logradouro

			if(idLogradouro != null

			&& !idLogradouro.equals("")

			&& !idLogradouro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " logradouro.id = :idLogradouro  and  ";

			}

			Query query = session.createQuery(consulta.substring(0, (consulta

			.length() - 5)));

			// seta os valores na condição where

			// imovel principal

			if(idImovel != null

			&& !idImovel.equals("")

			&& !idImovel.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("idImovel", new Integer(idImovel).intValue());

			}

			// localidade

			if((idLocalidade != null && !idLocalidade.equals("") && !idLocalidade

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				query.setInteger("idLocalidade", new Integer(idLocalidade)

				.intValue());

			}

			// setor

			if((codigoSetorComercial != null

			&& !codigoSetorComercial.equals("") && !codigoSetorComercial

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				query.setInteger("codigoSetorComercial", new Integer(

				codigoSetorComercial).intValue());

			}

			// quadra

			if((numeroQuadra != null && !numeroQuadra.equals("") && !numeroQuadra

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				query.setInteger("numeroQuadra", new Integer(numeroQuadra)

				.intValue());

			}

			// hidrometro

			if((idHidrometroHistInst != null && !idHidrometroHistInst.equals("") && !idHidrometroHistInst

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				query.setInteger("idHidrometroHistInst", new Integer(idHidrometroHistInst)

				.intValue());

			}

			// lote

			if((lote != null && !lote.equals("") && !lote.trim()

			.equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				query.setInteger("lote", new Integer(lote).intValue());

			}

			// subLote

			if((subLote != null && !subLote.equals("") && !subLote.trim()

			.equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				query.setInteger("subLote", new Integer(subLote).intValue());

			}

			// cliente

			if(codigoCliente != null

			&& !codigoCliente.equals("")

			&& !codigoCliente.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("codigoCliente", new Integer(codigoCliente)

				.intValue());

			}

			// municipio

			if(idMunicipio != null

			&& !idMunicipio.equals("")

			&& !idMunicipio.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("idMunicipio", new Integer(idMunicipio)

				.intValue());

			}

			// cep

			if(cep != null

			&& !cep.equals("")

			&& !cep.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("cep", new Integer(cep).intValue());

			}

			// bairro

			if(idBairro != null

			&& !idBairro.equals("")

			&& !idBairro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("idBairro", new Integer(idBairro).intValue());

			}

			// logradouro

			if(idLogradouro != null

			&& !idLogradouro.equals("")

			&& !idLogradouro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("idLogradouro", new Integer(idLogradouro)

				.intValue());

			}

			retorno = query.setFirstResult(10 * numeroPagina).setMaxResults(10)

			.list();

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
	 * Filtrar o Imovel pelos parametros informados, para saber a quantidade de
	 * imoveis
	 * 
	 * @author Rafael Santos
	 * @date 24/11/2006
	 * @author eduardo henrique
	 * @date 16/12/2008
	 *       Correção no filtro do Nr. do hidrômetro
	 * @author eduardo henrique
	 * @date 30/01/2009
	 *       Inclusao do Filtro de Numero do Imovel.
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Object pesquisarQuantidadeImovel(String idImovel,

	String idLocalidade, String codigoSetorComercial,

	String numeroQuadra, String idHidrometroHistInst, String lote, String subLote,

	String codigoCliente, String idMunicipio, String cep,

	String idBairro, String idLogradouro,

	boolean pesquisarImovelManterVinculo,

	boolean pesquisarImovelCondominio, String numeroHidrometroImovel, String numeroImovel) throws ErroRepositorioException{

		Object retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = "select count(distinct imovel.id) "

			+ "from ClienteImovel clienteImovel ";

			/*
			 * ## JOIN ##
			 */

			consulta = consulta + " left join clienteImovel.imovel imovel ";

			// join facultativos

			// join necessários

			// cep

			if(cep != null

			&& !cep.equals("")

			&& !cep.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta

				+ " left join imovel.logradouroCep logradouroCep "

				+ "left join logradouroCep.cep cep ";

			}

			// bairro

			if(idBairro != null

			&& !idBairro.equals("")

			&& !idBairro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta

				+ " left join imovel.logradouroBairro logradouroBairro "

				+ " left join logradouroBairro.bairro bairro ";

			}

			// logradouro

			if(idLogradouro != null

			&& !idLogradouro.equals("")

			&& !idLogradouro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta

				+ " left join imovel.logradouroCep logradouroCep "

				+ " left join logradouroCep.logradouro logradouro ";

			}

			// municipio

			if(idMunicipio != null

			&& !idMunicipio.equals("")

			&& !idMunicipio.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta

				+ " left join imovel.logradouroBairro logradouroBairro "

				+ " left join logradouroBairro.bairro bairro "

				+ "left join bairro.municipio municipio ";

			}

			// localidade

			if((idLocalidade != null && !idLocalidade.equals("") && !idLocalidade

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta

				+ " inner join imovel.localidade localidade ";

			}

			// setor comercial

			if((codigoSetorComercial != null

			&& !codigoSetorComercial.equals("") && !codigoSetorComercial

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta

				+ " inner join imovel.setorComercial setorComercial ";

			}

			// quadra

			if((numeroQuadra != null && !numeroQuadra.equals("") && !numeroQuadra

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta + " inner join imovel.quadra quadra ";

			}

			// hidrometro

			if((idHidrometroHistInst != null && !idHidrometroHistInst.equals("") && !idHidrometroHistInst

			.trim().equalsIgnoreCase(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){

				consulta = consulta + " left join imovel.hidrometroInstalacaoHistorico hidrometroInstalacaoHistorico ";

			}

			if((numeroHidrometroImovel != null && !numeroHidrometroImovel.equalsIgnoreCase(""))){
				consulta = consulta + " left join imovel.ligacaoAgua.hidrometroInstalacaoHistorico hidromInstHistoricoAgua ";
			}

			// cliente

			if(codigoCliente != null

			&& !codigoCliente.equals("")

			&& !codigoCliente.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				if(pesquisarImovelManterVinculo){

					consulta = consulta

					+ " left outer join imovel.clienteImoveis clienteImoveisUsuario with "

					+ "(clienteImoveisUsuario.clienteRelacaoTipo.id = "

					+ ClienteRelacaoTipo.USUARIO.toString()

					+ ") and clienteImoveisUsuario.dataFimRelacao is null "

					+ " left outer join clienteImoveisUsuario.cliente cliente ";

				}else{

					consulta = consulta

					+ " left join clienteImovel.cliente cliente ";

					// + " left join clienteImoveis.clienteRelacaoTipo

					// clienteRelacaoTipo "

					// + " left join clienteImoveis.cliente cliente ";

				}

			}

			/*
			 * ## CONDIÇÕES ##
			 */

			consulta = consulta

			+ " where clienteImovel.dataFimRelacao is null and imovel.indicadorExclusao != "

			+ Imovel.IMOVEL_EXCLUIDO + " and  ";

			// pesquisar imovel condominio

			if(pesquisarImovelCondominio){

				consulta = consulta + " imovel.indicadorImovelCondominio = "

				+ Imovel.IMOVEL_CONDOMINIO + "  and  ";

			}

			// imovel

			if(idImovel != null

			&& !idImovel.equals("")

			&& !idImovel.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " imovel.id = :idImovel  and  ";

			}

			// localidade

			if((idLocalidade != null && !idLocalidade.equals("") && !idLocalidade

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta + " localidade.id = :idLocalidade  and  ";

			}

			// setor comercial

			if((codigoSetorComercial != null

			&& !codigoSetorComercial.equals("") && !codigoSetorComercial

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta

				+ " setorComercial.codigo = :codigoSetorComercial  and  ";

			}

			// quadra

			if((numeroQuadra != null && !numeroQuadra.equals("") && !numeroQuadra

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta

				+ " quadra.numeroQuadra = :numeroQuadra and  ";

			}

			// hidrometro instalação historico

			if((idHidrometroHistInst != null && !idHidrometroHistInst.equals("") && !idHidrometroHistInst

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta

				+ " hidrometroInstalacaoHistorico.id = :idHidrometroHistInst and  ";

			}

			// numero do hidrômetro
			if(numeroHidrometroImovel != null && !numeroHidrometroImovel.equalsIgnoreCase("")){
				consulta = consulta + " hidromInstHistoricoAgua.hidrometro.numero = :numeroHidrometro and  ";
			}

			if(numeroImovel != null && !numeroImovel.equalsIgnoreCase("")){
				consulta = consulta + " imovel.numeroImovel = :numeroImovel and ";
			}

			// lote

			if((lote != null && !lote.equals("") && !lote.trim()

			.equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta + " imovel.lote = :lote  and  ";

			}

			// sublote

			if((subLote != null && !subLote.equals("") && !subLote.trim()

			.equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta + " imovel.subLote = :subLote  and  ";

			}

			// cliente

			if(codigoCliente != null

			&& !codigoCliente.equals("")

			&& !codigoCliente.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " cliente.id = :codigoCliente  and  ";

			}

			// municipio

			if(idMunicipio != null

			&& !idMunicipio.equals("")

			&& !idMunicipio.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " municipio.id = :idMunicipio  and  ";

			}

			// cep

			if(cep != null

			&& !cep.equals("")

			&& !cep.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " cep.codigo = :cep  and  ";

			}

			// bairro

			if(idBairro != null

			&& !idBairro.equals("")

			&& !idBairro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " bairro.codigo = :idBairro  and  ";

			}

			// logradouro

			if(idLogradouro != null

			&& !idLogradouro.equals("")

			&& !idLogradouro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " logradouro.id = :idLogradouro  and  ";

			}

			Query query = session.createQuery(consulta.substring(0, (consulta

			.length() - 5)));

			// seta os valores na condição where

			// imovel principal

			if(idImovel != null

			&& !idImovel.equals("")

			&& !idImovel.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("idImovel", new Integer(idImovel).intValue());

			}

			// localidade

			if((idLocalidade != null && !idLocalidade.equals("") && !idLocalidade

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				query.setInteger("idLocalidade", new Integer(idLocalidade)

				.intValue());

			}

			// setor

			if((codigoSetorComercial != null

			&& !codigoSetorComercial.equals("") && !codigoSetorComercial

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				query.setInteger("codigoSetorComercial", new Integer(

				codigoSetorComercial).intValue());

			}

			// quadra

			if((numeroQuadra != null && !numeroQuadra.equals("") && !numeroQuadra

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				query.setInteger("numeroQuadra", new Integer(numeroQuadra)

				.intValue());

			}

			// hidrometro instalação histórico

			if((idHidrometroHistInst != null && !idHidrometroHistInst.equals("") && !idHidrometroHistInst

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO).toString()))){

				query.setString("idHidrometroHistInst", idHidrometroHistInst);

			}

			// numero do hidrometro
			if(numeroHidrometroImovel != null && !numeroHidrometroImovel.equals("")){
				query.setString("numeroHidrometro", numeroHidrometroImovel);
			}
			// numero do imovel
			if(numeroImovel != null && !numeroImovel.equalsIgnoreCase("")){
				query.setString("numeroImovel", numeroImovel);
			}

			// lote

			if((lote != null && !lote.equals("") && !lote.trim()

			.equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				query.setInteger("lote", new Integer(lote).intValue());

			}

			// subLote

			if((subLote != null && !subLote.equals("") && !subLote.trim()

			.equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				query.setInteger("subLote", new Integer(subLote).intValue());

			}

			// cliente

			if(codigoCliente != null

			&& !codigoCliente.equals("")

			&& !codigoCliente.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("codigoCliente", new Integer(codigoCliente)

				.intValue());

			}

			// municipio

			if(idMunicipio != null

			&& !idMunicipio.equals("")

			&& !idMunicipio.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("idMunicipio", new Integer(idMunicipio)

				.intValue());

			}

			// cep

			if(cep != null

			&& !cep.equals("")

			&& !cep.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("cep", new Integer(cep).intValue());

			}

			// bairro

			if(idBairro != null

			&& !idBairro.equals("")

			&& !idBairro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("idBairro", new Integer(idBairro).intValue());

			}

			// logradouro

			if(idLogradouro != null

			&& !idLogradouro.equals("")

			&& !idLogradouro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("idLogradouro", new Integer(idLogradouro)

				.intValue());

			}

			retorno = ((Number) query.uniqueResult()).intValue();

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
	 * Usado pelo Pesquisar Imovel Retorno o Imovel, com o Nome do Cliente,
	 * Matricula e Endereço
	 * 
	 * @author Rafael Santos
	 * @date 27/11/2006
	 * @author eduardo henrique
	 * @date 16/12/2008
	 *       Correção da consulta pelo Num. do Hidrômetro, para ser realizada pelo Hidrômetro da
	 *       Lig. Água e não de esgoto.
	 * @author eduardo henrique
	 * @date 30/01/2009
	 *       Correção no método para desativação definitiva da Classe filtro e
	 *       inclusao do numero do imovel na consulta.
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarImovelInscricaoNew(String idImovel,

	String idLocalidade, String codigoSetorComercial,

	String numeroQuadra, String idHidrometro, String lote, String subLote,

	String codigoCliente, String idMunicipio, String cep,

	String idBairro, String idLogradouro,

	boolean pesquisarImovelCondominio, String numeroImovel, Integer numeroPagina)

	throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = "select distinct logradouro.nome,"

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

							" enderecoReferencia.descricaoAbreviada,"

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

							" imovel.numeroImovel,"

							+ // 17

							" imovel.complementoEndereco,"

							+ // 18

							" logradouro.id,"

							+ // 19

							" logradouroCep.id,"

							+ // 20

							" logradouroBairro.id,"

							+ // 21

							" logradouroTipo.descricao,"

							+ // 22

							" logradouroTitulo.descricao,"

							+ // 23

							" enderecoReferencia.descricao, "

							+ // 24

							" imovel.id, "

							+ // 25

							" imovel.lote, "

							+ // 26

							" imovel.subLote, "

							+ // 27

							" localidade.id, "

							+ // 28

							" setorComercial.codigo, "

							+ // 29

							" quadra.numeroQuadra, "

							+ // 30

							" clienteUsuario.nome, "

							+ // 31

							" imovel.ligacaoAgua.hidrometroInstalacaoHistorico.hidrometro.numero "

							+ "from ClienteImovel clienteImovel "

							/*
							 * ## JOIN ##
							 */

							// join necessários
							+ "left join clienteImovel.imovel imovel "

							+ "inner join imovel.quadra quadra "

							+ "inner join imovel.localidade localidade "

							+ "inner join imovel.setorComercial setorComercial "

							+ "left join imovel.logradouroCep logradouroCep "

							+ "left join logradouroCep.cep cep "

							+ "left join logradouroCep.logradouro logradouro "

							+ "left join logradouro.logradouroTipo logradouroTipo "

							+ "left join logradouro.logradouroTitulo logradouroTitulo "

							+ "left join imovel.logradouroBairro logradouroBairro "

							+ "left join logradouroBairro.bairro bairro "

							+ "left join bairro.municipio municipio "

							+ "left join municipio.unidadeFederacao unidadeFederacao "

							+ "left join imovel.enderecoReferencia enderecoReferencia "

							+ "inner join clienteImovel.clienteRelacaoTipo clienteRelacaoTipo "

							+ "inner join clienteImovel.cliente clienteUsuario "

							+ "left join imovel.ligacaoAgua.hidrometroInstalacaoHistorico.hidrometro ";

			// join facultativos

			// cep

			if(cep != null

			&& !cep.equals("")

			&& !cep.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta

				+ " left join imovel.logradouroCep logradouroCep "

				+ "left join logradouroCep.cep cep ";

			}

			// bairro

			if(idBairro != null

			&& !idBairro.equals("")

			&& !idBairro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta

				+ " left join imovel.logradouroBairro logradouroBairro "

				+ " left join logradouroBairro.bairro bairro ";

			}

			// logradouro

			if(idLogradouro != null

			&& !idLogradouro.equals("")

			&& !idLogradouro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta

				+ " left join imovel.logradouroCep logradouroCep "

				+ " left join logradouroCep.logradouro logradouro ";

			}

			// municipio

			if(idMunicipio != null

			&& !idMunicipio.equals("")

			&& !idMunicipio.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta

				+ " left join imovel.logradouroBairro logradouroBairro "

				+ " left join logradouroBairro.bairro bairro "

				+ "left join bairro.municipio municipio ";

			}

			/*
			 * ## CONDIÇÕES ##
			 */

			consulta = consulta

			+ " where clienteImovel.dataFimRelacao is null "

			+ " and imovel.indicadorExclusao != "

			+ Imovel.IMOVEL_EXCLUIDO

			+ " and clienteRelacaoTipo.id = " + ClienteRelacaoTipo.USUARIO

			+ "  and  ";

			// pesquisar imovel condominio

			if(pesquisarImovelCondominio){

				consulta = consulta + " imovel.indicadorImovelCondominio = "

				+ Imovel.IMOVEL_CONDOMINIO + "  and  ";

			}

			// imovel

			if(idImovel != null

			&& !idImovel.equals("")

			&& !idImovel.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " imovel.id = :idImovel  and  ";

			}

			// localidade

			if((idLocalidade != null && !idLocalidade.equals("") && !idLocalidade

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta + " localidade.id = :idLocalidade  and  ";

			}

			// setor comercial

			if((codigoSetorComercial != null

			&& !codigoSetorComercial.equals("") && !codigoSetorComercial

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta

				+ " setorComercial.codigo = :codigoSetorComercial  and  ";

			}

			// quadra

			if((numeroQuadra != null && !numeroQuadra.equals("") && !numeroQuadra

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta

				+ " quadra.numeroQuadra = :numeroQuadra and  ";

			}

			// lote

			if((lote != null && !lote.equals("") && !lote.trim()

			.equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta + " imovel.lote = :lote  and  ";

			}

			// sublote

			if((subLote != null && !subLote.equals("") && !subLote.trim()

			.equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta + " imovel.subLote = :subLote  and  ";

			}

			// cliente

			if(codigoCliente != null

			&& !codigoCliente.equals("")

			&& !codigoCliente.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta

				+ " clienteUsuario.id = :codigoCliente  and  ";

			}

			// hidrometro

			if(idHidrometro != null

			&& !idHidrometro.equals("")

			&& !idHidrometro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta

				+ " imovel.ligacaoAgua.hidrometroInstalacaoHistorico.hidrometro.numero = :idHidrometro  and  "
								+ " imovel.ligacaoAgua.hidrometroInstalacaoHistorico.dataRetirada is null and ";

			}

			if(numeroImovel != null && !numeroImovel.trim().equalsIgnoreCase("")){
				consulta = consulta + " imovel.numeroImovel = :numeroImovel and ";
			}

			// municipio

			if(idMunicipio != null

			&& !idMunicipio.equals("")

			&& !idMunicipio.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " municipio.id = :idMunicipio  and  ";

			}

			// cep

			if(cep != null

			&& !cep.equals("")

			&& !cep.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " cep.codigo = :cep  and  ";

			}

			// bairro

			if(idBairro != null

			&& !idBairro.equals("")

			&& !idBairro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " bairro.codigo = :idBairro  and  ";

			}

			// logradouro

			if(idLogradouro != null

			&& !idLogradouro.equals("")

			&& !idLogradouro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " logradouro.id = :idLogradouro  and  ";

			}

			Query query = session.createQuery(consulta.substring(0, (consulta

			.length() - 5)));

			// seta os valores na condição where

			// imovel principal

			if(idImovel != null

			&& !idImovel.equals("")

			&& !idImovel.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("idImovel", new Integer(idImovel).intValue());

			}

			// localidade

			if((idLocalidade != null && !idLocalidade.equals("") && !idLocalidade

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				query.setInteger("idLocalidade", new Integer(idLocalidade)

				.intValue());

			}

			// setor

			if((codigoSetorComercial != null

			&& !codigoSetorComercial.equals("") && !codigoSetorComercial

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				query.setInteger("codigoSetorComercial", new Integer(

				codigoSetorComercial).intValue());

			}

			// quadra

			if((numeroQuadra != null && !numeroQuadra.equals("") && !numeroQuadra

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				query.setInteger("numeroQuadra", new Integer(numeroQuadra)

				.intValue());

			}

			// lote

			if((lote != null && !lote.equals("") && !lote.trim()

			.equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				query.setInteger("lote", new Integer(lote).intValue());

			}

			// subLote

			if((subLote != null && !subLote.equals("") && !subLote.trim()

			.equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				query.setInteger("subLote", new Integer(subLote).intValue());

			}

			// cliente

			if(codigoCliente != null

			&& !codigoCliente.equals("")

			&& !codigoCliente.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("codigoCliente", new Integer(codigoCliente)

				.intValue());

			}

			// hidrometro

			if(idHidrometro != null

			&& !idHidrometro.equals("")

			&& !idHidrometro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setString("idHidrometro", idHidrometro);

			}
			// Nr. Imovel
			if(numeroImovel != null && !numeroImovel.trim().equalsIgnoreCase("")){
				query.setString("numeroImovel", numeroImovel);
			}

			// municipio

			if(idMunicipio != null

			&& !idMunicipio.equals("")

			&& !idMunicipio.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("idMunicipio", new Integer(idMunicipio)

				.intValue());

			}

			// cep

			if(cep != null

			&& !cep.equals("")

			&& !cep.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("cep", new Integer(cep).intValue());

			}

			// bairro

			if(idBairro != null

			&& !idBairro.equals("")

			&& !idBairro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("idBairro", new Integer(idBairro).intValue());

			}

			// logradouro

			if(idLogradouro != null

			&& !idLogradouro.equals("")

			&& !idLogradouro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("idLogradouro", new Integer(idLogradouro)

				.intValue());

			}

			retorno = query.setFirstResult(10 * numeroPagina).setMaxResults(10)

			.list();

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
	 * Usado pelo Pesquisar Imovel Retorno o Imovel, com o Nome do Cliente,
	 * Matricula e Endereço
	 * 
	 * @author Rafael Santos
	 * @date 27/11/2006
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarImovelInscricao(String idImovel,

	String idLocalidade, String codigoSetorComercial,

	String numeroQuadra, String idHidrometro, String lote, String subLote,

	String codigoCliente, String idMunicipio, String cep,

	String idBairro, String idLogradouro,

	boolean pesquisarImovelCondominio, Integer numeroPagina)

	throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = "select distinct logradouro.nome,"

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

							" enderecoReferencia.descricaoAbreviada,"

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

							" imovel.numeroImovel,"

							+ // 17

							" imovel.complementoEndereco,"

							+ // 18

							" logradouro.id,"

							+ // 19

							" logradouroCep.id,"

							+ // 20

							" logradouroBairro.id,"

							+ // 21

							" logradouroTipo.descricao,"

							+ // 22

							" logradouroTitulo.descricao,"

							+ // 23

							" enderecoReferencia.descricao, "

							+ // 24

							" imovel.id, "

							+ // 25

							" imovel.lote, "

							+ // 26

							" imovel.subLote, "

							+ // 27

							" localidade.id, "

							+ // 28

							" setorComercial.codigo, "

							+ // 29

							" quadra.numeroQuadra, "

							+ // 30

							" clienteUsuario.nome, "

							+ // 31

							" imovel.hidrometroInstalacaoHistorico.hidrometro.numero "

							+ "from ClienteImovel clienteImovel "

							/*
							 * ## JOIN ##
							 */

							// join necessários
							+ "left join clienteImovel.imovel imovel "

							+ "inner join imovel.quadra quadra "

							+ "inner join imovel.localidade localidade "

							+ "inner join imovel.setorComercial setorComercial "

							+ "left join imovel.logradouroCep logradouroCep "

							+ "left join logradouroCep.cep cep "

							+ "left join logradouroCep.logradouro logradouro "

							+ "left join logradouro.logradouroTipo logradouroTipo "

							+ "left join logradouro.logradouroTitulo logradouroTitulo "

							+ "left join imovel.logradouroBairro logradouroBairro "

							+ "left join logradouroBairro.bairro bairro "

							+ "left join bairro.municipio municipio "

							+ "left join municipio.unidadeFederacao unidadeFederacao "

							+ "left join imovel.enderecoReferencia enderecoReferencia "

							+ "inner join clienteImovel.clienteRelacaoTipo clienteRelacaoTipo "

							+ "inner join clienteImovel.cliente clienteUsuario "

							+ "left join imovel.hidrometroInstalacaoHistorico.hidrometro hidrometro ";

			// join facultativos

			// cep

			if(cep != null

			&& !cep.equals("")

			&& !cep.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta

				+ " left join imovel.logradouroCep logradouroCep "

				+ "left join logradouroCep.cep cep ";

			}

			// bairro

			if(idBairro != null

			&& !idBairro.equals("")

			&& !idBairro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta

				+ " left join imovel.logradouroBairro logradouroBairro "

				+ " left join logradouroBairro.bairro bairro ";

			}

			// logradouro

			if(idLogradouro != null

			&& !idLogradouro.equals("")

			&& !idLogradouro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta

				+ " left join imovel.logradouroCep logradouroCep "

				+ " left join logradouroCep.logradouro logradouro ";

			}

			// municipio

			if(idMunicipio != null

			&& !idMunicipio.equals("")

			&& !idMunicipio.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta

				+ " left join imovel.logradouroBairro logradouroBairro "

				+ " left join logradouroBairro.bairro bairro "

				+ "left join bairro.municipio municipio ";

			}

			/*
			 * ## CONDIÇÕES ##
			 */

			consulta = consulta + " where " + "clienteRelacaoTipo.id = "

			+ ClienteRelacaoTipo.USUARIO

			+ " and clienteImovel.dataFimRelacao is null "

			+ " and imovel.indicadorExclusao != "

			+ Imovel.IMOVEL_EXCLUIDO + " and  ";

			// pesquisar imovel condominio

			if(pesquisarImovelCondominio){

				consulta = consulta + " imovel.indicadorImovelCondominio = "

				+ Imovel.IMOVEL_CONDOMINIO + "  and  ";

			}

			// imovel

			if(idImovel != null

			&& !idImovel.equals("")

			&& !idImovel.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " imovel.id = :idImovel  and  ";

			}

			// localidade

			if((idLocalidade != null && !idLocalidade.equals("") && !idLocalidade

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta + " localidade.id = :idLocalidade  and  ";

			}

			// setor comercial

			if((codigoSetorComercial != null

			&& !codigoSetorComercial.equals("") && !codigoSetorComercial

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta

				+ " setorComercial.codigo = :codigoSetorComercial  and  ";

			}

			// quadra

			if((numeroQuadra != null && !numeroQuadra.equals("") && !numeroQuadra

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta

				+ " quadra.numeroQuadra = :numeroQuadra and  ";

			}

			// lote

			if((lote != null && !lote.equals("") && !lote.trim()

			.equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta + " imovel.lote = :lote  and  ";

			}

			// sublote

			if((subLote != null && !subLote.equals("") && !subLote.trim()

			.equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				consulta = consulta + " imovel.subLote = :subLote  and  ";

			}

			// cliente

			if(codigoCliente != null

			&& !codigoCliente.equals("")

			&& !codigoCliente.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta

				+ " clienteUsuario.id = :codigoCliente  and  ";

			}

			// hidrometro

			if(idHidrometro != null

			&& !idHidrometro.equals("")

			&& !idHidrometro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta

				+ " imovel.hidrometroInstalacaoHistorico.hidrometro.numero = :idHidrometro  and  "
								+ " imovel.hidrometroInstalacaoHistorico.dataRetirada is null and ";

			}

			// municipio

			if(idMunicipio != null

			&& !idMunicipio.equals("")

			&& !idMunicipio.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " municipio.id = :idMunicipio  and  ";

			}

			// cep

			if(cep != null

			&& !cep.equals("")

			&& !cep.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " cep.codigo = :cep  and  ";

			}

			// bairro

			if(idBairro != null

			&& !idBairro.equals("")

			&& !idBairro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " bairro.codigo = :idBairro  and  ";

			}

			// logradouro

			if(idLogradouro != null

			&& !idLogradouro.equals("")

			&& !idLogradouro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				consulta = consulta + " logradouro.id = :idLogradouro  and  ";

			}

			Query query = session.createQuery(consulta.substring(0, (consulta

			.length() - 5)));

			// seta os valores na condição where

			// imovel principal

			if(idImovel != null

			&& !idImovel.equals("")

			&& !idImovel.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("idImovel", new Integer(idImovel).intValue());

			}

			// localidade

			if((idLocalidade != null && !idLocalidade.equals("") && !idLocalidade

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				query.setInteger("idLocalidade", new Integer(idLocalidade)

				.intValue());

			}

			// setor

			if((codigoSetorComercial != null

			&& !codigoSetorComercial.equals("") && !codigoSetorComercial

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				query.setInteger("codigoSetorComercial", new Integer(

				codigoSetorComercial).intValue());

			}

			// quadra

			if((numeroQuadra != null && !numeroQuadra.equals("") && !numeroQuadra

			.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				query.setInteger("numeroQuadra", new Integer(numeroQuadra)

				.intValue());

			}

			// lote

			if((lote != null && !lote.equals("") && !lote.trim()

			.equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				query.setInteger("lote", new Integer(lote).intValue());

			}

			// subLote

			if((subLote != null && !subLote.equals("") && !subLote.trim()

			.equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString()))){

				query.setInteger("subLote", new Integer(subLote).intValue());

			}

			// cliente

			if(codigoCliente != null

			&& !codigoCliente.equals("")

			&& !codigoCliente.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("codigoCliente", new Integer(codigoCliente)

				.intValue());

			}

			// hidrometro

			if(idHidrometro != null && !idHidrometro.equals("") && !idHidrometro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setString("idHidrometro", idHidrometro);

			}

			// municipio

			if(idMunicipio != null

			&& !idMunicipio.equals("")

			&& !idMunicipio.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("idMunicipio", new Integer(idMunicipio)

				.intValue());

			}

			// cep

			if(cep != null

			&& !cep.equals("")

			&& !cep.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("cep", new Integer(cep).intValue());

			}

			// bairro

			if(idBairro != null

			&& !idBairro.equals("")

			&& !idBairro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("idBairro", new Integer(idBairro).intValue());

			}

			// logradouro

			if(idLogradouro != null

			&& !idLogradouro.equals("")

			&& !idLogradouro.trim().equalsIgnoreCase(

			new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO)

			.toString())){

				query.setInteger("idLogradouro", new Integer(idLogradouro)

				.intValue());

			}

			retorno = query.setFirstResult(10 * numeroPagina).setMaxResults(10)

			.list();

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
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Recupera o id da situação da ligação de esgoto
	 * 
	 * @author Sávio Luiz
	 * @date 04/12/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Integer pesquisaridLigacaoEsgotoSituacao(Integer idImovel)

	throws ErroRepositorioException{

		// cria a coleção de retorno

		Integer retorno = null;

		// Query

		String consulta;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			// pesquisa a coleção de atividades e atribui a variável "retorno"

			consulta = "SELECT ligEsgSit.id "

			+ "FROM gcom.cadastro.imovel.Imovel  imovel "

			+ "INNER JOIN imovel.ligacaoEsgotoSituacao ligEsgSit "

			+ "WHERE imovel.id = :idImovel";

			retorno = (Integer) session.createQuery(consulta).setInteger(

			"idImovel", idImovel).setMaxResults(1).uniqueResult();

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
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Recupera o id da situação da ligacao de agua
	 * 
	 * @author Sávio Luiz
	 * @date 04/12/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Integer pesquisaridLigacaoAguaSituacao(Integer idImovel)

	throws ErroRepositorioException{

		// cria a coleção de retorno

		Integer retorno = null;

		// Query

		String consulta;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			// pesquisa a coleção de atividades e atribui a variável "retorno"

			consulta = "SELECT ligAguaSit.id "

			+ "FROM gcom.cadastro.imovel.Imovel  imovel "

			+ "INNER JOIN imovel.ligacaoAguaSituacao ligAguaSit "

			+ "WHERE imovel.id = :idImovel";

			retorno = (Integer) session.createQuery(consulta).setInteger(

			"idImovel", idImovel).setMaxResults(1).uniqueResult();

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
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Recupera o id da situação da ligacao de agua
	 * 
	 * @author Sávio Luiz
	 * @date 04/12/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Integer pesquisarTarifaImovel(Integer idImovel)

	throws ErroRepositorioException{

		// cria a coleção de retorno

		Integer retorno = null;

		// Query

		String consulta;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			consulta = "SELECT consTarifa.id "

			+ "FROM gcom.cadastro.imovel.Imovel  imovel "

			+ "INNER JOIN imovel.consumoTarifa consTarifa "

			+ "WHERE imovel.id = :idImovel";

			retorno = (Integer) session.createQuery(consulta).setInteger(

			"idImovel", idImovel).setMaxResults(1).uniqueResult();

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
	 * Pesquisa os imóveis do cliente de acordo com o tipo de relação
	 * [UC0251] Gerar Atividade de Ação de Cobrança [SB0001] Gerar Atividade de
	 * Ação de Cobrança para os Imóveis do Cliente
	 * 
	 * @author Leonardo Vieira
	 * @created 12/12/2006
	 * @param cliente
	 * @param relacaoClienteImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarImoveisClientesRelacao(Cliente cliente,

	ClienteRelacaoTipo relacaoClienteImovel, Integer numeroInicial, Integer idSetorComercial)

	throws ErroRepositorioException{

		// cria a coleção de retorno

		Collection retorno = null;

		// Query

		StringBuffer consulta = new StringBuffer();

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			consulta.append("select distinct im.id,  ") // 0

							.append("rt.id, ") // 1

							.append("im.ligacaoAguaSituacao.id, ") // 2

							.append("im.ligacaoEsgotoSituacao.id, ") // 3

							.append("im.imovelPerfil.id, ") // 4

							.append("rt.empresa.id, ") // 5

							.append("im.localidade.id, ") // 6

							.append("scm.codigo, ") // 7

							.append("qd.numeroQuadra, ") // 8

							.append("im.lote, ") // 9

							.append("im.subLote, ") // 10

							.append("qd.id, ") // 11

							.append("im.cobrancaSituacaoTipo.id, ") // 12

							.append("im.indicadorDebitoConta, ") // 13

							.append("rt.empresaCobranca.id ") // 14

							.append("from ClienteImovel ci ")

							.append("inner join ci.imovel im ")

							// .append("inner join ci.cliente cl ")
							//
							// if (relacaoClienteImovel != null
							// && relacaoClienteImovel.getId() != null) {
							//
							// consulta.append("inner join ci.clienteRelacaoTipo crt ");
							// }

							.append("inner join im.quadra qd ")

							.append("inner join im.rota rt ")

							.append("inner join im.setorComercial scm ")

							.append("where ci.cliente = :idCliente and ci.dataFimRelacao is null ");

			if(relacaoClienteImovel != null && relacaoClienteImovel.getId() != null){

				consulta.append(" and ci.clienteRelacaoTipo  = :relacaoClienteImovel ");
			}

			if(idSetorComercial != null){
				consulta.append("and scm.id = :idSetorComercial ");
			}

			consulta.append(" order by im.id");

			Query query = session.createQuery(consulta.toString()).setInteger("idCliente", cliente.getId());

			if(relacaoClienteImovel != null && relacaoClienteImovel.getId() != null){

				query.setInteger("relacaoClienteImovel", relacaoClienteImovel.getId());
			}

			if(idSetorComercial != null){
				query.setInteger("idSetorComercial", idSetorComercial);
			}

			retorno = query.setFirstResult(numeroInicial).setMaxResults(500).list();

			// erro no hibernate

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);
			consulta = null;

		}

		// retorna a coleção de atividades pesquisada(s)

		return retorno;

	}

	public Integer pesquisarExistenciaImovelSubCategoria(Integer idImovel,

	Integer idCategoria) throws ErroRepositorioException{

		// cria a coleção de retorno

		Integer retorno = null;

		// Query

		StringBuffer consulta = new StringBuffer();

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			consulta.append("select imovelSubcategoria.comp_id.imovel.id ")

			.append("from ImovelSubcategoria imovelSubcategoria ")

			.append("inner join imovelSubcategoria.comp_id.subcategoria subcategoria ")

			.append("inner join subcategoria.categoria categoria ")

			.append("where imovelSubcategoria.comp_id.imovel.id = :idImovel ")

			.append("and categoria.id = :idCategoria ");

			retorno = (Integer) session.createQuery(consulta.toString()).setInteger(

			"idImovel", idImovel)

			.setInteger("idCategoria", idCategoria).setMaxResults(1)

			.uniqueResult();

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

	public Collection pesquisarImoveisPorRota(Integer idRota)

	throws ErroRepositorioException{

		// cria a coleção de retorno

		Collection retorno = null;

		// Query

		String consulta;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			consulta = "select im.id," // 0

							+ "im.ligacaoAguaSituacao.id, " // 1

							+ "im.ligacaoEsgotoSituacao.id, " // 2

							+ "im.imovelPerfil.id, " // 3

							+ "rt.empresa.id, " // 4

							+ "im.localidade.id, " // 5

							+ "im.setorComercial.codigo, " // 6

							+ "im.quadra.numeroQuadra, " // 7

							+ "im.lote, " // 8

							+ "im.subLote, " // 9

							+ "im.quadra.id " // 10

							+ "from Imovel im " + "inner join im.rota rt "

							+ "where rt.id = :idRota ";

			retorno = (Collection) session.createQuery(consulta).setInteger(

			"idRota", idRota).list();

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

	public Collection pesquisarImoveisPorRotaComPaginacao(Integer idRota,

	Integer numeroInicial) throws ErroRepositorioException{

		// cria a coleção de retorno

		Collection retorno = null;

		// Query

		StringBuffer consulta = new StringBuffer();

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			consulta.append("select im.id,") // 0

							.append("im.ligacaoAguaSituacao.id, ") // 1

							.append("im.ligacaoEsgotoSituacao.id, ") // 2

							.append("im.imovelPerfil.id, ") // 3

							.append("rt.empresa.id, ") // 4

							.append("im.localidade.id, ") // 5

							.append("scm.codigo, ") // 6

							.append("im.quadra.numeroQuadra, ") // 7

							.append("im.lote, ") // 8

							.append("im.subLote, ") // 9

							.append("im.quadra.id, ") // 10

							.append("im.cobrancaSituacaoTipo.id, ") // 11

							.append("im.indicadorDebitoConta, ") // 12

							.append("rt.empresaCobranca.id ") // 13

							.append("from Imovel im " + "inner join im.rota rt ")

							.append("inner join im.setorComercial scm ")

							.append("where rt.id = :idRota ");

			retorno = (Collection) session.createQuery(consulta.toString()).setInteger(

			"idRota", idRota).setFirstResult(numeroInicial)

			.setMaxResults(500).list();

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
	 * @author Saulo Lima
	 * @date 01/10/2010
	 * @param idRota
	 * @param idCriterioCobranca
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarImoveisPorRotaCriterioCobranca(Integer idRota, Integer idCriterioCobranca)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		// Connection jdbcCon = session.connection();
		// PreparedStatement st = null;

		try{
			StringBuffer consulta = criarConsultaPesquisarImoveisPorSetorComercialCriterioCobranca();
			consulta.append(",   cobranca_criterio cc");
			consulta.append(" where i.qdra_id = q.qdra_id");
			consulta.append("   and q.rota_id = r.rota_id");
			consulta.append("   and q.stcm_id = sc.stcm_id");
			consulta.append("   and r.rota_id = :idRota");
			consulta.append("   and cc.cbct_id = :idCriterioCobranca");
			consulta
							.append("   and ((cbct_ictelefone <> 1) or (cbct_ictelefone = 1 and exists (select ci.clie_id from cliente_imovel ci, cliente_fone cf where ci.imov_id = i.imov_id and cf.clie_id = ci.clie_id and clim_dtrelacaofim is null and crtp_id = 2)))");
			// consulta.append("   and ((cbct_iccpf <> 1) or (cbct_iccpf = 1 and exists (select ci.clie_id from cliente_imovel ci, cliente cl where ci.imov_id = i.imov_id and cl.clie_id = ci.clie_id and clim_dtrelacaofim is null and crtp_id = 2 and not (clie_nncpf is null and clie_nncnpj is null))))");
			consulta.append("   and exists");
			consulta.append(" (select sub.catg_id");
			consulta.append("  from imovel_subcategoria isub, subcategoria sub, cobranca_criterio_linha ccl");
			consulta.append("  where isub.imov_id = i.imov_id");
			consulta.append("     and isub.scat_id = sub.scat_id");
			consulta.append("     and ccl.catg_id  = sub.catg_id");
			consulta.append("     and ccl.cbct_id = cc.cbct_id)");
			consulta
							.append(" and i.iper_id in (select nvl(ccl.iper_id,i.iper_id) from cobranca_criterio_linha ccl where ccl.cbct_id = cc.cbct_id )");
			consulta
							.append(" and i.last_id in (select nvl(ccl.last_id,i.last_id) from cobranca_criterio_linha ccl where ccl.cbct_id = cc.cbct_id )");
			consulta
							.append(" and i.lest_id in (select nvl(ccl.lest_id,i.lest_id) from cobranca_criterio_linha ccl where ccl.cbct_id = cc.cbct_id )");

			// abre a conexao
			// st = jdbcCon.prepareStatement(consulta.toString());

			retorno = criarSQLQuyery(consulta, session).setInteger("idRota", idRota).setInteger("idCriterioCobranca", idCriterioCobranca)
							.list();

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
	 * @author Saulo Lima
	 * @date 01/10/2010
	 * @param idSetorComercial
	 * @param idCriterioCobranca
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarImoveisPorSetorComercialCriterioCobranca(Integer idSetorComercial, Integer idCriterioCobranca,
					Date dataCortado, Integer idGrupoCobranca, Integer[] idsTipoDocumentoAIgnorar, Integer idAcaoCobranca,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection retorno = null;
		StringBuffer consulta = criarConsultaPesquisarImoveisPorSetorComercialCriterioCobranca();
		try{

			if(dataCortado != null){
				consulta.append(",   ligacao_agua la,");
				consulta.append("   cobranca_criterio cc");
				montarRelacionamentosBase(consulta);
				consulta.append("   and i.imov_id = la.lagu_id");
				consulta.append("   and r.stcm_id = :idSetorComercial");
				consulta.append("   and cc.cbct_id = :idCriterioCobranca");
				//consulta.append("   and ((cbct_ictelefone <> 1) or (cbct_ictelefone = 1 and exists (select ci.clie_id from cliente_imovel ci, cliente_fone cf where ci.imov_id = i.imov_id and cf.clie_id = ci.clie_id and clim_dtrelacaofim is null and crtp_id = :idClienteRelacaoTipo )))");
				// consulta
				// .append("   and ((cbct_iccpf <> 1) or (cbct_iccpf = 1 and exists (select ci.clie_id from cliente_imovel ci, cliente cl where ci.imov_id = i.imov_id and cl.clie_id = ci.clie_id and clim_dtrelacaofim is null and crtp_id = :idClienteRelacaoTipo and not (clie_nncpf is null and clie_nncnpj is null))))");
				consulta.append("   and exists");
				consulta.append(" (select sub.catg_id");
				consulta.append("  from imovel_subcategoria isub, subcategoria sub, cobranca_criterio_linha ccl");
				consulta.append("  where isub.imov_id = i.imov_id");
				consulta.append("     and isub.scat_id = sub.scat_id");
				consulta.append("     and ccl.catg_id  = sub.catg_id");
				consulta.append("     and ccl.cbct_id = cc.cbct_id)");
				consulta
								.append(" and i.iper_id in (select nvl(ccl.iper_id,i.iper_id) from cobranca_criterio_linha ccl where ccl.cbct_id = cc.cbct_id )");
				consulta
								.append(" and i.last_id in (select nvl(csla.last_id,i.last_id) from criterio_situacao_ligacao_agua csla where csla.cbct_id = cc.cbct_id )");
				consulta
								.append(" and i.lest_id in (select nvl(csle.lest_id,i.lest_id) from criterio_situacao_ligacao_esgo csle where csle.cbct_id = cc.cbct_id )");
				consulta.append(" and i.last_id = :situacao  ");
				consulta.append(" and la.lagu_dtcorte <= :dataCortado ");

				if(idAcaoCobranca.intValue() != CobrancaAcao.NEGATIVACAO_SPC_BRASIL.intValue()
								&& idAcaoCobranca.intValue() != CobrancaAcao.NEGATIVACAO_SPC_SP.intValue()
								&& idAcaoCobranca.intValue() != CobrancaAcao.NEGATIVACAO_SERASA.intValue()
								&& idAcaoCobranca.intValue() != CobrancaAcao.COBRANCA_BANCARIA.intValue()
								&& idAcaoCobranca.intValue() != CobrancaAcao.COBRANCA_ADMINISTRATIVA.intValue()){
					// imovel nao esteja com documento de cobranca valida
					consulta
									.append(" and not exists ( select imov_id from cobranca_documento where imov_id = i.imov_id and (cast_id is null or cast_id <> :idCobrancaAcaoSituacao ) and dotp_id IN(:idsTipoDocumentoAIgnorar)  and cbdo_tmemissao + (select ca.cbac_nndiasvalidade from cobranca_acao ca where cbac_id = :idAcaoCobranca) >= :dataAtual )");
				}

				if(idGrupoCobranca != null){
					consulta.append(" and r.cbgr_id = :idGrupoCobranca ");
				}

				// Caso o intervalo de rotas do comando esteja preenchido
				if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getRotaInicial() != null
								&& cobrancaAcaoAtividadeComando.getRotaFinal() != null){

					consulta.append(" and i.rota_id between :idRotaInicial and :idRotaFinal ");
				}

				// Caso o intervalo de quadras do comando esteja preenchido
				if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getLocalidadeInicial() != null
								&& cobrancaAcaoAtividadeComando.getCodigoSetorComercialInicial() != null
								&& cobrancaAcaoAtividadeComando.getNumeroQuadraInicial() != null
								&& cobrancaAcaoAtividadeComando.getNumeroQuadraFinal() != null){

					consulta.append(" and i.loca_id = :idLocalidadeInicial and i.stcm_cdsetorcomercial = :cdSetorComercialInicial  and i.qdra_nnquadra between :nnQuadraInicial and :nnQuadraFinal ");
				}

				SQLQuery query = criarSQLQuyery(consulta, session);

				if(idAcaoCobranca.intValue() != CobrancaAcao.NEGATIVACAO_SPC_BRASIL.intValue()
								&& idAcaoCobranca.intValue() != CobrancaAcao.NEGATIVACAO_SPC_SP.intValue()
								&& idAcaoCobranca.intValue() != CobrancaAcao.NEGATIVACAO_SERASA.intValue()
								&& idAcaoCobranca.intValue() != CobrancaAcao.COBRANCA_BANCARIA.intValue()
								&& idAcaoCobranca.intValue() != CobrancaAcao.COBRANCA_ADMINISTRATIVA.intValue()){

					query.setInteger("idCobrancaAcaoSituacao", CobrancaAcaoSituacao.CANCELADA_PRAZO);
					query.setParameterList("idsTipoDocumentoAIgnorar", idsTipoDocumentoAIgnorar);
					query.setInteger("idAcaoCobranca", idAcaoCobranca);
					query.setDate("dataAtual", new Date());
				}

				// Caso o intervalo de rotas do comando esteja preenchido
				if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getRotaInicial() != null
								&& cobrancaAcaoAtividadeComando.getRotaFinal() != null){

					query.setInteger("idRotaInicial", cobrancaAcaoAtividadeComando.getRotaInicial().getId());
					query.setInteger("idRotaFinal", cobrancaAcaoAtividadeComando.getRotaFinal().getId());
				}

				// Caso o intervalo de quadras do comando esteja preenchido
				if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getLocalidadeInicial() != null
								&& cobrancaAcaoAtividadeComando.getCodigoSetorComercialInicial() != null
								&& cobrancaAcaoAtividadeComando.getNumeroQuadraInicial() != null
								&& cobrancaAcaoAtividadeComando.getNumeroQuadraFinal() != null){

					query.setInteger("idLocalidadeInicial", cobrancaAcaoAtividadeComando.getLocalidadeInicial().getId());
					query.setInteger("cdSetorComercialInicial", cobrancaAcaoAtividadeComando.getCodigoSetorComercialInicial());
					query.setInteger("nnQuadraInicial", cobrancaAcaoAtividadeComando.getNumeroQuadraInicial());
					query.setInteger("nnQuadraFinal", cobrancaAcaoAtividadeComando.getNumeroQuadraFinal());
				}

				query.setInteger("idSetorComercial", idSetorComercial).setInteger("idCriterioCobranca", idCriterioCobranca)
								//.setInteger("idClienteRelacaoTipo", clienteRelacaoTipo.getId())
								.setInteger(
								"situacao", LigacaoAguaSituacao.CORTADO).setDate("dataCortado", dataCortado);

				if(idGrupoCobranca != null){
					query.setInteger("idGrupoCobranca", idGrupoCobranca);
				}
				retorno = query.list();

			}else{
				retorno = realizarPesquisarImoveisPorSetorComercialCriterioCobranca(idSetorComercial, idCriterioCobranca, idGrupoCobranca,
								idsTipoDocumentoAIgnorar, idAcaoCobranca, cobrancaAcaoAtividadeComando, session, consulta);
			}

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
			consulta = null;
		}

		return retorno;
	}

	private SQLQuery criarSQLQuyery(StringBuffer consulta, Session session){

		return session.createSQLQuery(consulta.toString()).addScalar("imov_id", Hibernate.INTEGER).addScalar("last_id", Hibernate.INTEGER)
						.addScalar("lest_id", Hibernate.INTEGER).addScalar("iper_id", Hibernate.INTEGER)
						.addScalar("empr_id", Hibernate.INTEGER).addScalar("loca_id", Hibernate.INTEGER)
						.addScalar("stcm_cdsetorcomercial", Hibernate.INTEGER).addScalar("qdra_nnquadra", Hibernate.INTEGER)
						.addScalar("imov_nnlote", Hibernate.SHORT).addScalar("imov_nnsublote", Hibernate.SHORT)
						.addScalar("qdra_id", Hibernate.INTEGER).addScalar("CBSP_ID", Hibernate.INTEGER)
						.addScalar("imov_icdebitoconta", Hibernate.SHORT).addScalar("empr_idcobranca", Hibernate.INTEGER)
						.addScalar("lgcp_id", Hibernate.INTEGER).addScalar("imov_icexclusao", Hibernate.SHORT);
	}

	private StringBuffer criarConsultaPesquisarImoveisPorSetorComercialCriterioCobranca(){

		StringBuffer consulta = new StringBuffer();
		consulta.append("select ");
		consulta.append(" i.imov_id,"); // 0
		consulta.append(" i.last_id,"); // 1
		consulta.append(" i.lest_id,"); // 2
		consulta.append(" i.iper_id,"); // 3
		consulta.append(" r.empr_id,"); // 4
		consulta.append(" i.loca_id,"); // 5
		consulta.append(" sc.stcm_cdsetorcomercial,"); // 6
		consulta.append(" q.qdra_nnquadra,"); // 7
		consulta.append(" i.imov_nnlote,"); // 8
		consulta.append(" i.imov_nnsublote,"); // 9
		consulta.append(" i.qdra_id,"); // 10
		consulta.append(" i.CBSP_ID,"); // 11
		consulta.append(" i.imov_icdebitoconta,"); // 12
		consulta.append(" r.empr_idcobranca, "); // 13
		consulta.append(" i.lgcp_id, "); // 14
		consulta.append(" i.imov_icexclusao "); // 15
		consulta.append(" from imovel i,");
		consulta.append("   quadra q,");
		consulta.append("   rota r,");
		consulta.append("   setor_comercial sc ");
		return consulta;
	}

	private Collection realizarPesquisarImoveisPorSetorComercialCriterioCobranca(Integer idSetorComercial, Integer idCriterioCobranca,
					Integer idGrupoCobranca, Integer[] idsTipoDocumentoAIgnorar, Integer idAcaoCobranca,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Session session, StringBuffer consulta){

		Collection retorno;
		consulta.append(" , cobranca_criterio cc ");
		montarRelacionamentosBase(consulta);
		consulta.append("   and r.stcm_id = :idSetorComercial");
		consulta.append("   and cc.cbct_id = :idCriterioCobranca");
		//consulta.append("   and ((cbct_ictelefone <> 1) or (cbct_ictelefone = 1 and exists (select ci.clie_id from cliente_imovel ci, cliente_fone cf where ci.imov_id = i.imov_id and cf.clie_id = ci.clie_id and clim_dtrelacaofim is null and crtp_id = :idClienteRelacaoTipo )))");
		// consulta.append("   and ((cbct_iccpf <> 1) or (cbct_iccpf = 1 and exists (select ci.clie_id from cliente_imovel ci, cliente cl where ci.imov_id = i.imov_id and cl.clie_id = ci.clie_id and clim_dtrelacaofim is null and crtp_id = :idClienteRelacaoTipo and not (clie_nncpf is null and clie_nncnpj is null))))");
		consulta.append("   and exists");
		consulta.append(" (select sub.catg_id");
		consulta.append("  from imovel_subcategoria isub, subcategoria sub, cobranca_criterio_linha ccl");
		consulta.append("  where isub.imov_id = i.imov_id");
		consulta.append("     and isub.scat_id = sub.scat_id");
		consulta.append("     and ccl.catg_id  = sub.catg_id");
		consulta.append("     and ccl.cbct_id = cc.cbct_id)");
		consulta
						.append(" and i.iper_id in (select nvl(ccl.iper_id,i.iper_id) from cobranca_criterio_linha ccl where ccl.cbct_id = cc.cbct_id )");
		consulta
						.append(" and i.last_id in (select nvl(csla.last_id,i.last_id) from criterio_situacao_ligacao_agua csla where csla.cbct_id = cc.cbct_id )");
		consulta
						.append(" and i.lest_id in (select nvl(csle.lest_id,i.lest_id) from criterio_situacao_ligacao_esgo csle where csle.cbct_id = cc.cbct_id )");

		if(idAcaoCobranca.intValue() != CobrancaAcao.NEGATIVACAO_SPC_BRASIL.intValue()
						&& idAcaoCobranca.intValue() != CobrancaAcao.NEGATIVACAO_SPC_SP.intValue()
						&& idAcaoCobranca.intValue() != CobrancaAcao.NEGATIVACAO_SERASA.intValue()
						&& idAcaoCobranca.intValue() != CobrancaAcao.COBRANCA_BANCARIA.intValue()
						&& idAcaoCobranca.intValue() != CobrancaAcao.COBRANCA_ADMINISTRATIVA.intValue()){
			// imovel nao esteja com documento de cobranca valida
			consulta
							.append(" and not exists ( select imov_id from cobranca_documento where imov_id = i.imov_id and (cast_id is null or cast_id <> :idCobrancaAcaoSituacao ) and dotp_id IN(:idsTipoDocumentoAIgnorar)  and cbdo_tmemissao + (select ca.cbac_nndiasvalidade from cobranca_acao ca where cbac_id = :idAcaoCobranca) > :dataAtual )");
		}

		consulta.append(" and i.imov_icexclusao <> 1");

		if(idGrupoCobranca != null){
			consulta.append(" and r.cbgr_id = :idGrupoCobranca ");
		}

		// Caso o intervalo de rotas do comando esteja preenchido
		if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getRotaInicial() != null
						&& cobrancaAcaoAtividadeComando.getRotaFinal() != null){

			consulta.append(" and i.rota_id between :idRotaInicial and :idRotaFinal ");
		}

		// Caso o intervalo de quadras do comando esteja preenchido
		if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getLocalidadeInicial() != null
						&& cobrancaAcaoAtividadeComando.getCodigoSetorComercialInicial() != null
						&& cobrancaAcaoAtividadeComando.getNumeroQuadraInicial() != null
						&& cobrancaAcaoAtividadeComando.getNumeroQuadraFinal() != null){

			consulta.append(" and i.loca_id = :idLocalidadeInicial and i.stcm_cdsetorcomercial = :cdSetorComercialInicial  and i.qdra_nnquadra between :nnQuadraInicial and :nnQuadraFinal ");
		}

		SQLQuery query = criarSQLQuyery(consulta, session);

		if(idAcaoCobranca.intValue() != CobrancaAcao.NEGATIVACAO_SPC_BRASIL.intValue()
						&& idAcaoCobranca.intValue() != CobrancaAcao.NEGATIVACAO_SPC_SP.intValue()
						&& idAcaoCobranca.intValue() != CobrancaAcao.NEGATIVACAO_SERASA.intValue()
						&& idAcaoCobranca.intValue() != CobrancaAcao.COBRANCA_BANCARIA.intValue()
						&& idAcaoCobranca.intValue() != CobrancaAcao.COBRANCA_ADMINISTRATIVA.intValue()){

			query.setInteger("idCobrancaAcaoSituacao", CobrancaAcaoSituacao.CANCELADA_PRAZO);
			query.setParameterList("idsTipoDocumentoAIgnorar", idsTipoDocumentoAIgnorar);
			query.setInteger("idAcaoCobranca", idAcaoCobranca);
			query.setDate("dataAtual", new Date());
		}

		// Caso o intervalo de rotas do comando esteja preenchido
		if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getRotaInicial() != null
						&& cobrancaAcaoAtividadeComando.getRotaFinal() != null){

			query.setInteger("idRotaInicial", cobrancaAcaoAtividadeComando.getRotaInicial().getId());
			query.setInteger("idRotaFinal", cobrancaAcaoAtividadeComando.getRotaFinal().getId());
		}

		// Caso o intervalo de quadras do comando esteja preenchido
		if(cobrancaAcaoAtividadeComando != null && cobrancaAcaoAtividadeComando.getLocalidadeInicial() != null
						&& cobrancaAcaoAtividadeComando.getCodigoSetorComercialInicial() != null
						&& cobrancaAcaoAtividadeComando.getNumeroQuadraInicial() != null
						&& cobrancaAcaoAtividadeComando.getNumeroQuadraFinal() != null){

			query.setInteger("idLocalidadeInicial", cobrancaAcaoAtividadeComando.getLocalidadeInicial().getId());
			query.setInteger("cdSetorComercialInicial", cobrancaAcaoAtividadeComando.getCodigoSetorComercialInicial());
			query.setInteger("nnQuadraInicial", cobrancaAcaoAtividadeComando.getNumeroQuadraInicial());
			query.setInteger("nnQuadraFinal", cobrancaAcaoAtividadeComando.getNumeroQuadraFinal());
		}

		query.setInteger("idSetorComercial", idSetorComercial).setInteger("idCriterioCobranca", idCriterioCobranca);
						

		if(idGrupoCobranca != null){
			query.setInteger("idGrupoCobranca", idGrupoCobranca);
		}
		retorno = query.list();
		return retorno;
	}

	private void montarRelacionamentosBase(StringBuffer consulta){

		consulta.append(" where i.qdra_id = q.qdra_id");
		consulta.append("   and i.rota_id = r.rota_id");
		consulta.append("   and q.stcm_id = sc.stcm_id");
	}

	//
	// /**
	// * @author Saulo Lima
	// * @date 01/10/2010
	// *
	// * @param idRota
	// * @param idCriterioCobranca
	// * @return
	// * @throws ErroRepositorioException
	// */
	// public Collection<Object[]> pesquisarImoveisPorRotaCriterioCobranca(Integer idRota, Integer
	// idCriterioCobranca) throws ErroRepositorioException {
	//
	// Collection<Object[]> retorno = new ArrayList<Object[]>();
	//
	// Session session = HibernateUtil.getSession();
	//
	// Connection jdbcCon = session.connection();
	// PreparedStatement st = null;
	//
	// try {
	// StringBuffer consulta = new StringBuffer();
	//
	// consulta.append("select /*+ RULE*/");
	// consulta.append(" i.imov_id,"); //0
	// consulta.append(" i.last_id,"); //1
	// consulta.append(" i.lest_id,"); //2
	// consulta.append(" i.iper_id,"); //3
	// consulta.append(" r.empr_id,"); //4
	// consulta.append(" i.loca_id,"); //5
	// consulta.append(" sc.stcm_cdsetorcomercial,"); //6
	// consulta.append(" q.qdra_nnquadra,"); //7
	// consulta.append(" i.imov_nnlote,"); //8
	// consulta.append(" i.imov_nnsublote,"); //9
	// consulta.append(" i.qdra_id,"); //10
	// consulta.append(" i.CBSP_ID,"); //11
	// consulta.append(" i.imov_icdebitoconta,"); //12
	// consulta.append(" r.empr_idcobranca "); //13
	// consulta.append(" from imovel i,");
	// consulta.append("   quadra q,");
	// consulta.append("   rota r,");
	// consulta.append("   setor_comercial sc,");
	// consulta.append("   cobranca_criterio cc");
	// consulta.append(" where i.qdra_id = q.qdra_id");
	// consulta.append("   and q.rota_id = r.rota_id");
	// consulta.append("   and q.stcm_id = sc.stcm_id");
	// consulta.append("   and r.rota_id = ? ");
	// consulta.append("   and cc.cbct_id = ? ");
	// consulta.append("   and ((cbct_ictelefone <> 1) or (cbct_ictelefone = 1 and exists (select ci.clie_id from cliente_imovel ci, cliente_fone cf where ci.imov_id = i.imov_id and cf.clie_id = ci.clie_id and clim_dtrelacaofim is null and crtp_id = 2)))");
	// consulta.append("   and ((cbct_iccpf <> 1) or (cbct_iccpf = 1 and exists (select ci.clie_id from cliente_imovel ci, cliente cl where ci.imov_id = i.imov_id and cl.clie_id = ci.clie_id and clim_dtrelacaofim is null and crtp_id = 2 and not (clie_nncpf is null and clie_nncnpj is null))))");
	// consulta.append("   and exists");
	// consulta.append(" (select sub.catg_id");
	// consulta.append("  from imovel_subcategoria isub, subcategoria sub, cobranca_criterio_linha ccl");
	// consulta.append("  where isub.imov_id = i.imov_id");
	// consulta.append("     and isub.scat_id = sub.scat_id");
	// consulta.append("     and ccl.catg_id  = sub.catg_id");
	// consulta.append("     and ccl.cbct_id = cc.cbct_id)");
	// consulta.append(" and i.iper_id in (select nvl(ccl.iper_id,i.iper_id) from cobranca_criterio_linha ccl where ccl.cbct_id = cc.cbct_id )");
	// consulta.append(" and i.last_id in (select nvl(ccl.last_id,i.last_id) from cobranca_criterio_linha ccl where ccl.cbct_id = cc.cbct_id )");
	// consulta.append(" and i.lest_id in (select nvl(ccl.lest_id,i.lest_id) from cobranca_criterio_linha ccl where ccl.cbct_id = cc.cbct_id )");
	//
	// //abre a conexao
	// st = jdbcCon.prepareStatement(consulta.toString());
	// st.setInt(1, idRota);
	// st.setInt(2, idCriterioCobranca);
	//
	// ResultSet rs = st.executeQuery();
	//
	// while(rs.next()){
	// Object[] item = new Object[14];
	// item[0] = rs.getInt(1);
	// item[1] = rs.getInt(2);
	// item[2] = rs.getInt(3);
	// item[3] = rs.getInt(4);
	// item[4] = rs.getInt(5);
	// item[5] = rs.getInt(6);
	// item[6] = rs.getInt(7);
	// item[7] = rs.getInt(8);
	// item[8] = rs.getShort(9);
	// item[9] = rs.getShort(10);
	// item[10] = rs.getInt(11);
	// item[11] = rs.getInt(12);
	// item[12] = rs.getShort(13);
	// item[13] = rs.getInt(14);
	//
	// retorno.add(item);
	// }
	//
	// jdbcCon.close();
	//
	// } catch (HibernateException e) {
	// // levanta a exceção para a próxima camada
	// throw new ErroRepositorioException(e, "Erro no Hibernate");
	// } catch (SQLException e) {
	// e.printStackTrace();
	// } finally {
	// // fecha a sessão
	// HibernateUtil.closeSession(session);
	// }
	//
	// return retorno;
	// }

	/**
	 * Overload do método original que consulta o máximo de registros passado por parâmetro
	 * 
	 * @author eduardo henrique
	 * @date 20/10/2008
	 * @param idRota
	 *            - Imóveis de determinada Rota
	 * @return Collection<Imovel>
	 * @author eduardo henrique
	 * @date 08/01/2009
	 *       Alteração no método para não realizar mais a paginação do hibernate na consulta
	 *       inicial, pois da forma como é realizada é inadequada para o Cache do Oracle.
	 * @throws ErroRepositorioException
	 */
	public Collection<Imovel> pesquisarImoveisPorRotaComLocalidade(Integer idRota) throws ErroRepositorioException{

		Collection retorno = null;

		String consulta;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			consulta = "select im " // 0
							+ "from Imovel im " + "inner join im.rota rt "
							// + "inner join im.setorComercial scm "
							+ "inner join im.localidade loc " + "where rt.id = :idRota ";

			retorno = (Collection) session.createQuery(consulta).setInteger(

			"idRota", idRota).list();

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
	 * [UC0978] Associar Tarifa de Consumo a Imóveis
	 * 
	 * @author Rômulo Aurélio
	 * @created 19/12/2006
	 * @param idLocalidadeInicial
	 *            ,
	 *            idLocalidadeFinal, codigoSetorComercialInicial,
	 *            codigoSetorComercialFinal, quadraInicial, quadraFinal,
	 *            loteInicial, loteFinal, subLoteInicial, subLoteFinal,
	 *            idTarifaAnterior
	 * @return
	 * @throws ErroRepositorioException
	 */

	public String criarCondicionaisImovelTarifaConsumo(

	String idLocalidadeInicial, String idLocalidadeFinal,

	String codigoSetorComercialInicial,

	String codigoSetorComercialFinal, String quadraInicial,

	String quadraFinal, String loteInicial, String loteFinal,

	String subLoteInicial, String subLoteFinal, String idTarifaAnterior){

		String hql = " WHERE ";

		if(idLocalidadeInicial != null && !idLocalidadeInicial.equals("")){

			hql = hql + " loc.id >= " + idLocalidadeInicial + " and ";

		}

		if(idLocalidadeFinal != null && !idLocalidadeFinal.equals("")){

			hql = hql + " loc.id <= " + idLocalidadeFinal + " and ";

		}

		if(codigoSetorComercialInicial != null

		&& !codigoSetorComercialInicial.equals("")){

			hql = hql + " sc.id >= " + codigoSetorComercialInicial

			+ " and ";

		}

		if(codigoSetorComercialFinal != null

		&& !codigoSetorComercialFinal.equals("")){

			hql = hql + " sc.id <= " + codigoSetorComercialFinal + " and ";

		}

		if(quadraInicial != null && !quadraInicial.equals("")){

			hql = hql + " quadra.id >= " + quadraInicial + " and ";

		}

		if(quadraFinal != null && !quadraFinal.equals("")){

			hql = hql + " quadra.id <= " + quadraFinal + " and ";

		}

		if(loteInicial != null && !loteInicial.equals("")){

			hql = hql + " imov.lote >= " + loteInicial + " and ";

		}

		if(loteFinal != null && !loteFinal.equals("")){

			hql = hql + " imov.lote <= " + loteFinal + " and ";

		}

		if(subLoteInicial != null && !subLoteInicial.equals("")){

			hql = hql + " imov.subLote >= " + subLoteInicial + " and ";

		}

		if(subLoteFinal != null && !subLoteFinal.equals("")){

			hql = hql + " imov.subLote <= " + subLoteFinal + " and ";

		}

		if(idTarifaAnterior != null && !idTarifaAnterior.equals("")){

			hql = hql + " tarifaConsumo = " + idTarifaAnterior;

		}

		return hql;

	}

	public Collection pesquisarImoveisTarifaConsumo(String idLocalidadeInicial,

	String idLocalidadeFinal, String codigoSetorComercialInicial,

	String codigoSetorComercialFinal, String quadraInicial,

	String quadraFinal, String loteInicial, String loteFinal,

	String subLoteInicial, String subLoteFinal, String idTarifaAnterior)

	throws ErroRepositorioException{

		// cria a coleção de retorno

		Collection retorno = null;

		// Query

		String consulta = "";

		// obtém a sessão

		String condicionais = this.criarCondicionaisImovelTarifaConsumo(

		idLocalidadeInicial, idLocalidadeFinal,

		codigoSetorComercialInicial, codigoSetorComercialFinal,

		quadraInicial, quadraFinal, loteInicial, loteFinal,

		subLoteInicial, subLoteFinal, idTarifaAnterior);

		Session session = HibernateUtil.getSession();

		try{

			consulta = "SELECT imov.id " + " FROM Imovel imov "

			+ " INNER JOIN imov.localidade loc "

			+ " INNER JOIN imov.setorComercial sc "

			+ " INNER JOIN imov.quadra quadra "

			+ " INNER JOIN imov.consumoTarifa tarifaConsumo "

			+ condicionais;

			retorno = (Collection) session.createQuery(consulta).list();

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
	 * Atualiza a tarifa de consumo de um ou mais imoveis
	 * [UC0378] Associar Tarifa de Consumo a Imóveis
	 * 
	 * @author Rômulo Aurelio
	 * @created 19/12/2006
	 * @author Hugo Lima
	 * @date 06/03/2012
	 *       Alteracao na consulta para atualizacao por mais dois novos campos (tarifaEspecial,
	 *       dataValidadeTarifaEspecial)
	 * @param matricula
	 * @param tarifaAtual
	 * @param tarifaEspecial
	 * @param dataValidadeTarifaEspecial
	 * @param colecaoImoveis
	 * @throws ErroRepositorioException
	 */
	public void atualizarImoveisTarifaConsumo(String matricula, String tarifaAtual, String tarifaEspecial, Date dataValidadeTarifaEspecial,
					Collection colecaoImoveis) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			Object obj = null;
			if(colecaoImoveis != null && colecaoImoveis.size() > 0){
				obj = colecaoImoveis.iterator().next();
			}

			String update;

			List colecaoAuxiliar = new ArrayList();

			colecaoAuxiliar.addAll(colecaoImoveis);

			int i = 0;

			Collection colecao = new ArrayList();

			while(i <= colecaoImoveis.size()){

				if(colecaoImoveis.size() - i >= 1000){

					colecao = colecaoAuxiliar.subList(i, i + 1000);

				}else{

					colecao = colecaoAuxiliar.subList(i, colecaoImoveis.size());

				}

				i = i + 1000;

				boolean isTodosParametrosRecebidos = false;
				boolean isApenasTarifaEspecial = false;

				if(!Util.isVazioOuBranco(tarifaAtual) && !Util.isVazioOuBranco(tarifaEspecial)){
					isTodosParametrosRecebidos = true;
				}else if(!Util.isVazioOuBranco(tarifaEspecial)){
					isApenasTarifaEspecial = true;
				}

				if(obj instanceof Integer){

					update = "UPDATE Imovel imov SET ";

					if(!isTodosParametrosRecebidos && !isApenasTarifaEspecial){
						// Caso exista apenas tarifaAtual
						update = update + " imov.consumoTarifa.id = :idTarifaConsumo, " + " imov.ultimaAlteracao = :ultimaAlteracao "
										+ " WHERE imov.id in (:idsImovel)";
						session.createQuery(update).setInteger("idTarifaConsumo", new Integer(tarifaAtual)).setTimestamp("ultimaAlteracao",
										new Date()).setParameterList("idsImovel", colecao).executeUpdate();
					}else if(isApenasTarifaEspecial){
						// Caso exista apenas tarifaEspecial
						update = update + " imov.consumoTarifaTemporaria.id = :idTarifaEspecial, "
										+ " imov.dataValidadeTarifaTemporaria = :dataValidadeTarifaEspecial, "
										+ " imov.ultimaAlteracao = :ultimaAlteracao " + " WHERE imov.id in (:idsImovel)";
						session.createQuery(update).setInteger("idTarifaEspecial", new Integer(tarifaEspecial)).setDate(
										"dataValidadeTarifaEspecial", dataValidadeTarifaEspecial).setTimestamp("ultimaAlteracao",
										new Date()).setParameterList("idsImovel", colecao).executeUpdate();
					}else if(isTodosParametrosRecebidos){
						// Caso todos os parametros tenham vindo preenchidos
						update = update + " imov.consumoTarifa.id = :idTarifaConsumo, "
										+ " imov.consumoTarifaTemporaria.id = :idTarifaEspecial, "
										+ " imov.dataValidadeTarifaTemporaria = :dataValidadeTarifaEspecial, "
										+ " imov.ultimaAlteracao = :ultimaAlteracao " + " WHERE imov.id in (:idsImovel)";
						session.createQuery(update).setInteger("idTarifaConsumo", new Integer(tarifaAtual)).setInteger("idTarifaEspecial",
										new Integer(tarifaEspecial)).setDate("dataValidadeTarifaEspecial", dataValidadeTarifaEspecial)
										.setTimestamp("ultimaAlteracao", new Date()).setParameterList("idsImovel", colecao).executeUpdate();
					}

				}else if(obj instanceof Imovel){

					update = "UPDATE Imovel imov SET ";

					if(!isTodosParametrosRecebidos && !isApenasTarifaEspecial){
						// Caso exista apenas tarifaAtual
						update = update + " imov.consumoTarifa.id = :idTarifaConsumo, " + " imov.ultimaAlteracao = :ultimaAlteracao "
										+ " WHERE imov in (:idsImovel)";
						session.createQuery(update).setInteger("idTarifaConsumo", new Integer(tarifaAtual)).setTimestamp("ultimaAlteracao",
										new Date()).setParameterList("idsImovel", colecao).executeUpdate();
					}else if(isApenasTarifaEspecial){
						// Caso exista apenas tarifaEspecial
						update = update + " imov.consumoTarifaTemporaria.id = :idTarifaEspecial, "
										+ " imov.dataValidadeTarifaTemporaria = :dataValidadeTarifaEspecial, "
										+ " imov.ultimaAlteracao = :ultimaAlteracao " + " WHERE imov in (:idsImovel)";
						session.createQuery(update).setInteger("idTarifaEspecial", new Integer(tarifaEspecial)).setDate(
										"dataValidadeTarifaEspecial", dataValidadeTarifaEspecial).setTimestamp("ultimaAlteracao",
										new Date()).setParameterList("idsImovel", colecao).executeUpdate();
					}else if(isTodosParametrosRecebidos){
						// Caso todos os parametros tenham vindo preenchidos
						update = update + " imov.consumoTarifa.id = :idTarifaConsumo, "
										+ " imov.consumoTarifaTemporaria.id = :idTarifaEspecial, "
										+ " imov.dataValidadeTarifaTemporaria = :dataValidadeTarifaEspecial, "
										+ " imov.ultimaAlteracao = :ultimaAlteracao " + " WHERE imov in (:idsImovel)";
						session.createQuery(update).setInteger("idTarifaConsumo", new Integer(tarifaAtual)).setInteger("idTarifaEspecial",
										new Integer(tarifaEspecial)).setDate("dataValidadeTarifaEspecial", dataValidadeTarifaEspecial)
										.setTimestamp("ultimaAlteracao", new Date()).setParameterList("idsImovel", colecao).executeUpdate();
					}
				}
			}
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
	 * [UC0054] - Inserir Dados Tarifa Social
	 * Atualiza o perfil do imóvel para o perfil normal
	 * 
	 * @date 04/01/2007
	 * @author Rafael Corrêa
	 * @throws ErroRepositorioException
	 */

	public void atualizarImovelPerfilNormal(Integer idImovel, boolean manter)

	throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		String update;

		try{

			update = "UPDATE Imovel imov " + "SET imov.imovelPerfil.id = "

			+ ImovelPerfil.NORMAL.toString() + ",";



			if(!manter){
				update = update + " imov.consumoTarifa.id = ";

				update = update + ConsumoTarifa.CONSUMO_NORMAL.toString() + ",";

				update = update + " imov.consumoTarifaTemporaria.id = null" + ",";
			}

			if(manter){
				update = update + " imov.dataValidadeTarifaTemporaria = null" + ",";
			}

			update = update + " imov.faturamentoSituacaoTipo.id = null "

			+ "where imov.id = :idImovel ";

			session.createQuery(update).setInteger("idImovel", idImovel)

			.executeUpdate();

		}catch(HibernateException e){

			e.printStackTrace();

			throw new ErroRepositorioException("Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

	}

	/**
	 * [UC0490] - Informar Situação de Cobrança
	 * Pesquisa o imóvel com a situação da ligação de água e a de esgoto
	 * 
	 * @date 13/01/2007
	 * @author Rafael Corrêa
	 * @throws ErroRepositorioException
	 */

	public Imovel pesquisarImovelComSituacaoAguaEsgoto(Integer idImovel)

	throws ErroRepositorioException{

		// cria a coleção de retorno

		Imovel imovel = null;

		// Query

		String consulta = "";

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			consulta = "SELECT imov "

			+ " FROM Imovel imov "

			+ " INNER JOIN FETCH imov.ligacaoAguaSituacao ligAguaSit "

			+ " INNER JOIN FETCH imov.ligacaoEsgotoSituacao ligEsgotoSit "

			+ " WHERE " + " imov.id = :idImovel";

			imovel = (Imovel) session.createQuery(consulta).setInteger(

			"idImovel", idImovel).setMaxResults(1).uniqueResult();

			// erro no hibernate

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

		// retorna o imóvel

		return imovel;

	}

	/**
	 * [UC0069] - Manter Dados da Tarifa Social
	 * [FS0006] - Verificar número de IPTU
	 * Verifica se já existe outro imóvel ou economia com o mesmo número de IPTU
	 * no mesmo município
	 * 
	 * @date 13/01/2007
	 * @author Rafael Corrêa
	 * @throws ErroRepositorioException
	 */

	public Integer verificarNumeroIptu(BigDecimal numeroIptu, Integer idImovel,

	Integer idImovelEconomia, Integer idMunicipio)

	throws ErroRepositorioException{

		// cria a coleção de retorno

		Integer idImovelRetorno = null;

		// Query

		String consulta = "";

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			if(idImovel != null){

				consulta = "SELECT imov.imov_id as idImovel "

				+ " FROM imovel imov " + " INNER JOIN "

				+ " setor_comercial sc "

				+ " on imov.stcm_id = sc.stcm_id " + " WHERE "

				+ " imov.imov_nniptu = :numeroIptu "

				+ " AND sc.muni_id = :idMunicipio "

				+ " AND imov.imov_id <> :idImovel " + " UNION "

				+ " SELECT imovel.imov_id as idImovel "

				+ " FROM imovel_economia imovEcon "

				+ " INNER JOIN " + " imovel imovel "

				+ " on imovEcon.imov_id = imovel.imov_id "

				+ " INNER JOIN " + " setor_comercial sc "

				+ " on imovel.stcm_id = sc.stcm_id " + " WHERE "

				+ " imovEcon.imec_nniptu = :numeroIptu "

				+ " AND sc.muni_id = :idMunicipio ";

				idImovelRetorno = (Integer) session.createSQLQuery(consulta)

				.addScalar("idImovel", Hibernate.INTEGER)

				.setBigDecimal("numeroIptu", numeroIptu).setInteger(

				"idImovel", idImovel).setInteger("idMunicipio",

				idMunicipio).setMaxResults(1).uniqueResult();

			}else{

				consulta = "SELECT imovel.imov_id as idImovel "

				+ " FROM imovel_economia imovEcon "

				+ " INNER JOIN " + " imovel imovel "

				+ " on imovEcon.imov_id = imovel.imov_id "

				+ " INNER JOIN " + " setor_comercial sc "

				+ " on imovel.stcm_id = sc.stcm_id " + " WHERE "

				+ " imovEcon.imec_nniptu = :numeroIptu "

				+ " AND sc.muni_id = :idMunicipio "

				+ " AND imovEcon.imec_id <> :idImovelEconomia "

				+ " UNION " + " SELECT imov.imov_id as idImovel "

				+ " FROM imovel imov " + " INNER JOIN "

				+ " setor_comercial sc "

				+ " on imov.stcm_id = sc.stcm_id " + " WHERE "

				+ " imov.imov_nniptu = :numeroIptu "

				+ " AND sc.muni_id = :idMunicipio ";

				idImovelRetorno = (Integer) session.createSQLQuery(consulta)

				.addScalar("idImovel", Hibernate.INTEGER)

				.setBigDecimal("numeroIptu", numeroIptu).setInteger(

				"idImovelEconomia", idImovelEconomia)

				.setInteger("idMunicipio", idMunicipio)

				.setMaxResults(1).uniqueResult();

			}

			// erro no hibernate

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

		// retorna o id do imóvel

		return idImovelRetorno;

	}

	/**
	 * [UC0069] - Manter Dados da Tarifa Social
	 * [FS0007] - Verificar número de contrato da companhia de energia elétrica
	 * Verifica se já existe outro imóvel ou economia com o mesmo número de
	 * contrato da companhia elétrica
	 * 
	 * @date 18/01/2007
	 * @author Rafael Corrêa
	 * @throws ErroRepositorioException
	 */

	public Integer verificarNumeroCompanhiaEletrica(

	Long numeroCompanhiaEletrica, Integer idImovel,

	Integer idImovelEconomia) throws ErroRepositorioException{

		// cria a coleção de retorno

		Integer idImovelRetorno = null;

		// Query

		String consulta = "";

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			if(idImovel != null){

				consulta = "SELECT imov.imov_id as idImovel " + " FROM imovel imov " + " WHERE "
								+ " imov.imov_nncontratoenergia = :numeroCompanhiaEletrica " + " AND imov.imov_id <> :idImovel "
								+ " UNION " + " SELECT imovel.imov_id as idImovel " + " FROM imovel_economia imovEcon "
								+ " INNER JOIN imovel imovel " + " on imovEcon.imov_id = imovel.imov_id " + " WHERE "
								+ " imovEcon.imec_nncontratoenergia = :numeroCompanhiaEletrica ";

				idImovelRetorno = (Integer) session.createSQLQuery(consulta).addScalar("idImovel", Hibernate.INTEGER).setLong(
								"numeroCompanhiaEletrica", numeroCompanhiaEletrica).setInteger("idImovel", idImovel).setMaxResults(1)
								.uniqueResult();

			}else{

				consulta = " SELECT imovel.imov_id as idImovel " + " FROM imovel_economia imovEcon " + " INNER JOIN imovel imovel "
								+ " on imovEcon.imov_id = imovel.imov_id " + " WHERE "
								+ " imovEcon.imec_nncontratoenergia = :numeroCompanhiaEletrica "
								+ " AND imovEcon.imec_id <> :idImovelEconomia " + " UNION " + " SELECT imov.imov_id as idImovel "
								+ " FROM imovel imov " + " WHERE " + " imov.imov_nncontratoenergia = :numeroCompanhiaEletrica ";

				idImovelRetorno = (Integer) session.createSQLQuery(consulta).addScalar("idImovel", Hibernate.INTEGER).setLong(
								"numeroCompanhiaEletrica", numeroCompanhiaEletrica).setInteger("idImovelEconomia", idImovelEconomia)
								.setMaxResults(1).uniqueResult();

			}

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

		// retorna o id do imóvel

		return idImovelRetorno;

	}

	/**
	 * Obtém o quantidade de economias da subCategoria por imovel
	 * 
	 * @param idImovel
	 *            O identificador do imóvel
	 * @return Coleção de SubCategorias
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */

	public Collection obterQuantidadeEconomiasSubCategoria(Integer idImovel)

	throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = "select sb.id, " // 0

							+ "sb.codigo," // 1

							+ "sb.descricao, "// 2

							+ "sum(isb.quantidadeEconomias), "// 3

							+ "sb.codigoTarifaSocial, "// 4

							+ "sb.numeroFatorFiscalizacao, "// 5

							+ "sb.indicadorTarifaConsumo, " + "c.id, " + "c.descricao "

							+ "from ImovelSubcategoria isb "

							+ "inner join isb.comp_id.subcategoria sb "

							+ "inner join sb.categoria c "

							+ "inner join sb.categoria  "

							+ "where isb.comp_id.imovel.id = :idImovel "

							+ "group by sb.id, "

							+ "sb.codigo," + "sb.descricao, "

							+ "sb.codigoTarifaSocial, "

							+ "sb.numeroFatorFiscalizacao, "

							+ "sb.indicadorTarifaConsumo, " + "c.id, " + "c.descricao,"

							+ "isb.comp_id.imovel.id ";

			// + "having  ";

			retorno = session.createQuery(consulta).setInteger("idImovel",

			idImovel).list();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	/**
	 * Obtém o quantidade de economias da subCategoria por imovel
	 * 
	 * @autor Rômulo Aurélio
	 * @param idImovel
	 *            O identificador do imóvel
	 * @return Coleção de imovelSubCategorias
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 * @date 08/02/2007
	 */

	public Collection pesquisarImovelSubcategorias(Integer idImovel)

	throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			String consulta = "select imovSub "

			+ "from ImovelSubcategoria imovSub "

			+ "left join fetch imovSub.imovelEconomias "

			+ "left join fetch imovSub.comp_id.imovel imovel "

			+ "left join fetch imovel.imovelPerfil imovelPerfil "

			+ "left join fetch imovSub.comp_id.subcategoria subcategoria "

			+ "left join fetch subcategoria.categoria "

			+ "where imovel.id = :idImovel";

			retorno = new ArrayList(new CopyOnWriteArraySet(session

			.createQuery(consulta).setInteger("idImovel", idImovel)

			.list()));

			// ((ImovelSubcategoria)retorno.iterator().next()).getImovelEconomias().size();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	/**
	 * Obtém o quantidade de economias da subCategoria por imovel
	 * 
	 * @autor Rômulo Aurélio
	 * @param idImovel
	 *            O identificador do imóvel
	 * @return Coleção de imovelSubCategorias
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 * @date 08/02/2007
	 */

	// public Collection pesquisarImoveisEconomias(Integer idImovel)
	// throws ErroRepositorioException {
	//
	// Collection retorno = null;
	// Session session = HibernateUtil.getSession();
	//
	// try {
	// String consulta = "select imovSub"
	// + "from ImovelSubcategoria imovSub "
	// + "left join fetch imovSub.comp_id.imovel imovel "
	// + "left join fetch imovSub.comp_id.subcategoria subcategoria "
	// + "where imovel.id = :idImovel";
	//
	// retorno = session.createQuery(consulta).setInteger("idImovel",
	// idImovel).list();
	//
	// } catch (HibernateException e) {
	// throw new ErroRepositorioException(e, "Erro no Hibernate");
	// } finally {
	// HibernateUtil.closeSession(session);
	// }
	//
	// return retorno;
	//
	// }
	/**
	 * @date 21/02/2007
	 * @author Vivianne Sousa, Ivan Sérgio
	 * @alteracao: Adicionado LigacaoEsgoto; LigacaoEsgotoSituacao, LigacaoAguaSituacao,
	 *             hidrometroLocalInstalacao, hidrometroProtecao
	 * @throws ErroRepositorioException
	 */

	public Imovel pesquisarImovel(Integer idImovel)

	throws ErroRepositorioException{

		// cria a coleção de retorno

		Imovel imovel = null;

		// Query

		String consulta = "";

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			consulta = "SELECT imov " + " FROM Imovel imov "

			+ " LEFT JOIN FETCH imov.imovelPerfil imovelPerfil "

			+ " LEFT JOIN FETCH imov.localidade lo"

			+ " LEFT JOIN FETCH imov.setorComercial sc"

			+ " LEFT JOIN FETCH imov.rota rt"

			+ " LEFT JOIN FETCH	imov.quadra qu"

			+ " LEFT JOIN FETCH qu.rota rota"

			+ " LEFT JOIN FETCH rota.faturamentoGrupo fg"

			+ " LEFT JOIN FETCH imov.logradouroBairro logBairro"

			+ " LEFT JOIN FETCH logBairro.bairro bairro"

			+ " LEFT JOIN FETCH logBairro.logradouro logradouro"

			+ " LEFT JOIN FETCH logradouro.logradouroTitulo logtit"

			+ " LEFT JOIN FETCH logradouro.logradouroTipo logtip"

			+ " LEFT JOIN FETCH imov.enderecoReferencia"

			+ " LEFT JOIN FETCH imov.logradouroCep logCep"

			+ " LEFT JOIN FETCH logCep.cep"

			+ " LEFT JOIN FETCH logCep.logradouro logradouroCep"

			+ " LEFT JOIN FETCH logradouroCep.municipio municipioCep"

			+ " LEFT JOIN FETCH municipioCep.unidadeFederacao"

			+ " LEFT JOIN FETCH logradouroCep.logradouroTitulo"

			+ " LEFT JOIN FETCH logradouroCep.logradouroTipo"

			+ " LEFT JOIN FETCH bairro.municipio"

			+ " LEFT JOIN FETCH imov.ligacaoEsgoto lesg"

			+ " LEFT JOIN FETCH imov.ligacaoEsgotoSituacao lest"

			+ " LEFT JOIN FETCH imov.ligacaoAgua lagu"

			+ " LEFT JOIN FETCH imov.ligacaoAguaSituacao last"

			+ " LEFT JOIN FETCH imov.hidrometroInstalacaoHistorico imovHih"

			+ " LEFT JOIN FETCH lagu.hidrometroInstalacaoHistorico hih"

			+ " LEFT JOIN FETCH hih.hidrometroLocalInstalacao hil"

			+ " LEFT JOIN FETCH hih.hidrometroProtecao hip"

			+ " LEFT JOIN FETCH hih.hidrometro"

			+ " LEFT JOIN FETCH imov.faturamentoTipo"

			+ " LEFT JOIN FETCH imov.imovelContaEnvio imContaEnvio"

			+ " LEFT JOIN FETCH imov.esgotamento esgot"

			+ " LEFT JOIN FETCH imov.pavimentoRua pavRua"

			+ " LEFT JOIN FETCH imov.pocoTipo pocoTp"

			+ " LEFT JOIN FETCH imov.fonteAbastecimento fonteAbas"

			+ " LEFT JOIN FETCH imov.padraoConstrucao pdConstrucao"

			+ " LEFT JOIN FETCH imov.consumoTarifa consTarifa"

			+ " LEFT JOIN FETCH imov.imovelAguaParaTodos imovelAguaParaTodos"

			+ " LEFT JOIN FETCH lo.unidadeNegocio unidNeg"

			+ " WHERE "

			+ " imov.id = :idImovel";

			imovel = (Imovel) session.createQuery(consulta).setInteger(

			"idImovel", idImovel).setMaxResults(1).uniqueResult();

			// erro no hibernate

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

		// retorna o imóvel

		return imovel;

	}

	/**
	 * @date 28/01/2012
	 * @author Carlos Chrystian
	 *         @ [UC0074] Alterar Inscrição de Imóvel
	 *         @ [SB0002] - Emitir Relatório dos Imóveis da Inscrição Origem com Mudança da Quadra
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImovelPorInscricao(Integer idLocalidade, Integer codigoSetorComercial, Integer nnQuadra, Short nnLote,
					int indicadorExclusao)

	throws ErroRepositorioException{

		// cria a coleção de retorno

		Collection retorno = null;

		// Query

		String consulta = "";

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			consulta = "SELECT imovel.id, imovel.lote, imovel.subLote, loca.id, loca.descricao, sc.id, sc.codigo, sc.descricao,"
							+ " quadra.id, quadra.numeroQuadra, rota.id, rota.codigo, rtqdra.id"

							+ " FROM Imovel imovel "

							+ " INNER JOIN imovel.localidade loca"

							+ " INNER JOIN imovel.setorComercial sc"

							+ " INNER JOIN imovel.quadra quadra"

							+ " INNER JOIN quadra.rota rtqdra"

							+ " INNER JOIN imovel.rota rota"

							+ " WHERE "

							+ "(imovel.indicadorExclusao IS NULL OR imovel.indicadorExclusao != "

							+ indicadorExclusao + ")";

			if(idLocalidade != null){
				consulta = consulta + " AND imovel.localidade.id = " + idLocalidade.intValue();
			}

			if(codigoSetorComercial != null){
				consulta = consulta + " AND imovel.setorComercial.codigo = " + codigoSetorComercial.intValue();
			}

			if(nnQuadra != null){
				consulta = consulta + " AND imovel.quadra.numeroQuadra = " + nnQuadra.intValue();
			}

			if(nnLote != null){
				consulta = consulta + " AND imovel.lote = " + nnLote.shortValue();
			}

			retorno = session.createQuery(consulta).list();

			// erro no hibernate

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

		// retorna o imóvel

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

	public void atualizarLogradouroBairro(

	LogradouroBairro logradouroBairroAntigo,

	LogradouroBairro logradouroBairroNovo)

	throws ErroRepositorioException{

		String consulta = "";

		Session session = HibernateUtil.getSession();

		try{

			consulta = "UPDATE gcom.cadastro.imovel.Imovel SET "

			+ "lgbr_id = :idLogradouroBairroNovo, imov_tmultimaalteracao = :ultimaAlteracao "

			+ "WHERE lgbr_id = :idLogradouroBairroAntigo ";

			session.createQuery(consulta).setInteger("idLogradouroBairroNovo",

			logradouroBairroNovo.getId()).setTimestamp(

			"ultimaAlteracao", new Date()).setInteger(

			"idLogradouroBairroAntigo", logradouroBairroAntigo.getId())

			.executeUpdate();

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

	public void atualizarLogradouroCep(LogradouroCep logradouroCepAntigo,

	LogradouroCep logradouroCepNovo) throws ErroRepositorioException{

		String consulta = "";

		Session session = HibernateUtil.getSession();

		try{

			consulta = "UPDATE gcom.cadastro.imovel.Imovel SET "

			+ "lgcp_id = :idLogradouroCepNovo, imov_tmultimaalteracao = :ultimaAlteracao "

			+ "WHERE lgcp_id = :idLogradouroCepAntigo ";

			session.createQuery(consulta).setInteger("idLogradouroCepNovo",

			logradouroCepNovo.getId()).setTimestamp("ultimaAlteracao",

			new Date()).setInteger("idLogradouroCepAntigo",

			logradouroCepAntigo.getId()).executeUpdate();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

	}

	/**
	 * [UC0302] Gerar Débitos a Cobrar de Acréscimos por Impontualidade
	 * Pequisa o identificador de cobranca de acréscimo pro impontualidade para
	 * o imóvel do cliente responsável.
	 * 
	 * @author Pedro Alexandre
	 * @date 26/02/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Short obterIndicadorGeracaoAcrescimosClienteImovel(Integer idImovel)

	throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		Short retorno = null;

		try{

			String consulta = "select clie.indicadorCobrancaAcrescimos "

			+ "from ClienteImovel clim " + "inner join clim.imovel imov "

			+ "inner join clim.cliente clie "

			+ "where imov.id = :idImovel "

			+ "and clim.clienteRelacaoTipo.id = :idRelacao "

			+ "and clim.dataFimRelacao is null";

			retorno = (Short) session.createQuery(consulta).setParameter(

			"idImovel", idImovel).setParameter("idRelacao",

			ClienteRelacaoTipo.RESPONSAVEL).uniqueResult();

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
	 * [UC0488] - Informar Retorno Ordem de Fiscalização
	 * Obter o consumo médio como não medido para o imóvel passado
	 * 
	 * @author Raphael Rossiter
	 * @date 06/03/2007
	 */

	public Integer obterConsumoMedioNaoMedidoImovel(Imovel imovel)

	throws ErroRepositorioException{

		Integer retorno = 0;

		Short consumoNaoMedido = null;

		Session session = HibernateUtil.getSession();

		String consulta;

		try{

			consulta = "select sum(imsb.quantidadeEconomias * scat.numeroFatorFiscalizacao) "

			+ "from gcom.cadastro.imovel.ImovelSubcategoria  imsb "

			+ "inner join imsb.comp_id.imovel imov "

			+ "inner join imsb.comp_id.subcategoria scat "

			+ "where imov.id = :idImovel ";

			consumoNaoMedido = ((Number) session.createQuery(consulta)

			.setInteger("idImovel", imovel.getId().intValue())

			.setMaxResults(1).uniqueResult()).shortValue();

			if(consumoNaoMedido != null){

				retorno = consumoNaoMedido.intValue();

			}

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
	 * Obter a situação de cobrança para o imóvel passado
	 * 
	 * @author Vivianne Sousa
	 * @date 07/03/2007
	 */

	public String obterSituacaoCobrancaImovel(Integer idImovel)

	throws ErroRepositorioException{

		String retorno = "";

		Session session = HibernateUtil.getSession();

		String consulta;

		try{

			consulta = "select cobSit.descricao "

			+ "from ImovelCobrancaSituacao imovCobSit "

			+ "left join imovCobSit.cobrancaSituacao cobSit "

			+ "left join imovCobSit.imovel imovel "

			+ "where imovel.id = :idImovel "

			+ " and imovCobSit.dataRetiradaCobranca is null ";

			retorno = (String) session.createQuery(consulta).setInteger(

			"idImovel", idImovel).setMaxResults(1).uniqueResult();

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
	 * Pesquisa uma coleção de imóveis
	 * 
	 * @author eduardo henrique
	 * @date 08/01/2009
	 *       Adição do filtro de Bairro na seleção dos Imóveis
	 * @param idLocalidade
	 *            parametros para a consulta
	 * @param idSetorComercial
	 *            parametros para a consulta
	 * @param idQuadra
	 *            parametros para a consulta
	 * @param lote
	 *            Descrição do parâmetro
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */

	public Collection pesquisarColecaoImovel(

	FiltrarImovelInserirManterContaHelper filtro)

	throws ErroRepositorioException{

		Integer localidadeOrigemID = filtro.getLocalidadeOrigemID();

		Integer setorComercialOrigemID = filtro.getSetorComercialOrigemID();

		Integer quadraOrigemID = filtro.getQuadraOrigemID();

		Short loteOrigem = filtro.getLoteOrigem();

		Short subLoteOrigem = filtro.getSubLoteOrigem();

		Integer localidadeDestinoID = filtro.getLocalidadeDestinoID();

		Integer setorComercialDestinoID = filtro.getSetorComercialDestinoID();

		Integer quadraDestinoID = filtro.getQuadraDestinoID();

		Short loteDestino = filtro.getLoteDestino();

		Short subLoteDestino = filtro.getSubLoteDestino();

		Collection colecaoQuadraSelecionada = filtro

		.getColecaoQuadraSelecionada();

		Integer codigoRotaOrigem = filtro.getCodigoRotaOrigem();

		Integer codigoRotaDestino = filtro.getCodigoRotaDestino();

		Integer sequencialRotaOrigem = filtro.getSequencialRotaOrigem();

		Integer sequencialRotaDestino = filtro.getSequencialRotaDestino();

		/*
		 * Colocado por Raphael Rossiter em 02/08/2007
		 * OBJETIVO: Acrescentar o parâmetro grupo de faturamento para o filtro
		 * de manutenção de várias contas.
		 */

		Integer idGrupoFaturamento = filtro.getIdGrupoFaturamento();

		// cria a coleção de retorno

		Collection retorno = null;

		// Query

		String consulta;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			consulta = "select imovel.id "

			+ "FROM gcom.cadastro.imovel.Imovel  imovel "

			+ "LEFT JOIN imovel.faturamentoSituacaoTipo ftst "

			+ "INNER JOIN imovel.rota rota "

			+ "INNER JOIN rota.faturamentoGrupo ftgr " + "WHERE 1 = 1 ";

			if(localidadeOrigemID != null && localidadeDestinoID != null){

				consulta = consulta + "and imovel.localidade.id between "

				+ localidadeOrigemID.intValue() + " and "

				+ localidadeDestinoID.intValue();

			}

			if(setorComercialOrigemID != null

			&& setorComercialDestinoID != null){

				consulta = consulta + " and imovel.setorComercial.id between "

				+ setorComercialOrigemID.intValue() + " and "

				+ setorComercialDestinoID.intValue();

			}

			if(colecaoQuadraSelecionada != null

			&& !colecaoQuadraSelecionada.isEmpty()){

				consulta = consulta

				+ " and imovel.quadra.id in (:colecaoQuadraSelecionada)";

			}else if(quadraOrigemID != null && quadraDestinoID != null){

				consulta = consulta + " and imovel.quadra.id between "

				+ quadraOrigemID.intValue() + " and "

				+ quadraDestinoID.intValue();

			}

			if(loteOrigem != 0 && loteDestino != 0){

				consulta = consulta + " and imovel.lote between "

				+ loteOrigem.intValue() + " and "

				+ loteDestino.intValue();

			}

			if(subLoteOrigem != 0 && subLoteDestino != 0){

				consulta = consulta + " and imovel.subLote between "

				+ subLoteOrigem.intValue() + " and "

				+ subLoteDestino.intValue();

			}

			if(codigoRotaOrigem != null && codigoRotaDestino != null){

				consulta = consulta + " and rota.codigo between "

				+ codigoRotaOrigem.shortValue() + " and "

				+ codigoRotaDestino.shortValue();

			}

			if(sequencialRotaOrigem != null && sequencialRotaDestino != null){

				consulta = consulta

				+ " and imovel.numeroSequencialRota between "

				+ sequencialRotaOrigem.intValue() + " and "

				+ sequencialRotaDestino.intValue();

			}

			/*
			 * Colocado por Raphael Rossiter em 02/08/2007
			 * OBJETIVO: Acrescentar o parâmetro grupo de faturamento para o
			 * filtro de manutenção de várias contas.
			 */

			if(idGrupoFaturamento != null){

				consulta = consulta + " and ftgr.id = "

				+ idGrupoFaturamento.intValue();

			}

			if(filtro.getNomeBairro() != null){
				consulta += " and imovel.logradouroBairro.bairro.nome like '%" + filtro.getNomeBairro() + "%'";
			}

			if(colecaoQuadraSelecionada != null

			&& !colecaoQuadraSelecionada.isEmpty()){

				retorno = session.createQuery(consulta).setParameterList(

				"colecaoQuadraSelecionada", colecaoQuadraSelecionada)

				.list();

			}else{

				retorno = session.createQuery(consulta).list();

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

	/**
	 * Pesquisa uma coleção de imóveis do cliente
	 * 
	 * @author Ana Maria
	 * @date 20/03/2007
	 */

	public Collection pesquisarColecaoImovelCliente(Integer codigoCliente,

	Integer relacaoTipo) throws ErroRepositorioException{

		// cria a coleção de retorno

		Collection retorno = null;

		// Query

		String consulta;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			consulta = "select imov.id " + "from ClienteImovel clim "

			+ "inner join clim.cliente clie "

			+ "inner join clim.imovel imov "

			+ "WHERE clie.id = :codigoCliente ";

			if(relacaoTipo != null){

				consulta = consulta + " and clim.clienteRelacaoTipo = "

				+ relacaoTipo.intValue();

			}

			retorno = session.createQuery(consulta).setInteger("codigoCliente",

			codigoCliente).list();

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

	public Integer pesquisarContaMotivoRevisao(Integer idImovel)

	throws ErroRepositorioException{

		Integer retorno = null;

		// Query

		String consulta;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			consulta = "select cmrv.id from ImovelCobrancaSituacao ics "

			+ "inner join ics.imovel im "

			+ "inner join ics.contaMotivoRevisao cmrv "

			+ "where im.id = :idImovel and ics.dataRetiradaCobranca is null";

			retorno = (Integer) session.createQuery(consulta).setInteger(

			"idImovel", idImovel).setMaxResults(1).uniqueResult();

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

	public Collection obterSubCategoriasPorCategoria(Integer idCategoria,

	Integer idImovel) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			String hql = "select "

			+ " new gcom.cadastro.imovel.ImovelSubcategoria("

			+ "  sub.id,"

			+ "  sub.quantidadeEconomias,"

			+ "  sub.ultimaAlteracao ) "

			+ "from"

			+ "  gcom.cadastro.imovel.ImovelSubcategoria sub "

			+ "where"

			+ "  sub.comp_id.subcategoria.categoria.id = :idCategoria and "

			+ "  sub.comp_id.imovel.id = :idImovel";

			retorno = session.createQuery(hql).setInteger("idCategoria",

			idCategoria).setInteger("idImovel", idImovel).list();

		}catch(HibernateException e){

			throw new ErroRepositorioException("Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	/**
	 * [UC0623] - GERAR RESUMO DE METAS EXECUTDO NO MÊS(CAERN)
	 * 
	 * @author Sávio Luiz
	 * @date 08/08/2007
	 */

	public Object[] obterSubCategoriasComCodigoGrupoPorCategoria(

	Integer idCategoria, Integer idImovel)

	throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			String hql = "select "

			+ "  sub.comp_id.subcategoria.id,"

			+ "  sub.quantidadeEconomias,"

			+ "  sub.ultimaAlteracao,  "

			+ "  sub.comp_id.subcategoria.codigoGrupoSubcategoria "

			+ "from"

			+ "  gcom.cadastro.imovel.ImovelSubcategoria sub "

			+ "where"

			+ "  sub.comp_id.subcategoria.categoria.id = :idCategoria and "

			+ "  sub.comp_id.imovel.id = :idImovel"

			+ "  order by 2,4 desc";

			retorno = (Object[]) session.createQuery(hql).setInteger("idCategoria",

			idCategoria).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException("Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	/**
	 * [UC0150] - Retificar Conta
	 * 
	 * @author Raphael Rossiter
	 * @date 16/04/2007
	 */

	public Collection obterQuantidadeEconomiasCategoriaPorSubcategoria(

	Integer conta) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append("SELECT scat.id, scat.codigo, scat.descricao, ")

			.append("scat.codigoTarifaSocial, sum(ctcg.quantidadeEconomia), ")

			.append("scat.numeroFatorFiscalizacao, scat.indicadorTarifaConsumo, ")

			.append("cnta.id, ")

			.append("catg.id, catg.descricao, catg.descricaoAbreviada ")

			.append("FROM ContaCategoria ctcg ")

			.append("INNER JOIN ctcg.comp_id.conta cnta ")

			.append("INNER JOIN ctcg.comp_id.categoria catg ")

			.append("INNER JOIN ctcg.comp_id.subcategoria scat ")

			.append("GROUP BY scat.id, scat.codigo, scat.descricao, scat.codigoTarifaSocial, ")

			.append("scat.numeroFatorFiscalizacao, scat.indicadorTarifaConsumo, cnta.id, catg.id, catg.descricao, ")

			.append("catg.descricaoAbreviada " + "HAVING cnta.id = :contaId ")

			.append("ORDER BY catg.descricao, scat.descricao");

			retorno = session.createQuery(consulta.toString()).setInteger("contaId",

			conta.intValue()).list();

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
	 * [UC0591] - Gerar Relatório de Clientes Especiais
	 * Pesquisas os imóveis de acordo com os parâmetros da pesquisa
	 * 
	 * @author Rafael Corrêa
	 * @date 31/05/2007
	 */

	public Collection pesquisarImovelClientesEspeciaisRelatorio(

	String idUnidadeNegocio, String idGerenciaRegional,

	String idLocalidadeInicial, String idLocalidadeFinal,

	String[] idsPerfilImovel, String[] idsCategoria,

	String[] idsSubcategoria, String idSituacaoAgua,

	String idSituacaoEsgoto, String qtdeEconomiasInicial,

	String qtdeEconomiasFinal, String intervaloConsumoAguaInicial,

	String intervaloConsumoAguaFinal,

	String intervaloConsumoEsgotoInicial,

	String intervaloConsumoEsgotoFinal, String idClienteResponsavel,

	String intervaloConsumoResponsavelInicial,

	String intervaloConsumoResponsavelFinal,

	Date dataInstalacaoHidrometroInicial,

	Date dataInstalacaoHidrometroFinal,

	String[] idsCapacidadesHidrometro, String[] idsTarifasConsumo,

	Integer anoMesFaturamento, String idLeituraAnormalidade,

	String leituraAnormalidade, String idConsumoAnormalidade,

	String consumoAnormalidade, String[] idsClienteTipoEspecial) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = "SELECT greg.greg_id as idGerencia, greg.greg_nmregional as nomeGerencia, " // 0,
							// 1

							+ " loc.loca_id as idLocalidade, loc.loca_nmlocalidade as nomeLocalidade, " // 2,
							// 3

							+ " setor.stcm_cdsetorcomercial as codSetor, quadra.qdra_nnquadra as numeroQuadra, " // 4,
							// 5

							+ " imov.imov_nnlote as lote, imov.imov_nnsublote as subLote, " // 6, 7

							+ " imov.imov_id as idImovel, clieUsuario.clie_id as idClienteUsuario, " // 8,
							// 9

							+ " clieUsuario.clie_nmcliente as nomeClienteUsuario, imov.imov_qteconomia as qtdeEconomias, " // 10,

							// 11

							+ " ligAguaSit.last_dsligacaoaguasituacao as sitLigAgua, " // 12

							+ " ligEsgSit.lest_dsligacaoesgotosituacao as sitLigEsg, " // 13

							+ " hidrCap.hicp_dsabreviadahidrometrocapa as hidrCapacidade, " // 14

							+ " hidrInstHist.hidi_dtinstalacaohidrometro as dtInstalacao, " // 15

							+ " clieResponsavel.clie_id as idClienteResponsavel, " // 16

							+ " clieResponsavel.clie_nmcliente as nomeClienteResponsavel, " // 17

							+ " consHistAgua.cshi_nnconsumofaturadomes as consumoAgua, " // 18

							+ " consHistEsgoto.cshi_nnconsumofaturadomes as consumoEsgoto, " // 19

							+ " ligEsgoto.lesg_nnconsumominimoesgoto as consumoMinimoEsgoto, " // 20

							+ " consTar.cstf_dsconsumotarifa as consumoTarifa, " // 21

							+ " conta.cnta_vlagua as valorAgua, conta.cnta_vlesgoto as valorEsgoto, " // 22,

							// 23

							+ " conta.cnta_vldebitos as valorDebitos, conta.cnta_vlcreditos as valorCreditos " // 24,

							// 25

							+ " FROM imovel imov "

							+ " INNER JOIN consumo_tarifa consTar "

							+ " on consTar.cstf_id = imov.cstf_id "

							+ " INNER JOIN ligacao_agua_situacao ligAguaSit "

							+ " on ligAguaSit.last_id = imov.last_id "

							+ " INNER JOIN ligacao_esgoto_situacao ligEsgSit "

							+ " on ligEsgSit.lest_id = imov.lest_id "

							+ " INNER JOIN setor_comercial setor "

							+ " on setor.stcm_id = imov.stcm_id "

							+ " INNER JOIN quadra quadra "

							+ " on quadra.qdra_id = imov.qdra_id "

							+ " INNER JOIN localidade loc "

							+ " on loc.loca_id = imov.loca_id "

							+ " INNER JOIN gerencia_regional greg "

							+ " on greg.greg_id = loc.greg_id "

							+ " INNER JOIN cliente_imovel clieImovUsuario "

							+ " on clieImovUsuario.imov_id = imov.imov_id "

							+ " and clieImovUsuario.crtp_id = "

							+ ClienteRelacaoTipo.USUARIO.toString()

							+ " and clieImovUsuario.clim_dtrelacaofim is null "

							+ " INNER JOIN cliente clieUsuario "

							+ " on clieUsuario.clie_id = clieImovUsuario.clie_id ";

			if(idsClienteTipoEspecial != null){

				consulta = consulta

				+ " LEFT OUTER JOIN cliente_tipo_especial cles "

				+ " on clieUsuario.cles_id = cles.cles_id ";

			}

			if(idsCategoria != null || idsSubcategoria != null){

				consulta = consulta

				+ " INNER JOIN imovel_subcategoria imovSub "

				+ " on imovSub.imov_id = imov.imov_id "

				+ " INNER JOIN subcategoria subcategoria "

				+ " on subcategoria.scat_id = imovSub.scat_id ";

			}

			if((idClienteResponsavel != null && !idClienteResponsavel.trim()

			.equals(""))

			|| (intervaloConsumoResponsavelInicial != null && !intervaloConsumoResponsavelInicial

			.trim().equals(""))){

				consulta = consulta

				+ " INNER JOIN cliente_imovel clieImovResponsavel "

				+ " on clieImovResponsavel.imov_id = imov.imov_id "

				+ " and clieImovResponsavel.crtp_id = "

				+ ClienteRelacaoTipo.RESPONSAVEL.toString()

				+ " and clieImovResponsavel.clim_dtrelacaofim is null "

				+ " INNER JOIN cliente clieResponsavel "

				+ " on clieResponsavel.clie_id = clieImovResponsavel.clie_id ";

			}else{

				consulta = consulta

				+ " LEFT OUTER JOIN cliente_imovel clieImovResponsavel "

				+ " on clieImovResponsavel.imov_id = imov.imov_id "

				+ " and clieImovResponsavel.crtp_id = "

				+ ClienteRelacaoTipo.RESPONSAVEL.toString()

				+ " and clieImovResponsavel.clim_dtrelacaofim is null "

				+ " LEFT OUTER JOIN cliente clieResponsavel "

				+ " on clieResponsavel.clie_id = clieImovResponsavel.clie_id ";

			}

			if(idsCapacidadesHidrometro != null){

				consulta = consulta

				+ " INNER JOIN ligacao_agua ligAgua "

				+ " on ligAgua.lagu_id = imov.imov_id "

				+ " INNER JOIN hidrometro_instalacao_hist hidrInstHist "

				+ " on hidrInstHist.hidi_id = ligAgua.hidi_id "

				+ " INNER JOIN hidrometro hidr "

				+ " on hidr.hidr_id = hidrInstHist.hidr_id "

				+ " INNER JOIN hidrometro_capacidade hidrCap "

				+ " on hidrCap.hicp_id =  hidr.hicp_id ";

			}else{

				consulta = consulta

				+ " LEFT OUTER JOIN ligacao_agua ligAgua "

				+ " on ligAgua.lagu_id = imov.imov_id "

				+ " LEFT OUTER JOIN hidrometro_instalacao_hist hidrInstHist "

				+ " on hidrInstHist.hidi_id = ligAgua.hidi_id "

				+ " LEFT OUTER JOIN hidrometro hidr "

				+ " on hidr.hidr_id = hidrInstHist.hidr_id "

				+ " LEFT OUTER JOIN hidrometro_capacidade hidrCap "

				+ " on hidrCap.hicp_id =  hidr.hicp_id ";

			}

			if(intervaloConsumoAguaInicial != null

			&& !intervaloConsumoAguaInicial.trim().equals("")){

				consulta = consulta

				+ " INNER JOIN consumo_historico consHistAgua "

				+ " on consHistAgua.imov_id = imov.imov_id "

				+ " and consHistAgua.cshi_amfaturamento = :anoMesFaturamento "

				+ " and consHistAgua.lgti_id = "

				+ LigacaoTipo.LIGACAO_AGUA.toString();

			}else{

				consulta = consulta

				+ " LEFT OUTER JOIN consumo_historico consHistAgua "

				+ " on consHistAgua.imov_id = imov.imov_id "

				+ " and consHistAgua.cshi_amfaturamento = :anoMesFaturamento "

				+ " and consHistAgua.lgti_id = "

				+ LigacaoTipo.LIGACAO_AGUA.toString();

			}

			if(intervaloConsumoEsgotoInicial != null

			&& !intervaloConsumoEsgotoInicial.trim().equals("")){

				consulta = consulta

				+ " INNER JOIN consumo_historico consHistEsgoto "

				+ " on consHistEsgoto.imov_id =  imov.imov_id "

				+ " and consHistEsgoto.cshi_amfaturamento = :anoMesFaturamento "

				+ " and consHistEsgoto.lgti_id = "

				+ LigacaoTipo.LIGACAO_ESGOTO.toString();

			}else{

				consulta = consulta

				+ " LEFT OUTER JOIN consumo_historico consHistEsgoto "

				+ " on consHistEsgoto.imov_id =  imov.imov_id "

				+ " and consHistEsgoto.cshi_amfaturamento = :anoMesFaturamento "

				+ " and consHistEsgoto.lgti_id = "

				+ LigacaoTipo.LIGACAO_ESGOTO.toString();

			}

			if((leituraAnormalidade != null && !leituraAnormalidade.trim()

			.equals(""))

			|| (idLeituraAnormalidade != null && !idLeituraAnormalidade

			.trim().equals(""))){

				consulta = consulta

				+ " INNER JOIN medicao_historico medHist "

				+ " on medHist.lagu_id = imov.imov_id "

				+ " and medHist.mdhi_amleitura = :anoMesFaturamento ";

			}

			consulta = consulta

			+ " LEFT OUTER JOIN conta conta "

			+ " on conta.imov_id = imov.imov_id "

			+ " and cnta_amreferenciaconta = :anoMesFaturamento "

			+ " LEFT OUTER JOIN ligacao_esgoto ligEsgoto "

			+ " on ligEsgoto.lesg_id = imov.imov_id ";

			String condicionais = this

			.criarCondicionaisImovelClientesEspeciais(idUnidadeNegocio,

			idGerenciaRegional, idLocalidadeInicial,

			idLocalidadeFinal, idsPerfilImovel, idsCategoria,

			idsSubcategoria, idSituacaoAgua, idSituacaoEsgoto,

			qtdeEconomiasInicial, qtdeEconomiasFinal,

			intervaloConsumoAguaInicial,

			intervaloConsumoAguaFinal,

			intervaloConsumoEsgotoInicial,

			intervaloConsumoEsgotoFinal, idClienteResponsavel,

			intervaloConsumoResponsavelInicial,

			intervaloConsumoResponsavelFinal,

			dataInstalacaoHidrometroInicial,

			dataInstalacaoHidrometroFinal,

			idsCapacidadesHidrometro, idsTarifasConsumo,

			anoMesFaturamento, idLeituraAnormalidade,

			leituraAnormalidade, idConsumoAnormalidade,

			consumoAnormalidade, idsClienteTipoEspecial);

			consulta = consulta + condicionais;

			retorno = session.createSQLQuery(consulta).addScalar("idGerencia",

			Hibernate.INTEGER).addScalar("nomeGerencia",

			Hibernate.STRING).addScalar("idLocalidade",

			Hibernate.INTEGER).addScalar("nomeLocalidade",

			Hibernate.STRING).addScalar("codSetor", Hibernate.INTEGER)

			.addScalar("numeroQuadra", Hibernate.INTEGER).addScalar(

			"lote", Hibernate.SHORT).addScalar("subLote",

			Hibernate.SHORT).addScalar("idImovel",

			Hibernate.INTEGER).addScalar("idClienteUsuario",

			Hibernate.INTEGER).addScalar("nomeClienteUsuario",

			Hibernate.STRING).addScalar("qtdeEconomias",

			Hibernate.SHORT).addScalar("sitLigAgua",

			Hibernate.STRING).addScalar("sitLigEsg",

			Hibernate.STRING).addScalar("hidrCapacidade",

			Hibernate.STRING).addScalar("dtInstalacao",

			Hibernate.DATE).addScalar("idClienteResponsavel",

			Hibernate.INTEGER).addScalar(

			"nomeClienteResponsavel", Hibernate.STRING)

			.addScalar("consumoAgua", Hibernate.INTEGER).addScalar(

			"consumoEsgoto", Hibernate.INTEGER).addScalar(

			"consumoMinimoEsgoto", Hibernate.INTEGER)

			.addScalar("consumoTarifa", Hibernate.STRING).addScalar(

			"valorAgua", Hibernate.BIG_DECIMAL).addScalar(

			"valorEsgoto", Hibernate.BIG_DECIMAL).addScalar(

			"valorDebitos", Hibernate.BIG_DECIMAL).addScalar(

			"valorCreditos", Hibernate.BIG_DECIMAL).setInteger(

			"anoMesFaturamento", anoMesFaturamento).list();

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
	 * [UC0978] Associar Tarifa de Consumo a Imóveis
	 * 
	 * @author Rômulo Aurélio
	 * @created 19/12/2006
	 * @param idLocalidadeInicial
	 *            ,
	 *            idLocalidadeFinal, codigoSetorComercialInicial,
	 *            codigoSetorComercialFinal, quadraInicial, quadraFinal,
	 *            loteInicial, loteFinal, subLoteInicial, subLoteFinal,
	 *            idTarifaAnterior
	 * @return
	 * @throws ErroRepositorioException
	 */

	public String criarCondicionaisImovelClientesEspeciais(

	String idUnidadeNegocio, String idGerenciaRegional,

	String idLocalidadeInicial, String idLocalidadeFinal,

	String[] idsPerfilImovel, String[] idsCategoria,

	String[] idsSubcategoria, String idSituacaoAgua,

	String idSituacaoEsgoto, String qtdeEconomiasInicial,

	String qtdeEconomiasFinal, String intervaloConsumoAguaInicial,

	String intervaloConsumoAguaFinal,

	String intervaloConsumoEsgotoInicial,

	String intervaloConsumoEsgotoFinal, String idClienteResponsavel,

	String intervaloConsumoResponsavelInicial,

	String intervaloConsumoResponsavelFinal,

	Date dataInstalacaoHidrometroInicial,

	Date dataInstalacaoHidrometroFinal,

	String[] idsCapacidadesHidrometro, String[] idsTarifasConsumo,

	Integer anoMesFaturamento, String idLeituraAnormalidade,

	String leituraAnormalidade, String idConsumoAnormalidade,

	String consumoAnormalidade, String[] idsClienteTipoEspecial){

		String sql = " WHERE ";

		// Unidade de Negócio

		if(idUnidadeNegocio != null

		&& !idUnidadeNegocio.trim().equals(

		"" + ConstantesSistema.NUMERO_NAO_INFORMADO)

		&& !idUnidadeNegocio.trim().equals("")){

			sql = sql + " loc.uneg_id = " + idUnidadeNegocio + " and ";

		}

		// Gerência Regional

		if(idGerenciaRegional != null

		&& !idGerenciaRegional.trim().equals(

		"" + ConstantesSistema.NUMERO_NAO_INFORMADO)

		&& !idGerenciaRegional.trim().equals("")){

			sql = sql + " loc.greg_id = " + idGerenciaRegional + " and ";

		}

		// Localidade

		if(idLocalidadeInicial != null

		&& !idLocalidadeInicial.trim().equals("")){

			sql = sql + " loc.loca_id between " + idLocalidadeInicial + " and "

			+ idLocalidadeFinal + " and ";

		}

		// Perfil do Imóvel

		if(idsPerfilImovel != null && !idsPerfilImovel.equals("")){

			String valoresIn = "";

			for(int i = 0; i < idsPerfilImovel.length; i++){

				if(!idsPerfilImovel[i].equals("")

				&& !idsPerfilImovel[i].equals(""

				+ ConstantesSistema.NUMERO_NAO_INFORMADO)){

					valoresIn = valoresIn + idsPerfilImovel[i] + ",";

				}

			}

			if(!valoresIn.equals("")){

				sql = sql + " imov.iper_id in (" + valoresIn;

				sql = Util.formatarHQL(sql, 1);

				sql = sql + ") and ";

			}

		}

		// Cliente Tipo Especial

		if(idsClienteTipoEspecial != null && !idsClienteTipoEspecial.equals("")){

			String valoresIn = "";

			for(int i = 0; i < idsClienteTipoEspecial.length; i++){

				if(!idsClienteTipoEspecial[i].equals("")

				&& !idsClienteTipoEspecial[i].equals(""

				+ ConstantesSistema.NUMERO_NAO_INFORMADO)){

					valoresIn = valoresIn + idsClienteTipoEspecial[i] + ",";

				}

			}

			if(!valoresIn.equals("")){

				sql = sql + " cles.cles_id in (" + valoresIn;

				sql = Util.formatarHQL(sql, 1);

				sql = sql + ") and ";

			}

		}

		// Categoria

		if(idsCategoria != null && !idsCategoria.equals("")){

			String valoresIn = "";

			for(int i = 0; i < idsCategoria.length; i++){

				if(!idsCategoria[i].equals("")

				&& !idsCategoria[i].equals(""

				+ ConstantesSistema.NUMERO_NAO_INFORMADO)){

					valoresIn = valoresIn + idsCategoria[i] + ",";

				}

			}

			if(!valoresIn.equals("")){

				sql = sql + " subcategoria.catg_id in (" + valoresIn;

				sql = Util.formatarHQL(sql, 1);

				sql = sql + ") and ";

			}

		}

		// Subcategoria

		if(idsSubcategoria != null && !idsSubcategoria.equals("")){

			String valoresIn = "";

			for(int i = 0; i < idsSubcategoria.length; i++){

				if(!idsSubcategoria[i].equals("")

				&& !idsSubcategoria[i].equals(""

				+ ConstantesSistema.NUMERO_NAO_INFORMADO)){

					valoresIn = valoresIn + idsSubcategoria[i] + ",";

				}

			}

			if(!valoresIn.equals("")){

				sql = sql + " subcategoria.scat_id in (" + valoresIn;

				sql = Util.formatarHQL(sql, 1);

				sql = sql + ") and ";

			}

		}

		// Situação de Água

		if(idSituacaoAgua != null

		&& !idSituacaoAgua.trim().equals(

		"" + ConstantesSistema.NUMERO_NAO_INFORMADO)

		&& !idSituacaoAgua.trim().equals("")){

			sql = sql + " imov.last_id = " + idSituacaoAgua + " and ";

		}

		// Situação de Esgoto

		if(idSituacaoEsgoto != null

		&& !idSituacaoEsgoto.trim().equals(

		"" + ConstantesSistema.NUMERO_NAO_INFORMADO)

		&& !idSituacaoEsgoto.trim().equals("")){

			sql = sql + " imov.lest_id = " + idSituacaoEsgoto + " and ";

		}

		// Quantidade de Economias

		if(qtdeEconomiasInicial != null

		&& !qtdeEconomiasInicial.trim().equals("")){

			sql = sql + " imov.imov_qteconomia between " + qtdeEconomiasInicial

			+ " and " + qtdeEconomiasFinal + " and ";

		}

		// Intervalo de Consumo de Água

		if(intervaloConsumoAguaInicial != null

		&& !intervaloConsumoAguaInicial.trim().equals("")){

			sql = sql + " consHistAgua.cshi_nnconsumofaturadomes between "

			+ intervaloConsumoAguaInicial + " and "

			+ intervaloConsumoAguaFinal + " and ";

		}

		// Intervalo de Consumo de Esgoto

		if(intervaloConsumoEsgotoInicial != null

		&& !intervaloConsumoEsgotoInicial.trim().equals("")){

			sql = sql + " consHistEsgoto.cshi_nnconsumofaturadomes between "

			+ intervaloConsumoEsgotoInicial + " and "

			+ intervaloConsumoEsgotoFinal + " and ";

		}

		// Cliente Responsável

		if(idClienteResponsavel != null

		&& !idClienteResponsavel.trim().equals("")){

			sql = sql + " clieImovResponsavel.clie_id = "

			+ idClienteResponsavel + " and ";

		}

		// Intervalo de Consumo por Responsável

		if(intervaloConsumoResponsavelInicial != null

		&& !intervaloConsumoResponsavelInicial.trim().equals("")){

			sql = sql

			+ " ((SELECT sum(CASE WHEN consHistAgua.cshi_id is not null "

			+ " THEN consHistAgua.cshi_nnconsumofaturadomes "

			+ " ELSE consHistEsgoto.cshi_nnconsumofaturadomes END) "

			+ " FROM    cliente_imovel clieImov "

			+ " INNER JOIN imovel imov "

			+ " on imov.imov_id = clieImov.imov_id "

			+ " INNER JOIN localidade loc "

			+ " on loc.loca_id = imov.loca_id "

			+ " LEFT OUTER JOIN consumo_historico consHistAgua "

			+ " on clieImov.imov_id = consHistAgua.imov_id and consHistAgua.lgti_id = 1 and consHistAgua.cshi_amfaturamento = "

			+ anoMesFaturamento.toString()

			+ " LEFT OUTER JOIN consumo_historico consHistEsgoto "

			+ " on clieImov.imov_id = consHistEsgoto.imov_id and consHistEsgoto.lgti_id = 2 and consHistEsgoto.cshi_amfaturamento = "

			+ anoMesFaturamento.toString();

			if(idsCategoria != null || idsSubcategoria != null){

				sql = sql + " INNER JOIN imovel_subcategoria imovSub "

				+ " on imovSub.imov_id = imov.imov_id "

				+ " INNER JOIN subcategoria subcategoria "

				+ " on subcategoria.scat_id = imovSub.scat_id ";

			}

			if(idsCapacidadesHidrometro != null){

				sql = sql

				+ " INNER JOIN ligacao_agua ligAgua "

				+ " on ligAgua.lagu_id = imov.imov_id "

				+ " INNER JOIN hidrometro_instalacao_hist hidrInstHist "

				+ " on hidrInstHist.hidi_id = ligAgua.hidi_id "

				+ " INNER JOIN hidrometro hidr "

				+ " on hidr.hidr_id = hidrInstHist.hidr_id ";

			}else{

				sql = sql

				+ " LEFT OUTER JOIN ligacao_agua ligAgua "

				+ " on ligAgua.lagu_id = imov.imov_id "

				+ " LEFT OUTER JOIN hidrometro_instalacao_hist hidrInstHist "

				+ " on hidrInstHist.hidi_id = ligAgua.hidi_id "

				+ " LEFT OUTER JOIN hidrometro hidr "

				+ " on hidr.hidr_id = hidrInstHist.hidr_id ";

			}

			if((leituraAnormalidade != null && !leituraAnormalidade.trim()

			.equals(""))

			|| (idLeituraAnormalidade != null && !idLeituraAnormalidade

			.trim().equals(""))){

				sql = sql

				+ " INNER JOIN medicao_historico medHist "

				+ " on medHist.lagu_id = imov.imov_id "

				+ " and medHist.medt_id "

				+ MedicaoTipo.LIGACAO_AGUA.toString()

				+ " and medHist.mdhi_amleitura = "

				+ anoMesFaturamento.toString();

			}

			sql = sql + " WHERE ";

			// Unidade de Negócio

			if(idUnidadeNegocio != null

			&& !idUnidadeNegocio.trim().equals(

			"" + ConstantesSistema.NUMERO_NAO_INFORMADO)

			&& !idUnidadeNegocio.trim().equals("")){

				sql = sql + " loc.uneg_id = " + idUnidadeNegocio + " and ";

			}

			// Gerência Regional

			if(idGerenciaRegional != null

			&& !idGerenciaRegional.trim().equals(

			"" + ConstantesSistema.NUMERO_NAO_INFORMADO)

			&& !idGerenciaRegional.trim().equals("")){

				sql = sql + " loc.greg_id = " + idGerenciaRegional + " and ";

			}

			// Localidade

			if(idLocalidadeInicial != null

			&& !idLocalidadeInicial.trim().equals("")){

				sql = sql + " loc.loca_id between " + idLocalidadeInicial

				+ " and " + idLocalidadeFinal + " and ";

			}

			// Perfil do Imóvel

			if(idsPerfilImovel != null && !idsPerfilImovel.equals("")){

				String valoresIn = "";

				for(int i = 0; i < idsPerfilImovel.length; i++){

					if(!idsPerfilImovel[i].equals("")

					&& !idsPerfilImovel[i].equals(""

					+ ConstantesSistema.NUMERO_NAO_INFORMADO)){

						valoresIn = valoresIn + idsPerfilImovel[i] + ",";

					}

				}

				if(!valoresIn.equals("")){

					sql = sql + " imov.iper_id in (" + valoresIn;

					sql = Util.formatarHQL(sql, 1);

					sql = sql + ") and ";

				}

			}

			// Categoria

			if(idsCategoria != null && !idsCategoria.equals("")){

				String valoresIn = "";

				for(int i = 0; i < idsCategoria.length; i++){

					if(!idsCategoria[i].equals("")

					&& !idsCategoria[i].equals(""

					+ ConstantesSistema.NUMERO_NAO_INFORMADO)){

						valoresIn = valoresIn + idsCategoria[i] + ",";

					}

				}

				if(!valoresIn.equals("")){

					sql = sql + " subcategoria.catg_id in (" + valoresIn;

					sql = Util.formatarHQL(sql, 1);

					sql = sql + ") and ";

				}

			}

			// Subcategoria

			if(idsSubcategoria != null && !idsSubcategoria.equals("")){

				String valoresIn = "";

				for(int i = 0; i < idsSubcategoria.length; i++){

					if(!idsSubcategoria[i].equals("")

					&& !idsSubcategoria[i].equals(""

					+ ConstantesSistema.NUMERO_NAO_INFORMADO)){

						valoresIn = valoresIn + idsSubcategoria[i] + ",";

					}

				}

				if(!valoresIn.equals("")){

					sql = sql + " subcategoria.scat_id in (" + valoresIn;

					sql = Util.formatarHQL(sql, 1);

					sql = sql + ") and ";

				}

			}

			// Situação de Água

			if(idSituacaoAgua != null

			&& !idSituacaoAgua.trim().equals(

			"" + ConstantesSistema.NUMERO_NAO_INFORMADO)

			&& !idSituacaoAgua.trim().equals("")){

				sql = sql + " imov.last_id = " + idSituacaoAgua + " and ";

			}

			// Situação de Esgoto

			if(idSituacaoEsgoto != null

			&& !idSituacaoEsgoto.trim().equals(

			"" + ConstantesSistema.NUMERO_NAO_INFORMADO)

			&& !idSituacaoEsgoto.trim().equals("")){

				sql = sql + " imov.lest_id = " + idSituacaoEsgoto + " and ";

			}

			// Quantidade de Economias

			if(qtdeEconomiasInicial != null

			&& !qtdeEconomiasInicial.trim().equals("")){

				sql = sql + " imov.imov_qteconomia between "

				+ qtdeEconomiasInicial + " and " + qtdeEconomiasFinal

				+ " and ";

			}

			// Intervalo de Consumo de Água

			if(intervaloConsumoAguaInicial != null

			&& !intervaloConsumoAguaInicial.trim().equals("")){

				sql = sql + " consHistAgua.cshi_nnconsumofaturadomes between "

				+ intervaloConsumoAguaInicial + " and "

				+ intervaloConsumoAguaFinal + " and ";

			}

			// Intervalo de Consumo de Esgoto

			if(intervaloConsumoEsgotoInicial != null

			&& !intervaloConsumoEsgotoInicial.trim().equals("")){

				sql = sql

				+ " consHistEsgoto.cshi_nnconsumofaturadomes between "

				+ intervaloConsumoEsgotoInicial + " and "

				+ intervaloConsumoEsgotoFinal + " and ";

			}

			// Data Instalação do Hidrômetro

			if(dataInstalacaoHidrometroInicial != null){

				String dataInicial = Util

				.formatarData(dataInstalacaoHidrometroInicial);

				String dataFinal = Util

				.formatarData(dataInstalacaoHidrometroFinal);

				// dataInicial = dataInicial.substring(0, 4) + "-"
				//
				// + dataInicial.substring(4, 6) + "-"
				//
				// + dataInicial.substring(6, 8);
				//
				// dataFinal = dataFinal.substring(0, 4) + "-"
				//
				// + dataFinal.substring(4, 6) + "-"
				//
				// + dataFinal.substring(6, 8);

				sql = sql

				+ " hidrInstHist.hidi_dtinstalacaohidrometro between '"

				+ dataInicial + "' and '" + dataFinal + "' and ";

			}

			// Capacidade do Hidrômetro

			if(idsCapacidadesHidrometro != null

			&& !idsCapacidadesHidrometro.equals("")){

				String valoresIn = "";

				for(int i = 0; i < idsCapacidadesHidrometro.length; i++){

					if(!idsCapacidadesHidrometro[i].equals("")

					&& !idsCapacidadesHidrometro[i].equals(""

					+ ConstantesSistema.NUMERO_NAO_INFORMADO)){

						valoresIn = valoresIn + idsCapacidadesHidrometro[i]

						+ ",";

					}

				}

				if(!valoresIn.equals("")){

					sql = sql + " hidr.hicp_id in (" + valoresIn;

					sql = Util.formatarHQL(sql, 1);

					sql = sql + ") and ";

				}

			}

			// Tarifa de Consumo

			if(idsTarifasConsumo != null && !idsTarifasConsumo.equals("")){

				String valoresIn = "";

				for(int i = 0; i < idsTarifasConsumo.length; i++){

					if(!idsTarifasConsumo[i].equals("")

					&& !idsTarifasConsumo[i].equals(""

					+ ConstantesSistema.NUMERO_NAO_INFORMADO)){

						valoresIn = valoresIn + idsTarifasConsumo[i] + ",";

					}

				}

				if(!valoresIn.equals("")){

					sql = sql + " imov.cstf_id in (" + valoresIn;

					sql = Util.formatarHQL(sql, 1);

					sql = sql + ") and ";

				}

			}

			// Anormalidade de Leitura

			if(idLeituraAnormalidade != null

			&& !idLeituraAnormalidade.trim().equals("")){

				sql = sql + " medHist.ltan_idleituraanormalidadefatu = "

				+ idLeituraAnormalidade + " and ";

			}

			if(leituraAnormalidade != null

			&& !leituraAnormalidade.trim().equals("")){

				if(leituraAnormalidade.trim().equals("1")){

					sql = sql

					+ " medHist.ltan_idleituraanormalidadefatu is not null and ";

				}else{

					sql = sql

					+ " medHist.ltan_idleituraanormalidadefatu is null and ";

				}

			}

			// Anormalidade de Consumo

			if(idConsumoAnormalidade != null

			&& !idConsumoAnormalidade.trim().equals(

			"" + ConstantesSistema.NUMERO_NAO_INFORMADO)

			&& !idConsumoAnormalidade.trim().equals("")){

				sql = sql + " consHistAgua.csan_id = " + idConsumoAnormalidade

				+ " and ";

			}

			if(consumoAnormalidade != null

			&& !consumoAnormalidade.trim().equals("")){

				if(consumoAnormalidade.trim().equals("1")){

					sql = sql + " consHistAgua.csan_id is not null and ";

				}else{

					sql = sql + " consHistAgua.csan_id is null and ";

				}

			}

			sql = sql + " clieImov.clie_id = clieImovResponsavel.clie_id "

			+ " and clieImov.crtp_id = "

			+ ClienteRelacaoTipo.RESPONSAVEL.toString()

			+ " and clieImov.clim_dtrelacaofim is null" + " ) between "

			+ intervaloConsumoResponsavelInicial + " and "

			+ intervaloConsumoResponsavelFinal + " ) and ";

		}

		// Data Instalação do Hidrômetro

		if(dataInstalacaoHidrometroInicial != null){

			String dataInicial = Util

			.formatarData(dataInstalacaoHidrometroInicial);

			String dataFinal = Util

			.formatarData(dataInstalacaoHidrometroFinal);

			// dataInicial = dataInicial.substring(0, 4) + "-"
			//
			// + dataInicial.substring(4, 6) + "-"
			//
			// + dataInicial.substring(6, 8);
			//
			// dataFinal = dataFinal.substring(0, 4) + "-"
			//
			// + dataFinal.substring(4, 6) + "-"
			//
			// + dataFinal.substring(6, 8);

			sql = sql + " hidrInstHist.hidi_dtinstalacaohidrometro between '"

			+ dataInicial + "' and '" + dataFinal + "' and ";

		}

		// Capacidade do Hidrômetro

		if(idsCapacidadesHidrometro != null

		&& !idsCapacidadesHidrometro.equals("")){

			String valoresIn = "";

			for(int i = 0; i < idsCapacidadesHidrometro.length; i++){

				if(!idsCapacidadesHidrometro[i].equals("")

				&& !idsCapacidadesHidrometro[i].equals(""

				+ ConstantesSistema.NUMERO_NAO_INFORMADO)){

					valoresIn = valoresIn + idsCapacidadesHidrometro[i] + ",";

				}

			}

			if(!valoresIn.equals("")){

				sql = sql + " hidr.hicp_id in (" + valoresIn;

				sql = Util.formatarHQL(sql, 1);

				sql = sql + ") and ";

			}

		}

		// Tarifa de Consumo

		if(idsTarifasConsumo != null && !idsTarifasConsumo.equals("")){

			String valoresIn = "";

			for(int i = 0; i < idsTarifasConsumo.length; i++){

				if(!idsTarifasConsumo[i].equals("")

				&& !idsTarifasConsumo[i].equals(""

				+ ConstantesSistema.NUMERO_NAO_INFORMADO)){

					valoresIn = valoresIn + idsTarifasConsumo[i] + ",";

				}

			}

			if(!valoresIn.equals("")){

				sql = sql + " imov.cstf_id in (" + valoresIn;

				sql = Util.formatarHQL(sql, 1);

				sql = sql + ") and ";

			}

		}

		// Anormalidade de Leitura

		if(idLeituraAnormalidade != null

		&& !idLeituraAnormalidade.trim().equals("")){

			sql = sql + " medHist.ltan_idleituraanormalidadefatu = "

			+ idLeituraAnormalidade + " and ";

		}

		if(leituraAnormalidade != null

		&& !leituraAnormalidade.trim().equals("")){

			if(leituraAnormalidade.trim().equals("1")){

				sql = sql

				+ " medHist.ltan_idleituraanormalidadefatu is not null and ";

			}else{

				sql = sql

				+ " medHist.ltan_idleituraanormalidadefatu is null and ";

			}

		}

		// Anormalidade de Consumo

		if(idConsumoAnormalidade != null

		&& !idConsumoAnormalidade.trim().equals(

		"" + ConstantesSistema.NUMERO_NAO_INFORMADO)

		&& !idConsumoAnormalidade.trim().equals("")){

			sql = sql + " consHistAgua.csan_id = " + idConsumoAnormalidade

			+ " and ";

		}

		if(consumoAnormalidade != null

		&& !consumoAnormalidade.trim().equals("")){

			if(consumoAnormalidade.trim().equals("1")){

				sql = sql + " consHistAgua.csan_id is not null and ";

			}else{

				sql = sql + " consHistAgua.csan_id is null and ";

			}

		}

		// verifica se foi contruida a clausula where
		if(sql.length() > 7){
			// retira o " and " q fica sobrando no final da query
			sql = Util.formatarHQL(sql, 4);
		}else{
			sql = "";
		}

		return sql;

	}

	/**
	 * Pesquisa todos os ids do perfil do imóvel.
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2007
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection<Integer> pesquisarTodosIdsPerfilImovel()

	throws ErroRepositorioException{

		Collection<Integer> retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "select iper.id from ImovelPerfil iper";

			retorno = session.createQuery(consulta).list();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	/**
	 * Obtém a descrição de uma categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 04/06/2007
	 * @return
	 * @throws ErroRepositorioException
	 */

	public String obterDescricaoSubcategoria(Integer idSubcategoria)

	throws ErroRepositorioException{

		String descricao = null;

		// Query

		String consulta;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			consulta = "select sub.descricao from Subcategoria sub "

			+ " where sub.id = :idSubcategoria";

			descricao = (String) session.createQuery(consulta).setInteger(

			"idSubcategoria", idSubcategoria).setMaxResults(1)

			.uniqueResult();

			// erro no hibernate

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

		// retorna a coleção de atividades pesquisada(s)

		return descricao;

	}

	/**
	 * Pesquisar Rota do imovel
	 */

	public FaturamentoGrupo pesquisarGrupoImovel(Integer idImovel)

	throws ErroRepositorioException{

		FaturamentoGrupo retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "select faturamentoGrupo from Imovel imovel"

			+ " inner join imovel.rota rota"

			+ " inner join rota.faturamentoGrupo faturamentoGrupo"

			+ " where imovel.id =:idImovel";

			retorno = (FaturamentoGrupo) session.createQuery(consulta)

			.setInteger("idImovel", idImovel).setMaxResults(1)

			.uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	/**
	 * Gerar Relatório de Imóvel Outros Critérios
	 * 
	 * @author Rafael Corrêa,Rafael Santos
	 * @date 24/07/2006,01/08/2006
	 */

	public Collection gerarRelatorioImovelEnderecoOutrosCriterios(String idImovelCondominio, String idImovelPrincipal,
					String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String[] idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,
					String intervaloValorPercentualEsgotoInicial, String intervaloValorPercentualEsgotoFinal,
					String intervaloMediaMinimaImovelInicial, String intervaloMediaMinimaImovelFinal,
					String intervaloMediaMinimaHidrometroInicial, String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,
					String idPocoTipo, String idFaturamentoSituacaoTipo, String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,
					String idEloAnormalidade, String areaConstruidaInicial, String areaConstruidaFinal, String idCadastroOcorrencia,
					String idConsumoTarifa, String idGerenciaRegional, String idLocalidadeInicial, String idLocalidadeFinal,
					String setorComercialInicial, String setorComercialFinal, String quadraInicial, String quadraFinal, String loteOrigem,
					String loteDestno, String cep, String logradouro, String bairro, String municipio, String idTipoMedicao,
					String indicadorMedicao, String idSubCategoria, String idCategoria, String quantidadeEconomiasInicial,
					String quantidadeEconomiasFinal, String diaVencimento, String idCliente, String idClienteTipo,
					String idClienteRelacaoTipo, String numeroPontosInicial, String numeroPontosFinal, String numeroMoradoresInicial,
					String numeroMoradoresFinal, String idAreaConstruidaFaixa, String idUnidadeNegocio, String rotaInicial,
					String rotaFinal, String sequencialRotaInicial, String sequencialRotaFinal, String segmentoInicial,
					String segmentoFinal, String subloteInicial, String subloteFinal, String indicadorOrdenacao)
					throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = "";

		try{

			consulta = consulta
							+ "select distinct "
							/* 00 */+ "gerencia_regional.greg_id, "
							/* 01 */+ "gerencia_regional.greg_nmregional, "
							/* 02 */+ "localidade.loca_id, "
							/* 03 */+ "localidade.loca_nmlocalidade, "
							/* 04 */+ "imovel.imov_id, "
							/* 05 */+ "imovel.imov_qteconomia, "
							/* 06 */+ "setor_comercial.stcm_cdsetorcomercial, "
							/* 07 */+ "setor_comercial.stcm_nmsetorcomercial, "
							/* 08 */+ "quadra.qdra_nnquadra, "
							/* 09 */+ "imovel.imov_nnlote, "
							/* 10 */+ "imovel.imov_nnsublote, "
							/* 11 */+ "ligacao_agua_situacao.last_dsabreviado, "
							/* 12 */+ "ligacao_esgoto_situacao.lest_dsabreviado, "
							/* 13 */+ "ligacao_esgoto.lesg_pcesgoto, "
							/* 14 */+ "ligacao_esgoto.lesg_dtligacao, "
							/* 15 */+ "ligacao_agua.lagu_dtligacaoagua, "

							+
							// Informações do Cliente Usuasrio e Resposanvel
							/* 16 */"cliente_usuario.clie_id as idClienteUsuario, "
							/* 17 */+ "cliente_usuario.clie_nmcliente as nomeClienteUsuario, "
							/* 18 */+ "cliente_responsavel.clie_id as idClienteResponsavel, "
							/* 19 */+ "cliente_responsavel.clie_nmcliente as nomeClienteResponsavel, "
							/* 20 */+ "logradouro.logr_nmlogradouro, "
							/* 21 */+ "logradouro_tipo.lgtp_dslogradourotipo, "
							/* 22 */+ "logradouro_titulo.lgtt_dslogradourotitulo, "
							/* 23 */+ "cep.cep_cdcep, "
							/* 24 */+ "endereco_referencia.edrf_dsenderecoreferencia, "
							/* 25 */+ "imovel.imov_dscomplementoendereco, "
							/* 26 */+ "imovel.imov_nnimovel, "
							/* 27 */+ "bairro.bair_nmbairro, "
							/* 28 */+ "municipio.muni_nmmunicipio, "
							/* 29 */+ "unidade_federacao.unfe_dsufsigla, "

							+ "imovel.imov_icimovelcondominio, "
							+ "imovel.imov_nnmorador, "
							+ "imovel.imov_idimovelcondominio, "
							+ "imovel.imov_idimovelprincipal, "
							+ "imovel.imov_nnpontosutilizacao, "
							+ "imovel_perfil.iper_dsimovelperfil, "
							+ "area_construida_faixa.acon_nnmaiorfaixa, "
							+ "area_construida_faixa.acon_nnmenorfaixa, "
							+ "imovel.imov_nnareaconstruida, "
							+ "pavimento_calcada.pcal_dspavimentocalcada, "
							+ "pavimento_rua.prua_dspavimentorua, "
							+ "despejo.depj_dsdespejo, "
							+ "reservatorio_volume_faixa.resv_vomenorfaixa, "
							+ "reservatorio_volume_faixa.resv_vomaiorfaixa, "
							+ "imovel.imov_voreservatoriosuperior, "
							+ "reservatorio_volume_faixa_inf.resv_vomenorfaixa, "
							+ "reservatorio_volume_faixa_inf.resv_vomaiorfaixa, "
							+ "imovel.imov_voreservatorioinferior, "
							+ "piscina_volume_faixa.pisc_vomenorfaixa, "
							+ "piscina_volume_faixa.pisc_vomaiorfaixa, "
							+ "imovel.imov_vopiscina, "
							+ "poco_tipo.poco_dspocotipo, "
							+ "ligacao_agua_diametro.lagd_dsligacaoaguadiametro as ds_LigacaoAguaDiametroAgua, "
							+ "ligacao_agua_material.lagm_dsligacaoaguamaterial as ds_LigacaoAguaMaterialAgua, "
							+ "ligacao_esgoto_diametro.legd_dsligacaoesgotodiametro as ds_LigacaoEsgotoDiametroEsg, "
							+ "ligacao_esgoto_material.legm_dsligacaoesgotomaterial as ds_LigacaoESgotoMaterialEsg, "
							+ "ligacao_esgoto.lesg_nnconsumominimoesgoto, "
							+ "ligacao_esgoto.lesg_pccoleta, "
							+ "ligacao_esgoto.lesg_pcesgoto, "

							+

							// Agua
							"hidrometro.hidr_nnhidrometro as idHidrometroAgua, "
							+ "hidrometro.hidr_nnanofabricacao as anoFabricacaoHidrometroAgua, "
							+ "hidrometro_capacidade.hicp_dshidrometrocapacidade as ds_HidrometroCapacidadeAgua, "
							+ "hidrometro_marca.himc_dshidrometromarca as ds_HidrometroMarcaAgua, "
							+ "hidrometro_diametro.hidm_dshidrometrodiametro as ds_HidrometroDiametroAgua, "
							+ "hidrometro_tipo.hitp_dshidrometrotipo as ds_HidrometroTipoAgua, "
							+ "hidrometro_instalacao_hist.hidi_dtinstalacaohidrometro as dt_InstalacaoHistoricoAgua, "
							+ "hidrometro_local_instalacao.hili_dshidrometrolocalinstalac as ds_HidrometroLocalInstalacAgua, "
							+ "hidrometro_protecao.hipr_dshidrometroprotecao as ds_HidrometroProtecaoAgua, "
							+ "hidrometro_instalacao_hist.hidi_iccavalete as ic_HidrometroInstalacHistAgua, "

							+
							// Esgoto
							"hidrometro_esgoto.hidr_nnhidrometro as idHidrometroEsgoto, "
							+ "hidrometro_esgoto.hidr_nnanofabricacao as anoFabricacaoHidrometroEsgoto, "
							+ "hidrometro_capacidade_esgoto.hicp_dshidrometrocapacidade as ds_HidrometroCapacidadeEsgoto, "
							+ "hidrometro_marca_esgoto.himc_dshidrometromarca as ds_HidrometroMarcaEsgoto, "
							+ "hidrometro_diametro_esgoto.hidm_dshidrometrodiametro as ds_HidrometroDiametroEsgoto, "
							+ "hidrometro_tipo_esgoto.hitp_dshidrometrotipo as ds_HidrometroTipoEsgoto, "
							+ "hidrom_inst_hist_esgoto.hidi_dtinstalacaohidrometro as dtHidroInstalacHistoricoEsg, "
							+ "hidrometro_local_instalac_esg.hili_dshidrometrolocalinstalac as ds_HidrometroLocalInstalacEsg, "
							+ "hidrometro_protecao_esgoto.hipr_dshidrometroprotecao as ds_HidrometroProtecaoEsgoto, "
							+ "hidrom_inst_hist_esgoto.hidi_iccavalete as ic_HidrometroInstalacaoEsg, "

							+
							// Ligacao Agua
							"ligacao_agua.lagu_nnconsumominimoagua, "

							+
							// Ligacao Esgoto
							"ligacao_esgoto.lesg_nnconsumominimoesgoto, "

							+
							// Jardim
							"imovel.imov_icjardim, "

							+
							// Rota
							"rotaImovel.rota_cdrota, "

							+
							// Sequencial Rota
							"imovel.imov_nnsequencialrota, "

							+
							// Rota do Imóvel
							"imovel.rota_id, "

							+
							// Segmento
							"imovel.imov_nnsegmento "

							+
							// From

							"from imovel_subcategoria imovelSubcategoria "
							+ "inner join imovel on imovelSubcategoria.imov_id = imovel.imov_id "
							+ "inner join localidade on imovel.loca_id = localidade.loca_id "
							+ "inner join gerencia_regional on localidade.greg_id = gerencia_regional.greg_id "
							+ "inner join setor_comercial on imovel.stcm_id = setor_comercial.stcm_id "
							+

							// Logradouro Bairro
							"left join logradouro_bairro on imovel.lgbr_id = logradouro_bairro.lgbr_id "
							+ "left join bairro on logradouro_bairro.bair_id = bairro.bair_id "
							+ "left join municipio on bairro.muni_id = municipio.muni_id "
							+ "inner join quadra on imovel.qdra_id = quadra.qdra_id "
							+ "inner join rota rotaImovel on imovel.rota_id = rotaImovel.rota_id "

							+

							// Logradouro Cep

							"left join logradouro_cep on imovel.lgcp_id = logradouro_cep.lgcp_id "

							+ "left join cep on logradouro_cep.cep_id = cep.cep_id "

							+ "left join logradouro on logradouro_cep.logr_id = logradouro.logr_id "

							+

							// AGUA

							"left join ligacao_agua_situacao on imovel.last_id = ligacao_agua_situacao.last_id "

							+ "left join ligacao_agua on imovel.imov_id = ligacao_agua.lagu_id "

							+

							// ESGOTO

							"left join ligacao_esgoto_situacao on imovel.lest_id = ligacao_esgoto_situacao.lest_id "

							+ "left join ligacao_esgoto on imovel.imov_id = ligacao_esgoto.lesg_id "

							+

							"left join imovel_perfil on imovel.iper_id = imovel_perfil.iper_id "

							+

							"left join poco_tipo on imovel.poco_id = poco_tipo.poco_id "

							+

							"left join area_construida_faixa on imovel.acon_id = area_construida_faixa.acon_id "

							+

							// Cliente Usuario

							"left outer join cliente_imovel cliente_imovel_usuario on imovel.imov_id = cliente_imovel_usuario.imov_id  "

							+ " and (cliente_imovel_usuario.crtp_id=2 and cliente_imovel_usuario.clim_dtrelacaofim is null) "

							+ "left outer join cliente cliente_usuario on cliente_imovel_usuario.clie_id = cliente_usuario.clie_id "

							+

							// Cliente Resposanvel

							"left outer join cliente_imovel cliente_imovel_responsavel on imovel.imov_id = cliente_imovel_responsavel.imov_id "

							+ " and (cliente_imovel_responsavel.crtp_id=3 and cliente_imovel_responsavel.clim_dtrelacaofim is null) "

							+ "left outer join cliente cliente_responsavel on cliente_imovel_responsavel.clie_id = cliente_responsavel.clie_id "

							+

							// AGUA

							"left join hidrometro_instalacao_hist on ligacao_agua.hidi_id =  hidrometro_instalacao_hist.hidi_id "

							+

							// ESGOTO

							"left join hidrometro_instalacao_hist hidrom_inst_hist_esgoto on imovel.hidi_id =  hidrom_inst_hist_esgoto.hidi_id "

							+

							// Relacionamento para o Relatorio de Imovel

							"left join logradouro_titulo on logradouro.lgtt_id = logradouro_titulo.lgtt_id "

							+ "left join logradouro_tipo on logradouro.lgtp_id = logradouro_tipo.lgtp_id "

							+ "left join endereco_referencia on imovel.edrf_id = endereco_referencia.edrf_id "

							+ "left join unidade_federacao on municipio.unfe_id = unidade_federacao.unfe_id "

							+

							"left join reservatorio_volume_faixa on imovel.resv_idreservatoriosuperior = reservatorio_volume_faixa.resv_id "

							+ "left join reservatorio_volume_faixa reservatorio_volume_faixa_inf on imovel.resv_idreservatorioinferior = reservatorio_volume_faixa_inf.resv_id "

							+

							"left join piscina_volume_faixa on imovel.pisc_id = piscina_volume_faixa.pisc_id "

							+ "left join pavimento_calcada on imovel.pcal_id = pavimento_calcada.pcal_id "

							+ "left join pavimento_rua on imovel.prua_id = pavimento_rua.prua_id "

							+ "left join despejo on imovel.depj_id = despejo.depj_id "

							+

							// AGUA

							"left join ligacao_agua_diametro on ligacao_agua.lagd_id = ligacao_agua_diametro.lagd_id "

							+ "left join ligacao_agua_material on ligacao_agua.lagm_id = ligacao_agua_material.lagm_id "

							+

							// ESGOTO

							"left join ligacao_esgoto_diametro on ligacao_esgoto.legd_id = ligacao_esgoto_diametro.legd_id "

							+ "left join ligacao_esgoto_material on ligacao_esgoto.legm_id = ligacao_esgoto_material.legm_id "

							+

							// AGUA

							"left join hidrometro on hidrometro_instalacao_hist.hidr_id = hidrometro.hidr_id "

							+ "left join hidrometro_capacidade on hidrometro.hicp_id = hidrometro_capacidade.hicp_id "

							+ "left join hidrometro_marca on hidrometro.himc_id = hidrometro_marca.himc_id "

							+ "left join hidrometro_diametro on hidrometro.hidm_id = hidrometro_diametro.hidm_id "

							+ "left join hidrometro_tipo on hidrometro.hitp_id = hidrometro_tipo.hitp_id "

							+ "left join hidrometro_local_instalacao on hidrometro_instalacao_hist.hili_id = hidrometro_local_instalacao.hili_id "

							+ "left join hidrometro_protecao on hidrometro_instalacao_hist.hipr_id = hidrometro_protecao.hipr_id "

							+

							// ESGOTO

							"left join hidrometro hidrometro_esgoto on hidrom_inst_hist_esgoto.hidr_id = hidrometro_esgoto.hidr_id "

							+ "left join hidrometro_capacidade hidrometro_capacidade_esgoto on hidrometro_esgoto.hicp_id = hidrometro_capacidade_esgoto.hicp_id "

							+ "left join hidrometro_marca hidrometro_marca_esgoto on hidrometro_esgoto.himc_id = hidrometro_marca_esgoto.himc_id "

							+ "left join hidrometro_diametro hidrometro_diametro_esgoto on hidrometro_esgoto.hidm_id = hidrometro_diametro_esgoto.hidm_id "

							+ "left join hidrometro_tipo hidrometro_tipo_esgoto on hidrometro_esgoto.hitp_id = hidrometro_tipo_esgoto.hitp_id "

							+ "left join hidrometro_local_instalacao hidrometro_local_instalac_esg on hidrom_inst_hist_esgoto.hili_id = hidrometro_local_instalacao.hili_id "

							+ "left join hidrometro_protecao hidrometro_protecao_esgoto on hidrom_inst_hist_esgoto.hipr_id = hidrometro_protecao_esgoto.hipr_id ";

			consulta = consulta
							+ montarInnerJoinFiltrarImoveisOutrosCriterios(intervaloMediaMinimaImovelInicial,
											intervaloMediaMinimaImovelFinal, intervaloMediaMinimaHidrometroInicial,
											intervaloMediaMinimaHidrometroFinal, idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo,
											idSituacaoEspecialCobranca, idEloAnormalidade, idCadastroOcorrencia, idConsumoTarifa,
											idTipoMedicao, indicadorMedicao, idSubCategoria, idCategoria, idCliente, idClienteTipo,
											idClienteRelacaoTipo, ConstantesSistema.GERAR_RELATORIO_IMOVEL);
			consulta = consulta
							+ montarCondicaoSQLWhereFiltrarImovelOutrosCriterio(idImovelCondominio, idImovelPrincipal,
											idSituacaoLigacaoAgua, consumoMinimoInicialAgua, consumoMinimoFinalAgua,
											idSituacaoLigacaoEsgoto, consumoMinimoInicialEsgoto, consumoMinimoFinalEsgoto,
											intervaloValorPercentualEsgotoInicial, intervaloValorPercentualEsgotoFinal,
											intervaloMediaMinimaImovelInicial, intervaloMediaMinimaImovelFinal,
											intervaloMediaMinimaHidrometroInicial, intervaloMediaMinimaHidrometroFinal, idImovelPerfil,
											idPocoTipo, idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo, idSituacaoEspecialCobranca,
											idEloAnormalidade, areaConstruidaInicial, areaConstruidaFinal, idCadastroOcorrencia,
											idConsumoTarifa, idGerenciaRegional, idLocalidadeInicial, idLocalidadeFinal,
											setorComercialInicial, setorComercialFinal, quadraInicial, quadraFinal, loteOrigem, loteDestno,
											cep, logradouro, bairro, municipio, idTipoMedicao, indicadorMedicao, idSubCategoria,
											idCategoria, quantidadeEconomiasInicial, quantidadeEconomiasFinal, diaVencimento, idCliente,
											idClienteTipo, idClienteRelacaoTipo, numeroPontosInicial, numeroPontosFinal,
											numeroMoradoresInicial, numeroMoradoresFinal, idAreaConstruidaFaixa, idUnidadeNegocio,
											ConstantesSistema.GERAR_RELATORIO_IMOVEL, rotaInicial, rotaFinal, sequencialRotaInicial,
											sequencialRotaFinal, segmentoInicial, segmentoFinal, subloteInicial, subloteFinal);

			/*
			 * # COLOCANDO O VALOR NAS CONDIÇÕES#
			 */
			String orderBy = "";
			if(!Util.isVazioOuBranco(indicadorOrdenacao)){
				if(indicadorOrdenacao.equals("1")){
					orderBy = " order by gerencia_regional.greg_id, localidade.loca_id,18 ";

				}else{

					orderBy = " order by gerencia_regional.greg_id, localidade.loca_id,"
									+ " logradouro_tipo.lgtp_dslogradourotipo, logradouro_titulo.lgtt_dslogradourotitulo, logradouro.logr_nmlogradouro";

				}

			}

			// String orderBy =
			// " order by gerencia_regional.greg_id, localidade.loca_id, setor_comercial.stcm_cdsetorcomercial, "
			// + " quadra.qdra_nnquadra, imovel.imov_nnlote, imovel.imov_nnsublote, "
			// +
			// " logradouro_tipo.lgtp_dslogradourotipo, logradouro_titulo.lgtt_dslogradourotitulo, logradouro.logr_nmlogradouro ";

			SQLQuery query = session.createSQLQuery(consulta.substring(0, (consulta.length() - 5)).concat(orderBy));
			informarDadosQueryFiltrarImovelOutrosCriterio(query, idImovelCondominio, idImovelPrincipal, idSituacaoLigacaoAgua,
							consumoMinimoInicialAgua, consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto, consumoMinimoInicialEsgoto,
							consumoMinimoFinalEsgoto, intervaloValorPercentualEsgotoInicial, intervaloValorPercentualEsgotoFinal,
							intervaloMediaMinimaImovelInicial, intervaloMediaMinimaImovelFinal, intervaloMediaMinimaHidrometroInicial,
							intervaloMediaMinimaHidrometroFinal, idImovelPerfil, idPocoTipo, idFaturamentoSituacaoTipo,
							idCobrancaSituacaoTipo, idSituacaoEspecialCobranca, idEloAnormalidade, areaConstruidaInicial,
							areaConstruidaFinal, idCadastroOcorrencia, idConsumoTarifa, idGerenciaRegional, idLocalidadeInicial,
							idLocalidadeFinal, setorComercialInicial, setorComercialFinal, quadraInicial, quadraFinal, loteOrigem,
							loteDestno, cep, logradouro, bairro, municipio, idTipoMedicao, indicadorMedicao, idSubCategoria, idCategoria,
							quantidadeEconomiasInicial, quantidadeEconomiasFinal, diaVencimento, idCliente, idClienteTipo,
							idClienteRelacaoTipo, numeroPontosInicial, numeroPontosFinal, numeroMoradoresInicial, numeroMoradoresFinal,
							idAreaConstruidaFaixa, idUnidadeNegocio, rotaInicial, rotaFinal, sequencialRotaInicial, sequencialRotaFinal,
							segmentoInicial, segmentoFinal, subloteInicial, subloteFinal);

			retorno = query.addScalar("greg_id", Hibernate.INTEGER).addScalar("greg_nmregional", Hibernate.STRING).addScalar("loca_id",
							Hibernate.INTEGER).addScalar("loca_nmlocalidade", Hibernate.STRING).addScalar("imov_id", Hibernate.INTEGER)
							.addScalar("imov_qteconomia", Hibernate.SHORT).addScalar("stcm_cdsetorcomercial", Hibernate.INTEGER).addScalar(
											"stcm_nmsetorcomercial", Hibernate.STRING).addScalar("qdra_nnquadra", Hibernate.INTEGER)
							.addScalar("imov_nnlote", Hibernate.SHORT).addScalar("imov_nnsublote", Hibernate.SHORT).addScalar(
											"last_dsabreviado", Hibernate.STRING).addScalar("lest_dsabreviado", Hibernate.STRING)
							.addScalar("lesg_pcesgoto", Hibernate.BIG_DECIMAL).addScalar("lesg_dtligacao", Hibernate.DATE).addScalar(
											"lagu_dtligacaoagua", Hibernate.DATE).addScalar("idClienteUsuario", Hibernate.INTEGER)
							.addScalar("nomeClienteUsuario", Hibernate.STRING).addScalar("idClienteResponsavel", Hibernate.INTEGER)
							.addScalar("nomeClienteResponsavel", Hibernate.STRING).addScalar("logr_nmlogradouro", Hibernate.STRING)
							.addScalar("lgtp_dslogradourotipo", Hibernate.STRING).addScalar("lgtt_dslogradourotitulo", Hibernate.STRING)
							.addScalar("cep_cdcep", Hibernate.INTEGER).addScalar("edrf_dsenderecoreferencia", Hibernate.STRING).addScalar(
											"imov_dscomplementoendereco", Hibernate.STRING).addScalar("imov_nnimovel", Hibernate.STRING)
							.addScalar("bair_nmbairro", Hibernate.STRING).addScalar("muni_nmmunicipio", Hibernate.STRING).addScalar(
											"unfe_dsufsigla", Hibernate.STRING).addScalar("imov_icimovelcondominio", Hibernate.INTEGER)
							.addScalar("imov_nnmorador", Hibernate.INTEGER).addScalar("imov_idimovelcondominio", Hibernate.INTEGER)
							.addScalar("imov_idimovelprincipal", Hibernate.INTEGER).addScalar("imov_nnpontosutilizacao", Hibernate.INTEGER)
							.addScalar("iper_dsimovelperfil", Hibernate.STRING).addScalar("acon_nnmaiorfaixa", Hibernate.INTEGER)
							.addScalar("acon_nnmenorfaixa", Hibernate.INTEGER).addScalar("imov_nnareaconstruida", Hibernate.BIG_DECIMAL)
							.addScalar("pcal_dspavimentocalcada", Hibernate.STRING).addScalar("prua_dspavimentorua", Hibernate.STRING)
							.addScalar("depj_dsdespejo", Hibernate.STRING).addScalar("resv_vomenorfaixa", Hibernate.BIG_DECIMAL).addScalar(
											"resv_vomaiorfaixa", Hibernate.BIG_DECIMAL).addScalar("imov_voreservatoriosuperior",
											Hibernate.BIG_DECIMAL).addScalar("resv_vomenorfaixa", Hibernate.BIG_DECIMAL).addScalar(
											"resv_vomaiorfaixa", Hibernate.BIG_DECIMAL).addScalar("imov_voreservatorioinferior",
											Hibernate.BIG_DECIMAL).addScalar("pisc_vomenorfaixa", Hibernate.BIG_DECIMAL).addScalar(
											"pisc_vomaiorfaixa", Hibernate.BIG_DECIMAL).addScalar("imov_vopiscina", Hibernate.BIG_DECIMAL)
							.addScalar("poco_dspocotipo", Hibernate.STRING).addScalar("ds_LigacaoAguaDiametroAgua", Hibernate.STRING)
							.addScalar("ds_LigacaoAguaMaterialAgua", Hibernate.STRING).addScalar("ds_LigacaoEsgotoDiametroEsg",
											Hibernate.STRING).addScalar("ds_LigacaoESgotoMaterialEsg", Hibernate.STRING).addScalar(
											"lesg_nnconsumominimoesgoto", Hibernate.INTEGER).addScalar("lesg_pccoleta",
											Hibernate.BIG_DECIMAL).addScalar("lesg_pcesgoto", Hibernate.BIG_DECIMAL).addScalar(
											"idHidrometroAgua", Hibernate.STRING).addScalar("anoFabricacaoHidrometroAgua",
											Hibernate.INTEGER).addScalar("ds_HidrometroCapacidadeAgua", Hibernate.STRING).addScalar(
											"ds_HidrometroMarcaAgua", Hibernate.STRING).addScalar("ds_HidrometroDiametroAgua",
											Hibernate.STRING).addScalar("ds_HidrometroTipoAgua", Hibernate.STRING).addScalar(
											"dt_InstalacaoHistoricoAgua", Hibernate.DATE).addScalar("ds_HidrometroLocalInstalacAgua",
											Hibernate.STRING).addScalar("ds_HidrometroProtecaoAgua", Hibernate.STRING).addScalar(
											"ic_HidrometroInstalacHistAgua", Hibernate.INTEGER).addScalar("idHidrometroEsgoto",
											Hibernate.STRING).addScalar("anoFabricacaoHidrometroEsgoto", Hibernate.INTEGER).addScalar(
											"ds_HidrometroCapacidadeEsgoto", Hibernate.STRING).addScalar("ds_HidrometroMarcaEsgoto",
											Hibernate.STRING).addScalar("ds_HidrometroDiametroEsgoto", Hibernate.STRING).addScalar(
											"ds_HidrometroTipoEsgoto", Hibernate.STRING).addScalar("dtHidroInstalacHistoricoEsg",
											Hibernate.DATE).addScalar("ds_HidrometroLocalInstalacEsg", Hibernate.STRING).addScalar(
											"ds_HidrometroProtecaoEsgoto", Hibernate.STRING).addScalar("ic_HidrometroInstalacaoEsg",
											Hibernate.INTEGER).addScalar("lagu_nnconsumominimoagua", Hibernate.INTEGER).addScalar(
											"lesg_nnconsumominimoesgoto", Hibernate.INTEGER).addScalar("imov_icjardim", Hibernate.SHORT)
							.addScalar("rota_cdrota", Hibernate.SHORT).addScalar("imov_nnsequencialrota", Hibernate.INTEGER).addScalar(
											"rota_id", Hibernate.INTEGER).addScalar("imov_nnsegmento", Hibernate.SHORT).list();

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
	 * [UC00609] Transferencia de Debitos/Creditos
	 * Recupera a situação da ligação de esgoto
	 * 
	 * @author Raphael Rossiter
	 * @date 07/06/2007
	 * @param idImovel
	 * @return LigacaoEsgotoSituacao
	 * @throws ErroRepositorioException
	 */

	public LigacaoEsgotoSituacao pesquisarLigacaoEsgotoSituacao(Integer idImovel)

	throws ErroRepositorioException{

		LigacaoEsgotoSituacao retorno = null;

		String consulta;

		Session session = HibernateUtil.getSession();

		try{

			consulta = "SELECT ligEsgSit "

			+ "FROM gcom.cadastro.imovel.Imovel  imovel "

			+ "INNER JOIN imovel.ligacaoEsgotoSituacao ligEsgSit "

			+ "WHERE imovel.id = :idImovel";

			retorno = (LigacaoEsgotoSituacao) session.createQuery(consulta)

			.setInteger("idImovel", idImovel).setMaxResults(1)

			.uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	/**
	 * [UC00609] Transferencia de Debitos/Creditos
	 * Recupera a situação da ligação de agua
	 * 
	 * @author Raphael Rossiter
	 * @date 07/06/2007
	 * @param idImovel
	 * @return LigacaoAguaSituacao
	 * @throws ErroRepositorioException
	 */

	public LigacaoAguaSituacao pesquisarLigacaoAguaSituacao(Integer idImovel)

	throws ErroRepositorioException{

		LigacaoAguaSituacao retorno = null;

		String consulta;

		Session session = HibernateUtil.getSession();

		try{

			consulta = "SELECT ligAguaSit "

			+ "FROM gcom.cadastro.imovel.Imovel  imovel "

			+ "INNER JOIN imovel.ligacaoAguaSituacao ligAguaSit "

			+ "WHERE imovel.id = :idImovel";

			retorno = (LigacaoAguaSituacao) session.createQuery(consulta)

			.setInteger("idImovel", idImovel).setMaxResults(1)

			.uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	/**
	 * Gerar Relatório de Imóvel Outros Critérios
	 * 
	 * @author Rafael Corrêa,Rafael Santos
	 * @date 24/07/2006,01/08/2006
	 */

	public Collection gerarRelatorioCadastroConsumidoreInscricao(

	String idImovelCondominio, String idImovelPrincipal,

	String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua,

	String consumoMinimoFinalAgua, String[] idSituacaoLigacaoEsgoto,

	String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,

	String intervaloValorPercentualEsgotoInicial,

	String intervaloValorPercentualEsgotoFinal,

	String intervaloMediaMinimaImovelInicial,

	String intervaloMediaMinimaImovelFinal,

	String intervaloMediaMinimaHidrometroInicial,

	String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,

	String idPocoTipo, String idFaturamentoSituacaoTipo,

	String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,

	String idEloAnormalidade, String areaConstruidaInicial,

	String areaConstruidaFinal, String idCadastroOcorrencia,

	String idConsumoTarifa, String idGerenciaRegional,

	String idLocalidadeInicial, String idLocalidadeFinal,

	String setorComercialInicial, String setorComercialFinal,

	String quadraInicial, String quadraFinal, String loteOrigem,

	String loteDestno, String cep, String logradouro, String bairro,

	String municipio, String idTipoMedicao, String indicadorMedicao,

	String idSubCategoria, String idCategoria,

	String quantidadeEconomiasInicial, String quantidadeEconomiasFinal,

	String diaVencimento, String idCliente, String idClienteTipo,

	String idClienteRelacaoTipo, String numeroPontosInicial,

	String numeroPontosFinal, String numeroMoradoresInicial,

	String numeroMoradoresFinal, String idAreaConstruidaFaixa,

	String idUnidadeNegocio, String rotaInicial, String rotaFinal, String sequencialRotaInicial, String sequencialRotaFinal,
					String segmentoInicial, String segmentoFinal, String subloteInicial, String subloteFinal, String indicadorOrdenacao)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = consulta

							+ "select distinct "

							+ "unidadeNegocio.uneg_id, " // 1

							+ "unidadeNegocio.uneg_nmunidadenegocio, " // 2

							+ "gerencia_regional.greg_id, " // 3

							+ "gerencia_regional.greg_nmregional, " // 4

							+ "localidade.loca_id, " // 5

							+ "localidade.loca_nmlocalidade, " // 6

							+ "imovel.imov_id, " // 7

							+ "imovel.imov_qteconomia, " // 8

							+ "setor_comercial.stcm_cdsetorcomercial, " // 9

							+ "setor_comercial.stcm_nmsetorcomercial, " // 10

							+ "quadra.qdra_nnquadra, " // 11

							+ "imovel.imov_nnlote, " // 12

							+ "imovel.imov_nnsublote, " // 13

							+ "ligacao_agua_situacao.last_id, " // 14

							+ "ligacao_esgoto_situacao.lest_id, " // 15

							+ "ligacao_esgoto.lesg_pcesgoto, " // 16

							+ "ligacao_esgoto.lesg_dtligacao, " // 17

							+ "ligacao_agua.lagu_dtligacaoagua, " // 18

							+

							// Informações do Cliente Usuasrio e Resposanvel

							"cliente_usuario.clie_id as idClienteUsuario, " // 19

							+ "cliente_usuario.clie_nmcliente as nomeClienteUsuario, " // 20

							+ "cliente_responsavel.clie_id as idClienteResponsavel, " // 21

							+ "cliente_responsavel.clie_nmcliente as nomeClienteResponsavel, " // 22

							+

							"logradouro.logr_nmlogradouro, " // 23

							+ "logradouro_tipo.lgtp_dslogradourotipo, " // 24

							+ "logradouro_titulo.lgtt_dslogradourotitulo, " // 25

							+ "cep.cep_cdcep, " // 26

							+ "endereco_referencia.edrf_dsenderecoreferencia, " // 27

							+ "imovel.imov_dscomplementoendereco, " // 28

							+ "imovel.imov_nnimovel, " // 29

							+ "bairro.bair_nmbairro, " // 30

							+ "municipio.muni_nmmunicipio, " // 31

							+ "unidade_federacao.unfe_dsufsigla, " // 32

							+ "imovel.imov_icimovelcondominio, " // 33

							+ "imovel.imov_nnmorador, " // 34

							+ "imovel.imov_idimovelcondominio, " // 35

							+ "imovel.imov_idimovelprincipal, " // 36

							+ "imovel.imov_nnpontosutilizacao, " // 37

							+ "imovel_perfil.iper_dsimovelperfil, " // 38

							+ "area_construida_faixa.acon_nnmaiorfaixa, " // 39

							+ "area_construida_faixa.acon_nnmenorfaixa, " // 40

							+ "imovel.imov_nnareaconstruida, " // 41

							+ "pavimento_calcada.pcal_id, " // 42

							+ "pavimento_rua.prua_id, " // 43

							+ "despejo.depj_id, " // 44

							+ "reservatorio_volume_faixa.resv_vomenorfaixa, " // 45

							+ "reservatorio_volume_faixa.resv_vomaiorfaixa, " // 46

							+ "imovel.resv_idreservatoriosuperior, " // 47

							+ "reservatorio_volume_faixa_inf.resv_vomenorfaixa, " // 48

							+ "reservatorio_volume_faixa_inf.resv_vomaiorfaixa, " // 49

							+ "imovel.resv_idreservatorioinferior, " // 50

							+ "piscina_volume_faixa.pisc_vomenorfaixa, " // 51

							+ "piscina_volume_faixa.pisc_vomaiorfaixa, " // 52

							+ "imovel.pisc_id, " // 53

							+ "poco_tipo.poco_dspocotipo, " // 54

							+ "ligacao_agua_diametro.lagd_id as descLigAguaDiametroAgua, " // 55

							+ "ligacao_agua_material.lagm_dsligacaoaguamaterial as descLigAguaMaterialAgua, " // 56

							+ "ligacao_esgoto_diametro.legd_id as descLigEsgotoDiametroEsgoto, " // 57

							+ "ligacao_esgoto_material.legm_dsligacaoesgotomaterial as descLigEsgotoMatEsgoto, " // 58

							+ "ligacao_esgoto.lesg_nnconsumominimoesgoto, " // 59

							+ "ligacao_esgoto.lesg_pccoleta, " // 60

							+ "ligacao_esgoto.lesg_pcesgoto, " // 61

							+

							// Agua

							"hidrometro.hidr_nnhidrometro as idHidrometroAgua, " // 62

							+ "hidrometro.hidr_nnanofabricacao as anoFabricancaoHidrometroAgua, " // 63

							+ "hidrometro_capacidade.hicp_id as descHidromCapacidadeAgua, " // 64

							+ "hidrometro_marca.himc_id as descricaoHidrometroMarcaAgua, " // 65

							+ "hidrometro_diametro.hidm_id as descHidromDiametroAgua, " // 66

							+ "hidrometro_tipo.hitp_id as descricaoHidrometroTipoAgua, " // 67

							+ "hidrometro_instalacao_hist.hidi_dtinstalacaohidrometro as dataHidromInstHistoricoAgua, " // 68

							+ "hidrometro_local_instalacao.hili_id as hidrometroLocalInstalacaoAgua, " // 69

							+ "hidrometro_protecao.hipr_id as descHidrometroProtecaoAgua, " // 70

							+ "hidrometro_instalacao_hist.hidi_iccavalete as indHidromInstHistoricoAgua, " // 71

							+

							// Esgoto

							"hidrometro_esgoto.hidr_nnhidrometro as idHidrometroEsgoto, " // 72

							+ "hidrometro_esgoto.hidr_nnanofabricacao as anoFabricacaoHidrometroEsgoto, " // 73

							+ "hidrometro_capacidade_esgoto.hicp_dshidrometrocapacidade as descHidromCapacidadeEsgoto, " // 74

							+ "hidrometro_marca_esgoto.himc_dshidrometromarca as descricaoHidrometroMarcaEsgoto, " // 75

							+ "hidrometro_diametro_esgoto.hidm_dshidrometrodiametro as descHidromDiametroEsgoto, " // 76

							+ "hidrometro_tipo_esgoto.hitp_dshidrometrotipo as descricaoHidrometroTipoEsgoto, " // 77

							+ "hidrom_inst_hist_esgoto.hidi_dtinstalacaohidrometro as dataHidromInstHistoricoEsgoto, " // 78

							+ "hidrometro_local_instalac_esg.hili_dshidrometrolocalinstalac as descHidromLocalInstEsgoto, " // 79

							+ "hidrometro_protecao_esgoto.hipr_dshidrometroprotecao as descHidromProtecaoEsgoto, " // 80

							+ "hidrom_inst_hist_esgoto.hidi_iccavalete as indHidromInstEsgoto, " // 81

							+

							// Ligacao Agua

							"ligacao_agua.lagu_nnconsumominimoagua, " // 82

							+

							// Ligacao Esgoto

							"ligacao_esgoto.lesg_nnconsumominimoesgoto, " // 83

							+

							// Jardim

							"imovel.imov_icjardim, " // 84

							+

							// Rota

							"rotaImovel.rota_cdrota, " // 85

							+

							// Sequencial Rota

							"imovel.imov_nnsequencialrota, " // 86

							+

							// Tipo Faturamento

							"imovel.fttp_id, " // 87

							+

							// Logradouro id

							"logradouro_cep.logr_id, " // 88

							+

							// DDD

							"cliente_fone.cfon_cdddd, " // 89

							+

							// Fone

							"cliente_fone.cfon_nnfone, " // 90

							+

							// Segmento

							"imovel.imov_nnsegmento, " // 91

							+

							// Rota

							"imovel.rota_id " // 92

							+

							// From

							"from imovel_subcategoria imovelSubcategoria "

							+

							"inner join imovel on imovelSubcategoria.imov_id = imovel.imov_id "

							+ "inner join localidade on imovel.loca_id = localidade.loca_id "

							+ "inner join gerencia_regional on localidade.greg_id = gerencia_regional.greg_id "

							+ "left join unidade_negocio unidadeNegocio on localidade.uneg_id = unidadeNegocio.uneg_id "

							+ "inner join setor_comercial on imovel.stcm_id = setor_comercial.stcm_id "

							+

							// Logradouro Bairro

							"left join logradouro_bairro on imovel.lgbr_id = logradouro_bairro.lgbr_id "

							+ "left join bairro on logradouro_bairro.bair_id = bairro.bair_id "

							+ "left join municipio on bairro.muni_id = municipio.muni_id "

							+ "inner join quadra on imovel.qdra_id = quadra.qdra_id "

							+ "inner join rota rotaImovel on imovel.rota_id = rotaImovel.rota_id "

							+

							// Logradouro Cep

							"left join logradouro_cep on imovel.lgcp_id = logradouro_cep.lgcp_id "

							+ "left join cep on logradouro_cep.cep_id = cep.cep_id "

							+ "left join logradouro on logradouro_cep.logr_id = logradouro.logr_id "

							+

							// AGUA

							"left join ligacao_agua_situacao on imovel.last_id = ligacao_agua_situacao.last_id "

							+ "left join ligacao_agua on imovel.imov_id = ligacao_agua.lagu_id "

							+

							// ESGOTO

							"left join ligacao_esgoto_situacao on imovel.lest_id = ligacao_esgoto_situacao.lest_id "

							+ "left join ligacao_esgoto on imovel.imov_id = ligacao_esgoto.lesg_id "

							+

							"left join imovel_perfil on imovel.iper_id = imovel_perfil.iper_id "

							+

							"left join poco_tipo on imovel.poco_id = poco_tipo.poco_id "

							+

							"left join area_construida_faixa on imovel.acon_id = area_construida_faixa.acon_id "

							+

							// Cliente Usuario

							"left outer join cliente_imovel  cliente_imovel_usuario on imovel.imov_id = cliente_imovel_usuario.imov_id  "

							+ " and (cliente_imovel_usuario.crtp_id=2 and cliente_imovel_usuario.clim_dtrelacaofim is null) "

							+ "left outer join cliente  cliente_usuario on cliente_imovel_usuario.clie_id = cliente_usuario.clie_id "

							+ "left outer join cliente_fone  cliente_fone on cliente_usuario.clie_id = cliente_fone.clie_id and (cliente_fone.cfon_icfonepadrao = 1) "

							+

							// Cliente Resposanvel

							"left outer join cliente_imovel  cliente_imovel_responsavel on imovel.imov_id = cliente_imovel_responsavel.imov_id "

							+ " and (cliente_imovel_responsavel.crtp_id=3 and cliente_imovel_responsavel.clim_dtrelacaofim is null) "

							+ "left outer join cliente   cliente_responsavel on cliente_imovel_responsavel.clie_id = cliente_responsavel.clie_id "

							+

							// AGUA

							"left join hidrometro_instalacao_hist on ligacao_agua.hidi_id =  hidrometro_instalacao_hist.hidi_id "

							+

							// ESGOTO

							"left join hidrometro_instalacao_hist  hidrom_inst_hist_esgoto on imovel.hidi_id =  hidrom_inst_hist_esgoto.hidi_id "

							+

							// Relacionamento para o Relatorio de Imovel

							"left join logradouro_titulo on logradouro.lgtt_id = logradouro_titulo.lgtt_id "

							+ "left join logradouro_tipo on logradouro.lgtp_id = logradouro_tipo.lgtp_id "

							+ "left join endereco_referencia on imovel.edrf_id = endereco_referencia.edrf_id "

							+ "left join unidade_federacao on municipio.unfe_id = unidade_federacao.unfe_id "

							+

							"left join reservatorio_volume_faixa on imovel.resv_idreservatoriosuperior = reservatorio_volume_faixa.resv_id "

							+ "left join reservatorio_volume_faixa  reservatorio_volume_faixa_inf on imovel.resv_idreservatorioinferior = reservatorio_volume_faixa_inf.resv_id "

							+

							"left join piscina_volume_faixa on imovel.pisc_id = piscina_volume_faixa.pisc_id "

							+ "left join pavimento_calcada on imovel.pcal_id = pavimento_calcada.pcal_id "

							+ "left join pavimento_rua on imovel.prua_id = pavimento_rua.prua_id "

							+ "left join despejo on imovel.depj_id = despejo.depj_id "

							+

							// AGUA

							"left join ligacao_agua_diametro on ligacao_agua.lagd_id = ligacao_agua_diametro.lagd_id "

							+ "left join ligacao_agua_material on ligacao_agua.lagm_id = ligacao_agua_material.lagm_id "

							+

							// ESGOTO

							"left join ligacao_esgoto_diametro on ligacao_esgoto.legd_id = ligacao_esgoto_diametro.legd_id "

							+ "left join ligacao_esgoto_material on ligacao_esgoto.legm_id = ligacao_esgoto_material.legm_id "

							+

							// AGUA

							"left join hidrometro on hidrometro_instalacao_hist.hidr_id = hidrometro.hidr_id "

							+ "left join hidrometro_capacidade on hidrometro.hicp_id = hidrometro_capacidade.hicp_id "

							+ "left join hidrometro_marca on hidrometro.himc_id = hidrometro_marca.himc_id "

							+ "left join hidrometro_diametro on hidrometro.hidm_id = hidrometro_diametro.hidm_id "

							+ "left join hidrometro_tipo on hidrometro.hitp_id = hidrometro_tipo.hitp_id "

							+ "left join hidrometro_local_instalacao on hidrometro_instalacao_hist.hili_id = hidrometro_local_instalacao.hili_id "

							+ "left join hidrometro_protecao on hidrometro_instalacao_hist.hipr_id = hidrometro_protecao.hipr_id "

							+

							// ESGOTO

							"left join hidrometro  hidrometro_esgoto on hidrom_inst_hist_esgoto.hidr_id = hidrometro_esgoto.hidr_id "

							+ "left join hidrometro_capacidade  hidrometro_capacidade_esgoto on hidrometro_esgoto.hicp_id = hidrometro_capacidade_esgoto.hicp_id "

							+ "left join hidrometro_marca  hidrometro_marca_esgoto on hidrometro_esgoto.himc_id = hidrometro_marca_esgoto.himc_id "

							+ "left join hidrometro_diametro  hidrometro_diametro_esgoto on hidrometro_esgoto.hidm_id = hidrometro_diametro_esgoto.hidm_id "

							+ "left join hidrometro_tipo  hidrometro_tipo_esgoto on hidrometro_esgoto.hitp_id = hidrometro_tipo_esgoto.hitp_id "

							+ "left join hidrometro_local_instalacao  hidrometro_local_instalac_esg on hidrometro_local_instalac_esg.hili_id = hidrometro_local_instalacao.hili_id "

							+ "left join hidrometro_protecao  hidrometro_protecao_esgoto on hidrom_inst_hist_esgoto.hipr_id = hidrometro_protecao_esgoto.hipr_id ";

			consulta = consulta + montarInnerJoinFiltrarImoveisOutrosCriterios(

			intervaloMediaMinimaImovelInicial,

			intervaloMediaMinimaImovelFinal,

			intervaloMediaMinimaHidrometroInicial,

			intervaloMediaMinimaHidrometroFinal,

			idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo,

			idSituacaoEspecialCobranca, idEloAnormalidade,

			idCadastroOcorrencia, idConsumoTarifa,

			idTipoMedicao, indicadorMedicao, idSubCategoria,

			idCategoria, idCliente, idClienteTipo,

			idClienteRelacaoTipo,

			ConstantesSistema.GERAR_RELATORIO_IMOVEL);

			consulta = consulta

							+ montarCondicaoSQLWhereFiltrarImovelOutrosCriterio(

							idImovelCondominio, idImovelPrincipal,

							idSituacaoLigacaoAgua, consumoMinimoInicialAgua,

							consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto,

							consumoMinimoInicialEsgoto,

							consumoMinimoFinalEsgoto,

							intervaloValorPercentualEsgotoInicial,

							intervaloValorPercentualEsgotoFinal,

							intervaloMediaMinimaImovelInicial,

							intervaloMediaMinimaImovelFinal,

							intervaloMediaMinimaHidrometroInicial,

							intervaloMediaMinimaHidrometroFinal,

							idImovelPerfil, idPocoTipo,

							idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo,

							idSituacaoEspecialCobranca, idEloAnormalidade,

							areaConstruidaInicial, areaConstruidaFinal,

							idCadastroOcorrencia, idConsumoTarifa,

							idGerenciaRegional, idLocalidadeInicial,

							idLocalidadeFinal, setorComercialInicial,

							setorComercialFinal, quadraInicial, quadraFinal,

							loteOrigem, loteDestno, cep, logradouro, bairro,

							municipio, idTipoMedicao, indicadorMedicao,

							idSubCategoria, idCategoria,

							quantidadeEconomiasInicial,

							quantidadeEconomiasFinal, diaVencimento, idCliente,

							idClienteTipo, idClienteRelacaoTipo,

							numeroPontosInicial, numeroPontosFinal,

							numeroMoradoresInicial, numeroMoradoresFinal,

							idAreaConstruidaFaixa, idUnidadeNegocio,

							ConstantesSistema.GERAR_RELATORIO_IMOVEL, rotaInicial, rotaFinal, sequencialRotaInicial, sequencialRotaFinal,
											segmentoInicial, segmentoFinal, subloteInicial, subloteFinal);

			/*
			 * # COLOCANDO O VALOR NAS CONDIÇÕES#
			 */

			/*
			 * consulta = consulta + " consumosHistorico.referenciaFaturamento = " +
			 * "(select max(consumoHistorico.referenciaFaturamento) from
			 * ConsumoHistorico consumoHistorico " + " left join
			 * consumoHistorico.imovel imovelConsumoHistorico " + "where
			 * imovelConsumoHistorico.id = imovel.id) and ";
			 */
			SQLQuery query = null;

			if(!Util.isVazioOuBranco(indicadorOrdenacao)){
				if(indicadorOrdenacao.equals("1")){
					query = session.createSQLQuery(consulta.substring(0,

					(consulta.length() - 5)).concat(" order by 5, 9, 11, 12, 13"));
				}else{
					query = session.createSQLQuery(consulta.substring(0,

					(consulta.length() - 5)).concat(" order by 5, 9, 85, 91, 12"));
				}
			}else{
				query = session.createSQLQuery(consulta.substring(0,

				(consulta.length() - 5)).concat(" order by 1, 3, 7, 9, 10, 11"));
			}

			informarDadosQueryFiltrarImovelOutrosCriterio(query,

			idImovelCondominio, idImovelPrincipal,

			idSituacaoLigacaoAgua, consumoMinimoInicialAgua,

			consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto,

			consumoMinimoInicialEsgoto, consumoMinimoFinalEsgoto,

			intervaloValorPercentualEsgotoInicial,

			intervaloValorPercentualEsgotoFinal,

			intervaloMediaMinimaImovelInicial,

			intervaloMediaMinimaImovelFinal,

			intervaloMediaMinimaHidrometroInicial,

			intervaloMediaMinimaHidrometroFinal, idImovelPerfil,

			idPocoTipo, idFaturamentoSituacaoTipo,

			idCobrancaSituacaoTipo, idSituacaoEspecialCobranca,

			idEloAnormalidade, areaConstruidaInicial,

			areaConstruidaFinal, idCadastroOcorrencia, idConsumoTarifa,

			idGerenciaRegional, idLocalidadeInicial, idLocalidadeFinal,

			setorComercialInicial, setorComercialFinal, quadraInicial,

			quadraFinal, loteOrigem, loteDestno, cep, logradouro,

			bairro, municipio, idTipoMedicao, indicadorMedicao,

			idSubCategoria, idCategoria, quantidadeEconomiasInicial,

			quantidadeEconomiasFinal, diaVencimento, idCliente,

			idClienteTipo, idClienteRelacaoTipo, numeroPontosInicial,

			numeroPontosFinal, numeroMoradoresInicial,

			numeroMoradoresFinal, idAreaConstruidaFaixa,

			idUnidadeNegocio, rotaInicial, rotaFinal, sequencialRotaInicial, sequencialRotaFinal, segmentoInicial, segmentoFinal,
							subloteInicial, subloteFinal);

			retorno = query

			// .addScalar("imov_id",Hibernate.INTEGER)

							.addScalar("uneg_id", Hibernate.INTEGER)

							.addScalar("uneg_nmunidadenegocio", Hibernate.STRING)

							.addScalar("greg_id", Hibernate.INTEGER)

							.addScalar("greg_nmregional", Hibernate.STRING)

							.addScalar("loca_id", Hibernate.INTEGER)

							.addScalar("loca_nmlocalidade", Hibernate.STRING)

							.addScalar("imov_id", Hibernate.INTEGER)

							.addScalar("imov_qteconomia", Hibernate.SHORT)

							.addScalar("stcm_cdsetorcomercial", Hibernate.INTEGER)

							.addScalar("stcm_nmsetorcomercial", Hibernate.STRING)

							.addScalar("qdra_nnquadra", Hibernate.INTEGER)

							.addScalar("imov_nnlote", Hibernate.SHORT)

							.addScalar("imov_nnsublote", Hibernate.SHORT)

							.addScalar("last_id", Hibernate.INTEGER)

							.addScalar("lest_id", Hibernate.INTEGER)

							.addScalar("lesg_pcesgoto", Hibernate.BIG_DECIMAL)

							.addScalar("lesg_dtligacao", Hibernate.DATE)

							.addScalar("lagu_dtligacaoagua", Hibernate.DATE)

							.addScalar("idClienteUsuario", Hibernate.INTEGER)

							.addScalar("nomeClienteUsuario", Hibernate.STRING)

							.addScalar("idClienteResponsavel", Hibernate.INTEGER)

							.addScalar("nomeClienteResponsavel", Hibernate.STRING)

							.addScalar("logr_nmlogradouro", Hibernate.STRING)

							.addScalar("lgtp_dslogradourotipo", Hibernate.STRING)

							.addScalar("lgtt_dslogradourotitulo", Hibernate.STRING)

							.addScalar("cep_cdcep", Hibernate.INTEGER)

							.addScalar("edrf_dsenderecoreferencia", Hibernate.STRING)

							.addScalar("imov_dscomplementoendereco", Hibernate.STRING)

							.addScalar("imov_nnimovel", Hibernate.STRING)

							.addScalar("bair_nmbairro", Hibernate.STRING)

							.addScalar("muni_nmmunicipio", Hibernate.STRING)

							.addScalar("unfe_dsufsigla", Hibernate.STRING)

							.addScalar("imov_icimovelcondominio", Hibernate.INTEGER)

							.addScalar("imov_nnmorador", Hibernate.INTEGER)

							.addScalar("imov_idimovelcondominio", Hibernate.INTEGER)

							.addScalar("imov_idimovelprincipal", Hibernate.INTEGER)

							.addScalar("imov_nnpontosutilizacao", Hibernate.INTEGER)

							.addScalar("iper_dsimovelperfil", Hibernate.STRING)

							.addScalar("acon_nnmaiorfaixa", Hibernate.INTEGER)

							.addScalar("acon_nnmenorfaixa", Hibernate.INTEGER)

							.addScalar("imov_nnareaconstruida", Hibernate.BIG_DECIMAL)

							.addScalar("pcal_id", Hibernate.INTEGER)

							.addScalar("prua_id", Hibernate.INTEGER)

							.addScalar("depj_id", Hibernate.INTEGER)

							.addScalar("resv_vomenorfaixa", Hibernate.BIG_DECIMAL)

							.addScalar("resv_vomaiorfaixa", Hibernate.BIG_DECIMAL)

							.addScalar("resv_idreservatoriosuperior", Hibernate.INTEGER)

							.addScalar("resv_vomenorfaixa", Hibernate.BIG_DECIMAL)

							.addScalar("resv_vomaiorfaixa", Hibernate.BIG_DECIMAL)

							.addScalar("resv_idreservatorioinferior", Hibernate.INTEGER)

							.addScalar("pisc_vomenorfaixa", Hibernate.BIG_DECIMAL)

							.addScalar("pisc_vomaiorfaixa", Hibernate.BIG_DECIMAL)

							.addScalar("pisc_id", Hibernate.INTEGER).addScalar(

							"poco_dspocotipo", Hibernate.STRING).addScalar(

							"descLigAguaDiametroAgua",

							Hibernate.INTEGER).addScalar(

							"descLigAguaMaterialAgua",

							Hibernate.STRING).addScalar(

							"descLigEsgotoDiametroEsgoto",

							Hibernate.INTEGER).addScalar(

							"descLigEsgotoMatEsgoto",

							Hibernate.STRING).addScalar(

							"lesg_nnconsumominimoesgoto", Hibernate.INTEGER)

							.addScalar("lesg_pccoleta", Hibernate.BIG_DECIMAL)

							.addScalar("lesg_pcesgoto", Hibernate.BIG_DECIMAL)

							.addScalar("idHidrometroAgua", Hibernate.STRING).addScalar(

							"anoFabricancaoHidrometroAgua", Hibernate.INTEGER)

							.addScalar("descHidromCapacidadeAgua",

							Hibernate.INTEGER).addScalar(

							"descricaoHidrometroMarcaAgua", Hibernate.INTEGER)

							.addScalar("descHidromDiametroAgua",

							Hibernate.INTEGER).addScalar(

							"descricaoHidrometroTipoAgua", Hibernate.INTEGER)

							.addScalar("dataHidromInstHistoricoAgua",

							Hibernate.DATE).addScalar(

							"hidrometroLocalInstalacaoAgua", Hibernate.INTEGER)

							.addScalar("descHidrometroProtecaoAgua",

							Hibernate.INTEGER).addScalar(

							"indHidromInstHistoricoAgua",

							Hibernate.INTEGER)

							.addScalar("idHidrometroEsgoto", Hibernate.STRING)

							.addScalar("anoFabricacaoHidrometroEsgoto",

							Hibernate.INTEGER).addScalar(

							"descHidromCapacidadeEsgoto",

							Hibernate.STRING).addScalar(

							"descricaoHidrometroMarcaEsgoto", Hibernate.STRING)

							.addScalar("descHidromDiametroEsgoto",

							Hibernate.STRING).addScalar(

							"descricaoHidrometroTipoEsgoto", Hibernate.STRING)

							.addScalar("dataHidromInstHistoricoEsgoto",

							Hibernate.DATE).addScalar(

							"descHidromLocalInstEsgoto",

							Hibernate.STRING).addScalar(

							"descHidromProtecaoEsgoto",

							Hibernate.STRING).addScalar(

							"indHidromInstEsgoto",

							Hibernate.INTEGER).addScalar(

							"lagu_nnconsumominimoagua", Hibernate.INTEGER)

							.addScalar("lesg_nnconsumominimoesgoto", Hibernate.INTEGER)

							.addScalar("imov_icjardim", Hibernate.SHORT).addScalar(

							"rota_cdrota", Hibernate.SHORT).addScalar(

							"imov_nnsequencialrota", Hibernate.INTEGER)

							.addScalar("fttp_id", Hibernate.INTEGER).addScalar(

							"logr_id", Hibernate.INTEGER).addScalar(

							"cfon_cdddd", Hibernate.STRING).addScalar(

							"cfon_nnfone", Hibernate.STRING).addScalar("imov_nnsegmento", Hibernate.SHORT).addScalar("rota_id",
											Hibernate.INTEGER).list();

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
	 * Filtra o Pagamento Historico pelo seu id carregando os dados necessários
	 * [UC0549] Consultar Dados do Pagamento
	 * 
	 * @author Kássia Albuquerque
	 * @date 12/07/2007
	 * @author Saulo Lima
	 * @date 14/01/2009
	 *       Retirada do da cláusula FETCH do join com a tabela imovel (erro de Blob)
	 * @param idPagamentoHistorico
	 * @return Collection<PagamentoHistorico>
	 * @throws ErroRepositorioException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoPeloId(Integer idPagamentoHistorico) throws ErroRepositorioException{

		Collection<PagamentoHistorico> retorno = null;
		String hql = "";
		Session session = HibernateUtil.getSession();

		try{

			hql = "SELECT DISTINCT pagamentoHistorico " + "FROM PagamentoHistorico pagamentoHistorico "
							+ "INNER JOIN FETCH pagamentoHistorico.localidade loc "
							+ "INNER JOIN FETCH pagamentoHistorico.documentoTipo doctoTp "
							+ "INNER JOIN FETCH pagamentoHistorico.pagamentoSituacaoAtual pagtoSitAtual "
							+ "LEFT JOIN pagamentoHistorico.imovel imov " + "LEFT JOIN FETCH pagamentoHistorico.cliente cliente "
							+ "LEFT JOIN FETCH pagamentoHistorico.debitoTipo debTp "
							+ "LEFT JOIN FETCH pagamentoHistorico.pagamentoSituacaoAnterior pagtoSitAnterior "
							+ "LEFT JOIN FETCH pagamentoHistorico.avisoBancario avbc " + "LEFT JOIN FETCH avbc.arrecadador arrec "
							+ "LEFT JOIN FETCH arrec.cliente clienteArrec "
							+ "LEFT JOIN FETCH pagamentoHistorico.arrecadadorMovimentoItem arrecMovItem "
							+ "LEFT JOIN FETCH arrecMovItem.registroCodigo regCod "
							+ "LEFT JOIN FETCH arrecMovItem.arrecadadorMovimento arrecMov "
							+ "WHERE pagamentoHistorico.id = :idPagamentoHistorico";

			retorno = session.createQuery(hql).setInteger("idPagamentoHistorico", idPagamentoHistorico).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Filtra o Pagamento pelo seu id carregando os dados necessários
	 * [UC0549] Consultar Dados do Pagamento
	 * 
	 * @author Kássia Albuquerque
	 * @date 12/07/2007
	 * @author Saulo Lima
	 * @date 14/01/2009
	 *       Correção do HQL
	 * @param idPagamentoHistorico
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentoPeloId(Integer idPagamento) throws ErroRepositorioException{

		Collection<Pagamento> retorno = null;
		String hql = "";
		Session session = HibernateUtil.getSession();

		try{

			hql = "SELECT DISTINCT pagamento " + "FROM Pagamento pagamento " + "INNER JOIN FETCH pagamento.localidade loc "
							+ "INNER JOIN FETCH pagamento.documentoTipo doctoTp " + "INNER JOIN FETCH pagamento.avisoBancario avbc "
							+ "INNER JOIN FETCH avbc.arrecadador arrec " + "INNER JOIN FETCH arrec.cliente clienteArrec "
							+ "LEFT JOIN pagamento.imovel imov " + "LEFT JOIN FETCH pagamento.cliente cliente "
							+ "LEFT JOIN FETCH pagamento.debitoTipo debTp "
							+ "LEFT JOIN FETCH pagamento.pagamentoSituacaoAnterior pagtoSitAnterior "
							+ "LEFT JOIN FETCH pagamento.pagamentoSituacaoAtual pagtoSitAtual "
							+ "LEFT JOIN FETCH pagamento.arrecadadorMovimentoItem arrecMovItem "
							+ "LEFT JOIN FETCH arrecMovItem.registroCodigo regCod "
							+ "LEFT JOIN FETCH arrecMovItem.arrecadadorMovimento arrecMov " + "WHERE pagamento.id = :idPagamento";

			retorno = session.createQuery(hql).setInteger("idPagamento", idPagamento).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Obtém a o nome do cliente
	 * 
	 * @author Kassia Albuquerque
	 * @date 05/07/2007
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */

	public Object[] obterDescricaoIdCliente(Integer idImovel)

	throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			String consulta = "select c.clie_id as id ,c.clie_nmcliente as nome "

			+ "from cliente_imovel cimovel "

			+ "inner join cliente c on cimovel.clie_id = c.clie_id "

			+ "where cimovel.imov_id ="

			+ idImovel

			+ " and cimovel.crtp_id= 2 and cimovel.clim_dtrelacaofim is null ";

			Collection colecaoConsulta = session.createSQLQuery(consulta)

			.addScalar("id", Hibernate.INTEGER).addScalar("nome",

			Hibernate.STRING).list();

			retorno = (Object[]) Util.retonarObjetoDeColecao(colecaoConsulta);

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	/**
	 * Obtém o nome do Arrecadador
	 * 
	 * @author Kassia Albuquerque
	 * @date 09/07/2007
	 * @return String
	 * @throws ErroRepositorioException
	 */

	public String pesquisarNomeAgenteArrecadador(Integer idPagamentoHistorico)

	throws ErroRepositorioException{

		String retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			String consulta = "select c.clie_nmcliente as nome "

			+ "from cliente c, arrecadador a, pagamento_historico p "

			+ "where a.clie_id = c.clie_id and a.arrc_cdagente = p.pghi_cdagente "

			+ "and p.pghi_id =" + idPagamentoHistorico;

			Collection colecaoConsulta = session.createSQLQuery(consulta)

			.addScalar("nome", Hibernate.STRING).list();

			retorno = (String) Util.retonarObjetoDeColecao(colecaoConsulta);

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	/**
	 * Obtém a o 117º caracter de uma String
	 * 
	 * @author Kassia Albuquerque
	 * @date 20/07/2007
	 * @return String
	 * @throws ErroRepositorioException
	 */

	public String pesquisarCaracterRetorno(Integer idConteudoRegistro)

	throws ErroRepositorioException{

		String retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			String consulta = "select substr(amit.amit_cnregistro, 117,1) as caracterRetorno"

			+ " from arrecadador_movimento_item amit"

			+ " where amit.amit_id =" + idConteudoRegistro;

			Collection colecaoConsulta = session.createSQLQuery(consulta)

			.addScalar("caracterRetorno", Hibernate.STRING).list();

			retorno = (String) Util.retonarObjetoDeColecao(colecaoConsulta);

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	/**
	 * @author Sávio Luiz
	 * @date 24/08/2007
	 * @return String
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarSequencialRota(Integer idImovel)

	throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			String consulta = "select imov.numeroSequencialRota "

			+ " from Imovel imov "

			+ " where imov.id = :idImovel";

			retorno = (Integer) session.createQuery(consulta)

			.setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	/**
	 * Gerar Boletim de Cadastro
	 * 
	 * @date 20/08/2007
	 */

	public Collection<EmitirDocumentoCobrancaBoletimCadastroHelper> pesquisarBoletimCadastro(

	String idImovelCondominio, String idImovelPrincipal,

	String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua,

	String consumoMinimoFinalAgua, String[] idSituacaoLigacaoEsgoto,

	String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,

	String intervaloValorPercentualEsgotoInicial,

	String intervaloValorPercentualEsgotoFinal,

	String intervaloMediaMinimaImovelInicial,

	String intervaloMediaMinimaImovelFinal,

	String intervaloMediaMinimaHidrometroInicial,

	String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,

	String idPocoTipo, String idFaturamentoSituacaoTipo,

	String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,

	String idEloAnormalidade, String areaConstruidaInicial,

	String areaConstruidaFinal, String idCadastroOcorrencia,

	String idConsumoTarifa, String idGerenciaRegional,

	String idLocalidadeInicial, String idLocalidadeFinal,

	String setorComercialInicial, String setorComercialFinal,

	String quadraInicial, String quadraFinal, String loteOrigem,

	String loteDestno, String cep, String logradouro, String bairro,

	String municipio, String idTipoMedicao, String indicadorMedicao,

	String idSubCategoria, String idCategoria,

	String quantidadeEconomiasInicial, String quantidadeEconomiasFinal,

	String diaVencimento, String idCliente, String idClienteTipo,

	String idClienteRelacaoTipo, String numeroPontosInicial,

	String numeroPontosFinal, String numeroMoradoresInicial,

	String numeroMoradoresFinal, String idAreaConstruidaFaixa,

	String idUnidadeNegocio, int quantidadeCobrancaDocumentoInicio) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		String consulta = "";

		Collection<EmitirDocumentoCobrancaBoletimCadastroHelper> emitirDocumentoCobrancaBoletimCadastroHelper = new ArrayList();

		try{

			consulta = consulta

							+ "select distinct imovel.imov_id, localidade.loca_id, "// 0,1

							+ "setor_comercial.stcm_cdsetorcomercial, "// 2

							+ "quadra.qdra_nnquadra, imovel.imov_nnlote, " // 3,4

							+ "imovel.imov_nnsublote, rota.cbgr_id, "// 5,6

							+ "ligacao_agua_situacao.last_id, ligacao_esgoto_situacao.lest_id, "// 7,8

							+ "imovel.imov_nnmorador, imovel.imov_nnareaconstruida, logradouro.logr_id , "// 9,10,11

							+ "cep.cep_cdcep, bairro.bair_cdbairro, imovel.edrf_id, imovel.imov_nnimovel, "// 12,13,14,15

							+ "imovel.imov_dscomplementoendereco, imovel.resv_idreservatorioinferior, "// 16,17

							+ "imovel.resv_idreservatoriosuperior, imovel.pisc_id, "// 18,19

							+ "imovel.imov_icjardim, imovel.prua_id,imovel.pcal_id, "// 20,21,22

							+ "imovel.imov_nnpontosutilizacao, imovel.iper_id, imovel.depj_id, "// 23,24,25

							+ "imovel.poco_id, imovel.ftab_id, imovel.imov_nniptu, imovel.imov_nncontratoenergia "// 26,27,28,29

							+

							// From

							"from imovel_subcategoria imovelSubcategoria "

							+

							"inner join imovel on imovelSubcategoria.imov_id = imovel.imov_id "

							+ "inner join localidade on imovel.loca_id = localidade.loca_id "

							+ "inner join gerencia_regional on localidade.greg_id = gerencia_regional.greg_id "

							+ "inner join setor_comercial on imovel.stcm_id = setor_comercial.stcm_id "

							+

							// Logradouro Bairro

							"left join logradouro_bairro on imovel.lgbr_id = logradouro_bairro.lgbr_id "

							+ "left join bairro on logradouro_bairro.bair_id = bairro.bair_id "

							+ "left join municipio on bairro.muni_id = municipio.muni_id "

							+ "inner join quadra on imovel.qdra_id = quadra.qdra_id "

							+ "inner join rota rotaImovel on imovel.rota_id = rotaImovel.rota_id "

							+

							// Logradouro Cep

							"left join logradouro_cep on imovel.lgcp_id = logradouro_cep.lgcp_id "

							+ "left join cep on logradouro_cep.cep_id = cep.cep_id "

							+ "left join logradouro on logradouro_cep.logr_id = logradouro.logr_id "

							+

							// AGUA

							"left join ligacao_agua_situacao on imovel.last_id = ligacao_agua_situacao.last_id "

							+ "left join ligacao_agua on imovel.imov_id = ligacao_agua.lagu_id "

							+

							// ESGOTO

							"left join ligacao_esgoto_situacao on imovel.lest_id = ligacao_esgoto_situacao.lest_id "

							+ "left join ligacao_esgoto on imovel.imov_id = ligacao_esgoto.lesg_id "

							+

							"left join imovel_perfil on imovel.iper_id = imovel_perfil.iper_id "

							+

							"left join poco_tipo on imovel.poco_id = poco_tipo.poco_id "

							+

							"left join area_construida_faixa on imovel.acon_id = area_construida_faixa.acon_id "

							+

							// Cliente Usuario

							"left outer join cliente_imovel  cliente_imovel_usuario on imovel.imov_id = cliente_imovel_usuario.imov_id  "

							+ " and (cliente_imovel_usuario.crtp_id=2 and cliente_imovel_usuario.clim_dtrelacaofim is null) "

							+ "left outer join cliente  cliente_usuario on cliente_imovel_usuario.clie_id = cliente_usuario.clie_id "

							+

							// Cliente Resposanvel

							"left outer join cliente_imovel  cliente_imovel_responsavel on imovel.imov_id = cliente_imovel_responsavel.imov_id "

							+ " and (cliente_imovel_responsavel.crtp_id=3 and cliente_imovel_responsavel.clim_dtrelacaofim is null) "

							+ "left outer join cliente  cliente_responsavel on cliente_imovel_responsavel.clie_id = cliente_responsavel.clie_id "

							+

							// AGUA

							"left join hidrometro_instalacao_hist on ligacao_agua.hidi_id =  hidrometro_instalacao_hist.hidi_id "

							+

							// ESGOTO

							"left join hidrometro_instalacao_hist  hidrom_inst_hist_esgoto on imovel.hidi_id =  hidrom_inst_hist_esgoto.hidi_id "

							+

							// Relacionamento para o Relatorio de Imovel

							"left join logradouro_titulo on logradouro.lgtt_id = logradouro_titulo.lgtt_id "

							+ "left join logradouro_tipo on logradouro.lgtp_id = logradouro_tipo.lgtp_id "

							+ "left join endereco_referencia on imovel.edrf_id = endereco_referencia.edrf_id "

							+ "left join unidade_federacao on municipio.unfe_id = unidade_federacao.unfe_id "

							+

							"left join reservatorio_volume_faixa on imovel.resv_idreservatoriosuperior = reservatorio_volume_faixa.resv_id "

							+ "left join reservatorio_volume_faixa  reservatorio_volume_faixa_inf on imovel.resv_idreservatorioinferior = reservatorio_volume_faixa_inf.resv_id "

							+

							"left join piscina_volume_faixa on imovel.pisc_id = piscina_volume_faixa.pisc_id "

							+ "left join pavimento_calcada on imovel.pcal_id = pavimento_calcada.pcal_id "

							+ "left join pavimento_rua on imovel.prua_id = pavimento_rua.prua_id "

							+ "left join despejo on imovel.depj_id = despejo.depj_id "

							+

							// AGUA

							"left join ligacao_agua_diametro on ligacao_agua.lagd_id = ligacao_agua_diametro.lagd_id "

							+ "left join ligacao_agua_material on ligacao_agua.lagm_id = ligacao_agua_material.lagm_id "

							+

							// ESGOTO

							"left join ligacao_esgoto_diametro on ligacao_esgoto.legd_id = ligacao_esgoto_diametro.legd_id "

							+ "left join ligacao_esgoto_material on ligacao_esgoto.legm_id = ligacao_esgoto_material.legm_id "

							+

							// AGUA

							"left join hidrometro on hidrometro_instalacao_hist.hidr_id = hidrometro.hidr_id "

							+ "left join hidrometro_capacidade on hidrometro.hicp_id = hidrometro_capacidade.hicp_id "

							+ "left join hidrometro_marca on hidrometro.himc_id = hidrometro_marca.himc_id "

							+ "left join hidrometro_diametro on hidrometro.hidm_id = hidrometro_diametro.hidm_id "

							+ "left join hidrometro_tipo on hidrometro.hitp_id = hidrometro_tipo.hitp_id "

							+ "left join hidrometro_local_instalacao on hidrometro_instalacao_hist.hili_id = hidrometro_local_instalacao.hili_id "

							+ "left join hidrometro_protecao on hidrometro_instalacao_hist.hipr_id = hidrometro_protecao.hipr_id "

							+

							// ESGOTO

							"left join hidrometro  hidrometro_esgoto on hidrom_inst_hist_esgoto.hidr_id = hidrometro_esgoto.hidr_id "

							+ "left join hidrometro_capacidade  hidrometro_capacidade_esgoto on hidrometro_esgoto.hicp_id = hidrometro_capacidade_esgoto.hicp_id "

							+ "left join hidrometro_marca  hidrometro_marca_esgoto on hidrometro_esgoto.himc_id = hidrometro_marca_esgoto.himc_id "

							+ "left join hidrometro_diametro  hidrometro_diametro_esgoto on hidrometro_esgoto.hidm_id = hidrometro_diametro_esgoto.hidm_id "

							+ "left join hidrometro_tipo  hidrometro_tipo_esgoto on hidrometro_esgoto.hitp_id = hidrometro_tipo_esgoto.hitp_id "

							+ "left join hidrometro_local_instalacao  hidrometro_local_instalac_esg on hidrom_inst_hist_esgoto.hili_id = hidrometro_local_instalacao.hili_id "

							+ "left join hidrometro_protecao  hidrometro_protecao_esgoto on hidrom_inst_hist_esgoto.hipr_id = hidrometro_protecao_esgoto.hipr_id ";

			// Rota do Imóvel
			// + "left join rota rotaImovel on rotaImovel.rota_id = imovel.rota_id ";

			consulta = consulta

			+ montarInnerJoinFiltrarImoveisOutrosCriterios(

			intervaloMediaMinimaImovelInicial,

			intervaloMediaMinimaImovelFinal,

			intervaloMediaMinimaHidrometroInicial,

			intervaloMediaMinimaHidrometroFinal,

			idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo,

			idSituacaoEspecialCobranca, idEloAnormalidade,

			idCadastroOcorrencia, idConsumoTarifa,

			idTipoMedicao, indicadorMedicao, idSubCategoria,

			idCategoria, idCliente, idClienteTipo,

			idClienteRelacaoTipo,

			ConstantesSistema.GERAR_RELATORIO_IMOVEL);

			consulta = consulta

			+ montarCondicaoSQLWhereFiltrarImovelOutrosCriterio(

			idImovelCondominio, idImovelPrincipal,

			idSituacaoLigacaoAgua, consumoMinimoInicialAgua,

			consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto,

			consumoMinimoInicialEsgoto,

			consumoMinimoFinalEsgoto,

			intervaloValorPercentualEsgotoInicial,

			intervaloValorPercentualEsgotoFinal,

			intervaloMediaMinimaImovelInicial,

			intervaloMediaMinimaImovelFinal,

			intervaloMediaMinimaHidrometroInicial,

			intervaloMediaMinimaHidrometroFinal,

			idImovelPerfil, idPocoTipo,

			idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo,

			idSituacaoEspecialCobranca, idEloAnormalidade,

			areaConstruidaInicial, areaConstruidaFinal,

			idCadastroOcorrencia, idConsumoTarifa,

			idGerenciaRegional, idLocalidadeInicial,

			idLocalidadeFinal, setorComercialInicial,

			setorComercialFinal, quadraInicial, quadraFinal,

			loteOrigem, loteDestno, cep, logradouro, bairro,

			municipio, idTipoMedicao, indicadorMedicao,

			idSubCategoria, idCategoria,

			quantidadeEconomiasInicial,

			quantidadeEconomiasFinal, diaVencimento, idCliente,

			idClienteTipo, idClienteRelacaoTipo,

			numeroPontosInicial, numeroPontosFinal,

			numeroMoradoresInicial, numeroMoradoresFinal,

			idAreaConstruidaFaixa, idUnidadeNegocio,

			ConstantesSistema.GERAR_RELATORIO_IMOVEL, null, null, null, null, null, null, null, null);

			consulta = consulta.substring(0,

			(consulta.length() - 5));

			consulta = consulta + " ORDER BY localidade.loca_id, "

			+ "setor_comercial.stcm_cdsetorcomercial, "

			+ "quadra.qdra_nnquadra, imovel.imov_nnlote, "

			+ "imovel.imov_nnsublote";

			SQLQuery query = session.createSQLQuery(consulta);

			informarDadosQueryFiltrarImovelOutrosCriterio(query,

			idImovelCondominio, idImovelPrincipal,

			idSituacaoLigacaoAgua, consumoMinimoInicialAgua,

			consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto,

			consumoMinimoInicialEsgoto, consumoMinimoFinalEsgoto,

			intervaloValorPercentualEsgotoInicial,

			intervaloValorPercentualEsgotoFinal,

			intervaloMediaMinimaImovelInicial,

			intervaloMediaMinimaImovelFinal,

			intervaloMediaMinimaHidrometroInicial,

			intervaloMediaMinimaHidrometroFinal, idImovelPerfil,

			idPocoTipo, idFaturamentoSituacaoTipo,

			idCobrancaSituacaoTipo, idSituacaoEspecialCobranca,

			idEloAnormalidade, areaConstruidaInicial,

			areaConstruidaFinal, idCadastroOcorrencia, idConsumoTarifa,

			idGerenciaRegional, idLocalidadeInicial, idLocalidadeFinal,

			setorComercialInicial, setorComercialFinal, quadraInicial,

			quadraFinal, loteOrigem, loteDestno, cep, logradouro,

			bairro, municipio, idTipoMedicao, indicadorMedicao,

			idSubCategoria, idCategoria, quantidadeEconomiasInicial,

			quantidadeEconomiasFinal, diaVencimento, idCliente,

			idClienteTipo, idClienteRelacaoTipo, numeroPontosInicial,

			numeroPontosFinal, numeroMoradoresInicial,

			numeroMoradoresFinal, idAreaConstruidaFaixa,

			idUnidadeNegocio, null, null, null, null, null, null, null, null);

			Collection colecaoImovel = query

			.addScalar("imov_id", Hibernate.INTEGER).addScalar("loca_id", Hibernate.INTEGER)

			.addScalar("stcm_cdsetorcomercial", Hibernate.INTEGER).addScalar("qdra_nnquadra", Hibernate.INTEGER)

			.addScalar("imov_nnlote", Hibernate.SHORT).addScalar("imov_nnsublote", Hibernate.SHORT)

			.addScalar("cbgr_id", Hibernate.INTEGER).addScalar("last_id", Hibernate.INTEGER)

			.addScalar("lest_id", Hibernate.INTEGER).addScalar("imov_nnmorador", Hibernate.SHORT)

			.addScalar("imov_nnareaconstruida", Hibernate.BIG_DECIMAL).addScalar("logr_id", Hibernate.INTEGER)

			.addScalar("cep_cdcep", Hibernate.INTEGER).addScalar("bair_cdbairro", Hibernate.INTEGER)

			.addScalar("edrf_id", Hibernate.INTEGER).addScalar("imov_nnimovel", Hibernate.STRING)

			.addScalar("imov_dscomplementoendereco", Hibernate.STRING).addScalar("resv_idreservatorioinferior", Hibernate.INTEGER)

			.addScalar("resv_idreservatoriosuperior", Hibernate.INTEGER).addScalar("pisc_id", Hibernate.INTEGER)

			.addScalar("imov_icjardim", Hibernate.SHORT).addScalar("prua_id", Hibernate.INTEGER)

			.addScalar("pcal_id", Hibernate.INTEGER).addScalar("imov_nnpontosutilizacao", Hibernate.SHORT)

			.addScalar("iper_id", Hibernate.INTEGER).addScalar("depj_id", Hibernate.INTEGER)

			.addScalar("poco_id", Hibernate.INTEGER).addScalar("ftab_id", Hibernate.INTEGER)

			.addScalar("imov_nniptu", Hibernate.BIG_DECIMAL).addScalar("imov_nncontratoenergia", Hibernate.LONG)

			.setFirstResult(quantidadeCobrancaDocumentoInicio).setMaxResults(500).list();

			EmitirDocumentoCobrancaBoletimCadastroHelper helper = null;

			if(!colecaoImovel.isEmpty()){

				Iterator iteratorColecaoImovel = colecaoImovel.iterator();

				while(iteratorColecaoImovel.hasNext()){

					Object[] imovel = (Object[]) iteratorColecaoImovel.next();

					helper = new EmitirDocumentoCobrancaBoletimCadastroHelper();

					if(imovel[0] != null){

						helper.setIdImovel((Integer) imovel[0]);

					}

					if(imovel[1] != null){

						helper.setIdLocalidade((Integer) imovel[1]);

					}

					if(imovel[2] != null){

						helper.setCodigoSetorComercial((Integer) imovel[2]);

					}

					if(imovel[3] != null){

						helper.setNumeroQuadra((Integer) imovel[3]);

					}

					if(imovel[4] != null){

						helper.setLote((Short) imovel[4]);

					}

					if(imovel[5] != null){

						helper.setSubLote((Short) imovel[5]);

					}

					if(imovel[6] != null){

						helper.setIdCobrancaGrupo((Integer) imovel[6]);

					}

					if(imovel[7] != null){

						helper.setIdLigacaoAguaSituacao((Integer) imovel[7]);

					}

					if(imovel[8] != null){

						helper.setIdLigacaoEsgotoSituacao((Integer) imovel[8]);

					}

					if(imovel[9] != null){

						helper.setNumeroMorador((Short) imovel[9]);

					}

					if(imovel[10] != null){

						helper.setAreaConstruida((BigDecimal) imovel[10]);

					}

					if(imovel[11] != null){

						helper.setIdLogradouro((Integer) imovel[11]);

					}

					if(imovel[12] != null){

						helper.setCodigoCep((Integer) imovel[12]);

					}

					if(imovel[13] != null){

						helper.setCodigoBairro((Integer) imovel[13]);

					}

					if(imovel[14] != null){

						helper.setReferencia((Integer) imovel[14]);

					}

					if(imovel[15] != null){

						helper.setNumeroImovel((String) imovel[15]);

					}

					if(imovel[16] != null){

						helper.setComplemento((String) imovel[16]);

					}

					if(imovel[17] != null){

						helper.setVolumeReservatorioInferior((Integer) imovel[17]);

					}

					if(imovel[18] != null){

						helper.setVolumeReservatorioSuperior((Integer) imovel[18]);

					}

					if(imovel[19] != null){

						helper.setVolumePiscina((Integer) imovel[19]);

					}

					if(imovel[20] != null){

						helper.setJardim((Short) imovel[20]);

					}

					if(imovel[21] != null){

						helper.setIdPavimentoRua((Integer) imovel[21]);

					}

					if(imovel[22] != null){

						helper.setIdPavimentoCalcada((Integer) imovel[22]);

					}

					if(imovel[23] != null){

						helper.setNumeroPontosUtilizacao((Short) imovel[23]);

					}

					if(imovel[24] != null){

						helper.setIdImovelPerfil((Integer) imovel[24]);

					}

					if(imovel[25] != null){

						helper.setIdDespejo((Integer) imovel[25]);

					}

					if(imovel[26] != null){

						helper.setIdPoco((Integer) imovel[26]);

					}

					if(imovel[27] != null){

						helper.setIdFonteAbastecimento((Integer) imovel[27]);

					}

					if(imovel[28] != null){

						helper.setNumeroIptu((BigDecimal) imovel[28]);

					}

					if(imovel[29] != null){

						helper.setNumeroCelpe((Long) imovel[29]);

					}

					emitirDocumentoCobrancaBoletimCadastroHelper.add(helper);

				}

			}

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

		return emitirDocumentoCobrancaBoletimCadastroHelper;

	}

	/**
	 * @author Vivianne Sousa
	 * @date 19/09/2007
	 * @return ImovelCobrancaSituacao
	 * @throws ErroRepositorioException
	 */
	public CobrancaSituacao pesquisarImovelCobrancaSituacao(Integer idImovel) throws ErroRepositorioException{

		CobrancaSituacao retorno = null;
		Session session = HibernateUtil.getSession();

		try{
			String consulta = "select iCS.cobrancaSituacao " + " from ImovelCobrancaSituacao iCS "
			// + " inner join iCS.imovel imovel "
							+ " where iCS.imovel = :idImovel" + " and iCS.dataRetiradaCobranca is null ";

			retorno = (CobrancaSituacao) session.createQuery(consulta).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisas todas as Situações Especiais de Cobrança ativas de um determinado imóvel
	 * 
	 * @author Saulo Lima
	 * @date 09/09/2013
	 * @param idImovel
	 * @return Collection<ImovelCobrancaSituacao>
	 * @throws ErroRepositorioException
	 */
	public Collection<ImovelCobrancaSituacao> pesquisarImovelCobrancaSituacaoAtivas(Integer idImovel) throws ErroRepositorioException{

		Collection<ImovelCobrancaSituacao> retorno = null;
		Session session = HibernateUtil.getSession();

		try{
			String consulta = "SELECT ics FROM ImovelCobrancaSituacao ics INNER JOIN FETCH ics.cobrancaSituacao cobSit "
							+ "WHERE ics.imovel.id = :idImovel AND ics.dataRetiradaCobranca IS NULL";

			retorno = (Collection<ImovelCobrancaSituacao>) session.createQuery(consulta).setInteger("idImovel", idImovel).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Short pesquisarObterQuantidadeEconomias(Imovel imovel, Categoria categoria) throws ErroRepositorioException{

		Short retorno = null;
		Number soma = 0;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select sum( imsc.quantidadeEconomias ) " + "from ImovelSubcategoria as imsc " + "where "
							+ "  imsc.comp_id.imovel.id = :imovelId and " + "  imsc.comp_id.subcategoria.categoria.id = :categoriaId ";

			soma = ((Number) session.createQuery(consulta).setInteger("imovelId", imovel.getId().intValue()).setInteger("categoriaId",
							categoria.getId().intValue()).uniqueResult());

			if(soma != null){
				retorno = soma.shortValue();
			}else{
				retorno = null;
			}

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
	 * [UC0541] Emitir 2 Via de Conta Internet
	 * 
	 * @author Vivianne Sousa
	 * @date 02/09/2007
	 * @throws ErroRepositorioException
	 */

	public Imovel pesquisarDadosImovel(Integer idImovel) throws ErroRepositorioException{

		Imovel imovel = null;
		String consulta = "";
		Session session = HibernateUtil.getSession();

		try{

			consulta = "SELECT imov " + " FROM Imovel imov " + " LEFT JOIN FETCH imov.localidade loc "
							+ " LEFT JOIN FETCH imov.setorComercial setorCom " + " LEFT JOIN FETCH imov.quadra quad "
							+ " INNER JOIN FETCH imov.ligacaoAguaSituacao ligAguaSit "
							+ " INNER JOIN FETCH imov.ligacaoEsgotoSituacao ligEsgotoSit " + " WHERE " + " imov.id = :idImovel";

			imovel = (Imovel) session.createQuery(consulta).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		// retorna o imóvel
		return imovel;
	}

	/**
	 * O método abaixo realiza uma pesquisa em imovel e retorna os campos
	 * necessários para a criação da inscrição e de rota para exibição.
	 * 
	 * @author Vivianne Sousa
	 * @date 06/11/2007
	 */

	public Collection pesquisarInscricaoImoveleRota(String idsImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta = "";
		Collection retorno = null;

		try{

			consulta = "SELECT imov.id, " + " loc.id, " + " setorCom.codigo, " + " quad.numeroQuadra, " + " imov.lote, "
							+ " imov.subLote, " + " imov.numeroSequencialRota, " + " rot.codigo, " + " imov.quantidadeEconomias "
							+ " FROM Imovel imov " + " LEFT JOIN imov.localidade loc " + " LEFT JOIN imov.setorComercial setorCom "
							+ " LEFT JOIN imov.quadra quad " + " LEFT JOIN quad.rota rot " + " WHERE " + " imov.id in (" + idsImovel + ") "
							+ " ORDER BY " + " loc.id, " + " setorCom.codigo, " + " rot.codigo, "
							// + " imov.numeroSequencialRota, "
							+ " quad.numeroQuadra ";
			// + " imov.lote, "
			// + " imov.subLote ";

			retorno = session.createQuery(consulta)
			// .setString("idsImovel",idsImovel)
							.list();

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
	 * Pesquisa os imóveis do cliente de acordo com o tipo de relação
	 * [UC0251] Gerar Atividade de Ação de Cobrança [SB0001] Gerar Atividade de
	 * Ação de Cobrança para os Imóveis do Cliente
	 * 
	 * @author Sávio Luiz
	 * @created 23/11/2007
	 * @param cliente
	 * @param relacaoClienteImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarImoveisClientesRelacao(Collection idsCliente,

	Integer numeroInicial, Integer idSetorComercial)

	throws ErroRepositorioException{

		// cria a coleção de retorno

		Collection retorno = null;

		// Query

		StringBuilder consulta = new StringBuilder();

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			consulta.append("select distinct im.id, ")// 0

							.append("rt.id, ") // 1

							.append("im.ligacaoAguaSituacao.id, ") // 2

							.append("im.ligacaoEsgotoSituacao.id, ") // 3

							.append("im.imovelPerfil.id, ") // 4

							.append("rt.empresa.id, ") // 5

							.append("im.localidade.id, ") // 6

							.append("scm.codigo, ") // 7

							.append("qd.numeroQuadra, ") // 8

							.append("im.lote, ") // 9

							.append("im.subLote, ") // 10

							.append("qd.id, ") // 11

							.append("im.cobrancaSituacaoTipo.id, ") // 12

							.append("im.indicadorDebitoConta, ") // 13

							.append("rt.empresaCobranca.id, ") // 14

							.append("im.indicadorExclusao ") // 15

							.append("from ClienteImovel ci ")

							.append("inner join ci.imovel im ")

							.append("inner join ci.cliente cl ")

							.append("inner join im.quadra qd ")

							.append("inner join im.rota rt ")

							.append("inner join im.setorComercial scm ")

							.append("where cl.cliente.id in (:idsClientes) and ci.dataFimRelacao is null ");

			if(idSetorComercial != null){
				consulta.append("and scm.id = :idSetorComercial ");
			}

			consulta.append(" order by im.id");

			Query query = session.createQuery(consulta.toString()).setParameterList("idsClientes", idsCliente);

			if(idSetorComercial != null){
				query.setInteger("idSetorComercial", idSetorComercial);
			}

			retorno = query.setFirstResult(numeroInicial).setMaxResults(500).list();

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
	 * @author eduardo henrique
	 * @date 12/11/2008
	 *       Alteração no método para adequação dos campos blob oracle
	 */
	public Collection<ImovelCadastroOcorrencia> pesquisarOcorrenciasCadastro(String idImovel)

	throws ErroRepositorioException{

		Collection<ImovelCadastroOcorrencia> retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = "select imovelCad.cadastroOcorrencia.descricao, " +

			"imovelCad.dataOcorrencia, " +

			"imovelCad.funcionario.nome, " +

			"imovelCad.fotoOcorrencia, " +

			"imovelCad.id " +

			"from ImovelCadastroOcorrencia imovelCad " +

			"where imovelCad.imovel.id = :idImovel ";

			retorno = session.createQuery(consulta).setString("idImovel", idImovel).list();

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
	 * @author eduardo henrique
	 * @date 12/11/2008
	 *       Alteração no método para adequação dos campos blob oracle
	 */
	public Collection<ImovelEloAnormalidade> pesquisarEloAnormalidade(String idImovel)

	throws ErroRepositorioException{

		Collection<ImovelEloAnormalidade> retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = null;

		try{

			consulta = "select imovelEloAnor.eloAnormalidade.descricao, " +

			"imovelEloAnor.dataAnormalidade, " +

			"imovelEloAnor.funcionario.nome, " +

			"imovelEloAnor.fotoAnormalidade, " +

			"imovelEloAnor.id " +

			"from ImovelEloAnormalidade imovelEloAnor " +

			"where imovelEloAnor.imovel.id = :idImovel ";

			retorno = session.createQuery(consulta).setString("idImovel", idImovel).list();

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
	 * Método responsável por obter o proprietário do imóvel.
	 * 
	 * @author Virgínia Melo
	 * @date 04/06/2009
	 * @param idImovel
	 * @return ClienteImovel
	 */
	public ClienteImovel pesquisarClienteProprietarioImovel(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		ClienteImovel clienteImovel = null;

		try{
			Criteria criteria = session.createCriteria(ClienteImovel.class).add(
							Expression.eq("clienteRelacaoTipo.id", Integer.valueOf(ClienteRelacaoTipo.PROPRIETARIO))).add(
							Expression.eq("imovel.id", idImovel)).setFetchMode("cliente", FetchMode.JOIN);

			List<ClienteImovel> lista = criteria.list();
			if(lista != null && !lista.isEmpty()){
				clienteImovel = lista.get(0);
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return clienteImovel;
	}

	public Integer verificarExistenciaImovelParaCliente(Integer idImovel)

	throws ErroRepositorioException{

		// cria a coleção de retorno

		Integer retorno = null;

		// Query

		String consulta;

		// obtém a sessão

		Session session = HibernateUtil.getSession();

		try{

			// pesquisa a coleção de atividades e atribui a variável "retorno"

			consulta = "select imov.id " + "from ClienteImovel clienteImovel "

			+ "inner join clienteImovel.cliente cli "

			+ "inner join clienteImovel.imovel imov "

			+ "inner join clienteImovel.clienteRelacaoTipo crt "

			+ "where crt.id = :idProprietario AND "

			+ "imov.id = :idImovel AND " + "cli.id = :idCliente";

			retorno = (Integer) session.createQuery(consulta).setInteger(

			"idProprietario", ClienteRelacaoTipo.PROPRIETARIO)

			.setInteger("idImovel", idImovel).setInteger("idCliente",

			Cliente.CODIGO_CLIENTE_MARIO_GOUVEIA)

			.setMaxResults(1).uniqueResult();

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
	 * Atualiza a situação de cobrança do imóvel
	 */

	public void atualizarSituacaoCobrancaImovel(Integer idSituacaoCobranca, Integer idImovel) throws ErroRepositorioException{

		String consulta = "";
		Session session = HibernateUtil.getSession();

		try{

			consulta = "update gcom.cadastro.imovel.Imovel set "
							+ "cbst_id = :idSituacaoCobranca, imov_tmultimaalteracao = :ultimaAlteracao " + "where imov_id = :idImovel ";

			session.createQuery(consulta).setInteger("idSituacaoCobranca", idSituacaoCobranca).setTimestamp("ultimaAlteracao", new Date())
							.setInteger("idImovel", idImovel).executeUpdate();

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão
			HibernateUtil.closeSession(session);

		}

	}

	public Imovel pesquisarImovelOrdemServico(Integer numeroOS) throws ErroRepositorioException{

		Session sessao = HibernateUtil.getSession();
		Imovel imovel = null;
		try{
			String hql = "select i from OrdemServico os join os.imovel i where os.id :idOS";
			Query qry = sessao.createQuery(hql);
			imovel = (Imovel) qry.setInteger("idOS", numeroOS).uniqueResult();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(sessao);
		}
		return imovel;
	}

	public Integer consultarClienteRelacaoTipoPorImovel(Integer idImovel, Short idClienteRelacaoTipo)

	throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append(" select clim.clie_id as idCliente ").append(" from cliente_imovel clim ").append(
							" inner join imovel imov on imov.imov_id = clim.imov_id ").append(
							" where clim.imov_id = :idImovel and imov.imov_icexclusao != 1 and ").append(
							" clim.crtp_id = :idClienteUsuario and clim.clim_dtrelacaofim is null  ");

			retorno = (Integer) session.createSQLQuery(consulta.toString()).addScalar("idCliente", Hibernate.INTEGER).setInteger(
"idImovel", idImovel).setShort("idClienteUsuario", idClienteRelacaoTipo).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{
			HibernateUtil.closeSession(session);
			consulta = null;
		}

		return retorno;

	}

	public HidrometroInstalacaoHistorico pesquisarHidrometroPorImovel(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		HidrometroInstalacaoHistorico hidrometro = null;
		String query = "select i.hidrometroInstalacaoHistorico from Imovel i where i.id = :idImovel";
		try{
			hidrometro = (HidrometroInstalacaoHistorico) session.createQuery(query).setInteger("idImovel", idImovel).uniqueResult();
		}catch(Exception e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return hidrometro;
	}

	public HidrometroInstalacaoHistorico pesquisarHidrometroPorLigacaoAgua(Integer idLigacaoAgua) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		HidrometroInstalacaoHistorico hidrometro = null;
		String query = "select l.hidrometroInstalacaoHistorico from LigacaoAgua l where l.id = :id";
		try{
			hidrometro = (HidrometroInstalacaoHistorico) session.createQuery(query).setInteger("id", idLigacaoAgua).uniqueResult();
		}catch(Exception e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return hidrometro;
	}

	/**
	 * Obtem o ConsumoFaixaAreaCategoria da categoria com a area informada
	 * 
	 * @author isilva
	 * @date 21/01/2011
	 * @param idCategoria
	 * @param area
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ConsumoFaixaAreaCategoria pesquisarConsumoFaixaAreaCategoriaPorCategoriaArea(Integer idCategoria, Integer area)
					throws ErroRepositorioException{

		ConsumoFaixaAreaCategoria retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer hql = new StringBuffer();
		Query query = null;
		Map parameters = new HashMap();

		try{

			hql.append("select grupou ");
			hql.append("from ConsumoFaixaAreaCategoria grupou ");

			hql.append("where ");

			if(idCategoria != null){
				hql.append("grupou.categoria.id = :idCategoria ");
				parameters.put("idCategoria", idCategoria);
			}

			if(area != null){
				hql.append("and grupou.faixaInicialArea <= :area ");
				hql.append("and grupou.faixaFinalArea >= :area ");
				parameters.put("area", area);
			}

			hql.append("and grupou.indicadorUso = :idIndicadorUso ");
			parameters.put("idIndicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO);

			query = session.createQuery(hql.toString());

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Integer){
					Integer paramInteger = (Integer) parameters.get(key);
					query.setParameter(key, paramInteger);
				}else if(parameters.get(key) instanceof Short){
					Short paramShort = (Short) parameters.get(key);
					query.setParameter(key, paramShort);
				}else{
					query.setParameter(key, parameters.get(key));
				}
			}

			retorno = (ConsumoFaixaAreaCategoria) query.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [FS0003] - Verificar se imóvel já esta suprimido Judicialmente.
	 * 
	 * @author isilva
	 * @date 08/02/2011
	 * @return
	 * @throws ControladorException
	 */
	public Boolean verificarImovelSuprimidoJudicial(Integer idImovel) throws ErroRepositorioException{

		Boolean retorno = false;

		Session session = HibernateUtil.getSession();
		StringBuffer hql = new StringBuffer();
		Query query = null;
		Map parameters = new HashMap();

		try{

			hql.append("select count(*) ");
			hql.append("from ImovelEsgotoJudicial iej ");
			hql.append("where ");
			hql.append("iej.ligacaoEsgotoSituacaoExclusao is null and ");
			hql.append("iej.indicadorUso = :indicadorUso and ");
			hql.append("iej.imovel.id = :idImovel ");
			parameters.put("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO);
			parameters.put("idImovel", idImovel);

			query = session.createQuery(hql.toString());

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();
			while(iterMap.hasNext()){
				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Short){
					Short paramShort = (Short) parameters.get(key);
					query.setParameter(key, paramShort);
				}else if(parameters.get(key) instanceof Integer){
					Integer paramShort = (Integer) parameters.get(key);
					query.setParameter(key, paramShort);
				}else{
					query.setParameter(key, parameters.get(key));
				}
			}

			Long retornoConsulta = (Long) query.setMaxResults(1).uniqueResult();

			if(retornoConsulta != null && retornoConsulta.intValue() > 0){
				retorno = true;
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * @author Ailton Sousa
	 * @date 11/02/2011
	 *       Pesquisa uma coleção de imóveis com uma query especifica
	 * @param idLocalidade
	 * @param idSetorComercial
	 * @param rota
	 * @param segmento
	 * @param lote
	 * @param sublote
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 */
	public Collection pesquisarImovel(Integer idLocalidade,

	Integer idSetorComercial, Integer idRota, Short segmento,

	Short lote, Short sublote, int indicadorExclusao)

	throws ErroRepositorioException{

		// cria a coleção de retorno
		Collection retorno = null;

		// Query
		String consulta;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{

			// pesquisa a coleção de atividades e atribui a variável "retorno"
			consulta = "SELECT imovel.id, imovel.localidade, "

			+ "imovel.setorComercial, rotaImovel.id, "

			+ "imovel.numeroSegmento, imovel.lote, imovel.subLote, "

			+ "ftst.id " + "FROM gcom.cadastro.imovel.Imovel  imovel "

			+ "LEFT JOIN imovel.faturamentoSituacaoTipo ftst "

			+ "LEFT JOIN imovel.rota rotaImovel ";

			consulta = consulta

			+ "where (imovel.indicadorExclusao IS NULL or imovel.indicadorExclusao != "

			+ indicadorExclusao + ")";

			if(idLocalidade != null){

				consulta = consulta + "and imovel.localidade.id = " + idLocalidade.intValue();

			}

			if(idSetorComercial != null){

				consulta = consulta + "and imovel.setorComercial.id = " + idSetorComercial.intValue();

			}

			if(idRota != null){

				consulta = consulta + " and imovel.rota.id = " + idRota;

			}

			if(segmento != null){

				consulta = consulta + " and imovel.numeroSegmento = " + segmento.intValue();

			}

			if(lote != null){

				consulta = consulta + " and imovel.lote = " + lote.intValue();

			}

			if(sublote != null){

				consulta = consulta + " and imovel.subLote = " + sublote.intValue();

			}

			retorno = session.createQuery(consulta).list();

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
	 * [UC0XXX] Gerar Relatório Boletim de Cadastro
	 * 
	 * @author Anderson Italo
	 * @date 19/04/2011
	 *       Obter dados dos Imóveis pelos parametros informados
	 */
	public Collection pesquisarRelatorioBoletimCadastro(FiltrarRelatorioBoletimCadastroHelper filtro, int totalPaginacao)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();
		Query query = null;
		Map parameters = new HashMap();
		Collection retorno = null;

		try{

			consulta.append("select distinct imov.id, imov.localidade.id, setor.codigo, ");
			consulta.append("quad.numeroQuadra, imov.lote, imov.subLote, rot.cobrancaGrupo.id, ");
			consulta.append("imov.ligacaoAguaSituacao.id, imov.ligacaoEsgotoSituacao.id, ");
			consulta.append("imov.numeroMorador, imov.areaConstruida, logr.id, ");
			consulta.append("cep.codigo, bair.codigo, imov.enderecoReferencia.id, ");
			consulta.append("imov.numeroImovel, imov.complementoEndereco, ");
			consulta.append("imov.reservatorioVolumeFaixaInferior.id, imov.reservatorioVolumeFaixaSuperior.id, ");
			consulta.append("imov.piscinaVolumeFaixa.id, ");
			consulta.append("imov.indicadorJardim, imov.pavimentoRua.id, imov.pavimentoCalcada.id, ");
			consulta.append("imov.numeroPontosUtilizacao, imov.imovelPerfil.id, imov.despejo.id, ");
			consulta.append("imov.pocoTipo.id, imov.fonteAbastecimento.id, ");
			consulta.append("imov.numeroIptu, imov.numeroCelpe, rotaImovel.id, imov.numeroSegmento, bair.nome, imov.localidade, ");
			consulta.append("bair.municipio, imov.enderecoReferencia, last.descricao, lest.descricao, ftab.descricao ");

			if((!Util.isVazioOuBranco(filtro.getInformouDadosCliente()) && !Util.isVazioOuBranco(filtro.getInformouDadosCaracteristicas()))
							|| !Util.isVazioOuBranco(filtro.getInformouDadosCaracteristicas())){

				if(!Util.isVazioOuBranco(filtro.getInformouDadosCliente())) consulta
								.append("from ImovelSubcategoria imovsbct, ClienteImovel cliimov ");
				else consulta.append("from ImovelSubcategoria imovsbct ");

				if(!Util.isVazioOuBranco(filtro.getInformouDadosCaracteristicas())){

					consulta.append("left join imovsbct.comp_id.subcategoria sbct ");
					consulta.append("left join sbct.categoria cat ");
					consulta.append("inner join imovsbct.comp_id.imovel imov ");
				}
			}else if(!Util.isVazioOuBranco(filtro.getInformouDadosCliente())){

				consulta.append("from ClienteImovel cliimov ");
				consulta.append("inner join cliimov.imovel imov ");
			}else{

				consulta.append("from Imovel imov ");
			}

			consulta.append("inner join imov.setorComercial setor ");
			consulta.append("left join imov.logradouroBairro logba ");
			consulta.append("left join logba.logradouro logr ");
			consulta.append("inner join logba.bairro bair ");
			consulta.append("left join bair.municipio muni ");
			consulta.append("left join imov.logradouroCep logce ");
			consulta.append("inner join logce.cep cep ");
			consulta.append("left join imov.enderecoReferencia edrf ");
			consulta.append("left join imov.ligacaoAguaSituacao last ");
			consulta.append("left join imov.ligacaoEsgotoSituacao lest ");
			consulta.append("left join imov.fonteAbastecimento ftab ");

			if(!Util.isVazioOuBranco(filtro.getInformouDadosLocalizacao())){

				if(!Util.isVazioOuBranco(filtro.getIdMunicipio())){
					consulta.append("inner join bair.municipio muni ");
				}
			}

			if(!Util.isVazioOuBranco(filtro.getInformouDadosCliente())){
				consulta.append("inner join cliimov.cliente cli ");
			}

			if(!Util.isVazioOuBranco(filtro.getIdGerenciaRegional()) || !Util.isVazioOuBranco(filtro.getIdUnidadeNegocio())){
				consulta.append("inner join imov.localidade loca ");
			}

			if(!Util.isVazioOuBranco(filtro.getIdGerenciaRegional())){
				consulta.append("inner join loca.gerenciaRegional ger ");
			}

			if(!Util.isVazioOuBranco(filtro.getIdUnidadeNegocio())){
				consulta.append("inner join loca.unidadeNegocio uneg ");
			}

			consulta.append("inner join imov.quadra quad ");
			consulta.append("inner join imov.rota rot ");
			consulta.append("left join imov.rota rotaImovel ");

			if(!Util.isVazioOuBranco(filtro.getInformouDadosLigacao())){

				if(!Util.isVazioOuBranco(filtro.getIndicadorMedicao()) && filtro.getIndicadorMedicao().equals("comMedicao")){

					consulta.append("inner join imov.ligacaoAgua lagu ");
					consulta.append("inner join imov.ligacaoEsgotoSituacao lest  ");

				}else{

					consulta.append("left join imov.ligacaoAgua lagu ");
					consulta.append("left join imov.ligacaoEsgotoSituacao lest ");

					if(!Util.isVazioOuBranco(filtro.getIdTipoMedicao())){

						consulta.append("inner join lagu.hidrometroInstalacaoHistorico hhis ");
						consulta.append("inner join hhis.hidrometro hid ");
						consulta.append("inner join hid.medicaoTipo medti ");
					}
				}
			}

			if(!Util.isVazioOuBranco(filtro.getInformouDadosCaracteristicas())){

				if(!Util.isVazioOuBranco(filtro.getIdImovelPerfil())) consulta.append("inner join imov.imovelPerfil iper ");
			}

			// Verificação de parâmetros do filtro
			consulta.append("Where ");

			// Dados da localização
			if(!Util.isVazioOuBranco(filtro.getInformouDadosLocalizacao())){

				// Gerencia Regional
				if(!Util.isVazioOuBranco(filtro.getIdGerenciaRegional())){

					consulta.append(" and ger.id = :idGerencia ");
					parameters.put("idGerencia", Util.obterInteger(filtro.getIdGerenciaRegional()));
				}

				// Unidade de Negócio
				if(!Util.isVazioOuBranco(filtro.getIdUnidadeNegocio())){

					consulta.append("and uneg.id = :idUnidadeNegocio ");
					parameters.put("idUnidadeNegocio", Util.obterInteger(filtro.getIdUnidadeNegocio()));
				}

				// Dados da Inscrição
				if(!Util.isVazioOuBranco(filtro.getIdLocalidadeInicial()) && !Util.isVazioOuBranco(filtro.getIdLocalidadeFinal())){

					// Localidade
					consulta.append("and (imov.localidade.id >= :idLocalidadeInicial and imov.localidade.id <= :idLocalidadeFinal) ");
					parameters.put("idLocalidadeInicial", Integer.valueOf(filtro.getIdLocalidadeInicial()));
					parameters.put("idLocalidadeFinal", Integer.valueOf(filtro.getIdLocalidadeFinal()));

					// Setor Comercial
					if(!Util.isVazioOuBranco(filtro.getCodigoSetorInicial()) && !Util.isVazioOuBranco(filtro.getCodigoSetorFinal())){

						consulta.append("and (setor.codigo >= :codigoSetorInicial and setor.codigo <= :codigoSetorFinal) ");
						parameters.put("codigoSetorInicial", Util.obterInteger(filtro.getCodigoSetorInicial()));
						parameters.put("codigoSetorFinal", Util.obterInteger(filtro.getCodigoSetorFinal()));
					}

					// Quadra
					if(!Util.isVazioOuBranco(filtro.getQuadraInicial()) && !Util.isVazioOuBranco(filtro.getQuadraFinal())){

						consulta.append("and (quad.numeroQuadra >= :numeroQuadraInicial ");
						consulta.append("and quad.numeroQuadra <= :numeroQuadraFinal) ");
						parameters.put("numeroQuadraInicial", Util.obterInteger(filtro.getQuadraInicial()));
						parameters.put("numeroQuadraFinal", Util.obterInteger(filtro.getQuadraFinal()));
					}

					// Rota
					if(!Util.isVazioOuBranco(filtro.getCodigoRotaInicial()) && !Util.isVazioOuBranco(filtro.getCodigoRotaFinal())){

						consulta.append("and (rot.id >= :codigoRotaInicial and rot.id <= :codigoRotaFinal) ");
						parameters.put("codigoRotaInicial", Util.obterInteger(filtro.getCodigoRotaInicial()));
						parameters.put("codigoRotaFinal", Util.obterInteger(filtro.getCodigoRotaFinal()));
					}

					// Segmento
					if(!Util.isVazioOuBranco(filtro.getSegmentoInicial()) && !Util.isVazioOuBranco(filtro.getSegmentoFinal())){

						consulta.append("and (imov.numeroSegmento >= :segmentoInicial and imov.numeroSegmento<= :segmentoFinal) ");
						parameters.put("segmentoInicial", Short.valueOf(filtro.getSegmentoInicial()));
						parameters.put("segmentoFinal", Short.valueOf(filtro.getSegmentoFinal()));
					}

					// Lote
					if(!Util.isVazioOuBranco(filtro.getLoteInicial()) && !Util.isVazioOuBranco(filtro.getLoteFinal())){

						consulta.append("and (imov.lote >= :loteInicial and imov.lote <= :loteFinal) ");
						parameters.put("loteInicial", Short.valueOf(filtro.getLoteInicial()));
						parameters.put("loteFinal", Short.valueOf(filtro.getLoteFinal()));
					}

					// SubLote
					if(!Util.isVazioOuBranco(filtro.getSubLoteInicial()) && !Util.isVazioOuBranco(filtro.getSubLoteFinal())){

						consulta.append("and (imov.subLote >= :subLoteInicial and imov.subLote <= :subLoteFinal) ");
						parameters.put("subLoteInicial", Short.valueOf(filtro.getSubLoteInicial()));
						parameters.put("subLoteFinal", Short.valueOf(filtro.getSubLoteFinal()));
					}
				}
			}

			// Município
			if(!Util.isVazioOuBranco(filtro.getIdMunicipio())){

				consulta.append("and muni.id = :idMunicipio ");
				parameters.put("idMunicipio", Util.obterInteger(filtro.getIdMunicipio()));
			}

			// Bairro
			if(!Util.isVazioOuBranco(filtro.getCodigoBairro())){

				consulta.append("and bair.codigo = :codigoBairro ");
				parameters.put("codigoBairro", Util.obterInteger(filtro.getCodigoBairro()));
			}

			// Logradouro
			if(!Util.isVazioOuBranco(filtro.getIdLogradouro())){

				consulta.append("and logba.logradouro.id = :idLogradouro ");
				parameters.put("idLogradouro", Util.obterInteger(filtro.getIdLogradouro()));
			}

			// CEP
			if(!Util.isVazioOuBranco(filtro.getCep())){

				consulta.append("and cep.codigo = :codigoCep ");
				parameters.put("codigoCep", Util.obterInteger(filtro.getCep()));
			}

			// Dados do Cliente
			// Cliente
			if(!Util.isVazioOuBranco(filtro.getIdCliente())){

				consulta.append("and cli.id = :idCliente ");
				parameters.put("idCliente", Util.obterInteger(filtro.getIdCliente()));
			}

			// Tipo da Relação
			if(Util.verificarIdNaoVazio(filtro.getIdClienteRelacaoTipo())){

				consulta.append("and cliimov.clienteRelacaoTipo.id = :idRelacaoTipo ");
				consulta.append("and cliimov.dataFimRelacao is null ");
				parameters.put("idRelacaoTipo", Util.obterInteger(filtro.getIdClienteRelacaoTipo()));
			}

			// Tipo do Cliente
			if(Util.verificarIdNaoVazio(filtro.getIdClienteTipo())){

				consulta.append("and cli.clienteTipo.id = :idClienteTipo ");
				parameters.put("idClienteTipo", Util.obterInteger(filtro.getIdClienteTipo()));
			}

			// Dados da Ligação
			// Situação da Ligação de Água
			if(!Util.isVazioOuBranco(filtro.getInformouDadosLigacao())){

				consulta.append("and (lagu.id = imov.id or lest.id = imov.id) ");
			}

			if(Util.verificarIdNaoVazio(filtro.getIdSituacaoLigacaoAgua())){

				consulta.append("and imov.ligacaoAguaSituacao.id = :idLigacaoAguaSituacao ");
				parameters.put("idLigacaoAguaSituacao", Util.obterInteger(filtro.getIdSituacaoLigacaoAgua()));
			}

			// Situação da Ligação de Esgoto
			if(Util.verificarIdNaoVazio(filtro.getIdSituacaoLigacaoEsgoto())){

				consulta.append("and imov.ligacaoEsgotoSituacao.id = :idLigacaoEsgotoSituacao ");
				parameters.put("idLigacaoEsgotoSituacao", Util.obterInteger(filtro.getIdSituacaoLigacaoEsgoto()));
			}

			// Dados das Características
			// Perfil do Imóvel
			if(Util.verificarIdNaoVazio(filtro.getIdImovelPerfil())){

				consulta.append("and imov.imovelPerfil.id = :idImovelPerfil ");
				parameters.put("idImovelPerfil", Util.obterInteger(filtro.getIdImovelPerfil()));
			}

			// Categoria
			if(Util.verificarIdNaoVazio(filtro.getIdCategoria())){

				consulta.append("and cat.id = :idCategoria ");
				parameters.put("idCategoria", Util.obterInteger(filtro.getIdCategoria()));
			}

			// SubCategoria
			if(Util.verificarIdNaoVazio(filtro.getIdSubCategoria())){

				consulta.append("and sbct.id = :idSubCategoria ");
				parameters.put("idSubCategoria", Util.obterInteger(filtro.getIdSubCategoria()));
			}

			if(parameters.isEmpty()) query = session.createQuery(consulta.toString().replace("Where", ""));
			else query = session.createQuery(consulta.toString().replaceFirst(" and ", " "));

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();

			while(iterMap.hasNext()){

				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Collection){

					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else{

					query.setParameter(key, parameters.get(key));
				}
			}

			retorno = query.setFirstResult(totalPaginacao).setMaxResults(500).list();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0XXX] Gerar Relatório Boletim de Cadastro
	 * 
	 * @author Anderson Italo
	 * @date 19/04/2011
	 *       Obter Total dos Imóveis pelos parametros informados
	 */
	public Integer pesquisarTotalRegistrosRelatorioBoletimCadastro(FiltrarRelatorioBoletimCadastroHelper filtro)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();
		Query query = null;
		Map parameters = new HashMap();
		Integer retorno = null;
		Object retornoConsulta = null;

		try{

			consulta.append("select count(distinct imov.id) ");

			if((!Util.isVazioOuBranco(filtro.getInformouDadosCliente()) && !Util.isVazioOuBranco(filtro.getInformouDadosCaracteristicas()))
							|| !Util.isVazioOuBranco(filtro.getInformouDadosCaracteristicas())){

				if(!Util.isVazioOuBranco(filtro.getInformouDadosCliente())) consulta
								.append("from ImovelSubcategoria imovsbct, ClienteImovel cliimov ");
				else consulta.append("from ImovelSubcategoria imovsbct ");

				if(!Util.isVazioOuBranco(filtro.getInformouDadosCaracteristicas())){

					consulta.append("left join imovsbct.comp_id.subcategoria sbct ");
					consulta.append("left join sbct.categoria cat ");
					consulta.append("inner join imovsbct.comp_id.imovel imov ");
				}
			}else if(!Util.isVazioOuBranco(filtro.getInformouDadosCliente())){

				consulta.append("from ClienteImovel cliimov ");
				consulta.append("inner join cliimov.imovel imov ");
			}else{

				consulta.append("from Imovel imov ");
			}

			if(!Util.isVazioOuBranco(filtro.getInformouDadosLocalizacao())){

				if(!Util.isVazioOuBranco(filtro.getCodigoSetorInicial()) || !Util.isVazioOuBranco(filtro.getCodigoSetorFinal())){

					consulta.append("inner join imov.setorComercial setor ");
				}

				if(!Util.isVazioOuBranco(filtro.getIdMunicipio())){

					consulta.append("left join imov.logradouroBairro logba ");
					consulta.append("left join logba.logradouro logr ");
					consulta.append("inner join logba.bairro bair ");
					consulta.append("inner join bair.municipio muni ");
				}

				if(!Util.isVazioOuBranco(filtro.getCep())){

					consulta.append("left join imov.logradouroCep logce ");
					consulta.append("inner join logce.cep cep ");
				}
			}

			if(!Util.isVazioOuBranco(filtro.getIdCliente())) consulta.append("inner join cliimov.cliente cli ");

			if(!Util.isVazioOuBranco(filtro.getIdGerenciaRegional()) || !Util.isVazioOuBranco(filtro.getIdUnidadeNegocio())){
				consulta.append("inner join imov.localidade loca ");
			}

			if(!Util.isVazioOuBranco(filtro.getIdGerenciaRegional())){
				consulta.append("inner join loca.gerenciaRegional ger ");
			}

			if(!Util.isVazioOuBranco(filtro.getIdUnidadeNegocio())){
				consulta.append("inner join loca.unidadeNegocio uneg ");
			}

			consulta.append("inner join imov.quadra quad ");
			consulta.append("inner join imov.rota rot ");

			if(!Util.isVazioOuBranco(filtro.getInformouDadosLigacao())){

				if(!Util.isVazioOuBranco(filtro.getIndicadorMedicao()) && filtro.getIndicadorMedicao().equals("comMedicao")){

					consulta.append("inner join imov.ligacaoAgua lagu ");
					consulta.append("inner join imov.ligacaoEsgoto lest  ");

				}else{

					consulta.append("left join imov.ligacaoAgua lagu ");
					consulta.append("left join imov.ligacaoEsgoto lest   ");

					if(!Util.isVazioOuBranco(filtro.getIdTipoMedicao())){

						consulta.append("inner join lagu.hidrometroInstalacaoHistorico hhis ");
						consulta.append("inner join hhis.hidrometro hid ");
						consulta.append("inner join hid.medicaoTipo medti ");
					}
				}
			}

			if(!Util.isVazioOuBranco(filtro.getIdImovelPerfil())) consulta.append("inner join imov.imovelPerfil iper ");

			// Verificação de parâmetros do filtro
			consulta.append("Where ");

			if((!Util.isVazioOuBranco(filtro.getInformouDadosCliente()) && !Util.isVazioOuBranco(filtro.getInformouDadosCaracteristicas()))){

				consulta.append(" and cliimov.imovel.id = imov.id  ");
			}

			// Dados da localização
			if(!Util.isVazioOuBranco(filtro.getInformouDadosLocalizacao())){

				// Gerencia Regional
				if(!Util.isVazioOuBranco(filtro.getIdGerenciaRegional())){

					consulta.append(" and ger.id = :idGerencia ");
					parameters.put("idGerencia", Util.obterInteger(filtro.getIdGerenciaRegional()));
				}

				// Unidade de Negócio
				if(!Util.isVazioOuBranco(filtro.getIdUnidadeNegocio())){

					consulta.append("and uneg.id = :idUnidadeNegocio ");
					parameters.put("idUnidadeNegocio", Util.obterInteger(filtro.getIdUnidadeNegocio()));
				}

				// Dados da Inscrição
				if(!Util.isVazioOuBranco(filtro.getIdLocalidadeInicial()) && !Util.isVazioOuBranco(filtro.getIdLocalidadeFinal())){

					// Localidade
					consulta.append("and (imov.localidade.id >= :idLocalidadeInicial and imov.localidade.id <= :idLocalidadeFinal) ");
					parameters.put("idLocalidadeInicial", Integer.valueOf(filtro.getIdLocalidadeInicial()));
					parameters.put("idLocalidadeFinal", Integer.valueOf(filtro.getIdLocalidadeFinal()));

					// Setor Comercial
					if(!Util.isVazioOuBranco(filtro.getCodigoSetorInicial()) && !Util.isVazioOuBranco(filtro.getCodigoSetorFinal())){

						consulta.append("and (setor.codigo >= :codigoSetorInicial and setor.codigo <= :codigoSetorFinal) ");
						parameters.put("codigoSetorInicial", Util.obterInteger(filtro.getCodigoSetorInicial()));
						parameters.put("codigoSetorFinal", Util.obterInteger(filtro.getCodigoSetorFinal()));
					}

					// Quadra
					if(!Util.isVazioOuBranco(filtro.getQuadraInicial()) && !Util.isVazioOuBranco(filtro.getQuadraFinal())){

						consulta.append("and (quad.numeroQuadra >= :numeroQuadraInicial ");
						consulta.append("and quad.numeroQuadra <= :numeroQuadraFinal) ");
						parameters.put("numeroQuadraInicial", Util.obterInteger(filtro.getQuadraInicial()));
						parameters.put("numeroQuadraFinal", Util.obterInteger(filtro.getQuadraFinal()));
					}

					// Rota
					if(!Util.isVazioOuBranco(filtro.getCodigoRotaInicial()) && !Util.isVazioOuBranco(filtro.getCodigoRotaFinal())){

						consulta.append("and (rot.id >= :codigoRotaInicial and rot.id <= :codigoRotaFinal) ");
						parameters.put("codigoRotaInicial", Util.obterInteger(filtro.getCodigoRotaInicial()));
						parameters.put("codigoRotaFinal", Util.obterInteger(filtro.getCodigoRotaFinal()));
					}

					// Segmento
					if(!Util.isVazioOuBranco(filtro.getSegmentoInicial()) && !Util.isVazioOuBranco(filtro.getSegmentoFinal())){

						consulta.append("and (imov.numeroSegmento >= :segmentoInicial and imov.numeroSegmento<= :segmentoFinal) ");
						parameters.put("segmentoInicial", Short.valueOf(filtro.getSegmentoInicial()));
						parameters.put("segmentoFinal", Short.valueOf(filtro.getSegmentoFinal()));
					}

					// Lote
					if(!Util.isVazioOuBranco(filtro.getLoteInicial()) && !Util.isVazioOuBranco(filtro.getLoteFinal())){

						consulta.append("and (imov.lote >= :loteInicial and imov.lote <= :loteFinal) ");
						parameters.put("loteInicial", Short.valueOf(filtro.getLoteInicial()));
						parameters.put("loteFinal", Short.valueOf(filtro.getLoteFinal()));
					}

					// SubLote
					if(!Util.isVazioOuBranco(filtro.getSubLoteInicial()) && !Util.isVazioOuBranco(filtro.getSubLoteFinal())){

						consulta.append("and (imov.subLote >= :subLoteInicial and imov.subLote <= :subLoteFinal) ");
						parameters.put("subLoteInicial", Short.valueOf(filtro.getSubLoteInicial()));
						parameters.put("subLoteFinal", Short.valueOf(filtro.getSubLoteFinal()));
					}
				}
			}

			// Município
			if(!Util.isVazioOuBranco(filtro.getIdMunicipio())){

				consulta.append("and muni.id = :idMunicipio ");
				parameters.put("idMunicipio", Util.obterInteger(filtro.getIdMunicipio()));
			}

			// Bairro
			if(!Util.isVazioOuBranco(filtro.getCodigoBairro())){

				consulta.append("and bair.codigo = :codigoBairro ");
				parameters.put("codigoBairro", Util.obterInteger(filtro.getCodigoBairro()));
			}

			// Logradouro
			if(!Util.isVazioOuBranco(filtro.getIdLogradouro())){

				consulta.append("and logba.logradouro.id = :idLogradouro ");
				parameters.put("idLogradouro", Util.obterInteger(filtro.getIdLogradouro()));
			}

			// CEP
			if(!Util.isVazioOuBranco(filtro.getCep())){

				consulta.append("and cep.codigo = :codigoCep ");
				parameters.put("codigoCep", Util.obterInteger(filtro.getCep()));
			}

			// Dados do Cliente
			// Cliente
			if(!Util.isVazioOuBranco(filtro.getIdCliente())){

				consulta.append("and cli.id = :idCliente ");
				parameters.put("idCliente", Util.obterInteger(filtro.getIdCliente()));
			}

			// Tipo da Relação
			if(Util.verificarIdNaoVazio(filtro.getIdClienteRelacaoTipo())){

				consulta.append("and cliimov.clienteRelacaoTipo.id = :idRelacaoTipo ");
				consulta.append("and cliimov.dataFimRelacao is null ");
				parameters.put("idRelacaoTipo", Util.obterInteger(filtro.getIdClienteRelacaoTipo()));
			}

			// Tipo do Cliente
			if(Util.verificarIdNaoVazio(filtro.getIdClienteTipo())){

				consulta.append("and cliimov.cliente.clienteTipo.id = :idClienteTipo ");
				parameters.put("idClienteTipo", Util.obterInteger(filtro.getIdClienteTipo()));
			}

			// Dados da Ligação
			// Situação da Ligação de Água
			if(!Util.isVazioOuBranco(filtro.getInformouDadosLigacao())){

				consulta.append("and (lagu.id = imov.id or lest.id = imov.id) ");
			}

			if(Util.verificarIdNaoVazio(filtro.getIdSituacaoLigacaoAgua())){

				consulta.append("and imov.ligacaoAguaSituacao.id = :idLigacaoAguaSituacao ");
				parameters.put("idLigacaoAguaSituacao", Util.obterInteger(filtro.getIdSituacaoLigacaoAgua()));
			}

			// Situação da Ligação de Esgoto
			if(Util.verificarIdNaoVazio(filtro.getIdSituacaoLigacaoEsgoto())){

				consulta.append("and imov.ligacaoEsgotoSituacao.id = :idLigacaoEsgotoSituacao ");
				parameters.put("idLigacaoEsgotoSituacao", Util.obterInteger(filtro.getIdSituacaoLigacaoEsgoto()));
			}

			// Dados das Características
			// Perfil do Imóvel
			if(Util.verificarIdNaoVazio(filtro.getIdImovelPerfil())){

				consulta.append("and imov.imovelPerfil.id = :idImovelPerfil ");
				parameters.put("idImovelPerfil", Util.obterInteger(filtro.getIdImovelPerfil()));
			}

			// Categoria
			if(Util.verificarIdNaoVazio(filtro.getIdCategoria())){

				consulta.append("and cat.id = :idCategoria ");
				parameters.put("idCategoria", Util.obterInteger(filtro.getIdCategoria()));
			}

			// SubCategoria
			if(Util.verificarIdNaoVazio(filtro.getIdSubCategoria())){

				consulta.append("and sbct.id = :idSubCategoria ");
				parameters.put("idSubCategoria", Util.obterInteger(filtro.getIdSubCategoria()));
			}

			if(parameters.isEmpty()) query = session.createQuery(consulta.toString().replace("Where", ""));
			else query = session.createQuery(consulta.toString().replaceFirst(" and ", " "));

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();

			while(iterMap.hasNext()){

				String key = (String) iterMap.next();
				if(parameters.get(key) instanceof Collection){

					Collection collection = (ArrayList) parameters.get(key);
					query.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof Integer){
					Integer paramInteger = (Integer) parameters.get(key);
					query.setParameter(key, paramInteger);
				}else if(parameters.get(key) instanceof Short){
					Short paramShort = (Short) parameters.get(key);
					query.setParameter(key, paramShort);
				}else{

					query.setParameter(key, parameters.get(key));
				}
			}

			retornoConsulta = query.uniqueResult();

			if(retornoConsulta != null){
				retorno = ((Number) retornoConsulta).intValue();
			}

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(Exception e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Obterm o Funcionário que executou a operação em conta
	 * 
	 * @author isilva
	 * @date 02/05/2011
	 * @param idConta
	 * @param tipo
	 * @return
	 */
	public Funcionario obterFuncionarioExecutouOperacaoEmConta(Integer idConta, String tipo) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();
		Query query = null;
		Map parameters = new HashMap();
		Funcionario retornoConsulta = null;

		try{

			consulta.append("select ");

			if(!Util.isVazioOuBranco(tipo)){
				if("C".equalsIgnoreCase(tipo)){
					consulta.append("c.usuario.funcionario ");
					consulta.append("from Conta c ");
					consulta.append("where c.id = :idConta");
					parameters.put("idConta", idConta);
				}else if("CH".equalsIgnoreCase(tipo)){
					consulta.append("ch.usuario.funcionario ");
					consulta.append("from ContaHistorico ch ");
					consulta.append("where ch.id = :idConta");
					parameters.put("idConta", idConta);
				}

				if(parameters.isEmpty()){
					return null;
				}

				query = session.createQuery(consulta.toString());

				Set set = parameters.keySet();
				Iterator iterMap = set.iterator();

				while(iterMap.hasNext()){
					String key = (String) iterMap.next();
					if(parameters.get(key) instanceof Integer){
						Integer inteiro = (Integer) parameters.get(key);
						query.setParameter(key, inteiro);
					}else{
						query.setParameter(key, parameters.get(key));
					}
				}

				retornoConsulta = (Funcionario) query.uniqueResult();
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(Exception e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * [UC0XXX] Gerar Relatório Imóveis por Endereço
	 * Obter Total dos Imóveis pelos parametros informados
	 * 
	 * @date 14/06/2011
	 */
	public Integer pesquisarTotalRegistrosRelatorioImovelEndereco(String idImovelCondominio, String idImovelPrincipal,
					String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String[] idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,
					String intervaloValorPercentualEsgotoInicial, String intervaloValorPercentualEsgotoFinal,
					String intervaloMediaMinimaImovelInicial, String intervaloMediaMinimaImovelFinal,
					String intervaloMediaMinimaHidrometroInicial, String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,
					String idPocoTipo, String idFaturamentoSituacaoTipo, String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,
					String idEloAnormalidade, String areaConstruidaInicial, String areaConstruidaFinal, String idCadastroOcorrencia,
					String idConsumoTarifa, String idGerenciaRegional, String idLocalidadeInicial, String idLocalidadeFinal,
					String setorComercialInicial, String setorComercialFinal, String quadraInicial, String quadraFinal, String loteOrigem,
					String loteDestno, String cep, String logradouro, String bairro, String municipio, String idTipoMedicao,
					String indicadorMedicao, String idSubCategoria, String idCategoria, String quantidadeEconomiasInicial,
					String quantidadeEconomiasFinal, String diaVencimento, String idCliente, String idClienteTipo,
					String idClienteRelacaoTipo, String numeroPontosInicial, String numeroPontosFinal, String numeroMoradoresInicial,
					String numeroMoradoresFinal, String idAreaConstruidaFaixa, String idUnidadeNegocio, String rotaInicial,
					String rotaFinal, String sequencialRotaInicial, String sequencialRotaFinal, String segmentoInicial,
					String segmentoFinal, String subloteInicial, String subloteFinal) throws ErroRepositorioException{

		Integer retorno = 0;
		Object retornoConsulta = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{

			consulta = consulta.append("select count (distinct (imovel.imov_id)) as quantidade  ");
			consulta.append("from imovel_subcategoria imovelSubcategoria ");
			consulta.append("inner join imovel on imovelSubcategoria.imov_id = imovel.imov_id ");
			consulta.append("inner join localidade on imovel.loca_id = localidade.loca_id ");
			consulta.append("inner join gerencia_regional on localidade.greg_id = gerencia_regional.greg_id ");
			consulta.append("inner join setor_comercial on imovel.stcm_id = setor_comercial.stcm_id ");

			// LOGRADOURO BAIRRO
			consulta.append("left join logradouro_bairro on imovel.lgbr_id = logradouro_bairro.lgbr_id ");
			consulta.append("left join bairro on logradouro_bairro.bair_id = bairro.bair_id ");
			consulta.append("left join municipio on bairro.muni_id = municipio.muni_id ");
			consulta.append("inner join quadra on imovel.qdra_id = quadra.qdra_id ");
			consulta.append("inner join rota rotaImovel on imovel.rota_id = rotaImovel.rota_id ");

			// LOGRADOURO CEP
			consulta.append("left join logradouro_cep on imovel.lgcp_id = logradouro_cep.lgcp_id ");
			consulta.append("left join cep on logradouro_cep.cep_id = cep.cep_id ");
			consulta.append("left join logradouro on logradouro_cep.logr_id = logradouro.logr_id ");

			// ÁGUA
			consulta.append("left join ligacao_agua_situacao on imovel.last_id = ligacao_agua_situacao.last_id ");
			consulta.append("left join ligacao_agua on imovel.imov_id = ligacao_agua.lagu_id ");

			// ESGOTO
			consulta.append("left join ligacao_esgoto_situacao on imovel.lest_id = ligacao_esgoto_situacao.lest_id ");
			consulta.append("left join ligacao_esgoto on imovel.imov_id = ligacao_esgoto.lesg_id ");
			consulta.append("left join imovel_perfil on imovel.iper_id = imovel_perfil.iper_id ");
			consulta.append("left join poco_tipo on imovel.poco_id = poco_tipo.poco_id ");
			consulta.append("left join area_construida_faixa on imovel.acon_id = area_construida_faixa.acon_id ");

			// CLIENTE USUÁRIO
			consulta.append("left outer join cliente_imovel cliente_imovel_usuario on imovel.imov_id = cliente_imovel_usuario.imov_id  ");
			consulta.append(" and (cliente_imovel_usuario.crtp_id=2 and cliente_imovel_usuario.clim_dtrelacaofim is null) ");
			consulta.append("left outer join cliente cliente_usuario on cliente_imovel_usuario.clie_id = cliente_usuario.clie_id ");

			// CLIENTE RESPOSANVEL
			consulta.append("left outer join cliente_imovel cliente_imovel_responsavel ");
			consulta.append("on imovel.imov_id = cliente_imovel_responsavel.imov_id ");
			consulta.append(" and (cliente_imovel_responsavel.crtp_id=3 and cliente_imovel_responsavel.clim_dtrelacaofim is null) ");
			consulta.append("left outer join cliente cliente_responsavel ");
			consulta.append("on cliente_imovel_responsavel.clie_id = cliente_responsavel.clie_id ");

			// ÁGUA
			consulta.append("left join hidrometro_instalacao_hist on ligacao_agua.hidi_id =  hidrometro_instalacao_hist.hidi_id ");

			// ESGOTO
			consulta.append("left join hidrometro_instalacao_hist hidrom_inst_hist_esgoto ");
			consulta.append("on imovel.hidi_id =  hidrom_inst_hist_esgoto.hidi_id ");

			// RELACIONAMENTO PARA O RELATORIO DE IMÓVEL
			consulta.append("left join logradouro_titulo on logradouro.lgtt_id = logradouro_titulo.lgtt_id ");
			consulta.append("left join logradouro_tipo on logradouro.lgtp_id = logradouro_tipo.lgtp_id ");
			consulta.append("left join endereco_referencia on imovel.edrf_id = endereco_referencia.edrf_id ");
			consulta.append("left join unidade_federacao on municipio.unfe_id = unidade_federacao.unfe_id ");
			consulta.append("left join reservatorio_volume_faixa ");
			consulta.append("on imovel.resv_idreservatoriosuperior = reservatorio_volume_faixa.resv_id ");
			consulta.append("left join reservatorio_volume_faixa reservatorio_volume_faixa_inf ");
			consulta.append("on imovel.resv_idreservatorioinferior = reservatorio_volume_faixa_inf.resv_id ");

			consulta.append("left join piscina_volume_faixa on imovel.pisc_id = piscina_volume_faixa.pisc_id ");
			consulta.append("left join pavimento_calcada on imovel.pcal_id = pavimento_calcada.pcal_id ");
			consulta.append("left join pavimento_rua on imovel.prua_id = pavimento_rua.prua_id ");
			consulta.append("left join despejo on imovel.depj_id = despejo.depj_id ");

			// ÁGUA
			consulta.append("left join ligacao_agua_diametro on ligacao_agua.lagd_id = ligacao_agua_diametro.lagd_id ");
			consulta.append("left join ligacao_agua_material on ligacao_agua.lagm_id = ligacao_agua_material.lagm_id ");

			// ESGOTO
			consulta.append("left join ligacao_esgoto_diametro on ligacao_esgoto.legd_id = ligacao_esgoto_diametro.legd_id ");
			consulta.append("left join ligacao_esgoto_material on ligacao_esgoto.legm_id = ligacao_esgoto_material.legm_id ");

			// ÁGUA
			consulta.append("left join hidrometro on hidrometro_instalacao_hist.hidr_id = hidrometro.hidr_id ");
			consulta.append("left join hidrometro_capacidade on hidrometro.hicp_id = hidrometro_capacidade.hicp_id ");
			consulta.append("left join hidrometro_marca on hidrometro.himc_id = hidrometro_marca.himc_id ");
			consulta.append("left join hidrometro_diametro on hidrometro.hidm_id = hidrometro_diametro.hidm_id ");
			consulta.append("left join hidrometro_tipo on hidrometro.hitp_id = hidrometro_tipo.hitp_id ");
			consulta.append("left join hidrometro_local_instalacao ");
			consulta.append("on hidrometro_instalacao_hist.hili_id = hidrometro_local_instalacao.hili_id ");
			consulta.append("left join hidrometro_protecao ");
			consulta.append("on hidrometro_instalacao_hist.hipr_id = hidrometro_protecao.hipr_id ");

			// ESGOTO
			consulta.append("left join hidrometro hidrometro_esgoto on hidrom_inst_hist_esgoto.hidr_id = hidrometro_esgoto.hidr_id ");
			consulta.append("left join hidrometro_capacidade hidrometro_capacidade_esgoto ");
			consulta.append("on hidrometro_esgoto.hicp_id = hidrometro_capacidade_esgoto.hicp_id ");
			consulta.append("left join hidrometro_marca hidrometro_marca_esgoto ");
			consulta.append("on hidrometro_esgoto.himc_id = hidrometro_marca_esgoto.himc_id ");
			consulta.append("left join hidrometro_diametro hidrometro_diametro_esgoto ");
			consulta.append("on hidrometro_esgoto.hidm_id = hidrometro_diametro_esgoto.hidm_id ");
			consulta.append("left join hidrometro_tipo hidrometro_tipo_esgoto ");
			consulta.append("on hidrometro_esgoto.hitp_id = hidrometro_tipo_esgoto.hitp_id ");
			consulta.append("left join hidrometro_local_instalacao hidrometro_local_instalac_esg ");
			consulta.append("on hidrom_inst_hist_esgoto.hili_id = hidrometro_local_instalacao.hili_id ");

			consulta.append("left join hidrometro_protecao hidrometro_protecao_esgoto ");
			consulta.append("on hidrom_inst_hist_esgoto.hipr_id = hidrometro_protecao_esgoto.hipr_id ");

			consulta.append(montarInnerJoinFiltrarImoveisOutrosCriterios(intervaloMediaMinimaImovelInicial,
							intervaloMediaMinimaImovelFinal, intervaloMediaMinimaHidrometroInicial, intervaloMediaMinimaHidrometroFinal,
							idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo, idSituacaoEspecialCobranca, idEloAnormalidade,
							idCadastroOcorrencia, idConsumoTarifa, idTipoMedicao, indicadorMedicao, idSubCategoria, idCategoria, idCliente,
							idClienteTipo, idClienteRelacaoTipo, ConstantesSistema.GERAR_RELATORIO_IMOVEL));

			consulta.append(montarCondicaoSQLWhereFiltrarImovelOutrosCriterio(idImovelCondominio, idImovelPrincipal, idSituacaoLigacaoAgua,
							consumoMinimoInicialAgua, consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto, consumoMinimoInicialEsgoto,
							consumoMinimoFinalEsgoto, intervaloValorPercentualEsgotoInicial, intervaloValorPercentualEsgotoFinal,
							intervaloMediaMinimaImovelInicial, intervaloMediaMinimaImovelFinal, intervaloMediaMinimaHidrometroInicial,
							intervaloMediaMinimaHidrometroFinal, idImovelPerfil, idPocoTipo, idFaturamentoSituacaoTipo,
							idCobrancaSituacaoTipo, idSituacaoEspecialCobranca, idEloAnormalidade, areaConstruidaInicial,
							areaConstruidaFinal, idCadastroOcorrencia, idConsumoTarifa, idGerenciaRegional, idLocalidadeInicial,
							idLocalidadeFinal, setorComercialInicial, setorComercialFinal, quadraInicial, quadraFinal, loteOrigem,
							loteDestno, cep, logradouro, bairro, municipio, idTipoMedicao, indicadorMedicao, idSubCategoria, idCategoria,
							quantidadeEconomiasInicial, quantidadeEconomiasFinal, diaVencimento, idCliente, idClienteTipo,
							idClienteRelacaoTipo, numeroPontosInicial, numeroPontosFinal, numeroMoradoresInicial, numeroMoradoresFinal,
							idAreaConstruidaFaixa, idUnidadeNegocio, ConstantesSistema.GERAR_RELATORIO_IMOVEL, rotaInicial, rotaFinal,
							sequencialRotaInicial, sequencialRotaFinal, segmentoInicial, segmentoFinal, subloteInicial, subloteFinal));

			/*
			 * # COLOCANDO O VALOR NAS CONDIÇÕES#
			 */

			SQLQuery query = session.createSQLQuery(consulta.toString().substring(0, (consulta.toString().length() - 5)));
			informarDadosQueryFiltrarImovelOutrosCriterio(query, idImovelCondominio, idImovelPrincipal, idSituacaoLigacaoAgua,
							consumoMinimoInicialAgua, consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto, consumoMinimoInicialEsgoto,
							consumoMinimoFinalEsgoto, intervaloValorPercentualEsgotoInicial, intervaloValorPercentualEsgotoFinal,
							intervaloMediaMinimaImovelInicial, intervaloMediaMinimaImovelFinal, intervaloMediaMinimaHidrometroInicial,
							intervaloMediaMinimaHidrometroFinal, idImovelPerfil, idPocoTipo, idFaturamentoSituacaoTipo,
							idCobrancaSituacaoTipo, idSituacaoEspecialCobranca, idEloAnormalidade, areaConstruidaInicial,
							areaConstruidaFinal, idCadastroOcorrencia, idConsumoTarifa, idGerenciaRegional, idLocalidadeInicial,
							idLocalidadeFinal, setorComercialInicial, setorComercialFinal, quadraInicial, quadraFinal, loteOrigem,
							loteDestno, cep, logradouro, bairro, municipio, idTipoMedicao, indicadorMedicao, idSubCategoria, idCategoria,
							quantidadeEconomiasInicial, quantidadeEconomiasFinal, diaVencimento, idCliente, idClienteTipo,
							idClienteRelacaoTipo, numeroPontosInicial, numeroPontosFinal, numeroMoradoresInicial, numeroMoradoresFinal,
							idAreaConstruidaFaixa, idUnidadeNegocio, rotaInicial, rotaFinal, sequencialRotaInicial, sequencialRotaFinal,
							segmentoInicial, segmentoFinal, subloteInicial, subloteFinal);

			retornoConsulta = query.addScalar("quantidade", Hibernate.INTEGER).uniqueResult();

			if(retornoConsulta != null){

				retorno = (Integer) retornoConsulta;
			}

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
	 * [UC0591] - Gerar Relatório de Clientes Especiais
	 * Pesquisas Total de Registros de acordo com os parâmetros da pesquisa
	 * 
	 * @author Anderson Italo
	 * @date 11/07/2011
	 */
	public Integer pesquisarTotalRegistrosRelatorioClientesEspeciais(FiltrarRelatorioClientesEspeciaisHelper filtro)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno = 0;
		Object retornoConsulta = null;
		StringBuffer consultaBuffer = new StringBuffer();

		try{

			consultaBuffer.append("SELECT count(distinct(imov.imov_id)) as totalRegistros ");
			consultaBuffer.append(" FROM imovel imov ");
			consultaBuffer.append(" INNER JOIN consumo_tarifa consTar ");
			consultaBuffer.append(" on consTar.cstf_id = imov.cstf_id ");
			consultaBuffer.append(" INNER JOIN ligacao_agua_situacao ligAguaSit ");
			consultaBuffer.append(" on ligAguaSit.last_id = imov.last_id ");
			consultaBuffer.append(" INNER JOIN ligacao_esgoto_situacao ligEsgSit ");
			consultaBuffer.append(" on ligEsgSit.lest_id = imov.lest_id ");
			consultaBuffer.append(" INNER JOIN setor_comercial setor ");
			consultaBuffer.append(" on setor.stcm_id = imov.stcm_id ");
			consultaBuffer.append(" INNER JOIN quadra quadra ");
			consultaBuffer.append(" on quadra.qdra_id = imov.qdra_id ");
			consultaBuffer.append(" INNER JOIN localidade loc ");
			consultaBuffer.append(" on loc.loca_id = imov.loca_id ");
			consultaBuffer.append(" INNER JOIN gerencia_regional greg ");
			consultaBuffer.append(" on greg.greg_id = loc.greg_id ");
			consultaBuffer.append(" INNER JOIN cliente_imovel clieImovUsuario ");
			consultaBuffer.append(" on clieImovUsuario.imov_id = imov.imov_id ");
			consultaBuffer.append(" and clieImovUsuario.crtp_id = ");
			consultaBuffer.append(ClienteRelacaoTipo.USUARIO.toString());
			consultaBuffer.append(" and clieImovUsuario.clim_dtrelacaofim is null ");
			consultaBuffer.append(" INNER JOIN cliente clieUsuario ");
			consultaBuffer.append(" on clieUsuario.clie_id = clieImovUsuario.clie_id ");

			if(filtro.getIdsCategoria() != null || filtro.getIdsSubcategoria() != null){

				consultaBuffer.append(" INNER JOIN imovel_subcategoria imovSub ");
				consultaBuffer.append(" on imovSub.imov_id = imov.imov_id ");
				consultaBuffer.append(" INNER JOIN subcategoria subcategoria ");
				consultaBuffer.append(" on subcategoria.scat_id = imovSub.scat_id ");
			}

			if((filtro.getIdClienteResponsavel() != null && !filtro.getIdClienteResponsavel().trim().equals(""))
							|| (filtro.getIntervaloConsumoResponsavelInicial() != null && !filtro.getIntervaloConsumoResponsavelInicial()
											.trim().equals(""))){

				consultaBuffer.append(" INNER JOIN cliente_imovel clieImovResponsavel ");
				consultaBuffer.append(" on clieImovResponsavel.imov_id = imov.imov_id ");
				consultaBuffer.append(" and clieImovResponsavel.crtp_id = ");
				consultaBuffer.append(ClienteRelacaoTipo.RESPONSAVEL.toString());
				consultaBuffer.append(" and clieImovResponsavel.clim_dtrelacaofim is null ");
				consultaBuffer.append(" INNER JOIN cliente clieResponsavel ");
				consultaBuffer.append(" on clieResponsavel.clie_id = clieImovResponsavel.clie_id ");
			}else{

				consultaBuffer.append(" LEFT OUTER JOIN cliente_imovel clieImovResponsavel ");
				consultaBuffer.append(" on clieImovResponsavel.imov_id = imov.imov_id ");
				consultaBuffer.append(" and clieImovResponsavel.crtp_id = ");
				consultaBuffer.append(ClienteRelacaoTipo.RESPONSAVEL.toString());
				consultaBuffer.append(" and clieImovResponsavel.clim_dtrelacaofim is null ");
				consultaBuffer.append(" LEFT OUTER JOIN cliente clieResponsavel ");
				consultaBuffer.append(" on clieResponsavel.clie_id = clieImovResponsavel.clie_id ");
			}

			if(filtro.getIdsClienteTipoEspecial() != null){

				consultaBuffer.append(" LEFT OUTER JOIN cliente_tipo_especial cles ");
				consultaBuffer.append(" on clieResponsavel.cles_id = cles.cles_id ");
			}

			if(filtro.getIdsCapacidadesHidrometro() != null){

				consultaBuffer.append(" INNER JOIN ligacao_agua ligAgua ");
				consultaBuffer.append(" on ligAgua.lagu_id = imov.imov_id ");
				consultaBuffer.append(" INNER JOIN hidrometro_instalacao_hist hidrInstHist ");
				consultaBuffer.append(" on hidrInstHist.hidi_id = ligAgua.hidi_id ");
				consultaBuffer.append(" INNER JOIN hidrometro hidr ");
				consultaBuffer.append(" on hidr.hidr_id = hidrInstHist.hidr_id ");
				consultaBuffer.append(" INNER JOIN hidrometro_capacidade hidrCap ");
				consultaBuffer.append(" on hidrCap.hicp_id =  hidr.hicp_id ");
			}else{

				consultaBuffer.append(" LEFT OUTER JOIN ligacao_agua ligAgua ");
				consultaBuffer.append(" on ligAgua.lagu_id = imov.imov_id ");
				consultaBuffer.append(" LEFT OUTER JOIN hidrometro_instalacao_hist hidrInstHist ");
				consultaBuffer.append(" on hidrInstHist.hidi_id = ligAgua.hidi_id ");
				consultaBuffer.append(" LEFT OUTER JOIN hidrometro hidr ");
				consultaBuffer.append(" on hidr.hidr_id = hidrInstHist.hidr_id ");
				consultaBuffer.append(" LEFT OUTER JOIN hidrometro_capacidade hidrCap ");
				consultaBuffer.append(" on hidrCap.hicp_id =  hidr.hicp_id ");
			}

			if(filtro.getIntervaloConsumoAguaInicial() != null && !filtro.getIntervaloConsumoAguaInicial().trim().equals("")){

				consultaBuffer.append(" INNER JOIN consumo_historico consHistAgua ");
				consultaBuffer.append(" on consHistAgua.imov_id = imov.imov_id ");
				consultaBuffer.append(" and consHistAgua.cshi_amfaturamento = :anoMesFaturamento ");
				consultaBuffer.append(" and consHistAgua.lgti_id = ");
				consultaBuffer.append(LigacaoTipo.LIGACAO_AGUA.toString());
			}else{

				consultaBuffer.append(" LEFT OUTER JOIN consumo_historico consHistAgua ");
				consultaBuffer.append(" on consHistAgua.imov_id = imov.imov_id ");
				consultaBuffer.append(" and consHistAgua.cshi_amfaturamento = :anoMesFaturamento ");
				consultaBuffer.append(" and consHistAgua.lgti_id = ");
				consultaBuffer.append(LigacaoTipo.LIGACAO_AGUA.toString());
			}

			if(filtro.getIntervaloConsumoEsgotoInicial() != null && !filtro.getIntervaloConsumoEsgotoInicial().trim().equals("")){

				consultaBuffer.append(" INNER JOIN consumo_historico consHistEsgoto ");
				consultaBuffer.append(" on consHistEsgoto.imov_id =  imov.imov_id ");
				consultaBuffer.append(" and consHistEsgoto.cshi_amfaturamento = :anoMesFaturamento ");
				consultaBuffer.append(" and consHistEsgoto.lgti_id = ");
				consultaBuffer.append(LigacaoTipo.LIGACAO_ESGOTO.toString());

			}else{

				consultaBuffer.append(" LEFT OUTER JOIN consumo_historico consHistEsgoto ");
				consultaBuffer.append(" on consHistEsgoto.imov_id =  imov.imov_id ");
				consultaBuffer.append(" and consHistEsgoto.cshi_amfaturamento = :anoMesFaturamento ");
				consultaBuffer.append(" and consHistEsgoto.lgti_id = ");
				consultaBuffer.append(LigacaoTipo.LIGACAO_ESGOTO.toString());
			}

			if((filtro.getLeituraAnormalidade() != null && !filtro.getLeituraAnormalidade().trim().equals(""))
							|| (filtro.getIdLeituraAnormalidade() != null && !filtro.getIdLeituraAnormalidade().trim().equals(""))){

				consultaBuffer.append(" INNER JOIN medicao_historico medHist ");
				consultaBuffer.append(" on medHist.lagu_id = imov.imov_id ");
				consultaBuffer.append(" and medHist.mdhi_amleitura = :anoMesFaturamento ");
			}

			consultaBuffer.append(" LEFT OUTER JOIN conta conta ");
			consultaBuffer.append(" on conta.imov_id = imov.imov_id ");
			consultaBuffer.append(" and cnta_amreferenciaconta = :anoMesFaturamento ");
			consultaBuffer.append(" LEFT OUTER JOIN ligacao_esgoto ligEsgoto ");
			consultaBuffer.append(" on ligEsgoto.lesg_id = imov.imov_id ");

			String condicionais = this.criarCondicionaisImovelClientesEspeciais(filtro.getIdUnidadeNegocio(), filtro
							.getIdGerenciaRegional(), filtro.getIdLocalidadeInicial(), filtro.getIdLocalidadeFinal(), filtro
							.getIdsPerfilImovel(), filtro.getIdsCategoria(), filtro.getIdsSubcategoria(), filtro.getIdSituacaoAgua(),
							filtro.getIdSituacaoEsgoto(), filtro.getQtdeEconomiasInicial(), filtro.getQtdeEconomiasFinal(), filtro
											.getIntervaloConsumoAguaInicial(), filtro.getIntervaloConsumoAguaFinal(), filtro
											.getIntervaloConsumoEsgotoInicial(), filtro.getIntervaloConsumoEsgotoFinal(), filtro
											.getIdClienteResponsavel(), filtro.getIntervaloConsumoResponsavelInicial(), filtro
											.getIntervaloConsumoResponsavelFinal(), filtro.getDataInstalacaoHidrometroInicial(), filtro
											.getDataInstalacaoHidrometroFinal(), filtro.getIdsCapacidadesHidrometro(), filtro
											.getIdsTarifasConsumo(), filtro.getAnoMesFaturamento(), filtro.getIdLeituraAnormalidade(),
											filtro.getLeituraAnormalidade(), filtro.getIdConsumoAnormalidade(), filtro.getConsumoAnormalidade(), filtro.getIdsClienteTipoEspecial());

			if(!Util.isVazioOuBranco(condicionais)){

				consultaBuffer.append(condicionais);
			}

			retornoConsulta = session.createSQLQuery(consultaBuffer.toString()).addScalar("totalRegistros", Hibernate.INTEGER).setInteger(
							"anoMesFaturamento", filtro.getAnoMesFaturamento()).setMaxResults(1).uniqueResult();

			if(retornoConsulta != null){

				retorno = ((Number) retornoConsulta).intValue();
			}

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	/**
	 * [UC0275] Gerar Resumo das Ligações/Economias
	 * - Verifica se o Imovel é do tipo condomínio
	 * 
	 * @author Ailton Sousa
	 * @date 12/08/2011
	 */
	public boolean isImovelCondominio(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Short indicadorCondominio = null;
		boolean retorno = false;

		try{
			StringBuilder hql = new StringBuilder();
			hql.append("select imv.indicadorImovelCondominio ");
			hql.append(" from Imovel imv ");
			hql.append(" where imv.id = :idImovel ");

			Query query = session.createQuery(hql.toString());

			indicadorCondominio = (Short) query.setInteger("idImovel", idImovel).uniqueResult();

			if(indicadorCondominio != null && indicadorCondominio.equals(ConstantesSistema.SIM)){
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
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0003] – Obter Grupo do Imóvel
	 * Obtem o ImovelConsumoFaixaAreaCategoria da categoria pelo imovel e categoria.
	 * 
	 * @author Ailton Sousa
	 * @date 22/08/2011
	 * @param idCategoria
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ImovelConsumoFaixaAreaCategoria pesquisarImovelConsumoFaixaAreaCategoriaPorCategoriaImovel(Integer idCategoria, Integer idImovel)
					throws ErroRepositorioException{

		ImovelConsumoFaixaAreaCategoria retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer hql = new StringBuffer();
		Query query = null;

		try{

			hql.append("select icfac ");
			hql.append("from ImovelConsumoFaixaAreaCategoria icfac ");
			hql.append("inner join icfac.comp_id.consumoFaixaAreaCategoria cfac  ");
			// hql.append("inner join icfac.imovel ");
			hql.append("where ");
			hql.append("cfac.categoria.id = :idCategoria ");
			hql.append("and icfac.comp_id.imovel.id = :idImovel ");
			hql.append("and icfac.indicadorUso = 1 ");

			query = session.createQuery(hql.toString()).setInteger("idCategoria", idCategoria).setInteger("idImovel", idImovel);

			retorno = (ImovelConsumoFaixaAreaCategoria) query.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * Verifica se o indicador de debito automático do imovel está ativo.
	 * 
	 * @author Ailton Sousa
	 * @date 22/08/2011
	 */
	public boolean isIndicadorDebitoAutomaticoImovel(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Short indicadorDebitoAutomatico = null;
		boolean retorno = false;

		try{
			StringBuilder hql = new StringBuilder();
			hql.append("select imv.indicadorDebitoConta ");
			hql.append(" from Imovel imv ");
			hql.append(" where imv.id = :idImovel ");

			Query query = session.createQuery(hql.toString());

			indicadorDebitoAutomatico = (Short) query.setInteger("idImovel", idImovel).uniqueResult();

			if(indicadorDebitoAutomatico != null && indicadorDebitoAutomatico.equals(ConstantesSistema.SIM)){
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
	 * Retorna true caso exista imoveis para a coleçao de rotas recebidas.
	 * 
	 * @author Ailton Sousa
	 * @date 15/09/2011
	 * @param colecaoRotas
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean isImovelPorColecaoRotas(Collection<Rota> colecaoRotas) throws ErroRepositorioException{

		Collection resultado = null;
		boolean retorno = false;

		String consulta;

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{

			consulta = "select im.id " + "from Imovel im " + "inner join im.rota rt " + "where rt in (:listRotas) ";

			// resultado = (Collection) session.createQuery(consulta).setParameterList("listRotas",
			// colecaoRotas).list();

			if(colecaoRotas != null && colecaoRotas.size() > 1000){
				Collection colecaoAuxiliar = new ArrayList<Object>();
				resultado = new ArrayList<Object>();
				for(Object rota : colecaoRotas){
					colecaoAuxiliar.add(rota);
					if(colecaoAuxiliar.size() == 1000){
						resultado.addAll(session.createQuery(consulta).setParameterList("listRotas", colecaoAuxiliar).list());
						colecaoAuxiliar.clear();
					}
				}
				if(!colecaoAuxiliar.isEmpty()){
					resultado.addAll(session.createQuery(consulta).setParameterList("listRotas", colecaoAuxiliar).list());
				}
			}else{
				resultado = session.createQuery(consulta).setParameterList("listRotas", colecaoRotas).list();
			}

			if(resultado != null && !resultado.isEmpty()){
				retorno = true;
			}

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
	 * @author Ailton Sousa
	 * @date 12/10/2011
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public String pesquisarNomeImovel(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String retorno = "";

		try{
			StringBuilder hql = new StringBuilder();
			hql.append("select imv.nomeImovel ");
			hql.append(" from Imovel imv ");
			hql.append(" where imv.id = :idImovel ");

			Query query = session.createQuery(hql.toString());

			retorno = (String) query.setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Retorna coleção de imoveis para os ids passados.
	 * 
	 * @date 13/10/2011
	 * @author Péricles Tavares
	 * @param idImoveis
	 * @param idSetorComercial
	 * @param idAcaoCobranca
	 * @param idsTipoDocumentoAIgnorar
	 * @return imoveis
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImoveis(Collection<Integer> idImoveis, Integer idSetorComercial, Integer idAcaoCobranca,
					Integer[] idsTipoDocumentoAIgnorar, CobrancaCriterio criterioCobranca, ClienteRelacaoTipo clienteRelacaoTipo)
					throws ErroRepositorioException{

		Collection imoveis = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();
		try{
			consulta.append("select ");
			consulta.append(" i.imov_id,"); // 0
			consulta.append(" i.last_id,"); // 1
			consulta.append(" i.lest_id,"); // 2
			consulta.append(" i.iper_id,"); // 3
			consulta.append(" r.empr_id,"); // 4
			consulta.append(" i.loca_id,"); // 5
			consulta.append(" sc.stcm_cdsetorcomercial,"); // 6
			consulta.append(" q.qdra_nnquadra,"); // 7
			consulta.append(" i.imov_nnlote,"); // 8
			consulta.append(" i.imov_nnsublote,"); // 9
			consulta.append(" i.qdra_id,"); // 10
			consulta.append(" i.CBSP_ID,"); // 11
			consulta.append(" i.imov_icdebitoconta,"); // 12
			consulta.append(" r.empr_idcobranca, "); // 13
			consulta.append(" i.lgcp_id ,"); // 14
			consulta.append(" r.rota_id, "); // 15
			consulta.append(" i.imov_icexclusao "); // 16
			consulta.append(" from imovel i,");
			consulta.append("   quadra q,");
			consulta.append("   rota r,");
			consulta.append("   setor_comercial sc,");

			consulta.append("   cobranca_criterio cc");
			consulta.append(" where i.imov_id in (:imoveisId)");
			consulta.append("   and i.qdra_id = q.qdra_id");
			consulta.append("   and i.rota_id = r.rota_id");
			consulta.append("   and q.stcm_id = sc.stcm_id");

			consulta.append("   and cc.cbct_id = :idCriterioCobranca");

			consulta
.append("   and ((cbct_ictelefone <> 1) or (cbct_ictelefone = 1 and exists (select ci.clie_id from cliente_imovel ci, cliente_fone cf where ci.imov_id = i.imov_id and cf.clie_id = ci.clie_id and clim_dtrelacaofim is null and crtp_id = :idClienteRelacaoTipo)))");
			// consulta.append("   and ((cbct_iccpf <> 1) or (cbct_iccpf = 1 and exists (select ci.clie_id from cliente_imovel ci, cliente cl where ci.imov_id = i.imov_id and cl.clie_id = ci.clie_id and clim_dtrelacaofim is null and crtp_id = :idClienteRelacaoTipo and not (clie_nncpf is null and clie_nncnpj is null))))");
			consulta.append("   and exists");
			consulta.append(" (select sub.catg_id");
			consulta.append("  from imovel_subcategoria isub, subcategoria sub, cobranca_criterio_linha ccl");
			consulta.append("  where isub.imov_id = i.imov_id");
			consulta.append("     and isub.scat_id = sub.scat_id");
			consulta.append("     and ccl.catg_id  = sub.catg_id");
			consulta.append("     and ccl.cbct_id = cc.cbct_id)");
			consulta
							.append(" and i.iper_id in (select nvl(ccl.iper_id,i.iper_id) from cobranca_criterio_linha ccl where ccl.cbct_id = cc.cbct_id )");
			consulta
							.append(" and i.last_id in (select nvl(csla.last_id,i.last_id) from criterio_situacao_ligacao_agua csla where csla.cbct_id = cc.cbct_id )");
			consulta
							.append(" and i.lest_id in (select nvl(csle.lest_id,i.lest_id) from criterio_situacao_ligacao_esgo csle where csle.cbct_id = cc.cbct_id )");

			consulta.append(" and i.imov_icexclusao <> 1");
			consulta.append(" and sc.stcm_id = :idSetorComercial ");

			SQLQuery query = session.createSQLQuery(consulta.toString()).addScalar("imov_id", Hibernate.INTEGER)
							.addScalar("last_id", Hibernate.INTEGER).addScalar("lest_id", Hibernate.INTEGER)
							.addScalar("iper_id", Hibernate.INTEGER).addScalar("empr_id", Hibernate.INTEGER)
							.addScalar("loca_id", Hibernate.INTEGER).addScalar("stcm_cdsetorcomercial", Hibernate.INTEGER)
							.addScalar("qdra_nnquadra", Hibernate.INTEGER).addScalar("imov_nnlote", Hibernate.SHORT)
							.addScalar("imov_nnsublote", Hibernate.SHORT).addScalar("qdra_id", Hibernate.INTEGER)
							.addScalar("CBSP_ID", Hibernate.INTEGER).addScalar("imov_icdebitoconta", Hibernate.SHORT)
							.addScalar("empr_idcobranca", Hibernate.INTEGER).addScalar("lgcp_id", Hibernate.INTEGER)
							.addScalar("rota_id", Hibernate.INTEGER).addScalar("imov_icexclusao", Hibernate.SHORT);

			// Tratamento para quebrar a coleção e executar de 1000 em 1000, pois o oracle
			// limita a cláusula IN a receber uma coleção com, no máximo, 1000.
			if(idImoveis.size() <= 1000){

				query.setParameterList("imoveisId", idImoveis).setInteger("idSetorComercial", idSetorComercial)
								.setInteger("idCriterioCobranca", criterioCobranca.getId())
								.setInteger("idClienteRelacaoTipo", clienteRelacaoTipo.getId());

				imoveis = query.list();

			}else{

				Collection<Integer> idImoveisAux = new ArrayList<Integer>();
				Iterator itImoveis = idImoveis.iterator();
				imoveis = new ArrayList();

				while(itImoveis.hasNext()){
					idImoveisAux.add((Integer) itImoveis.next());

					if(idImoveisAux.size() == 1000){

						query.setParameterList("imoveisId", idImoveisAux).setInteger("idSetorComercial", idSetorComercial).setInteger(
"idCriterioCobranca", criterioCobranca.getId())
										.setInteger("idClienteRelacaoTipo", clienteRelacaoTipo.getId());
						imoveis.addAll(query.list());
						idImoveisAux.clear();
					}
				}

				// Ao sair do while, checa se tem imoveis que ainda nao foram processados.
				if(!Util.isVazioOrNulo(idImoveisAux) && idImoveisAux.size() < 1000){

					query.setParameterList("imoveisId", idImoveisAux).setInteger("idSetorComercial", idSetorComercial).setInteger(
"idCriterioCobranca", criterioCobranca.getId())
									.setInteger("idClienteRelacaoTipo", clienteRelacaoTipo.getId());
					imoveis.addAll(query.list());
					idImoveisAux.clear();
				}
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return imoveis;

	}

	/**
	 * Retorna o ImovelSubcategoria com a maior quantidade de economia.
	 * 
	 * @author Ailton Sousa
	 * @date 28/12/2011
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ImovelSubcategoria pesquisarImovelSubcategoriaComMaiorQuantidadeEconomia(Integer idImovel) throws ErroRepositorioException{

		ImovelSubcategoria retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			String consulta = "select imovSub "

			+ "from ImovelSubcategoria imovSub "

			+ "left join fetch imovSub.comp_id.imovel imovel "

			+ "left join fetch imovSub.comp_id.subcategoria subcategoria "

			+ "where imovel.id = :idImovel "

			+ "order by imovSub.quantidadeEconomias desc";

			retorno = (ImovelSubcategoria) session.createQuery(consulta).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	/**
	 * [UC0025] Manter Quadra
	 * [SB0003] - Verificar Mudança de Rota da Quadra
	 * Método que obtém o total de imóveis com rota igual à rota anterior da quadra.
	 * 
	 * @author Anderson Italo
	 * @date 23/01/2012
	 * @throws ErroRepositorioException
	 */
	public Integer obterTotalImoveisRotaIgualAnteriorQuadra(Integer idQuadra, Integer idRota) throws ErroRepositorioException{

		Integer retorno = null;
		Object retornoConsulta = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append("SELECT count(imov.id) ");
			consulta.append("FROM Imovel imov ");
			consulta.append("WHERE imov.quadra.id = :idQuadra ");
			consulta.append("AND imov.rota.id = :idRota");

			retornoConsulta = (Object) session.createQuery(consulta.toString()).setInteger("idQuadra", idQuadra).setInteger("idRota",
							idRota).setMaxResults(1).uniqueResult();

			if(retornoConsulta == null){

				retorno = 0;
			}else{

				retorno = ((Number) retornoConsulta).intValue();
			}

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(Exception e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0025] Manter Quadra
	 * [SB0005] - Verificar Mudança de Distrito Operacional da Quadra
	 * Método que obtém o total de imóveis com distrito operacional igual ao distrito operacional
	 * anterior da quadra.
	 * 
	 * @author Luciano Galvão
	 * @date 31/05/2012
	 * @throws ErroRepositorioException
	 */
	public Integer obterTotalImoveisDistritoOperacionalIgualAnteriorQuadra(Integer idQuadra, Integer idDistritoOperacional)
					throws ErroRepositorioException{

		Integer retorno = null;
		Object retornoConsulta = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append("SELECT count(imov.id) ");
			consulta.append("FROM Imovel imov ");
			consulta.append("WHERE imov.quadra.id = :idQuadra ");
			consulta.append("AND imov.distritoOperacional.id = :idDistritoOperacional");

			retornoConsulta = (Object) session.createQuery(consulta.toString()).setInteger("idQuadra", idQuadra).setInteger(
							"idDistritoOperacional", idDistritoOperacional).setMaxResults(1).uniqueResult();

			if(retornoConsulta == null){

				retorno = 0;
			}else{

				retorno = ((Number) retornoConsulta).intValue();
			}

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(Exception e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0025] Manter Quadra
	 * [SB0003] - Verificar Mudança de Rota da Quadra
	 * Método que obtém o total de imóveis com rota diferente à rota anterior da quadra.
	 * 
	 * @author Anderson Italo
	 * @date 23/01/2012
	 * @throws ErroRepositorioException
	 */
	public Integer obterTotalImoveisRotaDiferenteAnteriorQuadra(Integer idQuadra, Integer idRota) throws ErroRepositorioException{

		Integer retorno = null;
		Object retornoConsulta = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append("SELECT count(imov.id) ");
			consulta.append("FROM Imovel imov ");
			consulta.append("WHERE imov.quadra.id = :idQuadra ");
			consulta.append("AND imov.rota.id <> :idRota");

			retornoConsulta = (Object) session.createQuery(consulta.toString()).setInteger("idQuadra", idQuadra).setInteger("idRota",
							idRota).setMaxResults(1).uniqueResult();

			if(retornoConsulta == null){

				retorno = 0;
			}else{

				retorno = ((Number) retornoConsulta).intValue();
			}

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(Exception e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0025] Manter Quadra
	 * [SB0005] - Verificar Mudança de Distrito Operacional da Quadra
	 * Método que obtém o total de imóveis com distrito operacional diferente do distrito
	 * operacional anterior da quadra.
	 * 
	 * @author Luciano Galvão
	 * @date 31/05/2012
	 * @throws ErroRepositorioException
	 */
	public Integer obterTotalImoveisDistritoOperacionalDiferenteAnteriorQuadra(Integer idQuadra, Integer idDistritoOperacional)
					throws ErroRepositorioException{

		Integer retorno = null;
		Object retornoConsulta = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append("SELECT count(imov.id) ");
			consulta.append("FROM Imovel imov ");
			consulta.append("WHERE imov.quadra.id = :idQuadra ");
			consulta.append("AND imov.distritoOperacional.id <> :idDistritoOperacional");

			retornoConsulta = (Object) session.createQuery(consulta.toString()).setInteger("idQuadra", idQuadra).setInteger(
							"idDistritoOperacional", idDistritoOperacional).setMaxResults(1).uniqueResult();

			if(retornoConsulta == null){

				retorno = 0;
			}else{

				retorno = ((Number) retornoConsulta).intValue();
			}

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(Exception e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0025] Manter Quadra
	 * [SB0001] - Atualizar Quadra
	 * Método que atualiza a rota dos imóveis pela quadra e rota(informada ou não).
	 * 
	 * @author Anderson Italo
	 * @date 24/01/2012
	 * @throws ErroRepositorioException
	 */
	public void atualizarRotaImoveisPorQuadra(Integer idQuadra, Integer idRotaAtualizar, Integer idRotaAnterior)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		StringBuffer update = new StringBuffer();

		try{

			update.append("update gcom.cadastro.imovel.Imovel imov set ");
			update.append("imov.rota.id = :idRotaAtualizar,");
			update.append("imov.ultimaAlteracao = :ultimaAlteracao ");
			update.append("where imov.quadra.id= :idQuadra ");

			if(idRotaAnterior != null){

				update.append("and imov.rota.id = :idRotaAnterior");

				session.createQuery(update.toString()).setInteger("idRotaAtualizar", idRotaAtualizar).setTimestamp("ultimaAlteracao",
								new Date()).setInteger("idQuadra", idQuadra).setInteger("idRotaAnterior", idRotaAnterior).executeUpdate();
			}else{

				session.createQuery(update.toString()).setInteger("idRotaAtualizar", idRotaAtualizar).setTimestamp("ultimaAlteracao",
								new Date()).setInteger("idQuadra", idQuadra).executeUpdate();
			}

		}catch(HibernateException e){

			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");

		}catch(Exception e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0025] Manter Quadra
	 * [SB0001] - Atualizar Quadra
	 * Método que atualiza o distrito operacional dos imóveis pela quadra e distrito operacional
	 * anterior(informada ou não).
	 * 
	 * @author Luciano Galvão
	 * @date 31/05/2012
	 * @throws ErroRepositorioException
	 */
	public void atualizarDistritoOperacionalImoveisPorQuadra(Integer idQuadra, Integer idDistritoOperacionalAtualizar,
					Integer idDistritoOperacionalAnterior) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		StringBuffer update = new StringBuffer();

		try{

			update.append("update gcom.cadastro.imovel.Imovel imov set ");
			update.append("imov.distritoOperacional.id = :idDistritoOperacionalAtualizar,");
			update.append("imov.ultimaAlteracao = :ultimaAlteracao ");
			update.append("where imov.quadra.id= :idQuadra ");

			if(idDistritoOperacionalAnterior != null){

				update.append("and imov.distritoOperacional.id = :idDistritoOperacionalAnterior");

				session.createQuery(update.toString()).setInteger("idDistritoOperacionalAtualizar", idDistritoOperacionalAtualizar)
								.setTimestamp("ultimaAlteracao", new Date()).setInteger("idQuadra", idQuadra).setInteger(
												"idDistritoOperacionalAnterior", idDistritoOperacionalAnterior).executeUpdate();
			}else{

				session.createQuery(update.toString()).setInteger("idDistritoOperacionalAtualizar", idDistritoOperacionalAtualizar)
								.setTimestamp("ultimaAlteracao", new Date()).setInteger("idQuadra", idQuadra).executeUpdate();
			}

		}catch(HibernateException e){

			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");

		}catch(Exception e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0025] Manter Quadra
	 * [SB0004] - Emitir Relatório dos Imóveis da Quadra
	 * 
	 * @author Anderson Italo
	 * @date 24/01/2011
	 */
	public Collection pesquisarRelatorioImoveisPorQuadra(Integer idQuadra) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();
		Collection retorno = null;

		try{

			consulta.append("select distinct imov.id, imov.localidade.id, setor.codigo, ");
			consulta.append("quad.numeroQuadra, imov.lote, imov.subLote,  ");
			consulta.append("rotaImovel.codigo, ");
			consulta.append("doImov.descricaoAbreviada, ");
			consulta.append("doQuadra.descricaoAbreviada ");
			consulta.append("from Imovel imov ");
			consulta.append("left join imov.distritoOperacional doImov ");
			consulta.append("inner join imov.setorComercial setor ");
			consulta.append("inner join imov.quadra quad ");
			consulta.append("inner join quad.rota rot ");
			consulta.append("left join quad.distritoOperacional doQuadra ");
			consulta.append("left join imov.rota rotaImovel ");
			consulta.append("where quad.id = :idQuadra ");
			consulta.append("order by imov.localidade.id, setor.codigo, quad.numeroQuadra, ");
			consulta.append("imov.lote, imov.subLote, rotaImovel.codigo ");

			retorno = session.createQuery(consulta.toString()).setInteger("idQuadra", idQuadra).list();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(Exception e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0025] Manter Quadra
	 * [SB0004] - Emitir Relatório dos Imóveis da Quadra
	 * 
	 * @author Anderson Italo
	 * @date 24/01/2011
	 */
	public Integer pesquisarTotalRegistrosRelatorioImoveisPorQuadra(Integer idQuadra) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();
		Integer retorno = null;
		Object retornoConsulta = null;

		try{

			consulta.append("select count(distinct imov.id) ");
			consulta.append("from Imovel imov ");
			consulta.append("where imov.quadra.id = :idQuadra");

			retornoConsulta = session.createQuery(consulta.toString()).setInteger("idQuadra", idQuadra).uniqueResult();

			if(retornoConsulta != null){

				retorno = ((Number) retornoConsulta).intValue();
			}

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(Exception e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Obtém os imovéis para gerar o arquivo da agencia virtual web
	 * 
	 * @autor Josenildo Neves
	 * @return Coleção de imovel
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 * @date 20/03/2012
	 */

	public Collection<ImovelArquivoAgenciaVirtualWebHelper> pesquisarImovelArquivoAgenciaVirtualWebPorSetorComercial(
					Integer idSetorComercial) throws ErroRepositorioException{

		List<Object> retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();
		Collection<ImovelArquivoAgenciaVirtualWebHelper> coll = new ArrayList<ImovelArquivoAgenciaVirtualWebHelper>();

		try{

			consulta.append("select ");
			consulta.append("	i.IMOV_ID as idImovel, ");
			consulta.append("	i.LOCA_ID as idlocalidade, ");
			consulta.append("	sc.STCM_CDSETORCOMERCIAL as cdComercial, ");
			consulta.append("	r.ROTA_CDROTA as cdRota, ");
			consulta.append("	i.imov_nnsegmento as numeroSegmento, ");
			consulta.append("	i.imov_nnlote as lote, ");
			consulta.append("	i.imov_nnsublote as sublote, ");
			consulta.append("	c.CLIE_ID as idCliente, ");
			consulta.append("	c.CLIE_NMCLIENTE as nomeCliente, ");
			consulta.append("	hih.HIDI_NNHIDROMETRO as numeroHidrometro ");
			consulta.append("from Cliente_Imovel ci ");
			consulta.append("	left join cliente c on c.CLIE_ID = ci.CLIE_ID ");
			consulta.append("	left join imovel i on i.IMOV_ID = ci.IMOV_ID ");
			consulta.append("	left join rota r on r.ROTA_ID = i.ROTA_ID ");
			consulta.append("   left join setor_comercial sc on i.STCM_ID = sc.STCM_ID ");
			consulta.append("	left join ligacao_agua la on la.LAGU_ID = i.IMOV_ID ");
			consulta.append("	left join HIDROMETRO_INSTALACAO_HIST hih on la.HIDI_ID = hih.HIDI_ID ");
			consulta.append("where ");
			consulta.append("	ci.CRTP_ID = :clienteRelacaoTipo and ");

			consulta.append("  NOT EXISTS   (SELECT ci.clie_id  FROM CLIENTE_IMOVEL ci   ");
			consulta.append(" LEFT JOIN CLIENTE clie   ON ci.CLIE_ID = clie.CLIE_ID ");
			consulta.append(" LEFT JOIN CLIENTE_TIPO cltp   ON clie.CLTP_ID   = cltp.CLTP_ID ");
			consulta
							.append("  WHERE ci.CRTP_ID  = :clienteRelacaoTipoResp AND (cltp.EPOD_ID = :municipal   OR cltp.EPOD_ID   = :estadual   OR cltp.EPOD_ID   = :federal) AND ci.IMOV_ID = i.IMOV_ID    GROUP BY ci.clie_id ) and ");

			consulta.append("NOT EXISTS  ");
			consulta.append(" (SELECT ");
			consulta.append("  imovSubCat.IMOV_ID ");
			consulta.append("		from Imovel_Subcategoria imovSubCat ");
			// consulta.append("		left join subcategoria sc on sc.SCAT_ID = imovSubCat.SCAT_ID ");
			// consulta.append("		left join categoria cat on cat.CATG_ID = sc.CATG_ID ");
			consulta.append("		where ");
			// consulta.append(" 			cat.CATG_ID = :idCategoria and imovSubCat.IMOV_ID = i.IMOV_ID) and ");
			consulta.append(" 			imovSubCat.CATG_ID = :idCategoria and imovSubCat.IMOV_ID = i.IMOV_ID) and ");
			consulta.append("	ci.clim_dtrelacaofim is null ");
			consulta.append("	and i.STCM_ID = :idSetorComercial");

			// apenas teste -----retirar
			// consulta.append("	AND i.LOCA_ID = 27 ");
			// apenas teste -----retirar

			retorno = session.createSQLQuery(consulta.toString()).addScalar("idImovel", Hibernate.INTEGER).addScalar("idLocalidade",
							Hibernate.INTEGER).addScalar("cdComercial", Hibernate.INTEGER).addScalar("cdRota", Hibernate.SHORT).addScalar(
							"numeroSegmento", Hibernate.SHORT).addScalar("lote", Hibernate.SHORT).addScalar("sublote", Hibernate.SHORT)
							.addScalar("idCliente", Hibernate.INTEGER).addScalar("nomeCliente", Hibernate.STRING).addScalar(
											"numeroHidrometro", Hibernate.STRING).setInteger("clienteRelacaoTipo",
											ClienteRelacaoTipo.PROPRIETARIO).setInteger("clienteRelacaoTipoResp",
											ClienteRelacaoTipo.RESPONSAVEL).setInteger("municipal", EsferaPoder.MUNICIPAL).setInteger(
											"estadual", EsferaPoder.ESTADUAL).setInteger("federal", EsferaPoder.FEDERAL).setInteger(
											"idCategoria", Categoria.PUBLICO).setInteger("idSetorComercial", idSetorComercial).list();

			Object[] list = retorno.toArray();

			for(int i = 0; i < (list.length); i++){
				Object[] lista = (Object[]) list[i];
				ImovelArquivoAgenciaVirtualWebHelper helper = new ImovelArquivoAgenciaVirtualWebHelper((Integer) lista[0],
								(Integer) lista[1], (Integer) lista[2], (Short) lista[3], (Short) lista[4], (Short) lista[5],
								(Short) lista[6], (Integer) lista[7], (String) lista[8], (String) lista[9]);

				coll.add(helper);
			}

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return coll;

	}

	public Object pesquisarImovelIdComContaHistorico(Integer imovelId, Integer anoMesReferencia) throws ErroRepositorioException{

		Object retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append("select ct.id from ContaHistorico ct ");
			consulta.append("inner join ct.imovel im ");
			consulta.append("where im.id = :imovelId and ct.anoMesReferenciaConta = :anoMesReferencia");

			retorno = session.createQuery(consulta.toString()).setInteger("imovelId", imovelId.intValue()).setInteger("anoMesReferencia",
							anoMesReferencia).setMaxResults(1).uniqueResult();
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
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * Consultar Imóvel pelo Id
	 * 
	 * @author Anderson Italo
	 * @date 24/03/2012
	 */
	public Imovel pesquisarImovelPorId(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();
		Imovel retorno = null;

		try{

			consulta.append("from Imovel imov ");
			consulta.append("where imov.id = :idImovel");

			retorno = (Imovel) session.createQuery(consulta.toString()).setInteger("idImovel", idImovel).uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}catch(Exception e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Obtém o cliente responsável do imovel para gerar o arquivo da agencia virtual web
	 * 
	 * @autor Josenildo Neves
	 * @return Coleção de imovel
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 * @date 01/06/2012
	 */

	public Collection pesquisarClienteResponsavelArquivoAgenciaVirtualWeb(Integer idImovel) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("select ");
			consulta.append("	ci.CLIE_ID as idCliente ");
			consulta.append("from Cliente_Imovel ci ");
			consulta.append("where ");
			consulta.append("	ci.CRTP_ID = :clienteRelacaoTipo and ");
			consulta.append("	ci.IMOV_ID = :idImovel ");

			retorno = session.createSQLQuery(consulta.toString()).addScalar("idCliente", Hibernate.INTEGER).setInteger(
							"clienteRelacaoTipo", ClienteRelacaoTipo.RESPONSAVEL).setInteger("idImovel", idImovel).list();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	/**
	 * 
	 */

	public Collection pesquisarObterQuantidadeEconomias(Integer idImovel) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuilder consulta = new StringBuilder();

		try{

			consulta.append("select sum(IMSB_QTECONOMIA) as somatorioQtdEconomias, CATG_ID as categoria from imovel_subcategoria ");
			consulta.append("where imov_id = :idImovel ");
			consulta.append("group by CATG_ID ");

			Query query = session.createSQLQuery(consulta.toString()).addScalar("somatorioQtdEconomias", Hibernate.INTEGER).addScalar(
							"categoria", Hibernate.INTEGER);

			retorno = (Collection) query.setInteger("idImovel", idImovel).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * Obtém informações do cliente responsável do imóvel
	 * 
	 * @author Anderson Italo
	 * @throws ErroRepositorioException
	 * @date 04/06/2012
	 */
	public Object[] pesquisarInformacoesClienteResponsavel(Integer idImovel) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer sql = new StringBuffer();

		try{

			sql.append(" select cli.clie_id as idCliente, cli.clie_nmcliente as nomeCliente ");
			sql.append(" from cliente_imovel cliImovel ");
			sql.append(" inner join cliente cli on cli.clie_id = cliImovel.clie_id");
			sql.append(" where cliImovel.imov_id = " + idImovel.toString());
			sql.append(" and cliImovel.crtp_id = " + ClienteRelacaoTipo.RESPONSAVEL.toString());
			sql.append(" and cliImovel.clim_dtrelacaofim is null ");

			retorno = (Object[]) session.createSQLQuery(sql.toString()).addScalar("idCliente", Hibernate.INTEGER).addScalar("nomeCliente",
							Hibernate.STRING).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * gcom.cadastro.imovel.IRepositorioImovel#pesquisarImoveisPorFaturamentoAtividadeCronograma
	 * (java.lang.Integer)
	 */
	public Collection<Object[]> pesquisarImoveisPorFaturamentoAtividadeCronograma(Integer idSetor, Integer idFaturamentoAtvCron)
					throws ErroRepositorioException{

		StringBuffer consulta = criarConsultaPesquisarImoveisPorSetorComercialCriterioCobranca();
		consulta.append(", FATURAMENTO_ATIVIDADE_CRON FAC ");
		consulta.append(", FATURAMENTO_ATIV_CRON_ROTA FACR ");

		consulta.append(" where i.qdra_id = q.qdra_id");
		consulta.append("   and i.rota_id = r.rota_id");
		consulta.append("   and i.stcm_id = sc.stcm_id");

		consulta.append(" and FACR.ROTA_ID = r.rota_id ");
		consulta.append(" and FAC.FTAC_ID = FACR.FTAC_ID ");
		consulta.append(" AND FAC.FTAC_ID = :ftac_id ");
		consulta.append(" AND sc.stcm_id = :stcm_id ");

		Session session = HibernateUtil.getSession();
		List retorno = Collections.EMPTY_LIST;
		try{
			SQLQuery query = criarSQLQuyery(consulta, session);
			query.setInteger("stcm_id", idSetor);
			query.setInteger("ftac_id", idFaturamentoAtvCron);

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

	public List<Integer> consultarSetoresPorFaturamentoAtividadeCronograma(Integer idFaturamentoAtividadeCronograma)
					throws ErroRepositorioException{

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DISTINCT im.STCM_ID id_setor FROM IMOVEL im ");
		sql.append(" INNER JOIN SETOR_COMERCIAL sc ON im.STCM_ID = sc.STCM_ID ");
		sql.append(" INNER JOIN ( ");
		sql.append(" SELECT rt.ROTA_ID rt_id FROM FATURAMENTO_ATIVIDADE_CRON fac ");
		sql.append(" INNER JOIN FATURAMENTO_ATIV_CRON_ROTA facr ON fac.FTAC_ID = facr.FTAC_ID ");
		sql.append(" INNER JOIN ROTA rt ON rt.ROTA_ID = facr.ROTA_ID ");
		sql.append(" WHERE fac.FTAC_ID = :FTAC_ID ");
		sql.append(" ) rt_fac ON rt_fac.rt_id = im.ROTA_ID");

		Session session = HibernateUtil.getSession();
		List retorno = Collections.EMPTY_LIST;
		try{
			SQLQuery query = session.createSQLQuery(sql.toString());
			query.addScalar("id_setor", Hibernate.INTEGER);
			query.setInteger("FTAC_ID", idFaturamentoAtividadeCronograma);
			retorno = query.list();
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
	 * @author Saulo Lima
	 * @date 27/06/2012
	 * @param Integer
	 *            idImovel
	 * @return Object[] idLocalidade, idSetorComercial, idBairro
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosLocalizacaoImovel(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Object[] retorno = null;

		try{
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT i.loca_id, i.stcm_id, b.bair_id ");
			sql.append("FROM imovel i ");
			sql.append("INNER JOIN logradouro_bairro b on i.lgbr_id = b.lgbr_id ");
			sql.append("WHERE i.imov_id = :idImovel");

			SQLQuery query = session.createSQLQuery(sql.toString());
			query.addScalar("loca_id", Hibernate.INTEGER);
			query.addScalar("stcm_id", Hibernate.INTEGER);
			query.addScalar("bair_id", Hibernate.INTEGER);
			query.setInteger("idImovel", idImovel);

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
	 * Pesquisar Rotas dos Imóveis
	 * 
	 * @author Hebert Falcão
	 * @date 12/11/2012
	 */
	public Collection<Rota> pesquisarRotasDosImoveis(Collection<Integer> idImoveis) throws ErroRepositorioException{

		Collection<Rota> rotas = null;

		Session session = HibernateUtil.getSession();

		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("select distinct imov.rota ");
			consulta.append("from Imovel imov ");
			consulta.append("where imov.id in (:idImoveis) ");

			Query query = session.createQuery(consulta.toString());

			if(idImoveis.size() <= 1000){
				query.setParameterList("idImoveis", idImoveis);

				rotas = query.list();
			}else{
				rotas = new ArrayList();

				Collection<Rota> rotasAux = new ArrayList<Rota>();

				Collection<Integer> idImoveisAux = new ArrayList<Integer>();

				for(Integer idImovel : idImoveis){
					idImoveisAux.add(idImovel);

					if(idImoveisAux.size() == 1000){
						query.setParameterList("idImoveis", idImoveisAux);

						rotasAux.addAll(query.list());

						idImoveisAux.clear();
					}
				}

				if(!Util.isVazioOrNulo(idImoveisAux) && idImoveisAux.size() < 1000){
					query.setParameterList("idImoveis", idImoveisAux);

					rotasAux.addAll(query.list());

					idImoveisAux.clear();
				}

				if(!Util.isVazioOrNulo(rotasAux)){
					Collection<Integer> idRotas = new ArrayList<Integer>();

					Integer idRota = null;

					for(Rota rota : rotasAux){
						idRota = rota.getId();

						if(!idRotas.contains(idRota)){
							rotas.add(rota);
							idRotas.add(idRota);
						}
					}
				}
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return rotas;
	}

	public Short obterCodigoRota(Integer idRota) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Short codigo = null;

		try{

			Criteria criteria = session.createCriteria(Rota.class);
			criteria.add(Restrictions.idEq(idRota));
			criteria.setProjection(Projections.property("codigo"));

			codigo = (Short) criteria.uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{
			HibernateUtil.closeSession(session);
		}

		return codigo;

	}

	/**
	 * Gerar Relatório de Imóvel Outros Critérios Contador
	 * 
	 * @author Ítalo Almeida
	 * @date 10/01/2013
	 */

	public int gerarRelatorioImovelOutrosCriteriosCount(

	String idImovelCondominio, String idImovelPrincipal,

	String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua,

	String consumoMinimoFinalAgua, String[] idSituacaoLigacaoEsgoto,

	String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,

	String intervaloValorPercentualEsgotoInicial,

	String intervaloValorPercentualEsgotoFinal,

	String intervaloMediaMinimaImovelInicial,

	String intervaloMediaMinimaImovelFinal,

	String intervaloMediaMinimaHidrometroInicial,

	String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,

	String idPocoTipo, String idFaturamentoSituacaoTipo,

	String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,

	String idEloAnormalidade, String areaConstruidaInicial,

	String areaConstruidaFinal, String idCadastroOcorrencia,

	String idConsumoTarifa, String idGerenciaRegional,

	String idLocalidadeInicial, String idLocalidadeFinal,

	String setorComercialInicial, String setorComercialFinal,

	String quadraInicial, String quadraFinal, String loteOrigem,

	String loteDestno, String cep, String logradouro, String bairro,

	String municipio, String idTipoMedicao, String indicadorMedicao,

	String idSubCategoria, String idCategoria,

	String quantidadeEconomiasInicial, String quantidadeEconomiasFinal,

	String diaVencimento, String idCliente, String idClienteTipo,

	String idClienteRelacaoTipo, String numeroPontosInicial,

	String numeroPontosFinal, String numeroMoradoresInicial,

	String numeroMoradoresFinal, String idAreaConstruidaFaixa,

	String idUnidadeNegocio, String cdRotaInicial, String cdRotaFinal, String sequencialRotaInicial, String sequencialRotaFinal,
					String segmentoInicial, String segmentoFinal, String subloteInicial, String subloteFinal)
					throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = consulta
							+ "select distinct count(*) as count "

							+

							// From
							"from imovel_subcategoria imovelSubcategoria "
							+ "inner join imovel on imovelSubcategoria.imov_id = imovel.imov_id "
							+ "inner join localidade on imovel.loca_id = localidade.loca_id "
							+ "inner join unidade_negocio unidadeNegocio on localidade.uneg_id = unidadeNegocio.uneg_id "
							+ "inner join gerencia_regional on localidade.greg_id = gerencia_regional.greg_id "
							+ "inner join setor_comercial on imovel.stcm_id = setor_comercial.stcm_id "
							+

							// Logradouro Bairro

							"left join logradouro_bairro on imovel.lgbr_id = logradouro_bairro.lgbr_id "
							+ "left join bairro on logradouro_bairro.bair_id = bairro.bair_id "
							+ "left join municipio on bairro.muni_id = municipio.muni_id "
							+ "inner join quadra on imovel.qdra_id = quadra.qdra_id "
							+ "inner join rota on imovel.rota_id = rota.rota_id "
							+

							// Logradouro Cep
							"left join logradouro_cep on imovel.lgcp_id = logradouro_cep.lgcp_id "
							+ "left join cep on logradouro_cep.cep_id = cep.cep_id "
							+ "left join logradouro on logradouro_cep.logr_id = logradouro.logr_id "
							+

							// AGUA
							"left join ligacao_agua_situacao on imovel.last_id = ligacao_agua_situacao.last_id "
							+ "left join ligacao_agua on imovel.imov_id = ligacao_agua.lagu_id "

							+

							// ESGOTO
							"left join ligacao_esgoto_situacao on imovel.lest_id = ligacao_esgoto_situacao.lest_id "
							+ "left join ligacao_esgoto on imovel.imov_id = ligacao_esgoto.lesg_id "
							+ "left join imovel_perfil on imovel.iper_id = imovel_perfil.iper_id "
							+ "left join poco_tipo on imovel.poco_id = poco_tipo.poco_id "
							+ "left join area_construida_faixa on imovel.acon_id = area_construida_faixa.acon_id "
							+

							// Cliente Usuario
							"left outer join cliente_imovel cliente_imovel_usuario on imovel.imov_id = cliente_imovel_usuario.imov_id  "
							+ " and (cliente_imovel_usuario.crtp_id=2 and cliente_imovel_usuario.clim_dtrelacaofim is null) "
							+ "left outer join cliente cliente_usuario on cliente_imovel_usuario.clie_id = cliente_usuario.clie_id "
							+

							// Cliente Resposanvel
							"left outer join cliente_imovel  cliente_imovel_responsavel on imovel.imov_id = cliente_imovel_responsavel.imov_id "
							+ " and (cliente_imovel_responsavel.crtp_id=3 and cliente_imovel_responsavel.clim_dtrelacaofim is null) "
							+ "left outer join cliente  cliente_responsavel on cliente_imovel_responsavel.clie_id = cliente_responsavel.clie_id "
							+

							// AGUA
							"left join hidrometro_instalacao_hist on ligacao_agua.hidi_id =  hidrometro_instalacao_hist.hidi_id "
							+

							// ESGOTO
							"left join hidrometro_instalacao_hist  hidrom_inst_hist_esgoto on imovel.hidi_id =  hidrom_inst_hist_esgoto.hidi_id "
							+

							// Relacionamento para o Relatorio de Imovel
							"left join logradouro_titulo on logradouro.lgtt_id = logradouro_titulo.lgtt_id "
							+ "left join logradouro_tipo on logradouro.lgtp_id = logradouro_tipo.lgtp_id "
							+ "left join endereco_referencia on imovel.edrf_id = endereco_referencia.edrf_id "
							+ "left join unidade_federacao on municipio.unfe_id = unidade_federacao.unfe_id "
							+ "left join reservatorio_volume_faixa on imovel.resv_idreservatoriosuperior = reservatorio_volume_faixa.resv_id "
							+ "left join reservatorio_volume_faixa reservatorio_volume_faixa_inf on imovel.resv_idreservatorioinferior = reservatorio_volume_faixa_inf.resv_id "
							+ "left join piscina_volume_faixa on imovel.pisc_id = piscina_volume_faixa.pisc_id "
							+ "left join pavimento_calcada on imovel.pcal_id = pavimento_calcada.pcal_id "
							+ "left join pavimento_rua on imovel.prua_id = pavimento_rua.prua_id "
							+ "left join despejo on imovel.depj_id = despejo.depj_id "
							+

							// AGUA
							"left join ligacao_agua_diametro on ligacao_agua.lagd_id = ligacao_agua_diametro.lagd_id "
							+ "left join ligacao_agua_material on ligacao_agua.lagm_id = ligacao_agua_material.lagm_id "
							+

							// ESGOTO
							"left join ligacao_esgoto_diametro on ligacao_esgoto.legd_id = ligacao_esgoto_diametro.legd_id "
							+ "left join ligacao_esgoto_material on ligacao_esgoto.legm_id = ligacao_esgoto_material.legm_id "
							+

							// AGUA
							"left join hidrometro on hidrometro_instalacao_hist.hidr_id = hidrometro.hidr_id "
							+ "left join hidrometro_capacidade on hidrometro.hicp_id = hidrometro_capacidade.hicp_id "
							+ "left join hidrometro_marca on hidrometro.himc_id = hidrometro_marca.himc_id "
							+ "left join hidrometro_diametro on hidrometro.hidm_id = hidrometro_diametro.hidm_id "
							+ "left join hidrometro_tipo on hidrometro.hitp_id = hidrometro_tipo.hitp_id "
							+ "left join hidrometro_local_instalacao on hidrometro_instalacao_hist.hili_id = hidrometro_local_instalacao.hili_id "
							+ "left join hidrometro_protecao on hidrometro_instalacao_hist.hipr_id = hidrometro_protecao.hipr_id "
							+

							// ESGOTO
							"left join hidrometro  hidrometro_esgoto on hidrom_inst_hist_esgoto.hidr_id = hidrometro_esgoto.hidr_id "
							+ "left join hidrometro_capacidade  hidrometro_capacidade_esgoto on hidrometro_esgoto.hicp_id = hidrometro_capacidade_esgoto.hicp_id "
							+ "left join hidrometro_marca  hidrometro_marca_esgoto on hidrometro_esgoto.himc_id = hidrometro_marca_esgoto.himc_id "
							+ "left join hidrometro_diametro  hidrometro_diametro_esgoto on hidrometro_esgoto.hidm_id = hidrometro_diametro_esgoto.hidm_id "
							+ "left join hidrometro_tipo  hidrometro_tipo_esgoto on hidrometro_esgoto.hitp_id = hidrometro_tipo_esgoto.hitp_id "
							+ "left join hidrometro_local_instalacao  hidrometro_local_instalac_esg on hidrom_inst_hist_esgoto.hili_id = hidrometro_local_instalacao.hili_id "
							+ "left join hidrometro_protecao  hidrometro_protecao_esgoto on hidrom_inst_hist_esgoto.hipr_id = hidrometro_protecao_esgoto.hipr_id "

							// Rota do Imóvel
							+ "left join rota rotaImovel on rotaImovel.rota_id = imovel.rota_id ";

			consulta = consulta
							+ montarInnerJoinFiltrarImoveisOutrosCriterios(intervaloMediaMinimaImovelInicial,
											intervaloMediaMinimaImovelFinal, intervaloMediaMinimaHidrometroInicial,
											intervaloMediaMinimaHidrometroFinal, idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo,
											idSituacaoEspecialCobranca, idEloAnormalidade, idCadastroOcorrencia, idConsumoTarifa,
											idTipoMedicao, indicadorMedicao, idSubCategoria, idCategoria, idCliente, idClienteTipo,
											idClienteRelacaoTipo, ConstantesSistema.GERAR_RELATORIO_IMOVEL);

			consulta = consulta
							+ montarCondicaoSQLWhereFiltrarImovelOutrosCriterio(idImovelCondominio, idImovelPrincipal,
											idSituacaoLigacaoAgua, consumoMinimoInicialAgua, consumoMinimoFinalAgua,
											idSituacaoLigacaoEsgoto, consumoMinimoInicialEsgoto, consumoMinimoFinalEsgoto,
											intervaloValorPercentualEsgotoInicial, intervaloValorPercentualEsgotoFinal,
											intervaloMediaMinimaImovelInicial, intervaloMediaMinimaImovelFinal,
											intervaloMediaMinimaHidrometroInicial, intervaloMediaMinimaHidrometroFinal, idImovelPerfil,
											idPocoTipo, idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo, idSituacaoEspecialCobranca,
											idEloAnormalidade, areaConstruidaInicial, areaConstruidaFinal, idCadastroOcorrencia,
											idConsumoTarifa, idGerenciaRegional, idLocalidadeInicial, idLocalidadeFinal,
											setorComercialInicial, setorComercialFinal, quadraInicial, quadraFinal, loteOrigem, loteDestno,
											cep, logradouro, bairro, municipio, idTipoMedicao, indicadorMedicao, idSubCategoria,
											idCategoria, quantidadeEconomiasInicial, quantidadeEconomiasFinal, diaVencimento, idCliente,
											idClienteTipo, idClienteRelacaoTipo, numeroPontosInicial, numeroPontosFinal,
											numeroMoradoresInicial, numeroMoradoresFinal, idAreaConstruidaFaixa, idUnidadeNegocio,
											ConstantesSistema.GERAR_RELATORIO_IMOVEL, cdRotaInicial, cdRotaFinal, sequencialRotaInicial,
											sequencialRotaFinal, segmentoInicial, segmentoFinal, subloteInicial, subloteFinal);

			/*
			 * # COLOCANDO O VALOR NAS CONDIÇÕES#
			 */

			SQLQuery query = session.createSQLQuery(consulta.substring(0, (consulta.length() - 5)));
			informarDadosQueryFiltrarImovelOutrosCriterio(query, idImovelCondominio, idImovelPrincipal, idSituacaoLigacaoAgua,
							consumoMinimoInicialAgua, consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto, consumoMinimoInicialEsgoto,
							consumoMinimoFinalEsgoto, intervaloValorPercentualEsgotoInicial, intervaloValorPercentualEsgotoFinal,
							intervaloMediaMinimaImovelInicial, intervaloMediaMinimaImovelFinal, intervaloMediaMinimaHidrometroInicial,
							intervaloMediaMinimaHidrometroFinal, idImovelPerfil, idPocoTipo, idFaturamentoSituacaoTipo,
							idCobrancaSituacaoTipo, idSituacaoEspecialCobranca, idEloAnormalidade, areaConstruidaInicial,
							areaConstruidaFinal, idCadastroOcorrencia, idConsumoTarifa, idGerenciaRegional, idLocalidadeInicial,
							idLocalidadeFinal, setorComercialInicial, setorComercialFinal, quadraInicial, quadraFinal, loteOrigem,
							loteDestno, cep, logradouro, bairro, municipio, idTipoMedicao, indicadorMedicao, idSubCategoria, idCategoria,
							quantidadeEconomiasInicial, quantidadeEconomiasFinal, diaVencimento, idCliente, idClienteTipo,
							idClienteRelacaoTipo, numeroPontosInicial, numeroPontosFinal, numeroMoradoresInicial, numeroMoradoresFinal,
							idAreaConstruidaFaixa, idUnidadeNegocio, cdRotaInicial, cdRotaFinal, sequencialRotaInicial,
							sequencialRotaFinal, segmentoInicial, segmentoFinal, subloteInicial, subloteFinal);

			retorno = (Integer) query.addScalar("count", Hibernate.INTEGER).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){

			// levanta a exceção para a próxima camada

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			// fecha a sessão

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	public Collection pesquisarImoveisGerarProvisaoReceita(Integer idSetorComercial) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection retorno = null;

		try{

			StringBuffer hql = new StringBuffer();
			hql.append("select imov ");
			hql.append("from Imovel imov ");
			hql.append("inner join fetch imov.localidade lo ");
			hql.append("inner join fetch imov.rota rt ");
			hql.append("inner join fetch imov.ligacaoEsgotoSituacao lest ");
			hql.append("inner join fetch imov.ligacaoAguaSituacao last ");
			hql.append("left join fetch imov.consumoTarifaTemporaria tarifaTemp ");
			hql.append("left join fetch imov.consumoTarifa tarif ");
			hql.append("where imov.setorComercial.id = " + idSetorComercial.toString());

			Query query = session.createQuery(hql.toString());

			retorno = query.list();
		}catch(Exception e){

			throw new ErroRepositorioException(e, "Erro durante a consulta");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * 
	 * @author Anderson Italo
	 * @date 01/04/2013
	 */
	public Cliente pesquisarClientePropietarioImovel(Integer idImovel) throws ErroRepositorioException{

		Cliente retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuilder consulta = new StringBuilder();

		try{

			consulta.append("select cli ");
			consulta.append("from Cliente cli ");
			consulta.append("inner join fetch cli.clienteTipo cliTip ");
			consulta.append("where exists (select 1 from ClienteImovel cliImov where ");
			consulta.append("cliImov.imovel.id = :idImovel ");
			consulta.append("and cliImov.cliente.id = cli.id ");
			consulta.append("and cliImov.clienteRelacaoTipo.id = :tipoRelacao and cliImov.dataFimRelacao is null) ");

			retorno = (Cliente) session.createQuery(consulta.toString()).setInteger("idImovel", idImovel)
							.setInteger("tipoRelacao", ClienteRelacaoTipo.PROPRIETARIO).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.cadastro.imovel.IRepositorioImovel#pesquisarImoveisPorLocalidade(java.lang.Integer)
	 */
	public Collection<Integer> pesquisarImoveisPorLocalidade(Integer idLocalidade) throws ErroRepositorioException{

		Collection<Integer> retorno = null;
		StringBuilder consulta = new StringBuilder();

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{
			consulta.append("SELECT i.id ");
			consulta.append("FROM Imovel i ");
			consulta.append("WHERE ");
			consulta.append("i.localidade.id = :idLocalidade ");
			retorno = (Collection<Integer>) session.createQuery(consulta.toString()).setInteger("idLocalidade", idLocalidade).list();

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
	 * [UC0098] – Manter Vinculos Rateio Consumo
	 * [FS0011] – Verificar ciclo de faturamento do imóvel
	 * 
	 * @author Ítalo Almeida
	 * @date 09/07/2013
	 */
	public Integer retornarAnoMesReferenciaFaturamentoGrupoImovel(Integer idImovel) throws ErroRepositorioException{

		Integer retorno = 0;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{

			consulta = "select f.ftgr_amreferencia from faturamento_grupo f " + "inner join rota r on r.ftgr_id = f.ftgr_id "
							+ "inner join imovel i on i.rota_id = r.rota_id " + "where i.imov_id = :idImovel";

			retorno = ((Number) session.createSQLQuery(consulta.toString()).setInteger("idImovel", idImovel).setMaxResults(1)
							.uniqueResult()).intValue();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0098] – Manter Vinculos Rateio Consumo
	 * [FS0011] – Verificar ciclo de faturamento do imóvel
	 * 
	 * @author Ítalo Almeida
	 */
	public Boolean verificarFaturamentoGrupoImovelIniciado(Integer idImovel, String atividadeFaturamento)
					throws ErroRepositorioException{

		Boolean retorno = false;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{

			consulta = "SELECT FA.FTAC_TMREALIZACAO " + "FROM FATURAMENTO_GRUPO_CRON_MENSAL FC, "
							+ "FATURAMENTO_ATIVIDADE_CRON FA, FATURAMENTO_GRUPO FG "
							+ "WHERE  FC.FTCM_AMREFERENCIA = FG.FTGR_AMREFERENCIA " + "AND FC.FTCM_ID = FA.FTCM_ID "
							+ "AND FC.FTGR_ID = FG.FTGR_ID " + "AND FA.FTAT_ID = (select FTAT_ID from faturamento_atividade "
							+ "where ftat_dsfaturamentoatividade = :atividadeFaturamento) "
							+ "AND FG.FTGR_ID = (select f.ftgr_id from faturamento_grupo f inner join rota r on r.ftgr_id = f.ftgr_id "
							+ "inner join imovel i on i.rota_id = r.rota_id where i.imov_id = :idImovel)";

			Date resultado = (Date) session.createSQLQuery(consulta.toString()).setInteger("idImovel", idImovel)
							.setString("atividadeFaturamento", atividadeFaturamento).setMaxResults(1).uniqueResult();

			if(resultado != null){
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
	 * [UC0098] – Manter Vinculos Rateio Consumo
	 * [FS0011] – Verificar ciclo de faturamento do imóvel
	 * 
	 * @author Ítalo Almeida
	 */
	public Boolean verificarFaturamentoGrupoImovelEncerrado(Integer idImovel, String atividadeFaturamento) throws ErroRepositorioException{

		Boolean retorno = false;

		try{

			retorno = this.verificarFaturamentoGrupoImovelIniciado(idImovel, atividadeFaturamento);

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}

		return retorno;
	}

	/**
	 * [UC0098] – Manter Vinculos Rateio Consumo
	 * [FS0011] – Verificar ciclo de faturamento do imóvel
	 * 
	 * @author Ítalo Almeida
	 */
	public Integer retornaTipoLeituraFaturamento(Integer idImovel) throws ErroRepositorioException{

		Integer retorno;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{

			consulta = "select l.lttp_id from leitura_tipo l " + "inner join rota r on r.lttp_id = l.lttp_id "
							+ "inner join imovel i on i.rota_id = r.rota_id " + "where i.imov_id = :idImovel";

			retorno = ((Number) session.createSQLQuery(consulta.toString()).setInteger("idImovel", idImovel).setMaxResults(1)
							.uniqueResult()).intValue();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public void atualizarNumeroEmissaoContrato(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String sql = null;

		try{

			sql = "update imovel set imov_nncontratoemissao = (imov_nncontratoemissao + 1) where imov_id = :idImovel";

			session.createSQLQuery(sql.toString()).setInteger("idImovel", idImovel).executeUpdate();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0102] –Gerar Arquivo Texto para Faturamento Imediato –Modelo 4
	 * 
	 * @author Anderson Italo
	 * @date 03/10/2013
	 */
	public Integer verificarImovelSubcategoriaReparticoesPublicasFederais(Integer idImovel, Integer idCategoria, String idSubcategorias)
					throws ErroRepositorioException{

		Integer retorno = 0;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append("select count(*) from imovel_subcategoria isc where isc.imov_id = :idImovel and isc.catg_id = :idCategoria and isc.scat_id in ("
							+ idSubcategorias + ")");

			retorno = ((Number) session.createSQLQuery(consulta.toString()).setInteger("idImovel", idImovel)
							.setInteger("idCategoria", idCategoria).setMaxResults(1).uniqueResult()).intValue();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * @autor Eduardo Oliveira
	 * @date 16/12/2013
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Integer obterQuantidadeAlteracoesImovel(Integer idImovel) throws ErroRepositorioException{

		Integer retorno;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "SELECT count(clienteImovel.cliente.id) FROM ClienteImovel clienteImovel "
							+ "WHERE clienteImovel.imovel.id = :idImovel ";

			retorno = ((Number) session.createQuery(consulta).setInteger("idImovel", idImovel).uniqueResult()).intValue();

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
	 * @author Anderson Italo
	 * @date 11/02/2014
	 */
	public List<Object[]> pesquisarDadosImoveisComContaEmAtraso(boolean apenasPublicos) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		List<Object[]> retorno = null;
		StringBuilder consulta = new StringBuilder();

		try{

			consulta.append("  SELECT ");
			consulta.append("  LTRIM(to_char(idLocalidade, '000'), ' ') ||'.'|| LTRIM(to_char(codigoSetor, '000'), ' ') ");
			consulta.append("  ||'.'|| LTRIM(to_char(numeroQuadra, '000'), ' ') ||'.'|| LTRIM(to_char(lote, '0000'), ' ') ||'.'|| LTRIM(to_char(sublote, '000'), ' ') as inscricao, ");
			consulta.append("  idLocalidade, ");
			consulta.append("  nomeLocalidade, ");
			consulta.append("  matricula, ");
			consulta.append("  situacaoCobranca, ");
			consulta.append("  usuarioPossuiCPFCNPJ, ");
			consulta.append("  SUM(valorAtrasoAte30Dias)       AS valAtrasoAte30Dias, "); // 6
			consulta.append("  SUM(valorAtraso31A60Dias)       AS valAtraso31A60Dias, ");
			consulta.append("  SUM(valorAtraso61A90Dias)       AS valAtraso61A90Dias, ");
			consulta.append("  SUM(valorAtraso91A120Dias)      AS valAtraso91A120Dias, ");
			consulta.append("  SUM(valorAtraso121A150Dias)     AS valAtraso121A150Dias, ");
			consulta.append("  SUM(valorAtraso151A180Dias)     AS valAtraso151A180Dias, ");
			consulta.append("  SUM(valorAtraso181DiasA5Anos)   AS valAtraso181DiasA5Anos, ");
			consulta.append("  SUM(valorAtrasoDe5A10Anos)      AS valAtrasoDe5A10Anos, ");
			consulta.append("  SUM(valorTotal)                 AS valTotalContas, ");
			consulta.append("  SUM(quantidadeAtrasoAte30Dias)  AS qtdAtrasoAte30Dias, ");
			consulta.append("  SUM(quantidadeAtraso31A60Dias)  AS qtdAtraso31A60Dias, ");
			consulta.append("  SUM(quantidadeAtraso61A90Dias)  AS qtdAtraso61A90Dias, ");
			consulta.append("  SUM(quantidadeAtraso91A120Dias) AS qtdAtraso91A120Dias, ");
			consulta.append("  SUM(quantidadeAtraso121A150Dias) AS qtdAtraso121A150Dias, ");
			consulta.append("  SUM(quantidadeAtraso151A180Dias)     AS qtdAtraso151A180Dias, ");
			consulta.append("  SUM(quantidadeAtraso181DiasA5Anos)   AS qtdAtraso181DiasA5Anos, ");
			consulta.append("  SUM(quantidadeAtrasoDe5A10Anos)     AS qtdAtrasoDe5A10Anos, ");
			consulta.append("  SUM(quantidadeTotal)       AS qtdTotalContas ");
			consulta.append("  FROM ");
			consulta.append("  ( ");
			consulta.append("  ");
			consulta.append("  SELECT ");
			consulta.append("    loc.loca_id           AS idLocalidade, ");
			consulta.append("    loc.loca_nmlocalidade AS nomeLocalidade, ");
			consulta.append("    sca.stcm_cdsetorcomercial           AS codigoSetor, ");
			consulta.append("    imo.qdra_nnquadra  AS numeroQuadra, ");
			consulta.append("    imo.imov_nnlote AS lote, ");
			consulta.append("    imo.imov_nnsublote AS sublote, ");
			consulta.append("    imo.imov_id     AS matricula, ");
			consulta.append("    ( SELECT cs.cbst_dscobrancasituacao FROM imovel_cobranca_situacao ics ");
			consulta.append("      INNER JOIN cobranca_situacao cs ON cs.cbst_id = ics.cbst_id ");
			consulta.append("      WHERE ics.imov_id = imo.imov_id AND ics.iscb_dtretiradacobranca IS NULL AND ROWNUM  = 1 ");
			consulta.append("    ) AS situacaoCobranca, ");
			consulta.append("    ( SELECT (CASE WHEN (cli.clie_nncpf IS NOT NULL OR cli.clie_nncnpj IS NOT NULL) THEN 'SIM' ELSE 'NÃO' END) AS temCPFCNPJ ");
			consulta.append("      FROM cliente_imovel cim ");
			consulta.append("      INNER JOIN cliente cli ON cli.clie_id = cim.clie_id ");
			consulta.append("      WHERE cim.imov_id = imo.imov_id AND cim.clim_dtrelacaofim IS NULL AND cim.crtp_id = 2 ");
			consulta.append("    ) AS usuarioPossuiCPFCNPJ, ");
			consulta.append("    ( ");
			consulta.append("    CASE ");
			consulta.append("      WHEN con.cnta_dtvencimentoconta                                                                < CURRENT_TIMESTAMP ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) <= 30 ");
			consulta.append("     THEN SUM((NVL(con.cnta_vlagua,0) + NVL(con.cnta_vlesgoto,0) + NVL(con.cnta_vldebitos,0) - (NVL(con.cnta_vlcreditos,0) + NVL(con.cnta_vlimpostos,0)))) ");
			consulta.append("      ELSE 0 ");
			consulta.append("    END) AS valorAtrasoAte30Dias, ");
			consulta.append("    ( ");
			consulta.append("    CASE ");
			consulta.append("      WHEN con.cnta_dtvencimentoconta                                                                 < CURRENT_TIMESTAMP ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 31 ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) <= 60 ");
			consulta.append("      THEN SUM((NVL(con.cnta_vlagua,0) + NVL(con.cnta_vlesgoto,0) + NVL(con.cnta_vldebitos,0) - (NVL(con.cnta_vlcreditos,0) + NVL(con.cnta_vlimpostos,0)))) ");
			consulta.append("      ELSE 0 ");
			consulta.append("    END) AS valorAtraso31A60Dias, ");
			consulta.append("    ( ");
			consulta.append("    CASE ");
			consulta.append("      WHEN con.cnta_dtvencimentoconta                                                                 < CURRENT_TIMESTAMP ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 61 ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) <= 90 ");
			consulta.append("      THEN SUM((NVL(con.cnta_vlagua,0) + NVL(con.cnta_vlesgoto,0) + NVL(con.cnta_vldebitos,0) - (NVL(con.cnta_vlcreditos,0) + NVL(con.cnta_vlimpostos,0)))) ");
			consulta.append("      ELSE 0 ");
			consulta.append("    END) AS valorAtraso61A90Dias, ");
			consulta.append("    ( ");
			consulta.append("    CASE ");
			consulta.append("      WHEN con.cnta_dtvencimentoconta                                                                < CURRENT_TIMESTAMP ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 91 ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) <= 120 ");
			consulta.append("      THEN SUM((NVL(con.cnta_vlagua,0) + NVL(con.cnta_vlesgoto,0) + NVL(con.cnta_vldebitos,0) - (NVL(con.cnta_vlcreditos,0) + NVL(con.cnta_vlimpostos,0)))) ");
			consulta.append("      ELSE 0 ");
			consulta.append("    END)                        AS valorAtraso91A120Dias, ");
			consulta.append("     ( ");
			consulta.append("    CASE ");
			consulta.append("      WHEN con.cnta_dtvencimentoconta                                                                < CURRENT_TIMESTAMP ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 121 ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) <= 150 ");
			consulta.append("      THEN SUM((NVL(con.cnta_vlagua,0) + NVL(con.cnta_vlesgoto,0) + NVL(con.cnta_vldebitos,0) - (NVL(con.cnta_vlcreditos,0) + NVL(con.cnta_vlimpostos,0)))) ");
			consulta.append("      ELSE 0 ");
			consulta.append("    END)                        AS valorAtraso121A150Dias, ");
			consulta.append("     ( ");
			consulta.append("    CASE ");
			consulta.append("      WHEN con.cnta_dtvencimentoconta                                                                < CURRENT_TIMESTAMP ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 151 ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) <= 180 ");
			consulta.append("      THEN SUM((NVL(con.cnta_vlagua,0) + NVL(con.cnta_vlesgoto,0) + NVL(con.cnta_vldebitos,0) - (NVL(con.cnta_vlcreditos,0) + NVL(con.cnta_vlimpostos,0)))) ");
			consulta.append("      ELSE 0 ");
			consulta.append("    END)                        AS valorAtraso151A180Dias, ");
			consulta.append("     ( ");
			consulta.append("    CASE ");
			consulta.append("      WHEN con.cnta_dtvencimentoconta                                                                < CURRENT_TIMESTAMP ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 181 ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) <= 1825 ");
			consulta.append("      THEN SUM((NVL(con.cnta_vlagua,0) + NVL(con.cnta_vlesgoto,0) + NVL(con.cnta_vldebitos,0) - (NVL(con.cnta_vlcreditos,0) + NVL(con.cnta_vlimpostos,0)))) ");
			consulta.append("      ELSE 0 ");
			consulta.append("    END)                        AS valorAtraso181DiasA5Anos, ");
			consulta.append("     ( ");
			consulta.append("    CASE ");
			consulta.append("      WHEN con.cnta_dtvencimentoconta                                                                < CURRENT_TIMESTAMP ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 1826 ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) <= 3650 ");
			consulta.append("      THEN SUM((NVL(con.cnta_vlagua,0) + NVL(con.cnta_vlesgoto,0) + NVL(con.cnta_vldebitos,0) - (NVL(con.cnta_vlcreditos,0) + NVL(con.cnta_vlimpostos,0)))) ");
			consulta.append("      ELSE 0 ");
			consulta.append("    END)                        AS valorAtrasoDe5A10Anos, ");
			consulta.append("    SUM((NVL(con.cnta_vlagua,0) + NVL(con.cnta_vlesgoto,0) + NVL(con.cnta_vldebitos,0) - (NVL(con.cnta_vlcreditos,0) + NVL(con.cnta_vlimpostos,0)))) AS valorTotal, ");
			consulta.append("    ( ");
			consulta.append("    CASE ");
			consulta.append("      WHEN con.cnta_dtvencimentoconta                                                                < CURRENT_TIMESTAMP ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) < 30 ");
			consulta.append("      THEN COUNT(DISTINCT(con.cnta_id)) ");
			consulta.append("      ELSE 0 ");
			consulta.append("    END) AS quantidadeAtrasoAte30Dias, ");
			consulta.append("    ( ");
			consulta.append("    CASE ");
			consulta.append("      WHEN con.cnta_dtvencimentoconta                                                                 < CURRENT_TIMESTAMP ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 31 ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) <= 60 ");
			consulta.append("      THEN COUNT(DISTINCT(con.cnta_id)) ");
			consulta.append("      ELSE 0 ");
			consulta.append("    END) quantidadeAtraso31A60Dias, ");
			consulta.append("    ( ");
			consulta.append("    CASE ");
			consulta.append("      WHEN con.cnta_dtvencimentoconta                                                                 < CURRENT_TIMESTAMP ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 61 ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) <= 90 ");
			consulta.append("      THEN COUNT(DISTINCT(con.cnta_id)) ");
			consulta.append("      ELSE 0 ");
			consulta.append("    END) AS quantidadeAtraso61A90Dias, ");
			consulta.append("    ( ");
			consulta.append("    CASE ");
			consulta.append("      WHEN con.cnta_dtvencimentoconta                                                                < CURRENT_TIMESTAMP ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 91 ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) <= 120 ");
			consulta.append("      THEN COUNT(DISTINCT(con.cnta_id)) ");
			consulta.append("      ELSE 0 ");
			consulta.append("    END)                         AS quantidadeAtraso91A120Dias, ");
			consulta.append("    ( ");
			consulta.append("    CASE ");
			consulta.append("      WHEN con.cnta_dtvencimentoconta                                                                < CURRENT_TIMESTAMP ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 121 ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) <= 150 ");
			consulta.append("      THEN COUNT(DISTINCT(con.cnta_id)) ");
			consulta.append("      ELSE 0 ");
			consulta.append("    END)                         AS quantidadeAtraso121A150Dias, ");
			consulta.append("     ( ");
			consulta.append("    CASE ");
			consulta.append("      WHEN con.cnta_dtvencimentoconta                                                                < CURRENT_TIMESTAMP ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 151 ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) <= 180 ");
			consulta.append("      THEN COUNT(DISTINCT(con.cnta_id)) ");
			consulta.append("      ELSE 0 ");
			consulta.append("    END)                        AS quantidadeAtraso151A180Dias, ");
			consulta.append("     ( ");
			consulta.append("    CASE ");
			consulta.append("      WHEN con.cnta_dtvencimentoconta                                                                < CURRENT_TIMESTAMP ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 181 ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) <= 1825 ");
			consulta.append("      THEN COUNT(DISTINCT(con.cnta_id)) ");
			consulta.append("      ELSE 0 ");
			consulta.append("    END)                        AS quantidadeAtraso181DiasA5Anos, ");
			consulta.append("     ( ");
			consulta.append("    CASE ");
			consulta.append("      WHEN con.cnta_dtvencimentoconta                                                                < CURRENT_TIMESTAMP ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) >= 1826 ");
			consulta.append("      AND (to_date(TO_CHAR(CURRENT_TIMESTAMP, 'dd/mm/yy'), 'dd/mm/yy') - con.cnta_dtvencimentoconta) <= 3650 ");
			consulta.append("      THEN COUNT(DISTINCT(con.cnta_id)) ");
			consulta.append("      ELSE 0 ");
			consulta.append("    END)                        AS quantidadeAtrasoDe5A10Anos, ");
			consulta.append("    COUNT(DISTINCT(con.cnta_id)) AS quantidadeTotal ");
			consulta.append("  FROM conta con ");
			consulta.append("  INNER JOIN imovel imo ON imo.imov_id = con.imov_id ");
			consulta.append("  INNER JOIN localidade loc ");
			consulta.append("  ON loc.loca_id = imo.loca_id ");
			consulta.append("  INNER JOIN setor_comercial sca ");
			consulta.append("  ON sca.stcm_id                       = imo.stcm_id ");
			consulta.append("  INNER JOIN quadra qdr ");
			consulta.append("  ON qdr.qdra_id                       = imo.qdra_id ");
			consulta.append("  WHERE con.cnta_dtvencimentoconta < current_date ");
			consulta.append("  AND con.dcst_idatual                IN (" + DebitoCreditoSituacao.NORMAL.toString() + ","
							+ DebitoCreditoSituacao.RETIFICADA.toString() + "," + DebitoCreditoSituacao.INCLUIDA.toString() + ") ");
			consulta.append("  AND exists (select 1 from imovel_subcategoria isc where isc.imov_id = imo.imov_id and ");
			consulta.append("    isc.imsb_qteconomia = (select max(isc2.imsb_qteconomia) from imovel_subcategoria isc2 where isc2.imov_id = isc.imov_id) ");

			if(apenasPublicos){

				consulta.append("    and isc.catg_id = 4 ");
			}else{

				consulta.append("    and isc.catg_id <> 4 ");
			}

			consulta.append("  ) ");
			consulta.append("  GROUP BY imo.imov_id, ");
			consulta.append("    loc.loca_id, ");
			consulta.append("    loc.loca_nmlocalidade, ");
			consulta.append("    sca.stcm_cdsetorcomercial, ");
			consulta.append("    imo.qdra_nnquadra, ");
			consulta.append("    imo.imov_nnlote, ");
			consulta.append("    imo.imov_nnsublote, ");
			consulta.append("    con.cnta_dtvencimentoconta) ");
			consulta.append("    ");
			consulta.append("  GROUP BY ");
			consulta.append("  matricula, ");
			consulta.append("  idLocalidade, ");
			consulta.append("  nomeLocalidade, ");
			consulta.append("  codigoSetor, ");
			consulta.append("  numeroQuadra, ");
			consulta.append("  lote, ");
			consulta.append("  sublote, ");
			consulta.append("  situacaoCobranca, ");
			consulta.append("  usuarioPossuiCPFCNPJ ");
			consulta.append("  ");
			consulta.append("  ORDER BY ");
			consulta.append("  idLocalidade, ");
			consulta.append("  codigoSetor, ");
			consulta.append("  numeroQuadra, ");
			consulta.append("  lote, ");
			consulta.append("  sublote, ");
			consulta.append("  matricula ");

			retorno = session.createSQLQuery(consulta.toString()).addScalar("inscricao", Hibernate.STRING)
							.addScalar("idLocalidade", Hibernate.INTEGER).addScalar("nomeLocalidade", Hibernate.STRING)
							.addScalar("matricula", Hibernate.INTEGER).addScalar("situacaoCobranca", Hibernate.STRING)
							.addScalar("usuarioPossuiCPFCNPJ", Hibernate.STRING).addScalar("valAtrasoAte30Dias", Hibernate.BIG_DECIMAL)
							.addScalar("valAtraso31A60Dias", Hibernate.BIG_DECIMAL).addScalar("valAtraso61A90Dias", Hibernate.BIG_DECIMAL)
							.addScalar("valAtraso91A120Dias", Hibernate.BIG_DECIMAL)
							.addScalar("valAtraso121A150Dias", Hibernate.BIG_DECIMAL)
							.addScalar("valAtraso151A180Dias", Hibernate.BIG_DECIMAL)
							.addScalar("valAtraso181DiasA5Anos", Hibernate.BIG_DECIMAL)
							.addScalar("valAtrasoDe5A10Anos", Hibernate.BIG_DECIMAL).addScalar("valTotalContas", Hibernate.BIG_DECIMAL)
							.addScalar("qtdAtrasoAte30Dias", Hibernate.INTEGER).addScalar("qtdAtraso31A60Dias", Hibernate.INTEGER)
							.addScalar("qtdAtraso61A90Dias", Hibernate.INTEGER).addScalar("qtdAtraso91A120Dias", Hibernate.INTEGER)
							.addScalar("qtdAtraso121A150Dias", Hibernate.INTEGER).addScalar("qtdAtraso151A180Dias", Hibernate.INTEGER)
							.addScalar("qtdAtraso181DiasA5Anos", Hibernate.INTEGER).addScalar("qtdAtrasoDe5A10Anos", Hibernate.INTEGER)
							.addScalar("qtdTotalContas", Hibernate.INTEGER).list();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * @author Anderson Italo
	 * @date 11/02/2014
	 */
	public Collection<Conta> pesquisarContasEmAtrasoPorImovel(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection<Conta> retorno = null;
		StringBuilder consulta = new StringBuilder();

		try{

			consulta.append("  SELECT con FROM Conta con ");
			consulta.append("  WHERE con.imovel.id = :idImovel ");
			consulta.append("  AND con.dataVencimentoConta < :dataCorrente ");
			consulta.append("  AND con.debitoCreditoSituacaoAtual.id IN (" + DebitoCreditoSituacao.NORMAL.toString() + ","
							+ DebitoCreditoSituacao.RETIFICADA.toString() + "," + DebitoCreditoSituacao.INCLUIDA.toString() + ") ");

			retorno = session.createQuery(consulta.toString()).setInteger("idImovel", idImovel).setDate("dataCorrente", new Date()).list();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}
}
