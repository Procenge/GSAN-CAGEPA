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

package gcom.gui.util.tabelaauxiliar.abreviada;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.tabelaauxiliar.abreviada.TabelaAuxiliarAbreviada;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 */
public class ExibirInserirTabelaAuxiliarAbreviadaAction
				extends GcomAction {

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

		// Prepara o retorno da Ação
		ActionForward retorno = actionMapping.findForward("inserirTabelaAuxiliarAbreviada");

		// Pega o parametro passado no request
		// String tela = (String) httpServletRequest.getParameter("tela");

		// Declaração de objetos e tipos primitivos

		String titulo = null;
		int tamMaxCampoDescricao = 40;
		int tamMaxCampoDescricaoAbreviada = 3;
		TabelaAuxiliarAbreviada tabelaAuxiliarAbreviada = null;
		String funcionalidadeTabelaAuxiliarAbreviadaInserir = null;
		String descricao = "Descrição";
		String descricaoAbreviada = "Descrição Abreviada";

		// ********BLOCO DE CÓDIGO PARA DEFINIÇÃO DOS CADASTROS PERTENCENTES A
		// INSERIR TABELA AUXILIAR ABREVIADA******//
		// Para serem incluidos novos cadastros com código, descrição e
		// descrição abreviada basta apenas cria um novo
		// if (condicional) semelhante ao exemplo abaixo, informando apenas os
		// dados relativos
		// ao objeto desejado.

		// --------------------CATEGORIA--------------------//
		// Identifica a string do objeto passado no get do request
		/***********************************************************************
		 * if (tela.equals("categoria")) { //Título a ser exido nas páginas
		 * titulo = "Categoria";
		 * //Cria o objeto Categoria categoria = new Categoria();
		 * //Associa o objeto tabela auxiliar ao tipo criado
		 * tabelaAuxiliarAbreviada = categoria; //Define o link a ser exibido na
		 * página de sucesso do inserir
		 * funcionalidadeTabelaAuxiliarAbreviadaInserir =
		 * Funcionalidade.TABELA_AUXILIAR_ABREVIADA_INSERIR +
		 * Funcionalidade.TELA_CATEGORIA; //Obtém o tamanho da propriedade da
		 * classe de acordo com length do mapeamento tamMaxCampoDescricao =
		 * HibernateUtil.getColumnSize(Categoria.class, "descricao");
		 * tamMaxCampoDescricaoAbreviada =
		 * HibernateUtil.getColumnSize(Categoria.class, "descricaoAbreviada"); } }
		 * else { //Caso a string (atribuída a tela) passada no request não seja
		 * válida reportarErros(httpServletRequest, "erro.sistema.parametro");
		 * retorno = actionMapping.findForward("telaErro"); }
		 * //********FIM DO BLOCO DE CÓDIGO
		 **********************************************************************/

		// Cria a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		// Remove os objetos da sessão
		sessao.removeAttribute("funcionalidadeTabelaAuxiliarAbreviadaInserir");
		sessao.removeAttribute("titulo");
		sessao.removeAttribute("tabelaAuxiliarAbreviada");

		String tela = httpServletRequest.getParameter("tela");
		// tempo da sessão
		sessao.setMaxInactiveInterval(1000);
		// Adiciona os objetos na sessão

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		SistemaParametro sistemaParametro = (SistemaParametro) fachada.pesquisarParametrosDoSistema();

		sessao.setAttribute("nomeSistema", sistemaParametro.getNomeEmpresa());

		DadosTelaTabelaAuxiliarAbreviada dados = DadosTelaTabelaAuxiliarAbreviada.obterDadosTelaTabelaAuxiliar(tela);
		// sessao.setAttribute("tabelaAuxiliarAbreviada", dados.getTabela());
		if(dados.getTitulo().equalsIgnoreCase("banco")){
			descricao = "Nome";
			descricaoAbreviada = "Nome Abreviado";
		}

		if(sessao.getAttribute("titulo") != null){
			titulo = (String) sessao.getAttribute("titulo");
		}
		sessao.setAttribute("dados", dados);
		sessao.setAttribute("titulo", dados.getTitulo());
		sessao.setAttribute("tabela", dados.getTabela());
		sessao.setAttribute("funcionalidadeTabelaAuxiliarAbreviadaInserir", dados.getFuncionalidadeTabelaAuxInserir());
		sessao.setAttribute("nomeParametroFuncionalidade", dados.getNomeParametroFuncionalidade());

		// Adiciona o objeto no request
		httpServletRequest.setAttribute("tamMaxCampoDescricao", new Integer(tamMaxCampoDescricao));
		httpServletRequest.setAttribute("tamMaxCampoDescricaoAbreviada", new Integer(tamMaxCampoDescricaoAbreviada));
		httpServletRequest.setAttribute("descricao", descricao);
		httpServletRequest.setAttribute("descricaoAbreviada", descricaoAbreviada);

		return retorno;
	}

}
