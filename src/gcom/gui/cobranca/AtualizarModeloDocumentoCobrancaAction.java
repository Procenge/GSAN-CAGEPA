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
 * [UC3170] Informar Entrega/Devolução de Documentos de Cobrança
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

		montarPaginaSucesso(httpServletRequest, "Modelo do Documento de Cobrança " + documentoTipoLayout.getId()
						+ " atualizado com sucesso.", "Realizar outra Manutenção Modelo do Documento de Cobrança",
						"exibirInformarModeloDocumentoCobrancaAction.do?menu=sim");


		return retorno;
	}

}
