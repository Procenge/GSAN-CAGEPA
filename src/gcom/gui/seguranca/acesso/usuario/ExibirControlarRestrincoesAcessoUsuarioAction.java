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

package gcom.gui.seguranca.acesso.usuario;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.FiltroFuncionalidade;
import gcom.seguranca.acesso.Funcionalidade;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel por exibir a p�gina de definir o acesso inicial que o
 * usuario vai possuir
 * 
 * @author S�vio Luiz
 * @date 28/06/2006
 */
public class ExibirControlarRestrincoesAcessoUsuarioAction
				extends GcomAction {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ControlarAcessoUsuarioActionForm controlarAcessoUsuarioActionForm = (ControlarAcessoUsuarioActionForm) actionForm;

		// cria a vari�vel de retorno e seta o mapeamento para a p�gina de
		// controlar
		ActionForward retorno = actionMapping.findForward("controlarRestrincoesAcessoUsuario");
		// verifica se foi clicado no bot�o para salvar as opera��es alteradas
		String botaoSalvar = httpServletRequest.getParameter("botaoSalvar");

		// cria uma inst�ncia da fachada
		// cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioAtualizar = (Usuario) sessao.getAttribute("usuarioParaAtualizar");

		Integer[] idsGrupos = (Integer[]) sessao.getAttribute("grupo");

		controlarAcessoUsuarioActionForm.setNomeUsuario(usuarioAtualizar.getNomeUsuario());
		controlarAcessoUsuarioActionForm.setLoginUsuario(usuarioAtualizar.getLogin());

		// recupera a colecao de funcionalidades que foram removidas(Todas as
		// opera��es removidas)
		Collection colecaoidsFuncionalidadesRemovidas = controlarAcessoUsuarioActionForm.getIdsFuncionalidadesRemovidas();
		if(colecaoidsFuncionalidadesRemovidas == null){
			colecaoidsFuncionalidadesRemovidas = new ArrayList();
		}

		// //cria o filtro de modulo
		// FiltroModulo filtroModulo = new FiltroModulo();

		// seta a ordena��o da pesquisa
		// filtroModulo.setCampoOrderBy(FiltroModulo.DESCRICAO_MODULO);

		// cria o filtro de funcionalidade
		FiltroFuncionalidade filtroFuncionalidade = new FiltroFuncionalidade();

		// seta o filtro para retornar todas as funcionalidades que s�o ponto de
		// entrada
		filtroFuncionalidade.adicionarParametro(new ParametroSimples(FiltroFuncionalidade.INDICADOR_PONTO_ENTRADA, new Integer(1)));
		filtroFuncionalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroFuncionalidade.OPERACOES);
		filtroFuncionalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroFuncionalidade.FUNCIONALIDADE_DEPENDENCIAS_BY_ID_INDEPENDENCIA);
		filtroFuncionalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroFuncionalidade.FUNCIONALIDADE_DEPENDENCIAS);

		// cria a cole��o de funcionalidades
		Collection funcionalidades = null;

		Map<Integer, Map<Integer, Collection<Operacao>>> funcionalidadesMap = (Map<Integer, Map<Integer, Collection<Operacao>>>) sessao
						.getAttribute("funcionalidadesMap");
		if(funcionalidadesMap == null){
			funcionalidadesMap = new HashMap();
		}

		// pega o c�digo da funcionalidade selecionada na p�gina
		String codigoFuncionalidade = httpServletRequest.getParameter("codigoFuncionalidade");

		// cadastrando as operacoes selecionadas
		// String codigoFuncionalidade = form.getFuncionalidade();
		if(codigoFuncionalidade != null && !"".equals(codigoFuncionalidade) && botaoSalvar != null){

			// pegando as operacoes escolhidas
			String[] operacoesAInserir = (String[]) controlarAcessoUsuarioActionForm.getOperacoes();
			funcionalidadesMap = Fachada.getInstancia().recuperaFuncionalidadeOperacaoRestrincao(new Integer(codigoFuncionalidade),
							operacoesAInserir, funcionalidadesMap);

			// manda o map de opera��es para a sess�o
			Map<Integer, Collection<Operacao>> operacoesMap = funcionalidadesMap.get(new Integer(codigoFuncionalidade));
			httpServletRequest.setAttribute("operacoesMap", operacoesMap);

			Collection operacoesMarcadas = operacoesMap.get(1);
			Collection operacoesDesmarcadas = operacoesMap.get(2);
			Collection operacoesDesabilitadas = operacoesMap.get(3);
			httpServletRequest.setAttribute("operacoesMarcadas", operacoesMarcadas);
			httpServletRequest.setAttribute("operacoesDesmarcadas", operacoesDesmarcadas);
			httpServletRequest.setAttribute("operacoesDesabilitadas", operacoesDesabilitadas);

		}else{
			if(codigoFuncionalidade != null && !codigoFuncionalidade.equals("")){

				funcionalidadesMap = Fachada.getInstancia().organizarOperacoesComValor(new Integer(codigoFuncionalidade),
								funcionalidadesMap, idsGrupos, usuarioAtualizar);

				// manda o map de opera��es para a sess�o
				Map<Integer, Collection<Operacao>> operacoesMap = funcionalidadesMap.get(new Integer(codigoFuncionalidade));
				httpServletRequest.setAttribute("operacoesMap", operacoesMap);

				Collection operacoesMarcadas = operacoesMap.get(1);
				Collection operacoesDesmarcadas = operacoesMap.get(2);
				Collection operacoesDesabilitadas = operacoesMap.get(3);

				httpServletRequest.setAttribute("operacoesMarcadas", operacoesMarcadas);
				httpServletRequest.setAttribute("operacoesDesmarcadas", operacoesDesmarcadas);
				httpServletRequest.setAttribute("operacoesDesabilitadas", operacoesDesabilitadas);

			}else{

				String linkRetorno = "controlarAcessosUsuarioWizardAction.do?action=exibirControlarRestrincoesAcessoUsuarioAction";
				// chama o met�do para criar a �rvore das funcionalidades
				String arvoreFuncionalidades = fachada.construirMenuAcesso(usuarioAtualizar, linkRetorno);

				// manda o javascript da �rvore para a p�gina no request
				sessao.setAttribute("arvoreFuncionalidades", arvoreFuncionalidades);
			}

		}

		/**
		 * Com a funcionalidade passada pesquisa todas as funcionalidade Ocultas
		 * com as funcionalidade Ocultas retira as operacoes
		 */

		// pesquisa a cole��o de funcionalidades cadastradas no sistema
		funcionalidades = fachada.pesquisar(filtroFuncionalidade, Funcionalidade.class.getName());

		// se nenhuma funcionalidade cadastrada no sistema
		if(funcionalidades == null || funcionalidades.isEmpty()){
			// manda o erro no request atual
			throw new ActionServletException("atencao.naocadastrado.funcionalidade");

		}

		// se for informado o c�digo da funcionalidade no request
		if(codigoFuncionalidade != null && !codigoFuncionalidade.equalsIgnoreCase("")){

			// cria a vari�vel que vai conter a descri��o da funcionalidade
			String descricaoFuncionalidade = null;

			// coloca a cole��o de funcionalidades no iterator
			Iterator iteratorFuncionalidades = funcionalidades.iterator();

			// cria a cole��o de funcionalidades ocultas(dependentes)
			// Collection funcionalidadesOcultas = new ArrayList();

			// la�o para encontrar a funcionalidade informada
			while(iteratorFuncionalidades.hasNext()){
				// recupera a funcionalidade do iterator
				Funcionalidade funcionalidade = (Funcionalidade) iteratorFuncionalidades.next();

				// se o c�digo da funcionalidade for igual ao c�digo da
				// funcionalidade informada
				if(codigoFuncionalidade.equalsIgnoreCase(funcionalidade.getId().toString())){
					// recupera a descri��o da funcionalidade
					descricaoFuncionalidade = funcionalidade.getDescricao();

					/*
					 * // recupera a cole��o de funcionalidades ocultas
					 * funcionalidadesOcultas = funcionalidade
					 * .getFuncionalidadeDependencias();
					 */

					// para a pesquisa
					break;
				}
			}

			// manda a descri��o da funcionalidade no request
			httpServletRequest.setAttribute("descricaoFuncionalidade", descricaoFuncionalidade);

			// manda a c�digo da funcionalidade no request
			httpServletRequest.setAttribute("idFuncionalidade", codigoFuncionalidade);

		}

		sessao.setAttribute("funcionalidadesMap", funcionalidadesMap);

		// retorna o mapeamento contido na vari�vel retorno
		return retorno;
	}
}