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

package gcom.gui.cadastro.empresa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import gcom.cadastro.empresa.Empresa;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.validacao.ValidarCampos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * Classe respons�vel por atualizar uma Empresa.
 * 
 * @author Virg�nia Melo
 * @date: 06/08/08
 */
public class AtualizarEmpresaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AtualizarEmpresaActionForm form = (AtualizarEmpresaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		Empresa empresa = (Empresa) sessao.getAttribute("empresaAtualizar");

		empresa.setDescricao(form.getNomeEmpresa());
		empresa.setDescricaoAbreviada(form.getNomeEmpresaAbreviado());
		empresa.setEmail(form.getEmailEmpresa());
		empresa.setUltimaAlteracao(new Date());
		empresa.setIndicadorUso(Short.parseShort(form.getIndicadorUso()));
		FormFile logoEmpresa = (FormFile) form.getLogoEmpresa();

		if(logoEmpresa != null){

			if(ValidarCampos.validaFoto(logoEmpresa)){

				byte[] fotoLogo = null;

				try{
					fotoLogo = logoEmpresa.getFileData();
				}catch(FileNotFoundException e){
					e.printStackTrace(); // TODO -> vsm: tratar exce��o
				}catch(IOException e){
					e.printStackTrace(); // TODO -> vsm: tratar exce��o
				}

				empresa.setLogoEmpresa(fotoLogo);
			}
		}

		fachada.atualizar(empresa);

		// ------------ REGISTRAR TRANSA��O ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_ATUALIZAR_NEGATIVADOR,
						new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();

		operacao.setId(Operacao.OPERACAO_ATUALIZAR_NEGATIVADOR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// ------------ REGISTRAR TRANSA��O ----------------

		sessao.removeAttribute("idEmpresa");

		sessao.removeAttribute("empresaAtualizar");

		montarPaginaSucesso(httpServletRequest, "Empresa de c�digo " + empresa.getId() + " atualizada com sucesso.",
						"Manter outra Empresa", "exibirFiltrarEmpresaAction.do?menu=sim");

		return retorno;

	}
}