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
 * GSAN - Procenge
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

package gcom.cadastro.imovel;

import gcom.cobranca.CobrancaDebitoSituacao;
import gcom.cobranca.DocumentoTipo;
import gcom.faturamento.GuiaPagamentoGeral;
import gcom.faturamento.conta.ContaGeral;
import gcom.util.ConstantesSistema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.CompareToBuilder;

public class ImovelCobrancaSituacaoItem
				implements Serializable, Comparable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private ImovelCobrancaSituacao imovelCobrancaSituacao;

	private DocumentoTipo documentoTipo;

	private ContaGeral contaGeral;

	private GuiaPagamentoGeral guiaPagamentoGeral;

	private Short numeroDaPrestacao;

	private CobrancaDebitoSituacao cobrancaDebitoSituacao;

	private Date dataSituacaoDebito;

	private Date dataDividaAtiva;

	private BigDecimal valorItemDividaAtiva;

	private BigDecimal valorAcrescimoDividaAtiva;

	private BigDecimal valorMultaDividaAtiva;

	private BigDecimal valorMoraDividaAtiva;

	private BigDecimal valorMonetariaDividaAtiva;

	private Date dataExecucaoFiscal;

	private BigDecimal valorItemExecucaoFiscal;

	private BigDecimal valorAcrescimoExecucaoFiscal;

	private BigDecimal valorMultaExecucaoFiscal;

	private BigDecimal valorMoraExecucaoFiscal;

	private BigDecimal valorMonetariaExecucaoFiscal;

	private Short indicadorAtualizado;

	private Date ultimaAlteracao;

	public ImovelCobrancaSituacaoItem() {

		this.indicadorAtualizado = ConstantesSistema.NAO;
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public ImovelCobrancaSituacao getImovelCobrancaSituacao(){

		return imovelCobrancaSituacao;
	}

	public void setImovelCobrancaSituacao(ImovelCobrancaSituacao imovelCobrancaSituacao){

		this.imovelCobrancaSituacao = imovelCobrancaSituacao;
	}

	public DocumentoTipo getDocumentoTipo(){

		return documentoTipo;
	}

	public void setDocumentoTipo(DocumentoTipo documentoTipo){

		this.documentoTipo = documentoTipo;
	}

	public ContaGeral getContaGeral(){

		return contaGeral;
	}

	public void setContaGeral(ContaGeral contaGeral){

		this.contaGeral = contaGeral;
	}

	public GuiaPagamentoGeral getGuiaPagamentoGeral(){

		return guiaPagamentoGeral;
	}

	public void setGuiaPagamentoGeral(GuiaPagamentoGeral guiaPagamentoGeral){

		this.guiaPagamentoGeral = guiaPagamentoGeral;
	}

	public Short getNumeroDaPrestacao(){

		return numeroDaPrestacao;
	}

	public void setNumeroDaPrestacao(Short numeroDaPrestacao){

		this.numeroDaPrestacao = numeroDaPrestacao;
	}

	public CobrancaDebitoSituacao getCobrancaDebitoSituacao(){

		return cobrancaDebitoSituacao;
	}

	public void setCobrancaDebitoSituacao(CobrancaDebitoSituacao cobrancaDebitoSituacao){

		this.cobrancaDebitoSituacao = cobrancaDebitoSituacao;
	}

	public Date getDataSituacaoDebito(){

		return dataSituacaoDebito;
	}

	public void setDataSituacaoDebito(Date dataSituacaoDebito){

		this.dataSituacaoDebito = dataSituacaoDebito;
	}

	public Date getDataDividaAtiva(){

		return dataDividaAtiva;
	}

	public void setDataDividaAtiva(Date dataDividaAtiva){

		this.dataDividaAtiva = dataDividaAtiva;
	}

	public BigDecimal getValorItemDividaAtiva(){

		return valorItemDividaAtiva;
	}

	public void setValorItemDividaAtiva(BigDecimal valorItemDividaAtiva){

		this.valorItemDividaAtiva = valorItemDividaAtiva;
	}

	public BigDecimal getValorAcrescimoDividaAtiva(){

		return valorAcrescimoDividaAtiva;
	}

	public void setValorAcrescimoDividaAtiva(BigDecimal valorAcrescimoDividaAtiva){

		this.valorAcrescimoDividaAtiva = valorAcrescimoDividaAtiva;
	}

	public BigDecimal getValorMultaDividaAtiva(){

		return valorMultaDividaAtiva;
	}

	public void setValorMultaDividaAtiva(BigDecimal valorMultaDividaAtiva){

		this.valorMultaDividaAtiva = valorMultaDividaAtiva;
	}

	public BigDecimal getValorMoraDividaAtiva(){

		return valorMoraDividaAtiva;
	}

	public void setValorMoraDividaAtiva(BigDecimal valorMoraDividaAtiva){

		this.valorMoraDividaAtiva = valorMoraDividaAtiva;
	}

	public BigDecimal getValorMonetariaDividaAtiva(){

		return valorMonetariaDividaAtiva;
	}

	public void setValorMonetariaDividaAtiva(BigDecimal valorMonetariaDividaAtiva){

		this.valorMonetariaDividaAtiva = valorMonetariaDividaAtiva;
	}

	public Date getDataExecucaoFiscal(){

		return dataExecucaoFiscal;
	}

	public void setDataExecucaoFiscal(Date dataExecucaoFiscal){

		this.dataExecucaoFiscal = dataExecucaoFiscal;
	}

	public BigDecimal getValorItemExecucaoFiscal(){

		return valorItemExecucaoFiscal;
	}

	public void setValorItemExecucaoFiscal(BigDecimal valorItemExecucaoFiscal){

		this.valorItemExecucaoFiscal = valorItemExecucaoFiscal;
	}

	public BigDecimal getValorAcrescimoExecucaoFiscal(){

		return valorAcrescimoExecucaoFiscal;
	}

	public void setValorAcrescimoExecucaoFiscal(BigDecimal valorAcrescimoExecucaoFiscal){

		this.valorAcrescimoExecucaoFiscal = valorAcrescimoExecucaoFiscal;
	}

	public BigDecimal getValorMultaExecucaoFiscal(){

		return valorMultaExecucaoFiscal;
	}

	public void setValorMultaExecucaoFiscal(BigDecimal valorMultaExecucaoFiscal){

		this.valorMultaExecucaoFiscal = valorMultaExecucaoFiscal;
	}

	public BigDecimal getValorMoraExecucaoFiscal(){

		return valorMoraExecucaoFiscal;
	}

	
	public void setValorMoraExecucaoFiscal(BigDecimal valorMoraExecucaoFiscal){

		this.valorMoraExecucaoFiscal = valorMoraExecucaoFiscal;
	}

	public BigDecimal getValorMonetariaExecucaoFiscal(){

		return valorMonetariaExecucaoFiscal;
	}

	public void setValorMonetariaExecucaoFiscal(BigDecimal valorMonetariaExecucaoFiscal){

		this.valorMonetariaExecucaoFiscal = valorMonetariaExecucaoFiscal;
	}

	public Short getIndicadorAtualizado(){

		return indicadorAtualizado;
	}

	public void setIndicadorAtualizado(Short indicadorAtualizado){

		this.indicadorAtualizado = indicadorAtualizado;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public int compareTo(Object other){

		if((this == other)){
			return 0;
		}
		if(!(other instanceof ImovelCobrancaSituacaoItem)){
			return -1;
		}
		ImovelCobrancaSituacaoItem castOther = (ImovelCobrancaSituacaoItem) other;

		return new CompareToBuilder().append(this.getId(), castOther.getId()).toComparison();
	}

}
