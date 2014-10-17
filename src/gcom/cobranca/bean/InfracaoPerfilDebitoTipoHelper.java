
package gcom.cobranca.bean;

public class InfracaoPerfilDebitoTipoHelper {

	private String id;

	private String idDebitoTipo;

	private String tipoDebitoDescricao;

	private String lancamentoAtivoDesc;

	private String lancamentoAtivo;

	private String porcentagemDesconto;

	private String fatorMultiplicador;

	private String idInfracaoPerfil;

	public String getIdDebitoTipo(){

		return idDebitoTipo;
	}

	public void setIdDebitoTipo(String idDebitoTipo){

		this.idDebitoTipo = idDebitoTipo;
	}

	public String getLancamentoAtivoDesc(){

		return lancamentoAtivoDesc;
	}

	public void setLancamentoAtivoDesc(String lancamentoAtivoDesc){

		this.lancamentoAtivoDesc = lancamentoAtivoDesc;
	}

	public String getIdInfracaoPerfil(){

		return idInfracaoPerfil;
	}

	public void setIdInfracaoPerfil(String idInfracaoPerfil){

		this.idInfracaoPerfil = idInfracaoPerfil;
	}

	public String getId(){

		return id;
	}

	public void setId(String id){

		this.id = id;
	}

	public String getTipoDebitoDescricao(){

		return tipoDebitoDescricao;
	}

	public void setTipoDebitoDescricao(String tipoDebitoDescricao){

		this.tipoDebitoDescricao = tipoDebitoDescricao;
	}

	public String getLancamentoAtivo(){

		return lancamentoAtivo;
	}

	public void setLancamentoAtivo(String lancamentoAtivo){

		this.lancamentoAtivo = lancamentoAtivo;
	}

	public String getPorcentagemDesconto(){

		return porcentagemDesconto;
	}

	public void setPorcentagemDesconto(String porcentagemDesconto){

		this.porcentagemDesconto = porcentagemDesconto;
	}

	public String getFatorMultiplicador(){

		return fatorMultiplicador;
	}

	public void setFatorMultiplicador(String fatorMultiplicador){

		this.fatorMultiplicador = fatorMultiplicador;
	}

}
