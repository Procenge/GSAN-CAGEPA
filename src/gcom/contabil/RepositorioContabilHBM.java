/*
 * Copyright (C) 2007-2007 the GSAN ? Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Foundation, Inc., 59 Temple Place ? Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN ? Sistema Integrado de Gestão de Serviços de Saneamento
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

package gcom.contabil;

import gcom.arrecadacao.banco.ContaBancaria;
import gcom.arrecadacao.pagamento.GuiaPagamentoCategoria;
import gcom.arrecadacao.pagamento.GuiaPagamentoCategoriaHistorico;
import gcom.arrecadacao.pagamento.Pagamento;
import gcom.arrecadacao.pagamento.PagamentoHistorico;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.imovel.Categoria;
import gcom.contabil.bean.LancamentoContabilAnaliticoConsultaHelper;
import gcom.contabil.bean.LancamentoContabilSinteticoConsultaHelper;
import gcom.faturamento.GuiaPagamentoGeral;
import gcom.faturamento.ImpostoTipo;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaHistorico;
import gcom.faturamento.credito.CreditoRealizado;
import gcom.faturamento.credito.CreditoRealizadoHistorico;
import gcom.faturamento.debito.*;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.integracao.piramide.TabelaIntegracaoLancamentoContabil;
import gcom.integracao.piramide.bean.RetencaoHelper;
import gcom.util.*;
import gcom.util.parametrizacao.ExecutorParametro;
import gcom.util.parametrizacao.ParametroContabil;

import java.math.BigDecimal;
import java.util.*;

import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 * O repositório faz a comunicação com a base de dados através do hibernate.
 * 
 * @author Genival Barbosa
 */
public class RepositorioContabilHBM
				implements IRepositorioContabil, ExecutorParametro {

	private static IRepositorioContabil instancia;

	private static String CONTA_TABELA = "conta";

	private static String DEBITO_A_COBRAR_TABELA = "debito_a_cobrar";

	private static String PARCELAMENTO_TABELA = "PARCELAMENTO";

	private static String PAGAMENTO_TABELA = "pagamento";

	private static String CREDITO_A_REALIZAR_TABELA = "CREDITO_A_REALIZAR";

	private static String GUIA_PAGAMENTO = "GUIA_PAGAMENTO";

	/**
	 * Construtor da classe RepositorioCargaFuncionalidadesHBM
	 */
	private RepositorioContabilHBM() {

	}

	/**
	 * Retorna o valor de instancia
	 * 
	 * @return O valor de instancia
	 */
	public static IRepositorioContabil getInstancia(){

		if(instancia == null){
			instancia = new RepositorioContabilHBM();
		}

		return instancia;
	}

	/**
	 * Método responsável por consultar os LancamentoContabilSintetico apartir de um filtro antes de
	 * inserir
	 * 
	 * @param filtro
	 *            o filtro de pesquisa
	 * @return os LancamentoContabilSintetico que se enquadram nos parâmetros passados no filtro
	 * @throws ErroRepositorioException
	 */
	@SuppressWarnings("unchecked")
	public Collection consultarLancamentoContabilSinteticoInserir(Map<String, Object> filtro) throws ErroRepositorioException{

		Collection retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();
		try{

			StringBuffer consulta = montarConsultaLancamentoContabilSinteticoInserir(filtro);
			// executa o hql
			Query query = session.createQuery(consulta.toString());
			montarClausulasQuery(filtro, query);
			retorno = query.list();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}catch(ControladorException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	private void montarClausulasQuery(Map<String, Object> filtro, Query query){

		Date dataGeracao = (Date) filtro.get("dataGeracao");
		if(dataGeracao != null){
			dataGeracao = Util.zerarHoraMinutoSegundo(dataGeracao);
			query.setDate("dataGeracao", dataGeracao);
		}

		Date dataContabil = (Date) filtro.get("dataContabil");
		if(dataContabil != null){
			dataContabil = Util.zerarHoraMinutoSegundo(dataContabil);
			query.setDate("dataContabil", dataContabil);
		}

		Integer idUnidadeContabilAgrupamento = (Integer) filtro.get("idUnidadeContabilAgrupamento");
		if(idUnidadeContabilAgrupamento != null){
			query.setInteger("idUnidadeContabilAgrupamento", idUnidadeContabilAgrupamento);
		}

		Integer idEventoComercial = (Integer) filtro.get("idEventoComercial");
		if(idEventoComercial != null){
			query.setInteger("idEventoComercial", idEventoComercial);
		}

		Integer idCategoria = (Integer) filtro.get("idCategoria");
		if(idCategoria != null){
			query.setInteger("idCategoria", idCategoria);
		}

		Integer idLancamentoItemContabil = (Integer) filtro.get("idLancamentoItemContabil");
		if(idLancamentoItemContabil != null){
			query.setInteger("idLancamentoItemContabil", idLancamentoItemContabil);
		}

		Integer idImpostoTipo = (Integer) filtro.get("idImpostoTipo");
		if(idImpostoTipo != null){
			query.setInteger("idImpostoTipo", idImpostoTipo);
		}

		String cnpj = (String) filtro.get("cnpj");
		if(cnpj != null && !cnpj.trim().equals("")){
			query.setString("numeroCNPJ", Util.removerCaracteresEspeciais(cnpj));
		}

		Integer idContaBancaria = (Integer) filtro.get("idContaBancaria");
		if(idContaBancaria != null){
			query.setInteger("idContaBancaria", idContaBancaria);
		}
	}

	private StringBuffer montarConsultaLancamentoContabilSinteticoInserir(Map<String, Object> filtro) throws ControladorException{

		String parametroUnidadeContabilAgrupamento = ParametroContabil.UNIDADE_CONTABIL_AGRUPAMENTO.executar(this).toString();

		StringBuffer consulta = new StringBuffer();

		if(parametroUnidadeContabilAgrupamento.equals(UnidadeContabilAgrupamento.LOCALIDADE)){

			consulta.append(" select lancamentoContabilSintetico, localidade.descricao ");
			consulta.append(" from LancamentoContabilSintetico lancamentoContabilSintetico, Localidade localidade ");
			consulta.append(" where lancamentoContabilSintetico.idUnidadeContabilAgrupamento = localidade.id ");

		}else if(parametroUnidadeContabilAgrupamento.equals(UnidadeContabilAgrupamento.SETOR_COMERCIAL)){

			consulta.append(" select lancamentoContabilSintetico, setorComercial.descricao ");
			consulta.append(" from LancamentoContabilSintetico lancamentoContabilSintetico, SetorComercial setorComercial ");
			consulta.append(" where lancamentoContabilSintetico.idUnidadeContabilAgrupamento = setorComercial.id ");

		}else if(parametroUnidadeContabilAgrupamento.equals(UnidadeContabilAgrupamento.GERENCIA_REGIONAL)){

			consulta.append(" select lancamentoContabilSintetico, gerenciaRegional.descricao ");
			consulta.append(" from LancamentoContabilSintetico lancamentoContabilSintetico, GerenciaRegional gerenciaRegional ");
			consulta.append(" where lancamentoContabilSintetico.idUnidadeContabilAgrupamento = gerenciaRegional.id ");

		}else if(parametroUnidadeContabilAgrupamento.equals(UnidadeContabilAgrupamento.UNIDADE_NEGOCIO)){

			consulta.append(" select lancamentoContabilSintetico, unidadeNegocio.descricao ");
			consulta.append(" from LancamentoContabilSintetico lancamentoContabilSintetico, UnidadeNegocio unidadeNegocio ");
			consulta.append(" where lancamentoContabilSintetico.idUnidadeContabilAgrupamento = unidadeNegocio.id ");
		}

		Date dataGeracao = (Date) filtro.get("dataGeracao");
		if(dataGeracao != null){
			consulta.append(" AND lancamentoContabilSintetico.dataGeracao = :dataGeracao ");
		}

		Date dataContabil = (Date) filtro.get("dataContabil");
		if(dataContabil != null){
			consulta.append(" AND lancamentoContabilSintetico.dataContabil = :dataContabil ");
		}

		Integer idUnidadeContabilAgrupamento = (Integer) filtro.get("idUnidadeContabilAgrupamento");
		if(idUnidadeContabilAgrupamento != null){
			consulta.append(" AND lancamentoContabilSintetico.idUnidadeContabilAgrupamento = :idUnidadeContabilAgrupamento ");
		}

		Integer idEventoComercial = (Integer) filtro.get("idEventoComercial");
		if(idEventoComercial != null){
			consulta.append(" AND lancamentoContabilSintetico.eventoComercial.id = :idEventoComercial ");
		}

		Integer idCategoria = (Integer) filtro.get("idCategoria");
		if(idCategoria != null){
			consulta.append(" AND lancamentoContabilSintetico.categoria.id = :idCategoria ");
		}else{
			consulta.append(" AND lancamentoContabilSintetico.categoria is null ");
		}

		Integer idLancamentoItemContabil = (Integer) filtro.get("idLancamentoItemContabil");
		if(idLancamentoItemContabil != null){
			consulta.append(" AND lancamentoContabilSintetico.lancamentoItemContabil.id = :idLancamentoItemContabil ");
		}

		Integer idImpostoTipo = (Integer) filtro.get("idImpostoTipo");
		if(idImpostoTipo != null){
			consulta.append(" AND lancamentoContabilSintetico.impostoTipo.id = :idImpostoTipo ");
		}

		String cnpj = (String) filtro.get("cnpj");
		if(cnpj != null && !cnpj.trim().equals("")){
			consulta.append(" AND lancamentoContabilSintetico.lancamentoContabilSintetico.numeroCNPJ = :numeroCNPJ ");
		}

		Integer idContaBancaria = (Integer) filtro.get("idContaBancaria");
		if(idContaBancaria != null){
			consulta.append(" AND lancamentoContabilSintetico.contaBancaria.id = :idContaBancaria ");
		}

		return consulta;
	}

	/**
	 * Método responsável por obter o eventoComercial através do id
	 * 
	 * @param id
	 * @return eventoComercial
	 * @throws ErroRepositorioException
	 */
	@SuppressWarnings("unchecked")
	public EventoComercial obterEventoComercial(Integer id) throws ErroRepositorioException{

		EventoComercial retorno = null;

		Session session = HibernateUtil.getSession();
		try{
			Criteria criteria = session.createCriteria(EventoComercial.class);
			criteria.add(Restrictions.eq("id", id));

			retorno = (EventoComercial) criteria.uniqueResult();

		}catch(HibernateException e){
			// Levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// Fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	// /**
	// * Inseri um LancamentoContabilSintetico na base
	// *
	// * @param lancamentoContabilSintetico
	// * Descrição do parâmetro
	// * @exception ErroRepositorioException
	// * Descrição da exceção
	// */
	// public void inserirlancamentoContabilSintetico(LancamentoContabilSintetico
	// lancamentoContabilSintetico) throws ErroRepositorioException{
	//
	// Session session = HibernateUtil.getSession();
	// try{
	//
	// lancamentoContabilSintetico.setUltimaAlteracao(Calendar.getInstance().getTime());
	// lancamentoContabilSintetico.setIndicadorUso(Short.valueOf("1"));
	//
	// session.save(lancamentoContabilSintetico);
	//
	// Collection<LancamentoContabilAnalitico> listaLancamentoContabilAnalitico =
	// lancamentoContabilSintetico
	// .getListaLancamentoContabilAnalitico();
	//
	// for(LancamentoContabilAnalitico lcoa : listaLancamentoContabilAnalitico){
	//
	// lcoa.setUltimaAlteracao(Calendar.getInstance().getTime());
	// lcoa.setIndicadorUso(Short.valueOf("1"));
	// lcoa.setLancamentoContabilSintetico(lancamentoContabilSintetico);
	// session.save(lcoa);
	// }
	//
	// session.flush();
	//
	// }catch(HibernateException e){
	//
	// e.printStackTrace();
	//
	// throw new ErroRepositorioException("Erro no Hibernate");
	//
	// }finally{
	//
	// HibernateUtil.closeSession(session);
	//
	// // session.close();
	//
	// }
	//
	// }

	/**
	 * Inseri um LancamentoContabilAnalitico na base
	 * 
	 * @param lancamentoContabilSintetico
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public void inserirlancamentosContabeisAnaliticos(Collection<LancamentoContabilAnalitico> listaLancamentoContabilAnalitico,
					LancamentoContabilSintetico lancamentoContabilSintetico) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		try{

			for(LancamentoContabilAnalitico lcoa : listaLancamentoContabilAnalitico){
				lcoa.setUltimaAlteracao(Calendar.getInstance().getTime());
				lcoa.setIndicadorUso(Short.valueOf("1"));
				lcoa.setLancamentoContabilSintetico(lancamentoContabilSintetico);
				session.save(lcoa);
			}

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
	 * altera um lancamentoContabilSintetico na base
	 * 
	 * @param lancamentoContabilSintetico
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public void atualizarLancamentoContabilSintetico(LancamentoContabilSintetico lancamentoContabilSintetico)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		try{

			session.update(lancamentoContabilSintetico);

			Collection<LancamentoContabilAnalitico> listaLancamentoContabilAnalitico = lancamentoContabilSintetico
							.getListaLancamentoContabilAnalitico();

			for(LancamentoContabilAnalitico lcoa : listaLancamentoContabilAnalitico){

				lcoa.setUltimaAlteracao(Calendar.getInstance().getTime());
				lcoa.setIndicadorUso(Short.valueOf("1"));
				lcoa.setLancamentoContabilSintetico(lancamentoContabilSintetico);
				session.save(lcoa);
			}

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
	 * Método responsável por consultar os LancamentoContabilSintetico apartir de um filtro
	 * 
	 * @param filtro
	 *            o filtro de pesquisa
	 * @return os LancamentoContabilSintetico que se enquadram nos parâmetros passados no filtro
	 * @throws ErroRepositorioException
	 */
	@SuppressWarnings("unchecked")
	public Collection<LancamentoContabilSinteticoConsultaHelper> consultarLancamentoContabilSintetico(Map<String, Object> filtro)
					throws ErroRepositorioException{

		Collection<LancamentoContabilSinteticoConsultaHelper> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		try{

			StringBuffer consulta = montarConsultaLancamentoContabilSintetico(filtro);

			// executa o hql
			Query query = session.createQuery(consulta.toString());

			Date dataInicio = (Date) filtro.get("dataInicio");
			if(dataInicio != null){
				dataInicio = Util.zerarHoraMinutoSegundo(dataInicio);
				query.setDate("dataInicio", dataInicio);
			}

			Date dataFim = (Date) filtro.get("dataFim");
			if(dataFim != null){
				dataFim = Util.ajustarHoraMinutoSegundo(dataFim, 23, 59, 59, 59);
				query.setDate("dataFim", dataFim);
			}

			// Integer idUnidadeContabilAgrupamento = (Integer)
			// filtro.get("idUnidadeContabilAgrupamento");
			// if(idUnidadeContabilAgrupamento != null){
			// query.setInteger("idUnidadeContabilAgrupamento", idUnidadeContabilAgrupamento);
			// }
			Integer localidadeInicial = (Integer) filtro.get("localidadeInicial");
			Integer localidadeFinal = (Integer) filtro.get("localidadeFinal");
			if(Util.isNaoNuloBrancoZero(localidadeInicial) && Util.isNaoNuloBrancoZero(localidadeFinal)){
				query.setInteger("localidadeInicial", localidadeInicial);
				query.setInteger("localidadeFinal", localidadeFinal);
			}

			Integer contaContabilInicial = (Integer) filtro.get("contaContabilInicial");
			Integer contaContabilFinal = (Integer) filtro.get("contaContabilFinal");
			if((Util.isNaoNuloBrancoZero(contaContabilInicial) && !contaContabilInicial.equals(ConstantesSistema.NUMERO_NAO_INFORMADO))
							&& (Util.isNaoNuloBrancoZero(contaContabilFinal) && !contaContabilFinal
											.equals(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				query.setInteger("contaContabilInicial", contaContabilInicial);
				query.setInteger("contaContabilFinal", contaContabilFinal);
			}

			Short contaAuxiliarInicial = (Short) filtro.get("contaAuxiliarInicial");
			Short contaAuxiliarFinal = (Short) filtro.get("contaAuxiliarFinal");
			if((Util.isNaoNuloBrancoZero(contaAuxiliarInicial) && !contaAuxiliarInicial.equals(Short
							.valueOf(ConstantesSistema.VALOR_NAO_INFORMADO)))
							&& (Util.isNaoNuloBrancoZero(contaAuxiliarFinal) && !contaAuxiliarFinal.equals(Short
											.valueOf(ConstantesSistema.VALOR_NAO_INFORMADO)))){
				query.setInteger("contaAuxiliarInicial", contaAuxiliarInicial);
				query.setInteger("contaAuxiliarFinal", contaAuxiliarFinal);
			}

			Integer regionalInicial = (Integer) filtro.get("regionalInicial");
			Integer regionalFinal = (Integer) filtro.get("regionalFinal");
			if((Util.isNaoNuloBrancoZero(regionalInicial) && !regionalInicial.equals(ConstantesSistema.NUMERO_NAO_INFORMADO))
							&& (Util.isNaoNuloBrancoZero(regionalFinal) && !regionalFinal.equals(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				query.setInteger("regionalInicial", regionalInicial);
				query.setInteger("regionalFinal", regionalFinal);
			}

			Integer idEventoComercial = (Integer) filtro.get("idEventoComercial");
			if(idEventoComercial != null){
				query.setInteger("idEventoComercial", idEventoComercial);
			}

			Integer idCategoria = (Integer) filtro.get("idCategoria");
			if(idCategoria != null){
				query.setInteger("idCategoria", idCategoria);
			}

			Integer idLancamentoItemContabil = (Integer) filtro.get("idLancamentoItemContabil");
			if(idLancamentoItemContabil != null){
				query.setInteger("idLancamentoItemContabil", idLancamentoItemContabil);
			}

			Integer idImpostoTipo = (Integer) filtro.get("idImpostoTipo");
			if(idImpostoTipo != null){
				query.setInteger("idImpostoTipo", idImpostoTipo);
			}

			Integer idModulo = (Integer) filtro.get("idModulo");
			if(idModulo != null){
				query.setInteger("idModulo", idModulo);
			}

			query.setResultTransformer(Transformers.aliasToBean(LancamentoContabilSinteticoConsultaHelper.class));
			retorno = query.list();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);

		}

		return retorno;
	}

	private StringBuffer montarConsultaLancamentoContabilSintetico(Map<String, Object> filtro) throws ControladorException{

		String parametroUnidadeContabilAgrupamento = ParametroContabil.UNIDADE_CONTABIL_AGRUPAMENTO.executar(this).toString();

		StringBuffer consulta = new StringBuffer();
		consulta.append(" select lancamentoContabilSintetico.id as id, ")
						.append(" lancamentoContabilSintetico.dataContabil as dataContabil, ")
						.append(" eventoComercialLancamento.contaContabilCredito.numeroConta as contaCredito, ")
						.append(" eventoComercialLancamento.contaContabilDebito.numeroConta as contaDebito, ")
						.append(" lancamentoContabilSintetico.eventoComercial.modulo.descricaoModulo as descricaoModulo, ")
						.append(" lancamentoContabilSintetico.eventoComercial.modulo.descricaoAbreviado as descricaoAbrevModulo, ")
						.append(" lancamentoContabilSintetico.eventoComercial.descricao as descricaoEventoComercial, ")
						.append(" lancamentoContabilSintetico.categoria.descricao as descricaoCategoria, ")
						.append(" lancamentoContabilSintetico.categoria.descricaoAbreviada as descricaoAbrevCategoria, ")
						.append(" lancamentoItemContabil.descricao as lancamentoItemContabil, ")
						.append(" lancamentoItemContabil.descricaoAbreviada as abrevLancamentoItemContabil, ")
						.append(" impostoTipo.descricao as impostoTipo, ")
						.append(" impostoTipo.descricaoAbreviada as abrevImpostoTipo, ")
						.append(" lancamentoContabilSintetico.valor as valor, ");

		if(parametroUnidadeContabilAgrupamento.equals(UnidadeContabilAgrupamento.LOCALIDADE)){

			consulta.append(" localidade.descricao as descricaoUnidadeContabilAgrupamento ")
							.append(" from LancamentoContabilSintetico lancamentoContabilSintetico, ")
							.append(" EventoComercialLancamento eventoComercialLancamento, Localidade localidade, ArrecadadorContrato arrecadadorContrato ")
							.append(" LEFT JOIN lancamentoContabilSintetico.lancamentoItemContabil lancamentoItemContabil ")
							.append(" LEFT JOIN lancamentoContabilSintetico.impostoTipo impostoTipo ")
							.append(" LEFT JOIN lancamentoContabilSintetico.categoria categoria ")
							.append(" where lancamentoContabilSintetico.idUnidadeContabilAgrupamento = localidade.id ");

		}else if(parametroUnidadeContabilAgrupamento.equals(UnidadeContabilAgrupamento.SETOR_COMERCIAL)){

			consulta.append(" setorComercial.descricao as descricaoUnidadeContabilAgrupamento ").append(
							" from LancamentoContabilSintetico lancamentoContabilSintetico, ").append(
							" EventoComercialLancamento eventoComercialLancamento, SetorComercial setorComercial ").append(
							" LEFT JOIN lancamentoContabilSintetico.lancamentoItemContabil lancamentoItemContabil ").append(
							" LEFT JOIN lancamentoContabilSintetico.impostoTipo impostoTipo ").append(
							" LEFT JOIN lancamentoContabilSintetico.categoria categoria ").append(
							" where lancamentoContabilSintetico.idUnidadeContabilAgrupamento = setorComercial.id ");

		}else if(parametroUnidadeContabilAgrupamento.equals(UnidadeContabilAgrupamento.GERENCIA_REGIONAL)){

			consulta.append(" gerenciaRegional.descricao as descricaoUnidadeContabilAgrupamento ").append(
							" from LancamentoContabilSintetico lancamentoContabilSintetico, ").append(
							" EventoComercialLancamento eventoComercialLancamento, GerenciaRegional gerenciaRegional ").append(
							" LEFT JOIN lancamentoContabilSintetico.lancamentoItemContabil lancamentoItemContabil ").append(
							" LEFT JOIN lancamentoContabilSintetico.impostoTipo impostoTipo ").append(
							" LEFT JOIN lancamentoContabilSintetico.categoria categoria ").append(
							" where lancamentoContabilSintetico.idUnidadeContabilAgrupamento = gerenciaRegional.id ");

		}else if(parametroUnidadeContabilAgrupamento.equals(UnidadeContabilAgrupamento.UNIDADE_NEGOCIO)){

			consulta.append(" unidadeNegocio.descricao as descricaoUnidadeContabilAgrupamento ").append(
							" from LancamentoContabilSintetico lancamentoContabilSintetico, ").append(
							" EventoComercialLancamento eventoComercialLancamento, UnidadeNegocio unidadeNegocio ").append(
							" LEFT JOIN lancamentoContabilSintetico.lancamentoItemContabil lancamentoItemContabil ").append(
							" LEFT JOIN lancamentoContabilSintetico.impostoTipo impostoTipo ").append(
							" LEFT JOIN lancamentoContabilSintetico.categoria categoria ").append(
							" where lancamentoContabilSintetico.idUnidadeContabilAgrupamento = unidadeNegocio.id ");
		}

		consulta.append(" AND (eventoComercialLancamento.eventoComercial is null OR ")
						.append(" eventoComercialLancamento.eventoComercial.id = lancamentoContabilSintetico.eventoComercial.id ) ")

						.append(" AND (eventoComercialLancamento.categoria is null OR ")
						.append(" eventoComercialLancamento.categoria.id = lancamentoContabilSintetico.categoria.id ) ")

						.append(" AND (eventoComercialLancamento.lancamentoItemContabil is null OR ")
						.append(" eventoComercialLancamento.lancamentoItemContabil.id = lancamentoContabilSintetico.lancamentoItemContabil.id ) ")

						.append(" AND (eventoComercialLancamento.impostoTipo is null OR ")
						.append(" eventoComercialLancamento.impostoTipo.id = lancamentoContabilSintetico.lancamentoItemContabil.id ) ")

						.append(" AND (arrecadadorContrato.contaBancariaDepositoArrecadacao is null OR  ")
						.append(" arrecadadorContrato.contaBancariaDepositoArrecadacao.id = lancamentoContabilSintetico.contaBancaria.id ) ");

		Date dataInicio = (Date) filtro.get("dataInicio");
		if(dataInicio != null){
			consulta.append(" AND lancamentoContabilSintetico.dataContabil >= :dataInicio ");
		}

		Date dataFim = (Date) filtro.get("dataFim");
		if(dataFim != null){
			consulta.append(" AND lancamentoContabilSintetico.dataContabil <= :dataFim ");
		}

		// Integer idUnidadeContabilAgrupamento = (Integer)
		// filtro.get("idUnidadeContabilAgrupamento");
		// if(idUnidadeContabilAgrupamento != null){
		// consulta.append(" AND lancamentoContabilSintetico.idUnidadeContabilAgrupamento = :idUnidadeContabilAgrupamento ");
		// }
		Integer localidadeInicial = (Integer) filtro.get("localidadeInicial");
		Integer localidadeFinal = (Integer) filtro.get("localidadeFinal");
		if(Util.isNaoNuloBrancoZero(localidadeInicial) && Util.isNaoNuloBrancoZero(localidadeFinal)){
			consulta.append(" AND lancamentoContabilSintetico.idUnidadeContabilAgrupamento between :localidadeInicial and :localidadeFinal ");
		}

		Integer contaContabilInicial = (Integer) filtro.get("contaContabilInicial");
		Integer contaContabilFinal = (Integer) filtro.get("contaContabilFinal");
		if((Util.isNaoNuloBrancoZero(contaContabilInicial) && !contaContabilInicial.equals(ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (Util.isNaoNuloBrancoZero(contaContabilFinal) && !contaContabilFinal
										.equals(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			consulta.append(" AND (eventoComercialLancamento.contaContabilCredito.id between :contaContabilInicial and :contaContabilFinal ");
			consulta.append(" 		OR eventoComercialLancamento.contaContabilDebito.id between :contaContabilInicial and :contaContabilFinal) ");
		}

		Short contaAuxiliarInicial = (Short) filtro.get("contaAuxiliarInicial");
		Short contaAuxiliarFinal = (Short) filtro.get("contaAuxiliarFinal");
		if((Util.isNaoNuloBrancoZero(contaAuxiliarInicial) && !contaAuxiliarInicial.equals(Short
						.valueOf(ConstantesSistema.VALOR_NAO_INFORMADO)))
						&& (Util.isNaoNuloBrancoZero(contaAuxiliarFinal) && !contaAuxiliarFinal.equals(Short
										.valueOf(ConstantesSistema.VALOR_NAO_INFORMADO)))){
			consulta.append(" AND arrecadadorContrato.arrecadador.codigoAgente between :contaAuxiliarInicial and :contaAuxiliarFinal ");
		}

		Integer regionalInicial = (Integer) filtro.get("regionalInicial");
		Integer regionalFinal = (Integer) filtro.get("regionalFinal");
		if((Util.isNaoNuloBrancoZero(regionalInicial) && !regionalInicial.equals(ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (Util.isNaoNuloBrancoZero(regionalFinal) && !regionalFinal.equals(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			consulta.append(" AND localidade.gerenciaRegional.id between :regionalInicial and :regionalFinal ");
		}

		Integer idEventoComercial = (Integer) filtro.get("idEventoComercial");
		if(idEventoComercial != null){
			consulta.append(" AND lancamentoContabilSintetico.eventoComercial.id = :idEventoComercial ");
		}

		Integer idCategoria = (Integer) filtro.get("idCategoria");
		if(idCategoria != null){
			consulta.append(" AND lancamentoContabilSintetico.categoria.id = :idCategoria ");
		}

		Integer idLancamentoItemContabil = (Integer) filtro.get("idLancamentoItemContabil");
		if(idLancamentoItemContabil != null){
			consulta.append(" AND lancamentoContabilSintetico.lancamentoItemContabil.id = :idLancamentoItemContabil ");
		}

		Integer idImpostoTipo = (Integer) filtro.get("idImpostoTipo");
		if(idImpostoTipo != null){
			consulta.append(" AND lancamentoContabilSintetico.impostoTipo.id = :idImpostoTipo ");
		}

		Integer idModulo = (Integer) filtro.get("idModulo");
		if(idModulo != null){
			consulta.append(" AND lancamentoContabilSintetico.eventoComercial.modulo.id = :idModulo ");
		}

		consulta.append(" order by lancamentoContabilSintetico.dataContabil desc, ")
						.append(" lancamentoContabilSintetico.eventoComercial.modulo.descricaoModulo, ")
						.append(" lancamentoContabilSintetico.eventoComercial.descricao, ")
						.append(" lancamentoContabilSintetico.categoria.id, ")//
						.append(" lancamentoItemContabil.descricao, ")
						.append(" impostoTipo.descricao ");

		return consulta;
	}

	/**
	 * Método responsável por consultar e montar os LancamentoContabilAnaliticoConsultaHelper
	 * 
	 * @param lancamentoContabilSintetico
	 *            o filtro de pesquisa
	 * @return os LancamentoContabilAnaliticoConsultaHelper
	 * @throws ErroRepositorioException
	 */
	@SuppressWarnings("unchecked")
	public Collection<LancamentoContabilAnaliticoConsultaHelper> consultarLancamentoContabilAnaliticoPorSintetico(
					LancamentoContabilSintetico lancamentoContabilSintetico) throws ErroRepositorioException{

		Collection<LancamentoContabilAnaliticoConsultaHelper> retorno = null;

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		session.refresh(lancamentoContabilSintetico);
		Hibernate.initialize(lancamentoContabilSintetico);
		Hibernate.initialize(lancamentoContabilSintetico.getEventoComercial());

		try{

			StringBuffer consulta = new StringBuffer();
			consulta.append(" select lancamentoContabilSintetico.eventoComercial.tabelaReferencia.nomeTabela as descricaoObjeto, ").append(
							" lancamentoContabilAnalitico.codigoObjeto as complemento, lancamentoContabilAnalitico.valor as valor ");

			if(lancamentoContabilSintetico.getEventoComercial().getTabelaReferencia().getNomeTabela().equalsIgnoreCase(CONTA_TABELA)){
				consulta.append(" , (select conta.imovel.id from Conta conta ").append(
								" where conta.id = lancamentoContabilAnalitico.codigoObjeto)  as idImovel ").append(
								" , (select contaHistorico.imovel.id from ContaHistorico contaHistorico ").append(
								" where contaHistorico.id = lancamentoContabilAnalitico.codigoObjeto) as idImovelHistorico ");

			}else if(lancamentoContabilSintetico.getEventoComercial().getTabelaReferencia().getNomeTabela().equalsIgnoreCase(
							DEBITO_A_COBRAR_TABELA)){
				consulta.append(" , (select debitoACobrar.imovel.id from DebitoACobrar debitoACobrar ").append(
								" where debitoACobrar.id = lancamentoContabilAnalitico.codigoObjeto) as idImovel ").append(
								" , (select debitoACobrarHistorico.imovel.id from DebitoACobrarHistorico debitoACobrarHistorico ").append(
								" where debitoACobrarHistorico.id = lancamentoContabilAnalitico.codigoObjeto) as idImovelHistorico ");

			}else if(lancamentoContabilSintetico.getEventoComercial().getTabelaReferencia().getNomeTabela().equalsIgnoreCase(
							PARCELAMENTO_TABELA)){
				consulta.append(" , (select parcelamento.imovel.id from Parcelamento parcelamento where parcelamento.id = ").append(
								" lancamentoContabilAnalitico.codigoObjeto) as idImovel ");

			}else if(lancamentoContabilSintetico.getEventoComercial().getTabelaReferencia().getNomeTabela().equalsIgnoreCase(
							PAGAMENTO_TABELA)){
				consulta.append(" , (select pagamento.imovel.id from Pagamento pagamento where pagamento.id = ").append(
								" lancamentoContabilAnalitico.codigoObjeto) as idImovel ").append(
								" , (select pagamentoHistorico.imovel.id from PagamentoHistorico pagamentoHistorico ").append(
								" where pagamentoHistorico.id = lancamentoContabilAnalitico.codigoObjeto) as idImovelHistorico ");

			}else if(lancamentoContabilSintetico.getEventoComercial().getTabelaReferencia().getNomeTabela().equalsIgnoreCase(
							CREDITO_A_REALIZAR_TABELA)){
				consulta
								.append(
												" , (select creditoARealizar.imovel.id from CreditoARealizar creditoARealizar where creditoARealizar.id = ")
								.append(" lancamentoContabilAnalitico.codigoObjeto) as idImovel ")
								.append(
												" , (select creditoARealizarHistorico.imovel.id from CreditoARealizarHistorico creditoARealizarHistorico ")
								.append(
												" where creditoARealizarHistorico.id = lancamentoContabilAnalitico.codigoObjeto) as idImovelHistorico ");

			}else if(lancamentoContabilSintetico.getEventoComercial().getTabelaReferencia().getNomeTabela()
							.equalsIgnoreCase(GUIA_PAGAMENTO)){
				consulta.append(" , (select guiaPagamento.imovel.id from GuiaPagamento guiaPagamento where guiaPagamento.id = ").append(
								" lancamentoContabilAnalitico.codigoObjeto) as idImovel ").append(
								" , (select guiaPagamentoHistorico.imovel.id from GuiaPagamentoHistorico guiaPagamentoHistorico ").append(
								" where guiaPagamentoHistorico.id = lancamentoContabilAnalitico.codigoObjeto) as idImovelHistorico ");
			}

			consulta.append(" from LancamentoContabilAnalitico lancamentoContabilAnalitico ").append(
							" INNER JOIN lancamentoContabilAnalitico.lancamentoContabilSintetico lancamentoContabilSintetico ");

			consulta.append(" where lancamentoContabilSintetico.id = :idSintetico ");

			consulta.append(" order by lancamentoContabilAnalitico.ultimaAlteracao desc ");
			// executa o hql
			Query query = session.createQuery(consulta.toString()).setLong("idSintetico", lancamentoContabilSintetico.getId());

			query.setResultTransformer(Transformers.aliasToBean(LancamentoContabilAnaliticoConsultaHelper.class));
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
	 * Carregar atributos lazy da conta
	 * 
	 * @param conta
	 * @throws ErroRepositorioException
	 */
	@SuppressWarnings("unchecked")
	public void carregarAtributosConta(Conta conta){

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		session.refresh(conta);

		Hibernate.initialize(conta.getContaCategorias());
		Hibernate.initialize(conta.getDebitoCobrados());
		for(DebitoCobrado debitoCobrado : conta.getDebitoCobrados()){
			session.refresh(debitoCobrado);
			Hibernate.initialize(debitoCobrado.getDebitoCobradoCategorias());
		}
		Hibernate.initialize(conta.getCreditoRealizados());
		for(Object objeto : conta.getCreditoRealizados()){
			CreditoRealizado creditoRealizado = (CreditoRealizado) objeto;
			session.refresh(creditoRealizado);
			Hibernate.initialize(creditoRealizado.getCreditoRealizadoCategorias());
		}
		Hibernate.initialize(conta.getContaImpostosDeduzidos());

		HibernateUtil.closeSession(session);
	}

	/**
	 * Carregar atributos lazy da contaHistorico
	 * 
	 * @param conta
	 * @throws ErroRepositorioException
	 */
	@SuppressWarnings("unchecked")
	public void carregarAtributosContaHistorico(ContaHistorico conta){

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		session.refresh(conta);
		Hibernate.initialize(conta.getLocalidade());
		Hibernate.initialize(conta.getContaCategoriaHistoricos());
		Hibernate.initialize(conta.getDebitoCobradoHistoricos());
		for(DebitoCobradoHistorico debitoCobrado : conta.getDebitoCobradoHistoricos()){
			session.refresh(debitoCobrado);
			Hibernate.initialize(debitoCobrado.getDebitoCobradoCategoriasHistorico());
		}
		Hibernate.initialize(conta.getCreditoRealizadoHistoricos());
		for(Object objeto : conta.getCreditoRealizadoHistoricos()){
			CreditoRealizadoHistorico creditoRealizado = (CreditoRealizadoHistorico) objeto;
			session.refresh(creditoRealizado);
			Hibernate.initialize(creditoRealizado.getCreditoRealizadoCategoriasHistorico());
		}
		Hibernate.initialize(conta.getContaImpostosDeduzidosHistoricos());

		HibernateUtil.closeSession(session);
	}

	/**
	 * Carregar atributos lazy da GuiaPagamentoGeral
	 * 
	 * @param conta
	 * @throws ErroRepositorioException
	 */
	@SuppressWarnings("unchecked")
	public void carregarAtributosGuiaPagamentoGeral(GuiaPagamentoGeral guiaPagamentoGeral){

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		session.refresh(guiaPagamentoGeral);
		Hibernate.initialize(guiaPagamentoGeral);
		Hibernate.initialize(guiaPagamentoGeral.getGuiaPagamento());
		Hibernate.initialize(guiaPagamentoGeral.getGuiaPagamentoHistorico());

		if(guiaPagamentoGeral.getGuiaPagamento() != null){

			Hibernate.initialize(guiaPagamentoGeral.getGuiaPagamento().getGuiasPagamentoPrestacao());

			Hibernate.initialize(guiaPagamentoGeral.getGuiaPagamento().getGuiasPagamentoCategoria());
			for(Object objeto : guiaPagamentoGeral.getGuiaPagamento().getGuiasPagamentoCategoria()){
				GuiaPagamentoCategoria guiaPagamentoCategoria = (GuiaPagamentoCategoria) objeto;
				session.refresh(guiaPagamentoCategoria);
				Hibernate.initialize(guiaPagamentoCategoria.getCategoria());
			}
		}
		if(guiaPagamentoGeral.getGuiaPagamentoHistorico() != null){

			Hibernate.initialize(guiaPagamentoGeral.getGuiaPagamentoHistorico().getGuiasPagamentoPrestacaoHistorico());

			Hibernate.initialize(guiaPagamentoGeral.getGuiaPagamentoHistorico().getGuiaPagamentoCategoriaHistoricos());
			Hibernate.initialize(guiaPagamentoGeral.getGuiaPagamentoHistorico().getGuiasPagamentoCategoriaHistorico());
			for(Object objeto : guiaPagamentoGeral.getGuiaPagamentoHistorico().getGuiaPagamentoCategoriaHistoricos()){
				GuiaPagamentoCategoriaHistorico guiaPagamentoCategoria = (GuiaPagamentoCategoriaHistorico) objeto;
				session.refresh(guiaPagamentoCategoria);
				Hibernate.initialize(guiaPagamentoCategoria.getCategoria());
			}
		}

		HibernateUtil.closeSession(session);
	}

	/**
	 * Carregar atributos lazy da DebitoACobrarHistorico
	 * 
	 * @param conta
	 * @throws ErroRepositorioException
	 */
	@SuppressWarnings("unchecked")
	public void carregarAtributosDebitoACobrarHistorico(DebitoACobrarHistorico debitoACobrar){

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		session.refresh(debitoACobrar);
		// debitoACobrar.initializeLazy();
		Hibernate.initialize(debitoACobrar);
		Hibernate.initialize(debitoACobrar.getLocalidade());
		Hibernate.initialize(debitoACobrar.getDebitoTipo());
		Hibernate.initialize(debitoACobrar.getLancamentoItemContabil());

		Hibernate.initialize(debitoACobrar.getDebitoACobrarCategoriasHistorico());

		if(debitoACobrar.getDebitoACobrarCategoriasHistorico() != null){
			for(Object objeto : debitoACobrar.getDebitoACobrarCategoriasHistorico()){
				DebitoACobrarCategoriaHistorico debitoACobrarCategoria = (DebitoACobrarCategoriaHistorico) objeto;
				session.refresh(debitoACobrarCategoria);
				Hibernate.initialize(debitoACobrarCategoria.getCategoria());
			}
		}

		HibernateUtil.closeSession(session);
	}

	/**
	 * Carregar atributos lazy do DebitoACobrar
	 * 
	 * @param debitoACobrar
	 * @throws ErroRepositorioException
	 */
	@SuppressWarnings("unchecked")
	public void carregarAtributosDebitoACobrar(DebitoACobrar debitoACobrar){

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();

		session.refresh(debitoACobrar);
		// debitoACobrar.initializeLazy();
		Hibernate.initialize(debitoACobrar);
		Hibernate.initialize(debitoACobrar.getLocalidade());
		Hibernate.initialize(debitoACobrar.getDebitoTipo());
		Hibernate.initialize(debitoACobrar.getLancamentoItemContabil());

		Hibernate.initialize(debitoACobrar.getDebitoACobrarCategorias());

		// if(debitoACobrar.getDebitoACobrarCategoriasHistorico() != null){
		// for(Object objeto : debitoACobrar.getDebitoACobrarCategoriasHistorico()){
		// DebitoACobrarCategoriaHistorico debitoACobrarCategoria =
		// (DebitoACobrarCategoriaHistorico) objeto;
		// session.refresh(debitoACobrarCategoria);
		// Hibernate.initialize(debitoACobrarCategoria.getCategoria());
		// }
		// }

		HibernateUtil.closeSession(session);
	}

	public void carregarAtributosPagamento(Pagamento pagamento){

		Session session = HibernateUtil.getSession();

		session.refresh(pagamento);
		Hibernate.initialize(pagamento);
		Hibernate.initialize(pagamento.getAvisoBancario());
		Hibernate.initialize(pagamento.getAvisoBancario().getContaBancaria());
		Hibernate.initialize(pagamento.getLocalidade());

		HibernateUtil.closeSession(session);
	}

	public void carregarAtributosPagamentoHistorico(PagamentoHistorico pagamentoHistorico){

		Session session = HibernateUtil.getSession();

		session.refresh(pagamentoHistorico);
		Hibernate.initialize(pagamentoHistorico);
		Hibernate.initialize(pagamentoHistorico.getAvisoBancario());
		Hibernate.initialize(pagamentoHistorico.getAvisoBancario().getContaBancaria());
		Hibernate.initialize(pagamentoHistorico.getLocalidade());

		HibernateUtil.closeSession(session);
	}

	/**
	 * Retorna o registro mais recente de provisão devedores duvidosos
	 * 
	 * @date 26/06/2012
	 * @author Hugo Lima
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ProvisaoDevedoresDuvidosos obterProvisaoDevedoresDuvidososMaisRecente(Integer idConta) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		ProvisaoDevedoresDuvidosos retorno = null;
		String consulta = null;
		try{

			consulta = " SELECT pdd FROM ProvisaoDevedoresDuvidosos pdd INNER JOIN pdd.contaGeral c WHERE c.id = :idConta order by pdd.id desc";

			retorno = (ProvisaoDevedoresDuvidosos) session.createQuery(consulta).setInteger("idConta", idConta).setMaxResults(1)
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
	 * Retorna o total de eventos comerciais
	 * 
	 * @date 26/06/2012
	 * @author Hugo Lima
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer obterTotalEventoComercial() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();

		BigDecimal retorno = null;
		String consulta = null;
		try{

			consulta = " SELECT count(evco_id) FROM evento_comercial ";

			retorno = (BigDecimal) session.createSQLQuery(consulta).setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno.intValue();
	}

	/**
	 * [UC3065] Gerar Integração Contábil
	 * [SB0002] ? Obter data final de lançamento para a Integração Contábil
	 * 
	 * @return dataFinal
	 */
	public Date obterDataFinalLancamentoIntegracaoContabil() throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Date retorno = null;
		try{

			String consulta = " select MAX(LACS_DTCONTABIL) from LANCAMENTO_CONTABIL_SINTETICO where LACS_DTCONTABIL <= CURRENT_DATE ";
			retorno = (Date) session.createSQLQuery(consulta).setMaxResults(1).uniqueResult();

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
	 * Este caso de uso permite gerar os dados de integração com o Sistema Contábil da empresa.
	 * [UC3065] Gerar Integração Contábil
	 * Fluxo Principal:
	 * 
	 * @param dataInicialContabil
	 * @param dataFinalContabil
	 * @return Lista de lancamentos Contabeis Sintético
	 * @throws ErroRepositorioException
	 */
	public List<LancamentoContabilSintetico> pesquisaLancamentosContabeisSintetico(Date dataInicialContabil, Date dataFinalContabil)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		List<LancamentoContabilSintetico> retorno = null;
		StringBuilder consulta = new StringBuilder();

		try{

			consulta.append(" select ");
			consulta.append(" lancamentoContabilSintetico ");
			consulta.append(" from LancamentoContabilSintetico lancamentoContabilSintetico ");
			consulta.append(" left JOIN fetch lancamentoContabilSintetico.contaBancaria contaB ");
			consulta.append(" left JOIN fetch contaB.agencia agencia ");
			consulta.append(" left JOIN fetch agencia.banco bco ");
			consulta.append(" inner JOIN fetch lancamentoContabilSintetico.eventoComercial eventoComercial ");
			consulta.append(" where ");
			consulta.append(" lancamentoContabilSintetico.dataContabil between ");
			consulta.append(" :dataInicialContabil and :dataFinalContabil ");
			consulta.append(" and eventoComercial.indicadorUso = :indicadorUso ");

			retorno = session.createQuery(consulta.toString()).setDate("dataInicialContabil", dataInicialContabil)
							.setDate("dataFinalContabil", dataFinalContabil)
							.setShort("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO).list();

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
	 * Metodo temporário para ajuste contábil. Analista de negócio Luís Eduardo
	 * Ocorrência: OC1128813
	 * 
	 * @author Saulo Lima
	 * @since 20/08/2013
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public List<LancamentoContabilSintetico> pesquisaLancamentosContabeisSinteticoAjusteTemp(Date dataContabil)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		List<LancamentoContabilSintetico> retorno = null;
		StringBuilder consulta = new StringBuilder();

		try{

			consulta.append(" select ");
			consulta.append(" lancamentoContabilSintetico ");
			consulta.append(" from LancamentoContabilSintetico lancamentoContabilSintetico ");
			consulta.append(" left JOIN fetch lancamentoContabilSintetico.contaBancaria contaB ");
			consulta.append(" left JOIN fetch contaB.agencia agencia ");
			consulta.append(" left JOIN fetch agencia.banco bco ");
			consulta.append(" inner JOIN fetch lancamentoContabilSintetico.eventoComercial evco ");
			consulta.append(" where ");
			consulta.append(" lancamentoContabilSintetico.dataGeracao = :dataContabil");
			consulta.append(" and evco.indicadorUso = :indicadorUso ");

			retorno = session.createQuery(consulta.toString()).setDate("dataContabil", dataContabil)
							.setShort("indicadorUso", ConstantesSistema.INDICADOR_USO_ATIVO).list();

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
	 * [UC3065] Gerar Integração Contábil
	 * [SB0004] ? Gerar Dados para Integração Contábil
	 * 
	 * @return idGerenciaRegional
	 */
	public int obterIdGerenciaRegionalPorUnidadeContabilAgrupamento(int idUnidadeContabilAgrupamento) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		Integer retorno = null;
		StringBuilder builder = new StringBuilder();
		try{
			builder.append(" Select l.gerenciaRegional.id ");
			builder.append(" FROM Localidade l ");
			builder.append(" WHERE l.id =:idlancamentoSistetico ");

			retorno = (Integer) session.createQuery(builder.toString()).setInteger("idlancamentoSistetico", idUnidadeContabilAgrupamento)
							.setMaxResults(1).uniqueResult();

		}catch(HibernateException e){
			// levanta a exceção para a próxima camada
			e.printStackTrace();
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			// fecha a sessão
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/**
	 * [UC3065] Gerar Integração Contábil
	 * [SB0004] ? Gerar Dados para Integração Contábil
	 * Obter o 11- descricaoComplemento
	 * 
	 * @return descricaoComplemento
	 */
	public String obterCampoPorParametroEmEventoComercialLancamentoPorEventoComercial(Integer idEventoComercial, String campoDescricao)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String retorno = null;
		StringBuilder builder = new StringBuilder();
		try{
			builder.append(" SELECT ecl.");
			builder.append(campoDescricao);
			builder.append("  FROM EventoComercialLancamento ecl ");
			builder.append(" WHERE ecl.eventoComercial.id =:idEventoComercial");

			retorno = (String) session.createQuery(builder.toString()).setInteger("idEventoComercial", idEventoComercial).setMaxResults(1)
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
	 * [UC3065] Gerar Integração Contábil
	 * [SB0004] ? Gerar Dados para Integração Contábil
	 * Obter o 03- COD_CONTA_ORIGEM
	 * 
	 * @param codigoOperacao
	 *            dependendo da operação o consulta é pelo CNCT_ID_CREDITO ou CNCT_ID_DEBITO
	 * @param lancamentoContabilSintetico
	 * @return retorna o código da conta origem
	 * @throws ErroRepositorioException
	 */
	public String obterCodigoContaOrigem(String codigoOperacao, LancamentoContabilSintetico lancamentoContabilSintetico)
					throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		String retorno = null;
		StringBuilder builder = new StringBuilder();
		try{
			builder.append(" SELECT DISTINCT cc.CNCT_NNCONTA numeroConta ");
			builder.append(" FROM CONTA_CONTABIL cc, ");
			builder.append("  EVENTO_COMERCIAL_LANCAMENTO ecl ");
			builder.append("  WHERE cc.CNCT_ID =  ");
			builder.append(" ( ");
			builder.append("    SELECT  ");
			if(TabelaIntegracaoLancamentoContabil.CODIGO_OPERACAO_DEBITO.equals(codigoOperacao)){
				builder.append("    CNCT_ID_DEBITO  ");
			}else{
				builder.append("    CNCT_ID_CREDITO  ");
			}
			builder.append("    FROM EVENTO_COMERCIAL_LANCAMENTO ecl ");
			builder.append("     WHERE ecl.EVCO_ID =  " + lancamentoContabilSintetico.getEventoComercial().getId());

			if(lancamentoContabilSintetico.getCategoria() == null){
				builder.append("  AND ecl.CATG_ID is null ");
			}else{
				builder.append("  AND (ecl.CATG_ID = " + lancamentoContabilSintetico.getCategoria().getId() + " OR ecl.CATG_ID is null ) ");
			}

			if(lancamentoContabilSintetico.getLancamentoItemContabil() == null){
				builder.append("  AND ecl.LICT_ID is null ");
			}else{
				Integer idContabil = lancamentoContabilSintetico.getLancamentoItemContabil().getId();
				builder.append("  AND ( ecl.LICT_ID = " + idContabil + " OR ecl.LICT_ID is null ) ");
			}

			if(lancamentoContabilSintetico.getImpostoTipo() == null){
				builder.append("  AND ecl.IMTP_ID is null ");
			}else{
				Integer idImpostoTipo = lancamentoContabilSintetico.getImpostoTipo().getId();
				builder.append("  AND ( ecl.IMTP_ID = " + idImpostoTipo + " OR ecl.IMTP_ID is null ) ");
			}
			builder.append(" ) ");

			retorno = (String) session.createSQLQuery(builder.toString()).addScalar("numeroConta", Hibernate.STRING).uniqueResult();

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
	 * Este caso de uso realiza a geração dos dados de Integração relativos à retenção com o Sistema
	 * Financeiro da empresa através de tabelas de integração.
	 * [UC3066] Gerar Integração Retenção
	 * Fluxo Principal:
	 * 
	 * @param dataInicialPagamento
	 * @param dataFinalPagamento
	 * @return Lista de Retanções.
	 * @throws ErroRepositorioException
	 */
	public List<RetencaoHelper> pesquisaRelacaoRetencao(Date dataInicialPagamento, Date dataFinalPagamento) throws ErroRepositorioException{

		Session session = HibernateUtil.getSession();
		List<RetencaoHelper> retorno = null;
		StringBuilder consulta = new StringBuilder();

		try{

			consulta.append("SELECT ");
			consulta.append("	pagamentoHistorico.PGHI_DTPAGAMENTO as dataPagamento, ");
			consulta
.append("	SUM(contaHistorico.CNHI_VLAGUA + contaHistorico.CNHI_VLESGOTO + contaHistorico.CNHI_VLDEBITOS - contaHistorico.CNHI_VLCREDITOS) as valorConta, ");
			consulta.append("	localidade.GREG_ID as codigoRegional, ");
			consulta.append("	SUM(contaImpostoDedHistPIS.CIDH_VLIMPOSTO) as valorRetidoPIS, ");
			consulta.append("	SUM(contaImpostoDedHistCOFFINS.CIDH_VLIMPOSTO) as valorRetidoCOFFINS, ");
			consulta.append("	cliente.CLIE_NNCNPJ as cnpjCliente ");
			consulta.append("FROM PAGAMENTO_HISTORICO pagamentoHistorico ");
			consulta.append("INNER JOIN LOCALIDADE localidade ON localidade.LOCA_ID = pagamentoHistorico.LOCA_ID ");
			consulta.append("INNER JOIN CONTA_HISTORICO contaHistorico ON pagamentoHistorico.CNTA_ID = contaHistorico.CNTA_ID ");
			consulta.append("INNER JOIN CLIENTE_CONTA_HISTORICO clienteContaHistorico ON clienteContaHistorico.CNTA_ID = pagamentoHistorico.CNTA_ID ");
			consulta.append("  AND clienteContaHistorico.CRTP_ID = (SELECT MAX(cch.CRTP_ID) FROM CLIENTE_CONTA_HISTORICO cch INNER JOIN CLIENTE c ON cch.CLIE_ID = c.CLIE_ID ");
			consulta.append("  WHERE cch.CNTA_ID = pagamentoHistorico.CNTA_ID AND c.CLIE_NNCNPJ is not null) ");
			consulta.append("INNER JOIN CLIENTE cliente ON clienteContaHistorico.CLIE_ID = cliente.CLIE_ID ");
			consulta.append("INNER JOIN CLIENTE_TIPO clienteTipo ON clienteTipo.CLTP_ID = cliente.CLTP_ID ");
			consulta.append(" AND clienteTipo.EPOD_ID= " + EsferaPoder.FEDERAL);
			consulta.append("INNER JOIN CONTA_IMPOSTOS_DEDUZIDOS_HIST contaImpostoDedHistPIS ON contaImpostoDedHistPIS.CNTA_ID = pagamentoHistorico.CNTA_ID AND contaImpostoDedHistPIS.IMTP_ID = "
											+ ImpostoTipo.PIS_PASEP);
			consulta
.append(" INNER JOIN CONTA_IMPOSTOS_DEDUZIDOS_HIST contaImpostoDedHistCOFFINS ON contaImpostoDedHistCOFFINS.CNTA_ID = pagamentoHistorico.CNTA_ID AND contaImpostoDedHistCOFFINS.IMTP_ID = "
											+ ImpostoTipo.COFINS);
			consulta.append(" WHERE ");
			consulta.append(" pagamentoHistorico.PGHI_DTPAGAMENTO BETWEEN :dataInicialPagamento AND :dataFinalPagamento ");
			consulta.append("GROUP BY ");
			consulta.append("	pagamentoHistorico.PGHI_DTPAGAMENTO, cliente.CLIE_NNCNPJ, localidade.GREG_ID ");

			Query query = session.createSQLQuery(consulta.toString()).addScalar("dataPagamento", Hibernate.DATE) //
							.addScalar("valorConta", Hibernate.BIG_DECIMAL).addScalar("codigoRegional", Hibernate.INTEGER) //
							.addScalar("valorRetidoPIS", Hibernate.BIG_DECIMAL).addScalar("valorRetidoCOFFINS", Hibernate.BIG_DECIMAL) //
							.addScalar("cnpjCliente", Hibernate.STRING) //
							.setDate("dataInicialPagamento", dataInicialPagamento) //
							.setDate("dataFinalPagamento", dataFinalPagamento);

			query.setResultTransformer(Transformers.aliasToBean(RetencaoHelper.class));

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
	 * Método obterNumeroConta
	 * <p>
	 * Esse método implementa a regra para obtenção do Número da Conta corespondente ao critério de
	 * </p>
	 * RASTREIO: [OC750003][UC3065][SB0004][ITEM 5 e 7]
	 * 
	 * @param tipo
	 * @param eventoComercial
	 * @param categoria
	 * @param lancamentoItemContabil
	 * @param impostoTipo
	 * @return
	 * @author Marlos Ribeiro
	 * @since 14/09/2012
	 */
	public String obterNumeroConta(ObjetoContabil tipo, EventoComercial eventoComercial, Categoria categoria,
					LancamentoItemContabil lancamentoItemContabil, ImpostoTipo impostoTipo) throws ErroRepositorioException{

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT evt.");
		if(ObjetoContabil.DEBITO.equals(tipo)){
			buffer.append("contaContabilDebito");
		}else if(ObjetoContabil.CREDITO.equals(tipo)){
			buffer.append("contaContabilCredito");
		}else{
			throw new IllegalArgumentException("tipo não pode ser NULO. ou é CREDITO ou DEBITO.");
		}
		buffer.append(".numeroConta ");
		buffer.append(" FROM ");
		buffer.append(EventoComercialLancamento.class.getName());
		buffer.append(" evt ");
		buffer.append(" WHERE ");
		buffer.append(" evt.eventoComercial.id = :idEventoComercial ");

		if(categoria != null && categoria.getId() != null){
			buffer.append(" AND (evt.categoria.id = :idCategoria OR evt.categoria is null)");
		}else{
			buffer.append(" AND evt.categoria is null");
		}

		if(lancamentoItemContabil != null && lancamentoItemContabil.getId() != null){
			buffer.append(" AND (evt.lancamentoItemContabil.id = :idLancamentoItemContabil OR evt.lancamentoItemContabil is null)");
		}else{
			buffer.append(" AND evt.lancamentoItemContabil is null");
		}

		if(impostoTipo != null && impostoTipo.getId() != null){
			buffer.append(" AND (evt.impostoTipo.id = :idImpostoTipo OR evt.impostoTipo is null)");
		}else{
			buffer.append(" AND evt.impostoTipo is null");
		}

		Session session = HibernateUtil.getSession();
		Query query = session.createQuery(buffer.toString());
		query.setInteger("idEventoComercial", eventoComercial.getId());
		if(categoria != null && categoria.getId() != null){
			query.setInteger("idCategoria", categoria.getId());
		}
		if(lancamentoItemContabil != null && lancamentoItemContabil.getId() != null){
			query.setInteger("idLancamentoItemContabil", lancamentoItemContabil.getId());
		}
		if(impostoTipo != null && impostoTipo.getId() != null){
			query.setInteger("idImpostoTipo", impostoTipo.getId());
		}
		return (String) query.setMaxResults(1).uniqueResult();
	}

	/**
	 * Método obterCodigoDocumento
	 * <p>
	 * Esse método implementa a regra de obtenção do código do documento contabil.
	 * </p>
	 * RASTREIO: [OC750003][UC3065][SB0004][ITEM 13]
	 * 
	 * @param eventoComercial
	 * @return
	 * @author Marlos Ribeiro
	 * @since 14/09/2012
	 */
	public String obterDescricaoDocumento(EventoComercial eventoComercial) throws ErroRepositorioException{

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT evt.descricaoDocumento");
		buffer.append(" FROM ");
		buffer.append(EventoComercialLancamento.class.getName());
		buffer.append(" evt ");
		buffer.append(" WHERE ");
		buffer.append(" evt.eventoComercial.id = :idEventoComercial ");
		Session session = HibernateUtil.getSession();
		return (String) session.createQuery(buffer.toString()) //
						.setInteger("idEventoComercial", eventoComercial.getId())//
						.setMaxResults(1)//
						.uniqueResult();
	}

	/**
	 * {@inheritDoc}
	 */
	public int consultarQuantidadeLancamentoContabilSinteticoInserir(Map<String, Object> mapaParametros) throws ErroRepositorioException{

		// cria uma sessão com o hibernate
		Session session = HibernateUtil.getSession();
		int retorno;
		try{

			StringBuffer consulta = montarConsultaLancamentoContabilSinteticoInserir(mapaParametros);
			int init = consulta.indexOf("select ") + "select ".length();
			int fim = consulta.indexOf(" from");
			consulta.replace(init, fim, "count(*)");
			// executa o hql
			Query query = session.createQuery(consulta.toString());
			montarClausulasQuery(mapaParametros, query);
			retorno = ((Long) query.uniqueResult()).intValue();

		}catch(HibernateException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}catch(ControladorException e){
			throw new ErroRepositorioException(e, "Erro no Hibernate");
		}finally{
			HibernateUtil.closeSession(session);
		}

		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.contabil.IRepositorioContabil#obterEventoComercialLancamento(gcom.contabil.
	 * LancamentoContabilSintetico)
	 */
	public String obterNumeroContaAuiliar(ObjetoContabil objetoContabil,
					LancamentoContabilSintetico lancamentoContabilSintetico)
					throws ErroRepositorioException{

		String retorno = null;
		Session session = HibernateUtil.getSession();

		try{

			if(lancamentoContabilSintetico.getEventoComercial() != null){

				Criteria criteria = session.createCriteria(EventoComercialLancamento.class);

				if(objetoContabil.equals(ObjetoContabil.DEBITO)){

					criteria.createAlias("contaContabilDebito", "contaContabilDebito", Criteria.INNER_JOIN);
					criteria.setProjection(Projections.property("contaContabilDebito.numeroGrupoContaAuxiliar"));

				}else if(objetoContabil.equals(ObjetoContabil.CREDITO)){

					criteria.createAlias("contaContabilCredito", "contaContabilCredito", Criteria.INNER_JOIN);
					criteria.setProjection(Projections.property("contaContabilCredito.numeroGrupoContaAuxiliar"));

				}else{

					throw new IllegalArgumentException("tipo não pode ser NULO. ou é CREDITO ou DEBITO.");

				}

				criteria.add(Restrictions.eq("eventoComercial.id", lancamentoContabilSintetico.getEventoComercial().getId()));

				if(lancamentoContabilSintetico.getCategoria() != null){

					Criterion critId = Restrictions.eq("categoria.id", lancamentoContabilSintetico.getCategoria().getId());
					Criterion critNull = Restrictions.isNull("categoria");

					criteria.add(Restrictions.or(critId, critNull));

				}else{

					criteria.add(Restrictions.isNull("categoria"));

				}

				if(lancamentoContabilSintetico.getLancamentoItemContabil() != null){

					Criterion critId = Restrictions.eq("lancamentoItemContabil.id", lancamentoContabilSintetico.getLancamentoItemContabil()
									.getId());
					Criterion critNull = Restrictions.isNull("lancamentoItemContabil");

					criteria.add(Restrictions.or(critId, critNull));
					
				}else{

					criteria.add(Restrictions.isNull("lancamentoItemContabil"));

				}

				if(lancamentoContabilSintetico.getImpostoTipo() != null){

					Criterion critId = Restrictions.eq("impostoTipo.id", lancamentoContabilSintetico.getImpostoTipo().getId());
					Criterion critNull = Restrictions.isNull("impostoTipo");

					criteria.add(Restrictions.or(critId, critNull));

				}else{

					criteria.add(Restrictions.isNull("impostoTipo"));
					
				}
				
				retorno = (String) criteria.setMaxResults(1).uniqueResult();

			}

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * gcom.contabil.IRepositorioContabil#obterContaBancaria(gcom.contabil.LancamentoContabilSintetico
	 * )
	 */
	public ContaBancaria obterContaBancaria(LancamentoContabilSintetico lancamentoContabilSintetico) throws ErroRepositorioException{

		ContaBancaria retorno = null;
		Session session = HibernateUtil.getSession();

		try{

			if(lancamentoContabilSintetico.getContaBancaria() != null){

				Criteria criteria = session.createCriteria(ContaBancaria.class);
				criteria.createAlias("agencia", "agencia");
				criteria.createAlias("agencia.banco", "banco");
				criteria.add(Restrictions.idEq(lancamentoContabilSintetico.getContaBancaria().getId()));

				retorno = (ContaBancaria) criteria.list().iterator().next();

			}

		}catch(HibernateException e){

			throw new ErroRepositorioException(e, "Erro no Hibernate");

		}finally{

			HibernateUtil.closeSession(session);

		}

		return retorno;

	}

}
