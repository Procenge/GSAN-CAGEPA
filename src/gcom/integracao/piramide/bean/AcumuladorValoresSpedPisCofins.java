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
 * 
 * GSANPCG
 * Eduardo Henrique
 */
package gcom.integracao.piramide.bean;

import java.math.BigDecimal;


/**
 * @author mgrb
 *
 */
public class AcumuladorValoresSpedPisCofins {

	private Integer qtdDoctoNormal;

	private Integer qtdDoctoJuros;

	private Integer qtdCancDoctoNormal;

	private Integer qtdCancDoctoJuros;

	private BigDecimal valDocto;

	private BigDecimal valDoctoNormal;

	private BigDecimal valDoctoJuros;

	private BigDecimal valCancDoctoNormal;

	private BigDecimal valCancDoctoJuros;

	private Integer qtdCancDoctoNormalAnt;

	private BigDecimal valCancDoctoNormalAnt;

	/**
	 * TabelaIntegracaoConslDiaNfclHelper.Acumulador
	 * <p>
	 * Esse m�todo Constroi uma inst�ncia de TabelaIntegracaoConslDiaNfclHelper.Acumulador.
	 * </p>
	 * 
	 * @author Marlos Ribeiro
	 * @since 18/03/2013
	 */
	public AcumuladorValoresSpedPisCofins() {

		qtdDoctoNormal = Integer.valueOf(0);
		qtdDoctoJuros = Integer.valueOf(0);
		qtdCancDoctoNormal = Integer.valueOf(0);
		qtdCancDoctoNormalAnt = Integer.valueOf(0);
		qtdCancDoctoJuros = Integer.valueOf(0);

		valDocto = BigDecimal.ZERO;
		valDoctoNormal = BigDecimal.ZERO;
		valDoctoJuros = BigDecimal.ZERO;
		valCancDoctoNormal = BigDecimal.ZERO;
		valCancDoctoNormalAnt = BigDecimal.ZERO;
		valCancDoctoJuros = BigDecimal.ZERO;
	}

	/**
	 * M�todo addQtdDoctoNormal
	 * <p>
	 * Esse m�todo implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param i
	 * @author Marlos Ribeiro
	 * @since 18/03/2013
	 */
	public void addQtdDoctoNormal(int i){

		qtdDoctoNormal += i;
	}

	/**
	 * M�todo addValDocto
	 * <p>
	 * Esse m�todo implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param vlConta
	 * @author Marlos Ribeiro
	 * @since 18/03/2013
	 */
	public void addValDocto(BigDecimal vlConta){

		valDocto = valDocto.add(vlConta);

	}

	/**
	 * M�todo addValDoctoNormal
	 * <p>
	 * Esse m�todo implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param vlConta
	 * @author Marlos Ribeiro
	 * @since 18/03/2013
	 */
	public void addValDoctoNormal(BigDecimal vlConta){

		valDoctoNormal = valDoctoNormal.add(vlConta);

	}

	/**
	 * M�todo addQtdDoctoJuros
	 * <p>
	 * Esse m�todo implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param i
	 * @author Marlos Ribeiro
	 * @since 18/03/2013
	 */
	public void addQtdDoctoJuros(int i){

		qtdDoctoJuros += i;
	}

	/**
	 * M�todo addValDoctoJuros
	 * <p>
	 * Esse m�todo implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param valorJurosCorrecaoParcelamento
	 * @author Marlos Ribeiro
	 * @since 18/03/2013
	 */
	public void addValDoctoJuros(BigDecimal valorJurosCorrecaoParcelamento){

		valDoctoJuros = valDoctoJuros.add(valorJurosCorrecaoParcelamento);

	}

	public Integer getQtdDoctoNormal(){

		return qtdDoctoNormal;
	}

	public void setQtdDoctoNormal(Integer qtdDoctoNormal){

		this.qtdDoctoNormal = qtdDoctoNormal;
	}

	public Integer getQtdDoctoJuros(){

		return qtdDoctoJuros;
	}

	public void setQtdDoctoJuros(Integer qtdDoctoJuros){

		this.qtdDoctoJuros = qtdDoctoJuros;
	}

	public Integer getQtdCancDoctoNormal(){

		return qtdCancDoctoNormal;
	}

	public void setQtdCancDoctoNormal(Integer qtdCancDoctoNormal){

		this.qtdCancDoctoNormal = qtdCancDoctoNormal;
	}

	public Integer getQtdCancDoctoJuros(){

		return qtdCancDoctoJuros;
	}

	public void setQtdCancDoctoJuros(Integer qtdCancDoctoJuros){

		this.qtdCancDoctoJuros = qtdCancDoctoJuros;
	}

	public BigDecimal getValDocto(){

		return valDocto;
	}

	public void setValDocto(BigDecimal valDocto){

		this.valDocto = valDocto;
	}

	public BigDecimal getValDoctoNormal(){

		return valDoctoNormal;
	}

	public void setValDoctoNormal(BigDecimal valDoctoNormal){

		this.valDoctoNormal = valDoctoNormal;
	}

	public BigDecimal getValDoctoJuros(){

		return valDoctoJuros;
	}

	public void setValDoctoJuros(BigDecimal valDoctoJuros){

		this.valDoctoJuros = valDoctoJuros;
	}

	public BigDecimal getValCancDoctoNormal(){

		return valCancDoctoNormal;
	}

	public void setValCancDoctoNormal(BigDecimal valCancDoctoNormal){

		this.valCancDoctoNormal = valCancDoctoNormal;
	}

	public BigDecimal getValCancDoctoJuros(){

		return valCancDoctoJuros;
	}

	public void setValCancDoctoJuros(BigDecimal valCancDoctoJuros){

		this.valCancDoctoJuros = valCancDoctoJuros;
	}

	/**
	 * M�todo addQtdCancDoctoNormal
	 * <p>
	 * Esse m�todo implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param i
	 * @author Marlos Ribeiro
	 * @since 22/03/2013
	 */
	public void addQtdCancDoctoNormal(int i){

		qtdCancDoctoNormal += i;

	}

	/**
	 * M�todo addQtdCancDoctoNormalAnt
	 * <p>
	 * Esse m�todo implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param i
	 * @author Marlos Ribeiro
	 * @since 22/03/2013
	 */
	public void addQtdCancDoctoNormalAnt(int i){

		qtdCancDoctoNormalAnt += i;

	}

	/**
	 * M�todo addValCancDoctoNormal
	 * <p>
	 * Esse m�todo implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param vlCancelamento
	 * @author Marlos Ribeiro
	 * @since 22/03/2013
	 */
	public void addValCancDoctoNormal(BigDecimal vlCancelamento){

		valCancDoctoNormal = valCancDoctoNormal.add(vlCancelamento);

	}

	/**
	 * M�todo addValCancDoctoNormalAnt
	 * <p>
	 * Esse m�todo implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param vlCancelamento
	 * @author Marlos Ribeiro
	 * @since 22/03/2013
	 */
	public void addValCancDoctoNormalAnt(BigDecimal vlCancelamento){

		valCancDoctoNormalAnt = valCancDoctoNormalAnt.add(vlCancelamento);

	}

	public void addQtdCanDoctoJuros(int i){

		qtdCancDoctoJuros += i;
	}

	public void addValCanDoctoJuros(BigDecimal valorJurosCorrecao){

		valCancDoctoJuros = valCancDoctoJuros.add(valorJurosCorrecao);
	}

}
