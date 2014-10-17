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
package gcom.faturamento.bean;

import java.math.BigDecimal;


/**
 * @author mgrb
 *
 */
public class HistogramaEsgotoEconomiaDTO {

	private Integer inicio;

	private Integer fim;

	private String descricaoOpcaoTotalizacao;
	private Short percentualEsgoto;

	private String totalizadorDescricao;
	private Integer IdCategoria;
	private String descricaoCategoria;

	private String faixaDescricao;

	private Long totalEconomiasMedido;
	private Long totalLigacoesMedido;
	private Long totalVolumeFaturadoMedido;
	private Long totalConsumoMedido;
	private BigDecimal totalReceitaMedido;
	private Long totalEconomiasNaoMedido;
	private Long totalLigacoesNaoMedido;
	private Long totalVolumeFaturadoNaoMedido;
	private Long totalConsumoNaoMedido;
	private BigDecimal totalReceitaNaoMedido;
	private BigDecimal totalConsumoMedioMedido;
	private BigDecimal totalConsumoMedioNaoMedido;

	private Integer difConsumoFaixa;

	public Short getPercentualEsgoto(){

		return percentualEsgoto;
	}

	public void setPercentualEsgoto(Short percentualEsgoto){

		this.percentualEsgoto = percentualEsgoto;
	}

	public Integer getIdCategoria(){

		return IdCategoria;
	}

	public void setIdCategoria(Integer idCategoria){

		IdCategoria = idCategoria;
	}

	public String getDescricaoCategoria(){

		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria){

		this.descricaoCategoria = descricaoCategoria;
	}

	public Long getTotalEconomiasMedido(){

		return totalEconomiasMedido;
	}

	public void setTotalEconomiasMedido(Long totalEconomiasMedido){

		this.totalEconomiasMedido = totalEconomiasMedido;
	}

	public Long getTotalLigacoesMedido(){

		return totalLigacoesMedido;
	}

	public void setTotalLigacoesMedido(Long totalLigacoesMedido){

		this.totalLigacoesMedido = totalLigacoesMedido;
	}

	public Long getTotalVolumeFaturadoMedido(){

		return totalVolumeFaturadoMedido;
	}

	public void setTotalVolumeFaturadoMedido(Long totalVolumeFaturadoMedido){

		this.totalVolumeFaturadoMedido = totalVolumeFaturadoMedido;
	}

	public Long getTotalEconomiasNaoMedido(){

		return totalEconomiasNaoMedido;
	}

	public void setTotalEconomiasNaoMedido(Long totalEconomiasNaoMedido){

		this.totalEconomiasNaoMedido = totalEconomiasNaoMedido;
	}

	public Long getTotalLigacoesNaoMedido(){

		return totalLigacoesNaoMedido;
	}

	public void setTotalLigacoesNaoMedido(Long totalLigacoesNaoMedido){

		this.totalLigacoesNaoMedido = totalLigacoesNaoMedido;
	}

	public Long getTotalVolumeFaturadoNaoMedido(){

		return totalVolumeFaturadoNaoMedido;
	}

	public void setTotalVolumeFaturadoNaoMedido(Long totalVolumeFaturadoNaoMedido){

		this.totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido;
	}

	public BigDecimal getTotalReceitaMedido(){

		return totalReceitaMedido;
	}

	public void setTotalReceitaMedido(BigDecimal totalReceitaMedido){

		this.totalReceitaMedido = totalReceitaMedido;
	}

	public BigDecimal getTotalReceitaNaoMedido(){

		return totalReceitaNaoMedido;
	}

	public void setTotalReceitaNaoMedido(BigDecimal totalReceitaNaoMedido){

		this.totalReceitaNaoMedido = totalReceitaNaoMedido;
	}

	public Long getTotalConsumoMedido(){

		return totalConsumoMedido;
	}

	public void setTotalConsumoMedido(Long totalConsumoMedido){

		this.totalConsumoMedido = totalConsumoMedido;
	}

	public Long getTotalConsumoNaoMedido(){

		return totalConsumoNaoMedido;
	}

	public void setTotalConsumoNaoMedido(Long totalConsumoNaoMedido){

		this.totalConsumoNaoMedido = totalConsumoNaoMedido;
	}

	public String getTotalizadorDescricao(){

		return totalizadorDescricao;
	}

	public void setTotalizadorDescricao(String totalizadorDescricao){

		this.totalizadorDescricao = totalizadorDescricao;
	}

	public String getDescricaoOpcaoTotalizacao(){

		return descricaoOpcaoTotalizacao;
	}

	public void setDescricaoOpcaoTotalizacao(String opcaoTotalizacao){

		this.descricaoOpcaoTotalizacao = opcaoTotalizacao;
	}

	public String getFaixaDescricao(){

		return faixaDescricao;
	}

	public void setFaixaDescricao(String faixaDescricao){

		this.faixaDescricao = faixaDescricao;
	}

	public BigDecimal getTotalConsumoMedioMedido(){

		return totalConsumoMedioMedido;
	}

	public void setTotalConsumoMedioMedido(BigDecimal totalConsumoMedioMedido){

		this.totalConsumoMedioMedido = totalConsumoMedioMedido;
	}

	public BigDecimal getTotalConsumoMedioNaoMedido(){

		return totalConsumoMedioNaoMedido;
	}

	public void setTotalConsumoMedioNaoMedido(BigDecimal totalConsumoMedioNaoMedido){

		this.totalConsumoMedioNaoMedido = totalConsumoMedioNaoMedido;
	}

	public Integer getInicio(){

		return inicio;
	}

	public void setInicio(Integer inicio){

		this.inicio = inicio;
	}

	public Integer getFim(){

		return fim;
	}

	public void setFim(Integer fim){

		this.fim = fim;
	}

	public Integer getDifConsumoFaixa(){

		return difConsumoFaixa;
	}

	public void setDifConsumoFaixa(Integer difConsumoFaixa){

		this.difConsumoFaixa = difConsumoFaixa;
	}

}
