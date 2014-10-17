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

public class LancamentoContabilAnaliticoConsultaHelper {

	private Integer idImovel;

	private Integer idImovelHistorico;

	private String descricaoObjeto;

	private Long complemento;

	private BigDecimal valor;

	private String valorString;

	public String getDescricaoObjeto(){

		return descricaoObjeto;
	}

	public void setDescricaoObjeto(String descricaoObjeto){

		this.descricaoObjeto = descricaoObjeto;
	}

	public Long getComplemento(){

		return complemento;
	}

	public void setComplemento(Long complemento){

		this.complemento = complemento;
	}

	public BigDecimal getValor(){

		return valor;
	}

	public void setValor(BigDecimal valor){

		this.valor = valor;
		this.valorString = Util.formataBigDecimal(valor, 2, Boolean.TRUE);
	}

	public void setValorString(String valorString){

		this.valorString = valorString;
	}

	public String getValorString(){

		return valorString;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovelHistorico(Integer idImovelHistorico){

		this.idImovelHistorico = idImovelHistorico;
	}

	public Integer getIdImovelHistorico(){

		return idImovelHistorico;
	}

}
