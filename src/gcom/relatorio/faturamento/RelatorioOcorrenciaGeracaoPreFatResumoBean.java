
package gcom.relatorio.faturamento;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

/**
 * Relatório de Ocorrência da Geração do Pré-Faturamento - Resumo
 * 
 * @author Hebert Falcão
 * @date 06/04/2012
 */
public class RelatorioOcorrenciaGeracaoPreFatResumoBean
				implements RelatorioBean {

	private String localidade;

	private String setoresProcessados;

	private String quantidadeFaturados;

	private String quantidadeMedidos;

	private String quantidadeNaoMedidos;

	private String quantidadeTipo1;

	private String quantidadeTipo2;

	private String quantidadeTipo3;

	private String quantidadeTipo4;

	private String quantidadeTipo5;

	private String quantidadeTipo6;

	private String quantidadeTipo7;

	private BigDecimal valorTotalDebitos;

	private BigDecimal valorTotalCreditos;

	public String getSetoresProcessados(){

		return setoresProcessados;
	}

	public void setSetoresProcessados(String setoresProcessados){

		this.setoresProcessados = setoresProcessados;
	}

	public String getQuantidadeFaturados(){

		return quantidadeFaturados;
	}

	public void setQuantidadeFaturados(String quantidadeFaturados){

		this.quantidadeFaturados = quantidadeFaturados;
	}

	public String getQuantidadeMedidos(){

		return quantidadeMedidos;
	}

	public void setQuantidadeMedidos(String quantidadeMedidos){

		this.quantidadeMedidos = quantidadeMedidos;
	}

	public String getQuantidadeNaoMedidos(){

		return quantidadeNaoMedidos;
	}

	public void setQuantidadeNaoMedidos(String quantidadeNaoMedidos){

		this.quantidadeNaoMedidos = quantidadeNaoMedidos;
	}

	public String getQuantidadeTipo1(){

		return quantidadeTipo1;
	}

	public void setQuantidadeTipo1(String quantidadeTipo1){

		this.quantidadeTipo1 = quantidadeTipo1;
	}

	public String getQuantidadeTipo2(){

		return quantidadeTipo2;
	}

	public void setQuantidadeTipo2(String quantidadeTipo2){

		this.quantidadeTipo2 = quantidadeTipo2;
	}

	public String getQuantidadeTipo3(){

		return quantidadeTipo3;
	}

	public void setQuantidadeTipo3(String quantidadeTipo3){

		this.quantidadeTipo3 = quantidadeTipo3;
	}

	public String getQuantidadeTipo4(){

		return quantidadeTipo4;
	}

	public void setQuantidadeTipo4(String quantidadeTipo4){

		this.quantidadeTipo4 = quantidadeTipo4;
	}

	public String getQuantidadeTipo5(){

		return quantidadeTipo5;
	}

	public void setQuantidadeTipo5(String quantidadeTipo5){

		this.quantidadeTipo5 = quantidadeTipo5;
	}

	public String getQuantidadeTipo6(){

		return quantidadeTipo6;
	}

	public void setQuantidadeTipo6(String quantidadeTipo6){

		this.quantidadeTipo6 = quantidadeTipo6;
	}

	public BigDecimal getValorTotalDebitos(){

		return valorTotalDebitos;
	}

	public void setValorTotalDebitos(BigDecimal valorTotalDebitos){

		this.valorTotalDebitos = valorTotalDebitos;
	}

	public BigDecimal getValorTotalCreditos(){

		return valorTotalCreditos;
	}

	public void setValorTotalCreditos(BigDecimal valorTotalCreditos){

		this.valorTotalCreditos = valorTotalCreditos;
	}

	public String getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

	public String getQuantidadeTipo7(){

		return quantidadeTipo7;
	}

	public void setQuantidadeTipo7(String quantidadeTipo7){

		this.quantidadeTipo7 = quantidadeTipo7;
	}

}
