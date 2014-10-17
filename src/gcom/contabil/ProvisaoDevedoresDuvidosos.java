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

package gcom.contabil;

import gcom.cadastro.imovel.Categoria;
import gcom.faturamento.conta.ContaGeral;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gestão Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * 
 * @author Genival Barbosa
 * @created 6 de Dezembro de 2011
 * @version 1.0
 */
public class ProvisaoDevedoresDuvidosos
				extends ObjetoTransacao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3531961234356691933L;

	private int id;

	private ContaGeral contaGeral;

	private Integer referenciaContabil;

	private EventoComercial eventoComercial;

	private Categoria categoria;

	private BigDecimal valorAgua;

	private BigDecimal valorEsgoto;

	private BigDecimal valorDebitos;

	private BigDecimal valorCreditos;

	private BigDecimal valorMulta;

	private BigDecimal valorjuros;

	private BigDecimal valorCorrecao;

	private Date dataBaixa;

	private Integer referenciaBaixa;

	private Short indicadorUso;

	private Date ultimaAlteracao;

	private EventoComercial eventoComercialBaixaContaPDD;

	private ProvisaoDevedoresDuvidososMotivoBaixa provisaoDevedoresDuvidososMotivoBaixa;

	public Integer getReferenciaContabil(){

		return referenciaContabil;
	}

	public void setReferenciaContabil(Integer referenciaContabil){

		this.referenciaContabil = referenciaContabil;
	}

	public EventoComercial getEventoComercial(){

		return eventoComercial;
	}

	public void setEventoComercial(EventoComercial eventoComercial){

		this.eventoComercial = eventoComercial;
	}

	public Categoria getCategoria(){

		return categoria;
	}

	public void setCategoria(Categoria categoria){

		this.categoria = categoria;
	}

	public BigDecimal getValorAgua(){

		return valorAgua;
	}

	public void setValorAgua(BigDecimal valorAgua){

		this.valorAgua = valorAgua;
	}

	public BigDecimal getValorEsgoto(){

		return valorEsgoto;
	}

	public void setValorEsgoto(BigDecimal valorEsgoto){

		this.valorEsgoto = valorEsgoto;
	}

	public BigDecimal getValorDebitos(){

		return valorDebitos;
	}

	public void setValorDebitos(BigDecimal valorDebitos){

		this.valorDebitos = valorDebitos;
	}

	public BigDecimal getValorCreditos(){

		return valorCreditos;
	}

	public void setValorCreditos(BigDecimal valorCreditos){

		this.valorCreditos = valorCreditos;
	}

	public BigDecimal getValorMulta(){

		return valorMulta;
	}

	public void setValorMulta(BigDecimal valorMulta){

		this.valorMulta = valorMulta;
	}

	public BigDecimal getValorjuros(){

		return valorjuros;
	}

	public void setValorjuros(BigDecimal valorjuros){

		this.valorjuros = valorjuros;
	}

	public BigDecimal getValorCorrecao(){

		return valorCorrecao;
	}

	public void setValorCorrecao(BigDecimal valorCorrecao){

		this.valorCorrecao = valorCorrecao;
	}

	public Date getDataBaixa(){

		return dataBaixa;
	}

	public void setDataBaixa(Date dataBaixa){

		this.dataBaixa = dataBaixa;
	}

	public Integer getReferenciaBaixa(){

		return referenciaBaixa;
	}

	public void setReferenciaBaixa(Integer referenciaBaixa){

		this.referenciaBaixa = referenciaBaixa;
	}

	@Override
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	@Override
	public Filtro retornaFiltro(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;

	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		// TODO Auto-generated method stub
		return null;
	}

	public void setId(int id){

		this.id = id;
	}

	public int getId(){

		return id;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public EventoComercial getEventoComercialBaixaContaPDD(){

		return eventoComercialBaixaContaPDD;
	}

	public void setEventoComercialBaixaContaPDD(EventoComercial eventoComercialBaixaContaPDD){

		this.eventoComercialBaixaContaPDD = eventoComercialBaixaContaPDD;
	}

	public ProvisaoDevedoresDuvidososMotivoBaixa getProvisaoDevedoresDuvidososMotivoBaixa(){

		return provisaoDevedoresDuvidososMotivoBaixa;
	}

	public void setProvisaoDevedoresDuvidososMotivoBaixa(ProvisaoDevedoresDuvidososMotivoBaixa provisaoDevedoresDuvidososMotivoBaixa){

		this.provisaoDevedoresDuvidososMotivoBaixa = provisaoDevedoresDuvidososMotivoBaixa;
	}

	public ContaGeral getContaGeral(){

		return contaGeral;
	}

	public void setContaGeral(ContaGeral contaGeral){

		this.contaGeral = contaGeral;
	}

}
