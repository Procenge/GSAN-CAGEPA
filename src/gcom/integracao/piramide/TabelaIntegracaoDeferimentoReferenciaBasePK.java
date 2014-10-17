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
package gcom.integracao.piramide;

import gcom.interceptor.ObjetoGcom;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * @author mgrb
 *         Representa a chave primaria de TI_DIFER_PERIODO
 */
public class TabelaIntegracaoDeferimentoReferenciaBasePK
				extends ObjetoGcom {

	private String mesAnoReferencia;

	private String cnpj;

	private String codigoGerenciaRegional;

	private String codigoSistemaOrigem;

	/**
	 * TabelaIntegracaoDeferimentoReferenciaBasePK
	 * <p>
	 * Esse método Constroi uma instância de TabelaIntegracaoDeferimentoReferenciaBasePK.
	 * </p>
	 * 
	 * @author Marlos Ribeiro
	 * @since 02/10/2012
	 */
	public TabelaIntegracaoDeferimentoReferenciaBasePK() {

	}


	/**
	 * TabelaIntegracaoDeferimentoReferenciaBasePK
	 * <p>
	 * Esse método Constroi uma instância de TabelaIntegracaoDeferimentoReferenciaBasePK.
	 * </p>
	 * 
	 * @param codigoGerenciaRegional
	 * @param codigoSistemaOrigem
	 * @param mesAnoReferencia
	 * @param cnpj
	 * @author Marlos Ribeiro
	 * @since 02/10/2012
	 */
	public TabelaIntegracaoDeferimentoReferenciaBasePK(String codigoGerenciaRegional, String codigoSistemaOrigem, String mesAnoReferencia,
														String cnpj) {

		super();
		this.codigoGerenciaRegional = codigoGerenciaRegional;
		this.codigoSistemaOrigem = codigoSistemaOrigem;
		this.mesAnoReferencia = mesAnoReferencia;
		this.cnpj = cnpj;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object other){

		if((this == other)) return true;
		if(!(other instanceof TabelaIntegracaoDeferimentoReferenciaBasePK)) return false;
		TabelaIntegracaoDeferimentoReferenciaBasePK castOther = (TabelaIntegracaoDeferimentoReferenciaBasePK) other;
		return new EqualsBuilder()//
						.append(codigoGerenciaRegional, castOther.codigoGerenciaRegional)//
						.append(codigoSistemaOrigem, castOther.codigoSistemaOrigem)//
						.append(getMesAnoReferencia(), castOther.getMesAnoReferencia())//
						.append(cnpj, castOther.cnpj)//
						.isEquals();
	}

	public String getCnpj(){

		return cnpj;
	}

	public String getCodigoGerenciaRegional(){

		return codigoGerenciaRegional;
	}

	public String getCodigoSistemaOrigem(){

		return codigoSistemaOrigem;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode(){

		return new HashCodeBuilder()//
						.append(codigoGerenciaRegional)//
						.append(codigoSistemaOrigem)//
						.append(getMesAnoReferencia())//
						.append(cnpj)//
						.toHashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] chavePrimaria = new String[4];
		chavePrimaria[0] = "gerenciaRegional";
		chavePrimaria[0] = "codigoSistemaOrigem";
		chavePrimaria[0] = "mesAnoReferencia";
		chavePrimaria[0] = "cnpj";
		return chavePrimaria;
	}

	public void setCnpj(String cnpj){

		this.cnpj = cnpj;
	}

	public void setCodigoGerenciaRegional(String codigoGerenciaRegional){

		this.codigoGerenciaRegional = codigoGerenciaRegional;
	}

	public void setCodigoSistemaOrigem(String codigoSistemaOrigem){

		this.codigoSistemaOrigem = codigoSistemaOrigem;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString(){

		return new ToStringBuilder(this)//
						.append("codigoGerenciaRegional", codigoGerenciaRegional)//
						.append("codigoSistemaOrigem", codigoSistemaOrigem)//
						.append("mesAnoReferencia", getMesAnoReferencia())//
						.append("cnpj", cnpj)//
						.toString();
	}


	public String getMesAnoReferencia(){

		return mesAnoReferencia;
	}


	public void setMesAnoReferencia(String mesAnoReferencia){

		this.mesAnoReferencia = mesAnoReferencia;
	}
}
