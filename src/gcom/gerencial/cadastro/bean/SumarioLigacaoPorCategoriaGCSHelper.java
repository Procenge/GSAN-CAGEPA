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
 * 
 * GSANPCG
 * Eduardo Henrique
 */
package gcom.gerencial.cadastro.bean;


/**
 * @author mgrb
 *
 */
public class SumarioLigacaoPorCategoriaGCSHelper {

	private Integer totalizadorId;

	private String totalizador;

	private Integer ligacaoAguaSituacaoId;

	private String ligacaoAguaSituacao;

	private Integer categoriaId;

	private String categoria;

	private Integer qtdLigacoes;

	private Integer qtdEconomias;

	public Integer getTotalizadorId(){

		return totalizadorId;
	}

	public void setTotalizadorId(Integer totalizadorId){

		this.totalizadorId = totalizadorId;
	}

	public String getTotalizador(){

		return totalizador;
	}

	public void setTotalizador(String totalizador){

		this.totalizador = totalizador;
	}

	public Integer getLigacaoAguaSituacaoId(){

		return ligacaoAguaSituacaoId;
	}

	public void setLigacaoAguaSituacaoId(Integer ligacaoAguaSituacaoId){

		this.ligacaoAguaSituacaoId = ligacaoAguaSituacaoId;
	}

	public String getLigacaoAguaSituacao(){

		return ligacaoAguaSituacao;
	}

	public void setLigacaoAguaSituacao(String ligacaoAguaSituacao){

		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
	}

	public Integer getCategoriaId(){

		return categoriaId;
	}

	public void setCategoriaId(Integer categoriaId){

		this.categoriaId = categoriaId;
	}

	public String getCategoria(){

		return categoria;
	}

	public void setCategoria(String categoria){

		this.categoria = categoria;
	}

	public Integer getQtdLigacoes(){

		return qtdLigacoes;
	}

	public void setQtdLigacoes(Integer qtdLigacoes){

		this.qtdLigacoes = qtdLigacoes;
	}

	public Integer getQtdEconomias(){

		return qtdEconomias;
	}

	public void setQtdEconomias(Integer qtdEconomias){

		this.qtdEconomias = qtdEconomias;
	}
}
