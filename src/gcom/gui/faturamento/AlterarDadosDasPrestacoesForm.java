/*
 * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 * 
 * GSANPCG
 * Eduardo Henrique
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.faturamento;

import org.apache.struts.action.ActionForm;

/**
 * [SB0003] – Alterar Dados das Prestações
 * 
 * @author Carlos Chrystian Ramos
 * @date 21/05/2013
 *       Classe Helper utilizada na visualização dos dados da Prestações de uma Guia de Pagamento
 */
public class AlterarDadosDasPrestacoesForm
				extends ActionForm {

	private String[] numeroPrestacao;

	private String[] dataVencimentoPrestacao;

	private String[] numeroPrestacaoArray;

	private String[] dataVencimentoPrestacaoArray;

	private String[] debitoTipo;

	private String[] descricaDebitoTipo;

	private String[] valorDebitoNaPrestacao;

	/**
	 * @return the numeroPrestacao
	 */
	public String[] getNumeroPrestacao(){

		String[] retorno = null;

		if(numeroPrestacao != null){
			retorno = new String[numeroPrestacao.length];

			for(int i = 0; i < numeroPrestacao.length; i++){
				retorno[i] = numeroPrestacao[i];
			}

		}

		return retorno;
	}

	/**
	 * @param numeroPrestacao
	 *            the numeroPrestacao to set
	 */
	public void setNumeroPrestacao(String[] numeroPrestacao){

		this.numeroPrestacao = numeroPrestacao;
	}


	/**
	 * @return the dataVencimentoPrestacao
	 */
	public String[] getDataVencimentoPrestacao(){

		String[] retorno = null;

		if(dataVencimentoPrestacao != null){
			retorno = new String[dataVencimentoPrestacao.length];

			for(int i = 0; i < dataVencimentoPrestacao.length; i++){
				retorno[i] = dataVencimentoPrestacao[i];
			}

		}

		return retorno;

	}

	/**
	 * @param dataVencimentoPrestacao
	 *            the dataVencimentoPrestacao to set
	 */
	public void setDataVencimentoPrestacao(String[] dataVencimentoPrestacao){

		this.dataVencimentoPrestacao = dataVencimentoPrestacao;
	}

	/**
	 * @return the numeroPrestacaoArray
	 */
	public String[] getNumeroPrestacaoArray(){

		String[] retorno = null;

		if(numeroPrestacaoArray != null){
			retorno = new String[numeroPrestacaoArray.length];

			for(int i = 0; i < numeroPrestacaoArray.length; i++){
				retorno[i] = numeroPrestacaoArray[i];
			}

		}

		return retorno;
	}

	/**
	 * @param numeroPrestacaoArray
	 *            the numeroPrestacaoArray to set
	 */
	public void setNumeroPrestacaoArray(String[] numeroPrestacaoArray){

		this.numeroPrestacaoArray = numeroPrestacaoArray;
	}

	/**
	 * @return the dataVencimentoPrestacaoArray
	 */
	public String[] getDataVencimentoPrestacaoArray(){

		String[] retorno = null;

		if(dataVencimentoPrestacaoArray != null){
			retorno = new String[dataVencimentoPrestacaoArray.length];

			for(int i = 0; i < dataVencimentoPrestacaoArray.length; i++){
				retorno[i] = dataVencimentoPrestacaoArray[i];
			}

		}

		return retorno;
	}

	/**
	 * @param dataVencimentoPrestacaoArray
	 *            the dataVencimentoPrestacaoArray to set
	 */
	public void setDataVencimentoPrestacaoArray(String[] dataVencimentoPrestacaoArray){

		this.dataVencimentoPrestacaoArray = dataVencimentoPrestacaoArray;
	}

	/**
	 * @return the debitoTipo
	 */
	public String[] getDebitoTipo(){

		String[] retorno = null;

		if(debitoTipo != null){
			retorno = new String[debitoTipo.length];

			for(int i = 0; i < debitoTipo.length; i++){
				retorno[i] = debitoTipo[i];
			}

		}

		return retorno;
	}

	/**
	 * @param debitoTipo
	 *            the debitoTipo to set
	 */
	public void setDebitoTipo(String[] debitoTipo){

		this.debitoTipo = debitoTipo;
	}

	/**
	 * @return the descricaDebitoTipo
	 */
	public String[] getDescricaDebitoTipo(){

		String[] retorno = null;

		if(descricaDebitoTipo != null){
			retorno = new String[descricaDebitoTipo.length];

			for(int i = 0; i < descricaDebitoTipo.length; i++){
				retorno[i] = descricaDebitoTipo[i];
			}

		}

		return retorno;
	}

	/**
	 * @param descricaDebitoTipo
	 *            the descricaDebitoTipo to set
	 */
	public void setDescricaDebitoTipo(String[] descricaDebitoTipo){

		this.descricaDebitoTipo = descricaDebitoTipo;
	}

	/**
	 * @return the valorDebitoNaPrestacao
	 */
	public String[] getValorDebitoNaPrestacao(){

		String[] retorno = null;

		if(valorDebitoNaPrestacao != null){
			retorno = new String[valorDebitoNaPrestacao.length];

			for(int i = 0; i < valorDebitoNaPrestacao.length; i++){
				retorno[i] = valorDebitoNaPrestacao[i];
			}

		}

		return retorno;
	}

	/**
	 * @param valorDebitoNaPrestacao
	 *            the valorDebitoNaPrestacao to set
	 */
	public void setValorDebitoNaPrestacao(String[] valorDebitoNaPrestacao){

		this.valorDebitoNaPrestacao = valorDebitoNaPrestacao;
	}

}
