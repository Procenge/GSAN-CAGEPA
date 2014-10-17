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

package gcom.cadastro.unidadeoperacional;

import gcom.cadastro.endereco.Cep;
import gcom.cadastro.endereco.EnderecoReferencia;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.localidade.Localidade;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.operacional.SistemaAbastecimento;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class UnidadeOperacional
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	public static final int ATRIBUTOS_UNIDADE_OPERACIONAL_INSERIR = 1505;

	public static final int ATRIBUTOS_UNIDADE_OPERACIONAL_ATUALIZAR = 1507;

	public static final int ATRIBUTOS_UNIDADE_OPERACIONAL_REMOVER = 13931;

	/** identifier field */
	private Integer id;

	/** persistent field */
	private short codigoUnidadeOperacional;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_UNIDADE_OPERACIONAL_INSERIR, ATRIBUTOS_UNIDADE_OPERACIONAL_ATUALIZAR, ATRIBUTOS_UNIDADE_OPERACIONAL_REMOVER})
	private short indicadorAtivo;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_UNIDADE_OPERACIONAL_INSERIR, ATRIBUTOS_UNIDADE_OPERACIONAL_ATUALIZAR, ATRIBUTOS_UNIDADE_OPERACIONAL_REMOVER})
	private String descricao;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_UNIDADE_OPERACIONAL_INSERIR, ATRIBUTOS_UNIDADE_OPERACIONAL_ATUALIZAR, ATRIBUTOS_UNIDADE_OPERACIONAL_REMOVER})
	private String descricaoAbreviada;

	/** persistent field */
	@ControleAlteracao(value = FiltroUnidadeOperacional.SISTEMA_ABASTECIMENTO, funcionalidade = {ATRIBUTOS_UNIDADE_OPERACIONAL_INSERIR, ATRIBUTOS_UNIDADE_OPERACIONAL_ATUALIZAR, ATRIBUTOS_UNIDADE_OPERACIONAL_REMOVER})
	private gcom.operacional.SistemaAbastecimento sistemaAbastecimento;

	/** persistent field */
	@ControleAlteracao(value = FiltroUnidadeOperacional.LOCALIDADE, funcionalidade = {ATRIBUTOS_UNIDADE_OPERACIONAL_INSERIR, ATRIBUTOS_UNIDADE_OPERACIONAL_ATUALIZAR, ATRIBUTOS_UNIDADE_OPERACIONAL_REMOVER})
	private gcom.cadastro.localidade.Localidade localidade;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_UNIDADE_OPERACIONAL_INSERIR, ATRIBUTOS_UNIDADE_OPERACIONAL_ATUALIZAR, ATRIBUTOS_UNIDADE_OPERACIONAL_REMOVER})
	private String numeroImovel;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_UNIDADE_OPERACIONAL_INSERIR, ATRIBUTOS_UNIDADE_OPERACIONAL_ATUALIZAR, ATRIBUTOS_UNIDADE_OPERACIONAL_REMOVER})
	private String complementoEndereco;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_UNIDADE_OPERACIONAL_INSERIR, ATRIBUTOS_UNIDADE_OPERACIONAL_ATUALIZAR, ATRIBUTOS_UNIDADE_OPERACIONAL_REMOVER})
	private Integer telefone;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_UNIDADE_OPERACIONAL_INSERIR, ATRIBUTOS_UNIDADE_OPERACIONAL_ATUALIZAR, ATRIBUTOS_UNIDADE_OPERACIONAL_REMOVER})
	private Integer ramal;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_UNIDADE_OPERACIONAL_INSERIR, ATRIBUTOS_UNIDADE_OPERACIONAL_ATUALIZAR, ATRIBUTOS_UNIDADE_OPERACIONAL_REMOVER})
	private Integer fax;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_UNIDADE_OPERACIONAL_INSERIR, ATRIBUTOS_UNIDADE_OPERACIONAL_ATUALIZAR, ATRIBUTOS_UNIDADE_OPERACIONAL_REMOVER})
	private String email;

	/** persistent field */
	private gcom.cadastro.endereco.Logradouro logradouro;

	/** persistent field */
	@ControleAlteracao(value = FiltroUnidadeOperacional.BAIRRO, funcionalidade = {ATRIBUTOS_UNIDADE_OPERACIONAL_INSERIR, ATRIBUTOS_UNIDADE_OPERACIONAL_ATUALIZAR, ATRIBUTOS_UNIDADE_OPERACIONAL_REMOVER})
	private gcom.cadastro.geografico.Bairro bairro;

	/** persistent field */
	@ControleAlteracao(value = FiltroUnidadeOperacional.ENDERECOREFERENCIA, funcionalidade = {ATRIBUTOS_UNIDADE_OPERACIONAL_INSERIR, ATRIBUTOS_UNIDADE_OPERACIONAL_ATUALIZAR, ATRIBUTOS_UNIDADE_OPERACIONAL_REMOVER})
	private gcom.cadastro.endereco.EnderecoReferencia enderecoReferencia;

	/** persistent field */
	private gcom.cadastro.endereco.Cep cep;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_UNIDADE_OPERACIONAL_INSERIR, ATRIBUTOS_UNIDADE_OPERACIONAL_ATUALIZAR, ATRIBUTOS_UNIDADE_OPERACIONAL_REMOVER})
	private String descricaoTipoInstalacao;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_UNIDADE_OPERACIONAL_INSERIR, ATRIBUTOS_UNIDADE_OPERACIONAL_ATUALIZAR, ATRIBUTOS_UNIDADE_OPERACIONAL_REMOVER})
	private Integer numeroCota;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_UNIDADE_OPERACIONAL_INSERIR, ATRIBUTOS_UNIDADE_OPERACIONAL_ATUALIZAR, ATRIBUTOS_UNIDADE_OPERACIONAL_REMOVER})
	private Integer latitude;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_UNIDADE_OPERACIONAL_INSERIR, ATRIBUTOS_UNIDADE_OPERACIONAL_ATUALIZAR, ATRIBUTOS_UNIDADE_OPERACIONAL_REMOVER})
	private Integer longitude;

	/** persistent field */
	private Date ultimaAlteracao;

	public String getDescricaoComCodigo(){

		String descricaoComCodigo = null;
		if(this.getId().compareTo(10) == -1){
			descricaoComCodigo = "0" + getCodigoUnidadeOperacional() + " - " + getDescricao();
		}else{
			descricaoComCodigo = getCodigoUnidadeOperacional() + " - " + getDescricao();
		}

		return descricaoComCodigo;
	}

	/** default constructor */
	public UnidadeOperacional() {

	}

	/** full constructor */
	public UnidadeOperacional(Integer id, short codigoUnidadeOperacional, short indicadorAtivo, String descricao,
								String descricaoAbreviada, SistemaAbastecimento sistemaAbastecimento, Localidade localidade,
								String numeroImovel, String complementoEndereco, Integer telefone, Integer ramal, Integer fax,
								String email, Logradouro logradouro, Bairro bairro, EnderecoReferencia enderecoReferencia, Cep cep,
								String descricaoTipoInstalacao, Integer numeroCota, Integer latitude, Integer longitude,
								Date ultimaAlteracao) {

		super();
		this.id = id;
		this.codigoUnidadeOperacional = codigoUnidadeOperacional;
		this.indicadorAtivo = indicadorAtivo;
		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.sistemaAbastecimento = sistemaAbastecimento;
		this.localidade = localidade;
		this.numeroImovel = numeroImovel;
		this.complementoEndereco = complementoEndereco;
		this.telefone = telefone;
		this.ramal = ramal;
		this.fax = fax;
		this.email = email;
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.enderecoReferencia = enderecoReferencia;
		this.cep = cep;
		this.descricaoTipoInstalacao = descricaoTipoInstalacao;
		this.numeroCota = numeroCota;
		this.latitude = latitude;
		this.longitude = longitude;
		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public short getCodigoUnidadeOperacional(){

		return codigoUnidadeOperacional;
	}

	public void setCodigoUnidadeOperacional(short codigoUnidadeOperacional){

		this.codigoUnidadeOperacional = codigoUnidadeOperacional;
	}

	public short getIndicadorAtivo(){

		return indicadorAtivo;
	}

	public void setIndicadorAtivo(short indicadorAtivo){

		this.indicadorAtivo = indicadorAtivo;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getDescricaoAbreviada(){

		return descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	public gcom.operacional.SistemaAbastecimento getSistemaAbastecimento(){

		return sistemaAbastecimento;
	}

	public void setSistemaAbastecimento(gcom.operacional.SistemaAbastecimento sistemaAbastecimento){

		this.sistemaAbastecimento = sistemaAbastecimento;
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

	public gcom.cadastro.endereco.Logradouro getLogradouro(){

		return logradouro;
	}

	public void setLogradouro(gcom.cadastro.endereco.Logradouro logradouro){

		this.logradouro = logradouro;
	}

	public gcom.cadastro.geografico.Bairro getBairro(){

		return bairro;
	}

	public void setBairro(gcom.cadastro.geografico.Bairro bairro){

		this.bairro = bairro;
	}

	public gcom.cadastro.endereco.EnderecoReferencia getEnderecoReferencia(){

		return enderecoReferencia;
	}

	public void setEnderecoReferencia(gcom.cadastro.endereco.EnderecoReferencia enderecoReferencia){

		this.enderecoReferencia = enderecoReferencia;
	}

	public gcom.cadastro.endereco.Cep getCep(){

		return cep;
	}

	public void setCep(gcom.cadastro.endereco.Cep cep){

		this.cep = cep;
	}

	public String getDescricaoTipoInstalacao(){

		return descricaoTipoInstalacao;
	}

	public void setDescricaoTipoInstalacao(String descricaoTipoInstalacao){

		this.descricaoTipoInstalacao = descricaoTipoInstalacao;
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

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroUnidadeOperacional filtroUnidadeOperacional = new FiltroUnidadeOperacional();

		filtroUnidadeOperacional.adicionarCaminhoParaCarregamentoEntidade(FiltroUnidadeOperacional.SISTEMA_ABASTECIMENTO);
		filtroUnidadeOperacional.adicionarCaminhoParaCarregamentoEntidade(FiltroUnidadeOperacional.LOCALIDADE);
		filtroUnidadeOperacional.adicionarCaminhoParaCarregamentoEntidade(FiltroUnidadeOperacional.LOGRADOURO);
		filtroUnidadeOperacional.adicionarCaminhoParaCarregamentoEntidade(FiltroUnidadeOperacional.BAIRRO);
		filtroUnidadeOperacional.adicionarCaminhoParaCarregamentoEntidade(FiltroUnidadeOperacional.ENDERECOREFERENCIA);
		filtroUnidadeOperacional.adicionarCaminhoParaCarregamentoEntidade(FiltroUnidadeOperacional.CEP);
		filtroUnidadeOperacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOperacional.ID, this.getId()));
		return filtroUnidadeOperacional;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"descricao"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Descricao"};
		return labels;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
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
				endereco = endereco + " " + this.getCep().getLogradouro().trim();
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
				if(this.getBairro().getNome() != null){
					endereco = endereco + " - " + this.getBairro().getNome().trim();
				}

				if(this.getBairro().getMunicipio() != null && this.getBairro().getMunicipio().getId().intValue() != 0){
					if(this.getBairro().getMunicipio().getNome() != null){
						endereco = endereco + " " + this.getBairro().getMunicipio().getNome().trim();
					}
				}

				if(this.getBairro().getMunicipio().getUnidadeFederacao() != null
								&& this.getBairro().getMunicipio().getUnidadeFederacao().getId().intValue() != 0){
					if(this.getBairro().getMunicipio().getUnidadeFederacao().getSigla() != null){
						endereco = endereco + " " + this.getBairro().getMunicipio().getUnidadeFederacao().getSigla().trim();
					}
				}
			}

			if(this.getCep() != null && this.getCep() != null){
				// concatena o cep formatado do imóvel
				if(this.getCep().getCepFormatado() != null){
					endereco = endereco + " " + this.getCep().getCepFormatado().trim();
				}
			}

		}

		return endereco;
	}
}