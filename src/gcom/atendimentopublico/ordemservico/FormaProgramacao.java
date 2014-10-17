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
 * André Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * José Gilberto de França Matos
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. 
 * Consulte a Licença Pública Geral GNU para obter mais detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.atendimentopublico.ordemservico;

import gcom.util.tabelaauxiliar.abreviada.TabelaAuxiliarAbreviada;

import java.io.Serializable;

public class FormaProgramacao
				extends TabelaAuxiliarAbreviada
				implements Serializable {

	private static final long serialVersionUID = -6307545921109825542L;

	/*
	 * private Integer id;
	 * private String descricao;
	 * private String descricaoAbreviada;
	 * private Date ultimaAlteracao;
	 * private int indicadorUso;
	 */
	public enum FormaProgramacaoServico {
		MESMO_DIA(1), SOLICITA_AUTORIZACAO(2), DIA_POSTERIOR(3), POSTERIOR(4);

		private final int idFormaProgramacao;

		FormaProgramacaoServico(int idFormaProgramacao) {

			this.idFormaProgramacao = idFormaProgramacao;
		}

		public int valorId(){

			return this.idFormaProgramacao;
		}

	}

	/*
	 * public FormaProgramacao(String descricao, String descricaoAbreviada, Date ultimaAlteracao,
	 * int indicadorUso) {
	 * this.descricao = descricao;
	 * this.descricaoAbreviada = descricaoAbreviada;
	 * this.ultimaAlteracao = ultimaAlteracao;
	 * this.indicadorUso = indicadorUso;
	 * }
	 */

	public FormaProgramacao() {

	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	/*
	 * public Integer getId(){
	 * return id;
	 * }
	 * public void setId(Integer id){
	 * this.id = id;
	 * }
	 * public String getDescricao(){
	 * return descricao;
	 * }
	 * public void setDescricao(String descricao){
	 * this.descricao = descricao;
	 * }
	 * public String getDescricaoAbreviada(){
	 * return descricaoAbreviada;
	 * }
	 * public void setDescricaoAbreviada(String descricaoAbreviada){
	 * this.descricaoAbreviada = descricaoAbreviada;
	 * }
	 * public Date getUltimaAlteracao(){
	 * return ultimaAlteracao;
	 * }
	 * public void setUltimaAlteracao(Date ultimaAlteracao){
	 * this.ultimaAlteracao = ultimaAlteracao;
	 * }
	 * public int getIndicadorUso(){
	 * return indicadorUso;
	 * }
	 * public void setIndicadorUso(int indicadorUso){
	 * this.indicadorUso = indicadorUso;
	 * }
	 */

}
