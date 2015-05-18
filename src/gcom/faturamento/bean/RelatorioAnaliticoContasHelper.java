package gcom.faturamento.bean;

import java.math.BigDecimal;
import java.util.Date;


public class RelatorioAnaliticoContasHelper {


	private String origem;

	private String IdImovel;

	private String referencia;

	private String titularConta;

	private String hora;

	private BigDecimal valorAtual;

	private BigDecimal valorAnterior;

	private Date ultimaAlteracao;

	private String motivo;

	private String usuarioGsan;

	private String nenhumResultado;

	public String getOrigem(){

		return origem;
	}

	public void setOrigem(String origem){

		this.origem = origem;
	}

	public String getIdImovel(){

		return IdImovel;
	}

	public void setIdImovel(String idImovel){

		IdImovel = idImovel;
	}

	public String getReferencia(){

		return referencia;
	}

	public void setReferencia(String referencia){

		this.referencia = referencia;
	}

	public String getTitularConta(){

		return titularConta;
	}

	public void setTitularConta(String titularConta){

		this.titularConta = titularConta;
	}

	public String getHora(){

		return hora;
	}

	public void setHora(String hora){

		this.hora = hora;
	}

	public BigDecimal getValorAtual(){

		return valorAtual;
	}

	public void setValorAtual(BigDecimal valorAtual){

		this.valorAtual = valorAtual;
	}

	public BigDecimal getValorAnterior(){

		return valorAnterior;
	}

	public void setValorAnterior(BigDecimal valorAnterior){

		this.valorAnterior = valorAnterior;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String getMotivo(){

		return motivo;
	}

	public void setMotivo(String motivo){

		this.motivo = motivo;
	}

	public String getUsuarioGsan(){

		return usuarioGsan;
	}

	public void setUsuarioGsan(String usuarioGsan){

		this.usuarioGsan = usuarioGsan;
	}

	public String getNenhumResultado(){

		return nenhumResultado;
	}

	public void setNenhumResultado(String nenhumResultado){

		this.nenhumResultado = nenhumResultado;
	}
}
