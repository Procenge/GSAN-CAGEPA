
package gcom.relatorio.faturamento;

import gcom.relatorio.RelatorioBean;

public class RelatorioAnaliticoContasBean
				implements RelatorioBean {

	private String origem;

	private String idImovel;

	private String referencia; // mesAno

	private String titularConta;

	private String hora;

	private String valorAtual;

	private String valorAnterior;

	private String ultimaAlteracao;

	private String motivo;

	private String usuarioGsan;

	private String nenhumResultado;

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

	public String getOrigem(){

		return origem;
	}

	public void setOrigem(String origem){

		this.origem = origem;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
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

	public String getValorAtual(){

		return valorAtual;
	}

	public void setValorAtual(String valorAtual){

		this.valorAtual = valorAtual;
	}

	public String getValorAnterior(){

		return valorAnterior;
	}

	public void setValorAnterior(String valorAnterior){

		this.valorAnterior = valorAnterior;
	}

	public String getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(String ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String getNenhumResultado(){

		return nenhumResultado;
	}

	public void setNenhumResultado(String nenhumResultado){

		this.nenhumResultado = nenhumResultado;
	}


	public RelatorioAnaliticoContasBean(String origem, String idImovel, String referencia, String titularConta, String hora,
										String valorAtual, String valorAnterior, String ultimaAlteracao, String motivo, String usuarioGsan,
										String nenhumResultado) {

		super();
		this.origem = origem;
		this.idImovel = idImovel;
		this.referencia = referencia;
		this.titularConta = titularConta;
		this.hora = hora;
		this.valorAtual = valorAtual;
		this.valorAnterior = valorAnterior;
		this.ultimaAlteracao = ultimaAlteracao;
		this.motivo = motivo;
		this.usuarioGsan = usuarioGsan;
		this.nenhumResultado = nenhumResultado;
	}



}
