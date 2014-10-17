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

package gcom.gui.atendimentopublico.registroatendimento;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Filtrar Registro Atendimento
 * 
 * @author Leonardo Regis
 * @author eduardo henrique
 * @date 10/03/2009 - Inclus�o de filtros de Datas de Previs�o Inicial e Final de Atendimento
 */
public class FiltrarRegistroAtendimentoIncompletoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String numeroRA;

	private String codigoDDD;

	private String foneChamada;

	private String nomeContato;

	private String tipoPesquisa;

	private String indicadorRetornoChamada;

	private String[] motivoAtendimentoIncompleto;

	private String[] tipoSolicitacao;

	private String[] especificacao;

	private String cliente;

	private String clientedescricao;

	private String matriculaImovel;

	private String inscricaoImovel;

	private String unidadeAtendimento;

	private String unidadeAtendimentoDescricao;

	private String usuarioAtendimento;

	private String usuarioAtendimentoDescricao;

	private String unidadeRetornoChamada;

	private String unidadeRetornoChamadaDescricao;

	private String usuarioRetornoChamada;

	private String usuarioRetornoChamadaDescricao;

	private String periodoAtendimentoInicial;

	private String periodoAtendimentoFinal;

	private String RADefinitivo;

	private String usuarioNaoEncontrado;

	private String descricaoRA;

	private String selectedTipoSolicitacaoSize = "0";

	public String getDescricaoRA(){

		return descricaoRA;
	}

	public void setDescricaoRA(String descricaoRA){

		this.descricaoRA = descricaoRA;
	}

	public String getUsuarioNaoEncontrado(){

		return usuarioNaoEncontrado;
	}

	public void setUsuarioNaoEncontrado(String usuarioNaoEncontrado){

		this.usuarioNaoEncontrado = usuarioNaoEncontrado;
	}

	public String getRADefinitivo(){

		return RADefinitivo;
	}

	public void setRADefinitivo(String definitivo){

		RADefinitivo = definitivo;
	}

	public String getTipoPesquisa(){

		return tipoPesquisa;
	}

	public void setTipoPesquisa(String tipoPesquisa){

		this.tipoPesquisa = tipoPesquisa;
	}

	public void resetRA(){

		numeroRA = null;
		matriculaImovel = null;
		inscricaoImovel = null;
		tipoSolicitacao = null;
		especificacao = null;
		periodoAtendimentoInicial = null;
		periodoAtendimentoFinal = null;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
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

	public String getNumeroRA(){

		return numeroRA;
	}

	public void setNumeroRA(String numeroRA){

		this.numeroRA = numeroRA;
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

	public String getCodigoDDD(){

		return codigoDDD;
	}

	public void setCodigoDDD(String codigoDDD){

		this.codigoDDD = codigoDDD;
	}

	public String getFoneChamada(){

		return foneChamada;
	}

	public void setFoneChamada(String foneChamada){

		this.foneChamada = foneChamada;
	}

	public String getNomeContato(){

		return nomeContato;
	}

	public void setNomeContato(String nomeContato){

		this.nomeContato = nomeContato;
	}

	public String getIndicadorRetornoChamada(){

		return indicadorRetornoChamada;
	}

	public void setIndicadorRetornoChamada(String indicadorRetornoChamada){

		this.indicadorRetornoChamada = indicadorRetornoChamada;
	}

	public String[] getMotivoAtendimentoIncompleto(){

		return motivoAtendimentoIncompleto;
	}

	public String getCliente(){

		return cliente;
	}

	public void setCliente(String cliente){

		this.cliente = cliente;
	}

	public String getUnidadeAtendimento(){

		return unidadeAtendimento;
	}

	public void setUnidadeAtendimento(String unidadeAtendimento){

		this.unidadeAtendimento = unidadeAtendimento;
	}

	public String getUsuarioAtendimento(){

		return usuarioAtendimento;
	}

	public void setUsuarioAtendimento(String usuarioAtendimento){

		this.usuarioAtendimento = usuarioAtendimento;
	}

	public String getUnidadeRetornoChamada(){

		return unidadeRetornoChamada;
	}

	public void setUnidadeRetornoChamada(String unidadeRetornoChamada){

		this.unidadeRetornoChamada = unidadeRetornoChamada;
	}

	public String getUsuarioRetornoChamada(){

		return usuarioRetornoChamada;
	}

	public void setUsuarioRetornoChamada(String usuarioRetornoChamada){

		this.usuarioRetornoChamada = usuarioRetornoChamada;
	}

	public void setMotivoAtendimentoIncompleto(String[] motivoAtendimentoIncompleto){

		this.motivoAtendimentoIncompleto = motivoAtendimentoIncompleto;
	}

	public String getClientedescricao(){

		return clientedescricao;
	}

	public void setClientedescricao(String clientedescricao){

		this.clientedescricao = clientedescricao;
	}

	public String getUnidadeAtendimentoDescricao(){

		return unidadeAtendimentoDescricao;
	}

	public void setUnidadeAtendimentoDescricao(String unidadeAtendimentoDescricao){

		this.unidadeAtendimentoDescricao = unidadeAtendimentoDescricao;
	}

	public String getUnidadeRetornoChamadaDescricao(){

		return unidadeRetornoChamadaDescricao;
	}

	public void setUnidadeRetornoChamadaDescricao(String unidadeRetornoChamadaDescricao){

		this.unidadeRetornoChamadaDescricao = unidadeRetornoChamadaDescricao;
	}

	public String getUsuarioRetornoChamadaDescricao(){

		return usuarioRetornoChamadaDescricao;
	}

	public void setUsuarioRetornoChamadaDescricao(String usuarioRetornoChamadaDescricao){

		this.usuarioRetornoChamadaDescricao = usuarioRetornoChamadaDescricao;
	}

	public String getUsuarioAtendimentoDescricao(){

		return usuarioAtendimentoDescricao;
	}

	public void setUsuarioAtendimentoDescricao(String usuarioAtendimentoDescricao){

		this.usuarioAtendimentoDescricao = usuarioAtendimentoDescricao;
	}

	public String getSelectedTipoSolicitacaoSize(){

		return selectedTipoSolicitacaoSize;
	}

	public void setSelectedTipoSolicitacaoSize(String selectedTipoSolicitacaoSize){

		this.selectedTipoSolicitacaoSize = selectedTipoSolicitacaoSize;
	}

}