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
 * Virgínia Melo
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

package gcom.gui.cobranca.programacobranca;

import org.apache.struts.validator.ValidatorActionForm;

public class PesquisarProgramaCobrancaActionForm
				extends ValidatorActionForm {

	private String codigoProgramaCobranca;

	private String nomeProgramaCobranca;

	private String descricaoProgramaCobranca;

	private String idCriterio;

	private String descricaoCriterio;

	private String dataCriacao;

	private String dataCriacaoInicial;

	private String dataCriacaoFinal;

	private String dataInicio;

	private String dataInicioInicial;

	private String dataInicioFinal;

	private String dataEncerramento;

	private String dataEncerramentoInicial;

	private String dataEncerramentoFinal;

	private String dataUltimoMovimento;

	private String dataUltimoMovimentoInicial;

	private String dataUltimoMovimentoFinal;

	private String indicadorUso;

	private String dataUltimaAlteracao;

	private String atualizar;

	private String tipoPesquisa;

	private String tipoPesquisaDescricao;

	private static final long serialVersionUID = 1L;

	public String getCodigoProgramaCobranca(){

		return codigoProgramaCobranca;
	}

	public void setCodigoProgramaCobranca(String codigoProgramaCobranca){

		this.codigoProgramaCobranca = codigoProgramaCobranca;
	}

	public String getNomeProgramaCobranca(){

		return nomeProgramaCobranca;
	}

	public void setNomeProgramaCobranca(String nomeProgramaCobranca){

		this.nomeProgramaCobranca = nomeProgramaCobranca;
	}

	public String getDescricaoProgramaCobranca(){

		return descricaoProgramaCobranca;
	}

	public void setDescricaoProgramaCobranca(String descricaoProgramaCobranca){

		this.descricaoProgramaCobranca = descricaoProgramaCobranca;
	}

	public String getIdCriterio(){

		return idCriterio;
	}

	public void setIdCriterio(String idCriterio){

		this.idCriterio = idCriterio;
	}

	public String getDescricaoCriterio(){

		return descricaoCriterio;
	}

	public void setDescricaoCriterio(String descricaoCriterio){

		this.descricaoCriterio = descricaoCriterio;
	}

	public String getDataCriacao(){

		return dataCriacao;
	}

	public void setDataCriacao(String dataCriacao){

		this.dataCriacao = dataCriacao;
	}

	public String getDataCriacaoInicial(){

		return dataCriacaoInicial;
	}

	public void setDataCriacaoInicial(String dataCriacaoInicial){

		this.dataCriacaoInicial = dataCriacaoInicial;
	}

	public String getDataCriacaoFinal(){

		return dataCriacaoFinal;
	}

	public void setDataCriacaoFinal(String dataCriacaoFinal){

		this.dataCriacaoFinal = dataCriacaoFinal;
	}

	public String getDataInicio(){

		return dataInicio;
	}

	public void setDataInicio(String dataInicio){

		this.dataInicio = dataInicio;
	}

	public String getDataInicioInicial(){

		return dataInicioInicial;
	}

	public void setDataInicioInicial(String dataInicioInicial){

		this.dataInicioInicial = dataInicioInicial;
	}

	public String getDataInicioFinal(){

		return dataInicioFinal;
	}

	public void setDataInicioFinal(String dataInicioFinal){

		this.dataInicioFinal = dataInicioFinal;
	}

	public String getDataEncerramento(){

		return dataEncerramento;
	}

	public void setDataEncerramento(String dataEncerramento){

		this.dataEncerramento = dataEncerramento;
	}

	public String getDataEncerramentoInicial(){

		return dataEncerramentoInicial;
	}

	public void setDataEncerramentoInicial(String dataEncerramentoInicial){

		this.dataEncerramentoInicial = dataEncerramentoInicial;
	}

	public String getDataEncerramentoFinal(){

		return dataEncerramentoFinal;
	}

	public void setDataEncerramentoFinal(String dataEncerramentoFinal){

		this.dataEncerramentoFinal = dataEncerramentoFinal;
	}

	public String getDataUltimoMovimento(){

		return dataUltimoMovimento;
	}

	public void setDataUltimoMovimento(String dataUltimoMovimento){

		this.dataUltimoMovimento = dataUltimoMovimento;
	}

	public String getDataUltimoMovimentoInicial(){

		return dataUltimoMovimentoInicial;
	}

	public void setDataUltimoMovimentoInicial(String dataUltimoMovimentoInicial){

		this.dataUltimoMovimentoInicial = dataUltimoMovimentoInicial;
	}

	public String getDataUltimoMovimentoFinal(){

		return dataUltimoMovimentoFinal;
	}

	public void setDataUltimoMovimentoFinal(String dataUltimoMovimentoFinal){

		this.dataUltimoMovimentoFinal = dataUltimoMovimentoFinal;
	}

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public String getDataUltimaAlteracao(){

		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(String dataUltimaAlteracao){

		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public String getAtualizar(){

		return atualizar;
	}

	public void setAtualizar(String atualizar){

		this.atualizar = atualizar;
	}

	public String getTipoPesquisa(){

		return tipoPesquisa;
	}

	public void setTipoPesquisa(String tipoPesquisa){

		this.tipoPesquisa = tipoPesquisa;
	}

	public String getTipoPesquisaDescricao(){

		return tipoPesquisaDescricao;
	}

	public void setTipoPesquisaDescricao(String tipoPesquisaDescricao){

		this.tipoPesquisaDescricao = tipoPesquisaDescricao;
	}

}