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

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.faturamento.FaturamentoGrupo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Josenildo Neves */
public class QuitacaoDebitoAnual
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** persistent field */
	private Integer anoReferencia;

	/** persistent field */
	private short indicadorImpressao;

	/** persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private Imovel imovel;

	/** persistent field */
	private Cliente clienteResponsavel;

	/** persistent field */
	private Cliente clienteUsuario;

	/** persistent field */
	private FaturamentoGrupo faturamentoGrupo;

	/** default constructor */
	public QuitacaoDebitoAnual() {

	}

	public QuitacaoDebitoAnual(Integer id) {

		this.id = id;
	}

	public String toString(){

		return new ToStringBuilder(this).append("cdstId", getId()).toString();
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
	 * @return the anoReferencia
	 */
	public Integer getAnoReferencia(){

		return anoReferencia;
	}

	/**
	 * @param anoReferencia
	 *            the anoReferencia to set
	 */
	public void setAnoReferencia(Integer anoReferencia){

		this.anoReferencia = anoReferencia;
	}

	/**
	 * @return the indicadorImpressao
	 */
	public short getIndicadorImpressao(){

		return indicadorImpressao;
	}

	/**
	 * @param indicadorImpressao
	 *            the indicadorImpressao to set
	 */
	public void setIndicadorImpressao(short indicadorImpressao){

		this.indicadorImpressao = indicadorImpressao;
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
	 * @return the imovel
	 */
	public Imovel getImovel(){

		return imovel;
	}

	/**
	 * @param imovel
	 *            the imovel to set
	 */
	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	/**
	 * @return the clienteResponsavel
	 */
	public Cliente getClienteResponsavel(){

		return clienteResponsavel;
	}

	/**
	 * @param clienteResponsavel
	 *            the clienteResponsavel to set
	 */
	public void setClienteResponsavel(Cliente clienteResponsavel){

		this.clienteResponsavel = clienteResponsavel;
	}

	/**
	 * @return the clienteUsuario
	 */
	public Cliente getClienteUsuario(){

		return clienteUsuario;
	}

	/**
	 * @param clienteUsuario
	 *            the clienteUsuario to set
	 */
	public void setClienteUsuario(Cliente clienteUsuario){

		this.clienteUsuario = clienteUsuario;
	}

	/**
	 * @return the faturamentoGrupo
	 */
	public FaturamentoGrupo getFaturamentoGrupo(){

		return faturamentoGrupo;
	}

	/**
	 * @param faturamentoGrupo
	 *            the faturamentoGrupo to set
	 */
	public void setFaturamentoGrupo(FaturamentoGrupo faturamentoGrupo){

		this.faturamentoGrupo = faturamentoGrupo;
	}

}
