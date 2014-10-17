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

package gcom.cobranca.contrato;

import gcom.arrecadacao.ContratoMotivoCancelamento;
import gcom.cadastro.empresa.Empresa;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;

import java.math.BigDecimal;
import java.util.Date;

public class CobrancaContrato
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String numeroContrato;

	private Empresa empresa;

	private Date dataInicial;

	private Date dataFinal;

	private Date dataEncerramento;

	private ContratoMotivoCancelamento contratoMotivoCancelamento;

	private Date ultimaAlteracao;

	private Integer quantidadeMinimaDiasVencidos;

	private Integer quantidadeDiasReincidencia;

	private BigDecimal percentualRemuneracaoReincidencia;

	private ContratoTipoRemuneracao contratoTipoRemuneracao;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getNumeroContrato(){

		return numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato){

		this.numeroContrato = numeroContrato;
	}

	public Empresa getEmpresa(){

		return empresa;
	}

	public void setEmpresa(Empresa empresa){

		this.empresa = empresa;
	}

	public Date getDataInicial(){

		return dataInicial;
	}

	public void setDataInicial(Date dataInicial){

		this.dataInicial = dataInicial;
	}

	public Date getDataFinal(){

		return dataFinal;
	}

	public void setDataFinal(Date dataFinal){

		this.dataFinal = dataFinal;
	}

	public Date getDataEncerramento(){

		return dataEncerramento;
	}

	public void setDataEncerramento(Date dataEncerramento){

		this.dataEncerramento = dataEncerramento;
	}

	public ContratoMotivoCancelamento getContratoMotivoCancelamento(){

		return contratoMotivoCancelamento;
	}

	public void setContratoMotivoCancelamento(ContratoMotivoCancelamento contratoMotivoCancelamento){

		this.contratoMotivoCancelamento = contratoMotivoCancelamento;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	@Override
	public Filtro retornaFiltro(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Integer getQuantidadeMinimaDiasVencidos(){

		return quantidadeMinimaDiasVencidos;
	}

	public void setQuantidadeMinimaDiasVencidos(Integer quantidadeMinimaDiasVencidos){

		this.quantidadeMinimaDiasVencidos = quantidadeMinimaDiasVencidos;
	}

	public Integer getQuantidadeDiasReincidencia(){

		return quantidadeDiasReincidencia;
	}

	public void setQuantidadeDiasReincidencia(Integer quantidadeDiasReincidencia){

		this.quantidadeDiasReincidencia = quantidadeDiasReincidencia;
	}

	public BigDecimal getPercentualRemuneracaoReincidencia(){

		return percentualRemuneracaoReincidencia;
	}

	public void setPercentualRemuneracaoReincidencia(BigDecimal percentualRemuneracaoReincidencia){

		this.percentualRemuneracaoReincidencia = percentualRemuneracaoReincidencia;
	}

	public ContratoTipoRemuneracao getContratoTipoRemuneracao(){

		return contratoTipoRemuneracao;
	}

	public void setContratoTipoRemuneracao(ContratoTipoRemuneracao contratoTipoRemuneracao){

		this.contratoTipoRemuneracao = contratoTipoRemuneracao;
	}

}
