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
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.imovel.Imovel;
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
 * Consultar Pagamento - Exibir
 * 
 * @author Gicevaler Couto
 * @date 03/10/2014
 */
public class ExibirFiltrarAtendimentosRealizadosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("filtrarAtendimentosRealizados");

		// Instacia a fachada
		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarAtendimentosRealizadosActionForm form = (FiltrarAtendimentosRealizadosActionForm) actionForm;


		if(sessao.getAttribute("colecaoTipoSolicitacao") == null){
			FiltroSolicitacaoTipo filtroSolicitacaoTipo = new FiltroSolicitacaoTipo();
			filtroSolicitacaoTipo.setCampoOrderBy(FiltroSolicitacaoTipo.DESCRICAO);

			Collection colecaoSolicitacaoTipo = fachada.pesquisar(filtroSolicitacaoTipo, SolicitacaoTipo.class.getName());
			sessao.setAttribute("colecaoTipoSolicitacao", colecaoSolicitacaoTipo);
		}

		String idFuncionalidadeDigitada = form.getIdFuncionalidade();

		// Caso o código da funcionalidade tenha sido informado
		if(httpServletRequest.getParameter("carregarFuncionalidade") != null && idFuncionalidadeDigitada != null
						&& !idFuncionalidadeDigitada.trim().equalsIgnoreCase("")){

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

		if(httpServletRequest.getParameter("carregarSolicitacaoTipoEspecificacao") != null && form.getIdSolicitacaoTipo() != null
						&& !form.getIdSolicitacaoTipo().equals("")){
			Collection<SolicitacaoTipoEspecificacao> colecaoSolicitacaoTipoEspecificacao = new ArrayList<SolicitacaoTipoEspecificacao>();

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
			}else{
				colecaoSolicitacaoTipoEspecificacao = (Collection<SolicitacaoTipoEspecificacao>) sessao
								.getAttribute("colecaoEspecificacao");
			}

			sessao.setAttribute("colecaoEspecificacao", colecaoSolicitacaoTipoEspecificacao);
		}

		if(httpServletRequest.getParameter("carregarImovel") != null && form.getIdImovel() != null
						&& !form.getIdImovel().trim().equalsIgnoreCase("")){

			Imovel imovelAtual = fachada.pesquisarImovel(Integer.valueOf(form.getIdImovel()));

			if(imovelAtual != null){
				form.setIdImovel(String.valueOf(imovelAtual.getId()));
				form.setInscricaoImovel(imovelAtual.getInscricaoFormatada().toString());
				httpServletRequest.setAttribute("imovelEncontrado", "true");

			}else{
				form.setIdImovel("");
				form.setInscricaoImovel("FUNCIONALIDADE INEXISTENTE");
				httpServletRequest.setAttribute("imovelNaoEncontrado", "exception");
			}
		}

		if(httpServletRequest.getParameter("carregarCliente") != null && form.getIdCliente() != null
						&& !form.getIdCliente().trim().equalsIgnoreCase("")){

			FiltroCliente filtroCliente = new FiltroCliente();
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, Integer.valueOf(form.getIdCliente())));
			filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.CLIENTE_TIPO);
			Cliente clienteAtual = (Cliente) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroCliente, Cliente.class.getName()));

			if(clienteAtual != null){
				form.setIdCliente(String.valueOf(clienteAtual.getId()));
				form.setNomeCliente(clienteAtual.getDescricao().toString());
				httpServletRequest.setAttribute("clienteEncontrado", "true");

			}else{
				form.setIdCliente("");
				form.setNomeCliente("CLIENTE INEXISTENTE");
				httpServletRequest.setAttribute("clienteNaoEncontrado", "exception");
			}
		}

		if(httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer").equalsIgnoreCase("S")){
			form.setId("");
			form.setIdSolicitacaoTipo("");
			form.setIdSolicitacaoTipoEspecificacao("");
			form.setIdFuncionalidade("");
			form.setDescricaoFuncionalidade("");
			form.setdataHoraInicioAtendimento("");
			form.setdataHoraFimAtendimento("");

			form.setIdImovel("");
			form.setInscricaoImovel("");

			form.setIdCliente("");
			form.setNomeCliente("");

			sessao.removeAttribute("colecaoEspecificacao");
		}

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
