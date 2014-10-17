/**
 * 
 */

package gcom.gui.micromedicao;

import gcom.relatorio.RelatorioBean;

/**
 * Bean responsável de pegar os parametros que serão exibidos na parte de detail
 * do relatório.
 * 
 * @author Péricles Tavares
 * @created 03/08/2011
 */
public class RelatorioAnormalidadesLeiturasBean
				implements RelatorioBean {

	private String empresa;

	private String agenteComercial;

	private String rota;

	private String descricao;

	private Integer total;

	public RelatorioAnormalidadesLeiturasBean(String empresa, String agenteComercial, String rota, String descricao, Integer total,
												Boolean imprimeAgenteComercial, Boolean imprimeRota) {

		this.empresa = empresa;
		this.agenteComercial = agenteComercial;
		this.rota = rota;
		this.descricao = descricao;
		this.total = total;
		this.imprimeAgenteComercial = imprimeAgenteComercial;
		this.imprimeRota = imprimeRota;
	}

	private Boolean imprimeAgenteComercial;

	private Boolean imprimeRota;

	public String getEmpresa(){

		return empresa;
	}

	public void setEmpresa(String empresa){

		this.empresa = empresa;
	}

	public String getAgenteComercial(){

		return agenteComercial;
	}

	public void setAgenteComercial(String agenteComercial){

		this.agenteComercial = agenteComercial;
	}

	public String getRota(){

		return rota;
	}

	public void setRota(String rota){

		this.rota = rota;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public Integer getTotal(){

		return total;
	}

	public void setTotal(Integer total){

		this.total = total;
	}

	public Boolean getImprimeAgenteComercial(){

		return imprimeAgenteComercial;
	}

	public void setImprimeAgenteComercial(Boolean imprimeAgenteComercial){

		this.imprimeAgenteComercial = imprimeAgenteComercial;
	}

	public Boolean getImprimeRota(){

		return imprimeRota;
	}

	public void setImprimeRota(Boolean imprimeRota){

		this.imprimeRota = imprimeRota;
	}

}
