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

package gcom.gui.relatorio.cobranca;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Filtrar Registro Atendimento
 * 
 * @author Luciano Galvao
 * @date 30/08/2012
 */
public class GerarRelatorioRemuneracaoCobrancaAdministrativaActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	// MODELO 1
	private String[] localidades;

	private String[] setoresComerciais;

	private String periodoPagamentoInicial;

	private String periodoPagamentoFinal;

	private String selectedLocalidadesSize = "0";

	private String situacaoRemuneracao;

	private String indicadorConfirmaPagamento;

	// MODELO 2
	private String tipoRelatorio;

	private String referenciaInicial;

	private String referenciaFinal;

	private Integer[] idEmpresaCobrancaAdministrativa;

	private String modeloRelatorio;


	public String getModeloRelatorio(){

		return modeloRelatorio;
	}

	public void setModeloRelatorio(String modeloRelatorio){

		this.modeloRelatorio = modeloRelatorio;
	}

	public static long getSerialversionuid(){

		return serialVersionUID;
	}


	public String[] getLocalidades(){

		return localidades;
	}


	public void setLocalidades(String[] localidades){

		this.localidades = localidades;
	}


	public String[] getSetoresComerciais(){

		return setoresComerciais;
	}


	public void setSetoresComerciais(String[] setoresComerciais){

		this.setoresComerciais = setoresComerciais;
	}


	public String getPeriodoPagamentoInicial(){

		return periodoPagamentoInicial;
	}


	public void setPeriodoPagamentoInicial(String periodoPagamentoInicial){

		this.periodoPagamentoInicial = periodoPagamentoInicial;
	}


	public String getPeriodoPagamentoFinal(){

		return periodoPagamentoFinal;
	}


	public void setPeriodoPagamentoFinal(String periodoPagamentoFinal){

		this.periodoPagamentoFinal = periodoPagamentoFinal;
	}


	public String getSelectedLocalidadesSize(){

		return selectedLocalidadesSize;
	}


	public void setSelectedLocalidadesSize(String selectedLocalidadesSize){

		this.selectedLocalidadesSize = selectedLocalidadesSize;
	}


	public String getSituacaoRemuneracao(){

		return situacaoRemuneracao;
	}

	public void setSituacaoRemuneracao(String situacaoRemuneracao){

		this.situacaoRemuneracao = situacaoRemuneracao;
	}

	public String getIndicadorConfirmaPagamento(){

		return indicadorConfirmaPagamento;
	}

	public void setIndicadorConfirmaPagamento(String indicadorConfirmaPagamento){

		this.indicadorConfirmaPagamento = indicadorConfirmaPagamento;
	}

	public final String getTipoRelatorio(){

		return tipoRelatorio;
	}

	public final void setTipoRelatorio(String tipoRelatorio){

		this.tipoRelatorio = tipoRelatorio;
	}

	public final String getReferenciaInicial(){

		return referenciaInicial;
	}

	public final void setReferenciaInicial(String referenciaInicial){

		this.referenciaInicial = referenciaInicial;
	}

	public final String getReferenciaFinal(){

		return referenciaFinal;
	}

	public final void setReferenciaFinal(String referenciaFinal){

		this.referenciaFinal = referenciaFinal;
	}

	public Integer[] getIdEmpresaCobrancaAdministrativa(){

		return idEmpresaCobrancaAdministrativa;
	}

	public void setIdEmpresaCobrancaAdministrativa(Integer[] idEmpresaCobrancaAdministrativa){

		this.idEmpresaCobrancaAdministrativa = idEmpresaCobrancaAdministrativa;
	}

}