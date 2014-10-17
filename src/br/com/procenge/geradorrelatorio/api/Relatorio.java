
package br.com.procenge.geradorrelatorio.api;

public interface Relatorio {

	String BEAN_ID = "relatorio";

	String getArquivo();

	void setArquivo(String arquivo);

	String getClasseValidacaoParametros();

	void setClasseValidacaoParametros(String classe);

	String getProcessoId();

	void setProcessoId(String processoId);

	String getRelatorioId();

	void setRelatorioId(String relatorioId);

	String getNomeArquivo();
}