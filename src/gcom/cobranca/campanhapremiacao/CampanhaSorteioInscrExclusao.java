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

import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;

import java.util.Date;

public class CampanhaSorteioInscrExclusao
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private CampanhaSorteio campanhaSorteio;

	private CampanhaCadastro campanhaCadastro;

	private CampanhaSorteioMotExclusao campanhaSorteioMotExclusao;

	private Date ultimaAlteracao;

	public CampanhaSorteioInscrExclusao() {

		// TODO Auto-generated constructor stub
	}

	@Override
	public Filtro retornaFiltro(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}


	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public CampanhaSorteio getCampanhaSorteio(){

		return campanhaSorteio;
	}

	public void setCampanhaSorteio(CampanhaSorteio campanhaSorteio){

		this.campanhaSorteio = campanhaSorteio;
	}

	public CampanhaCadastro getCampanhaCadastro(){

		return campanhaCadastro;
	}

	public void setCampanhaCadastro(CampanhaCadastro campanhaCadastro){

		this.campanhaCadastro = campanhaCadastro;
	}

	public CampanhaSorteioMotExclusao getCampanhaSorteioMotExclusao(){

		return campanhaSorteioMotExclusao;
	}

	public void setCampanhaSorteioMotExclusao(CampanhaSorteioMotExclusao campanhaSorteioMotExclusao){

		this.campanhaSorteioMotExclusao = campanhaSorteioMotExclusao;
	}

}
