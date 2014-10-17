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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.EquipeComponentes;
import gcom.atendimentopublico.ordemservico.FiltroServicoPerfilTipo;
import gcom.atendimentopublico.ordemservico.ServicoPerfilTipo;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel pela pr�-exibi��o da p�gina de atualizar liga��o de esgoto
 * 
 * @author Leonardo Regis
 * @created 18 de Julho de 2006
 */
public class ExibirInserirEquipeAction
				extends GcomAction {

	/**
	 * Exibi��o de inser��o de equipe.
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta Retorno (Forward = Exibi��o da Tela de Inser��o)
		ActionForward retorno = actionMapping.findForward("inserirEquipe");
		// Form
		InserirEquipeActionForm inserirEquipeActionForm = (InserirEquipeActionForm) actionForm;
		// Sess�o
		HttpSession sessao = httpServletRequest.getSession(false);
		// Fachada
		Fachada fachada = Fachada.getInstancia();
		// Testa unidade organizacional
		if(inserirEquipeActionForm.getUnidadeOrganizacionalId() != null && !inserirEquipeActionForm.getUnidadeOrganizacionalId().equals("")){
			getUnidadeOrganizacional(inserirEquipeActionForm, httpServletRequest);
		}
		// Testa tipo perfil servi�o
		if(inserirEquipeActionForm.getTipoPerfilServicoId() != null && !inserirEquipeActionForm.getTipoPerfilServicoId().equals("")){
			getTipoPerfilServico(inserirEquipeActionForm, httpServletRequest);
		}
		// Testa se � pra remover equipe componente
		if(inserirEquipeActionForm.getDeleteComponente() != null && !inserirEquipeActionForm.getDeleteComponente().equals("")){
			removeEquipeComponente(inserirEquipeActionForm);
		}else{
			// Testa se � pra adicionar componente ou n�o
			if(httpServletRequest.getParameter("method") != null){
				retorno = actionMapping.findForward("inserirEquipeComponente");
				inserirEquipeActionForm.setResponsavelId("");
				inserirEquipeActionForm.setFuncionarioId("");
				inserirEquipeActionForm.setFuncionarioName("");
				inserirEquipeActionForm.setFuncionario(null);
				inserirEquipeActionForm.setEquipeComponenteNome("");
				inserirEquipeActionForm.setMethod("add");
			}else{
				if(!inserirEquipeActionForm.getMethod().equals("")){
					retorno = actionMapping.findForward("inserirEquipeComponente");

					// Cria objeto componente
					EquipeComponentes equipeComponentes = null;
					if(((inserirEquipeActionForm.getEquipeComponenteNome() != null && !inserirEquipeActionForm.getEquipeComponenteNome()
									.equals("")) || (inserirEquipeActionForm.getFuncionarioId() != null && !inserirEquipeActionForm
									.getFuncionarioId().equals("")))
									&& inserirEquipeActionForm.getValidaFuncionario().equals("false")){
						// Recupera equipe componente vindo do popup
						equipeComponentes = getEquipeComponente(inserirEquipeActionForm);
						// Reseta informa��es vindas do popup
						resetPopup(inserirEquipeActionForm);
						// Faz as valida��es de inser��o de equipe
						fachada.validarExibirInsercaoEquipeComponentes(inserirEquipeActionForm.getEquipeComponentes(), equipeComponentes);
						// Seta componente na cole��o
						setColecaoEquipeComponentes(inserirEquipeActionForm, equipeComponentes);
						// Seta retorno
						retorno = actionMapping.findForward("inserirEquipe");
						inserirEquipeActionForm.setMethod("");
					}else{
						// Testa funcion�rio
						if(inserirEquipeActionForm.getFuncionarioId() != null && !inserirEquipeActionForm.getFuncionarioId().equals("")){
							retorno = getFuncionario(actionMapping, inserirEquipeActionForm, httpServletRequest);
						}
					}
				}
			}
		}

		if(httpServletRequest.getParameter("tipoConsulta") != null
						&& httpServletRequest.getParameter("tipoConsulta").equalsIgnoreCase("funcionario")){
			String codigoPesquisa = httpServletRequest.getParameter("idCampoEnviarDados");
			String descricaoPesquisa = httpServletRequest.getParameter("descricaoCampoEnviarDados");
			Funcionario funcionario = new Funcionario();
			inserirEquipeActionForm.setFuncionarioId(codigoPesquisa);
			inserirEquipeActionForm.setFuncionarioName(descricaoPesquisa);
			inserirEquipeActionForm.setEquipeComponenteNome("");
			funcionario.setId(new Integer(codigoPesquisa));
			funcionario.setNome(descricaoPesquisa);
			inserirEquipeActionForm.setFuncionario(funcionario);

			sessao.setAttribute("funcionarioIdEncontrada", "true");

			retorno = actionMapping.findForward("inserirEquipeComponente");
		}

		return retorno;
	}

	/**
	 * Seta novo Componente na Cole��o
	 * 
	 * @author Leonardo Regis
	 * @date 29/07/2006
	 * @param inserirEquipeActionForm
	 * @param equipeComponentes
	 */
	private void setColecaoEquipeComponentes(InserirEquipeActionForm inserirEquipeActionForm, EquipeComponentes equipeComponentes){

		inserirEquipeActionForm.getEquipeComponentes().add(equipeComponentes);
		inserirEquipeActionForm.setTamanhoColecao(inserirEquipeActionForm.getEquipeComponentes().size() + "");
	}

	/**
	 * Recupera objeto Equipe Componente com informa��es vindas da tela
	 * 
	 * @author Leonardo Regis
	 * @date 29/07/2006
	 * @param inserirEquipeActionForm
	 * @param equipeComponentes
	 */
	private EquipeComponentes getEquipeComponente(InserirEquipeActionForm inserirEquipeActionForm){

		EquipeComponentes equipeComponentes;
		equipeComponentes = new EquipeComponentes();
		if(inserirEquipeActionForm.getFuncionarioId() != null && !inserirEquipeActionForm.getFuncionarioId().equals("")){
			equipeComponentes.setComponentes("");
			equipeComponentes.setFuncionario(inserirEquipeActionForm.getFuncionario());
			if(inserirEquipeActionForm.getResponsavelId() != null && !inserirEquipeActionForm.getResponsavelId().equals("")){
				equipeComponentes.setIndicadorResponsavel(new Short(inserirEquipeActionForm.getResponsavelId()));
			}
		}else{
			equipeComponentes.setComponentes(inserirEquipeActionForm.getEquipeComponenteNome().toUpperCase());
			equipeComponentes.setFuncionario(null);

			if(inserirEquipeActionForm.getResponsavelId() != null && !inserirEquipeActionForm.getResponsavelId().equals("")){
				equipeComponentes.setIndicadorResponsavel(new Short(inserirEquipeActionForm.getResponsavelId()));
			}
		}
		equipeComponentes.setUltimaAlteracao(new Date());
		return equipeComponentes;
	}

	/**
	 * Reseta informa��es vindas do popup
	 * 
	 * @author Leonardo Regis
	 * @date 29/07/2006
	 * @param inserirEquipeActionForm
	 * @param equipeComponentes
	 */
	private void resetPopup(InserirEquipeActionForm inserirEquipeActionForm){

		inserirEquipeActionForm.setResponsavelId("");
		inserirEquipeActionForm.setEquipeComponenteNome("");
		inserirEquipeActionForm.setFuncionario(null);
		inserirEquipeActionForm.setFuncionarioId("");
		inserirEquipeActionForm.setFuncionarioName("");
	}

	/**
	 * Recupera Funcion�rio
	 * 
	 * @author Leonardo Regis
	 * @date 29/07/2006
	 * @param inserirEquipeActionForm
	 * @param equipeComponentes
	 */
	private ActionForward getFuncionario(ActionMapping actionMapping, InserirEquipeActionForm inserirEquipeActionForm,
					HttpServletRequest httpServletRequest){

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		ActionForward retorno;
		// [F0004] Valida Funcion�rio
		// if (isValidateFuncionario(inserirEquipeActionForm)) {
		// Filtra Funcionario
		FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
		filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, inserirEquipeActionForm.getFuncionarioId()));

		// Recupera Funcionario
		Collection colecaoFuncionario = fachada.pesquisar(filtroFuncionario, Funcionario.class.getName());
		if(colecaoFuncionario != null && !colecaoFuncionario.isEmpty()){
			sessao.setAttribute("funcionarioIdEncontrada", "true");
			Funcionario funcionario = (Funcionario) colecaoFuncionario.iterator().next();
			inserirEquipeActionForm.setFuncionario(funcionario);
			inserirEquipeActionForm.setFuncionarioName(funcionario.getNome());
		}else{
			sessao.removeAttribute("funcionarioIdEncontrada");
			inserirEquipeActionForm.setFuncionarioId("");
			inserirEquipeActionForm.setFuncionario(null);
			inserirEquipeActionForm.setFuncionarioName("Funcion�rio inexistente");
		}
		inserirEquipeActionForm.setValidaFuncionario("false");
		// }
		retorno = actionMapping.findForward("inserirEquipeComponente");
		return retorno;
	}

	/**
	 * Recupera Tipo Perfil Servi�o
	 * 
	 * @author Leonardo Regis
	 * @date 29/07/2006
	 * @param inserirEquipeActionForm
	 * @param equipeComponentes
	 */
	private void getTipoPerfilServico(InserirEquipeActionForm inserirEquipeActionForm, HttpServletRequest httpServletRequest){

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		// [F0002] Valida Tipo Perfil Servi�o
		// if (isValidateTipoPerfilServico(inserirEquipeActionForm)) {
		// Filtra
		FiltroServicoPerfilTipo filtroServicoPerfilTipo = new FiltroServicoPerfilTipo();
		filtroServicoPerfilTipo.adicionarParametro(new ParametroSimples(FiltroServicoPerfilTipo.ID, inserirEquipeActionForm
						.getTipoPerfilServicoId()));
		// Recupera
		Collection colecaoServicoPerfilTipo = fachada.pesquisar(filtroServicoPerfilTipo, ServicoPerfilTipo.class.getName());
		if(colecaoServicoPerfilTipo != null && !colecaoServicoPerfilTipo.isEmpty()){
			sessao.setAttribute("tipoPerfilServicoIdEncontrada", "true");
			ServicoPerfilTipo servicoPerfilTipo = (ServicoPerfilTipo) colecaoServicoPerfilTipo.iterator().next();
			inserirEquipeActionForm.setServicoPerfilTipo(servicoPerfilTipo);
			inserirEquipeActionForm.setTipoPerfilServicoDescricao(servicoPerfilTipo.getDescricao());
			if(servicoPerfilTipo.getComponentesEquipe() != null){
				inserirEquipeActionForm.setQtdeComponentesEquipe(servicoPerfilTipo.getComponentesEquipe().toString());
			}else{
				inserirEquipeActionForm.setQtdeComponentesEquipe("");
			}
		}else{
			sessao.removeAttribute("tipoPerfilServicoIdEncontrada");
			inserirEquipeActionForm.setTipoPerfilServicoId("");
			inserirEquipeActionForm.setServicoPerfilTipo(null);
			inserirEquipeActionForm.setTipoPerfilServicoDescricao("Tipo Perfil Servi�o inexistente");
			inserirEquipeActionForm.setQtdeComponentesEquipe("-1");
		}
		inserirEquipeActionForm.setValidaTipoPerfilServico("false");
		// }
	}

	/**
	 * Recupera Unidade Organizacional
	 * 
	 * @author Leonardo Regis
	 * @date 29/07/2006
	 * @param inserirEquipeActionForm
	 * @param equipeComponentes
	 */
	private void getUnidadeOrganizacional(InserirEquipeActionForm inserirEquipeActionForm, HttpServletRequest httpServletRequest){

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		// [F0001] Valida Unidade Organizacional
		// if (isValidateUnidadeOrganizacional(inserirEquipeActionForm)) {
		// Filtra Unidade Organizacional
		FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
		filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, inserirEquipeActionForm
						.getUnidadeOrganizacionalId()));
		// Recupera Unidade Organizacional
		Collection colecaoUnidadeOrganizacional = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());
		if(colecaoUnidadeOrganizacional != null && !colecaoUnidadeOrganizacional.isEmpty()){
			sessao.setAttribute("unidadeOrganizacionalIdEncontrada", "true");

			UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) colecaoUnidadeOrganizacional.iterator().next();
			inserirEquipeActionForm.setUnidadeOrganizacional(unidadeOrganizacional);
			inserirEquipeActionForm.setUnidadeOrganizacionalDescricao(unidadeOrganizacional.getDescricao());
		}else{
			sessao.removeAttribute("unidadeOrganizacionalIdEncontrada");
			inserirEquipeActionForm.setUnidadeOrganizacionalId("");
			inserirEquipeActionForm.setUnidadeOrganizacional(null);
			inserirEquipeActionForm.setUnidadeOrganizacionalDescricao("Unidade Organizacional inexistente");
		}
		inserirEquipeActionForm.setValidaUnidadeOrganizacional("false");
		// }
	}

	/**
	 * Remove Equipe Componente da Cole��o
	 * 
	 * @author Leonardo Regis
	 * @date 29/07/2006
	 * @param inserirEquipeActionForm
	 * @param equipeComponentes
	 */
	private void removeEquipeComponente(InserirEquipeActionForm inserirEquipeActionForm){

		Collection newEquipeComponentesCollection = new ArrayList();
		int index = 0;
		for(Iterator iter = inserirEquipeActionForm.getEquipeComponentes().iterator(); iter.hasNext();){
			index++;
			EquipeComponentes element = (EquipeComponentes) iter.next();
			if(index != new Integer(inserirEquipeActionForm.getDeleteComponente()).intValue()){
				newEquipeComponentesCollection.add(element);
			}
		}
		inserirEquipeActionForm.setEquipeComponentes(newEquipeComponentesCollection);
		inserirEquipeActionForm.setTamanhoColecao(inserirEquipeActionForm.getEquipeComponentes().size() + "");
		inserirEquipeActionForm.setDeleteComponente("");
		resetPopup(inserirEquipeActionForm);
	}

	/**
	 * Valida Unidade Organizacional
	 * 
	 * @author Leonardo Regis
	 * @date 25/07/2006
	 * @return est� validando unidade organizacional?
	 */
	/*
	 * private boolean isValidateUnidadeOrganizacional(InserirEquipeActionForm form) {
	 * boolean toReturn = false;
	 * if (form.getValidaUnidadeOrganizacional().equalsIgnoreCase("true")) {
	 * toReturn = true;
	 * }
	 * return toReturn;
	 * }
	 */

	/**
	 * Valida Tipo de Perfil do Servi�o
	 * 
	 * @author Leonardo Regis
	 * @date 25/07/2006
	 * @return est� validando tipo de perfil do servi�o?
	 */
	/*
	 * private boolean isValidateTipoPerfilServico(InserirEquipeActionForm form) {
	 * boolean toReturn = false;
	 * if (form.getValidaTipoPerfilServico().equalsIgnoreCase("true")) {
	 * toReturn = true;
	 * }
	 * return toReturn;
	 * }
	 */

	/**
	 * Valida Funcion�rio
	 * 
	 * @author Leonardo Regis
	 * @date 28/07/2006
	 * @return est� validando funcion�rio?
	 */
	/*
	 * private boolean isValidateFuncionario(InserirEquipeActionForm form) {
	 * boolean toReturn = false;
	 * if (form.getValidaFuncionario().equalsIgnoreCase("true")) {
	 * toReturn = true;
	 * }
	 * return toReturn;
	 * }
	 */
}