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

package gcom.atendimentopublico;

import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ordemservico.OrdemServicoProgramacao;
import gcom.atendimentopublico.ordemservico.OsReferidaRetornoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipoPrioridade;
import gcom.atendimentopublico.ordemservico.bean.ObterValorDebitoHelper;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.imovel.Imovel;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.micromedicao.hidrometro.HidrometroSituacao;
import gcom.util.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.*;
import org.hibernate.criterion.Expression;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 */
public class RepositorioAtendimentoPublicoHBM
				implements IRepositorioAtendimentoPublico {

	private static IRepositorioAtendimentoPublico instancia;

	/**
	 * Construtor da classe RepositorioMicromedicaoHBM
	 */
	private RepositorioAtendimentoPublicoHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static IRepositorioAtendimentoPublico getInstancia(){

		if(instancia == null){
			instancia = new RepositorioAtendimentoPublicoHBM();
		}
		return instancia;
	}

	/**
	 * [UC-0355] - Efetuar Corte de Ligaçã de Àgua [SB001] Atualizar Ligação -
	 * (corte de ligação de água) Água- os campos LAGU_DTCORTE e
	 * LAGU_NNSELOCORTE e LAGU_ TMULTIMAALTERACAO
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioExceptions
	 *                Descrição da exceção
	 * @author Leandro Cavalcanti
	 * @date 10/07/2006
	 * @param imovel
	 * @param idLigacaoAguaSituacao
	 * @throws ErroRepositorioException
	 */
	public void atualizarLigacaoAgua(Integer idImovel, Integer idLigacaoAguaSituacao, Integer numeroSeloCorte)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String update;

		try{

			// Atualizar os campos LAST_ID, LAGU_DTCORTE, LAGU_NNSELOCORTE e
			// LAGU_ TMULTIMAALTERACAO
			update = "update gcom.atendimentopublico.ligacaoagua.LigacaoAgua set " + "lagu_id = :idligacaoAgua ,"
							+ "lagu_dtcorte = :dataExecOrdServico, " + "lagu_nnselocorte = :numeroSeloCorte, "
							+ "lagu_tmultimaalteracao = :datahoracorrente " + "where lagu_id = :imovelId";

			session.createQuery(update).setInteger("idligacaoAgua", idLigacaoAguaSituacao.intValue()).setDate("dataExecOrdServico",
							new Date()).setInteger("numeroSeloCorte", numeroSeloCorte.intValue()).setInteger("imovelId",
							idImovel.intValue()).setDate("datahoracorrente", new Date()).executeUpdate();

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
			// session.close();
		}

	}

	/**
	 * [UC-0355] - Efetuar Corte de Ligaçã de Àgua [SB001] Atualizar Hidrometro -
	 * (corte de ligação de água) Atualizar os campos hidi_nnleituracorte e
	 * hidi_tmultimaalteracao de HidrometroInstalacaoHistorico
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioExceptions
	 *                Descrição da exceção
	 * @author Leandro Cavalcanti
	 * @date 10/07/2006
	 * @param imovel
	 * @param idLigacaoAguaSituacao
	 * @throws ErroRepositorioException
	 */
	public void atualizarHidrometroLIgacaoAgua(Integer imovelId, Integer numeroLeituraCorte) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String update;

		try{

			update = "update gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico set "
							+ "hidi_nnleituracorte = :numCorte, hidi_tmultimaalteracao = :datahoracorrente " + "where imov_id = :imovelId";

			session.createQuery(update).setInteger("numCorte", numeroLeituraCorte.intValue()).setInteger("imovelId", imovelId.intValue())
							.setDate("datahoracorrente", new Date()).executeUpdate();

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
			// session.close();
		}

	}

	/**
	 * [UC-0362] - Efetuar Instalação de Hidrômetro [SB002] Atualizar Ligação de
	 * Água Atualizar os campos hidi_id e lagu_tmultimaalteracao de LigacaoAgua
	 * 
	 * @exception ErroRepositorioExceptions
	 *                Descrição da exceção
	 * @author Ana Maria
	 * @date 13/07/2006
	 * @param idLigacaoAgua
	 * @param idHidrometroInstalacaoHistorico
	 * @throws ErroRepositorioException
	 */
	public void atualizarHidrometroInstalacaoHistoricoLigacaoAgua(Integer idLigacaoAgua, Integer idHidrometroInstalacaoHistorico)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String update = null;

		try{
			if(idHidrometroInstalacaoHistorico != null && !idHidrometroInstalacaoHistorico.equals("")){
				update = "update gcom.atendimentopublico.ligacaoagua.LigacaoAgua set "
								+ "hidi_id = :idHidrometroInstalacaoHistorico, lagu_tmultimaalteracao = :datahoracorrente "
								+ "where lagu_id = :idLigacaoAgua";

				session.createQuery(update).setInteger("idHidrometroInstalacaoHistorico", idHidrometroInstalacaoHistorico.intValue())
								.setTimestamp("datahoracorrente", new Date()).setInteger("idLigacaoAgua", idLigacaoAgua.intValue())
								.executeUpdate();
			}else{
				update = "update gcom.atendimentopublico.ligacaoagua.LigacaoAgua set "
								+ "hidi_id = null, lagu_tmultimaalteracao = :datahoracorrente " + "where lagu_id = :idLigacaoAgua";

				session.createQuery(update).setTimestamp("datahoracorrente", new Date()).setInteger("idLigacaoAgua",
								idLigacaoAgua.intValue()).executeUpdate();

			}

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * [UC-0362] - Efetuar Instalação de Hidrômetro [SB002] Atualizar Imóvel
	 * Atualizar os campos hidi_id e imov_tmultimaalteracao de Imovel
	 * 
	 * @exception ErroRepositorioExceptions
	 *                Descrição da exceção
	 * @author Ana Maria
	 * @date 13/07/2006
	 * @param idImovel
	 * @param idHidrometroInstalacaoHistorico
	 * @throws ErroRepositorioException
	 */
	public void atualizarHidrometroIntalacaoHistoricoImovel(Integer idImovel, Integer idHidrometroInstalacaoHistorico)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String update = null;

		try{
			if(idHidrometroInstalacaoHistorico != null && !idHidrometroInstalacaoHistorico.equals("")){
				update = "update gcom.cadastro.imovel.Imovel set "
								+ "hidi_id = :idHidrometroInstalacaoHistorico, imov_tmultimaalteracao = :datahoracorrente "
								+ "where imov_id = :idImovel";

				session.createQuery(update).setInteger("idHidrometroInstalacaoHistorico", idHidrometroInstalacaoHistorico.intValue())
								.setTimestamp("datahoracorrente", new Date()).setInteger("idImovel", idImovel.intValue()).executeUpdate();
			}else{
				update = "update gcom.cadastro.imovel.Imovel set " + "hidi_id = null, imov_tmultimaalteracao = :datahoracorrente "
								+ "where imov_id = :idImovel";

				session.createQuery(update).setTimestamp("datahoracorrente", new Date()).setInteger("idImovel", idImovel.intValue())
								.executeUpdate();
			}

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * [UC-0362] - Efetuar Instalação de Hidrômetro [SB003] Atualizar Hidrômetro
	 * Atualizar o campo hisi_id
	 * 
	 * @exception ErroRepositorioExceptions
	 *                Descrição da exceção
	 * @author Ana Maria
	 * @date 17/07/2006
	 * @throws ErroRepositorioException
	 */
	public void atualizarSituacaoHidrometro(Integer idHidrometro, Integer situacaoHidrometro) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String update = null;

		try{

			update = "update gcom.micromedicao.hidrometro.Hidrometro set "
							+ "hist_id = :idSituacaoHidrometro, hidr_tmultimaalteracao = :datahoracorrente "
							+ "where hidr_id = :numeroHidrometro";

			session.createQuery(update).setInteger("idSituacaoHidrometro", situacaoHidrometro).setTimestamp("datahoracorrente", new Date())
							.setInteger("numeroHidrometro", idHidrometro.intValue()).executeUpdate();

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * [UC-0362] - Efetuar Instalação de Hidrômetro [SB003] Atualizar Hidrômetro
	 * Atualizar o campo hisi_id
	 * 
	 * @exception ErroRepositorioExceptions
	 *                Descrição da exceção
	 * @author Ana Maria
	 * @date 17/07/2006
	 * @throws ErroRepositorioException
	 */
	public void atualizarLocalArmazanagemHidrometro(Integer idHidrometro, Integer localArmazanagemHidrometro)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String update = null;

		try{

			update = "update gcom.micromedicao.hidrometro.Hidrometro set " + "hila_id = :localArmazanagemHidrometro "
							+ "where hidr_id = :numeroHidrometro";

			session.createQuery(update).setInteger("localArmazanagemHidrometro", localArmazanagemHidrometro).setInteger("numeroHidrometro",
							idHidrometro.intValue()).executeUpdate();

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Substituicao de hidrometro
	 */
	public void atualizarSubstituicaoHidrometroInstalacoHistorico(HidrometroInstalacaoHistorico hidrometroSubstituicaoHistorico)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{

			if(hidrometroSubstituicaoHistorico.getNumeroLeituraRetirada() != null){

				String sql = "update gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico "
								+ "set hidi_dtretiradahidrometro = :dataRetirada "
								+ ", hidi_nnleituraretiradahidromet = :numeroLeituraRetirada " + ", hidi_tmultimaalteracao = :data "
								+ ", hidi_icinstalacaosubstituicao =:indicadorSubstituicao" + " where hidi_id = :id ";

				session.createQuery(sql).setDate("dataRetirada", hidrometroSubstituicaoHistorico.getDataRetirada()).setInteger(
								"numeroLeituraRetirada", hidrometroSubstituicaoHistorico.getNumeroLeituraRetirada()).setTimestamp("data",
								new Date()).setInteger("id", hidrometroSubstituicaoHistorico.getId()).setShort("indicadorSubstituicao",
								new Short("2")).executeUpdate();
			}else{

				String sql = "update gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico "
								+ "set hidi_dtretiradahidrometro = :dataRetirada " + ", hidi_tmultimaalteracao = :data "
								+ ", hidi_icinstalacaosubstituicao =:indicadorSubstituicao" + " where hidi_id = :id ";

				session.createQuery(sql).setDate("dataRetirada", hidrometroSubstituicaoHistorico.getDataRetirada()).setTimestamp("data",
								new Date()).setInteger("id", hidrometroSubstituicaoHistorico.getId()).setShort("indicadorSubstituicao",
								new Short("2")).executeUpdate();
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * [UC-0362] - Efetuar Instalação de Hidrômetro [SB003] Atualizar Hidrômetro
	 * Atualizar o campo hisi_id
	 * 
	 * @exception ErroRepositorioExceptions
	 *                Descrição da exceção
	 * @author Ana Maria
	 * @date 17/07/2006
	 * @throws ErroRepositorioException
	 */
	public void atualizarHidrometroInstalacoHistorico(HidrometroInstalacaoHistorico hidrometroSubstituicaoHistorico)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{

			if(hidrometroSubstituicaoHistorico.getNumeroLeituraRetirada() != null){

				String sql = "update gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico "
								+ "set hidi_dtretiradahidrometro = :dataRetirada "
								+ ", hidi_nnleituraretiradahidromet = :numeroLeituraRetirada "
								+ ", funcionarioInstalacaoHidrometro.id = "
								+ (hidrometroSubstituicaoHistorico.getFuncionarioInstalacaoHidrometro() != null ? hidrometroSubstituicaoHistorico
												.getFuncionarioInstalacaoHidrometro().getId().toString()
												: "null")
								+ ", funcionarioRetiradaHidrometro.id = "
								+ (hidrometroSubstituicaoHistorico.getFuncionarioRetiradaHidrometro() != null ? hidrometroSubstituicaoHistorico
												.getFuncionarioRetiradaHidrometro().getId().toString()
												: "null") + ", hidi_tmultimaalteracao = :data " + "where hidi_id = :id ";

				session.createQuery(sql).setDate("dataRetirada", hidrometroSubstituicaoHistorico.getDataRetirada()).setInteger(
								"numeroLeituraRetirada", hidrometroSubstituicaoHistorico.getNumeroLeituraRetirada()).setTimestamp("data",
								new Date()).setInteger("id", hidrometroSubstituicaoHistorico.getId()).executeUpdate();
			}else{

				String sql = "update gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico "
								+ "set hidi_dtretiradahidrometro = :dataRetirada " + ", hidi_tmultimaalteracao = :data "
								+ "where hidi_id = :id ";

				session.createQuery(sql).setDate("dataRetirada", hidrometroSubstituicaoHistorico.getDataRetirada()).setTimestamp("data",
								new Date()).setInteger("id", hidrometroSubstituicaoHistorico.getId()).executeUpdate();
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * [UC0396] - Inserir Tipo de retorno da OS Referida
	 * [FS0005] Validar indicador de deferimento
	 * 
	 * @author lms
	 * @date 31/07/2006
	 * @throws ErroRepositorioException
	 */
	public int consultarTotalIndicadorDeferimentoAtivoPorServicoTipoReferencia(OsReferidaRetornoTipo osReferidaRetornoTipo)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String select = null;
		try{
			select = "select count(*) from gcom.atendimentopublico.ordemservico.OsReferidaRetornoTipo o where "
							+ "o.indicadorDeferimento = " + ConstantesSistema.INDICADOR_USO_ATIVO + " and " + "o.indicadorUso = "
							+ ConstantesSistema.INDICADOR_USO_ATIVO + " and " + "o.servicoTipoReferencia.id = :idServicoTipoReferencia";
			return ((Number) session.createQuery(select).setInteger("idServicoTipoReferencia",
							osReferidaRetornoTipo.getServicoTipoReferencia().getId()).uniqueResult()).intValue();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0463] Atualizar Consumo Mínimo da Ligação de Água
	 * 
	 * @author Leonardo Regis
	 * @date 30/08/2006
	 * @param ligacaoAgua
	 * @exception ErroRepositorioExceptions
	 */
	public void atualizarConsumoMinimoLigacaoAgua(LigacaoAgua ligacaoAgua) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String update;
		try{
			update = "update LigacaoAgua set " + "numeroConsumoMinimoAgua = :consumoMinimo, " + "ultimaAlteracao = :dataCorrente "
							+ "where id = :ligacaoAguaId";

			session.createQuery(update).setInteger("consumoMinimo", ligacaoAgua.getNumeroConsumoMinimoAgua()).setTimestamp("dataCorrente",
							ligacaoAgua.getUltimaAlteracao()).setInteger("ligacaoAguaId", ligacaoAgua.getId()).executeUpdate();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0475] Obter Valor do Débito
	 * Verificar existência de hidrômetro na ligação de água.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param imovelId
	 * @return existencia de hidrometro ou não
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaHidrometroEmLigacaoAgua(Integer imovelId) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;
		boolean retorno = false;
		Collection<HidrometroInstalacaoHistorico> retornoConsulta = new ArrayList();
		try{
			consulta = "SELECT la.hidrometroInstalacaoHistorico " + "FROM LigacaoAgua la " + "where la.id = :imovelId";
			retornoConsulta = (Collection<HidrometroInstalacaoHistorico>) session.createQuery(consulta).setInteger("imovelId", imovelId)
							.list();

			if(retornoConsulta != null && !retornoConsulta.isEmpty()){
				retorno = true;
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
	 * [UC0475] Obter Valor do Débito
	 * Verificar existência de hidrômetro no imóvel.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param imovelId
	 * @return existencia de hidrometro ou não
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaHidrometroEmImovel(Integer imovelId) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;
		boolean retorno = false;
		Collection<HidrometroInstalacaoHistorico> retornoConsulta = new ArrayList();
		try{
			consulta = "SELECT i.hidrometroInstalacaoHistorico " + "FROM Imovel i " + "where i.id = :imovelId";
			retornoConsulta = (Collection<HidrometroInstalacaoHistorico>) session.createQuery(consulta).setInteger("imovelId", imovelId)
							.list();

			if(retornoConsulta != null && !retornoConsulta.isEmpty()){
				retorno = true;
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
	 * [UC0475] Obter Valor do Débito
	 * Obter Capacidade de Hidrômetro pela Ligação de Água.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param imovelId
	 * @return existencia de hidrometro ou não
	 * @throws ErroRepositorioException
	 */
	public HidrometroCapacidade obterHidrometroCapacidadeEmLigacaoAgua(Integer imovelId) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;
		HidrometroCapacidade retornoConsulta = null;
		try{
			consulta = "SELECT h.hidrometroCapacidade " + "FROM LigacaoAgua la " + "INNER JOIN la.hidrometroInstalacaoHistorico hih "
							+ "INNER JOIN hih.hidrometro h " + "INNER JOIN h.hidrometroCapacidade hc " + "where la.id = :imovelId";

			retornoConsulta = (HidrometroCapacidade) session.createQuery(consulta).setInteger("imovelId", imovelId).setMaxResults(1)
							.uniqueResult();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retornoConsulta;
	}

	/**
	 * [UC0475] Obter Valor do Débito
	 * Obter Capacidade de Hidrômetro pelo Imóvel.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param imovelId
	 * @return existencia de hidrometro ou não
	 * @throws ErroRepositorioException
	 */
	public HidrometroCapacidade obterHidrometroCapacidadeEmImovel(Integer imovelId) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;
		HidrometroCapacidade retornoConsulta = null;
		try{
			consulta = "SELECT h.hidrometroCapacidade " + "FROM Imovel i " + "INNER JOIN i.hidrometroInstalacaoHistorico hih "
							+ "INNER JOIN hih.hidrometro h " + "INNER JOIN h.hidrometroCapacidade hc " + "where i.id = :imovelId";

			retornoConsulta = (HidrometroCapacidade) session.createQuery(consulta).setInteger("imovelId", imovelId).setMaxResults(1)
							.uniqueResult();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retornoConsulta;
	}

	/**
	 * [UC0475] Obter Valor do Débito
	 * Obter Valor do Debito pelos parâmtros passados.
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param obterValorDebitoHelper
	 * @return o valor do débito
	 * @throws ErroRepositorioException
	 */
	public BigDecimal obterValorDebito(ObterValorDebitoHelper params) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;
		BigDecimal retornoConsulta = null;
		Query query = null;
		try{
			consulta = "SELECT scv.valor " + "FROM ServicoCobrancaValor scv " + "where scv.servicoTipo.id = :servicoTipoId "
							+ "and scv.imovelPerfil.id = :imovelPerfilId ";
			if(params.getHidrometroCapacidade() != null){
				consulta += "and scv.hidrometroCapacidade.id = :hidrometroCapacidadeId ";
			}

			if(params.getSituacaoMedicao() != null){
				consulta += "and scv.indicadorMedido = :situacaoMedicao ";
			}

			query = session.createQuery(consulta).setInteger("imovelPerfilId", params.getImovelPerfil().getId()).setInteger(
							"servicoTipoId", params.getServicoTipo().getId());
			if(params.getHidrometroCapacidade() != null){
				query.setInteger("hidrometroCapacidadeId", params.getHidrometroCapacidade().getId());
			}
			if(params.getSituacaoMedicao() != null){
				query.setShort("situacaoMedicao", params.getSituacaoMedicao());
			}

			retornoConsulta = (BigDecimal) query.setMaxResults(1).uniqueResult();

			if(retornoConsulta == null){

				consulta = "SELECT scv.valor " + "FROM ServicoCobrancaValor scv " + "where scv.servicoTipo.id = :servicoTipoId "
								+ "and scv.imovelPerfil is null ";

				if(params.getHidrometroCapacidade() != null){
					consulta += "and scv.hidrometroCapacidade.id = :hidrometroCapacidadeId ";
				}
				if(params.getSituacaoMedicao() != null){
					consulta += "and scv.indicadorMedido = :situacaoMedicao ";
				}

				query = session.createQuery(consulta).setInteger("servicoTipoId", params.getServicoTipo().getId());

				if(params.getHidrometroCapacidade() != null){
					query.setInteger("hidrometroCapacidadeId", params.getHidrometroCapacidade().getId());
				}

				if(params.getSituacaoMedicao() != null){
					query.setShort("situacaoMedicao", params.getSituacaoMedicao());
				}

				retornoConsulta = (BigDecimal) query.setMaxResults(1).uniqueResult();

				if(retornoConsulta == null){

					consulta = "SELECT st.valor " + "FROM ServicoTipo st " + "where st.id = :servicoTipoId ";
					retornoConsulta = (BigDecimal) session.createQuery(consulta).setInteger("servicoTipoId",
									params.getServicoTipo().getId()).setMaxResults(1).uniqueResult();

				}

			}
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retornoConsulta;
	}

	/**
	 * Método que retorna o número do hidrômetro da ligação de água
	 * 
	 * @author Ana Maria
	 * @date 12/09/2006
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */

	public String pesquisarNumeroHidrometroLigacaoAgua(Integer idLigacaoAgua) throws ErroRepositorioException{

		String retorno = "";

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = " select hidr.numero" + " from LigacaoAgua lagu" + " inner join lagu.hidrometroInstalacaoHistorico hidi"
							+ " inner join hidi.hidrometro hidr" + " where lagu.id = :idLigacaoAgua";

			retorno = (String) session.createQuery(consulta).setInteger("idLigacaoAgua", idLigacaoAgua).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Método que retorna o tipo da ligação de água, a data do corte da ligação
	 * de água e a data da Religação
	 * 
	 * @author Ana Maria, Raphael Rossiter
	 * @date 18/08/2006, 03/01/2008
	 * @param idLigacaoAgua
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosLigacaoAgua(Integer idLigacaoAgua) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select lagu.corteTipo.id, lagu.dataCorteAdministrativo, lagu.dataReligacao,"
							+ " lagu.dataCorte, lagu.dataSupressao" + " from LigacaoAgua lagu" + " where lagu.id = :idLigacaoAgua";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idLigacaoAgua", idLigacaoAgua).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Consulta os dados das ordens de serviço para a geração do relatório
	 * 
	 * @author Rafael Corrêa, Vivianne Sousa
	 * @created 07/10/2006, 09/03/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarOrdemServicoProgramacaoRelatorio(Integer idEquipe, Date dataRoteiro) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			if(dataRoteiro != null){

				consulta = "SELECT ospg.nnSequencialProgramacao, ra.id, " + "orse.id, svtp.id, orse.observacao "
								+ "FROM OrdemServicoProgramacao ospg " + "INNER JOIN ospg.ordemServico orse "
								+ "INNER JOIN ospg.equipe equipe " + "INNER JOIN ospg.programacaoRoteiro progRot "
								+ "INNER JOIN orse.servicoTipo svtp " + "LEFT JOIN orse.registroAtendimento ra "
								+ "WHERE equipe.id = :idEquipe " + "AND progRot.dataRoteiro = :dataRoteiro "
								+ "AND orse.indicadorProgramada = :programada " + "AND (ospg.indicadorAtivo = :indicadorAtivo "
								+ "OR (ospg.indicadorAtivo = :indicadorAtivoNao AND ospg.situacaoFechamento = :situacaoFechamento )) "
								+ "ORDER BY ospg.nnSequencialProgramacao ";

				retorno = session.createQuery(consulta).setInteger("idEquipe", idEquipe).setTimestamp("dataRoteiro", dataRoteiro).setShort(
								"indicadorAtivo", OrdemServicoProgramacao.INDICADOR_ATIVO).setShort("indicadorAtivoNao",
								OrdemServicoProgramacao.INDICADOR_ATIVO_NAO).setShort("situacaoFechamento",
								OrdemServicoProgramacao.SITUACAO_FECHAMENTO).setShort("programada", ConstantesSistema.INDICADOR_USO_ATIVO)
								.list();

			}else{

				consulta = "select ospg.nnSequencialProgramacao, ra.id, " + "orse.id, svtp.id, orse.observacao "
								+ "from OrdemServicoProgramacao ospg " + "inner join ospg.ordemServico orse "
								+ "inner join ospg.equipe equipe " + "left join orse.registroAtendimento ra "
								+ "inner join orse.servicoTipo svtp " + "where equipe.id = :idEquipe"
								+ "AND orse.indicadorProgramada = :programada " + "AND (ospg.indicadorAtivo = :indicadorAtivo "
								+ "OR (ospg.indicadorAtivo = :indicadorAtivoNao AND ospg.situacaoFechamento = :situacaoFechamento )) "
								+ "ORDER BY ospg.nnSequencialProgramacao ";

				retorno = session.createQuery(consulta).setInteger("idEquipe", idEquipe).setShort("indicadorAtivo",
								OrdemServicoProgramacao.INDICADOR_ATIVO).setShort("indicadorAtivoNao",
								OrdemServicoProgramacao.INDICADOR_ATIVO_NAO).setShort("situacaoFechamento",
								OrdemServicoProgramacao.SITUACAO_FECHAMENTO).setShort("programada", ConstantesSistema.INDICADOR_USO_ATIVO)
								.list();
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
	 * [UC0404] Manter Especificação da Situação do Imovel
	 * Este caso de uso remove a especificação e os critério
	 * [SB0002] Remover Especificação da situacao
	 * 
	 * @author Rafael Pinto
	 * @created 08/11/2006
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void removerEspecificacaoSituacaoImovelCriterio(String[] idsEspecificacaoSituacaoImovel) throws ErroRepositorioException{

		String remocao = null;

		Session session = HibernateUtil.getSession();

		try{

			remocao = "delete EspecificacaoImovSitCriterio " + "where esim_id IN(:ids)";

			session.createQuery(remocao).setParameterList("ids", idsEspecificacaoSituacaoImovel).executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Pesquisa todos os ids das situações de ligação de água.
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarTodosIdsSituacaoLigacaoAgua() throws ErroRepositorioException{

		Collection<Integer> retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select last.id from LigacaoAguaSituacao last";

			retorno = session.createQuery(consulta).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Pesquisa todos os ids das situações de ligação de esgoto.
	 * [UC0564 - Gerar Resumo das Instalações de Hidrômetros]
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarTodosIdsSituacaoLigacaoEsgoto() throws ErroRepositorioException{

		Collection<Integer> retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select lest.id from LigacaoEsgotoSituacao lest";

			retorno = session.createQuery(consulta).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Este cso de uso permite efetuar a ligação de água e eventualmente
	 * a instalação de hidrômetro, sem informação de RA sendo chamado direto pelo menu.
	 * [UC0579] - Efetuar Ligação de Água com Intalação de Hidrômetro
	 * 
	 * @author Flávio Leonardo
	 * @date 25/04/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarEfetuarLigacaoAguaHidrometroSemRA(Integer idImovel) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		try{
			String hql = "select imovel.id," // 0
							+ " cliente.nome,"// 1
							+ " cliente.cpf,"// 2
							+ " cliente.cnpj,"// 3
							+ " ligacaoAguaSituacao.descricao,"// 4
							+ " ligacaoEsgotoSituacao.descricao,"// 5
							+ " imovel.indicadorExclusao,"// 6
							+ " quadra.indicadorRedeAgua,"// 7
							+ " ligacaoAguaSituacao.id,"// 8
							+ " ligacaoEsgotoSituacao.id"// 9
							+ " from ClienteImovel clienteImovel"
							+ " inner join clienteImovel.imovel imovel"
							+ " inner join clienteImovel.cliente cliente"
							+ " inner join imovel.ligacaoAguaSituacao ligacaoAguaSituacao"
							+ " inner join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao"
							+ " inner join imovel.quadra quadra"
							+ " where imovel.id = :idImovel"
							+ " and clienteImovel.clienteRelacaoTipo.id = :relacaoTipo"
							+ " and clienteImovel.dataFimRelacao is null";

			retorno = session.createQuery(hql).setInteger("idImovel", idImovel).setInteger("relacaoTipo",
							new Integer(ClienteRelacaoTipo.USUARIO)).setMaxResults(1).list();

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
	 * [UC0XXX] Gerar Contrato de Prestação de Serviço
	 * 
	 * @author Rafael Corrêa
	 * @date 03/05/2007
	 * @throws ErroRepositorioException
	 */
	public Collection obterDadosContratoPrestacaoServico(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;
		Collection retorno = null;
		try{

			consulta = "SELECT "
							+
							// 0
							"imov.imov_id as idImovel, "
							+
							// 1
							"clie.clie_id as idCliente, "
							+
							// 2
							"clieResponsavel.clie_id as idResponsavel, "
							+
							// 3
							"unidNeg.uneg_nmabreviado as nomeUnidadeNegocio, "
							+
							// 4
							"imov.cstf_id as consumoTarifa, "
							+
							// 5
							"municipio.muni_nmmunicipio as nomeMunicipio "

							+ "FROM imovel imov "
							+ "INNER JOIN localidade loc "
							+ "on loc.loca_id = imov.loca_id "
							+ "LEFT OUTER JOIN bairro bairro "
							+ "on loc.bair_id = bairro.bair_id "
							+ "LEFT OUTER JOIN municipio municipio "
							+ "on bairro.muni_id = municipio.muni_id "
							+ "INNER JOIN unidade_negocio unidNeg "
							+ "on unidNeg.uneg_id = loc.uneg_id "
							+ "INNER JOIN cliente_imovel clieImov "
							+ "on clieImov.imov_id = imov.imov_id and clieImov.crtp_id = "
							+ ClienteRelacaoTipo.USUARIO.toString()
							+ "and clieImov.clim_dtrelacaofim is null "
							+ "INNER JOIN cliente clie "
							+ "on clie.clie_id = clieImov.clie_id "
							+ "LEFT OUTER JOIN cliente clieResponsavel "
							+ "on clieResponsavel.clie_id = unidNeg.clie_id "
							+ "LEFT JOIN profissao prof on clie.prof_id = prof.prof_id "
							+ "LEFT JOIN orgao_expedidor_rg oerg on clie.oerg_id = oerg.oerg_id "
							+ "LEFT JOIN unidade_federacao unfe on clie.unfe_id = unfe.unfe_id "
							+ "where imov.imov_id = :idImovel";

			retorno = (Collection) session.createSQLQuery(consulta).addScalar("idImovel", Hibernate.INTEGER)
							.addScalar("idCliente", Hibernate.INTEGER).addScalar("idResponsavel", Hibernate.INTEGER)
							.addScalar("nomeUnidadeNegocio", Hibernate.STRING).addScalar("consumoTarifa", Hibernate.INTEGER)
							.addScalar("nomeMunicipio", Hibernate.STRING).setInteger("idImovel", idImovel).list();

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0582] - Emitir Boletim de Cadastro
	 * Obtém os dados necessário da ligação de água, de esgoto e do hidrômetro
	 * instalado na ligação de água
	 * 
	 * @author Rafael Corrêa
	 * @date 17/05/2007
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosLigacaoAguaEsgoto(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;
		Object[] retorno = null;
		try{

			consulta = "SELECT lagu.lagd_id as diametroLigAgua, lagu.lagm_id as materialLigAgua, "
							+ " lesg.legd_id as diametroLigEsgoto, lesg.legm_id as materialLigEsgoto, "
							+ " hidrInstHist.hidi_nnleiturainstalacaohidrom as leituraInicial, hidr.hicp_id as capacidade, "
							+ " hidr.himc_id as marca, hidrInstHist.hili_id as localInstalacao, hidrInstHist.hipr_id as protecao, "
							+ " hidrInstHist.hidi_iccavalete as cavalete, lagu.lagu_dtligacaoagua as dtLigacaoAgua, " 
							+ " lagu.lagu_dtcorte as dtCorteAgua, lagu.lagu_dtsupressaoagua as dtSupressaoAgua, " 
							+ " lesg.lesg_dtligacao as dtLigacaoEsgoto, lagu.lagu_dtreligacaoagua as dtReligAgua, lagu.lagu_dtrestabelecimentoagua as dtRestabAgua, "
							+ " hidrInstHist.hidi_dtinstalacaohidrometro as dtInstHidrometro,  hidr.hidr_nnhidrometro as nuHidrometro, "
							+ " hidrInstHist.hidi_iccavalete as inCavalete, hipr.hipr_dshidrometroprotecao as dsHidProtecao "
							+ " FROM imovel imov "
							+ " LEFT OUTER JOIN ligacao_agua lagu on lagu.lagu_id = imov.imov_id "
							+ " LEFT OUTER JOIN hidrometro_instalacao_hist hidrInstHist on hidrInstHist.hidi_id = lagu.hidi_id "
							+ " LEFT OUTER JOIN hidrometro hidr on hidr.hidr_id = hidrInstHist.hidr_id "
							+ " LEFT OUTER JOIN hidrometro_protecao hipr on hipr.hipr_id = hidrInstHist.hipr_id "
							+ " LEFT OUTER JOIN ligacao_esgoto lesg on lesg.lesg_id = imov.imov_id " + " WHERE imov.imov_id = :idImovel";

			retorno = (Object[]) session.createSQLQuery(consulta).addScalar("diametroLigAgua", Hibernate.INTEGER).addScalar(
							"materialLigAgua", Hibernate.INTEGER).addScalar("diametroLigEsgoto", Hibernate.INTEGER).addScalar(
							"materialLigEsgoto", Hibernate.INTEGER).addScalar("leituraInicial", Hibernate.INTEGER).addScalar("capacidade",
							Hibernate.INTEGER).addScalar("marca", Hibernate.INTEGER).addScalar("localInstalacao", Hibernate.INTEGER)
							.addScalar("protecao", Hibernate.INTEGER).addScalar("cavalete", Hibernate.SHORT).addScalar("dtLigacaoAgua", 
							Hibernate.DATE).addScalar("dtCorteAgua", Hibernate.DATE).addScalar("dtSupressaoAgua", Hibernate.DATE).
							addScalar("dtLigacaoEsgoto", Hibernate.DATE).addScalar("dtReligAgua", Hibernate.DATE)
							.addScalar("dtRestabAgua", Hibernate.DATE).addScalar("dtInstHidrometro", Hibernate.DATE)
							.addScalar("nuHidrometro", Hibernate.STRING).addScalar("inCavalete", Hibernate.SHORT)
							.addScalar("dsHidProtecao", Hibernate.STRING).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public void atualizarImovelLigacaoAguaInstalacaoHidrometroSemRA(Integer idImovel, Integer idHidrometro) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{
			if(idImovel != null){
				String hql = "update gcom.cadastro.imovel.Imovel set " + "last_id = :situacao where imov_id = :idImovel";

				session.createQuery(hql).setInteger("situacao", LigacaoAguaSituacao.LIGADO).setInteger("idImovel", idImovel)
								.executeUpdate();
			}
			if(idHidrometro != null){
				String hql2 = "update gcom.micromedicao.hidrometro.Hidrometro set " + "hist_id = :situacao where hidr_id = :idHidrometro";

				session.createQuery(hql2).setInteger("situacao", HidrometroSituacao.INSTALADO).setInteger("idHidrometro", idHidrometro)
								.executeUpdate();
			}

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	// *********************************************************
	// ****************CONTRATO PESSOA JURIDICA*****************

	public Cliente pesquisaClienteContrato(Integer idCliente) throws ErroRepositorioException{

		Cliente retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "from Cliente cliente" + " inner join fetch cliente.profissao profissao" + " where cliente.id = :idCliente";

			retorno = (Cliente) session.createQuery(consulta).setInteger("idCliente", idCliente).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public ClienteImovel pesquisarDadosContratoJuridica(Integer idImovel) throws ErroRepositorioException{

		ClienteImovel retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "from ClienteImovel clienteImovel" + " inner join fetch clienteImovel.cliente cliente"
							+ " inner join fetch clienteImovel.imovel imovel" + " where imovel.id = " + idImovel
							+ " and clienteImovel.clienteRelacaoTipo.id = " + ClienteRelacaoTipo.USUARIO;

			retorno = (ClienteImovel) session.createQuery(consulta).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public String pesquisarMunicipio(Integer idImovel) throws ErroRepositorioException{

		String retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{
			consulta = "select municipio.nome from  Imovel imovel" + " inner join fetch imovel.localidade localidade"
							+ " inner join fetch localidade.logradouroBairro logradouro" + " inner join fetch logradouro.bairro bairro"
							+ " inner join fetch bairro.municipio municipio" + " where imovel.id = :idImovel";

			retorno = (String) session.createQuery(consulta).setInteger("idImovel", idImovel).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	// **********************************************************************************

	/**
	 * [UC0482]Emitir 2ª Via de Conta
	 *obter numero do hidrômetro na ligação de água.
	 * 
	 * @author Vivianne Sousa
	 * @date 11/09/2007
	 * @param imovelId
	 * @return existencia de hidrometro ou não
	 * @throws ErroRepositorioException
	 */
	public String obterNumeroHidrometroEmLigacaoAgua(Integer imovelId) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;
		String retornoConsulta = null;
		try{
			consulta = "SELECT hidr.numero " + "FROM LigacaoAgua la " + "inner join la.hidrometroInstalacaoHistorico hidi "
							+ "inner join hidi.hidrometro hidr " + "where la.id = :imovelId ";

			retornoConsulta = (String) session.createQuery(consulta).setInteger("imovelId", imovelId).uniqueResult();

		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retornoConsulta;
	}

	/**
	 * Método responsável por retornar um servicoTipoPrioridade a partir do seu id.
	 * Não importa se o indicadorUso é ativo ou inativo.
	 * 
	 * @author Virgínia Melo
	 * @param idServicoTipoPrioridade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ServicoTipoPrioridade pesquisarServicoTipoPrioridade(Integer idServicoTipoPrioridade) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		ServicoTipoPrioridade retorno = null;

		try{

			Criteria criteria = session.createCriteria(ServicoTipoPrioridade.class).add(Expression.eq("id", idServicoTipoPrioridade));

			List<ServicoTipoPrioridade> lista = criteria.list();
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
	 * [UC0738] Retorna as informações para o relatório de certidão negativa
	 * 
	 * @param imo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarRelatorioCertidaoNegativa(Imovel imo) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		String consulta;

		Collection retornoConsulta = null;

		try{

			consulta = " select \n"
							+
							// 0
							"   cli.nome, \n"
							+
							// 1
							"   imo.id, \n"
							+
							// 2
							"   loc.id, \n"
							+
							// 3
							"   sc.codigo, \n"
							+
							// 4
							"   qua.numeroQuadra, \n"
							+
							// 5
							"   imo.lote, \n"
							+
							// 6
							"   imo.subLote, \n"
							+
							// 7
							"   imo.quantidadeEconomias, \n"
							+
							// 8
							"   ip.descricao, \n"
							+
							// 9
							"   las.descricao, \n"
							+
							// 10
							"   les.descricao, \n"
							+
							// 11
							"   pt.descricao, \n"
							+
							// 12
							"   sp.nomeAbreviadoEmpresa, \n"
							+
							// 13
							"   sp.nomeEmpresa, \n"
							+
							// 14
							"   sp.cnpjEmpresa, \n"
							+
							// 15
							"   loc.descricao, \n"
							+
							// 16
							"   coalesce( ltp.descricao, '' ) || ' ' || coalesce( ltt.descricao, '' ) || ' ' || coalesce( log.nome, '' ) as logradouro, \n"
							+
							// 17
							"   sp.numeroImovel, \n"
							+
							// 18
							"   sp.complementoEndereco, \n"
							+
							// 19
							"   er.descricaoAbreviada, \n"
							+
							// 20
							"   bai.nome, \n"
							+
							// 21
							"   cep.codigo, \n"
							+
							// 22
							"   sp.nomeSiteEmpresa, \n"
							+
							// 23
							"   sp.numero0800Empresa, \n"
							+
							// 24
							"   sp.inscricaoEstadual, \n"
							+
							// 25
							"   imo.areaConstruida, \n"
							+
							// 26
							"   areaConstruidaFaixa, \n"
							+
							// 27
							"   hidr.numero \n " + " from \n" + "   ClienteImovel ci \n" + "   inner join ci.clienteRelacaoTipo crt \n"
							+ "   inner join ci.cliente cli \n" + "   inner join ci.imovel imo \n" + "   inner join imo.localidade loc \n"
							+ "   inner join imo.setorComercial sc \n" + "   inner join imo.quadra qua \n"
							+ "   inner join imo.imovelPerfil ip \n" + "   inner join imo.ligacaoAguaSituacao las \n"
							+ "   inner join imo.ligacaoEsgotoSituacao les  \n" + "   left join imo.pocoTipo pt,  \n"
							+ "   SistemaParametro sp \n" + "   left join sp.logradouro log \n" + "   left join log.logradouroTipo ltp \n"
							+ "   left join log.logradouroTitulo ltt \n" + "   left join sp.enderecoReferencia er \n"
							+ "   left join sp.bairro bai \n" + "   left join sp.cep cep \n"
							+ "   left join imo.areaConstruidaFaixa areaConstruidaFaixa \n" + "   left join imo.ligacaoAgua lagu \n"
							+ "   left join lagu.hidrometroInstalacaoHistorico hidInsHist " + "   left join hidInsHist.hidrometro hidr "
							+ " where  \n" + "   crt.id = 2 and \n" + "   ci.dataFimRelacao is null and \n " + "   imo.id = :idImovel \n";

			retornoConsulta = session.createQuery(consulta).setInteger("idImovel", imo.getId()).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retornoConsulta;
	}

	/**
	 * Pesquisa os dados necessários para a geração do relatório
	 * [UC0864] Gerar Certidão Negativa por Cliente
	 * 
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarRelatorioCertidaoNegativaCliente(Collection<Integer> idsClientes) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		String consulta;

		Collection<Object[]> retorno = null;

		try{

			consulta = " SELECT DISTINCT clie.clie_id as idClienteResponsavel, clieImov.imov_id as idImovel, "
							+ " ligAguaSit.last_dsabreviado as situacaoLigacaoAgua " + " FROM cadastro.cliente clie "
							+ " INNER JOIN cadastro.cliente_imovel clieImov "
							+ " on clieImov.clie_id = clie.clie_id and clieImov.clim_dtrelacaofim is null "
							+ " INNER JOIN cadastro.imovel imov " + " on imov.imov_id = clieImov.imov_id "
							+ " INNER JOIN atendimentopublico.ligacao_agua_situacao ligAguaSit " + " on ligAguaSit.last_id = imov.last_id "
							+ " WHERE clie.clie_id in (:idsClientes)";

			retorno = session.createSQLQuery(consulta).addScalar("idClienteResponsavel", Hibernate.INTEGER).addScalar("idImovel",
							Hibernate.INTEGER).addScalar("situacaoLigacaoAgua", Hibernate.STRING).setParameterList("idsClientes",
							idsClientes).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC3135] Gerar Relatório de Materiais Aplicados
	 * 
	 * @author Felipe Rosacruz
	 * @date 03/02/2014
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> obterDadosRelatorioMateriaisAplicados(Integer idLocalidade, Integer cdSetorComercial,
					Date dataExecucaoInicial, Date dataExecucaoFinal, Collection<Integer> colecaoIdServicoTipo,
					Collection<Integer> colecaoIdMaterial, Collection<Integer> colecaoIdEquipe) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;
		Collection retorno = null;
		try{
			// 0
			consulta = "SELECT LOCA.LOCA_ID idLocalidade, "
							+
							// 1
							"STCM.STCM_CDSETORCOMERCIAL cdSetorComercial, "
							+
							// 2
							"SVTP.SVTP_ID idServicoTipo, "
							+
							// 3
							"EQPE1.EQPE_ID idEquipe1, "
							+
							// 4
							"EQPE2.EQPE_ID idEquipe2, "
							+
							// 5
							"ORSE.ORSE_TMEXECUCAO tmExecucao, "
							+
							// 6
							"ORSE.ORSE_ID idOrdemServico, "
							+
							// 7
							"MATE.MATE_DSMATERIAL dsMaterial, "
							+
							// 8
							"OAME.OAME_QTMATERIAL qtMaterial, "
							+
							// 9
							"LOCA.LOCA_NMLOCALIDADE nmLocalidade, "
							+
							// 10
							"STCM.STCM_NMSETORCOMERCIAL nmSetorComercial, "
							+
							// 11
							"SVTP.SVTP_DSSERVICOTIPO dsServicoTipo, "
							+
							// 12
							"EQPE1.EQPE_NMEQUIPE nmEquipe1, "
							+
							// 13
							"EQPE2.EQPE_NMEQUIPE nmEquipe2, "
							+
							// 14
							"MATE.MATE_ID idMaterial "

							+ "FROM OS_ATIVIDADE_MATERIAL_EXECUCAO OAME " + "INNER JOIN MATERIAL MATE ON MATE.MATE_ID = OAME.MATE_ID "
							+ "INNER JOIN ORDEM_SERVICO_ATIVIDADE OSAT ON OSAT.OSAT_ID = OAME.OSAT_ID "
							+ "INNER JOIN ORDEM_SERVICO ORSE ON ORSE.ORSE_ID = OSAT.ORSE_ID "
							+ "INNER JOIN SERVICO_TIPO SVTP ON SVTP.SVTP_ID = ORSE.SVTP_ID "
							+ "INNER JOIN IMOVEL IMOV ON IMOV.IMOV_ID = ORSE.IMOV_ID "
							+ "INNER JOIN LOCALIDADE LOCA ON LOCA.LOCA_ID = IMOV.LOCA_ID "
							+ "INNER JOIN SETOR_COMERCIAL STCM ON STCM.STCM_ID = IMOV.STCM_ID "
							+ "INNER JOIN ordem_servico_programacao OSPG ON OSPG.ORSE_ID = ORSE.ORSE_ID "
							+ "INNER JOIN EQUIPE EQPE1 ON EQPE1.EQPE_ID = OSPG.EQPE_ID "
							+ "LEFT JOIN OS_ATIVIDADE_PERIODO_EXECUCAO OAPE ON OAPE.OSAT_ID = OSAT.OSAT_ID "
							+ "LEFT JOIN OS_EXECUCAO_EQUIPE OSEE ON OSEE.OAPE_ID = OAPE.OAPE_ID "
							+ "LEFT JOIN EQUIPE EQPE2 ON EQPE2.EQPE_ID = OSEE.EQPE_ID ";
			consulta += "WHERE ORSE.ORSE_TMEXECUCAO between :dataExecucaoInicial and :dataExecucaoFinal ";
			if(idLocalidade != null){
				consulta += "AND LOCA.LOCA_ID = :idLocalidade ";
			}
			if(cdSetorComercial != null){
				consulta += "AND STCM.STCM_CDSETORCOMERCIAL = :cdSetorComercial ";
			}
			if(!Util.isVazioOrNulo(colecaoIdServicoTipo)){
				consulta += "AND SVTP.SVTP_ID in (:colecaoIdServicoTipo) ";
			}
			if(!Util.isVazioOrNulo(colecaoIdMaterial)){
				consulta += "AND MATE.MATE_ID in (:colecaoIdMaterial) ";
			}
			if(!Util.isVazioOrNulo(colecaoIdEquipe)){
				consulta += "AND (EQPE1.EQPE_ID in (:colecaoIdEquipe) or EQPE2.EQPE_ID in (:colecaoIdEquipe))";

			}

			if(dataExecucaoInicial != null){
				dataExecucaoInicial = Util.formatarDataInicial(dataExecucaoInicial);
			}

			if(dataExecucaoFinal != null){
				dataExecucaoFinal = Util.formatarDataFinal(dataExecucaoFinal);
			}

			Query query = session.createSQLQuery(consulta).addScalar("idLocalidade", Hibernate.INTEGER)
							.addScalar("cdSetorComercial", Hibernate.INTEGER).addScalar("idServicoTipo", Hibernate.INTEGER)
							.addScalar("idEquipe1", Hibernate.INTEGER).addScalar("idEquipe2", Hibernate.INTEGER)
							.addScalar("tmExecucao", Hibernate.DATE)
							.addScalar("idOrdemServico", Hibernate.INTEGER).addScalar("dsMaterial", Hibernate.STRING)
							.addScalar("qtMaterial", Hibernate.INTEGER).addScalar("nmLocalidade", Hibernate.STRING)
							.addScalar("nmSetorComercial", Hibernate.STRING).addScalar("dsServicoTipo", Hibernate.STRING)
.addScalar("nmEquipe1", Hibernate.STRING)
							.addScalar("nmEquipe2", Hibernate.STRING).addScalar("idMaterial", Hibernate.INTEGER)
							.setTimestamp("dataExecucaoInicial", dataExecucaoInicial).setTimestamp("dataExecucaoFinal", dataExecucaoFinal);

			if(idLocalidade != null){
				query = query.setInteger("idLocalidade", idLocalidade);
			}
			if(cdSetorComercial != null){
				query = query.setInteger("cdSetorComercial", cdSetorComercial);
			}
			if(!Util.isVazioOrNulo(colecaoIdServicoTipo)){
				query = query.setParameterList("colecaoIdServicoTipo", colecaoIdServicoTipo);
			}
			if(!Util.isVazioOrNulo(colecaoIdMaterial)){
				query = query.setParameterList("colecaoIdMaterial", colecaoIdMaterial);
			}
			if(!Util.isVazioOrNulo(colecaoIdEquipe)){
				query = query.setParameterList("colecaoIdEquipe", colecaoIdEquipe);
			}
			retorno = (Collection) query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

}