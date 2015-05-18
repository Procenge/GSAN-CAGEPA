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

package gcom.cobranca.ajustetarifa;

import gcom.faturamento.conta.ContaGeral;
import gcom.interceptor.ObjetoGcom;

import java.math.BigDecimal;
import java.util.Date;

public class AjusteTarifaConta
				extends ObjetoGcom {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private BigDecimal valorAguaAnterior;

	private BigDecimal valorAguaAtual;

	private BigDecimal valorEsgotoAnterior;

	private BigDecimal valorEsgotoAtual;

	private BigDecimal valorEsgotoEspecialAnterior;

	private BigDecimal valorEsgotoEspecialAtual;

	private String indicadorProcessado;

	private String descricaoProcessado;

	private Date dataVencimento;

	private Integer quantidadeDiasVecimento;

	private String indicadorPagamento;

	private Date ultimaAlteracao;

	private AjusteTarifa ajusteTarifa;

	private ContaGeral contaGeral;

	public AjusteTarifaConta() {
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public BigDecimal getValorAguaAnterior(){

		return valorAguaAnterior;
	}

	public void setValorAguaAnterior(BigDecimal valorAguaAnterior){

		this.valorAguaAnterior = valorAguaAnterior;
	}

	public BigDecimal getValorAguaAtual(){

		return valorAguaAtual;
	}

	public void setValorAguaAtual(BigDecimal valorAguaAtual){

		this.valorAguaAtual = valorAguaAtual;
	}

	public BigDecimal getValorEsgotoAnterior(){

		return valorEsgotoAnterior;
	}

	public void setValorEsgotoAnterior(BigDecimal valorEsgotoAnterior){

		this.valorEsgotoAnterior = valorEsgotoAnterior;
	}

	public BigDecimal getValorEsgotoAtual(){

		return valorEsgotoAtual;
	}

	public void setValorEsgotoAtual(BigDecimal valorEsgotoAtual){

		this.valorEsgotoAtual = valorEsgotoAtual;
	}

	public String getIndicadorProcessado(){

		return indicadorProcessado;
	}

	public void setIndicadorProcessado(String indicadorProcessado){

		this.indicadorProcessado = indicadorProcessado;
	}

	public String getDescricaoProcessado(){

		return descricaoProcessado;
	}

	public void setDescricaoProcessado(String descricaoProcessado){

		this.descricaoProcessado = descricaoProcessado;
	}

	public Date getDataVencimento(){

		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	public Integer getQuantidadeDiasVecimento(){

		return quantidadeDiasVecimento;
	}

	public void setQuantidadeDiasVecimento(Integer quantidadeDiasVecimento){

		this.quantidadeDiasVecimento = quantidadeDiasVecimento;
	}

	public String getIndicadorPagamento(){

		return indicadorPagamento;
	}

	public void setIndicadorPagamento(String indicadorPagamento){

		this.indicadorPagamento = indicadorPagamento;
	}

	public AjusteTarifa getAjusteTarifa(){

		return ajusteTarifa;
	}

	public void setAjusteTarifa(AjusteTarifa ajusteTarifa){

		this.ajusteTarifa = ajusteTarifa;
	}

	public ContaGeral getContaGeral(){

		return contaGeral;
	}

	public void setContaGeral(ContaGeral contaGeral){

		this.contaGeral = contaGeral;
	}

	public BigDecimal getValorEsgotoEspecialAnterior(){

		return valorEsgotoEspecialAnterior;
	}

	public void setValorEsgotoEspecialAnterior(BigDecimal valorEsgotoEspecialAnterior){

		this.valorEsgotoEspecialAnterior = valorEsgotoEspecialAnterior;
	}

	public BigDecimal getValorEsgotoEspecialAtual(){

		return valorEsgotoEspecialAtual;
	}

	public void setValorEsgotoEspecialAtual(BigDecimal valorEsgotoEspecialAtual){

		this.valorEsgotoEspecialAtual = valorEsgotoEspecialAtual;
	}

	public String getSituacaoFormatada(){

		String retorno = "";

		if(this.getIndicadorPagamento() != null && (this.getIndicadorPagamento().equals("S") || this.getIndicadorPagamento().equals("1"))){

			retorno = "QUITADA";
		}else if(this.getIndicadorPagamento() != null
						&& (this.getIndicadorPagamento().equals("N") || this.getIndicadorPagamento().equals("2"))){

			retorno = "EM D�BITO";
		}else{
			retorno = "N�O INFORM.";
		}

		return retorno;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		// TODO Auto-generated method stub
		return null;
	}
}
