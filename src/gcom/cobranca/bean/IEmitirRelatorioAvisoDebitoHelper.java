package gcom.cobranca.bean;


public interface IEmitirRelatorioAvisoDebitoHelper {

	Integer getIdImovel();

	void setSequencialImpressao(int sequencialAtual);

	void setTotalContasImpressao(int totalContas);

	void setEnderecoFormatado(String enderecoFormatado);

	Integer getIdCobrancaDocumento();
}
