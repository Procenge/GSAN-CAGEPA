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

package gcom.gui.cadastro.imovel;

import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.FiltroRendaFamiliarFaixa;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.RendaFamiliarFaixa;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.FiltroParametro;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 */
public class ExibirAtualizarImovelConclusaoAction
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

		ActionForward retorno = actionMapping.findForward("atualizarImovelConclusao");

		// obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		DynaValidatorForm inserirImovelConclusaoActionForm = (DynaValidatorForm) actionForm;

		// Cria Filtros

		FiltroRendaFamiliarFaixa filtroRendaFamiliar = new FiltroRendaFamiliarFaixa();

		// Cria coleçao

		Collection rendaFamiliarFaixas = null;

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		// CARREGA RENDA FAMILIAR
		filtroRendaFamiliar.setCampoOrderBy(FiltroRendaFamiliarFaixa.MENOR_FAIXA);
		filtroRendaFamiliar.adicionarParametro(new ParametroSimples(FiltroRendaFamiliarFaixa.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		rendaFamiliarFaixas = fachada.pesquisar(filtroRendaFamiliar, RendaFamiliarFaixa.class.getName());
		if(rendaFamiliarFaixas == null || rendaFamiliarFaixas.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Renda Familiar Faixa");
		}
		httpServletRequest.setAttribute("rendaFamiliarFaixas", rendaFamiliarFaixas);

		// Faz a pesquisa de Ocupacao Tipo

		// Testa se já existe um clinte proprietário
		if(sessao.getAttribute("imovelClientesNovos") == null){
			httpServletRequest.setAttribute("envioContaListar", "naoListar");
		}else{
			Collection imovelClientes = (Collection) sessao.getAttribute("imovelClientesNovos");
			ClienteImovel clienteImovel = new ClienteImovel();
			Iterator iteratorClienteImovel = imovelClientes.iterator();

			while(iteratorClienteImovel.hasNext()){
				clienteImovel = null;
				clienteImovel = (ClienteImovel) iteratorClienteImovel.next();
				if(clienteImovel.getClienteRelacaoTipo().getDescricao().equalsIgnoreCase(ConstantesSistema.IMOVEL_ENVIO_CONTA)){
					httpServletRequest.setAttribute("envioContaListar", "listar");
				}
			}
		}

		Imovel imovel = (Imovel) sessao.getAttribute("imovelAtualizacao");
		if(imovel.getImovelPerfil() != null && imovel.getImovelPerfil().getId().equals(ConstantesSistema.INDICADOR_TARIFA_SOCIAL)){

			httpServletRequest.setAttribute("tarifaSocial", "1");
		}else{
			httpServletRequest.setAttribute("tarifaSocial", "0");
		}

		imovel = null;

		String pesquisar = httpServletRequest.getParameter("pesquisar");

		// Cria variaveis
		String idImovel = (String) inserirImovelConclusaoActionForm.get("idImovel");

		if(pesquisar != null && !pesquisar.equalsIgnoreCase("")){

			// Cria variaveis
			// Cria Filtros
			FiltroImovel filtroImovel = new FiltroImovel();

			// Objetos que serão retornados pelo Hibernate
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal");

			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal.quadra");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal.logradouroBairro.bairro.municipio.unidadeFederacao");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal.logradouroCep.cep");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal.logradouroCep.logradouro.logradouroTipo");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal.logradouroCep.logradouro.logradouroTitulo");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal.enderecoReferencia");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal.setorComercial");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("imovelPrincipal.imovelPrincipal");

			filtroImovel.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO, Imovel.IMOVEL_EXCLUIDO,
							FiltroParametro.CONECTOR_OR, 2));

			filtroImovel.adicionarParametro(new ParametroNulo(FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO));

			Collection imoveis = null;

			if(idImovel != null && !idImovel.trim().equalsIgnoreCase("")){

				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, new Integer(idImovel)));

				Imovel imovelAtualizado = (Imovel) sessao.getAttribute("imovelAtualizacao");

				// testa se o imovel a ser principal é o mesmo que esta sendo
				// atualizado
				if(idImovel.equals(imovelAtualizado.getId().toString())){

					// atualizarImovelPrincipalActionForm.set("idImovel","");
					throw new ActionServletException("atencao.imovel.principal.igual.atualizado");
				}

				imoveis = this.getFachada().pesquisar(filtroImovel, Imovel.class.getName());

				if(imoveis != null && !imoveis.isEmpty()){

					filtroImovel = new FiltroImovel();
					filtroImovel.limparListaParametros();

					// Cria Variaveis
					String idLocalidade = (String) inserirImovelConclusaoActionForm.get("idLocalidade");
					String idSetorComercial = (String) inserirImovelConclusaoActionForm.get("idSetorComercial");
					String idQuadra = (String) inserirImovelConclusaoActionForm.get("idQuadra");

					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel.trim()));
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LOCALIDADE_ID, new Integer(idLocalidade)));
					filtroImovel
									.adicionarParametro(new ParametroSimples(FiltroImovel.SETOR_COMERCIAL_CODIGO, new Integer(
													idSetorComercial)));
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.QUADRA_NUMERO, new Integer(idQuadra)));

					Collection colecaoImovel = null;
					colecaoImovel = this.getFachada().pesquisar(filtroImovel, Imovel.class.getName());

					if(colecaoImovel == null || colecaoImovel.isEmpty()){
						throw new ActionServletException("atencao.imovel_principal.inexistente.quadra");
					}

					sessao.setAttribute("idImoveilPrincipal", ((Imovel) imoveis.iterator().next()).getId().toString());
					sessao.setAttribute("imoveisPrincipal", imoveis);

					inserirImovelConclusaoActionForm.set("idImovel", idImovel);

					httpServletRequest.setAttribute("valorMatriculaImovelPrincipal", this.getFachada().pesquisarInscricaoImovel(
									new Integer(idImovel.trim()), true));
					httpServletRequest.setAttribute("idImovelPrincipalNaoEncontrado", null);

				}else{
					httpServletRequest.setAttribute("idImovelPrincipalNaoEncontrado", "true");
					httpServletRequest.setAttribute("valorMatriculaImovelPrincipal", "IMOVEL INEXISTENTE");
				}

			}
		}else{
			if(idImovel != null && !idImovel.equals("")){
				inserirImovelConclusaoActionForm.set("idImovel", idImovel);
				httpServletRequest.setAttribute("valorMatriculaImovelPrincipal", this.getFachada().pesquisarInscricaoImovel(
								new Integer(idImovel.trim()), true));

			}
		}

		String remover = httpServletRequest.getParameter("remover");
		String idFuncionario = (String) inserirImovelConclusaoActionForm.get("idFuncionario");

		if(remover != null && !remover.equalsIgnoreCase("")){
			// Cria variaveis
			Collection imoveisPrincipaisNovos = (Collection) sessao.getAttribute("imoveisPrincipal");

			inserirImovelConclusaoActionForm.set("idImovel", "");

			if(imoveisPrincipaisNovos != null && !imoveisPrincipaisNovos.equals("")){

				Iterator ImovelPrincipalIterator = imoveisPrincipaisNovos.iterator();

				while(ImovelPrincipalIterator.hasNext()){
					ImovelPrincipalIterator.next();
					ImovelPrincipalIterator.remove();
					inserirImovelConclusaoActionForm.set("idImovel", "");
					sessao.removeAttribute("idImoveilPrincipal");
				}
			}
		}

		if(idFuncionario != null && !idFuncionario.trim().equals("")){

			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, idFuncionario));

			Collection colecaoFuncionario = this.getFachada().pesquisar(filtroFuncionario, Funcionario.class.getName());

			if(colecaoFuncionario != null && !colecaoFuncionario.isEmpty()){

				Funcionario funcionario = (Funcionario) Util.retonarObjetoDeColecao(colecaoFuncionario);

				httpServletRequest.setAttribute("idImovelPrincipalEncontrado", "true");

				inserirImovelConclusaoActionForm.set("idFuncionario", funcionario.getId().toString());
				inserirImovelConclusaoActionForm.set("nomeFuncionario", funcionario.getNome());

			}else{

				inserirImovelConclusaoActionForm.set("idFuncionario", "");
				inserirImovelConclusaoActionForm.set("nomeFuncionario", "Funcionário Inexistente");

			}
		}

		return retorno;
	}

}
