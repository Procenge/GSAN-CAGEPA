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

package gcom.gui.relatorio.atendimentopublico.registroatendimento;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * [UC0XXX] Relat�rio Gest�o Registro Atendimento
 * 
 * @author Anderson Italo
 * @date 25/03/2011
 */
public class GerarRelatorioGestaoRegistroAtendimentoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String situacao;

	private String[] tipoSolicitacao;

	private String[] especificacao;

	private String periodoAtendimentoInicial;

	private String periodoAtendimentoFinal;

	private String periodoEncerramentoInicial;

	private String periodoEncerramentoFinal;

	private String[] unidadeAtendimento;

	private String[] unidadeAtual;

	private String unidadeSuperiorId;

	private String unidadeSuperiorDescricao;

	private String tipoPesquisa;

	// Controle
	private String selectedTipoSolicitacaoSize = "0";

	private String situacaoId;

	public String getSelectedTipoSolicitacaoSize(){

		return selectedTipoSolicitacaoSize;
	}

	public void setSelectedTipoSolicitacaoSize(String selectedTipoSolicitacaoSize){

		this.selectedTipoSolicitacaoSize = selectedTipoSolicitacaoSize;
	}

	public void resetRA(){

		situacao = null;
		tipoSolicitacao = null;
		especificacao = null;
		periodoAtendimentoInicial = null;
		periodoAtendimentoFinal = null;
		periodoEncerramentoInicial = null;
		periodoEncerramentoFinal = null;
		unidadeAtendimento = null;
		unidadeAtual = null;
		unidadeSuperiorId = null;
		unidadeSuperiorDescricao = null;
		tipoPesquisa = null;
	}

	public String getPeriodoAtendimentoFinal(){

		return periodoAtendimentoFinal;
	}

	public void setPeriodoAtendimentoFinal(String periodoAtendimentoFinal){

		this.periodoAtendimentoFinal = periodoAtendimentoFinal;
	}

	public String getPeriodoAtendimentoInicial(){

		return periodoAtendimentoInicial;
	}

	public void setPeriodoAtendimentoInicial(String periodoAtendimentoInicial){

		this.periodoAtendimentoInicial = periodoAtendimentoInicial;
	}

	public String getPeriodoEncerramentoFinal(){

		return periodoEncerramentoFinal;
	}

	public void setPeriodoEncerramentoFinal(String periodoEncerramentoFinal){

		this.periodoEncerramentoFinal = periodoEncerramentoFinal;
	}

	public String getPeriodoEncerramentoInicial(){

		return periodoEncerramentoInicial;
	}

	public void setPeriodoEncerramentoInicial(String periodoEncerramentoInicial){

		this.periodoEncerramentoInicial = periodoEncerramentoInicial;
	}

	public String getSituacao(){

		return situacao;
	}

	public void setSituacao(String situacao){

		this.situacao = situacao;
	}

	public String getUnidadeSuperiorDescricao(){

		return unidadeSuperiorDescricao;
	}

	public void setUnidadeSuperiorDescricao(String unidadeSuperiorDescricao){

		this.unidadeSuperiorDescricao = unidadeSuperiorDescricao;
	}

	public String getUnidadeSuperiorId(){

		return unidadeSuperiorId;
	}

	public void setUnidadeSuperiorId(String unidadeSuperiorId){

		this.unidadeSuperiorId = unidadeSuperiorId;
	}

	public String[] getEspecificacao(){

		return especificacao;
	}

	public void setEspecificacao(String[] especificacao){

		this.especificacao = especificacao;
	}

	public String[] getTipoSolicitacao(){

		return tipoSolicitacao;
	}

	public void setTipoSolicitacao(String[] tipoSolicitacao){

		this.tipoSolicitacao = tipoSolicitacao;
	}

	public String getSituacaoId(){

		return situacaoId;
	}

	public void setSituacaoId(String situacaoId){

		this.situacaoId = situacaoId;
	}

	public String getTipoPesquisa(){

		return tipoPesquisa;
	}

	public void setTipoPesquisa(String tipoPesquisa){

		this.tipoPesquisa = tipoPesquisa;
	}

	public String[] getUnidadeAtendimento(){

		return unidadeAtendimento;
	}

	public void setUnidadeAtendimento(String[] unidadeAtendimento){

		this.unidadeAtendimento = unidadeAtendimento;
	}

	public String[] getUnidadeAtual(){

		return unidadeAtual;
	}

	public void setUnidadeAtual(String[] unidadeAtual){

		this.unidadeAtual = unidadeAtual;
	}
}
