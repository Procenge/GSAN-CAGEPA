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

package gcom.arrecadacao.pagamento.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Andr� Lopes
 * @date 25/09/2013
 */
public class PagamentoHistoricoAdmiteDevolucaoHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final Integer TIPO_CONTA = 1;

	public static final Integer TIPO_GUIA_PGTO = 2;

	public static final Integer TIPO_DEBITO_A_COBRAR = 3;

	public static final String CONCATENAR_ID_NNPRESTACAO = "/";

	private Integer idPagamentoHistorico;

	private String dataPagamento;

	private BigDecimal valorPagamento;

	private Integer situacaoAtual;

	private String situacaoAtualDescricao;

	private Integer tipoIdentifica��oDocumento;

	private String indentificacaoDocumento;

	private BigDecimal valorDocumento;

	private Integer debitoCreditoIndicadorAtual;

	public Integer getIdPagamentoHistorico(){

		return idPagamentoHistorico;
	}

	public void setIdPagamentoHistorico(Integer idPagamentoHistorico){

		this.idPagamentoHistorico = idPagamentoHistorico;
	}

	public String getDataPagamento(){

		return dataPagamento;
	}

	public void setDataPagamento(String dataPagamento){

		this.dataPagamento = dataPagamento;
	}

	public BigDecimal getValorPagamento(){

		return valorPagamento;
	}

	public void setValorPagamento(BigDecimal valorPagamento){

		this.valorPagamento = valorPagamento;
	}

	public Integer getSituacaoAtual(){

		return situacaoAtual;
	}

	public void setSituacaoAtual(Integer situacaoAtual){

		this.situacaoAtual = situacaoAtual;
	}

	public String getSituacaoAtualDescricao(){

		return situacaoAtualDescricao;
	}

	public void setSituacaoAtualDescricao(String situacaoAtualDescricao){

		this.situacaoAtualDescricao = situacaoAtualDescricao;
	}

	public String getIndentificacaoDocumento(){

		return indentificacaoDocumento;
	}

	public void setIndentificacaoDocumento(String indentificacaoDocumento){

		this.indentificacaoDocumento = indentificacaoDocumento;
	}

	public BigDecimal getValorDocumento(){

		return valorDocumento;
	}

	public void setValorDocumento(BigDecimal valorDocumento){

		this.valorDocumento = valorDocumento;
	}

	public Integer getTipoIdentifica��oDocumento(){

		return tipoIdentifica��oDocumento;
	}

	public void setTipoIdentifica��oDocumento(Integer tipoIdentifica��oDocumento){

		this.tipoIdentifica��oDocumento = tipoIdentifica��oDocumento;
	}

	public Integer getDebitoCreditoIndicadorAtual(){

		return debitoCreditoIndicadorAtual;
	}

	public void setDebitoCreditoIndicadorAtual(Integer debitoCreditoIndicadorAtual){

		this.debitoCreditoIndicadorAtual = debitoCreditoIndicadorAtual;
	}


}
