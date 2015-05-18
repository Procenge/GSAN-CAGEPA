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
 * Defini��o da l�gica de neg�cio do Session Bean de ControladorCliente
 * 
 * @author S�vio Luiz
 * @created 25 de Abril de 2005
 */
public class ControladorUsuarioSEJB
				implements SessionBean {

	private static final long serialVersionUID = 1L;

	private IRepositorioUtil repositorioUtil;

	private IRepositorioUsuario repositorioUsuario;

	SessionContext sessionContext;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @exception CreateException
	 *                Descri��o da exce��o
	 */
	public void ejbCreate() throws CreateException{

		repositorioUtil = RepositorioUtilHBM.getInstancia();
		repositorioUsuario = RepositorioUsuarioHBM.getInstancia();
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
		 * [UC0107] Registrar Transa��o
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
		// verifica a data de cadastramento final se � diferente de nulo
		if(dataCadastramentoFinal != null && !dataCadastramentoFinal.equals("")){
			// caso a data atual + dias sistemas parametros seja maior que a
			// data de cadastramento final do usu�rio
			if(dataAtualMaisDiasSistemasParametros.after(dataCadastramentoFinal)){
				// seta o valor da data de cadastramento final do usuario no
				// usuario data expira��o acesso
				usuario.setDataExpiracaoAcesso(dataCadastramentoFinal);
			}else{
				// seta o valor da a data atual + dias sistemas parametros no
				// usuario data expira��o acesso
				usuario.setDataExpiracaoAcesso(dataAtualMaisDiasSistemasParametros);
			}

			// caso a data atual - dias mensagem expiracao sistemas parametros
			// seja maior que a
			// data de cadastramento final do usu�rio
			if(dataAtualMenosDiasMSGExpiracao.after(dataCadastramentoFinal)){
				// seta o valor da data de cadastramento final do usuario no
				// usuario data expira��o acesso
				usuario.setDataPrazoMensagemExpiracao(dataCadastramentoFinal);
			}else{
				// seta o valor da a data atual - dias mensagem expiracao
				// sistemas parametros no
				// usuario data expira��o acesso
				usuario.setDataPrazoMensagemExpiracao(dataAtualMenosDiasMSGExpiracao);
			}
		}else{
			// seta o valor da a data atual + dias sistemas parametros no
			// usuario data expira��o acesso
			usuario.setDataExpiracaoAcesso(dataAtualMaisDiasSistemasParametros);
			// seta o valor da a data atual - dias mensagem expiracao sistemas
			// parametros no
			// usuario data prazo mensagem expira��o acesso
			usuario.setDataPrazoMensagemExpiracao(dataAtualMenosDiasMSGExpiracao);
		}
		// registrar transa��o
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

				// registrar transa��o
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
	 * @author S�vio Luiz
	 * @date 07/07/2006
	 * @param usuario
	 * @param idGrupo
	 *            grupos que o usuario faz parte
	 * @throws ControladorException
	 */
	public void atualizarUsuario(Usuario usuario, Integer[] idGrupos, String processo, Usuario usuarioLogado,
					Collection<UsuarioAcesso> colecaoUsuarioAcesso, String indicadorHorarioAcessoRestrito) throws ControladorException{

		/*
		 * [UC0107] Registrar Transa��o
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
		// verifica a data de cadastramento final se � diferente de nulo
		if(dataCadastramentoFinal != null && !dataCadastramentoFinal.equals("")){
			// caso a data atual + dias sistemas parametros seja maior que a
			// data de cadastramento final do usu�rio
			if(dataAtualMaisDiasSistemasParametros.after(dataCadastramentoFinal)){
				// seta o valor da data de cadastramento final do usuario no
				// usuario data expira��o acesso
				usuario.setDataExpiracaoAcesso(dataCadastramentoFinal);
			}else{
				// seta o valor da a data atual + dias sistemas parametros no
				// usuario data expira��o acesso
				usuario.setDataExpiracaoAcesso(dataAtualMaisDiasSistemasParametros);
			}

			// caso a data atual - dias mensagem expiracao sistemas parametros
			// seja maior que a
			// data de cadastramento final do usu�rio
			if(dataAtualMenosDiasMSGExpiracao.after(dataCadastramentoFinal)){
				// seta o valor da data de cadastramento final do usuario no
				// usuario data expira��o acesso
				usuario.setDataPrazoMensagemExpiracao(dataCadastramentoFinal);
			}else{
				// seta o valor da a data atual - dias mensagem expiracao
				// sistemas parametros no
				// usuario data expira��o acesso
				usuario.setDataPrazoMensagemExpiracao(dataAtualMenosDiasMSGExpiracao);
			}
		}else{
			// seta o valor da a data atual + dias sistemas parametros no
			// usuario data expira��o acesso
			usuario.setDataExpiracaoAcesso(dataAtualMaisDiasSistemasParametros);
			// seta o valor da a data atual - dias mensagem expiracao sistemas
			// parametros no
			// usuario data prazo mensagem expira��o acesso
			usuario.setDataPrazoMensagemExpiracao(dataAtualMenosDiasMSGExpiracao);
		}
		// registrar transa��o
		usuario.setOperacaoEfetuada(operacaoEfetuada);
		usuario.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorOperacao.registrarOperacao(usuario);

		this.getControladorUtil().atualizar(usuario);

		/*
		 * [UC0107] Registrar Transa��o
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
				 * Alteracao realizada por R�mulo Aur�lio
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

						// registrar transa��o
						usuarioGrupo.setOperacaoEfetuada(operacaoEfetuada);
						usuarioGrupo.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

						registradorOperacao.registrarOperacao(usuarioGrupo);

						this.getControladorUtil().remover(usuarioGrupo);
					}
				}

				/*
				 * [UC0107] Registrar Transa��o
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
						// registrar transa��o
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
	 * @author S�vio Luiz
	 * @date 07/07/2006
	 * @param idsUsuario
	 * @param usuario
	 * @throws ControladorException
	 */
	public void removerUsuario(String[] idsUsuario, Usuario usuario) throws ControladorException{

		// removendo os usuarios grupos
		for(int i = 0; i < idsUsuario.length; i++){

			// ------------ REGISTRAR TRANSA��O ----------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_USUARIO_REMOVER,
							new UsuarioAcaoUsuarioHelper(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_USUARIO_REMOVER);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			// Parte da verifica��o do filtro
			FiltroUsuario filtroUsuario = new FiltroUsuario();

			// filtroUsuario.setCampoOrderBy(FiltroUsuario.NOME_USUARIO);
			filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, idsUsuario[i]));
			filtroUsuario.adicionarCaminhoParaCarregamentoEntidade("unidadeOrganizacional");
			filtroUsuario.adicionarCaminhoParaCarregamentoEntidade("funcionario.unidadeOrganizacional");
			Collection colecaoUsuario = this.getControladorUtil().pesquisar(filtroUsuario, Usuario.class.getName());

			Usuario usuarioParaRemover = (Usuario) Util.retonarObjetoDeColecao(colecaoUsuario);
			// [FS0008] - Verificar permiss�o para atualiza��o

			UnidadeOrganizacional unidadeEmpresa = usuarioParaRemover.getUnidadeOrganizacional();
			if(unidadeEmpresa == null){
				if(usuarioParaRemover.getFuncionario() != null && !usuarioParaRemover.getFuncionario().equals("")){
					unidadeEmpresa = usuarioParaRemover.getFuncionario().getUnidadeOrganizacional();
				}
			}

			// caso o usu�rio que esteja efetuando a inser��o n�o
			// seja
			// do grupo de administradores
			FiltroUsuarioGrupo filtroUsuarioGrupo = new FiltroUsuarioGrupo();

			filtroUsuarioGrupo.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupo.USUARIO_ID, usuario.getId()));
			filtroUsuarioGrupo.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupo.GRUPO_ID, Grupo.ADMINISTRADOR));
			Collection colecaoUsuarioGrupo = this.getControladorUtil().pesquisar(filtroUsuarioGrupo, UsuarioGrupo.class.getName());
			if(colecaoUsuarioGrupo == null || colecaoUsuarioGrupo.isEmpty()){
				// se a unidade de lotacao do usuario que estiver
				// efetuando seja diferente da unidade de
				// lota��o informada
				if(usuario.getUnidadeOrganizacional() != null && unidadeEmpresa != null
								&& usuario.getUnidadeOrganizacional().getId() != null
								&& !usuario.getUnidadeOrganizacional().getId().equals(unidadeEmpresa.getId())){
					// recupera a unidade do usu�rio
					FiltroUnidadeOrganizacional filtroUnidadeEmpresaUsuario = new FiltroUnidadeOrganizacional();
					filtroUnidadeEmpresaUsuario.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, usuario
									.getUnidadeOrganizacional().getId()));
					Collection colecaoUnidadeEmpresa = this.getControladorUtil().pesquisar(filtroUnidadeEmpresaUsuario,
									UnidadeOrganizacional.class.getName());
					UnidadeOrganizacional unidadeEmpresaUsuario = null;
					if(colecaoUnidadeEmpresa != null && !colecaoUnidadeEmpresa.isEmpty()){
						unidadeEmpresaUsuario = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeEmpresa);
					}
					// se o nivel da unidade de lota��o do usu�rio
					// que
					// estiver efetuando a inser��o seja maior ou
					// igual
					// ao nivel de unidade de lota��o informada
					if(unidadeEmpresaUsuario != null){
						if(unidadeEmpresaUsuario.getUnidadeTipo().getNivel().intValue() >= unidadeEmpresa.getUnidadeTipo().getNivel()
										.intValue()){
							throw new ControladorException("atencao.usuario.sem.permissao.atualizacao", null, usuario.getLogin(),
											unidadeEmpresa.getDescricao());
						}
					}
					// ou a unidade superior da unidade de lota��o
					// informada seja diferente da unidade de
					// lota��o do usu�rio

					// enquanto o nivel superior da unidade de
					// lota��o n�o esteja no mesmo nivel da unidade
					// de lota��o do usu�rio
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
						// recupera a unidade do usu�rio
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
								// unidade empresa do usu�rio no
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
			// remove os usuarios grupos restrin��es
			FiltroUsuarioGrupoRestricao filtroUsuarioGrupoRestricao = new FiltroUsuarioGrupoRestricao();
			filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.USUARIO_ID, idsUsuario[i]));
			Collection colecaoUsuarioGrupoRestricao = this.getControladorUtil().pesquisar(filtroUsuarioGrupoRestricao,
							UsuarioGrupoRestricao.class.getName());

			if(colecaoUsuarioGrupoRestricao != null && !colecaoUsuarioGrupoRestricao.isEmpty()){
				Iterator it = colecaoUsuarioGrupoRestricao.iterator();
				while(it.hasNext()){
					UsuarioGrupoRestricao usuarioGrupoRestricao = (UsuarioGrupoRestricao) it.next();

					// registrar transa��o
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
					// registrar transa��o
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

					// registrar transa��o
					usuarioGrupo.setOperacaoEfetuada(operacaoEfetuada);
					usuarioGrupo.adicionarUsuario(Usuario.USUARIO_TESTE, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

					registradorOperacao.registrarOperacao(usuarioGrupo);

					this.getControladorUtil().remover(usuarioGrupo);
				}
			}
			// registrar
			// transa��ofiltroUsuarioGrupoRestricao.adicionarCaminhoParaCarregamentoEntidade("grupo");
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

			// ------------ REGISTRAR TRANSA��O ----------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_USUARIO_ATUALIZAR,
							new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_USUARIO_ATUALIZAR);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			// Parte da verifica��o do filtro
			FiltroUsuario filtroUsuario = new FiltroUsuario();

			// filtroUsuario.setCampoOrderBy(FiltroUsuario.NOME_USUARIO);
			filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, idsUsuario[i]));
			filtroUsuario.adicionarCaminhoParaCarregamentoEntidade("unidadeOrganizacional");
			filtroUsuario.adicionarCaminhoParaCarregamentoEntidade("funcionario.unidadeOrganizacional");
			Collection colecaoUsuario = this.getControladorUtil().pesquisar(filtroUsuario, Usuario.class.getName());

			Usuario usuarioParaInativar = (Usuario) Util.retonarObjetoDeColecao(colecaoUsuario);

			// atualiza situa��o
			usuarioParaInativar.setUsuarioSituacao(usuarioSituacao);

			usuarioParaInativar.setUltimaAlteracao(new Date());
			usuarioParaInativar.setOperacaoEfetuada(operacaoEfetuada);
			usuarioParaInativar.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			registradorOperacao.registrarOperacao(usuarioParaInativar);
			this.getControladorUtil().atualizar(usuarioParaInativar);
		}
	}

	/**
	 * M�todo que atualiza o controle de acesso do usu�rio
	 * [UC0231] - Manter Usu�rio
	 * 
	 * @author S�vio Luiz
	 * @date 14/07/2006
	 * @param String
	 *            []
	 * @param grupoFuncionalidadeOperacao
	 */
	public void atualizarControleAcessoUsuario(String[] permissoesEspeciais,
					Map<Integer, Map<Integer, Collection<Operacao>>> funcionalidadesMap, Usuario usuarioAtualizar, Integer[] idsGrupos,
					String permissoesCheckBoxVazias) throws ControladorException{

		// ------------ REGISTRAR TRANSA��O ----------------
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
			 * Pesquisa todos os usu�rios grupos restrin��es, e remove todos
			 * para ser inseridos os novos acesso(s) informados pelo usu�rio
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
					 * // registrar transa��o
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
			 * Caso o usu�rio tenha informado algum acesso para o grupo que est�
			 * sendo atualizado, inseri na tabela usuario_grupo_restrin��o
			 */

			Collection idsfuncionalidades = funcionalidadesMap.keySet();
			if(idsfuncionalidades != null && !idsfuncionalidades.isEmpty()){
				Iterator iteratorFuncionalidades = idsfuncionalidades.iterator();
				// verifica se existe a funcionalidade escolhida na cole��o de
				// funcionalidade
				while(iteratorFuncionalidades.hasNext()){
					Integer idfuncionalidade = (Integer) iteratorFuncionalidades.next();
					Map<Integer, Collection<Operacao>> operacoesMap = funcionalidadesMap.get(idfuncionalidade);
					// para cada funcionalidade verifica se existe opera��es
					// desmarcadas(com indicador igual a 2).
					Collection colecaoOperacao = operacoesMap.get(2);
					if(colecaoOperacao != null && !colecaoOperacao.isEmpty()){
						Iterator iteratorOperacao = colecaoOperacao.iterator();
						while(iteratorOperacao.hasNext()){
							Operacao operacao = (Operacao) iteratorOperacao.next();

							Collection idsGruposNaBase = null;
							try{
								// pesquisando os ids que v�o ser inseridos na
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
								 * // registrar transa��o usuarioGrupoRestricao
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
		// caso tenha passado na funcionalidade de permiss�es especiais
		if(!(permissoesEspeciais == null && permissoesCheckBoxVazias == null)){

			/*
			 * Pesquisa todos os usu�rios com permiss�o especial , e remove
			 * todos para ser inseridos os novos usu�rios com permiss�o especial
			 * informados pelo usu�rio
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
					// registrar transa��o
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
			 * Caso o usu�rio tenha informado algum usuario permissao especial
			 * que est� sendo atualizado, inseri na tabela usuario permissao
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
					 * // registrar transa��o
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
	 * @author R�mulo Aur�lio
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
		// Verifica se o campo Usuario Situa��o foi preenchido
		if(usuario.getUsuarioSituacao() == null || usuario.getUsuarioSituacao().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("atencao.Informe_entidade", null, " Situa��o do Usu�rio");
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
								"Usu�rio:" + usuario.getLogin() + " \n " + "Senha:" + usuario.getSenha());

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

		// // [FS0003] Verificar nova situa��o de usuario
		// // Verifica se situcaoUsuario atual � igual a nova
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
	 * M�todo que consulta os grupos do usu�rio
	 * 
	 * @author S�vio Luiz
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
	 * M�todo que consulta os grupos do usu�rio da tabela grupoAcessos
	 * 
	 * @author S�vio Luiz
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
	 * M�todo que consulta as abrang�ncias dos usu�rio pelos os ids das
	 * abrang�ncias superiores e com o id da abrang�ncia diferente do id da
	 * abrang�ncia do usu�rio que est� inserindo(usu�rio logado)
	 * 
	 * @author S�vio Luiz
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
	 * Informa o n�mero total de registros de usuario grupo, auxiliando o
	 * esquema de pagina��o
	 * 
	 * @author S�vio Luiz
	 * @date 30/06/2006
	 * @param Filtro
	 *            da Pesquisa
	 * @param Pacote
	 *            do objeto pesquisado
	 * @return N�mero de registros da pesquisa
	 * @throws ErroRepositorioException
	 *             Exce��o do reposit�rio
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
	 * Informa o n�mero total de registros de usuario grupo, auxiliando o
	 * esquema de pagina��o
	 * 
	 * @author S�vio Luiz
	 * @date 30/06/2006
	 * @param Filtro
	 *            da Pesquisa
	 * @param Pacote
	 *            do objeto pesquisado
	 * @return N�mero de registros da pesquisa
	 * @throws ErroRepositorioException
	 *             Exce��o do reposit�rio
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
	 * M�todo que consulta os grupos funcion�rios opera��es passando os ids dos
	 * grupos
	 * 
	 * @author S�vio Luiz
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
	 * M�todo que consulta os grupos funcion�rios opera��es passando os ids dos
	 * grupos e o id da funcionalidade
	 * 
	 * @author S�vio Luiz
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
	 * M�todo que consulta os usu�rios restrin��o passando os ids dos grupos , o
	 * id da funcionalidade e o id do usu�rio
	 * 
	 * @author S�vio Luiz
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
	 * M�todo que consulta as funcionalidades da(s) funcionalidade(s)
	 * princpial(is)
	 * 
	 * @author S�vio Luiz
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
	 * M�todo que consulta as opera��es da(s) funcionalidade(s)
	 * 
	 * @author S�vio Luiz
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
	 * M�todo que consulta as opera��es da(s) funcionalidade(s) e das
	 * funcionalidades dependencia
	 * 
	 * @author S�vio Luiz
	 * @date 12/07/2006
	 * @author Eduardo Henrique
	 * @date 30/05/2008
	 *       Altera��o no m�todo para evitar loops infinitos para configura��o incorreta de
	 *       depend�ncias de funcionalidades no BD
	 */
	public Collection recuperarOperacoesFuncionalidadesEDependentes(Integer idFuncionalidade) throws ControladorException{

		// cria uma cole��o de funcionalidades principal e inseri o id da
		// funcionalidade na colle��o
		Collection funcionalidadesPrincipal = new ArrayList();
		funcionalidadesPrincipal.add(idFuncionalidade);
		// cria uma cole��o de funcionalidades dependencias que retornar� as
		// funcionalidades dependencias da(s) funcionalidade(s) principal(is)
		Collection funcionalidadesDependencia = null;

		// Map serve de suporte para verificar que funcionalidades j� foram consultadas
		Map<Integer, Integer> mapFuncionalidadesRelacionadas = new HashMap<Integer, Integer>();
		mapFuncionalidadesRelacionadas.put(idFuncionalidade, idFuncionalidade);

		// cria um boolean que vai verificar quando um determinado grupo(ou uma
		// s�) de funcionalidade(s) n�o tem mais dependencias
		boolean terminou = false;
		// caso n�o tenha funcionalidades dependencias ent�o sai do la�o e
		// pesquisa as opera��es das funcionalidades
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
	 * M�todo que marca e desmarca as permiss�es especiais do usu�rio.
	 * 
	 * @author S�vio Luiz
	 * @date 12/07/2006
	 */

	public String[] retornarPermissoesMarcadas(Collection permissoesEspeciais){

		String[] permissaoEspecial = null;
		if(permissoesEspeciais != null && !permissoesEspeciais.isEmpty()){
			// seta os campos de permiss�o especial no form para
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
	 * Retorna 2 cole��es e um array ,com os valores que v�o retornar
	 * marcados,uma com as permiss�es do usu�rio que ele possa marcar/desmarcar
	 * e a outra o usu�rio logado n�o vai poder marcar/desmarcar
	 * [UC0231] - Manter Usu�rio [SB0010] - Selecionar Permiss�es Especiais
	 * (n�2)
	 * 
	 * @author S�vio Luiz
	 * @date 13/07/2006
	 */
	public Object[] pesquisarPermissoesEspeciaisUsuarioEUsuarioLogado(Usuario usuarioAtualizar, Usuario usuarioLogado)
					throws ControladorException{

		Collection colecaoPermissaoEspecial = new ArrayList();
		Collection colecaoPermissaoEspecialDesabilitado = new ArrayList();
		String[] idsPermissaoEspecialMarcado = null;

		Object[] object = new Object[3];
		try{
			// permiss�es especiais do usu�rio que est� sendo atualizado
			Collection colecaoPermissaoEspecialUsuarioAtualizar = repositorioUsuario.pesquisarPermissaoEspecialUsuario(usuarioAtualizar
							.getId());
			// array com os ids das permiss�es que vai ser checado
			idsPermissaoEspecialMarcado = retornarPermissoesMarcadas(colecaoPermissaoEspecialUsuarioAtualizar);

			colecaoPermissaoEspecial.addAll(colecaoPermissaoEspecialUsuarioAtualizar);
			// pesquisa permiss�es especiais do usu�rio que est� logado
			Collection colecaoPermissaoEspecialUsuarioLogado = repositorioUsuario.pesquisarPermissaoEspecialUsuario(usuarioLogado.getId());

			Collection colecaoPermissaoUsuarioLogadoAux = new ArrayList();
			colecaoPermissaoUsuarioLogadoAux.addAll(colecaoPermissaoEspecialUsuarioLogado);
			colecaoPermissaoUsuarioLogadoAux.removeAll(colecaoPermissaoEspecialUsuarioAtualizar);
			colecaoPermissaoEspecial.addAll(colecaoPermissaoUsuarioLogadoAux);

			colecaoPermissaoEspecialUsuarioAtualizar.removeAll(colecaoPermissaoEspecialUsuarioLogado);
			colecaoPermissaoEspecialDesabilitado.addAll(colecaoPermissaoEspecialUsuarioAtualizar);
			colecaoPermissaoEspecial.removeAll(colecaoPermissaoEspecialDesabilitado);

			/*
			 * // pesquisa as permiss�o especial dos usu�rios que n�o v�o //
			 * aparecer marcados Collection
			 * colecaoPermissaoUsuarioLogadoSemMarcado = repositorioUsuario
			 * .pesquisarPermissaoEspecialUsuarioSemPermissoes(
			 * usuarioLogado.getId(), colecaoPermissaoEspecialUsuarioAtualizar); //
			 * adiciona todos os campos na cole��o de permiss�es especiais
			 * colecaoPermissaoEspecial
			 * .addAll(colecaoPermissaoEspecialUsuarioAtualizar);
			 * colecaoPermissaoEspecial
			 * .addAll(colecaoPermissaoUsuarioLogadoSemMarcado); // pesquisa
			 * permiss�es especiais do usu�rio que est� logado Collection
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
	 * marcado(1),desmarcado(2) ou desabilitado(3) para cada opera��o da
	 * funcionalidade escolhida
	 * [UC0231] - Manter Usu�rio [SB0008] - Selecionar Restrin��es (n�2)
	 * 
	 * @author S�vio Luiz
	 * @date 17/07/2006
	 */
	public Map<Integer, Map<Integer, Collection<Operacao>>> organizarOperacoesComValor(Integer codigoFuncionalidade,
					Map<Integer, Map<Integer, Collection<Operacao>>> funcionalidadeMap, Integer[] idsGrupos, Usuario usuarioAtualizar)
					throws ControladorException{

		boolean existeFuncionalidade = false;

		if(funcionalidadeMap != null && !funcionalidadeMap.isEmpty()){

			Collection idsfuncionalidades = funcionalidadeMap.keySet();
			Iterator iteratorFuncionalidades = idsfuncionalidades.iterator();
			// verifica se existe a funcionalidade escolhida na cole��o de
			// funcionalidade
			while(iteratorFuncionalidades.hasNext()){
				Integer idfuncionalidade = (Integer) iteratorFuncionalidades.next();
				if(idfuncionalidade.equals(new Integer(codigoFuncionalidade))){
					existeFuncionalidade = true;
				}
			}
		}
		// caso n�o exista funcionalidade na cole��o de
		// gruposFuncionalidades e a cole��o de ids funcionalidades n�o
		// esteja vazio ent�o pesquisa na base as opera��es que
		// o usu�rio tem acesso
		if(!existeFuncionalidade){

			// Cria a funcionalidade para ser inserido no map
			Map<Integer, Collection<Operacao>> operacoesMap = new HashMap();

			// pesquisa as opera��es na tabela de
			// grupoFuncionalidadeOperacao
			Collection operacoesDaFuncionalidadeGrupo = pesquisarGruposFuncionalidadesOperacoesPelaFuncionalidade(idsGrupos, new Integer(
							codigoFuncionalidade));

			// pesquisa as opera��es na tabela de
			// UsuarioRestrin��o
			Collection operacoesUsuarioComRestrincao = pesquisarUsuarioRestrincao(idsGrupos, new Integer(codigoFuncionalidade),
							usuarioAtualizar.getId());
			// remove todas as opera��es do usu�rio com
			// restrin��o
			// da
			// cole��o de opera��es funcionalidade
			operacoesDaFuncionalidadeGrupo.removeAll(operacoesUsuarioComRestrincao);

			if(operacoesDaFuncionalidadeGrupo != null && !operacoesDaFuncionalidadeGrupo.isEmpty()){
				// coloca as opera��es com o indicador de sele��o igual a
				// 1(checkbox marcados)
				operacoesMap.put(1, operacoesDaFuncionalidadeGrupo);
			}

			if(operacoesUsuarioComRestrincao != null && !operacoesUsuarioComRestrincao.isEmpty()){
				// coloca as opera��es com o indicador de sele��o igual a
				// 2(checkbox desmarcados)
				operacoesMap.put(2, operacoesUsuarioComRestrincao);
			}
			// recupera as opera��es da tabela grupo funcionalidade opera��o e
			// da tabela usu�rio restrin��es pesquisadas anteriormente
			Collection colecaoOperacoesUniao = new ArrayList();

			colecaoOperacoesUniao.addAll(operacoesUsuarioComRestrincao);

			colecaoOperacoesUniao.addAll(operacoesDaFuncionalidadeGrupo);

			Collection colecaoOperacao = recuperarOperacoesFuncionalidadesEDependentes(new Integer(codigoFuncionalidade));
			if(colecaoOperacao != null && !colecaoOperacao.isEmpty()){
				Collection operacoesDesabilitados = new ArrayList();
				Iterator iteratorOperacoes = colecaoOperacao.iterator();

				while(iteratorOperacoes.hasNext()){
					Operacao operacao = (Operacao) iteratorOperacoes.next();
					// caso n�o exista na cole��o de opera��es pesquisadas
					// operacoesUsuarioComRestrincao e
					// operacoesDaFuncionalidadeGrupo ent�o � colocado no map
					// com o
					// indicador de sele��o igual a 3(Desabilitado)
					if(!colecaoOperacoesUniao.contains(operacao)){
						operacoesDesabilitados.add(operacao);
					}
				}
				// coloca as opera��es com o indicador de sele��o igual a
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
	 * marcado(1),desmarcado(2) ou desabilitado(3) para cada opera��o da
	 * funcionalidade escolhida e a cole��o com as opera��es e funcionalidades
	 * que que foram desmarcados
	 * [UC0231] - Manter Usu�rio [SB0008] - Selecionar Restrin��es (n�2)
	 * 
	 * @author S�vio Luiz
	 * @date 17/07/2006
	 */
	public Map<Integer, Map<Integer, Collection<Operacao>>> recuperaFuncionalidadeOperacaoRestrincao(Integer codigoFuncionalidade,
					String[] idsOperacoes, Map<Integer, Map<Integer, Collection<Operacao>>> funcionalidadeMap) throws ControladorException{

		Map<Integer, Collection<Operacao>> operacoesMap = funcionalidadeMap.get(new Integer(codigoFuncionalidade));
		// recupera a cole��o de opera��es que est�o com o indicador de sele��o
		// igual a 1(Marcados)
		Collection operacoesMarcadas = operacoesMap.get(1);
		Collection operacoesDesmarcadasMais = new ArrayList();
		if(operacoesMarcadas != null && !operacoesMarcadas.isEmpty()){
			Iterator iteratorOperacoes = operacoesMarcadas.iterator();
			while(iteratorOperacoes.hasNext()){
				// cria um boolean que verifica se cada opera��o que tinha o
				// indicador igual a 1(Marcada) continua marcada,caso contr�rio
				// remove da cole��o de opera��es marcadas e inseri na cole��o
				// de
				// opera��es n�o marcadas.
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
		// recupera a cole��o de opera��es que est�o com o indicador de sele��o
		// igual a 2(Desmarcados)
		Collection operacoesDesmarcadas = operacoesMap.get(2);
		Collection operacoesMarcadasMais = new ArrayList();
		if(operacoesDesmarcadas != null && !operacoesDesmarcadas.isEmpty()){
			Iterator iteratorOperacoesDesmarcadas = operacoesDesmarcadas.iterator();
			while(iteratorOperacoesDesmarcadas.hasNext()){
				// cria um boolean que verifica se cada opera��o que tinha o
				// indicador igual a 2(Desmarcada) foi marcada,se sim
				// remove da cole��o de opera��es desmarcadas e inseri na
				// cole��o de
				// opera��es marcadas.
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
		// adiciona as novas opera��es marcadas na cole��o de opera��es
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
		// adiciona as novas opera��es desmarcadas na cole��o de opera��es
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
	 * M�todo que verifica a permiss�o do usu�rio a uma determinada funcionalidade
	 * 
	 * @author �talo Almeida
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
	 * M�todo que cria uma cole��o de UsuarioAcesso
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
	 * M�todo que pesquisa as retri��es de hor�rio de acesso ao sistema de um usu�rio
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