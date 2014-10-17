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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gui.micromedicao.leitura;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.funcionario.Funcionario;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.micromedicao.Leiturista;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action utilizado para inserir um Leiturista no banco
 * [UC0588] Inserir Leiturista Permite inserir um Leiturista
 * 
 * @author Thiago Tenório
 * @since 22/07/2007
 * @author eduardo henrique
 * @date 09/06/2008
 *       Alterado a nomeação de Leiturista para Agente Comercial , onde o dado for exibido na GUI.
 *       Numero IMEI não é mais obrigatório para Inclusão.
 */
public class InserirLeituristaAction
				extends GcomAction {

	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		InserirLeituristaActionForm inserirLeituristaActionForm = (InserirLeituristaActionForm) actionForm;

		// Cria um Leiturista setando os valores informados pelo
		// usuário na pagina para ser inserido no banco
		Leiturista leiturista = new Leiturista();

		// Funcionario
		if(inserirLeituristaActionForm.getIdFuncionario() != null && !inserirLeituristaActionForm.getIdFuncionario().equalsIgnoreCase("")){
			Funcionario funcionario = new Funcionario();
			funcionario.setId(new Integer(inserirLeituristaActionForm.getIdFuncionario()));

			leiturista.setFuncionario(funcionario);
		}

		// Cliente
		if(inserirLeituristaActionForm.getIdCliente() != null && !inserirLeituristaActionForm.getIdCliente().equalsIgnoreCase("")){
			Cliente cliente = new Cliente();
			cliente.setId(new Integer(inserirLeituristaActionForm.getIdCliente()));

			leiturista.setCliente(cliente);
		}

		// Telefone
		leiturista.setNumeroFone(inserirLeituristaActionForm.getTelefone());

		// Numero do IMEI
		if(inserirLeituristaActionForm.getNumeroImei() != null && !inserirLeituristaActionForm.getNumeroImei().equalsIgnoreCase("")){
			leiturista.setNumeroImei(new Long(inserirLeituristaActionForm.getNumeroImei()));
		}

		// Código DDD do Municipio
		leiturista.setCodigoDDD(inserirLeituristaActionForm.getDdd());

		FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
		Collection colecaoEmpresa = new ArrayList<Empresa>();

		// Empresa
		if(Util.validarNumeroMaiorQueZERO(inserirLeituristaActionForm.getEmpresaID())){

			// Constrói o filtro para pesquisa da Empresa
			filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.ID, inserirLeituristaActionForm.getEmpresaID()));

			colecaoEmpresa = (Collection) fachada.pesquisar(filtroEmpresa, Empresa.class.getName());

		}else{

			// Constrói o filtro para pesquisa da Empresa (Caso o funcionário seja da própria
			// empresa de sanea)
			filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.ID, ConstantesSistema.SIM));

			colecaoEmpresa = (Collection) fachada.pesquisar(filtroEmpresa, Empresa.class.getName());
		}

		// setando
		leiturista.setEmpresa((Empresa) colecaoEmpresa.iterator().next());

		// Ultima alteração
		leiturista.setUltimaAlteracao(new Date());
		leiturista.setIndicadorUso(new Short("1"));

		// Insere um Leiturista - Agente Comercial - na base, mas fazendo, antes,
		// algumas verificações no ControladorMicromediçãoSEJB.
		fachada.inserirLeiturista(leiturista, usuarioLogado);

		// Exibe a página de sucesso
		montarPaginaSucesso(httpServletRequest, "Agente Comercial " + leiturista.getId() + " inserido com sucesso.",
						"Inserir outro Agente Comercial", "exibirInserirLeituristaAction.do?menu=sim",
						"exibirAtualizarLeituristaAction.do?idRegistroAtualizacao=" + leiturista.getId(),
						"Atualizar Agente Comercial Inserido");

		return retorno;

	}

}