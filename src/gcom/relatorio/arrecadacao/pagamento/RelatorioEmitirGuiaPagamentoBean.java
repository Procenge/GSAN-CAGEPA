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

package gcom.relatorio.arrecadacao.pagamento;

import gcom.relatorio.RelatorioBean;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * [UC0379] Emitir Guia de Pagamento
 * 
 * @author Vivianne Sousa
 * @date 22/09/2006
 * @author eduardo henrique
 * @date 21/08/2008
 *       Alteração para Impressão de Guia de Pagamento por Prestação
 */
public class RelatorioEmitirGuiaPagamentoBean
				implements RelatorioBean {

	private JRBeanCollectionDataSource arrayJRDetail;

	private ArrayList arrayRelatorioEmitirGuiaPagamentoDetailBean;

	private String matricula;

	private String nomeCliente;

	private String dataVencimento;

	private String inscricao;

	private String enderecoImovel;

	private String enderecoClienteResponsavel;

	private String representacaoNumericaCodBarraFormatada;

	private String representacaoNumericaCodBarraSemDigito;

	private String dataValidade;

	private String valorDebito;

	private String idGuiaPagamento;

	private String descricaoTipoDocumento;

	private String numeroGuiaPrestacaoFormatado;

	private String indicadorPrestacaoNoHistorico;

	private String indicadorPrestacaoDebitoAutomatico;

	private String indicadorExibirAcrescimos;

	private String dataEmissaoGuia;

	private String indicadorEmissaoObservacaoRA;

	private String descricaoObservacao;

	private String numeroContratoParcelOrgaoPublico;

	public RelatorioEmitirGuiaPagamentoBean(Collection colecaoDetail, String matricula, String nomeCliente, String dataVencimento,
											String inscricao, String enderecoImovel, String enderecoClienteResponsavel,
											String representacaoNumericaCodBarraFormatada, String representacaoNumericaCodBarraSemDigito,
											String dataValidade, String valorDebito, String idGuiaPagamento, String descricaoTipoDocumento,
											String numeroGuiaPrestacaoFormatado, String indicadorEmissaoObservacaoRA,
											String descricaoObservacao, String numeroContratoParcelOrgaoPublico) {

		this.arrayRelatorioEmitirGuiaPagamentoDetailBean = new ArrayList();
		this.arrayRelatorioEmitirGuiaPagamentoDetailBean.addAll(colecaoDetail);
		this.arrayJRDetail = new JRBeanCollectionDataSource(this.arrayRelatorioEmitirGuiaPagamentoDetailBean);

		this.matricula = matricula;
		this.nomeCliente = nomeCliente;
		this.dataVencimento = dataVencimento;
		this.inscricao = inscricao;
		this.enderecoImovel = enderecoImovel;
		this.enderecoClienteResponsavel = enderecoClienteResponsavel;
		this.representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarraFormatada;
		this.representacaoNumericaCodBarraSemDigito = representacaoNumericaCodBarraSemDigito;
		this.dataValidade = dataValidade;
		this.valorDebito = valorDebito;
		this.idGuiaPagamento = idGuiaPagamento;
		this.descricaoTipoDocumento = descricaoTipoDocumento;
		this.numeroGuiaPrestacaoFormatado = numeroGuiaPrestacaoFormatado;
		this.indicadorEmissaoObservacaoRA = indicadorEmissaoObservacaoRA;
		this.descricaoObservacao = descricaoObservacao;
		this.numeroContratoParcelOrgaoPublico = numeroContratoParcelOrgaoPublico;
	}

	public JRBeanCollectionDataSource getArrayJRDetail(){

		return arrayJRDetail;
	}

	public void setArrayJRDetail(JRBeanCollectionDataSource arrayJRDetail){

		this.arrayJRDetail = arrayJRDetail;
	}

	public ArrayList getArrayRelatorioEmitirGuiaPagamentoDetailBean(){

		return arrayRelatorioEmitirGuiaPagamentoDetailBean;
	}

	public void setArrayRelatorioEmitirGuiaPagamentoDetailBean(ArrayList arrayRelatorioEmitirGuiaPagamentoDetailBean){

		this.arrayRelatorioEmitirGuiaPagamentoDetailBean = arrayRelatorioEmitirGuiaPagamentoDetailBean;
	}

	public String getDataValidade(){

		return dataValidade;
	}

	public void setDataValidade(String dataValidade){

		this.dataValidade = dataValidade;
	}

	public String getDataVencimento(){

		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	public String getEnderecoClienteResponsavel(){

		return enderecoClienteResponsavel;
	}

	public void setEnderecoClienteResponsavel(String enderecoClienteResponsavel){

		this.enderecoClienteResponsavel = enderecoClienteResponsavel;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
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

	public String getValorDebito(){

		return valorDebito;
	}

	public void setValorDebito(String valorDebito){

		this.valorDebito = valorDebito;
	}

	public String getIdGuiaPagamento(){

		return idGuiaPagamento;
	}

	public void setIdGuiaPagamento(String idGuiaPagamento){

		this.idGuiaPagamento = idGuiaPagamento;
	}

	/**
	 * @return the descricaoTipoDocumento
	 */
	public String getDescricaoTipoDocumento(){

		return descricaoTipoDocumento;
	}

	/**
	 * @param descricaoTipoDocumento
	 *            the descricaoTipoDocumento to set
	 */
	public void setDescricaoTipoDocumento(String descricaoTipoDocumento){

		this.descricaoTipoDocumento = descricaoTipoDocumento;
	}

	/**
	 * @return the numeroGuiaPrestacaoFormatado
	 */
	public String getNumeroGuiaPrestacaoFormatado(){

		return numeroGuiaPrestacaoFormatado;
	}

	/**
	 * @param numeroGuiaPrestacaoFormatado
	 *            the numeroGuiaPrestacaoFormatado to set
	 */
	public void setNumeroGuiaPrestacaoFormatado(String numeroGuiaPrestacaoFormatado){

		this.numeroGuiaPrestacaoFormatado = numeroGuiaPrestacaoFormatado;
	}

	public String getIndicadorPrestacaoNoHistorico(){

		return indicadorPrestacaoNoHistorico;
	}

	public void setIndicadorPrestacaoNoHistorico(String indicadorPrestacaoNoHistorico){

		this.indicadorPrestacaoNoHistorico = indicadorPrestacaoNoHistorico;
	}

	public String getIndicadorPrestacaoDebitoAutomatico(){

		return indicadorPrestacaoDebitoAutomatico;
	}

	public void setIndicadorPrestacaoDebitoAutomatico(String indicadorPrestacaoDebitoAutomatico){

		this.indicadorPrestacaoDebitoAutomatico = indicadorPrestacaoDebitoAutomatico;
	}

	public String getIndicadorExibirAcrescimos(){

		return indicadorExibirAcrescimos;
	}

	public void setIndicadorExibirAcrescimos(String indicadorExibirAcrescimos){

		this.indicadorExibirAcrescimos = indicadorExibirAcrescimos;
	}

	public String getDataEmissaoGuia(){

		return dataEmissaoGuia;
	}

	public void setDataEmissaoGuia(String dataEmissaoGuia){

		this.dataEmissaoGuia = dataEmissaoGuia;
	}

	public String getIndicadorEmissaoObservacaoRA(){

		return indicadorEmissaoObservacaoRA;
	}

	public void setIndicadorEmissaoObservacaoRA(String indicadorEmissaoObservacaoRA){

		this.indicadorEmissaoObservacaoRA = indicadorEmissaoObservacaoRA;
	}

	public String getDescricaoObservacao(){

		return descricaoObservacao;
	}

	public void setDescricaoObservacao(String descricaoObservacao){

		this.descricaoObservacao = descricaoObservacao;
	}

	public String getNumeroContratoParcelOrgaoPublico(){

		return numeroContratoParcelOrgaoPublico;
	}

	public void setNumeroContratoParcelOrgaoPublico(String numeroContratoParcelOrgaoPublico){

		this.numeroContratoParcelOrgaoPublico = numeroContratoParcelOrgaoPublico;
	}

}
