package gcom.faturamento.bean;

import gcom.cobranca.ajustetarifa.AjusteTarifa;
import gcom.cobranca.ajustetarifa.AjusteTarifaConta;

import java.util.Collection;

public class DadosHistoricoCREDTACHelper {

	private String descricaoLigacaoAguaSituacao;

	private String descricaoLigacaoEsgotoSituacao;

	private String descricaoCategoria;

	private String tarifaConsumo;

	private String dataCalculo;

	private String numeroParcelas;

	private String descricaoProcessamento;

	private String valorAnterior;

	private String valorAtual;

	private String valorCredito;

	private String valorSaldo;

	private String valorResidual;

	private String valorUtilizado;

	private AjusteTarifa ajusteTarifa;

	private Collection<AjusteTarifaConta> colecaoAjusteTarifaConta;

	public String getDescricaoLigacaoAguaSituacao(){

		return descricaoLigacaoAguaSituacao;
	}

	public void setDescricaoLigacaoAguaSituacao(String descricaoLigacaoAguaSituacao){

		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
	}

	public String getDescricaoLigacaoEsgotoSituacao(){

		return descricaoLigacaoEsgotoSituacao;
	}

	public void setDescricaoLigacaoEsgotoSituacao(String descricaoLigacaoEsgotoSituacao){

		this.descricaoLigacaoEsgotoSituacao = descricaoLigacaoEsgotoSituacao;
	}

	public String getDescricaoCategoria(){

		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria){

		this.descricaoCategoria = descricaoCategoria;
	}

	public String getTarifaConsumo(){

		return tarifaConsumo;
	}

	public void setTarifaConsumo(String tarifaConsumo){

		this.tarifaConsumo = tarifaConsumo;
	}

	public String getDataCalculo(){

		return dataCalculo;
	}

	public void setDataCalculo(String dataCalculo){

		this.dataCalculo = dataCalculo;
	}

	public String getNumeroParcelas(){

		return numeroParcelas;
	}

	public void setNumeroParcelas(String numeroParcelas){

		this.numeroParcelas = numeroParcelas;
	}

	public String getDescricaoProcessamento(){

		return descricaoProcessamento;
	}

	public void setDescricaoProcessamento(String descricaoProcessamento){

		this.descricaoProcessamento = descricaoProcessamento;
	}

	public String getValorAnterior(){

		return valorAnterior;
	}

	public void setValorAnterior(String valorAnterior){

		this.valorAnterior = valorAnterior;
	}

	public String getValorAtual(){

		return valorAtual;
	}

	public void setValorAtual(String valorAtual){

		this.valorAtual = valorAtual;
	}

	public String getValorCredito(){

		return valorCredito;
	}

	public void setValorCredito(String valorCredito){

		this.valorCredito = valorCredito;
	}

	public String getValorSaldo(){

		return valorSaldo;
	}

	public void setValorSaldo(String valorSaldo){

		this.valorSaldo = valorSaldo;
	}

	public String getValorResidual(){

		return valorResidual;
	}

	public void setValorResidual(String valorResidual){

		this.valorResidual = valorResidual;
	}

	public String getValorUtilizado(){

		return valorUtilizado;
	}

	public void setValorUtilizado(String valorUtilizado){

		this.valorUtilizado = valorUtilizado;
	}

	public Collection<AjusteTarifaConta> getColecaoAjusteTarifaConta(){

		return colecaoAjusteTarifaConta;
	}

	public void setColecaoAjusteTarifaConta(Collection<AjusteTarifaConta> colecaoAjusteTarifaConta){

		this.colecaoAjusteTarifaConta = colecaoAjusteTarifaConta;
	}

	public AjusteTarifa getAjusteTarifa(){

		return ajusteTarifa;
	}

	public void setAjusteTarifa(AjusteTarifa ajusteTarifa){

		this.ajusteTarifa = ajusteTarifa;
	}

}
