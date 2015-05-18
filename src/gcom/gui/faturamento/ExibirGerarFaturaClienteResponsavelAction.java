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

package gcom.gui.faturamento;

import gcom.cadastro.cliente.Cliente;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.faturamento.conta.FiltroContaMotivoRevisao;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Pre-processamento gerar as faturas do cliente respons�vel
 * 
 * @author Rafael Pinto, Saulo Lima 16/09/2008
 */
public class ExibirGerarFaturaClienteResponsavelAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		List<Cliente> listaCliente = null;
		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarFatura");

		// Pega o formul�rio
		GerarFaturaClienteResponsavelActionForm gerarFaturaClienteResponsavelActionForm = (GerarFaturaClienteResponsavelActionForm) actionForm;

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		sessao.removeAttribute("PesquisarActionForm");

		if(Util.isVazioOuBranco(gerarFaturaClienteResponsavelActionForm.getIndicadorContasRevisao())){
			gerarFaturaClienteResponsavelActionForm.setIndicadorContasRevisao(ConstantesSistema.TODOS.toString());
		}

		// Pesquisar os motivos para conta em revis�o
		this.pesquisarContaMotivoRevisao(httpServletRequest);

		String addIdCliente = (String) httpServletRequest.getParameter("add");

		if(Util.isNaoNuloBrancoZero(addIdCliente) && "Ok".equals(addIdCliente)){

			String idClienteFiltro = gerarFaturaClienteResponsavelActionForm.getIdClienteFiltro();
			Integer idCliente = null;
			String nomeClienteFiltro = gerarFaturaClienteResponsavelActionForm.getNomeClienteFiltro();
			if(Util.isNaoNuloBrancoZero(idClienteFiltro)){
				idCliente = Integer.valueOf(idClienteFiltro);
				Cliente cliente = Fachada.getInstancia().consultarCliente(idCliente);
				if(cliente == null){
					idCliente = null;
					gerarFaturaClienteResponsavelActionForm.setIdClienteFiltro("");
					gerarFaturaClienteResponsavelActionForm.setNomeClienteFiltro("Cliente inexistente");

				}else{
					httpServletRequest.setAttribute("codigoClienteEncontrada", "true");
					nomeClienteFiltro = cliente.getNome();
					gerarFaturaClienteResponsavelActionForm.setNomeClienteFiltro(nomeClienteFiltro);
				}
			}

			listaCliente = (List<Cliente>) sessao.getAttribute("listaCliente");

			if(listaCliente == null){
				listaCliente = new ArrayList<Cliente>();
			}
			boolean idNaoExiste = true;
			for(Cliente cliente : listaCliente){
				if(cliente.getId().equals(idCliente)){
					idNaoExiste = false;
				}
			}
			if(idNaoExiste && idCliente != null){
				listaCliente.add(new Cliente(nomeClienteFiltro, idCliente));
			}

			popularClientesSelecionadosDoForm(listaCliente, gerarFaturaClienteResponsavelActionForm);
			sessao.setAttribute("listaCliente", listaCliente);
		}

		String removerIdCliente = (String) httpServletRequest.getParameter("remover");

		if(Util.isNaoNuloBrancoZero(removerIdCliente)){
			listaCliente = (List<Cliente>) sessao.getAttribute("listaCliente");
			List<Cliente> listaNova = new ArrayList<Cliente>();
			for(Cliente cliente : listaCliente){
				if(!cliente.getId().equals(Integer.valueOf(removerIdCliente))){
					listaNova.add(cliente);
				}
			}
			popularClientesSelecionadosDoForm(listaNova, gerarFaturaClienteResponsavelActionForm);
			sessao.setAttribute("listaCliente", listaNova);
			gerarFaturaClienteResponsavelActionForm.setIdClienteFiltro("");
			gerarFaturaClienteResponsavelActionForm.setNomeClienteFiltro("");
		}

		return retorno;
	}

	private void popularClientesSelecionadosDoForm(List<Cliente> listaCliente, GerarFaturaClienteResponsavelActionForm gerarFaturaClienteResponsavelActionForm){

		if(!Util.isVazioOrNulo(listaCliente)){
			String listaStr = "";
			String virgula = "";
			for(Cliente cliente : listaCliente){
				listaStr += virgula + cliente.getId();
				virgula = ",";
			}
			gerarFaturaClienteResponsavelActionForm.setClientesSelecionados(new String[] {listaStr});
		}
	}

	/**
	 * Pesquisa Conta Motivo Revis�o
	 * 
	 * @author Luciano Galvao
	 * @date 25/06/2013
	 */
	private void pesquisarContaMotivoRevisao(HttpServletRequest httpServletRequest){

		// Motivo de Revis�o
		FiltroContaMotivoRevisao filtroContaMotivoRevisao = new FiltroContaMotivoRevisao(FiltroContaMotivoRevisao.DESCRICAO);

		Collection colecaoContaMotivoRevisao = Fachada.getInstancia().pesquisar(filtroContaMotivoRevisao,
						ContaMotivoRevisao.class.getName());

		((ArrayList<ContaMotivoRevisao>) colecaoContaMotivoRevisao).add(0, new ContaMotivoRevisao());

		httpServletRequest.setAttribute("colecaoContaMotivoRevisao", colecaoContaMotivoRevisao);
	}

}
