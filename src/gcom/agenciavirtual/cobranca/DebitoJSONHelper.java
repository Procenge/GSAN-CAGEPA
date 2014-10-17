
package gcom.agenciavirtual.cobranca;

import java.math.BigDecimal;
import java.util.Date;

public class DebitoJSONHelper {

	private Integer idConta;

	private Integer referencia;

	private Integer consumo;

	private BigDecimal valorConta;

	private Date vencimento;

	private Boolean bloquearSegundaVia;

	private Boolean exibirAcrescimos;

	private BigDecimal valorAcrescimos;

	private Boolean emRevisao;

	public Integer getIdConta(){

		return idConta;
	}

	public void setIdConta(Integer idConta){

		this.idConta = idConta;
	}

	public Integer getReferencia(){

		return referencia;
	}

	public void setReferencia(Integer referencia){

		this.referencia = referencia;
	}

	public Integer getConsumo(){

		return consumo;
	}

	public void setConsumo(Integer consumo){

		this.consumo = consumo;
	}

	public BigDecimal getValorConta(){

		return valorConta;
	}

	public void setValorConta(BigDecimal valorConta){

		this.valorConta = valorConta;
	}

	public Date getVencimento(){

		return vencimento;
	}

	public void setVencimento(Date vencimento){

		this.vencimento = vencimento;
	}

	public Boolean getBloquearSegundaVia(){

		return bloquearSegundaVia;
	}

	public void setBloquearSegundaVia(Boolean bloquearSegundaVia){

		this.bloquearSegundaVia = bloquearSegundaVia;
	}

	public Boolean getExibirAcrescimos(){

		return exibirAcrescimos;
	}

	public void setExibirAcrescimos(Boolean exibirAcrescimos){

		this.exibirAcrescimos = exibirAcrescimos;
	}

	public BigDecimal getValorAcrescimos(){

		return valorAcrescimos;
	}

	public void setValorAcrescimos(BigDecimal valorAcrescimos){

		this.valorAcrescimos = valorAcrescimos;
	}

	public Boolean getEmRevisao(){

		return emRevisao;
	}

	public void setEmRevisao(Boolean emRevisao){

		this.emRevisao = emRevisao;
	}

	public boolean isEmRevisao(){

		return emRevisao == null ? false : emRevisao.booleanValue();
	}

}
