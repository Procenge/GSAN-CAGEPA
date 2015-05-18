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

import gcom.cobranca.DocumentoLayoutAssinatura;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.DocumentoTipoLayout;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * InformarModeloDocumentoCobrancaActionForm
 * [UC3170] Informar Entrega/Devolu��o de Documentos de Cobran�a
 * 
 * @author Gicevalter Couto
 * @created 13/03/2015
 */

public class AtualizarModeloDocumentoCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AtualizarModeloDocumentoCobrancaActionForm form = (AtualizarModeloDocumentoCobrancaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		DocumentoTipoLayout documentoTipoLayout = new DocumentoTipoLayout();

		documentoTipoLayout.setDescricaoLayout(form.getDescricaoLayout());

		if(form.getId() != null){
			documentoTipoLayout.setId(Integer.valueOf(form.getId()));
		}

		if(form.getDescricaoCI() != null){
			documentoTipoLayout.setDescricaoCIControleDocumento(form.getDescricaoCI());
		}

		if(form.getIndicadorLayoutPadrao() != null && form.getIndicadorLayoutPadrao().equals("1")){
			documentoTipoLayout.setIndicadorPadrao(ConstantesSistema.SIM);
		}else{
			documentoTipoLayout.setIndicadorPadrao(ConstantesSistema.NAO);
		}

		if(form.getConteudoLayout() != null){
			documentoTipoLayout.setConteudoDocumentoTipoLayout(form.getConteudoLayout());
		}else{
			documentoTipoLayout.setConteudoDocumentoTipoLayout("");
		}

		if(form.getDocumentoTipoId() != null){
			DocumentoTipo documentoTipo = new DocumentoTipo();
			documentoTipo.setId(Integer.valueOf(form.getDocumentoTipoId().trim()));

			documentoTipoLayout.setDocumentoTipo(documentoTipo);
		}

		if(form.getIndicadorUso() != null && form.getIndicadorUso().equals(ConstantesSistema.INDICADOR_USO_ATIVO.toString())){
			documentoTipoLayout.setIndicadorUso(ConstantesSistema.SIM);
		}else{
			documentoTipoLayout.setIndicadorUso(ConstantesSistema.NAO);
		}

		if(form.getUltimaAlteracao() != null){
			documentoTipoLayout.setUltimaAlteracao(form.getUltimaAlteracao());
		}

		Collection<DocumentoLayoutAssinatura> colecaoDocumentoLayoutAssinatura = null;
		if(sessao.getAttribute("colecaoDocumentoLayoutAssinatura") != null){
			colecaoDocumentoLayoutAssinatura = (Collection<DocumentoLayoutAssinatura>) sessao
							.getAttribute("colecaoDocumentoLayoutAssinatura");
		}

		fachada.atualizarDocumentoTipoLayout(documentoTipoLayout, colecaoDocumentoLayoutAssinatura, usuario);

		form.setIndicadorModificacao("S");

		montarPaginaSucesso(httpServletRequest, "Modelo do Documento de Cobran�a " + documentoTipoLayout.getId()
						+ " atualizado com sucesso.", "Realizar outra Manuten��o Modelo do Documento de Cobran�a",
						"exibirInformarModeloDocumentoCobrancaAction.do?menu=sim");


		return retorno;
	}

}
