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

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class ClienteFone
				extends ObjetoTransacao
				implements Comparable<ClienteFone> {

	private static final long serialVersionUID = 1L;

	public static final int ATRIBUTOS_CLIENTE_FONE_INSERIR = 189;

	public static final int ATRIBUTOS_CLIENTE_FONE_REMOVER = 345;

	public static final int ATRIBUTOS_CLIENTE_FONE_ATUALIZAR = 334;

	/** identifier field */
	private Integer id;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_FONE_INSERIR, ATRIBUTOS_CLIENTE_FONE_ATUALIZAR, ATRIBUTOS_CLIENTE_FONE_REMOVER})
	private String ddd;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_FONE_INSERIR, ATRIBUTOS_CLIENTE_FONE_ATUALIZAR, ATRIBUTOS_CLIENTE_FONE_REMOVER})
	private String telefone;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_FONE_INSERIR, ATRIBUTOS_CLIENTE_FONE_ATUALIZAR, ATRIBUTOS_CLIENTE_FONE_REMOVER})
	private String ramal;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_FONE_INSERIR, ATRIBUTOS_CLIENTE_FONE_ATUALIZAR, ATRIBUTOS_CLIENTE_FONE_REMOVER})
	private Short indicadorTelefonePadrao;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	@ControleAlteracao(value = FiltroClienteFone.FONE_TIPO, funcionalidade = {ATRIBUTOS_CLIENTE_FONE_INSERIR, ATRIBUTOS_CLIENTE_FONE_ATUALIZAR, ATRIBUTOS_CLIENTE_FONE_REMOVER})
	private gcom.cadastro.cliente.FoneTipo foneTipo;

	@ControleAlteracao(value = FiltroClienteFone.CLIENTE, funcionalidade = {ATRIBUTOS_CLIENTE_FONE_INSERIR, ATRIBUTOS_CLIENTE_FONE_ATUALIZAR, ATRIBUTOS_CLIENTE_FONE_REMOVER})
	private gcom.cadastro.cliente.Cliente cliente;

	public static final Short INDICADOR_FONE_PADRAO = new Short("1");

	/** full constructor */
	public ClienteFone(String ddd, String telefone, String ramal, Short indicadorTelefonePadrao, Date ultimaAlteracao,
						gcom.cadastro.cliente.FoneTipo foneTipo, gcom.cadastro.cliente.Cliente cliente) {

		this.ddd = ddd;
		this.telefone = telefone;
		this.ramal = ramal;
		this.indicadorTelefonePadrao = indicadorTelefonePadrao;
		this.ultimaAlteracao = ultimaAlteracao;
		this.foneTipo = foneTipo;
		this.cliente = cliente;
	}

	/** default constructor */
	public ClienteFone() {

	}

	/** minimal constructor */
	public ClienteFone(gcom.cadastro.cliente.FoneTipo foneTipo, gcom.cadastro.cliente.Cliente cliente) {

		this.foneTipo = foneTipo;
		this.cliente = cliente;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getDdd(){

		return this.ddd;
	}

	public void setDdd(String ddd){

		this.ddd = ddd;
	}

	public String getTelefone(){

		return this.telefone;
	}

	public void setTelefone(String telefone){

		this.telefone = telefone;
	}

	public String getRamal(){

		return this.ramal;
	}

	public void setRamal(String ramal){

		this.ramal = ramal;
	}

	public Short getIndicadorTelefonePadrao(){

		return this.indicadorTelefonePadrao;
	}

	public void setIndicadorTelefonePadrao(Short indicadorTelefonePadrao){

		this.indicadorTelefonePadrao = indicadorTelefonePadrao;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public gcom.cadastro.cliente.FoneTipo getFoneTipo(){

		return this.foneTipo;
	}

	public void setFoneTipo(gcom.cadastro.cliente.FoneTipo foneTipo){

		this.foneTipo = foneTipo;
	}

	public gcom.cadastro.cliente.Cliente getCliente(){

		return this.cliente;
	}

	public void setCliente(gcom.cadastro.cliente.Cliente cliente){

		this.cliente = cliente;
	}

	/**
	 * Retorna o valor de dddTelefone
	 * 
	 * @return O valor de dddTelefone
	 */
	public String getDddTelefone(){

		if(this.ddd != null){
			return "(" + this.ddd + ")" + this.telefone;
		}else{
			return this.telefone;
		}
	}

	public String getDddTelefoneFormatado(){

		if(this.ddd != null){
			return "(" + this.ddd + ") " + Util.formatarFone(this.telefone);
		}else{
			return this.telefone;
		}
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
		if(!(other instanceof ClienteFone)){
			return false;
		}
		ClienteFone castOther = (ClienteFone) other;

		return new EqualsBuilder().append(this.getDdd(), castOther.getDdd()).append(this.getTelefone(), castOther.getTelefone()).append(
						this.getRamal(), castOther.getRamal()).isEquals();

	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"ddd", "telefone"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"DDD", "Telefone"};
		return labels;
	}

	public Filtro retornaFiltro(){

		FiltroClienteFone filtroClienteFone = new FiltroClienteFone();
		filtroClienteFone.adicionarParametro(new ParametroSimples(FiltroClienteFone.ID, this.getId()));
		filtroClienteFone.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteFone.CLIENTE);
		filtroClienteFone.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteFone.FONE_TIPO);
		return filtroClienteFone;
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

		return getId() + " " + getDdd() + " " + getTelefone();
	}

	public int compareTo(ClienteFone o){

		int retorno = ddd.compareTo(o.getDdd());
		if(retorno == 0) retorno = telefone.compareTo(o.getTelefone());
		if(retorno == 0 && Util.isNaoNuloBrancoZero(ramal)) retorno = ramal.compareTo(o.getRamal());
		return retorno;
	}
}
