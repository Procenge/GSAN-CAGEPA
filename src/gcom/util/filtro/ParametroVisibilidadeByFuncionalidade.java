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

package gcom.util.filtro;

import gcom.seguranca.acesso.usuario.Usuario;

/**
 * Representa a query de visibilidade pela permissao de funcionalidade do usuario
 * 
 * @author fmarconi
 */
public class ParametroVisibilidadeByFuncionalidade
				extends FiltroParametro {

	private static final long serialVersionUID = -6700369387583320024L;

	protected Usuario usuarioLogado;

	/**
	 * Construtor da classe ParametroSimples
	 * 
	 * @param aliasTabela
	 *            Alias do atributo de que será feita a filtragem
	 * @param nomeAtributo
	 *            Nome do atributo de que será feita a filtragem
	 * @param usuarioLogado
	 *            Usuario logado
	 */
	public ParametroVisibilidadeByFuncionalidade(String nomeAtributo, Usuario usuarioLogado) {

		super(nomeAtributo);
		this.usuarioLogado = usuarioLogado;
	}

	public Usuario getUsuarioLogado(){

		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado){

		this.usuarioLogado = usuarioLogado;
	}

}
