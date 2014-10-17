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

package gcom.relatorio.gerencial.faturamento;

import gcom.relatorio.RelatorioBean;

public class RelatorioAnaliseFaturamentoBean
				implements RelatorioBean {

	// Campos Genericos que serão usado para quebra
	private Integer idCampoQuebra;

	private String descricaoCampoQuebra;

	// Campos do registro de análise de faturamento
	private String descricao;

	private String quantidadeConta;

	private String quantidadeEconomia;

	private String volumeConsumidoAgua;

	private String valorFaturadoAgua;

	private String volumeColetadoEsgoto;

	private String valorFaturadoEsgoto;

	private String debitosCobrados;

	private String creditosRealizados;

	private String totalCobrado;

	/**
	 * Construtor default.
	 */
	public RelatorioAnaliseFaturamentoBean() {

	}

	public RelatorioAnaliseFaturamentoBean(String descricao, String quantidadeConta, String quantidadeEconomia, String volumeConsumidoAgua,
											String valorFaturadoAgua, String volumeColetadoEsgoto, String valorFaturadoEsgoto,
											String debitosCobrados, String creditosRealizados, String totalCobrado) {

		this.descricao = descricao;
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

	/**
	 * @return Retorna o campo creditosRealizados.
	 */
	public String getCreditosRealizados(){

		return creditosRealizados;
	}

	/**
	 * @param creditosRealizados
	 *            O creditosRealizados a ser setado.
	 */
	public void setCreditosRealizados(String creditosRealizados){

		this.creditosRealizados = creditosRealizados;
	}

	/**
	 * @return Retorna o campo debitosCobrados.
	 */
	public String getDebitosCobrados(){

		return debitosCobrados;
	}

	/**
	 * @param debitosCobrados
	 *            O debitosCobrados a ser setado.
	 */
	public void setDebitosCobrados(String debitosCobrados){

		this.debitosCobrados = debitosCobrados;
	}

	/**
	 * @return Retorna o campo descricao.
	 */
	public String getDescricao(){

		return descricao;
	}

	/**
	 * @param descricao
	 *            O descricao a ser setado.
	 */
	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	/**
	 * @return Retorna o campo quantidadeConta.
	 */
	public String getQuantidadeConta(){

		return quantidadeConta;
	}

	/**
	 * @param quantidadeConta
	 *            O quantidadeConta a ser setado.
	 */
	public void setQuantidadeConta(String quantidadeConta){

		this.quantidadeConta = quantidadeConta;
	}

	/**
	 * @return Retorna o campo quantidadeEconomia.
	 */
	public String getQuantidadeEconomia(){

		return quantidadeEconomia;
	}

	/**
	 * @param quantidadeEconomia
	 *            O quantidadeEconomia a ser setado.
	 */
	public void setQuantidadeEconomia(String quantidadeEconomia){

		this.quantidadeEconomia = quantidadeEconomia;
	}

	/**
	 * @return Retorna o campo totalCobrado.
	 */
	public String getTotalCobrado(){

		return totalCobrado;
	}

	/**
	 * @param totalCobrado
	 *            O totalCobrado a ser setado.
	 */
	public void setTotalCobrado(String totalCobrado){

		this.totalCobrado = totalCobrado;
	}

	/**
	 * @return Retorna o campo valorFaturadoAgua.
	 */
	public String getValorFaturadoAgua(){

		return valorFaturadoAgua;
	}

	/**
	 * @param valorFaturadoAgua
	 *            O valorFaturadoAgua a ser setado.
	 */
	public void setValorFaturadoAgua(String valorFaturadoAgua){

		this.valorFaturadoAgua = valorFaturadoAgua;
	}

	/**
	 * @return Retorna o campo valorFaturadoEsgoto.
	 */
	public String getValorFaturadoEsgoto(){

		return valorFaturadoEsgoto;
	}

	/**
	 * @param valorFaturadoEsgoto
	 *            O valorFaturadoEsgoto a ser setado.
	 */
	public void setValorFaturadoEsgoto(String valorFaturadoEsgoto){

		this.valorFaturadoEsgoto = valorFaturadoEsgoto;
	}

	/**
	 * @return Retorna o campo volumeColetadoEsgoto.
	 */
	public String getVolumeColetadoEsgoto(){

		return volumeColetadoEsgoto;
	}

	/**
	 * @param volumeColetadoEsgoto
	 *            O volumeColetadoEsgoto a ser setado.
	 */
	public void setVolumeColetadoEsgoto(String volumeColetadoEsgoto){

		this.volumeColetadoEsgoto = volumeColetadoEsgoto;
	}

	/**
	 * @return Retorna o campo volumeConsumidoAgua.
	 */
	public String getVolumeConsumidoAgua(){

		return volumeConsumidoAgua;
	}

	/**
	 * @param volumeConsumidoAgua
	 *            O volumeConsumidoAgua a ser setado.
	 */
	public void setVolumeConsumidoAgua(String volumeConsumidoAgua){

		this.volumeConsumidoAgua = volumeConsumidoAgua;
	}

	public Integer getIdCampoQuebra(){

		return idCampoQuebra;
	}

	public void setIdCampoQuebra(Integer idCampoQuebra){

		this.idCampoQuebra = idCampoQuebra;
	}

	public String getDescricaoCampoQuebra(){

		return descricaoCampoQuebra;
	}

	public void setDescricaoCampoQuebra(String descricaoCampoQuebra){

		this.descricaoCampoQuebra = descricaoCampoQuebra;
	}

}
