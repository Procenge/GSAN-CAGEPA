package gcom.faturamento.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

public class EmitirHistogramaAguaEsgotoEconomiaHelper
				implements Serializable {

	private String descricaoTarifa = null;

	private String opcaoTotalizacao = null;

	private String descricaoPercentual = null;

	private String descricaoFaixa = null;

	private String descricaoCategoria = null;

	// Parte com Hidrometro
	private Long totalEconomiasMedido = new Long("0");

	private Long totalVolumeConsumoMedido = new Long("0");

	private Long totalVolumeFaturadoMedido = new Long("0");

	private BigDecimal totalReceitaMedido = null;

	private Long totalLigacoesMedido = new Long("0");

	// Parte sem Hidrometro
	private Long totalEconomiasNaoMedido = new Long("0");

	private Long totalVolumeConsumoNaoMedido = new Long("0");

	private Long totalVolumeFaturadoNaoMedido = new Long("0");

	private BigDecimal totalReceitaNaoMedido = null;

	private Long totalLigacoesNaoMedido = new Long("0");

	private Integer idGerenciaRegional;

	private Integer idLocalidade;

	private Integer idLocalidadeVinculada;

	private Integer idCategoria;

	private Boolean exibirNoArquivoTxt;

	private short percentualEsgoto = 0;

	private Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe = null;

	public String getDescricaoTarifa(){

		return descricaoTarifa;
	}

	public void setDescricaoTarifa(String descricaoTarifa){

		this.descricaoTarifa = descricaoTarifa;
	}

	public String getOpcaoTotalizacao(){

		return opcaoTotalizacao;
	}

	public void setOpcaoTotalizacao(String opcaoTotalizacao){

		this.opcaoTotalizacao = opcaoTotalizacao;
	}

	public String getDescricaoFaixa(){

		return descricaoFaixa;
	}

	public void setDescricaoFaixa(String descricaoFaixa){

		this.descricaoFaixa = descricaoFaixa;
	}

	public String getDescricaoCategoria(){

		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria){

		this.descricaoCategoria = descricaoCategoria;
	}

	public Long getTotalEconomiasMedido(){

		return totalEconomiasMedido;
	}

	public void setTotalEconomiasMedido(Long totalEconomiasMedido){

		this.totalEconomiasMedido = totalEconomiasMedido;
	}

	public Long getTotalVolumeConsumoMedido(){

		return totalVolumeConsumoMedido;
	}

	public void setTotalVolumeConsumoMedido(Long totalVolumeConsumoMedido){

		this.totalVolumeConsumoMedido = totalVolumeConsumoMedido;
	}

	public Long getTotalVolumeFaturadoMedido(){

		return totalVolumeFaturadoMedido;
	}

	public void setTotalVolumeFaturadoMedido(Long totalVolumeFaturadoMedido){

		this.totalVolumeFaturadoMedido = totalVolumeFaturadoMedido;
	}

	public BigDecimal getTotalReceitaMedido(){

		return totalReceitaMedido;
	}

	public void setTotalReceitaMedido(BigDecimal totalReceitaMedido){

		this.totalReceitaMedido = totalReceitaMedido;
	}

	public Long getTotalLigacoesMedido(){

		return totalLigacoesMedido;
	}

	public void setTotalLigacoesMedido(Long totalLigacoesMedido){

		this.totalLigacoesMedido = totalLigacoesMedido;
	}

	public Long getTotalEconomiasNaoMedido(){

		return totalEconomiasNaoMedido;
	}

	public void setTotalEconomiasNaoMedido(Long totalEconomiasNaoMedido){

		this.totalEconomiasNaoMedido = totalEconomiasNaoMedido;
	}

	public Long getTotalVolumeConsumoNaoMedido(){

		return totalVolumeConsumoNaoMedido;
	}

	public void setTotalVolumeConsumoNaoMedido(Long totalVolumeConsumoNaoMedido){

		this.totalVolumeConsumoNaoMedido = totalVolumeConsumoNaoMedido;
	}

	public Long getTotalVolumeFaturadoNaoMedido(){

		return totalVolumeFaturadoNaoMedido;
	}

	public void setTotalVolumeFaturadoNaoMedido(Long totalVolumeFaturadoNaoMedido){

		this.totalVolumeFaturadoNaoMedido = totalVolumeFaturadoNaoMedido;
	}

	public BigDecimal getTotalReceitaNaoMedido(){

		return totalReceitaNaoMedido;
	}

	public void setTotalReceitaNaoMedido(BigDecimal totalReceitaNaoMedido){

		this.totalReceitaNaoMedido = totalReceitaNaoMedido;
	}

	public Long getTotalLigacoesNaoMedido(){

		return totalLigacoesNaoMedido;
	}

	public void setTotalLigacoesNaoMedido(Long totalLigacoesNaoMedido){

		this.totalLigacoesNaoMedido = totalLigacoesNaoMedido;
	}

	public String getDescricaoPercentual(){

		return descricaoPercentual;
	}

	public void setDescricaoPercentual(String descricaoPercentual){

		this.descricaoPercentual = descricaoPercentual;
	}

	public Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> getColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(){

		return colecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe;
	}

	public void setColecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe(
					Collection<EmitirHistogramaAguaEsgotoEconomiaDetalheHelper> colecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe){

		this.colecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe = colecaoEmitirHistogramaAguaEsgotoEconomiaDetalhe;
	}

	public Integer getIdGerenciaRegional(){

		return idGerenciaRegional;
	}

	public void setIdGerenciaRegional(Integer idGerenciaRegional){

		this.idGerenciaRegional = idGerenciaRegional;
	}

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public Integer getIdLocalidadeVinculada(){

		return idLocalidadeVinculada;
	}

	public void setIdLocalidadeVinculada(Integer idLocalidadeVinculada){

		this.idLocalidadeVinculada = idLocalidadeVinculada;
	}

	public Integer getIdCategoria(){

		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria){

		this.idCategoria = idCategoria;
	}

	public Boolean getExibirNoArquivoTxt(){

		return exibirNoArquivoTxt;
	}

	public void setExibirNoArquivoTxt(Boolean exibirNoArquivoTxt){

		this.exibirNoArquivoTxt = exibirNoArquivoTxt;
	}

	public short getPercentualEsgoto(){

		return percentualEsgoto;
	}

	public void setPercentualEsgoto(short percentualEsgoto){

		this.percentualEsgoto = percentualEsgoto;
	}

}
