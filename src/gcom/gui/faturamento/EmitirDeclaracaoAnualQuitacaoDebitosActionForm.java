package gcom.gui.faturamento;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * [UC3014] Emitir Declaração Anual Quitação Débitos
 * 
 * @author Hebert Falcão
 * @created 27/04/2013
 */
public class EmitirDeclaracaoAnualQuitacaoDebitosActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String idFaturamentoGrupo;

	private String idImovel;

	private String inscricaoImovel;

	private String anoBaseDeclaracaoInformado;

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getIdFaturamentoGrupo(){

		return idFaturamentoGrupo;
	}

	public void setIdFaturamentoGrupo(String idFaturamentoGrupo){

		this.idFaturamentoGrupo = idFaturamentoGrupo;
	}

	public String getAnoBaseDeclaracaoInformado(){

		return anoBaseDeclaracaoInformado;
	}

	public void setAnoBaseDeclaracaoInformado(String anoBaseDeclaracaoInformado){

		this.anoBaseDeclaracaoInformado = anoBaseDeclaracaoInformado;
	}

}
