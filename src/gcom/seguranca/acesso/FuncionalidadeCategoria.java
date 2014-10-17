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

package gcom.seguranca.acesso;

import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Representa uma categoria de funcionalidade que ser� utilizada no sistema e no
 * menu
 * 
 * @author rodrigo
 */
public class FuncionalidadeCategoria {

	private String nome;

	private boolean acessivel = false;

	// Na cole��o de funcionalidades pode existir tamb�m FuncionalidadesCategoria
	private Collection elementos = new ArrayList();

	/**
	 * Construtor da classe FuncionalidadeCategoria
	 * 
	 * @param nome
	 *            Descri��o do par�metro
	 */
	public FuncionalidadeCategoria(String nome) {

		this.nome = nome;
	}

	/**
	 * Retorna o valor de elementos
	 * 
	 * @return O valor de elementos
	 */
	public Collection getElementos(){

		return elementos;
	}

	/**
	 * Seta o valor de elementos
	 * 
	 * @param elementos
	 *            O novo valor de elementos
	 */
	public void setElementos(Collection elementos){

		this.elementos = elementos;
	}

	/**
	 * Retorna o valor de nome
	 * 
	 * @return O valor de nome
	 */
	public String getNome(){

		return nome;
	}

	/**
	 * Seta o valor de nome
	 * 
	 * @param nome
	 *            O novo valor de nome
	 */
	public void setNome(String nome){

		this.nome = nome;
	}

	// Retorna a inst�ncia da categoria
	// O caminho do menu deve ser separado por /
	/**
	 * <<Descri��o do m�todo>>
	 * 
	 * @param nomeCategoria
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public FuncionalidadeCategoria pesquisarCategoria(String nomeCategoria){

		FuncionalidadeCategoria retorno = null;

		if(nomeCategoria.equals(this.getNome())){
			retorno = this;
		}else{
			Iterator iterator = this.elementos.iterator();

			while(iterator.hasNext()){
				FuncionalidadeCategoria funcionalidadeCategoria = null;
				Object item = iterator.next();

				if(item instanceof Funcionalidade){
					continue;
				}else if(item instanceof FuncionalidadeCategoria){
					funcionalidadeCategoria = (FuncionalidadeCategoria) item;
				}

				if(funcionalidadeCategoria.getNome().equals(nomeCategoria)){
					retorno = funcionalidadeCategoria;
					break;
				}
			}

		}
		return retorno;
	}

	/**
	 * <<Descri��o do m�todo>>
	 * 
	 * @param funcionalidade
	 *            Descri��o do par�metro
	 */
	public void adicionarFuncionalidadeCategoria(Object funcionalidade){

		if(!elementos.contains(funcionalidade)){
			this.elementos.add(funcionalidade);
		}
	}

	/**
	 * Retorna o valor de acessivel
	 * 
	 * @return O valor de acessivel
	 */
	public boolean isAcessivel(){

		return acessivel;
	}

	/**
	 * Seta o valor de acessivel
	 * 
	 * @param acessivel
	 *            O novo valor de acessivel
	 */
	public void setAcessivel(boolean acessivel){

		this.acessivel = acessivel;
	}

}
