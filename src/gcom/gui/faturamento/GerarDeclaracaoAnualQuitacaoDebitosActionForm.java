package gcom.gui.faturamento;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * [UC3013] Gerar Declara��o Anual Quita��o D�bitos
 * 
 * @author Hebert Falc�o
 * @created 28/04/2013
 */
public class GerarDeclaracaoAnualQuitacaoDebitosActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String idFaturamentoGrupo;

	public String getIdFaturamentoGrupo(){

		return idFaturamentoGrupo;
	}

	public void setIdFaturamentoGrupo(String idFaturamentoGrupo){

		this.idFaturamentoGrupo = idFaturamentoGrupo;
	}

}
