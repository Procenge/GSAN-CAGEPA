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
package gcom.util.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Representa um problema inesperado de ambiente
 * 
 * @author fabio.marconi
 */
public class GcomSystemException extends RuntimeException {

	private static final long serialVersionUID = 1670113806031783956L;

	private ArrayList<String> parametrosMensagem = new ArrayList<String>();
	
	private String parametroMensagem;
	
	private String urlBotaoVoltar;

	/**
	 * Construtor da classe GcomSystemException
	 * 
	 * @param mensagem
	 *            Chave do erro que aconteceu no controlador(mensagem obtida num
	 *            arquivo de properties)
	 * @param excecaoCausa
	 *            Exce��o que originou o problema
	 */
	public GcomSystemException(Exception excecaoCausa) {
		super(excecaoCausa);
	}

	/**
	 * Construtor da classe GcomSystemException
	 * 
	 * @param mensagem
	 *            Chave do erro que aconteceu no controlador(mensagem obtida num
	 *            arquivo de properties)
	 * @param excecaoCausa
	 *            Exce��o que originou o problema
	 */
	public GcomSystemException(String mensagem, Exception excecaoCausa) {
		super(mensagem, excecaoCausa);
	}

	/**
	 * Construtor da classe GcomSystemException
	 * 
	 * @param mensagem
	 *            Descri��o do par�metro
	 */
	public GcomSystemException(String mensagem) {
		super(mensagem);
	}

	/**
	 * Construtor da classe GcomSystemException
	 * 
	 * @param mensagem
	 *            Descri��o do par�metro
	 * @param excecaoCausa
	 *            Descri��o do par�metro
	 * @param parametroMensagem
	 *            Descri��o do par�metro
	 */
	public GcomSystemException(String mensagem, Exception excecaoCausa,
			String... parametroMensagem) {
		super(mensagem, excecaoCausa);
		this.parametrosMensagem.addAll(Arrays.asList(parametroMensagem));
	}

	public GcomSystemException(String mensagem, Exception excecaoCausa,
			String parametroMensagem) {
		super(mensagem, excecaoCausa);
		this.parametroMensagem = parametroMensagem;
	}

	public GcomSystemException(String mensagem, String parametroMensagem) {
		super(mensagem);
		this.parametroMensagem = parametroMensagem;
	}

	public GcomSystemException(String mensagem, String... parametroMensagem) {
		super(mensagem);
		this.parametrosMensagem.addAll(Arrays.asList(parametroMensagem));
	}

	/**
	 * Construtor da classe GcomSystemException
	 * 
	 * @param mensagem
	 *            Descri��o do par�metro
	 * @param excecaoCausa
	 *            Descri��o do par�metro
	 * @param url bot�o voltar
	 *            Descri��o do par�metro
	 * @param parametroMensagem
	 *            Descri��o do par�metro
	 */
	public GcomSystemException(String mensagem, String urlBotaoVoltar, 
			Exception excecaoCausa, String... parametroMensagem) {
		super(mensagem, excecaoCausa);
		this.urlBotaoVoltar = urlBotaoVoltar;
		this.parametrosMensagem.addAll(Arrays.asList(parametroMensagem));
	}

	public List<String> getParametrosMensagem() {
		List<String> parametrosMensagem = new ArrayList<String>();
		if (this.parametrosMensagem != null && !this.parametrosMensagem.isEmpty()) {
			parametrosMensagem.addAll(this.parametrosMensagem);
		}
		if (StringUtils.isNotEmpty(this.parametroMensagem)) {
			parametrosMensagem.add(this.parametroMensagem);
		}
		return parametrosMensagem;
	}
	
	public String[] getArrayParametrosMensagem(){
		String[] strParametrosMensagem = null;
		
		if (!this.getParametrosMensagem().isEmpty()){
			
			strParametrosMensagem = new String[this.getParametrosMensagem().size()];
			for (int i = 0; i < this.getParametrosMensagem().size(); i++) {
				strParametrosMensagem[i] = this.getParametrosMensagem().get(i);
			}
		}
		
		return strParametrosMensagem;
	}

	public String getParametroMensagem(int numeroMensagem) {
		return getParametrosMensagem().get(numeroMensagem);
	}

	public void setParametrosMensagem(String... parametroMensagem) {
		this.parametrosMensagem.addAll(Arrays.asList(parametroMensagem));
	}

	public String getUrlBotaoVoltar() {
		return urlBotaoVoltar;
	}

	public void setUrlBotaoVoltar(String urlBotaoVoltar) {
		this.urlBotaoVoltar = urlBotaoVoltar;
	}
	
}
