package gcom.integracao.piramide;

import java.math.BigDecimal;


public class TabelaIntegracaoApuCsocRetida {
	
	/** identifier field */
	private TabelaIntegracaoApuCsocRetidaPK comp_id;

	private String codigoReceita;
	
	private String codigoNaturezaRetencaoFonte;
	
	private BigDecimal valorBaseRetencao;
	
	private BigDecimal valorRetido;
	
	private String codigoOperacaoRegistrada;
	
	private String codigoStatusResgistrado;
	
	private String descricaoErroRegistro;
	
	public TabelaIntegracaoApuCsocRetida() {

	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];

		retorno[0] = "comp_id";

		return retorno;
	}

	public TabelaIntegracaoApuCsocRetidaPK getComp_id(){

		return comp_id;
	}

	public void setComp_id(TabelaIntegracaoApuCsocRetidaPK comp_id){

		this.comp_id = comp_id;
	}

	public String getCodigoReceita(){

		return codigoReceita;
	}

	public void setCodigoReceita(String codigoReceita){

		this.codigoReceita = codigoReceita;
	}

	public String getCodigoNaturezaRetencaoFonte(){

		return codigoNaturezaRetencaoFonte;
	}

	public void setCodigoNaturezaRetencaoFonte(String codigoNaturezaRetencaoFonte){

		this.codigoNaturezaRetencaoFonte = codigoNaturezaRetencaoFonte;
	}

	public BigDecimal getValorBaseRetencao(){

		return valorBaseRetencao;
	}

	public void setValorBaseRetencao(BigDecimal valorBaseRetencao){

		this.valorBaseRetencao = valorBaseRetencao;
	}

	public BigDecimal getValorRetido(){

		return valorRetido;
	}

	public void setValorRetido(BigDecimal valorRetido){

		this.valorRetido = valorRetido;
	}

	public String getCodigoOperacaoRegistrada(){

		return codigoOperacaoRegistrada;
	}

	public void setCodigoOperacaoRegistrada(String codigoOperacaoRegistrada){

		this.codigoOperacaoRegistrada = codigoOperacaoRegistrada;
	}

	public String getCodigoStatusResgistrado(){

		return codigoStatusResgistrado;
	}

	public void setCodigoStatusResgistrado(String codigoStatusResgistrado){

		this.codigoStatusResgistrado = codigoStatusResgistrado;
	}

	public String getDescricaoErroRegistro(){

		return descricaoErroRegistro;
	}

	public void setDescricaoErroRegistro(String descricaoErroRegistro){

		this.descricaoErroRegistro = descricaoErroRegistro;
	}

	public void initializeLazy(){

		retornaCamposChavePrimaria();
	}

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((comp_id == null) ? 0 : comp_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		TabelaIntegracaoApuCsocRetida other = (TabelaIntegracaoApuCsocRetida) obj;
		if(comp_id == null){
			if(other.comp_id != null) return false;
		}else if(!comp_id.equals(other.comp_id)) return false;
		return true;
	}


}
