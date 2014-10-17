/**
 * 
 */
package gcom.relatorio.gerencial.cadastro;

import gcom.relatorio.RelatorioBean;


/**
 * @author mribeiro
 *
 */
public class RelatorioResumoLigacoesEconomiaBean
				implements RelatorioBean {

	private Integer idCampoQuebra;
	private String descricaoCampoQuebra;
	private String situacaoAgua;
	private String situacaoEsgoto;

	private Integer idSituacaoAgua;

	private Integer idSituacaoEsgoto;

	private Integer qtdLigacoesResidencialComHidrometro;

	private Integer qtdLigacoesResidencialSemHidrometro;

	private Integer qtdEconomiasResidencialComHidrometro;

	private Integer qtdEconomiasResidencialSemHidrometro;

	private Integer qtdLigacoesComercialComHidrometro;

	private Integer qtdLigacoesComercialSemHidrometro;

	private Integer qtdEconomiasComercialComHidrometro;

	private Integer qtdEconomiasComercialSemHidrometro;

	private Integer qtdLigacoesIndustrialComHidrometro;

	private Integer qtdLigacoesIndustrialSemHidrometro;

	private Integer qtdEconomiasIndustrialComHidrometro;

	private Integer qtdEconomiasIndustrialSemHidrometro;

	private Integer qtdLigacoesPublicoComHidrometro;

	private Integer qtdLigacoesPublicoSemHidrometro;

	private Integer qtdEconomiasPublicoComHidrometro;

	private Integer qtdEconomiasPublicoSemHidrometro;

	public RelatorioResumoLigacoesEconomiaBean() {

		qtdLigacoesResidencialComHidrometro = 0;
		qtdLigacoesResidencialSemHidrometro = 0;
		qtdEconomiasResidencialComHidrometro = 0;
		qtdEconomiasResidencialSemHidrometro = 0;

		qtdLigacoesComercialComHidrometro = 0;
		qtdLigacoesComercialSemHidrometro = 0;
		qtdEconomiasComercialComHidrometro = 0;
		qtdEconomiasComercialSemHidrometro = 0;

		qtdLigacoesIndustrialComHidrometro = 0;
		qtdLigacoesIndustrialSemHidrometro = 0;
		qtdEconomiasIndustrialComHidrometro = 0;
		qtdEconomiasIndustrialSemHidrometro = 0;

		qtdLigacoesPublicoComHidrometro = 0;
		qtdLigacoesPublicoSemHidrometro = 0;
		qtdEconomiasPublicoComHidrometro = 0;
		qtdEconomiasPublicoSemHidrometro = 0;
	}



	public Integer getIdCampoQuebra(){

		return idCampoQuebra;
	}


	public void setIdCampoQuebra(Integer idCampoQuebra){

		this.idCampoQuebra = idCampoQuebra;
	}


	public String getDescricaoCampoQuebra(){

		return descricaoCampoQuebra;
	}


	public void setDescricaoCampoQuebra(String descricaoCampoQuebra){

		this.descricaoCampoQuebra = descricaoCampoQuebra;
	}


	public String getSituacaoAgua(){

		return situacaoAgua;
	}


	public void setSituacaoAgua(String situacaoAgua){

		this.situacaoAgua = situacaoAgua;
	}


	public String getSituacaoEsgoto(){

		return situacaoEsgoto;
	}


	public void setSituacaoEsgoto(String situacaoEsgoto){

		this.situacaoEsgoto = situacaoEsgoto;
	}

	public Integer getQtdLigacoesResidencialComHidrometro(){

		return qtdLigacoesResidencialComHidrometro;
	}

	public void setQtdLigacoesResidencialComHidrometro(Integer qtdLigacoesResidencialComHidrometro){

		this.qtdLigacoesResidencialComHidrometro = qtdLigacoesResidencialComHidrometro;
	}

	public Integer getQtdLigacoesResidencialSemHidrometro(){

		return qtdLigacoesResidencialSemHidrometro;
	}

	public void setQtdLigacoesResidencialSemHidrometro(Integer qtdLigacoesResidencialSemHidrometro){

		this.qtdLigacoesResidencialSemHidrometro = qtdLigacoesResidencialSemHidrometro;
	}

	public Integer getQtdEconomiasResidencialComHidrometro(){

		return qtdEconomiasResidencialComHidrometro;
	}

	public void setQtdEconomiasResidencialComHidrometro(Integer qtdEconomiasResidencialComHidrometro){

		this.qtdEconomiasResidencialComHidrometro = qtdEconomiasResidencialComHidrometro;
	}

	public Integer getQtdEconomiasResidencialSemHidrometro(){

		return qtdEconomiasResidencialSemHidrometro;
	}

	public void setQtdEconomiasResidencialSemHidrometro(Integer qtdEconomiasResidencialSemHidrometro){

		this.qtdEconomiasResidencialSemHidrometro = qtdEconomiasResidencialSemHidrometro;
	}

	public Integer getQtdLigacoesComercialComHidrometro(){

		return qtdLigacoesComercialComHidrometro;
	}

	
	public void setQtdLigacoesComercialComHidrometro(Integer qtdLigacoesComercialComHidrometro){

		this.qtdLigacoesComercialComHidrometro = qtdLigacoesComercialComHidrometro;
	}

	public Integer getQtdLigacoesComercialSemHidrometro(){

		return qtdLigacoesComercialSemHidrometro;
	}

	public void setQtdLigacoesComercialSemHidrometro(Integer qtdLigacoesComercialSemHidrometro){

		this.qtdLigacoesComercialSemHidrometro = qtdLigacoesComercialSemHidrometro;
	}


	public Integer getQtdEconomiasComercialComHidrometro(){

		return qtdEconomiasComercialComHidrometro;
	}


	public void setQtdEconomiasComercialComHidrometro(Integer qtdEconomiasComercialComHidrometro){

		this.qtdEconomiasComercialComHidrometro = qtdEconomiasComercialComHidrometro;
	}


	public Integer getQtdEconomiasComercialSemHidrometro(){

		return qtdEconomiasComercialSemHidrometro;
	}


	public void setQtdEconomiasComercialSemHidrometro(Integer qtdEconomiasComercialSemHidrometro){

		this.qtdEconomiasComercialSemHidrometro = qtdEconomiasComercialSemHidrometro;
	}


	public Integer getQtdLigacoesIndustrialComHidrometro(){

		return qtdLigacoesIndustrialComHidrometro;
	}


	public void setQtdLigacoesIndustrialComHidrometro(Integer qtdLigacoesIndustrialComHidrometro){

		this.qtdLigacoesIndustrialComHidrometro = qtdLigacoesIndustrialComHidrometro;
	}


	public Integer getQtdLigacoesIndustrialSemHidrometro(){

		return qtdLigacoesIndustrialSemHidrometro;
	}


	public void setQtdLigacoesIndustrialSemHidrometro(Integer qtdLigacoesIndustrialSemHidrometro){

		this.qtdLigacoesIndustrialSemHidrometro = qtdLigacoesIndustrialSemHidrometro;
	}


	public Integer getQtdEconomiasIndustrialComHidrometro(){

		return qtdEconomiasIndustrialComHidrometro;
	}


	public void setQtdEconomiasIndustrialComHidrometro(Integer qtdEconomiasIndustrialComHidrometro){

		this.qtdEconomiasIndustrialComHidrometro = qtdEconomiasIndustrialComHidrometro;
	}


	public Integer getQtdEconomiasIndustrialSemHidrometro(){

		return qtdEconomiasIndustrialSemHidrometro;
	}


	public void setQtdEconomiasIndustrialSemHidrometro(Integer qtdEconomiasIndustrialSemHidrometro){

		this.qtdEconomiasIndustrialSemHidrometro = qtdEconomiasIndustrialSemHidrometro;
	}


	public Integer getQtdLigacoesPublicoComHidrometro(){

		return qtdLigacoesPublicoComHidrometro;
	}


	public void setQtdLigacoesPublicoComHidrometro(Integer qtdLigacoesPublicoComHidrometro){

		this.qtdLigacoesPublicoComHidrometro = qtdLigacoesPublicoComHidrometro;
	}


	public Integer getQtdLigacoesPublicoSemHidrometro(){

		return qtdLigacoesPublicoSemHidrometro;
	}


	public void setQtdLigacoesPublicoSemHidrometro(Integer qtdLigacoesPublicoSemHidrometro){

		this.qtdLigacoesPublicoSemHidrometro = qtdLigacoesPublicoSemHidrometro;
	}


	public Integer getQtdEconomiasPublicoComHidrometro(){

		return qtdEconomiasPublicoComHidrometro;
	}


	public void setQtdEconomiasPublicoComHidrometro(Integer qtdEconomiasPublicoComHidrometro){

		this.qtdEconomiasPublicoComHidrometro = qtdEconomiasPublicoComHidrometro;
	}


	public Integer getQtdEconomiasPublicoSemHidrometro(){

		return qtdEconomiasPublicoSemHidrometro;
	}


	public void setQtdEconomiasPublicoSemHidrometro(Integer qtdEconomiasPublicoSemHidrometro){

		this.qtdEconomiasPublicoSemHidrometro = qtdEconomiasPublicoSemHidrometro;
	}

	@Override
	public boolean equals(Object obj){
		boolean igual = (obj != null) && this.getClass().equals(obj.getClass());
		if(igual){
			RelatorioResumoLigacoesEconomiaBean outro = (RelatorioResumoLigacoesEconomiaBean) obj;
			if(this.descricaoCampoQuebra.equals(outro.getDescricaoCampoQuebra()) && this.situacaoAgua.equals(outro.getSituacaoAgua())
							&& this.situacaoEsgoto.equals(outro.getSituacaoEsgoto())){
				igual = true;
			}else{
				igual = false;
			}
		}
		return igual;
	}

	public Integer getQtdEconomiasParticularComHidrometro(){

		return qtdEconomiasComercialComHidrometro + qtdEconomiasResidencialComHidrometro + qtdEconomiasIndustrialComHidrometro;
	}

	public Integer getQtdEconomiasParticularSemHidrometro(){

		return qtdEconomiasComercialSemHidrometro + qtdEconomiasResidencialSemHidrometro + qtdEconomiasIndustrialSemHidrometro;
	}

	public Integer getQtdLigacoesParticularComHidrometro(){

		return qtdLigacoesComercialComHidrometro + qtdLigacoesResidencialComHidrometro + qtdLigacoesIndustrialComHidrometro;
	}

	public Integer getQtdLigacoesParticularSemHidrometro(){

		return qtdLigacoesComercialSemHidrometro + qtdLigacoesResidencialSemHidrometro + qtdLigacoesIndustrialSemHidrometro;
	}

	public Integer getQtdEconomiasTotalComHidrometro(){

		return qtdEconomiasPublicoComHidrometro + getQtdEconomiasParticularComHidrometro();
	}

	public Integer getQtdEconomiasTotalSemHidrometro(){

		return qtdEconomiasPublicoSemHidrometro + getQtdEconomiasParticularSemHidrometro();
	}

	public Integer getQtdLigacoesTotalComHidrometro(){

		return qtdLigacoesPublicoComHidrometro + getQtdLigacoesParticularComHidrometro();
	}

	public Integer getQtdLigacoesTotalSemHidrometro(){

		return qtdLigacoesPublicoSemHidrometro + getQtdLigacoesParticularSemHidrometro();
	}

	public Integer getQtdEconomiasTotalGeral(){

		return getQtdEconomiasTotalComHidrometro() + getQtdEconomiasTotalSemHidrometro();
	}

	public Integer getQtdLigacoesTotalGeral(){

		return getQtdLigacoesTotalComHidrometro() + getQtdLigacoesTotalSemHidrometro();
	}

	public Integer getQtdTotalGeral(){

		return getQtdLigacoesTotalGeral() + getQtdEconomiasTotalGeral();
	}

	public void add(RelatorioResumoLigacoesEconomiaBean relatorioResumoLigacoesEconomiaBean, String situacaoAgua, String situacaoEsgoto){

		idCampoQuebra = relatorioResumoLigacoesEconomiaBean.getIdCampoQuebra();
		descricaoCampoQuebra = relatorioResumoLigacoesEconomiaBean.getDescricaoCampoQuebra();
		idSituacaoAgua = "TODAS".equals(situacaoAgua) ? 0 : relatorioResumoLigacoesEconomiaBean.getIdSituacaoAgua();
		idSituacaoEsgoto = "TODAS".equals(situacaoEsgoto) ? 0 : relatorioResumoLigacoesEconomiaBean.getIdSituacaoEsgoto();
		if(situacaoAgua != null){
			this.situacaoAgua = situacaoAgua;
		}
		if(situacaoEsgoto != null){
			this.situacaoEsgoto = situacaoEsgoto;
		}

		qtdLigacoesResidencialComHidrometro += relatorioResumoLigacoesEconomiaBean.getQtdLigacoesResidencialComHidrometro();
		qtdLigacoesResidencialSemHidrometro += relatorioResumoLigacoesEconomiaBean.getQtdLigacoesResidencialSemHidrometro();
		qtdEconomiasResidencialComHidrometro += relatorioResumoLigacoesEconomiaBean.getQtdEconomiasResidencialComHidrometro();
		qtdEconomiasResidencialSemHidrometro += relatorioResumoLigacoesEconomiaBean.getQtdEconomiasResidencialSemHidrometro();
		qtdLigacoesComercialComHidrometro += relatorioResumoLigacoesEconomiaBean.getQtdLigacoesComercialComHidrometro();
		qtdLigacoesComercialSemHidrometro += relatorioResumoLigacoesEconomiaBean.getQtdLigacoesComercialSemHidrometro();
		qtdEconomiasComercialComHidrometro += relatorioResumoLigacoesEconomiaBean.getQtdEconomiasComercialComHidrometro();
		qtdEconomiasComercialSemHidrometro += relatorioResumoLigacoesEconomiaBean.getQtdEconomiasComercialSemHidrometro();
		qtdLigacoesIndustrialComHidrometro += relatorioResumoLigacoesEconomiaBean.getQtdLigacoesIndustrialComHidrometro();
		qtdLigacoesIndustrialSemHidrometro += relatorioResumoLigacoesEconomiaBean.getQtdLigacoesIndustrialSemHidrometro();
		qtdEconomiasIndustrialComHidrometro += relatorioResumoLigacoesEconomiaBean.getQtdEconomiasIndustrialComHidrometro();
		qtdEconomiasIndustrialSemHidrometro += relatorioResumoLigacoesEconomiaBean.getQtdEconomiasIndustrialSemHidrometro();
		qtdLigacoesPublicoComHidrometro += relatorioResumoLigacoesEconomiaBean.getQtdLigacoesPublicoComHidrometro();
		qtdLigacoesPublicoSemHidrometro += relatorioResumoLigacoesEconomiaBean.getQtdLigacoesPublicoSemHidrometro();
		qtdEconomiasPublicoComHidrometro += relatorioResumoLigacoesEconomiaBean.getQtdEconomiasPublicoComHidrometro();
		qtdEconomiasPublicoSemHidrometro += relatorioResumoLigacoesEconomiaBean.getQtdEconomiasPublicoSemHidrometro();
	}

	public void add(RelatorioResumoLigacoesEconomiaBean novalinha){
		add(novalinha, null, null);
	}

	public Integer getIdSituacaoAgua(){

		return idSituacaoAgua;
	}

	public String getIdSituacaoAguaAsString(){

		return idSituacaoAgua == null ? "" : idSituacaoAgua.toString();
	}

	public void setIdSituacaoAgua(Integer idSituacaoAgua){

		this.idSituacaoAgua = idSituacaoAgua;
	}

	public Integer getIdSituacaoEsgoto(){

		return idSituacaoEsgoto;
	}

	public void setIdSituacaoEsgoto(Integer idSituacaoEsgoto){

		this.idSituacaoEsgoto = idSituacaoEsgoto;
	}

}
