
package gcom.relatorio.atendimentopublico;

import gcom.relatorio.RelatorioBean;

/**
 * Bean do [UC0713] Emitir Ordem de Servico Seletiva - Modelo 5
 * 
 * @author Eduardo Oliveira
 * @date 08/11/2013
 */
public class RelatorioEmitirOrdemServicoSeletivaModelo5Bean
				implements RelatorioBean {

	// 1° página
	private String descricaoTipoServico;

	private String idLocalidade;

	private String nomeLocalidade;

	private String dataGeracao;

	private String idOrdemServico;

	private String matriculaImovel;

	private String inscricao;

	private String codigoSetorComercial;

	private String catSubCatQtdeEconomias;

	private String situacaoEsgoto;

	private String situacaoAgua;

	private String nomeCliente;

	private String endereco;

	private Integer numeroPagina;

	// 2° página
	private String descricaoTipoServico_1;

	private String idLocalidade_1;

	private String nomeLocalidade_1;

	private String dataGeracao_1;

	private String idOrdemServico_1;

	private String matriculaImovel_1;

	private String inscricao_1;

	private String codigoSetorComercial_1;

	private String catSubCatQtdeEconomias_1;

	private String situacaoEsgoto_1;

	private String situacaoAgua_1;

	private String nomeCliente_1;

	private String endereco_1;

	private Integer numeroPagina_1;

	public RelatorioEmitirOrdemServicoSeletivaModelo5Bean(String descricaoTipoServico, String idLocalidade, String nomeLocalidade,
															String dataGeracao, String idOrdemServico, String matriculaImovel,
															String inscricao, String codigoSetorComercial, String catSubCatQtdeEconomias,
															String situacaoEsgoto, String situacaoAgua, String nomeCliente,
															String endereco, Integer numeroPagina, String descricaoTipoServico_1,
															String idLocalidade_1, String nomeLocalidade_1, String dataGeracao_1,
															String idOrdemServico_1, String matriculaImovel_1, String inscricao_1,
															String codigoSetorComercial_1, String catSubCatQtdeEconomias_1,
															String situacaoEsgoto_1, String situacaoAgua_1, String nomeCliente_1,
															String endereco_1, Integer numeroPagina_1) {

		super();
		this.descricaoTipoServico = descricaoTipoServico;
		this.idLocalidade = idLocalidade;
		this.nomeLocalidade = nomeLocalidade;
		this.dataGeracao = dataGeracao;
		this.idOrdemServico = idOrdemServico;
		this.matriculaImovel = matriculaImovel;
		this.inscricao = inscricao;
		this.codigoSetorComercial = codigoSetorComercial;
		this.catSubCatQtdeEconomias = catSubCatQtdeEconomias;
		this.situacaoEsgoto = situacaoEsgoto;
		this.situacaoAgua = situacaoAgua;
		this.nomeCliente = nomeCliente;
		this.endereco = endereco;
		this.numeroPagina = numeroPagina;
		this.descricaoTipoServico_1 = descricaoTipoServico_1;
		this.idLocalidade_1 = idLocalidade_1;
		this.nomeLocalidade_1 = nomeLocalidade_1;
		this.dataGeracao_1 = dataGeracao_1;
		this.idOrdemServico_1 = idOrdemServico_1;
		this.matriculaImovel_1 = matriculaImovel_1;
		this.inscricao_1 = inscricao_1;
		this.codigoSetorComercial_1 = codigoSetorComercial_1;
		this.catSubCatQtdeEconomias_1 = catSubCatQtdeEconomias_1;
		this.situacaoEsgoto_1 = situacaoEsgoto_1;
		this.situacaoAgua_1 = situacaoAgua_1;
		this.nomeCliente_1 = nomeCliente_1;
		this.endereco_1 = endereco_1;
		this.numeroPagina_1 = numeroPagina_1;
	}

	public RelatorioEmitirOrdemServicoSeletivaModelo5Bean(RelatorioEmitirOrdemServicoSeletivaModelo5Bean primeiroHelper,
															RelatorioEmitirOrdemServicoSeletivaModelo5Bean segundoHelper) {

		if(primeiroHelper != null){
			this.descricaoTipoServico = primeiroHelper.getDescricaoTipoServico() != null ? primeiroHelper.getDescricaoTipoServico() : "";
			this.idLocalidade = primeiroHelper.getIdLocalidade() != null ? primeiroHelper.getIdLocalidade() : "";
			this.nomeLocalidade = primeiroHelper.getNomeLocalidade() != null ? primeiroHelper.getNomeLocalidade() : "";
			this.dataGeracao = primeiroHelper.getDataGeracao() != null ? primeiroHelper.getDataGeracao() : "";
			this.idOrdemServico = primeiroHelper.getIdOrdemServico() != null ? primeiroHelper.getIdOrdemServico() : "";
			this.matriculaImovel = primeiroHelper.getMatriculaImovel() != null ? primeiroHelper.getMatriculaImovel() : "";
			this.inscricao = primeiroHelper.getInscricao() != null ? primeiroHelper.getInscricao() : "";
			this.codigoSetorComercial = primeiroHelper.getCodigoSetorComercial() != null ? primeiroHelper.getCodigoSetorComercial() : "";
			this.catSubCatQtdeEconomias = primeiroHelper.getCatSubCatQtdeEconomias() != null ? primeiroHelper.getCatSubCatQtdeEconomias()
							: "";
			this.situacaoEsgoto = primeiroHelper.getSituacaoEsgoto() != null ? primeiroHelper.getSituacaoEsgoto() : "";
			this.situacaoAgua = primeiroHelper.getSituacaoAgua() != null ? primeiroHelper.getSituacaoAgua() : "";
			this.nomeCliente = primeiroHelper.getNomeCliente() != null ? primeiroHelper.getNomeCliente() : "";
			this.endereco = primeiroHelper.getEndereco() != null ? primeiroHelper.getEndereco() : "";
			this.numeroPagina = primeiroHelper.getNumeroPagina();
		}

		if(segundoHelper != null){
			this.descricaoTipoServico_1 = segundoHelper.getDescricaoTipoServico() != null ? segundoHelper.getDescricaoTipoServico() : "";
			this.idLocalidade_1 = segundoHelper.getIdLocalidade() != null ? segundoHelper.getIdLocalidade() : "";
			this.nomeLocalidade_1 = segundoHelper.getNomeLocalidade() != null ? segundoHelper.getNomeLocalidade() : "";
			this.dataGeracao_1 = segundoHelper.getDataGeracao() != null ? segundoHelper.getDataGeracao() : "";
			this.idOrdemServico_1 = segundoHelper.getIdOrdemServico() != null ? segundoHelper.getIdOrdemServico() : "";
			this.matriculaImovel_1 = segundoHelper.getMatriculaImovel() != null ? segundoHelper.getMatriculaImovel() : "";
			this.inscricao_1 = segundoHelper.getInscricao() != null ? segundoHelper.getInscricao() : "";
			this.codigoSetorComercial_1 = segundoHelper.getCodigoSetorComercial() != null ? segundoHelper.getCodigoSetorComercial() : "";
			this.catSubCatQtdeEconomias_1 = segundoHelper.getCatSubCatQtdeEconomias() != null ? segundoHelper.getCatSubCatQtdeEconomias()
							: "";
			this.situacaoEsgoto_1 = segundoHelper.getSituacaoEsgoto() != null ? segundoHelper.getSituacaoEsgoto() : "";
			this.situacaoAgua_1 = segundoHelper.getSituacaoAgua() != null ? segundoHelper.getSituacaoAgua() : "";
			this.nomeCliente_1 = segundoHelper.getNomeCliente() != null ? segundoHelper.getNomeCliente() : "";
			this.endereco_1 = segundoHelper.getEndereco() != null ? segundoHelper.getEndereco() : "";
			this.numeroPagina_1 = segundoHelper.getNumeroPagina();
		}
	}

	public String getCatSubCatQtdeEconomias(){

		return catSubCatQtdeEconomias;
	}

	public void setCatSubCatQtdeEconomias(String catSubCatQtdeEconomias){

		this.catSubCatQtdeEconomias = catSubCatQtdeEconomias;
	}

	public String getDataGeracao(){

		return dataGeracao;
	}

	public void setDataGeracao(String dataGeracao){

		this.dataGeracao = dataGeracao;
	}

	public RelatorioEmitirOrdemServicoSeletivaModelo5Bean() {

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

	public Integer getNumeroPagina(){

		return numeroPagina;
	}

	public void setNumeroPagina(Integer numeroPagina){

		this.numeroPagina = numeroPagina;
	}

	public String getDescricaoTipoServico_1(){

		return descricaoTipoServico_1;
	}

	public void setDescricaoTipoServico_1(String descricaoTipoServico_1){

		this.descricaoTipoServico_1 = descricaoTipoServico_1;
	}

	public String getIdLocalidade_1(){

		return idLocalidade_1;
	}

	public void setIdLocalidade_1(String idLocalidade_1){

		this.idLocalidade_1 = idLocalidade_1;
	}

	public String getNomeLocalidade_1(){

		return nomeLocalidade_1;
	}

	public void setNomeLocalidade_1(String nomeLocalidade_1){

		this.nomeLocalidade_1 = nomeLocalidade_1;
	}

	public String getDataGeracao_1(){

		return dataGeracao_1;
	}

	public void setDataGeracao_1(String dataGeracao_1){

		this.dataGeracao_1 = dataGeracao_1;
	}

	public String getIdOrdemServico_1(){

		return idOrdemServico_1;
	}

	public void setIdOrdemServico_1(String idOrdemServico_1){

		this.idOrdemServico_1 = idOrdemServico_1;
	}

	public String getMatriculaImovel_1(){

		return matriculaImovel_1;
	}

	public void setMatriculaImovel_1(String matriculaImovel_1){

		this.matriculaImovel_1 = matriculaImovel_1;
	}

	public String getInscricao_1(){

		return inscricao_1;
	}

	public void setInscricao_1(String inscricao_1){

		this.inscricao_1 = inscricao_1;
	}

	public String getCodigoSetorComercial_1(){

		return codigoSetorComercial_1;
	}

	public void setCodigoSetorComercial_1(String codigoSetorComercial_1){

		this.codigoSetorComercial_1 = codigoSetorComercial_1;
	}

	public String getCatSubCatQtdeEconomias_1(){

		return catSubCatQtdeEconomias_1;
	}

	public void setCatSubCatQtdeEconomias_1(String catSubCatQtdeEconomias_1){

		this.catSubCatQtdeEconomias_1 = catSubCatQtdeEconomias_1;
	}

	public String getSituacaoEsgoto_1(){

		return situacaoEsgoto_1;
	}

	public void setSituacaoEsgoto_1(String situacaoEsgoto_1){

		this.situacaoEsgoto_1 = situacaoEsgoto_1;
	}

	public String getSituacaoAgua_1(){

		return situacaoAgua_1;
	}

	public void setSituacaoAgua_1(String situacaoAgua_1){

		this.situacaoAgua_1 = situacaoAgua_1;
	}

	public String getNomeCliente_1(){

		return nomeCliente_1;
	}

	public void setNomeCliente_1(String nomeCliente_1){

		this.nomeCliente_1 = nomeCliente_1;
	}

	public String getEndereco_1(){

		return endereco_1;
	}

	public void setEndereco_1(String endereco_1){

		this.endereco_1 = endereco_1;
	}

	public Integer getNumeroPagina_1(){

		return numeroPagina_1;
	}

	public void setNumeroPagina_1(Integer numeroPagina_1){

		this.numeroPagina_1 = numeroPagina_1;
	}


}