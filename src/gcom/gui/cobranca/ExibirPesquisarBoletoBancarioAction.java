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

package gcom.gui.cobranca;

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.FiltroArrecadador;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3025] Pesquisar Boleto Banc�rio
 * Pr�-processamento para a p�gina de pesquisa de boleto bancario
 * 
 * @author Cinthya Cavalcanti
 * @created 23/09/2011
 */
public class ExibirPesquisarBoletoBancarioAction
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

		ActionForward retorno = actionMapping.findForward("pesquisarBoletoBancario");

		PesquisarBoletoBancarioActionForm pesquisarBoletoBancarioActionForm = (PesquisarBoletoBancarioActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		if(httpServletRequest.getParameter("acao") != null && httpServletRequest.getParameter("acao").equals("abrir")){
			pesquisarBoletoBancarioActionForm.setBancoBoletoBancarioPesquisa("");
		}

		if(httpServletRequest.getParameter("indicador") != null){
			sessao.setAttribute("indicador", httpServletRequest.getParameter("indicador"));
		}

		if(httpServletRequest.getParameter("idDataInicialEntrada") != null){
			pesquisarBoletoBancarioActionForm.setDataInicialEntradaBoletoBancarioPesquisa(httpServletRequest
							.getParameter("idDataInicialEntrada"));
		}

		if(httpServletRequest.getParameter("idDataFinalEntrada") != null){
			pesquisarBoletoBancarioActionForm.setDataFinalEntradaBoletoBancarioPesquisa(httpServletRequest
							.getParameter("idDataFinalEntrada"));
		}

		if(httpServletRequest.getParameter("idDataInicialVencimento") != null){
			pesquisarBoletoBancarioActionForm.setDataInicialVencimentoBoletoBancarioPesquisa(httpServletRequest
							.getParameter("idDataInicialVencimento"));
		}

		if(httpServletRequest.getParameter("idDataFinalVencimento") != null){
			pesquisarBoletoBancarioActionForm.setDataFinalVencimentoBoletoBancarioPesquisa(httpServletRequest
							.getParameter("idDataFinalVencimento"));
		}

		String idArrecadador = null;
		if(httpServletRequest.getParameter("reset") != null && httpServletRequest.getParameter("reset").equals("1")){

			pesquisarBoletoBancarioActionForm.setBancoBoletoBancarioPesquisa("");
			pesquisarBoletoBancarioActionForm.setDescricaoBancoBoletoBancarioPesquisa("");

			pesquisarBoletoBancarioActionForm.setImovelBoletoBancarioPesquisa("");
			pesquisarBoletoBancarioActionForm.setInscricaoImovelBoletoBancarioPesquisa("");

			pesquisarBoletoBancarioActionForm.setClienteBoletoBancarioPesquisa("");
			pesquisarBoletoBancarioActionForm.setNomeClienteBoletoBancarioPesquisa("");
		}

		if(httpServletRequest.getParameter("idArrecadador") != null
						&& !httpServletRequest.getParameter("idArrecadador").trim().equalsIgnoreCase("")){

			idArrecadador = (String) httpServletRequest.getParameter("idArrecadador");

		}else if(pesquisarBoletoBancarioActionForm.getBancoBoletoBancarioPesquisa() != null
						&& !pesquisarBoletoBancarioActionForm.getBancoBoletoBancarioPesquisa().equals("")){

			idArrecadador = pesquisarBoletoBancarioActionForm.getBancoBoletoBancarioPesquisa();
		}

		if(idArrecadador != null && !idArrecadador.equals("")){

			FiltroArrecadador filtroArrecadador = new FiltroArrecadador();
			filtroArrecadador.adicionarParametro(new ParametroSimples(FiltroArrecadador.CODIGO_AGENTE, idArrecadador));
			filtroArrecadador.adicionarCaminhoParaCarregamentoEntidade("cliente");
			Collection colecaoArrecadador = fachada.pesquisar(filtroArrecadador, Arrecadador.class.getName());

			if(colecaoArrecadador != null && !colecaoArrecadador.isEmpty()){

				Arrecadador arrecadador = (Arrecadador) Util.retonarObjetoDeColecao(colecaoArrecadador);

				pesquisarBoletoBancarioActionForm.setBancoBoletoBancarioPesquisa(arrecadador.getCodigoAgente().toString());
				pesquisarBoletoBancarioActionForm.setDescricaoBancoBoletoBancarioPesquisa(arrecadador.getCliente().getNome());

				httpServletRequest.setAttribute("nomeCampo", "bancoBoletoBancarioPesquisa");
			}else{

				pesquisarBoletoBancarioActionForm.setBancoBoletoBancarioPesquisa("");
				pesquisarBoletoBancarioActionForm.setDescricaoBancoBoletoBancarioPesquisa("ARRECADADOR INEXISTENTE");

				httpServletRequest.setAttribute("nomeCampo", "bancoBoletoBancarioPesquisa");
				httpServletRequest.setAttribute("corBanco", "exception");
			}
		}else{

			pesquisarBoletoBancarioActionForm.setBancoBoletoBancarioPesquisa("");
			pesquisarBoletoBancarioActionForm.setDescricaoBancoBoletoBancarioPesquisa("");

		}

		String idImovel = null;
		if(httpServletRequest.getParameter("idImovel") != null && !httpServletRequest.getParameter("idImovel").trim().equalsIgnoreCase("")){

			idImovel = (String) httpServletRequest.getParameter("idImovel");

		}else if(pesquisarBoletoBancarioActionForm.getImovelBoletoBancarioPesquisa() != null
						&& !pesquisarBoletoBancarioActionForm.getImovelBoletoBancarioPesquisa().trim().equalsIgnoreCase("")){

			idImovel = pesquisarBoletoBancarioActionForm.getImovelBoletoBancarioPesquisa();
		}

		if(idImovel != null && !idImovel.equals("")){

			FiltroImovel filtroImovel = new FiltroImovel();

			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);

			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));

			Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			if(colecaoImovel != null && !colecaoImovel.isEmpty()){

				Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);

				pesquisarBoletoBancarioActionForm.setImovelBoletoBancarioPesquisa(imovel.getId().toString());
				pesquisarBoletoBancarioActionForm.setInscricaoImovelBoletoBancarioPesquisa(imovel.getInscricaoFormatada());

				httpServletRequest.setAttribute("nomeCampo", "imovelBoletoBancarioPesquisa");
			}else{

				pesquisarBoletoBancarioActionForm.setImovelBoletoBancarioPesquisa("");
				pesquisarBoletoBancarioActionForm.setInscricaoImovelBoletoBancarioPesquisa("IM�VEL INEXISTENTE");

				httpServletRequest.setAttribute("nomeCampo", "imovelBoletoBancarioPesquisa");
				httpServletRequest.setAttribute("corImovel", "exception");
			}
		}else{
			pesquisarBoletoBancarioActionForm.setImovelBoletoBancarioPesquisa("");
			pesquisarBoletoBancarioActionForm.setInscricaoImovelBoletoBancarioPesquisa("");
		}

		String idCliente = null;
		if(httpServletRequest.getParameter("idCliente") != null
						&& !httpServletRequest.getParameter("idCliente").trim().equalsIgnoreCase("")){

			idCliente = (String) httpServletRequest.getParameter("idCliente");

		}else if(pesquisarBoletoBancarioActionForm.getClienteBoletoBancarioPesquisa() != null
						&& !pesquisarBoletoBancarioActionForm.getClienteBoletoBancarioPesquisa().trim().equalsIgnoreCase("")){

			idCliente = pesquisarBoletoBancarioActionForm.getClienteBoletoBancarioPesquisa();
		}

		if(idCliente != null && !idCliente.equals("")){

			FiltroCliente filtroCliente = new FiltroCliente();
			filtroCliente.adicionarCaminhoParaCarregamentoEntidade("cliente");
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente));
			Collection colecaoCliente = fachada.pesquisar(filtroCliente, Cliente.class.getName());

			if(colecaoCliente != null && !colecaoCliente.isEmpty()){

				Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);

				pesquisarBoletoBancarioActionForm.setClienteBoletoBancarioPesquisa(cliente.getId().toString());
				pesquisarBoletoBancarioActionForm.setNomeClienteBoletoBancarioPesquisa(cliente.getNome());

				httpServletRequest.setAttribute("nomeCampo", "clienteBoletoBancarioPesquisa");
			}else{

				pesquisarBoletoBancarioActionForm.setClienteBoletoBancarioPesquisa("");
				pesquisarBoletoBancarioActionForm.setNomeClienteBoletoBancarioPesquisa("CLIENTE INEXISTENTE");

				httpServletRequest.setAttribute("nomeCampo", "clienteBoletoBancarioPesquisa");
				httpServletRequest.setAttribute("corCliente", "exception");
			}
		}else{
			pesquisarBoletoBancarioActionForm.setClienteBoletoBancarioPesquisa("");
			pesquisarBoletoBancarioActionForm.setNomeClienteBoletoBancarioPesquisa("");
		}

		if(httpServletRequest.getParameter("tipoConsulta") != null && !httpServletRequest.getParameter("tipoConsulta").equals("")){

			// Verifica se o tipo da consulta � arrecadador
			// se for os parametros de enviarDadosParametros ser�o mandados para
			// a pagina boleto_bancario_pesquisar.jsp
			if(httpServletRequest.getParameter("tipoConsulta").equals("arrecadador")){
				pesquisarBoletoBancarioActionForm.setBancoBoletoBancarioPesquisa(httpServletRequest.getParameter("idCampoEnviarDados"));
				pesquisarBoletoBancarioActionForm.setDescricaoBancoBoletoBancarioPesquisa(httpServletRequest
								.getParameter("descricaoCampoEnviarDados"));

				sessao.removeAttribute("caminhoRetornoTelaPesquisaArrecadador");

			}else if(httpServletRequest.getParameter("tipoConsulta").equals("imovel")){
				pesquisarBoletoBancarioActionForm.setImovelBoletoBancarioPesquisa(httpServletRequest.getParameter("idCampoEnviarDados"));
				pesquisarBoletoBancarioActionForm.setInscricaoImovelBoletoBancarioPesquisa(httpServletRequest
								.getParameter("descricaoCampoEnviarDados"));

				sessao.removeAttribute("caminhoRetornoTelaPesquisaImovel");

			}else if(httpServletRequest.getParameter("tipoConsulta").equals("cliente")){
				pesquisarBoletoBancarioActionForm.setClienteBoletoBancarioPesquisa(httpServletRequest.getParameter("idCampoEnviarDados"));
				pesquisarBoletoBancarioActionForm.setNomeClienteBoletoBancarioPesquisa(httpServletRequest
								.getParameter("descricaoCampoEnviarDados"));

				sessao.removeAttribute("caminhoRetornoTelaPesquisaCliente");
			}
		}

		return retorno;
	}

}
