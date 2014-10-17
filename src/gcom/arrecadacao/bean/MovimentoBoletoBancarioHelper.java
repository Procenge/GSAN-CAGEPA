
package gcom.arrecadacao.bean;

import gcom.arrecadacao.aviso.AvisoBancario;
import gcom.arrecadacao.pagamento.Pagamento;

import java.math.BigDecimal;
import java.util.Collection;

public class MovimentoBoletoBancarioHelper {

	private Collection<Pagamento> colecaoPagamento;

	private AvisoBancario avisoBancario;

	private Integer idArrecadadorMovimentoItem;

	private BigDecimal valorRecebidoDeducao;

	private BigDecimal valorLiquidoDeducao;

	private BigDecimal valorRecebidoLiquidacao;

	public MovimentoBoletoBancarioHelper() {

	}

	public Collection<Pagamento> getColecaoPagamento(){

		return colecaoPagamento;
	}

	public void setColecaoPagamento(Collection<Pagamento> colecaoPagamento){

		this.colecaoPagamento = colecaoPagamento;
	}

	public Integer getIdArrecadadorMovimentoItem(){

		return idArrecadadorMovimentoItem;
	}

	public void setIdArrecadadorMovimentoItem(Integer idArrecadadorMovimentoItem){

		this.idArrecadadorMovimentoItem = idArrecadadorMovimentoItem;
	}

	public AvisoBancario getAvisoBancario(){

		return avisoBancario;
	}

	public void setAvisoBancario(AvisoBancario avisoBancario){

		this.avisoBancario = avisoBancario;
	}

	public BigDecimal getValorRecebidoDeducao(){

		return valorRecebidoDeducao;
	}

	public void setValorRecebidoDeducao(BigDecimal valorRecebidoDeducao){

		this.valorRecebidoDeducao = valorRecebidoDeducao;
	}

	public BigDecimal getValorLiquidoDeducao(){

		return valorLiquidoDeducao;
	}

	public void setValorLiquidoDeducao(BigDecimal valorLiquidoDeducao){

		this.valorLiquidoDeducao = valorLiquidoDeducao;
	}

	public BigDecimal getValorRecebidoLiquidacao(){

		return valorRecebidoLiquidacao;
	}

	public void setValorRecebidoLiquidacao(BigDecimal valorRecebidoLiquidacao){

		this.valorRecebidoLiquidacao = valorRecebidoLiquidacao;
	}

}
