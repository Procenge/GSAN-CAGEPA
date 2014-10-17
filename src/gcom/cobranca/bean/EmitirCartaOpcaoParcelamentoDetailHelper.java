
package gcom.cobranca.bean;

import java.io.Serializable;

//UC01101 - Emitir Carta com Opção de parcelamento
public class EmitirCartaOpcaoParcelamentoDetailHelper
				implements Serializable {

	private String numeroOpcao;

	private String desconto;

	private String aPagar;

	private String textoPropostoNegociacao;

	private String condicoesPagamento;

	private String textoValorPrimeiraParcela;

	private String representacaoNumericaCodBarraFormatada;

	private String representacaoNumericaCodBarraSemDigito;

	private String valorEntrada;

	private String numeroPrestacoes;

	private String valorPrestacao;

	private String posicaoOpcao;

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

	public String getValorEntrada(){

		return valorEntrada;
	}

	public void setValorEntrada(String valorEntrada){

		this.valorEntrada = valorEntrada;
	}

	public String getNumeroPrestacoes(){

		return numeroPrestacoes;
	}

	public void setNumeroPrestacoes(String numeroPrestacoes){

		this.numeroPrestacoes = numeroPrestacoes;
	}

	public String getValorPrestacao(){

		return valorPrestacao;
	}

	public void setValorPrestacao(String valorPrestacao){

		this.valorPrestacao = valorPrestacao;
	}

	public String getPosicaoOpcao(){

		return posicaoOpcao;
	}

	public void setPosicaoOpcao(String posicaoOpcao){

		this.posicaoOpcao = posicaoOpcao;
	}

}