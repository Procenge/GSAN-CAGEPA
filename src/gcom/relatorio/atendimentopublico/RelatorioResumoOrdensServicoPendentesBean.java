
package gcom.relatorio.atendimentopublico;

import gcom.relatorio.RelatorioBean;

/**
 * [UC1015] Relatório Resumo de Ordens de Serviço Pendentes
 * 
 * @author Anderson Italo
 * @date 06/06/2011
 */
public class RelatorioResumoOrdensServicoPendentesBean
				implements RelatorioBean {

	private String codigoLocalidade;

	private String nomeLocalidade;

	private String codigoGerencia;

	private String nomeGerencia;

	private String codigoSetorComercial;

	private String nomeSetorComercial;

	private String codigoTipoServico;

	private String descricaoTipoServico;

	private Integer totalRegistros;

	private String inscricao;

	private String dataEmissao;

	private String ordemServico;

	private String endereco;

	private String numeroHidrometro;

	private String marcaHidrometro;

	private String tipoHidrometro;

	private String dataInstalacaoHidrometro;

	private String localInstalacaoHidrometro;

	private String capacidadeHidrometro;

	private String tipoProtecaoHidrometro;

	private String diametroHidrometro;

	private String cavaleteHidrometro;

	private String categoria;

	private String anoFabricacaoHidrometro;

	private String nomeUnidadeNegocio;

	private String codigoUnidadeNegocio;

	public String getCodigoLocalidade(){

		return codigoLocalidade;
	}

	public void setCodigoLocalidade(String codigoLocalidade){

		this.codigoLocalidade = codigoLocalidade;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public String getCodigoGerencia(){

		return codigoGerencia;
	}

	public void setCodigoGerencia(String codigoGerencia){

		this.codigoGerencia = codigoGerencia;
	}

	public String getNomeGerencia(){

		return nomeGerencia;
	}

	public void setNomeGerencia(String nomeGerencia){

		this.nomeGerencia = nomeGerencia;
	}

	public Integer getTotalRegistros(){

		return totalRegistros;
	}

	public void setTotalRegistros(Integer totalRegistros){

		this.totalRegistros = totalRegistros;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public String getDataEmissao(){

		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao){

		this.dataEmissao = dataEmissao;
	}

	public String getOrdemServico(){

		return ordemServico;
	}

	public void setOrdemServico(String ordemServico){

		this.ordemServico = ordemServico;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	public String getMarcaHidrometro(){

		return marcaHidrometro;
	}

	public void setMarcaHidrometro(String marcaHidrometro){

		this.marcaHidrometro = marcaHidrometro;
	}

	public String getTipoHidrometro(){

		return tipoHidrometro;
	}

	public void setTipoHidrometro(String tipoHidrometro){

		this.tipoHidrometro = tipoHidrometro;
	}

	public String getDataInstalacaoHidrometro(){

		return dataInstalacaoHidrometro;
	}

	public void setDataInstalacaoHidrometro(String dataInstalacaoHidrometro){

		this.dataInstalacaoHidrometro = dataInstalacaoHidrometro;
	}

	public String getLocalInstalacaoHidrometro(){

		return localInstalacaoHidrometro;
	}

	public void setLocalInstalacaoHidrometro(String localInstalacaoHidrometro){

		this.localInstalacaoHidrometro = localInstalacaoHidrometro;
	}

	public String getCapacidadeHidrometro(){

		return capacidadeHidrometro;
	}

	public void setCapacidadeHidrometro(String capacidadeHidrometro){

		this.capacidadeHidrometro = capacidadeHidrometro;
	}

	public String getTipoProtecaoHidrometro(){

		return tipoProtecaoHidrometro;
	}

	public void setTipoProtecaoHidrometro(String tipoProtecaoHidrometro){

		this.tipoProtecaoHidrometro = tipoProtecaoHidrometro;
	}

	public String getDiametroHidrometro(){

		return diametroHidrometro;
	}

	public void setDiametroHidrometro(String diametroHidrometro){

		this.diametroHidrometro = diametroHidrometro;
	}

	public String getCavaleteHidrometro(){

		return cavaleteHidrometro;
	}

	public void setCavaleteHidrometro(String cavaleteHidrometro){

		this.cavaleteHidrometro = cavaleteHidrometro;
	}

	public String getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(String codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public String getNomeSetorComercial(){

		return nomeSetorComercial;
	}

	public void setNomeSetorComercial(String nomeSetorComercial){

		this.nomeSetorComercial = nomeSetorComercial;
	}

	public String getCodigoTipoServico(){

		return codigoTipoServico;
	}

	public void setCodigoTipoServico(String codigoTipoServico){

		this.codigoTipoServico = codigoTipoServico;
	}

	public String getDescricaoTipoServico(){

		return descricaoTipoServico;
	}

	public void setDescricaoTipoServico(String descricaoTipoServico){

		this.descricaoTipoServico = descricaoTipoServico;
	}

	public String getCategoria(){

		return categoria;
	}

	public void setCategoria(String categoria){

		this.categoria = categoria;
	}

	public String getAnoFabricacaoHidrometro(){

		return anoFabricacaoHidrometro;
	}

	public void setAnoFabricacaoHidrometro(String anoFabricacaoHidrometro){

		this.anoFabricacaoHidrometro = anoFabricacaoHidrometro;
	}

	public String getNomeUnidadeNegocio(){

		return nomeUnidadeNegocio;
	}

	public void setNomeUnidadeNegocio(String nomeUnidadeNegocio){

		this.nomeUnidadeNegocio = nomeUnidadeNegocio;
	}

	public String getCodigoUnidadeNegocio(){

		return codigoUnidadeNegocio;
	}

	public void setCodigoUnidadeNegocio(String codigoUnidadeNegocio){

		this.codigoUnidadeNegocio = codigoUnidadeNegocio;
	}

}
