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
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.FiltroUsuarioGrupo;
import gcom.seguranca.acesso.usuario.FiltroUsuarioTipo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioGrupo;
import gcom.seguranca.acesso.usuario.UsuarioTipo;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que exibe o menu
 * 
 * @author Administrador
 * @date 02/05/2006
 */
public class AtualizarUsuarioDadosGeraisAction
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

		AtualizarUsuarioDadosGeraisActionForm form = (AtualizarUsuarioDadosGeraisActionForm) actionForm;

		ActionForward retorno = actionMapping.findForward("gerenciadorProcesso");
		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		// Usuario que vai ser cadastrado no sistema, usado só nessa
		// funcionalidade
		Usuario usuarioParaAtualizar = (Usuario) sessao.getAttribute("usuarioParaAtualizar");

		if(usuarioParaAtualizar == null){
			usuarioParaAtualizar = new Usuario();
		}

		if(!"".equals(form.getDataNascimento())){
			Date data = Util.converteStringParaDate(form.getDataNascimento(), "dd/MM/yyyy");
			if(data == null){
				throw new ActionServletException("atencao.data.nascimento.invalida");
			}
			if(data.getTime() > new Date(System.currentTimeMillis()).getTime()){
				throw new ActionServletException("atencao.data_nascimento.posterior.hoje", null, Util.formatarData(new Date()));
			}
		}
		if(!"".equals(form.getDataInicial())){
			Date data = Util.converteStringParaDate(form.getDataInicial(), "dd/MM/yyyy");
			if(data == null){
				throw new ActionServletException("atencao.data.inicio.invalida");
			}
			if(data.getTime() > new Date(System.currentTimeMillis()).getTime()){
				throw new ActionServletException("atencao.data_inicial.posterior.hoje", null, Util.formatarData(new Date()));
			}
		}
		if(!"".equals(form.getDataFinal())){
			Date data = Util.converteStringParaDate(form.getDataFinal(), "dd/MM/yyyy");
			if(data == null){
				throw new ActionServletException("atencao.data.final.invalida");
			}
			Calendar dataFim = new GregorianCalendar();
			dataFim.setTime(data);
			dataFim.set(Calendar.HOUR, 23);
			dataFim.set(Calendar.MINUTE, 59);
			dataFim.set(Calendar.SECOND, 59);
			data = dataFim.getTime();

			if(data.getTime() < new Date(System.currentTimeMillis()).getTime()){
				throw new ActionServletException("atencao.data_final.posterior.hoje", null, Util.formatarData(new Date()));
			}
		}
		if(!"".equals(form.getDataNascimento())){
			Date dataNascimento = Util.converterStringParaData(form.getDataNascimento());

			usuarioParaAtualizar.setDataNascimento(dataNascimento);
		}
		if(!"".equals(form.getDataInicial()) && !"".equals(form.getDataFinal())){
			Date dataInicial = Util.converteStringParaDate(form.getDataInicial());
			Date dataFinal = Util.converteStringParaDate(form.getDataFinal());

			if(dataFinal.getTime() < dataInicial.getTime()){
				throw new ActionServletException("atencao.data.intervalo.invalido", null, Util.formatarData(new Date()));
			}
			usuarioParaAtualizar.setDataCadastroInicio(dataInicial);
			usuarioParaAtualizar.setDataCadastroFim(dataFinal);
		}

		if(!"".equals(form.getLogin()) && !form.getLogin().equalsIgnoreCase(usuarioParaAtualizar.getLogin())){
			FiltroUsuario filtroUsuario = new FiltroUsuario();
			filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.LOGIN, form.getLogin()));
			if(usuarioParaAtualizar.getId() != null){
				filtroUsuario.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroUsuario.ID, usuarioParaAtualizar.getId()));
			}
			Collection coll = Fachada.getInstancia().pesquisar(filtroUsuario, Usuario.class.getName());
			if(coll != null && !coll.isEmpty()){
				throw new ActionServletException("atencao.usuario.login.ja.existe", null, ((Usuario) Util.retonarObjetoDeColecao(coll))
								.getLogin());
			}
		}

		if(!"".equals(form.getEmail()) && !form.getEmail().equalsIgnoreCase(usuarioParaAtualizar.getDescricaoEmail())){
			FiltroUsuario filtroUsuario = new FiltroUsuario();
			filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.EMAIL, form.getEmail()));
			if(usuarioParaAtualizar.getId() != null){
				filtroUsuario.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroUsuario.ID, usuarioParaAtualizar.getId()));
			}
			Collection coll = Fachada.getInstancia().pesquisar(filtroUsuario, Usuario.class.getName());
			if(coll != null && !coll.isEmpty()){
				throw new ActionServletException("atencao.usuario.email.ja.existe");
			}
		}

		// valida os campos obrigatórios do usuario tipo
		if(!"".equals(form.getUsuarioTipo())){

			FiltroUsuarioTipo filtroUsuarioTipo = new FiltroUsuarioTipo();
			filtroUsuarioTipo.adicionarParametro(new ParametroSimples(FiltroUsuarioTipo.ID, form.getUsuarioTipo()));
			Collection coll = Fachada.getInstancia().pesquisar(filtroUsuarioTipo, UsuarioTipo.class.getName());
			if(coll != null && !coll.isEmpty()){
				UsuarioTipo usuarioTipo = (UsuarioTipo) Util.retonarObjetoDeColecao(coll);

				// caso não seja usuario tipo adiministrador então valida os
				// campos
				if(!usuarioTipo.getId().equals(UsuarioTipo.USUARIO_TIPO_ADMINISTRADOR)){

					// valida os campos obrigatorios
					if(usuarioTipo.getIndicadorFuncionario() == UsuarioTipo.INDICADOR_FUNCIONARIO){
						// matricula do funcionário é obrigatório
						if(form.getIdFuncionario() == null || form.getIdFuncionario().equals("")){
							throw new ActionServletException("atencao.required", null, "Matrícula Funcionário");
						}else{
							FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
							filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, form.getIdFuncionario()));
							filtroFuncionario.adicionarCaminhoParaCarregamentoEntidade(FiltroFuncionario.UNIDADE_ORGANIZACIONAL);
							// filtroFuncionario.adicionarCaminhoParaCarregamentoEntidade(FiltroFuncionario.UNIDADE_EMPRESA_ID);
							Collection colecaoFuncionario = Fachada.getInstancia()
											.pesquisar(filtroFuncionario, Funcionario.class.getName());
							if(colecaoFuncionario != null && !colecaoFuncionario.isEmpty()){
								Funcionario f = (Funcionario) colecaoFuncionario.iterator().next();
								usuarioParaAtualizar.setFuncionario(f);
								form.setIdFuncionario(f.getId().toString());
								form.setNomeFuncionario(f.getNome());
								usuarioParaAtualizar.setNomeUsuario(f.getNome());
							}else{
								throw new ActionServletException("atencao.required", null, "Matrícula Funcionário");
							}
						}
						// nome do funcionario é obrigatorio
						if(form.getNome() == null || form.getNome().equals("")){
							throw new ActionServletException("atencao.required", null, "Nome Usuário");
						}

					}else{
						if(usuarioTipo.getIndicadorFuncionario() != UsuarioTipo.INDICADOR_FUNCIONARIO){
							// data inicio e data fim é obrigatorio
							if(form.getEmpresa() == null || form.getEmpresa().equals("")){
								throw new ActionServletException("atencao.required", null, "Empresa");
							}
							// data inicio e data fim é obrigatorio
							if(form.getNome() == null || form.getNome().equals("")){
								throw new ActionServletException("atencao.required", null, "Nome Usuário");
							}
							// data inicio e data fim é obrigatorio
							if(form.getIdLotacao() == null || form.getIdLotacao().equals("")){
								throw new ActionServletException("atencao.required", null, "Unidade Lotação");
							}
							// data inicio e data fim é obrigatorio
							if(form.getDataInicial() == null || form.getDataInicial().equals("") || form.getDataFinal() == null
											|| form.getDataFinal().equals("")){
								throw new ActionServletException("atencao.required", null, "Período de Cadastramento");
							}
						}
					}
				}
				usuarioParaAtualizar.setUsuarioTipo(usuarioTipo);
			}else{
				usuarioParaAtualizar.setUsuarioTipo(null);
			}

		}else{
			usuarioParaAtualizar.setUsuarioTipo(null);
		}

		if(!"".equals(form.getEmpresa())){
			if(!(usuarioParaAtualizar.getEmpresa() != null && usuarioParaAtualizar.getEmpresa().getId() != null && usuarioParaAtualizar
							.getEmpresa().getId().toString().equals(form.getEmpresa()))){

				FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
				filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.ID, form.getEmpresa()));
				Collection coll = Fachada.getInstancia().pesquisar(filtroEmpresa, Empresa.class.getName());
				if(coll != null && !coll.isEmpty()){
					usuarioParaAtualizar.setEmpresa((Empresa) coll.iterator().next());
				}else{
					usuarioParaAtualizar.setEmpresa(null);
				}
			}
		}else{
			usuarioParaAtualizar.setEmpresa(null);
		}

		if(!"".equals(form.getIdFuncionario())){
			if(!(usuarioParaAtualizar.getFuncionario() != null && usuarioParaAtualizar.getFuncionario().getId() != null && usuarioParaAtualizar
							.getFuncionario().getId().toString().equals(form.getIdFuncionario()))){

				FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
				filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, form.getIdFuncionario()));
				filtroFuncionario.adicionarCaminhoParaCarregamentoEntidade(FiltroFuncionario.UNIDADE_ORGANIZACIONAL);
				Collection coll = Fachada.getInstancia().pesquisar(filtroFuncionario, Funcionario.class.getName());
				if(coll != null && !coll.isEmpty()){
					usuarioParaAtualizar.setFuncionario((Funcionario) coll.iterator().next());
				}else{
					usuarioParaAtualizar.setFuncionario(null);
				}
			}
		}else{
			usuarioParaAtualizar.setFuncionario(null);
		}

		// valida a unidade de lotação
		if(!"".equals(form.getIdLotacao())){

			// if (!(usuarioParaAtualizar.getUnidadeOrganizacional() != null
			// && usuarioParaAtualizar.getUnidadeOrganizacional().getId() != null
			// && usuarioParaAtualizar.getUnidadeOrganizacional().getId()
			// .toString().equals(form.getIdLotacao()) && usuarioParaAtualizar
			// .getUnidadeOrganizacional().getDescricao().equals(
			// form.getNomeLotacao()))) {

			if(usuarioParaAtualizar.getUnidadeOrganizacional() == null
							|| (usuarioParaAtualizar.getUnidadeOrganizacional() != null
											&& usuarioParaAtualizar.getUnidadeOrganizacional().getId() != null && !form.getIdLotacao()
											.equals(usuarioParaAtualizar.getUnidadeOrganizacional().getId().toString()))
							|| (usuarioParaAtualizar.getUsuarioTipo().getIndicadorFuncionario() == UsuarioTipo.INDICADOR_FUNCIONARIO
											&& usuarioParaAtualizar.getUnidadeOrganizacional() != null
											&& usuarioParaAtualizar.getUnidadeOrganizacional().getId() != null && !form.getIdLotacao()
											.equals(usuarioParaAtualizar.getFuncionario().getUnidadeOrganizacional().getId().toString()))){

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
									throw new ActionServletException("atencao.usuario.sem.permissao", usuario.getLogin(), unidadeEmpresa
													.getDescricao());
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
										if(unidadeEmpresa.getUnidadeSuperior() != null && !unidadeEmpresa.getUnidadeSuperior().equals("")){
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
									filtroUnidadeEmpresaSuperior.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID,
													idUnidadeEmpresaSuperior));
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

							// Alterado por Vivianne Sousa 13/02/2007
							// solicitado por Leonardo Vieira
							if(usuarioParaAtualizar.getUsuarioTipo().getIndicadorFuncionario() == UsuarioTipo.INDICADOR_FUNCIONARIO){
								Funcionario funcionario = usuarioParaAtualizar.getFuncionario();
								funcionario.setUnidadeOrganizacional(unidadeEmpresa);
								usuarioParaAtualizar.setFuncionario(funcionario);
							}

						}
					}
					short indicadorFuncionario = new Short(form.getIndicadorFuncionario());
					if(form.getUsuarioTipo() != null && indicadorFuncionario != UsuarioTipo.INDICADOR_FUNCIONARIO){
						usuarioParaAtualizar.setUnidadeOrganizacional(unidadeEmpresa);
					}else{
						usuarioParaAtualizar.setUnidadeOrganizacional(null);
					}
				}else{
					usuarioParaAtualizar.setUnidadeOrganizacional(null);
				}
			}
		}else{
			usuarioParaAtualizar.setUnidadeOrganizacional(null);
		}

		if(!"".equals(form.getNome())){
			usuarioParaAtualizar.setNomeUsuario(form.getNome());
		}
		if(!"".equals(form.getDataInicial())) usuarioParaAtualizar
						.setDataCadastroInicio(Util.converteStringParaDate(form.getDataInicial()));
		if(!"".equals(form.getDataFinal())) usuarioParaAtualizar.setDataCadastroFim(Util.converteStringParaDate(form.getDataFinal()));
		if(!"".equals(form.getLogin())) usuarioParaAtualizar.setLogin(form.getLogin());
		if(!"".equals(form.getEmail())) usuarioParaAtualizar.setDescricaoEmail(form.getEmail());

		sessao.setAttribute("usuarioParaAtualizar", usuarioParaAtualizar);

		sessao.setAttribute("usuario", usuario);

		return retorno;
	}
}