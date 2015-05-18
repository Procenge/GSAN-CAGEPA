
package gcom.relatorio.atendimentopublico;

import gcom.relatorio.RelatorioBean;

import java.util.Date;


public class RelatorioResumoOrdensServicoDocCobBean
				implements RelatorioBean {

	private String ordemServico;

	private String tipoServico;

	private String numeroRA;

	private String imovel;

	private String situacaoDocumentoCobranca;

	private Date dataGeracao;

	private Date dataEmissao;

	private String unidadeAtual;
	
	private Integer totalRegistros;

	private String numeroDocumentoCobranca;

	
	public String getOrdemServico(){
	
		return ordemServico;
	}

	
	public void setOrdemServico(String ordemServico){
	
		this.ordemServico = ordemServico;
	}

	
	public String getTipoServico(){
	
		return tipoServico;
	}

	
	public void setTipoServico(String tipoServico){
	
		this.tipoServico = tipoServico;
	}

	
	public String getNumeroRA(){
	
		return numeroRA;
	}

	
	public void setNumeroRA(String numeroRA){
	
		this.numeroRA = numeroRA;
	}

	
	public String getImovel(){
	
		return imovel;
	}

	
	public void setImovel(String imovel){
	
		this.imovel = imovel;
	}

	
	public String getSituacaoDocumentoCobranca(){
	
		return situacaoDocumentoCobranca;
	}

	
	public void setSituacaoDocumentoCobranca(String situacaoDocumentoCobranca){
	
		this.situacaoDocumentoCobranca = situacaoDocumentoCobranca;
	}

	
	
	public Date getDataGeracao(){

		return dataGeracao;
	}


	
	public void setDataGeracao(Date dataGeracao){
	
		this.dataGeracao = dataGeracao;
	}


	
	public Date getDataEmissao(){
	
		return dataEmissao;
	}


	
	public void setDataEmissao(Date dataEmissao){
	
		this.dataEmissao = dataEmissao;
	}


	public String getUnidadeAtual(){
	
		return unidadeAtual;
	}

	
	public void setUnidadeAtual(String unidadeAtual){
	
		this.unidadeAtual = unidadeAtual;
	}


	public Integer getTotalRegistros(){

		return totalRegistros;
	}

	public void setTotalRegistros(Integer totalRegistros){

		this.totalRegistros = totalRegistros;
	}

	public String getNumeroDocumentoCobranca(){

		return numeroDocumentoCobranca;
	}

	public void setNumeroDocumentoCobranca(String numeroDocumentoCobranca){

		this.numeroDocumentoCobranca = numeroDocumentoCobranca;
	}

}
