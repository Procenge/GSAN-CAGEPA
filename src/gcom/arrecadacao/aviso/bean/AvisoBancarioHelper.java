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

package gcom.arrecadacao.aviso.bean;

import gcom.arrecadacao.Devolucao;
import gcom.arrecadacao.aviso.AvisoBancario;
import gcom.arrecadacao.pagamento.Pagamento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 * Classe que ir� auxiliar no formato do retorno da pesquisa dos avisos banc�rios de um
 * determinado movimento do arrecadador
 * 
 * @author Raphael Rossiter
 * @date 08/03/2006
 */
public class AvisoBancarioHelper
				implements Serializable {

	private Integer idMovimentoArrecadador;

	private Integer idAvisoBancario;

	private Date dataLancamento;

	private Short numeroSequencial;

	private short indicadorCreditoDebito;

	private String descricaoIndicadorCreditoDebito;

	private Date dataRealizada;

	private BigDecimal valorAcertos;

	private BigDecimal valorCalculado;

	private BigDecimal valorInformado;

	private BigDecimal valorDiferenca;

	private String situacao;

	private AvisoBancario avisoBancario;

	private String tipoAviso;

	private Date dataLancamentoInicial;

	private Date dataLancamentoFinal;

	private int anoMesReferenciaArrecadacaoInicial;

	private int anoMesReferenciaArrecadacaoFinal;

	private BigDecimal valorPrevistoInicial;

	private BigDecimal valorPrevistoFinal;

	private Date dataPrevistaInicial;

	private Date dataPrevistaFinal;

	private BigDecimal valorRealizadoInicial;

	private BigDecimal valorRealizadoFinal;

	private Date dataRealizadaInicial;

	private Date dataRealizadaFinal;

	private Integer idContaBancaria;

	private Short codigoAgenteArrecadador;

	private String nomeCliente;

	private Integer idBancoContaBancaria;

	private String codigoAgenciaContaBancaria;

	private String numeroContaBancaria;

	private Collection<Pagamento> colecaoPagamentos;

	private Collection<Devolucao> colecaoDevolucoes;

	private String numeroSequencialArquivo;

	private BigDecimal valorSomatorioDeducoes;

	private BigDecimal valorArrecadacaoInformado;

	private BigDecimal valorDevolucaoInformado;

	private BigDecimal valorRealizado;

	private BigDecimal valorPagamentosClassificados;

	private BigDecimal valorPagamentosNaoClassificados;

	private Short indicadorAbertoFechado;

	private Integer idConcessionaria;

	private String cdConvenio;

	private String tipoServico;

	public AvisoBancario getAvisoBancario(){

		return avisoBancario;
	}

	public void setAvisoBancario(AvisoBancario avisoBancario){

		this.avisoBancario = avisoBancario;
	}

	public AvisoBancarioHelper() {

	}

	public Date getDataLancamento(){

		return dataLancamento;
	}

	public void setDataLancamento(Date dataLancamento){

		this.dataLancamento = dataLancamento;
	}

	public Date getDataRealizada(){

		return dataRealizada;
	}

	public void setDataRealizada(Date dataRealizada){

		this.dataRealizada = dataRealizada;
	}

	public Integer getIdAvisoBancario(){

		return idAvisoBancario;
	}

	public void setIdAvisoBancario(Integer idAvisoBancario){

		this.idAvisoBancario = idAvisoBancario;
	}

	public short getIndicadorCreditoDebito(){

		return indicadorCreditoDebito;
	}

	public void setIndicadorCreditoDebito(short indicadorCreditoDebito){

		this.indicadorCreditoDebito = indicadorCreditoDebito;
	}

	public Short getNumeroSequencial(){

		return numeroSequencial;
	}

	public void setNumeroSequencial(Short numeroSequencial){

		this.numeroSequencial = numeroSequencial;
	}

	public String getSituacao(){

		return situacao;
	}

	public void setSituacao(String situacao){

		this.situacao = situacao;
	}

	public String getTipoAviso(){

		return tipoAviso;
	}

	public void setTipoAviso(String tipoAviso){

		this.tipoAviso = tipoAviso;
	}

	public Collection<Pagamento> getColecaoPagamentos(){

		return colecaoPagamentos;
	}

	public void setColecaoPagamentos(Collection<Pagamento> colecaoPagamentos){

		this.colecaoPagamentos = colecaoPagamentos;
	}

	public Collection<Devolucao> getColecaoDevolucoes(){

		return colecaoDevolucoes;
	}

	public void setColecaoDevolucoes(Collection<Devolucao> colecaoDevolucoes){

		this.colecaoDevolucoes = colecaoDevolucoes;
	}

	public String getDescricaoIndicadorCreditoDebito(){

		return descricaoIndicadorCreditoDebito;
	}

	public void setDescricaoIndicadorCreditoDebito(String descricaoIndicadorCreditoDebito){

		this.descricaoIndicadorCreditoDebito = descricaoIndicadorCreditoDebito;
	}

	public Integer getIdMovimentoArrecadador(){

		return idMovimentoArrecadador;
	}

	public void setIdMovimentoArrecadador(Integer idMovimentoArrecadador){

		this.idMovimentoArrecadador = idMovimentoArrecadador;
	}

	public int getAnoMesReferenciaArrecadacaoFinal(){

		return anoMesReferenciaArrecadacaoFinal;
	}

	public void setAnoMesReferenciaArrecadacaoFinal(int anoMesReferenciaArrecadacaoFinal){

		this.anoMesReferenciaArrecadacaoFinal = anoMesReferenciaArrecadacaoFinal;
	}

	public int getAnoMesReferenciaArrecadacaoInicial(){

		return anoMesReferenciaArrecadacaoInicial;
	}

	public void setAnoMesReferenciaArrecadacaoInicial(int anoMesReferenciaArrecadacaoInicial){

		this.anoMesReferenciaArrecadacaoInicial = anoMesReferenciaArrecadacaoInicial;
	}

	public Date getDataLancamentoFinal(){

		return dataLancamentoFinal;
	}

	public void setDataLancamentoFinal(Date dataLancamentoFinal){

		this.dataLancamentoFinal = dataLancamentoFinal;
	}

	public Date getDataLancamentoInicial(){

		return dataLancamentoInicial;
	}

	public void setDataLancamentoInicial(Date dataLancamentoInicial){

		this.dataLancamentoInicial = dataLancamentoInicial;
	}

	public Date getDataPrevistaFinal(){

		return dataPrevistaFinal;
	}

	public void setDataPrevistaFinal(Date dataPrevistaFinal){

		this.dataPrevistaFinal = dataPrevistaFinal;
	}

	public Date getDataPrevistaInicial(){

		return dataPrevistaInicial;
	}

	public void setDataPrevistaInicial(Date dataPrevistaInicial){

		this.dataPrevistaInicial = dataPrevistaInicial;
	}

	public Date getDataRealizadaFinal(){

		return dataRealizadaFinal;
	}

	public void setDataRealizadaFinal(Date dataRealizadaFinal){

		this.dataRealizadaFinal = dataRealizadaFinal;
	}

	public Date getDataRealizadaInicial(){

		return dataRealizadaInicial;
	}

	public void setDataRealizadaInicial(Date dataRealizadaInicial){

		this.dataRealizadaInicial = dataRealizadaInicial;
	}

	public BigDecimal getValorRealizadoFinal(){

		return valorRealizadoFinal;
	}

	public void setValorRealizadoFinal(BigDecimal valorRealizadoFinal){

		this.valorRealizadoFinal = valorRealizadoFinal;
	}

	public BigDecimal getValorRealizadoInicial(){

		return valorRealizadoInicial;
	}

	public void setValorRealizadoInicial(BigDecimal valorRealizadoInicial){

		this.valorRealizadoInicial = valorRealizadoInicial;
	}

	public BigDecimal getValorPrevistoFinal(){

		return valorPrevistoFinal;
	}

	public void setValorPrevistoFinal(BigDecimal valorPrevistoFinal){

		this.valorPrevistoFinal = valorPrevistoFinal;
	}

	public BigDecimal getValorPrevistoInicial(){

		return valorPrevistoInicial;
	}

	public void setValorPrevistoInicial(BigDecimal valorPrevistoInicial){

		this.valorPrevistoInicial = valorPrevistoInicial;
	}

	public Integer getIdContaBancaria(){

		return idContaBancaria;
	}

	public void setIdContaBancaria(Integer idContaBancaria){

		this.idContaBancaria = idContaBancaria;
	}

	public Short getCodigoAgenteArrecadador(){

		return codigoAgenteArrecadador;
	}

	public void setCodigoAgenteArrecadador(Short codigoAgenteArrecadador){

		this.codigoAgenteArrecadador = codigoAgenteArrecadador;
	}

	public String getCodigoAgenciaContaBancaria(){

		return codigoAgenciaContaBancaria;
	}

	public void setCodigoAgenciaContaBancaria(String codigoAgenciaContaBancaria){

		this.codigoAgenciaContaBancaria = codigoAgenciaContaBancaria;
	}

	public Integer getIdBancoContaBancaria(){

		return idBancoContaBancaria;
	}

	public void setIdBancoContaBancaria(Integer idBancoContaBancaria){

		this.idBancoContaBancaria = idBancoContaBancaria;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getNumeroContaBancaria(){

		return numeroContaBancaria;
	}

	public void setNumeroContaBancaria(String numeroContaBancaria){

		this.numeroContaBancaria = numeroContaBancaria;
	}

	public String getCodigoNomeArrecadador(){

		return getCodigoAgenteArrecadador() + " - " + getNomeCliente();
	}

	public String getNumeroSequencialArquivo(){

		return numeroSequencialArquivo;
	}

	public void setNumeroSequencialArquivo(String numeroSequencialArquivo){

		this.numeroSequencialArquivo = numeroSequencialArquivo;
	}

	public BigDecimal getValorAcertos(){

		return valorAcertos;
	}

	public void setValorAcertos(BigDecimal valorAcertos){

		this.valorAcertos = valorAcertos;
	}

	public BigDecimal getValorCalculado(){

		return valorCalculado;
	}

	public void setValorCalculado(BigDecimal valorCalculado){

		this.valorCalculado = valorCalculado;
	}

	public BigDecimal getValorInformado(){

		return valorInformado;
	}

	public void setValorInformado(BigDecimal valorInformado){

		this.valorInformado = valorInformado;
	}

	public BigDecimal getValorDiferenca(){

		return valorDiferenca;
	}

	public void setValorDiferenca(BigDecimal valorDiferenca){

		this.valorDiferenca = valorDiferenca;
	}

	public BigDecimal getValorSomatorioDeducoes(){

		return valorSomatorioDeducoes;
	}

	public void setValorSomatorioDeducoes(BigDecimal valorSomatorioDeducoes){

		this.valorSomatorioDeducoes = valorSomatorioDeducoes;
	}

	public BigDecimal getValorArrecadacaoInformado(){

		return valorArrecadacaoInformado;
	}

	public void setValorArrecadacaoInformado(BigDecimal valorArrecadacaoInformado){

		this.valorArrecadacaoInformado = valorArrecadacaoInformado;
	}

	public BigDecimal getValorDevolucaoInformado(){

		return valorDevolucaoInformado;
	}

	public void setValorDevolucaoInformado(BigDecimal valorDevolucaoInformado){

		this.valorDevolucaoInformado = valorDevolucaoInformado;
	}

	public BigDecimal getValorRealizado(){

		return valorRealizado;
	}

	public void setValorRealizado(BigDecimal valorRealizado){

		this.valorRealizado = valorRealizado;
	}

	public BigDecimal getValorPagamentosClassificados(){

		return valorPagamentosClassificados;
	}

	public void setValorPagamentosClassificados(BigDecimal valorPagamentosClassificados){

		this.valorPagamentosClassificados = valorPagamentosClassificados;
	}

	public BigDecimal getValorPagamentosNaoClassificados(){

		return valorPagamentosNaoClassificados;
	}

	public void setValorPagamentosNaoClassificados(BigDecimal valorPagamentosNaoClassificados){

		this.valorPagamentosNaoClassificados = valorPagamentosNaoClassificados;
	}

	public Short getIndicadorAbertoFechado(){

		return indicadorAbertoFechado;
	}

	public void setIndicadorAbertoFechado(Short indicadorAbertoFechado){

		this.indicadorAbertoFechado = indicadorAbertoFechado;
	}

	public Integer getIdConcessionaria(){

		return idConcessionaria;
	}

	public void setIdConcessionaria(Integer idConcessionaria){

		this.idConcessionaria = idConcessionaria;
	}

	public String getCdConvenio(){

		return cdConvenio;
	}

	public void setCdConvenio(String cdConvenio){

		this.cdConvenio = cdConvenio;
	}

	public String getTipoServico(){

		return tipoServico;
	}

	public void setTipoServico(String tipoServico){

		this.tipoServico = tipoServico;
	}

}
