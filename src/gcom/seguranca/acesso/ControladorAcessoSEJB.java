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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.seguranca.acesso;

import gcom.batch.FiltroFuncionalidadeIniciada;
import gcom.batch.FuncionalidadeIniciada;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.*;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.usuario.*;
import gcom.seguranca.transacao.FiltroTabelaColuna;
import gcom.seguranca.transacao.Tabela;
import gcom.seguranca.transacao.TabelaColuna;
import gcom.util.*;
import gcom.util.email.ErroEmailException;
import gcom.util.email.ServicosEmail;
import gcom.util.filtro.*;
import gcom.util.parametrizacao.ParametroGeral;
import gcom.util.parametrizacao.batch.ParametroBatch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * Defini��o da l�gica de neg�cio do Session Bean de ControladorCliente
 * 
 * @author S�vio Luiz
 * @created 25 de Abril de 2005
 */
/**
 * Descri��o da classe
 * 
 * @author Administrador
 * @date 04/07/2006
 */
public class ControladorAcessoSEJB
				implements SessionBean {

	private static final long serialVersionUID = 1L;

	SessionContext sessionContext;

	private IRepositorioAcesso repositorioAcesso;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @exception CreateException
	 *                Descri��o da exce��o
	 */
	public void ejbCreate() throws CreateException{

		repositorioAcesso = RepositorioAcessoHBM.getInstancia();

	}

	/**
	 * < <Descri��o do m�todo>>
	 */
	public void ejbRemove(){

	}

	/**
	 * < <Descri��o do m�todo>>
	 */
	public void ejbActivate(){

	}

	/**
	 * < <Descri��o do m�todo>>
	 */
	public void ejbPassivate(){

	}

	/**
	 * Seta o valor de sessionContext
	 * 
	 * @param sessionContext
	 *            O novo valor de sessionContext
	 */
	public void setSessionContext(SessionContext sessionContext){

		this.sessionContext = sessionContext;
	}

	/**
	 * M�todo que consulta todas as TabelaColunas que estejam ligadas a uma
	 * Operacao
	 * 
	 * @author Thiago Toscano
	 * @date 23/03/2006
	 * @return
	 * @throws ControladorException
	 */
	public Collection getTabelaColunaPertencenteOperacao() throws ControladorException{

		// cria a cole��o de retorno
		Collection retorno = null;
		// obt�m a sess�o
		Session session = HibernateUtil.getSession();

		try{

			FiltroTabelaColuna filtroTabelaColuna = new FiltroTabelaColuna();
			filtroTabelaColuna.adicionarCaminhoParaCarregamentoEntidade(FiltroTabelaColuna.TABELA);

			// pesquisa a cole��o de atividades e atribui a vari�vel "retorno"
			retorno = GeradorHQLCondicional.gerarCondicionalQuery(
							filtroTabelaColuna,
							"gcom.seguranca.transacao.TabelaColuna",
							"tabelaColuna",
							" select tabelaColuna "
											+ " from TabelaColuna as tabelaColuna "
											+ PersistenciaUtil.processaObjetosParaCarregamentoJoinFetch("tabelaColuna", filtroTabelaColuna
															.getColecaoCaminhosParaCarregamentoEntidades())
											+ " where tabelaColuna in (	select operacao.tabelaColuna "
											+ "							from Operacao as operacao " + "							where operacao.tabelaColuna is not null)" + "",
							session).list();

			// Carrega os objetos informados no filtro
			/*
			 * if (!filtroTabelaColuna
			 * .getColecaoCaminhosParaCarregamentoEntidades().isEmpty()) {
			 * PersistenciaUtil.processaObjetosParaCarregamento(
			 * filtroTabelaColuna
			 * .getColecaoCaminhosParaCarregamentoEntidades(), retorno); }
			 */
			// erro no hibernate
		}catch(ErroRepositorioException e){
			// levanta a exce��o para a pr�xima camada
			throw new ControladorException("Erro no Hibernate", e);
		}catch(HibernateException e){
			// levanta a exce��o para a pr�xima camada
			throw new ControladorException("Erro no Hibernate", e);
		}finally{
			// fecha a sess�o
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * M�todo que pesquisa todas as tabelas colunas que tem ligacao com operacao
	 * pela operacao tabela
	 * 
	 * @author thiago toscano
	 * @date 23/03/2006
	 * @param idOperacao
	 * @return
	 * @throws ControladorException
	 */
	public Collection getTabelaColunaDasOperacaoTabela(Integer idOperacao) throws ControladorException{

		// cria a cole��o de retorno
		Collection retorno = null;
		// obt�m a sess�o
		Session session = HibernateUtil.getSession();

		try{

			// FiltroTabelaColuna filtroTabelaColuna = new FiltroTabelaColuna();
			// filtroTabelaColuna.adicionarCaminhoParaCarregamentoEntidade(FiltroTabelaColuna.TABELA);
			// filtroOperacao.adicionarCaminhoParaCarregamentoEntidade(FiltroOperacao.TABELA_COLUNA);

			FiltroOperacaoTabela filtroOperacaoTabela = new FiltroOperacaoTabela();

			// pesquisa a cole��o de atividades e atribui a vari�vel "retorno"
			retorno = GeradorHQLCondicional.gerarCondicionalQuery(
							filtroOperacaoTabela,
							"gcom.seguranca.acesso.OperacaoTabela",
							"operacaoTabela",
							" select distinct operacaoTabela.tabela.tabelaColunas "
											+ " from OperacaoTabela as operacaoTabela "
											+ PersistenciaUtil.processaObjetosParaCarregamentoJoinFetch("operacaoTabela",
															filtroOperacaoTabela.getColecaoCaminhosParaCarregamentoEntidades())
											+ " inner join operacaoTabela.operacao as operacao "
											+ " inner join operacaoTabela.tabela as tabela " + " inner join tabela.tabelaColunas as tc "
											+ " where  operacaoTabela.operacao.id = " + idOperacao + "", session).list();

			// Carrega os objetos informados no filtro
			/*
			 * if (!filtroOperacaoTabela
			 * .getColecaoCaminhosParaCarregamentoEntidades().isEmpty()) {
			 * PersistenciaUtil.processaObjetosParaCarregamento(
			 * filtroOperacaoTabela
			 * .getColecaoCaminhosParaCarregamentoEntidades(), retorno); }
			 */
			// erro no hibernate
		}catch(ErroRepositorioException e){
			// levanta a exce��o para a pr�xima camada
			throw new ControladorException("Erro no Hibernate", e);
		}catch(HibernateException e){
			// levanta a exce��o para a pr�xima camada
			throw new ControladorException("Erro no Hibernate", e);
		}finally{
			// fecha a sess�o
			HibernateUtil.closeSession(session);
		}
		return retorno;
	}

	/**
	 * Retorna o valor de controladorUtil
	 * 
	 * @return O valor de controladorUtil
	 */
	private ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;

		// pega a inst�ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas �
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	/**
	 * Retorna a interface remota de ControladorParametro
	 * 
	 * @return A interface remota do controlador de par�metro
	 */
	private ControladorUsuarioLocal getControladorUsuario(){

		ControladorUsuarioLocalHome localHome = null;
		ControladorUsuarioLocal local = null;

		// pega a inst�ncia do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorUsuarioLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_USUARIO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas �
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * [UC0280] Inserir Funcionalidade
	 * Metodo que verifica os dados da tabela e inseri a funcionalidade
	 * 
	 * @author R�mulo Aur�lio
	 * @date 28/04/2006
	 * @param funcionalidade
	 * @throws ControladorException
	 */

	public Integer inserirFuncionalidade(Funcionalidade funcionalidade, Collection colecaoFuncionalidadeDependencia)
					throws ControladorException{

		// Verifica se todos os campos obrigatorios foram preenchidos

		if((funcionalidade.getDescricao() == null || funcionalidade.getDescricao().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (funcionalidade.getDescricaoAbreviada() == null || funcionalidade.getDescricaoAbreviada().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (funcionalidade.getCaminhoMenu() == null || funcionalidade.getCaminhoMenu().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (funcionalidade.getCaminhoMenu() == null || funcionalidade.getCaminhoMenu().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO))){
			throw new ControladorException("atencao.filtro.nenhum_parametro_informado");

		}

		// Verifica se o campo Descri��o foi preenchido

		if(funcionalidade.getDescricao() == null || funcionalidade.getDescricao().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, " Descri��o");
		}

		// Verifica se o campo DescricaoAbreviada foi preenchido

		if(funcionalidade.getDescricaoAbreviada() == null
						|| funcionalidade.getDescricaoAbreviada().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, "Descri��o Abreviada");
		}

		// Verifica se o campo CaminhoMenu foi preenchido

		if(funcionalidade.getCaminhoMenu() == null || funcionalidade.getCaminhoMenu().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, "Caminho Menu");
		}

		// Verifica se o campo CaminhoURL foi preenchido

		if(funcionalidade.getCaminhoUrl() == null || funcionalidade.getCaminhoUrl().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, "Caminho URL");
		}

		// Verifica se o campo IndicadorPontoEntrada foi preenchido

		if(funcionalidade.getIndicadorPontoEntrada() == null
						|| funcionalidade.getIndicadorPontoEntrada().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, " Ponto de Entrada");
		}

		// Verifica se o campo Modulo foi preenchido

		if(funcionalidade.getModulo() == null || funcionalidade.getModulo().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.naoinformado", null, "M�dulo");
		}

		FiltroFuncionalidade filtroFuncionalidade = new FiltroFuncionalidade();

		filtroFuncionalidade.adicionarParametro(new ParametroSimples(FiltroFuncionalidade.DESCRICAO, funcionalidade.getDescricao()));

		Collection colecaoFuncionalidade = getControladorUtil().pesquisar(filtroFuncionalidade, Funcionalidade.class.getName());

		if(colecaoFuncionalidade != null && !colecaoFuncionalidade.isEmpty()){
			throw new ControladorException("atencao.descricao_ja_existente", null, "" + funcionalidade.getDescricao() + "");
		}

		funcionalidade.setUltimaAlteracao(new Date());

		Integer idFuncionalidade = (Integer) getControladorUtil().inserir(funcionalidade);

		if(colecaoFuncionalidadeDependencia != null && !colecaoFuncionalidadeDependencia.isEmpty()){
			Iterator iterator = colecaoFuncionalidadeDependencia.iterator();

			while(iterator.hasNext()){

				FuncionalidadeDependencia funcionalidadeDependencia = new FuncionalidadeDependencia();
				Funcionalidade funcionalidadeInserir = (Funcionalidade) iterator.next();
				funcionalidadeDependencia.setFuncionalidade(funcionalidadeInserir);

				FuncionalidadeDependenciaPK funcionalidadeDependenciaPK = new FuncionalidadeDependenciaPK();
				funcionalidadeDependenciaPK.setFuncionalidadeDependenciaId(funcionalidadeDependencia.getFuncionalidade().getId());
				funcionalidadeDependenciaPK.setFuncionalidadeId(funcionalidade.getId());

				funcionalidadeDependencia.setComp_id(funcionalidadeDependenciaPK);

				this.getControladorUtil().inserir(funcionalidadeDependencia);

			}

		}

		return idFuncionalidade;

	}

	/**
	 * [UC0281] Manter Funcionalidade [SB0001] Atualizar Funcionalidade Metodo
	 * que atualiza a funcionalidade
	 * 
	 * @author R�mulo Aur�lio
	 * @date 17/05/2006
	 * @param funcionalidade
	 * @throws ControladorException
	 */

	public void atualizarFuncionalidade(Funcionalidade funcionalidade, Collection colecaoFuncionalidadeDependencia)
					throws ControladorException{

		// Verifica se todos os campos obrigatorios foram preenchidos

		if((funcionalidade.getDescricao() == null || funcionalidade.getDescricao().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (funcionalidade.getDescricaoAbreviada() == null || funcionalidade.getDescricaoAbreviada().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (funcionalidade.getCaminhoMenu() == null || funcionalidade.getCaminhoMenu().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (funcionalidade.getCaminhoMenu() == null || funcionalidade.getCaminhoMenu().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO))){
			throw new ControladorException("atencao.filtro.nenhum_parametro_informado");

		}

		// Verifica se o campo Descri��o foi preenchido

		if(funcionalidade.getDescricao() == null || funcionalidade.getDescricao().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, " Descri��o");
		}

		// Verifica se o campo DescricaoAbreviada foi preenchido

		if(funcionalidade.getDescricaoAbreviada() == null
						|| funcionalidade.getDescricaoAbreviada().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, "Descri��o Abreviada");
		}

		// Verifica se o campo CaminhoMenu foi preenchido

		if(funcionalidade.getCaminhoMenu() == null || funcionalidade.getCaminhoMenu().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, "Caminho Menu");
		}

		// Verifica se o campo CaminhoURL foi preenchido

		if(funcionalidade.getCaminhoUrl() == null || funcionalidade.getCaminhoUrl().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, "Caminho URL");
		}

		// Verifica se o campo IndicadorPontoEntrada foi preenchido

		if(funcionalidade.getIndicadorPontoEntrada() == null
						|| funcionalidade.getIndicadorPontoEntrada().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, " Ponto de Entrada");
		}

		// [FS0003] - Atualiza��o realizada por outro usu�rio
		FiltroFuncionalidade filtroFuncionalidade = new FiltroFuncionalidade();
		filtroFuncionalidade.adicionarParametro(new ParametroSimples(FiltroFuncionalidade.ID, funcionalidade.getId()));

		Collection colecaoFuncionalidadeBase = getControladorUtil().pesquisar(filtroFuncionalidade, Funcionalidade.class.getName());

		if(colecaoFuncionalidadeBase == null || colecaoFuncionalidadeBase.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		Funcionalidade funcionalidadeBase = (Funcionalidade) colecaoFuncionalidadeBase.iterator().next();

		if(funcionalidadeBase.getUltimaAlteracao().after(funcionalidade.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		filtroFuncionalidade.limparListaParametros();

		// Verifica se o campo Modulo foi preenchido

		if(funcionalidade.getModulo() == null || funcionalidade.getModulo().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.naoinformado", null, "M�dulo");
		}

		String descFuncionalidadeNaBase = funcionalidadeBase.getDescricao();
		if(!funcionalidade.getDescricao().equalsIgnoreCase(descFuncionalidadeNaBase)){
			filtroFuncionalidade.adicionarParametro(new ParametroSimples(FiltroFuncionalidade.DESCRICAO, funcionalidade.getDescricao()));

			Collection colecaoFuncionalidade = getControladorUtil().pesquisar(filtroFuncionalidade, Funcionalidade.class.getName());

			if(colecaoFuncionalidade != null && !colecaoFuncionalidade.isEmpty()){
				throw new ControladorException("atencao.descricao_ja_existente", null, "" + funcionalidade.getDescricao() + "");
			}
		}

		funcionalidade.setUltimaAlteracao(new Date());

		getControladorUtil().atualizar(funcionalidade);

		FiltroFuncionalidadeDependencia filtroFuncionalidadeDependencia = new FiltroFuncionalidadeDependencia();
		filtroFuncionalidadeDependencia.adicionarParametro(new ParametroSimples(FiltroFuncionalidadeDependencia.FUNCIONALIDADE_ID,
						funcionalidade.getId()));

		Collection colecaoFuncionalidadeDependenciaBase = getControladorUtil().pesquisar(filtroFuncionalidadeDependencia,
						FuncionalidadeDependencia.class.getName());

		if(colecaoFuncionalidadeDependenciaBase != null && !colecaoFuncionalidadeDependenciaBase.isEmpty()){
			Iterator colecaoFuncionalidadeDependenciaBaseIterator = colecaoFuncionalidadeDependenciaBase.iterator();

			while(colecaoFuncionalidadeDependenciaBaseIterator.hasNext()){
				FuncionalidadeDependencia funcionalidadeDependenciaBase = (FuncionalidadeDependencia) colecaoFuncionalidadeDependenciaBaseIterator
								.next();
				getControladorUtil().remover(funcionalidadeDependenciaBase);
			}
		}

		if(colecaoFuncionalidadeDependencia != null && !colecaoFuncionalidadeDependencia.isEmpty()){
			Iterator colecaoFuncionalidadeDependenciaIterator = colecaoFuncionalidadeDependencia.iterator();

			while(colecaoFuncionalidadeDependenciaIterator.hasNext()){
				FuncionalidadeDependencia funcionalidadeDependenciaTela = (FuncionalidadeDependencia) colecaoFuncionalidadeDependenciaIterator
								.next();

				FuncionalidadeDependencia funcionalidadeDependencia = new FuncionalidadeDependencia();

				funcionalidadeDependencia.setFuncionalidade(funcionalidade);
				funcionalidadeDependencia.setFuncionalidadeDependencia(funcionalidadeDependenciaTela.getFuncionalidadeDependencia());

				FuncionalidadeDependenciaPK funcionalidadeDependenciaPK = new FuncionalidadeDependenciaPK();
				funcionalidadeDependenciaPK
								.setFuncionalidadeDependenciaId(funcionalidadeDependencia.getFuncionalidadeDependencia().getId());
				funcionalidadeDependenciaPK.setFuncionalidadeId(funcionalidade.getId());
				funcionalidadeDependencia.setComp_id(funcionalidadeDependenciaPK);

				getControladorUtil().inserir(funcionalidadeDependencia);
			}
		}
	}

	/**
	 * [UC0197] Filtrar Opera��es Efetuadas
	 * 
	 * @author Saulo Lima
	 * @date 18/05/2012
	 * @param
	 * @throws ControladorException
	 */
	public Collection<Funcionalidade> pesquisarFuncionalidadesComOperacaoAuditavel() throws ControladorException{

		Collection<Funcionalidade> retorno = null;

		try{
			retorno = repositorioAcesso.pesquisarFuncionalidadesComOperacaoAuditavel();
		}catch(ErroRepositorioException e){
			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/*
	 * public void inserirOperacao(Operacao operacao, Collection<Tabela>
	 * colecaoOperacaoTabela) throws ControladorException { // Verifica se todos
	 * os campos obrigatorios foram preenchidos // Verifica se o campo Descri��o
	 * foi preenchido
	 * if (funcionalidade.getDescricao() == null ||
	 * funcionalidade.getDescricao().equals( "" +
	 * ConstantesSistema.NUMERO_NAO_INFORMADO)) { throw new
	 * ControladorException("atencao.Informe_entidade", null, " Descri��o"); } //
	 * Verifica se o campo DescricaoAbreviada foi preenchido
	 * if (funcionalidade.getDescricaoAbreviada() == null ||
	 * funcionalidade.getDescricaoAbreviada().equals( "" +
	 * ConstantesSistema.NUMERO_NAO_INFORMADO)) { throw new
	 * ControladorException("atencao.Informe_entidade", null, "Descri��o
	 * Abreviada"); } // Verifica se o campo CaminhoMenu foi preenchido
	 * if (funcionalidade.getCaminhoMenu() == null ||
	 * funcionalidade.getCaminhoMenu().equals( "" +
	 * ConstantesSistema.NUMERO_NAO_INFORMADO)) { throw new
	 * ControladorException("atencao.Informe_entidade", null, "Caminho Menu"); } //
	 * Verifica se o campo CaminhoURL foi preenchido
	 * if (funcionalidade.getCaminhoUrl() == null ||
	 * funcionalidade.getCaminhoUrl().equals( "" +
	 * ConstantesSistema.NUMERO_NAO_INFORMADO)) { throw new
	 * ControladorException("atencao.Informe_entidade", null, "Caminho URL"); } //
	 * Verifica se o campo IndicadorPontoEntrada foi preenchido
	 * if (funcionalidade.getIndicadorPontoEntrada() == null ||
	 * funcionalidade.getIndicadorPontoEntrada().equals( "" +
	 * ConstantesSistema.NUMERO_NAO_INFORMADO)) { throw new
	 * ControladorException("atencao.Informe_entidade", null, " Ponto de
	 * Entrada"); } // Verifica se o campo Modulo foi preenchido
	 * if (funcionalidade.getModulo() == null ||
	 * funcionalidade.getModulo().equals( "" +
	 * ConstantesSistema.NUMERO_NAO_INFORMADO)) { throw new
	 * ControladorException("atencao.naoinformado", null, "M�dulo"); }
	 * FiltroFuncionalidade filtroFuncionalidade = new FiltroFuncionalidade();
	 * filtroFuncionalidade.adicionarParametro(new ParametroSimples(
	 * FiltroFuncionalidade.DESCRICAO, funcionalidade.getDescricao()));
	 * filtroFuncionalidade.adicionarParametro(new ParametroSimples(
	 * FiltroFuncionalidade.DESCRICAO_ABREVIADA, funcionalidade
	 * .getDescricaoAbreviada()));
	 * filtroFuncionalidade.adicionarParametro(new ParametroSimples(
	 * FiltroFuncionalidade.CAMINHO_MENU, funcionalidade .getCaminhoMenu()));
	 * filtroFuncionalidade.adicionarParametro(new ParametroSimples(
	 * FiltroFuncionalidade.CAMINHO_URL, funcionalidade .getCaminhoUrl()));
	 * filtroFuncionalidade.adicionarParametro(new ParametroSimples(
	 * FiltroFuncionalidade.INDICADOR_PONTO_ENTRADA, funcionalidade
	 * .getIndicadorPontoEntrada()));
	 * Collection colecaoFuncionalidade = getControladorUtil().pesquisar(
	 * filtroFuncionalidade, Funcionalidade.class.getName());
	 * if (colecaoFuncionalidade != null && !colecaoFuncionalidade.isEmpty()) {
	 * throw new ControladorException("atencao.descricao_ja_existente", null,
	 * FiltroFuncionalidade.DESCRICAO); }
	 * Iterator iterator = colecaoFuncionalidadeDependencia.iterator();
	 * while (iterator.hasNext()) {
	 * FuncionalidadeDependencia funcionalidadeDependencia =
	 * (FuncionalidadeDependencia) iterator .next();
	 * funcionalidadeDependencia.setFuncionalidade(funcionalidade);
	 * this.getControladorUtil().inserir(funcionalidadeDependencia); }
	 * operacao.setUltimaAlteracao(new Date());
	 * Integer idOperacao = (Integer) getControladorUtil().inserir(operacao);
	 * operacao.setId(idOperacao);
	 * for(Tabela tabela : colecaoOperacaoTabela){
	 * OperacaoTabela operacaoTabela = new OperacaoTabela(new
	 * OperacaoTabelaPK(idOperacao, tabela.getId()));
	 * this.getControladorUtil().inserir(operacaoTabela); } }
	 */

	/**
	 * Inseri um grupo na base de dados e suas permiss�es
	 * [UC0278] Inserir Grupo
	 * 
	 * @author Pedro Alexandre
	 * @date 21/07/2006
	 * @param grupo
	 * @param colecaoGrupoFuncionalidadeOperacao
	 * @throws ControladorException
	 */
	public void inserirGrupo(Grupo grupo, Collection colecaoGrupoFuncionalidadeOperacao) throws ControladorException{

		// Seta a data de �ltima altera��o do grupo
		grupo.setUltimaAlteracao(new Date());

		// Inseri o grupo no sistema
		getControladorUtil().inserir(grupo);

		/*
		 * Caso o usu�rio tenha informado alguma permiss�opara o grupo inseri as
		 * permiss�es do grupo na tabela GrupoFuncionalidadeOperacao
		 */
		if(colecaoGrupoFuncionalidadeOperacao != null && !colecaoGrupoFuncionalidadeOperacao.isEmpty()){
			// Cria o iterator das permiss�es
			Iterator iteratorGrupoFuncionalidadeOperacao = colecaoGrupoFuncionalidadeOperacao.iterator();

			// La�o para adicionar as todas as permiss�es informadas para o
			// grupo
			while(iteratorGrupoFuncionalidadeOperacao.hasNext()){
				// Cria o objeto GrupoFuncionalidadeOperacao que vai representar
				// a permiss�o do grupo
				GrupoFuncionalidadeOperacao grupoFuncionalidadeOperacao = (GrupoFuncionalidadeOperacao) iteratorGrupoFuncionalidadeOperacao
								.next();

				// Seta o grupo inserido na permiss�o
				grupoFuncionalidadeOperacao.setGrupo(grupo);

				// Cria a chave para a permiss�o do grupo
				GrupoFuncionalidadeOperacaoPK grupoFuncionalidadeOperacaoPK = new GrupoFuncionalidadeOperacaoPK();
				grupoFuncionalidadeOperacaoPK.setFuncionalidadeId(grupoFuncionalidadeOperacao.getFuncionalidade().getId());
				grupoFuncionalidadeOperacaoPK.setOperacaoId(grupoFuncionalidadeOperacao.getOperacao().getId());
				grupoFuncionalidadeOperacaoPK.setGrupoId(grupoFuncionalidadeOperacao.getGrupo().getId());

				// Seta a chave na permiss�o
				grupoFuncionalidadeOperacao.setComp_id(grupoFuncionalidadeOperacaoPK);

				// Inseri a permiss�o do grupo no sistema
				getControladorUtil().inserir(grupoFuncionalidadeOperacao);
			}
		}
	}

	/**
	 * M�todo que atualiza um grupo e seus acessos
	 * [UC0279] - Manter Grupo
	 * 
	 * @author Pedro Alexandre
	 * @date 03/07/2006
	 * @param grupo
	 * @param grupoFuncionalidadeOperacao
	 */
	public void atualizarGrupo(Grupo grupo, Collection colecaoGrupoFuncionalidadeOperacao) throws ControladorException{

		/*
		 * Pesquisa o grupo na base de dados e verifica se o registro n�o foi
		 * atualizado por outro usu�rio durante essa transa��o
		 */
		FiltroGrupo filtroGrupo = new FiltroGrupo();
		filtroGrupo.adicionarParametro(new ParametroSimples(FiltroGrupo.ID, grupo.getId()));
		Collection colecaoGrupo = getControladorUtil().pesquisar(filtroGrupo, Grupo.class.getName());
		if(colecaoGrupo != null && !colecaoGrupo.isEmpty()){
			// Recupera o grupo na base de dados
			Grupo grupoNaBase = (Grupo) colecaoGrupo.iterator().next();

			// [FS0004] - Atualiza��o realizada por outro usu�rio
			if(grupoNaBase.getUltimaAlteracao().after(grupo.getUltimaAlteracao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}
		}

		/*
		 * Seta a data da ultima atualiza��o do grupo e atualiza os dados do
		 * grupo
		 */
		grupo.setUltimaAlteracao(new Date());
		getControladorUtil().atualizar(grupo);

		/*
		 * Cria o filtro para pesquisar as permiss�es j� cadastradas para o
		 * grupo
		 */
		FiltroGrupoFuncionalidadeOperacao filtroGrupoFuncionalidadeOperacao = new FiltroGrupoFuncionalidadeOperacao();
		filtroGrupoFuncionalidadeOperacao
						.adicionarParametro(new ParametroSimples(FiltroGrupoFuncionalidadeOperacao.GRUPO_ID, grupo.getId()));
		filtroGrupoFuncionalidadeOperacao.setConsultaSemLimites(true);
		Collection colecaoGrupoFuncionalidadeOperacaoCadastradas = getControladorUtil().pesquisar(filtroGrupoFuncionalidadeOperacao,
						GrupoFuncionalidadeOperacao.class.getName());

		// Caso exista permiss�es cadastradas para o grupo que est� sendo
		// atualizado
		if(colecaoGrupoFuncionalidadeOperacaoCadastradas != null && !colecaoGrupoFuncionalidadeOperacaoCadastradas.isEmpty()
						&& colecaoGrupoFuncionalidadeOperacao != null){

			// Cria o iterator das permiss�es cadastradas para o grupo
			Iterator iteratorGrupoFuncionalidadeOperacao = colecaoGrupoFuncionalidadeOperacaoCadastradas.iterator();

			// La�o para remover as permiss�es que foram retiradas pelo usu�rio
			// para o grupo
			while(iteratorGrupoFuncionalidadeOperacao.hasNext()){

				// Recupera a permiss�o do grupo do iterator
				GrupoFuncionalidadeOperacao grupoFuncionalidadeOperacao = (GrupoFuncionalidadeOperacao) iteratorGrupoFuncionalidadeOperacao
								.next();

				/*
				 * Caso a permiss�o n�o esteja contida na nova cole��o de
				 * permiss�es registradas pelo usu�rio para atualizar o grupo
				 * Remove as retri��es para essa opera��o e depois remove a
				 * permiss�o para a opera��o
				 */
				if(!colecaoGrupoFuncionalidadeOperacao.contains(grupoFuncionalidadeOperacao)){

					/*
					 * Cria o filtro para pesquisar se existe alguma restri�a�
					 * para essa opera��o para algum usu�rio, Setando o c�digo
					 * do grupo, c�digo da funcionalidade e c�digo da opera��o
					 */
					FiltroUsuarioGrupoRestricao filtroUsuarioGrupoRestricao = new FiltroUsuarioGrupoRestricao();
					filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.GRUPO_ID,
									grupoFuncionalidadeOperacao.getGrupo().getId()));
					filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.FUNCIONALIDADE_ID,
									grupoFuncionalidadeOperacao.getFuncionalidade().getId()));
					filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.OPERACAO_ID,
									grupoFuncionalidadeOperacao.getOperacao().getId()));

					// Pesquisa as retri��es para a opera��o
					Collection<UsuarioGrupoRestricao> restricoes = getControladorUtil().pesquisar(filtroUsuarioGrupoRestricao,
									UsuarioGrupoRestricao.class.getName());

					/*
					 * Caso exista restri��o para a opera��o remove as
					 * restri��es para depois remover a permiss�o para a
					 * opera��o
					 */
					if(restricoes != null && !restricoes.isEmpty()){
						// La�o para remover todas as restri��es
						for(UsuarioGrupoRestricao usuarioGrupoRestricao : restricoes){
							getControladorUtil().remover(usuarioGrupoRestricao);
						}
					}
					// Remove a permiss�o
					getControladorUtil().remover(grupoFuncionalidadeOperacao);
				}
			}
		}

		/*
		 * Caso o usu�rio tenha informado algum acesso para o grupo que est�
		 * sendo atualizado, inseri todos os acessos do grupo informados inserir
		 * na tabela grupo_funcionalidade_operacao
		 */
		if(colecaoGrupoFuncionalidadeOperacao != null && !colecaoGrupoFuncionalidadeOperacao.isEmpty()){

			// Cria o iterator para as permiss�es do grupo informadas pelo
			// usu�rio
			Iterator iterator = colecaoGrupoFuncionalidadeOperacao.iterator();
			while(iterator.hasNext()){
				// Recupera a permiss�o do iterator
				GrupoFuncionalidadeOperacao grupoFuncionalidadeOperacao = (GrupoFuncionalidadeOperacao) iterator.next();
				grupoFuncionalidadeOperacao.setGrupo(grupo);

				// Cria a chave para a permiss�o
				GrupoFuncionalidadeOperacaoPK grupoFuncionalidadeOperacaoPK = new GrupoFuncionalidadeOperacaoPK();
				grupoFuncionalidadeOperacaoPK.setFuncionalidadeId(grupoFuncionalidadeOperacao.getFuncionalidade().getId());
				grupoFuncionalidadeOperacaoPK.setOperacaoId(grupoFuncionalidadeOperacao.getOperacao().getId());
				grupoFuncionalidadeOperacaoPK.setGrupoId(grupoFuncionalidadeOperacao.getGrupo().getId());

				// Seta a chave composta na permiss�o
				grupoFuncionalidadeOperacao.setComp_id(grupoFuncionalidadeOperacaoPK);

				// Caso a permiss�o ainda n�o esteja cadastrada para o grupo
				// inseri a permiss�o para o grupo
				if(!colecaoGrupoFuncionalidadeOperacaoCadastradas.contains(grupoFuncionalidadeOperacao)){
					getControladorUtil().inserir(grupoFuncionalidadeOperacao);
				}
			}
		}
	}

	/**
	 * Remove os grupos selecionados na tela de manter grupo e os
	 * relacionamentos existentes para o grupo(remove da tabela
	 * GrupoFuncionalidadeOperacao).
	 * [UC0279] - Manter Grupo
	 * 
	 * @author Pedro Alexandre
	 * @date 29/06/2006
	 * @param idsGrupos
	 * @throws ControladorException
	 */
	public void removerGrupo(String[] idsGrupos) throws ControladorException{

		// La�o para remover todos os grupos informados
		for(int i = 0; i < idsGrupos.length; i++){
			// Verifica se o grupo existe realmente na base
			FiltroGrupo filtroGrupo = new FiltroGrupo();
			filtroGrupo.adicionarParametro(new ParametroSimples(FiltroGrupo.ID, idsGrupos[i]));
			Collection colecaoGrupo = getControladorUtil().pesquisar(filtroGrupo, Grupo.class.getName());

			/*
			 * Caso a pesquisa tenha retornado o grupo remove todas as
			 * permiss�es existentes para o grupoe depois remove o grupo do
			 * sistema
			 */
			if(colecaoGrupo != null && !colecaoGrupo.isEmpty()){
				// Recupera o grupo da cole��o
				Grupo grupo = (Grupo) colecaoGrupo.iterator().next();

				// Pesquisa todos os acessos do grupo
				FiltroGrupoFuncionalidadeOperacao filtroGrupoFuncionalidadeOperacao = new FiltroGrupoFuncionalidadeOperacao();
				filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(FiltroGrupoFuncionalidadeOperacao.GRUPO_ID, grupo
								.getId()));
				Collection colecaoGrupoFuncionalidadeOperacao = getControladorUtil().pesquisar(filtroGrupoFuncionalidadeOperacao,
								GrupoFuncionalidadeOperacao.class.getName());

				// Caso exista acessos para o grupo(na tabela
				// GrupoFuncinalidadeOperacao) remove todos os
				// acessos do grupo antes de remover o grupo
				if(colecaoGrupoFuncionalidadeOperacao != null && !colecaoGrupoFuncionalidadeOperacao.isEmpty()){
					// Cria o iterator para remover todos os acessos do grupo
					Iterator iteratorGrupoFuncionalidadeOperacao = colecaoGrupoFuncionalidadeOperacao.iterator();

					// La�o para remover todos os acessos do grupo
					while(iteratorGrupoFuncionalidadeOperacao.hasNext()){
						// Recupera o acesso do grupo da cole��o
						GrupoFuncionalidadeOperacao grupoFuncionalidadeOperacao = (GrupoFuncionalidadeOperacao) iteratorGrupoFuncionalidadeOperacao
										.next();

						// Remove o acesso do grupo
						getControladorUtil().remover(grupoFuncionalidadeOperacao);
					}
				}
				// Remove o grupo selecionado
				getControladorUtil().remover(grupo);
			}
		}
	}

	/**
	 * Permite inserir uma ResolucaoDiretoria
	 * [UC0217] Inserir Resolu��o de Diretoria
	 * 
	 * @author Rafael Corr�a
	 * @date 30/03/2006
	 */
	public Integer inserirSituacaoUsuario(UsuarioSituacao usuarioSituacao) throws ControladorException{

		FiltroUsuarioSituacao filtroUsuarioSituacao = new FiltroUsuarioSituacao();
		filtroUsuarioSituacao.adicionarParametro(new ParametroSimples(FiltroUsuarioSituacao.DESCRICAO, usuarioSituacao
						.getDescricaoUsuarioSituacao()));

		Collection colecaoResolucaoDiretoria = getControladorUtil().pesquisar(filtroUsuarioSituacao, UsuarioSituacao.class.getName());

		if(colecaoResolucaoDiretoria != null && !colecaoResolucaoDiretoria.isEmpty()){
			throw new ControladorException("atencao.desc_ja_existente_situacao_usuario", null, usuarioSituacao
							.getDescricaoUsuarioSituacao());
		}

		usuarioSituacao.setUltimaAlteracao(new Date());
		Integer id = (Integer) getControladorUtil().inserir(usuarioSituacao);

		return id;
	}

	/**
	 * Inseri uma opera��o e seus relacionamentos com as tabelas se existir
	 * [UC0284]Inserir Opera��o
	 * 
	 * @author Pedro Alexandre
	 * @date 08/05/2006
	 * @param operacao
	 * @param colecaoOperacaoTabela
	 * @throws ControladorException
	 */
	public void inserirOperacao(Operacao operacao, Collection<Tabela> colecaoOperacaoTabela, Usuario usuarioLogado)
					throws ControladorException{

		// [FS0001 Verificar exist�ncia da descri��o]
		// Cria o filtro de opera��o para verificar se j� existe uma opera��o
		// cadastrada
		// com a descri��o informada
		FiltroOperacao filtroOperacao = new FiltroOperacao();
		filtroOperacao.adicionarParametro(new ParametroSimples(FiltroOperacao.DESCRICAO, operacao.getDescricao().toUpperCase()));
		Collection colecaoOperacao = getControladorUtil().pesquisar(filtroOperacao, Operacao.class.getName());

		// Caso exista opera��o cadastrada com a opera��o informada
		// levanta a exce��o para o usu�rio
		if(colecaoOperacao != null && !colecaoOperacao.isEmpty()){
			throw new ControladorException("atencao.descricao.operacao.ja.existente", null, operacao.getDescricao());
		}

		// [FS0004 - Verificar exist�ncia da funcionalidade]
		// Cria o filtro de funcionalidade para verificar se existe a
		// funcionalidade informada
		FiltroFuncionalidade filtroFuncionalidade = new FiltroFuncionalidade();
		filtroFuncionalidade.adicionarParametro(new ParametroSimples(FiltroFuncionalidade.ID, operacao.getFuncionalidade().getId()));
		Collection colecaoFuncionalidade = getControladorUtil().pesquisar(filtroFuncionalidade, Funcionalidade.class.getName());

		// Caso a funcionalidade informada n�o esteja cadastrada no sistema
		// levanta uma exce��o para o cliente
		if(colecaoFuncionalidade == null || colecaoFuncionalidade.isEmpty()){
			throw new ControladorException("atencao.funcionalidade.inexistente", null, operacao.getDescricao());
		}

		// Cria a vari�vel que vai aramzenar o tipo da opera��o
		OperacaoTipo operacaoTipo = null;

		// Caso o tipo da opera��o tenha sido informada
		// pesquisa o tipo da opera��o no sistema
		// Caso contr�rio levanta uma exce��o indicando que o tipo da opera��o
		// n�o foi informada
		if(operacao.getOperacaoTipo() != null){
			FiltroOperacaoTipo filtroOperacaoTipo = new FiltroOperacaoTipo();
			filtroOperacaoTipo.adicionarParametro(new ParametroSimples(FiltroOperacaoTipo.ID, operacao.getOperacaoTipo().getId()));
			Collection colecaoOperacaoTipo = getControladorUtil().pesquisar(filtroOperacaoTipo, OperacaoTipo.class.getName());

			// Caso o tipo da opera��o informada n�o exista
			// levanta uma exce��o indicando que o tipo da opera��o n�o existe
			// Caso contr�rio recupera o tipo da opera��o da cole��o pesquisada
			if(colecaoOperacaoTipo == null || colecaoOperacaoTipo.isEmpty()){
				throw new ControladorException("atencao.operacao_tipo.inexistente", null, "" + operacao.getOperacaoTipo().getId());
			}else{
				operacaoTipo = (OperacaoTipo) Util.retonarObjetoDeColecao(colecaoOperacaoTipo);
			}
		}else{
			throw new ControladorException("atencao.operacao_tipo.nao.informado", null);
		}

		if(operacao.getIndicadorAuditoria() != ConstantesSistema.SIM.intValue()
						&& operacao.getIndicadorAuditoria() != ConstantesSistema.NAO.intValue()){
			throw new ControladorException("atencao.naoinformado", null, "Indicador de Auditoria");
		}

		// Caso o tipo da opera��o informada seja pesquisar
		// verifica o preenchimento do argumento de pesquisa
		if(operacaoTipo.getId().intValue() == OperacaoTipo.PESQUISAR.intValue()){

			// Caso o argumento de pesquisa n�o tenha sido informado
			// levanta uma exce��o indicando que o argumento de pesquisa n�o foi
			// informado
			if(operacao.getTabelaColuna() == null){
				throw new ControladorException("atencao.argumento_pesquisa.nao.informado", null);
			}else{
				// [FS0005 - Verificar exist�ncia do argumento de pesquisa]
				// Cria o filtro para pesqusiar o argumento de pesquisa
				// informado
				FiltroTabelaColuna filtroTabelaColuna = new FiltroTabelaColuna();
				filtroTabelaColuna.adicionarParametro(new ParametroSimples(FiltroTabelaColuna.ID, operacao.getTabelaColuna().getId()));

				// Pesquisa o argumento de pesquisa
				Collection colecaoTabelaColuna = getControladorUtil().pesquisar(filtroTabelaColuna, TabelaColuna.class.getName());

				// Caso o argumento de pesquisa n�o esteja cadastrado
				// levanta uma exce��o indicando que o argumento de pesquisa n�o
				// existe
				// Caso contr�rio recupera o argumento de pesquisa da cole��o
				if(colecaoTabelaColuna == null || colecaoTabelaColuna.isEmpty()){
					throw new ControladorException("atencao.argumento_pesquisa.inexistente", null);
				}else{
					// [FS0011 - Verificar argumento de pesquisa]
					TabelaColuna argumentoPesquisa = (TabelaColuna) Util.retonarObjetoDeColecao(colecaoTabelaColuna);

					// Caso o argumento de pesquisa informado n�o seja chave
					// prim�ria
					// levanta uma exce�a� indicando que o argumento de pesquisa
					// n�o � chave prim�ria da tabela
					if(argumentoPesquisa.getIndicadorPrimaryKey() == ConstantesSistema.NAO){
						throw new ControladorException("atencao.argumento_pesquisa.nao.chave.primaria", null);
					}

					// Cria o filtro para verificar se j� existe opera��o com o
					// argumento de pesquisa informado
					FiltroOperacao filtroOperacaoComArgumentoPesquisa = new FiltroOperacao();
					filtroOperacaoComArgumentoPesquisa.adicionarParametro(new ParametroSimples(FiltroOperacao.TABELA_COLUNA_ID,
									argumentoPesquisa.getId()));
					Collection colecaoOperacaoComArgumentoPesquisa = getControladorUtil().pesquisar(filtroOperacaoComArgumentoPesquisa,
									Operacao.class.getName());

					// Caso j� existe opera��o com o argumento de pesquisa
					// informado
					// levanta uma exce��o indicando que j� existe uma opera��o
					// com o
					// argumento de pesquisa informado
					if(colecaoOperacaoComArgumentoPesquisa != null && !colecaoOperacaoComArgumentoPesquisa.isEmpty()){
						Operacao operacaoComArgumentoPesquisa = (Operacao) Util.retonarObjetoDeColecao(colecaoOperacaoComArgumentoPesquisa);
						throw new ControladorException("atencao.argumento_pesquisa.ja.associado", null, operacaoComArgumentoPesquisa
										.getDescricao());
					}
				}
			}

		}else{
			// Caso o tipo de opera��o n�o seja "pesquisar"
			if(operacaoTipo.getIndicadorAtualiza() == ConstantesSistema.SIM){

				// Caso o usu�rio n�o tenha informado nenhuma tabela
				if(colecaoOperacaoTabela == null || colecaoOperacaoTabela.isEmpty()){
					throw new ControladorException("atencao.tabela.nao.informada", null);
				}

				// [FS0007 - Verificar exist�ncia da opera��o]
				if(operacao.getOperacaoPesquisa() != null){

					// Cria o filtro para pesquisar a opera��o de pesquisa
					// informada
					FiltroOperacao filtroOperacaoPesquisa = new FiltroOperacao();
					filtroOperacaoPesquisa.adicionarParametro(new ParametroSimples(FiltroOperacao.ID, operacao.getOperacaoPesquisa()
									.getId()));
					Collection colecaoOperacaoPesquisa = getControladorUtil().pesquisar(filtroOperacaoPesquisa, Operacao.class.getName());

					// Caso a opera��o de pesquisa n�o esteja cadastrada
					// levanta uma exce��o indicando que a opera��o de pesquisa
					// n�o existe
					if(colecaoOperacaoPesquisa == null || colecaoOperacaoPesquisa.isEmpty()){

						throw new ControladorException("atencao.operacao_pesquisa.inexistente", null);
					}
				}
			}
		}

		// ------------ REGISTRAR TRANSA��O ----------------

		// RETIRAR ISSO DEPOIS
		// *******************************************************
		usuarioLogado = Usuario.USUARIO_TESTE;
		// ***************************************************************************

		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_OPERACAO_INSERIR, new UsuarioAcaoUsuarioHelper(
						usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacaoRegistrarTransacao = new Operacao();
		operacaoRegistrarTransacao.setId(Operacao.OPERACAO_OPERACAO_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacaoRegistrarTransacao);

		operacao.setOperacaoEfetuada(operacaoEfetuada);
		operacao.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(operacao);
		// ------------ REGISTRAR TRANSA��O ----------------

		// Inseri a opera��o no sistema e recupera o id gerado
		Integer idOperacao = (Integer) getControladorUtil().inserir(operacao);

		// Seta o id no objeto
		operacao.setId(idOperacao);

		// Caso exista a cole��o de tabela
		// Inseri todos os relacionamento entre a opera��o inserida e as tabelas
		// informadas
		if(colecaoOperacaoTabela != null){
			for(Tabela tabela : colecaoOperacaoTabela){
				OperacaoTabela operacaoTabela = new OperacaoTabela(new OperacaoTabelaPK(idOperacao, tabela.getId()));

				// operacaoTabela.setOperacaoEfetuada(operacaoEfetuada);
				// operacaoTabela.adicionarUsuario(usuarioLogado,
				// UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				// registradorOperacao.registrarOperacao(operacaoTabela);

				this.getControladorUtil().inserir(operacaoTabela);
			}
		}
	}

	/**
	 * Permite inserir uma ResolucaoDiretoria
	 * [UC0297] Inserir Abrang�ncia Usuario
	 * 
	 * @author Thiago Ten�rio
	 * @date 30/03/2006
	 */
	public Integer inserirAbrangenciaUsuario(UsuarioAbrangencia usuarioAbrangencia) throws ControladorException{

		FiltroAbrangenciaUsuario filtroAbrangenciaUsuario = new FiltroAbrangenciaUsuario();
		filtroAbrangenciaUsuario.adicionarParametro(new ParametroSimples(FiltroAbrangenciaUsuario.DESCRICAO, usuarioAbrangencia
						.getDescricao()));

		filtroAbrangenciaUsuario.adicionarParametro(new ParametroSimples(FiltroAbrangenciaUsuario.DESCRICAO_ABREVIADA, usuarioAbrangencia
						.getDescricaoAbreviada()));

		if(usuarioAbrangencia.getUsuarioAbrangenciaSuperior() != null
						&& !usuarioAbrangencia.getUsuarioAbrangenciaSuperior().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			filtroAbrangenciaUsuario.adicionarParametro(new ParametroSimples(FiltroAbrangenciaUsuario.ABRANGENCIA_SUPERIOR,
							usuarioAbrangencia.getUsuarioAbrangenciaSuperior()));

		}

		filtroAbrangenciaUsuario.adicionarParametro(new ParametroSimples(FiltroAbrangenciaUsuario.INDICADOR_USO, usuarioAbrangencia
						.getIndicadorUso()));

		// Aqui
		Collection colecaoUsuarioAbrangencia = getControladorUtil().pesquisar(filtroAbrangenciaUsuario, UsuarioAbrangencia.class.getName());

		if(colecaoUsuarioAbrangencia != null && !colecaoUsuarioAbrangencia.isEmpty()){
			throw new ControladorException("atencao.numero_resolucao_ja_existente");
		}

		usuarioAbrangencia.setUltimaAlteracao(new Date());
		Integer id = (Integer) getControladorUtil().inserir(usuarioAbrangencia);

		return id;
	}

	/**
	 * [UC0294] Manter Situa��o Usu�rio [] Atualizar Situa��o do Usuario Metodo
	 * que atualiza a Situa��o Usuario
	 * 
	 * @author Thiago Ten�rio
	 * @date 25/05/2006
	 * @param Situa��o
	 *            Usu�rio
	 * @throws ControladorException
	 */

	public void atualizarSituacaoUsuario(UsuarioSituacao usuarioSituacao, Collection colecaoUsuarioSituacao) throws ControladorException{

		// Verifica se todos os campos obrigatorios foram preenchidos

		if((usuarioSituacao.getDescricaoUsuarioSituacao() == null || usuarioSituacao.getDescricaoUsuarioSituacao().equals(
						"" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (usuarioSituacao.getDescricaoAbreviada() == null || usuarioSituacao.getDescricaoAbreviada().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (usuarioSituacao.getId() == null || usuarioSituacao.getId().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (usuarioSituacao.getIndicadorUso() == null || usuarioSituacao.getIndicadorUso().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO))){
			throw new ControladorException("atencao.filtro.nenhum_parametro_informado");

		}

		// Verifica se o campo Descri��o foi preenchido

		if(usuarioSituacao.getDescricaoUsuarioSituacao() == null
						|| usuarioSituacao.getDescricaoUsuarioSituacao().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, " Descri��o");
		}

		// Verifica se o campo DescricaoAbreviada foi preenchido

		if(usuarioSituacao.getDescricaoAbreviada() == null
						|| usuarioSituacao.getDescricaoAbreviada().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, "Descri��o Abreviada");
		}

		// Verifica se o campo IndicadorUso foi preenchido
		if(usuarioSituacao.getIndicadorUso() == null
						|| usuarioSituacao.getIndicadorUso().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, " Indicador de Uso");
		}

		// Verifica se o campo IndicadorUsoSistema preenchido

		if(usuarioSituacao.getIndicadorUsoSistema() == null
						|| usuarioSituacao.getIndicadorUsoSistema().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, " Indicador de Uso Exclusivo do Sistema");
		}

		// [FS0003] - Atualiza��o realizada por outro usu�rio
		FiltroSituacaoUsuario filtroSituacaoUsuario = new FiltroSituacaoUsuario();
		filtroSituacaoUsuario.adicionarParametro(new ParametroSimples(FiltroSituacaoUsuario.ID, usuarioSituacao.getId()));

		Collection colecaoUsuarioSituacaoBase = getControladorUtil().pesquisar(filtroSituacaoUsuario, UsuarioSituacao.class.getName());

		if(colecaoUsuarioSituacaoBase == null || colecaoUsuarioSituacaoBase.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		UsuarioSituacao usuarioSituacaoBase = (UsuarioSituacao) colecaoUsuarioSituacaoBase.iterator().next();

		if(usuarioSituacaoBase.getUltimaAlteracao().after(usuarioSituacaoBase.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		usuarioSituacao.setUltimaAlteracao(new Date());

		getControladorUtil().atualizar(usuarioSituacao);

	}

	/**
	 * [UC0298] Manter Abrang�ncia Usu�rio [] Atualizar Abrang�ncia do Usuario
	 * Metodo que atualiza a Situa��o Usuario
	 * 
	 * @author Thiago Ten�rio
	 * @date 25/05/2006
	 * @param Abrang�ncia
	 *            Usu�rio
	 * @throws ControladorException
	 */

	public void atualizarAbrangenciaUsuario(UsuarioAbrangencia usuarioAbrangencia) throws ControladorException{

		// Verifica se todos os campos obrigatorios foram preenchidos

		if((usuarioAbrangencia.getDescricao() == null || usuarioAbrangencia.getDescricao().equals(
						"" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (usuarioAbrangencia.getUsuarioAbrangenciaSuperior() == null || usuarioAbrangencia.getDescricaoAbreviada()
										.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (usuarioAbrangencia.getDescricaoAbreviada() == null || usuarioAbrangencia.getDescricaoAbreviada().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (usuarioAbrangencia.getId() == null || usuarioAbrangencia.getId().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (usuarioAbrangencia.getIndicadorUso() == null || usuarioAbrangencia.getIndicadorUso().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO))){
			throw new ControladorException("atencao.filtro.nenhum_parametro_informado");

		}

		// Verifica se o campo Descri��o foi preenchido

		if(usuarioAbrangencia.getDescricao() == null
						|| usuarioAbrangencia.getDescricao().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, " Descri��o");
		}

		// Verifica se o campo DescricaoAbreviada foi preenchido

		if(usuarioAbrangencia.getDescricaoAbreviada() == null
						|| usuarioAbrangencia.getDescricaoAbreviada().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, "Descri��o Abreviada");
		}

		// Verifica se o campo IndicadorUso foi preenchido
		if(usuarioAbrangencia.getIndicadorUso() == null
						|| usuarioAbrangencia.getIndicadorUso().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, " Indicador de Uso");
		}

		// Verifica se o campo AbrangenciaSuperior foi preenchido
		if(usuarioAbrangencia.getUsuarioAbrangenciaSuperior() == null
						|| usuarioAbrangencia.getUsuarioAbrangenciaSuperior().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, " Indicador de Uso");
		}

		// Verifica se o campo Codigo foi preenchido

		if(usuarioAbrangencia.getId() == null || usuarioAbrangencia.getId().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, " Indicador de Uso Exclusivo do Sistema");
		}

		// [FS0003] - Atualiza��o realizada por outro usu�rio
		FiltroAbrangenciaUsuario filtroAbrangenciaUsuario = new FiltroAbrangenciaUsuario();
		filtroAbrangenciaUsuario.adicionarParametro(new ParametroSimples(FiltroAbrangenciaUsuario.ID, usuarioAbrangencia.getId()));

		Collection colecaoUsuarioAbrangenciaBase = getControladorUtil().pesquisar(filtroAbrangenciaUsuario,
						UsuarioAbrangencia.class.getName());

		if(colecaoUsuarioAbrangenciaBase == null || colecaoUsuarioAbrangenciaBase.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		UsuarioAbrangencia usuarioAbrangenciaBase = (UsuarioAbrangencia) colecaoUsuarioAbrangenciaBase.iterator().next();

		if(usuarioAbrangencia.getUltimaAlteracao().after(usuarioAbrangenciaBase.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		usuarioAbrangencia.setUltimaAlteracao(new Date());

		getControladorUtil().atualizar(usuarioAbrangencia);

	}

	/**
	 * Constroi um menu de acesso de acordo com as permiss�es que o usu�rio que
	 * est� logado no sistema conteme monta o link de retorno com o link
	 * informado.
	 * [UC0277] - Construir menu de acesso
	 * 
	 * @author Pedro Alexandre
	 * @date 10/07/2006
	 * @param usuarioLogado
	 * @param linkRetorno
	 * @return
	 * @throws ControladorException
	 */
	public String construirMenuAcesso(Usuario usuarioLogado, String linkRetorno) throws ControladorException{

		Collection<Funcionalidade> colecaoFuncionalidadesPermitidas = new ArrayList();

		// Cria a colec�o que vai armazenar os m�dulos aos que o usu�rio tem
		// alguma funcionalidade cadastrada
		Collection modulos = new ArrayList();

		// Inicializa a vari�vel que vai definir os identicadores dos n�s
		// principais da arvore
		int contador = 0;

		// Vari�vel que vai armazenar o c�digo do n� principal temporariamente
		// para incluir as funcionalidades do m�dulo atual
		int temp;

		// Cria a primeira parte da arvore
		StringBuffer menu = new StringBuffer();
		menu
						.append("<link rel=\"StyleSheet\" href=\"/sgcq/css/dtree.css\" type=\"text/css\" /><script type=\"text/javascript\" src=\"/sgcq/javascript/dtree.js\"></script>\n");
		menu.append("<div class=\"dtree\">\n");
		menu.append("<script><!--\n p = new dTree('p');\n");
		menu.append("p.add(0,-1,'Funcionalidades');\n");

		Collection colecaoGruposUsuario = getControladorUsuario().pesquisarGruposUsuario(usuarioLogado.getId());

		if(!usuarioLogado.getUsuarioTipo().getId().equals(UsuarioTipo.USUARIO_TIPO_ADMINISTRADOR)){

			/*
			 * Pesquisa os grupos do usu�rio logado e seta esses grupos no
			 * filtro para a pesquisa das funcionalidade que os grupos tem
			 * acesso.
			 */
			Iterator iterator = colecaoGruposUsuario.iterator();
			FiltroGrupoFuncionalidadeOperacao filtroGrupoFuncionalidadeOperacao = new FiltroGrupoFuncionalidadeOperacao();
			filtroGrupoFuncionalidadeOperacao.adicionarCaminhoParaCarregamentoEntidade("funcionalidade.modulo");
			filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(
							FiltroGrupoFuncionalidadeOperacao.FUNCIONALIDADE_INDICADOR_PONTO_ENTRADA, ConstantesSistema.SIM));
			filtroGrupoFuncionalidadeOperacao.setConsultaSemLimites(true);

			// Inseri os grupos do usu�rio no filtro
			while(iterator.hasNext()){
				Grupo grupoUsuario = (Grupo) iterator.next();
				filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(FiltroGrupoFuncionalidadeOperacao.GRUPO_ID,
								grupoUsuario.getId(), FiltroParametro.CONECTOR_OR));
			}

			/*
			 * Pesquisa as funcionalidades as quais o usu�rio tem acesso atrav�s
			 * dos grupos a que o usu�rio pertence.
			 */
			Collection<GrupoFuncionalidadeOperacao> permissoes = getControladorUtil().pesquisar(filtroGrupoFuncionalidadeOperacao,
							GrupoFuncionalidadeOperacao.class.getName());
			Iterator<GrupoFuncionalidadeOperacao> iteratorPermissoes = permissoes.iterator();

			/*
			 * Retira as funcionalidades repetidas cadastradas para o usu�rio
			 * que est� logado recupera tamb�m os m�dulos para ser gerada a
			 * arvore
			 */
			while(iteratorPermissoes.hasNext()){
				// Recupera a funcionalidade
				GrupoFuncionalidadeOperacao grupoFuncionalidadeOperacao = iteratorPermissoes.next();
				Funcionalidade funcionalidadePermitida = grupoFuncionalidadeOperacao.getFuncionalidade();

				// Caso a funcionalidade ainda n�o esteja na cole��o de
				// funcionalidades de acesso do usu�rio
				// adiciona a funcionalidade a cole��o
				if(!colecaoFuncionalidadesPermitidas.contains(funcionalidadePermitida)){
					colecaoFuncionalidadesPermitidas.add(funcionalidadePermitida);
				}

				// Recupera o m�dulo da funcionalidade
				Modulo moduloPermitido = funcionalidadePermitida.getModulo();

				// Caso o modulo ainda n�o esteja na cole��o de modulos de
				// acesso do usu�rio
				// adiciona o modulo a cole��o
				if(!modulos.contains(moduloPermitido)){
					modulos.add(moduloPermitido);
				}
			}
		}else{
			FiltroFuncionalidade filtroFuncionalidade = new FiltroFuncionalidade();
			filtroFuncionalidade.adicionarParametro(new ParametroSimples(FiltroFuncionalidade.INDICADOR_PONTO_ENTRADA,
							ConstantesSistema.SIM));
			filtroFuncionalidade.adicionarCaminhoParaCarregamentoEntidade("modulo");
			filtroFuncionalidade.setConsultaSemLimites(true);

			Collection<Funcionalidade> permissoes = getControladorUtil().pesquisar(filtroFuncionalidade, Funcionalidade.class.getName());
			Iterator<Funcionalidade> iteratorPermissoes = permissoes.iterator();

			while(iteratorPermissoes.hasNext()){
				Funcionalidade funcionalidadePermitida = iteratorPermissoes.next();

				colecaoFuncionalidadesPermitidas.add(funcionalidadePermitida);

				// Recupera o m�dulo da funcionalidade
				Modulo moduloPermitido = funcionalidadePermitida.getModulo();

				// Caso o modulo ainda n�o esteja na cole��o de modulos de
				// acesso do usu�rio
				// adiciona o modulo a cole��o
				if(!modulos.contains(moduloPermitido)){
					modulos.add(moduloPermitido);
				}
			}
		}

		/*
		 * Inicio do c�digo din�mico para cria a arvore de acesso
		 */

		// Cria o iterator dos modulos do usu�rio logado
		Iterator iteratorModulos = modulos.iterator();

		/*
		 * La�o para incluir todos os modulos na �rvore e as suas
		 * funcionalidades
		 */
		while(iteratorModulos.hasNext()){
			// A vari�vel temp vai conter o valor do contador
			temp = ++contador;

			// Recupera o modulo do iterator
			Modulo modulo = (Modulo) iteratorModulos.next();

			// Inseri o n� do modulo na �rvore
			menu.append("p.add(" + temp + "," + "0" + ",'" + modulo.getDescricaoModulo() + "');\n");

			// Coloca a cole��o de funcionalidades permitidas para o usu�rio que
			// est� logado no iterator
			Iterator iteratorFuncionalidadesPermitidas = colecaoFuncionalidadesPermitidas.iterator();

			// La�o para incluir todas as funcionalidades na �rvore
			while(iteratorFuncionalidadesPermitidas.hasNext()){
				// Incrementa o contador
				++contador;

				// Pega a funcionalidade do iterator
				Funcionalidade funcionalidade = (Funcionalidade) iteratorFuncionalidadesPermitidas.next();

				// Caso a funcionalidade perten�a ao modulo atual
				// inseri a funcionalidade na arvor� dentro do n� do m�dulo
				if(modulo.getId().equals(funcionalidade.getModulo().getId())
								&& funcionalidade.getIndicadorPontoEntrada().equals(ConstantesSistema.SIM)){

					FiltroGrupoFuncionalidadeOperacao filtroFuncionalidadeCadastradaParaUsuario = new FiltroGrupoFuncionalidadeOperacao();
					filtroFuncionalidadeCadastradaParaUsuario.adicionarParametro(new ParametroSimples(
									FiltroGrupoFuncionalidadeOperacao.FUNCIONALIDADE_ID, funcionalidade.getId()));
					filtroFuncionalidadeCadastradaParaUsuario.setConsultaSemLimites(true);

					Iterator iteratorGrupos = colecaoGruposUsuario.iterator();

					// Inseri os grupos do usu�rio no filtro
					while(iteratorGrupos.hasNext()){
						Grupo grupoUsuario = (Grupo) iteratorGrupos.next();
						filtroFuncionalidadeCadastradaParaUsuario.adicionarParametro(new ParametroSimples(
										FiltroGrupoFuncionalidadeOperacao.GRUPO_ID, grupoUsuario.getId(), FiltroParametro.CONECTOR_OR,
										colecaoGruposUsuario.size()));
					}

					Collection funcionlidadeCadastradaParaUsuario = getControladorUtil().pesquisar(
									filtroFuncionalidadeCadastradaParaUsuario, GrupoFuncionalidadeOperacao.class.getName());

					if(funcionlidadeCadastradaParaUsuario == null || funcionlidadeCadastradaParaUsuario.isEmpty()){
						menu.append("p.add(" + contador + "," + temp + ",'" + funcionalidade.getDescricao() + "','" + linkRetorno
										+ "&codigoFuncionalidade=" + funcionalidade.getId() + "');\n");
					}else{
						menu.append("p.add(" + contador + "," + temp + ",'" + funcionalidade.getDescricao() + "','" + linkRetorno
										+ "&codigoFuncionalidade=" + funcionalidade.getId() + "','','','','check.gif');\n");

					}
				}
			}
		}
		// Fim do c�digo din�mico

		/*
		 * Parte final da arvore de acesso
		 */
		menu.append("p.draw();\n//--></script>\n");
		menu.append("</div>");

		// Retorna o javascript que monta a arvore de acesso
		return menu.toString();
	}

	/**
	 * Met�do respons�vel por validar o login e senha do usu�rio, verificando se
	 * o usu�rio existe no sistema.
	 * [UC0287] - Efetuar Login
	 * 
	 * @author Pedro Alexandre
	 * @date 04/07/2006
	 * @param login
	 * @param senha
	 * @return
	 * @throws ControladorException
	 */
	public Usuario validarUsuario(String login, String senha) throws ControladorException{

		// Vari�vel que vai armazenar o usu�rio logado
		Usuario retorno = null;

		// Cria o filtro de usu�rio
		FiltroUsuario filtroUsuario = new FiltroUsuario();

		// Busca o usu�rio por senha e login
		filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.LOGIN, login));
		filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.GERENCIA_REGIONAL);
		filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.LOCALIDADE_ELO);
		filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.LOCALIDADE);

		filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.USUARIO_SITUACAO);
		filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.USUARIO_TIPO);
		filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.UNIDADE_ORGANIZACIONAL);
		filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.FUNCIONARIO);
		filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.EMPRESA);

		// filtroUsuario.adicionarParametro(new
		// ParametroSimples(FiltroUsuario.SENHA,senha));
		try{
			// Criptografa a senha para compar�-la no banco de dados
			filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.SENHA, Criptografia.encriptarSenha(senha)));

		}catch(ErroCriptografiaException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.criptografia.senha");
		}

		// Faz a pesquisa
		Collection usuarioEncontrado = getControladorUtil().pesquisar(filtroUsuario, Usuario.class.getName());

		// Caso tenha encontrad o usu�rio no sistema com o login e a senha
		// informados
		// retorna o usu�rio para o casode uso que chamou a fun��o
		if(!usuarioEncontrado.isEmpty()){
			retorno = (Usuario) usuarioEncontrado.iterator().next();
		}

		// Retorna o usu�rio encontrado ou nulo se n�o encontrar
		return retorno;
	}

	/**
	 * Met�do respons�vel por registrar o acesso do usu�rio incrementando o n�
	 * de acessos e atualizando a data do ultimo acesso do usu�rio.
	 * [UC0287] - Efetuar Login
	 * 
	 * @author Pedro Alexandre
	 * @date 04/07/2006
	 * @param usuario
	 * @throws ControladorException
	 */
	public void registrarAcessoUsuario(Usuario usuario) throws ControladorException{

		// Seta o valor um para o n� de acessos
		int numeroAcesso = 1;

		// Caso n�o seja a primeira vez que o usu�rio tenha acessado o sistema
		// incrementa o n� de acesso + 1
		if(usuario.getNumeroAcessos() != null){
			numeroAcesso = (usuario.getNumeroAcessos().intValue() + numeroAcesso);
		}

		// Atualiza o n� de acessos do usu�rio
		usuario.setNumeroAcessos(new Integer(numeroAcesso));

		// Atualiza a data do �ltimo acesso do usu�rio
		Date data = new Date();
		usuario.setUltimoAcesso(data);

		// Chama o met�do para atualizar o usu�rio
		try{
			repositorioAcesso.atualizarRegistrarAcessoUsuario(usuario);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Met�do respons�vel por criar a arvore do menu com todas as permiss�es do
	 * usu�rio de acordo com os grupos que o usu�rio pertence.
	 * [UC0287] - Efetuar Login
	 * 
	 * @author Pedro Alexandre
	 * @date 04/07/2006
	 * @param permissoesUsuario
	 * @return
	 * @throws ControladorException
	 */
	public FuncionalidadeCategoria pesquisarArvoreFuncionalidades(Collection permissoesUsuario) throws ControladorException{

		// Cria a cole��o que vai armazenar as funcionalidade permitidas para o
		// usu�rio acessar
		Collection funcionalidadesPermitidasAcesso = new ArrayList();

		// Obt�m a lista de todas as funcionalidades do sistema
		Collection funcionalidades = null;

		FiltroFuncionalidade filtroFuncionalidade = new FiltroFuncionalidade();
		filtroFuncionalidade.adicionarParametro(new ParametroSimples(FiltroFuncionalidade.INDICADOR_PONTO_ENTRADA, ConstantesSistema.SIM));
		filtroFuncionalidade.setCampoOrderBy(FiltroFuncionalidade.NUMERO_ORDEM_MENU);
		filtroFuncionalidade.setConsultaSemLimites(true);

		// Pesquisa todas as funcionalidades cadastradas no sistema
		funcionalidades = getControladorUtil().pesquisar(filtroFuncionalidade, Funcionalidade.class.getName());

		// Cria o iterator das funcionalidades cadastradas no sistema
		Iterator iteratorFuncionalidadesPermissoes = funcionalidades.iterator();

		// La�o para criar o menu com as funcionalidades permitidas para o
		// usu�rio
		while(iteratorFuncionalidadesPermissoes.hasNext()){
			// Recupera a funcionalidade do iterator
			Funcionalidade funcionalidade = (Funcionalidade) iteratorFuncionalidadesPermissoes.next();

			// Usa a cole��o de permiss�es para eliminar as funcionalidades que
			// o usu�rio n�o
			// pode acessar
			Iterator iteratorPermissoes = permissoesUsuario.iterator();

			while(iteratorPermissoes.hasNext()){
				GrupoFuncionalidadeOperacao grupoFuncionalidadeOperacao = (GrupoFuncionalidadeOperacao) iteratorPermissoes.next();
				GrupoFuncionalidadeOperacaoPK chavePermissao = grupoFuncionalidadeOperacao.getComp_id();

				// Verifica se a funcionalidade tem o mesmo id da funcionalidade
				// e representa a mesma opera��o da permiss�o
				// para verificar se o usu�rio tem acesso
				if(funcionalidade.getId().equals(chavePermissao.getFuncionalidadeId())){
					// A permiss�o foi encontrada para esta funcionalidade e a
					// mesma entra na lista das permitidas
					funcionalidadesPermitidasAcesso.add(funcionalidade);
				}
			}
		}

		// Cria o iterator das funcionalidades permitidas para o usu�rio acessar
		Iterator iterator = funcionalidadesPermitidasAcesso.iterator();

		// Primeira Funcionalidade da arvore
		FuncionalidadeCategoria arvoreFuncionalidades = new FuncionalidadeCategoria("Menu");
		FuncionalidadeCategoria arvoreFuncionalidadesRetorno = arvoreFuncionalidades;

		// La�o para criar a arvore do menu
		while(iterator.hasNext()){
			// Recupera a funcionalidade
			Funcionalidade funcionalidadeInserir = (Funcionalidade) iterator.next();

			// Recupera o caminho do menu onde a funcionalidade vai ficar
			String caminho = funcionalidadeInserir.getCaminhoMenu();
			arvoreFuncionalidades = arvoreFuncionalidadesRetorno;

			// Cria a string para pegar os par�metros que est�o separados por /
			StringTokenizer stringTokenizer = new StringTokenizer(caminho, "/");

			// La�o para criar a string que constroi o menu
			while(stringTokenizer.hasMoreTokens()){

				// Recupera o nome da funcionalidade
				String nomeFuncionalidade = stringTokenizer.nextToken();

				FuncionalidadeCategoria funcionalidade = arvoreFuncionalidades.pesquisarCategoria(nomeFuncionalidade);
				// O caminho ainda n�o existe na arvore
				if(funcionalidade == null){
					funcionalidade = new FuncionalidadeCategoria(nomeFuncionalidade);

					// O caminho ainda n�o existe na arvore
					arvoreFuncionalidades.adicionarFuncionalidadeCategoria(funcionalidade);
				}
				arvoreFuncionalidades = funcionalidade;
			}
			// Adiciona a funcionalidade a arvore de funcionalidades permitidas
			arvoreFuncionalidades.adicionarFuncionalidadeCategoria(funcionalidadeInserir);
		}

		return arvoreFuncionalidadesRetorno;
	}

	/**
	 * Met�do respons�vel por atualizar as datas de expira��o do login do
	 * usu�rio assim como definir uma nova senha para o login
	 * [UC0289] Efetuar Altera��o da Senha
	 * 
	 * @author Pedro Alexandre
	 * @date 13/07/2006
	 * @param usuarioLogado
	 * @param dataNascimentoString
	 * @param cpf
	 * @param lembreteSenha
	 * @param novaSenha
	 * @param confirmacaoNovaSenha
	 * @throws ControladorException
	 */
	public void efetuarAlteracaoSenha(Usuario usuarioLogado, String dataNascimentoString, String cpf, String lembreteSenha,
					String novaSenha, String confirmacaoNovaSenha) throws ControladorException{

		// [UC0288] - Validar Nova Senha
		this.validarNovaSenha(usuarioLogado, dataNascimentoString, cpf, lembreteSenha, novaSenha, confirmacaoNovaSenha);

		/*
		 * Recupera os par�metros do sistema para recuperar o n� de dias da
		 * expira��o do acesso e o n� de dias de para a mensagem de expira��o
		 */
		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
		Short numeroDiasExpiracaoAcesso = sistemaParametro.getNumeroDiasExpiracaoAcesso();
		Short numeroDiasMensagemExpiracao = sistemaParametro.getNumeroDiasMensagemExpiracao();

		// Cria e recupera as datas necess�rias para verificar se altera��o de
		// senha � permitida
		Date dataAtual = new Date();
		Date dataInicioCadastro = usuarioLogado.getDataCadastroInicio();
		Date dataFimCadastro = usuarioLogado.getDataCadastroFim();
		Date dataExpiracaoAcesso = null;
		Date dataPrazoMensagemExpiracao = null;

		/*
		 * Caso o n� de dias para expira��o de acesso for nulo atribui zero a
		 * ele, a mesma coisa para o n� de dias da mensagem de expira��o
		 */
		if(numeroDiasExpiracaoAcesso == null){
			numeroDiasExpiracaoAcesso = 0;
		}
		if(numeroDiasMensagemExpiracao == null){
			numeroDiasMensagemExpiracao = 0;
		}

		/*
		 * Caso a data de inicio do cadastro esteja preenchida e a data atual
		 * mais o n� de dias para expirar for maior que a data de fim do
		 * cadastro a data de expira��o do acesso ser� a data fim do cadastro
		 * Caso contr�rio a data de expira��o vai ser adata atual mais o n� de
		 * dias de expira��o de acesso
		 */
		if(dataInicioCadastro != null && dataFimCadastro != null
						&& (Util.adicionarNumeroDiasDeUmaData(dataAtual, numeroDiasExpiracaoAcesso)).after(dataFimCadastro)){
			dataExpiracaoAcesso = dataFimCadastro;
		}else{
			dataExpiracaoAcesso = Util.adicionarNumeroDiasDeUmaData(dataAtual, numeroDiasExpiracaoAcesso);
		}

		/*
		 * Caso a data de inicioa do cadastro esteja preenchida e a data atual
		 * mas a diferen�a entre o n� de dias para expira��o e o n� de dias da
		 * mensagem de expira��o for maior que a data fim do cadastro a data do
		 * prazo para mensagem de expira��o vai ser a data fim do cadastro mais
		 * o n� de dias mensagem de expira��o Caso contr�rio a data para o prazo
		 * de expira��o da mensagem ser� a data atual mais a diferen�a entre o
		 * n� de dias para expira��o e o n� de dias da mensagem de expira��o
		 */
		if(dataInicioCadastro != null
						&& dataFimCadastro != null
						&& (Util.adicionarNumeroDiasDeUmaData(dataAtual, (numeroDiasExpiracaoAcesso - numeroDiasMensagemExpiracao)))
										.after(dataFimCadastro)){
			dataPrazoMensagemExpiracao = Util.subtrairNumeroDiasDeUmaData(dataFimCadastro, numeroDiasMensagemExpiracao);
		}else{
			dataPrazoMensagemExpiracao = Util.adicionarNumeroDiasDeUmaData(dataAtual,
							(numeroDiasExpiracaoAcesso - numeroDiasMensagemExpiracao));
		}

		// Valida a data de nascimento digitada
		Date dataNascimento = null;
		if(!Util.isVazioOuBranco(dataNascimentoString)){
			SimpleDateFormat dataFormato = new SimpleDateFormat("dd/MM/yyyy");
			try{
				dataNascimento = dataFormato.parse(dataNascimentoString);
			}catch(ParseException ex){
				throw new ControladorException("atencao.data.invalida", null, "Data de Nascimento");
			}
		}

		// Cria a situa�do usu�rio e setaseu valor para senha ativa
		UsuarioSituacao usuarioSituacao = new UsuarioSituacao();
		usuarioSituacao.setId(UsuarioSituacao.ATIVO);

		/*
		 * Criptografa a nova senha gerada para ser usada pelo usu�rio
		 */
		try{
			novaSenha = Criptografia.encriptarSenha(novaSenha);
		}catch(ErroCriptografiaException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.criptografia.senha");
		}

		// �ltimas senhas criptografadas do usu�rio para evitar repeti��es
		UsuarioSenha usuarioSenha = new UsuarioSenha();
		usuarioSenha.setUsuario(usuarioLogado);
		usuarioSenha.setNomeSenha(novaSenha);
		usuarioSenha.setUltimaAlteracao(new Date());
		getControladorUtil().inserir(usuarioSenha);

		// Atualiza os dados do usu�rio
		usuarioLogado.setSenha(novaSenha);
		usuarioLogado.setDataExpiracaoAcesso(dataExpiracaoAcesso);
		usuarioLogado.setDataPrazoMensagemExpiracao(dataPrazoMensagemExpiracao);
		usuarioLogado.setDataNascimento(dataNascimento);
		usuarioLogado.setCpf(cpf);
		usuarioLogado.setLembreteSenha(lembreteSenha);
		usuarioLogado.setUltimaAlteracao(new Date());
		usuarioLogado.setUsuarioSituacao(usuarioSituacao);

		// ------------ REGISTRAR TRANSA��O ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_EFETUAR_ALTERACAO_SENHA,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_EFETUAR_ALTERACAO_SENHA);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		usuarioLogado.setOperacaoEfetuada(operacaoEfetuada);
		usuarioLogado.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(usuarioLogado);
		// ------------ REGISTRAR TRANSA��O ----------------

		// Atualiza os dados do usu�rio
		getControladorUtil().atualizar(usuarioLogado);
	}

	/**
	 * Met�do respons�vel por validar todos os dados informados pelo usu�rio
	 * para cadastrar uma nova senha para o usu�rio.
	 * [UC0288] - Validar Nova Senha
	 * 
	 * @author Pedro Alexandre
	 * @date 13/07/2006
	 * @author Eduardo Henrique
	 * @date 30/05/2008
	 *       Altera��o para validar se o cpf informado (caso exista - [FS0008]) j� existe na base,
	 *       se o mesmo n�o for nulo
	 * @param usuarioLogado
	 * @param dataNascimentoString
	 * @param cpf
	 * @param lembreteSenha
	 * @param novaSenha
	 * @param confirmacaoNovaSenha
	 * @throws ControladorException
	 */
	public void validarNovaSenha(Usuario usuarioLogado, String dataNascimentoString, String cpf, String lembreteSenha, String novaSenha,
					String confirmacaoNovaSenha) throws ControladorException{

		// Recupera o login do usu�rio logado
		String login = usuarioLogado.getLogin();

		// [FS0005] - Verificar data de nascimento do login
		Date dataNascimentoUsuarioLogado = usuarioLogado.getDataNascimento();
		if(dataNascimentoUsuarioLogado != null){

			// [FS0003] - Validar Data
			Date dataNascimento = null;
			SimpleDateFormat dataFormato = new SimpleDateFormat("dd/MM/yyyy");
			try{
				dataNascimento = dataFormato.parse(dataNascimentoString);
			}catch(ParseException ex){
				throw new ControladorException("atencao.data.invalida", null, "Data de Nascimento");
			}

			// Recupera a data atual
			Date dataAtual = new Date();
			// [FS0004] - Verificar data maior ou igual a data corrente
			if(!dataNascimento.before(dataAtual)){
				throw new ControladorException("atencao.data_nascimento.anterior.dataatual", null, login, Util.formatarData(dataAtual));
			}

			if(dataNascimento.compareTo(dataNascimentoUsuarioLogado) != 0){
				throw new ControladorException("atencao.data_nascimento.incorreta.login", null, login);
			}
		}

		// [FS0008] - Verificar CPF do login
		// Recupera o CPF do usu�rio que est� logado e verifica
		// se � o mesmo que foi informado n� p�gina
		// Caso o usu�rio n�o tenha cadastrado o cpf verifica se existe
		// um outro usu�rio j� com esse cpf informado
		String cpfUsuarioLogado = usuarioLogado.getCpf();
		if(cpfUsuarioLogado != null && !cpfUsuarioLogado.trim().equals("")){
			if(!cpf.equals(cpfUsuarioLogado)){
				throw new ControladorException("atencao.cpf.incorreto.login", null, login);
			}
		}else if(cpf != null && !cpf.trim().equals("")){
			FiltroUsuario filtroUsuarioComCpf = new FiltroUsuario();
			filtroUsuarioComCpf.adicionarParametro(new ParametroSimples(FiltroUsuario.CPF, cpf));
			Collection colecaoUsuariosComCpf = getControladorUtil().pesquisar(filtroUsuarioComCpf, Usuario.class.getName());

			// Caso exista um usu�rio cadastrado com o cpf informado
			if(colecaoUsuariosComCpf != null && !colecaoUsuariosComCpf.isEmpty()){
				throw new ControladorException("atencao.cpf.jainformado.login", null, login);
			}
		}

		// [FS0011] - Validar confirma��o da nova senha
		if(!novaSenha.equals(confirmacaoNovaSenha)){
			throw new ControladorException("atencao.confirmacao.novasenha.invalida");
		}

		// [FS0010] - Validar Senha
		this.validarSenha(novaSenha);

		String quantidadeHistorico = ParametroGeral.P_SENHA_QUANTIDADE_HISTORICO.executar();
		if(quantidadeHistorico != null){
			Integer valor = Util.converterStringParaInteger(quantidadeHistorico);
			if(valor != null && valor.intValue() > 0){

				Collection<String> senhasAnteriores = null;
				try{
					senhasAnteriores = this.repositorioAcesso.pesquisarUsuarioSenha(usuarioLogado.getId(), valor);
				}catch(ErroRepositorioException e){
					sessionContext.setRollbackOnly();
					e.printStackTrace();
				}

				if(!Util.isVazioOrNulo(senhasAnteriores)){

					String senhaCriptografada = null;
					try{
						// Criptografa a nova senha gerada para ser usada pelo usu�rio
						senhaCriptografada = Criptografia.encriptarSenha(novaSenha);
					}catch(ErroCriptografiaException e){
						sessionContext.setRollbackOnly();
						throw new ControladorException("erro.criptografia.senha");
					}

					for(String senhaAnterior : senhasAnteriores){
						if(senhaAnterior.equals(senhaCriptografada)){
							throw new ControladorException("atencao.senha.invalida", null, "As �ltimas " + valor.intValue()
											+ " senhas n�o podem se repetir.");
						}
					}
				}
			}
		}

	}

	/**
	 * [UC0287] - Efetuar Login
	 * Met�do respons�vel por enviar uma nova senha para o e-mail do usu�rio com
	 * situa��o pendente
	 * [SB0002] - Lembrar senha
	 * 
	 * @author Pedro Alexandre
	 * @date 14/07/2006
	 * @author Virg�nia Melo
	 * @date 16/02/2009
	 *       Adicionado valida��o para emailDestinatario
	 * @param login
	 * @param cpf
	 * @param dataNascimentoString
	 * @throws ControladorException
	 */
	public void lembrarSenha(String login, String cpf, String dataNascimentoString) throws ControladorException{

		// [FS0006] - Validar data
		Date dataNascimento = null;
		SimpleDateFormat dataFormato = new SimpleDateFormat("dd/MM/yyyy");

		try{
			dataNascimento = dataFormato.parse(dataNascimentoString);
		}catch(ParseException ex){
			throw new ControladorException("atencao.data.invalida", null, "Data de Nascimento");
		}

		// [FS0007] Verificar data maior ou igual a data corrente
		Date dataAtual = new Date();
		if(!dataNascimento.before(dataAtual)){
			throw new ControladorException("atencao.data_nascimento.anterior.dataatual", null, login, Util.formatarData(dataAtual));
		}

		// Cria o filtro e pesquisa o usu�rio com o login informado
		FiltroUsuario filtroUsuario = new FiltroUsuario();
		filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.LOGIN, login));
		filtroUsuario.adicionarCaminhoParaCarregamentoEntidade("usuarioSituacao");
		Collection usuarios = this.getControladorUtil().pesquisar(filtroUsuario, Usuario.class.getName());

		// Recupera o usu�rio que est� solicitando o lembrete da senha
		Usuario usuarioLogado = (Usuario) usuarios.iterator().next();

		// [UC0008] - Verificar data de nascimento do login
		Date dataNascimentoUsuarioLogado = usuarioLogado.getDataNascimento();
		if(dataNascimentoUsuarioLogado != null && dataNascimento.compareTo(dataNascimentoUsuarioLogado) != 0){
			throw new ControladorException("atencao.data_nascimento.incorreta.login", null, login);
		}

		// [FS0012] - Verificar situa��o do usu�rio para lembrar senha
		UsuarioSituacao usuarioSituacao = usuarioLogado.getUsuarioSituacao();
		if(!usuarioSituacao.getId().equals(UsuarioSituacao.ATIVO)){
			throw new ControladorException("atencao.situacao_usuario.invalida.lembrar_senha", null, login, usuarioSituacao
							.getDescricaoUsuarioSituacao());
		}

		// Recupera o CPF do usu�rio que est� logado e verifica se � o mesmo que foi informado n�
		// p�gina
		String cpfUsuarioLogado = usuarioLogado.getCpf();
		if(cpfUsuarioLogado != null && !cpfUsuarioLogado.trim().equals("")){
			// [FS0010] - Verificar CPF do login
			if(!cpf.equals(cpfUsuarioLogado)){
				throw new ControladorException("atencao.cpf.incorreto.login", null, login);
			}
		}

		// Obt�m uma senha tempor�ria com 6 caracteres essa senha ser� atribuida ao usu�rio e sua
		// situa��o vai estar pendente
		String novaSenha;
		String senhaCriptografada;
		try{
			novaSenha = Util.geradorSenha(6);
			senhaCriptografada = Criptografia.encriptarSenha(novaSenha);
		}catch(ErroCriptografiaException e1){
			throw new ControladorException("erro.criptografia.senha");
		}

		// Atualiza os dados do usu�rio informando que a nova senha est� pendente
		usuarioSituacao = new UsuarioSituacao();
		usuarioSituacao.setId(UsuarioSituacao.PENDENTE_SENHA);
		usuarioLogado.setUsuarioSituacao(usuarioSituacao);
		usuarioLogado.setSenha(senhaCriptografada);
		usuarioLogado.setUltimaAlteracao(new Date());
		getControladorUtil().atualizar(usuarioLogado);

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

		// Trecho de c�digo respons�vel por enviar a nova senha do usu�rio para o email cadastrado
		String emailDestinatario = usuarioLogado.getDescricaoEmail();
		String emailRemetente = sistemaParametro.getDescricaoEmail();
		StringBuilder corpoEmail = new StringBuilder();
		corpoEmail.append("Login:" + login);
		corpoEmail.append(System.getProperty("line.separator"));
		corpoEmail.append("Senha:" + novaSenha);
		String assuntoEmail = "Solicita��o de senha";

		// Valida��o email destinat�rio
		if(emailDestinatario == null || emailDestinatario.trim().equals("")){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.envio_email");
		}

		try{
			ServicosEmail.enviarMensagem(emailRemetente, emailDestinatario, assuntoEmail, corpoEmail.toString());
		}catch(ErroEmailException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException(e.getMessage());
		}
	}

	/**
	 * [UC0288] - Validar nova senha
	 * Met�do que verifica se a senha informada est� de acordo com o padr�o de
	 * seguran�a adotado.
	 * [FS0011] - Validar senha
	 * 
	 * @author Pedro Alexandre
	 * @date 14/07/2006
	 * @param senha
	 * @throws ControladorException
	 */
	private void validarSenha(String senha) throws ControladorException{

		String quantidadeMinimaCaracteres = ParametroGeral.P_SENHA_QUANTIDADE_MINIMA_CARACTERES.executar();
		Integer quantidade = Util.converterStringParaInteger(quantidadeMinimaCaracteres);
		if(senha.length() < quantidade.intValue()){
			throw new ControladorException("atencao.senha.invalida", null, "Senha deve ter pelo menos " + quantidade.intValue()
							+ " caracteres.");
		}

		int contadorMinusculas = 0;
		int contadorMaiusculas = 0;
		int contadorNumeros = 0;
		int contadorEspeciais = 0;

		char[] caracteresSenha = senha.toCharArray();
		for(char caracter : caracteresSenha){

			if(Character.isLowerCase(caracter)){
				contadorMinusculas++;
			}else if(Character.isUpperCase(caracter)){
				contadorMaiusculas++;
			}else if(Character.isDigit(caracter)){
				contadorNumeros++;
			}else if(Util.isCaracterEspecial(String.valueOf(caracter))){
				contadorEspeciais++;
			}
		}

		String quantidadeMinimaLetrasMinusculas = ParametroGeral.P_SENHA_QUANTIDADE_MINIMA_LETRAS_MINUSCULAS.executar();
		if(quantidadeMinimaLetrasMinusculas != null){
			Integer valor = Util.converterStringParaInteger(quantidadeMinimaLetrasMinusculas);
			if(valor != null && valor.intValue() > contadorMinusculas){
				throw new ControladorException("atencao.senha.invalida", null, "Senha deve ter pelo menos " + valor.intValue()
								+ " letra(s) min�scula(s).");
			}
		}

		String quantidadeMinimaLetrasMaiusculas = ParametroGeral.P_SENHA_QUANTIDADE_MINIMA_LETRAS_MAIUSCULAS.executar();
		if(quantidadeMinimaLetrasMaiusculas != null){
			Integer valor = Util.converterStringParaInteger(quantidadeMinimaLetrasMaiusculas);
			if(valor != null && valor.intValue() > contadorMaiusculas){
				throw new ControladorException("atencao.senha.invalida", null, "Senha deve ter pelo menos " + valor.intValue()
								+ " letra(s) mai�scula(s).");
			}
		}

		String quantidadeMinimaNumeros = ParametroGeral.P_SENHA_QUANTIDADE_MINIMA_NUMEROS.executar();
		if(quantidadeMinimaNumeros != null){
			Integer valor = Util.converterStringParaInteger(quantidadeMinimaNumeros);
			if(valor != null && valor.intValue() > contadorNumeros){
				throw new ControladorException("atencao.senha.invalida", null, "Senha deve ter pelo menos " + valor.intValue()
								+ " n�mero(s).");
			}
		}

		String quantidadeMinimaCaracteresEspeciais = ParametroGeral.P_SENHA_QUANTIDADE_MINIMA_CARACTERES_ESPECIAIS.executar();
		if(quantidadeMinimaCaracteresEspeciais != null){
			Integer valor = Util.converterStringParaInteger(quantidadeMinimaCaracteresEspeciais);
			if(valor != null && valor.intValue() > contadorEspeciais){
				throw new ControladorException("atencao.senha.invalida", null, "Senha deve ter pelo menos " + valor.intValue()
								+ " caracter(es) especial(is).");
			}
		}

	}

	/**
	 * Verifica se uma url solicitada para o servidor � uma funcionalidade ou
	 * uma opera��o
	 * 
	 * @author Pedro Alexandre
	 * @date 18/07/2006
	 * @param url
	 * @return
	 * @throws ControladorException
	 */
	public Map<Integer, String> verificarTipoURL(String url) throws ControladorException{

		// Vari�vel que vai conter uma string indicando se a url � uma opera��o
		// ou uma funcionalidade
		Map<Integer, String> retorno = null;

		// Caso a url starte com "/"(barra) retira a barra da url
		if(url.startsWith("/")){
			url = url.substring(1);
		}

		// Cria o filtro para pesquisar a funcionalidade na base de dados pela
		// url informada
		FiltroFuncionalidade filtroFuncionalidade = new FiltroFuncionalidade();
		// filtroFuncionalidade.adicionarParametro(new
		// ParametroSimples(FiltroFuncionalidade.CAMINHO_URL,url));
		filtroFuncionalidade.adicionarParametro(new ComparacaoTexto(FiltroFuncionalidade.CAMINHO_URL, url));

		// Pesquisa a funcionalidade com a url
		Collection colecaoFuncionalidade = getControladorUtil().pesquisar(filtroFuncionalidade, Funcionalidade.class.getName());

		/*
		 * Caso exista funcionalidade cadastrada para esta url indica que a url
		 * � uma funcionalidade. Caso contr�rio verifica se a url � uma
		 * opera��o.
		 */
		if(colecaoFuncionalidade != null && !colecaoFuncionalidade.isEmpty()){
			Funcionalidade funcionalidadeAtual = (Funcionalidade) Util.retonarObjetoDeColecao(colecaoFuncionalidade);

			retorno = new HashMap<Integer, String>();
			retorno.put(funcionalidadeAtual.getId(), "funcionalidade");
		}else{
			// Cria o filtro de opera��o para verificar se a url � uma opera��o
			FiltroOperacao filtroOperacao = new FiltroOperacao();
			// filtroOperacao.adicionarParametro(new
			// ParametroSimples(FiltroOperacao.CAMINHO_URL,url));
			filtroOperacao.adicionarParametro(new ComparacaoTexto(FiltroOperacao.CAMINHO_URL, url));

			// Pesquisa a opera��o com a url
			Collection colecaoOperacao = getControladorUtil().pesquisar(filtroOperacao, Operacao.class.getName());

			/*
			 * Caso exista opera��o cadastrada para esta url indica que a url �
			 * uma opera��o
			 */
			if(colecaoOperacao != null && !colecaoOperacao.isEmpty()){
				Operacao operacaoAtual = (Operacao) Util.retonarObjetoDeColecao(colecaoOperacao);
				
				retorno = new HashMap<Integer, String>();
				retorno.put(operacaoAtual.getId(), "operacao");
			}
		}
		// Retorna uma string indicando se a url � uma funcionalidade ou
		// opera��o
		return retorno;
	}

	/**
	 * Met�do que verifica se o usu�rio tem permiss�o para acessar a
	 * funcionalidade que est� sendo requisitada (existe ocorr�ncia na tabela
	 * GrupoFuncionalidadeOperacao). Verifica se o(s) grupo(s) que o usu�rio
	 * pertence tem acesso a funcionalidade e se todas as opera��es desta
	 * funcionalidade n�o est�o com restri��es(existe ocorr�ncia na tabela
	 * UsuarioGrupoRestricao)
	 * 
	 * @author Pedro Alexandre
	 * @date 18/07/2006
	 * @param usuarioLogado
	 * @param urlFuncionalidade
	 * @param colecaoGruposUsuario
	 * @return
	 * @throws ControladorException
	 */
	public boolean verificarAcessoPermitidoFuncionalidade(Usuario usuarioLogado, String urlFuncionalidade, Collection colecaoGruposUsuario)
					throws ControladorException{

		// Cria a vari�vel que indica se o usu�rio tem ou n�o acesso a
		// funcionalidade
		boolean retorno = false;

		// Caso a url starte com "/"(barra) retira a barra da url
		if(urlFuncionalidade.startsWith("/")){
			urlFuncionalidade = urlFuncionalidade.substring(1);
		}

		// Cria o filtro para pesquisar a funcionalidade com a url informada
		FiltroFuncionalidade filtroFuncionalidade = new FiltroFuncionalidade();
		// filtroFuncionalidade.adicionarParametro(new
		// ParametroSimples(FiltroFuncionalidade.CAMINHO_URL,urlFuncionalidade));
		filtroFuncionalidade.adicionarParametro(new ComparacaoTexto(FiltroFuncionalidade.CAMINHO_URL, urlFuncionalidade));

		// Pesquisa a funcionalidade com a url informada
		Collection colecaoFuncionalidade = getControladorUtil().pesquisar(filtroFuncionalidade, Funcionalidade.class.getName());

		// Vari�vel que vai armazenar a funcionalidade pesquisada
		Funcionalidade funcionalidade = null;

		// Caso a pesquisa retorne a funcionalidade
		/*
		 * @Date 18/04/2008
		 * Inclu�do por Eduardo Henrique para atender � verifica��o das p�ginas de tabelas
		 * auxiliares
		 * antes havia um if, e n�o havia itera��o na cole��o
		 */
		for(Iterator iterator = colecaoFuncionalidade.iterator(); iterator.hasNext();){

			// Recupera a funcionalidade da cole��o
			funcionalidade = (Funcionalidade) iterator.next();

			// funcionalidade = (Funcionalidade) colecaoFuncionalidade.iterator()
			// .next();

			// Cria a vari�vel que vao armazenar as funcionalidades permitidas
			// para ser acessadas
			// com a opera�a� que est� sendo requisitada
			Collection<Funcionalidade> colecaoFuncionalidadesPermitidas = new ArrayList();
			colecaoFuncionalidadesPermitidas.add(funcionalidade);

			// Cria o filtro para verificar se existe permiss�o para acessar a
			// funcionalidade
			// existe ocorr�ncia na tabela GrupoFuncionalidadeOperacao
			FiltroGrupoFuncionalidadeOperacao filtroGrupoFuncionalidadeOperacao = new FiltroGrupoFuncionalidadeOperacao();

			// Cria o filtro para pesquisar se a funcionalidade � dependente de
			// alguma outra funcionalidade
			// para verificar se a opera�a� foi cadastrada para a funcionalidade
			// da propria opera��o
			// ou com uma funcionalidade principal com rela��o de depend�ncia
			// com a funcionalidade da propria opera��o
			FiltroFuncionalidadeDependencia filtroFuncionalidadeDependencia = new FiltroFuncionalidadeDependencia();
			filtroFuncionalidadeDependencia.adicionarParametro(new ParametroSimples(
							FiltroFuncionalidadeDependencia.FUNCIONALIDADE_DEPENDENCIA, funcionalidade.getId()));
			Collection colecaoFuncionalidadePrincipal = getControladorUtil().pesquisar(filtroFuncionalidadeDependencia,
							FuncionalidadeDependencia.class.getName());

			/*
			 * Caso a cole��o de funcionalidade principal n�o estiver vazia
			 * adiciona as funcionalidades no filtro para pesquisar as
			 * permiss�es
			 */
			if(colecaoFuncionalidadePrincipal != null && !colecaoFuncionalidadePrincipal.isEmpty()){
				Iterator iteratorFuncionalidadePrincipal = colecaoFuncionalidadePrincipal.iterator();

				// La�o para adicionar as funcionalidades principais a cole��o
				// de funcionalidades permitidas
				while(iteratorFuncionalidadePrincipal.hasNext()){
					FuncionalidadeDependencia funcionalidadeDependencia = (FuncionalidadeDependencia) iteratorFuncionalidadePrincipal
									.next();
					colecaoFuncionalidadesPermitidas.add(funcionalidadeDependencia.getFuncionalidade());
				}
			}

			// Cria um contador para auxiliar na cria��o dos filtros
			int cont = 1;

			/*
			 * Caso a cole��o de funcionalidades permitidas n�o esteja vazia
			 * adicona os ids das funcionalidades permitidas no filtro para
			 * pesquisar as permiss�es
			 */
			if(colecaoFuncionalidadesPermitidas != null && !colecaoFuncionalidadesPermitidas.isEmpty()){
				Iterator<Funcionalidade> iteratorFuncionalidadesPeritidas = colecaoFuncionalidadesPermitidas.iterator();

				// La�o para adicionar os ids das funcionalidades permitidas no
				// filtro
				while(iteratorFuncionalidadesPeritidas.hasNext()){
					// Recupera a funcionalidade do iterator
					Funcionalidade funcionalidadePermitida = iteratorFuncionalidadesPeritidas.next();

					/*
					 * Caso a cole��o possua uma �nica funcionalidade permitida
					 * para o usu�rio adiciona o id da cole��o no filtro sem
					 * nenhum conector Caso contr�rio verifica qual a posi��o do
					 * iterator para adicionar o id com o conector correto
					 */
					if(colecaoFuncionalidadesPermitidas.size() == 1){
						filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(
										FiltroGrupoFuncionalidadeOperacao.FUNCIONALIDADE_ID, funcionalidadePermitida.getId()));
					}else{
						// Caso seja a primeira funcionalidade adiciona o id com
						// o conector "OR"
						// e informa quantas funcionalidades vai ter para inseri
						// os parenteses
						if(cont == 1){
							filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(
											FiltroGrupoFuncionalidadeOperacao.FUNCIONALIDADE_ID, funcionalidadePermitida.getId(),
											FiltroParametro.CONECTOR_OR, colecaoFuncionalidadesPermitidas.size()));
							cont++;
						}else{
							/*
							 * Caso seja a �ltima funcionalidade da cole��o
							 * adiciona o id da funcionalidade sem conector Caso
							 * contr�rio adiciona o id da funcionalidade com o
							 * conector "OR"
							 */
							if(cont == colecaoFuncionalidadesPermitidas.size()){
								filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(
												FiltroGrupoFuncionalidadeOperacao.FUNCIONALIDADE_ID, funcionalidadePermitida.getId()));
								cont++;
							}else{
								filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(
												FiltroGrupoFuncionalidadeOperacao.FUNCIONALIDADE_ID, funcionalidadePermitida.getId(),
												ParametroSimples.CONECTOR_OR));
								cont++;
							}
						}
					}
				}
			}

			// Inicializa o contador para auxiliar no filtro de grupos
			cont = 1;

			// Cria o iteratorpara a cole��o de grupos do usu�rio logado
			Iterator iteratorGruposUsuario = colecaoGruposUsuario.iterator();

			// La�o para adicionar os grupos do usu�rio no filtro
			while(iteratorGruposUsuario.hasNext()){
				// Recupera o grupo do iterator
				Grupo grupoUsuario = (Grupo) iteratorGruposUsuario.next();

				/*
				 * Caso a cole��o possua um �nico grupo para o usu�rio adiciona
				 * o id do grupo no filtro sem nenhum conector Caso contr�rio
				 * verifica qual a posi��o do iterator para adicionar o id com o
				 * conector correto
				 */
				if(colecaoGruposUsuario.size() == 1){
					filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(FiltroGrupoFuncionalidadeOperacao.GRUPO_ID,
									grupoUsuario.getId()));
				}else{
					// Caso seja o primeiro grupo adiciona o id com o conector
					// "OR"
					// e informa quantos grupos vai ter para inserir os
					// parenteses
					if(cont == 1){
						filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(
										FiltroGrupoFuncionalidadeOperacao.GRUPO_ID, grupoUsuario.getId(), FiltroParametro.CONECTOR_OR,
										colecaoGruposUsuario.size()));
						cont++;
					}else{
						/*
						 * Caso seja o �ltimo grupo da cole��o adiciona o id do
						 * grupo sem conector Caso contr�rio adiciona o id
						 * dogrupo com o conector "OR"
						 */
						if(cont == colecaoGruposUsuario.size()){
							filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(
											FiltroGrupoFuncionalidadeOperacao.GRUPO_ID, grupoUsuario.getId()));
							cont++;
						}else{
							filtroGrupoFuncionalidadeOperacao
											.adicionarParametro(new ParametroSimples(FiltroGrupoFuncionalidadeOperacao.GRUPO_ID,
															grupoUsuario.getId(), ParametroSimples.CONECTOR_OR));
							cont++;
						}
					}
				}
			}

			/*
			 * Cria o filtro para pesquisar as opera��oes da funcionalidade
			 * requisitada para verificar se o usu�rio tem acesso a alguma
			 * opera��o da funcionalidade
			 */
			FiltroOperacao filtroOperacao = new FiltroOperacao();
			filtroOperacao.adicionarParametro(new ParametroSimples(FiltroOperacao.FUNCIONALIDADE_ID, funcionalidade.getId()));
			Collection colecaoOperacoesFuncionalidade = getControladorUtil().pesquisar(filtroOperacao, Operacao.class.getName());

			// Inicializa o contador para auxiliar no filtro de opera��es
			cont = 1;

			// Cria o iterator das opera��es
			Iterator iteratorOperacoesFuncionalidade = colecaoOperacoesFuncionalidade.iterator();

			// La�o para adicionar as opera��es da funcionalidade no filtro
			while(iteratorOperacoesFuncionalidade.hasNext()){
				// Recupera a opera��o da funcionalidade
				Operacao operacaoFuncionalidade = (Operacao) iteratorOperacoesFuncionalidade.next();

				/*
				 * Caso a cole��o possua uma �nica opera��o para a
				 * funcionalidade adiciona o id da opera��o no filtro sem nenhum
				 * conector Caso contr�rio verifica qual a posi��o do iterator
				 * para adicionar o id com o conector correto
				 */
				if(colecaoOperacoesFuncionalidade.size() == 1){
					filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(
									FiltroGrupoFuncionalidadeOperacao.OPERACAO_ID, operacaoFuncionalidade.getId()));
				}else{
					// Caso seja a primeira opera��o adiciona o id com o
					// conector "OR"
					// e informa quantas opera��es vai ter para inserir os
					// parenteses
					if(cont == 1){
						filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(
										FiltroGrupoFuncionalidadeOperacao.OPERACAO_ID, operacaoFuncionalidade.getId(),
										FiltroParametro.CONECTOR_OR, colecaoOperacoesFuncionalidade.size()));
						cont++;
					}else{
						/*
						 * Caso seja a �ltima opera��o da cole��o adiciona o id
						 * da opera��o sem conector Caso contr�rio adiciona o id
						 * da opera��o com o conector "OR"
						 */
						if(cont == colecaoOperacoesFuncionalidade.size()){
							filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(
											FiltroGrupoFuncionalidadeOperacao.OPERACAO_ID, operacaoFuncionalidade.getId()));
							cont++;
						}else{
							filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(
											FiltroGrupoFuncionalidadeOperacao.OPERACAO_ID, operacaoFuncionalidade.getId(),
											ParametroSimples.CONECTOR_OR));
							cont++;
						}
					}
				}
			}

			// Pesquisa se o usu�rio tem permiss�o para acessar a funcionalidade
			Collection permissoes = getControladorUtil().pesquisar(filtroGrupoFuncionalidadeOperacao,
							GrupoFuncionalidadeOperacao.class.getName());

			/*
			 * Caso o usu�rio tenha permiss�o para acessar a funcionalidade
			 * verifica se existe restri��o para todas as opera��es da
			 * funcionalidade
			 */
			if(permissoes != null && !permissoes.isEmpty()){

				/*
				 * Cria o filtro para pesquisar todas as restri��es do usu�rio
				 * seta o c�digo do usu�rio logado e o c�digo da funcionalidade
				 * no filtro
				 */
				FiltroUsuarioGrupoRestricao filtroUsuarioGrupoRestricao = new FiltroUsuarioGrupoRestricao();
				filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.USUARIO_ID, usuarioLogado
								.getId()));
				filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.FUNCIONALIDADE_ID,
								funcionalidade.getId()));

				/*
				 * Bloco de c�digo para montar o filtro com todos os grupos do
				 * usu�rio logado
				 */
				Iterator iteratorGruposUsuarioPermissoes = colecaoGruposUsuario.iterator();
				cont = 1;
				// La�o para inserir todos os grupos no filtro
				while(iteratorGruposUsuarioPermissoes.hasNext()){
					// Recupera o grupo do usu�rio
					Grupo grupoUsuario = (Grupo) iteratorGruposUsuarioPermissoes.next();
					/*
					 * Caso a cole��o de grupos de usu�rio tenha apenas um
					 * elemento adiciona o grupo no filtro sem o conector "OR"
					 * Caso contr�rio vai adicionar os grupos um a um com seus
					 * conectores
					 */
					if(colecaoGruposUsuario.size() == 1){
						filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.GRUPO_ID,
										grupoUsuario.getId()));
					}else{
						if(cont == 1){
							filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.GRUPO_ID,
											grupoUsuario.getId(), FiltroParametro.CONECTOR_OR, colecaoGruposUsuario.size()));
							cont++;
						}else{
							if(cont == colecaoGruposUsuario.size()){
								filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.GRUPO_ID,
												grupoUsuario.getId()));
								cont++;
							}else{
								filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.GRUPO_ID,
												grupoUsuario.getId(), ParametroSimples.CONECTOR_OR));
								cont++;
							}
						}
					}
				}
				// Fim do bloco para adicionar os grupos no filtro

				// Inicializa ocontador para auxiliar no filtro das permiss�es
				cont = 1;

				// Cria o iterator das permiss�es
				Iterator iteratorPermissoes = permissoes.iterator();

				// La�o para inserir todas as opera��es no filtro
				while(iteratorPermissoes.hasNext()){

					// Recupera a permiss�o do iterator
					GrupoFuncionalidadeOperacao grupoFuncionalidadeOperacao = (GrupoFuncionalidadeOperacao) iteratorPermissoes.next();

					/*
					 * Caso a cole��o de permiss�es de usu�rio tenha apenas um
					 * elemento adiciona a opera��o no filtro sem o conector
					 * "OR" Caso contr�rio vai adicionar as opera��es uma a uma
					 * com seus conectores
					 */
					if(permissoes.size() == 1){
						filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.OPERACAO_ID,
										grupoFuncionalidadeOperacao.getComp_id().getOperacaoId()));
					}else{
						if(cont == 1){
							filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.OPERACAO_ID,
											grupoFuncionalidadeOperacao.getComp_id().getOperacaoId(), FiltroParametro.CONECTOR_OR,
											permissoes.size()));
							cont++;
						}else{
							if(cont == colecaoGruposUsuario.size()){
								filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(
												FiltroUsuarioGrupoRestricao.OPERACAO_ID, grupoFuncionalidadeOperacao.getComp_id()
																.getOperacaoId()));
								cont++;
							}else{
								filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(
												FiltroUsuarioGrupoRestricao.OPERACAO_ID, grupoFuncionalidadeOperacao.getComp_id()
																.getOperacaoId(), ParametroSimples.CONECTOR_OR));
								cont++;
							}
						}
					}
				}
				// Fim do bloco para adicionar as opera��es no filtro

				// Pesquisa as restri��es do usu�rio
				Collection restricoes = getControladorUtil().pesquisar(filtroUsuarioGrupoRestricao, UsuarioGrupoRestricao.class.getName());

				/*
				 * Caso o n� de restri��es for menor que o n� de permiss�es seta
				 * a flag para indicar que o usu�rio tem acesso a funcionalidade
				 */
				if(restricoes.size() < permissoes.size()){
					retorno = true;

					/*
					 * Chama o met�do para registrar o acesso do usu�rio a
					 * funcionalidade na tabela UsuarioFavorito
					 */
					this.registrarFuncionalidadeAcessada(usuarioLogado, funcionalidade);
				}
			}
		}

		// Retorna uma flag indicando se o usu�rio tem acesso a funcionalidade
		return retorno;
	}

	/**
	 * Met�do que verifica se o usu�rio tem permiss�o para acessar a opera��o
	 * que est� sendo requisitada (existe ocorr�ncia na tabela
	 * GrupoFuncionalidadeOperacao). Verifica se o(s) grupo(s) que o usu�rio
	 * pertence tem acesso a opera��o e se a opera��o desta funcionalidade n�o
	 * est�o com restri��o(existe ocorr�ncia na tabela UsuarioGrupoRestricao)
	 * 
	 * @author Pedro Alexandre
	 * @date 18/07/2006
	 * @param usuarioLogado
	 * @param urlOperacao
	 * @param colecaoGruposUsuario
	 * @return
	 * @throws ControladorException
	 */
	public boolean verificarAcessoPermitidoOperacao(Usuario usuarioLogado, String urlOperacao, Collection colecaoGruposUsuario)
					throws ControladorException{

		// Cria a flag que vai indicar se o usu�rio tem acesso para opera��o ou
		// n�o
		boolean retorno = false;

		// Caso a url inicia com barra retira a barra da url
		if(urlOperacao.startsWith("/")){
			urlOperacao = urlOperacao.substring(1);
		}

		// Cria o filtro para pesquisar a opera��o da url informada
		// e carrega a funcionalidade da opera��o
		FiltroOperacao filtroOperacao = new FiltroOperacao();
		// filtroOperacao.adicionarParametro(new
		// ParametroSimples(FiltroOperacao.CAMINHO_URL,urlOperacao));
		filtroOperacao.adicionarParametro(new ComparacaoTexto(FiltroOperacao.CAMINHO_URL, urlOperacao));
		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade("funcionalidade");

		// Pesquisa a opera��o no sistema com a url informada
		Collection colecaoOperacao = getControladorUtil().pesquisar(filtroOperacao, Operacao.class.getName());

		// Cria as vari�veis que v�o armazenar a funcionalidade e a opera��o
		Funcionalidade funcionalidadeOperacao = null;
		Operacao operacao = null;

		/*
		 * Caso a cole��o de opera��es n�o esteja vazia pesquisa as permiss�es
		 * do usu�rio e as restri��es
		 */
		if(colecaoOperacao != null && !colecaoOperacao.isEmpty()){

			// Recupera a opera��o do iterator
			operacao = (Operacao) colecaoOperacao.iterator().next();

			// Recupera a funcionalidade da opera��o
			funcionalidadeOperacao = operacao.getFuncionalidade();

			// Cria a cole��o que vai armazenar as funcionalidades permitidas
			// para
			// cadastrar coma opera��o requerida
			Collection<Funcionalidade> colecaoFuncionalidadesPermitidas = new ArrayList();
			colecaoFuncionalidadesPermitidas.add(funcionalidadeOperacao);

			/*
			 * Cria o filtro de funcionalidades dep�ndencia para pesquisar se a
			 * funcionalidade da opera��o requerida pelo usu�rio tem alguma
			 * funcionalidade ligada a ela como principal
			 */
			FiltroFuncionalidadeDependencia filtroFuncionalidadeDependencia = new FiltroFuncionalidadeDependencia();
			filtroFuncionalidadeDependencia.adicionarCaminhoParaCarregamentoEntidade("funcionalidadeDependencia");
			filtroFuncionalidadeDependencia.adicionarCaminhoParaCarregamentoEntidade("funcionalidade");

			filtroFuncionalidadeDependencia.adicionarParametro(new ParametroSimples(
							FiltroFuncionalidadeDependencia.FUNCIONALIDADE_DEPENDENCIA, funcionalidadeOperacao.getId()));
			Collection colecaoFuncionalidadePrincipal = getControladorUtil().pesquisar(filtroFuncionalidadeDependencia,
							FuncionalidadeDependencia.class.getName());

			/*
			 * Caso a cole��o de funcionalidades principais n�o esteja vazia
			 * adiciona as funcionalidades a cole��o de funcionalidades
			 * permitidas
			 */
			if(colecaoFuncionalidadePrincipal != null && !colecaoFuncionalidadePrincipal.isEmpty()){
				Iterator iteratorFuncionalidadePrincipal = colecaoFuncionalidadePrincipal.iterator();

				// La�o para adicionar a funcionalidade a cole��o de
				// funcionalidades permitidas
				while(iteratorFuncionalidadePrincipal.hasNext()){
					FuncionalidadeDependencia funcionalidadeDependencia = (FuncionalidadeDependencia) iteratorFuncionalidadePrincipal
									.next();
					colecaoFuncionalidadesPermitidas.add(funcionalidadeDependencia.getFuncionalidade());
				}
			}

			// Cria o filtro para pesquisar as permiss�es do usu�rio para a
			// opera��o informada
			FiltroGrupoFuncionalidadeOperacao filtroGrupoFuncionalidadeOperacao = new FiltroGrupoFuncionalidadeOperacao();
			filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(FiltroGrupoFuncionalidadeOperacao.OPERACAO_ID,
							operacao.getId()));

			// Cria um contador para inserir os grupos do usu�rio no filtro
			int cont = 1;

			/*
			 * Caso a cole��o de funcionalidades permitidas n�o esteja vazia
			 * adicona os ids das funcionalidades permitidas no filtro para
			 * pesquisar as permiss�es para a opera��o
			 */
			if(colecaoFuncionalidadesPermitidas != null && !colecaoFuncionalidadesPermitidas.isEmpty()){

				// Cria o iterator das funcionalidades permitidas
				Iterator<Funcionalidade> iteratorFuncionalidadesPermitidas = colecaoFuncionalidadesPermitidas.iterator();

				// La�o para adicionar os ids das funcionalidades permitidas no
				// filtro
				while(iteratorFuncionalidadesPermitidas.hasNext()){
					// Recupera a funcionalidade do iterator
					Funcionalidade funcionalidadePermitida = iteratorFuncionalidadesPermitidas.next();

					/*
					 * Caso a cole��o possua uma �nica funcionalidade permitida
					 * para o usu�rio adiciona o id da cole��o no filtro sem
					 * nenhum conector Caso contr�rio verifica qual a posi��o do
					 * iterator para adicionar o id com o conector correto
					 */
					if(colecaoFuncionalidadesPermitidas.size() == 1){
						filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(
										FiltroGrupoFuncionalidadeOperacao.FUNCIONALIDADE_ID, funcionalidadePermitida.getId()));
					}else{
						// Caso seja a primeira funcionalidade adiciona o id com
						// o conector "OR"
						// e informa quantas funcionalidades vai ter para inseri
						// os parenteses
						if(cont == 1){
							filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(
											FiltroGrupoFuncionalidadeOperacao.FUNCIONALIDADE_ID, funcionalidadePermitida.getId(),
											FiltroParametro.CONECTOR_OR, colecaoFuncionalidadesPermitidas.size()));
							cont++;
						}else{
							/*
							 * Caso seja a �ltima funcionalidade da cole��o
							 * adiciona o id da funcionalidade sem conector Caso
							 * contr�rio adiciona o id da funcionalidade com o
							 * conector "OR"
							 */
							if(cont == colecaoFuncionalidadesPermitidas.size()){
								filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(
												FiltroGrupoFuncionalidadeOperacao.FUNCIONALIDADE_ID, funcionalidadePermitida.getId()));
								cont++;
							}else{
								filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(
												FiltroGrupoFuncionalidadeOperacao.FUNCIONALIDADE_ID, funcionalidadePermitida.getId(),
												ParametroSimples.CONECTOR_OR));
								cont++;
							}
						}
					}
				}
			}

			// Inicializa o contador para auxiliar com a cole��o de grupos
			cont = 1;

			// Cria oa iterator dos grupos do usu�rio
			Iterator iteratorGruposUsuario = colecaoGruposUsuario.iterator();

			// La�o para adicionar os ids dos grupos no filtro
			while(iteratorGruposUsuario.hasNext()){
				// Recupera o grupo da cole��o
				Grupo grupoUsuario = (Grupo) iteratorGruposUsuario.next();

				/*
				 * Caso a cole��o de grupos do usu�rio n�o esteja vazia adicona
				 * os ids dos grupos do usu�rio no filtro para pesquisar as
				 * permiss�es para a opera��o
				 */
				if(colecaoGruposUsuario.size() == 1){
					filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(FiltroGrupoFuncionalidadeOperacao.GRUPO_ID,
									grupoUsuario.getId()));
				}else{
					// Caso seja o primeiro grupo adiciona o id com o conector
					// "OR"
					// e informa quantos grupos vai ter para inserir os
					// parenteses
					if(cont == 1){
						filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(
										FiltroGrupoFuncionalidadeOperacao.GRUPO_ID, grupoUsuario.getId(), FiltroParametro.CONECTOR_OR,
										colecaoGruposUsuario.size()));
						cont++;
					}else{
						/*
						 * Caso seja o �ltimo grupo da cole��o adiciona o id do
						 * grupo sem conector Caso contr�rio adiciona o id do
						 * grupo com o conector "OR"
						 */
						if(cont == colecaoGruposUsuario.size()){
							filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(
											FiltroGrupoFuncionalidadeOperacao.GRUPO_ID, grupoUsuario.getId()));
							cont++;
						}else{
							filtroGrupoFuncionalidadeOperacao
											.adicionarParametro(new ParametroSimples(FiltroGrupoFuncionalidadeOperacao.GRUPO_ID,
															grupoUsuario.getId(), ParametroSimples.CONECTOR_OR));
							cont++;
						}
					}
				}
			}

			// Pesquisa as permiss�es do usu�rio
			Collection permissoes = getControladorUtil().pesquisar(filtroGrupoFuncionalidadeOperacao,
							GrupoFuncionalidadeOperacao.class.getName());

			/*
			 * Caso exista permiss�es para o usu�rio acessar a opera��o pesquisa
			 * as restri��es da opera��o para o usu�rio
			 */
			if(permissoes != null && !permissoes.isEmpty()){
				// Cria o filtro para pesquisar as restri��es do usu�rio
				// seta no filtro o c�digo do usu�rio da funcionalidade e da
				// opera��o
				FiltroUsuarioGrupoRestricao filtroUsuarioGrupoRestricao = new FiltroUsuarioGrupoRestricao();
				filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.USUARIO_ID, usuarioLogado
								.getId()));
				filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.OPERACAO_ID, operacao
								.getId()));

				// La�o para adicionar os ids dos grupos no filtro
				while(iteratorGruposUsuario.hasNext()){
					// Recupera o grupo da cole��o
					Grupo grupoUsuario = (Grupo) iteratorGruposUsuario.next();

					/*
					 * Caso a cole��o de grupos do usu�rio n�o esteja vazia
					 * adicona os ids dos grupos do usu�rio no filtro para
					 * pesquisar as restri��es para a opera��o
					 */
					if(colecaoGruposUsuario.size() == 1){
						filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.GRUPO_ID,
										grupoUsuario.getId()));
					}else{
						// Caso seja o primeiro grupo adiciona o id com o
						// conector "OR"
						// e informa quantos grupos vai ter para inserir os
						// parenteses
						if(cont == 1){
							filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.GRUPO_ID,
											grupoUsuario.getId(), FiltroParametro.CONECTOR_OR, colecaoGruposUsuario.size()));
							cont++;
						}else{
							/*
							 * Caso seja o �ltimo grupo da cole��o adiciona o id
							 * do grupo sem conector Caso contr�rio adiciona o
							 * id do grupo com o conector "OR"
							 */
							if(cont == colecaoGruposUsuario.size()){
								filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.GRUPO_ID,
												grupoUsuario.getId()));
								cont++;
							}else{
								filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.GRUPO_ID,
												grupoUsuario.getId(), ParametroSimples.CONECTOR_OR));
								cont++;
							}
						}
					}
				}

				// Inicializa o contador para auxiliar com a cole��o de
				// funcionalidades permitidas.
				cont = 1;

				// Caso a cole��o de funcionalidades permitidas n�o esteja
				// vazia.
				if(colecaoFuncionalidadesPermitidas != null && !colecaoFuncionalidadesPermitidas.isEmpty()){
					// Cria o iterator das funcionalidades permitidas.
					Iterator<Funcionalidade> iteratorFuncionalidadesPermitidas = colecaoFuncionalidadesPermitidas.iterator();

					// La�o para adicionar os ids das funcionalidades permitidas
					// no filtro.
					while(iteratorFuncionalidadesPermitidas.hasNext()){
						// Recupera a funcionalidade do iterator
						Funcionalidade funcionalidadePermitida = iteratorFuncionalidadesPermitidas.next();

						/*
						 * Caso a cole��o possua uma �nica funcionalidade
						 * permitida para o usu�rio adiciona o id da cole��o no
						 * filtro sem nenhum conector. Caso contr�rio verifica
						 * qual a posi��o do iterator para adicionar o id com o
						 * conector correto para pesquisar as restri��es da
						 * opera��o.
						 */
						if(colecaoFuncionalidadesPermitidas.size() == 1){
							filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(
											FiltroUsuarioGrupoRestricao.FUNCIONALIDADE_ID, funcionalidadePermitida.getId()));
						}else{
							// Caso seja a primeira funcionalidade adiciona o id
							// com o conector "OR"
							// e informa quantas funcionalidades vai ter para
							// inserir os parenteses.
							if(cont == 1){
								filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(
												FiltroUsuarioGrupoRestricao.FUNCIONALIDADE_ID, funcionalidadePermitida.getId(),
												FiltroParametro.CONECTOR_OR, colecaoFuncionalidadesPermitidas.size()));
								cont++;
							}else{
								/*
								 * Caso seja a �ltima funcionalidade da cole��o
								 * adiciona o id da funcionalidade sem conector.
								 * Caso contr�rio adiciona o id do grupo com o
								 * conector "OR".
								 */
								if(cont == colecaoFuncionalidadesPermitidas.size()){
									filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(
													FiltroUsuarioGrupoRestricao.FUNCIONALIDADE_ID, funcionalidadePermitida.getId()));
									cont++;
								}else{
									filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(
													FiltroUsuarioGrupoRestricao.FUNCIONALIDADE_ID, funcionalidadePermitida.getId(),
													ParametroSimples.CONECTOR_OR));
									cont++;
								}
							}
						}
					}
				}

				// Pesquisa as restri��es do usu�rio para a opera��o solicitada
				Collection restricoes = getControladorUtil().pesquisar(filtroUsuarioGrupoRestricao, UsuarioGrupoRestricao.class.getName());

				/*
				 * Caso o n� de restri��es for menor que o n� de permiss�es seta
				 * a flag para indicar que o usu�rio tem acesso a opera��o
				 */
				if(restricoes.size() < permissoes.size()){
					retorno = true;
				}
			}
		}

		// Retorna uma flag indicando se o usu�rio tem acesso a opera��o
		return retorno;
	}

	/**
	 * [UC0285] - Manter Opera��o
	 * Met�do respons�vel por atualizar uma opera��o no sistema e os
	 * relacionamentos entre a tabela e a opera��o
	 * [SB0001] - Atualizar Opera��o
	 * 
	 * @author Pedro Alexandre
	 * @date 02/08/2006
	 * @param operacao
	 * @param colecaoOperacaoTabela
	 * @throws ControladorException
	 */
	public void atualizarOperacao(Operacao operacao, Collection<OperacaoTabela> colecaoOperacaoTabela, Usuario usuarioLogado)
					throws ControladorException{

		/*
		 * [FS0009] - Verificar preenchimento dos campos
		 */
		if(operacao.getDescricao() == null || operacao.getDescricao().trim().equals("")){
			throw new ControladorException("atencao.naoinformado", null, "Descri��o");
		}

		if(operacao.getDescricaoAbreviada() == null || operacao.getDescricaoAbreviada().trim().equals("")){
			throw new ControladorException("atencao.naoinformado", null, "Descri��o Abreviada");
		}

		if(operacao.getCaminhoUrl() == null || operacao.getCaminhoUrl().trim().equals("")){
			throw new ControladorException("atencao.naoinformado", null, "Caminho URL");
		}

		if(operacao.getFuncionalidade() == null){
			throw new ControladorException("atencao.naoinformado", null, "Funcionalidade");
		}

		if(operacao.getOperacaoTipo() == null){
			throw new ControladorException("atencao.naoinformado", null, "Tipo de Opera��o");
		}

		if(operacao.getIndicadorAuditoria() != ConstantesSistema.SIM.intValue()
						&& operacao.getIndicadorAuditoria() != ConstantesSistema.NAO.intValue()){
			throw new ControladorException("atencao.naoinformado", null, "Indicador de Auditoria");
		}

		/*
		 * [FS0010] - Atualiza��o realizada por outro usu�rio Pesquisa a
		 * opera��o no base de dados e verifica se a opera��o foi atualizada por
		 * outro usu�rio durante esta opera��ode remo��o
		 */
		FiltroOperacao filtroOperacao = new FiltroOperacao();
		filtroOperacao.adicionarParametro(new ParametroSimples(FiltroOperacao.ID, operacao.getId()));
		Collection colecaoOperacaoBase = getControladorUtil().pesquisar(filtroOperacao, Operacao.class.getName());
		if(colecaoOperacaoBase == null || colecaoOperacaoBase.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		// Recupera a opera��o na base de dados
		Operacao operacaoNaBase = (Operacao) colecaoOperacaoBase.iterator().next();

		/*
		 * Caso a data de ultima altera��o da opera��o na base for posterior a
		 * opera��o que vai ser removida levanta uma exce��o para o usu�rio
		 * informando que a opera��o foi atualizada por outro usu�rio durante a
		 * tentativa de remo��o
		 */
		if(operacaoNaBase.getUltimaAlteracao().after(operacao.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		/*
		 * [FS0001] - Verificar exist�ncia da descri��o verifica se j� existe
		 * uma opera��o cadastrada com a descri��o informada na base de dados
		 */
		filtroOperacao.limparListaParametros();
		String descricaoOperacaoNaBase = operacaoNaBase.getDescricao();
		if(!operacao.getDescricao().equalsIgnoreCase(descricaoOperacaoNaBase)){
			filtroOperacao.adicionarParametro(new ParametroSimples(FiltroOperacao.DESCRICAO, operacao.getDescricao()));
			Collection colecaoOperacaoComDescricao = getControladorUtil().pesquisar(filtroOperacao, Operacao.class.getName());
			if(colecaoOperacaoComDescricao != null && !colecaoOperacaoComDescricao.isEmpty()){
				throw new ControladorException("atencao.descricao_ja_existente", null, operacao.getDescricao() + "");
			}
		}

		/*
		 * [FS0002] - Verificar exist�ncia da url verifica se j� existe uma
		 * opera��o cadastrada com a url informada
		 */
		filtroOperacao.limparListaParametros();
		String urlOperacaoNaBase = operacaoNaBase.getCaminhoUrl();
		if(!operacao.getCaminhoUrl().equalsIgnoreCase(urlOperacaoNaBase)){
			filtroOperacao.adicionarParametro(new ParametroSimples(FiltroOperacao.CAMINHO_URL, operacao.getCaminhoUrl()));
			Collection colecaoOperacaoComURL = getControladorUtil().pesquisar(filtroOperacao, Operacao.class.getName());
			if(colecaoOperacaoComURL != null && !colecaoOperacaoComURL.isEmpty()){
				throw new ControladorException("atencao.url_ja_existente", null, operacao.getCaminhoUrl() + "");
			}
		}

		/*
		 * [FS0004 - Verificar exist�ncia da funcionalidade] Cria o filtro de
		 * funcionalidade para verificar se existe a funcionalidade informada
		 */
		FiltroFuncionalidade filtroFuncionalidade = new FiltroFuncionalidade();
		filtroFuncionalidade.adicionarParametro(new ParametroSimples(FiltroFuncionalidade.ID, operacao.getFuncionalidade().getId()));
		Collection colecaoFuncionalidade = getControladorUtil().pesquisar(filtroFuncionalidade, Funcionalidade.class.getName());

		// Caso a funcionalidade informada n�o esteja cadastrada no sistema
		// levanta uma exce��o para o cliente
		if(colecaoFuncionalidade == null || colecaoFuncionalidade.isEmpty()){
			throw new ControladorException("atencao.funcionalidade.inexistente", null, operacao.getDescricao());
		}

		// Cria a vari�vel que vai aramzenar o tipo da opera��o
		OperacaoTipo operacaoTipo = null;

		/*
		 * Caso o tipo da opera��o tenha sido informada pesquisa o tipo da
		 * opera��o no sistema Caso contr�rio levanta uma exce��o indicando que
		 * o tipo da opera��o n�o foi informada
		 */
		if(operacao.getOperacaoTipo() != null){
			FiltroOperacaoTipo filtroOperacaoTipo = new FiltroOperacaoTipo();
			filtroOperacaoTipo.adicionarParametro(new ParametroSimples(FiltroOperacaoTipo.ID, operacao.getOperacaoTipo().getId()));
			Collection colecaoOperacaoTipo = getControladorUtil().pesquisar(filtroOperacaoTipo, OperacaoTipo.class.getName());

			/*
			 * Caso o tipo da opera��o informada n�o exista levanta uma exce��o
			 * indicando que o tipo da opera��o n�o existe Caso contr�rio
			 * recupera o tipo da opera��o da cole��o pesquisada
			 */
			if(colecaoOperacaoTipo == null || colecaoOperacaoTipo.isEmpty()){
				throw new ControladorException("atencao.operacao_tipo.inexistente", null, "" + operacao.getOperacaoTipo().getId());
			}else{
				operacaoTipo = (OperacaoTipo) Util.retonarObjetoDeColecao(colecaoOperacaoTipo);
			}
		}else{
			throw new ControladorException("atencao.operacao_tipo.nao.informado", null);
		}

		// Caso o tipo da opera��o informada seja pesquisar
		// verifica o preenchimento do argumento de pesquisa
		if(operacaoTipo.getId().intValue() == OperacaoTipo.PESQUISAR.intValue()){

			// Caso o argumento de pesquisa n�o tenha sido informado
			// levanta uma exce��o indicando que o argumento de pesquisa n�o foi
			// informado
			if(operacao.getTabelaColuna() == null){
				throw new ControladorException("atencao.argumento_pesquisa.nao.informado", null);
			}else{
				// [FS0005 - Verificar exist�ncia do argumento de pesquisa]
				// Cria o filtro para pesqusiar o argumento de pesquisa
				// informado
				FiltroTabelaColuna filtroTabelaColuna = new FiltroTabelaColuna();
				filtroTabelaColuna.adicionarParametro(new ParametroSimples(FiltroTabelaColuna.ID, operacao.getTabelaColuna().getId()));

				// Pesquisa o argumento de pesquisa
				Collection colecaoTabelaColuna = getControladorUtil().pesquisar(filtroTabelaColuna, TabelaColuna.class.getName());

				/*
				 * Caso o argumento de pesquisa n�o esteja cadastrado levanta
				 * uma exce��o indicando que o argumento de pesquisa n�o existe
				 * Caso contr�rio recupera o argumento de pesquisa da cole��o
				 */
				if(colecaoTabelaColuna == null || colecaoTabelaColuna.isEmpty()){
					throw new ControladorException("atencao.argumento_pesquisa.inexistente", null);
				}else{
					// [FS0013 - Verificar argumento de pesquisa]
					TabelaColuna argumentoPesquisa = (TabelaColuna) Util.retonarObjetoDeColecao(colecaoTabelaColuna);

					/*
					 * Caso o argumento de pesquisa informado n�o seja chave
					 * prim�ria levanta uma exce�a� indicando que o argumento de
					 * pesquisa n�o � chave prim�ria da tabela
					 */
					if(argumentoPesquisa.getIndicadorPrimaryKey() == ConstantesSistema.NAO){
						throw new ControladorException("atencao.argumento_pesquisa.nao.chave.primaria", null);
					}

					// Cria o filtro para verificar se j� existe opera��o com o
					// argumento de pesquisa informado
					FiltroOperacao filtroOperacaoComArgumentoPesquisa = new FiltroOperacao();
					filtroOperacaoComArgumentoPesquisa.adicionarParametro(new ParametroSimples(FiltroOperacao.TABELA_COLUNA_ID,
									argumentoPesquisa.getId()));
					Collection colecaoOperacaoComArgumentoPesquisa = getControladorUtil().pesquisar(filtroOperacaoComArgumentoPesquisa,
									Operacao.class.getName());

					/*
					 * Caso j� existe opera��o com o argumento de pesquisa
					 * informado levanta uma exce��o indicando que j� existe uma
					 * opera��o com o argumento de pesquisa informado
					 */
					if(colecaoOperacaoComArgumentoPesquisa != null && !colecaoOperacaoComArgumentoPesquisa.isEmpty()){
						Operacao operacaoComArgumentoPesquisa = (Operacao) Util.retonarObjetoDeColecao(colecaoOperacaoComArgumentoPesquisa);
						throw new ControladorException("atencao.argumento_pesquisa.ja.associado", null, operacaoComArgumentoPesquisa
										.getDescricao());
					}
				}
			}

		}else{
			// Caso o tipo de opera��o n�o seja "pesquisar"
			if(operacaoTipo.getIndicadorAtualiza() == ConstantesSistema.SIM){

				// Caso o usu�rio n�o tenha informado nenhuma tabela
				if(colecaoOperacaoTabela == null || colecaoOperacaoTabela.isEmpty()){
					throw new ControladorException("atencao.tabela.nao.informada", null);
				}

				// [FS0007 - Verificar exist�ncia da opera��o]
				if(operacao.getOperacaoPesquisa() != null){

					// Cria o filtro para pesquisar a opera��o de pesquisa
					// informada
					FiltroOperacao filtroOperacaoPesquisa = new FiltroOperacao();
					filtroOperacaoPesquisa.adicionarParametro(new ParametroSimples(FiltroOperacao.ID, operacao.getOperacaoPesquisa()
									.getId()));
					Collection colecaoOperacaoPesquisa = getControladorUtil().pesquisar(filtroOperacaoPesquisa, Operacao.class.getName());

					// Caso a opera��o de pesquisa n�o esteja cadastrada
					// levanta uma exce��o indicando que a opera��o de pesquisa
					// n�o existe
					if(colecaoOperacaoPesquisa == null || colecaoOperacaoPesquisa.isEmpty()){
						throw new ControladorException("atencao.operacao_pesquisa.inexistente", null);
					}
				}
			}
		}

		// ------------ REGISTRAR TRANSA��O ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_OPERACAO_ATUALIZAR,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacaoRegistrarTransacao = new Operacao();
		operacaoRegistrarTransacao.setId(Operacao.OPERACAO_OPERACAO_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacaoRegistrarTransacao);

		operacao.setOperacaoEfetuada(operacaoEfetuada);
		operacao.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(operacao);
		// ------------ REGISTRAR TRANSA��O ----------------

		// Seta a data de ultima altera��o e atualiza a opera��o
		operacao.setUltimaAlteracao(new Date());
		getControladorUtil().atualizar(operacao);

		/*
		 * Cria o filtro para recuperar os relacionamentos entre a opera��o e a
		 * tabela
		 */
		FiltroOperacaoTabela filtroOperacaoTabela = new FiltroOperacaoTabela();
		filtroOperacaoTabela.adicionarParametro(new ParametroSimples(FiltroOperacaoTabela.OPERACAO_ID, operacao.getId()));
		Collection<OperacaoTabela> colecaoOperacaoTabelaNaBase = getControladorUtil().pesquisar(filtroOperacaoTabela,
						OperacaoTabela.class.getName());

		/*
		 * Caso exista tabela relacionadas com a opera��o remove os
		 * relacionamentos da base caso o relacionamento tenha sido removido
		 * pelo usu�rio ou remove da cole��o que dos novos relacionamentos
		 * marcados caso j� exista na base
		 */
		if(colecaoOperacaoTabelaNaBase != null && !colecaoOperacaoTabelaNaBase.isEmpty() && colecaoOperacaoTabela != null
						&& !colecaoOperacaoTabela.isEmpty()){
			// Cria o iterator do relacionamento entre tabela e opera��o
			Iterator<OperacaoTabela> iteratorOperacaoTabelaNaBase = colecaoOperacaoTabelaNaBase.iterator();

			/*
			 * La�o para remover os relacionamentos entre tabela e opera��o que
			 * foram removidos pelo usu�rio
			 */
			while(iteratorOperacaoTabelaNaBase.hasNext()){
				// Recupera o relacionamento da base de dados do iterator
				OperacaoTabela operacaoTabelaNaBase = iteratorOperacaoTabelaNaBase.next();

				/*
				 * Caso a cole��o informada pelo usu�rio n�o tenha o
				 * relacionamento que est� na na base de dados remove o
				 * relacionamento da base de dados
				 */
				if(!colecaoOperacaoTabela.contains(operacaoTabelaNaBase)){
					// ------------ REGISTRAR TRANSA��O ----------------
					// operacaoTabelaNaBase.setOperacaoEfetuada(operacaoEfetuada);
					// operacaoTabelaNaBase.adicionarUsuario(usuarioLogado,
					// UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					// registradorOperacao.registrarOperacao(operacaoTabelaNaBase);
					// ------------ REGISTRAR TRANSA��O ----------------

					// Remove o relacionamento entre tabela e opera��o
					getControladorUtil().remover(operacaoTabelaNaBase);
				}else{
					colecaoOperacaoTabela.remove(operacaoTabelaNaBase);
				}
			}
		}

		/*
		 * Caso a cole��o de relacionamento entre tabela e opera��o ainda tenha
		 * algum relacionamento que foi informado pelo usu�rio e que ainda n�o
		 * esteja cadastrado na base de dados inseri o relacionamento na base de
		 * dados
		 */
		if(colecaoOperacaoTabela != null && !colecaoOperacaoTabela.isEmpty()){
			// Cria o iterator para inserir os relacionamentos entre tabela e
			// opera��o
			Iterator<OperacaoTabela> iteratorOperacaoTabelaInformado = colecaoOperacaoTabela.iterator();

			// La�o para inserir o relacionamento entre tabela e opera��o
			while(iteratorOperacaoTabelaInformado.hasNext()){
				// Recupera o relacionamento informado pelo usu�rio
				OperacaoTabela operacaoTabelaInformado = iteratorOperacaoTabelaInformado.next();

				// ------------ REGISTRAR TRANSA��O ----------------
				// operacaoTabelaInformado.setOperacaoEfetuada(operacaoEfetuada);
				// operacaoTabelaInformado.adicionarUsuario(usuarioLogado,
				// UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				// registradorOperacao.registrarOperacao(operacaoTabelaInformado);
				// ------------ REGISTRAR TRANSA��O ----------------

				// Inseri o relacionamento entre tabela e opera��o
				getControladorUtil().inserir(operacaoTabelaInformado);
			}
		}
	}

	/**
	 * [UC0285] - Manter Opera��o
	 * Met�do respons�vel por remover uma opera��o no sistema e os
	 * relacionamentos entre a tabela e a opera��o
	 * [SB0002] - Excluir Opera��o
	 * 
	 * @author Pedro Alexandre
	 * @date 02/08/2006
	 * @param idsOperacao
	 * @throws ControladorException
	 */
	public void removerOperacao(String[] idsOperacao, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSA��O ----------------

		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_OPERACAO_REMOVER, new UsuarioAcaoUsuarioHelper(
						usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacaoRegistrarTransacao = new Operacao();
		operacaoRegistrarTransacao.setId(Operacao.OPERACAO_OPERACAO_REMOVER);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacaoRegistrarTransacao);
		// ------------ REGISTRAR TRANSA��O ----------------

		// La�o para remover todas as opera��es selecionadas
		for(int i = 0; i < idsOperacao.length; i++){

			// Cria o filtro para pesquisar a opera��o que vai ser removida
			FiltroOperacao filtroOperacao = new FiltroOperacao();
			filtroOperacao.adicionarParametro(new ParametroSimples(FiltroOperacao.ID, idsOperacao[i]));
			Collection colecaoOperacao = getControladorUtil().pesquisar(filtroOperacao, Operacao.class.getName());

			/*
			 * Caso a pesquisa retorne a opera��o selecionada para remo��o
			 * recupera a opera��o da cole�a� de pesquisa para ser removida Caso
			 * contr�rio passa para a pr�xima opera��o no array
			 */
			if(colecaoOperacao != null && !colecaoOperacao.isEmpty()){
				// Recupera a opera��o que vai ser removida
				Operacao operacao = (Operacao) colecaoOperacao.iterator().next();

				// Cria o filtro para recuperar os relacionamentos entre a
				// opera��o e as tabelas da opera��o
				FiltroOperacaoTabela filtroOperacaoTabela = new FiltroOperacaoTabela();
				filtroOperacaoTabela.adicionarParametro(new ParametroSimples(FiltroOperacaoTabela.OPERACAO_ID, operacao.getId()));
				Collection colecaoOperacaoTabela = getControladorUtil().pesquisar(filtroOperacaoTabela, OperacaoTabela.class.getName());

				/*
				 * Caso exista relacionamentos entre opera��o e tabela
				 * cadastradas para a opera��o que vai ser removida, remove os
				 * relacionamentos antes de remover a opera��o
				 */
				if(colecaoOperacaoTabela != null && !colecaoOperacaoTabela.isEmpty()){

					// Coloca a cole��o de TabelaOPeracao no iterator
					Iterator<OperacaoTabela> iteratorOperacaoTabela = colecaoOperacaoTabela.iterator();

					// La�o para remover todos os relacionamentos TabelaOperacao
					while(iteratorOperacaoTabela.hasNext()){
						// Recupera o relacionamento da cole��o
						OperacaoTabela operacaoTabela = iteratorOperacaoTabela.next();

						// ------------ REGISTRAR TRANSA��O ----------------
						// operacaoTabela.setOperacaoEfetuada(operacaoEfetuada);
						// operacaoTabela.adicionarUsuario(usuarioLogado,
						// UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
						// registradorOperacao.registrarOperacao(operacaoTabela);
						// ------------ REGISTRAR TRANSA��O ----------------

						// Remove o relacionamento
						getControladorUtil().remover(operacaoTabela);
					}
				}

				// ------------ REGISTRAR TRANSA��O ----------------
				operacao.setOperacaoEfetuada(operacaoEfetuada);
				operacao.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(operacao);
				// ------------ REGISTRAR TRANSA��O ----------------

				// Remove a opera��o selecionada
				getControladorUtil().remover(operacao);
			}
		}
	}

	/**
	 * Met�do respons�vel por registrar a funcionalidade que o usu�rio est�
	 * acessando no momento
	 * 
	 * @author Pedro Alexandre
	 * @date 08/08/2006
	 * @param usuarioLogado
	 * @param funcionalidade
	 * @throws ControladorException
	 */
	private void registrarFuncionalidadeAcessada(Usuario usuarioLogado, Funcionalidade funcionalidade) throws ControladorException{

		// /*
		// * Caso a funcionalidade seja ponto de entrada registra o acesso a
		// * funcionalidade pelo usu�rio (incluir na tabela UsuarioFavorito)
		// */
		// if(funcionalidade.getIndicadorPontoEntrada().equals(ConstantesSistema.SIM)){
		//
		// // Recupera o n� m�ximo de funcionalidades registradas para �ltimos
		// // acessos
		// SistemaParametro sistemaParametros = getControladorUtil().pesquisarParametrosDoSistema();
		// int numeroMaximoFavorito = sistemaParametros.getNumeroMaximoFavorito();
		//
		// // Cria o objeto UsuarioFavorito que representa a funcionalidade
		// // acessada pelo usu�rio
		// UsuarioFavorito usuarioFavorito = new UsuarioFavorito(new
		// UsuarioFavoritoPK(usuarioLogado.getId(), funcionalidade.getId()),
		// new Short("1"), new Date(), funcionalidade, usuarioLogado);
		//
		// // Cria o filtro para pesquisar os �ltimos acessos do usu�rio
		// FiltroUsuarioFavorito filtroUsuarioFavoritoCadastrados = new FiltroUsuarioFavorito();
		// filtroUsuarioFavoritoCadastrados.adicionarParametro(new
		// ParametroSimples(FiltroUsuarioFavorito.USUARIO_ID, usuarioLogado
		// .getId()));
		// filtroUsuarioFavoritoCadastrados.adicionarParametro(new ParametroSimples(
		// FiltroUsuarioFavorito.INDICADOR_FAVORITO_ULTIMO_ACESSADO, ConstantesSistema.SIM));
		// filtroUsuarioFavoritoCadastrados.setCampoOrderBy(FiltroUsuarioFavorito.ULTIMA_ALTERACAO);
		// Collection<UsuarioFavorito> colecaoUsuarioFavoritosCadastrados =
		// getControladorUtil().pesquisar(
		// filtroUsuarioFavoritoCadastrados, UsuarioFavorito.class.getName());
		//
		// /*
		// * Caso a cole��o de ultimos acessos do usu�rio esteja vazia
		// * registra o primeiro acesso do usu�rio no sistema
		// */
		// if(colecaoUsuarioFavoritosCadastrados == null ||
		// colecaoUsuarioFavoritosCadastrados.isEmpty()){
		// getControladorUtil().inserir(usuarioFavorito);
		// }else{
		// /*
		// * Caso j� exista acessos cadatrado para o usu�rio que est�
		// * logado e o n� de acessos registrados seja menor que o m�ximo
		// * permitido verifica se j� existe registro para a
		// * funcionalidade acessada se existir atualiza a data de �ltimo
		// * acesso se n�o existir inseri o registro na tabela
		// * UsuarioFavorito
		// */
		// if(colecaoUsuarioFavoritosCadastrados.size() < numeroMaximoFavorito){
		// if(!colecaoUsuarioFavoritosCadastrados.contains(usuarioFavorito)){
		// getControladorUtil().inserir(usuarioFavorito);
		// }else{
		// getControladorUtil().atualizar(usuarioFavorito);
		// }
		// }else{
		// /*
		// * Caso o n� de de acessos registrados seja igual ao n�
		// * m�ximo permitido verifica se a funcionalidade acessada j�
		// * est� registrada para este usu�rio Caso j� esteja
		// * registrada para o usu�rio atualiza apenas a data de
		// * �ltima altera��o Caso n�o esteja registrada remove o
		// * registro com menor data de �ltimo acesso e adiciona o
		// * novo acesso.
		// */
		// if(colecaoUsuarioFavoritosCadastrados.size() == numeroMaximoFavorito){
		// /*
		// * Caso o acesso feito pelo usu�rio logado n�o esteja
		// * cadastrado remove o acesso com a data de ultimo
		// * acesso menor
		// */
		// if(!colecaoUsuarioFavoritosCadastrados.contains(usuarioFavorito)){
		//
		// // Recupera o acesso que foi acessado mais
		// // antigamente para ser removido
		// UsuarioFavorito usuarioFavoritoUltimoAcessado =
		// colecaoUsuarioFavoritosCadastrados.iterator().next();
		// getControladorUtil().remover(usuarioFavoritoUltimoAcessado);
		//
		// // Inseri o acesso mais recente a funcionalidade
		// getControladorUtil().inserir(usuarioFavorito);
		// }else{
		// // Atualiza a data de ultimo acesso
		// getControladorUtil().atualizar(usuarioFavorito);
		// }
		// }else{
		// // Caso o n� de acessos for maior que o permitido
		// }
		// }
		// }
		// }
	}

	/**
	 * Met�do respons�vel por verificar se o usu�rio tem abrang�ncia sobre a
	 * opera��o e o n�vel de informa��o que est�o sendo informados.
	 * [UC0XXX] Verificar Acesso Abrang�ncia
	 * 
	 * @author Pedro Alexandre
	 * @date 08/11/2006
	 * @param abrangencia
	 * @return
	 * @throws ControladorException
	 */
	public boolean verificarAcessoAbrangencia(Abrangencia abrangencia) throws ControladorException{

		this.carregarAbrangencia(abrangencia);

		boolean retorno = true;

		UsuarioAbrangencia usuarioAbrangencia = abrangencia.getUsuario().getUsuarioAbrangencia();

		// DADOS INFORMADOS PARA EXECUTAR A OPERA��O
		GerenciaRegional gerenciaRegionalInformada = abrangencia.getGerenciaRegional();
		UnidadeNegocio unidadeNegocioInformada = abrangencia.getUnidadeNegocio();
		Localidade eloPoloInformado = abrangencia.getEloPolo();
		Localidade localidadeInformada = abrangencia.getLocalidade();

		// ABRANGENCIA DO USUARIO
		GerenciaRegional gerenciaRegionalUsuario = abrangencia.getUsuario().getGerenciaRegional();
		// UnidadeNegocio unidadeNegocioUsuario =
		// abrangencia.getUsuario().getUnidadeNegocio();
		Localidade eloPoloUsuario = abrangencia.getUsuario().getLocalidadeElo();
		Localidade localidadeUsuario = abrangencia.getUsuario().getLocalidade();

		Integer nivelAbrangencia = usuarioAbrangencia.getId();

		switch(nivelAbrangencia.intValue()){
			case UsuarioAbrangencia.ESTADO_INT:
				retorno = true;
				break;

			case UsuarioAbrangencia.GERENCIA_REGIONAL_INT:

				if(gerenciaRegionalInformada != null){
					if(gerenciaRegionalUsuario.getId().intValue() == gerenciaRegionalInformada.getId().intValue()){
						retorno = true;
					}else{
						retorno = false;
					}
				}else{
					retorno = false;
				}

				break;

			case UsuarioAbrangencia.UNIDADE_NEGOCIO_INT:
				if(unidadeNegocioInformada != null){
					if(unidadeNegocioInformada.getId().intValue() == unidadeNegocioInformada.getId().intValue()){
						retorno = true;
					}else{
						retorno = false;
					}
				}else{
					retorno = false;
				}

				break;

			case UsuarioAbrangencia.ELO_POLO_INT:
				if(eloPoloInformado != null){
					if(eloPoloUsuario.getId().intValue() == eloPoloInformado.getId().intValue()){
						retorno = true;
					}else{
						retorno = false;
					}
				}else{
					retorno = false;
				}
				break;

			case UsuarioAbrangencia.LOCALIDADE_INT:
				if(localidadeInformada != null){
					if(localidadeUsuario.getId().intValue() == localidadeInformada.getId().intValue()){
						retorno = true;
					}else{
						retorno = false;
					}
				}else{
					retorno = false;
				}
				break;

		}
		return retorno;
	}

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 13/11/2006
	 * @param abrangencia
	 */
	private void carregarAbrangencia(Abrangencia abrangencia) throws ControladorException{

		GerenciaRegional gerenciaRegional = abrangencia.getGerenciaRegional();
		UnidadeNegocio unidadeNegocio = abrangencia.getUnidadeNegocio();
		Localidade eloPolo = abrangencia.getEloPolo();
		Localidade localidade = abrangencia.getLocalidade();
		Imovel imovel = abrangencia.getImovel();
		SetorComercial setorComercial = abrangencia.getSetorComercial();
		Quadra quadra = abrangencia.getQuadra();

		String consulta = null;

		try{
			if(gerenciaRegional != null){
				// para ger�ncia n�o precisa carregar nada
			}else if(unidadeNegocio != null){
				consulta = "from UnidadeNegocio as unidadeNegocio " + "inner join fetch unidadeNegocio.gerenciaRegional gerenciaRegional "
								+ "where unidadeNegocio.id = " + unidadeNegocio.getId();

				UnidadeNegocio unidadeNegocioPesquisado = (UnidadeNegocio) this.repositorioAcesso.pesquisarObjetoAbrangencia(consulta);

				if(unidadeNegocioPesquisado != null && unidadeNegocioPesquisado.getGerenciaRegional() != null){
					abrangencia.setGerenciaRegional(unidadeNegocioPesquisado.getGerenciaRegional());
				}
			}else if(eloPolo != null){
				consulta = "from Localidade as elo " + "left join fetch elo.unidadeNegocio unidadeNegocio "
								+ "left join fetch unidadeNegocio.gerenciaRegional gerenciaRegional " + "where elo.localidade.id = "
								+ eloPolo.getId();

				Localidade eloPoloPesquisado = (Localidade) this.repositorioAcesso.pesquisarObjetoAbrangencia(consulta);

				if(eloPoloPesquisado != null && eloPoloPesquisado.getUnidadeNegocio() != null){
					abrangencia.setUnidadeNegocio(eloPoloPesquisado.getUnidadeNegocio());
					abrangencia.setGerenciaRegional(eloPoloPesquisado.getUnidadeNegocio().getGerenciaRegional());
				}
			}else if(localidade != null){
				consulta = "from Localidade as localidade " + "inner join fetch localidade.localidade elo "
								+ "left join fetch elo.unidadeNegocio unidadeNegocio "
								+ "left join fetch unidadeNegocio.gerenciaRegional gerenciaRegional " + "where localidade.id = "
								+ localidade.getId();

				Localidade localidadePesquisada = (Localidade) this.repositorioAcesso.pesquisarObjetoAbrangencia(consulta);

				if(localidadePesquisada != null && localidadePesquisada.getLocalidade() != null){

					abrangencia.setEloPolo(localidadePesquisada.getLocalidade());
					abrangencia.setUnidadeNegocio(localidadePesquisada.getLocalidade().getUnidadeNegocio());

					if(localidadePesquisada.getLocalidade().getUnidadeNegocio() != null){

						abrangencia.setGerenciaRegional(localidadePesquisada.getLocalidade().getUnidadeNegocio().getGerenciaRegional());
					}
				}
			}else if(imovel != null){
				consulta = "from Imovel as imovel " + "inner join fetch imovel.localidade localidade "
								+ "inner join fetch localidade.localidade elo " + "left join fetch elo.unidadeNegocio unidadeNegocio "
								+ "left join fetch unidadeNegocio.gerenciaRegional gerenciaRegional " + "where imovel.id = "
								+ imovel.getId();

				Imovel imovelPesquisado = (Imovel) this.repositorioAcesso.pesquisarObjetoAbrangencia(consulta);

				if(imovelPesquisado != null && imovelPesquisado.getLocalidade() != null
								&& imovelPesquisado.getLocalidade().getLocalidade() != null){
					abrangencia.setLocalidade(imovelPesquisado.getLocalidade());
					abrangencia.setEloPolo(imovelPesquisado.getLocalidade().getLocalidade());
					abrangencia.setUnidadeNegocio(imovelPesquisado.getLocalidade().getLocalidade().getUnidadeNegocio());

					if(imovelPesquisado.getLocalidade().getLocalidade().getUnidadeNegocio() != null){
						abrangencia.setGerenciaRegional(imovelPesquisado.getLocalidade().getLocalidade().getUnidadeNegocio()
										.getGerenciaRegional());
					}
				}
			}else if(setorComercial != null){
				consulta = "from SetorComercial as setorComercial " + "inner join fetch setorComercial.localidade localidade "
								+ "inner join fetch localidade.localidade elo " + "left join fetch elo.unidadeNegocio unidadeNegocio "
								+ "left join fetch unidadeNegocio.gerenciaRegional gerenciaRegional " + "where setorComercial.id = "
								+ setorComercial.getId();

				SetorComercial setorComercialPesquisado = (SetorComercial) this.repositorioAcesso.pesquisarObjetoAbrangencia(consulta);

				if(setorComercialPesquisado != null && setorComercialPesquisado.getLocalidade() != null
								&& setorComercialPesquisado.getLocalidade().getLocalidade() != null){
					abrangencia.setLocalidade(setorComercialPesquisado.getLocalidade());
					abrangencia.setEloPolo(setorComercialPesquisado.getLocalidade().getLocalidade());
					abrangencia.setUnidadeNegocio(setorComercialPesquisado.getLocalidade().getLocalidade().getUnidadeNegocio());

					if(setorComercialPesquisado.getLocalidade().getLocalidade().getUnidadeNegocio() != null){
						abrangencia.setGerenciaRegional(setorComercialPesquisado.getLocalidade().getLocalidade().getUnidadeNegocio()
										.getGerenciaRegional());
					}
				}
			}else if(quadra != null){
				consulta = "from Quadra as quadra " + "inner join fetch quadra.setorComercial setorComercial "
								+ "inner join fetch setorComercial.localidade localidade " + "inner join fetch localidade.localidade elo "
								+ "left join fetch elo.unidadeNegocio unidadeNegocio "
								+ "left join fetch unidadeNegocio.gerenciaRegional gerenciaRegional " + "where quadra.id = "
								+ quadra.getId();

				Quadra quadraPesquisada = (Quadra) this.repositorioAcesso.pesquisarObjetoAbrangencia(consulta);

				if(quadraPesquisada != null && quadraPesquisada.getSetorComercial() != null
								&& quadraPesquisada.getSetorComercial().getLocalidade() != null){
					abrangencia.setSetorComercial(quadraPesquisada.getSetorComercial());
					abrangencia.setLocalidade(quadraPesquisada.getSetorComercial().getLocalidade());
					abrangencia.setEloPolo(quadraPesquisada.getSetorComercial().getLocalidade().getLocalidade());
					abrangencia.setUnidadeNegocio(quadraPesquisada.getSetorComercial().getLocalidade().getLocalidade().getUnidadeNegocio());

					if(quadraPesquisada.getSetorComercial().getLocalidade().getLocalidade().getUnidadeNegocio() != null){
						abrangencia.setGerenciaRegional(quadraPesquisada.getSetorComercial().getLocalidade().getLocalidade()
										.getUnidadeNegocio().getGerenciaRegional());
					}
				}
			}/*
			 * else if(municipio != null){ consulta = "from Municipio as
			 * municipio " + "inner join fetch municipio.setorComercial
			 * setorComercial " + "inner join fetch
			 * setorComercial.localidade localidade " + "inner join fetch
			 * localidade.localidade elo " + "inner join fetch
			 * elo.unidadeNegocio unidadeNegocio " + "inner join fetch
			 * unidadeNegocio.gerenciaRegional gerenciaRegional " + "where
			 * municipio.id = " + municipio.getId();
			 * Municipio municipioPesquisada =
			 * (Municipio)repositorioAcesso.pesquisarObjetoAbrangencia(consulta);
			 * abrangencia.setSetorComercial(municipioPesquisada.getSetorComercial());
			 * abrangencia.setLocalidade(municipioPesquisada.getSetorComercial().getLocalidade());
			 * abrangencia.setEloPolo(municipioPesquisada.getSetorComercial().getLocalidade().
			 * getLocalidade());
			 * 
			 * abrangencia.setUnidadeNegocio(municipioPesquisada.getSetorComercial().getLocalidade().
			 * getLocalidade().getUnidadeNegocio());
			 * 
			 * abrangencia.setGerenciaRegional(municipioPesquisada.getSetorComercial().getLocalidade(
			 * ).getLocalidade().getUnidadeNegocio().getGerenciaRegional()); }
			 */

		}catch(ErroRepositorioException e){
			throw new ControladorException("Erro no Hibernate", e);
		}
	}

	/**
	 * [UC3043] Registrar Log de Execu��o do Processo
	 * Registra uma mensagem no log do processo em execu��o
	 * 
	 * @author Hugo Lima
	 * @date 19/12/2011
	 * @param idFuncionalidadeIniciada
	 * @param mensagem
	 * @throws ControladorException
	 */
	public void registrarLogExecucaoProcesso(Integer idFuncionalidadeIniciada, String mensagem) throws ControladorException{

		try{
			// 2. Caso exista o par�metro de sistema referente ao registro de log do processo (a
			// partir da tabela PARAMETRO_SISTEMA com PASI_CDPARAMETRO = �P_REGISTRO_LOG_PROCESSO�),
			// e seu valor seja (PASI_VLPARAMETRO) seja igual a 1
			if(ParametroBatch.P_REGISTRO_LOG_PROCESSO.executar().equals(ConstantesSistema.SIM.toString())){
				// Consulta a funcionalidade passada no parametro
				FiltroFuncionalidadeIniciada filtroFuncionalidadeIniciada = new FiltroFuncionalidadeIniciada();
				filtroFuncionalidadeIniciada.adicionarParametro(new ParametroSimples(FiltroFuncionalidadeIniciada.ID,
								idFuncionalidadeIniciada));

				Collection colecaoFuncionalidadeIniciada = Fachada.getInstancia().pesquisar(filtroFuncionalidadeIniciada,
								FuncionalidadeIniciada.class.getName());

				FuncionalidadeIniciada funcionalidadeIniciada = (FuncionalidadeIniciada) Util
								.retonarObjetoDeColecao(colecaoFuncionalidadeIniciada);

				// 2.1. O sistema adiciona a mensagem recebida no final do log do processo em
				// execu��o
				funcionalidadeIniciada.adicionarLinhaTextoLogExecucao(mensagem);
				// funcionalidadeIniciada.setTextoLogExecucao(IoUtil.transformarObjetoParaBytes(mensagem));

				this.getControladorUtil().atualizar(funcionalidadeIniciada);
			}
		}catch(Exception e){
			throw new ControladorException(e.getMessage());
		}

	}

	/**
	 * Consulta os dados de acesso do usu�rio ao M�dulo gest�o de Leitura
	 * 
	 * @author Felipe Rosacruz
	 * @date 25/03/2014
	 * @param usuariologado
	 * @throws ControladorException
	 */
	public Object[] consultarDadosAcessoGcsME(Usuario usuariologado) throws ControladorException{

		Object[] retorno = null;
		try{
			retorno = repositorioAcesso.consultarDadosAcessoGcsME(usuariologado);
		}catch(Exception ex){
			throw new ControladorException(ex.getMessage());
		}
		return retorno;
	}


}