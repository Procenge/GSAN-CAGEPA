/**
 * 
 */

package gcom.gui.arrecadacao.pagamento;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

/**
 * Action responsável por exibir a página de Classificar em Lote Pagamentos Não Classificados.
 * [UC3080] Classificar em Lote Pagamentos Não Classificados.
 * 
 * @author Josenildo Neves
 * @date 10/12/2012
 */
public class RelatorioClassificarLotePagamentosNaoClassificadosBean
				implements RelatorioBean {

	private String matriculaImovel;

	private String inscricaoImovel;

	private String referenciaFatura;

	private String sequencial;

	private String dataPagamento;

	private BigDecimal valorConta;

	private String valorCorrigido;

	private BigDecimal valorPagamento;

	private String ocorrencia;

	private Integer totalContas;

	private Integer totalPagamentos;

	private String totalValorPagamentos;
	
	private Integer idLocalidade;

	private Integer idConta;

	private Integer idPagamento;

	private String valorContaFormatado;

	private String valorPagamentoFormatado;

	private String localidade;

	public RelatorioClassificarLotePagamentosNaoClassificadosBean(String matriculaImovel, String inscricaoImovel, String referenciaFatura,
																	String sequencial, String dataPagamento, String valorContaFormatado,
																	String valorCorrigido, String valorPagamentoFormatado,
																	String ocorrencia,
																	Integer idLocalidade, Integer idConta, Integer idPagamento,
 BigDecimal valorConta, BigDecimal valorPagamento,
																	Integer totalContas, Integer totalPagamentos,
																	String totalValorPagamentos, String localidade) {

		super();
		this.matriculaImovel = matriculaImovel;
		this.inscricaoImovel = inscricaoImovel;
		this.referenciaFatura = referenciaFatura;
		this.sequencial = sequencial;
		this.dataPagamento = dataPagamento;
		this.valorContaFormatado = valorContaFormatado;
		this.valorCorrigido = valorCorrigido;
		this.valorPagamentoFormatado = valorPagamentoFormatado;
		this.ocorrencia = ocorrencia;
		this.totalContas = totalContas;
		this.totalPagamentos = totalPagamentos;
		this.totalValorPagamentos = totalValorPagamentos;
		this.idLocalidade = idLocalidade;
		this.idConta = idConta;
		this.idPagamento = idPagamento;
		this.valorConta = valorConta;
		this.valorPagamento = valorPagamento;
		this.localidade = localidade;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getReferenciaFatura(){

		return referenciaFatura;
	}

	public void setReferenciaFatura(String referenciaFatura){

		this.referenciaFatura = referenciaFatura;
	}

	public String getSequencial(){

		return sequencial;
	}

	public void setSequencial(String sequencial){

		this.sequencial = sequencial;
	}

	public String getDataPagamento(){

		return dataPagamento;
	}

	public void setDataPagamento(String dataPagamento){

		this.dataPagamento = dataPagamento;
	}

	public String getValorCorrigido(){

		return valorCorrigido;
	}

	public void setValorCorrigido(String valorCorrigido){

		this.valorCorrigido = valorCorrigido;
	}

	public String getOcorrencia(){

		return ocorrencia;
	}

	public void setOcorrencia(String ocorrencia){

		this.ocorrencia = ocorrencia;
	}

	public Integer getTotalContas(){

		return totalContas;
	}

	public void setTotalContas(Integer totalContas){

		this.totalContas = totalContas;
	}

	public Integer getTotalPagamentos(){

		return totalPagamentos;
	}

	public void setTotalPagamentos(Integer totalPagamentos){

		this.totalPagamentos = totalPagamentos;
	}

	public String getTotalValorPagamentos(){

		return totalValorPagamentos;
	}

	public void setTotalValorPagamentos(String totalValorPagamentos){

		this.totalValorPagamentos = totalValorPagamentos;
	}

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public Integer getIdConta(){

		return idConta;
	}

	public void setIdConta(Integer idConta){

		this.idConta = idConta;
	}

	public Integer getIdPagamento(){

		return idPagamento;
	}

	public void setIdPagamento(Integer idPagamento){

		this.idPagamento = idPagamento;
	}

	public BigDecimal getValorConta(){

		return valorConta;
	}

	public void setValorConta(BigDecimal valorConta){

		this.valorConta = valorConta;
	}

	public BigDecimal getValorPagamento(){

		return valorPagamento;
	}

	public void setValorPagamento(BigDecimal valorPagamento){

		this.valorPagamento = valorPagamento;
	}

	public String getValorContaFormatado(){

		return valorContaFormatado;
	}

	public void setValorContaFormatado(String valorContaFormatado){

		this.valorContaFormatado = valorContaFormatado;
	}

	public String getValorPagamentoFormatado(){

		return valorPagamentoFormatado;
	}

	public void setValorPagamentoFormatado(String valorPagamentoFormatado){

		this.valorPagamentoFormatado = valorPagamentoFormatado;
	}

	public String getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

}
