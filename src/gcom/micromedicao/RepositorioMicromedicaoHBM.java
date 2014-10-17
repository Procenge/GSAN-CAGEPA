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

package gcom.micromedicao;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.bean.LeituraConsumoHelper;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cadastro.imovel.bean.ImovelCobrarDoacaoHelper;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.RotaAcaoCriterio;
import gcom.faturamento.FaturamentoAtividade;
import gcom.faturamento.FaturamentoAtividadeCriterio;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FaturamentoSituacaoTipo;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.ConsumoTarifaVigencia;
import gcom.faturamento.credito.CreditoTipo;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.faturamento.debito.DebitoTipo;
import gcom.gerencial.cadastro.bean.ResumoLigacaoEconomiaHelper;
import gcom.gui.micromedicao.DadosMovimentacao;
import gcom.interceptor.ObjetoTransacao;
import gcom.micromedicao.bean.LigacaoMedicaoIndividualizadaHelper;
import gcom.micromedicao.consumo.*;
import gcom.micromedicao.hidrometro.*;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraTipo;
import gcom.micromedicao.medicao.FiltroMedicaoHistoricoSql;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.*;
import gcom.util.filtro.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import org.hibernate.*;
import org.hibernate.criterion.Expression;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 */
public class RepositorioMicromedicaoHBM
				implements IRepositorioMicromedicao {

	private static IRepositorioMicromedicao instancia;

	private String movimentoRoteiroEmpresaFaturamentoGrupo = "mre.faturamentoGrupo.id = :idFaturamentoGrupo and mre.anoMesMovimento = mre.faturamentoGrupo.anoMesReferencia ";

	/**
	 * Construtor da classe RepositorioMicromedicaoHBM
	 */
	private RepositorioMicromedicaoHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static IRepositorioMicromedicao getInstancia(){

		if(instancia == null){
			instancia = new RepositorioMicromedicaoHBM();
		}

		return instancia;
	}

	public Collection pesquisarMovimentoRoteiroEmpresa(Integer anoMesFaturamento, Integer idFaturamentoGrupo,
					Collection colecaoDeDebitosCobrados) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");
		Query query = null;

		try{
			consulta.append("select mre.imovel ");
			consulta.append("from MovimentoRoteiroEmpresa mre ");
			consulta.append("inner join  mre.rota rot ");
			consulta.append("inner join  mre.localidade ");
			consulta.append("inner join  mre.localidade.localidade ");
			consulta.append("inner join mre.imovel ");
			consulta.append("inner join mre.ligacaoAguaSituacao ");
			consulta.append("inner join mre.ligacaoEsgotoSituacao ");
			consulta.append("inner join mre.consumoTarifa ");
			consulta.append("where ");
			// consulta.append("  rot.leituraTipo.id = :idLeituraTipo  and " );
			consulta.append("  mre.indicadorFase in (6,7,8)");
			consulta.append(" and mre.faturamentoGrupo.id = :idFaturamentoGrupo");
			consulta.append(" and mre.anoMesMovimento = :anoMesFaturamento ");
			consulta.append(" and mre.imovel.id not in (:colecaoDeDebitosCobrados) ");
			consulta.append("order by rot.id ");

			query = session
							.createQuery(consulta.toString())
							// .setInteger("idLeituraTipo",
							// LeituraTipo.LEITURA_E_ENTRADA_SIMULTANEA)
							.setInteger("idFaturamentoGrupo", idFaturamentoGrupo).setInteger("anoMesFaturamento", anoMesFaturamento)
							.setParameterList("colecaoDeDebitosCobrados", colecaoDeDebitosCobrados);
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
	 * pesquisa uma coleção de cep(s) de acordo com o código
	 * 
	 * @param faixaInicial
	 *            Descrição do parâmetro
	 * @param faixaFinal
	 *            Descrição do parâmetro
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarNumeroHidrometroFaixaPaginacao(String faixaInicial, String faixaFinal, Integer numeroPagina)
					throws ErroRepositorioException{

		// Collection<Hidrometro> retorno = new ArrayList();
		Collection<Hidrometro> hidrometros = new ArrayList();
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select hidrometro " + "from gcom.micromedicao.hidrometro.Hidrometro hidrometro "
							+ "left join fetch hidrometro.hidrometroMarca " + "left join fetch hidrometro.hidrometroCapacidade "
							+ "left join fetch hidrometro.hidrometroSituacao " + "left join fetch hidrometro.hidrometroLocalArmazenagem "
							+ "where hidrometro.numero between :fi and :ff " + "order by hidrometro.numero";

			hidrometros = session.createQuery(consulta).setString("fi", faixaInicial).setString("ff", faixaFinal).setFirstResult(
							10 * numeroPagina).setMaxResults(10).list();

			/**
			 * alterado por pedro alexandre dia 23/01/2007
			 */
			// Carregar todas as entidades que são necessárias para a consulta
			/*
			 * Iterator iteratorHidrometros = hidrometros.iterator();
			 * while (iteratorHidrometros.hasNext()) { Hidrometro hidrometro =
			 * (Hidrometro) iteratorHidrometros.next();
			 * Hibernate.initialize(hidrometro.getHidrometroMarca());
			 * Hibernate.initialize(hidrometro.getHidrometroCapacidade());
			 * Hibernate.initialize(hidrometro.getHidrometroSituacao());
			 * Hibernate.initialize(hidrometro.getHidrometroLocalArmazenagem());
			 * retorno.add(hidrometro); }
			 */

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção de atividades pesquisada(s)
		// return retorno;
		return hidrometros;
	}

	/**
	 * @author Saulo Lima
	 * @date 03/03/2009
	 *       Alteração para colocar o Generics no retorno
	 * @param faixaInicial
	 * @param faixaFinal
	 * @return Collection<Hidrometro>
	 * @exception ErroRepositorioException
	 */
	public Collection<Hidrometro> pesquisarNumeroHidrometroFaixa(String faixaInicial, String faixaFinal) throws ErroRepositorioException{

		Collection<Hidrometro> hidrometros = new ArrayList();
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select hidrometro " + "from gcom.micromedicao.hidrometro.Hidrometro hidrometro "
							+ "left join fetch hidrometro.hidrometroMarca " + "left join fetch hidrometro.hidrometroCapacidade "
							+ "left join fetch hidrometro.hidrometroSituacao " + "left join fetch hidrometro.hidrometroLocalArmazenagem "
							+ "left join fetch hidrometro.hidrometroTipo " + "left join fetch hidrometro.hidrometroClasseMetrologica "
							+ "left join fetch hidrometro.hidrometroDiametro " + "where hidrometro.numero between :fi and :ff "
							+ "order by hidrometro.numero";

			hidrometros = session.createQuery(consulta).setString("fi", faixaInicial).setString("ff", faixaFinal).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção de atividades pesquisada(s)
		return hidrometros;
	}

	/**
	 * Pesquisa uma coleção de hidrômetros de acordo com fixo, faixa inicial e
	 * faixa final
	 * 
	 * @param faixaInicial
	 *            Descrição do parâmetro
	 * @param faixaFinal
	 *            Descrição do parâmetro
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarNumeroHidrometroFaixaRelatorio(String faixaInicial, String faixaFinal) throws ErroRepositorioException{

		// Collection<Hidrometro> retorno = new ArrayList();
		Collection<Hidrometro> hidrometros = new ArrayList();
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select hidrometro " + "from gcom.micromedicao.hidrometro.Hidrometro hidrometro "
							+ "left join fetch hidrometro.hidrometroClasseMetrologica " + "left join fetch hidrometro.hidrometroMarca "
							+ "left join fetch hidrometro.hidrometroDiametro " + "left join fetch hidrometro.hidrometroCapacidade "
							+ "left join fetch hidrometro.hidrometroTipo " + "left join fetch hidrometro.hidrometroSituacao "
							+ "where hidrometro.numero between :fi and :ff " + "order by hidrometro.numero";

			hidrometros = session.createQuery(consulta).setString("fi", faixaInicial).setString("ff", faixaFinal).list();

			/**
			 * alterado por pedro alexandre dia 23/01/2007
			 */
			/*
			 * // Carregar todas as entidades que são necessárias para a
			 * consulta Iterator iteratorHidrometros = hidrometros.iterator();
			 * while (iteratorHidrometros.hasNext()) { Hidrometro hidrometro =
			 * (Hidrometro) iteratorHidrometros.next();
			 * Hibernate.initialize(hidrometro.getHidrometroClasseMetrologica());
			 * Hibernate.initialize(hidrometro.getHidrometroMarca());
			 * Hibernate.initialize(hidrometro.getHidrometroDiametro());
			 * Hibernate.initialize(hidrometro.getHidrometroCapacidade());
			 * Hibernate.initialize(hidrometro.getHidrometroTipo());
			 * retorno.add(hidrometro); }
			 */

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção de atividades pesquisada(s)
		return hidrometros;
	}

	/**
	 * pesquisa uma coleção de cep(s) de acordo com o código
	 * 
	 * @param faixaInicial
	 *            Descrição do parâmetro
	 * @param faixaFinal
	 *            Descrição do parâmetro
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarNumeroHidrometroFaixaComLimite(String faixaInicial, String faixaFinal) throws ErroRepositorioException{

		Collection<Hidrometro> retorno = new ArrayList();
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select hidrometro " + "from gcom.micromedicao.hidrometro.Hidrometro hidrometro "
							+ "left join fetch hidrometro.hidrometroMarca " + "left join fetch hidrometro.hidrometroCapacidade "
							+ "left join fetch hidrometro.hidrometroSituacao " + "where hidrometro.numero between :fi and :ff "
							+ "order by hidrometro.numero ";

			retorno = session.createQuery(consulta).setString("fi", faixaInicial).setString("ff", faixaFinal).setMaxResults(50).list();

			/**
			 * alterado por pedro alexandre dia 23/01/2007
			 */
			/*
			 * Iterator<Hidrometro> iterator = session.createQuery(consulta)
			 * .setString("fi", faixaInicial).setString("ff", faixaFinal)
			 * .setMaxResults(50).iterate(); // Carregar todas as entidades que
			 * são necessárias para a consulta while (iterator.hasNext()) {
			 * Hidrometro hidrometro = iterator.next();
			 * Hibernate.initialize(hidrometro.getHidrometroMarca());
			 * Hibernate.initialize(hidrometro.getHidrometroCapacidade());
			 * Hibernate.initialize(hidrometro.getHidrometroSituacao());
			 * retorno.add(hidrometro); }
			 */

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
	 * @param faixaInicial
	 *            Descrição do parâmetro
	 * @param faixaFinal
	 *            Descrição do parâmetro
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Integer pesquisarNumeroHidrometroFaixaCount(String Fixo, String faixaInicial, String faixaFinal) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select count (hidrometro.id) from gcom.micromedicao.hidrometro.Hidrometro hidrometro where hidrometro.numero between :fi and :ff ";

			Object resultado = session.createQuery(consulta).setString("fi", faixaInicial).setString("ff", faixaFinal).uniqueResult();

			if(resultado != null){
				retorno = ((Number) resultado).intValue();
			}

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
	 * < <Descrição do método>>
	 * 
	 * @param faturamentoGrupo
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarImovelFaturamentoGrupoObterIds(FaturamentoGrupo faturamentoGrupo) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select im.id from Imovel im " + "inner join im.quadra " + "inner join im.rota "
							+ "inner join im.rota.faturamentoGrupo " + "where im.rota.faturamentoGrupo.id = :id ";

			retorno = session.createQuery(consulta).setInteger("id", faturamentoGrupo.getId().intValue()).list();

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
	 * @param faturamentoGrupo
	 *            Descrição do parâmetro
	 * @param imovel
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarImovelTesteMedicaoConsumoLigacaoAgua(FaturamentoGrupo faturamentoGrupo, Imovel imovel)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select new gcom.micromedicao.ImovelTestesMedicaoConsumo(mh.ligacaoAgua.imovel.id, "
							+ "mh.ligacaoAgua.imovel.ligacaoAguaSituacao.id, " + "mh.ligacaoAgua.hidrometroInstalacaoHistorico.id, "
							+ "mh.leituraAnteriorFaturamento, " + "mh.leituraAnteriorInformada, " + "mh.leituraSituacaoAnterior.id, "
							+ "mh.leituraAtualInformada, " + "mh.leituraSituacaoAtual.id, " + "mh.leituraAnormalidadeInformada.id, "
							+ "mh.ligacaoAgua.imovel.faturamentoSituacaoTipo.id, " + "mh.ligacaoAgua.numeroConsumoMinimoAgua, "
							+ "mh.ligacaoAgua.hidrometroInstalacaoHistorico.hidrometro.numeroDigitosLeitura, "
							+ "mh.ligacaoAgua.hidrometroInstalacaoHistorico.hidrometro.hidrometroCapacidade.id) "
							+ "from MedicaoHistorico mh " + "right join mh.ligacaoAgua " + "right join mh.ligacaoAgua.imovel "
							+ "where mh.ligacaoAgua.imovel.id = :id and mh.anoMesReferencia = :anoMes ";

			retorno = session.createQuery(consulta).setInteger("id", imovel.getId().intValue()).setInteger("anoMes",
							faturamentoGrupo.getAnoMesReferencia().intValue()).list();

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
	 * @param faturamentoGrupo
	 *            Descrição do parâmetro
	 * @param imovel
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarImovelTesteMedicaoConsumoPoco(FaturamentoGrupo faturamentoGrupo, Imovel imovel)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select new gcom.micromedicao.ImovelTestesMedicaoConsumo(mh.imovel.id, "
							+ "mh.imovel.hidrometroInstalacaoHistorico.id, " + "mh.leituraAnteriorFaturamento, "
							+ "mh.leituraAnteriorInformada, " + "mh.leituraSituacaoAnterior.id, " + "mh.leituraAtualInformada, "
							+ "mh.leituraSituacaoAtual.id, " + "mh.leituraAnormalidadeInformada.id, "
							+ "mh.imovel.faturamentoSituacaoTipo.id, " + "mh.imovel.pocoTipo.id, "
							+ "mh.imovel.hidrometroInstalacaoHistorico.hidrometro.numeroDigitosLeitura, "
							+ "mh.imovel.hidrometroInstalacaoHistorico.hidrometro.hidrometroCapacidade.id) " + "from MedicaoHistorico mh "
							+ "right join mh.imovel " + "where mh.imovel.id = :id and mh.anoMesReferencia = :anoMes ";

			retorno = session.createQuery(consulta).setInteger("id", imovel.getId().intValue()).setInteger("anoMes",
							faturamentoGrupo.getAnoMesReferencia().intValue()).list();

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
	 * @param faturamentoGrupo
	 *            Descrição do parâmetro
	 * @param imovel
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarImovelTesteMedicaoConsumoLigacaoEsgoto(FaturamentoGrupo faturamentoGrupo, Imovel imovel)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = " select new gcom.micromedicao.ImovelTestesMedicaoConsumo(im.id, " + " im.ligacaoEsgotoSituacao.id, "
							+ " im.ligacaoEsgoto.consumoMinimo, " + " im.ligacaoEsgoto.percentualAguaConsumidaColetada) "
							+ " from Imovel im " + " inner join im.ligacaoEsgoto " + " where im.id = :id ";

			retorno = session.createQuery(consulta).setInteger("id", imovel.getId().intValue()).list();

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
	 * @param imovel
	 *            Descrição do parâmetro
	 * @param anoMesReferencia
	 *            Descrição do parâmetro
	 * @param periodoInformado
	 *            Descrição do parâmetro
	 * @param ligacaoTipo
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarConsumoMedidoHidrometroPeriodoInformadoLigacaoAgua(Imovel imovel, int anoMesReferencia,
					int periodoInformado, LigacaoTipo ligacaoTipo) throws ErroRepositorioException{

		StringBuffer condicao = new StringBuffer();

		for(int i = periodoInformado; i > 0; i--){

			anoMesReferencia = Util.subtrairData(anoMesReferencia);

			condicao.append(" mh.anoMesReferencia = " + (anoMesReferencia) + " OR ");
		}

		int tamanhoCondicao = condicao.length();

		// retira o último (or) da condição
		String condicaoFormatada = condicao.substring(1, tamanhoCondicao - 4);

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT mh.anoMesReferencia," // 0
							+ " la.hidrometroInstalacaoHistorico.id," // 1
							+ " mh.hidrometroInstalacaoHistorico.id," // 2
							+ " mh.numeroConsumoMes," // 3
							+ " mh.medicaoTipo.id," // 4
							+ " ch.consumoAnormalidade.id," // 5
							+ " ch.numeroConsumoFaturadoMes," // 6
							+ " mh.leituraAtualInformada," // 7
							+ " mh.leituraAtualFaturamento," // 8
							+ " mh.leituraAnormalidadeFaturamento.id" // 9
							+ " FROM Imovel im, LigacaoAgua la, MedicaoHistorico  mh, ConsumoHistorico ch"
							+ " WHERE im.id = :imovelId"
							+ " AND ch.ligacaoTipo.id = :ligacaoTipoId" + " AND im.id = la.id"
							+ " AND la.id = mh.ligacaoAgua.id"
							+ " AND im.id = ch.imovel.id" + " AND mh.anoMesReferencia = ch.referenciaFaturamento"
							+ " AND ("
							+ condicaoFormatada + ") ORDER BY mh.anoMesReferencia desc";

			retorno = session.createQuery(consulta).setInteger("imovelId", imovel.getId().intValue()).setInteger("ligacaoTipoId",
							ligacaoTipo.getId().intValue()).list();

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
	 * @param imovel
	 *            Descrição do parâmetro
	 * @param anoMesReferencia
	 *            Descrição do parâmetro
	 * @param periodoInformado
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarConsumoMedidoHidrometroPeriodoInformadoPoco(Imovel imovel, int anoMesReferencia, int periodoInformado)
					throws ErroRepositorioException{

		StringBuffer condicao = new StringBuffer();

		for(int i = periodoInformado; i > 0; i--){
			condicao.append(" mh.anoMesReferencia = " + (anoMesReferencia - i) + " or ");
		}

		Collection retorno = null;

		int tamanhoCondicao = condicao.length();

		// retira o último (or) da condição
		String condicaoFormatada = condicao.substring(1, tamanhoCondicao - 4);

		Session session = HibernateUtil.getSession();
		String consulta;

		try{

			consulta = "SELECT mh.anoMesReferencia," // 0
							+ " im.hidrometroInstalacaoHistorico.id," // 1
							+ " mh.hidrometroInstalacaoHistorico.id," // 2
							+ " mh.numeroConsumoMes," // 3
							+ " mh.medicaoTipo.id," // 4
							+ " ch.consumoAnormalidade.id," // 5
							+ " ch.numeroConsumoFaturadoMes," // 6
							+ " mh.leituraAtualInformada," // 7
							+ " mh.leituraAtualFaturamento," // 8
							+ " mh.leituraAnormalidadeFaturamento.id" // 9
							+ " FROM Imovel im,MedicaoHistorico  mh, ConsumoHistorico ch"
							+ " WHERE im.id = :imovelId"
							+ " AND ch.ligacaoTipo.id = :ligacaoTipoId" + " AND im.id = mh.imovel.id"
							+ " AND im.id = ch.imovel.id"
							+ " AND mh.anoMesReferencia = ch.referenciaFaturamento" + " AND (" + condicaoFormatada
							+ ")"
							+ " ORDER BY mh.anoMesReferencia desc ";

			retorno = session.createQuery(consulta).setInteger("imovelId", imovel.getId().intValue()).setInteger("ligacaoTipoId",
							LigacaoTipo.LIGACAO_ESGOTO).list();

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
	 * @param imovel
	 *            Descrição do parâmetro
	 * @param anoMesReferencia
	 *            Descrição do parâmetro
	 * @param periodoInformado
	 *            Descrição do parâmetro
	 * @param ligacaoTipo
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Integer pesquisarConsumoFaturadoMesPorConsumoHistorico(Integer idImovel, int anoMesReferencia, Integer idLigacaoTipo)
					throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT ch.numeroConsumoFaturadoMes" + " FROM Imovel im, LigacaoAgua la, MedicaoHistorico  mh, ConsumoHistorico ch"
							+ " WHERE im.id = :imovelId" + " AND ch.ligacaoTipo = :ligacaoTipoId" + " AND im.id = la.id"
							+ " AND la.id = mh.ligacaoAgua.id" + " AND im.id = ch.imovel.id"
							+ " AND ch.referenciaFaturamento = :anoMesReferencia" + " AND mh.anoMesReferencia = ch.referenciaFaturamento";

			retorno = (Integer) session.createQuery(consulta).setInteger("imovelId", idImovel).setInteger("ligacaoTipoId", idLigacaoTipo)
							.setInteger("anoMesReferencia", anoMesReferencia).setMaxResults(1).uniqueResult();

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
	 * @param imovel
	 *            Descrição do parâmetro
	 * @param anoMesReferencia
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarObterConsumoMedioImovel(Imovel imovel, int anoMesReferencia) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select ch.id, " + "ch.ligacaoTipo.id, " + "ch.consumoTipo.id, " + "ch.consumoAnormalidade.id, "
							+ "ch.numeroConsumoFaturadoMes, " + "ch.percentualColeta " + "from ConsumoHistorico ch "
							+ "inner join ch.imovel imov " + "where imov.id = :id and ch.referenciaFaturamento = :anoMes ";

			retorno = session.createQuery(consulta).setInteger("id", imovel.getId().intValue()).setInteger("anoMes", anoMesReferencia)
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
	 * < <Descrição do método>>
	 * 
	 * @param imovel
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarImovelLigacaoSituacao(Imovel imovel) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select im.id, " + "im.ligacaoAguaSituacao.id, " + "im.ligacaoEsgotoSituacao.id " + "from Imovel im "
							+ "inner join im.ligacaoAguaSituacao " + "inner join im.ligacaoEsgotoSituacao " + "where im.id = :id ";

			retorno = session.createQuery(consulta).setInteger("id", imovel.getId().intValue()).list();

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
	 * @param imovel
	 *            Descrição do parâmetro
	 * @param medicaoTipo
	 *            Descrição do parâmetro
	 * @param sistemaParametro
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarObterDadosHistoricoMedicao(Imovel imovel, MedicaoTipo medicaoTipo, SistemaParametro sistemaParametro)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		String composicao = null;

		/*
		 * // Caso seja ligação de água if (medicaoTipo.getId().intValue() ==
		 * MedicaoTipo.LIGACAO_AGUA .intValue()) { composicao = "where
		 * mh.medicaoTipo = " + (MedicaoTipo.LIGACAO_AGUA).toString() + " and
		 * mh.ligacaoAgua.id = " + imovel.getId().toString() + " and
		 * mh.anoMesReferencia = " + sistemaParametro.getAnoMesFaturamento() +
		 * ""; } else if (medicaoTipo.getId().intValue() == MedicaoTipo.POCO
		 * .intValue()) { composicao = "where mh.medicaoTipo = " +
		 * (MedicaoTipo.POCO).toString() + " and mh.imovel.id = " +
		 * imovel.getId().toString() + " and mh.anoMesReferencia = " +
		 * sistemaParametro.getAnoMesFaturamento() + ""; }
		 */

		// Caso seja ligação de água
		if(medicaoTipo.getId().intValue() == MedicaoTipo.LIGACAO_AGUA.intValue()){
			composicao = "where mh.medicaoTipo.id = :medicaoTipoId" + " and mh.ligacaoAgua.id = :imovelId"
							+ " and mh.anoMesReferencia = :amReferencia";
		}else if(medicaoTipo.getId().intValue() == MedicaoTipo.POCO.intValue()){
			composicao = "where mh.medicaoTipo.id = :medicaoTipoId" + " and mh.imovel.id = :imovelId "
							+ " and mh.anoMesReferencia = :amReferencia";

		}

		try{
			consulta = "select mh.id, " // 00
							+ "mh.imovel.id, " // 01
							+ "mh.ligacaoAgua.id, " // 02
							+ "mh.medicaoTipo.id, " // 03
							+ "mh.anoMesReferencia, " // 04
							+ "mh.leituraAnormalidadeInformada.id, " // 05
							+ "mh.leituraAnormalidadeFaturamento.id, " // 06
							+ "mh.leituraSituacaoAnterior.id, " // 07
							+ "mh.dataLeituraAnteriorFaturamento, " // 08
							+ "mh.leituraAnteriorFaturamento, " // 09
							+ "mh.leituraAnteriorInformada, " // 10
							+ "mh.leituraSituacaoAtual.id, " // 11
							+ "mh.dataLeituraAtualInformada, " // 12
							+ "mh.leituraAtualInformada, " // 13
							+ "mh.dataLeituraAtualFaturamento, " // 14
							+ "mh.numeroConsumoMes, " // 15
							+ "mh.numeroConsumoInformado, " // 16
							+ "mh.hidrometroInstalacaoHistorico.id, " // 17
							+ "mh.leituraAtualFaturamento, " // 18
							+ "mh.leituraProcessamentoMovimento, " // 19
							+ "mh.consumoCreditoAnterior, " // 20
							+ "mh.funcionario.id " // 21
							+ "from MedicaoHistorico mh " + "left join mh.imovel "
							+ "left join mh.ligacaoAgua "
							+ "inner join mh.medicaoTipo "
							+ "left join mh.leituraSituacaoAnterior "
							+ "inner join mh.leituraSituacaoAtual "
							+ "left join mh.hidrometroInstalacaoHistorico "
							+ "left join mh.funcionario " + composicao;

			retorno = session.createQuery(consulta).setInteger("medicaoTipoId", medicaoTipo.getId()).setInteger("imovelId", imovel.getId())
							.setInteger("amReferencia", sistemaParametro.getAnoMesFaturamento()).list();

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
	 * Método utilizado para pesquisar dados do maior histórico de medição
	 * existente para um imóvel
	 * [UC0101] Consistir Leituras e Calcular Consumos Autor: Leonardo Vieira
	 * Data: 20/02/2006
	 * 
	 * @author eduardo henrique
	 * @date 29/09/2008
	 *       Adicionado na consulta o campo de Crédito de Consumo Anterior
	 */

	public Object pesquisarObterDadosMaiorHistoricoMedicao(Imovel imovel, MedicaoTipo medicaoTipo, SistemaParametro sistemaParametro)
					throws ErroRepositorioException{

		Object retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		String composicao = null;
		String composicaoInner = null;

		// Caso seja ligação de água
		if(medicaoTipo.getId().intValue() == MedicaoTipo.LIGACAO_AGUA.intValue()){
			composicao = "where mt.id = :medicaoTipoId  and la.id = :imovelId ";
			composicaoInner = "where mti.id = :medicaoTipoId and lai.id = :imovelId ";

		}else if(medicaoTipo.getId().intValue() == MedicaoTipo.POCO.intValue()){
			composicao = "where mt.id = :medicaoTipoId and im.id = :imovelId ";
			composicaoInner = "where mti.id = :medicaoTipoId and imi.id = :imovelId ";
		}

		try{
			consulta = " select mh.dataLeituraAtualFaturamento, "
							+ // 0
							"mh.leituraAtualFaturamento, "
							+ // 1
							"mh.leituraAtualInformada, "
							+ // 2
							"mh.consumoCreditoAnterior, "
							+ // 3
							"mh.leituraSituacaoAtual.id "
							+ // 4
							"from MedicaoHistorico mh " + "inner join mh.medicaoTipo mt " + "left join mh.ligacaoAgua la "
							+ "left join mh.imovel im " + composicao
							+ " and mh.anoMesReferencia in (select max(mhi.anoMesReferencia) from MedicaoHistorico mhi "
							+ " inner join mhi.medicaoTipo mti " + " left join mhi.ligacaoAgua lai " + " left join mhi.imovel imi "
							+ composicaoInner + ")";

			retorno = session.createQuery(consulta).setInteger("medicaoTipoId", medicaoTipo.getId()).setInteger("imovelId", imovel.getId())
							.uniqueResult();

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
	 * @param objeto
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Object inserirBat(Object objeto) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		Object retorno = null;

		try{

			retorno = session.save(objeto);
			session.flush();
			session.clear();
			return retorno;
		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			session.clear();
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param rotas
	 *            Descrição do parâmetro
	 * @param anoMesReferencia
	 *            Descrição do parâmetro
	 * @param ligacaoAguaSituacaoLigado
	 *            Descrição do parâmetro
	 * @param ligacaoAguaSituacaoCortado
	 *            Descrição do parâmetro
	 * @param ligacaoEsgotoLigado
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	// public Collection pesquisarImoveisLigadosCortadosAguaLigadosEsgoto(Rota rota, int
	// anoMesReferencia, Integer ligacaoAguaSituacaoLigado,
	// Integer ligacaoAguaSituacaoCortado, Integer ligacaoEsgotoLigado) throws
	// ErroRepositorioException{
	// Alteração conforme OC0857313
	// .......................................................
	public Collection pesquisarImoveisLigadosCortadosAguaLigadosEsgoto(Rota rota, int anoMesReferencia) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select im.id," // 0
							+ "im.ligacaoAguaSituacao.id, " // 1
							+ "im.ligacaoEsgotoSituacao.id, " // 2
							+ "li.id, " // 3
							+ "hia.id, " // 4
							+ "hdra.id, " // 5
							+ "hdra.numeroDigitosLeitura, " // 6
							+ "fst.id," // 7
							+ "im.pocoTipo.id, " // 8
							+ "hie.id, " // 9
							+ "hdre.id, " // 10
							+ "im.rota.indicadorAjusteConsumo, " // 11
							+ "im.ligacaoAgua.numeroConsumoMinimoAgua, " // 12
							+ "im.indicadorImovelCondominio, " // 13
							+ "fst.indicadorParalisacaoFaturamento, " // 14
							+ "im.indicadorDebitoConta, " // 15
							+ "le.id, " // Ligacao Esgoto //16
							+ "le.consumoMinimo, " // 17
							+ "hia.dataInstalacao, " // 18
							+ "ct.id, " // 19
							+ "le.percentualAguaConsumidaColetada, " // 20
							+ "im.quantidadeEconomias, " // 21
							+ "hdre.numeroDigitosLeitura, " // 22
							+ "hie.dataInstalacao "// 23
							+ "from Imovel im "
							+ "inner join im.rota.faturamentoGrupo "
							+ "inner join im.consumoTarifa ct "
							+ "left join im.ligacaoAgua li "
							+ "left join im.ligacaoAgua.hidrometroInstalacaoHistorico hia "
							+ "left join im.ligacaoAgua.hidrometroInstalacaoHistorico.hidrometro hdra "
							+ "left join im.hidrometroInstalacaoHistorico hie "
							+ "left join im.hidrometroInstalacaoHistorico.hidrometro hdre "
							+ "left join im.faturamentoSituacaoTipo fst "
							+ "left join im.ligacaoEsgoto le "
							+ "left join im.ligacaoAguaSituacao las "
							+ "left join im.ligacaoEsgotoSituacao les "
							+ "where (im.rota.faturamentoGrupo.anoMesReferencia = :anoMes) "
							+ "and im.rota.id = :rota "
							// +
							// "and (im.ligacaoAguaSituacao.id = :ligacaoAguaSituacaoId1 or im.ligacaoAguaSituacao = :ligacaoAguaSituacaoId2 or im.ligacaoEsgotoSituacao.id = :ligacaoAguaSituacaoId3) ";
							+ "and (las.indicadorFaturamentoSituacao = :indicadorFaturamentoSituacaoLigacaoAgua or les.indicadorFaturamentoSituacao = :indicadorFaturamentoSituacaoLigacaoEsgoto) ";

			retorno = session.createQuery(consulta).setInteger("anoMes", anoMesReferencia)
			// .setInteger("ligacaoAguaSituacaoId1",ligacaoAguaSituacaoLigado.intValue())
							// .setInteger("ligacaoAguaSituacaoId2",ligacaoAguaSituacaoCortado.intValue())
							// .setInteger("ligacaoAguaSituacaoId3", ligacaoEsgotoLigado.intValue())
							.setInteger("rota", rota.getId()).setShort("indicadorFaturamentoSituacaoLigacaoAgua",
											LigacaoAguaSituacao.FATURAMENTO_ATIVO).setShort("indicadorFaturamentoSituacaoLigacaoEsgoto",
											LigacaoEsgotoSituacao.FATURAMENTO_ATIVO).list();

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
	 * Método Especifico Para CAERN
	 * 
	 * @author Raphael Rossiter, Flávio Cordeiro
	 * @date 20/03/2007
	 * @param rota
	 * @param anoMesReferencia
	 * @param ligacaoAguaSituacaoLigado
	 * @param ligacaoEsgotoLigado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImoveisLigadosAguaLigadosEsgoto(Rota rota, int anoMesReferencia, Integer ligacaoAguaSituacaoLigado,
					Integer ligacaoEsgotoLigado) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select im.id," // 0
							+ "im.ligacaoAguaSituacao.id, " // 1
							+ "im.ligacaoEsgotoSituacao.id, " // 2
							+ "li.id, " // 3
							+ "hia.id, " // 4
							+ "hdra.id, " // 5
							+ "hdra.numeroDigitosLeitura, " // 6
							+ "fst.id," // 7
							+ "im.pocoTipo.id, " // 8
							+ "hie.id, " // 9
							+ "hdre.id, " // 10
							+ "im.rota.indicadorAjusteConsumo, " // 11
							+ "im.ligacaoAgua.numeroConsumoMinimoAgua, " // 12
							+ "im.indicadorImovelCondominio, " // 13
							+ "fst.indicadorParalisacaoFaturamento, " // 14
							+ "im.indicadorDebitoConta, " // 15
							+ "le.id, " // Ligacao Esgoto //16
							+ "le.consumoMinimo, " // 17
							+ "hia.dataInstalacao, " // 18
							+ "ct.id, " // 19
							+ "le.percentualAguaConsumidaColetada, " // 20
							+ "im.quantidadeEconomias, " // 21
							+ "hdre.numeroDigitosLeitura, " // 22
							+ "hie.dataInstalacao "// 23
							+ "from Imovel im "
							+ "inner join im.rota.faturamentoGrupo "
							+ "inner join im.consumoTarifa ct "
							+ "left join im.ligacaoAgua li "
							+ "left join im.ligacaoAgua.hidrometroInstalacaoHistorico hia "
							+ "left join im.ligacaoAgua.hidrometroInstalacaoHistorico.hidrometro hdra "
							+ "left join im.hidrometroInstalacaoHistorico hie "
							+ "left join im.hidrometroInstalacaoHistorico.hidrometro hdre "
							+ "left join im.faturamentoSituacaoTipo fst "
							+ "left join im.ligacaoEsgoto le "
							+ "where (im.rota.faturamentoGrupo.anoMesReferencia = :anoMes) "
							+ "and im.rota.id = :rota "
							+ "and (im.ligacaoAguaSituacao.id = :ligacaoAguaSituacaoId1 or im.ligacaoEsgotoSituacao.id = :ligacaoAguaSituacaoId3) ";

			retorno = session.createQuery(consulta).setInteger("anoMes", anoMesReferencia).setInteger("ligacaoAguaSituacaoId1",
							ligacaoAguaSituacaoLigado.intValue()).setInteger("ligacaoAguaSituacaoId3", ligacaoEsgotoLigado.intValue())
							.setInteger("rota", rota.getId()).list();

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
	 * @param imovel
	 *            Descrição do parâmetro
	 * @param ligacaoTipo
	 *            Descrição do parâmetro
	 * @param anoMesReferencia
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarConsumoHistoricoConsumoAnormalidade(Imovel imovel, LigacaoTipo ligacaoTipo, int anoMesReferencia)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String consulta = "select ch.id, ca.id, lt.id,  ch.referenciaFaturamento  from ConsumoHistorico ch "
							+ "inner join ch.consumoAnormalidade ca " + "inner join ch.ligacaoTipo lt " + "inner join ch.imovel im "
							+ "where im.id = :id and lt.id = :ligacaoTipoId and ch.referenciaFaturamento = :anoMes ";

			retorno = session.createQuery(consulta).setInteger("id", imovel.getId().intValue()).setInteger("ligacaoTipoId",
							ligacaoTipo.getId().intValue()).setInteger("anoMes", anoMesReferencia).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public Collection pesquisarConsumoHistorico(Imovel imovel, LigacaoTipo ligacaoTipo, int anoMesReferencia)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String consulta = "select ch.id, ch.imovel.id, ch.ligacaoTipo.id, "
							+ "ch.referenciaFaturamento, ch.indicadorAlteracaoUltimosConsumos, "
							+ "ch.indicadorAjuste, ch.numeroConsumoFaturadoMes, ch.consumoRateio, "
							+ "ch.consumoHistoricoCondominio.id, ch.indicadorImovelCondominio, ch.consumoMedio,"
							+ "ch.consumoMinimo, ch.percentualColeta, ch.ultimaAlteracao, "
							+ "ch.rateioTipo.id, ch.consumoTipo.id, ch.consumoAnormalidade.id, "
							+ "ch.pocoTipo.id, ch.faturamentoSituacaoTipo.id, ch.indicadorFaturamento " + "from ConsumoHistorico ch "
							+ "where ch.imovel.id = :id and ch.referenciaFaturamento = :anoMes and ch.ligacaoTipo.id = :ligacaoTipoId ";

			retorno = session.createQuery(consulta).setInteger("id", imovel.getId().intValue()).setInteger("ligacaoTipoId",
							ligacaoTipo.getId().intValue()).setInteger("anoMes", anoMesReferencia).list();

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
	 * Adicionado o campo indicadorCreditoConsumo
	 * 
	 * @author Virgínia Melo
	 *         Customização v0.05
	 */
	public Collection pesquisarAnormalidadeLeitura(LeituraAnormalidade leituraAnormalidadeFaturamento) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String consulta = "select la.id, " + "la.leituraAnormalidadeConsumoSemleitura.id, "
							+ "la.leituraAnormalidadeConsumoComleitura.id, " + "la.leituraAnormalidadeLeituraSemleitura.id, "
							+ "la.leituraAnormalidadeLeituraComleitura.id, " + "la.indicadorCreditoConsumo "
							+ "from LeituraAnormalidade la " + "left join la.leituraAnormalidadeConsumoSemleitura "
							+ "left join la.leituraAnormalidadeConsumoComleitura " + "left join la.leituraAnormalidadeLeituraSemleitura "
							+ "left join la.leituraAnormalidadeLeituraComleitura " + "where la.id = :idLeituraAnormalidadeFaturamento";

			retorno = session.createQuery(consulta).setInteger("idLeituraAnormalidadeFaturamento",
							leituraAnormalidadeFaturamento.getId().intValue()).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public Collection pesquisarFaturamentoSituacaoTipo(FaturamentoSituacaoTipo faturamentoSituacaoTipo) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String consulta = "select fst.id, " + "fst.leituraAnormalidadeConsumoSemLeitura.id, "
							+ "fst.leituraAnormalidadeConsumoComLeitura.id, " + "fst.leituraAnormalidadeLeituraSemLeitura.id, "
							+ "fst.leituraAnormalidadeLeituraComLeitura.id " + "from FaturamentoSituacaoTipo fst "
							+ "left join fst.leituraAnormalidadeConsumoSemLeitura " + "left join fst.leituraAnormalidadeConsumoComLeitura "
							+ "left join fst.leituraAnormalidadeLeituraSemLeitura " + "left join fst.leituraAnormalidadeLeituraComLeitura "
							+ "where fst.id = :idFaturamentoSituacaoTipo";

			retorno = session.createQuery(consulta).setInteger("idFaturamentoSituacaoTipo", faturamentoSituacaoTipo.getId().intValue())
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

	public Collection pesquisarMaiorDataVigenciaConsumoTarifaImovel(Date dataCorrente, Integer idConsumoTarifa)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			String consulta = "select ctv.id, ctv.dataVigencia " + "from ConsumoTarifaVigencia ctv  " + "inner join ctv.consumoTarifa ct "
							+ "where ct.id = :consumoTarifaId " + "and ctv.dataVigencia in "
							+ "(select max(ctv2.dataVigencia) from ConsumoTarifaVigencia ctv2 " + "inner join ctv2.consumoTarifa ct2 "
							+ "where ct2.id = :consumoTarifaId and ctv2.dataVigencia  <= :dataCorrente)";

			retorno = session.createQuery(consulta).setDate("dataCorrente", dataCorrente).setInteger("consumoTarifaId",
 idConsumoTarifa.intValue()).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public Collection pesquisarDataVigenciaConsumoTarifaPeriodo(Date dataCorrente, Integer idConsumoTarifaConta)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			String consulta = "select ctv.id, ctv.dataVigencia " + "from ConsumoTarifaVigencia ctv  " + "inner join ctv.consumoTarifa ct "
							+ "where ct.id = :consumoTarifaId " + "and ctv.dataVigencia in "
							+ "(select max(ctv2.dataVigencia) from ConsumoTarifaVigencia ctv2 " + "inner join ctv2.consumoTarifa ct2 "
							+ "where ct2.id = :consumoTarifaId and ctv2.dataVigencia  <= :dataCorrente)";

			retorno = session.createQuery(consulta).setDate("dataCorrente", dataCorrente).setInteger("consumoTarifaId",
							idConsumoTarifaConta.intValue()).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public Object pesquisarConsumoMinimoTarifaCategoriaVigencia(Categoria categoria, ConsumoTarifaVigencia consumoTarifaVigencia)
					throws ErroRepositorioException{

		Object retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String consulta = "select ct.numeroConsumoMinimo " + "from ConsumoTarifaCategoria ct "
							+ "inner join ct.consumoTarifaVigencia.consumoTarifa " + "inner join ct.categoria "
							+ "where ct.consumoTarifaVigencia.id = :consumoTarifaVigenciaId " + "and ct.categoria.id = :categoriaId "
							+ "and ct.subCategoria.id = :subCategoriaId ";

			retorno = session.createQuery(consulta).setInteger("consumoTarifaVigenciaId", consumoTarifaVigencia.getId()).setInteger(
							"categoriaId", categoria.getId()).setInteger("subCategoriaId", Subcategoria.SUBCATEGORIA_ZERO.getId())
							.uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public void inseriOuAtualizaMedicaoHistorico(Collection colecaoMedicaoHistorico) throws ErroRepositorioException{

		StatelessSession session = HibernateUtil.getStatelessSession();

		try{
			Iterator iteratorColecaoMedicaoHistorico = colecaoMedicaoHistorico.iterator();

			while(iteratorColecaoMedicaoHistorico.hasNext()){

				MedicaoHistorico medicaoHistorico = (MedicaoHistorico) iteratorColecaoMedicaoHistorico.next();

				if(medicaoHistorico.getId() == null){
					session.insert(medicaoHistorico);
				}else{
					session.update(medicaoHistorico);
				}

				/*
				 * if (i % 50 == 0) { // 20, same as the JDBC batch size //
				 * flush a batch of inserts and release memory: session.flush();
				 * session.clear(); } i++;
				 */
			}
			// session.flush();
			// session.clear();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
			// session.close();
		}

	}

	public void inseriOuAtualizaConsumoHistorico(Collection colecaoConsumoHistorico) throws ErroRepositorioException{

		StatelessSession session = HibernateUtil.getStatelessSession();

		Iterator iteratorColecaoConsumoHistorico = colecaoConsumoHistorico.iterator();

		try{

			while(iteratorColecaoConsumoHistorico.hasNext()){

				ConsumoHistorico consumoHistorico = (ConsumoHistorico) iteratorColecaoConsumoHistorico.next();

				if(consumoHistorico.getId() == null){
					session.insert(consumoHistorico);
				}else{
					session.update(consumoHistorico);
				}

				/*
				 * if (i % 50 == 0) { // 20, same as the JDBC batch size //
				 * flush a batch of inserts and release memory: session.flush();
				 * session.clear(); } i++;
				 */
			}
			// session.flush();
			// session.clear();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
			// session.close();
		}

	}

	public Collection pesquisarImoveisPorRota(Collection colecaoRota, Integer idLeituraTipo, int inicioPesquisa)
					throws ErroRepositorioException{

		Collection retorno = null;
		Collection idsRotas = new ArrayList();
		Iterator iteColecaoRotas = colecaoRota.iterator();
		while(iteColecaoRotas.hasNext()){
			Rota rota = (Rota) iteColecaoRotas.next();
			idsRotas.add(rota.getId());
		}

		Session session = HibernateUtil.getSession();

		try{
			String consulta = "select imovel.id,"
							+ // 0
							"localidade.id,"
							+ // 1
							"setorComercial.codigo,"
							+ // 2
							"quadra.numeroQuadra,"
							+ // 3
							"imovel.lote,"
							+ // 4
							"imovel.subLote,"
							+ // 5
							"imovelPerfil.id,"
							+ // 6
							"ligacaoAgua.id,"
							+ // 7
							"hidInstHistoricoAgua.id,"
							+ // 8
							"hidInstHistoricoPoco.id,"
							+ // 9
							"rota.id,"
							+ // 10
							"rota.indicadorFiscalizarSuprimido,"
							+ // 11
							"rota.indicadorFiscalizarCortado,"
							+ // 12
							"rota.indicadorGerarFiscalizacao,"
							+ // 13
							"rota.indicadorGerarFalsaFaixa,"
							+ // 14
							"rota.percentualGeracaoFiscalizacao,"
							+ // 15
							"rota.percentualGeracaoFaixaFalsa,"
							+ // 16
							"empresa.id,"
							+ // 17
							"empresa.descricaoAbreviada,"
							+ // 18
							"empresa.email,"
							+ // 19
							"faturamentoGrupo.id,"
							+ // 20
							"faturamentoGrupo.descricao,"
							+ // 21
							"leituraTipo.id,"
							+ // 22
							"ligacaoAguaSituacao.id,"
							+ // 23
							"ligacaoEsgotoSituacao.id, "
							+ // 24
							"faturamentoSituacaoTipo.indicadorParalisacaoLeitura, "// 25
							+ "medTipoAgua.id, "// 26
							+ "medTipoPoco.id "// 27

							+ "from Imovel imovel " + "left join imovel.localidade localidade "
							+ "left join imovel.setorComercial setorComercial " + "left join imovel.quadra quadra "
							+ "left join imovel.imovelPerfil imovelPerfil " + "left join imovel.ligacaoAgua ligacaoAgua "
							+ "left join imovel.hidrometroInstalacaoHistorico hidInstHistoricoPoco "
							+ "left join ligacaoAgua.hidrometroInstalacaoHistorico hidInstHistoricoAgua "
							+ "left join hidInstHistoricoAgua.medicaoTipo medTipoAgua "
							+ "left join hidInstHistoricoPoco.medicaoTipo medTipoPoco " + "left join imovel.rota rota "
							+ "left join rota.empresa empresa " + "left join rota.faturamentoGrupo faturamentoGrupo "
							+ "left join imovel.ligacaoAguaSituacao ligacaoAguaSituacao "
							+ "left join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao "
							+ "left join imovel.faturamentoSituacaoTipo faturamentoSituacaoTipo "
							+ "left join rota.leituraTipo leituraTipo "

							+ "where rota.id in (:idsRotas) and " + "leituraTipo.id = :idLeituraTipo " + "order by empresa.id";

			retorno = session.createQuery(consulta).setInteger("idLeituraTipo", idLeituraTipo).setParameterList("idsRotas", idsRotas)
							.setFirstResult(inicioPesquisa).setMaxResults(1000).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public Collection pesquisarDadosHidrometroTipoLigacaoAgua(Integer idImovel) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select hm.codigoHidrometroMarca,"
							+ // 0
							" hid.numero,"
							+ // 1
							" hc.codigoHidrometroCapacidade,"
							+ // 2
							" hli.id,"
							+ // 3
							" hih.dataInstalacao,"
							+ // 4
							" hp.id,"
							+ // 5
							" mt.id, "
							+ // 6
							" hid.numeroDigitosLeitura, "
							+ // 7
							" hc.id, "
							+ // 8 Adicionado por Rodrigo 06/08/07
							" hm.id " // 9 Adicionado por Rodrigo 06/08/07
							+ "from Imovel imovel " + "inner join imovel.ligacaoAgua la "
							+ "inner join la.hidrometroInstalacaoHistorico hih " + "left join hih.hidrometroLocalInstalacao hli "
							+ "left join hih.hidrometro hid " + "left join hid.hidrometroMarca hm "
							+ "left join hid.hidrometroCapacidade hc " + "left join hih.hidrometroProtecao hp "
							+ "left join hih.hidrometroLocalInstalacao hli " + "left join hih.medicaoTipo mt "
							+ "where imovel.id = :idImovel and " + "mt.id = :idMedicaoTipo";
			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setInteger("idMedicaoTipo",
							MedicaoTipo.LIGACAO_AGUA.intValue()).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	public Collection pesquisarDadosHidrometroTipoPoco(Integer idImovel) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select hm.codigoHidrometroMarca,"
							+ // 0
							" hid.numero,"
							+ // 1
							" hc.codigoHidrometroCapacidade,"
							+ // 2
							" hli.id,"
							+ // 3
							" hih.dataInstalacao,"
							+ // 4
							" hp.id,"
							+ // 5
							" mt.id, "
							+ // 6
							" hid.numeroDigitosLeitura, "
							+ // 7
							" hc.id, "
							+ // 8 Adicionado por Rodrigo 06/08/07
							" hm.id " // 9 Adicionado por Rodrigo 06/08/07
							+ "from Imovel imovel " + "inner join imovel.hidrometroInstalacaoHistorico hih "
							+ "left join hih.hidrometroLocalInstalacao hli " + "left join hih.hidrometro hid "
							+ "left join hid.hidrometroMarca hm " + "left join hid.hidrometroCapacidade hc "
							+ "left join hih.hidrometroProtecao hp " + "left join hih.hidrometroLocalInstalacao hli "
							+ "left join hih.medicaoTipo mt " + "where imovel.id = :idImovel and " + "mt.id = :idMedicaoTipo";
			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setInteger("idMedicaoTipo",
							MedicaoTipo.POCO.intValue()).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;

	}

	public Collection pesquisarLeituraAnteriorTipoLigacaoAgua(Integer idImovel, Integer anoMes) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select md.leituraAtualFaturamento, "
							+ // 0
							"leituraSituacaoAtual.id, "
							+ // 1
							"md.id, "
							+ // 2
							"md.leituraAnteriorInformada,"
							+ // 3
							"md.leituraAtualInformada "// 4
							+ "from MedicaoHistorico md " + "left join md.leituraSituacaoAtual leituraSituacaoAtual "
							+ "where md.ligacaoAgua.id = :idImovel and " + "md.medicaoTipo.id = :idMedicaoTipo and "
							+ "md.anoMesReferencia = :anoMes ";

			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setInteger("idMedicaoTipo",
							MedicaoTipo.LIGACAO_AGUA.intValue()).setInteger("anoMes", anoMes.intValue()).setMaxResults(1).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Collection pesquisarLeituraAnteriorTipoPoco(Integer idImovel, Integer anoMes) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select md.leituraAtualFaturamento, "
							+ // 0
							"leituraSituacaoAtual.id, "
							+ // 1
							"md.id, "
							+ // 2
							"md.leituraAnteriorInformada,"
							+ // 3
							"md.leituraAtualInformada "// 4
							+ "from MedicaoHistorico md " + "left join md.leituraSituacaoAtual leituraSituacaoAtual "
							+ "where md.imovel.id = :idImovel and " + "md.medicaoTipo.id = :idMedicaoTipo and "
							+ "md.anoMesReferencia = :anoMes ";

			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setInteger("idMedicaoTipo",
							MedicaoTipo.POCO.intValue()).setMaxResults(1).setInteger("anoMes", anoMes.intValue()).setMaxResults(1).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Collection pesquisarIdConsumoTarifaCategoria(Integer idImovel) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select consumoTarifa.id,"
							+ // 0
							"categoria.id " // 1
							+ "from ImovelSubcategoria imovelSubcategoria " + "inner join imovelSubcategoria.comp_id.imovel imovel "
							+ "inner join imovel.consumoTarifa consumoTarifa "
							+ "inner join imovelSubcategoria.comp_id.subcategoria.categoria categoria " + "where imovel.id = :idImovel "
							+ "order by imovelSubcategoria.quantidadeEconomias desc ";

			retorno = session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setMaxResults(1).list();
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer pesquisarMaiorDataVigencia(Integer idConsumoTarifa) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select consumoTarifaVigencia.id " + "from ConsumoTarifaVigencia consumoTarifaVigencia "
							+ "inner join consumoTarifaVigencia.consumoTarifa consumoTarifa "
							+ "where consumoTarifa.id = :idConsumoTarifa " + "order by consumoTarifaVigencia.dataVigencia desc ";

			retorno = (Integer) session.createQuery(consulta).setInteger("idConsumoTarifa", idConsumoTarifa.intValue()).setMaxResults(1)
							.uniqueResult();
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer pesquisarConsumoMinimoTarifaCategoria(Integer idConsumoTarifaVigencia, Integer idCategoria)
					throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select consumoTarifaCategoria.numeroConsumoMinimo " + "from ConsumoTarifaCategoria consumoTarifaCategoria "
							+ "inner join consumoTarifaCategoria.consumoTarifaVigencia consumoTarifaVigencia "
							+ "inner join consumoTarifaVigencia.consumoTarifa consumoTarifa "
							+ "inner join consumoTarifaCategoria.categoria categoria "
							+ "where consumoTarifaVigencia.id = :idConsumoTarifaVigencia and categoria.id = :idCategoria ";

			retorno = (Integer) session.createQuery(consulta).setInteger("idConsumoTarifaVigencia", idConsumoTarifaVigencia.intValue())
							.setInteger("idCategoria", idCategoria.intValue()).setMaxResults(1).uniqueResult();
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer verificarExistenciaHidrometroInstalacaoHistoricoTipoAgua(Integer idImovel) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select hih.id " + "from HidrometroInstalacaoHistorico hih " + "where hih.ligacaoAgua.id = :idImovel and "
							+ "hih.medicaoTipo.id = :idMedicaoTipo ";

			retorno = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setInteger("idMedicaoTipo",
							MedicaoTipo.LIGACAO_AGUA.intValue()).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	public Integer verificarExistenciaHidrometroInstalacaoHistoricoTipoPoco(Integer idImovel) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select hih.id " + "from HidrometroInstalacaoHistorico hih " + "where hih.imovel.id = :idImovel and "
							+ "hih.medicaoTipo.id = :idMedicaoTipo ";

			retorno = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setInteger("idMedicaoTipo",
							MedicaoTipo.POCO.intValue()).setMaxResults(1).uniqueResult();

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
	 * @date 19/11/2008
	 *       Alteração no método para adicionar dados do hidrômetro na consulta.
	 */
	public MedicaoHistorico pesquisarMedicaoHistoricoTipoAgua(Integer idImovel, Integer anoMes) throws ErroRepositorioException{

		MedicaoHistorico retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select mh " + "from MedicaoHistorico mh " + "left join fetch mh.ligacaoAgua la "
							+ "left join fetch la.hidrometroInstalacaoHistorico hih " + "left join fetch hih.hidrometro hid "
							+ "left join fetch hih.hidrometroLocalInstalacao hiLocalInst " + "left join fetch hid.hidrometroMarca him "
							+ "left join fetch hid.hidrometroCapacidade hic "
							+ "where la.id = :idImovel and "
							+ "mh.medicaoTipo.id = :idMedicaoTipo and " + "mh.anoMesReferencia = :anoMesReferencia";

			retorno = (MedicaoHistorico) session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setInteger(
							"idMedicaoTipo", MedicaoTipo.LIGACAO_AGUA.intValue()).setInteger("anoMesReferencia", anoMes.intValue())
							.setMaxResults(1).uniqueResult();

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
	 * @date 19/11/2008
	 *       Alteração no método para adicionar dados do hidrômetro na consulta.
	 */
	public MedicaoHistorico pesquisarMedicaoHistoricoTipoPoco(Integer idImovel, Integer anoMes) throws ErroRepositorioException{

		MedicaoHistorico retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select mh " + "from MedicaoHistorico mh " + "left join fetch mh.imovel imovel "
							+ "left join fetch imovel.hidrometroInstalacaoHistorico hih " + "left join fetch hih.hidrometro hidrometro "
							+ "where imovel.id = :idImovel and " + "mh.medicaoTipo.id = :idMedicaoTipo and "
							+ "mh.anoMesReferencia = :anoMesReferencia";

			retorno = (MedicaoHistorico) session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setInteger(
							"idMedicaoTipo", MedicaoTipo.POCO.intValue()).setInteger("anoMesReferencia", anoMes.intValue())
							.setMaxResults(1).uniqueResult();

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
	 * [UC0082] - Registrar Leituras e Anormalidades Autor: Sávio Luiz Data:
	 * 04/01/2006
	 */

	public Integer verificarExistenciaMedicaoTipo(Integer idMedicaoTipo) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select mt.id " + "from MedicaoTipo mt " + "where mt.id = :idMedicaoTipo";

			retorno = (Integer) session.createQuery(consulta).setInteger("idMedicaoTipo", idMedicaoTipo.intValue()).setMaxResults(1)
							.uniqueResult();

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
	 * [UC0082] - Registrar Leituras e Anormalidades Autor: Sávio Luiz Data:
	 * 04/01/2006
	 */

	public Object[] pesquisarLeituraAnormalidade(Integer idLeituraAnormalidade) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select la.id,la.indicadorImovelSemHidrometro " + "from LeituraAnormalidade la "
							+ "where la.id = :idLeituraAnormalidade";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idLeituraAnormalidade", idLeituraAnormalidade.intValue())
							.setMaxResults(1).uniqueResult();

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
	 * [UC0082] - Registrar Leituras e Anormalidades Autor: Sávio Luiz Data:
	 * 04/01/2006
	 */

	public Object[] pesquisarMedicaoHistoricoAnteriorTipoPoco(Integer idImovel, Integer anoMes) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select md.dataLeituraAtualFaturamento, "
							+ // 0
							"md.leituraAtualFaturamento, "
							+ // 1
							"md.leituraAtualInformada,"
							+ // 2
							"leituraSituacaoAtual.id "// 3
							+ "from MedicaoHistorico md	" + "left join md.leituraSituacaoAtual leituraSituacaoAtual "
							+ "where md.imovel.id = :idImovel and " + "md.medicaoTipo.id = :idMedicaoTipo and "
							+ "md.anoMesReferencia = :anoMes ";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setInteger("idMedicaoTipo",
							MedicaoTipo.POCO.intValue()).setInteger("anoMes", anoMes.intValue()).setMaxResults(1).uniqueResult();

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
	 * [UC0082] - Registrar Leituras e Anormalidades Autor: Sávio Luiz Data:
	 * 04/01/2006
	 */

	public Date pesquisarDataInstalacaoHidrometro(Integer idHidrometroInstalacaoHistorico) throws ErroRepositorioException{

		Date retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select hih.dataInstalacao " + "from HidrometroInstalacaoHistorico hih "
							+ "where hih.id = :idHidrometroInstalacaoHistorico ";

			retorno = (Date) session.createQuery(consulta).setInteger("idHidrometroInstalacaoHistorico",
							idHidrometroInstalacaoHistorico.intValue()).setMaxResults(1).uniqueResult();

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
	 * [UC0082] - Registrar Leituras e Anormalidades Autor: Sávio Luiz Data:
	 * 04/01/2006
	 */

	public void inserirAtualizarMedicaoHistorico(Collection medicoesHistoricosParaRegistrar) throws ErroRepositorioException{

		StatelessSession session = HibernateUtil.getStatelessSession();

		Iterator iteratorMedicaoHistoricoParaImportar = medicoesHistoricosParaRegistrar.iterator();

		try{

			while(iteratorMedicaoHistoricoParaImportar.hasNext()){
				MedicaoHistorico medicaoHistorico = (MedicaoHistorico) iteratorMedicaoHistoricoParaImportar.next();

				// session.setFlushMode(FlushMode.COMMIT);
				// session.merge(medicaoHistorico);
				if(medicaoHistorico.getId() == null){
					session.insert(medicaoHistorico);

				}else{
					session.update(medicaoHistorico);

				}

				/*
				 * if (i % 50 == 0) { // 50, same as the JDBC batch size //
				 * flush a batch of inserts and release memory: session.flush();
				 * session.clear(); }
				 */
			}
			// session.flush();
			// session.clear();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
			// session.close();
		}

	}

	/**
	 * Consultar Dados do Cliente Imovel Vinculado Auhtor: Rafael Santos Data:
	 * 23/01/2006
	 * 
	 * @param inscricaoImovel
	 *            Inscrição do Imovel
	 * @return Dados do Imovel Vinculado
	 * @throws ControladorException
	 */
	public Object[] consultarDadosClienteImovelUsuario(Imovel imovel) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select cliente.id,cliente.nome " + "from ClienteImovel clienteImovel "
							+ "inner join clienteImovel.cliente cliente " + "inner join clienteImovel.imovel imovel "
							+ "inner join clienteImovel.clienteRelacaoTipo clienteRelacaoTipo "
							+ "where imovel.id = :id and clienteRelacaoTipo.id = :usuario "
							+ "and clienteImovel.clienteImovelFimRelacaoMotivo is null";

			retorno = (Object[]) session.createQuery(consulta).setInteger("id", imovel.getId().intValue()).setInteger("usuario",
							ClienteRelacaoTipo.USUARIO).setMaxResults(1).uniqueResult();

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
	 * Consultar Matriculas dos Imoveis Vinculados do Imovel condominio Auhtor:
	 * Rafael Santos Data: 23/01/2006 [UC0179] Consultar Historico Medição
	 * Indiviualizada
	 * 
	 * @param consumoHistorico
	 *            Consumo Historico
	 * @return
	 * @throws ControladorException
	 */
	public Collection consultarConsumoHistoricoImoveisVinculados(ConsumoHistorico consumoHistorico) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select imovel.id " + "from ConsumoHistorico consumo " + "inner join consumo.imovel imovel "
							+ "where consumo.consumoImovelCondominio = :id";

			retorno = session.createQuery(consulta).setInteger("id", consumoHistorico.getId().intValue()).list();

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
	 * Consultar Consumo Tipo do Consumo Historico Auhtor: Rafael Santos Data:
	 * 23/01/2006
	 * 
	 * @param consumoHistorico
	 *            Consumo Historico
	 * @return Dados do Consumo Tipo
	 * @throws ControladorException
	 */
	public Object[] consultarDadosConsumoTipoConsumoHistorico(ConsumoHistorico consumoHistorico) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select consumo.descricaoAbreviada,consumo.id " + "from ConsumoTipo consumo " + "where consumo.id = :id";

			retorno = (Object[]) session.createQuery(consulta).setInteger("id", consumoHistorico.getConsumoTipo().getId().intValue())
							.setMaxResults(1).uniqueResult();

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
	 * Consultar Consumo Historico da Medicao Individualizada Auhtor: Rafael
	 * Santos Data: 23/01/2006 [UC0179] Consultar Historico Medição
	 * Indiviualizada
	 * 
	 * @param imovel
	 *            Imovel
	 * @param ligcaoTipo
	 *            Tipo de Ligacação
	 * @param anoMesFaturamento
	 *            Ano Mes Faturamento
	 * @return
	 * @throws ControladorException
	 */
	public Object[] obterConsumoHistoricoMedicaoIndividualizada(Imovel imovel, LigacaoTipo ligacaoTipo, int anoMesFaturamento)
					throws ErroRepositorioException{

		Object[] retornoDados = null;
		Object retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select ch.id,ch.consumoRateio, "
							+ "ch.numeroConsumoFaturadoMes,consumoTipo.id,ch.indicadorFaturamento,consumoAnormalidade.id "
							+ "from ConsumoHistorico ch " + "left join ch.consumoAnormalidade consumoAnormalidade "
							+ "left join ch.consumoTipo consumoTipo " + "where  " + "ch.imovel.id = :id  "
							+ "and ch.referenciaFaturamento = :anoMes " + "and ch.ligacaoTipo.id = :ligacao";

			retorno = session.createQuery(consulta).setInteger("id", imovel.getId().intValue()).setInteger("anoMes", anoMesFaturamento)
							.setInteger("ligacao", ligacaoTipo.getId()).setMaxResults(1).uniqueResult();

			if(retorno != null){
				retornoDados = (Object[]) retorno;
			}

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retornoDados;

	}

	/**
	 * Pesquisa o imóvel condomínio com a situação de ligação de água (ligado ou
	 * cortado) e a situação da ligação de esgoto (ligado) [UC0103] Efetuar
	 * Rateio de Consumo Autor: Leonardo Vieira Data: 17/02/2006
	 */

	public Collection pesquisarImovelCondominio(Integer indicadorImovelCondominio, Integer ligacaoAguaSituacaoLigado,
					Integer ligacaoAguaSituacaoCortado, Integer ligacaoEsgotoSituacaoCortado) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{
			consulta = " select im from Imovel im " + "inner join im.ligacaoAguaSituacao las " + "inner join im.ligacaoEsgotoSituacao les "
							+ " where im.indicadorImovelCondominio = indicadorImovelCondominio "
							+ " and (las.id = ligado or las.id = cortado)  "
							+ " or (les.id = 3 and im.hidrometroInstalacaoHistorico != null) ";

			retorno = session.createQuery(consulta).setInteger("indicadorImovelCondominio", indicadorImovelCondominio.intValue())
							.setInteger("ligacaoAguaSituacaoLigado", ligacaoAguaSituacaoLigado.intValue()).setInteger(
											"ligacaoAguaSituacaoCortado", ligacaoAguaSituacaoCortado.intValue()).setInteger(
											"ligacaoEsgotoSituacaoCortado", ligacaoEsgotoSituacaoCortado.intValue()).list();

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
	 * Consultar Consumo Historico da Medicao Individualizada [UC0113] Faturar
	 * Grupo Faturamento Auhtor: Rafael Santos Data: 20/02/2006
	 * 
	 * @param idImovel
	 * @param idAnormalidade
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterConsumoHistoricoAnormalidade(Integer idImovel, Integer idAnormalidade, int anoMes) throws ErroRepositorioException{

		Object[] retornoDados = null;
		Object retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select ch.id " + "from ConsumoHistorico ch " + "inner join ch.consumoAnormalidade where " + "ch.imovel.id = :id  "
							+ "and ch.referenciaFaturamento = :anoMes " + " and consumoAnormalidade.id = :idAnormalidade "
							+ "and ch.ligacaoTipo.id = :ligacao";

			retorno = session.createQuery(consulta).setInteger("id", idImovel.intValue()).setInteger("anoMes", anoMes).setInteger(
							"idAnormalidade", idAnormalidade.intValue()).setMaxResults(1).uniqueResult();

			if(retorno != null){
				retornoDados = (Object[]) retorno;
			}

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retornoDados;

	}

	/**
	 * Consultar Imoveis com Medição Indiviualizada Auhtor: Sávio Luiz Data:
	 * 06/02/2006 [UC0180] Consultar Imoveis com Medição Indiviualizada
	 */

	public String pesquisarDescricaoRateioTipoLigacaoAgua(Integer idImovel) throws ErroRepositorioException{

		String retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select rt.descricao " + "from HidrometroInstalacaoHistorico hih " + "inner join hih.rateioTipo rt "
							+ "inner join hih.ligacaoAgua la " + "where la.id = :idImovel";

			retorno = (String) session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setMaxResults(1).uniqueResult();

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
	 * Consultar Imoveis com Medição Indiviualizada Auhtor: Sávio Luiz Data:
	 * 06/02/2006 [UC0180] Consultar Imoveis com Medição Indiviualizada
	 */

	public String pesquisarDescricaoRateioTipoLigacaoEsgoto(Integer idImovel) throws ErroRepositorioException{

		String retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select rt.descricao " + "from HidrometroInstalacaoHistorico hih " + "inner join hih.rateioTipo rt "
							+ "inner join hih.imovel imov " + "where imov.id = :idImovel";

			retorno = (String) session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setMaxResults(1).uniqueResult();

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
	 * // esse método realizar a consulta do CASO DE USO - ANALISAR EXECOES DE
	 * // LEITURAS E CONSUMOS
	 * Correçao de Consulta SQL com coluna com mais de 30 letras
	 * Andre Nishimura
	 */
	public Collection pesquisarImovelExcecoesLeituras(FiltroMedicaoHistoricoSql filtroMedicaoHistoricoSql, Integer numeroPagina,
					boolean todosRegistros, Integer anoMesReferencia) throws ErroRepositorioException{

		Collection retorno = null;

		Collection medicaohistoricoParametros = filtroMedicaoHistoricoSql.getParametros();

		Session session = HibernateUtil.getSession();

		String sql = "";

		if(!medicaohistoricoParametros.isEmpty() && medicaohistoricoParametros.size() >= 1){

			sql = "select  distinct(imovel.imov_id) 								as idImovel,"// 0
							+ " imovel.loca_id 												as idLocalidade, " // 01
							+ " setorComercial.stcm_cdsetorcomercial 						as codigoSetorComercial, "// 02
							+ " quadra.qdra_nnquadra 										as numeroQuadra, " // 03
							+ " imovel.imov_nnlote 											as lote, " // 04
							+ " imovel.imov_nnsublote 										as subLote,"// 05
							+ " imovelPerfil.iper_dsimovelperfil 							as dsImovelPerfil, " // 06
							+ " medicaoTipo.medt_dsmedicaotipo 								as dsMedicaoTipo," // 07
							+ " consumoHistorico.cshi_nnconsumofaturadomes 					as numeroConsumoFaturadoMes,"// 08
							+ " leituraAnormalidadeFaturamento.ltan_dsleituraanormalidade 	as dsLeituraAnormFaturamento,"// 09
							+ " consumoAnormalidade.csan_dsconsumoanormalidade 				as dsConsumoAnormalidade," // 10
							+ " medicaoTipo.medt_id 										as idMedicaoTipo,"// 11
							+ " rota.rota_cdrota 									    	as cdRota"// 12
							+ " from imovel imovel"
							+ " inner join consumo_historico consumoHistorico on imovel.imov_id=consumoHistorico.imov_id and consumoHistorico.cshi_amfaturamento = "
							+ anoMesReferencia
							+ " and consumoHistorico.lgti_id = "
							+ LigacaoTipo.LIGACAO_AGUA
							+ " inner join medicao_historico medicaoHistorico on  consumoHistorico.imov_id=medicaoHistorico.lagu_id and medicaoHistorico.mdhi_amleitura = "
							+ anoMesReferencia
							+ " and medicaoHistorico.medt_id = "
							+ LigacaoTipo.LIGACAO_AGUA
							+ " left outer join imovel_subcategoria imovelSubcategoria on imovel.imov_id=imovelSubcategoria.imov_id  "
							+ " left outer join quadra quadra on imovel.qdra_id=quadra.qdra_id"
							+ " left outer join setor_comercial setorComercial on imovel.stcm_id=setorComercial.stcm_id "
							+ " left outer join rota rota on imovel.rota_id=rota.rota_id"
							+ " left outer join faturamento_grupo faturamentoGrupo on rota.ftgr_id=faturamentoGrupo.ftgr_id  left outer join empresa empresa on rota.empr_id=empresa.empr_id"
							+ " left outer join imovel_perfil imovelPerfil on imovel.iper_id=imovelPerfil.iper_id"
							+ " left outer join subcategoria subcategoria on imovelSubcategoria.scat_id=subcategoria.scat_id"
							+ " left outer join consumo_anormalidade consumoAnormalidade on consumoHistorico.csan_id=consumoAnormalidade.csan_id"
							+ " left outer join categoria categoria on subcategoria.catg_id=categoria.catg_id"
							+ " left outer join medicao_tipo medicaoTipo on medicaoHistorico.medt_id=medicaoTipo.medt_id"
							+ " left outer join leitura_anormalidade leituraAnormalidadeFaturamento on medicaoHistorico.ltan_idleituraanormalidadefatu=leituraAnormalidadeFaturamento.ltan_id"
							+ " where ";

			String sqlSegundaParte = " select  distinct(imovel.imov_id) 			as idImovel,"
							+ " imovel.loca_id 												as idLocalidade, "
							+ " setorComercial.stcm_cdsetorcomercial 						as codigoSetorComercial,"
							+ " quadra.qdra_nnquadra 										as numeroQuadra, "
							+ " imovel.imov_nnlote 											as lote, "
							+ " imovel.imov_nnsublote 										as subLote,"
							+ " imovelPerfil.iper_dsimovelperfil 							as dsImovelPerfil, "
							+ " medicaoTipo.medt_dsmedicaotipo 								as dsMedicaoTipo,"
							+ " consumoHistorico.cshi_nnconsumofaturadomes 					as numeroConsumoFaturadoMes,"
							+ " leituraAnormalidadeFaturamento.ltan_dsleituraanormalidade 	as dsLeituraAnormFaturamento,"
							+ " consumoAnormalidade.csan_dsconsumoanormalidade 				as dsConsumoAnormalidade,"
							+ " medicaoTipo.medt_id 										as idMedicaoTipo,"
							+ " rota.rota_cdrota 									    	as cdRota"// 12
							+ " from imovel imovel"
							+ " inner join consumo_historico consumoHistorico on imovel.imov_id=consumoHistorico.imov_id and consumoHistorico.cshi_amfaturamento =  "
							+ anoMesReferencia
							+ "  and consumoHistorico.lgti_id = "
							+ LigacaoTipo.LIGACAO_ESGOTO
							+ " inner join medicao_historico medicaoHistorico on  consumoHistorico.imov_id=medicaoHistorico.imov_id and medicaoHistorico.mdhi_amleitura =  "
							+ anoMesReferencia
							+ " and medicaoHistorico.medt_id = "
							+ LigacaoTipo.LIGACAO_ESGOTO
							+ " left outer join imovel_subcategoria imovelSubcategoria on imovel.imov_id=imovelSubcategoria.imov_id  left outer join quadra quadra on imovel.qdra_id=quadra.qdra_id "
							+ " left outer join setor_comercial setorComercial on imovel.stcm_id=setorComercial.stcm_id  "
							+ " left outer join rota rota on imovel.rota_id=rota.rota_id"
							+ " left outer join faturamento_grupo faturamentoGrupo on rota.ftgr_id=faturamentoGrupo.ftgr_id  left outer join empresa empresa on rota.empr_id=empresa.empr_id"
							+ " left outer join imovel_perfil imovelPerfil on imovel.iper_id=imovelPerfil.iper_id"
							+ " left outer join subcategoria subcategoria on imovelSubcategoria.scat_id=subcategoria.scat_id"
							+ " left outer join consumo_anormalidade consumoAnormalidade on consumoHistorico.csan_id=consumoAnormalidade.csan_id"
							+ " left outer join categoria categoria on subcategoria.catg_id=categoria.catg_id"
							+ " left outer join medicao_tipo medicaoTipo on medicaoHistorico.medt_id=medicaoTipo.medt_id"
							+ " left outer join leitura_anormalidade leituraAnormalidadeFaturamento on medicaoHistorico.ltan_idleituraanormalidadefatu=leituraAnormalidadeFaturamento.ltan_id"
							+ " where ";

			Iterator iteratorImovelSub = medicaohistoricoParametros.iterator();
			while(iteratorImovelSub.hasNext()){
				FiltroParametro filtroParametro = (FiltroParametro) iteratorImovelSub.next();

				if(filtroParametro instanceof Intervalo){

					Intervalo intervalo = ((Intervalo) filtroParametro);

					sql = sql + " (quadra.qdra_nnquadra between " + intervalo.getIntervaloInicial() + " and "
									+ intervalo.getIntervaloFinal() + ") and ";

					sqlSegundaParte = sqlSegundaParte + " (quadra.qdra_nnquadra between " + intervalo.getIntervaloInicial() + " and "
									+ intervalo.getIntervaloFinal() + ") and ";

				}

				if(filtroParametro instanceof ParametroSimples){
					ParametroSimples parametroSimples = ((ParametroSimples) filtroParametro);

					if(parametroSimples.getNomeAtributo().trim().equalsIgnoreCase(FiltroMedicaoHistoricoSql.IMOVEL_CONDOMINIO_ID)){

						sql = sql + " (" + parametroSimples.getNomeAtributo() + " = " + parametroSimples.getValor() + " and ";

						sql = Util.formatarHQL(sql, 4);
						sql = sql + " or imovel.imov_id " + " = " + parametroSimples.getValor() + ") and ";

						sqlSegundaParte = sqlSegundaParte + " (" + parametroSimples.getNomeAtributo() + " = " + parametroSimples.getValor()
										+ " and ";

						sqlSegundaParte = Util.formatarHQL(sqlSegundaParte, 4);
						sqlSegundaParte = sqlSegundaParte + " or imovel.imov_id " + " = " + parametroSimples.getValor() + ") and ";

					}else{
						sql = sql + parametroSimples.getNomeAtributo() + " = " + parametroSimples.getValor() + " and ";

						sqlSegundaParte = sqlSegundaParte + parametroSimples.getNomeAtributo() + " = " + parametroSimples.getValor()
										+ " and ";
					}
				}

				if(filtroParametro instanceof ParametroNaoNulo){
					ParametroNaoNulo parametroSimples = ((ParametroNaoNulo) filtroParametro);

					sql = sql + " (" + parametroSimples.getNomeAtributo() + " is not null) and ";

					sqlSegundaParte = sqlSegundaParte + " (" + parametroSimples.getNomeAtributo() + " is not null) and ";

				}

				if(filtroParametro instanceof MaiorQue){
					MaiorQue parametroSimples = ((MaiorQue) filtroParametro);

					sql = sql + " (" + parametroSimples.getNomeAtributo() + " >= " + parametroSimples.getNumero() + ") and ";

					sqlSegundaParte = sqlSegundaParte + " (" + parametroSimples.getNomeAtributo() + " >= " + parametroSimples.getNumero()
									+ ") and ";
				}

				// testa para colocar a anomes no consumo historico

			}

			sql = Util.formatarHQL(sql, 4);
			// System.out.println(sql);

			sqlSegundaParte = Util.formatarHQL(sqlSegundaParte, 4);
			// System.out.println(sqlSegundaParte);

			sql = sql + " UNION " + sqlSegundaParte + " order by idImovel,cdRota,numeroQuadra";
			// System.out.println(sql);
		}
		try{
			if(todosRegistros){
				retorno = session.createSQLQuery(sql).addScalar("idImovel", Hibernate.INTEGER).addScalar("idLocalidade", Hibernate.INTEGER)
								.addScalar("codigoSetorComercial", Hibernate.INTEGER).addScalar("numeroQuadra", Hibernate.INTEGER)
								.addScalar("lote", Hibernate.SHORT).addScalar("subLote", Hibernate.SHORT).addScalar("dsImovelPerfil",
												Hibernate.STRING).addScalar("dsMedicaoTipo", Hibernate.STRING).addScalar(
												"numeroConsumoFaturadoMes", Hibernate.INTEGER).addScalar("dsLeituraAnormFaturamento",
												Hibernate.STRING).addScalar("dsConsumoAnormalidade", Hibernate.STRING).addScalar(
												"idMedicaoTipo", Hibernate.INTEGER).addScalar("cdRota", Hibernate.INTEGER).list();
			}else{
				List list = session.createSQLQuery(sql).addScalar("idImovel", Hibernate.INTEGER).addScalar("idLocalidade",
								Hibernate.INTEGER).addScalar("codigoSetorComercial", Hibernate.INTEGER).addScalar("numeroQuadra",
								Hibernate.INTEGER).addScalar("lote", Hibernate.SHORT).addScalar("subLote", Hibernate.SHORT).addScalar(
								"dsImovelPerfil", Hibernate.STRING).addScalar("dsMedicaoTipo", Hibernate.STRING).addScalar(
								"numeroConsumoFaturadoMes", Hibernate.INTEGER).addScalar("dsLeituraAnormFaturamento", Hibernate.STRING)
								.addScalar("dsConsumoAnormalidade", Hibernate.STRING).addScalar("idMedicaoTipo", Hibernate.INTEGER)
								.addScalar("cdRota", Hibernate.INTEGER).setFirstResult(10 * numeroPagina).list();
				/* Alternativa para paginaçao devido á um bug no metodo setMaxResult do hibernate */
				if(list.size() > 10){
					retorno = list.subList(0, 10);
				}else{
					retorno = list;
				}
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
	 * Realiza a consulta do CASO DE USO -ANALISAR EXECOES DE LEITURAS E CONSUMOS
	 * 
	 * @author anishimura
	 * @updated 18 de Novembro de 2008
	 *          Correção de alias no sql
	 */
	public Integer pesquisarImovelExcecoesLeiturasCount(FiltroMedicaoHistoricoSql filtroMedicaoHistoricoSql, Integer anoMesReferencia)
					throws ErroRepositorioException{

		Integer retorno = null;

		Collection medicaohistoricoParametros = filtroMedicaoHistoricoSql.getParametros();

		Session session = HibernateUtil.getSession();

		String sql = "";

		if(!medicaohistoricoParametros.isEmpty() && medicaohistoricoParametros.size() >= 1){

			sql = "select  count(distinct(imovel.imov_id)) as count"
							+ " from imovel imovel"
							+ " inner join consumo_historico consumoHistorico on imovel.imov_id=consumoHistorico.imov_id and consumoHistorico.cshi_amfaturamento = "
							+ anoMesReferencia
							+ " and consumoHistorico.lgti_id = "
							+ LigacaoTipo.LIGACAO_AGUA
							+ " inner join medicao_historico medicaoHistorico on  consumoHistorico.imov_id=medicaoHistorico.lagu_id and medicaoHistorico.mdhi_amleitura = "
							+ anoMesReferencia
							+ " and medicaoHistorico.medt_id = "
							+ LigacaoTipo.LIGACAO_AGUA
							+ " left outer join imovel_subcategoria imovelSubcategoria on imovel.imov_id=imovelSubcategoria.imov_id  left outer join quadra quadra on imovel.qdra_id=quadra.qdra_id"
							+ " left outer join setor_comercial setorComercial on imovel.stcm_id=setorComercial.stcm_id  left outer join rota rota on imovel.rota_id=rota.rota_id"
							+ " left outer join faturamento_grupo faturamentoGrupo on rota.ftgr_id=faturamentoGrupo.ftgr_id  left outer join empresa empresa on rota.empr_id=empresa.empr_id"
							+ " left outer join imovel_perfil imovelPerfil on imovel.iper_id=imovelPerfil.iper_id"
							+ " left outer join subcategoria subcategoria on imovelSubcategoria.scat_id=subcategoria.scat_id"
							+ " left outer join consumo_anormalidade consumoAnormalidade on consumoHistorico.csan_id=consumoAnormalidade.csan_id"
							+ " left outer join categoria categoria on subcategoria.catg_id=categoria.catg_id"
							+ " left outer join medicao_tipo medicaoTipo on medicaoHistorico.medt_id=medicaoTipo.medt_id"
							+ " left outer join leitura_anormalidade leituraAnormalidadeFaturamento on medicaoHistorico.ltan_idleituraanormalidadefatu=leituraAnormalidadeFaturamento.ltan_id"
							+ " where ";

			String sqlSegundaParte = " select  count(distinct(imovel.imov_id)) as count"
							+ " from imovel imovel"
							+ " inner join consumo_historico consumoHistorico on imovel.imov_id=consumoHistorico.imov_id and consumoHistorico.cshi_amfaturamento =  "
							+ anoMesReferencia
							+ "  and consumoHistorico.lgti_id = "
							+ LigacaoTipo.LIGACAO_ESGOTO
							+ " inner join medicao_historico medicaoHistorico on  consumoHistorico.imov_id=medicaoHistorico.imov_id and medicaoHistorico.mdhi_amleitura =  "
							+ anoMesReferencia
							+ " and medicaoHistorico.medt_id = "
							+ LigacaoTipo.LIGACAO_ESGOTO
							+ " left outer join imovel_subcategoria imovelSubcategoria on imovel.imov_id=imovelSubcategoria.imov_id  left outer join quadra quadra on imovel.qdra_id=quadra.qdra_id "
							+ " left outer join setor_comercial setorComercial on imovel.stcm_id=setorComercial.stcm_id  left outer join rota rota on imovel.rota_id=rota.rota_id"
							+ " left outer join faturamento_grupo faturamentoGrupo on rota.ftgr_id=faturamentoGrupo.ftgr_id  left outer join empresa empresa on rota.empr_id=empresa.empr_id"
							+ " left outer join imovel_perfil imovelPerfil on imovel.iper_id=imovelPerfil.iper_id"
							+ " left outer join subcategoria subcategoria on imovelSubcategoria.scat_id=subcategoria.scat_id"
							+ " left outer join consumo_anormalidade consumoAnormalidade on consumoHistorico.csan_id=consumoAnormalidade.csan_id"
							+ " left outer join categoria categoria on subcategoria.catg_id=categoria.catg_id"
							+ " left outer join medicao_tipo medicaoTipo on medicaoHistorico.medt_id=medicaoTipo.medt_id"
							+ " left outer join leitura_anormalidade leituraAnormalidadeFaturamento on medicaoHistorico.ltan_idleituraanormalidadefatu=leituraAnormalidadeFaturamento.ltan_id"
							+ " where ";

			Iterator iteratorImovelSub = medicaohistoricoParametros.iterator();
			while(iteratorImovelSub.hasNext()){
				FiltroParametro filtroParametro = (FiltroParametro) iteratorImovelSub.next();

				if(filtroParametro instanceof Intervalo){

					Intervalo intervalo = ((Intervalo) filtroParametro);

					sql = sql + " (quadra.qdra_nnquadra between " + intervalo.getIntervaloInicial() + " and "
									+ intervalo.getIntervaloFinal() + ") and ";

					sqlSegundaParte = sqlSegundaParte + " (quadra.qdra_nnquadra between " + intervalo.getIntervaloInicial() + " and "
									+ intervalo.getIntervaloFinal() + ") and ";

				}

				if(filtroParametro instanceof ParametroSimples){
					ParametroSimples parametroSimples = ((ParametroSimples) filtroParametro);

					if(parametroSimples.getNomeAtributo().trim().equalsIgnoreCase(FiltroMedicaoHistoricoSql.IMOVEL_CONDOMINIO_ID)){

						sql = sql + " (" + parametroSimples.getNomeAtributo() + " = " + parametroSimples.getValor() + " and ";

						sql = Util.formatarHQL(sql, 4);
						sql = sql + " or imovel.imov_id " + " = " + parametroSimples.getValor() + ") and ";

						sqlSegundaParte = sqlSegundaParte + " (" + parametroSimples.getNomeAtributo() + " = " + parametroSimples.getValor()
										+ " and ";

						sqlSegundaParte = Util.formatarHQL(sqlSegundaParte, 4);
						sqlSegundaParte = sqlSegundaParte + " or imovel.imov_id " + " = " + parametroSimples.getValor() + ") and ";

					}else{
						sql = sql + parametroSimples.getNomeAtributo() + " = " + parametroSimples.getValor() + " and ";

						sqlSegundaParte = sqlSegundaParte + parametroSimples.getNomeAtributo() + " = " + parametroSimples.getValor()
										+ " and ";
					}
				}

				if(filtroParametro instanceof ParametroNaoNulo){
					ParametroNaoNulo parametroSimples = ((ParametroNaoNulo) filtroParametro);

					sql = sql + " (" + parametroSimples.getNomeAtributo() + " is not null) and ";

					sqlSegundaParte = sqlSegundaParte + " (" + parametroSimples.getNomeAtributo() + " is not null) and ";

				}

				if(filtroParametro instanceof MaiorQue){
					MaiorQue parametroSimples = ((MaiorQue) filtroParametro);

					sql = sql + " (" + parametroSimples.getNomeAtributo() + " >= " + parametroSimples.getNumero() + ") and ";

					sqlSegundaParte = sqlSegundaParte + " (" + parametroSimples.getNomeAtributo() + " >= " + parametroSimples.getNumero()
									+ ") and ";
				}

				// testa para colocar a anomes no consumo historico

			}

			sql = Util.formatarHQL(sql, 4);
			// System.out.println(sql);

			sqlSegundaParte = Util.formatarHQL(sqlSegundaParte, 4);
			// System.out.println(sqlSegundaParte);

			sql = sql + " UNION " + sqlSegundaParte;
			// System.out.println(sql);
		}
		try{

			Collection resultado = session.createSQLQuery(sql).addScalar("count", Hibernate.INTEGER).list();

			int valor = 0;
			if(!resultado.isEmpty()){
				Iterator iterator = resultado.iterator();
				while(iterator.hasNext()){
					Integer valorUm = (Integer) iterator.next();
					valor = valor + valorUm.intValue();
				}
				retorno = new Integer(valor);

			}else{
				retorno = new Integer(0);
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
	 * Método que apresenta os dados do imovel
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 * 
	 * @author Sávio Luiz
	 * @date 04/08/2006
	 * @param idImovel
	 * @return Collection
	 */
	public Collection pesquiarImovelExcecoesApresentaDados(Integer idImovel, boolean ligacaoAgua) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		Collection retorno = null;

		String ligacaoHidrometro = "";
		if(ligacaoAgua){
			ligacaoHidrometro = " ligacaoAgua.hidi_id = hidrometroIstalacaoHistorico.hidi_id";
		}else{
			ligacaoHidrometro = " imovel.hidi_id = hidrometroIstalacaoHistorico.hidi_id";
		}

		String sql = "select faturamentoGrupo.ftgr_id as idFaturamentoGrupo,"// 0
						+ " faturamentoGrupo.ftgr_amreferencia as amFaturamentoGrupo,"// 1
						+ " empresa.empr_nmempresa as nomeEmpresa,"// 2
						+ " imovel.imov_icimovelcondominio as icCondominio,"// 3
						+ " ligacaoAguaSituacao.last_dsligacaoaguasituacao as descricaoLigAguaSit,"// 4
						+ " ligacaoEsgotoSituacao.lest_dsligacaoesgotosituacao as descricaoLigEsgSit,"// 5
						+ " cliente.clie_nmcliente as nomeCliente,"// 6
						+ " cliente.clie_nncpf as cpf,"// 7
						+ " cliente.clie_nncnpj as cnpj,"// 8
						+ " hidrometro.hidr_nnhidrometro as numerohidrometro,"// 9
						+ " hidrometroIstalacaoHistorico.hidi_dtinstalacaohidrometro as dtInstHidrometro,"// 10
						+ " hidrometroCapacidade.hicp_dshidrometrocapacidade as descricaoHidCapacidade,"// 11
						+ " hidrometroTipo.hitp_dshidrometrotipo as descricaoHidTipo,"// 12
						+ " hidrometroMarca.himc_dshidrometromarca as descricaoHidMarca,"// 13
						+ " hidrometroLocalInstalacao.hili_dshidrometrolocalinstalac as descricaoHidLocInst,"// 14
						+ " hidrometroDiametro.hidm_dshidrometrodiametro as descricaoHidDiamentro,"// 15
						+ " hidrometroProtecao.hipr_dshidrometroprotecao as descricaoHidProtecao,"// 16
						+ " hidrometroIstalacaoHistorico.hidi_iccavalete as descricaoIcCavalete,"// 17
						+ " hidrometro.hidr_nnanofabricacao as anoFabricacao,"// 18
						+ " imovelPerfil.iper_dsimovelperfil as descricaoImovelPerfil,"// 19
						+ " ligacaoAgua.lagu_dtligacaoagua as dataLigacaoAgua,"// 20
						+ " ligacaoAgua.lagu_dtcorte as dataCorte,"// 21
						+ " ligacaoAgua.lagu_dtreligacaoagua as dataReligAgua,"// 22
						+ " ligacaoAgua.lagu_dtsupressaoagua as dataSupAgua,"// 23
						+ " ligacaoAguaDiametro.lagd_dsligacaoaguadiametro as descricaoAguaDia,"// 24
						+ " ligacaoAguaMaterial.lagm_dsligacaoaguamaterial as descricaoLigAguaMat,"// 25
						+ " ligacaoAgua.lagu_nnconsumominimoagua as numeroConsumoMinAgua,"// 26
						+ " ligacaoEsgoto.lesg_dtligacao as dataLigacao,"// 27
						+ " ligacaoEsgotoDiametro.legd_dsligacaoesgotodiametro as descricaoLigEsgDia,"// 28
						+ " ligacaoEsgotoMaterial.legm_dsligacaoesgotomaterial as descricaoLigEsgMat,"// 29
						+ " ligacaoEsgotoPerfil.lepf_dsligacaoesgotoperfil as descricaoLigEsgPerfil,"// 30
						+ " ligacaoEsgoto.lesg_nnconsumominimoesgoto as numeroConsumoMinEsgo,"// 31
						+ " ligacaoEsgoto.lesg_pcesgoto as percentualEsgoto,"// 32
						+ " ligacaoEsgoto.lesg_pccoleta as percentualColeta,"// 33
						+ " pocoTipo.poco_dspocotipo as descricaoPocoTipo, "// 34
						+ " ligacaoAgua.lagu_dtrestabelecimentoagua as dataRestabelecimentoAgua,"// 35
						+ " ligacaoAguaPerfil.lapf_dsligacaoaguaperfil as descricaoLigacaoAguaPerfil,"// 36
						+ " ligacaoEsgoto.lesg_id as idLigacaoEsgoto, "// 37
						+ " ligacaoAgua.lagu_id as idLigacaoAgua,"// 38
						+ " imovel.last_id as idLigacaoAguaSituacao,"// 39
						+ " imovel.imov_idimovelcondominio as idImovelCondominio, "// 40
						+ " faturamentoGrupo.ftgr_nndiavencimento as diaVencimento, " // 41
						+ " rota.rota_cdrota as codigoRota, " // 42
						+ " imovel.imov_nnsequencialrota as sequencialRota, "// 43
						+ " hidrometro.hidr_id as idHidrometro, "// 44
						+ " ligacaoAgua.lagu_idfuncreligacaoagua as idFuncionarioReligacaoAgua, "// 45
						+ " funcionario.func_nmfuncionario as nomeFuncionarioReligacaoAgua, "// 46
						+ " hidrometroIstalacaoHistorico.hidi_nnleiturainstalacaohidrom as numeroLeituraInstalacao, "// 47
						+ " ramalLocalInstalacao.rlin_dsramallocalinstalcao as descricaoRamalLocalInstalacao "// 48
						+ " from cliente_imovel clienteImovel "
						+ " inner join imovel imovel on clienteImovel.imov_id=imovel.imov_id "
						+ "	left outer join ligacao_agua ligacaoAgua on imovel.imov_id=ligacaoAgua.lagu_id "
						+ " left outer join hidrometro_instalacao_hist hidrometroIstalacaoHistorico on "
						+ ligacaoHidrometro
						+ "	left outer join hidrometro_local_instalacao hidrometroLocalInstalacao on hidrometroIstalacaoHistorico.hili_id=hidrometroLocalInstalacao.hili_id "
						+ "	left outer join hidrometro hidrometro on hidrometroIstalacaoHistorico.hidr_id=hidrometro.hidr_id "
						+ "	left outer join hidrometro_capacidade hidrometroCapacidade on hidrometro.hicp_id=hidrometroCapacidade.hicp_id "
						+ "	left outer join hidrometro_tipo hidrometroTipo on hidrometro.hitp_id=hidrometroTipo.hitp_id "
						+ "	left outer join hidrometro_marca hidrometroMarca on hidrometro.himc_id=hidrometroMarca.himc_id "
						+ "	left outer join hidrometro_diametro hidrometroDiametro on hidrometro.hidm_id=hidrometroDiametro.hidm_id "
						+ "	left outer join hidrometro_protecao hidrometroProtecao on  hidrometroIstalacaoHistorico.hipr_id=hidrometroProtecao.hipr_id "
						+ "	inner join cliente cliente on clienteImovel.clie_id=cliente.clie_id "
						+ "	left outer join quadra quadra on imovel.qdra_id=quadra.qdra_id "
						+ "	left outer join rota rota on imovel.rota_id=rota.rota_id  "
						+ "	left outer join faturamento_grupo faturamentoGrupo on rota.ftgr_id=faturamentoGrupo.ftgr_id "
						+ "	left outer join empresa empresa on rota.empr_id=empresa.empr_id  "
						+ "	left outer join ligacao_agua_situacao ligacaoAguaSituacao on imovel.last_id=ligacaoAguaSituacao.last_id "
						+ "	left outer join ligacao_esgoto_situacao ligacaoEsgotoSituacao on  imovel.lest_id=ligacaoEsgotoSituacao.lest_id "
						+ "	left outer join imovel_perfil imovelPerfil on imovel.iper_id=imovelPerfil.iper_id "
						+ "	left outer join ligacao_agua_diametro ligacaoAguaDiametro on  ligacaoAgua.lagd_id=ligacaoAguaDiametro.lagd_id "
						+ "	left outer join ligacao_agua_material ligacaoAguaMaterial on ligacaoAgua.lagm_id=ligacaoAguaMaterial.lagm_id "
						+ "	left outer join ligacao_agua_perfil ligacaoAguaPerfil on ligacaoAgua.lapf_id=ligacaoAguaPerfil.lapf_id "
						+ "	left outer join ligacao_esgoto ligacaoEsgoto on imovel.imov_id=ligacaoEsgoto.lesg_id "
						+ "	left outer join ligacao_esgoto_diametro ligacaoEsgotoDiametro on  ligacaoEsgoto.legd_id=ligacaoEsgotoDiametro.legd_id "
						+ "	left outer join ligacao_esgoto_material ligacaoEsgotoMaterial on ligacaoEsgoto.legm_id=ligacaoEsgotoMaterial.legm_id "
						+ "	left outer join ligacao_esgoto_perfil ligacaoEsgotoPerfil on ligacaoEsgoto.lepf_id=ligacaoEsgotoPerfil.lepf_id "
						+ "	left outer join poco_tipo pocoTipo on imovel.poco_id=pocoTipo.poco_id  "
						+ " left outer join funcionario funcionario on funcionario.func_id = ligacaoAgua.lagu_idfuncreligacaoagua "
						+ "	left outer join ramal_local_instalacao ramalLocalInstalacao on ramalLocalInstalacao.rlin_id=ligacaoEsgoto.rlin_id "
						+ "	where imovel.imov_id = "
						+ idImovel
						+ " and clienteImovel.cifr_id is null AND clienteImovel.crtp_id = "
						+ ConstantesSistema.CLIENTE_IMOVEL_TIPO_USUARIO;

		try{
			retorno = session.createSQLQuery(sql).addScalar("idFaturamentoGrupo", Hibernate.INTEGER).addScalar("amFaturamentoGrupo",
							Hibernate.INTEGER).addScalar("nomeEmpresa", Hibernate.STRING).addScalar("icCondominio", Hibernate.SHORT)
							.addScalar("descricaoLigAguaSit", Hibernate.STRING).addScalar("descricaoLigEsgSit", Hibernate.STRING)
							.addScalar("nomeCliente", Hibernate.STRING).addScalar("cpf", Hibernate.STRING).addScalar("cnpj",
											Hibernate.STRING).addScalar("numerohidrometro", Hibernate.STRING).addScalar("dtInstHidrometro",
											Hibernate.DATE).addScalar("descricaoHidCapacidade", Hibernate.STRING).addScalar(
											"descricaoHidTipo", Hibernate.STRING).addScalar("descricaoHidMarca", Hibernate.STRING)
							.addScalar("descricaoHidLocInst", Hibernate.STRING).addScalar("descricaoHidDiamentro", Hibernate.STRING)
							.addScalar("descricaoHidProtecao", Hibernate.STRING).addScalar("descricaoIcCavalete", Hibernate.SHORT)
							.addScalar("anoFabricacao", Hibernate.SHORT).addScalar("descricaoImovelPerfil", Hibernate.STRING).addScalar(
											"dataLigacaoAgua", Hibernate.DATE).addScalar("dataCorte", Hibernate.DATE).addScalar(
											"dataReligAgua", Hibernate.DATE).addScalar("dataSupAgua", Hibernate.DATE).addScalar(
											"descricaoAguaDia", Hibernate.STRING).addScalar("descricaoLigAguaMat", Hibernate.STRING)
							.addScalar("numeroConsumoMinAgua", Hibernate.INTEGER).addScalar("dataLigacao", Hibernate.DATE).addScalar(
											"descricaoLigEsgDia", Hibernate.STRING).addScalar("descricaoLigEsgMat", Hibernate.STRING)
							.addScalar("descricaoLigEsgPerfil", Hibernate.STRING).addScalar("numeroConsumoMinEsgo", Hibernate.INTEGER)
							.addScalar("percentualEsgoto", Hibernate.BIG_DECIMAL).addScalar("percentualColeta", Hibernate.BIG_DECIMAL)
							.addScalar("descricaoPocoTipo", Hibernate.STRING).addScalar("dataRestabelecimentoAgua", Hibernate.DATE)
							.addScalar("descricaoLigacaoAguaPerfil", Hibernate.STRING).addScalar("idLigacaoEsgoto", Hibernate.INTEGER)
							.addScalar("idLigacaoAgua", Hibernate.INTEGER).addScalar("idLigacaoAguaSituacao", Hibernate.INTEGER).addScalar(
											"idImovelCondominio", Hibernate.INTEGER).addScalar("diaVencimento", Hibernate.SHORT).addScalar(
											"codigoRota", Hibernate.SHORT).addScalar("sequencialRota", Hibernate.INTEGER).addScalar(
											"idHidrometro", Hibernate.INTEGER).addScalar("idFuncionarioReligacaoAgua", Hibernate.INTEGER)
							.addScalar("nomeFuncionarioReligacaoAgua", Hibernate.STRING).addScalar("numeroLeituraInstalacao",
											Hibernate.INTEGER).addScalar("descricaoRamalLocalInstalacao", Hibernate.STRING).list();

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
	 * Método que apresenta os dados do imovel
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 * 
	 * @author Flávio Cordeiro
	 * @date 04/08/2006
	 * @param idImovel
	 * @return Collection
	 */
	public Collection pesquiarImovelExcecoesApresentaDadosResumido(Integer idImovel, boolean ligacaoAgua) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		Collection retorno = null;

		String ligacaoHidrometro = "";
		if(ligacaoAgua){
			ligacaoHidrometro = " ligacaoAgua.hidi_id = hidrometroIstalacaoHistorico.hidi_id";
		}else{
			ligacaoHidrometro = " imovel.hidi_id = hidrometroIstalacaoHistorico.hidi_id";
		}

		String sql = "select  faturamentoGrupo.ftgr_id as idFaturamentoGrupo,"// 0
						+ " faturamentoGrupo.ftgr_amreferencia as amFaturamentoGrupo,"// 1
						+ " imovel.imov_icimovelcondominio as icCondominio,"// 2
						+ " ligacaoAguaSituacao.last_dsligacaoaguasituacao as descricaoLigAguaSit,"// 3
						+ " ligacaoEsgotoSituacao.lest_dsligacaoesgotosituacao as descricaoLigEsgSit,"// 4
						+ " hidrometro.hidr_nnhidrometro as numerohidrometro,"// 5
						+ " hidrometroIstalacaoHistorico.hidi_dtinstalacaohidrometro as dtInstHidrometro,"// 6
						+ " hidrometroCapacidade.hicp_dshidrometrocapacidade as descricaoHidCapacidade,"// 7
						+ " imovelPerfil.iper_dsimovelperfil as descricaoImovelPerfil,"// 8
						+ " imovel.imov_idimovelcondominio as idImovelCondominio,"// 9
						+ " imovel.imov_qteconomia as qtdEconomias,"// 10
						+ " imovel.hidi_id as idHidrometroHistoricoImovel,"// 11
						+ " hidrometro.hidr_nndigitosleitura as numeroDigitosLeitura"// 12
						+ " from cliente_imovel clienteImovel "
						+ " inner join imovel imovel on clienteImovel.imov_id=imovel.imov_id and imovel.imov_id = "
						+ idImovel
						+ "	left outer join ligacao_agua ligacaoAgua on imovel.imov_id=ligacaoAgua.lagu_id "
						+ " left outer join hidrometro_instalacao_hist hidrometroIstalacaoHistorico on "
						+ ligacaoHidrometro
						+ "	left outer join hidrometro hidrometro on hidrometroIstalacaoHistorico.hidr_id=hidrometro.hidr_id "
						+ "	left outer join hidrometro_capacidade hidrometroCapacidade on hidrometro.hicp_id=hidrometroCapacidade.hicp_id "
						+ "	left outer join quadra quadra on imovel.qdra_id=quadra.qdra_id "
						+ "	left outer join rota rota on imovel.rota_id=rota.rota_id  "
						+ "	left outer join faturamento_grupo faturamentoGrupo on rota.ftgr_id=faturamentoGrupo.ftgr_id "
						+ "	left outer join ligacao_agua_situacao ligacaoAguaSituacao on imovel.last_id=ligacaoAguaSituacao.last_id "
						+ "	left outer join ligacao_esgoto_situacao ligacaoEsgotoSituacao on  imovel.lest_id=ligacaoEsgotoSituacao.lest_id "
						+ "	left outer join imovel_perfil imovelPerfil on imovel.iper_id=imovelPerfil.iper_id "
						+ "	left outer join ligacao_esgoto ligacaoEsgoto on imovel.imov_id=ligacaoEsgoto.lesg_id "
						+ "	left outer join poco_tipo pocoTipo on imovel.poco_id=pocoTipo.poco_id  "
						+ "	where clienteImovel.cifr_id is null AND clienteImovel.crtp_id = "
						+ ConstantesSistema.CLIENTE_IMOVEL_TIPO_USUARIO;

		// System.out.println(sql);

		try{
			retorno = session.createSQLQuery(sql).addScalar("idFaturamentoGrupo", Hibernate.INTEGER).addScalar("amFaturamentoGrupo",
							Hibernate.INTEGER).addScalar("icCondominio", Hibernate.SHORT)
							.addScalar("descricaoLigAguaSit", Hibernate.STRING).addScalar("descricaoLigEsgSit", Hibernate.STRING)
							.addScalar("numerohidrometro", Hibernate.STRING).addScalar("dtInstHidrometro", Hibernate.DATE).addScalar(
											"descricaoHidCapacidade", Hibernate.STRING)
							.addScalar("descricaoImovelPerfil", Hibernate.STRING).addScalar("idImovelCondominio", Hibernate.INTEGER)
							.addScalar("qtdEconomias", Hibernate.SHORT).addScalar("idHidrometroHistoricoImovel", Hibernate.INTEGER)
							.addScalar("numeroDigitosLeitura", Hibernate.SHORT).list();

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
	 * Método que apresenta os dados do imovel
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 * 
	 * @author Sávio Luiz
	 * @date 04/08/2006
	 * @param idImovel
	 * @return Collection
	 */
	public Collection pesquiarMedicaoConsumoHistoricoExcecoesApresentaDados(FaturamentoGrupo faturamentoGrupo, Integer idImovel,
					boolean ligacaoAgua) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		Collection retorno = null;

		String ligadoPorAguaOuHidrometro = "";

		if(ligacaoAgua){
			ligadoPorAguaOuHidrometro = " medicaoHistorico.lagu_id = imovel.imov_id ";
		}else{
			ligadoPorAguaOuHidrometro = " medicaoHistorico.imov_id = imovel.imov_id ";
		}

		String sql = "select   distinct medicaoTipo.medt_dsmedicaotipo as descricaoMedTipo,"// 0
						+ "medicaoTipo.medt_id as idMedTipo,"// 1
						+ "mdhi_dtleituraanteriorfaturame as dataLeituraAnteriorFaturame,"// 2
						+ "mdhi_dtleituraatualfaturamento as dataLeituraAtualFaturamento,"// 3
						+ "mdhi_nnleituraatualfaturamento as numeroLeituraAtualFaturamento,"// 4
						+ "mdhi_nnleituraanteriorfaturame as numeroLeituraAnteriorFaturame,"// 5
						+ "mdhi_dtleituraatualinformada as dataLeituraAtualInformada,"// 6
						+ "mdhi_nnleituraatualinformada as numeroLeituraInformada,"// 7
						+ "leituraSituacaoAtual.ltst_dsleiturasituacao as descricaoLeituraSituacao,"// 8
						+ "mdhi_dtleituraatualfaturamento as dataLeituraFaturamento,"// 9
						+ "mdhi_nnleituraatualfaturamento as numeroLeituraFaturamento,"// 10
						+ "medicaoHistorico.func_id as idFuncionario,"// 11
						+ "leituraAnormalidadeInformada.ltan_dsleituraanormalidade as descLeituraAnormalidadeInform,"// 12
						+ "leituraAnormalidadeFaturamento.ltan_dsleituraanormalidade as descLeituraAnormalidadeFat,"// 13
						+ "mdhi_nnconsumomedidomes as numeroConsumoMes,"// 14
						+ "consumoHistorico.cshi_nnconsumofaturadomes as numeroConsumoFaturadoMes,"// 15
						+ "consumoHistorico.cshi_nnconsumorateio as numeroConsumoRateio,"// 16
						+ "consumoAnormalidade.csan_dsabreviadaconsumoanormal as descAbreviadaConsumoAnormal,"// 17
						+ "consumoTipo.cstp_dsabreviadaconsumotipo as descricaoAbreviadaConsumoTipo, " // 18
						+ "medicaoHistorico.mdhi_nnconsumomediohidrometro as consumoMedioHidrometro, " // 19
						+ "consumoHistorico.cshi_nnconsumomedio as consumoMedioImovel, " // 20
						+ "consumoHistoricoEsgoto.cshi_nnconsumofaturadomes as consumoFaturadoEsgoto, "// 21
						+ "funcionario.func_nmfuncionario as nomeFuncionarioHint " // 22

						+ "from imovel imovel "

						+ " left outer join consumo_historico consumoHistorico on imovel.imov_id=consumoHistorico.imov_id and consumoHistorico.cshi_amfaturamento = "
						+ faturamentoGrupo.getAnoMesReferencia()
						+ " left outer join medicao_historico medicaoHistorico on medicaoHistorico.mdhi_amleitura = "
						+ faturamentoGrupo.getAnoMesReferencia()
						+ "  AND "
						+ ligadoPorAguaOuHidrometro
						+ " left outer join consumo_anormalidade consumoAnormalidade on consumoHistorico.csan_id=consumoAnormalidade.csan_id "
						+ " left outer join funcionario funcionario on medicaoHistorico.func_id=funcionario.func_id "
						+ " left outer join consumo_tipo consumoTipo on consumoHistorico.cstp_id=consumoTipo.cstp_id "
						+ " left outer join quadra quadra on imovel.qdra_id=quadra.qdra_id "
						+ " left outer join rota rota on imovel.rota_id=rota.rota_id "
						+ " left outer join faturamento_grupo faturamentoGrupo on rota.ftgr_id=faturamentoGrupo.ftgr_id "
						+ " left outer join empresa empresa on rota.empr_id=empresa.empr_id "
						+ " left outer join imovel_perfil imovelPerfil on imovel.iper_id=imovelPerfil.iper_id "
						+ " left outer join medicao_tipo medicaoTipo on medicaoHistorico.medt_id=medicaoTipo.medt_id "
						+ " left outer join leitura_situacao leituraSituacaoAtual on medicaoHistorico.ltst_idleiturasituacaoatual=leituraSituacaoAtual.ltst_id "
						+ " left outer join leitura_anormalidade leituraAnormalidadeInformada on medicaoHistorico.ltan_idleituraanormalidadeinfo=leituraAnormalidadeInformada.ltan_id "
						+ " left outer join leitura_anormalidade leituraAnormalidadeFaturamento on medicaoHistorico.ltan_idleituraanormalidadefatu=leituraAnormalidadeFaturamento.ltan_id "
						+ "	left outer join consumo_historico consumoHistoricoEsgoto on consumoHistoricoEsgoto.imov_id= "
						+ idImovel
						+ " and  consumoHistorico.cshi_amfaturamento = "
						+ faturamentoGrupo.getAnoMesReferencia()
						+ " and consumoHistorico.lgti_id = " + LigacaoTipo.LIGACAO_ESGOTO + " where imovel.imov_id = " + idImovel;

		// System.out.println(sql);

		try{
			retorno = session.createSQLQuery(sql).addScalar("descricaoMedTipo", Hibernate.STRING).addScalar("idMedTipo", Hibernate.INTEGER)
							.addScalar("dataLeituraAnteriorFaturame", Hibernate.DATE).addScalar("dataLeituraAtualFaturamento",
											Hibernate.DATE).addScalar("numeroLeituraAnteriorFaturame", Hibernate.INTEGER).addScalar(
											"numeroLeituraAnteriorFaturame", Hibernate.INTEGER).addScalar("dataLeituraAtualInformada",
											Hibernate.DATE).addScalar("numeroLeituraInformada", Hibernate.INTEGER).addScalar(
											"descricaoLeituraSituacao", Hibernate.STRING).addScalar("dataLeituraFaturamento",
											Hibernate.DATE).addScalar("numeroLeituraFaturamento", Hibernate.INTEGER).addScalar(
											"idFuncionario", Hibernate.INTEGER)
							.addScalar("descLeituraAnormalidadeInform", Hibernate.STRING).addScalar("descLeituraAnormalidadeFat",
											Hibernate.STRING).addScalar("numeroConsumoMes", Hibernate.INTEGER).addScalar(
											"numeroConsumoFaturadoMes", Hibernate.INTEGER).addScalar("numeroConsumoRateio",
											Hibernate.INTEGER).addScalar("descAbreviadaConsumoAnormal", Hibernate.STRING).addScalar(
											"descricaoAbreviadaConsumoTipo", Hibernate.STRING).addScalar("consumoMedioHidrometro",
											Hibernate.INTEGER).addScalar("consumoMedioImovel", Hibernate.INTEGER).addScalar(
											"consumoFaturadoEsgoto", Hibernate.INTEGER).addScalar("nomeFuncionarioHint", Hibernate.STRING)
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
	 * Método que apresenta os dados do imovel
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 * 
	 * @author Sávio Luiz
	 * @date 04/08/2006
	 * @author eduardo henrique
	 * @date 26/06/2008
	 *       correção do tipo de ligação na claus. where, dependendo do parâmetro ligacaoAgua
	 *       informado
	 *       e retirada do 2o. Left join com a propria consumoHistorico.
	 * @param idImovel
	 * @return Collection
	 */
	public Collection pesquisarMedicaoConsumoHistoricoExcecoesApresentaDadosConsultarImovel(Integer anoMesReferencia, Integer idImovel,
					boolean ligacaoAgua) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		Collection retorno = null;

		String ligadoPorAguaOuHidrometro = "";
		String tipoMedicao = "";

		if(ligacaoAgua){
			ligadoPorAguaOuHidrometro = " medicaoHistorico.lagu_id = imovel.imov_id ";
			tipoMedicao = " medicaoHistorico.medt_id = " + MedicaoTipo.LIGACAO_AGUA;
		}else{
			ligadoPorAguaOuHidrometro = " medicaoHistorico.imov_id = imovel.imov_id ";
			tipoMedicao = " medicaoHistorico.medt_id = " + MedicaoTipo.POCO;
		}

		String sql = "select distinct medicaoTipo.medt_dsmedicaotipo as descricaoMedTipo, " // 0
						+ "medicaoTipo.medt_id as idMedTipo, " // 1
						+ "mdhi_dtleituraanteriorfaturame as dataLeituraAnteriorFaturamento, " // 2
						+ "mdhi_dtleituraatualfaturamento as dataLeituraAtualFaturamento, " // 3
						+ "mdhi_nnleituraatualfaturamento as numeroLeituraAtualFat, " // 4
						+ "mdhi_nnleituraanteriorfaturame as numeroLeituraAnteriorFat, " // 5
						+ "mdhi_dtleituraatualinformada as dataLeituraAtualInformada, " // 6
						+ "mdhi_nnleituraatualinformada as numeroLeituraInformada, " // 7
						+ "leituraSituacaoAtual.ltst_dsleiturasituacao as descricaoLeituraSituacao, " // 8
						+ "mdhi_dtleituraatualfaturamento as dataLeituraFaturamento, " // 9
						+ "mdhi_nnleituraatualfaturamento as numeroLeituraFaturamento, " // 10
						+ "medicaoHistorico.func_id as idFuncionario, " // 11
						+ "leituraAnormalidadeInformada.ltan_dsleituraanormalidade as descLeituraAnormalidadeInform, " // 12
						+ "leituraAnormalidadeFaturamento.ltan_dsleituraanormalidade as descLeituraAnormalidadeFat, " // 13
						+ "mdhi_nnconsumomedidomes as numeroConsumoMes, " // 14
						+ "consumoHistorico.cshi_nnconsumofaturadomes as numeroConsumoFaturadoMes, " // 15
						+ "consumoHistorico.cshi_nnconsumorateio as numeroConsumoRateio, " // 16
						+ "consumoAnormalidade.csan_dsabreviadaconsumoanormal as descAbreviadaConsumoAnormal, " // 17
						+ "consumoTipo.cstp_dsabreviadaconsumotipo as descricaoAbreviadaConsumoTipo, " // 18
						+ "medicaoHistorico.mdhi_nnconsumomediohidrometro as consumoMedioHidrometro, " // 19
						+ "consumoHistorico.cshi_nnconsumomedio as consumoMedioImovel, " // 20
						+ "consumoHistorico.cshi_nnconsumocredito as consumoMinimoCredito, " // 21
						+ "medicaoHistorico.mdhi_nnconsumocreditoanterior as consumoCreditoAnterior, " // 22
						+ "funcionario.func_nmfuncionario as nomeFuncionarioHintPoco, " // 23
						+ "consumoAnormalidade.csan_dsconsumoanormalidade as descConsumoAnormal, " // 24
						+ "consumoTipo.cstp_dsconsumotipo as descricaoConsumoTipo " // 25
						+ "from imovel imovel "
						+ " inner join consumo_historico consumoHistorico on imovel.imov_id=consumoHistorico.imov_id "
						+ " left outer join medicao_historico medicaoHistorico on medicaoHistorico.mdhi_amleitura = "
						+ anoMesReferencia
						+ " AND "
						+ ligadoPorAguaOuHidrometro
						+ " AND "
						+ tipoMedicao
						+ " left outer join funcionario funcionario on medicaoHistorico.func_id=funcionario.func_id "
						+ " left outer join consumo_anormalidade consumoAnormalidade on consumoHistorico.csan_id=consumoAnormalidade.csan_id "
						+ " left outer join consumo_tipo consumoTipo on consumoHistorico.cstp_id=consumoTipo.cstp_id "
						+ " left outer join quadra quadra on imovel.qdra_id=quadra.qdra_id "
						+ " left outer join rota rota on imovel.rota_id=rota.rota_id "
						+ " left outer join faturamento_grupo faturamentoGrupo on rota.ftgr_id=faturamentoGrupo.ftgr_id "
						+ " left outer join empresa empresa on rota.empr_id=empresa.empr_id "
						+ " left outer join imovel_perfil imovelPerfil on imovel.iper_id=imovelPerfil.iper_id "
						+ " left outer join medicao_tipo medicaoTipo on medicaoHistorico.medt_id=medicaoTipo.medt_id "
						+ " left outer join leitura_situacao leituraSituacaoAtual on medicaoHistorico.ltst_idleiturasituacaoatual=leituraSituacaoAtual.ltst_id "
						+ " left outer join leitura_anormalidade leituraAnormalidadeInformada on medicaoHistorico.ltan_idleituraanormalidadeinfo=leituraAnormalidadeInformada.ltan_id "
						+ " left outer join leitura_anormalidade leituraAnormalidadeFaturamento on medicaoHistorico.ltan_idleituraanormalidadefatu=leituraAnormalidadeFaturamento.ltan_id ";

		if(ligacaoAgua){
			sql += " where consumoHistorico.cshi_amfaturamento = " + anoMesReferencia + " and imovel.imov_id = " + idImovel
							+ " and consumoHistorico.lgti_id = " + LigacaoTipo.LIGACAO_AGUA;

		}else{
			sql += " where consumoHistorico.cshi_amfaturamento = " + anoMesReferencia + " and imovel.imov_id = " + idImovel
							+ " and consumoHistorico.lgti_id = " + LigacaoTipo.LIGACAO_ESGOTO;
		}

		// System.out.println(sql);

		try{
			retorno = session.createSQLQuery(sql).addScalar("descricaoMedTipo", Hibernate.STRING).addScalar("idMedTipo", Hibernate.INTEGER)
							.addScalar("dataLeituraAnteriorFaturamento", Hibernate.DATE).addScalar("dataLeituraAtualFaturamento",
											Hibernate.DATE).addScalar("numeroLeituraAtualFat", Hibernate.INTEGER).addScalar(
											"numeroLeituraAnteriorFat", Hibernate.INTEGER).addScalar("dataLeituraAtualInformada",
											Hibernate.DATE).addScalar("numeroLeituraInformada", Hibernate.INTEGER).addScalar(
											"descricaoLeituraSituacao", Hibernate.STRING).addScalar("dataLeituraFaturamento",
											Hibernate.DATE).addScalar("numeroLeituraFaturamento", Hibernate.INTEGER).addScalar(
											"idFuncionario", Hibernate.INTEGER)
							.addScalar("descLeituraAnormalidadeInform", Hibernate.STRING).addScalar("descLeituraAnormalidadeFat",
											Hibernate.STRING).addScalar("numeroConsumoMes", Hibernate.INTEGER).addScalar(
											"numeroConsumoFaturadoMes", Hibernate.INTEGER).addScalar("numeroConsumoRateio",
											Hibernate.INTEGER).addScalar("descAbreviadaConsumoAnormal", Hibernate.STRING).addScalar(
											"descricaoAbreviadaConsumoTipo", Hibernate.STRING).addScalar("consumoMedioHidrometro",
											Hibernate.INTEGER).addScalar("consumoMedioImovel", Hibernate.INTEGER).addScalar(
											"consumoMinimoCredito", Hibernate.INTEGER).addScalar("consumoCreditoAnterior",
											Hibernate.INTEGER).addScalar("nomeFuncionarioHintPoco", Hibernate.STRING).addScalar(
											"descConsumoAnormal", Hibernate.STRING).addScalar("descricaoConsumoTipo", Hibernate.STRING)
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
	 * Retorna uma coleção com os dados das medicoes para apresentação
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 * 
	 * @author eduardo henrique
	 * @date 30/01/2009
	 *       Alteração na consulta para inclusão do CreditoConsumoAnterior para exibição
	 *       na consulta de dados de Imóvel.
	 *       Alterado por: Yara Souza
	 *       data: 20/05/2010
	 *       Acrescentando o nome do leiturista no retorno da consulta em medição histórico.
	 */
	public Collection carregarDadosMedicao(Integer idImovel, boolean ligacaoAgua) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;
		try{
			consulta = "select medicaoHistorico.mdhi_amleitura as mesAno,"// 0
							+ " medicaoHistorico.mdhi_dtleituraatualinformada as dataLeituraInformada,"// 1
							+ " medicaoHistorico.mdhi_nnleituraatualinformada as numeroLeituraInformada,"// 2
							+ " medicaoHistorico.mdhi_dtleituraatualfaturamento as dataLeituraFaturada,"// 3
							+ " medicaoHistorico.mdhi_nnleituraatualfaturamento as numeroLeituraFaturada,"// 4
							+ " leituraAnormalidadeInformada.ltan_dsleituraanormalidade as descAnormalidadeLeituraInform,"// 5
							+ " leituraAnormalidadeFaturamento.ltan_dsleituraanormalidade as descAnormalidadeLeituraFat,"// 6
							+ " leituraSituacao.ltst_dsleiturasituacao as leituraSituacao,"// 7
							+ " medicaoHistorico.mdhi_nnconsumomediohidrometro as consumoMedioHidrometro,"// 8
							+ " leituraAnormalidadeInformada.ltan_id as idAnormalidadeLeituraInformada,"// 9
							+ " leituraAnormalidadeFaturamento.ltan_id as idAnormalidadeLeituraFaturada,"// 10
							+ " medicaoHistorico.mdhi_nnconsumocreditoanterior as creditoConsumoAnterior, "// 11
							+ " medicaoHistorico.func_id  as idFuncionario, "// 12
							+ " funcionario.func_nmfuncionario  as nomeFuncionario "// 13
							+ " from medicao_historico medicaoHistorico "
							+ " left outer join leitura_situacao leituraSituacao on medicaoHistorico.ltst_idleiturasituacaoatual = leituraSituacao.ltst_id "
							+ "	left outer join leitura_anormalidade leituraAnormalidadeInformada on medicaoHistorico.ltan_idleituraanormalidadeinfo=leituraAnormalidadeInformada.ltan_id "
							+ "	left outer join leitura_anormalidade leituraAnormalidadeFaturamento on medicaoHistorico.ltan_idleituraanormalidadefatu=leituraAnormalidadeFaturamento.ltan_id "
							+ "	left outer join funcionario funcionario on funcionario.func_id=medicaoHistorico.func_id "
							+ "	left outer join leiturista leiturista on leiturista.func_id=funcionario.func_id ";
			if(ligacaoAgua){
				consulta = consulta + " where medicaoHistorico.lagu_id = " + idImovel + " order by medicaoHistorico.mdhi_amleitura";
			}else{
				consulta = consulta + " where medicaoHistorico.imov_id = " + idImovel + " order by medicaoHistorico.mdhi_amleitura";
			}

			// System.out.println(consulta);

			retorno = session.createSQLQuery(consulta).addScalar("mesAno", Hibernate.INTEGER).addScalar("dataLeituraInformada",
							Hibernate.DATE).addScalar("numeroLeituraInformada", Hibernate.INTEGER).addScalar("dataLeituraFaturada",
							Hibernate.DATE).addScalar("numeroLeituraFaturada", Hibernate.INTEGER).addScalar(
							"descAnormalidadeLeituraInform", Hibernate.STRING).addScalar("descAnormalidadeLeituraFat", Hibernate.STRING)
							.addScalar("leituraSituacao", Hibernate.STRING).addScalar("consumoMedioHidrometro", Hibernate.INTEGER)
							.addScalar("idAnormalidadeLeituraInformada", Hibernate.INTEGER).addScalar("idAnormalidadeLeituraFaturada",
											Hibernate.INTEGER).addScalar("creditoConsumoAnterior", Hibernate.INTEGER).addScalar(
											"idFuncionario", Hibernate.INTEGER).addScalar("nomeFuncionario", Hibernate.STRING).list();

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
	 * Retorna uma coleção com os dados das medicoes para apresentação
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 */
	public Collection carregarDadosMedicaoResumo(Integer idImovel, boolean ligacaoAgua) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;
		try{
			consulta = "select medicaoHistorico.mdhi_amleitura as mesAno,"
							+ " medicaoHistorico.mdhi_dtleituraatualinformada as dataLeituraInformada,"
							+ " medicaoHistorico.mdhi_nnleituraatualinformada as numeroLeituraInformada,"
							+ " medicaoHistorico.mdhi_dtleituraatualfaturamento as dataLeituraFaturada,"
							+ " medicaoHistorico.mdhi_nnleituraatualfaturamento as numeroLeituraFaturada,"
							+ " leituraAnormalidadeInformada.ltan_id as idAnormalidadeLeituraInformada,"
							+ " leituraAnormalidadeFaturamento.ltan_id as idAnormalidadeLeituraFaturada,"
							+ " leituraSituacao.ltst_dsleiturasituacao as leituraSituacao, "
							+ " leituraAnormalidadeInformada.ltan_dcabreviadaleituraanormal as descAnormalidadeLeituraInf, "
							+ " leituraAnormalidadeFaturamento.ltan_dcabreviadaleituraanormal as descAnormalidadeLeituraFat "
							+ " from medicao_historico medicaoHistorico "
							+ " left outer join leitura_situacao leituraSituacao on medicaoHistorico.ltst_idleiturasituacaoatual = leituraSituacao.ltst_id "
							+ "	left outer join leitura_anormalidade leituraAnormalidadeInformada on medicaoHistorico.ltan_idleituraanormalidadeinfo=leituraAnormalidadeInformada.ltan_id "
							+ "	left outer join leitura_anormalidade leituraAnormalidadeFaturamento on medicaoHistorico.ltan_idleituraanormalidadefatu=leituraAnormalidadeFaturamento.ltan_id ";
			if(ligacaoAgua){
				consulta = consulta + " where medicaoHistorico.lagu_id = " + idImovel + " order by medicaoHistorico.mdhi_amleitura";
			}else{
				consulta = consulta + " where medicaoHistorico.imov_id = " + idImovel + " order by medicaoHistorico.mdhi_amleitura";
			}

			// System.out.println(consulta);

			retorno = session.createSQLQuery(consulta).addScalar("mesAno", Hibernate.INTEGER).addScalar("dataLeituraInformada",
							Hibernate.DATE).addScalar("numeroLeituraInformada", Hibernate.INTEGER).addScalar("dataLeituraFaturada",
							Hibernate.DATE).addScalar("numeroLeituraFaturada", Hibernate.INTEGER).addScalar(
							"idAnormalidadeLeituraInformada", Hibernate.INTEGER).addScalar("idAnormalidadeLeituraFaturada",
 Hibernate.INTEGER).addScalar("leituraSituacao", Hibernate.STRING)
							.addScalar("descAnormalidadeLeituraInf", Hibernate.STRING)
							.addScalar("descAnormalidadeLeituraFat", Hibernate.STRING)

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
	 * Retorna um objeto com os dados das medicoes para apresentação
	 * Flávio
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 */
	public Object[] carregarDadosMedicaoResumido(Integer idImovel, boolean ligacaoAgua, String anoMes) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;
		try{
			consulta = "select medicaoHistorico.mdhi_amleitura as mesAno,"
							+ " medicaoHistorico.mdhi_dtleituraatualinformada as dataLeituraInformada,"
							+ " medicaoHistorico.mdhi_nnleituraatualinformada as numeroLeituraInformada,"
							+ " medicaoHistorico.mdhi_dtleituraanteriorfaturame as dataLeituraAnteriorFaturada,"
							+ " medicaoHistorico.mdhi_nnleituraanteriorfaturame as numeroLeituraAnteriorFaturada,"
							+ " leituraAnormalidadeInformada.ltan_dcabreviadaleituraanormal as descAnormalidadeLeituraInform,"
							+ " leituraAnormalidadeFaturamento.ltan_dcabreviadaleituraanormal as descAnormalidadeLeituraFat,"
							+ " medicaoHistorico.mdhi_nnvezesconsecutivaanormal as numeroVezesConsecutivosAnormal,"
							+ " medicaoHistorico.func_id as idFuncionario,"
							+ " leituraSituacao.ltst_dsleiturasituacao as leituraSituacao, "
							+ " medicaoHistorico.mdhi_dtleituraatualfaturamento as dataLeituraAtualFaturada,"
							+ " medicaoHistorico.mdhi_nnleituraatualfaturamento as numeroLeituraAtualFaturada,"
							+ " medicaoHistorico.ltan_idleituraanormalidadefatu as idAnormalidade, "
							+ " medicaoHistorico.mdhi_nnconsumoinformado as consumoInformado,"
							+ " medicaoHistorico.mdhi_nnconsumomediohidrometro as consumoMedioHidrometro, "
							+ " medicaoHistorico.ltan_idleituraanormalidadeinfo as idAnormalidadeInformada, "
							+ " leituraAnormalidadeInformada.ltan_dcabreviadaleituraanormal as descAnormalidadeInf, "
							+ " leituraAnormalidadeFaturamento.ltan_dcabreviadaleituraanormal as descAnormalidadeFat "
							+ " from medicao_historico medicaoHistorico "
							+ " left outer join leitura_situacao leituraSituacao on medicaoHistorico.ltst_idleiturasituacaoatual = leituraSituacao.ltst_id "
							+ "	left outer join leitura_anormalidade leituraAnormalidadeInformada on medicaoHistorico.ltan_idleituraanormalidadeinfo=leituraAnormalidadeInformada.ltan_id "
							+ "	left outer join leitura_anormalidade leituraAnormalidadeFaturamento on medicaoHistorico.ltan_idleituraanormalidadefatu=leituraAnormalidadeFaturamento.ltan_id ";
			if(ligacaoAgua){
				consulta = consulta + " where medicaoHistorico.lagu_id = " + idImovel + " and medicaoHistorico.mdhi_amleitura = " + anoMes
								+ " order by medicaoHistorico.mdhi_amleitura";
			}else{
				consulta = consulta + " where medicaoHistorico.imov_id = " + idImovel + " and medicaoHistorico.mdhi_amleitura = " + anoMes
								+ " order by medicaoHistorico.mdhi_amleitura";
			}

			// System.out.println(consulta);

			retorno = (Object[]) session.createSQLQuery(consulta).addScalar("mesAno", Hibernate.INTEGER)// 0
							.addScalar("dataLeituraInformada", Hibernate.DATE)// 1
							.addScalar("numeroLeituraInformada", Hibernate.INTEGER)// 2
							.addScalar("dataLeituraAnteriorFaturada", Hibernate.DATE)// 3
							.addScalar("numeroLeituraAnteriorFaturada", Hibernate.INTEGER)// 4
							.addScalar("descAnormalidadeLeituraInform", Hibernate.STRING)// 5
							.addScalar("descAnormalidadeLeituraFat", Hibernate.STRING)// 6
							.addScalar("numeroVezesConsecutivosAnormal", Hibernate.SHORT)// 7
							.addScalar("idFuncionario", Hibernate.INTEGER)// 8
							.addScalar("leituraSituacao", Hibernate.STRING)// 9
							.addScalar("dataLeituraAtualFaturada", Hibernate.DATE)// 10
							.addScalar("numeroLeituraAtualFaturada", Hibernate.INTEGER)// 11
							.addScalar("idAnormalidade", Hibernate.INTEGER)// 12
							.addScalar("consumoInformado", Hibernate.INTEGER)// 13
							.addScalar("consumoMedioHidrometro", Hibernate.INTEGER)// 14
							.addScalar("idAnormalidadeInformada", Hibernate.INTEGER)// 15
							.addScalar("descAnormalidadeInf", Hibernate.STRING)// 16
							.addScalar("descAnormalidadeFat", Hibernate.STRING)// 17
							.setMaxResults(1).uniqueResult();

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
	 * Retorna uma coleção com os dados dos Consumos para apresentação
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 * 
	 * @author Virgínia Melo
	 * @date 26/12/2008
	 *       Adicionado campo consumoMinimoCreditado e descrição do tipo de consumo.
	 * @author Eduardo Henrique
	 * @date 31/12/2008
	 *       Alteração no Join com Medicao_Historico para que Consumos sejam trazidos mesmo sem
	 *       medicao.
	 *       Alteração no posicionamento do Campo de Esgoto, para adequação da montagem do resultado
	 *       no
	 *       método chamador.
	 */
	public Collection carregarDadosConsumo(Integer idImovel, int anoMes, boolean ligacaoAgua) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;
		try{
			consulta = "select consumoHistorico.cshi_amfaturamento as mesAno,"// 0
							+ " medicaohistorico.mdhi_nnconsumomedidomes as consumoMedido,"// 1
							+ " consumoHistorico.cshi_nnconsumofaturadomes as consumoFaturado,"// 2
							+ " consumoHistorico.cshi_nnconsumorateio as consumoRateio,"// 3
							+ " consumoAnormalidade.csan_dsconsumoanormalidade as consumoAnormalidade,"// 4
							+ " consumoTipo.cstp_dsabreviadaconsumotipo as consumoTipo,"// 5
							+ " medicaoHistorico.mdhi_dtleituraanteriorfaturame as dataLeituraAnterior,"// 6
							+ " medicaoHistorico.mdhi_dtleituraatualfaturamento as dataLeituraAtual,"// 7
							+ " consumoTipo.cstp_dsconsumotipo as consumoTipoDescricao, "// 8
							+ " consumoHistorico.cshi_nnconsumomedio as consumoMedio, "// 9
							+ " medicaoHistorico.ltan_idleituraanormalidadefatu as idAnormalidade,"// 10
							+ " medicaoHistorico.mdhi_nnconsumoInformado as consumoInformado, "// 11
							+ " consumoAnormalidade.csan_dsabreviadaconsumoanormal as consumoAnormalidadeAbreviada, "// 12
							+ " consumoHistorico.cshi_nnconsumorateio as rateio, "// 13
							+ " consumoHistorico.cshi_nnconsumocredito as consumoMinimoCreditado, " // 14
							+ " consumoHistoricoEsgoto.cshi_nnconsumofaturadomes as consumoFaturadoEsgoto " // 15
							+ " from consumo_historico consumoHistorico"
							+ " left outer join medicao_historico medicaoHistorico on consumoHistorico.imov_id = medicaoHistorico.lagu_id and consumoHistorico.cshi_amfaturamento = medicaoHistorico.mdhi_amleitura"
							+ " left outer join consumo_anormalidade consumoAnormalidade on consumoHistorico.csan_id = consumoAnormalidade.csan_id"
							+ " left outer join consumo_tipo consumoTipo on consumoHistorico.cstp_id=consumoTipo.cstp_id"
							+ " left outer join consumo_historico consumoHistoricoEsgoto on consumoHistorico.imov_id = consumoHistoricoEsgoto.imov_id and consumoHistorico.cshi_amfaturamento = consumoHistoricoEsgoto.cshi_amfaturamento and consumoHistoricoEsgoto.lgti_id = "
							+ LigacaoTipo.LIGACAO_ESGOTO;
			if(ligacaoAgua){
				consulta += " where consumoHistorico.cshi_amfaturamento = " + anoMes + " and  consumoHistorico.imov_id =" + idImovel
								+ " and consumoHistorico.lgti_id = " + LigacaoTipo.LIGACAO_AGUA
								+ " order by consumoHistorico.cshi_amfaturamento";

			}else{
				consulta += " where  consumoHistorico.cshi_amfaturamento = " + anoMes + " and consumoHistorico.imov_id = " + idImovel
								+ " and consumoHistorico.lgti_id = " + LigacaoTipo.LIGACAO_ESGOTO
								+ " order by consumoHistorico.cshi_amfaturamento";

			}

			retorno = session.createSQLQuery(consulta).addScalar("mesAno", Hibernate.INTEGER).addScalar("consumoMedido", Hibernate.INTEGER)
							.addScalar("consumoFaturado", Hibernate.INTEGER).addScalar("consumoRateio", Hibernate.INTEGER).addScalar(
											"consumoAnormalidade", Hibernate.STRING).addScalar("consumoTipo", Hibernate.STRING).addScalar(
											"dataLeituraAnterior", Hibernate.DATE).addScalar("dataLeituraAtual", Hibernate.DATE).addScalar(
											"consumoTipoDescricao", Hibernate.STRING).addScalar("consumoMedio", Hibernate.INTEGER)
							.addScalar("idAnormalidade", Hibernate.INTEGER).addScalar("consumoInformado", Hibernate.INTEGER).addScalar(
											"consumoAnormalidadeAbreviada", Hibernate.STRING).addScalar("rateio", Hibernate.INTEGER)
							.addScalar("consumoMinimoCreditado", Hibernate.INTEGER).addScalar("consumoFaturadoEsgoto", Hibernate.INTEGER)
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
	 * Retorna uma coleção com os dados dos Consumos para apresentação
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 * 
	 * @author Eduardo Henrique
	 * @date 30/12/2008
	 * @author eduardo henrique
	 * @date 18/01/2009
	 *       Alteração no método para otimização de uso de índice no Oracle, até que
	 *       seja criado índice apropriado para a consulta.
	 */
	public Collection<Object[]> carregarDadosConsumo(Integer idImovel, Integer tipoLigacao) throws ErroRepositorioException{

		Collection retorno = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta;
		try{

			consulta = "Select distinct consumoHistorico.referenciaFaturamento  " + " From ConsumoHistorico consumoHistorico  "
							+ " Where consumoHistorico.imovel.id = :id " + " Order by consumoHistorico.referenciaFaturamento desc ";

			Collection<Integer> colecaoReferenciasConsumo = session.createQuery(consulta).setInteger("id", idImovel).list();

			for(Integer anoMesReferencia : colecaoReferenciasConsumo){

				consulta = "select consumoHistorico.cshi_amfaturamento as mesAno,"// 0
								+ " medicaohistorico.mdhi_nnconsumomedidomes as consumoMedido,"// 1
								+ " consumoHistorico.cshi_nnconsumofaturadomes as consumoFaturado,"// 2
								+ " consumoHistorico.cshi_nnconsumorateio as consumoRateio,"// 3
								+ " consumoAnormalidade.csan_dsconsumoanormalidade as consumoAnormalidade,"// 4
								+ " consumoTipo.cstp_dsabreviadaconsumotipo as consumoTipo,"// 5
								+ " medicaoHistorico.mdhi_dtleituraanteriorfaturame as dataLeituraAnterior,"// 6
								+ " medicaoHistorico.mdhi_dtleituraatualfaturamento as dataLeituraAtual,"// 7
								+ " consumoTipo.cstp_dsconsumotipo as consumoTipoDescricao, "// 8
								+ " consumoHistorico.cshi_nnconsumomedio as consumoMedio, "// 9
								+ " medicaoHistorico.ltan_idleituraanormalidadefatu as idAnormalidade,"// 10
								+ " medicaoHistorico.mdhi_nnconsumoInformado as consumoInformado, "// 11
								+ " consumoAnormalidade.csan_dsabreviadaconsumoanormal as consumoAnormalidadeAbreviada, "// 12
								+ " consumoHistorico.cshi_nnconsumorateio as rateio, "// 13
								+ " consumoHistorico.cshi_nnconsumocredito as consumoMinimoCreditado, " // 14
								+ " consumoHistoricoEsgoto.cshi_nnconsumofaturadomes as consumoFaturadoEsgoto " // 15
								+ " from consumo_historico consumoHistorico";

				if(tipoLigacao.equals(LigacaoTipo.LIGACAO_AGUA)){
					consulta += " left outer join medicao_historico medicaoHistorico on consumoHistorico.imov_id = medicaoHistorico.lagu_id and consumoHistorico.cshi_amfaturamento = medicaoHistorico.mdhi_amleitura";
				}else{
					consulta += " left outer join medicao_historico medicaoHistorico on consumoHistorico.imov_id = medicaoHistorico.imov_id and consumoHistorico.cshi_amfaturamento = medicaoHistorico.mdhi_amleitura";
				}

				consulta += " left outer join consumo_anormalidade consumoAnormalidade on consumoHistorico.csan_id = consumoAnormalidade.csan_id"
								+ " left outer join consumo_tipo consumoTipo on consumoHistorico.cstp_id=consumoTipo.cstp_id "
								+ " left outer join consumo_historico consumoHistoricoEsgoto on consumoHistorico.imov_id = consumoHistoricoEsgoto.imov_id and consumoHistorico.cshi_amfaturamento = consumoHistoricoEsgoto.cshi_amfaturamento and consumoHistoricoEsgoto.lgti_id = "
								+ LigacaoTipo.LIGACAO_ESGOTO;

				if(tipoLigacao.equals(LigacaoTipo.LIGACAO_AGUA)){
					consulta += " where consumoHistorico.imov_id =" + idImovel + " and consumoHistorico.cshi_amfaturamento = "
									+ anoMesReferencia + " and consumoHistorico.lgti_id = " + LigacaoTipo.LIGACAO_AGUA;

				}else{
					consulta += " where consumoHistorico.imov_id = " + idImovel + " and consumoHistorico.cshi_amfaturamento = "
									+ anoMesReferencia + " and consumoHistorico.lgti_id = " + LigacaoTipo.LIGACAO_ESGOTO;

				}
				consulta += " order by consumoHistorico.cshi_amfaturamento";

				retorno.addAll(session.createSQLQuery(consulta).addScalar("mesAno", Hibernate.INTEGER).addScalar("consumoMedido",
								Hibernate.INTEGER).addScalar("consumoFaturado", Hibernate.INTEGER).addScalar("consumoRateio",
								Hibernate.INTEGER).addScalar("consumoAnormalidade", Hibernate.STRING).addScalar("consumoTipo",
								Hibernate.STRING).addScalar("dataLeituraAnterior", Hibernate.DATE).addScalar("dataLeituraAtual",
								Hibernate.DATE).addScalar("consumoTipoDescricao", Hibernate.STRING).addScalar("consumoMedio",
								Hibernate.INTEGER).addScalar("idAnormalidade", Hibernate.INTEGER).addScalar("consumoInformado",
								Hibernate.INTEGER).addScalar("consumoAnormalidadeAbreviada", Hibernate.STRING).addScalar("rateio",
								Hibernate.INTEGER).addScalar("consumoMinimoCreditado", Hibernate.INTEGER).addScalar(
								"consumoFaturadoEsgoto", Hibernate.INTEGER).list());
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
	 * Retorna um Objeto com os dados dos Consumos para apresentação
	 * Flávio
	 * [UC0153] Apresentar dados para Analise da medição e Consumo
	 */
	public Object[] carregarDadosConsumoResumido(Integer idImovel, int anoMes, boolean ligacaoAgua) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;
		try{
			if(ligacaoAgua){
				consulta = "select consumoHistorico.cshi_amfaturamento as mesAno,"
								+ " consumoHistorico.cshi_nnconsumofaturadomes as consumoFaturado,"
								+ " consumoHistorico.cshi_nnconsumomedio as consumoMedio,"
								+ " consumoTipo.cstp_dsconsumotipo as consumoTipo,"
								+ " consumoHistoricoEsgoto.cshi_nnconsumofaturadomes as consumoFaturadoEsgoto"
								+ " from consumo_historico consumoHistorico"
								+ " left outer join consumo_tipo consumoTipo on consumoHistorico.cstp_id=consumoTipo.cstp_id"
								+ " left outer join consumo_historico consumoHistoricoEsgoto on consumoHistorico.imov_id = consumoHistoricoEsgoto.imov_id and consumoHistorico.cshi_amfaturamento = consumoHistoricoEsgoto.cshi_amfaturamento and consumoHistoricoEsgoto.lgti_id = "
								+ LigacaoTipo.LIGACAO_ESGOTO + " where consumoHistorico.imov_id =" + idImovel
								+ " and consumoHistorico.lgti_id = " + LigacaoTipo.LIGACAO_AGUA
								+ " and consumoHistorico.cshi_amfaturamento = " + anoMes + " order by consumoHistorico.cshi_amfaturamento";

				retorno = (Object[]) session.createSQLQuery(consulta).addScalar("mesAno", Hibernate.INTEGER).addScalar("consumoMedio",
								Hibernate.INTEGER).addScalar("consumoFaturado", Hibernate.INTEGER).addScalar("consumoTipo",
								Hibernate.STRING).uniqueResult();
			}else{
				consulta = "select consumoHistorico.cshi_amfaturamento as mesAno,"
								+ " consumoHistorico.cshi_nnconsumofaturadomes as consumoFaturado,"
								+ " consumoHistorico.cshi_nnconsumomedio as consumoMedio,"
								+ " consumoTipo.cstp_dsconsumotipo as consumoTipo,"
								+ " consumoHistoricoEsgoto.cshi_nnconsumofaturadomes as consumoFaturadoEsgoto"
								+ " from consumo_historico consumoHistorico"
								+ " left outer join consumo_tipo consumoTipo on consumoHistorico.cstp_id=consumoTipo.cstp_id"
								+ " left outer join consumo_historico consumoHistoricoEsgoto on consumoHistorico.imov_id = consumoHistoricoEsgoto.imov_id and consumoHistorico.cshi_amfaturamento = consumoHistoricoEsgoto.cshi_amfaturamento and consumoHistoricoEsgoto.lgti_id = "
								+ " where consumoHistorico.imov_id = " + idImovel + " and consumoHistorico.lgti_id = "
								+ LigacaoTipo.LIGACAO_ESGOTO + " and consumoHistorico.cshi_amfaturamento = " + anoMes
								+ " order by consumoHistorico.cshi_amfaturamento";

				retorno = (Object[]) session.createSQLQuery(consulta).addScalar("mesAno", Hibernate.INTEGER).addScalar("consumoMedio",
								Hibernate.INTEGER).addScalar("consumoFaturado", Hibernate.INTEGER).addScalar("consumoTipo",
								Hibernate.STRING).uniqueResult();

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
	 * Método que retorna os imoveis condominiais e esteja com ligados ou
	 * cortados a agua e ou ligados com esgoto que possuam hidrometro no poço
	 * das rotas passadas
	 * [UC0103] Efetuar Rateio de Consumo
	 * 
	 * @author Thiago Toscano, Pedro Alexandre
	 * @date 07/04/2006, 04/09/2006
	 * @param rotas
	 * @return Imoveis
	 */
	public Collection getImovelCondominioParaCalculoDoRateioDasRotas(Collection rotas) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{

			if(rotas != null && rotas.size() > 0){

				Object obj = rotas.iterator().next();

				consulta = "select imovel.id ,imovel.ligacaoAguaSituacao.id,imovel.ligacaoEsgotoSituacao.id,imovel.ligacaoAguaSituacao.indicadorFaturamentoSituacao, "
								+ "imovel.ligacaoEsgotoSituacao.indicadorFaturamentoSituacao "
								+ "from "
								+ "gcom.cadastro.imovel.Imovel imovel "
								+ "inner join imovel.quadra quadra "
								+ "inner join imovel.rota rota "
								+ "where "
								+ "imovel.indicadorImovelCondominio = "
								+ ConstantesSistema.SIM
								// + " and (imovel.ligacaoAguaSituacao.id in(" +
								// LigacaoAguaSituacao.CORTADO + "," + LigacaoAguaSituacao.LIGADO +
								// " ) or "
								// + " (imovel.ligacaoEsgotoSituacao.id = " +
								// LigacaoEsgotoSituacao.LIGADO + " and  " +
								// "  imovel.hidrometroInstalacaoHistorico is not null ))";

								+ " and (imovel.ligacaoAguaSituacao.indicadorFaturamentoSituacao = "
								+ LigacaoAguaSituacao.FATURAMENTO_ATIVO
								+ " ) or "
								+ " (imovel.ligacaoEsgotoSituacao.indicadorFaturamentoSituacao = "
								+ LigacaoEsgotoSituacao.FATURAMENTO_ATIVO
								+ " and  "
								+ "  imovel.hidrometroInstalacaoHistorico is not null ))";

				if(obj instanceof Rota){
					consulta += "and rota in (:idsRotas) ";
				}else if(obj instanceof Integer){
					consulta += "and rota.id in (:idsRotas) ";
				}

				retorno = session.createQuery(consulta).setParameterList("idsRotas", rotas).list();
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
	 * Método que retorna todos os imoveis veinculados a um imovel condominio
	 * [UC0103] Efetuar Rateio de Consumo
	 * 
	 * @author Thiago Toscano, Pedro Alexandre
	 * @date 07/04/2006, 04/09/2006
	 * @param rotas
	 * @return Imoveis
	 */
	public Collection getImovelVinculadosImovelCondominio(Integer idImovelCondominio) throws ErroRepositorioException{

		Collection retorno = new ArrayList();

		Session session = HibernateUtil.getSession();
		String consulta;

		try{

			// gerando o hql das rotas
			consulta = "select "
							+ "imov.id,imov.quantidadeEconomias,last.id, lest.id, imov.hidrometroInstalacaoHistorico.id,imov.consumoTarifa.id, lagu.id, hidi.id, "
							+ "last.indicadorFaturamentoSituacao, lest.indicadorFaturamentoSituacao, imov.rota.faturamentoGrupo.id, imov.localidade.id, imov.quadra.id "
							+ "from " + "Imovel imov " + "left join imov.ligacaoAguaSituacao last "
							+ "left join imov.ligacaoEsgotoSituacao lest " + "left join imov.imovelCondominio imovelCondominio "
							+ "left join imov.ligacaoAgua lagu " + "left join lagu.hidrometroInstalacaoHistorico hidi "
							+ "where imovelCondominio.id = :idImovelCondominio and imov.indicadorExclusao =  " + ConstantesSistema.NAO
							+ " and ((last.id in(" + LigacaoAguaSituacao.LIGADO + ","
							+ LigacaoAguaSituacao.CORTADO + "," + LigacaoAguaSituacao.CORTADO_PEDIDO + ","
							+ LigacaoAguaSituacao.CORT_MED_INDIVIDUAL + ", " + LigacaoAguaSituacao.SUPRIMIDO + ")) or " + "(lest.id = "
							+ LigacaoEsgotoSituacao.LIGADO + "))";

			retorno = session.createQuery(consulta).setInteger("idImovelCondominio", idImovelCondominio).list();

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
	 * Método que retorna o consumo de ligacao da agua ou esgoto (tipo passado)
	 * de um imovel em um determinado anoMes do faturamento. Método utilizado
	 * pra saber a ligacao de
	 * 2.2.2.2 e 2.2.3.2 do [UC0103] Efetuar Rateio de Consumo
	 * 
	 * @author Administrador
	 * @date 07/04/2006
	 * @param idImovel
	 * @param anoMes
	 * @param idLigacaoTipo
	 *            podendo ser agua ou esgoto
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object obterConsumoLigacaoAguaOuEsgotoDoImovel(Integer idImovel, Integer anoMes, Integer idLigacaoTipo)
					throws ErroRepositorioException{

		Object retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "		consumoHistorico.id, consumoHistorico.numeroConsumoFaturadoMes, pocoTipo.id " + "from "
							+ "		gcom.micromedicao.consumo.ConsumoHistorico consumoHistorico "
							+ "		inner join consumoHistorico.ligacaoTipo ligacaoTipo " + "		inner join consumoHistorico.imovel imovel"
							+ "     left join imovel.pocoTipo pocoTipo " + "where " + " 	imovel.id = " + idImovel
							+ " 	and ligacaoTipo.id = " + idLigacaoTipo + " 	and consumoHistorico.referenciaFaturamento = " + anoMes + " ";

			retorno = session.createQuery(consulta).setMaxResults(1).uniqueResult();

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
	 * Método que retorna o consumo de ligacao da agua ou esgoto (tipo passado)
	 * de um imovel em um determinado anoMes do faturamento. Método utilizado
	 * pra saber a ligacao de
	 * 2.2.2.2 e 2.2.3.2 do [UC0103] Efetuar Rateio de Consumo
	 * 
	 * @author Sávio Luiz
	 * @date 07/04/2006
	 * @param idImovel
	 * @param anoMes
	 * @param idLigacaoTipo
	 *            podendo ser agua ou esgoto
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer obterConsumoLigacaoAguaOuEsgotoDoImovelPeloTipoLogacao(Integer idImovel, Integer anoMes, Integer idLigacaoTipo)
					throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "		consumoHistorico.numeroConsumoFaturadoMes " + "from "
							+ "		gcom.micromedicao.consumo.ConsumoHistorico consumoHistorico "
							+ "		inner join consumoHistorico.ligacaoTipo ligacaoTipo " + "		inner join consumoHistorico.imovel imovel"
							+ " " + "where " + " 	imovel.id = " + idImovel + " 	and ligacaoTipo.id = " + idLigacaoTipo
							+ " 	and consumoHistorico.referenciaFaturamento = " + anoMes + " ";

			Object pesquisa = session.createQuery(consulta).setMaxResults(1).uniqueResult();

			if(pesquisa != null){
				retorno = (Integer) pesquisa;
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
	 * Método que retorna o consumo de ligacao da agua ou esgoto (tipo passado)
	 * de um imovel em um determinado anoMes do faturamento. Método utilizado
	 * pra saber a ligacao de
	 * 2.2.2.2 e 2.2.3.2 do [UC0103] Efetuar Rateio de Consumo
	 * 
	 * @author Sávio Luiz
	 * @date 07/04/2006
	 * @param idImovel
	 * @param anoMes
	 * @param idLigacaoTipo
	 *            podendo ser agua ou esgoto
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterConsumoAnteriorAnormalidadeDoImovel(Integer idImovel, Integer anoMes, Integer idLigacaoTipo)
					throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "		consumoHistorico.numeroConsumoFaturadoMes,consAnormalidade.descricaoAbreviada " + "from "
							+ "		gcom.micromedicao.consumo.ConsumoHistorico consumoHistorico "
							+ "     left join consumoHistorico.consumoAnormalidade consAnormalidade "
							+ "		left join consumoHistorico.ligacaoTipo ligacaoTipo " + "		left join consumoHistorico.imovel imovel" + " "
							+ "where " + " 	imovel.id = " + idImovel + " 	and ligacaoTipo.id = " + idLigacaoTipo
							+ " 	and consumoHistorico.referenciaFaturamento = " + anoMes + " ";

			retorno = (Object[]) session.createQuery(consulta).setMaxResults(1).uniqueResult();

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
	 * Método que retorna um consumoHistorico do imovel com o anoMes passado
	 * 
	 * @author thiago toscano
	 * @date 18/04/2006
	 * @param idImovel
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ConsumoHistorico obterConsumoHistoricoImovel(Integer idImovel, Integer anoMes, Integer idLigacaoTipo)
					throws ErroRepositorioException{

		ConsumoHistorico retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select consumoHistorico from " + "ConsumoHistorico consumoHistorico "
							+ "inner join consumoHistorico.imovel imovel " + "inner join consumoHistorico.ligacaoTipo ligacaoTipo "
							+ "where ligacaoTipo.id = :idLigacaoTipo " + "and imovel.id = :idImovel "
							+ "and consumoHistorico.referenciaFaturamento = :anoMes ";

			retorno = (ConsumoHistorico) session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("anoMes", anoMes)
							.setInteger("idLigacaoTipo", idLigacaoTipo).setMaxResults(1).uniqueResult();

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
	 * Método que retorna o id da leitura anormalidade do faturamento no caso do
	 * tipo de ligação ser agua
	 * [UC0348] Emitir Contas
	 * [SB0003] Obter Dados do Consumo e Medicao Anterior
	 * 
	 * @author Sávio Luiz
	 * @date 17/05/2006
	 * @param idImovel
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarIdLeituraAnormalidadeTipoAgua(Integer idImovel, Integer anoMes) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select laf.id " + "from MedicaoHistorico mh " + "left join mh.leituraAnormalidadeFaturamento laf "
							+ "left join mh.ligacaoAgua la " + "where la.id = :idImovel and " + "mh.medicaoTipo.id = :idMedicaoTipo and "
							+ "mh.anoMesReferencia = :anoMesReferencia";

			retorno = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setInteger("idMedicaoTipo",
							MedicaoTipo.LIGACAO_AGUA.intValue()).setInteger("anoMesReferencia", anoMes.intValue()).setMaxResults(1)
							.uniqueResult();

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
	 * Método que retorna o id da leitura anormalidade do faturamento no caso do
	 * tipo de ligação ser esgoto
	 * [UC0348] Emitir Contas
	 * [SB0003] Obter Dados do Consumo e Medicao Anterior
	 * 
	 * @author Sávio Luiz
	 * @date 17/05/2006
	 * @param idImovel
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarIdLeituraAnormalidadeTipoEsgoto(Integer idImovel, Integer anoMes) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select laf.id " + "from MedicaoHistorico mh " + "left join mh.leituraAnormalidadeFaturamento laf "
							+ "left join mh.imovel imov " + "where imov.id = :idImovel and " + "mh.medicaoTipo.id = :idMedicaoTipo and "
							+ "mh.anoMesReferencia = :anoMesReferencia";

			retorno = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setInteger("idMedicaoTipo",
							MedicaoTipo.LIGACAO_AGUA.intValue()).setInteger("anoMesReferencia", anoMes.intValue()).setMaxResults(1)
							.uniqueResult();

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
	 * Método que retorna um array de Object com informações do histórico de medição com tipo de
	 * medição agua
	 * [UC0348] Emitir Contas
	 * [SB0004] Obter Dados de medição da conta
	 * 
	 * @author Sávio Luiz
	 * @date 17/05/2006
	 * @author Saulo Lima
	 * @date 13/10/2009
	 *       Melhoria de desempenho
	 * @param idImovel
	 * @param anoMes
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosMedicaoContaTipoAgua(Integer idImovel, Integer anoMes) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{
			// consulta = "select md.leituraAnteriorFaturamento,"
			// + // 0
			// "md.leituraAtualFaturamento, "
			// + // 1
			// "md.dataLeituraAtualFaturamento, "
			// + // 2
			// "md.dataLeituraAnteriorFaturamento, "
			// + // 3
			// "leituraSituacaoAtual.id, "
			// + // 4
			// "leituraAnormalidadeFaturamento.id, "// 5
			// + // 6
			// "md.numeroConsumoMes "
			// + "from MedicaoHistorico md	"
			// + "left join md.leituraSituacaoAtual leituraSituacaoAtual "
			// + "left join md.leituraAnormalidadeFaturamento leituraAnormalidadeFaturamento "
			// + "where md.ligacaoAgua.id = :idImovel and "
			// + "md.medicaoTipo.id = :idMedicaoTipo and "
			// + "md.anoMesReferencia = :anoMes ";

			consulta = "SELECT md.leituraAnteriorFaturamento, " // 0
							+ "md.leituraAtualFaturamento, " // 1
							+ "md.dataLeituraAtualFaturamento, " // 2
							+ "md.dataLeituraAnteriorFaturamento, " // 3
							+ "md.leituraSituacaoAtual.id, " // 4
							+ "md.leituraAnormalidadeFaturamento.id, "// 5
							+ "md.numeroConsumoMes " // 6
							+ "FROM MedicaoHistorico md	"
							+ "WHERE md.ligacaoAgua.id = :idImovel "
							+ "AND md.medicaoTipo.id = :idMedicaoTipo " + "AND md.anoMesReferencia = :anoMes";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setInteger("idMedicaoTipo",
							MedicaoTipo.LIGACAO_AGUA.intValue()).setInteger("anoMes", anoMes.intValue()).setMaxResults(1).uniqueResult();

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
	 * Método que retorna um array de Object com informações do histórico de medição com tipo de
	 * medição poco
	 * [UC0348] Emitir Contas
	 * [SB0004] Obter Dados de medição da conta
	 * 
	 * @author Sávio Luiz
	 * @date 17/05/2006
	 * @author Saulo Lima
	 * @date 13/10/2009
	 *       Melhoria de desempenho
	 * @param idImovel
	 * @param anoMes
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosMedicaoContaTipoPoco(Integer idImovel, Integer anoMes) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;

		try{
			// consulta = "select md.leituraAnteriorFaturamento,"
			// + // 0
			// "md.leituraAtualFaturamento, "
			// + // 1
			// "md.dataLeituraAtualFaturamento, "
			// + // 2
			// "md.dataLeituraAnteriorFaturamento, "
			// + // 3
			// "leituraSituacaoAtual.id, "
			// + // 4
			// "leituraAnormalidadeFaturamento.id, "// 5
			// + // 6
			// "md.numeroConsumoMes "
			// + "from MedicaoHistorico md	"
			// + "left join md.leituraSituacaoAtual leituraSituacaoAtual "
			// + "left join md.leituraAnormalidadeFaturamento leituraAnormalidadeFaturamento "
			// + "where md.imovel.id = :idImovel and "
			// + "md.medicaoTipo.id = :idMedicaoTipo and "
			// + "md.anoMesReferencia = :anoMes ";

			consulta = "SELECT md.leituraAnteriorFaturamento, "
							+ // 0
							"md.leituraAtualFaturamento, "
							+ // 1
							"md.dataLeituraAtualFaturamento, "
							+ // 2
							"md.dataLeituraAnteriorFaturamento, "
							+ // 3
							"md.leituraSituacaoAtual.id, "
							+ // 4
							"md.leituraAnormalidadeFaturamento.id, "
							+ // 5
							"md.numeroConsumoMes " // 6
							+ "FROM MedicaoHistorico md	" + "WHERE md.imovel.id = :idImovel AND "
							+ "md.medicaoTipo.id = :idMedicaoTipo AND " + "md.anoMesReferencia = :anoMes ";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setInteger("idMedicaoTipo",
							MedicaoTipo.POCO.intValue()).setInteger("anoMes", anoMes.intValue()).setMaxResults(1).uniqueResult();

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
	 * Método que retorna um array de Object com informações do histórico de consumo com tipo de
	 * medição poco
	 * [UC0348] Emitir Contas
	 * [SB0006] Obter Dados de consumo da conta
	 * 
	 * @author Sávio Luiz
	 * @date 19/05/2006
	 * @author Saulo Lima
	 * @date 13/10/2009
	 *       Melhoria de desempenho
	 * @param idImovel
	 * @param anoMes
	 * @param idTipoLigacao
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosConsumoConta(Integer idImovel, int anoMesReferencia, Integer idTipoLigacao) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			// consulta = "select conTipo.descricaoAbreviada, "// 0
			// + "conTipo.descricao, "// 1
			// + "ch.consumoMedio, "// 2
			// + "consAnormalidade.descricaoAbreviada, "// 3
			// + "consAnormalidade.descricao, "// 4
			// + "ch.consumoRateio "// 5
			// + "from ConsumoHistorico ch "
			// + "inner join ch.imovel imovel "
			// + "inner join ch.ligacaoTipo lt "
			// + "left join ch.consumoTipo conTipo "
			// + "left join ch.consumoAnormalidade consAnormalidade "
			// + "where imovel.id = :idImovel and ch.referenciaFaturamento = :anoMes  and "
			// + "lt.id = :idTipoLigacao";

			consulta = "SELECT conTipo.descricaoAbreviada, " // 0
							+ "conTipo.descricao, " // 1
							+ "ch.consumoMedio, " // 2
							+ "consAnormalidade.descricaoAbreviada, " // 3
							+ "consAnormalidade.descricao, " // 4
							+ "ch.consumoRateio, " // 5
							+ "conTipo.id, " // 6
							+ "consAnormalidade.id " // 7
							+ "FROM ConsumoHistorico ch "
							+ "LEFT JOIN ch.consumoTipo conTipo "
							+ "LEFT JOIN ch.consumoAnormalidade consAnormalidade "
							+ "WHERE ch.imovel.id = :idImovel "
							+ "AND ch.referenciaFaturamento = :anoMes " + "AND ch.ligacaoTipo.id = :idTipoLigacao";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("anoMes", anoMesReferencia)
							.setInteger("idTipoLigacao", idTipoLigacao.intValue()).setMaxResults(1).uniqueResult();

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
	 * Método que retorna o maior código de Rota de um Setor Comercial
	 * 
	 * @author Vivianne Sousa
	 * @date 12/07/2006
	 * @param idSetorComercial
	 * @return
	 * @throws ControladorException
	 */
	public Short pesquisarMaximoCodigoRota(Integer idSetorComercial) throws ErroRepositorioException{

		Short retorno = 0;
		Object maxCodigoRota = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT max(r.codigo) " + "FROM Rota r " + "INNER JOIN r.setorComercial setorC "
							+ "WHERE setorC.id = :idSetorComercial ";

			maxCodigoRota = session.createQuery(consulta).setInteger("idSetorComercial", idSetorComercial).setMaxResults(1).uniqueResult();

			if(maxCodigoRota != null){
				retorno = ((Number) maxCodigoRota).shortValue();
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Método que retorna o maior código de Rota a partir de um Grupo de Faturamento.
	 * 
	 * @author Virgínia Melo
	 * @date 18/02/2009
	 * @param idFaturamentoGrupo
	 * @return
	 * @throws ControladorException
	 */
	public Short pesquisarMaximoCodigoRotaGrupoFaturamento(Integer idFaturamentoGrupo) throws ErroRepositorioException{

		Short retorno = 0;
		Object maxCodigoRota = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT max(r.codigo) " + "FROM Rota r " + "INNER JOIN r.faturamentoGrupo fatGrupo "
							+ "WHERE fatGrupo.id = :idFaturamentoGrupo ";

			maxCodigoRota = session.createQuery(consulta).setInteger("idFaturamentoGrupo", idFaturamentoGrupo).setMaxResults(1)
							.uniqueResult();

			if(maxCodigoRota != null){
				retorno = ((Number) maxCodigoRota).shortValue();
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Método que remove RotaAcaoCriterio
	 * 
	 * @author Vivianne Sousa
	 * @date 12/07/2006
	 * @param id
	 * @param operacaoEfetuada
	 * @param acaoUsuarioHelper
	 * @return
	 * @throws ControladorException
	 */
	public void removerRotaAcaoCriterio(int id, OperacaoEfetuada operacaoEfetuada, Collection<UsuarioAcaoUsuarioHelper> acaoUsuarioHelper)
					throws ErroRepositorioException{

		// obtém a sessão
		Session session = HibernateUtil.getSession();

		try{

			// Remove RotaAcaoCriterio
			Iterator iteratorRotaAcaoCriterio = session.createQuery("from " + RotaAcaoCriterio.class.getName() + " where rota_id = :id")
							.setInteger("id", id).iterate();

			if(!iteratorRotaAcaoCriterio.hasNext()){
				throw new RemocaoRegistroNaoExistenteException();

			}

			while(iteratorRotaAcaoCriterio.hasNext()){
				Object obj = iteratorRotaAcaoCriterio.next();
				if(obj instanceof ObjetoTransacao && operacaoEfetuada != null){
					ObjetoTransacao objetoTransacao = (ObjetoTransacao) obj;
					objetoTransacao.setOperacaoEfetuada(operacaoEfetuada);
					Iterator it = acaoUsuarioHelper.iterator();
					while(it.hasNext()){
						UsuarioAcaoUsuarioHelper helper = (UsuarioAcaoUsuarioHelper) it.next();
						objetoTransacao.adicionarUsuario(helper.getUsuario(), helper.getUsuarioAcao());
					}
				}
				iteratorRotaAcaoCriterio.remove();
			}

			session.flush();
			// restrições no sistema
		}catch(JDBCException e){
			// e.printStackTrace();
			// levanta a exceção para a próxima camada
			throw new RemocaoInvalidaException(e);
			// erro no hibernate
		}catch(CallbackException e){
			throw new ErroRepositorioException(e, e.getMessage());

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Método utilizado para pesquisar os consumo historicos a serem
	 * substituidos pelo caso de uso [UC0106] Substituir Consumos Anteriores
	 * Correção em tamanho de nome de coluna em 19/11/08 André Nishimura.
	 */
	public Collection<Object[]> pesquisaConsumoHistoricoSubstituirConsumo(Integer idImovel, Integer anoMesInicial, Integer anoMesFinal)
					throws ErroRepositorioException{

		Collection<Object[]> retorno = new ArrayList<Object[]>();

		Session session = HibernateUtil.getSession();

		try{
			if(!Util.isVazioOuBranco(anoMesInicial) && !Util.isVazioOuBranco(anoMesFinal)){
				StringBuilder hql = null;

				Query query = null;

				ConsumoHistorico consumoHistoricoAgua = null;
				ConsumoHistorico consumoHistoricoEsgoto = null;
				Integer anoMesReferencia = null;

				Object[] retornoAux = null;

				for(int referencia = anoMesInicial; referencia < Util.somaUmMesAnoMesReferencia(anoMesFinal); referencia = Util
								.somaUmMesAnoMesReferencia(referencia)){

					// Água
					hql = new StringBuilder();

					hql.append("select csha ");
					hql.append("from ConsumoHistorico csha ");
					hql.append("left join fetch csha.consumoTipo cstp ");
					hql.append("left join fetch csha.consumoAnormalidade csan ");
					hql.append("where csha.referenciaFaturamento = :referencia ");
					hql.append("  and csha.imovel.id = :idImovel ");
					hql.append("  and csha.ligacaoTipo.id = :ligacaoTipoAgua");

					query = session.createQuery(hql.toString());

					query.setInteger("referencia", referencia);
					query.setInteger("idImovel", idImovel);
					query.setInteger("ligacaoTipoAgua", LigacaoTipo.LIGACAO_AGUA);

					consumoHistoricoAgua = (ConsumoHistorico) query.setMaxResults(1).uniqueResult();

					// Esgoto
					hql = new StringBuilder();

					hql.append("select cshe ");
					hql.append("from ConsumoHistorico cshe ");
					hql.append("left join fetch cshe.consumoTipo cstp ");
					hql.append("left join fetch cshe.consumoAnormalidade csan ");
					hql.append("where cshe.referenciaFaturamento = :referencia ");
					hql.append("  and cshe.imovel.id = :idImovel ");
					hql.append("  and cshe.ligacaoTipo.id = :ligacaoTipoEsgoto");

					query = session.createQuery(hql.toString());

					query.setInteger("referencia", referencia);
					query.setInteger("idImovel", idImovel);
					query.setInteger("ligacaoTipoEsgoto", LigacaoTipo.LIGACAO_ESGOTO);

					consumoHistoricoEsgoto = (ConsumoHistorico) query.setMaxResults(1).uniqueResult();

					// Referência
					hql = new StringBuilder();

					hql.append("select ftgr.anoMesReferencia ");
					hql.append("from Imovel imov ");
					hql.append("inner join imov.rota rota ");
					hql.append("inner join rota.faturamentoGrupo ftgr ");
					hql.append("where imov.id = :idImovel ");

					query = session.createQuery(hql.toString());

					query.setInteger("idImovel", idImovel);

					anoMesReferencia = (Integer) query.setMaxResults(1).uniqueResult();

					if(consumoHistoricoAgua != null || consumoHistoricoEsgoto != null){
						retornoAux = new Object[3];
						retornoAux[0] = consumoHistoricoAgua;
						retornoAux[1] = consumoHistoricoEsgoto;
						retornoAux[2] = anoMesReferencia;

						retorno.add(retornoAux);
					}
				}
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Atualiza o valor de cshi_nnconsumoFaturadomes consumo historico [UC0106] -
	 * Substituir Consumos Anteriores
	 * 
	 * @throws ErroRepositorioException
	 */
	public void atualizarConsumosAnteriores(ConsumoHistorico consumoHistorico) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			String sql = "update gcom.micromedicao.consumo.ConsumoHistorico " + " set numeroConsumoFaturadoMes = "
							+ consumoHistorico.getNumeroConsumoFaturadoMes() + ", ultimaAlteracao = :data ,"
							+ " indicadorAlteracaoUltimosConsumos = 1" + " where id = " + consumoHistorico.getId();

			session.createQuery(sql).setTimestamp("data", new Date()).executeUpdate();
		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Atualiza o valor de cshi_nnconsumomedio, cshi_nnconsumomediohidrometro
	 * consumo historico [UC0106] - Substituir Consumos Anteriores
	 * 
	 * @throws ErroRepositorioException
	 */
	public void atualizarConsumosMedio(Integer idImovel, Integer anoMesGrupoFaturamento, int consumoMedioImovel,
					int consumoMedioHidrometroAgua, int consumoMedioHidrometroEsgoto) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			String sql = "update gcom.micromedicao.consumo.ConsumoHistorico " + " set consumoMedio = " + consumoMedioImovel
							+ ", ultimaAlteracao = :data" + " where  imovel = " + idImovel + " and referenciaFaturamento = "
							+ anoMesGrupoFaturamento;

			session.createQuery(sql).setTimestamp("data", new Date()).executeUpdate();

			String sql2 = "";
			if(consumoMedioHidrometroAgua != 0){
				sql2 = "update gcom.micromedicao.medicao.MedicaoHistorico " + " set consumoMedioHidrometro = " + consumoMedioHidrometroAgua
								+ ", ultimaAlteracao = :data" + " where ligacaoAgua = " + idImovel + " and anoMesReferencia = "
								+ anoMesGrupoFaturamento;
			}else{
				sql2 = "update gcom.micromedicao.medicao.MedicaoHistorico " + " set consumoMedioHidrometro = "
								+ consumoMedioHidrometroEsgoto + ", ultimaAlteracao = :data" + " where imovel = " + idImovel
								+ " and anoMesReferencia = " + anoMesGrupoFaturamento;
			}

			session.createQuery(sql2).setTimestamp("data", new Date()).executeUpdate();
		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * Atualizar Analise excecoes consumo resumido
	 * 
	 * @throws ErroRepositorioException
	 */
	public void atualizarLeituraConsumoResumido(MedicaoHistorico medicaoHistorico) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			boolean ligacaoAgua = false;
			Integer idImovel = null;
			if(medicaoHistorico.getLigacaoAgua() != null){
				ligacaoAgua = true;
				idImovel = medicaoHistorico.getLigacaoAgua().getId();
			}else{
				ligacaoAgua = false;
				idImovel = medicaoHistorico.getImovel().getId();
			}

			String sql = "update gcom.micromedicao.consumo.ConsumoHistorico as consumoHistorico" + " set ultimaAlteracao = :data"
							+ " where consumoHistorico.imovel.id = " + idImovel + " and consumoHistorico.referenciaFaturamento ="
							+ medicaoHistorico.getMesAno();

			String sqlMedicao = "update gcom.micromedicao.medicao.MedicaoHistorico as medicaoHistorico"
							+ " set medicaoHistorico.dataLeituraAnteriorFaturamento = " + " :dataLeituraAnteriorFaturamento"
							+ ", medicaoHistorico.leituraAnteriorFaturamento = " + medicaoHistorico.getLeituraAnteriorFaturamento()
							+ ", medicaoHistorico.dataLeituraAtualInformada = " + " :dataLeituraAtualInformada"
							+ ", medicaoHistorico.leituraAtualInformada = " + medicaoHistorico.getLeituraAtualInformada()
							+ ", medicaoHistorico.leituraAnormalidadeFaturamento = "
							+ medicaoHistorico.getLeituraAnormalidadeFaturamento().getId() + ", medicaoHistorico.ultimaAlteracao = :data, "
							+ "numeroConsumoInformado = " + medicaoHistorico.getNumeroConsumoInformado()
							+ " , medicaoHistorico.leituraSituacaoAtual.id = :idleituraSituacaoAtual"
							+ " where medicaoHistorico.anoMesReferencia =" + medicaoHistorico.getMesAno();

			if(ligacaoAgua){
				sqlMedicao = sqlMedicao + " and medicaoHistorico.ligacaoAgua.id =" + idImovel;
			}else{
				sqlMedicao = sqlMedicao + " and medicaoHistorico.imovel.id =" + idImovel;
			}

			session.createQuery(sql).setTimestamp("data", new Date()).executeUpdate();

			session.createQuery(sqlMedicao).setTimestamp("dataLeituraAnteriorFaturamento",
							medicaoHistorico.getDataLeituraAnteriorFaturamento()).setTimestamp("dataLeituraAtualInformada",
							medicaoHistorico.getDataLeituraAtualInformada()).setTimestamp("data", new Date()).setInteger(
							"idleituraSituacaoAtual", medicaoHistorico.getLeituraSituacaoAtual().getId()).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Registrar leituras e anormalidades Autor:Sávio Luiz
	 * 
	 * @author eduardo henrique
	 * @date 19/01/2009
	 *       Alteraçao na forma de Consulta para montar coleção de retorno, para
	 *       utilizar todos os campos de índice disponível
	 *       // TODO Refatorar método para utilizar ou coleção de Integers ou Coleção de Imoveis
	 * @author eduardo henrique
	 * @date 10/02/2009
	 *       Alteracao no metodo para utilizacao otimizada do indice.
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarMedicaoHistorico(Collection idsImovel, Integer anoMes, Integer idMedicaoTipo)
					throws ErroRepositorioException{

		Collection retorno = new ArrayList<Object[]>();

		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			for(Object imovel : idsImovel){
				Integer idImovel = null;
				if(imovel instanceof Imovel){
					idImovel = ((Imovel) imovel).getId();
				}else if(imovel instanceof Integer){
					idImovel = (Integer) imovel;
				}

				consulta = "";
				if(idMedicaoTipo.equals(MedicaoTipo.POCO)){
					consulta = "Select " + " medicaohis0_.mdhi_id as idMedicaoHistorico, " + " imovel2_.imov_id as idImovel "
									+ " from  medicao_historico medicaohis0_ "
									+ " inner join  imovel imovel2_ on medicaohis0_.imov_id=imovel2_.imov_id " + " where "
									+ " medicaohis0_.mdhi_amleitura = :anoMesReferencia  and "
									+ " medicaohis0_.medt_id = :idMedicaoTipo and " + " imovel2_.imov_id = (:idImovel) ";

				}
				if(idMedicaoTipo.equals(MedicaoTipo.LIGACAO_AGUA)){
					consulta = " Select " + " medicaohis0_.mdhi_id as idMedicaoHistorico, " + " ligacaoagu1_.lagu_id as idImovel "
									+ " from  medicao_historico medicaohis0_ "
									+ " inner join  ligacao_agua ligacaoagu1_   on medicaohis0_.lagu_id=ligacaoagu1_.lagu_id " + " where "
									+ " medicaohis0_.mdhi_amleitura = :anoMesReferencia  and "
									+ " medicaohis0_.medt_id= :idMedicaoTipo and " + " ligacaoagu1_.lagu_id = (:idImovel) ";

				}
				retorno.addAll(session.createSQLQuery(consulta).addScalar("idMedicaoHistorico", Hibernate.INTEGER).addScalar("idImovel",
								Hibernate.INTEGER).setInteger("idImovel", idImovel).setInteger("idMedicaoTipo", idMedicaoTipo).setInteger(
								"anoMesReferencia", anoMes.intValue()).list());
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
	 * [UC0082] - Registrar Leituras e Anormalidades Autor: Sávio Luiz Data:
	 * 04/01/2006
	 * 
	 * @author eduardo henrique
	 * @date 11/09/2008
	 *       Alteração para obter o CreditoConsumoAnterior do mês da Referência, segundo alterações
	 *       no [UC0082]/[UC0712]
	 *       // TODO Refatorar método para utilizar ou coleção de Integers ou Coleção de Imoveis
	 * @author eduardo henrique
	 * @date 10/02/2009
	 *       Alteracao no metodo para utilizacao otimizada do indice.
	 */

	public Collection pesquisarMedicaoHistoricoAnterior(Collection colecaoIdsImoveis, Integer anoMes, Integer idMedicaoTipo)
					throws ErroRepositorioException{

		Collection retorno = new ArrayList<Object[]>();
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			for(Object imovel : colecaoIdsImoveis){
				Integer idImovel = null;
				if(imovel instanceof Imovel){
					idImovel = ((Imovel) imovel).getId();
				}else if(imovel instanceof Integer){
					idImovel = (Integer) imovel;
				}

				consulta = "";
				if(idMedicaoTipo.equals(MedicaoTipo.POCO)){
					consulta = "Select "
									+ " medicaohis0_.mdhi_dtleituraatualfaturamento as dataLeituraAtualFat, "
									+ // 0
									" medicaohis0_.mdhi_nnleituraatualfaturamento as leituraAtualFat, "
									+ // 1
									" medicaohis0_.mdhi_nnleituraatualinformada as leituraAtualInf, "
									+ // 2
									" medicaohis0_.ltst_idleiturasituacaoatual as idLeituraSitAtual, "
									+ // 3
									" hidrometro3_.hidi_id as idHidrometroInstHist, "
									+ // 4
									" hidrometro3_.hidi_dtinstalacaohidrometro as dataInstalacao, "
									+ // 5
									" medicaohis0_.imov_id as idImovel, "
									+ // 6
									" medicaohis0_.mdhi_nnconsumocreditoanterior as consumoCreditoAnterior  " // 7
									+ " from  medicao_historico medicaohis0_ "
									+ " inner join  imovel imovel2_ on medicaohis0_.imov_id=imovel2_.imov_id "
									+ " left outer join  hidrometro_instalacao_hist hidrometro3_  on imovel2_.hidi_id=hidrometro3_.hidi_id "
									+ " where " + " medicaohis0_.mdhi_amleitura = :anoMesReferencia  and "
									+ " medicaohis0_.medt_id = :idMedicaoTipo and " + " imovel2_.imov_id = (:idImovel) ";

				}
				if(idMedicaoTipo.equals(MedicaoTipo.LIGACAO_AGUA)){
					consulta = " Select "
									+ " medicaohis0_.mdhi_dtleituraatualfaturamento as dataLeituraAtualFat, "
									+ // 0
									" medicaohis0_.mdhi_nnleituraatualfaturamento as leituraAtualFat, "
									+ // 1
									" medicaohis0_.mdhi_nnleituraatualinformada as leituraAtualInf, "
									+ // 2
									" medicaohis0_.ltst_idleiturasituacaoatual as idLeituraSitAtual, "
									+ // 3
									" hidrometro3_.hidi_id as idHidrometroInstHist, "
									+ // 4
									" hidrometro3_.hidi_dtinstalacaohidrometro as dataInstalacao, "
									+ // 5
									" medicaohis0_.lagu_id as idImovel, "
									+ // 6
									" medicaohis0_.mdhi_nnconsumocreditoanterior as consumoCreditoAnterior  " // 7
									+ " from  medicao_historico medicaohis0_ "
									+ " inner join  ligacao_agua ligacaoagu1_   on medicaohis0_.lagu_id=ligacaoagu1_.lagu_id "
									+ " left outer join  hidrometro_instalacao_hist hidrometro3_  on ligacaoagu1_.hidi_id=hidrometro3_.hidi_id "
									+ " where " + " medicaohis0_.mdhi_amleitura = :anoMesReferencia  and "
									+ " medicaohis0_.medt_id= :idMedicaoTipo and " + " ligacaoagu1_.lagu_id = (:idImovel) ";

				}

				retorno.addAll(session.createSQLQuery(consulta).addScalar("dataLeituraAtualFat", Hibernate.DATE).addScalar(
								"leituraAtualFat", Hibernate.INTEGER).addScalar("leituraAtualInf", Hibernate.INTEGER).addScalar(
								"idLeituraSitAtual", Hibernate.INTEGER).addScalar("idHidrometroInstHist", Hibernate.INTEGER).addScalar(
								"dataInstalacao", Hibernate.DATE).addScalar("idImovel", Hibernate.INTEGER).addScalar(
								"consumoCreditoAnterior", Hibernate.INTEGER).setInteger("idImovel", idImovel).setInteger("idMedicaoTipo",
								idMedicaoTipo).setInteger("anoMesReferencia", anoMes.intValue()).list());
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

	public void inseriMedicaoHistorico(Collection colecaoMedicaoHistorico) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		Iterator iteratorColecaoMedicaoHistorico = colecaoMedicaoHistorico.iterator();

		try{
			while(iteratorColecaoMedicaoHistorico.hasNext()){

				MedicaoHistorico medicaoHistorico = (MedicaoHistorico) iteratorColecaoMedicaoHistorico.next();

				session.save(medicaoHistorico);

				/*
				 * if (i % 50 == 0) { // 20, same as the JDBC batch size //
				 * flush a batch of inserts and release memory: session.flush();
				 * session.clear(); } i++;
				 */
			}
			session.flush();
			session.clear();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
			// session.close();
		}

	}

	// ----------Savio
	/**
	 * [UC0082] / [UC0712] - Registrar Leituras e Anormalidades A
	 * 
	 * @author eduardo henrique
	 * @date 12/09/2008
	 *       Alterações na geração da Medição Histórico, adição da atualização do id da Situação
	 *       Atual da Leitura
	 */
	public void atualizarMedicaoHistorico(Collection medicaoHistoricoAtualizar) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{

			Iterator iteMedicaoHistorico = medicaoHistoricoAtualizar.iterator();
			while(iteMedicaoHistorico.hasNext()){
				MedicaoHistorico medicaoHistorico = (MedicaoHistorico) iteMedicaoHistorico.next();

				String atualizarMedicaoHistorico;

				atualizarMedicaoHistorico = "update gcom.micromedicao.medicao.MedicaoHistorico "
								+ "set mdhi_tmultimaalteracao = :ultimaAlteracao ";
				if(medicaoHistorico.getFuncionario() != null && medicaoHistorico.getFuncionario().getId() != null){
					atualizarMedicaoHistorico = atualizarMedicaoHistorico + ",func_id = " + medicaoHistorico.getFuncionario().getId();
				}
				if(medicaoHistorico.getLeituraAnormalidadeInformada() != null
								&& medicaoHistorico.getLeituraAnormalidadeInformada().getId() != null){
					atualizarMedicaoHistorico = atualizarMedicaoHistorico + ",ltan_idleituraanormalidadeinfo = "
									+ medicaoHistorico.getLeituraAnormalidadeInformada().getId();
				}
				if(medicaoHistorico.getDataLeituraAtualInformada() != null && !medicaoHistorico.getDataLeituraAtualInformada().equals("")){
					atualizarMedicaoHistorico = atualizarMedicaoHistorico + ",mdhi_dtleituraatualinformada = :dataLeituraAtualInformada ";

					atualizarMedicaoHistorico += ",mdhi_dtleituraatualfaturamento = :dataLeituraAtualFaturamento ";
				}
				if(medicaoHistorico.getLeituraAtualInformada() != null && !medicaoHistorico.getLeituraAtualInformada().equals("")){
					atualizarMedicaoHistorico = atualizarMedicaoHistorico + ",mdhi_nnleituraatualinformada = "
									+ medicaoHistorico.getLeituraAtualInformada();

					atualizarMedicaoHistorico += ",mdhi_nnleituraatualfaturamento = " + medicaoHistorico.getLeituraAtualInformada();
				}
				if(medicaoHistorico.getLeituraSituacaoAtual() != null){
					atualizarMedicaoHistorico = atualizarMedicaoHistorico + ",ltst_idleiturasituacaoatual = "
									+ medicaoHistorico.getLeituraSituacaoAtual().getId();
				}

				atualizarMedicaoHistorico = atualizarMedicaoHistorico + " where mdhi_id = :idMedicaoHistorico";

				if(medicaoHistorico.getDataLeituraAtualInformada() != null && !medicaoHistorico.getDataLeituraAtualInformada().equals("")){
					session.createQuery(atualizarMedicaoHistorico).setInteger("idMedicaoHistorico", medicaoHistorico.getId()).setTimestamp(
									"ultimaAlteracao", medicaoHistorico.getUltimaAlteracao()).setTimestamp("dataLeituraAtualInformada",
									medicaoHistorico.getDataLeituraAtualInformada()).setTimestamp("dataLeituraAtualFaturamento",
									medicaoHistorico.getDataLeituraAtualInformada()).executeUpdate();
				}else{
					session.createQuery(atualizarMedicaoHistorico).setInteger("idMedicaoHistorico", medicaoHistorico.getId()).setTimestamp(
									"ultimaAlteracao", medicaoHistorico.getUltimaAlteracao()).executeUpdate();
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
	 * Registrar leituras e anormalidades Autor:Sávio Luiz
	 * utilizado no [UC0088]
	 * 
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarHidrometroInstalacaoHistorico(Collection idsImovel) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "Select (CASE WHEN hidInsHist.lagu_id is not null THEN hidInsHist.lagu_id ELSE hidInsHist.imov_id END) as idImovel "
							+ " from hidrometro_instalacao_hist  hidInsHist " + " where hidInsHist.hidi_dtretiradahidrometro is null AND "
							+ " hidInsHist.lagu_id in (:idsImovel) or hidInsHist.imov_id in (:idsImovel)";

			retorno = session.createSQLQuery(consulta).addScalar("idImovel", Hibernate.INTEGER).setParameterList("idsImovel", idsImovel)
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
	 * Método que retorna o número da leitura de retirada do hidrômetro
	 * 
	 * @author Ana Maria
	 * @date 25/09/2006
	 * @param idLigacaoAgua
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarNumeroLeituraRetiradaLigacaoAgua(Integer idLigacaoAgua) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select mdhi.leituraAtualFaturamento " + "from MedicaoHistorico mdhi " + "left join mdhi.ligacaoAgua lagu "
							+ "where lagu.id = :idLigacaoAgua " + "order by mdhi.anoMesReferencia desc";

			retorno = (Integer) session.createQuery(consulta).setInteger("idLigacaoAgua", idLigacaoAgua).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Método que retorna o número da leitura de retirada do hidrômetro
	 * 
	 * @author Ana Maria
	 * @date 25/09/2006
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarNumeroLeituraRetiradaImovel(Integer idImovel) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select mdhi.leituraAtualFaturamento " + "from MedicaoHistorico mdhi " + "left join mdhi.imovel imov "
							+ "where imov.id = :idImovel " + "order by mdhi.anoMesReferencia desc";

			retorno = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Permite pesquisar imóvel doação baseando-se em rotas [UC0394] Gerar
	 * Débitos a Cobrar de Doações
	 * 
	 * @author César Araújo
	 * @date 05/08/2006
	 * @param Collection
	 *            <Rota> rotas - Coleção de rotas
	 * @return Collection<ImovelCobrarDoacaoHelper> - Coleção de
	 *         ImovelCobrarDoacaoHelper já com as informações necessárias para
	 *         registro da cobrança
	 * @throws ErroRepositorioException
	 */
	public Collection<ImovelCobrarDoacaoHelper> pesquisarImovelDoacaoPorRota(Collection<Rota> rotas) throws ErroRepositorioException{

		/** * Declara variáveis locais ** */
		Session session = null;
		String idsRotas = "";
		Collection<ImovelCobrarDoacaoHelper> retorno = null;

		/** * Testa se a coleção de rotas está preenchida ** */
		if(rotas != null && rotas.size() > 0){

			session = HibernateUtil.getSession();

			/** * Monta string com os ids das rotas ** */
			for(Rota rota : rotas){
				idsRotas = rota.getId() + ", " + idsRotas;
			}

			/** * Somente para eliminar a vírgula ** */
			idsRotas = idsRotas.substring(0, idsRotas.length() - 2);

			/**
			 * * Script HQL que já monta uma coleção de ImovelCobrarDoacaoHelper
			 * com tudo que é necessário **
			 */
			try{
				String consulta = "select " + "  new gcom.cadastro.imovel.bean.ImovelCobrarDoacaoHelper(" + "    imovelDoacao.imovel.id,"
								+ "    imovelDoacao.entidadeBeneficente.debitoTipo.id," + "    imovelDoacao.valorDoacao,"
								+ "	   imovelDoacao.imovel.localidade.id," + "    imovelDoacao.imovel.quadra.id,"
								+ "	   imovelDoacao.imovel.setorComercial.codigo," + "	   imovelDoacao.imovel.quadra.numeroQuadra,"
								+ "    imovelDoacao.imovel.lote," + "	   imovelDoacao.imovel.subLote,"
								+ "    imovelDoacao.entidadeBeneficente.debitoTipo.financiamentoTipo.id,"
								+ "    imovelDoacao.entidadeBeneficente.debitoTipo.lancamentoItemContabil.id) " + "from"
								+ "  gcom.cadastro.imovel.ImovelDoacao imovelDoacao " + "where"
								+ "  imovelDoacao.imovel.rota.id in (:idsRotas) and" + "  imovelDoacao.dataCancelamento is null";

				// System.out.println(consulta);

				retorno = session.createQuery(consulta).setString("idsRotas", idsRotas).list();

			}catch(HibernateException e){
				/** * levanta a exceção para a próxima camada ** */
				throw new ErroRepositorioException(e, "Erro no Hibernate");
			}finally{
				/** * fecha a sessão ** */
				HibernateUtil.closeSession(session);
			}

		}

		return retorno;
	}

	// ----------Savio
	public void atualizarFaturamentoAtividadeCronograma(Integer idGrupoFaturamento, int amReferencia) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{

			String atualizar;

			atualizar = "update FaturamentoAtividadeCronograma fatAtiv set " + "ftac_tmrealizacao = :dataCorrente "
							+ ",ftac_tmultimaalteracao = :dataCorrente " + "where ftat_id = :idFaturamentoAtividade AND "
							+ "ftcm_id IN (select fgcm.id from FaturamentoGrupoCronogramaMensal fgcm where "
							+ "fgcm.anoMesReferencia = :amReferencia AND " + "fgcm.faturamentoGrupo.id = :idGrupoFaturamento)";

			session.createQuery(atualizar).setTimestamp("dataCorrente", new Date()).setInteger("idGrupoFaturamento", idGrupoFaturamento)
							.setInteger("idFaturamentoAtividade", FaturamentoAtividade.GERAR_ARQUIVO_LEITURA).setInteger("amReferencia",
											amReferencia).executeUpdate();

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
	 * Verifica se existe consumo histórico para o imóvel de acordo com o tipo
	 * de ligação
	 * 
	 * @author Ana Maria
	 * @date 17/10/2006
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarConsumoMedioImovel(Integer idImovel) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select cshi.consumoMedio " + "from ConsumoHistorico cshi "
							+ "where cshi.imovel.id = :idImovel and cshi.ligacaoTipo.id = :tipoLigacao "
							+ "order by cshi.referenciaFaturamento desc";

			retorno = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("tipoLigacao",
							LigacaoTipo.LIGACAO_AGUA).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Método que deleta o consumo histórico do imóvel
	 * 
	 * @author Leonardo Vieira
	 * @date 02/11/2006
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public void deletarConsumoHistorico(Integer idRota, int amFaturamento) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{
			String delete = "delete ConsumoHistorico " + "where rota = :idRota and referenciaFaturamento = :amFaturamento";

			session.createQuery(delete).setInteger("idRota", idRota).setInteger("amFaturamento", amFaturamento).executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	public void deletarConsumoHistoricoCondominio(Integer idRota, int amFaturamento) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{
			String delete = "delete ConsumoHistorico "
							+ "where rota = :idRota and referenciaFaturamento = :amFaturamento and consumoImovelCondominio is not null";

			session.createQuery(delete).setInteger("idRota", idRota).setInteger("amFaturamento", amFaturamento).executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	public void deletarConsumoHistoricoRETIRAR(Integer idImovel, int amFaturamento) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{
			String delete = "delete ConsumoHistorico "
							+ " where imovel = :idImovel and referenciaFaturamento = :amFaturamento"
							+ " and not exists(select conta.id from Conta conta where conta.referencia = :referenciaConta and conta.imovel.id = :idImovelConta) ";

			session.createQuery(delete).setInteger("idImovel", idImovel).setInteger("amFaturamento", amFaturamento).setInteger(
							"idImovelConta", idImovel).setInteger("referenciaConta", amFaturamento).executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	public void deletarConsumoHistoricoCondominioRETIRAR(Integer idImovel, int amFaturamento) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{
			String delete = "delete ConsumoHistorico "
							+ " where imovel = :idImovel and referenciaFaturamento = :amFaturamento and consumoImovelCondominio is not null "
							+ " and not exists(select conta.id from Conta conta where conta.referencia = :referenciaConta and conta.imovel.id = :idImovelConta) ";

			session.createQuery(delete).setInteger("idImovel", idImovel).setInteger("amFaturamento", amFaturamento).setInteger(
							"idImovelConta", idImovel).setInteger("referenciaConta", amFaturamento).executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 15/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Integer pesquisarConsumoMedio(Integer idImovel, Integer tipoMedicao) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select cshi.consumoMedio " + "from ConsumoHistorico cshi "
							+ "where cshi.imovel.id = :idImovel and cshi.ligacaoTipo.id = :tipoLigacao "
							+ "order by cshi.referenciaFaturamento desc";

			retorno = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("tipoLigacao", tipoMedicao)
							.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 15/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Collection pesquisarConsumoFaturadoQuantidadeMeses(Integer idImovel, Integer tipoMedicao, short qtdMeses)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select cshi.numeroConsumoFaturadoMes from ConsumoHistorico cshi "
							+ "where cshi.imovel.id = :idImovel and cshi.ligacaoTipo.id = :tipoLigacao "
							+ "order by cshi.referenciaFaturamento desc ";

			retorno = (Collection) session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("tipoLigacao", tipoMedicao)
							.setMaxResults(qtdMeses).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Raphael Rossiter
	 * @date 19/01/2007
	 * @param Integer
	 *            idImovel, Integer tipoMedicao
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarUltimoAnoMesConsumoFaturado(Integer idImovel, Integer tipoMedicao) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select cshi.referenciaFaturamento " + "from ConsumoHistorico cshi "
							+ "where cshi.imovel.id = :idImovel and cshi.ligacaoTipo.id = :tipoLigacao "
							+ "order by cshi.referenciaFaturamento desc ";

			retorno = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("tipoLigacao", tipoMedicao)
							.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Atualizar Hidrômetro
	 * Pesquisa o imóvel no qual o hidrômetro está instalado
	 * 
	 * @author Rafael Corrêa
	 * @date 23/11/2006
	 * @param idHidrometro
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarImovelHidrometroInstalado(Integer idHidrometro) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT imovel.id, lagu.id " + "FROM HidrometroInstalacaoHistorico hidInsHis "
							+ "INNER JOIN hidInsHis.hidrometro hidrometro " + "LEFT JOIN hidInsHis.imovel imovel "
							+ "LEFT JOIN hidInsHis.ligacaoAgua lagu " + "WHERE hidrometro.id =:idHidrometro and "
							+ "hidInsHis.dataRetirada is null";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idHidrometro", idHidrometro).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0498] - Efetuar Ligação de Água com Instalaação de Hidrômetro
	 * Pesquisa o id do hidrômetro e a sua situação pelo número
	 * 
	 * @author Rafael Corrêa
	 * @date 29/11/2006
	 * @param numeroHidrometro
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarHidrometroPeloNumero(String numeroHidrometro) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT hidr.id, " // 0
							+ "hidr.numero, " // 1
							+ "hidrSit.id, " // 2
							+ "hidrSit.descricao,"// 3
							+ "hidrCap.id, " // 4
							+ "hidrCap.descricao "// 5
							+ "FROM Hidrometro hidr "
							+ "INNER JOIN hidr.hidrometroSituacao hidrSit "
							+ "INNER JOIN hidr.hidrometroCapacidade hidrCap " + "WHERE hidr.numero =:numeroHidrometro";

			retorno = (Object[]) session.createQuery(consulta).setString("numeroHidrometro", numeroHidrometro).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 06/12/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Date pesquisarDataInstalacaoHidrometroAgua(Integer idImovel) throws ErroRepositorioException{

		Date retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select hih.dataInstalacao " + "FROM LigacaoAgua ligAgua " + "LEFT JOIN ligAgua.hidrometroInstalacaoHistorico hih "
							+ "where ligAgua.id = :idImovel ";

			retorno = (Date) session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setMaxResults(1).uniqueResult();

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
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 06/12/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Date pesquisarDataInstalacaoHidrometroPoco(Integer idImovel) throws ErroRepositorioException{

		Date retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select hih.dataInstalacao " + "FROM Imovel imov " + "LEFT JOIN imov.hidrometroInstalacaoHistorico hih "
							+ "where imov.id = :idImovel ";

			retorno = (Date) session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setMaxResults(1).uniqueResult();

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
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer pesquisarConsumoFaturaMes(int anoMesReferencia, Integer idMedicaoTipo, Integer idImovel) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select ch.numeroConsumoFaturadoMes " + "from ConsumoHistorico ch " + "left join ch.ligacaoTipo ligTipo "
							+ "left join ch.imovel imov " + "where ligTipo.id = :idLigacaoTipo and ch.referenciaFaturamento = :anoMes AND "
							+ "imov.id = :idImovel";

			retorno = (Integer) session.createQuery(consulta).setInteger("idLigacaoTipo", idMedicaoTipo).setInteger("anoMes",
							anoMesReferencia).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public Collection pesqusiarLigacoesMedicaoIndividualizada(Integer idImovel, String anoMes) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select imovel.imov_id as idImovel, " // 0
							+ " imovel.imov_qteconomia as qtdEconomias, "// 1
							+ " medicaoHistorico.mdhi_id as idMedicaoHistorico,"// 2
							+ " medicaoHistorico.mdhi_dtleituraanteriorfaturame as dataLeituraAnterior,"// 3
							+ " medicaoHistorico.mdhi_nnleituraanteriorfaturame as leituraAnterior,"// 4
							+ " medicaoHistorico.mdhi_dtleituraatualfaturamento as dataLeituraAtualFaturamento,"// 5
							+ " medicaoHistorico.mdhi_nnleituraatualfaturamento as leituraAtualFaturamento,"// 6
							+ " medicaoHistorico.mdhi_nnconsumomedidomes as consumoMedido,"// 7
							+ " medicaoHistorico.ltan_idleituraanormalidadefatu as idAnormalidadeLeitura,"// 8
							+ " consumoHistorico.cshi_id as idConsumoHistorico,"// 9
							+ " consumoHistorico.cshi_nnconsumomedio as consumoMedio,"// 10
							+ " consumoHistorico.cshi_nnconsumofaturadomes as consumoFaturado,"// 11
							+ " consumoHistorico.cshi_nnconsumorateio as rateio,"// 12
							+ " consumoAnormalidade.csan_dsabreviadaconsumoanormal as dsAbreviadaAnormalidadeConsumo,"// 13
							+ " consumoTipo.cstp_dsabreviadaconsumotipo as dsAbreviadaTipoConsumo,"// 14
							+ " medicaoTipo.medt_id as tipoMedicao,"// 15

							// 16 Consumo Esgoto
							+ " (select consumo.cshi_nnconsumofaturadomes"
							+ " from consumo_historico consumo"
							+ " where consumo.imov_id = imovel.imov_id" + " and consumo.cshi_amfaturamento = "
							+ anoMes
							+ " and consumo.lgti_id = "
							+ LigacaoTipo.LIGACAO_ESGOTO
							+ ") as consumoEsgoto,"

							+ " medicaoHistorico.mdhi_nnconsumoinformado as consumoInformado,"// 17
							+ " consumoHistorico.cshi_nnconsumoimoveisvinculado as consumosVinculados"// 18

							+ " from imovel imovel"
							+ " inner join consumo_historico consumoHistorico on imovel.imov_id=consumoHistorico.imov_id and consumoHistorico.cshi_amfaturamento = "
							+ anoMes
							+ " inner join medicao_historico medicaoHistorico on  consumoHistorico.imov_id=medicaoHistorico.lagu_id and medicaoHistorico.mdhi_amleitura = "
							+ anoMes
							+ " left outer join consumo_anormalidade consumoAnormalidade on consumoHistorico.csan_id=consumoAnormalidade.csan_id"
							+ " left outer join medicao_tipo medicaoTipo on medicaoHistorico.medt_id=medicaoTipo.medt_id"
							+ " left outer join consumo_tipo consumoTipo on consumoHistorico.cstp_id=consumoTipo.cstp_id"
							+ " where (medicaoHistorico.medt_id = "
							+ LigacaoTipo.LIGACAO_AGUA
							+ " and consumoHistorico.lgti_id = "
							+ LigacaoTipo.LIGACAO_AGUA
							+ ")"
							+ " and (imovel.imov_idimovelcondominio = "
							+ idImovel
							+ " or imovel.imov_id = " + idImovel + ") order by imovel.imov_idimovelcondominio";

			// System.out.println(consulta);

			retorno = (Collection) session.createSQLQuery(consulta).addScalar("idImovel", Hibernate.INTEGER).addScalar("qtdEconomias",
							Hibernate.INTEGER).addScalar("idMedicaoHistorico", Hibernate.INTEGER).addScalar("dataLeituraAnterior",
							Hibernate.DATE).addScalar("leituraAnterior", Hibernate.INTEGER).addScalar("dataLeituraAtualFaturamento",
							Hibernate.DATE).addScalar("leituraAtualFaturamento", Hibernate.INTEGER).addScalar("consumoMedido",
							Hibernate.INTEGER).addScalar("idAnormalidadeLeitura", Hibernate.INTEGER).addScalar("idConsumoHistorico",
							Hibernate.INTEGER).addScalar("consumoMedio", Hibernate.INTEGER).addScalar("consumoFaturado", Hibernate.INTEGER)
							.addScalar("rateio", Hibernate.INTEGER).addScalar("dsAbreviadaAnormalidadeConsumo", Hibernate.STRING)
							.addScalar("dsAbreviadaTipoConsumo", Hibernate.STRING).addScalar("tipoMedicao", Hibernate.INTEGER).addScalar(
											"consumoEsgoto", Hibernate.INTEGER).addScalar("consumoInformado", Hibernate.INTEGER).addScalar(
											"consumosVinculados", Hibernate.INTEGER).list();

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
	 * Obtém os ids de todas as rotas cadastradas
	 * [UC0251] - Gerar Atividade de Ação de Cobrança
	 * [SB0002] - Gerar Atividade de Ação de Cobrança para os Imóveis da Lista
	 * de Rotas
	 * 
	 * @author Leonardo Vieira
	 * @date 13/12/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarListaRotas() throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select rota from Rota rota ";

			retorno = (Collection) session.createQuery(consulta).list();

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
	 * Obtém os ids de todas as rotas cadastradas menos as rotas que tiverem o
	 * emp_cobranca = 1
	 * [UC0251] - Gerar Atividade de Ação de Cobrança
	 * [SB0002] - Gerar Atividade de Ação de Cobrança para os Imóveis da Lista
	 * de Rotas
	 * 
	 * @author Sávio Luiz
	 * @date 05/03/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarListaRotasEspecificas() throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select rota from Rota rota " + "left join rota.empresaCobranca empCobranca " + "where empCobranca.id = :indUm";

			retorno = (Collection) session.createQuery(consulta).setInteger("indUm", 1).list();

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
	 * [] Ligacoes Medicao Individualizada
	 * 
	 * @author Flávio Cordeiro
	 * @date 17/12/2006
	 * @param colecaoLigacoesMedicao
	 * @throws ControladorException
	 */
	public void atualizarLigacoesMedicaoIndividualizada(LigacaoMedicaoIndividualizadaHelper ligacaoMedicaoIndividualizadaHelper,
					Integer anoMes) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String update;

		try{

			update = "update MedicaoHistorico as medicao set" + " leituraAnteriorFaturamento = :leituraAnteriorFaturamento,"
							+ " dataLeituraAnteriorFaturamento = :dataLeituraAnteriorFaturamento,"
							+ " leituraAtualFaturamento = :leituraAtualFaturamento,"
							+ " dataLeituraAtualFaturamento = :dataLeituraAtualFaturamento," + " leituraAnormalidadeFaturamento = "
							+ ligacaoMedicaoIndividualizadaHelper.getIdLeituraAnormalidade() + ", numeroConsumoInformado = "
							+ ligacaoMedicaoIndividualizadaHelper.getConsumoInformado() + " where medicao.id = "
							+ ligacaoMedicaoIndividualizadaHelper.getIdMedicaoHistorico() + " and medicao.anoMesReferencia = " + anoMes;

			session.createQuery(update).setInteger("leituraAnteriorFaturamento", ligacaoMedicaoIndividualizadaHelper.getLeituraAnterior())
							.setDate("dataLeituraAnteriorFaturamento",
											Util.converteStringParaDate(ligacaoMedicaoIndividualizadaHelper.getDataLeituraAnterior()))
							.setInteger("leituraAtualFaturamento", ligacaoMedicaoIndividualizadaHelper.getLeituraAtual()).setDate(
											"dataLeituraAtualFaturamento",
											Util.converteStringParaDate(ligacaoMedicaoIndividualizadaHelper.getDataLeituraAtual()))
							.executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC0113] - Faturar Grupo de Faturamento
	 * Obter empresa da rota.
	 * [SB0006 - ]
	 * 
	 * @author Raphael Rossiter
	 * @date 09/01/2007
	 * @param rota
	 * @throws ErroRepositorioException
	 */
	public Integer obterIdEmpresaPorRota(Rota rota) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT rota.empresa.id " + "FROM Rota rota " + "WHERE rota.id = :idRota";

			retorno = (Integer) session.createQuery(consulta).setInteger("idRota", rota.getId()).setMaxResults(1).uniqueResult();

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
	 * [UC0082] - Registrar Leituras e Anormalidades
	 * [SB0001] - Gerar Relatório Resumo das Leituras e Anormalidades
	 * Registradas
	 * Obter empresa do imóvel.
	 * 
	 * @author Rafael Corrêa
	 * @date 13/04/2007
	 * @param idImovel
	 * @throws ErroRepositorioException
	 */
	public Integer obterIdEmpresaPorImovel(Integer idImovel) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT imovel.rota.empresa.id " + "FROM Imovel imovel " + "WHERE imovel.id = :idImovel";

			retorno = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

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
	 * [UC0103] Efetuar Rateio de Consumo
	 * atualiza o consumo de água/esgoto a ser rateado e o consumo de
	 * água/esgoto dos imóveis vínculados do imóvel condomínio.
	 * 
	 * @author Pedro Alexandre
	 * @date 17/01/2007
	 * @param idConsumoHistoricoImovelCondominio
	 * @param consumoRateio
	 * @param consumoImovelVinculadosCondominio
	 * @throws ErroRepositorioException
	 */
	public void atualizarConsumoHistoricoImovelCondominio(Integer idConsumoHistoricoImovelCondominio, Integer consumoRateio,
					Integer consumoImovelVinculadosCondominio) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			String sql = "update ConsumoHistorico " + "set consumoRateio = :consumoRateio "
							+ ",consumoImovelVinculadosCondominio = :consumoImovelVinculadosCondominio " + ",ultimaAlteracao = :data "
							+ "where id = :idConsumoHistoricoImovelCondominio";

			session.createQuery(sql).setInteger("consumoRateio", consumoRateio).setInteger("consumoImovelVinculadosCondominio",
							consumoImovelVinculadosCondominio).setTimestamp("data", new Date()).setInteger(
							"idConsumoHistoricoImovelCondominio", idConsumoHistoricoImovelCondominio).executeUpdate();
		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * [UC00083] Gerar Dados para Leitura
	 * [SB0002] Gerar Relação(ROL) em TXT
	 * 
	 * @author Rafael Francisco Pinto
	 * @date 19/01/2007
	 * @param colecaoRota
	 * @param tipoOrdenacao
	 * @author eduardo henrique
	 * @date 06/01/2008 Alteração no método de consulta para não realizar paginação (Hibernate
	 *       utiliza rownum no Oracle como default).
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImoveisParaLeituraPorColecaoRota(Collection idsRotas, SistemaParametro sistemaParametro)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String orderBy;

			if(SistemaParametro.INDICADOR_EMPRESA_DESO == sistemaParametro.getParmId().shortValue()){
				orderBy = " order by gerenciaRegional.id, localidade.localidade, localidade.id, "
								+ " setorComercial.codigo, rota.codigo, quadra.numeroQuadra, imovel.numeroSequencialRota, imovel.lote, imovel.subLote ";
			}else{
				orderBy = " order by gerenciaRegional.id, localidade.id, setorComercial.codigo, "
								+ "rota.codigo, imovel.numeroSequencialRota ";
			}
			if(idsRotas != null && idsRotas.size() > 0){

				Object obj = idsRotas.iterator().next();
				String consulta = "";

				consulta = "select imovel.id,"// 0
								+ "localidade.id,"// 1
								+ "setorComercial.codigo,"// 2
								+ "quadra.numeroQuadra,"// 3
								+ "imovel.lote,"// 4
								+ "imovel.subLote,"// 5
								+ "imovelPerfil.id,"// 6
								+ "ligacaoAgua.id,"// 7
								+ "hidInstHistoricoAgua.id,"// 8
								+ "hidInstHistoricoPoco.id,"// 9
								+ "rota.id,"// 10
								+ "rota.indicadorFiscalizarSuprimido,"// 11
								+ "rota.indicadorFiscalizarCortado,"// 12
								+ "rota.indicadorGerarFiscalizacao,"// 13
								+ "rota.indicadorGerarFalsaFaixa,"// 14
								+ "rota.percentualGeracaoFiscalizacao,"// 15
								+ "rota.percentualGeracaoFaixaFalsa,"// 16
								+ "empresa.id,"// 17
								+ "empresa.descricaoAbreviada,"// 18
								+ "empresa.email,"// 19
								+ "faturamentoGrupo.id,"// 20
								+ "faturamentoGrupo.descricao,"// 21
								+ "leituraTipo.id,"// 22
								+ "ligacaoAguaSituacao.id,"// 23
								+ "ligacaoEsgotoSituacao.id, "// 24
								+ "faturamentoSituacaoTipo.indicadorParalisacaoLeitura, "// 25
								+ "medTipoAgua.id, "// 26
								+ "medTipoPoco.id, "// 27
								+ "gerenciaRegional.id, "// 28
								+ "gerenciaRegional.nome, "// 29
								+ "localidade.descricao, "// 30
								+ "ligacaoAguaSituacao.descricao, "// 31
								+ "ligacaoAgua.numeroLacre, "// 32
								+ "hidroAgua.numero, "// 33
								+ "imovel.numeroSequencialRota, "// 34
								+ "rota.codigo, "// 35
								+ "hidroPoco.numero, "// 36
								+ "ligacaoAguaSituacao.indicadorFaturamentoSituacao, "// 37
								+ "ligacaoEsgotoSituacao.indicadorFaturamentoSituacao, "// 38
								+ "imovel.numeroSegmento, "// 39
								+ "hidLocInstAgua.descricao, "// 40
								+ "hidLocInstPoco.descricao, "// 41
								+ "rotaImovel.id, " // 42
								+ "leiturista.id, " // 43
								+ "consumoTarifa.id, " // 44
								+ "logradouro.id, " // 45
								+ "logradouro.nome, " // 46
								+ "bairro.id, " // 47
								+ "bairro.nome, " // 48
								+ "endRef.id, " // 49
								+ "endRef.descricao, " // 50
								+ "cep.codigo, " // 51
								+ "uniFed.id, " // 52
								+ "uniFed.descricao, " // 53
								+ "uniFed.sigla, " // 54
								+ "muni.id, " // 55
								+ "muni.nome, " // 56
								+ "logBairro.id, " // 57
								+ "logCep.id, " // 58
								+ "logradouroCep.id, " // 59
								+ "logradouroCep.nome, " // 60
								+ "hidroAgua.numeroDigitosLeitura, " // 61
								+ "hidroPoco.numeroDigitosLeitura, " // 62
								+ "elo.id, " // 63
								+ "roteiroEmp.id, " // 64
								+ "empresarRot.id, " // 65
								+ "areaTipo.descricao, " // 66
								+ "pocoTipo.id, " // 67
								+ "hidInstHistoricoAgua.dataInstalacao, " // 68
								+ "hidInstHistoricoPoco.dataInstalacao, " // 69
								+ "imovel.numeroImovel, " // 70
								+ "logtit.descricaoAbreviada, " // 71
								+ "logtip.descricaoAbreviada, " // 72
								+ "faturamentoGrupo.anoMesReferencia "// 73
								+ "from Imovel imovel "
								+ "left join imovel.localidade localidade "
								+ "left join localidade.localidade elo "
								+ "left join imovel.setorComercial setorComercial "
								+ "left join imovel.quadra quadra "
								+ "left join imovel.imovelPerfil imovelPerfil "
								+ "left join imovel.ligacaoAgua ligacaoAgua "
								+ "left join imovel.hidrometroInstalacaoHistorico hidInstHistoricoPoco "
								+ "left join ligacaoAgua.hidrometroInstalacaoHistorico hidInstHistoricoAgua "
								+ "left join hidInstHistoricoAgua.hidrometro hidroAgua "
								+ "left join hidInstHistoricoAgua.medicaoTipo medTipoAgua "
								+ "left join hidInstHistoricoAgua.hidrometroLocalInstalacao hidLocInstAgua "
								+ "left join hidInstHistoricoPoco.medicaoTipo medTipoPoco "
								+ "left join hidInstHistoricoPoco.hidrometro hidroPoco "
								+ "left join hidInstHistoricoPoco.hidrometroLocalInstalacao hidLocInstPoco "
								+ "left join imovel.rota rota "
								+ "left join imovel.logradouroBairro logBairro "
								+ "left join logBairro.bairro bairro "
								+ "left join logBairro.logradouro logradouro "
								+ "left join logradouro.logradouroTitulo logtit "
								+ "left join logradouro.logradouroTipo logtip "
								+ "left join imovel.enderecoReferencia endRef "
								+ "left join imovel.logradouroCep logCep "
								+ "left join logCep.cep cep "
								+ "left join logCep.logradouro logradouroCep "
								+ "left join logradouroCep.municipio municipioCep "
								+ "left join municipioCep.unidadeFederacao uniFed "
								+ "left join logradouroCep.logradouroTitulo "
								+ "left join logradouroCep.logradouroTipo "
								+ "left join bairro.municipio muni "
								+ "left join rota.leiturista leiturista "
								+ "left join rota.empresa empresa "
								+ "left join rota.faturamentoGrupo faturamentoGrupo "
								+ "left join imovel.ligacaoAguaSituacao ligacaoAguaSituacao "
								+ "left join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao "
								+ "left join imovel.consumoTarifa consumoTarifa "
								+ "left join imovel.faturamentoSituacaoTipo faturamentoSituacaoTipo "
								+ "left join rota.leituraTipo leituraTipo "
								+ "left join localidade.gerenciaRegional gerenciaRegional "
								+ "left join quadra.roteiroEmpresa roteiroEmp "
								+ "left join roteiroEmp.empresa empresarRot "
								+ "left join imovel.rota rotaImovel "
								+ "left join quadra.areaTipo areaTipo "
								+ "left join imovel.pocoTipo pocoTipo ";
				if(obj instanceof Rota){
					consulta += "where rota in (:idsRotas) ";
				}else if(obj instanceof Integer){
					consulta += "where rota.id in (:idsRotas) ";
				}

				consulta += orderBy;

				if(idsRotas != null && idsRotas.size() > 1000){
					Collection colecaoAuxiliar = new ArrayList<Object>();
					retorno = new ArrayList<Object>();
					for(Object id : idsRotas){
						colecaoAuxiliar.add(id);
						if(colecaoAuxiliar.size() == 1000){
							retorno.addAll(session.createQuery(consulta).setParameterList("idsRotas", colecaoAuxiliar).list());
							colecaoAuxiliar.clear();
						}
					}
					if(!colecaoAuxiliar.isEmpty()){
						retorno.addAll(session.createQuery(consulta).setParameterList("idsRotas", colecaoAuxiliar).list());
					}
				}else{
					retorno = session.createQuery(consulta).setParameterList("idsRotas", idsRotas).list();
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
	 * [UC00083] Gerar Dados para Leitura
	 * [SB0002] Gerar Relação(ROL) em TXT - Registro 2 (DOIS)
	 * 
	 * @author Rafael Francisco Pinto
	 * @date 22/01/2007
	 * @param idImovel
	 * @param anoMesReferencia
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarNumeroConsumoMedioImovel(Integer idImovel, int anoMesReferencia, Integer idLigacaoTipo)
					throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT ch.consumoMedio " + "FROM ConsumoHistorico ch " + "INNER JOIN ch.imovel imov "
							+ "INNER JOIN ch.ligacaoTipo ligacaoTipo " + "WHERE imov.id = :id " + "AND ch.referenciaFaturamento = :anoMes "
							+ "AND ligacaoTipo.id = :idLigacaoTipo ";

			retorno = (Integer) session.createQuery(consulta).setInteger("id", idImovel).setInteger("anoMes", anoMesReferencia).setInteger(
							"idLigacaoTipo", idLigacaoTipo).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC00083] Gerar Dados para Leitura
	 * [SB0002] Gerar Relação(ROL) em TXT - Registro 1 (HUM)
	 * 
	 * @author Rafael Francisco Pinto
	 * @date 23/01/2007
	 * @param idImovel
	 * @param anoMesReferencia
	 * @param idTipoLigacao
	 * @throws ErroRepositorioException
	 */
	public String obterDescricaoConsumoTipo(Integer idImovel, Integer idTipoLigacao) throws ErroRepositorioException{

		String retorno = null;
		Object[] retornoConsulta = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT consumoTipo.id,consumoTipo.descricao,max(consumoHistorico.referenciaFaturamento) "
							+ "FROM ConsumoHistorico consumoHistorico " + "INNER JOIN consumoHistorico.imovel imovel "
							+ "INNER JOIN consumoHistorico.ligacaoTipo ligacaoTipo "
							+ "INNER JOIN consumoHistorico.consumoTipo consumoTipo " + "WHERE imovel.id = :idImovel "
							+ "AND ligacaoTipo.id = :idTipoLigacao " + "GROUP BY consumoTipo.id,consumoTipo.descricao";

			retornoConsulta = (Object[]) session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("idTipoLigacao",
							idTipoLigacao).setMaxResults(1).uniqueResult();

			if(retornoConsulta != null && retornoConsulta.length > 0){
				retorno = (String) retornoConsulta[1];
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC00083] Gerar Dados para Leitura
	 * 
	 * @author Sávio Luiz
	 * @date 13/03/2007
	 * @author eduardo henrique
	 * @date 25/08/2008
	 *       Alteração para adicionar a Tarifa de Consumo do Imóvel e Leiturista da Rota na consulta
	 * @author eduardo henrique
	 * @date 06/01/2008
	 *       Alteração no método de consulta para não realizar paginação (Hibernate utiliza rownum
	 *       no Oracle como default).
	 * @throws ErroRepositorioException
	 * @author eduardo henrique
	 * @date 27/01/2009
	 *       Alteração no método para inclusão do Código da Rota no MovimentoRoteiroEmpresa
	 * @author eduardo henrique
	 * @date 06/02/2009
	 *       Alteração no método para retirada do Param. de Tipo de Leitura.
	 */
	public Collection pesquisarImoveisPorRotaOrdenadoPorInscricao(Collection colecaoRota) throws ErroRepositorioException{

		Collection retorno = null;
		Collection idsRotas = new ArrayList();
		Iterator iteColecaoRotas = colecaoRota.iterator();
		while(iteColecaoRotas.hasNext()){
			Rota rota = (Rota) iteColecaoRotas.next();
			idsRotas.add(rota.getId());
		}

		Session session = HibernateUtil.getSession();

		try{
			String consulta = "select imovel.id,"
							+ // 0
							"localidade.id,"
							+ // 1
							"setorComercial.codigo,"
							+ // 2
							"quadra.numeroQuadra,"
							+ // 3
							"imovel.lote,"
							+ // 4
							"imovel.subLote,"
							+ // 5
							"imovelPerfil.id,"
							+ // 6
							"ligacaoAgua.id,"
							+ // 7
							"hidInstHistoricoAgua.id,"
							+ // 8
							"hidInstHistoricoPoco.id,"
							+ // 9
							"rota.id,"
							+ // 10
							"rota.indicadorFiscalizarSuprimido,"
							+ // 11
							"rota.indicadorFiscalizarCortado,"
							+ // 12
							"rota.indicadorGerarFiscalizacao,"
							+ // 13
							"rota.indicadorGerarFalsaFaixa,"
							+ // 14
							"rota.percentualGeracaoFiscalizacao,"
							+ // 15
							"rota.percentualGeracaoFaixaFalsa,"
							+ // 16
							"empresa.id,"
							+ // 17
							"empresa.descricaoAbreviada,"
							+ // 18
							"empresa.email,"
							+ // 19
							"faturamentoGrupo.id,"
							+ // 20
							"faturamentoGrupo.descricao,"
							+ // 21
							"leituraTipo.id,"
							+ // 22
							"ligacaoAguaSituacao.id,"
							+ // 23
							"ligacaoEsgotoSituacao.id, "
							+ // 24
							"faturamentoSituacaoTipo.indicadorParalisacaoLeitura, "// 25
							+ "medTipoAgua.id, "// 26
							+ "medTipoPoco.id, "// 27
							+ "empresa.descricao, " // 28
							+ "roteiroEmpresa, "// 29
							+ "hidInstHistoricoAgua,"// 30
							+ "hidInstHistoricoPoco, "// 31
							+ "empresaRoteiro.id, " // 32
							+ "ligacaoAguaSituacao.indicadorFaturamentoSituacao, " // 33
							+ "ligacaoEsgotoSituacao.indicadorFaturamentoSituacao, " // 34
							+ "consumoTarifa.id, " // 35
							+ "leituristaRota.id, " // 36
							+ "areaTipo.descricao, " // 37
							+ "pocoTipo.id, " // 38
							+ "rota.codigo, " // 39
							+ "quadra " // 40
							+ "from Imovel imovel " + "left join imovel.localidade localidade "
							+ "left join imovel.setorComercial setorComercial " + "left join imovel.quadra quadra "
							+ "left join quadra.areaTipo areaTipo " + "left join imovel.imovelPerfil imovelPerfil "
							+ "left join imovel.ligacaoAgua ligacaoAgua "
							+ "left join imovel.hidrometroInstalacaoHistorico hidInstHistoricoPoco "
							+ "left join imovel.consumoTarifa consumoTarifa " + "left join imovel.pocoTipo pocoTipo "
							+ "left join ligacaoAgua.hidrometroInstalacaoHistorico hidInstHistoricoAgua "
							+ "left join hidInstHistoricoAgua.hidrometroProtecao "
							+ "left join hidInstHistoricoAgua.hidrometroLocalInstalacao "
							+ "left join hidInstHistoricoPoco.hidrometroProtecao "
							+ "left join hidInstHistoricoPoco.hidrometroLocalInstalacao "
							+ "left join hidInstHistoricoAgua.medicaoTipo medTipoAgua "
							+ "left join hidInstHistoricoPoco.medicaoTipo medTipoPoco " + "left join imovel.rota rota "
							+ "left join rota.leiturista leituristaRota " + "left join rota.empresa empresa "
							+ "left join quadra.roteiroEmpresa roteiroEmpresa " + "left join roteiroEmpresa.empresa empresaRoteiro "
							+ "left join rota.faturamentoGrupo faturamentoGrupo "
							+ "left join imovel.ligacaoAguaSituacao ligacaoAguaSituacao "
							+ "left join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao "
							+ "left join imovel.faturamentoSituacaoTipo faturamentoSituacaoTipo "
							+ "left join rota.leituraTipo leituraTipo " + "where rota.id in (:idsRotas) "
							+ "order by empresa.id,localidade.id,setorComercial.codigo,quadra.numeroQuadra,imovel.lote,imovel.subLote";

			retorno = session.createQuery(consulta).setParameterList("idsRotas", idsRotas).list();

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
	 * Pesquisa todas as rotas do sistema. Metódo usado no [UC0302] Gerar Débito
	 * a Cobrar de Acréscimos por Impontualidade
	 * 
	 * @author Pedro Alexandre
	 * @date 20/03/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarListaRotasCarregadas() throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select rota from Rota rota ";

			retorno = (Collection) session.createQuery(consulta).list();

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
	 * [UC0105] Obter Consumo Mínimo da Ligação por Subcategoria
	 * (CSTC_NNCONSUMOMINIMO da tarifa associada ao imóvel na tabela
	 * CONSUMO_TARIFA_CATEGORIA com SCAT_ID=Id da subcategoria e CSTV_ID =
	 * CSTV_ID da tabela CONSUMO_TARIFA_VIGENCIA com CSTF_ID=CSTF_ID da tabela
	 * IMOVEL e maior CSTV_DTVIGENCIA, que seja menor ou igual a data corrente)
	 * 
	 * @author Raphael Rossiter
	 * @date 11/04/2007
	 * @return subcategoria, consumoTarifaVigencia
	 * @throws ControladorException
	 */
	public Object pesquisarConsumoMinimoTarifaSubcategoriaVigencia(Subcategoria subcategoria, ConsumoTarifaVigencia consumoTarifaVigencia)
					throws ErroRepositorioException{

		Object retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String consulta = "select ct.numeroConsumoMinimo " + "from ConsumoTarifaCategoria ct "
							+ "inner join ct.consumoTarifaVigencia.consumoTarifa " + "inner join ct.subCategoria scat "
							+ "where ct.consumoTarifaVigencia.id = :consumoTarifaVigenciaId " + "and ct.subCategoria.id = :subCategoriaId ";

			retorno = session.createQuery(consulta).setInteger("consumoTarifaVigenciaId", consumoTarifaVigencia.getId()).setInteger(
							"subCategoriaId", subcategoria.getId()).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0082] - Registrar Leituras e Anormalidades
	 * [SB0001] - Gerar Relatório Resumo das Leituras e Anormalidades
	 * Pesquisa os dados do relatório do comparativo de leituras e anormalidades
	 * 
	 * @author Rafael Corrêa
	 * @date 13/04/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDadosRelatorioComparativoLeiturasEAnormalidades(Integer idGrupoFaturamento, Integer idEmpresa, Integer anoMes)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String consulta = "select "
							+ " imovel.loca_id as idLocalidade, "
							+ " setor.stcm_id as idSetor, "
							+ " setor.stcm_cdsetorcomercial as codigoSetor, "
							+ " rota.ftgr_id as idGrupo, "
							+ " empresa.empr_id as idEmpresa, "
							+ " empresa.empr_nmempresa as nomeEmpresa, "
							+ " count(medi.mdhi_id) as recebidos, "
							+ " (select "
							+ " count(medi1.mdhi_id) "
							+ " from medicao_historico medi1 "
							+ " inner join imovel imovel1 on imovel1.imov_id = medi1.lagu_id "
							+ " inner join quadra quadra1 on imovel1.qdra_id = quadra1.qdra_id "
							+ " inner join rota rota1 on imovel1.rota_id = rota1.rota_id "
							+ " inner join setor_comercial setor1 on setor1.stcm_id = imovel1.stcm_id "
							+ " where medi1.mdhi_amleitura = :anoMes ";
			if(idEmpresa != null){
				consulta += " and rota1.empr_id = :idEmpresa ";
			}
			consulta += " and rota1.ftgr_id = :idGrupoFaturamento "
							+ " and medi1.mdhi_nnleituraatualinformada is not null "
							+ " and medi1.mdhi_nnleituraatualinformada <> 0 "
							+ " and medi1.ltan_idleituraanormalidadeinfo is null "
							+ " and imovel1.loca_id = imovel.loca_id and setor1.stcm_id = setor.stcm_id "
							+ " ) as comLeitura, "
							+ " (select "
							+ " count(medi2.mdhi_id) "
							+ " from medicao_historico medi2 "
							+ " inner join imovel imovel2 on imovel2.imov_id = medi2.lagu_id "
							+ " inner join quadra quadra2 on imovel2.qdra_id = quadra2.qdra_id "
							+ " inner join rota rota2 on imovel2.rota_id = rota2.rota_id "
							+ " inner join setor_comercial setor2 on setor2.stcm_id = imovel2.stcm_id "
							+ " where medi2.mdhi_amleitura = :anoMes ";
			if(idEmpresa != null){
				consulta += " and rota2.empr_id = :idEmpresa ";
			}
			consulta += " and rota2.ftgr_id = :idGrupoFaturamento"
 + " and medi2.ltan_idleituraanormalidadeinfo is not null "
							+ "  and (medi2.mdhi_nnleituraatualinformada is null or medi2.mdhi_nnleituraatualinformada = 0) "
							+ " and imovel2.loca_id = imovel.loca_id and setor2.stcm_id = setor.stcm_id "
							+ " ) as comAnormalidade, "
							+ " (select "
							+ " count(medi3.mdhi_id) "
							+ " from medicao_historico medi3 "
							+ " inner join imovel imovel3 on imovel3.imov_id = medi3.lagu_id "
							+ " inner join quadra quadra3 on imovel3.qdra_id = quadra3.qdra_id "
							+ " inner join rota rota3 on imovel3.rota_id = rota3.rota_id "
							+ " inner join setor_comercial setor3 on setor3.stcm_id = imovel3.stcm_id "
							+ " where medi3.mdhi_amleitura = :anoMes ";
			if(idEmpresa != null){
				consulta += " and rota3.empr_id = :idEmpresa ";
			}
			consulta += " and rota3.ftgr_id = :idGrupoFaturamento "
							+ " and medi3.mdhi_nnleituraatualinformada is not null "
							+ " and medi3.mdhi_nnleituraatualinformada <> 0 "
							+ " and medi3.ltan_idleituraanormalidadeinfo is not null "
							+ " and imovel3.loca_id = imovel.loca_id and setor3.stcm_id = setor.stcm_id "
							+ " ) as comLeituraAnormalidade "
							+ " from medicao_historico medi "
							+ " inner join imovel imovel on imovel.imov_id = medi.lagu_id "
							+ " inner join quadra quadra on imovel.qdra_id = quadra.qdra_id "
							+ " inner join rota rota on imovel.rota_id = rota.rota_id "
							+ " inner join empresa empresa on rota.empr_id = empresa.empr_id "
							+ " inner join setor_comercial setor on setor.stcm_id = imovel.stcm_id "
							+ " where medi.mdhi_amleitura = :anoMes ";
			if(idEmpresa != null){
				consulta += " and rota.empr_id = :idEmpresa ";
			}
			consulta += " and rota.ftgr_id = :idGrupoFaturamento "
							+ " and (medi.mdhi_nnleituraatualinformada is not null "
							+ " or medi.ltan_idleituraanormalidadeinfo is not null) "
							+ " group by rota.ftgr_id, empresa.empr_id, "
							+ " empresa.empr_nmempresa, imovel.loca_id, setor.stcm_id, setor.stcm_cdsetorcomercial "
							+ " order by imovel.loca_id, setor.stcm_cdsetorcomercial ";

			Query queryRetorno = session.createSQLQuery(consulta).addScalar("idLocalidade", Hibernate.INTEGER)
							.addScalar("idSetor", Hibernate.INTEGER)
							.addScalar("codigoSetor", Hibernate.INTEGER).addScalar("idGrupo", Hibernate.INTEGER).addScalar("idEmpresa",
											Hibernate.INTEGER).addScalar("nomeEmpresa", Hibernate.STRING).addScalar("recebidos",
											Hibernate.INTEGER).addScalar("comLeitura", Hibernate.INTEGER).addScalar("comAnormalidade",
 Hibernate.INTEGER)
							.addScalar("comLeituraAnormalidade", Hibernate.INTEGER).setInteger("anoMes", anoMes)
							.setInteger("idGrupoFaturamento", idGrupoFaturamento);

			if(idEmpresa != null){
				queryRetorno.setInteger("idEmpresa", idEmpresa);
			}

			retorno = queryRetorno.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0082] - Registrar Leituras e Anormalidades
	 * [SB0001] - Gerar Relatório Resumo das Leituras e Anormalidades
	 * Pesquisa as Anormalidades de Leitura e suas quantidades
	 * 
	 * @author Rafael Corrêa
	 * @date 13/04/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarAnormalidadesRelatorioComparativoLeiturasEAnormalidades(Integer idGrupoFaturamento, Integer idEmpresa,
					Integer anoMes) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String consulta = "select "
							+ " anormalidade.ltan_id as idLeitura, "
							+ " anormalidade.ltan_dsleituraanormalidade as descricao, "
							+ " rota.ftgr_id as grupoFaturamento, "
							+ " empresa.empr_id as idEmpresa, "
							+ " empresa.empr_nmempresa as nomeEmpresa, "
							+ " count(medi.ltan_idleituraanormalidadeinfo) as qtd "
							+ " from medicao_historico medi "
							+ " inner join leitura_anormalidade anormalidade on medi.ltan_idleituraanormalidadeinfo = anormalidade.ltan_id "
							+ " inner join imovel imovel on imovel.imov_id = medi.lagu_id "
							+ " inner join quadra quadra on imovel.qdra_id = quadra.qdra_id "
							+ " inner join rota rota on imovel.rota_id = rota.rota_id "
							+ " inner join empresa empresa on rota.empr_id = empresa.empr_id "
 + " where medi.mdhi_amleitura = :anoMes ";
			if(idEmpresa != null){
				consulta += " and rota.empr_id = :idEmpresa";
			}
			consulta += " and rota.ftgr_id = :idGrupoFaturamento "
							// + " and medi.mdhi_tmultimaalteracao =
							// :dataUltimaAlteracao "
							+ " group by rota.ftgr_id, empresa.empr_id, empresa.empr_nmempresa, anormalidade.ltan_id, anormalidade.ltan_dsleituraanormalidade "
							+ " order by anormalidade.ltan_dsleituraanormalidade ";

			Query queryRetorno = session.createSQLQuery(consulta).addScalar("idLeitura", Hibernate.INTEGER)
							.addScalar("descricao", Hibernate.STRING)
							.addScalar("grupoFaturamento", Hibernate.INTEGER).addScalar("idEmpresa", Hibernate.INTEGER).addScalar(
											"nomeEmpresa", Hibernate.STRING).addScalar("qtd", Hibernate.INTEGER).setInteger("anoMes",
 anoMes)
							.setInteger("idGrupoFaturamento", idGrupoFaturamento);
			if(idEmpresa != null){
				queryRetorno.setInteger("idEmpresa", idEmpresa);
			}
			retorno = queryRetorno.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * @author Vivianne Sousa
	 * @date 06/06/2007
	 * @param idImovel
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosConsumoHistoricoDoImovel(Integer idImovel, Integer anoMes) throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "consumoHistorico.numeroConsumoFaturadoMes, " + "consAnormalidade.descricaoAbreviada " + "from "
							+ "gcom.micromedicao.consumo.ConsumoHistorico consumoHistorico "
							+ "left join consumoHistorico.consumoAnormalidade consAnormalidade "
							+ "left join consumoHistorico.ligacaoTipo ligacaoTipo " + "left join consumoHistorico.imovel imovel "
							+ "where imovel.id = :idImovel " + "and ligacaoTipo.id = :idLigacaoTipo "
							+ "and consumoHistorico.referenciaFaturamento = :anoMes ";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("idLigacaoTipo",
							LigacaoTipo.LIGACAO_AGUA).setInteger("anoMes", anoMes).setMaxResults(1).uniqueResult();

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
	 * @author Vivianne Sousa
	 * @date 07/06/2007
	 * @param idImovel
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosMedicaoHistoricoDoImovel(Integer idImovel, Integer anoMes) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select mh.dataLeituraAtualInformada, mh.leituraAtualInformada, "
							+ "mh.leituraAnteriorInformada, mh.leituraAnteriorFaturamento from MedicaoHistorico mh "
							+ "left join mh.ligacaoAgua ligacaoAgua where ligacaoAgua.id = :idImovel and "
							+ "mh.medicaoTipo.id = :idMedicaoTipo and mh.anoMesReferencia = :anoMesReferencia";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setInteger("idMedicaoTipo",
							MedicaoTipo.LIGACAO_AGUA.intValue()).setInteger("anoMesReferencia", anoMes.intValue()).setMaxResults(1)
							.uniqueResult();

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
	 * [UC0613] - Registrar Leituras e Anormalidades
	 * Pesquisa os imóveis com faixa falsa
	 * 
	 * @author Rafael Corrêa
	 * @date 18/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImovelFaixaFalsa(Integer anoMesReferencia) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String consulta = "SELECT ftgr.ftgr_id as idGrupo, ftgr.ftgr_dsfaturamentogrupo as descricaoGrupo, " // 0,1
							+ " empresa.empr_id as idEmpresa, empresa.empr_nmempresa as nomeEmpresa, " // 2,3
							+ " loc.loca_id as idLocalidade, loc.loca_nmlocalidade as nomeLocalidade, " // 4,5
							+ " stcm.stcm_id as idSetor, stcm.stcm_cdsetorcomercial as codSetor, " // 6,7
							+ " stcm.stcm_nmsetorcomercial as nomeSetor, " // 8
							+ " leitFaixaFalsa.fxer_nnleiturafalsainferior as leituraFalsaInferior, " // 9
							+ " mdhi.mdhi_nnleituraatualinformada as leituraInformada, " // 10
							+ " leitFaixaFalsa.fxer_nnleiturafalsasuperior as leituraFalsaSuperior, " // 11
							+ " quadra.qdra_nnquadra as numeroQuadra, imov.imov_nnlote as lote, " // 12,13
							+ " imov.imov_nnsublote as sublote, imov.imov_id as idImovel, " // 14,15
							+ " func.func_id as idLeiturista, " // 16
							+ " leitFaixaFalsa.fxer_nnleituracorretainferior as leituraCorretaInferior, " // 17
							+ " leitFaixaFalsa.fxer_nnleituracorretasuperior as leituraCorretaSuperior, " // 18
							+ " mdhi.mdhi_dtleituraatualinformada as dataLeitura, " // 19
							+ " leitAnorm.ltan_dsleituraanormalidade as leituraAnormalidade, " // 20
							+ " leitSit.ltst_dsleiturasituacao as situacaoLeitura " // 21
							+ " FROM leitura_faixa_falsa leitFaixaFalsa "
							+ " INNER JOIN medicao_historico mdhi "
							+ " on mdhi.mdhi_id = leitFaixaFalsa.mdhi_id "
							+ " INNER JOIN imovel imov "
							+ " on imov.imov_id = mdhi.imov_id or imov.imov_id = mdhi.lagu_id "
							+ " INNER JOIN setor_comercial stcm "
							+ " on stcm.stcm_id = imov.stcm_id " + " INNER JOIN quadra quadra "
							+ " on quadra.qdra_id = imov.qdra_id "
							+ " INNER JOIN rota rota " + " on rota.rota_id = imov.rota_id "
							+ " INNER JOIN faturamento_grupo ftgr "
							+ " on ftgr.ftgr_id = rota.ftgr_id "
							+ " LEFT OUTER JOIN funcionario func "
							+ " on func.func_id = mdhi.func_id "
							+ " LEFT OUTER JOIN empresa empresa "
							+ " on empresa.empr_id = func.empr_id "
							+ " INNER JOIN localidade loc "
							+ " on loc.loca_id = imov.loca_id "
							+ " LEFT OUTER JOIN leitura_anormalidade leitAnorm "
							+ " on leitAnorm.ltan_id = mdhi.ltan_idleituraanormalidadeinfo "
							+ " LEFT OUTER JOIN leitura_situacao leitSit "
							+ " on leitSit.ltst_id = mdhi.ltst_idleiturasituacaoatual "
							+ " WHERE mdhi.mdhi_amleitura = :anoMesReferencia "
							+ " ORDER BY idGrupo, idEmpresa, idLocalidade, codSetor, numeroQuadra, lote, sublote ";

			retorno = session.createSQLQuery(consulta).addScalar("idGrupo", Hibernate.INTEGER)
							.addScalar("descricaoGrupo", Hibernate.STRING).addScalar("idEmpresa", Hibernate.INTEGER).addScalar(
											"nomeEmpresa", Hibernate.STRING).addScalar("idLocalidade", Hibernate.INTEGER).addScalar(
											"nomeLocalidade", Hibernate.STRING).addScalar("idSetor", Hibernate.INTEGER).addScalar(
											"codSetor", Hibernate.INTEGER).addScalar("nomeSetor", Hibernate.STRING).addScalar(
											"leituraFalsaInferior", Hibernate.INTEGER).addScalar("leituraInformada", Hibernate.INTEGER)
							.addScalar("leituraFalsaSuperior", Hibernate.INTEGER).addScalar("numeroQuadra", Hibernate.INTEGER).addScalar(
											"lote", Hibernate.SHORT).addScalar("sublote", Hibernate.SHORT).addScalar("idImovel",
											Hibernate.INTEGER).addScalar("idLeiturista", Hibernate.INTEGER).addScalar(
											"leituraCorretaInferior", Hibernate.INTEGER).addScalar("leituraCorretaSuperior",
											Hibernate.INTEGER).addScalar("dataLeitura", Hibernate.DATE).addScalar("leituraAnormalidade",
											Hibernate.STRING).addScalar("situacaoLeitura", Hibernate.STRING).setInteger("anoMesReferencia",
											anoMesReferencia).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0613] - Registrar Leituras e Anormalidades
	 * Retorna a quantidade de imóveis com faixa falsa
	 * 
	 * @author Rafael Corrêa
	 * @date 18/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarImovelFaixaFalsaCount(Integer anoMesReferencia) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String consulta = "SELECT COUNT(leitFaixaFalsa.mdhi_id) as count " + " FROM leitura_faixa_falsa leitFaixaFalsa "
							+ " INNER JOIN medicao_historico mdhi " + " on mdhi.mdhi_id = leitFaixaFalsa.mdhi_id "
							+ " WHERE mdhi.mdhi_amleitura = :anoMesReferencia ";

			retorno = (Integer) session.createSQLQuery(consulta).addScalar("count", Hibernate.INTEGER).setInteger("anoMesReferencia",
							anoMesReferencia).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * @author Vivianne Sousa
	 * @date 06/06/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer obterMaxAMFaturamentoConsumoHistoricoDoImovel(Integer idImovel) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "max(consumoHistorico.referenciaFaturamento) " + "from "
							+ "gcom.micromedicao.consumo.ConsumoHistorico consumoHistorico "
							+ "left join consumoHistorico.ligacaoTipo ligacaoTipo " + "left join consumoHistorico.imovel imovel "
							+ "where imovel.id = :idImovel " + "and ligacaoTipo.id = :idLigacaoTipo ";

			retorno = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("idLigacaoTipo",
							LigacaoTipo.LIGACAO_AGUA).setMaxResults(1).uniqueResult();

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
	 * @author Vivianne Sousa
	 * @date 08/06/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosHidrometro(Integer idImovel) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select hidrometro.numero, hidrometroMarca.descricaoAbreviada, "
							+ "hidrometroCapacidade.descricaoAbreviada,hidrometroDiametro.descricaoAbreviada, "
							+ "hidrometroLocalInstalacao.descricaoAbreviada, medicaoHistorico.dataLeituraAtualInformada, "
							+ "hidrometro.numeroDigitosLeitura, hidrometroInstalacaoHistorico.dataInstalacao, "
							+ "hidProtecao.id, hidProtecao.descricao, hidTipo.id, hidTipo.descricao "
							+ "from gcom.micromedicao.medicao.MedicaoHistorico medicaoHistorico "
							+ "left join medicaoHistorico.hidrometroInstalacaoHistorico hidrometroInstalacaoHistorico "
							+ "left join medicaoHistorico.ligacaoAgua ligacaoagua "
							+ "left join hidrometroInstalacaoHistorico.hidrometro hidrometro "
							+ "left join hidrometro.hidrometroMarca hidrometroMarca "
							+ "left join hidrometro.hidrometroCapacidade hidrometroCapacidade "
							+ "left join hidrometro.hidrometroDiametro hidrometroDiametro "
							+ "left join hidrometroInstalacaoHistorico.hidrometroLocalInstalacao hidrometroLocalInstalacao "
							+ "left join hidrometroInstalacaoHistorico.hidrometroProtecao hidProtecao "
							+ "left join hidrometro.hidrometroTipo hidTipo "
							+ "where ligacaoagua.id = :idImovel and hidrometroInstalacaoHistorico.dataRetirada is null";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

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
	 * @author Vivianne Sousa
	 * @date 13/06/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterRotaESequencialRotaDoImovel(Integer idImovel) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select rota.codigo, " + "imovel.numeroSequencialRota " + "from Imovel imovel " + "left join imovel.rota rota "
							+ "where imovel.id = :idImovel";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

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
	 * [UC0623] Gerar Resumo de Metas CAERN
	 * 
	 * @author Sávio Luiz
	 * @date 15/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Integer pesquisarConsumoFaturado(Integer idImovel, Integer tipoLigacao, Integer idConsumoTipoMediaImovel,
					Integer idConsumoTipoMediaHidrometro, Integer amArrecadacao) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select cshi.numeroConsumoFaturadoMes " + "from ConsumoHistorico cshi "
							+ "where cshi.imovel.id = :idImovel and cshi.ligacaoTipo.id = :tipoLigacao AND "
							+ " cshi.referenciaFaturamento = :amArrecadacao ";
			if(idConsumoTipoMediaImovel != null && !idConsumoTipoMediaImovel.equals("")){
				if(idConsumoTipoMediaHidrometro != null && !idConsumoTipoMediaHidrometro.equals("")){
					consulta += "and (cshi.consumoTipo.id = " + idConsumoTipoMediaImovel;
					consulta += " or cshi.consumoTipo.id = " + idConsumoTipoMediaHidrometro + ")";
				}else{
					consulta += "and cshi.consumoTipo.id = " + idConsumoTipoMediaImovel;
				}

			}

			retorno = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("tipoLigacao", tipoLigacao)
							.setInteger("amArrecadacao", amArrecadacao).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * selecionar os movimentos roteiros empresas.
	 * [UC0627] Gerar Arquivo Texto para Leituristas
	 * 
	 * @author Pedro Alexandre
	 * @date 03/08/2007
	 * @param idRoteiroEmpresa
	 * @param anoMesFaturamento
	 * @param idFaturamentoGrupo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarMovimentoRoteiroEmpresa(Integer idRoteiroEmpresa, Integer anoMesFaturamento,
					Integer idFaturamentoGrupo) throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select loca.id, " + "mrem.codigoSetorComercial, " + "mrem.numeroQuadra, " + "mrem.numeroLoteImovel, "
							+ "mrem.numeroSubloteImovel, " + "medt.id, " + "imov.id, " + "iper.id, " + "mrem.nomeCliente, "
							+ "mrem.enderecoImovel, " + "himc.id, " + "mrem.numeroHidrometro, " + "hicp.id, " + "hili.id, "
							+ "mrem.dataInstalacaoHidrometro, " + "hipr.id, " + "last.id, " + "lest.id, "
							+ "mrem.descricaoAbreviadaCategoriaImovel, " + "mrem.quantidadeEconomias, " + "mrem.numeroLeituraAnterior, "
							+ "mrem.numeroFaixaLeituraEsperadaInicial, " + "mrem.numeroFaixaLeituraEsperadaFinal "
							+ "from MovimentoRoteiroEmpresa mrem " + "left join mrem.roteiroEmpresa roem "
							+ "left join mrem.localidade loca " + "left join mrem.medicaoTipo medt " + "left join mrem.imovel imov "
							+ "left join imov.imovelPerfil iper " + "left join mrem.hidrometroMarca himc "
							+ "left join mrem.hidrometroCapacidade hicp " + "left join mrem.hidrometroLocalInstalacao hili "
							+ "left join mrem.hidrometroProtecao hipr " + "left join mrem.ligacaoAguaSituacao last "
							+ "left join mrem.ligacaoEsgotoSituacao lest " + "left join mrem.faturamentoGrupo ftgr "
							+ "where roem.id = :idRoteiroEmpresa and " + "mrem.anoMesMovimento = :anoMesFaturamento and "
							+ "ftgr.id = :idFaturamentoGrupo " + "order by mrem.codigoSetorComercial, mrem.numeroQuadra";

			retorno = session.createQuery(consulta).setInteger("idRoteiroEmpresa", idRoteiroEmpresa).setInteger("anoMesFaturamento",
							anoMesFaturamento).setInteger("idFaturamentoGrupo", idFaturamentoGrupo).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0627] Gerar Arquivo Texto para Leituristas
	 * Pesquisa a quantidade de setores comercias por roteiro empresa.
	 * [FS0004] Verificar Quantidade de setores comercias.
	 * 
	 * @author Pedro Alexandre
	 * @date 06/08/2007
	 * @param idRoteiroEmpresa
	 * @param anoMesFaturamento
	 * @param idFaturamentoGrupo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarQuantidadeSetorComercialPorRoteiroEmpresa(Integer idRoteiroEmpresa, Integer anoMesFaturamento,
					Integer idFaturamentoGrupo) throws ErroRepositorioException{

		Collection retorno = null;
		Integer qtdSetoresComercias = 0;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select distinct(mrem.codigoSetorComercial) " + "from MovimentoRoteiroEmpresa mrem "
							+ "left join mrem.roteiroEmpresa roem " + "left join mrem.faturamentoGrupo ftgr "
							+ "where roem.id = :idRoteiroEmpresa and " + "mrem.anoMesMovimento = :anoMesFaturamento and "
							+ "ftgr.id = :idFaturamentoGrupo";

			retorno = session.createQuery(consulta).setInteger("idRoteiroEmpresa", idRoteiroEmpresa).setInteger("anoMesFaturamento",
							anoMesFaturamento).setInteger("idFaturamentoGrupo", idFaturamentoGrupo).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		if(retorno != null && !retorno.isEmpty()){
			qtdSetoresComercias = retorno.size();
		}
		return qtdSetoresComercias;
	}

	/**
	 * [UC00083] Gerar Dados para Leitura
	 * [SB0005] Gerar Relação(ROL) em TXT - CAER
	 * 
	 * @author Sávio Luiz
	 * @date 13/08/2007
	 * @param idRota
	 * @param idLeituraTipo
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImoveisParaLeituraPorColecaoRotaCAER(Collection colecaoRota, Integer idLeituraTipo)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{

			/*
			 * Alterado por Raphael Rossiter em 27/09/2007 OBJ: Obter os dados
			 * necessários para verificar se o imóvel é não medido
			 */

			String consulta = "select imovel.id,"// 0
							+ "localidade.id,"// 1
							+ "setorComercial.codigo,"// 2
							+ "quadra.numeroQuadra,"// 3
							+ "imovel.lote,"// 4
							+ "imovel.subLote,"// 5
							+ "imovelPerfil.id,"// 6
							+ "ligacaoAgua.id,"// 7
							+ "hidInstHistoricoAgua.id,"// 8
							+ "hidInstHistoricoPoco.id,"// 9
							+ "rota.id,"// 10
							+ "rota.indicadorFiscalizarSuprimido,"// 11
							+ "rota.indicadorFiscalizarCortado,"// 12
							+ "rota.indicadorGerarFiscalizacao,"// 13
							+ "rota.indicadorGerarFalsaFaixa,"// 14
							+ "rota.percentualGeracaoFiscalizacao,"// 15
							+ "rota.percentualGeracaoFaixaFalsa,"// 16
							+ "empresa.id,"// 17
							+ "empresa.descricaoAbreviada,"// 18
							+ "empresa.email,"// 19
							+ "faturamentoGrupo.id,"// 20
							+ "faturamentoGrupo.descricao,"// 21
							+ "leituraTipo.id,"// 22
							+ "ligacaoAguaSituacao.id,"// 23
							+ "ligacaoEsgotoSituacao.id, "// 24
							+ "faturamentoSituacaoTipo.indicadorParalisacaoLeitura, "// 25
							+ "medTipoAgua.id, "// 26
							+ "medTipoPoco.id, "// 27
							+ "gerenciaRegional.id, "// 28
							+ "gerenciaRegional.nome, "// 29
							+ "localidade.descricao, "// 30
							+ "ligacaoAguaSituacao.descricao, "// 31
							+ "ligacaoAgua.numeroLacre, "// 32
							+ "hidroAgua.numero, "// 33
							+ "imovel.numeroSequencialRota, "// 34
							+ "rota.codigo, "// 35
							+ "hidroPoco.numero, "// 36

							+ "ligacaoAguaSituacao.descricaoAbreviado,"// 37
							+ "logradouro.id,"// 38
							+ "logradouro.nome,"// 39
							+ "imovel.numeroImovel,"// 40
							+ "imovel.complementoEndereco,"// 41
							+ "hidroAgua.numeroDigitosLeitura,"// 42
							+ "hidroPoco.numeroDigitosLeitura,"// 43
							+ "logTit.descricaoAbreviada, "// 44
							+ "logTip.descricaoAbreviada, "// 45
							+ "ligacaoAguaSituacao.indicadorFaturamentoSituacao, "// 46
							+ "ligacaoEsgotoSituacao.indicadorFaturamentoSituacao "// 47

							+ "from Imovel imovel "
							+ "left join imovel.localidade localidade "
							+ "left join imovel.setorComercial setorComercial "
							+ "left join imovel.quadra quadra "
							+ "left join imovel.imovelPerfil imovelPerfil "
							+ "left join imovel.ligacaoAgua ligacaoAgua "
							+ "left join imovel.hidrometroInstalacaoHistorico hidInstHistoricoPoco "
							+ "left join ligacaoAgua.hidrometroInstalacaoHistorico hidInstHistoricoAgua "
							+ "left join hidInstHistoricoAgua.hidrometro hidroAgua "
							+ "left join hidInstHistoricoAgua.medicaoTipo medTipoAgua "
							+ "left join hidInstHistoricoPoco.medicaoTipo medTipoPoco "
							+ "left join hidInstHistoricoPoco.hidrometro hidroPoco "
							+ "left join imovel.rota rota "
							+ "left join rota.empresa empresa "
							+ "left join rota.faturamentoGrupo faturamentoGrupo "
							+ "left join imovel.ligacaoAguaSituacao ligacaoAguaSituacao "
							+ "left join imovel.ligacaoEsgotoSituacao ligacaoEsgotoSituacao "
							+ "left join imovel.faturamentoSituacaoTipo faturamentoSituacaoTipo "
							+ "left join rota.leituraTipo leituraTipo " + "left join localidade.gerenciaRegional gerenciaRegional "

							+ "left join imovel.logradouroBairro logBairro "
							+ "left join logBairro.logradouro logradouro "
							+ "left join logradouro.logradouroTitulo logTit " + "left join logradouro.logradouroTipo logTip "

							+ "where rota.id in (:idsRotas) and "
							+ "leituraTipo.id = :idLeituraTipo "
							+ "order by rota.id,imovel.numeroSequencialRota ";

			retorno = session.createQuery(consulta).setInteger("idLeituraTipo", idLeituraTipo).setParameterList("idsRotas", colecaoRota)
							.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC00083] Gerar Dados para Leitura
	 * [SB0005] Gerar Relação(ROL) em TXT - CAER
	 * 
	 * @author Sávio Luiz
	 * @date 24/08/2007
	 * @param idRota
	 * @param idLeituraTipo
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosLocalProtecaoTipoLigacaoAgua(Integer idImovel) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select hli.descricao,"
							+ // 0
							" hp.descricao " // 1
							+ "from Imovel imovel " + "left join imovel.ligacaoAgua la "
							+ "left join la.hidrometroInstalacaoHistorico hih " + "left join hih.hidrometroLocalInstalacao hli "
							+ "left join hih.hidrometroProtecao hp " + "left join hih.medicaoTipo mt " + "where imovel.id = :idImovel and "
							+ "mt.id = :idMedicaoTipo";
			retorno = (Object[]) session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setInteger("idMedicaoTipo",
							MedicaoTipo.LIGACAO_AGUA.intValue()).setMaxResults(1).uniqueResult();

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
	 * [UC00083] Gerar Dados para Leitura
	 * [SB0005] Gerar Relação(ROL) em TXT - CAER
	 * 
	 * @author Sávio Luiz
	 * @date 24/08/2007
	 * @param idRota
	 * @param idLeituraTipo
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosLocalProtecaoTipoTipoPoco(Integer idImovel) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select hli.descricao,"
							+ // 0
							" hp.descricao " // 1
							+ "from Imovel imovel " + "left join imovel.hidrometroInstalacaoHistorico hih "
							+ "left join hih.hidrometroLocalInstalacao hli " + "left join hih.hidrometroProtecao hp "
							+ "left join hih.medicaoTipo mt " + "where imovel.id = :idImovel and " + "mt.id = :idMedicaoTipo";
			retorno = (Object[]) session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setInteger("idMedicaoTipo",
							MedicaoTipo.POCO.intValue()).setMaxResults(1).uniqueResult();

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
	 * [UC0712] (old[UC00082]) Registrar Leituras e Anormalidades
	 * 
	 * @author Sávio Luiz
	 * @date 29/08/2007
	 * @author eduardo henrique
	 * @date 11/09/2008
	 *       Alteração na forma de Consulta dos Registros de Movimento a serem processados.(v0.05)
	 * @author eduardo henrique
	 * @date 10/12/2008
	 *       Inclusão do ano/mês referência na consulta dos Movimentos que serão processados.
	 * @param idRota
	 * @param idLeituraTipo
	 * @throws ErroRepositorioException
	 */
	public Collection<MovimentoRoteiroEmpresa> pesquisarColecaoMovimentoRoteiroEmpresaRegistrarLeituraAnormalidade(
					Integer idGrupoFaturamento, Integer anoMesReferencia, Collection<Integer> idRotas) throws ErroRepositorioException{

		Collection<MovimentoRoteiroEmpresa> retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select movimentoRoteiroEmpresa " + "from MovimentoRoteiroEmpresa movimentoRoteiroEmpresa "
							+ "inner join movimentoRoteiroEmpresa.faturamentoGrupo fatGrupo "
							+ "inner join fetch movimentoRoteiroEmpresa.leiturista leiturista "
							+ "inner join fetch leiturista.funcionario func inner join fetch movimentoRoteiroEmpresa.rota rota "
							+ "inner join fetch movimentoRoteiroEmpresa.localidade localidade "
							+ "left join fetch movimentoRoteiroEmpresa.leituraAnormalidade "
							+ "where fatGrupo.id = :idGrupoFaturamento and "
							+ "movimentoRoteiroEmpresa.anoMesMovimento = :anoMesReferencia and "
							+ "movimentoRoteiroEmpresa.indicadorFase = :indicadorFase and " + "rota.id in(:idRotas) ";

			if(idRotas != null && idRotas.size() > 1000){
				Collection colecaoAuxiliar = new ArrayList<Object>();
				retorno = new ArrayList<MovimentoRoteiroEmpresa>();
				for(Object id : idRotas){
					colecaoAuxiliar.add(id);
					if(colecaoAuxiliar.size() == 1000){
						retorno.addAll(session.createQuery(consulta).setInteger("idGrupoFaturamento", idGrupoFaturamento).setShort(
										"indicadorFase", MovimentoRoteiroEmpresa.FASE_LEITURA_RETORNADA).setInteger("anoMesReferencia",
										anoMesReferencia).setParameterList("idRotas", colecaoAuxiliar).list());
						colecaoAuxiliar.clear();
					}
				}
				if(!colecaoAuxiliar.isEmpty()){
					retorno.addAll(session.createQuery(consulta).setInteger("idGrupoFaturamento", idGrupoFaturamento).setShort(
									"indicadorFase", MovimentoRoteiroEmpresa.FASE_LEITURA_RETORNADA).setInteger("anoMesReferencia",
									anoMesReferencia).setParameterList("idRotas", colecaoAuxiliar).list());
				}
			}else{
				retorno = session.createQuery(consulta).setInteger("idGrupoFaturamento", idGrupoFaturamento).setShort("indicadorFase",
								MovimentoRoteiroEmpresa.FASE_LEITURA_RETORNADA).setInteger("anoMesReferencia", anoMesReferencia)
								.setParameterList("idRotas", idRotas).list();
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
	 * pesquisa uma coleção de roteiroempresa(s) de acordo com o código
	 * 
	 * @param idLocalidade
	 *            Descrição do parâmetro
	 * @param codigoSetorComercial
	 *            Descrição do parâmetro
	 * @param idLeiturista
	 *            Descrição do parâmetro
	 * @return Description of the Return Value
	 * @exception ErroRepositorioException
	 *                Description of the Exception
	 */
	public Collection pesquisarRoteiroEmpresa(String idEmpresa, String idLocalidade, String codigoSetorComercial, String idLeiturista,
					String indicadorUso, Integer numeroPagina) throws ErroRepositorioException{

		// Collection<Hidrometro> retorno = new ArrayList();
		Collection roteirosEmpresa = new ArrayList();
		Session session = HibernateUtil.getSession();
		String consulta;

		/*
		 * private String nomeEmpresa; private Integer idRoteiroEmpresa; private
		 * Integer idLocalidade; private Integer codigoSetorComercial; private
		 * Integer idFuncionario; private Integer idCliente; private String
		 * nomeLeiturista;
		 */

		try{

			consulta = "select distinct(re.roem_id) as id, "
							+ // [0] id roteiro empresa
							"re.empr_id as idEmpresa, "
							+ // [1] id da empresa
							"emp.empr_nmempresa as nomeEmpresa, "
							+ // [2] nome da empresa
							"loc.loca_id as idLocalidade, "
							+ // [3] id da localidade
							"loc.loca_nmlocalidade as nomeLocalidade, "
							+ // [4] nome da localidade
							"re.leit_id as idLeiturista, "
							+ // [5] id leiturista
							"cli.clie_nmcliente as nomeCliente, "
							+ // [6] nome do cliente
							"func.func_nmfuncionario as nomeFuncionario, "
							+ // [7] nome do funcionario
							"sc.stcm_cdsetorcomercial as codigoSetor "
							+ "from roteiro_empresa re "
							+ "LEFT OUTER JOIN quadra q  on re.roem_id = q.roem_id "
							+ "LEFT OUTER JOIN setor_comercial sc on q.stcm_id = sc.stcm_id "
							+ "LEFT OUTER JOIN empresa emp  on re.empr_id = emp.empr_id "
							+ "LEFT OUTER JOIN localidade loc  on sc.loca_id = loc.loca_id "
							+ "LEFT OUTER JOIN leiturista lei on re.leit_id = lei.leit_id "
							+ "LEFT OUTER JOIN cliente cli on cli.clie_id = lei.clie_id "
							+ "LEFT OUTER JOIN funcionario func on func.func_id = lei.func_id "
							+ "WHERE (1 = 1) "
							+ ((idLocalidade != null && !idLocalidade.equals("")) ? " and sc.loca_id = " + idLocalidade : "")
							+ ((codigoSetorComercial != null && !codigoSetorComercial.equals("") && !codigoSetorComercial.equals(""
											+ ConstantesSistema.NUMERO_NAO_INFORMADO)) ? " and sc.stcm_id = " + codigoSetorComercial : "")
							+ ((idLeiturista != null && !idLeiturista.equals("") && !idLeiturista.equals(""
											+ ConstantesSistema.NUMERO_NAO_INFORMADO)) ? " and re.leit_id = " + idLeiturista : "")
							+ ((idEmpresa != null && !idEmpresa.equals("") && !idEmpresa
											.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)) ? " and re.empr_id = " + idEmpresa : "");

			roteirosEmpresa = session.createSQLQuery(consulta).addScalar("id", Hibernate.INTEGER).addScalar("idEmpresa", Hibernate.INTEGER)
							.addScalar("nomeEmpresa", Hibernate.STRING).addScalar("idLocalidade", Hibernate.INTEGER).addScalar(
											"nomeLocalidade", Hibernate.STRING).addScalar("idLeiturista", Hibernate.INTEGER).addScalar(
											"nomeCliente", Hibernate.STRING).addScalar("nomeFuncionario", Hibernate.STRING).addScalar(
											"codigoSetor", Hibernate.INTEGER).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return roteirosEmpresa;
	}

	/**
	 * Verifica a quantidade de registros retornados da pesquisa de roteiro
	 * empresa
	 * [UC0370] - Filtrar Roteiro Empresa
	 * 
	 * @author Thiago Tenório
	 * @date 01/11/06
	 * @param idEquipe
	 * @param nome
	 * @param placa
	 * @param cargaTrabalho
	 * @param idUnidade
	 * @param idFuncionario
	 * @param idPerfilServico
	 * @param indicadorUso
	 * @return int
	 */
	public int pesquisarRoteiroEmpresaCount(String idEmpresa, String idLocalidade, String codigoSetorComercial, String idLeiturista,
					String indicadorUso) throws ErroRepositorioException{

		int retorno = 0;

		Session session = HibernateUtil.getSession();

		String consulta = "";

		try{

			consulta = "select COUNT(distinct(re.roem_id)) as count from roteiro_empresa re "
							+ "LEFT OUTER JOIN quadra q  on re.roem_id = q.roem_id "
							+ "LEFT OUTER JOIN setor_comercial sc on q.stcm_id = sc.stcm_id "
							+ "WHERE (1 = 1) "
							+ ((idLocalidade != null && !idLocalidade.equals("")) ? " and sc.loca_id = " + idLocalidade : "")
							+ ((codigoSetorComercial != null && !codigoSetorComercial.equals("") && !codigoSetorComercial.equals(""
											+ ConstantesSistema.NUMERO_NAO_INFORMADO)) ? " and sc.stcm_id = " + codigoSetorComercial : "")
							+ ((idLeiturista != null && !idLeiturista.equals("") && !idLeiturista.equals(""
											+ ConstantesSistema.NUMERO_NAO_INFORMADO)) ? " and re.leit_id = " + idLeiturista : "")
							+ ((idEmpresa != null && !idEmpresa.equals("") && !idEmpresa
											.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)) ? " and re.empr_id = " + idEmpresa : "");

			retorno = (Integer) session.createSQLQuery(consulta).addScalar("count", Hibernate.INTEGER).setMaxResults(1).uniqueResult();

			// retorno =
			// session.createQuery(consulta).setInteger("idLeiturista",
			// idLeiturista).setParameterList("idsRotas", colecaoRota)
			// .list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0631] Processar Requisições do Dispositivo Móvel.
	 * [SB0001] Baixar Arquivo Texto para o Leiturista.
	 * 
	 * @author Thiago Nascimento
	 * @date 14/08/2007
	 * @param imei
	 * @return
	 * @throws ErroRepositorioException
	 */
	public byte[] baixarArquivoTextoParaLeitura(long imei) throws ErroRepositorioException{

		byte[] retorno = null;
		ArquivoTextoRoteiroEmpresa arquivoTextoRoteiro = null;
		Session session = HibernateUtil.getSession();

		String hdl = "FROM ArquivoTextoRoteiroEmpresa a where a.situacaoTransmissaoLeitura.id = 2 and" + " a.numeroImei = " + imei;
		try{
			arquivoTextoRoteiro = (ArquivoTextoRoteiroEmpresa) session.createQuery(hdl).uniqueResult();
		}catch(HibernateException e){
			e.printStackTrace();
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		if(arquivoTextoRoteiro != null){
			retorno = arquivoTextoRoteiro.getArquivoTexto();
			StringBuilder sb = new StringBuilder();
			try{
				sb = (StringBuilder) IoUtil.transformarBytesParaObjeto(retorno);
			}catch(IOException e){
				throw new ErroRepositorioException("Erro em Transformar Array de Byte em Object");
			}catch(ClassNotFoundException e){
				throw new ErroRepositorioException("Erro em Transformar Array de Byte em Object");
			}
			retorno = sb.toString().getBytes();
		}
		return retorno;
	}

	/**
	 * [UC0631] Processar Requisições do Dispositivo Móvel.
	 * Atualizar Situação do Arquivo Texto.
	 * 
	 * @author Thiago Nascimento
	 * @date 14/08/2007
	 * @param imei
	 * @param situacaoAnterior
	 * @param situacaoNova
	 * @throws ErroRepositorioException
	 */
	public void atualizarArquivoTextoEnviado(long imei, int situacaoAnterior, int situacaoNova) throws ErroRepositorioException{

		ArquivoTextoRoteiroEmpresa arquivoTextoRoteiro = null;
		Session session = HibernateUtil.getSession();
		String hql = "FROM ArquivoTextoRoteiroEmpresa a where a.situacaoTransmissaoLeitura.id = " + situacaoAnterior + " and"
						+ " a.numeroImei = " + imei;
		String hql2 = "FROM SituacaoTransmissaoLeitura s where s.id =" + situacaoNova;
		try{
			arquivoTextoRoteiro = (ArquivoTextoRoteiroEmpresa) session.createQuery(hql).uniqueResult();
			arquivoTextoRoteiro.setSituacaoTransmissaoLeitura((SituacaoTransmissaoLeitura) session.createQuery(hql2).uniqueResult());
			arquivoTextoRoteiro.setUltimaAlteracao(new Date());
			session.update(arquivoTextoRoteiro);
			session.flush();
		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * [UC0631] Processar Requisições do Dispositivo Móvel.
	 * [SB0002] Atualizar o movimento roteiro empresa.
	 * 
	 * @author Thiago Nascimento
	 * @date 14/08/2007
	 * @param dados
	 * @throws ErroRepositorioException
	 */
	public void atualiarRoteiro(Collection<DadosMovimentacao> dados) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Iterator<DadosMovimentacao> it = dados.iterator();
		try{
			while(it.hasNext()){
				MovimentoRoteiroEmpresa movimento = null;
				DadosMovimentacao dado = it.next();
				String hql1 = "FROM SistemaParametro";
				SistemaParametro sistemaParametro = (SistemaParametro) session.createQuery(hql1).uniqueResult();
				StringBuffer hql = new StringBuffer(
								"FROM MovimentoRoteiroEmpresa m where m.anoMesMovimento = (select MAX(m2.anoMesMovimento) from MovimentoRoteiroEmpresa m2 where m2.imovel.id =");
				hql.append(dado.getImovel());
				hql.append(")  and m.imovel.id =");
				hql.append(dado.getImovel());
				if(sistemaParametro.getIndicadorRoteiroEmpresa().shortValue() == 1){
					hql.append(" and m.roteiroEmpresa.leiturista.numeroImei= ");
				}else{
					hql.append(" and m.rota.leiturista.numeroImei= ");
				}
				hql.append(dado.getImei());
				movimento = (MovimentoRoteiroEmpresa) session.createQuery(hql.toString()).uniqueResult();
				if(movimento != null){
					/*
					 * if (dado.getLeituraHidrometro().intValue() != -1) {
					 * movimento.setNumeroLeituraHidrometro(dado
					 * .getLeituraHidrometro());
					 * } else {
					 * movimento.setNumeroLeituraHidrometro(null);
					 * }
					 */
					String hql2 = "FROM LeituraAnormalidade l where l.id =" + dado.getAnormalidade();
					movimento.setLeituraAnormalidade((LeituraAnormalidade) session.createQuery(hql2).uniqueResult());
					movimento.setIndicadorConfirmacaoLeitura(new Short((short) dado.getIndicador()));
					movimento.setDataLeitura(dado.getData());

					session.update(movimento);
					session.flush();
				}

			}
		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * @author Vivianne Sousa
	 * @date 06/09/2007
	 * @param idImovel
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer obterConsumoMedioEmConsumoHistorico(Integer idImovel, Integer idLigacaoTipo) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select " + "consumoHistorico.consumoMedio " + "from "
							+ "gcom.micromedicao.consumo.ConsumoHistorico consumoHistorico "
							+ "left join consumoHistorico.ligacaoTipo ligacaoTipo " + "left join consumoHistorico.imovel imovel "
							+ "where imovel.id = :idImovel " + "and ligacaoTipo.id = :idLigacaoTipo "
							+ "and consumoHistorico.referenciaFaturamento = "
							+ "(select max(consHist.referenciaFaturamento) from gcom.micromedicao.consumo.ConsumoHistorico consHist) ";

			retorno = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("idLigacaoTipo", idLigacaoTipo)
							.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

	public void removerMovimentoRoteiroEmpresa(Integer anoMesCorrente, Integer idGrupoFaturamentoRota) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "delete " + "MovimentoRoteiroEmpresa mrem "
							+ "where mrem.faturamentoGrupo.id = :idFaturamentoGrupo and mrem.anoMesMovimento = :anoMes";

			session.createQuery(consulta).setInteger("anoMes", anoMesCorrente).setInteger("idFaturamentoGrupo", idGrupoFaturamentoRota)
							.executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * Pesquisa os roteiros empresas pelo grupo de faturamento
	 * [UC0627] Gerar Arquivo Texto para Leitura
	 * 
	 * @author Pedro Alexandre
	 * @date 13/09/2007
	 * @param idGrupoFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarRoteiroEmpresaPorGrupoFaturamento(Integer idGrupoFaturamento) throws ErroRepositorioException{

		Collection<RoteiroEmpresa> roteirosEmpresa = new ArrayList();
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select roem " + "from RoteiroEmpresa roem " + "inner join fetch roem.empresa empr "
							+ "inner join fetch roem.leiturista leit " + "where roem.id in (select qdra.roteiroEmpresa.id "
							+ "from Quadra qdra " + "inner join qdra.rota rota " + "inner join rota.faturamentoGrupo ftgr "
							+ "where ftgr.id = :idFaturamentoGrupo)"

			;

			roteirosEmpresa = session.createQuery(consulta).setInteger("idFaturamentoGrupo", idGrupoFaturamento).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return roteirosEmpresa;
	}

	/**
	 * [UC0101] - Consistir Leituras e Calcular Consumos.
	 * Seleciona os imóveis pertencentes a rota, a partir das tabelas IMOVEL e ROTA.
	 * 
	 * @author Raphael Rossiter
	 * @date 17/09/2007
	 * @exception ErroRepositorioException
	 */
	public Collection pesquisarImoveisParaFaturamento(Rota rota, int anoMesReferencia) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{

			consulta.append("select im.id,"); // 0
			consulta.append("im.ligacaoAguaSituacao.id, "); // 1
			consulta.append("im.ligacaoEsgotoSituacao.id, "); // 2
			consulta.append("li.id, "); // 3
			consulta.append("hia.id, "); // 4
			consulta.append("hdra.id, "); // 5
			consulta.append("hdra.numeroDigitosLeitura, "); // 6
			consulta.append("fst.id,"); // 7
			consulta.append("im.pocoTipo.id, "); // 8
			consulta.append("hie.id, "); // 9
			consulta.append("hdre.id, "); // 10
			consulta.append("rot.indicadorAjusteConsumo, "); // 11
			consulta.append("li.numeroConsumoMinimoAgua, "); // 12
			consulta.append("im.indicadorImovelCondominio, "); // 13
			consulta.append("fst.indicadorParalisacaoFaturamento, "); // 14
			consulta.append("im.indicadorDebitoConta, "); // 15
			consulta.append("le.id, "); // Ligacao Esgoto //16
			consulta.append("le.consumoMinimo, "); // 17
			consulta.append("hia.dataInstalacao, "); // 18
			consulta.append("ct.id, "); // 19
			consulta.append("le.percentualAguaConsumidaColetada, "); // 20
			consulta.append("im.quantidadeEconomias, "); // 21
			consulta.append("hdre.numeroDigitosLeitura, "); // 22
			consulta.append("hie.dataInstalacao, ");// 23
			consulta.append("fst.indicadorValidoAgua, "); // 24
			consulta.append("fst.indicadorValidoEsgoto, "); // 25
			consulta.append("esferaPoderClieResp.id, "); // 26
			consulta.append("im.ligacaoAguaSituacao.indicadorFaturamentoSituacao, "); // 27
			consulta.append("im.ligacaoEsgotoSituacao.indicadorFaturamentoSituacao, "); // 28
			consulta.append("hia.indicadorInstalcaoSubstituicao, "); // 29
			consulta.append("hie.indicadorInstalcaoSubstituicao, "); // 30
			consulta.append("fst.indicadorFaturamentoParalisacaoEsgoto, "); // 31
			consulta.append("hcpa.id, "); // 32
			consulta.append("hcpa.codigoHidrometroCapacidade, "); // 33
			consulta.append("hcpe.id, "); // 34
			consulta.append("hcpe.codigoHidrometroCapacidade, "); // 35
			consulta.append("li.dataCorte, "); // 36
			consulta.append("im.setorComercial.id, "); // 37
			consulta.append("im.setorComercial.codigo, "); // 38
			consulta.append("loca.id, "); // 39
			consulta.append("im.quadra.id, "); // 40
			consulta.append("im.imovelPerfil.id, "); // 41
			consulta.append("im.imovelContaEnvio.id, "); // 42
			consulta.append("im.faturamentoTipo.id, "); // 43
			consulta.append("im.quadra.numeroQuadra, "); // 44
			consulta.append("im.lote, "); // 45
			consulta.append("im.imovelAguaParaTodos.id, "); // 46
			consulta.append("im.diaVencimento, "); // 47
			consulta.append("im.indicadorEmissaoExtratoFaturamento, "); // 48
			consulta.append("rot.id, "); // 49
			consulta.append("rot.leituraTipo.id, "); // 50
			consulta.append("rot.leiturista.id, "); // 51
			consulta.append("rot.empresa.id, "); // 52
			consulta.append("rot.indicadorFiscalizarCortado, "); // 53
			consulta.append("rot.indicadorFiscalizarSuprimido, "); // 54
			consulta.append("ctt.id, "); // 55
			consulta.append("im.dataValidadeTarifaTemporaria, "); // 56
			consulta.append("hila.id, "); // 57
			consulta.append("hila.descricaoAbreviada, "); // 58
			consulta.append("hile.id, "); // 59
			consulta.append("hile.descricaoAbreviada, "); // 60
			consulta.append("im.ligacaoEsgotoSituacao.indicadorAjusteConsumo, "); // 61
			consulta.append("im.ligacaoAguaSituacao.indicadorAjusteConsumo, "); // 62
			consulta.append("ligacaoEsgotoPerfil.percentualEsgotoConsumidaColetada, "); // 63
			consulta.append("im.subLote, "); // 64
			consulta.append("li.dataSupressao, "); // 65
			consulta.append("li.dataReligacao, "); // 66
			consulta.append("li.dataRestabelecimentoAgua, "); // 67
			consulta.append("hia.numeroLeituraInstalacao, "); // 68
			consulta.append("hia.numeroLeituraCorte, "); // 69
			consulta.append("hia.numeroLeituraSupressao, "); // 70
			consulta.append("hie.numeroLeituraInstalacao, "); // 71
			consulta.append("hie.numeroLeituraCorte, "); // 72
			consulta.append("hie.numeroLeituraSupressao, "); // 73
			consulta.append("rot.codigo, "); // 74
			consulta.append("li.dataLigacao, "); // 75
			consulta.append("loca.descricao, "); // 76
			consulta.append("elo.id, "); // 77
			consulta.append("elo.descricao "); // 78
			consulta.append("from Imovel im ");
			consulta.append("inner join im.rota rot ");
			consulta.append("inner join im.consumoTarifa ct ");
			consulta.append("left join im.consumoTarifaTemporaria ctt ");
			consulta.append("left join im.ligacaoAgua li ");
			consulta.append("left join li.hidrometroInstalacaoHistorico hia ");
			consulta.append("left join hia.hidrometroLocalInstalacao hila ");
			consulta.append("left join hia.hidrometro hdra ");
			consulta.append("left join hdra.hidrometroCapacidade hcpa ");
			consulta.append("left join im.hidrometroInstalacaoHistorico hie ");
			consulta.append("left join hie.hidrometroLocalInstalacao hile ");
			consulta.append("left join hie.hidrometro hdre ");
			consulta.append("left join hdre.hidrometroCapacidade hcpe ");
			consulta.append("left join im.faturamentoSituacaoTipo fst ");
			consulta.append("left join im.ligacaoEsgoto le ");
			consulta.append("left join le.ligacaoEsgotoPerfil ligacaoEsgotoPerfil ");
			consulta.append("left join im.clienteImoveis clieImovResp ");
			consulta.append("with (clieImovResp.clienteRelacaoTipo.id = :clienteResponsavel and clieImovResp.dataFimRelacao is null) ");
			consulta.append("left join clieImovResp.cliente clieResp ");
			consulta.append("left join clieResp.clienteTipo tipoClieResp ");
			consulta.append("left join tipoClieResp.esferaPoder esferaPoderClieResp ");
			consulta.append("inner join im.localidade loca ");
			consulta.append("left join loca.localidade elo ");
			consulta.append("where (im.rota.faturamentoGrupo.anoMesReferencia = :anoMes) ");
			consulta.append(" and rot.id = :rota ");
			consulta.append(" and im.indicadorExclusao = :indicadorExclusao");

			retorno = session.createQuery(consulta.toString()).setInteger("anoMes", anoMesReferencia).setInteger("rota", rota.getId())
							.setInteger("clienteResponsavel", ClienteRelacaoTipo.RESPONSAVEL.intValue()).setInteger("indicadorExclusao",
											ConstantesSistema.NAO.intValue()).list();

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
	 * [FS005] - Verificar existência do arquivo texto roteiro empresa.
	 * Caso já exista um arquivo texto para o mês de referência informado, mesmo
	 * roteiro empresa, mesmo grupo de faturamento e sua situação de leitura
	 * transmissão esteja liberado, exclui o arquivo correspondente e retorna
	 * pra o caso se uso que chamou esta funcionalidade.
	 * [UC0627] Gerar Arquivo Texto para Leiturista
	 * 
	 * @author Pedro Alexandre
	 * @date 17/09/2007
	 * @param anoMesReferencia
	 * @param idRoteiroEmpresa
	 * @param idGrupoFaturamento
	 * @throws ErroRepositorioException
	 */
	public void excluirArquivoTextoParaLeiturista(Integer anoMesReferencia, Integer idRoteiroEmpresa, Integer idGrupoFaturamento)
					throws ErroRepositorioException{

		// Cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// Cria a variável que vai conter o hql
		String consulta;

		try{

			consulta = "delete ArquivoTextoRoteiroEmpresa where " + "anoMesReferencia = :anoMesReferencia "
							+ "and roteiroEmpresa = :idRoteiroEmpresa " + "and faturamentoGrupo = :idFaturamentoGrupo "
							+ "and situacaoTransmissaoLeitura = :situacao";

			// Executa o hql
			session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).setInteger("idRoteiroEmpresa", idRoteiroEmpresa)
							.setInteger("idFaturamentoGrupo", idGrupoFaturamento).setInteger("situacao",
											SituacaoTransmissaoLeitura.LIBERADO).executeUpdate();

			// Erro no hibernate
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}
	}

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public String[] pesquisarQuantidadeRotas(String codigoSetorComercial, String rotaInicial, String rotaFinal, String idLocalidade,
					String idCobrancaAcao) throws ErroRepositorioException{

		String[] retorno = new String[3];
		// Cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();
		// Cria a variável que vai conter o hql
		String consulta;
		try{

			consulta = "select count(rota.id) from Rota rota " + " inner join rota.setorComercial setor "
							+ " inner join setor.localidade localidade" + " where rota.id between :rotaInicial and :rotaFinal "
							+ " and setor.codigo = :setorComercial " + " and localidade.id = :idLocalidade";

			// Executa o hql
			Integer qtdRotas = ((Number) session.createQuery(consulta).setInteger("rotaInicial", new Integer(rotaInicial)).setInteger(
							"rotaFinal", new Integer(rotaFinal)).setInteger("setorComercial", new Integer(codigoSetorComercial))
							.setInteger("idLocalidade", new Integer(idLocalidade)).setMaxResults(1).uniqueResult()).intValue();

			String consultaComCriterio = "select count(rota.id) from RotaAcaoCriterio rotaCriterio " + " inner join rotaCriterio.rota rota"
							+ " inner join rota.setorComercial setor " + " inner join setor.localidade localidade"
							+ " inner join rotaCriterio.cobrancaAcao cobrancaAcao " + " where cobrancaAcao.id = :idCobrancaAcao"
							+ " and rota.id between :rotaInicial and :rotaFinal " + " and setor.codigo = :setorComercial "
							+ " and localidade.id = :idLocalidade";

			Integer qtdComCriterio = ((Number) session.createQuery(consultaComCriterio).setInteger("idLocalidade",
							new Integer(idLocalidade)).setInteger("setorComercial", new Integer(codigoSetorComercial)).setInteger(
							"rotaInicial", new Integer(rotaInicial)).setInteger("rotaFinal", new Integer(rotaFinal)).setInteger(
							"idCobrancaAcao", new Integer(idCobrancaAcao)).setMaxResults(1).uniqueResult()).intValue();

			consulta = "select distinct(rota.id) from Rota rota " + " inner join rota.setorComercial setor "
							+ " inner join setor.localidade localidade" + " where rota.id between :rotaInicial and :rotaFinal "
							+ " and setor.codigo = :setorComercial " + " and localidade.id = :idLocalidade";

			String consultaSemCriterio = "select count(rota.id) from RotaAcaoCriterio rotaCriterio " + " inner join rotaCriterio.rota rota"
							+ " inner join rota.setorComercial setor " + " inner join setor.localidade localidade"
							+ " inner join rotaCriterio.cobrancaAcao cobrancaAcao " + " where" + " cobrancaAcao.id <> :idCobrancaAcao"
							+ " and rota.id in (" + consulta + ") and setor.codigo = :setorComercial "
							+ " and localidade.id = :idLocalidade";

			Integer qtdSemCriterio = ((Number) session.createQuery(consultaSemCriterio).setInteger("idLocalidade",
							new Integer(idLocalidade)).setInteger("setorComercial", new Integer(codigoSetorComercial)).setInteger(
							"rotaInicial", new Integer(rotaInicial)).setInteger("rotaFinal", new Integer(rotaFinal)).setInteger(
							"idCobrancaAcao", new Integer(idCobrancaAcao)).setMaxResults(1).uniqueResult()).intValue();

			retorno[0] = "" + qtdRotas;
			retorno[1] = "" + qtdComCriterio;
			retorno[2] = "" + (qtdRotas - qtdComCriterio);

			// Erro no hibernate
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public String[] pesquisarQuantidadeRotasIntervaloSetor(String codigoSetorComercialInicial, String codigoSetorComercialFinal,
					String idLocalidade, String idCobrancaAcao) throws ErroRepositorioException{

		String[] retorno = new String[3];
		// Cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();
		// Cria a variável que vai conter o hql
		String consulta;
		try{

			consulta = "select count(rota.id) from Rota rota " + " inner join rota.setorComercial setor "
							+ " inner join setor.localidade localidade"
							+ " where setor.codigo between :codigoSetorComercialInicial and :codigoSetorComercialFinal "
							+ " and localidade.id = :idLocalidade";

			// Executa o hql
			Integer qtdRotas = ((Number) session.createQuery(consulta).setInteger("idLocalidade", new Integer(idLocalidade)).setInteger(
							"codigoSetorComercialInicial", new Integer(codigoSetorComercialInicial)).setInteger(
							"codigoSetorComercialFinal", new Integer(codigoSetorComercialFinal)).setMaxResults(1).uniqueResult())
							.intValue();

			String consultaComCriterio = "select count(rota.id) from RotaAcaoCriterio rotaCriterio " + " inner join rotaCriterio.rota rota"
							+ " inner join rota.setorComercial setor " + " inner join setor.localidade localidade"
							+ " inner join rotaCriterio.cobrancaAcao cobrancaAcao " + " where cobrancaAcao.id = :idCobrancaAcao"
							+ " and setor.codigo between :codigoSetorComercialInicial and :codigoSetorComercialFinal "
							+ " and localidade.id = :idLocalidade";

			Integer qtdComCriterio = ((Number) session.createQuery(consultaComCriterio).setInteger("idLocalidade",
							new Integer(idLocalidade)).setInteger("codigoSetorComercialInicial", new Integer(codigoSetorComercialInicial))
							.setInteger("codigoSetorComercialFinal", new Integer(codigoSetorComercialFinal)).setInteger("idCobrancaAcao",
											new Integer(idCobrancaAcao)).setMaxResults(1).uniqueResult()).intValue();

			consulta = "select distinct(rota.id) from Rota rota " + " inner join rota.setorComercial setor "
							+ " inner join setor.localidade localidade"
							+ " where setor.codigo between :codigoSetorComercialInicial and :codigoSetorComercialFinal "
							+ " and localidade.id = :idLocalidade";

			String consultaSemCriterio = "select count(rota.id) from RotaAcaoCriterio rotaCriterio " + " inner join rotaCriterio.rota rota"
							+ " inner join rota.setorComercial setor " + " inner join setor.localidade localidade"
							+ " inner join rotaCriterio.cobrancaAcao cobrancaAcao " + " where" + " cobrancaAcao.id <> :idCobrancaAcao"
							+ " and setor.codigo between :codigoSetorComercialInicial and :codigoSetorComercialFinal "
							+ " and localidade.id = :idLocalidade and" + " rota.id in (" + consulta + ")";

			Integer qtdSemCriterio = ((Number) session.createQuery(consultaSemCriterio).setInteger("idLocalidade",
							new Integer(idLocalidade)).setInteger("codigoSetorComercialInicial", new Integer(codigoSetorComercialInicial))
							.setInteger("codigoSetorComercialFinal", new Integer(codigoSetorComercialFinal)).setInteger("idCobrancaAcao",
											new Integer(idCobrancaAcao)).setMaxResults(1).uniqueResult()).intValue();

			retorno[0] = "" + qtdRotas;
			retorno[1] = "" + qtdComCriterio;
			retorno[2] = "" + (qtdRotas - qtdComCriterio);

			// Erro no hibernate
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public String[] pesquisarQuantidadeRotasIntervaloLocalidade(String idLocalidadeInicial, String idLocalidadeFinal, String idCobrancaAcao)
					throws ErroRepositorioException{

		String[] retorno = new String[3];
		// Cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();
		// Cria a variável que vai conter o hql
		String consulta;
		try{

			consulta = "select count(rota.id) from Rota rota " + " inner join rota.setorComercial setor "
							+ " inner join setor.localidade localidade"
							+ " where localidade.id between :idLocalidadeInicial and :idLocalidadeFinal ";

			// Executa o hql
			Integer qtdRotas = ((Number) session.createQuery(consulta).setInteger("idLocalidadeInicial", new Integer(idLocalidadeInicial))
							.setInteger("idLocalidadeFinal", new Integer(idLocalidadeFinal)).setMaxResults(1).uniqueResult()).intValue();

			String consultaComCriterio = "select count(rota.id) from RotaAcaoCriterio rotaCriterio " + " inner join rotaCriterio.rota rota"
							+ " inner join rota.setorComercial setor " + " inner join setor.localidade localidade"
							+ " inner join rotaCriterio.cobrancaAcao cobrancaAcao " + " where cobrancaAcao.id = :idCobrancaAcao"
							+ " and localidade.id between :idLocalidadeInicial and :idLocalidadeFinal";

			Integer qtdComCriterio = ((Number) session.createQuery(consultaComCriterio).setInteger("idLocalidadeInicial",
							new Integer(idLocalidadeInicial)).setInteger("idLocalidadeFinal", new Integer(idLocalidadeFinal)).setInteger(
							"idCobrancaAcao", new Integer(idCobrancaAcao)).setMaxResults(1).uniqueResult()).intValue();

			consulta = "select distinct(rota.id) from Rota rota " + " inner join rota.setorComercial setor "
							+ " inner join setor.localidade localidade"
							+ " where localidade.id between :idLocalidadeInicial and :idLocalidadeFinal ";

			String consultaSemCriterio = "select count(rota.id) from RotaAcaoCriterio rotaCriterio " + " inner join rotaCriterio.rota rota"
							+ " inner join rota.setorComercial setor " + " inner join setor.localidade localidade"
							+ " inner join rotaCriterio.cobrancaAcao cobrancaAcao " + " where" + " cobrancaAcao.id <> :idCobrancaAcao"
							+ " and localidade.id between :idLocalidadeInicial and :idLocalidadeFinal" + " and rota.id in (" + consulta
							+ ")";

			Integer qtdSemCriterio = ((Number) session.createQuery(consultaSemCriterio).setInteger("idLocalidadeInicial",
							new Integer(idLocalidadeInicial)).setInteger("idLocalidadeFinal", new Integer(idLocalidadeFinal)).setInteger(
							"idCobrancaAcao", new Integer(idCobrancaAcao)).setMaxResults(1).uniqueResult()).intValue();

			retorno[0] = "" + qtdRotas;
			retorno[1] = "" + qtdComCriterio;
			retorno[2] = "" + (qtdRotas - qtdComCriterio);

			// Erro no hibernate
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public String[] pesquisarQuantidadeRotasIntervaloGerencia(String idGerenciaRegional, String idCobrancaAcao)
					throws ErroRepositorioException{

		String[] retorno = new String[3];
		// Cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();
		// Cria a variável que vai conter o hql
		String consulta;
		try{

			consulta = "select count(rota.id) from Rota rota " + " inner join rota.setorComercial setor "
							+ " inner join setor.localidade localidade" + " inner join localidade.unidadeNegocio unidadeNegocio"
							+ " inner join unidadeNegocio.gerenciaRegional gerenciaRegional"
							+ " where gerenciaRegional.id = :idGerenciaRegional ";

			// Executa o hql
			Integer qtdRotas = ((Number) session.createQuery(consulta).setInteger("idGerenciaRegional", new Integer(idGerenciaRegional))
							.setMaxResults(1).uniqueResult()).intValue();

			String consultaComCriterio = "select count(rota.id) from RotaAcaoCriterio rotaCriterio " + " inner join rotaCriterio.rota rota"
							+ " inner join rota.setorComercial setor " + " inner join setor.localidade localidade"
							+ " inner join rotaCriterio.cobrancaAcao cobrancaAcao "
							+ " inner join localidade.unidadeNegocio unidadeNegocio"
							+ " inner join unidadeNegocio.gerenciaRegional gerenciaRegional"
							+ " inner join rotaCriterio.cobrancaAcao cobrancaAcao " + " where cobrancaAcao.id = :idCobrancaAcao"
							+ " and gerenciaRegional.id = :idGerenciaRegional";

			Integer qtdComCriterio = ((Number) session.createQuery(consultaComCriterio).setInteger("idGerenciaRegional",
							new Integer(idGerenciaRegional)).setInteger("idCobrancaAcao", new Integer(idCobrancaAcao)).setMaxResults(1)
							.uniqueResult()).intValue();

			consulta = "select distinct(rota.id) from Rota rota " + " inner join rota.setorComercial setor "
							+ " inner join setor.localidade localidade" + " inner join localidade.unidadeNegocio unidadeNegocio"
							+ " inner join unidadeNegocio.gerenciaRegional gerenciaRegional"
							+ " where gerenciaRegional.id = :idGerenciaRegional ";

			String consultaSemCriterio = "select count(rotaAcao.id) from RotaAcaoCriterio rotaCriterioAcao "
							+ " inner join rotaCriterioAcao.rota rotaAcao" + " inner join rotaAcao.setorComercial setorAcao "
							+ " inner join setorAcao.localidade localidadeAcao"
							+ " inner join localidadeAcao.unidadeNegocio unidadeNegocioAcao"
							+ " inner join rotaCriterioAcao.cobrancaAcao cobrancaAcao "
							+ " inner join unidadeNegocioAcao.gerenciaRegional gerenciaRegionalAcao" + " where"
							+ " cobrancaAcao.id <> :idCobrancaAcao" + " and gerenciaRegionalAcao.id = :idGerenciaRegional"
							+ " and rotaAcao.id in (" + consulta + ")";

			Integer qtdSemCriterio = ((Number) session.createQuery(consultaSemCriterio).setInteger("idGerenciaRegional",
							new Integer(idGerenciaRegional)).setInteger("idCobrancaAcao", new Integer(idCobrancaAcao)).setMaxResults(1)
							.uniqueResult()).intValue();

			retorno[0] = "" + qtdRotas;
			retorno[1] = "" + qtdComCriterio;
			retorno[2] = "" + (qtdRotas - qtdComCriterio);
			// Erro no hibernate
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public String[] pesquisarQuantidadeRotasIntervaloGrupo(String idGrupoCobranca, String idCobrancaAcao) throws ErroRepositorioException{

		String[] retorno = new String[3];
		// Cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();
		// Cria a variável que vai conter o hql
		String consulta;
		try{

			consulta = "select count(rota.id) from Rota rota " + " inner join rota.setorComercial setor "
							+ " inner join rota.cobrancaGrupo cobrancaGrupo" + " where cobrancaGrupo.id = :idGrupoCobranca";

			// Executa o hql
			Integer qtdRotas = ((Number) session.createQuery(consulta).setInteger("idGrupoCobranca", new Integer(idGrupoCobranca))
							.setMaxResults(1).uniqueResult()).intValue();

			String consultaComCriterio = "select count(rota.id) from RotaAcaoCriterio rotaCriterio " + " inner join rotaCriterio.rota rota"
							+ " inner join rota.cobrancaGrupo cobrancaGrupo" + " inner join rotaCriterio.cobrancaAcao cobrancaAcao "
							+ " where cobrancaAcao.id = :idCobrancaAcao" + " and cobrancaGrupo.id = :idGrupoCobranca";

			Integer qtdComCriterio = ((Number) session.createQuery(consultaComCriterio).setInteger("idGrupoCobranca",
							new Integer(idGrupoCobranca)).setInteger("idCobrancaAcao", new Integer(idCobrancaAcao)).setMaxResults(1)
							.uniqueResult()).intValue();

			consulta = "select distinct(rota.id) from Rota rota " + " inner join rota.setorComercial setor "
							+ " inner join rota.cobrancaGrupo cobrancaGrupo" + " where cobrancaGrupo.id = :idGrupoCobranca";

			String consultaSemCriterio = "select count(rota.id) from RotaAcaoCriterio rotaCriterio " + " inner join rotaCriterio.rota rota"
							+ " inner join rota.cobrancaGrupo cobrancaGrupo" + " left join rotaCriterio.cobrancaAcao cobrancaAcao "
							+ " where " + " cobrancaAcao.id <> :idCobrancaAcao" + " and cobrancaGrupo.id = :idGrupoCobranca"
							+ " and rota.id in (" + consulta + ")";

			Integer qtdSemCriterio = ((Number) session.createQuery(consultaSemCriterio).setInteger("idGrupoCobranca",
							new Integer(idGrupoCobranca)).setInteger("idCobrancaAcao", new Integer(idCobrancaAcao)).setMaxResults(1)
							.uniqueResult()).intValue();

			retorno[0] = "" + qtdRotas;
			retorno[1] = "" + qtdComCriterio;
			retorno[2] = "" + (qtdRotas - qtdComCriterio);

			// Erro no hibernate
		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão com o hibernate
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public String[] pesquisarQuantidadeRotasIntervaloUnidadeNegocio(String idUnidadeNegocio, String idCobrancaAcao)
					throws ErroRepositorioException{

		String[] retorno = new String[3];
		// Cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();
		// Cria a variável que vai conter o hql
		String consulta;
		try{

			consulta = "select count(rota.id) from Rota rota " + " inner join rota.setorComercial setor "
							+ " inner join setor.localidade localidade" + " inner join localidade.unidadeNegocio unidadeNegocio"
							+ " where unidadeNegocio.id = :idUnidadeNegocio";

			// Executa o hql
			Integer qtdRotas = ((Number) session.createQuery(consulta).setInteger("idUnidadeNegocio", new Integer(idUnidadeNegocio))
							.setMaxResults(1).uniqueResult()).intValue();

			String consultaComCriterio = "select count(rota.id) from RotaAcaoCriterio rotaCriterio " + " inner join rotaCriterio.rota rota"
							+ " inner join rota.setorComercial setor " + " inner join setor.localidade localidade"
							+ " inner join localidade.unidadeNegocio unidadeNegocio "
							+ " inner join rotaCriterio.cobrancaAcao cobrancaAcao " + " where cobrancaAcao.id = :idCobrancaAcao"
							+ " and unidadeNegocio.id = :idUnidadeNegocio";

			Integer qtdComCriterio = ((Number) session.createQuery(consultaComCriterio).setInteger("idUnidadeNegocio",
							new Integer(idUnidadeNegocio)).setInteger("idCobrancaAcao", new Integer(idCobrancaAcao)).setMaxResults(1)
							.uniqueResult()).intValue();

			consulta = "select distinct(rota.id) from Rota rota " + " inner join rota.setorComercial setor "
							+ " inner join setor.localidade localidade" + " inner join localidade.unidadeNegocio unidadeNegocio"
							+ " where unidadeNegocio.id = :idUnidadeNegocio";

			String consultaSemCriterio = "select count(rota.id) from RotaAcaoCriterio rotaCriterio " + " inner join rotaCriterio.rota rota"
							+ " inner join rota.setorComercial setor " + " inner join setor.localidade localidade"
							+ " inner join localidade.unidadeNegocio unidadeNegocio "
							+ " left join rotaCriterio.cobrancaAcao cobrancaAcao " + " where cobrancaAcao.id <> :idCobrancaAcao"
							+ " and unidadeNegocio.id = :idUnidadeNegocio" + " and rota.id in (" + consulta + ")";

			Integer qtdSemCriterio = ((Number) session.createQuery(consultaSemCriterio).setInteger("idUnidadeNegocio",
							new Integer(idUnidadeNegocio)).setInteger("idCobrancaAcao", new Integer(idCobrancaAcao)).setMaxResults(1)
							.uniqueResult()).intValue();

			retorno[0] = "" + qtdRotas;
			retorno[1] = "" + qtdComCriterio;
			retorno[2] = "" + (qtdRotas - qtdComCriterio);

			// Erro no hibernate
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
	 * Pesquisa as rotas pelo grupo de faturamento
	 * [UC0627] Gerar Arquivo Texto para Leitura
	 * 
	 * @author Pedro Alexandre
	 * @date 15/10/2007
	 * @param idGrupoFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarRotasPorGrupoFaturamento(Integer idGrupoFaturamento) throws ErroRepositorioException{

		Collection<Rota> rotas = new ArrayList();
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select rota " + "from Quadra qdra " + "inner join qdra.rota rota " + "inner join rota.faturamentoGrupo ftgr "
							+ "inner join fetch rota.empresa empr " + "inner join fetch rota.leiturista leit "
							+ "where ftgr.id = :idFaturamentoGrupo"

			;

			rotas = session.createQuery(consulta).setInteger("idFaturamentoGrupo", idGrupoFaturamento).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return rotas;
	}

	/**
	 * [FS005] - Verificar existência do arquivo texto roteiro empresa por rota.
	 * Caso já exista um arquivo texto para o mês de referência informado, mesma
	 * rota, mesmo grupo de faturamento e sua situação de leitura transmissão
	 * esteja liberado, exclui o arquivo correspondente e retorna pra o caso se
	 * uso que chamou esta funcionalidade.
	 * [UC0627] Gerar Arquivo Texto para Leiturista
	 * 
	 * @author Pedro Alexandre
	 * @date 15/10/2007
	 * @param anoMesReferencia
	 * @param idRota
	 * @param idGrupoFaturamento
	 * @throws ErroRepositorioException
	 */
	public void excluirArquivoTextoParaLeituristaPorRota(Integer anoMesReferencia, Integer idRota, Integer idGrupoFaturamento)
					throws ErroRepositorioException{

		// Cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		// Cria a variável que vai conter o hql
		String consulta;

		try{

			consulta = "delete ArquivoTextoRoteiroEmpresa where " + "anoMesReferencia = :anoMesReferencia " + "and rota = :idRota "
							+ "and faturamentoGrupo = :idFaturamentoGrupo " + "and situacaoTransmissaoLeitura = :situacao";

			// Executa o hql
			session.createQuery(consulta).setInteger("anoMesReferencia", anoMesReferencia).setInteger("idRota", idRota).setInteger(
							"idFaturamentoGrupo", idGrupoFaturamento).setInteger("situacao", SituacaoTransmissaoLeitura.LIBERADO)
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
	 * [UC0627] Gerar Arquivo Texto para Leituristas
	 * Pesquisa a quantidade de setores comercias por roteiro empresa.
	 * [FS0004] Verificar Quantidade de setores comercias.
	 * 
	 * @author Pedro Alexandre
	 * @date 15/10/2007
	 * @param idRota
	 * @param anoMesFaturamento
	 * @param idFaturamentoGrupo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarQuantidadeSetorComercialPorRota(Integer idRota, Integer anoMesFaturamento, Integer idFaturamentoGrupo)
					throws ErroRepositorioException{

		Collection retorno = null;
		Integer qtdSetoresComercias = 0;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select distinct(mrem.codigoSetorComercial) " + "from MovimentoRoteiroEmpresa mrem " + "left join mrem.rota rota "
							+ "left join mrem.faturamentoGrupo ftgr " + "where rota.id = :idRota and "
							+ "mrem.anoMesMovimento = :anoMesFaturamento and " + "ftgr.id = :idFaturamentoGrupo";

			retorno = session.createQuery(consulta).setInteger("idRota", idRota).setInteger("anoMesFaturamento", anoMesFaturamento)
							.setInteger("idFaturamentoGrupo", idFaturamentoGrupo).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		if(retorno != null && !retorno.isEmpty()){
			qtdSetoresComercias = retorno.size();
		}
		return qtdSetoresComercias;
	}

	/**
	 * selecionar os movimentos roteiros empresas por rota.
	 * [UC0627] Gerar Arquivo Texto para Leituristas
	 * 
	 * @author Pedro Alexandre
	 * @date 15/10/2007
	 * @param idRota
	 * @param anoMesFaturamento
	 * @param idFaturamentoGrupo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarMovimentoRoteiroEmpresaPorRota(Integer idRota, Integer anoMesFaturamento,
					Integer idFaturamentoGrupo) throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select loca.id, " + "mrem.codigoSetorComercial, " + "mrem.numeroQuadra, " + "mrem.numeroLoteImovel, "
							+ "mrem.numeroSubloteImovel, " + "medt.id, " + "imov.id, " + "iper.id, " + "mrem.nomeCliente, "
							+ "mrem.enderecoImovel, " + "himc.id, " + "mrem.numeroHidrometro, " + "hicp.id, " + "hili.id, "
							+ "mrem.dataInstalacaoHidrometro, " + "hipr.id, " + "last.id, " + "lest.id, "
							+ "mrem.descricaoAbreviadaCategoriaImovel, " + "mrem.quantidadeEconomias, " + "mrem.numeroLeituraAnterior, "
							+ "mrem.numeroFaixaLeituraEsperadaInicial, " + "mrem.numeroFaixaLeituraEsperadaFinal "
							+ "from MovimentoRoteiroEmpresa mrem " + "left join mrem.rota rota " + "left join mrem.localidade loca "
							+ "left join mrem.medicaoTipo medt " + "left join mrem.imovel imov " + "left join imov.imovelPerfil iper "
							+ "left join mrem.hidrometroMarca himc " + "left join mrem.hidrometroCapacidade hicp "
							+ "left join mrem.hidrometroLocalInstalacao hili " + "left join mrem.hidrometroProtecao hipr "
							+ "left join mrem.ligacaoAguaSituacao last " + "left join mrem.ligacaoEsgotoSituacao lest "
							+ "left join mrem.faturamentoGrupo ftgr " + "where rota.id = :idRota and "
							+ "mrem.anoMesMovimento = :anoMesFaturamento and " + "ftgr.id = :idFaturamentoGrupo "
							+ "order by mrem.codigoSetorComercial, mrem.numeroQuadra";

			retorno = session.createQuery(consulta).setInteger("idRota", idRota).setInteger("anoMesFaturamento", anoMesFaturamento)
							.setInteger("idFaturamentoGrupo", idFaturamentoGrupo).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public void desassociarRotasIntervaloRotas(String codigoSetorComercial, String rotaInicial, String rotaFinal, String idLocalidade,
					String idCobrancaAcao, String idCriterioCobranca) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			String pesquisa = "select rotaCriterio.comp_id.rotaId from RotaAcaoCriterio rotaCriterio "
							+ " inner join rotaCriterio.rota rota" + " inner join rota.setorComercial setor "
							+ " inner join setor.localidade localidade" + " inner join rotaCriterio.cobrancaAcao cobrancaAcao "
							+ " where cobrancaAcao.id = :idCobrancaAcao" + " and rota.id between :rotaInicial and :rotaFinal "
							+ " and setor.codigo = :setorComercial " + " and localidade.id = :idLocalidade"
							+ " and rotaCriterio.comp_id.cobrancaAcaoId = :idCriterioCobranca";

			ArrayList colecao = (ArrayList) session.createQuery(pesquisa).setInteger("idLocalidade", new Integer(idLocalidade)).setInteger(
							"setorComercial", new Integer(codigoSetorComercial)).setInteger("rotaInicial", new Integer(rotaInicial))
							.setInteger("rotaFinal", new Integer(rotaFinal)).setInteger("idCobrancaAcao", new Integer(idCobrancaAcao))
							.setInteger("idCriterioCobranca", new Integer(idCriterioCobranca)).list();

			String consultaComCriterio = "delete from RotaAcaoCriterio rotaCriterioAcao " + " where rotaCriterioAcao.comp_id.rotaId in ("
							+ ":colecao" + ") and rotaCriterioAcao.comp_id.cobrancaAcaoId = :idCriterio";

			session.createQuery(consultaComCriterio).setParameterList("colecao", colecao).setInteger("idCriterio",
							new Integer(idCriterioCobranca)).executeUpdate();
		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public void desassociarRotasIntervaloSetor(String codigoSetorComercialInicial, String codigoSetorComercialFinal, String idLocalidade,
					String idCobrancaAcao) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{

			String consulta = "select distinct(rotaCriterio.comp_id.rotaId) from RotaAcaoCriterio rotaCriterio "
							+ " inner join rotaCriterio.rota rota" + " inner join rota.setorComercial setor "
							+ " inner join setor.localidade localidade" + " inner join rotaCriterio.cobrancaAcao cobrancaAcao "
							+ " where cobrancaAcao.id = :idCobrancaAcao"
							+ " and setor.codigo between :codigoSetorComercialInicial and :codigoSetorComercialFinal "
							+ " and localidade.id = :idLocalidade";

			String consultaComCriterio = "delete from RotaAcaoCriterio rotaCriterioAcao "
							+ " where rotaCriterioAcao.comp_id.cobrancaAcaoId = :idCobrancaAcao"
							+ " and rotaCriterioAcao.comp_id.rotaId in (" + consulta + ")";

			session.createQuery(consultaComCriterio).setInteger("idLocalidade", new Integer(idLocalidade)).setInteger(
							"codigoSetorComercialInicial", new Integer(codigoSetorComercialInicial)).setInteger(
							"codigoSetorComercialFinal", new Integer(codigoSetorComercialFinal)).setInteger("idCobrancaAcao",
							new Integer(idCobrancaAcao)).executeUpdate();
		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public void desassociarRotasIntervaloLocalidade(String idLocalidadeInicial, String idLocalidadeFinal, String idCobrancaAcao)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{
			String consulta = "select count(rotaCriterio.comp_id.rotaId) from RotaAcaoCriterio rotaCriterio "
							+ " inner join rotaCriterio.rota rota" + " inner join rota.setorComercial setor "
							+ " inner join setor.localidade localidade" + " inner join rotaCriterio.cobrancaAcao cobrancaAcao "
							+ " where cobrancaAcao.id = :idCobrancaAcao"
							+ " and localidade.id between :idLocalidadeInicial and :idLocalidadeFinal";

			String consultaComCriterio = "delete from RotaAcaoCriterioAcao rotaCriterioAcao "
							+ " where rotaCriterioAcao.comp_id.cobrancaAcaoId = :idCobrancaAcao"
							+ " and rotaCriterioAcao.comp_id.rotaId in (" + consulta + ")";

			session.createQuery(consultaComCriterio).setInteger("idLocalidadeInicial", new Integer(idLocalidadeInicial)).setInteger(
							"idLocalidadeFinal", new Integer(idLocalidadeFinal)).setInteger("idCobrancaAcao", new Integer(idCobrancaAcao))
							.executeUpdate();
		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public void desassociarRotasIntervaloGerencia(String idGerenciaRegional, String idCobrancaAcao) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{

			String consulta = "select distinct(rotaCriterio.comp_id.rotaId) from RotaAcaoCriterio rotaCriterio "
							+ " inner join rotaCriterio.rota rota" + " inner join rota.setorComercial setor "
							+ " inner join setor.localidade localidade" + " inner join localidade.unidadeNegocio unidadeNegocio"
							+ " inner join unidadeNegocio.gerenciaRegional gerenciaRegional"
							+ " inner join rotaCriterio.cobrancaAcao cobrancaAcao " + " where cobrancaAcao.id = :idCobrancaAcao"
							+ " and gerenciaRegional.id = :idGerenciaRegional";

			String consultaComCriterio = "delete from RotaAcaoCriterio rotaCriterioAcao "
							+ " where rotaCriterioAcao.comp_id.cobrancaAcaoId = :idCobrancaAcao"
							+ " and rotaCriterioAcao.comp_id.rotaId in (" + consulta + ")";

			session.createQuery(consultaComCriterio).setInteger("idGerenciaRegional", new Integer(idGerenciaRegional)).setInteger(
							"idCobrancaAcao", new Integer(idCobrancaAcao)).executeUpdate();
		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
	}

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public void desassociarRotasIntervaloGrupo(String idGrupoCobranca, String idCobrancaAcao) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{
			String consulta = "select distinct(rotaCriterio.comp_id.rotaId) from RotaAcaoCriterio rotaCriterio "
							+ " inner join rotaCriterio.rota rota" + " inner join rota.cobrancaGrupo cobrancaGrupo"
							+ " inner join rotaCriterio.cobrancaAcao cobrancaAcao " + " where cobrancaAcao.id = :idCobrancaAcao"
							+ " and cobrancaGrupo.id = :idGrupoCobranca";

			String consultaComCriterio = "delete from RotaAcaoCriterio rotaCriterioAcao "
							+ " where rotaCriterioAcao.comp_id.cobrancaAcaoId = :idCobrancaAcao"
							+ " and rotaCriterioAcao.comp_id.rotaId in (" + consulta + ")";

			session.createQuery(consultaComCriterio).setInteger("idGrupoCobranca", new Integer(idGrupoCobranca)).setInteger(
							"idCobrancaAcao", new Integer(idCobrancaAcao)).executeUpdate();
		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public void desassociarRotasIntervaloUnidadeNegocio(String idUnidadeNegocio, String idCobrancaAcao) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		// Cria a variável que vai conter o hql
		try{

			String consulta = "select distinct(rotaCriterio.comp_id.rotaId) from RotaAcaoCriterio rotaCriterio "
							+ " inner join rotaCriterio.rota rota" + " inner join rota.setorComercial setor "
							+ " inner join setor.localidade localidade" + " inner join localidade.unidadeNegocio unidadeNegocio "
							+ " inner join rotaCriterio.cobrancaAcao cobrancaAcao " + " where cobrancaAcao.id = :idCobrancaAcao"
							+ " and unidadeNegocio.id = :idUnidadeNegocio";

			String consultaComCriterio = "delete from RotaAcaoCriterio rotaCriterioAcao "
							+ " where rotaCriterioAcao.comp_id.cobrancaAcaoId = :idCobrancaAcao"
							+ " and rotaCriterioAcao.comp_id.rotaId in (" + consulta + ")";

			session.createQuery(consultaComCriterio).setInteger("idUnidadeNegocio", new Integer(idUnidadeNegocio)).setInteger(
							"idCobrancaAcao", new Integer(idCobrancaAcao)).executeUpdate();
		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * pesquisa o consumo historico passando o imovel e o anomes referencia e o
	 * consumo anormalidade correspondente ao faturameto antecipado.
	 * [UC0113] Faturar Grupo de Faturamento
	 * 
	 * @author Sávio LuIz
	 * @date 08/11/2007
	 * @param idRota
	 * @param anoMesFaturamento
	 * @param idFaturamentoGrupo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarConsumoHistoricoAntecipado(Integer idImovel, Integer anoMes) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			consulta = "select ch.id " + "from ConsumoHistorico ch " + "inner join ch.imovel im " + "left join ch.consumoAnormalidade ca "
							+ "where im.id = :idImovel and " + "ca.id = :idConsumoAnormalidade and "
							+ "ch.referenciaFaturamento = :anoMesReferencia";

			retorno = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel.intValue()).setInteger(
							"idConsumoAnormalidade", ConsumoAnormalidade.FATURAMENTO_ANTECIPADO).setInteger("anoMesReferencia",
							anoMes.intValue()).setMaxResults(1).uniqueResult();

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
	 * Relatório Analise de Consumo
	 * Flávio Leonardo
	 * 26/12/2007
	 * Yara Souza
	 * Alteração no layout do relatório
	 * 
	 * @param idImovel
	 * @param anomes
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarLeiturasImovel(String idImovel, String anoMes) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "select "
						+ "medicaoHistorico.mdhi_nnleituraatualinformada as leituraAtualInformada,"// 0
						+ "medicaoHistorico.mdhi_nnleituraanteriorfaturame as leituraAnteriorFaturamento,"// 1
						+ "hidrometro.hidr_nnhidrometro as numeroHidrometro,"// 2
						+ "medicaoHistorico.lagu_id as ligacaoAgua,"// 3
						+ "ligacaoAguaSituacao.last_dsligacaoaguasituacao as descLigacaoAguaSituacao,"// 4
						+ "funcionario.func_nmfuncionario as funcionario,"// 5
						+ "leituraAnormalidadeInformada.ltan_dsleituraanormalidade as descLeituraAnormalidadeInf, "// 6
						+ "consumoAnormalidade.csan_dsconsumoanormalidade as descConsumoAnormalidade, "// 7
						+ "ligacaoEsgotoSituacao.lest_dsligacaoesgotosituacao as descLigacaoEsgotoSituacao "// 8
						+ " from medicao_historico medicaoHistorico "
						+ " left join ligacao_agua ligacaoAgua on medicaoHistorico.lagu_id = ligacaoAgua.lagu_id"
						+ " left join imovel imovel on imovel.imov_id = medicaoHistorico.lagu_id "
						+ " left join ligacao_agua_situacao ligacaoAguaSituacao on ligacaoAguaSituacao.last_id =imovel.last_id "
						+ " left outer join leiturista leiturista  on medicaoHistorico.func_id = leiturista.func_id"
						+ " left outer join funcionario funcionario on leiturista.func_id = funcionario.func_id"
						+ " left join hidrometro_instalacao_hist hidrometroInstalacaoHist on medicaoHistorico.hidi_id = hidrometroInstalacaoHist.hidi_id"
						+ " left join hidrometro hidrometro on hidrometro.hidr_id = hidrometroInstalacaoHist.hidr_id"
						+ " left join leitura_anormalidade leituraAnormalidadeInformada on leituraAnormalidadeInformada.ltan_id = medicaoHistorico.ltan_idleituraanormalidadeinfo "
						+ " left join consumo_historico consumoHistorico on consumoHistorico.imov_id=medicaoHistorico.lagu_id and "
						+ " medicaoHistorico.mdhi_amleitura = consumoHistorico.cshi_amfaturamento"
						+ " left join consumo_anormalidade consumoAnormalidade on consumoAnormalidade.csan_id=consumoHistorico.csan_id "
						+ " left join ligacao_esgoto_situacao ligacaoEsgotoSituacao on ligacaoEsgotoSituacao.lest_id = imovel.lest_id "
						+ " where medicaoHistorico.lagu_id = " + idImovel + " and medicaoHistorico.mdhi_amleitura = " + anoMes;

		try{
			retorno = session.createSQLQuery(consulta).addScalar("leituraAtualInformada", Hibernate.INTEGER).addScalar(
							"leituraAnteriorFaturamento", Hibernate.INTEGER).addScalar("numeroHidrometro", Hibernate.STRING).addScalar(
							"ligacaoAgua", Hibernate.INTEGER).addScalar("descLigacaoAguaSituacao", Hibernate.STRING).addScalar(
							"funcionario", Hibernate.STRING).addScalar("descLeituraAnormalidadeInf", Hibernate.STRING).addScalar(
							"descConsumoAnormalidade", Hibernate.STRING).addScalar("descLigacaoEsgotoSituacao", Hibernate.STRING).list();

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
	 * Relatório Manter Hidrometro
	 * Flávio Leonardo
	 * pesquisa o id do imovel do hidrometro instalado
	 * 
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarImovelPeloHidrometro(Integer hidrometroId) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select CASE WHEN (hih.imovel.id = null) THEN " + " hih.ligacaoAgua.id " + " ELSE " + " hih.imovel.id END "
							+ " from HidrometroInstalacaoHistorico hih" + " where hih.hidrometro.id = :id "
							+ " and hih.dataRetirada is null";

			retorno = (Integer) session.createQuery(consulta).setInteger("id", new Integer(hidrometroId)).setMaxResults(1).uniqueResult();

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
	 * 
	 * @author eduardo henrique
	 * @date 18/01/2009
	 *       Método que retorna a quantidade de Referências de Consumos Históricos válidos para
	 *       Cálculo de Média.
	 */
	public Collection<Integer> obterQuantidadeReferenciasConsumoHistoricoImovelValidosMedia(Imovel imovel) throws ErroRepositorioException{

		Collection<Integer> retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "Select distinct consumoHistorico.referenciaFaturamento  " + " From ConsumoHistorico consumoHistorico  "
							+ " Where consumoHistorico.imovel.id = :id "
							+ "    and consumoHistorico.numeroConsumoFaturadoMes > :minConsumoValido "
							+ " Order by consumoHistorico.referenciaFaturamento Desc ";

			retorno = session.createQuery(consulta).setInteger("id", imovel.getId()).setInteger("minConsumoValido", new Integer("0"))
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
	 * [UC0353] Efetuar Ligação de Água.
	 * [SB0001] Gerar Ligação de Água
	 * 
	 * @author eduardo henrique
	 * @date 18/03/2009
	 * @param numeroPontosUtilizacao
	 *            Inteiro que representa os pontos de utilizacao do imovel (normalmente obtido por
	 *            imovel.getNumeroPontosUtilizacao)
	 * @return Integer volume do Consumo Fixo da Faixa baseada nos Pontos de Utilização.
	 * @throws ErroRepositorioException
	 */
	public Integer obterVolumeConsumoFixoFaixaImovel(Integer numeroPontosUtilizacao) throws ErroRepositorioException{

		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = " Select consumoFixoFaixa.volumeConsumoFixoFaixa  " + " From ConsumoFixoFaixa consumoFixoFaixa  "
							+ " Where :numeroPontos Between consumoFixoFaixa.numeroInicialFaixa and consumoFixoFaixa.numeroFinalFaixa  "
							+ "   and  consumoFixoFaixa.consumoFixoUnidade.id = :idUnidadePontoConsumo ";

			retorno = (Integer) session.createQuery(consulta).setInteger("numeroPontos", numeroPontosUtilizacao).setInteger(
							"idUnidadePontoConsumo", ConsumoFixoUnidade.UNIDADE_TIPO_PONTOS_CONSUMO).uniqueResult();

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
	 * @param imovel
	 *            [obrigatorio]
	 * @param anoMesReferencia
	 *            [obrigatorio]
	 * @return
	 * @throws ErroRepositorioException
	 *             NullPointer se Imovel ou AnoMesReferencia nulos
	 */
	public MovimentoRoteiroEmpresa obterMovimentoRoteiroPorImovel(Imovel imovel, Integer anoMesReferencia) throws ErroRepositorioException{

		String consulta;
		Session session = HibernateUtil.getSession();
		MovimentoRoteiroEmpresa movimentoRoteiro = null;

		try{
			consulta = "select movimentoRoteiro FROM MovimentoRoteiroEmpresa movimentoRoteiro "
							+ "WHERE movimentoRoteiro.imovel.id = :idImovel " + "AND movimentoRoteiro.anoMesMovimento = :referencia ";

			movimentoRoteiro = (MovimentoRoteiroEmpresa) session.createQuery(consulta).setInteger("idImovel", imovel.getId()).setInteger(
							"referencia", anoMesReferencia).setMaxResults(1).uniqueResult();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return movimentoRoteiro;

	}

	public Collection pesquisarHidrometroProtecaoPorDescricaoAbreviada(String protecao) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Collection retorno = null;
		try{
			Criteria criteria = session.createCriteria(HidrometroProtecao.class).add(
							Expression.like(FiltroHidrometroProtecao.DESCRICAOABREVIADA, "%" + protecao + "%"));
			retorno = criteria.list();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public Integer obterUltimaLeituraDoImovel(Integer idImovel) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer ultimaLeitura = null;
		try{
			String consulta = "select distinct leituraAtualFaturamento from MedicaoHistorico where ligacaoAgua = :idImovel "
							+ "and anoMesReferencia = (select max(anoMesReferencia) from MedicaoHistorico where ligacaoAgua = :idImovel)";

			Object retorno = session.createQuery(consulta).setInteger("idImovel", idImovel).uniqueResult();

			if(retorno != null){
				ultimaLeitura = (Integer) retorno;
			}
			// ultimaLeitura = (Integer) session.createQuery(consulta).setInteger("idImovel",
			// idImovel).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return ultimaLeitura;
	}

	/**
	 * Relatório Analise de Consumo
	 * 
	 * @autor Yara Souza
	 *        date 28/04/2010
	 */

	public Collection pesquisarConsumoPorQuantidadeMeses(Integer idImovel, Integer anoMesFaturamentoMinimo, Integer anoMesFaturamento)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		Collection retorno = null;

		String sql = "select  distinct "
						+ "medicaoHistorico.mdhi_amleitura as anoMes,"// 1
						+ "medicaoHistorico.mdhi_nnleituraatualfaturamento as numeroLeituraAtualFaturamento,"// 2
						+ "medicaoHistorico.mdhi_nnconsumomedidomes as numeroConsumoMedidoMes,"// 4
						+ "consumoHistorico.cshi_nnconsumomedio as consumoMedioImovel, " // 5
						+ "consumoHistorico.cshi_nnconsumofaturadomes as numeroConsumoFaturadoMes,"// 6
						+ "medicaoHistorico.ltan_idleituraanormalidadefatu as leituraAnormalidadeFatu "// 7
						+ "from medicao_historico medicaoHistorico "
						+ " inner join consumo_historico consumoHistorico on consumoHistorico.imov_id=medicaoHistorico.lagu_id and "
						+ " medicaoHistorico.mdhi_amleitura = consumoHistorico.cshi_amfaturamento" + " where medicaoHistorico.lagu_id = "
						+ idImovel + " and medicaoHistorico.mdhi_amleitura > " + anoMesFaturamentoMinimo
						+ " order by medicaoHistorico.mdhi_amleitura desc";

		try{
			retorno = session.createSQLQuery(sql).addScalar("anoMes", Hibernate.INTEGER).addScalar("numeroLeituraAtualFaturamento",
							Hibernate.INTEGER).addScalar("numeroConsumoMedidoMes", Hibernate.INTEGER).addScalar("consumoMedioImovel",
							Hibernate.INTEGER).addScalar("numeroConsumoFaturadoMes", Hibernate.INTEGER).addScalar(
							"leituraAnormalidadeFatu", Hibernate.INTEGER).setMaxResults(3).list();

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
	 * [UC0XXX] Gerar Relatório Grandes Consumidores
	 * 
	 * @author Anderson Italo
	 * @date 16/02/2011
	 *       Obter média de consumo do hidrômetro por imóvel
	 */
	public Integer pesquisarMediaConsumoHidrometro(Integer matricula) throws ErroRepositorioException{

		Object retornoConsulta = null;
		Integer retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select consumoMedioHidrometro " + " from MedicaoHistorico medHist " + " where medHist.anoMesReferencia = "
							+ " (select max(medHist1.anoMesReferencia) " + " from MedicaoHistorico medHist1 "
							+ " where medHist1.ligacaoAgua.id = :matricula) " + " and medHist.ligacaoAgua.id = :matricula";

			retornoConsulta = session.createQuery(consulta).setInteger("matricula", matricula).setMaxResults(1).uniqueResult();

			if(retornoConsulta != null){
				retorno = (Integer) retornoConsulta;
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * @author isilva
	 * @date 23/05/2011
	 *       Obtem as últimas referências dos Dados de Leitura e Consumo do imóvel
	 * @param idImovel
	 *            Identificador do Imóvel
	 * @param maximoResultado
	 *            Resultado máximo, se informado null ou um número menor ou igual a zero, a consulta
	 *            retornará quantidades de linhas ilimitadas
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<LeituraConsumoHelper> obterDadosLeituraEConsumo(Integer idImovel, Integer maximoResultado)
					throws ErroRepositorioException{

		Collection<LeituraConsumoHelper> retorno = new ArrayList<LeituraConsumoHelper>();

		Session session = HibernateUtil.getSession();

		try{
			StringBuffer hql = new StringBuffer("select distinct ");
			hql.append("new gcom.atendimentopublico.ordemservico.bean.LeituraConsumoHelper( ");
			hql.append("medicao.anoMesReferencia, ");
			hql.append("medicao.leituraAtualFaturamento, ");
			hql.append("consumo.numeroConsumoFaturadoMes, ");
			hql.append("anormalidade.descricaoAbreviada, ");
			hql.append("medicao.leituraAnormalidadeFaturamento.id ");
			hql.append(") ");

			hql.append("from ");
			hql.append("MedicaoHistorico medicao, ConsumoHistorico consumo ");
			hql.append("left join consumo.consumoAnormalidade anormalidade ");

			hql.append("where ");
			hql.append("medicao.ligacaoAgua.id = :idImovel and ");
			hql.append("medicao.anoMesReferencia = consumo.referenciaFaturamento and ");
			hql.append("consumo.imovel.id = medicao.ligacaoAgua.id ");

			hql.append("order by medicao.anoMesReferencia desc");

			if(maximoResultado == null || maximoResultado <= 0){
				retorno = (Collection<LeituraConsumoHelper>) session.createQuery(hql.toString())
								.setInteger("idImovel", idImovel.intValue()).list();
			}else{
				retorno = (Collection<LeituraConsumoHelper>) session.createQuery(hql.toString())
								.setInteger("idImovel", idImovel.intValue()).setMaxResults(maximoResultado).list();
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * @author Anderson Italo
	 * @date 25/05/2011
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosHidrometroPorImovel(Integer idImovel) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select hidrometro.numero, hidrometroMarca.descricaoAbreviada, "
							+ "hidrometroCapacidade.descricaoAbreviada,hidrometroDiametro.descricaoAbreviada, "
							+ "hidrometroLocalInstalacao.descricaoAbreviada, "
							+ "hidrometro.numeroDigitosLeitura, hidrometroInstalacaoHistorico.dataInstalacao, "
							+ "hidProtecao.id, hidProtecao.descricao, hidTipo.id, hidTipo.descricao, "
							+ "hidrometroInstalacaoHistorico.indicadorExistenciaCavalete, hidrometro.anoFabricacao "
							+ "from gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico "
							+ "left join hidrometroInstalacaoHistorico.ligacaoAgua ligacaoagua "
							+ "left join hidrometroInstalacaoHistorico.hidrometro hidrometro "
							+ "left join hidrometro.hidrometroMarca hidrometroMarca "
							+ "left join hidrometro.hidrometroCapacidade hidrometroCapacidade "
							+ "left join hidrometro.hidrometroDiametro hidrometroDiametro "
							+ "left join hidrometroInstalacaoHistorico.hidrometroLocalInstalacao hidrometroLocalInstalacao "
							+ "left join hidrometroInstalacaoHistorico.hidrometroProtecao hidProtecao "
							+ "left join hidrometro.hidrometroTipo hidTipo "
							+ "where ligacaoagua.id = :idImovel and hidrometroInstalacaoHistorico.dataRetirada is null";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

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
	 * Relatório Ordem de Serviço de Substituição de Hidrômetro
	 * Obter dados da leitura e consumo do imóvel
	 * 
	 * @author Anderson Italo
	 * @date 26/05/2011
	 */
	public Object[] pesquisarLeituraConsumoImovel(Integer matricula) throws ErroRepositorioException{

		Object[] retornoConsulta = null;
		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select medHist.leituraAtualInformada, medHist.dataLeituraAtualInformada, leitAnorm.descricao, medHist.consumoMedioHidrometro   "
							+ " from MedicaoHistorico medHist  left join medHist.leituraAnormalidadeInformada leitAnorm "
							+ " where medHist.anoMesReferencia = "
							+ " (select max(medHist1.anoMesReferencia) "
							+ " from MedicaoHistorico medHist1 "
							+ " where medHist1.ligacaoAgua.id = :matricula) "
							+ " and medHist.ligacaoAgua.id = :matricula";

			retornoConsulta = (Object[]) session.createQuery(consulta).setInteger("matricula", matricula).setMaxResults(1).uniqueResult();

			if(retornoConsulta != null){
				retorno = (Object[]) retornoConsulta;
			}

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC3009] - Substituir Leituras Anteriores
	 * 
	 * @author Hebert Falcão
	 * @date 09/06/2011
	 */
	public Collection<Object[]> pesquisaMedicaoHistoricoSubstituirLeitura(Integer idImovel, Integer anoMesInicial, Integer anoMesFinal)
					throws ErroRepositorioException{

		Collection<Object[]> retorno = new ArrayList();

		Session session = HibernateUtil.getSession();

		try{
			StringBuffer sql = new StringBuffer();

			sql.append(" select mdhia.medt_id as medicaoTipoAgua, ");
			sql.append("        mdhip.medt_id as medicaoTipoPoco, ");
			sql.append("        mdhia.mdhi_id as medicaoHistoricoAgua, ");
			sql.append("        mdhip.mdhi_id as medicaoHistoricoPoco, ");
			sql.append("        mdhia.mdhi_amleitura as anoMesLeituraAgua, ");
			sql.append("        mdhip.mdhi_amleitura as anoMesLeituraPoco, ");
			sql.append("        mdhia.mdhi_nnleituraatualfaturamento as leituraAtualFaturamentoAgua, ");
			sql.append("        mdhip.mdhi_nnleituraatualfaturamento as leituraAtualFaturamentoPoco, ");
			sql.append("        ltsta.ltst_dsleiturasituacao as descLeituraSituacaoAgua, ");
			sql.append("        ltstp.ltst_dsleiturasituacao as descLeituraSituacaoPoco, ");
			sql.append("        ltana.ltan_dcabreviadaleituraanormal as descLeituraAnormalidadeAgua, ");
			sql.append("        ltanp.ltan_dcabreviadaleituraanormal as descLeituraAnormalidadePoco, ");
			sql.append("        ftgr.ftgr_amreferencia as anoMesGrupoFaturamento ");
			sql.append(" from imovel imov ");
			sql.append(" left join medicao_historico mdhia on mdhia.lagu_id = imov.imov_id ");
			sql.append("      and (mdhia.mdhi_amleitura between :anoMesInicial and :anoMesFinal and mdhia.medt_id = :medicaoTipoAgua) ");
			sql.append(" left join medicao_historico mdhip on mdhip.imov_id = imov.imov_id ");
			sql.append("      and (mdhip.mdhi_amleitura between :anoMesInicial and :anoMesFinal and mdhip.medt_id = :medicaoTipoPoco) ");
			sql.append(" left join leitura_situacao ltsta on ltsta.ltst_id = mdhia.ltst_idleiturasituacaoatual ");
			sql.append(" left join leitura_situacao ltstp on ltstp.ltst_id = mdhip.ltst_idleiturasituacaoatual ");
			sql.append(" left join leitura_anormalidade ltana on ltana.ltan_id = mdhia.ltan_idleituraanormalidadefatu ");
			sql.append(" left join leitura_anormalidade ltanp on ltanp.ltan_id = mdhip.ltan_idleituraanormalidadefatu ");
			sql.append(" inner join quadra qdra on qdra.qdra_id = imov.qdra_id ");
			sql.append(" inner join rota rota on rota.rota_id = imov.rota_id ");
			sql.append(" inner join faturamento_grupo ftgr on ftgr.ftgr_id = rota.ftgr_id ");
			sql.append(" where imov.imov_id = :idImovel ");
			sql.append("   and (mdhia.medt_id is not null or mdhip.medt_id is not null) ");

			SQLQuery sqlQuery = session.createSQLQuery(sql.toString());

			sqlQuery.addScalar("medicaoTipoAgua", Hibernate.INTEGER);
			sqlQuery.addScalar("medicaoTipoPoco", Hibernate.INTEGER);
			sqlQuery.addScalar("medicaoHistoricoAgua", Hibernate.INTEGER);
			sqlQuery.addScalar("medicaoHistoricoPoco", Hibernate.INTEGER);
			sqlQuery.addScalar("anoMesLeituraAgua", Hibernate.INTEGER);
			sqlQuery.addScalar("anoMesLeituraPoco", Hibernate.INTEGER);
			sqlQuery.addScalar("leituraAtualFaturamentoAgua", Hibernate.INTEGER);
			sqlQuery.addScalar("leituraAtualFaturamentoPoco", Hibernate.INTEGER);
			sqlQuery.addScalar("descLeituraSituacaoAgua", Hibernate.STRING);
			sqlQuery.addScalar("descLeituraSituacaoPoco", Hibernate.STRING);
			sqlQuery.addScalar("descLeituraAnormalidadeAgua", Hibernate.STRING);
			sqlQuery.addScalar("descLeituraAnormalidadePoco", Hibernate.STRING);
			sqlQuery.addScalar("anoMesGrupoFaturamento", Hibernate.INTEGER);

			sqlQuery.setInteger("idImovel", idImovel.intValue());
			sqlQuery.setInteger("anoMesInicial", anoMesInicial);
			sqlQuery.setInteger("anoMesFinal", anoMesFinal);
			sqlQuery.setInteger("medicaoTipoPoco", MedicaoTipo.POCO);
			sqlQuery.setInteger("medicaoTipoAgua", MedicaoTipo.LIGACAO_AGUA);

			retorno = (Collection<Object[]>) sqlQuery.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * @author Isaac Silva
	 * @date 13/06/2011
	 * @param idImovel
	 * @param anoMesGrupoFaturamento
	 * @param consumoMedioHidrometroAgua
	 * @param consumoMedioHidrometroEsgoto
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarMedicaoHistoricoAguaOuEsgotoPorImovelEReferencia(Integer idImovel, Integer anoMesGrupoFaturamento,
					int consumoMedioHidrometroAgua, Integer ligacaoTipo) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			StringBuffer consulta = new StringBuffer("select ");
			consulta.append("mh ");
			consulta.append("from ");
			consulta.append("MedicaoHistorico mh ");
			consulta.append("where ");
			consulta.append("mh.anoMesReferencia = :anoMesGrupoFaturamento ");

			if(ligacaoTipo == null){
				if(consumoMedioHidrometroAgua != 0){
					consulta.append(" and mh.ligacaoAgua = :idImovel ");
				}else{
					consulta.append(" and mh.imovel = :idImovel ");
				}
			}else{
				if(ligacaoTipo.intValue() == LigacaoTipo.LIGACAO_AGUA.intValue()){
					consulta.append(" and mh.ligacaoAgua = :idImovel ");
				}else if(ligacaoTipo.intValue() == LigacaoTipo.LIGACAO_ESGOTO.intValue()){
					consulta.append(" and mh.imovel = :idImovel ");
				}
			}

			retorno = session.createQuery(consulta.toString()).setInteger("idImovel", idImovel).setInteger("anoMesGrupoFaturamento",
							anoMesGrupoFaturamento).list();

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
	 * Pesquisa os Historicos de Consumo dos Imoveis pelas Referência de Faturamento
	 * 
	 * @author Isaac Silva
	 * @date 13/06/2011
	 * @param idImovel
	 * @param anoMesGrupoFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarConsumoHistoricoPorReferenciaFaturamentoImovel(Integer idImovel, int anoMesGrupoFaturamento)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			StringBuffer consulta = new StringBuffer("select ");
			consulta.append("ch ");
			consulta.append("from ");
			consulta.append("ConsumoHistorico ch ");
			consulta.append("where ");
			consulta.append("ch.imovel = :idImovel ");
			consulta.append("and ch.referenciaFaturamento = :anoMesGrupoFaturamento");

			retorno = session.createQuery(consulta.toString()).setInteger("idImovel", idImovel).setInteger("anoMesGrupoFaturamento",
							anoMesGrupoFaturamento).list();

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
	 * Consultar última medição histórico do imóvel
	 * 
	 * @author Hebert Falcão
	 * @date 22/06/2011
	 */
	public MedicaoHistorico consultarUltimaMedicaoHistoricoDoImovel(Integer idImovel, Integer idHidrometro) throws ErroRepositorioException{

		MedicaoHistorico retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			StringBuffer consulta = new StringBuffer();
			consulta.append("select mdhi ");
			consulta.append("from MedicaoHistorico mdhi ");
			consulta.append("inner join mdhi.hidrometroInstalacaoHistorico hidi ");
			consulta.append("where mdhi.ligacaoAgua = :idImovel ");
			consulta.append("  and mdhi.anoMesReferencia = (select max(mdhiAux.anoMesReferencia) ");
			consulta.append("                               from MedicaoHistorico mdhiAux ");
			consulta.append("                               inner join mdhiAux.hidrometroInstalacaoHistorico hidiAux ");
			consulta.append("                               where mdhiAux.ligacaoAgua = :idImovel ");
			consulta.append("                                 and hidiAux.hidrometro = :idHidrometro) ");
			consulta.append("  and hidi.hidrometro = :idHidrometro ");

			retorno = (MedicaoHistorico) session.createQuery(consulta.toString()).setInteger("idImovel", idImovel).setInteger(
							"idHidrometro", idHidrometro).uniqueResult();
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
	 * @author Isaac Silva
	 * @date 29/06/2011
	 * @param idFaturamentoAtividade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<FaturamentoAtividadeCriterio> pesquisarFaturamentoAtividadeCriterioPorIdFaturamentoAtividade(
					Integer idFaturamentoAtividade) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");
		Query query = null;

		try{
			consulta.append("select ");
			consulta.append("fac ");
			consulta.append("from ");
			consulta.append("FaturamentoAtividadeCriterio fac ");
			consulta.append("where ");
			consulta.append("fac.faturamentoAtividade.id = :idFaturamentoAtividade ");

			query = session.createQuery(consulta.toString()).setInteger("idFaturamentoAtividade", idFaturamentoAtividade);
			retorno = (Collection<FaturamentoAtividadeCriterio>) query.list();

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
	 * [UC89962] Gerar Dados para leitura (Comprovantes)
	 * 
	 * @author Marcelo Revoredo
	 * @date 07/07/2011
	 * @param Integer
	 *            idImovel
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarUltimoAnoMesConsumoFaturado(Integer idImovel) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select cshi.referenciaFaturamento " + "from ConsumoHistorico cshi " + "where cshi.imovel.id = :idImovel "
							+ "order by cshi.referenciaFaturamento desc ";

			retorno = (Integer) session.createQuery(consulta).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0082] Registrar Leitura Anormalidade
	 * 
	 * @author Péricles Tavares
	 * @date 02/08/2011
	 * @param idFaturamentoGrupo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer quantidadeAnormalidadesGrupoFaturamento(Integer idFaturamentoGrupo) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("select count(mre) from MovimentoRoteiroEmpresa mre ");
			consulta.append("where mre.leituraAnormalidade is not null and mre.leituraAnormalidade.id <> 0 and ");
			consulta.append("mre.faturamentoGrupo.id = :idFaturamentoGrupo and ");
			consulta.append("mre.anoMesMovimento = mre.faturamentoGrupo.anoMesReferencia");

			retorno = ((Number) session.createQuery(consulta.toString()).setInteger("idFaturamentoGrupo", idFaturamentoGrupo)
							.setMaxResults(1).uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0082] Registrar Leitura Anormalidade
	 * 
	 * @author Péricles Tavares
	 * @date 02/08/2011
	 * @param idFaturamentoGrupo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer quantidadeLeiturasGrupoFaturamento(Integer idFaturamentoGrupo) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("select count(mre) from MovimentoRoteiroEmpresa mre where ");
			consulta.append(movimentoRoteiroEmpresaFaturamentoGrupo);

			retorno = ((Number) session.createQuery(consulta.toString()).setInteger("idFaturamentoGrupo", idFaturamentoGrupo)
							.setMaxResults(1).uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0082] Registrar Leitura Anormalidade
	 * 
	 * @author Péricles Tavares
	 * @date 02/08/2011
	 * @param idFaturamentoGrupo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> recuperarAnormalidadesGrupoFaturamento(Integer idFaturamentoGrupo) throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("select mre.empresa.descricao as nomeEmpresa, mre.leiturista.id as agenteComercial, ");
			consulta.append(" mre.rota.id as rota, mre.leituraAnormalidade.descricao as leituraAnormalidade, count(mre) as total ");
			consulta.append("from MovimentoRoteiroEmpresa mre where mre.leituraAnormalidade is not null and ");
			consulta.append(movimentoRoteiroEmpresaFaturamentoGrupo);
			consulta.append("group by mre.empresa.descricao, mre.leiturista.id, mre.rota.id, ");
			consulta.append("mre.leituraAnormalidade.descricao, mre.empresa.id ");
			consulta.append("order by mre.empresa.id, mre.leiturista.id, mre.rota.id");

			retorno = (Collection<Object[]>) session.createQuery(consulta.toString()).setInteger("idFaturamentoGrupo", idFaturamentoGrupo)
							.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0082] Registrar Leitura Anormalidade
	 * 
	 * @author Péricles Tavares
	 * @date 02/08/2011
	 * @param idFaturamentoGrupo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> quantidadeAnormalidadesGrupoFaturamentoPorSetorComercial(Integer idFaturamentoGrupo)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("select count(mre), mre.localidade.id, mre.codigoSetorComercial,");
			consulta.append(" (select count(mrei.id) from MovimentoRoteiroEmpresa mrei where ");
			consulta
							.append("mrei.faturamentoGrupo.id = :idFaturamentoGrupo and mrei.anoMesMovimento = mrei.faturamentoGrupo.anoMesReferencia) ");
			consulta.append("from MovimentoRoteiroEmpresa mre ");
			consulta.append("where mre.leituraAnormalidade is not null and mre.leituraAnormalidade.id <> 0 and ");
			consulta.append(movimentoRoteiroEmpresaFaturamentoGrupo);
			consulta.append("group by mre.localidade.id, mre.codigoSetorComercial ");
			consulta.append("order by mre.localidade.id, mre.codigoSetorComercial");

			retorno = (Collection<Object[]>) session.createQuery(consulta.toString()).setInteger("idFaturamentoGrupo", idFaturamentoGrupo)
							.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0082] Registrar Leitura Anormalidade
	 * 
	 * @author Péricles Tavares
	 * @date 02/08/2011
	 * @param idFaturamentoGrupo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> recuperarAnormalidadesGrupoFaturamentoSetorComercial(Integer idFaturamentoGrupo, Integer idLocalidade,
					Integer idSetorComercial) throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append("select mre.empresa.descricao as nomeEmpresa, mre.leiturista.id as agenteComercial, ");
			consulta.append(" mre.rota.id as rota, mre.leituraAnormalidade.descricao as leituraAnormalidade, count(mre) as total ");
			consulta.append("from MovimentoRoteiroEmpresa mre where mre.leituraAnormalidade is not null and ");
			consulta.append("mre.localidade.id  = :idLocalidade and mre.codigoSetorComercial = :idSetorComercial and ");
			consulta.append(movimentoRoteiroEmpresaFaturamentoGrupo);
			consulta.append("group by mre.empresa.descricao, mre.leiturista.id, mre.rota.id, ");
			consulta.append("mre.leituraAnormalidade.descricao, mre.empresa.id ");
			consulta.append("order by mre.empresa.id, mre.leiturista.id, mre.rota.id");

			retorno = (Collection<Object[]>) session.createQuery(consulta.toString()).setInteger("idFaturamentoGrupo", idFaturamentoGrupo)
							.setInteger("idLocalidade", idLocalidade).setInteger("idSetorComercial", idSetorComercial).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0082] Registrar Leitura Anormalidade
	 * 
	 * @author Péricles Tavares
	 * @date 02/08/2011
	 * @param idFaturamentoGrupo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> quantidadeAnormalidadesGrupoFaturamentoPorRota(Integer idFaturamentoGrupo) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("select count(mre), mre.rota.id,");
			consulta.append(" (select count(mrei) from MovimentoRoteiroEmpresa mrei where ");
			consulta
							.append("mrei.faturamentoGrupo.id = :idFaturamentoGrupo and mrei.anoMesMovimento = mrei.faturamentoGrupo.anoMesReferencia) ");
			consulta.append("from MovimentoRoteiroEmpresa mre ");
			consulta.append("where mre.leituraAnormalidade is not null and  mre.leituraAnormalidade.id <> 0 and ");
			consulta.append(movimentoRoteiroEmpresaFaturamentoGrupo);
			consulta.append("group by mre.rota.id ");
			consulta.append("order by mre.rota.id");

			retorno = (Collection<Object[]>) session.createQuery(consulta.toString()).setInteger("idFaturamentoGrupo", idFaturamentoGrupo)
							.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0082] Registrar Leitura Anormalidade
	 * 
	 * @author Péricles Tavares
	 * @date 02/08/2011
	 * @param idFaturamentoGrupo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> recuperarAnormalidadesGrupoFaturamentoRota(Integer idFaturamentoGrupo, Integer idRota)
					throws ErroRepositorioException{

		Collection<Object[]> retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("select mre.empresa.descricao as nomeEmpresa, mre.leiturista.id as agenteComercial, ");
			consulta.append(" mre.rota.id as rota, mre.leituraAnormalidade.descricao as leituraAnormalidade, count(mre) as total ");
			consulta.append("from MovimentoRoteiroEmpresa mre where mre.leituraAnormalidade is not null and ");
			consulta.append("mre.rota.id  = :idRota and ");
			consulta.append(movimentoRoteiroEmpresaFaturamentoGrupo);
			consulta.append("group by mre.empresa.descricao, mre.leiturista.id, mre.rota.id, ");
			consulta.append("mre.leituraAnormalidade.descricao, mre.empresa.id ");
			consulta.append("order by mre.empresa.id, mre.leiturista.id, mre.rota.id");

			retorno = (Collection<Object[]>) session.createQuery(consulta.toString()).setInteger("idFaturamentoGrupo", idFaturamentoGrupo)
							.setInteger("idRota", idRota).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * @author
	 * @date 29/06/2011
	 * @param idFaturamentoAtividade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<FaturamentoAtividadeCriterio> pesquisarFaturamentoAtividadeCriterioPorLeituraTipo(Integer idFaturamentoAtividade,
					Collection collLeituraTipo) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");
		Query query = null;

		try{
			consulta.append("select ");
			consulta.append("fac ");
			consulta.append("from ");
			consulta.append("FaturamentoAtividadeCriterio fac ");
			consulta.append("where ");
			consulta.append("fac.faturamentoAtividade.id = :idFaturamentoAtividade ");
			consulta.append("and fac.leituraTipo.id in (:collLeituraTipo) ");
			consulta.append("order by fac.id ");

			query = session.createQuery(consulta.toString()).setInteger("idFaturamentoAtividade", idFaturamentoAtividade).setParameterList(
							"collLeituraTipo", collLeituraTipo);
			retorno = (Collection<FaturamentoAtividadeCriterio>) query.list();

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
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * Consulta Usada para retornar uma coleção de MovimentoRoteiroEmpresa de acordo com os
	 * parâmetros.
	 * 
	 * @author Ailton Sousa
	 * @date 18/08/2011
	 * @param idsRota
	 * @param anoMesFaturamento
	 * @param idFaturamentoGrupo
	 * @param idEmpresa
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarMovimentoRoteiroEmpresaPorColecaoRotas(String idsRota, Integer anoMesFaturamento,
					Integer idFaturamentoGrupo, Integer idEmpresa) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");
		Query query = null;

		try{
			consulta.append("select mre ");
			consulta.append("from MovimentoRoteiroEmpresa mre ");
			consulta.append("inner join fetch mre.rota rot ");
			consulta.append("inner join fetch mre.localidade ");
			consulta.append("inner join fetch mre.localidade.localidade ");
			consulta.append("inner join fetch mre.imovel ");
			consulta.append("inner join fetch mre.ligacaoAguaSituacao ");
			consulta.append("inner join fetch mre.ligacaoEsgotoSituacao ");
			consulta.append("inner join fetch mre.consumoTarifa ");
			consulta.append("inner join fetch mre.faturamentoGrupo ");
			consulta.append("where ");
			consulta.append(" rot.id in (" + idsRota + ") ");
			consulta.append(" and rot.leituraTipo.id = :idLeituraTipo ");
			consulta.append(" and mre.indicadorFase = 4 ");
			consulta.append(" and mre.empresa = :idEmpresa ");
			consulta.append(" and mre.anoMesMovimento = :anoMesFaturamento ");
			consulta.append(" and mre.faturamentoGrupo.id = :idFaturamentoGrupo ");
			consulta.append("order by rot.id ");

			query = session.createQuery(consulta.toString()).setInteger("idLeituraTipo", LeituraTipo.LEITURA_E_ENTRADA_SIMULTANEA)
							.setInteger("idEmpresa", idEmpresa).setInteger("anoMesFaturamento", anoMesFaturamento).setInteger(
											"idFaturamentoGrupo", idFaturamentoGrupo);
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
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0001] – Gerar Arquivo Texto para Faturamento Imediato.
	 * 
	 * @author Ailton Sousa
	 * @date 18/08/2011
	 * @param idLeituraAnormalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public String pesquisarDescricaoLeituraAnormalidade(Integer idLeituraAnormalidade) throws ErroRepositorioException{

		String retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");
		Query query = null;

		try{
			consulta.append("select ");
			consulta.append("la.descricao ");
			consulta.append("from ");
			consulta.append("LeituraAnormalidade la ");
			consulta.append("where ");
			consulta.append("la.id = :idLeituraAnormalidade ");

			query = session.createQuery(consulta.toString()).setInteger("idLeituraAnormalidade", idLeituraAnormalidade);
			retorno = (String) query.setMaxResults(1).uniqueResult();

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
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0001] – Gerar Arquivo Texto para Faturamento Imediato.
	 * 
	 * @author Ailton Sousa
	 * @date 19/08/2011
	 */
	public List pesquisarTodasAnormalidadesLeitura() throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String consulta = "select la.id, la.descricao from LeituraAnormalidade la where la.indicadorUso = 1 order by la.id";

			retorno = session.createQuery(consulta).list();

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
	 * Mï¿½todo que retona uma lista de objeto xxxxx que representa o agrupamento
	 * id do imovel id gerencia regional id licalidade id do setor comercial
	 * codigo do setor comercial id rota id guradra numero da quadra id perfil
	 * do imovel id situacao da ligacao de agua id situacao da ligacao de esgoto
	 * principal categoria do imovel esfera de poder do cliente responsavel
	 * indicador de existencia de hidrometro
	 * 
	 * @author Thiago Toscano, Bruno Barrros
	 * @date 19/04/2006, 16/04/2007
	 * @author Ailton Sousa
	 * @date 10/08/2011 Mudou o parâmetro recebido, antes era o Id do Setor Comercial
	 * @param idImovel
	 *            id do Imovel a ser pesquisado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List getImoveisResumoLigacaoEconomias(int idImovel, Integer idRota) throws ErroRepositorioException{

		List retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{
			consulta.append("select ");
			consulta.append("   imovel.id,");
			consulta.append("   imovel.localidade.gerenciaRegional.id,");
			consulta.append("   imovel.localidade.unidadeNegocio.id,");
			consulta.append("   imovel.localidade.id,");
			consulta.append("   imovel.localidade.localidade.id,");
			consulta.append("   imovel.setorComercial.id,");
			consulta.append("   imovel.rota.id,");
			consulta.append("   imovel.quadra.id,");
			consulta.append("   imovel.setorComercial.codigo,");
			consulta.append("   imovel.quadra.numeroQuadra,");
			consulta.append("   imovel.imovelPerfil.id,");
			consulta.append("   imovel.ligacaoAguaSituacao.id,");
			consulta.append("   imovel.ligacaoEsgotoSituacao.id,");

			// Consultando Perfil da ligacao de agua
			consulta.append("case when (");
			consulta.append("     ligacaoAgua.ligacaoAguaPerfil.id is null ) then");
			consulta.append("     0");
			consulta.append("   else");
			consulta.append("     ligacaoAgua.ligacaoAguaPerfil.id");
			consulta.append("   end,");

			// Consultando Perfil da ligacao de Esgoto
			consulta.append("   case when (");
			consulta.append("     ligacaoEsgoto.ligacaoEsgotoPerfil.id is null ) then");
			consulta.append("     0");
			consulta.append("   else");
			consulta.append("     ligacaoEsgoto.ligacaoEsgotoPerfil.id");
			consulta.append("   end,");

			// Consultando Indicador de Hidrômetro Instalado
			consulta.append("   case when (");
			consulta
							.append("       ( imovel.ligacaoAguaSituacao.indicadorFaturamentoSituacao = :indicadorFaturamentoSituacaoLigacaoAgua and");
			consulta.append("         ligacaoAgua.hidrometroInstalacaoHistorico.id is not null ) or");
			consulta
							.append("      ( imovel.ligacaoEsgotoSituacao.indicadorFaturamentoSituacao = :indicadorFaturamentoSituacaoLigacaoEsgoto and");
			consulta.append("         imovel.hidrometroInstalacaoHistorico.id is not null ) ) then");
			consulta.append("     1");
			consulta.append("   else");
			consulta.append("     2");
			consulta.append("   end,");

			// Consultando Indicador de Hidrômetro Instalado no Poço
			consulta.append("   case when (");
			consulta.append("     imovel.hidrometroInstalacaoHistorico.id is not null ) then");
			consulta.append("     1");
			consulta.append("   else");
			consulta.append("     2");
			consulta.append("   end,");

			// Consultando Indicador de volume minimo de agua fixado
			consulta.append("   case when ( ligacaoAgua.numeroConsumoMinimoAgua > 0 ) then");
			consulta.append("     1");
			consulta.append("   else");
			consulta.append("     2");
			consulta.append("   end,");

			// Consultando Indicador de volume minimo de esgoto fixado
			consulta.append("   case when ( ligacaoEsgoto.consumoMinimo > 0 ) then");
			consulta.append("     1");
			consulta.append("   else");
			consulta.append("     2");
			consulta.append("   end,");

			// Consultando Indicador de Poço
			consulta.append("   case when ( imovel.pocoTipo.id is not null ) then");
			consulta.append("     1");
			consulta.append("   else");
			consulta.append("     2");
			consulta.append("   end,");
			// Quantidade Ligacao
			consulta.append("   1 as qtdligacao,");
			// Quantidade Economias
			consulta.append("   imovel.quantidadeEconomias, ");
			// Consumo Tarifa
			consulta.append("    imovel.consumoTarifa.id ");
			//
			consulta.append(" from gcom.cadastro.imovel.Imovel imovel");
			// Joins
			consulta.append(" left join imovel.ligacaoAgua ligacaoAgua ");
			consulta.append(" left join imovel.ligacaoEsgoto ligacaoEsgoto ");
			consulta.append(" inner join imovel.localidade ");
			consulta.append(" inner join imovel.localidade.gerenciaRegional ");
			consulta.append(" inner join imovel.localidade.unidadeNegocio ");
			consulta.append(" inner join imovel.localidade.localidade ");
			consulta.append(" inner join imovel.setorComercial ");
			consulta.append(" inner join imovel.quadra ");
			consulta.append(" inner join imovel.rota rota ");
			consulta.append(" inner join imovel.imovelPerfil ");
			consulta.append(" inner join imovel.ligacaoAguaSituacao ");
			consulta.append(" inner join imovel.ligacaoEsgotoSituacao ");
			consulta.append(" left join ligacaoAgua.ligacaoAguaPerfil ");
			consulta.append(" left join ligacaoEsgoto.ligacaoEsgotoPerfil ");

			if(Util.isVazioOuBranco(idRota)){
				consulta.append(" where imovel.id = :idImovel ");
			}else{
				consulta.append(" where rota.id = :idRota ");
			}
			consulta.append(" and imovel.indicadorExclusao = :indicadorExclusao ");
			consulta.append(" and imovel.indicadorImovelCondominio = :indicadorImovelCondominio");

			if(Util.isVazioOuBranco(idRota)){
				retorno = session.createQuery(consulta.toString()).setShort("indicadorFaturamentoSituacaoLigacaoAgua",
								LigacaoAguaSituacao.FATURAMENTO_ATIVO).setInteger("indicadorFaturamentoSituacaoLigacaoEsgoto",
								LigacaoEsgotoSituacao.FATURAMENTO_ATIVO).setInteger("idImovel", idImovel).setShort("indicadorExclusao",
								ConstantesSistema.NAO).setShort("indicadorImovelCondominio", ConstantesSistema.NAO).list();
			}else{
				retorno = session.createQuery(consulta.toString()).setShort("indicadorFaturamentoSituacaoLigacaoAgua",
								LigacaoAguaSituacao.FATURAMENTO_ATIVO).setInteger("indicadorFaturamentoSituacaoLigacaoEsgoto",
								LigacaoEsgotoSituacao.FATURAMENTO_ATIVO).setInteger("idRota", idRota).setShort("indicadorExclusao",
								ConstantesSistema.NAO).setShort("indicadorImovelCondominio", ConstantesSistema.NAO).list();
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
	 * - Consulta se já existe registro correspondente na base.
	 * 
	 * @author Ailton Sousa
	 * @date 11/08/2011
	 */
	public Integer pesquisarExistenciaResumoLigacaoEconomia(ResumoLigacaoEconomiaHelper helper, Integer anoMesReferencia)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer idResumoLigacaoEconomia = null;

		try{
			StringBuilder hql = new StringBuilder();
			hql.append("select rle.id ");
			hql.append("from ResumoLigacoesEconomia rle ");
			hql.append("where rle.anoMesReferencia = :anoMesReferencia ");
			hql.append("  and rle.indicadorHidrometro = :indicadorHidrometro ");
			hql.append("  and rle.indicadorVolumeFixadoAgua = :indicadorVolumeFixadoAgua ");
			hql.append("  and rle.indicadorVolumeFixadoEsgoto = :indicadorVolumeFixadoEsgoto ");
			hql.append("  and rle.indicadorHidrometroPoco = :indicadorHidrometroPoco ");
			hql.append("  and rle.indicadorPoco = :indicadorPoco ");
			hql.append("  and rle.quadra.numeroQuadra = :numeroQuadra ");
			hql.append("  and rle.setorComercial.codigo = :cdSetorComercial ");
			hql.append("  and rle.gerenciaRegional.id = :idGerenciaRegional ");
			hql.append("  and rle.localidade.id = :idLocalidade ");
			hql.append("  and rle.setorComercial.id = :idSetorComercial ");
			hql.append("  and rle.rota.id = :idRota ");
			hql.append("  and rle.quadra.id = :idQuadra ");
			hql.append("  and rle.imovelPerfil.id = :idImovelPerfil ");
			hql.append("  and rle.ligacaoAguaSituacao.id = :idLigacaoAguaSituacao ");
			hql.append("  and rle.ligacaoEsgotoSituacao.id = :idLigacaoEsgotoSituacao ");
			hql.append("  and rle.categoria.id = :idCategoria ");
			hql.append("  and rle.unidadeNegocio.id = :idUnidadeNegocio ");
			hql.append("  and rle.eloLocalidade.id = :eloLocalidade ");
			hql.append("  and rle.subcategoria.id = :idSubcategoria ");
			hql.append("  and rle.clienteTipo.id = :idClienteTipo ");
			hql.append("  and rle.ligacaoAguaPerfil.id = :idLigacaoAguaPerfil ");
			hql.append("  and rle.ligacaoEsgotoPerfil.id = :idLigacaoEsgotoPerfil ");
			hql.append("  and rle.consumoTarifa.id = :idConsumoTarifa ");

			if(helper.getIdEsfera() != null){
				hql.append("  and rle.esferaPoder.id = :idEsferaPoder ");
			}

			Query query = session.createQuery(hql.toString());

			query.setInteger("anoMesReferencia", anoMesReferencia).setShort("indicadorHidrometro",
							Util.obterShort(helper.getIdHidrometro().toString())).setShort("indicadorVolumeFixadoAgua",
							Util.obterShort(helper.getIdHidrometro().toString())).setShort("indicadorVolumeFixadoEsgoto",
							Util.obterShort(helper.getIdHidrometro().toString())).setShort("indicadorHidrometroPoco",
							Util.obterShort(helper.getIdHidrometro().toString())).setShort("indicadorPoco",
							Util.obterShort(helper.getIdHidrometro().toString())).setInteger("numeroQuadra", helper.getNumeroQuadra())
							.setInteger("cdSetorComercial", helper.getCodigoSetorComercial()).setInteger("idGerenciaRegional",
											helper.getIdGerenciaRegional()).setInteger("idLocalidade", helper.getIdLocalidade())
							.setInteger("idSetorComercial", helper.getIdSetorComercial()).setInteger("idRota", helper.getIdRota())
							.setInteger("idQuadra", helper.getIdQuadra()).setInteger("idImovelPerfil", helper.getIdPerfilImovel())
							.setInteger("idLigacaoAguaSituacao", helper.getIdSituacaoLigacaoAgua()).setInteger("idLigacaoEsgotoSituacao",
											helper.getIdSituacaoLigacaoEsgoto()).setInteger("idCategoria", helper.getIdCategoria())
							.setInteger("idUnidadeNegocio", helper.getIdUnidadeNegocio()).setInteger("eloLocalidade", helper.getIdElo())
							.setInteger("idSubcategoria", helper.getIdSubCategoria()).setInteger("idClienteTipo",
											helper.getIdTipoClienteResponsavel()).setInteger("idLigacaoAguaPerfil",
											helper.getIdPerfilLigacaoAgua()).setInteger("idLigacaoEsgotoPerfil",
											helper.getIdPerfilLigacaoEsgoto()).setInteger("idConsumoTarifa",
											helper.getIdTipoTarifaConsumo());

			if(helper.getIdEsfera() != null){
				query.setInteger("idEsferaPoder", helper.getIdEsfera());
			}

			idResumoLigacaoEconomia = (Integer) query.uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return idResumoLigacaoEconomia;
	}

	/**
	 * [UC0275] Gerar Resumo das Ligações/Economias
	 * - Consulta a quantidade de ligações que já existem na base.
	 * 
	 * @author Ailton Sousa
	 * @date 12/08/2011
	 */
	public Integer pesquisarQuantidadeLigacoesResumoLigacaoEconomia(Integer idResumoLigacaoEconomia) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer qtdLigacao = null;

		try{
			StringBuilder hql = new StringBuilder();
			hql.append("select rle.quantidadeLigacoes ");
			hql.append(" from ResumoLigacoesEconomia rle ");
			hql.append(" where rle.id = :idResumoLigacaoEconomia ");

			Query query = session.createQuery(hql.toString());

			qtdLigacao = (Integer) query.setInteger("idResumoLigacaoEconomia", idResumoLigacaoEconomia).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return qtdLigacao;
	}

	/**
	 * [UC0275] Gerar Resumo das Ligações/Economias
	 * - Consulta a quantidade de economias que já existem na base.
	 * 
	 * @author Ailton Sousa
	 * @date 12/08/2011
	 */
	public Integer pesquisarQuantidadeEconomiaResumoLigacaoEconomia(Integer idResumoLigacaoEconomia) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer qtdEconomia = null;

		try{
			StringBuilder hql = new StringBuilder();
			hql.append("select rle.quantidadeEconomias ");
			hql.append(" from ResumoLigacoesEconomia rle ");
			hql.append(" where rle.id = :idResumoLigacaoEconomia ");

			Query query = session.createQuery(hql.toString());

			qtdEconomia = (Integer) query.setInteger("idResumoLigacaoEconomia", idResumoLigacaoEconomia).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return qtdEconomia;
	}

	/**
	 * [UC0275] Gerar Resumo das Ligações/Economias
	 * - Consulta a quantidade de ligações novas de água que já existem na base.
	 * 
	 * @author Ailton Sousa
	 * @date 12/08/2011
	 */
	public Integer pesquisarQuantidadeLigacoesNovasAguaResumoLigacaoEconomia(Integer idResumoLigacaoEconomia)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer qtdLigacao = null;

		try{
			StringBuilder hql = new StringBuilder();
			hql.append("select rle.quantidadeLigacoesNovasAgua ");
			hql.append(" from ResumoLigacoesEconomia rle ");
			hql.append(" where rle.id = :idResumoLigacaoEconomia ");

			Query query = session.createQuery(hql.toString());

			qtdLigacao = (Integer) query.setInteger("idResumoLigacaoEconomia", idResumoLigacaoEconomia).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return qtdLigacao;
	}

	/**
	 * [UC0275] Gerar Resumo das Ligações/Economias
	 * - Consulta a quantidade de ligações novas de esgoto que já existem na base.
	 * 
	 * @author Ailton Sousa
	 * @date 12/08/2011
	 */
	public Integer pesquisarQuantidadeLigacoesNovasEsgotoResumoLigacaoEconomia(Integer idResumoLigacaoEconomia)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer qtdLigacao = null;

		try{
			StringBuilder hql = new StringBuilder();
			hql.append("select rle.quantidadeLigacoesNovasEsgoto ");
			hql.append(" from ResumoLigacoesEconomia rle ");
			hql.append(" where rle.id = :idResumoLigacaoEconomia ");

			Query query = session.createQuery(hql.toString());

			qtdLigacao = (Integer) query.setInteger("idResumoLigacaoEconomia", idResumoLigacaoEconomia).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return qtdLigacao;
	}

	/**
	 * [UC0275] Gerar Resumo das Ligações/Economias
	 * - Consulta a entidade Resumo de Ligacoes/Economia
	 * 
	 * @author Ailton Sousa
	 * @date 12/08/2011
	 */
	public ResumoLigacoesEconomia pesquisarResumoLigacaoEconomia(Integer idResumoLigacaoEconomia) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		ResumoLigacoesEconomia retorno = null;

		try{
			StringBuilder hql = new StringBuilder();
			hql.append("select rle ");
			hql.append(" from ResumoLigacoesEconomia rle ");
			hql.append(" where rle.id = :idResumoLigacaoEconomia ");

			Query query = session.createQuery(hql.toString());

			retorno = (ResumoLigacoesEconomia) query.setInteger("idResumoLigacaoEconomia", idResumoLigacaoEconomia).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * Consulta Usada para retornar o maior consumo grande usuario de Localidade.
	 * 
	 * @author Ailton Sousa
	 * @date 20/08/2011
	 * @param idsRota
	 * @param anoMesFaturamento
	 * @param idFaturamentoGrupo
	 * @param idEmpresa
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarMaiorConsumoGrandeUsuario(String idsRota, Integer anoMesFaturamento, Integer idFaturamentoGrupo,
					Integer idEmpresa) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");
		Query query = null;

		try{
			consulta.append("select loc.consumoGrandeUsuario ");
			consulta.append("from MovimentoRoteiroEmpresa mre ");
			consulta.append("inner join mre.localidade loc");
			consulta.append(" where ");
			consulta.append(" mre.rota.id in (" + idsRota + ") ");
			consulta.append(" and mre.rota.leituraTipo.id = :idLeituraTipo ");
			consulta.append(" and mre.indicadorFase = :indicadorFase");
			consulta.append(" and mre.empresa = :idEmpresa ");
			consulta.append(" and mre.anoMesMovimento = :anoMesFaturamento ");
			consulta.append(" and mre.faturamentoGrupo.id = :idFaturamentoGrupo ");
			consulta.append("order by loc.consumoGrandeUsuario desc ");

			query = session.createQuery(consulta.toString()).setInteger("idLeituraTipo", LeituraTipo.LEITURA_E_ENTRADA_SIMULTANEA)
							.setInteger("indicadorFase", MovimentoRoteiroEmpresa.FASE_GERADO).setInteger("idEmpresa", idEmpresa)
							.setInteger("anoMesFaturamento", anoMesFaturamento).setInteger("idFaturamentoGrupo", idFaturamentoGrupo);
			retorno = (Integer) query.setMaxResults(1).uniqueResult();

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
	 * @date 22/08/2011
	 * @param local
	 * @return
	 * @throws ErroRepositorioException
	 */
	public HidrometroLocalInstalacao pesquisarHidrometroLocalInstalacaoPorDescricaoAbreviada(String local) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		HidrometroLocalInstalacao retorno = null;
		try{
			Criteria criteria = session.createCriteria(HidrometroLocalInstalacao.class).add(
							Expression.like(FiltroHidrometroLocalInstalacao.DESCRICAOABREVIADA, "%" + local + "%"));
			retorno = (HidrometroLocalInstalacao) criteria.setMaxResults(1).uniqueResult();
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Consulta que retorna a quantidade de Movimento Roteiro Empresa por FaturamentoGrupo e
	 * AnoMesReferencia.
	 * 
	 * @author Ailton Sousa
	 * @date 29/08/2011
	 * @param idFaturamentoGrupo
	 * @param anoMesReferencia
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer obterQuantidadeMovimentoRoteiroPorGrupoAnoMes(Integer idFaturamentoGrupo, Integer anoMesReferencia)
					throws ErroRepositorioException{

		String consulta;
		Session session = HibernateUtil.getSession();
		Integer retono = null;

		try{
			consulta = "SELECT COUNT(*) " + "FROM MovimentoRoteiroEmpresa movimentoRoteiro "
							+ " WHERE movimentoRoteiro.faturamentoGrupo.id = :idFaturamentoGrupo "
							+ " AND movimentoRoteiro.anoMesMovimento = :referencia ";

			retono = (Integer) session.createQuery(consulta).setInteger("idFaturamentoGrupo", idFaturamentoGrupo).setInteger("referencia",
							anoMesReferencia).uniqueResult();

			// erro no hibernate
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return retono;

	}

	/**
	 * [UC0712] - Atualizar Leituras e Anormalidades
	 * 
	 * @author Péricles Tavares
	 * @date 25/08/2011
	 */

	public Collection pesquisarMaiorMedicaoHistoricoAnterior(Collection colecaoIdsImoveis, Integer idMedicaoTipo)
					throws ErroRepositorioException{

		Collection retorno = new ArrayList<Object[]>();
		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{
			for(Object imovel : colecaoIdsImoveis){
				Integer idImovel = null;
				if(imovel instanceof Imovel){
					idImovel = ((Imovel) imovel).getId();
				}else if(imovel instanceof Integer){
					idImovel = (Integer) imovel;
				}

				consulta = "";
				if(idMedicaoTipo.equals(MedicaoTipo.POCO)){
					consulta = "Select "
									+ " medicaohis0_.mdhi_dtleituraatualfaturamento as dataLeituraAtualFat, "
									+ // 0
									" medicaohis0_.mdhi_nnleituraatualfaturamento as leituraAtualFat, "
									+ // 1
									" medicaohis0_.mdhi_nnleituraatualinformada as leituraAtualInf, "
									+ // 2
									" medicaohis0_.ltst_idleiturasituacaoatual as idLeituraSitAtual, "
									+ // 3
									" hidrometro3_.hidi_id as idHidrometroInstHist, "
									+ // 4
									" hidrometro3_.hidi_dtinstalacaohidrometro as dataInstalacao, "
									+ // 5
									" medicaohis0_.imov_id as idImovel, "
									+ // 6
									" medicaohis0_.mdhi_nnconsumocreditoanterior as consumoCreditoAnterior  " // 7
									+ " from  medicao_historico medicaohis0_ "
									+ " inner join  imovel imovel2_ on medicaohis0_.imov_id=imovel2_.imov_id "
									+ " left outer join  hidrometro_instalacao_hist hidrometro3_  on imovel2_.hidi_id=hidrometro3_.hidi_id "
									+ " where " + "medicaohis0_.medt_id = :idMedicaoTipo and " + " imovel2_.imov_id = (:idImovel) ";

				}
				if(idMedicaoTipo.equals(MedicaoTipo.LIGACAO_AGUA)){
					consulta = " Select "
									+ " medicaohis0_.mdhi_dtleituraatualfaturamento as dataLeituraAtualFat, "
									+ // 0
									" medicaohis0_.mdhi_nnleituraatualfaturamento as leituraAtualFat, "
									+ // 1
									" medicaohis0_.mdhi_nnleituraatualinformada as leituraAtualInf, "
									+ // 2
									" medicaohis0_.ltst_idleiturasituacaoatual as idLeituraSitAtual, "
									+ // 3
									" hidrometro3_.hidi_id as idHidrometroInstHist, "
									+ // 4
									" hidrometro3_.hidi_dtinstalacaohidrometro as dataInstalacao, "
									+ // 5
									" medicaohis0_.lagu_id as idImovel, "
									+ // 6
									" medicaohis0_.mdhi_nnconsumocreditoanterior as consumoCreditoAnterior  " // 7
									+ " from  medicao_historico medicaohis0_ "
									+ " inner join  ligacao_agua ligacaoagu1_   on medicaohis0_.lagu_id=ligacaoagu1_.lagu_id "
									+ " left outer join  hidrometro_instalacao_hist hidrometro3_  on ligacaoagu1_.hidi_id=hidrometro3_.hidi_id "
									+ " where medicaohis0_.medt_id= :idMedicaoTipo and " + " ligacaoagu1_.lagu_id = (:idImovel) ";

				}

				consulta += " order by medicaohis0_.mdhi_amleitura desc";

				retorno.add(session.createSQLQuery(consulta).addScalar("dataLeituraAtualFat", Hibernate.DATE).addScalar("leituraAtualFat",
								Hibernate.INTEGER).addScalar("leituraAtualInf", Hibernate.INTEGER).addScalar("idLeituraSitAtual",
								Hibernate.INTEGER).addScalar("idHidrometroInstHist", Hibernate.INTEGER).addScalar("dataInstalacao",
								Hibernate.DATE).addScalar("idImovel", Hibernate.INTEGER).addScalar("consumoCreditoAnterior",
								Hibernate.INTEGER).setInteger("idImovel", idImovel).setInteger("idMedicaoTipo", idMedicaoTipo)
								.setMaxResults(1).uniqueResult());
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
	 * - Retorna a quantidade de imóveis com hidrômetro na ligação de água no setor comercial
	 * [UC3033] Verificar Anormalidades de Consumo nos Movimentos do Faturamento
	 * 
	 * @author Hugo Lima
	 * @date 12/01/2012
	 * @param idGrupoFaturamento
	 * @param controleAnormalidadeConsumo
	 * @throws ControladorException
	 */
	public Collection<Integer[]> pesquisarImovelLigacaoAguaGrupoFaturamento(Integer idGrupoFaturamento, int controleAnormalidadeConsumo)
					throws ErroRepositorioException{

		Collection retorno = new ArrayList<Integer[]>();
		String consulta = "";
		Session session = HibernateUtil.getSession();

		try{
			switch(controleAnormalidadeConsumo){
				case ConstantesSistema.CD_CONTROLE_ANORMALIDADE_GRUPO_FATURAMENTO:
					consulta = "select count(la.hidi_id) as quantidade, r.ftgr_id as grupo from ligacao_agua la "
									+ " inner join imovel i on la.lagu_id = i.imov_id " + " inner join quadra q on i.qdra_id = q.qdra_id "
									+ " inner join rota r on i.rota_id = r.rota_id " + " and r.lttp_id <> "
									+ LeituraTipo.LEITURA_E_ENTRADA_SIMULTANEA + " where la.hidi_id <> 0 and la.hidi_id is not null "
									+ " and r.ftgr_id = :idGrupoFaturamento" + " group by r.ftgr_id ";

					retorno = (Collection) session.createSQLQuery(consulta).addScalar("quantidade", Hibernate.INTEGER).addScalar("grupo",
									Hibernate.INTEGER).setInteger("idGrupoFaturamento", idGrupoFaturamento).list();

					break;

				case ConstantesSistema.CD_CONTROLE_ANORMALIDADE_SETOR_COMERCIAL:
					consulta = "select count(la.hidi_id) as quantidade, i.loca_id as localidade, sc.stcm_cdsetorcomercial as setor "
									+ " from ligacao_agua la " //
									+ " inner join imovel i on la.lagu_id = i.imov_id "
									+ " inner join setor_comercial sc on i.STCM_ID = sc.STCM_ID "
									+ " inner join quadra q on i.qdra_id = q.qdra_id "
									+ " inner join rota r on i.rota_id = r.rota_id and r.lttp_id <> "
									+ LeituraTipo.LEITURA_E_ENTRADA_SIMULTANEA //
									+ " where la.hidi_id <> 0 and la.hidi_id is not null " //
									+ " and r.ftgr_id = :idGrupoFaturamento " //
									+ " group by i.loca_id, sc.stcm_cdsetorcomercial " //
									+ " order by i.loca_id, sc.stcm_cdsetorcomercial ";

					retorno = (Collection) session.createSQLQuery(consulta).addScalar("quantidade", Hibernate.INTEGER).addScalar(
									"localidade", Hibernate.INTEGER).addScalar("setor", Hibernate.INTEGER).setInteger("idGrupoFaturamento",
									idGrupoFaturamento).list();

					break;

				case ConstantesSistema.CD_CONTROLE_ANORMALIDADE_ROTA:
					consulta = "select count(la.hidi_id) as quantidade, r.rota_id as rota from ligacao_agua la "
									+ " inner join imovel i on la.lagu_id = i.imov_id " + " inner join quadra q on i.qdra_id = q.qdra_id "
									+ " inner join rota r on i.rota_id = r.rota_id " + " and r.lttp_id <> "
									+ LeituraTipo.LEITURA_E_ENTRADA_SIMULTANEA + " where la.hidi_id <> 0 and la.hidi_id is not null "
									+ " and r.ftgr_id = :idGrupoFaturamento " + " group by r.rota_id " + " order by r.rota_id ";

					retorno = (Collection) session.createSQLQuery(consulta).addScalar("quantidade", Hibernate.INTEGER).addScalar("rota",
									Hibernate.INTEGER).setInteger("idGrupoFaturamento", idGrupoFaturamento).list();

					break;

				default:
					break;
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * - Retorna a quantidade de anormalidades de consumo no grupo de faturamento
	 * [UC3033] Verificar Anormalidades de Consumo nos Movimentos do Faturamento
	 * 
	 * @author Hugo Lima
	 * @date 13/01/2012
	 * @param idGrupoFaturamento
	 * @param idAnormalidade
	 * @param controleAnormalidadeConsumo
	 * @param idLocalidade
	 * @param cdSetorComercial
	 * @param idRota
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarAnormalidadesConsumoGrupoFaturamento(Integer idGrupoFaturamento, Integer idAnormalidade,
					int controleAnormalidadeConsumo, Integer idLocalidade, Integer cdSetorComercial, Integer idRota)
					throws ErroRepositorioException{

		Integer retorno = 0;
		String consulta = "";
		Session session = HibernateUtil.getSession();

		try{
			switch(controleAnormalidadeConsumo){
				case ConstantesSistema.CD_CONTROLE_ANORMALIDADE_GRUPO_FATURAMENTO:
					consulta = "select count(ch.cshi_id) as quantidade from consumo_historico ch "
									+ " inner join ligacao_agua la on ch.imov_id = la.lagu_id and la.hidi_id <> 0 and la.hidi_id is not null "
									+ " inner join rota r on r.rota_id = ch.rota_id "
									+ " and r.lttp_id <> "
									+ LeituraTipo.LEITURA_E_ENTRADA_SIMULTANEA
									+ " inner join faturamento_grupo fg on ch.cshi_amfaturamento = fg.ftgr_amreferencia and r.ftgr_id = fg.ftgr_id "
									+ " where ch.lgti_id = " + LigacaoTipo.LIGACAO_AGUA
									+ " and r.ftgr_id = :idGrupoFaturamento and ch.csan_id = :idAnormalidade";

					retorno = (Integer) session.createSQLQuery(consulta).addScalar("quantidade", Hibernate.INTEGER).setInteger(
									"idGrupoFaturamento", idGrupoFaturamento).setInteger("idAnormalidade", idAnormalidade).uniqueResult();

					break;

				case ConstantesSistema.CD_CONTROLE_ANORMALIDADE_SETOR_COMERCIAL:
					consulta = "select count(ch.cshi_id) as quantidade from consumo_historico ch "
									+ " inner join ligacao_agua la on ch.imov_id = la.lagu_id and la.hidi_id <> 0 and la.hidi_id is not null "
									+ " inner join imovel i on ch.imov_id = i.imov_id "
									+ " inner join setor_comercial sc on i.STCM_ID = sc.STCM_ID "
									+ " inner join rota r on r.rota_id = ch.rota_id "
									+ " and r.lttp_id <> "
									+ LeituraTipo.LEITURA_E_ENTRADA_SIMULTANEA
									+ " inner join faturamento_grupo fg on ch.cshi_amfaturamento = fg.ftgr_amreferencia and r.ftgr_id = fg.ftgr_id "
									+ " where ch.lgti_id = " + LigacaoTipo.LIGACAO_AGUA
									+ " and r.ftgr_id = :idGrupoFaturamento and ch.csan_id = :idAnormalidade "
									+ " and i.loca_id = :idLocalidade and sc.stcm_cdsetorcomercial = :cdSetorComercial ";

					retorno = (Integer) session.createSQLQuery(consulta).addScalar("quantidade", Hibernate.INTEGER).setInteger(
									"idGrupoFaturamento", idGrupoFaturamento).setInteger("idAnormalidade", idAnormalidade).setInteger(
									"idLocalidade", idLocalidade).setInteger("cdSetorComercial", cdSetorComercial).uniqueResult();
					break;

				case ConstantesSistema.CD_CONTROLE_ANORMALIDADE_ROTA:
					consulta = "select count(ch.cshi_id) as quantidade from consumo_historico ch "
									+ " inner join ligacao_agua la on ch.imov_id = la.lagu_id and la.hidi_id <> 0 and la.hidi_id is not null "
									+ " inner join rota r on r.rota_id = ch.rota_id "
									+ " and r.lttp_id <> "
									+ LeituraTipo.LEITURA_E_ENTRADA_SIMULTANEA
									+ " inner join faturamento_grupo fg on ch.cshi_amfaturamento = fg.ftgr_amreferencia and r.ftgr_id = fg.ftgr_id "
									+ " where ch.lgti_id = " + LigacaoTipo.LIGACAO_AGUA
									+ " and r.ftgr_id = :idGrupoFaturamento and ch.csan_id = :idAnormalidade "
									+ " and r.rota_id = :idRota ";

					retorno = (Integer) session.createSQLQuery(consulta).addScalar("quantidade", Hibernate.INTEGER).setInteger(
									"idGrupoFaturamento", idGrupoFaturamento).setInteger("idAnormalidade", idAnormalidade).setInteger(
									"idRota", idRota).uniqueResult();
					break;

				default:
					break;
			}
		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0101] - Consistir Leituras e Calcular Consumos
	 * 
	 * @author Anderson Italo
	 * @date 20/03/2012
	 */
	public ConsumoAnormalidade pesquisarConsumoAnormalidade(Integer idAnormalidadeConsumo) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		ConsumoAnormalidade consumoAnormalidade = null;

		try{

			StringBuilder hql = new StringBuilder();
			hql.append(" from ConsumoAnormalidade anormalidade ");
			hql.append(" where anormalidade.id = :idAnormalidadeConsumo ");

			Query query = session.createQuery(hql.toString());

			consumoAnormalidade = (ConsumoAnormalidade) query.setInteger("idAnormalidadeConsumo", idAnormalidadeConsumo).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}
		return consumoAnormalidade;
	}

	/**
	 * [UC0101] - Consistir Leituras e Calcular Consumos
	 * [SB0014] - Verificar Estouro de Consumo
	 * 
	 * @author Anderson Italo
	 * @date 23/03/2012
	 */
	public List<ConsumoAnormalidadeFaixa> pesquisarConsumoAnormalidadeFaixa() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		List<ConsumoAnormalidadeFaixa> retorno = null;

		try{

			StringBuilder hql = new StringBuilder();
			hql.append(" from ConsumoAnormalidadeFaixa anormalidadeFaixa ");
			Query query = session.createQuery(hql.toString());

			retorno = (List<ConsumoAnormalidadeFaixa>) query.list();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * @author Hugo Lima
	 * @date 21/03/2012
	 * @param idImovel
	 * @param idTipoMedicao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosHidrometroPorTipoMedicao(Integer idImovel, Integer idTipoMedicao) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select hidrometro.numero, hidrometroMarca.descricaoAbreviada, "
							+ "hidrometroCapacidade.descricaoAbreviada,hidrometroDiametro.descricao, "
							+ "hidrometroLocalInstalacao.descricaoAbreviada, "
							+ "hidrometro.numeroDigitosLeitura, hidrometroInstalacaoHistorico.dataInstalacao, "
							+ "hidProtecao.id, hidProtecao.descricao, hidTipo.id, hidTipo.descricao, "
							+ "hidrometroInstalacaoHistorico.indicadorExistenciaCavalete, hidrometro.anoFabricacao "
							+ "from gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico ";

			if(idTipoMedicao.equals(MedicaoTipo.LIGACAO_AGUA)){
				consulta = consulta + "inner join hidrometroInstalacaoHistorico.ligacaoAgua ligacaoagua ";
			}else if(idTipoMedicao.equals(MedicaoTipo.POCO)){
				consulta = consulta + "inner join hidrometroInstalacaoHistorico.imovel imovel ";
			}

			consulta = consulta + "inner join hidrometroInstalacaoHistorico.hidrometro hidrometro "
							+ "left join hidrometro.hidrometroMarca hidrometroMarca "
							+ "left join hidrometro.hidrometroCapacidade hidrometroCapacidade "
							+ "left join hidrometro.hidrometroDiametro hidrometroDiametro "
							+ "left join hidrometroInstalacaoHistorico.hidrometroLocalInstalacao hidrometroLocalInstalacao "
							+ "left join hidrometroInstalacaoHistorico.hidrometroProtecao hidProtecao "
							+ "left join hidrometro.hidrometroTipo hidTipo ";

			if(idTipoMedicao.equals(MedicaoTipo.LIGACAO_AGUA)){
				consulta = consulta + "where ligacaoagua.id = :idImovel and hidrometroInstalacaoHistorico.dataRetirada is null";
			}else if(idTipoMedicao.equals(MedicaoTipo.POCO)){
				consulta = consulta + "where imovel.id = :idImovel and hidrometroInstalacaoHistorico.dataRetirada is null";
			}

			retorno = (Object[]) session.createQuery(consulta).setInteger("idImovel", idImovel).setMaxResults(1).uniqueResult();

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
	 * Retorna uma coleção com os dados dos Consumos para apresentação
	 * com a maior referencia para ano/mes de faturamento
	 * [UC0713] Emitir Ordem de Serviço Seletiva
	 * 
	 * @param idImovel
	 * @param tipoLigacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosConsumoMaiorReferenciaFaturamento(Integer idImovel, Integer idTipoLigacao) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = " select consumoTipo.id, " + " consumoTipo.descricao, " + " consumoAnormalidade.id, "
							+ " consumoAnormalidade.descricao, " + " consumoHistorico.consumoRateio, "
							+ " consumoHistorico.numeroConsumoFaturadoMes " + " from ConsumoHistorico consumoHistorico "
							+ " inner join consumoHistorico.imovel imovel " + " inner join consumoHistorico.ligacaoTipo ligacaoTipo "
							+ " inner join consumoHistorico.consumoTipo consumoTipo "
							+ " inner join consumoHistorico.consumoAnormalidade consumoAnormalidade "
							+ " where imovel.id = :idImovel and ligacaoTipo.id = :idTipoLigacao "
							+ " order by  consumoHistorico.referenciaFaturamento desc ";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("idTipoLigacao", idTipoLigacao)
							.setMaxResults(1).uniqueResult();
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
	 * Retorna uma coleção com os dados da medição para apresentação
	 * com a maior referencia para ano/mes de leitura
	 * [UC0713] Emitir Ordem de Serviço Seletiva
	 * 
	 * @param idImovel
	 * @param tipoLigacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterDadosMedicaoMaiorReferenciaLeitura(Integer idImovel, Integer idTipoLigacao) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = " select medicaoHistorico.leituraAnteriorFaturamento, "
							+ " medicaoHistorico.leituraAtualFaturamento, "
							+ " medicaoHistorico.dataLeituraAnteriorFaturamento, "
							+ " medicaoHistorico.dataLeituraAtualFaturamento, "
							+ " leituraSituacaoAtual.id, "
							+ " leituraAnormalidadeFaturamento.id, "
							+ " medicaoHistorico.numeroConsumoMes "
							+ " from MedicaoHistorico medicaoHistorico "
							+ " inner join medicaoHistorico.medicaoTipo medicaoTipo "
							+ " left join medicaoHistorico.leituraSituacaoAtual leituraSituacaoAtual "
							+ " left join medicaoHistorico.leituraAnormalidadeFaturamento leituraAnormalidadeFaturamento "
							+ (idTipoLigacao.equals(LigacaoTipo.LIGACAO_AGUA) ? " left join medicaoHistorico.ligacaoAgua ligacaoAgua where ligacaoAgua.id = :idImovel "
											: "")
							+ (idTipoLigacao.equals(LigacaoTipo.LIGACAO_ESGOTO) ? " left join medicaoHistorico.imovel imovel where imovel.id = :idImovel "
											: "") + " and medicaoTipo.id = :idTipoLigacao "
							+ " order by  medicaoHistorico.anoMesReferencia desc ";

			retorno = (Object[]) session.createQuery(consulta).setInteger("idImovel", idImovel).setInteger("idTipoLigacao", idTipoLigacao)
							.setMaxResults(1).uniqueResult();
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
	 * [UC0103] Efetuar Rateio de Consumo
	 * 
	 * @author Anderson Italo
	 * @date 22/05/2012
	 * @param idImovel
	 * @param idTipoMedicao
	 * @return RateioTipo
	 * @throws ErroRepositorioException
	 */
	public RateioTipo obterTipoRateioImovelPorTipoMedicao(Integer idImovel, Integer idTipoMedicao) throws ErroRepositorioException{

		RateioTipo retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{
			consulta.append("select hidrometroInstalacaoHistorico.rateioTipo ");
			consulta.append("from gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico ");

			if(idTipoMedicao.equals(MedicaoTipo.LIGACAO_AGUA)){

				consulta.append("where hidrometroInstalacaoHistorico.ligacaoAgua.id = :idImovel ");
				consulta.append("and hidrometroInstalacaoHistorico.dataRetirada is null ");
			}else if(idTipoMedicao.equals(MedicaoTipo.POCO)){

				consulta.append("where hidrometroInstalacaoHistorico.imovel.id = :idImovel ");
				consulta.append("and hidrometroInstalacaoHistorico.dataRetirada is null ");
			}

			retorno = (RateioTipo) session.createQuery(consulta.toString()).setInteger("idImovel", idImovel).setMaxResults(1)
							.uniqueResult();

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
	 * [UC0101] - Consistir Leituras e Calcular Consumos
	 * Método que limpa os dados de medição histórico já consistidos para o imóvel e referência de
	 * faturamento informada.
	 * 
	 * @author Anderson Italo
	 * @date 29/05/2012
	 * @throws ErroRepositorioException
	 */
	public void limparDadosFaturamentoConsitidosMedicaoHistorico(Integer idImovel, Integer anoMesReferenciaFaturamento)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{
			String sql = "update gcom.micromedicao.medicao.MedicaoHistorico med set "
							+ "med.leituraAtualFaturamento = med.leituraAtualInformada, med.leituraAnormalidadeFaturamento.id = med.leituraAnormalidadeInformada.id, "
							+ "med.ultimaAlteracao = :data where ( exists ( "
							+ "select imov.id from Imovel imov where imov.id = :idImovel and imov.id = med.imovel.id) "
							+ "or exists (select imov.id from Imovel imov where imov.id = :idImovel and imov.id = med.ligacaoAgua.id)) "
							+ "and med.anoMesReferencia = :anoMesReferencia and (med.imovel.id = :idImovel  or med.ligacaoAgua.id = :idImovel) ";

			session.createQuery(sql).setTimestamp("data", new Date()).setInteger("idImovel", idImovel).setInteger("anoMesReferencia",
							anoMesReferenciaFaturamento).executeUpdate();
		}catch(HibernateException e){

			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}
	}

	public Object[] pesquisarMedicaoHistorico(Integer idImovel, Integer anoMes, Integer idMedicaoTipo) throws ErroRepositorioException{

		Object[] resultadoPesquisa = null;

		Session session = HibernateUtil.getSession();
		String consulta = null;
		try{

			consulta = "";
			if(idMedicaoTipo.equals(MedicaoTipo.POCO)){
				consulta = " select " + " medicaohistorico.mdhi_dtleituraatualfaturamento as dtleituraatualfaturamento, "
								+ " medicaohistorico.mdhi_dtleituraanteriorfaturame as dtleituraanteriorfaturame, "
								+ " medicaohistorico.mdhi_nnleituraatualfaturamento as nnleituraatualfaturamento, "
								+ " medicaohistorico.mdhi_nnconsumomedidomes as nnconsumomedidomes "
								+ " from  medicao_historico medicaohistorico " + " where "
								+ " medicaohistorico.mdhi_amleitura = :anoMesReferencia  and "
								+ " medicaohistorico.medt_id= :idMedicaoTipo and " + " medicaohistorico.imov_id = :idImovel ";

			}
			if(idMedicaoTipo.equals(MedicaoTipo.LIGACAO_AGUA)){
				consulta = " select " + " medicaohistorico.mdhi_dtleituraatualfaturamento as dtleituraatualfaturamento, "
								+ " medicaohistorico.mdhi_dtleituraanteriorfaturame as dtleituraanteriorfaturame, "
								+ " medicaohistorico.mdhi_nnleituraatualfaturamento as nnleituraatualfaturamento, "
								+ " medicaohistorico.mdhi_nnconsumomedidomes as nnconsumomedidomes "
								+ " from  medicao_historico medicaohistorico " + " where "
								+ " medicaohistorico.mdhi_amleitura = :anoMesReferencia  and "
								+ " medicaohistorico.medt_id= :idMedicaoTipo and " + " medicaohistorico.lagu_id = :idImovel ";

			}

			resultadoPesquisa = (Object[]) session.createSQLQuery(consulta).addScalar("dtleituraatualfaturamento", Hibernate.DATE)
							.addScalar("dtleituraanteriorfaturame", Hibernate.DATE).addScalar("nnleituraatualfaturamento",
											Hibernate.INTEGER).addScalar("nnconsumomedidomes", Hibernate.INTEGER).setInteger("idImovel",
											idImovel).setInteger("idMedicaoTipo", idMedicaoTipo).setInteger("anoMesReferencia",
											anoMes.intValue()).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return resultadoPesquisa;
	}

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0001] – Gerar Arquivo Texto para Faturamento Imediato – Modelo 1
	 * 
	 * @author Anderson Italo
	 * @date 05/06/2012
	 * @throws ErroRepositorioException
	 */
	public BigDecimal obterTotalValorRubricaRestante(Integer idMovimentoRoteiroEmpresa) throws ErroRepositorioException{

		BigDecimal retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{
			consulta.append("select sum(coalesce(m.valorRubrica6,0) + coalesce(m.valorRubrica7,0)  + coalesce(m.valorRubrica8,0) +  ");
			consulta.append("coalesce(m.valorRubrica9,0) + coalesce(m.valorRubrica10,0) + ");
			consulta.append("coalesce(m.valorRubrica11,0) + coalesce(m.valorRubrica12,0) + ");
			consulta.append("coalesce(m.valorRubrica13,0) + coalesce(m.valorRubrica14,0) + coalesce(m.valorRubrica15,0)) ");
			consulta.append("from MovimentoRoteiroEmpresa m where  m.id = :idMovimento");

			retorno = (BigDecimal) session.createQuery(consulta.toString()).setInteger("idMovimento", idMovimentoRoteiroEmpresa)
							.setMaxResults(1).uniqueResult();

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
	 * Relação dos imóveis faturados/pré-faturados no grupo
	 * 
	 * @date 26/08/2012
	 * @author Hebert Falcão
	 */
	public Collection<Integer> pesquisarImoveisFaturadosOuPreFaturadosNoGrupo(Integer idFaturamentoGrupo, Integer anoMesFaturamento)
					throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		StringBuilder consulta = new StringBuilder();

		try{
			consulta.append("select distinct mrem.imovel.id ");
			consulta.append("from MovimentoRoteiroEmpresa mrem ");
			consulta.append("where mrem.faturamentoGrupo.id = :idFaturamentoGrupo ");
			consulta.append("  and mrem.anoMesMovimento = :anoMesFaturamento ");

			Query query = session.createQuery(consulta.toString());

			query.setInteger("idFaturamentoGrupo", idFaturamentoGrupo);
			query.setInteger("anoMesFaturamento", anoMesFaturamento);

			retorno = query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Método que retorna os imóveis condominiais ligados de água e/ou
	 * ligados de esgoto que possuam hidrômetro no poço das quadras pertencentes às rotas da lista
	 * recebida, a partir das tabelas IMOVEL, QUADRA e ROTA
	 * [UC0103] Efetuar Rateio de Consumo
	 * 
	 * @author Anderson Italo
	 * @date 21/05/2012
	 * @param rotas
	 * @return Imoveis
	 */
	public Collection pesquisarImovelCondominioParaCalculoDoRateioDasRotasLigados(Collection colecaoRota) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{

			consulta.append("select imovel.id ,imovel.ligacaoAguaSituacao.id, ");
			consulta.append("imovel.ligacaoEsgotoSituacao.id,imovel.ligacaoAguaSituacao.indicadorFaturamentoSituacao, ");
			consulta.append("imovel.ligacaoEsgotoSituacao.indicadorFaturamentoSituacao ");
			consulta.append("from ");
			consulta.append("gcom.cadastro.imovel.Imovel imovel ");
			consulta.append("inner join imovel.quadra quadra ");
			consulta.append("inner join imovel.rota rota ");
			consulta.append("where ");
			consulta.append("imovel.indicadorImovelCondominio = ");
			consulta.append(ConstantesSistema.SIM.toString());
			consulta.append(" and (imovel.ligacaoAguaSituacao.id = ");
			consulta.append(LigacaoAguaSituacao.LIGADO.toString());
			consulta.append(" or ");
			consulta.append(" (imovel.ligacaoEsgotoSituacao.id = ");
			consulta.append(LigacaoEsgotoSituacao.LIGADO.toString());
			consulta.append(" and imovel.hidrometroInstalacaoHistorico is not null))");
			consulta.append(" and rota.id = :idRota ");

			retorno = session.createQuery(consulta.toString()).setParameter("idRota", ((Rota) colecaoRota.iterator().next()).getId())
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

	public Collection pesquisarImoveisParaFaturamento(Integer idImovel) throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{

			consulta.append("select im.id,"); // 0
			consulta.append("im.ligacaoAguaSituacao.id, "); // 1
			consulta.append("im.ligacaoEsgotoSituacao.id, "); // 2
			consulta.append("li.id, "); // 3
			consulta.append("hia.id, "); // 4
			consulta.append("hdra.id, "); // 5
			consulta.append("hdra.numeroDigitosLeitura, "); // 6
			consulta.append("fst.id,"); // 7
			consulta.append("im.pocoTipo.id, "); // 8
			consulta.append("hie.id, "); // 9
			consulta.append("hdre.id, "); // 10
			consulta.append("im.rota.indicadorAjusteConsumo, "); // 11
			consulta.append("li.numeroConsumoMinimoAgua, "); // 12
			consulta.append("im.indicadorImovelCondominio, "); // 13
			consulta.append("fst.indicadorParalisacaoFaturamento, "); // 14
			consulta.append("im.indicadorDebitoConta, "); // 15
			consulta.append("le.id, "); // Ligacao Esgoto //16
			consulta.append("le.consumoMinimo, "); // 17
			consulta.append("hia.dataInstalacao, "); // 18
			consulta.append("ct.id, "); // 19
			consulta.append("le.percentualAguaConsumidaColetada, "); // 20
			consulta.append("im.quantidadeEconomias, "); // 21
			consulta.append("hdre.numeroDigitosLeitura, "); // 22
			consulta.append("hie.dataInstalacao, ");// 23
			consulta.append("fst.indicadorValidoAgua, "); // 24
			consulta.append("fst.indicadorValidoEsgoto, "); // 25
			consulta.append("esferaPoderClieResp.id, "); // 26
			consulta.append("im.ligacaoAguaSituacao.indicadorFaturamentoSituacao, "); // 27
			consulta.append("im.ligacaoEsgotoSituacao.indicadorFaturamentoSituacao, "); // 28
			consulta.append("hia.indicadorInstalcaoSubstituicao, "); // 29
			consulta.append("hie.indicadorInstalcaoSubstituicao, "); // 30
			consulta.append("fst.indicadorFaturamentoParalisacaoEsgoto, "); // 31
			consulta.append("hcpa.id, "); // 32
			consulta.append("hcpa.codigoHidrometroCapacidade, "); // 33
			consulta.append("hcpe.id, "); // 34
			consulta.append("hcpe.codigoHidrometroCapacidade, "); // 35
			consulta.append("im.ligacaoAgua.dataCorte, "); // 36
			consulta.append("im.setorComercial.id, "); // 37
			consulta.append("im.setorComercial.codigo, "); // 38
			consulta.append("im.localidade.id, "); // 39
			consulta.append("im.quadra.id, "); // 40
			consulta.append("im.imovelPerfil.id, "); // 41
			consulta.append("im.imovelContaEnvio.id, "); // 42
			consulta.append("im.faturamentoTipo.id, "); // 43
			consulta.append("im.quadra.numeroQuadra, "); // 44
			consulta.append("im.lote, "); // 45
			consulta.append("im.imovelAguaParaTodos.id, "); // 46
			consulta.append("im.diaVencimento, "); // 47
			consulta.append("im.indicadorEmissaoExtratoFaturamento, "); // 48
			consulta.append("im.rota.id, "); // 49
			consulta.append("im.rota.leituraTipo.id, "); // 50
			consulta.append("im.rota.leiturista.id, "); // 51
			consulta.append("im.rota.empresa.id, "); // 52
			consulta.append("im.rota.indicadorFiscalizarCortado, "); // 53
			consulta.append("im.rota.indicadorFiscalizarSuprimido, "); // 54
			consulta.append("ctt.id, "); // 55
			consulta.append("im.dataValidadeTarifaTemporaria, "); // 56
			consulta.append("hila.id, "); // 57
			consulta.append("hila.descricao, "); // 58
			consulta.append("hile.id, "); // 59
			consulta.append("hile.descricao, "); // 60
			consulta.append("im.ligacaoEsgotoSituacao.indicadorAjusteConsumo, "); // 61
			consulta.append("im.ligacaoAguaSituacao.indicadorAjusteConsumo, "); // 62
			consulta.append("ligacaoEsgotoPerfil.percentualEsgotoConsumidaColetada, "); // 63
			consulta.append("im.subLote "); // 64
			consulta.append("from Imovel im ");
			consulta.append("inner join im.rota.faturamentoGrupo ");
			consulta.append("inner join im.consumoTarifa ct ");
			consulta.append("left join im.consumoTarifaTemporaria ctt ");
			consulta.append("left join im.ligacaoAgua li ");
			consulta.append("left join im.ligacaoAgua.hidrometroInstalacaoHistorico hia ");
			consulta.append("left join im.ligacaoAgua.hidrometroInstalacaoHistorico.hidrometroLocalInstalacao hila ");
			consulta.append("left join im.ligacaoAgua.hidrometroInstalacaoHistorico.hidrometro hdra ");
			consulta.append("left join im.ligacaoAgua.hidrometroInstalacaoHistorico.hidrometro.hidrometroCapacidade hcpa ");
			consulta.append("left join im.hidrometroInstalacaoHistorico hie ");
			consulta.append("left join im.hidrometroInstalacaoHistorico.hidrometroLocalInstalacao hile ");
			consulta.append("left join im.hidrometroInstalacaoHistorico.hidrometro hdre ");
			consulta.append("left join im.hidrometroInstalacaoHistorico.hidrometro.hidrometroCapacidade hcpe ");
			consulta.append("left join im.faturamentoSituacaoTipo fst ");
			consulta.append("left join im.ligacaoEsgoto le ");
			consulta.append("left join le.ligacaoEsgotoPerfil ligacaoEsgotoPerfil ");
			consulta.append("left join im.clienteImoveis clieImovResp ");
			consulta.append("with (clieImovResp.clienteRelacaoTipo.id = :clienteResponsavel and clieImovResp.dataFimRelacao is null) ");
			consulta.append("left join clieImovResp.cliente clieResp ");
			consulta.append("left join clieResp.clienteTipo tipoClieResp ");
			consulta.append("left join tipoClieResp.esferaPoder esferaPoderClieResp ");
			consulta.append("where  ");
			consulta.append("  im.id = :idImovel ");
			consulta.append(" and im.indicadorExclusao = :indicadorExclusao");

			retorno = session.createQuery(consulta.toString()).setInteger("idImovel", idImovel).setInteger("clienteResponsavel",
							ClienteRelacaoTipo.RESPONSAVEL.intValue()).setInteger("indicadorExclusao", ConstantesSistema.NAO.intValue())
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
	 * [UC0275] - Gerar Resumo das Ligaï¿½ï¿½es/Economias
	 * Método que deleta o resumo ligações economia
	 * 
	 * @author Josenildo Neves
	 * @date 16/01/2013
	 * @param idRota
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */

	public void deletarResumoLigacoesEconomia(Integer idRota, int anoMesReferenciaArrecadacao) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{
			String delete = "delete ResumoLigacoesEconomia rle "
							+ "where rle.rota.id = :idRota and anoMesReferencia = :anoMesReferenciaArrecadacao";

			session.createQuery(delete).setInteger("idRota", idRota).setInteger("anoMesReferenciaArrecadacao", anoMesReferenciaArrecadacao)
							.executeUpdate();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
	}

	/**
	 * @param referenciaFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List<Integer> pesquisarRotasComAlteracaoNasLigacoesEconomiasPorReferencia(Integer referenciaFaturamento)
					throws ErroRepositorioException{

		List<Integer> retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{

			consulta.append("SELECT R AS idRota ");
			// consulta.append( ",SUM(DIF) ");
			consulta.append(" FROM ");
			consulta.append("	(SELECT R,S,Q,C,LA,LE,SUM(RLE) RLE1,SUM(IMO) IMO2, (SUM(RLE) - SUM(IMO)) DIF ");
			consulta.append("	FROM ");
			consulta.append("		(SELECT ");
			consulta.append("			RL.ROTA_ID R, ");
			consulta.append("			RL.STCM_ID S, ");
			consulta.append("			RL.QDRA_ID Q, ");
			consulta.append("			RL.CATG_ID C, ");
			consulta.append("			RL.LAST_ID LA, ");
			consulta.append("			RL.LEST_ID LE, ");
			consulta.append("			sum(RL.RELE_QTLIGACOES) RLE, ");
			consulta.append("			0 IMO ");
			consulta.append("		FROM resumo_ligacoes_economia RL ");
			consulta.append("		INNER JOIN ROTA RO ON RO.ROTA_ID = RL.ROTA_ID ");
			consulta.append("		WHERE RELE_AMREFERENCIA = :referenciaFaturamento ");
			// consulta.append("			AND RO.FTGR_ID = 25 ");
			// consulta.append("			--AND RO.ROTA_ID = 4761 ");
			consulta.append("		GROUP BY ");
			consulta.append("			RL.ROTA_ID, ");
			consulta.append("			RL.STCM_ID, ");
			consulta.append("			RL.QDRA_ID, ");
			consulta.append("			RL.CATG_ID, ");
			consulta.append("			RL.LAST_ID, ");
			consulta.append("			RL.LEST_ID ");
			consulta.append("		UNION ");
			consulta.append("		SELECT ");
			consulta.append("			I.ROTA_ID R, ");
			consulta.append("			I.STCM_ID S, ");
			consulta.append("			I.QDRA_ID Q, ");
			consulta.append("			CT.CATG_ID C, ");
			consulta.append("			I.LAST_ID LA, ");
			consulta.append("			I.LEST_ID LE, ");
			consulta.append("			0 RLE, ");
			consulta.append("			COUNT(*) IMO ");
			consulta.append("		FROM IMOVEL I ");
			consulta.append("		INNER JOIN ROTA R2 ON R2.ROTA_ID = I.ROTA_ID ");
			consulta.append("		INNER JOIN CATEGORIA CT ON CT.CATG_ID = ");
			consulta.append("			(SELECT MIN(IC2.CATG_ID) FROM IMOVEL_SUBCATEGORIA IC2 WHERE IC2.IMSB_QTECONOMIA = ");
			consulta.append("				(SELECT MAX(IMSB_QTECONOMIA) FROM IMOVEL_SUBCATEGORIA IC1 WHERE IC1.IMOV_ID = I.IMOV_ID) ");
			consulta.append("		 		AND IC2.IMOV_ID = I.IMOV_ID) ");
			// consulta.append("		WHERE R2.FTGR_ID = 25 ");
			// consulta.append("		--WHERE I.IMOV_ID = 1259172 ");
			// consulta.append("		--WHERE I.ROTA_ID = 4761 ");
			consulta.append("		GROUP BY  ");
			consulta.append("			I.ROTA_ID, ");
			consulta.append("			I.STCM_ID, ");
			consulta.append("			I.QDRA_ID, ");
			consulta.append("			CT.CATG_ID, ");
			consulta.append("			I.LAST_ID, ");
			consulta.append("			I.LEST_ID) TAB1 ");
			consulta.append("	GROUP BY R,S,Q,C,LA,LE ");
			consulta.append("	ORDER BY R,S,Q,C,LA,LE) TAB2 ");
			consulta.append("GROUP BY R ");
			consulta.append("ORDER BY R ");

			retorno = session.createSQLQuery(consulta.toString()).addScalar("idRota", Hibernate.INTEGER).setInteger(
							"referenciaFaturamento", referenciaFaturamento).list();

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
	 * Método que verifica se a Ligação de esgoto foi feita depois do último dia da referencia
	 * informada por imóvel.
	 * 
	 * @author Josenildo Neves
	 * @date 29/01/2013
	 */
	public boolean verificarDateLigacaoEsgotoPorImovel(Integer idImovel, Date referenciaInicial, Date referenciaFinal)
					throws ErroRepositorioException{

		boolean dataLigacaoMaiorQueReferencia = false;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{

			consulta.append("SELECT ");
			consulta.append("	le.id "); // 0
			consulta.append("FROM LigacaoEsgoto le ");
			consulta.append("WHERE le.id = :idImovel ");
			consulta.append("	AND le.ultimaAlteracao >= :referenciaInicial ");
			consulta.append("	AND le.dataLigacao IS NOT NULL ");
			consulta.append("	AND le.dataLigacao > :referenciaFinal ");

			Integer idLigacaoEsgoto = (Integer) session.createQuery(consulta.toString()).setInteger("idImovel", idImovel).setDate(
							"referenciaInicial", referenciaInicial).setDate("referenciaFinal", referenciaFinal).setMaxResults(1)
							.uniqueResult();

			if(!Util.isVazioOuBranco(idLigacaoEsgoto)){
				dataLigacaoMaiorQueReferencia = true;
			}

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return dataLigacaoMaiorQueReferencia;
	}

	/**
	 * Método que seleciona a Ligação água pelo imóvel.
	 * 
	 * @author Josenildo Neves
	 * @date 28/01/2013
	 */
	public Collection pesquisarLigacaoAguaPorImovel(Integer idImovel, Date referenciaInicial, Date referenciaFinal)
					throws ErroRepositorioException{

		Collection colecaoLigacaoAgua = new ArrayList<Rota>();
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{

			consulta.append("SELECT ");
			consulta.append("	la.id, "); // 0
			consulta.append("	la.dataLigacao, "); // 1
			consulta.append("	la.dataSupressao, "); // 2
			consulta.append("	la.dataCorte, "); // 3
			consulta.append("	la.dataReligacao, "); // 4
			consulta.append("	la.ultimaAlteracao, "); // 5
			consulta.append("	la.dataRestabelecimentoAgua, "); // 6
			consulta.append("	la.dataCorteAdministrativo, "); // 7
			consulta.append("	la.dataImplantacao "); // 8
			consulta.append("FROM LigacaoAgua la ");
			consulta.append("WHERE la.id = :idImovel ");

			colecaoLigacaoAgua = session.createQuery(consulta.toString()).setInteger("idImovel", idImovel).list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return colecaoLigacaoAgua;
	}

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0100] – Gerar Arquivo Texto para Faturamento Imediato – Modelo 2
	 * 
	 * @author Anderson Italo
	 * @date 21/02/2013
	 */
	public Integer obterCodigoEmpresaConcessionariaLocalidadeVigente(Integer idLocalidade) throws ErroRepositorioException{

		StringBuffer consulta = new StringBuffer("");
		Session session = HibernateUtil.getSession();
		Integer retorno = null;
		Number retornoConsulta = null;

		try{
			consulta.append(" select cons.conc_cdempresafebraban as empr FROM concessionaria_localidade consLoc ");
			consulta.append(" inner join concessionaria cons on cons.conc_id = consLoc.conc_id ");
			consulta.append(" where consLoc.loca_id = " + idLocalidade.toString());
			consulta.append(" and consLoc.cnlc_dtvigenciafim is null ");

			retornoConsulta = (Number) session.createSQLQuery(consulta.toString()).addScalar("empr", Hibernate.INTEGER).setMaxResults(1)
							.uniqueResult();

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

	/**
	 * [UC3012 – Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0101] – Gerar Integração para Faturamento Imediato – Modelo 3
	 * 
	 * @author Anderson Italo
	 * @date 27/03/2013
	 */
	public Object[] pesquisarDadosHidrometroInstalacaoHistoricoPorId(Integer idHidrometroInstalacaoHistorico)
					throws ErroRepositorioException{

		StringBuffer consulta = new StringBuffer("");
		Session session = HibernateUtil.getSession();
		Object[] retorno = null;

		try{
			consulta.append(" select hih.hidi_nnselo as seloHid, hih.hili_id as idLocalInstHid, hm.himc_dsabreviadahidrometromarc as marcaHid, ");
			consulta.append(" hc.hicp_dsabreviadahidrometrocapa as capacidadeHid, h.hidr_nndigitosleitura as numDigitos ");
			consulta.append(" FROM hidrometro_instalacao_hist hih ");
			consulta.append(" inner join hidrometro h on h.hidr_id = hih.hidr_id ");
			consulta.append(" inner join hidrometro_marca hm on hm.himc_id = h.himc_id ");
			consulta.append(" inner join hidrometro_capacidade hc on hc.hicp_id = h.hicp_id ");
			consulta.append(" where hih.hidi_id = " + idHidrometroInstalacaoHistorico.toString());

			retorno = (Object[]) session.createSQLQuery(consulta.toString()).addScalar("seloHid", Hibernate.STRING)
							.addScalar("idLocalInstHid", Hibernate.INTEGER).addScalar("marcaHid", Hibernate.STRING)
							.addScalar("capacidadeHid", Hibernate.STRING).addScalar("numDigitos", Hibernate.SHORT).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
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
	public Collection<Object[]> pesquisarDadosConsumoHistoricoRetroativos(Integer idImovel, Integer numeroMeses, Integer idLigacaoTipo)
					throws ErroRepositorioException{

		StringBuffer consulta = new StringBuffer("");
		Session session = HibernateUtil.getSession();
		Collection<Object[]> retorno = null;

		try{
			consulta.append(" select cstp_id as idTipoConsumo, ch.csan_id as idAnormalidade ");
			consulta.append(" FROM consumo_historico ch ");
			consulta.append(" where ch.imov_id = " + idImovel.toString());
			consulta.append(" and ch.lgti_id = " + idLigacaoTipo.toString());
			consulta.append(" order by ch.cshi_amfaturamento desc ");

			retorno = (Collection<Object[]>) session.createSQLQuery(consulta.toString()).addScalar("idTipoConsumo", Hibernate.INTEGER)
							.addScalar("idAnormalidade", Hibernate.INTEGER).setMaxResults(numeroMeses).list();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
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
	public Object[] pesquisarDadosUltimaLeituraReal(Integer idImovel)
					throws ErroRepositorioException{

		StringBuffer consulta = new StringBuffer("");
		Session session = HibernateUtil.getSession();
		Object[] retorno = null;

		try{
			consulta.append(" select mh.mdhi_nnleituraatualfaturamento as leituraAtualFatu, cshi_amfaturamento as anoMesFatu ");
			consulta.append(" FROM medicao_historico mh ");
			consulta.append(" inner join consumo_historico ch on ch.imov_id = mh.lagu_id and ch.cshi_amfaturamento = mh.mdhi_amleitura ");
			consulta.append(" where mh.lagu_id = " + idImovel.toString());
			consulta.append(" and ch.cstp_id = " + ConsumoTipo.REAL.toString());
			consulta.append(" and mh.mdhi_nnleituraatualfaturamento is not null ");
			consulta.append(" order by ch.cshi_amfaturamento desc ");

			retorno = (Object[]) session.createSQLQuery(consulta.toString()).addScalar("leituraAtualFatu", Hibernate.INTEGER)
							.addScalar("anoMesFatu", Hibernate.INTEGER).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0084] - Gerar Faturamento Imediato
	 * [SB0007] - Gerar Movimento Roteiro de Empresa
	 * 
	 * @author Anderson Italo
	 * @date 11/04/2013
	 */
	public Object[] obterMedicaoHistoricoAnterior(Integer idImovel, Integer anoMesFaturamentoAtual, Integer idMedicaoTipo)
					throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{

			consulta.append(" select mh.mdhi_dtleituraatualfaturamento as dataLeituraAtualFaturamento, "); // 0
			consulta.append(" mh.mdhi_nnleituraatualfaturamento as leituraAtualFaturamento, "); // 1
			consulta.append(" mh.mdhi_nnleituraatualinformada as leituraAtualInformada, "); // 2
			consulta.append(" mh.ltst_idleiturasituacaoatual as idLeituraSituacaoAtual, "); // 3
			consulta.append(" mh.mdhi_nnconsumocreditoanterior as creditoConsumoAnterior "); // 4
			consulta.append(" from medicao_historico mh ");

			if(idMedicaoTipo.equals(MedicaoTipo.LIGACAO_AGUA)){
															
				consulta.append(" inner join ligacao_agua la on la.lagu_id = mh.lagu_id ");
				consulta.append(" where ");
				consulta.append(" mh.lagu_id = :idImovel and ");
				consulta.append(" mh.mdhi_amleitura = (select max(mh1.mdhi_amleitura) from medicao_historico mh1 where mh1.lagu_id = mh.lagu_id and ");
				consulta.append(" mh.medt_id = mh1.medt_id and mh1.mdhi_amleitura < :anoMes) and ");
			}else{

				consulta.append(" inner join imovel im on im.imov_id = mh.imov_id ");
				consulta.append(" where ");
				consulta.append(" mh.imov_id = :idImovel and ");
				consulta.append(" mh.mdhi_amleitura = (select max(mh1.mdhi_amleitura) from medicao_historico mh1 where mh1.imov_id = mh.imov_id and ");
				consulta.append(" mh.medt_id = mh1.medt_id and mh1.mdhi_amleitura < :anoMes) and ");
			}

			consulta.append(" mh.medt_id = :idMedicaoTipo and mh.mdhi_amleitura < :anoMes ");

			retorno = (Object[]) session.createSQLQuery(consulta.toString()).addScalar("dataLeituraAtualFaturamento", Hibernate.DATE)
							.addScalar("leituraAtualFaturamento", Hibernate.INTEGER).addScalar("leituraAtualInformada", Hibernate.INTEGER)
							.addScalar("idLeituraSituacaoAtual", Hibernate.INTEGER).addScalar("creditoConsumoAnterior", Hibernate.INTEGER)
							.setInteger("idImovel", idImovel.intValue()).setInteger("idMedicaoTipo", idMedicaoTipo.intValue())
							.setInteger("anoMes", anoMesFaturamentoAtual.intValue()).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){


			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0084] - Gerar Faturamento Imediato
	 * [SB0007] - Gerar Movimento Roteiro de Empresa
	 * 
	 * @author Anderson Italo
	 * @date 11/04/2013
	 */
	public Integer obterUltimoConsumoFaturadoImovel(Integer idImovel, Integer tipoLigacao) throws ErroRepositorioException{

		Integer retorno = null;
		Number retornoConsulta = null;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select cshi.numeroConsumoFaturadoMes from ConsumoHistorico cshi "
							+ "where cshi.imovel.id = :idImovel and cshi.ligacaoTipo.id = :tipoLigacao "
							+ "order by cshi.referenciaFaturamento desc ";

			retornoConsulta = (Number) session.createQuery(consulta).setInteger("idImovel", idImovel)
							.setInteger("tipoLigacao", tipoLigacao)
							.setMaxResults(1).uniqueResult();

			if(retornoConsulta != null){

				retorno = retornoConsulta.intValue();
			}

		}catch(HibernateException e){

			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public Integer obterConsumoMedio(Integer idImovel) throws ErroRepositorioException{

		Integer consumoMedio = null;
		Number retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{
			consulta.append("select cosumoHistorico.cshi_nnconsumomedio from consumo_historico cosumoHistorico ");
			consulta.append(" inner join imovel imov on imov.imov_id = cosumohistorico.imov_id");
			consulta.append(" inner join rota rot on rot.rota_id = imov.rota_id");
			consulta.append(" inner join faturamento_grupo ftg on rot.ftgr_id = ftg.ftgr_id");
			consulta.append(" where ");
			consulta.append(" cosumohistorico.imov_id=:idImovel and");
			consulta.append(" cosumohistorico.lgti_id=:ligacaoTipo and");
			consulta.append(" cosumohistorico.cshi_amfaturamento<=ftg.FTGR_AMREFERENCIA ");
			consulta.append(" order by cosumohistorico.cshi_amfaturamento desc");
			

			retorno = (Number) session.createSQLQuery(consulta.toString()).setInteger("idImovel", idImovel.intValue())
							.setInteger("ligacaoTipo", LigacaoTipo.LIGACAO_AGUA).setMaxResults(1).uniqueResult();

			if(retorno != null){
				consumoMedio = retorno.intValue();
			}
							
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		// retorna a coleção de atividades pesquisada(s)
		return consumoMedio;
	}

	public Object[] obterIDReferenciaConsumoHistorico(String idImovel) throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{
			consulta.append("select cosumohistorico.cshi_id,max(cosumohistorico.cshi_amfaturamento) from consumo_historico cosumohistorico ");
			consulta.append("where cosumohistorico.imov_id=:idImovel and");
			consulta.append(" cosumohistorico.lgti_id=:ligacaoTipo");
			consulta.append(" group by cosumohistorico.cshi_id,cosumohistorico.cshi_amfaturamento");
			consulta.append(" order by cosumohistorico.cshi_amfaturamento desc");

			retorno = (Object[]) session.createSQLQuery(consulta.toString()).setString("idImovel", idImovel)
							.setInteger("ligacaoTipo", LigacaoTipo.LIGACAO_AGUA).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	public void atualizarUltimoConsumoHistorico(Integer idConsumoHistorico, Integer valor) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer();

		try{

			consulta.append("update consumo_historico  set cshi_nnconsumomedio=:consumoMedio");
			consulta.append(",cshi_tmultimaalteracao=:data");
			consulta.append(" where cshi_id=:idConsumoHistorico  and");
			consulta.append(" lgti_id=:ligacaoTipo");

			session.createSQLQuery(consulta.toString()).setInteger("idConsumoHistorico", idConsumoHistorico)
							.setTimestamp("data", new Date()).setInteger("ligacaoTipo", LigacaoTipo.LIGACAO_AGUA)
							.setInteger("consumoMedio", valor).executeUpdate();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

	}

	/**
	 * [UC0105] - Obter Consumo Mínimo da Ligação
	 * 
	 * @author Anderson Italo
	 * @date 07/05/2013
	 */
	public ConsumoTarifa obterConsumoTarifaTemporariaImovel(Integer idImovel) throws ErroRepositorioException{

		ConsumoTarifa retorno = null;
		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "select consumoTarifaTemp from Imovel im inner join im.consumoTarifaTemporaria consumoTarifaTemp where im.id = :idImovel  and im.dataValidadeTarifaTemporaria >= :dataCorrente";

			retorno = (ConsumoTarifa) session.createQuery(consulta).setInteger("idImovel", idImovel).setDate("dataCorrente", new Date())
							.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException("Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0084] - Gerar Faturamento Imediato
	 * [UC3010] - Verificar Atendimento do Critério de Faturamento pelo Imóvel
	 * 
	 * @author Anderson Italo
	 * @date 27/08/2013
	 */
	public Date obterDataUltimaLeituraReferenciaCortado(Integer idConsumoTipo, Integer idLigacaoTipo, Integer idMedicaoTipo,
					Integer idImovel, Integer consumoMedidoReferenciaCortado)
					throws ErroRepositorioException{

		Date retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{

			consulta.append("select mh.mdhi_dtleituraatualfaturamento as dataLeituraAtualFaturamento from medicao_historico mh ");
			consulta.append("inner join consumo_historico ch on ch.imov_id = mh.lagu_id ");
			consulta.append("where ch.cstp_id = :idConsumoTipo and ");
			consulta.append("ch.lgti_id = :idLigacaoTipo and ");
			consulta.append("ch.cshi_amfaturamento = ");
			consulta.append("(select max(chr.cshi_amfaturamento) from consumo_historico chr where ");
			consulta.append("chr.cstp_id = ch.cstp_id and chr.lgti_id = ch.lgti_id and ");
			consulta.append("chr.imov_id = ch.imov_id) and ");
			consulta.append("ch.cshi_amfaturamento = mh.mdhi_amleitura and ");
			consulta.append("mh.medt_id = :idMedicaoTipo and ");
			consulta.append("mh.lagu_id = :idImovel and ");
			consulta.append("mh.mdhi_dtleituraatualfaturamento is not null and ");
			consulta.append("coalesce(mh.mdhi_nnconsumomedidomes,0) > :consumoMedidoReferenciaCortado ");


			retorno = (Date) session.createSQLQuery(consulta.toString()).addScalar("dataLeituraAtualFaturamento", Hibernate.DATE)
							.setInteger("idConsumoTipo", idConsumoTipo.intValue()).setInteger("idLigacaoTipo", idLigacaoTipo.intValue())
							.setInteger("idMedicaoTipo", idMedicaoTipo.intValue()).setInteger("idImovel", idImovel.intValue())
							.setInteger("consumoMedidoReferenciaCortado", consumoMedidoReferenciaCortado.intValue()).setMaxResults(1)
							.uniqueResult();

		}catch(HibernateException e){


			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC0084] - Gerar Faturamento Imediato
	 * [SB0007] - Gerar Movimento Roteiro de Empresa
	 * 
	 * @author Anderson Italo
	 * @date 11/04/2013
	 */
	public Object[] obterDadosUltimosConsumos(Integer idImovel, Integer anoMesFaturamentoAtual, Integer idMedicaoTipo, Integer idConsumoTipo)
					throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{

			consulta.append(" select mh.mdhi_dtleituraatualfaturamento as dataLeituraAtualFaturamento, "); // 0
			consulta.append(" mh.mdhi_nnleituraatualfaturamento as leituraAtualFaturamento, "); // 1
			consulta.append(" mh.mdhi_nnleituraatualinformada as leituraAtualInformada, "); // 2
			consulta.append(" mh.ltst_idleiturasituacaoatual as idLeituraSituacaoAtual, "); // 3
			consulta.append(" mh.mdhi_nnconsumocreditoanterior as creditoConsumoAnterior "); // 4
			consulta.append(" from medicao_historico mh ");

			if(idMedicaoTipo.equals(MedicaoTipo.LIGACAO_AGUA)){

				consulta.append(" inner join ligacao_agua la on la.lagu_id = mh.lagu_id ");
				consulta.append(" inner join hidrometro_instalacao_hist hih on hih.hidi_id = la.hidi_id ");
				consulta.append(" where ");
				consulta.append(" mh.lagu_id = :idImovel and ");
				consulta.append(" mh.mdhi_amleitura = (select max(mh1.mdhi_amleitura) from medicao_historico mh1 where mh1.lagu_id = mh.lagu_id and ");
				consulta.append(" mh.medt_id = mh1.medt_id) and ");
			}else{

				consulta.append(" inner join imovel im on im.imov_id = mh.imov_id ");
				consulta.append(" inner join hidrometro_instalacao_hist hih on hih.hidi_id = im.hidi_id ");
				consulta.append(" where ");
				consulta.append(" mh.imov_id = :idImovel and ");
				consulta.append(" mh.mdhi_amleitura = (select max(mh1.mdhi_amleitura) from medicao_historico mh1 where mh1.imov_id = mh.imov_id and ");
				consulta.append(" mh.medt_id = mh1.medt_id) and ");
			}

			consulta.append(" mh.medt_id = :idMedicaoTipo and mh.mdhi_amleitura < :anoMes ");

			retorno = (Object[]) session.createSQLQuery(consulta.toString()).addScalar("dataLeituraAtualFaturamento", Hibernate.DATE)
							.addScalar("leituraAtualFaturamento", Hibernate.INTEGER).addScalar("leituraAtualInformada", Hibernate.INTEGER)
							.addScalar("idLeituraSituacaoAtual", Hibernate.INTEGER).addScalar("creditoConsumoAnterior", Hibernate.INTEGER)
							.setInteger("idImovel", idImovel.intValue()).setInteger("idMedicaoTipo", idMedicaoTipo.intValue())
							.setInteger("anoMes", anoMesFaturamentoAtual.intValue()).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Relatório Analise de Consumo
	 * Ítalo Almeida
	 * 11/10/2013
	 */

	public Integer pesquisarLeiturasImovelCount(String idImovel, String anoMes) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		String consulta = "select "
						+ "count(*) as count "// 8
						+ " from medicao_historico medicaoHistorico "
						+ " left join ligacao_agua ligacaoAgua on medicaoHistorico.lagu_id = ligacaoAgua.lagu_id"
						+ " left join imovel imovel on imovel.imov_id = medicaoHistorico.lagu_id "
						+ " left join ligacao_agua_situacao ligacaoAguaSituacao on ligacaoAguaSituacao.last_id =imovel.last_id "
						+ " left outer join leiturista leiturista  on medicaoHistorico.func_id = leiturista.func_id"
						+ " left outer join funcionario funcionario on leiturista.func_id = funcionario.func_id"
						+ " left join hidrometro_instalacao_hist hidrometroInstalacaoHist on medicaoHistorico.hidi_id = hidrometroInstalacaoHist.hidi_id"
						+ " left join hidrometro hidrometro on hidrometro.hidr_id = hidrometroInstalacaoHist.hidr_id"
						+ " left join leitura_anormalidade leituraAnormalidadeInformada on leituraAnormalidadeInformada.ltan_id = medicaoHistorico.ltan_idleituraanormalidadeinfo "
						+ " left join consumo_historico consumoHistorico on consumoHistorico.imov_id=medicaoHistorico.lagu_id and "
						+ " medicaoHistorico.mdhi_amleitura = consumoHistorico.cshi_amfaturamento"
						+ " left join consumo_anormalidade consumoAnormalidade on consumoAnormalidade.csan_id=consumoHistorico.csan_id "
						+ " left join ligacao_esgoto_situacao ligacaoEsgotoSituacao on ligacaoEsgotoSituacao.lest_id = imovel.lest_id "
						+ " where medicaoHistorico.lagu_id = " + idImovel + " and medicaoHistorico.mdhi_amleitura = " + anoMes;

		try{
			retorno = (Integer) session.createSQLQuery(consulta).addScalar("count", Hibernate.INTEGER).uniqueResult();

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
	 * [UC3112][UC3113] Atualizacao Cadastral Coletor de Dados
	 * 
	 * @author Victon Malcolm
	 * @since 08/10/2013
	 */
	public Collection pesquisarAtualizacaoCadastralColetorDados(Integer referenciaInicial,
 Integer referenciaFinal, Integer matricula,
					Integer localidade, Integer setorComercial, Integer rota, Boolean relatorio)
					throws ErroRepositorioException{

		Collection colecaoMovimentoRoteiroEmpresa = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{

			consulta.append("SELECT ");
			consulta.append("mre.MREM_ID, ");// 0
			consulta.append("mre.IMOV_ID, ");// 1
			consulta.append("mre.mrem_inscricao, ");// 2
			consulta.append("mre.mrem_ammovimento, ");// 3
			consulta.append("fu.func_id || '-' || fu.FUNC_NMFUNCIONARIO as func, ");// 4
			consulta.append("mre.mrem_tmleitura ");// 5
			if(relatorio){
				consulta.append(", mre.MREM_NNIMOVEL, ");// 6
				consulta.append("mre.MREM_NNIMOVELALTERACAO, ");// 7
				consulta.append("mre.MREM_DSCOMPLEMENTOENDERECO, ");// 8
				consulta.append("mre.MREM_DSCOMPLEMENTOENDERECOALT, ");// 9
				consulta.append("mre.MREM_NNHIDROMETRO, ");// 10
				consulta.append("mre.MREM_NNHIDROMETROALTERACAO, ");// 11
				consulta.append("mre.MREM_QTECONOMIARESIDENCIAL, ");// 12
				consulta.append("mre.MREM_QTECONOMIARESIDENCIALALT, ");// 13
				consulta.append("mre.MREM_QTECONOMIACOMERCIAL, ");// 14
				consulta.append("mre.MREM_QTECONOMIACOMERCIALALT, ");// 15
				consulta.append("mre.MREM_QTECONOMIAINDUSTRIAL, ");// 16
				consulta.append("mre.MREM_QTECONOMIAINDUSTRIALALT, ");// 17
				consulta.append("mre.MREM_QTECONOMIAPUBLICA, ");// 18
				consulta.append("mre.MREM_QTECONOMIAPUBLICAALT ");// 19
			}
			consulta.append("FROM movimento_roteiro_empresa mre ");
			consulta.append("left join leiturista le on le.leit_id = mre.leit_id ");
			consulta.append("left join funcionario fu on fu.func_id=le.func_id ");
			consulta.append("WHERE mre.mrem_ammovimento between :referenciaInicial and :referenciaFinal and ");
			if(matricula != null && !matricula.equals("")){
				consulta.append("mre.IMOV_ID = :matricula and ");
			}else{
				if(localidade != null && !localidade.equals("")){
					consulta.append("mre.LOCA_ID  = :localidade and ");
				}
				if(setorComercial != null && !setorComercial.equals("")){
					consulta.append("mre.MREM_CDSETORCOMERCIAL = :setorComercial and ");
				}
				if(rota != null && !rota.equals("")){
					consulta.append("mre.MREM_CDROTA = :rota and ");
				}
			}
			consulta.append("(MREM_NNHIDROMETROALTERACAO IS NOT NULL OR " + "MREM_DSCOMPLEMENTOENDERECOALT IS NOT NULL OR "
							+ "MREM_QTECONOMIARESIDENCIALALT IS NOT NULL OR " + "MREM_QTECONOMIACOMERCIALALT IS NOT NULL OR "
							+ "MREM_QTECONOMIAINDUSTRIALALT IS NOT NULL OR " + "MREM_QTECONOMIAPUBLICAALT IS NOT NULL) "
							+ "ORDER BY MREM_INSCRICAO, MREM_AMMOVIMENTO DESC");
			Query query = session.createSQLQuery(consulta.toString()).setInteger("referenciaInicial", referenciaInicial)
							.setInteger("referenciaFinal", referenciaFinal);
			
			if(matricula != null && !matricula.equals("")){
				query.setInteger("matricula", matricula);
			}else{
				if(localidade != null && !localidade.equals("")){
					query.setInteger("localidade", localidade);
				}
				if(setorComercial != null && !setorComercial.equals("")){
					query.setInteger("setorComercial", setorComercial);
				}
				if(rota != null && !rota.equals("")){
					query.setInteger("rota", rota);
				}
			}
			colecaoMovimentoRoteiroEmpresa = query.list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return colecaoMovimentoRoteiroEmpresa;
	}

	/**
	 * [UC3113] Atualizacao Cadastral Coletor de Dados
	 * 
	 * @author Victon Malcolm
	 * @since 08/10/2013
	 */
	public Collection pesquisarConsultaAtualizacaoCadastralColetorDados(Integer id) throws ErroRepositorioException{

		Collection colecaoConsultaAtualizacaoCadastralColetorDados = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");
		try{
			consulta.append("SELECT  " + "im.loca_id," + "seco.stcm_cdsetorcomercial," + "qu.qdra_nnquadra," + "im.imov_nnlote,"
							+ "im.imov_nnsublote," + "cl.clie_nmcliente,"
							+ "case when cltp.CLTP_ICPESSOAFISICAJURIDICA=1 then cl.CLIE_NNCPF else cl.CLIE_NNCNPJ end as CpfCnpj,"
							+ "las.LAST_DSLIGACAOAGUASITUACAO," + "les.LEST_DSLIGACAOESGOTOSITUACAO," + "fu.FUNC_ID,"
							+ "fu.FUNC_NMFUNCIONARIO," + "mre.MREM_TMLEITURA," + "mre.MREM_AMMOVIMENTO," + "mre.MREM_NNIMOVEL,"
							+ "mre.MREM_NNIMOVELALTERACAO," + "mre.MREM_DSCOMPLEMENTOENDERECO," + "mre.MREM_DSCOMPLEMENTOENDERECOALT,"
							+ "mre.MREM_NNHIDROMETRO," + "mre.MREM_NNHIDROMETROALTERACAO," + "mre.MREM_QTECONOMIARESIDENCIAL,"
							+ "mre.MREM_QTECONOMIARESIDENCIALALT," + "mre.MREM_QTECONOMIACOMERCIAL," + "mre.MREM_QTECONOMIACOMERCIALALT,"
							+ "mre.MREM_QTECONOMIAINDUSTRIAL," + "mre.MREM_QTECONOMIAINDUSTRIALALT," + "mre.MREM_QTECONOMIAPUBLICA,"
							+ "mre.MREM_QTECONOMIAPUBLICAALT, " + "im.imov_id " + "from movimento_roteiro_empresa mre "
							+ "left join imovel im on im.imov_id = mre.imov_id "
							+ "left join setor_comercial seco on seco.STCM_ID = im.STCM_ID "
							+ "left join quadra qu on qu.QDRA_ID = im.QDRA_ID "
							+ "left join cliente_imovel clim on clim.imov_id = im.imov_id "
							+ "left join cliente cl on cl.clie_id = clim.clie_id "
							+ "left join cliente_tipo cltp on cltp.CLTP_ID = cl.CLTP_ID "
							+ "left join ligacao_agua_situacao las on las.last_id = im.last_id "
							+ "left join ligacao_esgoto_situacao les on les.lest_id = im.lest_id "
							+ "left join leiturista le on le.leit_id = mre.leit_id " + "left join funcionario fu on fu.func_id=le.func_id "
							+ "where clim.CLIM_DTRELACAOFIM is null and mre.MREM_ID = :idMrem " + "GROUP BY im.loca_id,"
							+ "seco.stcm_cdsetorcomercial," + "qu.qdra_nnquadra," + "im.imov_nnlote," + "im.imov_nnsublote,"
							+ "cl.clie_nmcliente,"
							+ "case when cltp.CLTP_ICPESSOAFISICAJURIDICA=1 then cl.CLIE_NNCPF else cl.CLIE_NNCNPJ end,"
							+ "las.LAST_DSLIGACAOAGUASITUACAO," + "les.LEST_DSLIGACAOESGOTOSITUACAO," + "fu.FUNC_ID,"
							+ "fu.FUNC_NMFUNCIONARIO," + "mre.MREM_TMLEITURA," + "mre.MREM_AMMOVIMENTO," + "mre.MREM_NNIMOVEL,"
							+ "mre.MREM_NNIMOVELALTERACAO," + "mre.MREM_DSCOMPLEMENTOENDERECO," + "mre.MREM_DSCOMPLEMENTOENDERECOALT,"
							+ "mre.MREM_NNHIDROMETRO," + "mre.MREM_NNHIDROMETROALTERACAO," + "mre.MREM_QTECONOMIARESIDENCIAL,"
							+ "mre.MREM_QTECONOMIARESIDENCIALALT," + "mre.MREM_QTECONOMIACOMERCIAL," + "mre.MREM_QTECONOMIACOMERCIALALT,"
							+ "mre.MREM_QTECONOMIAINDUSTRIAL," + "mre.MREM_QTECONOMIAINDUSTRIALALT," + "mre.MREM_QTECONOMIAPUBLICA,"
							+ "mre.MREM_QTECONOMIAPUBLICAALT, " + "im.imov_id");
			colecaoConsultaAtualizacaoCadastralColetorDados = session.createSQLQuery(consulta.toString()).setInteger("idMrem", id).list();
		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}
		return colecaoConsultaAtualizacaoCadastralColetorDados;
	}
	public Collection<FaturamentoGrupo> pesquisarGrupoFatProcessoRetornoImedInicConc() throws ErroRepositorioException{

		Collection retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");
		Collection<FaturamentoGrupo> colecaoFaturamentoGrupo = new ArrayList<FaturamentoGrupo>();

		try{

			consulta.append(" SELECT ftgr.ftgr_id id,  ftgr.ftgr_dsfaturamentogrupo descricao ");
			consulta.append(" FROM faturamento_grupo ftgr ");
			consulta.append(" INNER JOIN faturamento_grupo_cron_mensal ftcm ON ftgr.ftgr_id = ftcm.ftgr_id ");
			consulta.append(" WHERE ftcm.ftcm_amreferencia = ");
			consulta.append(" (SELECT sistema_parametros.parm_amreferenciafaturamento FROM sistema_parametros)");
			consulta.append(" AND ftcm.ftcm_id IN (SELECT ftcm_id ");
			consulta.append(" FROM faturamento_atividade_cron ");
			consulta.append(" WHERE ftac_tmrealizacao IS NOT NULL)");
			consulta.append(" ORDER BY id");

			SQLQuery sqlQuery = session.createSQLQuery(consulta.toString());

			retorno = sqlQuery.addScalar("id", Hibernate.INTEGER).addScalar("descricao", Hibernate.STRING).list();

			FaturamentoGrupo faturamentoGrupo = null;

			for(Object object : retorno){

				Object fatGrup[] = (Object[]) object;

				faturamentoGrupo = new FaturamentoGrupo();
				faturamentoGrupo.setId((Integer) fatGrup[0]);
				faturamentoGrupo.setDescricao((String) fatGrup[1]);

				colecaoFaturamentoGrupo.add(faturamentoGrupo);

			}

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}
		return colecaoFaturamentoGrupo;
	}

	/**
	 * [UC0150] Retificar Conta
	 * 
	 * @author Anderson Italo
	 * @date 07/11/2013
	 */
	public MedicaoHistorico consultarMedicaoHistoricoAnterior(Integer idImovel, Integer anoMesFaturamentoAtual, Integer idMedicaoTipo)
					throws ErroRepositorioException{

		MedicaoHistorico retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{

			consulta.append(" select mh ");
			consulta.append(" from MedicaoHistorico mh ");
			consulta.append(" inner join mh.medicaoTipo mt ");

			if(idMedicaoTipo.equals(MedicaoTipo.LIGACAO_AGUA)){

				consulta.append(" inner join mh.ligacaoAgua la ");
				consulta.append(" where ");
				consulta.append(" la.id = :idImovel and ");
				consulta.append(" mh.anoMesReferencia = (select max(mh1.anoMesReferencia) from MedicaoHistorico mh1 where mh1.ligacaoAgua.id = la.id and ");
				consulta.append(" mt.id = mh1.medicaoTipo.id and mh1.anoMesReferencia < :anoMes) and ");
			}else{

				consulta.append(" inner join mh.imovel im ");
				consulta.append(" where ");
				consulta.append(" im.id = :idImovel and ");
				consulta.append(" mh.anoMesReferencia = (select max(mh1.anoMesReferencia) from MedicaoHistorico mh1 where mh1.imovel.id = im.id and ");
				consulta.append(" mt.id = mh1.medicaoTipo.id and mh1.anoMesReferencia < :anoMes) and ");
			}

			consulta.append(" mt.id = :idMedicaoTipo and mh.anoMesReferencia < :anoMes ");

			retorno = (MedicaoHistorico) session.createQuery(consulta.toString()).setInteger("idImovel", idImovel.intValue())
							.setInteger("idMedicaoTipo", idMedicaoTipo.intValue())
							.setInteger("anoMes", anoMesFaturamentoAtual.intValue()).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [OC0791503] - Relatório Quadro de Hidrômetros
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarQuadroHidrometros(Date dataReferencia) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String consulta = "SELECT hicp.hicp_dshidrometrocapacidade AS capacidade, "
							+ "hidm.hidm_dshidrometrodiametro AS diametro, "
							+ "himc.himc_dshidrometromarca AS marca, "
							+ "imov.loca_id AS idLocalidade, "
							+ "loca.loca_nmlocalidade AS descricaoLocalidade, "
							+ "greg.greg_id AS idGerencia, "
							+ "greg.greg_nmregional AS descricaoGerencia, "
							+ "COUNT(*) AS quantidade "
							+ "FROM imovel imov "
							+ "LEFT JOIN localidade loca "
							+ "ON imov.loca_id=loca.loca_id "
							+ "LEFT JOIN gerencia_regional greg "
							+ "ON loca.greg_id=greg.greg_id "
							+ "LEFT JOIN ligacao_agua lagu "
							+ "ON imov.imov_id=lagu.lagu_id "
							+ "LEFT JOIN hidrometro_instalacao_hist hidi "
							+ "ON lagu.hidi_id=hidi.hidi_id "
							+ "LEFT JOIN hidrometro hidr "
							+ "ON hidi.hidr_id=hidr.hidr_id "
							+ "LEFT JOIN hidrometro_capacidade hicp "
							+ "ON hidr.hicp_id=hicp.hicp_id "
							+ "LEFT JOIN hidrometro_diametro hidm "
							+ "ON hidr.hidm_id=hidm.hidm_id "
							+ "LEFT JOIN hidrometro_marca himc "
							+ "ON hidr.hicm_id=himc.himc_id "
							+ "WHERE hidi.hidi_dtinstalacaohidrometro IS NOT NULL "
							+ "AND hidi.hidi_dtretiradahidrometro IS NULL "
							+ "AND hidi.hidi_dtinstalacaohidrometro <= :dataReferencia "
							+ "AND hicp.hicp_icuso=1 "
							+ "AND hidm.hidm_icuso=1 "
							+ "AND himc.himc_icuso=1 "
							+ "GROUP BY hicp.hicp_dshidrometrocapacidade, hidm.hidm_dshidrometrodiametro, himc.himc_dshidrometromarca, imov.loca_id, loca.loca_nmlocalidade, greg.greg_id, greg.greg_nmregional "
							+ "ORDER BY imov.loca_id";

			retorno = session.createSQLQuery(consulta).addScalar("capacidade", Hibernate.STRING).addScalar("diametro", Hibernate.STRING)
							.addScalar("marca", Hibernate.STRING).addScalar("idLocalidade", Hibernate.INTEGER)
							.addScalar("descricaoLocalidade", Hibernate.STRING).addScalar("idGerencia", Hibernate.INTEGER)
							.addScalar("descricaoGerencia", Hibernate.STRING).addScalar("quantidade", Hibernate.INTEGER)
							.setDate("dataReferencia", dataReferencia).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [OC0791503] - Count Relatório Quadro de Hidrômetros
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Integer pesquisarQuadroHidrometrosCount(Date dataReferencia) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String consulta = "SELECT COUNT(*) as quantidade " + "FROM imovel imov " + "LEFT JOIN ligacao_agua lagu "
							+ "ON imov.imov_id=lagu.lagu_id " + "LEFT JOIN hidrometro_instalacao_hist hidi "
							+ "ON lagu.hidi_id=hidi.hidi_id " + "LEFT JOIN hidrometro hidr " + "ON hidi.hidr_id=hidr.hidr_id "
							+ "LEFT JOIN hidrometro_capacidade hicp " + "ON hidr.hicp_id=hicp.hicp_id "
							+ "LEFT JOIN hidrometro_diametro hidm " + "ON hidr.hidm_id=hidm.hidm_id " + "LEFT JOIN hidrometro_marca himc "
							+ "ON hidr.hicm_id=himc.himc_id " + "WHERE hidi.hidi_dtinstalacaohidrometro IS NOT NULL "
							+ "AND hidi.hidi_dtretiradahidrometro IS NULL " + "AND hidi.hidi_dtinstalacaohidrometro<= :dataReferencia "
							+ "AND hicp.hicp_icuso=1 " + "AND hidm.hidm_icuso=1 " + "AND himc.himc_icuso=1";

			retorno = (Integer) session.createSQLQuery(consulta).addScalar("quantidade", Hibernate.INTEGER)
							.setDate("dataReferencia", dataReferencia).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [OC0791503] - Relatório Quadro de Hidrômetros Ano Instalação
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarQuadroHidrometrosAnoInstalacao() throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String consulta = "SELECT himc.himc_id AS idMarca, "
							+ "himc.himc_dshidrometromarca AS marca, "
							+ "imov.loca_id AS idLocalidade, "
							+ "loca.loca_nmlocalidade AS descricaoLocalidade, "
							+ "to_number(to_char(hidi.hidi_dtinstalacaohidrometro, 'yyyy')) as ano, "
							+ "count(*) AS quantidade "
							+ "FROM imovel imov "
							+ "LEFT JOIN localidade loca "
							+ "ON imov.loca_id=loca.loca_id "
							+ "LEFT JOIN ligacao_agua lagu "
							+ "ON imov.imov_id=lagu.lagu_id "
							+ "LEFT JOIN hidrometro_instalacao_hist hidi "
							+ "ON lagu.hidi_id=hidi.hidi_id "
							+ "LEFT JOIN hidrometro hidr "
							+ "ON hidi.hidr_id=hidr.hidr_id "
							+ "LEFT JOIN hidrometro_capacidade hicp "
							+ "ON hidr.hicp_id=hicp.hicp_id "
							+ "LEFT JOIN hidrometro_diametro hidm "
							+ "ON hidr.hidm_id=hidm.hidm_id "
							+ "LEFT JOIN hidrometro_marca himc "
							+ "ON hidr.hicm_id=himc.himc_id "
							+ "WHERE hidi.hidi_dtinstalacaohidrometro IS NOT NULL "
							+ "AND hidi.hidi_dtretiradahidrometro     IS NULL "
							+ "AND hicp.hicp_icuso=1 "
							+ "AND hidm.hidm_icuso=1 "
							+ "AND himc.himc_icuso=1 "
							+ "GROUP BY himc.himc_id, himc.himc_dshidrometromarca, imov.loca_id, loca.loca_nmlocalidade, to_number(to_char(hidi.hidi_dtinstalacaohidrometro, 'yyyy')) "
							+ "ORDER BY imov.loca_id asc, ano desc";

			retorno = session.createSQLQuery(consulta).addScalar("idMarca", Hibernate.INTEGER).addScalar("marca", Hibernate.STRING)
							.addScalar("idLocalidade", Hibernate.INTEGER).addScalar("descricaoLocalidade", Hibernate.STRING)
							.addScalar("ano", Hibernate.INTEGER).addScalar("quantidade", Hibernate.INTEGER).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [OC0791503] - Count Relatório Quadro de Hidrômetros Ano Instalação
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Integer pesquisarQuadroHidrometrosAnoInstalacaoCount() throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String consulta = "SELECT count(*) AS quantidade " + "FROM imovel imov " + "LEFT JOIN localidade loca "
							+ "ON imov.loca_id=loca.loca_id " + "LEFT JOIN ligacao_agua lagu " + "ON imov.imov_id=lagu.lagu_id "
							+ "LEFT JOIN hidrometro_instalacao_hist hidi "
							+ "ON lagu.hidi_id=hidi.hidi_id " + "LEFT JOIN hidrometro hidr " + "ON hidi.hidr_id=hidr.hidr_id "
							+ "LEFT JOIN hidrometro_capacidade hicp " + "ON hidr.hicp_id=hicp.hicp_id "
							+ "LEFT JOIN hidrometro_diametro hidm " + "ON hidr.hidm_id=hidm.hidm_id " + "LEFT JOIN hidrometro_marca himc "
							+ "ON hidr.hicm_id=himc.himc_id " + "WHERE hidi.hidi_dtinstalacaohidrometro IS NOT NULL "
							+ "AND hidi.hidi_dtretiradahidrometro IS NULL " + "AND hicp.hicp_icuso=1 " + "AND hidm.hidm_icuso=1 "
							+ "AND himc.himc_icuso=1";

			retorno = (Integer) session.createSQLQuery(consulta).addScalar("quantidade", Hibernate.INTEGER).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [OC0791503] - Count Relatório Quadro de Hidrômetros Ano Instalação
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Integer pesquisarQuadroHidrometrosSituacaoCount(Date dataInicial, Date dataFinal) throws ErroRepositorioException{

		Integer retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String consulta = "SELECT COUNT(*) AS quantidade " + "FROM medicao_historico mdhi " + "LEFT JOIN imovel imov "
							+ "ON mdhi.lagu_id=imov.imov_id " + "LEFT JOIN localidade loca " + "ON imov.loca_id=loca.loca_id "
							+ "LEFT JOIN gerencia_regional greg " + "ON loca.greg_id=greg.greg_id " + "LEFT JOIN ligacao_agua lagu "
							+ "ON imov.imov_id=lagu.lagu_id " + "LEFT JOIN hidrometro_instalacao_hist hidi "
							+ "ON lagu.hidi_id=hidi.hidi_id " + "LEFT JOIN hidrometro hidr " + "ON hidi.hidr_id=hidr.hidr_id "
							+ "LEFT JOIN hidrometro_capacidade hicp " + "ON hidr.hicp_id=hicp.hicp_id "
							+ "LEFT JOIN hidrometro_diametro hidm " + "ON hidr.hidm_id=hidm.hidm_id " + "LEFT JOIN hidrometro_marca himc "
							+ "ON hidr.hicm_id=himc.himc_id " + "LEFT JOIN leitura_anormalidade ltan "
							+ "ON mdhi.ltan_idleituraanormalidadefatu=ltan.ltan_id "
							+ "WHERE hidi.hidi_dtinstalacaohidrometro IS NOT NULL " + "AND hidi.hidi_dtretiradahidrometro IS NULL "
							+ "AND mdhi.mdhi_dtleituraatualfaturamento BETWEEN :dataInicial AND :dataFinal " + "AND hicp.hicp_icuso=1 "
							+ "AND hidm.hidm_icuso=1 " + "AND himc.himc_icuso=1 ";

			retorno = (Integer) session.createSQLQuery(consulta).addScalar("quantidade", Hibernate.INTEGER)
							.setDate("dataInicial", dataInicial).setDate("dataFinal", dataFinal).uniqueResult();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [OC0791503] - Relatório Quadro de Hidrômetros Situação
	 * 
	 * @author Ado Rocha
	 * @date 03/12/2013
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarQuadroHidrometrosSituacao(Date dataInicial, Date dataFinal) throws ErroRepositorioException{

		Collection retorno = null;

		Session session = HibernateUtil.getSession();

		try{
			String consulta = "SELECT hicp.hicp_dshidrometrocapacidade AS capacidade, "
							+ "hidm.hidm_dshidrometrodiametro AS diametro, "
							+ "himc.himc_dshidrometromarca AS marca, "
							+ "imov.loca_id AS idLocalidade, "
							+ "loca.loca_nmlocalidade AS descricaoLocalidade, "
							+ "greg.greg_id AS idGerencia, "
							+ "greg.greg_nmregional AS descricaoGerencia, "
							+ "COUNT(*) AS quantidade, "
							+ "mdhi.ltan_idleituraanormalidadefatu AS idLeituraAnormalidade, "
							+ "ltan.ltan_dsleituraanormalidade AS descricaoLeituraAnormalidade "
							+ "FROM medicao_historico mdhi "
							+ "LEFT JOIN imovel imov "
							+ "ON mdhi.lagu_id=imov.imov_id "
							+ "LEFT JOIN localidade loca "
							+ "ON imov.loca_id=loca.loca_id "
							+ "LEFT JOIN gerencia_regional greg "
							+ "ON loca.greg_id=greg.greg_id "
							+ "LEFT JOIN ligacao_agua lagu "
							+ "ON imov.imov_id=lagu.lagu_id "
							+ "LEFT JOIN hidrometro_instalacao_hist hidi "
							+ "ON lagu.hidi_id=hidi.hidi_id "
							+ "LEFT JOIN hidrometro hidr "
							+ "ON hidi.hidr_id=hidr.hidr_id "
							+ "LEFT JOIN hidrometro_capacidade hicp "
							+ "ON hidr.hicp_id=hicp.hicp_id "
							+ "LEFT JOIN hidrometro_diametro hidm "
							+ "ON hidr.hidm_id=hidm.hidm_id "
							+ "LEFT JOIN hidrometro_marca himc "
							+ "ON hidr.hicm_id=himc.himc_id "
							+ "LEFT JOIN leitura_anormalidade ltan "
							+ "ON mdhi.ltan_idleituraanormalidadefatu=ltan.ltan_id "
							+ "WHERE hidi.hidi_dtinstalacaohidrometro IS NOT NULL "
							+ "AND hidi.hidi_dtretiradahidrometro IS NULL "
							+ "AND mdhi.mdhi_dtleituraatualfaturamento BETWEEN :dataInicial AND :dataFinal "
							+ "AND hicp.hicp_icuso=1 "
							+ "AND hidm.hidm_icuso=1 "
							+ "AND himc.himc_icuso=1 "
							+ "GROUP BY hicp.hicp_dshidrometrocapacidade, hidm.hidm_dshidrometrodiametro, himc.himc_dshidrometromarca, imov.loca_id, loca.loca_nmlocalidade, greg.greg_id, greg.greg_nmregional, mdhi.ltan_idleituraanormalidadefatu, ltan.ltan_dsleituraanormalidade, "
							+ "hicp.hicp_dshidrometrocapacidade, hidm.hidm_dshidrometrodiametro, himc.himc_dshidrometromarca, imov.loca_id, loca.loca_nmlocalidade, greg.greg_id, greg.greg_nmregional, mdhi.ltan_idleituraanormalidadefatu, ltan.ltan_dsleituraanormalidade "
							+ "ORDER BY greg.greg_id, imov.loca_id, mdhi.ltan_idleituraanormalidadefatu";

			retorno = session.createSQLQuery(consulta).addScalar("capacidade", Hibernate.STRING).addScalar("diametro", Hibernate.STRING)
							.addScalar("marca", Hibernate.STRING).addScalar("idLocalidade", Hibernate.INTEGER)
							.addScalar("descricaoLocalidade", Hibernate.STRING).addScalar("idGerencia", Hibernate.INTEGER)
							.addScalar("descricaoGerencia", Hibernate.STRING).addScalar("quantidade", Hibernate.INTEGER)
							.addScalar("idLeituraAnormalidade", Hibernate.INTEGER)
							.addScalar("descricaoLeituraAnormalidade", Hibernate.STRING).setDate("dataInicial", dataInicial)
							.setDate("dataFinal", dataFinal).list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}
	/**
	 * [UC0113] Faturar Grupo de Faturamento
	 * [SB0002] - Determinar Valores para Faturamento de Água e/ou Esgoto
	 * 
	 * @author Anderson Italo
	 * @date 05/01/2014
	 */
	public Object[] obterConsumoHistoricoAnterior(Integer idImovel, Integer anoMesFaturamentoAtual, Integer idLigacaoTipo)
					throws ErroRepositorioException{

		Object[] retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{

			consulta.append(" select ch.cshi_id as idConsumoHistorico, "); // 0
			consulta.append(" ch.cshi_nnconsumofaturadomes as numeroConsumoFaturado, "); // 1
			consulta.append(" ch.cshi_nnconsumomedio as numeroConsumoMedio, "); // 2
			consulta.append(" ch.cshi_nnconsumominimo as numeroConsumoMinimo "); // 3
			consulta.append(" from consumo_historico ch ");
			consulta.append(" where ");
			consulta.append(" ch.imov_id = :idImovel and ");
			consulta.append(" ch.cshi_amfaturamento = (select max(ch1.cshi_amfaturamento) from consumo_historico ch1 where ch1.imov_id = ch.imov_id and ");
			consulta.append(" ch.lgti_id = ch1.lgti_id and ch1.cshi_amfaturamento < :anoMes) and ");
			consulta.append(" ch.lgti_id = :idLigacaoTipo and ch.cshi_amfaturamento < :anoMes ");

			retorno = (Object[]) session.createSQLQuery(consulta.toString()).addScalar("idConsumoHistorico", Hibernate.INTEGER)
							.addScalar("numeroConsumoFaturado", Hibernate.INTEGER).addScalar("numeroConsumoMedio", Hibernate.INTEGER)
							.addScalar("numeroConsumoMinimo", Hibernate.INTEGER).setInteger("idImovel", idImovel.intValue())
							.setInteger("idLigacaoTipo", idLigacaoTipo.intValue()).setInteger("anoMes", anoMesFaturamentoAtual.intValue())
							.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * Pesquisar consumo e leitura anormalidade por consumo histórico
	 * 
	 * @author Hebert Falcão
	 * @date 26/01/2014
	 */
	public Object[] pesquisarConsumoELeituraAnormalidadePorConsumoHistorico(Integer idImovel, int anoMesReferencia, Integer idLigacaoTipo)
					throws ErroRepositorioException{

		Object[] retorno = null;

		Session session = HibernateUtil.getSession();
		String consulta;

		try{
			consulta = "SELECT ch.consumoAnormalidade.id, mh.leituraAnormalidadeFaturamento.id "
							+ " FROM Imovel im, LigacaoAgua la, MedicaoHistorico  mh, ConsumoHistorico ch" + " WHERE im.id = :imovelId"
							+ " AND ch.ligacaoTipo = :ligacaoTipoId" + " AND im.id = la.id" + " AND la.id = mh.ligacaoAgua.id"
							+ " AND im.id = ch.imovel.id" + " AND ch.referenciaFaturamento = :anoMesReferencia"
							+ " AND mh.anoMesReferencia = ch.referenciaFaturamento";

			retorno = (Object[]) session.createQuery(consulta).setInteger("imovelId", idImovel).setInteger("ligacaoTipoId", idLigacaoTipo)
							.setInteger("anoMesReferencia", anoMesReferencia).setMaxResults(1).uniqueResult();

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
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> obterImovelParaAjusteContaComValorAMenor(Integer referencia, String idsGrupos)
					throws ErroRepositorioException{

		Collection<Object[]> retorno = null;
		Session session = HibernateUtil.getSession();
		StringBuffer consulta = new StringBuffer("");

		try{

			consulta.append(" select ");
			consulta.append("  (select cr.crrz_vlcredito ");
			consulta.append("  from credito_realizado cr ");
			consulta.append("  where cr.cnta_id = cc.cnta_id ");
			consulta.append("  and cr.crti_id = " + CreditoTipo.DESCONTO_ACRESCIMOS_IMPONTUALIDADE.toString());
			consulta.append("  )          as vlcredito, ");
			consulta.append("  cc.imov_id as idimovel, ");
			consulta.append("  cc.cnta_id as idconta ");
			consulta.append(" from conta cc ");
			consulta.append(" inner join movimento_roteiro_empresa mrem ");
			consulta.append(" on ( mrem.imov_id         = cc.imov_id ");
			consulta.append(" and mrem.mrem_ammovimento = cc.cnta_amreferenciaconta) ");
			consulta.append(" where exists ");
			consulta.append("  (select 1 ");
			consulta.append("  from debito_cobrado db ");
			consulta.append("  where db.cnta_id = cc.cnta_id ");
			consulta.append("  and db.dbtp_id = " + DebitoTipo.PARCELAMENTO_ACRESCIMOS_IMPONTUALIDADE.toString());
			consulta.append("  and db.parc_id  is not null ");
			consulta.append("  ) ");
			consulta.append(" and exists ");
			consulta.append("  (select 1 ");
			consulta.append("  from credito_realizado ca ");
			consulta.append("  where ca.cnta_id = cc.cnta_id ");
			consulta.append("  and ca.crti_id  = " + CreditoTipo.DESCONTO_ACRESCIMOS_IMPONTUALIDADE.toString());
			consulta.append("  ) ");
			consulta.append(" and cc.cnta_amreferenciaconta =:referencia ");
			consulta.append(" and cc.dcst_idatual = " + DebitoCreditoSituacao.NORMAL.toString());
			consulta.append(" and mrem.mrem_icfase = " + MovimentoRoteiroEmpresa.FASE_PROCESSADO.toString());

			if(idsGrupos != null){

				consulta.append(" and mrem.ftgr_id in (" + idsGrupos + ") ");
				consulta.append(" order by cc.imov_id ");

				retorno = (Collection<Object[]>) session.createSQLQuery(consulta.toString()).addScalar("vlcredito", Hibernate.BIG_DECIMAL)
								.addScalar("idImovel", Hibernate.INTEGER).addScalar("idConta", Hibernate.INTEGER)
								.setInteger("referencia", referencia).list();

			}else{

				consulta.append(" order by cc.imov_id ");
				retorno = (Collection<Object[]>) session.createSQLQuery(consulta.toString()).addScalar("vlcredito", Hibernate.BIG_DECIMAL)
								.addScalar("idImovel", Hibernate.INTEGER).addScalar("idConta", Hibernate.INTEGER)
								.setInteger("referencia", referencia).list();
			}



		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{

			HibernateUtil.closeSession(session);
		}

		return retorno;

	}

}