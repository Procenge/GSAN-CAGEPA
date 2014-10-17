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
 * action respons�vel pela exibi��o do relat�rio de opera��o consultar
 * 
 * @author Rafael Corr�a
 * @created 06/04/2006
 * @author eduardo Henrique
 * @date 21/05/2008
 *       Inclu�do filtro de m�ltipla escolha de opera��es para visualiza��o de Transa��es efetuadas.
 *       Filtro Argumento de Pesquisa foi retirado por enquanto, at� revis�o desta funcionalidade.
 */
public class GerarRelatorioConsultarOperacaoAction
				extends ExibidorProcessamentoTarefaRelatorio {

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

		// cria a vari�vel de retorno
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		Collection colecaoOperacoesEfetuadas = (Collection) sessao.getAttribute("colecaoOperacaoEfetuada");

		// recupera o id da opera��o efetuada que est� na sessao.
		String idOperacaoEfetuada = "";

		if(sessao.getAttribute("idOperacaoEfetuada") != null){

			idOperacaoEfetuada = ((String) sessao.getAttribute("idOperacaoEfetuada")).toString();

		}

		// Inicio da parte que vai mandar os parametros para o relat�rio
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
				// A opera��o foi encontrada

				operacao = (Operacao) colecaoOperacoes.iterator().next();

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Opera��o");
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
				// O usu�rio foi encontrado

				usuario = (Usuario) colecaoUsuarios.iterator().next();

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Usu�rio");
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
				// A a��o do usu�rio foi encontrado

				usuarioAcao = (UsuarioAcao) colecaoUsuariosAcoes.iterator().next();

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "A��o do Usu�rio");
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

		// cria uma inst�ncia da classe do relat�rio
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
			// manda o erro para a p�gina no request atual
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de aten��o de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");

		}

		return retorno;
	}

}
