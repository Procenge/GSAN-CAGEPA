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

package gcom.cadastro.cliente;

import gcom.cadastro.imovel.Imovel;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class ClienteImovel
				extends ObjetoTransacao
				implements Comparable<ClienteImovel> {

	private static final long serialVersionUID = 1L;

	public static final int ATRIBUTOS_IMOVEL_ATUALIZAR = 17;

	public static final int ATRIBUTOS_CLIENTE_IMOVEL_ATUALIZAR = 53817;

	public static final int ATRIBUTOS_CLIENTE_IMOVEL_REMOVER = 54335;

	public static final int ATRIBUTOS_CLIENTE_IMOVEL_INSERIR = 54853;

	/** identifier field */
	private Integer id;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_IMOVEL_INSERIR, ATRIBUTOS_CLIENTE_IMOVEL_ATUALIZAR, ATRIBUTOS_CLIENTE_IMOVEL_REMOVER})
	private Date dataInicioRelacao;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_IMOVEL_INSERIR, ATRIBUTOS_CLIENTE_IMOVEL_ATUALIZAR, ATRIBUTOS_CLIENTE_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_ATUALIZAR})
	private Date dataFimRelacao;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	@ControleAlteracao(value = FiltroClienteImovel.IMOVEL, funcionalidade = {ATRIBUTOS_CLIENTE_IMOVEL_INSERIR, ATRIBUTOS_CLIENTE_IMOVEL_ATUALIZAR, ATRIBUTOS_CLIENTE_IMOVEL_REMOVER})
	private Imovel imovel;

	// @ControleAlteracao(value=FiltroClienteImovel.IMOVEL_ENVIO_CONTA,
	// funcionalidade={ATRIBUTOS_CLIENTE_IMOVEL_INSERIR,ATRIBUTOS_CLIENTE_IMOVEL_ATUALIZAR,ATRIBUTOS_CLIENTE_IMOVEL_REMOVER})
	// private ImovelContaEnvio imovelContaEnvio;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_IMOVEL_INSERIR, ATRIBUTOS_CLIENTE_IMOVEL_ATUALIZAR, ATRIBUTOS_CLIENTE_IMOVEL_REMOVER})
	private Short indicadorNomeConta;

	/** persistent field */
	@ControleAlteracao(value = FiltroClienteImovel.CLIENTE_IMOVEL_FIM_RELACAO_MOTIVO, funcionalidade = {ATRIBUTOS_CLIENTE_IMOVEL_INSERIR, ATRIBUTOS_CLIENTE_IMOVEL_ATUALIZAR, ATRIBUTOS_CLIENTE_IMOVEL_REMOVER, ATRIBUTOS_IMOVEL_ATUALIZAR})
	private gcom.cadastro.cliente.ClienteImovelFimRelacaoMotivo clienteImovelFimRelacaoMotivo;

	@ControleAlteracao(value = FiltroClienteImovel.CLIENTE, funcionalidade = {ATRIBUTOS_CLIENTE_IMOVEL_INSERIR, ATRIBUTOS_CLIENTE_IMOVEL_ATUALIZAR, ATRIBUTOS_CLIENTE_IMOVEL_REMOVER})
	private gcom.cadastro.cliente.Cliente cliente;

	@ControleAlteracao(value = FiltroClienteImovel.CLIENTE_RELACAO_TIPO, funcionalidade = {ATRIBUTOS_CLIENTE_IMOVEL_INSERIR, ATRIBUTOS_CLIENTE_IMOVEL_ATUALIZAR, ATRIBUTOS_CLIENTE_IMOVEL_REMOVER})
	private gcom.cadastro.cliente.ClienteRelacaoTipo clienteRelacaoTipo;

	public transient Short indicadorEmissaoExtratoFaturamento;

	/** full constructor */
	public ClienteImovel(Date dataInicioRelacao, Date dataFimRelacao, Date ultimaAlteracao, Imovel imovel,
							gcom.cadastro.cliente.ClienteImovelFimRelacaoMotivo clienteImovelFimRelacaoMotivo,
							gcom.cadastro.cliente.Cliente cliente, gcom.cadastro.cliente.ClienteRelacaoTipo clienteRelacaoTipo) {

		this.dataInicioRelacao = dataInicioRelacao;
		this.dataFimRelacao = dataFimRelacao;
		this.ultimaAlteracao = ultimaAlteracao;
		this.imovel = imovel;
		this.clienteImovelFimRelacaoMotivo = clienteImovelFimRelacaoMotivo;
		this.cliente = cliente;
		this.clienteRelacaoTipo = clienteRelacaoTipo;
	}

	/** default constructor */
	public ClienteImovel() {

	}

	/** minimal constructor */
	public ClienteImovel(Date dataInicioRelacao, Imovel imovel,
							gcom.cadastro.cliente.ClienteImovelFimRelacaoMotivo clienteImovelFimRelacaoMotivo,
							gcom.cadastro.cliente.Cliente cliente, gcom.cadastro.cliente.ClienteRelacaoTipo clienteRelacaoTipo) {

		this.dataInicioRelacao = dataInicioRelacao;
		this.imovel = imovel;
		this.clienteImovelFimRelacaoMotivo = clienteImovelFimRelacaoMotivo;
		this.cliente = cliente;
		this.clienteRelacaoTipo = clienteRelacaoTipo;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Date getDataInicioRelacao(){

		return this.dataInicioRelacao;
	}

	public void setDataInicioRelacao(Date dataInicioRelacao){

		this.dataInicioRelacao = dataInicioRelacao;
	}

	public Date getDataFimRelacao(){

		return this.dataFimRelacao;
	}

	public void setDataFimRelacao(Date dataFimRelacao){

		this.dataFimRelacao = dataFimRelacao;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Imovel getImovel(){

		return this.imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public gcom.cadastro.cliente.ClienteImovelFimRelacaoMotivo getClienteImovelFimRelacaoMotivo(){

		return this.clienteImovelFimRelacaoMotivo;
	}

	public void setClienteImovelFimRelacaoMotivo(gcom.cadastro.cliente.ClienteImovelFimRelacaoMotivo clienteImovelFimRelacaoMotivo){

		this.clienteImovelFimRelacaoMotivo = clienteImovelFimRelacaoMotivo;
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

	// /**
	// * @return the imovelContaEnvio
	// */
	// public ImovelContaEnvio getImovelContaEnvio() {
	// return imovelContaEnvio;
	// }
	//
	// /**
	// * @param imovelContaEnvio
	// * the imovelContaEnvio to set
	// */
	// public void setImovelContaEnvio(ImovelContaEnvio imovelContaEnvio) {
	// this.imovelContaEnvio = imovelContaEnvio;
	// }

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public boolean equals(Object other){

		if((this == other)){
			return true;
		}
		if(!(other instanceof ClienteImovel)){
			return false;
		}
		ClienteImovel castOther = (ClienteImovel) other;

		return ((this.getCliente().getId().equals(castOther.getCliente().getId()))
						&& (this.getClienteRelacaoTipo().getId().equals(castOther.getClienteRelacaoTipo().getId()))
						&& (this.getImovel() != null ? this.getImovel().getId().equals(castOther.getImovel().getId()) : true) && (this
							.getDataInicioRelacao() != null ? this.getDataInicioRelacao().equals(castOther.getDataInicioRelacao()) : true));
	}

	/**
	 * Description of the Method
	 * 
	 * @return Description of the Return Value
	 */
	public int hashCode(){

		return new HashCodeBuilder().append(getCliente()).append(getClienteRelacaoTipo()).append(getDataInicioRelacao()).append(
						getUltimaAlteracao()).toHashCode();
	}

	public Short getIndicadorNomeConta(){

		return indicadorNomeConta;
	}

	public void setIndicadorNomeConta(Short indicadorNomeConta){

		this.indicadorNomeConta = indicadorNomeConta;
	}

	public Filtro retornaFiltro(){

		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.ID, this.getId()));
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_IMOVEL_FIM_RELACAO_MOTIVO);
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.IMOVEL);
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_RELACAO_TIPO);

		return filtroClienteImovel;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	@Override
	public void initializeLazy(){

		getCliente();
		if(getClienteRelacaoTipo() != null){
			getClienteRelacaoTipo().initializeLazy();
		}
		if(getClienteImovelFimRelacaoMotivo() != null){
			getClienteImovelFimRelacaoMotivo().initializeLazy();
		}
	}

	@Override
	public Filtro retornaFiltroRegistroOperacao(){

		Filtro filtro = super.retornaFiltroRegistroOperacao();
		// filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.IMOVEL);
		// filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_IMOVEL_FIM_RELACAO_MOTIVO);
		filtro.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
		return filtro;
	}

	public String getDescricao(){

		String ret = "";
		if(getCliente() != null){
			ret = getCliente().getNome();
		}
		return ret;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return this.getDescricao() + " (" + this.getClienteRelacaoTipo().getDescricao() + ")";
	}

	public Short getIndicadorEmissaoExtratoFaturamento(){

		return indicadorEmissaoExtratoFaturamento;
	}

	public void setIndicadorEmissaoExtratoFaturamento(Short indicadorEmissaoExtratoFaturamento){

		this.indicadorEmissaoExtratoFaturamento = indicadorEmissaoExtratoFaturamento;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"cliente.id"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Cliente"};
		return labels;
	}

	public int compareTo(ClienteImovel arg0){

		int x = 0;
		if(this.getCliente() == null && arg0.getCliente() != null){
			x = -1;
		}else if(this.getCliente() != null && arg0.getCliente() == null){
			x = 1;
		}else if(this.getCliente() != null && arg0.getCliente() != null){
			x = this.getCliente().getId().compareTo(arg0.getCliente().getId());
		}
		if(x == 0){
			if(this.getClienteRelacaoTipo() == null && arg0.getClienteRelacaoTipo() != null){
				x = -1;
			}else if(this.getClienteRelacaoTipo() != null && arg0.getClienteRelacaoTipo() == null){
				x = 1;
			}else if(this.getClienteRelacaoTipo() != null && arg0.getClienteRelacaoTipo() != null){
				x = this.getClienteRelacaoTipo().getId().compareTo(arg0.getClienteRelacaoTipo().getId());
			}
		}
		if(x == 0){
			if(this.getImovel() == null && arg0.getImovel() != null){
				x = 1;
			}else if(this.getImovel() != null && arg0.getImovel() == null){
				x = -1;
			}else if(this.getImovel() != null && arg0.getImovel() != null){
				x = this.getImovel().getId().compareTo(arg0.getImovel().getId());
			}
		}
		if(x == 0){
			if(this.getDataInicioRelacao() == null && this.getDataInicioRelacao() != null){
				x = 1;
			}else if(this.getDataInicioRelacao() == null && this.getDataInicioRelacao() != null){
				x = -1;
			}else if(this.getDataInicioRelacao() == null && this.getDataInicioRelacao() != null){
				if(this.getDataInicioRelacao().after(arg0.getDataInicioRelacao())){
					x = 1;
				}else if(this.getDataInicioRelacao().before(arg0.getDataInicioRelacao())){
					x = -1;
				}
			}
		}
		return x;
	}

}
