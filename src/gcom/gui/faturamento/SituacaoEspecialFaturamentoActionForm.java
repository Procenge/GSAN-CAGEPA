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
