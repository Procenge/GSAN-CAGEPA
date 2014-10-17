
package gcom.cobranca.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import gcom.cadastro.cliente.FoneTipo;
import gcom.util.Util;

public class DocumentoTeleCobrancaHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer tipoTelefone;

	private String telefonePrincipal;

	private String empresa;

	private String numeroImovel;

	private String nomeCliente;

	private String enderecoImovel;

	private String nomeBairro;

	private String telefoneSecundario;

	private BigDecimal valorDebito;

	private String cep;

	public Integer getTipoTelefone(){

		return tipoTelefone;
	}

	public void setTipoTelefone(Integer tipoTelefone){

		this.tipoTelefone = tipoTelefone;
	}

	public String getTelefonePrincipal(){

		if(telefonePrincipal != null){
			return telefonePrincipal;
		}
		return "";
	}

	public void setTelefonePrincipal(String telefonePrincipal){

		this.telefonePrincipal = telefonePrincipal;
	}

	public String getEmpresa(){

		String retorno = "";

		if(empresa != null){
			retorno = "\"" + empresa + "\"";
		}

		return retorno;
	}

	public void setEmpresa(String empresa){

		this.empresa = empresa;
	}

	public String getNumeroImovel(){

		String retorno = "";

		if(numeroImovel != null){
			retorno = "\"" + numeroImovel + "\"";
		}

		return retorno;
	}

	public void setNumeroImovel(String numeroImovel){

		this.numeroImovel = numeroImovel;
	}

	public String getNomeCliente(){

		String retorno = "";

		if(nomeCliente != null){
			retorno = "\"" + nomeCliente + "\"";
		}

		return retorno;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getEnderecoImovel(){

		String retorno = "";

		if(enderecoImovel != null){
			retorno = "\"" + enderecoImovel + "\"";
		}

		return retorno;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getNomeBairro(){

		String retorno = "";

		if(nomeBairro != null){
			retorno = "\"" + nomeBairro + "\"";
		}

		return retorno;
	}

	public void setNomeBairro(String nomeBairro){

		this.nomeBairro = nomeBairro;
	}

	public String getTelefoneSecundario(){

		if(telefoneSecundario != null){
			return telefoneSecundario;
		}
		return "";

	}

	public void setTelefoneSecundario(String telefoneSecundario){

		this.telefoneSecundario = telefoneSecundario;
	}

	public BigDecimal getValorDebito(){

		return valorDebito;
	}

	public void setValorDebito(BigDecimal valorDebito){

		this.valorDebito = valorDebito;
	}

	public String getCep(){

		return cep;
	}

	public void setCep(String cep){

		this.cep = cep;
	}

	public static long getSerialversionuid(){

		return serialVersionUID;
	}

	public String getTipoTelefoneString(){

		String retorno = "";

		if(tipoTelefone != null){

			if(tipoTelefone.equals(FoneTipo.RESIDENCIAL)){
				retorno = "R";
			}else if(tipoTelefone.equals(FoneTipo.CELULAR)){
				retorno = "L";
			}else if(tipoTelefone.equals(FoneTipo.RECADO)){
				retorno = "R";
			}else if(tipoTelefone.equals(FoneTipo.COMERCIAL)){
				retorno = "C";
			}

		}

		return retorno;
	}

	public String getValorDebitoFormatado(){

		String retorno = "";

		if(valorDebito != null){
			retorno = Util.formatarMoedaReal(valorDebito);
		}

		return retorno;
	}
}
