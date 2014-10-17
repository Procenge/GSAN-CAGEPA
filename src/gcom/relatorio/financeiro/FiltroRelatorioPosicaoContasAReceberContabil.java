
package gcom.relatorio.financeiro;

import java.io.Serializable;

public class FiltroRelatorioPosicaoContasAReceberContabil
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String TIPO_CATEGORIA_RESIDENCIAL = "1";

	public static final String TIPO_CATEGORIA_COMERCIAL = "2";

	public static final String TIPO_CATEGORIA_INDUSTRIAL = "3";

	public static final String TIPO_CATEGORIA_PUBLICO = "4";

	public static final String TIPO_CATEGORIA_GERAL = "5";

	public static final String TIPO_CATEGORIA_PARTICULAR = "6";

	private Integer idGerenciaRegional;

	private Integer idLocalidade;

	private String indicadorTipoCategoria;

	private Integer anoMesReferencia;

	private String opcaoTotalizacao;

	public String getOpcaoTotalizacao(){

		return opcaoTotalizacao;
	}

	public void setOpcaoTotalizacao(String opcaoTotalizacao){

		this.opcaoTotalizacao = opcaoTotalizacao;
	}

	public Integer getIdGerenciaRegional(){

		return idGerenciaRegional;
	}

	public void setIdGerenciaRegional(Integer idGerenciaRegional){

		this.idGerenciaRegional = idGerenciaRegional;
	}

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getIndicadorTipoCategoria(){

		return indicadorTipoCategoria;
	}

	public void setIndicadorTipoCategoria(String indicadorTipoCategoria){

		this.indicadorTipoCategoria = indicadorTipoCategoria;
	}

	public Integer getAnoMesReferencia(){

		return anoMesReferencia;
	}

	public void setAnoMesReferencia(Integer anoMesReferencia){

		this.anoMesReferencia = anoMesReferencia;
	}

}
