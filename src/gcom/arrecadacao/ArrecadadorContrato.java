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

package gcom.arrecadacao;

import gcom.arrecadacao.banco.ContaBancaria;
import gcom.cadastro.cliente.Cliente;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ArrecadadorContrato
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** persistent field */
	private String numeroContrato;

	/** nullable persistent field */
	private Date dataContratoInicio;

	/** nullable persistent field */
	private String codigoConvenio;

	/** nullable persistent field */
	private Date dataContratoFim;

	/** nullable persistent field */
	private Date dataContratoEncerramento;

	/** nullable persistent field */
	private Short indicadorCobrancaIss;

	/** nullable persistent field */
	private Integer numeroSequecialArquivoRetornoCodigoBarras;

	/** nullable persistent field */
	private Integer numeroSequencialArquivoRetornoDebitoAutomatico;

	/** nullable persistent field */
	private Integer numeroSequencialArquivoEnvioDebitoAutomatico;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private ContaBancaria contaBancariaDepositoTarifa;

	/** persistent field */
	private ContaBancaria contaBancariaDepositoArrecadacao;

	/** persistent field */
	private gcom.arrecadacao.Arrecadador arrecadador;

	/** persistent field */
	private gcom.arrecadacao.ContratoMotivoCancelamento contratoMotivoCancelamento;

	/** persistent field */
	private Cliente cliente;

	private String descricaoEmail;

	/** nullable persistent field */
	private Integer numeroSequencialArquivoRetornoFichaCompensacao;

	private String codigoConvenioDebitoAutomatico;

	private String codigoConvenioFichaCompensacao;

	private String codigoConvenioBoletoBancario;

	private Integer numeroSequencialArquivoRetornoBoleto;

	private Integer numeroSequencialArquivoEnvioBoleto;

	private Integer numeroSequencialArquivoEnvioFichaCompensacao;

	private Integer numeroSequencialBoleto;

	private ContaBancaria contaBancariaDepositoTarifaBoleto;

	private ContaBancaria contaBancariaDepositoArrecadacaoBoleto;

	private String codigoConvenioParcelamentoResposavel;

	private Integer numeroSequencialArquivoRetornoParcelamentoResposavel;

	private Integer numeroSequencialArquivoEnvioParcelamentoResposavel;

	private Integer numeroSequencialFichaCompensacao;

	private String nomePrimeiroResposavel;

	private String cpfPrimeiroResposavel;

	private String nomeSegundoResposavel;

	private String cpfSegundoResposavel;

	private Short indicadorCriticarNumeroSequencialArquivo = 2;

	private Short indicadorCriarNumeroSequencialArquivoCodigoBarras = 2;

	private Concessionaria concessionaria = new Concessionaria(1);

	public String getCodigoConvenioFichaCompensacao(){

		return codigoConvenioFichaCompensacao;
	}

	public void setCodigoConvenioFichaCompensacao(String codigoConvenioFichaCompensacao){

		this.codigoConvenioFichaCompensacao = codigoConvenioFichaCompensacao;
	}

	public String getCodigoConvenioParcelamentoResposavel(){

		return codigoConvenioParcelamentoResposavel;
	}

	public void setCodigoConvenioParcelamentoResposavel(String codigoConvenioParcelamentoResposavel){

		this.codigoConvenioParcelamentoResposavel = codigoConvenioParcelamentoResposavel;
	}

	public String getNomePrimeiroResposavel(){

		return nomePrimeiroResposavel;
	}

	public void setNomePrimeiroResposavel(String nomePrimeiroResposavel){

		this.nomePrimeiroResposavel = nomePrimeiroResposavel;
	}

	public String getCpfPrimeiroResposavel(){

		return cpfPrimeiroResposavel;
	}

	public void setCpfPrimeiroResposavel(String cpfPrimeiroResposavel){

		this.cpfPrimeiroResposavel = cpfPrimeiroResposavel;
	}

	public String getNomeSegundoResposavel(){

		return nomeSegundoResposavel;
	}

	public void setNomeSegundoResposavel(String nomeSegundoResposavel){

		this.nomeSegundoResposavel = nomeSegundoResposavel;
	}

	public String getCpfSegundoResposavel(){

		return cpfSegundoResposavel;
	}

	public void setCpfSegundoResposavel(String cpfSegundoResposavel){

		this.cpfSegundoResposavel = cpfSegundoResposavel;
	}

	/** full constructor */
	public ArrecadadorContrato(String numeroContrato, Date dataContratoInicio, String codigoConvenio, Date dataContratoFim,
								Date dataContratoEncerramento, Short indicadorCobrancaIss,
								Integer numeroSequecialArquivoRetornoCodigoBarras, Integer numeroSequencialArquivoRetornoDebitoAutomatico,
								Integer numeroSequencialArquivoEnvioDebitoAutomatico, Date ultimaAlteracao,
								ContaBancaria contaBancariaDepositoTarifa, ContaBancaria contaBancariaDepositoArrecadacao,
								gcom.arrecadacao.Arrecadador arrecadador,
								gcom.arrecadacao.ContratoMotivoCancelamento contratoMotivoCancelamento, Cliente cliente,
								String codigoConvenioDebitoAutomatico) {

		this.numeroContrato = numeroContrato;
		this.dataContratoInicio = dataContratoInicio;
		this.codigoConvenio = codigoConvenio;
		this.dataContratoFim = dataContratoFim;
		this.dataContratoEncerramento = dataContratoEncerramento;
		this.indicadorCobrancaIss = indicadorCobrancaIss;
		this.numeroSequecialArquivoRetornoCodigoBarras = numeroSequecialArquivoRetornoCodigoBarras;
		this.numeroSequencialArquivoRetornoDebitoAutomatico = numeroSequencialArquivoRetornoDebitoAutomatico;
		this.numeroSequencialArquivoEnvioDebitoAutomatico = numeroSequencialArquivoEnvioDebitoAutomatico;
		this.ultimaAlteracao = ultimaAlteracao;
		this.contaBancariaDepositoTarifa = contaBancariaDepositoTarifa;
		this.contaBancariaDepositoArrecadacao = contaBancariaDepositoArrecadacao;
		this.arrecadador = arrecadador;
		this.contratoMotivoCancelamento = contratoMotivoCancelamento;
		this.cliente = cliente;
		this.codigoConvenioDebitoAutomatico = codigoConvenioDebitoAutomatico;
	}

	/** default constructor */
	public ArrecadadorContrato() {

	}

	/** minimal constructor */
	public ArrecadadorContrato(String numeroContrato, ContaBancaria contaBancariaDepositoTarifa,
								ContaBancaria contaBancariaDepositoArrecadacao, gcom.arrecadacao.Arrecadador arrecadador,
								gcom.arrecadacao.ContratoMotivoCancelamento contratoMotivoCancelamento, Cliente cliente) {

		this.numeroContrato = numeroContrato;
		this.contaBancariaDepositoTarifa = contaBancariaDepositoTarifa;
		this.contaBancariaDepositoArrecadacao = contaBancariaDepositoArrecadacao;
		this.arrecadador = arrecadador;
		this.contratoMotivoCancelamento = contratoMotivoCancelamento;
		this.cliente = cliente;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getNumeroContrato(){

		return this.numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato){

		this.numeroContrato = numeroContrato;
	}

	public Date getDataContratoInicio(){

		return this.dataContratoInicio;
	}

	public void setDataContratoInicio(Date dataContratoInicio){

		this.dataContratoInicio = dataContratoInicio;
	}

	public String getCodigoConvenio(){

		return this.codigoConvenio;
	}

	public void setCodigoConvenio(String codigoConvenio){

		this.codigoConvenio = codigoConvenio;
	}

	public Date getDataContratoFim(){

		return this.dataContratoFim;
	}

	public void setDataContratoFim(Date dataContratoFim){

		this.dataContratoFim = dataContratoFim;
	}

	public Date getDataContratoEncerramento(){

		return this.dataContratoEncerramento;
	}

	public void setDataContratoEncerramento(Date dataContratoEncerramento){

		this.dataContratoEncerramento = dataContratoEncerramento;
	}

	public Short getIndicadorCobrancaIss(){

		return this.indicadorCobrancaIss;
	}

	public void setIndicadorCobrancaIss(Short indicadorCobrancaIss){

		this.indicadorCobrancaIss = indicadorCobrancaIss;
	}

	public Integer getNumeroSequecialArquivoRetornoCodigoBarras(){

		return this.numeroSequecialArquivoRetornoCodigoBarras;
	}

	public void setNumeroSequecialArquivoRetornoCodigoBarras(Integer numeroSequecialArquivoRetornoCodigoBarras){

		this.numeroSequecialArquivoRetornoCodigoBarras = numeroSequecialArquivoRetornoCodigoBarras;
	}

	public Integer getNumeroSequencialArquivoRetornoDebitoAutomatico(){

		return this.numeroSequencialArquivoRetornoDebitoAutomatico;
	}

	public void setNumeroSequencialArquivoRetornoDebitoAutomatico(Integer numeroSequencialArquivoRetornoDebitoAutomatico){

		this.numeroSequencialArquivoRetornoDebitoAutomatico = numeroSequencialArquivoRetornoDebitoAutomatico;
	}

	public Integer getNumeroSequencialArquivoEnvioDebitoAutomatico(){

		return this.numeroSequencialArquivoEnvioDebitoAutomatico;
	}

	public void setNumeroSequencialArquivoEnvioDebitoAutomatico(Integer numeroSequencialArquivoEnvioDebitoAutomatico){

		this.numeroSequencialArquivoEnvioDebitoAutomatico = numeroSequencialArquivoEnvioDebitoAutomatico;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public ContaBancaria getContaBancariaDepositoTarifa(){

		return this.contaBancariaDepositoTarifa;
	}

	public void setContaBancariaDepositoTarifa(ContaBancaria contaBancariaDepositoTarifa){

		this.contaBancariaDepositoTarifa = contaBancariaDepositoTarifa;
	}

	public ContaBancaria getContaBancariaDepositoArrecadacao(){

		return this.contaBancariaDepositoArrecadacao;
	}

	public void setContaBancariaDepositoArrecadacao(ContaBancaria contaBancariaDepositoArrecadacao){

		this.contaBancariaDepositoArrecadacao = contaBancariaDepositoArrecadacao;
	}

	public gcom.arrecadacao.Arrecadador getArrecadador(){

		return this.arrecadador;
	}

	public void setArrecadador(gcom.arrecadacao.Arrecadador arrecadador){

		this.arrecadador = arrecadador;
	}

	public gcom.arrecadacao.ContratoMotivoCancelamento getContratoMotivoCancelamento(){

		return this.contratoMotivoCancelamento;
	}

	public void setContratoMotivoCancelamento(gcom.arrecadacao.ContratoMotivoCancelamento contratoMotivoCancelamento){

		this.contratoMotivoCancelamento = contratoMotivoCancelamento;
	}

	public Cliente getCliente(){

		return this.cliente;
	}

	public void setCliente(Cliente cliente){

		this.cliente = cliente;
	}

	public String getDescricaoEmail(){

		return descricaoEmail;
	}

	public void setDescricaoEmail(String descricaoEmail){

		this.descricaoEmail = descricaoEmail;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public Integer getNumeroSequencialArquivoRetornoFichaCompensacao(){

		return numeroSequencialArquivoRetornoFichaCompensacao;
	}

	public void setNumeroSequencialArquivoRetornoFichaCompensacao(Integer numeroSequencialArquivoRetornoFichaCompensacao){

		this.numeroSequencialArquivoRetornoFichaCompensacao = numeroSequencialArquivoRetornoFichaCompensacao;
	}

	public String getCodigoConvenioDebitoAutomatico(){

		return codigoConvenioDebitoAutomatico;
	}

	public void setCodigoConvenioDebitoAutomatico(String codigoConvenioDebitoAutomatico){

		this.codigoConvenioDebitoAutomatico = codigoConvenioDebitoAutomatico;
	}

	public String getCodigoConvenioBoletoBancario(){

		return codigoConvenioBoletoBancario;
	}

	public void setCodigoConvenioBoletoBancario(String codigoConvenioBoletoBancario){

		this.codigoConvenioBoletoBancario = codigoConvenioBoletoBancario;
	}

	public void setContaBancariaDepositoTarifaBoleto(ContaBancaria contaBancariaDepositoTarifaBoleto){

		this.contaBancariaDepositoTarifaBoleto = contaBancariaDepositoTarifaBoleto;
	}

	public ContaBancaria getContaBancariaDepositoTarifaBoleto(){

		return contaBancariaDepositoTarifaBoleto;
	}

	public void setContaBancariaDepositoArrecadacaoBoleto(ContaBancaria contaBancariaDepositoArrecadacaoBoleto){

		this.contaBancariaDepositoArrecadacaoBoleto = contaBancariaDepositoArrecadacaoBoleto;
	}

	public ContaBancaria getContaBancariaDepositoArrecadacaoBoleto(){

		return contaBancariaDepositoArrecadacaoBoleto;
	}

	public Integer getNumeroSequencialArquivoRetornoBoleto(){

		return numeroSequencialArquivoRetornoBoleto;
	}

	public void setNumeroSequencialArquivoRetornoBoleto(Integer numeroSequencialArquivoRetornoBoleto){

		this.numeroSequencialArquivoRetornoBoleto = numeroSequencialArquivoRetornoBoleto;
	}

	public Integer getNumeroSequencialArquivoEnvioBoleto(){

		return numeroSequencialArquivoEnvioBoleto;
	}

	public void setNumeroSequencialArquivoEnvioBoleto(Integer numeroSequencialArquivoEnvioBoleto){

		this.numeroSequencialArquivoEnvioBoleto = numeroSequencialArquivoEnvioBoleto;
	}

	public Integer getNumeroSequencialBoleto(){

		return numeroSequencialBoleto;
	}

	public void setNumeroSequencialBoleto(Integer numeroSequencialBoleto){

		this.numeroSequencialBoleto = numeroSequencialBoleto;
	}

	public void setNumeroSequencialArquivoEnvioFichaCompensacao(Integer numeroSequencialArquivoEnvioFichaCompensacao){

		this.numeroSequencialArquivoEnvioFichaCompensacao = numeroSequencialArquivoEnvioFichaCompensacao;
	}

	public Integer getNumeroSequencialArquivoEnvioFichaCompensacao(){

		return numeroSequencialArquivoEnvioFichaCompensacao;
	}

	public void setNumeroSequencialArquivoRetornoParcelamentoResposavel(Integer numeroSequencialArquivoRetornoParcelamentoResposavel){

		this.numeroSequencialArquivoRetornoParcelamentoResposavel = numeroSequencialArquivoRetornoParcelamentoResposavel;
	}

	public Integer getNumeroSequencialArquivoRetornoParcelamentoResposavel(){

		return numeroSequencialArquivoRetornoParcelamentoResposavel;
	}

	public void setNumeroSequencialArquivoEnvioParcelamentoResposavel(Integer numeroSequencialArquivoEnvioParcelamentoResposavel){

		this.numeroSequencialArquivoEnvioParcelamentoResposavel = numeroSequencialArquivoEnvioParcelamentoResposavel;
	}

	public Integer getNumeroSequencialArquivoEnvioParcelamentoResposavel(){

		return numeroSequencialArquivoEnvioParcelamentoResposavel;
	}

	public void setNumeroSequencialFichaCompensacao(Integer numeroSequencialFichaCompensacao){

		this.numeroSequencialFichaCompensacao = numeroSequencialFichaCompensacao;
	}

	public Integer getNumeroSequencialFichaCompensacao(){

		return numeroSequencialFichaCompensacao;
	}

	public Short getIndicadorCriticarNumeroSequencialArquivo(){

		return indicadorCriticarNumeroSequencialArquivo;
	}

	public void setIndicadorCriticarNumeroSequencialArquivo(Short indicadorCriticarNumeroSequencialArquivo){

		this.indicadorCriticarNumeroSequencialArquivo = indicadorCriticarNumeroSequencialArquivo;
	}

	public Short getIndicadorCriarNumeroSequencialArquivoCodigoBarras(){

		return indicadorCriarNumeroSequencialArquivoCodigoBarras;
	}

	public void setIndicadorCriarNumeroSequencialArquivoCodigoBarras(Short indicadorCriarNumeroSequencialArquivoCodigoBarras){

		this.indicadorCriarNumeroSequencialArquivoCodigoBarras = indicadorCriarNumeroSequencialArquivoCodigoBarras;
	}

	public Concessionaria getConcessionaria(){

		return concessionaria;
	}

	public void setConcessionaria(Concessionaria concessionaria){

		this.concessionaria = concessionaria;
	}

}
