
package gcom.gui.cadastro.endereco;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class AtualizarCepActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String codigo;

	private String sigla;

	private String municipio;

	private String codigoMunicipio;

	private String bairro;

	private String codigoBairro;

	private String idBairro;

	private String logradouro;

	private String codigoLogradouro;

	private String descricaoTipoLogradouro;

	private Short indicadorUso;

	private Integer cepTipo;

	private String intervaloNumeracao;

	private String faixaInicial;

	private String faixaFinal;

	private String codigoLadoCep;

	private Date ultimaAlteracao;

	private String codigoLocalidade;

	private String localidade;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getCodigo(){

		return codigo;
	}

	public void setCodigo(String codigo){

		this.codigo = codigo;
	}

	public String getSigla(){

		return sigla;
	}

	public void setSigla(String sigla){

		this.sigla = sigla;
	}

	public String getMunicipio(){

		return municipio;
	}

	public void setMunicipio(String municipio){

		this.municipio = municipio;
	}

	public String getCodigoMunicipio(){

		return codigoMunicipio;
	}

	public void setCodigoMunicipio(String codigoMunicipio){

		this.codigoMunicipio = codigoMunicipio;
	}

	public String getBairro(){

		return bairro;
	}

	public void setBairro(String bairro){

		this.bairro = bairro;
	}

	public String getLogradouro(){

		return logradouro;
	}

	public void setLogradouro(String logradouro){

		this.logradouro = logradouro;
	}

	public String getDescricaoTipoLogradouro(){

		return descricaoTipoLogradouro;
	}

	public void setDescricaoTipoLogradouro(String descricaoTipoLogradouro){

		this.descricaoTipoLogradouro = descricaoTipoLogradouro;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Integer getCepTipo(){

		return cepTipo;
	}

	public void setCepTipo(Integer cepTipo){

		this.cepTipo = cepTipo;
	}

	public String getIntervaloNumeracao(){

		return intervaloNumeracao;
	}

	public void setIntervaloNumeracao(String intervaloNumeracao){

		this.intervaloNumeracao = intervaloNumeracao;
	}

	public String getFaixaInicial(){

		return faixaInicial;
	}

	public void setFaixaInicial(String faixaInicial){

		this.faixaInicial = faixaInicial;
	}

	public String getFaixaFinal(){

		return faixaFinal;
	}

	public void setFaixaFinal(String faixaFinal){

		this.faixaFinal = faixaFinal;
	}

	public String getCodigoLadoCep(){

		return codigoLadoCep;
	}

	public void setCodigoLadoCep(String codigoLadoCep){

		this.codigoLadoCep = codigoLadoCep;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String getCodigoBairro(){

		return codigoBairro;
	}

	public void setCodigoBairro(String codigoBairro){

		this.codigoBairro = codigoBairro;
	}

	public String getCodigoLogradouro(){

		return codigoLogradouro;
	}

	public void setCodigoLogradouro(String codigoLogradouro){

		this.codigoLogradouro = codigoLogradouro;
	}

	public String getIdBairro(){

		return idBairro;
	}

	public void setIdBairro(String idBairro){

		this.idBairro = idBairro;
	}

	public String getCodigoLocalidade(){

		return codigoLocalidade;
	}

	public void setCodigoLocalidade(String codigoLocalidade){

		this.codigoLocalidade = codigoLocalidade;
	}

	public String getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 */
	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest){



		this.codigo = "";

		this.sigla= "";

		this.municipio= "";

		this.codigoMunicipio= "";

		this.bairro= "";

		this.codigoBairro= "";

		this.idBairro= "";

		this.logradouro= "";

		this.codigoLogradouro= "";

		this.descricaoTipoLogradouro= "";

		this.intervaloNumeracao= "";

		this.faixaInicial = "";

		this.faixaFinal = "";

		this.codigoLadoCep = "";

	
	}


}
