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

package gcom.faturamento.bean;

import java.io.Serializable;
import java.util.Collection;

/** @author Hibernate CodeGenerator */
/**
 */
public class FaturaClienteResponsavelHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeCliente; // 01

	private String endereco; // 02

	private String cnpj; // 03

	private String dataEmissao; // 04

	private String numeroFatura; // 05

	private String numeroOrgao; // 06

	private String mesAno; // 07

	private String vencimento; // 08

	// p�ginas //09
	private String valorExtenso; // 10

	private String qtdItens; // 11

	private String valorTotalAPagar; // 12

	private String representacaoNumericaCodBarraFormatada; // 13

	private String representacaoNumericaCodBarraSemDigito; // 14

	private String indicadorCodigoBarras; // 15

	private Collection colecaoFaturaItemClienteResponsavelHelper; // 16

	private String valorImpostos; // 17

	private String valorBruto; // 18

	private String nomeEmpresa;

	private String enderecoCompletoEmpresa;

	private String foneEmpresa;

	private String cnpjEmpresa;

	private String homePageEmpresa;

	private String tituloRelatorioEmpresa;

	// Inicio - Usado somente no relat�rio RelatorioRelacaoAnaliticaFaturas
	private String codigoCliente;

	// Fim - Usado somente no relat�rio RelatorioRelacaoAnaliticaFaturas

	public FaturaClienteResponsavelHelper() {

	}

	public FaturaClienteResponsavelHelper(String nomeCliente, String endereco, String cnpj, String dataEmissao, String numeroFatura,
											String numeroOrgao, String mesAno, String vencimento, String valorExtenso, String qtdItens,
											String valorTotalAPagar, String representacaoNumericaCodBarraFormatada,
											String representacaoNumericaCodBarraSemDigito, String indicadorCodigoBarras,
											String valorImpostos, String valorBruto, Collection colecaoFaturaItemClienteResponsavelHelper) {

		this.nomeCliente = nomeCliente;
		this.endereco = endereco;
		this.cnpj = cnpj;
		this.dataEmissao = dataEmissao;
		this.numeroFatura = numeroFatura;
		this.numeroOrgao = numeroOrgao;
		this.mesAno = mesAno;
		this.vencimento = vencimento;
		this.valorExtenso = valorExtenso;
		this.qtdItens = qtdItens;
		this.valorTotalAPagar = valorTotalAPagar;
		this.representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarraFormatada;
		this.representacaoNumericaCodBarraSemDigito = representacaoNumericaCodBarraSemDigito;
		this.indicadorCodigoBarras = indicadorCodigoBarras;
		this.valorImpostos = valorImpostos;
		this.valorBruto = valorBruto;
		this.colecaoFaturaItemClienteResponsavelHelper = colecaoFaturaItemClienteResponsavelHelper;

	}

	public String getDataEmissao(){

		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao){

		this.dataEmissao = dataEmissao;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getMesAno(){

		return mesAno;
	}

	public void setMesAno(String mesAno){

		this.mesAno = mesAno;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getNumeroFatura(){

		return numeroFatura;
	}

	public void setNumeroFatura(String numeroFatura){

		this.numeroFatura = numeroFatura;
	}

	public String getVencimento(){

		return vencimento;
	}

	public void setVencimento(String vencimento){

		this.vencimento = vencimento;
	}

	public String getQtdItens(){

		return qtdItens;
	}

	public void setQtdItens(String qtdItens){

		this.qtdItens = qtdItens;
	}

	public String getValorTotalAPagar(){

		return valorTotalAPagar;
	}

	public void setValorTotalAPagar(String valorTotalAPagar){

		this.valorTotalAPagar = valorTotalAPagar;
	}

	public Collection getColecaoFaturaItemClienteResponsavelHelper(){

		return colecaoFaturaItemClienteResponsavelHelper;
	}

	public void setColecaoFaturaItemClienteResponsavelHelper(Collection colecaoFaturaItemClienteResponsavelHelper){

		this.colecaoFaturaItemClienteResponsavelHelper = colecaoFaturaItemClienteResponsavelHelper;
	}

	public String getCodigoCliente(){

		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente){

		this.codigoCliente = codigoCliente;
	}

	public String getCnpj(){

		return cnpj;
	}

	public void setCnpj(String cnpj){

		this.cnpj = cnpj;
	}

	public String getNumeroOrgao(){

		return numeroOrgao;
	}

	public void setNumeroOrgao(String numeroOrgao){

		this.numeroOrgao = numeroOrgao;
	}

	public String getValorExtenso(){

		return valorExtenso;
	}

	public void setValorExtenso(String valorExtenso){

		this.valorExtenso = valorExtenso;
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

	public String getIndicadorCodigoBarras(){

		return indicadorCodigoBarras;
	}

	public void setIndicadorCodigoBarras(String indicadorCodigoBarras){

		this.indicadorCodigoBarras = indicadorCodigoBarras;
	}

	public String getValorImpostos(){

		return valorImpostos;
	}

	public void setValorImpostos(String valorImpostos){

		this.valorImpostos = valorImpostos;
	}

	public String getValorBruto(){

		return valorBruto;
	}

	public void setValorBruto(String valorBruto){

		this.valorBruto = valorBruto;
	}

	public String getNomeEmpresa(){

		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa){

		this.nomeEmpresa = nomeEmpresa;
	}

	public String getEnderecoCompletoEmpresa(){

		return enderecoCompletoEmpresa;
	}

	public void setEnderecoCompletoEmpresa(String enderecoCompletoEmpresa){

		this.enderecoCompletoEmpresa = enderecoCompletoEmpresa;
	}

	public String getFoneEmpresa(){

		return foneEmpresa;
	}

	public void setFoneEmpresa(String foneEmpresa){

		this.foneEmpresa = foneEmpresa;
	}

	public String getCnpjEmpresa(){

		return cnpjEmpresa;
	}

	public void setCnpjEmpresa(String cnpjEmpresa){

		this.cnpjEmpresa = cnpjEmpresa;
	}

	public String getHomePageEmpresa(){

		return homePageEmpresa;
	}

	public void setHomePageEmpresa(String homePageEmpresa){

		this.homePageEmpresa = homePageEmpresa;
	}

	public String getTituloRelatorioEmpresa(){

		return tituloRelatorioEmpresa;
	}

	public void setTituloRelatorioEmpresa(String tituloRelatorioEmpresa){

		this.tituloRelatorioEmpresa = tituloRelatorioEmpresa;
	}

}
