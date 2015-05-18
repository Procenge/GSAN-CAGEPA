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

package gcom.gui.micromedicao.hidrometro;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.bean.FiltroHidrometroHelper;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.util.Util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 * @created 8 de Setembro de 2005
 */
public class ExibirManterHidrometroAction
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

		ActionForward retorno = actionMapping.findForward("manterHidrometro");

		Fachada fachada = Fachada.getInstancia();
		// Obt�m o action form
		// HidrometroActionForm hidrometroActionForm = (HidrometroActionForm) actionForm;

		Collection colecaoHidrometro = null;

		Collection hidrometros2 = null;

		// Mudar isso quando implementar a parte de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		if(httpServletRequest.getParameter("limpaSessao") != null && !httpServletRequest.getParameter("limpaSessao").equals("")){
			// limpa os parametros do form de hidrometro do filtro
			sessao.removeAttribute("faixaInicial");
			sessao.removeAttribute("faixaFinal");
			sessao.removeAttribute("fixo");
			sessao.removeAttribute("codigoFormatoNumeracao");
		}

		if(sessao.getAttribute("i") != null){
			sessao.removeAttribute("i");
		}
		String atualizar = httpServletRequest.getParameter("atualizar");
		// Parte da verifica��o do filtro
		FiltroHidrometroHelper filtroHidrometroHelper = new FiltroHidrometroHelper();

		// Verifica se o filtro foi informado pela p�gina de filtragem de
		// hidrometro
		if(sessao.getAttribute("filtroHidrometroHelper") != null && sessao.getAttribute("voltarFiltrar") != null){

			filtroHidrometroHelper = (FiltroHidrometroHelper) sessao.getAttribute("filtroHidrometroHelper");
			sessao.removeAttribute("voltarFiltrar");
		}else{
			if(sessao.getAttribute("fixo") == null){
				// Caso o exibirManterHidrometro n�o tenha passado por algum esquema
				// de filtro,
				// a quantidade de registros � verificada para avaliar a
				// necessidade de filtragem
				filtroHidrometroHelper = new FiltroHidrometroHelper();

				// Se o limite de registros foi atingido, a p�gina de
				// filtragem � chamada
				retorno = actionMapping.findForward("filtrarHidrometro");
			}else if(httpServletRequest.getParameter("voltarFiltro") != null){
				retorno = actionMapping.findForward("filtrarHidrometro");
			}
		}

		// A pesquisa de hidrometros s� ser� feita se o forward estiver
		// direcionado
		// para a p�gina de manterHidrometro
		if(retorno.getName().equalsIgnoreCase("manterHidrometro")){
			// caso venha do FiltrarHidrometro e foi escolhido como parametros o
			// fixo e a faixa
			// da numera��o do hidrometro ent�o faz outra pesquisa sem o filtro.
			// Como � obrigat�rio a faixa caso coloque como parametro o fixo ,
			// ent�o
			// n�o � necessario fazer a verifica��o da faixa s� do fixo.
			if(sessao.getAttribute("fixo") != null && !sessao.getAttribute("fixo").equals("")){
				String fixo = (String) sessao.getAttribute("fixo");
				String faixaInicial = (String) sessao.getAttribute("faixaInicial");
				String faixaFinal = (String) sessao.getAttribute("faixaFinal");
				String codigoFormatoNumeracao = (String) sessao.getAttribute("codigoFormatoNumeracao");

				// 1� Passo - Pegar o total de registros atrav�s de um count da consulta
				// que aparecer� na tela
				String numeroFormatadoInicial = "";
				String numeroFormatadoFinal = "";

				if(codigoFormatoNumeracao.equals(Hidrometro.FORMATO_NUMERACAO_4_X_6.toString())){
					numeroFormatadoInicial = Util.adicionarZerosEsquedaNumero(6, faixaInicial);
					numeroFormatadoFinal = Util.adicionarZerosEsquedaNumero(6, faixaFinal);
				}else if(codigoFormatoNumeracao.equals(Hidrometro.FORMATO_NUMERACAO_5_X_5.toString())){
					numeroFormatadoInicial = Util.adicionarZerosEsquedaNumero(5, faixaInicial);
					numeroFormatadoFinal = Util.adicionarZerosEsquedaNumero(5, faixaFinal);
				}

				Integer totalRegistros = fachada.pesquisarNumeroHidrometroFaixaCount(fixo, fixo + numeroFormatadoInicial, fixo
								+ numeroFormatadoFinal);

				// 2� Passo - Chamar a fun��o de Pagina��o passando o total de registros
				retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

				colecaoHidrometro = fachada.pesquisarNumeroHidrometroFaixaPaginacao(fixo, faixaInicial, faixaFinal,
								((Integer) httpServletRequest
								.getAttribute("numeroPaginasPesquisa")));
				if(colecaoHidrometro == null || colecaoHidrometro.isEmpty()){
					// Nenhum hidrometro cadastrado
					throw new ActionServletException("atencao.pesquisa.nenhumresultado");
				}else{
					hidrometros2 = fachada.pesquisarNumeroHidrometroFaixa(fixo, faixaInicial, faixaFinal);
				}

				// seta um valor no form para que o bot�o
				// atualizarConjuntoHidrometro possa atualizar um
				// conjunto de hidrometro
				// hidrometroActionForm.setConjuntoHidrometro("1");
				httpServletRequest.setAttribute("conjuntoHidrometro", new Boolean(true));
			}else{

				// Aciona o controle de pagina��o para que sejam pesquisados apenas
				// os registros que aparecem na p�gina

				Integer totalRegistros = fachada.pesquisarHidrometroFiltroTotalRegistros(filtroHidrometroHelper);

				retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

				colecaoHidrometro = fachada.pesquisarHidrometroFiltro(filtroHidrometroHelper,
								(Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"));


				if(colecaoHidrometro == null || colecaoHidrometro.isEmpty()){

					// Nenhum hidrometro cadastrado
					throw new ActionServletException("atencao.pesquisa.nenhumresultado");
				}else if(atualizar != null && colecaoHidrometro.size() == 1){

					Hidrometro hidrometro = (Hidrometro) colecaoHidrometro.iterator().next();
					httpServletRequest.setAttribute("idRegistroAtualizacao", hidrometro.getId());

					retorno = actionMapping.findForward("exibirAtualizarHidrometro");
				}
			}

			if(hidrometros2 != null && !hidrometros2.isEmpty()){

				sessao.setAttribute("hidrometros2", hidrometros2);
			}

			sessao.setAttribute("hidrometros", colecaoHidrometro);
			sessao.setAttribute("parametroInformado", "sim");

		}

		httpServletRequest.setAttribute("nomeCampo", "numeroHidrometro");
		return retorno;
	}
}
