
package gcom.gui.atendimentopublico.ordemservico;

import org.apache.struts.validator.ValidatorActionForm;

public class FiltrarOrdemProcessoRepavimentacaoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private Integer idUnidadeResponsavel;

	private Integer idSituacaoRetorno;

	private String dataEncerramentoOSInicio;

	private String dataEncerramentoOSFim;

	private String dataRetornoServicoInicio;

	private String dataRetornoServicoFim;

	public String getDataEncerramentoOSFim(){

		return dataEncerramentoOSFim;
	}

	public void setDataEncerramentoOSFim(String dataEncerramentoOSFim){

		this.dataEncerramentoOSFim = dataEncerramentoOSFim;
	}

	public String getDataEncerramentoOSInicio(){

		return dataEncerramentoOSInicio;
	}

	public void setDataEncerramentoOSInicio(String dataEncerramentoOSInicio){

		this.dataEncerramentoOSInicio = dataEncerramentoOSInicio;
	}

	public String getDataRetornoServicoFim(){

		return dataRetornoServicoFim;
	}

	public void setDataRetornoServicoFim(String dataRetornoServicoFim){

		this.dataRetornoServicoFim = dataRetornoServicoFim;
	}

	public String getDataRetornoServicoInicio(){

		return dataRetornoServicoInicio;
	}

	public void setDataRetornoServicoInicio(String dataRetornoServicoInicio){

		this.dataRetornoServicoInicio = dataRetornoServicoInicio;
	}

	public Integer getIdSituacaoRetorno(){

		return idSituacaoRetorno;
	}

	public void setIdSituacaoRetorno(Integer idSituacaoRetorno){

		this.idSituacaoRetorno = idSituacaoRetorno;
	}

	public Integer getIdUnidadeResponsavel(){

		return idUnidadeResponsavel;
	}

	public void setIdUnidadeResponsavel(Integer idUnidadeResponsavel){

		this.idUnidadeResponsavel = idUnidadeResponsavel;
	}

}