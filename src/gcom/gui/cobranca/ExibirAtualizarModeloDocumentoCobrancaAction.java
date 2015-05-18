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

import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cobranca.*;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroNaoNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

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

public class ExibirAtualizarModeloDocumentoCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirAtualizarModeloDocumentoCobranca");

		AtualizarModeloDocumentoCobrancaActionForm form = (AtualizarModeloDocumentoCobrancaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		String desfazer = httpServletRequest.getParameter("desfazer");

		if((form.getId() != null && form.getIndicadorModificacao() != null && form.getIndicadorModificacao().equals("S"))
						|| (httpServletRequest.getParameter("idDocumentoLayout") != null && (desfazer != null && desfazer
										.equalsIgnoreCase("S")))){

			if(httpServletRequest.getParameter("idDocumentoLayout") != null){
				form.setId(httpServletRequest.getParameter("idDocumentoLayout"));
			}

			form.setIndicadorModificacao("N");
			desfazer = "S";
			sessao.removeAttribute("colecaoCargoFuncionario");

		}

		if(desfazer != null && desfazer.equalsIgnoreCase("S")){
			FiltroDocumentoTipoLayout filtroDocumentoTipoLayout = new FiltroDocumentoTipoLayout();
			filtroDocumentoTipoLayout.adicionarParametro(new ParametroSimples(FiltroDocumentoTipoLayout.ID, form.getId()));
			filtroDocumentoTipoLayout.adicionarCaminhoParaCarregamentoEntidade(FiltroDocumentoTipoLayout.DOCUMENTO_TIPO);

			DocumentoTipoLayout documentoTipoLayout = (DocumentoTipoLayout) fachada
							.pesquisar(filtroDocumentoTipoLayout, DocumentoTipoLayout.class.getName()).iterator().next();

			form.setId(documentoTipoLayout.getId().toString());

			form.setDescricaoLayout(documentoTipoLayout.getDescricaoLayout());

			if(documentoTipoLayout.getDescricaoCIControleDocumento() != null){
				form.setDescricaoCI(documentoTipoLayout.getDescricaoCIControleDocumento());
			}

			form.setDocumentoTipoId(documentoTipoLayout.getDocumentoTipo().getId().toString());
			form.setDocumentoTipoDescricao(documentoTipoLayout.getDocumentoTipo().getDescricaoDocumentoTipo());

			form.setIndicadorLayoutPadrao(documentoTipoLayout.getIndicadorPadrao().toString());
			form.setConteudoLayout(documentoTipoLayout.getConteudoDocumentoTipoLayout());
			form.setIndicadorUso(documentoTipoLayout.getIndicadorUso().toString());

			form.setUltimaAlteracao(documentoTipoLayout.getUltimaAlteracao());

			sessao.removeAttribute("colecaoDocumentoLayoutAssinatura");
			sessao.removeAttribute("colecaoCargoFuncionario");

			form.setDescricaoCargoDocumentoLayoutAssinatura("");

			FiltroDocumentoLayoutAssinatura filtroDocumentoLayoutAssinatura = new FiltroDocumentoLayoutAssinatura();
			filtroDocumentoLayoutAssinatura.adicionarParametro(new ParametroSimples(
							FiltroDocumentoLayoutAssinatura.DOCUMENTO_TIPO_LAYOUT_ID, form.getId()));

			Collection<DocumentoLayoutAssinatura> colecaoDocumentoLayoutAssinatura = fachada.pesquisar(filtroDocumentoLayoutAssinatura,
							DocumentoLayoutAssinatura.class.getName());

			sessao.setAttribute("colecaoDocumentoLayoutAssinatura", colecaoDocumentoLayoutAssinatura);

			// Verifica o que pode ser pode ser editado o item
			FiltroCobrancaAcaoAtividadeComando filtroCobrancaAcaoAtividadeComando = new FiltroCobrancaAcaoAtividadeComando();
			filtroCobrancaAcaoAtividadeComando.adicionarParametro(new ParametroSimples(
							FiltroCobrancaAcaoAtividadeComando.DOCUMENTO_TIPO_LAYOUT_ID, documentoTipoLayout.getId()));
			filtroCobrancaAcaoAtividadeComando.adicionarParametro(new ParametroNaoNulo(FiltroCobrancaAcaoAtividadeComando.REALIZACAO));
			filtroCobrancaAcaoAtividadeComando.adicionarParametro(new ParametroSimplesDiferenteDe(
							FiltroCobrancaAcaoAtividadeComando.QUANTIDADE_DOCUMENTOS, Integer.valueOf("0")));

			Collection<CobrancaAcaoAtividadeComando> colecaoCobrancaAcaoAtividadeComando = fachada.pesquisar(
							filtroCobrancaAcaoAtividadeComando, CobrancaAcaoAtividadeComando.class.getName());
			if(colecaoCobrancaAcaoAtividadeComando.size() > 0){
				sessao.setAttribute("impedirModificacao", ConstantesSistema.SIM);
			}else{
				sessao.removeAttribute("impedirModificacao");
			}
		}

		if(sessao.getAttribute("colecaoCargoFuncionario") == null){
			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
			filtroFuncionario.setCampoDistinct(FiltroFuncionario.DESCRICAO_CARGO);
			filtroFuncionario.setCampoOrderBy(FiltroFuncionario.DESCRICAO_CARGO);

			Collection colecaoCargoFuncionario = fachada.pesquisar(filtroFuncionario, Funcionario.class.getName());

			sessao.setAttribute("colecaoCargoFuncionario", colecaoCargoFuncionario);
		}

		return retorno;

	}

}
