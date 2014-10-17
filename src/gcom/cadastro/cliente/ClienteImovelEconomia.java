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
import gcom.cadastro.imovel.ImovelEconomia;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class ClienteImovelEconomia
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private Date dataInicioRelacao;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private Date dataFimRelacao;

	@ControleAlteracao(funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private Date ultimaAlteracao;

	@ControleAlteracao(value = FiltroClienteImovelEconomia.FIM_RELACAO_MOTIVO, funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private gcom.cadastro.cliente.ClienteImovelFimRelacaoMotivo clienteImovelFimRelacaoMotivo;

	@ControleAlteracao(value = FiltroClienteImovelEconomia.CLIENTE, funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private gcom.cadastro.cliente.Cliente cliente;

	@ControleAlteracao(value = FiltroClienteImovelEconomia.CLIENTE_RELACAO_TIPO, funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private gcom.cadastro.cliente.ClienteRelacaoTipo clienteRelacaoTipo;

	@ControleAlteracao(value = FiltroClienteImovelEconomia.IMOVEL_ECONOMIA, funcionalidade = {Imovel.ATRIBUTOS_IMOVEL_INSERIR, Imovel.ATRIBUTOS_IMOVEL_ATUALIZAR, Imovel.ATRIBUTOS_IMOVEL_REMOVER})
	private ImovelEconomia imovelEconomia;

	/** full constructor */
	public ClienteImovelEconomia(Date dataInicioRelacao, Date dataFimRelacao, Date ultimaAlteracao,
									gcom.cadastro.cliente.ClienteImovelFimRelacaoMotivo clienteImovelFimRelacaoMotivo,
									gcom.cadastro.cliente.Cliente cliente, gcom.cadastro.cliente.ClienteRelacaoTipo clienteRelacaoTipo,
									ImovelEconomia imovelEconomia) {

		this.dataInicioRelacao = dataInicioRelacao;
		this.dataFimRelacao = dataFimRelacao;
		this.ultimaAlteracao = ultimaAlteracao;
		this.clienteImovelFimRelacaoMotivo = clienteImovelFimRelacaoMotivo;
		this.cliente = cliente;
		this.clienteRelacaoTipo = clienteRelacaoTipo;
		this.imovelEconomia = imovelEconomia;
	}

	/** default constructor */
	public ClienteImovelEconomia() {

	}

	/** minimal constructor */
	public ClienteImovelEconomia(Date dataInicioRelacao, gcom.cadastro.cliente.ClienteImovelFimRelacaoMotivo clienteImovelFimRelacaoMotivo,
									gcom.cadastro.cliente.Cliente cliente, gcom.cadastro.cliente.ClienteRelacaoTipo clienteRelacaoTipo,
									ImovelEconomia imovelEconomia) {

		this.dataInicioRelacao = dataInicioRelacao;
		this.clienteImovelFimRelacaoMotivo = clienteImovelFimRelacaoMotivo;
		this.cliente = cliente;
		this.clienteRelacaoTipo = clienteRelacaoTipo;
		this.imovelEconomia = imovelEconomia;
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

	public ImovelEconomia getImovelEconomia(){

		return this.imovelEconomia;
	}

	public void setImovelEconomia(ImovelEconomia imovelEconomia){

		this.imovelEconomia = imovelEconomia;
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param other
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public boolean equals(Object other){

		if((this == other)){
			return true;
		}
		if(!(other instanceof ClienteImovelEconomia)){
			return false;
		}
		ClienteImovelEconomia castOther = (ClienteImovelEconomia) other;

		return ((this.getCliente().getId().equals(castOther.getCliente().getId())) && (this.getClienteRelacaoTipo().getId()
						.equals(castOther.getClienteRelacaoTipo().getId())));
	}

	/**
	 * Description of the Method
	 * 
	 * @return Description of the Return Value
	 */
	public int hashCode(){

		if(this.ultimaAlteracao != null) return this.ultimaAlteracao.hashCode();
		else return super.hashCode();
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {""};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {""};
		return labels;
	}

	public Filtro retornaFiltro(){

		FiltroClienteImovelEconomia filtroClienteImovelEconomia = new FiltroClienteImovelEconomia();
		filtroClienteImovelEconomia.adicionarParametro(new ParametroSimples(FiltroClienteImovelEconomia.ID, this.getId()));
		filtroClienteImovelEconomia.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovelEconomia.CLIENTE);
		filtroClienteImovelEconomia.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovelEconomia.IMOVEL);
		filtroClienteImovelEconomia.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovelEconomia.CLIENTE_RELACAO_TIPO);
		filtroClienteImovelEconomia.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovelEconomia.IMOVEL_ECONOMIA);
		filtroClienteImovelEconomia
						.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovelEconomia.IMOVEL_ECONOMIEA_AREA_CONSTRUIDA_FAIXA);
		filtroClienteImovelEconomia.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovelEconomia.IMOVEL_PERFIL);
		filtroClienteImovelEconomia.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovelEconomia.FIM_RELACAO_MOTIVO);

		return filtroClienteImovelEconomia;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}
}
