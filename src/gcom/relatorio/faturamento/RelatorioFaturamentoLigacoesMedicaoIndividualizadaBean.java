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

package gcom.relatorio.faturamento;

import gcom.relatorio.RelatorioBean;

import java.util.ArrayList;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * [UC0532] Gerar Relatório de Faturamento das Ligações com Medição Individualizada
 * 
 * @author Vivianne Sousa
 * @date 09/01/2007
 */
public class RelatorioFaturamentoLigacoesMedicaoIndividualizadaBean
				implements RelatorioBean {

	private String idLocalidade;

	private String nomeLocalidade;

	private String matriculaImovel;

	private String inscricao;

	private String nomeConsumidor;

	private String qtdeEconomias;

	private String leituraAnterior;

	private String dataLeituraAnterior;

	private String leituraAtual;

	private String dataLeituraAtual;

	private String media;

	private String consumoImoveisVinculados;

	private String consumoFaturado;

	private String rateio;

	private String consumoEsgoto;

	private String anormalidade;

	private String anormalidadeConsumo;

	private String tipoConsumo;

	private String indicadorPoco;

	private String indicadorQuebraImovelCondominio;

	private String totalConsumidoresRateioMacromedidor;

	private String numeroEconomiasRateio;

	private String ligacaoSituacaoAgua;

	private String ligacaoSituacaoEsgoto;

	private String numeroHidrometro;

	private String valorComRateio;

	private String valorSemRateio;

	private String totalValorSemRateio;

	private String totalValorComRateio;

	private String diferencaComRateioSemRateio;

	private String valorRateioProxFat;

	private String indicadorImovelCondominio;

	private String codigoSetorComercial;

	private String numeroQuadra;

	private String numeroLote;

	private String numeroSubLote;

	private String diferencaConsumoMacroMicros;

	public RelatorioFaturamentoLigacoesMedicaoIndividualizadaBean(String idLocalidade, String nomeLocalidade, String matriculaImovel,
																	String inscricao, String nomeConsumidor, String qtdeEconomias,
																	String leituraAnterior, String dataLeituraAnterior,
																	String leituraAtual, String dataLeituraAtual, String media,
																	String consumoImoveisVinculados, String consumoFaturado, String rateio,
																	String consumoEsgoto, String anormalidade, String anormalidadeConsumo,
																	String tipoConsumo, String indicadorPoco,
																	String indicadorQuebraImovelCondominio,
																	String totalConsumidoresRateioMacromedidor,
																	String numeroEconomiasRateio, String ligacaoSituacaoAgua,
																	String ligacaoSituacaoEsgoto, String numeroHidrometro,
																	String valorComRateio, String valorSemRateio,
																	String totalValorSemRateio, String totalValorComRateio,
																	String diferencaComRateioSemRateio, String valorRateioProxFat,
																	String indicadorImovelCondominio, String codigoSetorComercial,
																	String numeroQuadra, String numeroLote, String numeroSubLote,
																	String diferencaConsumoMacroMicros) {

		this.idLocalidade = idLocalidade;
		this.nomeLocalidade = nomeLocalidade;
		this.matriculaImovel = matriculaImovel;
		this.inscricao = inscricao;
		this.nomeConsumidor = nomeConsumidor;
		this.qtdeEconomias = qtdeEconomias;
		this.leituraAnterior = leituraAnterior;
		this.dataLeituraAnterior = dataLeituraAnterior;
		this.leituraAtual = leituraAtual;
		this.dataLeituraAtual = dataLeituraAtual;
		this.media = media;
		this.consumoImoveisVinculados = consumoImoveisVinculados;
		this.consumoFaturado = consumoFaturado;
		this.rateio = rateio;
		this.consumoEsgoto = consumoEsgoto;
		this.anormalidade = anormalidade;
		this.anormalidadeConsumo = anormalidadeConsumo;
		this.tipoConsumo = tipoConsumo;
		this.indicadorPoco = indicadorPoco;
		this.indicadorQuebraImovelCondominio = indicadorQuebraImovelCondominio;
		this.totalConsumidoresRateioMacromedidor = totalConsumidoresRateioMacromedidor;
		this.numeroEconomiasRateio = numeroEconomiasRateio;
		this.ligacaoSituacaoAgua = ligacaoSituacaoAgua;
		this.ligacaoSituacaoEsgoto = ligacaoSituacaoEsgoto;
		this.numeroHidrometro = numeroHidrometro;
		this.valorComRateio = valorComRateio;
		this.valorSemRateio = valorSemRateio;
		this.totalValorSemRateio = totalValorSemRateio;
		this.totalValorComRateio = totalValorComRateio;
		this.diferencaComRateioSemRateio = diferencaComRateioSemRateio;
		this.valorRateioProxFat = valorRateioProxFat;
		this.indicadorImovelCondominio = indicadorImovelCondominio;
		this.codigoSetorComercial = codigoSetorComercial;
		this.numeroQuadra = numeroQuadra;
		this.numeroLote = numeroLote;
		this.numeroSubLote = numeroSubLote;
		this.diferencaConsumoMacroMicros = diferencaConsumoMacroMicros;
	}

	private String indicadorPrimeiraPagina;

	private JRBeanCollectionDataSource arrayJRDetail;

	private ArrayList arrayRelatorio2ViaContaDetailBean;

	public String getAnormalidade(){

		return anormalidade;
	}

	public void setAnormalidade(String anormalidade){

		this.anormalidade = anormalidade;
	}

	public String getAnormalidadeConsumo(){

		return anormalidadeConsumo;
	}

	public void setAnormalidadeConsumo(String anormalidadeConsumo){

		this.anormalidadeConsumo = anormalidadeConsumo;
	}

	public JRBeanCollectionDataSource getArrayJRDetail(){

		return arrayJRDetail;
	}

	public void setArrayJRDetail(JRBeanCollectionDataSource arrayJRDetail){

		this.arrayJRDetail = arrayJRDetail;
	}

	public ArrayList getArrayRelatorio2ViaContaDetailBean(){

		return arrayRelatorio2ViaContaDetailBean;
	}

	public void setArrayRelatorio2ViaContaDetailBean(ArrayList arrayRelatorio2ViaContaDetailBean){

		this.arrayRelatorio2ViaContaDetailBean = arrayRelatorio2ViaContaDetailBean;
	}

	public String getConsumoEsgoto(){

		return consumoEsgoto;
	}

	public void setConsumoEsgoto(String consumoEsgoto){

		this.consumoEsgoto = consumoEsgoto;
	}

	public String getConsumoFaturado(){

		return consumoFaturado;
	}

	public void setConsumoFaturado(String consumoFaturado){

		this.consumoFaturado = consumoFaturado;
	}

	public String getConsumoImoveisVinculados(){

		return consumoImoveisVinculados;
	}

	public void setConsumoImoveisVinculados(String consumoImoveisVinculados){

		this.consumoImoveisVinculados = consumoImoveisVinculados;
	}

	public String getDataLeituraAnterior(){

		return dataLeituraAnterior;
	}

	public void setDataLeituraAnterior(String dataLeituraAnterior){

		this.dataLeituraAnterior = dataLeituraAnterior;
	}

	public String getDataLeituraAtual(){

		return dataLeituraAtual;
	}

	public void setDataLeituraAtual(String dataLeituraAtual){

		this.dataLeituraAtual = dataLeituraAtual;
	}

	public String getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getIndicadorPoco(){

		return indicadorPoco;
	}

	public void setIndicadorPoco(String indicadorPoco){

		this.indicadorPoco = indicadorPoco;
	}

	public String getIndicadorPrimeiraPagina(){

		return indicadorPrimeiraPagina;
	}

	public void setIndicadorPrimeiraPagina(String indicadorPrimeiraPagina){

		this.indicadorPrimeiraPagina = indicadorPrimeiraPagina;
	}

	public String getIndicadorQuebraImovelCondominio(){

		return indicadorQuebraImovelCondominio;
	}

	public void setIndicadorQuebraImovelCondominio(String indicadorQuebraImovelCondominio){

		this.indicadorQuebraImovelCondominio = indicadorQuebraImovelCondominio;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
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

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getMedia(){

		return media;
	}

	public void setMedia(String media){

		this.media = media;
	}

	public String getNomeConsumidor(){

		return nomeConsumidor;
	}

	public void setNomeConsumidor(String nomeConsumidor){

		this.nomeConsumidor = nomeConsumidor;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public String getNumeroEconomiasRateio(){

		return numeroEconomiasRateio;
	}

	public void setNumeroEconomiasRateio(String numeroEconomiasRateio){

		this.numeroEconomiasRateio = numeroEconomiasRateio;
	}

	public String getQtdeEconomias(){

		return qtdeEconomias;
	}

	public void setQtdeEconomias(String qtdeEconomias){

		this.qtdeEconomias = qtdeEconomias;
	}

	public String getRateio(){

		return rateio;
	}

	public void setRateio(String rateio){

		this.rateio = rateio;
	}

	public String getTipoConsumo(){

		return tipoConsumo;
	}

	public void setTipoConsumo(String tipoConsumo){

		this.tipoConsumo = tipoConsumo;
	}

	public String getTotalConsumidoresRateioMacromedidor(){

		return totalConsumidoresRateioMacromedidor;
	}

	public void setTotalConsumidoresRateioMacromedidor(String totalConsumidoresRateioMacromedidor){

		this.totalConsumidoresRateioMacromedidor = totalConsumidoresRateioMacromedidor;
	}

	public String getLigacaoSituacaoAgua(){

		return ligacaoSituacaoAgua;
	}

	public void setLigacaoSituacaoAgua(String ligacaoSituacaoAgua){

		this.ligacaoSituacaoAgua = ligacaoSituacaoAgua;
	}

	public String getLigacaoSituacaoEsgoto(){

		return ligacaoSituacaoEsgoto;
	}

	public void setLigacaoSituacaoEsgoto(String ligacaoSituacaoEsgoto){

		this.ligacaoSituacaoEsgoto = ligacaoSituacaoEsgoto;
	}

	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	public String getValorComRateio(){

		return valorComRateio;
	}

	public void setValorComRateio(String valorComRateio){

		this.valorComRateio = valorComRateio;
	}

	public String getValorSemRateio(){

		return valorSemRateio;
	}

	public void setValorSemRateio(String valorSemRateio){

		this.valorSemRateio = valorSemRateio;
	}

	public String getTotalValorSemRateio(){

		return totalValorSemRateio;
	}

	public void setTotalValorSemRateio(String totalValorSemRateio){

		this.totalValorSemRateio = totalValorSemRateio;
	}

	public String getTotalValorComRateio(){

		return totalValorComRateio;
	}

	public void setTotalValorComRateio(String totalValorComRateio){

		this.totalValorComRateio = totalValorComRateio;
	}

	public String getDiferencaComRateioSemRateio(){

		return diferencaComRateioSemRateio;
	}

	public void setDiferencaComRateioSemRateio(String diferencaComRateioSemRateio){

		this.diferencaComRateioSemRateio = diferencaComRateioSemRateio;
	}

	public String getValorRateioProxFat(){

		return valorRateioProxFat;
	}

	public void setValorRateioProxFat(String valorRateioProxFat){

		this.valorRateioProxFat = valorRateioProxFat;
	}

	public String getIndicadorImovelCondominio(){

		return indicadorImovelCondominio;
	}

	public void setIndicadorImovelCondominio(String indicadorImovelCondominio){

		this.indicadorImovelCondominio = indicadorImovelCondominio;
	}

	public String getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(String codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public String getNumeroQuadra(){

		return numeroQuadra;
	}

	public void setNumeroQuadra(String numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	public String getNumeroLote(){

		return numeroLote;
	}

	public void setNumeroLote(String numeroLote){

		this.numeroLote = numeroLote;
	}

	public String getNumeroSubLote(){

		return numeroSubLote;
	}

	public void setNumeroSubLote(String numeroSubLote){

		this.numeroSubLote = numeroSubLote;
	}

	public String getDiferencaConsumoMacroMicros(){

		return diferencaConsumoMacroMicros;
	}

	public void setDiferencaConsumoMacroMicros(String diferencaConsumoMacroMicros){

		this.diferencaConsumoMacroMicros = diferencaConsumoMacroMicros;
	}

}
