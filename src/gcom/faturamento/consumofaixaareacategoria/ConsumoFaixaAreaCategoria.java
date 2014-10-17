/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.faturamento.consumofaixaareacategoria;

import gcom.cadastro.imovel.Categoria;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author isilva
 * @date 20/01/2011
 */
@ControleAlteracao()
public class ConsumoFaixaAreaCategoria
				extends ObjetoTransacao {

	public static final int ATRIBUTOS_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR = 26370;

	public static final int ATRIBUTOS_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR = 26371;

	public static final int ATRIBUTOS_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER = 26375;

	private static final long serialVersionUID = 1L;

	private Integer id;

	@ControleAlteracao(value = FiltroConsumoFaixaAreaCategoria.CATEGORIA, funcionalidade = {ATRIBUTOS_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR, ATRIBUTOS_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR, ATRIBUTOS_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER})
	private Categoria categoria;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR, ATRIBUTOS_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR, ATRIBUTOS_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER})
	private Integer faixaInicialArea;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR, ATRIBUTOS_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR, ATRIBUTOS_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER})
	private Integer faixaFinalArea;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR, ATRIBUTOS_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR, ATRIBUTOS_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER})
	private Integer consumoEstimadoArea;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CONSUMO_FAIXA_AREA_CATEGORIA_INSERIR, ATRIBUTOS_CONSUMO_FAIXA_AREA_CATEGORIA_ATUALIZAR, ATRIBUTOS_CONSUMO_FAIXA_AREA_CATEGORIA_REMOVER})
	private Short indicadorUso;

	private Date ultimaAlteracao;

	public ConsumoFaixaAreaCategoria() {

	}

	public ConsumoFaixaAreaCategoria(Integer id, Categoria categoria, Integer faixaInicialArea, Integer faixaFinalArea,
										Integer consumoEstimadoArea, Short indicadorUso, Date ultimaAlteracao) {

		this.id = id;
		this.categoria = categoria;
		this.faixaInicialArea = faixaInicialArea;
		this.faixaFinalArea = faixaFinalArea;
		this.consumoEstimadoArea = consumoEstimadoArea;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @return Descri��o do retorno
	 */
	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param other
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public boolean equals(Object other){

		if((this == other)){
			return true;
		}
		if(!(other instanceof ConsumoFaixaAreaCategoria)){
			return false;
		}
		ConsumoFaixaAreaCategoria castOther = (ConsumoFaixaAreaCategoria) other;

		return (this.getId().equals(castOther.getId()));
	}

	public Filtro retornaFiltro(){

		FiltroConsumoFaixaAreaCategoria filtroConsumoFaixaAreaCategoria = new FiltroConsumoFaixaAreaCategoria();
		filtroConsumoFaixaAreaCategoria.adicionarParametro(new ParametroSimples(FiltroConsumoFaixaAreaCategoria.ID, this.getId()));
		filtroConsumoFaixaAreaCategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroConsumoFaixaAreaCategoria.CATEGORIA);
		return filtroConsumoFaixaAreaCategoria;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	/**
	 * @return the id
	 */
	public Integer getId(){

		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id){

		this.id = id;
	}

	/**
	 * @return the categoria
	 */
	public Categoria getCategoria(){

		return categoria;
	}

	/**
	 * @param categoria
	 *            the categoria to set
	 */
	public void setCategoria(Categoria categoria){

		this.categoria = categoria;
	}

	/**
	 * @return the faixaInicialArea
	 */
	public Integer getFaixaInicialArea(){

		return faixaInicialArea;
	}

	/**
	 * @param faixaInicialArea
	 *            the faixaInicialArea to set
	 */
	public void setFaixaInicialArea(Integer faixaInicialArea){

		this.faixaInicialArea = faixaInicialArea;
	}

	/**
	 * @return the faixaFinalArea
	 */
	public Integer getFaixaFinalArea(){

		return faixaFinalArea;
	}

	/**
	 * @param faixaFinalArea
	 *            the faixaFinalArea to set
	 */
	public void setFaixaFinalArea(Integer faixaFinalArea){

		this.faixaFinalArea = faixaFinalArea;
	}

	/**
	 * @return the consumoEstimadoArea
	 */
	public Integer getConsumoEstimadoArea(){

		return consumoEstimadoArea;
	}

	/**
	 * @param consumoEstimadoArea
	 *            the consumoEstimadoArea to set
	 */
	public void setConsumoEstimadoArea(Integer consumoEstimadoArea){

		this.consumoEstimadoArea = consumoEstimadoArea;
	}

	/**
	 * @return the indicadorUso
	 */
	public Short getIndicadorUso(){

		return indicadorUso;
	}

	/**
	 * @param indicadorUso
	 *            the indicadorUso to set
	 */
	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	/**
	 * @return the ultimaAlteracao
	 */
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	/**
	 * @param ultimaAlteracao
	 *            the ultimaAlteracao to set
	 */
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"id", "categoria.id"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"ID", "Categoria"};
		return labels;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}
}