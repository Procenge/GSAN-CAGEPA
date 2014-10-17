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

package gcom.cobranca.programacobranca;

import gcom.cobranca.CobrancaCriterio;
import gcom.interceptor.ObjetoGcom;

import java.io.Serializable;
import java.util.Date;

public class ProgramaCobranca
				extends ObjetoGcom
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String nome;

	private String descricao;

	private CobrancaCriterio criterio;

	private Date dataCriacao;

	private Date dataInicio;

	private Date dataEncerramento;

	private Date dataUltimoMovimento;

	private Date ultimaAlteracao;

	private Short indicadorUso;

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getNome(){

		return nome;
	}

	public void setNome(String nome){

		this.nome = nome;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public Date getDataCriacao(){

		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao){

		this.dataCriacao = dataCriacao;
	}

	public Date getDataInicio(){

		return dataInicio;
	}

	public void setDataInicio(Date dataInicio){

		this.dataInicio = dataInicio;
	}

	public Date getDataEncerramento(){

		return dataEncerramento;
	}

	public void setDataEncerramento(Date dataEncerramento){

		this.dataEncerramento = dataEncerramento;
	}

	public Date getDataUltimoMovimento(){

		return dataUltimoMovimento;
	}

	public void setDataUltimoMovimento(Date dataUltimoMovimento){

		this.dataUltimoMovimento = dataUltimoMovimento;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public CobrancaCriterio getCriterio(){

		return criterio;
	}

	public void setCriterio(CobrancaCriterio criterio){

		this.criterio = criterio;
	}

}
