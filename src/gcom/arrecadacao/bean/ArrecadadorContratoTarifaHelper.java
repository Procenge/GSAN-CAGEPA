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

package gcom.arrecadacao.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ArrecadadorContratoTarifaHelper {

	/**
	 * @since 08/03/2013
	 */

	private Integer id;

	private Integer idFormaArrecadacao;

	private BigDecimal valorTarifa;

	private BigDecimal percentualTarifa;

	private Integer quantidadePagamentos;

	private BigDecimal valorPagamentos;

	private Date dataVigencia;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getIdFormaArrecadacao(){

		return idFormaArrecadacao;
	}

	public void setIdFormaArrecadacao(Integer idFormaArrecadacao){

		this.idFormaArrecadacao = idFormaArrecadacao;
	}

	public BigDecimal getValorTarifa(){

		return valorTarifa;
	}

	public void setValorTarifa(BigDecimal valorTarifa){

		this.valorTarifa = valorTarifa;
	}

	public BigDecimal getPercentualTarifa(){

		return percentualTarifa;
	}

	public void setPercentualTarifa(BigDecimal percentualTarifa){

		this.percentualTarifa = percentualTarifa;
	}

	public Integer getQuantidadePagamentos(){

		return quantidadePagamentos;
	}

	public void setQuantidadePagamentos(Integer quantidadePagamentos){

		this.quantidadePagamentos = quantidadePagamentos;
	}

	public BigDecimal getValorPagamentos(){

		return valorPagamentos;
	}

	public void setValorPagamentos(BigDecimal valorPagamentos){

		this.valorPagamentos = valorPagamentos;
	}

	public Date getDataVigencia(){

		return dataVigencia;
	}

	public void setDataVigencia(Date dataVigencia){

		this.dataVigencia = dataVigencia;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof ArrecadadorContratoTarifaHelper)) return false;
		final ArrecadadorContratoTarifaHelper other = (ArrecadadorContratoTarifaHelper) obj;
		return new EqualsBuilder()//
						.append(id, other.id)//
						.append(idFormaArrecadacao, other.id)//
						.append(valorTarifa, other.id)//
						.append(percentualTarifa, other.id)//
						.append(quantidadePagamentos, other.id)//
						.append(valorPagamentos, other.id)//
						.isEquals();
	}

	@Override
	public int hashCode(){

		return new HashCodeBuilder()//
						.append(id)//
						.append(idFormaArrecadacao)//
						.append(valorTarifa)//
						.append(percentualTarifa)//
						.append(quantidadePagamentos)//
						.append(valorPagamentos)//
						.toHashCode();

	}

}
