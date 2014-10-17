
package gcom.atendimentopublico.ordemservico.bean;

/**
 * @author Anderson Italo
 * @date 24/05/2011
 *       Bean do Relatório de Ordem de Serviço de Ligação de Água
 */
public class RelatorioOrdemServicoLigacaoAguaBean
				extends RelatorioOrdemServicoEstruturaBean {

	private String qtdEconomiasResidencial;

	private String qtdEconomiasComercial;

	private String qtdEconomiasPublica;

	private String qtdEconomiasIndustrial;

	private String reservatorio;

	private String piscina;

	private String fonteAbastecimento;

	private String qtdOcupantes;

	private String qtdPontosUtilizados;

	private String numeroHidrometro;

	private String marcaHidrometro;

	private String tipoHidrometro;

	private String dataInstalacaoHidrometro;

	private String localInstalacaoHidrometro;

	private String capacidadeHidrometro;

	private String tipoProtecaoHidrometro;

	private String diametroHidrometro;

	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	public String getMarcaHidrometro(){

		return marcaHidrometro;
	}

	public void setMarcaHidrometro(String marcaHidrometro){

		this.marcaHidrometro = marcaHidrometro;
	}

	public String getTipoHidrometro(){

		return tipoHidrometro;
	}

	public void setTipoHidrometro(String tipoHidrometro){

		this.tipoHidrometro = tipoHidrometro;
	}

	public String getDataInstalacaoHidrometro(){

		return dataInstalacaoHidrometro;
	}

	public void setDataInstalacaoHidrometro(String dataInstalacaoHidrometro){

		this.dataInstalacaoHidrometro = dataInstalacaoHidrometro;
	}

	public String getLocalInstalacaoHidrometro(){

		return localInstalacaoHidrometro;
	}

	public void setLocalInstalacaoHidrometro(String localInstalacaoHidrometro){

		this.localInstalacaoHidrometro = localInstalacaoHidrometro;
	}

	public String getCapacidadeHidrometro(){

		return capacidadeHidrometro;
	}

	public void setCapacidadeHidrometro(String capacidadeHidrometro){

		this.capacidadeHidrometro = capacidadeHidrometro;
	}

	public String getTipoProtecaoHidrometro(){

		return tipoProtecaoHidrometro;
	}

	public void setTipoProtecaoHidrometro(String tipoProtecaoHidrometro){

		this.tipoProtecaoHidrometro = tipoProtecaoHidrometro;
	}

	public String getDiametroHidrometro(){

		return diametroHidrometro;
	}

	public void setDiametroHidrometro(String diametroHidrometro){

		this.diametroHidrometro = diametroHidrometro;
	}

	public String getQtdEconomiasResidencial(){

		return qtdEconomiasResidencial;
	}

	public void setQtdEconomiasResidencial(String qtdEconomiasResidencial){

		this.qtdEconomiasResidencial = qtdEconomiasResidencial;
	}

	public String getQtdEconomiasComercial(){

		return qtdEconomiasComercial;
	}

	public void setQtdEconomiasComercial(String qtdEconomiasComercial){

		this.qtdEconomiasComercial = qtdEconomiasComercial;
	}

	public String getQtdEconomiasPublica(){

		return qtdEconomiasPublica;
	}

	public void setQtdEconomiasPublica(String qtdEconomiasPublica){

		this.qtdEconomiasPublica = qtdEconomiasPublica;
	}

	public String getQtdEconomiasIndustrial(){

		return qtdEconomiasIndustrial;
	}

	public void setQtdEconomiasIndustrial(String qtdEconomiasIndustrial){

		this.qtdEconomiasIndustrial = qtdEconomiasIndustrial;
	}

	public String getReservatorio(){

		return reservatorio;
	}

	public void setReservatorio(String reservatorio){

		this.reservatorio = reservatorio;
	}

	public String getPiscina(){

		return piscina;
	}

	public void setPiscina(String piscina){

		this.piscina = piscina;
	}

	public String getFonteAbastecimento(){

		return fonteAbastecimento;
	}

	public void setFonteAbastecimento(String fonteAbastecimento){

		this.fonteAbastecimento = fonteAbastecimento;
	}

	public String getQtdOcupantes(){

		return qtdOcupantes;
	}

	public void setQtdOcupantes(String qtdOcupantes){

		this.qtdOcupantes = qtdOcupantes;
	}

	public String getQtdPontosUtilizados(){

		return qtdPontosUtilizados;
	}

	public void setQtdPontosUtilizados(String qtdPontosUtilizados){

		this.qtdPontosUtilizados = qtdPontosUtilizados;
	}

}
