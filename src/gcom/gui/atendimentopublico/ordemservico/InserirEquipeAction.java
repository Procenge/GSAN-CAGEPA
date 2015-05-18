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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.Equipe;
import gcom.atendimentopublico.ordemservico.EquipeComponentes;
import gcom.atendimentopublico.ordemservico.EquipeTipo;
import gcom.atendimentopublico.ordemservico.ServicoPerfilTipo;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ConstantesSistema;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0371] Inserir Equipe
 * 
 * @author Leonardo Regis
 * @created 24 de Julho de 2006
 */
public class InserirEquipeAction
				extends GcomAction {

	/**
	 * [UC0371] Inserir Equipe
	 * [UC0107] Registrar Transação
	 * 
	 * @author Leonardo Regis
	 * @date 21/07/2006
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta Retorno (Forward = Sucesso)
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		// Form
		InserirEquipeActionForm inserirEquipeActionForm = (InserirEquipeActionForm) actionForm;
		// Fachada
		Fachada fachada = Fachada.getInstancia();
		// Sessão
		HttpSession sessao = httpServletRequest.getSession(false);
		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		// Registrando a operação
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_EQUIPE_INSERIR, new UsuarioAcaoUsuarioHelper(
						usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_EQUIPE_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		// Equipe
		Equipe equipe = null;
		if(inserirEquipeActionForm.getUnidadeOrganizacional() != null && inserirEquipeActionForm.getServicoPerfilTipo() != null){

			// Recupera informações da equipe
			equipe = getEquipe(inserirEquipeActionForm);

			// Faz as validações de inserção de equipe
			fachada.validarInsercaoEquipe(equipe);

			// Faz as validações de inserção de equipe componentes
			fachada.validarInsercaoEquipeComponentes(inserirEquipeActionForm.getEquipeComponentes(), equipe);

			// Insere Equipe
			long idEquipe = fachada.inserirEquipe(equipe);

			// Insere Equipe Componentes
			inserirEquipeComponentes(inserirEquipeActionForm, operacaoEfetuada, equipe, registradorOperacao, usuario);

			// Registra operação
			equipe.setOperacaoEfetuada(operacaoEfetuada);
			equipe.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(equipe);
			// [FS008] Monta a página de sucesso
			montarPaginaSucesso(httpServletRequest, "Inserção da Equipe " + equipe.getNome() + " efetuada com sucesso!",
							"Efetuar outra Inserção de Equipe", "exibirInserirEquipeAction.do?menu=sim",
							"exibirAtualizarEquipeAction.do?equipeID=" + idEquipe, "Atualizar Equipe Inserida");

		}
		return retorno;
	}

	/**
	 * Insere Coleção de Componentes devidamente validados na base
	 * 
	 * @author Leonardo Regis
	 * @date 29/07/2006
	 * @param inserirEquipeActionForm
	 * @param fachada
	 * @param equipe
	 */
	private void inserirEquipeComponentes(InserirEquipeActionForm inserirEquipeActionForm, OperacaoEfetuada operacaoEfetuada,
					Equipe equipe, RegistradorOperacao registradorOperacao, Usuario usuario){

		// Fachada
		Fachada fachada = Fachada.getInstancia();

		// Coleção de Componentes
		Collection colecaoEquipeComponentes = inserirEquipeActionForm.getEquipeComponentes();
		for(Iterator iter = colecaoEquipeComponentes.iterator(); iter.hasNext();){
			EquipeComponentes element = (EquipeComponentes) iter.next();
			element.setEquipe(equipe);
			// Registra operação
			element.setOperacaoEfetuada(operacaoEfetuada);
			element.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(element);
			// Insere componente
			fachada.inserirEquipeComponentes(element);
		}
	}

	/**
	 * Recupera Equipe com informações vindas da tela
	 * 
	 * @author Leonardo Regis
	 * @date 29/07/2006
	 * @param inserirEquipeActionForm
	 * @param fachada
	 * @param equipe
	 */
	private Equipe getEquipe(InserirEquipeActionForm inserirEquipeActionForm){

		Equipe equipe;
		// Cria objeto Equipe
		equipe = new Equipe();
		equipe.setNome(inserirEquipeActionForm.getNomeEquipe());
		equipe.setPlacaVeiculo(inserirEquipeActionForm.getPlacaVeiculo());
		// Seta valor da carga horária no banco convertido para minuto
		int cargaHoraria = Integer.parseInt(inserirEquipeActionForm.getCargaTrabalhoDia()) * 60;
		equipe.setCargaTrabalho(cargaHoraria);
		// Unidade Organizacional
		UnidadeOrganizacional unidadeOrganizacional = new UnidadeOrganizacional();
		unidadeOrganizacional.setId(new Integer(inserirEquipeActionForm.getUnidadeOrganizacionalId()));
		equipe.setUnidadeOrganizacional(unidadeOrganizacional);
		// Tipo Perfil Servico
		ServicoPerfilTipo servicoPerfilTipo = new ServicoPerfilTipo();
		servicoPerfilTipo.setId(new Integer(inserirEquipeActionForm.getTipoPerfilServicoId()));
		equipe.setServicoPerfilTipo(servicoPerfilTipo);
		equipe.setUltimaAlteracao(new Date());
		equipe.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
		if(inserirEquipeActionForm.getIdEquipeTipo() != null
						&& !inserirEquipeActionForm.getIdEquipeTipo().equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
			EquipeTipo equipeTipo = new EquipeTipo();
			equipeTipo.setId(inserirEquipeActionForm.getIdEquipeTipo());
			equipe.setEquipeTipo(equipeTipo);
		}
		return equipe;
	}
}
