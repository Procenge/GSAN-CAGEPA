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

import gcom.util.ConstantesConfig;
import gcom.util.Util;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpStatus;

/**
 * Filtro respons�vel por verificar se a sess�o do usu�rio expirou o tempo
 * 
 * @author Pedro Alexandre
 * @date 05/07/2006
 */
public class FiltroSessaoExpirada
				extends HttpServlet
				implements Filter {

	// Vari�vel que vai armazenar a configura��o do filtro
	private FilterConfig filterConfig;

	private static final long serialVersionUID = 1L;

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 05/07/2006
	 * @param filterConfig
	 */
	public void init(FilterConfig filterConfig){

		this.filterConfig = filterConfig;
	}

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 05/07/2006
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
					tratarDemaisRestricoes(request, response, filterChain, requestPagina, enderecoURL);
				}
			}

		}catch(ServletException sx){
			throw sx;
		}catch(IOException iox){
			throw iox;
		}
	}

	private boolean verificarChamadaWebservice(String enderecoURL) throws IOException, ServletException{

		boolean isWebserviceCalled = false;
		if(enderecoURL.toLowerCase().startsWith("/agenciavirtual/") || enderecoURL.toLowerCase().startsWith("/atendimentoraos/")){
			isWebserviceCalled = true;
		}
		return isWebserviceCalled;
	}

	private void tratarDemaisRestricoes(ServletRequest request, ServletResponse response, FilterChain filterChain,
					HttpServletRequest requestPagina, String enderecoURL) throws ServletException, IOException{

		// Caso a sess�o esteja nula(expirou) redireciona o usu�rio para a
		// p�gina de sess�o expirada
		// Caso contr�rio chama o pr�ximo filtro do web.xml se existir
		HttpSession sessao = requestPagina.getSession(false);
		if(sessao == null
						&& (!enderecoURL.contains("exibirEmitirSegundaViaContaInternetAcessoGeralAction")
										&& !enderecoURL.contains("emitirSegundaViaContaInternetAcessoGeralAction")
										&& !enderecoURL.contains("enviarDadosBancosAcessoGeralAction")
										&& !enderecoURL.contains("exibirSelecionarBancoAction")
										&& !enderecoURL.contains("enviarDadosBancosAction")
										&& !enderecoURL.contains("exibirSelecionarBancoAcessoGeralAction")
										&& !enderecoURL.contains("exibirLogTelaInicialAction")
										&& !enderecoURL.contains("exibirLogTelaFinalAction")
										&& !enderecoURL.contains("processarRequisicaoDipositivoMovelAction")
										&& !enderecoURL.contains("processarRequisicaoGisAction")
										&& !enderecoURL.contains("processarRequisicaoExtratoDebitoAction")
										&& !enderecoURL.toLowerCase().startsWith("/agenciavirtual/") && !enderecoURL.toLowerCase()
										.startsWith("/atendimentoraos/")

						)){

			RequestDispatcher rd = filterConfig.getServletContext().getRequestDispatcher("/jsp/util/sessao_expirada.jsp");
			rd.forward(request, response);
		}else{
			if(enderecoURL.contains("exibirEmitirSegundaViaContaInternetAcessoGeralAction")){
				// Cria uma sessao temporaria para o usuario que entra no
				// EmitirSegundaViaContaInternet sem logar no sistema
				sessao = requestPagina.getSession(true);

			}
			filterChain.doFilter(request, response);
		}
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
