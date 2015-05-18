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

package gcom.gui.cobranca;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Esta classe tem por finalidade gerar o formul�rio que receber� os par�metros
 * para realiza��o da selecao de d�bitos e cr�ditos para emitir o extrato de d�bito.
 * 
 * @author Vivianne Sousa
 * @date 02/08/2007
 */
public class DebitoCreditoDadosSelecaoExtratoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String idImovel;

	private String inscricaoImovel;

	private String nomeClienteUsuarioImovel;

	private String descricaoLigacaoAguaSituacaoImovel;

	private String descricaoLigacaoEsgotoSituacaoImovel;

	private String indicadorIncluirAcrescimosImpontualidade;

	private String indicadorTaxaCobranca;

	private String idsConta;

	private String idsDebito;

	private String idsCredito;

	private String idsGuia;

	private String idsParcelamento;

	private String funcionalidade = "2";

	private String indicadorMulta;

	private String indicadorJurosMora;

	private String indicadorAtualizacaoMonetaria;

	private String permitirSelecaoAcrescimoExtrato;

	private String idClienteRelacaoImovelSelecionado;

	public String getIdClienteRelacaoImovelSelecionado(){

		return idClienteRelacaoImovelSelecionado;
	}

	public void setIdClienteRelacaoImovelSelecionado(String idClienteRelacaoImovelSelecionado){

		this.idClienteRelacaoImovelSelecionado = idClienteRelacaoImovelSelecionado;
	}

	public String getDescricaoLigacaoAguaSituacaoImovel(){

		return descricaoLigacaoAguaSituacaoImovel;
	}

	public void setDescricaoLigacaoAguaSituacaoImovel(String descricaoLigacaoAguaSituacaoImovel){

		this.descricaoLigacaoAguaSituacaoImovel = descricaoLigacaoAguaSituacaoImovel;
	}

	public String getDescricaoLigacaoEsgotoSituacaoImovel(){

		return descricaoLigacaoEsgotoSituacaoImovel;
	}

	public void setDescricaoLigacaoEsgotoSituacaoImovel(String descricaoLigacaoEsgotoSituacaoImovel){

		this.descricaoLigacaoEsgotoSituacaoImovel = descricaoLigacaoEsgotoSituacaoImovel;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getNomeClienteUsuarioImovel(){

		return nomeClienteUsuarioImovel;
	}

	public void setNomeClienteUsuarioImovel(String nomeClienteUsuarioImovel){

		this.nomeClienteUsuarioImovel = nomeClienteUsuarioImovel;
	}

	public String getIndicadorIncluirAcrescimosImpontualidade(){

		return indicadorIncluirAcrescimosImpontualidade;
	}

	public void setIndicadorIncluirAcrescimosImpontualidade(String indicadorIncluirAcrescimosImpontualidade){

		this.indicadorIncluirAcrescimosImpontualidade = indicadorIncluirAcrescimosImpontualidade;
	}

	public String getIdsConta(){

		return idsConta;
	}

	public void setIdsConta(String idsConta){

		this.idsConta = idsConta;
	}

	public String getIdsCredito(){

		return idsCredito;
	}

	public void setIdsCredito(String idsCredito){

		this.idsCredito = idsCredito;
	}

	public String getIdsDebito(){

		return idsDebito;
	}

	public void setIdsDebito(String idsDebito){

		this.idsDebito = idsDebito;
	}

	public String getIdsGuia(){

		return idsGuia;
	}

	public void setIdsGuia(String idsGuia){

		this.idsGuia = idsGuia;
	}

	public String getIdsParcelamento(){

		return idsParcelamento;
	}

	public void setIdsParcelamento(String idsParcelamento){

		this.idsParcelamento = idsParcelamento;
	}

	public String getIndicadorTaxaCobranca(){

		return indicadorTaxaCobranca;
	}

	public void setIndicadorTaxaCobranca(String indicadorTaxaCobranca){

		this.indicadorTaxaCobranca = indicadorTaxaCobranca;
	}

	public String getFuncionalidade(){

		return funcionalidade;
	}

	public void setFuncionalidade(String funcionalidade){

		this.funcionalidade = funcionalidade;
	}

	/**
	 * @return the indicadorMulta
	 */
	public String getIndicadorMulta(){

		return indicadorMulta;
	}

	/**
	 * @param indicadorMulta
	 *            the indicadorMulta to set
	 */
	public void setIndicadorMulta(String indicadorMulta){

		this.indicadorMulta = indicadorMulta;
	}

	/**
	 * @return the indicadorJurosMora
	 */
	public String getIndicadorJurosMora(){

		return indicadorJurosMora;
	}

	/**
	 * @param indicadorJurosMora
	 *            the indicadorJurosMora to set
	 */
	public void setIndicadorJurosMora(String indicadorJurosMora){

		this.indicadorJurosMora = indicadorJurosMora;
	}

	/**
	 * @return the indicadorAtualizacaoMonetaria
	 */
	public String getIndicadorAtualizacaoMonetaria(){

		return indicadorAtualizacaoMonetaria;
	}

	/**
	 * @param indicadorAtualizacaoMonetaria
	 *            the indicadorAtualizacaoMonetaria to set
	 */
	public void setIndicadorAtualizacaoMonetaria(String indicadorAtualizacaoMonetaria){

		this.indicadorAtualizacaoMonetaria = indicadorAtualizacaoMonetaria;
	}

	/**
	 * @return the permitirSelecaoAcrescimoExtrato
	 */
	public String getPermitirSelecaoAcrescimoExtrato(){

		return permitirSelecaoAcrescimoExtrato;
	}

	/**
	 * @param permitirSelecaoAcrescimoExtrato
	 *            the permitirSelecaoAcrescimoExtrato to set
	 */
	public void setPermitirSelecaoAcrescimoExtrato(String permitirSelecaoAcrescimoExtrato){

		this.permitirSelecaoAcrescimoExtrato = permitirSelecaoAcrescimoExtrato;
	}

}
