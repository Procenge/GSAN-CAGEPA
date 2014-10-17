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
 */

package gcom.cadastro.tarifasocial;

import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelEconomia;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class TarifaSocialDadoEconomiaHistorico
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private Long numeroCartaoProgramaSocial;

	/** nullable persistent field */
	private Short numeroMesesAdesao;

	/** nullable persistent field */
	private Integer consumoCelpe;

	/** nullable persistent field */
	private BigDecimal valorRendaFamiliar;

	/** nullable persistent field */
	private Date dataValidadeCartao;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	// private gcom.cadastro.tarifasocial.TarifaSocialDado tarifaSocialDado;
	/** persistent field */
	private gcom.cadastro.tarifasocial.RendaTipo rendaTipo;

	/** persistent field */
	private gcom.cadastro.tarifasocial.TarifaSocialCartaoTipo tarifaSocialCartaoTipo;

	/** persistent field */
	private ImovelEconomia imovelEconomia;

	/** nullable persistent field */
	private Date dataImplantacao;

	/** nullable persistent field */
	private Date dataExclusao;

	/** nullable persistent field */
	private Date dataRevisao;

	/** nullable persistent field */
	private Short quantidadeRecadastramento;

	/** nullable persistent field */
	private Date dataRecadastramento;

	/** persistent field */
	private TarifaSocialRevisaoMotivo tarifaSocialRevisaoMotivo;

	/** persistent field */
	private TarifaSocialExclusaoMotivo tarifaSocialExclusaoMotivo;

	/** persistent field */
	private Imovel imovel;

	/** nullable persistent field */
	private Date ultimaAlteracaoTarifaSocialHistorico;

	/** full constructor */
	public TarifaSocialDadoEconomiaHistorico(Long numeroCartaoProgramaSocial, Short numeroMesesAdesao, Integer consumoCelpe,
									BigDecimal valorRendaFamiliar, Date dataValidadeCartao, Date ultimaAlteracao,
									gcom.cadastro.tarifasocial.RendaTipo rendaTipo,
									gcom.cadastro.tarifasocial.TarifaSocialCartaoTipo tarifaSocialCartaoTipo, ImovelEconomia imovelEconomia) {

		this.numeroCartaoProgramaSocial = numeroCartaoProgramaSocial;
		this.numeroMesesAdesao = numeroMesesAdesao;
		this.consumoCelpe = consumoCelpe;
		this.valorRendaFamiliar = valorRendaFamiliar;
		this.dataValidadeCartao = dataValidadeCartao;
		this.ultimaAlteracao = ultimaAlteracao;
		this.rendaTipo = rendaTipo;
		this.tarifaSocialCartaoTipo = tarifaSocialCartaoTipo;
		this.imovelEconomia = imovelEconomia;
	}

	/** default constructor */
	public TarifaSocialDadoEconomiaHistorico() {

	}

	/** minimal constructor */
	public TarifaSocialDadoEconomiaHistorico(gcom.cadastro.tarifasocial.RendaTipo rendaTipo,
									gcom.cadastro.tarifasocial.TarifaSocialCartaoTipo tarifaSocialCartaoTipo, ImovelEconomia imovelEconomia) {

		this.rendaTipo = rendaTipo;
		this.tarifaSocialCartaoTipo = tarifaSocialCartaoTipo;
		this.imovelEconomia = imovelEconomia;
	}

	public TarifaSocialDadoEconomiaHistorico(TarifaSocialDadoEconomia tarifaSocialDadoEconomia) {

		this.consumoCelpe = tarifaSocialDadoEconomia.getConsumoCelpe();
		this.dataExclusao = tarifaSocialDadoEconomia.getDataExclusao();
		this.dataImplantacao = tarifaSocialDadoEconomia.getDataImplantacao();
		this.dataRecadastramento = tarifaSocialDadoEconomia.getDataRecadastramento();
		this.dataRevisao = tarifaSocialDadoEconomia.getDataRevisao();
		this.dataValidadeCartao = tarifaSocialDadoEconomia.getDataValidadeCartao();
		this.id = tarifaSocialDadoEconomia.getId();
		this.imovel = tarifaSocialDadoEconomia.getImovel();
		this.imovelEconomia = tarifaSocialDadoEconomia.getImovelEconomia();
		this.numeroCartaoProgramaSocial = tarifaSocialDadoEconomia.getNumeroCartaoProgramaSocial();
		this.numeroMesesAdesao = tarifaSocialDadoEconomia.getNumeroMesesAdesao();
		this.quantidadeRecadastramento = tarifaSocialDadoEconomia.getQuantidadeRecadastramento();
		this.rendaTipo = tarifaSocialDadoEconomia.getRendaTipo();
		this.tarifaSocialCartaoTipo = tarifaSocialDadoEconomia.getTarifaSocialCartaoTipo();
		this.tarifaSocialExclusaoMotivo = tarifaSocialDadoEconomia.getTarifaSocialExclusaoMotivo();
		this.tarifaSocialRevisaoMotivo = tarifaSocialDadoEconomia.getTarifaSocialRevisaoMotivo();
		this.ultimaAlteracao = tarifaSocialDadoEconomia.getUltimaAlteracao();
		this.valorRendaFamiliar = tarifaSocialDadoEconomia.getValorRendaFamiliar();

	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Long getNumeroCartaoProgramaSocial(){

		return this.numeroCartaoProgramaSocial;
	}

	public void setNumeroCartaoProgramaSocial(Long numeroCartaoProgramaSocial){

		this.numeroCartaoProgramaSocial = numeroCartaoProgramaSocial;
	}

	public Short getNumeroMesesAdesao(){

		return this.numeroMesesAdesao;
	}

	public void setNumeroMesesAdesao(Short numeroMesesAdesao){

		this.numeroMesesAdesao = numeroMesesAdesao;
	}

	public Integer getConsumoCelpe(){

		return this.consumoCelpe;
	}

	public void setConsumoCelpe(Integer consumoCelpe){

		this.consumoCelpe = consumoCelpe;
	}

	public BigDecimal getValorRendaFamiliar(){

		return this.valorRendaFamiliar;
	}

	public void setValorRendaFamiliar(BigDecimal valorRendaFamiliar){

		this.valorRendaFamiliar = valorRendaFamiliar;
	}

	public Date getDataValidadeCartao(){

		return this.dataValidadeCartao;
	}

	public String getDataValidadeCartaoFormatada(){

		if(this.dataValidadeCartao != null){
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			return formato.format(this.dataValidadeCartao);
		}else{
			return "";
		}
	}

	public void setDataValidadeCartao(Date dataValidadeCartao){

		this.dataValidadeCartao = dataValidadeCartao;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public gcom.cadastro.tarifasocial.RendaTipo getRendaTipo(){

		return this.rendaTipo;
	}

	public void setRendaTipo(gcom.cadastro.tarifasocial.RendaTipo rendaTipo){

		this.rendaTipo = rendaTipo;
	}

	public gcom.cadastro.tarifasocial.TarifaSocialCartaoTipo getTarifaSocialCartaoTipo(){

		return this.tarifaSocialCartaoTipo;
	}

	public void setTarifaSocialCartaoTipo(gcom.cadastro.tarifasocial.TarifaSocialCartaoTipo tarifaSocialCartaoTipo){

		this.tarifaSocialCartaoTipo = tarifaSocialCartaoTipo;
	}

	public ImovelEconomia getImovelEconomia(){

		return this.imovelEconomia;
	}

	public void setImovelEconomia(ImovelEconomia imovelEconomia){

		this.imovelEconomia = imovelEconomia;
	}

	/**
	 * @return Retorna o campo dataExclusao.
	 */
	public Date getDataExclusao(){

		return dataExclusao;
	}

	/**
	 * @param dataExclusao
	 *            O dataExclusao a ser setado.
	 */
	public void setDataExclusao(Date dataExclusao){

		this.dataExclusao = dataExclusao;
	}

	/**
	 * @return Retorna o campo dataImplantacao.
	 */
	public Date getDataImplantacao(){

		return dataImplantacao;
	}

	/**
	 * @param dataImplantacao
	 *            O dataImplantacao a ser setado.
	 */
	public void setDataImplantacao(Date dataImplantacao){

		this.dataImplantacao = dataImplantacao;
	}

	/**
	 * @return Retorna o campo dataRecadastramento.
	 */
	public Date getDataRecadastramento(){

		return dataRecadastramento;
	}

	/**
	 * @param dataRecadastramento
	 *            O dataRecadastramento a ser setado.
	 */
	public void setDataRecadastramento(Date dataRecadastramento){

		this.dataRecadastramento = dataRecadastramento;
	}

	/**
	 * @return Retorna o campo dataRevisao.
	 */
	public Date getDataRevisao(){

		return dataRevisao;
	}

	/**
	 * @param dataRevisao
	 *            O dataRevisao a ser setado.
	 */
	public void setDataRevisao(Date dataRevisao){

		this.dataRevisao = dataRevisao;
	}

	/**
	 * @return Retorna o campo quantidadeRecadastramento.
	 */
	public Short getQuantidadeRecadastramento(){

		return quantidadeRecadastramento;
	}

	/**
	 * @param quantidadeRecadastramento
	 *            O quantidadeRecadastramento a ser setado.
	 */
	public void setQuantidadeRecadastramento(Short quantidadeRecadastramento){

		this.quantidadeRecadastramento = quantidadeRecadastramento;
	}

	/**
	 * @return Retorna o campo tarifaSocialExclusaoMotivo.
	 */
	public TarifaSocialExclusaoMotivo getTarifaSocialExclusaoMotivo(){

		return tarifaSocialExclusaoMotivo;
	}

	/**
	 * @param tarifaSocialExclusaoMotivo
	 *            O tarifaSocialExclusaoMotivo a ser setado.
	 */
	public void setTarifaSocialExclusaoMotivo(TarifaSocialExclusaoMotivo tarifaSocialExclusaoMotivo){

		this.tarifaSocialExclusaoMotivo = tarifaSocialExclusaoMotivo;
	}

	/**
	 * @return Retorna o campo tarifaSocialRevisaoMotivo.
	 */
	public TarifaSocialRevisaoMotivo getTarifaSocialRevisaoMotivo(){

		return tarifaSocialRevisaoMotivo;
	}

	/**
	 * @param tarifaSocialRevisaoMotivo
	 *            O tarifaSocialRevisaoMotivo a ser setado.
	 */
	public void setTarifaSocialRevisaoMotivo(TarifaSocialRevisaoMotivo tarifaSocialRevisaoMotivo){

		this.tarifaSocialRevisaoMotivo = tarifaSocialRevisaoMotivo;
	}

	/**
	 * @return Retorna o campo imovel.
	 */
	public Imovel getImovel(){

		return imovel;
	}

	/**
	 * @param imovel
	 *            O imovel a ser setado.
	 */
	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"numeroCartaoProgramaSocial"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"N. Cartao Programa Social"};
		return labels;
	}

	public Filtro retornaFiltro(){

		FiltroTarifaSocialDadoEconomiaHistorico filtroTarifaSocialDadoEconomiaHistorico = new FiltroTarifaSocialDadoEconomiaHistorico();
		filtroTarifaSocialDadoEconomiaHistorico.adicionarParametro(new ParametroSimples(FiltroTarifaSocialDadoEconomia.ID, this.getId()));
		filtroTarifaSocialDadoEconomiaHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroTarifaSocialDadoEconomia.IMOVEL);
		filtroTarifaSocialDadoEconomiaHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroTarifaSocialDadoEconomia.IMOVEL_ECONOMEIA);
		filtroTarifaSocialDadoEconomiaHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroTarifaSocialDadoEconomia.RENDA_TIPO);
		filtroTarifaSocialDadoEconomiaHistorico
						.adicionarCaminhoParaCarregamentoEntidade(FiltroTarifaSocialDadoEconomia.TARIFA_SOCIAL_CARTAO_TIPO);
		filtroTarifaSocialDadoEconomiaHistorico
						.adicionarCaminhoParaCarregamentoEntidade(FiltroTarifaSocialDadoEconomia.TARIFA_SOCIAL_REVISAO_MOTIVO);
		filtroTarifaSocialDadoEconomiaHistorico
						.adicionarCaminhoParaCarregamentoEntidade(FiltroTarifaSocialDadoEconomia.TARIFA_SOCIAL_EXCLUSAO_MOTIVO);
		return filtroTarifaSocialDadoEconomiaHistorico;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}

	/**
	 * @return the ultimaAlteracaoTarifaSocialHistorico
	 */
	public Date getUltimaAlteracaoTarifaSocialHistorico(){

		return ultimaAlteracaoTarifaSocialHistorico;
	}

	/**
	 * @param ultimaAlteracaoTarifaSocialHistorico
	 *            the ultimaAlteracaoTarifaSocialHistorico to set
	 */
	public void setUltimaAlteracaoTarifaSocialHistorico(Date ultimaAlteracaoTarifaSocialHistorico){

		this.ultimaAlteracaoTarifaSocialHistorico = ultimaAlteracaoTarifaSocialHistorico;
	}

}
