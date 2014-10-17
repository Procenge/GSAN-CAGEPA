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

package gcom.relatorio.faturamento.conta;

import gcom.relatorio.RelatorioBean;

public class RelatorioContasBloqueadasAnaliseBean
				implements RelatorioBean {

	private String idLocalidade;

	private String codigoSetorComercial;

	private String matriculaImovel;

	private String idGrupoFaturamento;

	private String dataVencimento;

	private String descricaoLocalidade;

	private String dataLeitura;

	private String numeroQuadra;

	private String numeroLote;

	private String consumoMesUm;

	private String consumoMesDois;

	private String consumoMesTres;

	private String consumoMesQuatro;

	private String consumoMesCinco;

	private String consumoMesSeis;

	private String leituraMesUm;

	private String leituraMesDois;

	private String leituraMesTres;

	private String leituraMesQuatro;

	private String leituraMesCinco;

	private String leituraMesSeis;

	private String descricaoCategoria;

	private String quantidadeEconomias;

	private String idSituacaoLigacaoAgua;

	private String idSituacaoLigacaoEsgoto;

	private String consumoFaturadoAgua;

	private String variacao;

	private String consumoMinimo;

	private String percentualEsgoto;

	private String leituraAnterior;

	private String leituraAtual;

	private String idLeituraAnormalidade;

	private String cobrouMedia;

	private String diasConsumo;

	private String consumoMedio;

	public String getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(String codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getIdGrupoFaturamento(){

		return idGrupoFaturamento;
	}

	public void setIdGrupoFaturamento(String idGrupoFaturamento){

		this.idGrupoFaturamento = idGrupoFaturamento;
	}

	public String getDataVencimento(){

		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	public String getDataLeitura(){

		return dataLeitura;
	}

	public void setDataLeitura(String dataLeitura){

		this.dataLeitura = dataLeitura;
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

	public String getConsumoMesUm(){

		return consumoMesUm;
	}

	public void setConsumoMesUm(String consumoMesUm){

		this.consumoMesUm = consumoMesUm;
	}

	public String getConsumoMesDois(){

		return consumoMesDois;
	}

	public void setConsumoMesDois(String consumoMesDois){

		this.consumoMesDois = consumoMesDois;
	}

	public String getConsumoMesTres(){

		return consumoMesTres;
	}

	public void setConsumoMesTres(String consumoMesTres){

		this.consumoMesTres = consumoMesTres;
	}

	public String getConsumoMesQuatro(){

		return consumoMesQuatro;
	}

	public void setConsumoMesQuatro(String consumoMesQuatro){

		this.consumoMesQuatro = consumoMesQuatro;
	}

	public String getConsumoMesCinco(){

		return consumoMesCinco;
	}

	public void setConsumoMesCinco(String consumoMesCinco){

		this.consumoMesCinco = consumoMesCinco;
	}

	public String getConsumoMesSeis(){

		return consumoMesSeis;
	}

	public void setConsumoMesSeis(String consumoMesSeis){

		this.consumoMesSeis = consumoMesSeis;
	}

	public String getLeituraMesUm(){

		return leituraMesUm;
	}

	public void setLeituraMesUm(String leituraMesUm){

		this.leituraMesUm = leituraMesUm;
	}

	public String getLeituraMesDois(){

		return leituraMesDois;
	}

	public void setLeituraMesDois(String leituraMesDois){

		this.leituraMesDois = leituraMesDois;
	}

	public String getLeituraMesTres(){

		return leituraMesTres;
	}

	public void setLeituraMesTres(String leituraMesTres){

		this.leituraMesTres = leituraMesTres;
	}

	public String getLeituraMesQuatro(){

		return leituraMesQuatro;
	}

	public void setLeituraMesQuatro(String leituraMesQuatro){

		this.leituraMesQuatro = leituraMesQuatro;
	}

	public String getLeituraMesCinco(){

		return leituraMesCinco;
	}

	public void setLeituraMesCinco(String leituraMesCinco){

		this.leituraMesCinco = leituraMesCinco;
	}

	public String getLeituraMesSeis(){

		return leituraMesSeis;
	}

	public void setLeituraMesSeis(String leituraMesSeis){

		this.leituraMesSeis = leituraMesSeis;
	}

	public String getDescricaoCategoria(){

		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria){

		this.descricaoCategoria = descricaoCategoria;
	}

	public String getQuantidadeEconomias(){

		return quantidadeEconomias;
	}

	public void setQuantidadeEconomias(String quantidadeEconomias){

		this.quantidadeEconomias = quantidadeEconomias;
	}

	public String getIdSituacaoLigacaoAgua(){

		return idSituacaoLigacaoAgua;
	}

	public void setIdSituacaoLigacaoAgua(String idSituacaoLigacaoAgua){

		this.idSituacaoLigacaoAgua = idSituacaoLigacaoAgua;
	}

	public String getIdSituacaoLigacaoEsgoto(){

		return idSituacaoLigacaoEsgoto;
	}

	public void setIdSituacaoLigacaoEsgoto(String idSituacaoLigacaoEsgoto){

		this.idSituacaoLigacaoEsgoto = idSituacaoLigacaoEsgoto;
	}

	public String getConsumoFaturadoAgua(){

		return consumoFaturadoAgua;
	}

	public void setConsumoFaturadoAgua(String consumoFaturadoAgua){

		this.consumoFaturadoAgua = consumoFaturadoAgua;
	}

	public String getVariacao(){

		return variacao;
	}

	public void setVariacao(String variacao){

		this.variacao = variacao;
	}

	public String getConsumoMinimo(){

		return consumoMinimo;
	}

	public void setConsumoMinimo(String consumoMinimo){

		this.consumoMinimo = consumoMinimo;
	}

	public String getPercentualEsgoto(){

		return percentualEsgoto;
	}

	public void setPercentualEsgoto(String percentualEsgoto){

		this.percentualEsgoto = percentualEsgoto;
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

	public String getIdLeituraAnormalidade(){

		return idLeituraAnormalidade;
	}

	public void setIdLeituraAnormalidade(String idLeituraAnormalidade){

		this.idLeituraAnormalidade = idLeituraAnormalidade;
	}

	public String getCobrouMedia(){

		return cobrouMedia;
	}

	public void setCobrouMedia(String cobrouMedia){

		this.cobrouMedia = cobrouMedia;
	}

	public String getDiasConsumo(){

		return diasConsumo;
	}

	public void setDiasConsumo(String diasConsumo){

		this.diasConsumo = diasConsumo;
	}

	public String getConsumoMedio(){

		return consumoMedio;
	}

	public void setConsumoMedio(String consumoMedio){

		this.consumoMedio = consumoMedio;
	}

}
