/**
 * 
 */

package gcom.micromedicao;

import gcom.relatorio.RelatorioBean;

/**
 * Bean responsável de pegar os parametros que serão exibidos na parte de detail
 * do relatório.
 * 
 * @author Péricles Tavares
 * @created 16/08/2011
 */
public class RelatorioProcessarArquivoInformarAnormalidadesLeituraBean
				implements RelatorioBean {

	private String faturamentoGrupo;

	private String empresa;

	private String localidade;

	private String sequencial;

	private String listaProblemas;

	private Integer total;

	private Integer totalSemProblema;

	private Integer totalComProblema;

	private String tipo;

	public RelatorioProcessarArquivoInformarAnormalidadesLeituraBean(String faturamentoGrupo, String empresa, String localidade,
																		String sequencial, String listaProblemas, Integer total,
																		Integer totalSemProblema, Integer totalComProblema, String tipo) {

		this.faturamentoGrupo = faturamentoGrupo;
		this.empresa = empresa;
		this.localidade = localidade;
		this.sequencial = sequencial;
		this.listaProblemas = listaProblemas;
		this.total = total;
		this.totalSemProblema = totalSemProblema;
		this.totalComProblema = totalComProblema;
		this.tipo = tipo;
	}

	public String getFaturamentoGrupo(){

		return faturamentoGrupo;
	}

	public void setFaturamentoGrupo(String faturamentoGrupo){

		this.faturamentoGrupo = faturamentoGrupo;
	}

	public String getEmpresa(){

		return empresa;
	}

	public void setEmpresa(String empresa){

		this.empresa = empresa;
	}

	public String getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

	public String getSequencial(){

		return sequencial;
	}

	public void setSequencial(String sequencial){

		this.sequencial = sequencial;
	}

	public String getListaProblemas(){

		return listaProblemas;
	}

	public void setListaProblemas(String listaProblemas){

		this.listaProblemas = listaProblemas;
	}

	public Integer getTotal(){

		return total;
	}

	public void setTotal(Integer total){

		this.total = total;
	}

	public Integer getTotalSemProblema(){

		return totalSemProblema;
	}

	public void setTotalSemProblema(Integer totalSemProblema){

		this.totalSemProblema = totalSemProblema;
	}

	public Integer getTotalComProblema(){

		return totalComProblema;
	}

	public void setTotalComProblema(Integer totalComProblema){

		this.totalComProblema = totalComProblema;
	}

	public String getTipo(){

		return tipo;
	}

	public void setTipo(String tipo){

		this.tipo = tipo;
	}

}
