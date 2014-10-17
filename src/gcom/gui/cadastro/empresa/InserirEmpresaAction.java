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

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
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
import gcom.util.validacao.ValidarCampos;

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
 * Action respons�vel por Inserir Empresa.
 * 
 * @author Virg�nia Melo
 */
public class InserirEmpresaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		InserirEmpresaActionForm form = (InserirEmpresaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		Integer codigoEmpresa = new Integer(form.getCodigoEmpresa());
		String nomeEmpresa = form.getNomeEmpresa();
		String emailEmpresa = form.getEmailEmpresa();
		String nomeAbreviado = form.getNomeEmpresaAbreviado();
		FormFile logoEmpresa = (FormFile) form.getLogoEmpresa();

		// Verifica se j� existe empresa com o ID informado
		FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
		filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.ID, codigoEmpresa));
		Collection empresas = fachada.pesquisar(filtroEmpresa, Empresa.class.getName());

		if(empresas != null && !empresas.isEmpty()){
			Empresa empresaExistente = (Empresa) empresas.iterator().next();

			throw new ActionServletException("atencao.empresa_ja_cadastrada", null, empresaExistente.getId().toString());
		}

		Empresa empresa = new Empresa();
		empresa.setId(codigoEmpresa);
		empresa.setDescricao(nomeEmpresa);
		empresa.setEmail(emailEmpresa);
		empresa.setDescricaoAbreviada(nomeAbreviado);
		empresa.setIndicadorUso(new Integer(1).shortValue());
		empresa.setIndicadorEmpresaPrincipal(new Integer(2).shortValue());
		empresa.setUltimaAlteracao(new Date());

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

		fachada.inserir(empresa);

		// Registrar Transa��o
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_INSERIR_EMPRESA, new UsuarioAcaoUsuarioHelper(
						usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_INSERIR_EMPRESA);
		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		montarPaginaSucesso(httpServletRequest, "Empresa de c�digo " + codigoEmpresa + " inserida com sucesso.", "Inserir outra Empresa",
						"exibirInserirEmpresaAction.do?menu=sim", "exibirAtualizarEmpresaAction.do?codigoEmpresa=" + codigoEmpresa,
						"Atualizar Empresa inserida");

		return retorno;

	}
}