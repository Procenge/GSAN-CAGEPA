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

package gcom.operacional;

import gcom.cadastro.endereco.EnderecoReferencia;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.cadastro.localidade.Localidade;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class DistritoOperacional
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private String descricao;

	/** nullable persistent field */
	private String descricaoAbreviada;

	/** nullable persistent field */
	private Short indicadorUso;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private gcom.cadastro.localidade.Localidade localidade;

	/** persistent field */
	private String numeroImovel;

	/** persistent field */
	private String complementoEndereco;

	/** persistent field */
	private Integer telefone;

	/** persistent field */
	private Integer ramal;

	/** persistent field */
	private Integer fax;

	/** persistent field */
	private String email;

	/** persistent field */
	private gcom.cadastro.endereco.LogradouroBairro bairro;

	/** persistent field */
	private gcom.cadastro.endereco.EnderecoReferencia enderecoReferencia;

	/** persistent field */
	private gcom.cadastro.endereco.LogradouroCep cep;

	/** persistent field */
	private gcom.operacional.TipoUnidadeOperacional tipoUnidadeOperacional;

	/** persistent field */
	private Integer numeroCota;

	/** persistent field */
	private Integer latitude;

	/** persistent field */
	private Integer longitude;

	private gcom.operacional.SistemaAbastecimento sistemaAbastecimento;

	private Set<gcom.operacional.DadoDistritoOperacional> dadosDistritoOperacional;

	private transient Integer qtidadeOs;

	/** persistent field */
	// private gcom.operacional.SistemaAbastecimento sistemaAbastecimento;

	/** full constructor */
	public DistritoOperacional(String descricao, String descricaoAbreviada, Short indicadorUso, Date ultimaAlteracao,
								Localidade localidade, String numeroImovel, String complementoEndereco, Integer telefone, Integer ramal,
								Integer fax, String email, LogradouroBairro bairro, EnderecoReferencia enderecoReferencia,
								LogradouroCep cep, TipoUnidadeOperacional tipoUnidadeOperacional, Integer numeroCota, Integer latitude,
								Integer longitude, SistemaAbastecimento sistemaAbastecimento,
								Set<DadoDistritoOperacional> dadosDistritoOperacional) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.localidade = localidade;
		this.numeroImovel = numeroImovel;
		this.complementoEndereco = complementoEndereco;
		this.telefone = telefone;
		this.ramal = ramal;
		this.fax = fax;
		this.email = email;
		this.bairro = bairro;
		this.enderecoReferencia = enderecoReferencia;
		this.cep = cep;
		this.tipoUnidadeOperacional = tipoUnidadeOperacional;
		this.numeroCota = numeroCota;
		this.latitude = latitude;
		this.longitude = longitude;
		this.sistemaAbastecimento = sistemaAbastecimento;
		this.dadosDistritoOperacional = dadosDistritoOperacional;
	}

	/** default constructor */
	public DistritoOperacional() {

	}

	/** minimal constructor */
	public DistritoOperacional(gcom.operacional.SistemaAbastecimento sistemaAbastecimento) {

		// this.sistemaAbastecimento = sistemaAbastecimento;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getDescricao(){

		return this.descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getDescricaoAbreviada(){

		return this.descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	public Short getIndicadorUso(){

		return this.indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String getIdComDescricao(){

		if(this.getId().compareTo(10) == -1){
			return "000" + getId() + " - " + (!Util.isVazioOuBranco(getDescricao()) ? getDescricao() : "");

		}else if(this.getId().compareTo(100) == -1){
			return "00" + getId() + " - " + (!Util.isVazioOuBranco(getDescricao()) ? getDescricao() : "");
		}else if(this.getId().compareTo(1000) == -1){
			return "0" + getId() + " - " + (!Util.isVazioOuBranco(getDescricao()) ? getDescricao() : "");
		}else{
			return getId() + " - " + (!Util.isVazioOuBranco(getDescricao()) ? getDescricao() : "");
		}
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public gcom.cadastro.endereco.LogradouroBairro getBairro(){

		return bairro;
	}

	public void setBairro(gcom.cadastro.endereco.LogradouroBairro bairro){

		this.bairro = bairro;
	}

	public gcom.cadastro.endereco.EnderecoReferencia getEnderecoReferencia(){

		return enderecoReferencia;
	}

	public void setEnderecoReferencia(gcom.cadastro.endereco.EnderecoReferencia enderecoReferencia){

		this.enderecoReferencia = enderecoReferencia;
	}

	public gcom.cadastro.endereco.LogradouroCep getCep(){

		return cep;
	}

	public void setCep(gcom.cadastro.endereco.LogradouroCep cep){

		this.cep = cep;
	}

	public TipoUnidadeOperacional getTipoUnidadeOperacional(){

		return tipoUnidadeOperacional;
	}

	public void setTipoUnidadeOperacional(TipoUnidadeOperacional tipoUnidadeOperacional){

		this.tipoUnidadeOperacional = tipoUnidadeOperacional;
	}

	public Integer getNumeroCota(){

		return numeroCota;
	}

	public void setNumeroCota(Integer numeroCota){

		this.numeroCota = numeroCota;
	}

	public Integer getLatitude(){

		return latitude;
	}

	public void setLatitude(Integer latitude){

		this.latitude = latitude;
	}

	public Integer getLongitude(){

		return longitude;
	}

	public void setLongitude(Integer longitude){

		this.longitude = longitude;
	}

	public gcom.cadastro.localidade.Localidade getLocalidade(){

		return localidade;
	}

	public void setLocalidade(gcom.cadastro.localidade.Localidade localidade){

		this.localidade = localidade;
	}

	public String getNumeroImovel(){

		return numeroImovel;
	}

	public void setNumeroImovel(String numeroImovel){

		this.numeroImovel = numeroImovel;
	}

	public String getComplementoEndereco(){

		return complementoEndereco;
	}

	public void setComplementoEndereco(String complementoEndereco){

		this.complementoEndereco = complementoEndereco;
	}

	public Integer getTelefone(){

		return telefone;
	}

	public void setTelefone(Integer telefone){

		this.telefone = telefone;
	}

	public Integer getRamal(){

		return ramal;
	}

	public void setRamal(Integer ramal){

		this.ramal = ramal;
	}

	public Integer getFax(){

		return fax;
	}

	public void setFax(Integer fax){

		this.fax = fax;
	}

	public String getEmail(){

		return email;
	}

	public void setEmail(String email){

		this.email = email;
	}

	public gcom.operacional.SistemaAbastecimento getSistemaAbastecimento(){

		return sistemaAbastecimento;
	}

	public void setSistemaAbastecimento(gcom.operacional.SistemaAbastecimento sistemaAbastecimento){

		this.sistemaAbastecimento = sistemaAbastecimento;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroDistritoOperacional filtroDistritoOperacional = new FiltroDistritoOperacional();

		filtroDistritoOperacional.adicionarCaminhoParaCarregamentoEntidade(FiltroDistritoOperacional.SISTEMA_ABASTECIMENTO);
		filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.ID, this.getId()));
		return filtroDistritoOperacional;
	}

	/**
	 * Gets the endereco attribute of the Imovel object
	 * 
	 * @return The endereco value
	 */
	public String getEnderecoFormatado(){

		String endereco = "";

		// verifica se o logradouro do imovel é diferente de null
		if(this.getCep() != null && this.getCep().getLogradouro() != null){

			// concatena o logradouro do imovel
			if(this.getCep().getLogradouro() != null){
				endereco = endereco + " " + this.getCep().getCep().getLogradouro().trim();
			}

			if(this.getEnderecoReferencia() != null && !this.getEnderecoReferencia().equals("")){
				if(this.getEnderecoReferencia().getDescricao() != null && !this.getEnderecoReferencia().getDescricao().equals("")){
					endereco = endereco + " - " + this.getEnderecoReferencia().getDescricao().trim();
				}
			}
			if(this.getNumeroImovel() != null && !this.getNumeroImovel().equals("")){
				// concate o numero do imovel
				endereco = endereco + " - " + this.getNumeroImovel() + "".trim();
			}

			if(this.getComplementoEndereco() != null && !this.getComplementoEndereco().equalsIgnoreCase("")){
				endereco = endereco + " - " + this.getComplementoEndereco().trim();
			}

			if(this.getBairro() != null && this.getBairro() != null && this.getBairro().getId().intValue() != 0){
				if(this.getBairro().getBairro().getNome() != null){
					endereco = endereco + " - " + this.getBairro().getBairro().getNome().trim();
				}

				if(this.getBairro().getBairro().getMunicipio() != null
								&& this.getBairro().getBairro().getMunicipio().getId().intValue() != 0){
					if(this.getBairro().getBairro().getMunicipio().getNome() != null){
						endereco = endereco + " " + this.getBairro().getBairro().getMunicipio().getNome().trim();
					}
				}

				if(this.getBairro().getBairro().getMunicipio().getUnidadeFederacao() != null
								&& this.getBairro().getBairro().getMunicipio().getUnidadeFederacao().getId().intValue() != 0){
					if(this.getBairro().getBairro().getMunicipio().getUnidadeFederacao().getSigla() != null){
						endereco = endereco + " " + this.getBairro().getBairro().getMunicipio().getUnidadeFederacao().getSigla().trim();
					}
				}
			}

			if(this.getCep() != null && this.getCep() != null){
				// concatena o cep formatado do imóvel
				if(this.getCep().getCep().getCepFormatado() != null){
					endereco = endereco + " " + this.getCep().getCep().getCepFormatado().trim();
				}
			}

		}

		return endereco;
	}

	public Set<gcom.operacional.DadoDistritoOperacional> getDadosDistritoOperacional(){

		return dadosDistritoOperacional;
	}

	public void setDadosDistritoOperacional(Set<gcom.operacional.DadoDistritoOperacional> dadosDistritoOperacional){

		this.dadosDistritoOperacional = dadosDistritoOperacional;
	}

	public String getDescricaoComId(){

		String descricaoComId = null;
		if(this.getId().compareTo(10) == -1){
			descricaoComId = "000" + getId() + " - " + getDescricao();
		}else if(this.getId().compareTo(100) == -1){
			descricaoComId = "00" + getId() + " - " + getDescricao();
		}else if(this.getId().compareTo(1000) == -1){
			descricaoComId = "0" + getId() + " - " + getDescricao();
		}else{
			descricaoComId = getId() + " - " + getDescricao();
		}

		return descricaoComId;
	}

	public Integer getQtidadeOs(){

		return qtidadeOs;
	}

	public void setQtidadeOs(Integer qtidadeOs){

		this.qtidadeOs = qtidadeOs;
	}

	public String getDescricaoQtidadeOs(){

		StringBuilder st = new StringBuilder();
		st.append(getDescricao());
		st.append(" - ");

		if(getQtidadeOs() == null){
			st.append(0);
		}else{
			st.append(getQtidadeOs());
		}

		return st.toString();
	}

}
