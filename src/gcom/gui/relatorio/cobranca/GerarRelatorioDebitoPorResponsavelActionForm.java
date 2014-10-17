
package gcom.gui.relatorio.cobranca;

import org.apache.struts.validator.ValidatorActionForm;

public class GerarRelatorioDebitoPorResponsavelActionForm
				extends ValidatorActionForm {

	private String idClienteInicial;

	private String nomeClienteInicial;

	private String idClienteFinal;

	private String nomeClienteFinal;

	private String idTipoClienteInicial;

	private String idTipoClienteFinal;

	private String referenciaDebitoInicial;

	private String referenciaDebitoFinal;

	private String indicadorTipoRelatorio;

	private String indicadorResponsabilidade;

	private String indicadorContasEmRevisao;

	private String idMotivoRevisao;

	private String indicadorValorCorrigido;

	private String idResponsavelInicialHide;

	private String idResponsavelFinalHide;

	private String nomeClienteInicialHide;

	private String nomeClienteFinalHide;

	private String esferaPoder;

	public String getIdClienteInicial(){

		return idClienteInicial;
	}

	public void setIdClienteInicial(String idClienteInicial){

		this.idClienteInicial = idClienteInicial;
	}

	public String getNomeClienteInicial(){

		return nomeClienteInicial;
	}

	public void setNomeClienteInicial(String nomeClienteInicial){

		this.nomeClienteInicial = nomeClienteInicial;
	}

	public String getIdClienteFinal(){

		return idClienteFinal;
	}

	public void setIdClienteFinal(String idClienteFinal){

		this.idClienteFinal = idClienteFinal;
	}

	public String getNomeClienteFinal(){

		return nomeClienteFinal;
	}

	public void setNomeClienteFinal(String nomeClienteFinal){

		this.nomeClienteFinal = nomeClienteFinal;
	}

	public String getIdTipoClienteInicial(){

		return idTipoClienteInicial;
	}

	public void setIdTipoClienteInicial(String idTipoClienteInicial){

		this.idTipoClienteInicial = idTipoClienteInicial;
	}

	public String getIdTipoClienteFinal(){

		return idTipoClienteFinal;
	}

	public void setIdTipoClienteFinal(String idTipoClienteFinal){

		this.idTipoClienteFinal = idTipoClienteFinal;
	}

	public String getReferenciaDebitoInicial(){

		return referenciaDebitoInicial;
	}

	public void setReferenciaDebitoInicial(String referenciaDebitoInicial){

		this.referenciaDebitoInicial = referenciaDebitoInicial;
	}

	public String getReferenciaDebitoFinal(){

		return referenciaDebitoFinal;
	}

	public void setReferenciaDebitoFinal(String referenciaDebitoFinal){

		this.referenciaDebitoFinal = referenciaDebitoFinal;
	}

	public String getIndicadorTipoRelatorio(){

		return indicadorTipoRelatorio;
	}

	public void setIndicadorTipoRelatorio(String indicadorTipoRelatorio){

		this.indicadorTipoRelatorio = indicadorTipoRelatorio;
	}

	public String getIndicadorResponsabilidade(){

		return indicadorResponsabilidade;
	}

	public void setIndicadorResponsabilidade(String indicadorResponsabilidade){

		this.indicadorResponsabilidade = indicadorResponsabilidade;
	}

	public String getIndicadorContasEmRevisao(){

		return indicadorContasEmRevisao;
	}

	public void setIndicadorContasEmRevisao(String indicadorContasEmRevisao){

		this.indicadorContasEmRevisao = indicadorContasEmRevisao;
	}

	public String getIdMotivoRevisao(){

		return idMotivoRevisao;
	}

	public void setIdMotivoRevisao(String idMotivoRevisao){

		this.idMotivoRevisao = idMotivoRevisao;
	}

	public String getIndicadorValorCorrigido(){

		return indicadorValorCorrigido;
	}

	public void setIndicadorValorCorrigido(String indicadorValorCorrigido){

		this.indicadorValorCorrigido = indicadorValorCorrigido;
	}

	public String getIdResponsavelInicialHide(){

		return idResponsavelInicialHide;
	}

	public void setIdResponsavelInicialHide(String idResponsavelInicialHide){

		this.idResponsavelInicialHide = idResponsavelInicialHide;
	}

	public String getIdResponsavelFinalHide(){

		return idResponsavelFinalHide;
	}

	public void setIdResponsavelFinalHide(String idResponsavelFinalHide){

		this.idResponsavelFinalHide = idResponsavelFinalHide;
	}

	public String getNomeClienteInicialHide(){

		return nomeClienteInicialHide;
	}

	public void setNomeClienteInicialHide(String nomeClienteInicialHide){

		this.nomeClienteInicialHide = nomeClienteInicialHide;
	}

	public String getNomeClienteFinalHide(){

		return nomeClienteFinalHide;
	}

	public void setNomeClienteFinalHide(String nomeClienteFinalHide){

		this.nomeClienteFinalHide = nomeClienteFinalHide;
	}

	public String getEsferaPoder(){

		return esferaPoder;
	}

	public void setEsferaPoder(String esferaPoder){

		this.esferaPoder = esferaPoder;
	}

}
