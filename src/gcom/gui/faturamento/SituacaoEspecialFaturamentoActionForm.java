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

package gcom.gui.faturamento;

import gcom.faturamento.bean.SituacaoEspecialFaturamentoHelper;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.struts.action.ActionForm;

public class SituacaoEspecialFaturamentoActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String idFaturamentoSituacaoTipo;

	private String idFaturamentoSituacaoMotivo;

	private String tipoSituacaoEspecialFaturamento;

	private String motivoSituacaoEspecialFaturamento;

	private String mesAnoReferenciaFaturamentoInicial;

	private String mesAnoReferenciaFaturamentoFinal;

	private String opcaoEscolhida;

	private String volume;

	private String percentualEsgoto;

	private Collection<SituacaoEspecialFaturamentoHelper> colecaoSituacaoEspecialFaturamentoHelper = new ArrayList<SituacaoEspecialFaturamentoHelper>();

	private Collection<SituacaoEspecialFaturamentoHelper> colecaoSituacaoEspecialFaturamentoHelperRemovidas = new ArrayList<SituacaoEspecialFaturamentoHelper>();

	private String[] ItensSelecionados = {};

	// @Override
	// public void reset(ActionMapping arg0, HttpServletRequest arg1) {
	// idFaturamentoSituacaoTipo = null;
	// idFaturamentoSituacaoMotivo = null;
	// tipoSituacaoEspecialFaturamento = null;
	// motivoSituacaoEspecialFaturamento = null;
	// mesAnoReferenciaFaturamentoInicial = null;
	// mesAnoReferenciaFaturamentoFinal = null;
	// volume = null;
	// percentualEsgoto = null;
	// colecaoSituacaoEspecialFaturamentoHelper = new
	// ArrayList<SituacaoEspecialFaturamentoHelper>();
	// colecaoSituacaoEspecialFaturamentoHelperRemovidas = new
	// ArrayList<SituacaoEspecialFaturamentoHelper>();
	// }

	public String getMesAnoReferenciaFaturamentoFinal(){

		return mesAnoReferenciaFaturamentoFinal;
	}

	public void setMesAnoReferenciaFaturamentoFinal(String mesAnoReferenciaFaturamentoFinal){

		this.mesAnoReferenciaFaturamentoFinal = mesAnoReferenciaFaturamentoFinal;
	}

	public String getMesAnoReferenciaFaturamentoInicial(){

		return mesAnoReferenciaFaturamentoInicial;
	}

	public void setMesAnoReferenciaFaturamentoInicial(String mesAnoReferenciaFaturamentoInicial){

		this.mesAnoReferenciaFaturamentoInicial = mesAnoReferenciaFaturamentoInicial;
	}

	public String getMotivoSituacaoEspecialFaturamento(){

		return motivoSituacaoEspecialFaturamento;
	}

	public void setMotivoSituacaoEspecialFaturamento(String motivoSituacaoEspecialFaturamento){

		this.motivoSituacaoEspecialFaturamento = motivoSituacaoEspecialFaturamento;
	}

	public String getTipoSituacaoEspecialFaturamento(){

		return tipoSituacaoEspecialFaturamento;
	}

	public void setTipoSituacaoEspecialFaturamento(String tipoSituacaoEspecialFaturamento){

		this.tipoSituacaoEspecialFaturamento = tipoSituacaoEspecialFaturamento;
	}

	public String getVolume(){

		return volume;
	}

	public void setVolume(String volume){

		this.volume = volume;
	}

	public String getPercentualEsgoto(){

		return percentualEsgoto;
	}

	public void setPercentualEsgoto(String percentualEsgoto){

		this.percentualEsgoto = percentualEsgoto;
	}

	public String getIdFaturamentoSituacaoTipo(){

		return idFaturamentoSituacaoTipo;
	}

	public void setIdFaturamentoSituacaoTipo(String idFaturamentoSituacaoTipo){

		this.idFaturamentoSituacaoTipo = idFaturamentoSituacaoTipo;
	}

	public String getIdFaturamentoSituacaoMotivo(){

		return idFaturamentoSituacaoMotivo;
	}

	public void setIdFaturamentoSituacaoMotivo(String idFaturamentoSituacaoMotivo){

		this.idFaturamentoSituacaoMotivo = idFaturamentoSituacaoMotivo;
	}

	public Collection<SituacaoEspecialFaturamentoHelper> getColecaoSituacaoEspecialFaturamentoHelper(){

		return colecaoSituacaoEspecialFaturamentoHelper;
	}

	public void setColecaoSituacaoEspecialFaturamentoHelper(
					Collection<SituacaoEspecialFaturamentoHelper> colecaoSituacaoEspecialFaturamentoHelper){

		this.colecaoSituacaoEspecialFaturamentoHelper = colecaoSituacaoEspecialFaturamentoHelper;
	}

	public Collection<SituacaoEspecialFaturamentoHelper> getColecaoSituacaoEspecialFaturamentoHelperRemovidas(){

		return colecaoSituacaoEspecialFaturamentoHelperRemovidas;
	}

	public void setColecaoSituacaoEspecialFaturamentoHelperRemovidas(
					Collection<SituacaoEspecialFaturamentoHelper> colecaoSituacaoEspecialFaturamentoHelperRemovidas){

		this.colecaoSituacaoEspecialFaturamentoHelperRemovidas = colecaoSituacaoEspecialFaturamentoHelperRemovidas;
	}

	public String getOpcaoEscolhida(){

		return opcaoEscolhida;
	}

	public void setOpcaoEscolhida(String opcaoEscolhida){

		this.opcaoEscolhida = opcaoEscolhida;
	}

	public String[] getItensSelecionados(){

		return ItensSelecionados;
	}

	public void setItensSelecionados(String[] ItensSelecionados){

		this.ItensSelecionados = ItensSelecionados;
	}
}
