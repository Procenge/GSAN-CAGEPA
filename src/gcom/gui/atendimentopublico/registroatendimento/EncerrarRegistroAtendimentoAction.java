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

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.*;
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

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0435] Encerrar Registro Atendimento
 * 
 * @author Leonardo Regis
 * @created 24 de Julho de 2006
 */
public class EncerrarRegistroAtendimentoAction
				extends GcomAction {

	/**
	 * [UC0435] Encerrar Registro Atendimento
	 * [UC0107] Registrar Transa��o
	 * 
	 * @author Leonardo Regis
	 * @date 18/08/2006
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return forward
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta Retorno (Forward = Sucesso)
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Form
		EncerrarRegistroAtendimentoActionForm encerrarRegistroAtendimentoActionForm = (EncerrarRegistroAtendimentoActionForm) actionForm;

		// TODO Rodrigo Oliveira Remover syout testeLentidaoRA
		// testeLentidaoRA
		Date dataInicio = new Date();

		// Fachada
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		if(httpServletRequest.getParameter("veioDeInserir") != null && httpServletRequest.getParameter("veioDeInserir").equals("true")){
			encerrarRegistroAtendimentoActionForm = recuperarDados(httpServletRequest);
		}

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// [UC0107] Registrar Transa��o
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_REGISTRO_ATENDIMENTO_ENCERRAR,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_REGISTRO_ATENDIMENTO_ENCERRAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		// Tramite
		RegistroAtendimento registroAtendimento = null;
		RegistroAtendimentoUnidade registroAtendimentoUnidade = null;

		if(Integer.valueOf(encerrarRegistroAtendimentoActionForm.getMotivoEncerramentoId()).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){

			// Recupera informa��es do tr�mite
			registroAtendimento = this.getDadosEncerramento(encerrarRegistroAtendimentoActionForm);
			registroAtendimentoUnidade = this.getDadosRegistroAtendimentoUnidade(usuario, registroAtendimento,
							encerrarRegistroAtendimentoActionForm);

			// Faz as valida��es de tramita��o
			fachada.validarEncerramentoRA(registroAtendimento);

			// Encerrar RA
			fachada.encerrarRegistroAtendimento(registroAtendimento, registroAtendimentoUnidade, usuario);

			// Setando Opera��o
			registroAtendimento.setOperacaoEfetuada(operacaoEfetuada);
			registroAtendimento.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(registroAtendimento);

			// Setando Opera��o
			registroAtendimentoUnidade.setOperacaoEfetuada(operacaoEfetuada);
			registroAtendimentoUnidade.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(registroAtendimentoUnidade);

			// [FS008] Monta a p�gina de sucesso
			montarPaginaSucesso(httpServletRequest, "RA - Registro de Atendimento " + registroAtendimento.getId()
							+ " encerrado com sucesso!", "Manter RA - Registro de Atendimento",
							"exibirFiltrarRegistroAtendimentoAction.do?menu=sim",
							"exibirConsultarRegistroAtendimentoAction.do?numeroRA=" + registroAtendimento.getId().toString(), "Voltar");

			sessao.removeAttribute("idRegistroAtendimento");
		}

		// TODO Rodrigo Oliveira Remover syout testeLentidaoRA
		System.out.println("testeLentidaoRA ## " + " ## EncerrarRegistroAtendimentoAction -> tempo do m�todo execute: "
						+ Util.diferencaSegundos(dataInicio, new Date()) + " s ");
		System.out.println("testeLentidaoRA N�mero RA: " + encerrarRegistroAtendimentoActionForm.getNumeroRA());

		return retorno;
	}

	private EncerrarRegistroAtendimentoActionForm recuperarDados(HttpServletRequest request){

		EncerrarRegistroAtendimentoActionForm form = new EncerrarRegistroAtendimentoActionForm();
		form.setNumeroRA((String) request.getParameter("idRegistroAtendimento"));
		form.setDataAtendimento((String) request.getParameter("dataAtendimento"));
		form.setHoraAtendimento((String) request.getParameter("horaAtendimento"));
		form.setMotivoEncerramentoId("" + AtendimentoMotivoEncerramento.ENCERRAMENTO_AUTOMATICO);
		form.setDataEncerramento(Util.formatarData(new Date()));
		form.setHoraEncerramento(Util.formatarHoraSemSegundos(new Date()));
		form.setParecerEncerramento("RA Imediata com encerramento autom�tico");
		form.setDataConcorrenciaRA(new Date());
		return form;
	}

	/**
	 * Carrega Tr�mite com informa��es vindas da tela
	 * 
	 * @author Leonardo Regis
	 * @date 18/08/2006
	 * @author Saulo Lima
	 * @date 08/02/2009
	 *       Caso a hora de atendimento esteja no formato 'HH:mm', colocar no formato 'HH:mm:00'
	 *       antes de converter para Date.
	 * @param form
	 */
	private RegistroAtendimento getDadosEncerramento(EncerrarRegistroAtendimentoActionForm form){

		RegistroAtendimento registroAtendimento = new RegistroAtendimento();

		// Registro de Atendimento
		registroAtendimento.setId(Integer.valueOf(form.getNumeroRA()));

		if(form.getHoraAtendimento() != null && form.getHoraAtendimento().trim().length() > 5){
			registroAtendimento.setRegistroAtendimento(Util.converteStringParaDateHora(form.getDataAtendimento() + " "
							+ form.getHoraAtendimento()));
		}else{
			registroAtendimento.setRegistroAtendimento(Util.converteStringParaDateHora(form.getDataAtendimento() + " "
							+ form.getHoraAtendimento() + ":00"));
		}

		AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = this.consultaAtendimentoMotivoEncerramento(Integer.valueOf(form
						.getMotivoEncerramentoId()));

		registroAtendimento.setAtendimentoMotivoEncerramento(atendimentoMotivoEncerramento);

		RegistroAtendimento registroAtendimentoDuplicidade = null;
		if(form.getNumeroRAReferencia() != null && !form.getNumeroRAReferencia().equals("")){
			registroAtendimentoDuplicidade = new RegistroAtendimento();
			registroAtendimentoDuplicidade.setId(Integer.valueOf(form.getNumeroRAReferencia()));
		}

		registroAtendimento.setRegistroAtendimentoDuplicidade(registroAtendimentoDuplicidade);
		registroAtendimento.setDataEncerramento(Util.converteStringParaDateHora(form.getDataEncerramento() + " "
						+ form.getHoraEncerramento() + ":00"));

		if(form.getParecerEncerramento() != null && !form.getParecerEncerramento().equals("")){
			registroAtendimento.setParecerEncerramento(form.getParecerEncerramento().toUpperCase());
		}
		registroAtendimento.setUltimaAlteracao(new Date());

		return registroAtendimento;
	}

	/**
	 * Carrega Registro Atendimento Unidade a partir do usu�rio logado.
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
		// registroAtendimentoUnidade.setUltimaAlteracao(form.getDataConcorrenciaRA());
		// Alterado por: Yara Souza
		// Data: 13/05/2010
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