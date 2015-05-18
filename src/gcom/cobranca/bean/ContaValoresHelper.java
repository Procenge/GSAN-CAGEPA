/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.cobranca.bean;

import gcom.cobranca.parcelamento.Parcelamento;
import gcom.faturamento.conta.Conta;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Conta do Imovel ou Cliente e : Valor Pago Valor da multa Valor dos juros de
 * mora Valor da atualiza��o monet�ria
 * 
 * @author Rafael Santos
 * @since 04/01/2006
 */
public class ContaValoresHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Conta do Imovel ou Cliente
	 */
	private Conta conta;

	/**
	 * Valor Pago
	 */
	private BigDecimal valorPago = BigDecimal.ZERO;

	/**
	 * Valor da Multa
	 */
	private BigDecimal valorMulta = BigDecimal.ZERO;

	/**
	 * Valor Juros Mora
	 */
	private BigDecimal valorJurosMora = BigDecimal.ZERO;

	/**
	 * Valor Atualizacao Monetaria
	 */
	private BigDecimal valorAtualizacaoMonetaria = BigDecimal.ZERO;

	/**
	 * Valor da Sucumbencia
	 */
	private BigDecimal valorSucumbencia = BigDecimal.ZERO;

	/**
	 * Valor Atualizacao Juros Mora da Sucumbencia
	 */
	private BigDecimal valorJurosMoraSucumbencia = BigDecimal.ZERO;

	/**
	 * Valor Atualizacao Monetaria da Sucumbencia
	 */
	private BigDecimal valorAtualizacaoMonetariaSucumbencia = BigDecimal.ZERO;

	/**
	 * Valor Atualizacao Monetaria
	 */
	private Integer indicadorContasDebito;

	private Short existeParcelamento;

	private Short contaPermitida;

	/**
	 * 
	 */
	public ContaValoresHelper() {

	}

	/**
	 * @param conta
	 * @param valorPago
	 * @param valorMulta
	 * @param valorJurosMora
	 * @param valoratualizacaoMonetaria
	 */
	public ContaValoresHelper(Conta conta, BigDecimal valorPago, BigDecimal valorMulta, BigDecimal valorJurosMora,
								BigDecimal valoratualizacaoMonetaria) {

		this.conta = conta;
		this.valorPago = valorPago;
		this.valorMulta = valorMulta;
		this.valorJurosMora = valorJurosMora;
		this.valorAtualizacaoMonetaria = valoratualizacaoMonetaria;
	}

	/**
	 * @return Returns the conta.
	 */
	public Conta getConta(){

		return conta;
	}

	/**
	 * @param conta
	 *            The conta to set.
	 */
	public void setConta(Conta conta){

		this.conta = conta;

	}

	/**
	 * @return Returns the valoratualizacaoMonetaria.
	 */
	public BigDecimal getValorAtualizacaoMonetaria(){

		return valorAtualizacaoMonetaria;
	}

	/**
	 * @param valoratualizacaoMonetaria
	 *            The valoratualizacaoMonetaria to set.
	 */
	public void setValorAtualizacaoMonetaria(BigDecimal valoratualizacaoMonetaria){

		this.valorAtualizacaoMonetaria = valoratualizacaoMonetaria;
	}

	/**
	 * @return Returns the valorJurosMora.
	 */
	public BigDecimal getValorJurosMora(){

		return valorJurosMora;
	}

	/**
	 * @param valorJurosMora
	 *            The valorJurosMora to set.
	 */
	public void setValorJurosMora(BigDecimal valorJurosMora){

		this.valorJurosMora = valorJurosMora;
	}

	/**
	 * @return Returns the valorMulta.
	 */
	public BigDecimal getValorMulta(){

		return valorMulta;
	}

	/**
	 * @param valorMulta
	 *            The valorMulta to set.
	 */
	public void setValorMulta(BigDecimal valorMulta){

		this.valorMulta = valorMulta;
	}

	/**
	 * @return Returns the valorPago.
	 */
	public BigDecimal getValorPago(){

		return valorPago;
	}

	/**
	 * @param valorPago
	 *            The valorPago to set.
	 */
	public void setValorPago(BigDecimal valorPago){

		this.valorPago = valorPago;
	}

	/**
	 * @return Retorna o campo indicadorContasDebito.
	 */
	public Integer getIndicadorContasDebito(){

		return indicadorContasDebito;
	}

	/**
	 * @param indicadorContasDebito
	 *            O indicadorContasDebito a ser setado.
	 */
	public void setIndicadorContasDebito(Integer indicadorContasDebito){

		this.indicadorContasDebito = indicadorContasDebito;
	}

	/**
	 * @return Retorna o campo existeParcelamento.
	 */
	public Short getExisteParcelamento(){

		return existeParcelamento;
	}

	/**
	 * @param existeParcelamento
	 *            O existeParcelamento a ser setado.
	 */
	public void setExisteParcelamento(Short existeParcelamento){

		this.existeParcelamento = existeParcelamento;
	}

	public BigDecimal getValorTotalContaValores(){

		BigDecimal retorno = BigDecimal.ZERO;

		// Valor de Multa
		if(this.getValorMulta() != null){
			retorno = retorno.add(this.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
		}
		// Valor de JurosMora
		if(this.getValorJurosMora() != null){
			retorno = retorno.add(this.getValorJurosMora().setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
		}
		// Valor de AtualizacaoMonetaria
		if(this.getValorAtualizacaoMonetaria() != null){
			retorno = retorno.add(this.getValorAtualizacaoMonetaria().setScale(Parcelamento.CASAS_DECIMAIS,
							Parcelamento.TIPO_ARREDONDAMENTO));
		}

		retorno = retorno.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);

		return retorno;
	}

	public BigDecimal getValorTotalAcrescimoSucumbencia(){

		BigDecimal retorno = BigDecimal.ZERO;

		// Valor de JurosMora
		if(this.getValorJurosMoraSucumbencia() != null){
			retorno = retorno.add(this.getValorJurosMoraSucumbencia().setScale(Parcelamento.CASAS_DECIMAIS,
							Parcelamento.TIPO_ARREDONDAMENTO));
		}
		// Valor de AtualizacaoMonetaria
		if(this.getValorAtualizacaoMonetariaSucumbencia() != null){
			retorno = retorno.add(this.getValorAtualizacaoMonetariaSucumbencia().setScale(Parcelamento.CASAS_DECIMAIS,
							Parcelamento.TIPO_ARREDONDAMENTO));
		}

		retorno = retorno.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);

		return retorno;
	}

	/*
	 * [UC0214] - Efetuar Parcelamento de D�bitos C�lcula o valor total da conta
	 * (�gua + esgoto + d�bitos - creditos) @author Roberta Costa @created
	 * 03/03/2006
	 */
	public BigDecimal getValorTotalConta(){

		BigDecimal retorno = BigDecimal.ZERO;

		// Valor de �gua
		if(this.getConta().getValorAgua() != null){
			retorno = retorno.add(this.getConta().getValorAgua());
		}
		// Valor de Esgoto
		if(this.getConta().getValorEsgoto() != null){
			retorno = retorno.add(this.getConta().getValorEsgoto());
		}
		// Valor de D�bitos
		if(this.getConta().getDebitos() != null){
			retorno = retorno.add(this.getConta().getDebitos());
		}
		// Valor de Cr�ditos
		if(this.getConta().getValorCreditos() != null){
			retorno = retorno.subtract(this.getConta().getValorCreditos());
		}

		// Valor do Imposto
		// if (this.getConta().getValorImposto() != null) {
		// retorno = retorno.subtract(this.getConta().getValorImposto());
		// }

		retorno = retorno.setScale(2, BigDecimal.ROUND_HALF_UP);

		return retorno;
	}

	// /**
	// * Este m�todo retorna o valor total da conta (VALOR_AGUA + VALOR_ESGOTO +
	// * VALOR_DEBITOS) - VALOR_CREDITOS + (VALOR_MULTA + VALOR_JUROS_MORA +
	// * VALOR_ATUALIZACAO_MONETARIA)
	// * OBS - Este m�todo foi alterado por Raphael pois n�o estava refletindo
	// * corretamente o valor da conta
	// *
	// * @author R�mulo Aur�lio
	// * @date 18/01/2006
	// */
	// public BigDecimal getValorTotalComValorAtualizacaoMonetaria(){
	//
	// BigDecimal valorTotalConta = BigDecimal.ZERO;
	//
	// // Valor de �gua
	// if(this.getConta().getValorAgua() != null){
	// valorTotalConta = valorTotalConta.add(this.getConta().getValorAgua());
	// }
	// // Valor de Esgoto
	// if(this.getConta().getValorEsgoto() != null){
	// valorTotalConta = valorTotalConta.add(this.getConta().getValorEsgoto());
	// }
	// // Valor de D�bitos
	// if(this.getConta().getDebitos() != null){
	// valorTotalConta = valorTotalConta.add(this.getConta().getDebitos());
	// }
	// // Valor de Cr�ditos
	// if(this.getConta().getValorCreditos() != null){
	// valorTotalConta = valorTotalConta.subtract(this.getConta().getValorCreditos());
	// }
	//
	// // Valor de Multa
	// if(this.getValorMulta() != null){
	// valorTotalConta = valorTotalConta.add(this.getValorMulta());
	// }
	// // Valor de JurosMora
	// if(this.getValorJurosMora() != null){
	// valorTotalConta = valorTotalConta.add(this.getValorJurosMora());
	// }
	// // Valor de AtualizacaoMonetaria
	// if(this.getValorAtualizacaoMonetaria() != null){
	// valorTotalConta = valorTotalConta.add(this.getValorAtualizacaoMonetaria());
	// }
	//
	// valorTotalConta = valorTotalConta.setScale(2, BigDecimal.ROUND_HALF_UP);
	//
	// return valorTotalConta;
	// }

	public String getFormatarAnoMesParaMesAno(){

		String anoMesRecebido = "" + this.getConta().getReferencia();
		String mes = anoMesRecebido.substring(4, 6);
		String ano = anoMesRecebido.substring(0, 4);
		String anoMesFormatado = mes + "/" + ano;

		return anoMesFormatado.toString();
	}

	/*
	 * [UC0214] - Efetuar Parcelamento de D�bitos
	 * (multa + juros de mora + atualiza��o monet�ria ) com o arredondamento de Parcelamento
	 * @author Vivianne Sousa
	 * @created 31/01/2007
	 */
	public BigDecimal getValorTotalContaValoresParcelamento(){

		BigDecimal retorno = BigDecimal.ZERO;

		// Valor de Multa
		if(this.getValorMulta() != null){
			retorno.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
			retorno = retorno.add(this.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
		}
		// Valor de JurosMora
		if(this.getValorJurosMora() != null){
			retorno.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
			retorno = retorno.add(this.getValorJurosMora().setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
		}
		// Valor de AtualizacaoMonetaria
		if(this.getValorAtualizacaoMonetaria() != null){
			retorno.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
			retorno = retorno.add(this.getValorAtualizacaoMonetaria().setScale(Parcelamento.CASAS_DECIMAIS,
							Parcelamento.TIPO_ARREDONDAMENTO));
		}
		// Valor de JurosMora Sucumb�ncia
		if(this.getValorJurosMoraSucumbencia() != null){
			retorno.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
			retorno = retorno.add(this.getValorJurosMoraSucumbencia().setScale(Parcelamento.CASAS_DECIMAIS,
							Parcelamento.TIPO_ARREDONDAMENTO));
		}
		// Valor de AtualizacaoMonetaria Sucumb�ncia
		if(this.getValorAtualizacaoMonetariaSucumbencia() != null){
			retorno.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
			retorno = retorno.add(this.getValorAtualizacaoMonetariaSucumbencia().setScale(Parcelamento.CASAS_DECIMAIS,
							Parcelamento.TIPO_ARREDONDAMENTO));
		}

		retorno = retorno.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);

		return retorno;
	}

	// valor de �gua + valor de esgoto + valor dos d�bitos + valor dos acr�scimos por impontualidade
	// - valor dos cr�ditos - valor dos impostos.

	public BigDecimal getValorTotalContaComAcrescimoPorImpontualidade(){

		BigDecimal valorTotalConta = BigDecimal.ZERO;

		// Valor de �gua
		if(this.getConta().getValorAgua() != null){
			valorTotalConta = valorTotalConta.add(this.getConta().getValorAgua());
		}
		// Valor de Esgoto
		if(this.getConta().getValorEsgoto() != null){
			valorTotalConta = valorTotalConta.add(this.getConta().getValorEsgoto());
		}

		// Valor de D�bitos
		if(this.getConta().getDebitos() != null){
			valorTotalConta = valorTotalConta.add(this.getConta().getDebitos());
		}

		// Valor de Multa
		if(this.getValorMulta() != null){
			valorTotalConta = valorTotalConta.add(this.getValorMulta());
		}
		// Valor de JurosMora
		if(this.getValorJurosMora() != null){
			valorTotalConta = valorTotalConta.add(this.getValorJurosMora());
		}
		// Valor de AtualizacaoMonetaria
		if(this.getValorAtualizacaoMonetaria() != null){
			valorTotalConta = valorTotalConta.add(this.getValorAtualizacaoMonetaria());
		}

		// Valor de Cr�ditos
		if(this.getConta().getValorCreditos() != null){
			valorTotalConta = valorTotalConta.subtract(this.getConta().getValorCreditos());
		}

		// Valor do Imposto
		if(this.getConta().getValorImposto() != null){
			valorTotalConta = valorTotalConta.subtract(this.getConta().getValorImposto());
		}

		valorTotalConta = valorTotalConta.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);

		return valorTotalConta;
	}

	public static BigDecimal[] retornarArrayValores(ContaValoresHelper contaValoresHelper){

		BigDecimal[] valoresArray = new BigDecimal[8];

		BigDecimal valorTotalContas = BigDecimal.ZERO;
		BigDecimal valorAtualizacaoMonetaria = BigDecimal.ZERO;
		BigDecimal valorJurosMora = BigDecimal.ZERO;
		BigDecimal valorMulta = BigDecimal.ZERO;
		BigDecimal valorAtualizacaoMonetariaSucumbencia = BigDecimal.ZERO;
		BigDecimal valorJurosMoraSucumbencia = BigDecimal.ZERO;
		BigDecimal valorSucumbencia = BigDecimal.ZERO;
		BigDecimal valorTotalAcrescimoImpontualidadeContas = BigDecimal.ZERO;

		valorTotalContas.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
		valorTotalContas = valorTotalContas.add(contaValoresHelper.getValorTotalConta().setScale(Parcelamento.CASAS_DECIMAIS,
						Parcelamento.TIPO_ARREDONDAMENTO));

		if(contaValoresHelper.getValorAtualizacaoMonetaria() != null && !contaValoresHelper.getValorAtualizacaoMonetaria().equals("")){
			valorAtualizacaoMonetaria.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
			valorAtualizacaoMonetaria = valorAtualizacaoMonetaria.add(contaValoresHelper.getValorAtualizacaoMonetaria().setScale(
							Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
		}
		if(contaValoresHelper.getValorJurosMora() != null && !contaValoresHelper.getValorJurosMora().equals("")){
			valorJurosMora.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
			valorJurosMora = valorJurosMora.add(contaValoresHelper.getValorJurosMora().setScale(Parcelamento.CASAS_DECIMAIS,
							Parcelamento.TIPO_ARREDONDAMENTO));
		}
		if(contaValoresHelper.getValorMulta() != null && !contaValoresHelper.getValorMulta().equals("")){
			valorMulta.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
			valorMulta = valorMulta.add(contaValoresHelper.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS,
							Parcelamento.TIPO_ARREDONDAMENTO));
		}
		if(contaValoresHelper.getValorAtualizacaoMonetariaSucumbencia() != null
						&& !contaValoresHelper.getValorAtualizacaoMonetariaSucumbencia().equals("")){
			valorAtualizacaoMonetariaSucumbencia.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
			valorAtualizacaoMonetariaSucumbencia = valorAtualizacaoMonetariaSucumbencia.add(contaValoresHelper
							.getValorAtualizacaoMonetariaSucumbencia().setScale(Parcelamento.CASAS_DECIMAIS,
											Parcelamento.TIPO_ARREDONDAMENTO));
		}
		if(contaValoresHelper.getValorJurosMoraSucumbencia() != null && !contaValoresHelper.getValorJurosMoraSucumbencia().equals("")){
			valorJurosMoraSucumbencia.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
			valorJurosMoraSucumbencia = valorJurosMoraSucumbencia.add(contaValoresHelper.getValorJurosMoraSucumbencia().setScale(
							Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
		}
		if(contaValoresHelper.getValorSucumbencia() != null && !contaValoresHelper.getValorSucumbencia().equals("")){
			valorSucumbencia.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
			valorSucumbencia = valorSucumbencia.add(contaValoresHelper.getValorSucumbencia().setScale(Parcelamento.CASAS_DECIMAIS,
							Parcelamento.TIPO_ARREDONDAMENTO));
		}

		// Para c�lculo do Acrescimo de Impontualidade
		valorTotalAcrescimoImpontualidadeContas.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
		valorTotalAcrescimoImpontualidadeContas = valorTotalAcrescimoImpontualidadeContas.add(contaValoresHelper
						.getValorTotalContaValoresParcelamento().setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));

		valoresArray[0] = valorTotalContas;
		valoresArray[1] = valorAtualizacaoMonetaria;
		valoresArray[2] = valorJurosMora;
		valoresArray[3] = valorMulta;
		valoresArray[4] = valorAtualizacaoMonetariaSucumbencia;
		valoresArray[5] = valorJurosMoraSucumbencia;
		valoresArray[6] = valorSucumbencia;
		valoresArray[7] = valorTotalAcrescimoImpontualidadeContas;

		return valoresArray;
	}

	// public static BigDecimal[] retornarArrayValores(ContaValoresHelper contaValoresHelper,
	// BigDecimal valorTotalContas,
	// BigDecimal valorTotalAcrescimoImpontualidadeContas, BigDecimal valorAtualizacaoMonetaria,
	// BigDecimal valorJurosMora,
	// BigDecimal valorMulta, BigDecimal valorAtualizacaoMonetariaSucumbencia, BigDecimal
	// valorJurosMoraSucumbencia,
	// BigDecimal valorSucumbencia){
	//
	// BigDecimal[] valoresArray = new BigDecimal[8];
	//
	// BigDecimal retornoValorTotalContas = BigDecimal.ZERO;
	// BigDecimal retornoValorAtualizacaoMonetaria = BigDecimal.ZERO;
	// BigDecimal retornoValorJurosMora = BigDecimal.ZERO;
	// BigDecimal retornoValorMulta = BigDecimal.ZERO;
	// BigDecimal retornoValorAtualizacaoMonetariaSucumbencia = BigDecimal.ZERO;
	// BigDecimal retornoValorJurosMoraSucumbencia = BigDecimal.ZERO;
	// BigDecimal retornoValorSucumbencia = BigDecimal.ZERO;
	// BigDecimal retornoValorTotalAcrescimoImpontualidadeContas = BigDecimal.ZERO;
	//
	// retornoValorTotalContas.setScale(Parcelamento.CASAS_DECIMAIS,
	// Parcelamento.TIPO_ARREDONDAMENTO);
	// retornoValorTotalContas =
	// valorTotalContas.add(contaValoresHelper.getValorTotalConta().setScale(Parcelamento.CASAS_DECIMAIS,
	// Parcelamento.TIPO_ARREDONDAMENTO));
	//
	// if(contaValoresHelper.getValorAtualizacaoMonetaria() != null &&
	// !contaValoresHelper.getValorAtualizacaoMonetaria().equals("")){
	// retornoValorAtualizacaoMonetaria.setScale(Parcelamento.CASAS_DECIMAIS,
	// Parcelamento.TIPO_ARREDONDAMENTO);
	// retornoValorAtualizacaoMonetaria =
	// valorAtualizacaoMonetaria.add(contaValoresHelper.getValorAtualizacaoMonetaria().setScale(
	// Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
	// }
	// if(contaValoresHelper.getValorJurosMora() != null &&
	// !contaValoresHelper.getValorJurosMora().equals("")){
	// retornoValorJurosMora.setScale(Parcelamento.CASAS_DECIMAIS,
	// Parcelamento.TIPO_ARREDONDAMENTO);
	// retornoValorJurosMora =
	// valorJurosMora.add(contaValoresHelper.getValorJurosMora().setScale(Parcelamento.CASAS_DECIMAIS,
	// Parcelamento.TIPO_ARREDONDAMENTO));
	// }
	// if(contaValoresHelper.getValorMulta() != null &&
	// !contaValoresHelper.getValorMulta().equals("")){
	// retornoValorMulta.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
	// retornoValorMulta =
	// valorMulta.add(contaValoresHelper.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS,
	// Parcelamento.TIPO_ARREDONDAMENTO));
	// }
	//
	// if(contaValoresHelper.getValorAtualizacaoMonetariaSucumbencia() != null
	// && !contaValoresHelper.getValorAtualizacaoMonetariaSucumbencia().equals("")){
	// retornoValorAtualizacaoMonetariaSucumbencia.setScale(Parcelamento.CASAS_DECIMAIS,
	// Parcelamento.TIPO_ARREDONDAMENTO);
	// retornoValorAtualizacaoMonetariaSucumbencia =
	// valorAtualizacaoMonetariaSucumbencia.add(contaValoresHelper
	// .getValorAtualizacaoMonetariaSucumbencia().setScale(Parcelamento.CASAS_DECIMAIS,
	// Parcelamento.TIPO_ARREDONDAMENTO));
	// }
	// if(contaValoresHelper.getValorJurosMoraSucumbencia() != null &&
	// !contaValoresHelper.getValorJurosMoraSucumbencia().equals("")){
	// retornoValorJurosMoraSucumbencia.setScale(Parcelamento.CASAS_DECIMAIS,
	// Parcelamento.TIPO_ARREDONDAMENTO);
	// retornoValorJurosMoraSucumbencia =
	// valorJurosMoraSucumbencia.add(contaValoresHelper.getValorJurosMoraSucumbencia().setScale(
	// Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
	// }
	// if(contaValoresHelper.getValorSucumbencia() != null &&
	// !contaValoresHelper.getValorSucumbencia().equals("")){
	// retornoValorSucumbencia.setScale(Parcelamento.CASAS_DECIMAIS,
	// Parcelamento.TIPO_ARREDONDAMENTO);
	// retornoValorSucumbencia =
	// valorSucumbencia.add(contaValoresHelper.getValorSucumbencia().setScale(Parcelamento.CASAS_DECIMAIS,
	// Parcelamento.TIPO_ARREDONDAMENTO));
	// }
	//
	// // Para c�lculo do Acrescimo de Impontualidade
	// retornoValorTotalAcrescimoImpontualidadeContas.setScale(Parcelamento.CASAS_DECIMAIS,
	// Parcelamento.TIPO_ARREDONDAMENTO);
	// retornoValorTotalAcrescimoImpontualidadeContas =
	// valorTotalAcrescimoImpontualidadeContas.add(contaValoresHelper
	// .getValorTotalContaValoresParcelamento().setScale(Parcelamento.CASAS_DECIMAIS,
	// Parcelamento.TIPO_ARREDONDAMENTO));
	//
	// valoresArray[0] = retornoValorTotalContas;
	// valoresArray[1] = retornoValorAtualizacaoMonetaria;
	// valoresArray[2] = retornoValorJurosMora;
	// valoresArray[3] = retornoValorMulta;
	// valoresArray[4] = retornoValorAtualizacaoMonetariaSucumbencia;
	// valoresArray[5] = retornoValorJurosMoraSucumbencia;
	// valoresArray[6] = retornoValorSucumbencia;
	// valoresArray[7] = retornoValorTotalAcrescimoImpontualidadeContas;
	//
	// return valoresArray;
	// }

	public Short getContaPermitida(){

		return contaPermitida;
	}

	public void setContaPermitida(Short contaPermitida){

		this.contaPermitida = contaPermitida;
	}

	public BigDecimal getValorSucumbencia(){

		return valorSucumbencia;
	}

	public void setValorSucumbencia(BigDecimal valorSucumbencia){

		this.valorSucumbencia = valorSucumbencia;
	}

	public BigDecimal getValorJurosMoraSucumbencia(){

		return valorJurosMoraSucumbencia;
	}

	public void setValorJurosMoraSucumbencia(BigDecimal valorJurosMoraSucumbencia){

		this.valorJurosMoraSucumbencia = valorJurosMoraSucumbencia;
	}

	public BigDecimal getValorAtualizacaoMonetariaSucumbencia(){

		return valorAtualizacaoMonetariaSucumbencia;
	}

	public void setValorAtualizacaoMonetariaSucumbencia(BigDecimal valorAtualizacaoMonetariaSucumbencia){

		this.valorAtualizacaoMonetariaSucumbencia = valorAtualizacaoMonetariaSucumbencia;
	}

}