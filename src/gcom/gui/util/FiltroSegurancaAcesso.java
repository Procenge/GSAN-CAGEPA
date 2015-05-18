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
 */

package gcom.gui.util;

import gcom.cadastro.atendimento.AtendimentoProcedimento;
import gcom.cadastro.atendimento.FiltroAtendimentoProcedimento;
import gcom.fachada.Fachada;
import gcom.seguranca.acesso.Abrangencia;
import gcom.seguranca.acesso.Grupo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesConfig;
import gcom.util.ConstantesSistema;
import gcom.util.FachadaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpStatus;

/**
 * Filtro respons�vel pela seguran�a do sistema verificando se o usu�rio que est�
 * requisitando a funcionalidade ou opera��o, tem acesso ou algum tipo de restri��o
 * 
 * @author Pedro Alexandre
 * @date 20/07/2006
 */
public class FiltroSegurancaAcesso
				extends HttpServlet
				implements Filter {

	// Vari�vel que aramzena as configura��es iniciais do filtro
	private FilterConfig filterConfig;

	private static final long serialVersionUID = 1L;

	/**
	 * Met�do respons�vel por setaas configura��es inicias necess�rias
	 * 
	 * @author Pedro Alexandre
	 * @date 20/07/2006
	 * @param filterConfig
	 */
	public void init(FilterConfig filterConfig){

		this.filterConfig = filterConfig;
	}

	/**
	 * Met�do respons�vel por verificar se o usu�rio tem acesso a funcionalidade ou opera��o
	 * 
	 * @author Pedro Alexandre
	 * @date 20/07/2006
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException{

		try{
			// Faz um cast no request para recuperar a sess�o do usu�rio
			HttpServletRequest requestPagina = ((HttpServletRequest) request);

			// Recupera a url do request
			String enderecoURL = requestPagina.getServletPath();

			// N�o validar seguranca para chamadas do WEBSERVICE
			if(verificarChamadaWebservice(enderecoURL)){
				filterChain.doFilter(request, response);
			}else{
				int port = requestPagina.getServerPort();
				String portaWebService = ConstantesConfig.get("webservice.porta_permitida");
				if(!Util.isVazioOuBranco(portaWebService) && Integer.valueOf(portaWebService).equals(port)){
					HttpServletResponse httpResponse = (HttpServletResponse) response;
					httpResponse.sendError(HttpStatus.SC_FORBIDDEN, "GSAN Web n�o pode ser acessado por esta URL.");
				}else{
					tratarDemaisRequisicoes(request, response, filterChain, requestPagina, enderecoURL);
				}
			}
		}catch(ServletException sx){
			HttpServletRequest requestPagina = ((HttpServletRequest) request);

			// Corre��o da OC0931878 e OC0937300: Problema acontecia apenas com o IE
			if(requestPagina.getServletPath().equals("/encerrarOrdemServicoAction.do")){

				if(sx.getCause() instanceof FachadaException){
					FachadaException e = (FachadaException) sx.getCause();
					e.setUrlBotaoVoltar("/gsan/exibirEncerrarOrdemServicoAction.do?carregarCampos="
									+ "&processoAutorizacaoServicosAssociados=&idServicoTipo=");
					// Corre��o da OC1395832
				}

			}

			throw sx;
		}catch(IOException iox){
			throw iox;
		}
	}

	private void tratarDemaisRequisicoes(ServletRequest request, ServletResponse response, FilterChain filterChain,
					HttpServletRequest requestPagina, String enderecoURL) throws ServletException, IOException{

		// Recupera a sess�o do usu�rio logado
		HttpSession sessao = requestPagina.getSession(false);

		// Recupera o usu�rio que est� logado da sess�o
		Usuario usuarioLogado = null;
		String enderecoURLChamada = enderecoURL;
		if(sessao != null) usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		Abrangencia abrangencia = (Abrangencia) request.getAttribute(Abrangencia.ABRANGENCIA);

		// Recupera a cole��o de grupos que o usu�rio logado pertence
		Collection<Grupo> colecaoGruposUsuario = null;
		if(sessao != null) colecaoGruposUsuario = (Collection) sessao.getAttribute("colecaoGruposUsuario");

		// String parametros = requestPagina.getQueryString();
		// if(parametros != null && !parametros.trim().equals("") &&
		// !parametros.trim().equals("menu=sim")){
		// enderecoURL = enderecoURL + "?" + requestPagina.getQueryString();
		// }

		// Cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		boolean bFlagAtendimentoPreenchimentoOK = false;
		if(sessao.getAttribute(enderecoURL) != null && sessao.getAttribute(enderecoURL).equals("OK")){
			bFlagAtendimentoPreenchimentoOK = true;
			sessao.removeAttribute(enderecoURL);
		}else{
			if(request.getParameter("menuprincipal") == null){
				bFlagAtendimentoPreenchimentoOK = true;
			}else{
				if(!request.getParameter("menuprincipal").toString().toUpperCase().equals("SIM")){
					bFlagAtendimentoPreenchimentoOK = true;
				}
			}
		}


		/*
		 * Caso a url seja de um processo de abas
		 * recupera a url pelo parametro do wizard adicionando o ".do" no final
		 */
		if(enderecoURL.contains("Wizard")){
			enderecoURL = requestPagina.getParameter("action") + ".do";
		}

		// Verifica se a url requisitada pelo usu�rio � uma opera��o ou uma funcionalidade
		Map<Integer, String> maptipoURL = fachada.verificarTipoURL(enderecoURL);
		String tipoURL = null;
		Integer codigoFuncionalidade = null;

		if(maptipoURL != null){
			for(Integer chaveMapTipoURL : maptipoURL.keySet()){
				codigoFuncionalidade = chaveMapTipoURL;

				tipoURL = maptipoURL.get(chaveMapTipoURL);
			}
		}

		// Verifica se existe Procedimento de Atendimentos Associados a esta funcionalidade
		// existindo e chamando a tela de atendimento para validar os itens
		boolean existeAtendimentoProcedimento = false;
		if(tipoURL != null && tipoURL.equalsIgnoreCase("funcionalidade") && !bFlagAtendimentoPreenchimentoOK){
			FiltroAtendimentoProcedimento filtroAtendimentoProcedimento = new FiltroAtendimentoProcedimento();
			filtroAtendimentoProcedimento.adicionarParametro(new ParametroSimples(FiltroAtendimentoProcedimento.INDICADOR_USO,
							ConstantesSistema.SIM));
			filtroAtendimentoProcedimento.adicionarParametro(new ParametroSimples(FiltroAtendimentoProcedimento.FUNCIONALIDADE_ID,
							codigoFuncionalidade));

			Collection<AtendimentoProcedimento> colecaoAtendimentoProcedimento = fachada.pesquisar(filtroAtendimentoProcedimento,
							AtendimentoProcedimento.class.getName());
			if(colecaoAtendimentoProcedimento.size() > 0){
				existeAtendimentoProcedimento = true;
			}
		}

		// Caso o usu�rio esteja logado e n�o tenha clicado no link de logoff
		if(usuarioLogado != null && !enderecoURL.contains("Logoff") && !enderecoURL.contains("Login")
						&& !enderecoURL.contains("telaPrincipal") && !enderecoURL.contains("cancelar")
						&& !enderecoURL.contains("executarBatch") && !enderecoURL.toLowerCase().contains("pesquisar")
						&& !enderecoURL.toLowerCase().contains("relatorio") && !enderecoURL.contains("efetuarAlteracaoSenhaAction")
						&& !enderecoURL.contains("carregarParametrosAction") && !enderecoURL.contains("exibirInformarMelhoriasGcomAction")
						&& !enderecoURL.contains("exibirInformarImovelComentarioAction")
						&& !enderecoURL.contains("informarImovelComentarioAction")
						&& !enderecoURL.contains("exibirAtualizarImovelComentarioAction")
						&& !enderecoURL.contains("atualizarImovelComentarioAction")
						&& !enderecoURL.contains("informarMelhoriasGcomAction")
						&& !enderecoURL.contains("exibirEfetuarAlteracaoSenhaSimplificadaAction")
						&& !enderecoURL.contains("efetuarAlteracaoSenhaSimplificadaAction")
						&& !enderecoURL.contains("exibirConsultarSistemaAlteracaoHistoricoAction")
						&& !enderecoURL.contains("exibirSistemaHistAlteracaoDetalharPopupAction")
						&& !enderecoURL.contains("exibirConsultarDadosPagamentoAction")
						&& !enderecoURL.contains("processarRequisicaoDipositivoMovelAction")
						&& !enderecoURL.contains("servicosManutencaoAction") && !enderecoURL.contains("processarRequisicaoGisAction")
						&& !enderecoURL.contains("processarRequisicaoExtratoDebitoAction")
						&& !enderecoURL.contains("exibirInserirAtendimentoAction") && !enderecoURL.contains("inserirAtendimentoAction")){

			// Caso o tipo da url n�o esteja nulo
			if(tipoURL != null){
				// Caso o usu�rio tenha solicitado uma funcionalidade
				if(tipoURL.equalsIgnoreCase("funcionalidade")){
					/*
					 * Caso usu�rio n�o tenha acesso a funcionalidade
					 * exibe a tela de acesso negado para funcionalidade
					 * Caso contr�rio chama o pr�ximo filtro na fila
					 */
					if(!fachada.verificarAcessoPermitidoFuncionalidade(usuarioLogado, enderecoURL, colecaoGruposUsuario)){
						RequestDispatcher rd = filterConfig.getServletContext().getRequestDispatcher(
										"/jsp/util/acesso_negado_funcionalidade.jsp");
						request.setAttribute("URL", enderecoURL);
						rd.forward(request, response);
					}else{
						if(!existeAtendimentoProcedimento){
							filterChain.doFilter(request, response);
						}else{
							RequestDispatcher rd = filterConfig.getServletContext().getRequestDispatcher(
											"/exibirInserirAtendimentoAction.do");
							request.setAttribute("idFuncionalidade", codigoFuncionalidade);
							request.setAttribute("enderecoURLChamada", enderecoURLChamada);
							rd.forward(request, response);
						}
					}
					// Caso o usu�rio tenha solicitado uma opera��o
				}else if(tipoURL.equalsIgnoreCase("operacao")){
					/*
					 * Caso o usu�rio n�o tenha acesso a opera��o
					 * exibe a tela de acesso negado para opera��o
					 * Caso contr�rio chama o pr�ximo filtro na fila
					 */
					if(!fachada.verificarAcessoPermitidoOperacao(usuarioLogado, enderecoURL, colecaoGruposUsuario)){
						RequestDispatcher rd = filterConfig.getServletContext()
										.getRequestDispatcher("/jsp/util/acesso_negado_operacao.jsp");
						request.setAttribute("URL", enderecoURL);
						rd.forward(request, response);
					}else{
						if(abrangencia != null){
							if(!fachada.verificarAcessoAbrangencia(abrangencia)){
								RequestDispatcher rd = filterConfig.getServletContext().getRequestDispatcher(
												"/jsp/util/acesso_negado_abrangencia.jsp");
								rd.forward(request, response);
							}else{
								if(!existeAtendimentoProcedimento){
									filterChain.doFilter(request, response);
								}else{
									RequestDispatcher rd = filterConfig.getServletContext().getRequestDispatcher(
													"/exibirInserirAtendimentoAction.do");
									request.setAttribute("idFuncionalidade", codigoFuncionalidade);
									request.setAttribute("enderecoURLChamada", enderecoURLChamada);
									rd.forward(request, response);
								}
							}
						}else{
							if(!existeAtendimentoProcedimento){
								filterChain.doFilter(request, response);
							}else{
								RequestDispatcher rd = filterConfig.getServletContext().getRequestDispatcher(
												"/exibirInserirAtendimentoAction.do");
								request.setAttribute("idFuncionalidade", codigoFuncionalidade);
								request.setAttribute("enderecoURLChamada", enderecoURLChamada);
								rd.forward(request, response);
							}
						}
					}
				}
			}else{

				RequestDispatcher rd = filterConfig.getServletContext().getRequestDispatcher("/jsp/util/acesso_negado_funcionalidade.jsp");
				request.setAttribute("URL", enderecoURL);
				rd.forward(request, response);
			}

			// Lista de todas as funcionalidades que podem ser acessadas sem que exista um
			// usuario logado na sessao
		}else if(enderecoURL.contains("Logoff") //
						|| enderecoURL.contains("Login") //
						|| enderecoURL.contains("telaPrincipal") //
						|| enderecoURL.contains("cancelar") //
						|| enderecoURL.contains("executarBatch") //
						|| enderecoURL.toLowerCase().contains("pesquisar") //
						|| enderecoURL.toLowerCase().contains("relatorio") //
						|| enderecoURL.contains("efetuarAlteracaoSenhaAction") //
						|| enderecoURL.contains("carregarParametrosAction") //
						|| enderecoURL.contains("exibirInformarMelhoriasGcomAction") //
						|| enderecoURL.contains("exibirInformarImovelComentarioAction") //
						|| enderecoURL.contains("informarImovelComentarioAction") //
						|| enderecoURL.contains("exibirAtualizarImovelComentarioAction") //
						|| enderecoURL.contains("atualizarImovelComentarioAction")
						|| enderecoURL.contains("informarMelhoriasGcomAction") //
						|| enderecoURL.contains("exibirEfetuarAlteracaoSenhaSimplificadaAction") //
						|| enderecoURL.contains("efetuarAlteracaoSenhaSimplificadaAction") //
						|| enderecoURL.contains("exibirConsultarSistemaAlteracaoHistoricoAction") //
						|| enderecoURL.contains("exibirSistemaHistAlteracaoDetalharPopupAction") //
						|| enderecoURL.contains("exibirLembrarSenhaAction") //
						|| enderecoURL.contains("lembrarSenhaAction") //
						|| enderecoURL.contains("exibirEmitirSegundaViaContaInternetAcessoGeralAction") //
						|| enderecoURL.contains("emitirSegundaViaContaInternetAcessoGeralAction") //
						|| enderecoURL.contains("enviarDadosBancosAcessoGeralAction") //
						|| enderecoURL.contains("exibirSelecionarBancoAcessoGeralAction") //
						|| enderecoURL.contains("exibirSelecionarBancoAction") //
						|| enderecoURL.contains("enviarDadosBancosAction") //
						|| enderecoURL.contains("exibirLogTelaInicialAction") //
						|| enderecoURL.contains("exibirLogTelaFinalAction") //
						|| enderecoURL.contains("exibirConsultarDadosPagamentoAction") //
						|| enderecoURL.contains("processarRequisicaoDipositivoMovelAction") //
						|| enderecoURL.contains("servicosManutencaoAction") //
						|| enderecoURL.contains("processarRequisicaoGisAction") //
						|| enderecoURL.contains("processarRequisicaoExtratoDebitoAction")
						|| enderecoURL.contains("exibirInserirAtendimentoAction") || enderecoURL.contains("inserirAtendimentoAction")){

			if(!existeAtendimentoProcedimento){
				filterChain.doFilter(request, response);
			}else{
				RequestDispatcher rd = filterConfig.getServletContext().getRequestDispatcher("/exibirInserirAtendimentoAction.do");
				request.setAttribute("idFuncionalidade", codigoFuncionalidade);
				request.setAttribute("enderecoURLChamada", enderecoURLChamada);
				rd.forward(request, response);
			}
		}else{

			RequestDispatcher rd = filterConfig.getServletContext().getRequestDispatcher("/jsp/util/acesso_negado_funcionalidade.jsp");
			request.setAttribute("URL", enderecoURL);
			rd.forward(request, response);

			/*
			 * Caso o usu�rio ainda n�o esteja logado ou tenha clicado no
			 * link de logoff chama o pr�ximo filtro na fila
			 */
			// filterChain.doFilter(request, response);
		}
	}

	private boolean verificarChamadaWebservice(String enderecoURL) throws IOException, ServletException{

		boolean isWebserviceCalled = false;
		if(enderecoURL.toLowerCase().startsWith("/agenciavirtual/")){
			isWebserviceCalled = true;
		}else if(enderecoURL.toLowerCase().startsWith("/atendimentoraos/")){
			isWebserviceCalled = true;
		}
		return isWebserviceCalled;
	}

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 05/07/2006
	 */
	public void destroy(){

	}
}
