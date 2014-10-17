
package gcom.gui.cobranca;

import gcom.cobranca.ResolucaoDiretoriaLayout;

import java.io.Serializable;
import java.util.Date;

public class ResolucaoDiretoriaGrupoHelper
				implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private String numero;

	private String assunto;

	private Date dataInicio;

	private Date dataFim;

	private ResolucaoDiretoriaLayout resolucaoDiretoriaLayout;

	private Integer grupo;

	private String tipoPesquisa;

	private String indicadorUsoRDParcImovel;

	private String indicadorUsoRDUsuarios;

	private String indicadorUsoRDDebitoCobrar;

	private String indicadorEmissaoAssuntoConta;

	private String indicadorTrataMediaAtualizacaoMonetaria;

	private String indicadorCobrarDescontosArrasto;

	private String indicadorArrasto;

	private Integer localidade;

	private Integer anoMesReferenciaDebitoInicio;

	private Integer anoMesReferenciaDebitoFim;

	public ResolucaoDiretoriaGrupoHelper() {

	}

	public ResolucaoDiretoriaGrupoHelper clone(){

		try{
			return (ResolucaoDiretoriaGrupoHelper) super.clone();
		}catch(CloneNotSupportedException e){
			return null;
		}
	}

	public String getNumero(){

		return numero;
	}

	public void setNumero(String numero){

		this.numero = numero;
	}

	public String getAssunto(){

		return assunto;
	}

	public void setAssunto(String assunto){

		this.assunto = assunto;
	}

	public Date getDataInicio(){

		return dataInicio;
	}

	public void setDataInicio(Date dataInicio){

		this.dataInicio = dataInicio;
	}

	public Date getDataFim(){

		return dataFim;
	}

	public void setDataFim(Date dataFim){

		this.dataFim = dataFim;
	}

	public void setGrupo(Integer grupo){

		this.grupo = grupo;
	}

	public ResolucaoDiretoriaLayout getResolucaoDiretoriaLayout(){

		return resolucaoDiretoriaLayout;
	}

	public void setResolucaoDiretoriaLayout(ResolucaoDiretoriaLayout resolucaoDiretoriaLayout){

		this.resolucaoDiretoriaLayout = resolucaoDiretoriaLayout;
	}

	public Integer getGrupo(){

		return grupo;
	}

	public String getTipoPesquisa(){

		return tipoPesquisa;
	}

	public void setTipoPesquisa(String tipoPesquisa){

		this.tipoPesquisa = tipoPesquisa;
	}

	public String getIndicadorUsoRDParcImovel(){

		return indicadorUsoRDParcImovel;
	}

	public void setIndicadorUsoRDParcImovel(String indicadorUsoRDParcImovel){

		this.indicadorUsoRDParcImovel = indicadorUsoRDParcImovel;
	}

	public String getIndicadorUsoRDUsuarios(){

		return indicadorUsoRDUsuarios;
	}

	public void setIndicadorUsoRDUsuarios(String indicadorUsoRDUsuarios){

		this.indicadorUsoRDUsuarios = indicadorUsoRDUsuarios;
	}

	public String getIndicadorUsoRDDebitoCobrar(){

		return indicadorUsoRDDebitoCobrar;
	}

	public void setIndicadorUsoRDDebitoCobrar(String indicadorUsoRDDebitoCobrar){

		this.indicadorUsoRDDebitoCobrar = indicadorUsoRDDebitoCobrar;
	}

	public Integer getLocalidade(){

		return localidade;
	}

	public void setLocalidade(Integer localidade){

		this.localidade = localidade;
	}

	public Integer getAnoMesReferenciaDebitoInicio(){

		return anoMesReferenciaDebitoInicio;
	}

	public void setAnoMesReferenciaDebitoInicio(Integer anoMesReferenciaDebitoInicio){

		this.anoMesReferenciaDebitoInicio = anoMesReferenciaDebitoInicio;
	}

	public Integer getAnoMesReferenciaDebitoFim(){

		return anoMesReferenciaDebitoFim;
	}

	public void setAnoMesReferenciaDebitoFim(Integer anoMesReferenciaDebitoFim){

		this.anoMesReferenciaDebitoFim = anoMesReferenciaDebitoFim;
	}

	public String getIndicadorEmissaoAssuntoConta(){

		return indicadorEmissaoAssuntoConta;
	}

	public void setIndicadorEmissaoAssuntoConta(String indicadorEmissaoAssuntoConta){

		this.indicadorEmissaoAssuntoConta = indicadorEmissaoAssuntoConta;
	}

	public String getIndicadorTrataMediaAtualizacaoMonetaria(){

		return indicadorTrataMediaAtualizacaoMonetaria;
	}

	public void setIndicadorTrataMediaAtualizacaoMonetaria(String indicadorTrataMediaAtualizacaoMonetaria){

		this.indicadorTrataMediaAtualizacaoMonetaria = indicadorTrataMediaAtualizacaoMonetaria;
	}

	public String getIndicadorCobrarDescontosArrasto(){

		return indicadorCobrarDescontosArrasto;
	}

	public void setIndicadorCobrarDescontosArrasto(String indicadorCobrarDescontosArrasto){

		this.indicadorCobrarDescontosArrasto = indicadorCobrarDescontosArrasto;
	}

	public void setIndicadorArrasto(String indicadorArrasto){

		this.indicadorArrasto = indicadorArrasto;
	}

	public String getIndicadorArrasto(){

		return indicadorArrasto;
	}

}
