/*
 Copyright (C) <2011> GGAS – Sistema de Gestão Comercial (Billing) de Serviços de Distribuição de Gás

 Este arquivo é parte do GGAS, um sistema de gestão comercial de Serviços de Distribuição de Gás

 Este programa é um software livre; você pode redistribuí-lo e/ou
 modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 publicada pela Free Software Foundation; versão 2 da Licença.

 O GGAS é distribuído na expectativa de ser útil,
 mas SEM QUALQUER GARANTIA; sem mesmo a garantia implícita de
 COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM PARTICULAR.
 Consulte a Licença Pública Geral GNU para obter mais detalhes.

 Você deve ter recebido uma cópia da Licença Pública Geral GNU
 junto com este programa; se não, escreva para Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.


 Copyright (C) 2011-2011 the GGAS – Sistema de Gestão Comercial (Billing) de Serviços de Distribuição de Gás

 This file is part of GGAS, a commercial management system for Gas Distribution Services

 GGAS is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; version 2 of the License.

 GGAS is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/**
 * 
 */

package gcom.contabil;

import gcom.arrecadacao.banco.ContaBancaria;
import gcom.cadastro.imovel.Categoria;
import gcom.faturamento.ImpostoTipo;
import gcom.financeiro.ContaContabil;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;

import java.util.Date;

/**
 * 
 *
 */
class EventoComercialLancamento
				extends ObjetoTransacao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3531961234356691933L;

	private Integer id;

	private EventoComercial eventoComercial;

	private Categoria categoria;

	private LancamentoItemContabil lancamentoItemContabil;

	private ImpostoTipo impostoTipo;

	private ContaBancaria contaBancaria;

	private ContaContabil contaContabilDebito;

	private ContaContabil contaContabilCredito;

	private Short indicadorUso;

	private Date ultimaAlteracao;

	private String descricaoDocumento;

	private String descricaoComplemento;

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

	public ContaBancaria getContaBancaria(){

		return contaBancaria;
	}

	public void setContaBancaria(ContaBancaria contaBancaria){

		this.contaBancaria = contaBancaria;
	}

	public ContaContabil getContaContabilDebito(){

		return contaContabilDebito;
	}

	public void setContaContabilDebito(ContaContabil contaContabilDebito){

		this.contaContabilDebito = contaContabilDebito;
	}

	public ContaContabil getContaContabilCredito(){

		return contaContabilCredito;
	}

	public void setContaContabilCredito(ContaContabil contaContabilCredito){

		this.contaContabilCredito = contaContabilCredito;
	}

	public static long getSerialversionuid(){

		return serialVersionUID;
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

		this.setUltimaAlteracao(ultimaAlteracao);
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		// TODO Auto-generated method stub
		return null;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public void setCategoria(Categoria categoria){

		this.categoria = categoria;
	}

	public Categoria getCategoria(){

		return categoria;
	}

	public void setImpostoTipo(ImpostoTipo impostoTipo){

		this.impostoTipo = impostoTipo;
	}

	public ImpostoTipo getImpostoTipo(){

		return impostoTipo;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getId(){

		return id;
	}

	public String getDescricaoDocumento(){

		return descricaoDocumento;
	}

	public void setDescricaoDocumento(String descricaoDocumento){

		this.descricaoDocumento = descricaoDocumento;
	}

	public String getDescricaoComplemento(){

		return descricaoComplemento;
	}

	public void setDescricaoComplemento(String descricaoComplemento){

		this.descricaoComplemento = descricaoComplemento;
	}

}
