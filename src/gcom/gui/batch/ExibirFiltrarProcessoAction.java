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

package gcom.gui.batch;

import gcom.batch.*;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesConfig;
import gcom.util.Util;
import gcom.util.agendadortarefas.VerificadorIP;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action responsável pela pre-exibição da pagina de filtrar processo iniciado
 * 
 * @author Rodrigo Silveira
 * @created 22/07/2006
 */
public class ExibirFiltrarProcessoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("filtrarProcesso");

		// Pesquiasr os tipos do processo, para popular o select da página.
		this.pesquisarTipoProcesso(httpServletRequest);

		// Pesquiasr os Grupos Faturamento e Cobranca, para popular o select da página.
		this.pesquisarGrupos(httpServletRequest);

		// Pesquisa as situações do processos para o select da página
		this.pesquisarProcessoSituacao(httpServletRequest);

		// Limpar o formulário.
		this.resetForm((FiltrarProcessoActionForm) actionForm, httpServletRequest);

		// Pesquisa o Processo digitado
		this.pesquisarProcessoDigitado(httpServletRequest, (FiltrarProcessoActionForm) actionForm);

		// Pesquisa o Usuario digitado
		this.pesquisarUsuarioDigitado((FiltrarProcessoActionForm) actionForm);

		httpServletRequest.setAttribute("ipLocal", VerificadorIP.getInstancia().getIp());
		httpServletRequest.setAttribute("exibirIpProcesso", ConstantesConfig.getExibirIpProcesso());

		return retorno;
	}

	/**
	 * Método pesquisarGrupos
	 * 
	 * @param httpServletRequest
	 * @author Marlos Ribeiro
	 * @since 17/01/2013
	 */
	private void pesquisarGrupos(HttpServletRequest httpServletRequest){

		// FiltroCobrancaGrupo filtroCobrancaGrupo = new FiltroCobrancaGrupo();
		// filtroCobrancaGrupo.adicionarParametro(new
		// ParametroSimples(FiltroCobrancaGrupo.INDICADOR_USO, "1"));
		// filtroCobrancaGrupo.setCampoOrderBy(FiltroCobrancaGrupo.ID);
		// Collection<CobrancaGrupo> collectionCobrancaGrupo =
		// Fachada.getInstancia().pesquisar(filtroCobrancaGrupo,
		// CobrancaGrupo.class.getName());

		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
		filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO, "1"));
		filtroFaturamentoGrupo.setCampoOrderBy(FiltroFaturamentoGrupo.ID);
		Collection<FaturamentoGrupo> collectionFaturamentoGrupo = Fachada.getInstancia().pesquisar(filtroFaturamentoGrupo,
						FaturamentoGrupo.class.getName());

		// httpServletRequest.setAttribute("collectionGrupoCobranca", collectionCobrancaGrupo);
		httpServletRequest.setAttribute("collectionGrupoFaturamento", collectionFaturamentoGrupo);
	}

	/**
	 * Pesquisa no banco todos tipos do processo, para popular o select da página.
	 * 
	 * @author Ricardo Rodrigues.
	 * @date 05/03/2012
	 * @param httpServletRequest
	 */
	private void pesquisarTipoProcesso(HttpServletRequest httpServletRequest){

		FiltroProcessoTipo filtroProcessoTipo = new FiltroProcessoTipo();
		filtroProcessoTipo.adicionarParametro(new ParametroSimples(FiltroProcessoTipo.INDICADORUSO, "1"));
		filtroProcessoTipo.setCampoOrderBy(FiltroProcessoTipo.DESCRICAO);
		Collection<ProcessoTipo> collection = Fachada.getInstancia().pesquisar(filtroProcessoTipo, ProcessoTipo.class.getName());
		httpServletRequest.setAttribute("collectionTipoProcesso", collection);

	}

	/**
	 * Pesquisa todas as Situações do Processo para popular o select da página
	 * de inserir processo
	 * 
	 * @author Rodrigo Silveira
	 * @date 22/07/2006
	 * @param httpServletRequest
	 */
	private void pesquisarProcessoSituacao(HttpServletRequest httpServletRequest){

		FiltroProcessoSituacao filtroProcessoSituacao = new FiltroProcessoSituacao();
		Collection<ProcessoSituacao> colecao = Fachada.getInstancia().pesquisar(filtroProcessoSituacao, ProcessoSituacao.class.getName());
		httpServletRequest.setAttribute("colecaoProcessoSituacao", colecao);

	}

	/**
	 * Pesquisa todas as Situações do Processo para popular o select da página
	 * de inserir processo
	 * 
	 * @author Rodrigo Silveira
	 * @date 15/03/2007
	 * @param httpServletRequest
	 */

	private void resetForm(FiltrarProcessoActionForm form, HttpServletRequest request){

		String limpar = (String) request.getParameter("limpar");

		if(limpar != null && limpar.equals("true")){

			form.resetFormCustom();
		}

	}

	/**
	 * Função que pesquisa um Processo iniciado precedente digitado
	 * 
	 * @author Rodrigo Silveira
	 * @date 24/07/2006
	 * @param httpServletRequest
	 * @param actionForm
	 */
	private void pesquisarProcessoDigitado(HttpServletRequest httpServletRequest, FiltrarProcessoActionForm actionForm){

		String idDigitadoEnterProcesso = (String) actionForm.getIdProcesso();

		actionForm.setDescricaoProcesso("");
		actionForm.setIdProcesso("");

		// Verifica se o código foi digitado
		if(idDigitadoEnterProcesso != null && !idDigitadoEnterProcesso.trim().equals("") && Integer.parseInt(idDigitadoEnterProcesso) > 0
						&& !idDigitadoEnterProcesso.trim().equals(actionForm.getIdProcesso())
						&& Util.validarStringNumerica(idDigitadoEnterProcesso) == true){

			Fachada fachada = Fachada.getInstancia();

			// Este trecho pesquisa o processo digitado e altera o form para
			// refletir o resultado da busca na página de filtrar processo

			FiltroProcesso filtroProcesso = new FiltroProcesso();

			filtroProcesso.adicionarParametro(new ParametroSimples(FiltroProcesso.ID, idDigitadoEnterProcesso));

			Collection processos = fachada.pesquisar(filtroProcesso, Processo.class.getName());

			if(processos != null && !processos.isEmpty()){
				// O processo foi encontrado
				actionForm.setIdProcesso(((Processo) ((List) processos).get(0)).getId().toString());
				actionForm.setDescricaoProcesso(((Processo) ((List) processos).get(0)).getDescricaoProcesso());

			}else{
				actionForm.setIdProcesso("");
				httpServletRequest.setAttribute("idProcessoNaoEncontrado", "true");
				actionForm.setDescricaoProcesso("Processo inexistente");

			}
		}

	}

	/**
	 * Método que busca o usuário quando for digitado e clicado enter.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 14/03/2012
	 * @param actionForm
	 */
	private void pesquisarUsuarioDigitado(FiltrarProcessoActionForm actionForm){

		String idUsuarioDigitadoEnter = (String) actionForm.getIdUsuario();

		if(idUsuarioDigitadoEnter != null && !"".equals(idUsuarioDigitadoEnter.trim())
						&& Util.validarStringNumerica(idUsuarioDigitadoEnter) == true && Integer.parseInt(idUsuarioDigitadoEnter) > 0){
			FiltroUsuario filtroUsuario = new FiltroUsuario();

			// coloca parametro no filtro (virá o login, após pesquisa seta o idDoUsuário)
			filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, Integer.parseInt(idUsuarioDigitadoEnter)));

			// pesquisa
			Collection usuarios = Fachada.getInstancia().pesquisar(filtroUsuario, Usuario.class.getName());
			if(usuarios != null && !usuarios.isEmpty()){
				// O usuario foi encontrado
				actionForm.setNomeUsuario(((Usuario) ((List) usuarios).get(0)).getNomeUsuario());
				actionForm.setIdUsuario(((Usuario) ((List) usuarios).get(0)).getId().toString());

				actionForm.setUsuarioNaoEncontrada("false");
			}else{
				actionForm.setIdUsuario("");
				actionForm.setUsuarioNaoEncontrada("true");
				actionForm.setNomeUsuario("pesquisa.usuario.inexistente");
			}
		}else{
			actionForm.setNomeUsuario("");
		}

	}
}
