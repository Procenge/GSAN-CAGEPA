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

import gcom.arrecadacao.banco.ContaBancaria;
import gcom.cadastro.imovel.Categoria;
import gcom.contabil.EventoComercial;
import gcom.faturamento.ImpostoTipo;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.util.Util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

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

public class LancamentoContabilSinteticoHelper {

	private Date dataRealizacaoEvento;

	private Integer idUnidadeContabilAgrupamento;

	private EventoComercial eventoComercial;

	private Categoria categoria;

	private LancamentoItemContabil lancamentoItemContabil;

	private ImpostoTipo impostoTipo;

	private String cnpj;

	private ContaBancaria contaBancaria;

	private Collection<LancamentoContabilAnaliticoHelper> listaLancamentosContabeisAnaliticosHelper = new HashSet<LancamentoContabilAnaliticoHelper>();

	public void setDataRealizacaoEvento(Date dataRealizacaoEvento){

		this.dataRealizacaoEvento = Util.zerarHoraMinutoSegundo(dataRealizacaoEvento);
	}

	public Date getDataRealizacaoEvento(){

		return dataRealizacaoEvento;
	}

	public void setListaLancamentosContabeisAnaliticosHelper(
					Collection<LancamentoContabilAnaliticoHelper> listaLancamentosContabeisAnaliticosHelper){

		this.listaLancamentosContabeisAnaliticosHelper = listaLancamentosContabeisAnaliticosHelper;
	}

	public Collection<LancamentoContabilAnaliticoHelper> getListaLancamentosContabeisAnaliticosHelper(){

		return listaLancamentosContabeisAnaliticosHelper;
	}

	/**
	 * @return the valorTotal
	 */
	public BigDecimal getValorTotal(){

		BigDecimal retorno = BigDecimal.ZERO;
		if(this.listaLancamentosContabeisAnaliticosHelper != null){
			for(LancamentoContabilAnaliticoHelper lcAnalitico : listaLancamentosContabeisAnaliticosHelper){
				retorno = retorno.add(lcAnalitico.getValor()).setScale(2, RoundingMode.HALF_UP);
			}
		}
		return retorno;
	}

	public void setEventoComercial(EventoComercial eventoComercial){

		this.eventoComercial = eventoComercial;
	}

	public EventoComercial getEventoComercial(){

		return eventoComercial;
	}

	public void setLancamentoItemContabil(LancamentoItemContabil lancamentoItemContabil){

		this.lancamentoItemContabil = lancamentoItemContabil;
	}

	public LancamentoItemContabil getLancamentoItemContabil(){

		return lancamentoItemContabil;
	}

	public void setContaBancaria(ContaBancaria contaBancaria){

		this.contaBancaria = contaBancaria;
	}

	public ContaBancaria getContaBancaria(){

		return contaBancaria;
	}

	public void setCategoria(Categoria categoria){

		this.categoria = categoria;
	}

	public Categoria getCategoria(){

		return categoria;
	}

	public void setCnpj(String cnpj){

		this.cnpj = cnpj;
	}

	public String getCnpj(){

		return cnpj;
	}

	public void setImpostoTipo(ImpostoTipo impostoTipo){

		this.impostoTipo = impostoTipo;
	}

	public ImpostoTipo getImpostoTipo(){

		return impostoTipo;
	}

	public void setIdUnidadeContabilAgrupamento(Integer idUnidadeContabilAgrupamento){

		this.idUnidadeContabilAgrupamento = idUnidadeContabilAgrupamento;
	}

	public Integer getIdUnidadeContabilAgrupamento(){

		return idUnidadeContabilAgrupamento;
	}
}
