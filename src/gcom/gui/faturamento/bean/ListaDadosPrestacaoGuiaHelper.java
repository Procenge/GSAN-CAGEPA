/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * 
 * GSANPCG
 * Eduardo Henrique
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.faturamento.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * [SB0002] � Criar Lista dos Dados das Presta��es da Guia
 * 
 * @author Carlos Chrystian Ramos
 * @date 16/05/2013
 *       Classe Helper utilizada na visualiza��o dos dados da Presta��es de uma Guia de Pagamento
 */
public class ListaDadosPrestacaoGuiaHelper
				implements Serializable {

	private Integer prestacao;

	private Date dataVencimentoPrestacao;

	private Map<Integer, BigDecimal> mapValorDebitoNaPrestacaoPorTipoDebito = new HashMap<Integer, BigDecimal>();

	private Map<Integer, Integer> mapNumeroProcessoAdministrativoExecucaoFiscalNaPrestacaoPorTipoDebito = new HashMap<Integer, Integer>();

	/**
	 * Construtor padr�o
	 */
	public ListaDadosPrestacaoGuiaHelper() {

	}

	/**
	 * @param prestacao
	 * @param dataVencimentoPrestacao
	 * @param mapValorDebitoNaPrestacaoPorTipoDebito
	 */
	public ListaDadosPrestacaoGuiaHelper(Integer prestacao, Date dataVencimentoPrestacao,
											Map<Integer, BigDecimal> mapValorDebitoNaPrestacaoPorTipoDebito,
											Map<Integer, Integer> mapValorDebitoNaPrestacaoPorProcessoAdministrativo) {

		super();
		this.prestacao = prestacao;
		this.dataVencimentoPrestacao = dataVencimentoPrestacao;
		this.mapValorDebitoNaPrestacaoPorTipoDebito = mapValorDebitoNaPrestacaoPorTipoDebito;
		this.mapNumeroProcessoAdministrativoExecucaoFiscalNaPrestacaoPorTipoDebito = mapNumeroProcessoAdministrativoExecucaoFiscalNaPrestacaoPorTipoDebito;
	}


	/**
	 * @return the prestacao
	 */
	public Integer getPrestacao(){

		return prestacao;
	}


	/**
	 * @param prestacao
	 *            the prestacao to set
	 */
	public void setPrestacao(Integer prestacao){

		this.prestacao = prestacao;
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
	 * @return the mapValorDebitoNaPrestacaoPorTipoDebito
	 */
	public Map<Integer, BigDecimal> getMapValorDebitoNaPrestacaoPorTipoDebito(){

		return mapValorDebitoNaPrestacaoPorTipoDebito;
	}


	/**
	 * @param valorDebitoNaPrestacaoPorTipoDebito
	 *            the mapValorDebitoNaPrestacaoPorTipoDebito to set
	 */
	public void setMapValorDebitoNaPrestacaoPorTipoDebito(Map<Integer, BigDecimal> mapValorDebitoNaPrestacaoPorTipoDebito){

		this.mapValorDebitoNaPrestacaoPorTipoDebito = mapValorDebitoNaPrestacaoPorTipoDebito;
	}

	/**
	 * @return the mapNumeroProcessoAdministrativoExecucaoFiscalNaPrestacaoPorTipoDebito
	 */
	public Map<Integer, Integer> getMapNumeroProcessoAdministrativoExecucaoFiscalNaPrestacaoPorTipoDebito(){

		return mapNumeroProcessoAdministrativoExecucaoFiscalNaPrestacaoPorTipoDebito;
	}

	/**
	 * @param mapNumeroProcessoAdministrativoExecucaoFiscalNaPrestacaoPorTipoDebito
	 *            the mapNumeroProcessoAdministrativoExecucaoFiscalNaPrestacaoPorTipoDebito to set
	 */
	public void setMapNumeroProcessoAdministrativoExecucaoFiscalNaPrestacaoPorTipoDebito(
					Map<Integer, Integer> mapNumeroProcessoAdministrativoExecucaoFiscalNaPrestacaoPorTipoDebito){

		this.mapNumeroProcessoAdministrativoExecucaoFiscalNaPrestacaoPorTipoDebito = mapNumeroProcessoAdministrativoExecucaoFiscalNaPrestacaoPorTipoDebito;
	}

}
