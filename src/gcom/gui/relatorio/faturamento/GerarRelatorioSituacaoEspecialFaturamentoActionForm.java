
package gcom.gui.relatorio.faturamento;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * [XYZ] Gerar Relatório Situação Especial de Faturamento
 * 
 * @author Hebert Falcão
 * @date 16/03/2014
 */
public class GerarRelatorioSituacaoEspecialFaturamentoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String mesAnoFaturamento;

	private String idLocalidade;

	private String descricaoLocalidade;

	private String codigoSetorComercial;

	private String descricaoSetorComercial;

	private String numeroQuadra;

	private String codigoRota;

	private String descricaoRota;

	private String idFaturamentoSituacaoTipo;

	private String idFaturamentoSituacaoMotivo;

	public String getMesAnoFaturamento(){

		return mesAnoFaturamento;
	}

	public void setMesAnoFaturamento(String mesAnoFaturamento){

		this.mesAnoFaturamento = mesAnoFaturamento;
	}

	public String getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	public String getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(String codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public String getDescricaoSetorComercial(){

		return descricaoSetorComercial;
	}

	public void setDescricaoSetorComercial(String descricaoSetorComercial){

		this.descricaoSetorComercial = descricaoSetorComercial;
	}

	public String getNumeroQuadra(){

		return numeroQuadra;
	}

	public void setNumeroQuadra(String numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	public String getCodigoRota(){

		return codigoRota;
	}

	public void setCodigoRota(String codigoRota){

		this.codigoRota = codigoRota;
	}

	public String getIdFaturamentoSituacaoTipo(){

		return idFaturamentoSituacaoTipo;
	}

	public void setIdFaturamentoSituacaoTipo(String idFaturamentoSituacaoTipo){

		this.idFaturamentoSituacaoTipo = idFaturamentoSituacaoTipo;
	}

	public String getIdFaturamentoSituacaoMotivo(){

		return idFaturamentoSituacaoMotivo;
	}

	public void setIdFaturamentoSituacaoMotivo(String idFaturamentoSituacaoMotivo){

		this.idFaturamentoSituacaoMotivo = idFaturamentoSituacaoMotivo;
	}

	public String getDescricaoRota(){

		return descricaoRota;
	}

	public void setDescricaoRota(String descricaoRota){

		this.descricaoRota = descricaoRota;
	}

}
