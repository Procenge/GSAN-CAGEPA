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

import gcom.cadastro.imovel.Imovel;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Hugo Lima
 * @date 08/05/2012
 *       Classe Helper utilizada na consulta de contas de um im�vel
 */
public class ManterContaHelper
				implements Serializable {

	private Imovel imovel;

	private Integer anoMesReferenciaContaInicio;

	private Integer anoMesReferenciaContaFim;

	private Date dataPagamentoContaInicio;

	private Date dataPagamentoContaFim;

	private String inContasRevisao;

	private Integer[] motivosRevisaoDisponiveis;

	/**
	 * 
	 */
	public ManterContaHelper() {

	}

	public ManterContaHelper(Imovel imovel, Integer anoMesReferenciaContaInicio, Integer anoMesReferenciaContaFim,
								Date dataPagamentoContaInicio, Date dataPagamentoContaFim, String inContasRevisao,
								Integer[] motivosRevisaoDisponiveis) {

		this.imovel = imovel;
		this.anoMesReferenciaContaInicio = anoMesReferenciaContaInicio;
		this.anoMesReferenciaContaFim = anoMesReferenciaContaFim;
		this.dataPagamentoContaInicio = dataPagamentoContaInicio;
		this.dataPagamentoContaFim = dataPagamentoContaFim;
		this.inContasRevisao = inContasRevisao;
		this.motivosRevisaoDisponiveis = motivosRevisaoDisponiveis;
	}

	public Imovel getImovel(){

		return imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public Integer getAnoMesReferenciaContaInicio(){

		return anoMesReferenciaContaInicio;
	}

	public void setAnoMesReferenciaContaInicio(Integer anoMesReferenciaContaInicio){

		this.anoMesReferenciaContaInicio = anoMesReferenciaContaInicio;
	}

	public Integer getAnoMesReferenciaContaFim(){

		return anoMesReferenciaContaFim;
	}

	public void setAnoMesReferenciaContaFim(Integer anoMesReferenciaContaFim){

		this.anoMesReferenciaContaFim = anoMesReferenciaContaFim;
	}

	public Date getDataPagamentoContaInicio(){

		return dataPagamentoContaInicio;
	}

	public void setDataPagamentoContaInicio(Date dataPagamentoContaInicio){

		this.dataPagamentoContaInicio = dataPagamentoContaInicio;
	}

	public Date getDataPagamentoContaFim(){

		return dataPagamentoContaFim;
	}

	public void setDataPagamentoContaFim(Date dataPagamentoContaFim){

		this.dataPagamentoContaFim = dataPagamentoContaFim;
	}

	public String getInContasRevisao(){

		return inContasRevisao;
	}

	public void setInContasRevisao(String inContasRevisao){

		this.inContasRevisao = inContasRevisao;
	}

	public Integer[] getMotivosRevisaoDisponiveis(){

		return motivosRevisaoDisponiveis;
	}

	public void setMotivosRevisaoDisponiveis(Integer[] motivosRevisaoDisponiveis){

		this.motivosRevisaoDisponiveis = motivosRevisaoDisponiveis;
	}

	public void setMotivosRevisaoDisponiveisComoInteger(String[] motivosRevisaoDisponiveis){

		Integer[] arrayInteger = new Integer[motivosRevisaoDisponiveis.length];

		for(int i = 0; i < motivosRevisaoDisponiveis.length; i++){
			arrayInteger[i] = Integer.valueOf(motivosRevisaoDisponiveis[i]);
		}

		this.motivosRevisaoDisponiveis = arrayInteger;
	}

}
