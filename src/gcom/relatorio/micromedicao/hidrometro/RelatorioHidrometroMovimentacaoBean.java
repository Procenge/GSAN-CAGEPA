
package gcom.relatorio.micromedicao.hidrometro;

import gcom.relatorio.RelatorioBean;

/**
 * @author Eduardo Oliveira
 * @created 05/07/2013
 * @version 1.0
 */

public class RelatorioHidrometroMovimentacaoBean
				implements RelatorioBean {

	private String numero;

	private String marca;

	private String capacidade;

	private String situacao;

	private String localOrigem;

	private String localDestino;

	private String dataTransferencia;

	public RelatorioHidrometroMovimentacaoBean() {

	}

	public RelatorioHidrometroMovimentacaoBean(String numero, String marca, String capacidade,
												String situacao, String localOrigem, String localDestino, String dataTransferencia) {

		this.numero = numero;
		this.marca = marca;
		this.capacidade = capacidade;
		this.situacao = situacao;
		this.localOrigem = localOrigem;
		this.localDestino = localDestino;
		this.dataTransferencia = dataTransferencia;

	}


	public String getNumero(){

		return numero;
	}

	public void setNumero(String numero){

		this.numero = numero;
	}


	public String getMarca(){

		return marca;
	}

	public void setMarca(String marca){

		this.marca = marca;
	}

	public String getCapacidade(){

		return capacidade;
	}

	public void setCapacidade(String capacidade){

		this.capacidade = capacidade;
	}

	public String getSituacao(){

		return situacao;
	}

	public void setSituacao(String situacao){

		this.situacao = situacao;
	}

	public String getLocalOrigem(){

		return localOrigem;
	}

	public void setLocalOrigem(String localOrigem){

		this.localOrigem = localOrigem;
	}

	public String getLocalDestino(){

		return localDestino;
	}

	public void setLocalDestino(String localDestino){

		this.localDestino = localDestino;
	}

	public String getDataTransferencia(){

		return dataTransferencia;
	}

	public void setDataTransferecia(String dataTransferencia){

		this.dataTransferencia = dataTransferencia;
	}



}
