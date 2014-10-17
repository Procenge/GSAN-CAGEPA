
package gcom.gui.atendimentopublico.registroatendimento;

import org.apache.struts.validator.ValidatorActionForm;

public class PesquisarMensagemTipoSolicitacaoEspecificacaoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String descricao;

	private String tipoPesquisa;

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getTipoPesquisa(){

		return tipoPesquisa;
	}

	public void setTipoPesquisa(String tipoPesquisa){

		this.tipoPesquisa = tipoPesquisa;
	}

}