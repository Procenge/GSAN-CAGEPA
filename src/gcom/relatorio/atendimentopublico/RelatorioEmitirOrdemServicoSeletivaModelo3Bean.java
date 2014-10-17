
package gcom.relatorio.atendimentopublico;

import gcom.relatorio.RelatorioBean;

/**
 * Bean do [UC0713] Emitir Ordem de Servico Seletiva - Modelo 2
 * 
 * @author Hugo Lima
 * @date 16/03/2012
 */
public class RelatorioEmitirOrdemServicoSeletivaModelo3Bean
				implements RelatorioBean {

	private String descricaoTipoServico;

	private String idLocalidade;

	private String nomeLocalidade;

	private String idOrdemServico;

	private String matriculaImovel;

	private String inscricao;

	private String codigoSetorComercial;

	private String quadra;

	private String lote;

	private String subLote;

	private String categoria;

	private String quantidadeEconomias;

	private String situacaoEsgoto;

	private String situacaoAgua;

	private String numeroHidrometro;

	private String capacidade;

	private String dataInstalacao;

	private String media;

	private String anormalidadeLeitura;

	private String leitura;

	private String consumo;

	private String variacao;

	private String consumoMes;

	private String consumoMedioImovel;

	public RelatorioEmitirOrdemServicoSeletivaModelo3Bean() {

	}

	public String getDescricaoTipoServico(){

		return descricaoTipoServico;
	}

	public void setDescricaoTipoServico(String descricaoTipoServico){

		this.descricaoTipoServico = descricaoTipoServico;
	}

	public String getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public String getIdOrdemServico(){

		return idOrdemServico;
	}

	public void setIdOrdemServico(String idOrdemServico){

		this.idOrdemServico = idOrdemServico;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public String getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(String codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public String getQuadra(){

		return quadra;
	}

	public void setQuadra(String quadra){

		this.quadra = quadra;
	}

	public String getLote(){

		return lote;
	}

	public void setLote(String lote){

		this.lote = lote;
	}

	public String getSubLote(){

		return subLote;
	}

	public void setSubLote(String subLote){

		this.subLote = subLote;
	}

	public String getCategoria(){

		return categoria;
	}

	public void setCategoria(String categoria){

		this.categoria = categoria;
	}

	public String getQuantidadeEconomias(){

		return quantidadeEconomias;
	}

	public void setQuantidadeEconomias(String quantidadeEconomias){

		this.quantidadeEconomias = quantidadeEconomias;
	}

	public String getSituacaoEsgoto(){

		return situacaoEsgoto;
	}

	public void setSituacaoEsgoto(String situacaoEsgoto){

		this.situacaoEsgoto = situacaoEsgoto;
	}

	public String getSituacaoAgua(){

		return situacaoAgua;
	}

	public void setSituacaoAgua(String situacaoAgua){

		this.situacaoAgua = situacaoAgua;
	}

	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	public String getCapacidade(){

		return capacidade;
	}

	public void setCapacidade(String capacidade){

		this.capacidade = capacidade;
	}

	public String getDataInstalacao(){

		return dataInstalacao;
	}

	public void setDataInstalacao(String dataInstalacao){

		this.dataInstalacao = dataInstalacao;
	}

	public String getMedia(){

		return media;
	}

	public void setMedia(String media){

		this.media = media;
	}

	public String getAnormalidadeLeitura(){

		return anormalidadeLeitura;
	}

	public void setAnormalidadeLeitura(String anormalidadeLeitura){

		this.anormalidadeLeitura = anormalidadeLeitura;
	}

	public String getLeitura(){

		return leitura;
	}

	public void setLeitura(String leitura){

		this.leitura = leitura;
	}

	public String getConsumo(){

		return consumo;
	}

	public void setConsumo(String consumo){

		this.consumo = consumo;
	}

	public String getVariacao(){

		return variacao;
	}

	public void setVariacao(String variacao){

		this.variacao = variacao;
	}

	public String getConsumoMes(){

		return consumoMes;
	}

	public void setConsumoMes(String consumoMes){

		this.consumoMes = consumoMes;
	}

	public String getConsumoMedioImovel(){

		return consumoMedioImovel;
	}

	public void setConsumoMedioImovel(String consumoMedioImovel){

		this.consumoMedioImovel = consumoMedioImovel;
	}

	public String getInscricaoFormatada(){

		return this.idLocalidade + "." + this.codigoSetorComercial + "." + this.quadra + "." + this.lote + "." + this.subLote;
	}

}