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

package gcom.seguranca.acesso.usuario;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.ControladorException;
import gcom.util.SistemaException;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ParametroGeral;
import gcom.util.parametrizacao.webservice.ParametrosAgenciaVirtual;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Usuario
				extends ObjetoTransacao
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Constante usada para recuperar o usuário logado no sistema */
	public static final String USUARIO_LOGADO = "usuarioLogado";

	public static final Integer ID_USUARIO_ADM_SISTEMA = Integer.valueOf(9999);

	public Filtro retornaFiltro(){

		FiltroUsuario filtroUsuario = new FiltroUsuario();

		filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, this.getId()));
		filtroUsuario.adicionarCaminhoParaCarregamentoEntidade("unidadeOrganizacional");
		filtroUsuario.adicionarCaminhoParaCarregamentoEntidade("usuarioSituacao");
		filtroUsuario.adicionarCaminhoParaCarregamentoEntidade("empresa");
		filtroUsuario.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
		filtroUsuario.adicionarCaminhoParaCarregamentoEntidade("localidadeElo");
		filtroUsuario.adicionarCaminhoParaCarregamentoEntidade("localidade");
		filtroUsuario.adicionarCaminhoParaCarregamentoEntidade("usuarioTipo");
		filtroUsuario.adicionarCaminhoParaCarregamentoEntidade("usuarioAbrangencia");
		filtroUsuario.adicionarCaminhoParaCarregamentoEntidade("funcionario");

		return filtroUsuario;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	/** Usuário utilizado para o processamento batch */
	public static final Usuario USUARIO_BATCH = new Usuario();

	/** Usuário utilizado para o processamento dos serviços dos Webservicesbatch */
	public static final Usuario USUARIO_AGENCIA_VIRTUAL = new Usuario();

	// public static final Usuario USUARIO_MIGRACAO;

	/** Operacao de teste */
	public static final Usuario USUARIO_TESTE = new Usuario();

	public static void inicializarUsuariosPadroes(){

		// USUARIO BATCH
		Integer idUsuarioBatch;
		try{
			idUsuarioBatch = Integer.valueOf((String) ParametroGeral.P_USUARIO_BATCH.executar());
		}catch(ControladorException e){
			throw new SistemaException(e, e.getMensagem());
		}

		// MUDAR O IDENTIFICADOR QUANDO INSERIR O USUARIO BATCH
		USUARIO_BATCH.setId(Integer.valueOf(idUsuarioBatch));

		// NA BASE
		UsuarioAbrangencia usuarioAbrangencia = new UsuarioAbrangencia();
		usuarioAbrangencia.setId(Integer.valueOf(idUsuarioBatch));

		USUARIO_BATCH.setUsuarioAbrangencia(usuarioAbrangencia);

		// USUARIO AGENCIA VIRTUAL
		Integer idUsuarioAgenciaVirtual;
		try{
			idUsuarioAgenciaVirtual = Integer.valueOf((String) ParametrosAgenciaVirtual.P_AV_USUARIO_ABERTURA.executar());
		}catch(ControladorException e){
			throw new SistemaException(e, e.getMensagem());
		}
		USUARIO_AGENCIA_VIRTUAL.setId(Integer.valueOf(idUsuarioAgenciaVirtual));
		UsuarioAbrangencia usuarioAgenciaVirtualAbrangencia = new UsuarioAbrangencia();
		usuarioAgenciaVirtualAbrangencia.setId(Integer.valueOf(idUsuarioAgenciaVirtual));
		USUARIO_AGENCIA_VIRTUAL.setUsuarioAbrangencia(usuarioAgenciaVirtualAbrangencia);

		USUARIO_TESTE.setId(ID_USUARIO_ADM_SISTEMA);
	}

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private String login;

	/** nullable persistent field */
	private String senha;

	/** nullable persistent field */
	private Integer numeroAcessos;

	/** nullable persistent field */
	private Short bloqueioAcesso;

	/** nullable persistent field */
	private Date dataExpiracaoAcesso;

	/** nullable persistent field */
	private Date dataPrazoMensagemExpiracao;

	/** nullable persistent field */
	private Date dataCadastroAcesso;

	/** nullable persistent field */
	private Date dataCadastroInicio;

	/** nullable persistent field */
	private Date dataCadastroFim;

	/** nullable persistent field */
	private String descricaoEmail;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** nullable persistent field */
	private Date ultimoAcesso;

	/** persistent field */
	private UnidadeOrganizacional unidadeOrganizacional;

	/** persistent field */
	private UsuarioSituacao usuarioSituacao;

	/** persistent field */
	private Empresa empresa;

	/** persistent field */
	private GerenciaRegional gerenciaRegional;

	/** persistent field */
	private UnidadeNegocio unidadeNegocio;

	/** persistent field */
	private Localidade localidadeElo;

	/** persistent field */
	private Localidade localidade;

	/** persistent field */
	private UsuarioTipo usuarioTipo;

	/** persistent field */
	private UsuarioAbrangencia usuarioAbrangencia;

	/** persistent field */
	private Funcionario funcionario;

	/** persistent field */
	private String nomeUsuario;

	/** persistent field */
	private Date dataNascimento;

	/** persistent field */
	private String lembreteSenha;

	/** persistent field */
	private String cpf;

	private Short indicadorTipoRelatorioPadrao;

	private Short indicadorExibeMensagem;

	private Set usuarioGrupos;

	public Set getUsuarioGrupos(){

		return usuarioGrupos;
	}

	public void setUsuarioGrupos(Set usuarioGrupos){

		this.usuarioGrupos = usuarioGrupos;
	}

	/** full constructor */
	public Usuario(String login, String senha, Integer numeroAcessos, Short bloqueioAcesso, Date dataExpiracaoAcesso,
					Date dataPrazoMensagemExpiracao, Date dataCadastroAcesso, Date dataCadastroInicio, Date dataCadastroFim,
					String descricaoEmail, Date ultimaAlteracao, Date ultimoAcesso, UnidadeOrganizacional unidadeOrganizacional,
					UsuarioSituacao usuarioSituacao, Empresa empresa, GerenciaRegional gerenciaRegional, Localidade localidadeElo,
					Localidade localidade, UsuarioTipo usuarioTipo, UsuarioAbrangencia usuarioAbrangencia, Funcionario funcionario,
					String nomeUsuario, Short indicadorTipoRelatorioPadrao, Short indicadorExibeMensagem) {

		this.login = login;
		this.senha = senha;
		this.numeroAcessos = numeroAcessos;
		this.bloqueioAcesso = bloqueioAcesso;
		this.dataExpiracaoAcesso = dataExpiracaoAcesso;
		this.dataPrazoMensagemExpiracao = dataPrazoMensagemExpiracao;
		this.dataCadastroAcesso = dataCadastroAcesso;
		this.dataCadastroInicio = dataCadastroInicio;
		this.dataCadastroFim = dataCadastroFim;
		this.descricaoEmail = descricaoEmail;
		this.ultimaAlteracao = ultimaAlteracao;
		this.ultimoAcesso = ultimoAcesso;
		this.unidadeOrganizacional = unidadeOrganizacional;
		this.usuarioSituacao = usuarioSituacao;
		this.empresa = empresa;
		this.gerenciaRegional = gerenciaRegional;
		this.localidadeElo = localidadeElo;
		this.localidade = localidade;
		this.usuarioTipo = usuarioTipo;
		this.usuarioAbrangencia = usuarioAbrangencia;
		this.funcionario = funcionario;
		this.nomeUsuario = nomeUsuario;
		this.indicadorTipoRelatorioPadrao = indicadorTipoRelatorioPadrao;
		this.indicadorExibeMensagem = indicadorExibeMensagem;
	}

	/** full constructor */
	public Usuario(String login, String senha, Integer numeroAcessos, Short bloqueioAcesso, Date dataExpiracaoAcesso,
					Date dataPrazoMensagemExpiracao, Date dataCadastroAcesso, Date dataCadastroInicio, Date dataCadastroFim,
					String descricaoEmail, String cpf, Date ultimaAlteracao, Date ultimoAcesso,
					UnidadeOrganizacional unidadeOrganizacional, UsuarioSituacao usuarioSituacao, Empresa empresa,
					GerenciaRegional gerenciaRegional, Localidade localidadeElo, Localidade localidade, UsuarioTipo usuarioTipo,
					UsuarioAbrangencia usuarioAbrangencia, Funcionario funcionario, String nomeUsuario) {

		this.login = login;
		this.senha = senha;
		this.numeroAcessos = numeroAcessos;
		this.bloqueioAcesso = bloqueioAcesso;
		this.dataExpiracaoAcesso = dataExpiracaoAcesso;
		this.dataPrazoMensagemExpiracao = dataPrazoMensagemExpiracao;
		this.dataCadastroAcesso = dataCadastroAcesso;
		this.dataCadastroInicio = dataCadastroInicio;
		this.dataCadastroFim = dataCadastroFim;
		this.descricaoEmail = descricaoEmail;
		this.ultimaAlteracao = ultimaAlteracao;
		this.ultimoAcesso = ultimoAcesso;
		this.unidadeOrganizacional = unidadeOrganizacional;
		this.usuarioSituacao = usuarioSituacao;
		this.empresa = empresa;
		this.gerenciaRegional = gerenciaRegional;
		this.localidadeElo = localidadeElo;
		this.localidade = localidade;
		this.usuarioTipo = usuarioTipo;
		this.usuarioAbrangencia = usuarioAbrangencia;
		this.funcionario = funcionario;
		this.nomeUsuario = nomeUsuario;
		this.cpf = cpf;
	}

	public Short getIndicadorExibeMensagem(){

		return indicadorExibeMensagem;
	}

	public void setIndicadorExibeMensagem(Short indicadorExibeMensagem){

		this.indicadorExibeMensagem = indicadorExibeMensagem;
	}

	public Short getIndicadorTipoRelatorioPadrao(){

		return indicadorTipoRelatorioPadrao;
	}

	public void setIndicadorTipoRelatorioPadrao(Short indicadorTipoRelatorioPadrao){

		this.indicadorTipoRelatorioPadrao = indicadorTipoRelatorioPadrao;
	}

	/*
	 * public Usuario(String login, String senha, Integer numeroAcessos, Short
	 * bloqueioAcesso, Date dataExpiracaoAcesso, Date
	 * dataPrazoMensagemExpiracao, Date dataCadastroAcesso, Date
	 * dataCadastroInicio, Date dataCadastroFim, String descricaoEmail, Date
	 * ultimaAlteracao, Date ultimoAcesso, UnidadeOrganizacional unidadeEmpresa,
	 * UsuarioSituacao usuarioSituacao, Empresa empresa, GerenciaRegional
	 * gerenciaRegional, Localidade localidadeElo, Localidade localidade,
	 * UsuarioTipo usuarioTipo, UsuarioAbrangencia usuarioAbrangencia,
	 * Funcionario funcionario, String nomeUsuario, Date dataNascimento, String
	 * lembreteSenha, String cpf) { this.login = login; this.senha = senha;
	 * this.numeroAcessos = numeroAcessos; this.bloqueioAcesso = bloqueioAcesso;
	 * this.dataExpiracaoAcesso = dataExpiracaoAcesso;
	 * this.dataPrazoMensagemExpiracao = dataPrazoMensagemExpiracao;
	 * this.dataCadastroAcesso = dataCadastroAcesso; this.dataCadastroInicio =
	 * dataCadastroInicio; this.dataCadastroFim = dataCadastroFim;
	 * this.descricaoEmail = descricaoEmail; this.ultimaAlteracao =
	 * ultimaAlteracao; this.ultimoAcesso = ultimoAcesso; this.unidadeEmpresa =
	 * unidadeEmpresa; this.usuarioSituacao = usuarioSituacao; this.empresa =
	 * empresa; this.gerenciaRegional = gerenciaRegional; this.localidadeElo =
	 * localidadeElo; this.localidade = localidade; this.usuarioTipo =
	 * usuarioTipo; this.usuarioAbrangencia = usuarioAbrangencia;
	 * this.funcionario = funcionario; this.nomeUsuario = nomeUsuario;
	 * this.dataNascimento = dataNascimento; this.lembreteSenha = lembreteSenha;
	 * this.cpf = cpf; }
	 */
	/** default constructor */
	public Usuario() {

	}

	/** minimal constructor */
	public Usuario(UnidadeOrganizacional unidadeOrganizacional, UsuarioSituacao usuarioSituacao, Empresa empresa,
					GerenciaRegional gerenciaRegional, Localidade localidadeElo, Localidade localidade, UsuarioTipo usuarioTipo,
					UsuarioAbrangencia usuarioAbrangencia, Funcionario funcionario) {

		this.unidadeOrganizacional = unidadeOrganizacional;
		this.usuarioSituacao = usuarioSituacao;
		this.empresa = empresa;
		this.gerenciaRegional = gerenciaRegional;
		this.localidadeElo = localidadeElo;
		this.localidade = localidade;
		this.usuarioTipo = usuarioTipo;
		this.usuarioAbrangencia = usuarioAbrangencia;
		this.funcionario = funcionario;
	}

	public UnidadeOrganizacional getUnidadeOrganizacional(){

		return unidadeOrganizacional;
	}

	public void setUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional){

		this.unidadeOrganizacional = unidadeOrganizacional;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getLogin(){

		return this.login;
	}

	public void setLogin(String login){

		this.login = login;
	}

	public String getSenha(){

		return this.senha;
	}

	public void setSenha(String senha){

		this.senha = senha;
	}

	public Integer getNumeroAcessos(){

		return this.numeroAcessos;
	}

	public void setNumeroAcessos(Integer numeroAcessos){

		this.numeroAcessos = numeroAcessos;
	}

	public Short getBloqueioAcesso(){

		return this.bloqueioAcesso;
	}

	public void setBloqueioAcesso(Short bloqueioAcesso){

		this.bloqueioAcesso = bloqueioAcesso;
	}

	public Date getDataExpiracaoAcesso(){

		return this.dataExpiracaoAcesso;
	}

	public void setDataExpiracaoAcesso(Date dataExpiracaoAcesso){

		this.dataExpiracaoAcesso = dataExpiracaoAcesso;
	}

	public Date getDataPrazoMensagemExpiracao(){

		return this.dataPrazoMensagemExpiracao;
	}

	public void setDataPrazoMensagemExpiracao(Date dataPrazoMensagemExpiracao){

		this.dataPrazoMensagemExpiracao = dataPrazoMensagemExpiracao;
	}

	public Date getDataCadastroAcesso(){

		return this.dataCadastroAcesso;
	}

	public void setDataCadastroAcesso(Date dataCadastroAcesso){

		this.dataCadastroAcesso = dataCadastroAcesso;
	}

	public Date getDataCadastroInicio(){

		return this.dataCadastroInicio;
	}

	public void setDataCadastroInicio(Date dataCadastroInicio){

		this.dataCadastroInicio = dataCadastroInicio;
	}

	public Date getDataCadastroFim(){

		return this.dataCadastroFim;
	}

	public void setDataCadastroFim(Date dataCadastroFim){

		this.dataCadastroFim = dataCadastroFim;
	}

	public String getDescricaoEmail(){

		return this.descricaoEmail;
	}

	public void setDescricaoEmail(String descricaoEmail){

		this.descricaoEmail = descricaoEmail;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Date getUltimoAcesso(){

		return this.ultimoAcesso;
	}

	public void setUltimoAcesso(Date ultimoAcesso){

		this.ultimoAcesso = ultimoAcesso;
	}

	public UsuarioSituacao getUsuarioSituacao(){

		return this.usuarioSituacao;
	}

	public void setUsuarioSituacao(UsuarioSituacao usuarioSituacao){

		this.usuarioSituacao = usuarioSituacao;
	}

	public Empresa getEmpresa(){

		return this.empresa;
	}

	public void setEmpresa(Empresa empresa){

		this.empresa = empresa;
	}

	public GerenciaRegional getGerenciaRegional(){

		return this.gerenciaRegional;
	}

	public void setGerenciaRegional(GerenciaRegional gerenciaRegional){

		this.gerenciaRegional = gerenciaRegional;
	}

	public Localidade getLocalidadeElo(){

		return this.localidadeElo;
	}

	public void setLocalidadeElo(Localidade localidadeElo){

		this.localidadeElo = localidadeElo;
	}

	public Localidade getLocalidade(){

		return this.localidade;
	}

	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	public UsuarioTipo getUsuarioTipo(){

		return this.usuarioTipo;
	}

	public void setUsuarioTipo(UsuarioTipo usuarioTipo){

		this.usuarioTipo = usuarioTipo;
	}

	public UsuarioAbrangencia getUsuarioAbrangencia(){

		return this.usuarioAbrangencia;
	}

	public void setUsuarioAbrangencia(UsuarioAbrangencia usuarioAbrangencia){

		this.usuarioAbrangencia = usuarioAbrangencia;
	}

	public Funcionario getFuncionario(){

		return this.funcionario;
	}

	public void setFuncionario(Funcionario funcionario){

		this.funcionario = funcionario;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String getNomeUsuario(){

		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario){

		this.nomeUsuario = nomeUsuario;
	}

	public String getCpf(){

		return cpf;
	}

	public void setCpf(String cpf){

		this.cpf = cpf;
	}

	public Date getDataNascimento(){

		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento){

		this.dataNascimento = dataNascimento;
	}

	public String getLembreteSenha(){

		return lembreteSenha;
	}

	public void setLembreteSenha(String lembreteSenha){

		this.lembreteSenha = lembreteSenha;
	}

	public UnidadeNegocio getUnidadeNegocio(){

		return unidadeNegocio;
	}

	public void setUnidadeNegocio(UnidadeNegocio unidadeNegocio){

		this.unidadeNegocio = unidadeNegocio;
	}

	public boolean equals(Object arg){

		if(arg == null){
			return false;
		}
		if(!(arg instanceof Usuario)){
			return false;
		}
		return this.id.intValue() == ((Usuario) arg).getId().intValue();
	}

	public String getDescricaoUsuario(){

		String retorno = "";

		if(this.id != null){

			retorno = this.id.toString();
		}

		if(this.nomeUsuario != null){

			retorno += " - " + this.nomeUsuario.toString();
		}

		return retorno;
	}

}