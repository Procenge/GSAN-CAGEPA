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

package gcom.contabil.bean;

import gcom.util.Util;

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
 * @created 7 de Julho de 2011
 * @version 1.0
 */

public class LancamentoContabilSinteticoConsultaHelper {

	private Long id;

	private Date dataContabil;

	private String dataContabilFormatada;

	private String contaCredito;

	private String contaDebito;

	private String descricaoModulo;

	private String descricaoAbrevModulo;

	private String descricaoEventoComercial;

	private String descricaoUnidadeContabilAgrupamento;

	private String descricaoCategoria;

	private String descricaoAbrevCategoria;

	private String lancamentoItemContabil;

	private String abrevLancamentoItemContabil;

	private String impostoTipo;

	private String abrevImpostoTipo;

	private String complemento;

	private BigDecimal valor;

	private String valorString;

	public String getContaCredito(){

		return contaCredito;
	}

	public void setContaCredito(String contaCredito){

		this.contaCredito = contaCredito;
	}

	public String getContaDebito(){

		return contaDebito;
	}

	public void setContaDebito(String contaDebito){

		this.contaDebito = contaDebito;
	}

	public String getDescricaoModulo(){

		return descricaoModulo;
	}

	public void setDescricaoModulo(String descricaoModulo){

		this.descricaoModulo = descricaoModulo;
	}

	public String getDescricaoEventoComercial(){

		return descricaoEventoComercial;
	}

	public void setDescricaoEventoComercial(String descricaoEventoComercial){

		this.descricaoEventoComercial = descricaoEventoComercial;
	}

	public String getDescricaoUnidadeContabilAgrupamento(){

		return descricaoUnidadeContabilAgrupamento;
	}

	public void setDescricaoUnidadeContabilAgrupamento(String descricaoUnidadeContabilAgrupamento){

		this.descricaoUnidadeContabilAgrupamento = descricaoUnidadeContabilAgrupamento;
	}

	public String getDescricaoCategoria(){

		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria){

		this.descricaoCategoria = descricaoCategoria;
	}

	public BigDecimal getValor(){

		return valor;
	}

	public void setValor(BigDecimal valor){

		this.valor = valor;
		this.valorString = Util.formataBigDecimal(valor, 2, Boolean.TRUE);
	}

	public void setDataContabil(Date dataContabil){

		this.dataContabil = dataContabil;
		this.dataContabilFormatada = Util.formatarData(dataContabil);
	}

	public Date getDataContabil(){

		return dataContabil;
	}

	public void setLancamentoItemContabil(String lancamentoItemContabil){

		this.lancamentoItemContabil = lancamentoItemContabil;
	}

	public String getLancamentoItemContabil(){

		return lancamentoItemContabil;
	}

	public void setImpostoTipo(String impostoTipo){

		this.impostoTipo = impostoTipo;
	}

	public String getImpostoTipo(){

		return impostoTipo;
	}

	public void setComplemento(String complemento){

		this.complemento = complemento;
	}

	public String getComplemento(){

		return complemento;
	}

	public void setDescricaoAbrevModulo(String descricaoAbrevModulo){

		this.descricaoAbrevModulo = descricaoAbrevModulo;
	}

	public String getDescricaoAbrevModulo(){

		return descricaoAbrevModulo;
	}

	public void setDescricaoAbrevCategoria(String descricaoAbrevCategoria){

		this.descricaoAbrevCategoria = descricaoAbrevCategoria;
	}

	public String getDescricaoAbrevCategoria(){

		return descricaoAbrevCategoria;
	}

	public void setAbrevLancamentoItemContabil(String abrevLancamentoItemContabil){

		this.abrevLancamentoItemContabil = abrevLancamentoItemContabil;
	}

	public String getAbrevLancamentoItemContabil(){

		return abrevLancamentoItemContabil;
	}

	public void setAbrevImpostoTipo(String abrevImpostoTipo){

		this.abrevImpostoTipo = abrevImpostoTipo;
	}

	public String getAbrevImpostoTipo(){

		return abrevImpostoTipo;
	}

	public void setValorString(String valorString){

		this.valorString = valorString;
	}

	public String getValorString(){

		return valorString;
	}

	public void setDataContabilFormatada(String dataContabilFormatada){

		this.dataContabilFormatada = dataContabilFormatada;
	}

	public String getDataContabilFormatada(){

		return dataContabilFormatada;
	}

	public void setId(Long id){

		this.id = id;
	}

	public Long getId(){

		return id;
	}
}
