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

package gcom.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Representa um problema no n�vel do controlador
 * 
 * @author rodrigo
 */
public class ControladorException
				extends Exception {

	private static final long serialVersionUID = 1L;

	private List<String> parametrosMensagem = new ArrayList<String>();

	private String parametroMensagem;

	/**
	 * Construtor da classe ControladorException
	 * 
	 * @param excecaoCausa
	 *            Exce��o que originou o problema
	 * @param mensagem
	 *            Chave do erro que aconteceu no controlador(mensagem obtida num
	 *            arquivo de properties)
	 */
	public ControladorException(Exception excecaoCausa, String mensagem) {

		super(mensagem, excecaoCausa);
	}

	/**
	 * Construtor da classe ControladorException
	 * 
	 * @param mensagem
	 *            Descri��o do par�metro
	 */
	public ControladorException(String mensagem) {

		this(mensagem, null);
	}

	/**
	 * Construtor da classe ControladorException
	 * 
	 * @param mensagem
	 *            Chave do erro que aconteceu no controlador(mensagem obtida num
	 *            arquivo de properties)
	 * @param excecaoCausa
	 *            Exce��o que originou o problema
	 * @param parametroMensagem
	 *            Descri��o do par�metro
	 */
	public ControladorException(String mensagem, Exception excecaoCausa, String... parametroMensagem) {

		super(mensagem, excecaoCausa);
		this.parametrosMensagem.addAll(Arrays.asList(parametroMensagem));
	}

	public ControladorException(String mensagem, Exception excecaoCausa, String parametroMensagem) {

		super(mensagem, excecaoCausa);
		this.parametroMensagem = parametroMensagem;
	}

	public List<String> getParametroMensagem(){

		ArrayList<String> list = new ArrayList(parametrosMensagem);
		if(parametroMensagem != null && !parametroMensagem.trim().equals("")){
			list.add(parametroMensagem);
		}
		return list;
	}

	public void setParametroMensagem(String... parametrosMensagem){

		this.parametrosMensagem.clear();
		if(parametrosMensagem != null) this.parametrosMensagem.addAll(Arrays.asList(parametrosMensagem));
	}

	/**
	 * Retorna mensagem composta com os seus parametros.
	 * 
	 * @return Mensagem
	 */
	public String getMensagem(){

		if(parametroMensagem != null && !parametroMensagem.trim().equals("")){
			parametrosMensagem.add(parametroMensagem);
		}
		return ConstantesAplicacao.get(super.getMessage(), parametrosMensagem.toArray(new String[parametrosMensagem.size()]));
	}
}
