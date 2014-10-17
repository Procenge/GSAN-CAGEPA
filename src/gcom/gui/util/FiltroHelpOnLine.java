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

package gcom.gui.util;

import gcom.fachada.Fachada;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.FiltroFuncionalidade;
import gcom.seguranca.acesso.FiltroOperacao;
import gcom.seguranca.acesso.Funcionalidade;
import gcom.seguranca.acesso.FuncionalidadeCorrente;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.Util;
import gcom.util.WebUtil;
import gcom.util.filtro.ComparacaoTexto;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

/**
 * Filtro responsável pela geração do link de ajuda para o usuário. Verificando a funcionalidade
 * e/ou operação para
 * geração do mesmo.
 * 
 * @author isilva
 * @date 11/05/2010
 */
public class FiltroHelpOnLine
				implements Filter {

	private FilterConfig filterConfig;

	private static final String ALTERAR_SENHA = "/exibirEfetuarAlteracaoSenhaSimplificadaAction.do";

	private static final String CONTATO = "/exibirInformarMelhoriasGcomAction.do";

	private static final String LEMBRAR_SENHA = "/exibirLembrarSenhaAction.do";

	private static final String LOGIN = "/efetuarLoginAction.do";

	/**
	 * Metódo responsável por atribuir configurações inicias necessárias
	 * 
	 * @author isilva
	 * @date 11/05/2010
	 * @param filterConfig
	 */
	public void init(FilterConfig filterConfig){

		this.filterConfig = filterConfig;
	}

	/**
	 * Metódo responsável por verificar o id da funcionalidade ou operação
	 * 
	 * @author isilva
	 * @date 11/05/2010
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException{

		try{

			boolean debugar = "true".equals(filterConfig.getInitParameter("Debug"));

			// Faz um cast no request para recuperar a sessão do usuário
			HttpServletRequest requestPagina = ((HttpServletRequest) request);

			// Recupera a sessão do usuário logado
			HttpSession sessao = requestPagina.getSession(false);

			// Recupera a url do request
			String enderecoURL = requestPagina.getServletPath();

			/*
			 * Caso a url seja de um processo de abas recupera a url pelo parametro do wizard
			 * adicionando o ".do"
			 * no final
			 */
			boolean funcionalidadeComWizard = false;
			if(enderecoURL.contains("Wizard")){
				boolean buscarByAction = true;
				funcionalidadeComWizard = true;
				String destino = requestPagina.getParameter("destino");
				if(StringUtils.isNotEmpty(destino)){
					try{
						enderecoURL = WebUtil.getInstancia().retornarUrlDestinoWizard(filterConfig.getServletContext(), enderecoURL,
										destino, requestPagina, (HttpServletResponse) response);
						buscarByAction = false;
					}catch(Exception e){
					}
				}
				if(buscarByAction || StringUtils.isEmpty(enderecoURL)){
					String action = requestPagina.getParameter("action");
					if(StringUtils.isNotEmpty(action)){
						enderecoURL = action + ".do";
					}
				}
			}

			// Alterado por Isilva.
			// Data:12/05/2010
			// verifica se o endereço da funcionalidade existe a palavra tabela auxiliar se existir
			// então
			// acrescenta o parametros ao caminho para que ele seja único.
			if(enderecoURL.contains("TabelaAuxiliar")){ // é uma tabela auxiliar

				if(enderecoURL.indexOf("tela=") == -1){ // nao possui o parametro tela na url

					String tela = (String) sessao.getAttribute("tela");

					if(tela == null) tela = (String) requestPagina.getAttribute("tela");

					if(tela == null) tela = requestPagina.getParameter("tela");

					if(enderecoURL.indexOf("?") != -1){ // ja possui o token ? na url

						enderecoURL += "&tela=" + tela;

					}else{

						enderecoURL += "?tela=" + tela;
					}
				}
			}

			String urlHelp = null;
			String chaveHelp = null;
			String nomeFuncionalidadeHelpOnLine = null;

			Operacao operacao = pesquisarOperacaoPorURL(enderecoURL);

			if(operacao == null){

				RegistradorOperacao.set(null);
				Funcionalidade funcionalidade = pesquisarFuncionalidadePorURL(enderecoURL);

				FuncionalidadeCorrente.set(funcionalidade);

				if(funcionalidade != null){

					urlHelp = funcionalidade.getCaminhoAjuda();

					nomeFuncionalidadeHelpOnLine = " para " + funcionalidade.getDescricao();
				}else if(enderecoURL.equals(LOGIN)){
					urlHelp = "omenuprincipaldosistema.htm";

					nomeFuncionalidadeHelpOnLine = " para Tela Principal";
				}else if(enderecoURL.equals(ALTERAR_SENHA)){
					urlHelp = "alterandoasenhadousurio.htm";

					nomeFuncionalidadeHelpOnLine = " para Alterar Senha";

				}else if(enderecoURL.equals(CONTATO)){
					urlHelp = "contactandoaprocenge.htm";

					nomeFuncionalidadeHelpOnLine = " para Entre em Contato";

				}else if(enderecoURL.equals(LEMBRAR_SENHA)){
					urlHelp = "lembretedesenhadousurio.htm";

					nomeFuncionalidadeHelpOnLine = " para Lembrar Senha";

				}else{
					urlHelp = "acessandoosistema.htm";

					nomeFuncionalidadeHelpOnLine = " para Tela de Login";
				}

			}else{

				FuncionalidadeCorrente.set(operacao.getFuncionalidade());

				urlHelp = operacao.getCaminhoAjuda();

				nomeFuncionalidadeHelpOnLine = " para " + operacao.getDescricao();

				Usuario usuario = (Usuario) requestPagina.getSession(false).getAttribute(Usuario.USUARIO_LOGADO);
				RegistradorOperacao registrador = null;

				if(funcionalidadeComWizard){

					if(operacao.getOperacaoPesquisa() != null){

						registrador = new RegistradorOperacao(operacao.getOperacaoPesquisa().getId(), new UsuarioAcaoUsuarioHelper(usuario,
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
					}else{

						registrador = new RegistradorOperacao(operacao.getId(), new UsuarioAcaoUsuarioHelper(usuario,
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

					}
				}else{

					registrador = new RegistradorOperacao(operacao.getId(), new UsuarioAcaoUsuarioHelper(usuario,
									UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
				}

				RegistradorOperacao.set(registrador);
			}
			request.setAttribute("urlHelpOnLine", urlHelp);
			request.setAttribute("nomeFuncionalidadeHelpOnLine", nomeFuncionalidadeHelpOnLine);
			if(debugar){
				request.setAttribute("chaveUrlHelpOnLine", chaveHelp);
			}

			filterChain.doFilter(request, response);

		}catch(ServletException sx){
			throw sx;
		}catch(IOException iox){
			throw iox;
		}
	}

	/**
	 * @author isilva
	 * @param enderecoURL
	 * @return
	 */
	private Funcionalidade pesquisarFuncionalidadePorURL(String enderecoURL){

		Funcionalidade retorno = null;

		// Cria o filtro para pesquisar a funcionalidade com a url informada
		FiltroFuncionalidade filtroFuncionalidade = new FiltroFuncionalidade();

		String urlEndereco = enderecoURL;
		if(enderecoURL.startsWith("/")){
			urlEndereco = enderecoURL.substring(1);
		}
		filtroFuncionalidade.adicionarParametro(new ComparacaoTexto(FiltroFuncionalidade.CAMINHO_URL, urlEndereco));

		// Pesquisa a funcionalidade com a url informada
		Collection colecaoFuncionalidade = Fachada.getInstancia().pesquisar(filtroFuncionalidade, Funcionalidade.class.getName());

		retorno = (Funcionalidade) Util.retonarObjetoDeColecao(colecaoFuncionalidade);

		return retorno;
	}

	/**
	 * @author isilva
	 * @param enderecoURL
	 * @return
	 */
	private Operacao pesquisarOperacaoPorURL(String enderecoURL){

		Operacao retorno = null;

		// Cria o filtro para pesquisar a operacao com a url informada
		FiltroOperacao filtroOperacao = new FiltroOperacao();

		String urlEndereco = enderecoURL;
		if(enderecoURL.startsWith("/")){
			urlEndereco = enderecoURL.substring(1);
		}
		filtroOperacao.adicionarParametro(new ComparacaoTexto(FiltroOperacao.CAMINHO_URL, urlEndereco));
		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade("funcionalidade");

		// Pesquisa a operacao com a url informada
		Collection colecaoOperacao = Fachada.getInstancia().pesquisar(filtroOperacao, Operacao.class.getName());

		retorno = (Operacao) Util.retonarObjetoDeColecao(colecaoOperacao);

		return retorno;
	}

	/**
	 * <Breve descrição sobre o caso de uso>
	 * 
	 * @author isilva
	 * @date 11/05/2010
	 */
	public void destroy(){

	}
}
