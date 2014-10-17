
package gcom.relatorio.cobranca;

import java.math.BigDecimal;

import gcom.relatorio.RelatorioBean;

//UC01101 - Emitir Carta com Opção de parcelamento
public class RelatorioCartaOpcaoParcelamentoDetailBean
				implements RelatorioBean {

	private String numeroOpcao;

	private String desconto;

	private String aPagar;

	private String textoPropostoNegociacao;

	private String condicoesPagamento;

	private String textoValorPrimeiraParcela;

	private String representacaoNumericaCodBarraFormatada;

	private String representacaoNumericaCodBarraSemDigito;

	public RelatorioCartaOpcaoParcelamentoDetailBean() {

	}

	public RelatorioCartaOpcaoParcelamentoDetailBean(String numeroOpcao, String desconto, String aPagar, String textoPropostoNegociacao,
														String condicoesPagamento, String textoValorPrimeiraParcela,
														String representacaoNumericaCodBarraFormatada,
														String representacaoNumericaCodBarraSemDigito) {

		this.numeroOpcao = numeroOpcao;
		this.desconto = desconto;
		this.aPagar = aPagar;
		this.textoPropostoNegociacao = textoPropostoNegociacao;
		this.condicoesPagamento = condicoesPagamento;
		this.textoValorPrimeiraParcela = textoValorPrimeiraParcela;
		this.representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarraFormatada;
		this.representacaoNumericaCodBarraSemDigito = representacaoNumericaCodBarraSemDigito;
	}

	public String getNumeroOpcao(){

		return numeroOpcao;
	}

	public void setNumeroOpcao(String numeroOpcao){

		this.numeroOpcao = numeroOpcao;
	}

	public String getCondicoesPagamento(){

		return condicoesPagamento;
	}

	public void setCondicoesPagamento(String condicoesPagamento){

		this.condicoesPagamento = condicoesPagamento;
	}

	public String getTextoValorPrimeiraParcela(){

		return textoValorPrimeiraParcela;
	}

	public void setTextoValorPrimeiraParcela(String textoValorPrimeiraParcela){

		this.textoValorPrimeiraParcela = textoValorPrimeiraParcela;
	}

	public String getRepresentacaoNumericaCodBarraFormatada(){

		return representacaoNumericaCodBarraFormatada;
	}

	public void setRepresentacaoNumericaCodBarraFormatada(String representacaoNumericaCodBarraFormatada){

		this.representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarraFormatada;
	}

	public String getRepresentacaoNumericaCodBarraSemDigito(){

		return representacaoNumericaCodBarraSemDigito;
	}

	public void setRepresentacaoNumericaCodBarraSemDigito(String representacaoNumericaCodBarraSemDigito){

		this.representacaoNumericaCodBarraSemDigito = representacaoNumericaCodBarraSemDigito;
	}

	public String getTextoPropostoNegociacao(){

		return textoPropostoNegociacao;
	}

	public void setTextoPropostoNegociacao(String textoPropostoNegociacao){

		this.textoPropostoNegociacao = textoPropostoNegociacao;
	}

	public String getDesconto(){

		return desconto;
	}

	public void setDesconto(String desconto){

		this.desconto = desconto;
	}

	public String getaPagar(){

		return aPagar;
	}

	public void setaPagar(String aPagar){

		this.aPagar = aPagar;
	}

}
