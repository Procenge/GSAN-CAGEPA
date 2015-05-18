package gcom.gui.faturamento.bean;

import gcom.util.Util;

import java.io.Serializable;
import java.math.BigDecimal;


public class ContaSimularCalculoDadosReaisHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer anoMesReferenciaConta;

	private String situacaoConta;

	private BigDecimal valorOriginalAgua;

	private BigDecimal valorOriginalEsgoto;

	private BigDecimal valorOriginalTotal;

	private BigDecimal valorRecalculadoAgua;

	private BigDecimal valorRecalculadoEsgoto;

	private BigDecimal valorRecalculadoTotal;

	private BigDecimal valorDiferenca;

	private Integer idConta;

	private Integer consumoFaturadoAgua;

	private Integer consumoFaturadoEsgoto;

	private BigDecimal percentualEsgoto;

	private Short indicadorHistorico;

	private Integer idLigacaoAguaSituacao;

	private Integer idLigacaoEsgotoSituacao;

	private Short indicadorFaturamentoAgua;

	private Short indicadorFaturamentoEsgoto;

	private Integer idImovel;

	private Integer idConsumoTarifaImovel;

	BigDecimal valorCreditos;

	BigDecimal valorDebitos;

	BigDecimal valorImpostos;

	public ContaSimularCalculoDadosReaisHelper() {

	}

	public Integer getAnoMesReferenciaConta(){

		return anoMesReferenciaConta;
	}

	public void setAnoMesReferenciaConta(Integer anoMesReferenciaConta){

		this.anoMesReferenciaConta = anoMesReferenciaConta;
	}

	public String getSituacaoConta(){

		return situacaoConta;
	}

	public void setSituacaoConta(String situacaoConta){

		this.situacaoConta = situacaoConta;
	}

	public BigDecimal getValorOriginalAgua(){

		return valorOriginalAgua;
	}

	public void setValorOriginalAgua(BigDecimal valorOriginalAgua){

		this.valorOriginalAgua = valorOriginalAgua;
	}

	public BigDecimal getValorOriginalEsgoto(){

		return valorOriginalEsgoto;
	}

	public void setValorOriginalEsgoto(BigDecimal valorOriginalEsgoto){

		this.valorOriginalEsgoto = valorOriginalEsgoto;
	}

	public BigDecimal getValorOriginalTotal(){

		return valorOriginalTotal;
	}

	public void setValorOriginalTotal(BigDecimal valorOriginalTotal){

		this.valorOriginalTotal = valorOriginalTotal;
	}

	public BigDecimal getValorRecalculadoAgua(){

		return valorRecalculadoAgua;
	}

	public void setValorRecalculadoAgua(BigDecimal valorRecalculadoAgua){

		this.valorRecalculadoAgua = valorRecalculadoAgua;
	}

	public BigDecimal getValorRecalculadoEsgoto(){

		return valorRecalculadoEsgoto;
	}

	public void setValorRecalculadoEsgoto(BigDecimal valorRecalculadoEsgoto){

		this.valorRecalculadoEsgoto = valorRecalculadoEsgoto;
	}

	public BigDecimal getValorRecalculadoTotal(){

		return valorRecalculadoTotal;
	}

	public void setValorRecalculadoTotal(BigDecimal valorRecalculadoTotal){

		this.valorRecalculadoTotal = valorRecalculadoTotal;
	}

	public BigDecimal getValorDiferenca(){

		return valorDiferenca;
	}

	public void setValorDiferenca(BigDecimal valorDiferenca){

		this.valorDiferenca = valorDiferenca;
	}

	public Integer getConsumoFaturadoAgua(){

		return consumoFaturadoAgua;
	}

	public void setConsumoFaturadoAgua(Integer consumoFaturadoAgua){

		this.consumoFaturadoAgua = consumoFaturadoAgua;
	}

	public Integer getConsumoFaturadoEsgoto(){

		return consumoFaturadoEsgoto;
	}

	public void setConsumoFaturadoEsgoto(Integer consumoFaturadoEsgoto){

		this.consumoFaturadoEsgoto = consumoFaturadoEsgoto;
	}

	public BigDecimal getPercentualEsgoto(){

		return percentualEsgoto;
	}

	public void setPercentualEsgoto(BigDecimal percentualEsgoto){

		this.percentualEsgoto = percentualEsgoto;
	}

	public Short getIndicadorHistorico(){

		return indicadorHistorico;
	}

	public void setIndicadorHistorico(Short indicadorHistorico){

		this.indicadorHistorico = indicadorHistorico;
	}

	public Integer getIdLigacaoAguaSituacao(){

		return idLigacaoAguaSituacao;
	}

	public void setIdLigacaoAguaSituacao(Integer idLigacaoAguaSituacao){

		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
	}

	public Integer getIdLigacaoEsgotoSituacao(){

		return idLigacaoEsgotoSituacao;
	}

	public void setIdLigacaoEsgotoSituacao(Integer idLigacaoEsgotoSituacao){

		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
	}

	public Short getIndicadorFaturamentoAgua(){

		return indicadorFaturamentoAgua;
	}

	public void setIndicadorFaturamentoAgua(Short indicadorFaturamentoAgua){

		this.indicadorFaturamentoAgua = indicadorFaturamentoAgua;
	}

	public Short getIndicadorFaturamentoEsgoto(){

		return indicadorFaturamentoEsgoto;
	}

	public void setIndicadorFaturamentoEsgoto(Short indicadorFaturamentoEsgoto){

		this.indicadorFaturamentoEsgoto = indicadorFaturamentoEsgoto;
	}

	public Integer getIdConta(){

		return idConta;
	}

	public void setIdConta(Integer idConta){

		this.idConta = idConta;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public String getIdImovelFormado(){

		String retorno = "";

		if(this.idImovel != null){

			retorno = this.idImovel.toString();
		}

		return retorno;
	}

	public String getAnoMesReferenciaContaFormado(){

		String retorno = "";

		if(this.anoMesReferenciaConta != null){

			retorno = Util.formatarAnoMesParaMesAno(this.anoMesReferenciaConta);
		}

		return retorno;
	}

	public Integer getIdConsumoTarifaImovel(){

		return idConsumoTarifaImovel;
	}

	public void setIdConsumoTarifaImovel(Integer idConsumoTarifaImovel){

		this.idConsumoTarifaImovel = idConsumoTarifaImovel;
	}

	public BigDecimal getValorCreditos(){

		return valorCreditos;
	}

	public void setValorCreditos(BigDecimal valorCreditos){

		this.valorCreditos = valorCreditos;
	}

	public BigDecimal getValorDebitos(){

		return valorDebitos;
	}

	public void setValorDebitos(BigDecimal valorDebitos){

		this.valorDebitos = valorDebitos;
	}

	public BigDecimal getValorImpostos(){

		return valorImpostos;
	}

	public void setValorImpostos(BigDecimal valorImpostos){

		this.valorImpostos = valorImpostos;
	}

	public BigDecimal getValorTotalContaComDadosRecalculo(){

		BigDecimal valorTotalConta = BigDecimal.ZERO;

		// Valor de água
		if(this.getValorRecalculadoAgua() != null){

			valorTotalConta = valorTotalConta.add(this.getValorRecalculadoAgua());
		}

		// Valor de esgoto
		if(this.getValorRecalculadoEsgoto() != null){

			valorTotalConta = valorTotalConta.add(this.getValorRecalculadoEsgoto());
		}

		// Valor dos débitos
		if(this.getValorDebitos() != null){

			valorTotalConta = valorTotalConta.add(this.getValorDebitos());
		}

		// Valor dos créditos
		if(this.getValorCreditos() != null){

			valorTotalConta = valorTotalConta.subtract(this.getValorCreditos());
		}

		// Valor dos Impostos
		if(this.getValorImpostos() != null){

			valorTotalConta = valorTotalConta.subtract(this.getValorImpostos());
		}

		return valorTotalConta;
	}

}
