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

package gcom.gui.cadastro.atendimento;

import gcom.cadastro.atendimento.bean.AtendimentoDocumentacaoInformadaHelper;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class ConsultarDadosAtendimentoPopupActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	// Dados Gerais
	private String idAtendimento;
	
	private String descricaoAtendimentoProcedimento;

	private String indicadorDispensadoDocumentacaoObrigatoria;

	private String nomeUsuario;

	private String descricaoFuncionalidade;

	private String descricaoTipoEspecificacao;

	private String idImovel;

	private String inscricaoImovel;

	private String idCliente;

	private String nomeCliente;

	private String dataInicioAtendimento;

	private String dataFimAtendimento;

	private List colecaoAtendimentoDocumentacao = new ArrayList();

	public void setDescricaoAtendimentoProcedimento(String descricaoAtendimentoProcedimento){

		this.descricaoAtendimentoProcedimento = descricaoAtendimentoProcedimento;
	}

	public String getDescricaoAtendimentoProcedimento(){

		return descricaoAtendimentoProcedimento;
	}

	public void setIndicadorDispensadoDocumentacaoObrigatoria(String indicadorDispensadoDocumentacaoObrigatoria){

		this.indicadorDispensadoDocumentacaoObrigatoria = indicadorDispensadoDocumentacaoObrigatoria;
	}

	public String getIndicadorDispensadoDocumentacaoObrigatoria(){

		return indicadorDispensadoDocumentacaoObrigatoria;
	}

	public List getColecaoAtendimentoDocumentacao(){

		return colecaoAtendimentoDocumentacao;
	}

	public void setColecaoAtendimentoDocumentacao(List colecaoAtendimentoDocumentacao){

		this.colecaoAtendimentoDocumentacao = colecaoAtendimentoDocumentacao;
	}

	public AtendimentoDocumentacaoInformadaHelper getAtendimentoDocumentacao(int index){

		return (AtendimentoDocumentacaoInformadaHelper) colecaoAtendimentoDocumentacao.get(index);
	}

	public void setAtendimentoDocumentacao(int index, AtendimentoDocumentacaoInformadaHelper atendimentoDocumentacao){

		colecaoAtendimentoDocumentacao.set(index, atendimentoDocumentacao);
	}

	public void setDescricaoTipoEspecificacao(String descricaoTipoEspecificacao){

		this.descricaoTipoEspecificacao = descricaoTipoEspecificacao;
	}

	public String getDescricaoTipoEspecificacao(){

		return descricaoTipoEspecificacao;
	}

	public void setIdAtendimento(String idAtendimento){

		this.idAtendimento = idAtendimento;
	}

	public String getIdAtendimento(){

		return idAtendimento;
	}

	public void setDataInicioAtendimento(String dataInicioAtendimento){

		this.dataInicioAtendimento = dataInicioAtendimento;
	}

	public String getDataInicioAtendimento(){

		return dataInicioAtendimento;
	}

	public void setDataFimAtendimento(String dataFimAtendimento){

		this.dataFimAtendimento = dataFimAtendimento;
	}

	public String getDataFimAtendimento(){

		return dataFimAtendimento;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setIdCliente(String idCliente){

		this.idCliente = idCliente;
	}

	public String getIdCliente(){

		return idCliente;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public String getNomeUsuario(){

		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario){

		this.nomeUsuario = nomeUsuario;
	}

	public String getDescricaoFuncionalidade(){

		return descricaoFuncionalidade;
	}

	public void setDescricaoFuncionalidade(String descricaoFuncionalidade){

		this.descricaoFuncionalidade = descricaoFuncionalidade;
	}

	public void resetarConsultarDadosAtendimentoPopup(){

		this.idAtendimento = null;
		this.descricaoAtendimentoProcedimento = null;
		this.indicadorDispensadoDocumentacaoObrigatoria = null;
		this.nomeUsuario = null;
		this.descricaoFuncionalidade = null;
		this.descricaoTipoEspecificacao = null;
		this.idImovel = null;
		this.inscricaoImovel = null;
		this.idCliente = null;
		this.nomeCliente = null;
		this.dataInicioAtendimento = null;
		this.dataFimAtendimento = null;
		this.colecaoAtendimentoDocumentacao = new ArrayList();
	}

}