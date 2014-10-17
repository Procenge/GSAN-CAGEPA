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

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author Ana Maria
 * @date 22/11/2006
 */
public class AtualizarUnidadeOrganizacionalAction
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

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtém a fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		/*
		 * // ------------ REGISTRAR TRANSAÇÃO ----------------
		 * RegistradorOperacao registradorOperacao = new RegistradorOperacao(
		 * Operacao.OPERACAO_HIDROMETRO_ATUALIZAR,
		 * new UsuarioAcaoUsuarioHelper(getUsuarioLogado(httpServletRequest),
		 * UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
		 * Operacao operacao = new Operacao();
		 * operacao.setId(Operacao.OPERACAO_HIDROMETRO_ATUALIZAR);
		 * OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		 * operacaoEfetuada.setOperacao(operacao);
		 * //------------ REGISTRAR TRANSAÇÃO ----------------
		 */

		AtualizarUnidadeOrganizacionalActionForm form = (AtualizarUnidadeOrganizacionalActionForm) actionForm;

		// UnidadeOrganizacional unidadeNova = new UnidadeOrganizacional();

		UnidadeOrganizacional unidadeNova = (UnidadeOrganizacional) sessao.getAttribute("unidadeOrganizacional");

		// unidadeNova.setId(new Integer(form.getId()));

		unidadeNova.setIndicadorEsgoto(new Short(form.getUnidadeEsgoto()).shortValue());

		unidadeNova.setIndicadorTramite(new Short(form.getUnidadeAceita()).shortValue());

		unidadeNova.setIndicadorAberturaRa(new Short(form.getUnidadeAbreRegistro()).shortValue());

		unidadeNova.setIndicadorUso(new Short(form.getIndicadorUso()).shortValue());

		if(form.getSigla() != null){
			unidadeNova.setSigla(form.getSigla());
		}else{
			unidadeNova.setSigla(null);
		}

		unidadeNova.setDescricao(form.getDescricao());

		String idUnidadeTipo = form.getUnidadeTipo();
		UnidadeTipo unidadeTipoNovo = this.pesquisarUnidadeTipo(idUnidadeTipo);

		String idEmpresa = form.getIdEmpresa();
		Empresa empresaNova = this.pesquisarEmpresa(idEmpresa);

		if(Util.isVazioOuBranco(unidadeTipoNovo.getCodigoTipo())
						|| !unidadeTipoNovo.getCodigoTipo().equalsIgnoreCase(UnidadeTipo.UNIDADE_TIPO_TERCERIZADO)){

			// Diferente de Tercerizado

			if(unidadeNova.getEmpresa().getId().intValue() != empresaNova.getId().intValue()){
				if(sessao.getAttribute("permissaoAlterarCampos") == null
								|| sessao.getAttribute("permissaoAlterarCampos").equals(ConstantesSistema.NAO)){

				// Empresa foi alterada
				throw new ActionServletException("atencao.so.permitido.alterar.empresa.caso.unidade.seja.terceirizada");
				}
			}
		}

		unidadeNova.setUnidadeTipo(unidadeTipoNovo);
		unidadeNova.setEmpresa(empresaNova);

		String idUnidadeSuperior = form.getIdUnidadeSuperior();
		if(idUnidadeSuperior != null && !idUnidadeSuperior.equals("")){
			unidadeNova.setUnidadeSuperior(this.pesquisarUnidadeOrganizacional(idUnidadeSuperior));
		}else{
			unidadeNova.setUnidadeSuperior(null);
		}

		unidadeNova.setUnidadeCentralizadora(null);

		String idMeioSolicitacao = form.getMeioSolicitacao();
		if(idMeioSolicitacao != null && !idMeioSolicitacao.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)
						&& !idMeioSolicitacao.equals("")){

			unidadeNova.setMeioSolicitacao(this.pesquisarMeioSolicitacao(idMeioSolicitacao));
		}

		if(sessao.getAttribute("permissaoAlterarCampos").equals(ConstantesSistema.SIM)){

			String idLocalidade = form.getIdLocalidade();
			Localidade localidade = fachada.pesquisarLocalidadeDigitada(Integer.parseInt(idLocalidade));
			unidadeNova.setLocalidade(localidade);

			String idGerencia = form.getIdGerenciaRegional();
			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
			filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, Integer.parseInt(idGerencia)));
			GerenciaRegional gerenciaRegional = (GerenciaRegional) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroGerenciaRegional,
							GerenciaRegional.class.getName()));
			unidadeNova.setGerenciaRegional(gerenciaRegional);

		}

		fachada.atualizarUnidadeOrganizacional(unidadeNova, this.getUsuarioLogado(httpServletRequest));

		montarPaginaSucesso(httpServletRequest, "Unidade Organizacional " + unidadeNova.getDescricao() + " atualizada com sucesso.",
						"Realizar outra Manutenção de Unidade Organizacional", "exibirFiltrarUnidadeOrganizacionalAction.do?menu=sim");

		return retorno;
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
