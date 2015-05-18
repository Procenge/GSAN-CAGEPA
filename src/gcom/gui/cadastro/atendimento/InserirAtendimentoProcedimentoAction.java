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

package gcom.gui.cadastro.atendimento;

import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.cadastro.atendimento.AtendProcDocumentoPessoaTipo;
import gcom.cadastro.atendimento.AtendProcNormaProcedimental;
import gcom.cadastro.atendimento.AtendimentoProcedimento;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.Funcionalidade;
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
 * Descrição da classe
 * 
 * @author Giecvalter Couto
 * @date 23/09/2014
 */
public class InserirAtendimentoProcedimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		InserirAtendimentoProcedimentoActionForm form = (InserirAtendimentoProcedimentoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		AtendimentoProcedimento atendimentoProcedimento = new AtendimentoProcedimento();
		atendimentoProcedimento.setDescricao(form.getDescricao());

		if(form.getIdFuncionalidade() != null && !form.getIdFuncionalidade().trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)
						&& !form.getIdFuncionalidade().equals("")){

			Funcionalidade funcionalidade = new Funcionalidade();
			funcionalidade.setId(Integer.valueOf(form.getIdFuncionalidade()));
			
			atendimentoProcedimento.setFuncionalidade(funcionalidade);
		}

		if(form.getIdSolicitacaoTipoEspecificacao() != null
						&& !form.getIdSolicitacaoTipoEspecificacao().trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)
						&& !form.getIdSolicitacaoTipoEspecificacao().equals("")){

			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();
			solicitacaoTipoEspecificacao.setId(Integer.valueOf(form.getIdSolicitacaoTipoEspecificacao()));

			atendimentoProcedimento.setSolicitacaoTipoEspecificacao(solicitacaoTipoEspecificacao);
		}

		if(form.getIndicadorCliente() != null && form.getIndicadorCliente().equals("1")){
			atendimentoProcedimento.setIndicadorCliente(ConstantesSistema.SIM);
		}else{
			atendimentoProcedimento.setIndicadorCliente(ConstantesSistema.NAO);
		}

		if(form.getIndicadorImovel() != null && form.getIndicadorImovel().equals("1")){
			atendimentoProcedimento.setIndicadorImovel(ConstantesSistema.SIM);
		}else{
			atendimentoProcedimento.setIndicadorImovel(ConstantesSistema.NAO);
		}

		if(form.getIndicadorDispensadoDocumentacaoObrigatoria() != null && form.getIndicadorDispensadoDocumentacaoObrigatoria().equals("1")){
			atendimentoProcedimento.setIndicadorDispensadoDocumentacaoObrigatoria(ConstantesSistema.SIM);
		}else{
			atendimentoProcedimento.setIndicadorDispensadoDocumentacaoObrigatoria(ConstantesSistema.NAO);
		}

		Collection<AtendProcDocumentoPessoaTipo> colecaoAtendProcDocumentoPessoaTipo = null;
		if(sessao.getAttribute("colecaoAtendProcDocumentoPessoaTipo") != null){
			colecaoAtendProcDocumentoPessoaTipo = (Collection<AtendProcDocumentoPessoaTipo>) sessao
							.getAttribute("colecaoAtendProcDocumentoPessoaTipo");
		}

		Collection<AtendProcNormaProcedimental> colecaoAtendProcNormaProcedimental = null;
		if(sessao.getAttribute("colecaoAtendProcNormaProcedimental") != null){
			colecaoAtendProcNormaProcedimental = (Collection<AtendProcNormaProcedimental>) sessao
							.getAttribute("colecaoAtendProcNormaProcedimental");
		}

		Integer idAtendimentoProcedimento = (Integer) fachada.inserirAtendimentoProcedimento(atendimentoProcedimento,
						colecaoAtendProcDocumentoPessoaTipo, colecaoAtendProcNormaProcedimental, usuarioLogado);

		montarPaginaSucesso(httpServletRequest, "Procedimento de Atendimento " + idAtendimentoProcedimento + " inserida com sucesso.",
						"Inserir outro Procedimento de Atendimento", "exibirInserirAtendimentoProcedimentoAction.do?menu=sim",
						"exibirAtualizarAtendimentoProcedimentoAction.do?idAtendimentoProcedimento=" + idAtendimentoProcedimento,
						"Atualizar Procedimento de Atendimento inserida");

		return retorno;
	}
}