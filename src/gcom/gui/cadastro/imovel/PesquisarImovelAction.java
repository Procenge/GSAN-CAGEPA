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

package gcom.gui.cadastro.imovel;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Realiza a pesquisa de imovel de acordo com os parâmetros informados
 * 
 * @author Sávio Luiz
 * @created 21 de Julho de 2005
 * @author eduardo henrique
 * @date 02/12/2008
 *       Inclusão do Parâmetro de Número do Imóvel na Consulta
 * @author eduardo henrique
 * @date 30/01/2009
 *       Correção no método para desativação definitiva da Classe filtro e
 *       inclusao do numero do imovel na consulta.
 */

public class PesquisarImovelAction
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

		ActionForward retorno = actionMapping.findForward("listaImovel");

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;

		// Recupera os parâmetros do form
		String idLocalidade = (String) pesquisarActionForm.get("idLocalidade");
		String codigoSetorComercial = (String) pesquisarActionForm.get("codigoSetorComercial");
		String idQuadra = (String) pesquisarActionForm.get("idQuadra");
		// String idHidrometroHistInst = (String) pesquisarActionForm.get("idHidrometroHistInst");
		String lote = (String) pesquisarActionForm.get("lote");
		String SubLote = (String) pesquisarActionForm.get("subLote");
		String idCliente = (String) pesquisarActionForm.get("idCliente");
		String idHidrometro = (String) pesquisarActionForm.get("idHidrometro");
		String cep = (String) pesquisarActionForm.get("cep");
		String codigoBairro = (String) pesquisarActionForm.get("codigoBairroImovel");
		String idMunicipio = (String) pesquisarActionForm.get("idMunicipioImovel");
		String idLogradouro = (String) pesquisarActionForm.get("idLogradouro");
		String numeroImovel = (String) pesquisarActionForm.get("numeroImovel");

		boolean peloMenosUmParametroInformado = false;

		// caso codigo cliente esteja nulo então faz uma pesquisa por cliente
		// imovel
		if(idCliente != null && !idCliente.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

		}

		// caso numero hidrometro esteja nulo
		if(idHidrometro != null && !idHidrometro.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

		}

		// Insere os parâmetros informados no filtro
		if(idLocalidade != null && !idLocalidade.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

		}

		if(codigoSetorComercial != null && !codigoSetorComercial.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

		}

		if(idQuadra != null && !idQuadra.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

		}
		if(lote != null && !lote.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

		}

		if(SubLote != null && !SubLote.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

		}

		if(cep != null && !cep.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

		}

		if(idMunicipio != null && !idMunicipio.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

		}

		if(codigoBairro != null && !codigoBairro.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

		}

		if(idLogradouro != null && !idLogradouro.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

		}

		// inclui o parametro de pesquisa de imóveis os quais não possuem o
		// perfil de
		// tarifa social, cujo o id é 3.
		if(sessao.getAttribute("caminhoRetorno") != null && !sessao.getAttribute("caminhoRetorno").equals("")
						&& sessao.getAttribute("caminhoRetorno").equals("exibirInserirTarifaSocialImovelAction")){

		}

		if(numeroImovel != null && !numeroImovel.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
		}

		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		boolean pesquisarImovelCondominio = false;
		// verfica se é so para pesquisar os imoveis condominios
		if(sessao.getAttribute("pesquisarImovelCondominio") != null){
			pesquisarImovelCondominio = true;
			sessao.removeAttribute("pesquisarImovelCondominio");
		}

		Collection clienteImoveis = null;

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		// 1º Passo - Pegar o total de registros através de um count da consulta que aparecerá na
		// tela
		int totalRegistros = fachada.pesquisarQuantidadeImovel(null, idLocalidade, codigoSetorComercial, idQuadra, null, lote, SubLote,
						idCliente, idMunicipio, cep, codigoBairro, idLogradouro, false, pesquisarImovelCondominio, idHidrometro,
						numeroImovel).intValue();

		// 2º Passo - Chamar a função de Paginação passando o total de registros
		retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

		// 3º Passo - Obter a coleção da consulta que aparecerá na tela passando o numero de paginas
		// da pesquisa que está no request
		clienteImoveis = fachada.pesquisarImovelInscricao(null, idLocalidade, codigoSetorComercial, idQuadra, idHidrometro, lote, SubLote,
						idCliente, idMunicipio, cep, codigoBairro, idLogradouro, pesquisarImovelCondominio, numeroImovel,
						((Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa")));

		if(clienteImoveis == null || clienteImoveis.isEmpty()){
			// Nenhuma imovel cadastrado
			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "imovel");
		}else if(clienteImoveis.size() > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_PESQUISA){
			// Muitos registros encontrados
			throw new ActionServletException("atencao.pesquisa.muitosregistros");
		}else{

			// Coloca a coleção na sessão
			sessao.setAttribute("colecaoClientesImoveis", clienteImoveis);
		}

		return retorno;
	}
}
