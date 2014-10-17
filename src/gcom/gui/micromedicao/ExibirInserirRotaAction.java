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

package gcom.gui.micromedicao;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaCriterio;
import gcom.cobranca.CobrancaGrupo;
import gcom.cobranca.FiltroCobrancaAcao;
import gcom.cobranca.FiltroCobrancaCriterio;
import gcom.cobranca.FiltroCobrancaGrupo;
import gcom.cobranca.RotaAcaoCriterio;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroLeiturista;
import gcom.micromedicao.Leiturista;
import gcom.micromedicao.leitura.FiltroLeituraTipo;
import gcom.micromedicao.leitura.LeituraTipo;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroNaoNulo;
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
 * Action que define o pré-processamento da página de inserir rota
 * 
 * @author Vivianne Sousa
 * @created 21/03/2006
 * @author eduardo henrique
 * @date 25/08/2008
 *       Alteração para obrigatoriedade do Leiturista/Agente Comercial no cadastro da Rota
 */
public class ExibirInserirRotaAction
				extends GcomAction {

	/**
	 * Este caso de uso permite a inclusão de uma nova rota
	 * [UC0038] Inserir Rota
	 * 
	 * @author Vivianne Sousa
	 * @date 21/03/2006
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirRota");
		InserirRotaActionForm inserirRotaActionForm = (InserirRotaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Pesquisar Max Rota
		String pesquisarMaxRota = httpServletRequest.getParameter("pesquisarMaxRota");

		if(pesquisarMaxRota != null && !pesquisarMaxRota.equals("")){

			String idGrupoFaturamento = inserirRotaActionForm.getFaturamentoGrupo();

			// Retorna o maior código de Rota existente para o grupo de faturamento pesquisado
			int codigo = fachada.pesquisarMaximoCodigoRotaGrupoFaturamento(new Integer(idGrupoFaturamento));

			// Acrescenta 1 no valor do código para setar o primeiro código vazio para o usuário
			codigo = (codigo + 1);

			inserirRotaActionForm.setCodigoRota("" + codigo);

			httpServletRequest.setAttribute("nomeCampo", "codigoRota");

		}

		// Pesquisando grupo de cobrança
		FiltroCobrancaGrupo filtroCobrancaGrupo = new FiltroCobrancaGrupo();
		filtroCobrancaGrupo.setCampoOrderBy(FiltroCobrancaGrupo.DESCRICAO_ABREVIADA);
		Collection<CobrancaGrupo> collectionCobrancaGrupo = fachada.pesquisar(filtroCobrancaGrupo, CobrancaGrupo.class.getName());
		// httpServletRequest.setAttribute("collectionCobrancaGrupo", collectionCobrancaGrupo);
		sessao.setAttribute("collectionCobrancaGrupo", collectionCobrancaGrupo);
		// Fim de pesquisando grupo de cobrança

		// Pesquisando grupo de faturamento
		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
		filtroFaturamentoGrupo.setCampoOrderBy(FiltroFaturamentoGrupo.DESCRICAO_ABREVIADA);
		Collection<FaturamentoGrupo> collectionFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class
						.getName());
		// httpServletRequest.setAttribute("collectionFaturamentoGrupo",
		// collectionFaturamentoGrupo);
		sessao.setAttribute("collectionFaturamentoGrupo", collectionFaturamentoGrupo);
		// Fim de pesquisando grupo de faturamento

		// Pesquisando tipo de leitura
		FiltroLeituraTipo filtroLeituraTipo = new FiltroLeituraTipo();

		filtroLeituraTipo.adicionarParametro(new ParametroSimples(FiltroLeituraTipo.INDICADOR_USO, new Short(
						ConstantesSistema.INDICADOR_USO_ATIVO)));
		filtroLeituraTipo.setCampoOrderBy(FiltroLeituraTipo.DESCRICAO);
		Collection<LeituraTipo> collectionLeituraTipo = fachada.pesquisar(filtroLeituraTipo, LeituraTipo.class.getName());
		// httpServletRequest.setAttribute("collectionLeituraTipo", collectionLeituraTipo);
		sessao.setAttribute("collectionLeituraTipo", collectionLeituraTipo);
		// Fim de pesquisando tipo de leitura

		// Pesquisando empresa leiturística
		FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
		filtroEmpresa.setCampoOrderBy(FiltroEmpresa.DESCRICAO);
		Collection<Empresa> collectionEmpresa = fachada.pesquisar(filtroEmpresa, Empresa.class.getName());
		// httpServletRequest.setAttribute("collectionEmpresa", collectionEmpresa);
		sessao.setAttribute("collectionEmpresa", collectionEmpresa);
		// Fim de pesquisando empresa leiturística

		if(httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer").equalsIgnoreCase("S")){

			// -------------- bt DESFAZER ---------------

			inserirRotaActionForm.setIdLocalidade("");
			inserirRotaActionForm.setLocalidadeDescricao("");
			inserirRotaActionForm.setCodigoSetorComercial("");
			inserirRotaActionForm.setSetorComercialDescricao("");
			inserirRotaActionForm.setCodigoRota("");
			inserirRotaActionForm.setCobrancaGrupo("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			inserirRotaActionForm.setFaturamentoGrupo("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			inserirRotaActionForm.setLeituraTipo("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			inserirRotaActionForm.setEmpresaLeituristica("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			inserirRotaActionForm.setCobrancaCriterio("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			// inserirRotaActionForm.setDataAjusteLeitura("");
			// inserirRotaActionForm.setIndicadorAjusteConsumo("" +
			// ConstantesSistema.NUMERO_NAO_INFORMADO);
			inserirRotaActionForm.setIndicadorFiscalizarCortado("");
			inserirRotaActionForm.setIndicadorFiscalizarSuprimido("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			inserirRotaActionForm.setIndicadorGerarFalsaFaixa("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			inserirRotaActionForm.setPercentualGeracaoFaixaFalsa("");
			inserirRotaActionForm.setIndicadorGerarFiscalizacao("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			inserirRotaActionForm.setPercentualGeracaoFiscalizacao("");
			inserirRotaActionForm.setCodigoLeiturista("");
			inserirRotaActionForm.setNomeLeiturista("");

		}

		// -------Parte que trata do código quando o usuário tecla enter
		String idDigitadoEnterLocalidade = inserirRotaActionForm.getIdLocalidade();
		if(idDigitadoEnterLocalidade != null && !idDigitadoEnterLocalidade.equalsIgnoreCase("")
						&& Util.validarValorNaoNumerico(idDigitadoEnterLocalidade)){
			// Localidade não numérico.
			httpServletRequest.setAttribute("nomeCampo", "idLocalidade");
			throw new ActionServletException("atencao.nao.numerico", null, "Localidade ");
		}
		verificaExistenciaCodLocalidade(idDigitadoEnterLocalidade, inserirRotaActionForm, httpServletRequest, fachada);

		String idDigitadoEnterSetorComercial = inserirRotaActionForm.getCodigoSetorComercial();
		if(idDigitadoEnterSetorComercial != null && !idDigitadoEnterSetorComercial.equalsIgnoreCase("")
						&& Util.validarValorNaoNumerico(idDigitadoEnterSetorComercial)){
			// Setor Comercial não numérico.
			httpServletRequest.setAttribute("nomeCampo", "codigoSetorComercial");
			throw new ActionServletException("atencao.nao.numerico", null, "Setor Comercial ");
		}
		verificaExistenciaCodSetorComercial(idDigitadoEnterLocalidade, idDigitadoEnterSetorComercial, inserirRotaActionForm,
						httpServletRequest, fachada);

		String idDigitadoEnterLeiturista = inserirRotaActionForm.getCodigoLeiturista();
		if(idDigitadoEnterLeiturista != null && !idDigitadoEnterLeiturista.equalsIgnoreCase("")
						&& Util.validarValorNaoNumerico(idDigitadoEnterLeiturista)){
			// Setor Comercial não numérico.
			httpServletRequest.setAttribute("nomeCampo", "codigoLeiturista");
			throw new ActionServletException("atencao.nao.numerico", null, "Agente Comercial ");
		}
		verificarExistenciaCodLeiturista(inserirRotaActionForm.getEmpresaLeituristica(), idDigitadoEnterLeiturista, inserirRotaActionForm,
						httpServletRequest, fachada);

		// -------Fim de parte que trata do código quando o usuário tecla enter

		sessao.removeAttribute("caminhoRetornoTelaPesquisa");
		// DEFINIÇÃO QUE IRÁ AUXILIAR O RETORNO DOS POPUPS
		sessao.setAttribute("UseCase", "INSERIRROTA");

		return retorno;
	}

	private void verificaExistenciaCodLocalidade(String idDigitadoEnterLocalidade, InserirRotaActionForm inserirRotaActionForm,
					HttpServletRequest httpServletRequest, Fachada fachada){

		// Verifica se o código da Localidade foi digitado
		if(idDigitadoEnterLocalidade != null && !idDigitadoEnterLocalidade.trim().equals("")
						&& Integer.parseInt(idDigitadoEnterLocalidade) > 0){

			// Recupera a localidade informada pelo usuário
			Localidade localidadeEncontrada = fachada.pesquisarLocalidadeDigitada(new Integer(idDigitadoEnterLocalidade));

			// Caso a localidade informada pelo usuário esteja cadastrada no sistema
			// Seta os dados da localidade no form
			// Caso contrário seta as informações da localidade para vazio
			// e indica ao usuário que a localidade não existe

			if(localidadeEncontrada != null){
				// a localidade foi encontrada
				inserirRotaActionForm.setIdLocalidade("" + (localidadeEncontrada.getId()));
				inserirRotaActionForm.setLocalidadeDescricao(localidadeEncontrada.getDescricao());
				httpServletRequest.setAttribute("idLocalidadeNaoEncontrada", "true");
				httpServletRequest.setAttribute("nomeCampo", "codigoSetorComercial");

			}else{
				// a localidade não foi encontrada
				inserirRotaActionForm.setIdLocalidade("");
				httpServletRequest.setAttribute("idLocalidadeNaoEncontrada", "exception");
				inserirRotaActionForm.setLocalidadeDescricao("LOCALIDADE INEXISTENTE");

			}
		}

	}

	private void verificaExistenciaCodSetorComercial(String idDigitadoEnterLocalidade, String idDigitadoEnterSetorComercial,
					InserirRotaActionForm inserirRotaActionForm, HttpServletRequest httpServletRequest, Fachada fachada){

		// Verifica se o código do Setor Comercial foi digitado
		if((idDigitadoEnterSetorComercial != null && !idDigitadoEnterSetorComercial.toString().trim().equalsIgnoreCase(""))
						&& (idDigitadoEnterLocalidade != null && !idDigitadoEnterLocalidade.toString().trim().equalsIgnoreCase(""))){

			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			if(idDigitadoEnterLocalidade != null && !idDigitadoEnterLocalidade.toString().trim().equalsIgnoreCase("")){

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, new Integer(
								idDigitadoEnterLocalidade)));
			}

			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, new Integer(
							idDigitadoEnterSetorComercial)));

			Collection<SetorComercial> setorComerciais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(setorComerciais != null && !setorComerciais.isEmpty()){
				// o setor comercial foi encontrado
				SetorComercial setorComercialEncontrado = (SetorComercial) Util.retonarObjetoDeColecao(setorComerciais);
				inserirRotaActionForm.setCodigoSetorComercial("" + (setorComercialEncontrado.getCodigo()));
				inserirRotaActionForm.setSetorComercialDescricao(setorComercialEncontrado.getDescricao());
				httpServletRequest.setAttribute("idSetorComercialNaoEncontrada", "true");
				httpServletRequest.setAttribute("nomeCampo", "codigoRota");

				/*
				 * // Retorna o maior código de Rota existente para o setor comercial pesquisado
				 * int codigo = fachada.pesquisarMaximoCodigoRota(new
				 * Integer(setorComercialEncontrado.getId()));
				 * // Acrescenta 1 no valor do código para setar o primeiro código vazio para o
				 * usuário
				 * codigo = (codigo + 1);
				 * inserirRotaActionForm.setCodigoRota("" + codigo);
				 * httpServletRequest.setAttribute("nomeCampo", "codigoRota");
				 */

			}else{
				// o setor comercial não foi encontrado
				inserirRotaActionForm.setCodigoSetorComercial("");
				httpServletRequest.setAttribute("idSetorComercialNaoEncontrada", "exception");
				inserirRotaActionForm.setSetorComercialDescricao("SETOR COMERCIAL INEXISTENTE");

			}

		}

	}

	private void verificarExistenciaCodLeiturista(String idDigitadoEnterEmpresaLeitura, String idDigitadoEnterLeiturista,
					InserirRotaActionForm inserirRotaActionForm, HttpServletRequest httpServletRequest, Fachada fachada){

		// Verifica se o código do Leiturista que foi digitado
		if((idDigitadoEnterLeiturista != null && !idDigitadoEnterLeiturista.trim().equalsIgnoreCase(""))
						&& (idDigitadoEnterEmpresaLeitura != null && !idDigitadoEnterEmpresaLeitura.trim().equalsIgnoreCase(""))){

			FiltroLeiturista filtroLeiturista = new FiltroLeiturista();
			filtroLeiturista.adicionarCaminhoParaCarregamentoEntidade("funcionario");
			filtroLeiturista.adicionarCaminhoParaCarregamentoEntidade("cliente");
			if(idDigitadoEnterEmpresaLeitura != null && !idDigitadoEnterEmpresaLeitura.trim().equalsIgnoreCase("")){

				filtroLeiturista.adicionarParametro(new ParametroSimples(FiltroLeiturista.EMPRESA_ID, new Integer(
								idDigitadoEnterEmpresaLeitura)));
			}

			filtroLeiturista.adicionarParametro(new ParametroSimples(FiltroLeiturista.ID, new Integer(idDigitadoEnterLeiturista)));

			Collection<Leiturista> leituristas = fachada.pesquisar(filtroLeiturista, Leiturista.class.getName());

			if(leituristas != null && !leituristas.isEmpty()){
				// o Leiturista foi encontrado
				Leiturista leituristaEncontrado = (Leiturista) Util.retonarObjetoDeColecao(leituristas);
				inserirRotaActionForm.setCodigoLeiturista("" + (leituristaEncontrado.getId()));

				if(leituristaEncontrado.getFuncionario() != null){
					inserirRotaActionForm.setNomeLeiturista(leituristaEncontrado.getFuncionario().getNome());
				}else if(leituristaEncontrado.getCliente() != null){
					inserirRotaActionForm.setNomeLeiturista(leituristaEncontrado.getCliente().getNome());
				}
				httpServletRequest.setAttribute("idLeituristaEncontrado", "true");
				httpServletRequest.setAttribute("nomeCampo", "codigoLeiturista");

			}else{
				// o setor comercial não foi encontrado
				inserirRotaActionForm.setCodigoLeiturista("");
				httpServletRequest.setAttribute("idSetorComercialNaoEncontrada", "exception");
				inserirRotaActionForm.setNomeLeiturista("AGENTE COMERCIAL INEXISTENTE");

			}

		}
	}

	private void criteriosCobrancaRotaInicial(Collection collectionRotaAcaoCriterio, Fachada fachada, HttpSession sessao,
					HttpServletRequest httpServletRequest){

		collectionRotaAcaoCriterio = new ArrayList();

		FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();
		filtroCobrancaAcao.adicionarParametro(new ParametroNaoNulo(FiltroCobrancaAcao.COBRANCA_CRITERIO));
		filtroCobrancaAcao.adicionarParametro(new ParametroSimples(FiltroCobrancaCriterio.INDICADOR_USO, ConstantesSistema.SIM));

		Collection<CobrancaGrupo> collectionCobrancaAcao = fachada.pesquisar(filtroCobrancaAcao, CobrancaAcao.class.getName());

		Iterator iterator = collectionCobrancaAcao.iterator();

		while(iterator.hasNext()){

			CobrancaAcao cobrancaAcao = (CobrancaAcao) iterator.next();

			RotaAcaoCriterio rotaAcaoCriterioNova = new RotaAcaoCriterio();
			rotaAcaoCriterioNova.setCobrancaAcao(cobrancaAcao);

			// Filtro p pesquisar o critério cobrança padrão para cada ação de cobrança
			FiltroCobrancaCriterio filtroCobrancaCriterio = new FiltroCobrancaCriterio();
			filtroCobrancaCriterio.adicionarParametro(new ParametroSimples(FiltroCobrancaCriterio.ID, cobrancaAcao.getCobrancaCriterio()
							.getId()));

			Collection<CobrancaCriterio> collectionCobrancaCriterio = fachada.pesquisar(filtroCobrancaCriterio, CobrancaCriterio.class
							.getName());

			CobrancaCriterio cobrancaCriterio = (CobrancaCriterio) Util.retonarObjetoDeColecao(collectionCobrancaCriterio);
			rotaAcaoCriterioNova.setCobrancaCriterio(cobrancaCriterio);

			collectionRotaAcaoCriterio.add(rotaAcaoCriterioNova);

		}

		// manda para a sessão a coleção de collectionRotaAcaoCriterio
		sessao.setAttribute("collectionRotaAcaoCriterio", collectionRotaAcaoCriterio);
		httpServletRequest.setAttribute("adicionar", "desabilitado");
	}

}
