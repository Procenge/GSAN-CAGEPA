
package gcom.relatorio.faturamento;

import gcom.relatorio.RelatorioBean;

public class RelatorioHistoricoFaturamentoBean
				implements RelatorioBean {

	private String mesAno;

	private String vencimento;

	private String valorAgua;

	private String valorEsgoto;

	private String valorDebitos;

	private String valorCreditos;

	private String valorImpostos;

	private String valorTotal;

	private String situacao;

	public RelatorioHistoricoFaturamentoBean(String mesAno, String vencimento, String valorAgua, String valorEsgoto, String valorDebitos,
												String valorCreditos, String valorImpostos, String valorTotal, String situacao) {

		this.mesAno = mesAno;
		this.vencimento = vencimento;
		this.valorAgua = valorAgua;
		this.valorEsgoto = valorEsgoto;
		this.valorDebitos = valorDebitos;
		this.valorCreditos = valorCreditos;
		this.valorImpostos = valorImpostos;
		this.valorTotal = valorTotal;
		this.situacao = situacao;
	}

	public String getMesAno(){

		return mesAno;
	}

	public void setMesAno(String mesAno){

		this.mesAno = mesAno;
	}

	public String getVencimento(){

		return vencimento;
	}

	public void setVencimento(String vencimento){

		this.vencimento = vencimento;
	}

	public String getValorAgua(){

		return valorAgua;
	}

	public void setValorAgua(String valorAgua){

		this.valorAgua = valorAgua;
	}

	public String getValorEsgoto(){

		return valorEsgoto;
	}

	public void setValorEsgoto(String valorEsgoto){

		this.valorEsgoto = valorEsgoto;
	}

	public String getValorDebitos(){

		return valorDebitos;
	}

	public void setValorDebitos(String valorDebitos){

		this.valorDebitos = valorDebitos;
	}

	public String getValorCreditos(){

		return valorCreditos;
	}

	public void setValorCreditos(String valorCreditos){

		this.valorCreditos = valorCreditos;
	}

	public String getValorImpostos(){

		return valorImpostos;
	}

	public void setValorImpostos(String valorImpostos){

		this.valorImpostos = valorImpostos;
	}

	public String getValorTotal(){

		return valorTotal;
	}

	public void setValorTotal(String valorTotal){

		this.valorTotal = valorTotal;
	}

	public String getSituacao(){

		return situacao;
	}

	public void setSituacao(String situacao){

		this.situacao = situacao;
	}

}