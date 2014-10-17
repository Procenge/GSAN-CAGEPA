
package gcom.gerencial.bean;


public class FiltroQuadroComparativoFaturamentoArrecadacaoHelper {

	public static final int OPCAO_TOTALIZACAO_ESTADO = 1;

	public static final int OPCAO_TOTALIZACAO_GERENCIA_REGIONAL = 2;

	public static final int OPCAO_TOTALIZACAO_LOCALIDADE = 3;

	private int opcaoTotalizacao;

	private Integer anoMesReferencia;

	private Integer idLocalidade;

	private String descricaoLocalidade;

	private Integer idGerenciaRegional;

	private String nomeGerenciaRegional;

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public Integer getAnoMesReferencia(){

		return anoMesReferencia;
	}

	public void setAnoMesReferencia(Integer anoMesReferencia){

		this.anoMesReferencia = anoMesReferencia;
	}

	public Integer getIdGerenciaRegional(){

		return idGerenciaRegional;
	}

	public void setIdGerenciaRegional(Integer idGerenciaRegional){

		this.idGerenciaRegional = idGerenciaRegional;
	}

	public int getOpcaoTotalizacao(){

		return opcaoTotalizacao;
	}

	public String getDescricaoOpcaoTotalizacao(){
		switch(opcaoTotalizacao){
			case OPCAO_TOTALIZACAO_ESTADO:
				return "ESTADO";
			case OPCAO_TOTALIZACAO_GERENCIA_REGIONAL:
				return "GERÊNCIA REGIONAL";
			case OPCAO_TOTALIZACAO_LOCALIDADE:
				return "LOCALIDADE";
			default:
				return "";
		}
	}

	public void setOpcaoTotalizacao(int opcaoTotalizacao){

		this.opcaoTotalizacao = opcaoTotalizacao;
	}

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	public String getNomeGerenciaRegional(){

		return nomeGerenciaRegional;
	}

	public void setNomeGerenciaRegional(String nomeGerenciaRegional){

		this.nomeGerenciaRegional = nomeGerenciaRegional;
	}

}
