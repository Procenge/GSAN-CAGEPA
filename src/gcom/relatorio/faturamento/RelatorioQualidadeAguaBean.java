
package gcom.relatorio.faturamento;

import gcom.relatorio.RelatorioBean;

/**
 * Bean responsável de pegar os parametros que serão exibidos na parte de detail
 * do relatório.
 * 
 * @author Sávio Luiz
 * @created 8 de Julho de 2005
 */
public class RelatorioQualidadeAguaBean
				implements RelatorioBean {

	private String mesAno;

	private String idLocalidade;

	private String codigoSetorComercial;

	private String fonteCaptacao;

	private String numeroAmostrasRealizadasTurbidez;

	private String numeroAmostrasRealizadasCor;

	private String numeroAmostrasRealizadasCloro;

	private String numeroAmostrasRealizadasPH;

	private String numeroAmostrasRealizadasFluor;

	private String numeroAmostrasRealizadasColiformesTotais;

	private String numeroAmostrasRealizadasColiformesTermotolerantes;

	private String numeroAmostrasConformesTurbidez;

	private String numeroAmostrasConformesCor;

	private String numeroAmostrasConformesCloro;

	private String numeroAmostrasConformesPH;

	private String numeroAmostrasConformesFluor;

	private String numeroAmostrasConformesColiformesTotais;

	private String numeroAmostrasConformesColiformesTermotolerantes;

	private String numeroAmostrasMediaTurbidez;

	private String numeroAmostrasMediaCor;

	private String numeroAmostrasMediaCloro;

	private String numeroAmostrasMediaPH;

	private String numeroAmostrasMediaFluor;

	private String numeroAmostrasMediaColiformesTotais;

	private String numeroAmostrasMediaColiformesTermotolerantes;

	/**
	 * Construtor da classe RelatorioAnaliseFisicoQuimicaAguaBean
	 * 
	 * @param codigo
	 *            Description of the Parameter
	 * @param nome
	 *            Description of the Parameter
	 * @param municipio
	 *            Description of the Parameter
	 * @param codPref
	 *            Description of the Parameter
	 * @param indicadorUso
	 *            Description of the Parameter
	 */
	public RelatorioQualidadeAguaBean(String mesAno, String idLocalidade, String codigoSetorComercial, String fonteCaptacao,
										String numeroAmostrasRealizadasTurbidez, String numeroAmostrasRealizadasCor,
										String numeroAmostrasRealizadasCloro, String numeroAmostrasRealizadasPH,
										String numeroAmostrasRealizadasFluor, String numeroAmostrasRealizadasColiformesTotais,
										String numeroAmostrasRealizadasColiformesTermotolerantes, String numeroAmostrasConformesTurbidez,
										String numeroAmostrasConformesCor, String numeroAmostrasConformesCloro,
										String numeroAmostrasConformesPH, String numeroAmostrasConformesFluor,
										String numeroAmostrasConformesColiformesTotais,
										String numeroAmostrasConformesColiformesTermotolerantes, String numeroAmostrasMediaTurbidez,
										String numeroAmostrasMediaCor, String numeroAmostrasMediaCloro, String numeroAmostrasMediaPH,
										String numeroAmostrasMediaFluor, String numeroAmostrasMediaColiformesTotais,
										String numeroAmostrasMediaColiformesTermotolerantes) {

		this.mesAno = mesAno;
		this.idLocalidade = idLocalidade;
		this.codigoSetorComercial = codigoSetorComercial;
		this.fonteCaptacao = fonteCaptacao;
		this.numeroAmostrasRealizadasTurbidez = numeroAmostrasRealizadasTurbidez;
		this.numeroAmostrasRealizadasCor = numeroAmostrasRealizadasCor;
		this.numeroAmostrasRealizadasCloro = numeroAmostrasRealizadasCloro;
		this.numeroAmostrasRealizadasPH = numeroAmostrasRealizadasPH;
		this.numeroAmostrasRealizadasFluor = numeroAmostrasRealizadasFluor;
		this.numeroAmostrasRealizadasColiformesTotais = numeroAmostrasRealizadasColiformesTotais;
		this.numeroAmostrasRealizadasColiformesTermotolerantes = numeroAmostrasRealizadasColiformesTermotolerantes;
		this.numeroAmostrasConformesTurbidez = numeroAmostrasConformesTurbidez;
		this.numeroAmostrasConformesCor = numeroAmostrasConformesCor;
		this.numeroAmostrasConformesCloro = numeroAmostrasConformesCloro;
		this.numeroAmostrasConformesPH = numeroAmostrasConformesPH;
		this.numeroAmostrasConformesFluor = numeroAmostrasConformesFluor;
		this.numeroAmostrasConformesColiformesTotais = numeroAmostrasConformesColiformesTotais;
		this.numeroAmostrasConformesColiformesTermotolerantes = numeroAmostrasConformesColiformesTermotolerantes;
		this.numeroAmostrasMediaTurbidez = numeroAmostrasMediaTurbidez;
		this.numeroAmostrasMediaCor = numeroAmostrasMediaCor;
		this.numeroAmostrasMediaCloro = numeroAmostrasMediaCloro;
		this.numeroAmostrasMediaPH = numeroAmostrasMediaPH;
		this.numeroAmostrasMediaFluor = numeroAmostrasMediaFluor;
		this.numeroAmostrasMediaColiformesTotais = numeroAmostrasMediaColiformesTotais;
		this.numeroAmostrasMediaColiformesTermotolerantes = numeroAmostrasMediaColiformesTermotolerantes;

	}

	public String getMesAno(){

		return mesAno;
	}

	public void setMesAno(String mesAno){

		this.mesAno = mesAno;
	}

	public String getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(String codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public String getFonteCaptacao(){

		return fonteCaptacao;
	}

	public void setFonteCaptacao(String fonteCaptacao){

		this.fonteCaptacao = fonteCaptacao;
	}

	public String getNumeroAmostrasRealizadasTurbidez(){

		return numeroAmostrasRealizadasTurbidez;
	}

	public void setNumeroAmostrasRealizadasTurbidez(String numeroAmostrasRealizadasTurbidez){

		this.numeroAmostrasRealizadasTurbidez = numeroAmostrasRealizadasTurbidez;
	}

	public String getNumeroAmostrasRealizadasCor(){

		return numeroAmostrasRealizadasCor;
	}

	public void setNumeroAmostrasRealizadasCor(String numeroAmostrasRealizadasCor){

		this.numeroAmostrasRealizadasCor = numeroAmostrasRealizadasCor;
	}

	public String getNumeroAmostrasRealizadasCloro(){

		return numeroAmostrasRealizadasCloro;
	}

	public void setNumeroAmostrasRealizadasCloro(String numeroAmostrasRealizadasCloro){

		this.numeroAmostrasRealizadasCloro = numeroAmostrasRealizadasCloro;
	}

	public String getNumeroAmostrasRealizadasPH(){

		return numeroAmostrasRealizadasPH;
	}

	public void setNumeroAmostrasRealizadasPH(String numeroAmostrasRealizadasPH){

		this.numeroAmostrasRealizadasPH = numeroAmostrasRealizadasPH;
	}

	public String getNumeroAmostrasRealizadasFluor(){

		return numeroAmostrasRealizadasFluor;
	}

	public void setNumeroAmostrasRealizadasFluor(String numeroAmostrasRealizadasFluor){

		this.numeroAmostrasRealizadasFluor = numeroAmostrasRealizadasFluor;
	}

	public String getNumeroAmostrasRealizadasColiformesTotais(){

		return numeroAmostrasRealizadasColiformesTotais;
	}

	public void setNumeroAmostrasRealizadasColiformesTotais(String numeroAmostrasRealizadasColiformesTotais){

		this.numeroAmostrasRealizadasColiformesTotais = numeroAmostrasRealizadasColiformesTotais;
	}

	public String getNumeroAmostrasRealizadasColiformesTermotolerantes(){

		return numeroAmostrasRealizadasColiformesTermotolerantes;
	}

	public void setNumeroAmostrasRealizadasColiformesTermotolerantes(String numeroAmostrasRealizadasColiformesTermotolerantes){

		this.numeroAmostrasRealizadasColiformesTermotolerantes = numeroAmostrasRealizadasColiformesTermotolerantes;
	}

	public String getNumeroAmostrasConformesTurbidez(){

		return numeroAmostrasConformesTurbidez;
	}

	public void setNumeroAmostrasConformesTurbidez(String numeroAmostrasConformesTurbidez){

		this.numeroAmostrasConformesTurbidez = numeroAmostrasConformesTurbidez;
	}

	public String getNumeroAmostrasConformesCor(){

		return numeroAmostrasConformesCor;
	}

	public void setNumeroAmostrasConformesCor(String numeroAmostrasConformesCor){

		this.numeroAmostrasConformesCor = numeroAmostrasConformesCor;
	}

	public String getNumeroAmostrasConformesCloro(){

		return numeroAmostrasConformesCloro;
	}

	public void setNumeroAmostrasConformesCloro(String numeroAmostrasConformesCloro){

		this.numeroAmostrasConformesCloro = numeroAmostrasConformesCloro;
	}

	public String getNumeroAmostrasConformesPH(){

		return numeroAmostrasConformesPH;
	}

	public void setNumeroAmostrasConformesPH(String numeroAmostrasConformesPH){

		this.numeroAmostrasConformesPH = numeroAmostrasConformesPH;
	}

	public String getNumeroAmostrasConformesFluor(){

		return numeroAmostrasConformesFluor;
	}

	public void setNumeroAmostrasConformesFluor(String numeroAmostrasConformesFluor){

		this.numeroAmostrasConformesFluor = numeroAmostrasConformesFluor;
	}

	public String getNumeroAmostrasConformesColiformesTotais(){

		return numeroAmostrasConformesColiformesTotais;
	}

	public void setNumeroAmostrasConformesColiformesTotais(String numeroAmostrasConformesColiformesTotais){

		this.numeroAmostrasConformesColiformesTotais = numeroAmostrasConformesColiformesTotais;
	}

	public String getNumeroAmostrasConformesColiformesTermotolerantes(){

		return numeroAmostrasConformesColiformesTermotolerantes;
	}

	public void setNumeroAmostrasConformesColiformesTermotolerantes(String numeroAmostrasConformesColiformesTermotolerantes){

		this.numeroAmostrasConformesColiformesTermotolerantes = numeroAmostrasConformesColiformesTermotolerantes;
	}

	public String getNumeroAmostrasMediaTurbidez(){

		return numeroAmostrasMediaTurbidez;
	}

	public void setNumeroAmostrasMediaTurbidez(String numeroAmostrasMediaTurbidez){

		this.numeroAmostrasMediaTurbidez = numeroAmostrasMediaTurbidez;
	}

	public String getNumeroAmostrasMediaCor(){

		return numeroAmostrasMediaCor;
	}

	public void setNumeroAmostrasMediaCor(String numeroAmostrasMediaCor){

		this.numeroAmostrasMediaCor = numeroAmostrasMediaCor;
	}

	public String getNumeroAmostrasMediaCloro(){

		return numeroAmostrasMediaCloro;
	}

	public void setNumeroAmostrasMediaCloro(String numeroAmostrasMediaCloro){

		this.numeroAmostrasMediaCloro = numeroAmostrasMediaCloro;
	}

	public String getNumeroAmostrasMediaPH(){

		return numeroAmostrasMediaPH;
	}

	public void setNumeroAmostrasMediaPH(String numeroAmostrasMediaPH){

		this.numeroAmostrasMediaPH = numeroAmostrasMediaPH;
	}

	public String getNumeroAmostrasMediaFluor(){

		return numeroAmostrasMediaFluor;
	}

	public void setNumeroAmostrasMediaFluor(String numeroAmostrasMediaFluor){

		this.numeroAmostrasMediaFluor = numeroAmostrasMediaFluor;
	}

	public String getNumeroAmostrasMediaColiformesTotais(){

		return numeroAmostrasMediaColiformesTotais;
	}

	public void setNumeroAmostrasMediaColiformesTotais(String numeroAmostrasMediaColiformesTotais){

		this.numeroAmostrasMediaColiformesTotais = numeroAmostrasMediaColiformesTotais;
	}

	public String getNumeroAmostrasMediaColiformesTermotolerantes(){

		return numeroAmostrasMediaColiformesTermotolerantes;
	}

	public void setNumeroAmostrasMediaColiformesTermotolerantes(String numeroAmostrasMediaColiformesTermotolerantes){

		this.numeroAmostrasMediaColiformesTermotolerantes = numeroAmostrasMediaColiformesTermotolerantes;
	}

}
