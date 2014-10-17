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

package gcom.gui.arrecadacao.pagamento;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Descri��o da classe
 * 
 * @author Vivianne Sousa
 * @date 10/07/2007
 * @author Virg�nia Melo
 * @date 17/08/2009
 *       Adicionados campos que ser�o exibidos ao consultar
 *       um pagamento na tela de estorno de d�bido.
 */
public class ConsultarPagamentoPopupActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String codigoAgenteArrecadador;

	private String nomeClienteArrecadador;

	private String ultimaAlteracaoPagamento;

	// Dados do pagamento
	private String dataLancamento;

	private String sequencial;

	private String formaArrecadacao;

	private String tipoDocumento;

	private String codigoLocalidade;

	private String descricaoLocalidade;

	private String matriculaImovel;

	private String codigoCliente;

	private String nomeCliente;

	private String valorPagamento;

	private String dataPagamento;

	private String situacaoPagamento;

	private String tipoDebito; // usado em guia e debito a cobrar

	private String numeroPrestacao; // usado em guia e debito a cobrar

	// Dados da conta
	private String idConta;

	private String referenciaConta;

	// Dados da guia
	private String idGuiaPagamento;

	// Dados d�bito a cobrar
	private String idDebito;

	private String quantidadeParcelas;

	public String getQuantidadeParcelas(){

		return quantidadeParcelas;
	}

	public void setQuantidadeParcelas(String quantidadeParcelas){

		this.quantidadeParcelas = quantidadeParcelas;
	}

	public String getCodigoAgenteArrecadador(){

		return codigoAgenteArrecadador;
	}

	public void setCodigoAgenteArrecadador(String codigoAgenteArrecadador){

		this.codigoAgenteArrecadador = codigoAgenteArrecadador;
	}

	public String getNomeClienteArrecadador(){

		return nomeClienteArrecadador;
	}

	public void setNomeClienteArrecadador(String nomeClienteArrecadador){

		this.nomeClienteArrecadador = nomeClienteArrecadador;
	}

	public String getUltimaAlteracaoPagamento(){

		return ultimaAlteracaoPagamento;
	}

	public void setUltimaAlteracaoPagamento(String ultimaAlteracaoPagamento){

		this.ultimaAlteracaoPagamento = ultimaAlteracaoPagamento;
	}

	public String getDataLancamento(){

		return dataLancamento;
	}

	public void setDataLancamento(String dataLancamento){

		this.dataLancamento = dataLancamento;
	}

	public String getSequencial(){

		return sequencial;
	}

	public void setSequencial(String sequencial){

		this.sequencial = sequencial;
	}

	public String getFormaArrecadacao(){

		return formaArrecadacao;
	}

	public void setFormaArrecadacao(String formaArrecadacao){

		this.formaArrecadacao = formaArrecadacao;
	}

	public String getTipoDocumento(){

		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento){

		this.tipoDocumento = tipoDocumento;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getCodigoCliente(){

		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente){

		this.codigoCliente = codigoCliente;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getValorPagamento(){

		return valorPagamento;
	}

	public void setValorPagamento(String valorPagamento){

		this.valorPagamento = valorPagamento;
	}

	public String getDataPagamento(){

		return dataPagamento;
	}

	public void setDataPagamento(String dataPagamento){

		this.dataPagamento = dataPagamento;
	}

	public String getSituacaoPagamento(){

		return situacaoPagamento;
	}

	public void setSituacaoPagamento(String situacaoPagamento){

		this.situacaoPagamento = situacaoPagamento;
	}

	public String getCodigoLocalidade(){

		return codigoLocalidade;
	}

	public void setCodigoLocalidade(String codigoLocalidade){

		this.codigoLocalidade = codigoLocalidade;
	}

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	public String getTipoDebito(){

		return tipoDebito;
	}

	public void setTipoDebito(String tipoDebito){

		this.tipoDebito = tipoDebito;
	}

	public String getReferenciaConta(){

		return referenciaConta;
	}

	public void setReferenciaConta(String referenciaConta){

		this.referenciaConta = referenciaConta;
	}

	public String getIdGuiaPagamento(){

		return idGuiaPagamento;
	}

	public void setIdGuiaPagamento(String idGuiaPagamento){

		this.idGuiaPagamento = idGuiaPagamento;
	}

	public String getNumeroPrestacao(){

		return numeroPrestacao;
	}

	public void setNumeroPrestacao(String numeroPrestacao){

		this.numeroPrestacao = numeroPrestacao;
	}

	public String getIdDebito(){

		return idDebito;
	}

	public void setIdDebito(String idDebito){

		this.idDebito = idDebito;
	}

	public String getIdConta(){

		return idConta;
	}

	public void setIdConta(String idConta){

		this.idConta = idConta;
	}

}
