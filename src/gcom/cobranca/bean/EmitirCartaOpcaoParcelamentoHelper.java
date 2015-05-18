
package gcom.cobranca.bean;

import java.io.Serializable;
import java.util.List;

//UC01101 - Emitir Carta com Opção de parcelamento
public class EmitirCartaOpcaoParcelamentoHelper
				implements Serializable {

	private String inscricao;

	private String roteiro;

	private String Hm;

	private String endereco;

	private String bairro;

	private String matricula;

	private String nomeCliente;

	private String textoNomeEMatriculaCliente;

	private String saldoPrincipal;

	private String juros;

	private String multa;

	private String dividaTotal;

	private String cep;

	private String idCliente;

	private List<EmitirCartaOpcaoParcelamentoDetailHelper> opcoesDeParcelamento;

	public String getIdCliente(){

		return idCliente;
	}

	public void setIdCliente(String idCliente){

		this.idCliente = idCliente;
	}

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public List<EmitirCartaOpcaoParcelamentoDetailHelper> getOpcoesDeParcelamento(){

		return opcoesDeParcelamento;
	}

	public void setOpcoesDeParcelamento(List<EmitirCartaOpcaoParcelamentoDetailHelper> opcoesDeParcelamento){

		this.opcoesDeParcelamento = opcoesDeParcelamento;
	}

	public String getTextoNomeEMatriculaCliente(){

		return textoNomeEMatriculaCliente;
	}

	public void setTextoNomeEMatriculaCliente(String textoNomeEMatriculaCliente){

		this.textoNomeEMatriculaCliente = textoNomeEMatriculaCliente;
	}

	public String getSaldoPrincipal(){

		return saldoPrincipal;
	}

	public void setSaldoPrincipal(String saldoPrincipal){

		this.saldoPrincipal = saldoPrincipal;
	}

	public String getJuros(){

		return juros;
	}

	public void setJuros(String juros){

		this.juros = juros;
	}

	public String getMulta(){

		return multa;
	}

	public void setMulta(String multa){

		this.multa = multa;
	}

	public String getDividaTotal(){

		return dividaTotal;
	}

	public void setDividaTotal(String dividaTotal){

		this.dividaTotal = dividaTotal;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public String getRoteiro(){

		return roteiro;
	}

	public void setRoteiro(String roteiro){

		this.roteiro = roteiro;
	}

	public String getHm(){

		return Hm;
	}

	public void setHm(String hm){

		Hm = hm;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getBairro(){

		return bairro;
	}

	public void setBairro(String bairro){

		this.bairro = bairro;
	}

	public String getCep(){

		return cep;
	}

	public void setCep(String cep){

		this.cep = cep;
	}

}
