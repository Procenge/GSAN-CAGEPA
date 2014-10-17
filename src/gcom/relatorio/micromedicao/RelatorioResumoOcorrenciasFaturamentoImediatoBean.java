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
 * 
 * GSANPCG
 * Virg�nia Melo
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
 */

package gcom.relatorio.micromedicao;

import gcom.relatorio.RelatorioBean;

/**
 * Relat�rio Resumo Ocorr�ncias
 * 
 * @author Virg�nia Melo
 * @date 13/10/2008
 */
public class RelatorioResumoOcorrenciasFaturamentoImediatoBean
				implements RelatorioBean {

	private String codLocalidade;

	private String nomeLocalidade;

	private String codImovel;

	private String nomeCliente;

	private String inscricao;

	private String situacaoAgua;

	private String situacaoEsgoto;

	private String numeroConsumoFaturadoAgua;

	private String numeroConsumoFaturadoEsgoto;

	private String erroEncontradoNoProcessamento;

	private String valorAguaGsan;

	private String valorAguaColetor;

	private String valorEsgotoGsan;

	private String valorEsgotoColetor;

	private String categorias;

	public RelatorioResumoOcorrenciasFaturamentoImediatoBean(String codLocalidade, String nomeLocalidade, String codImovel,
																String nomeCliente, String inscricao, String situacaoAgua,
																String situacaoEsgoto, String numeroConsumoFaturadoAgua,
																String numeroConsumoFaturadoEsgoto, String erroEncontradoNoProcessamento) {

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
	}

	public String getCodLocalidade(){

		return codLocalidade;
	}

	public void setCodLocalidade(String codLocalidade){

		this.codLocalidade = codLocalidade;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public String getCodImovel(){

		return codImovel;
	}

	public void setCodImovel(String codImovel){

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

	public String getNumeroConsumoFaturadoAgua(){

		return numeroConsumoFaturadoAgua;
	}

	public void setNumeroConsumoFaturadoAgua(String numeroConsumoFaturadoAgua){

		this.numeroConsumoFaturadoAgua = numeroConsumoFaturadoAgua;
	}

	public String getNumeroConsumoFaturadoEsgoto(){

		return numeroConsumoFaturadoEsgoto;
	}

	public void setNumeroConsumoFaturadoEsgoto(String numeroConsumoFaturadoEsgoto){

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

}
