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

package gcom.gui.relatorio.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Filtrar Registro Atendimento
 * 
 * @author Luciano Galvao
 * @date 30/08/2012
 */
public class GerarRelatorioEstatisticoRegistroAtendimentoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String[] tipoSolicitacao;

	private String[] especificacao;

	private String periodoAtendimentoInicial;

	private String periodoAtendimentoFinal;

	private String unidadeAtendimentoId;

	private String unidadeAtendimentoDescricao;

	private String unidadeAtualId;

	private String unidadeAtualDescricao;

	private String unidadeSuperiorId;

	private String unidadeSuperiorDescricao;

	private String loginUsuario;

	private String situacao;

	private String nomeUsuario;

	// Controle
	private String validaUnidadeAtendimento = "false";

	private String validaUnidadeAtual = "false";

	private String validaUnidadeSuperior = "false";

	private String selectedTipoSolicitacaoSize = "0";

	private RegistroAtendimento ra = new RegistroAtendimento();

	public RegistroAtendimento getRa(){

		return ra;
	}

	public void setRa(RegistroAtendimento ra){

		this.ra = ra;
	}

	public String getSelectedTipoSolicitacaoSize(){

		return selectedTipoSolicitacaoSize;
	}

	public void setSelectedTipoSolicitacaoSize(String selectedTipoSolicitacaoSize){

		this.selectedTipoSolicitacaoSize = selectedTipoSolicitacaoSize;
	}

	public void resetRA(){

		tipoSolicitacao = null;
		especificacao = null;
		periodoAtendimentoInicial = null;
		periodoAtendimentoFinal = null;
		unidadeAtendimentoId = null;
		unidadeAtendimentoDescricao = null;
		unidadeAtualId = null;
		unidadeAtualDescricao = null;
		unidadeSuperiorId = null;
		unidadeSuperiorDescricao = null;
		loginUsuario = null;
		nomeUsuario = null;
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

	public String getUnidadeAtendimentoDescricao(){

		return unidadeAtendimentoDescricao;
	}

	public void setUnidadeAtendimentoDescricao(String unidadeAtendimentoDescricao){

		this.unidadeAtendimentoDescricao = unidadeAtendimentoDescricao;
	}

	public String getUnidadeAtendimentoId(){

		return unidadeAtendimentoId;
	}

	public void setUnidadeAtendimentoId(String unidadeAtendimentoId){

		this.unidadeAtendimentoId = unidadeAtendimentoId;
	}

	public String getUnidadeAtualDescricao(){

		return unidadeAtualDescricao;
	}

	public void setUnidadeAtualDescricao(String unidadeAtualDescricao){

		this.unidadeAtualDescricao = unidadeAtualDescricao;
	}

	public String getUnidadeAtualId(){

		return unidadeAtualId;
	}

	public void setUnidadeAtualId(String unidadeAtualId){

		this.unidadeAtualId = unidadeAtualId;
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

	public String getValidaUnidadeAtendimento(){

		return validaUnidadeAtendimento;
	}

	public void setValidaUnidadeAtendimento(String validaUnidadeAtendimento){

		this.validaUnidadeAtendimento = validaUnidadeAtendimento;
	}

	public String getValidaUnidadeAtual(){

		return validaUnidadeAtual;
	}

	public void setValidaUnidadeAtual(String validaUnidadeAtual){

		this.validaUnidadeAtual = validaUnidadeAtual;
	}

	public String getValidaUnidadeSuperior(){

		return validaUnidadeSuperior;
	}

	public void setValidaUnidadeSuperior(String validaUnidadeSuperior){

		this.validaUnidadeSuperior = validaUnidadeSuperior;
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

	public String getLoginUsuario(){

		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario){

		this.loginUsuario = loginUsuario;
	}

	public String getNomeUsuario(){

		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario){

		this.nomeUsuario = nomeUsuario;
	}

	public static long getSerialversionuid(){

		return serialVersionUID;
	}

	public void setSituacao(String situacao){

		this.situacao = situacao;
	}

	public String getSituacao(){

		return situacao;
	}

}