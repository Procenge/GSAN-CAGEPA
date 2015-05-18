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
 * Saulo Vasconcelos de Lima
 */

package gcom.cadastro.endereco;

import gcom.agenciavirtual.cadastro.endereco.LogradouroJSONHelper;
import gcom.arrecadacao.ControladorArrecadacaoLocal;
import gcom.arrecadacao.ControladorArrecadacaoLocalHome;
import gcom.arrecadacao.pagamento.bean.PagamentoHistoricoAdmiteDevolucaoHelper;
import gcom.atendimentopublico.registroatendimento.ControladorRegistroAtendimentoLocal;
import gcom.atendimentopublico.registroatendimento.ControladorRegistroAtendimentoLocalHome;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoSolicitante;
import gcom.cadastro.cliente.*;
import gcom.cadastro.endereco.bean.AtualizarLogradouroBairroHelper;
import gcom.cadastro.endereco.bean.AtualizarLogradouroCepHelper;
import gcom.cadastro.geografico.*;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.ControladorLocalidadeLocal;
import gcom.cadastro.localidade.ControladorLocalidadeLocalHome;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.faturamento.ControladorFaturamentoLocal;
import gcom.faturamento.ControladorFaturamentoLocalHome;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.*;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ParametroGeral;

import java.math.BigDecimal;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import br.com.procenge.parametrosistema.api.ControladorParametroSistema;
import br.com.procenge.util.SpringBeanLocator;

/**
 * Description of the Class
 * 
 * @author compesa
 * @created 19 de Julho de 2005
 */
public class ControladorEnderecoSEJB
				implements SessionBean {

	private static final long serialVersionUID = 1L;

	private IRepositorioEndereco repositorioEndereco = null;

	private IRepositorioUtil repositorioUtil = null;

	SessionContext sessionContext;

	/**
	 * < <Descrição do método>>
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

		repositorioEndereco = RepositorioEnderecoHBM.getInstancia();
		repositorioUtil = RepositorioUtilHBM.getInstancia();
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

	protected ControladorFaturamentoLocal getControladorFaturamento(){

		ControladorFaturamentoLocalHome localHome = null;
		ControladorFaturamentoLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorFaturamentoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_FATURAMENTO_SEJB);
			local = localHome.create();

			return local;

		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
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

	public void atualizarCep(Cep cep, Usuario usuarioLogado) throws ErroRepositorioException, ControladorException{

		try{
			FiltroCep filtroCep = new FiltroCep();
			// Seta o filtro para buscar o cep na base
			filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.CEPID, new Integer(cep.getCepId())));

			// Procura o cep na base
			Collection colecaoCep = RepositorioUtilHBM.getInstancia().pesquisar(filtroCep, Cep.class.getName());
			Cep cepNaBase = (Cep) Util.retonarObjetoDeColecao(colecaoCep);

			// Verifica se o cep já foi atualizado por outro usuário
			// durante
			// esta atualização
			if(cepNaBase.getUltimaAlteracao().after(cep.getUltimaAlteracao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}

			// Atualiza a data de última alteração
			cep.setUltimaAlteracao(new Date());

			// Inicio Registrando transações
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_CEP_ATUALIZAR, cep.getCepId(),
							cep.getCepId(), new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_CEP_ATUALIZAR);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			cep.setOperacaoEfetuada(operacaoEfetuada);
			cep.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			registradorOperacao.registrarOperacao(cep);

			// Fim - Registrando as transações

			// Atualiza o cep
			RepositorioUtilHBM.getInstancia().atualizar(cep);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * inseri um logradouro na base
	 * 
	 * @param logradouro
	 *            Description of the Parameter
	 * @param bairro
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 * @throws ControladorException
	 */
	public Integer inserirLogradouro(Logradouro logradouro, Collection<Bairro> colecaoBairros, Collection<Cep> colecaoCeps)
					throws ControladorException{

		Integer chaveGeradaLogradouro = null;

		// ------------ REGISTRAR TRANSAÇÃO
		// LOGRADOURO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_LOGRADOURO_INSERIR,
						new UsuarioAcaoUsuarioHelper(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_LOGRADOURO_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		logradouro.setOperacaoEfetuada(operacaoEfetuada);
		logradouro.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(logradouro);
		// ------------ REGISTRAR TRANSAÇÃO
		// LOGRADOURO----------------------------

		chaveGeradaLogradouro = (Integer) getControladorUtil().inserir(logradouro);

		logradouro.setId(chaveGeradaLogradouro);

		/*
		 * Inseri na tabela LOGRADOURO_BAIRRO todos os bairros selecionados pelo
		 * usuário para um determinado logradouro
		 */

		this.inserirLogradouroBairro(logradouro, colecaoBairros, registradorOperacao);

		/*
		 * Inseri na tabela LOGRADOURO_CEP todos os ceps selecionados pelo
		 * usuário para um determinado logradouro
		 */
		this.inserirLogradouroCep(logradouro, colecaoCeps, registradorOperacao);

		return chaveGeradaLogradouro;
	}

	/**
	 * Inseri na tabela LOGRADOURO_BAIRRO todos os bairros selecionados pelo
	 * usuário para um determinado logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 03/05/2006
	 * @param logradouro
	 *            ,
	 *            colecaoBairros
	 * @return void
	 */
	public void inserirLogradouroBairro(Logradouro logradouro, Collection<Bairro> colecaoBairros, RegistradorOperacao registradorOperacao)
					throws ControladorException{

		Iterator iteratorColecaoBairros = colecaoBairros.iterator();
		Bairro bairro = null;
		LogradouroBairro logradouroBairro = null;

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_LOGRADOURO_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		while(iteratorColecaoBairros.hasNext()){
			bairro = (Bairro) iteratorColecaoBairros.next();

			if(!bairro.getMunicipio().getId().equals(logradouro.getMunicipio().getId())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.logradouro_municipio_bairro_invalido", null, bairro.getNome(), logradouro
								.getMunicipio().getNome());
			}

			logradouroBairro = new LogradouroBairro();

			// ------------ REGISTRAR TRANSAÇÃO LOGRADOURO BAIRRO--------------
			logradouroBairro.setOperacaoEfetuada(operacaoEfetuada);
			logradouroBairro.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(logradouroBairro);
			// ------------ REGISTRAR TRANSAÇÃO LOGRADOURO
			// BAIRRO----------------

			logradouroBairro.setLogradouro(logradouro);
			logradouroBairro.setBairro(bairro);
			logradouroBairro.setUltimaAlteracao(new Date());

			this.getControladorUtil().inserir(logradouroBairro);
		}
	}

	/**
	 * Altera na tabela LOGRADOURO_BAIRRO todos os bairros selecionados pelo
	 * usuário para um determinado logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 03/05/2006
	 * @param logradouro
	 *            ,
	 *            colecaoBairros
	 * @return void
	 */
	public void atualizarLogradouroBairro(Logradouro logradouro, Collection<Bairro> colecaoBairros,
					Collection<AtualizarLogradouroBairroHelper> colecaoBairrosAtualizacao, RegistradorOperacao registradorOperacao)
					throws ControladorException{

		Iterator iteratorColecaoBairros = colecaoBairros.iterator();
		Bairro bairro = null;
		LogradouroBairro logradouroBairro = null;
		LogradouroBairro logradouroBairroNaBase = null;

		Collection colecaoBairroNaBase = this.obterTodosBairrosPorLogradouro(logradouro);
		Iterator iteratorColecaoBairroNaBase = null;
		Bairro bairroNaBase = null;

		Iterator iterator = null;
		AtualizarLogradouroBairroHelper atualizarLogradouroBairroHelper = null;
		boolean atualizacaoRealizada = false;

		if(colecaoBairroNaBase != null && !colecaoBairroNaBase.isEmpty()){

			iteratorColecaoBairroNaBase = colecaoBairroNaBase.iterator();

			while(iteratorColecaoBairroNaBase.hasNext()){

				bairroNaBase = (Bairro) iteratorColecaoBairroNaBase.next();

				if(!colecaoBairros.contains(bairroNaBase)){

					logradouroBairroNaBase = this.pesquisarAssociacaoLogradouroBairro(bairroNaBase.getId(), logradouro.getId());

					if(logradouroBairroNaBase != null){

						/*
						 * Verificando existência de atualização
						 */
						// =============================================================================================
						if(colecaoBairrosAtualizacao != null && !colecaoBairrosAtualizacao.isEmpty()){

							iterator = colecaoBairrosAtualizacao.iterator();
							atualizarLogradouroBairroHelper = null;
							atualizacaoRealizada = false;

							while(iterator.hasNext()){

								atualizarLogradouroBairroHelper = (AtualizarLogradouroBairroHelper) iterator.next();

								if(logradouroBairroNaBase.getId().equals(atualizarLogradouroBairroHelper.getLogradouroBairro().getId())){

									/*
									 * Caso o bairro atual seja "BAIRRO NAO
									 * INFORMADO" e o novo bairro já tenho sido
									 * adicionado na coleção , removeremos
									 * apenas o "BAIRRO NAO INFORMADO" e todos
									 * os imóveis que estiverem associados ao
									 * mesmo, serão automaticamente associados
									 * ao bairro novo
									 * Colocado em 22/02/2007 por Raphael
									 * Rossiter
									 */
									/*
									 * if
									 * (this.verificarBairroTipoBairroNaoInformado(
									 * logradouroBairroNaBase
									 * .getBairro())){
									 */

									LogradouroBairro logradouroBairroNovo = this.pesquisarAssociacaoLogradouroBairro(
													atualizarLogradouroBairroHelper.getBairro().getId(), logradouro.getId());

									if(logradouroBairroNovo != null){

										// Atualizar dependências
										this.atualizarLogradouroBairroDependencias(logradouroBairroNaBase, logradouroBairroNovo);
									}else{

										// Atualização no BD
										atualizarLogradouroBairroHelper.getLogradouroBairro().setBairro(
														atualizarLogradouroBairroHelper.getBairro());

										this.atualizarObjetoLogradouroBairro(atualizarLogradouroBairroHelper.getLogradouroBairro(),
														registradorOperacao);
									}

									/*
									 * } else{
									 * //Atualização no BD
									 * atualizarLogradouroBairroHelper
									 * .getLogradouroBairro().setBairro(
									 * atualizarLogradouroBairroHelper
									 * .getBairro());
									 * this
									 * .atualizarObjetoLogradouroBairro(atualizarLogradouroBairroHelper
									 * .getLogradouroBairro(),
									 * registradorOperacao); }
									 */

									atualizacaoRealizada = true;
									break;
								}
							}

							if(!atualizacaoRealizada){
								getControladorUtil().remover(logradouroBairroNaBase);
							}
						}else{
							getControladorUtil().remover(logradouroBairroNaBase);
						}
						// =============================================================================================
					}
				}
			}
		}

		while(iteratorColecaoBairros.hasNext()){
			bairro = (Bairro) iteratorColecaoBairros.next();

			if(!bairro.getMunicipio().getId().equals(logradouro.getMunicipio().getId())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.logradouro_municipio_bairro_invalido", null, bairro.getNome(), logradouro
								.getMunicipio().getNome());
			}

			logradouroBairroNaBase = this.pesquisarAssociacaoLogradouroBairro(bairro.getId(), logradouro.getId());

			// Verificando se o bairro em questão já foi atualizado
			// =========================================================================================================

			atualizacaoRealizada = false;
			if(logradouroBairroNaBase == null && colecaoBairrosAtualizacao != null && !colecaoBairrosAtualizacao.isEmpty()){

				iterator = colecaoBairrosAtualizacao.iterator();
				atualizarLogradouroBairroHelper = null;

				while(iterator.hasNext()){

					atualizarLogradouroBairroHelper = (AtualizarLogradouroBairroHelper) iterator.next();

					if(bairro.getId().equals(atualizarLogradouroBairroHelper.getBairro().getId())){

						atualizacaoRealizada = true;
						break;
					}
				}

			}
			// =========================================================================================================

			if(logradouroBairroNaBase == null && !atualizacaoRealizada){

				logradouroBairro = new LogradouroBairro();
				logradouroBairro.setLogradouro(logradouro);
				logradouroBairro.setBairro(bairro);
				logradouroBairro.setUltimaAlteracao(new Date());

				// ------------ REGISTRAR TRANSAÇÃO LOGRADOURO
				// BAIRRO--------------
				Operacao operacao = new Operacao();
				operacao.setId(Operacao.OPERACAO_LOGRADOURO_ATUALIZAR);
				OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
				operacaoEfetuada.setOperacao(operacao);
				logradouroBairro.setOperacaoEfetuada(operacaoEfetuada);
				logradouroBairro.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(logradouroBairro);
				// ------------ REGISTRAR TRANSAÇÃO LOGRADOURO
				// BAIRRO---------------

				this.getControladorUtil().inserir(logradouroBairro);

			}
		}
	}

	/**
	 * Altera todas as tebelas que tenham associação com LOGRADOURO_BAIRRO
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2004
	 * @param logradouroBairroAntigo
	 *            ,
	 *            logradouroBairroNovo
	 * @return void
	 */
	public void atualizarLogradouroBairroDependencias(LogradouroBairro logradouroBairroAntigo, LogradouroBairro logradouroBairroNovo)
					throws ControladorException{

		// Imóvel
		this.getControladorImovel().atualizarLogradouroBairro(logradouroBairroAntigo, logradouroBairroNovo);

		// Agencia
		this.getControladorArrecadacao().atualizarLogradouroBairro(logradouroBairroAntigo, logradouroBairroNovo);

		// RegistroAtendimento
		this.getControladorRegistroAtendimento().atualizarLogradouroBairro(logradouroBairroAntigo, logradouroBairroNovo);

		// RegistroAtendimentoSolicitante
		this.getControladorRegistroAtendimento().atualizarLogradouroBairroSolicitante(logradouroBairroAntigo, logradouroBairroNovo);

		// Localidade
		this.getControladorLocalidade().atualizarLogradouroBairro(logradouroBairroAntigo, logradouroBairroNovo);

		// GerenciaRegional
		this.getControladorLocalidade().atualizarLogradouroBairroGerenciaRegional(logradouroBairroAntigo, logradouroBairroNovo);

		// ClienteEndereco
		this.getControladorCliente().atualizarLogradouroBairro(logradouroBairroAntigo, logradouroBairroNovo);

		// Remove o LogradouroBairro que foi substituído
		this.getControladorUtil().remover(logradouroBairroAntigo);

	}

	/**
	 * Altera todas as tebelas que tenham associação com LOGRADOURO_CEP
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2004
	 * @param logradouroCepAntigo
	 *            ,
	 *            logradouroCepNovo
	 * @return void
	 */
	public void atualizarLogradouroCepDependencias(LogradouroCep logradouroCepAntigo, LogradouroCep logradouroCepNovo)
					throws ControladorException{

		// Imóvel
		this.getControladorImovel().atualizarLogradouroCep(logradouroCepAntigo, logradouroCepNovo);

		// Agencia
		this.getControladorArrecadacao().atualizarLogradouroCep(logradouroCepAntigo, logradouroCepNovo);

		// RegistroAtendimento
		this.getControladorRegistroAtendimento().atualizarLogradouroCep(logradouroCepAntigo, logradouroCepNovo);

		// RegistroAtendimentoSolicitante
		this.getControladorRegistroAtendimento().atualizarLogradouroCepSolicitante(logradouroCepAntigo, logradouroCepNovo);

		// Localidade
		this.getControladorLocalidade().atualizarLogradouroCep(logradouroCepAntigo, logradouroCepNovo);

		// GerenciaRegional
		this.getControladorLocalidade().atualizarLogradouroCepGerenciaRegional(logradouroCepAntigo, logradouroCepNovo);

		// ClienteEndereco
		this.getControladorCliente().atualizarLogradouroCep(logradouroCepAntigo, logradouroCepNovo);

		// Remove o LogradouroCep que foi substituído
		this.getControladorUtil().remover(logradouroCepAntigo);

	}

	/**
	 * Altera na tabela LOGRADOURO_BAIRRO
	 * 
	 * @author Raphael Rossiter
	 * @date 14/11/2006
	 * @param logradouroBairro
	 *            ,
	 *            RegistradorOperacao
	 * @return void
	 */
	public void atualizarObjetoLogradouroBairro(LogradouroBairro logradouroBairro, RegistradorOperacao registradorOperacao)
					throws ControladorException{

		FiltroLogradouroBairro filtroLogradouroBairro = new FiltroLogradouroBairro();

		// Seta o filtro para buscar o logradouroBairro na base
		filtroLogradouroBairro.adicionarParametro(new ParametroSimples(FiltroLogradouroBairro.ID, logradouroBairro.getId()));

		// Procura o logradouroBairro na base
		LogradouroBairro logradouroBairroNaBase = (LogradouroBairro) ((List) (getControladorUtil().pesquisar(filtroLogradouroBairro,
						LogradouroBairro.class.getName()))).get(0);

		// Verificar se o logradouroBairro já foi atualizado por outro usuário
		// durante
		// esta atualização
		if(logradouroBairroNaBase.getUltimaAlteracao().after(logradouroBairro.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		// Atualiza a data de última alteração
		logradouroBairro.setUltimaAlteracao(new Date());

		// ------------ REGISTRAR TRANSAÇÃO LOGRADOURO
		// BAIRRO--------------
		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_LOGRADOURO_ATUALIZAR);
		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		logradouroBairro.setOperacaoEfetuada(operacaoEfetuada);
		logradouroBairro.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(logradouroBairro);
		// ------------ REGISTRAR TRANSAÇÃO LOGRADOURO
		// BAIRRO---------------

		// Atualiza o logradouro
		getControladorUtil().atualizar(logradouroBairro);

	}

	/**
	 * Inseri na tabela LOGRADOURO_CEP todos os ceps selecionados pelo usuário
	 * para um determinado logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 03/05/2006
	 * @author eduardo henrique
	 * @date 18/02/2009
	 *       Alteração no método para permitir associar um cep a mais de um Logradouro.
	 * @param logradouro
	 *            ,
	 *            colecaoCeps
	 * @return void
	 */
	public void inserirLogradouroCep(Logradouro logradouro, Collection<Cep> colecaoCeps, RegistradorOperacao registradorOperacao)
					throws ControladorException{

		Iterator iteratorColecaoCeps = colecaoCeps.iterator();
		Cep cep = null;
		LogradouroCep logradouroCep = null;
		Logradouro logradouroAssociado = null;
		Cep cepInicial = null;
		Cep cepUnico = null;

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_LOGRADOURO_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		while(iteratorColecaoCeps.hasNext()){
			cep = (Cep) iteratorColecaoCeps.next();

			if(!cep.getMunicipio().equalsIgnoreCase(logradouro.getMunicipio().getNome())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.logradouro_municipio_cep_invalido", null, cep.getCepFormatado(), logradouro
								.getMunicipio().getNome());
			}

			cepInicial = this.obterCepInicialMunicipio(logradouro.getMunicipio());
			cepUnico = this.obterCepUnicoMunicipio(logradouro.getMunicipio());

			/*
			 * Quando o CEP for único de município ou inicial de município não
			 * deve haver a crítica CEP já associado a outro logradouro
			 */

			if(cepUnico != null || cepInicial != null){

				if(cepUnico != null && !cepUnico.getCepId().equals(cep.getCepId())){

					logradouroAssociado = this.verificarCepAssociadoOutroLogradouro(cep);

					if(logradouroAssociado != null){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.logradouro_cep_ja_associado", null, cep.getCepFormatado(),
										logradouroAssociado.getId().toString() + " - " + logradouroAssociado.getDescricaoFormatada());
					}

				}

				if(cepInicial != null && !cepInicial.getCepId().equals(cep.getCepId())){

					logradouroAssociado = this.verificarCepAssociadoOutroLogradouro(cep);

					if(logradouroAssociado != null){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.logradouro_cep_ja_associado", null, cep.getCepFormatado(),
										logradouroAssociado.getId().toString() + " - " + logradouroAssociado.getDescricaoFormatada());
					}

				}else if(cepInicial != null && cepInicial.getCepId().equals(cep.getCepId()) && colecaoCeps.size() > 1){

					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.logradouro_cep_inicial_municipio_unico");
				}else if(cepInicial != null && cepInicial.getCepId().equals(cep.getCepId())){
					throw new ControladorException("atencao.endereco_cep_logradouro");

				}

			}

			/*
			 * else {
			 * logradouroAssociado = this
			 * .verificarCepAssociadoOutroLogradouro(cep);
			 * if (logradouroAssociado != null) {
			 * sessionContext.setRollbackOnly();
			 * throw new ControladorException(
			 * "atencao.logradouro_cep_ja_associado", null, cep
			 * .getCepFormatado(), logradouroAssociado
			 * .getId().toString()
			 * + " - "
			 * + logradouroAssociado
			 * .getDescricaoFormatada());
			 * }
			 * }
			 */

			logradouroCep = new LogradouroCep();

			// ------------ REGISTRAR TRANSAÇÃO LOGRADOURO CEP--------------
			logradouroCep.setOperacaoEfetuada(operacaoEfetuada);
			logradouroCep.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(logradouroCep);
			// ------------ REGISTRAR TRANSAÇÃO LOGRADOURO CEP----------------
			logradouroCep.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
			logradouroCep.setLogradouro(logradouro);
			logradouroCep.setCep(cep);
			logradouroCep.setUltimaAlteracao(new Date());

			this.getControladorUtil().inserir(logradouroCep);

		}
	}

	/**
	 * Inseri na tabela LOGRADOURO_CEP todos os ceps selecionados pelo usuário
	 * para um determinado logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 03/05/2006
	 * @param logradouro
	 *            ,
	 *            colecaoCeps
	 * @return void
	 */
	public void atualizarLogradouroCep(Logradouro logradouro, Collection<Cep> colecaoCeps,
					Collection<AtualizarLogradouroCepHelper> colecaoCepsAtualizacao, RegistradorOperacao registradorOperacao)
					throws ControladorException{

		Iterator iteratorColecaoCeps = colecaoCeps.iterator();
		Cep cep = null;
		LogradouroCep logradouroCep = null;
		Logradouro logradouroAssociado = null;
		Cep cepInicial = null;
		Cep cepUnico = null;

		cepInicial = this.obterCepInicialMunicipio(logradouro.getMunicipio());
		cepUnico = this.obterCepUnicoMunicipio(logradouro.getMunicipio());

		Collection colecaoLogradouroCepNaBase = this.pesquisarAssociacaoLogradouroCepPorLogradouro(logradouro);

		LogradouroCep logradouroCepNaBase = null;

		// Variáveis que auxiliam na identificação de uma atualização
		Iterator iterator = null;
		AtualizarLogradouroCepHelper atualizarLogradouroCepHelper = null;
		boolean atualizacaoRealizada = false;

		if(colecaoLogradouroCepNaBase != null && !colecaoLogradouroCepNaBase.isEmpty()){

			Iterator iteratorColecaoLogradouroCepNaBase = colecaoLogradouroCepNaBase.iterator();

			while(iteratorColecaoLogradouroCepNaBase.hasNext()){

				logradouroCepNaBase = (LogradouroCep) iteratorColecaoLogradouroCepNaBase.next();

				if(cepInicial != null){

					/*
					 * Caso um Cep que esteja associado, na base, com o
					 * logradouro que estamos querendo atualizar não esteja
					 * presenta na coleção gerada pelo usuário E o cep que faz
					 * parte desta associação não seja o cep inicial do
					 * município
					 */
					/*
					 * if (!colecaoCeps.contains(logradouroCepNaBase.getCep()) &&
					 * !logradouroCepNaBase.getCep().getCepId().equals(cepInicial.getCepId())) {
					 */
					if(!colecaoCeps.contains(logradouroCepNaBase.getCep())){

						/*
						 * Verificando existência de atualização
						 */
						// =============================================================================================
						if(colecaoCepsAtualizacao != null && !colecaoCepsAtualizacao.isEmpty()){

							iterator = colecaoCepsAtualizacao.iterator();
							atualizarLogradouroCepHelper = null;
							atualizacaoRealizada = false;

							while(iterator.hasNext()){

								atualizarLogradouroCepHelper = (AtualizarLogradouroCepHelper) iterator.next();

								if(logradouroCepNaBase.getCep().getCepId()
												.equals(atualizarLogradouroCepHelper.getLogradouroCep().getCep().getCepId())){

									/*
									 * Caso o cep atual seja inicial de
									 * município e o novo cep já tenho sido
									 * adicionado na coleção
									 * colecaoCepSelecionadosUsuario,
									 * removeremos apenas o cep inicial de
									 * município e todos os imóveis que
									 * estiverem associados ao mesmo, serão
									 * automaticamente associados ao cep de
									 * logradouro
									 * Colocado em 22/02/2007 por Raphael
									 * Rossiter
									 */
									/*
									 * if
									 * (this.verificarCepInicialMunicipio(logradouroCepNaBase.getCep(
									 * ))){
									 */

									LogradouroCep logradouroCepNovo = this.pesquisarAssociacaoLogradouroCep(atualizarLogradouroCepHelper
													.getCep().getCepId(), logradouro.getId());

									if(logradouroCepNovo != null){

										// Atualizar dependências
										this.atualizarLogradouroCepDependencias(logradouroCepNaBase, logradouroCepNovo);
									}else{

										// Atualização no BD
										atualizarLogradouroCepHelper.getLogradouroCep().setCep(atualizarLogradouroCepHelper.getCep());

										this.atualizarObjetoLogradouroCep(atualizarLogradouroCepHelper.getLogradouroCep(),
														registradorOperacao);
									}

									/*
									 * } else{
									 * //Atualização no BD
									 * atualizarLogradouroCepHelper
									 * .getLogradouroCep().setCep(
									 * atualizarLogradouroCepHelper .getCep());
									 * this
									 * .atualizarObjetoLogradouroCep(atualizarLogradouroCepHelper
									 * .getLogradouroCep(),
									 * registradorOperacao); }
									 */

									atualizacaoRealizada = true;
									break;
								}
							}

							if(!atualizacaoRealizada){
								getControladorUtil().remover(logradouroCepNaBase);
							}
						}else{
							getControladorUtil().remover(logradouroCepNaBase);
						}
						// =============================================================================================

					}

				}
				/*
				 * Caso um Cep que esteja associado, na base, com o logradouro
				 * que estamos querendo atualizar não esteja presenta na coleção
				 * gerada pelo usuário
				 */
				else if(!colecaoCeps.contains(logradouroCepNaBase.getCep())){

					/*
					 * Verificando existência de atualização
					 */
					// =============================================================================================
					if(colecaoCepsAtualizacao != null && !colecaoCepsAtualizacao.isEmpty()){

						iterator = colecaoCepsAtualizacao.iterator();
						atualizarLogradouroCepHelper = null;
						atualizacaoRealizada = false;

						while(iterator.hasNext()){

							atualizarLogradouroCepHelper = (AtualizarLogradouroCepHelper) iterator.next();

							if(logradouroCepNaBase.getCep().getCepId()
											.equals(atualizarLogradouroCepHelper.getLogradouroCep().getCep().getCepId())){

								/*
								 * Caso o cep atual seja inicial de município e
								 * o novo cep já tenho sido adicionado na
								 * coleção colecaoCepSelecionadosUsuario,
								 * removeremos apenas o cep inicial de município
								 * e todos os imóveis que estiverem associados
								 * ao mesmo, serão automaticamente associados ao
								 * cep de logradouro
								 * Colocado em 22/02/2007 por Raphael Rossiter
								 */
								// if
								// (this.verificarCepInicialMunicipio(logradouroCepNaBase.getCep())){
								LogradouroCep logradouroCepNovo = this.pesquisarAssociacaoLogradouroCep(atualizarLogradouroCepHelper
												.getCep().getCepId(), logradouro.getId());

								if(logradouroCepNovo != null){

									// Atualizar dependências
									this.atualizarLogradouroCepDependencias(logradouroCepNaBase, logradouroCepNovo);
								}else{

									// Atualização no BD
									atualizarLogradouroCepHelper.getLogradouroCep().setCep(atualizarLogradouroCepHelper.getCep());

									this.atualizarObjetoLogradouroCep(atualizarLogradouroCepHelper.getLogradouroCep(), registradorOperacao);
								}

								/*
								 * } else{
								 * //Atualização no BD
								 * atualizarLogradouroCepHelper
								 * .getLogradouroCep().setCep(
								 * atualizarLogradouroCepHelper .getCep());
								 * this
								 * .atualizarObjetoLogradouroCep(atualizarLogradouroCepHelper
								 * .getLogradouroCep(), registradorOperacao); }
								 */

								atualizacaoRealizada = true;
								break;
							}
						}

						if(!atualizacaoRealizada){
							getControladorUtil().remover(logradouroCepNaBase);
						}
					}else{
						getControladorUtil().atualizar(logradouroCepNaBase);
					}
					// =============================================================================================
				}
			}
		}

		while(iteratorColecaoCeps.hasNext()){
			cep = (Cep) iteratorColecaoCeps.next();

			if(!cep.getMunicipio().equalsIgnoreCase(logradouro.getMunicipio().getNome())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.logradouro_municipio_cep_invalido", null, cep.getCepFormatado(), logradouro
								.getMunicipio().getNome());
			}

			/*
			 * Quando o CEP for único de município ou inicial de município não
			 * deve haver a crítica CEP já associado a outro logradouro
			 */

			if(cepUnico != null || cepInicial != null){

				if(cepUnico != null && !cepUnico.getCepId().equals(cep.getCepId())){

					logradouroAssociado = this.verificarCepAssociadoOutroLogradouro(cep);

					if(logradouroAssociado != null && !logradouroAssociado.getId().equals(logradouro.getId())){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.logradouro_cep_ja_associado", null, cep.getCepFormatado(),
										logradouroAssociado.getId().toString() + " - " + logradouroAssociado.getDescricaoFormatada());
					}
				}

				if(cepInicial != null && !cepInicial.getCepId().equals(cep.getCepId())){

					logradouroAssociado = this.verificarCepAssociadoOutroLogradouro(cep);

					FiltroLogradouroCep filtroLogradouroCep = new FiltroLogradouroCep();

					filtroLogradouroCep.adicionarCaminhoParaCarregamentoEntidade("logradouro");
					filtroLogradouroCep.adicionarParametro(new ParametroSimples(FiltroLogradouroCep.ID_CEP, cep.getCepId()));
					filtroLogradouroCep.adicionarParametro(new ParametroSimples(FiltroLogradouroCep.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					Collection colecaoLogradouroCep = this.getControladorUtil().pesquisar(filtroLogradouroCep,
									LogradouroCep.class.getName());

					// verifica se o CEP já existe para outro logradouro, porém
					// se já existir e for devido a migração
					// não critica
					boolean logradouroMigracao = false;
					if(colecaoLogradouroCep != null && !colecaoLogradouroCep.isEmpty()){
						Iterator iteratorColecaoLogradouroCep = colecaoLogradouroCep.iterator();
						while(iteratorColecaoLogradouroCep.hasNext()){
							LogradouroCep logradouroCepPesquisado = (LogradouroCep) iteratorColecaoLogradouroCep.next();
							// se existir(na base) o cep para o o logradouro
							// corrente, mantem o cep
							if(logradouroCepPesquisado.getLogradouro().getId().toString().equals(logradouro.getId().toString())){
								logradouroMigracao = true;
							}
						}
					}
					if(!logradouroMigracao && logradouroAssociado != null && !logradouroAssociado.getId().equals(logradouro.getId())){

						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.logradouro_cep_ja_associado", null, cep.getCepFormatado(),
										logradouroAssociado.getId().toString() + " - " + logradouroAssociado.getDescricaoFormatada());
					}

				}else if(cepInicial != null && cepInicial.getCepId().equals(cep.getCepId()) && colecaoCeps.size() > 1){

					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.logradouro_cep_inicial_municipio_unico");
				}else if(cepInicial != null && cepInicial.getCepId().equals(cep.getCepId())){
					throw new ControladorException("atencao.endereco_cep_logradouro");
				}

			}else{

				logradouroAssociado = this.verificarCepAssociadoOutroLogradouro(cep);

				FiltroLogradouroCep filtroLogradouroCep = new FiltroLogradouroCep();

				filtroLogradouroCep.adicionarCaminhoParaCarregamentoEntidade("logradouro");
				filtroLogradouroCep.adicionarParametro(new ParametroSimples(FiltroLogradouroCep.ID_CEP, cep.getCepId()));
				filtroLogradouroCep.adicionarParametro(new ParametroSimples(FiltroLogradouroCep.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				Collection colecaoLogradouroCep = this.getControladorUtil().pesquisar(filtroLogradouroCep, LogradouroCep.class.getName());

				// verifica se o CEP já existe para outro logradouro, porém
				// se já existir e for devido a migração
				// não critica
				boolean logradouroMigracao = false;

				if(colecaoLogradouroCep != null && !colecaoLogradouroCep.isEmpty()){
					Iterator iteratorColecaoLogradouroCep = colecaoLogradouroCep.iterator();
					while(iteratorColecaoLogradouroCep.hasNext()){
						LogradouroCep logradouroCepPesquisado = (LogradouroCep) iteratorColecaoLogradouroCep.next();
						// se existir(na base) o cep para o o logradouro
						// corrente, mantem o cep
						if(logradouroCepPesquisado.getLogradouro().getId().toString().equals(logradouro.getId().toString())){
							logradouroMigracao = true;
						}
					}
				}

				if(!logradouroMigracao && logradouroAssociado != null && !logradouroAssociado.getId().equals(logradouro.getId())){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.logradouro_cep_ja_associado", null, cep.getCepFormatado(), logradouroAssociado
									.getId().toString() + " - " + logradouroAssociado.getDescricaoFormatada());
				}

			}

			logradouroCepNaBase = this.pesquisarAssociacaoLogradouroCep(cep.getCepId(), logradouro.getId());

			// Verificando se o cep em questão já foi atualizado
			// =========================================================================================================
			atualizacaoRealizada = false;
			if(logradouroCepNaBase == null && colecaoCepsAtualizacao != null && !colecaoCepsAtualizacao.isEmpty()){

				iterator = colecaoCepsAtualizacao.iterator();
				atualizarLogradouroCepHelper = null;

				while(iterator.hasNext()){

					atualizarLogradouroCepHelper = (AtualizarLogradouroCepHelper) iterator.next();

					if(cep.getCepId().equals(atualizarLogradouroCepHelper.getCep().getCepId())){

						atualizacaoRealizada = true;
						break;
					}
				}

			}
			// =========================================================================================================

			if(logradouroCepNaBase == null && !atualizacaoRealizada){

				logradouroCep = new LogradouroCep();
				logradouroCep.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
				logradouroCep.setLogradouro(logradouro);
				logradouroCep.setCep(cep);
				logradouroCep.setUltimaAlteracao(new Date());

				// ------------ REGISTRAR TRANSAÇÃO LOGRADOURO
				// BAIRRO--------------
				Operacao operacao = new Operacao();
				operacao.setId(Operacao.OPERACAO_LOGRADOURO_ATUALIZAR);
				OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
				operacaoEfetuada.setOperacao(operacao);
				logradouroCep.setOperacaoEfetuada(operacaoEfetuada);
				logradouroCep.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(logradouroCep);
				// ------------ REGISTRAR TRANSAÇÃO LOGRADOURO
				// BAIRRO---------------

				this.getControladorUtil().inserir(logradouroCep);

			}else if(logradouroCepNaBase != null && cepInicial != null
							&& cepInicial.getCepId().equals(logradouroCepNaBase.getCep().getCepId()) && colecaoCeps.size() == 1){

				try{

					// ------------ REGISTRAR TRANSAÇÃO LOGRADOURO
					// BAIRRO--------------
					Operacao operacao = new Operacao();
					operacao.setId(Operacao.OPERACAO_LOGRADOURO_ATUALIZAR);
					OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
					operacaoEfetuada.setOperacao(operacao);
					logradouroCepNaBase.setOperacaoEfetuada(operacaoEfetuada);
					logradouroCepNaBase.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					registradorOperacao.registrarOperacao(logradouroCepNaBase);
					// ------------ REGISTRAR TRANSAÇÃO LOGRADOURO
					// BAIRRO---------------

					repositorioEndereco.atualizarIndicadorUsoLogradouroCep(logradouroCepNaBase.getCep().getCepId(), logradouroCepNaBase
									.getLogradouro().getId(), ConstantesSistema.INDICADOR_USO_ATIVO);

					LogradouroCep logradouroCepNovo = this.pesquisarAssociacaoLogradouroCep((colecaoCeps.iterator().next()).getCepId(),
									logradouro.getId());

					if(logradouroCepNovo != null && !logradouroCepNovo.equals(logradouroCepNaBase)){

						// Atualizar dependências
						this.atualizarLogradouroCepDependencias(logradouroCepNaBase, logradouroCepNovo);

					}
					// else{
					//
					// //Atualização no BD
					// logradouroCepNaBase.setCep(colecaoCeps.iterator().next());
					//
					// this.atualizarObjetoLogradouroCep(logradouroCepNaBase,
					// registradorOperacao);
					//
					// }

				}catch(ErroRepositorioException ex){
					ex.printStackTrace();
					throw new ControladorException("erro.sistema", ex);
				}
			}
		}

		colecaoLogradouroCepNaBase = this.pesquisarAssociacaoLogradouroCepPorLogradouro(logradouro);

		if(cepInicial != null && colecaoLogradouroCepNaBase.size() > 1){

			logradouroCep = this.pesquisarAssociacaoLogradouroCep(cepInicial.getCepId(), logradouro.getId());

			if(logradouroCep != null){
				// ------------ REGISTRAR TRANSAÇÃO LOGRADOURO
				// BAIRRO--------------
				Operacao operacao = new Operacao();
				operacao.setId(Operacao.OPERACAO_LOGRADOURO_ATUALIZAR);
				OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
				operacaoEfetuada.setOperacao(operacao);
				logradouroCep.setOperacaoEfetuada(operacaoEfetuada);
				logradouroCep.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(logradouroCep);
				// ------------ REGISTRAR TRANSAÇÃO LOGRADOURO
				// BAIRRO---------------

				this.inserirAssociacaoLogradouroCep(logradouroCep);

				if(colecaoCepsAtualizacao != null && !colecaoCepsAtualizacao.isEmpty()){

					iterator = colecaoCepsAtualizacao.iterator();
					atualizarLogradouroCepHelper = null;

					while(iterator.hasNext()){

						atualizarLogradouroCepHelper = (AtualizarLogradouroCepHelper) iterator.next();

						if(logradouroCep.getCep().getCepId().equals(atualizarLogradouroCepHelper.getLogradouroCep().getCep().getCepId())){

							/*
							 * Caso o cep atual seja inicial de município e o
							 * novo cep já tenho sido adicionado na coleção
							 * colecaoCepSelecionadosUsuario, removeremos apenas
							 * o cep inicial de município e todos os imóveis que
							 * estiverem associados ao mesmo, serão
							 * automaticamente associados ao cep de logradouro
							 * Colocado em 22/02/2007 por Raphael Rossiter
							 */

							LogradouroCep logradouroCepNovo = this.pesquisarAssociacaoLogradouroCep(atualizarLogradouroCepHelper.getCep()
											.getCepId(), logradouro.getId());

							if(logradouroCepNovo != null){

								// Atualizar dependências
								this.atualizarLogradouroCepDependencias(logradouroCep, logradouroCepNovo);

							}else{

								// Atualização no BD
								logradouroCep.setCep(colecaoCeps.iterator().next());

								this.atualizarObjetoLogradouroCep(logradouroCepNaBase, registradorOperacao);
							}

							break;
						}
					}

				}
			}
		}
	}

	/**
	 * Altera na tabela LOGRADOURO_CEP
	 * 
	 * @author Raphael Rossiter
	 * @date 14/11/2006
	 * @param logradouroCep
	 *            ,
	 *            RegistradorOperacao
	 * @return void
	 */
	public void atualizarObjetoLogradouroCep(LogradouroCep logradouroCep, RegistradorOperacao registradorOperacao)
					throws ControladorException{

		FiltroLogradouroCep filtroLogradouroCep = new FiltroLogradouroCep();

		// Seta o filtro para buscar o logradouroCep na base
		filtroLogradouroCep.adicionarParametro(new ParametroSimples(FiltroLogradouroCep.ID, logradouroCep.getId()));

		// Procura o logradouroCep na base
		LogradouroCep logradouroCepNaBase = (LogradouroCep) ((List) (getControladorUtil().pesquisar(filtroLogradouroCep,
						LogradouroCep.class.getName()))).get(0);

		// Verificar se o logradouroCep já foi atualizado por outro usuário
		// durante
		// esta atualização
		if(logradouroCepNaBase.getUltimaAlteracao().after(logradouroCep.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		// Atualiza a data de última alteração
		logradouroCep.setUltimaAlteracao(new Date());

		// ------------ REGISTRAR TRANSAÇÃO LOGRADOURO
		// BAIRRO--------------
		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_LOGRADOURO_ATUALIZAR);
		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		logradouroCep.setOperacaoEfetuada(operacaoEfetuada);
		logradouroCep.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(logradouroCep);
		// ------------ REGISTRAR TRANSAÇÃO LOGRADOURO
		// BAIRRO---------------

		// Atualiza o logradouro
		getControladorUtil().atualizar(logradouroCep);

	}

	/**
	 * Obtém o CEP Inicial para um determinado município
	 * 
	 * @author Raphael Rossiter
	 * @date 04/05/2006
	 * @param municipio
	 * @return Cep
	 */
	public Cep obterCepInicialMunicipio(Municipio municipio) throws ControladorException{

		Cep cep = null;

		if(municipio.getCepInicio() != null){

			FiltroCep filtroCep = new FiltroCep();

			filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.CODIGO, municipio.getCepInicio()));
			filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.CEP_TIPO_ID, CepTipo.INICIAL));
			filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoCepPadrao = this.getControladorUtil().pesquisar(filtroCep, Cep.class.getName());

			if(colecaoCepPadrao != null && !colecaoCepPadrao.isEmpty()){
				cep = (Cep) Util.retonarObjetoDeColecao(colecaoCepPadrao);
			}
		}

		return cep;
	}

	/**
	 * Obtém o CEP Único para um determinado município
	 * 
	 * @author Raphael Rossiter
	 * @date 23/05/2006
	 * @param municipio
	 * @return Cep
	 */
	public Cep obterCepUnicoMunicipio(Municipio municipio) throws ControladorException{

		Cep cep = null;

		if(municipio.getCepInicio() != null){

			FiltroCep filtroCep = new FiltroCep();

			filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.CODIGO, municipio.getCepInicio()));
			filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.CEP_TIPO_ID, CepTipo.UNICO));
			filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoCepPadrao = this.getControladorUtil().pesquisar(filtroCep, Cep.class.getName());

			if(colecaoCepPadrao != null && !colecaoCepPadrao.isEmpty()){
				cep = (Cep) Util.retonarObjetoDeColecao(colecaoCepPadrao);
			}
		}

		return cep;
	}

	/**
	 * Verificar se o CEP está associado a outro logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 04/05/2006
	 * @param cep
	 * @return Logradouro
	 */
	public Logradouro verificarCepAssociadoOutroLogradouro(Cep cep) throws ControladorException{

		Logradouro retorno = null;

		FiltroLogradouroCep filtroLogradouroCep = new FiltroLogradouroCep();

		filtroLogradouroCep.adicionarCaminhoParaCarregamentoEntidade("logradouro");
		filtroLogradouroCep.adicionarCaminhoParaCarregamentoEntidade("logradouro.logradouroTipo");
		filtroLogradouroCep.adicionarCaminhoParaCarregamentoEntidade("logradouro.logradouroTitulo");

		filtroLogradouroCep.adicionarParametro(new ParametroSimples(FiltroLogradouroCep.ID_CEP, cep.getCepId()));

		filtroLogradouroCep.adicionarParametro(new ParametroSimples(FiltroLogradouroCep.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoLogradouroCep = this.getControladorUtil().pesquisar(filtroLogradouroCep, LogradouroCep.class.getName());

		if(colecaoLogradouroCep != null && !colecaoLogradouroCep.isEmpty()){

			LogradouroCep logradouroCep = (LogradouroCep) Util.retonarObjetoDeColecao(colecaoLogradouroCep);
			retorno = logradouroCep.getLogradouro();
		}

		return retorno;
	}

	/**
	 * Verifica se o logradouro já está associado a CEPs do tipo logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 17/05/2006
	 * @param logradouro
	 * @return boolean
	 */
	public boolean verificarLogradouroAssociadoCepTipoLogradouro(Logradouro logradouro) throws ControladorException{

		boolean retorno = false;

		FiltroLogradouroCep filtroLogradouroCep = new FiltroLogradouroCep();

		filtroLogradouroCep.adicionarParametro(new ParametroSimples(FiltroLogradouroCep.ID_LOGRADOURO, logradouro.getId()));

		filtroLogradouroCep.adicionarParametro(new ParametroSimples(FiltroLogradouroCep.ID_CEP_TIPO_CEP, CepTipo.LOGRADOURO));

		Collection colecaoLogradouroCep = this.getControladorUtil().pesquisar(filtroLogradouroCep, LogradouroCep.class.getName());

		if(colecaoLogradouroCep != null && !colecaoLogradouroCep.isEmpty()){
			retorno = true;
		}

		return retorno;
	}

	/**
	 * Verifica se o CEP é único de município
	 * 
	 * @author Raphael Rossiter
	 * @date 10/05/2006
	 * @param cep
	 * @return boolean
	 */
	public boolean verificarCepUnicoMunicipio(Cep cep) throws ControladorException{

		boolean retorno = false;

		FiltroCep filtroCep = new FiltroCep();

		filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.CODIGO, cep.getCodigo()));

		filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.CEP_TIPO_ID, CepTipo.UNICO));

		Collection colecaoCep = this.getControladorUtil().pesquisar(filtroCep, Cep.class.getName());

		if(colecaoCep != null && !colecaoCep.isEmpty()){

			retorno = true;
		}

		return retorno;
	}

	/**
	 * Verifica se o CEP é inicial de município
	 * 
	 * @author Raphael Rossiter
	 * @date 10/05/2006
	 * @param cep
	 * @return boolean
	 */
	public boolean verificarCepInicialMunicipio(Cep cep) throws ControladorException{

		boolean retorno = false;

		FiltroCep filtroCep = new FiltroCep();

		filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.CODIGO, cep.getCodigo()));

		filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.CEP_TIPO_ID, CepTipo.INICIAL));

		Collection colecaoCep = this.getControladorUtil().pesquisar(filtroCep, Cep.class.getName());

		if(colecaoCep != null && !colecaoCep.isEmpty()){

			retorno = true;
		}

		return retorno;
	}

	/**
	 * Verifica se o Bairro é do tipo "BAIRRO NAO INFORMADO"
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param bairro
	 * @return boolean
	 */
	public boolean verificarBairroTipoBairroNaoInformado(Bairro bairro) throws ControladorException{

		boolean retorno = false;

		FiltroBairro filtroBairro = new FiltroBairro();

		filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.ID, bairro.getId()));

		filtroBairro.adicionarParametro(new ComparacaoTextoCompleto(FiltroBairro.NOME, Bairro.BAIRRO_NAO_INFORMADO));

		Collection colecaoBairro = this.getControladorUtil().pesquisar(filtroBairro, Bairro.class.getName());

		if(colecaoBairro != null && !colecaoBairro.isEmpty()){

			retorno = true;
		}

		return retorno;
	}

	/**
	 * Seleciona os bairros em que o logradouro está contido
	 * 
	 * @author Raphael Rossiter
	 * @date 10/05/2006
	 * @param Logradouro
	 * @return Collection<Bairro>
	 */
	public Collection<Bairro> obterBairrosPorLogradouro(Logradouro logradouro) throws ControladorException{

		Collection retorno = null;

		FiltroLogradouroBairro filtroLogradouroBairro = new FiltroLogradouroBairro();

		filtroLogradouroBairro.adicionarCaminhoParaCarregamentoEntidade("bairro");

		filtroLogradouroBairro.adicionarParametro(new ParametroSimples(FiltroLogradouroBairro.ID_LOGRADOURO, logradouro.getId()));
		filtroLogradouroBairro.adicionarParametro(new ParametroSimples(FiltroLogradouroBairro.INDICADORUSO_BAIRRO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoLogradouroBairro = this.getControladorUtil().pesquisar(filtroLogradouroBairro, LogradouroBairro.class.getName());

		if(colecaoLogradouroBairro != null && !colecaoLogradouroBairro.isEmpty()){

			Iterator iteratorColecaoLogradouroBairro = colecaoLogradouroBairro.iterator();
			retorno = new ArrayList();
			LogradouroBairro logradouroBairro = null;

			while(iteratorColecaoLogradouroBairro.hasNext()){
				logradouroBairro = (LogradouroBairro) iteratorColecaoLogradouroBairro.next();
				retorno.add(logradouroBairro.getBairro());
			}
		}

		return retorno;
	}

	/**
	 * Seleciona os bairros em que o logradouro está contido (INCLUSIVE OS QUE
	 * ESTEJAM COM INDICADOR DESATIVADO)
	 * 
	 * @author Raphael Rossiter
	 * @date 10/05/2006
	 * @param Logradouro
	 * @return Collection<Bairro>
	 */
	public Collection<Bairro> obterTodosBairrosPorLogradouro(Logradouro logradouro) throws ControladorException{

		Collection retorno = null;

		FiltroLogradouroBairro filtroLogradouroBairro = new FiltroLogradouroBairro();

		filtroLogradouroBairro.adicionarCaminhoParaCarregamentoEntidade("bairro");

		filtroLogradouroBairro.adicionarParametro(new ParametroSimples(FiltroLogradouroBairro.ID_LOGRADOURO, logradouro.getId()));

		Collection colecaoLogradouroBairro = this.getControladorUtil().pesquisar(filtroLogradouroBairro, LogradouroBairro.class.getName());

		if(colecaoLogradouroBairro != null && !colecaoLogradouroBairro.isEmpty()){

			Iterator iteratorColecaoLogradouroBairro = colecaoLogradouroBairro.iterator();
			retorno = new ArrayList();
			LogradouroBairro logradouroBairro = null;

			while(iteratorColecaoLogradouroBairro.hasNext()){
				logradouroBairro = (LogradouroBairro) iteratorColecaoLogradouroBairro.next();
				retorno.add(logradouroBairro.getBairro());
			}
		}

		return retorno;
	}

	/**
	 * @author Raphael Rossiter
	 * @date 10/05/2006
	 * @param Logradouro
	 * @return Integer
	 */
	public Integer inserirAssociacaoLogradouroCep(LogradouroCep logradouroCep) throws ControladorException{

		Integer retorno = null;
		Collection<LogradouroCep> colecaoLogradouroCep = null;

		try{

			colecaoLogradouroCep = repositorioEndereco.pesquisarAssociacaoLogradouroCepPorLogradouro(logradouroCep.getLogradouro().getId());

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(colecaoLogradouroCep != null && !colecaoLogradouroCep.isEmpty()){

			if(colecaoLogradouroCep.size() > 1){

				Iterator iteratorColecaoLogradouroCep = colecaoLogradouroCep.iterator();
				LogradouroCep logradouroCepColecao = null;

				while(iteratorColecaoLogradouroCep.hasNext()){
					logradouroCepColecao = (LogradouroCep) iteratorColecaoLogradouroCep.next();

					if(logradouroCepColecao.getCep().getCepTipo() != null
									&& logradouroCepColecao.getCep().getCepTipo().getId().equals(CepTipo.INICIAL)){

						try{

							repositorioEndereco.atualizarIndicadorUsoLogradouroCep(logradouroCepColecao.getCep().getCepId(),
											logradouroCepColecao.getLogradouro().getId(), ConstantesSistema.INDICADOR_USO_DESATIVO);

						}catch(ErroRepositorioException ex){
							ex.printStackTrace();
							throw new ControladorException("erro.sistema", ex);
						}
					}
				}
			}
		}

		LogradouroCep logradouroCepExistente = null;

		logradouroCepExistente = this.pesquisarAssociacaoLogradouroCep(logradouroCep.getCep().getCepId(), logradouroCep.getLogradouro()
						.getId());

		if(logradouroCepExistente == null){
			logradouroCep.setUltimaAlteracao(new Date());
			retorno = (Integer) this.getControladorUtil().inserir(logradouroCep);
		}else{
			retorno = logradouroCepExistente.getId();
		}

		return retorno;
	}

	/**
	 * [UC0003] Informar Endereço
	 * Pesquisar associação de LogradouroCep apenas por logradouro
	 * 
	 * @author Raphael Rossiter
	 * @data 12/05/2006
	 * @param idLogradouro
	 * @return LogradouroCep
	 */
	public Collection<LogradouroCep> pesquisarAssociacaoLogradouroCepPorLogradouro(Logradouro logradouro) throws ControladorException{

		Collection<LogradouroCep> retorno = null;

		try{

			retorno = repositorioEndereco.pesquisarAssociacaoLogradouroCepPorLogradouro(logradouro.getId());

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * atualiza um logradouro na base e se tiver um bairro inseri na tabela de
	 * ligação logradouroBairro
	 * 
	 * @param logradouro
	 *            Description of the Parameter
	 * @param bairro
	 *            Description of the Parameter
	 * @throws ControladorException
	 */
	public void atualizarLogradouro(Logradouro logradouro, Collection colecaoBairros, Collection colecaoCeps,
					Collection<AtualizarLogradouroBairroHelper> colecaoBairrosAtualizacao,
					Collection<AtualizarLogradouroCepHelper> colecaoCepsAtualizacao) throws ControladorException{

		FiltroLogradouro filtroLogradouro = new FiltroLogradouro();

		// Seta o filtro para buscar o logradouro na base
		filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, logradouro.getId()));

		// Procura o logradouro na base
		Collection colecaoLogradouro = getControladorUtil().pesquisar(filtroLogradouro, Logradouro.class.getName());
		if(colecaoLogradouro == null || colecaoLogradouro.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		Logradouro logradouroNaBase = (Logradouro) Util.retonarObjetoDeColecao(colecaoLogradouro);

		// Verificar se o logradouro já foi atualizado por outro usuário
		// durante
		// esta atualização
		if(logradouroNaBase.getUltimaAlteracao().after(logradouro.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		// Atualiza a data de última alteração
		logradouro.setUltimaAlteracao(new Date());

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_LOGRADOURO_ATUALIZAR,
						new UsuarioAcaoUsuarioHelper(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_LOGRADOURO_ATUALIZAR);
		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		logradouro.setOperacaoEfetuada(operacaoEfetuada);
		logradouro.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(logradouro);
		// ------------ REGISTRAR TRANSAÇÃO ----------------

		// Atualiza o logradouro
		getControladorUtil().atualizar(logradouro);

		/*
		 * Atualiza todos os relacionamentos encontrados (LogradouroBairro)
		 * informados pelo usuário
		 */
		this.atualizarLogradouroBairro(logradouro, colecaoBairros, colecaoBairrosAtualizacao, registradorOperacao);

		/*
		 * Atualizando todos os relacionamentos encontrados (LogradouroCep)
		 * informados pelo usuário
		 */
		this.atualizarLogradouroCep(logradouro, colecaoCeps, colecaoCepsAtualizacao, registradorOperacao);

	}

	/**
	 * remove um logradouro e o bairro ta tabela ligação logradouroBairro do
	 * logradouro removido.
	 * 
	 * @param ids
	 *            Description of the Parameter
	 * @param pacoteLogradouro
	 *            Description of the Parameter
	 * @throws ControladorException
	 */
	public void removerLogradouro(String[] ids, String pacoteLogradouro, OperacaoEfetuada operacaoEfetuada,
					Collection<UsuarioAcaoUsuarioHelper> acaoUsuarioHelper) throws ControladorException{

		if(ids != null && ids.length != 0){

			Logradouro logradouro = new Logradouro();

			for(int i = 0; i < ids.length; i++){

				int idLogradouro = Integer.parseInt(ids[i]);

				this.getControladorUtil().remover(new String[] {"" + idLogradouro}, logradouro.getClass().getName(), operacaoEfetuada,
								acaoUsuarioHelper);

				// chama o metódo de remover unidade executora do
				// repositório
				/*
				 * getControladorUtil().removerUm(idLogradouro,
				 * pacoteLogradouro, operacaoEfetuada, acaoUsuarioHelper);
				 */
			}
		}
	}

	/**
	 * Inserir os ceps importados
	 * 
	 * @param cepsImportados
	 *            Coleção contendo todos os CEPs lidos no arquivo
	 * @return retorno
	 *         Array de String contendo nas posições:
	 *         0- Total de CEPs
	 *         1- CEPs Inseridos
	 *         2- CEPs Atualizados
	 *         3- CEPs Desprezados
	 * @throws ControladorException
	 */
	public String[] inserirCepImportados(Collection cepsImportados) throws ControladorException{

		Collection inserirCepImportados = new ArrayList();
		Collection atualizarCepImportados = new ArrayList();
		Iterator cepsImportadosIterator = cepsImportados.iterator();
		String[] retorno = {"0", "0", "0", "0"};

		try{
			// Pesquisa todas as Unidades da Federação
			FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao();
			filtroUnidadeFederacao.setCampoOrderBy(FiltroUnidadeFederacao.DESCRICAO);
			Collection<UnidadeFederacao> colecaoUnidadeFederacao = repositorioUtil.pesquisar(filtroUnidadeFederacao,
							UnidadeFederacao.class.getName());
			if(colecaoUnidadeFederacao == null || colecaoUnidadeFederacao.isEmpty()){
				throw new ControladorException("atencao.entidade_sem_dados_para_selecao", null, "Unidade Federação");
			}

			// Armazena na coleção apenas as siglas das Unidades da Federação.
			Collection<String> siglasUnidadesFederacao = new LinkedList();
			Iterator unidadeFederacaoIterator = colecaoUnidadeFederacao.iterator();
			while(unidadeFederacaoIterator.hasNext()){
				UnidadeFederacao unidade = (UnidadeFederacao) unidadeFederacaoIterator.next();
				siglasUnidadesFederacao.add(unidade.getSigla());
			}

			Collection cepsCadastrados = repositorioEndereco.pesquisarCep();

			Collections.sort((List) cepsCadastrados, new Comparator() {

				public int compare(Object a, Object b){

					Integer codigo1 = ((Cep) a).getCodigo();
					Integer codigo2 = ((Cep) b).getCodigo();

					return codigo1.compareTo(codigo2);
				}
			});

			while(cepsImportadosIterator.hasNext()){

				Cep cepImportado = (Cep) cepsImportadosIterator.next();
				if(siglasUnidadesFederacao.contains(cepImportado.getSigla())){
					if(cepsCadastrados.contains(cepImportado)){
						int posicaoRegistro = Collections.binarySearch((List) cepsCadastrados, cepImportado, new Comparator() {

							public int compare(Object a, Object b){

								Integer codigo1 = ((Cep) a).getCodigo();
								Integer codigo2 = ((Cep) b).getCodigo();

								return codigo1.compareTo(codigo2);
							}
						});
						Cep cepCadastrado = (Cep) ((List) cepsCadastrados).get(posicaoRegistro);

						if(atualizarCepImportados.contains(cepCadastrado)){

							Collections.sort((List) atualizarCepImportados, new Comparator() {

								public int compare(Object a, Object b){

									Integer codigo1 = ((Cep) a).getCodigo();
									Integer codigo2 = ((Cep) b).getCodigo();

									return codigo1.compareTo(codigo2);
								}
							});

							int posicaoRegistroAtual = Collections.binarySearch((List) atualizarCepImportados, cepCadastrado,
											new Comparator() {

												public int compare(Object a, Object b){

													Integer codigo1 = ((Cep) a).getCodigo();
													Integer codigo2 = ((Cep) b).getCodigo();

													return codigo1.compareTo(codigo2);
												}
											});
							Cep cepAtualizado = (Cep) ((List) atualizarCepImportados).get(posicaoRegistroAtual);

							atualizarCepImportados.remove(cepAtualizado);

							cepImportado.setCepId(cepCadastrado.getCepId());
							cepImportado.setCepTipo(cepCadastrado.getCepTipo());

							atualizarCepImportados.add(cepImportado);

						}else{
							cepImportado.setCepId(cepCadastrado.getCepId());
							cepImportado.setCepTipo(cepCadastrado.getCepTipo());
							atualizarCepImportados.add(cepImportado);
						}

					}else{
						if(!inserirCepImportados.contains(cepImportado)){
							inserirCepImportados.add(cepImportado);
						}
					}
				}
			}

			if(atualizarCepImportados != null && !atualizarCepImportados.isEmpty()){
				repositorioEndereco.atualizarImportacaoCep(atualizarCepImportados);
			}

			if(inserirCepImportados != null && !inserirCepImportados.isEmpty()){
				repositorioEndereco.inserirImportacaoCep(inserirCepImportados);
			}
		}catch(RemocaoInvalidaException ex){
			throw new ControladorException("atencao.dependencias.existentes", ex);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		retorno[0] = "" + cepsImportados.size();
		retorno[1] = "" + inserirCepImportados.size();
		retorno[2] = "" + atualizarCepImportados.size();
		retorno[3] = "" + (cepsImportados.size() - (inserirCepImportados.size() + atualizarCepImportados.size()));
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
	 * Retorna o valor de controladorImovel
	 * 
	 * @return O valor de controladorImovel
	 */
	private ControladorImovelLocal getControladorImovel(){

		ControladorImovelLocalHome localHome = null;
		ControladorImovelLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorImovelLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_IMOVEL_SEJB);
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
	 * Retorna o valor de controladorArrecadacao
	 * 
	 * @return O valor de controladorArrecadacao
	 */
	private ControladorArrecadacaoLocal getControladorArrecadacao(){

		ControladorArrecadacaoLocalHome localHome = null;
		ControladorArrecadacaoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorArrecadacaoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_ARRECADACAO_SEJB);
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
	 * Retorna o valor de controladorRegistroAtendimento
	 * 
	 * @return O valor de controladorRegistroAtendimento
	 */
	private ControladorRegistroAtendimentoLocal getControladorRegistroAtendimento(){

		ControladorRegistroAtendimentoLocalHome localHome = null;
		ControladorRegistroAtendimentoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorRegistroAtendimentoLocalHome) locator
							.getLocalHome(ConstantesJNDI.CONTROLADOR_REGISTRO_ATENDIMENTO_SEJB);
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
	 * Retorna o valor de controladorLocalidade
	 * 
	 * @return O valor de controladorLocalidade
	 */
	private ControladorLocalidadeLocal getControladorLocalidade(){

		ControladorLocalidadeLocalHome localHome = null;
		ControladorLocalidadeLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorLocalidadeLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_LOCALIDADE_SEJB);
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
	 * Retorna o valor de controladorCliente
	 * 
	 * @return O valor de controladorCliente
	 */
	private ControladorClienteLocal getControladorCliente(){

		ControladorClienteLocalHome localHome = null;
		ControladorClienteLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorClienteLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CLIENTE_SEJB);
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
	 * [UC0085] - Obter Endereço Autor: Sávio Luiz Data: 26/12/2005
	 */

	public String pesquisarEndereco(Integer idImovel) throws ControladorException{

		String endereco = "";
		String[] enderecoArray = pesquisarEndereco(idImovel, 0);

		if(!Util.isVazioOrNulo(enderecoArray)){
			endereco = enderecoArray[0];
		}

		return endereco;
	}

	/**
	 * [UC0085] - Obter Endereço Autor: Sávio Luiz Data: 26/12/2005
	 * Para atender o Caso de Uso [UC0349], foi criado este método que retorna o endereço quebrado
	 * em duas Strings, de acordo com o tamanho limite informado
	 * 
	 * @param idImovel
	 * @param limiteTamanhoEndereco
	 */

	public String[] pesquisarEndereco(Integer idImovel, int limiteTamanhoEndereco) throws ControladorException{

		String[] endereco = null;
		Collection colecaoEndereco = null;
		try{
			colecaoEndereco = repositorioEndereco.pesquisarEndereco(idImovel);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		Imovel imovel = new Imovel();

		Iterator enderecoIterator = colecaoEndereco.iterator();

		Object[] arrayEndereco = (Object[]) enderecoIterator.next();

		if(arrayEndereco[20] != null && arrayEndereco[21] != null){

			LogradouroCep logradouroCep = null;
			if(arrayEndereco[20] != null){

				logradouroCep = new LogradouroCep();
				logradouroCep.setId((Integer) arrayEndereco[20]);

				// id do Logradouro
				Logradouro logradouro = null;
				if(arrayEndereco[19] != null){
					logradouro = new Logradouro();
					logradouro.setId((Integer) arrayEndereco[19]);
					logradouro.setNome("" + arrayEndereco[0]);
				}
				LogradouroTipo logradouroTipo = null;
				// Descrição logradouro tipo
				if(arrayEndereco[22] != null){
					logradouroTipo = new LogradouroTipo();
					logradouroTipo.setDescricao("" + arrayEndereco[22]);
					logradouro.setLogradouroTipo(logradouroTipo);
				}
				LogradouroTitulo logradouroTitulo = null;
				// Descrição logradouro titulo
				if(arrayEndereco[23] != null){
					logradouroTitulo = new LogradouroTitulo();
					logradouroTitulo.setDescricao("" + arrayEndereco[23]);
					logradouro.setLogradouroTitulo(logradouroTitulo);
				}
				// id do CEP
				Cep cep = null;
				if(arrayEndereco[10] != null){
					cep = new Cep();
					cep.setCepId((Integer) arrayEndereco[10]);
					cep.setCodigo((Integer) arrayEndereco[16]);
				}

				logradouroCep.setLogradouro(logradouro);
				logradouroCep.setCep(cep);
			}

			imovel.setLogradouroCep(logradouroCep);

			LogradouroBairro logradouroBairro = null;
			if(arrayEndereco[21] != null){

				logradouroBairro = new LogradouroBairro();
				logradouroBairro.setId((Integer) arrayEndereco[21]);

				Bairro bairro = null;
				// nome bairro
				if(arrayEndereco[3] != null){
					bairro = new Bairro();
					bairro.setId((Integer) arrayEndereco[3]);
					bairro.setNome("" + arrayEndereco[4]);
				}

				Municipio municipio = null;
				// nome municipio
				if(arrayEndereco[5] != null){
					municipio = new Municipio();
					municipio.setId((Integer) arrayEndereco[5]);
					municipio.setNome("" + arrayEndereco[6]);

					// id da unidade federação
					if(arrayEndereco[7] != null){
						UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
						unidadeFederacao.setId((Integer) arrayEndereco[7]);
						unidadeFederacao.setSigla("" + arrayEndereco[8]);
						municipio.setUnidadeFederacao(unidadeFederacao);
					}

					bairro.setMunicipio(municipio);
				}

				logradouroBairro.setBairro(bairro);
			}

			imovel.setLogradouroBairro(logradouroBairro);

			// descricao de endereço referência
			if(arrayEndereco[24] != null){
				EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
				enderecoReferencia.setDescricao("" + arrayEndereco[24]);
				imovel.setEnderecoReferencia(enderecoReferencia);
			}

			// numero imovel
			if(arrayEndereco[17] != null){
				imovel.setNumeroImovel("" + arrayEndereco[17]);
			}

			// complemento endereço
			if(arrayEndereco[18] != null){
				imovel.setComplementoEndereco("" + arrayEndereco[18]);
			}

			endereco = imovel.obterEnderecoFormatadoSemBairroCEP(limiteTamanhoEndereco);
		}else{

			FiltroImovelEnderecoAnterior filtroImovelEnderecoAnterior = new FiltroImovelEnderecoAnterior();

			filtroImovelEnderecoAnterior.adicionarParametro(new ParametroSimples(FiltroImovelEnderecoAnterior.ID, idImovel));

			Collection colecaoImovelEnderecoAnterior = this.getControladorUtil().pesquisar(filtroImovelEnderecoAnterior,
							ImovelEnderecoAnterior.class.getName());

			if(colecaoImovelEnderecoAnterior != null && !colecaoImovelEnderecoAnterior.isEmpty()){

				ImovelEnderecoAnterior imovelEnderecoAnterior = ((ImovelEnderecoAnterior) colecaoImovelEnderecoAnterior.iterator().next());

				String enderecoAnterior = imovelEnderecoAnterior.getEnderecoAnterior();

				// Se deve ser particionado, o endereço deve ser limitado conforme parâmetro passado
				if(limiteTamanhoEndereco > 0){
					enderecoAnterior = enderecoAnterior.substring(0, limiteTamanhoEndereco);
				}

				endereco = new String[3];
				endereco[0] = enderecoAnterior;
				endereco[1] = "";
				endereco[2] = enderecoAnterior;
			}
		}

		return endereco;
	}

	/**
	 * [UC0210] - Obter Endereço
	 * 
	 * @author Fernanda Paiva
	 * @param idImovel
	 * @return String
	 * @author Saulo Lima
	 * @date 26/03/2010
	 *       Mudança na forma de pesquisar
	 */
	public String pesquisarEnderecoFormatado(Integer idImovel) throws ControladorException{

		Object[] arrayDadosEndereco = this.pesquisarEnderecoFormatadoLista(idImovel);
		return (String) arrayDadosEndereco[0];
	}

	/**
	 * [UC0210] - Obter Endereço
	 * 
	 * @author Saulo Lima
	 * @date 26/03/2010
	 * @param idImovel
	 * @return Object[]
	 *         0 - endereco (String)
	 *         1 - idLogradouro (Integer)
	 *         2 - numeroImovel (String)
	 *         3 - cepImovel (String)
	 *         4 - imovel (Imovel)
	 */
	public Object[] pesquisarEnderecoFormatadoLista(Integer idImovel) throws ControladorException{

		Object[] retorno = new Object[9];

		Object[] retornoCompleto = pesquisarEnderecoFormatadoLista(idImovel, 0);

		retorno[0] = retornoCompleto[0];
		retorno[1] = retornoCompleto[1];
		retorno[2] = retornoCompleto[2];
		retorno[3] = retornoCompleto[3];
		retorno[4] = retornoCompleto[4];
		retorno[5] = retornoCompleto[5];
		retorno[6] = retornoCompleto[8];
		retorno[7] = retornoCompleto[9];
		retorno[8] = retornoCompleto[10];

		return retorno;
	}

	/**
	 * [UC0210] - Obter Endereço
	 * 
	 * @author Saulo Lima
	 *         Alterado por Luciano Galvão em 22/06/2012
	 * @date 26/03/2010
	 * @param idImovel
	 * @return Object[]
	 *         0 - endereco (String)
	 *         1 - idLogradouro (Integer)
	 *         2 - numeroImovel (String)
	 *         3 - cepImovel (String)
	 *         4 - imovel (Imovel)
	 *         5 - municipio (String)
	 *         6 - enderecoParticionado1 (String)
	 *         7 - enderecoParticionado2 (String)
	 */
	public Object[] pesquisarEnderecoFormatadoLista(Integer idImovel, int limiteTamanhoEndereco) throws ControladorException{

		String enderecoAnterior = null;
		Integer idLogradouro = null;
		String numeroImovel = null;
		String cepImovel = "";
		String nomeMunicipio = "";
		String idMunicipio = "";
		String bairroImovel = null;
		Object[] retorno = new Object[11];
		String[] enderecoParticionado = new String[2];
		Collection colecaoEndereco = null;
		String siglaUnidadeFederacao = null;

		try{
			colecaoEndereco = repositorioEndereco.pesquisarEnderecoFormatado(idImovel);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		Imovel imovel = new Imovel();

		Iterator enderecoIterator = colecaoEndereco.iterator();
		if(colecaoEndereco != null && !colecaoEndereco.isEmpty()){
			Object[] arrayEndereco = (Object[]) enderecoIterator.next();

			if(arrayEndereco[20] != null && arrayEndereco[21] != null){

				LogradouroCep logradouroCep = null;
				if(arrayEndereco[20] != null){

					logradouroCep = new LogradouroCep();
					logradouroCep.setId((Integer) arrayEndereco[20]);

					// id do Logradouro
					Logradouro logradouro = null;
					if(arrayEndereco[19] != null){
						logradouro = new Logradouro();
						logradouro.setId((Integer) arrayEndereco[19]);
						idLogradouro = (Integer) arrayEndereco[19];
						logradouro.setNome("" + arrayEndereco[0]);
					}
					LogradouroTipo logradouroTipo = null;
					// Descrição abreviada logradouro tipo
					if(arrayEndereco[1] != null){
						logradouroTipo = new LogradouroTipo();
						logradouroTipo.setDescricao("" + arrayEndereco[1]);
						if(arrayEndereco[22] != null){
							logradouroTipo.setDescricaoAbreviada("" + arrayEndereco[22]);
						}
						logradouro.setLogradouroTipo(logradouroTipo);
					}
					LogradouroTitulo logradouroTitulo = null;
					// Descrição abreviada logradouro titulo
					if(arrayEndereco[2] != null){
						logradouroTitulo = new LogradouroTitulo();
						logradouroTitulo.setDescricao("" + arrayEndereco[2]);
						if(arrayEndereco[23] != null){
							logradouroTitulo.setDescricaoAbreviada("" + arrayEndereco[23]);
						}
						logradouro.setLogradouroTitulo(logradouroTitulo);
					}
					// id do CEP
					Cep cep = null;
					if(arrayEndereco[10] != null){
						cep = new Cep();
						cep.setCepId((Integer) arrayEndereco[10]);
						cep.setCodigo((Integer) arrayEndereco[16]);
						cepImovel = cep.getCepFormatado();
					}

					logradouroCep.setLogradouro(logradouro);
					logradouroCep.setCep(cep);
				}

				imovel.setLogradouroCep(logradouroCep);

				LogradouroBairro logradouroBairro = null;
				if(arrayEndereco[21] != null){

					logradouroBairro = new LogradouroBairro();
					logradouroBairro.setId((Integer) arrayEndereco[21]);

					Bairro bairro = null;
					// nome bairro
					if(arrayEndereco[3] != null){
						bairro = new Bairro();
						bairro.setId((Integer) arrayEndereco[3]);
						bairro.setNome("" + arrayEndereco[4]);
					}

					Municipio municipio = null;
					// nome municipio
					if(arrayEndereco[5] != null){
						municipio = new Municipio();
						municipio.setId((Integer) arrayEndereco[5]);
						nomeMunicipio = "" + arrayEndereco[6];
						municipio.setNome(nomeMunicipio);
						idMunicipio = arrayEndereco[5].toString();

						// id da unidade federação
						if(arrayEndereco[7] != null){
							UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
							unidadeFederacao.setId((Integer) arrayEndereco[7]);
							unidadeFederacao.setSigla("" + arrayEndereco[8]);
							municipio.setUnidadeFederacao(unidadeFederacao);
							siglaUnidadeFederacao = unidadeFederacao.getSigla();
						}

						bairro.setMunicipio(municipio);
					}

					logradouroBairro.setBairro(bairro);

					bairroImovel = bairro.getNome();
				}

				imovel.setLogradouroBairro(logradouroBairro);

				// descricao de endereço referência
				if(arrayEndereco[9] != null){
					EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
					enderecoReferencia.setDescricao("" + arrayEndereco[9]);
					if(arrayEndereco[24] != null){
						enderecoReferencia.setDescricaoAbreviada("" + arrayEndereco[24]);
					}
					imovel.setEnderecoReferencia(enderecoReferencia);
				}

				// numero imovel
				if(arrayEndereco[17] != null){
					imovel.setNumeroImovel("" + arrayEndereco[17]);
					numeroImovel = "" + arrayEndereco[17];
				}

				// complemento endereço
				if(arrayEndereco[18] != null){
					imovel.setComplementoEndereco("" + arrayEndereco[18]);
				}

				enderecoParticionado = imovel.obterEnderecoFormatadoAbreviado(limiteTamanhoEndereco);

			}else{

				FiltroImovelEnderecoAnterior filtroImovelEnderecoAnterior = new FiltroImovelEnderecoAnterior();

				filtroImovelEnderecoAnterior.adicionarParametro(new ParametroSimples(FiltroImovelEnderecoAnterior.ID, idImovel));

				Collection colecaoImovelEnderecoAnterior = this.getControladorUtil().pesquisar(filtroImovelEnderecoAnterior,
								ImovelEnderecoAnterior.class.getName());

				if(colecaoImovelEnderecoAnterior != null && !colecaoImovelEnderecoAnterior.isEmpty()){

					ImovelEnderecoAnterior imovelEnderecoAnterior = ((ImovelEnderecoAnterior) colecaoImovelEnderecoAnterior.iterator()
									.next());

					enderecoAnterior = imovelEnderecoAnterior.getEnderecoAnterior();

					// Se deve ser particionado, o endereço deve ser limitado conforme parâmetro
					// passado
					if(limiteTamanhoEndereco > 0){
						enderecoAnterior = enderecoAnterior.substring(0, limiteTamanhoEndereco);
					}

					enderecoParticionado[0] = enderecoAnterior;
					enderecoParticionado[1] = "";
				}
			}
		}

		if(enderecoParticionado[0] != null && enderecoParticionado[1] != null){
			retorno[0] = enderecoParticionado[0] + enderecoParticionado[1];
		}else if(enderecoParticionado[0] != null && enderecoParticionado[1] == null){
			retorno[0] = enderecoParticionado[0];
		}else if(enderecoParticionado[0] == null && enderecoParticionado[1] != null){
			retorno[0] = enderecoParticionado[1];
		}else{
			retorno[0] = "";
		}

		retorno[1] = idLogradouro;
		retorno[2] = numeroImovel;
		retorno[3] = cepImovel;
		retorno[4] = imovel;
		retorno[5] = nomeMunicipio;
		retorno[6] = enderecoParticionado[0];
		retorno[7] = enderecoParticionado[1];
		retorno[8] = bairroImovel;
		retorno[9] = siglaUnidadeFederacao;
		retorno[10] = idMunicipio;
		return retorno;
	}

	/**
	 * Obter os campos necessário para o endereço do imóvel Autor:Sávio Luiz
	 */

	public Imovel pesquisarImovelParaEndereco(Integer idImovel) throws ControladorException{

		Imovel imovel = null;
		Collection colecaoEndereco = null;
		try{
			colecaoEndereco = repositorioEndereco.pesquisarEnderecoFormatado(idImovel);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		imovel = new Imovel();

		Iterator enderecoIterator = colecaoEndereco.iterator();
		if(colecaoEndereco != null && !colecaoEndereco.isEmpty()){
			Object[] arrayEndereco = (Object[]) enderecoIterator.next();

			if(arrayEndereco[20] != null && arrayEndereco[21] != null){

				LogradouroCep logradouroCep = null;
				if(arrayEndereco[20] != null){

					logradouroCep = new LogradouroCep();
					logradouroCep.setId((Integer) arrayEndereco[20]);

					// id do Logradouro
					Logradouro logradouro = null;
					if(arrayEndereco[19] != null){
						logradouro = new Logradouro();
						logradouro.setId((Integer) arrayEndereco[19]);
						logradouro.setNome("" + arrayEndereco[0]);
					}
					LogradouroTipo logradouroTipo = null;
					// Descrição abreviada logradouro tipo
					if(arrayEndereco[1] != null){
						logradouroTipo = new LogradouroTipo();
						logradouroTipo.setDescricao("" + arrayEndereco[1]);
						logradouro.setLogradouroTipo(logradouroTipo);
					}
					LogradouroTitulo logradouroTitulo = null;
					// Descrição abreviada logradouro titulo
					if(arrayEndereco[2] != null){
						logradouroTitulo = new LogradouroTitulo();
						logradouroTitulo.setDescricao("" + arrayEndereco[2]);
						logradouro.setLogradouroTitulo(logradouroTitulo);
					}
					// id do CEP
					Cep cep = null;
					if(arrayEndereco[10] != null){
						cep = new Cep();
						cep.setCepId((Integer) arrayEndereco[10]);
						cep.setCodigo((Integer) arrayEndereco[16]);
					}

					logradouroCep.setLogradouro(logradouro);
					logradouroCep.setCep(cep);
				}

				imovel.setLogradouroCep(logradouroCep);

				LogradouroBairro logradouroBairro = null;
				if(arrayEndereco[21] != null){

					logradouroBairro = new LogradouroBairro();
					logradouroBairro.setId((Integer) arrayEndereco[21]);

					Bairro bairro = null;
					// nome bairro
					if(arrayEndereco[3] != null){
						bairro = new Bairro();
						bairro.setId((Integer) arrayEndereco[3]);
						bairro.setNome("" + arrayEndereco[4]);
					}

					Municipio municipio = null;
					// nome municipio
					if(arrayEndereco[5] != null){
						municipio = new Municipio();
						municipio.setId((Integer) arrayEndereco[5]);
						municipio.setNome("" + arrayEndereco[6]);

						// id da unidade federação
						if(arrayEndereco[7] != null){
							UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
							unidadeFederacao.setId((Integer) arrayEndereco[7]);
							unidadeFederacao.setSigla("" + arrayEndereco[8]);
							municipio.setUnidadeFederacao(unidadeFederacao);
						}

						bairro.setMunicipio(municipio);
					}

					logradouroBairro.setBairro(bairro);
				}

				imovel.setLogradouroBairro(logradouroBairro);

				// descricao de endereço referência
				if(arrayEndereco[9] != null){
					EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
					enderecoReferencia.setDescricaoAbreviada("" + arrayEndereco[9]);
					enderecoReferencia.setId((Integer) arrayEndereco[25]);
					imovel.setEnderecoReferencia(enderecoReferencia);
				}

				// numero imovel
				if(arrayEndereco[17] != null){
					imovel.setNumeroImovel("" + arrayEndereco[17]);
				}

				// complemento endereço
				if(arrayEndereco[18] != null){
					imovel.setComplementoEndereco("" + arrayEndereco[18]);
				}

			}
		}

		return imovel;
	}

	/**
	 * [UC0085] - Obter Endereço
	 * 
	 * @author Sávio Luiz
	 * @date 14/06/2006
	 * @param idCliente
	 * @return String
	 */
	public String pesquisarEnderecoClienteAbreviado(Integer idCliente) throws ControladorException{

		Object[] arrayDadosEndereco = this.pesquisarEnderecoClienteAbreviadoLista(idCliente, false);
		return (String) arrayDadosEndereco[0];
	}

	/**
	 * @author Saulo Lima
	 * @date 24/03/2010
	 * @param idCliente
	 * @return Object[]:
	 *         0 - endereco (String)
	 *         1 - idLogradouro (Integer)
	 *         2 - numeroImovel (String)
	 */
	public Object[] pesquisarEnderecoClienteAbreviadoLista(Integer idCliente, boolean indicadorAbreviado) throws ControladorException{

		String endereco = null;
		Integer idLogradouro = null;
		String numeroImovel = null;
		String bairroEntrega = "";
		String cepEntrega = "";
		String municipioEntrega = "";
		String idMunicipioEntrega = "";
		String siglaUnidadeFederacaoEntrega = "";

		Object[] retorno = new Object[8];

		Collection colecaoEndereco = null;
		try{
			colecaoEndereco = repositorioEndereco.pesquisarEnderecoClienteAbreviado(idCliente);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		ClienteEndereco clienteEndereco = new ClienteEndereco();

		Iterator enderecoIterator = colecaoEndereco.iterator();
		while(enderecoIterator.hasNext()){
			// cria um array de objetos para pegar os parametros de
			// retorno da pesquisa
			Object[] arrayEndereco = (Object[]) enderecoIterator.next();

			LogradouroCep logradouroCep = null;
			if(arrayEndereco[20] != null){

				logradouroCep = new LogradouroCep();
				logradouroCep.setId((Integer) arrayEndereco[20]);

				// id do Logradouro
				Logradouro logradouro = null;
				if(arrayEndereco[19] != null){
					logradouro = new Logradouro();
					logradouro.setId((Integer) arrayEndereco[19]);
					idLogradouro = (Integer) arrayEndereco[19];
					logradouro.setNome("" + arrayEndereco[0]);

					Municipio municipio = null;
					// nome municipio
					if(arrayEndereco[5] != null){
						municipio = new Municipio();
						municipio.setId((Integer) arrayEndereco[5]);
						municipio.setNome("" + arrayEndereco[6]);
						municipioEntrega = municipio.getNome();
						idMunicipioEntrega = arrayEndereco[5].toString();

						// id da unidade federação
						if(arrayEndereco[7] != null){
							UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
							unidadeFederacao.setId((Integer) arrayEndereco[7]);
							unidadeFederacao.setSigla("" + arrayEndereco[8]);
							municipio.setUnidadeFederacao(unidadeFederacao);
							siglaUnidadeFederacaoEntrega = unidadeFederacao.getSigla();
						}

						logradouro.setMunicipio(municipio);
					}
				}
				LogradouroTipo logradouroTipo = null;
				// Descrição logradouro tipo
				if(arrayEndereco[22] != null){
					logradouroTipo = new LogradouroTipo();
					logradouroTipo.setDescricaoAbreviada("" + arrayEndereco[22]);
					logradouro.setLogradouroTipo(logradouroTipo);
				}
				LogradouroTitulo logradouroTitulo = null;
				// Descrição logradouro titulo
				if(arrayEndereco[23] != null){
					logradouroTitulo = new LogradouroTitulo();
					logradouroTitulo.setDescricaoAbreviada("" + arrayEndereco[23]);
					logradouro.setLogradouroTitulo(logradouroTitulo);
				}
				// id do CEP
				Cep cep = null;
				if(arrayEndereco[10] != null){
					cep = new Cep();
					cep.setCepId((Integer) arrayEndereco[10]);
					cep.setCodigo((Integer) arrayEndereco[16]);
					cepEntrega = cep.getCodigo().toString();
				}

				logradouroCep.setLogradouro(logradouro);
				logradouroCep.setCep(cep);
			}

			clienteEndereco.setLogradouroCep(logradouroCep);

			LogradouroBairro logradouroBairro = null;
			if(arrayEndereco[21] != null){

				logradouroBairro = new LogradouroBairro();
				logradouroBairro.setId((Integer) arrayEndereco[21]);

				Bairro bairro = null;
				// nome bairro
				if(arrayEndereco[3] != null){
					bairro = new Bairro();
					bairro.setId((Integer) arrayEndereco[3]);
					bairro.setNome("" + arrayEndereco[4]);
					bairroEntrega = bairro.getNome();
				}

				Municipio municipio = null;
				// nome municipio
				if(arrayEndereco[25] != null){
					municipio = new Municipio();
					municipio.setId((Integer) arrayEndereco[25]);
					municipio.setNome("" + arrayEndereco[26]);
					municipioEntrega = municipio.getNome();
					idMunicipioEntrega = arrayEndereco[25].toString();

					// id da unidade federação
					if(arrayEndereco[27] != null){
						UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
						unidadeFederacao.setId((Integer) arrayEndereco[27]);
						unidadeFederacao.setSigla("" + arrayEndereco[28]);
						municipio.setUnidadeFederacao(unidadeFederacao);
						siglaUnidadeFederacaoEntrega = unidadeFederacao.getSigla();
					}

					bairro.setMunicipio(municipio);
				}

				logradouroBairro.setBairro(bairro);
			}

			clienteEndereco.setLogradouroBairro(logradouroBairro);

			// descricao de endereço referência
			if(arrayEndereco[9] != null){
				EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
				enderecoReferencia.setDescricaoAbreviada("" + arrayEndereco[9]);
				clienteEndereco.setEnderecoReferencia(enderecoReferencia);
			}

			// numero imovel
			if(arrayEndereco[17] != null){
				clienteEndereco.setNumero("" + arrayEndereco[17]);
				numeroImovel = "" + arrayEndereco[17];
			}

			// complemento endereço
			if(arrayEndereco[18] != null){
				clienteEndereco.setComplemento("" + arrayEndereco[18]);
			}

			if(indicadorAbreviado){
				endereco = clienteEndereco.getEnderecoAbreviado();
			}else{
				endereco = clienteEndereco.getEnderecoFormatadoAbreviado();
			}

		}

		retorno[0] = endereco;
		retorno[1] = idLogradouro;
		retorno[2] = numeroImovel;
		retorno[3] = bairroEntrega;
		retorno[4] = cepEntrega;
		retorno[5] = municipioEntrega;
		retorno[6] = siglaUnidadeFederacaoEntrega;
		retorno[7] = idMunicipioEntrega;

		return retorno;
	}

	/**
	 * [UC0085] - Obter Endereço Autor: Sávio Luiz Data: 09/04/2007 Recupera o
	 * endereço em 5 partes:o endereço abreviado formatado sem o municipio e a
	 * unidade federação,a descrição do municipio, a terceira parte a sigla da
	 * unidade federação,quarta parte o nome do bairro e a quinta parte o cep
	 * formatado
	 */

	public String[] pesquisarEnderecoClienteAbreviadoDividido(Integer idCliente) throws ControladorException{

		// 0 - Endereço sem municipio e UF
		// 1 - municipio
		// 2 - unidade federeção
		// 3 - bairro
		// 4 - CEP
		String[] parmsEndereco = new String[5];
		Collection colecaoEndereco = null;
		String endereco = "";
		String nomeMunicipio = "";
		String siglaUnidadeFederacao = "";
		String nomeBairro = "";
		String cepFormatado = "";
		try{
			colecaoEndereco = repositorioEndereco.pesquisarEnderecoClienteAbreviado(idCliente);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		ClienteEndereco clienteEndereco = new ClienteEndereco();

		Iterator enderecoIterator = colecaoEndereco.iterator();
		while(enderecoIterator.hasNext()){
			// cria um array de objetos para pegar os parametros de
			// retorno da pesquisa
			Object[] arrayEndereco = (Object[]) enderecoIterator.next();

			LogradouroCep logradouroCep = null;
			if(arrayEndereco[20] != null){

				logradouroCep = new LogradouroCep();
				logradouroCep.setId((Integer) arrayEndereco[20]);

				// id do Logradouro
				Logradouro logradouro = null;
				if(arrayEndereco[19] != null){
					logradouro = new Logradouro();
					logradouro.setId((Integer) arrayEndereco[19]);
					logradouro.setNome("" + arrayEndereco[0]);

					// nome municipio
					if(arrayEndereco[5] != null){
						nomeMunicipio = "" + arrayEndereco[6];

						// id da unidade federação
						if(arrayEndereco[7] != null){
							siglaUnidadeFederacao = "" + arrayEndereco[8];

						}

					}
				}
				LogradouroTipo logradouroTipo = null;
				// Descrição logradouro tipo
				if(arrayEndereco[22] != null){
					logradouroTipo = new LogradouroTipo();
					logradouroTipo.setDescricaoAbreviada("" + arrayEndereco[22]);
					logradouro.setLogradouroTipo(logradouroTipo);
				}
				LogradouroTitulo logradouroTitulo = null;
				// Descrição logradouro titulo
				if(arrayEndereco[23] != null){
					logradouroTitulo = new LogradouroTitulo();
					logradouroTitulo.setDescricaoAbreviada("" + arrayEndereco[23]);
					logradouro.setLogradouroTitulo(logradouroTitulo);
				}
				if(arrayEndereco[10] != null){
					cepFormatado = "" + (Integer) arrayEndereco[16];
				}

				logradouroCep.setLogradouro(logradouro);
			}

			clienteEndereco.setLogradouroCep(logradouroCep);

			LogradouroBairro logradouroBairro = null;
			if(arrayEndereco[21] != null){

				logradouroBairro = new LogradouroBairro();
				logradouroBairro.setId((Integer) arrayEndereco[21]);

				// nome bairro
				if(arrayEndereco[3] != null){
					nomeBairro = "" + arrayEndereco[4];
				}

				// nome municipio
				if(arrayEndereco[25] != null){
					nomeMunicipio = "" + arrayEndereco[26];

					// id da unidade federação
					if(arrayEndereco[27] != null){
						siglaUnidadeFederacao = "" + arrayEndereco[28];
					}
				}

			}

			clienteEndereco.setLogradouroBairro(logradouroBairro);

			// descricao de endereço referência
			if(arrayEndereco[9] != null){
				EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
				enderecoReferencia.setDescricaoAbreviada("" + arrayEndereco[9]);
				clienteEndereco.setEnderecoReferencia(enderecoReferencia);
			}

			// numero imovel
			if(arrayEndereco[17] != null){
				clienteEndereco.setNumero("" + arrayEndereco[17]);
			}

			// complemento endereço
			if(arrayEndereco[18] != null){
				clienteEndereco.setComplemento("" + arrayEndereco[18]);
			}

			endereco = clienteEndereco.getEnderecoFormatadoAbreviado();
		}

		parmsEndereco[0] = endereco;
		parmsEndereco[1] = nomeMunicipio;
		parmsEndereco[2] = siglaUnidadeFederacao;
		parmsEndereco[3] = nomeBairro;
		parmsEndereco[4] = cepFormatado;

		return parmsEndereco;
	}

	/**
	 * [UC0210] - Obter Endereço
	 * 
	 * @autor: Sávio Luiz
	 * @author eduardo henrique
	 * @date 25/08/2008
	 *       Adição da Zona Territorial / Regiao no retorno.
	 */

	public String[] pesquisarEnderecoFormatadoDividido(Integer idImovel) throws ControladorException{

		return pesquisarEnderecoFormatadoDividido(idImovel, 0);
	}

	public String[] pesquisarEnderecoFormatadoDividido(Integer idImovel, int limiteEndereco) throws ControladorException{

		// 0 - Endereço sem municipio e UF
		// 1 - municipio
		// 2 - unidade federeção
		// 3 - bairro
		// 4 - CEP
		// 5 - Zona Territorial / Regiao
		// 6 - Complemento
		String[] parmsEndereco = new String[7];
		Collection colecaoEndereco = null;
		String endereco = "";
		String nomeMunicipio = "";
		String siglaUnidadeFederacao = "";
		String nomeBairro = "";
		String cepFormatado = "";
		String zonaTerritorial = "";
		try{
			colecaoEndereco = repositorioEndereco.pesquisarEnderecoFormatado(idImovel);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		Imovel imovel = new Imovel();

		if(colecaoEndereco != null && !colecaoEndereco.isEmpty()){
			Iterator enderecoIterator = colecaoEndereco.iterator();
			Object[] arrayEndereco = (Object[]) enderecoIterator.next();

			if(arrayEndereco[20] != null && arrayEndereco[21] != null){

				LogradouroCep logradouroCep = null;
				if(arrayEndereco[20] != null){

					logradouroCep = new LogradouroCep();
					logradouroCep.setId((Integer) arrayEndereco[20]);

					// id do Logradouro
					Logradouro logradouro = null;
					if(arrayEndereco[19] != null){
						logradouro = new Logradouro();
						logradouro.setId((Integer) arrayEndereco[19]);
						logradouro.setNome("" + arrayEndereco[0]);
					}
					LogradouroTipo logradouroTipo = null;
					// Descrição abreviada logradouro tipo
					if(arrayEndereco[1] != null){
						logradouroTipo = new LogradouroTipo();
						logradouroTipo.setDescricao("" + arrayEndereco[1]);
						if(arrayEndereco[22] != null){
							logradouroTipo.setDescricaoAbreviada("" + arrayEndereco[22]);
						}
						logradouro.setLogradouroTipo(logradouroTipo);
					}
					LogradouroTitulo logradouroTitulo = null;
					// Descrição abreviada logradouro titulo
					if(arrayEndereco[2] != null){
						logradouroTitulo = new LogradouroTitulo();
						logradouroTitulo.setDescricao("" + arrayEndereco[2]);
						if(arrayEndereco[23] != null){
							logradouroTitulo.setDescricaoAbreviada("" + arrayEndereco[23]);
						}
						logradouro.setLogradouroTitulo(logradouroTitulo);
					}
					if(arrayEndereco[10] != null){
						cepFormatado = "" + (Integer) arrayEndereco[16];
					}

					logradouroCep.setLogradouro(logradouro);
				}

				imovel.setLogradouroCep(logradouroCep);

				LogradouroBairro logradouroBairro = null;
				if(arrayEndereco[21] != null){

					logradouroBairro = new LogradouroBairro();
					logradouroBairro.setId((Integer) arrayEndereco[21]);

					// nome bairro
					if(arrayEndereco[3] != null){
						nomeBairro = "" + arrayEndereco[4];
					}

					// nome municipio
					if(arrayEndereco[5] != null){
						nomeMunicipio = "" + arrayEndereco[6];

						// id da unidade federação
						if(arrayEndereco[7] != null){
							siglaUnidadeFederacao = "" + arrayEndereco[8];
						}

						if(arrayEndereco[25] != null){
							zonaTerritorial = "" + arrayEndereco[25];
						}

					}
				}

				imovel.setLogradouroBairro(logradouroBairro);

				// descricao de endereço referência
				if(arrayEndereco[9] != null){
					EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
					enderecoReferencia.setDescricao("" + arrayEndereco[9]);
					if(arrayEndereco[24] != null){
						enderecoReferencia.setDescricaoAbreviada("" + arrayEndereco[24]);
					}
					imovel.setEnderecoReferencia(enderecoReferencia);
				}

				// numero imovel
				if(arrayEndereco[17] != null){
					imovel.setNumeroImovel("" + arrayEndereco[17]);
				}

				// complemento endereço
				if(arrayEndereco[18] != null){
					imovel.setComplementoEndereco("" + arrayEndereco[18]);
				}

				endereco = imovel.obterEnderecoFormatadoAbreviado(limiteEndereco)[0];

			}
		}
		parmsEndereco[0] = endereco;
		parmsEndereco[1] = nomeMunicipio;
		parmsEndereco[2] = siglaUnidadeFederacao;
		parmsEndereco[3] = nomeBairro;
		parmsEndereco[4] = cepFormatado;
		parmsEndereco[5] = zonaTerritorial;

		if(imovel.getComplementoEndereco() != null){

			parmsEndereco[6] = imovel.getComplementoEndereco();
		}

		return parmsEndereco;

	}

	/**
	 * [UC0003] Informar Endereço
	 * Pesquisar associação de LogradouroBairro já existente
	 * 
	 * @author Raphael Rossiter
	 * @data 24/05/2006
	 * @param idBairro
	 *            ,
	 *            idLogradouro
	 * @return LogradouroBairro
	 */
	public LogradouroBairro pesquisarAssociacaoLogradouroBairro(Integer idBairro, Integer idLogradouro) throws ControladorException{

		// LogradouroBairro logradouroBairro = null;

		try{

			return /* logradouroBairro = */repositorioEndereco.pesquisarAssociacaoLogradouroBairro(idBairro, idLogradouro);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0003] Informar Endereço
	 * Pesquisar associação de LogradouroCep já existente
	 * 
	 * @author Raphael Rossiter
	 * @data 24/05/2006
	 * @param idCep
	 *            ,
	 *            idLogradouro
	 * @return LogradouroBairro
	 */
	public LogradouroCep pesquisarAssociacaoLogradouroCep(Integer idCep, Integer idLogradouro) throws ControladorException{

		try{

			return repositorioEndereco.pesquisarAssociacaoLogradouroCep(idCep, idLogradouro);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	public Collection<Logradouro> pesquisarLogradouro(FiltroLogradouro filtroLogradouro, Integer numeroPaginas) throws ControladorException{

		try{
			return repositorioEndereco.pesquisarLogradouro(filtroLogradouro, numeroPaginas);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	public Integer pesquisarLogradouroCount(FiltroLogradouro filtroLogradouro) throws ControladorException{

		try{
			return repositorioEndereco.pesquisarLogradouroCount(filtroLogradouro);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	// metodo que serve para fazer a pesquisa do logradouro
	// apartir dos parametros informados
	public Collection pesquisarLogradouroCompleto(String codigoMunicipio, String codigoBairro, String nome, String nomePopular,
					String logradouroTipo, String logradouroTitulo, String cep, String codigoLogradouro, String indicadorUso,
					String tipoPesquisa, String tipoPesquisaPopular, Integer numeroPaginas) throws ControladorException{

		try{
			return repositorioEndereco.pesquisarLogradouroCompleto(codigoMunicipio, codigoBairro, nome, nomePopular, logradouroTipo,
							logradouroTitulo, cep, codigoLogradouro, indicadorUso, tipoPesquisa, tipoPesquisaPopular, numeroPaginas);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	public Collection pesquisarLogradouroCompletoRelatorio(String codigoMunicipio, String codigoBairro, String nome, String nomePopular,
					String logradouroTipo, String logradouroTitulo, String cep, String codigoLogradouro, String indicadorUso,
					String tipoPesquisa, String tipoPesquisaPopular) throws ControladorException{

		try{
			return repositorioEndereco.pesquisarLogradouroCompletoRelatorio(codigoMunicipio, codigoBairro, nome, nomePopular,
							logradouroTipo, logradouroTitulo, cep, codigoLogradouro, indicadorUso, tipoPesquisa, tipoPesquisaPopular);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	public Integer pesquisarLogradouroCompletoCount(String codigoMunicipio, String codigoBairro, String nome, String nomePopular,
					String logradouroTipo, String logradouroTitulo, String cep, String codigoLogradouro, String indicadorUso,
					String tipoPesquisa, String tipoPesquisaPopular) throws ControladorException{

		try{
			return repositorioEndereco.pesquisarLogradouroCompletoCount(codigoMunicipio, codigoBairro, nome, nomePopular, logradouroTipo,
							logradouroTitulo, cep, codigoLogradouro, indicadorUso, tipoPesquisa, tipoPesquisaPopular);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * [UC0085] - Obter Endereço Autor: Ana Maria
	 */

	public String pesquisarEnderecoRegistroAtendimentoFormatado(Integer idRegistroAtendimento) throws ControladorException{

		String endereco = null;
		RegistroAtendimento registroAtendimento = pesquisarRegistroAtendimentoDadosEnderecoFormatado(idRegistroAtendimento);

		endereco = registroAtendimento.getEnderecoFormatado();

		return endereco;
	}

	public RegistroAtendimento pesquisarRegistroAtendimentoDadosEnderecoFormatado(Integer idRegistroAtendimento)
					throws ControladorException{

		Collection colecaoEndereco = null;
		try{
			colecaoEndereco = repositorioEndereco.pesquisarEnderecoRegistroAtendimentoFormatado(idRegistroAtendimento);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		RegistroAtendimento registroAtendimento = new RegistroAtendimento();

		Iterator enderecoIterator = colecaoEndereco.iterator();

		Object[] arrayEndereco = (Object[]) enderecoIterator.next();

		// idlogradouroCep e idlogradouroBairro
		if(arrayEndereco[18] != null){

			LogradouroCep logradouroCep = null;
			if(arrayEndereco[18] != null){

				logradouroCep = new LogradouroCep();
				logradouroCep.setId((Integer) arrayEndereco[18]);

				// id do Logradouro
				Logradouro logradouro = null;
				if(arrayEndereco[17] != null){
					logradouro = new Logradouro();
					logradouro.setId((Integer) arrayEndereco[17]);
					logradouro.setNome("" + arrayEndereco[0]);
				}

				LogradouroTipo logradouroTipo = null;
				// Descrição logradouro tipo
				if(arrayEndereco[1] != null){
					logradouroTipo = new LogradouroTipo();
					logradouroTipo.setDescricao("" + arrayEndereco[1]);
				}

				// Descrição abreviada logradouro tipo
				if(arrayEndereco[20] != null){

					if(logradouroTipo == null){
						logradouroTipo = new LogradouroTipo();
					}

					logradouroTipo.setDescricaoAbreviada("" + arrayEndereco[20]);
				}

				logradouro.setLogradouroTipo(logradouroTipo);

				LogradouroTitulo logradouroTitulo = null;
				// Descrição logradouro titulo
				if(arrayEndereco[2] != null){
					logradouroTitulo = new LogradouroTitulo();
					logradouroTitulo.setDescricao("" + arrayEndereco[2]);
				}

				// Descrição Abreviada logradouro titulo
				if(arrayEndereco[21] != null){

					if(logradouroTitulo == null){
						logradouroTitulo = new LogradouroTitulo();
					}

					logradouroTitulo.setDescricaoAbreviada("" + arrayEndereco[21]);
				}

				logradouro.setLogradouroTitulo(logradouroTitulo);

				// id do CEP
				Cep cep = null;
				if(arrayEndereco[9] != null){
					cep = new Cep();
					cep.setCepId((Integer) arrayEndereco[9]);
					cep.setCodigo((Integer) arrayEndereco[15]);
				}

				logradouroCep.setLogradouro(logradouro);
				logradouroCep.setCep(cep);
			}

			registroAtendimento.setLogradouroCep(logradouroCep);

		}

		if(arrayEndereco[19] != null){
			LogradouroBairro logradouroBairro = null;
			if(arrayEndereco[19] != null){

				logradouroBairro = new LogradouroBairro();
				logradouroBairro.setId((Integer) arrayEndereco[19]);

				Bairro bairro = null;
				// nome bairro
				if(arrayEndereco[3] != null){
					bairro = new Bairro();
					bairro.setId((Integer) arrayEndereco[3]);
					bairro.setNome("" + arrayEndereco[4]);
				}

				Municipio municipio = null;
				// nome municipio
				if(arrayEndereco[5] != null){
					municipio = new Municipio();
					municipio.setId((Integer) arrayEndereco[5]);
					municipio.setNome("" + arrayEndereco[6]);

					// id da unidade federação
					if(arrayEndereco[7] != null){
						UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
						unidadeFederacao.setId((Integer) arrayEndereco[7]);
						unidadeFederacao.setSigla("" + arrayEndereco[8]);
						municipio.setUnidadeFederacao(unidadeFederacao);
					}

					bairro.setMunicipio(municipio);
				}

				logradouroBairro.setBairro(bairro);
			}

			registroAtendimento.setLogradouroBairro(logradouroBairro);

		}

		// complemento endereço
		if(arrayEndereco[16] != null){
			registroAtendimento.setComplementoEndereco("" + arrayEndereco[16]);
		}

		// Número do Imóvel
		if(arrayEndereco[22] != null){
			registroAtendimento.setNumeroImovel("" + arrayEndereco[22]);
		}
		return registroAtendimento;
	}

	/**
	 * [UC0085] - Obter Endereço Autor: Ana Maria
	 */

	public String pesquisarEnderecoRegistroAtendimentoSolicitanteFormatado(Integer idRegistroAtendimentoSolicitante)
					throws ControladorException{

		String endereco = null;
		Collection colecaoEndereco = null;
		try{
			colecaoEndereco = repositorioEndereco
							.pesquisarEnderecoRegistroAtendimentoSolicitanteFormatado(idRegistroAtendimentoSolicitante);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		RegistroAtendimentoSolicitante registroAtendimentoSolicitante = new RegistroAtendimentoSolicitante();

		Iterator enderecoIterator = colecaoEndereco.iterator();

		Object[] arrayEndereco = (Object[]) enderecoIterator.next();

		// idlogradouroCep e idlogradouroBairro
		if(arrayEndereco[18] != null){

			LogradouroCep logradouroCep = null;
			if(arrayEndereco[18] != null){

				logradouroCep = new LogradouroCep();
				logradouroCep.setId((Integer) arrayEndereco[18]);

				// id do Logradouro
				Logradouro logradouro = null;
				if(arrayEndereco[17] != null){
					logradouro = new Logradouro();
					logradouro.setId((Integer) arrayEndereco[17]);
					logradouro.setNome("" + arrayEndereco[0]);
				}
				LogradouroTipo logradouroTipo = null;
				// Descrição logradouro tipo
				if(arrayEndereco[1] != null){
					logradouroTipo = new LogradouroTipo();
					logradouroTipo.setDescricao("" + arrayEndereco[1]);
					logradouro.setLogradouroTipo(logradouroTipo);
				}
				LogradouroTitulo logradouroTitulo = null;
				// Descrição logradouro titulo
				if(arrayEndereco[2] != null){
					logradouroTitulo = new LogradouroTitulo();
					logradouroTitulo.setDescricao("" + arrayEndereco[2]);
					logradouro.setLogradouroTitulo(logradouroTitulo);
				}
				// id do CEP
				Cep cep = null;
				if(arrayEndereco[9] != null){
					cep = new Cep();
					cep.setCepId((Integer) arrayEndereco[9]);
					cep.setCodigo((Integer) arrayEndereco[15]);
				}

				logradouroCep.setLogradouro(logradouro);
				logradouroCep.setCep(cep);
			}

			registroAtendimentoSolicitante.setLogradouroCep(logradouroCep);
		}

		LogradouroBairro logradouroBairro = null;
		if(arrayEndereco[19] != null){

			logradouroBairro = new LogradouroBairro();
			logradouroBairro.setId((Integer) arrayEndereco[19]);

			Bairro bairro = null;
			// nome bairro
			if(arrayEndereco[3] != null){
				bairro = new Bairro();
				bairro.setId((Integer) arrayEndereco[3]);
				bairro.setNome("" + arrayEndereco[4]);
			}

			Municipio municipio = null;
			// nome municipio
			if(arrayEndereco[5] != null){
				municipio = new Municipio();
				municipio.setId((Integer) arrayEndereco[5]);
				municipio.setNome("" + arrayEndereco[6]);

				// id da unidade federação
				if(arrayEndereco[7] != null){
					UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
					unidadeFederacao.setId((Integer) arrayEndereco[7]);
					unidadeFederacao.setSigla("" + arrayEndereco[8]);
					municipio.setUnidadeFederacao(unidadeFederacao);
				}

				bairro.setMunicipio(municipio);
			}

			logradouroBairro.setBairro(bairro);
			registroAtendimentoSolicitante.setLogradouroBairro(logradouroBairro);

		}

		// complemento endereço
		if(arrayEndereco[16] != null){
			registroAtendimentoSolicitante.setComplementoEndereco("" + arrayEndereco[16]);
		}

		// Número do Imóvel
		if(arrayEndereco[22] != null){
			registroAtendimentoSolicitante.setNumeroImovel("" + arrayEndereco[22]);
		}

		endereco = registroAtendimentoSolicitante.getEnderecoFormatado();

		return endereco;
	}

	/**
	 * Obter o objeto de registro atendimento para recuperar Endereço Autor:
	 * Sávio Luiz
	 */

	public RegistroAtendimento pesquisarRegistroAtendimentoEndereco(Integer idRegistroAtendimento) throws ControladorException{

		Collection colecaoEndereco = null;
		try{
			colecaoEndereco = repositorioEndereco.pesquisarEnderecoRegistroAtendimentoFormatado(idRegistroAtendimento);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		RegistroAtendimento registroAtendimento = new RegistroAtendimento();

		Iterator enderecoIterator = colecaoEndereco.iterator();

		Object[] arrayEndereco = (Object[]) enderecoIterator.next();

		// idlogradouroCep e idlogradouroBairro
		if(arrayEndereco[18] != null && arrayEndereco[19] != null){

			LogradouroCep logradouroCep = null;
			if(arrayEndereco[18] != null){

				logradouroCep = new LogradouroCep();
				logradouroCep.setId((Integer) arrayEndereco[18]);

				// id do Logradouro
				Logradouro logradouro = null;
				if(arrayEndereco[17] != null){
					logradouro = new Logradouro();
					logradouro.setId((Integer) arrayEndereco[17]);
					logradouro.setNome("" + arrayEndereco[0]);
				}
				LogradouroTipo logradouroTipo = null;
				// Descrição abreviada logradouro tipo
				if(arrayEndereco[1] != null){
					logradouroTipo = new LogradouroTipo();
					logradouroTipo.setDescricao("" + arrayEndereco[1]);
					logradouro.setLogradouroTipo(logradouroTipo);
				}
				LogradouroTitulo logradouroTitulo = null;
				// Descrição abreviada logradouro titulo
				if(arrayEndereco[2] != null){
					logradouroTitulo = new LogradouroTitulo();
					logradouroTitulo.setDescricao("" + arrayEndereco[2]);
					logradouro.setLogradouroTitulo(logradouroTitulo);
				}
				// id do CEP
				Cep cep = null;
				if(arrayEndereco[9] != null){
					cep = new Cep();
					cep.setCepId((Integer) arrayEndereco[9]);
					cep.setCodigo((Integer) arrayEndereco[15]);
				}

				logradouroCep.setLogradouro(logradouro);
				logradouroCep.setCep(cep);
			}

			registroAtendimento.setLogradouroCep(logradouroCep);

			LogradouroBairro logradouroBairro = null;
			if(arrayEndereco[19] != null){

				logradouroBairro = new LogradouroBairro();
				logradouroBairro.setId((Integer) arrayEndereco[19]);

				Bairro bairro = null;
				// nome bairro
				if(arrayEndereco[3] != null){
					bairro = new Bairro();
					bairro.setId((Integer) arrayEndereco[3]);
					bairro.setNome("" + arrayEndereco[4]);
				}

				Municipio municipio = null;
				// nome municipio
				if(arrayEndereco[5] != null){
					municipio = new Municipio();
					municipio.setId((Integer) arrayEndereco[5]);
					municipio.setNome("" + arrayEndereco[6]);

					// id da unidade federação
					if(arrayEndereco[7] != null){
						UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
						unidadeFederacao.setId((Integer) arrayEndereco[7]);
						unidadeFederacao.setSigla("" + arrayEndereco[8]);
						municipio.setUnidadeFederacao(unidadeFederacao);
					}

					bairro.setMunicipio(municipio);
				}

				logradouroBairro.setBairro(bairro);
			}

			registroAtendimento.setLogradouroBairro(logradouroBairro);

			// complemento endereço
			if(arrayEndereco[16] != null){
				registroAtendimento.setComplementoEndereco("" + arrayEndereco[16]);
			}

			// número do imóvel
			if(arrayEndereco.length >= 23 && arrayEndereco[22] != null){

				registroAtendimento.setNumeroImovel("" + arrayEndereco[22]);
			}
		}

		return registroAtendimento;
	}

	/**
	 * Obter os parametros de logradouroCep para o endereço Autor: Sávio Luiz
	 */

	public LogradouroCep pesquisarLogradouroCepEndereco(Integer idLogradouroCep) throws ControladorException{

		Collection colecaoLogradouroCepEndereco = null;
		try{
			colecaoLogradouroCepEndereco = repositorioEndereco.obterDadosLogradouroCepEndereco(idLogradouroCep);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		LogradouroCep logradouroCep = new LogradouroCep();
		logradouroCep.setId(idLogradouroCep);

		Iterator enderecoIterator = colecaoLogradouroCepEndereco.iterator();

		Object[] arrayEndereco = (Object[]) enderecoIterator.next();

		// id do Logradouro
		Logradouro logradouro = null;
		if(arrayEndereco[0] != null){
			logradouro = new Logradouro();
			logradouro.setId((Integer) arrayEndereco[0]);
			logradouro.setNome((String) arrayEndereco[1]);
		}
		LogradouroTipo logradouroTipo = null;
		// Descrição logradouro tipo
		if(arrayEndereco[2] != null){
			logradouroTipo = new LogradouroTipo();
			logradouroTipo.setDescricaoAbreviada("" + arrayEndereco[2]);
			logradouro.setLogradouroTipo(logradouroTipo);
		}
		LogradouroTitulo logradouroTitulo = null;
		// Descrição logradouro titulo
		if(arrayEndereco[3] != null){
			logradouroTitulo = new LogradouroTitulo();
			logradouroTitulo.setDescricaoAbreviada("" + arrayEndereco[3]);
			logradouro.setLogradouroTitulo(logradouroTitulo);
		}
		// id do CEP
		Cep cep = null;
		if(arrayEndereco[4] != null){
			cep = new Cep();
			cep.setCepId((Integer) arrayEndereco[4]);
			cep.setCodigo((Integer) arrayEndereco[5]);
		}

		logradouroCep.setLogradouro(logradouro);
		logradouroCep.setCep(cep);

		return logradouroCep;
	}

	/**
	 * Obter os parametros de logradouroBairro para o endereço Autor: Sávio Luiz
	 */

	public LogradouroBairro pesquisarLogradouroBairroEndereco(Integer idLogradouroBairro) throws ControladorException{

		Collection colecaoLogradouroCepEndereco = null;
		try{
			colecaoLogradouroCepEndereco = repositorioEndereco.obterDadosLogradouroBairroEndereco(idLogradouroBairro);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		LogradouroBairro logradouroBairro = new LogradouroBairro();
		logradouroBairro.setId(idLogradouroBairro);

		Iterator enderecoIterator = colecaoLogradouroCepEndereco.iterator();

		Object[] arrayEndereco = (Object[]) enderecoIterator.next();

		// id do Logradouro
		Bairro bairro = null;
		if(arrayEndereco[0] != null){
			bairro = new Bairro();
			bairro.setId((Integer) arrayEndereco[0]);
			bairro.setNome((String) arrayEndereco[1]);
		}
		Municipio municipio = null;
		// Descrição logradouro tipo
		if(arrayEndereco[2] != null){
			municipio = new Municipio();
			municipio.setId((Integer) arrayEndereco[2]);
			municipio.setNome((String) arrayEndereco[3]);
		}
		UnidadeFederacao unidadeFederacao = null;
		// Descrição logradouro tipo
		if(arrayEndereco[4] != null){
			unidadeFederacao = new UnidadeFederacao();
			unidadeFederacao.setId((Integer) arrayEndereco[4]);
			unidadeFederacao.setSigla((String) arrayEndereco[5]);
		}

		municipio.setUnidadeFederacao(unidadeFederacao);
		bairro.setMunicipio(municipio);
		logradouroBairro.setBairro(bairro);

		return logradouroBairro;
	}

	/**
	 * Pesquisar os Endereços do Cliente
	 * [UC0474] Consultar Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 19/09/2006
	 * @param idCliente
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarClientesEnderecosAbreviado(Integer idCliente) throws ControladorException{

		Collection<String> colecaoClienteEndereco = null;

		String endereco = null;
		Collection colecaoEndereco = null;
		try{
			colecaoEndereco = repositorioEndereco.pesquisarClientesEnderecosAbreviado(idCliente);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		ClienteEndereco clienteEndereco = new ClienteEndereco();
		if(colecaoEndereco != null && !colecaoEndereco.isEmpty()){
			Iterator enderecoIterator = colecaoEndereco.iterator();

			colecaoClienteEndereco = new ArrayList();
			while(enderecoIterator.hasNext()){
				// cria um array de objetos para pegar os parametros de
				// retorno da pesquisa
				Object[] arrayEndereco = (Object[]) enderecoIterator.next();

				LogradouroCep logradouroCep = null;
				if(arrayEndereco[20] != null){

					logradouroCep = new LogradouroCep();
					logradouroCep.setId((Integer) arrayEndereco[20]);

					// id do Logradouro
					Logradouro logradouro = null;
					if(arrayEndereco[19] != null){
						logradouro = new Logradouro();
						logradouro.setId((Integer) arrayEndereco[19]);
						logradouro.setNome("" + arrayEndereco[0]);
					}
					LogradouroTipo logradouroTipo = null;
					// Descrição logradouro tipo
					if(arrayEndereco[22] != null){
						logradouroTipo = new LogradouroTipo();
						logradouroTipo.setDescricaoAbreviada("" + arrayEndereco[22]);
						logradouro.setLogradouroTipo(logradouroTipo);
					}
					LogradouroTitulo logradouroTitulo = null;
					// Descrição logradouro titulo
					if(arrayEndereco[23] != null){
						logradouroTitulo = new LogradouroTitulo();
						logradouroTitulo.setDescricaoAbreviada("" + arrayEndereco[23]);
						logradouro.setLogradouroTitulo(logradouroTitulo);
					}
					// id do CEP
					Cep cep = null;
					if(arrayEndereco[10] != null){
						cep = new Cep();
						cep.setCepId((Integer) arrayEndereco[10]);
						cep.setCodigo((Integer) arrayEndereco[16]);
					}

					logradouroCep.setLogradouro(logradouro);
					logradouroCep.setCep(cep);
				}

				clienteEndereco.setLogradouroCep(logradouroCep);

				LogradouroBairro logradouroBairro = null;
				if(arrayEndereco[21] != null){

					logradouroBairro = new LogradouroBairro();
					logradouroBairro.setId((Integer) arrayEndereco[21]);

					Bairro bairro = null;
					// nome bairro
					if(arrayEndereco[3] != null){
						bairro = new Bairro();
						bairro.setId((Integer) arrayEndereco[3]);
						bairro.setNome("" + arrayEndereco[4]);
					}

					Municipio municipio = null;
					// nome municipio
					if(arrayEndereco[5] != null){
						municipio = new Municipio();
						municipio.setId((Integer) arrayEndereco[5]);
						municipio.setNome("" + arrayEndereco[6]);

						// id da unidade federação
						if(arrayEndereco[7] != null){
							UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
							unidadeFederacao.setId((Integer) arrayEndereco[7]);
							unidadeFederacao.setSigla("" + arrayEndereco[8]);
							municipio.setUnidadeFederacao(unidadeFederacao);
						}

						bairro.setMunicipio(municipio);
					}

					logradouroBairro.setBairro(bairro);
				}

				clienteEndereco.setLogradouroBairro(logradouroBairro);

				// descricao de endereço referência
				if(arrayEndereco[9] != null){
					EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
					enderecoReferencia.setDescricaoAbreviada("" + arrayEndereco[9]);
					clienteEndereco.setEnderecoReferencia(enderecoReferencia);
				}

				// numero imovel
				if(arrayEndereco[17] != null){
					clienteEndereco.setNumero("" + arrayEndereco[17]);
				}

				// complemento endereço
				if(arrayEndereco[18] != null){
					clienteEndereco.setComplemento("" + arrayEndereco[18]);
				}

				endereco = clienteEndereco.getEnderecoFormatadoAbreviado();

				colecaoClienteEndereco.add(endereco);
			}
		}

		return colecaoClienteEndereco;

	}

	/**
	 * Pesquisar o endereço abreviado a partir do id do imóvel
	 * [UC0483] - Obter Endereço Abreviado
	 * 
	 * @author Rafael Corrêa
	 * @date 18/10/2006
	 * @param idImovel
	 * @return String
	 * @throws ControladorException
	 */

	public String obterEnderecoAbreviadoImovel(Integer idImovel) throws ControladorException{

		Object[] dadosEndereco = null;
		try{
			dadosEndereco = repositorioEndereco.obterEnderecoAbreviadoImovel(idImovel);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		String endereco = "";

		if(dadosEndereco != null){

			// Nome do Logradouro
			if(dadosEndereco[0] != null){
				endereco = endereco + dadosEndereco[0].toString().trim();
			}

			// Número do Imóvel
			if(dadosEndereco[1] != null){
				endereco = endereco + ", " + dadosEndereco[1].toString().trim();
			}

			// Nome do Bairro
			if(dadosEndereco[2] != null){
				endereco = endereco + " - " + dadosEndereco[2].toString().trim();
			}

		}

		return endereco;
	}

	/**
	 * Pesquisar o endereço abreviado a partir do id do RA
	 * [UC0483] - Obter Endereço Abreviado
	 * 
	 * @author Rafael Corrêa
	 * @date 18/10/2006
	 * @param idImovel
	 * @return String
	 * @throws ControladorException
	 */

	public String obterEnderecoAbreviadoRA(Integer idRA) throws ControladorException{

		Object[] dadosEndereco = null;
		try{
			dadosEndereco = repositorioEndereco.obterEnderecoAbreviadoRA(idRA);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		String endereco = "";

		if(dadosEndereco != null){

			// Nome do Logradouro
			if(dadosEndereco[0] != null){
				endereco = endereco + dadosEndereco[0].toString().trim();
			}

			// Número do Imóvel
			if(dadosEndereco[1] != null){
				endereco = endereco + ", " + dadosEndereco[1].toString().trim();
			}

			// Nome do Bairro
			if(dadosEndereco[2] != null){
				endereco = endereco + " - " + dadosEndereco[2].toString().trim();
			}

		}

		return endereco;
	}

	/**
	 * [UC0348] Emitir Contas por cliente responsavel CAERN
	 * Pesquisar endereco formatado para cliente
	 * 
	 * @author Raphael Rossiter
	 * @data 22/05/2007
	 * @param idCliente
	 *            ,
	 * @return Collection
	 */
	public String[] pesquisarEnderecoFormatadoClienteDividido(Integer idCliente) throws ControladorException{

		// 0 - Endereço sem municipio e UF
		// 1 - municipio
		// 2 - unidade federeção
		// 3 - bairro
		// 4 - CEP
		String[] parmsEndereco = new String[5];
		Collection colecaoEndereco = null;
		String endereco = "";
		String nomeMunicipio = "";
		String siglaUnidadeFederacao = "";
		String nomeBairro = "";
		String cepFormatado = "";

		try{

			colecaoEndereco = repositorioEndereco.pesquisarEnderecoFormatadoCliente(idCliente);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		Imovel imovel = new Imovel();

		if(colecaoEndereco != null && !colecaoEndereco.isEmpty()){
			Iterator enderecoIterator = colecaoEndereco.iterator();
			Object[] arrayEndereco = (Object[]) enderecoIterator.next();

			if(arrayEndereco[20] != null && arrayEndereco[21] != null){

				LogradouroCep logradouroCep = null;
				if(arrayEndereco[20] != null){

					logradouroCep = new LogradouroCep();
					logradouroCep.setId((Integer) arrayEndereco[20]);

					// id do Logradouro
					Logradouro logradouro = null;
					if(arrayEndereco[19] != null){
						logradouro = new Logradouro();
						logradouro.setId((Integer) arrayEndereco[19]);
						logradouro.setNome("" + arrayEndereco[0]);
					}
					LogradouroTipo logradouroTipo = null;
					// Descrição abreviada logradouro tipo
					if(arrayEndereco[1] != null){
						logradouroTipo = new LogradouroTipo();
						logradouroTipo.setDescricao("" + arrayEndereco[1]);
						if(arrayEndereco[22] != null){
							logradouroTipo.setDescricaoAbreviada("" + arrayEndereco[22]);
						}
						logradouro.setLogradouroTipo(logradouroTipo);
					}
					LogradouroTitulo logradouroTitulo = null;
					// Descrição abreviada logradouro titulo
					if(arrayEndereco[2] != null){
						logradouroTitulo = new LogradouroTitulo();
						logradouroTitulo.setDescricao("" + arrayEndereco[2]);
						if(arrayEndereco[23] != null){
							logradouroTitulo.setDescricaoAbreviada("" + arrayEndereco[23]);
						}
						logradouro.setLogradouroTitulo(logradouroTitulo);
					}
					if(arrayEndereco[10] != null){
						cepFormatado = "" + (Integer) arrayEndereco[16];
					}

					logradouroCep.setLogradouro(logradouro);
				}

				imovel.setLogradouroCep(logradouroCep);

				LogradouroBairro logradouroBairro = null;
				if(arrayEndereco[21] != null){

					logradouroBairro = new LogradouroBairro();
					logradouroBairro.setId((Integer) arrayEndereco[21]);

					// nome bairro
					if(arrayEndereco[3] != null){
						nomeBairro = "" + arrayEndereco[4];
					}

					// nome municipio
					if(arrayEndereco[5] != null){
						nomeMunicipio = "" + arrayEndereco[6];

						// id da unidade federação
						if(arrayEndereco[7] != null){
							siglaUnidadeFederacao = "" + arrayEndereco[8];
						}

					}
				}

				imovel.setLogradouroBairro(logradouroBairro);

				// descricao de endereço referência
				if(arrayEndereco[9] != null){
					EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
					enderecoReferencia.setDescricao("" + arrayEndereco[9]);
					if(arrayEndereco[24] != null){
						enderecoReferencia.setDescricaoAbreviada("" + arrayEndereco[24]);
					}
					imovel.setEnderecoReferencia(enderecoReferencia);
				}

				// numero imovel
				if(arrayEndereco[17] != null){
					imovel.setNumeroImovel("" + arrayEndereco[17]);
				}

				// complemento endereço
				if(arrayEndereco[18] != null){
					imovel.setComplementoEndereco("" + arrayEndereco[18]);
				}

				endereco = imovel.getEnderecoFormatadoAbreviado();

			}
		}

		parmsEndereco[0] = endereco;
		parmsEndereco[1] = nomeMunicipio;
		parmsEndereco[2] = siglaUnidadeFederacao;
		parmsEndereco[3] = nomeBairro;
		parmsEndereco[4] = cepFormatado;

		return parmsEndereco;

	}

	/**
	 * [UC0210] - Obter Endereço Autor: Sávio Luiz
	 */

	public String pesquisarEnderecoAbreviadoCAER(Integer idImovel) throws ControladorException{

		String endereco = null;
		Collection colecaoEndereco = null;
		try{
			colecaoEndereco = repositorioEndereco.pesquisarEnderecoAbreviadoCAER(idImovel);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		Imovel imovel = new Imovel();

		Iterator enderecoIterator = colecaoEndereco.iterator();
		if(colecaoEndereco != null && !colecaoEndereco.isEmpty()){
			Object[] arrayEndereco = (Object[]) enderecoIterator.next();

			if(arrayEndereco[20] != null && arrayEndereco[21] != null){

				LogradouroCep logradouroCep = null;
				if(arrayEndereco[20] != null){

					logradouroCep = new LogradouroCep();
					logradouroCep.setId((Integer) arrayEndereco[20]);

					// id do Logradouro
					Logradouro logradouro = null;
					if(arrayEndereco[19] != null){
						logradouro = new Logradouro();
						logradouro.setId((Integer) arrayEndereco[19]);
						logradouro.setNome("" + arrayEndereco[0]);
					}
					LogradouroTipo logradouroTipo = null;
					// Descrição abreviada logradouro tipo
					if(arrayEndereco[1] != null){
						logradouroTipo = new LogradouroTipo();
						logradouroTipo.setDescricao("" + arrayEndereco[1]);
						if(arrayEndereco[22] != null){
							logradouroTipo.setDescricaoAbreviada("" + arrayEndereco[22]);
						}
						logradouro.setLogradouroTipo(logradouroTipo);
					}
					LogradouroTitulo logradouroTitulo = null;
					// Descrição abreviada logradouro titulo
					if(arrayEndereco[2] != null){
						logradouroTitulo = new LogradouroTitulo();
						logradouroTitulo.setDescricao("" + arrayEndereco[2]);
						if(arrayEndereco[23] != null){
							logradouroTitulo.setDescricaoAbreviada("" + arrayEndereco[23]);
						}
						logradouro.setLogradouroTitulo(logradouroTitulo);
					}
					// id do CEP
					Cep cep = null;

					logradouroCep.setLogradouro(logradouro);
					logradouroCep.setCep(cep);
				}

				imovel.setLogradouroCep(logradouroCep);

				LogradouroBairro logradouroBairro = null;
				if(arrayEndereco[21] != null){

					logradouroBairro = new LogradouroBairro();
					logradouroBairro.setId((Integer) arrayEndereco[21]);

					Bairro bairro = null;
					// nome bairro
					if(arrayEndereco[3] != null){
						bairro = new Bairro();
						bairro.setId((Integer) arrayEndereco[3]);
						bairro.setNome("" + arrayEndereco[4]);
					}

					Municipio municipio = null;
					// nome municipio
					if(arrayEndereco[5] != null){
						municipio = new Municipio();
						municipio.setId((Integer) arrayEndereco[5]);
						municipio.setNome("" + arrayEndereco[6]);

						// id da unidade federação
						if(arrayEndereco[7] != null){
							UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
							unidadeFederacao.setId((Integer) arrayEndereco[7]);
							unidadeFederacao.setSigla("" + arrayEndereco[8]);
							municipio.setUnidadeFederacao(unidadeFederacao);
						}

						bairro.setMunicipio(municipio);
					}

					logradouroBairro.setBairro(bairro);
				}

				imovel.setLogradouroBairro(logradouroBairro);

				// descricao de endereço referência
				if(arrayEndereco[9] != null){
					EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
					enderecoReferencia.setDescricao("" + arrayEndereco[9]);
					if(arrayEndereco[24] != null){
						enderecoReferencia.setDescricaoAbreviada("" + arrayEndereco[24]);
					}
					imovel.setEnderecoReferencia(enderecoReferencia);
				}

				// numero imovel
				if(arrayEndereco[17] != null){
					imovel.setNumeroImovel("" + arrayEndereco[17]);
				}

				// complemento endereço
				if(arrayEndereco[18] != null){
					imovel.setComplementoEndereco("" + arrayEndereco[18]);
				}

				endereco = imovel.getEnderecoFormatadoAbreviado();

			}else{

				FiltroImovelEnderecoAnterior filtroImovelEnderecoAnterior = new FiltroImovelEnderecoAnterior();

				filtroImovelEnderecoAnterior.adicionarParametro(new ParametroSimples(FiltroImovelEnderecoAnterior.ID, idImovel));

				Collection colecaoImovelEnderecoAnterior = this.getControladorUtil().pesquisar(filtroImovelEnderecoAnterior,
								ImovelEnderecoAnterior.class.getName());

				if(colecaoImovelEnderecoAnterior != null && !colecaoImovelEnderecoAnterior.isEmpty()){

					ImovelEnderecoAnterior imovelEnderecoAnterior = ((ImovelEnderecoAnterior) colecaoImovelEnderecoAnterior.iterator()
									.next());

					endereco = imovelEnderecoAnterior.getEnderecoAnterior();
				}
			}
		}

		return endereco;
	}

	/**
	 * Pesquisar o cep pelo codigo do cep
	 * 
	 * @author Sávio Luiz
	 * @date 05/11/2007
	 */

	public Cep pesquisarCep(Integer codigoCep) throws ControladorException{

		try{
			return repositorioEndereco.pesquisarCep(codigoCep);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Verifica a existência do endereço de correspondência do cliente pelo seu id
	 */
	public boolean verificarExistenciaClienteEndereco(Integer idCliente) throws ControladorException{

		try{
			return repositorioEndereco.verificarExistenciaClienteEndereco(idCliente);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisar Logradouro Bairro
	 * 
	 * @author Virgínia Melo
	 * @date 24/07/2009
	 * @param idLogradouroBairro
	 * @return LogradouroBairro
	 * @throws ControladorException
	 */
	public LogradouroBairro pesquisarLogradouroBairro(Integer idLogradouroBairro) throws ControladorException{

		try{
			return repositorioEndereco.pesquisarLogradouroBairro(idLogradouroBairro);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	public String pesquisarEnderecoClienteAbreviado(Integer idCliente, boolean indicadorAbreviado) throws ControladorException{

		Object[] arrayDadosEndereco = this.pesquisarEnderecoClienteAbreviadoLista(idCliente, indicadorAbreviado);
		return (String) arrayDadosEndereco[0];
	}

	/**
	 * [UC0XXX] Gerar Relatório Logradouro Geral
	 * 
	 * @author Anderson Italo
	 * @date 26/01/2011
	 *       Obter dados dos Logradouros pelos parametros informados
	 */
	public Collection pesquisarLogradourosPorMunicipio(Integer idMunicipio) throws ControladorException{

		Collection colecaoRetorno = null;
		try{
			colecaoRetorno = this.repositorioEndereco.pesquisarLogradourosPorMunicipio(idMunicipio);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return colecaoRetorno;
	}

	/**
	 * [UC0XXX] Gerar Relatório Logradouro Geral
	 * 
	 * @author Anderson Italo
	 * @date 26/01/2011
	 *       Obter total dos Logradouros pelos por Município
	 */
	public Integer calcularTotalLogradourosPorMunicipio(Integer idMunicipio) throws ControladorException{

		Integer retorno = null;
		try{
			retorno = this.repositorioEndereco.calcularTotalLogradourosPorMunicipio(idMunicipio);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * Obtem o Bairro pelo id do imóvel (se informado), caso não informado o id do imóvel ou não
	 * encontrado bairro para aquele imóvel, então procura o bairro pelo id do RA
	 * 
	 * @author isilva
	 * @param idImovel
	 * @param idRegistroAtendimento
	 * @return
	 * @throws ControladorException
	 */
	public Bairro obterBairroPorImovelOuRA(Integer idImovel, Integer idRegistroAtendimento) throws ControladorException{

		Bairro retorno = null;
		try{
			retorno = this.repositorioEndereco.obterBairroPorImovelOuRA(idImovel, idRegistroAtendimento);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * Inserir o cep
	 * 
	 * @param codigo
	 * @param sigla
	 * @param municipio
	 * @param bairro
	 * @param logradouro
	 * @param descricaoTipoLogradouro
	 * @param indicadorUso
	 * @param ultimaAlteracao
	 * @param Coleção
	 *            contendo todos os CEPs lidos no arquivo
	 * @return id da entidade inserida
	 * @throws ControladorException
	 */
	public Integer inserirCep(Integer codigo, String sigla, String municipio, String bairro, String logradouro,
					String descricaoTipoLogradouro) throws ControladorException{

		Integer retorno = 0;
		try{
			if(repositorioEndereco.pesquisarCep(codigo) == null){
				Cep cep = new Cep();

				cep.setCodigo(codigo);
				cep.setSigla(sigla);
				cep.setMunicipio(municipio);
				cep.setBairro(bairro);
				cep.setLogradouro(logradouro);
				cep.setDescricaoTipoLogradouro(descricaoTipoLogradouro);
				cep.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
				cep.setUltimaAlteracao(new Date());
				CepTipo cepTipo = new CepTipo();
				cepTipo.setId(CepTipo.INICIAL);
				cep.setCepTipo(cepTipo);
				retorno = (Integer) getControladorUtil().inserir(cep);
			}else{
				throw new ControladorException("atencao.cep.cadastrado");
			}

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	public Object[] obterDadosEndereco(Integer idImovel) throws ControladorException{

		Object[] arrayEndereco = null;

		try{
			Collection colecaoEndereco = null;

			colecaoEndereco = repositorioEndereco.pesquisarEndereco(idImovel);

			Iterator enderecoIterator = colecaoEndereco.iterator();

			arrayEndereco = (Object[]) enderecoIterator.next();

		}catch(ErroRepositorioException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arrayEndereco;
	}

	/**
	 * Pesquisa na View logradouro por munícipio, bairro, noemLogradouro e retorna uma lista de
	 * logradouros
	 * 
	 * @author Josenildo Neves
	 * @date 08/08/2012
	 */
	public List<LogradouroJSONHelper> pesquisarViewLogradouro(Integer idMunicipio, Integer idBairro, String nomeLogradouro)
					throws ControladorException{

		List<LogradouroJSONHelper> retorno = null;
		try{
			retorno = this.repositorioEndereco.pesquisarViewLogradouro(idMunicipio, idBairro, nomeLogradouro);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */

	public Integer obterMunicipio(Integer idImovel) throws ControladorException{

		try{
			return this.repositorioEndereco.obterMunicipio(idImovel);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

	}

	public Integer pesquisarCepFiltroCount(String nomeLogradouro, String codigoLado, String faixa, String nomeMunicipio)
					throws ControladorException{

		try{
			return repositorioEndereco.pesquisarCepFiltroCount(nomeLogradouro, codigoLado, faixa, nomeMunicipio);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	public Collection pesquisarCepFiltro(String nomeLogradouro, String codigoLado, String faixa, Integer numeroPaginas, String nomeMunicipio)
					throws ControladorException{

		try{
			return repositorioEndereco.pesquisarCepFiltro(nomeLogradouro, codigoLado, faixa, numeroPaginas, nomeMunicipio);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Método responsável por obter o controlador de parâmetros
	 * 
	 * @author Felipe Rosacruz
	 * @return ControladorParametroSistema O controlador de parâmetros
	 */
	private ControladorParametroSistema getControladorParametroSistema(){

		return (ControladorParametroSistema) SpringBeanLocator.getInstancia().getBeanPorID(
						ControladorParametroSistema.BEAN_ID_CONTROLADOR_PARAMETRO_SISTEMA);
	}

	/**
	 * @author Felipe rosacruz
	 * @date 22/08/2013
	 * @throws ControladorException
	 */
	public String pesquisarEnderecoFormatadoEmpresa() throws ControladorException{

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
		String enderecoEmpresa = "";

		try{
			FiltroLogradouro filtroLogradouro = new FiltroLogradouro();
			filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, sistemaParametro.getLogradouro().getId()));
			filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("logradouroTitulo");
			filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("logradouroTipo");
			filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("municipio");
			Collection colecaoLogradouro = getControladorUtil().pesquisar(filtroLogradouro, Logradouro.class.getName());

			Logradouro logradouro = (Logradouro) Util.retonarObjetoDeColecao(colecaoLogradouro);

			String bairro = sistemaParametro.getBairro().getNome();
			String numero = sistemaParametro.getNumeroImovel();
			String cep = sistemaParametro.getCep().getCepFormatado();
			
			
			String nomeEstadoAbreviado = (String) getControladorParametroSistema().obterValorDoParametroPorCodigo(
							ParametroGeral.P_NOME_ESTADO_ABREVIADO_EMPRESA.getCodigo());

			if(logradouro != null){

				String logradouroTitulo = null;
				String logradouroTipo = null;
				String municipioNome = null;

				if(logradouro.getLogradouroTitulo() != null){
					logradouroTitulo = logradouro.getLogradouroTitulo().getDescricao();
				}
				if(logradouro.getLogradouroTipo() != null){
					logradouroTipo = logradouro.getLogradouroTipo().getDescricao();
				}
				String logradouroNome =logradouro.getNome();
				
				if(logradouro.getMunicipio() != null){
					municipioNome = logradouro.getMunicipio().getNome();
				}

				if(logradouroTitulo == null){
					logradouroTitulo = "";
				}
				if(logradouroTipo == null){
					logradouroTipo = "";
				}
				if(logradouroNome == null){
					logradouroNome = "";
				}
				if(municipioNome == null){
					municipioNome = "";
				}
				
				enderecoEmpresa = logradouroTipo + " " + logradouroTitulo
								+ " " + logradouroNome + ", " + numero + " - " + bairro + " - " + municipioNome 
								+ " - " + nomeEstadoAbreviado + " CEP " + cep;
			}

		}catch(Exception ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
		return enderecoEmpresa;
	}

	/**
	 * @param idImovel
	 * @param limiteTamanhoEndereco
	 */

	public String pesquisarEnderecoComDetalhamento(Integer idImovel) throws ControladorException{

		String endereco = "";
		String[] enderecoArray = pesquisarEnderecoComDetalhamento(idImovel, 0);

		if(!Util.isVazioOrNulo(enderecoArray)){
			endereco = enderecoArray[0];
		}

		return endereco;
	}

	public String[] pesquisarEnderecoComDetalhamento(Integer idImovel, int limiteTamanhoEndereco) throws ControladorException{

		String[] endereco = null;
		Collection colecaoEndereco = null;
		try{
			colecaoEndereco = repositorioEndereco.pesquisarEndereco(idImovel);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		Imovel imovel = new Imovel();

		Iterator enderecoIterator = colecaoEndereco.iterator();

		Object[] arrayEndereco = (Object[]) enderecoIterator.next();

		if(arrayEndereco[20] != null && arrayEndereco[21] != null){

			LogradouroCep logradouroCep = null;
			if(arrayEndereco[20] != null){

				logradouroCep = new LogradouroCep();
				logradouroCep.setId((Integer) arrayEndereco[20]);

				// id do Logradouro
				Logradouro logradouro = null;
				if(arrayEndereco[19] != null){
					logradouro = new Logradouro();
					logradouro.setId((Integer) arrayEndereco[19]);
					logradouro.setNome("" + arrayEndereco[0]);
				}
				LogradouroTipo logradouroTipo = null;
				// Descrição logradouro tipo
				if(arrayEndereco[22] != null){
					logradouroTipo = new LogradouroTipo();
					logradouroTipo.setDescricao("" + arrayEndereco[22]);
					logradouro.setLogradouroTipo(logradouroTipo);
				}
				LogradouroTitulo logradouroTitulo = null;
				// Descrição logradouro titulo
				if(arrayEndereco[23] != null){
					logradouroTitulo = new LogradouroTitulo();
					logradouroTitulo.setDescricao("" + arrayEndereco[23]);
					logradouro.setLogradouroTitulo(logradouroTitulo);
				}
				// id do CEP
				Cep cep = null;
				if(arrayEndereco[10] != null){
					cep = new Cep();
					cep.setCepId((Integer) arrayEndereco[10]);
					cep.setCodigo((Integer) arrayEndereco[16]);
				}

				logradouroCep.setLogradouro(logradouro);
				logradouroCep.setCep(cep);
			}

			imovel.setLogradouroCep(logradouroCep);

			LogradouroBairro logradouroBairro = null;
			if(arrayEndereco[21] != null){

				logradouroBairro = new LogradouroBairro();
				logradouroBairro.setId((Integer) arrayEndereco[21]);

				Bairro bairro = null;
				// nome bairro
				if(arrayEndereco[3] != null){
					bairro = new Bairro();
					bairro.setId((Integer) arrayEndereco[3]);
					bairro.setNome("" + arrayEndereco[4]);
				}

				Municipio municipio = null;
				// nome municipio
				if(arrayEndereco[5] != null){
					municipio = new Municipio();
					municipio.setId((Integer) arrayEndereco[5]);
					municipio.setNome("" + arrayEndereco[6]);

					// id da unidade federação
					if(arrayEndereco[7] != null){
						UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
						unidadeFederacao.setId((Integer) arrayEndereco[7]);
						unidadeFederacao.setSigla("" + arrayEndereco[8]);
						municipio.setUnidadeFederacao(unidadeFederacao);
					}

					bairro.setMunicipio(municipio);
				}

				logradouroBairro.setBairro(bairro);
			}

			imovel.setLogradouroBairro(logradouroBairro);

			// descricao de endereço referência
			if(arrayEndereco[24] != null){
				EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
				enderecoReferencia.setDescricao("" + arrayEndereco[24]);
				imovel.setEnderecoReferencia(enderecoReferencia);
			}

			// numero imovel
			if(arrayEndereco[17] != null){
				imovel.setNumeroImovel("" + arrayEndereco[17]);
			}

			// complemento endereço
			if(arrayEndereco[18] != null){
				imovel.setComplementoEndereco("" + arrayEndereco[18]);
			}

			endereco = imovel.obterEnderecoFormatadoComDetalhamento(limiteTamanhoEndereco);
		}else{

			FiltroImovelEnderecoAnterior filtroImovelEnderecoAnterior = new FiltroImovelEnderecoAnterior();

			filtroImovelEnderecoAnterior.adicionarParametro(new ParametroSimples(FiltroImovelEnderecoAnterior.ID, idImovel));

			Collection colecaoImovelEnderecoAnterior = this.getControladorUtil().pesquisar(filtroImovelEnderecoAnterior,
							ImovelEnderecoAnterior.class.getName());

			if(colecaoImovelEnderecoAnterior != null && !colecaoImovelEnderecoAnterior.isEmpty()){

				ImovelEnderecoAnterior imovelEnderecoAnterior = ((ImovelEnderecoAnterior) colecaoImovelEnderecoAnterior.iterator().next());

				String enderecoAnterior = imovelEnderecoAnterior.getEnderecoAnterior();

				// Se deve ser particionado, o endereço deve ser limitado conforme parâmetro passado
				if(limiteTamanhoEndereco > 0){
					enderecoAnterior = enderecoAnterior.substring(0, limiteTamanhoEndereco);
				}

				endereco = new String[2];
				endereco[0] = enderecoAnterior;
				endereco[1] = "";
			}
		}

		return endereco;
	}

	/**
	 * [UC0085] - Obter Endereço
	 * 
	 * @author Sávio Luiz
	 * @date 14/06/2006
	 * @param idCliente
	 * @return String
	 */
	public String pesquisarEnderecoClienteAbreviadoComDetalhamento(Integer idCliente) throws ControladorException{

		Object[] arrayDadosEndereco = this.pesquisarEnderecoClienteAbreviadoListaComDetalhamento(idCliente, false);
		return (String) arrayDadosEndereco[0];
	}

	/**
	 * @author Saulo Lima
	 * @date 24/03/2010
	 * @param idCliente
	 * @return Object[]:
	 *         0 - endereco (String)
	 *         1 - idLogradouro (Integer)
	 *         2 - numeroImovel (String)
	 */
	public Object[] pesquisarEnderecoClienteAbreviadoListaComDetalhamento(Integer idCliente, boolean indicadorAbreviado)
					throws ControladorException{

		String endereco = null;
		Integer idLogradouro = null;
		String numeroImovel = null;
		String bairroEntrega = "";
		String cepEntrega = "";
		String municipioEntrega = "";
		String ufEntrega = "";
		String complementoEndereco = "";

		Object[] retorno = new Object[8];

		Collection colecaoEndereco = null;
		try{
			colecaoEndereco = repositorioEndereco.pesquisarEnderecoClienteAbreviado(idCliente);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		ClienteEndereco clienteEndereco = new ClienteEndereco();

		Iterator enderecoIterator = colecaoEndereco.iterator();
		while(enderecoIterator.hasNext()){
			// cria um array de objetos para pegar os parametros de
			// retorno da pesquisa
			Object[] arrayEndereco = (Object[]) enderecoIterator.next();

			LogradouroCep logradouroCep = null;
			if(arrayEndereco[20] != null){

				logradouroCep = new LogradouroCep();
				logradouroCep.setId((Integer) arrayEndereco[20]);

				// id do Logradouro
				Logradouro logradouro = null;
				if(arrayEndereco[19] != null){
					logradouro = new Logradouro();
					logradouro.setId((Integer) arrayEndereco[19]);
					idLogradouro = (Integer) arrayEndereco[19];
					logradouro.setNome("" + arrayEndereco[0]);

					Municipio municipio = null;
					// nome municipio
					if(arrayEndereco[5] != null){
						municipio = new Municipio();
						municipio.setId((Integer) arrayEndereco[5]);
						municipio.setNome("" + arrayEndereco[6]);
						municipioEntrega = municipio.getNome();

						// id da unidade federação
						if(arrayEndereco[7] != null){
							UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
							unidadeFederacao.setId((Integer) arrayEndereco[7]);
							unidadeFederacao.setSigla("" + arrayEndereco[8]);
							municipio.setUnidadeFederacao(unidadeFederacao);

							ufEntrega = (String) arrayEndereco[8];
						}

						logradouro.setMunicipio(municipio);
					}
				}
				LogradouroTipo logradouroTipo = null;
				// Descrição logradouro tipo
				if(arrayEndereco[22] != null){
					logradouroTipo = new LogradouroTipo();
					logradouroTipo.setDescricaoAbreviada("" + arrayEndereco[22]);
					logradouro.setLogradouroTipo(logradouroTipo);
				}
				LogradouroTitulo logradouroTitulo = null;
				// Descrição logradouro titulo
				if(arrayEndereco[23] != null){
					logradouroTitulo = new LogradouroTitulo();
					logradouroTitulo.setDescricaoAbreviada("" + arrayEndereco[23]);
					logradouro.setLogradouroTitulo(logradouroTitulo);
				}
				// id do CEP
				Cep cep = null;
				if(arrayEndereco[10] != null){
					cep = new Cep();
					cep.setCepId((Integer) arrayEndereco[10]);
					cep.setCodigo((Integer) arrayEndereco[16]);
					cepEntrega = cep.getCodigo().toString();
				}

				logradouroCep.setLogradouro(logradouro);
				logradouroCep.setCep(cep);
			}

			clienteEndereco.setLogradouroCep(logradouroCep);

			LogradouroBairro logradouroBairro = null;
			if(arrayEndereco[21] != null){

				logradouroBairro = new LogradouroBairro();
				logradouroBairro.setId((Integer) arrayEndereco[21]);

				Bairro bairro = null;
				// nome bairro
				if(arrayEndereco[3] != null){
					bairro = new Bairro();
					bairro.setId((Integer) arrayEndereco[3]);
					bairro.setNome("" + arrayEndereco[4]);
					bairroEntrega = bairro.getNome();
				}

				Municipio municipio = null;
				// nome municipio
				if(arrayEndereco[25] != null){
					municipio = new Municipio();
					municipio.setId((Integer) arrayEndereco[25]);
					municipio.setNome("" + arrayEndereco[26]);
					municipioEntrega = municipio.getNome();

					// id da unidade federação
					if(arrayEndereco[27] != null){
						UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
						unidadeFederacao.setId((Integer) arrayEndereco[27]);
						unidadeFederacao.setSigla("" + arrayEndereco[28]);
						municipio.setUnidadeFederacao(unidadeFederacao);

						ufEntrega = (String) arrayEndereco[28];
					}

					bairro.setMunicipio(municipio);
				}

				logradouroBairro.setBairro(bairro);
			}

			clienteEndereco.setLogradouroBairro(logradouroBairro);

			// descricao de endereço referência
			if(arrayEndereco[9] != null){
				EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
				enderecoReferencia.setDescricaoAbreviada("" + arrayEndereco[9]);
				clienteEndereco.setEnderecoReferencia(enderecoReferencia);
			}

			// numero imovel
			if(arrayEndereco[17] != null){
				clienteEndereco.setNumero("" + arrayEndereco[17]);
				numeroImovel = "" + arrayEndereco[17];
			}

			// complemento endereço
			if(arrayEndereco[18] != null){
				clienteEndereco.setComplemento("" + arrayEndereco[18]);
				complementoEndereco = (String) arrayEndereco[18];
			}

			if(indicadorAbreviado){
				endereco = clienteEndereco.getEnderecoAbreviadoComDetalhamento();
			}else{
				endereco = clienteEndereco.getEnderecoFormatadoAbreviadoComDetalhamento();
			}

		}

		retorno[0] = endereco;
		retorno[1] = idLogradouro;
		retorno[2] = numeroImovel;
		retorno[3] = bairroEntrega;
		retorno[4] = cepEntrega;
		retorno[5] = municipioEntrega;
		retorno[6] = ufEntrega;
		retorno[7] = complementoEndereco;

		return retorno;
	}

	/**
	 * FIXME - MOVER PARA CONTROLADOR FATURAMENTO APOS IMPLEMENTAÇÃO
	 * 
	 * @return
	 * @throws ControladorException
	 */
	public Collection<PagamentoHistoricoAdmiteDevolucaoHelper> consultarPagamentosHistoricoAdmiteDevolucao(Integer idImovel,
					boolean creditoARealizar) throws ControladorException{

		Collection<PagamentoHistoricoAdmiteDevolucaoHelper> retorno = null;
		try{
			Collection<Object[]> consulta = repositorioEndereco.consultarPagamentosHistoricoAdmiteDevolucao(Integer.valueOf(idImovel),
							creditoARealizar);

			if(consulta != null && !consulta.isEmpty()){
				retorno = new ArrayList<PagamentoHistoricoAdmiteDevolucaoHelper>();

				for(Object[] obj : consulta){
					PagamentoHistoricoAdmiteDevolucaoHelper helper = new PagamentoHistoricoAdmiteDevolucaoHelper();

					helper.setIdPagamentoHistorico((Integer) obj[0]);
					helper.setDataPagamento(Util.formatarData((Date) obj[1]));
					helper.setValorPagamento((BigDecimal) obj[2]);
					helper.setSituacaoAtual(((Short) obj[3]).intValue());
					helper.setSituacaoAtualDescricao((String) obj[4]);

					Integer anoMesConta = (Integer) obj[5];
					Integer idGuiaPgto = (Integer) obj[6];
					Integer idDebitoACobrar = (Integer) obj[7];
					Integer numeroPrestacao = (Integer) obj[8];
					BigDecimal valorTotalContaHistorico = (BigDecimal) obj[9];
					BigDecimal valorPrestacaoGuiaPgtoHist = (BigDecimal) obj[10];
					BigDecimal valorDebitoACobrarPrestacoes = (BigDecimal) obj[11];
					Integer idAtualConta = (Integer) obj[12];
					Integer idAtualGuia = (Integer) obj[13];
					Integer idAtualDebito = (Integer) obj[14];

					if(anoMesConta != null){
						helper.setTipoIdentificaçãoDocumento(PagamentoHistoricoAdmiteDevolucaoHelper.TIPO_CONTA);
						helper.setIndentificacaoDocumento(Util.formatarAnoMesParaMesAno(anoMesConta));
						helper.setValorDocumento((BigDecimal) valorTotalContaHistorico);
						helper.setDebitoCreditoIndicadorAtual(idAtualConta);
					}

					if(idGuiaPgto != null){
						helper.setTipoIdentificaçãoDocumento(PagamentoHistoricoAdmiteDevolucaoHelper.TIPO_GUIA_PGTO);
						helper.setIndentificacaoDocumento(idGuiaPgto + PagamentoHistoricoAdmiteDevolucaoHelper.CONCATENAR_ID_NNPRESTACAO + numeroPrestacao);
						helper.setValorDocumento((BigDecimal) valorPrestacaoGuiaPgtoHist);
						helper.setDebitoCreditoIndicadorAtual(idAtualGuia);
					}

					if(idDebitoACobrar != null){
						helper.setTipoIdentificaçãoDocumento(PagamentoHistoricoAdmiteDevolucaoHelper.TIPO_DEBITO_A_COBRAR);
						if(numeroPrestacao == null || numeroPrestacao.equals(0)){
							numeroPrestacao = 1;
						}
						helper.setIndentificacaoDocumento(idDebitoACobrar + PagamentoHistoricoAdmiteDevolucaoHelper.CONCATENAR_ID_NNPRESTACAO + numeroPrestacao);
						helper.setValorDocumento((BigDecimal) valorDebitoACobrarPrestacoes);
						helper.setDebitoCreditoIndicadorAtual(idAtualDebito);
					}

					retorno.add(helper);
				}
			}
		}catch(Exception ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}
	
	public Integer pesquisarQuantidadePagamentosHistoricoCount(PagamentoHistoricoAdmiteDevolucaoHelper pagamento, String matriculaImovel)
					throws ControladorException{
		
		try{
			return repositorioEndereco.pesquisarQuantidadePagamentosHistoricoCount(pagamento, matriculaImovel);
		}catch(ErroRepositorioException e){
			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Pesquisar endereço sem referência
	 * 
	 * @author Hebert Falcão
	 * @date 12/11/2013
	 */
	public String pesquisarEnderecoSemReferencia(Integer idImovel) throws ControladorException{

		String endereco = "";
		String[] enderecoArray = this.pesquisarEndereco(idImovel, 0);

		if(!Util.isVazioOrNulo(enderecoArray)){
			endereco = enderecoArray[2];
		}

		return endereco;
	}

	/**
	 * Pesquisar o endereço abreviado a partir do id do RA
	 * [UC0483] - Obter Endereço Abreviado
	 * 
	 * @author Rafael Corrêa
	 * @date 18/10/2006
	 * @param idImovel
	 * @return String
	 * @throws ControladorException
	 */

	public String obterLogradouroTipoImovel(Integer idImovel) throws ControladorException{

		Object[] dadosEndereco = null;
		try{
			dadosEndereco = repositorioEndereco.obterLogradouroTipoImovel(idImovel);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		String endereco = "";

		if(dadosEndereco != null){

			// Nome do Logradouro
			if(dadosEndereco[0] != null){
				endereco = endereco + dadosEndereco[0].toString().trim();
			}



		}

		return endereco;
	}

	/**
	 * Agencia Virtual - Consultar Cep Por Logradouro e Bairro
	 * 
	 * @author Anderson Italo
	 * @date 16/03/2014
	 */
	public List<Object[]> pesquisarCepPorLogradouroEBairro(Integer idLogradouro, Integer idBairro) throws ControladorException{

		try{

			return repositorioEndereco.pesquisarCepPorLogradouroEBairro(idLogradouro, idBairro);
		}catch(ErroRepositorioException e){

			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0083] Gerar Dados para Leitura
	 * [SB0001] - Gerar Arquivo Convencional
	 * [SB0010] - Gerar Arquivo - Modelo 2
	 * <<Inclui>> [UC3148 - Obter Endereço de Entrega]
	 * 
	 * @author Anderson Italo
	 * @throws ControladorException
	 * @date 27/05/2014
	 */
	public Object[] obterEnderecoEntrega(Integer idImovel, Integer idImovelContaEnvio) throws ControladorException{

		Object[] retorno = new Object[12];
		String enderecoEntrega = "";
		Integer idLogradouroEntrega = null;
		String numeroImovelEntrega = null;
		String bairroImovelEntrega = null;
		String cepImovelEntrega = null;
		String municipioEntrega = null;
		String idMunicipioEntrega = null;
		String siglaUnidadeFederacaoEntrega = null;
		String indicadorEnderecoEntregaConta = null;
		String idImovelVinculadoEnderecoEntrega = null;
		String codigoRotaImovelVinculadoEnderecoEntrega = null;
		String idFaturamentoGrupoImovelVinculadoEnderecoEntrega = null;

		// Endereço do imóvel
		Object[] arrayDadosEnderecoImovel = this.pesquisarEnderecoFormatadoLista(idImovel);
		enderecoEntrega = (String) arrayDadosEnderecoImovel[0];
		idLogradouroEntrega = (Integer) arrayDadosEnderecoImovel[1];
		numeroImovelEntrega = (String) arrayDadosEnderecoImovel[2];
		municipioEntrega = (String) arrayDadosEnderecoImovel[5];
		idMunicipioEntrega = (String) arrayDadosEnderecoImovel[8];
		siglaUnidadeFederacaoEntrega = (String) arrayDadosEnderecoImovel[7];
		indicadorEnderecoEntregaConta = ConstantesSistema.INDICADOR_ENDERECO_ENTREGA_CONTA_IMOVEL;

		if(arrayDadosEnderecoImovel[6] != null){

			bairroImovelEntrega = (String) arrayDadosEnderecoImovel[6];
		}

		if(arrayDadosEnderecoImovel[3] != null){

			cepImovelEntrega = (String) arrayDadosEnderecoImovel[3];
		}

		// Caso o endereço de entrega não seja o do imóvel, verifica se tem endereço
		// alternativo
		if(idImovelContaEnvio != null && !idImovelContaEnvio.equals(ImovelContaEnvio.ENVIAR_IMOVEL)
						&& !idImovelContaEnvio.equals(ImovelContaEnvio.PAGAVEL_PARA_IMOVEL_E_NAO_PAGAVEL_PARA_RESPOSAVEL)
						&& !idImovelContaEnvio.equals(ImovelContaEnvio.PAGAVEL_PARA_IMOVEL_E_PAGAVEL_PARA_RESPONSAVEL)){

			Collection<ClienteImovel> colecaoClienteImovel = null;
			FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, idImovel));
			filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_ENDERECOS);

			if(idImovelContaEnvio.intValue() == ImovelContaEnvio.ENVIAR_CLIENTE_RESPONSAVEL
							|| idImovelContaEnvio.intValue() == ImovelContaEnvio.NAO_PAGAVEL_IMOVEL_PAGAVEL_RESPONSAVEL){

				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID,
								ClienteRelacaoTipo.RESPONSAVEL));

			}else if(idImovelContaEnvio.intValue() == ImovelContaEnvio.ENVIAR_PARA_CLIENTE_PROPRIETARIO){

				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID,
								ClienteRelacaoTipo.PROPRIETARIO));
			}else{

				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO_ID,
								ClienteRelacaoTipo.USUARIO));
			}

			colecaoClienteImovel = getControladorUtil().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

			if(!Util.isVazioOrNulo(colecaoClienteImovel)){

				ClienteImovel clienteImovel = (ClienteImovel) Util.retonarObjetoDeColecao(colecaoClienteImovel);

				Collection<ClienteEndereco> clienteEnderecos = clienteImovel.getCliente().getClienteEnderecos();

				for(ClienteEndereco clienteEndereco : clienteEnderecos){

					if(clienteEndereco.getIndicadorEnderecoCorrespondencia().intValue() == ClienteEndereco.INDICADOR_ENDERECO_CORRESPONDENCIA){

						Imovel imovelEnderecoCorrespondencia = null;

						if(clienteEndereco.getImovel() != null){

							FiltroClienteEndereco filtroClienteEndereco = new FiltroClienteEndereco();
							filtroClienteEndereco
											.adicionarParametro(new ParametroSimples(FiltroClienteEndereco.ID, clienteEndereco.getId()));
							filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteEndereco.IMOVEL);
							filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteEndereco.IMOVEL_ROTA);

							Collection<ClienteEndereco> colecaoClienteEndereco = getControladorUtil().pesquisar(filtroClienteEndereco,
											ClienteEndereco.class.getName());

							ClienteEndereco clienteEnderecoCorrespondencia = colecaoClienteEndereco.iterator().next();
							imovelEnderecoCorrespondencia = clienteEnderecoCorrespondencia.getImovel();
						}

						// [UC0085]Obter Endereco
						Object[] arrayDadosEnderecoCliente = this.pesquisarEnderecoClienteAbreviadoLista(clienteEndereco.getCliente()
										.getId(), false);

						enderecoEntrega = (String) arrayDadosEnderecoCliente[0];
						idLogradouroEntrega = (Integer) arrayDadosEnderecoCliente[1];
						numeroImovelEntrega = (String) arrayDadosEnderecoCliente[2];
						bairroImovelEntrega = (String) arrayDadosEnderecoCliente[3];
						cepImovelEntrega = (String) arrayDadosEnderecoCliente[4];
						municipioEntrega = (String) arrayDadosEnderecoCliente[5];
						idMunicipioEntrega = (String) arrayDadosEnderecoCliente[7];
						siglaUnidadeFederacaoEntrega = (String) arrayDadosEnderecoCliente[6];

						if(imovelEnderecoCorrespondencia != null){

							idImovelVinculadoEnderecoEntrega = imovelEnderecoCorrespondencia.getId().toString();
							codigoRotaImovelVinculadoEnderecoEntrega = imovelEnderecoCorrespondencia.getRota().getCodigo().toString();
							idFaturamentoGrupoImovelVinculadoEnderecoEntrega = imovelEnderecoCorrespondencia.getRota()
											.getFaturamentoGrupo().getId().toString();
						}

						indicadorEnderecoEntregaConta = ConstantesSistema.INDICADOR_ENDERECO_ENTREGA_CONTA_CLIENTE;
						break;
					}
				}
			}
		}

		retorno[0] = enderecoEntrega;
		retorno[1] = idLogradouroEntrega;
		retorno[2] = numeroImovelEntrega;
		retorno[3] = bairroImovelEntrega;
		retorno[4] = cepImovelEntrega;
		retorno[5] = municipioEntrega;
		retorno[6] = siglaUnidadeFederacaoEntrega;
		retorno[7] = indicadorEnderecoEntregaConta;
		retorno[8] = idMunicipioEntrega;
		retorno[9] = idImovelVinculadoEnderecoEntrega;
		retorno[10] = codigoRotaImovelVinculadoEnderecoEntrega;
		retorno[11] = idFaturamentoGrupoImovelVinculadoEnderecoEntrega;

		return retorno;

}
}
