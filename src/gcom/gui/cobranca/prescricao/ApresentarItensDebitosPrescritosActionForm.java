
package gcom.gui.cobranca.prescricao;

import org.apache.struts.action.ActionForm;

/**
 * [UC3140] Acompanhar Imóveis com Débitos Prescritos
 * [SB0001] - Apresentar Itens de Débitos Prescritos
 * 
 * @author Anderson Italo
 * @date 04/04/2014
 */
public class ApresentarItensDebitosPrescritosActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String idImovel;
	private String inscricaoFormatadaImovel;
	private String descricaoLigacaoAguaSituacao;
	private String descricaoLigacaoEsgotoSituacao;
	private String descricaoImovelPerfil;
	private String enderecoImovel;


	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getInscricaoFormatadaImovel(){

		return inscricaoFormatadaImovel;
	}

	public void setInscricaoFormatadaImovel(String inscricaoFormatadaImovel){

		this.inscricaoFormatadaImovel = inscricaoFormatadaImovel;
	}

	public String getDescricaoLigacaoAguaSituacao(){

		return descricaoLigacaoAguaSituacao;
	}

	public void setDescricaoLigacaoAguaSituacao(String descricaoLigacaoAguaSituacao){

		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
	}

	public String getDescricaoLigacaoEsgotoSituacao(){

		return descricaoLigacaoEsgotoSituacao;
	}

	public void setDescricaoLigacaoEsgotoSituacao(String descricaoLigacaoEsgotoSituacao){

		this.descricaoLigacaoEsgotoSituacao = descricaoLigacaoEsgotoSituacao;
	}

	public String getDescricaoImovelPerfil(){

		return descricaoImovelPerfil;
	}

	public void setDescricaoImovelPerfil(String descricaoImovelPerfil){

		this.descricaoImovelPerfil = descricaoImovelPerfil;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public void limpar(){

		this.setIdImovel("");
		this.setInscricaoFormatadaImovel("");
		this.setDescricaoLigacaoAguaSituacao("");
		this.setDescricaoLigacaoEsgotoSituacao("");
		this.setDescricaoImovelPerfil("");
		this.setEnderecoImovel("");
	}
}
