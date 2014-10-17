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

package gcom.cobranca.campanhapremiacao;

import gcom.interceptor.ObjetoTransacao;
import gcom.util.IoUtil;
import gcom.util.filtro.Filtro;

import java.sql.Blob;
import java.util.Date;

public class Campanha
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String dsTituloCampanha;

	private Date tmInscricaoInicio;

	private Date tmInscricaoFim;

	private Date dataLiberacaoSorteio;

	private Integer quantidadePremios;

	private Short indicadorUnidadePremiacao;

	private Integer tipoUnidadePremiacao;

	private Short indicadorInscricaoBloqueio;

	private Short indicadorAdimplencia;

	private Short indicadorParcelamentoEntradaPend;

	private Integer amReferenciaInicio;
	
	private Integer amReferenciaFim;
	private Date dataVencimentoInicio;
	private Date dataVencimentoFim;
	private Short indicadorPagamento;
	
	private Short indicadorContaRevisao;
	private Short indicadorDebitoACobrar;
	private byte[] regulamentoCampanha;

	private String nomeClasseGeraInscricao;

	private String nomeRelatorioComprovante;

	private String nomeClasseEmiteComprovante;

	private String dsEmailEnvioComprovante;

	private Date ultimaAlteracao;

	public Campanha() {

	}

	public Campanha(Integer id) {

		this.id = id;
	}

	@Override
	public Filtro retornaFiltro(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	
	public Integer getId(){
	
		return id;
	}

	
	public void setId(Integer id){
	
		this.id = id;
	}

	
	public String getDsTituloCampanha(){
	
		return dsTituloCampanha;
	}

	
	public void setDsTituloCampanha(String dsTituloCampanha){
	
		this.dsTituloCampanha = dsTituloCampanha;
	}

	
	public Date getTmInscricaoInicio(){
	
		return tmInscricaoInicio;
	}

	
	public void setTmInscricaoInicio(Date tmInscricaoInicio){
	
		this.tmInscricaoInicio = tmInscricaoInicio;
	}

	
	public Date getTmInscricaoFim(){
	
		return tmInscricaoFim;
	}

	
	public void setTmInscricaoFim(Date tmInscricaoFim){
	
		this.tmInscricaoFim = tmInscricaoFim;
	}

	
	public Date getDataLiberacaoSorteio(){
	
		return dataLiberacaoSorteio;
	}

	
	public void setDataLiberacaoSorteio(Date dataLiberacaoSorteio){
	
		this.dataLiberacaoSorteio = dataLiberacaoSorteio;
	}

	
	public Integer getQuantidadePremios(){
	
		return quantidadePremios;
	}

	
	public void setQuantidadePremios(Integer quantidadePremios){
	
		this.quantidadePremios = quantidadePremios;
	}

	
	public Short getIndicadorUnidadePremiacao(){
	
		return indicadorUnidadePremiacao;
	}

	
	public void setIndicadorUnidadePremiacao(Short indicadorUnidadePremiacao){
	
		this.indicadorUnidadePremiacao = indicadorUnidadePremiacao;
	}

	
	public Integer getTipoUnidadePremiacao(){
	
		return tipoUnidadePremiacao;
	}

	
	public void setTipoUnidadePremiacao(Integer tipoUnidadePremiacao){
	
		this.tipoUnidadePremiacao = tipoUnidadePremiacao;
	}

	
	public Short getIndicadorInscricaoBloqueio(){
	
		return indicadorInscricaoBloqueio;
	}

	
	public void setIndicadorInscricaoBloqueio(Short indicadorInscricaoBloqueio){
	
		this.indicadorInscricaoBloqueio = indicadorInscricaoBloqueio;
	}

	
	public Short getIndicadorAdimplencia(){
	
		return indicadorAdimplencia;
	}

	
	public void setIndicadorAdimplencia(Short indicadorAdimplencia){
	
		this.indicadorAdimplencia = indicadorAdimplencia;
	}

	
	public Short getIndicadorParcelamentoEntradaPend(){
	
		return indicadorParcelamentoEntradaPend;
	}

	
	public void setIndicadorParcelamentoEntradaPend(Short indicadorParcelamentoEntradaPend){
	
		this.indicadorParcelamentoEntradaPend = indicadorParcelamentoEntradaPend;
	}

	
	public Integer getAmReferenciaInicio(){
	
		return amReferenciaInicio;
	}

	
	public void setAmReferenciaInicio(Integer amReferenciaInicio){
	
		this.amReferenciaInicio = amReferenciaInicio;
	}

	
	public Integer getAmReferenciaFim(){
	
		return amReferenciaFim;
	}

	
	public void setAmReferenciaFim(Integer amReferenciaFim){
	
		this.amReferenciaFim = amReferenciaFim;
	}

	public Date getDataVencimentoInicio(){
	
		return dataVencimentoInicio;
	}

	
	public void setDataVencimentoInicio(Date dataVencimentoInicio){
	
		this.dataVencimentoInicio = dataVencimentoInicio;
	}

	
	public Date getDataVencimentoFim(){
	
		return dataVencimentoFim;
	}

	
	public void setDataVencimentoFim(Date dataVencimentoFim){
	
		this.dataVencimentoFim = dataVencimentoFim;
	}

	
	public Short getIndicadorPagamento(){
	
		return indicadorPagamento;
	}

	
	public void setIndicadorPagamento(Short indicadorPagamento){
	
		this.indicadorPagamento = indicadorPagamento;
	}

	
	public Short getIndicadorContaRevisao(){
	
		return indicadorContaRevisao;
	}

	
	public void setIndicadorContaRevisao(Short indicadorContaRevisao){
	
		this.indicadorContaRevisao = indicadorContaRevisao;
	}

	
	public Short getIndicadorDebitoACobrar(){
	
		return indicadorDebitoACobrar;
	}

	
	public void setIndicadorDebitoACobrar(Short indicadorDebitoACobrar){
	
		this.indicadorDebitoACobrar = indicadorDebitoACobrar;
	}

	
	public byte[] getRegulamentoCampanha(){
	
		return regulamentoCampanha;
	}

	public void setRegulamentoCampanha(byte[] regulamentoCampanha){

		this.regulamentoCampanha = regulamentoCampanha;
	}

	public void setRegulamentoCampanha(Blob regulamentoCampanha){
	
		this.regulamentoCampanha = IoUtil.toByteArray(regulamentoCampanha);
	}

	public String getNomeClasseGeraInscricao(){

		return nomeClasseGeraInscricao;
	}

	public void setNomeClasseGeraInscricao(String nomeClasseGeraInscricao){

		this.nomeClasseGeraInscricao = nomeClasseGeraInscricao;
	}

	public String getNomeRelatorioComprovante(){

		return nomeRelatorioComprovante;
	}

	public void setNomeRelatorioComprovante(String nomeRelatorioComprovante){

		this.nomeRelatorioComprovante = nomeRelatorioComprovante;
	}

	public String getNomeClasseEmiteComprovante(){

		return nomeClasseEmiteComprovante;
	}

	public void setNomeClasseEmiteComprovante(String nomeClasseEmiteComprovante){

		this.nomeClasseEmiteComprovante = nomeClasseEmiteComprovante;
	}

	public String getDsEmailEnvioComprovante(){

		return dsEmailEnvioComprovante;
	}

	public void setDsEmailEnvioComprovante(String dsEmailEnvioComprovante){

		this.dsEmailEnvioComprovante = dsEmailEnvioComprovante;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}


}
