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
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
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
 * [UC0371] Inserir Equipe
 * 
 * @author Leonardo Regis
 * @created 24 de Julho de 2006
 */
public class TramitarRegistroAtendimentoAction
				extends GcomAction {

	/**
	 * [UC0371] Inserir Equipe
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
		TramitarRegistroAtendimentoActionForm tramitarRegistroAtendimentoActionForm = (TramitarRegistroAtendimentoActionForm) actionForm;
		// Fachada
		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		RegistroAtendimento registroAtendimentoBackup = null;

		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		/*
		 * [UC0107] Registrar Transa��o
		 */
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_REGISTRO_ATENDIMENTO_TRAMITAR,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_REGISTRO_ATENDIMENTO_TRAMITAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// [UC0107] -Fim- Registrar Transa��o

		// Tramite
		Tramite tramite = null;
		if(tramitarRegistroAtendimentoActionForm.getUnidadeDestinoId() != null){
			// Recupera informa��es do tr�mite
			tramite = getTramite(tramitarRegistroAtendimentoActionForm, fachada);

			// Se ocorrer o tramite em conjunto com a Ordem de Servi�o � necess�rio fazer um backup
			// do Registro de Atendimento pois o mesmo � perdido na tramita��o da OS
			registroAtendimentoBackup = tramite.getRegistroAtendimento();

			// Verifica se ser�o tramitados tamb�m as OS�s
			if(tramitarRegistroAtendimentoActionForm.getIndicadorTramiteApenasRA().equals(ConstantesSistema.NAO.toString())){
				// Faz as valida��es de tramita��o
				fachada.validarTramitacao(tramite, usuario);

				fachada.tramitarRAOS(tramite, tramitarRegistroAtendimentoActionForm.getDataConcorrenciaRA(), usuario);

				// Devolve o registro de atendimento armazenado antes da tramitacao
				tramite.setRegistroAtendimento(registroAtendimentoBackup);
			}else{
				// Faz as valida��es de tramita��o
				fachada.validarTramitacao(tramite, usuario);
				// Insere Tr�mite
				// Independente da OS
				fachada.tramitar(tramite, tramitarRegistroAtendimentoActionForm.getDataConcorrenciaRA());
			}

			// Setando Opera��o
			tramite.setOperacaoEfetuada(operacaoEfetuada);
			tramite.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(tramite);
			// [FS008] Monta a p�gina de sucesso
			montarPaginaSucesso(httpServletRequest, "RA - Registro de Atendimento " + tramite.getRegistroAtendimento().getId()
							+ " tramitado com sucesso!", "Efetuar outra Tramita��o do RA - Registro de Atendimento",
							"exibirTramitarRegistroAtendimentoAction.do?resetarTramitacao=true&numeroRA="
											+ tramite.getRegistroAtendimento().getId(),
							"exibirConsultarRegistroAtendimentoAction.do?numeroRA=" + tramite.getRegistroAtendimento().getId().toString(),
							"Voltar");
		}
		return retorno;
	}

	/**
	 * Carrega Tr�mite com informa��es vindas da tela
	 * 
	 * @author Leonardo Regis
	 * @date 18/08/2006
	 * @param form
	 */
	private Tramite getTramite(TramitarRegistroAtendimentoActionForm form, Fachada fachada){

		Tramite tramite = new Tramite();
		// Unidade Origem
		UnidadeOrganizacional unidadeOrigem = null;
		if(form.getUnidadeAtualId() != null && !form.getUnidadeAtualId().equals("")){
			unidadeOrigem = new UnidadeOrganizacional();
			unidadeOrigem.setId(new Integer(form.getUnidadeAtualId()));
			UnidadeTipo unidadeTipoOrigem = null;
			if(form.getUnidadeAtualCodigoTipo() != null && !form.getUnidadeAtualCodigoTipo().equals("")){
				unidadeTipoOrigem = new UnidadeTipo();
				unidadeTipoOrigem.setCodigoTipo(form.getUnidadeAtualCodigoTipo());
			}
			unidadeOrigem.setUnidadeTipo(unidadeTipoOrigem);
		}
		UnidadeOrganizacional unidadeCentralizadora = null;
		if(form.getUnidadeAtualIdCentralizadora() != null && !form.getUnidadeAtualIdCentralizadora().equals("")){
			unidadeCentralizadora = new UnidadeOrganizacional();
			unidadeCentralizadora.setId(new Integer(form.getUnidadeAtualIdCentralizadora()));
		}
		unidadeOrigem.setUnidadeCentralizadora(unidadeCentralizadora);
		tramite.setUnidadeOrganizacionalOrigem(unidadeOrigem);
		// Unidade Destino
		UnidadeOrganizacional unidadeDestino = null;
		// unidadeDestino.setId(new Integer(form.getUnidadeDestinoId()));
		// Filtra Unidade de Destino
		FiltroUnidadeOrganizacional filtroUnidadeDestino = new FiltroUnidadeOrganizacional();
		filtroUnidadeDestino.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, form.getUnidadeDestinoId()));
		filtroUnidadeDestino.adicionarCaminhoParaCarregamentoEntidade("unidadeTipo");
		// Recupera Unidade de Destino
		Collection colecaoUnidadeDestino = fachada.pesquisar(filtroUnidadeDestino, UnidadeOrganizacional.class.getName());
		if(colecaoUnidadeDestino != null && !colecaoUnidadeDestino.isEmpty()){
			unidadeDestino = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeDestino);
		}else{
			// levanta a exce��o para a pr�xima camada
			throw new ActionServletException("atencao.data.invalida", null, "Unidade Destino");
		}

		/*
		 * if(form.getUnidadeDestinoIndicadorTramite() != null &&
		 * !form.getUnidadeDestinoIndicadorTramite().equals("")){
		 * unidadeDestino.setIndicadorTramite(new Short(form.getUnidadeDestinoIndicadorTramite()));
		 * }
		 * UnidadeTipo unidadeTipo = new UnidadeTipo();
		 * unidadeTipo.setCodigoTipo(form.getUnidadeDestinoCodigoTipo());
		 * unidadeDestino.setUnidadeTipo(unidadeTipo);
		 */
		tramite.setUnidadeOrganizacionalDestino(unidadeDestino);
		// Registro de Atendimento
		RegistroAtendimento registroAtendimento = new RegistroAtendimento();
		registroAtendimento.setId(new Integer(form.getNumeroRA()));
		registroAtendimento.setCodigoSituacao(new Short(form.getCodigoSituacaoRA()));
		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();
		solicitacaoTipoEspecificacao.setId(new Integer(form.getEspecificacaoId()));
		SolicitacaoTipo solicitacaoTipo = new SolicitacaoTipo();
		solicitacaoTipo.setId(new Integer(form.getTipoSolicitacaoId()));
		solicitacaoTipo.setIndicadorTarifaSocial(new Short(form.getTipoSolicitacaoIndicadorTarifaSocial()));
		solicitacaoTipoEspecificacao.setSolicitacaoTipo(solicitacaoTipo);
		registroAtendimento.setSolicitacaoTipoEspecificacao(solicitacaoTipoEspecificacao);
		tramite.setRegistroAtendimento(registroAtendimento);
		// Usu�rio Registro
		Usuario usuarioRegistro = new Usuario();
		usuarioRegistro.setId(new Integer(form.getUsuarioRegistroId()));
		UnidadeOrganizacional unidadeOrganizacional = new UnidadeOrganizacional();
		unidadeOrganizacional.setId(new Integer(form.getUsuarioRegistroUnidade()));
		unidadeOrganizacional.setIndicadorTarifaSocial(new Short(form.getUsuarioRegistroUnidadeIndicadorTarifaSocial()));
		usuarioRegistro.setUnidadeOrganizacional(unidadeOrganizacional);
		tramite.setUsuarioRegistro(usuarioRegistro);
		// Usu�rio Respons�vel
		if(form.getUsuarioResponsavelId() != null && !form.getUsuarioResponsavelId().equals("")){
			Usuario usuarioResponsavel = new Usuario();
			usuarioResponsavel.setId(new Integer(form.getUsuarioResponsavelId()));
			tramite.setUsuarioResponsavel(usuarioResponsavel);
		}
		if(form.getParecerTramite() != null && !form.getParecerTramite().equals("")){
			tramite.setParecerTramite(form.getParecerTramite());
		}else{
			tramite.setParecerTramite(null);
		}
		tramite.setDataTramite(Util.converteStringParaDateHora(form.getDataTramite() + " " + form.getHoraTramite() + ":00"));
		tramite.setUltimaAlteracao(new Date());

		if(form.getMotivoTramite() != null && !form.getMotivoTramite().equals("-1") && !form.getMotivoTramite().equals("")){
			MotivoTramite motivoTramite = new MotivoTramite();
			motivoTramite.setId(new Integer(form.getMotivoTramite()));
			tramite.setMotivoTramite(motivoTramite);
		}

		return tramite;
	}
}