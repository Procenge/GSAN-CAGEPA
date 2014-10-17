package gcom.integracao.piramide;

import java.math.BigDecimal;
import java.util.Date;

public class TabelaIntegracaoConsCanNtfisc {

	/** identifier field */
	private TabelaIntegracaoConsCanNtfiscPk comp_id;

	private BigDecimal valorCancelamento;

	private String codigoOperacaoRegistro;

	private String codigoStatusRegistro;

	private String descricaoErroRegistro;

	private boolean nova;

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];

		retorno[0] = "comp_id";

		return retorno;
	}

	/**
	 * TabelaIntegracaoConsCanNtfisc
	 * <p>
	 * Esse método Constroi uma instância de TabelaIntegracaoConsCanNtfisc.
	 * </p>
	 * 
	 * @author Marlos Ribeiro
	 * @since 15/03/2013
	 */
	public TabelaIntegracaoConsCanNtfisc() {
		super();
	}
	public TabelaIntegracaoConsCanNtfisc(TabelaIntegracaoConsCanNtfiscPk comp_id, BigDecimal valorCancelamento, String codigoOperacaoRegistro,
								String codigoStatusRegistro, String descricaoErroRegistro) {

		this();
		this.comp_id = comp_id;
		this.valorCancelamento = valorCancelamento;
		this.codigoOperacaoRegistro = codigoOperacaoRegistro;
		this.codigoStatusRegistro = codigoStatusRegistro;
		this.descricaoErroRegistro = descricaoErroRegistro;
	}

	/**
	 * TabelaIntegracaoConsCanNtfisc
	 * <p>
	 * Esse método Constroi uma instância de TabelaIntegracaoConsCanNtfisc.
	 * </p>
	 * 
	 * @param codigoFilialOrigem
	 * @param codSisOrigem
	 * @param dtCancelamento
	 * @author Marlos Ribeiro
	 * @since 21/03/2013
	 */
	public TabelaIntegracaoConsCanNtfisc(String codigoFilialOrigem, String codSisOrigem, Date dtCancelamento) {

		this();
		comp_id = new TabelaIntegracaoConsCanNtfiscPk();
		comp_id.setCodigoFilialOrigem(codigoFilialOrigem);
		comp_id.setCodigoSistemaOrigem(codSisOrigem);
		comp_id.setDataCancelamento(dtCancelamento);
	}

	public TabelaIntegracaoConsCanNtfiscPk getComp_id(){

		return comp_id;
	}

	public void setComp_id(TabelaIntegracaoConsCanNtfiscPk comp_id){

		this.comp_id = comp_id;
	}

	public BigDecimal getValorCancelamento(){

		return valorCancelamento;
	}

	public void setValorCancelamento(BigDecimal valorCancelamento){

		this.valorCancelamento = valorCancelamento;
	}

	public String getCodigoOperacaoRegistro(){

		return codigoOperacaoRegistro;
	}

	public void setCodigoOperacaoRegistro(String codigoOperacaoRegistro){

		this.codigoOperacaoRegistro = codigoOperacaoRegistro;
	}

	public String getCodigoStatusRegistro(){

		return codigoStatusRegistro;
	}

	public void setCodigoStatusRegistro(String codigoStatusRegistro){

		this.codigoStatusRegistro = codigoStatusRegistro;
	}

	public String getDescricaoErroRegistro(){

		return descricaoErroRegistro;
	}

	public void setDescricaoErroRegistro(String descricaoErroRegistro){

		this.descricaoErroRegistro = descricaoErroRegistro;
	}

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoOperacaoRegistro == null) ? 0 : codigoOperacaoRegistro.hashCode());
		result = prime * result + ((codigoStatusRegistro == null) ? 0 : codigoStatusRegistro.hashCode());
		result = prime * result + ((comp_id == null) ? 0 : comp_id.hashCode());
		result = prime * result + ((descricaoErroRegistro == null) ? 0 : descricaoErroRegistro.hashCode());
		result = prime * result + ((valorCancelamento == null) ? 0 : valorCancelamento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		TabelaIntegracaoConsCanNtfisc other = (TabelaIntegracaoConsCanNtfisc) obj;
		if(codigoOperacaoRegistro == null){
			if(other.codigoOperacaoRegistro != null) return false;
		}else if(!codigoOperacaoRegistro.equals(other.codigoOperacaoRegistro)) return false;
		if(codigoStatusRegistro == null){
			if(other.codigoStatusRegistro != null) return false;
		}else if(!codigoStatusRegistro.equals(other.codigoStatusRegistro)) return false;
		if(comp_id == null){
			if(other.comp_id != null) return false;
		}else if(!comp_id.equals(other.comp_id)) return false;
		if(descricaoErroRegistro == null){
			if(other.descricaoErroRegistro != null) return false;
		}else if(!descricaoErroRegistro.equals(other.descricaoErroRegistro)) return false;
		if(valorCancelamento == null){
			if(other.valorCancelamento != null) return false;
		}else if(!valorCancelamento.equals(other.valorCancelamento)) return false;
		return true;
	}

	@Override
	public String toString(){

		return "TI_CONS_CAN_NTFISC [comp_id=" + comp_id + ", valorCancelamento=" + valorCancelamento + ", codigoOperacaoRegistro="
						+ codigoOperacaoRegistro + ", codigoStatusRegistro=" + codigoStatusRegistro + ", descricaoErroRegistro="
						+ descricaoErroRegistro + "]";
	}

	/**
	 * Método addValorCancelamento
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param vlEsgoto
	 * @author Marlos Ribeiro
	 * @since 21/03/2013
	 */
	public void addValorCancelamento(BigDecimal valor){

		valorCancelamento = valorCancelamento.add(valor);
	}

	/**
	 * @return the nova
	 */
	public boolean isNova(){

		return nova;
	}

	/**
	 * @param nova
	 *            the nova to set
	 */
	public void setNova(boolean nova){

		this.nova = nova;
	}

	public void formatarDadosEntidade(){

		// comp_id.setCodigoFilialOrigem(Util.completarStringZeroEsquerda(comp_id.getCodigoFilialOrigem(),
		// 3));
	}
}
