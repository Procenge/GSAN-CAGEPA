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
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.FiltroFuncionalidade;
import gcom.seguranca.acesso.Funcionalidade;
import gcom.util.Util;
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
 * @author Gicevalter Couto
 * @date 25/04/2006
 */
public class ExibirInserirAtendimentoProcedimentoAction
				extends GcomAction {


	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno para a tela de inserir operação
		ActionForward retorno = actionMapping.findForward("atendimentoProcedimentoInserir");

		// Recupera o form de inserir procedmento de atendimento
		InserirAtendimentoProcedimentoActionForm form = (InserirAtendimentoProcedimentoActionForm) actionForm;

		// Cria uma instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Cria uma instância da sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		Collection<SolicitacaoTipoEspecificacao> colecaoSolicitacaoTipoEspecificacao = new ArrayList<SolicitacaoTipoEspecificacao>();

		// Recupera a flag para indicar se o usuário apertou o botão de desfazer
		String desfazer = httpServletRequest.getParameter("desfazer");

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

		// Caso o usuário não tenha apertado o botão de desfazer na tela
		// verifica todas as validações e pesquisas
		// Caso contrário limpa o form de inserir operação
		if(desfazer == null){

			// Recupera o código da funcionalidade se ela for digitada
			String idFuncionalidadeDigitada = form.getIdFuncionalidade();

			// Caso o código da funcionalidade tenha sido informado
			if(idFuncionalidadeDigitada != null && !idFuncionalidadeDigitada.trim().equalsIgnoreCase("")){

				// Pesquisa a funcionalidade digitada na base de dados
				Funcionalidade funcionalidade = this.pesquisarFuncionalidade(idFuncionalidadeDigitada);

				// Caso exista a funcionalidade digitada na base de dados
				// seta as informações da funcionalidade no form
				// Caso contrário indica que a funcionalidade digitada não existe
				if(funcionalidade != null){
					form.setIdFuncionalidade(String.valueOf(funcionalidade.getId()));
					form.setDescricaoFuncionalidade(funcionalidade.getDescricao());
					httpServletRequest.setAttribute("funcionalidadeEncontrada", "true");

				}else{
					form.setIdFuncionalidade("");
					form.setDescricaoFuncionalidade("FUNCIONALIDADE INEXISTENTE");
					httpServletRequest.setAttribute("funcionalidadeNaoEncontrada", "exception");
				}
			}

			if(form.getIdSolicitacaoTipo() != null && !form.getIdSolicitacaoTipo().equals("")){

				if(httpServletRequest.getParameter("carregarSolicitacaoTipoEspecificacao") != null){
					// Filtra Solicitação Tipo Especificação
					FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
					filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(
									FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ID, form.getIdSolicitacaoTipo()));
					filtroSolicitacaoTipoEspecificacao.setCampoOrderBy(FiltroSolicitacaoTipoEspecificacao.DESCRICAO);
					filtroSolicitacaoTipoEspecificacao
									.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO);

					colecaoSolicitacaoTipoEspecificacao = fachada.pesquisar(filtroSolicitacaoTipoEspecificacao,
									SolicitacaoTipoEspecificacao.class.getName());
				} else {
					colecaoSolicitacaoTipoEspecificacao = (Collection<SolicitacaoTipoEspecificacao>) sessao
									.getAttribute("colecaoEspecificacao");
				}
			}


		}else{
			// Caso o usuário tenha apertado o botão de desfazer na tela
			// volta o form ao estado inicial
			form.setDescricao("");
			form.setIdSolicitacaoTipo("");
			form.setIdSolicitacaoTipoEspecificacao("");
			form.setIdFuncionalidade("");
			form.setDescricaoFuncionalidade("");
			form.setIdTipoPessoa("");
			form.setDescricaoTipoPessoa("");
			form.setIdTipoDocumento("");
			form.setDescricaoTipoDocumento("");
			form.setIdNormaProcedimental("");
			form.setDescricaoNormaProcedimental("");
			form.setIndicadorDocumentoObrigatorio("1");
			form.setIndicadorDispensadoDocumentacaoObrigatoria("");
			form.setIndicadorCliente("");

			sessao.removeAttribute("colecaoAtendProcDocumentoPessoaTipo");
			sessao.removeAttribute("colecaoAtendProcNormaProcedimental");
		}

		sessao.setAttribute("colecaoEspecificacao", colecaoSolicitacaoTipoEspecificacao);

		// Retorna o mapeamento contido na variável retorno
		return retorno;

	}

	private Funcionalidade pesquisarFuncionalidade(String idFuncionalidade){

		// Cria a variável que vai armazenar a funcionalidade pesquisada
		Funcionalidade funcionalidade = null;

		// Cria o filtro para pesquisa e seta o código da funcionalidade informada no filtro
		FiltroFuncionalidade filtroFuncionalidade = new FiltroFuncionalidade();
		filtroFuncionalidade.adicionarParametro(new ParametroSimples(FiltroFuncionalidade.ID, idFuncionalidade));

		// Pesquisa a funcionalidade na base de dados
		Collection colecaoFuncionalidade = Fachada.getInstancia().pesquisar(filtroFuncionalidade, Funcionalidade.class.getName());

		// Caso exista a funcionalidade cadastrada na base de dados
		// recupera a funcionalidade da coleção
		if(colecaoFuncionalidade != null && !colecaoFuncionalidade.isEmpty()){
			funcionalidade = (Funcionalidade) Util.retonarObjetoDeColecao(colecaoFuncionalidade);
		}

		// Retorna a funcionalidade pesquisa ou nulo se a funcionalidade não for encontrada
		return funcionalidade;

	}
}