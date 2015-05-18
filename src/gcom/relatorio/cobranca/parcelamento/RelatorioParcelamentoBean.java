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
package gcom.relatorio.cobranca.parcelamento;

import gcom.relatorio.RelatorioBean;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * 
 * Bean do [UC0227] Gerar Relação de Débitos
 * 
 * @author Rafael Santos
 * @date 25/05/2006
 * 
 * @author Saulo Lima
 * @date 24/07/2009
 * Novos campos para exibir numeração das Guias
 */
public class RelatorioParcelamentoBean implements RelatorioBean {

	private String matriculaImovel;

	private String nomeCliente;

	private String endereco;

	private String cpfCnpj;

	private String telefone;
	
	private String fax;

	private String dataParcelamento;

	private String faturasEmAberto;

	private String servicosACobrar;

	private String atualizacaoMonetaria;

	private String taxaJurosMora;
	
	private String taxaJurosMoraExtenso;

	private String taxaMulta;
	
	private String taxaMultaExtenso;

	private String guiaPagamento;

	private String parcelamentoACobrar;

	private String totalDebitos;
	
	private String totalDebitosExtenso;

	private String descontoAcrescimo;

	private String descontosAntiguidade;

	private String descontoInatividade;

	private String creditosARealizar;

	private String totalDescontos;

	private String valorASerNegociado;

	private String valorEntrada;
	
	private String valorEntradaExtenso;

	private String numeroParcelas;
	
	private String numeroParcelasExtenso;

	private String valorParcela;

	private String valorASerParcelado;

	private String solicitacaoRestabelecimento;

	private String municipioData;

	private String idParcelamento;

	private String unidadeUsuario;

	private String idFuncionario;
	
	private String descricaoLocalidade;
	
	private String colecaoAnoMesReferencia;
	
	private String colecaoAnoMesReferenciaSobra;
	
	private String detalhamentoGuiasPrestacoes;
	
	private String detalhamentoGuiasPrestacoesSobra;
	
	private String nomeClienteParcelamento;
	
	private String cpfClienteParcelamento;

	private String pagina;

	private JRBeanCollectionDataSource arrayJRDetalhamento;

	private ArrayList arrayRelatorioParcelamentoDetalhamentoBean;
	
	private String codigoEmpresaFebraban;
	
	private String rgCliente;
	
	private String nomeUsuario;
	
	private String matriculaUsuario;
	
	private String municipio;
	
	private String imovelDiaVencimento;
	
	private String mesAnoInicioParcelamento;
	
	private String mesAnoFinalParcelamento;
	
	private String tipoCliente;
	
	private String valorJuros;
	
	private String valorJurosExtenso;
	
	private String valorDescontoSancoesRegulamentares;
    
    private String valorDescontoTarifaSocial;
    
	private String dataEntradaParcelamento;
	
	private String rg;

	/**
	 * Construtor de RelatorioGerarRelacaoDebitosBean
	 */
	public RelatorioParcelamentoBean(String matriculaImovel,
			String nomeCliente,
			String endereco, 
			String cpfCnpj,
			String telefone, 
			String fax, 
			String dataParcelamento, 
			String faturasEmAberto,
			String servicosACobrar, 
			String atualizacaoMonetaria,
			String taxaJurosMora, 
			String taxaJurosMoraExtenso, 
			String taxaMulta,
			String taxaMultaExtenso,
			String guiaPagamento,
			String parcelamentoACobrar, 
			String totalDebitos,
			String totalDebitosExtenso,
			String descontoAcrescimo, 
			String descontosAntiguidade,
			String descontoInatividade, 
			String creditosARealizar,
			String totalDescontos, 
			String valorASerNegociado,
			String valorEntrada, 
			String valorEntradaExtenso, 
			String numeroParcelas, 
			String numeroParcelasExtenso, 
			String valorParcela,
			String valorASerParcelado, 
			String solicitacaoRestabelecimento,
			String municipioData, 
			String idParcelamento, 
			String unidadeUsuario,
			String idFuncionario, 
			String descricaoLocalidade,
			String colecaoAnoMesReferencia,
			String colecaoAnoMesReferenciaSobra,
			String detalhamentoGuiasPrestacoes,
			String detalhamentoGuiasPrestacoesSobra,
			String nomeClienteParcelamento,
			String cpfClienteParcelamento, 
			String pagina,
			Collection colecaoRelatorioParcelamentoDetalhamentosBean,
			String codigoEmpresaFebraban,
			String rgCliente,
			String nomeUsuario,
			String matriculaUsuario,
			String municipio,
			String imovelDiaVencimento,
			String mesAnoInicioParcelamento,
			String mesAnoFinalParcelamento,
			String tipoCliente,
			String valorJuros,
			String valorJurosExtenso,
			String valorDescontoSancoesRegulamentares,
            String valorDescontoTarifaSocial,
            String dataEntradaParcelamento,
 String rg) {
		this.matriculaImovel = matriculaImovel;
		this.nomeCliente = nomeCliente;
		this.endereco = endereco;
		this.cpfCnpj = cpfCnpj;
		this.telefone = telefone;
		this.fax = fax;
		this.dataParcelamento = dataParcelamento;
		this.faturasEmAberto = faturasEmAberto;
		this.servicosACobrar = servicosACobrar;
		this.atualizacaoMonetaria = atualizacaoMonetaria;
		this.taxaJurosMora = taxaJurosMora;
		this.taxaJurosMoraExtenso = taxaJurosMoraExtenso;
		this.taxaMulta = taxaMulta;
		this.taxaMultaExtenso = taxaMultaExtenso;
		this.guiaPagamento = guiaPagamento;
		this.parcelamentoACobrar = parcelamentoACobrar;
		this.totalDebitos = totalDebitos;
		this.totalDebitosExtenso = totalDebitosExtenso;
		this.descontoAcrescimo = descontoAcrescimo;
		this.descontosAntiguidade = descontosAntiguidade;
		this.descontoInatividade = descontoInatividade;
		this.creditosARealizar = creditosARealizar;
		this.totalDescontos = totalDescontos;
		this.valorASerNegociado = valorASerNegociado;
		this.valorEntrada = valorEntrada;
		this.valorEntradaExtenso = valorEntradaExtenso;
		this.numeroParcelas = numeroParcelas;
		this.numeroParcelasExtenso = numeroParcelasExtenso;
		this.valorParcela = valorParcela;
		this.valorASerParcelado = valorASerParcelado;
		this.solicitacaoRestabelecimento = solicitacaoRestabelecimento;
		this.municipioData = municipioData;
		this.idParcelamento = idParcelamento;
		this.unidadeUsuario = unidadeUsuario;
		this.idFuncionario = idFuncionario;
		this.descricaoLocalidade = descricaoLocalidade;
		this.colecaoAnoMesReferencia = colecaoAnoMesReferencia;
		this.colecaoAnoMesReferenciaSobra = colecaoAnoMesReferenciaSobra;
		this.detalhamentoGuiasPrestacoes = detalhamentoGuiasPrestacoes;
		this.detalhamentoGuiasPrestacoesSobra = detalhamentoGuiasPrestacoesSobra;
		this.nomeClienteParcelamento = nomeClienteParcelamento;
		this.cpfClienteParcelamento = cpfClienteParcelamento;
		this.pagina = pagina;
		this.codigoEmpresaFebraban = codigoEmpresaFebraban;
		this.rgCliente = rgCliente;
		this.nomeUsuario = nomeUsuario;
		this.matriculaUsuario = matriculaUsuario;
		this.municipio = municipio;
		this.imovelDiaVencimento = imovelDiaVencimento;
		this.mesAnoInicioParcelamento = mesAnoInicioParcelamento;
		this.mesAnoFinalParcelamento = mesAnoFinalParcelamento;
		this.tipoCliente = tipoCliente;
		
		this.arrayRelatorioParcelamentoDetalhamentoBean = new ArrayList();
		this.arrayRelatorioParcelamentoDetalhamentoBean.addAll(colecaoRelatorioParcelamentoDetalhamentosBean);
		this.arrayJRDetalhamento = new JRBeanCollectionDataSource(
				this.arrayRelatorioParcelamentoDetalhamentoBean);
	
		this.valorJuros = valorJuros;
		this.valorJurosExtenso = valorJurosExtenso;
	
		this.valorDescontoSancoesRegulamentares = valorDescontoSancoesRegulamentares;
        this.valorDescontoTarifaSocial = valorDescontoTarifaSocial;
        this.dataEntradaParcelamento = dataEntradaParcelamento;
        
        this.rg =rg;
	}

	public JRBeanCollectionDataSource getArrayJRDetalhamento() {
		return arrayJRDetalhamento;
	}

	public void setArrayJRDetalhamento(
			JRBeanCollectionDataSource arrayJRDetalhamento) {
		this.arrayJRDetalhamento = arrayJRDetalhamento;
	}

	public ArrayList getArrayRelatorioParcelamentoDetalhamentoBean() {
		return arrayRelatorioParcelamentoDetalhamentoBean;
	}

	public void setArrayRelatorioEfetuarAnaliseParcelamentoDetalhamentoBean(
			ArrayList arrayRelatorioParcelamentoDetalhamentoBean) {
		this.arrayRelatorioParcelamentoDetalhamentoBean = arrayRelatorioParcelamentoDetalhamentoBean;
	}

	public String getAtualizacaoMonetaria() {
		return atualizacaoMonetaria;
	}

	public void setAtualizacaoMonetaria(String atualizacaoMonetaria) {
		this.atualizacaoMonetaria = atualizacaoMonetaria;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getCreditosARealizar() {
		return creditosARealizar;
	}

	public void setCreditosARealizar(String creditosARealizar) {
		this.creditosARealizar = creditosARealizar;
	}

	public String getDataParcelamento() {
		return dataParcelamento;
	}

	public void setDataParcelamento(String dataParcelamento) {
		this.dataParcelamento = dataParcelamento;
	}

	public String getDescontoAcrescimo() {
		return descontoAcrescimo;
	}

	public void setDescontoAcrescimo(String descontoAcrescimos) {
		this.descontoAcrescimo = descontoAcrescimos;
	}

	public String getDescontoInatividade() {
		return descontoInatividade;
	}

	public void setDescontoInatividade(String descontoInatividade) {
		this.descontoInatividade = descontoInatividade;
	}

	public String getDescontosAntiguidade() {
		return descontosAntiguidade;
	}

	public void setDescontosAntiguidade(String descontosAntiguidade) {
		this.descontosAntiguidade = descontosAntiguidade;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getFaturasEmAberto() {
		return faturasEmAberto;
	}

	public void setFaturasEmAberto(String faturasEmAberto) {
		this.faturasEmAberto = faturasEmAberto;
	}

	public String getGuiaPagamento() {
		return guiaPagamento;
	}

	public void setGuiaPagamento(String guiaPagamento) {
		this.guiaPagamento = guiaPagamento;
	}

	public String getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(String idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public String getIdParcelamento() {
		return idParcelamento;
	}

	public void setIdParcelamento(String idParcelamento) {
		this.idParcelamento = idParcelamento;
	}

	public String getTaxaJurosMora() {
		return taxaJurosMora;
	}

	public void setTaxaJurosMora(String taxaJurosMora) {
		this.taxaJurosMora = taxaJurosMora;
	}

	public String getMatriculaImovel() {
		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel) {
		this.matriculaImovel = matriculaImovel;
	}

	public String getTaxaMulta() {
		return taxaMulta;
	}

	public void setTaxaMulta(String multa) {
		this.taxaMulta = multa;
	}
	
	public String getTaxaMultaExtenso() {
		return taxaMultaExtenso;
	}

	public void setTaxaMultaExtenso(String multaExtenso) {
		this.taxaMultaExtenso = multaExtenso;
	}

	public String getMunicipioData() {
		return municipioData;
	}

	public void setMunicipioData(String municipioData) {
		this.municipioData = municipioData;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(String numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}

	public String getPagina() {
		return pagina;
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

	public String getParcelamentoACobrar() {
		return parcelamentoACobrar;
	}

	public void setParcelamentoACobrar(String parcelamentoACobrar) {
		this.parcelamentoACobrar = parcelamentoACobrar;
	}

	public String getServicosACobrar() {
		return servicosACobrar;
	}

	public void setServicosACobrar(String servicosACobrar) {
		this.servicosACobrar = servicosACobrar;
	}

	public String getSolicitacaoRestabelecimento() {
		return solicitacaoRestabelecimento;
	}

	public void setSolicitacaoRestabelecimento(
			String solicitacaoRestabelecimento) {
		this.solicitacaoRestabelecimento = solicitacaoRestabelecimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getTotalDebitos() {
		return totalDebitos;
	}

	public void setTotalDebitos(String totalDebitos) {
		this.totalDebitos = totalDebitos;
	}

	public String getTotalDescontos() {
		return totalDescontos;
	}

	public void setTotalDescontos(String totalDescontos) {
		this.totalDescontos = totalDescontos;
	}

	public String getUnidadeUsuario() {
		return unidadeUsuario;
	}

	public void setUnidadeUsuario(String unidadeUsuario) {
		this.unidadeUsuario = unidadeUsuario;
	}

	public String getValorASerNegociado() {
		return valorASerNegociado;
	}

	public void setValorASerNegociado(String valorASerNegociado) {
		this.valorASerNegociado = valorASerNegociado;
	}

	public String getValorASerParcelado() {
		return valorASerParcelado;
	}

	public void setValorASerParcelado(String valorASerParcelado) {
		this.valorASerParcelado = valorASerParcelado;
	}

	public String getValorEntrada() {
		return valorEntrada;
	}

	public void setValorEntrada(String valorEntrada) {
		this.valorEntrada = valorEntrada;
	}

	public String getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(String valorParcela) {
		this.valorParcela = valorParcela;
	}

	/**
	 * @return Retorna o campo cpfClienteParcelamento.
	 */
	public String getCpfClienteParcelamento() {
		return cpfClienteParcelamento;
	}

	/**
	 * @param cpfClienteParcelamento O cpfClienteParcelamento a ser setado.
	 */
	public void setCpfClienteParcelamento(String cpfClienteParcelamento) {
		this.cpfClienteParcelamento = cpfClienteParcelamento;
	}

	/**
	 * @return Retorna o campo nomeClienteParcelamento.
	 */
	public String getNomeClienteParcelamento() {
		return nomeClienteParcelamento;
	}

	/**
	 * @param nomeClienteParcelamento O nomeClienteParcelamento a ser setado.
	 */
	public void setNomeClienteParcelamento(String nomeClienteParcelamento) {
		this.nomeClienteParcelamento = nomeClienteParcelamento;
	}

	/**
	 * @param arrayRelatorioParcelamentoDetalhamentoBean O arrayRelatorioParcelamentoDetalhamentoBean a ser setado.
	 */
	public void setArrayRelatorioParcelamentoDetalhamentoBean(
			ArrayList arrayRelatorioParcelamentoDetalhamentoBean) {
		this.arrayRelatorioParcelamentoDetalhamentoBean = arrayRelatorioParcelamentoDetalhamentoBean;
	}

	public String getCodigoEmpresaFebraban() {
		return codigoEmpresaFebraban;
	}

	public void setCodigoEmpresaFebraban(String codigoEmpresaFebraban) {
		this.codigoEmpresaFebraban = codigoEmpresaFebraban;
	}

	public String getRgCliente() {
		return rgCliente;
	}

	public void setRgCliente(String rgCliente) {
		this.rgCliente = rgCliente;
	}

	public String getMatriculaUsuario() {
		return matriculaUsuario;
	}

	public void setMatriculaUsuario(String matriculaUsuario) {
		this.matriculaUsuario = matriculaUsuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getMesAnoFinalParcelamento() {
		return mesAnoFinalParcelamento;
	}

	public void setMesAnoFinalParcelamento(String mesAnoFinalParcelamento) {
		this.mesAnoFinalParcelamento = mesAnoFinalParcelamento;
	}

	public String getMesAnoInicioParcelamento() {
		return mesAnoInicioParcelamento;
	}

	public void setMesAnoInicioParcelamento(String mesAnoInicioParcelamento) {
		this.mesAnoInicioParcelamento = mesAnoInicioParcelamento;
	}

	public String getValorJuros() {
		return valorJuros;
	}

	public void setValorJuros(String valorJuros) {
		this.valorJuros = valorJuros;
	}

	public String getValorDescontoSancoesRegulamentares() {
		return valorDescontoSancoesRegulamentares;
	}

	public void setValorDescontoSancoesRegulamentares(
			String valorDescontoSancoesRegulamentares) {
		this.valorDescontoSancoesRegulamentares = valorDescontoSancoesRegulamentares;
	}

    public String getValorDescontoTarifaSocial() {
        return valorDescontoTarifaSocial;
    }

    public void setValorDescontoTarifaSocial(String valorDescontoTarifaSocial) {
        this.valorDescontoTarifaSocial = valorDescontoTarifaSocial;
    }

	public String getTotalDebitosExtenso() {
		return totalDebitosExtenso;
	}

	public void setTotalDebitosExtenso(String totalDebitosExtenso) {
		this.totalDebitosExtenso = totalDebitosExtenso;
	}

	public String getValorEntradaExtenso() {
		return valorEntradaExtenso;
	}

	public void setValorEntradaExtenso(String valorEntradaExtenso) {
		this.valorEntradaExtenso = valorEntradaExtenso;
	}

	public String getNumeroParcelasExtenso() {
		return numeroParcelasExtenso;
	}

	public void setNumeroParcelasExtenso(String numeroParcelasExtenso) {
		this.numeroParcelasExtenso = numeroParcelasExtenso;
	}

	public String getTaxaJurosMoraExtenso() {
		return taxaJurosMoraExtenso;
	}

	public void setTaxaJurosMoraExtenso(String taxaJurosMoraExtenso) {
		this.taxaJurosMoraExtenso = taxaJurosMoraExtenso;
	}

	public String getValorJurosExtenso() {
		return valorJurosExtenso;
	}

	public void setValorJurosExtenso(String valorJurosExtenso) {
		this.valorJurosExtenso = valorJurosExtenso;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getDescricaoLocalidade() {
		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade) {
		this.descricaoLocalidade = descricaoLocalidade;
	}

	public String getColecaoAnoMesReferencia() {
		return colecaoAnoMesReferencia;
	}

	public void setColecaoAnoMesReferencia(String colecaoAnoMesReferencia) {
		this.colecaoAnoMesReferencia = colecaoAnoMesReferencia;
	}

	public String getColecaoAnoMesReferenciaSobra() {
		return colecaoAnoMesReferenciaSobra;
	}

	public void setColecaoAnoMesReferenciaSobra(String colecaoAnoMesReferenciaSobra) {
		this.colecaoAnoMesReferenciaSobra = colecaoAnoMesReferenciaSobra;
	}

	public String getImovelDiaVencimento() {
		return imovelDiaVencimento;
	}

	public void setImovelDiaVencimento(String imovelDiaVencimento) {
		this.imovelDiaVencimento = imovelDiaVencimento;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getDetalhamentoGuiasPrestacoes() {
		return detalhamentoGuiasPrestacoes;
	}

	public void setDetalhamentoGuiasPrestacoes(String detalhamentoGuiasPrestacoes) {
		this.detalhamentoGuiasPrestacoes = detalhamentoGuiasPrestacoes;
	}

	public String getDetalhamentoGuiasPrestacoesSobra() {
		return detalhamentoGuiasPrestacoesSobra;
	}

	public void setDetalhamentoGuiasPrestacoesSobra(String detalhamentoGuiasPrestacoesSobra) {
		this.detalhamentoGuiasPrestacoesSobra = detalhamentoGuiasPrestacoesSobra;
	}

	public String getDataEntradaParcelamento() {
		return dataEntradaParcelamento;
	}

	public void setDataEntradaParcelamento(String dataEntradaParcelamento) {
		this.dataEntradaParcelamento = dataEntradaParcelamento;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getRg() {
		return rg;
	}

	
	
}