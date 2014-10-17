
package gcom.agenciavirtual.faturamento;

import java.util.Date;
import java.util.Set;

/**
 * @author Ado Rocha
 */
public class CalcularContaHelper {

	private Integer idCliente;

	private String nomeCliente;

	private Integer idTipoRelacao;

	private String descricaoTipoRelacao;

	private Date dataInicioRelacao;

	private Set clienteFones;

	private String cnpj;

	private String cpf;

	private Integer idRelacionamentoClienteImovel;

	private String email;

	private Integer idTipoCliente;

	private Short indicadorPessoaFisicaJuridica;

	private String rg;

	public Integer getIdCliente(){

		return idCliente;
	}

	public void setIdCliente(Integer idCliente){

		this.idCliente = idCliente;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public Integer getIdTipoRelacao(){

		return idTipoRelacao;
	}

	public void setIdTipoRelacao(Integer idTipoRelacao){

		this.idTipoRelacao = idTipoRelacao;
	}

	public String getDescricaoTipoRelacao(){

		return descricaoTipoRelacao;
	}

	public void setDescricaoTipoRelacao(String descricaoTipoRelacao){

		this.descricaoTipoRelacao = descricaoTipoRelacao;
	}


	public Date getDataInicioRelacao(){

		return dataInicioRelacao;
	}


	public void setDataInicioRelacao(Date dataInicioRelacao){

		this.dataInicioRelacao = dataInicioRelacao;
	}

	public String getCnpj(){

		return cnpj;
	}

	public void setCnpj(String cnpj){

		this.cnpj = cnpj;
	}

	public String getCpf(){

		return cpf;
	}

	public void setCpf(String cpf){

		this.cpf = cpf;
	}

	public Integer getIdRelacionamentoClienteImovel(){

		return idRelacionamentoClienteImovel;
	}

	public void setIdRelacionamentoClienteImovel(Integer idRelacionamentoClienteImovel){

		this.idRelacionamentoClienteImovel = idRelacionamentoClienteImovel;
	}

	public String getEmail(){

		return email;
	}

	public void setEmail(String email){

		this.email = email;
	}

	public Integer getIdTipoCliente(){

		return idTipoCliente;
	}

	public void setIdTipoCliente(Integer idTipoCliente){

		this.idTipoCliente = idTipoCliente;
	}

	public Short getIndicadorPessoaFisicaJuridica(){

		return indicadorPessoaFisicaJuridica;
	}

	public void setIndicadorPessoaFisicaJuridica(Short indicadorPessoaFisicaJuridica){

		this.indicadorPessoaFisicaJuridica = indicadorPessoaFisicaJuridica;
	}

	public String getRg(){

		return rg;
	}

	public void setRg(String rg){

		this.rg = rg;
	}

	public Set getClienteFones(){

		return clienteFones;
	}

	public void setClienteFones(Set clienteFones){

		this.clienteFones = clienteFones;
	}

}
