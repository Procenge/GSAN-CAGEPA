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
 *
 * GSANPCG
 * Virg�nia Melo
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

package gcom.gui.cadastro.atendimento;

import gcom.cadastro.atendimento.AtendProcDocumentoPessoaTipo;
import gcom.cadastro.atendimento.FiltroAtendProcDocumentoPessoaTipo;
import gcom.cadastro.atendimento.NormaProcedimental;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.filtro.ParametroSimples;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * Classe respons�vel por atualizar uma NormaProcedimental.
 * 
 * @author Gicevalter Couo
 * @date: 22/09/2014
 */
public class AtualizarNormaProcedimentalAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AtualizarNormaProcedimentalActionForm form = (AtualizarNormaProcedimentalActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		NormaProcedimental normaProcedimental = (NormaProcedimental) sessao.getAttribute("NormaProcedimentalAtualizar");

		FiltroAtendProcDocumentoPessoaTipo filtroAtendProcDocumentoPessoaTipo = new FiltroAtendProcDocumentoPessoaTipo();
		filtroAtendProcDocumentoPessoaTipo.adicionarParametro(new ParametroSimples(
						FiltroAtendProcDocumentoPessoaTipo.ATENDIMENTO_PROCEDIMENTO_ID, normaProcedimental.getId()));
		
		Collection<AtendProcDocumentoPessoaTipo> colecaoAtendProcDocumentoPessoaTipo = fachada.pesquisar(filtroAtendProcDocumentoPessoaTipo,AtendProcDocumentoPessoaTipo.class.getName() );
		if(colecaoAtendProcDocumentoPessoaTipo.size() > 0){
			throw new ActionServletException("atencao.norma_procedimental_ja_utilizada", normaProcedimental.getId().toString());
		}

		normaProcedimental.setDescricao(form.getTituloNormaProcedimental());
		normaProcedimental.setUltimaAlteracao(new Date());
		normaProcedimental.setIndicadorUso(Short.parseShort(form.getIndicadorUso()));
		FormFile conteudoNormaProcedimental = (FormFile) form.getConteudoNormaProcedimental();

		if(conteudoNormaProcedimental != null && !form.getConteudoNormaProcedimental().getFileName().equals("")){
			byte[] arquivoConteudo = null;

			try{
				arquivoConteudo = conteudoNormaProcedimental.getFileData();
			}catch(FileNotFoundException e){
				e.printStackTrace(); // TODO -> vsm: tratar exce��o
			}catch(IOException e){
				e.printStackTrace(); // TODO -> vsm: tratar exce��o
			}

			normaProcedimental.setNomeArquivo(conteudoNormaProcedimental.getFileName());
			normaProcedimental.setConteudo(arquivoConteudo);
		}

		fachada.atualizar(normaProcedimental);

		// ------------ REGISTRAR TRANSA��O ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_ATULIZAR_NORMA_PROCEDIMENTAL,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();

		operacao.setId(Operacao.OPERACAO_ATULIZAR_NORMA_PROCEDIMENTAL);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// ------------ REGISTRAR TRANSA��O ----------------

		sessao.removeAttribute("idNormaProcedimental");

		sessao.removeAttribute("NormaProcedimentalAtualizar");

		montarPaginaSucesso(httpServletRequest, "Norma Procedimental de c�digo " + normaProcedimental.getId() + " atualizada com sucesso.",
						"Manter outra Norma Procedimental", "exibirFiltrarNormaProcedimentalAction.do?menu=sim");

		return retorno;

	}
}