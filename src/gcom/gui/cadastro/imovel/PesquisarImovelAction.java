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
 * Realiza a pesquisa de imovel de acordo com os par�metros informados
 * 
 * @author S�vio Luiz
 * @created 21 de Julho de 2005
 * @author eduardo henrique
 * @date 02/12/2008
 *       Inclus�o do Par�metro de N�mero do Im�vel na Consulta
 * @author eduardo henrique
 * @date 30/01/2009
 *       Corre��o no m�todo para desativa��o definitiva da Classe filtro e
 *       inclusao do numero do imovel na consulta.
 */

public class PesquisarImovelAction
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

		ActionForward retorno = actionMapping.findForward("listaImovel");

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		DynaValidatorForm pesquisarActionForm = (DynaValidatorForm) actionForm;

		// Recupera os par�metros do form
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

		// caso codigo cliente esteja nulo ent�o faz uma pesquisa por cliente
		// imovel
		if(idCliente != null && !idCliente.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

		}

		// caso numero hidrometro esteja nulo
		if(idHidrometro != null && !idHidrometro.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

		}

		// Insere os par�metros informados no filtro
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

		// inclui o parametro de pesquisa de im�veis os quais n�o possuem o
		// perfil de
		// tarifa social, cujo o id � 3.
		if(sessao.getAttribute("caminhoRetorno") != null && !sessao.getAttribute("caminhoRetorno").equals("")
						&& sessao.getAttribute("caminhoRetorno").equals("exibirInserirTarifaSocialImovelAction")){

		}

		if(numeroImovel != null && !numeroImovel.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
		}

		// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		boolean pesquisarImovelCondominio = false;
		// verfica se � so para pesquisar os imoveis condominios
		if(sessao.getAttribute("pesquisarImovelCondominio") != null){
			pesquisarImovelCondominio = true;
			sessao.removeAttribute("pesquisarImovelCondominio");
		}

		Collection clienteImoveis = null;

		// Obt�m a inst�ncia da Fachada
		Fachada fachada = Fachada.getInstancia();

		// 1� Passo - Pegar o total de registros atrav�s de um count da consulta que aparecer� na
		// tela
		int totalRegistros = fachada.pesquisarQuantidadeImovel(null, idLocalidade, codigoSetorComercial, idQuadra, null, lote, SubLote,
						idCliente, idMunicipio, cep, codigoBairro, idLogradouro, false, pesquisarImovelCondominio, idHidrometro,
						numeroImovel).intValue();

		// 2� Passo - Chamar a fun��o de Pagina��o passando o total de registros
		retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

		// 3� Passo - Obter a cole��o da consulta que aparecer� na tela passando o numero de paginas
		// da pesquisa que est� no request
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

			// Coloca a cole��o na sess�o
			sessao.setAttribute("colecaoClientesImoveis", clienteImoveis);
		}

		return retorno;
	}
}
