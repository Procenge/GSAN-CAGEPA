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

package gcom.relatorio.cobranca;

import gcom.relatorio.RelatorioBean;

import java.util.Collection;

public class RelatorioContasAReceberCorrigidoCategoriasBean
				implements RelatorioBean {

	private String tipoQuebra;

	private String valorQuebra;

	private String subTitulo;

	private String categoria;

	private Collection<RelatorioContasAReceberCorrigidoCategoriasDetalhesBean> CategoriaDetalhes;

	/**
	 * @return the categoria
	 */
	public String getCategoria(){

		return categoria;
	}

	/**
	 * @param categoria
	 *            the categoria to set
	 */
	public void setCategoria(String categoria){

		this.categoria = categoria;
	}

	/**
	 * @return the tipoQuebra
	 */
	public String getTipoQuebra(){

		return tipoQuebra;
	}

	/**
	 * @param tipoQuebra
	 *            the tipoQuebra to set
	 */
	public void setTipoQuebra(String tipoQuebra){

		this.tipoQuebra = tipoQuebra;
	}

	/**
	 * @return the valorQuebra
	 */
	public String getValorQuebra(){

		return valorQuebra;
	}

	/**
	 * @param valorQuebra
	 *            the valorQuebra to set
	 */
	public void setValorQuebra(String valorQuebra){

		this.valorQuebra = valorQuebra;
	}

	/**
	 * @return the categoriaDetalhes
	 */
	public Collection<RelatorioContasAReceberCorrigidoCategoriasDetalhesBean> getCategoriaDetalhes(){

		return CategoriaDetalhes;
	}

	/**
	 * @param categoriaDetalhes
	 *            the categoriaDetalhes to set
	 */
	public void setCategoriaDetalhes(Collection<RelatorioContasAReceberCorrigidoCategoriasDetalhesBean> categoriaDetalhes){

		CategoriaDetalhes = categoriaDetalhes;
	}

	/**
	 * @return the subTitulo
	 */
	public String getSubTitulo(){

		return subTitulo;
	}

	/**
	 * @param subTitulo
	 *            the subTitulo to set
	 */
	public void setSubTitulo(String subTitulo){

		this.subTitulo = subTitulo;
	}

}
