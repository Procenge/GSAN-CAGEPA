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

package gcom.relatorio.micromedicao;

import java.io.Serializable;

/**
 * Relatório Resumo das Ocorrências
 * 
 * @author Virgínia Melo
 * @date 13/10/2008
 */
public class RelatorioResumoOcorrenciasFaturamentoImediatoHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer codLocalidade;

	private String nomeLocalidade;

	private Integer codImovel;

	private String nomeCliente;

	private String inscricao;

	private String situacaoAgua;

	private String situacaoEsgoto;

	private Integer numeroConsumoFaturadoAgua;

	private Integer numeroConsumoFaturadoEsgoto;

	private String erroEncontradoNoProcessamento;

	private String valorAguaGsan;

	private String valorAguaColetor;

	private String valorEsgotoGsan;

	private String valorEsgotoColetor;

	private String categorias;

	private boolean isDivergenciaValoresSistemaColetor;

	/**
	 * Default Constructor
	 */
	public RelatorioResumoOcorrenciasFaturamentoImediatoHelper() {

	}

	/**
	 * Construtor da classe
	 * 
	 * @param codLocalidade
	 *            - Código Localidade
	 * @param nomeLocalidade
	 *            - Nome Localidade
	 * @param codImovel
	 *            - Código Imóvel
	 * @param nomeCliente
	 *            - Nome do Cliente
	 * @param inscricao
	 *            - Número da Inscrição formatada
	 * @param situacaoAgua
	 *            - Situação de Água
	 * @param situacaoEsgoto
	 *            - Situação de Esgoto
	 * @param numeroConsumoFaturadoAgua
	 *            - Consumo Água
	 * @param numeroConsumoFaturadoEsgoto
	 *            - Consumo Esgoto
	 * @param erroEncontradoNoProcessamento
	 *            - Erros encontrados no processamento
	 */
	public RelatorioResumoOcorrenciasFaturamentoImediatoHelper(Integer codLocalidade, String nomeLocalidade, Integer codImovel,
																String nomeCliente, String inscricao, String situacaoAgua,
																String situacaoEsgoto, Integer numeroConsumoFaturadoAgua,
																Integer numeroConsumoFaturadoEsgoto, String erroEncontradoNoProcessamento,
																String valorAguaGsan, String valorAguaColetor, String valorEsgotoGsan,
																String valorEsgotoColetor, String categorias) {

		this.codLocalidade = codLocalidade;
		this.nomeLocalidade = nomeLocalidade;
		this.codImovel = codImovel;
		this.nomeCliente = nomeCliente;
		this.inscricao = inscricao;
		this.situacaoAgua = situacaoAgua;
		this.situacaoEsgoto = situacaoEsgoto;
		this.numeroConsumoFaturadoAgua = numeroConsumoFaturadoAgua;
		this.numeroConsumoFaturadoEsgoto = numeroConsumoFaturadoEsgoto;
		this.erroEncontradoNoProcessamento = erroEncontradoNoProcessamento;
		this.valorAguaGsan = valorAguaGsan;
		this.valorAguaColetor = valorAguaColetor;
		this.valorEsgotoGsan = valorEsgotoGsan;
		this.valorEsgotoColetor = valorEsgotoColetor;
		this.categorias = categorias;

	}

	public Integer getCodLocalidade(){

		return codLocalidade;
	}

	public void setCodLocalidade(Integer codLocalidade){

		this.codLocalidade = codLocalidade;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public Integer getCodImovel(){

		return codImovel;
	}

	public void setCodImovel(Integer codImovel){

		this.codImovel = codImovel;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public String getSituacaoAgua(){

		return situacaoAgua;
	}

	public void setSituacaoAgua(String situacaoAgua){

		this.situacaoAgua = situacaoAgua;
	}

	public String getSituacaoEsgoto(){

		return situacaoEsgoto;
	}

	public void setSituacaoEsgoto(String situacaoEsgoto){

		this.situacaoEsgoto = situacaoEsgoto;
	}

	public Integer getNumeroConsumoFaturadoAgua(){

		return numeroConsumoFaturadoAgua;
	}

	public void setNumeroConsumoFaturadoAgua(Integer numeroConsumoFaturadoAgua){

		this.numeroConsumoFaturadoAgua = numeroConsumoFaturadoAgua;
	}

	public Integer getNumeroConsumoFaturadoEsgoto(){

		return numeroConsumoFaturadoEsgoto;
	}

	public void setNumeroConsumoFaturadoEsgoto(Integer numeroConsumoFaturadoEsgoto){

		this.numeroConsumoFaturadoEsgoto = numeroConsumoFaturadoEsgoto;
	}

	public String getErroEncontradoNoProcessamento(){

		return erroEncontradoNoProcessamento;
	}

	public void setErroEncontradoNoProcessamento(String erroEncontradoNoProcessamento){

		this.erroEncontradoNoProcessamento = erroEncontradoNoProcessamento;
	}

	public String getValorAguaGsan(){

		return valorAguaGsan;
	}

	public void setValorAguaGsan(String valorAguaGsan){

		this.valorAguaGsan = valorAguaGsan;
	}

	public String getValorAguaColetor(){

		return valorAguaColetor;
	}

	public void setValorAguaColetor(String valorAguaColetor){

		this.valorAguaColetor = valorAguaColetor;
	}

	public String getValorEsgotoGsan(){

		return valorEsgotoGsan;
	}

	public void setValorEsgotoGsan(String valorEsgotoGsan){

		this.valorEsgotoGsan = valorEsgotoGsan;
	}

	public String getValorEsgotoColetor(){

		return valorEsgotoColetor;
	}

	public void setValorEsgotoColetor(String valorEsgotoColetor){

		this.valorEsgotoColetor = valorEsgotoColetor;
	}

	public String getCategorias(){

		return categorias;
	}

	public void setCategorias(String categorias){

		this.categorias = categorias;
	}

	public boolean getIsDivergenciaValoresSistemaColetor(){

		return isDivergenciaValoresSistemaColetor;
	}

	public void setIsDivergenciaValoresSistemaColetor(boolean isDivergenciaValoresSistemaColetor){

		this.isDivergenciaValoresSistemaColetor = isDivergenciaValoresSistemaColetor;
	}

}