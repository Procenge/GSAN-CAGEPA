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

package gcom.cadastro.cliente;

import gcom.faturamento.conta.Conta;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

@ControleAlteracao()
public class ClienteConta
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	public static final int ATRIBUTOS_ALTERAR_CLIENTE_RESPONSAVEL_CONJUNTO_CONTAS = 900155;

	/** identifier field */
	private Integer id;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ALTERAR_CLIENTE_RESPONSAVEL_CONJUNTO_CONTAS})
	private Short indicadorNomeConta;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ALTERAR_CLIENTE_RESPONSAVEL_CONJUNTO_CONTAS})
	private Date ultimaAlteracao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ALTERAR_CLIENTE_RESPONSAVEL_CONJUNTO_CONTAS})
	private gcom.cadastro.cliente.Cliente cliente;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ALTERAR_CLIENTE_RESPONSAVEL_CONJUNTO_CONTAS})
	private gcom.cadastro.cliente.ClienteRelacaoTipo clienteRelacaoTipo;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ALTERAR_CLIENTE_RESPONSAVEL_CONJUNTO_CONTAS})
	private Conta conta;

	/** full constructor */
	public ClienteConta(gcom.cadastro.cliente.Cliente cliente, gcom.cadastro.cliente.ClienteRelacaoTipo clienteRelacaoTipo, Conta conta) {

		this.cliente = cliente;
		this.clienteRelacaoTipo = clienteRelacaoTipo;
		this.conta = conta;
	}

	/** default constructor */
	public ClienteConta() {

	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public gcom.cadastro.cliente.Cliente getCliente(){

		return this.cliente;
	}

	public void setCliente(gcom.cadastro.cliente.Cliente cliente){

		this.cliente = cliente;
	}

	public gcom.cadastro.cliente.ClienteRelacaoTipo getClienteRelacaoTipo(){

		return this.clienteRelacaoTipo;
	}

	public void setClienteRelacaoTipo(gcom.cadastro.cliente.ClienteRelacaoTipo clienteRelacaoTipo){

		this.clienteRelacaoTipo = clienteRelacaoTipo;
	}

	public Conta getConta(){

		return this.conta;
	}

	public void setConta(Conta conta){

		this.conta = conta;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public Short getIndicadorNomeConta(){

		return indicadorNomeConta;
	}

	public void setIndicadorNomeConta(Short indicadorNomeConta){

		this.indicadorNomeConta = indicadorNomeConta;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"cliente.id"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Cliente"};
		return labels;
	}

	@Override
	public Filtro retornaFiltro(){

		FiltroClienteConta filtroClienteConta = new FiltroClienteConta();

		filtroClienteConta.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteConta.CLIENTE);
		filtroClienteConta.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteConta.CLIENTE_RELACAO_TIPO);
		filtroClienteConta.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteConta.CONTA);

		filtroClienteConta.adicionarParametro(new ParametroSimples(FiltroClienteConta.ID, this.getId()));
		return filtroClienteConta;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		String retorno = "";

		if(this.getCliente() != null && this.getConta() != null){

			retorno += "Cliente: " + this.getCliente().getId().toString();

			if(this.getClienteRelacaoTipo() != null){

				if(this.getClienteRelacaoTipo().getId().equals(ClienteRelacaoTipo.PROPRIETARIO)){

					retorno += " / " + " PROPRIETARIO ";
				}else if(this.getClienteRelacaoTipo().getId().equals(ClienteRelacaoTipo.USUARIO)){

					retorno += " / " + " USUARIO ";
				}else if(this.getClienteRelacaoTipo().getId().equals(ClienteRelacaoTipo.RESPONSAVEL)){

					retorno += " / " + " RESPONSAVEL ";
				}

				if(this.getConta().getReferencia() > 0){

					retorno += " #Ref: " + this.conta.getReferenciaFormatada();
				}
			}else if(this.getConta() != null){

				retorno += " / " + "ID da Conta: " + this.getConta().getId().toString();
			}
		}else{

			retorno = this.getId().toString();
		}

		return retorno;
	}
}
