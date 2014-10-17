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