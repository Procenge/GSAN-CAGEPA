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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.Equipe;
import gcom.atendimentopublico.ordemservico.FiltroEquipe;
import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.registroatendimento.FiltroRegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.endereco.FiltroLogradouro;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.geografico.*;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.cobranca.CobrancaDocumento;
import gcom.cobranca.FiltroDocumentoCobranca;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0450] Pesquisar Ordem Servico - Exibir
 * 
 * @author Leonardo Regis
 * @date 04/09/2006
 */
public class ExibirFiltrarOrdemServicoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("filtrarOrdemServico");

		HttpSession sessao = httpServletRequest.getSession(false);

		// Form
		FiltrarOrdemServicoActionForm filtrarOrdemServicoActionForm = (FiltrarOrdemServicoActionForm) actionForm;

		filtrarOrdemServicoActionForm.setIndicadorTipoServico((short) 0);


		// Colocado por Raphael Rossiter em 29/01/2007
		String menu = httpServletRequest.getParameter("menu");

		if(!Util.isVazioOuBranco(menu)){

			// Sugerindo um período para realização da filtragem de uma OS
			// SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
			Integer qtdDias = 30;

			Date dataAtual = new Date();
			Date dataSugestao = Util.subtrairNumeroDiasDeUmaData(dataAtual, qtdDias);

			filtrarOrdemServicoActionForm.setPeriodoGeracaoInicial(Util.formatarData(dataSugestao));
			filtrarOrdemServicoActionForm.setPeriodoGeracaoFinal(Util.formatarData(dataAtual));

		}

		// Flag indicando que o usuário fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		// [UC0443] - Pesquisar Registro Atendimento
		if(!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("1")){

			// Faz a consulta de Registro Atendimento
			this.pesquisarRegistroAtendimento(filtrarOrdemServicoActionForm);
		}

		// [UC9999] - Pesquisar Documento de Cobrança
		if(!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("2")){

			// Faz a consulta de Documento Cobrança
			this.pesquisarDocumentoCobranca(filtrarOrdemServicoActionForm);
		}

		// [UC0013] - Pesquisar Imovel
		if(!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("3")){

			// Faz a consulta de Imovel
			this.pesquisarImovel(filtrarOrdemServicoActionForm);
		}

		// [UC0012] - Pesquisar Cliente
		if(!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("4")){

			// Faz a consulta de Cliente
			this.pesquisarCliente(filtrarOrdemServicoActionForm);
		}

		// [UC0376 - Pesquisar Unidade
		// if((!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("5"))
		// || (!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("6"))
		// || (!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("7"))){
		//
		// // Faz a consulta de Cliente
		// this.pesquisarUnidadeOrganizacional(filtrarOrdemServicoActionForm, objetoConsulta);
		// }

		if(!Util.isVazioOuBranco(objetoConsulta)
						&& (objetoConsulta.trim().equals("5") || objetoConsulta.trim().equals("6") || objetoConsulta.trim().equals("7"))){

			// Faz a consulta de Cliente
			this.pesquisarUnidadeOrganizacional(filtrarOrdemServicoActionForm, objetoConsulta);
		}

		// [UC0075] - Pesquisar Municipio
		if(!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("8")){

			// Faz a consulta de Municipio
			this.pesquisarMunicipio(filtrarOrdemServicoActionForm);
		}

		// [UC0141] - Pesquisar Bairro
		if(!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("9")){

			// Faz a consulta de Bairro
			this.pesquisarBairro(filtrarOrdemServicoActionForm, httpServletRequest);
		}

		// [UC0004] - Pesquisar Logradouro
		if(!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("10")){

			// Faz a consulta de logradouro
			this.pesquisarLogradouro(filtrarOrdemServicoActionForm);
		}

		if(!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("11")){

			// Faz a consulta de equipe
			this.pesquisarEquipe(filtrarOrdemServicoActionForm);
		}

		if(httpServletRequest.getParameter("tipoConsulta") != null && !httpServletRequest.getParameter("tipoConsulta").equals("")){

			String id = httpServletRequest.getParameter("idCampoEnviarDados");
			String descricao = httpServletRequest.getParameter("descricaoCampoEnviarDados");

			if(httpServletRequest.getParameter("tipoConsulta").equals("registroAtendimento")){

				filtrarOrdemServicoActionForm.setNumeroRA(id);
				filtrarOrdemServicoActionForm.setDescricaoRA(descricao);

			}else if(httpServletRequest.getParameter("tipoConsulta").equals("documentoCobranca")){

				filtrarOrdemServicoActionForm.setDocumentoCobranca(id);
				filtrarOrdemServicoActionForm.setDescricaoDocumentoCobranca(descricao);

			}else if(httpServletRequest.getParameter("tipoConsulta").equals("imovel")){

				filtrarOrdemServicoActionForm.setMatriculaImovel(id);
				filtrarOrdemServicoActionForm.setInscricaoImovel(descricao);

			}else if(httpServletRequest.getParameter("tipoConsulta").equals("cliente")){

				filtrarOrdemServicoActionForm.setCodigoCliente(id);
				filtrarOrdemServicoActionForm.setNomeCliente(descricao);

			}else if(httpServletRequest.getParameter("tipoConsulta").equals("unidadeOrganizacional")){

				if(sessao.getAttribute("tipoUnidade").equals("unidadeGeracao")){
					filtrarOrdemServicoActionForm.setUnidadeGeracao(id);
					filtrarOrdemServicoActionForm.setDescricaoUnidadeGeracao(descricao);

				}else if(sessao.getAttribute("tipoUnidade").equals("unidadeAtual")){
					filtrarOrdemServicoActionForm.setUnidadeAtual(id);
					filtrarOrdemServicoActionForm.setDescricaoUnidadeAtual(descricao);

				}else{
					filtrarOrdemServicoActionForm.setUnidadeSuperior(id);
					filtrarOrdemServicoActionForm.setDescricaoUnidadeSuperior(descricao);
				}

			}else if(httpServletRequest.getParameter("tipoConsulta").equals("municipio")){

				filtrarOrdemServicoActionForm.setMunicipio(id);
				filtrarOrdemServicoActionForm.setDescricaoMunicipio(descricao);

			}else if(httpServletRequest.getParameter("tipoConsulta").equals("bairro")){

				filtrarOrdemServicoActionForm.setCodigoBairro(id);
				filtrarOrdemServicoActionForm.setDescricaoBairro(descricao);

			}else if(httpServletRequest.getParameter("tipoConsulta").equals("logradouro")){

				filtrarOrdemServicoActionForm.setLogradouro(id);
				filtrarOrdemServicoActionForm.setDescricaoLogradouro(descricao);

			}
		}

		String indicadorTipoServico = httpServletRequest.getParameter("indicadorTipoServico");
		if(indicadorTipoServico == null){
			indicadorTipoServico = "0";
		}
		// Monta a colecao de tipos Servicos
		if(indicadorTipoServico.equals("0") && sessao.getAttribute("colecaoTipoServicoSelecionados") == null){
			this.pesquisarTipoServico(httpServletRequest);
		}

		// Monta a colecao de tipos Servicos selecionados
		if(indicadorTipoServico.equals("1")){
			this.pesquisarTipoServicoSelecionados(filtrarOrdemServicoActionForm, httpServletRequest);
		}

		if(sessao.getAttribute("colecaoTipoServicoSelecionados") != null){
			this.pesquisarTipoServicoSelecionados(filtrarOrdemServicoActionForm, httpServletRequest);
		}

		if(indicadorTipoServico.equals("2")){
			this.pesquisarTipoServico(httpServletRequest);
			httpServletRequest.setAttribute("colecaoTipoServicoSelecionados", null);
			sessao.setAttribute("colecaoTipoServicoSelecionados", null);
		}

		// Seta os request´s encontrados
		this.setaRequest(httpServletRequest, filtrarOrdemServicoActionForm);

		if(httpServletRequest.getParameter("caminhoRetornoTelaPesquisaOrdemServico") != null){

			sessao.setAttribute("caminhoRetornoTelaPesquisaOrdemServico", httpServletRequest
							.getParameter("caminhoRetornoTelaPesquisaOrdemServico"));

		}

		if(Util.isVazioOuBranco(filtrarOrdemServicoActionForm.getSituacaoProgramacao())){
			filtrarOrdemServicoActionForm.setSituacaoProgramacao(ConstantesSistema.SET_ZERO.toString());
		}

		return retorno;
	}

	/**
	 * Pesquisa Imóvel
	 * 
	 * @author Rafael Pinto
	 * @date 15/08/2006
	 */
	private void pesquisarImovel(FiltrarOrdemServicoActionForm form){

		// Filtra Imovel
		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, form.getMatriculaImovel()));

		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");

		// Recupera Imóvel
		Collection colecaoImovel = Fachada.getInstancia().pesquisar(filtroImovel, Imovel.class.getName());

		if(colecaoImovel != null && !colecaoImovel.isEmpty()){

			Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);

			form.setMatriculaImovel(imovel.getId().toString());
			form.setInscricaoImovel(imovel.getInscricaoFormatada());

		}else{
			form.setMatriculaImovel("");
			form.setInscricaoImovel("Matrícula inexistente");
		}
	}

	/**
	 * Pesquisa Registro Atendimento
	 * 
	 * @author Rafael Pinto
	 * @date 15/08/2006
	 */
	private void pesquisarRegistroAtendimento(FiltrarOrdemServicoActionForm form){

		// Filtro para obter o localidade ativo de id informado
		FiltroRegistroAtendimento filtroRegistroAtendimento = new FiltroRegistroAtendimento();

		if(!Util.isVazioOuBranco(form.getNumeroRA())){
			filtroRegistroAtendimento
							.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.ID, new Integer(form.getNumeroRA())));
		}else{
			filtroRegistroAtendimento.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.ID, new Integer(form
							.getIdRaReiteracao())));
		}
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("solicitacaoTipoEspecificacao");

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoRegistros = Fachada.getInstancia().pesquisar(filtroRegistroAtendimento, RegistroAtendimento.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoRegistros != null && !colecaoRegistros.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			RegistroAtendimento registroAtendimento = (RegistroAtendimento) Util.retonarObjetoDeColecao(colecaoRegistros);

			form.setNumeroRA(registroAtendimento.getId().toString());
			form.setDescricaoRA(registroAtendimento.getSolicitacaoTipoEspecificacao().getDescricao());

		}else{

			form.setDescricaoRA("RA - Registro de Atendimento inexistente");
			form.setNumeroRA("");

		}
	}

	/**
	 * Pesquisa Documento Cobrança
	 * 
	 * @author Rafael Pinto
	 * @date 21/08/2006
	 */
	private void pesquisarDocumentoCobranca(FiltrarOrdemServicoActionForm form){

		FiltroDocumentoCobranca filtroDocumentoCobranca = new FiltroDocumentoCobranca();

		filtroDocumentoCobranca.adicionarParametro(new ParametroSimples(FiltroDocumentoCobranca.ID,
						new Integer(form.getDocumentoCobranca())));

		filtroDocumentoCobranca.adicionarCaminhoParaCarregamentoEntidade("documentoTipo");

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoDocumentoCobranca = Fachada.getInstancia().pesquisar(filtroDocumentoCobranca, CobrancaDocumento.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoDocumentoCobranca != null && !colecaoDocumentoCobranca.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			CobrancaDocumento cobrancaDocumento = (CobrancaDocumento) Util.retonarObjetoDeColecao(colecaoDocumentoCobranca);

			form.setDocumentoCobranca(cobrancaDocumento.getId().toString());
			form.setDescricaoDocumentoCobranca(cobrancaDocumento.getDocumentoTipo().getDescricaoDocumentoTipo());

		}else{
			form.setDocumentoCobranca("");
			form.setDescricaoDocumentoCobranca("Documento Cobrança inexistente");
		}
	}

	/**
	 * Pesquisa Cliente
	 * 
	 * @author Rafael Pinto
	 * @date 15/08/2006
	 */
	private void pesquisarCliente(FiltrarOrdemServicoActionForm form){

		FiltroCliente filtroCliente = new FiltroCliente();

		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, new Integer(form.getCodigoCliente())));

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoCliente = Fachada.getInstancia().pesquisar(filtroCliente, Cliente.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoCliente != null && !colecaoCliente.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);

			form.setCodigoCliente(cliente.getId().toString());
			form.setNomeCliente(cliente.getNome());

		}else{
			form.setCodigoCliente("");
			form.setNomeCliente("Cliente inexistente");
		}
	}

	/**
	 * Pesquisa Unidade Organizacional
	 * 
	 * @author Rafael Pinto
	 * @date 15/08/2006
	 */
	private void pesquisarUnidadeOrganizacional(FiltrarOrdemServicoActionForm form, String objetoConsulta){

		FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

		Integer idUnidade = null;

		if(objetoConsulta.equals("5")){
			idUnidade = new Integer(form.getUnidadeGeracao());
		}else if(objetoConsulta.equals("6")){
			idUnidade = new Integer(form.getUnidadeAtual());
		}else{
			idUnidade = new Integer(form.getUnidadeSuperior());

		}

		filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidade));

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoUnidade = Fachada.getInstancia().pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoUnidade != null && !colecaoUnidade.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidade);

			if(objetoConsulta.equals("5")){

				form.setUnidadeGeracao(unidadeOrganizacional.getId().toString());
				form.setDescricaoUnidadeGeracao(unidadeOrganizacional.getDescricao());

			}else if(objetoConsulta.equals("6")){

				form.setUnidadeAtual(unidadeOrganizacional.getId().toString());
				form.setDescricaoUnidadeAtual(unidadeOrganizacional.getDescricao());

			}else{

				// [FS0009] - Verificar existência de unidades subordinadas
				filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

				filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID_UNIDADE_SUPERIOR,
								idUnidade));

				colecaoUnidade = Fachada.getInstancia().pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

				// Verifica se a pesquisa retornou algum objeto para a coleção
				if(colecaoUnidade != null && !colecaoUnidade.isEmpty()){
					form.setUnidadeSuperior(unidadeOrganizacional.getId().toString());
					form.setDescricaoUnidadeSuperior(unidadeOrganizacional.getDescricao());
				}else{
					throw new ActionServletException("atencao.filtrar_ra_sem_unidades_subordinadas");
				}

			}

		}else{
			if(objetoConsulta.equals("5")){

				form.setUnidadeGeracao("");
				form.setDescricaoUnidadeGeracao("Unidade de Geração inexistente");

			}else if(objetoConsulta.equals("6")){

				form.setUnidadeAtual("");
				form.setDescricaoUnidadeAtual("Unidade Atual inexistente");

			}else{

				form.setUnidadeSuperior("");
				form.setDescricaoUnidadeSuperior("Unidade Superior inexistente");

			}
		}
	}

	/**
	 * Pesquisa Municipio
	 * 
	 * @author Rafael Pinto
	 * @date 16/08/2006
	 */
	private void pesquisarMunicipio(FiltrarOrdemServicoActionForm form){

		FiltroMunicipio filtroMunicipio = new FiltroMunicipio();

		filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, new Integer(form.getMunicipio())));

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoMunicipio = Fachada.getInstancia().pesquisar(filtroMunicipio, Municipio.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoMunicipio != null && !colecaoMunicipio.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			Municipio municipio = (Municipio) Util.retonarObjetoDeColecao(colecaoMunicipio);

			form.setMunicipio(municipio.getId().toString());
			form.setDescricaoMunicipio(municipio.getNome());

		}else{
			form.setMunicipio("");
			form.setDescricaoMunicipio("Município inexistente");
		}
	}

	/**
	 * Pesquisa Bairro
	 * 
	 * @author Rafael Pinto
	 * @date 16/08/2006
	 */
	private void pesquisarBairro(FiltrarOrdemServicoActionForm form, HttpServletRequest httpServletRequest){

		// [FS0013] - Verificar informação do munícipio
		String codigoMunicipio = form.getMunicipio();
		if(codigoMunicipio == null || codigoMunicipio.equals("")){
			throw new ActionServletException("atencao.filtrar_informar_municipio");
		}

		FiltroBairro filtroBairro = new FiltroBairro();

		filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.CODIGO, new Integer(form.getCodigoBairro())));

		filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, new Integer(codigoMunicipio)));

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoBairro = Fachada.getInstancia().pesquisar(filtroBairro, Bairro.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoBairro != null && !colecaoBairro.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			Bairro bairro = (Bairro) Util.retonarObjetoDeColecao(colecaoBairro);

			this.montarAreaBairroPorId(httpServletRequest, new Integer(bairro.getId()));

			form.setCodigoBairro("" + bairro.getCodigo());
			form.setIdBairro("" + bairro.getId());
			form.setDescricaoBairro(bairro.getNome());

		}else{
			form.setCodigoBairro(null);
			form.setDescricaoBairro("Bairro inexistente");
		}
	}

	/**
	 * Pesquisa Logradouro
	 * 
	 * @author Rafael Pinto
	 * @date 16/08/2006
	 */
	private void pesquisarLogradouro(FiltrarOrdemServicoActionForm form){

		FiltroLogradouro filtroLogradouro = new FiltroLogradouro();

		filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, new Integer(form.getLogradouro())));

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoLogradouro = Fachada.getInstancia().pesquisar(filtroLogradouro, Logradouro.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoLogradouro != null && !colecaoLogradouro.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			Logradouro logradouro = (Logradouro) Util.retonarObjetoDeColecao(colecaoLogradouro);

			form.setLogradouro(logradouro.getId().toString());
			form.setDescricaoLogradouro(logradouro.getNome());

		}else{
			form.setLogradouro("");
			form.setDescricaoLogradouro("Logradouro inexistente");
		}
	}

	/**
	 * Pesquisa Equipe
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 30/03/2011
	 */
	private void pesquisarEquipe(FiltrarOrdemServicoActionForm form){

		FiltroEquipe filtroEquipe = new FiltroEquipe();

		filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.ID, new Integer(form.getEquipe())));

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoEquipe = Fachada.getInstancia().pesquisar(filtroEquipe, Equipe.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoEquipe != null && !colecaoEquipe.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			Equipe equipe = (Equipe) Util.retonarObjetoDeColecao(colecaoEquipe);

			form.setEquipe(equipe.getId().toString());
			form.setDescricaoEquipe(equipe.getNome());

		}else{
			form.setEquipe("");
			form.setDescricaoEquipe("Equipe inexistente");
		}
	}

	/**
	 * Pesquisa Area do Bairro pelo Id
	 * 
	 * @author Rafael Pinto
	 * @date 16/08/2006
	 */
	private void montarAreaBairroPorId(HttpServletRequest request, Integer id){

		// Parte que passa as coleções necessárias no jsp
		Collection colecaoAreaBairro = new ArrayList();

		FiltroBairroArea filtroBairroArea = new FiltroBairroArea();
		filtroBairroArea.adicionarParametro(new ParametroSimples(FiltroBairroArea.ID_BAIRRO, id));

		colecaoAreaBairro = Fachada.getInstancia().pesquisar(filtroBairroArea, BairroArea.class.getName());

		if(colecaoAreaBairro != null && !colecaoAreaBairro.isEmpty()){
			request.setAttribute("colecaoAreaBairro", colecaoAreaBairro);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Área do Bairro");
		}

	}

	/**
	 * Pesquisa Tipo Servico
	 * 
	 * @author Rafael Pinto
	 * @date 17/08/2006
	 */
	private void pesquisarTipoServico(HttpServletRequest httpServletRequest){

		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();

		filtroServicoTipo.setConsultaSemLimites(true);
		filtroServicoTipo.setCampoOrderBy(FiltroServicoTipo.DESCRICAO);

		Collection colecaoTipoServico = Fachada.getInstancia().pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

		BeanComparator comparador = new BeanComparator("descricao");
		Collections.sort((List) colecaoTipoServico, comparador);

		Collection colecaoTipoServicoSelecionados = null;

		if(colecaoTipoServico == null || colecaoTipoServico.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Tipo de Serviço");
		}else{
			httpServletRequest.setAttribute("colecaoTipoServico", colecaoTipoServico);
			httpServletRequest.setAttribute("colecaoTipoServicoSelecionados", colecaoTipoServicoSelecionados);
		}
	}

	/**
	 * Pesquisa Tipo Servico
	 * 
	 * @author Eduardo Oliveira
	 * @date 26/03/2014
	 */
	private void pesquisarTipoServicoSelecionados(FiltrarOrdemServicoActionForm form, HttpServletRequest httpServletRequest){

		HttpSession sessao = httpServletRequest.getSession(false);

		if(form.getTipoServicoSelecionados() != null){
			Integer[] idsTiposServicosSelecionados = form.getTipoServicoSelecionados();

			Collection colecaoTipoServicoSelecionados = new ArrayList<ServicoTipo>();
			List<Integer> intList = new ArrayList<Integer>();

			FiltroServicoTipo filtroServicoTipo1 = new FiltroServicoTipo();
			filtroServicoTipo1.setConsultaSemLimites(true);

			Collection colecaoServicoTipoDisponivel = Fachada.getInstancia().pesquisar(filtroServicoTipo1, ServicoTipo.class.getName());
			for(int index = 0; index < idsTiposServicosSelecionados.length; index++){
				intList.add(idsTiposServicosSelecionados[index]);

				FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
				filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, idsTiposServicosSelecionados[index]));

				Collection colecaoServicoTipoId = Fachada.getInstancia().pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

				ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(colecaoServicoTipoId);

				colecaoTipoServicoSelecionados.add(servicoTipo);

				BeanComparator comparador = new BeanComparator("descricao");
				Collections.sort((List) colecaoTipoServicoSelecionados, comparador);

				if(colecaoServicoTipoId != null && !colecaoServicoTipoId.isEmpty()){

					Iterator colecaoServicoTipoDisponivelIterator = colecaoServicoTipoId.iterator();

					while(colecaoServicoTipoDisponivelIterator.hasNext()){
						ServicoTipo servicoTipoSelecionado = ((ServicoTipo) colecaoServicoTipoDisponivelIterator.next());

						if(colecaoServicoTipoDisponivel.contains(servicoTipoSelecionado)){
							colecaoServicoTipoDisponivel.remove(servicoTipoSelecionado);
						}

						BeanComparator comparadorcolecaoServicoTipoDisponivel = new BeanComparator("descricao");
						Collections.sort((List) colecaoServicoTipoDisponivel, comparadorcolecaoServicoTipoDisponivel);

						httpServletRequest.setAttribute("colecaoServicoTipoDisponivel", colecaoServicoTipoDisponivel);
					}

					if(colecaoTipoServicoSelecionados == null || colecaoTipoServicoSelecionados.isEmpty()){
						throw new ActionServletException("atencao.naocadastrado", null, "Tipo de Serviço");
					}else{
						httpServletRequest.setAttribute("colecaoTipoServicoSelecionados", colecaoTipoServicoSelecionados);
						sessao.setAttribute("colecaoTipoServicoSelecionados", colecaoTipoServicoSelecionados);
					}
				}
			}
		}
	}


	/**
	 * Seta os request com os id encontrados
	 * 
	 * @author Rafael Pinto
	 * @date 16/08/2006
	 */
	private void setaRequest(HttpServletRequest httpServletRequest, FiltrarOrdemServicoActionForm form){

		// Imovel
		if(!Util.isVazioOuBranco(form.getMatriculaImovel()) && !Util.isVazioOuBranco(form.getInscricaoImovel())){
			httpServletRequest.setAttribute("matriculaImovelEncontrada", "true");
		}

		// Registro Atendimento
		if(!Util.isVazioOuBranco(form.getNumeroRA()) && !Util.isVazioOuBranco(form.getDescricaoRA())){
			httpServletRequest.setAttribute("numeroRAEncontrada", "true");
		}

		// Documento Cobrança
		if(!Util.isVazioOuBranco(form.getDocumentoCobranca()) && !Util.isVazioOuBranco(form.getDescricaoDocumentoCobranca())){
			httpServletRequest.setAttribute("documentoCobrancaEncontrada", "true");
		}

		// Codigo Cliente
		if(!Util.isVazioOuBranco(form.getCodigoCliente()) && !Util.isVazioOuBranco(form.getNomeCliente())){
			httpServletRequest.setAttribute("codigoClienteEncontrada", "true");
		}

		// Unidade Geração
		if(!Util.isVazioOuBranco(form.getUnidadeGeracao()) && !Util.isVazioOuBranco(form.getDescricaoUnidadeGeracao())){
			httpServletRequest.setAttribute("unidadeGeracaoEncontrada", "true");
		}

		// Unidade Atual
		if(!Util.isVazioOuBranco(form.getUnidadeAtual()) && !Util.isVazioOuBranco(form.getDescricaoUnidadeAtual())){
			httpServletRequest.setAttribute("unidadeAtualEncontrada", "true");
		}

		// Unidade Superior
		if(!Util.isVazioOuBranco(form.getUnidadeSuperior()) && !Util.isVazioOuBranco(form.getDescricaoUnidadeSuperior())){
			httpServletRequest.setAttribute("unidadeSuperiorEncontrada", "true");
		}

		// Equipe
		if(!Util.isVazioOuBranco(form.getEquipe()) && !Util.isVazioOuBranco(form.getDescricaoEquipe())){
			httpServletRequest.setAttribute("equipeEncontrada", "true");
		}

		// Municipio
		if(!Util.isVazioOuBranco(form.getMunicipio()) && !Util.isVazioOuBranco(form.getDescricaoMunicipio())){
			httpServletRequest.setAttribute("municipioEncontrada", "true");
		}

		// Bairro
		if(!Util.isVazioOuBranco(form.getCodigoBairro()) && !Util.isVazioOuBranco(form.getDescricaoBairro())){
			httpServletRequest.setAttribute("bairroEncontrada", "true");
		}

		// Logradouro
		if(!Util.isVazioOuBranco(form.getLogradouro()) && !Util.isVazioOuBranco(form.getDescricaoLogradouro())){
			httpServletRequest.setAttribute("logradouroEncontrado", "true");
		}

	}

}