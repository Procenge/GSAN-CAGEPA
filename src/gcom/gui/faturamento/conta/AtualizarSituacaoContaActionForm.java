
package gcom.gui.faturamento.conta;

import org.apache.struts.action.ActionForm;

/**
 * Permite atualizar as contas Pré-Faturadas
 * [UC3035] Concluir Faturamento Contas Pré-Faturadas
 * 
 * @author Carlos Chrystian
 * @created 16/02/2012
 */
public class AtualizarSituacaoContaActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	// Conta
	private String idConta;

	// 1.1. Imóvel
	private String imovel;

	// 1.2. Referência da Conta
	private String dataReferenciaConta;

	// 1.3. Grupo Faturamento
	private String faturamentoGrupoDsAbreviado;

	// 1.4. Vencimento da Conta
	private String dataVencimentoConta;

	// 1.5. Valor da Conta
	private String valorConta;

	// 1.6. Situação Atual
	private String situacaoConta;

	//
	// 1.7. Dados do Imóvel
	//

	// 1.7.1. Inscrição
	private String inscricaoFormatadaImovel;

	// 1.7.2. Rota
	private String rota;

	// 1.7.3. Segmento
	private String segmento;

	// 1.7.4. Situação de Água
	private String descricaoLigacaoAguaSituacao;

	// 1.4.4 Situação da Ligação de Esgoto
	private String descricaoLigacaoEsgotoSituacao;

	// 1.4.5 Perfil do Imóvel
	private String descricaoImovelPerfil;

	// 1.4.6 Endereço do Imóvel
	private String enderecoImovel;

	//
	// 1.8. Dados da Conta
	//
	// 1.8.1. Dados Gerais da Conta
	// 1.8.1.1. Situação da Conta
	private String dsSitucaoDebitoCredito;

	// 1.8.1.2. Mês/ano de Referência da Conta
	private String referenciaConta;

	// 1.8.1.3. Mês/ano de Referência Contábil
	private String referenciaContabil;

	// 1.8.1.4. Situação da Ligação de Água
	private String dsLigacaoAguaSituacao;

	// 1.8.1.5. Situação da Ligação de Esgoto
	private String dsLigacaoEsgotoSituacao;

	// 1.8.1.6. Perfil do Imóvel
	private String dsImovelPerfil;

	// 1.8.1.7. Vencimento da Conta
	private String dtVencimentoConta;

	// 1.8.1.8. Validade da Conta
	private String dtValidadeConta;

	// 1.8.1.9. Percentual de Esgoto
	private String pcEsgoto;

	// 1.8.1.10. Fixo de Esgoto
	private String nnConsumoMinimoEsgoto;

	// 1.8.1.11. Tarifa
	private String dsConsumoTarifa;

	// 1.8.1.12. Valor de Água
	private String vlAgua;

	// 1.8.1.13. Valor de Esgoto
	private String vlEsgoto;

	// 1.8.1.14. Valor dos Débitos
	private String vlDebitos;

	// 1.8.1.15. Valor dos Créditos
	private String vlCreditos;

	// 1.8.1.16. Valor dos Impostos
	private String vlImpostos;

	// 1.8.1.17. Valor Total da Conta
	private String vlTotalConta;

	// 1.8.1.18. Débito em Conta
	private String icDebitoConta;

	// 1.8.1.19. Caso a conta seja débito automático, exibir campos abaixo:
	// 1.8.1.19.1. Banco
	private String nmBanco;

	// 1.8.1.19.2. Agência
	private String nmAgencia;

	// 1.8.1.19.3. Conta
	private String dsIdentificacaoClienteBCO;

	// 1.8.2. Dados Retificação/Cancelamento/Revisão
	// 1.8.2.1. Data da Retificação
	private String dtRetificacao;

	// 1.8.2.2. Motivo da Retificação
	private String dsMotivoRetificacaoConta;

	// 1.8.2.3. Data do Cancelamento
	private String dtCancelamento;

	// 1.8.2.4. Motivo do Cancelamento
	private String dsMotivoCancelamentoConta;

	// 1.8.2.5. Data da Revisão
	private String dtRevisao;

	// 1.8.2.6. Motivo da Revisão
	private String dsMotivoRevisaoConta;

	// 1.8.2.7. Usuário Responsável pela Revisão
	private String usuarioResposavelRevisao;

	// Nome do Usuário
	private String nomeUsuarioResposavelRevisao;

	// 1.8.3. Dados do Último Faturamento
	// 1.8.3.1. Leitura Anterior
	private String nnLeituraAnteriorFaturamento;

	// 1.8.3.2. Data da Leitura Anterior
	private String dtLeiruraAnteriorFaturamento;

	// 1.8.3.3. Anormalidade de Leitura Anterior
	private String dsLeituraAnormalidade;

	// 1.8.3.4. Dias de Consumo Anterior
	private String diasConsumoAnterior;

	// 1.8.3.5. Anormalidade de Consumo Anterior
	private String dsConsumoAnormalidade;

	// 1.8.3.6. Tipo de Leitura Anterior
	private String dsLeituraSituacao;

	// 1.8.3.7. Tipo de Consumo Anterior
	private String dsConsumoTipo;

	// 1.8.4. Clientes da Conta
	// 1.8.4.1. Para cada cliente selecionado, exibir os seguintes dados:
	// 1.8.4.1.1. Nome do Cliente
	private String nmCliente;

	// 1.8.4.1.2. Nome na Conta
	private String icNomeConta;

	// 1.8.4.1.3. Tipo de Relação
	private String dsClienteRelacaoTipo;

	// 1.8.4.1.4. CPF
	private String nnCPF;

	// 1.8.5. Categorias da Conta
	// 1.8.5.1. Para cada categoria selecionada, exibir os seguintes dados:
	// 1.8.5.1.1. Categoria
	private String categoria;

	// 1.8.5.1.2. Quantidade de Economias
	private String qtEconomia;

	public String getImovel(){

		return imovel;
	}

	public void setImovel(String imovel){

		this.imovel = imovel;
	}

	public String getDataReferenciaConta(){

		return dataReferenciaConta;
	}

	public void setDataReferenciaConta(String dataReferenciaConta){

		this.dataReferenciaConta = dataReferenciaConta;
	}

	public String getFaturamentoGrupoDsAbreviado(){

		return faturamentoGrupoDsAbreviado;
	}

	public void setFaturamentoGrupoDsAbreviado(String faturamentoGrupoDsAbreviado){

		this.faturamentoGrupoDsAbreviado = faturamentoGrupoDsAbreviado;
	}

	public String getDataVencimentoConta(){

		return dataVencimentoConta;
	}

	public void setDataVencimentoConta(String dataVencimentoConta){

		this.dataVencimentoConta = dataVencimentoConta;
	}

	public String getValorConta(){

		return valorConta;
	}

	public void setValorConta(String valorConta){

		this.valorConta = valorConta;
	}

	public String getSituacaoConta(){

		return situacaoConta;
	}

	public void setSituacaoConta(String situacaoConta){

		this.situacaoConta = situacaoConta;
	}

	public String getInscricaoFormatadaImovel(){

		return inscricaoFormatadaImovel;
	}

	public void setInscricaoFormatadaImovel(String inscricaoFormatadaImovel){

		this.inscricaoFormatadaImovel = inscricaoFormatadaImovel;
	}

	public String getRota(){

		return rota;
	}

	public void setRota(String rota){

		this.rota = rota;
	}

	public String getSegmento(){

		return segmento;
	}

	public void setSegmento(String segmento){

		this.segmento = segmento;
	}

	public String getDescricaoLigacaoAguaSituacao(){

		return descricaoLigacaoAguaSituacao;
	}

	public void setDescricaoLigacaoAguaSituacao(String descricaoLigacaoAguaSituacao){

		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
	}

	public String getDescricaoLigacaoEsgotoSituacao(){

		return descricaoLigacaoEsgotoSituacao;
	}

	public void setDescricaoLigacaoEsgotoSituacao(String descricaoLigacaoEsgotoSituacao){

		this.descricaoLigacaoEsgotoSituacao = descricaoLigacaoEsgotoSituacao;
	}

	public String getDescricaoImovelPerfil(){

		return descricaoImovelPerfil;
	}

	public void setDescricaoImovelPerfil(String descricaoImovelPerfil){

		this.descricaoImovelPerfil = descricaoImovelPerfil;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getDsSitucaoDebitoCredito(){

		return dsSitucaoDebitoCredito;
	}

	public void setDsSitucaoDebitoCredito(String dsSitucaoDebitoCredito){

		this.dsSitucaoDebitoCredito = dsSitucaoDebitoCredito;
	}

	public String getRefereciaConta(){

		return referenciaConta;
	}

	public void setRefereciaConta(String refereciaConta){

		this.referenciaConta = refereciaConta;
	}

	public String getReferenciaContabil(){

		return referenciaContabil;
	}

	public void setReferenciaContabil(String referenciaContabil){

		this.referenciaContabil = referenciaContabil;
	}

	public String getDsLigacaoAguaSituacao(){

		return dsLigacaoAguaSituacao;
	}

	public void setDsLigacaoAguaSituacao(String dsLigacaoAguaSituacao){

		this.dsLigacaoAguaSituacao = dsLigacaoAguaSituacao;
	}

	public String getDsLigacaoEsgotoSituacao(){

		return dsLigacaoEsgotoSituacao;
	}

	public void setDsLigacaoEsgotoSituacao(String dsLigacaoEsgotoSituacao){

		this.dsLigacaoEsgotoSituacao = dsLigacaoEsgotoSituacao;
	}

	public String getDsImovelPerfil(){

		return dsImovelPerfil;
	}

	public void setDsImovelPerfil(String dsImovelPerfil){

		this.dsImovelPerfil = dsImovelPerfil;
	}

	public String getDtVencimentoConta(){

		return dtVencimentoConta;
	}

	public void setDtVencimentoConta(String dtVencimentoConta){

		this.dtVencimentoConta = dtVencimentoConta;
	}

	public String getDtValidadeConta(){

		return dtValidadeConta;
	}

	public void setDtValidadeConta(String dtValidadeConta){

		this.dtValidadeConta = dtValidadeConta;
	}

	public String getPcEsgoto(){

		return pcEsgoto;
	}

	public void setPcEsgoto(String pcEsgoto){

		this.pcEsgoto = pcEsgoto;
	}

	public String getNnConsumoMinimoEsgoto(){

		return nnConsumoMinimoEsgoto;
	}

	public void setNnConsumoMinimoEsgoto(String nnConsumoMinimoEsgoto){

		this.nnConsumoMinimoEsgoto = nnConsumoMinimoEsgoto;
	}

	public String getDsConsumoTarifa(){

		return dsConsumoTarifa;
	}

	public void setDsConsumoTarifa(String dsConsumoTarifa){

		this.dsConsumoTarifa = dsConsumoTarifa;
	}

	public String getVlAgua(){

		return vlAgua;
	}

	public void setVlAgua(String vlAgua){

		this.vlAgua = vlAgua;
	}

	public String getVlEsgoto(){

		return vlEsgoto;
	}

	public void setVlEsgoto(String vlEsgoto){

		this.vlEsgoto = vlEsgoto;
	}

	public String getVlDebitos(){

		return vlDebitos;
	}

	public void setVlDebitos(String vlDebitos){

		this.vlDebitos = vlDebitos;
	}

	public String getVlCreditos(){

		return vlCreditos;
	}

	public void setVlCreditos(String vlCreditos){

		this.vlCreditos = vlCreditos;
	}

	public String getVlImpostos(){

		return vlImpostos;
	}

	public void setVlImpostos(String vlImpostos){

		this.vlImpostos = vlImpostos;
	}

	public String getVlTotalConta(){

		return vlTotalConta;
	}

	public void setVlTotalConta(String vlTotalConta){

		this.vlTotalConta = vlTotalConta;
	}

	public String getIcDebitoConta(){

		return icDebitoConta;
	}

	public void setIcDebitoConta(String icDebitoConta){

		this.icDebitoConta = icDebitoConta;
	}

	public String getNmBanco(){

		return nmBanco;
	}

	public void setNmBanco(String nmBanco){

		this.nmBanco = nmBanco;
	}

	public String getNmAgencia(){

		return nmAgencia;
	}

	public void setNmAgencia(String nmAgencia){

		this.nmAgencia = nmAgencia;
	}

	public String getDsIdentificacaoClienteBCO(){

		return dsIdentificacaoClienteBCO;
	}

	public void setDsIdentificacaoClienteBCO(String dsIdentificacaoClienteBCO){

		this.dsIdentificacaoClienteBCO = dsIdentificacaoClienteBCO;
	}

	public String getDtRetificacao(){

		return dtRetificacao;
	}

	public void setDtRetificacao(String dtRetificacao){

		this.dtRetificacao = dtRetificacao;
	}

	public String getDsMotivoRetificacaoConta(){

		return dsMotivoRetificacaoConta;
	}

	public void setDsMotivoRetificacaoConta(String dsMotivoRetificacaoConta){

		this.dsMotivoRetificacaoConta = dsMotivoRetificacaoConta;
	}

	public String getDtCancelamento(){

		return dtCancelamento;
	}

	public void setDtCancelamento(String dtCancelamento){

		this.dtCancelamento = dtCancelamento;
	}

	public String getDsMotivoCancelamentoConta(){

		return dsMotivoCancelamentoConta;
	}

	public void setDsMotivoCancelamentoConta(String dsMotivoCancelamentoConta){

		this.dsMotivoCancelamentoConta = dsMotivoCancelamentoConta;
	}

	public String getDtRevisao(){

		return dtRevisao;
	}

	public void setDtRevisao(String dtRevisao){

		this.dtRevisao = dtRevisao;
	}

	public String getDsMotivoRevisaoConta(){

		return dsMotivoRevisaoConta;
	}

	public void setDsMotivoRevisaoConta(String dsMotivoRevisaoConta){

		this.dsMotivoRevisaoConta = dsMotivoRevisaoConta;
	}

	public String getUsuarioResposavelRevisao(){

		return usuarioResposavelRevisao;
	}

	public void setUsuarioResposavelRevisao(String usuarioResposavelRevisao){

		this.usuarioResposavelRevisao = usuarioResposavelRevisao;
	}

	public String getNomeUsuarioResposavelRevisao(){

		return nomeUsuarioResposavelRevisao;
	}

	public void setNomeUsuarioResposavelRevisao(String nomeUsuarioResposavelRevisao){

		this.nomeUsuarioResposavelRevisao = nomeUsuarioResposavelRevisao;
	}

	public String getNnLeituraAnteriorFaturamento(){

		return nnLeituraAnteriorFaturamento;
	}

	public void setNnLeituraAnteriorFaturamento(String nnLeituraAnteriorFaturamento){

		this.nnLeituraAnteriorFaturamento = nnLeituraAnteriorFaturamento;
	}

	public String getDtLeiruraAnteriorFaturamento(){

		return dtLeiruraAnteriorFaturamento;
	}

	public void setDtLeiruraAnteriorFaturamento(String dtLeiruraAnteriorFaturamento){

		this.dtLeiruraAnteriorFaturamento = dtLeiruraAnteriorFaturamento;
	}

	public String getDsLeituraAnormalidade(){

		return dsLeituraAnormalidade;
	}

	public void setDsLeituraAnormalidade(String dsLeituraAnormalidade){

		this.dsLeituraAnormalidade = dsLeituraAnormalidade;
	}

	public String getDiasConsumoAnterior(){

		return diasConsumoAnterior;
	}

	public void setDiasConsumoAnterior(String diasConsumoAnterior){

		this.diasConsumoAnterior = diasConsumoAnterior;
	}

	public String getDsConsumoAnormalidade(){

		return dsConsumoAnormalidade;
	}

	public void setDsConsumoAnormalidade(String dsConsumoAnormalidade){

		this.dsConsumoAnormalidade = dsConsumoAnormalidade;
	}

	public String getDsLeituraSituacao(){

		return dsLeituraSituacao;
	}

	public void setDsLeituraSituacao(String dsLeituraSituacao){

		this.dsLeituraSituacao = dsLeituraSituacao;
	}

	public String getDsConsumoTipo(){

		return dsConsumoTipo;
	}

	public void setDsConsumoTipo(String dsConsumoTipo){

		this.dsConsumoTipo = dsConsumoTipo;
	}

	public String getNmCliente(){

		return nmCliente;
	}

	public void setNmCliente(String nmCliente){

		this.nmCliente = nmCliente;
	}

	public String getIcNomeConta(){

		return icNomeConta;
	}

	public void setIcNomeConta(String icNomeConta){

		this.icNomeConta = icNomeConta;
	}

	public String getDsClienteRelacaoTipo(){

		return dsClienteRelacaoTipo;
	}

	public void setDsClienteRelacaoTipo(String dsClienteRelacaoTipo){

		this.dsClienteRelacaoTipo = dsClienteRelacaoTipo;
	}

	public String getNnCPF(){

		return nnCPF;
	}

	public void setNnCPF(String nnCPF){

		this.nnCPF = nnCPF;
	}

	public String getCategoria(){

		return categoria;
	}

	public void setCategoria(String categoria){

		this.categoria = categoria;
	}

	public String getQtEconomia(){

		return qtEconomia;
	}

	public void setQtEconomia(String qtEconomia){

		this.qtEconomia = qtEconomia;
	}

	public String getIdConta(){

		return idConta;
	}

	public void setIdConta(String idConta){

		this.idConta = idConta;
	}

}
