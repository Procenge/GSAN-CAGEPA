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

package gcom.relatorio.faturamento;

import gcom.faturamento.bean.HistogramaEsgotoEconomiaDTO;
import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

/**
 * Bean respons�vel de pegar os parametros que ser�o exibidos na parte de detail
 * do relat�rio de emitir histograma de esgoto por economia.
 * 
 * @author Rafael Pinto
 * @created 07/11/2007
 */
public class RelatorioHistogramaEsgotoEconomiaBean
				implements RelatorioBean {

	private String opcaoTotalizacaoDescricao;
	private String totalizadorDescricao;
	private String percentualDescricao;
	private String categoriaDescricao;
	private String faixaDescricao;
	private Integer qtdEconomiasMedido;
	private Integer qtdEconomiasNaoMedido;
	private Integer qtdLigacoesMedido;
	private Integer qtdLigacoesNaoMedido;
	private BigDecimal valorEsgotoMedido;
	private BigDecimal valorEsgotoNaoMedido;
	private BigDecimal volumeFaturadoMedido;
	private BigDecimal volumeFaturadoNaoMedido;
	private BigDecimal volumeGeradoMedido;
	private BigDecimal volumeGeradoNaoMedido;
	private BigDecimal volumeMedioMedido;
	private BigDecimal volumeMedioNaoMedido;

	/**
	 * RelatorioHistogramaEsgotoEconomiaBean
	 * <p>
	 * Esse m�todo Constroi uma inst�ncia de RelatorioHistogramaEsgotoEconomiaBean.
	 * </p>
	 * 
	 * @param histogramaDTO
	 * @author Marlos Ribeiro
	 * @since 07/06/2013
	 */
	public RelatorioHistogramaEsgotoEconomiaBean(HistogramaEsgotoEconomiaDTO histogramaDTO) {

		opcaoTotalizacaoDescricao = histogramaDTO.getDescricaoOpcaoTotalizacao();
		totalizadorDescricao = histogramaDTO.getTotalizadorDescricao();
		percentualDescricao = histogramaDTO.getPercentualEsgoto() + "%";
		categoriaDescricao = histogramaDTO.getDescricaoCategoria();
		faixaDescricao = histogramaDTO.getFaixaDescricao();
		qtdEconomiasMedido = histogramaDTO.getTotalEconomiasMedido().intValue();
		qtdEconomiasNaoMedido = histogramaDTO.getTotalEconomiasNaoMedido().intValue();
		qtdLigacoesMedido = histogramaDTO.getTotalLigacoesMedido().intValue();
		qtdLigacoesNaoMedido = histogramaDTO.getTotalLigacoesNaoMedido().intValue();
		valorEsgotoMedido = histogramaDTO.getTotalReceitaMedido();
		valorEsgotoNaoMedido = histogramaDTO.getTotalReceitaNaoMedido();
		volumeFaturadoMedido = BigDecimal.valueOf(histogramaDTO.getTotalVolumeFaturadoMedido());
		volumeFaturadoNaoMedido = BigDecimal.valueOf(histogramaDTO.getTotalVolumeFaturadoNaoMedido());
		volumeGeradoMedido = BigDecimal.valueOf(histogramaDTO.getTotalConsumoMedido());
		volumeGeradoNaoMedido = BigDecimal.valueOf(histogramaDTO.getTotalConsumoNaoMedido());
		volumeMedioMedido = histogramaDTO.getTotalConsumoMedioMedido();
		volumeMedioNaoMedido = histogramaDTO.getTotalConsumoMedioNaoMedido();
	}

	public String getOpcaoTotalizacaoDescricao(){

		return opcaoTotalizacaoDescricao;
	}

	public void setOpcaoTotalizacaoDescricao(String opcaoTotalizacaoDescricao){

		this.opcaoTotalizacaoDescricao = opcaoTotalizacaoDescricao;
	}

	public String getTotalizadorDescricao(){

		return totalizadorDescricao;
	}

	public void setTotalizadorDescricao(String totalizadorDescricao){

		this.totalizadorDescricao = totalizadorDescricao;
	}

	public String getPercentualDescricao(){

		return percentualDescricao;
	}

	public void setPercentualDescricao(String percentualDescricao){

		this.percentualDescricao = percentualDescricao;
	}

	public String getCategoriaDescricao(){

		return categoriaDescricao;
	}

	public void setCategoriaDescricao(String categoriaDescricao){

		this.categoriaDescricao = categoriaDescricao;
	}

	public String getFaixaDescricao(){

		return faixaDescricao;
	}

	public void setFaixaDescricao(String faixaDescricao){

		this.faixaDescricao = faixaDescricao;
	}

	public Integer getQtdEconomiasMedido(){

		return qtdEconomiasMedido;
	}

	public void setQtdEconomiasMedido(Integer qtdEconomiasMedido){

		this.qtdEconomiasMedido = qtdEconomiasMedido;
	}

	public Integer getQtdEconomiasNaoMedido(){

		return qtdEconomiasNaoMedido;
	}

	public void setQtdEconomiasNaoMedido(Integer qtdEconomiasNaoMedido){

		this.qtdEconomiasNaoMedido = qtdEconomiasNaoMedido;
	}

	public Integer getQtdLigacoesMedido(){

		return qtdLigacoesMedido;
	}

	public void setQtdLigacoesMedido(Integer qtdLigacoesMedido){

		this.qtdLigacoesMedido = qtdLigacoesMedido;
	}

	public Integer getQtdLigacoesNaoMedido(){

		return qtdLigacoesNaoMedido;
	}

	public void setQtdLigacoesNaoMedido(Integer qtdLigacoesNaoMedido){

		this.qtdLigacoesNaoMedido = qtdLigacoesNaoMedido;
	}

	public BigDecimal getValorEsgotoMedido(){

		return valorEsgotoMedido;
	}

	public void setValorEsgotoMedido(BigDecimal valorEsgotoMedido){

		this.valorEsgotoMedido = valorEsgotoMedido;
	}

	public BigDecimal getValorEsgotoNaoMedido(){

		return valorEsgotoNaoMedido;
	}

	public void setValorEsgotoNaoMedido(BigDecimal valorEsgotoNaoMedido){

		this.valorEsgotoNaoMedido = valorEsgotoNaoMedido;
	}

	public BigDecimal getVolumeFaturadoMedido(){

		return volumeFaturadoMedido;
	}

	public void setVolumeFaturadoMedido(BigDecimal volumeFaturadoMedido){

		this.volumeFaturadoMedido = volumeFaturadoMedido;
	}

	public BigDecimal getVolumeFaturadoNaoMedido(){

		return volumeFaturadoNaoMedido;
	}

	public void setVolumeFaturadoNaoMedido(BigDecimal volumeFaturadoNaoMedido){

		this.volumeFaturadoNaoMedido = volumeFaturadoNaoMedido;
	}

	public BigDecimal getVolumeGeradoMedido(){

		return volumeGeradoMedido;
	}

	public void setVolumeGeradoMedido(BigDecimal volumeGeradoMedido){

		this.volumeGeradoMedido = volumeGeradoMedido;
	}

	public BigDecimal getVolumeGeradoNaoMedido(){

		return volumeGeradoNaoMedido;
	}

	public void setVolumeGeradoNaoMedido(BigDecimal volumeGeradoNaoMedido){

		this.volumeGeradoNaoMedido = volumeGeradoNaoMedido;
	}

	public BigDecimal getVolumeMedioMedido(){

		return volumeMedioMedido;
	}

	public void setVolumeMedioMedido(BigDecimal volumeMedioMedido){

		this.volumeMedioMedido = volumeMedioMedido;
	}

	public BigDecimal getVolumeMedioNaoMedido(){

		return volumeMedioNaoMedido;
	}

	public void setVolumeMedioNaoMedido(BigDecimal volumeMedioNaoMedido){

		this.volumeMedioNaoMedido = volumeMedioNaoMedido;
	}

}
