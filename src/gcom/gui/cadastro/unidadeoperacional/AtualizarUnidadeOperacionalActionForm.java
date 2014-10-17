/**
 * 
 */

package gcom.gui.cadastro.unidadeoperacional;

import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.unidadeoperacional.UnidadeOperacional;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * @author Péricles Tavares
 */
public class AtualizarUnidadeOperacionalActionForm
				extends ValidatorActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String codigoUnidadeOperacional;

	private String descricao;

	private String descricaoAbreviada;

	private String sistemaAbastecimento;

	private String localidade;

	private String descricaoLocalidade;

	private String endereco;

	private String telefone;

	private String ramal;

	private String fax;

	private String email;

	private String descricaoTipoInstalacao;

	private String numeroCota;

	private String latitude;

	private String longitude;

	private String indicadorUso;

	public String getId(){

		return id;
	}

	public void setId(String id){

		this.id = id;
	}

	public String getCodigoUnidadeOperacional(){

		return codigoUnidadeOperacional;
	}

	public void setCodigoUnidadeOperacional(String codigoUnidadeOperacional){

		this.codigoUnidadeOperacional = codigoUnidadeOperacional;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getDescricaoAbreviada(){

		return descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	public String getSistemaAbastecimento(){

		return sistemaAbastecimento;
	}

	public void setSistemaAbastecimento(String sistemaAbastecimento){

		this.sistemaAbastecimento = sistemaAbastecimento;
	}

	public String getLocalidade(){

		return localidade;
	}

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getTelefone(){

		return telefone;
	}

	public void setTelefone(String telefone){

		this.telefone = telefone;
	}

	public String getRamal(){

		return ramal;
	}

	public void setRamal(String ramal){

		this.ramal = ramal;
	}

	public String getFax(){

		return fax;
	}

	public void setFax(String fax){

		this.fax = fax;
	}

	public String getEmail(){

		return email;
	}

	public void setEmail(String email){

		this.email = email;
	}

	public String getDescricaoTipoInstalacao(){

		return descricaoTipoInstalacao;
	}

	public void setDescricaoTipoInstalacao(String descricaoTipoInstalacao){

		this.descricaoTipoInstalacao = descricaoTipoInstalacao;
	}

	public String getNumeroCota(){

		return numeroCota;
	}

	public void setNumeroCota(String numeroCota){

		this.numeroCota = numeroCota;
	}

	public String getLatitude(){

		return latitude;
	}

	public void setLatitude(String latitude){

		this.latitude = latitude;
	}

	public String getLongitude(){

		return longitude;
	}

	public void setLongitude(String longitude){

		this.longitude = longitude;
	}

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public UnidadeOperacional setFormValues(UnidadeOperacional unidadeOperacional){

		unidadeOperacional.setDescricao(descricao);
		unidadeOperacional.setDescricaoAbreviada(descricaoAbreviada);
		Localidade localidadeUnidade = new Localidade();
		localidadeUnidade.setId(Integer.parseInt(localidade));
		localidadeUnidade.setDescricao(descricaoLocalidade);
		unidadeOperacional.setLocalidade(localidadeUnidade);
		if(telefone != null && !telefone.equals("")){
			unidadeOperacional.setTelefone(Integer.parseInt(telefone));
		}
		if(ramal != null && !ramal.equals("")){
			unidadeOperacional.setRamal(Integer.parseInt(ramal));
		}
		if(fax != null && !fax.equals("")){
			unidadeOperacional.setFax(Integer.parseInt(fax));
		}
		unidadeOperacional.setEmail(email);
		unidadeOperacional.setDescricaoTipoInstalacao(descricaoTipoInstalacao);
		if(numeroCota != null && !numeroCota.equals("")){
			unidadeOperacional.setNumeroCota(Integer.parseInt(numeroCota));
		}
		if(latitude != null && !latitude.equals("")){
			unidadeOperacional.setLatitude(Integer.parseInt(latitude));
		}
		if(longitude != null && !longitude.equals("")){
			unidadeOperacional.setLongitude(Integer.parseInt(longitude));
		}
		unidadeOperacional.setIndicadorAtivo(Short.parseShort(indicadorUso));
		return unidadeOperacional;
	}

}
