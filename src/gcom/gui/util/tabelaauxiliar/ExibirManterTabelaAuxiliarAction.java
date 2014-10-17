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

package gcom.gui.util.tabelaauxiliar;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.tabelaauxiliar.FiltroTabelaAuxiliar;
import gcom.util.tabelaauxiliar.TabelaAuxiliar;

import java.util.Collection;

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
 * @created 11 de Fevereiro de 2005
 */
public class ExibirManterTabelaAuxiliarAction
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

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("manterTabelaAuxiliar");

		// Obtém a instancia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Cria a coleção de tabelas auxiliares
		Collection tabelasAuxiliares = null;

		// Obtém a instância da sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		// Obtém o nome da tela passado no get do request
		String tela = (String) httpServletRequest.getParameter("tela");

		// Declaração de objetos e tipos primitivos
		String titulo = null;
		TabelaAuxiliar tabelaAuxiliar = null;
		int tamanhoMaximoCampo = 40;
		String pacoteNomeObjeto = null;
		String funcionalidadeTabelaAuxiliarManter = null;
		String descricao = "descricao";

		// Verifica se o exibir manter foi chamado da tela de filtro
		if(httpServletRequest.getAttribute("filtroTabelaAuxiliar") != null){
			tela = (String) sessao.getAttribute("tela");
		}

		// ********BLOCO DE CÓDIGO PARA DEFINIÇÃO DOS CADASTROS PERTENCENTES A
		// INSERIR TABELA AUXILIAR******//
		// Para serem incluidos novos cadastros com código e descrição basta
		// apenas cria um novo
		// if (condicional) semelhante ao exemplo abaixo, informando apenas os
		// dados relativos
		// ao objeto desejado.

		// ***************************************************************//
		// **********************MÓDULO DE CADASTRO***********************//
		// ***************************************************************//

		// --------------------PAVIMENTO CALÇADA--------------------//
		// Identifica a string do objeto passado no get do request
		/*
		 * if (tela.equals("pavimentoCalcada")) { //Título a ser exido nas
		 * páginas titulo = "Pavimento Calçada"; //Cria o objeto
		 * PavimentoCalcada PavimentoCalcada = new PavimentoCalcada(); //Associa
		 * o objeto tabela auxiliar ao tipo criado tabelaAuxiliar =
		 * pavimentoCalcada; //Obtém o path do pacote mais o tipo do objeto
		 * pacoteNomeObjeto = tabelaAuxiliar.getClass().getName(); //Define o
		 * link a ser exibido na página de sucesso atualizar/remover
		 * funcionalidadeTabelaAuxiliarManter =
		 * Funcionalidade.TABELA_AUXILIAR_MANTER +
		 * Funcionalidade.TELA_TIPO_PAVIMENTO_CALCADA; //Obtém o tamanho da
		 * propriedade da classe de acordo com length do mapeamento
		 * tamanhoMaximoCampo =
		 * HibernateUtil.getColumnSize(PavimentoCalcada.class, "descricao"); }
		 * //Caso a string (atribuída a tela) passada no request não seja válida
		 * else { throw new ActionServletException("erro.sistema.parametro"); }
		 * //********FIM DO BLOCO DE CÓDIGO******
		 */
		// Parte da verificação do filtro
		FiltroTabelaAuxiliar filtroTabelaAuxiliar = null;

		// Verifica se o filtro foi informado pela página de filtragem da tabela
		// auxiliar
		if(httpServletRequest.getAttribute("filtroTabelaAuxiliar") != null){
			filtroTabelaAuxiliar = (FiltroTabelaAuxiliar) httpServletRequest.getAttribute("filtroTabelaAuxiliar");
		}else{
			// Caso o exibirManterTabelaAuxiliar não tenha passado por algum
			// esquema de filtro,
			// a quantidade de registros é verificada para avaliar a necessidade
			// de filtragem
			filtroTabelaAuxiliar = new FiltroTabelaAuxiliar();

			if(fachada.registroMaximo(TabelaAuxiliar.class) > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_MANUTENCAO){
				// Se o limite de registros foi atingido, a página de filtragem
				// é chamada
				retorno = actionMapping.findForward("filtrarTabelaAuxiliar");
				sessao.setAttribute("tela", tela);
			}
		}

		// A pesquisa de tabelas auxiliares só será feita se o forward estiver
		// direcionado
		// para a página de manterTabelaAuxiliar
		if(retorno.getName().equalsIgnoreCase("manterTabelaAuxiliar")){
			// Seta a ordenação desejada do filtro
			filtroTabelaAuxiliar.setCampoOrderBy(FiltroTabelaAuxiliar.ID);
			// Pesquisa de tabelas auxiliares
			tabelasAuxiliares = fachada.pesquisarTabelaAuxiliar(filtroTabelaAuxiliar, pacoteNomeObjeto);

			if(tabelasAuxiliares == null || tabelasAuxiliares.isEmpty()){
				// Nenhum atividade cadastrado
				throw new ActionServletException("atencao.naocadastrado", null, titulo);
			}

			// Verifica o numero de objetos retornados
			if(tabelasAuxiliares.size() > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_MANUTENCAO){
				throw new ActionServletException("atencao.pesquisa.muitosregistros");
				// reportarErros(httpServletRequest,
				// "erro.pesquisa.muitosregistros");
				// retorno = actionMapping.findForward("telaAtencao");
			}

			// A coleção fica na sessão devido ao esquema de paginação
			sessao.setAttribute("tabelasAuxiliares", tabelasAuxiliares);
			// Envia o path do pacote na sessão
			sessao.setAttribute("pacoteNomeObjeto", pacoteNomeObjeto);
		}

		// Envia os objetos na sessão
		sessao.setAttribute("titulo", titulo);
		sessao.setAttribute("descricao", descricao);
		sessao.setAttribute("funcionalidadeTabelaAuxiliarManter", funcionalidadeTabelaAuxiliarManter);
		sessao.setAttribute("tamanhoMaximoCampo", Integer.valueOf(tamanhoMaximoCampo));

		// Devolve o mapeamento de retorno
		return retorno;
	}
}
