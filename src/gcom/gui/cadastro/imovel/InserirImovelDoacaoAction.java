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

package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.EntidadeBeneficente;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelDoacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0389] - Inserir Imovel Doacao Action respons�vel pela inser��o de um
 * Im�vel Doa��o
 * 
 * @author C�sar Ara�jo
 * @created 30 de 08 de 2006
 */
public class InserirImovelDoacaoAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		/*** Declara e inicializa vari�veis ***/
		Imovel imovel = null;
		Date dataAdesao = null;
		Fachada fachada = null;
		ActionForward retorno = null;
		HttpSession sessao = null;
		Usuario usuarioAdesao = null;
		BigDecimal valorDoacao = null;
		ImovelDoacao imovelDoacao = null;
		EntidadeBeneficente entidadeBeneficente = null;

		/*** Procedimentos b�sicos para execu��o do m�todo ***/
		retorno = actionMapping.findForward("telaSucesso");
		ImovelDoacaoActionForm imovelDoacaoActionForm = (ImovelDoacaoActionForm) actionForm;
		fachada = Fachada.getInstancia();
		sessao = httpServletRequest.getSession(false);

		/*** Define os valores a serem armazenados na base de dados ***/
		valorDoacao = new BigDecimal(imovelDoacaoActionForm.getValorDoacao().replace(",", "."));
		usuarioAdesao = new Usuario();
		usuarioAdesao.setId(((Usuario) sessao.getAttribute("usuarioLogado")).getId());
		dataAdesao = new Date();
		entidadeBeneficente = new EntidadeBeneficente();
		entidadeBeneficente.setId(new Integer(imovelDoacaoActionForm.getIdEntidadeBeneficente()));
		imovel = new Imovel();
		imovel.setId(new Integer(imovelDoacaoActionForm.getIdImovel()));

		// ------------ REGISTRAR TRANSA��O ----------------//
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_CATEGORIA_INSERIR,
						new UsuarioAcaoUsuarioHelper(usuarioAdesao, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_CATEGORIA_INSERIR);
		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// ------------ REGISTRAR TRANSA��O ----------------//

		/*** Verifica se a associa��o de imovel e entidade beneficente j� existe na base de dados ***/
		imovelDoacao = fachada.verificarExistenciaImovelDoacao(imovel.getId(), entidadeBeneficente.getId());

		if(imovelDoacao != null && imovelDoacao.getId() != 0){
			throw new ActionServletException("atencao.inserir_imovel_doacao_ja_existe", null, imovelDoacao.getId().toString());
		}

		/*** Preenche as informa��es de imovel doa��o ***/
		imovelDoacao = new ImovelDoacao(imovel, valorDoacao, entidadeBeneficente, dataAdesao, usuarioAdesao, new Date());

		// ------------ REGISTRAR TRANSA��O ----------------//
		imovelDoacao.setOperacaoEfetuada(operacaoEfetuada);
		imovelDoacao.adicionarUsuario(usuarioAdesao, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(imovelDoacao);
		// ------------ REGISTRAR TRANSA��O ----------------//

		/**
		 * alterado por pedro alexandre dia 16/11/2006
		 * Recupera o usu�rio logado para passar no met�do de inserir
		 * para verificar se o usu�rio tem abrang�ncia para inserir a doa��o
		 */
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		/*** Insere o imovel doacao - As valida��es est�o no Controlador ***/
		fachada.inserirImovelDoacaoRetorno(imovelDoacao, usuarioLogado);

		/*** Monta tela de sucesso ***/
		montarPaginaSucesso(httpServletRequest, "Autoriza��o para Doa��o Mensal do Im�vel " + imovel.getId().toString()
						+ " inserida com sucesso.", "Inserir outra Autoriza��o para Doa��o Mensal", "exibirInserirImovelDoacaoAction.do",
						"exibirManterImovelDoacaoAction.do?idRegistroAtualizacao=" + imovel.getId().toString(),
						"Cancelar Autoriza��o para Doa��o Mensal do Im�vel " + imovel.getId().toString());

		return retorno;
	}
}