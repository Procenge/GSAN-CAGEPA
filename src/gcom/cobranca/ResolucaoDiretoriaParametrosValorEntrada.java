
package gcom.cobranca;

import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Resolução Diretoria Parâmetros Valor Entrada
 * 
 * @author Hebert Falcão
 * @date 30/10/2012
 */
public class ResolucaoDiretoriaParametrosValorEntrada {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Date dataNegociacaoInicio;

	private Date dataNegociacaoFinal;

	private BigDecimal percentualMinimoEntrada;

	private Date ultimaAlteracao;

	private ResolucaoDiretoria resolucaoDiretoria;

	public ResolucaoDiretoriaParametrosValorEntrada() {

	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Date getDataNegociacaoInicio(){

		return dataNegociacaoInicio;
	}

	public void setDataNegociacaoInicio(Date dataNegociacaoInicio){

		this.dataNegociacaoInicio = dataNegociacaoInicio;
	}

	public Date getDataNegociacaoFinal(){

		return dataNegociacaoFinal;
	}

	public void setDataNegociacaoFinal(Date dataNegociacaoFinal){

		this.dataNegociacaoFinal = dataNegociacaoFinal;
	}

	public BigDecimal getPercentualMinimoEntrada(){

		return percentualMinimoEntrada;
	}

	public void setPercentualMinimoEntrada(BigDecimal percentualMinimoEntrada){

		this.percentualMinimoEntrada = percentualMinimoEntrada;
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

	public String getFormatarDataNegociacaoInicio(){

		return Util.formatarData(dataNegociacaoInicio);
	}

	public String getFormatarDataNegociacaoInicioSemBarras(){

		return Util.formatarDataAAAAMMDD(dataNegociacaoInicio);
	}

	public String getFormatarDataNegociacaoFinal(){

		return Util.formatarData(dataNegociacaoFinal);
	}

	public String getFormatarPercentualMinimoEntrada(){

		return Util.formatarMoedaReal(percentualMinimoEntrada);
	}

}
