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
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

/**
 * [UC0630] - Solicitar Emiss�o do Extrato de D�bitos
 * 
 * @author Vivianne Sousa
 * @since 21/08/2007
 */
public class DebitoCreditoParcelamentoHelper {

	private Parcelamento parcelamento;

	/**
	 * Cole��o de Debitos a Cobrar do Parcelamento
	 */
	private Collection<DebitoACobrar> colecaoDebitoACobrarParcelamento;

	/**
	 * Cole��o de Creditos a Realizar do Parcelamento
	 */
	private Collection<CreditoARealizar> colecaoCreditoARealizarParcelamento;

	private BigDecimal valorTotalDebito;

	private BigDecimal valorTotalCredito;

	private Collection<Integer> quantidadeParcelasAberto;

	private Boolean selecaoPermitida;

	/*
	 * getters and setters
	 */
	public Collection<Integer> getQuantidadeParcelasAberto(){

		return quantidadeParcelasAberto;
	}

	public void setQuantidadeParcelasAberto(Collection<Integer> quantidadeParcelasAberto){

		this.quantidadeParcelasAberto = quantidadeParcelasAberto;
	}

	public Collection<CreditoARealizar> getColecaoCreditoARealizarParcelamento(){

		return colecaoCreditoARealizarParcelamento;
	}

	public void setColecaoCreditoARealizarParcelamento(Collection<CreditoARealizar> colecaoCreditoARealizarParcelamento){

		this.colecaoCreditoARealizarParcelamento = colecaoCreditoARealizarParcelamento;
	}

	public Collection<DebitoACobrar> getColecaoDebitoACobrarParcelamento(){

		return colecaoDebitoACobrarParcelamento;
	}

	public void setColecaoDebitoACobrarParcelamento(Collection<DebitoACobrar> colecaoDebitoACobrarParcelamento){

		this.colecaoDebitoACobrarParcelamento = colecaoDebitoACobrarParcelamento;
	}

	public Parcelamento getParcelamento(){

		return parcelamento;
	}

	public void setParcelamento(Parcelamento parcelamento){

		this.parcelamento = parcelamento;
	}

	public BigDecimal getValorTotalCredito(){

		return valorTotalCredito;
	}

	public void setValorTotalCredito(BigDecimal valorTotalCredito){

		this.valorTotalCredito = valorTotalCredito;
	}

	public BigDecimal getValorTotalDebito(){

		return valorTotalDebito;
	}

	public void setValorTotalDebito(BigDecimal valorTotalDebito){

		this.valorTotalDebito = valorTotalDebito;
	}

	/**
	 * M�todo respons�vel por retornar o valor total do parcelamento (D�bitos - Cr�ditos)
	 * 
	 * @author Saulo Lima
	 * @date 27/07/2009
	 *       Altera��o para subtrair os cr�ditos dos d�bitos.
	 * @return BigDecimal
	 */
	public BigDecimal getValorTotal(){

		BigDecimal valorTotal = BigDecimal.ZERO;
		BigDecimal valorTotalDebito = BigDecimal.ZERO;
		BigDecimal valorTotalCredito = BigDecimal.ZERO;

		if(this.valorTotalDebito != null){
			valorTotalDebito = this.valorTotalDebito;
		}

		if(this.valorTotalCredito != null){
			valorTotalCredito = this.valorTotalCredito;
		}

		valorTotal = valorTotalDebito.subtract(valorTotalCredito);

		return valorTotal;
	}

	public Boolean getSelecaoPermitida(){

		return selecaoPermitida;
	}

	public void setSelecaoPermitida(Boolean selecaoPermitida){

		this.selecaoPermitida = selecaoPermitida;
	}

	public int getQtdPrestacaoAnteciparParcelasRestantes(){

		int retorno = 0;

		if(!Util.isVazioOrNulo(quantidadeParcelasAberto)){

			Iterator iteratorQuantidadeParcelas = quantidadeParcelasAberto.iterator();
			while(iteratorQuantidadeParcelas.hasNext()){

				retorno = (Integer) iteratorQuantidadeParcelas.next();
				break;

			}

			retorno++;
		}else{

			retorno = 1;
		}

		return retorno;
	}


}
