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

package gcom.seguranca.acesso;

import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Representa uma categoria de funcionalidade que será utilizada no sistema e no
 * menu
 * 
 * @author rodrigo
 */
public class FuncionalidadeCategoria {

	private String nome;

	private boolean acessivel = false;

	// Na coleção de funcionalidades pode existir também FuncionalidadesCategoria
	private Collection elementos = new ArrayList();

	/**
	 * Construtor da classe FuncionalidadeCategoria
	 * 
	 * @param nome
	 *            Descrição do parâmetro
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

	// Retorna a instância da categoria
	// O caminho do menu deve ser separado por /
	/**
	 * <<Descrição do método>>
	 * 
	 * @param nomeCategoria
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
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
	 * <<Descrição do método>>
	 * 
	 * @param funcionalidade
	 *            Descrição do parâmetro
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
