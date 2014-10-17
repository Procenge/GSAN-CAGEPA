/* This file is part of GSAN, an integrated service management system for Sanitation
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
 * Eduardo Henrique
 * 
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

package gcom.cobranca;

import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;

import java.util.Date;
import java.util.Set;

/**
 * @author eduardo henrique
 * @date 26/08/2008
 */
public class CobrancaEstagio
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String descricao;

	private Short indicadorUso;

	private Date ultimaAlteracao;

	private Set<CobrancaAcao> cobrancaAcoes;

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	@Override
	public Filtro retornaFiltro(){

		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Default Constructor
	 */
	public CobrancaEstagio() {

	}

	/**
	 * Full Constructor
	 * 
	 * @param id
	 * @param descricaoCobrancaEstagio
	 * @param indicadorUso
	 * @param ultimaAlteracao
	 * @param cobrancaAcoes
	 */
	public CobrancaEstagio(Integer id, String descricao, Short indicadorUso, Date ultimaAlteracao, Set<CobrancaAcao> cobrancaAcoes) {

		this.id = id;
		this.descricao = descricao;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.cobrancaAcoes = cobrancaAcoes;
	}

	/**
	 * @return the id
	 */
	public Integer getId(){

		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id){

		this.id = id;
	}

	/**
	 * @return the descricaoCobrancaEstagio
	 */
	public String getDescricao(){

		return descricao;
	}

	/**
	 * @param descricaoCobrancaEstagio
	 *            the descricaoCobrancaEstagio to set
	 */
	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	/**
	 * @return the indicadorUso
	 */
	public Short getIndicadorUso(){

		return indicadorUso;
	}

	/**
	 * @param indicadorUso
	 *            the indicadorUso to set
	 */
	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	/**
	 * @return the ultimaAlteracao
	 */
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	/**
	 * @param ultimaAlteracao
	 *            the ultimaAlteracao to set
	 */
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	/**
	 * @return the cobrancaAcoes
	 */
	public Set<CobrancaAcao> getCobrancaAcoes(){

		return cobrancaAcoes;
	}

	/**
	 * @param cobrancaAcoes
	 *            the cobrancaAcoes to set
	 */
	public void setCobrancaAcoes(Set<CobrancaAcao> cobrancaAcoes){

		this.cobrancaAcoes = cobrancaAcoes;
	}

}
