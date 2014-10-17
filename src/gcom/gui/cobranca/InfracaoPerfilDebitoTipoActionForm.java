
package gcom.gui.cobranca;

import org.apache.struts.action.ActionForm;

public class InfracaoPerfilDebitoTipoActionForm
				extends ActionForm {

	private String idPerfil;

	private String idTipoDebito;

	private String descricaoDebitoTipo;

	private String indicadorLancamentoAtivo;

	private String pcDesconto;

	private String fatorMultiplicador;

	public String getIdPerfil(){

		return idPerfil;
	}

	public void setIdPerfil(String idPerfil){

		this.idPerfil = idPerfil;
	}

	public String getIdTipoDebito(){

		return idTipoDebito;
	}

	public void setIdTipoDebito(String debitoTipo){

		this.idTipoDebito = debitoTipo;
	}

	public String getDescricaoDebitoTipo(){

		return descricaoDebitoTipo;
	}

	public void setDescricaoDebitoTipo(String descricaoDebitoTipo){

		this.descricaoDebitoTipo = descricaoDebitoTipo;
	}

	public String getIndicadorLancamentoAtivo(){

		return indicadorLancamentoAtivo;
	}

	public void setIndicadorLancamentoAtivo(String indicadorLancamentoAtivo){

		this.indicadorLancamentoAtivo = indicadorLancamentoAtivo;
	}

	public String getPcDesconto(){

		return pcDesconto;
	}

	public void setPcDesconto(String pcDesconto){

		this.pcDesconto = pcDesconto;
	}

	public String getFatorMultiplicador(){

		return fatorMultiplicador;
	}

	public void setFatorMultiplicador(String fatorMultiplicador){

		this.fatorMultiplicador = fatorMultiplicador;
	}
}
