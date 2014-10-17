
package gcom.cobranca;

import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Resolução Diretoria Parâmetros Pagamento A Vista
 * 
 * @author Hebert Falcão
 * @date 30/10/2012
 */
public class ResolucaoDiretoriaParametrosPagamentoAVista {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Date dataPagamentoInicio;

	private Date dataPagamentoFinal;

	private BigDecimal percentualDescontoMulta;

	private BigDecimal percentualDescontoJurosMora;

	private BigDecimal percentualDescontoCorrecaoMonetaria;

	private String descricaoMensagemExtrato;

	private Date ultimaAlteracao;

	private ResolucaoDiretoria resolucaoDiretoria;

	private Integer anoMesReferenciaInicial;

	private Integer anoMesReferenciaFinal;

	private Date dataVencimentoInicial;

	private Date dataVencimentoFinal;

	public ResolucaoDiretoriaParametrosPagamentoAVista() {

	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Date getDataPagamentoInicio(){

		return dataPagamentoInicio;
	}

	public void setDataPagamentoInicio(Date dataPagamentoInicio){

		this.dataPagamentoInicio = dataPagamentoInicio;
	}

	public Date getDataPagamentoFinal(){

		return dataPagamentoFinal;
	}

	public void setDataPagamentoFinal(Date dataPagamentoFinal){

		this.dataPagamentoFinal = dataPagamentoFinal;
	}

	public BigDecimal getPercentualDescontoMulta(){

		return percentualDescontoMulta;
	}

	public void setPercentualDescontoMulta(BigDecimal percentualDescontoMulta){

		this.percentualDescontoMulta = percentualDescontoMulta;
	}

	public BigDecimal getPercentualDescontoJurosMora(){

		return percentualDescontoJurosMora;
	}

	public void setPercentualDescontoJurosMora(BigDecimal percentualDescontoJurosMora){

		this.percentualDescontoJurosMora = percentualDescontoJurosMora;
	}

	public BigDecimal getPercentualDescontoCorrecaoMonetaria(){

		return percentualDescontoCorrecaoMonetaria;
	}

	public void setPercentualDescontoCorrecaoMonetaria(BigDecimal percentualDescontoCorrecaoMonetaria){

		this.percentualDescontoCorrecaoMonetaria = percentualDescontoCorrecaoMonetaria;
	}

	public String getDescricaoMensagemExtrato(){

		return descricaoMensagemExtrato;
	}

	public void setDescricaoMensagemExtrato(String descricaoMensagemExtrato){

		this.descricaoMensagemExtrato = descricaoMensagemExtrato;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public ResolucaoDiretoria getResolucaoDiretoria(){

		return resolucaoDiretoria;
	}

	public void setResolucaoDiretoria(ResolucaoDiretoria resolucaoDiretoria){

		this.resolucaoDiretoria = resolucaoDiretoria;
	}

	public String getFormatarDataPagamentoInicio(){

		return Util.formatarData(dataPagamentoInicio);
	}

	public String getFormatarDataPagamentoInicioSemBarras(){

		return Util.formatarDataAAAAMMDD(dataPagamentoInicio);
	}

	public String getFormatarDataPagamentoFinal(){

		return Util.formatarData(dataPagamentoFinal);
	}

	public String getFormatarPercentualDescontoMulta(){

		return Util.formatarMoedaReal(percentualDescontoMulta);
	}

	public String getFormatarPercentualDescontoJurosMora(){

		return Util.formatarMoedaReal(percentualDescontoJurosMora);
	}

	public String getFormatarPercentualDescontoCorrecaoMonetaria(){

		return Util.formatarMoedaReal(percentualDescontoCorrecaoMonetaria);
	}

	public Integer getAnoMesReferenciaInicial(){

		return anoMesReferenciaInicial;
	}

	public void setAnoMesReferenciaInicial(Integer anoMesReferenciaInicial){

		this.anoMesReferenciaInicial = anoMesReferenciaInicial;
	}

	public Integer getAnoMesReferenciaFinal(){

		return anoMesReferenciaFinal;
	}

	public void setAnoMesReferenciaFinal(Integer anoMesReferenciaFinal){

		this.anoMesReferenciaFinal = anoMesReferenciaFinal;
	}

	public Date getDataVencimentoInicial(){

		return dataVencimentoInicial;
	}

	public void setDataVencimentoInicial(Date dataVencimentoInicial){

		this.dataVencimentoInicial = dataVencimentoInicial;
	}

	public Date getDataVencimentoFinal(){

		return dataVencimentoFinal;
	}

	public void setDataVencimentoFinal(Date dataVencimentoFinal){

		this.dataVencimentoFinal = dataVencimentoFinal;
	}

	public String getFormatarDescricaoMensagemExtrato(){

		String resultado = "";

		if(!Util.isVazioOuBranco(descricaoMensagemExtrato)){
			resultado = descricaoMensagemExtrato;
			if(resultado.length() > 30){
				resultado = resultado.substring(0, 30) + "...";
			}
		}

		return resultado;
	}

}
