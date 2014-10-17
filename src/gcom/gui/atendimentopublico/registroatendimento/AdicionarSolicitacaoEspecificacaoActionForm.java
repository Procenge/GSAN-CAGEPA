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

import gcom.util.Util;

import org.apache.struts.validator.ValidatorActionForm;

public class AdicionarSolicitacaoEspecificacaoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String descricaoSolicitacao;

	private String prazoPrevisaoAtendimento;

	private String indicadorPavimentoCalcada;

	private String indicadorPavimentoRua;

	private String indicadorLigacaoAgua;

	private String indicadorCobrancaMaterial;

	private String indicadorParecerEncerramento;

	private String indicadorGerarDebito;

	private String indicadorGerarCredito;

	private String indicadorCliente;

	private String indicadorMatriculaImovel;

	private String idSituacaoImovel;

	private String idUnidadeTramitacao;

	private String descricaoUnidadeTramitacao;

	private String indicadorGeraOrdemServico;

	private String idServicoOS;

	private String descricaoServicoOS;

	private String idTipoServico;

	private String descricaoTipoServico;

	private String ordemExecucao;

	private String indicadorVerificarDebito;

	private String indicadorColetor;

	private String idSolicitacaoTipoEspecificacaoMensagem;

	private String descricaoSolicitacaoTipoEspecificacaoMensagem;

	private String descricaoSolicitacaoTipoEspecificacaoMensagemTruncada;

	private String indicadorContaEmRevisao;

	private String indicadorMensagemAlertaRevisao;

	private String indicadorObrigatoriedadeRgRA;

	private String indicadorObrigatoriedadeCpfCnpjRA;

	private String indicadorTipoVerificarDebito;

	private String indicadorCalculoDataPrevistaAtendimento;

	private String indicadorGerarOrdemServico;

	private String indicadorConsiderarApenasDebitoTitularAtual;

	private String idClienteRelacaoTipo;

	public String getIndicadorColetor(){

		return indicadorColetor;
	}

	public void setIndicadorColetor(String indicadorColetor){

		this.indicadorColetor = indicadorColetor;
	}

	/**
	 * @return Retorna o campo indicadorVerificarDebito.
	 */
	public String getIndicadorVerificarDebito(){

		return indicadorVerificarDebito;
	}

	/**
	 * @param indicadorVerificarDebito
	 *            O indicadorVerificarDebito a ser setado.
	 */
	public void setIndicadorVerificarDebito(String indicadorVerificarDebito){

		this.indicadorVerificarDebito = indicadorVerificarDebito;
	}

	public String getDescricaoServicoOS(){

		return descricaoServicoOS;
	}

	public void setDescricaoServicoOS(String descricaoServicoOS){

		this.descricaoServicoOS = descricaoServicoOS;
	}

	public String getDescricaoSolicitacao(){

		return descricaoSolicitacao;
	}

	public void setDescricaoSolicitacao(String descricaoSolicitacao){

		this.descricaoSolicitacao = descricaoSolicitacao;
	}

	public String getDescricaoUnidadeTramitacao(){

		return descricaoUnidadeTramitacao;
	}

	public void setDescricaoUnidadeTramitacao(String descricaoUnidadeTramitacao){

		this.descricaoUnidadeTramitacao = descricaoUnidadeTramitacao;
	}

	public String getIdServicoOS(){

		return idServicoOS;
	}

	public void setIdServicoOS(String idServicoOS){

		this.idServicoOS = idServicoOS;
	}

	public String getIdSituacaoImovel(){

		return idSituacaoImovel;
	}

	public void setIdSituacaoImovel(String idSituacaoImovel){

		this.idSituacaoImovel = idSituacaoImovel;
	}

	public String getIdUnidadeTramitacao(){

		return idUnidadeTramitacao;
	}

	public void setIdUnidadeTramitacao(String idUnidadeTramitacao){

		this.idUnidadeTramitacao = idUnidadeTramitacao;
	}

	public String getIndicadorCliente(){

		return indicadorCliente;
	}

	public void setIndicadorCliente(String indicadorCliente){

		this.indicadorCliente = indicadorCliente;
	}

	public String getIndicadorCobrancaMaterial(){

		return indicadorCobrancaMaterial;
	}

	public void setIndicadorCobrancaMaterial(String indicadorCobrancaMaterial){

		this.indicadorCobrancaMaterial = indicadorCobrancaMaterial;
	}

	public String getIndicadorGeraOrdemServico(){

		return indicadorGeraOrdemServico;
	}

	public void setIndicadorGeraOrdemServico(String indicadorGeraOrdemServico){

		this.indicadorGeraOrdemServico = indicadorGeraOrdemServico;
	}

	public String getIndicadorGerarCredito(){

		return indicadorGerarCredito;
	}

	public void setIndicadorGerarCredito(String indicadorGerarCredito){

		this.indicadorGerarCredito = indicadorGerarCredito;
	}

	public String getIndicadorGerarDebito(){

		return indicadorGerarDebito;
	}

	public void setIndicadorGerarDebito(String indicadorGerarDebito){

		this.indicadorGerarDebito = indicadorGerarDebito;
	}

	public String getIndicadorParecerEncerramento(){

		return indicadorParecerEncerramento;
	}

	public void setIndicadorParecerEncerramento(String indicadorParecerEncerramento){

		this.indicadorParecerEncerramento = indicadorParecerEncerramento;
	}

	public String getIndicadorPavimentoCalcada(){

		return indicadorPavimentoCalcada;
	}

	public void setIndicadorPavimentoCalcada(String indicadorPavimentoCalcada){

		this.indicadorPavimentoCalcada = indicadorPavimentoCalcada;
	}

	public String getIndicadorPavimentoRua(){

		return indicadorPavimentoRua;
	}

	public void setIndicadorPavimentoRua(String indicadorPavimentoRua){

		this.indicadorPavimentoRua = indicadorPavimentoRua;
	}

	public String getPrazoPrevisaoAtendimento(){

		return prazoPrevisaoAtendimento;
	}

	public void setPrazoPrevisaoAtendimento(String prazoPrevisaoAtendimento){

		this.prazoPrevisaoAtendimento = prazoPrevisaoAtendimento;
	}

	public String getOrdemExecucao(){

		return ordemExecucao;
	}

	public void setOrdemExecucao(String ordemExecucao){

		this.ordemExecucao = ordemExecucao;
	}

	public String getDescricaoTipoServico(){

		return descricaoTipoServico;
	}

	public void setDescricaoTipoServico(String descricaoTipoServico){

		this.descricaoTipoServico = descricaoTipoServico;
	}

	public String getIdTipoServico(){

		return idTipoServico;
	}

	public void setIdTipoServico(String idTipoServico){

		this.idTipoServico = idTipoServico;
	}

	public String getIndicadorMatriculaImovel(){

		return indicadorMatriculaImovel;
	}

	public void setIndicadorMatriculaImovel(String indicadorMatriculaImovel){

		this.indicadorMatriculaImovel = indicadorMatriculaImovel;
	}

	public String getIndicadorLigacaoAgua(){

		return indicadorLigacaoAgua;
	}

	public void setIndicadorLigacaoAgua(String indicadorLigacaoAgua){

		this.indicadorLigacaoAgua = indicadorLigacaoAgua;
	}

	/**
	 * @return the idSolicitacaoTipoEspecificacaoMensagem
	 */
	public String getIdSolicitacaoTipoEspecificacaoMensagem(){

		return idSolicitacaoTipoEspecificacaoMensagem;
	}

	/**
	 * @param idSolicitacaoTipoEspecificacaoMensagem
	 *            the idSolicitacaoTipoEspecificacaoMensagem to set
	 */
	public void setIdSolicitacaoTipoEspecificacaoMensagem(String idSolicitacaoTipoEspecificacaoMensagem){

		this.idSolicitacaoTipoEspecificacaoMensagem = idSolicitacaoTipoEspecificacaoMensagem;
	}

	/**
	 * @return the descricaoSolicitacaoTipoEspecificacaoMensagem
	 */
	public String getDescricaoSolicitacaoTipoEspecificacaoMensagem(){

		return descricaoSolicitacaoTipoEspecificacaoMensagem;
	}

	/**
	 * @param descricaoSolicitacaoTipoEspecificacaoMensagem
	 *            the descricaoSolicitacaoTipoEspecificacaoMensagem to set
	 */
	public void setDescricaoSolicitacaoTipoEspecificacaoMensagem(String descricaoSolicitacaoTipoEspecificacaoMensagem){

		this.descricaoSolicitacaoTipoEspecificacaoMensagem = descricaoSolicitacaoTipoEspecificacaoMensagem;
		setDescricaoSolicitacaoTipoEspecificacaoMensagemTruncada(Util.truncarString(this.descricaoSolicitacaoTipoEspecificacaoMensagem, 30));
	}

	/**
	 * @return the descricaoSolicitacaoTipoEspecificacaoMensagemTruncada
	 */
	public String getDescricaoSolicitacaoTipoEspecificacaoMensagemTruncada(){

		return descricaoSolicitacaoTipoEspecificacaoMensagemTruncada;
	}

	/**
	 * @param descricaoSolicitacaoTipoEspecificacaoMensagemTruncada
	 *            the descricaoSolicitacaoTipoEspecificacaoMensagemTruncada to set
	 */
	public void setDescricaoSolicitacaoTipoEspecificacaoMensagemTruncada(String descricaoSolicitacaoTipoEspecificacaoMensagemTruncada){

		this.descricaoSolicitacaoTipoEspecificacaoMensagemTruncada = descricaoSolicitacaoTipoEspecificacaoMensagemTruncada;
	}

	public String getIndicadorContaEmRevisao(){

		return indicadorContaEmRevisao;
	}

	public void setIndicadorContaEmRevisao(String indicadorContaEmRevisao){

		this.indicadorContaEmRevisao = indicadorContaEmRevisao;
	}

	public String getIndicadorMensagemAlertaRevisao(){

		return indicadorMensagemAlertaRevisao;
	}

	public void setIndicadorMensagemAlertaRevisao(String indicadorMensagemAlertaRevisao){

		this.indicadorMensagemAlertaRevisao = indicadorMensagemAlertaRevisao;
	}

	public String getIndicadorObrigatoriedadeRgRA(){

		return indicadorObrigatoriedadeRgRA;
	}

	public void setIndicadorObrigatoriedadeRgRA(String indicadorObrigatoriedadeRgRA){

		this.indicadorObrigatoriedadeRgRA = indicadorObrigatoriedadeRgRA;
	}

	public String getIndicadorObrigatoriedadeCpfCnpjRA(){

		return indicadorObrigatoriedadeCpfCnpjRA;
	}

	public void setIndicadorObrigatoriedadeCpfCnpjRA(String indicadorObrigatoriedadeCpfCnpjRA){

		this.indicadorObrigatoriedadeCpfCnpjRA = indicadorObrigatoriedadeCpfCnpjRA;
	}

	public void setIndicadorTipoVerificarDebito(String indicadorTipoVerificarDebito){

		this.indicadorTipoVerificarDebito = indicadorTipoVerificarDebito;
	}

	public String getIndicadorTipoVerificarDebito(){

		return indicadorTipoVerificarDebito;
	}

	public void setIndicadorCalculoDataPrevistaAtendimento(String indicadorCalculoDataPrevistaAtendimento){

		this.indicadorCalculoDataPrevistaAtendimento = indicadorCalculoDataPrevistaAtendimento;
	}

	public String getIndicadorCalculoDataPrevistaAtendimento(){

		return indicadorCalculoDataPrevistaAtendimento;
	}

	public String getIndicadorGerarOrdemServico(){

		return indicadorGerarOrdemServico;
	}

	public void setIndicadorGerarOrdemServico(String indicadorGerarOrdemServico){

		this.indicadorGerarOrdemServico = indicadorGerarOrdemServico;
	}

	public String getIndicadorConsiderarApenasDebitoTitularAtual(){

		return indicadorConsiderarApenasDebitoTitularAtual;
	}

	public void setIndicadorConsiderarApenasDebitoTitularAtual(String indicadorConsiderarApenasDebitoTitularAtual){

		this.indicadorConsiderarApenasDebitoTitularAtual = indicadorConsiderarApenasDebitoTitularAtual;
	}

	public String getIdClienteRelacaoTipo(){

		return idClienteRelacaoTipo;
	}

	public void setIdClienteRelacaoTipo(String idClienteRelacaoTipo){

		this.idClienteRelacaoTipo = idClienteRelacaoTipo;
	}

}
