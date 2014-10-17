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
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
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

import gcom.micromedicao.bean.GerarDadosParaLeituraHelper;
import gcom.relatorio.RelatorioBean;

/**
 * [UC ]
 * 
 * @author Sávio Luiz
 * @date 27/08/2007
 */
public class RelatorioGerarDadosParaLeituraBean
				implements RelatorioBean {

	private String numeroPagina;

	private String codigoRota1;

	private String codigoLocalidade1;

	private String descricaoLocalidade1;

	private String anoMesReferencia1;

	private String grupo1;

	private String codigoSetor1;

	private String sequencialRota1;

	private String nomeClienteUsuario1;

	private String numeroHidrometro1;

	private String localInstalacao1;

	private String protecao1;

	private String inscricao1;

	private String matricula1;

	private String enderecoImovel1;

	private String codigoRota;

	private String codigoLocalidade;

	private String descricaoLocalidade;

	private String anoMesReferencia;

	private String grupo;

	private String codigoSetor;

	private String sequencialRota;

	private String nomeClienteUsuario;

	private String numeroHidrometro;

	private String localInstalacao;

	private String protecao;

	private String inscricao;

	private String matricula;

	private String enderecoImovel;

	// CR 89962

	// ---
	private String ocorrencia1 = "";

	private String leituraAtual1 = "";

	private String confirmacao1 = "";

	private String consumoInformado1 = "";

	private String responsavel1 = "";

	private String leituraAnterior1 = "";

	private String consumoMedio1 = "";

	private String observacao1 = "";

	private String localizacao1 = "";

	private String codigoCategoria1 = "";

	private String faixaLeitura1 = "";

	private String elo1 = "";

	private String dataAtual1 = "";

	private String dataLeituraAnterior1 = "";

	private String ocorrencia = "";

	private String leituraAtual = "";

	private String confirmacao = "";

	private String consumoInformado = "";

	private String responsavel = "";

	private String leituraAnterior = "";

	private String consumoMedio = "";

	private String observacao = "";

	private String localizacao = "";

	private String codigoCategoria = "";

	private String faixaLeitura = "";

	private String elo = "";

	private String dataAtual = "";

	private String dataLeituraAnterior = "";

	private String matriculaImovel;

	/*
	 * Indicadores utilizados na totalização do Relatório
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

	public RelatorioGerarDadosParaLeituraBean(GerarDadosParaLeituraHelper gerarDadosParaLeituraHelper1,
												GerarDadosParaLeituraHelper gerarDadosParaLeituraHelper2) {

		if(gerarDadosParaLeituraHelper1 != null){
			this.numeroPagina = gerarDadosParaLeituraHelper1.getNumeroPagina();

			this.codigoRota = gerarDadosParaLeituraHelper1.getCodigoRota();
			this.codigoLocalidade = gerarDadosParaLeituraHelper1.getCodigoLocalidade();
			this.descricaoLocalidade = gerarDadosParaLeituraHelper1.getDescricaoLocalidade();
			this.anoMesReferencia = gerarDadosParaLeituraHelper1.getAnoMesReferncia();
			this.grupo = gerarDadosParaLeituraHelper1.getGrupo();
			this.codigoSetor = gerarDadosParaLeituraHelper1.getCodigoSetor();
			this.sequencialRota = gerarDadosParaLeituraHelper1.getSequencialRota();
			this.nomeClienteUsuario = gerarDadosParaLeituraHelper1.getNomeClienteUsuario();
			this.numeroHidrometro = gerarDadosParaLeituraHelper1.getNumeroHidrometro();
			this.localInstalacao = gerarDadosParaLeituraHelper1.getLocalInstalacao();
			this.protecao = gerarDadosParaLeituraHelper1.getProtecao();
			this.inscricao = gerarDadosParaLeituraHelper1.getInscricao();
			this.matricula = gerarDadosParaLeituraHelper1.getMatriculaImovel();
			this.enderecoImovel = gerarDadosParaLeituraHelper1.getEnderecoImovel();
			this.matriculaImovel = gerarDadosParaLeituraHelper1.getMatricula();

			this.ocorrencia = gerarDadosParaLeituraHelper1.getOcorrencia();
			this.leituraAtual = gerarDadosParaLeituraHelper1.getLeituraAtual();
			this.confirmacao = gerarDadosParaLeituraHelper1.getConfirmacao();
			this.consumoInformado = gerarDadosParaLeituraHelper1.getConsumoInformado();
			this.responsavel = gerarDadosParaLeituraHelper1.getResponsavel();
			this.leituraAnterior = gerarDadosParaLeituraHelper1.getLeituraAnterior();
			this.consumoMedio = gerarDadosParaLeituraHelper1.getConsumoMedio();
			this.observacao = gerarDadosParaLeituraHelper1.getObservacao();
			this.localizacao = gerarDadosParaLeituraHelper1.getLocalizacao();
			this.codigoCategoria = gerarDadosParaLeituraHelper1.getCodigoCategoria();
			this.faixaLeitura = gerarDadosParaLeituraHelper1.getFaixaLeitura();
			this.elo = gerarDadosParaLeituraHelper1.getElo();
			this.dataAtual = gerarDadosParaLeituraHelper1.getDataAtual();
			this.dataLeituraAnterior = gerarDadosParaLeituraHelper1.getDataLeituraAnterior();
		}

		if(gerarDadosParaLeituraHelper2 != null){
			this.numeroPagina = gerarDadosParaLeituraHelper2.getNumeroPagina();

			this.codigoRota1 = gerarDadosParaLeituraHelper2.getCodigoRota();
			this.codigoLocalidade1 = gerarDadosParaLeituraHelper2.getCodigoLocalidade();
			this.descricaoLocalidade1 = gerarDadosParaLeituraHelper2.getDescricaoLocalidade();
			this.anoMesReferencia1 = gerarDadosParaLeituraHelper2.getAnoMesReferncia();
			this.grupo1 = gerarDadosParaLeituraHelper2.getGrupo();
			this.codigoSetor1 = gerarDadosParaLeituraHelper2.getCodigoSetor();
			this.sequencialRota1 = gerarDadosParaLeituraHelper2.getSequencialRota();
			this.nomeClienteUsuario1 = gerarDadosParaLeituraHelper2.getNomeClienteUsuario();
			this.numeroHidrometro1 = gerarDadosParaLeituraHelper2.getNumeroHidrometro();
			this.localInstalacao1 = gerarDadosParaLeituraHelper2.getLocalInstalacao();
			this.protecao1 = gerarDadosParaLeituraHelper2.getProtecao();
			this.inscricao1 = gerarDadosParaLeituraHelper2.getInscricao();
			this.matricula1 = gerarDadosParaLeituraHelper2.getMatriculaImovel();
			this.enderecoImovel1 = gerarDadosParaLeituraHelper2.getEnderecoImovel();

			this.ocorrencia1 = gerarDadosParaLeituraHelper2.getOcorrencia();
			this.leituraAtual1 = gerarDadosParaLeituraHelper2.getLeituraAtual();
			this.confirmacao1 = gerarDadosParaLeituraHelper2.getConfirmacao();
			this.consumoInformado1 = gerarDadosParaLeituraHelper2.getConsumoInformado();
			this.responsavel1 = gerarDadosParaLeituraHelper2.getResponsavel();
			this.leituraAnterior1 = gerarDadosParaLeituraHelper2.getLeituraAnterior();
			this.consumoMedio1 = gerarDadosParaLeituraHelper2.getConsumoMedio();
			this.observacao1 = gerarDadosParaLeituraHelper2.getObservacao();
			this.localizacao1 = gerarDadosParaLeituraHelper2.getLocalizacao();
			this.codigoCategoria1 = gerarDadosParaLeituraHelper2.getCodigoCategoria();
			this.faixaLeitura1 = gerarDadosParaLeituraHelper2.getFaixaLeitura();
			this.elo1 = gerarDadosParaLeituraHelper2.getElo();
			this.dataAtual1 = gerarDadosParaLeituraHelper2.getDataAtual();
			this.dataLeituraAnterior1 = gerarDadosParaLeituraHelper2.getDataLeituraAnterior();
		}
	}

	public String getCodigoRota1(){

		return codigoRota1;
	}

	public void setCodigoRota1(String codigoRota1){

		this.codigoRota1 = codigoRota1;
	}

	public String getCodigoLocalidade1(){

		return codigoLocalidade1;
	}

	public void setCodigoLocalidade1(String codigoLocalidade1){

		this.codigoLocalidade1 = codigoLocalidade1;
	}

	public String getDescricaoLocalidade1(){

		return descricaoLocalidade1;
	}

	public void setDescricaoLocalidade1(String descricaoLocalidade1){

		this.descricaoLocalidade1 = descricaoLocalidade1;
	}

	public String getAnoMesReferencia1(){

		return anoMesReferencia1;
	}

	public void setAnoMesReferencia1(String anoMesReferencia1){

		this.anoMesReferencia1 = anoMesReferencia1;
	}

	public String getGrupo1(){

		return grupo1;
	}

	public void setGrupo1(String grupo1){

		this.grupo1 = grupo1;
	}

	public String getCodigoSetor1(){

		return codigoSetor1;
	}

	public void setCodigoSetor1(String codigoSetor1){

		this.codigoSetor1 = codigoSetor1;
	}

	public String getSequencialRota1(){

		return sequencialRota1;
	}

	public void setSequencialRota1(String sequencialRota1){

		this.sequencialRota1 = sequencialRota1;
	}

	public String getNomeClienteUsuario1(){

		return nomeClienteUsuario1;
	}

	public void setNomeClienteUsuario1(String nomeClienteUsuario1){

		this.nomeClienteUsuario1 = nomeClienteUsuario1;
	}

	public String getNumeroHidrometro1(){

		return numeroHidrometro1;
	}

	public void setNumeroHidrometro1(String numeroHidrometro1){

		this.numeroHidrometro1 = numeroHidrometro1;
	}

	public String getLocalInstalacao1(){

		return localInstalacao1;
	}

	public void setLocalInstalacao1(String localInstalacao1){

		this.localInstalacao1 = localInstalacao1;
	}

	public String getProtecao1(){

		return protecao1;
	}

	public void setProtecao1(String protecao1){

		this.protecao1 = protecao1;
	}

	public String getInscricao1(){

		return inscricao1;
	}

	public void setInscricao1(String inscricao1){

		this.inscricao1 = inscricao1;
	}

	public String getMatricula1(){

		return matricula1;
	}

	public void setMatricula1(String matricula1){

		this.matricula1 = matricula1;
	}

	public String getEnderecoImovel1(){

		return enderecoImovel1;
	}

	public void setEnderecoImovel1(String enderecoImovel1){

		this.enderecoImovel1 = enderecoImovel1;
	}

	public String getOcorrencia1(){

		return ocorrencia1;
	}

	public void setOcorrencia1(String ocorrencia1){

		this.ocorrencia1 = ocorrencia1;
	}

	public String getLeituraAtual1(){

		return leituraAtual1;
	}

	public void setLeituraAtual1(String leituraAtual1){

		this.leituraAtual1 = leituraAtual1;
	}

	public String getConfirmacao1(){

		return confirmacao1;
	}

	public void setConfirmacao1(String confirmacao1){

		this.confirmacao1 = confirmacao1;
	}

	public String getConsumoInformado1(){

		return consumoInformado1;
	}

	public void setConsumoInformado1(String consumoInformado1){

		this.consumoInformado1 = consumoInformado1;
	}

	public String getResponsavel1(){

		return responsavel1;
	}

	public void setResponsavel1(String responsavel1){

		this.responsavel1 = responsavel1;
	}

	public String getLeituraAnterior1(){

		return leituraAnterior1;
	}

	public void setLeituraAnterior1(String leituraAnterior1){

		this.leituraAnterior1 = leituraAnterior1;
	}

	public String getConsumoMedio1(){

		return consumoMedio1;
	}

	public void setConsumoMedio1(String consumoMedio1){

		this.consumoMedio1 = consumoMedio1;
	}

	public String getObservacao1(){

		return observacao1;
	}

	public void setObservacao1(String observacao1){

		this.observacao1 = observacao1;
	}

	public String getLocalizacao1(){

		return localizacao1;
	}

	public void setLocalizacao1(String localizacao1){

		this.localizacao1 = localizacao1;
	}

	public String getCodigoCategoria1(){

		return codigoCategoria1;
	}

	public void setCodigoCategoria1(String codigoCategoria1){

		this.codigoCategoria1 = codigoCategoria1;
	}

	public String getFaixaLeitura1(){

		return faixaLeitura1;
	}

	public void setFaixaLeitura1(String faixaLeitura1){

		this.faixaLeitura1 = faixaLeitura1;
	}

	public String getElo1(){

		return elo1;
	}

	public void setElo1(String elo1){

		this.elo1 = elo1;
	}

	public String getDataAtual1(){

		return dataAtual1;
	}

	public void setDataAtual1(String dataAtual1){

		this.dataAtual1 = dataAtual1;
	}

	public String getDataLeituraAnterior1(){

		return dataLeituraAnterior1;
	}

	public void setDataLeituraAnterior1(String dataLeituraAnterior1){

		this.dataLeituraAnterior1 = dataLeituraAnterior1;
	}

	public RelatorioGerarDadosParaLeituraBean(GerarDadosParaLeituraHelper gerarDadosParaLeituraHelper) {

		this.codigoRota = gerarDadosParaLeituraHelper.getCodigoRota();
		this.codigoLocalidade = gerarDadosParaLeituraHelper.getCodigoLocalidade();
		this.descricaoLocalidade = gerarDadosParaLeituraHelper.getDescricaoLocalidade();
		this.anoMesReferencia = gerarDadosParaLeituraHelper.getAnoMesReferncia();
		this.grupo = gerarDadosParaLeituraHelper.getGrupo();
		this.codigoSetor = gerarDadosParaLeituraHelper.getCodigoSetor();
		this.sequencialRota = gerarDadosParaLeituraHelper.getSequencialRota();
		this.nomeClienteUsuario = gerarDadosParaLeituraHelper.getNomeClienteUsuario();
		this.numeroHidrometro = gerarDadosParaLeituraHelper.getNumeroHidrometro();
		this.localInstalacao = gerarDadosParaLeituraHelper.getLocalInstalacao();
		this.protecao = gerarDadosParaLeituraHelper.getProtecao();
		this.inscricao = gerarDadosParaLeituraHelper.getInscricao();
		this.enderecoImovel = gerarDadosParaLeituraHelper.getEnderecoImovel();
		this.matricula = gerarDadosParaLeituraHelper.getMatricula();

		this.ocorrencia = gerarDadosParaLeituraHelper.getOcorrencia();
		this.leituraAtual = gerarDadosParaLeituraHelper.getLeituraAtual();
		this.confirmacao = gerarDadosParaLeituraHelper.getConfirmacao();
		this.consumoInformado = gerarDadosParaLeituraHelper.getConsumoInformado();
		this.responsavel = gerarDadosParaLeituraHelper.getResponsavel();
		this.leituraAnterior = gerarDadosParaLeituraHelper.getLeituraAnterior();
		this.consumoMedio = gerarDadosParaLeituraHelper.getConsumoMedio();
		this.observacao = gerarDadosParaLeituraHelper.getObservacao();
		this.codigoCategoria = gerarDadosParaLeituraHelper.getCodigoCategoria();
		this.faixaLeitura = gerarDadosParaLeituraHelper.getFaixaLeitura();
		this.elo = gerarDadosParaLeituraHelper.getElo();
		this.dataAtual = gerarDadosParaLeituraHelper.getDataAtual();
		this.dataLeituraAnterior = gerarDadosParaLeituraHelper.getDataLeituraAnterior();

		this.indicadorLigacaoAtiva = gerarDadosParaLeituraHelper.getIndicadorLigacaoAtiva();
		this.indicadorLigacaoCortada = gerarDadosParaLeituraHelper.getIndicadorLigacaoCortada();
		this.indicadorLigacaoComEsgoto = gerarDadosParaLeituraHelper.getIndicadorLigacaoComEsgoto();
		this.indicadorLigacaoSemEsgoto = gerarDadosParaLeituraHelper.getIndicadorLigacaoSemEsgoto();
		this.indicadorLigacaoComHidrometro = gerarDadosParaLeituraHelper.getIndicadorLigacaoComHidrometro();
		this.indicadorLigacaoSemHidrometro = gerarDadosParaLeituraHelper.getIndicadorLigacaoSemHidrometro();
		this.indicadorLigacaoComServicosCobrados = gerarDadosParaLeituraHelper.getIndicadorLigacaoComServicosCobrados();
		this.indicadorLigacaoComDevolucoes = gerarDadosParaLeituraHelper.getIndicadorLigacaoComDevolucoes();
		this.indicadorLigacaoComEmissaoConta = gerarDadosParaLeituraHelper.getIndicadorLigacaoComEmissaoConta();
		this.indicadorLigacaoSemEmissaoConta = gerarDadosParaLeituraHelper.getIndicadorLigacaoSemEmissaoConta();
	}

	public String getAnoMesReferencia(){

		return anoMesReferencia;
	}

	public void setAnoMesReferencia(String anoMesReferencia){

		this.anoMesReferencia = anoMesReferencia;
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

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
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

	public String getLeituraAnterior(){

		return leituraAnterior;
	}

	public void setLeituraAnterior(String leituraAnterior){

		this.leituraAnterior = leituraAnterior;
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

	public String getConsumoMedio(){

		return consumoMedio;
	}

	public void setConsumoMedio(String consumoMedio){

		this.consumoMedio = consumoMedio;
	}

	public String getObservacao(){

		return observacao;
	}

	public void setObservacao(String observacao){

		this.observacao = observacao;
	}

	public String getLocalizacao(){

		return localizacao;
	}

	public void setLocalizacao(String localizacao){

		this.localizacao = localizacao;
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

	public String getCodigoCategoria(){

		return codigoCategoria;
	}

	public void setCodigoCategoria(String codigoCategoria){

		this.codigoCategoria = codigoCategoria;
	}

	public String getFaixaLeitura(){

		return faixaLeitura;
	}

	public void setFaixaLeitura(String faixaLeitura){

		this.faixaLeitura = faixaLeitura;
	}

	public String getCodigoLocalidade(){

		return codigoLocalidade;
	}

	public void setCodigoLocalidade(String codigoLocalidade){

		this.codigoLocalidade = codigoLocalidade;
	}

	public String getElo(){

		return elo;
	}

	public void setElo(String elo){

		this.elo = elo;
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

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
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
