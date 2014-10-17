/* This file is part of GSAN, an integrated service management system for Sanitation
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

package gcom.faturamento;

import gcom.util.tabelaauxiliar.abreviada.TabelaAuxiliarAbreviada;

import java.io.Serializable;

/**
 * @author eduardo henrique
 * @date 23/08/2008
 * @since v0.04
 */
public class LocalEntregaDocumento
				extends TabelaAuxiliarAbreviada
				implements Serializable {

	/*
	 * private static final long serialVersionUID = 1L;
	 * private Integer id;
	 * private String descricaoLocalEntregaDocumento;
	 * private String descricaoAbreviadaLocalEntregaDocumento;
	 * private Short indicadorUso;
	 * private Date ultimaAlteracao;
	 */

	/*
	 * (non-Javadoc)
	 * @see gcom.interceptor.ObjetoGcom#retornaCamposChavePrimaria()
	 */
	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	/**
	 * @return the id
	 */
	/*
	 * public Integer getId(){
	 * return id;
	 * }
	 *//**
	 * @param id
	 *            the id to set
	 */
	/*
	 * public void setId(Integer id){
	 * this.id = id;
	 * }
	 *//**
	 * @return the descricaoLocalEntregaDocumento
	 */
	/*
	 * public String getDescricaoLocalEntregaDocumento(){
	 * return descricaoLocalEntregaDocumento;
	 * }
	 *//**
	 * @param descricaoLocalEntregaDocumento
	 *            the descricaoLocalEntregaDocumento to set
	 */
	/*
	 * public void setDescricaoLocalEntregaDocumento(String descricaoLocalEntregaDocumento){
	 * this.descricaoLocalEntregaDocumento = descricaoLocalEntregaDocumento;
	 * }
	 *//**
	 * @return the descricaoAbreviadaLocalEntregaDocumento
	 */
	/*
	 * public String getDescricaoAbreviadaLocalEntregaDocumento(){
	 * return descricaoAbreviadaLocalEntregaDocumento;
	 * }
	 *//**
	 * @param descricaoAbreviadaLocalEntregaDocumento
	 *            the descricaoAbreviadaLocalEntregaDocumento to set
	 */
	/*
	 * public void setDescricaoAbreviadaLocalEntregaDocumento(String
	 * descricaoAbreviadaLocalEntregaDocumento){
	 * this.descricaoAbreviadaLocalEntregaDocumento = descricaoAbreviadaLocalEntregaDocumento;
	 * }
	 *//**
	 * @return the indicadorUso
	 */
	/*
	 * public Short getIndicadorUso(){
	 * return indicadorUso;
	 * }
	 *//**
	 * @param indicadorUso
	 *            the indicadorUso to set
	 */
	/*
	 * public void setIndicadorUso(Short indicadorUso){
	 * this.indicadorUso = indicadorUso;
	 * }
	 *//**
	 * @return the ultimaAlteracao
	 */
	/*
	 * public Date getUltimaAlteracao(){
	 * return ultimaAlteracao;
	 * }
	 *//**
	 * @param ultimaAlteracao
	 *            the ultimaAlteracao to set
	 */
	/*
	 * public void setUltimaAlteracao(Date ultimaAlteracao){
	 * this.ultimaAlteracao = ultimaAlteracao;
	 * }
	 * @Override
	 * public Filtro retornaFiltro(){
	 * FiltroLocalEntregaDocumento filtroLocalEntregaDocumento = new FiltroLocalEntregaDocumento();
	 * return filtroLocalEntregaDocumento;
	 * }
	 */

}
