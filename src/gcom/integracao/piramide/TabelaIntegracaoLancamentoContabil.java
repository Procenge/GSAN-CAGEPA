package gcom.integracao.piramide;

import java.math.BigDecimal;


public class TabelaIntegracaoLancamentoContabil {
	
	public static String CODIGO_HISTORICO_ORIGEM = "I001";

	public static String CODIGO_OPERACAO_DEBITO = "D";

	public static String CODIGO_OPERACAO_CREDITO = "C";

	/** identifier field */
	private TabelaIntegracaoLancamentoContabilPK comp_id;
	
	private String codigoContaOrigem;
	
	private String codigoOperacao;

	private BigDecimal valorLancamento;
	
	private String codigoUnidadeOrigem;
	
	private String codigoCcustoOrigem;
	
	private String codigoHistoricoOrigem;
	
	private String codigoContaAuxiliarOrigem;
	
	private String codigoGrupoContaAuxiliarOrigem;
	
	private String descricaoComplemento;

	public TabelaIntegracaoLancamentoContabil() {

	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];

		retorno[0] = "comp_id";

		return retorno;
	}

	public TabelaIntegracaoLancamentoContabilPK getComp_id(){

		return comp_id;
	}

	public void setComp_id(TabelaIntegracaoLancamentoContabilPK comp_id){

		this.comp_id = comp_id;
	}

	public String getCodigoContaOrigem(){

		return codigoContaOrigem;
	}

	public void setCodigoContaOrigem(String codigoContaOrigem){

		this.codigoContaOrigem = codigoContaOrigem;
	}

	public String getCodigoOperacao(){

		return codigoOperacao;
	}

	public void setCodigoOperacao(String codigoOperacao){

		this.codigoOperacao = codigoOperacao;
	}

	public BigDecimal getValorLancamento(){

		return valorLancamento;
	}

	public void setValorLancamento(BigDecimal valorLancamento){

		this.valorLancamento = valorLancamento;
	}

	public String getCodigoUnidadeOrigem(){

		return codigoUnidadeOrigem;
	}

	public void setCodigoUnidadeOrigem(String codigoUnidadeOrigem){

		this.codigoUnidadeOrigem = codigoUnidadeOrigem;
	}

	public String getCodigoCcustoOrigem(){

		return codigoCcustoOrigem;
	}

	public void setCodigoCcustoOrigem(String codigoCcustoOrigem){

		this.codigoCcustoOrigem = codigoCcustoOrigem;
	}

	public String getCodigoHistoricoOrigem(){

		return codigoHistoricoOrigem;
	}

	public void setCodigoHistoricoOrigem(String codigoHistoricoOrigem){

		this.codigoHistoricoOrigem = codigoHistoricoOrigem;
	}

	public String getCodigoContaAuxiliarOrigem(){

		return codigoContaAuxiliarOrigem;
	}

	public void setCodigoContaAuxiliarOrigem(String codigoContaAuxiliarOrigem){

		this.codigoContaAuxiliarOrigem = codigoContaAuxiliarOrigem;
	}

	public String getCodigoGrupoContaAuxiliarOrigem(){

		return codigoGrupoContaAuxiliarOrigem;
	}

	public void setCodigoGrupoContaAuxiliarOrigem(String codigoGrupoContaAuxiliarOrigem){

		this.codigoGrupoContaAuxiliarOrigem = codigoGrupoContaAuxiliarOrigem;
	}

	public String getDescricaoComplemento(){

		return descricaoComplemento;
	}

	public void setDescricaoComplemento(String descricaoComplemento){

		this.descricaoComplemento = descricaoComplemento;
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
		TabelaIntegracaoLancamentoContabil other = (TabelaIntegracaoLancamentoContabil) obj;
		if(comp_id == null){
			if(other.comp_id != null) return false;
		}else if(!comp_id.equals(other.comp_id)) return false;
		return true;
	}

}
