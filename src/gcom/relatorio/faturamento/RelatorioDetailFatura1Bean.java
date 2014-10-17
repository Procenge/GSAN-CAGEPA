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

/**
 * [UC ]
 * 
 * @author Vivianne Sousa, Saulo Lima
 * @date 16/08/2007, 24/09/2008
 * @author eduardo henrique
 * @date 06/01/2009
 *       Alteração no bean para adição dos campos valorAgua, valorEsgoto, valorImpostoIR,
 *       valorImpostoCSLL, valorImpostoCOFINS, valorImpostoPIS
 */
public class RelatorioDetailFatura1Bean
				implements RelatorioBean {

	private String ligacao; // B01

	private String numeroHidrometro; // B02

	private String nome; // B03

	private String endereco; // B04

	private String cnpj; // B05

	private String leituraAnterior; // B06

	private String leituraAtual; // B07

	private String consumoMedido; // B08

	private String consumoFaturado; // B09

	private String dataLeitura; // B10

	private String qtdEconomias; // B11

	private String tipoServicoAgua; // B12

	private String tipoServicoEsgoto; // B13

	private String valor; // B14

	private String valorAgua;

	private String valorEsgoto;

	private String valorImpostoIR;

	private String valorImpostoCSLL;

	private String valorImpostoCOFINS;

	private String valorImpostoPIS;

	private String debitoCobradoMulta;

	private String debitoCobradoJuros;

	private String debitoCobradoOutrosServicos;

	private String valorCredito;

	public RelatorioDetailFatura1Bean(String ligacao, String numeroHidrometro, String nome, String endereco, String cnpj,
										String leituraAnterior, String leituraAtual, String consumoMedido, String consumoFaturado,
										String dataLeitura, String qtdEconomias, String tipoServicoAgua, String tipoServicoEsgoto,
										String valor, String valorAgua, String valorEsgoto, String valorImpostoCOFINS,
										String valorImpostoPIS, String valorImpostoCSLL, String valorImpostoIR, String debitoCobradoMulta,
										String debitoCobradoJuros, String debitoCobradoOutrosServicos, String valorCredito) {

		this.ligacao = ligacao;
		this.numeroHidrometro = numeroHidrometro;
		this.nome = nome;
		this.endereco = endereco;
		this.cnpj = cnpj;
		this.leituraAnterior = leituraAnterior;
		this.leituraAtual = leituraAtual;
		this.consumoMedido = consumoMedido;
		this.consumoFaturado = consumoFaturado;
		this.dataLeitura = dataLeitura;
		this.qtdEconomias = qtdEconomias;
		this.tipoServicoAgua = tipoServicoAgua;
		this.tipoServicoEsgoto = tipoServicoEsgoto;
		this.valor = valor;
		this.valorAgua = valorAgua;
		this.valorEsgoto = valorEsgoto;
		this.valorImpostoCOFINS = valorImpostoCOFINS;
		this.valorImpostoCSLL = valorImpostoCSLL;
		this.valorImpostoPIS = valorImpostoPIS;
		this.valorImpostoIR = valorImpostoIR;
		this.debitoCobradoMulta = debitoCobradoMulta;
		this.debitoCobradoJuros = debitoCobradoJuros;
		this.debitoCobradoOutrosServicos = debitoCobradoOutrosServicos;
		this.valorCredito = valorCredito;
	}

	public String getLigacao(){

		return ligacao;
	}

	public void setLigacao(String ligacao){

		this.ligacao = ligacao;
	}

	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	public String getNome(){

		return nome;
	}

	public void setNome(String nome){

		this.nome = nome;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getCnpj(){

		return cnpj;
	}

	public void setCnpj(String cnpj){

		this.cnpj = cnpj;
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

	public String getConsumoMedido(){

		return consumoMedido;
	}

	public void setConsumoMedido(String consumoMedido){

		this.consumoMedido = consumoMedido;
	}

	public String getConsumoFaturado(){

		return consumoFaturado;
	}

	public void setConsumoFaturado(String consumoFaturado){

		this.consumoFaturado = consumoFaturado;
	}

	public String getDataLeitura(){

		return dataLeitura;
	}

	public void setDataLeitura(String dataLeitura){

		this.dataLeitura = dataLeitura;
	}

	public String getQtdEconomias(){

		return qtdEconomias;
	}

	public void setQtdEconomias(String qtdEconomias){

		this.qtdEconomias = qtdEconomias;
	}

	public String getTipoServicoAgua(){

		return tipoServicoAgua;
	}

	public void setTipoServicoAgua(String tipoServicoAgua){

		this.tipoServicoAgua = tipoServicoAgua;
	}

	public String getTipoServicoEsgoto(){

		return tipoServicoEsgoto;
	}

	public void setTipoServicoEsgoto(String tipoServicoEsgoto){

		this.tipoServicoEsgoto = tipoServicoEsgoto;
	}

	public String getValor(){

		return valor;
	}

	public void setValor(String valor){

		this.valor = valor;
	}

	public String getValorAgua(){

		return valorAgua;
	}

	public void setValorAgua(String valorAgua){

		this.valorAgua = valorAgua;
	}

	public String getValorEsgoto(){

		return valorEsgoto;
	}

	public void setValorEsgoto(String valorEsgoto){

		this.valorEsgoto = valorEsgoto;
	}

	public String getValorImpostoIR(){

		return valorImpostoIR;
	}

	public void setValorImpostoIR(String valorImpostoIR){

		this.valorImpostoIR = valorImpostoIR;
	}

	public String getValorImpostoCSLL(){

		return valorImpostoCSLL;
	}

	public void setValorImpostoCSLL(String valorImpostoCSLL){

		this.valorImpostoCSLL = valorImpostoCSLL;
	}

	public String getValorImpostoCOFINS(){

		return valorImpostoCOFINS;
	}

	public void setValorImpostoCOFINS(String valorImpostoCOFINS){

		this.valorImpostoCOFINS = valorImpostoCOFINS;
	}

	public String getValorImpostoPIS(){

		return valorImpostoPIS;
	}

	public void setValorImpostoPIS(String valorImpostoPIS){

		this.valorImpostoPIS = valorImpostoPIS;
	}

	/**
	 * @return the debitoCobradoMulta
	 */
	public String getDebitoCobradoMulta(){

		return debitoCobradoMulta;
	}

	/**
	 * @param debitoCobradoMulta
	 *            the debitoCobradoMulta to set
	 */
	public void setDebitoCobradoMulta(String debitoCobradoMulta){

		this.debitoCobradoMulta = debitoCobradoMulta;
	}

	/**
	 * @return the debitoCobradoJuros
	 */
	public String getDebitoCobradoJuros(){

		return debitoCobradoJuros;
	}

	/**
	 * @param debitoCobradoJuros
	 *            the debitoCobradoJuros to set
	 */
	public void setDebitoCobradoJuros(String debitoCobradoJuros){

		this.debitoCobradoJuros = debitoCobradoJuros;
	}

	/**
	 * @return the debitoCobradoOutrosServicos
	 */
	public String getDebitoCobradoOutrosServicos(){

		return debitoCobradoOutrosServicos;
	}

	/**
	 * @param debitoCobradoOutrosServicos
	 *            the debitoCobradoOutrosServicos to set
	 */
	public void setDebitoCobradoOutrosServicos(String debitoCobradoOutrosServicos){

		this.debitoCobradoOutrosServicos = debitoCobradoOutrosServicos;
	}

	public void setValorCredito(String valorCredito){

		this.valorCredito = valorCredito;
	}

	public String getValorCredito(){

		return valorCredito;
	}
}
