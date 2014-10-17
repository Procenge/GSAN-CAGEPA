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
 * Eduardo Henrique
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

package gcom.faturamento.bean;

import java.math.BigDecimal;

/**
 * @author eduardo henrique
 *         Classe Helper que serve de Suporte para Geração do Guia_Pagamento_Categoria
 *         na inserção de uma nova Guia de Pagamento
 */
public class GuiaPagamentoCategoriaHelper {

	private Integer idLancamentoItemContabil;

	private Short numeroPrestacao;

	private BigDecimal valorAcumulado;

	private Integer idCategoria;

	/**
	 * @return the idLancamentoItemContabil
	 */
	public Integer getIdLancamentoItemContabil(){

		return idLancamentoItemContabil;
	}

	/**
	 * @param idLancamentoItemContabil
	 *            the idLancamentoItemContabil to set
	 */
	public void setIdLancamentoItemContabil(Integer idLancamentoItemContabil){

		this.idLancamentoItemContabil = idLancamentoItemContabil;
	}

	/**
	 * @return the numeroPrestacao
	 */
	public Short getNumeroPrestacao(){

		return numeroPrestacao;
	}

	/**
	 * @param numeroPrestacao
	 *            the numeroPrestacao to set
	 */
	public void setNumeroPrestacao(Short numeroPrestacao){

		this.numeroPrestacao = numeroPrestacao;
	}

	/**
	 * @return the valorAcumulado
	 */
	public BigDecimal getValorAcumulado(){

		return valorAcumulado;
	}

	/**
	 * @param valorAcumulado
	 *            the valorAcumulado to set
	 */
	public void setValorAcumulado(BigDecimal valorAcumulado){

		this.valorAcumulado = valorAcumulado;
	}

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLancamentoItemContabil == null) ? 0 : idLancamentoItemContabil.hashCode());
		result = prime * result + ((numeroPrestacao == null) ? 0 : numeroPrestacao.hashCode());

		result = prime * result + ((idCategoria == null) ? 0 : idCategoria.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof GuiaPagamentoCategoriaHelper)) return false;
		final GuiaPagamentoCategoriaHelper other = (GuiaPagamentoCategoriaHelper) obj;
		if(idLancamentoItemContabil == null){
			if(other.idLancamentoItemContabil != null) return false;
		}else if(!idLancamentoItemContabil.equals(other.idLancamentoItemContabil)) return false;
		if(numeroPrestacao == null){
			if(other.numeroPrestacao != null) return false;
		}else if(!numeroPrestacao.equals(other.numeroPrestacao)) return false;
		if(idCategoria == null){
			if(other.idCategoria != null) return false;
		}else if(!idCategoria.equals(other.idCategoria)) return false;
		return true;
	}

	/**
	 * @return the idCategoria
	 */
	public Integer getIdCategoria(){

		return idCategoria;
	}

	/**
	 * @param idCategoria
	 *            the idCategoria to set
	 */
	public void setIdCategoria(Integer idCategoria){

		this.idCategoria = idCategoria;
	}

}
