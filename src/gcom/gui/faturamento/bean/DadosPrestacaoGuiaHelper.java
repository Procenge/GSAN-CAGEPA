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
 * Eduardo Henrique
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

package gcom.gui.faturamento.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * [SB0002] – Criar Lista dos Dados das Prestações da Guia
 * 
 * @author Carlos Chrystian Ramos
 * @date 16/05/2013
 *       Classe Helper utilizada na visualização dos dados da Prestações de uma Guia de Pagamento
 */
public class DadosPrestacaoGuiaHelper
				implements Serializable {

	private Integer numeroPrestacao;

	private Date dataVencimentoPrestacao;

	private Integer[] numeroPrestacaoArray;

	private Date[] dataVencimentoPrestacaoArray;

	private String[] idDebitoTipo;

	private String[] descricaoDebitoTipo;

	private String[] valorDebitoNaPrestacao;

	private Integer[] numeroProcessoAdministrativoExecucaoFiscalArray;

	/**
	 * Construtor padrão
	 */
	public DadosPrestacaoGuiaHelper() {

	}

	/**
	 * @return the numeroProcessoAdministrativoExecucaoFiscalArray
	 */
	public Integer[] getNumeroProcessoAdministrativoExecucaoFiscalArray(){

		return numeroProcessoAdministrativoExecucaoFiscalArray;
	}

	/**
	 * @param numeroProcessoAdministrativoExecucaoFiscalArray
	 *            the numeroProcessoAdministrativoExecucaoFiscalArray to set
	 */
	public void setNumeroProcessoAdministrativoExecucaoFiscalArray(Integer[] numeroProcessoAdministrativoExecucaoFiscalArray){

		this.numeroProcessoAdministrativoExecucaoFiscalArray = numeroProcessoAdministrativoExecucaoFiscalArray;
	}

	/**
	 * @return the numeroPrestacao
	 */
	public Integer getNumeroPrestacao(){

		return numeroPrestacao;
	}

	/**
	 * @param numeroPrestacao
	 *            the numeroPrestacao to set
	 */
	public void setNumeroPrestacao(Integer numeroPrestacao){

		this.numeroPrestacao = numeroPrestacao;
	}

	/**
	 * @return the dataVencimentoPrestacao
	 */
	public Date getDataVencimentoPrestacao(){

		return dataVencimentoPrestacao;
	}

	/**
	 * @param dataVencimentoPrestacao
	 *            the dataVencimentoPrestacao to set
	 */
	public void setDataVencimentoPrestacao(Date dataVencimentoPrestacao){

		this.dataVencimentoPrestacao = dataVencimentoPrestacao;
	}

	/**
	 * @return the numeroPrestacaoArray
	 */
	public Integer[] getNumeroPrestacaoArray(){

		return numeroPrestacaoArray;
	}


	/**
	 * @param numeroPrestacaoArray
	 *            the numeroPrestacaoArray to set
	 */
	public void setNumeroPrestacaoArray(Integer[] numeroPrestacaoArray){

		this.numeroPrestacaoArray = numeroPrestacaoArray;
	}


	/**
	 * @return the dataVencimentoPrestacaoArray
	 */
	public Date[] getDataVencimentoPrestacaoArray(){

		return dataVencimentoPrestacaoArray;
	}


	/**
	 * @param dataVencimentoPrestacaoArray
	 *            the dataVencimentoPrestacaoArray to set
	 */
	public void setDataVencimentoPrestacaoArray(Date[] dataVencimentoPrestacaoArray){

		this.dataVencimentoPrestacaoArray = dataVencimentoPrestacaoArray;
	}


	/**
	 * @return the idDebitoTipo
	 */
	public String[] getIdDebitoTipo(){

		return idDebitoTipo;
	}

	/**
	 * @param idDebitoTipo
	 *            the idDebitoTipo to set
	 */
	public void setIdDebitoTipo(String[] idDebitoTipo){

		this.idDebitoTipo = idDebitoTipo;
	}

	/**
	 * @return the descricaoDebitoTipo
	 */
	public String[] getDescricaoDebitoTipo(){

		return descricaoDebitoTipo;
	}


	/**
	 * @param descricaoDebitoTipo
	 *            the descricaoDebitoTipo to set
	 */
	public void setDescricaoDebitoTipo(String[] descricaoDebitoTipo){

		this.descricaoDebitoTipo = descricaoDebitoTipo;
	}


	/**
	 * @return the valorDebitoNaPrestacao
	 */
	public String[] getValorDebitoNaPrestacao(){

		return valorDebitoNaPrestacao;
	}


	/**
	 * @param valorDebitoNaPrestacao
	 *            the valorDebitoNaPrestacao to set
	 */
	public void setValorDebitoNaPrestacao(String[] valorDebitoNaPrestacao){

		this.valorDebitoNaPrestacao = valorDebitoNaPrestacao;
	}

}
