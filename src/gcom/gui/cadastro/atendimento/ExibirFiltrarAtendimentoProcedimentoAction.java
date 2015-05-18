/**
 * 
 */
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

public class ExibirFiltrarAtendimentoProcedimentoAction
				extends GcomAction {

	/**
	 * Descri��o da classe
	 * 
	 * @author Gicevalter Couto
	 * @date 23/09/2014
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atendimentoProcedimentoFiltrar");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarAtendimentoProcedimentoActionForm form = (FiltrarAtendimentoProcedimentoActionForm) actionForm;

		// Limpa o atributo se o usu�rio voltou o filtrar
		if(sessao.getAttribute("colecaoAtendimentoProcedimentoTela") != null){
			sessao.removeAttribute("colecaoAtendimentoProcedimentoTela");
		}

		if(form.getAtualizar() == null){
			form.setAtualizar("true");
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

		String idFuncionalidadeDigitada = form.getIdFuncionalidade();

		// Caso o c�digo da funcionalidade tenha sido informado
		if(idFuncionalidadeDigitada != null && !idFuncionalidadeDigitada.trim().equalsIgnoreCase("")){

			// Pesquisa a funcionalidade digitada na base de dados
			Funcionalidade funcionalidade = this.pesquisarFuncionalidade(idFuncionalidadeDigitada);

			// Caso exista a funcionalidade digitada na base de dados
			// seta as informa��es da funcionalidade no form
			// Caso contr�rio indica que a funcionalidade digitada n�o existe
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
				// Filtra Solicita��o Tipo Especifica��o
				FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
				filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(
								FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ID, form.getIdSolicitacaoTipo()));
				filtroSolicitacaoTipoEspecificacao.setCampoOrderBy(FiltroSolicitacaoTipoEspecificacao.DESCRICAO);
				filtroSolicitacaoTipoEspecificacao
								.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO);

				colecaoSolicitacaoTipoEspecificacao = fachada.pesquisar(filtroSolicitacaoTipoEspecificacao,
								SolicitacaoTipoEspecificacao.class.getName());
			}else{
				colecaoSolicitacaoTipoEspecificacao = (Collection<SolicitacaoTipoEspecificacao>) sessao
								.getAttribute("colecaoEspecificacao");
			}
		}

		if(httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer").equalsIgnoreCase("S")){
			form.setId("");
			form.setDescricao("");
			form.setIdSolicitacaoTipo("");
			form.setIdSolicitacaoTipoEspecificacao("");
			form.setIdFuncionalidade("");
			form.setDescricaoFuncionalidade("");
			form.setIndicadorUso("");

			form.setAtualizar("true");
		}

		sessao.setAttribute("colecaoEspecificacao", colecaoSolicitacaoTipoEspecificacao);

		return retorno;
	}

	private Funcionalidade pesquisarFuncionalidade(String idFuncionalidade){

		// Cria a vari�vel que vai armazenar a funcionalidade pesquisada
		Funcionalidade funcionalidade = null;

		// Cria o filtro para pesquisa e seta o c�digo da funcionalidade informada no filtro
		FiltroFuncionalidade filtroFuncionalidade = new FiltroFuncionalidade();
		filtroFuncionalidade.adicionarParametro(new ParametroSimples(FiltroFuncionalidade.ID, idFuncionalidade));

		// Pesquisa a funcionalidade na base de dados
		Collection colecaoFuncionalidade = Fachada.getInstancia().pesquisar(filtroFuncionalidade, Funcionalidade.class.getName());

		// Caso exista a funcionalidade cadastrada na base de dados
		// recupera a funcionalidade da cole��o
		if(colecaoFuncionalidade != null && !colecaoFuncionalidade.isEmpty()){
			funcionalidade = (Funcionalidade) Util.retonarObjetoDeColecao(colecaoFuncionalidade);
		}

		// Retorna a funcionalidade pesquisa ou nulo se a funcionalidade n�o for encontrada
		return funcionalidade;

	}

}
