package gcom.integracao.piramide;

import java.util.Date;


public class TabelaIntegracaoGrupoContabil {

	public static String CODIGO_TIPO_LANCAMENTO_CONTABIL = "C";

	public static String CODIGO_OPERACAO_REGISTRO = "I";

	public static String CODIGO_STATUS_REGISTRO = "NP";

	private Integer numeroIdLancamento;

	private String codigoEmpresaOrigem;

	private String codigoFilialOrigem;

	private String codigoTipoLancamento;

	private Date dataLancamento;

	private String codigoDocumento;

	private String codigoArquivo;

	private String codigoOperacaoRegistro;

	private String codigoStatusRegistro;

	public TabelaIntegracaoGrupoContabil() {

	}

	public Integer getNumeroIdLancamento(){

		return numeroIdLancamento;
	}

	public void setNumeroIdLancamento(Integer numeroIdLancamento){

		this.numeroIdLancamento = numeroIdLancamento;
	}

	public String getCodigoEmpresaOrigem(){

		return codigoEmpresaOrigem;
	}

	public void setCodigoEmpresaOrigem(String codigoEmpresaOrigem){

		this.codigoEmpresaOrigem = codigoEmpresaOrigem;
	}

	public String getCodigoFilialOrigem(){

		return codigoFilialOrigem;
	}

	public void setCodigoFilialOrigem(String codigoFilialOrigem){

		this.codigoFilialOrigem = codigoFilialOrigem;
	}

	public String getCodigoTipoLancamento(){

		return codigoTipoLancamento;
	}

	public void setCodigoTipoLancamento(String codigoTipoLancamento){

		this.codigoTipoLancamento = codigoTipoLancamento;
	}

	public Date getDataLancamento(){

		return dataLancamento;
	}

	public void setDataLancamento(Date dataLancamento){

		this.dataLancamento = dataLancamento;
	}

	public String getCodigoDocumento(){

		return codigoDocumento;
	}

	public void setCodigoDocumento(String codigoDocumento){

		this.codigoDocumento = codigoDocumento;
	}

	public String getCodigoArquivo(){

		return codigoArquivo;
	}

	public void setCodigoArquivo(String codigoArquivo){

		this.codigoArquivo = codigoArquivo;
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

}
