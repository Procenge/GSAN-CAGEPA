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

package gcom.cadastro.localidade;

import gcom.arrecadacao.Concessionaria;
import gcom.cadastro.endereco.EnderecoReferencia;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.geografico.Municipio;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.micromedicao.hidrometro.HidrometroLocalArmazenagem;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.jms.Session;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class Localidade
				extends ObjetoTransacao {

	public static final int ATRIBUTOS_LOCALIDADE_INSERIR = 10;

	public static final int ATRIBUTOS_LOCALIDADE_ATUALIZAR = 12;

	public static final int ATRIBUTOS_LOCALIDADE_REMOVER = 11;

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private String descricao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private String numeroImovel;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private String complementoEndereco;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private String fone;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private String ramalfone;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private String fax;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private String email;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private Integer consumoGrandeUsuario;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private Short indicadorUso;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	@ControleAlteracao(value = FiltroLocalidade.LOCALIDADEPORTE, funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private gcom.cadastro.localidade.LocalidadePorte localidadePorte;

	// @ControleAlteracao(value = FiltroLocalidade.ID_ELO, funcionalidade =
	// {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private gcom.cadastro.localidade.Localidade localidade;

	@ControleAlteracao(value = FiltroLocalidade.GERENCIAREGIONAL, funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private gcom.cadastro.localidade.GerenciaRegional gerenciaRegional;

	@ControleAlteracao(value = FiltroLocalidade.ENDERECOREFERENCIA, funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private EnderecoReferencia enderecoReferencia;

	@ControleAlteracao(value = FiltroLocalidade.LOCALIDADECLASSE, funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private gcom.cadastro.localidade.LocalidadeClasse localidadeClasse;

	@ControleAlteracao(value = FiltroLocalidade.LOGRADOUROCEP, funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private LogradouroCep logradouroCep;

	@ControleAlteracao(value = FiltroLocalidade.LOGRADOUROBAIRRO, funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private LogradouroBairro logradouroBairro;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private Integer codigoICMS;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private String codigoCentroCusto;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private Short indicadorLocalidadeInformatizada;

	@ControleAlteracao(value = FiltroLocalidade.FUNCIONARIO, funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private Funcionario funcionario;

	@ControleAlteracao(value = FiltroLocalidade.HIDROMETROLOCALARMAZENAGEM, funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private HidrometroLocalArmazenagem hidrometroLocalArmazenagem;

	@ControleAlteracao(value = FiltroLocalidade.UNIDADENEGOCIO, funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private UnidadeNegocio unidadeNegocio;

	@ControleAlteracao(value = FiltroLocalidade.MUNICIPIO, funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private Municipio municipio;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private Integer codigoLocalidadeCEF;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private Integer indicardoAbastecimentoSuspenso;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private Integer indicadorAbastecimentoMinimo;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private Integer codigoLocalidadeContabil;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_LOCALIDADE_INSERIR, ATRIBUTOS_LOCALIDADE_ATUALIZAR, ATRIBUTOS_LOCALIDADE_REMOVER})
	private Integer numeroUltimaQuadraCadastrada;

	private String descricaoComId;

	private transient Integer qtidadeOs;

	private Set<Concessionaria> concessionarias;

	public Municipio getMunicipio(){

		return municipio;
	}

	public void setMunicipio(Municipio municipio){

		this.municipio = municipio;
	}

	public String getCodigoCentroCusto(){

		return codigoCentroCusto;
	}

	public void setCodigoCentroCusto(String codigoCentroCusto){

		this.codigoCentroCusto = codigoCentroCusto;
	}

	public Integer getCodigoICMS(){

		return codigoICMS;
	}

	public void setCodigoICMS(Integer codigoICMS){

		this.codigoICMS = codigoICMS;
	}

	/** full constructor */
	public Localidade(Integer id, String descricao, String numeroImovel, String complementoEndereco, String fone, String ramalfone,
						String fax, String email, Integer consumoGrandeUsuario, Short indicadorUso, Date ultimaAlteracao,
						gcom.cadastro.localidade.LocalidadePorte localidadePorte, gcom.cadastro.localidade.Localidade localidade,
						gcom.cadastro.localidade.GerenciaRegional gerenciaRegional, EnderecoReferencia enderecoReferencia,
						gcom.cadastro.localidade.LocalidadeClasse localidadeClasse, LogradouroCep logradouroCep,
						LogradouroBairro logradouroBairro) {

		this.id = id;
		this.descricao = descricao;
		this.numeroImovel = numeroImovel;
		this.complementoEndereco = complementoEndereco;
		this.fone = fone;
		this.ramalfone = ramalfone;
		this.fax = fax;
		this.email = email;
		this.consumoGrandeUsuario = consumoGrandeUsuario;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.localidadePorte = localidadePorte;
		this.localidade = localidade;
		this.gerenciaRegional = gerenciaRegional;
		this.enderecoReferencia = enderecoReferencia;
		this.localidadeClasse = localidadeClasse;
		this.logradouroCep = logradouroCep;
		this.logradouroBairro = logradouroBairro;
	}

	/** default constructor */
	public Localidade() {

	}

	// Construido por Sávio Luiz para setar o id no objeto
	public Localidade(Integer id) {

		this.id = id;
	}

	/** minimal constructor */
	public Localidade(Integer id, String descricao, String fone, String ramalfone, Integer consumoGrandeUsuario,
						gcom.cadastro.localidade.LocalidadePorte localidadePorte, gcom.cadastro.localidade.Localidade localidade,
						gcom.cadastro.localidade.GerenciaRegional gerenciaRegional, EnderecoReferencia enderecoReferencia,
						gcom.cadastro.localidade.LocalidadeClasse localidadeClasse, LogradouroCep logradouroCep,
						LogradouroBairro logradouroBairro) {

		this.id = id;
		this.descricao = descricao;
		this.fone = fone;
		this.ramalfone = ramalfone;
		this.consumoGrandeUsuario = consumoGrandeUsuario;
		this.localidadePorte = localidadePorte;
		this.localidade = localidade;
		this.gerenciaRegional = gerenciaRegional;
		this.enderecoReferencia = enderecoReferencia;
		this.localidadeClasse = localidadeClasse;
		this.logradouroCep = logradouroCep;
		this.logradouroBairro = logradouroBairro;
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

	public String getNumeroImovel(){

		return this.numeroImovel;
	}

	public void setNumeroImovel(String numeroImovel){

		this.numeroImovel = numeroImovel;
	}

	public String getComplementoEndereco(){

		return this.complementoEndereco;
	}

	public void setComplementoEndereco(String complementoEndereco){

		this.complementoEndereco = complementoEndereco;
	}

	public String getFone(){

		return this.fone;
	}

	public void setFone(String fone){

		this.fone = fone;
	}

	public String getRamalfone(){

		return this.ramalfone;
	}

	public void setRamalfone(String ramalfone){

		this.ramalfone = ramalfone;
	}

	public String getFax(){

		return this.fax;
	}

	public void setFax(String fax){

		this.fax = fax;
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

	public gcom.cadastro.localidade.LocalidadePorte getLocalidadePorte(){

		return this.localidadePorte;
	}

	public void setLocalidadePorte(gcom.cadastro.localidade.LocalidadePorte localidadePorte){

		this.localidadePorte = localidadePorte;
	}

	public gcom.cadastro.localidade.Localidade getLocalidade(){

		return this.localidade;
	}

	public void setLocalidade(gcom.cadastro.localidade.Localidade localidade){

		this.localidade = localidade;
	}

	public gcom.cadastro.localidade.GerenciaRegional getGerenciaRegional(){

		return this.gerenciaRegional;
	}

	public void setGerenciaRegional(gcom.cadastro.localidade.GerenciaRegional gerenciaRegional){

		this.gerenciaRegional = gerenciaRegional;
	}

	public EnderecoReferencia getEnderecoReferencia(){

		return this.enderecoReferencia;
	}

	public void setEnderecoReferencia(EnderecoReferencia enderecoReferencia){

		this.enderecoReferencia = enderecoReferencia;
	}

	public gcom.cadastro.localidade.LocalidadeClasse getLocalidadeClasse(){

		return this.localidadeClasse;
	}

	public void setLocalidadeClasse(gcom.cadastro.localidade.LocalidadeClasse localidadeClasse){

		this.localidadeClasse = localidadeClasse;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param session
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public boolean onSave(Session session){

		if(this.localidade == null){
			Localidade localidadeElo = new Localidade();

			localidadeElo.setId(this.id);
			this.localidade = localidadeElo;
		}
		return false;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param session
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public boolean onUpdate(Session session){

		return false;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param session
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public boolean onDelete(Session session){

		return false;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param session
	 *            Descrição do parâmetro
	 * @param serializable
	 *            Descrição do parâmetro
	 */
	public void onLoad(Session session, Serializable serializable){

	}

	public LogradouroBairro getLogradouroBairro(){

		return logradouroBairro;
	}

	public void setLogradouroBairro(LogradouroBairro logradouroBairro){

		this.logradouroBairro = logradouroBairro;
	}

	public LogradouroCep getLogradouroCep(){

		return logradouroCep;
	}

	public void setLogradouroCep(LogradouroCep logradouroCep){

		this.logradouroCep = logradouroCep;
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
						&& !this.getLogradouroCep().getLogradouro().getId().equals(new Integer("0"))){

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

	/**
	 * @return Retorna o campo unidadeNegocio.
	 */
	public UnidadeNegocio getUnidadeNegocio(){

		return unidadeNegocio;
	}

	/**
	 * @param unidadeNegocio
	 *            O unidadeNegocio a ser setado.
	 */
	public void setUnidadeNegocio(UnidadeNegocio unidadeNegocio){

		this.unidadeNegocio = unidadeNegocio;
	}

	public Integer getConsumoGrandeUsuario(){

		return consumoGrandeUsuario;
	}

	public void setConsumoGrandeUsuario(Integer consumoGrandeUsuario){

		this.consumoGrandeUsuario = consumoGrandeUsuario;
	}

	public String getEmail(){

		return email;
	}

	public void setEmail(String email){

		this.email = email;
	}

	public Funcionario getFuncionario(){

		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario){

		this.funcionario = funcionario;
	}

	public Short getIndicadorLocalidadeInformatizada(){

		return indicadorLocalidadeInformatizada;
	}

	public void setIndicadorLocalidadeInformatizada(Short indicadorLocalidadeInformatizada){

		this.indicadorLocalidadeInformatizada = indicadorLocalidadeInformatizada;
	}

	public HidrometroLocalArmazenagem getHidrometroLocalArmazenagem(){

		return hidrometroLocalArmazenagem;
	}

	public void setHidrometroLocalArmazenagem(HidrometroLocalArmazenagem hidrometroLocalArmazenagem){

		this.hidrometroLocalArmazenagem = hidrometroLocalArmazenagem;
	}

	/**
	 * @return the codigoLocalidadeCEF
	 */
	public Integer getCodigoLocalidadeCEF(){

		return codigoLocalidadeCEF;
	}

	/**
	 * @param codigoLocalidadeCEF
	 *            the codigoLocalidadeCEF to set
	 */
	public void setCodigoLocalidadeCEF(Integer codigoLocalidadeCEF){

		this.codigoLocalidadeCEF = codigoLocalidadeCEF;
	}

	/**
	 * @return the indicardoAbastecimentoSuspenso
	 */
	public Integer getIndicardoAbastecimentoSuspenso(){

		return indicardoAbastecimentoSuspenso;
	}

	/**
	 * @param indicardoAbastecimentoSuspenso
	 *            the indicardoAbastecimentoSuspenso to set
	 */
	public void setIndicardoAbastecimentoSuspenso(Integer indicardoAbastecimentoSuspenso){

		this.indicardoAbastecimentoSuspenso = indicardoAbastecimentoSuspenso;
	}

	/**
	 * @return the indicadorAbastecimentoMinimo
	 */
	public Integer getIndicadorAbastecimentoMinimo(){

		return indicadorAbastecimentoMinimo;
	}

	/**
	 * @param indicadorAbastecimentoMinimo
	 *            the indicadorAbastecimentoMinimo to set
	 */
	public void setIndicadorAbastecimentoMinimo(Integer indicadorAbastecimentoMinimo){

		this.indicadorAbastecimentoMinimo = indicadorAbastecimentoMinimo;
	}

	/**
	 * @return the codigoLocalidadeContabil
	 */
	public Integer getCodigoLocalidadeContabil(){

		return codigoLocalidadeContabil;
	}

	/**
	 * @param codigoLocalidadeContabil
	 *            the codigoLocalidadeContabil to set
	 */
	public void setCodigoLocalidadeContabil(Integer codigoLocalidadeContabil){

		this.codigoLocalidadeContabil = codigoLocalidadeContabil;
	}

	/**
	 * @return the numeroUltimaQuadraCadastrada
	 */
	public Integer getNumeroUltimaQuadraCadastrada(){

		return numeroUltimaQuadraCadastrada;
	}

	/**
	 * @param numeroUltimaQuadraCadastrada
	 *            the numeroUltimaQuadraCadastrada to set
	 */
	public void setNumeroUltimaQuadraCadastrada(Integer numeroUltimaQuadraCadastrada){

		this.numeroUltimaQuadraCadastrada = numeroUltimaQuadraCadastrada;
	}

	public String getDescricaoComId(){

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

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"descricao", "gerenciaRegional.id", "municipio.id"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Desc", "GerReg", "Munic"};
		return labels;
	}

	public Filtro retornaFiltro(){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.LOCALIDADEPORTE);
		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.ID_ELO);
		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.GERENCIAREGIONAL);
		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.ENDERECOREFERENCIA);
		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.LOCALIDADECLASSE);
		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.LOGRADOUROCEP);
		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.LOGRADOUROBAIRRO);
		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.UNIDADENEGOCIO);
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, this.getId()));
		return filtroLocalidade;
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

		return this.id + " - " + this.descricao;
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

	public Set<Concessionaria> getConcessionarias(){

		return concessionarias;
	}

	public void setConcessionarias(Set<Concessionaria> concessionarias){

		this.concessionarias = concessionarias;
	}

}
