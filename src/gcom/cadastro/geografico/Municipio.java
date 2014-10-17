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

package gcom.cadastro.geografico;

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class Municipio
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	public static final int ATRIBUTOS_MUNICIPIO_INSERIR = 726;

	public static final int ATRIBUTOS_MUNICIPIO_REMOVER = 735;

	public static final int ATRIBUTOS_MUNICIPIO_ATUALIZAR = 739;

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_MUNICIPIO_INSERIR, ATRIBUTOS_MUNICIPIO_ATUALIZAR, ATRIBUTOS_MUNICIPIO_REMOVER})
	private String nome;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_MUNICIPIO_INSERIR, ATRIBUTOS_MUNICIPIO_ATUALIZAR, ATRIBUTOS_MUNICIPIO_REMOVER})
	private Integer cepInicio;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_MUNICIPIO_INSERIR, ATRIBUTOS_MUNICIPIO_ATUALIZAR, ATRIBUTOS_MUNICIPIO_REMOVER})
	private Integer cepFim;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_MUNICIPIO_INSERIR, ATRIBUTOS_MUNICIPIO_ATUALIZAR, ATRIBUTOS_MUNICIPIO_REMOVER})
	private Short ddd;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_MUNICIPIO_INSERIR, ATRIBUTOS_MUNICIPIO_ATUALIZAR, ATRIBUTOS_MUNICIPIO_REMOVER})
	private Short indicadorUso;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	@ControleAlteracao(value = FiltroMunicipio.MICRORREGICAO, funcionalidade = {ATRIBUTOS_MUNICIPIO_INSERIR, ATRIBUTOS_MUNICIPIO_ATUALIZAR, ATRIBUTOS_MUNICIPIO_REMOVER})
	private gcom.cadastro.geografico.Microrregiao microrregiao;

	/** persistent field */
	@ControleAlteracao(value = FiltroMunicipio.REGIAO_DESENVOLVOMENTO, funcionalidade = {ATRIBUTOS_MUNICIPIO_INSERIR, ATRIBUTOS_MUNICIPIO_ATUALIZAR, ATRIBUTOS_MUNICIPIO_REMOVER})
	private gcom.cadastro.geografico.RegiaoDesenvolvimento regiaoDesenvolvimento;

	/** persistent field */
	@ControleAlteracao(value = FiltroMunicipio.UNIDADE_FEDERACAO, funcionalidade = {ATRIBUTOS_MUNICIPIO_INSERIR, ATRIBUTOS_MUNICIPIO_ATUALIZAR, ATRIBUTOS_MUNICIPIO_REMOVER})
	private gcom.cadastro.geografico.UnidadeFederacao unidadeFederacao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_MUNICIPIO_INSERIR, ATRIBUTOS_MUNICIPIO_ATUALIZAR, ATRIBUTOS_MUNICIPIO_REMOVER})
	private Date dataConcessaoInicio;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_MUNICIPIO_INSERIR, ATRIBUTOS_MUNICIPIO_ATUALIZAR, ATRIBUTOS_MUNICIPIO_REMOVER})
	private Date dataConcessaoFim;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_MUNICIPIO_INSERIR, ATRIBUTOS_MUNICIPIO_ATUALIZAR, ATRIBUTOS_MUNICIPIO_REMOVER})
	private String nomePrefeitura;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_MUNICIPIO_INSERIR, ATRIBUTOS_MUNICIPIO_ATUALIZAR, ATRIBUTOS_MUNICIPIO_REMOVER})
	private String enderecoPrefeitura;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_MUNICIPIO_INSERIR, ATRIBUTOS_MUNICIPIO_ATUALIZAR, ATRIBUTOS_MUNICIPIO_REMOVER})
	private String numeroCnpjPrefeitura;

	/** nullable persistent field */
	@ControleAlteracao(value = FiltroMunicipio.AG, funcionalidade = {ATRIBUTOS_MUNICIPIO_INSERIR, ATRIBUTOS_MUNICIPIO_ATUALIZAR, ATRIBUTOS_MUNICIPIO_REMOVER})
	private gcom.arrecadacao.banco.Agencia agencia;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_MUNICIPIO_INSERIR, ATRIBUTOS_MUNICIPIO_ATUALIZAR, ATRIBUTOS_MUNICIPIO_REMOVER})
	private Integer numeroContaBancaria;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_MUNICIPIO_INSERIR, ATRIBUTOS_MUNICIPIO_ATUALIZAR, ATRIBUTOS_MUNICIPIO_REMOVER})
	private String nomePrefeito;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_MUNICIPIO_INSERIR, ATRIBUTOS_MUNICIPIO_ATUALIZAR, ATRIBUTOS_MUNICIPIO_REMOVER})
	private String numeroCpfPrefeito;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_MUNICIPIO_INSERIR, ATRIBUTOS_MUNICIPIO_ATUALIZAR, ATRIBUTOS_MUNICIPIO_REMOVER})
	private String nomePartidoPrefeito;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_MUNICIPIO_INSERIR, ATRIBUTOS_MUNICIPIO_ATUALIZAR, ATRIBUTOS_MUNICIPIO_REMOVER})
	private String nacionalidadePrefeito;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_MUNICIPIO_INSERIR, ATRIBUTOS_MUNICIPIO_ATUALIZAR, ATRIBUTOS_MUNICIPIO_REMOVER})
	private String estadoCivilPrefeito;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_MUNICIPIO_INSERIR, ATRIBUTOS_MUNICIPIO_ATUALIZAR, ATRIBUTOS_MUNICIPIO_REMOVER})
	private Integer cepPadrao;

	private String nomeComId;

	public Date getDataConcessaoFim(){

		return dataConcessaoFim;
	}

	public void setDataConcessaoFim(Date dataConcessaoFim){

		this.dataConcessaoFim = dataConcessaoFim;
	}

	public Date getDataConcessaoInicio(){

		return dataConcessaoInicio;
	}

	public void setDataConcessaoInicio(Date dataConcessaoInicio){

		this.dataConcessaoInicio = dataConcessaoInicio;
	}

	/** full constructor */
	public Municipio(String nome, Integer cepInicio, Integer cepFim, Short ddd, Short indicadorUso, Date ultimaAlteracao,
						gcom.cadastro.geografico.Microrregiao microrregiao,
						gcom.cadastro.geografico.RegiaoDesenvolvimento regiaoDesenvolvimento,
						gcom.cadastro.geografico.UnidadeFederacao unidadeFederacao, Date dataConcessaoInicio, Date dataConcessaoFim,
						String nomePrefeitura, String enderecoPrefeitura, String numeroCnpjPrefeitura,
						gcom.arrecadacao.banco.Agencia agencia, Integer numeroContaBancaria, String nomePrefeito, String numeroCpfPrefeito,
						String nomePartidoPrefeito, String nacionalidadePrefeito, String estadoCivilPrefeito, Integer cepPadrao) {

		this.nome = nome;
		this.cepInicio = cepInicio;
		this.cepFim = cepFim;
		this.ddd = ddd;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.microrregiao = microrregiao;
		this.regiaoDesenvolvimento = regiaoDesenvolvimento;
		this.unidadeFederacao = unidadeFederacao;
		this.dataConcessaoInicio = dataConcessaoInicio;
		this.dataConcessaoFim = dataConcessaoFim;
		this.nomePrefeitura = nomePrefeitura;
		this.enderecoPrefeitura = enderecoPrefeitura;
		this.numeroCnpjPrefeitura = numeroCnpjPrefeitura;
		this.agencia = agencia;
		this.numeroContaBancaria = numeroContaBancaria;
		this.nomePrefeito = nomePrefeito;
		this.numeroCpfPrefeito = numeroCpfPrefeito;
		this.nomePartidoPrefeito = nomePartidoPrefeito;
		this.nacionalidadePrefeito = nacionalidadePrefeito;
		this.estadoCivilPrefeito = estadoCivilPrefeito;
		this.cepPadrao = cepPadrao;
	}

	/** default constructor */
	public Municipio() {

	}

	/**
	 * @param id
	 */
	public Municipio(Integer id) {

		this.id = id;
	}

	/** minimal constructor */
	public Municipio(gcom.cadastro.geografico.Microrregiao microrregiao,
						gcom.cadastro.geografico.RegiaoDesenvolvimento regiaoDesenvolvimento,
						gcom.cadastro.geografico.UnidadeFederacao unidadeFederacao) {

		this.microrregiao = microrregiao;
		this.regiaoDesenvolvimento = regiaoDesenvolvimento;
		this.unidadeFederacao = unidadeFederacao;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getNome(){

		return this.nome;
	}

	public void setNome(String nome){

		this.nome = nome;
	}

	public Integer getCepInicio(){

		return this.cepInicio;
	}

	public void setCepInicio(Integer cepInicio){

		this.cepInicio = cepInicio;
	}

	public Integer getCepFim(){

		return this.cepFim;
	}

	public void setCepFim(Integer cepFim){

		this.cepFim = cepFim;
	}

	public Short getDdd(){

		return this.ddd;
	}

	public void setDdd(Short ddd){

		this.ddd = ddd;
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

	public gcom.cadastro.geografico.Microrregiao getMicrorregiao(){

		return this.microrregiao;
	}

	public void setMicrorregiao(gcom.cadastro.geografico.Microrregiao microrregiao){

		this.microrregiao = microrregiao;
	}

	public gcom.cadastro.geografico.RegiaoDesenvolvimento getRegiaoDesenvolvimento(){

		return this.regiaoDesenvolvimento;
	}

	public void setRegiaoDesenvolvimento(gcom.cadastro.geografico.RegiaoDesenvolvimento regiaoDesenvolvimento){

		this.regiaoDesenvolvimento = regiaoDesenvolvimento;
	}

	public gcom.cadastro.geografico.UnidadeFederacao getUnidadeFederacao(){

		return this.unidadeFederacao;
	}

	public void setUnidadeFederacao(gcom.cadastro.geografico.UnidadeFederacao unidadeFederacao){

		this.unidadeFederacao = unidadeFederacao;
	}

	public Integer getCepPadrao(){

		return cepPadrao;
	}

	public void setCepPadrao(Integer cepPadrao){

		this.cepPadrao = cepPadrao;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String getNomePrefeitura(){

		return nomePrefeitura;
	}

	public void setNomePrefeitura(String nomePrefeitura){

		this.nomePrefeitura = nomePrefeitura;
	}

	public String getEnderecoPrefeitura(){

		return enderecoPrefeitura;
	}

	public void setEnderecoPrefeitura(String enderecoPrefeitura){

		this.enderecoPrefeitura = enderecoPrefeitura;
	}

	public String getNumeroCnpjPrefeitura(){

		return numeroCnpjPrefeitura;
	}

	public gcom.arrecadacao.banco.Agencia getAgencia(){

		return agencia;
	}

	public void setAgencia(gcom.arrecadacao.banco.Agencia agencia){

		this.agencia = agencia;
	}

	public Integer getNumeroContaBancaria(){

		return numeroContaBancaria;
	}

	public void setNumeroContaBancaria(Integer numeroContaBancaria){

		this.numeroContaBancaria = numeroContaBancaria;
	}

	public String getNomePrefeito(){

		return nomePrefeito;
	}

	public void setNomePrefeito(String nomePrefeito){

		this.nomePrefeito = nomePrefeito;
	}

	public String getNumeroCpfPrefeito(){

		return numeroCpfPrefeito;
	}

	public void setNumeroCpfPrefeito(String numeroCpfPrefeito){

		this.numeroCpfPrefeito = numeroCpfPrefeito;
	}

	public String getNomePartidoPrefeito(){

		return nomePartidoPrefeito;
	}

	public void setNomePartidoPrefeito(String nomePartidoPrefeito){

		this.nomePartidoPrefeito = nomePartidoPrefeito;
	}

	public String getNacionalidadePrefeito(){

		return nacionalidadePrefeito;
	}

	public void setNacionalidadePrefeito(String nacionalidadePrefeito){

		this.nacionalidadePrefeito = nacionalidadePrefeito;
	}

	public String getEstadoCivilPrefeito(){

		return estadoCivilPrefeito;
	}

	public void setEstadoCivilPrefeito(String estadoCivilPrefeito){

		this.estadoCivilPrefeito = estadoCivilPrefeito;
	}

	public void setNumeroCnpjPrefeitura(String numeroCnpjPrefeitura){

		this.numeroCnpjPrefeitura = numeroCnpjPrefeitura;
	}

	public Filtro retornaFiltro(){

		FiltroMunicipio filtroMunicipio = new FiltroMunicipio();

		filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, this.getId()));
		filtroMunicipio.adicionarCaminhoParaCarregamentoEntidade("microrregiao");
		filtroMunicipio.adicionarCaminhoParaCarregamentoEntidade("regiaoDesenvolvimento");
		filtroMunicipio.adicionarCaminhoParaCarregamentoEntidade("unidadeFederacao");
		filtroMunicipio.adicionarCaminhoParaCarregamentoEntidade("agencia");

		return filtroMunicipio;
	}

	public String getNomeComId(){

		if(this.getId().compareTo(10) == -1){
			nomeComId = "0" + getId() + " - " + getNome();
		}else{
			nomeComId = getId() + " - " + getNome();
		}

		return nomeComId;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return this.id + " - " + this.nome;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"nome"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Nome"};
		return labels;
	}

	public String getDddComMunicipio(){

		String retorno = getDdd() + " - " + getNome();

		return retorno;
	}

}
