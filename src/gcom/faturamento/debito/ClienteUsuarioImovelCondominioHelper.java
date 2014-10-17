package gcom.faturamento.debito;

/**
 * [UC3143] Inserir Débito a Cobrar de Rateio por Macromedidor.
 * 
 * @author Ado Rocha
 * @date 26/02/2014
 */

public class ClienteUsuarioImovelCondominioHelper {

	private Integer idImovel;

	private String nomeCliente;

	private String descricaoLigacaoAguaSituacao;

	private String descricaoLigacaoEsgotoSituacao;

	public ClienteUsuarioImovelCondominioHelper() {

	}

	public ClienteUsuarioImovelCondominioHelper(Integer idImovel, String nomeCliente, String descricaoLigacaoAguaSituacao,
												String descricaoLigacaoEsgotoSituacao) {

		super();
		this.idImovel = idImovel;
		this.nomeCliente = nomeCliente;
		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
		this.descricaoLigacaoEsgotoSituacao = descricaoLigacaoEsgotoSituacao;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
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

}
