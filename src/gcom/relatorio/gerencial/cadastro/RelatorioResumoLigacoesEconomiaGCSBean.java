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
 * 
 * GSANPCG
 * Eduardo Henrique
 */
package gcom.relatorio.gerencial.cadastro;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.cadastro.imovel.Categoria;
import gcom.gerencial.cadastro.bean.DetalheLigacaoEconomiaGCSHelper;
import gcom.gerencial.cadastro.bean.SumarioLigacaoPorCategoriaGCSHelper;
import gcom.relatorio.RelatorioBean;
import gcom.util.Util;

import java.math.BigDecimal;


/**
 * @author mgrb
 *
 */
public class RelatorioResumoLigacoesEconomiaGCSBean
				implements RelatorioBean {

	public static final int ID_GRUPO_LIGACOES = 1;

	public static final int ID_GRUPO_ECONOMIAS = 2;

	private Integer totalizadorId;

	private String totalizador;

	private String subTitulo;

	private String itemDescricao;

	private BigDecimal itemResComHid;

	private BigDecimal itemResSemHid;

	private BigDecimal itemResTotal;

	private BigDecimal itemComercComHid;

	private BigDecimal itemComercSemHid;

	private BigDecimal itemComercTotal;

	private BigDecimal itemIndComHid;

	private BigDecimal itemIndSemHid;

	private BigDecimal itemIndTotal;

	private BigDecimal itemPubComHid;

	private BigDecimal itemPubSemHid;

	private BigDecimal itemPubTotal;

	private BigDecimal itemTotalComHid;

	private BigDecimal itemTotalSemHid;

	private BigDecimal itemTotalTotal;

	private BigDecimal itemMistoComHid;

	private BigDecimal itemMistoSemHid;

	private BigDecimal itemMistoTotal;

	/**
	 * RelatorioResumoLigacoesEconomiaGCSBean
	 * <p>
	 * Esse método Constroi uma instância de RelatorioResumoLigacoesEconomiaGCSBean.
	 * </p>
	 * 
	 * @param totalizadorId2
	 * @param totalizadorDesc
	 * @param grupo
	 * @author Marlos Ribeiro
	 * @since 22/11/2012
	 */
	public RelatorioResumoLigacoesEconomiaGCSBean(Integer totalizadorId, String totalizadorDesc, String subTitulo, String itemDescricao) {

		this.totalizadorId = totalizadorId;
		this.totalizador = totalizadorDesc;
		this.itemDescricao = itemDescricao;
		this.subTitulo = subTitulo;
	}

	public String getTotalizador(){

		return totalizador;
	}

	public void setTotalizador(String totalizador){

		this.totalizador = totalizador;
	}

	public Integer getTotalizadorId(){

		return totalizadorId;
	}

	public void setTotalizadorId(Integer totalizadorId){

		this.totalizadorId = totalizadorId;
	}

	public String getSubTitulo(){

		return subTitulo;
	}

	public void setSubTitulo(String subTitulo){

		this.subTitulo = subTitulo;
	}

	public String getItemDescricao(){

		return itemDescricao;
	}

	public void setItemDescricao(String itemDescricao){

		this.itemDescricao = itemDescricao;
	}

	public BigDecimal getItemResComHid(){

		return itemResComHid;
	}

	public void setItemResComHid(BigDecimal itemResComHid){

		this.itemResComHid = itemResComHid;
	}

	public BigDecimal getItemResSemHid(){

		return itemResSemHid;
	}

	public void setItemResSemHid(BigDecimal itemResSemHid){

		this.itemResSemHid = itemResSemHid;
	}

	public BigDecimal getItemResTotal(){

		return itemResTotal;
	}

	public void setItemResTotal(BigDecimal itemResTotal){

		this.itemResTotal = itemResTotal;
	}

	public BigDecimal getItemComercComHid(){

		return itemComercComHid;
	}

	public void setItemComercComHid(BigDecimal itemComercComHid){

		this.itemComercComHid = itemComercComHid;
	}

	public BigDecimal getItemComercSemHid(){

		return itemComercSemHid;
	}

	public void setItemComercSemHid(BigDecimal itemComercSemHid){

		this.itemComercSemHid = itemComercSemHid;
	}

	public BigDecimal getItemComercTotal(){

		return itemComercTotal;
	}

	public void setItemComercTotal(BigDecimal itemComercTotal){

		this.itemComercTotal = itemComercTotal;
	}

	public BigDecimal getItemIndComHid(){

		return itemIndComHid;
	}

	public void setItemIndComHid(BigDecimal itemIndComHid){

		this.itemIndComHid = itemIndComHid;
	}

	public BigDecimal getItemIndSemHid(){

		return itemIndSemHid;
	}

	public void setItemIndSemHid(BigDecimal itemIndSemHid){

		this.itemIndSemHid = itemIndSemHid;
	}

	public BigDecimal getItemIndTotal(){

		return itemIndTotal;
	}

	public void setItemIndTotal(BigDecimal itemIndTotal){

		this.itemIndTotal = itemIndTotal;
	}

	public BigDecimal getItemPubComHid(){

		return itemPubComHid;
	}

	public void setItemPubComHid(BigDecimal itemPubComHid){

		this.itemPubComHid = itemPubComHid;
	}

	public BigDecimal getItemPubSemHid(){

		return itemPubSemHid;
	}

	public void setItemPubSemHid(BigDecimal itemPubSemHid){

		this.itemPubSemHid = itemPubSemHid;
	}

	public BigDecimal getItemPubTotal(){

		return itemPubTotal;
	}

	public void setItemPubTotal(BigDecimal itemPubTotal){

		this.itemPubTotal = itemPubTotal;
	}

	public BigDecimal getItemTotalComHid(){

		return itemTotalComHid;
	}

	public void setItemTotalComHid(BigDecimal itemTotalComHid){

		this.itemTotalComHid = itemTotalComHid;
	}

	public BigDecimal getItemTotalSemHid(){

		return itemTotalSemHid;
	}

	public void setItemTotalSemHid(BigDecimal itemTotalSemHid){

		this.itemTotalSemHid = itemTotalSemHid;
	}

	public BigDecimal getItemTotalTotal(){

		return itemTotalTotal;
	}

	public void setItemTotalTotal(BigDecimal itemTotalTotal){

		this.itemTotalTotal = itemTotalTotal;
	}

	public BigDecimal getItemMistoComHid(){

		return itemMistoComHid;
	}

	public void setItemMistoComHid(BigDecimal itemMistoComHid){

		this.itemMistoComHid = itemMistoComHid;
	}

	public BigDecimal getItemMistoSemHid(){

		return itemMistoSemHid;
	}

	public void setItemMistoSemHid(BigDecimal itemMistoSemHid){

		this.itemMistoSemHid = itemMistoSemHid;
	}

	public BigDecimal getItemMistoTotal(){

		return itemMistoTotal;
	}

	public void setItemMistoTotal(BigDecimal itemMistoTotal){

		this.itemMistoTotal = itemMistoTotal;
	}

	/**
	 * Método addSumerio
	 * <p>
	 * Esse método implementa sumarizacao dos totais das {@link LigacaoAguaSituacao}.
	 * </p>
	 * RASTREIO: [OC897714][UC0269]
	 * 
	 * @param sumario
	 * @author Marlos Ribeiro
	 * @since 22/11/2012
	 */
	public void addSumario(Integer grupoId,SumarioLigacaoPorCategoriaGCSHelper sumario){

		BigDecimal valorSumario = null;
		switch(grupoId){
			case ID_GRUPO_LIGACOES:
				valorSumario = BigDecimal.valueOf(sumario.getQtdLigacoes());
				break;
			case ID_GRUPO_ECONOMIAS:
				valorSumario = BigDecimal.valueOf(sumario.getQtdEconomias());
				break;
		}
		switch(sumario.getCategoriaId()){
			case Categoria.COMERCIAL_INT:
				itemComercTotal = itemComercTotal.add(valorSumario);
				break;
			case Categoria.INDUSTRIAL_INT:
				itemIndTotal = itemIndTotal.add(valorSumario);
				break;
			case Categoria.PUBLICO_INT:
				itemPubTotal = itemPubTotal.add(valorSumario);
				break;
			case Categoria.RESIDENCIAL_INT:
				itemResTotal = itemResTotal.add(valorSumario);
				break;
		}

		itemTotalTotal = itemTotalTotal == null ? valorSumario : itemTotalTotal.add(valorSumario);
	}

	/**
	 * Método resetTotais
	 * <p>
	 * Esse método implementa o reset dos TOTAIS.
	 * </p>
	 * RASTREIO: [OC897714][UC0269]
	 * 
	 * @author Marlos Ribeiro
	 * @since 22/11/2012
	 */
	public void resetTotais(){

		itemComercTotal = BigDecimal.ZERO;
		itemIndTotal = BigDecimal.ZERO;
		itemMistoTotal = BigDecimal.ZERO;
		itemPubTotal = BigDecimal.ZERO;
		itemResTotal = BigDecimal.ZERO;
		itemTotalTotal = BigDecimal.ZERO;

	}

	/**
	 * Método resetAll
	 * <p>
	 * Esse método implementa o reset de todos os campos.
	 * </p>
	 * RASTREIO: [OC897714][UC0269]
	 * 
	 * @author Marlos Ribeiro
	 * @since 22/11/2012
	 */
	public void resetAll(){

		itemComercComHid = BigDecimal.ZERO;
		itemComercSemHid = BigDecimal.ZERO;
		itemComercTotal = BigDecimal.ZERO;

		itemIndComHid = BigDecimal.ZERO;
		itemIndSemHid = BigDecimal.ZERO;
		itemIndTotal = BigDecimal.ZERO;

		itemMistoComHid = BigDecimal.ZERO;
		itemMistoSemHid = BigDecimal.ZERO;
		itemMistoTotal = BigDecimal.ZERO;

		itemPubComHid = BigDecimal.ZERO;
		itemPubSemHid = BigDecimal.ZERO;
		itemPubTotal = BigDecimal.ZERO;

		itemResComHid = BigDecimal.ZERO;
		itemResSemHid = BigDecimal.ZERO;
		itemResTotal = BigDecimal.ZERO;

		itemTotalComHid = BigDecimal.ZERO;
		itemTotalSemHid = BigDecimal.ZERO;
		itemTotalTotal = BigDecimal.ZERO;
	}

	/**
	 * Método addDetalhe
	 * <p>
	 * Esse método implementa o preenchimento do detalhe.
	 * </p>
	 * RASTREIO: [OC897714][UC0269]
	 * 
	 * @param detalhe
	 * @param tipoGrupo
	 * @param categoriaId
	 * @author Marlos Ribeiro
	 * @since 22/11/2012
	 */
	public void addDetalhe(DetalheLigacaoEconomiaGCSHelper detalhe, int tipoGrupo, Integer categoriaId){

		switch(categoriaId){
			case Categoria.COMERCIAL_INT:
				if(ID_GRUPO_ECONOMIAS == tipoGrupo){
					itemComercComHid = BigDecimal.valueOf(detalhe.getEconomiasComHidrometro());
					itemComercSemHid = BigDecimal.valueOf(detalhe.getEconomiasSemHidrometro());
				}else if(ID_GRUPO_LIGACOES == tipoGrupo){
					itemComercComHid = BigDecimal.valueOf(detalhe.getLigacoesComHidrometro());
					itemComercSemHid = BigDecimal.valueOf(detalhe.getLigacoesSemHidrometro());
				}
				itemComercTotal = itemComercComHid.add(itemComercSemHid);
				break;
			case Categoria.INDUSTRIAL_INT:
				if(ID_GRUPO_ECONOMIAS == tipoGrupo){
					itemIndComHid = BigDecimal.valueOf(detalhe.getEconomiasComHidrometro());
					itemIndSemHid = BigDecimal.valueOf(detalhe.getEconomiasSemHidrometro());
				}else if(ID_GRUPO_LIGACOES == tipoGrupo){
					itemIndComHid = BigDecimal.valueOf(detalhe.getLigacoesComHidrometro());
					itemIndSemHid = BigDecimal.valueOf(detalhe.getLigacoesSemHidrometro());
				}
				itemIndTotal = itemIndComHid.add(itemIndSemHid);
				break;
			case Categoria.PUBLICO_INT:
				if(ID_GRUPO_ECONOMIAS == tipoGrupo){
					itemPubComHid = BigDecimal.valueOf(detalhe.getEconomiasComHidrometro());
					itemPubSemHid = BigDecimal.valueOf(detalhe.getEconomiasSemHidrometro());
				}else if(ID_GRUPO_LIGACOES == tipoGrupo){
					itemPubComHid = BigDecimal.valueOf(detalhe.getLigacoesComHidrometro());
					itemPubSemHid = BigDecimal.valueOf(detalhe.getLigacoesSemHidrometro());
				}
				itemPubTotal = itemPubComHid.add(itemPubSemHid);
				break;
			case Categoria.RESIDENCIAL_INT:
				if(ID_GRUPO_ECONOMIAS == tipoGrupo){
					itemResComHid = BigDecimal.valueOf(detalhe.getEconomiasComHidrometro());
					itemResSemHid = BigDecimal.valueOf(detalhe.getEconomiasSemHidrometro());
				}else if(ID_GRUPO_LIGACOES == tipoGrupo){
					itemResComHid = BigDecimal.valueOf(detalhe.getLigacoesComHidrometro());
					itemResSemHid = BigDecimal.valueOf(detalhe.getLigacoesSemHidrometro());
				}
				itemResTotal = itemResComHid.add(itemResSemHid);
				break;
			case Categoria.MISTO_INT:
				if(ID_GRUPO_ECONOMIAS == tipoGrupo){
					itemMistoComHid = BigDecimal.valueOf(detalhe.getEconomiasComHidrometro());
					itemMistoSemHid = BigDecimal.valueOf(detalhe.getEconomiasSemHidrometro());
				}else if(ID_GRUPO_LIGACOES == tipoGrupo){
					itemMistoComHid = BigDecimal.valueOf(detalhe.getLigacoesComHidrometro());
					itemMistoSemHid = BigDecimal.valueOf(detalhe.getLigacoesSemHidrometro());
				}
				itemMistoTotal = itemMistoComHid.add(itemMistoSemHid);
				break;
		}
		totalizar();
	}

	private void totalizar(){

		itemTotalComHid = itemComercComHid.add(itemIndComHid).add(itemPubComHid).add(itemResComHid).add(itemMistoComHid);
		itemTotalSemHid = itemComercSemHid.add(itemIndSemHid).add(itemPubSemHid).add(itemResSemHid).add(itemMistoSemHid);
		itemTotalTotal = itemTotalComHid.add(itemTotalSemHid);
	}

	/**
	 * Método totalizar
	 * <p>
	 * Esse método implementa a totalizacao das linhas de detalhe.
	 * </p>
	 * RASTREIO: [OC897714][UC0269]
	 * 
	 * @param agua
	 * @param esgoto
	 * @param aguaEsgoto
	 * @author Marlos Ribeiro
	 * @since 22/11/2012
	 */
	public void totalizar(RelatorioResumoLigacoesEconomiaGCSBean... beans){

		for(RelatorioResumoLigacoesEconomiaGCSBean detalhes : beans){
			itemComercComHid = itemComercComHid.add(detalhes.getItemComercComHid());
			itemComercSemHid = itemComercSemHid.add(detalhes.getItemComercSemHid());
			itemComercTotal = itemComercTotal.add(detalhes.getItemComercTotal());

			itemIndComHid = itemIndComHid.add(detalhes.getItemIndComHid());
			itemIndSemHid = itemIndSemHid.add(detalhes.getItemIndSemHid());
			itemIndTotal = itemIndTotal.add(detalhes.getItemIndTotal());

			itemPubComHid = itemPubComHid.add(detalhes.getItemPubComHid());
			itemPubSemHid = itemPubSemHid.add(detalhes.getItemPubSemHid());
			itemPubTotal = itemPubTotal.add(detalhes.getItemPubTotal());

			itemResComHid = itemResComHid.add(detalhes.getItemResComHid());
			itemResSemHid = itemResSemHid.add(detalhes.getItemResSemHid());
			itemResTotal = itemResTotal.add(detalhes.getItemResTotal());

			itemMistoComHid = itemMistoComHid.add(detalhes.getItemMistoComHid());
			itemMistoSemHid = itemMistoSemHid.add(detalhes.getItemMistoSemHid());
			itemMistoTotal = itemMistoTotal.add(detalhes.getItemMistoTotal());
		}

		totalizar();
	}

	/**
	 * Método calcularPercentualDesligados
	 * <p>
	 * Esse método implementa calculo dos percentuais dos desligados.
	 * </p>
	 * RASTREIO: [OC897714][UC0269]
	 * 
	 * @param linhaTotalDesligados
	 * @param linhaTotalFuncionandoMaisDesligadas
	 * @author Marlos Ribeiro
	 * @since 23/11/2012
	 */
	public void calcularPercentualDesligados(RelatorioResumoLigacoesEconomiaGCSBean linhaTotalDesligados,
					RelatorioResumoLigacoesEconomiaGCSBean linhaTotalfuncionandoMaisDesligadas){

		resetTotais();
		itemComercTotal = calcularPercentual(linhaTotalDesligados.getItemComercTotal(),
						linhaTotalfuncionandoMaisDesligadas.getItemComercTotal());
		itemIndTotal = calcularPercentual(linhaTotalDesligados.getItemIndTotal(), linhaTotalfuncionandoMaisDesligadas.getItemIndTotal());
		itemPubTotal = calcularPercentual(linhaTotalDesligados.getItemPubTotal(), linhaTotalfuncionandoMaisDesligadas.getItemPubTotal());
		itemResTotal = calcularPercentual(linhaTotalDesligados.getItemResTotal(), linhaTotalfuncionandoMaisDesligadas.getItemResTotal());
		itemMistoTotal = calcularPercentual(linhaTotalDesligados.getItemMistoTotal(),
						linhaTotalfuncionandoMaisDesligadas.getItemMistoTotal());
		itemTotalTotal = calcularPercentual(linhaTotalDesligados.getItemTotalTotal(),
						linhaTotalfuncionandoMaisDesligadas.getItemTotalTotal());
	}

	/**
	 * Método calcularPercentualMedido
	 * <p>
	 * Esse método implementa calculo dos percentual das ligacoes medidas.
	 * </p>
	 * RASTREIO: [OC897714][UC0269]
	 * 
	 * @param linhaTotalFuncionandoMaisDesligadas
	 * @author Marlos Ribeiro
	 * @since 23/11/2012
	 */
	public void calcularPercentualMedido(RelatorioResumoLigacoesEconomiaGCSBean linhaTotalFuncionandoMaisDesligadas){

		resetTotais();
		itemComercTotal = calcularPercentual(linhaTotalFuncionandoMaisDesligadas.getItemComercComHid(),
						linhaTotalFuncionandoMaisDesligadas.getItemComercTotal());
		itemIndTotal = calcularPercentual(linhaTotalFuncionandoMaisDesligadas.getItemIndComHid(),
						linhaTotalFuncionandoMaisDesligadas.getItemIndTotal());
		itemPubTotal = calcularPercentual(linhaTotalFuncionandoMaisDesligadas.getItemPubComHid(),
						linhaTotalFuncionandoMaisDesligadas.getItemPubTotal());
		itemResTotal = calcularPercentual(linhaTotalFuncionandoMaisDesligadas.getItemResComHid(),
						linhaTotalFuncionandoMaisDesligadas.getItemResTotal());
		itemMistoTotal = calcularPercentual(linhaTotalFuncionandoMaisDesligadas.getItemMistoComHid(),
						linhaTotalFuncionandoMaisDesligadas.getItemMistoTotal());
		itemTotalTotal = calcularPercentual(linhaTotalFuncionandoMaisDesligadas.getItemTotalComHid(),
						linhaTotalFuncionandoMaisDesligadas.getItemTotalTotal());

	}

	/**
	 * Método calcularPotencial
	 * <p>
	 * Esse método implementa calculo de percentual com um decimal de precisao.
	 * </p>
	 * RASTREIO: [OC897714][UC0269]
	 * 
	 * @param itemTotalComHid2
	 * @param itemTotalTotal2
	 * @return
	 * @author Marlos Ribeiro
	 * @since 23/11/2012
	 */
	private BigDecimal calcularPercentual(BigDecimal valorParte, BigDecimal valorTotal){

		return BigDecimal.ZERO.equals(valorTotal) ? BigDecimal.ZERO : Util.calcularPercentualBigDecimal(valorParte, valorTotal).setScale(1,
						BigDecimal.ROUND_HALF_DOWN);
	}

}
