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

package gcom.gui.arrecadacao.pagamento;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Descrição da classe
 * 
 * @author Vivianne Sousa
 * @date 10/07/2007
 * @author Virgínia Melo
 * @date 17/08/2009
 *       Adicionados campos que serão exibidos ao consultar
 *       um pagamento na tela de estorno de débido.
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

	// Dados débito a cobrar
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
