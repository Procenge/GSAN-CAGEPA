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

package gcom.relatorio.gerencial;

import gcom.cadastro.imovel.Categoria;
import gcom.gerencial.bean.FiltroQuadroComparativoFaturamentoArrecadacaoHelper;
import gcom.gerencial.bean.QuadroComparativoFaturamentoArrecadacaoHelper;
import gcom.relatorio.RelatorioBean;
import gcom.util.Util;

import java.util.Collection;

/**
 * classe responsável por criar o relatório de Quadro de Metas Acumulado
 * 
 * @author Luciano Galvão
 * @created 08/05/2013
 */
public class RelatorioQuadroComparativoFaturamentoArrecadacaoBean
				implements RelatorioBean {

	private static final long serialVersionUID = 1L;

	private static final String ZERO = "0,00";

	private String opcaoTotalizacao;

	private String mesAnoReferencia;

	private String nomeGerenciaRegional;

	private String descricaoLocalidade;

	// Valores da categoria RESIDENCIAL
	private String valorContaFaturadaResidencial;

	private String valorContaIncluidaResidencial;

	private String valorParcCobradoContaFatResidencial;

	private String valorContaCanceladaResidencial;

	private String valorContaCancPorParcResidencial;

	private String valorArrecadavelResidencial;

	private String valorRecebimentoResidencial;

	private String valorPendenciaResidencial;

	private String percRecebimentosResidencial;

	private String percRecebimentosEmDiaResidencial;

	// Valores da categoria COMERCIAL
	private String valorContaFaturadaComercial;

	private String valorContaIncluidaComercial;

	private String valorParcCobradoContaFatComercial;

	private String valorContaCanceladaComercial;

	private String valorContaCancPorParcComercial;

	private String valorArrecadavelComercial;

	private String valorRecebimentoComercial;

	private String valorPendenciaComercial;

	private String percRecebimentosComercial;

	private String percRecebimentosEmDiaComercial;

	// Valores da categoria INDUSTRIAL
	private String valorContaFaturadaIndustrial;

	private String valorContaIncluidaIndustrial;

	private String valorParcCobradoContaFatIndustrial;

	private String valorContaCanceladaIndustrial;

	private String valorContaCancPorParcIndustrial;

	private String valorArrecadavelIndustrial;

	private String valorRecebimentoIndustrial;

	private String valorPendenciaIndustrial;

	private String percRecebimentosIndustrial;

	private String percRecebimentosEmDiaIndustrial;

	// Valores da categoria PUBLICO
	private String valorContaFaturadaPublico;

	private String valorContaIncluidaPublico;

	private String valorParcCobradoContaFatPublico;

	private String valorContaCanceladaPublico;

	private String valorContaCancPorParcPublico;

	private String valorArrecadavelPublico;

	private String valorRecebimentoPublico;

	private String valorPendenciaPublico;

	private String percRecebimentosPublico;

	private String percRecebimentosEmDiaPublico;

	// Valores da categoria TOTAL
	private String valorContaFaturadaTotal;

	private String valorContaIncluidaTotal;

	private String valorParcCobradoContaFatTotal;

	private String valorContaCanceladaTotal;

	private String valorContaCancPorParcTotal;

	private String valorArrecadavelTotal;

	private String valorRecebimentoTotal;

	private String valorPendenciaTotal;

	private String percRecebimentosTotal;

	private String percRecebimentosEmDiaTotal;



	public RelatorioQuadroComparativoFaturamentoArrecadacaoBean(Collection<QuadroComparativoFaturamentoArrecadacaoHelper> quadroComparativo,
																FiltroQuadroComparativoFaturamentoArrecadacaoHelper filtro) {

		opcaoTotalizacao = filtro.getDescricaoOpcaoTotalizacao() != null ? filtro.getDescricaoOpcaoTotalizacao() : "";

		mesAnoReferencia = filtro.getAnoMesReferencia() != null ? Util.formatarAnoMesSemBarraParaMesAnoComBarra(filtro
						.getAnoMesReferencia()) : "";

		nomeGerenciaRegional = filtro.getNomeGerenciaRegional() != null ? filtro.getNomeGerenciaRegional() : "";

		descricaoLocalidade = filtro.getDescricaoLocalidade() != null ? filtro.getDescricaoLocalidade() : "";
		
		// Valores da categoria RESIDENCIAL
		for(QuadroComparativoFaturamentoArrecadacaoHelper registroQuadroComparativo : quadroComparativo){

			if(registroQuadroComparativo.getIdCategoria() != null){
				switch(registroQuadroComparativo.getIdCategoria()){

					case Categoria.RESIDENCIAL_INT:

						valorContaFaturadaResidencial = registroQuadroComparativo.getValorContaFaturada() != null ? Util
										.formatarMoedaReal(registroQuadroComparativo.getValorContaFaturada()) : ZERO;
						valorContaIncluidaResidencial = registroQuadroComparativo.getValorContaIncluida() != null ? Util
										.formatarMoedaReal(registroQuadroComparativo.getValorContaIncluida()) : ZERO;
						valorParcCobradoContaFatResidencial = registroQuadroComparativo.getValorParcelamentoCobradoContaFaturada() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorParcelamentoCobradoContaFaturada()) : ZERO;
						valorContaCanceladaResidencial = registroQuadroComparativo.getValorContaCancelada() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorContaCancelada()) : ZERO;
						valorContaCancPorParcResidencial = registroQuadroComparativo.getValorContaCanceladaPorParcelamento() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorContaCanceladaPorParcelamento()) : ZERO;
						valorArrecadavelResidencial = registroQuadroComparativo.getValorArrecadavel() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorArrecadavel()) : ZERO;
						valorRecebimentoResidencial = registroQuadroComparativo.getValorContaPaga() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorContaPaga()) : ZERO;
						valorPendenciaResidencial = registroQuadroComparativo.getValorPendencia() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorPendencia()) : ZERO;
						percRecebimentosResidencial = registroQuadroComparativo.getPercentualRecebimentos() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getPercentualRecebimentos()) : ZERO;
						percRecebimentosEmDiaResidencial = registroQuadroComparativo.getPercentualRecebimentoEmDia() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getPercentualRecebimentoEmDia()) : ZERO;
						break;

					case Categoria.COMERCIAL_INT:

						valorContaFaturadaComercial = registroQuadroComparativo.getValorContaFaturada() != null ? Util
										.formatarMoedaReal(registroQuadroComparativo.getValorContaFaturada()) : ZERO;
						valorContaIncluidaComercial = registroQuadroComparativo.getValorContaIncluida() != null ? Util
										.formatarMoedaReal(registroQuadroComparativo.getValorContaIncluida()) : ZERO;
						valorParcCobradoContaFatComercial = registroQuadroComparativo.getValorParcelamentoCobradoContaFaturada() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorParcelamentoCobradoContaFaturada()) : ZERO;
						valorContaCanceladaComercial = registroQuadroComparativo.getValorContaCancelada() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorContaCancelada()) : ZERO;
						valorContaCancPorParcComercial = registroQuadroComparativo.getValorContaCanceladaPorParcelamento() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorContaCanceladaPorParcelamento()) : ZERO;
						valorArrecadavelComercial = registroQuadroComparativo.getValorArrecadavel() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorArrecadavel()) : ZERO;
						valorRecebimentoComercial = registroQuadroComparativo.getValorContaPaga() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorContaPaga()) : ZERO;
						valorPendenciaComercial = registroQuadroComparativo.getValorPendencia() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorPendencia()) : ZERO;
						percRecebimentosComercial = registroQuadroComparativo.getPercentualRecebimentos() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getPercentualRecebimentos()) : ZERO;
						percRecebimentosEmDiaComercial = registroQuadroComparativo.getPercentualRecebimentoEmDia() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getPercentualRecebimentoEmDia()) : ZERO;
						break;

					case Categoria.INDUSTRIAL_INT:

						valorContaFaturadaIndustrial = registroQuadroComparativo.getValorContaFaturada() != null ? Util
										.formatarMoedaReal(registroQuadroComparativo.getValorContaFaturada()) : ZERO;
						valorContaIncluidaIndustrial = registroQuadroComparativo.getValorContaIncluida() != null ? Util
										.formatarMoedaReal(registroQuadroComparativo.getValorContaIncluida()) : ZERO;
						valorParcCobradoContaFatIndustrial = registroQuadroComparativo.getValorParcelamentoCobradoContaFaturada() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorParcelamentoCobradoContaFaturada()) : ZERO;
						valorContaCanceladaIndustrial = registroQuadroComparativo.getValorContaCancelada() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorContaCancelada()) : ZERO;
						valorContaCancPorParcIndustrial = registroQuadroComparativo.getValorContaCanceladaPorParcelamento() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorContaCanceladaPorParcelamento()) : ZERO;
						valorArrecadavelIndustrial = registroQuadroComparativo.getValorArrecadavel() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorArrecadavel()) : ZERO;
						valorRecebimentoIndustrial = registroQuadroComparativo.getValorContaPaga() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorContaPaga()) : ZERO;
						valorPendenciaIndustrial = registroQuadroComparativo.getValorPendencia() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorPendencia()) : ZERO;
						percRecebimentosIndustrial = registroQuadroComparativo.getPercentualRecebimentos() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getPercentualRecebimentos()) : ZERO;
						percRecebimentosEmDiaIndustrial = registroQuadroComparativo.getPercentualRecebimentoEmDia() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getPercentualRecebimentoEmDia()) : ZERO;
						break;

					case Categoria.PUBLICO_INT:

						valorContaFaturadaPublico = registroQuadroComparativo.getValorContaFaturada() != null ? Util
										.formatarMoedaReal(registroQuadroComparativo.getValorContaFaturada()) : ZERO;
						valorContaIncluidaPublico = registroQuadroComparativo.getValorContaIncluida() != null ? Util
										.formatarMoedaReal(registroQuadroComparativo.getValorContaIncluida()) : ZERO;
						valorParcCobradoContaFatPublico = registroQuadroComparativo.getValorParcelamentoCobradoContaFaturada() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorParcelamentoCobradoContaFaturada()) : ZERO;
						valorContaCanceladaPublico = registroQuadroComparativo.getValorContaCancelada() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorContaCancelada()) : ZERO;
						valorContaCancPorParcPublico = registroQuadroComparativo.getValorContaCanceladaPorParcelamento() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorContaCanceladaPorParcelamento()) : ZERO;
						valorArrecadavelPublico = registroQuadroComparativo.getValorArrecadavel() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorArrecadavel()) : ZERO;
						valorRecebimentoPublico = registroQuadroComparativo.getValorContaPaga() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorContaPaga()) : ZERO;
						valorPendenciaPublico = registroQuadroComparativo.getValorPendencia() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorPendencia()) : ZERO;
						percRecebimentosPublico = registroQuadroComparativo.getPercentualRecebimentos() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getPercentualRecebimentos()) : ZERO;
						percRecebimentosEmDiaPublico = registroQuadroComparativo.getPercentualRecebimentoEmDia() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getPercentualRecebimentoEmDia()) : ZERO;
						break;

					default:
						break;
				}
			}else{
				valorContaFaturadaTotal = registroQuadroComparativo.getValorContaFaturada() != null ? Util
								.formatarMoedaReal(registroQuadroComparativo.getValorContaFaturada()) : ZERO;
				valorContaIncluidaTotal = registroQuadroComparativo.getValorContaIncluida() != null ? Util
								.formatarMoedaReal(registroQuadroComparativo.getValorContaIncluida()) : ZERO;
				valorParcCobradoContaFatTotal = registroQuadroComparativo.getValorParcelamentoCobradoContaFaturada() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorParcelamentoCobradoContaFaturada()) : ZERO;
				valorContaCanceladaTotal = registroQuadroComparativo.getValorContaCancelada() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorContaCancelada()) : ZERO;
				valorContaCancPorParcTotal = registroQuadroComparativo.getValorContaCanceladaPorParcelamento() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorContaCanceladaPorParcelamento()) : ZERO;
				valorArrecadavelTotal = registroQuadroComparativo.getValorArrecadavel() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorArrecadavel()) : ZERO;
				valorRecebimentoTotal = registroQuadroComparativo.getValorContaPaga() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorContaPaga()) : ZERO;
				valorPendenciaTotal = registroQuadroComparativo.getValorPendencia() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getValorPendencia()) : ZERO;
				percRecebimentosTotal = registroQuadroComparativo.getPercentualRecebimentos() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getPercentualRecebimentos()) : ZERO;
				percRecebimentosEmDiaTotal = registroQuadroComparativo.getPercentualRecebimentoEmDia() != null ? Util.formatarMoedaReal(registroQuadroComparativo.getPercentualRecebimentoEmDia()) : ZERO;
			}
		}
	}

	public static long getSerialVersionUID(){

		return serialVersionUID;
	}

	public String getOpcaoTotalizacao(){

		return opcaoTotalizacao;
	}

	public void setOpcaoTotalizacao(String opcaoTotalizacao){

		this.opcaoTotalizacao = opcaoTotalizacao;
	}

	public String getMesAnoReferencia(){

		return mesAnoReferencia;
	}

	public void setMesAnoReferencia(String mesAnoReferencia){

		this.mesAnoReferencia = mesAnoReferencia;
	}

	public String getNomeGerenciaRegional(){

		return nomeGerenciaRegional;
	}

	public void setNomeGerenciaRegional(String nomeGerenciaRegional){

		this.nomeGerenciaRegional = nomeGerenciaRegional;
	}

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	public String getValorContaFaturadaResidencial(){

		return valorContaFaturadaResidencial;
	}

	public void setValorContaFaturadaResidencial(String valorContaFaturadaResidencial){

		this.valorContaFaturadaResidencial = valorContaFaturadaResidencial;
	}

	public String getValorContaIncluidaResidencial(){

		return valorContaIncluidaResidencial;
	}

	public void setValorContaIncluidaResidencial(String valorContaIncluidaResidencial){

		this.valorContaIncluidaResidencial = valorContaIncluidaResidencial;
	}

	public String getValorParcCobradoContaFatResidencial(){

		return valorParcCobradoContaFatResidencial;
	}

	public void setValorParcCobradoContaFatResidencial(String valorParcCobradoContaFatResidencial){

		this.valorParcCobradoContaFatResidencial = valorParcCobradoContaFatResidencial;
	}

	public String getValorContaCanceladaResidencial(){

		return valorContaCanceladaResidencial;
	}

	public void setValorContaCanceladaResidencial(String valorContaCanceladaResidencial){

		this.valorContaCanceladaResidencial = valorContaCanceladaResidencial;
	}

	public String getValorContaCancPorParcResidencial(){

		return valorContaCancPorParcResidencial;
	}

	public void setValorContaCancPorParcResidencial(String valorContaCancPorParcResidencial){

		this.valorContaCancPorParcResidencial = valorContaCancPorParcResidencial;
	}

	public String getValorArrecadavelResidencial(){

		return valorArrecadavelResidencial;
	}

	public void setValorArrecadavelResidencial(String valorArrecadavelResidencial){

		this.valorArrecadavelResidencial = valorArrecadavelResidencial;
	}

	public String getValorRecebimentoResidencial(){

		return valorRecebimentoResidencial;
	}

	public void setValorRecebimentoResidencial(String valorRecebimentoResidencial){

		this.valorRecebimentoResidencial = valorRecebimentoResidencial;
	}

	public String getValorPendenciaResidencial(){

		return valorPendenciaResidencial;
	}

	public void setValorPendenciaResidencial(String valorPendenciaResidencial){

		this.valorPendenciaResidencial = valorPendenciaResidencial;
	}

	public String getPercRecebimentosResidencial(){

		return percRecebimentosResidencial;
	}

	public void setPercRecebimentosResidencial(String percRecebimentosResidencial){

		this.percRecebimentosResidencial = percRecebimentosResidencial;
	}

	public String getPercRecebimentosEmDiaResidencial(){

		return percRecebimentosEmDiaResidencial;
	}

	public void setPercRecebimentosEmDiaResidencial(String percRecebimentosEmDiaResidencial){

		this.percRecebimentosEmDiaResidencial = percRecebimentosEmDiaResidencial;
	}

	public String getValorContaFaturadaComercial(){

		return valorContaFaturadaComercial;
	}

	public void setValorContaFaturadaComercial(String valorContaFaturadaComercial){

		this.valorContaFaturadaComercial = valorContaFaturadaComercial;
	}

	public String getValorContaIncluidaComercial(){

		return valorContaIncluidaComercial;
	}

	public void setValorContaIncluidaComercial(String valorContaIncluidaComercial){

		this.valorContaIncluidaComercial = valorContaIncluidaComercial;
	}

	public String getValorParcCobradoContaFatComercial(){

		return valorParcCobradoContaFatComercial;
	}

	public void setValorParcCobradoContaFatComercial(String valorParcCobradoContaFatComercial){

		this.valorParcCobradoContaFatComercial = valorParcCobradoContaFatComercial;
	}

	public String getValorContaCanceladaComercial(){

		return valorContaCanceladaComercial;
	}

	public void setValorContaCanceladaComercial(String valorContaCanceladaComercial){

		this.valorContaCanceladaComercial = valorContaCanceladaComercial;
	}

	public String getValorContaCancPorParcComercial(){

		return valorContaCancPorParcComercial;
	}

	public void setValorContaCancPorParcComercial(String valorContaCancPorParcComercial){

		this.valorContaCancPorParcComercial = valorContaCancPorParcComercial;
	}

	public String getValorArrecadavelComercial(){

		return valorArrecadavelComercial;
	}

	public void setValorArrecadavelComercial(String valorArrecadavelComercial){

		this.valorArrecadavelComercial = valorArrecadavelComercial;
	}

	public String getValorRecebimentoComercial(){

		return valorRecebimentoComercial;
	}

	public void setValorRecebimentoComercial(String valorRecebimentoComercial){

		this.valorRecebimentoComercial = valorRecebimentoComercial;
	}

	public String getValorPendenciaComercial(){

		return valorPendenciaComercial;
	}

	public void setValorPendenciaComercial(String valorPendenciaComercial){

		this.valorPendenciaComercial = valorPendenciaComercial;
	}

	public String getPercRecebimentosComercial(){

		return percRecebimentosComercial;
	}

	public void setPercRecebimentosComercial(String percRecebimentosComercial){

		this.percRecebimentosComercial = percRecebimentosComercial;
	}

	public String getPercRecebimentosEmDiaComercial(){

		return percRecebimentosEmDiaComercial;
	}

	public void setPercRecebimentosEmDiaComercial(String percRecebimentosEmDiaComercial){

		this.percRecebimentosEmDiaComercial = percRecebimentosEmDiaComercial;
	}

	public String getValorContaFaturadaIndustrial(){

		return valorContaFaturadaIndustrial;
	}

	public void setValorContaFaturadaIndustrial(String valorContaFaturadaIndustrial){

		this.valorContaFaturadaIndustrial = valorContaFaturadaIndustrial;
	}

	public String getValorContaIncluidaIndustrial(){

		return valorContaIncluidaIndustrial;
	}

	public void setValorContaIncluidaIndustrial(String valorContaIncluidaIndustrial){

		this.valorContaIncluidaIndustrial = valorContaIncluidaIndustrial;
	}

	public String getValorParcCobradoContaFatIndustrial(){

		return valorParcCobradoContaFatIndustrial;
	}

	public void setValorParcCobradoContaFatIndustrial(String valorParcCobradoContaFatIndustrial){

		this.valorParcCobradoContaFatIndustrial = valorParcCobradoContaFatIndustrial;
	}

	public String getValorContaCanceladaIndustrial(){

		return valorContaCanceladaIndustrial;
	}

	public void setValorContaCanceladaIndustrial(String valorContaCanceladaIndustrial){

		this.valorContaCanceladaIndustrial = valorContaCanceladaIndustrial;
	}

	public String getValorContaCancPorParcIndustrial(){

		return valorContaCancPorParcIndustrial;
	}

	public void setValorContaCancPorParcIndustrial(String valorContaCancPorParcIndustrial){

		this.valorContaCancPorParcIndustrial = valorContaCancPorParcIndustrial;
	}

	public String getValorArrecadavelIndustrial(){

		return valorArrecadavelIndustrial;
	}

	public void setValorArrecadavelIndustrial(String valorArrecadavelIndustrial){

		this.valorArrecadavelIndustrial = valorArrecadavelIndustrial;
	}

	public String getValorRecebimentoIndustrial(){

		return valorRecebimentoIndustrial;
	}

	public void setValorRecebimentoIndustrial(String valorRecebimentoIndustrial){

		this.valorRecebimentoIndustrial = valorRecebimentoIndustrial;
	}

	public String getValorPendenciaIndustrial(){

		return valorPendenciaIndustrial;
	}

	public void setValorPendenciaIndustrial(String valorPendenciaIndustrial){

		this.valorPendenciaIndustrial = valorPendenciaIndustrial;
	}

	public String getPercRecebimentosIndustrial(){

		return percRecebimentosIndustrial;
	}

	public void setPercRecebimentosIndustrial(String percRecebimentosIndustrial){

		this.percRecebimentosIndustrial = percRecebimentosIndustrial;
	}

	public String getPercRecebimentosEmDiaIndustrial(){

		return percRecebimentosEmDiaIndustrial;
	}

	public void setPercRecebimentosEmDiaIndustrial(String percRecebimentosEmDiaIndustrial){

		this.percRecebimentosEmDiaIndustrial = percRecebimentosEmDiaIndustrial;
	}

	public String getValorContaFaturadaPublico(){

		return valorContaFaturadaPublico;
	}

	public void setValorContaFaturadaPublico(String valorContaFaturadaPublico){

		this.valorContaFaturadaPublico = valorContaFaturadaPublico;
	}

	public String getValorContaIncluidaPublico(){

		return valorContaIncluidaPublico;
	}

	public void setValorContaIncluidaPublico(String valorContaIncluidaPublico){

		this.valorContaIncluidaPublico = valorContaIncluidaPublico;
	}

	public String getValorParcCobradoContaFatPublico(){

		return valorParcCobradoContaFatPublico;
	}

	public void setValorParcCobradoContaFatPublico(String valorParcCobradoContaFatPublico){

		this.valorParcCobradoContaFatPublico = valorParcCobradoContaFatPublico;
	}

	public String getValorContaCanceladaPublico(){

		return valorContaCanceladaPublico;
	}

	public void setValorContaCanceladaPublico(String valorContaCanceladaPublico){

		this.valorContaCanceladaPublico = valorContaCanceladaPublico;
	}

	public String getValorContaCancPorParcPublico(){

		return valorContaCancPorParcPublico;
	}

	public void setValorContaCancPorParcPublico(String valorContaCancPorParcPublico){

		this.valorContaCancPorParcPublico = valorContaCancPorParcPublico;
	}

	public String getValorArrecadavelPublico(){

		return valorArrecadavelPublico;
	}

	public void setValorArrecadavelPublico(String valorArrecadavelPublico){

		this.valorArrecadavelPublico = valorArrecadavelPublico;
	}

	public String getValorRecebimentoPublico(){

		return valorRecebimentoPublico;
	}

	public void setValorRecebimentoPublico(String valorRecebimentoPublico){

		this.valorRecebimentoPublico = valorRecebimentoPublico;
	}

	public String getValorPendenciaPublico(){

		return valorPendenciaPublico;
	}

	public void setValorPendenciaPublico(String valorPendenciaPublico){

		this.valorPendenciaPublico = valorPendenciaPublico;
	}

	public String getPercRecebimentosPublico(){

		return percRecebimentosPublico;
	}

	public void setPercRecebimentosPublico(String percRecebimentosPublico){

		this.percRecebimentosPublico = percRecebimentosPublico;
	}

	public String getPercRecebimentosEmDiaPublico(){

		return percRecebimentosEmDiaPublico;
	}

	public void setPercRecebimentosEmDiaPublico(String percRecebimentosEmDiaPublico){

		this.percRecebimentosEmDiaPublico = percRecebimentosEmDiaPublico;
	}

	public String getValorContaFaturadaTotal(){

		return valorContaFaturadaTotal;
	}

	public void setValorContaFaturadaTotal(String valorContaFaturadaTotal){

		this.valorContaFaturadaTotal = valorContaFaturadaTotal;
	}

	public String getValorContaIncluidaTotal(){

		return valorContaIncluidaTotal;
	}

	public void setValorContaIncluidaTotal(String valorContaIncluidaTotal){

		this.valorContaIncluidaTotal = valorContaIncluidaTotal;
	}

	public String getValorParcCobradoContaFatTotal(){

		return valorParcCobradoContaFatTotal;
	}

	public void setValorParcCobradoContaFatTotal(String valorParcCobradoContaFatTotal){

		this.valorParcCobradoContaFatTotal = valorParcCobradoContaFatTotal;
	}

	public String getValorContaCanceladaTotal(){

		return valorContaCanceladaTotal;
	}

	public void setValorContaCanceladaTotal(String valorContaCanceladaTotal){

		this.valorContaCanceladaTotal = valorContaCanceladaTotal;
	}

	public String getValorContaCancPorParcTotal(){

		return valorContaCancPorParcTotal;
	}

	public void setValorContaCancPorParcTotal(String valorContaCancPorParcTotal){

		this.valorContaCancPorParcTotal = valorContaCancPorParcTotal;
	}

	public String getValorArrecadavelTotal(){

		return valorArrecadavelTotal;
	}

	public void setValorArrecadavelTotal(String valorArrecadavelTotal){

		this.valorArrecadavelTotal = valorArrecadavelTotal;
	}

	public String getValorRecebimentoTotal(){

		return valorRecebimentoTotal;
	}

	public void setValorRecebimentoTotal(String valorRecebimentoTotal){

		this.valorRecebimentoTotal = valorRecebimentoTotal;
	}

	public String getValorPendenciaTotal(){

		return valorPendenciaTotal;
	}

	public void setValorPendenciaTotal(String valorPendenciaTotal){

		this.valorPendenciaTotal = valorPendenciaTotal;
	}

	public String getPercRecebimentosTotal(){

		return percRecebimentosTotal;
	}

	public void setPercRecebimentosTotal(String percRecebimentosTotal){

		this.percRecebimentosTotal = percRecebimentosTotal;
	}

	public String getPercRecebimentosEmDiaTotal(){

		return percRecebimentosEmDiaTotal;
	}

	public void setPercRecebimentosEmDiaTotal(String percRecebimentosEmDiaTotal){

		this.percRecebimentosEmDiaTotal = percRecebimentosEmDiaTotal;
	}


}
