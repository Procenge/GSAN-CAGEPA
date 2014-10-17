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

import gcom.faturamento.bean.FaturaClienteResponsavelHelper;
import gcom.faturamento.bean.FaturaItemClienteResponsavelHelper;
import gcom.relatorio.RelatorioBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * [UC ]
 * 
 * @author Vivianne Sousa
 * @date 16/08/2007
 * @author eduardo henrique
 * @date 06/01/2009
 *       Alteração no bean para adição dos campos valorAgua, valorEsgoto, valorImpostoIR,
 *       valorImpostoCSLL, valorImpostoCOFINS, valorImpostoPIS
 */
public class RelatorioFaturaClienteResponsavelBean
				implements RelatorioBean {

	private JRBeanCollectionDataSource arrayJRDetailFatura1;

	private ArrayList arrayRelatorioDetailFaturaBean;

	private String nomeCliente; // 01

	private String endereco; // 02

	private String cnpj; // 03

	private String dataEmissao; // 04

	private String numeroFatura; // 05

	private String numeroOrgao; // 06

	private String mesAno; // 07

	private String vencimento; // 08

	// páginas //09
	private String valorExtenso; // 10

	private String qtdItens; // 11

	private String valorTotalAPagar; // 12

	private String representacaoNumericaCodBarraFormatada; // 13

	private String representacaoNumericaCodBarraSemDigito; // 14

	private String indicadorCodigoBarras; // 15

	private Collection colecaoFaturaItemClienteResponsavelHelper; // 16

	private String valorImpostos; // 17

	private String valorBruto; // 18

	private String codigoCliente;

	private String nomeEmpresa;

	private String enderecoCompletoEmpresa;

	private String foneEmpresa;

	private String cnpjEmpresa;

	private String homePageEmpresa;

	private String tituloRelatorioEmpresa;

	public RelatorioFaturaClienteResponsavelBean(FaturaClienteResponsavelHelper faturaClienteResponsavelHelper) {

		this.nomeCliente = faturaClienteResponsavelHelper.getNomeCliente();
		this.endereco = faturaClienteResponsavelHelper.getEndereco();
		this.cnpj = faturaClienteResponsavelHelper.getCnpj();
		this.dataEmissao = faturaClienteResponsavelHelper.getDataEmissao();
		this.numeroFatura = faturaClienteResponsavelHelper.getNumeroFatura();
		this.numeroOrgao = faturaClienteResponsavelHelper.getNumeroOrgao();
		this.mesAno = faturaClienteResponsavelHelper.getMesAno();
		this.vencimento = faturaClienteResponsavelHelper.getVencimento();
		this.valorExtenso = faturaClienteResponsavelHelper.getValorExtenso();
		this.qtdItens = faturaClienteResponsavelHelper.getQtdItens();
		this.valorTotalAPagar = faturaClienteResponsavelHelper.getValorTotalAPagar();
		this.representacaoNumericaCodBarraFormatada = faturaClienteResponsavelHelper.getRepresentacaoNumericaCodBarraFormatada();
		this.representacaoNumericaCodBarraSemDigito = faturaClienteResponsavelHelper.getRepresentacaoNumericaCodBarraSemDigito();
		this.indicadorCodigoBarras = faturaClienteResponsavelHelper.getIndicadorCodigoBarras();
		this.codigoCliente = faturaClienteResponsavelHelper.getCodigoCliente();
		this.valorImpostos = faturaClienteResponsavelHelper.getValorImpostos();
		this.valorBruto = faturaClienteResponsavelHelper.getValorBruto();
		this.nomeEmpresa = faturaClienteResponsavelHelper.getNomeEmpresa();
		this.enderecoCompletoEmpresa = faturaClienteResponsavelHelper.getEnderecoCompletoEmpresa();
		this.foneEmpresa = faturaClienteResponsavelHelper.getFoneEmpresa();
		this.cnpjEmpresa = faturaClienteResponsavelHelper.getCnpjEmpresa();
		this.homePageEmpresa = faturaClienteResponsavelHelper.getHomePageEmpresa();
		this.tituloRelatorioEmpresa = faturaClienteResponsavelHelper.getTituloRelatorioEmpresa();

		this.arrayRelatorioDetailFaturaBean = new ArrayList();
		this.arrayRelatorioDetailFaturaBean.addAll(this.gerarDetail(faturaClienteResponsavelHelper
						.getColecaoFaturaItemClienteResponsavelHelper()));
		this.arrayJRDetailFatura1 = new JRBeanCollectionDataSource(this.arrayRelatorioDetailFaturaBean);

	}

	private Collection gerarDetail(Collection colecaoFaturaItem){

		Collection colecaoDetail = new ArrayList();

		if(colecaoFaturaItem != null && !colecaoFaturaItem.isEmpty()){
			Iterator iterator = colecaoFaturaItem.iterator();
			while(iterator.hasNext()){

				FaturaItemClienteResponsavelHelper faturaItemClienteResponsavelHelper = (FaturaItemClienteResponsavelHelper) iterator
								.next();

				Object relatorio = null;

				relatorio = new RelatorioDetailFatura1Bean(faturaItemClienteResponsavelHelper.getLigacao(),
								faturaItemClienteResponsavelHelper.getNumeroHidrometro(), faturaItemClienteResponsavelHelper.getNome(),
								faturaItemClienteResponsavelHelper.getEndereco(), faturaItemClienteResponsavelHelper.getCnpj(),
								faturaItemClienteResponsavelHelper.getLeituraAnterior(), faturaItemClienteResponsavelHelper
												.getLeituraAtual(), faturaItemClienteResponsavelHelper.getConsumoMedido(),
								faturaItemClienteResponsavelHelper.getConsumoFaturado(), faturaItemClienteResponsavelHelper
												.getDataLeitura(), faturaItemClienteResponsavelHelper.getQtdEconomias(),
								faturaItemClienteResponsavelHelper.getTipoServicoAgua(), faturaItemClienteResponsavelHelper
												.getTipoServicoEsgoto(), faturaItemClienteResponsavelHelper.getValor(),
								faturaItemClienteResponsavelHelper.getValorAgua(), faturaItemClienteResponsavelHelper.getValorEsgoto(),
								faturaItemClienteResponsavelHelper.getValorImpostoCOFINS(), faturaItemClienteResponsavelHelper
												.getValorImpostoPIS(), faturaItemClienteResponsavelHelper.getValorImpostoCSLL(),
								faturaItemClienteResponsavelHelper.getValorImpostoIR(), faturaItemClienteResponsavelHelper
												.getDebitoCobradoMulta(), faturaItemClienteResponsavelHelper.getDebitoCobradoJuros(),
								faturaItemClienteResponsavelHelper.getDebitoCobradoOutrosServicos(), faturaItemClienteResponsavelHelper
												.getValorCredito());

				colecaoDetail.add(relatorio);
			}
		}
		return colecaoDetail;
	}

	public JRBeanCollectionDataSource getArrayJRDetailFatura1(){

		return arrayJRDetailFatura1;
	}

	public void setArrayJRDetailFatura1(JRBeanCollectionDataSource arrayJRDetailFatura1){

		this.arrayJRDetailFatura1 = arrayJRDetailFatura1;
	}

	public ArrayList getArrayRelatorioDetailFaturaBean(){

		return arrayRelatorioDetailFaturaBean;
	}

	public void setArrayRelatorioDetailFaturaBean(ArrayList arrayRelatorioDetailFaturaBean){

		this.arrayRelatorioDetailFaturaBean = arrayRelatorioDetailFaturaBean;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
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

	public String getDataEmissao(){

		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao){

		this.dataEmissao = dataEmissao;
	}

	public String getNumeroFatura(){

		return numeroFatura;
	}

	public void setNumeroFatura(String numeroFatura){

		this.numeroFatura = numeroFatura;
	}

	public String getNumeroOrgao(){

		return numeroOrgao;
	}

	public void setNumeroOrgao(String numeroOrgao){

		this.numeroOrgao = numeroOrgao;
	}

	public String getMesAno(){

		return mesAno;
	}

	public void setMesAno(String mesAno){

		this.mesAno = mesAno;
	}

	public String getVencimento(){

		return vencimento;
	}

	public void setVencimento(String vencimento){

		this.vencimento = vencimento;
	}

	public String getValorExtenso(){

		return valorExtenso;
	}

	public void setValorExtenso(String valorExtenso){

		this.valorExtenso = valorExtenso;
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
