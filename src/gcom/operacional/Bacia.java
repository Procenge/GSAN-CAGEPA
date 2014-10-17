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
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class Bacia
				extends ObjetoTransacao {

	public static final int ATRIBUTOS_BACIA_INSERIR = 1487;

	public static final int ATRIBUTOS_BACIA_ATUALIZAR = 1489;

	public static final int ATRIBUTOS_BACIA_REMOVER = 16521;

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_BACIA_INSERIR, ATRIBUTOS_BACIA_ATUALIZAR, ATRIBUTOS_BACIA_REMOVER})
	private String descricao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_BACIA_INSERIR, ATRIBUTOS_BACIA_ATUALIZAR, ATRIBUTOS_BACIA_REMOVER})
	private Short indicadorUso;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	@ControleAlteracao(value = FiltroBacia.SISTEMA_ESGOTO, funcionalidade = {ATRIBUTOS_BACIA_INSERIR, ATRIBUTOS_BACIA_ATUALIZAR, ATRIBUTOS_BACIA_REMOVER})
	private gcom.operacional.SistemaEsgoto sistemaEsgoto;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_BACIA_INSERIR, ATRIBUTOS_BACIA_ATUALIZAR, ATRIBUTOS_BACIA_REMOVER})
	private String descricaoAbreviada;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_BACIA_INSERIR, ATRIBUTOS_BACIA_ATUALIZAR, ATRIBUTOS_BACIA_REMOVER})
	private Integer codigo;

	@ControleAlteracao(value = FiltroBacia.SUBSISTEMA_ESGOTO, funcionalidade = {ATRIBUTOS_BACIA_INSERIR, ATRIBUTOS_BACIA_ATUALIZAR, ATRIBUTOS_BACIA_REMOVER})
	private SubsistemaEsgoto subsistemaEsgoto;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_BACIA_INSERIR, ATRIBUTOS_BACIA_ATUALIZAR, ATRIBUTOS_BACIA_REMOVER})
	private BigDecimal numeroUnidade;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_BACIA_INSERIR, ATRIBUTOS_BACIA_ATUALIZAR, ATRIBUTOS_BACIA_REMOVER})
	private BigDecimal numeroAducao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_BACIA_INSERIR, ATRIBUTOS_BACIA_ATUALIZAR, ATRIBUTOS_BACIA_REMOVER})
	private BigDecimal numeroDemandaEnergia;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_BACIA_INSERIR, ATRIBUTOS_BACIA_ATUALIZAR, ATRIBUTOS_BACIA_REMOVER})
	private String numeroImovel;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_BACIA_INSERIR, ATRIBUTOS_BACIA_ATUALIZAR, ATRIBUTOS_BACIA_REMOVER})
	private String complementoEndereco;

	@ControleAlteracao(value = FiltroBacia.ENDERECO_REFERENCIA, funcionalidade = {ATRIBUTOS_BACIA_INSERIR, ATRIBUTOS_BACIA_ATUALIZAR, ATRIBUTOS_BACIA_REMOVER})
	private EnderecoReferencia enderecoReferencia;

	@ControleAlteracao(value = FiltroBacia.LOGRADOURO_BAIRRO, funcionalidade = {ATRIBUTOS_BACIA_INSERIR, ATRIBUTOS_BACIA_ATUALIZAR, ATRIBUTOS_BACIA_REMOVER})
	private LogradouroBairro logradouroBairro;

	@ControleAlteracao(value = FiltroBacia.LOGRADOURO_CEP, funcionalidade = {ATRIBUTOS_BACIA_INSERIR, ATRIBUTOS_BACIA_ATUALIZAR, ATRIBUTOS_BACIA_REMOVER})
	private LogradouroCep logradouroCep;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_BACIA_INSERIR, ATRIBUTOS_BACIA_ATUALIZAR, ATRIBUTOS_BACIA_REMOVER})
	private Integer numeroCota;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_BACIA_INSERIR, ATRIBUTOS_BACIA_ATUALIZAR, ATRIBUTOS_BACIA_REMOVER})
	private Integer numeroLatitude;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_BACIA_INSERIR, ATRIBUTOS_BACIA_ATUALIZAR, ATRIBUTOS_BACIA_REMOVER})
	private Integer numeroLongitude;

	/** full constructor */
	public Bacia(String descricao, Short indicadorUso, Date ultimaAlteracao, gcom.operacional.SistemaEsgoto sistemaEsgoto) {

		this.descricao = descricao;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.sistemaEsgoto = sistemaEsgoto;
	}

	/** default constructor */
	public Bacia() {

	}

	/** minimal constructor */
	public Bacia(gcom.operacional.SistemaEsgoto sistemaEsgoto) {

		this.sistemaEsgoto = sistemaEsgoto;
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

	public gcom.operacional.SistemaEsgoto getSistemaEsgoto(){

		return this.sistemaEsgoto;
	}

	public void setSistemaEsgoto(gcom.operacional.SistemaEsgoto sistemaEsgoto){

		this.sistemaEsgoto = sistemaEsgoto;
	}

	/**
	 * @return the descricaoAbreviada
	 */
	public String getDescricaoAbreviada(){

		return descricaoAbreviada;
	}

	/**
	 * @param descricaoAbreviada
	 *            the descricaoAbreviada to set
	 */
	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	/**
	 * @return the codigo
	 */
	public Integer getCodigo(){

		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(Integer codigo){

		this.codigo = codigo;
	}

	/**
	 * @return the subsistemaEsgoto
	 */
	public SubsistemaEsgoto getSubsistemaEsgoto(){

		return subsistemaEsgoto;
	}

	/**
	 * @param subsistemaEsgoto
	 *            the subsistemaEsgoto to set
	 */
	public void setSubsistemaEsgoto(SubsistemaEsgoto subsistemaEsgoto){

		this.subsistemaEsgoto = subsistemaEsgoto;
	}

	/**
	 * @return the numeroUnidade
	 */
	public BigDecimal getNumeroUnidade(){

		return numeroUnidade;
	}

	/**
	 * @param numeroUnidade
	 *            the numeroUnidade to set
	 */
	public void setNumeroUnidade(BigDecimal numeroUnidade){

		this.numeroUnidade = numeroUnidade;
	}

	/**
	 * @return the numeroAducao
	 */
	public BigDecimal getNumeroAducao(){

		return numeroAducao;
	}

	/**
	 * @param numeroAducao
	 *            the numeroAducao to set
	 */
	public void setNumeroAducao(BigDecimal numeroAducao){

		this.numeroAducao = numeroAducao;
	}

	/**
	 * @return the numeroDemandaEnergia
	 */
	public BigDecimal getNumeroDemandaEnergia(){

		return numeroDemandaEnergia;
	}

	/**
	 * @param numeroDemandaEnergia
	 *            the numeroDemandaEnergia to set
	 */
	public void setNumeroDemandaEnergia(BigDecimal numeroDemandaEnergia){

		this.numeroDemandaEnergia = numeroDemandaEnergia;
	}

	/**
	 * @return the numeroImovel
	 */
	public String getNumeroImovel(){

		return numeroImovel;
	}

	/**
	 * @param numeroImovel
	 *            the numeroImovel to set
	 */
	public void setNumeroImovel(String numeroImovel){

		this.numeroImovel = numeroImovel;
	}

	/**
	 * @return the complementoEndereco
	 */
	public String getComplementoEndereco(){

		return complementoEndereco;
	}

	/**
	 * @param complementoEndereco
	 *            the complementoEndereco to set
	 */
	public void setComplementoEndereco(String complementoEndereco){

		this.complementoEndereco = complementoEndereco;
	}

	/**
	 * @return the enderecoReferencia
	 */
	public EnderecoReferencia getEnderecoReferencia(){

		return enderecoReferencia;
	}

	/**
	 * @param enderecoReferencia
	 *            the enderecoReferencia to set
	 */
	public void setEnderecoReferencia(EnderecoReferencia enderecoReferencia){

		this.enderecoReferencia = enderecoReferencia;
	}

	/**
	 * @return the logradouroBairro
	 */
	public LogradouroBairro getLogradouroBairro(){

		return logradouroBairro;
	}

	/**
	 * @param logradouroBairro
	 *            the logradouroBairro to set
	 */
	public void setLogradouroBairro(LogradouroBairro logradouroBairro){

		this.logradouroBairro = logradouroBairro;
	}

	/**
	 * @return the logradouroCep
	 */
	public LogradouroCep getLogradouroCep(){

		return logradouroCep;
	}

	/**
	 * @param logradouroCep
	 *            the logradouroCep to set
	 */
	public void setLogradouroCep(LogradouroCep logradouroCep){

		this.logradouroCep = logradouroCep;
	}

	/**
	 * @return the numeroCota
	 */
	public Integer getNumeroCota(){

		return numeroCota;
	}

	/**
	 * @param numeroCota
	 *            the numeroCota to set
	 */
	public void setNumeroCota(Integer numeroCota){

		this.numeroCota = numeroCota;
	}

	/**
	 * @return the numeroLatitude
	 */
	public Integer getNumeroLatitude(){

		return numeroLatitude;
	}

	/**
	 * @param numeroLatitude
	 *            the numeroLatitude to set
	 */
	public void setNumeroLatitude(Integer numeroLatitude){

		this.numeroLatitude = numeroLatitude;
	}

	/**
	 * @return the numeroLongitude
	 */
	public Integer getNumeroLongitude(){

		return numeroLongitude;
	}

	/**
	 * @param numeroLongitude
	 *            the numeroLongitude to set
	 */
	public void setNumeroLongitude(Integer numeroLongitude){

		this.numeroLongitude = numeroLongitude;
	}

	private String descricaoComCodigoEId;

	public String getDescricaoComCodigoEId(){

		if(this.getCodigo() != null && this.getCodigo().compareTo(10) == -1){
			descricaoComCodigoEId = "0" + (getCodigo() != null ? getCodigo() + " - " : "") + getDescricao();
		}else{
			descricaoComCodigoEId = (getCodigo() != null ? getCodigo() + " - " : "") + getDescricao();
		}

		return descricaoComCodigoEId;
	}

	public String getDescricaoComId(){

		String descricaoComId;
		if(this.getId().compareTo(10) == -1){
			descricaoComId = "0" + getId() + " - " + getDescricao();
		}else{
			descricaoComId = getId() + " - " + getDescricao();
		}

		return descricaoComId;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"id", "descricao", "subsistemaEsgoto.id"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"ID", "Descricao", "Subsist Esg."};
		return labels;
	}

	public Filtro retornaFiltro(){

		FiltroBacia filtroBacia = new FiltroBacia();
		filtroBacia.adicionarParametro(new ParametroSimples(FiltroBacia.ID, this.getId()));
		filtroBacia.adicionarCaminhoParaCarregamentoEntidade(FiltroBacia.SISTEMA_ESGOTO);
		filtroBacia.adicionarCaminhoParaCarregamentoEntidade(FiltroBacia.SUBSISTEMA_ESGOTO_SISTEMA_ESGOTO);

		return filtroBacia;
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

	/**
	 * Gets the endereco attribute of the Imovel object
	 * 
	 * @return The endereco value
	 */
	public String getEnderecoFormatado(){

		String endereco = null;

		// verifica se o logradouro do imovel é diferente de null
		if(this.getLogradouroCep() != null && this.getLogradouroCep().getLogradouro() != null
						&& !this.getLogradouroCep().getLogradouro().getId().equals(Integer.valueOf("0"))){

			// verifica se o logradouro tipo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTipo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTipo().equals("")){
				// concatena o logradouro tipo do imovel
				endereco = this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricao();
			}
			// verifica se o logradouro titulo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTitulo().equals("")){
				// concatena o logradouro titulo do imovel
				endereco = endereco + " " + this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricao();
			}

			// concatena o logradouro do imovel
			endereco = endereco + " " + this.getLogradouroCep().getLogradouro().getNome();

			if(this.getEnderecoReferencia() != null && !this.getEnderecoReferencia().equals("")){
				if(this.getEnderecoReferencia().getDescricao() != null && !this.getEnderecoReferencia().getDescricao().equals("")){
					endereco = endereco + " - " + this.getEnderecoReferencia().getDescricao();
				}
			}

			// concate o numero do imovel
			endereco = endereco + " - " + this.getNumeroImovel();

			if(this.getComplementoEndereco() != null && !this.getComplementoEndereco().equalsIgnoreCase("")){
				endereco = endereco + " - " + this.getComplementoEndereco();
			}

			if(this.getLogradouroBairro() != null && this.getLogradouroBairro().getBairro() != null
							&& this.getLogradouroBairro().getBairro().getId().intValue() != 0){
				endereco = endereco + " - " + this.getLogradouroBairro().getBairro().getNome();

				if(this.getLogradouroBairro().getBairro().getMunicipio() != null
								&& this.getLogradouroBairro().getBairro().getMunicipio().getId().intValue() != 0){
					endereco = endereco + " " + this.getLogradouroBairro().getBairro().getMunicipio().getNome();
				}

				if(this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao() != null
								&& this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getId().intValue() != 0){
					endereco = endereco + " " + this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getSigla();
				}
			}

			if(this.getLogradouroCep() != null && this.getLogradouroCep().getCep() != null){
				// concatena o cep formatado do imóvel
				endereco = endereco + " " + this.getLogradouroCep().getCep().getCepFormatado();
			}

		}

		return endereco;
	}

}
