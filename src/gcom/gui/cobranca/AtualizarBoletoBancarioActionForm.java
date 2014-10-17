
package gcom.gui.cobranca;

import org.apache.struts.action.ActionForm;

/**
 * [UC3023] Manter Boleto Bancário
 * 
 * @author Hebert Falcão
 * @date 12/10/2011
 */
public class AtualizarBoletoBancarioActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String idBoletoBancario;

	private String permitirAlteracao;

	// 1.1 Arrecadador
	private String codigoAgenteArrecadador;

	private String nomeAgenteArrecadador;

	// 1.2 Número do Boleto Bancário
	private String numeroSequencialBoletoBancario;

	// 1.3 Imóvel
	private String idImovel;

	//
	// 1.4 Dados do Imóvel
	//

	// 1.4.1 Inscrição
	private String inscricaoFormatadaImovel;

	// 1.4.3 Situação da Ligação de Água
	private String descricaoLigacaoAguaSituacao;

	// 1.4.4 Situação da Ligação de Esgoto
	private String descricaoLigacaoEsgotoSituacao;

	// 1.4.5 Perfil do Imóvel
	private String descricaoImovelPerfil;

	// 1.4.6 Endereço do Imóvel
	private String enderecoImovel;

	//
	// 1.5 Dados do Boleto
	//

	// 1.5.1 Nosso Número
	private String nossoNumero;

	private String mensagemBoletoLegado;

	// 1.5.2 Referência do Faturamento do Boleto
	private String anoMesReferenciaBoletoBancario;

	// 1.5.3 Data de Entrada do Boleto
	private String dataEntradaBoletoBancario;

	// 1.5.4 Data de Vencimento do Boleto
	private String dataVencimentoBoletoBancario;

	// 1.5.5 Valor do Boleto
	private String valorDebitoBoletoBancario;

	private String idSituacaoBoletoBancario;

	// 1.5.6 Situação Atual
	private String descricaoSituacaoBoletoBancario;

	//
	// 1.5.7 Dados do Documento do Boleto
	//

	// 1.5.7.1 Descrição Tipo do Documento
	private String descricaoDocumentoTipoBoletoBancario;

	//
	// 1.5.9 Dados de Pagamento do Boleto
	//

	// 1.5.9.1 Agência do Pagamento
	private String agenciaPagamento;

	// 1.5.9.2 Data do Pagamento
	private String dataPagamentoBoletoBancario;

	// 1.5.9.3 Data do Crédito
	private String dataCreditoBoletoBancario;

	// 1.5.9.4 Valor Pago
	private String valorPagoBoletoBancario;

	// 1.5.9.5 Valor Tarifa
	private String valorTarifaBoletoBancario;

	// 1.5.9.6 Valor Multa / Juros
	private String valorMultaJurosBoletoBancario;

	//
	// 1.5.10 Dados de Cancelamento do Boleto
	//

	// 1.5.10.1 Data do Cancelamento
	private String dataCancelamentoBoletoBancario;

	// 1.5.10.2 Motivo do Cancelamento
	private String descricaoMotivoCancelamentoBoletoBancario;

	//
	// 1.6 Dados da Alteração
	//

	// 1.6.1 Lançamento de Envio
	private String idBoletoBancarioLancamentoEnvio;

	// 1.6.2 Data de Vencimento do Título
	private String dataVencimentoTitulo;

	private String ocorrenciaMigracao;

	public String getIdBoletoBancario(){

		return idBoletoBancario;
	}

	public void setIdBoletoBancario(String idBoletoBancario){

		this.idBoletoBancario = idBoletoBancario;
	}

	public String getCodigoAgenteArrecadador(){

		return codigoAgenteArrecadador;
	}

	public void setCodigoAgenteArrecadador(String codigoAgenteArrecadador){

		this.codigoAgenteArrecadador = codigoAgenteArrecadador;
	}

	public String getNomeAgenteArrecadador(){

		return nomeAgenteArrecadador;
	}

	public void setNomeAgenteArrecadador(String nomeAgenteArrecadador){

		this.nomeAgenteArrecadador = nomeAgenteArrecadador;
	}

	public String getNumeroSequencialBoletoBancario(){

		return numeroSequencialBoletoBancario;
	}

	public void setNumeroSequencialBoletoBancario(String numeroSequencialBoletoBancario){

		this.numeroSequencialBoletoBancario = numeroSequencialBoletoBancario;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getInscricaoFormatadaImovel(){

		return inscricaoFormatadaImovel;
	}

	public void setInscricaoFormatadaImovel(String inscricaoFormatadaImovel){

		this.inscricaoFormatadaImovel = inscricaoFormatadaImovel;
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

	public String getNossoNumero(){

		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero){

		this.nossoNumero = nossoNumero;
	}

	public String getAnoMesReferenciaBoletoBancario(){

		return anoMesReferenciaBoletoBancario;
	}

	public void setAnoMesReferenciaBoletoBancario(String anoMesReferenciaBoletoBancario){

		this.anoMesReferenciaBoletoBancario = anoMesReferenciaBoletoBancario;
	}

	public String getMensagemBoletoLegado(){

		return mensagemBoletoLegado;
	}

	public void setMensagemBoletoLegado(String mensagemBoletoLegado){

		this.mensagemBoletoLegado = mensagemBoletoLegado;
	}

	public String getDataEntradaBoletoBancario(){

		return dataEntradaBoletoBancario;
	}

	public void setDataEntradaBoletoBancario(String dataEntradaBoletoBancario){

		this.dataEntradaBoletoBancario = dataEntradaBoletoBancario;
	}

	public String getDataVencimentoBoletoBancario(){

		return dataVencimentoBoletoBancario;
	}

	public void setDataVencimentoBoletoBancario(String dataVencimentoBoletoBancario){

		this.dataVencimentoBoletoBancario = dataVencimentoBoletoBancario;
	}

	public String getValorDebitoBoletoBancario(){

		return valorDebitoBoletoBancario;
	}

	public void setValorDebitoBoletoBancario(String valorDebitoBoletoBancario){

		this.valorDebitoBoletoBancario = valorDebitoBoletoBancario;
	}

	public String getDescricaoSituacaoBoletoBancario(){

		return descricaoSituacaoBoletoBancario;
	}

	public void setDescricaoSituacaoBoletoBancario(String descricaoSituacaoBoletoBancario){

		this.descricaoSituacaoBoletoBancario = descricaoSituacaoBoletoBancario;
	}

	public String getDescricaoDocumentoTipoBoletoBancario(){

		return descricaoDocumentoTipoBoletoBancario;
	}

	public void setDescricaoDocumentoTipoBoletoBancario(String descricaoDocumentoTipoBoletoBancario){

		this.descricaoDocumentoTipoBoletoBancario = descricaoDocumentoTipoBoletoBancario;
	}

	public String getDataPagamentoBoletoBancario(){

		return dataPagamentoBoletoBancario;
	}

	public void setDataPagamentoBoletoBancario(String dataPagamentoBoletoBancario){

		this.dataPagamentoBoletoBancario = dataPagamentoBoletoBancario;
	}

	public String getDataCreditoBoletoBancario(){

		return dataCreditoBoletoBancario;
	}

	public void setDataCreditoBoletoBancario(String dataCreditoBoletoBancario){

		this.dataCreditoBoletoBancario = dataCreditoBoletoBancario;
	}

	public String getValorPagoBoletoBancario(){

		return valorPagoBoletoBancario;
	}

	public void setValorPagoBoletoBancario(String valorPagoBoletoBancario){

		this.valorPagoBoletoBancario = valorPagoBoletoBancario;
	}

	public String getValorTarifaBoletoBancario(){

		return valorTarifaBoletoBancario;
	}

	public void setValorTarifaBoletoBancario(String valorTarifaBoletoBancario){

		this.valorTarifaBoletoBancario = valorTarifaBoletoBancario;
	}

	public String getDataCancelamentoBoletoBancario(){

		return dataCancelamentoBoletoBancario;
	}

	public void setDataCancelamentoBoletoBancario(String dataCancelamentoBoletoBancario){

		this.dataCancelamentoBoletoBancario = dataCancelamentoBoletoBancario;
	}

	public String getDescricaoMotivoCancelamentoBoletoBancario(){

		return descricaoMotivoCancelamentoBoletoBancario;
	}

	public void setDescricaoMotivoCancelamentoBoletoBancario(String descricaoMotivoCancelamentoBoletoBancario){

		this.descricaoMotivoCancelamentoBoletoBancario = descricaoMotivoCancelamentoBoletoBancario;
	}

	public String getIdBoletoBancarioLancamentoEnvio(){

		return idBoletoBancarioLancamentoEnvio;
	}

	public void setIdBoletoBancarioLancamentoEnvio(String idBoletoBancarioLancamentoEnvio){

		this.idBoletoBancarioLancamentoEnvio = idBoletoBancarioLancamentoEnvio;
	}

	public String getDataVencimentoTitulo(){

		return dataVencimentoTitulo;
	}

	public void setDataVencimentoTitulo(String dataVencimentoTitulo){

		this.dataVencimentoTitulo = dataVencimentoTitulo;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getIdSituacaoBoletoBancario(){

		return idSituacaoBoletoBancario;
	}

	public void setIdSituacaoBoletoBancario(String idSituacaoBoletoBancario){

		this.idSituacaoBoletoBancario = idSituacaoBoletoBancario;
	}

	public String getPermitirAlteracao(){

		return permitirAlteracao;
	}

	public void setPermitirAlteracao(String permitirAlteracao){

		this.permitirAlteracao = permitirAlteracao;
	}

	public String getValorMultaJurosBoletoBancario(){

		return valorMultaJurosBoletoBancario;
	}

	public void setValorMultaJurosBoletoBancario(String valorMultaJurosBoletoBancario){

		this.valorMultaJurosBoletoBancario = valorMultaJurosBoletoBancario;
	}

	public String getAgenciaPagamento(){

		return agenciaPagamento;
	}

	public void setAgenciaPagamento(String agenciaPagamento){

		this.agenciaPagamento = agenciaPagamento;
	}

	public String getOcorrenciaMigracao(){

		return ocorrenciaMigracao;
	}

	public void setOcorrenciaMigracao(String ocorrenciaMigracao){

		this.ocorrenciaMigracao = ocorrenciaMigracao;
	}

}
