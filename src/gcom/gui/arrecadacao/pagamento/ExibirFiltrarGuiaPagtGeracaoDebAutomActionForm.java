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

/**
 * [UC3046] Filtrar Guia Pagamento Para Gera��o D�bito Autom�tico
 * 
 * @author Carlos Chrystian
 * @created 15/03/2012
 *          Filtrar Guia Pagamento Para Gera��o D�bito Autom�tico.
 */

package gcom.gui.arrecadacao.pagamento;

import org.apache.struts.action.ActionForm;

public class ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String dataVencimentoGuiaPagamentoInicial;

	private String dataVencimentoGuiaPagamentoFinal;

	private String clienteResponsavel;

	private String nomeClienteResponsavel;

	private String indicadorTipoGuia;

	private String indicadorAtualizar;

	private String[] idRegistrosGeracaoDebitoAutomatico;

	public String getDataVencimentoGuiaPagamentoInicial(){

		return dataVencimentoGuiaPagamentoInicial;
	}

	public void setDataVencimentoGuiaPagamentoInicial(String dataVencimentoGuiaPagamentoInicial){

		this.dataVencimentoGuiaPagamentoInicial = dataVencimentoGuiaPagamentoInicial;
	}

	public String getDataVencimentoGuiaPagamentoFinal(){

		return dataVencimentoGuiaPagamentoFinal;
	}

	public void setDataVencimentoGuiaPagamentoFinal(String dataVencimentoGuiaPagamentoFinal){

		this.dataVencimentoGuiaPagamentoFinal = dataVencimentoGuiaPagamentoFinal;
	}

	public String getClienteResponsavel(){

		return clienteResponsavel;
	}

	public void setClienteResponsavel(String clienteResponsavel){

		this.clienteResponsavel = clienteResponsavel;
	}

	public String getNomeClienteResponsavel(){

		return nomeClienteResponsavel;
	}

	public void setNomeClienteResponsavel(String nomeClienteResponsavel){

		this.nomeClienteResponsavel = nomeClienteResponsavel;
	}

	public String getIndicadorTipoGuia(){

		return indicadorTipoGuia;
	}

	public void setIndicadorTipoGuia(String indicadorTipoGuia){

		this.indicadorTipoGuia = indicadorTipoGuia;
	}

	public String getIndicadorAtualizar(){

		return indicadorAtualizar;
	}

	public void setIndicadorAtualizar(String indicadorAtualizar){

		this.indicadorAtualizar = indicadorAtualizar;
	}

	public String[] getIdRegistrosGeracaoDebitoAutomatico(){

		return idRegistrosGeracaoDebitoAutomatico;
	}

	public void setIdRegistrosGeracaoDebitoAutomatico(String[] idRegistrosGeracaoDebitoAutomatico){

		this.idRegistrosGeracaoDebitoAutomatico = idRegistrosGeracaoDebitoAutomatico;
	}

	public void reset(){

		this.dataVencimentoGuiaPagamentoInicial = "";
		this.dataVencimentoGuiaPagamentoFinal = "";
		this.clienteResponsavel = "";
		this.nomeClienteResponsavel = "";
		this.indicadorTipoGuia = null;
		this.idRegistrosGeracaoDebitoAutomatico = null;
	}
}
