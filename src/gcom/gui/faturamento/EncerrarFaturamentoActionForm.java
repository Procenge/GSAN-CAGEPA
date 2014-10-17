
package gcom.gui.faturamento;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * [UC3055] Encerrar Faturamento
 * 
 * @author Hebert Falcão
 * @date 01/04/2012
 */
public class EncerrarFaturamentoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String referencia;

	private String grupoSituacao;

	private String idFaturamentoGrupo;

	public String getReferencia(){

		return referencia;
	}

	public void setReferencia(String referencia){

		this.referencia = referencia;
	}

	public String getGrupoSituacao(){

		return grupoSituacao;
	}

	public void setGrupoSituacao(String grupoSituacao){

		this.grupoSituacao = grupoSituacao;
	}

	public String getIdFaturamentoGrupo(){

		return idFaturamentoGrupo;
	}

	public void setIdFaturamentoGrupo(String idFaturamentoGrupo){

		this.idFaturamentoGrupo = idFaturamentoGrupo;
	}


}
