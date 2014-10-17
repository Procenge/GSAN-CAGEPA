
package gcom.relatorio.atendimentopublico;

import gcom.relatorio.RelatorioBean;

/**
 * Bean do [UC0713] Emitir Ordem de Servico Seletiva - Modelo 6
 * 
 * @author Eduardo Oliveira
 * @date 13/11/2013
 */
public class RelatorioEmitirOrdemServicoSeletivaModelo6Bean
				implements RelatorioBean {

	// 1° Pagina
	private String descricaoTipoServico;

	private String idLocalidade;

	private String nomeLocalidade;

	private String dataGeracao;

	private String idOrdemServico;

	private String matriculaImovel;

	private String inscricao;

	private String catSubCatQtdeEconomias;

	private String situacaoEsgoto;

	private String situacaoAgua;

	private String nomeCliente;

	private String endereco;

	private Integer numeroPagina;

	// Hidrômetro

	private String numeroHidrometro;

	private String capacidade;

	private String leitura;

	private String marca;

	private String tipo;

	private String diametro;

	private String protecao;

	private String local;

	private String cavalete;

	// 2° Pagina
	private String descricaoTipoServico_1;

	private String idLocalidade_1;

	private String nomeLocalidade_1;

	private String dataGeracao_1;

	private String idOrdemServico_1;

	private String matriculaImovel_1;

	private String inscricao_1;

	private String catSubCatQtdeEconomias_1;

	private String situacaoEsgoto_1;

	private String situacaoAgua_1;

	private String nomeCliente_1;

	private String endereco_1;

	private Integer numeroPagina_1;

	// Hidrômetro

	private String numeroHidrometro_1;

	private String capacidade_1;

	private String leitura_1;

	private String marca_1;

	private String tipo_1;

	private String diametro_1;

	private String protecao_1;

	private String local_1;

	private String cavalete_1;

	public RelatorioEmitirOrdemServicoSeletivaModelo6Bean() {

		super();
	}

	public RelatorioEmitirOrdemServicoSeletivaModelo6Bean(String descricaoTipoServico, String idLocalidade, String nomeLocalidade,
															String dataGeracao, String idOrdemServico, String matriculaImovel,
															String inscricao, String catSubCatQtdeEconomias, String situacaoEsgoto,
															String situacaoAgua, String nomeCliente, String endereco, Integer numeroPagina,
															String numeroHidrometro, String capacidade, String leitura, String marca,
															String tipo, String diametro, String protecao, String local, String cavalete,
															String descricaoTipoServico_1, String idLocalidade_1, String nomeLocalidade_1,
															String dataGeracao_1, String idOrdemServico_1, String matriculaImovel_1,
															String inscricao_1, String catSubCatQtdeEconomias_1, String situacaoEsgoto_1,
															String situacaoAgua_1, String nomeCliente_1, String endereco_1,
															Integer numeroPagina_1, String numeroHidrometro_1, String capacidade_1,
															String leitura_1, String marca_1, String tipo_1, String diametro_1,
															String protecao_1, String local_1, String cavalete_1) {

		super();
		this.descricaoTipoServico = descricaoTipoServico;
		this.idLocalidade = idLocalidade;
		this.nomeLocalidade = nomeLocalidade;
		this.dataGeracao = dataGeracao;
		this.idOrdemServico = idOrdemServico;
		this.matriculaImovel = matriculaImovel;
		this.inscricao = inscricao;
		this.catSubCatQtdeEconomias = catSubCatQtdeEconomias;
		this.situacaoEsgoto = situacaoEsgoto;
		this.situacaoAgua = situacaoAgua;
		this.nomeCliente = nomeCliente;
		this.endereco = endereco;
		this.numeroPagina = numeroPagina;
		this.numeroHidrometro = numeroHidrometro;
		this.capacidade = capacidade;
		this.leitura = leitura;
		this.marca = marca;
		this.tipo = tipo;
		this.diametro = diametro;
		this.protecao = protecao;
		this.local = local;
		this.cavalete = cavalete;
		this.descricaoTipoServico_1 = descricaoTipoServico_1;
		this.idLocalidade_1 = idLocalidade_1;
		this.nomeLocalidade_1 = nomeLocalidade_1;
		this.dataGeracao_1 = dataGeracao_1;
		this.idOrdemServico_1 = idOrdemServico_1;
		this.matriculaImovel_1 = matriculaImovel_1;
		this.inscricao_1 = inscricao_1;
		this.catSubCatQtdeEconomias_1 = catSubCatQtdeEconomias_1;
		this.situacaoEsgoto_1 = situacaoEsgoto_1;
		this.situacaoAgua_1 = situacaoAgua_1;
		this.nomeCliente_1 = nomeCliente_1;
		this.endereco_1 = endereco_1;
		this.numeroPagina_1 = numeroPagina_1;
		this.numeroHidrometro_1 = numeroHidrometro_1;
		this.capacidade_1 = capacidade_1;
		this.leitura_1 = leitura_1;
		this.marca_1 = marca_1;
		this.tipo_1 = tipo_1;
		this.diametro_1 = diametro_1;
		this.protecao_1 = protecao_1;
		this.local_1 = local_1;
		this.cavalete_1 = cavalete_1;
	}

	public RelatorioEmitirOrdemServicoSeletivaModelo6Bean(RelatorioEmitirOrdemServicoSeletivaModelo6Bean primeiroHelper,
															RelatorioEmitirOrdemServicoSeletivaModelo6Bean segundoHelper) {

		if(primeiroHelper != null){
			this.descricaoTipoServico = primeiroHelper.getDescricaoTipoServico() != null ? primeiroHelper.getDescricaoTipoServico() : "";
			this.idLocalidade = primeiroHelper.getIdLocalidade() != null ? primeiroHelper.getIdLocalidade() : "";
			this.nomeLocalidade = primeiroHelper.getNomeLocalidade() != null ? primeiroHelper.getNomeLocalidade() : "";
			this.dataGeracao = primeiroHelper.getDataGeracao() != null ? primeiroHelper.getDataGeracao() : "";
			this.idOrdemServico = primeiroHelper.getIdOrdemServico() != null ? primeiroHelper.getIdOrdemServico() : "";
			this.matriculaImovel = primeiroHelper.getMatriculaImovel() != null ? primeiroHelper.getMatriculaImovel() : "";
			this.inscricao = primeiroHelper.getInscricao() != null ? primeiroHelper.getInscricao() : "";
			this.catSubCatQtdeEconomias = primeiroHelper.getCatSubCatQtdeEconomias() != null ? primeiroHelper.getCatSubCatQtdeEconomias()
							: "";
			this.situacaoEsgoto = primeiroHelper.getSituacaoEsgoto() != null ? primeiroHelper.getSituacaoEsgoto() : "";
			this.situacaoAgua = primeiroHelper.getSituacaoAgua() != null ? primeiroHelper.getSituacaoAgua() : "";
			this.nomeCliente = primeiroHelper.getNomeCliente() != null ? primeiroHelper.getNomeCliente() : "";
			this.endereco = primeiroHelper.getEndereco() != null ? primeiroHelper.getEndereco() : "";
			this.numeroHidrometro = primeiroHelper.getNumeroHidrometro() != null ? primeiroHelper.getNumeroHidrometro() : "";
			this.capacidade = primeiroHelper.getCapacidade() != null ? primeiroHelper.getCapacidade() : "";
			this.leitura = primeiroHelper.getLeitura() != null ? primeiroHelper.getLeitura() : "";
			this.marca = primeiroHelper.getMarca() != null ? primeiroHelper.getMarca() : "";
			this.tipo = primeiroHelper.getTipo() != null ? primeiroHelper.getTipo() : "";
			this.diametro = primeiroHelper.getDiametro() != null ? primeiroHelper.getDiametro() : "";
			this.protecao = primeiroHelper.getProtecao() != null ? primeiroHelper.getProtecao() : "";
			this.local = primeiroHelper.getLocal() != null ? primeiroHelper.getLocal() : "";
			this.cavalete = primeiroHelper.getCavalete() != null ? primeiroHelper.getCavalete() : "";
			this.numeroPagina = primeiroHelper.getNumeroPagina();
		}

		if(segundoHelper != null){
			this.descricaoTipoServico_1 = segundoHelper.getDescricaoTipoServico_1() != null ? segundoHelper.getDescricaoTipoServico_1()
							: "";
			this.idLocalidade_1 = segundoHelper.getIdLocalidade() != null ? segundoHelper.getIdLocalidade() : "";
			this.nomeLocalidade_1 = segundoHelper.getNomeLocalidade() != null ? segundoHelper.getNomeLocalidade() : "";
			this.dataGeracao_1 = segundoHelper.getDataGeracao() != null ? segundoHelper.getDataGeracao() : "";
			this.idOrdemServico_1 = segundoHelper.getIdOrdemServico() != null ? segundoHelper.getIdOrdemServico() : "";
			this.matriculaImovel_1 = segundoHelper.getMatriculaImovel() != null ? segundoHelper.getMatriculaImovel() : "";
			this.inscricao_1 = segundoHelper.getInscricao() != null ? segundoHelper.getInscricao() : "";
			this.catSubCatQtdeEconomias_1 = segundoHelper.getCatSubCatQtdeEconomias() != null ? segundoHelper.getCatSubCatQtdeEconomias()
							: "";
			this.situacaoEsgoto_1 = segundoHelper.getSituacaoEsgoto() != null ? segundoHelper.getSituacaoEsgoto() : "";
			this.situacaoAgua_1 = segundoHelper.getSituacaoAgua() != null ? segundoHelper.getSituacaoAgua() : "";
			this.nomeCliente_1 = segundoHelper.getNomeCliente() != null ? segundoHelper.getNomeCliente() : "";
			this.endereco_1 = segundoHelper.getEndereco() != null ? segundoHelper.getEndereco() : "";
			this.numeroHidrometro_1 = segundoHelper.getNumeroHidrometro() != null ? segundoHelper.getNumeroHidrometro() : "";
			this.capacidade_1 = segundoHelper.getCapacidade() != null ? segundoHelper.getCapacidade() : "";
			this.leitura_1 = segundoHelper.getLeitura() != null ? segundoHelper.getLeitura() : "";
			this.marca_1 = segundoHelper.getMarca() != null ? segundoHelper.getMarca() : "";
			this.tipo_1 = segundoHelper.getTipo() != null ? segundoHelper.getTipo() : "";
			this.diametro_1 = segundoHelper.getDiametro() != null ? segundoHelper.getDiametro() : "";
			this.protecao_1 = segundoHelper.getProtecao() != null ? segundoHelper.getProtecao() : "";
			this.local_1 = segundoHelper.getLocal() != null ? segundoHelper.getLocal() : "";
			this.cavalete_1 = segundoHelper.getCavalete() != null ? segundoHelper.getCavalete() : "";
			this.numeroPagina = segundoHelper.getNumeroPagina();
		}

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

	public String getDataGeracao(){

		return dataGeracao;
	}

	public void setDataGeracao(String dataGeracao){

		this.dataGeracao = dataGeracao;
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

	public String getCatSubCatQtdeEconomias(){

		return catSubCatQtdeEconomias;
	}

	public void setCatSubCatQtdeEconomias(String catSubCatQtdeEconomias){

		this.catSubCatQtdeEconomias = catSubCatQtdeEconomias;
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


	public String getLeitura(){

		return leitura;
	}


	public void setLeitura(String leitura){

		this.leitura = leitura;
	}

	public String getMarca(){

		return marca;
	}

	public void setMarca(String marca){

		this.marca = marca;
	}

	public String getTipo(){

		return tipo;
	}

	public void setTipo(String tipo){

		this.tipo = tipo;
	}

	public String getDiametro(){

		return diametro;
	}

	public void setDiametro(String diametro){

		this.diametro = diametro;
	}

	public String getProtecao(){

		return protecao;
	}

	public void setProtecao(String protecao){

		this.protecao = protecao;
	}

	public String getLocal(){

		return local;
	}

	public void setLocal(String local){

		this.local = local;
	}

	public String getCavalete(){

		return cavalete;
	}

	public void setCavalete(String cavalete){

		this.cavalete = cavalete;
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

	public String getNumeroHidrometro_1(){

		return numeroHidrometro_1;
	}

	public void setNumeroHidrometro_1(String numeroHidrometro_1){

		this.numeroHidrometro_1 = numeroHidrometro_1;
	}

	public String getCapacidade_1(){

		return capacidade_1;
	}

	public void setCapacidade_1(String capacidade_1){

		this.capacidade_1 = capacidade_1;
	}

	public String getLeitura_1(){

		return leitura_1;
	}

	public void setLeitura_1(String leitura_1){

		this.leitura_1 = leitura_1;
	}

	public String getMarca_1(){

		return marca_1;
	}

	public void setMarca_1(String marca_1){

		this.marca_1 = marca_1;
	}

	public String getTipo_1(){

		return tipo_1;
	}

	public void setTipo_1(String tipo_1){

		this.tipo_1 = tipo_1;
	}

	public String getDiametro_1(){

		return diametro_1;
	}

	public void setDiametro_1(String diametro_1){

		this.diametro_1 = diametro_1;
	}

	public String getProtecao_1(){

		return protecao_1;
	}

	public void setProtecao_1(String protecao_1){

		this.protecao_1 = protecao_1;
	}

	public String getLocal_1(){

		return local_1;
	}

	public void setLocal_1(String local_1){

		this.local_1 = local_1;
	}

	public String getCavalete_1(){

		return cavalete_1;
	}

	public void setCavalete_1(String cavalete_1){

		this.cavalete_1 = cavalete_1;
	}


}