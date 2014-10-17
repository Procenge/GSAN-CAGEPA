
package gcom.relatorio.faturamento;

import gcom.relatorio.RelatorioBean;

/**
 * Relatório de Ocorrência da Geração do Pré-Faturamento
 * 
 * @author Hebert Falcão
 * @date 06/04/2012
 */
public class RelatorioOcorrenciaGeracaoPreFaturamentoBean
				implements RelatorioBean {

	private String localidade;

	private String categoria;

	private String endereco;

	private String inscricao;

	private String matricula;

	private String mensagem;

	private String situacaoAgua;

	private String situacaoEsgoto;

	public String getCategoria(){

		return categoria;
	}

	public void setCategoria(String categoria){

		this.categoria = categoria;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public String getMensagem(){

		return mensagem;
	}

	public void setMensagem(String mensagem){

		this.mensagem = mensagem;
	}

	public String getSituacaoAgua(){

		return situacaoAgua;
	}

	public void setSituacaoAgua(String situacaoAgua){

		this.situacaoAgua = situacaoAgua;
	}

	public String getSituacaoEsgoto(){

		return situacaoEsgoto;
	}

	public void setSituacaoEsgoto(String situacaoEsgoto){

		this.situacaoEsgoto = situacaoEsgoto;
	}

	public String getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

}
