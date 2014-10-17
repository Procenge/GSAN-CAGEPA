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

package gcom.arrecadacao.debitoautomatico;

import java.io.Serializable;

public class DebitoAutomaticoMovimentoHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** Chave de d�bito autom�tico quando vier de uma ContaGeral. */
	private String referenciaConta;

	private Integer dtReferenciaComparar;

	/** Chave de d�bito autom�tico quando vier de uma GuiaPagamento. */
	private String parcelaGuia;

	private String dataEnvioBanco;


	private String dataVencimento;

	private String dataRetornoBanco;

	private String valorDebito;

	private String situacao;

	private String descricaoSituacao;

	private String ocorrencia;

	public String getReferenciaConta(){

		return referenciaConta;
	}

	public void setReferenciaConta(String referenciaConta){

		this.referenciaConta = referenciaConta;
	}

	public String getParcelaGuia(){

		return parcelaGuia;
	}

	public void setParcelaGuia(String parcelaGuia){

		this.parcelaGuia = parcelaGuia;
	}

	public Integer getId(){

		return id;
	}


	public void setId(Integer id){

		this.id = id;
	}

	public String getDataEnvioBanco(){

		return dataEnvioBanco;
	}

	public void setDataEnvioBanco(String dataEnvioBanco){

		this.dataEnvioBanco = dataEnvioBanco;
	}

	public String getDataVencimento(){

		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	public String getDataRetornoBanco(){

		return dataRetornoBanco;
	}

	public void setDataRetornoBanco(String dataRetornoBanco){

		this.dataRetornoBanco = dataRetornoBanco;
	}

	public String getValorDebito(){

		return valorDebito;
	}

	public void setValorDebito(String valorDebito){

		this.valorDebito = valorDebito;
	}

	public String getSituacao(){

		return situacao;
	}

	public void setSituacao(String situacao){

		this.situacao = situacao;
	}

	public String getOcorrencia(){

		return ocorrencia;
	}

	public void setOcorrencia(String ocorrencia){

		this.ocorrencia = ocorrencia;
	}

	public String getDescricaoSituacao(){

		return descricaoSituacao;
	}

	public void setDescricaoSituacao(String descricaoSituacao){

		this.descricaoSituacao = descricaoSituacao;
	}

	public Integer getDtReferenciaComparar(){

		return dtReferenciaComparar;
	}

	public void setDtReferenciaComparar(Integer dtReferenciaComparar){

		this.dtReferenciaComparar = dtReferenciaComparar;
	}

	
}
