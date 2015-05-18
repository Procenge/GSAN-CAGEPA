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

import gcom.cadastro.atendimento.AtendProcDocumentoPessoaTipo;
import gcom.cadastro.atendimento.AtendimentoDocumentoTipo;
import gcom.cadastro.atendimento.AtendimentoPessoaTipo;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibm.icu.util.Calendar;

/**
 * Descri��o da classe
 * 
 * @author Gicevalter Couto
 * @date 24/09/2014
 */
public class RemoverAdicionarAtendProcDocumentosPessoaTipoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		HttpSession sessao = httpServletRequest.getSession(false);

		ActionForward retorno = actionMapping.findForward("inserirRemoverAtendProcDocumentosPessoaTipo");

		InserirAtendimentoProcedimentoActionForm form = (InserirAtendimentoProcedimentoActionForm) actionForm;

		String operacao = "";
		if(httpServletRequest.getParameter("operacao") != null && !httpServletRequest.getParameter("operacao").equals("")){
			operacao = httpServletRequest.getParameter("operacao").toString();
		}
		
		Collection<AtendProcDocumentoPessoaTipo> colecaoAtendProcDocumentoPessoaTipo = new ArrayList<AtendProcDocumentoPessoaTipo>();
		if(sessao.getAttribute("colecaoAtendProcDocumentoPessoaTipo") != null){
			colecaoAtendProcDocumentoPessoaTipo = (Collection<AtendProcDocumentoPessoaTipo>) sessao
							.getAttribute("colecaoAtendProcDocumentoPessoaTipo");
		}

		if(operacao.equals("inserir")){
			// Inserir de Objeto da collection
			AtendProcDocumentoPessoaTipo atendProcDocumentoPessoaTipo = new AtendProcDocumentoPessoaTipo();

			atendProcDocumentoPessoaTipo.setUltimaAlteracao(Calendar.getInstance().getTime());

			if(form.getIndicadorDocumentoObrigatorio() != null && form.getIndicadorDocumentoObrigatorio().equals("1")){
				atendProcDocumentoPessoaTipo.setIndicadorDocumentoObrigatorio(ConstantesSistema.SIM);
			}else{
				atendProcDocumentoPessoaTipo.setIndicadorDocumentoObrigatorio(ConstantesSistema.NAO);
			}

			AtendimentoDocumentoTipo atendimentoDocumentoTipo = new AtendimentoDocumentoTipo();
			atendimentoDocumentoTipo.setId(Integer.valueOf(form.getIdTipoDocumento()));
			atendimentoDocumentoTipo.setDescricao(form.getDescricaoTipoDocumento());
			atendProcDocumentoPessoaTipo.setAtendimentoDocumentoTipo(atendimentoDocumentoTipo);

			AtendimentoPessoaTipo atendimentoPessoaTipo = new AtendimentoPessoaTipo();
			atendimentoPessoaTipo.setId(Integer.valueOf(form.getIdTipoPessoa()));
			atendimentoPessoaTipo.setDescricao(form.getDescricaoTipoPessoa());
			atendProcDocumentoPessoaTipo.setAtendimentoPessoaTipo(atendimentoPessoaTipo);

			colecaoAtendProcDocumentoPessoaTipo.add(atendProcDocumentoPessoaTipo);

			sessao.setAttribute("colecaoAtendProcDocumentoPessoaTipo", colecaoAtendProcDocumentoPessoaTipo);

		}else if(operacao.equals("remover")){
			// Remocao de Objeto da collection
			if(httpServletRequest.getParameter("idDocumentoTipoPessoRemovao") != null
							&& !httpServletRequest.getParameter("idDocumentoTipoPessoRemovao").equals("")){

				Iterator iterator = colecaoAtendProcDocumentoPessoaTipo.iterator();
				long timestamp = new Long(httpServletRequest.getParameter("idDocumentoTipoPessoRemovao")).longValue();

				while(iterator.hasNext()){
					AtendProcDocumentoPessoaTipo atendProcDocumentoPessoaTipo = (AtendProcDocumentoPessoaTipo) iterator.next();
					if(GcomAction.obterTimestampIdObjeto(atendProcDocumentoPessoaTipo) == timestamp){
						iterator.remove();
					}
				}
				sessao.setAttribute("colecaoAtendProcDocumentoPessoaTipo", colecaoAtendProcDocumentoPessoaTipo);
			}
		}

		return retorno;
	}
}