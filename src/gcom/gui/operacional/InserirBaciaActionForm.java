
package gcom.gui.operacional;

import gcom.cadastro.endereco.EnderecoReferencia;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.operacional.Bacia;
import gcom.operacional.SubsistemaEsgoto;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * UC0042 - Inserir Bacia
 * 
 * @author Hebert Falcão
 * @created 27 de Janeiro de 2011
 */
public class InserirBaciaActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String codigo;

	private String descricao;

	private String descricaoAbreviada;

	private String idSistemaEsgoto;

	private String idSubsistemaEsgoto;

	private String numeroUnidade;

	private String numeroAducao;

	private String numeroDemandaEnergia;

	private String numeroCota;

	private String numeroLatitude;

	private String numeroLongitude;

	/**
	 * @return the codigo
	 */
	public String getCodigo(){

		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(String codigo){

		this.codigo = codigo;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao(){

		return descricao;
	}

	/**
	 * @param descricao
	 *            the descricao to set
	 */
	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	/**
	 * @return the descricaoAbreviada
	 */
	public String getDescricaoAbreviada(){

		return descricaoAbreviada;
	}

	/**
	 * @param descricaoAbreviada
	 *            the descricaoAbreviada to set
	 */
	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	/**
	 * @return the idSistemaEsgoto
	 */
	public String getIdSistemaEsgoto(){

		return idSistemaEsgoto;
	}

	/**
	 * @param idSistemaEsgoto
	 *            the idSistemaEsgoto to set
	 */
	public void setIdSistemaEsgoto(String idSistemaEsgoto){

		this.idSistemaEsgoto = idSistemaEsgoto;
	}

	/**
	 * @return the idSubsistemaEsgoto
	 */
	public String getIdSubsistemaEsgoto(){

		return idSubsistemaEsgoto;
	}

	/**
	 * @param idSubsistemaEsgoto
	 *            the idSubsistemaEsgoto to set
	 */
	public void setIdSubsistemaEsgoto(String idSubsistemaEsgoto){

		this.idSubsistemaEsgoto = idSubsistemaEsgoto;
	}

	/**
	 * @return the numeroUnidade
	 */
	public String getNumeroUnidade(){

		return numeroUnidade;
	}

	/**
	 * @param numeroUnidade
	 *            the numeroUnidade to set
	 */
	public void setNumeroUnidade(String numeroUnidade){

		this.numeroUnidade = numeroUnidade;
	}

	/**
	 * @return the numeroAducao
	 */
	public String getNumeroAducao(){

		return numeroAducao;
	}

	/**
	 * @param numeroAducao
	 *            the numeroAducao to set
	 */
	public void setNumeroAducao(String numeroAducao){

		this.numeroAducao = numeroAducao;
	}

	/**
	 * @return the numeroDemandaEnergia
	 */
	public String getNumeroDemandaEnergia(){

		return numeroDemandaEnergia;
	}

	/**
	 * @param numeroDemandaEnergia
	 *            the numeroDemandaEnergia to set
	 */
	public void setNumeroDemandaEnergia(String numeroDemandaEnergia){

		this.numeroDemandaEnergia = numeroDemandaEnergia;
	}

	/**
	 * @return the numeroCota
	 */
	public String getNumeroCota(){

		return numeroCota;
	}

	/**
	 * @param numeroCota
	 *            the numeroCota to set
	 */
	public void setNumeroCota(String numeroCota){

		this.numeroCota = numeroCota;
	}

	/**
	 * @return the numeroLatitude
	 */
	public String getNumeroLatitude(){

		return numeroLatitude;
	}

	/**
	 * @param numeroLatitude
	 *            the numeroLatitude to set
	 */
	public void setNumeroLatitude(String numeroLatitude){

		this.numeroLatitude = numeroLatitude;
	}

	/**
	 * @return the numeroLongitude
	 */
	public String getNumeroLongitude(){

		return numeroLongitude;
	}

	/**
	 * @param numeroLongitude
	 *            the numeroLongitude to set
	 */
	public void setNumeroLongitude(String numeroLongitude){

		this.numeroLongitude = numeroLongitude;
	}

	public Bacia setFormValues(Bacia bacia, Collection<Bacia> colecaoEnderecos){

		if(getCodigo() != null && !getCodigo().equals("")){
			bacia.setCodigo(Integer.valueOf(getCodigo()));
		}

		bacia.setDescricao(getDescricao());
		bacia.setDescricaoAbreviada(getDescricaoAbreviada());

		if(getIdSubsistemaEsgoto() != null && !getIdSubsistemaEsgoto().equals("")
						&& !getIdSubsistemaEsgoto().equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
			SubsistemaEsgoto subsistemaEsgoto = new SubsistemaEsgoto();
			subsistemaEsgoto.setId(Integer.valueOf(getIdSubsistemaEsgoto()));
			bacia.setSubsistemaEsgoto(subsistemaEsgoto);
		}

		// Unidade
		String numeroUnidadeStr = this.getNumeroUnidade();
		if(!Util.isVazioOuBranco(numeroUnidadeStr)){
			BigDecimal numeroUnidade = Util.formatarStringParaBigDecimal(numeroUnidadeStr, 4, false);
			bacia.setNumeroUnidade(numeroUnidade);
		}

		// Adução
		String numeroAducaoStr = this.getNumeroAducao();
		if(!Util.isVazioOuBranco(numeroAducaoStr)){
			BigDecimal numeroAducao = Util.formatarStringParaBigDecimal(numeroAducaoStr, 4, false);
			bacia.setNumeroAducao(numeroAducao);
		}

		// Demanda de energia
		String numeroDemandaEnergiaStr = this.getNumeroDemandaEnergia();
		if(!Util.isVazioOuBranco(numeroDemandaEnergiaStr)){
			BigDecimal numeroDemandaEnergia = Util.formatarStringParaBigDecimal(numeroDemandaEnergiaStr, 4, false);
			bacia.setNumeroDemandaEnergia(numeroDemandaEnergia);
		}

		// Coleção de endereços
		String numeroImovel = null;
		String complementoEndereco = null;
		EnderecoReferencia enderecoReferencia = null;
		LogradouroBairro logradouroBairro = null;
		LogradouroCep logradouroCep = null;

		if(colecaoEnderecos != null && !colecaoEnderecos.isEmpty()){
			Bacia baciaEndereco = (Bacia) Util.retonarObjetoDeColecao(colecaoEnderecos);

			numeroImovel = baciaEndereco.getNumeroImovel();
			complementoEndereco = baciaEndereco.getComplementoEndereco();
			enderecoReferencia = baciaEndereco.getEnderecoReferencia();
			logradouroBairro = baciaEndereco.getLogradouroBairro();
			logradouroCep = baciaEndereco.getLogradouroCep();
		}

		bacia.setNumeroImovel(numeroImovel);
		bacia.setComplementoEndereco(complementoEndereco);
		bacia.setEnderecoReferencia(enderecoReferencia);
		bacia.setLogradouroBairro(logradouroBairro);
		bacia.setLogradouroCep(logradouroCep);

		// Cota
		String numeroCotaStr = this.getNumeroCota();
		if(numeroCotaStr != null && !numeroCotaStr.equals("")){
			Integer numeroCota = Integer.valueOf(numeroCotaStr);
			bacia.setNumeroCota(numeroCota);
		}

		// Latitude
		String numeroLatitudeStr = this.getNumeroLatitude();
		if(numeroLatitudeStr != null && !numeroLatitudeStr.equals("")){
			Integer numeroLatitude = Integer.valueOf(numeroLatitudeStr);
			bacia.setNumeroLatitude(numeroLatitude);
		}

		// Longitude
		String numeroLongitudeStr = this.getNumeroLongitude();
		if(numeroLongitudeStr != null && !numeroLongitudeStr.equals("")){
			Integer numeroLongitude = Integer.valueOf(numeroLongitudeStr);
			bacia.setNumeroLongitude(numeroLongitude);
		}

		return bacia;
	}

}
