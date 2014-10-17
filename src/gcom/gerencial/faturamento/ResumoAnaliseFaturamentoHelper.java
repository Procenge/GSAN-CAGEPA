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
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
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

package gcom.gerencial.faturamento;

import gcom.relatorio.RelatorioBean;
import gcom.util.ConstantesSistema;

import java.math.BigDecimal;

public class ResumoAnaliseFaturamentoHelper
				implements RelatorioBean {

	// Campos do registro de análise de faturamento
	private Integer idCampo;

	private String descricaoCampo;

	private Integer quantidadeConta;

	private Integer quantidadeEconomia;

	private Integer volumeConsumidoAgua;

	private BigDecimal valorFaturadoAgua;

	private Integer volumeColetadoEsgoto;

	private BigDecimal valorFaturadoEsgoto;

	private BigDecimal debitosCobrados;

	private BigDecimal creditosRealizados;

	private BigDecimal totalCobrado;

	public ResumoAnaliseFaturamentoHelper(Integer idCampo, String descricaoCampo, Integer quantidadeConta, Integer quantidadeEconomia,
											Integer volumeConsumidoAgua, BigDecimal valorFaturadoAgua, Integer volumeColetadoEsgoto,
											BigDecimal valorFaturadoEsgoto, BigDecimal debitosCobrados, BigDecimal creditosRealizados,
											BigDecimal totalCobrado) {

		this.idCampo = idCampo;
		this.descricaoCampo = descricaoCampo;
		this.quantidadeConta = quantidadeConta;
		this.quantidadeEconomia = quantidadeEconomia;
		this.volumeConsumidoAgua = volumeConsumidoAgua;
		this.valorFaturadoAgua = valorFaturadoAgua;
		this.volumeColetadoEsgoto = volumeColetadoEsgoto;
		this.valorFaturadoEsgoto = valorFaturadoEsgoto;
		this.debitosCobrados = debitosCobrados;
		this.creditosRealizados = creditosRealizados;
		this.totalCobrado = totalCobrado;
	}

	public ResumoAnaliseFaturamentoHelper(Integer opcaoTotalizacao) {

		switch(opcaoTotalizacao){
			case ConstantesSistema.CODIGO_ESTADO:
			case ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO:
			case ConstantesSistema.CODIGO_ESTADO_GERENCIA_REGIONAL:
			case ConstantesSistema.CODIGO_ESTADO_UNIDADE_NEGOCIO:
			case ConstantesSistema.CODIGO_ESTADO_ELO_POLO:
			case ConstantesSistema.CODIGO_ESTADO_LOCALIDADE:
				this.descricaoCampo = "TOTAL DO ESTADO";
				break;
			case ConstantesSistema.CODIGO_GRUPO_FATURAMENTO:
				this.descricaoCampo = "TOTAL DO GRUPO DE FATURAMENTO";
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL:
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_ELO_POLO:
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_LOCALIDADE:
				this.descricaoCampo = "TOTAL DA GERENCIA REGIONAL";
				break;
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO:
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_ELO_POLO:
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_LOCALIDADE:
				this.descricaoCampo = "TOTAL DA UNIDADE DE NEGOCIO";
				break;
			case ConstantesSistema.CODIGO_ELO_POLO:
			case ConstantesSistema.CODIGO_ELO_POLO_LOCALIDADE:
			case ConstantesSistema.CODIGO_ELO_POLO_SETOR_COMERCIAL:
				this.descricaoCampo = "TOTAL DO ELO POLO";
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE:
			case ConstantesSistema.CODIGO_LOCALIDADE_SETOR_COMERCIAL:
			case ConstantesSistema.CODIGO_LOCALIDADE_QUADRA:
				this.descricaoCampo = "TOTAL DA LOCALIDADE";
				break;
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL:
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL_QUADRA:
				this.descricaoCampo = "TOTAL DO SETOR COMERCIAL";
				break;
			case ConstantesSistema.CODIGO_QUADRA:
				this.descricaoCampo = "TOTAL DA QUADRA";
				break;
		}

		this.quantidadeConta = new Integer(0);
		this.quantidadeEconomia = new Integer(0);
		this.volumeConsumidoAgua = new Integer(0);
		this.valorFaturadoAgua = BigDecimal.ZERO;
		this.volumeColetadoEsgoto = new Integer(0);
		this.valorFaturadoEsgoto = BigDecimal.ZERO;
		this.debitosCobrados = BigDecimal.ZERO;
		this.creditosRealizados = BigDecimal.ZERO;
		this.totalCobrado = BigDecimal.ZERO;

	}

	/**
	 * @return Retorna o campo creditosRealizados.
	 */
	public BigDecimal getCreditosRealizados(){

		return creditosRealizados;
	}

	/**
	 * @param creditosRealizados
	 *            O creditosRealizados a ser setado.
	 */
	public void setCreditosRealizados(BigDecimal creditosRealizados){

		this.creditosRealizados = creditosRealizados;
	}

	/**
	 * @return Retorna o campo debitosCobrados.
	 */
	public BigDecimal getDebitosCobrados(){

		return debitosCobrados;
	}

	/**
	 * @param debitosCobrados
	 *            O debitosCobrados a ser setado.
	 */
	public void setDebitosCobrados(BigDecimal debitosCobrados){

		this.debitosCobrados = debitosCobrados;
	}

	/**
	 * @return Retorna o campo descricao.
	 */
	public String getDescricao(){

		return descricaoCampo;
	}

	/**
	 * @param descricao
	 *            O descricao a ser setado.
	 */
	public void setDescricao(String descricao){

		this.descricaoCampo = descricao;
	}

	/**
	 * @return Retorna o campo quantidadeConta.
	 */
	public Integer getQuantidadeConta(){

		return quantidadeConta;
	}

	/**
	 * @param quantidadeConta
	 *            O quantidadeConta a ser setado.
	 */
	public void setQuantidadeConta(Integer quantidadeConta){

		this.quantidadeConta = quantidadeConta;
	}

	/**
	 * @return Retorna o campo quantidadeEconomia.
	 */
	public Integer getQuantidadeEconomia(){

		return quantidadeEconomia;
	}

	/**
	 * @param quantidadeEconomia
	 *            O quantidadeEconomia a ser setado.
	 */
	public void setQuantidadeEconomia(Integer quantidadeEconomia){

		this.quantidadeEconomia = quantidadeEconomia;
	}

	/**
	 * @return Retorna o campo totalCobrado.
	 */
	public BigDecimal getTotalCobrado(){

		return totalCobrado;
	}

	/**
	 * @param totalCobrado
	 *            O totalCobrado a ser setado.
	 */
	public void setTotalCobrado(BigDecimal totalCobrado){

		this.totalCobrado = totalCobrado;
	}

	/**
	 * @return Retorna o campo valorFaturadoAgua.
	 */
	public BigDecimal getValorFaturadoAgua(){

		return valorFaturadoAgua;
	}

	/**
	 * @param valorFaturadoAgua
	 *            O valorFaturadoAgua a ser setado.
	 */
	public void setValorFaturadoAgua(BigDecimal valorFaturadoAgua){

		this.valorFaturadoAgua = valorFaturadoAgua;
	}

	/**
	 * @return Retorna o campo valorFaturadoEsgoto.
	 */
	public BigDecimal getValorFaturadoEsgoto(){

		return valorFaturadoEsgoto;
	}

	/**
	 * @param valorFaturadoEsgoto
	 *            O valorFaturadoEsgoto a ser setado.
	 */
	public void setValorFaturadoEsgoto(BigDecimal valorFaturadoEsgoto){

		this.valorFaturadoEsgoto = valorFaturadoEsgoto;
	}

	/**
	 * @return Retorna o campo volumeColetadoEsgoto.
	 */
	public Integer getVolumeColetadoEsgoto(){

		return volumeColetadoEsgoto;
	}

	/**
	 * @param volumeColetadoEsgoto
	 *            O volumeColetadoEsgoto a ser setado.
	 */
	public void setVolumeColetadoEsgoto(Integer volumeColetadoEsgoto){

		this.volumeColetadoEsgoto = volumeColetadoEsgoto;
	}

	/**
	 * @return Retorna o campo volumeConsumidoAgua.
	 */
	public Integer getVolumeConsumidoAgua(){

		return volumeConsumidoAgua;
	}

	/**
	 * @param volumeConsumidoAgua
	 *            O volumeConsumidoAgua a ser setado.
	 */
	public void setVolumeConsumidoAgua(Integer volumeConsumidoAgua){

		this.volumeConsumidoAgua = volumeConsumidoAgua;
	}

	public Integer getIdCampo(){

		return idCampo;
	}

	public void setIdCampo(Integer idCampo){

		this.idCampo = idCampo;
	}

	public void acumularValores(ResumoAnaliseFaturamentoHelper objHelper){

		this.quantidadeConta = this.quantidadeConta + objHelper.getQuantidadeConta();
		this.quantidadeEconomia = this.quantidadeEconomia + objHelper.getQuantidadeEconomia();
		this.volumeConsumidoAgua = this.volumeConsumidoAgua + objHelper.getVolumeConsumidoAgua();
		this.valorFaturadoAgua = this.valorFaturadoAgua.add(objHelper.getValorFaturadoAgua());
		this.volumeColetadoEsgoto = this.volumeColetadoEsgoto + objHelper.getVolumeColetadoEsgoto();
		this.valorFaturadoEsgoto = this.valorFaturadoEsgoto.add(objHelper.getValorFaturadoEsgoto());
		this.debitosCobrados = this.debitosCobrados.add(objHelper.getDebitosCobrados());
		this.creditosRealizados = this.creditosRealizados.add(objHelper.getCreditosRealizados());
		this.totalCobrado = this.totalCobrado.add(objHelper.getTotalCobrado());
	}

}
