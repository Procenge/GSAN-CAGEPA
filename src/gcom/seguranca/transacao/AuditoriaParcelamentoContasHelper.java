package gcom.seguranca.transacao;

import java.math.BigDecimal;


public class AuditoriaParcelamentoContasHelper {

	private String codigo;

	private String data;

	private String hora;

	private String referenciaInicial;

	private String referenciaFinal;

	private String prestacao;

	private String gerencia;

	private BigDecimal valorEntrada;

	private BigDecimal valorPrestacao;

	private BigDecimal valorDebitoHistorico;

	private BigDecimal valorDebitoAtualizado;

	private BigDecimal valorParcelamento;

	public AuditoriaParcelamentoContasHelper() {

	}

	public String getCodigo(){

		return codigo;
	}

	public void setCodigo(String codigo){

		this.codigo = codigo;
	}

	public String getData(){

		return data;
	}

	public void setData(String data){

		this.data = data;
	}

	public String getHora(){

		return hora;
	}

	public void setHora(String hora){

		this.hora = hora;
	}

	public String getReferenciaInicial(){

		return referenciaInicial;
	}

	public void setReferenciaInicial(String referenciaInicial){

		this.referenciaInicial = referenciaInicial;
	}

	public String getReferenciaFinal(){

		return referenciaFinal;
	}

	public void setReferenciaFinal(String referenciaFinal){

		this.referenciaFinal = referenciaFinal;
	}

	public String getPrestacao(){

		return prestacao;
	}

	public void setPrestacao(String prestacao){

		this.prestacao = prestacao;
	}

	public String getGerencia(){

		return gerencia;
	}

	public void setGerencia(String gerencia){

		this.gerencia = gerencia;
	}

	public BigDecimal getValorEntrada(){

		return valorEntrada;
	}

	public void setValorEntrada(BigDecimal valorEntrada){

		this.valorEntrada = valorEntrada;
	}

	public BigDecimal getValorPrestacao(){

		return valorPrestacao;
	}

	public void setValorPrestacao(BigDecimal valorPrestacao){

		this.valorPrestacao = valorPrestacao;
	}

	public BigDecimal getValorDebitoHistorico(){

		return valorDebitoHistorico;
	}

	public void setValorDebitoHistorico(BigDecimal valorDebitoHistorico){

		this.valorDebitoHistorico = valorDebitoHistorico;
	}

	public BigDecimal getValorDebitoAtualizado(){

		return valorDebitoAtualizado;
	}

	public void setValorDebitoAtualizado(BigDecimal valorDebitoAtualizado){

		this.valorDebitoAtualizado = valorDebitoAtualizado;
	}

	public BigDecimal getValorParcelamento(){

		return valorParcelamento;
	}

	public void setValorParcelamento(BigDecimal valorParcelamento){

		this.valorParcelamento = valorParcelamento;
	}

}
