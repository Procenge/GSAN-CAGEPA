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
 * Ailton Francisco de Sousa Junior
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

import gcom.atendimentopublico.ordemservico.OcorrenciaInfracao;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cobranca.FiltroInfracaoImovelSituacao;
import gcom.cobranca.FiltroInfracaoLigacaoSituacao;
import gcom.cobranca.FiltroInfracaoTipo;
import gcom.cobranca.InfracaoImovelSituacao;
import gcom.cobranca.InfracaoLigacaoSituacao;
import gcom.cobranca.InfracaoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UCXXXX] INSERIR DADOS NO ENCERRAMENTO DA O.S.
 * 
 * @author Ailton Sousa
 * @date 16/05/2011
 */
public class ExibirInserirDadosEncerramentoOrdemServicoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirDadosEncerramentoOrdemServico");
		InserirDadosEncerramentoOrdemServicoActionForm form = (InserirDadosEncerramentoOrdemServicoActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		String objetoConsulta = "";

		// [FS0001] - VERIFICAR EXISTENCIA DE DADOS

		// Infracao Tipo
		FiltroInfracaoTipo filtroInfracaoTipo = new FiltroInfracaoTipo();
		filtroInfracaoTipo
						.adicionarParametro(new ParametroSimples(FiltroInfracaoTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroInfracaoTipo.setCampoOrderBy(FiltroInfracaoTipo.DESCRICAO);

		Collection<InfracaoTipo> colecaoInfracaoTipo = fachada.pesquisar(filtroInfracaoTipo, InfracaoTipo.class.getName());

		if(colecaoInfracaoTipo == null || colecaoInfracaoTipo.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Infracao Tipo");
		}

		httpServletRequest.setAttribute("colecaoInfracaoTipo", colecaoInfracaoTipo);

		// Infracao Imovel Situacao
		FiltroInfracaoImovelSituacao filtroInfracaoImovelSituacao = new FiltroInfracaoImovelSituacao();
		filtroInfracaoImovelSituacao.adicionarParametro(new ParametroSimples(FiltroInfracaoImovelSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroInfracaoImovelSituacao.setCampoOrderBy(FiltroInfracaoImovelSituacao.DESCRICAO);

		Collection<InfracaoImovelSituacao> colecaoInfracaoImovelSituacao = fachada.pesquisar(filtroInfracaoImovelSituacao,
						InfracaoImovelSituacao.class.getName());

		if(colecaoInfracaoImovelSituacao == null || colecaoInfracaoImovelSituacao.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Infracao Imovel Situacao");
		}

		httpServletRequest.setAttribute("colecaoInfracaoImovelSituacao", colecaoInfracaoImovelSituacao);

		// Infracao Ligacao Situacao
		FiltroInfracaoLigacaoSituacao filtroInfracaoLigacaoSituacao = new FiltroInfracaoLigacaoSituacao();
		filtroInfracaoLigacaoSituacao.adicionarParametro(new ParametroSimples(FiltroInfracaoLigacaoSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroInfracaoLigacaoSituacao.setCampoOrderBy(FiltroInfracaoLigacaoSituacao.DESCRICAO);

		Collection<InfracaoLigacaoSituacao> colecaoInfracaoLigacaoSituacao = fachada.pesquisar(filtroInfracaoLigacaoSituacao,
						InfracaoLigacaoSituacao.class.getName());

		if(colecaoInfracaoLigacaoSituacao == null || colecaoInfracaoLigacaoSituacao.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Infracao Ligacao Situacao");
		}

		httpServletRequest.setAttribute("colecaoInfracaoLigacaoSituacao", colecaoInfracaoLigacaoSituacao);

		if(httpServletRequest.getParameter("numeroOS") != null && !httpServletRequest.getParameter("numeroOS").equals("")){
			form.setNumeroOrdemServico(httpServletRequest.getParameter("numeroOS"));
			sessao.setAttribute("numeroOS", httpServletRequest.getParameter("numeroOS"));
		}

		if(httpServletRequest.getParameter("retornoTela") != null && !httpServletRequest.getParameter("retornoTela").equals("")){
			sessao.setAttribute("retornoTela", httpServletRequest.getParameter("retornoTela"));
			if(httpServletRequest.getParameter("retornoTela").equals("exibirEncerrarOrdemServicoPopupAction.do")){
				sessao.setAttribute("isPopup", "S");
			}
		}

		String idFuncionario = form.getFuncionario();

		if(httpServletRequest.getParameter("tipoConsulta") != null && !httpServletRequest.getParameter("tipoConsulta").equals("")){
			// se for os parametros de enviarDadosParametros serão mandados para a jsp
			form.setFuncionario(httpServletRequest.getParameter("idCampoEnviarDados"));
			form.setNomeFuncionario(httpServletRequest.getParameter("descricaoCampoEnviarDados"));
			httpServletRequest.setAttribute("funcionarioEncontrado", "OK");
		}else{
			form.setFuncionario("");
			form.setNomeFuncionario("");
		}

		objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		// Consulta os Dados do funcionario ao digitar o id e clicar enter na jsp
		if(!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.equals("1")){

			// Funcionario
			if(idFuncionario != null && !idFuncionario.trim().equals("")){
				FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
				filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, Util.obterInteger(idFuncionario)));

				Collection funcionarios = fachada.pesquisar(filtroFuncionario, Funcionario.class.getName());

				if(funcionarios != null && !funcionarios.isEmpty()){
					Funcionario funcionario = (Funcionario) ((List) funcionarios).get(0);

					// String id = Util.adicionarZerosEsqueda(3, idFuncionario);
					form.setFuncionario(idFuncionario);

					String nome = funcionario.getNome();
					form.setNomeFuncionario(nome);

					httpServletRequest.setAttribute("funcionarioEncontrado", "OK");
				}else{
					httpServletRequest.removeAttribute("funcionarioEncontrado");

					form.setFuncionario("");
					form.setNomeFuncionario("FUNCIONÁRIO INEXISTENTE");
				}
			}
		}else{
			OcorrenciaInfracao ocorrenciaInfracao = (OcorrenciaInfracao) sessao.getAttribute("ocorrenciaInfracao");
			Integer[] ocorrenciasTipo = (Integer[]) sessao.getAttribute("colecaoInfracaoTipo");
			if(ocorrenciaInfracao != null && ocorrenciasTipo != null){
				form.setValuesForm(ocorrenciaInfracao, ocorrenciasTipo);
			}
		}

		return retorno;
	}
}
