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

package gcom.gui.cobranca.contrato;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

/**
 * ActionForm Inserir Contrato Cobranca
 * 
 * @author Virgínia Melo
 * @date 17/11/2008
 * @author isilva
 * @date 07/06/2010
 */
public class InserirContratoCobrancaActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String numeroContrato;

	private String idEmpresa;

	private String dtInicioContrato;

	private String dtFimContrato;

	private String dtEncerramentoContrato;

	private String qtMinimaDiasVencidos;

	private String qtDiasReincidencia;

	private String percRemuneracaoReincidencia;

	private String qtdDebitoSucesso;

	private String valorSucesso;

	private String qtdDebitoProdutividade;

	private String valorProdutividade;

	private String sucesso;

	private String produtividade;

	/**
	 * 0 - Remuneração Por Sucesso <br>
	 * 1 - Remuneração Por Produtividade
	 */
	private String remuneracaoTipo;

	public String getNumeroContrato(){

		return numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato){

		this.numeroContrato = numeroContrato;
	}

	public String getIdEmpresa(){

		return idEmpresa;
	}

	public void setIdEmpresa(String idEmpresa){

		this.idEmpresa = idEmpresa;
	}

	public String getDtInicioContrato(){

		return dtInicioContrato;
	}

	public void setDtInicioContrato(String dtInicioContrato){

		this.dtInicioContrato = dtInicioContrato;
	}

	public String getDtFimContrato(){

		return dtFimContrato;
	}

	public void setDtFimContrato(String dtFimContrato){

		this.dtFimContrato = dtFimContrato;
	}

	@Deprecated
	public String getQtdDebitoSucesso(){

		return qtdDebitoSucesso;
	}

	@Deprecated
	public void setQtdDebitoSucesso(String qtdDebitoSucesso){

		this.qtdDebitoSucesso = qtdDebitoSucesso;
	}

	@Deprecated
	public String getValorSucesso(){

		return valorSucesso;
	}

	@Deprecated
	public void setValorSucesso(String valorSucesso){

		this.valorSucesso = valorSucesso;
	}

	@Deprecated
	public String getQtdDebitoProdutividade(){

		return qtdDebitoProdutividade;
	}

	@Deprecated
	public void setQtdDebitoProdutividade(String qtdDebitoProdutividade){

		this.qtdDebitoProdutividade = qtdDebitoProdutividade;
	}

	@Deprecated
	public String getValorProdutividade(){

		return valorProdutividade;
	}

	@Deprecated
	public void setValorProdutividade(String valorProdutividade){

		this.valorProdutividade = valorProdutividade;
	}

	@Deprecated
	public String getSucesso(){

		return sucesso;
	}

	@Deprecated
	public void setSucesso(String sucesso){

		this.sucesso = sucesso;
	}

	@Deprecated
	public String getProdutividade(){

		return produtividade;
	}

	@Deprecated
	public void setProdutividade(String produtividade){

		this.produtividade = produtividade;
	}

	public String getDtEncerramentoContrato(){

		return dtEncerramentoContrato;
	}

	public void setDtEncerramentoContrato(String dtEncerramentoContrato){

		this.dtEncerramentoContrato = dtEncerramentoContrato;
	}

	public String getQtMinimaDiasVencidos(){

		return qtMinimaDiasVencidos;
	}

	public void setQtMinimaDiasVencidos(String qtMinimaDiasVencidos){

		this.qtMinimaDiasVencidos = qtMinimaDiasVencidos;
	}

	public String getQtDiasReincidencia(){

		return qtDiasReincidencia;
	}

	public void setQtDiasReincidencia(String qtDiasReincidencia){

		this.qtDiasReincidencia = qtDiasReincidencia;
	}

	public String getPercRemuneracaoReincidencia(){

		return percRemuneracaoReincidencia;
	}

	public void setPercRemuneracaoReincidencia(String percRemuneracaoReincidencia){

		this.percRemuneracaoReincidencia = percRemuneracaoReincidencia;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request){

		// TODO Auto-generated method stub
		// super.reset(mapping, request);
		super.reset(mapping, request);
		this.sucesso = "";
		this.produtividade = "";
		this.qtdDebitoSucesso = "";
		this.valorSucesso = "";
		this.qtdDebitoProdutividade = "";
		this.valorProdutividade = "";
		this.remuneracaoTipo = "";
	}

	/**
	 * 0 - Remuneração Por Sucesso <br>
	 * 1 - Remuneração Por Produtividade
	 */
	public void setRemuneracaoTipo(String remuneracaoTipo){

		this.remuneracaoTipo = remuneracaoTipo;
	}

	/**
	 * 0 - Remuneração Por Sucesso <br>
	 * 1 - Remuneração Por Produtividade
	 */
	public String getRemuneracaoTipo(){

		return remuneracaoTipo;
	}

}
