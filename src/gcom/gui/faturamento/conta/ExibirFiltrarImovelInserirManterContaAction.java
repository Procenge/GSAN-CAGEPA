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

package gcom.gui.faturamento.conta;

import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.cliente.FiltroClienteRelacaoTipo;
import gcom.cadastro.endereco.FiltroLogradouro;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.*;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.io.*;
import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * [UC0407] Filtrar Imóveis para Inserir ou Manter Conta
 * 
 * @author Ana Maria
 * @created 12/03/2007
 * @author eduardo henrique
 * @date 08/01/2009
 *       Alteração para inclusão de filtro de bairro.
 */
public class ExibirFiltrarImovelInserirManterContaAction
				extends GcomAction {

	private Collection colecaoPesquisa = null;

	private String localidadeIDOrigem = null;

	private String localidadeIDDestino = null;

	private String setorComercialCDOrigem = null;

	private String setorComercialCDDestino = null;

	private String setorComercialIDOrigem = null;

	private String setorComercialIDDestino = null;

	private String quadraNMOrigem = null;

	private String quadraNMDestino = null;

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("filtrarImovelInserirManterConta");

		// Sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		FiltrarImovelContaActionForm filtrarImovelContaActionForm = (FiltrarImovelContaActionForm) actionForm;

		String codigoCliente = filtrarImovelContaActionForm.getCodigoCliente();

		FiltroClienteRelacaoTipo filtroClienteRelacaoTipo = new FiltroClienteRelacaoTipo();

		filtroClienteRelacaoTipo.adicionarParametro(new ParametroSimples(FiltroClienteRelacaoTipo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection<ClienteRelacaoTipo> collectionClienteRelacaoTipo = fachada.pesquisar(filtroClienteRelacaoTipo, ClienteRelacaoTipo.class
						.getName());

		if(collectionClienteRelacaoTipo != null && !collectionClienteRelacaoTipo.isEmpty()){
			httpServletRequest.setAttribute("collectionClienteRelacaoTipo", collectionClienteRelacaoTipo);
		}else{
			throw new ActionServletException("atencao.collectionClienteRelacaoTipo_inexistente", null, "id");
		}


	    // Faturamento Grupo
		this.getColecaoFaturamentoGrupo(sessao, fachada, filtrarImovelContaActionForm);

		// PESQUISAR CLIENTE
		if(codigoCliente != null && !codigoCliente.toString().trim().equalsIgnoreCase("")){
			this.pesquisarCliente(codigoCliente, filtrarImovelContaActionForm, fachada, httpServletRequest);
		}


		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");
		String inscricaoTipo = httpServletRequest.getParameter("inscricaoTipo");

		if(objetoConsulta != null && !objetoConsulta.trim().equalsIgnoreCase("") && inscricaoTipo != null
						&& !inscricaoTipo.trim().equalsIgnoreCase("")){

			switch(Integer.parseInt(objetoConsulta)){
				// Localidade
				case 1:

					pesquisarLocalidade(inscricaoTipo, filtrarImovelContaActionForm, fachada, httpServletRequest);

					break;
				// Setor Comercial
				case 2:

					pesquisarLocalidade(inscricaoTipo, filtrarImovelContaActionForm, fachada, httpServletRequest);

					pesquisarSetorComercial(inscricaoTipo, filtrarImovelContaActionForm, fachada, httpServletRequest);

					break;
				// Quadra
				case 3:

					pesquisarLocalidade(inscricaoTipo, filtrarImovelContaActionForm, fachada, httpServletRequest);

					pesquisarSetorComercial(inscricaoTipo, filtrarImovelContaActionForm, fachada, httpServletRequest);

					pesquisarQuadra(inscricaoTipo, filtrarImovelContaActionForm, fachada, httpServletRequest);

					break;
				case 4:

					break;
				default:
					break;
			}
		}
		
		String idBairroFiltro = null;
		String idMunicipioFiltro = null;
		
		idBairroFiltro = (String) filtrarImovelContaActionForm.getIdBairroFiltro();
		idMunicipioFiltro = (String) filtrarImovelContaActionForm.getIdMunicipioFiltro();
		

		// PESQUISAR MUNICIPIO
		if(idMunicipioFiltro != null && !idMunicipioFiltro.toString().trim().equalsIgnoreCase("")){
			this.pesquisarMunicipio(idMunicipioFiltro, filtrarImovelContaActionForm, fachada, httpServletRequest);
		}


		// PESQUISAR BAIRRO
		if(idBairroFiltro != null && !idBairroFiltro.toString().trim().equalsIgnoreCase("")){
			this.pesquisarBairro(idMunicipioFiltro, idBairroFiltro, filtrarImovelContaActionForm, fachada, httpServletRequest);
		}

		String codigoDigitadoLogradouroEnter = (String) filtrarImovelContaActionForm.getIdLogradouro();

		// Verifica se o código foi digitado
		if(codigoDigitadoLogradouroEnter != null && !codigoDigitadoLogradouroEnter.trim().equals("")
						&& Integer.parseInt(codigoDigitadoLogradouroEnter) > 0){

			// Manda para a página a informação do campo para que seja
			// focado no retorno da pesquisa
			httpServletRequest.setAttribute("nomeCampo", "logradouro");

			FiltroLogradouro filtroLogradouro = new FiltroLogradouro();

			filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("logradouroTipo");
			filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("logradouroTitulo");

			filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, codigoDigitadoLogradouroEnter));

			Collection logradouroEncontrado = fachada.pesquisar(filtroLogradouro, Logradouro.class.getName());

			if(logradouroEncontrado != null && !logradouroEncontrado.isEmpty()){
				// O logradouro foi encontrado
				filtrarImovelContaActionForm.setIdLogradouro("" + ((Logradouro) ((List) logradouroEncontrado).get(0)).getId());
				filtrarImovelContaActionForm.setDescricaoLogradouro(((Logradouro) ((List) logradouroEncontrado).get(0))
								.getDescricaoFormatada());
				httpServletRequest.setAttribute("idLogradouroNaoEncontrado", "true");

			}else{
				filtrarImovelContaActionForm.setIdLogradouro("");
				httpServletRequest.setAttribute("idLogradouroNaoEncontrado", "exception");
				filtrarImovelContaActionForm.setDescricaoLogradouro("Logradouro Inexistente");

			}
		}


		// Adicionar logradouro na coleção
		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("adicionarLogradouro"))){
			this.adicionarLogradouro(sessao, fachada, filtrarImovelContaActionForm);
		}

		// Remover logradouro na coleção
		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("removerLogradouro"))){

			String acao = (String) httpServletRequest.getParameter("removerLogradouro");

			this.removerLogradouro(sessao, httpServletRequest, filtrarImovelContaActionForm, acao);

		}


		Collection<Object[]> retornoArquivo = null;
		if(filtrarImovelContaActionForm.getArquivoMatricula() != null
						&& !filtrarImovelContaActionForm.getArquivoMatricula().getFileName().equals("")){

			retornoArquivo = validaArquivo(fachada, filtrarImovelContaActionForm, httpServletRequest, httpServletResponse);
			sessao.setAttribute("retornoArquivo", retornoArquivo);

		}

		sessao.setAttribute("telaFiltroImoveisConta", ConstantesSistema.SIM);

		return retorno;
	}

	/**
	 * Pesquisar Clientes
	 * 
	 * @param filtroCliente
	 * @param idCliente
	 * @param clientes
	 * @param form
	 * @param fachada
	 * @param httpServletRequest
	 */
	public void pesquisarCliente(String idCliente, FiltrarImovelContaActionForm form, Fachada fachada, HttpServletRequest httpServletRequest){

		FiltroCliente filtroCliente = new FiltroCliente();

		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente));
		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection clienteEncontrado = fachada.pesquisar(filtroCliente, Cliente.class.getName());

		if(clienteEncontrado != null && !clienteEncontrado.isEmpty()){
			// O Cliente foi encontrado
			if(((Cliente) ((List) clienteEncontrado).get(0)).getIndicadorUso().equals(ConstantesSistema.INDICADOR_USO_DESATIVO)){
				throw new ActionServletException("atencao.cliente.inativo", null, ""
								+ ((Cliente) ((List) clienteEncontrado).get(0)).getId());
			}

			form.setCodigoCliente(((Cliente) ((List) clienteEncontrado).get(0)).getId().toString());
			form.setNomeCliente(((Cliente) ((List) clienteEncontrado).get(0)).getNome());

		}else{
			httpServletRequest.setAttribute("corCliente", "exception");
			form.setCodigoCliente("");
			form.setNomeCliente(ConstantesSistema.CODIGO_CLIENTE_INEXISTENTE);
		}
	}

	private void pesquisarLocalidade(String inscricaoTipo, FiltrarImovelContaActionForm form, Fachada fachada,
					HttpServletRequest httpServletRequest){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

		// Recebe o valor do campo localidadeOrigemID do formulário.
		localidadeIDOrigem = form.getLocalidadeOrigemID();

		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeIDOrigem));

		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna localidade
		colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoPesquisa == null || colecaoPesquisa.isEmpty() && !localidadeIDOrigem.equalsIgnoreCase("")){
			// Localidade nao encontrada
			// Limpa os campos localidadeOrigemID e nomeLocalidadeOrigem do
			// formulário
			if(form.getLocalidadeOrigemID().equals(form.getLocalidadeDestinoID())){
				form.setLocalidadeDestinoID("");
			}
			form.setLocalidadeOrigemID("");
			form.setNomeLocalidadeOrigem("Localidade inexistente");
			httpServletRequest.setAttribute("corLocalidadeOrigem", "exception");
		}else if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
			Localidade objetoLocalidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);
			form.setLocalidadeOrigemID(String.valueOf(objetoLocalidade.getId()));
			form.setNomeLocalidadeOrigem(objetoLocalidade.getDescricao());
			if(form.getLocalidadeDestinoID() == null || form.getLocalidadeDestinoID().equals("")
							|| form.getLocalidadeOrigemID().equals(form.getLocalidadeDestinoID())){
				form.setLocalidadeDestinoID(String.valueOf(objetoLocalidade.getId()));
				form.setNomeLocalidadeDestino(objetoLocalidade.getDescricao());
			}
			httpServletRequest.setAttribute("corLocalidadeOrigem", "valor");
			httpServletRequest.setAttribute("nomeCampo", "setorComercialOrigemCD");
		}
		// Recebe o valor do campo localidadeDestinoID do formulário.
		localidadeIDDestino = form.getLocalidadeDestinoID();

		/*
		 * Alterado por Raphael Rossiter em 26/12/2007
		 * OBJ: Corrigir bug aberto por Rosana Carvalho por email em 26/12/2007
		 */
		if(localidadeIDDestino != null && !localidadeIDDestino.trim().equalsIgnoreCase("")){

			// Limpa os parametros do filtro
			filtroLocalidade.limparListaParametros();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeIDDestino));

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna localidade
			colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Localidade nao encontrada
				// Limpa os campos localidadeDestinoID e nomeLocalidadeDestino
				// do formulário
				form.setLocalidadeDestinoID("");
				form.setNomeLocalidadeDestino("Localidade inexistente");
				httpServletRequest.setAttribute("corLocalidadeDestino", "exception");
				httpServletRequest.setAttribute("nomeCampo", "localidadeDestinoID");
			}else{
				Localidade objetoLocalidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);
				form.setLocalidadeDestinoID(String.valueOf(objetoLocalidade.getId()));
				form.setNomeLocalidadeDestino(objetoLocalidade.getDescricao());
				httpServletRequest.setAttribute("corLocalidadeDestino", "valor");
				if(!form.getSetorComercialOrigemCD().equals("")){
					httpServletRequest.setAttribute("nomeCampo", "setorComercialDestinoCD");
				}
			}
		}

	}

	private void pesquisarSetorComercial(String inscricaoTipo, FiltrarImovelContaActionForm form, Fachada fachada,
					HttpServletRequest httpServletRequest){

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

		// Recebe o valor do campo localidadeOrigemID do formulário.
		localidadeIDOrigem = form.getLocalidadeOrigemID();

		// O campo localidadeOrigemID será obrigatório
		if(localidadeIDOrigem != null && !localidadeIDOrigem.trim().equalsIgnoreCase("")){

			setorComercialCDOrigem = form.getSetorComercialOrigemCD();

			// Adiciona o id da localidade que está no formulário para
			// compor a pesquisa.
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidadeIDOrigem));

			// Adiciona o código do setor comercial que esta no formulário
			// para compor a pesquisa.
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
							setorComercialCDOrigem));

			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna setorComercial
			colecaoPesquisa = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty() && !form.getSetorComercialOrigemCD().equals("")){
				// Setor Comercial nao encontrado
				// Limpa os campos setorComercialOrigemCD,
				// nomeSetorComercialOrigem e setorComercialOrigemID do
				// formulário
				if(form.getSetorComercialOrigemCD().equals(form.getSetorComercialDestinoCD())){
					form.setSetorComercialDestinoCD("");
				}
				form.setSetorComercialOrigemCD("");
				form.setSetorComercialOrigemID("");
				form.setNomeSetorComercialOrigem("Setor comercial inexistente");
				httpServletRequest.setAttribute("corSetorComercialOrigem", "exception");
			}else if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
				SetorComercial objetoSetorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoPesquisa);
				form.setSetorComercialOrigemCD(String.valueOf(objetoSetorComercial.getCodigo()));
				form.setSetorComercialOrigemID(String.valueOf(objetoSetorComercial.getId()));
				form.setNomeSetorComercialOrigem(objetoSetorComercial.getDescricao());
				if(form.getSetorComercialDestinoCD() == null || form.getSetorComercialDestinoCD().equals("")
								|| form.getSetorComercialDestinoCD().equals(form.getSetorComercialOrigemCD())){
					form.setSetorComercialDestinoCD(String.valueOf(objetoSetorComercial.getCodigo()));
					form.setSetorComercialDestinoID(String.valueOf(objetoSetorComercial.getId()));
					form.setNomeSetorComercialDestino(objetoSetorComercial.getDescricao());
				}
				httpServletRequest.setAttribute("corSetorComercialOrigem", "valor");
				httpServletRequest.setAttribute("nomeCampo", "quadraOrigemNM");
			}
		}else{
			// Limpa o campo setorComercialOrigemCD do formulário
			if(!form.getSetorComercialOrigemCD().equals("")){
				form.setSetorComercialOrigemCD("");
				form.setNomeSetorComercialOrigem("Informe a localidade da inscrição de origem.");
				httpServletRequest.setAttribute("corSetorComercialOrigem", "exception");
			}
		}
		// Recebe o valor do campo localidadeDestinoID do formulário.
		localidadeIDDestino = form.getLocalidadeDestinoID();

		// O campo localidadeOrigem será obrigatório
		if(localidadeIDDestino != null && !localidadeIDDestino.trim().equalsIgnoreCase("")){

			setorComercialCDDestino = form.getSetorComercialDestinoCD();

			// limpa o filtro
			filtroSetorComercial.limparListaParametros();

			// Adiciona o id da localidade que está no formulário para
			// compor a pesquisa.
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidadeIDDestino));

			// Adiciona o código do setor comercial que esta no formulário
			// para compor a pesquisa.
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
							setorComercialCDDestino));

			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna setorComercial
			colecaoPesquisa = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Setor Comercial nao encontrado
				// Limpa os campos setorComercialDestinoCD,
				// nomeSetorComercialDestino e setorComercialDestinoID do
				// formulário
				form.setSetorComercialDestinoCD("");
				form.setSetorComercialDestinoID("");
				form.setNomeSetorComercialDestino("Setor comercial inexistente");
				httpServletRequest.setAttribute("corSetorComercialDestino", "exception");
			}else{
				SetorComercial objetoSetorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoPesquisa);
				form.setSetorComercialDestinoCD(String.valueOf(objetoSetorComercial.getCodigo()));
				form.setSetorComercialDestinoID(String.valueOf(objetoSetorComercial.getId()));
				form.setNomeSetorComercialDestino(objetoSetorComercial.getDescricao());
				httpServletRequest.setAttribute("corSetorComercialDestino", "valor");
				if(!form.getQuadraOrigemNM().equals("")){
					httpServletRequest.setAttribute("nomeCampo", "quadraDestinoNM");
				}
			}
		}else{
			// Limpa o campo setorComercialDestinoCD do formulário
			form.setSetorComercialDestinoCD("");
			form.setNomeSetorComercialDestino("Informe a localidade da inscrição de destino.");
			httpServletRequest.setAttribute("corSetorComercialDestino", "exception");
		}
	}

	private void pesquisarQuadra(String inscricaoTipo, FiltrarImovelContaActionForm form, Fachada fachada,
					HttpServletRequest httpServletRequest){

		FiltroQuadra filtroQuadra = new FiltroQuadra();

		// Objetos que serão retornados pelo hibernate.
		filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("bairro");

		// Recebe os valores dos campos setorComercialOrigemCD e
		// setorComercialOrigemID do formulário.
		setorComercialCDOrigem = form.getSetorComercialOrigemCD();

		setorComercialIDOrigem = form.getSetorComercialOrigemID();

		// Os campos setorComercialOrigemCD e setorComercialID serão
		// obrigatórios
		if(setorComercialCDOrigem != null && !setorComercialCDOrigem.trim().equalsIgnoreCase("") && setorComercialIDOrigem != null
						&& !setorComercialIDOrigem.trim().equalsIgnoreCase("")){

			quadraNMOrigem = form.getQuadraOrigemNM();

			// Adiciona o id do setor comercial que está no formulário para
			// compor a pesquisa.
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, setorComercialIDOrigem));

			// Adiciona o número da quadra que esta no formulário para
			// compor a pesquisa.
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, quadraNMOrigem));

			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna quadra
			colecaoPesquisa = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty() && !form.getQuadraOrigemNM().equals("")){
				// Quadra nao encontrada
				// Limpa os campos quadraOrigemNM e quadraOrigemID do
				// formulário
				if(form.getQuadraOrigemNM().equals(form.getQuadraDestinoNM())){
					form.setQuadraDestinoNM("");
				}
				form.setQuadraOrigemNM("");
				form.setQuadraOrigemID("");
				// Mensagem de tela
				form.setQuadraMensagemOrigem("Quadra inexistente");
				httpServletRequest.setAttribute("corQuadraOrigem", "exception");
			}else if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
				Quadra objetoQuadra = (Quadra) Util.retonarObjetoDeColecao(colecaoPesquisa);
				form.setQuadraOrigemNM(String.valueOf(objetoQuadra.getNumeroQuadra()));
				form.setQuadraOrigemID(String.valueOf(objetoQuadra.getId()));
				if(form.getQuadraDestinoNM() == null || form.getQuadraDestinoNM().equals("")
								|| form.getQuadraOrigemNM().equals(form.getQuadraDestinoNM())){
					form.setQuadraDestinoNM(String.valueOf(objetoQuadra.getNumeroQuadra()));
					form.setQuadraDestinoID(String.valueOf(objetoQuadra.getId()));
				}
				httpServletRequest.setAttribute("corQuadraOrigem", "valor");
				httpServletRequest.setAttribute("nomeCampo", "loteOrigem");
			}
		}else{
			if(!form.getQuadraOrigemNM().equals("")){
				// Limpa o campo quadraOrigemNM do formulário
				form.setQuadraOrigemNM("");
				form.setQuadraMensagemOrigem("Informe o setor comercial da inscrição de origem.");
				httpServletRequest.setAttribute("corQuadraOrigem", "exception");
			}
		}
		// Recebe os valores dos campos setorComercialOrigemCD e
		// setorComercialOrigemID do formulário.
		setorComercialCDDestino = form.getSetorComercialDestinoCD();
		setorComercialIDDestino = form.getSetorComercialDestinoID();

		// Os campos setorComercialOrigemCD e setorComercialID serão
		// obrigatórios
		if(setorComercialCDDestino != null && !setorComercialCDDestino.trim().equalsIgnoreCase("") && setorComercialIDDestino != null
						&& !setorComercialIDDestino.trim().equalsIgnoreCase("")){

			quadraNMDestino = form.getQuadraDestinoNM();

			// Limpa os parametros do filtro
			filtroQuadra.limparListaParametros();

			// Adiciona o id do setor comercial que está no formulário para
			// compor a pesquisa.
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, setorComercialIDDestino));

			// Adiciona o número da quadra que esta no formulário para
			// compor a pesquisa.
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, quadraNMDestino));

			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna quadra
			colecaoPesquisa = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Quadra nao encontrada
				// Limpa os campos quadraOrigemNM e quadraOrigemID do
				// formulário
				form.setQuadraDestinoNM("");
				form.setQuadraDestinoID("");
				// Mensagem de tela
				form.setQuadraMensagemDestino("Quadra inexistente");
				httpServletRequest.setAttribute("corQuadraDestino", "exception");
			}else{
				Quadra objetoQuadra = (Quadra) Util.retonarObjetoDeColecao(colecaoPesquisa);
				form.setQuadraDestinoNM(String.valueOf(objetoQuadra.getNumeroQuadra()));
				form.setQuadraDestinoID(String.valueOf(objetoQuadra.getId()));
				httpServletRequest.setAttribute("corQuadraDestino", "valor");
				if(!form.getLoteOrigem().equals("")){
					httpServletRequest.setAttribute("nomeCampo", "loteDestino");
				}
			}
		}else{
			// Limpa o campo setorComercialOrigemCD do formulário
			form.setQuadraDestinoNM("");
			// Mensagem de tela
			form.setQuadraMensagemDestino("Informe o setor comercial da inscrição.");
			httpServletRequest.setAttribute("corQuadraDestino", "exception");
		}
	}

	private Collection validaArquivo(Fachada fachada, FiltrarImovelContaActionForm form, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		HttpSession sessao = httpServletRequest.getSession();
		final int IMOVEL = 0;
		final int REFERENCIA_INICIAL = 1;
		final int REFERENCIA_FINAL = 2;
		Collection<Object[]> retorno = new ArrayList();

		try{
			FormFile formFile = form.getArquivoMatricula();
			Scanner scanner = new Scanner(formFile.getInputStream());
			Collection<Imovel> colecaoImoveis = new ArrayList();
			Collection<String> devolucao = new ArrayList<String>();
			String matricula;
			Integer referenciaInicial;
			Integer referenciaFinal;
			Imovel imovel;
			Object item[] = null;

			if(formFile != null){
				String[] fileName = formFile.getFileName().split("\\.");
				String name = fileName[fileName.length - 1];
				if(!name.equalsIgnoreCase("csv")){
					throw new ActionServletException("atencao.arquivo.enviado.tem.que.ser.extensao", null, "CSV");
				}
			}

			while(scanner.hasNext()){
				String linha = scanner.nextLine();
				String[] itemLinha = linha.split(";");
				matricula = itemLinha[IMOVEL];
				String erro = "";

				try{

					if(itemLinha.length != 3){
						erro += " FORMATAÇÃO INCORRETA DA LINHA";
						devolucao.add(linha + ";" + erro);
					}else{

						if(Util.validarAnoMesSemBarra(itemLinha[REFERENCIA_INICIAL])){
							erro += " REFERENCIA INICIAL NÃO É VALIDA ";
						}
						referenciaInicial = Integer.valueOf(itemLinha[REFERENCIA_INICIAL]);
						if(Util.validarAnoMesSemBarra(itemLinha[REFERENCIA_FINAL])){
							if(!erro.equalsIgnoreCase("")){
								erro += ";";
							}
							erro += " REFERENCIA FINAL NÃO É VALIDA";
						}
						referenciaFinal = Integer.valueOf(itemLinha[REFERENCIA_FINAL]);
						if(referenciaInicial > referenciaFinal){
							if(!erro.equalsIgnoreCase("")){
								erro += ";";
							}
							erro += " REFERENCIA INICIAL MAIOR QUE A REFERENCIA FINAL";
						}

						imovel = fachada.pesquisarImovel(Integer.valueOf(matricula));
						if(imovel == null){
							if(!erro.equalsIgnoreCase("")){
								erro += ";";
							}
							erro += matricula + " MATRÍCULA INEXISTENTE";
						}else if(colecaoImoveis.contains(imovel)){
							// erro += matricula + " MATRICULA REPETIDA NO ARQUIVO";
							for(Object[] temp : retorno){
								if(temp[IMOVEL].equals(imovel)){
									if((((Integer) temp[REFERENCIA_FINAL]).intValue() >= referenciaInicial && ((Integer) temp[REFERENCIA_FINAL])
													.intValue() <= referenciaFinal)
													|| (((Integer) temp[REFERENCIA_INICIAL]).intValue() <= referenciaFinal && ((Integer) temp[REFERENCIA_INICIAL])
																	.intValue() >= referenciaInicial)){
										if(!erro.equalsIgnoreCase("")){
											erro += ";";
										}
										erro += " ESTE INTERVALO RETORNA CONTAS REPETIDAS ";
									}
								}
							}
						}else{
							Cliente cliente = fachada.pesquisarClienteUsuarioImovel(imovel.getId());

							// Verifica existência do cliente usuário
							if(cliente == null){
								if(!erro.equalsIgnoreCase("")){
									erro += ";";
								}
								erro += " CLIENTE USUARIO NÃO CADASTRADO";
							}
						}

						if(erro.equals("")){
							item = new Object[3];
							item[IMOVEL] = imovel;
							item[REFERENCIA_INICIAL] = referenciaInicial;
							item[REFERENCIA_FINAL] = referenciaFinal;
							if(!colecaoImoveis.contains(imovel)){
								colecaoImoveis.add(imovel);
							}
							retorno.add(item);
						}else{
							devolucao.add(linha + ";" + erro);
						}
					}

				}catch(NumberFormatException e){
					devolucao.add(linha + ";" + erro + ";" + " CARACTERES INVALIDOS");
				}
			}


			// Prepara arquivo com erros
			File arquivoErro = new File(formFile.getFileName());
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivoErro.getAbsolutePath())));
			if(devolucao.isEmpty()){
				form.setArquivoDownload("arquivo com " + retorno.size() + " linhas validado com sucesso. Foram encontrados "
								+ colecaoImoveis.size() + " imóveis válidos.");
				form.setArquivoMatricula(formFile);
			}else{
				form.setArquivoDownload("Foram encontradas " + devolucao.size()
								+ " falhas no arquivo. Clique aqui para baixar o arquivo com a descrição das falhas. ");
				form.setEnderecoArquivoDownload(arquivoErro.getPath());

				if(httpServletRequest.getParameter("download") != null && httpServletRequest.getParameter("download").equals("true")){
					for(String linha : devolucao){
						bufferedWriter.write(linha);
						bufferedWriter.newLine();
					}
					bufferedWriter.flush();
					bufferedWriter.close();
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					FileInputStream in = new FileInputStream(arquivoErro);
					int b;
					while((b = in.read()) > -1){
						byteArrayOutputStream.write(b);
					}
					// httpServletResponse.addHeader("Content-Disposition", "attachment; filename="
					// + arquivoErro.getName());
					// String mimeType = "application/txt";
					// httpServletResponse.setContentType(mimeType);
					ServletOutputStream out = httpServletResponse.getOutputStream();
					httpServletResponse.setContentType("application/txt");
					httpServletResponse.setContentLength(byteArrayOutputStream.toByteArray().length);
					httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + arquivoErro.getName());

					out.write(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.toByteArray().length);
					out.flush();
					out.close();
					// retorno = null;
					sessao.removeAttribute("arquivo");
					form.setEnderecoArquivoDownload(null);
				}
			}
			// if (idsImoveis.size() > 0) {
			// form.setIdsImoveis(idsImoveis);
			// } else {
			// form.setArquivoDownload("Nenhum imóvel foi encontrado no arquivo.");
			// }
		}catch(FileNotFoundException e){
			throw new ActionServletException("", httpServletRequest.getRequestURI(), "");
		}catch(IOException e){
			throw new ActionServletException("", httpServletRequest.getRequestURI(), "");
		}
		return retorno;
	}

	/**
	 * Carrega as grupos de faturamento selecionados.
	 * 
	 * @author Yara Souza
	 * @date 02/06/2014
	 * @param sessao
	 * @param fachada
	 */
	private void getColecaoFaturamentoGrupo(HttpSession sessao, Fachada fachada, FiltrarImovelContaActionForm form){

		Collection colecaoFaturamentoGrupo = null;



			// Filtra Faturamento Grupo
			FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
			filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.INDICADOR_USO,
							ConstantesSistema.SIM));
			filtroFaturamentoGrupo.setCampoOrderBy(FiltroSolicitacaoTipoEspecificacao.DESCRICAO);


			Collection colecaoRetorno = fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());

			if(colecaoRetorno != null && !colecaoRetorno.isEmpty()){

				if(colecaoFaturamentoGrupo == null){
					colecaoFaturamentoGrupo = colecaoRetorno;
				}else{
					colecaoFaturamentoGrupo.addAll(colecaoRetorno);
				}
			}


		if(colecaoFaturamentoGrupo != null && !colecaoFaturamentoGrupo.isEmpty()){
			sessao.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);
		}else{
			sessao.setAttribute("colecaoFaturamentoGrupo", new ArrayList());
		}
	}

	/**
	 * @param sessao
	 * @param fachada
	 * @param form
	 */

	private void adicionarLogradouro(HttpSession sessao, Fachada fachada, FiltrarImovelContaActionForm form){

		Collection<Logradouro> colecaoLogradouro = null;

		if(!Util.isVazioOuBranco(form.getIdLogradouro())){

			String codigoDigitadoLogradouroEnter = (String) form.getIdLogradouro();

			FiltroLogradouro filtroLogradouro = new FiltroLogradouro();

			filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("logradouroTipo");
			filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("logradouroTitulo");
			filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("municipio");

			filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, codigoDigitadoLogradouroEnter));

			Collection colLogradouro = fachada.pesquisar(filtroLogradouro, Logradouro.class.getName());

			Logradouro logradouro = (Logradouro) Util.retonarObjetoDeColecao(colLogradouro);

			colecaoLogradouro = (Collection) sessao.getAttribute("colecaoLogradouro");

			if(colecaoLogradouro != null && !colecaoLogradouro.isEmpty()){

				if(!colecaoLogradouro.contains(logradouro)){
					colecaoLogradouro.add(logradouro);
					form.setIdLogradouro("");
					form.setDescricaoLogradouro("");
				}

			}else{
				colecaoLogradouro = new ArrayList();
				colecaoLogradouro.add(logradouro);
			}

			sessao.setAttribute("colecaoLogradouro", colecaoLogradouro);

		}
	}

	/**
	 * @param sessao
	 * @param fachada
	 * @param form
	 */

	private void removerLogradouro(HttpSession sessao, HttpServletRequest httpServletRequest, FiltrarImovelContaActionForm form, String acao){

		Collection<Logradouro> colecaoLogradouro = (Collection) sessao.getAttribute("colecaoLogradouro");

		if(acao.equals("sim")){
			Collection<Logradouro> colecaoLograduroRemover = new ArrayList();

			if(httpServletRequest.getParameter("idLogradouroRemover") != null){

				if(!colecaoLogradouro.isEmpty()){
					Iterator it = colecaoLogradouro.iterator();
					while(it.hasNext()){
						Logradouro logradouro = (Logradouro) it.next();

						if(logradouro.getId().equals(
										Util.converterStringParaInteger(httpServletRequest.getParameter("idLogradouroRemover")))){
							colecaoLograduroRemover.add(logradouro);
						}
					}

					if(!colecaoLograduroRemover.isEmpty()){
						Iterator itt = colecaoLograduroRemover.iterator();
						while(itt.hasNext()){
							Logradouro logradouro = (Logradouro) itt.next();

							if(colecaoLogradouro.contains(logradouro)){
								colecaoLogradouro.remove(logradouro);
							}
						}

						form.setIdLogradouro("");
						form.setDescricaoLogradouro("");

						sessao.setAttribute("colecaoLogradouro", colecaoLogradouro);

					}

				}

			}

		}

		if(acao.equals("todos")){
			sessao.setAttribute("colecaoLogradouro", null);

		}

	}

	/**
	 * Pesquisar Municipio
	 * 
	 * @param filtroMunicipio
	 * @param idMunicipioFiltro
	 * @param codigoSetorComercial
	 * @param municipios
	 * @param filtrarImovelFiltrarActionForm
	 * @param fachada
	 * @param httpServletRequest
	 */
	public void pesquisarMunicipio(String idMunicipioFiltro,
 FiltrarImovelContaActionForm form,
					Fachada fachada, HttpServletRequest httpServletRequest){

		FiltroMunicipio filtroMunicipio = new FiltroMunicipio();

		filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, idMunicipioFiltro));
		filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection municipioEncontrado = null;

		// pesquisa
		municipioEncontrado = fachada.pesquisar(filtroMunicipio, Municipio.class.getName());

		if(municipioEncontrado != null && !municipioEncontrado.isEmpty()){
			// O municipio foi encontrado
			form.setIdMunicipioFiltro("" + ((Municipio) ((List) municipioEncontrado).get(0)).getId());
			form.setMunicipioFiltro(((Municipio) ((List) municipioEncontrado).get(0)).getNome());
			httpServletRequest.setAttribute("idMunicipioFiltroImovelNaoEncontrado", "true");

			httpServletRequest.setAttribute("nomeCampo", "idBairroFiltro");

		}else{
			form.setIdMunicipioFiltro("");
			httpServletRequest.setAttribute("idMunicipioFiltroImovelNaoEncontrado", "exception");
			form.setMunicipioFiltro("Município inexistente");

			httpServletRequest.setAttribute("nomeCampo", "idMunicipioFiltro");

		}
	}

	/**
	 * Pesquisar Bairro
	 * 
	 * @param filtroBairro
	 * @param idMunicipioFiltro
	 * @param idBairroFiltro
	 * @param bairros
	 * @param filtrarImovelFiltrarActionForm
	 * @param fachada
	 * @param httpServletRequest
	 */
	public void pesquisarBairro(String idMunicipioFiltro, String idBairroFiltro,
 FiltrarImovelContaActionForm form,
					Fachada fachada, HttpServletRequest httpServletRequest){

		FiltroBairro filtroBairro = new FiltroBairro();

		if(idMunicipioFiltro != null && !idMunicipioFiltro.trim().equals("") && Integer.parseInt(idMunicipioFiltro) > 0){

			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, idMunicipioFiltro));
		}

		filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.CODIGO, idBairroFiltro));
		filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection bairroEncontrado = fachada.pesquisar(filtroBairro, Bairro.class.getName());

		if(bairroEncontrado != null && !bairroEncontrado.isEmpty()){
			// O municipio foi encontrado
			form.setIdBairroFiltro("" + ((Bairro) ((List) bairroEncontrado).get(0)).getCodigo());
			form.setBairroFiltro(((Bairro) ((List) bairroEncontrado).get(0)).getNome());
			httpServletRequest.setAttribute("codigoBairroImovelNaoEncontrado", "true");

			httpServletRequest.setAttribute("nomeCampo", "idLogradouroFiltro");

		}else{
			form.setIdBairroFiltro("");
			httpServletRequest.setAttribute("codigoBairroImovelNaoEncontrado", "exception");
			form.setBairroFiltro("Bairro inexistente");

			httpServletRequest.setAttribute("nomeCampo", "idBairroFiltro");

		}

	}

}