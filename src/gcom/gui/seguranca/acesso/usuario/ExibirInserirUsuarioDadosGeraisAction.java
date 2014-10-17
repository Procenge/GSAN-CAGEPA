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

package gcom.gui.seguranca.acesso.usuario;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.Grupo;
import gcom.seguranca.acesso.usuario.FiltroUsuarioGrupo;
import gcom.seguranca.acesso.usuario.FiltroUsuarioTipo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioGrupo;
import gcom.seguranca.acesso.usuario.UsuarioTipo;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirInserirUsuarioDadosGeraisAction
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

		// FachadaBatch.getInstancia().gerarResumoSituacaoEspecialFaturamento();

		ActionForward retorno = actionMapping.findForward("inserirDadosGerais");

		InserirUsuarioDadosGeraisActionForm form = (InserirUsuarioDadosGeraisActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		// Usuario que vai ser cadastrado no sistema, usado só nessa
		// funcionalidade
		Usuario usuarioCadastrar = (Usuario) sessao.getAttribute("usuarioCadastrar");

		if(usuarioCadastrar == null){
			usuarioCadastrar = new Usuario();
		}

		if(usuarioCadastrar != null){

			if("".equals(form.getUsuarioTipo())){

				// se o form nao tiver o usuario tipo
				if(usuarioCadastrar.getUsuarioTipo() != null && usuarioCadastrar.getUsuarioTipo().getId() != null){
					form.setUsuarioTipo(usuarioCadastrar.getUsuarioTipo().getId().toString());
				}

			}else{

				UsuarioTipo usuarioTipo = new UsuarioTipo();
				usuarioTipo.setId(new Integer(form.getUsuarioTipo()));
				usuarioTipo.setIndicadorFuncionario(new Short(form.getIndicadorFuncionario()));
				usuarioCadastrar.setUsuarioTipo(usuarioTipo);

			}

			if(usuarioCadastrar.getEmpresa() != null && usuarioCadastrar.getEmpresa().getId() != null){
				form.setEmpresa(usuarioCadastrar.getEmpresa().getId().toString());
			}

			if("".equals(form.getIdFuncionario())){

				if(usuarioCadastrar.getFuncionario() != null){

					if(usuarioCadastrar.getFuncionario().getId() != null)

					form.setIdFuncionario(usuarioCadastrar.getFuncionario().getId().toString());

					if(usuarioCadastrar.getFuncionario().getNome() != null) form.setNomeFuncionario(usuarioCadastrar.getFuncionario()
									.getNome());
				}
			}else if(form.getIdFuncionario() != null && (form.getNomeFuncionario() == null || form.getNomeFuncionario().equals(""))){

				FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
				filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, form.getIdFuncionario()));

				filtroFuncionario.adicionarCaminhoParaCarregamentoEntidade(FiltroFuncionario.UNIDADE_ORGANIZACIONAL);
				Collection coll = Fachada.getInstancia().pesquisar(filtroFuncionario, Funcionario.class.getName());
				if(coll != null && !coll.isEmpty()){
					Funcionario f = (Funcionario) coll.iterator().next();
					usuarioCadastrar.setFuncionario(f);
					form.setIdFuncionario(f.getId().toString());
					form.setNomeFuncionario(f.getNome());
					form.setLogin(f.getId().toString());
					form.setFuncionarioNaoEncontrada("false");
				}else{
					usuarioCadastrar.setFuncionario(null);
					form.setIdFuncionario("");
					form.setNomeFuncionario("Funcionario inexistente.");
					form.setFuncionarioNaoEncontrada("true");
					form.setLogin("");
				}
			}

			// caso for funcionario pegar os dados a partir do funcionario
			if(usuarioCadastrar != null && usuarioCadastrar.getUsuarioTipo() != null && usuarioCadastrar.getUsuarioTipo().getId() != null
							&& usuarioCadastrar.getUsuarioTipo().getIndicadorFuncionario() == UsuarioTipo.INDICADOR_FUNCIONARIO){

				if(usuarioCadastrar.getFuncionario() != null){

					if(usuarioCadastrar.getFuncionario().getNome() != null) form.setNome(usuarioCadastrar.getFuncionario().getNome());

					if(usuarioCadastrar.getFuncionario().getUnidadeOrganizacional() != null
									&& (form.getIdLotacao() == null || form.getIdLotacao().equals(""))){
						if(usuarioCadastrar.getFuncionario().getUnidadeOrganizacional().getId() != null) form.setIdLotacao(usuarioCadastrar
										.getFuncionario().getUnidadeOrganizacional().getId().toString());
						if(usuarioCadastrar.getFuncionario().getUnidadeOrganizacional().getDescricao() != null) form
										.setNomeLotacao(usuarioCadastrar.getFuncionario().getUnidadeOrganizacional().getDescricao());
					}
				}

			}else{

				if("".equals(form.getIdLotacao())){
					if(usuarioCadastrar.getUnidadeOrganizacional() != null){
						if(usuarioCadastrar.getUnidadeOrganizacional().getId() != null) form.setIdLotacao(usuarioCadastrar
										.getUnidadeOrganizacional().getId().toString());
						if(usuarioCadastrar.getUnidadeOrganizacional().getDescricao() != null) form.setNomeLotacao(usuarioCadastrar
										.getUnidadeOrganizacional().getDescricao());
					}
				}else if(form.getIdLotacao() != null
								&& !form.getIdLotacao().equals("")
								&& (usuarioCadastrar.getUnidadeOrganizacional() == null || (usuarioCadastrar.getUnidadeOrganizacional() != null
												&& usuarioCadastrar.getUnidadeOrganizacional().getId() != null && !form.getIdLotacao()
												.equals(usuarioCadastrar.getUnidadeOrganizacional().getId().toString())))){

					FiltroUnidadeOrganizacional filtroUnidadeEmpresa = new FiltroUnidadeOrganizacional();
					filtroUnidadeEmpresa.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, form.getIdLotacao()));
					filtroUnidadeEmpresa.adicionarCaminhoParaCarregamentoEntidade("unidadeTipo");
					Collection coll = Fachada.getInstancia().pesquisar(filtroUnidadeEmpresa, UnidadeOrganizacional.class.getName());
					if(coll != null && !coll.isEmpty()){
						UnidadeOrganizacional unidadeEmpresa = (UnidadeOrganizacional) coll.iterator().next();

						// caso o usuário que esteja efetuando a inserção não
						// seja
						// do grupo de administradores
						FiltroUsuarioGrupo filtroUsuarioGrupo = new FiltroUsuarioGrupo();

						filtroUsuarioGrupo.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupo.USUARIO_ID, usuario.getId()));
						filtroUsuarioGrupo.adicionarParametro(new ParametroSimples(FiltroUsuarioGrupo.GRUPO_ID, Grupo.ADMINISTRADOR));
						Collection colecaoUsuarioGrupo = Fachada.getInstancia().pesquisar(filtroUsuarioGrupo, UsuarioGrupo.class.getName());
						if(colecaoUsuarioGrupo == null || colecaoUsuarioGrupo.isEmpty()){
							// se a unidade de lotacao do usuario que estiver
							// efetuando seja diferente da unidade de
							// lotação informada
							if(usuario.getUnidadeOrganizacional() != null && usuario.getUnidadeOrganizacional().getId() != null
											&& !usuario.getUnidadeOrganizacional().getId().equals(new Integer(form.getIdLotacao()))){
								// recupera a unidade do usuário
								FiltroUnidadeOrganizacional filtroUnidadeEmpresaUsuario = new FiltroUnidadeOrganizacional();
								filtroUnidadeEmpresaUsuario.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, usuario
												.getUnidadeOrganizacional().getId()));
								filtroUnidadeEmpresaUsuario.adicionarCaminhoParaCarregamentoEntidade("unidadeTipo");
								Collection colecaoUnidadeEmpresa = Fachada.getInstancia().pesquisar(filtroUnidadeEmpresaUsuario,
												UnidadeOrganizacional.class.getName());
								UnidadeOrganizacional unidadeEmpresaUsuario = null;
								if(colecaoUnidadeEmpresa != null && !colecaoUnidadeEmpresa.isEmpty()){
									unidadeEmpresaUsuario = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeEmpresa);
								}
								// se o nivel da unidade de lotação do usuário
								// que
								// estiver efetuando a inserção seja maior ou
								// igual
								// ao nivel de unidade de lotação informada
								if(unidadeEmpresaUsuario != null){
									if(unidadeEmpresaUsuario.getUnidadeTipo().getNivel() != null
													&& unidadeEmpresa.getUnidadeTipo().getNivel() != null){
										if(unidadeEmpresaUsuario.getUnidadeTipo().getNivel().intValue() >= unidadeEmpresa.getUnidadeTipo()
														.getNivel().intValue()){
											throw new ActionServletException("atencao.usuario.sem.permissao", usuario.getLogin(),
															unidadeEmpresa.getDescricao());
										}
									}else{
										throw new ActionServletException("atencao.usuario.sem.permissao", usuario.getLogin(),
														unidadeEmpresa.getDescricao());
									}

									// ou a unidade superior da unidade de
									// lotação
									// informada seja diferente da unidade de
									// lotação do usuário

									// enquanto o nivel superior da unidade de
									// lotação não esteja no mesmo nivel da
									// unidade
									// de lotação do usuário
									boolean mesmoNivel = true;
									int idNivelUsuario = unidadeEmpresaUsuario.getUnidadeTipo().getNivel().intValue();
									UnidadeOrganizacional unidadeEmpresaSuperior = null;
									while(mesmoNivel){
										Integer idUnidadeEmpresaSuperior = null;
										if(unidadeEmpresaSuperior == null){
											if(unidadeEmpresa.getUnidadeSuperior() != null
															&& !unidadeEmpresa.getUnidadeSuperior().equals("")){
												idUnidadeEmpresaSuperior = unidadeEmpresa.getUnidadeSuperior().getId();
											}
										}else{
											if(unidadeEmpresaSuperior.getUnidadeSuperior() != null
															&& !unidadeEmpresaSuperior.getUnidadeSuperior().equals("")){
												idUnidadeEmpresaSuperior = unidadeEmpresaSuperior.getUnidadeSuperior().getId();
											}
										}
										if(idUnidadeEmpresaSuperior == null){
											throw new ActionServletException("atencao.usuario.sem.permissao", usuario.getLogin(),
															unidadeEmpresa.getDescricao());
										}
										// recupera a unidade do usuário
										FiltroUnidadeOrganizacional filtroUnidadeEmpresaSuperior = new FiltroUnidadeOrganizacional();
										filtroUnidadeEmpresaSuperior.adicionarParametro(new ParametroSimples(
														FiltroUnidadeOrganizacional.ID, idUnidadeEmpresaSuperior));
										filtroUnidadeEmpresaSuperior.adicionarCaminhoParaCarregamentoEntidade("unidadeTipo");
										Collection colecaoUnidadeEmpresaSuperior = Fachada.getInstancia().pesquisar(
														filtroUnidadeEmpresaSuperior, UnidadeOrganizacional.class.getName());
										if(colecaoUnidadeEmpresaSuperior != null && !colecaoUnidadeEmpresaSuperior.isEmpty()){
											unidadeEmpresaSuperior = (UnidadeOrganizacional) Util
															.retonarObjetoDeColecao(colecaoUnidadeEmpresaSuperior);
										}
										if(unidadeEmpresaSuperior != null){
											if(unidadeEmpresaSuperior.getUnidadeTipo().getNivel() == null
															|| unidadeEmpresaSuperior.getUnidadeTipo().getNivel().equals("")){
												throw new ActionServletException("atencao.usuario.sem.permissao", usuario.getLogin(),
																unidadeEmpresa.getDescricao());

											}
											// caso seja o mesmo nivel
											if(unidadeEmpresaSuperior.getUnidadeTipo().getNivel().intValue() == idNivelUsuario){
												mesmoNivel = false;
												// caso o id da unidade empresa
												// informado for diferente do id
												// da
												// unidade empresa do usuário no
												// mesmo nivel
												if(!unidadeEmpresaSuperior.getId().equals(unidadeEmpresaUsuario.getId())){
													throw new ActionServletException("atencao.usuario.sem.permissao", usuario.getLogin(),
																	unidadeEmpresa.getDescricao());
												}

											}
										}
									}

								}

							}
						}

						usuarioCadastrar.setUnidadeOrganizacional(unidadeEmpresa);
						form.setIdLotacao(unidadeEmpresa.getId().toString());
						form.setNomeLotacao(unidadeEmpresa.getDescricao());
						form.setLotacaoNaoEncontrada("false");
					}else{
						usuarioCadastrar.setUnidadeOrganizacional(null);
						form.setIdLotacao("");
						form.setNomeLotacao("Lotação inexistente.");
						form.setLotacaoNaoEncontrada("true");
					}
				}
				if(usuarioCadastrar.getNomeUsuario() != null) form.setNome(usuarioCadastrar.getNomeUsuario());
				if(usuarioCadastrar.getDataCadastroInicio() != null) form.setDataInicial(Util.formatarData(usuarioCadastrar
								.getDataCadastroInicio()));
				if(usuarioCadastrar.getDataCadastroFim() != null) form.setDataFinal(Util
								.formatarData(usuarioCadastrar.getDataCadastroFim()));

			}

			if(usuarioCadastrar.getLogin() != null) form.setLogin(usuarioCadastrar.getLogin());
			if(usuarioCadastrar.getDescricaoEmail() != null) form.setEmail(usuarioCadastrar.getDescricaoEmail());
		}

		if(sessao.getAttribute("collEmpresa") == null || !sessao.getAttribute("collEmpresa").equals("")){

			FiltroEmpresa filtroEmpresa = new FiltroEmpresa();

			filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection coll = Fachada.getInstancia().pesquisar(filtroEmpresa, Empresa.class.getName());
			if(coll == null || coll.isEmpty()){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, " Empresa está ");
			}

			sessao.setAttribute("collEmpresa", coll);
		}

		if(sessao.getAttribute("collUsuarioTipo") == null || !sessao.getAttribute("collUsuarioTipo").equals("")){

			FiltroUsuarioTipo filtroUsuarioTipo = new FiltroUsuarioTipo();

			// caso o usuário não seja administrador então
			if(!usuario.getUsuarioTipo().getId().equals(UsuarioTipo.USUARIO_TIPO_ADMINISTRADOR)){
				// não seta na coleção de usuário tipo o tipo administrador
				filtroUsuarioTipo.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroUsuarioTipo.ID,
								UsuarioTipo.USUARIO_TIPO_ADMINISTRADOR));
			}

			filtroUsuarioTipo.adicionarParametro(new ParametroSimples(FiltroUsuarioTipo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection coll = Fachada.getInstancia().pesquisar(filtroUsuarioTipo, UsuarioTipo.class.getName());
			if(coll == null || coll.isEmpty()){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, " Tipo do Usúario está ");
			}

			sessao.setAttribute("collUsuarioTipo", coll);
		}

		sessao.setAttribute("usuarioCadastrar", usuarioCadastrar);

		sessao.setAttribute("usuario", usuario);

		return retorno;
	}
}