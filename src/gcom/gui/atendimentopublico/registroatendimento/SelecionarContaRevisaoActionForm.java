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

import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaMotivoRevisao;

import java.util.Collection;

import org.apache.struts.action.ActionForm;

/**
 * @author isilva
 */
public class SelecionarContaRevisaoActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	public static final String NOME_SESSAO = SelecionarContaRevisaoActionForm.class.getSimpleName();

	public static final String COM_MENSAGEM = "COM_MENSAGEM";

	public static final String SEM_MENSAGEM = "SEM_MENSAGEM";

	private String codigoImovel;

	private String[] contaSelectID;

	private String contaSelectAuxID;

	private Collection<Conta> colecaoContas;

	private ContaMotivoRevisao contaMotivoRevisao;

	private String idContaMotivoRevisao;

	private String especificacao;

	private String abrirPopUpEnviarContaRevisao;

	/**
	 * @return the codigoImovel
	 */
	public String getCodigoImovel(){

		return codigoImovel;
	}

	/**
	 * @param codigoImovel
	 *            the codigoImovel to set
	 */
	public void setCodigoImovel(String codigoImovel){

		this.codigoImovel = codigoImovel;
	}

	/**
	 * @return the contaSelectID
	 */
	public String[] getContaSelectID(){

		return contaSelectID;
	}

	/**
	 * @param contaSelectID
	 *            the contaSelectID to set
	 */
	public void setContaSelectID(String[] contaSelectID){

		this.contaSelectID = contaSelectID;
	}

	/**
	 * @return the contaSelectAuxID
	 */
	public String getContaSelectAuxID(){

		return contaSelectAuxID;
	}

	/**
	 * @param contaSelectAuxID
	 *            the contaSelectAuxID to set
	 */
	public void setContaSelectAuxID(String contaSelectAuxID){

		this.contaSelectAuxID = contaSelectAuxID;
	}

	/**
	 * @return the colecaoContas
	 */
	public Collection<Conta> getColecaoContas(){

		return colecaoContas;
	}

	/**
	 * @param colecaoContas
	 *            the colecaoContas to set
	 */
	public void setColecaoContas(Collection<Conta> colecaoContas){

		this.colecaoContas = colecaoContas;
	}

	/**
	 * @return the contaMotivoRevisao
	 */
	public ContaMotivoRevisao getContaMotivoRevisao(){

		return contaMotivoRevisao;
	}

	/**
	 * @param contaMotivoRevisao
	 *            the contaMotivoRevisao to set
	 */
	public void setContaMotivoRevisao(ContaMotivoRevisao contaMotivoRevisao){

		this.contaMotivoRevisao = contaMotivoRevisao;
	}

	/**
	 * @return the idContaMotivoRevisao
	 */
	public String getIdContaMotivoRevisao(){

		return idContaMotivoRevisao;
	}

	/**
	 * @param idContaMotivoRevisao
	 *            the idContaMotivoRevisao to set
	 */
	public void setIdContaMotivoRevisao(String idContaMotivoRevisao){

		this.idContaMotivoRevisao = idContaMotivoRevisao;
	}

	/**
	 * @return the especificacao
	 */
	public String getEspecificacao(){

		return especificacao;
	}

	/**
	 * @param especificacao
	 *            the especificacao to set
	 */
	public void setEspecificacao(String especificacao){

		this.especificacao = especificacao;
	}

	/**
	 * @return the abrirPopUpEnviarContaRevisao
	 */
	public String getAbrirPopUpEnviarContaRevisao(){

		return abrirPopUpEnviarContaRevisao;
	}

	/**
	 * @param abrirPopUpEnviarContaRevisao
	 *            the abrirPopUpEnviarContaRevisao to set
	 */
	public void setAbrirPopUpEnviarContaRevisao(String abrirPopUpEnviarContaRevisao){

		this.abrirPopUpEnviarContaRevisao = abrirPopUpEnviarContaRevisao;
	}

	/**
	 * @author isilva
	 */
	public void limparEnvioRevisao(){

		this.contaSelectID = null;
		this.contaSelectAuxID = null;
		this.colecaoContas = null;
		this.contaMotivoRevisao = null;
		this.idContaMotivoRevisao = null;
	}
}
