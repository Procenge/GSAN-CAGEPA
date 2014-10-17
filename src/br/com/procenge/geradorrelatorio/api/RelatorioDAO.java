
package br.com.procenge.geradorrelatorio.api;

public interface RelatorioDAO {

	String BEAN_ID = "relatorioDAO";

	Relatorio obterRelatorioPorNome(String nome);
}
