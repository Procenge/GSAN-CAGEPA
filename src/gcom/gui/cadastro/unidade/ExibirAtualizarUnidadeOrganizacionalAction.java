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
import gcom.seguranca.acesso.PermissaoEspecial;
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
 * @author Ana Maria
 * @date 22/11/2006
 */
public class ExibirAtualizarUnidadeOrganizacionalAction
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

		ActionForward retorno = actionMapping.findForward("atualizarUnidadeOrganizacional");

		// Obt�m o action form
		AtualizarUnidadeOrganizacionalActionForm form = (AtualizarUnidadeOrganizacionalActionForm) actionForm;

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		String objetoConsulta = (String) httpServletRequest.getParameter("objetoConsulta");

		if(objetoConsulta != null && objetoConsulta.equals("1")){
			pesquisarLocalidade(form, httpServletRequest);
		}

		String idUnidadeOrganizacional = httpServletRequest.getParameter("idRegistroAtualizacao");

		if(idUnidadeOrganizacional == null){
			if(httpServletRequest.getAttribute("idRegistroAtualizacao") == null){
				idUnidadeOrganizacional = (String) sessao.getAttribute("idRegistroAtualizacao");
			}else{
				idUnidadeOrganizacional = (String) httpServletRequest.getAttribute("idRegistroAtualizacao").toString();
			}

		}

		String modificouTipoUnidade = httpServletRequest.getParameter("modificouTipoUnidade");

		String consultaUnidadeSuperior = (String) httpServletRequest.getParameter("consultaUnidadeSuperior");

		boolean ehPresidencia = false;

		if(Util.isVazioOuBranco(modificouTipoUnidade) && Util.isVazioOuBranco(consultaUnidadeSuperior)){

			/*
			 * else {
			 * sessao.setAttribute("i", true);
			 * }
			 */

			// Verifica se os objetos est�o na sess�o
			if(idUnidadeOrganizacional != null && !idUnidadeOrganizacional.equals("")){

				UnidadeOrganizacional unidadeOrganizacional = Fachada.getInstancia().pesquisarUnidadeOrganizacional(
								new Integer(idUnidadeOrganizacional));

				if(unidadeOrganizacional != null && !unidadeOrganizacional.equals("")){

					sessao.setAttribute("unidadeOrganizacional", unidadeOrganizacional);

					obterDadosContatoUnidadeOrganizacional(unidadeOrganizacional, form);

					form.setId(formatarResultado("" + unidadeOrganizacional.getId()));
					form.setUnidadeTipo(formatarResultado("" + unidadeOrganizacional.getUnidadeTipo().getId()));
					if(unidadeOrganizacional.getLocalidade() != null && !unidadeOrganizacional.getLocalidade().equals("")){
						form.setIdLocalidade(formatarResultado("" + unidadeOrganizacional.getLocalidade().getId()));
						form.setDescricaoLocalidade(formatarResultado(unidadeOrganizacional.getLocalidade().getDescricao()));
					}
					if(unidadeOrganizacional.getGerenciaRegional() != null && !unidadeOrganizacional.getGerenciaRegional().equals("")){
						form.setIdGerenciaRegional(unidadeOrganizacional.getGerenciaRegional().getId().toString());
						if(unidadeOrganizacional.getGerenciaRegional().getNomeAbreviado() != null
										&& !unidadeOrganizacional.getGerenciaRegional().getNomeAbreviado().equals("")){
							form.setGerenciaRegional(formatarResultado(unidadeOrganizacional.getGerenciaRegional().getNomeAbreviado() + "-"
											+ unidadeOrganizacional.getGerenciaRegional().getNome()));
						}else{
							form.setGerenciaRegional(formatarResultado(unidadeOrganizacional.getGerenciaRegional().getNome()));
						}
					}

					ehPresidencia = (unidadeOrganizacional.getUnidadeSuperior() != null && unidadeOrganizacional.getId().equals(
									unidadeOrganizacional.getUnidadeSuperior().getId()));

					if(((unidadeOrganizacional.getLocalidade() != null && !unidadeOrganizacional.getLocalidade().equals("")) || (unidadeOrganizacional
									.getGerenciaRegional() != null && !unidadeOrganizacional.getGerenciaRegional().equals("")))
									&& !ehPresidencia){
						sessao.setAttribute("naomodificar", "naomodificar");
					}
					form.setDescricao(formatarResultado(unidadeOrganizacional.getDescricao()));
					form.setSigla(formatarResultado(unidadeOrganizacional.getSigla()));
					if(unidadeOrganizacional.getEmpresa() != null && !unidadeOrganizacional.getEmpresa().equals("")){
						form.setIdEmpresa(formatarResultado("" + unidadeOrganizacional.getEmpresa().getId()));
					}

					/**
					 * 7. Empresa, seleciona entre todas as empresas ativas. (exibindo
					 * EMPR_NMEMPRESA da tabela EMPRESA com EMPR_ICUSO igual a "Sim").
					 * 7.1. Caso o tipo da unidade seja terceirizada (UNTP_CDTIPO da tabela
					 * UNIDADE_TIPO com UNTP_ID igual UNTP_ID da tabela UNIDADE_ORGANIZACIONAL igual
					 * "T").
					 * 7.2. Caso contr�rio, n�o permitir alterar.
					 */
					if(ehPresidencia
									|| (!Util.isVazioOuBranco(unidadeOrganizacional.getUnidadeTipo().getCodigoTipo()) && unidadeOrganizacional
													.getUnidadeTipo().getCodigoTipo()
													.equalsIgnoreCase(UnidadeTipo.UNIDADE_TIPO_TERCERIZADO))){
						form.setModificarEmpresa("S");
						}else{
						form.setModificarEmpresa("N");
					}

					if(unidadeOrganizacional.getUnidadeSuperior() != null && !unidadeOrganizacional.getUnidadeSuperior().equals("")){
						form.setIdUnidadeSuperior(formatarResultado("" + unidadeOrganizacional.getUnidadeSuperior().getId()));
						form.setDescricaoUnidadeSuperior(formatarResultado(unidadeOrganizacional.getUnidadeSuperior().getDescricao()));
					}
					if(unidadeOrganizacional.getUnidadeCentralizadora() != null
									&& !unidadeOrganizacional.getUnidadeCentralizadora().equals("")){
						form.setIdUnidadeCentralizadora("" + unidadeOrganizacional.getUnidadeCentralizadora().getId());
					}
					form.setUnidadeEsgoto(formatarResultado("" + unidadeOrganizacional.getIndicadorEsgoto()));
					form.setUnidadeAbreRegistro(formatarResultado("" + unidadeOrganizacional.getIndicadorAberturaRa()));
					form.setUnidadeAceita(formatarResultado("" + unidadeOrganizacional.getIndicadorTramite()));
					if(unidadeOrganizacional.getMeioSolicitacao() != null && !unidadeOrganizacional.getMeioSolicitacao().equals("")){
						form.setMeioSolicitacao(formatarResultado("" + unidadeOrganizacional.getMeioSolicitacao().getId()));
						}
					form.setIndicadorUso("" + unidadeOrganizacional.getIndicadorUso());
				}else{
					throw new ActionServletException("atencao.atualizacao.timestamp");
					}

				// Envia as cole��es na sess�o
				pesquisarUnidadeTipo(sessao);
				pesquisarEmpresa(sessao);
				pesquisarUnidadeCentralizadora(sessao);
				pesquisarMeioSolicitacao(sessao);
			}

			sessao.removeAttribute("idRegistroAtualizacao");
		}else{
			idUnidadeOrganizacional = form.getId();
			if(idUnidadeOrganizacional != null && !idUnidadeOrganizacional.equals("")){

				ehPresidencia = (!Util.isVazioOuBranco(form.getIdUnidadeSuperior()) && idUnidadeOrganizacional.equals(form
								.getIdUnidadeSuperior()));

					/**
					 * 7. Empresa, seleciona entre todas as empresas ativas. (exibindo
					 * EMPR_NMEMPRESA da tabela EMPRESA com EMPR_ICUSO igual a "Sim").
					 * 7.1. Caso o tipo da unidade seja terceirizada (UNTP_CDTIPO da tabela
					 * UNIDADE_TIPO com UNTP_ID igual UNTP_ID da tabela UNIDADE_ORGANIZACIONAL igual
					 * "T").
					 * 7.2. Caso contr�rio, n�o permitir alterar.
					 */
				if(!Util.isVazioOuBranco(form.getUnidadeTipo())
								&& !form.getUnidadeTipo().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

					// Filtro de tipo de unidade
					FiltroUnidadeTipo filtroUnidadeTipo = new FiltroUnidadeTipo();
					filtroUnidadeTipo
									.adicionarParametro(new ParametroSimples(FiltroUnidadeTipo.ID, Integer.valueOf(form.getUnidadeTipo())));
					filtroUnidadeTipo.adicionarParametro(new ParametroSimples(FiltroUnidadeTipo.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					UnidadeTipo unidadeTipo = (UnidadeTipo) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroUnidadeTipo,
									UnidadeTipo.class.getName()));

					if(unidadeTipo == null){
						throw new ActionServletException("atencao.pesquisa_inexistente", null, "Tipo da Unidade");
					}

					if(ehPresidencia
									|| (!Util.isVazioOuBranco(unidadeTipo.getCodigoTipo()) && unidadeTipo.getCodigoTipo().equalsIgnoreCase(
													UnidadeTipo.UNIDADE_TIPO_TERCERIZADO))){
						form.setModificarEmpresa("S");
					}else{
						form.setModificarEmpresa("N");
					}

				}else if(!ehPresidencia){
						form.setModificarEmpresa("N");
					}
				}

		}

		if(!Util.isVazioOuBranco(consultaUnidadeSuperior) && (Integer.parseInt(consultaUnidadeSuperior)) == 1){

			// Consulta a unidade superior
			consultarUnidadeSuperior(httpServletRequest, form);
			}

		// verifica permiss�o especial para alterar independente de tipo unidade
		boolean permissaoAlterarCampos = Fachada.getInstancia()
						.verificarPermissaoEspecial(PermissaoEspecial.ALTERAR_UNIDADE_ORGANIZACIONAL_INDEPENDENTE_DE_TIPO_UNIDADE,
										getUsuarioLogado(httpServletRequest));
		if(permissaoAlterarCampos){
			sessao.setAttribute("permissaoAlterarCampos", ConstantesSistema.SIM);
			form.setModificarEmpresa("S");
			sessao.removeAttribute("naomodificar");
			consultarGerenciaRegional(Fachada.getInstancia(), sessao);

		}else{
			sessao.setAttribute("permissaoAlterarCampos", ConstantesSistema.NAO);
			}

		return retorno;
		}

	private void pesquisarLocalidade(AtualizarUnidadeOrganizacionalActionForm form, HttpServletRequest httpServletRequest){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

		String idLocalidade = null;
		Collection colecaoPesquisa = null;
		Fachada fachada = Fachada.getInstancia();

		// Recebe o valor do campo localidadeID do formul�rio.
		idLocalidade = form.getIdLocalidade();

		if(idLocalidade != null && !idLocalidade.equalsIgnoreCase("")){
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna localidade
			colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Localidade nao encontrada
				// Limpa os campos localidadeID e localidadeNome do
				// formul�rio
				httpServletRequest.setAttribute("localidadeInexistente", "");
				form.setIdLocalidade("");
				form.setDescricaoLocalidade("Localidade Inexistente.");
				httpServletRequest.setAttribute("nomeCampo", "idLocalidade");
			}else{

				Localidade objetoLocalidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);
				form.setIdLocalidade(String.valueOf(objetoLocalidade.getId()));
				form.setDescricaoLocalidade(objetoLocalidade.getDescricao());
				// httpServletRequest.setAttribute("nomeCampo", "setorComercialCD");
			}
		}

	}

	/**
	 * @author isilva
	 * @param sessao
	 */
	private void pesquisarMeioSolicitacao(HttpSession sessao){

		// Filtro meio de solicita��o
		FiltroMeioSolicitacao filtroMeioSolicitacao = new FiltroMeioSolicitacao();
		filtroMeioSolicitacao.adicionarParametro(new ParametroSimples(FiltroMeioSolicitacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroMeioSolicitacao.setCampoOrderBy(FiltroMeioSolicitacao.DESCRICAO);

		Collection colecaoMeioSolicitacao = Fachada.getInstancia().pesquisar(filtroMeioSolicitacao, MeioSolicitacao.class.getName());

		sessao.setAttribute("colecaoMeioSolicitacao", colecaoMeioSolicitacao);
	}

	/**
	 * @author isilva
	 * @param sessao
	 */
	private void pesquisarUnidadeCentralizadora(HttpSession sessao){

		// Filtro de unidade centralizadora
		FiltroUnidadeOrganizacional filtroUnidadeCentralizadora = new FiltroUnidadeOrganizacional();
		filtroUnidadeCentralizadora.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroUnidadeCentralizadora.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.UNIDADE_TIPO_CODIGO,
						UnidadeTipo.UNIDADE_TIPO_CENTRALIZADORA));
		filtroUnidadeCentralizadora.setCampoOrderBy(FiltroUnidadeOrganizacional.DESCRICAO);

		Collection colecaoUnidadeCentralizadora = Fachada.getInstancia().pesquisar(filtroUnidadeCentralizadora,
						UnidadeOrganizacional.class.getName());
		sessao.setAttribute("colecaoUnidadeCentralizadora", colecaoUnidadeCentralizadora);
	}

	/**
	 * @author isilva
	 * @param sessao
	 */
	private void pesquisarUnidadeTipo(HttpSession sessao){

		// Filtro de tipo de unidade
		FiltroUnidadeTipo filtroUnidadeTipo = new FiltroUnidadeTipo();

		filtroUnidadeTipo.adicionarParametro(new ParametroSimples(FiltroUnidadeTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroUnidadeTipo.setCampoOrderBy(FiltroUnidadeTipo.DESCRICAO);

		Collection colecaoUnidadeTipo = Fachada.getInstancia().pesquisar(filtroUnidadeTipo, UnidadeTipo.class.getName());
		sessao.setAttribute("colecaoUnidadeTipo", colecaoUnidadeTipo);
	}

	/**
	 * @author isilva
	 * @param sessao
	 */
	private void pesquisarEmpresa(HttpSession sessao){

		// Filtro de empresa
		FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
		filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroEmpresa.setCampoOrderBy(FiltroEmpresa.DESCRICAO);

		Collection colecaoEmpresa = Fachada.getInstancia().pesquisar(filtroEmpresa, Empresa.class.getName());
		sessao.setAttribute("colecaoEmpresa", colecaoEmpresa);
	}

	private void consultarUnidadeSuperior(HttpServletRequest httpServletRequest, AtualizarUnidadeOrganizacionalActionForm form){

		String consultaUnidadeSuperior = (String) httpServletRequest.getParameter("consultaUnidadeSuperior");

		if(!Util.isVazioOuBranco(consultaUnidadeSuperior) && (Integer.parseInt(consultaUnidadeSuperior)) == 1){

			// Filtro para obter o Unidade Superior ativo de id informado
			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, new Integer(form
							.getIdUnidadeSuperior()), ParametroSimples.CONECTOR_AND));

			// Pesquisa de acordo com os par�metros informados no filtro
			Collection colecaoUnidadeSuperior = Fachada.getInstancia().pesquisar(filtroUnidadeOrganizacional,
							UnidadeOrganizacional.class.getName());

			// Verifica se a pesquisa retornou algum objeto para a cole��o
			if(colecaoUnidadeSuperior != null && !colecaoUnidadeSuperior.isEmpty()){

				// Obt�m o objeto da cole��o pesquisada
				UnidadeOrganizacional unidadeSuperior = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeSuperior);

				obterDadosContatoUnidadeOrganizacional(unidadeSuperior, form);

				// Exibe o c�digo e a descri��o pesquisa na p�gina
				httpServletRequest.setAttribute("corUnidadeSuperior", "valor");
				form.setIdUnidadeSuperior(unidadeSuperior.getId().toString());
				form.setDescricaoUnidadeSuperior(unidadeSuperior.getDescricao());
				httpServletRequest.setAttribute("nomeCampo", "idUnidadeSuperior");
			}else{
				// Exibe mensagem de c�digo inexiste e limpa o campo de c�digo
				httpServletRequest.setAttribute("corUnidadeSuperior", "exception");
				form.setIdUnidadeSuperior("");
				form.setDescricaoUnidadeSuperior("UNIDADE ORGANIZACIONAL INEXISTENTE");

				httpServletRequest.setAttribute("nomeCampo", "idUnidadeSuperior");
			}
		}
	}

	private String formatarResultado(String parametro){

		if(parametro != null && !parametro.trim().equals("")){
			if(parametro.equals("null")){
				return "";
			}else{
				return parametro.trim();
			}
		}else{
			return "";
		}
	}

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
				throw new ActionServletException("atencao.naocadastrado", null, "Ger�ncial Regional");
			}
		}
		return colecaoGerenciaRegional;
	}

	private void obterDadosContatoUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional,
					AtualizarUnidadeOrganizacionalActionForm form){

		form.setDdd(unidadeOrganizacional.getDdd());
		form.setFax(unidadeOrganizacional.getFax());
		form.setTelefone(unidadeOrganizacional.getTelefone());
		form.setRamal(unidadeOrganizacional.getRamal());
		form.setObservacao(unidadeOrganizacional.getObservacao());

	}

}