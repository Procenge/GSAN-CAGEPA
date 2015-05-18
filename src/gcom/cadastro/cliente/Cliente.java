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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.cadastro.cliente;

import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.cobranca.QuitacaoDebitoAnual;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Hibernate CodeGenerator
 * @created 22 de Julho de 2005
 * @date 02/06/2008
 *       Inclusão do Atributo InscricaoEstadual
 */
@ControleAlteracao()
public class Cliente
				extends ObjetoTransacao {

	public static final int ATRIBUTOS_CLIENTE_INSERIR = 28;

	public static final int ATRIBUTOS_CLIENTE_ATUALIZAR = 38;

	public static final int ATRIBUTOS_CLIENTE_REMOVER = 39;

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private String nome;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private String nomeAbreviado;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private String cpf;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private String rg;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private Date dataEmissaoRg;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private Short dataVencimento;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private Date dataNascimento;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private String cnpj;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private String email;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private Short indicadorUso;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private Short documentoValidado;

	private Date ultimaAlteracao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private Short indicadorAcaoCobranca;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private String inscricaoEstadual;

	@ControleAlteracao(value = FiltroCliente.ORGAO_EXPEDIDOR_RG, funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private gcom.cadastro.cliente.OrgaoExpedidorRg orgaoExpedidorRg;

	@ControleAlteracao(value = FiltroCliente.CLIENTE_RESPONSAVEL, funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private gcom.cadastro.cliente.Cliente cliente;

	@ControleAlteracao(value = FiltroCliente.SEXO, funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private gcom.cadastro.cliente.PessoaSexo pessoaSexo;

	@ControleAlteracao(value = FiltroCliente.PROFISSAO, funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private gcom.cadastro.cliente.Profissao profissao;

	@ControleAlteracao(value = FiltroCliente.UNIDADE_FEDERACAO, funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private UnidadeFederacao unidadeFederacao;

	@ControleAlteracao(value = FiltroCliente.TIPOCLIENTE, funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private gcom.cadastro.cliente.ClienteTipo clienteTipo;

	@ControleAlteracao(value = FiltroCliente.CLIENTE_TIPO_ESPECIAL, funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private ClienteTipoEspecial clienteTipoEspecial;

	@ControleAlteracao(value = FiltroCliente.RAMO_ATIVIDADE, funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private gcom.cadastro.cliente.RamoAtividade ramoAtividade;

	@ControleAlteracao(value = FiltroCliente.RACA, funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private Raca raca;

	@ControleAlteracao(value = FiltroCliente.ESTADO_CIVIL, funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private EstadoCivil estadoCivil;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private Nacionalidade nacionalidade;

	/** persistent field */
	private Set clienteFones;

	/** persistent field */
	private Set clienteImoveis;

	/** persistent field */
	private Set clienteEnderecos;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private Short diaVencimento;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private String nomeMae;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private Short indicadorCobrancaAcrescimos;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private Short indicadorGeraArquivoTexto;

	public static final Integer CODIGO_CLIENTE_MARIO_GOUVEIA = 6548350;

	public static final Short GERA_ARQUIVO_TEXTO_SIM = 1;

	/** persistent field */
	private Set<QuitacaoDebitoAnual> quitacaoDebitoAnualResponsavel;

	/** persistent field */
	private Set<QuitacaoDebitoAnual> quitacaoDebitoAnualUsuario;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private Short indicadorContaBraille = 2;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_INSERIR, ATRIBUTOS_CLIENTE_ATUALIZAR, ATRIBUTOS_CLIENTE_REMOVER})
	private AtividadeEconomica atividadeEconomica;

	private String numeroBeneficio;

	/**
	 * GSANPCG
	 * full constructor
	 */
	public Cliente(String nome, String nomeAbreviado, String cpf, String rg, Date dataEmissaoRg, Date dataNascimento, String cnpj,
					String email, Short indicadorUso, Date ultimaAlteracao, gcom.cadastro.cliente.OrgaoExpedidorRg orgaoExpedidorRg,
					gcom.cadastro.cliente.Cliente cliente, gcom.cadastro.cliente.PessoaSexo pessoaSexo,
					gcom.cadastro.cliente.Profissao profissao, UnidadeFederacao unidadeFederacao,
					gcom.cadastro.cliente.ClienteTipo clienteTipo, gcom.cadastro.cliente.RamoAtividade ramoAtividade, Short diaVencimento,
					String inscricaoEstadual, Short indicadorContaBraille, Short documentoValidado) {

		this.nome = nome;
		this.nomeAbreviado = nomeAbreviado;
		this.cpf = cpf;
		this.rg = rg;
		this.dataEmissaoRg = dataEmissaoRg;
		this.dataNascimento = dataNascimento;
		this.cnpj = cnpj;
		this.email = email;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.orgaoExpedidorRg = orgaoExpedidorRg;
		this.cliente = cliente;
		this.pessoaSexo = pessoaSexo;
		this.profissao = profissao;
		this.unidadeFederacao = unidadeFederacao;
		this.clienteTipo = clienteTipo;
		this.ramoAtividade = ramoAtividade;
		this.diaVencimento = diaVencimento;
		this.inscricaoEstadual = inscricaoEstadual;
		this.indicadorContaBraille = indicadorContaBraille;
		this.documentoValidado = documentoValidado;
	}

	/**
	 * full constructor
	 */
	public Cliente(String nome, String nomeAbreviado, String cpf, String rg, Date dataEmissaoRg, Date dataNascimento, String cnpj,
					String email, Short indicadorUso, Date ultimaAlteracao, gcom.cadastro.cliente.OrgaoExpedidorRg orgaoExpedidorRg,
					gcom.cadastro.cliente.Cliente cliente, gcom.cadastro.cliente.PessoaSexo pessoaSexo,
					gcom.cadastro.cliente.Profissao profissao, UnidadeFederacao unidadeFederacao,
					gcom.cadastro.cliente.ClienteTipo clienteTipo, gcom.cadastro.cliente.RamoAtividade ramoAtividade,
					Short documentoValidado) {

		this.nome = nome;
		this.nomeAbreviado = nomeAbreviado;
		this.cpf = cpf;
		this.rg = rg;
		this.dataEmissaoRg = dataEmissaoRg;
		this.dataNascimento = dataNascimento;
		this.cnpj = cnpj;
		this.email = email;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.orgaoExpedidorRg = orgaoExpedidorRg;
		this.cliente = cliente;
		this.pessoaSexo = pessoaSexo;
		this.profissao = profissao;
		this.unidadeFederacao = unidadeFederacao;
		this.clienteTipo = clienteTipo;
		this.ramoAtividade = ramoAtividade;
		this.documentoValidado = documentoValidado;
	}

	/**
	 * full constructor
	 * 
	 * @param nome
	 *            Descrição do parâmetro
	 */
	public Cliente(String nome) {

		this.nome = nome;

	}

	/**
	 * full constructor
	 * 
	 * @param nome
	 *            Descrição do parâmetro
	 */
	public Cliente(String nome, Integer id) {

		this.nome = nome;
		this.id = id;
	}

	public Cliente(Integer id) {

		this.id = id;
	}

	/**
	 * full constructor
	 * 
	 * @param id
	 *            Description of the Parameter
	 * @param nome
	 *            Descrição do parâmetro
	 * @param clienteTipo
	 *            Description of the Parameter
	 * @param cpf
	 *            Description of the Parameter
	 * @param cnpj
	 *            Description of the Parameter
	 */
	public Cliente(Integer id, String nome, gcom.cadastro.cliente.ClienteTipo clienteTipo, String cpf, String cnpj) {

		this.id = id;
		this.nome = nome;
		this.clienteTipo = clienteTipo;
		this.cpf = cpf;
		this.cnpj = cnpj;
	}

	/**
	 * default constructor
	 */
	public Cliente() {

	}

	/**
	 * minimal constructor
	 * 
	 * @param orgaoExpedidorRg
	 *            Descrição do parâmetro
	 * @param cliente
	 *            Descrição do parâmetro
	 * @param pessoaSexo
	 *            Descrição do parâmetro
	 * @param profissao
	 *            Descrição do parâmetro
	 * @param unidadeFederacao
	 *            Descrição do parâmetro
	 * @param clienteTipo
	 *            Descrição do parâmetro
	 * @param ramoAtividade
	 *            Descrição do parâmetro
	 */
	public Cliente(gcom.cadastro.cliente.OrgaoExpedidorRg orgaoExpedidorRg, gcom.cadastro.cliente.Cliente cliente,
					gcom.cadastro.cliente.PessoaSexo pessoaSexo, gcom.cadastro.cliente.Profissao profissao,
					UnidadeFederacao unidadeFederacao, gcom.cadastro.cliente.ClienteTipo clienteTipo,
					gcom.cadastro.cliente.RamoAtividade ramoAtividade) {

		this.orgaoExpedidorRg = orgaoExpedidorRg;
		this.cliente = cliente;
		this.pessoaSexo = pessoaSexo;
		this.profissao = profissao;
		this.unidadeFederacao = unidadeFederacao;
		this.clienteTipo = clienteTipo;
		this.ramoAtividade = ramoAtividade;
	}

	/**
	 * Retorna o valor de id
	 * 
	 * @return O valor de id
	 */
	public Integer getId(){

		return this.id;
	}

	/**
	 * Seta o valor de id
	 * 
	 * @param id
	 *            O novo valor de id
	 */
	public void setId(Integer id){

		this.id = id;
	}

	/**
	 * Retorna o valor de nome
	 * 
	 * @return O valor de nome
	 */
	public String getNome(){

		return this.nome;
	}

	/**
	 * Seta o valor de nome
	 * 
	 * @param nome
	 *            O novo valor de nome
	 */
	public void setNome(String nome){

		this.nome = nome;
	}

	/**
	 * Retorna o valor de nomeAbreviado
	 * 
	 * @return O valor de nomeAbreviado
	 */
	public String getNomeAbreviado(){

		return this.nomeAbreviado;
	}

	/**
	 * Seta o valor de nomeAbreviado
	 * 
	 * @param nomeAbreviado
	 *            O novo valor de nomeAbreviado
	 */
	public void setNomeAbreviado(String nomeAbreviado){

		this.nomeAbreviado = nomeAbreviado;
	}

	/**
	 * Retorna o valor de cpf
	 * 
	 * @return O valor de cpf
	 */
	public String getCpf(){

		return this.cpf;
	}

	/**
	 * Seta o valor de cpf
	 * 
	 * @param cpf
	 *            O novo valor de cpf
	 */
	public void setCpf(String cpf){

		this.cpf = cpf;
	}

	/**
	 * Retorna o valor de rg
	 * 
	 * @return O valor de rg
	 */
	public String getRg(){

		return this.rg;
	}

	/**
	 * Seta o valor de rg
	 * 
	 * @param rg
	 *            O novo valor de rg
	 */
	public void setRg(String rg){

		this.rg = rg;
	}

	/**
	 * Retorna o valor de dataEmissaoRg
	 * 
	 * @return O valor de dataEmissaoRg
	 */
	public Date getDataEmissaoRg(){

		return this.dataEmissaoRg;
	}

	/**
	 * Seta o valor de dataEmissaoRg
	 * 
	 * @param dataEmissaoRg
	 *            O novo valor de dataEmissaoRg
	 */
	public void setDataEmissaoRg(Date dataEmissaoRg){

		this.dataEmissaoRg = dataEmissaoRg;
	}

	/**
	 * Retorna o valor de dataNascimento
	 * 
	 * @return O valor de dataNascimento
	 */
	public Date getDataNascimento(){

		return this.dataNascimento;
	}

	/**
	 * Seta o valor de dataNascimento
	 * 
	 * @param dataNascimento
	 *            O novo valor de dataNascimento
	 */
	public void setDataNascimento(Date dataNascimento){

		this.dataNascimento = dataNascimento;
	}

	/**
	 * Retorna o valor de cnpj
	 * 
	 * @return O valor de cnpj
	 */
	public String getCnpj(){

		return this.cnpj;
	}

	/**
	 * Seta o valor de cnpj
	 * 
	 * @param cnpj
	 *            O novo valor de cnpj
	 */
	public void setCnpj(String cnpj){

		this.cnpj = cnpj;
	}

	/**
	 * Retorna o valor de email
	 * 
	 * @return O valor de email
	 */
	public String getEmail(){

		return this.email;
	}

	/**
	 * Seta o valor de email
	 * 
	 * @param email
	 *            O novo valor de email
	 */
	public void setEmail(String email){

		this.email = email;
	}

	/**
	 * Retorna o valor de indicadorUso
	 * 
	 * @return O valor de indicadorUso
	 */
	public Short getIndicadorUso(){

		return this.indicadorUso;
	}

	/**
	 * Seta o valor de indicadorUso
	 * 
	 * @param indicadorUso
	 *            O novo valor de indicadorUso
	 */
	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	/**
	 * Retorna o valor de DocumentoValidado
	 * 
	 * @return O valor de DocumentoValidado
	 */

	public Short getDocumentoValidado(){

		return documentoValidado;
	}

	/**
	 * Seta o valor de DocumentoValidado
	 * 
	 * @param indicadorUso
	 *            O novo valor de DocumentoValidado
	 */
	public void setDocumentoValidado(Short documentoValidado){

		this.documentoValidado = documentoValidado;
	}

	/**
	 * Retorna o valor de ultimaAlteracao
	 * 
	 * @return O valor de ultimaAlteracao
	 */
	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	/**
	 * Seta o valor de ultimaAlteracao
	 * 
	 * @param ultimaAlteracao
	 *            O novo valor de ultimaAlteracao
	 */
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	/**
	 * Retorna o valor de orgaoExpedidorRg
	 * 
	 * @return O valor de orgaoExpedidorRg
	 */
	public gcom.cadastro.cliente.OrgaoExpedidorRg getOrgaoExpedidorRg(){

		return this.orgaoExpedidorRg;
	}

	/**
	 * Seta o valor de orgaoExpedidorRg
	 * 
	 * @param orgaoExpedidorRg
	 *            O novo valor de orgaoExpedidorRg
	 */
	public void setOrgaoExpedidorRg(gcom.cadastro.cliente.OrgaoExpedidorRg orgaoExpedidorRg){

		this.orgaoExpedidorRg = orgaoExpedidorRg;
	}

	/**
	 * Retorna o valor de cliente
	 * 
	 * @return O valor de cliente
	 */
	public gcom.cadastro.cliente.Cliente getCliente(){

		return this.cliente;
	}

	/**
	 * Seta o valor de cliente
	 * 
	 * @param cliente
	 *            O novo valor de cliente
	 */
	public void setCliente(gcom.cadastro.cliente.Cliente cliente){

		this.cliente = cliente;
	}

	/**
	 * Retorna o valor de pessoaSexo
	 * 
	 * @return O valor de pessoaSexo
	 */
	public gcom.cadastro.cliente.PessoaSexo getPessoaSexo(){

		return this.pessoaSexo;
	}

	/**
	 * Seta o valor de pessoaSexo
	 * 
	 * @param pessoaSexo
	 *            O novo valor de pessoaSexo
	 */
	public void setPessoaSexo(gcom.cadastro.cliente.PessoaSexo pessoaSexo){

		this.pessoaSexo = pessoaSexo;
	}

	/**
	 * Retorna o valor de profissao
	 * 
	 * @return O valor de profissao
	 */
	public gcom.cadastro.cliente.Profissao getProfissao(){

		return this.profissao;
	}

	/**
	 * Seta o valor de profissao
	 * 
	 * @param profissao
	 *            O novo valor de profissao
	 */
	public void setProfissao(gcom.cadastro.cliente.Profissao profissao){

		this.profissao = profissao;
	}

	/**
	 * Retorna o valor de unidadeFederacao
	 * 
	 * @return O valor de unidadeFederacao
	 */
	public UnidadeFederacao getUnidadeFederacao(){

		return this.unidadeFederacao;
	}

	/**
	 * Seta o valor de unidadeFederacao
	 * 
	 * @param unidadeFederacao
	 *            O novo valor de unidadeFederacao
	 */
	public void setUnidadeFederacao(UnidadeFederacao unidadeFederacao){

		this.unidadeFederacao = unidadeFederacao;
	}

	/**
	 * Retorna o valor de clienteTipo
	 * 
	 * @return O valor de clienteTipo
	 */
	public gcom.cadastro.cliente.ClienteTipo getClienteTipo(){

		return this.clienteTipo;
	}

	/**
	 * Seta o valor de clienteTipo
	 * 
	 * @param clienteTipo
	 *            O novo valor de clienteTipo
	 */
	public void setClienteTipo(gcom.cadastro.cliente.ClienteTipo clienteTipo){

		this.clienteTipo = clienteTipo;
	}

	/**
	 * Retorna o valor de ramoAtividade
	 * 
	 * @return O valor de ramoAtividade
	 */
	public gcom.cadastro.cliente.RamoAtividade getRamoAtividade(){

		return this.ramoAtividade;
	}

	/**
	 * Seta o valor de ramoAtividade
	 * 
	 * @param ramoAtividade
	 *            O novo valor de ramoAtividade
	 */
	public void setRamoAtividade(gcom.cadastro.cliente.RamoAtividade ramoAtividade){

		this.ramoAtividade = ramoAtividade;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param other
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public boolean equals(Object other){

		if((this == other)){
			return true;
		}
		if(!(other instanceof Cliente)){
			return false;
		}
		Cliente castOther = (Cliente) other;

		return (this.getId().equals(castOther.getId()));
	}

	/**
	 * Retorna o valor de cpfFormatado
	 * 
	 * @return O valor de cpfFormatado
	 */
	public String getCpfFormatado(){

		String cpfFormatado = this.cpf;

		if(cpfFormatado != null && cpfFormatado.length() == 11){

			cpfFormatado = cpfFormatado.substring(0, 3) + "." + cpfFormatado.substring(3, 6) + "." + cpfFormatado.substring(6, 9) + "-"
							+ cpfFormatado.substring(9, 11);
		}

		return cpfFormatado;
	}

	/**
	 * Retorna o valor de cnpjFormatado
	 * 
	 * @return O valor de cnpjFormatado
	 */
	public String getCnpjFormatado(){

		String cnpjFormatado = this.cnpj;
		String zeros = "";

		if(cnpjFormatado != null){

			for(int a = 0; a < (14 - cnpjFormatado.length()); a++){
				zeros = zeros.concat("0");
			}
			// concatena os zeros ao numero
			// caso o numero seja diferente de nulo
			cnpjFormatado = zeros.concat(cnpjFormatado);

			cnpjFormatado = cnpjFormatado.substring(0, 2) + "." + cnpjFormatado.substring(2, 5) + "." + cnpjFormatado.substring(5, 8) + "/"
							+ cnpjFormatado.substring(8, 12) + "-" + cnpjFormatado.substring(12, 14);
		}

		return cnpjFormatado;
	}

	/**
	 * Retorna a Inscricao Estadual no formato da UF origem
	 * Ref. para Formato : www.sintegra.gov.br
	 * Os Estados que não tem tratamento não sao verificados
	 * 
	 * @return
	 * @author eduardo henrique
	 *         PS : No caso de PE, só está se formatando o formato antigo
	 */
	public String getInscricaoEstadualFormatada(){

		String inscricaoFormatada = this.inscricaoEstadual;

		try{

			if(inscricaoFormatada != null && this.unidadeFederacao != null){
				if(this.unidadeFederacao.getSigla().equalsIgnoreCase("AC")){
					inscricaoFormatada = inscricaoFormatada.substring(0, 2) + "." + inscricaoFormatada.substring(2, 5) + "."
									+ inscricaoFormatada.substring(5, 8) + "/" + inscricaoFormatada.substring(8, 11) + "-"
									+ inscricaoFormatada.substring(11, 13);
				}else if(this.unidadeFederacao.getSigla().equalsIgnoreCase("AM") || this.unidadeFederacao.getSigla().equalsIgnoreCase("GO")){
					inscricaoFormatada = inscricaoFormatada.substring(0, 2) + "." + inscricaoFormatada.substring(2, 5) + "."
									+ inscricaoFormatada.substring(5, 8) + "-" + inscricaoFormatada.substring(8, 9);
				}else if(this.unidadeFederacao.getSigla().equalsIgnoreCase("BA")){
					inscricaoFormatada = inscricaoFormatada.substring(0, 6) + "-" + inscricaoFormatada.substring(6, 8);
				}else if(this.unidadeFederacao.getSigla().equalsIgnoreCase("CE") || this.unidadeFederacao.getSigla().equalsIgnoreCase("ES")
								|| this.unidadeFederacao.getSigla().equalsIgnoreCase("MA")
								|| this.unidadeFederacao.getSigla().equalsIgnoreCase("MS")
								|| this.unidadeFederacao.getSigla().equalsIgnoreCase("PB")
								|| this.unidadeFederacao.getSigla().equalsIgnoreCase("PI")
								|| this.unidadeFederacao.getSigla().equalsIgnoreCase("RR")
								|| this.unidadeFederacao.getSigla().equalsIgnoreCase("SE")){
					inscricaoFormatada = inscricaoFormatada.substring(0, 8) + "-" + inscricaoFormatada.substring(8, 9);
				}else if(this.unidadeFederacao.getSigla().equalsIgnoreCase("DF")){
					inscricaoFormatada = inscricaoFormatada.substring(0, 2) + "." + inscricaoFormatada.substring(2, 8) + "."
									+ inscricaoFormatada.substring(8, 11) + "-" + inscricaoFormatada.substring(11, 13);
				}else if(this.unidadeFederacao.getSigla().equalsIgnoreCase("MT") || this.unidadeFederacao.getSigla().equalsIgnoreCase("TO")){
					inscricaoFormatada = inscricaoFormatada.substring(0, 10) + "-" + inscricaoFormatada.substring(10, 11);
				}else if(this.unidadeFederacao.getSigla().equalsIgnoreCase("MG")){
					inscricaoFormatada = inscricaoFormatada.substring(0, 3) + "." + inscricaoFormatada.substring(3, 6) + "."
									+ inscricaoFormatada.substring(6, 9) + "/" + inscricaoFormatada.substring(9, 13);
				}else if(this.unidadeFederacao.getSigla().equalsIgnoreCase("PA")){
					inscricaoFormatada = inscricaoFormatada.substring(0, 2) + "-" + inscricaoFormatada.substring(2, 8) + "-"
									+ inscricaoFormatada.substring(8, 9);
				}else if(this.unidadeFederacao.getSigla().equalsIgnoreCase("PR")){
					inscricaoFormatada = inscricaoFormatada.substring(0, 3) + "." + inscricaoFormatada.substring(3, 8) + "-"
									+ inscricaoFormatada.substring(8, 10);
				}else if(this.unidadeFederacao.getSigla().equalsIgnoreCase("PA")){
					inscricaoFormatada = inscricaoFormatada.substring(0, 2) + "-" + inscricaoFormatada.substring(2, 8) + "-"
									+ inscricaoFormatada.substring(8, 9);
				}else if(this.unidadeFederacao.getSigla().equalsIgnoreCase("PE")){
					inscricaoFormatada = inscricaoFormatada.substring(0, 2) + "." + inscricaoFormatada.substring(2, 3) + "."
									+ inscricaoFormatada.substring(3, 6) + "." + inscricaoFormatada.substring(6, 13) + "-"
									+ inscricaoFormatada.substring(13, 14);
				}else if(this.unidadeFederacao.getSigla().equalsIgnoreCase("RJ")){
					inscricaoFormatada = inscricaoFormatada.substring(0, 2) + "." + inscricaoFormatada.substring(2, 5) + "."
									+ inscricaoFormatada.substring(5, 7) + "-" + inscricaoFormatada.substring(7, 8);
				}else if(this.unidadeFederacao.getSigla().equalsIgnoreCase("RN")){
					if(inscricaoFormatada.length() == 9){
						inscricaoFormatada = inscricaoFormatada.substring(0, 2) + "." + inscricaoFormatada.substring(2, 5) + "."
										+ inscricaoFormatada.substring(5, 8) + "-" + inscricaoFormatada.substring(8, 9);
					}else{
						inscricaoFormatada = inscricaoFormatada.substring(0, 2) + "." + inscricaoFormatada.substring(2, 3) + "."
										+ inscricaoFormatada.substring(3, 6) + "." + inscricaoFormatada.substring(6, 9) + "-"
										+ inscricaoFormatada.substring(9, 10);
					}
				}else if(this.unidadeFederacao.getSigla().equalsIgnoreCase("RS")){
					inscricaoFormatada = inscricaoFormatada.substring(0, 3) + "/" + inscricaoFormatada.substring(3, 10);
				}else if(this.unidadeFederacao.getSigla().equalsIgnoreCase("RO")){
					if(inscricaoFormatada.length() == 9){
						inscricaoFormatada = inscricaoFormatada.substring(0, 3) + "." + inscricaoFormatada.substring(3, 8) + "-"
										+ inscricaoFormatada.substring(8, 9);
					}else{
						inscricaoFormatada = inscricaoFormatada.substring(0, 13) + "-" + inscricaoFormatada.substring(13, 14);
					}
				}else if(this.unidadeFederacao.getSigla().equalsIgnoreCase("SC")){
					inscricaoFormatada = inscricaoFormatada.substring(0, 3) + "." + inscricaoFormatada.substring(3, 6) + "."
									+ inscricaoFormatada.substring(6, 9);
				}else if(this.unidadeFederacao.getSigla().equalsIgnoreCase("SP")){
					inscricaoFormatada = inscricaoFormatada.substring(0, 3) + "." + inscricaoFormatada.substring(3, 6) + "."
									+ inscricaoFormatada.substring(6, 9) + "." + inscricaoFormatada.substring(9, 12);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return inscricaoFormatada;
	}

	/**
	 * Retorna o valor de clienteEnderecos
	 * 
	 * @return O valor de clienteEnderecos
	 */
	public Set getClienteEnderecos(){

		return clienteEnderecos;
	}

	/**
	 * Seta o valor de clienteEnderecos
	 * 
	 * @param clienteEnderecos
	 *            O novo valor de clienteEnderecos
	 */
	public void setClienteEnderecos(Set clienteEnderecos){

		this.clienteEnderecos = clienteEnderecos;
	}

	/**
	 * Retorna o valor de clienteFones
	 * 
	 * @return O valor de clienteFones
	 */
	public Set getClienteFones(){

		return clienteFones;
	}

	/**
	 * Seta o valor de clienteFones
	 * 
	 * @param clienteFones
	 *            O novo valor de clienteFones
	 */
	public void setClienteFones(Set clienteFones){

		this.clienteFones = clienteFones;
	}

	public Set getClienteImoveis(){

		return clienteImoveis;
	}

	public void setClienteImoveis(Set clienteImoveis){

		this.clienteImoveis = clienteImoveis;
	}

	public Short getIndicadorAcaoCobranca(){

		return indicadorAcaoCobranca;
	}

	public void setIndicadorAcaoCobranca(Short indicadorAcaoCobranca){

		this.indicadorAcaoCobranca = indicadorAcaoCobranca;
	}

	/**
	 * @return Returns the diaVencimento.
	 */
	public Short getDiaVencimento(){

		return diaVencimento;
	}

	/**
	 * @param diaVencimento
	 *            The diaVencimento to set.
	 */
	public void setDiaVencimento(Short diaVencimento){

		this.diaVencimento = diaVencimento;
	}

	public Short getDataVencimento(){

		return dataVencimento;
	}

	public void setDataVencimento(Short dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	public String getNomeMae(){

		return nomeMae;
	}

	public void setNomeMae(String nomeMae){

		this.nomeMae = nomeMae;
	}

	public Short getIndicadorCobrancaAcrescimos(){

		return indicadorCobrancaAcrescimos;
	}

	public void setIndicadorCobrancaAcrescimos(Short indicadorCobrancaAcrescimos){

		this.indicadorCobrancaAcrescimos = indicadorCobrancaAcrescimos;
	}

	public Short getIndicadorGeraArquivoTexto(){

		return indicadorGeraArquivoTexto;
	}

	public void setIndicadorGeraArquivoTexto(Short indicadorGeraArquivoTexto){

		this.indicadorGeraArquivoTexto = indicadorGeraArquivoTexto;
	}

	public String getDescricao(){

		return this.nome;
	}

	public String getInscricaoEstadual(){

		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual){

		this.inscricaoEstadual = inscricaoEstadual;
	}

	/**
	 * @param clienteTipoEspecial
	 *            the clienteTipoEspecial to set
	 */
	public void setClienteTipoEspecial(ClienteTipoEspecial clienteTipoEspecial){

		this.clienteTipoEspecial = clienteTipoEspecial;
	}

	/**
	 * @return the clienteTipoEspecial
	 */
	public ClienteTipoEspecial getClienteTipoEspecial(){

		return clienteTipoEspecial;
	}

	/**
	 * Retorna o valor de quitacaoDebitoAnualResponsavel
	 * 
	 * @return the quitacaoDebitoAnualResponsavel
	 */
	public Set<QuitacaoDebitoAnual> getQuitacaoDebitoAnualResponsavel(){

		return quitacaoDebitoAnualResponsavel;
	}

	/**
	 * Seta o valor de quitacaoDebitoAnualResponsavel
	 * 
	 * @param quitacaoDebitoAnualResponsavel
	 *            the quitacaoDebitoAnualResponsavel to set
	 */
	public void setQuitacaoDebitoAnualResponsavel(Set<QuitacaoDebitoAnual> quitacaoDebitoAnualResponsavel){

		this.quitacaoDebitoAnualResponsavel = quitacaoDebitoAnualResponsavel;
	}

	/**
	 * Seta o valor de quitacaoDebitoAnualUsuario
	 * 
	 * @return the quitacaoDebitoAnualUsuario
	 */
	public Set<QuitacaoDebitoAnual> getQuitacaoDebitoAnualUsuario(){

		return quitacaoDebitoAnualUsuario;
	}

	/**
	 * Retorna o valor de quitacaoDebitoAnualUsuario
	 * 
	 * @param quitacaoDebitoAnualUsuario
	 *            the quitacaoDebitoAnualUsuario to set
	 */
	public void setQuitacaoDebitoAnualUsuario(Set<QuitacaoDebitoAnual> quitacaoDebitoAnualUsuario){

		this.quitacaoDebitoAnualUsuario = quitacaoDebitoAnualUsuario;
	}

	public Raca getRaca(){

		return raca;
	}

	public void setRaca(Raca raca){

		this.raca = raca;
	}

	public EstadoCivil getEstadoCivil(){

		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil){

		this.estadoCivil = estadoCivil;
	}



	
	public Nacionalidade getNacionalidade(){

		return nacionalidade;
	}

	public void setNacionalidade(Nacionalidade nacionalidade){

		this.nacionalidade = nacionalidade;
	}

	
	public Short getIndicadorContaBraille(){

		return indicadorContaBraille;
	}

	public void setIndicadorContaBraille(Short indicadorContaBraille){

		this.indicadorContaBraille = indicadorContaBraille;
	}

	public AtividadeEconomica getAtividadeEconomica(){

		return atividadeEconomica;
	}

	public void setAtividadeEconomica(AtividadeEconomica atividadeEconomica){

		this.atividadeEconomica = atividadeEconomica;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"descricao"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Descricao"};
		return labels;
	}

	public Filtro retornaFiltro(){

		FiltroCliente filtroCliente = new FiltroCliente();
		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, this.getId()));
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.ORGAO_EXPEDIDOR_RG);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.CLIENTE_RESPONSAVEL);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.SEXO);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.PROFISSAO);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.UNIDADE_FEDERACAO);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.TIPOCLIENTE);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.RAMO_ATIVIDADE);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.CLIENTE_ENDERECOS);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.CLIENTE_TIPO_ESPECIAL);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.ATIVIDADE_ECONOMICA);
		return filtroCliente;
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

		return getId() + " - " + getNome();
	}

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public String getNumeroBeneficio(){

		return numeroBeneficio;
	}

	public void setNumeroBeneficio(String numeroBeneficio){

		this.numeroBeneficio = numeroBeneficio;
	}

}