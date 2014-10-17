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

package gcom.util;

import gcom.faturamento.conta.Conta;

import java.math.BigDecimal;
import java.util.Date;

public class Calculos {

	/**
	 * [UC0261] - Obter o percentual de varia��o do consumo faturado
	 * Author : Fernanda Karla
	 * Data : 11/05/2006
	 * Calcula o percentual de variacao do consumo faturado
	 * 
	 * @param consumoFaturado
	 *            consumoMedio do Im�vel
	 * @return valorPercentual
	 */
	public static String obterPercentualVariacaoConsumoFaturado(int consumoFaturado, int consumoMedio){

		String retorno = null;

		if(consumoFaturado != 0 && consumoMedio != 0){
			int operacaoSubMult = (consumoFaturado - consumoMedio) * 100;
			BigDecimal percentual = new BigDecimal(operacaoSubMult).divide(new BigDecimal(consumoMedio), 2, BigDecimal.ROUND_HALF_UP);
			String valorPercentual = "" + percentual;
			retorno = valorPercentual.replace(".", ",") + "%";
		}
		return retorno;
	}

	/**
	 * [UC0337] - Obter Calculos
	 * Author : S�vio Luiz
	 * Data : 25/05/2006
	 * Calcula o valor da conta.
	 * Nome do C�lculo = "Valor da Conta"
	 * 
	 * @param consumoFaturado
	 *            consumoMedio do Im�vel
	 * @return valorPercentual
	 */
	public static BigDecimal valorConta(Conta conta){

		BigDecimal valorContaSemImpostos = conta.getValorTotal();
		BigDecimal valorConta = null;
		if(conta.getValorImposto() != null && !conta.getValorImposto().equals("")){
			valorConta = valorContaSemImpostos.subtract(conta.getValorImposto());
		}else{
			valorConta = valorContaSemImpostos;
		}
		return valorConta;
	}

	/**
	 * [UC0337] Calculo Consumo Estimado
	 * 
	 * @author Marlos Ribeiro
	 * @param dataLeituraAnterior
	 * @param dataLeituraAtual
	 * @param leituraAnterior
	 * @param leituraAtual
	 * @return Consumo estimado
	 */
	public static BigDecimal calcularConsumoEstimado(Date dataLeituraAnterior, Date dataLeituraAtual, Integer leituraAnterior,
					Integer leituraAtual){

		BigDecimal diasConsumo = calcularDiasConsumo(dataLeituraAnterior, dataLeituraAtual);
		BigDecimal consumoInformado = BigDecimal.valueOf(leituraAtual.longValue() - leituraAnterior.longValue());
		return consumoInformado.divide(diasConsumo, 10, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(30))
						.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * M�todo calcularDiasConsumo
	 * <p>
	 * Esse m�todo calcula a diferenca de dias entre as duas datas.r
	 * </p>
	 * RASTREIO: [OC879896]
	 * 
	 * @param dataLeituraAnterior
	 * @param dataLeituraAtual
	 * @return diferenca entre datas, 1 se as datas forem iguais.
	 * @author Marlos Ribeiro
	 * @since 14/09/2012
	 */
	public static BigDecimal calcularDiasConsumo(Date dataLeituraAnterior, Date dataLeituraAtual){

		return dataLeituraAnterior.equals(dataLeituraAtual) ? BigDecimal.ONE : Util.calcularDiferencaEntreDatas(dataLeituraAnterior,
						dataLeituraAtual, ConstantesSistema.DIFERENCA_DIAS);
	}

}