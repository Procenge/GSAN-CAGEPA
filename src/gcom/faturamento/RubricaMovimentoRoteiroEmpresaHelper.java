
package gcom.faturamento;

import java.math.BigDecimal;

public class RubricaMovimentoRoteiroEmpresaHelper {

	private int indexRubrica;

	private String descricao;

	private Short numeroPrestacao;

	private Short numeroPrestacaoCobrada;

	private BigDecimal valor;

	public RubricaMovimentoRoteiroEmpresaHelper(int indexRubrica, String descricao, Short numeroPrestacao, Short numeroPrestacaoCobrada,
												BigDecimal valor) {

		super();
		this.indexRubrica = indexRubrica;
		this.descricao = descricao;
		this.numeroPrestacao = numeroPrestacao;
		this.numeroPrestacaoCobrada = numeroPrestacaoCobrada;
		this.valor = valor;
	}

	public int getIndexRubrica(){

		return indexRubrica;
	}

	public void setIndexRubrica(int indexRubrica){

		this.indexRubrica = indexRubrica;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public Short getNumeroPrestacao(){

		return numeroPrestacao;
	}

	public void setNumeroPrestacao(Short numeroPrestacao){

		this.numeroPrestacao = numeroPrestacao;
	}

	public Short getNumeroPrestacaoCobrada(){

		return numeroPrestacaoCobrada;
	}

	public void setNumeroPrestacaoCobrada(Short numeroPrestacaoCobrada){

		this.numeroPrestacaoCobrada = numeroPrestacaoCobrada;
	}

	public BigDecimal getValor(){

		return valor;
	}

	public void setValor(BigDecimal valor){

		this.valor = valor;
	}

}
