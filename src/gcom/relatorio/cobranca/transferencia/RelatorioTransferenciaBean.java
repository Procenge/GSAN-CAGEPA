package gcom.relatorio.cobranca.transferencia;

import gcom.relatorio.RelatorioBean;
import gcom.util.Util;

import java.math.BigDecimal;

public class RelatorioTransferenciaBean
				implements RelatorioBean {

	private String nomeInteressado;

	private String cpfCnpjInteressado;

	private String enderecoInteressado;

	private String cepInteressado;

	private String matriculaInteressado;

	private String inscricaoInteressado;

	private String nomeDevedorOrigem;

	private String cpfCnpjDevedorOrigem;

	private String enderecoDevedorOrigem;

	private String cepDevedorOrigem;

	private String telefoneDevedorOrigem;

	private String telefoneInteressado;

	private String matriculaDevedorOrigem;

	private String inscricaoDevedorOrigem;

	private Integer idClienteDevedorOrigem;

	private BigDecimal valorTranferencia;

	private Integer idClienteInteressado;

	public Integer getIdClienteInteressado(){

		return idClienteInteressado;
	}

	public void setIdClienteInteressado(Integer idClienteInteressado){

		this.idClienteInteressado = idClienteInteressado;
	}

	public String getCepDevedorOrigem(){

		return cepDevedorOrigem;
	}

	public void setCepDevedorOrigem(String cepDevedorOrigem){

		this.cepDevedorOrigem = cepDevedorOrigem;
	}

	public String getNomeInteressado(){

		return nomeInteressado;
	}

	public void setNomeInteressado(String nomeInteressado){

		this.nomeInteressado = nomeInteressado;
	}

	public String getCpfCnpjInteressado(){

		return cpfCnpjInteressado;
	}

	public void setCpfCnpjInteressado(String cpfCnpjInteressado){

		this.cpfCnpjInteressado = cpfCnpjInteressado;
	}

	public String getEnderecoInteressado(){

		return enderecoInteressado;
	}

	public void setEnderecoInteressado(String enderecoInteressado){

		this.enderecoInteressado = enderecoInteressado;
	}

	public String getCepInteressado(){

		return cepInteressado;
	}

	public void setCepInteressado(String cepInteressado){

		this.cepInteressado = cepInteressado;
	}

	public String getMatriculaInteressado(){

		return matriculaInteressado;
	}

	public void setMatriculaInteressado(String matriculaInteressado){

		this.matriculaInteressado = matriculaInteressado;
	}

	public String getInscricaoInteressado(){

		return inscricaoInteressado;
	}

	public void setInscricaoInteressado(String inscricaoInteressado){

		this.inscricaoInteressado = inscricaoInteressado;
	}

	public String getNomeDevedorOrigem(){

		return nomeDevedorOrigem;
	}

	public void setNomeDevedorOrigem(String nomeDevedorOrigem){

		this.nomeDevedorOrigem = nomeDevedorOrigem;
	}

	public String getCpfCnpjDevedorOrigem(){

		return cpfCnpjDevedorOrigem;
	}

	public void setCpfCnpjDevedorOrigem(String cpfCnpjDevedorOrigem){

		this.cpfCnpjDevedorOrigem = cpfCnpjDevedorOrigem;
	}

	public String getEnderecoDevedorOrigem(){

		return enderecoDevedorOrigem;
	}

	public void setEnderecoDevedorOrigem(String enderecoDevedorOrigem){

		this.enderecoDevedorOrigem = enderecoDevedorOrigem;
	}

	public BigDecimal getValorTranferencia(){

		return valorTranferencia;
	}

	public void setValorTranferencia(BigDecimal valorTranferencia){

		this.valorTranferencia = valorTranferencia;
	}

	public String getMatriculaDevedorOrigem(){

		return matriculaDevedorOrigem;
	}

	public void setMatriculaDevedorOrigem(String matriculaDevedorOrigem){

		this.matriculaDevedorOrigem = matriculaDevedorOrigem;
	}

	public String getInscricaoDevedorOrigem(){

		return inscricaoDevedorOrigem;
	}

	public void setInscricaoDevedorOrigem(String inscricaoDevedorOrigem){

		this.inscricaoDevedorOrigem = inscricaoDevedorOrigem;
	}

	public Integer getIdClienteDevedorOrigem(){

		return idClienteDevedorOrigem;
	}


	public void setIdClienteDevedorOrigem(Integer idClienteDevedorOrigem){

		this.idClienteDevedorOrigem = idClienteDevedorOrigem;
	}

	public String getValorTransferenciaFormatado(){

		return Util.formatarMoedaReal(valorTranferencia);
	}



	public String getTelefoneDevedorOrigem(){

		return telefoneDevedorOrigem;
	}

	public void setTelefoneDevedorOrigem(String telefoneDevedorOrigem){

		this.telefoneDevedorOrigem = telefoneDevedorOrigem;
	}

	public String getTelefoneInteressado(){

		return telefoneInteressado;
	}

	public void setTelefoneInteressado(String telefoneInteressado){

		this.telefoneInteressado = telefoneInteressado;
	}

	public String getValorTransferenciaExtenso(){

		return Util.valorExtenso(valorTranferencia);

	}




}
