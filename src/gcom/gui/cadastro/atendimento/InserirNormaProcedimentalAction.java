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
 * 
 * GSANPCG
 * Virgínia Melo
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

package gcom.gui.cadastro.atendimento;

import gcom.cadastro.atendimento.FiltroNormaProcedimental;
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
 * Action responsável por Inserir NormaProcedimental.
 * 
 * @author Virgínia Melo
 */
public class InserirNormaProcedimentalAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		InserirNormaProcedimentalActionForm form = (InserirNormaProcedimentalActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		Integer codigoNormaProcedimental = new Integer(form.getCodigoNormaProcedimental());
		String tituloNormaProcedimental = form.getTituloNormaProcedimental();
		FormFile conteudoNormaProcedimental = (FormFile) form.getConteudoNormaProcedimental();

		// Verifica se já existe NormaProcedimental com o ID informado
		FiltroNormaProcedimental filtroNormaProcedimental = new FiltroNormaProcedimental();
		filtroNormaProcedimental.adicionarParametro(new ParametroSimples(FiltroNormaProcedimental.ID, codigoNormaProcedimental));
		Collection NormaProcedimentals = fachada.pesquisar(filtroNormaProcedimental, NormaProcedimental.class.getName());

		if(NormaProcedimentals != null && !NormaProcedimentals.isEmpty()){
			NormaProcedimental normaProcedimentalExistente = (NormaProcedimental) NormaProcedimentals.iterator().next();

			throw new ActionServletException("atencao.norma_procedimental_ja_cadastrada", null, normaProcedimentalExistente.getId()
							.toString());
		}

		NormaProcedimental normaProcedimental = new NormaProcedimental();
		normaProcedimental.setId(codigoNormaProcedimental);
		normaProcedimental.setDescricao(tituloNormaProcedimental);
		normaProcedimental.setNomeArquivo(conteudoNormaProcedimental.getFileName());
		normaProcedimental.setIndicadorUso(new Integer(1).shortValue());
		normaProcedimental.setUltimaAlteracao(new Date());

		if(conteudoNormaProcedimental != null){

			byte[] conteudoNormaProc = null;

			try{
				conteudoNormaProc = conteudoNormaProcedimental.getFileData();
			}catch(FileNotFoundException e){
				e.printStackTrace(); // TODO -> vsm: tratar exceção
			}catch(IOException e){
				e.printStackTrace(); // TODO -> vsm: tratar exceção
			}

			normaProcedimental.setConteudo(conteudoNormaProc);
		}

		fachada.inserir(normaProcedimental);

		// Registrar Transação
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_INSERIR_NORMA_PROCEDIMENTAL,
						new UsuarioAcaoUsuarioHelper(
						usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_INSERIR_NORMA_PROCEDIMENTAL);
		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		montarPaginaSucesso(httpServletRequest, "Norma Procedimental de código " + codigoNormaProcedimental + " inserida com sucesso.",
						"Inserir outra Norma Procedimental",
						"exibirInserirNormaProcedimentalAction.do?menu=sim", "exibirAtualizarNormaProcedimentalAction.do?codigoNormaProcedimental=" + codigoNormaProcedimental,
						"Atualizar Norma Procedimental inserida");

		return retorno;

	}
}