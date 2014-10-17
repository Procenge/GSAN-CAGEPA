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
 */

package gcom.micromedicao.bean;

import java.io.Serializable;

/**
 * [UC ]
 * 
 * @author S�vio Luiz
 * @date 23/08/2007
 */
public class GerarDadosParaLeituraHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private String numeroPagina;

	private String codigoRota = "";

	private String codigoLocalidade = "";

	private String descricaoLocalidade = "";

	private String anoMesReferncia = "";

	private String grupo = "";

	private String codigoSetor = "";

	private String sequencialRota = "";

	private String nomeClienteUsuario = "";

	private String numeroHidrometro = "";

	private String localInstalacao = "";

	private String protecao = "";

	private String inscricao = "";

	private String matriculaImovel = "";

	private String matricula = "";

	private String enderecoImovel = "";

	private String dataPrevistaFaturamento = "";

	// CR 89962
	private String leituraAtual = "";

	private String leituraAnterior = "";

	private String consumoMedio = "";

	private String usuario = "";

	private String observacao = "";

	private String faixaLeitura = "";

	private String localizacao = "";

	private String categoria = "";

	private String elo = "";

	private String ocorrencia = "";

	private String confirmacao = "";

	private String consumoInformado = "";

	private String responsavel = "";

	private String codigoCategoria = "";

	private String dataAtual = "";

	private String dataLeituraAnterior = "";

	/*
	 * Indicadores utilizados na totaliza��o do Relat�rio de Consumidores para Leitura
	 * (RelatorioGerarDadosParaLeitura)
	 */

	private Integer indicadorLigacaoAtiva;

	private Integer indicadorLigacaoCortada;

	private Integer indicadorLigacaoComEsgoto;

	private Integer indicadorLigacaoSemEsgoto;

	private Integer indicadorLigacaoComHidrometro;

	private Integer indicadorLigacaoSemHidrometro;

	private Integer indicadorLigacaoComServicosCobrados;

	private Integer indicadorLigacaoComDevolucoes;

	private Integer indicadorLigacaoComEmissaoConta;

	private Integer indicadorLigacaoSemEmissaoConta;

	public GerarDadosParaLeituraHelper() {

	}

	public GerarDadosParaLeituraHelper(String codigoRota, String descricaoLocalidade, String anoMesReferncia, String grupo,
										String codigoSetor, String sequencialRota, String nomeClienteUsuario, String numeroHidrometro,
										String localInstalacao, String protecao, String inscricao, String matriculaImovel,
										String enderecoImovel, String dataPrevistaFaturamento) {

		this.codigoRota = codigoRota;
		this.descricaoLocalidade = descricaoLocalidade;
		this.anoMesReferncia = anoMesReferncia;
		this.grupo = grupo;
		this.codigoSetor = codigoSetor;
		this.sequencialRota = sequencialRota;
		this.nomeClienteUsuario = nomeClienteUsuario;
		this.numeroHidrometro = numeroHidrometro;
		this.localInstalacao = localInstalacao;
		this.protecao = protecao;
		this.inscricao = inscricao;
		this.matriculaImovel = matriculaImovel;
		this.enderecoImovel = enderecoImovel;
		this.dataPrevistaFaturamento = dataPrevistaFaturamento;
	}

	public String getAnoMesReferncia(){

		return anoMesReferncia;
	}

	public void setAnoMesReferncia(String anoMesReferncia){

		this.anoMesReferncia = anoMesReferncia;
	}

	public String getCodigoRota(){

		return codigoRota;
	}

	public void setCodigoRota(String codigoRota){

		this.codigoRota = codigoRota;
	}

	public String getCodigoSetor(){

		return codigoSetor;
	}

	public void setCodigoSetor(String codigoSetor){

		this.codigoSetor = codigoSetor;
	}

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	public String getGrupo(){

		return grupo;
	}

	public void setGrupo(String grupo){

		this.grupo = grupo;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public String getLocalInstalacao(){

		return localInstalacao;
	}

	public void setLocalInstalacao(String localInstalacao){

		this.localInstalacao = localInstalacao;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getNomeClienteUsuario(){

		return nomeClienteUsuario;
	}

	public void setNomeClienteUsuario(String nomeClienteUsuario){

		this.nomeClienteUsuario = nomeClienteUsuario;
	}

	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	public String getProtecao(){

		return protecao;
	}

	public void setProtecao(String protecao){

		this.protecao = protecao;
	}

	public String getSequencialRota(){

		return sequencialRota;
	}

	public void setSequencialRota(String sequencialRota){

		this.sequencialRota = sequencialRota;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getDataPrevistaFaturamento(){

		return dataPrevistaFaturamento;
	}

	public void setDataPrevistaFaturamento(String dataPrevistaFaturamento){

		this.dataPrevistaFaturamento = dataPrevistaFaturamento;
	}

	public String getLeituraAnterior(){

		return leituraAnterior;
	}

	public void setLeituraAnterior(String leituraAnterior){

		this.leituraAnterior = leituraAnterior;
	}

	public String getConsumoMedio(){

		return consumoMedio;
	}

	public void setConsumoMedio(String consumoMedio){

		this.consumoMedio = consumoMedio;
	}

	public String getUsuario(){

		return usuario;
	}

	public void setUsuario(String usuario){

		this.usuario = usuario;
	}

	public String getObservacao(){

		return observacao;
	}

	public void setObservacao(String observacao){

		this.observacao = observacao;
	}

	public String getFaixaLeitura(){

		return faixaLeitura;
	}

	public void setFaixaLeitura(String faixaLeitura){

		this.faixaLeitura = faixaLeitura;
	}

	public String getLocalizacao(){

		return localizacao;
	}

	public void setLocalizacao(String localizacao){

		this.localizacao = localizacao;
	}

	public String getCategoria(){

		return categoria;
	}

	public void setCategoria(String categoria){

		this.categoria = categoria;
	}

	public String getElo(){

		return elo;
	}

	public void setElo(String elo){

		this.elo = elo;
	}

	public String getLeituraAtual(){

		return leituraAtual;
	}

	public void setLeituraAtual(String leituraAtual){

		this.leituraAtual = leituraAtual;
	}

	public String getOcorrencia(){

		return ocorrencia;
	}

	public void setOcorrencia(String ocorrencia){

		this.ocorrencia = ocorrencia;
	}

	public String getConfirmacao(){

		return confirmacao;
	}

	public void setConfirmacao(String confirmacao){

		this.confirmacao = confirmacao;
	}

	public String getConsumoInformado(){

		return consumoInformado;
	}

	public void setConsumoInformado(String consumoInformado){

		this.consumoInformado = consumoInformado;
	}

	public String getResponsavel(){

		return responsavel;
	}

	public void setResponsavel(String responsavel){

		this.responsavel = responsavel;
	}

	public String getCodigoLocalidade(){

		return codigoLocalidade;
	}

	public void setCodigoLocalidade(String codigoLocalidade){

		this.codigoLocalidade = codigoLocalidade;
	}

	public String getCodigoCategoria(){

		return codigoCategoria;
	}

	public void setCodigoCategoria(String codigoCategoria){

		this.codigoCategoria = codigoCategoria;
	}

	public String getDataAtual(){

		return dataAtual;
	}

	public void setDataAtual(String dataAtual){

		this.dataAtual = dataAtual;
	}

	public String getDataLeituraAnterior(){

		return dataLeituraAnterior;
	}

	public void setDataLeituraAnterior(String dataLeituraAnterior){

		this.dataLeituraAnterior = dataLeituraAnterior;
	}

	public void setNumeroPagina(String numeroPagina){

		this.numeroPagina = numeroPagina;
	}

	public String getNumeroPagina(){

		return numeroPagina;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public String getMatricula(){

		return matricula;
	}

	public Integer getIndicadorLigacaoAtiva(){

		return indicadorLigacaoAtiva;
	}

	public void setIndicadorLigacaoAtiva(Integer indicadorLigacaoAtiva){

		this.indicadorLigacaoAtiva = indicadorLigacaoAtiva;
	}

	public Integer getIndicadorLigacaoCortada(){

		return indicadorLigacaoCortada;
	}

	public void setIndicadorLigacaoCortada(Integer indicadorLigacaoCortada){

		this.indicadorLigacaoCortada = indicadorLigacaoCortada;
	}

	public Integer getIndicadorLigacaoComEsgoto(){

		return indicadorLigacaoComEsgoto;
	}

	public void setIndicadorLigacaoComEsgoto(Integer indicadorLigacaoComEsgoto){

		this.indicadorLigacaoComEsgoto = indicadorLigacaoComEsgoto;
	}

	public Integer getIndicadorLigacaoSemEsgoto(){

		return indicadorLigacaoSemEsgoto;
	}

	public void setIndicadorLigacaoSemEsgoto(Integer indicadorLigacaoSemEsgoto){

		this.indicadorLigacaoSemEsgoto = indicadorLigacaoSemEsgoto;
	}

	public Integer getIndicadorLigacaoComHidrometro(){

		return indicadorLigacaoComHidrometro;
	}

	public void setIndicadorLigacaoComHidrometro(Integer indicadorLigacaoComHidrometro){

		this.indicadorLigacaoComHidrometro = indicadorLigacaoComHidrometro;
	}

	public Integer getIndicadorLigacaoSemHidrometro(){

		return indicadorLigacaoSemHidrometro;
	}

	public void setIndicadorLigacaoSemHidrometro(Integer indicadorLigacaoSemHidrometro){

		this.indicadorLigacaoSemHidrometro = indicadorLigacaoSemHidrometro;
	}

	public Integer getIndicadorLigacaoComServicosCobrados(){

		return indicadorLigacaoComServicosCobrados;
	}

	public void setIndicadorLigacaoComServicosCobrados(Integer indicadorLigacaoComServicosCobrados){

		this.indicadorLigacaoComServicosCobrados = indicadorLigacaoComServicosCobrados;
	}

	public Integer getIndicadorLigacaoComDevolucoes(){

		return indicadorLigacaoComDevolucoes;
	}

	public void setIndicadorLigacaoComDevolucoes(Integer indicadorLigacaoComDevolucoes){

		this.indicadorLigacaoComDevolucoes = indicadorLigacaoComDevolucoes;
	}

	public Integer getIndicadorLigacaoComEmissaoConta(){

		return indicadorLigacaoComEmissaoConta;
	}

	public void setIndicadorLigacaoComEmissaoConta(Integer indicadorLigacaoComEmissaoConta){

		this.indicadorLigacaoComEmissaoConta = indicadorLigacaoComEmissaoConta;
	}

	public Integer getIndicadorLigacaoSemEmissaoConta(){

		return indicadorLigacaoSemEmissaoConta;
	}

	public void setIndicadorLigacaoSemEmissaoConta(Integer indicadorLigacaoSemEmissaoConta){

		this.indicadorLigacaoSemEmissaoConta = indicadorLigacaoSemEmissaoConta;
	}

}
