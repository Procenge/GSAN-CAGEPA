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

package gcom.gui.arrecadacao;

import gcom.arrecadacao.ArrecadadorContrato;
import gcom.arrecadacao.Concessionaria;

import org.apache.struts.validator.ValidatorForm;

/**
 * [UC510] Manter Contrato de Arrecadador [SB0001]Atualizar Contrato Arrecadador
 * 
 * @author Marcio Roberto
 * @date 11/04/2007
 */

public class AtualizarContratoArrecadadorActionForm
				extends ValidatorForm {

	private static final long serialVersionUID = 1L;

	private String idContrato;

	private String idArrecadador;

	private String idClienteCombo;

	private String nomeClienteCombo;

	private String numeroContrato;

	private String idContaBancariaArrecadador;

	private String bcoArrecadadorConta;

	private String agArrecadadorConta;

	private String numeroArrecadadorConta;

	private String bcoArrecadadorContaBoletoBancario;

	private String agArrecadadorContaBoletoBancario;

	private String numeroArrecadadorContaBoletoBancario;

	private String idContaBancariaTarifa;

	private String bcoTarifaConta;

	private String agTarifaConta;

	private String numeroTarifaConta;

	private String bcoTarifaContaBoletoBancario;

	private String agTarifaContaBoletoBancario;

	private String numeroTarifaContaBoletoBancario;

	private Concessionaria concessionaria;

	public String getBcoArrecadadorContaBoletoBancario(){

		return bcoArrecadadorContaBoletoBancario;
	}

	public void setBcoArrecadadorContaBoletoBancario(String bcoArrecadadorContaBoletoBancario){

		this.bcoArrecadadorContaBoletoBancario = bcoArrecadadorContaBoletoBancario;
	}

	public String getAgArrecadadorContaBoletoBancario(){

		return agArrecadadorContaBoletoBancario;
	}

	public void setAgArrecadadorContaBoletoBancario(String agArrecadadorContaBoletoBancario){

		this.agArrecadadorContaBoletoBancario = agArrecadadorContaBoletoBancario;
	}

	public String getNumeroArrecadadorContaBoletoBancario(){

		return numeroArrecadadorContaBoletoBancario;
	}

	public void setNumeroArrecadadorContaBoletoBancario(String numeroArrecadadorContaBoletoBancario){

		this.numeroArrecadadorContaBoletoBancario = numeroArrecadadorContaBoletoBancario;
	}

	public String getBcoTarifaContaBoletoBancario(){

		return bcoTarifaContaBoletoBancario;
	}

	public void setBcoTarifaContaBoletoBancario(String bcoTarifaContaBoletoBancario){

		this.bcoTarifaContaBoletoBancario = bcoTarifaContaBoletoBancario;
	}

	public String getAgTarifaContaBoletoBancario(){

		return agTarifaContaBoletoBancario;
	}

	public void setAgTarifaContaBoletoBancario(String agTarifaContaBoletoBancario){

		this.agTarifaContaBoletoBancario = agTarifaContaBoletoBancario;
	}

	public String getNumeroTarifaContaBoletoBancario(){

		return numeroTarifaContaBoletoBancario;
	}

	public void setNumeroTarifaContaBoletoBancario(String numeroTarifaContaBoletoBancario){

		this.numeroTarifaContaBoletoBancario = numeroTarifaContaBoletoBancario;
	}

	private String idCliente;

	private String nomeCliente;

	private String emailCliente;

	private String idConvenio;

	private String indicadorCobranca;

	private String dtInicioContrato;

	private String dtFimContrato;

	private String descricaoEmail;

	private String dataContratoEncerramento;

	private String contratoMotivoCancelamento;

	private String idContratoMotivoCancelamento;

	private String indicadorUso;

	private String idConvenioDebitoAutomatico;

	private String idFormaArrecadacao;

	private String valorTarifa;

	private String percentualTarifa;

	public String getIdFormaArrecadacao(){

		return idFormaArrecadacao;
	}

	public void setIdFormaArrecadacao(String idFormaArrecadacao){

		this.idFormaArrecadacao = idFormaArrecadacao;
	}

	public String getValorTarifa(){

		return valorTarifa;
	}

	public void setValorTarifa(String valorTarifa){

		this.valorTarifa = valorTarifa;
	}

	public String getPercentualTarifa(){

		return percentualTarifa;
	}

	public void setPercentualTarifa(String percentualTarifa){

		this.percentualTarifa = percentualTarifa;
	}

	public String getQtdDiasFloat(){

		return qtdDiasFloat;
	}

	public void setQtdDiasFloat(String qtdDiasFloat){

		this.qtdDiasFloat = qtdDiasFloat;
	}

	private String qtdDiasFloat;

	public String getIdConvenioDebitoAutomatico(){

		return idConvenioDebitoAutomatico;
	}

	public void setIdConvenioDebitoAutomatico(String idConvenioDebitoAutomatico){

		this.idConvenioDebitoAutomatico = idConvenioDebitoAutomatico;
	}

	public String getIdConvenioFichaCompensacao(){

		return idConvenioFichaCompensacao;
	}

	public void setIdConvenioFichaCompensacao(String idConvenioFichaCompensacao){

		this.idConvenioFichaCompensacao = idConvenioFichaCompensacao;
	}

	public String getIdConvenioParcelamentoResponsavel(){

		return idConvenioParcelamentoResponsavel;
	}

	public void setIdConvenioParcelamentoResponsavel(String idConvenioParcelamentoResponsavel){

		this.idConvenioParcelamentoResponsavel = idConvenioParcelamentoResponsavel;
	}

	public String getNomePrimeiroResponsavel(){

		return nomePrimeiroResponsavel;
	}

	public void setNomePrimeiroResponsavel(String nomePrimeiroResponsavel){

		this.nomePrimeiroResponsavel = nomePrimeiroResponsavel;
	}

	public String getCpfPrimeiroResponsavel(){

		return cpfPrimeiroResponsavel;
	}

	public void setCpfPrimeiroResponsavel(String cpfPrimeiroResponsavel){

		this.cpfPrimeiroResponsavel = cpfPrimeiroResponsavel;
	}

	public String getNomeSegundoResponsavel(){

		return nomeSegundoResponsavel;
	}

	public void setNomeSegundoResponsavel(String nomeSegundoResponsavel){

		this.nomeSegundoResponsavel = nomeSegundoResponsavel;
	}

	public String getCpfSegundoResponsavel(){

		return cpfSegundoResponsavel;
	}

	public void setCpfSegundoResponsavel(String cpfSegundoResponsavel){

		this.cpfSegundoResponsavel = cpfSegundoResponsavel;
	}

	private String idConvenioBoletoBancario;

	private String idDepositoArrecadacaoBoleto;

	private String idDepositoTarifaBoleto;

	public String getIdConvenioBoletoBancario(){

		return idConvenioBoletoBancario;
	}

	public void setIdConvenioBoletoBancario(String idConvenioBoletoBancario){

		this.idConvenioBoletoBancario = idConvenioBoletoBancario;
	}

	public String getIdDepositoArrecadacaoBoleto(){

		return idDepositoArrecadacaoBoleto;
	}

	public void setIdDepositoArrecadacaoBoleto(String idDepositoArrecadacaoBoleto){

		this.idDepositoArrecadacaoBoleto = idDepositoArrecadacaoBoleto;
	}

	public String getIdDepositoTarifaBoleto(){

		return idDepositoTarifaBoleto;
	}

	public void setIdDepositoTarifaBoleto(String idDepositoTarifaBoleto){

		this.idDepositoTarifaBoleto = idDepositoTarifaBoleto;
	}

	private String idConvenioFichaCompensacao;

	private String idConvenioParcelamentoResponsavel;

	private String nomePrimeiroResponsavel;

	private String cpfPrimeiroResponsavel;

	private String nomeSegundoResponsavel;

	private String cpfSegundoResponsavel;

	public String getAgArrecadadorConta(){

		return agArrecadadorConta;
	}

	public void setAgArrecadadorConta(String agArrecadadorConta){

		this.agArrecadadorConta = agArrecadadorConta;
	}

	public String getAgTarifaConta(){

		return agTarifaConta;
	}

	public void setAgTarifaConta(String agTarifaConta){

		this.agTarifaConta = agTarifaConta;
	}

	public String getBcoArrecadadorConta(){

		return bcoArrecadadorConta;
	}

	public void setBcoArrecadadorConta(String bcoArrecadadorConta){

		this.bcoArrecadadorConta = bcoArrecadadorConta;
	}

	public String getBcoTarifaConta(){

		return bcoTarifaConta;
	}

	public void setBcoTarifaConta(String bcoTarifaConta){

		this.bcoTarifaConta = bcoTarifaConta;
	}

	public String getDtFimContrato(){

		return dtFimContrato;
	}

	public void setDtFimContrato(String dtFimContrato){

		this.dtFimContrato = dtFimContrato;
	}

	public String getDtInicioContrato(){

		return dtInicioContrato;
	}

	public void setDtInicioContrato(String dtInicioContrato){

		this.dtInicioContrato = dtInicioContrato;
	}

	public String getEmailCliente(){

		return emailCliente;
	}

	public void setEmailCliente(String emailCliente){

		this.emailCliente = emailCliente;
	}

	public String getIdArrecadador(){

		return idArrecadador;
	}

	public void setIdArrecadador(String idArrecadador){

		this.idArrecadador = idArrecadador;
	}

	public String getIdCliente(){

		return idCliente;
	}

	public void setIdCliente(String idCliente){

		this.idCliente = idCliente;
	}

	public String getIdClienteCombo(){

		return idClienteCombo;
	}

	public void setIdClienteCombo(String idClienteCombo){

		this.idClienteCombo = idClienteCombo;
	}

	public String getIdContaBancariaArrecadador(){

		return idContaBancariaArrecadador;
	}

	public void setIdContaBancariaArrecadador(String idContaBancariaArrecadador){

		this.idContaBancariaArrecadador = idContaBancariaArrecadador;
	}

	public String getIdContaBancariaTarifa(){

		return idContaBancariaTarifa;
	}

	public void setIdContaBancariaTarifa(String idContaBancariaTarifa){

		this.idContaBancariaTarifa = idContaBancariaTarifa;
	}

	public String getIdContrato(){

		return idContrato;
	}

	public void setIdContrato(String idContrato){

		this.idContrato = idContrato;
	}

	public String getIdConvenio(){

		return idConvenio;
	}

	public void setIdConvenio(String idConvenio){

		this.idConvenio = idConvenio;
	}

	public String getIndicadorCobranca(){

		return indicadorCobranca;
	}

	public void setIndicadorCobranca(String indicadorCobranca){

		this.indicadorCobranca = indicadorCobranca;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getNomeClienteCombo(){

		return nomeClienteCombo;
	}

	public void setNomeClienteCombo(String nomeClienteCombo){

		this.nomeClienteCombo = nomeClienteCombo;
	}

	public String getNumeroArrecadadorConta(){

		return numeroArrecadadorConta;
	}

	public void setNumeroArrecadadorConta(String numeroArrecadadorConta){

		this.numeroArrecadadorConta = numeroArrecadadorConta;
	}

	public String getNumeroContrato(){

		return numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato){

		this.numeroContrato = numeroContrato;
	}

	public String getNumeroTarifaConta(){

		return numeroTarifaConta;
	}

	public void setNumeroTarifaConta(String numeroTarifaConta){

		this.numeroTarifaConta = numeroTarifaConta;
	}

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public String getDescricaoEmail(){

		return descricaoEmail;
	}

	public void setDescricaoEmail(String descricaoEmail){

		this.descricaoEmail = descricaoEmail;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	// Esse método carrega todos os valores da base de dados
	// necesários para exibição da tela AtualizarArrecadador.
	public ArrecadadorContrato setFormValues(ArrecadadorContrato arrecadadorContrato){

		// Fachada fachada = Fachada.getInstancia();

		// seta o atributo de Cliente no objeto arrecadador
		// Arrecadador arrecadador = new Arrecadador();
		// arrecadador.setId(new Integer(getIdArrecadador()));
		// arrecadadorContrato.setArrecadador(arrecadador);

		// Seta Objeto Cliente para Arrecadador
		// FiltroCliente filtroCliente = new FiltroCliente();
		// filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, getIdCliente()));
		// Collection<Cliente> colecaoCliente = fachada.pesquisar(filtroCliente,
		// Cliente.class.getName());
		// Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);
		// arrecadadorContrato.setCliente(cliente);

		return arrecadadorContrato;
	}

	public String getDataContratoEncerramento(){

		return dataContratoEncerramento;
	}

	public void setDataContratoEncerramento(String dataContratoEncerramento){

		this.dataContratoEncerramento = dataContratoEncerramento;
	}

	public String getContratoMotivoCancelamento(){

		return contratoMotivoCancelamento;
	}

	public void setContratoMotivoCancelamento(String contratoMotivoCancelamento){

		this.contratoMotivoCancelamento = contratoMotivoCancelamento;
	}

	public String getIdContratoMotivoCancelamento(){

		return idContratoMotivoCancelamento;
	}

	public void setIdContratoMotivoCancelamento(String idContratoMotivoCancelamento){

		this.idContratoMotivoCancelamento = idContratoMotivoCancelamento;
	}

	/**
	 * @return the concessionaria
	 */
	public Concessionaria getConcessionaria(){

		return concessionaria;
	}

	/**
	 * @param concessionaria
	 *            the concessionaria to set
	 */
	public void setConcessionaria(Concessionaria concessionaria){

		this.concessionaria = concessionaria;
	}

}
