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
 * GSANPCG
 * Eduardo Henrique
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

import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe Helper utilizada na Emissão da Guia de Pagamento
 * 
 * @author eduardo henrique
 * @date 14/08/2008
 *       Adição do Nr da Prestação da Guia de Pagamento
 */
public class GuiaPagamentoRelatorioHelper {

	private Integer idImovel;

	private Integer idCliente;

	private Date dataVencimento;

	private Integer idLocalidade;

	private String descLocalidade;

	private BigDecimal valorDebito;

	private Date dataEmissao;

	private String nomeCliente;

	private Integer idTipoDebito;

	private String descTipoDebito;

	private Short numeroPrestacaoGuiaPagamento;

	private Short numeroPrestacaoTotal;

	private Integer idTipoDocumento;

	private String descricaoTipoDocumento;

	private String matricula;

	private String inscricao;

	private String enderecoImovel;

	private String enderecoClienteResponsavel;

	private String representacaoNumericaCodBarraFormatada;

	private String representacaoNumericaCodBarraSemDigito;

	private String idGuiaPagamento;

	private Map<String, BigDecimal> mapValorPorTipoDebito = new HashMap<String, BigDecimal>();

	private String indicadorPrestacaoNoHistorico;

	private String indicadorPrestacaoDebitoAutomatico;

	private Integer anoMesReferenciaFaturamento;

	private Short indicadorCobrancaMulta;

	private String indicadorExibirAcrescimos;

	private Short indicadorEmissaoObservacaoRA;

	private String descricaoObservacao;

	private Integer numeroContratoParcelOrgaoPublico;

	/**
	 * minimal constructor
	 * 
	 * @param numeroPrestacaoGuiaPagamento
	 *            TODO
	 */
	public GuiaPagamentoRelatorioHelper(Integer idImovel, Date dataVencimento, Integer idLocalidade, String descLocalidade,
										BigDecimal valorDebito, Date dataEmissao, Integer idTipoDebito, String descTipoDebito,
										Short numeroPrestacaoDebito, Short numeroPrestacaoTotal, Integer idTipoDocumento,
										String descricaoTipoDocumento) {

		this.idImovel = idImovel;
		this.dataVencimento = dataVencimento;
		this.idLocalidade = idLocalidade;
		this.descLocalidade = descLocalidade;
		this.valorDebito = valorDebito;
		this.dataEmissao = dataEmissao;
		this.idTipoDebito = idTipoDebito;
		this.descTipoDebito = descTipoDebito;
		this.numeroPrestacaoGuiaPagamento = numeroPrestacaoDebito;
		this.numeroPrestacaoTotal = numeroPrestacaoTotal;
		this.idTipoDocumento = idTipoDocumento;
		this.descricaoTipoDocumento = descricaoTipoDocumento;
	}

	public GuiaPagamentoRelatorioHelper() {

	}

	public String getDescTipoDebito(){

		return descTipoDebito;
	}

	public void setDescTipoDebito(String descTipoDebito){

		this.descTipoDebito = descTipoDebito;
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

	public Date getDataEmissao(){

		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao){

		this.dataEmissao = dataEmissao;
	}

	public Integer getIdCliente(){

		return idCliente;
	}

	public void setIdCliente(Integer idCliente){

		this.idCliente = idCliente;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public BigDecimal getValorDebito(){

		return valorDebito;
	}

	public void setValorDebito(BigDecimal valorDebito){

		this.valorDebito = valorDebito;
	}

	public Date getDataVencimento(){

		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento){

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

	public String getDescLocalidade(){

		return descLocalidade;
	}

	public void setDescLocalidade(String descLocalidade){

		this.descLocalidade = descLocalidade;
	}

	public Integer getIdTipoDebito(){

		return idTipoDebito;
	}

	public void setIdTipoDebito(Integer idTipoDebito){

		this.idTipoDebito = idTipoDebito;
	}

	public String getIdGuiaPagamento(){

		return idGuiaPagamento;
	}

	public void setIdGuiaPagamento(String idGuiaPagamento){

		this.idGuiaPagamento = idGuiaPagamento;
	}

	public String getDescricaoObservacao(){

		return descricaoObservacao;
	}

	public void setDescricaoObservacao(String descricaoObservacao){

		this.descricaoObservacao = descricaoObservacao;
	}

	public Short getIndicadorEmissaoObservacaoRA(){

		return indicadorEmissaoObservacaoRA;
	}

	public void setIndicadorEmissaoObservacaoRA(Short indicadorEmissaoObservacaoRA){

		this.indicadorEmissaoObservacaoRA = indicadorEmissaoObservacaoRA;
	}

	// Data de Validade da Guia de Pagamento
	// último dia do proximo mês do mês/ano da data de vencimento da guia de pagamento
	public String getDataValidade(){

		int anoVencimento = Util.getAno(getDataVencimento());
		int mesVencimento = Util.getMes(getDataVencimento());

		Calendar calendarUltimoDiaMesAnoDataVencimento = new GregorianCalendar();

		calendarUltimoDiaMesAnoDataVencimento.set(Calendar.YEAR, anoVencimento);
		calendarUltimoDiaMesAnoDataVencimento.set(Calendar.MONTH, mesVencimento - 1);
		calendarUltimoDiaMesAnoDataVencimento.set(Calendar.DAY_OF_MONTH, calendarUltimoDiaMesAnoDataVencimento
						.getActualMaximum(Calendar.DAY_OF_MONTH));

		Date dateDataVencimentoMais3Dias = Util.adicionarNumeroDiasDeUmaData(getDataVencimento(), 3);
		Date dateDataCorrenteMais3Dias = Util.adicionarNumeroDiasDeUmaData(new Date(), 3);
		Date dateMaiorData = null;

		// retorna
		// -1 se a data1 for menor que a data2,
		// 0 se as datas forem iguais,
		// 1 se a data1 for maior que a data2.

		if(Util.compararData(dateDataVencimentoMais3Dias, dateDataCorrenteMais3Dias) >= 0){

			if(Util.compararData(dateDataVencimentoMais3Dias, calendarUltimoDiaMesAnoDataVencimento.getTime()) >= 0){
				dateMaiorData = dateDataVencimentoMais3Dias;
			}else{
				dateMaiorData = calendarUltimoDiaMesAnoDataVencimento.getTime();
			}

		}else{

			if(Util.compararData(dateDataCorrenteMais3Dias, calendarUltimoDiaMesAnoDataVencimento.getTime()) >= 0){
				dateMaiorData = dateDataCorrenteMais3Dias;
			}else{
				dateMaiorData = calendarUltimoDiaMesAnoDataVencimento.getTime();
			}
		}

		// String anoMesValidade = Util.getAnoMesComoString(getDataVencimento());
		// Calendar calendario = new GregorianCalendar();
		//
		// calendario.set(Calendar.YEAR, new Integer(anoMesValidade.substring(0, 4)).intValue());
		// calendario.set(Calendar.MONTH, new Integer(anoMesValidade.substring(4, 6)).intValue());
		// calendario.set(Calendar.DAY_OF_MONTH,
		// calendario.getActualMaximum(Calendar.DAY_OF_MONTH));

		return Util.formatarData(dateMaiorData);
	}

	public String getMatriculaFormatada(){

		String matriculaImovelFormatada = "";

		// Caso a matrícula tenha a quantidade de dígitos menor do que a cadastrada no parâmetro
		// P_NUMERO_DIGITOS_MATRICULA_IMOVEL adiciona zeros a esquerda do número
		matriculaImovelFormatada = Util.retornaMatriculaImovelFormatadaParametrizada(Integer.valueOf(getMatricula()));

		return matriculaImovelFormatada;
	}

	public Short getNumeroPrestacaoDebito(){

		return numeroPrestacaoGuiaPagamento;
	}

	public void setNumeroPrestacaoDebito(Short numeroPrestacaoDebito){

		this.numeroPrestacaoGuiaPagamento = numeroPrestacaoDebito;
	}

	public Short getNumeroPrestacaoTotal(){

		return numeroPrestacaoTotal;
	}

	public void setNumeroPrestacaoTotal(Short numeroPrestacaoTotal){

		this.numeroPrestacaoTotal = numeroPrestacaoTotal;
	}

	public String getPrestacaoFormatada(){

		String prestacaoFormatada = "";

		if(getNumeroPrestacaoDebito() != null && getNumeroPrestacaoTotal() != null){
			prestacaoFormatada = prestacaoFormatada + getNumeroPrestacaoDebito() + "/" + getNumeroPrestacaoTotal();
		}

		return prestacaoFormatada;
	}

	/**
	 * @return the numeroPrestacaoGuiaPagamento
	 */
	public Short getNumeroPrestacaoGuiaPagamento(){

		return numeroPrestacaoGuiaPagamento;
	}

	/**
	 * @param numeroPrestacaoGuiaPagamento
	 *            the numeroPrestacaoGuiaPagamento to set
	 */
	public void setNumeroPrestacaoGuiaPagamento(Short numeroPrestacaoGuiaPagamento){

		this.numeroPrestacaoGuiaPagamento = numeroPrestacaoGuiaPagamento;
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
	 * @return the idTipoDocumento
	 */
	public Integer getIdTipoDocumento(){

		return idTipoDocumento;
	}

	/**
	 * @param idTipoDocumento
	 *            the idTipoDocumento to set
	 */
	public void setIdTipoDocumento(Integer idTipoDocumento){

		this.idTipoDocumento = idTipoDocumento;
	}

	/**
	 * @return the mapValorPorTipoDebito
	 */
	public Map<String, BigDecimal> getMapValorPorTipoDebito(){

		return mapValorPorTipoDebito;
	}

	/**
	 * @param mapValorPorTipoDebito
	 *            the mapValorPorTipoDebito to set
	 */
	public void setMapValorPorTipoDebito(Map<String, BigDecimal> mapValorPorTipoDebito){

		this.mapValorPorTipoDebito = mapValorPorTipoDebito;
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

	public Integer getAnoMesReferenciaFaturamento(){

		return anoMesReferenciaFaturamento;
	}

	public void setAnoMesReferenciaFaturamento(Integer anoMesReferenciaFaturamento){

		this.anoMesReferenciaFaturamento = anoMesReferenciaFaturamento;
	}

	public Short getIndicadorCobrancaMulta(){

		return indicadorCobrancaMulta;
	}

	public void setIndicadorCobrancaMulta(Short indicadorCobrancaMulta){

		this.indicadorCobrancaMulta = indicadorCobrancaMulta;
	}

	public String getIndicadorExibirAcrescimos(){

		return indicadorExibirAcrescimos;
	}

	public void setIndicadorExibirAcrescimos(String indicadorExibirAcrescimos){

		this.indicadorExibirAcrescimos = indicadorExibirAcrescimos;
	}

	public Integer getNumeroContratoParcelOrgaoPublico(){

		return numeroContratoParcelOrgaoPublico;
	}

	public void setNumeroContratoParcelOrgaoPublico(Integer numeroContratoParcelOrgaoPublico){

		this.numeroContratoParcelOrgaoPublico = numeroContratoParcelOrgaoPublico;
	}

}
