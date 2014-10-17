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
 * Ivan S�rgio Virginio da Silva J�nior
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

package gcom.gui.atendimentopublico.ordemservico;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * [UC3136] Gerar Relatorio Estat�stico de Atendimento Por Ra�a/Cor.
 * 
 * @author Hiroshi Gon�alves
 * @date 13/02/2014
 */
public class GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String periodoAtendimentoInicial;

	private String periodoAtendimentoFinal;

	private String unidadeAtendimento;

	private String dsUnidadeAtendimento;

	private String[] racaCor;

	private Integer[] racaCorSelecionadas;

	private Integer[] tipoSolicitacao;

	private Integer[] especificacao;

	private String gerenciaRegional;

	private String localidade;

	private String dsLocalidade;

	private String selectedTipoSolicitacaoSize = "0";


	public String getPeriodoAtendimentoInicial(){

		return periodoAtendimentoInicial;
	}


	public void setPeriodoAtendimentoInicial(String periodoAtendimentoInicial){

		this.periodoAtendimentoInicial = periodoAtendimentoInicial;
	}


	public String getPeriodoAtendimentoFinal(){

		return periodoAtendimentoFinal;
	}


	public void setPeriodoAtendimentoFinal(String periodoAtendimentoFinal){

		this.periodoAtendimentoFinal = periodoAtendimentoFinal;
	}


	public String getUnidadeAtendimento(){

		return unidadeAtendimento;
	}


	public void setUnidadeAtendimento(String unidadeAtendimento){

		this.unidadeAtendimento = unidadeAtendimento;
	}


	public String getDsUnidadeAtendimento(){

		return dsUnidadeAtendimento;
	}

	public void setDsUnidadeAtendimento(String dsUnidadeAtendimento){

		this.dsUnidadeAtendimento = dsUnidadeAtendimento;
	}

	public String[] getRacaCor(){

		return racaCor;
	}


	public void setRacaCor(String[] racaCor){

		this.racaCor = racaCor;
	}


	public Integer[] getRacaCorSelecionadas(){

		return racaCorSelecionadas;
	}

	public void setRacaCorSelecionadas(Integer[] racaCorSelecionadas){

		this.racaCorSelecionadas = racaCorSelecionadas;
	}

	public Integer[] getTipoSolicitacao(){

		return tipoSolicitacao;
	}


	public void setTipoSolicitacao(Integer[] tipoSolicitacao){

		this.tipoSolicitacao = tipoSolicitacao;
	}


	public Integer[] getEspecificacao(){

		return especificacao;
	}


	public void setEspecificacao(Integer[] especificacao){

		this.especificacao = especificacao;
	}


	public String getSelectedTipoSolicitacaoSize(){

		return selectedTipoSolicitacaoSize;
	}

	public void setSelectedTipoSolicitacaoSize(String selectedTipoSolicitacaoSize){

		this.selectedTipoSolicitacaoSize = selectedTipoSolicitacaoSize;
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

	public String getDsLocalidade(){

		return dsLocalidade;
	}

	public void setDsLocalidade(String dsLocalidade){

		this.dsLocalidade = dsLocalidade;
	}

}