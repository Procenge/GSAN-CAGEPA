
package gcom.gui.operacional.cadastro;

import org.apache.struts.action.DynaActionForm;

/**
 * [UC0074] Alterar Inscrição de Imóvel
 * 
 * @author Carlos Chrystian
 * @date 02/02/2012
 */

public class FiltrarRelacaoImoveisComMudancaDaQuadraActionForm
				extends DynaActionForm {

	private static final long serialVersionUID = 1L;

	private String localidadeOrigemID;

	private String localidadeDestinoID;

	private String setorComercialOrigemCD;

	private String setorComercialDestinoCD;

	private String setorComercialOrigemID;

	private String setorComercialDestinoID;

	private String quadraOrigemNM;

	private String quadraDestinoNM;

	private String loteOrigem;

	private String loteDestino;

	private String totalImovelMesmaRota;

	private String totalImovelRotaDiferente;

	private String indicadorAlteracaoRota;

	// ==========================================================
	// ******** Métodos Getters e Setters ***********************
	// ==========================================================

	public String getLocalidadeOrigemID(){

		return localidadeOrigemID;
	}

	public void setLocalidadeOrigemID(String localidadeOrigemID){

		this.localidadeOrigemID = localidadeOrigemID;
	}

	public String getLocalidadeDestinoID(){

		return localidadeDestinoID;
	}

	public void setLocalidadeDestinoID(String localidadeDestinoID){

		this.localidadeDestinoID = localidadeDestinoID;
	}

	public String getSetorComercialOrigemCD(){

		return setorComercialOrigemCD;
	}

	public void setSetorComercialOrigemCD(String setorComercialOrigemCD){

		this.setorComercialOrigemCD = setorComercialOrigemCD;
	}

	public String getSetorComercialDestinoCD(){

		return setorComercialDestinoCD;
	}

	public void setSetorComercialDestinoCD(String setorComercialDestinoCD){

		this.setorComercialDestinoCD = setorComercialDestinoCD;
	}

	public String getSetorComercialOrigemID(){

		return setorComercialOrigemID;
	}

	public void setSetorComercialOrigemID(String setorComercialOrigemID){

		this.setorComercialOrigemID = setorComercialOrigemID;
	}

	public String getSetorComercialDestinoID(){

		return setorComercialDestinoID;
	}

	public void setSetorComercialDestinoID(String setorComercialDestinoID){

		this.setorComercialDestinoID = setorComercialDestinoID;
	}

	public String getQuadraOrigemNM(){

		return quadraOrigemNM;
	}

	public void setQuadraOrigemNM(String quadraOrigemNM){

		this.quadraOrigemNM = quadraOrigemNM;
	}

	public String getQuadraDestinoNM(){

		return quadraDestinoNM;
	}

	public void setQuadraDestinoNM(String quadraDestinoNM){

		this.quadraDestinoNM = quadraDestinoNM;
	}

	public String getTotalImovelMesmaRota(){

		return totalImovelMesmaRota;
	}

	public void setTotalImovelMesmaRota(String totalImovelMesmaRota){

		this.totalImovelMesmaRota = totalImovelMesmaRota;
	}

	public String getTotalImovelRotaDiferente(){

		return totalImovelRotaDiferente;
	}

	public void setTotalImovelRotaDiferente(String totalImovelRotaDiferente){

		this.totalImovelRotaDiferente = totalImovelRotaDiferente;
	}

	public String getIndicadorAlteracaoRota(){

		return indicadorAlteracaoRota;
	}

	public void setIndicadorAlteracaoRota(String indicadorAlteracaoRota){

		this.indicadorAlteracaoRota = indicadorAlteracaoRota;
	}

	public String getLoteOrigem(){

		return loteOrigem;
	}

	public void setLoteOrigem(String loteOrigem){

		this.loteOrigem = loteOrigem;
	}

	public String getLoteDestino(){

		return loteDestino;
	}

	public void setLoteDestino(String loteDestino){

		this.loteDestino = loteDestino;
	}

}
