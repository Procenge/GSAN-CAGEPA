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

public class RelatorioResumoFaturamentoSituacaoEspecialBean
				implements RelatorioBean {

	private String faturamentoEstimadoGeral;

	private String faturamentoEstimadoGerencia;

	private String faturamentoEstimadoLocalidade;

	private String faturamentoEstimadoMotivo;

	private String faturamentoEstimadoSituacao;

	private String gerenciaRegional;

	private String localidade;

	private String mesAnoFim;

	private String mesAnoInicio;

	private String motivo;

	private String percentualGeral;

	private String percentualGerencia;

	private String percentualLocalidade;

	private String percentualMotivo;

	private String percentualSituacao;

	private String qtdeImovelGeral;

	private String qtdeImovelGerencia;

	private String qtdeImovelLocalidade;

	private String qtdeImovelMotivo;

	private String qtdeImovelSituacao;

	private String qtdeParalisadaGeral;

	private String qtdeParalisadaGerencia;

	private String qtdeParalisadaMotivo;

	private String qtdeParalisadaSituacao;

	private String qtdeParalisadaLocalidade;

	private String situacao;

	private String faturamentoEstimadoUnidadeNegocio;

	private String unidadeNegocio;

	private String percentualUnidadeNegocio;

	private String qtdeImovelUnidadeNegocio;

	private String qtdeParalisadaUnidadeNegocio;

	public RelatorioResumoFaturamentoSituacaoEspecialBean(String faturamentoEstimadoGeral, String faturamentoEstimadoGerencia,
															String faturamentoEstimadoLocalidade, String faturamentoEstimadoMotivo,
															String faturamentoEstimadoSituacao, String gerenciaRegional, String localidade,
															String mesAnoFim, String mesAnoInicio, String motivo, String percentualGeral,
															String percentualGerencia, String percentualLocalidade,
															String percentualMotivo, String percentualSituacao, String qtdeImovelGeral,
															String qtdeImovelGerencia, String qtdeImovelLocalidade,
															String qtdeImovelMotivo, String qtdeImovelSituacao, String qtdeParalisadaGeral,
															String qtdeParalisadaGerencia, String qtdeParalisadaMotivo,
															String qtdeParalisadaSituacao, String qtdeParalisadaLocalidade,
															String situacao, String faturamentoEstimadoUnidadeNegocio,
															String unidadeNegocio, String percentualUnidadeNegocio,
															String qtdeImovelUnidadeNegocio, String qtdeParalisadaUnidadeNegocio) {

		this.faturamentoEstimadoGeral = faturamentoEstimadoGeral;
		this.faturamentoEstimadoGerencia = faturamentoEstimadoGerencia;
		this.faturamentoEstimadoLocalidade = faturamentoEstimadoLocalidade;
		this.faturamentoEstimadoMotivo = faturamentoEstimadoMotivo;
		this.faturamentoEstimadoSituacao = faturamentoEstimadoSituacao;
		this.gerenciaRegional = gerenciaRegional;
		this.localidade = localidade;
		this.mesAnoFim = mesAnoFim;
		this.mesAnoInicio = mesAnoInicio;
		this.motivo = motivo;
		this.percentualGeral = percentualGeral;
		this.percentualGerencia = percentualGerencia;
		this.percentualLocalidade = percentualLocalidade;
		this.percentualMotivo = percentualMotivo;
		this.percentualSituacao = percentualSituacao;
		this.qtdeImovelGeral = qtdeImovelGeral;
		this.qtdeImovelGerencia = qtdeImovelGerencia;
		this.qtdeImovelLocalidade = qtdeImovelLocalidade;
		this.qtdeImovelMotivo = qtdeImovelMotivo;
		this.qtdeImovelSituacao = qtdeImovelSituacao;
		this.qtdeParalisadaGeral = qtdeParalisadaGeral;
		this.qtdeParalisadaGerencia = qtdeParalisadaGerencia;
		this.qtdeParalisadaMotivo = qtdeParalisadaMotivo;
		this.qtdeParalisadaSituacao = qtdeParalisadaSituacao;
		this.qtdeParalisadaLocalidade = qtdeParalisadaLocalidade;
		this.situacao = situacao;
		this.faturamentoEstimadoUnidadeNegocio = faturamentoEstimadoUnidadeNegocio;
		this.unidadeNegocio = unidadeNegocio;
		this.percentualUnidadeNegocio = percentualUnidadeNegocio;
		this.qtdeImovelUnidadeNegocio = qtdeImovelUnidadeNegocio;
		this.qtdeParalisadaUnidadeNegocio = qtdeParalisadaUnidadeNegocio;
	}

	public String getFaturamentoEstimadoGeral(){

		return faturamentoEstimadoGeral;
	}

	public void setFaturamentoEstimadoGeral(String faturamentoEstimadoGeral){

		this.faturamentoEstimadoGeral = faturamentoEstimadoGeral;
	}

	public String getFaturamentoEstimadoGerencia(){

		return faturamentoEstimadoGerencia;
	}

	public void setFaturamentoEstimadoGerencia(String faturamentoEstimadoGerencia){

		this.faturamentoEstimadoGerencia = faturamentoEstimadoGerencia;
	}

	public String getFaturamentoEstimadoLocalidade(){

		return faturamentoEstimadoLocalidade;
	}

	public void setFaturamentoEstimadoLocalidade(String faturamentoEstimadoLocalidade){

		this.faturamentoEstimadoLocalidade = faturamentoEstimadoLocalidade;
	}

	public String getFaturamentoEstimadoMotivo(){

		return faturamentoEstimadoMotivo;
	}

	public void setFaturamentoEstimadoMotivo(String faturamentoEstimadoMotivo){

		this.faturamentoEstimadoMotivo = faturamentoEstimadoMotivo;
	}

	public String getFaturamentoEstimadoSituacao(){

		return faturamentoEstimadoSituacao;
	}

	public void setFaturamentoEstimadoSituacao(String faturamentoEstimadoSituacao){

		this.faturamentoEstimadoSituacao = faturamentoEstimadoSituacao;
	}

	public String getGerenciaRegional(){

		return gerenciaRegional;
	}

	public void setGerenciaRegional(String gerenciaRegional){

		this.gerenciaRegional = gerenciaRegional;
	}

	public String getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

	public String getMesAnoFim(){

		return mesAnoFim;
	}

	public void setMesAnoFim(String mesAnoFim){

		this.mesAnoFim = mesAnoFim;
	}

	public String getMesAnoInicio(){

		return mesAnoInicio;
	}

	public void setMesAnoInicio(String mesAnoInicio){

		this.mesAnoInicio = mesAnoInicio;
	}

	public String getMotivo(){

		return motivo;
	}

	public void setMotivo(String motivo){

		this.motivo = motivo;
	}

	public String getPercentualGeral(){

		return percentualGeral;
	}

	public void setPercentualGeral(String percentualGeral){

		this.percentualGeral = percentualGeral;
	}

	public String getPercentualGerencia(){

		return percentualGerencia;
	}

	public void setPercentualGerencia(String percentualGerencia){

		this.percentualGerencia = percentualGerencia;
	}

	public String getPercentualLocalidade(){

		return percentualLocalidade;
	}

	public void setPercentualLocalidade(String percentualLocalidade){

		this.percentualLocalidade = percentualLocalidade;
	}

	public String getPercentualMotivo(){

		return percentualMotivo;
	}

	public void setPercentualMotivo(String percentualMotivo){

		this.percentualMotivo = percentualMotivo;
	}

	public String getPercentualSituacao(){

		return percentualSituacao;
	}

	public void setPercentualSituacao(String percentualSituacao){

		this.percentualSituacao = percentualSituacao;
	}

	public String getQtdeImovelGeral(){

		return qtdeImovelGeral;
	}

	public void setQtdeImovelGeral(String qtdeImovelGeral){

		this.qtdeImovelGeral = qtdeImovelGeral;
	}

	public String getQtdeImovelGerencia(){

		return qtdeImovelGerencia;
	}

	public void setQtdeImovelGerencia(String qtdeImovelGerencia){

		this.qtdeImovelGerencia = qtdeImovelGerencia;
	}

	public String getQtdeImovelLocalidade(){

		return qtdeImovelLocalidade;
	}

	public void setQtdeImovelLocalidade(String qtdeImovelLocalidade){

		this.qtdeImovelLocalidade = qtdeImovelLocalidade;
	}

	public String getQtdeImovelMotivo(){

		return qtdeImovelMotivo;
	}

	public void setQtdeImovelMotivo(String qtdeImovelMotivo){

		this.qtdeImovelMotivo = qtdeImovelMotivo;
	}

	public String getQtdeImovelSituacao(){

		return qtdeImovelSituacao;
	}

	public void setQtdeImovelSituacao(String qtdeImovelSituacao){

		this.qtdeImovelSituacao = qtdeImovelSituacao;
	}

	public String getQtdeParalisadaGeral(){

		return qtdeParalisadaGeral;
	}

	public void setQtdeParalisadaGeral(String qtdeParalisadaGeral){

		this.qtdeParalisadaGeral = qtdeParalisadaGeral;
	}

	public String getQtdeParalisadaGerencia(){

		return qtdeParalisadaGerencia;
	}

	public void setQtdeParalisadaGerencia(String qtdeParalisadaGerencia){

		this.qtdeParalisadaGerencia = qtdeParalisadaGerencia;
	}

	public String getQtdeParalisadaMotivo(){

		return qtdeParalisadaMotivo;
	}

	public void setQtdeParalisadaMotivo(String qtdeParalisadaMotivo){

		this.qtdeParalisadaMotivo = qtdeParalisadaMotivo;
	}

	public String getQtdeParalisadaSituacao(){

		return qtdeParalisadaSituacao;
	}

	public void setQtdeParalisadaSituacao(String qtdeParalisadaSituacao){

		this.qtdeParalisadaSituacao = qtdeParalisadaSituacao;
	}

	public String getSituacao(){

		return situacao;
	}

	public void setSituacao(String situacao){

		this.situacao = situacao;
	}

	public String getQtdeParalisadaLocalidade(){

		return qtdeParalisadaLocalidade;
	}

	public void setQtdeParalisadaLocalidade(String qtdeParalisadaLocalidade){

		this.qtdeParalisadaLocalidade = qtdeParalisadaLocalidade;
	}

	/**
	 * @return the faturamentoEstimadoUnidadeNegocio
	 */
	public String getFaturamentoEstimadoUnidadeNegocio(){

		return faturamentoEstimadoUnidadeNegocio;
	}

	/**
	 * @param faturamentoEstimadoUnidadeNegocio the faturamentoEstimadoUnidadeNegocio to set
	 */
	public void setFaturamentoEstimadoUnidadeNegocio(String faturamentoEstimadoUnidadeNegocio){

		this.faturamentoEstimadoUnidadeNegocio = faturamentoEstimadoUnidadeNegocio;
	}

	/**
	 * @return the unidadeNegocio
	 */
	public String getUnidadeNegocio(){

		return unidadeNegocio;
	}

	/**
	 * @param unidadeNegocio the unidadeNegocio to set
	 */
	public void setUnidadeNegocio(String unidadeNegocio){

		this.unidadeNegocio = unidadeNegocio;
	}

	/**
	 * @return the percentualUnidadeNegocio
	 */
	public String getPercentualUnidadeNegocio(){

		return percentualUnidadeNegocio;
	}

	/**
	 * @param percentualUnidadeNegocio the percentualUnidadeNegocio to set
	 */
	public void setPercentualUnidadeNegocio(String percentualUnidadeNegocio){

		this.percentualUnidadeNegocio = percentualUnidadeNegocio;
	}

	/**
	 * @return the qtdeImovelUnidadeNegocio
	 */
	public String getQtdeImovelUnidadeNegocio(){

		return qtdeImovelUnidadeNegocio;
	}

	/**
	 * @param qtdeImovelUnidadeNegocio the qtdeImovelUnidadeNegocio to set
	 */
	public void setQtdeImovelUnidadeNegocio(String qtdeImovelUnidadeNegocio){

		this.qtdeImovelUnidadeNegocio = qtdeImovelUnidadeNegocio;
	}

	/**
	 * @return the qtdeParalisadaUnidadeNegocio
	 */
	public String getQtdeParalisadaUnidadeNegocio(){

		return qtdeParalisadaUnidadeNegocio;
	}

	/**
	 * @param qtdeParalisadaUnidadeNegocio the qtdeParalisadaUnidadeNegocio to set
	 */
	public void setQtdeParalisadaUnidadeNegocio(String qtdeParalisadaUnidadeNegocio){

		this.qtdeParalisadaUnidadeNegocio = qtdeParalisadaUnidadeNegocio;
	}

}
