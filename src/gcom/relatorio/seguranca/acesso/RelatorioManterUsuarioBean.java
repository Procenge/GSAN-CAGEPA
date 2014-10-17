
package gcom.relatorio.seguranca.acesso;

import gcom.relatorio.RelatorioBean;

/**
 * [UC] Manter Usuario
 * 
 * @author Eduardo Oliveira
 * @date 24/01/2014
 */

public class RelatorioManterUsuarioBean
				implements RelatorioBean {

	private String nomeUsuario;

	private String loginUsuario;

	private String usuarioGrupo;

	private String usuarioTipo;

	private String unidadeOrganizacional;

	private String situacaoUsuario;

	private String abrangenciaAcesso;

	private String dataCadastroAcesso;

	private String dataExpiracaoAcesso;

	public RelatorioManterUsuarioBean(String nomeUsuario, String loginUsuario, String usuarioGrupo, String usuarioTipo,
										String unidadeOrganizacional, String situacaoUsuario,
										String abrangenciaAcesso, String dataCadastroAcesso, String dataExpiracaoAcesso) {

		this.nomeUsuario = nomeUsuario;
		this.loginUsuario = loginUsuario;
		this.usuarioGrupo = usuarioGrupo;
		this.usuarioTipo = usuarioTipo;
		this.unidadeOrganizacional = unidadeOrganizacional;
		this.situacaoUsuario = situacaoUsuario;
		this.abrangenciaAcesso = abrangenciaAcesso;
		this.dataCadastroAcesso = dataCadastroAcesso;
		this.dataExpiracaoAcesso = dataExpiracaoAcesso;
	}

	public RelatorioManterUsuarioBean() {

		super();
	}

	public String getNomeUsuario(){

		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario){

		this.nomeUsuario = nomeUsuario;
	}

	public String getLoginUsuario(){

		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario){

		this.loginUsuario = loginUsuario;
	}

	public String getUsuarioGrupo(){

		return usuarioGrupo;
	}

	public void setUsuarioGrupo(String usuarioGrupo){

		this.usuarioGrupo = usuarioGrupo;
	}

	public String getUsuarioTipo(){

		return usuarioTipo;
	}

	public void setUsuarioTipo(String usuarioTipo){

		this.usuarioTipo = usuarioTipo;
	}

	public String getUnidadeOrganizacional(){

		return unidadeOrganizacional;
	}

	public void setUnidadeOrganizacional(String unidadeOrganizacional){

		this.unidadeOrganizacional = unidadeOrganizacional;
	}

	public String getSituacaoUsuario(){

		return situacaoUsuario;
	}

	public void setSituacaoUsuario(String situacaoUsuario){

		this.situacaoUsuario = situacaoUsuario;
	}

	public String getAbrangenciaAcesso(){

		return abrangenciaAcesso;
	}

	public void setAbrangenciaAcesso(String abrangenciaAcesso){

		this.abrangenciaAcesso = abrangenciaAcesso;
	}

	public String getDataCadastroAcesso(){

		return dataCadastroAcesso;
	}

	public void setDataCadastroAcesso(String dataCadastroAcesso){

		this.dataCadastroAcesso = dataCadastroAcesso;
	}

	public String getDataExpiracaoAcesso(){

		return dataExpiracaoAcesso;
	}

	public void setDataExpiracaoAcesso(String dataExpiracaoAcesso){

		this.dataExpiracaoAcesso = dataExpiracaoAcesso;
	}

	// @Override
	// public boolean equals(Object obj){
	//
	// RelatorioManterUsuarioBean relatorioManterUsuarioBean = (RelatorioManterUsuarioBean) obj;
	// return this.loginUsuario.equals(relatorioManterUsuarioBean.getLoginUsuario());
	//
	// }

}
