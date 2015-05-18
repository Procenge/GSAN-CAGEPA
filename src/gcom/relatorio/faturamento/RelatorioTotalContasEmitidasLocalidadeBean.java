
package gcom.relatorio.faturamento;

import gcom.relatorio.RelatorioBean;

public class RelatorioTotalContasEmitidasLocalidadeBean
				implements RelatorioBean {

	private String local;

	private String setor;

	private String quantidadeContas;

	private String valorContas;

	private String valorPublico;

	private String valorParticular;

	private String valorGeral;

	public String getLocal(){

		return local;
	}

	public void setLocal(String local){

		this.local = local;
	}

	public String getSetor(){

		return setor;
	}

	public void setSetor(String setor){

		this.setor = setor;
	}

	public String getQuantidadeContas(){

		return quantidadeContas;
	}

	public void setQuantidadeContas(String quantidadeContas){

		this.quantidadeContas = quantidadeContas;
	}

	public String getValorContas(){

		return valorContas;
	}

	public void setValorContas(String valorContas){

		this.valorContas = valorContas;
	}

	public String getValorPublico(){

		return valorPublico;
	}

	public void setValorPublico(String valorPublico){

		this.valorPublico = valorPublico;
	}

	public String getValorParticular(){

		return valorParticular;
	}

	public void setValorParticular(String valorParticular){

		this.valorParticular = valorParticular;
	}

	public String getValorGeral(){

		return valorGeral;
	}

	public void setValorGeral(String valorGeral){

		this.valorGeral = valorGeral;
	}

}
