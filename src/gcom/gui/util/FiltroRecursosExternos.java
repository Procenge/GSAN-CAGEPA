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

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

public class FiltroRecursosExternos
				implements Filter {

	public static String DIR_RECURSOS_EXTERNOS = null;

	public static String DIR_ARQUIVOS_PROCESSADOS_ARRECADACAO = null;

	// Vari�vel que aramzena as configura��es iniciais do filtro
	private FilterConfig filterConfig;

	public void init(FilterConfig filterConfig){

		this.filterConfig = filterConfig;
		this.DIR_RECURSOS_EXTERNOS = filterConfig.getInitParameter("Folder-RecursosExternos");
		this.DIR_ARQUIVOS_PROCESSADOS_ARRECADACAO = DIR_RECURSOS_EXTERNOS + "/ARQUIVOS_PROCESSADOS_ARRECADACAO/";
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException{

		String prefixoUrlPattern = "/recursoExterno/";
		String servletPath = ((HttpServletRequest) request).getServletPath();
		String recurso = servletPath.substring(prefixoUrlPattern.length());
		

		FileInputStream fis = null;
		BufferedInputStream bis = null;
		File file = new File(DIR_RECURSOS_EXTERNOS + recurso);

		try{

			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);

			int max = 0;
			byte[] buffer = new byte[1024];
			response.setContentType("text/html; charset=iso-8859-1");
			while((max = bis.read(buffer)) != -1){
				response.getOutputStream().write(buffer, 0, max);
			}

		}catch(FileNotFoundException e){
			// nao faz nada
		}finally{
			close(bis, fis);
		}

	}

	private void close(BufferedInputStream buf, InputStream ins){

		try{
			if(buf != null) buf.close();
		}catch(Exception e){
		}

		try{
			if(ins != null) ins.close();
		}catch(Exception e){
		}

	}

	public void destroy(){

	}

}
