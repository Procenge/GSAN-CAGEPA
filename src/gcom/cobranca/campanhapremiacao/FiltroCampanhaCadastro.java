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
 * GSANPCG
 * Virgínia Melo
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

package gcom.cobranca.campanhapremiacao;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * FiltroCampanhaCadastro
 * 
 * @author Hiroshi
 * @date 05/09/2013
 */
public class FiltroCampanhaCadastro
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public FiltroCampanhaCadastro(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Construtor da classe FiltroCampanhaCadastro
	 */
	public FiltroCampanhaCadastro() {

	}

	public final static String ID = "id";

	public final static String IMOVEL_ID = "imovel.id";

	public final static String CAMPANHA = "campanha";

	public final static String CAMPANHA_ID = "campanha.id";

	public final static String INDICADOR_COMPROVANTE_BLOQUEADO = "indicadorComprovanteBloqueado";

	public final static String ORGAO_EXPEDIDOR = "orgaoExpedidorRG";

	public final static String UNIDADE_ORGANIZACIONAL = "unidadeOrganizacional";

	public final static String UNIDADE_FEDERACAO = "unidadeFederacao";


}
