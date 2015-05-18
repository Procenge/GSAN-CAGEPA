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

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.*;
import gcom.atendimentopublico.registroatendimento.bean.ObterDadosRegistroAtendimentoHelper;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
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
 * @author Gicevalter Couto
 * @created 17/10/2014
 */
public class CancelarRegistroAtendimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta Retorno (Forward = Sucesso)
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Form
		EncerrarRegistroAtendimentoActionForm form = (EncerrarRegistroAtendimentoActionForm) actionForm;

		Date dataInicio = new Date();

		// Fachada
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_REGISTRO_ATENDIMENTO_CANCELAR,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_REGISTRO_ATENDIMENTO_CANCELAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		// Tramite
		RegistroAtendimento registroAtendimento = null;
		RegistroAtendimentoUnidade registroAtendimentoUnidade = null;

		if(Integer.valueOf(form.getMotivoEncerramentoId()).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
			String[] numerosRas = form.getNumerosRAs().split(",");
			Collection<RegistroAtendimento> colecaoRegistroAtendimento = new ArrayList<RegistroAtendimento>();
			Collection<RegistroAtendimentoUnidade> colecaoRegistroAtendimentoUnidade = new ArrayList<RegistroAtendimentoUnidade>();

			// Registro de Atendimento
			for(String numeroRegistroAtendimento : numerosRas){
				ObterDadosRegistroAtendimentoHelper registroAtendimentoHelper = fachada.obterDadosRegistroAtendimento(new Integer(
								numeroRegistroAtendimento));

				registroAtendimento = new RegistroAtendimento();

				registroAtendimento = this.getDadosEncerramento(registroAtendimentoHelper, form);
				registroAtendimentoUnidade = this.getDadosRegistroAtendimentoUnidade(usuario, registroAtendimento, form);

				// Faz as validações de tramitação
				fachada.validarEncerramentoRA(registroAtendimento);

				colecaoRegistroAtendimento.add(registroAtendimento);
				colecaoRegistroAtendimentoUnidade.add(registroAtendimentoUnidade);
			}

			// Encerrar RA
			fachada.encerrarRegistroAtendimento(colecaoRegistroAtendimento, colecaoRegistroAtendimentoUnidade, usuario);

			// Setando Operação
			registroAtendimento.setOperacaoEfetuada(operacaoEfetuada);
			registroAtendimento.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(registroAtendimento);

			// Setando Operação
			registroAtendimentoUnidade.setOperacaoEfetuada(operacaoEfetuada);
			registroAtendimentoUnidade.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(registroAtendimentoUnidade);

			String iRegistroAtendimentosCanceladas = "";
			String criterio = "";
			for(RegistroAtendimentoUnidade registroAtendimentoUnidadeEncerrada : colecaoRegistroAtendimentoUnidade){

				iRegistroAtendimentosCanceladas = iRegistroAtendimentosCanceladas + criterio + registroAtendimentoUnidadeEncerrada.getId();
				criterio = ", ";
			}

			// [FS008] Monta a página de sucesso
			montarPaginaSucesso(httpServletRequest, "RA - Registro de Atendimento " + iRegistroAtendimentosCanceladas
							+ " encerrado com sucesso!", "Manter RA - Registro de Atendimento",
							"exibirFiltrarRegistroAtendimentoAction.do?menuPrincipal=sim&menu=sim",
							"exibirConsultarRegistroAtendimentoAction.do", "Voltar");

			sessao.removeAttribute("idRegistroAtendimento");
		}

		// TODO Rodrigo Oliveira Remover syout testeLentidaoRA
		System.out.println("testeLentidaoRA ## " + " ## EncerrarRegistroAtendimentoAction -> tempo do método execute: "
						+ Util.diferencaSegundos(dataInicio, new Date()) + " s ");
		System.out.println("testeLentidaoRA Número RA: " + form.getNumeroRA());

		return retorno;
	}

	private RegistroAtendimento getDadosEncerramento(ObterDadosRegistroAtendimentoHelper registroAtendimentoHelper,
					EncerrarRegistroAtendimentoActionForm form){

		RegistroAtendimento registroAtendimento = new RegistroAtendimento();

		// Registro de Atendimento
		registroAtendimento.setId(Integer.valueOf(registroAtendimentoHelper.getRegistroAtendimento().getId()));

		Date dataAtendimento = registroAtendimentoHelper.getRegistroAtendimento().getRegistroAtendimento();
		if(dataAtendimento != null){
			registroAtendimento.setRegistroAtendimento(Util.converteStringParaDateHora(Util.formatarData(dataAtendimento) + " "
							+ Util.formatarHoraSemData(dataAtendimento) + ":00"));
		}

		AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = this.consultaAtendimentoMotivoEncerramento(Integer.valueOf(form
						.getMotivoEncerramentoId()));

		registroAtendimento.setAtendimentoMotivoEncerramento(atendimentoMotivoEncerramento);

		registroAtendimento.setRegistroAtendimentoDuplicidade(null);
		registroAtendimento.setDataEncerramento(Util.converteStringParaDateHora(form.getDataEncerramento() + " "
						+ form.getHoraEncerramento() + ":00"));

		if(form.getParecerEncerramento() != null && !form.getParecerEncerramento().equals("")){
			registroAtendimento.setParecerEncerramento(form.getParecerEncerramento().toUpperCase());
		}
		registroAtendimento.setUltimaAlteracao(new Date());

		return registroAtendimento;
	}

	/**
	 * Carrega Registro Atendimento Unidade a partir do usuário logado.
	 * 
	 * @author Leonardo Regis
	 * @date 26/08/2006
	 * @param usuario
	 * @param registroAtendimento
	 */
	private RegistroAtendimentoUnidade getDadosRegistroAtendimentoUnidade(Usuario usuario, RegistroAtendimento registroAtendimento,
					EncerrarRegistroAtendimentoActionForm form){

		RegistroAtendimentoUnidade registroAtendimentoUnidade = new RegistroAtendimentoUnidade();

		registroAtendimentoUnidade.setRegistroAtendimento(registroAtendimento);
		registroAtendimentoUnidade.setUnidadeOrganizacional(usuario.getUnidadeOrganizacional());
		registroAtendimentoUnidade.setUsuario(usuario);
		AtendimentoRelacaoTipo atendimentoRelacaoTipo = new AtendimentoRelacaoTipo();
		atendimentoRelacaoTipo.setId(AtendimentoRelacaoTipo.ENCERRAR);
		registroAtendimentoUnidade.setAtendimentoRelacaoTipo(atendimentoRelacaoTipo);
		registroAtendimentoUnidade.setUltimaAlteracao(new Date());

		return registroAtendimentoUnidade;
	}

	/**
	 * Consulta Atendimento Motivo Encerramento
	 * 
	 * @author Rafael Pinto
	 * @date 18/10/2006
	 * @param id
	 *            do Atendimento Motivo Encerramento
	 */
	private AtendimentoMotivoEncerramento consultaAtendimentoMotivoEncerramento(Integer id){

		FiltroAtendimentoMotivoEncerramento filtroAtendimentoMotivoEncerramento = new FiltroAtendimentoMotivoEncerramento();

		filtroAtendimentoMotivoEncerramento.adicionarParametro(new ParametroSimples(FiltroAtendimentoMotivoEncerramento.ID, id));

		Collection colecao = Fachada.getInstancia().pesquisar(filtroAtendimentoMotivoEncerramento,
						AtendimentoMotivoEncerramento.class.getName());

		AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = (AtendimentoMotivoEncerramento) Util.retonarObjetoDeColecao(colecao);

		return atendimentoMotivoEncerramento;
	}

}