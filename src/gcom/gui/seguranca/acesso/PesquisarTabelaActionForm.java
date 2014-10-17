
package gcom.gui.seguranca.acesso;

import org.apache.struts.validator.ValidatorActionForm;

public class PesquisarTabelaActionForm
				extends ValidatorActionForm {

	private String idTabela;

	private String descricaoTabela;

	private String nomeTabela;

	private String ultimaAlteracao;

	public String getIdTabela(){

		return idTabela;
	}

	public void setIdTabela(String idTabela){

		this.idTabela = idTabela;
	}

	public String getDescricaoTabela(){

		return descricaoTabela;
	}

	public void setDescricaoTabela(String descricaoTabela){

		this.descricaoTabela = descricaoTabela;
	}

	public String getNomeTabela(){

		return nomeTabela;
	}

	public void setNomeTabela(String nomeTabela){

		this.nomeTabela = nomeTabela;
	}

	public String getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(String ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

}
