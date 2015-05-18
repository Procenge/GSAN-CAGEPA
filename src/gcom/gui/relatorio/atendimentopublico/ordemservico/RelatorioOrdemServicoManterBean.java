package gcom.gui.relatorio.atendimentopublico.ordemservico;

import gcom.relatorio.RelatorioBean;

public class RelatorioOrdemServicoManterBean
				implements RelatorioBean {
	
	
	
	private String numeroOS;
	private String situacaoOS;
	private String dtGeracaoOs;
	private String imovelId;
	private String unidadeAtual;
	private String numeroRA;
	private String situacaoRA;
	private String dtAberturaRa;
	private String documentoCobranca;
	private String enderecoOcorrecia;
	private String solicitante; 
	private String foneDDD;
	private String fone;
	private String foneRamal;
	private String observacaoOS;
	private String observacaoRA;
	private String total;

	private String tipoServico;

	public String getNumeroOS(){

		return numeroOS;
	}

	public void setNumeroOS(String numeroOS){

		this.numeroOS = numeroOS;
	}

	public String getSituacaoOS(){

		return situacaoOS;
	}

	public void setSituacaoOS(String situacaoOS){

		this.situacaoOS = situacaoOS;
	}

	public String getDtGeracaoOs(){

		return dtGeracaoOs;
	}

	public void setDtGeracaoOs(String dtGeracaoOs){

		this.dtGeracaoOs = dtGeracaoOs;
	}

	public String getImovelId(){

		return imovelId;
	}

	public void setImovelId(String imovelId){

		this.imovelId = imovelId;
	}

	public String getUnidadeAtual(){

		return unidadeAtual;
	}

	public void setUnidadeAtual(String unidadeAtual){

		this.unidadeAtual = unidadeAtual;
	}

	public String getNumeroRA(){

		return numeroRA;
	}

	public void setNumeroRA(String numeroRA){

		this.numeroRA = numeroRA;
	}

	public String getSituacaoRA(){

		return situacaoRA;
	}

	public void setSituacaoRA(String situacaoRA){

		this.situacaoRA = situacaoRA;
	}

	public String getDtAberturaRa(){

		return dtAberturaRa;
	}

	public void setDtAberturaRa(String dtAberturaRa){

		this.dtAberturaRa = dtAberturaRa;
	}

	public String getDocumentoCobranca(){

		return documentoCobranca;
	}

	public void setDocumentoCobranca(String documentoCobranca){

		this.documentoCobranca = documentoCobranca;
	}

	public String getEnderecoOcorrecia(){

		return enderecoOcorrecia;
	}

	public void setEnderecoOcorrecia(String enderecoOcorrecia){

		this.enderecoOcorrecia = enderecoOcorrecia;
	}

	public String getSolicitante(){

		return solicitante;
	}

	public void setSolicitante(String solicitante){

		this.solicitante = solicitante;
	}

	public String getFoneDDD(){

		return foneDDD;
	}

	public void setFoneDDD(String foneDDD){

		this.foneDDD = foneDDD;
	}

	public String getFone(){

		return fone;
	}

	public void setFone(String fone){

		this.fone = fone;
	}

	public String getFoneRamal(){

		return foneRamal;
	}

	public void setFoneRamal(String foneRamal){

		this.foneRamal = foneRamal;
	}

	public String getObservacaoOS(){

		return observacaoOS;
	}

	public void setObservacaoOS(String observacaoOS){

		this.observacaoOS = observacaoOS;
	}

	public String getObservacaoRA(){

		return observacaoRA;
	}

	public void setObservacaoRA(String observacaoRA){

		this.observacaoRA = observacaoRA;
	}

	public String getTotal(){

		return total;
	}

	public void setTotal(String total){

		this.total = total;
	}

	public String getTipoServico(){

		return tipoServico;
	}

	public void setTipoServico(String tipoServico){

		this.tipoServico = tipoServico;
	}

	public RelatorioOrdemServicoManterBean(String numeroOS, String situacaoOS, String dtGeracaoOs, String imovelId, String unidadeAtual,
											String numeroRA, String situacaoRA, String dtAberturaRa, String documentoCobranca,
											String enderecoOcorrecia, String solicitante, String foneDDD, String fone, String foneRamal,
											String observacaoOS, String observacaoRA, String total, String tipoServico) {

		super();
		this.numeroOS = numeroOS;
		this.situacaoOS = situacaoOS;
		this.dtGeracaoOs = dtGeracaoOs;
		this.imovelId = imovelId;
		this.unidadeAtual = unidadeAtual;
		this.numeroRA = numeroRA;
		this.situacaoRA = situacaoRA;
		this.dtAberturaRa = dtAberturaRa;
		this.documentoCobranca = documentoCobranca;
		this.enderecoOcorrecia = enderecoOcorrecia;
		this.solicitante = solicitante;
		this.foneDDD = foneDDD;
		this.fone = fone;
		this.foneRamal = foneRamal;
		this.observacaoOS = observacaoOS;
		this.observacaoRA = observacaoRA;
		this.total = total;
		this.tipoServico = tipoServico;
	}



}
