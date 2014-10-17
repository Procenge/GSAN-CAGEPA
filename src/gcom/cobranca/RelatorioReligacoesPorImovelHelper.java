
package gcom.cobranca;

import java.io.Serializable;

public class RelatorioReligacoesPorImovelHelper
				implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String matricula;

	private String categoria;

	private String servicoTipo;

	private String grupo;

	private String zona;

	private String qtdDebitos;

	private String valorDivida;

	private String ordemServico;

	private String motivoEncerramento;

	private String dataEmissao;

	private String dataExecucao;

	private String executor;

	private String valorPago;

	private String valorParcelado;

	private String entradaParcelamento;

	private String numeroParcelas;

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public String getCategoria(){

		return categoria;
	}

	public void setCategoria(String categoria){

		this.categoria = categoria;
	}

	public String getServicoTipo(){

		return servicoTipo;
	}

	public void setServicoTipo(String servicoTipo){

		this.servicoTipo = servicoTipo;
	}

	public String getGrupo(){

		return grupo;
	}

	public void setGrupo(String grupo){

		this.grupo = grupo;
	}

	public String getZona(){

		return zona;
	}

	public void setZona(String zona){

		this.zona = zona;
	}

	public String getQtdDebitos(){

		return qtdDebitos;
	}

	public void setQtdDebitos(String qtdDebitos){

		this.qtdDebitos = qtdDebitos;
	}

	public String getValorDivida(){

		return valorDivida;
	}

	public void setValorDivida(String valorDivida){

		this.valorDivida = valorDivida;
	}

	public String getOrdemServico(){

		return ordemServico;
	}

	public void setOrdemServico(String ordemServico){

		this.ordemServico = ordemServico;
	}

	public String getMotivoEncerramento(){

		return motivoEncerramento;
	}

	public void setMotivoEncerramento(String motivoEncerramento){

		this.motivoEncerramento = motivoEncerramento;
	}

	public String getDataEmissao(){

		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao){

		this.dataEmissao = dataEmissao;
	}

	public String getDataExecucao(){

		return dataExecucao;
	}

	public void setDataExecucao(String dataExecucao){

		this.dataExecucao = dataExecucao;
	}

	public String getExecutor(){

		return executor;
	}

	public void setExecutor(String executor){

		this.executor = executor;
	}

	public String getValorPago(){

		return valorPago;
	}

	public void setValorPago(String valorPago){

		this.valorPago = valorPago;
	}

	public String getValorParcelado(){

		return valorParcelado;
	}

	public void setValorParcelado(String valorParcelado){

		this.valorParcelado = valorParcelado;
	}

	public String getEntradaParcelamento(){

		return entradaParcelamento;
	}

	public void setEntradaParcelamento(String entradaParcelamento){

		this.entradaParcelamento = entradaParcelamento;
	}

	public String getNumeroParcelas(){

		return numeroParcelas;
	}

	public void setNumeroParcelas(String numeroParcelas){

		this.numeroParcelas = numeroParcelas;
	}
}
