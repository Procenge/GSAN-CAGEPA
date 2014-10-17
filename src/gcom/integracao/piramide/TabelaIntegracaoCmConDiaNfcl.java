package gcom.integracao.piramide;

import java.math.BigDecimal;


public class TabelaIntegracaoCmConDiaNfcl {


	/** identifier field */
	private TabelaIntegracaoCmConDiaNfclPK comp_id;

	private String codigoSituacaoTributariaPis;

	private String codigoSituacaoTributariaConfins;

	private String codigoContribuicaoSocialApuradaPis;

	private String codigoContribuicaoSocialApuradaConfins;

	private String codigoContaPisOrigem;

	private String codigoContaConfinsOrigem;

	private BigDecimal valorItem;

	private BigDecimal valorBasePis;

	private BigDecimal percentualAlicotaPis;

	private BigDecimal valorPis;

	private BigDecimal valorBaseConfins;

	private BigDecimal percentualAlicotaConfins;

	private BigDecimal valorConfins;

	private String descricaoErroRegistro;

	private String codigoTabelaNaturezaReceitaPis;

	private String codigoNaturezaReceitaPis;

	private String codigoTabelaNaturezaReceitaConfins;

	private String codigoNaturezaReceitaConfins;


	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];

		retorno[0] = "comp_id";

		return retorno;
	}

	/**
	 * TabelaIntegracaoCmConDiaNfcl
	 * <p>
	 * Esse método Constroi uma instância de TabelaIntegracaoCmConDiaNfcl.
	 * </p>
	 * 
	 * @author Marlos Ribeiro
	 * @since 15/03/2013
	 */
	public TabelaIntegracaoCmConDiaNfcl() {

		super();
	}

	public TabelaIntegracaoCmConDiaNfcl(TabelaIntegracaoCmConDiaNfclPK comp_id, String codigoSituacaoTributariaPis, String codigoSituacaoTributariaConfins,
								String codigoContribuicaoSocialApuradaPis, String codigoContribuicaoSocialApuradaConfins,
								String codigoContaPisOrigem, String codigoContaConfinsOrigem, BigDecimal valorItem,
								BigDecimal valorBasePis, BigDecimal percentualAlicotaPis, BigDecimal valorPis, BigDecimal valorBaseConfins,
								BigDecimal percentualAlicotaConfins, BigDecimal valorConfins, String descricaoErroRegistro,
								String codigoTabelaNaturezaReceitaPis, String codigoNaturezaReceitaPis,
								String codigoTabelaNaturezaReceitaConfins, String codigoNaturezaReceitaConfins) {

		super();
		this.comp_id = comp_id;
		this.codigoSituacaoTributariaPis = codigoSituacaoTributariaPis;
		this.codigoSituacaoTributariaConfins = codigoSituacaoTributariaConfins;
		this.codigoContribuicaoSocialApuradaPis = codigoContribuicaoSocialApuradaPis;
		this.codigoContribuicaoSocialApuradaConfins = codigoContribuicaoSocialApuradaConfins;
		this.codigoContaPisOrigem = codigoContaPisOrigem;
		this.codigoContaConfinsOrigem = codigoContaConfinsOrigem;
		this.valorItem = valorItem;
		this.valorBasePis = valorBasePis;
		this.percentualAlicotaPis = percentualAlicotaPis;
		this.valorPis = valorPis;
		this.valorBaseConfins = valorBaseConfins;
		this.percentualAlicotaConfins = percentualAlicotaConfins;
		this.valorConfins = valorConfins;
		this.descricaoErroRegistro = descricaoErroRegistro;
		this.codigoTabelaNaturezaReceitaPis = codigoTabelaNaturezaReceitaPis;
		this.codigoNaturezaReceitaPis = codigoNaturezaReceitaPis;
		this.codigoTabelaNaturezaReceitaConfins = codigoTabelaNaturezaReceitaConfins;
		this.codigoNaturezaReceitaConfins = codigoNaturezaReceitaConfins;
	}

	public TabelaIntegracaoCmConDiaNfclPK getComp_id(){

		return comp_id;
	}

	public void setComp_id(TabelaIntegracaoCmConDiaNfclPK comp_id){

		this.comp_id = comp_id;
	}

	public String getCodigoSituacaoTributariaPis(){

		return codigoSituacaoTributariaPis;
	}

	public void setCodigoSituacaoTributariaPis(String codigoSituacaoTributariaPis){

		this.codigoSituacaoTributariaPis = codigoSituacaoTributariaPis;
	}

	public String getCodigoSituacaoTributariaConfins(){

		return codigoSituacaoTributariaConfins;
	}

	public void setCodigoSituacaoTributariaConfins(String codigoSituacaoTributariaConfins){

		this.codigoSituacaoTributariaConfins = codigoSituacaoTributariaConfins;
	}

	public String getCodigoContribuicaoSocialApuradaPis(){

		return codigoContribuicaoSocialApuradaPis;
	}

	public void setCodigoContribuicaoSocialApuradaPis(String codigoContribuicaoSocialApuradaPis){

		this.codigoContribuicaoSocialApuradaPis = codigoContribuicaoSocialApuradaPis;
	}

	public String getCodigoContribuicaoSocialApuradaConfins(){

		return codigoContribuicaoSocialApuradaConfins;
	}

	public void setCodigoContribuicaoSocialApuradaConfins(String codigoContribuicaoSocialApuradaConfins){

		this.codigoContribuicaoSocialApuradaConfins = codigoContribuicaoSocialApuradaConfins;
	}

	public String getCodigoContaPisOrigem(){

		return codigoContaPisOrigem;
	}

	public void setCodigoContaPisOrigem(String codigoContaPisOrigem){

		this.codigoContaPisOrigem = codigoContaPisOrigem;
	}

	public String getCodigoContaConfinsOrigem(){

		return codigoContaConfinsOrigem;
	}

	public void setCodigoContaConfinsOrigem(String codigoContaConfinsOrigem){

		this.codigoContaConfinsOrigem = codigoContaConfinsOrigem;
	}

	public BigDecimal getValorItem(){

		return valorItem;
	}

	public void setValorItem(BigDecimal valorItem){

		this.valorItem = valorItem;
	}

	public BigDecimal getValorBasePis(){

		return valorBasePis;
	}

	public void setValorBasePis(BigDecimal valorBasePis){

		this.valorBasePis = valorBasePis;
	}

	public BigDecimal getPercentualAlicotaPis(){

		return percentualAlicotaPis;
	}

	public void setPercentualAlicotaPis(BigDecimal percentualAlicotaPis){

		this.percentualAlicotaPis = percentualAlicotaPis;
	}

	public BigDecimal getValorPis(){

		return valorPis;
	}

	public void setValorPis(BigDecimal valorPis){

		this.valorPis = valorPis;
	}

	public BigDecimal getValorBaseConfins(){

		return valorBaseConfins;
	}

	public void setValorBaseConfins(BigDecimal valorBaseConfins){

		this.valorBaseConfins = valorBaseConfins;
	}

	public BigDecimal getPercentualAlicotaConfins(){

		return percentualAlicotaConfins;
	}

	public void setPercentualAlicotaConfins(BigDecimal percentualAlicotaConfins){

		this.percentualAlicotaConfins = percentualAlicotaConfins;
	}

	public BigDecimal getValorConfins(){

		return valorConfins;
	}

	public void setValorConfins(BigDecimal valorConfins){

		this.valorConfins = valorConfins;
	}

	public String getDescricaoErroRegistro(){

		return descricaoErroRegistro;
	}

	public void setDescricaoErroRegistro(String descricaoErroRegistro){

		this.descricaoErroRegistro = descricaoErroRegistro;
	}

	public String getCodigoTabelaNaturezaReceitaPis(){

		return codigoTabelaNaturezaReceitaPis;
	}

	public void setCodigoTabelaNaturezaReceitaPis(String codigoTabelaNaturezaReceitaPis){

		this.codigoTabelaNaturezaReceitaPis = codigoTabelaNaturezaReceitaPis;
	}

	public String getCodigoNaturezaReceitaPis(){

		return codigoNaturezaReceitaPis;
	}

	public void setCodigoNaturezaReceitaPis(String codigoNaturezaReceitaPis){

		this.codigoNaturezaReceitaPis = codigoNaturezaReceitaPis;
	}

	public String getCodigoTabelaNaturezaReceitaConfins(){

		return codigoTabelaNaturezaReceitaConfins;
	}

	public void setCodigoTabelaNaturezaReceitaConfins(String codigoTabelaNaturezaReceitaConfins){

		this.codigoTabelaNaturezaReceitaConfins = codigoTabelaNaturezaReceitaConfins;
	}

	public String getCodigoNaturezaReceitaConfins(){

		return codigoNaturezaReceitaConfins;
	}

	public void setCodigoNaturezaReceitaConfins(String codigoNaturezaReceitaConfins){

		this.codigoNaturezaReceitaConfins = codigoNaturezaReceitaConfins;
	}

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoContaConfinsOrigem == null) ? 0 : codigoContaConfinsOrigem.hashCode());
		result = prime * result + ((codigoContaPisOrigem == null) ? 0 : codigoContaPisOrigem.hashCode());
		result = prime * result
						+ ((codigoContribuicaoSocialApuradaConfins == null) ? 0 : codigoContribuicaoSocialApuradaConfins.hashCode());
		result = prime * result + ((codigoContribuicaoSocialApuradaPis == null) ? 0 : codigoContribuicaoSocialApuradaPis.hashCode());
		result = prime * result + ((codigoNaturezaReceitaConfins == null) ? 0 : codigoNaturezaReceitaConfins.hashCode());
		result = prime * result + ((codigoNaturezaReceitaPis == null) ? 0 : codigoNaturezaReceitaPis.hashCode());
		result = prime * result + ((codigoSituacaoTributariaConfins == null) ? 0 : codigoSituacaoTributariaConfins.hashCode());
		result = prime * result + ((codigoSituacaoTributariaPis == null) ? 0 : codigoSituacaoTributariaPis.hashCode());
		result = prime * result + ((codigoTabelaNaturezaReceitaConfins == null) ? 0 : codigoTabelaNaturezaReceitaConfins.hashCode());
		result = prime * result + ((codigoTabelaNaturezaReceitaPis == null) ? 0 : codigoTabelaNaturezaReceitaPis.hashCode());
		result = prime * result + ((comp_id == null) ? 0 : comp_id.hashCode());
		result = prime * result + ((descricaoErroRegistro == null) ? 0 : descricaoErroRegistro.hashCode());
		result = prime * result + ((percentualAlicotaConfins == null) ? 0 : percentualAlicotaConfins.hashCode());
		result = prime * result + ((percentualAlicotaPis == null) ? 0 : percentualAlicotaPis.hashCode());
		result = prime * result + ((valorBaseConfins == null) ? 0 : valorBaseConfins.hashCode());
		result = prime * result + ((valorBasePis == null) ? 0 : valorBasePis.hashCode());
		result = prime * result + ((valorConfins == null) ? 0 : valorConfins.hashCode());
		result = prime * result + ((valorItem == null) ? 0 : valorItem.hashCode());
		result = prime * result + ((valorPis == null) ? 0 : valorPis.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		TabelaIntegracaoCmConDiaNfcl other = (TabelaIntegracaoCmConDiaNfcl) obj;
		if(codigoContaConfinsOrigem == null){
			if(other.codigoContaConfinsOrigem != null) return false;
		}else if(!codigoContaConfinsOrigem.equals(other.codigoContaConfinsOrigem)) return false;
		if(codigoContaPisOrigem == null){
			if(other.codigoContaPisOrigem != null) return false;
		}else if(!codigoContaPisOrigem.equals(other.codigoContaPisOrigem)) return false;
		if(codigoContribuicaoSocialApuradaConfins == null){
			if(other.codigoContribuicaoSocialApuradaConfins != null) return false;
		}else if(!codigoContribuicaoSocialApuradaConfins.equals(other.codigoContribuicaoSocialApuradaConfins)) return false;
		if(codigoContribuicaoSocialApuradaPis == null){
			if(other.codigoContribuicaoSocialApuradaPis != null) return false;
		}else if(!codigoContribuicaoSocialApuradaPis.equals(other.codigoContribuicaoSocialApuradaPis)) return false;
		if(codigoNaturezaReceitaConfins == null){
			if(other.codigoNaturezaReceitaConfins != null) return false;
		}else if(!codigoNaturezaReceitaConfins.equals(other.codigoNaturezaReceitaConfins)) return false;
		if(codigoNaturezaReceitaPis == null){
			if(other.codigoNaturezaReceitaPis != null) return false;
		}else if(!codigoNaturezaReceitaPis.equals(other.codigoNaturezaReceitaPis)) return false;
		if(codigoSituacaoTributariaConfins == null){
			if(other.codigoSituacaoTributariaConfins != null) return false;
		}else if(!codigoSituacaoTributariaConfins.equals(other.codigoSituacaoTributariaConfins)) return false;
		if(codigoSituacaoTributariaPis == null){
			if(other.codigoSituacaoTributariaPis != null) return false;
		}else if(!codigoSituacaoTributariaPis.equals(other.codigoSituacaoTributariaPis)) return false;
		if(codigoTabelaNaturezaReceitaConfins == null){
			if(other.codigoTabelaNaturezaReceitaConfins != null) return false;
		}else if(!codigoTabelaNaturezaReceitaConfins.equals(other.codigoTabelaNaturezaReceitaConfins)) return false;
		if(codigoTabelaNaturezaReceitaPis == null){
			if(other.codigoTabelaNaturezaReceitaPis != null) return false;
		}else if(!codigoTabelaNaturezaReceitaPis.equals(other.codigoTabelaNaturezaReceitaPis)) return false;
		if(comp_id == null){
			if(other.comp_id != null) return false;
		}else if(!comp_id.equals(other.comp_id)) return false;
		if(descricaoErroRegistro == null){
			if(other.descricaoErroRegistro != null) return false;
		}else if(!descricaoErroRegistro.equals(other.descricaoErroRegistro)) return false;
		if(percentualAlicotaConfins == null){
			if(other.percentualAlicotaConfins != null) return false;
		}else if(!percentualAlicotaConfins.equals(other.percentualAlicotaConfins)) return false;
		if(percentualAlicotaPis == null){
			if(other.percentualAlicotaPis != null) return false;
		}else if(!percentualAlicotaPis.equals(other.percentualAlicotaPis)) return false;
		if(valorBaseConfins == null){
			if(other.valorBaseConfins != null) return false;
		}else if(!valorBaseConfins.equals(other.valorBaseConfins)) return false;
		if(valorBasePis == null){
			if(other.valorBasePis != null) return false;
		}else if(!valorBasePis.equals(other.valorBasePis)) return false;
		if(valorConfins == null){
			if(other.valorConfins != null) return false;
		}else if(!valorConfins.equals(other.valorConfins)) return false;
		if(valorItem == null){
			if(other.valorItem != null) return false;
		}else if(!valorItem.equals(other.valorItem)) return false;
		if(valorPis == null){
			if(other.valorPis != null) return false;
		}else if(!valorPis.equals(other.valorPis)) return false;
		return true;
	}

	@Override
	public String toString(){

		return "TI_CM_CON_DIA_NFCL [comp_id=" + comp_id + ", codigoSituacaoTributariaPis=" + codigoSituacaoTributariaPis
						+ ", codigoSituacaoTributariaConfins=" + codigoSituacaoTributariaConfins + ", codigoContribuicaoSocialApuradaPis="
						+ codigoContribuicaoSocialApuradaPis + ", codigoContribuicaoSocialApuradaConfins="
						+ codigoContribuicaoSocialApuradaConfins + ", codigoContaPisOrigem=" + codigoContaPisOrigem
						+ ", codigoContaConfinsOrigem=" + codigoContaConfinsOrigem + ", valorItem=" + valorItem + ", valorBasePis="
						+ valorBasePis + ", percentualAlicotaPis=" + percentualAlicotaPis + ", valorPis=" + valorPis
						+ ", valorBaseConfins=" + valorBaseConfins + ", percentualAlicotaConfins=" + percentualAlicotaConfins
						+ ", valorConfins=" + valorConfins + ", descricaoErroRegistro=" + descricaoErroRegistro
						+ ", codigoTabelaNaturezaReceitaPis=" + codigoTabelaNaturezaReceitaPis + ", codigoNaturezaReceitaPis="
						+ codigoNaturezaReceitaPis + ", codigoTabelaNaturezaReceitaConfins=" + codigoTabelaNaturezaReceitaConfins
						+ ", codigoNaturezaReceitaConfins=" + codigoNaturezaReceitaConfins + "]";
	}
}
