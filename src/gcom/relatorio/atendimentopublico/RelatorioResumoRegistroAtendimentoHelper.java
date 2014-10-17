
package gcom.relatorio.atendimentopublico;

public class RelatorioResumoRegistroAtendimentoHelper {

	private String idTipoSolicitacao;

	private String quantidadePorTipo;

	private String idUnidadeAtendimento;

	public RelatorioResumoRegistroAtendimentoHelper() {

	}

	public String getIdTipoSolicitacao(){

		return idTipoSolicitacao;
	}

	public void setIdTipoSolicitacao(String idTipoSolicitacao){

		this.idTipoSolicitacao = idTipoSolicitacao;
	}

	public String getIdUnidadeAtendimento(){

		return idUnidadeAtendimento;
	}

	public void setIdUnidadeAtendimento(String idUnidadeAtendimento){

		this.idUnidadeAtendimento = idUnidadeAtendimento;
	}

	public String getQuantidadePorTipo(){

		return quantidadePorTipo;
	}

	public void setQuantidadePorTipo(String quantidadePorTipo){

		this.quantidadePorTipo = quantidadePorTipo;
	}

}
