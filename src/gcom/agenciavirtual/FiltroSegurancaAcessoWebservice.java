
package gcom.agenciavirtual;

import gcom.util.ConstantesConfig;
import gcom.util.Util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.log4j.Logger;

/**
 * Implementa a segurança de acesso aos serviços Webservices
 * 
 * @author Marlos Ribeiro
 */
public class FiltroSegurancaAcessoWebservice
				extends HttpServlet
				implements Filter {

	private FilterConfig filterConfig;

	private static final Logger LOGGER = Logger.getLogger(FiltroSegurancaAcessoWebservice.class);

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException{

		request.setCharacterEncoding("ISO-8859-15");
		String ipAddress = Util.recuperarIpCliente(request);

		HttpServletRequest requestPagina = ((HttpServletRequest) request);
		String enderecoURL = requestPagina.getServletPath();
		int port = requestPagina.getServerPort();
		LOGGER.info("GSAN Webservice:[" + ipAddress + ":" + port + "]> acessando: " + enderecoURL);

		if(verificarPermissao(ipAddress, port)){
			filterChain.doFilter(request, response);
		}else{
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendError(HttpStatus.SC_FORBIDDEN, "Os serviços da Agencia Virtual não podem ser acessados por esta URL.");
		}
	}

	private boolean verificarPermissao(String ipAddress, int port){

		// Recupera os IPs configurados como permitidos a ter acesso aos serviços. Ver WEB.XML

		String ipPermitido = ConstantesConfig.get("webservice.ips_permitidos");
		String portaPermitida = ConstantesConfig.get("webservice.porta_permitida");
		if(Util.isVazioOuBranco(ipPermitido) && Util.isVazioOuBranco(portaPermitida)){
			return true;
		}else{
			boolean isIpPermitido = false;
			boolean isPortaPermitida = false;
			if(Util.isVazioOuBranco(ipPermitido)){
				isIpPermitido = true;
			}else{
				Collection ipValidos = Arrays.asList(ipPermitido.split(","));
				isIpPermitido = ipValidos.contains(ipAddress);
			}

			if(Util.isVazioOuBranco(portaPermitida)){
				isPortaPermitida = true;
			}else{
				isPortaPermitida = Integer.valueOf(portaPermitida).equals(port);
			}
			return isIpPermitido && isPortaPermitida;
		}
	}

	public void init(FilterConfig FilterConfig) throws ServletException{

		this.filterConfig = FilterConfig;
	}

}
