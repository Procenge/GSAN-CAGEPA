package gcom.cobranca.bean;


public interface IEmitirArquivoAvisoOrdemCorteHelper {

	Integer getIdImovel();

	void setSequencialImpressao(int sequencialAtual);

	void setTotalContasImpressao(int totalContas);

	void setEnderecoFormatado(String enderecoFormatado);

}