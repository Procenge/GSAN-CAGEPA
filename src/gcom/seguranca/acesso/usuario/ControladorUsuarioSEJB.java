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
 * Eduardo Henrique
 */

package gcom.seguranca.acesso.usuario;

import gcom.cadastro.ControladorCadastroLocal;
import gcom.cadastro.ControladorCadastroLocalHome;
import gcom.cadastro.EnvioEmail;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.gui.ActionServletException;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Grupo;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.*;
import gcom.util.email.ErroEmailException;
import gcom.util.email.ServicosEmail;
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
public class ControladorUsuarioSEJB
				implements SessionBean {

	private static final long serialVersionUID = 1L;

	private IRepositorioUtil repositorioUtil;

	private IRepositorioUsuario repositorioUsuario;

	SessionContext sessionContext;

	/**
	 * < <Descrição do método>>
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

		repositorioUtil = RepositorioUtilHBM.getInstancia();
		repositorioUsuario = RepositorioUsuarioHBM.getInstancia();
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

	private ControladorCadastroLocal getControladorCadastro(){

		ControladorCadastroLocalHome localHome = null;
		ControladorCadastroLocal local = null;

		ServiceLocator locator = null;
		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorCadastroLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CADASTRO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
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
	 * Inseri um usuario com seus grupos
	 * [UC0230]Inserir Usuario
	 * 
	 * @author Thiago Toscano
	 * @date 19/05/2006
	 * @param usuario
	 * @param idGrupo
	 *            grupos que o usuario faz parte
	 * @throws ControladorException
	 */
	public void inserirUsuario(Usuario usuario, Integer[] idGrupos, Collection<UsuarioAcesso> colecaoUsuarioAcesso)
					throws ControladorException{

		/*
		 * [UC0107] Registrar Transação
		 */

		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_USUARIO_INSERIR, new UsuarioAcaoUsuarioHelper(
						Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_USUARIO_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		// recupera o sistema parametro
		SistemaParametro sistemaParametro = null;
		sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
		usuario.setNumeroAcessos(new Integer(0));
		usuario.setUltimoAcesso(null);
		String senhaGerada = null;
		/*
		 * if (usuario.getDescricaoEmail() != null
		 * && !usuario.getDescricaoEmail().equals("")) {
		 * senhaGerada = Util.geradorSenha(6);
		 * } else {
		 */
		senhaGerada = "gcom";
		// }
		String senhaCriptografada = null;
		try{
			senhaCriptografada = Criptografia.encriptarSenha(senhaGerada);
		}catch(ErroCriptografiaException e1){
			throw new ControladorException("erro.criptografia.senha");
		}
		usuario.setSenha(senhaCriptografada);
		usuario.setBloqueioAcesso(new Short((short) 0));
		usuario.setDataCadastroAcesso(new Date());
		usuario.setUltimaAlteracao(new Date());
		UsuarioSituacao usuarioSituacao = new UsuarioSituacao();
		usuarioSituacao.setId(UsuarioSituacao.PENDENTE_SENHA);
		usuario.setUsuarioSituacao(usuarioSituacao);
		usuario.setDataNascimento(null);
		usuario.setLembreteSenha(null);
		usuario.setCpf(null);
		usuario.setIndicadorExibeMensagem(new Short((short) TarefaRelatorio.INDICADOR_EXIBE_MENSAGEM));
		usuario.setIndicadorTipoRelatorioPadrao(new Short((short) TarefaRelatorio.TIPO_PDF));
		Date dataCadastramentoFinal = usuario.getDataCadastroFim();
		Date dataAtual = new Date();
		int numeroDiasExpiracaoAcesso = sistemaParametro.getNumeroDiasExpiracaoAcesso().intValue();

		int numeroDiasMSGExpiracao = sistemaParametro.getNumeroDiasMensagemExpiracao().intValue();

		Date dataAtualMaisDiasSistemasParametros = Util.adicionarNumeroDiasDeUmaData(dataAtual, numeroDiasExpiracaoAcesso);

		Date dataAtualMenosDiasMSGExpiracao = Util.subtrairNumeroDiasDeUmaData(dataAtualMaisDiasSistemasParametros, numeroDiasMSGExpiracao);
		// verifica a data de cadastramento final se é diferente de nulo
		if(dataCadastramentoFinal != null && !dataCadastramentoFinal.equals("")){
			// caso a data atual + dias sistemas parametros seja maior que a
			// data de cadastramento final do usuário
			if(dataAtualMaisDiasSistemasParametros.after(dataCadastramentoFinal)){
				// seta o valor da data de cadastramento final do usuario no
				// usuario data expiração acesso
				usuario.setDataExpiracaoAcesso(dataCadastramentoFinal);
			}else{
				// seta o valor da a data atual + dias sistemas parametros no
				// usuario data expiração acesso
				usuario.setDataExpiracaoAcesso(dataAtualMaisDiasSistemasParametros);
			}

			// caso a data atual - dias mensagem expiracao sistemas parametros
			// seja maior que a
			// data de cadastramento final do usuário
			if(dataAtualMenosDiasMSGExpiracao.after(dataCadastramentoFinal)){
				// seta o valor da data de cadastramento final do usuario no
				// usuario data expiração acesso
				usuario.setDataPrazoMensagemExpiracao(dataCadastramentoFinal);
			}else{
				// seta o valor da a data atual - dias mensagem expiracao
				// sistemas parametros no
				// usuario data expiração acesso
				usuario.setDataPrazoMensagemExpiracao(dataAtualMenosDiasMSGExpiracao);
			}
		}else{
			// seta o valor da a data atual + dias sistemas parametros no
			// usuario data expiração acesso
			usuario.setDataExpiracaoAcesso(dataAtualMaisDiasSistemasParametros);
			// seta o valor da a data atual - dias mensagem expiracao sistemas
			// parametros no
			// usuario data prazo mensagem expiração acesso
			usuario.setDataPrazoMensagemExpiracao(dataAtualMenosDiasMSGExpiracao);
		}
		// registrar transação
		usuario.setOperacaoEfetuada(operacaoEfetuada);
		usuario.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorOperacao.registrarOperacao(usuario);
		usuario.setUltimaAlteracao(new Date());
		this.getControladorUtil().inserir(usuario);

		/**
		 * Para todos os grupos selecionados cria o relacionamento
		 */
		if(idGrupos != null){
			for(int i = 0; i < idGrupos.length; i++){
				Integer idGrupo = idGrupos[i];

				// cria o grupo corrente
				Grupo grupo = new Grupo();
				grupo.setId(idGrupo);

				// cria a pk
				UsuarioGrupoPK pk = new UsuarioGrupoPK();
				pk.setGrupoId(grupo.getId());
				pk.setUsuarioId(usuario.getId());

				// cria o relacionamenteo do usuario com o grupo
				UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
				usuarioGrupo.setGrupo(grupo);
				usuarioGrupo.setUsuario(usuario);
				usuarioGrupo.setComp_id(pk);
				usuarioGrupo.setUltimaAlteracao(new Date());

				// registrar transação
				usuarioGrupo.setOperacaoEfetuada(operacaoEfetuada);
				usuarioGrupo.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

				registradorOperacao.registrarOperacao(usuarioGrupo);
				try{
					// salvando o usuarioGrupo
					repositorioUtil.inserir(usuarioGrupo);
				}catch(ErroRepositorioException ex){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", ex);
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoUsuarioAcesso)){

			for(UsuarioAcesso usuarioAcesso : colecaoUsuarioAcesso){

				if(usuarioAcesso.getIndicadorSelecionado() == 1){
					usuarioAcesso.setUsuario(usuario);
					usuarioAcesso.setIndicadorUso(ConstantesSistema.SIM);
					usuarioAcesso.setUltimaAlteracao(new Date());

					try{
						// salvando o UsuarioAcesso
						repositorioUtil.inserir(usuarioAcesso);
					}catch(ErroRepositorioException ex){
						sessionContext.setRollbackOnly();
						throw new ControladorException("erro.sistema", ex);
					}
				}
			}
		}

		// Envia e-mail para o usuario informando usuario e senha
		if(usuario.getDescricaoEmail() != null && !usuario.getDescricaoEmail().equals("")){
			String mensagem = "Login:" + usuario.getLogin() + " \n" + "Senha:" + senhaGerada;
			EnvioEmail envioEmail = getControladorCadastro().pesquisarEnvioEmail(EnvioEmail.INSERIR_USUARIO);

			try{
				ServicosEmail.enviarMensagem(envioEmail.getEmailRemetente(), usuario.getDescricaoEmail(), envioEmail.getTituloMensagem(),
								mensagem);
			}catch(ErroEmailException e){
				throw new ControladorException("erro.envio.mensagem");
			}
		}

	}

	/**
	 * Atualiza um usuario com seus grupos
	 * [UC0231]Atualizar Usuario
	 * 
	 * @author Sávio Luiz
	 * @date 07/07/2006
	 * @param usuario
	 * @param idGrupo
	 *            grupos que o usuario faz parte
	 * @throws ControladorException
	 */
	public void atualizarUsuario(Usuario usuario, Integer[] idGrupos, String processo, Usuario usuarioLogado,
					Collection<UsuarioAcesso> colecaoUsuarioAcesso, String indicadorHorarioAcessoRestrito) throws ControladorException{

		/*
		 * [UC0107] Registrar Transação
		 */

		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_USUARIO_ATUALIZAR,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_USUARIO_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		operacaoEfetuada.setIdObjetoPrincipal(usuario.getId());

		// removendo os usuarios grupos
		FiltroUsuario filtroUsuario = new FiltroUsuario();
		filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, usuario.getId()));
		Collection coll = this.getControladorUtil().pesquisar(filtroUsuario, Usuario.class.getName());
		Usuario usuarioCadastrado = (Usuario) coll.iterator().next();

		if(usuarioCadastrado == null){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.registro_remocao_nao_existente");
		}

		usuario.setUltimaAlteracao(new Date());

		if(usuarioCadastrado.getUltimaAlteracao() != null && usuario.getUltimaAlteracao() != null
						&& usuarioCadastrado.getUltimaAlteracao().after(usuario.getUltimaAlteracao())){
			throw new ControladorException("atencao.atualizacao.timestamp");
		}
		// recupera o sistema parametro
		SistemaParametro sistemaParametro = null;
		sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

		usuario.setDataCadastroAcesso(new Date());
		usuario.setUltimaAlteracao(new Date());
		Date dataCadastramentoFinal = usuario.getDataCadastroFim();
		Date dataAtual = new Date();
		int numeroDiasExpiracaoAcesso = sistemaParametro.getNumeroDiasExpiracaoAcesso().intValue();

		int numeroDiasMSGExpiracao = sistemaParametro.getNumeroDiasMensagemExpiracao().intValue();

		Date dataAtualMaisDiasSistemasParametros = Util.adicionarNumeroDiasDeUmaData(dataAtual, numeroDiasExpiracaoAcesso);

		Date dataAtualMenosDiasMSGExpiracao = Util.subtrairNumeroDiasDeUmaData(dataAtualMaisDiasSistemasParametros, numeroDiasMSGExpiracao);
		// verifica a data de cadastramento final se é diferente de nulo
		if(dataCadastramentoFinal != null && !dataCadastramentoFinal.equals("")){
			// caso a data atual + dias sistemas parametros seja maior que a
			// data de cadastramento final do usuário
			if(dataAtualMaisDiasSistemasParametros.after(dataCadastramentoFinal)){
				// seta o valor da data de cadastramento final do usuario no
				// usuario data expiração acesso
				usuario.setDataExpiracaoAcesso(dataCadastramentoFinal);
			}else{
				// seta o valor da a data atual + dias sistemas parametros no
				// usuario data expiração acesso
				usuario.setDataExpiracaoAcesso(dataAtualMaisDiasSistemasParametros);
			}

			// caso a data atual - dias mensagem expiracao sistemas parametros
			// seja maior que a
			// data de cadastramento final do usuário
			if(dataAtualMenosDiasMSGExpiracao.after(dataCadastramentoFinal)){
				// seta o valor da data de cadastramento final do usuario no
				// usuario data expiração acesso
				usuario.setDataPrazoMensagemExpiracao(dataCadastramentoFinal);
			}else{
				// seta o valor da a data atual - dias mensagem expiracao
				// sistemas parametros no
				// usuario data expiração acesso
				usuario.setDataPrazoMensagemExpiracao(dataAtualMenosDiasMSGExpiracao);
			}
		}else{
			// seta o valor da a data atual + dias sistemas parametros no
			// usuario data expiração acesso
			usuario.setDataExpiracaoAcesso(dataAtualMaisDiasSistemasParametros);
			// seta o valor da a data atual - dias mensagem expiracao sistemas
			// parametros no
			// usuario data prazo mensagem expiração acesso
			usuario.setDataPrazoMensagemExpiracao(dataAtualMenosDiasMSGExpiracao);
		}
		// registrar transação
		usuario.setOperacaoEfetuada(operacaoEfetuada);
		usuario.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorOperacao.registrarOperacao(usuario);

		this.getControladorUtil().atualizar(usuario);

		/*
		 * [UC0107] Registrar Transação
		 */
		if(processo != null){
			if(processo.equalsIgnoreCase("2")){ // Caso o usuario tenha alterado os dados da segunda
				// aba

				registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_USUARIO_REMOVER, new UsuarioAcaoUsuarioHelper(
								usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

				operacao = new Operacao();
				operacao.setId(Operacao.OPERACAO_USUARIO_ATUALIZAR);

				operacaoEfetuada = new OperacaoEfetuada();
				operacaoEfetuada.setOperacao(operacao);

				// removendo os usuarios grupos
				FiltroUsuarioGrupo filtroUsuarioGrupo = new FiltroUsuarioGrupo();
				filtroUsuarioGrupo.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupo.USUARIO_ID, usuario.getId()));
				coll = this.getControladorUtil().pesquisar(filtroUsuarioGrupo, UsuarioGrupo.class.getName());

				/*
				 * Alteracao realizada por Rômulo Aurélio
				 * Problema na hora de remover usuarios grupos que existam na tabela
				 * usuarioGrupoRestricao
				 * Solicitado por Fatima
				 * Data: 14/05/2006
				 */
				FiltroUsuarioGrupoRestricao filtroUsuarioGrupoRestricao = new FiltroUsuarioGrupoRestricao();

				filtroUsuarioGrupoRestricao
								.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.USUARIO_ID, usuario.getId()));
				filtroUsuarioGrupoRestricao.adicionarCaminhoParaCarregamentoEntidade("usuarioGrupo.grupo");
				filtroUsuarioGrupoRestricao.adicionarCaminhoParaCarregamentoEntidade("usuarioGrupo.usuario");
				filtroUsuarioGrupoRestricao.adicionarCaminhoParaCarregamentoEntidade("grupoFuncionalidadeOperacao");
				// filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples
				// (FiltroUsuarioGrupoRestricao.GRUPO_ID,usuarioGrupo.getGrupo().getId()));

				Collection colecaoGrupoRestricao = getControladorUtil().pesquisar(filtroUsuarioGrupoRestricao,
								UsuarioGrupoRestricao.class.getName());

				if(colecaoGrupoRestricao != null && !colecaoGrupoRestricao.isEmpty()){

					Iterator colecaoGrupoRestricaoIterator = colecaoGrupoRestricao.iterator();

					while(colecaoGrupoRestricaoIterator.hasNext()){

						UsuarioGrupoRestricao usuarioGrupoRestricao = (UsuarioGrupoRestricao) colecaoGrupoRestricaoIterator.next();

						getControladorUtil().remover(usuarioGrupoRestricao);

					}

				}

				if(coll != null && !coll.isEmpty()){
					Iterator it = coll.iterator();
					while(it.hasNext()){

						UsuarioGrupo usuarioGrupo = (UsuarioGrupo) it.next();

						// registrar transação
						usuarioGrupo.setOperacaoEfetuada(operacaoEfetuada);
						usuarioGrupo.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

						registradorOperacao.registrarOperacao(usuarioGrupo);

						this.getControladorUtil().remover(usuarioGrupo);
					}
				}

				/*
				 * [UC0107] Registrar Transação
				 */

				registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_USUARIO_REMOVER, new UsuarioAcaoUsuarioHelper(
								usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

				operacao = new Operacao();
				operacao.setId(Operacao.OPERACAO_USUARIO_INSERIR);

				operacaoEfetuada = new OperacaoEfetuada();
				operacaoEfetuada.setOperacao(operacao);

				/**
				 * Para todos os grupos selecionados cria o relacionamento
				 */

				Collection colecaoGrupos = new ArrayList();
				if(idGrupos == null || idGrupos.length <= 0){
					throw new ActionServletException("atencao.usuario_sem_grupo", null, usuarioCadastrado.getLogin());
				}else{
					for(int i = 0; i < idGrupos.length; i++){
						Integer idGrupo = idGrupos[i];

						// cria o grupo corrente
						Grupo grupo = new Grupo();
						grupo.setId(idGrupo);

						colecaoGrupos.add(grupo.getId());

						// cria a pk
						UsuarioGrupoPK pk = new UsuarioGrupoPK();
						pk.setGrupoId(grupo.getId());
						pk.setUsuarioId(usuario.getId());

						// cria o relacionamenteo do usuario com o grupo
						UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
						usuarioGrupo.setGrupo(grupo);
						usuarioGrupo.setUsuario(usuario);
						usuarioGrupo.setComp_id(pk);
						usuarioGrupo.setUltimaAlteracao(new Date());
						// registrar transação
						usuarioGrupo.setOperacaoEfetuada(operacaoEfetuada);
						usuarioGrupo.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

						registradorOperacao.registrarOperacao(usuarioGrupo);

						// salvando o usuarioGrupo
						this.getControladorUtil().inserir(usuarioGrupo);
					}
				}

				if(colecaoGrupoRestricao != null && !colecaoGrupoRestricao.isEmpty()){

					Iterator colecaoGrupoRestricaoIterator = colecaoGrupoRestricao.iterator();

					while(colecaoGrupoRestricaoIterator.hasNext()){
						UsuarioGrupoRestricao usuarioGrupoRestricao = (UsuarioGrupoRestricao) colecaoGrupoRestricaoIterator.next();

						if(colecaoGrupos.contains(usuarioGrupoRestricao.getUsuarioGrupo().getGrupo().getId())){
							this.getControladorUtil().inserir(usuarioGrupoRestricao);
						}

					}
				}

			}
		}// processo != null

		// Alterado por Vivianne Sousa 13/02/2007
		// solicitado por Leonardo Vieira
		if(usuario.getUsuarioTipo().getIndicadorFuncionario() == UsuarioTipo.INDICADOR_FUNCIONARIO){
			Funcionario funcionario = usuario.getFuncionario();

			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, funcionario.getId()));
			filtroFuncionario.adicionarCaminhoParaCarregamentoEntidade(FiltroFuncionario.UNIDADE_ORGANIZACIONAL);
			Collection colecaoFuncionario = this.getControladorUtil().pesquisar(filtroFuncionario, Funcionario.class.getName());
			if(colecaoFuncionario != null && !colecaoFuncionario.isEmpty()){
				Funcionario f = (Funcionario) colecaoFuncionario.iterator().next();
				if(f.getUnidadeOrganizacional() != null
								&& !f.getUnidadeOrganizacional().getId().equals(funcionario.getUnidadeOrganizacional().getId())){
					funcionario.setOperacaoEfetuada(operacaoEfetuada);
					funcionario.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					funcionario.setUltimaAlteracao(new Date());
					this.getControladorUtil().atualizar(funcionario);
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoUsuarioAcesso)){

			Collection colecaoUsuarioAcessoRemover = this.pesquisarUsuarioAcesso(usuario.getId());
			this.getControladorUtil().removerColecaoObjetos(colecaoUsuarioAcessoRemover);
			
			for(UsuarioAcesso usuarioAcesso : colecaoUsuarioAcesso){

				if(usuarioAcesso.getIndicadorSelecionado() == 1){
					usuarioAcesso.setUsuario(usuario);
					usuarioAcesso.setIndicadorUso(ConstantesSistema.SIM);
					usuarioAcesso.setUltimaAlteracao(new Date());

					try{
						// salvando o UsuarioAcesso
						repositorioUtil.inserir(usuarioAcesso);
					}catch(ErroRepositorioException ex){
						sessionContext.setRollbackOnly();
						throw new ControladorException("erro.sistema", ex);
					}
				}
			}
		}else if(indicadorHorarioAcessoRestrito != null && indicadorHorarioAcessoRestrito.equals("2")){
			Collection colecaoUsuarioAcessoRemover = this.pesquisarUsuarioAcesso(usuario.getId());
			this.getControladorUtil().removerColecaoObjetos(colecaoUsuarioAcessoRemover);
		}
	}

	public Collection<UsuarioAcesso> atualizarHorarioAcessoRestrito(Map<String, String[]> mapParametros) throws ControladorException{

		Collection<UsuarioAcesso> retorno = new ArrayList<UsuarioAcesso>();

		for(int i = 1; i <= 7; i++){

			String dia = "diaSemana";
			String[] valor = mapParametros.get(dia + i);

			if(valor != null && valor[0] != null){

				String inicio = "horaInicio";
				String fim = "horaFim";

				String[] horaInicio = mapParametros.get(inicio + i);
				String[] horaFim = mapParametros.get(fim + i);

				this.validaHorarios(horaInicio, horaFim);

				Date horaInicial = Util.converterStringParaHoraMinuto(horaInicio[0]);
				Date horaFinal = Util.converterStringParaHoraMinuto(horaFim[0]);

				UsuarioAcesso usuarioAcessoDia = new UsuarioAcesso(i, Util.obterDiaSemanaDescricao(i), horaInicial, horaFinal, 1);

				retorno.add(usuarioAcessoDia);
			}else{

				Date data = Util.converteStringParaDate("01/01/1900", true);

				Date horaInicial = Util.formatarDataInicial(data);
				Date horaFinal = Util.formatarDataFinal(data);

				UsuarioAcesso usuarioAcessoDia = new UsuarioAcesso(i, Util.obterDiaSemanaDescricao(i), horaInicial, horaFinal, 2);

				retorno.add(usuarioAcessoDia);
			}
		}

		return retorno;
	}

	private void validaHorarios(String[] horaInicio, String[] horaFim){

		if(horaInicio == null || horaInicio.length == 0 || !Util.validaHoraMinuto(horaInicio[0])){
			throw new ActionServletException("atencao.usuario.hora_inicio");
		}

		if(horaFim == null || horaFim.length == 0 || !Util.validaHoraMinuto(horaFim[0])){
			throw new ActionServletException("atencao.usuario.hora_fim");
		}

		if(!Util.compararHoraMinuto(horaInicio[0], horaFim[0], "<")){
			throw new ActionServletException("atencao.usuario.hora_inicio_fim");
		}
	}

	/**
	 * Remove usuario(s)
	 * [UC0231] Manter Usuario
	 * 
	 * @author Sávio Luiz
	 * @date 07/07/2006
	 * @param idsUsuario
	 * @param usuario
	 * @throws ControladorException
	 */
	public void removerUsuario(String[] idsUsuario, Usuario usuario) throws ControladorException{

		// removendo os usuarios grupos
		for(int i = 0; i < idsUsuario.length; i++){

			// ------------ REGISTRAR TRANSAÇÃO ----------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_USUARIO_REMOVER,
							new UsuarioAcaoUsuarioHelper(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_USUARIO_REMOVER);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			// Parte da verificação do filtro
			FiltroUsuario filtroUsuario = new FiltroUsuario();

			// filtroUsuario.setCampoOrderBy(FiltroUsuario.NOME_USUARIO);
			filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, idsUsuario[i]));
			filtroUsuario.adicionarCaminhoParaCarregamentoEntidade("unidadeOrganizacional");
			filtroUsuario.adicionarCaminhoParaCarregamentoEntidade("funcionario.unidadeOrganizacional");
			Collection colecaoUsuario = this.getControladorUtil().pesquisar(filtroUsuario, Usuario.class.getName());

			Usuario usuarioParaRemover = (Usuario) Util.retonarObjetoDeColecao(colecaoUsuario);
			// [FS0008] - Verificar permissão para atualização

			UnidadeOrganizacional unidadeEmpresa = usuarioParaRemover.getUnidadeOrganizacional();
			if(unidadeEmpresa == null){
				if(usuarioParaRemover.getFuncionario() != null && !usuarioParaRemover.getFuncionario().equals("")){
					unidadeEmpresa = usuarioParaRemover.getFuncionario().getUnidadeOrganizacional();
				}
			}

			// caso o usuário que esteja efetuando a inserção não
			// seja
			// do grupo de administradores
			FiltroUsuarioGrupo filtroUsuarioGrupo = new FiltroUsuarioGrupo();

			filtroUsuarioGrupo.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupo.USUARIO_ID, usuario.getId()));
			filtroUsuarioGrupo.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupo.GRUPO_ID, Grupo.ADMINISTRADOR));
			Collection colecaoUsuarioGrupo = this.getControladorUtil().pesquisar(filtroUsuarioGrupo, UsuarioGrupo.class.getName());
			if(colecaoUsuarioGrupo == null || colecaoUsuarioGrupo.isEmpty()){
				// se a unidade de lotacao do usuario que estiver
				// efetuando seja diferente da unidade de
				// lotação informada
				if(usuario.getUnidadeOrganizacional() != null && unidadeEmpresa != null
								&& usuario.getUnidadeOrganizacional().getId() != null
								&& !usuario.getUnidadeOrganizacional().getId().equals(unidadeEmpresa.getId())){
					// recupera a unidade do usuário
					FiltroUnidadeOrganizacional filtroUnidadeEmpresaUsuario = new FiltroUnidadeOrganizacional();
					filtroUnidadeEmpresaUsuario.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, usuario
									.getUnidadeOrganizacional().getId()));
					Collection colecaoUnidadeEmpresa = this.getControladorUtil().pesquisar(filtroUnidadeEmpresaUsuario,
									UnidadeOrganizacional.class.getName());
					UnidadeOrganizacional unidadeEmpresaUsuario = null;
					if(colecaoUnidadeEmpresa != null && !colecaoUnidadeEmpresa.isEmpty()){
						unidadeEmpresaUsuario = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeEmpresa);
					}
					// se o nivel da unidade de lotação do usuário
					// que
					// estiver efetuando a inserção seja maior ou
					// igual
					// ao nivel de unidade de lotação informada
					if(unidadeEmpresaUsuario != null){
						if(unidadeEmpresaUsuario.getUnidadeTipo().getNivel().intValue() >= unidadeEmpresa.getUnidadeTipo().getNivel()
										.intValue()){
							throw new ControladorException("atencao.usuario.sem.permissao.atualizacao", null, usuario.getLogin(),
											unidadeEmpresa.getDescricao());
						}
					}
					// ou a unidade superior da unidade de lotação
					// informada seja diferente da unidade de
					// lotação do usuário

					// enquanto o nivel superior da unidade de
					// lotação não esteja no mesmo nivel da unidade
					// de lotação do usuário
					boolean mesmoNivel = true;
					Integer idNivelUsuario = unidadeEmpresaUsuario.getUnidadeTipo().getNivel().intValue();
					UnidadeOrganizacional unidadeEmpresaSuperior = null;
					while(mesmoNivel){
						Integer idUnidadeEmpresaSuperior = null;
						if(unidadeEmpresaSuperior == null){
							idUnidadeEmpresaSuperior = unidadeEmpresa.getUnidadeSuperior().getId();
						}else{
							idUnidadeEmpresaSuperior = unidadeEmpresaSuperior.getUnidadeSuperior().getId();
						}
						// recupera a unidade do usuário
						FiltroUnidadeOrganizacional filtroUnidadeEmpresaSuperior = new FiltroUnidadeOrganizacional();
						filtroUnidadeEmpresaSuperior.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID,
										idUnidadeEmpresaSuperior));
						Collection colecaoUnidadeEmpresaSuperior = this.getControladorUtil().pesquisar(filtroUnidadeEmpresaSuperior,
										UnidadeOrganizacional.class.getName());
						if(colecaoUnidadeEmpresaSuperior != null && !colecaoUnidadeEmpresaSuperior.isEmpty()){
							unidadeEmpresaSuperior = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeEmpresaSuperior);
						}
						if(unidadeEmpresaSuperior != null){
							// caso seja o mesmo nivel
							if(unidadeEmpresaSuperior.getUnidadeTipo().getNivel().equals(idNivelUsuario)){
								mesmoNivel = false;
								// caso o id da unidade empresa
								// informado for diferente do id da
								// unidade empresa do usuário no
								// mesmo nivel
								if(!unidadeEmpresaSuperior.getId().equals(unidadeEmpresaUsuario.getId())){
									throw new ControladorException("atencao.usuario.sem.permissao.atualizacao", null, usuario.getLogin(),
													unidadeEmpresa.getDescricao());
								}

							}
						}
					}

				}
			}
			// remove os usuarios grupos restrinções
			FiltroUsuarioGrupoRestricao filtroUsuarioGrupoRestricao = new FiltroUsuarioGrupoRestricao();
			filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.USUARIO_ID, idsUsuario[i]));
			Collection colecaoUsuarioGrupoRestricao = this.getControladorUtil().pesquisar(filtroUsuarioGrupoRestricao,
							UsuarioGrupoRestricao.class.getName());

			if(colecaoUsuarioGrupoRestricao != null && !colecaoUsuarioGrupoRestricao.isEmpty()){
				Iterator it = colecaoUsuarioGrupoRestricao.iterator();
				while(it.hasNext()){
					UsuarioGrupoRestricao usuarioGrupoRestricao = (UsuarioGrupoRestricao) it.next();

					// registrar transação
					usuarioGrupoRestricao.setOperacaoEfetuada(operacaoEfetuada);
					usuarioGrupoRestricao.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

					registradorOperacao.registrarOperacao(usuarioGrupoRestricao);

					this.getControladorUtil().remover(usuarioGrupoRestricao);
				}
			}

			// remove os usuarioGrupos
			FiltroUsuarioPemissaoEspecial filtroUsuarioPemissaoEspecial = new FiltroUsuarioPemissaoEspecial();
			filtroUsuarioPemissaoEspecial.adicionarParametro(new ParametroSimples(FiltroUsuarioPemissaoEspecial.USUARIO_ID, idsUsuario[i]));
			Collection colecaoUsuarioPermissaoEspecial = this.getControladorUtil().pesquisar(filtroUsuarioPemissaoEspecial,
							UsuarioPermissaoEspecial.class.getName());

			if(colecaoUsuarioPermissaoEspecial != null && !colecaoUsuarioPermissaoEspecial.isEmpty()){
				Iterator it = colecaoUsuarioPermissaoEspecial.iterator();
				while(it.hasNext()){
					UsuarioPermissaoEspecial usuarioPermissaoEspecial = (UsuarioPermissaoEspecial) it.next();
					// registrar transação
					usuarioPermissaoEspecial.setOperacaoEfetuada(operacaoEfetuada);
					usuarioPermissaoEspecial.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

					registradorOperacao.registrarOperacao(usuarioPermissaoEspecial);

					this.getControladorUtil().remover(usuarioPermissaoEspecial);
				}
			}

			// remove os usuarios grupos
			FiltroUsuarioGrupo filtroUsuarioGrupoParaRemocao = new FiltroUsuarioGrupo();
			filtroUsuarioGrupoParaRemocao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupo.USUARIO_ID, idsUsuario[i]));
			Collection colecaoUsuarioGrupoParaRemocao = this.getControladorUtil().pesquisar(filtroUsuarioGrupoParaRemocao,
							UsuarioGrupo.class.getName());

			if(colecaoUsuarioGrupoParaRemocao != null && !colecaoUsuarioGrupoParaRemocao.isEmpty()){
				Iterator it = colecaoUsuarioGrupoParaRemocao.iterator();
				while(it.hasNext()){
					UsuarioGrupo usuarioGrupo = (UsuarioGrupo) it.next();

					// registrar transação
					usuarioGrupo.setOperacaoEfetuada(operacaoEfetuada);
					usuarioGrupo.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

					registradorOperacao.registrarOperacao(usuarioGrupo);

					this.getControladorUtil().remover(usuarioGrupo);
				}
			}
			// registrar
			// transaçãofiltroUsuarioGrupoRestricao.adicionarCaminhoParaCarregamentoEntidade("grupo");
			usuarioParaRemover.setOperacaoEfetuada(operacaoEfetuada);
			usuarioParaRemover.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			registradorOperacao.registrarOperacao(usuarioParaRemover);
			this.getControladorUtil().remover(usuarioParaRemover);
		}
	}

	/**
	 * Ativa/Inativa usuario(s)
	 * [UC0231] Manter Usuario
	 * 
	 * @param idsUsuario
	 * @param usuario
	 */
	public void ativarInativarUsuario(String[] idsUsuario, Usuario usuario, boolean ativar) throws ControladorException{

		UsuarioSituacao usuarioSituacao = new UsuarioSituacao();
		if(ativar){
			usuarioSituacao.setId(UsuarioSituacao.ATIVO);
		}else{
			usuarioSituacao.setId(UsuarioSituacao.INATIVO);
		}

		// removendo os usuarios grupos
		for(int i = 0; i < idsUsuario.length; i++){

			// ------------ REGISTRAR TRANSAÇÃO ----------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_USUARIO_ATUALIZAR,
							new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_USUARIO_ATUALIZAR);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			// Parte da verificação do filtro
			FiltroUsuario filtroUsuario = new FiltroUsuario();

			// filtroUsuario.setCampoOrderBy(FiltroUsuario.NOME_USUARIO);
			filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, idsUsuario[i]));
			filtroUsuario.adicionarCaminhoParaCarregamentoEntidade("unidadeOrganizacional");
			filtroUsuario.adicionarCaminhoParaCarregamentoEntidade("funcionario.unidadeOrganizacional");
			Collection colecaoUsuario = this.getControladorUtil().pesquisar(filtroUsuario, Usuario.class.getName());

			Usuario usuarioParaInativar = (Usuario) Util.retonarObjetoDeColecao(colecaoUsuario);

			// atualiza situação
			usuarioParaInativar.setUsuarioSituacao(usuarioSituacao);

			usuarioParaInativar.setUltimaAlteracao(new Date());
			usuarioParaInativar.setOperacaoEfetuada(operacaoEfetuada);
			usuarioParaInativar.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			registradorOperacao.registrarOperacao(usuarioParaInativar);
			this.getControladorUtil().atualizar(usuarioParaInativar);
		}
	}

	/**
	 * Método que atualiza o controle de acesso do usuário
	 * [UC0231] - Manter Usuário
	 * 
	 * @author Sávio Luiz
	 * @date 14/07/2006
	 * @param String
	 *            []
	 * @param grupoFuncionalidadeOperacao
	 */
	public void atualizarControleAcessoUsuario(String[] permissoesEspeciais,
					Map<Integer, Map<Integer, Collection<Operacao>>> funcionalidadesMap, Usuario usuarioAtualizar, Integer[] idsGrupos,
					String permissoesCheckBoxVazias) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		/*
		 * RegistradorOperacao registradorOperacao = new RegistradorOperacao(
		 * Operacao.OPERACAO_USUARIO_CONTROLAR_ACESSO, new
		 * UsuarioAcaoUsuarioHelper(Usuario.USUARIO_TESTE,
		 * UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO)); Operacao
		 * operacaoTransacao = new Operacao();
		 * operacaoTransacao.setId(Operacao.OPERACAO_USUARIO_REMOVER);
		 * OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		 * operacaoEfetuada.setOperacao(operacaoTransacao);
		 */

		if(funcionalidadesMap != null && !funcionalidadesMap.isEmpty()){
			/*
			 * Pesquisa todos os usuários grupos restrinções, e remove todos
			 * para ser inseridos os novos acesso(s) informados pelo usuário
			 */
			FiltroUsuarioGrupoRestricao filtroUsuarioGrupoRestricao = new FiltroUsuarioGrupoRestricao();
			filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.USUARIO_ID, usuarioAtualizar
							.getId()));
			Collection colecaoUsuarioGrupoRestrincaoCadastradas = getControladorUtil().pesquisar(filtroUsuarioGrupoRestricao,
							UsuarioGrupoRestricao.class.getName());
			if(colecaoUsuarioGrupoRestrincaoCadastradas != null && !colecaoUsuarioGrupoRestrincaoCadastradas.isEmpty()){
				Iterator iteratorUsuarioGrupoRestrincao = colecaoUsuarioGrupoRestrincaoCadastradas.iterator();
				while(iteratorUsuarioGrupoRestrincao.hasNext()){
					UsuarioGrupoRestricao usuarioGrupoRestricao = (UsuarioGrupoRestricao) iteratorUsuarioGrupoRestrincao.next();
					/*
					 * // registrar transação
					 * usuarioGrupoRestricao.setOperacaoEfetuada(operacaoEfetuada);
					 * usuarioGrupoRestricao.adicionarUsuario(
					 * Usuario.USUARIO_TESTE,
					 * UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					 * registradorOperacao
					 * .registrarOperacao(usuarioGrupoRestricao);
					 */

					getControladorUtil().remover(usuarioGrupoRestricao);
				}
			}

			/*
			 * Caso o usuário tenha informado algum acesso para o grupo que está
			 * sendo atualizado, inseri na tabela usuario_grupo_restrinção
			 */

			Collection idsfuncionalidades = funcionalidadesMap.keySet();
			if(idsfuncionalidades != null && !idsfuncionalidades.isEmpty()){
				Iterator iteratorFuncionalidades = idsfuncionalidades.iterator();
				// verifica se existe a funcionalidade escolhida na coleção de
				// funcionalidade
				while(iteratorFuncionalidades.hasNext()){
					Integer idfuncionalidade = (Integer) iteratorFuncionalidades.next();
					Map<Integer, Collection<Operacao>> operacoesMap = funcionalidadesMap.get(idfuncionalidade);
					// para cada funcionalidade verifica se existe operações
					// desmarcadas(com indicador igual a 2).
					Collection colecaoOperacao = operacoesMap.get(2);
					if(colecaoOperacao != null && !colecaoOperacao.isEmpty()){
						Iterator iteratorOperacao = colecaoOperacao.iterator();
						while(iteratorOperacao.hasNext()){
							Operacao operacao = (Operacao) iteratorOperacao.next();

							Collection idsGruposNaBase = null;
							try{
								// pesquisando os ids que vão ser inseridos na
								// tabela de
								// usuarioGrupoRestrincao
								idsGruposNaBase = repositorioUsuario.pesquisarIdsGruposPelaFuncionalidadeGruposOperacao(idsGrupos,
												idfuncionalidade, operacao.getId());
							}catch(ErroRepositorioException ex){
								sessionContext.setRollbackOnly();
								throw new ControladorException("erro.sistema", ex);
							}
							// recupera os ids dos grupos existente no grupo
							// funcionalidade operacao
							Iterator iteratoridsGrupos = idsGruposNaBase.iterator();
							while(iteratoridsGrupos.hasNext()){
								Integer idGrupo = (Integer) iteratoridsGrupos.next();
								UsuarioGrupoRestricao usuarioGrupoRestricao = new UsuarioGrupoRestricao();
								UsuarioGrupoRestricaoPK usuarioGrupoRestricaoPK = new UsuarioGrupoRestricaoPK();

								usuarioGrupoRestricaoPK.setFuncionalidadeId(idfuncionalidade);
								usuarioGrupoRestricaoPK.setOperacaoId(operacao.getId());
								usuarioGrupoRestricaoPK.setGrupoId(idGrupo);
								usuarioGrupoRestricaoPK.setUsuarioId(usuarioAtualizar.getId());

								usuarioGrupoRestricao.setComp_id(usuarioGrupoRestricaoPK);
								usuarioGrupoRestricao.setUltimaAlteracao(new Date());
								/*
								 * // registrar transação usuarioGrupoRestricao
								 * .setOperacaoEfetuada(operacaoEfetuada);
								 * usuarioGrupoRestricao .adicionarUsuario(
								 * Usuario.USUARIO_TESTE,
								 * UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
								 * registradorOperacao
								 * .registrarOperacao(usuarioGrupoRestricao);
								 */
								getControladorUtil().inserir(usuarioGrupoRestricao);
							}
						}
					}

				}
			}
		}
		// caso tenha passado na funcionalidade de permissões especiais
		if(!(permissoesEspeciais == null && permissoesCheckBoxVazias == null)){

			/*
			 * Pesquisa todos os usuários com permissão especial , e remove
			 * todos para ser inseridos os novos usuários com permissão especial
			 * informados pelo usuário
			 */
			FiltroUsuarioPemissaoEspecial filtroUsuarioPemissaoEspecial = new FiltroUsuarioPemissaoEspecial();
			filtroUsuarioPemissaoEspecial.adicionarParametro(new ParametroSimples(FiltroUsuarioPemissaoEspecial.USUARIO_COMP_ID,
							usuarioAtualizar.getId()));
			Collection colecaoUsuarioPermissaoEspecialCadastradas = getControladorUtil().pesquisar(filtroUsuarioPemissaoEspecial,
							UsuarioPermissaoEspecial.class.getName());
			if(colecaoUsuarioPermissaoEspecialCadastradas != null && !colecaoUsuarioPermissaoEspecialCadastradas.isEmpty()){
				Iterator iteratorUsuarioPermissaoEspecial = colecaoUsuarioPermissaoEspecialCadastradas.iterator();
				while(iteratorUsuarioPermissaoEspecial.hasNext()){
					UsuarioPermissaoEspecial usuarioPermissaoEspecial = (UsuarioPermissaoEspecial) iteratorUsuarioPermissaoEspecial.next();
					// registrar transação
					/*
					 * usuarioPermissaoEspecial.setOperacaoEfetuada(operacaoEfetuada);
					 * usuarioPermissaoEspecial.adicionarUsuario(
					 * Usuario.USUARIO_TESTE,
					 * UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					 * registradorOperacao.registrarOperacao(usuarioPermissaoEspecial);
					 */
					getControladorUtil().remover(usuarioPermissaoEspecial);
				}
			}

			/*
			 * Caso o usuário tenha informado algum usuario permissao especial
			 * que está sendo atualizado, inseri na tabela usuario permissao
			 * especial
			 */
			if(permissoesEspeciais != null && permissoesEspeciais.length != 0){

				for(int i = 0; i < permissoesEspeciais.length; i++){

					UsuarioPermissaoEspecial usuarioPermissaoEspecial = new UsuarioPermissaoEspecial();
					UsuarioPermissaoEspecialPK usuarioPermissaoEspecialPK = new UsuarioPermissaoEspecialPK();

					usuarioPermissaoEspecialPK.setPermissaoEspecialId(new Integer(permissoesEspeciais[i]));
					usuarioPermissaoEspecialPK.setUsuarioId(usuarioAtualizar.getId());

					usuarioPermissaoEspecial.setComp_id(usuarioPermissaoEspecialPK);
					usuarioPermissaoEspecial.setUltimaAlteracao(new Date());

					/*
					 * // registrar transação
					 * usuarioPermissaoEspecial.setOperacaoEfetuada(operacaoEfetuada);
					 * usuarioPermissaoEspecial.adicionarUsuario(
					 * Usuario.USUARIO_TESTE,
					 * UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					 * registradorOperacao.registrarOperacao(usuarioPermissaoEspecial);
					 */
					getControladorUtil().inserir(usuarioPermissaoEspecial);
				}
			}
		}

	}

	/**
	 * [UC0291] Bloquear/Desbloquear Acesso Usuario
	 * 
	 * @author Rômulo Aurélio
	 * @date 09/06/2006
	 * @param usuario
	 * @throws ControladorException
	 * @throws ErroEmailException
	 */

	public void bloquearDesbloquearUsuarioSituacao(Usuario usuario) throws ControladorException{

		// Verifica se o campo Login foi preenchido

		if(usuario.getLogin() == null || usuario.getLogin().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, " Login");
		}
		// Verifica se o campo Usuario Situação foi preenchido
		if(usuario.getUsuarioSituacao() == null || usuario.getUsuarioSituacao().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, " Situação do Usuário");
		}

		FiltroUsuario filtroUsuario = new FiltroUsuario();

		filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.LOGIN, usuario.getLogin()));

		Collection colecaousuario = getControladorUtil().pesquisar(filtroUsuario, Usuario.class.getName());

		UsuarioSituacao usuarioSituacao = new UsuarioSituacao();

		if(colecaousuario == null || colecaousuario.isEmpty()){
			throw new ControladorException("atencao.login_nao_existente", null, "" + usuario.getLogin() + "");
		}

		if(usuario.getUsuarioSituacao().getId() == 2){
			throw new ControladorException("atencao.usuario_alteracao_senha", null);

		}

		// Caso a situcao do usuario selecionada corresponda ao valor "ATIVO",
		// id da situacao do usuario com o
		// valor correspondente a "SENHA PENDENTE" na tabela usuario_situacao
		// envia e-mail para o usuario informando login e senha gerada pelo
		// sistema
		if(usuario.getUsuarioSituacao().getId() == 1){

			usuarioSituacao.setDescricaoUsuarioSituacao(usuario.getUsuarioSituacao().getDescricaoUsuarioSituacao());

			usuarioSituacao.setId(2);

			usuario.setUsuarioSituacao(usuarioSituacao);

			// Gera senha aleatoria

			String senha = Util.geradorSenha(5);

			EnvioEmail envioEmail = getControladorCadastro().pesquisarEnvioEmail(EnvioEmail.INSERIR_USUARIO);

			// Envia e-mail para o usuario informando usuario e senha

			try{
				ServicosEmail.enviarMensagem(envioEmail.getEmailRemetente(), usuario.getDescricaoEmail(), envioEmail.getTituloMensagem(),
								"Usuário:" + usuario.getLogin() + " \n " + "Senha:" + usuario.getSenha());

				usuario.setSenha(Criptografia.encriptarSenha(senha));

			}catch(ErroEmailException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(ErroCriptografiaException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		filtroUsuario.limparListaParametros();

		// // [FS0003] Verificar nova situação de usuario
		// // Verifica se situcaoUsuario atual é igual a nova
		//
		// filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID,
		// usuario.getId()));
		//
		// filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.USUARIO_SITUACAO_ID,
		// usuario.getUsuarioSituacao().getId()
		// .toString()));
		//
		// colecaousuario = getControladorUtil().pesquisar(filtroUsuario, Usuario.class.getName());
		//
		// if(colecaousuario != null && !colecaousuario.isEmpty()){
		// throw new ControladorException("atencao.usuario_situcao_igual_principal", null, ""
		// + usuario.getUsuarioSituacao().getDescricaoUsuarioSituacao() + "");
		//
		// }

		usuario.setUltimaAlteracao(new Date());

		getControladorUtil().atualizar(usuario);

	} // fim

	/**
	 * Método que consulta os grupos do usuário
	 * 
	 * @author Sávio Luiz
	 * @date 27/06/2006
	 */
	public Collection pesquisarGruposUsuario(Integer idUsuario) throws ControladorException{

		try{
			// salvando o usuarioGrupo
			return repositorioUsuario.pesquisarGruposUsuario(idUsuario);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Método que consulta os grupos do usuário da tabela grupoAcessos
	 * 
	 * @author Sávio Luiz
	 * @date 21/02/2007
	 */
	public Collection pesquisarGruposUsuarioAcesso(Collection colecaoUsuarioGrupos) throws ControladorException{

		try{
			// salvando o usuarioGrupo
			return repositorioUsuario.pesquisarGruposUsuarioAcesso(colecaoUsuarioGrupos);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Método que consulta as abrangências dos usuário pelos os ids das
	 * abrangências superiores e com o id da abrangência diferente do id da
	 * abrangência do usuário que está inserindo(usuário logado)
	 * 
	 * @author Sávio Luiz
	 * @date 28/06/2006
	 */
	public Collection pesquisarUsuarioAbrangenciaPorSuperior(Collection colecaoUsuarioAbrangencia, Integer idUsuarioAbrangenciaLogado)
					throws ControladorException{

		try{
			// salvando o usuarioGrupo
			return repositorioUsuario.pesquisarUsuarioAbrangenciaPorSuperior(colecaoUsuarioAbrangencia, idUsuarioAbrangenciaLogado);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Informa o número total de registros de usuario grupo, auxiliando o
	 * esquema de paginação
	 * 
	 * @author Sávio Luiz
	 * @date 30/06/2006
	 * @param Filtro
	 *            da Pesquisa
	 * @param Pacote
	 *            do objeto pesquisado
	 * @return Número de registros da pesquisa
	 * @throws ErroRepositorioException
	 *             Exceção do repositório
	 */
	public int totalRegistrosPesquisaUsuarioGrupo(FiltroUsuarioGrupo filtroUsuarioGrupo) throws ControladorException{

		try{
			// salvando o usuarioGrupo
			return repositorioUsuario.totalRegistrosPesquisaUsuarioGrupo(filtroUsuarioGrupo);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Informa o número total de registros de usuario grupo, auxiliando o
	 * esquema de paginação
	 * 
	 * @author Sávio Luiz
	 * @date 30/06/2006
	 * @param Filtro
	 *            da Pesquisa
	 * @param Pacote
	 *            do objeto pesquisado
	 * @return Número de registros da pesquisa
	 * @throws ErroRepositorioException
	 *             Exceção do repositório
	 */
	public Collection pesquisarUsuariosDosGruposUsuarios(FiltroUsuarioGrupo filtroUsuarioGrupo, Integer numeroPagina)
					throws ControladorException{

		try{
			// salvando o usuarioGrupo
			return repositorioUsuario.pesquisarUsuariosDosGruposUsuarios(filtroUsuarioGrupo, numeroPagina);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Método que consulta os grupos funcionários operações passando os ids dos
	 * grupos
	 * 
	 * @author Sávio Luiz
	 * @date 11/07/2006
	 */

	public Collection pesquisarGruposFuncionalidadeOperacoes(Integer[] idsGrupos) throws ControladorException{

		try{
			// salvando o usuarioGrupo
			return repositorioUsuario.pesquisarGruposFuncionalidadesOperacoes(idsGrupos);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Método que consulta os grupos funcionários operações passando os ids dos
	 * grupos e o id da funcionalidade
	 * 
	 * @author Sávio Luiz
	 * @date 11/07/2006
	 */
	public Collection pesquisarGruposFuncionalidadesOperacoesPelaFuncionalidade(Integer[] idsGrupos, Integer idFuncionalidade)
					throws ControladorException{

		try{
			// salvando o usuarioGrupo
			return repositorioUsuario.pesquisarGruposFuncionalidadesOperacoesPelaFuncionalidade(idsGrupos, idFuncionalidade);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Método que consulta os usuários restrinção passando os ids dos grupos , o
	 * id da funcionalidade e o id do usuário
	 * 
	 * @author Sávio Luiz
	 * @date 11/07/2006
	 */
	public Collection pesquisarUsuarioRestrincao(Integer[] idsGrupos, Integer idFuncionalidade, Integer idUsuario)
					throws ControladorException{

		try{
			// salvando o usuarioGrupo
			return repositorioUsuario.pesquisarUsuarioRestrincao(idsGrupos, idFuncionalidade, idUsuario);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Método que consulta as funcionalidades da(s) funcionalidade(s)
	 * princpial(is)
	 * 
	 * @author Sávio Luiz
	 * @date 12/07/2006
	 */
	public Collection pesquisarFuncionanidadesDependencia(Collection idsFuncionalidades) throws ControladorException{

		try{
			// salvando o usuarioGrupo
			return repositorioUsuario.pesquisarFuncionanidadesDependencia(idsFuncionalidades);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Método que consulta as operações da(s) funcionalidade(s)
	 * 
	 * @author Sávio Luiz
	 * @date 12/07/2006
	 */
	public Collection pesquisarOperacoes(Collection idsFuncionalidades) throws ControladorException{

		try{
			// salvando o usuarioGrupo
			return repositorioUsuario.pesquisarOperacoes(idsFuncionalidades);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Método que consulta as operações da(s) funcionalidade(s) e das
	 * funcionalidades dependencia
	 * 
	 * @author Sávio Luiz
	 * @date 12/07/2006
	 * @author Eduardo Henrique
	 * @date 30/05/2008
	 *       Alteração no método para evitar loops infinitos para configuração incorreta de
	 *       dependências de funcionalidades no BD
	 */
	public Collection recuperarOperacoesFuncionalidadesEDependentes(Integer idFuncionalidade) throws ControladorException{

		// cria uma coleção de funcionalidades principal e inseri o id da
		// funcionalidade na colleção
		Collection funcionalidadesPrincipal = new ArrayList();
		funcionalidadesPrincipal.add(idFuncionalidade);
		// cria uma coleção de funcionalidades dependencias que retornará as
		// funcionalidades dependencias da(s) funcionalidade(s) principal(is)
		Collection funcionalidadesDependencia = null;

		// Map serve de suporte para verificar que funcionalidades já foram consultadas
		Map<Integer, Integer> mapFuncionalidadesRelacionadas = new HashMap<Integer, Integer>();
		mapFuncionalidadesRelacionadas.put(idFuncionalidade, idFuncionalidade);

		// cria um boolean que vai verificar quando um determinado grupo(ou uma
		// só) de funcionalidade(s) não tem mais dependencias
		boolean terminou = false;
		// caso não tenha funcionalidades dependencias então sai do laço e
		// pesquisa as operações das funcionalidades
		while(!terminou){
			funcionalidadesDependencia = pesquisarFuncionanidadesDependencia(funcionalidadesPrincipal);
			if(funcionalidadesDependencia != null && !funcionalidadesDependencia.isEmpty()){

				// funcionalidadesPrincipal.clear();

				for(Iterator iterator = funcionalidadesDependencia.iterator(); iterator.hasNext();){
					Integer idFuncionalidadeDependente = (Integer) iterator.next();

					if(!mapFuncionalidadesRelacionadas.containsKey(idFuncionalidadeDependente)
									&& !funcionalidadesPrincipal.contains(idFuncionalidadeDependente)){
						funcionalidadesPrincipal.add(idFuncionalidadeDependente);
						terminou = false;
					}else{
						terminou = true;
					}
				}
			}else{
				terminou = true;
			}
		}

		// // Verifica se a funcionalidade principal foi excluida da lista
		// if(!Util.isVazioOrNulo(funcionalidadesPrincipal) &&
		// !funcionalidadesPrincipal.contains(idFuncionalidade)){
		// // Insere a funcionalidade principal na lista
		// funcionalidadesPrincipal.add(idFuncionalidade);
		// }

		Collection operacoes = pesquisarOperacoes(funcionalidadesPrincipal);
		return operacoes;
	}

	/**
	 * Método que marca e desmarca as permissões especiais do usuário.
	 * 
	 * @author Sávio Luiz
	 * @date 12/07/2006
	 */

	public String[] retornarPermissoesMarcadas(Collection permissoesEspeciais){

		String[] permissaoEspecial = null;
		if(permissoesEspeciais != null && !permissoesEspeciais.isEmpty()){
			// seta os campos de permissão especial no form para
			// aparecer no
			// jsp como checado
			Iterator iteratorPermissaoEspecial = permissoesEspeciais.iterator();
			int i = 0;
			permissaoEspecial = new String[permissoesEspeciais.size()];
			while(iteratorPermissaoEspecial.hasNext()){
				UsuarioPermissaoEspecial usuarioPermissaoEspecialObject = (UsuarioPermissaoEspecial) iteratorPermissaoEspecial.next();
				permissaoEspecial[i] = "" + usuarioPermissaoEspecialObject.getPermissaoEspecial().getId();
				i = i + 1;
			}
		}
		return permissaoEspecial;
	}

	/**
	 * Retorna 2 coleções e um array ,com os valores que vão retornar
	 * marcados,uma com as permissões do usuário que ele possa marcar/desmarcar
	 * e a outra o usuário logado não vai poder marcar/desmarcar
	 * [UC0231] - Manter Usuário [SB0010] - Selecionar Permissões Especiais
	 * (n°2)
	 * 
	 * @author Sávio Luiz
	 * @date 13/07/2006
	 */
	public Object[] pesquisarPermissoesEspeciaisUsuarioEUsuarioLogado(Usuario usuarioAtualizar, Usuario usuarioLogado)
					throws ControladorException{

		Collection colecaoPermissaoEspecial = new ArrayList();
		Collection colecaoPermissaoEspecialDesabilitado = new ArrayList();
		String[] idsPermissaoEspecialMarcado = null;

		Object[] object = new Object[3];
		try{
			// permissões especiais do usuário que está sendo atualizado
			Collection colecaoPermissaoEspecialUsuarioAtualizar = repositorioUsuario.pesquisarPermissaoEspecialUsuario(usuarioAtualizar
							.getId());
			// array com os ids das permissões que vai ser checado
			idsPermissaoEspecialMarcado = retornarPermissoesMarcadas(colecaoPermissaoEspecialUsuarioAtualizar);

			colecaoPermissaoEspecial.addAll(colecaoPermissaoEspecialUsuarioAtualizar);
			// pesquisa permissões especiais do usuário que está logado
			Collection colecaoPermissaoEspecialUsuarioLogado = repositorioUsuario.pesquisarPermissaoEspecialUsuario(usuarioLogado.getId());

			Collection colecaoPermissaoUsuarioLogadoAux = new ArrayList();
			colecaoPermissaoUsuarioLogadoAux.addAll(colecaoPermissaoEspecialUsuarioLogado);
			colecaoPermissaoUsuarioLogadoAux.removeAll(colecaoPermissaoEspecialUsuarioAtualizar);
			colecaoPermissaoEspecial.addAll(colecaoPermissaoUsuarioLogadoAux);

			colecaoPermissaoEspecialUsuarioAtualizar.removeAll(colecaoPermissaoEspecialUsuarioLogado);
			colecaoPermissaoEspecialDesabilitado.addAll(colecaoPermissaoEspecialUsuarioAtualizar);
			colecaoPermissaoEspecial.removeAll(colecaoPermissaoEspecialDesabilitado);

			/*
			 * // pesquisa as permissão especial dos usuários que não vão //
			 * aparecer marcados Collection
			 * colecaoPermissaoUsuarioLogadoSemMarcado = repositorioUsuario
			 * .pesquisarPermissaoEspecialUsuarioSemPermissoes(
			 * usuarioLogado.getId(), colecaoPermissaoEspecialUsuarioAtualizar); //
			 * adiciona todos os campos na coleção de permissões especiais
			 * colecaoPermissaoEspecial
			 * .addAll(colecaoPermissaoEspecialUsuarioAtualizar);
			 * colecaoPermissaoEspecial
			 * .addAll(colecaoPermissaoUsuarioLogadoSemMarcado); // pesquisa
			 * permissões especiais do usuário que está logado Collection
			 * colecaoPermissaoEspecialUsuarioLogado = repositorioUsuario
			 * .pesquisarPermissaoEspecialUsuario(usuarioLogado.getId());
			 * Collection colecaoPermissaoUsuarioAtualizarSemMarcado =
			 * repositorioUsuario
			 * .pesquisarPermissaoEspecialUsuarioSemPermissoes(
			 * usuarioAtualizar.getId(), colecaoPermissaoEspecialUsuarioLogado);
			 */

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
		object[0] = colecaoPermissaoEspecial;
		object[1] = colecaoPermissaoEspecialDesabilitado;
		object[2] = idsPermissaoEspecialMarcado;
		return object;
	}

	/**
	 * Retorna um map com o indicador dizendo se vai aparecer
	 * marcado(1),desmarcado(2) ou desabilitado(3) para cada operação da
	 * funcionalidade escolhida
	 * [UC0231] - Manter Usuário [SB0008] - Selecionar Restrinções (n°2)
	 * 
	 * @author Sávio Luiz
	 * @date 17/07/2006
	 */
	public Map<Integer, Map<Integer, Collection<Operacao>>> organizarOperacoesComValor(Integer codigoFuncionalidade,
					Map<Integer, Map<Integer, Collection<Operacao>>> funcionalidadeMap, Integer[] idsGrupos, Usuario usuarioAtualizar)
					throws ControladorException{

		boolean existeFuncionalidade = false;

		if(funcionalidadeMap != null && !funcionalidadeMap.isEmpty()){

			Collection idsfuncionalidades = funcionalidadeMap.keySet();
			Iterator iteratorFuncionalidades = idsfuncionalidades.iterator();
			// verifica se existe a funcionalidade escolhida na coleção de
			// funcionalidade
			while(iteratorFuncionalidades.hasNext()){
				Integer idfuncionalidade = (Integer) iteratorFuncionalidades.next();
				if(idfuncionalidade.equals(new Integer(codigoFuncionalidade))){
					existeFuncionalidade = true;
				}
			}
		}
		// caso não exista funcionalidade na coleção de
		// gruposFuncionalidades e a coleção de ids funcionalidades não
		// esteja vazio então pesquisa na base as operações que
		// o usuário tem acesso
		if(!existeFuncionalidade){

			// Cria a funcionalidade para ser inserido no map
			Map<Integer, Collection<Operacao>> operacoesMap = new HashMap();

			// pesquisa as operações na tabela de
			// grupoFuncionalidadeOperacao
			Collection operacoesDaFuncionalidadeGrupo = pesquisarGruposFuncionalidadesOperacoesPelaFuncionalidade(idsGrupos, new Integer(
							codigoFuncionalidade));

			// pesquisa as operações na tabela de
			// UsuarioRestrinção
			Collection operacoesUsuarioComRestrincao = pesquisarUsuarioRestrincao(idsGrupos, new Integer(codigoFuncionalidade),
							usuarioAtualizar.getId());
			// remove todas as operações do usuário com
			// restrinção
			// da
			// coleção de operações funcionalidade
			operacoesDaFuncionalidadeGrupo.removeAll(operacoesUsuarioComRestrincao);

			if(operacoesDaFuncionalidadeGrupo != null && !operacoesDaFuncionalidadeGrupo.isEmpty()){
				// coloca as operações com o indicador de seleção igual a
				// 1(checkbox marcados)
				operacoesMap.put(1, operacoesDaFuncionalidadeGrupo);
			}

			if(operacoesUsuarioComRestrincao != null && !operacoesUsuarioComRestrincao.isEmpty()){
				// coloca as operações com o indicador de seleção igual a
				// 2(checkbox desmarcados)
				operacoesMap.put(2, operacoesUsuarioComRestrincao);
			}
			// recupera as operações da tabela grupo funcionalidade operação e
			// da tabela usuário restrinções pesquisadas anteriormente
			Collection colecaoOperacoesUniao = new ArrayList();

			colecaoOperacoesUniao.addAll(operacoesUsuarioComRestrincao);

			colecaoOperacoesUniao.addAll(operacoesDaFuncionalidadeGrupo);

			Collection colecaoOperacao = recuperarOperacoesFuncionalidadesEDependentes(new Integer(codigoFuncionalidade));
			if(colecaoOperacao != null && !colecaoOperacao.isEmpty()){
				Collection operacoesDesabilitados = new ArrayList();
				Iterator iteratorOperacoes = colecaoOperacao.iterator();

				while(iteratorOperacoes.hasNext()){
					Operacao operacao = (Operacao) iteratorOperacoes.next();
					// caso não exista na coleção de operações pesquisadas
					// operacoesUsuarioComRestrincao e
					// operacoesDaFuncionalidadeGrupo então é colocado no map
					// com o
					// indicador de seleção igual a 3(Desabilitado)
					if(!colecaoOperacoesUniao.contains(operacao)){
						operacoesDesabilitados.add(operacao);
					}
				}
				// coloca as operações com o indicador de seleção igual a
				// 3(checkbox desabilitado)
				operacoesMap.put(3, operacoesDesabilitados);
			}
			// seta o map operacoesMap na map de funcionalidades
			funcionalidadeMap.put(new Integer(codigoFuncionalidade), operacoesMap);
		}

		return funcionalidadeMap;
	}

	/**
	 * Retorna um map com o indicador dizendo se vai aparecer
	 * marcado(1),desmarcado(2) ou desabilitado(3) para cada operação da
	 * funcionalidade escolhida e a coleção com as operações e funcionalidades
	 * que que foram desmarcados
	 * [UC0231] - Manter Usuário [SB0008] - Selecionar Restrinções (n°2)
	 * 
	 * @author Sávio Luiz
	 * @date 17/07/2006
	 */
	public Map<Integer, Map<Integer, Collection<Operacao>>> recuperaFuncionalidadeOperacaoRestrincao(Integer codigoFuncionalidade,
					String[] idsOperacoes, Map<Integer, Map<Integer, Collection<Operacao>>> funcionalidadeMap) throws ControladorException{

		Map<Integer, Collection<Operacao>> operacoesMap = funcionalidadeMap.get(new Integer(codigoFuncionalidade));
		// recupera a coleção de operações que estão com o indicador de seleção
		// igual a 1(Marcados)
		Collection operacoesMarcadas = operacoesMap.get(1);
		Collection operacoesDesmarcadasMais = new ArrayList();
		if(operacoesMarcadas != null && !operacoesMarcadas.isEmpty()){
			Iterator iteratorOperacoes = operacoesMarcadas.iterator();
			while(iteratorOperacoes.hasNext()){
				// cria um boolean que verifica se cada operação que tinha o
				// indicador igual a 1(Marcada) continua marcada,caso contrário
				// remove da coleção de operações marcadas e inseri na coleção
				// de
				// operações não marcadas.
				boolean achou = false;
				Operacao operacao = (Operacao) iteratorOperacoes.next();
				if(idsOperacoes != null && !idsOperacoes.equals("")){
					for(int i = 0; i < idsOperacoes.length; i++){
						if(operacao.getId().equals(new Integer(idsOperacoes[i]))){
							achou = true;
						}
					}
				}
				if(!achou){
					operacoesDesmarcadasMais.add(operacao);
					iteratorOperacoes.remove();
				}
			}
		}
		// recupera a coleção de operações que estão com o indicador de seleção
		// igual a 2(Desmarcados)
		Collection operacoesDesmarcadas = operacoesMap.get(2);
		Collection operacoesMarcadasMais = new ArrayList();
		if(operacoesDesmarcadas != null && !operacoesDesmarcadas.isEmpty()){
			Iterator iteratorOperacoesDesmarcadas = operacoesDesmarcadas.iterator();
			while(iteratorOperacoesDesmarcadas.hasNext()){
				// cria um boolean que verifica se cada operação que tinha o
				// indicador igual a 2(Desmarcada) foi marcada,se sim
				// remove da coleção de operações desmarcadas e inseri na
				// coleção de
				// operações marcadas.
				boolean achou = false;
				Operacao operacao = (Operacao) iteratorOperacoesDesmarcadas.next();
				if(idsOperacoes != null && !idsOperacoes.equals("")){
					for(int i = 0; i < idsOperacoes.length; i++){
						if(operacao.getId().equals(new Integer(idsOperacoes[i]))){
							achou = true;
						}
					}
				}
				if(achou){
					operacoesMarcadasMais.add(operacao);
					iteratorOperacoesDesmarcadas.remove();
				}
			}
		}
		// adiciona as novas operações marcadas na coleção de operações
		// marcadas.
		if(operacoesMarcadas != null && !operacoesMarcadas.isEmpty()){
			operacoesMarcadas.addAll(operacoesMarcadasMais);
		}else{
			if(operacoesMarcadasMais != null && !operacoesMarcadasMais.isEmpty()){

				operacoesMarcadas = new ArrayList();
				operacoesMarcadas.addAll(operacoesMarcadasMais);
				operacoesMap.put(1, operacoesMarcadas);
			}
		}
		// adiciona as novas operações desmarcadas na coleção de operações
		// desmarcadas.
		if(operacoesDesmarcadas != null && !operacoesDesmarcadas.isEmpty()){
			operacoesDesmarcadas.addAll(operacoesDesmarcadasMais);

		}else{
			if(operacoesDesmarcadasMais != null && !operacoesDesmarcadasMais.isEmpty()){
				operacoesDesmarcadas = new ArrayList();
				operacoesDesmarcadas.addAll(operacoesDesmarcadasMais);
				operacoesMap.put(2, operacoesDesmarcadas);
			}
		}

		return funcionalidadeMap;

	}

	/**
	 * Método que verifica a permissão do usuário a uma determinada funcionalidade
	 * 
	 * @author Ítalo Almeida
	 * @date 28/06/2013
	 */
	public boolean verificarPermissaoFuncionalidadeUsuario(Integer idUsuario, String descricaoCaminhoOperacao,
					String descricaoCaminhoFuncionalidade)
					throws ControladorException{

		try{
			return repositorioUsuario.verificarPermissaoFuncionalidadeUsuario(idUsuario, descricaoCaminhoOperacao,
							descricaoCaminhoFuncionalidade);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Método que cria uma coleção de UsuarioAcesso
	 * 
	 * @author Saulo Lima
	 * @date 16/09/2014
	 */
	public Collection<UsuarioAcesso> criarColecaoUsuarioAcesso(int indicadorSelecionado) throws ControladorException{

		Collection<UsuarioAcesso> retorno = new ArrayList<UsuarioAcesso>();

		Date data = Util.converteStringParaDate("01/01/1900", true);

		Date horaInicial = Util.formatarDataInicial(data);
		Date horaFinal = Util.formatarDataFinal(data);

		for(int i = 1; i <= 7; i++){
			UsuarioAcesso usuarioAcessoDia = new UsuarioAcesso(i, Util.obterDiaSemanaDescricao(i), horaInicial, horaFinal,
							indicadorSelecionado);

			retorno.add(usuarioAcessoDia);
		}

		return retorno;
	}

	/**
	 * Método que pesquisa as retrições de horário de acesso ao sistema de um usuário
	 * 
	 * @author Saulo Lima
	 * @date 20/09/2014
	 */
	public Collection<UsuarioAcesso> pesquisarUsuarioAcesso(Integer idUsuario) throws ControladorException{

		Collection<UsuarioAcesso> retorno = null;
		try{
			retorno = repositorioUsuario.pesquisarUsuarioAcesso(idUsuario);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

}