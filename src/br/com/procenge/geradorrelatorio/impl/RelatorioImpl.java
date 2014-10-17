
package br.com.procenge.geradorrelatorio.impl;

import java.io.Serializable;

import br.com.procenge.geradorrelatorio.api.Relatorio;

public class RelatorioImpl
				implements Relatorio, Serializable {

	private String arquivo;

	private String classeValidacaoParametros;

	private String processoId;

	private String relatorioId;

	public RelatorioImpl() {

		super();
	}

	public RelatorioImpl(String arquivo) {

		this(arquivo, null, null);
	}

	public RelatorioImpl(String arquivo, String classeValidacaoParametros) {

		this(arquivo, classeValidacaoParametros, null);
	}

	public RelatorioImpl(String arquivo, String classeValidacaoParametros, String processoId) {

		this.arquivo = arquivo;
		this.classeValidacaoParametros = classeValidacaoParametros;
		this.processoId = processoId;
	}

	/*
	 * (non-Javadoc)
	 * @see br.com.procenge.geradorrelatorio.api.Relatorio#getArquivo()
	 */
	public String getArquivo(){

		return arquivo;
	}

	/*
	 * (non-Javadoc)
	 * @see br.com.procenge.geradorrelatorio.api.Relatorio#setArquivo(java.lang.String)
	 */
	public void setArquivo(String arquivo){

		this.arquivo = arquivo;
	}

	/*
	 * (non-Javadoc)
	 * @see br.com.procenge.geradorrelatorio.api.Relatorio#getClasseValidacaoParametros()
	 */
	public String getClasseValidacaoParametros(){

		return classeValidacaoParametros;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * br.com.procenge.geradorrelatorio.api.Relatorio#setClasseValidacaoParametros(java.lang.String)
	 */
	public void setClasseValidacaoParametros(String classeValidacaoParametros){

		this.classeValidacaoParametros = classeValidacaoParametros;
	}

	/*
	 * (non-Javadoc)
	 * @see br.com.procenge.geradorrelatorio.api.Relatorio#getProcessoId()
	 */
	public String getProcessoId(){

		return processoId;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * br.com.procenge.geradorrelatorio.api.Relatorio#setProcessoId(java.lang.String)
	 */
	public void setProcessoId(String processoId){

		this.processoId = processoId;
	}

	/*
	 * (non-Javadoc)
	 * @see br.com.procenge.geradorrelatorio.api.Relatorio#getRelatorioId()
	 */
	public String getRelatorioId(){

		return relatorioId;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * br.com.procenge.geradorrelatorio.api.Relatorio#setRelatorioId(java.lang.String)
	 */
	public void setRelatorioId(String relatorioId){

		this.relatorioId = relatorioId;
	}

	public String getNomeArquivo(){

		String nomeArquivo = getArquivo();
		nomeArquivo = nomeArquivo.replace("crystal/", "");
		nomeArquivo = nomeArquivo.replace(".rpt", ".pdf");

		return nomeArquivo;
	}
}
