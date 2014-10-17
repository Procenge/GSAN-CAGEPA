package gcom.relatorio.atendimentopublico;

import gcom.relatorio.RelatorioBean;


public class RelatorioDadosComandoOsSeletivaBean implements RelatorioBean {
	

	private String os;

	private String servicoTipoOs;

	private String imovelOs;

	private String localidadeOs;

	private String setorOs;

	private String quadraOs;

	private String situacaoOs;

	private String dataGeracaoOs;
	
	private String unidadeGeracaoOs;

	private String dataEncerramentoOs;

	private String unidadeEncerramentoOs;
	
	private String motivoEncerramentoOs;

	public RelatorioDadosComandoOsSeletivaBean(String os, String servicoTipoOs, String imovelOs, String localidadeOs, String setorOs,
												String quadraOs, String situacaoOs, String dataGeracaoOs, String unidadeGeracaoOs,
												String dataEncerramentoOs, String unidadeEncerramentoOs, String motivoEncerramentoOs) {

		super();
		this.os = os;
		this.servicoTipoOs = servicoTipoOs;
		this.imovelOs = imovelOs;
		this.localidadeOs = localidadeOs;
		this.setorOs = setorOs;
		this.quadraOs = quadraOs;
		this.situacaoOs = situacaoOs;
		this.dataGeracaoOs = dataGeracaoOs;
		this.unidadeGeracaoOs = unidadeGeracaoOs;
		this.dataEncerramentoOs = dataEncerramentoOs;
		this.unidadeEncerramentoOs = unidadeEncerramentoOs;
		this.motivoEncerramentoOs = motivoEncerramentoOs;
	}

	public String getOs(){

		return os;
	}

	public void setOs(String os){

		this.os = os;
	}

	public String getServicoTipoOs(){

		return servicoTipoOs;
	}

	public void setServicoTipoOs(String servicoTipoOs){

		this.servicoTipoOs = servicoTipoOs;
	}

	public String getImovelOs(){

		return imovelOs;
	}

	public void setImovelOs(String imovelOs){

		this.imovelOs = imovelOs;
	}

	public String getLocalidadeOs(){

		return localidadeOs;
	}

	public void setLocalidadeOs(String localidadeOs){

		this.localidadeOs = localidadeOs;
	}

	public String getSetorOs(){

		return setorOs;
	}

	public void setSetorOs(String setorOs){

		this.setorOs = setorOs;
	}

	public String getQuadraOs(){

		return quadraOs;
	}

	public void setQuadraOs(String quadraOs){

		this.quadraOs = quadraOs;
	}

	public String getSituacaoOs(){

		return situacaoOs;
	}

	public void setSituacaoOs(String situacaoOs){

		this.situacaoOs = situacaoOs;
	}

	public String getDataGeracaoOs(){

		return dataGeracaoOs;
	}

	public void setDataGeracaoOs(String dataGeracaoOs){

		this.dataGeracaoOs = dataGeracaoOs;
	}

	public String getUnidadeGeracaoOs(){

		return unidadeGeracaoOs;
	}

	public void setUnidadeGeracaoOs(String unidadeGeracaoOs){

		this.unidadeGeracaoOs = unidadeGeracaoOs;
	}

	public String getDataEncerramentoOs(){

		return dataEncerramentoOs;
	}

	public void setDataEncerramentoOs(String dataEncerramentoOs){

		this.dataEncerramentoOs = dataEncerramentoOs;
	}

	public String getUnidadeEncerramentoOs(){

		return unidadeEncerramentoOs;
	}

	public void setUnidadeEncerramentoOs(String unidadeEncerramentoOs){

		this.unidadeEncerramentoOs = unidadeEncerramentoOs;
	}

	public String getMotivoEncerramentoOs(){

		return motivoEncerramentoOs;
	}

	public void setMotivoEncerramentoOs(String motivoEncerramentoOs){

		this.motivoEncerramentoOs = motivoEncerramentoOs;
	}


}
