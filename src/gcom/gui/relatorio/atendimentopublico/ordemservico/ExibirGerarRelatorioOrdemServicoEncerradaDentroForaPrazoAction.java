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

package gcom.gui.relatorio.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.atendimentopublico.ordemservico.GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Victon Santos
 * @date 20/12/2013
 */
public class ExibirGerarRelatorioOrdemServicoEncerradaDentroForaPrazoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirRelatorioOrdemServicoEncerradaDentroForaPrazo");

		HttpSession sessao = httpServletRequest.getSession(false);

		// Form
		GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm = (GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm) actionForm;

		gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm.setOrigemServico(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO + "");

		if(gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm.getIdLocalidadeFiltro() != null
						&& !gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm.getIdLocalidadeFiltro().equals("")){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Integer
							.valueOf(gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm.getIdLocalidadeFiltro())));
			Localidade localidade = null;

			Iterator it = getFachada().pesquisar(filtroLocalidade, Localidade.class.getName()).iterator();

			if(it.hasNext()){
				localidade = (Localidade) it.next();
			}

			if(localidade != null){
				gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm.setIdLocalidadeFiltro(localidade.getId().toString());
				gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm.setLocalidadeDescricaoFiltro(localidade.getDescricao());
				httpServletRequest.setAttribute("idLocalidadeNaoEncontrada", "true");
			}else{
				gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm.setIdLocalidadeFiltro("");
				httpServletRequest.setAttribute("idLocalidadeNaoEncontrada", "exception");
				gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm.setLocalidadeDescricaoFiltro("Localidade inexistente");
			}
		}

		if(httpServletRequest.getParameter("menu") != null){

			gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm.setOrigemServico(ConstantesSistema.ORIGEM_SERVICO_SOLICITADO
							.toString());
			gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm.setTipoOrdenacao("1");
			Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
			if(usuario != null){
				gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm.setIdUnidadeAtual(usuario.getUnidadeOrganizacional().getId()
								.toString());
				gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm
								.setDescricaoUnidadeAtual(usuario.getUnidadeOrganizacional().getDescricao());
			}
		}

		// Flag indicando que o usu�rio fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		// [UC0376] - Pesquisar Unidade
		if((objetoConsulta != null && !objetoConsulta.trim().equals("") && objetoConsulta.trim().equals("1"))
						|| (objetoConsulta != null && !objetoConsulta.trim().equals("") && objetoConsulta.trim().equals("2"))
						|| (objetoConsulta != null && !objetoConsulta.trim().equals("") && objetoConsulta.trim().equals("3"))){

			// Faz a consulta de Cliente
			this.pesquisarUnidadeOrganizacional(gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm, objetoConsulta);
		}

		if((objetoConsulta != null && !objetoConsulta.trim().equals("") && objetoConsulta.trim().equals("6"))){

			// Faz a consulta de Localidade
			this.pesquisarLocalidade(gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm, objetoConsulta);
		}

		if(httpServletRequest.getParameter("tipoConsulta") != null && !httpServletRequest.getParameter("tipoConsulta").equals("")){

			String id = httpServletRequest.getParameter("idCampoEnviarDados");
			String descricao = httpServletRequest.getParameter("descricaoCampoEnviarDados");

			if(httpServletRequest.getParameter("tipoConsulta").equals("unidadeOrganizacional")){

				if(sessao.getAttribute("tipoUnidade").equals("unidadeAtendimento")){
					gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm.setIdUnidadeAtendimento(id);
					gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm.setDescricaoUnidadeAtendimento(descricao);

				}else if(sessao.getAttribute("tipoUnidade").equals("unidadeAtual")){
					gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm.setIdUnidadeAtual(id);
					gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm.setDescricaoUnidadeAtual(descricao);

				}else{
					gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm.setIdUnidadeEncerramento(id);
					gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm.setDescricaoUnidadeEncerramento(descricao);
				}

			}
		}

		// Monta a colecao de tipos Servicos
		this.pesquisarTipoServico(httpServletRequest);

		// Seta os request�s encontrados
		this.setaRequest(httpServletRequest, gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm);

		// if
		// (httpServletRequest.getParameter("caminhoRetornoTelaPesquisaOrdemServico")
		// != null) {
		//			
		// sessao.setAttribute("caminhoRetornoTelaPesquisaOrdemServico",
		// httpServletRequest.getParameter("caminhoRetornoTelaPesquisaOrdemServico"));
		//			
		// }

		httpServletRequest.setAttribute("origemServico", gerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm.getOrigemServico());

		return retorno;
	}

	/**
	 * @author Victon Santos
	 * @date 20/12/2013
	 */
	private void pesquisarUnidadeOrganizacional(GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm form, String objetoConsulta){

		FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

		Integer idUnidade = null;

		if(objetoConsulta.equals("1")){
			idUnidade = Integer.valueOf(form.getIdUnidadeAtendimento());
		}else if(objetoConsulta.equals("2")){
			idUnidade = Integer.valueOf(form.getIdUnidadeAtual());
		}else if(objetoConsulta.equals("3")){
			idUnidade = Integer.valueOf(form.getIdUnidadeEncerramento());
		}

		filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidade));

		// Pesquisa de acordo com os par�metros informados no filtro
		Collection colecaoUnidade = Fachada.getInstancia().pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a cole��o
		if(colecaoUnidade != null && !colecaoUnidade.isEmpty()){

			// Obt�m o objeto da cole��o pesquisada
			UnidadeOrganizacional unidadeOrganizacional = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidade);

			if(objetoConsulta.equals("1")){

				form.setIdUnidadeAtendimento(unidadeOrganizacional.getId().toString());
				form.setDescricaoUnidadeAtendimento(unidadeOrganizacional.getDescricao());

			}else if(objetoConsulta.equals("2")){

				form.setIdUnidadeAtual(unidadeOrganizacional.getId().toString());
				form.setDescricaoUnidadeAtual(unidadeOrganizacional.getDescricao());

			}else{

				form.setIdUnidadeEncerramento(unidadeOrganizacional.getId().toString());
				form.setDescricaoUnidadeEncerramento(unidadeOrganizacional.getDescricao());

			}

		}else{
			if(objetoConsulta.equals("1")){

				form.setIdUnidadeAtendimento("");
				form.setDescricaoUnidadeAtendimento("Unidade de Atendimento inexistente");

			}else if(objetoConsulta.equals("2")){

				form.setIdUnidadeAtual("");
				form.setDescricaoUnidadeAtual("Unidade Atual inexistente");

			}else{

				form.setIdUnidadeEncerramento("");
				form.setDescricaoUnidadeEncerramento("Unidade Encerramento inexistente");

			}
		}
	}

	private void pesquisarLocalidade(GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm gerarRelatorioAcompanhamento,
					String objetoConsulta){

		Fachada fachada = Fachada.getInstancia();

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

		// Recebe o valor do campo localidadeOrigemID do formul�rio.
		String localidadeID = (String) gerarRelatorioAcompanhamento.getIdLocalidadeFiltro();

		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeID));

		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna localidade
		Collection<Localidade> colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
			// Localidade nao encontrada
			// Limpa os campos localidadeOrigemID e nomeLocalidadeOrigem do
			// formul�rio
			gerarRelatorioAcompanhamento.setIdLocalidadeFiltro("");
			gerarRelatorioAcompanhamento.setLocalidadeDescricaoFiltro("Localidade inexistente");
		}else{
			Localidade objetoLocalidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);
			gerarRelatorioAcompanhamento.setIdLocalidadeFiltro(String.valueOf(objetoLocalidade.getId()));
			gerarRelatorioAcompanhamento.setLocalidadeDescricaoFiltro(objetoLocalidade.getDescricao());
		}
	}

	/**
	 * @author Victon Santos
	 * @date 20/12/2013
	 */
	private void pesquisarTipoServico(HttpServletRequest httpServletRequest){

		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();

		filtroServicoTipo.setConsultaSemLimites(true);
		filtroServicoTipo.setCampoOrderBy(FiltroServicoTipo.DESCRICAO);

		Collection colecaoTipoServico = Fachada.getInstancia().pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

		if(colecaoTipoServico == null || colecaoTipoServico.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Tipo de Servi�o");
		}else{
			httpServletRequest.setAttribute("colecaoTipoServico", colecaoTipoServico);
		}
	}

	/**
	 * @author Victon Santos
	 * @date 20/12/2013
	 */
	private void setaRequest(HttpServletRequest httpServletRequest, GerarRelatorioOrdemServicoEncerradaDentroForaPrazoActionForm form){

		// Unidade Atendimento
		if(form.getIdUnidadeAtendimento() != null && !form.getIdUnidadeAtendimento().equals("")
						&& form.getDescricaoUnidadeAtendimento() != null && !form.getDescricaoUnidadeAtendimento().equals("")){

			httpServletRequest.setAttribute("unidadeAtendimentoEncontrada", "true");
		}

		// Unidade Atual
		if(form.getIdUnidadeAtual() != null && !form.getIdUnidadeAtual().equals("") && form.getDescricaoUnidadeAtual() != null
						&& !form.getDescricaoUnidadeAtual().equals("")){

			httpServletRequest.setAttribute("unidadeAtualEncontrada", "true");
		}

		// Unidade Encerramento
		if(form.getIdUnidadeEncerramento() != null && !form.getIdUnidadeEncerramento().equals("")
						&& form.getDescricaoUnidadeEncerramento() != null && !form.getDescricaoUnidadeEncerramento().equals("")){

			httpServletRequest.setAttribute("unidadeEncerramentoEncontrada", "true");
		}

	}

}