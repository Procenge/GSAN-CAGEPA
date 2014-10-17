package gcom.relatorio.cobranca;

import gcom.relatorio.RelatorioBean;

public class RelatorioRemuneracaoCobrancaAdministrativaModelo2DetailBean
				implements RelatorioBean {

	private String dataPagamento;

	private String matricula;

	private String cliente;

	private String cpfCnpj;

	private String conta;

	private String guiaPrestacao;

	private String debito;

	private String valorArrecadadoLote;

	private String valorBaseRemuneracao;



	public String getDescricaoTituloLote(){

		return descricaoTituloLote;
	}

	public void setDescricaoTituloLote(String descricaoTituloLote){

		this.descricaoTituloLote = descricaoTituloLote;
	}

	private String descricaoTituloLote;

	public String getDataPagamento(){

		return dataPagamento;
	}

	public String getMatricula(){

		return matricula;
	}

	public String getCliente(){

		return cliente;
	}

	public String getCpfCnpj(){

		return cpfCnpj;
	}

	public String getConta(){

		return conta;
	}

	public String getGuiaPrestacao(){

		return guiaPrestacao;
	}

	public String getDebito(){

		return debito;
	}

	public String getValorArrecadadoLote(){

		return valorArrecadadoLote;
	}

	public String getValorBaseRemuneracao(){

		return valorBaseRemuneracao;
	}

	public void setDataPagamento(String dataPagamento){

		this.dataPagamento = dataPagamento;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public void setCliente(String cliente){

		this.cliente = cliente;
	}

	public void setCpfCnpj(String cpfCnpj){

		this.cpfCnpj = cpfCnpj;
	}

	public void setConta(String conta){

		this.conta = conta;
	}

	public void setGuiaPrestacao(String guiaPrestacao){

		this.guiaPrestacao = guiaPrestacao;
	}

	public void setDebito(String debito){

		this.debito = debito;
	}

	public void setValorArrecadadoLote(String valorArrecadadoLote){

		this.valorArrecadadoLote = valorArrecadadoLote;
	}


	public void setValorBaseRemuneracao(String valorBaseRemuneracao){

		this.valorBaseRemuneracao = valorBaseRemuneracao;
	}

}
