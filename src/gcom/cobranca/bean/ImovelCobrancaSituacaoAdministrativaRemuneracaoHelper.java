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

import gcom.util.Util;

import java.math.BigDecimal;

/**
 * [UC 3060] Consultar Retirar Imovel Cobranca Administrativa
 * 
 * @author Josenildo Neves
 * @date 30/07/2012
 */

public class ImovelCobrancaSituacaoAdministrativaRemuneracaoHelper {

	private static final long serialVersionUID = 1L;

	private String tipo;

	private String identificacao;

	private String descricao;

	private BigDecimal percentualRemuneracao;

	private BigDecimal percentualReincidencia;

	private BigDecimal percentualEspecial;

	private BigDecimal percentualParcelamento;

	private BigDecimal valorRemuneracao;

	private BigDecimal valorReincidencia;

	private BigDecimal valorEspecial;

	private BigDecimal valorParcelamento;

	private BigDecimal total;

	public ImovelCobrancaSituacaoAdministrativaRemuneracaoHelper() {

	};

	/**
	 * @param percentualRemuneracao
	 * @param percentualReincidencia
	 * @param percentualEspecial
	 * @param percentualParcelamento
	 * @param valorRemuneracao
	 * @param valorReincidencia
	 * @param valorEspecial
	 * @param valorParcelamento
	 */
	public ImovelCobrancaSituacaoAdministrativaRemuneracaoHelper(String descricao, BigDecimal percentualRemuneracao,
																	BigDecimal percentualReincidencia,
														BigDecimal percentualEspecial, BigDecimal percentualParcelamento,
														BigDecimal valorRemuneracao, BigDecimal valorReincidencia,
														BigDecimal valorEspecial, BigDecimal valorParcelamento) {

		super();
		this.descricao = descricao;
		this.percentualRemuneracao = percentualRemuneracao;
		this.percentualReincidencia = percentualReincidencia;
		this.percentualEspecial = percentualEspecial;
		this.percentualParcelamento = percentualParcelamento;
		this.valorRemuneracao = valorRemuneracao;
		this.valorReincidencia = valorReincidencia;
		this.valorEspecial = valorEspecial;
		this.valorParcelamento = valorParcelamento;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao(){

		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	/**
	 * @return the percentualRemuneracao
	 */
	public BigDecimal getPercentualRemuneracao(){

		return percentualRemuneracao;
	}

	/**
	 * @param percentualRemuneracao the percentualRemuneracao to set
	 */
	public void setPercentualRemuneracao(BigDecimal percentualRemuneracao){

		this.percentualRemuneracao = percentualRemuneracao;
	}

	/**
	 * @return the percentualReincidencia
	 */
	public BigDecimal getPercentualReincidencia(){

		return percentualReincidencia;
	}

	/**
	 * @param percentualReincidencia the percentualReincidencia to set
	 */
	public void setPercentualReincidencia(BigDecimal percentualReincidencia){

		this.percentualReincidencia = percentualReincidencia;
	}

	/**
	 * @return the percentualEspecial
	 */
	public BigDecimal getPercentualEspecial(){

		return percentualEspecial;
	}

	/**
	 * @param percentualEspecial the percentualEspecial to set
	 */
	public void setPercentualEspecial(BigDecimal percentualEspecial){

		this.percentualEspecial = percentualEspecial;
	}

	/**
	 * @return the percentualParcelamento
	 */
	public BigDecimal getPercentualParcelamento(){

		return percentualParcelamento;
	}

	/**
	 * @param percentualParcelamento the percentualParcelamento to set
	 */
	public void setPercentualParcelamento(BigDecimal percentualParcelamento){

		this.percentualParcelamento = percentualParcelamento;
	}

	/**
	 * @return the valorRemuneracao
	 */
	public BigDecimal getValorRemuneracao(){

		return valorRemuneracao;
	}

	/**
	 * @param valorRemuneracao the valorRemuneracao to set
	 */
	public void setValorRemuneracao(BigDecimal valorRemuneracao){

		this.valorRemuneracao = valorRemuneracao;
	}

	/**
	 * @return the valorReincidencia
	 */
	public BigDecimal getValorReincidencia(){

		return valorReincidencia;
	}

	/**
	 * @param valorReincidencia the valorReincidencia to set
	 */
	public void setValorReincidencia(BigDecimal valorReincidencia){

		this.valorReincidencia = valorReincidencia;
	}

	/**
	 * @return the valorEspecial
	 */
	public BigDecimal getValorEspecial(){

		return valorEspecial;
	}

	/**
	 * @param valorEspecial the valorEspecial to set
	 */
	public void setValorEspecial(BigDecimal valorEspecial){

		this.valorEspecial = valorEspecial;
	}

	/**
	 * @return the valorParcelamento
	 */
	public BigDecimal getValorParcelamento(){

		return valorParcelamento;
	}

	/**
	 * @param valorParcelamento the valorParcelamento to set
	 */
	public void setValorParcelamento(BigDecimal valorParcelamento){

		this.valorParcelamento = valorParcelamento;
	}

	/**
	 * @return the total
	 */
	public BigDecimal getTotal(){

		total = BigDecimal.ZERO;

		if(!Util.isVazioOuBranco(getValorRemuneracao())){
			total = total.add(valorRemuneracao);
		}
		if(!Util.isVazioOuBranco(getValorParcelamento())){
			total = total.add(valorParcelamento);
		}
		if(!Util.isVazioOuBranco(getValorEspecial())){
			total = total.add(valorEspecial);
		}
		if(!Util.isVazioOuBranco(getValorReincidencia())){
			total = total.add(valorReincidencia);
		}

		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(BigDecimal total){

		this.total = total;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo(){

		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo){

		this.tipo = tipo;
	}

	/**
	 * @return the identificacao
	 */
	public String getIdentificacao(){

		return identificacao;
	}

	/**
	 * @param identificacao the identificacao to set
	 */
	public void setIdentificacao(String identificacao){

		this.identificacao = identificacao;
	}

}