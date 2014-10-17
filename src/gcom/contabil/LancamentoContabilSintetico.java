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

import gcom.arrecadacao.banco.ContaBancaria;
import gcom.cadastro.imovel.Categoria;
import gcom.faturamento.ImpostoTipo;
import gcom.financeiro.ContaContabil;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.Util;
import gcom.util.filtro.Filtro;

import java.math.BigDecimal;
import java.util.Collection;
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

public class LancamentoContabilSintetico
				extends ObjetoTransacao {

	private static final long serialVersionUID = -3349242384970557635L;

	private Long id;

	private Date dataGeracao;

	private Date dataContabil;

	private int idUnidadeContabilAgrupamento;

	private UnidadeContabilAgrupamento unidadeContabilAgrupamento;

	private EventoComercial eventoComercial;

	private Categoria categoria;

	private LancamentoItemContabil lancamentoItemContabil;

	private ImpostoTipo impostoTipo;

	private String numeroCNPJ;

	private BigDecimal valor;

	private Collection<LancamentoContabilAnalitico> listaLancamentoContabilAnalitico;

	private ContaBancaria contaBancaria;

	private Date ultimaAlteracao;

	private Short indicadorUso;

	// atributos não persistidos apenas para apresentação na consulta
	private ContaContabil contaContabilCredito;

	private ContaContabil contaContabilDebito;

	public Date getDataGeracao(){

		return dataGeracao;
	}

	public void setDataGeracao(Date dataGeracao){

		this.dataGeracao = Util.zerarHoraMinutoSegundo(dataGeracao);
	}

	public Date getDataContabil(){

		return dataContabil;
	}

	public void setDataContabil(Date dataContabil){

		this.dataContabil = Util.zerarHoraMinutoSegundo(dataContabil);
	}

	public EventoComercial getEventoComercial(){

		return eventoComercial;
	}

	public void setEventoComercial(EventoComercial eventoComercial){

		this.eventoComercial = eventoComercial;
	}

	public LancamentoItemContabil getLancamentoItemContabil(){

		return lancamentoItemContabil;
	}

	public void setLancamentoItemContabil(LancamentoItemContabil lancamentoItemContabil){

		this.lancamentoItemContabil = lancamentoItemContabil;
	}

	// public Tributo getTributo(){
	//
	// return tributo;
	// }
	//

	// public void setTributo(Tributo tributo){
	//
	// this.tributo = tributo;
	// }

	public String getNumeroCNPJ(){

		return numeroCNPJ;
	}

	public void setNumeroCNPJ(String numeroCNPJ){

		this.numeroCNPJ = numeroCNPJ;
	}

	public BigDecimal getValor(){

		return valor;
	}

	public void setValor(BigDecimal valor){

		this.valor = valor;
	}

	public Collection<LancamentoContabilAnalitico> getListaLancamentoContabilAnalitico(){

		return listaLancamentoContabilAnalitico;
	}

	public void setListaLancamentoContabilAnalitico(Collection<LancamentoContabilAnalitico> listaLancamentoContabilAnalitico){

		this.listaLancamentoContabilAnalitico = listaLancamentoContabilAnalitico;
	}

	public void setContaBancaria(ContaBancaria contaBancaria){

		this.contaBancaria = contaBancaria;
	}

	public ContaBancaria getContaBancaria(){

		return contaBancaria;
	}

	public void setContaContabilCredito(ContaContabil contaContabilCredito){

		this.contaContabilCredito = contaContabilCredito;
	}

	public ContaContabil getContaContabilCredito(){

		return contaContabilCredito;
	}

	public void setContaContabilDebito(ContaContabil contaContabilDebito){

		this.contaContabilDebito = contaContabilDebito;
	}

	public ContaContabil getContaContabilDebito(){

		return contaContabilDebito;
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

	public void setCategoria(Categoria categoria){

		this.categoria = categoria;
	}

	public Categoria getCategoria(){

		return categoria;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public void setUnidadeContabilAgrupamento(UnidadeContabilAgrupamento unidadeContabilAgrupamento){

		this.unidadeContabilAgrupamento = unidadeContabilAgrupamento;
	}

	public UnidadeContabilAgrupamento getUnidadeContabilAgrupamento(){

		return unidadeContabilAgrupamento;
	}

	public void setIdUnidadeContabilAgrupamento(int idUnidadeContabilAgrupamento){

		this.idUnidadeContabilAgrupamento = idUnidadeContabilAgrupamento;
	}

	public int getIdUnidadeContabilAgrupamento(){

		return idUnidadeContabilAgrupamento;
	}

	public void setImpostoTipo(ImpostoTipo impostoTipo){

		this.impostoTipo = impostoTipo;
	}

	public ImpostoTipo getImpostoTipo(){

		return impostoTipo;
	}

	public void setId(Long id){

		this.id = id;
	}

	public Long getId(){

		return id;
	}
}
