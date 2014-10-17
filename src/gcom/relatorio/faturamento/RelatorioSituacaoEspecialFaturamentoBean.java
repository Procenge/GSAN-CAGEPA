
package gcom.relatorio.faturamento;

import gcom.relatorio.RelatorioBean;

/**
 * [XYZ] Gerar Relatório Situação Especial de Faturamento
 * 
 * @author Hebert Falcão
 * @date 16/03/2014
 */
public class RelatorioSituacaoEspecialFaturamentoBean
				implements RelatorioBean {

	private String idFaturamentoSituacaoTipo;

	private String descricaoFaturamentoSituacaoTipo;

	private String idFaturamentoSituacaoMotivo;

	private String descricaoFaturamentoSituacaoMotivo;

	private String anoMesFaturamentoSituacaoInicio;

	private String anoMesFaturamentoSituacaoFim;

	private String idLocalidade;

	private String descricaoLocalidade;

	private String codigoSetorComercial;

	private String descricaoSetorComercial;

	private String inscricao;

	private String codigoRota;

	private String idImovel;

	private String nomeCliente;

	private String endereco;


	public String getIdFaturamentoSituacaoTipo(){

		return idFaturamentoSituacaoTipo;
	}


	public void setIdFaturamentoSituacaoTipo(String idFaturamentoSituacaoTipo){

		this.idFaturamentoSituacaoTipo = idFaturamentoSituacaoTipo;
	}


	public String getDescricaoFaturamentoSituacaoTipo(){

		return descricaoFaturamentoSituacaoTipo;
	}


	public void setDescricaoFaturamentoSituacaoTipo(String descricaoFaturamentoSituacaoTipo){

		this.descricaoFaturamentoSituacaoTipo = descricaoFaturamentoSituacaoTipo;
	}


	public String getIdFaturamentoSituacaoMotivo(){

		return idFaturamentoSituacaoMotivo;
	}


	public void setIdFaturamentoSituacaoMotivo(String idFaturamentoSituacaoMotivo){

		this.idFaturamentoSituacaoMotivo = idFaturamentoSituacaoMotivo;
	}


	public String getDescricaoFaturamentoSituacaoMotivo(){

		return descricaoFaturamentoSituacaoMotivo;
	}


	public void setDescricaoFaturamentoSituacaoMotivo(String descricaoFaturamentoSituacaoMotivo){

		this.descricaoFaturamentoSituacaoMotivo = descricaoFaturamentoSituacaoMotivo;
	}


	public String getAnoMesFaturamentoSituacaoInicio(){

		return anoMesFaturamentoSituacaoInicio;
	}


	public void setAnoMesFaturamentoSituacaoInicio(String anoMesFaturamentoSituacaoInicio){

		this.anoMesFaturamentoSituacaoInicio = anoMesFaturamentoSituacaoInicio;
	}


	public String getAnoMesFaturamentoSituacaoFim(){

		return anoMesFaturamentoSituacaoFim;
	}


	public void setAnoMesFaturamentoSituacaoFim(String anoMesFaturamentoSituacaoFim){

		this.anoMesFaturamentoSituacaoFim = anoMesFaturamentoSituacaoFim;
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


	public String getInscricao(){

		return inscricao;
	}


	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}


	public String getCodigoRota(){

		return codigoRota;
	}


	public void setCodigoRota(String codigoRota){

		this.codigoRota = codigoRota;
	}


	public String getIdImovel(){

		return idImovel;
	}


	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}


	public String getEndereco(){

		return endereco;
	}


	public void setEndereco(String endereco){

		this.endereco = endereco;
	}


}
