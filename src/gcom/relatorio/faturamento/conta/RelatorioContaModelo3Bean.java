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

package gcom.relatorio.faturamento.conta;

import gcom.relatorio.RelatorioBean;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class RelatorioContaModelo3Bean
				implements RelatorioBean {

	private String sequencial;

	private String matricula;

	private String referencia;

	private String codigoResponsavel;

	private String nomeCliente;

	private String enderecoImovel;

	private String quantidadeEconomiasResidencial;

	private String quantidadeEconomiasComercial;

	private String quantidadeEconomiasIndustrial;

	private String quantidadeEconomiasPublica;

	private String inscricao;

	private String numeroHidrometro;

	private String dataInstalacaoHidrometro;

	private String marcaHidrometro;

	private String localInstalacaoHidrometro;

	private String capacidadeHidrometro;

	private String situacaoAgua;

	private String situacaoEsgoto;

	private String dataLeitura;

	private String qualidadeTurbidezExigida;

	private String qualidadeTurbidezRealizada;

	private String qualidadeTurbidezConforme;

	private String qualidadeCorExigida;

	private String qualidadeCorRealizada;

	private String qualidadeCorConforme;

	private String qualidadeCloroExigida;

	private String qualidadeCloroRealizada;

	private String qualidadeCloroConforme;

	private String qualidadeFluorExigida;

	private String qualidadeFluorRealizada;

	private String qualidadeFluorConforme;

	private String qualidadeColiformesTotaisExigida;

	private String qualidadeColiformesTotaisRealizada;

	private String qualidadeColiformesTotaisConforme;

	private String qualidadeColiformesTermotolerantesExigida;

	private String qualidadeColiformesTermotolerantesRealizada;

	private String qualidadeColiformesTermotolerantesConforme;

	private String qualidadeConclusao;

	private String leituraAnterior;

	private String leituraAtual;

	private String situacaoLeitura;

	private String consumo;

	private String periodoFaturamento;

	private String diasConsumo;

	private String media;

	private String condicaoFaturamento;

	private String anormalidadeLeitura;

	private String descricaoAnormalidade;

	private String faturaAtrasoReferencia1;

	private String faturaAtrasoValor1;

	private String faturaAtrasoReferencia2;

	private String faturaAtrasoValor2;

	private String faturaAtrasoReferencia3;

	private String faturaAtrasoValor3;

	private String faturaAtrasoReferencia4;

	private String faturaAtrasoValor4;

	private String faturaAtrasoReferencia5;

	private String faturaAtrasoValor5;

	private String faturaAtrasoReferencia6;

	private String faturaAtrasoValor6;

	private String faturaAtrasoReferencia7;

	private String faturaAtrasoValor7;

	private String faturaAtrasoReferencia8;

	private String faturaAtrasoValor8;

	private String faturaAtrasoReferencia9;

	private String faturaAtrasoValor9;

	private String faturaAtrasoReferencia10;

	private String faturaAtrasoValor10;

	private String faturaAtrasoReferencia11;

	private String faturaAtrasoValor11;

	private String faturaAtrasoReferencia12;

	private String faturaAtrasoValor12;

	private String outrosMesesFaturaAtraso;

	private String debitoDataCorrente;

	private String totalValorContasAtraso;

	private String ignoreDataCorrenteMenosDoisDias;

	private String referenciaConsumoMes1;

	private String leituraMes1;

	private String anormalidadeMes1;

	private String consumoFaturadoMes1;

	private String referenciaConsumoMes2;

	private String leituraMes2;

	private String anormalidadeMes2;

	private String consumoFaturadoMes2;

	private String referenciaConsumoMes3;

	private String leituraMes3;

	private String anormalidadeMes3;

	private String consumoFaturadoMes3;

	private String referenciaConsumoMes4;

	private String leituraMes4;

	private String anormalidadeMes4;

	private String consumoFaturadoMes4;

	private String referenciaConsumoMes5;

	private String leituraMes5;

	private String anormalidadeMes5;

	private String consumoFaturadoMes5;

	private String referenciaConsumoMes6;

	private String leituraMes6;

	private String anormalidadeMes6;

	private String consumoFaturadoMes6;

	private String mensagemConta;

	private String vencimento;

	private String totalAPagar;

	private String representacaoNumericaCodBarraFormatada;

	private String representacaoNumericaCodBarraSemDigito;

	private String localidade;

	private String setor;

	private String quadra;

	private String lote;

	private JRBeanCollectionDataSource beansVerso;

	private ArrayList arrayVersoDetailBean;

	private String faturamentoRealizado;

	private String enderecoClienteEntrega;

	private String cep;

	private String codigoAuxiliar;

	private String debitosExercicio;

	public String getSequencial(){

		return sequencial;
	}

	public void setSequencial(String sequencial){

		this.sequencial = sequencial;
	}

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public String getReferencia(){

		return referencia;
	}

	public void setReferencia(String referencia){

		this.referencia = referencia;
	}

	public String getCodigoResponsavel(){

		return codigoResponsavel;
	}

	public void setCodigoResponsavel(String codigoResponsavel){

		this.codigoResponsavel = codigoResponsavel;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getQuantidadeEconomiasResidencial(){

		return quantidadeEconomiasResidencial;
	}

	public void setQuantidadeEconomiasResidencial(String quantidadeEconomiasResidencial){

		this.quantidadeEconomiasResidencial = quantidadeEconomiasResidencial;
	}

	public String getQuantidadeEconomiasComercial(){

		return quantidadeEconomiasComercial;
	}

	public void setQuantidadeEconomiasComercial(String quantidadeEconomiasComercial){

		this.quantidadeEconomiasComercial = quantidadeEconomiasComercial;
	}

	public String getQuantidadeEconomiasIndustrial(){

		return quantidadeEconomiasIndustrial;
	}

	public void setQuantidadeEconomiasIndustrial(String quantidadeEconomiasIndustrial){

		this.quantidadeEconomiasIndustrial = quantidadeEconomiasIndustrial;
	}

	public String getQuantidadeEconomiasPublica(){

		return quantidadeEconomiasPublica;
	}

	public void setQuantidadeEconomiasPublica(String quantidadeEconomiasPublica){

		this.quantidadeEconomiasPublica = quantidadeEconomiasPublica;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	public String getDataInstalacaoHidrometro(){

		return dataInstalacaoHidrometro;
	}

	public void setDataInstalacaoHidrometro(String dataInstalacaoHidrometro){

		this.dataInstalacaoHidrometro = dataInstalacaoHidrometro;
	}

	public String getMarcaHidrometro(){

		return marcaHidrometro;
	}

	public void setMarcaHidrometro(String marcaHidrometro){

		this.marcaHidrometro = marcaHidrometro;
	}

	public String getLocalInstalacaoHidrometro(){

		return localInstalacaoHidrometro;
	}

	public void setLocalInstalacaoHidrometro(String localInstalacaoHidrometro){

		this.localInstalacaoHidrometro = localInstalacaoHidrometro;
	}

	public String getCapacidadeHidrometro(){

		return capacidadeHidrometro;
	}

	public void setCapacidadeHidrometro(String capacidadeHidrometro){

		this.capacidadeHidrometro = capacidadeHidrometro;
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

	public String getDataLeitura(){

		return dataLeitura;
	}

	public void setDataLeitura(String dataLeitura){

		this.dataLeitura = dataLeitura;
	}

	public String getQualidadeTurbidezExigida(){

		return qualidadeTurbidezExigida;
	}

	public void setQualidadeTurbidezExigida(String qualidadeTurbidezExigida){

		this.qualidadeTurbidezExigida = qualidadeTurbidezExigida;
	}

	public String getQualidadeTurbidezRealizada(){

		return qualidadeTurbidezRealizada;
	}

	public void setQualidadeTurbidezRealizada(String qualidadeTurbidezRealizada){

		this.qualidadeTurbidezRealizada = qualidadeTurbidezRealizada;
	}

	public String getQualidadeTurbidezConforme(){

		return qualidadeTurbidezConforme;
	}

	public void setQualidadeTurbidezConforme(String qualidadeTurbidezConforme){

		this.qualidadeTurbidezConforme = qualidadeTurbidezConforme;
	}

	public String getQualidadeCorExigida(){

		return qualidadeCorExigida;
	}

	public void setQualidadeCorExigida(String qualidadeCorExigida){

		this.qualidadeCorExigida = qualidadeCorExigida;
	}

	public String getQualidadeCorRealizada(){

		return qualidadeCorRealizada;
	}

	public void setQualidadeCorRealizada(String qualidadeCorRealizada){

		this.qualidadeCorRealizada = qualidadeCorRealizada;
	}

	public String getQualidadeCorConforme(){

		return qualidadeCorConforme;
	}

	public void setQualidadeCorConforme(String qualidadeCorConforme){

		this.qualidadeCorConforme = qualidadeCorConforme;
	}

	public String getQualidadeCloroExigida(){

		return qualidadeCloroExigida;
	}

	public void setQualidadeCloroExigida(String qualidadeCloroExigida){

		this.qualidadeCloroExigida = qualidadeCloroExigida;
	}

	public String getQualidadeCloroRealizada(){

		return qualidadeCloroRealizada;
	}

	public void setQualidadeCloroRealizada(String qualidadeCloroRealizada){

		this.qualidadeCloroRealizada = qualidadeCloroRealizada;
	}

	public String getQualidadeCloroConforme(){

		return qualidadeCloroConforme;
	}

	public void setQualidadeCloroConforme(String qualidadeCloroConforme){

		this.qualidadeCloroConforme = qualidadeCloroConforme;
	}

	public String getQualidadeFluorExigida(){

		return qualidadeFluorExigida;
	}

	public void setQualidadeFluorExigida(String qualidadeFluorExigida){

		this.qualidadeFluorExigida = qualidadeFluorExigida;
	}

	public String getQualidadeFluorRealizada(){

		return qualidadeFluorRealizada;
	}

	public void setQualidadeFluorRealizada(String qualidadeFluorRealizada){

		this.qualidadeFluorRealizada = qualidadeFluorRealizada;
	}

	public String getQualidadeFluorConforme(){

		return qualidadeFluorConforme;
	}

	public void setQualidadeFluorConforme(String qualidadeFluorConforme){

		this.qualidadeFluorConforme = qualidadeFluorConforme;
	}

	public String getQualidadeColiformesTotaisExigida(){

		return qualidadeColiformesTotaisExigida;
	}

	public void setQualidadeColiformesTotaisExigida(String qualidadeColiformesTotaisExigida){

		this.qualidadeColiformesTotaisExigida = qualidadeColiformesTotaisExigida;
	}

	public String getQualidadeColiformesTotaisRealizada(){

		return qualidadeColiformesTotaisRealizada;
	}

	public void setQualidadeColiformesTotaisRealizada(String qualidadeColiformesTotaisRealizada){

		this.qualidadeColiformesTotaisRealizada = qualidadeColiformesTotaisRealizada;
	}

	public String getQualidadeColiformesTotaisConforme(){

		return qualidadeColiformesTotaisConforme;
	}

	public void setQualidadeColiformesTotaisConforme(String qualidadeColiformesTotaisConforme){

		this.qualidadeColiformesTotaisConforme = qualidadeColiformesTotaisConforme;
	}

	public String getQualidadeColiformesTermotolerantesExigida(){

		return qualidadeColiformesTermotolerantesExigida;
	}

	public void setQualidadeColiformesTermotolerantesExigida(String qualidadeColiformesTermotolerantesExigida){

		this.qualidadeColiformesTermotolerantesExigida = qualidadeColiformesTermotolerantesExigida;
	}

	public String getQualidadeColiformesTermotolerantesRealizada(){

		return qualidadeColiformesTermotolerantesRealizada;
	}

	public void setQualidadeColiformesTermotolerantesRealizada(String qualidadeColiformesTermotolerantesRealizada){

		this.qualidadeColiformesTermotolerantesRealizada = qualidadeColiformesTermotolerantesRealizada;
	}

	public String getQualidadeColiformesTermotolerantesConforme(){

		return qualidadeColiformesTermotolerantesConforme;
	}

	public void setQualidadeColiformesTermotolerantesConforme(String qualidadeColiformesTermotolerantesConforme){

		this.qualidadeColiformesTermotolerantesConforme = qualidadeColiformesTermotolerantesConforme;
	}

	public String getQualidadeConclusao(){

		return qualidadeConclusao;
	}

	public void setQualidadeConclusao(String qualidadeConclusao){

		this.qualidadeConclusao = qualidadeConclusao;
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

	public String getSituacaoLeitura(){

		return situacaoLeitura;
	}

	public void setSituacaoLeitura(String situacaoLeitura){

		this.situacaoLeitura = situacaoLeitura;
	}

	public String getConsumo(){

		return consumo;
	}

	public void setConsumo(String consumo){

		this.consumo = consumo;
	}

	public String getPeriodoFaturamento(){

		return periodoFaturamento;
	}

	public void setPeriodoFaturamento(String periodoFaturamento){

		this.periodoFaturamento = periodoFaturamento;
	}

	public String getDiasConsumo(){

		return diasConsumo;
	}

	public void setDiasConsumo(String diasConsumo){

		this.diasConsumo = diasConsumo;
	}

	public String getMedia(){

		return media;
	}

	public void setMedia(String media){

		this.media = media;
	}

	public String getCondicaoFaturamento(){

		return condicaoFaturamento;
	}

	public void setCondicaoFaturamento(String condicaoFaturamento){

		this.condicaoFaturamento = condicaoFaturamento;
	}

	public String getAnormalidadeLeitura(){

		return anormalidadeLeitura;
	}

	public void setAnormalidadeLeitura(String anormalidadeLeitura){

		this.anormalidadeLeitura = anormalidadeLeitura;
	}

	public String getDescricaoAnormalidade(){

		return descricaoAnormalidade;
	}

	public void setDescricaoAnormalidade(String descricaoAnormalidade){

		this.descricaoAnormalidade = descricaoAnormalidade;
	}

	public String getFaturaAtrasoReferencia1(){

		return faturaAtrasoReferencia1;
	}

	public void setFaturaAtrasoReferencia1(String faturaAtrasoReferencia1){

		this.faturaAtrasoReferencia1 = faturaAtrasoReferencia1;
	}

	public String getFaturaAtrasoValor1(){

		return faturaAtrasoValor1;
	}

	public void setFaturaAtrasoValor1(String faturaAtrasoValor1){

		this.faturaAtrasoValor1 = faturaAtrasoValor1;
	}

	public String getFaturaAtrasoReferencia2(){

		return faturaAtrasoReferencia2;
	}

	public void setFaturaAtrasoReferencia2(String faturaAtrasoReferencia2){

		this.faturaAtrasoReferencia2 = faturaAtrasoReferencia2;
	}

	public String getFaturaAtrasoValor2(){

		return faturaAtrasoValor2;
	}

	public void setFaturaAtrasoValor2(String faturaAtrasoValor2){

		this.faturaAtrasoValor2 = faturaAtrasoValor2;
	}

	public String getFaturaAtrasoReferencia3(){

		return faturaAtrasoReferencia3;
	}

	public void setFaturaAtrasoReferencia3(String faturaAtrasoReferencia3){

		this.faturaAtrasoReferencia3 = faturaAtrasoReferencia3;
	}

	public String getFaturaAtrasoValor3(){

		return faturaAtrasoValor3;
	}

	public void setFaturaAtrasoValor3(String faturaAtrasoValor3){

		this.faturaAtrasoValor3 = faturaAtrasoValor3;
	}

	public String getFaturaAtrasoReferencia4(){

		return faturaAtrasoReferencia4;
	}

	public void setFaturaAtrasoReferencia4(String faturaAtrasoReferencia4){

		this.faturaAtrasoReferencia4 = faturaAtrasoReferencia4;
	}

	public String getFaturaAtrasoValor4(){

		return faturaAtrasoValor4;
	}

	public void setFaturaAtrasoValor4(String faturaAtrasoValor4){

		this.faturaAtrasoValor4 = faturaAtrasoValor4;
	}

	public String getFaturaAtrasoReferencia5(){

		return faturaAtrasoReferencia5;
	}

	public void setFaturaAtrasoReferencia5(String faturaAtrasoReferencia5){

		this.faturaAtrasoReferencia5 = faturaAtrasoReferencia5;
	}

	public String getFaturaAtrasoValor5(){

		return faturaAtrasoValor5;
	}

	public void setFaturaAtrasoValor5(String faturaAtrasoValor5){

		this.faturaAtrasoValor5 = faturaAtrasoValor5;
	}

	public String getFaturaAtrasoReferencia6(){

		return faturaAtrasoReferencia6;
	}

	public void setFaturaAtrasoReferencia6(String faturaAtrasoReferencia6){

		this.faturaAtrasoReferencia6 = faturaAtrasoReferencia6;
	}

	public String getFaturaAtrasoValor6(){

		return faturaAtrasoValor6;
	}

	public void setFaturaAtrasoValor6(String faturaAtrasoValor6){

		this.faturaAtrasoValor6 = faturaAtrasoValor6;
	}

	public String getFaturaAtrasoReferencia7(){

		return faturaAtrasoReferencia7;
	}

	public void setFaturaAtrasoReferencia7(String faturaAtrasoReferencia7){

		this.faturaAtrasoReferencia7 = faturaAtrasoReferencia7;
	}

	public String getFaturaAtrasoValor7(){

		return faturaAtrasoValor7;
	}

	public void setFaturaAtrasoValor7(String faturaAtrasoValor7){

		this.faturaAtrasoValor7 = faturaAtrasoValor7;
	}

	public String getFaturaAtrasoReferencia8(){

		return faturaAtrasoReferencia8;
	}

	public void setFaturaAtrasoReferencia8(String faturaAtrasoReferencia8){

		this.faturaAtrasoReferencia8 = faturaAtrasoReferencia8;
	}

	public String getFaturaAtrasoValor8(){

		return faturaAtrasoValor8;
	}

	public void setFaturaAtrasoValor8(String faturaAtrasoValor8){

		this.faturaAtrasoValor8 = faturaAtrasoValor8;
	}

	public String getFaturaAtrasoReferencia9(){

		return faturaAtrasoReferencia9;
	}

	public void setFaturaAtrasoReferencia9(String faturaAtrasoReferencia9){

		this.faturaAtrasoReferencia9 = faturaAtrasoReferencia9;
	}

	public String getFaturaAtrasoValor9(){

		return faturaAtrasoValor9;
	}

	public void setFaturaAtrasoValor9(String faturaAtrasoValor9){

		this.faturaAtrasoValor9 = faturaAtrasoValor9;
	}

	public String getFaturaAtrasoReferencia10(){

		return faturaAtrasoReferencia10;
	}

	public void setFaturaAtrasoReferencia10(String faturaAtrasoReferencia10){

		this.faturaAtrasoReferencia10 = faturaAtrasoReferencia10;
	}

	public String getFaturaAtrasoValor10(){

		return faturaAtrasoValor10;
	}

	public void setFaturaAtrasoValor10(String faturaAtrasoValor10){

		this.faturaAtrasoValor10 = faturaAtrasoValor10;
	}

	public String getFaturaAtrasoReferencia11(){

		return faturaAtrasoReferencia11;
	}

	public void setFaturaAtrasoReferencia11(String faturaAtrasoReferencia11){

		this.faturaAtrasoReferencia11 = faturaAtrasoReferencia11;
	}

	public String getFaturaAtrasoValor11(){

		return faturaAtrasoValor11;
	}

	public void setFaturaAtrasoValor11(String faturaAtrasoValor11){

		this.faturaAtrasoValor11 = faturaAtrasoValor11;
	}

	public String getFaturaAtrasoReferencia12(){

		return faturaAtrasoReferencia12;
	}

	public void setFaturaAtrasoReferencia12(String faturaAtrasoReferencia12){

		this.faturaAtrasoReferencia12 = faturaAtrasoReferencia12;
	}

	public String getFaturaAtrasoValor12(){

		return faturaAtrasoValor12;
	}

	public void setFaturaAtrasoValor12(String faturaAtrasoValor12){

		this.faturaAtrasoValor12 = faturaAtrasoValor12;
	}

	public String getOutrosMesesFaturaAtraso(){

		return outrosMesesFaturaAtraso;
	}

	public void setOutrosMesesFaturaAtraso(String outrosMesesFaturaAtraso){

		this.outrosMesesFaturaAtraso = outrosMesesFaturaAtraso;
	}

	public String getDebitoDataCorrente(){

		return debitoDataCorrente;
	}

	public void setDebitoDataCorrente(String debitoDataCorrente){

		this.debitoDataCorrente = debitoDataCorrente;
	}

	public String getTotalValorContasAtraso(){

		return totalValorContasAtraso;
	}

	public void setTotalValorContasAtraso(String totalValorContasAtraso){

		this.totalValorContasAtraso = totalValorContasAtraso;
	}

	public String getIgnoreDataCorrenteMenosDoisDias(){

		return ignoreDataCorrenteMenosDoisDias;
	}

	public void setIgnoreDataCorrenteMenosDoisDias(String ignoreDataCorrenteMenosDoisDias){

		this.ignoreDataCorrenteMenosDoisDias = ignoreDataCorrenteMenosDoisDias;
	}

	public String getReferenciaConsumoMes1(){

		return referenciaConsumoMes1;
	}

	public void setReferenciaConsumoMes1(String referenciaConsumoMes1){

		this.referenciaConsumoMes1 = referenciaConsumoMes1;
	}

	public String getLeituraMes1(){

		return leituraMes1;
	}

	public void setLeituraMes1(String leituraMes1){

		this.leituraMes1 = leituraMes1;
	}

	public String getAnormalidadeMes1(){

		return anormalidadeMes1;
	}

	public void setAnormalidadeMes1(String anormalidadeMes1){

		this.anormalidadeMes1 = anormalidadeMes1;
	}

	public String getConsumoFaturadoMes1(){

		return consumoFaturadoMes1;
	}

	public void setConsumoFaturadoMes1(String consumoFaturadoMes1){

		this.consumoFaturadoMes1 = consumoFaturadoMes1;
	}

	public String getReferenciaConsumoMes2(){

		return referenciaConsumoMes2;
	}

	public void setReferenciaConsumoMes2(String referenciaConsumoMes2){

		this.referenciaConsumoMes2 = referenciaConsumoMes2;
	}

	public String getLeituraMes2(){

		return leituraMes2;
	}

	public void setLeituraMes2(String leituraMes2){

		this.leituraMes2 = leituraMes2;
	}

	public String getAnormalidadeMes2(){

		return anormalidadeMes2;
	}

	public void setAnormalidadeMes2(String anormalidadeMes2){

		this.anormalidadeMes2 = anormalidadeMes2;
	}

	public String getConsumoFaturadoMes2(){

		return consumoFaturadoMes2;
	}

	public void setConsumoFaturadoMes2(String consumoFaturadoMes2){

		this.consumoFaturadoMes2 = consumoFaturadoMes2;
	}

	public String getReferenciaConsumoMes3(){

		return referenciaConsumoMes3;
	}

	public void setReferenciaConsumoMes3(String referenciaConsumoMes3){

		this.referenciaConsumoMes3 = referenciaConsumoMes3;
	}

	public String getLeituraMes3(){

		return leituraMes3;
	}

	public void setLeituraMes3(String leituraMes3){

		this.leituraMes3 = leituraMes3;
	}

	public String getAnormalidadeMes3(){

		return anormalidadeMes3;
	}

	public void setAnormalidadeMes3(String anormalidadeMes3){

		this.anormalidadeMes3 = anormalidadeMes3;
	}

	public String getConsumoFaturadoMes3(){

		return consumoFaturadoMes3;
	}

	public void setConsumoFaturadoMes3(String consumoFaturadoMes3){

		this.consumoFaturadoMes3 = consumoFaturadoMes3;
	}

	public String getReferenciaConsumoMes4(){

		return referenciaConsumoMes4;
	}

	public void setReferenciaConsumoMes4(String referenciaConsumoMes4){

		this.referenciaConsumoMes4 = referenciaConsumoMes4;
	}

	public String getLeituraMes4(){

		return leituraMes4;
	}

	public void setLeituraMes4(String leituraMes4){

		this.leituraMes4 = leituraMes4;
	}

	public String getAnormalidadeMes4(){

		return anormalidadeMes4;
	}

	public void setAnormalidadeMes4(String anormalidadeMes4){

		this.anormalidadeMes4 = anormalidadeMes4;
	}

	public String getConsumoFaturadoMes4(){

		return consumoFaturadoMes4;
	}

	public void setConsumoFaturadoMes4(String consumoFaturadoMes4){

		this.consumoFaturadoMes4 = consumoFaturadoMes4;
	}

	public String getReferenciaConsumoMes5(){

		return referenciaConsumoMes5;
	}

	public void setReferenciaConsumoMes5(String referenciaConsumoMes5){

		this.referenciaConsumoMes5 = referenciaConsumoMes5;
	}

	public String getLeituraMes5(){

		return leituraMes5;
	}

	public void setLeituraMes5(String leituraMes5){

		this.leituraMes5 = leituraMes5;
	}

	public String getAnormalidadeMes5(){

		return anormalidadeMes5;
	}

	public void setAnormalidadeMes5(String anormalidadeMes5){

		this.anormalidadeMes5 = anormalidadeMes5;
	}

	public String getConsumoFaturadoMes5(){

		return consumoFaturadoMes5;
	}

	public void setConsumoFaturadoMes5(String consumoFaturadoMes5){

		this.consumoFaturadoMes5 = consumoFaturadoMes5;
	}

	public String getReferenciaConsumoMes6(){

		return referenciaConsumoMes6;
	}

	public void setReferenciaConsumoMes6(String referenciaConsumoMes6){

		this.referenciaConsumoMes6 = referenciaConsumoMes6;
	}

	public String getLeituraMes6(){

		return leituraMes6;
	}

	public void setLeituraMes6(String leituraMes6){

		this.leituraMes6 = leituraMes6;
	}

	public String getAnormalidadeMes6(){

		return anormalidadeMes6;
	}

	public void setAnormalidadeMes6(String anormalidadeMes6){

		this.anormalidadeMes6 = anormalidadeMes6;
	}

	public String getConsumoFaturadoMes6(){

		return consumoFaturadoMes6;
	}

	public void setConsumoFaturadoMes6(String consumoFaturadoMes6){

		this.consumoFaturadoMes6 = consumoFaturadoMes6;
	}

	public String getMensagemConta(){

		return mensagemConta;
	}

	public void setMensagemConta(String mensagemConta){

		this.mensagemConta = mensagemConta;
	}

	public String getVencimento(){

		return vencimento;
	}

	public void setVencimento(String vencimento){

		this.vencimento = vencimento;
	}

	public String getTotalAPagar(){

		return totalAPagar;
	}

	public void setTotalAPagar(String totalAPagar){

		this.totalAPagar = totalAPagar;
	}

	public String getRepresentacaoNumericaCodBarraFormatada(){

		return representacaoNumericaCodBarraFormatada;
	}

	public void setRepresentacaoNumericaCodBarraFormatada(String representacaoNumericaCodBarraFormatada){

		this.representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarraFormatada;
	}

	public String getRepresentacaoNumericaCodBarraSemDigito(){

		return representacaoNumericaCodBarraSemDigito;
	}

	public void setRepresentacaoNumericaCodBarraSemDigito(String representacaoNumericaCodBarraSemDigito){

		this.representacaoNumericaCodBarraSemDigito = representacaoNumericaCodBarraSemDigito;
	}

	public String getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

	public String getSetor(){

		return setor;
	}

	public void setSetor(String setor){

		this.setor = setor;
	}

	public String getQuadra(){

		return quadra;
	}

	public void setQuadra(String quadra){

		this.quadra = quadra;
	}

	public String getLote(){

		return lote;
	}

	public void setLote(String lote){

		this.lote = lote;
	}

	public JRBeanCollectionDataSource getBeansVerso(){

		return beansVerso;
	}

	public void setBeansVerso(JRBeanCollectionDataSource beansVerso){

		this.beansVerso = beansVerso;
	}

	public ArrayList getArrayVersoDetailBean(){

		return arrayVersoDetailBean;
	}

	public void setArrayVersoDetailBean(ArrayList arrayVersoDetailBean){

		this.arrayVersoDetailBean = arrayVersoDetailBean;
	}

	public void setarBeansSubRelatorioVerso(Collection colecaoDetail){

		this.arrayVersoDetailBean = new ArrayList();
		this.arrayVersoDetailBean.addAll(colecaoDetail);
		this.beansVerso = new JRBeanCollectionDataSource(this.arrayVersoDetailBean);
	}

	public String getFaturamentoRealizado(){

		return faturamentoRealizado;
	}

	public void setFaturamentoRealizado(String faturamentoRealizado){

		this.faturamentoRealizado = faturamentoRealizado;
	}

	public String getEnderecoClienteEntrega(){

		return enderecoClienteEntrega;
	}

	public void setEnderecoClienteEntrega(String enderecoClienteEntrega){

		this.enderecoClienteEntrega = enderecoClienteEntrega;
	}

	public String getCep(){

		return cep;
	}

	public void setCep(String cep){

		this.cep = cep;
	}

	public String getCodigoAuxiliar(){

		return codigoAuxiliar;
	}

	public void setCodigoAuxiliar(String codigoAuxiliar){

		this.codigoAuxiliar = codigoAuxiliar;
	}

	public String getDebitosExercicio(){

		return debitosExercicio;
	}

	public void setDebitosExercicio(String debitosExercicio){

		this.debitosExercicio = debitosExercicio;
	}

}
