
package gcom.relatorio.cobranca;

import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Helper para construção do Relatório de Remuneração da Cobrança Administrativa
 * 
 * @author Luciano Galvão
 * @date 04/09/2012
 */
public class RelatorioRemuneracaoCobrancaAdministrativaHelper
				implements Serializable {

	private Date periodoPagamentoInicial;

	private Date periodoPagamentoFinal;

	private Collection<Localidade> localidadesComSetorComercial;

	private Collection<Localidade> localidadesSemSetorComercial;

	private Collection<SetorComercial> setoresComerciais;

	private String situacaoRemuneracao;

	private String indicadorConfirmaPagamento;

	/**
	 * Construtor da classe
	 * 
	 * @param periodoPagamentoInicial
	 * @param periodoPagamentoFinal
	 */
	public RelatorioRemuneracaoCobrancaAdministrativaHelper(Date periodoPagamentoInicial, Date periodoPagamentoFinal) {

		this.periodoPagamentoInicial = periodoPagamentoInicial;
		this.periodoPagamentoFinal = periodoPagamentoFinal;
	}

	public Date getPeriodoPagamentoInicial(){

		return periodoPagamentoInicial;
	}

	public void setPeriodoPagamentoInicial(Date periodoPagamentoInicial){

		this.periodoPagamentoInicial = periodoPagamentoInicial;
	}

	public Date getPeriodoPagamentoFinal(){

		return periodoPagamentoFinal;
	}

	public void setPeriodoPagamentoFinal(Date periodoPagamentoFinal){

		this.periodoPagamentoFinal = periodoPagamentoFinal;
	}

	public Collection<Localidade> getLocalidadesComSetorComercial(){

		return localidadesComSetorComercial;
	}

	public void setLocalidadesComSetorComercial(Collection<Localidade> localidadesComSetorComercial){

		this.localidadesComSetorComercial = localidadesComSetorComercial;
	}

	public Collection<SetorComercial> getSetoresComerciais(){

		return setoresComerciais;
	}

	public void setSetoresComerciais(Collection<SetorComercial> setoresComerciais){

		this.setoresComerciais = setoresComerciais;
	}

	public Collection<Localidade> getLocalidadesSemSetorComercial(){

		return localidadesSemSetorComercial;
	}

	public void setLocalidadesSemSetorComercial(Collection<Localidade> localidadesSemSetorComercial){

		this.localidadesSemSetorComercial = localidadesSemSetorComercial;
	}

	public String getSituacaoRemuneracao(){

		return situacaoRemuneracao;
	}

	public void setSituacaoRemuneracao(String situacaoRemuneracao){

		this.situacaoRemuneracao = situacaoRemuneracao;
	}

	public String getIndicadorConfirmaPagamento(){

		return indicadorConfirmaPagamento;
	}

	public void setIndicadorConfirmaPagamento(String indicadorConfirmaPagamento){

		this.indicadorConfirmaPagamento = indicadorConfirmaPagamento;
	}

}
