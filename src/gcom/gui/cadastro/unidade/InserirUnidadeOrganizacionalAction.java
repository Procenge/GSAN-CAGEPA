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
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author Rafael Pinto
 * @created 31/07/2006
 */
public class InserirUnidadeOrganizacionalAction
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
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		InserirUnidadeOrganizacionalActionForm inserirUnidadeOrganizacionalActionForm = (InserirUnidadeOrganizacionalActionForm) actionForm;

		short indicadorEsgoto = new Short(inserirUnidadeOrganizacionalActionForm.getUnidadeEsgoto()).shortValue();

		short indicadorTramite = new Short(inserirUnidadeOrganizacionalActionForm.getUnidadeAceita()).shortValue();

		short indicadorAberturaRa = new Short(inserirUnidadeOrganizacionalActionForm.getUnidadeAbreRegistro()).shortValue();

		short indicadorCentralAtendimento = new Short(inserirUnidadeOrganizacionalActionForm.getUnidadeCentralAtendimento());

		short indicadorTarifaSocial = new Short(inserirUnidadeOrganizacionalActionForm.getUnidadeTarifaSocial()).shortValue();

		short indicadorUso = ConstantesSistema.INDICADOR_USO_ATIVO;

		String sigla = inserirUnidadeOrganizacionalActionForm.getSigla();

		String descricao = inserirUnidadeOrganizacionalActionForm.getDescricao();
		Date ultimaAlteracao = new Date();

		String idUnidadeTipo = inserirUnidadeOrganizacionalActionForm.getUnidadeTipo();
		UnidadeTipo unidadeTipo = this.pesquisarUnidadeTipo(idUnidadeTipo);

		String idEmpresa = inserirUnidadeOrganizacionalActionForm.getIdEmpresa();
		Empresa empresa = this.pesquisarEmpresa(idEmpresa);

		String idUnidadeSuperior = inserirUnidadeOrganizacionalActionForm.getIdUnidadeSuperior();
		UnidadeOrganizacional unidadeSuperior = null;
		if(idUnidadeSuperior != null && !idUnidadeSuperior.equals("")){
			unidadeSuperior = this.pesquisarUnidadeOrganizacional(idUnidadeSuperior);
		}

		String idMeioSolicitacao = inserirUnidadeOrganizacionalActionForm.getMeioSolicitacao();
		MeioSolicitacao meioSolicitacao = null;
		if(idMeioSolicitacao != null && !idMeioSolicitacao.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)
						&& !idMeioSolicitacao.equals("")){

			meioSolicitacao = this.pesquisarMeioSolicitacao(idMeioSolicitacao);
		}

		String idLocalidade = inserirUnidadeOrganizacionalActionForm.getIdLocalidade();
		Localidade localidade = null;
		if(idLocalidade != null && !idLocalidade.equals("")){
			localidade = this.pesquisarLocalidade(idLocalidade);
		}

		String idGerenciaRegional = inserirUnidadeOrganizacionalActionForm.getGerenciaRegional();
		GerenciaRegional gerenciaRegional = null;
		if(idGerenciaRegional != null && !idGerenciaRegional.equals("")){
			gerenciaRegional = this.pesquisarGerenciaRegional(idGerenciaRegional);
		}

		UnidadeOrganizacional unidadeOrganizacional = new UnidadeOrganizacional(indicadorEsgoto, indicadorTramite, descricao, sigla,
						ultimaAlteracao, indicadorAberturaRa, indicadorUso, unidadeTipo, meioSolicitacao, empresa, localidade,
						gerenciaRegional, null, unidadeSuperior, indicadorCentralAtendimento, indicadorTarifaSocial);

		Integer codigoUnidadeOrganizacionalInserido = (Integer) fachada.inserirUnidadeOrganizacional(unidadeOrganizacional, this
						.getUsuarioLogado(httpServletRequest));

		sessao.removeAttribute("filtrar_manter");

		montarPaginaSucesso(httpServletRequest, "Unidade Organizacional de código " + codigoUnidadeOrganizacionalInserido
						+ " inserido com sucesso.", "Inserir outra Unidade", "exibirInserirUnidadeOrganizacionalAction.do?menu=sim",
						"exibirAtualizarUnidadeOrganizacionalAction.do?idRegistroAtualizacao=" + codigoUnidadeOrganizacionalInserido,
						"Atualizar Unidade Inserida");

		return retorno;
	}

	private Localidade pesquisarLocalidade(String id){

		Localidade localidade = null;

		// Filtro para obter o localidade ativo de id informado
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(id)));

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoLocalidade = Fachada.getInstancia().pesquisar(filtroLocalidade, Localidade.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

		}

		return localidade;
	}

	private UnidadeOrganizacional pesquisarUnidadeOrganizacional(String id){

		UnidadeOrganizacional unidadeOrganizacional = null;

		// Filtro para obter unidade organizacional ativo de id informado
		FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

		filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, id));

		filtroUnidadeOrganizacional.adicionarCaminhoParaCarregamentoEntidade("unidadeTipo");

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoUnidade = Fachada.getInstancia().pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoUnidade != null && !colecaoUnidade.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			unidadeOrganizacional = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidade);

		}

		return unidadeOrganizacional;
	}

	private Empresa pesquisarEmpresa(String id){

		Empresa empresa = null;

		FiltroEmpresa filtroEmpresa = new FiltroEmpresa();

		filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.ID, id));

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoEmpresa = Fachada.getInstancia().pesquisar(filtroEmpresa, Empresa.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoEmpresa != null && !colecaoEmpresa.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			empresa = (Empresa) Util.retonarObjetoDeColecao(colecaoEmpresa);

		}

		return empresa;
	}

	private GerenciaRegional pesquisarGerenciaRegional(String id){

		GerenciaRegional gerenciaRegional = null;

		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

		filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, id));

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoGerenciaRegional = Fachada.getInstancia().pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoGerenciaRegional != null && !colecaoGerenciaRegional.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			gerenciaRegional = (GerenciaRegional) Util.retonarObjetoDeColecao(colecaoGerenciaRegional);

		}

		return gerenciaRegional;
	}

	private UnidadeTipo pesquisarUnidadeTipo(String id){

		UnidadeTipo unidadeTipo = null;

		FiltroUnidadeTipo filtroUnidadeTipo = new FiltroUnidadeTipo();

		filtroUnidadeTipo.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, id));

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoUnidadeTipo = Fachada.getInstancia().pesquisar(filtroUnidadeTipo, UnidadeTipo.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoUnidadeTipo != null && !colecaoUnidadeTipo.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			unidadeTipo = (UnidadeTipo) Util.retonarObjetoDeColecao(colecaoUnidadeTipo);

		}

		return unidadeTipo;
	}

	private MeioSolicitacao pesquisarMeioSolicitacao(String id){

		MeioSolicitacao meioSolicitacao = null;

		FiltroMeioSolicitacao filtroMeioSolicitacao = new FiltroMeioSolicitacao();

		filtroMeioSolicitacao.adicionarParametro(new ParametroSimples(FiltroMeioSolicitacao.ID, id));

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoMeioSolicitacao = Fachada.getInstancia().pesquisar(filtroMeioSolicitacao, MeioSolicitacao.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoMeioSolicitacao != null && !colecaoMeioSolicitacao.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			meioSolicitacao = (MeioSolicitacao) Util.retonarObjetoDeColecao(colecaoMeioSolicitacao);

		}

		return meioSolicitacao;
	}

}
