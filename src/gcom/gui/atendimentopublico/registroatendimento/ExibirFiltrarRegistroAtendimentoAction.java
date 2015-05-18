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

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.*;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.endereco.FiltroLogradouro;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.geografico.*;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Filtrar Registro Atendimento - Exibir
 * 
 * @author Leonardo Regis - 02/08/2006
 * @author eduardo henrique
 * @date 10/03/2009 - Inclusão de filtros de Datas de Previsão Inicial e Final de Atendimento
 */
public class ExibirFiltrarRegistroAtendimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("filtrarRegistroAtendimento");
		// Instacia a fachada
		Fachada fachada = Fachada.getInstancia();
		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		// Form
		FiltrarRegistroAtendimentoActionForm filtrarRegistroAtendimentoActionForm = (FiltrarRegistroAtendimentoActionForm) actionForm;
		filtrarRegistroAtendimentoActionForm.setTipoPesquisa("0");

		// Colocado por Raphael Rossiter em 29/09/2006... Solicitado por Fátima
		String menu = httpServletRequest.getParameter("menu");

		if(menu != null && !menu.equalsIgnoreCase("")){
			filtrarRegistroAtendimentoActionForm.setSituacao(ConstantesSistema.SET_ZERO.toString());

			filtrarRegistroAtendimentoActionForm.setIndicadorOrdemServicoGerada(ConstantesSistema.TODOS.toString());

			filtrarRegistroAtendimentoActionForm.setIndicadorGeradaPelaUnidadeAtual(ConstantesSistema.TODOS.toString());

			// Sugerindo um período para realização da filtragem de um RA
			// SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
			Integer qtdDias = 30;

			Date dataAtual = new Date();
			Date dataSugestao = Util.subtrairNumeroDiasDeUmaData(dataAtual, qtdDias);

			filtrarRegistroAtendimentoActionForm.setPeriodoAtendimentoInicial(Util.formatarData(dataSugestao));
			filtrarRegistroAtendimentoActionForm.setPeriodoAtendimentoFinal(Util.formatarData(dataAtual));
		}

		// Matrícula
		if(filtrarRegistroAtendimentoActionForm.getMatriculaImovel() != null
						&& !filtrarRegistroAtendimentoActionForm.getMatriculaImovel().equals("")){

			getImovel(filtrarRegistroAtendimentoActionForm, httpServletRequest);
		}

		// Cliente
		if(filtrarRegistroAtendimentoActionForm.getCodigoCliente() != null
						&& !filtrarRegistroAtendimentoActionForm.getCodigoCliente().equals("")){

			getCliente(filtrarRegistroAtendimentoActionForm, httpServletRequest);
		}

		// Usuário
		if(filtrarRegistroAtendimentoActionForm.getLoginUsuario() != null
						&& !filtrarRegistroAtendimentoActionForm.getLoginUsuario().equals("")){

			getUsuario(filtrarRegistroAtendimentoActionForm, fachada, filtrarRegistroAtendimentoActionForm.getLoginUsuario(), sessao);
		}

		// Tipo Solicitação
		getSolicitacaoTipoCollection(sessao, fachada);

		// Carrega Motivo da Reativação
		carregaMotivoReativacao(fachada, sessao);

		// Matrícula Atendente
		if(filtrarRegistroAtendimentoActionForm.getMatriculaAtendente() != null
						&& !filtrarRegistroAtendimentoActionForm.getMatriculaAtendente().equals("")){

			getMatriculaAtendente(filtrarRegistroAtendimentoActionForm, httpServletRequest);
		}

		// Especificação
		if(filtrarRegistroAtendimentoActionForm.getTipoSolicitacao() != null
						&& filtrarRegistroAtendimentoActionForm.getTipoSolicitacao().length > 0){

			getSolicitacaoTipoEspecificacaoCollection(sessao, fachada, filtrarRegistroAtendimentoActionForm);
		}else{

			filtrarRegistroAtendimentoActionForm.setSelectedTipoSolicitacaoSize("0");
		}

		// Unidade de Atendimento
		if(filtrarRegistroAtendimentoActionForm.getUnidadeAtendimentoId() != null
						&& !filtrarRegistroAtendimentoActionForm.getUnidadeAtendimentoId().equals("")){

			getUnidade(filtrarRegistroAtendimentoActionForm, httpServletRequest, 1);
		}

		// Unidade Atual
		if(filtrarRegistroAtendimentoActionForm.getUnidadeAtualId() != null
						&& !filtrarRegistroAtendimentoActionForm.getUnidadeAtualId().equals("")){

			getUnidade(filtrarRegistroAtendimentoActionForm, httpServletRequest, 2);
		}
		// Unidade Superior
		if(filtrarRegistroAtendimentoActionForm.getUnidadeSuperiorId() != null
						&& !filtrarRegistroAtendimentoActionForm.getUnidadeSuperiorId().equals("")){

			getUnidade(filtrarRegistroAtendimentoActionForm, httpServletRequest, 3);
		}
		// Município
		if(filtrarRegistroAtendimentoActionForm.getMunicipioId() != null
						&& !filtrarRegistroAtendimentoActionForm.getMunicipioId().equals("")){

			getMunicipio(filtrarRegistroAtendimentoActionForm, httpServletRequest);
		}
		// Bairro & Área do Bairro
		if(filtrarRegistroAtendimentoActionForm.getBairroCodigo() != null
						&& !filtrarRegistroAtendimentoActionForm.getBairroCodigo().equals("")){

			// [FS009] Verificar informação do município
			if(filtrarRegistroAtendimentoActionForm.getMunicipioId() != null
							&& !filtrarRegistroAtendimentoActionForm.getMunicipioId().equals("")){

				getBairro(filtrarRegistroAtendimentoActionForm, httpServletRequest);
			}else{

				throw new ActionServletException("atencao.filtrar_informar_municipio");
			}
		}
		// Logradouro
		if(filtrarRegistroAtendimentoActionForm.getLogradouroId() != null
						&& !filtrarRegistroAtendimentoActionForm.getLogradouroId().equals("")){

			getLogradouro(filtrarRegistroAtendimentoActionForm, httpServletRequest);
		}

		// Preenche o indicador na tela
		String indicadorProcessoAdmJud = filtrarRegistroAtendimentoActionForm.getIndicadorProcessoAdmJud();

		// Coloca com default o indicador de processo adm jud como (Todos)
		if(Util.isVazioOuBranco(indicadorProcessoAdmJud)){
			filtrarRegistroAtendimentoActionForm.setIndicadorProcessoAdmJud(ConstantesSistema.TODOS.toString());
		}

		// Preenche o indicador na tela
		String indicadorRaVencidas = filtrarRegistroAtendimentoActionForm.getIndicadorRaVencidas();

		// Coloca com default o indicador de processo adm jud como (Todos)
		if(Util.isVazioOuBranco(indicadorRaVencidas)){
			filtrarRegistroAtendimentoActionForm.setIndicadorRaVencidas(ConstantesSistema.TODOS.toString());
		}

		// Preenche o indicador na tela
		String indicadorRaPagamento = filtrarRegistroAtendimentoActionForm.getIndicadorRaPagamento();

		// Coloca com default o indicador de processo adm jud como (Todos)
		if(Util.isVazioOuBranco(indicadorRaPagamento)){
			filtrarRegistroAtendimentoActionForm.setIndicadorRaPagamento(ConstantesSistema.TODOS.toString());
		}

		// Preenche o indicador na tela
		String indicadorRaDevolucao = filtrarRegistroAtendimentoActionForm.getIndicadorRaDevolucao();

		// Coloca com default o indicador de processo adm jud como (Todos)
		if(Util.isVazioOuBranco(indicadorRaDevolucao)){
			filtrarRegistroAtendimentoActionForm.setIndicadorRaDevolucao(ConstantesSistema.TODOS.toString());
		}

		return retorno;
	}

	/**
	 * Recupera Imóvel
	 * 
	 * @author Leonardo Regis
	 * @date 02/08/2006
	 * @param filtrarRegistroAtendimentoActionForm
	 * @param fachada
	 */
	private void getImovel(FiltrarRegistroAtendimentoActionForm filtrarRegistroAtendimentoActionForm, HttpServletRequest httpServletRequest){

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		// [F0001] Valida Imovel
		// if (isValidateObject(filtrarRegistroAtendimentoActionForm)) {
		// Filtra Imovel
		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, filtrarRegistroAtendimentoActionForm.getMatriculaImovel()));
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");
		// Recupera Imóvel
		Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

		if(colecaoImovel != null && !colecaoImovel.isEmpty()){

			sessao.setAttribute("inscricaoImovelEncontrada", "true");
			Imovel imovel = (Imovel) colecaoImovel.iterator().next();
			filtrarRegistroAtendimentoActionForm.setInscricaoImovel(imovel.getInscricaoFormatada());
		}else{

			sessao.removeAttribute("inscricaoImovelEncontrada");
			filtrarRegistroAtendimentoActionForm.setMatriculaImovel("");
			filtrarRegistroAtendimentoActionForm.setInscricaoImovel("Matrícula inexistente");
		}

		filtrarRegistroAtendimentoActionForm.setValidaImovel("false");
		// }
	}

	/**
	 * Recupera Cliente
	 * 
	 */
	private void getCliente(FiltrarRegistroAtendimentoActionForm filtrarRegistroAtendimentoActionForm, HttpServletRequest httpServletRequest){

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		// Filtra Cliente
		FiltroCliente filtroCliente = new FiltroCliente();
		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, filtrarRegistroAtendimentoActionForm.getCodigoCliente()));
		// Recupera Cliente
		Collection colecaoCliente = fachada.pesquisar(filtroCliente, Cliente.class.getName());

		if(colecaoCliente != null && !colecaoCliente.isEmpty()){

			sessao.setAttribute("clienteEncontrado", "true");
			Cliente cliente = (Cliente) colecaoCliente.iterator().next();
			filtrarRegistroAtendimentoActionForm.setCodigoCliente(cliente.getId().toString());
			filtrarRegistroAtendimentoActionForm.setNomeCliente(cliente.getNome());
		}else{

			sessao.removeAttribute("clienteEncontrado");
			filtrarRegistroAtendimentoActionForm.setCodigoCliente("");
			filtrarRegistroAtendimentoActionForm.setNomeCliente("Cliente inexistente");
		}
	}

	/**
	 * Carrega coleção de solicitação tipo
	 * 
	 * @author Leonardo Regis
	 * @date 03/08/2006
	 * @param sessao
	 * @param fachada
	 */
	private void getSolicitacaoTipoCollection(HttpSession sessao, Fachada fachada){

		// Filtra Solicitação Tipo
		FiltroSolicitacaoTipo filtroSolicitacaoTipo = new FiltroSolicitacaoTipo();
		filtroSolicitacaoTipo.setCampoOrderBy(FiltroSolicitacaoTipo.DESCRICAO);
		Collection colecaoSolicitacaoTipo = fachada.pesquisar(filtroSolicitacaoTipo, SolicitacaoTipo.class.getName());

		if(colecaoSolicitacaoTipo != null && !colecaoSolicitacaoTipo.isEmpty()){

			sessao.setAttribute("colecaoTipoSolicitacao", colecaoSolicitacaoTipo);
		}else{

			throw new ActionServletException("atencao.naocadastrado", null, "Especificação");
		}
	}

	/**
	 * Carrega coleção de solicitação tipo especificação.
	 * 
	 * @author Leonardo Regis
	 * @date 03/08/2006
	 * @param sessao
	 * @param fachada
	 */
	private void getSolicitacaoTipoEspecificacaoCollection(HttpSession sessao, Fachada fachada,
					FiltrarRegistroAtendimentoActionForm filtrarRegistroAtendimentoActionForm){

		String[] solicitacaoTipo = (String[]) filtrarRegistroAtendimentoActionForm.getTipoSolicitacao();
		filtrarRegistroAtendimentoActionForm.setSelectedTipoSolicitacaoSize(solicitacaoTipo.length + "");

		if(solicitacaoTipo.length == 1){

			// Filtra Solicitação Tipo Especificação
			FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
			filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(
							FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ID, solicitacaoTipo[0]));
			filtroSolicitacaoTipoEspecificacao.setCampoOrderBy(FiltroSolicitacaoTipoEspecificacao.DESCRICAO);

			Collection colecaoSolicitacaoTipoEspecificacao = fachada.pesquisar(filtroSolicitacaoTipoEspecificacao,
							SolicitacaoTipoEspecificacao.class.getName());

			if(colecaoSolicitacaoTipoEspecificacao != null && !colecaoSolicitacaoTipoEspecificacao.isEmpty()){

				sessao.setAttribute("colecaoEspecificacao", colecaoSolicitacaoTipoEspecificacao);
			}else{

				sessao.setAttribute("colecaoEspecificacao", new ArrayList());
			}

		}else{

			sessao.setAttribute("colecaoEspecificacao", new ArrayList());
		}
	}

	/**
	 * Recupera Unidade
	 * [FS007] Verificar existência de unidades subordinadas
	 * 
	 * @author Leonardo Regis
	 * @date 02/08/2006
	 * @param filtrarRegistroAtendimentoActionForm
	 * @param fachada
	 * @param tipo
	 */
	private void getUnidade(FiltrarRegistroAtendimentoActionForm filtrarRegistroAtendimentoActionForm,
					HttpServletRequest httpServletRequest, int tipo){

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		// [F0006] Valida Unidade Atendimento
		// if (isValidateObject(filtrarRegistroAtendimentoActionForm)) {

		String unidadeId = "";
		switch(tipo){
			case 1:

				unidadeId = filtrarRegistroAtendimentoActionForm.getUnidadeAtendimentoId();
				filtrarRegistroAtendimentoActionForm.setUnidadeAtendimentoDescricao(getUnidadeDescricao(
								filtrarRegistroAtendimentoActionForm, fachada, unidadeId));

				if(filtrarRegistroAtendimentoActionForm.getUnidadeAtendimentoDescricao().equalsIgnoreCase("Unidade Inexistente")){

					sessao.removeAttribute("unidadeAtendimentoEncontrada");
					filtrarRegistroAtendimentoActionForm.setUnidadeAtendimentoId("");
				}else{

					sessao.setAttribute("unidadeAtendimentoEncontrada", "true");
					filtrarRegistroAtendimentoActionForm.setValidaUnidadeAtendimento("false");
				}
				break;
			case 2:

				unidadeId = filtrarRegistroAtendimentoActionForm.getUnidadeAtualId();
				filtrarRegistroAtendimentoActionForm.setUnidadeAtualDescricao(getUnidadeDescricao(filtrarRegistroAtendimentoActionForm,
								fachada, unidadeId));

				if(filtrarRegistroAtendimentoActionForm.getUnidadeAtualDescricao().equalsIgnoreCase("Unidade Inexistente")){

					sessao.removeAttribute("unidadeAtualEncontrada");
					filtrarRegistroAtendimentoActionForm.setUnidadeAtualId("");
				}else{

					sessao.setAttribute("unidadeAtualEncontrada", "true");
					filtrarRegistroAtendimentoActionForm.setValidaUnidadeAtual("false");
				}
				break;
			case 3:

				unidadeId = filtrarRegistroAtendimentoActionForm.getUnidadeSuperiorId();
				UnidadeOrganizacional unidadeOrganizacional = new UnidadeOrganizacional();
				unidadeOrganizacional.setId(new Integer(unidadeId));
				filtrarRegistroAtendimentoActionForm.setUnidadeSuperiorDescricao(getUnidadeDescricao(filtrarRegistroAtendimentoActionForm,
								fachada, unidadeId));

				if(filtrarRegistroAtendimentoActionForm.getUnidadeSuperiorDescricao().equalsIgnoreCase("Unidade Inexistente")){

					sessao.removeAttribute("unidadeSuperiorEncontrada");
					filtrarRegistroAtendimentoActionForm.setUnidadeSuperiorId("");
				}else{

					// [FS007] Verificar existência de unidades subordinadas
					fachada.verificarExistenciaUnidadesSubordinadas(unidadeOrganizacional);
					sessao.setAttribute("unidadeSuperiorEncontrada", "true");
					filtrarRegistroAtendimentoActionForm.setValidaUnidadeSuperior("false");
				}
				break;
			default:
				break;
		}
		// }
	}

	/**
	 * Recupera a Unidade Organizacional (Atendimento, Atual e Superior)
	 * [F0006] Valida Unidade
	 * 
	 * @author Leonardo Regis
	 * @date 04/08/2006
	 * @param filtrarRegistroAtendimentoActionForm
	 * @param fachada
	 * @param unidadeId
	 * @return Descrição da Unidade Filtrada
	 */
	private String getUnidadeDescricao(FiltrarRegistroAtendimentoActionForm filtrarRegistroAtendimentoActionForm, Fachada fachada,
					String unidadeId){

		// Filtra Unidade
		FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
		filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, unidadeId));
		// filtroUnidadeOrganizacional.adicionarCaminhoParaCarregamentoEntidade("descricao");
		// Recupera Unidade Organizacional
		Collection colecaoUnidadeOrganizacional = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

		if(colecaoUnidadeOrganizacional != null && !colecaoUnidadeOrganizacional.isEmpty()){

			UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) colecaoUnidadeOrganizacional.iterator().next();
			return unidadeOrganizacional.getDescricao();
		}else{

			return "Unidade Inexistente";
		}
	}

	/**
	 * Recupera o Usuário
	 * 
	 * @author Raphael Rossiter
	 * @date 11/12/2006
	 * @param filtrarRegistroAtendimentoActionForm
	 * @param fachada
	 * @param idUsuario
	 * @return Descrição da Unidade Filtrada
	 */
	private void getUsuario(FiltrarRegistroAtendimentoActionForm filtrarRegistroAtendimentoActionForm, Fachada fachada, String idUsuario,
					HttpSession sessao){

		// Filtra Usuario
		FiltroUsuario filtroUsuario = new FiltroUsuario();
		filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.LOGIN, idUsuario));

		// Recupera Usuário
		Collection colecaoUsuario = fachada.pesquisar(filtroUsuario, Usuario.class.getName());
		if(colecaoUsuario != null && !colecaoUsuario.isEmpty()){

			Usuario usuario = (Usuario) colecaoUsuario.iterator().next();
			sessao.setAttribute("usuarioEncontrado", "true");
			filtrarRegistroAtendimentoActionForm.setNomeUsuario(usuario.getNomeUsuario());
		}else{

			sessao.removeAttribute("usuarioEncontrado");
			filtrarRegistroAtendimentoActionForm.setLoginUsuario("");
			filtrarRegistroAtendimentoActionForm.setNomeUsuario("Usuário Inexistente");
		}
	}

	/**
	 * Recupera Município
	 * 
	 * @author Leonardo Regis
	 * @date 02/08/2006
	 * @param filtrarRegistroAtendimentoActionForm
	 * @param fachada
	 */
	private void getMunicipio(FiltrarRegistroAtendimentoActionForm filtrarRegistroAtendimentoActionForm,
					HttpServletRequest httpServletRequest){

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		// [F0008] Valida Município
		// if (isValidateObject(filtrarRegistroAtendimentoActionForm)) {
		// Filtra Imovel
		FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
		filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, filtrarRegistroAtendimentoActionForm.getMunicipioId()));
		// filtroMunicipio.adicionarCaminhoParaCarregamentoEntidade("nome");
		// Recupera Município
		Collection colecaoMunicipio = fachada.pesquisar(filtroMunicipio, Municipio.class.getName());

		if(colecaoMunicipio != null && !colecaoMunicipio.isEmpty()){

			sessao.setAttribute("municipioEncontrada", "true");
			Municipio municipio = (Municipio) colecaoMunicipio.iterator().next();
			filtrarRegistroAtendimentoActionForm.setMunicipioDescricao(municipio.getNome());
		}else{

			sessao.removeAttribute("municipioEncontrada");
			filtrarRegistroAtendimentoActionForm.setMunicipioId("");
			filtrarRegistroAtendimentoActionForm.setMunicipioDescricao("Município inexistente");
		}

		filtrarRegistroAtendimentoActionForm.setValidaMunicipio("false");
		// }
	}

	/**
	 * Recupera Bairro
	 * 
	 * @author Leonardo Regis
	 * @date 02/08/2006
	 * @param filtrarRegistroAtendimentoActionForm
	 * @param fachada
	 * @param sessao
	 */
	private void getBairro(FiltrarRegistroAtendimentoActionForm filtrarRegistroAtendimentoActionForm, HttpServletRequest httpServletRequest){

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		// [F0010] Valida Bairro
		// if (isValidateObject(filtrarRegistroAtendimentoActionForm)) {
		// Filtra Bairro
		FiltroBairro filtroBairro = new FiltroBairro();
		filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.CODIGO, filtrarRegistroAtendimentoActionForm.getBairroCodigo()));
		filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, filtrarRegistroAtendimentoActionForm
						.getMunicipioId()));
		// filtroBairro.adicionarCaminhoParaCarregamentoEntidade("nome");
		// Recupera Município
		Collection colecaoBairro = fachada.pesquisar(filtroBairro, Bairro.class.getName());
		if(colecaoBairro != null && !colecaoBairro.isEmpty()){

			sessao.setAttribute("bairroEncontrada", "true");
			Bairro bairro = (Bairro) colecaoBairro.iterator().next();
			filtrarRegistroAtendimentoActionForm.setBairroId(bairro.getId().toString());
			filtrarRegistroAtendimentoActionForm.setBairroDescricao(bairro.getNome());
			carregaBairroArea(bairro.getId(), fachada, sessao);
		}else{

			sessao.removeAttribute("bairroEncontrada");
			filtrarRegistroAtendimentoActionForm.setBairroId("");
			filtrarRegistroAtendimentoActionForm.setBairroCodigo("");
			filtrarRegistroAtendimentoActionForm.setBairroDescricao("Bairro inexistente");
		}

		filtrarRegistroAtendimentoActionForm.setValidaBairro("false");
		// }
	}

	/**
	 * Recupera Logradouro
	 * 
	 * @author Leonardo Regis
	 * @date 02/08/2006
	 * @param filtrarRegistroAtendimentoActionForm
	 * @param fachada
	 */
	private void getLogradouro(FiltrarRegistroAtendimentoActionForm filtrarRegistroAtendimentoActionForm,
					HttpServletRequest httpServletRequest){

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		// [F0011] Valida Logradouro
		// if (isValidateObject(filtrarRegistroAtendimentoActionForm)) {
		// Filtra Logradouro
		FiltroLogradouro filtroLogradouro = new FiltroLogradouro();
		filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, filtrarRegistroAtendimentoActionForm
						.getLogradouroId()));

		// filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("nome");
		// Recupera Logradouro
		Collection colecaoLogradouro = fachada.pesquisar(filtroLogradouro, Logradouro.class.getName());
		if(colecaoLogradouro != null && !colecaoLogradouro.isEmpty()){

			sessao.setAttribute("logradouroEncontrada", "true");
			Logradouro logradouro = (Logradouro) colecaoLogradouro.iterator().next();
			filtrarRegistroAtendimentoActionForm.setLogradouroDescricao(logradouro.getNome());
		}else{

			sessao.removeAttribute("logradouroEncontrada");
			filtrarRegistroAtendimentoActionForm.setLogradouroId("");
			filtrarRegistroAtendimentoActionForm.setLogradouroDescricao("Logradouro inexistente");
		}

		filtrarRegistroAtendimentoActionForm.setValidaLogradouro("false");
		// }
	}

	/**
	 * Carrega Área do Bairro de acordo com o bairro informado
	 * 
	 * @author Leonardo Regis
	 * @date 04/08/2006
	 * @param bairroId
	 * @param fachada
	 * @param sessao
	 */
	private void carregaBairroArea(int bairroId, Fachada fachada, HttpSession sessao){

		FiltroBairroArea filtroBairroArea = new FiltroBairroArea();
		filtroBairroArea.adicionarParametro(new ParametroSimples(FiltroBairroArea.ID_BAIRRO, bairroId));
		filtroBairroArea.setCampoOrderBy(FiltroBairroArea.NOME);
		// filtroBairroArea.adicionarCaminhoParaCarregamentoEntidade("nome");
		// Recupera Área do Bairro
		Collection colecaoBairroArea = fachada.pesquisar(filtroBairroArea, BairroArea.class.getName());

		if(colecaoBairroArea != null && !colecaoBairroArea.isEmpty()){

			sessao.setAttribute("colecaoBairroArea", colecaoBairroArea);
		}else{

			sessao.removeAttribute("colecaoBairroArea");
		}
	}

	/**
	 * Valida Objeto com <Enter> da tela
	 * 
	 * @author Leonardo Regis
	 * @date 02/08/2006
	 * @return está validando algum objeto através do enter?
	 */
	/*
	 * private boolean isValidateObject(FiltrarRegistroAtendimentoActionForm form) {
	 * boolean toReturn = false;
	 * if (form.getValidaImovel().equalsIgnoreCase("true") ||
	 * form.getValidaUnidadeAtendimento().equalsIgnoreCase("true") ||
	 * form.getValidaUnidadeAtual().equalsIgnoreCase("true") ||
	 * form.getValidaUnidadeSuperior().equalsIgnoreCase("true") ||
	 * form.getValidaMunicipio().equalsIgnoreCase("true") ||
	 * form.getValidaBairro().equalsIgnoreCase("true") ||
	 * form.getValidaLogradouro().equalsIgnoreCase("true")) {
	 * toReturn = true;
	 * }
	 * return toReturn;
	 * }
	 */

	/**
	 * Motivo da Reativação - Carregando a coleção que irá ficar disponível
	 * para escolha do usuário
	 * 
	 * @author Ailton Sousa
	 * @date 08/04/2011
	 *       [FS0003] – Verificar existência de dados
	 */
	private void carregaMotivoReativacao(Fachada fachada, HttpSession sessao){

		FiltroRAMotivoReativacao filtroRAMotivoReativacao = new FiltroRAMotivoReativacao();
		filtroRAMotivoReativacao.setCampoOrderBy(FiltroRAMotivoReativacao.DESCRICAO);
		filtroRAMotivoReativacao.adicionarParametro(new ParametroSimples(FiltroRAMotivoReativacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoMotivoReativacao = fachada.pesquisar(filtroRAMotivoReativacao, RaMotivoReativacao.class.getName());

		if(colecaoMotivoReativacao == null || colecaoMotivoReativacao.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Motide da Reativação");
		}

		sessao.setAttribute("colecaoMotivoReativacao", colecaoMotivoReativacao);
	}

	/**
	 * Recupera Matrícula do Atendente
	 * 
	 * @author Ailton Sousa
	 * @date 08/04/2011
	 * @param filtrarRegistroAtendimentoActionForm
	 * @param fachada
	 */
	private void getMatriculaAtendente(FiltrarRegistroAtendimentoActionForm filtrarRegistroAtendimentoActionForm,
					HttpServletRequest httpServletRequest){

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Filtra Usuario
		FiltroUsuario filtroUsuario = new FiltroUsuario();
		filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, filtrarRegistroAtendimentoActionForm
						.getMatriculaAtendente()));

		// Recupera Usuário
		Collection colecaoUsuario = fachada.pesquisar(filtroUsuario, Usuario.class.getName());
		if(colecaoUsuario != null && !colecaoUsuario.isEmpty()){

			Usuario usuario = (Usuario) colecaoUsuario.iterator().next();
			sessao.setAttribute("atendenteEncontrado", "true");
			filtrarRegistroAtendimentoActionForm.setNomeAtendente(usuario.getNomeUsuario());
		}else{

			sessao.removeAttribute("atendenteEncontrado");
			filtrarRegistroAtendimentoActionForm.setMatriculaAtendente("");
			filtrarRegistroAtendimentoActionForm.setNomeAtendente("Atendente Inexistente");
		}

		// filtrarRegistroAtendimentoActionForm.setValidaMunicipio("false");
	}
}