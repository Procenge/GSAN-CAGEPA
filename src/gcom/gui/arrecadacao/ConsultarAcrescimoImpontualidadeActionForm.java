/**
 * 
 */

package gcom.gui.arrecadacao;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno Ferreira dos Santos
 * @date 26/09/2011
 */
public class ConsultarAcrescimoImpontualidadeActionForm
				extends ActionForm {

	private String idImovel;

	private String inscricaoImovel;

	private String situacaoAguaImovel;

	private String situacaoEsgotoImovel;

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setSituacaoAguaImovel(String situacaoAguaImovel){

		this.situacaoAguaImovel = situacaoAguaImovel;
	}

	public String getSituacaoAguaImovel(){

		return situacaoAguaImovel;
	}

	public void setSituacaoEsgotoImovel(String situacaoEsgotoImovel){

		this.situacaoEsgotoImovel = situacaoEsgotoImovel;
	}

	public String getSituacaoEsgotoImovel(){

		return situacaoEsgotoImovel;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request){

		// TODO Auto-generated method stub
		super.reset(mapping, request);

		this.idImovel = "";

		this.inscricaoImovel = "";

		this.situacaoAguaImovel = "";

		this.situacaoEsgotoImovel = "";

	}

}
