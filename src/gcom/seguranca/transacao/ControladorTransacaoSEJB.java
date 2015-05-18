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

package gcom.seguranca.transacao;

import gcom.interceptor.Interceptador;
import gcom.interceptor.ObjetoTransacao;
import gcom.seguranca.acesso.*;
import gcom.seguranca.acesso.usuario.FiltroUsuarioAlteracao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.seguranca.acesso.usuario.UsuarioAlteracao;
import gcom.util.*;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * Definição da lógica de negócio do Session Bean de ControladorCliente
 * 
 * @author Sávio Luiz
 * @created 25 de Abril de 2005
 */
public class ControladorTransacaoSEJB
				implements SessionBean {

	private static final long serialVersionUID = 1L;

	private IRepositorioTransacao repositorioTransacao = null;

	SessionContext sessionContext;

	/**
	 * < <Descrição do método>>
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

		repositorioTransacao = RepositorioTransacaoHBM.getInstancia();
	}

	/**
	 * < <Descrição do método>>
	 */
	public void ejbRemove(){

	}

	/**
	 * < <Descrição do método>>
	 */
	public void ejbActivate(){

	}

	/**
	 * < <Descrição do método>>
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
	 * Retorna o valor de controladorUtil
	 * 
	 * @return O valor de controladorUtil
	 */
	private ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas à
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
	 * Método que consulta os usuario alteracao de uma determinada operacao com
	 * as restricoes passadas
	 * 
	 * @param idOperacao
	 * @param idUsuario
	 * @param dataInicial
	 * @param dataFinal
	 * @param horaInicial
	 * @param hotaFinal
	 * @param idTabela
	 * @param idTabelaColuna
	 * @param id1
	 * @return
	 * @throws ControladorException
	 * @author Romulo Aurelio
	 * @date 11/05/2007
	 */

	public Collection pesquisarUsuarioAlteracaoDasOperacoesEfetuadasHql(Integer idUsuarioAcao, String[] idOperacoes, String idUsuario,
					Date dataInicial, Date dataFinal, Date horaInicial, Date horaFinal, Hashtable<Integer, String> argumentos, Integer id1,
					Integer numeroPagina) throws ControladorException{

		try{
			if(null == idUsuarioAcao && null == idUsuario && null == dataInicial && null == dataFinal && null == horaInicial
							&& null == horaFinal && (null == argumentos || argumentos.isEmpty())
							&& (idOperacoes == null || idOperacoes.length == 0) && null == id1){
				throw new ControladorException("atencao.filtro.nenhum_parametro_informado");
			}

			/*
			 * Collection coll =
			 * repositorioTransacao.pesquisarUsuarioAlteracaoDasOperacoesEfetuadas(idUsuarioAcao,
			 * idOperacao, idUsuario, dataInicial, dataFinal, horaInicial,
			 * horaFinal, idTabela, idTabelaColuna,id1);
			 */

			Collection coll = repositorioTransacao.pesquisarUsuarioAlteracaoDasOperacoesEfetuadasHql(idUsuarioAcao, idOperacoes, idUsuario,
							dataInicial, dataFinal, horaInicial, horaFinal, argumentos, id1, numeroPagina);

			// para todas as operacoes efetuadas carregar a lista de
			// usuariosAlteracao
			if(coll != null && !coll.isEmpty()){
				Iterator it = coll.iterator();
				while(it.hasNext()){
					OperacaoEfetuada operacaoEfetuada = (OperacaoEfetuada) it.next();
					Set set = new HashSet();
					operacaoEfetuada.setUsuarioAlteracoes(set);

					// criando o filtrtro para consultar os usuarios
					FiltroUsuarioAlteracao filtroUsuarioAlteracao = new FiltroUsuarioAlteracao();
					filtroUsuarioAlteracao.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuarioAlteracao.USUARIO);
					filtroUsuarioAlteracao.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuarioAlteracao.USUARIO_ACAO);
					filtroUsuarioAlteracao.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuarioAlteracao.USUARIO_TIPO);
					filtroUsuarioAlteracao.adicionarParametro(new ParametroSimples(FiltroUsuarioAlteracao.OPERACAO_EFETUADA_ID,
									operacaoEfetuada.getId()));

					Collection usuarioalteracoes = getControladorUtil().pesquisar(filtroUsuarioAlteracao, UsuarioAlteracao.class.getName());
					// para todos os usuarios colocar na lista
					if(usuarioalteracoes != null && !usuarioalteracoes.isEmpty()){
						Iterator itt = usuarioalteracoes.iterator();
						while(itt.hasNext()){
							set.add(itt.next());
						}
					}
				}
			}

			return coll;
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Método que consulta os usuario alteracao de uma determinada operacao com
	 * as restricoes passadas
	 * 
	 * @param idOperacao
	 * @param idUsuario
	 * @param dataInicial
	 * @param dataFinal
	 * @param horaInicial
	 * @param hotaFinal
	 * @param idTabela
	 * @param idTabelaColuna
	 * @param id1
	 * @return
	 * @throws ControladorException
	 * @author Romulo Aurelio
	 * @date 11/05/2007
	 */

	public Collection pesquisarUsuarioAlteracaoDasOperacoesEfetuadasHqlRelatorio(Integer idUsuarioAcao, String[] idOperacoes,
					String idUsuario, Date dataInicial, Date dataFinal, Date horaInicial, Date horaFinal,
					Hashtable<Integer, String> argumentos, Integer id1) throws ControladorException{

		try{
			if(null == idUsuarioAcao && null == idUsuario && null == dataInicial && null == dataFinal && null == horaInicial
							&& null == horaFinal && null == argumentos && null == id1){
				throw new ControladorException("atencao.filtro.nenhum_parametro_informado");
			}

			/*
			 * Collection coll =
			 * repositorioTransacao.pesquisarUsuarioAlteracaoDasOperacoesEfetuadas(idUsuarioAcao,
			 * idOperacao, idUsuario, dataInicial, dataFinal, horaInicial,
			 * horaFinal, idTabela, idTabelaColuna,id1);
			 */

			Collection coll = repositorioTransacao.pesquisarUsuarioAlteracaoDasOperacoesEfetuadasHqlRelatorio(idUsuarioAcao, idOperacoes,
							idUsuario, dataInicial, dataFinal, horaInicial, horaFinal, argumentos, id1);

			// para todas as operacoes efetuadas carregar a lista de
			// usuariosAlteracao
			if(coll != null && !coll.isEmpty()){
				Iterator it = coll.iterator();
				while(it.hasNext()){
					OperacaoEfetuada operacaoEfetuada = (OperacaoEfetuada) it.next();
					Set set = new HashSet();
					operacaoEfetuada.setUsuarioAlteracoes(set);

					// criando o filtrtro para consultar os usuarios
					FiltroUsuarioAlteracao filtroUsuarioAlteracao = new FiltroUsuarioAlteracao();
					filtroUsuarioAlteracao.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuarioAlteracao.USUARIO);
					filtroUsuarioAlteracao.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuarioAlteracao.USUARIO_ACAO);
					filtroUsuarioAlteracao.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuarioAlteracao.USUARIO_TIPO);
					filtroUsuarioAlteracao.adicionarParametro(new ParametroSimples(FiltroUsuarioAlteracao.OPERACAO_EFETUADA_ID,
									operacaoEfetuada.getId()));

					Collection usuarioalteracoes = getControladorUtil().pesquisar(filtroUsuarioAlteracao, UsuarioAlteracao.class.getName());
					// para todos os usuarios colocar na lista
					if(usuarioalteracoes != null && !usuarioalteracoes.isEmpty()){
						Iterator itt = usuarioalteracoes.iterator();
						while(itt.hasNext()){
							set.add(itt.next());
						}
					}
				}
			}

			return coll;
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Método que registra uma operacao ao sistema
	 * 
	 * @param operacaoEfetuada
	 * @param tabelaLinhaAlteracao
	 * @param tabelaLinhaColunaAlteracoes
	 * @throws ControladorException
	 */
	public void inserirOperacaoEfetuada(Collection usuariosAcaoUsuarioHelp, OperacaoEfetuada operacaoEfetuada,
					TabelaLinhaAlteracao tabelaLinhaAlteracao, Collection<TabelaLinhaColunaAlteracao> tabelaLinhaColunaAlteracoes,
					String nomeObjeto) throws ControladorException{

		// caso a operacaoEfetuada for nula
		// Nao ocorre pq o intercepto levanta execao caso nao tenha
		if(operacaoEfetuada == null){
			operacaoEfetuada = new OperacaoEfetuada();
		}
		// caso a operacao da operacaoEfetuada for nula
		// Nao ocorre pq o interceptador levanta excecao caso nao tenha
		if(operacaoEfetuada.getOperacao() == null){
			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_AVISO_BANCARIO_ATUALIZAR);
			operacaoEfetuada.setOperacao(operacao);
		}

		if(operacaoEfetuada.getArgumento() == null){

			Integer idArgumento = this.pesquisarArgumento(operacaoEfetuada.getOperacao().getId(), nomeObjeto);

			if(idArgumento != null){
				Argumento argumento = new Argumento();
				argumento.setId(idArgumento);

				operacaoEfetuada.setArgumento(argumento);
			}
		}

		// caso nao tenha id é pq nao foi adicionado
		if(operacaoEfetuada.getId() == null){
			operacaoEfetuada.setUltimaAlteracao(new Date(System.currentTimeMillis()));
			Integer idOperacaoEfetuada = (Integer) getControladorUtil().inserirOuAtualizar(operacaoEfetuada);
			operacaoEfetuada.setId(idOperacaoEfetuada);

			if(usuariosAcaoUsuarioHelp != null && !usuariosAcaoUsuarioHelp.isEmpty()){
				Iterator it = usuariosAcaoUsuarioHelp.iterator();
				while(it.hasNext()){
					UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelp = (UsuarioAcaoUsuarioHelper) it.next();

					UsuarioAlteracao usuarioAteracao = new UsuarioAlteracao();
					usuarioAteracao.setOperacaoEfetuada(operacaoEfetuada);
					usuarioAteracao.setUltimaAlteracao(new Date(System.currentTimeMillis()));
					usuarioAteracao.setUsuario(usuarioAcaoUsuarioHelp.getUsuario());
					usuarioAteracao.setUsuarioAcao(usuarioAcaoUsuarioHelp.getUsuarioAcao());
					getControladorUtil().inserir(usuarioAteracao);
				}
			}

			// Inserir tabela linha alteracao principal -> usada para conseguir recuperar dados do
			// objeto principal

		}else if(operacaoEfetuada.getDadosAdicionais() != null){
			getControladorUtil().atualizar(operacaoEfetuada);
		}

		if(tabelaLinhaAlteracao != null){
			tabelaLinhaAlteracao.setUltimaAlteracao(new Date(System.currentTimeMillis()));
			tabelaLinhaAlteracao.setOperacaoEfetuada(operacaoEfetuada);
			getControladorUtil().inserir(tabelaLinhaAlteracao);

			FiltroOperacaoTabela filtroOperacaoTabela = new FiltroOperacaoTabela();
			filtroOperacaoTabela.adicionarParametro(new ParametroSimples(FiltroOperacaoTabela.TABELA_ID, tabelaLinhaAlteracao.getTabela()
							.getId()));
			filtroOperacaoTabela.adicionarParametro(new ParametroSimples(FiltroOperacaoTabela.OPERACAO_ID, operacaoEfetuada.getOperacao()
							.getId()));
			Collection coll = getControladorUtil().pesquisar(filtroOperacaoTabela, OperacaoTabela.class.getName());
			if(coll == null || coll.isEmpty()){

				OperacaoTabelaPK pk = new OperacaoTabelaPK();
				pk.setOperacaoId(operacaoEfetuada.getOperacao().getId());
				pk.setTabelaId(tabelaLinhaAlteracao.getTabela().getId());

				OperacaoTabela operacaoTabela = new OperacaoTabela();
				operacaoTabela.setComp_id(pk);
				operacaoTabela.setOperacao(operacaoEfetuada.getOperacao());
				operacaoTabela.setTabela(tabelaLinhaAlteracao.getTabela());
				getControladorUtil().inserir(operacaoTabela);
			}

			if(tabelaLinhaColunaAlteracoes != null && !tabelaLinhaColunaAlteracoes.isEmpty()){
				Iterator it = tabelaLinhaColunaAlteracoes.iterator();
				while(it.hasNext()){
					TabelaLinhaColunaAlteracao tabelaLinhaColunaAlteracao = (TabelaLinhaColunaAlteracao) it.next();
					tabelaLinhaColunaAlteracao.setTabelaLinhaAlteracao(tabelaLinhaAlteracao);
					tabelaLinhaColunaAlteracao.setUltimaAlteracao(new Date(System.currentTimeMillis()));
					getControladorUtil().inserir(tabelaLinhaColunaAlteracao);
				}
			}
		}
	}

	public Integer pesquisarUsuarioAlteracaoDasOperacoesEfetuadasHqlCount(Integer idUsuarioAcao, String[] idOperacoes, String idUsuario,
					Date dataInicial, Date dataFinal, Date horaInicial, Date horaFinal, Hashtable<Integer, String> argumentos, Integer id1)
					throws ControladorException{

		Integer retorno = null;
		try{
			retorno = (Integer) repositorioTransacao.pesquisarUsuarioAlteracaoDasOperacoesEfetuadasHqlCount(idUsuarioAcao, idOperacoes,
							idUsuario, dataInicial, dataFinal, horaInicial, horaFinal, argumentos, id1);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
		return retorno;

	}

	public Collection pesquisarEntidadeOperacoesEfetuadasHql(Integer chaveEntidade, Integer numeroPagina) throws ControladorException{

		Collection retorno = null;
		try{
			retorno = (Collection) repositorioTransacao.pesquisarEntidadeOperacoesEfetuadasHql(chaveEntidade, numeroPagina);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
		return retorno;

	}

	/**
	 * Registrar
	 * 
	 * @param objetoTransacao
	 * @throws ControladorException
	 */
	public void registrarTransacao(ObjetoTransacao objetoTransacao) throws ControladorException{

		if(objetoTransacao.retornarAtributosSelecionadosRegistro() == null){
			sessionContext.setRollbackOnly();
			// Este método só deve ser chamado caso tenha sido definida
			// a coleção de atributos selecionados para o registro da transacao
			throw new ControladorException("erro.sistema");
		}
		Interceptador.getInstancia().verificarObjetoAlterado(objetoTransacao, objetoTransacao.retornarAtributosSelecionadosRegistro());
	}

	public HashMap consultarResumoInformacoesOperacaoEfetuada(OperacaoEfetuada operacaoEfetuada, int idItemAnalisado)
					throws ControladorException{

		FiltroOperacao filtroOperacao = new FiltroOperacao();
		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade(FiltroOperacao.ARGUMENTO_PESQUISA);
		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade(FiltroOperacao.ARGUMENTO_PESQUISA_TABELA);
		filtroOperacao.adicionarParametro(new ParametroSimples(FiltroOperacao.ID, operacaoEfetuada.getOperacao().getId()));

		Collection coll = this.getControladorUtil().pesquisar(filtroOperacao, Operacao.class.getName());

		Operacao operacao = (Operacao) coll.iterator().next();
		// Consultando dados para o preenchimento das informações do item
		HashMap<String, Object> dados = new HashMap<String, Object>();

		try{

			// Tabela principal da operacao efetuada é considerada ser a tabela do argumento de
			// pesquisa
			if(operacao.getArgumentoPesquisa() != null){
				String nomeTabela = operacao.getArgumentoPesquisa().getTabela().getNomeTabela();
				String nomeClasse = HibernateUtil.getClassName(nomeTabela);
				Object instancia = Class.forName(nomeClasse).newInstance();

				if(instancia instanceof ObjetoTransacao){
					ObjetoTransacao objTran = (ObjetoTransacao) instancia;
					objTran.set("id", Integer.class, idItemAnalisado);
					Filtro filtro = objTran.retornaFiltro();

					Collection objs = getControladorUtil().pesquisar(filtro, nomeClasse);

					if(objs.iterator().hasNext()){
						objTran = (ObjetoTransacao) objs.iterator().next();

						String[] atributos = objTran.retornarAtributosInformacoesOperacaoEfetuada();
						String[] labels = objTran.retornarLabelsInformacoesOperacaoEfetuada();

						for(int i = 0; i < atributos.length; i++){
							dados.put(labels[i], objTran.get(atributos[i]));
						}
					}
				}
			}
		}catch(ClassNotFoundException e){
			throw new ControladorException("erro.sistema", e);
		}catch(InstantiationException e){
			throw new ControladorException("erro.sistema", e);
		}catch(IllegalAccessException e){
			throw new ControladorException("erro.sistema", e);
		}catch(ControladorException e){
			throw new ControladorException("erro.sistema", e);
		}
		return dados;
	}

	public void ordenarTabelaLinhaColunaAlteracao(Collection linhas, int idOperacao) throws ControladorException{

		int[] idTCs = new int[linhas.size()];
		int i = 0;
		for(Iterator iter = linhas.iterator(); iter.hasNext();){
			TabelaLinhaColunaAlteracao tlca = (TabelaLinhaColunaAlteracao) iter.next();
			idTCs[i++] = tlca.getTabelaColuna().getId();
		}
		Collection ordem = null;
		try{
			ordem = repositorioTransacao.pesquisarOperacaoOrdemExibicao(idTCs, idOperacao);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		final Map mapOrdem = new HashMap();

		if(!Util.isVazioOrNulo(ordem)){
			Iterator iterOrdem = ordem.iterator();
			// final Map ordemTabelas = new HashMap();
			while(iterOrdem.hasNext()){
				OperacaoOrdemExibicao opOrdem = (OperacaoOrdemExibicao) iterOrdem.next();
				mapOrdem.put(opOrdem.getTabelaColuna().getId(), opOrdem.getNumeroOrdem()); // idTabcoluna
				// e ordem

				// Object itemOrdemTab =
				// ordemTabelas.get(opOrdem.getTabelaColuna().getTabela().getId());
				// if (itemOrdemTab == null){
				// ordemTabelas.put(opOrdem.getTabelaColuna().getTabela().getId(),
				// opOrdem.getNumeroOrdem());
				// }

			}
		}


		// Ordenador pelo Id da tabela para agrupar os itens atualizados
		class ComparadorTLCA
						implements Comparator {

			public int compare(Object obj1, Object obj2){

				TabelaLinhaColunaAlteracao tlca1 = (TabelaLinhaColunaAlteracao) obj1;
				TabelaLinhaColunaAlteracao tlca2 = (TabelaLinhaColunaAlteracao) obj2;
				if(obj1 instanceof TabelaLinhaColunaAlteracao && obj2 instanceof TabelaLinhaColunaAlteracao){

					int idTab1 = tlca1.getTabelaLinhaAlteracao().getId();
					int idTab2 = tlca2.getTabelaLinhaAlteracao().getId();

					int dif = idTab2 - idTab1;
					if(dif == 0){
						Object ordem = mapOrdem.get(tlca1.getTabelaColuna().getId());
						int i1 = 999;
						if(ordem != null){
							i1 = ((Integer) ordem).intValue();
						}
						ordem = mapOrdem.get(tlca2.getTabelaColuna().getId());
						int i2 = 999;
						if(ordem != null){
							i2 = ((Integer) ordem).intValue();
						}

						dif = i1 - i2;
					}

					return dif;
				}else{
					return 0;
				}
			}
		}

		Collections.sort((List) linhas, new ComparadorTLCA());

	}

	/**
	 * Pesquisar Argumento
	 * 
	 * @author Saulo Lima
	 * @date 25/10/2012
	 * @param idOperacao
	 * @param nomeObjeto
	 * @return idArgumento
	 * @exception ControladorException
	 */
	public Integer pesquisarArgumento(Integer idOperacao, String nomeObjeto) throws ControladorException{

		try{
			return repositorioTransacao.pesquisarArgumento(idOperacao, nomeObjeto);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC3124] - Auditoria de Cancelamento/Revisão de Contas
	 * 
	 * @author Ado Rocha
	 * @throws ControladorException
	 * @date 20/01/2014
	 */
	public Collection pesquisarAuditoriaRevisaoCancelamentoContaFuncionario(Integer idFuncionario, Integer dataInicial, Integer dataFinal)
					throws ControladorException{

		Collection retorno = new ArrayList();

		try{
			Collection colecaoDados = repositorioTransacao.pesquisarAuditoriaRevisaoCancelamentoContaFuncionario(idFuncionario,
							dataInicial, dataFinal);

			if(!colecaoDados.isEmpty()){

				Iterator colecaoDadosIterator = colecaoDados.iterator();

				while(colecaoDadosIterator.hasNext()){

					Object[] dados = (Object[]) colecaoDadosIterator.next();

					AuditoriaCancelamentoRevisaoContasHelper helper = new AuditoriaCancelamentoRevisaoContasHelper();

					// mctcdoper
					if(dados[0] != null){

						if(dados[0].equals(3)){
							helper.setMctcdoper("CANCELAMENTO DE CONTA");
						}

						if(dados[0].equals(4)){
							helper.setMctcdoper("CONTA COLOCADA EM REVISÃO");
						}

						if(dados[0].equals(5)){
							helper.setMctcdoper("CONTA RETIRADA DE REVISÃO");
						}

					}

					// mctiamref
					if(dados[1] != null){
						helper.setMctiamref((Integer) dados[1]);
					}

					// mcticdmotivo
					if(dados[2] != null){
						helper.setMcticdmotivo((String) dados[2]);
					}

					// mctcodigo
					if(dados[3] != null){
						helper.setMctcodigo((Integer) dados[3]);
					}

					// mctdata
					if(dados[4] != null){
						String data = (String) dados[4].toString();
						helper.setMctdata(Util.converterAAAAMMDD(data));
					}

					// mcthora
					if(dados[5] != null){

						String horaMinuto = (String) dados[5].toString();

						if(horaMinuto.length() == 5){
							horaMinuto = "0" + horaMinuto;
						}

						String hora = horaMinuto.substring(0, 2);
						String minuto = horaMinuto.substring(2, 4);
						String horaCompleta = hora + ":" + minuto;

						helper.setMcthora(horaCompleta);
					}

					retorno.add(helper);

				}

			}

		}catch(ErroRepositorioException e){
			e.printStackTrace();
		}

		return retorno;
	}

	/**
	 * [UC3124] - Auditoria de Cancelamento/Revisão de Contas
	 * 
	 * @author Ado Rocha
	 * @throws ControladorException
	 * @date 20/01/2014
	 */
	public Collection pesquisarAuditoriaRevisaoCancelamentoContaUsuario(Integer idUsuario, Integer dataInicial, Integer dataFinal)
					throws ControladorException{

		Collection retorno = new ArrayList();

		try{
			Collection colecaoDados = repositorioTransacao.pesquisarAuditoriaRevisaoCancelamentoContaUsuario(idUsuario, dataInicial,
							dataFinal);

			if(!colecaoDados.isEmpty()){

				Iterator colecaoDadosIterator = colecaoDados.iterator();

				while(colecaoDadosIterator.hasNext()){

					Object[] dados = (Object[]) colecaoDadosIterator.next();

					AuditoriaCancelamentoRevisaoContasHelper helper = new AuditoriaCancelamentoRevisaoContasHelper();

					// mctcdoper
					if(dados[0] != null){

						if(dados[0].equals(3)){
							helper.setMctcdoper("CANCELAMENTO DE CONTA");
						}

						if(dados[0].equals(4)){
							helper.setMctcdoper("CONTA COLOCADA EM REVISÃO");
						}

						if(dados[0].equals(5)){
							helper.setMctcdoper("CONTA RETIRADA DE REVISÃO");
						}

					}

					// mctiamref
					if(dados[1] != null){
						helper.setMctiamref((Integer) dados[1]);
					}

					// mcticdmotivo
					if(dados[2] != null){
						helper.setMcticdmotivo((String) dados[2]);
					}

					// mctcodigo
					if(dados[3] != null){
						helper.setMctcodigo((Integer) dados[3]);
					}

					// mctdata
					if(dados[4] != null){
						String data = (String) dados[4].toString();
						helper.setMctdata(Util.converterAAAAMMDD(data));
					}

					// mcthora
					if(dados[5] != null){

						String horaMinuto = (String) dados[5].toString();

						if(horaMinuto.length() == 5){
							horaMinuto = "0" + horaMinuto;
						}

						String hora = horaMinuto.substring(0, 2);
						String minuto = horaMinuto.substring(2, 4);
						String horaCompleta = hora + ":" + minuto;

						helper.setMcthora(horaCompleta);
					}

					retorno.add(helper);

				}

			}

		}catch(ErroRepositorioException e){
			e.printStackTrace();
		}

		return retorno;
	}

	/**
	 * [UC3123] - Auditoria de Retificação/Implantação de Contas
	 * 
	 * @author Ado Rocha
	 * @throws ControladorException
	 * @date 20/01/2014
	 */
	public Collection pesquisarRetificacaoImplantacaoContaFuncionario(Integer idFuncionario, Integer dataInicial, Integer dataFinal)
					throws ControladorException{

		Collection retorno = new ArrayList();

		try{
			Collection colecaoDados = repositorioTransacao.pesquisarRetificacaoImplantacaoContaFuncionario(idFuncionario, dataInicial,
							dataFinal);

			if(!colecaoDados.isEmpty()){

				Iterator colecaoDadosIterator = colecaoDados.iterator();

				while(colecaoDadosIterator.hasNext()){

					Object[] dados = (Object[]) colecaoDadosIterator.next();

					String data = Util.converterAAAAMMDD((String) dados[0].toString());
					String horaMinuto = (String) dados[1].toString();

					if(horaMinuto.length() == 5){
						horaMinuto = "0" + horaMinuto;
					}

					String horas = horaMinuto.substring(0, 2);
					String minuto = horaMinuto.substring(2, 4);
					String hora = horas + ":" + minuto;

					// 1.0
					// dados[0] = mmccdoper
					if(dados[0].equals(1)){
						AuditoriaRetificacaoImplantacaoContasHelper helper = new AuditoriaRetificacaoImplantacaoContasHelper();
						helper.setDescricaoCampo("IMPLANTAÇÃO");
						helper.setConteudoAtual("");
						helper.setConteudoAnterior("");
						helper.setReferencia(dados[8].toString());
						helper.setMotivo(dados[9].toString());
						helper.setCodigo(dados[6].toString());
						helper.setData(data);
						helper.setHora(hora);
						retorno.add(helper);

					}else{
						//
						// 2.1
						// dados[10] = mmcidtvencon
						// dados[43] = mmcadtvencon
						if(dados[10] != null && dados[43] != null){
							Date atual = (Date) dados[10];
							Date anterior = (Date) dados[43];
							Integer resultado = Util.compararData(atual, anterior);
							if(!resultado.equals(0)){
								AuditoriaRetificacaoImplantacaoContasHelper helper1 = new AuditoriaRetificacaoImplantacaoContasHelper();
								helper1.setDescricaoCampo("DATA DO VENCIMENTO DA CONTA.");
								helper1.setConteudoAtual(dados[10].toString());
								helper1.setConteudoAnterior(dados[43].toString());
								helper1.setReferencia(dados[8].toString());
								helper1.setMotivo(dados[9].toString());
								helper1.setCodigo(dados[6].toString());
								helper1.setData(data);
								helper1.setHora(hora);
								retorno.add(helper1);
							}
						}
						//
						// 2.2
						// dados[11] = MMCICDSITAGU
						// dados[44] = MMCACDSITAGU
						if(!dados[11].equals(dados[44])){
							AuditoriaRetificacaoImplantacaoContasHelper helper2 = new AuditoriaRetificacaoImplantacaoContasHelper();

							helper2.setDescricaoCampo("CODIGO DA SITUACAO DA AGUA.");
							if(!dados[11].equals(0)){
								helper2.setConteudoAtual(dados[11].toString());
							}else{
								helper2.setConteudoAtual("");
							}
							if(!dados[44].equals(0)){
								helper2.setConteudoAnterior(dados[44].toString());
							}else{
								helper2.setConteudoAnterior("");
							}
							helper2.setReferencia(dados[8].toString());
							helper2.setMotivo(dados[9].toString());
							helper2.setCodigo(dados[6].toString());
							helper2.setData(data);
							helper2.setHora(hora);
							retorno.add(helper2);
						}
						//
						// 2.3
						// dados[12] = MMCICDSITESG
						// dados[45] = MMCACDSITESG
						if(!dados[12].equals(dados[45])){
							AuditoriaRetificacaoImplantacaoContasHelper helper3 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper3.setDescricaoCampo("CODIGO DA SITUACAO DE ESGOTO.");
							if(!dados[12].equals(0)){
								helper3.setConteudoAtual(dados[12].toString());
							}else{
								helper3.setConteudoAtual("");
							}
							if(!dados[45].equals(0)){
								helper3.setConteudoAnterior(dados[45].toString());
							}else{
								helper3.setConteudoAnterior("");
							}
							helper3.setReferencia(dados[8].toString());
							helper3.setMotivo(dados[9].toString());
							helper3.setCodigo(dados[6].toString());
							helper3.setData(data);
							helper3.setHora(hora);
							retorno.add(helper3);
						}
						//
						// 2.4
						// dados[13] = MMCINNCONMES
						// dados[46] = MMCANNCONMES
						if(!dados[13].equals(dados[46])){
							AuditoriaRetificacaoImplantacaoContasHelper helper4 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper4.setDescricaoCampo("6     CONSUMO RATEIO");
							helper4.setConteudoAtual(dados[13].toString());
							helper4.setConteudoAnterior(dados[46].toString());
							helper4.setReferencia(dados[8].toString());
							helper4.setMotivo(dados[9].toString());
							helper4.setCodigo(dados[6].toString());
							helper4.setData(data);
							helper4.setHora(hora);
							retorno.add(helper4);
						}
						//
						// 2.5
						// dados[15] = MMCIQTFIXESG
						// dados[48] = MMCAQTFIXESG
						if(!dados[15].equals(dados[48])){
							AuditoriaRetificacaoImplantacaoContasHelper helper5 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper5.setDescricaoCampo("CONSUMO FIXO DE ESGOTO.");
							helper5.setConteudoAtual(dados[15].toString());
							helper5.setConteudoAnterior(dados[48].toString());
							helper5.setReferencia(dados[8].toString());
							helper5.setMotivo(dados[9].toString());
							helper5.setCodigo(dados[6].toString());
							helper5.setData(data);
							helper5.setHora(hora);
							retorno.add(helper5);
						}
						//
						// 2.6
						// dados[16] = MMCICDRSP
						// dados[49] = MMCACDRSP
						if(!dados[16].equals(dados[49])){
							AuditoriaRetificacaoImplantacaoContasHelper helper6 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper6.setDescricaoCampo("CODIGO DO RESPONSAVEL.");
							helper6.setConteudoAtual(dados[16].toString());
							helper6.setConteudoAnterior(dados[49].toString());
							helper6.setReferencia(dados[8].toString());
							helper6.setMotivo(dados[9].toString());
							helper6.setCodigo(dados[6].toString());
							helper6.setData(data);
							helper6.setHora(hora);
							retorno.add(helper6);
						}
						//
						// 2.7
						// dados[18] = MMCIQTCAT1
						// dados[51] = MMCAQTCAT1
						if(!dados[18].equals(dados[51])){
							AuditoriaRetificacaoImplantacaoContasHelper helper7 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper7.setDescricaoCampo("QTD DE CATEGORIAS RESIDENCIAIS.");
							helper7.setConteudoAtual(dados[18].toString());
							helper7.setConteudoAnterior(dados[51].toString());
							helper7.setReferencia(dados[8].toString());
							helper7.setMotivo(dados[9].toString());
							helper7.setCodigo(dados[6].toString());
							helper7.setData(data);
							helper7.setHora(hora);
							retorno.add(helper7);
						}
						//
						// 2.8
						// dados[19] = MMCIQTCAT2
						// dados[52] = MMCAQTCAT2
						if(!dados[19].equals(dados[52])){
							AuditoriaRetificacaoImplantacaoContasHelper helper8 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper8.setDescricaoCampo("QTD DE CATEGORIAS COMERCIAIS.");
							helper8.setConteudoAtual(dados[19].toString());
							helper8.setConteudoAnterior(dados[52].toString());
							helper8.setReferencia(dados[8].toString());
							helper8.setMotivo(dados[9].toString());
							helper8.setCodigo(dados[6].toString());
							helper8.setData(data);
							helper8.setHora(hora);
							retorno.add(helper8);
						}
						//
						// 2.9
						// dados[22] = MMCIQTCAT3
						// dados[53] = MMCAQTCAT3
						if(!dados[22].equals(dados[53])){
							AuditoriaRetificacaoImplantacaoContasHelper helper9 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper9.setDescricaoCampo("QTD DE CATEGORIAS INDUSTRIAIS.");
							helper9.setConteudoAtual(dados[22].toString());
							helper9.setConteudoAnterior(dados[53].toString());
							helper9.setReferencia(dados[8].toString());
							helper9.setMotivo(dados[9].toString());
							helper9.setCodigo(dados[6].toString());
							helper9.setData(data);
							helper9.setHora(hora);
							retorno.add(helper9);
						}
						//
						// 2.10
						// dados[21] = MMCIQTCAT4
						// dados[54] = MMCAQTCAT4
						if(!dados[21].equals(dados[54])){
							AuditoriaRetificacaoImplantacaoContasHelper helper10 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper10.setDescricaoCampo("QTD DE CATEGORIAS PUBLICAS.");
							helper10.setConteudoAtual(dados[21].toString());
							helper10.setConteudoAnterior(dados[54].toString());
							helper10.setReferencia(dados[8].toString());
							helper10.setMotivo(dados[9].toString());
							helper10.setCodigo(dados[6].toString());
							helper10.setData(data);
							helper10.setHora(hora);
							retorno.add(helper10);
						}
						//
						// 2.11
						// dados[22] = MMCICDSER1
						// dados[55] = MMCACDSER1
						if(!dados[22].equals(0) || !dados[55].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper11 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper11.setDescricaoCampo("CODIGO DO SERVICO 1.");
							helper11.setConteudoAtual(dados[22].toString());
							helper11.setConteudoAnterior(dados[55].toString());
							helper11.setReferencia(dados[8].toString());
							helper11.setMotivo(dados[9].toString());
							helper11.setCodigo(dados[6].toString());
							helper11.setData(data);
							helper11.setHora(hora);
							retorno.add(helper11);
						}
						//
						// 2.12
						// dados[23] = MMCIAMREFANT1
						// dados[56] = MMCAAMREFANT1
						if(!dados[23].equals(0) || !dados[56].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper12 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper12.setDescricaoCampo("ANO MES REFATURAMENTO 1.");
							helper12.setConteudoAtual(dados[23].toString());
							helper12.setConteudoAnterior(dados[56].toString());
							helper12.setReferencia(dados[8].toString());
							helper12.setMotivo(dados[9].toString());
							helper12.setCodigo(dados[6].toString());
							helper12.setData(data);
							helper12.setHora(hora);
							retorno.add(helper12);
						}
						//
						// 2.13
						// dados[24] = MMCIVLSERV1
						// dados[57] = MMCAVLSERV1
						if(!dados[24].equals(dados[58])){
							AuditoriaRetificacaoImplantacaoContasHelper helper13 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper13.setDescricaoCampo("VALOR SERVICO  1.");
							helper13.setConteudoAtual(dados[24].toString());
							helper13.setConteudoAnterior(dados[57].toString());
							helper13.setReferencia(dados[8].toString());
							helper13.setMotivo(dados[9].toString());
							helper13.setCodigo(dados[6].toString());
							helper13.setData(data);
							helper13.setHora(hora);
							retorno.add(helper13);
						}
						//
						// 2.14
						// dados[25] = MMCICDSER2
						// dados[58] = MMCACDSER2
						if(!dados[25].equals(0) || !dados[58].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper14 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper14.setDescricaoCampo("CODIGO DO SERVICO 2.");
							helper14.setConteudoAtual(dados[25].toString());
							helper14.setConteudoAnterior(dados[58].toString());
							helper14.setReferencia(dados[8].toString());
							helper14.setMotivo(dados[9].toString());
							helper14.setCodigo(dados[6].toString());
							helper14.setData(data);
							helper14.setHora(hora);
							retorno.add(helper14);
						}
						//
						// 2.15
						// dados[26] = MMCIAMREFANT2
						// dados[59] = MMCAAMREFANT2
						if(!dados[26].equals(0) || !dados[59].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper15 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper15.setDescricaoCampo("ANO MES REFATURAMENTO 2.");
							helper15.setConteudoAtual(dados[26].toString());
							helper15.setConteudoAnterior(dados[59].toString());
							helper15.setReferencia(dados[8].toString());
							helper15.setMotivo(dados[9].toString());
							helper15.setCodigo(dados[6].toString());
							helper15.setData(data);
							helper15.setHora(hora);
							retorno.add(helper15);
						}
						//
						// 2.16
						// dados[27] = MMCIVLSERV2
						// dados[60] = MMCAVLSERV2
						if(!dados[27].equals(dados[60])){
							AuditoriaRetificacaoImplantacaoContasHelper helper16 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper16.setDescricaoCampo("VALOR SERVICO  2.");
							helper16.setConteudoAtual(dados[27].toString());
							helper16.setConteudoAnterior(dados[60].toString());
							helper16.setReferencia(dados[8].toString());
							helper16.setMotivo(dados[9].toString());
							helper16.setCodigo(dados[6].toString());
							helper16.setData(data);
							helper16.setHora(hora);
							retorno.add(helper16);
						}
						//
						// 2.17
						// dados[28] = MMCICDSER3
						// dados[61] = MMCACDSER3
						if(!dados[28].equals(0) || !dados[61].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper17 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper17.setDescricaoCampo("CODIGO DO SERVICO 3.");
							helper17.setConteudoAtual(dados[28].toString());
							helper17.setConteudoAnterior(dados[61].toString());
							helper17.setReferencia(dados[8].toString());
							helper17.setMotivo(dados[9].toString());
							helper17.setCodigo(dados[6].toString());
							helper17.setData(data);
							helper17.setHora(hora);
							retorno.add(helper17);
						}
						//
						// 2.18
						// dados[29] = MMCIAMREFANT3
						// dados[62] = MMCAAMREFANT3
						if(!dados[29].equals(0) || !dados[62].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper18 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper18.setDescricaoCampo("ANO MES REFATURAMENTO 3.");
							helper18.setConteudoAtual(dados[29].toString());
							helper18.setConteudoAnterior(dados[62].toString());
							helper18.setReferencia(dados[8].toString());
							helper18.setMotivo(dados[9].toString());
							helper18.setCodigo(dados[6].toString());
							helper18.setData(data);
							helper18.setHora(hora);
							retorno.add(helper18);
						}
						//
						// 2.19
						// dados[30] = MMCIVLSERV3
						// dados[63] = MMCAVLSERV3
						if(!dados[30].equals(dados[63])){
							AuditoriaRetificacaoImplantacaoContasHelper helper19 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper19.setDescricaoCampo("VALOR SERVICO  3.");
							helper19.setConteudoAtual(dados[30].toString());
							helper19.setConteudoAnterior(dados[63].toString());
							helper19.setReferencia(dados[8].toString());
							helper19.setMotivo(dados[9].toString());
							helper19.setCodigo(dados[6].toString());
							helper19.setData(data);
							helper19.setHora(hora);
							retorno.add(helper19);
						}
						//
						// 2.20
						// dados[31] = MMCICDSER4
						// dados[64] = MMCACDSER4
						if(!dados[31].equals(0) || !dados[64].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper20 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper20.setDescricaoCampo("CODIGO DO SERVICO 4.");
							helper20.setConteudoAtual(dados[31].toString());
							helper20.setConteudoAnterior(dados[64].toString());
							helper20.setReferencia(dados[8].toString());
							helper20.setMotivo(dados[9].toString());
							helper20.setCodigo(dados[6].toString());
							helper20.setData(data);
							helper20.setHora(hora);
							retorno.add(helper20);
						}
						//
						// 2.21
						// dados[32] = MMCIAMREFANT4
						// dados[65] = MMCAAMREFANT4
						if(!dados[32].equals(0) || !dados[65].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper21 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper21.setDescricaoCampo("ANO MES REFATURAMENTO 4.");
							helper21.setConteudoAtual(dados[32].toString());
							helper21.setConteudoAnterior(dados[65].toString());
							helper21.setReferencia(dados[8].toString());
							helper21.setMotivo(dados[9].toString());
							helper21.setCodigo(dados[6].toString());
							helper21.setData(data);
							helper21.setHora(hora);
							retorno.add(helper21);
						}
						//
						// 2.22
						// dados[33] = MMCIVLSERV4
						// dados[66] = MMCAVLSERV4
						if(!dados[33].equals(dados[66])){
							AuditoriaRetificacaoImplantacaoContasHelper helper22 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper22.setDescricaoCampo("VALOR SERVICO  4.");
							helper22.setConteudoAtual(dados[33].toString());
							helper22.setConteudoAnterior(dados[66].toString());
							helper22.setReferencia(dados[8].toString());
							helper22.setMotivo(dados[9].toString());
							helper22.setCodigo(dados[6].toString());
							helper22.setData(data);
							helper22.setHora(hora);
							retorno.add(helper22);
						}
						//
						// 2.23
						// dados[34] = MMCICDSER5
						// dados[67] = MMCACDSER5
						if(!dados[34].equals(0) || !dados[67].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper23 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper23.setDescricaoCampo("CODIGO DO SERVICO 5.");
							helper23.setConteudoAtual(dados[34].toString());
							helper23.setConteudoAnterior(dados[67].toString());
							helper23.setReferencia(dados[8].toString());
							helper23.setMotivo(dados[9].toString());
							helper23.setCodigo(dados[6].toString());
							helper23.setData(data);
							helper23.setHora(hora);
							retorno.add(helper23);
						}
						//
						// 2.24
						// dados[35] = MMCIAMREFANT5
						// dados[68] = MMCAAMREFANT5
						if(!dados[35].equals(0) || !dados[68].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper24 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper24.setDescricaoCampo("ANO MES REFATURAMENTO 5.");
							helper24.setConteudoAtual(dados[35].toString());
							helper24.setConteudoAnterior(dados[68].toString());
							helper24.setReferencia(dados[8].toString());
							helper24.setMotivo(dados[9].toString());
							helper24.setCodigo(dados[6].toString());
							helper24.setData(data);
							helper24.setHora(hora);
							retorno.add(helper24);
						}
						//
						// 2.25
						// dados[36] = MMCIVLSERV5
						// dados[69] = MMCAVLSERV5
						if(!dados[36].equals(dados[69])){
							AuditoriaRetificacaoImplantacaoContasHelper helper25 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper25.setDescricaoCampo("VALOR SERVICO  5.");
							helper25.setConteudoAtual(dados[36].toString());
							helper25.setConteudoAnterior(dados[69].toString());
							helper25.setReferencia(dados[8].toString());
							helper25.setMotivo(dados[9].toString());
							helper25.setCodigo(dados[6].toString());
							helper25.setData(data);
							helper25.setHora(hora);
							retorno.add(helper25);
						}
						//
						// 2.26
						// dados[37] = MMCICDSER6
						// dados[70] = MMCACDSER6
						if(!dados[37].equals(0) || !dados[70].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper26 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper26.setDescricaoCampo("CODIGO DO SERVICO 6.");
							helper26.setConteudoAtual(dados[37].toString());
							helper26.setConteudoAnterior(dados[70].toString());
							helper26.setReferencia(dados[8].toString());
							helper26.setMotivo(dados[9].toString());
							helper26.setCodigo(dados[6].toString());
							helper26.setData(data);
							helper26.setHora(hora);
							retorno.add(helper26);
						}
						//
						// 2.27
						// dados[38] = MMCIAMREFANT6
						// dados[71] = MMCAAMREFANT6
						if(!dados[38].equals(0) || !dados[71].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper27 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper27.setDescricaoCampo("ANO MES REFATURAMENTO 6.");
							helper27.setConteudoAtual(dados[38].toString());
							helper27.setConteudoAnterior(dados[71].toString());
							helper27.setReferencia(dados[8].toString());
							helper27.setMotivo(dados[9].toString());
							helper27.setCodigo(dados[6].toString());
							helper27.setData(data);
							helper27.setHora(hora);
							retorno.add(helper27);
						}
						//
						// 2.28
						// dados[39] = MMCIVLSERV6
						// dados[72] = MMCAVLSERV6
						if(!dados[39].equals(dados[72])){
							AuditoriaRetificacaoImplantacaoContasHelper helper28 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper28.setDescricaoCampo("VALOR SERVICO  6.");
							helper28.setConteudoAtual(dados[39].toString());
							helper28.setConteudoAnterior(dados[72].toString());
							helper28.setReferencia(dados[8].toString());
							helper28.setMotivo(dados[9].toString());
							helper28.setCodigo(dados[6].toString());
							helper28.setData(data);
							helper28.setHora(hora);
							retorno.add(helper28);
						}
						//
						// 2.29
						// dados[40] = MMCICDSER7
						// dados[73] = MMCACDSER7
						if(!dados[40].equals(0) || !dados[73].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper29 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper29.setDescricaoCampo("CODIGO DO SERVICO 7.");
							helper29.setConteudoAtual(dados[40].toString());
							helper29.setConteudoAnterior(dados[73].toString());
							helper29.setReferencia(dados[8].toString());
							helper29.setMotivo(dados[9].toString());
							helper29.setCodigo(dados[6].toString());
							helper29.setData(data);
							helper29.setHora(hora);
							retorno.add(helper29);
						}
						//
						// 2.30
						// dados[41] = MMCIAMREFANT7
						// dados[74] = MMCAAMREFANT7
						if(!dados[41].equals(0) || !dados[74].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper30 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper30.setDescricaoCampo("ANO MES REFATURAMENTO 7.");
							helper30.setConteudoAtual(dados[41].toString());
							helper30.setConteudoAnterior(dados[74].toString());
							helper30.setReferencia(dados[8].toString());
							helper30.setMotivo(dados[9].toString());
							helper30.setCodigo(dados[6].toString());
							helper30.setData(data);
							helper30.setHora(hora);
							retorno.add(helper30);
						}
						//
						// 2.31
						// dados[42] = MMCIVLSERV7
						// dados[75] = MMCAVLSERV7
						if(!dados[42].equals(dados[75])){
							AuditoriaRetificacaoImplantacaoContasHelper helper31 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper31.setDescricaoCampo("VALOR SERVICO  7.");
							helper31.setConteudoAtual(dados[42].toString());
							helper31.setConteudoAnterior(dados[75].toString());
							helper31.setReferencia(dados[8].toString());
							helper31.setMotivo(dados[9].toString());
							helper31.setCodigo(dados[6].toString());
							helper31.setData(data);
							helper31.setHora(hora);
							retorno.add(helper31);
						}
					}
				}

			}

		}catch(ErroRepositorioException e){
			e.printStackTrace();
		}

		return retorno;
	}

	/**
	 * [UC3123] - Auditoria de Retificação/Implantação de Contas
	 * 
	 * @author Ado Rocha
	 * @throws ControladorException
	 * @date 20/01/2014
	 */
	public Collection pesquisarRetificacaoImplantacaoContaUsuario(Integer idUsuario, Integer dataInicial, Integer dataFinal)
					throws ControladorException{

		Collection retorno = new ArrayList();

		try{
			Collection colecaoDados = repositorioTransacao.pesquisarRetificacaoImplantacaoContaUsuario(idUsuario, dataInicial, dataFinal);

			if(!colecaoDados.isEmpty()){

				Iterator colecaoDadosIterator = colecaoDados.iterator();

				while(colecaoDadosIterator.hasNext()){

					Object[] dados = (Object[]) colecaoDadosIterator.next();

					String data = Util.converterAAAAMMDD((String) dados[0].toString());
					String horaMinuto = (String) dados[1].toString();

					if(horaMinuto.length() == 5){
						horaMinuto = "0" + horaMinuto;
					}

					String horas = horaMinuto.substring(0, 2);
					String minuto = horaMinuto.substring(2, 4);
					String hora = horas + ":" + minuto;

					// 1.0
					// dados[0] = mmccdoper
					if(dados[0].equals(1)){
						AuditoriaRetificacaoImplantacaoContasHelper helper = new AuditoriaRetificacaoImplantacaoContasHelper();
						helper.setDescricaoCampo("IMPLANTAÇÃO");
						helper.setConteudoAtual("");
						helper.setConteudoAnterior("");
						helper.setReferencia(dados[8].toString());
						helper.setMotivo(dados[9].toString());
						helper.setCodigo(dados[3].toString());
						helper.setData(data);
						helper.setHora(hora);
						retorno.add(helper);

					}else{
						//
						// 2.1
						// dados[10] = mmcidtvencon
						// dados[43] = mmcadtvencon
						if(dados[10] != null && dados[43] != null){
							Date atual = (Date) dados[10];
							Date anterior = (Date) dados[43];
							Integer resultado = Util.compararData(atual, anterior);
							if(!resultado.equals(0)){
								AuditoriaRetificacaoImplantacaoContasHelper helper1 = new AuditoriaRetificacaoImplantacaoContasHelper();
								helper1.setDescricaoCampo("DATA DO VENCIMENTO DA CONTA.");
								helper1.setConteudoAtual(dados[10].toString());
								helper1.setConteudoAnterior(dados[43].toString());
								helper1.setReferencia(dados[8].toString());
								helper1.setMotivo(dados[9].toString());
								helper1.setCodigo(dados[3].toString());
								helper1.setData(data);
								helper1.setHora(hora);
								retorno.add(helper1);
							}
						}
						//
						// 2.2
						// dados[11] = MMCICDSITAGU
						// dados[44] = MMCACDSITAGU
						if(!dados[11].equals(dados[44])){
							AuditoriaRetificacaoImplantacaoContasHelper helper2 = new AuditoriaRetificacaoImplantacaoContasHelper();

							helper2.setDescricaoCampo("CODIGO DA SITUACAO DA AGUA.");
							if(!dados[11].equals(0)){
								helper2.setConteudoAtual(dados[11].toString());
							}else{
								helper2.setConteudoAtual("");
							}
							if(!dados[44].equals(0)){
								helper2.setConteudoAnterior(dados[44].toString());
							}else{
								helper2.setConteudoAnterior("");
							}
							helper2.setReferencia(dados[8].toString());
							helper2.setMotivo(dados[9].toString());
							helper2.setCodigo(dados[3].toString());
							helper2.setData(data);
							helper2.setHora(hora);
							retorno.add(helper2);
						}
						//
						// 2.3
						// dados[12] = MMCICDSITESG
						// dados[45] = MMCACDSITESG
						if(!dados[12].equals(dados[45])){
							AuditoriaRetificacaoImplantacaoContasHelper helper3 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper3.setDescricaoCampo("CODIGO DA SITUACAO DE ESGOTO.");
							if(!dados[12].equals(0)){
								helper3.setConteudoAtual(dados[12].toString());
							}else{
								helper3.setConteudoAtual("");
							}
							if(!dados[45].equals(0)){
								helper3.setConteudoAnterior(dados[46].toString());
							}else{
								helper3.setConteudoAnterior("");
							}
							helper3.setReferencia(dados[8].toString());
							helper3.setMotivo(dados[9].toString());
							helper3.setCodigo(dados[3].toString());
							helper3.setData(data);
							helper3.setHora(hora);
							retorno.add(helper3);
						}
						//
						// 2.4
						// dados[13] = MMCINNCONMES
						// dados[46] = MMCANNCONMES
						if(!dados[13].equals(dados[46])){
							AuditoriaRetificacaoImplantacaoContasHelper helper4 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper4.setDescricaoCampo("6     CONSUMO RATEIO");
							helper4.setConteudoAtual(dados[13].toString());
							helper4.setConteudoAnterior(dados[46].toString());
							helper4.setReferencia(dados[8].toString());
							helper4.setMotivo(dados[9].toString());
							helper4.setCodigo(dados[3].toString());
							helper4.setData(data);
							helper4.setHora(hora);
							retorno.add(helper4);
						}
						//
						// 2.5
						// dados[17] = MMCIQTFIXESG
						// dados[48] = MMCAQTFIXESG
						if(!dados[15].equals(dados[48])){
							AuditoriaRetificacaoImplantacaoContasHelper helper5 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper5.setDescricaoCampo("CONSUMO FIXO DE ESGOTO.");
							helper5.setConteudoAtual(dados[15].toString());
							helper5.setConteudoAnterior(dados[48].toString());
							helper5.setReferencia(dados[8].toString());
							helper5.setMotivo(dados[9].toString());
							helper5.setCodigo(dados[3].toString());
							helper5.setData(data);
							helper5.setHora(hora);
							retorno.add(helper5);
						}
						//
						// 2.6
						// dados[16] = MMCICDRSP
						// dados[49] = MMCACDRSP
						if(!dados[16].equals(dados[49])){
							AuditoriaRetificacaoImplantacaoContasHelper helper6 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper6.setDescricaoCampo("CODIGO DO RESPONSAVEL.");
							helper6.setConteudoAtual(dados[16].toString());
							helper6.setConteudoAnterior(dados[49].toString());
							helper6.setReferencia(dados[8].toString());
							helper6.setMotivo(dados[9].toString());
							helper6.setCodigo(dados[3].toString());
							helper6.setData(data);
							helper6.setHora(hora);
							retorno.add(helper6);
						}
						//
						// 2.7
						// dados[18] = MMCIQTCAT1
						// dados[51] = MMCAQTCAT1
						if(!dados[18].equals(dados[51])){
							AuditoriaRetificacaoImplantacaoContasHelper helper7 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper7.setDescricaoCampo("QTD DE CATEGORIAS RESIDENCIAIS.");
							helper7.setConteudoAtual(dados[18].toString());
							helper7.setConteudoAnterior(dados[51].toString());
							helper7.setReferencia(dados[8].toString());
							helper7.setMotivo(dados[9].toString());
							helper7.setCodigo(dados[3].toString());
							helper7.setData(data);
							helper7.setHora(hora);
							retorno.add(helper7);
						}
						//
						// 2.8
						// dados[19] = MMCIQTCAT2
						// dados[52] = MMCAQTCAT2
						if(!dados[19].equals(dados[52])){
							AuditoriaRetificacaoImplantacaoContasHelper helper8 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper8.setDescricaoCampo("QTD DE CATEGORIAS COMERCIAIS.");
							helper8.setConteudoAtual(dados[19].toString());
							helper8.setConteudoAnterior(dados[52].toString());
							helper8.setReferencia(dados[8].toString());
							helper8.setMotivo(dados[9].toString());
							helper8.setCodigo(dados[3].toString());
							helper8.setData(data);
							helper8.setHora(hora);
							retorno.add(helper8);
						}
						//
						// 2.9
						// dados[22] = MMCIQTCAT3
						// dados[53] = MMCAQTCAT3
						if(!dados[22].equals(dados[53])){
							AuditoriaRetificacaoImplantacaoContasHelper helper9 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper9.setDescricaoCampo("QTD DE CATEGORIAS INDUSTRIAIS.");
							helper9.setConteudoAtual(dados[22].toString());
							helper9.setConteudoAnterior(dados[53].toString());
							helper9.setReferencia(dados[8].toString());
							helper9.setMotivo(dados[9].toString());
							helper9.setCodigo(dados[3].toString());
							helper9.setData(data);
							helper9.setHora(hora);
							retorno.add(helper9);
						}
						//
						// 2.10
						// dados[21] = MMCIQTCAT4
						// dados[54] = MMCAQTCAT4
						if(!dados[21].equals(dados[54])){
							AuditoriaRetificacaoImplantacaoContasHelper helper10 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper10.setDescricaoCampo("QTD DE CATEGORIAS PUBLICAS.");
							helper10.setConteudoAtual(dados[21].toString());
							helper10.setConteudoAnterior(dados[54].toString());
							helper10.setReferencia(dados[8].toString());
							helper10.setMotivo(dados[9].toString());
							helper10.setCodigo(dados[3].toString());
							helper10.setData(data);
							helper10.setHora(hora);
							retorno.add(helper10);
						}
						//
						// 2.11
						// dados[22] = MMCICDSER1
						// dados[55] = MMCACDSER1
						if(!dados[22].equals(0) || !dados[55].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper11 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper11.setDescricaoCampo("CODIGO DO SERVICO 1.");
							helper11.setConteudoAtual(dados[22].toString());
							helper11.setConteudoAnterior(dados[55].toString());
							helper11.setReferencia(dados[8].toString());
							helper11.setMotivo(dados[9].toString());
							helper11.setCodigo(dados[3].toString());
							helper11.setData(data);
							helper11.setHora(hora);
							retorno.add(helper11);
						}
						//
						// 2.12
						// dados[23] = MMCIAMREFANT1
						// dados[56] = MMCAAMREFANT1
						if(!dados[23].equals(0) || !dados[56].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper12 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper12.setDescricaoCampo("ANO MES REFATURAMENTO 1.");
							helper12.setConteudoAtual(dados[23].toString());
							helper12.setConteudoAnterior(dados[56].toString());
							helper12.setReferencia(dados[8].toString());
							helper12.setMotivo(dados[9].toString());
							helper12.setCodigo(dados[3].toString());
							helper12.setData(data);
							helper12.setHora(hora);
							retorno.add(helper12);
						}
						//
						// 2.13
						// dados[24] = MMCIVLSERV1
						// dados[57] = MMCAVLSERV1
						if(!dados[24].equals(dados[58])){
							AuditoriaRetificacaoImplantacaoContasHelper helper13 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper13.setDescricaoCampo("VALOR SERVICO  1.");
							helper13.setConteudoAtual(dados[24].toString());
							helper13.setConteudoAnterior(dados[57].toString());
							helper13.setReferencia(dados[8].toString());
							helper13.setMotivo(dados[9].toString());
							helper13.setCodigo(dados[3].toString());
							helper13.setData(data);
							helper13.setHora(hora);
							retorno.add(helper13);
						}
						//
						// 2.14
						// dados[25] = MMCICDSER2
						// dados[58] = MMCACDSER2
						if(!dados[25].equals(0) || !dados[58].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper14 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper14.setDescricaoCampo("CODIGO DO SERVICO 2.");
							helper14.setConteudoAtual(dados[25].toString());
							helper14.setConteudoAnterior(dados[58].toString());
							helper14.setReferencia(dados[8].toString());
							helper14.setMotivo(dados[9].toString());
							helper14.setCodigo(dados[3].toString());
							helper14.setData(data);
							helper14.setHora(hora);
							retorno.add(helper14);
						}
						//
						// 2.15
						// dados[26] = MMCIAMREFANT2
						// dados[59] = MMCAAMREFANT2
						if(!dados[26].equals(0) || !dados[59].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper15 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper15.setDescricaoCampo("ANO MES REFATURAMENTO 2.");
							helper15.setConteudoAtual(dados[26].toString());
							helper15.setConteudoAnterior(dados[59].toString());
							helper15.setReferencia(dados[8].toString());
							helper15.setMotivo(dados[9].toString());
							helper15.setCodigo(dados[3].toString());
							helper15.setData(data);
							helper15.setHora(hora);
							retorno.add(helper15);
						}
						//
						// 2.16
						// dados[27] = MMCIVLSERV2
						// dados[60] = MMCAVLSERV2
						if(!dados[27].equals(dados[60])){
							AuditoriaRetificacaoImplantacaoContasHelper helper16 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper16.setDescricaoCampo("VALOR SERVICO  2.");
							helper16.setConteudoAtual(dados[27].toString());
							helper16.setConteudoAnterior(dados[60].toString());
							helper16.setReferencia(dados[8].toString());
							helper16.setMotivo(dados[9].toString());
							helper16.setCodigo(dados[3].toString());
							helper16.setData(data);
							helper16.setHora(hora);
							retorno.add(helper16);
						}
						//
						// 2.17
						// dados[28] = MMCICDSER3
						// dados[61] = MMCACDSER3
						if(!dados[28].equals(0) || !dados[61].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper17 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper17.setDescricaoCampo("CODIGO DO SERVICO 3.");
							helper17.setConteudoAtual(dados[28].toString());
							helper17.setConteudoAnterior(dados[61].toString());
							helper17.setReferencia(dados[8].toString());
							helper17.setMotivo(dados[9].toString());
							helper17.setCodigo(dados[3].toString());
							helper17.setData(data);
							helper17.setHora(hora);
							retorno.add(helper17);
						}
						//
						// 2.18
						// dados[29] = MMCIAMREFANT3
						// dados[62] = MMCAAMREFANT3
						if(!dados[29].equals(0) || !dados[62].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper18 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper18.setDescricaoCampo("ANO MES REFATURAMENTO 3.");
							helper18.setConteudoAtual(dados[29].toString());
							helper18.setConteudoAnterior(dados[62].toString());
							helper18.setReferencia(dados[8].toString());
							helper18.setMotivo(dados[9].toString());
							helper18.setCodigo(dados[3].toString());
							helper18.setData(data);
							helper18.setHora(hora);
							retorno.add(helper18);
						}
						//
						// 2.19
						// dados[30] = MMCIVLSERV3
						// dados[63] = MMCAVLSERV3
						if(!dados[30].equals(dados[63])){
							AuditoriaRetificacaoImplantacaoContasHelper helper19 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper19.setDescricaoCampo("VALOR SERVICO  3.");
							helper19.setConteudoAtual(dados[30].toString());
							helper19.setConteudoAnterior(dados[63].toString());
							helper19.setReferencia(dados[8].toString());
							helper19.setMotivo(dados[9].toString());
							helper19.setCodigo(dados[3].toString());
							helper19.setData(data);
							helper19.setHora(hora);
							retorno.add(helper19);
						}
						//
						// 2.20
						// dados[31] = MMCICDSER4
						// dados[64] = MMCACDSER4
						if(!dados[31].equals(0) || !dados[64].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper20 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper20.setDescricaoCampo("CODIGO DO SERVICO 4.");
							helper20.setConteudoAtual(dados[31].toString());
							helper20.setConteudoAnterior(dados[64].toString());
							helper20.setReferencia(dados[8].toString());
							helper20.setMotivo(dados[9].toString());
							helper20.setCodigo(dados[3].toString());
							helper20.setData(data);
							helper20.setHora(hora);
							retorno.add(helper20);
						}
						//
						// 2.21
						// dados[32] = MMCIAMREFANT4
						// dados[65] = MMCAAMREFANT4
						if(!dados[32].equals(0) || !dados[65].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper21 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper21.setDescricaoCampo("ANO MES REFATURAMENTO 4.");
							helper21.setConteudoAtual(dados[32].toString());
							helper21.setConteudoAnterior(dados[65].toString());
							helper21.setReferencia(dados[8].toString());
							helper21.setMotivo(dados[9].toString());
							helper21.setCodigo(dados[3].toString());
							helper21.setData(data);
							helper21.setHora(hora);
							retorno.add(helper21);
						}
						//
						// 2.22
						// dados[33] = MMCIVLSERV4
						// dados[66] = MMCAVLSERV4
						if(!dados[33].equals(dados[66])){
							AuditoriaRetificacaoImplantacaoContasHelper helper22 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper22.setDescricaoCampo("VALOR SERVICO  4.");
							helper22.setConteudoAtual(dados[33].toString());
							helper22.setConteudoAnterior(dados[66].toString());
							helper22.setReferencia(dados[8].toString());
							helper22.setMotivo(dados[9].toString());
							helper22.setCodigo(dados[3].toString());
							helper22.setData(data);
							helper22.setHora(hora);
							retorno.add(helper22);
						}
						//
						// 2.23
						// dados[34] = MMCICDSER5
						// dados[67] = MMCACDSER5
						if(!dados[34].equals(0) || !dados[67].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper23 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper23.setDescricaoCampo("CODIGO DO SERVICO 5.");
							helper23.setConteudoAtual(dados[34].toString());
							helper23.setConteudoAnterior(dados[67].toString());
							helper23.setReferencia(dados[8].toString());
							helper23.setMotivo(dados[9].toString());
							helper23.setCodigo(dados[3].toString());
							helper23.setData(data);
							helper23.setHora(hora);
							retorno.add(helper23);
						}
						//
						// 2.24
						// dados[35] = MMCIAMREFANT5
						// dados[68] = MMCAAMREFANT5
						if(!dados[35].equals(0) || !dados[68].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper24 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper24.setDescricaoCampo("ANO MES REFATURAMENTO 5.");
							helper24.setConteudoAtual(dados[35].toString());
							helper24.setConteudoAnterior(dados[68].toString());
							helper24.setReferencia(dados[8].toString());
							helper24.setMotivo(dados[9].toString());
							helper24.setCodigo(dados[3].toString());
							helper24.setData(data);
							helper24.setHora(hora);
							retorno.add(helper24);
						}
						//
						// 2.25
						// dados[36] = MMCIVLSERV5
						// dados[69] = MMCAVLSERV5
						if(!dados[36].equals(dados[69])){
							AuditoriaRetificacaoImplantacaoContasHelper helper25 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper25.setDescricaoCampo("VALOR SERVICO  5.");
							helper25.setConteudoAtual(dados[36].toString());
							helper25.setConteudoAnterior(dados[69].toString());
							helper25.setReferencia(dados[8].toString());
							helper25.setMotivo(dados[9].toString());
							helper25.setCodigo(dados[3].toString());
							helper25.setData(data);
							helper25.setHora(hora);
							retorno.add(helper25);
						}
						//
						// 2.26
						// dados[37] = MMCICDSER6
						// dados[70] = MMCACDSER6
						if(!dados[37].equals(0) || !dados[70].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper26 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper26.setDescricaoCampo("CODIGO DO SERVICO 6.");
							helper26.setConteudoAtual(dados[37].toString());
							helper26.setConteudoAnterior(dados[70].toString());
							helper26.setReferencia(dados[8].toString());
							helper26.setMotivo(dados[9].toString());
							helper26.setCodigo(dados[3].toString());
							helper26.setData(data);
							helper26.setHora(hora);
							retorno.add(helper26);
						}
						//
						// 2.27
						// dados[38] = MMCIAMREFANT6
						// dados[71] = MMCAAMREFANT6
						if(!dados[38].equals(0) || !dados[71].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper27 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper27.setDescricaoCampo("ANO MES REFATURAMENTO 6.");
							helper27.setConteudoAtual(dados[38].toString());
							helper27.setConteudoAnterior(dados[71].toString());
							helper27.setReferencia(dados[8].toString());
							helper27.setMotivo(dados[9].toString());
							helper27.setCodigo(dados[3].toString());
							helper27.setData(data);
							helper27.setHora(hora);
							retorno.add(helper27);
						}
						//
						// 2.28
						// dados[39] = MMCIVLSERV6
						// dados[72] = MMCAVLSERV6
						if(!dados[39].equals(dados[72])){
							AuditoriaRetificacaoImplantacaoContasHelper helper28 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper28.setDescricaoCampo("VALOR SERVICO  6.");
							helper28.setConteudoAtual(dados[39].toString());
							helper28.setConteudoAnterior(dados[72].toString());
							helper28.setReferencia(dados[8].toString());
							helper28.setMotivo(dados[9].toString());
							helper28.setCodigo(dados[3].toString());
							helper28.setData(data);
							helper28.setHora(hora);
							retorno.add(helper28);
						}
						//
						// 2.29
						// dados[40] = MMCICDSER7
						// dados[73] = MMCACDSER7
						if(!dados[40].equals(0) || !dados[73].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper29 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper29.setDescricaoCampo("CODIGO DO SERVICO 7.");
							helper29.setConteudoAtual(dados[40].toString());
							helper29.setConteudoAnterior(dados[73].toString());
							helper29.setReferencia(dados[8].toString());
							helper29.setMotivo(dados[9].toString());
							helper29.setCodigo(dados[3].toString());
							helper29.setData(data);
							helper29.setHora(hora);
							retorno.add(helper29);
						}
						//
						// 2.30
						// dados[41] = MMCIAMREFANT7
						// dados[74] = MMCAAMREFANT7
						if(!dados[41].equals(0) || !dados[74].equals(0)){
							AuditoriaRetificacaoImplantacaoContasHelper helper30 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper30.setDescricaoCampo("ANO MES REFATURAMENTO 7.");
							helper30.setConteudoAtual(dados[41].toString());
							helper30.setConteudoAnterior(dados[74].toString());
							helper30.setReferencia(dados[8].toString());
							helper30.setMotivo(dados[9].toString());
							helper30.setCodigo(dados[3].toString());
							helper30.setData(data);
							helper30.setHora(hora);
							retorno.add(helper30);
						}
						//
						// 2.31
						// dados[42] = MMCIVLSERV7
						// dados[75] = MMCAVLSERV7
						if(!dados[42].equals(dados[75])){
							AuditoriaRetificacaoImplantacaoContasHelper helper31 = new AuditoriaRetificacaoImplantacaoContasHelper();
							helper31.setDescricaoCampo("VALOR SERVICO  7.");
							helper31.setConteudoAtual(dados[42].toString());
							helper31.setConteudoAnterior(dados[75].toString());
							helper31.setReferencia(dados[8].toString());
							helper31.setMotivo(dados[9].toString());
							helper31.setCodigo(dados[3].toString());
							helper31.setData(data);
							helper31.setHora(hora);
							retorno.add(helper31);
						}
					}
				}

			}

		}catch(ErroRepositorioException e){
			e.printStackTrace();
		}

		return retorno;
	}

	/**
	 * [UC3125] - Auditoria de Parcelamento de Contas
	 * 
	 * @author Ado Rocha
	 * @throws ControladorException
	 * @date 20/01/2014
	 */
	public Collection pesquisarParcelamentoContaFuncionario(Integer idFuncionario, Date dataInicial, Date dataFinal)
					throws ControladorException{

		Collection retorno = new ArrayList();

		try{
			Collection colecaoDados = repositorioTransacao.pesquisarParcelamentoContaFuncionario(idFuncionario,
							dataInicial, dataFinal);

			if(!colecaoDados.isEmpty()){

				Iterator colecaoDadosIterator = colecaoDados.iterator();

				while(colecaoDadosIterator.hasNext()){

					Object[] dados = (Object[]) colecaoDadosIterator.next();

					AuditoriaParcelamentoContasHelper helper = new AuditoriaParcelamentoContasHelper();

					// MPCNNMATUSU = dados[5]
					if(dados[5] != null){
						helper.setCodigo(dados[5].toString());
					}

					// MPCDTMPC = dados[1]
					if(dados[1] != null){
						String anoMesDia = dados[1].toString();
						String ano = anoMesDia.substring(0, 4);
						String mes = anoMesDia.substring(5, 7);
						String dia = anoMesDia.substring(8, 10);
						anoMesDia = ano + mes + dia;
						helper.setData(Util.converterAAAAMMDD(anoMesDia));
					}

					// MPCHRMPC = dados[2]
					if(dados[2] != null){

						String horaMinuto = (String) dados[2].toString();

						if(horaMinuto.length() == 5){
							horaMinuto = "0" + horaMinuto;
						}

						String hora = horaMinuto.substring(0, 2);
						String minuto = horaMinuto.substring(2, 4);
						String horaCompleta = hora + ":" + minuto;

						helper.setHora(horaCompleta);
					}

					//MPCAMINI = dados[3]
					if(dados[3] != null){
						helper.setReferenciaInicial(dados[3].toString());
					}

					//MPCAMFIN = dados[4]
					if(dados[4] != null){
						helper.setReferenciaFinal(dados[4].toString());
					}

					//MPCNNPREST = dados[7]
					if(dados[7] != null){
						helper.setPrestacao(dados[7].toString());
					}

					// MPCNNMATGER = dados[14]
					if(dados[14] != null){
						helper.setGerencia(dados[14].toString());
					}

					//MPCVLENTR = dados[8]
					if(dados[8] != null){
						helper.setValorEntrada(Util.formatarMoedaRealparaBigDecimal(dados[8].toString()));
					}

					//MPCVLPREST = dados[9] 
					if(dados[9] != null){
						helper.setValorPrestacao(Util.formatarMoedaRealparaBigDecimal(dados[9].toString()));
					}
					
					//MPCVLDEBHIST = dados[10] 
					if(dados[10] != null){
						helper.setValorDebitoHistorico(Util.formatarMoedaRealparaBigDecimal(dados[10].toString()));
					}

					//MPCVLDEBCORR = dados[11] 
					if(dados[11] != null){
						helper.setValorDebitoAtualizado(Util.formatarMoedaRealparaBigDecimal(dados[11].toString()));
					}

					// MPCVLPARCEL = dados[13]
					if(dados[13] != null){
						helper.setValorParcelamento(Util.formatarMoedaRealparaBigDecimal(dados[13].toString()));
					}


					retorno.add(helper);

				}

			}

		}catch(ErroRepositorioException e){
			e.printStackTrace();
		}

		return retorno;
	}

	/**
	 * [UC3125] - Auditoria de Parcelamento de Contas
	 * 
	 * @author Ado Rocha
	 * @throws ControladorException
	 * @date 20/01/2014
	 */
	public Collection pesquisarParcelamentoContaUsuario(Integer idUsuario, Date dataInicial, Date dataFinal)
					throws ControladorException{

		Collection retorno = new ArrayList();

		try{
			Collection colecaoDados = repositorioTransacao.pesquisarParcelamentoContaUsuario(idUsuario, dataInicial,
							dataFinal);

			if(!colecaoDados.isEmpty()){

				Iterator colecaoDadosIterator = colecaoDados.iterator();

				while(colecaoDadosIterator.hasNext()){

					Object[] dados = (Object[]) colecaoDadosIterator.next();

					AuditoriaParcelamentoContasHelper helper = new AuditoriaParcelamentoContasHelper();

					// MPCCDFUN = dados[0]
					if(dados[0] != null){
						helper.setCodigo(dados[0].toString());
					}

					// MPCDTMPC = dados[1]
					if(dados[1] != null){
						String anoMesDia = dados[1].toString();
						String ano = anoMesDia.substring(0, 4);
						String mes = anoMesDia.substring(5, 7);
						String dia = anoMesDia.substring(8, 10);
						anoMesDia = ano + mes + dia;
						helper.setData(Util.converterAAAAMMDD(anoMesDia));
					}

					// MPCHRMPC = dados[2]
					if(dados[2] != null){
						String horaMinuto = (String) dados[2].toString();

						if(horaMinuto.length() == 5){
							horaMinuto = "0" + horaMinuto;
						}

						String hora = horaMinuto.substring(0, 2);
						String minuto = horaMinuto.substring(2, 4);
						String horaCompleta = hora + ":" + minuto;

						helper.setHora(horaCompleta);
					}

					// MPCAMINI = dados[3]
					if(dados[3] != null){
						helper.setReferenciaInicial(dados[3].toString());
					}

					// MPCAMFIN = dados[4]
					if(dados[4] != null){
						helper.setReferenciaFinal(dados[4].toString());
					}

					// MPCNNPREST = dados[7]
					if(dados[7] != null){
						helper.setPrestacao(dados[7].toString());
					}

					// MPCNNMATGER
					if(dados[14] != null){
						helper.setGerencia(dados[14].toString());
					}

					// MPCVLENTR = dados[8]
					if(dados[8] != null){
						helper.setValorEntrada(Util.formatarMoedaRealparaBigDecimal(dados[8].toString()));
					}

					// MPCVLPREST = dados[9]
					if(dados[9] != null){
						helper.setValorPrestacao(Util.formatarMoedaRealparaBigDecimal(dados[9].toString()));
					}

					// MPCVLDEBHIST = dados[10]
					if(dados[10] != null){
						helper.setValorDebitoHistorico(Util.formatarMoedaRealparaBigDecimal(dados[10].toString()));
					}

					// MPCVLDEBCORR = dados[11]
					if(dados[11] != null){
						helper.setValorDebitoAtualizado(Util.formatarMoedaRealparaBigDecimal(dados[11].toString()));
					}

					// MPCVLPARCEL = dados[13]
					if(dados[13] != null){
						helper.setValorParcelamento(Util.formatarMoedaRealparaBigDecimal(dados[13].toString()));
					}

					retorno.add(helper);

				}

			}

		}catch(ErroRepositorioException e){
			e.printStackTrace();
		}

		return retorno;
	}

	/**
	 * [UC3122] Auditoria de Alterações de Clientes
	 * 
	 * @author Eduardo Oliveira
	 * @throws ControladorException
	 * @date 01/03/2014
	 */
	public Collection consultarAlteracoesClientesFuncionario(Integer idFuncionario, Date dataInicial, Date dataFinal)
					throws ControladorException{

		Collection retorno = new ArrayList();

		try{
			Collection colecaoDados = repositorioTransacao.consultarAlteracoesClientesFuncionario(idFuncionario, dataInicial, dataFinal);

			if(!colecaoDados.isEmpty()){

				Iterator colecaoDadosIterator = colecaoDados.iterator();

				while(colecaoDadosIterator.hasNext()){

					Object[] dados = (Object[]) colecaoDadosIterator.next();

					AuditoriaAlteracoesClientesHelper helper = new AuditoriaAlteracoesClientesHelper();

					// Descricao do Campo
					if(dados[5] != null){
						String codigoOperacaoUsuario = (String) dados[5];
						if(codigoOperacaoUsuario.equals("I")){
							helper.setDescricaoCampo("INCLUSÃO DE USUÁRIO");
							helper.setConteudoAtual("");
							helper.setConteudoAnterior("");
						}else if(codigoOperacaoUsuario.equals("E")){
							helper.setDescricaoCampo("EXCLUSÃO DE USUÁRIO");
							helper.setConteudoAtual("");
							helper.setConteudoAnterior("");
						}else{
							if(dados[0] != null){
								helper.setDescricaoCampo((String) dados[0].toString().trim());

								if(dados[1] != null){
									String conteudoAtual = (String) dados[1];
									if(!conteudoAtual.equals("9999-12-31")){
										helper.setConteudoAtual(conteudoAtual);
									}else{
										helper.setConteudoAtual("");
									}
								}

								if(dados[2] != null){
									String conteudoAnterior = (String) dados[2];
									if(!conteudoAnterior.equals("9999-12-31")){
										helper.setConteudoAnterior(conteudoAnterior);
									}else{
										helper.setConteudoAnterior("");
									}
								}
							}else{
								helper.setDescricaoCampo("");
							}
						}
					}

					if(dados[3] != null){
						helper.setCodigo((String.valueOf(dados[3])));
					}

					if(dados[4] != null){
						Date data = (Date) dados[4];
						helper.setData(Util.formatarData(data));
					}

					if(dados[4] != null){
						Date hora = (Date) dados[4];
						helper.setHora(Util.formatarHoraSemSegundos(hora));
					}

					retorno.add(helper);

				}

			}

		}catch(ErroRepositorioException e){
			e.printStackTrace();
		}

		return retorno;
	}

	/**
	 * [UC3122] Auditoria de Alterações de Clientes
	 * 
	 * @author Eduardo Oliveira
	 * @throws ControladorException
	 * @date 01/03/2014
	 */
	public Collection consultarAlteracoesClientesUsuario(Integer idUsuario, Date dataInicial, Date dataFinal) throws ControladorException{

		Collection retorno = new ArrayList();

		try{
			Collection colecaoDados = repositorioTransacao.consultarAlteracoesClientesUsuario(idUsuario, dataInicial, dataFinal);

			if(!colecaoDados.isEmpty()){

				Iterator colecaoDadosIterator = colecaoDados.iterator();

				while(colecaoDadosIterator.hasNext()){

					Object[] dados = (Object[]) colecaoDadosIterator.next();

					AuditoriaAlteracoesClientesHelper helper = new AuditoriaAlteracoesClientesHelper();

					// Descricao do Campo
					if(dados[5] != null){
						String codigoOperacaoUsuario = (String) dados[5];
						if(codigoOperacaoUsuario.equals("I")){
							helper.setDescricaoCampo("INCLUSÃO DE USUÁRIO");
							helper.setConteudoAtual("");
							helper.setConteudoAnterior("");
						}else if(codigoOperacaoUsuario.equals("E")){
							helper.setDescricaoCampo("EXCLUSÃO DE USUÁRIO");
							helper.setConteudoAtual("");
							helper.setConteudoAnterior("");
						}else{
							if(dados[0] != null){
								helper.setDescricaoCampo((String) dados[0]);

								if(dados[1] != null){
									String conteudoAtual = (String) dados[1];
									if(!conteudoAtual.equals("9999-12-31")){
										helper.setConteudoAtual(conteudoAtual);
									}else{
										helper.setConteudoAtual("");
									}
								}

								if(dados[2] != null){
									String conteudoAnterior = (String) dados[2];
									if(!conteudoAnterior.equals("9999-12-31")){
										helper.setConteudoAnterior(conteudoAnterior);
									}else{
										helper.setConteudoAnterior("");
									}
								}
							}else{
								helper.setDescricaoCampo("");
							}
						}
					}

					if(dados[3] != null){
						helper.setCodigo((String.valueOf(dados[3])));
					}

					if(dados[4] != null){
						Date data = (Date) dados[4];
						helper.setData(Util.formatarData(data));
					}

					if(dados[4] != null){
						Date hora = (Date) dados[4];
						helper.setHora(Util.formatarHoraSemSegundos(hora));
					}

					retorno.add(helper);
				}

			}

		}catch(ErroRepositorioException e){
			e.printStackTrace();
		}

		return retorno;
	}
	
	/**
	 * [UC3127] - Auditoria de Transferênncia de Débitos
	 * [SB0001] -€“ Apresenta Dados da Auditoria
	 * Consulta por Funcionário
	 * 
	 * @author Eduardo Oliveira
	 * @date 01/02/2014
	 */
	public Collection consultarAuditoriaTransferenciaDebitosFuncionario(Integer idFuncionario, Date dataInicial, Date dataFinal)
					throws ControladorException{

		Collection retorno = new ArrayList();

		try{
			Collection colecaoDados = repositorioTransacao.consultarAuditoriaTransferenciaDebitosFuncionario(idFuncionario, dataInicial,
							dataFinal);

			if(!colecaoDados.isEmpty()){

				Iterator colecaoDadosIterator = colecaoDados.iterator();

				while(colecaoDadosIterator.hasNext()){

					Object[] dados = (Object[]) colecaoDadosIterator.next();

					AuditoriaTransferenciaDebitosHelper helper = new AuditoriaTransferenciaDebitosHelper();

					if(dados[0] != null){
						helper.setIdFuncionario((Integer) dados[0]);
					}

					if(dados[1] != null){
						String data = Util.formatarData((Date) dados[1]);
						helper.setDataT(data);
					}

					if(dados[2] != null){
						String horaMinuto = String.valueOf(dados[2]);
						if(horaMinuto.length() < 6){
							horaMinuto = "0" + String.valueOf(dados[2]);
							// Obtém a hora
							String hora = horaMinuto.substring(0, 2);
							// Obtém os minutos
							String minuto = horaMinuto.substring(2, 4);
							helper.setHora(hora + ":" + minuto);
						}else{
							// Obtém a hora
							String hora = String.valueOf(dados[2]).substring(0, 2);
							// Obtém os minutos
							String minuto = String.valueOf(dados[2]).substring(3, 5);
							helper.setHora(hora + ":" + minuto);
						}

					}

					if(dados[3] != null){
						helper.setReferenciaInicial((Integer) dados[3]);
					}

					if(dados[4] != null){
						helper.setReferenciaFinal((Integer) dados[4]);
					}

					if(dados[5] != null){
						helper.setUsuarioOrigem((Integer) dados[5]);
					}

					if(dados[6] != null){
						helper.setUsuarioDestino((Integer) dados[6]);
					}

					if(dados[7] != null){
						helper.setTotalPrestacao(dados[7].toString());
					}

					if(dados[8] != null){
						if(dados[8].toString().equals("0.0")){
							helper.setValorEntrada("0");
						}else{
							helper.setValorEntrada(dados[8].toString().replace('.', ','));
						}
					}

					if(dados[9] != null){
						if(dados[9].toString().equals("0.0")){
							helper.setValorPrestacao("0");
						}else{

							helper.setValorPrestacao(dados[9].toString().replace('.', ','));
						}

					}

					if(dados[10] != null){
						if(dados[10].toString().equals("0.0")){
							helper.setValorDebitoHistorico("0");
						}else{
							helper.setValorDebitoHistorico(dados[10].toString().replace('.', ','));
						}
					}

					if(dados[11] != null){
						if(dados[11].toString().equals("0.0")){
							helper.setValorDebitoAtual("0");
						}else{
							helper.setValorDebitoAtual(dados[11].toString().replace('.', ','));
						}
					}

					if(dados[12] != null){
						if(dados[12].toString().equals("0.0")){
							helper.setValorSacadoIncluso("0");
						}else{
							helper.setValorSacadoIncluso(dados[12].toString().replace('.', ','));
						}
					}

					if(dados[13] != null){
						if(dados[13].toString().equals("0.0")){
							helper.setValorParcelado("0");
						}else{
							helper.setValorParcelado(dados[13].toString().replace('.', ','));
						}
					}

					helper.setCodigo("");

					retorno.add(helper);

				}

			}

		}catch(ErroRepositorioException e){
			e.printStackTrace();
		}

		return retorno;
	}

	/**
	 * [UC3127] - Auditoria de Transferencia de Debitos
	 * [SB0001] - Apresenta Dados da Auditoria
	 * Consulta por Usuario
	 * 
	 * @author Eduardo Oliveira
	 * @date 01/02/2014
	 */
	public Collection consultarAuditoriaTransferenciaDebitosUsuario(Integer idUsuarioOrigem, Integer idUsuarioDestino, Date dataInicial,
					Date dataFinal)
					throws ControladorException{

		Collection retorno = new ArrayList();

		try{
			Collection colecaoDados = repositorioTransacao.consultarAuditoriaTransferenciaDebitosUsuario(idUsuarioOrigem, idUsuarioDestino,
							dataInicial, dataFinal);

			if(!colecaoDados.isEmpty()){

				Iterator colecaoDadosIterator = colecaoDados.iterator();

				while(colecaoDadosIterator.hasNext()){

					Object[] dados = (Object[]) colecaoDadosIterator.next();

					AuditoriaTransferenciaDebitosHelper helper = new AuditoriaTransferenciaDebitosHelper();

					if(dados[0] != null){
						helper.setIdFuncionario((Integer) dados[0]);
					}

					if(dados[1] != null){
						String data = Util.formatarData((Date) dados[1]);
						helper.setDataT(data);
					}

					if(dados[2] != null){
						String horaMinuto = String.valueOf(dados[2]);
						if(horaMinuto.length() < 6){
							horaMinuto = "0" + horaMinuto;

							// Obtém a hora
							String hora = horaMinuto.substring(0, 2);
							// Obtém os minutos
							String minuto = horaMinuto.substring(3, 5);
							helper.setHora(hora + ":" + minuto);
						}else{
							// Obtém a hora
							String hora = horaMinuto.substring(0, 2);
							// Obtém os minutos
							String minuto = horaMinuto.substring(3, 5);
							helper.setHora(hora + ":" + minuto);
						}
					}

					if(dados[3] != null){
						helper.setReferenciaInicial((Integer) dados[3]);
					}

					if(dados[4] != null){
						helper.setReferenciaFinal((Integer) dados[4]);
					}

					if(dados[5] != null){
						helper.setUsuarioOrigem((Integer) dados[5]);
					}

					if(dados[6] != null){
						helper.setUsuarioDestino((Integer) dados[6]);
					}

					if(dados[7] != null){
						helper.setTotalPrestacao(dados[7].toString());
					}

					if(dados[8] != null){
						if(dados[8].toString().equals("0.0")){
							helper.setValorEntrada("0");
						}else{
							helper.setValorEntrada(dados[8].toString().replace('.', ','));
						}
					}

					if(dados[9] != null){
						if(dados[9].toString().equals("0.0")){
							helper.setValorPrestacao("0");
						}else{

							helper.setValorPrestacao(dados[9].toString().replace('.', ','));
						}

					}

					if(dados[10] != null){
						if(dados[10].toString().equals("0.0")){
							helper.setValorDebitoHistorico("0");
						}else{
							helper.setValorDebitoHistorico(dados[10].toString().replace('.', ','));
						}
					}

					if(dados[11] != null){
						if(dados[11].toString().equals("0.0")){
							helper.setValorDebitoAtual("0");
						}else{
							helper.setValorDebitoAtual(dados[11].toString().replace('.', ','));
						}
					}

					if(dados[12] != null){
						if(dados[12].toString().equals("0.0")){
							helper.setValorSacadoIncluso("0");
						}else{
							helper.setValorSacadoIncluso(dados[12].toString().replace('.', ','));
						}
					}

					if(dados[13] != null){
						if(dados[13].toString().equals("0.0")){
							helper.setValorParcelado("0");
						}else{
							helper.setValorParcelado(dados[13].toString().replace('.', ','));
						}
					}
					helper.setCodigo("");

					retorno.add(helper);

				}

			}

		}catch(ErroRepositorioException e){
			e.printStackTrace();
		}

		return retorno;
	}

	/**
	 * [UC3126] Auditoria de Serviços a Cobrar
	 * 
	 * @author Eduardo Oliveira
	 * @throws ControladorException
	 * @date 08/03/2014
	 */
	public Collection consultarServicosACobrarFuncionario(Integer idFuncionario, Date dataInicial, Date dataFinal)
					throws ControladorException{

		Collection retorno = new ArrayList();

		try{
			Collection colecaoDados = repositorioTransacao.consultarServicosACobrarFuncionario(idFuncionario, dataInicial, dataFinal);

			if(!colecaoDados.isEmpty()){

				Iterator colecaoDadosIterator = colecaoDados.iterator();

				while(colecaoDadosIterator.hasNext()){

					Object[] dados = (Object[]) colecaoDadosIterator.next();

					AuditoriaServicosACobrarHelper helper = new AuditoriaServicosACobrarHelper();

					// Descricao do Campo
					if(dados[5] != null){
						String codigoOperacaoTransacao = (String) dados[5];
						if(codigoOperacaoTransacao.equals("ICBS")){
							helper.setDescricaoCampo("INCLU. SERV. A COBRAR");
							helper.setConteudoAtual("");
							helper.setConteudoAnterior("");
						}else if(codigoOperacaoTransacao.equals("CCBS")){
							helper.setDescricaoCampo("EXCLU. SERV. A COBRAR");
							helper.setConteudoAtual("");
							helper.setConteudoAnterior("");
						}else{
							if(dados[0] != null){
								helper.setDescricaoCampo((String) dados[0].toString().trim());

								if(dados[1] != null){
									String conteudoAtual = (String) dados[1];
									helper.setConteudoAtual(conteudoAtual);
								}

								if(dados[2] != null){
									String conteudoAnterior = (String) dados[2];
									helper.setConteudoAnterior(conteudoAnterior);
								}
							}else{
								helper.setDescricaoCampo("");
							}
						}
					}

					if(dados[3] != null){
						helper.setCodigo((String.valueOf(dados[3])));
					}

					if(dados[4] != null){
						Date data = (Date) dados[4];
						helper.setData(Util.formatarData(data));
					}

					if(dados[4] != null){
						Date hora = (Date) dados[4];
						helper.setHora(Util.formatarHoraSemSegundos(hora));
					}

					retorno.add(helper);

				}

			}

		}catch(ErroRepositorioException e){
			e.printStackTrace();
		}

		return retorno;
	}

	/**
	 * [UC3126] Auditoria de Serviços a Cobrar
	 * 
	 * @author Eduardo Oliveira
	 * @throws ControladorException
	 * @date 08/03/2014
	 */
	public Collection consultarServicosACobrarUsuario(Integer idUsuario, Date dataInicial, Date dataFinal) throws ControladorException{

		Collection retorno = new ArrayList();

		try{
			Collection colecaoDados = repositorioTransacao.consultarServicosACobrarUsuario(idUsuario, dataInicial, dataFinal);

			if(!colecaoDados.isEmpty()){

				Iterator colecaoDadosIterator = colecaoDados.iterator();

				while(colecaoDadosIterator.hasNext()){

					Object[] dados = (Object[]) colecaoDadosIterator.next();

					AuditoriaServicosACobrarHelper helper = new AuditoriaServicosACobrarHelper();

					// Descricao do Campo
					if(dados[5] != null){
						String codigoOperacaoTransacao = (String) dados[5];
						if(codigoOperacaoTransacao.equals("ICBS")){
							helper.setDescricaoCampo("INCLU. SERV. A COBRAR");
							helper.setConteudoAtual("");
							helper.setConteudoAnterior("");
						}else if(codigoOperacaoTransacao.equals("CCBS")){
							helper.setDescricaoCampo("EXCLU. SERV. A COBRAR");
							helper.setConteudoAtual("");
							helper.setConteudoAnterior("");
						}else{
							if(dados[0] != null){
								helper.setDescricaoCampo((String) dados[0].toString().trim());

								if(dados[1] != null){
									String conteudoAtual = (String) dados[1];
									helper.setConteudoAtual(conteudoAtual);
								}

								if(dados[2] != null){
									String conteudoAnterior = (String) dados[2];
									helper.setConteudoAnterior(conteudoAnterior);
								}
							}else{
								helper.setDescricaoCampo("");
							}
						}
					}

					if(dados[3] != null){
						helper.setCodigo((String.valueOf(dados[3])));
					}

					if(dados[4] != null){
						Date data = (Date) dados[4];
						helper.setData(Util.formatarData(data));
					}

					if(dados[4] != null){
						Date hora = (Date) dados[4];
						helper.setHora(Util.formatarHoraSemSegundos(hora));
					}

					retorno.add(helper);

				}

			}

		}catch(ErroRepositorioException e){
			e.printStackTrace();
		}

		return retorno;
	}

}