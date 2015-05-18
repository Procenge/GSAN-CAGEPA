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

import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.cadastro.atendimento.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
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
 * @author Rômulo Aurélio
 * @date 15/05/2006
 */
public class ExibirAtualizarAtendimentoProcedimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("atualizarAtendimentoProcedimento");

		AtualizarAtendimentoProcedimentoActionForm form = (AtualizarAtendimentoProcedimentoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		String id = null;

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		if(httpServletRequest.getParameter("idAtendimentoProcedimento") != null && !httpServletRequest.getParameter("idAtendimentoProcedimento").equals("")){

			if(sessao.getAttribute("adicionar") != null){

				sessao.removeAttribute("objetoAtendimentoProcedimento");
				sessao.removeAttribute("adicionar");

			}else{
				sessao.removeAttribute("objetoAtendimentoProcedimento");
			}

		}

		Collection<SolicitacaoTipoEspecificacao> colecaoSolicitacaoTipoEspecificacao = new ArrayList<SolicitacaoTipoEspecificacao>();

		FiltroSolicitacaoTipo filtroSolicitacaoTipo = new FiltroSolicitacaoTipo();
		filtroSolicitacaoTipo.setCampoOrderBy(FiltroSolicitacaoTipo.DESCRICAO);

		Collection colecaoSolicitacaoTipo = fachada.pesquisar(filtroSolicitacaoTipo, SolicitacaoTipo.class.getName());
		sessao.setAttribute("colecaoTipoSolicitacao", colecaoSolicitacaoTipo);

		// Documento/Tipo Pessoa
		FiltroAtendimentoDocumentoTipo filtroAtendimentoDocumentoTipo = new FiltroAtendimentoDocumentoTipo();
		filtroAtendimentoDocumentoTipo.setCampoOrderBy(FiltroAtendimentoDocumentoTipo.DESCRICAO);

		Collection colecaoAtendimentoDocumentoTipo = fachada.pesquisar(filtroAtendimentoDocumentoTipo,
						AtendimentoDocumentoTipo.class.getName());
		sessao.setAttribute("colecaoAtendimentoDocumentoTipo", colecaoAtendimentoDocumentoTipo);

		FiltroAtendimentoPessoaTipo filtroAtendimentoPessoaTipo = new FiltroAtendimentoPessoaTipo();
		filtroAtendimentoPessoaTipo.setCampoOrderBy(FiltroAtendimentoPessoaTipo.DESCRICAO);

		Collection colecaoAtendimentoPessoaTipo = fachada.pesquisar(filtroAtendimentoDocumentoTipo, AtendimentoPessoaTipo.class.getName());
		sessao.setAttribute("colecaoAtendimentoPessoaTipo", colecaoAtendimentoPessoaTipo);

		FiltroNormaProcedimental filtroNormaProcedimental = new FiltroNormaProcedimental();
		filtroNormaProcedimental.setCampoOrderBy(FiltroNormaProcedimental.DESCRICAO);

		Collection colecaoNormaProcedimental = fachada.pesquisar(filtroNormaProcedimental, NormaProcedimental.class.getName());
		sessao.setAttribute("colecaoNormaProcedimental", colecaoNormaProcedimental);

		// Verifica se veio do filtrar ou do manter

		if(httpServletRequest.getParameter("manter") != null){
			sessao.setAttribute("manter", true);
		}else if(httpServletRequest.getParameter("filtrar") != null){
			sessao.removeAttribute("manter");
		}

		if(httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer").equalsIgnoreCase("S")){
			sessao.removeAttribute("colecaoAtendProcDocumentoPessoaTipo");
			sessao.removeAttribute("colecaoAtendProcNormaProcedimental");
			sessao.removeAttribute("colecaoEspecificacao");

			if(sessao.getAttribute("idAtendimentoProcedimento") != null){
				sessao.removeAttribute("objetoAtendimentoProcedimento");
			}
		}

		// Verifica se a AtendimentoProcedimento já está na sessão, em caso afirmativo
		// significa que o usuário já entrou na tela e apenas selecionou algum
		// item que deu um reload na tela e em caso negativo significa que ele
		// está entrando pela primeira vez
		if(sessao.getAttribute("objetoAtendimentoProcedimento") != null){
			AtendimentoProcedimento atendimentoProcedimento = (AtendimentoProcedimento) sessao
							.getAttribute("objetoAtendimentoProcedimento");

			if(sessao.getAttribute("idAtendimentoProcedimento") == null){
				sessao.setAttribute("idAtendimentoProcedimento", atendimentoProcedimento.getId().toString());

				form.setId(atendimentoProcedimento.getId().toString());
				form.setDescricao(atendimentoProcedimento.getDescricao());
				form.setIdFuncionalidade(atendimentoProcedimento.getFuncionalidade().getId().toString());
				form.setDescricaoFuncionalidade(atendimentoProcedimento.getFuncionalidade().getDescricao());

				if(atendimentoProcedimento.getSolicitacaoTipoEspecificacao() != null
								&& atendimentoProcedimento.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo() != null){
					form.setIdSolicitacaoTipo(atendimentoProcedimento.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo().getId()
									.toString());
				}else{
					form.setIdSolicitacaoTipo("");
				}

				if(atendimentoProcedimento.getSolicitacaoTipoEspecificacao() != null){
					form.setIdSolicitacaoTipoEspecificacao(atendimentoProcedimento.getSolicitacaoTipoEspecificacao().getId().toString());
				}else{
					form.setIdSolicitacaoTipoEspecificacao("");
				}
				
				if(atendimentoProcedimento.getIndicadorCliente().equals(ConstantesSistema.SIM)){
					form.setIndicadorCliente("1");
				}else{
					form.setIndicadorCliente("");
				}

				if(atendimentoProcedimento.getIndicadorImovel().equals(ConstantesSistema.SIM)){
					form.setIndicadorImovel("1");
				}else{
					form.setIndicadorImovel("");
				}

				if(atendimentoProcedimento.getIndicadorDispensadoDocumentacaoObrigatoria().equals(ConstantesSistema.SIM)){
					form.setIndicadorDispensadoDocumentacaoObrigatoria("1");
				}else{
					form.setIndicadorDispensadoDocumentacaoObrigatoria("");
				}

				form.setIndicadorUso(atendimentoProcedimento.getIndicadorUso().toString());
			}

			// Caso o código da funcionalidade tenha sido informado
			if(httpServletRequest.getParameter("carregarSolicitacaoTipoEspecificacao") != null
							|| sessao.getAttribute("colecaoEspecificacao") == null){

				Integer idEspecificacao = null;
				if(atendimentoProcedimento.getSolicitacaoTipoEspecificacao() != null
								&& atendimentoProcedimento.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo() != null){
					idEspecificacao = atendimentoProcedimento.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo().getId();
				}

				if(httpServletRequest.getParameter("carregarSolicitacaoTipoEspecificacao") != null){
					idEspecificacao = Integer.valueOf(form.getIdSolicitacaoTipo().toString());
				}

				if(idEspecificacao != null){

					// Filtra Solicitação Tipo Especificação
					FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
					filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(
									FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ID, idEspecificacao));
					filtroSolicitacaoTipoEspecificacao.setCampoOrderBy(FiltroSolicitacaoTipoEspecificacao.DESCRICAO);
					filtroSolicitacaoTipoEspecificacao
									.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO);

					colecaoSolicitacaoTipoEspecificacao = fachada.pesquisar(filtroSolicitacaoTipoEspecificacao,
									SolicitacaoTipoEspecificacao.class.getName());

					sessao.setAttribute("colecaoEspecificacao", colecaoSolicitacaoTipoEspecificacao);

				}
			}

			id = atendimentoProcedimento.getId().toString();

			// Carrega os dados do Tipo Pessoa/Documentos e Normas
			if(sessao.getAttribute("colecaoAtendProcDocumentoPessoaTipo") == null){
				FiltroAtendProcDocumentoPessoaTipo filtroAtendProcDocumentoPessoaTipo = new FiltroAtendProcDocumentoPessoaTipo();
				filtroAtendProcDocumentoPessoaTipo.adicionarParametro(new ParametroSimples(
								FiltroAtendProcDocumentoPessoaTipo.ATENDIMENTO_PROCEDIMENTO_ID, atendimentoProcedimento.getId()));
				filtroAtendProcDocumentoPessoaTipo.adicionarCaminhoParaCarregamentoEntidade("atendimentoPessoaTipo");
				filtroAtendProcDocumentoPessoaTipo.adicionarCaminhoParaCarregamentoEntidade("atendimentoDocumentoTipo");

				Collection colecaoAtendProcDocumentoPessoaTipo = fachada.pesquisar(filtroAtendProcDocumentoPessoaTipo,
								AtendProcDocumentoPessoaTipo.class.getName());

				sessao.setAttribute("colecaoAtendProcDocumentoPessoaTipo", colecaoAtendProcDocumentoPessoaTipo);
			}

			if(sessao.getAttribute("colecaoAtendProcNormaProcedimental") == null){
				FiltroAtendProcNormaProcedimental filtroAtendProcNormaProcedimental = new FiltroAtendProcNormaProcedimental();
				filtroAtendProcNormaProcedimental.adicionarParametro(new ParametroSimples(
								FiltroAtendProcNormaProcedimental.ATENDIMENTO_PROCEDIMENTO_ID, atendimentoProcedimento.getId()));
				filtroAtendProcNormaProcedimental.adicionarCaminhoParaCarregamentoEntidade("normaProcedimental");

				Collection colecaoAtendProcNormaProcedimental = fachada.pesquisar(filtroAtendProcNormaProcedimental,
								AtendProcNormaProcedimental.class.getName());

				sessao.setAttribute("colecaoAtendProcNormaProcedimental", colecaoAtendProcNormaProcedimental);
			}
			//

			sessao.setAttribute("objetoAtendimentoProcedimento", atendimentoProcedimento);


		}else{

			String atendimentoProcedimentoID = null;

			if(httpServletRequest.getParameter("idAtendimentoProcedimento") == null
							|| httpServletRequest.getParameter("idAtendimentoProcedimento").equals("")){
				atendimentoProcedimentoID = (String) sessao.getAttribute("idAtendimentoProcedimento");
			}else{
				atendimentoProcedimentoID = (String) httpServletRequest.getParameter("idAtendimentoProcedimento");
				sessao.setAttribute("idAtendimentoProcedimento", atendimentoProcedimentoID);
			}

			httpServletRequest.setAttribute("idAtendimentoProcedimento", atendimentoProcedimentoID);

			id = atendimentoProcedimentoID;
			FiltroAtendimentoProcedimento filtroAtendimentoProcedimento = new FiltroAtendimentoProcedimento();
			filtroAtendimentoProcedimento.adicionarCaminhoParaCarregamentoEntidade("solicitacaoTipoEspecificacao");
			filtroAtendimentoProcedimento.adicionarCaminhoParaCarregamentoEntidade("solicitacaoTipoEspecificacao.solicitacaoTipo");
			filtroAtendimentoProcedimento.adicionarCaminhoParaCarregamentoEntidade("funcionalidade");

			filtroAtendimentoProcedimento.adicionarParametro(new ParametroSimples(FiltroAtendimentoProcedimento.ID,
							atendimentoProcedimentoID));

			Collection<AtendimentoProcedimento> colecaoAtendimentoProcedimento = fachada.pesquisar(filtroAtendimentoProcedimento,
							AtendimentoProcedimento.class.getName());

			if(colecaoAtendimentoProcedimento == null || colecaoAtendimentoProcedimento.isEmpty()){
				throw new ActionServletException("atencao.atualizacao.timestamp");
			}

			AtendimentoProcedimento atendimentoProcedimento = (AtendimentoProcedimento) colecaoAtendimentoProcedimento.iterator().next();

			form.setId(atendimentoProcedimento.getId().toString());
			form.setDescricao(atendimentoProcedimento.getDescricao());
			form.setIdFuncionalidade(atendimentoProcedimento.getFuncionalidade().getId().toString());
			form.setDescricaoFuncionalidade(atendimentoProcedimento.getFuncionalidade().getDescricao());

			if(atendimentoProcedimento.getSolicitacaoTipoEspecificacao() != null
							&& atendimentoProcedimento.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo() != null){
				form.setIdSolicitacaoTipo(atendimentoProcedimento.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo().getId().toString());
			}else{
				form.setIdSolicitacaoTipo("");
			}

			if(atendimentoProcedimento.getSolicitacaoTipoEspecificacao() != null){
				form.setIdSolicitacaoTipoEspecificacao(atendimentoProcedimento.getSolicitacaoTipoEspecificacao().getId().toString());
			}else{
				form.setIdSolicitacaoTipoEspecificacao("");
			}

			if(atendimentoProcedimento.getIndicadorCliente().equals(ConstantesSistema.SIM)){
				form.setIndicadorCliente("1");
			}else{
				form.setIndicadorCliente("");
			}

			if(atendimentoProcedimento.getIndicadorImovel().equals(ConstantesSistema.SIM)){
				form.setIndicadorImovel("1");
			}else{
				form.setIndicadorImovel("");
			}

			if(atendimentoProcedimento.getIndicadorDispensadoDocumentacaoObrigatoria().equals(ConstantesSistema.SIM)){
				form.setIndicadorDispensadoDocumentacaoObrigatoria("1");
			}else{
				form.setIndicadorDispensadoDocumentacaoObrigatoria("");
			}

			form.setIndicadorUso(atendimentoProcedimento.getIndicadorUso().toString());

			// Caso o código da funcionalidade tenha sido informado
			if(httpServletRequest.getParameter("carregarSolicitacaoTipoEspecificacao") != null
							|| sessao.getAttribute("colecaoEspecificacao") == null){

				Integer idEspecificacao = null;
				if(atendimentoProcedimento.getSolicitacaoTipoEspecificacao() != null
								&& atendimentoProcedimento.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo() != null){
					idEspecificacao = atendimentoProcedimento.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo().getId();
				}

				if(httpServletRequest.getParameter("carregarSolicitacaoTipoEspecificacao") != null){
					idEspecificacao = Integer.valueOf(form.getIdSolicitacaoTipo().toString());
				}

				if(idEspecificacao != null){

					// Filtra Solicitação Tipo Especificação
					FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
					filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(
									FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ID, idEspecificacao));
					filtroSolicitacaoTipoEspecificacao.setCampoOrderBy(FiltroSolicitacaoTipoEspecificacao.DESCRICAO);
					filtroSolicitacaoTipoEspecificacao
									.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO);

					colecaoSolicitacaoTipoEspecificacao = fachada.pesquisar(filtroSolicitacaoTipoEspecificacao,
									SolicitacaoTipoEspecificacao.class.getName());

					sessao.setAttribute("colecaoEspecificacao", colecaoSolicitacaoTipoEspecificacao);

				}
			}

			// Carrega os dados do Tipo Pessoa/Documentos e Normas
			if(sessao.getAttribute("colecaoAtendProcDocumentoPessoaTipo") == null){
				FiltroAtendProcDocumentoPessoaTipo filtroAtendProcDocumentoPessoaTipo = new FiltroAtendProcDocumentoPessoaTipo();
				filtroAtendProcDocumentoPessoaTipo.adicionarParametro(new ParametroSimples(
								FiltroAtendProcDocumentoPessoaTipo.ATENDIMENTO_PROCEDIMENTO_ID, atendimentoProcedimento.getId()));
				filtroAtendProcDocumentoPessoaTipo.adicionarCaminhoParaCarregamentoEntidade("atendimentoPessoaTipo");
				filtroAtendProcDocumentoPessoaTipo.adicionarCaminhoParaCarregamentoEntidade("atendimentoDocumentoTipo");

				Collection colecaoAtendProcDocumentoPessoaTipo = fachada.pesquisar(filtroAtendProcDocumentoPessoaTipo,
								AtendProcDocumentoPessoaTipo.class.getName());

				sessao.setAttribute("colecaoAtendProcDocumentoPessoaTipo", colecaoAtendProcDocumentoPessoaTipo);
			}

			if(sessao.getAttribute("colecaoAtendProcNormaProcedimental") == null){
				FiltroAtendProcNormaProcedimental filtroAtendProcNormaProcedimental = new FiltroAtendProcNormaProcedimental();
				filtroAtendProcNormaProcedimental.adicionarParametro(new ParametroSimples(
								FiltroAtendProcNormaProcedimental.ATENDIMENTO_PROCEDIMENTO_ID, atendimentoProcedimento.getId()));
				filtroAtendProcNormaProcedimental.adicionarCaminhoParaCarregamentoEntidade("normaProcedimental");

				Collection colecaoAtendProcNormaProcedimental = fachada.pesquisar(filtroAtendProcNormaProcedimental,
								AtendProcNormaProcedimental.class.getName());

				sessao.setAttribute("colecaoAtendProcNormaProcedimental", colecaoAtendProcNormaProcedimental);
			}
			//

			sessao.setAttribute("objetoAtendimentoProcedimento", atendimentoProcedimento);

		}

		return retorno;

	}

}
