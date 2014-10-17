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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gui.relatorio.transacao;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.transacao.RelatorioConsultarOperacao;
import gcom.seguranca.acesso.FiltroOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.FiltroUsuarioAcao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action responsável pela exibição do relatório de operação consultar
 * 
 * @author Rafael Corrêa
 * @created 06/04/2006
 * @author eduardo Henrique
 * @date 21/05/2008
 *       Incluído filtro de múltipla escolha de operações para visualização de Transações efetuadas.
 *       Filtro Argumento de Pesquisa foi retirado por enquanto, até revisão desta funcionalidade.
 */
public class GerarRelatorioConsultarOperacaoAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a variável de retorno
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoOperacoesEfetuadas = (Collection) sessao.getAttribute("colecaoOperacaoEfetuada");

		// recupera o id da operação efetuada que está na sessao.
		String idOperacaoEfetuada = "";

		if(sessao.getAttribute("idOperacaoEfetuada") != null){

			idOperacaoEfetuada = ((String) sessao.getAttribute("idOperacaoEfetuada")).toString();

		}

		// Inicio da parte que vai mandar os parametros para o relatório
		String idOperacao = "";

		if(sessao.getAttribute("idOperacao") != null){

			idOperacao = ((Integer) sessao.getAttribute("idOperacao")).toString();

		}

		Operacao operacao = null;

		if(idOperacao != null && !idOperacao.equals("")){
			FiltroOperacao filtroOperacao = new FiltroOperacao();

			filtroOperacao.adicionarParametro(new ParametroSimples(FiltroOperacao.ID, idOperacao));

			Collection colecaoOperacoes = fachada.pesquisar(filtroOperacao, Operacao.class.getName());

			if(colecaoOperacoes != null && !colecaoOperacoes.isEmpty()){
				// A operação foi encontrada

				operacao = (Operacao) colecaoOperacoes.iterator().next();

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Operação");
			}

		}else{
			operacao = new Operacao();
		}

		String idUsuario = "";

		if(sessao.getAttribute("idUsuario") != null){

			idUsuario = (String) sessao.getAttribute("idUsuario");

		}

		Usuario usuario = null;

		if(idUsuario != null && !idUsuario.equals("")){
			FiltroUsuario filtroUsuario = new FiltroUsuario();

			filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, idUsuario));

			Collection colecaoUsuarios = fachada.pesquisar(filtroUsuario, Usuario.class.getName());

			if(colecaoUsuarios != null && !colecaoUsuarios.isEmpty()){
				// O usuário foi encontrado

				usuario = (Usuario) colecaoUsuarios.iterator().next();

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Usuário");
			}

		}else{
			usuario = new Usuario();
		}

		String idUsuarioAcao = "";

		if(sessao.getAttribute("idUsuarioAcao") != null){

			idUsuarioAcao = ((Integer) sessao.getAttribute("idUsuarioAcao")).toString();

		}

		UsuarioAcao usuarioAcao = null;

		if(idUsuarioAcao != null && !idUsuarioAcao.equals("")){
			FiltroUsuarioAcao filtroUsuarioAcao = new FiltroUsuarioAcao();

			filtroUsuarioAcao.adicionarParametro(new ParametroSimples(FiltroUsuarioAcao.ID, idUsuario));

			Collection colecaoUsuariosAcoes = fachada.pesquisar(filtroUsuarioAcao, UsuarioAcao.class.getName());

			if(colecaoUsuariosAcoes != null && !colecaoUsuariosAcoes.isEmpty()){
				// A ação do usuário foi encontrado

				usuarioAcao = (UsuarioAcao) colecaoUsuariosAcoes.iterator().next();

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Ação do Usuário");
			}

		}else{
			usuarioAcao = new UsuarioAcao();
		}

		Date periodoRealizacaoInicial = null;
		Date periodoRealizacaoFinal = null;

		if(sessao.getAttribute("dataInicial") != null){

			periodoRealizacaoInicial = (Date) sessao.getAttribute("dataInicial");

		}

		if(sessao.getAttribute("dataFinal") != null){

			periodoRealizacaoFinal = (Date) sessao.getAttribute("dataFinal");

		}

		Date horarioRealizacaoInicial = null;
		Date horarioRealizacaoFinal = null;

		if(sessao.getAttribute("horaInicial") != null){

			horarioRealizacaoInicial = (Date) sessao.getAttribute("horaInicial");

		}

		if(sessao.getAttribute("horaFinal") != null){

			horarioRealizacaoFinal = (Date) sessao.getAttribute("horaFinal");

		}
		Integer idTabela = null;

		if(sessao.getAttribute("idTabela") != null){
			idTabela = (Integer) sessao.getAttribute("idTabela");
		}

		Integer[] idTabelaColuna = null;

		if(sessao.getAttribute("idTabelaColuna") != null){
			idTabelaColuna = (Integer[]) sessao.getAttribute("idTabelaColuna");
		}

		Integer id1 = null;

		if(sessao.getAttribute("id1") != null){
			id1 = (Integer) sessao.getAttribute("id1");
		}

		String[] idOperacoes = null;
		if(sessao.getAttribute("operacoes") != null){
			String[] operacoesSelecionadas = (String[]) sessao.getAttribute("operacoes");
			idOperacoes = new String[operacoesSelecionadas.length];
			int iCont = 0;
			for(String idOperacaoSelecionada : operacoesSelecionadas){
				idOperacoes[iCont] = idOperacaoSelecionada;
				iCont++;
			}
		}

		// cria uma instância da classe do relatório
		RelatorioConsultarOperacao relatorioConsultarOperacao = new RelatorioConsultarOperacao(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

		relatorioConsultarOperacao.addParametro("colecaoOperacoesEfetuadas", colecaoOperacoesEfetuadas);
		relatorioConsultarOperacao.addParametro("operacao", operacao);
		relatorioConsultarOperacao.addParametro("usuario", usuario);
		relatorioConsultarOperacao.addParametro("usuarioAcao", usuarioAcao);
		relatorioConsultarOperacao.addParametro("periodoRealizacaoInicial", periodoRealizacaoInicial);
		relatorioConsultarOperacao.addParametro("periodoRealizacaoFinal", periodoRealizacaoFinal);
		relatorioConsultarOperacao.addParametro("horarioRealizacaoInicial", horarioRealizacaoInicial);
		relatorioConsultarOperacao.addParametro("horarioRealizacaoFinal", horarioRealizacaoFinal);
		relatorioConsultarOperacao.addParametro("idTabela", idTabela);
		relatorioConsultarOperacao.addParametro("idTabelaColuna", idTabelaColuna);
		relatorioConsultarOperacao.addParametro("id1", id1);
		relatorioConsultarOperacao.addParametro("idOperacaoEfetuada", idOperacaoEfetuada);
		relatorioConsultarOperacao.addParametro("argumentos", sessao.getAttribute("argumentos"));

		relatorioConsultarOperacao.addParametro("idOperacoes", idOperacoes);

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioConsultarOperacao.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		try{
			retorno = processarExibicaoRelatorio(relatorioConsultarOperacao, tipoRelatorio, httpServletRequest, httpServletResponse,
							actionMapping);

		}catch(RelatorioVazioException ex){
			// manda o erro para a página no request atual
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de atenção de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");

		}

		return retorno;
	}

}
