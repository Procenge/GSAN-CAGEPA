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
 * [UC0389] - Inserir Imovel Doacao Action responsável pela inserção de um
 * Imóvel Doação
 * 
 * @author César Araújo
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

		/*** Declara e inicializa variáveis ***/
		Imovel imovel = null;
		Date dataAdesao = null;
		Fachada fachada = null;
		ActionForward retorno = null;
		HttpSession sessao = null;
		Usuario usuarioAdesao = null;
		BigDecimal valorDoacao = null;
		ImovelDoacao imovelDoacao = null;
		EntidadeBeneficente entidadeBeneficente = null;

		/*** Procedimentos básicos para execução do método ***/
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

		// ------------ REGISTRAR TRANSAÇÃO ----------------//
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_CATEGORIA_INSERIR,
						new UsuarioAcaoUsuarioHelper(usuarioAdesao, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_CATEGORIA_INSERIR);
		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// ------------ REGISTRAR TRANSAÇÃO ----------------//

		/*** Verifica se a associação de imovel e entidade beneficente já existe na base de dados ***/
		imovelDoacao = fachada.verificarExistenciaImovelDoacao(imovel.getId(), entidadeBeneficente.getId());

		if(imovelDoacao != null && imovelDoacao.getId() != 0){
			throw new ActionServletException("atencao.inserir_imovel_doacao_ja_existe", null, imovelDoacao.getId().toString());
		}

		/*** Preenche as informações de imovel doação ***/
		imovelDoacao = new ImovelDoacao(imovel, valorDoacao, entidadeBeneficente, dataAdesao, usuarioAdesao, new Date());

		// ------------ REGISTRAR TRANSAÇÃO ----------------//
		imovelDoacao.setOperacaoEfetuada(operacaoEfetuada);
		imovelDoacao.adicionarUsuario(usuarioAdesao, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(imovelDoacao);
		// ------------ REGISTRAR TRANSAÇÃO ----------------//

		/**
		 * alterado por pedro alexandre dia 16/11/2006
		 * Recupera o usuário logado para passar no metódo de inserir
		 * para verificar se o usuário tem abrangência para inserir a doação
		 */
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		/*** Insere o imovel doacao - As validações estão no Controlador ***/
		fachada.inserirImovelDoacaoRetorno(imovelDoacao, usuarioLogado);

		/*** Monta tela de sucesso ***/
		montarPaginaSucesso(httpServletRequest, "Autorização para Doação Mensal do Imóvel " + imovel.getId().toString()
						+ " inserida com sucesso.", "Inserir outra Autorização para Doação Mensal", "exibirInserirImovelDoacaoAction.do",
						"exibirManterImovelDoacaoAction.do?idRegistroAtualizacao=" + imovel.getId().toString(),
						"Cancelar Autorização para Doação Mensal do Imóvel " + imovel.getId().toString());

		return retorno;
	}
}