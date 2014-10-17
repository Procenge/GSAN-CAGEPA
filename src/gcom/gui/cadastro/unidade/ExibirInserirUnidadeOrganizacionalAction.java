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

package gcom.gui.cadastro.unidade;

import gcom.atendimentopublico.registroatendimento.FiltroMeioSolicitacao;
import gcom.atendimentopublico.registroatendimento.MeioSolicitacao;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.FiltroUnidadeTipo;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que define o pré-processamento da página de inserção unidade organizacional
 * 
 * @author Rafael Pinto
 * @created 25/07/2006
 */
public class ExibirInserirUnidadeOrganizacionalAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirInserirUnidadeOrganizacional");

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		InserirUnidadeOrganizacionalActionForm inserirUnidadeOrganizacionalActionForm = (InserirUnidadeOrganizacionalActionForm) actionForm;

		// pega as colecoes
		Collection colecaoUnidadeTipo = this.consultarUnidadeTipo(fachada, sessao);
		Collection colecaoEmpresa = this.consultarEmpresa(fachada, sessao);
		Collection colecaoGerenciaRegional = this.consultarGerenciaRegional(fachada, sessao);

		// validar exibição da unidade tipo caso seja informada
		String idUnidadeTipo = httpServletRequest.getParameter("unidadeTipo");

		if(idUnidadeTipo != null && !idUnidadeTipo.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			UnidadeTipo unidadeTipo = this.retornaUnidadeTipoPorId(colecaoUnidadeTipo, new Integer(idUnidadeTipo));

			String cod = unidadeTipo.getCodigoTipo();

			if(cod != null){

				inserirUnidadeOrganizacionalActionForm.setCodigoUnidadeTipo(cod);

				if(cod.equals(UnidadeTipo.UNIDADE_TIPO_LOCALIDADE) || cod.equals(UnidadeTipo.UNIDADE_TIPO_GERENCIA_REGIONAL)
								|| cod.equals(UnidadeTipo.UNIDADE_TIPO_CENTRALIZADORA)){

					Empresa empresaPrincipal = retornaEmpresaPrincipal(colecaoEmpresa);

					inserirUnidadeOrganizacionalActionForm.setIdEmpresa("" + empresaPrincipal.getId());

					if(cod.equals(UnidadeTipo.UNIDADE_TIPO_GERENCIA_REGIONAL)){
						String idGerencia = inserirUnidadeOrganizacionalActionForm.getGerenciaRegional();

						if(idGerencia != null && !idGerencia.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

							GerenciaRegional gerenciaRegional = retornaGerenciaRegionalPorId(colecaoGerenciaRegional, new Integer(
											idGerencia));

							inserirUnidadeOrganizacionalActionForm.setSigla(gerenciaRegional.getNomeAbreviado());
							inserirUnidadeOrganizacionalActionForm.setDescricao(gerenciaRegional.getNome());

						}
					}

				}
			}

		}

		// verifica se o usuário solicitou uma consulta de localidade
		String idLocalidade = httpServletRequest.getParameter("idLocalidade");
		String descricaoLocalidade = httpServletRequest.getParameter("descricaoLocalidade");

		// Flag indicando que o usuário fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		// Seta no form os valores da pesquisa feita pela localidade
		if(idLocalidade != null && !idLocalidade.trim().equals("") && descricaoLocalidade != null && !descricaoLocalidade.trim().equals("")){

			// Indica que localidade foi encontrado
			httpServletRequest.setAttribute("idLocalidadeEncontrada", "true");

			inserirUnidadeOrganizacionalActionForm.setIdLocalidade(idLocalidade.trim());
			inserirUnidadeOrganizacionalActionForm.setDescricaoLocalidade(descricaoLocalidade.trim());
		}

		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && objetoConsulta.trim().equals("1")){

			// Faz a consulta de Localidade
			pesquisarLocalidade(httpServletRequest, retorno, inserirUnidadeOrganizacionalActionForm);

		}

		// verifica se o usuário solicitou uma consulta de unidade superior
		String idUnidadeSuperior = httpServletRequest.getParameter("idUnidadeSuperior");
		String descricaoUnidadeSuperior = httpServletRequest.getParameter("descricaoUnidadeSuperior");

		// Seta no form os valores da pesquisa feita
		if(idUnidadeSuperior != null && !idUnidadeSuperior.trim().equals("") && descricaoUnidadeSuperior != null
						&& !descricaoUnidadeSuperior.trim().equals("")){

			// Indica que o local de armazenagem foi encontrado
			httpServletRequest.setAttribute("idUnidadeEncontrada", "true");

			inserirUnidadeOrganizacionalActionForm.setIdUnidadeSuperior(idUnidadeSuperior.trim());
			inserirUnidadeOrganizacionalActionForm.setDescricaoUnidadeSuperior(descricaoUnidadeSuperior.trim());
		}

		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && objetoConsulta.trim().equals("2")){

			// Faz a consulta de Unidade
			pesquisarUnidadeOrganizacional(httpServletRequest, retorno, inserirUnidadeOrganizacionalActionForm);

		}

		Collection colecaoMeioSolicitacao = (Collection) sessao.getAttribute("colecaoMeioSolicitacao");

		// faz a consulta da colecao de meio solicatação para exibir no jsp
		if(colecaoMeioSolicitacao == null){

			FiltroMeioSolicitacao filtroMeioSolicitacao = new FiltroMeioSolicitacao();

			filtroMeioSolicitacao.adicionarParametro(new ParametroSimples(FiltroMeioSolicitacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroMeioSolicitacao.setCampoOrderBy(FiltroMeioSolicitacao.DESCRICAO);

			colecaoMeioSolicitacao = fachada.pesquisar(filtroMeioSolicitacao, MeioSolicitacao.class.getName());

			if(colecaoMeioSolicitacao != null && !colecaoMeioSolicitacao.isEmpty()){
				sessao.setAttribute("colecaoMeioSolicitacao", colecaoMeioSolicitacao);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Meio Solicitação");
			}
		}

		Collection colecaoUnidadeCentralizadora = (Collection) sessao.getAttribute("colecaoUnidadeCentralizadora");

		// faz a consulta da colecao unidade centralizador para exibir no jsp
		if(colecaoUnidadeCentralizadora == null){

			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.UNIDADE_TIPO_CODIGO,
							UnidadeTipo.UNIDADE_TIPO_CENTRALIZADORA));

			filtroUnidadeOrganizacional.setCampoOrderBy(FiltroMeioSolicitacao.DESCRICAO);

			colecaoUnidadeCentralizadora = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			if(colecaoUnidadeCentralizadora != null && !colecaoUnidadeCentralizadora.isEmpty()){
				sessao.setAttribute("colecaoUnidadeCentralizadora", colecaoUnidadeCentralizadora);
			}else{
				// throw new ActionServletException("atencao.naocadastrado",
				// null,"Unidade Centralizadora");
				sessao.setAttribute("colecaoUnidadeCentralizadora", new ArrayList());
			}
		}

		return retorno;
	}

	// Consulta a colecao de unidade tipo
	private Collection consultarUnidadeTipo(Fachada fachada, HttpSession sessao){

		Collection colecaoUnidadeTipo = (Collection) sessao.getAttribute("colecaoUnidadeTipo");

		if(colecaoUnidadeTipo == null){

			FiltroUnidadeTipo filtroUnidadeTipo = new FiltroUnidadeTipo();

			filtroUnidadeTipo.adicionarParametro(new ParametroSimples(FiltroUnidadeTipo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroUnidadeTipo.setCampoOrderBy(FiltroUnidadeTipo.DESCRICAO);

			colecaoUnidadeTipo = fachada.pesquisar(filtroUnidadeTipo, UnidadeTipo.class.getName());

			if(colecaoUnidadeTipo != null && !colecaoUnidadeTipo.isEmpty()){
				sessao.setAttribute("colecaoUnidadeTipo", colecaoUnidadeTipo);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Unidade Tipo");
			}

		}

		return colecaoUnidadeTipo;
	}

	// consulta a colecao de empresa
	private Collection consultarEmpresa(Fachada fachada, HttpSession sessao){

		Collection colecaoEmpresa = (Collection) sessao.getAttribute("colecaoEmpresa");

		if(colecaoEmpresa == null){
			// Filtro para obter empresa ativo de id informado
			FiltroEmpresa filtroEmpresa = new FiltroEmpresa();

			filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroEmpresa.setCampoOrderBy(FiltroEmpresa.DESCRICAO);

			colecaoEmpresa = fachada.pesquisar(filtroEmpresa, Empresa.class.getName());

			if(colecaoEmpresa != null && !colecaoEmpresa.isEmpty()){
				sessao.setAttribute("colecaoEmpresa", colecaoEmpresa);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Empresa");
			}
		}

		return colecaoEmpresa;
	}

	// consulta a colecao de gerencia regional
	private Collection consultarGerenciaRegional(Fachada fachada, HttpSession sessao){

		Collection colecaoGerenciaRegional = (Collection) sessao.getAttribute("colecaoGerenciaRegional");

		if(colecaoGerenciaRegional == null){

			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

			filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.NOME);

			colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

			if(colecaoGerenciaRegional != null && !colecaoGerenciaRegional.isEmpty()){
				sessao.setAttribute("colecaoGerenciaRegional", colecaoGerenciaRegional);
			}else{
				throw new ActionServletException("atencao.naocadastrado", null, "Gerêncial Regional");
			}
		}
		return colecaoGerenciaRegional;
	}

	// retorna unidadeTipo pelo id na colecao de unidade
	private UnidadeTipo retornaUnidadeTipoPorId(Collection colecaoUnidade, Integer id){

		UnidadeTipo retorno = null;

		if(colecaoUnidade != null && !colecaoUnidade.isEmpty()){
			Iterator itera = colecaoUnidade.iterator();

			while(itera.hasNext()){
				UnidadeTipo unidadeTipo = (UnidadeTipo) itera.next();

				if(unidadeTipo.getId().intValue() == id.intValue()){
					retorno = unidadeTipo;
					break;
				}
			}
		}
		return retorno;
	}

	// retorna gerenciaRegional pelo id na colecao de gerencia
	private GerenciaRegional retornaGerenciaRegionalPorId(Collection colecao, Integer id){

		GerenciaRegional retorno = null;

		if(colecao != null && !colecao.isEmpty()){
			Iterator itera = colecao.iterator();

			while(itera.hasNext()){
				GerenciaRegional gerenciaRegional = (GerenciaRegional) itera.next();

				if(gerenciaRegional.getId().intValue() == id.intValue()){
					retorno = gerenciaRegional;
					break;
				}
			}
		}
		return retorno;
	}

	// retorna a empresa principal
	private Empresa retornaEmpresaPrincipal(Collection colecaoEmpresa){

		Empresa retorno = null;

		if(colecaoEmpresa != null && !colecaoEmpresa.isEmpty()){
			Iterator itera = colecaoEmpresa.iterator();

			while(itera.hasNext()){
				Empresa empresa = (Empresa) itera.next();

				if(empresa.getIndicadorEmpresaPrincipal().equals(ConstantesSistema.SIM)){
					retorno = empresa;
					break;
				}
			}
		}
		return retorno;
	}

	// pesquisa a localidade pelo id
	private void pesquisarLocalidade(HttpServletRequest httpServletRequest, ActionForward retorno,
					InserirUnidadeOrganizacionalActionForm inserirUnidadeOrganizacionalActionForm){

		// Filtro para obter o localidade ativo de id informado
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(inserirUnidadeOrganizacionalActionForm
						.getIdLocalidade())));

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoLocalidade = Fachada.getInstancia().pesquisar(filtroLocalidade, Localidade.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			// Exibe o código e a descrição pesquisa na página
			httpServletRequest.setAttribute("idLocalidadeEncontrada", "true");

			inserirUnidadeOrganizacionalActionForm.setIdLocalidade(localidade.getId().toString());
			inserirUnidadeOrganizacionalActionForm.setDescricaoLocalidade(localidade.getDescricao());

			String codigo = inserirUnidadeOrganizacionalActionForm.getCodigoUnidadeTipo();

			if(codigo.equals(UnidadeTipo.UNIDADE_TIPO_LOCALIDADE) || codigo.equals(UnidadeTipo.UNIDADE_TIPO_GERENCIA_REGIONAL)){

				inserirUnidadeOrganizacionalActionForm.setDescricao(localidade.getDescricao());
			}
			httpServletRequest.setAttribute("corLocalidade", "valor");

		}else{
			httpServletRequest.setAttribute("corLocalidade", "exception");
			inserirUnidadeOrganizacionalActionForm.setDescricaoLocalidade("Localidade inexistente");
			inserirUnidadeOrganizacionalActionForm.setIdLocalidade("");

		}
	}

	// pesquisa a unidadeOrganizacional pelo id
	private void pesquisarUnidadeOrganizacional(HttpServletRequest httpServletRequest, ActionForward retorno,
					InserirUnidadeOrganizacionalActionForm inserirUnidadeOrganizacionalActionForm){

		// Filtro para obter unidade organizacional ativo de id informado
		FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

		filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		String idUnidade = inserirUnidadeOrganizacionalActionForm.getIdUnidadeSuperior();

		filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidade));

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoUnidade = Fachada.getInstancia().pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoUnidade != null && !colecaoUnidade.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidade);

			// Exibe o código e a descrição pesquisa na página
			httpServletRequest.setAttribute("idUnidadeEncontrada", "true");

			inserirUnidadeOrganizacionalActionForm.setIdUnidadeSuperior(unidadeOrganizacional.getId().toString());
			inserirUnidadeOrganizacionalActionForm.setDescricaoUnidadeSuperior(unidadeOrganizacional.getDescricao());

			httpServletRequest.setAttribute("corUnidadeSuperior", "valor");

		}else{
			httpServletRequest.setAttribute("corUnidadeSuperior", "exception");
			inserirUnidadeOrganizacionalActionForm.setDescricaoUnidadeSuperior("Unidade Organizacional inexistente");
			inserirUnidadeOrganizacionalActionForm.setIdUnidadeSuperior("");

		}
	}

}
