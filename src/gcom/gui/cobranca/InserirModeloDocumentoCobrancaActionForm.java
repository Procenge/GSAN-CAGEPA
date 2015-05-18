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

import org.apache.struts.action.ActionForm;

/**
 * InserirModeloDocumentoCobrancaActionForm
 * [UC3170] Informar Entrega/Devolu��o de Documentos de Cobran�a
 * 
 * @author Gicevalter Couto
 * @created 13/03/2015
 */


public class InserirModeloDocumentoCobrancaActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String documentoTipoId;

	private String documentoTipoDescricao;

	private String descricaoLayout;

	private String indicadorLayoutPadrao;

	private String descricaoCI;

	private String conteudoLayout;

	private String descricaoCargoDocumentoLayoutAssinatura;

	private String indicadorModificacao;

	public String getIndicadorModificacao(){

		return indicadorModificacao;
	}

	public void setIndicadorModificacao(String indicadorModificacao){

		this.indicadorModificacao = indicadorModificacao;
	}

	public String getDescricaoCargoDocumentoLayoutAssinatura(){

		return descricaoCargoDocumentoLayoutAssinatura;
	}

	public void setDescricaoCargoDocumentoLayoutAssinatura(String descricaoCargoDocumentoLayoutAssinatura){

		this.descricaoCargoDocumentoLayoutAssinatura = descricaoCargoDocumentoLayoutAssinatura;
	}

	public String getDocumentoTipoId(){

		return documentoTipoId;
	}

	public void setDocumentoTipoId(String documentoTipoId){

		this.documentoTipoId = documentoTipoId;
	}

	public String getDocumentoTipoDescricao(){

		return documentoTipoDescricao;
	}

	public void setDocumentoTipoDescricao(String documentoTipoDescricao){

		this.documentoTipoDescricao = documentoTipoDescricao;
	}

	public String getDescricaoLayout(){

		return descricaoLayout;
	}

	public void setDescricaoLayout(String descricaoLayout){

		this.descricaoLayout = descricaoLayout;
	}

	public String getIndicadorLayoutPadrao(){

		return indicadorLayoutPadrao;
	}

	public void setIndicadorLayoutPadrao(String indicadorLayoutPadrao){

		this.indicadorLayoutPadrao = indicadorLayoutPadrao;
	}

	public String getDescricaoCI(){

		return descricaoCI;
	}

	public void setDescricaoCI(String descricaoCI){

		this.descricaoCI = descricaoCI;
	}

	public String getConteudoLayout(){

		return conteudoLayout;
	}

	public void setConteudoLayout(String conteudoLayout){

		this.conteudoLayout = conteudoLayout;
	}

}
