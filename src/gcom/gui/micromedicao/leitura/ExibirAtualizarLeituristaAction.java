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

package gcom.gui.micromedicao.leitura;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.Leiturista;
import gcom.micromedicao.FiltroLeiturista;
import gcom.micromedicao.hidrometro.FiltroHidrometroMarca;
import gcom.micromedicao.hidrometro.HidrometroMarca;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sun.org.apache.bcel.internal.generic.DLOAD;

/**
 * Descri��o da classe
 * 
 * @author Thiago Ten�rio
 * @date 30/10/2006
 */
public class ExibirAtualizarLeituristaAction
				extends GcomAction {

	/**
	 * Atualizar Agente Comercial - Leiturista
	 * Este caso de uso permite alterar um Agente Comercial
	 * 
	 * @author Thiago Ten�rio
	 * @date 31/10/2006
	 * @author eduardo henrique
	 * @date 10/06/2008
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 *         Alterado a nomea��o de Leiturista para Agente Comercial , onde o dado for exibido na
	 *         GUI, e adequa��es em rela��o ao UC.
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("atualizarLeiturista");

		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarLeituristaActionForm atualizarLeituristaActionForm = (AtualizarLeituristaActionForm) actionForm;

		if(httpServletRequest.getParameter("menu") != null){
			atualizarLeituristaActionForm.setEmpresaID("");
		}

		Fachada fachada = Fachada.getInstancia();

		Leiturista leiturista = null;

		String idLeiturista = null;

		if(httpServletRequest.getParameter("idLeiturista") != null){
			// tela do manter
			idLeiturista = (String) httpServletRequest.getParameter("idLeiturista");
			sessao.setAttribute("idLeiturista", idLeiturista);
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirManterLeituristaAction.do");
		}else if(sessao.getAttribute("idLeiturista") != null){
			// tela do filtrar
			idLeiturista = (String) sessao.getAttribute("idLeiturista");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarLeituristaAction.do");
		}else if(httpServletRequest.getParameter("idRegistroAtualizacao") != null){
			// link na tela de sucesso do inserir sistema esgoto
			idLeiturista = (String) httpServletRequest.getParameter("idRegistroAtualizacao");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarLeituristaAction.do?menu=sim");
		}

		if(idLeiturista == null){// Filtro/Pesquisa s� retornou apenas um Leiturista (n�o houve tela
			// anterior para sele��o da pesquisa)
			leiturista = (Leiturista) sessao.getAttribute("leiturista");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarLeituristaAction.do?menu=sim");
		}

		if(leiturista == null){

			if(idLeiturista != null && !idLeiturista.equals("")){

				FiltroLeiturista filtroLeiturista = new FiltroLeiturista();

				filtroLeiturista.adicionarParametro(new ParametroSimples(FiltroLeiturista.ID, idLeiturista));

				Collection<Leiturista> colecaoLeiturista = fachada.pesquisar(filtroLeiturista, Leiturista.class.getName());

				if(colecaoLeiturista != null && !colecaoLeiturista.isEmpty()){

					leiturista = (Leiturista) Util.retonarObjetoDeColecao(colecaoLeiturista);

				}
			}
		}

		if(leiturista != null){
			atualizarLeituristaActionForm.setCodigoLeiturista(leiturista.getId().toString());
			atualizarLeituristaActionForm.setDdd(leiturista.getCodigoDDD());
			atualizarLeituristaActionForm.setTelefone(leiturista.getNumeroFone());
			atualizarLeituristaActionForm.setNumeroImei(leiturista.getNumeroImei());

			if(leiturista.getFuncionario() != null){
				FiltroFuncionario filtroFuncionario = new FiltroFuncionario();

				filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, leiturista.getFuncionario().getId()));

				Collection colecaoFuncionario = fachada.pesquisar(filtroFuncionario, Funcionario.class.getName());

				if(colecaoFuncionario != null && !colecaoFuncionario.isEmpty()){

					Funcionario funcionario = (Funcionario) colecaoFuncionario.iterator().next();
					atualizarLeituristaActionForm.setIdFuncionario(funcionario.getId().toString());
					atualizarLeituristaActionForm.setDescricaoFuncionario(funcionario.getNome());

				}else{
					httpServletRequest.setAttribute("funcionarioEncontrado", "exception");
					atualizarLeituristaActionForm.setIdFuncionario("");
					atualizarLeituristaActionForm.setDescricaoFuncionario("FUNCIONARIO INEXISTENTE");
				}
			}

			if(leiturista.getCliente() != null){
				FiltroCliente filtroCliente = new FiltroCliente();

				filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, leiturista.getCliente().getId()));

				Collection colecaoCliente = fachada.pesquisar(filtroCliente, Cliente.class.getName());

				if(colecaoCliente != null && !colecaoCliente.isEmpty()){

					Cliente cliente = (Cliente) colecaoCliente.iterator().next();
					atualizarLeituristaActionForm.setIdCliente(cliente.getId().toString());
					atualizarLeituristaActionForm.setDescricaoCliente(cliente.getNome());
				}else{
					httpServletRequest.setAttribute("clienteEncontrado", "exception");
					atualizarLeituristaActionForm.setIdCliente("");
					atualizarLeituristaActionForm.setDescricaoCliente("CLIENTE INEXISTENTE");
				}
			}

			if(leiturista.getEmpresa() != null){
				atualizarLeituristaActionForm.setEmpresaID(leiturista.getEmpresa().getId().toString());
			}

			if(leiturista.getIndicadorUso() != null){
				atualizarLeituristaActionForm.setIndicadorUso(leiturista.getIndicadorUso().toString());
			}

			sessao.setAttribute("leituristaAtualizar", leiturista);
		}

		httpServletRequest.setAttribute("idLeiturista", idLeiturista);

		FiltroEmpresa filtroEmpresa = new FiltroEmpresa();

		Collection colecaoEmpresa = fachada.pesquisar(filtroEmpresa, Empresa.class.getName());

		sessao.setAttribute("colecaoEmpresa", colecaoEmpresa);

		// Pesquisas por 'enter'
		String idFuncionario = httpServletRequest.getParameter("idFuncionario");
		if(idFuncionario != null && !idFuncionario.trim().equals("")){
			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();

			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, new Integer(idFuncionario)));

			Collection colecaoFuncionario = fachada.pesquisar(filtroFuncionario, Funcionario.class.getName());

			if(colecaoFuncionario != null && !colecaoFuncionario.isEmpty()){

				Funcionario funcionario = (Funcionario) colecaoFuncionario.iterator().next();
				atualizarLeituristaActionForm.setIdFuncionario(funcionario.getId().toString());
				atualizarLeituristaActionForm.setDescricaoFuncionario(funcionario.getNome());

			}else{
				httpServletRequest.setAttribute("funcionarioEncontrado", "exception");
				atualizarLeituristaActionForm.setIdFuncionario("");
				atualizarLeituristaActionForm.setDescricaoFuncionario("FUNCIONARIO INEXISTENTE");
			}
		}

		String idCliente = httpServletRequest.getParameter("idCliente");
		if(idCliente != null && !idCliente.trim().equals("")){
			FiltroCliente filtroCliente = new FiltroCliente();

			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente));

			Collection colecaoCliente = fachada.pesquisar(filtroCliente, Cliente.class.getName());

			if(colecaoCliente != null && !colecaoCliente.isEmpty()){

				Cliente cliente = (Cliente) colecaoCliente.iterator().next();
				atualizarLeituristaActionForm.setIdCliente(cliente.getId().toString());
				atualizarLeituristaActionForm.setDescricaoCliente(cliente.getNome());
			}else{
				httpServletRequest.setAttribute("clienteEncontrado", "exception");
				atualizarLeituristaActionForm.setIdCliente("");
				atualizarLeituristaActionForm.setDescricaoCliente("CLIENTE INEXISTENTE");
			}
		}

		return retorno;

	}

}
