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

package gcom.arrecadacao.bean;

import gcom.arrecadacao.ArrecadacaoForma;
import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.Concessionaria;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.localidade.*;
import gcom.cobranca.DocumentoTipo;
import gcom.micromedicao.Rota;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ArrecadacaoDadosDiariosHelper {

	/**
	 * @since 16/05/2007
	 */

	private Integer id;

	private Integer anoMesReferenciaArrecadacao;

	private Integer codigoSetorComercial;

	private Integer numeroQuadra;

	private Short indicadorHidrometro;

	private Date dataPagamento;

	private Integer quantidadePagamentos;

	private BigDecimal valorPagamentos;

	private Rota rota;

	private Arrecadador arrecadador;

	private SetorComercial setorComercial;

	private ArrecadacaoForma arrecadacaoForma;

	private LigacaoEsgotoSituacao ligacaoEsgotoSituacao;

	private DocumentoTipo documentoTipo;

	private EsferaPoder esferaPoder;

	private ImovelPerfil imovelPerfil;

	private Quadra quadra;

	private GerenciaRegional gerenciaRegional;

	private Localidade localidade;

	private LigacaoAguaSituacao ligacaoAguaSituacao;

	private Categoria categoria;

	private UnidadeNegocio unidadeNegocio;

	private BigDecimal valorDespesaBancaria;

	private Concessionaria concessionaria;

	public ArrecadacaoDadosDiariosHelper(Integer id, Integer anoMesReferenciaArrecadacao, Integer codigoSetorComercial, Integer numeroQuadra,
									Short indicadorHidrometro, Date dataPagamento, Integer quantidadePagamentos,
									BigDecimal valorPagamentos, Rota rota, Arrecadador arrecadador, SetorComercial setorComercial,
									ArrecadacaoForma arrecadacaoForma, LigacaoEsgotoSituacao ligacaoEsgotoSituacao,
									DocumentoTipo documentoTipo, EsferaPoder esferaPoder, ImovelPerfil imovelPerfil, Quadra quadra,
									GerenciaRegional gerenciaRegional, Localidade localidade, LigacaoAguaSituacao ligacaoAguaSituacao,
 Categoria categoria, UnidadeNegocio unidadeNegocio,
											Concessionaria concessionaria) {

		this.id = id;
		this.anoMesReferenciaArrecadacao = anoMesReferenciaArrecadacao;
		this.codigoSetorComercial = codigoSetorComercial;
		this.numeroQuadra = numeroQuadra;
		this.indicadorHidrometro = indicadorHidrometro;
		this.dataPagamento = dataPagamento;
		this.quantidadePagamentos = quantidadePagamentos;
		this.valorPagamentos = valorPagamentos;
		this.rota = rota;
		this.arrecadador = arrecadador;
		this.setorComercial = setorComercial;
		this.arrecadacaoForma = arrecadacaoForma;
		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
		this.documentoTipo = documentoTipo;
		this.esferaPoder = esferaPoder;
		this.imovelPerfil = imovelPerfil;
		this.quadra = quadra;
		this.gerenciaRegional = gerenciaRegional;
		this.localidade = localidade;
		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
		this.categoria = categoria;
		this.unidadeNegocio = unidadeNegocio;
		this.concessionaria = concessionaria;
	}

	/** default constructor */
	public ArrecadacaoDadosDiariosHelper() {

	}

	/** minimal constructor */
	public ArrecadacaoDadosDiariosHelper(Integer id, Integer codigoSetorComercial, Integer numeroQuadra, Rota rota, Arrecadador arrecadador,
									SetorComercial setorComercial, ArrecadacaoForma arrecadacaoForma,
									LigacaoEsgotoSituacao ligacaoEsgotoSituacao, DocumentoTipo documentoTipo, EsferaPoder esferaPoder,
									ImovelPerfil imovelPerfil, Quadra quadra, GerenciaRegional gerenciaRegional, Localidade localidade,
									LigacaoAguaSituacao ligacaoAguaSituacao, Categoria categoria, UnidadeNegocio unidadeNegocio) {

		this.id = id;
		this.codigoSetorComercial = codigoSetorComercial;
		this.numeroQuadra = numeroQuadra;
		this.rota = rota;
		this.arrecadador = arrecadador;
		this.setorComercial = setorComercial;
		this.arrecadacaoForma = arrecadacaoForma;
		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
		this.documentoTipo = documentoTipo;
		this.esferaPoder = esferaPoder;
		this.imovelPerfil = imovelPerfil;
		this.quadra = quadra;
		this.gerenciaRegional = gerenciaRegional;
		this.localidade = localidade;
		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
		this.categoria = categoria;
		this.unidadeNegocio = unidadeNegocio;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getAnoMesReferenciaArrecadacao(){

		return this.anoMesReferenciaArrecadacao;
	}

	public void setAnoMesReferenciaArrecadacao(Integer anoMesReferenciaArrecadacao){

		this.anoMesReferenciaArrecadacao = anoMesReferenciaArrecadacao;
	}

	public Integer getCodigoSetorComercial(){

		return this.codigoSetorComercial;
	}

	public void setCodigoSetorComercial(Integer codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public Integer getNumeroQuadra(){

		return this.numeroQuadra;
	}

	public void setNumeroQuadra(Integer numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	public Short getIndicadorHidrometro(){

		return this.indicadorHidrometro;
	}

	public void setIndicadorHidrometro(Short indicadorHidrometro){

		this.indicadorHidrometro = indicadorHidrometro;
	}

	public Date getDataPagamento(){

		return this.dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento){

		this.dataPagamento = dataPagamento;
	}

	public Integer getQuantidadePagamentos(){

		return this.quantidadePagamentos;
	}

	public void setQuantidadePagamentos(Integer quantidadePagamentos){

		this.quantidadePagamentos = quantidadePagamentos;
	}

	public BigDecimal getValorPagamentos(){

		return this.valorPagamentos;
	}

	public void setValorPagamentos(BigDecimal valorPagamentos){

		this.valorPagamentos = valorPagamentos;
	}

	public Rota getRota(){

		return this.rota;
	}

	public void setRota(Rota rota){

		this.rota = rota;
	}

	public Arrecadador getArrecadador(){

		return this.arrecadador;
	}

	public void setArrecadador(Arrecadador arrecadador){

		this.arrecadador = arrecadador;
	}

	public SetorComercial getSetorComercial(){

		return this.setorComercial;
	}

	public void setSetorComercial(SetorComercial setorComercial){

		this.setorComercial = setorComercial;
	}

	public ArrecadacaoForma getArrecadacaoForma(){

		return this.arrecadacaoForma;
	}

	public void setArrecadacaoForma(ArrecadacaoForma arrecadacaoForma){

		this.arrecadacaoForma = arrecadacaoForma;
	}

	public LigacaoEsgotoSituacao getLigacaoEsgotoSituacao(){

		return this.ligacaoEsgotoSituacao;
	}

	public void setLigacaoEsgotoSituacao(LigacaoEsgotoSituacao ligacaoEsgotoSituacao){

		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
	}

	public DocumentoTipo getDocumentoTipo(){

		return this.documentoTipo;
	}

	public void setDocumentoTipo(DocumentoTipo documentoTipo){

		this.documentoTipo = documentoTipo;
	}

	public EsferaPoder getEsferaPoder(){

		return this.esferaPoder;
	}

	public void setEsferaPoder(EsferaPoder esferaPoder){

		this.esferaPoder = esferaPoder;
	}

	public ImovelPerfil getImovelPerfil(){

		return this.imovelPerfil;
	}

	public void setImovelPerfil(ImovelPerfil imovelPerfil){

		this.imovelPerfil = imovelPerfil;
	}

	public Quadra getQuadra(){

		return this.quadra;
	}

	public void setQuadra(Quadra quadra){

		this.quadra = quadra;
	}

	public GerenciaRegional getGerenciaRegional(){

		return this.gerenciaRegional;
	}

	public void setGerenciaRegional(GerenciaRegional gerenciaRegional){

		this.gerenciaRegional = gerenciaRegional;
	}

	public Localidade getLocalidade(){

		return this.localidade;
	}

	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	public LigacaoAguaSituacao getLigacaoAguaSituacao(){

		return this.ligacaoAguaSituacao;
	}

	public void setLigacaoAguaSituacao(LigacaoAguaSituacao ligacaoAguaSituacao){

		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
	}

	public Categoria getCategoria(){

		return this.categoria;
	}

	public void setCategoria(Categoria categoria){

		this.categoria = categoria;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	/**
	 * @return Retorna o campo unidadeNegocio.
	 */
	public UnidadeNegocio getUnidadeNegocio(){

		return unidadeNegocio;
	}

	/**
	 * @param unidadeNegocio
	 *            O unidadeNegocio a ser setado.
	 */
	public void setUnidadeNegocio(UnidadeNegocio unidadeNegocio){

		this.unidadeNegocio = unidadeNegocio;
	}

	/**
	 * @return the valorDespesaBancaria
	 */
	public BigDecimal getValorDespesaBancaria(){

		return valorDespesaBancaria;
	}

	/**
	 * @param valorDespesaBancaria the valorDespesaBancaria to set
	 */
	public void setValorDespesaBancaria(BigDecimal valorDespesaBancaria){

		this.valorDespesaBancaria = valorDespesaBancaria;
	}

	public Concessionaria getConcessionaria(){

		return concessionaria;
	}

	public void setConcessionaria(Concessionaria concessionaria){

		this.concessionaria = concessionaria;
	}

}
