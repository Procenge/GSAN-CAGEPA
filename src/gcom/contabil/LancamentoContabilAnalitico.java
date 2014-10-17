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
 * @created 6 de Julho de 2011
 * @version 1.0
 */

public class LancamentoContabilAnalitico
				extends ObjetoTransacao {

	private static final long serialVersionUID = 3421607336298855932L;

	private Long id;

	private LancamentoContabilSintetico lancamentoContabilSintetico;

	private BigDecimal valor;

	private Long codigoObjeto;

	private Date ultimaAlteracao;

	private Short indicadorUso;

	public LancamentoContabilSintetico getLancamentoContabilSintetico(){

		return lancamentoContabilSintetico;
	}

	public void setLancamentoContabilSintetico(LancamentoContabilSintetico lancamentoContabilSintetico){

		this.lancamentoContabilSintetico = lancamentoContabilSintetico;
	}

	public BigDecimal getValor(){

		return valor;
	}

	public void setValor(BigDecimal valor){

		this.valor = valor;
	}

	public Long getCodigoObjeto(){

		return codigoObjeto;
	}

	public void setCodigoObjeto(Long codigoObjeto){

		this.codigoObjeto = codigoObjeto;
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

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public void setId(Long id){

		this.id = id;
	}

	public Long getId(){

		return id;
	}

}
