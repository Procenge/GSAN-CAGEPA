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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 *
 * GSANPCG
 * Eduardo Henrique
 * 
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

package gcom.arrecadacao.pagamento;

/**
 * @author eduardo henrique
 * @since v0.03
 * @date 24/07/2008
 *
 */
import gcom.interceptor.ObjetoGcom;

import org.apache.commons.lang.builder.ToStringBuilder;

public class GuiaPagamentoPrestacaoPK
				extends ObjetoGcom {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer guiaPagamentoId;

	/** identifier field */
	private Short numeroPrestacao;

	/** identifier field */
	private Integer debitoTipoId;

	/** identifier field */
	private Integer itemLancamentoContabilId;

	private Integer numeroProcessoAdministrativoExecucaoFiscal;

	/**
	 * @param guiaPagamentoId
	 * @param numeroPrestacao
	 * @param debitoTipoId
	 * @param itemLancamentoContabilId
	 */
	public GuiaPagamentoPrestacaoPK(Integer guiaPagamentoId, Short numeroPrestacao, Integer debitoTipoId, Integer itemLancamentoContabilId,
									Integer numeroProcessoAdministrativoExecucaoFiscal) {

		this.guiaPagamentoId = guiaPagamentoId;
		this.numeroPrestacao = numeroPrestacao;
		this.debitoTipoId = debitoTipoId;
		this.itemLancamentoContabilId = itemLancamentoContabilId;
		this.numeroProcessoAdministrativoExecucaoFiscal = numeroProcessoAdministrativoExecucaoFiscal;
	}

	/**
	 * Default Constructor
	 */
	public GuiaPagamentoPrestacaoPK() {

	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[5];
		retorno[0] = "guiaPagamentoId";
		retorno[1] = "numeroPrestacaoId";
		retorno[2] = "debitoTipoId";
		retorno[3] = "itemLancamentoContabilId";
		retorno[4] = "numeroProcessoAdministrativoExecucaoFiscal";
		return retorno;
	}

	public Integer getGuiaPagamentoId(){

		return guiaPagamentoId;
	}

	public void setGuiaPagamentoId(Integer guiaPagamentoId){

		this.guiaPagamentoId = guiaPagamentoId;
	}

	public Short getNumeroPrestacao(){

		return numeroPrestacao;
	}

	public void setNumeroPrestacao(Short numeroPrestacao){

		this.numeroPrestacao = numeroPrestacao;
	}

	public Integer getDebitoTipoId(){

		return debitoTipoId;
	}

	public void setDebitoTipoId(Integer debitoTipoId){

		this.debitoTipoId = debitoTipoId;
	}

	public Integer getItemLancamentoContabilId(){

		return itemLancamentoContabilId;
	}

	public void setItemLancamentoContabilId(Integer itemLancamentoContabilId){

		this.itemLancamentoContabilId = itemLancamentoContabilId;
	}

	public Integer getNumeroProcessoAdministrativoExecucaoFiscal(){

		return numeroProcessoAdministrativoExecucaoFiscal;
	}

	public void setNumeroProcessoAdministrativoExecucaoFiscal(Integer numeroProcessoAdministrativoExecucaoFiscal){

		this.numeroProcessoAdministrativoExecucaoFiscal = numeroProcessoAdministrativoExecucaoFiscal;
	}

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((debitoTipoId == null) ? 0 : debitoTipoId.hashCode());
		result = prime * result + ((guiaPagamentoId == null) ? 0 : guiaPagamentoId.hashCode());
		result = prime * result + ((itemLancamentoContabilId == null) ? 0 : itemLancamentoContabilId.hashCode());
		result = prime * result + ((numeroPrestacao == null) ? 0 : numeroPrestacao.hashCode());
		result = prime
						* result
						+ ((numeroProcessoAdministrativoExecucaoFiscal == null) ? 0 : numeroProcessoAdministrativoExecucaoFiscal.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;

		if(obj == null) return false;

		if(!(obj instanceof GuiaPagamentoPrestacaoPK)) return false;

		final GuiaPagamentoPrestacaoPK other = (GuiaPagamentoPrestacaoPK) obj;
		if(debitoTipoId == null){
			if(other.debitoTipoId != null) return false;
		}else if(!debitoTipoId.equals(other.debitoTipoId)) return false;
		if(guiaPagamentoId == null){
			if(other.guiaPagamentoId != null) return false;
		}else if(!guiaPagamentoId.equals(other.guiaPagamentoId)) return false;
		if(itemLancamentoContabilId == null){
			if(other.itemLancamentoContabilId != null) return false;
		}else if(!itemLancamentoContabilId.equals(other.itemLancamentoContabilId)) return false;
		if(numeroPrestacao == null){
			if(other.numeroPrestacao != null) return false;
		}else if(!numeroPrestacao.equals(other.numeroPrestacao)) return false;
		if(numeroProcessoAdministrativoExecucaoFiscal == null){
			if(other.numeroProcessoAdministrativoExecucaoFiscal != null) return false;
		}else if(!numeroProcessoAdministrativoExecucaoFiscal.equals(other.numeroProcessoAdministrativoExecucaoFiscal)) return false;

		return true;
	}

	@Override
	public String toString(){

		return new ToStringBuilder(this).append("guiaPagamentoId", getGuiaPagamentoId()).append("numeroPrestacao", getNumeroPrestacao())
						.append("debitoTipoId", getDebitoTipoId()).append("itemLancamentoContabilId", getItemLancamentoContabilId())
						.append("numeroProcessoAdministrativoExecucaoFiscal", getNumeroProcessoAdministrativoExecucaoFiscal())
						.toString();
	}

}
