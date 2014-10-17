
package gcom.relatorio.faturamento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Relatório de Ocorrência da Geração do Pré-Faturamento - Resumo
 * 
 * @author Hebert Falcão
 * @date 06/04/2012
 */
public class RelatorioOcorrenciaGeracaoPreFatResumoHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Collection<Integer> colecaoSetorComercial;

	private int quantidadeFaturados;

	private int quantidadeMedidos;

	private int quantidadeNaoMedidos;

	private int quantidadeTipo1;

	private int quantidadeTipo2;

	private int quantidadeTipo3;

	private int quantidadeTipo4;

	private int quantidadeTipo5;

	private int quantidadeTipo6;

	private int quantidadeTipo7;

	private BigDecimal valorTotalDebitos;

	private BigDecimal valorTotalCreditos;

	public RelatorioOcorrenciaGeracaoPreFatResumoHelper() {

		super();
		this.colecaoSetorComercial = new ArrayList<Integer>();
		this.quantidadeFaturados = 0;
		this.quantidadeMedidos = 0;
		this.quantidadeNaoMedidos = 0;
		this.quantidadeTipo1 = 0;
		this.quantidadeTipo2 = 0;
		this.quantidadeTipo3 = 0;
		this.quantidadeTipo4 = 0;
		this.quantidadeTipo5 = 0;
		this.quantidadeTipo6 = 0;
		this.quantidadeTipo7 = 0;
		this.valorTotalDebitos = BigDecimal.ZERO;
		this.valorTotalCreditos = BigDecimal.ZERO;
	}

	public Collection<Integer> getColecaoSetorComercial(){

		return colecaoSetorComercial;
	}

	public void setColecaoSetorComercial(Collection<Integer> colecaoSetorComercial){

		this.colecaoSetorComercial = colecaoSetorComercial;
	}

	public int getQuantidadeFaturados(){

		return quantidadeFaturados;
	}

	public void setQuantidadeFaturados(int quantidadeFaturados){

		this.quantidadeFaturados = quantidadeFaturados;
	}

	public int getQuantidadeMedidos(){

		return quantidadeMedidos;
	}

	public void setQuantidadeMedidos(int quantidadeMedidos){

		this.quantidadeMedidos = quantidadeMedidos;
	}

	public int getQuantidadeNaoMedidos(){

		return quantidadeNaoMedidos;
	}

	public void setQuantidadeNaoMedidos(int quantidadeNaoMedidos){

		this.quantidadeNaoMedidos = quantidadeNaoMedidos;
	}

	public int getQuantidadeTipo1(){

		return quantidadeTipo1;
	}

	public void setQuantidadeTipo1(int quantidadeTipo1){

		this.quantidadeTipo1 = quantidadeTipo1;
	}

	public int getQuantidadeTipo2(){

		return quantidadeTipo2;
	}

	public void setQuantidadeTipo2(int quantidadeTipo2){

		this.quantidadeTipo2 = quantidadeTipo2;
	}

	public int getQuantidadeTipo3(){

		return quantidadeTipo3;
	}

	public void setQuantidadeTipo3(int quantidadeTipo3){

		this.quantidadeTipo3 = quantidadeTipo3;
	}

	public int getQuantidadeTipo4(){

		return quantidadeTipo4;
	}

	public void setQuantidadeTipo4(int quantidadeTipo4){

		this.quantidadeTipo4 = quantidadeTipo4;
	}

	public int getQuantidadeTipo5(){

		return quantidadeTipo5;
	}

	public void setQuantidadeTipo5(int quantidadeTipo5){

		this.quantidadeTipo5 = quantidadeTipo5;
	}

	public int getQuantidadeTipo6(){

		return quantidadeTipo6;
	}

	public void setQuantidadeTipo6(int quantidadeTipo6){

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

	public int getQuantidadeTipo7(){

		return quantidadeTipo7;
	}

	public void setQuantidadeTipo7(int quantidadeTipo7){

		this.quantidadeTipo7 = quantidadeTipo7;
	}

	/**
	 * Método addOcorrencia
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param qtdTipo01
	 * @param qtdTipo02
	 * @param qtdTipo03
	 * @param vlDebitos
	 * @param vlCreditos
	 * @param b
	 * @param temDebitosAnteriores
	 * @param c
	 * @author Marlos Ribeiro
	 * @since 19/04/2013
	 */
	public void addOcorrencia(Integer codSetor, int qtdTipo01, int qtdTipo02, int qtdTipo03, BigDecimal vlDebitos, BigDecimal vlCreditos,
					boolean temHidrometro, boolean temDebitosAnteriores, boolean temRubrica){

		colecaoSetorComercial.add(codSetor);
		quantidadeFaturados++;
		quantidadeTipo1 = qtdTipo01;
		quantidadeTipo2 = qtdTipo02;
		quantidadeTipo3 = qtdTipo03;
		quantidadeTipo4++;

		if(temHidrometro) quantidadeTipo5++;
		if(temDebitosAnteriores) quantidadeTipo6++;
		if(temRubrica) quantidadeTipo7++;

		if(vlCreditos != null) valorTotalCreditos = valorTotalCreditos.add(vlCreditos);
		if(vlDebitos != null) valorTotalDebitos = valorTotalDebitos.add(vlDebitos);

		if(temHidrometro) quantidadeMedidos++;
		else quantidadeNaoMedidos++;
	}
}
