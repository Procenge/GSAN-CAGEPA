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

package gcom.gui.seguranca.acesso;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.FiltroGrupoFuncionalidadeOperacao;
import gcom.seguranca.acesso.Funcionalidade;
import gcom.seguranca.acesso.FuncionalidadeCategoria;
import gcom.seguranca.acesso.Grupo;
import gcom.seguranca.acesso.GrupoFuncionalidadeOperacao;
import gcom.seguranca.acesso.GrupoFuncionalidadeOperacaoPK;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.FiltroUsuarioFavorito;
import gcom.seguranca.acesso.usuario.FiltroUsuarioGrupoRestricao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioFavorito;
import gcom.seguranca.acesso.usuario.UsuarioGrupoRestricao;
import gcom.seguranca.acesso.usuario.UsuarioSituacao;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.FiltroParametro;
import gcom.util.filtro.ParametroSimples;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esse action valida o usuário e coloca as informações na sessão, todo o acesso
 * terá que passar obrigatoriamente por aqui primeiro
 * 
 * @author Pedro Alexandre
 * @date 05/07/2006
 */
public class EfetuarLoginAction
				extends GcomAction {

	/**
	 * [UC0287] - Efetuar Login
	 * 
	 * @author Pedro Alexandre
	 * @date 04/07/2006
	 * @author eduardo henrique
	 * @date 24/11/2008
	 *       Alteração para verificação caso o usuário não pertença a nenhum grupo de usuário.
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Prepara o retorno da ação para a tela principal
		ActionForward retorno = actionMapping.findForward("telaPrincipal");

		// Recupera o ActionForm
		EfetuarLoginActionForm loginActionForm = (EfetuarLoginActionForm) actionForm;

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		// Recupera o objeto que contém os parâmetros do sistema
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		// instacia a variavel de aplicacao tituloPagina com o valor cadastrado em sistema
		// parametro.
		/*
		 * if (servlet.getServletContext().getAttribute("tituloPagina") == null ||
		 * servlet.getServletContext().getAttribute("tituloPagina").equals("")){
		 * servlet.getServletContext().setAttribute("tituloPagina",
		 * sistemaParametro.getTituloPagina());
		 * }
		 */

		// Variável que vai armazenar o usuário logado
		Usuario usuarioLogado = null;

		// Recupera o login e a senha do usuário
		String login = loginActionForm.getLogin();
		String senha = loginActionForm.getSenha();

		// [FS0003] - Verificar preenchimento do login
		if(login == null || login.trim().equals("")){
			this.reportarErros(httpServletRequest, "atencao.login.invalido");
			retorno = actionMapping.findForward("telaLogin");
		}else{

			// Cria a variável que vai armazenar a mensagem com a quantidade de
			// dias que falta para expirar a validade da senha
			String mensagemExpiracao = "";

			// [FS0001] - Verificar existência do login
			if(!this.verificarExistenciaLogin(login)){
				this.reportarErrosMensagem(httpServletRequest, "atencao.login.inexistente", login);
				retorno = actionMapping.findForward("telaLogin");
			}else{

				// Cria uma instancia da sessão
				HttpSession sessao = httpServletRequest.getSession(true);

				// [FS0004] - Validar senha do login
				// Busca o usuário no sistema, o usuário será nulo se não
				// existir
				usuarioLogado = fachada.validarUsuario(login, senha);

				// [FS0005] - Verificar número de tentativas.
				Integer numeroTentativas = (Integer) sessao.getAttribute("numeroTentativas");
				Short numeroTentativasPermitidas = sistemaParametro.getNumeroMaximoLoginFalho();
				if(numeroTentativas == null){
					numeroTentativas = new Integer(0);
					sessao.setAttribute("numeroTentativas", numeroTentativas);
				}

				// Recupera o login do usuário da sessão
				String loginUsuarioSessao = (String) sessao.getAttribute("loginUsuarioSessao");

				// Caso seja a primeira vez que o usuário esteja logando
				// joga o login do usuário na sessão
				if(loginUsuarioSessao == null){
					loginUsuarioSessao = login;
					sessao.setAttribute("loginUsuarioSessao", loginUsuarioSessao);
				}

				// Caso o usuário não esteja cadastrado, manda o erro para a
				// página de login
				if(usuarioLogado == null){
					this.reportarErros(httpServletRequest, "atencao.usuario.inexistente");
					retorno = actionMapping.findForward("telaLogin");

					/*
					 * Caso o login informado seja igual ao que está na sessão
					 * incrementa o nº de tentativas e joga esse nº na sessão
					 * verifica se o nº de tentativas é maior que a permitida se
					 * for bloqueia a senha do usuário e indica o erro na página
					 * de login
					 */
					if(loginUsuarioSessao.equals(login)){
						numeroTentativas = numeroTentativas + 1;
						sessao.setAttribute("numeroTentativas", numeroTentativas);

						// [FS0005] - Verificar número de tentativas de acesso
						if(numeroTentativas.intValue() > numeroTentativasPermitidas.intValue()){
							this.bloquearSenha(login);
							this.reportarErros(httpServletRequest, "atencao.usuario.senha.bloqueada");
							retorno = actionMapping.findForward("telaLogin");
						}
					}else{
						// Zera o nº de tentativas de acesso e joga o login do usuário na sessão
						numeroTentativas = 0;
						sessao.setAttribute("loginUsuarioSessao", login);
					}

				}else{
					// [FS0002] - Verificar situação do usuário
					if(!this.verificarSituacaoUsuario(usuarioLogado)){
						if(usuarioLogado.getUsuarioSituacao().getId().equals(UsuarioSituacao.INATIVO)){
							throw new ActionServletException("atencao.usuario_invalido", null, usuarioLogado.getLogin());
						}else{
							this.reportarErrosMensagem(httpServletRequest, "atencao.usuario.situacao.invalida", login
											+ " está com situação correspondente a "
											+ usuarioLogado.getUsuarioSituacao().getDescricaoAbreviada());
							retorno = actionMapping.findForward("telaLogin");
						}
					}

					// [SB0005] Efetuar Controle de Alteração de Senha
					boolean disponibilizarAlteracaoSenha = false;
					Date dataExpiracaoAcesso = usuarioLogado.getDataExpiracaoAcesso();
					UsuarioSituacao usuarioSituacao = usuarioLogado.getUsuarioSituacao();

					// Caso a data de expiração de acesso esteja preenchida e seja menor
					// que a data atual disponibiliza a tela de alteração de senha
					if(dataExpiracaoAcesso != null){
						if(dataExpiracaoAcesso.before(new Date())){
							disponibilizarAlteracaoSenha = true;
						}
					}

					// Caso a situação da senha do usuário seja igual a "pendente"
					// disponibiliza a tela de alteração de senha
					if(usuarioSituacao.getId().equals(UsuarioSituacao.PENDENTE_SENHA)){
						disponibilizarAlteracaoSenha = true;
					}

					// Caso a flag de disponibilizar alteração de senha esteja "true"
					// seta o mapeamento para a tela de alterar senha
					if(disponibilizarAlteracaoSenha){
						retorno = actionMapping.findForward("alterarSenha");
						// Adiciona o usuário logado na sessão
						sessao.setAttribute("usuarioLogado", usuarioLogado);
					}else{
						// Caso a flag de disponibilizar alteração da senha esteja setada para
						// "false"
						// recupera a data da mensagem de expiração
						Date dataPrazoMensagemExpiracao = usuarioLogado.getDataPrazoMensagemExpiracao();

						// Caso a data de validade da mensagem de expiração esteja preenchida
						// recupera o nº de dias que falta para expirar a semha do usuário
						// Caso contrário indica que a senha do usuário expira hoje
						if(dataPrazoMensagemExpiracao != null){
							if(dataExpiracaoAcesso.after(new Date())){
								Integer numeroDiasParaExpirar = Util.obterQuantidadeDiasEntreDuasDatas(new Date(), dataExpiracaoAcesso);
								mensagemExpiracao = "Sua senha expira dentro de " + numeroDiasParaExpirar + " dia(s).";
							}else{
								mensagemExpiracao = "Sua senha expira hoje.";
							}
						}
					}

					// Buscar as permissões do(s) grupo(s) do usuário
					FiltroGrupoFuncionalidadeOperacao filtroGrupoFuncionalidadeOperacao = new FiltroGrupoFuncionalidadeOperacao();
					filtroGrupoFuncionalidadeOperacao.setCampoOrderBy(FiltroGrupoFuncionalidadeOperacao.FUNCIONALIDADE_NUMERO_ORDEM_MENU);
					filtroGrupoFuncionalidadeOperacao.setConsultaSemLimites(true);

					// Pesquisa os grupos do usuário
					Collection colecaoGruposUsuario = fachada.pesquisarGruposUsuario(usuarioLogado.getId());

					// verifica se o usuário pertence a algum grupo
					if(colecaoGruposUsuario == null || colecaoGruposUsuario.isEmpty()){
						throw new ActionServletException("atencao.usuario_sem_grupo", null, usuarioLogado.getLogin());
					}

					// Seta na sessão os grupos aos que o usuário pertence
					sessao.setAttribute("colecaoGruposUsuario", colecaoGruposUsuario);

					// Cria o iterator dos grupos do usuário logado
					Iterator iterator = colecaoGruposUsuario.iterator();

					// Laço para adicionar todos os id's dos grupos no filtro
					// para pesquisar os acessos de todos os grupos do usuário
					// logado
					while(iterator.hasNext()){
						Grupo grupoUsuario = (Grupo) iterator.next();
						filtroGrupoFuncionalidadeOperacao.adicionarParametro(new ParametroSimples(
										FiltroGrupoFuncionalidadeOperacao.GRUPO_ID, grupoUsuario.getId(), FiltroParametro.CONECTOR_OR));
					}

					/*
					 * Pesquisa todas as permissões do usuário
					 * pesquisa ocorrência na tabela GrupoFuncionalidadeOperacao para os grupos
					 * aos quais o usuário logado pertence
					 */
					Collection permissoes = fachada.pesquisar(filtroGrupoFuncionalidadeOperacao, GrupoFuncionalidadeOperacao.class
									.getName());

					/*
					 * Pesquisa todas as restrições do usuário
					 * pesquisa se existe ocorrência na tabela UsuarioGrupoRestricao para o usuário
					 * que está logado.
					 */
					Collection restricoes = this.pesquisarRestricaoUsuario(usuarioLogado);

					// Caso exista restrições para o usuário logado
					// remove todas as funcionalidades que o usuário não tem acesso
					if(restricoes != null && !restricoes.isEmpty()){
						permissoes.removeAll(restricoes);
					}

					// Obtém a árvore de funcionalidades do sistema
					FuncionalidadeCategoria arvoreFuncionalidades = fachada.pesquisarArvoreFuncionalidades(permissoes);

					// Coloca a árvore de funcionalidades/permissões na sessão
					// para
					// futuras consultas
					sessao.setAttribute("arvoreFuncionalidades", arvoreFuncionalidades);

					// Adiciona o usuário logado na sessão
					sessao.setAttribute("usuarioLogado", usuarioLogado);

					// Adiciona os parâmetros do sistema na sessão
					sessao.setAttribute(SistemaParametro.SISTEMA_PARAMETRO, sistemaParametro);

					// Cria uma instância do menu para gerar a arvóre do menu
					MenuGCOM menu = new MenuGCOM();
					String menuGerado = menu.gerarMenu(arvoreFuncionalidades);

					// Coloca o menu gerado na sessão
					sessao.setAttribute("menuGCOM", menuGerado);

					// Seta o tempo máximo que o usuário tem para expirar sua
					// sessão
					// caso nenhuma requisição seja feita em 1000(mil) segundos
					sessao.setMaxInactiveInterval(3600);

					/*
					 * Recupera a data do último acesso do usuário caso seja a
					 * primeira vez que o usuário acesse a aplicação cria uma
					 * nova data e seta essa data na sessão para a página de
					 * informaçãoes do usuário
					 */
					Date data = usuarioLogado.getUltimoAcesso();
					if(data == null){
						data = new Date();
					}
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
					String ultimaDataAcesso = formatter.format(data);
					sessao.setAttribute("dataUltimoAcesso", ultimaDataAcesso);

					// Cria a data atual e seta essa data na sessão
					data = new Date();
					SimpleDateFormat formatterDataAtual = new SimpleDateFormat("dd/MM/yyyy");
					String dataAtual = formatterDataAtual.format(data);
					sessao.setAttribute("dataAtual", dataAtual);

					// Coloca na sessão a mensagem informando quantos dias falta para
					// a senha do usuário expirar
					sessao.setAttribute("mensagemExpiracao", mensagemExpiracao);

					/*
					 * Cria a lista de últimos acessos do usuário logado
					 * e seta essa lista html na sessão para ser recuperada
					 * na página de informações do usuário
					 */
					String ultimosAcessos = this.construirUltimosAcessos(usuarioLogado);
					sessao.setAttribute("ultimosAcessos", ultimosAcessos);

					// Registra o acesso do usuário no sistema
					fachada.registrarAcessoUsuario(usuarioLogado);
				}
			}
		}
		return retorno;
	}

	/**
	 * Verifica se o login informado existe para algum usuário do sistema
	 * retorna true se existir caso contrário retorna false.
	 * [UC0287] - Verificar existência do login
	 * 
	 * @author Pedro Alexandre
	 * @date 06/07/2006
	 * @param login
	 * @return
	 */
	private boolean verificarExistenciaLogin(String login){

		// Inicializa o retorno para falso(login não existe)
		boolean retorno = false;

		// Cria o filtro e pesquisa o usuário com o login informado
		FiltroUsuario filtroUsuario = new FiltroUsuario();
		filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.LOGIN, login));
		Collection usuarios = Fachada.getInstancia().pesquisar(filtroUsuario, Usuario.class.getName());

		// Caso exista o usuário com o login informado
		// seta o retorno para verdadeiro(login existe no sistema)
		if(usuarios != null && !usuarios.isEmpty()){
			retorno = true;
		}
		// Retorna um indicador se o login informado existe ou não no sistema
		return retorno;
	}

	/**
	 * Metódo que verifica se a situação do usuário é diferente de ativo ou se é
	 * igual a senha pendente.Caso seja uma ou outra situação levanta uma
	 * exceção para o usuário indicando que o usuário não pode se logar ao
	 * sistema.
	 * [FS0002] - Verificar situação do usuário
	 * 
	 * @author Pedro Alexandre
	 * @date 06/07/2006
	 * @param usuarioLogado
	 * @return
	 */
	private boolean verificarSituacaoUsuario(Usuario usuarioLogado){

		boolean retorno = true;
		// Recupera a situação do usuário
		UsuarioSituacao usuarioSituacao = usuarioLogado.getUsuarioSituacao();

		/*
		 * Caso a situação do usuário não seja igual a ativo ou seja igual a
		 * pendente retorna uma flag indicando que o usuário não pode acessar o
		 * sistema
		 */
		if((!usuarioSituacao.getId().equals(UsuarioSituacao.ATIVO)) && (!usuarioSituacao.getId().equals(UsuarioSituacao.PENDENTE_SENHA))){
			retorno = false;
		}

		// Retorna uma flag indicando se a situção do usuário permite o acesso
		// ao sistema
		return retorno;
	}

	/**
	 * Bloqueia a senha do usuário depois de o números de tentativas de acesso
	 * exceder o número máximo de tentativas permitidas
	 * [FS0005] - Verificar número de tentativas de acesso
	 * 
	 * @author Pedro Alexandre
	 * @date 06/07/2006
	 * @param usuarioLogado
	 */
	private void bloquearSenha(String login){

		// Pesquisa o usário que vai ser bloqueada sua senha
		FiltroUsuario filtroUsuario = new FiltroUsuario();
		filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.LOGIN, login));
		Collection usuarios = Fachada.getInstancia().pesquisar(filtroUsuario, Usuario.class.getName());

		// Caso encontre o usuário com o login informado
		if(usuarios != null && !usuarios.isEmpty()){
			// Recupera o usuário
			Usuario usuarioLogado = (Usuario) usuarios.iterator().next();

			// Atualiza a situação do usuário para bloqueada
			UsuarioSituacao usuarioSituacao = new UsuarioSituacao();
			usuarioSituacao.setId(UsuarioSituacao.SENHA_BLOQUEADA);

			// Recupera o nº de vezes que o usuário foi bloqueado
			Short bloqueioAcesso = usuarioLogado.getBloqueioAcesso();

			/*
			 * Caso o usuário nunca tenha sido bloqueado seta o nº de bloqueios
			 * para 1(um) caso contrário incrementa o valor do nº de bloqueio do
			 * usuário
			 */
			if(bloqueioAcesso == null){
				usuarioLogado.setBloqueioAcesso(new Short("1"));
			}else{
				usuarioLogado.setBloqueioAcesso((new Integer(usuarioLogado.getBloqueioAcesso() + 1)).shortValue());
			}

			// Atualiza os dados do usuário
			usuarioLogado.setUsuarioSituacao(usuarioSituacao);
			usuarioLogado.setUltimaAlteracao(new Date());
			Fachada.getInstancia().atualizar(usuarioLogado);
		}
	}

	/**
	 * Pesquisa as retrições que o usuário tem.
	 * pesquisa se existe ocorrência na tabela UsuarioGrupoRestricao
	 * <Identificador e nome do fluxo secundário>
	 * 
	 * @author Pedro Alexandre
	 * @date 17/07/2006
	 * @param usuarioLogado
	 * @return
	 */
	private Collection<GrupoFuncionalidadeOperacao> pesquisarRestricaoUsuario(Usuario usuarioLogado){

		// Cria a coleção que vai armazenar as restrições do usuário
		Collection<GrupoFuncionalidadeOperacao> restricoes = new ArrayList();

		// Cria o filtro para pesquisar todas as restrições do usuário no sistema
		FiltroUsuarioGrupoRestricao filtroUsuarioGrupoRestricao = new FiltroUsuarioGrupoRestricao();
		filtroUsuarioGrupoRestricao.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupoRestricao.USUARIO_ID, usuarioLogado.getId()));
		filtroUsuarioGrupoRestricao.setConsultaSemLimites(true);

		Collection colecaoUsuarioRestricoes = Fachada.getInstancia().pesquisar(filtroUsuarioGrupoRestricao,
						UsuarioGrupoRestricao.class.getName());

		// Caso exista restrição para o usuário
		// Existe ocorrência para o id do usuário logado na tabela UsuarioGrupoRestricao
		if(colecaoUsuarioRestricoes != null && !colecaoUsuarioRestricoes.isEmpty()){

			// Cria o iterator das restrições do usuário
			Iterator<UsuarioGrupoRestricao> iterator = colecaoUsuarioRestricoes.iterator();

			while(iterator.hasNext()){

				// Recupera a restrição do iterator
				UsuarioGrupoRestricao usuarioGrupoRestricao = iterator.next();

				// Recupera qual a funcionalidade e sua operação a qual o usuário tem restrição
				// para um determinado grupo
				GrupoFuncionalidadeOperacao grupoFuncionalidadeOperacao = new GrupoFuncionalidadeOperacao();
				GrupoFuncionalidadeOperacaoPK grupoFuncionalidadeOperacaoPK = new GrupoFuncionalidadeOperacaoPK();
				grupoFuncionalidadeOperacaoPK.setGrupoId(usuarioGrupoRestricao.getComp_id().getGrupoId());
				grupoFuncionalidadeOperacaoPK.setFuncionalidadeId(usuarioGrupoRestricao.getComp_id().getFuncionalidadeId());
				grupoFuncionalidadeOperacaoPK.setOperacaoId(usuarioGrupoRestricao.getComp_id().getOperacaoId());
				grupoFuncionalidadeOperacao.setComp_id(grupoFuncionalidadeOperacaoPK);

				// Adiciona a operação a coleção de restrição
				restricoes.add(grupoFuncionalidadeOperacao);
			}
		}

		// Retorna a coleção de restrições do sistema
		return restricoes;
	}

	/**
	 * Metódo responsável por criar um list com as funcionalidades últimas acessadas
	 * pelo usuário
	 * 
	 * @author Pedro Alexandre
	 * @date 08/08/2006
	 * @param usuarioLogado
	 * @return
	 */
	private String construirUltimosAcessos(Usuario usuarioLogado){

		// Cria a variável que vai armazenar a string contendo o html do list com
		// os últimos acessos do usuário
		String retorno = null;
		StringBuilder ultimosAcessos = new StringBuilder();

		// Cria o filtro para pesquisar as últimas funcionalidades acessadas pelo usuário
		FiltroUsuarioFavorito filtroUltimosAcessos = new FiltroUsuarioFavorito();
		filtroUltimosAcessos.adicionarParametro(new ParametroSimples(FiltroUsuarioFavorito.USUARIO_ID, usuarioLogado.getId()));
		filtroUltimosAcessos.adicionarParametro(new ParametroSimples(FiltroUsuarioFavorito.INDICADOR_FAVORITO_ULTIMO_ACESSADO,
						ConstantesSistema.SIM));
		filtroUltimosAcessos.adicionarCaminhoParaCarregamentoEntidade("funcionalidade");
		Collection<UsuarioFavorito> colecaoUltimosAcessos = new ArrayList();
		colecaoUltimosAcessos = Fachada.getInstancia().pesquisar(filtroUltimosAcessos, UsuarioFavorito.class.getName());

		/*
		 * Bloco para recurepar o título das páginas do Sistema
		 * O resultado será setado na variavel de Aplicação: tituloSistema
		 * Autor: Tiago Moreno - 10/11/2006
		 */
		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();
		String tituloPagina = sistemaParametro.getTituloPagina();

		if(tituloPagina != null && !tituloPagina.equalsIgnoreCase("")){

		}else{

		}

		/*
		 * Caso a coleção de últimos acessos não esteja vazia
		 * cria a lista contendo os últimos acessos do usuário
		 * Caso contrário cria uma lista com nenhuma funcionalidade
		 * para último acessos
		 */
		if(colecaoUltimosAcessos != null && !colecaoUltimosAcessos.isEmpty()){

			/*
			 * Trecho para cria a parte inicial do list html
			 */
			ultimosAcessos
							.append("<select name=\"ultimoacesso\" id=\"ultimosacessos\" onchange=\"javascript:abrirUltimoAcesso();\" style=\"width:142px\">");
			ultimosAcessos.append(System.getProperty("line.separator"));
			ultimosAcessos.append("<option value=\"-1\">Ultimos Acessos</option>");
			ultimosAcessos.append(System.getProperty("line.separator"));
			ultimosAcessos.append("<option value=\"-1\">- - - - - - - - - - - - - - - - - - -</option>");

			// Laço para adicionar as funcionalidades a lista de últimos acessos
			for(UsuarioFavorito usuarioFavorito : colecaoUltimosAcessos){
				Funcionalidade funcionalidade = usuarioFavorito.getFuncionalidade();
				ultimosAcessos.append("<option value=\"" + funcionalidade.getCaminhoUrl() + "\" title=\"" + funcionalidade.getDescricao()
								+ "\">" + funcionalidade.getDescricao() + "</option>");
				ultimosAcessos.append(System.getProperty("line.separator"));
			}

			// Fecha o list html
			ultimosAcessos.append("</select>");
			retorno = ultimosAcessos.toString();
		}else{

			/*
			 * Cria o list html sem nenhuma funcionalidade dentro
			 */
			ultimosAcessos
							.append("<select name=\"ultimoacesso\" id=\"ultimosacessos\" onchange=\"javascript:abrirUltimoAcesso();\" style=\"width:142px\">");
			ultimosAcessos.append(System.getProperty("line.separator"));
			ultimosAcessos.append("<option value=\"-1\"><font size=\"1\">Ultimos Acessos</font></option>");
			ultimosAcessos.append(System.getProperty("line.separator"));
			ultimosAcessos.append("<option value=\"-1\">- - - - - - - - - - - - - - - - - - -</option>");
			ultimosAcessos.append("</select>");
			retorno = ultimosAcessos.toString();
		}

		// Retorna o html de uma lista contendo as funcionalidades últimas acessadas pelo usuário
		return retorno;
	}
}
