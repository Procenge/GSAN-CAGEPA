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
 * [UC3025] Pesquisar Boleto Bancário
 * Pré-processamento para a página de pesquisa de boleto bancario
 * 
 * @author Cinthya Cavalcanti
 * @created 23/09/2011
 */
public class ExibirPesquisarBoletoBancarioAction
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

		ActionForward retorno = actionMapping.findForward("pesquisarBoletoBancario");

		PesquisarBoletoBancarioActionForm pesquisarBoletoBancarioActionForm = (PesquisarBoletoBancarioActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de segurança
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
				pesquisarBoletoBancarioActionForm.setInscricaoImovelBoletoBancarioPesquisa("IMÓVEL INEXISTENTE");

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

			// Verifica se o tipo da consulta é arrecadador
			// se for os parametros de enviarDadosParametros serão mandados para
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
