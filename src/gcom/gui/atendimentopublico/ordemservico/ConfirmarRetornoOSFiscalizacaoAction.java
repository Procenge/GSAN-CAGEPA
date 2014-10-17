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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.EventoGeracaoServico;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.OrigemEncerramentoOrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.ordemservico.bean.OSEncerramentoHelper;
import gcom.atendimentopublico.ordemservico.bean.ServicoAssociadoAutorizacaoHelper;
import gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 06/03/2007
 */
public class ConfirmarRetornoOSFiscalizacaoAction
				extends GcomAction {

	private static final String ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER = "autorizarServicoAssociadoHelper";

	private static final String CAMINHO_ENCERRAR_ORDEM_SERVICO = "confirmarRetornoOSFiscalizacaoAction";

	private static final String CAMINHO_RETORNO_CONSULTAR_OS = "exibirInformarRetornoOSFiscalizacaoAction"; // TODO

	// confirmar
	// esse
	// valor

	/**
	 * [UC0448] Informar Retorno Ordem de Fiscaliza��o
	 * 
	 * @author Saulo Lima
	 * @date 20/05/2009
	 *       Altera��o para autorizar OS's associadas, quando for o caso
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("confirmarRetornoOSFiscalizacao");
		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		String idOS = (String) sessao.getAttribute("idOS");

		Integer numeroOS = new Integer(idOS);
		String msgFinal = "";

		if(sessao.getAttribute("msgFinal") != null){
			msgFinal = (String) sessao.getAttribute("msgFinal");
		}

		AutorizarServicoAssociadoHelper autorizarServicoAssociadoHelper = null;

		Map<Integer, ServicoAssociadoAutorizacaoHelper> mapServicosAutorizados = (Map<Integer, ServicoAssociadoAutorizacaoHelper>) httpServletRequest
						.getAttribute("mapServicosAutorizados");

		if(mapServicosAutorizados == null || mapServicosAutorizados.isEmpty()){

			if(httpServletRequest.getParameter("confirmado") != null){

				if(httpServletRequest.getParameter("confirmado").equalsIgnoreCase("sim")){

					OrdemServico ordemServico = fachada.consultarDadosOrdemServico(numeroOS);
					ServicoTipo servicoTipo = ordemServico.getServicoTipo();

					Date dataAtual = new Date();
					String idMotivoEncerramento = AtendimentoMotivoEncerramento.CONCLUSAO_SERVICO.toString();

					OSEncerramentoHelper osEncerramentoHelper = new OSEncerramentoHelper();
					osEncerramentoHelper.setNumeroOS(numeroOS);
					osEncerramentoHelper.setDataExecucao(dataAtual);
					osEncerramentoHelper.setUsuarioLogado(usuario);
					osEncerramentoHelper.setIdMotivoEncerramento(idMotivoEncerramento);
					osEncerramentoHelper.setUltimaAlteracao(dataAtual);
					osEncerramentoHelper.setParecerEncerramento("ORDEM DE SERVICO ENCERRADA ATRAVES DA FUNCIONALIDADE DE FISCALIZACAO.");
					osEncerramentoHelper.setIndicadorPavimento(ServicoTipo.INDICADOR_PAVIMENTO_NAO);
					osEncerramentoHelper.setIndicadorVistoriaServicoTipo(ServicoTipo.INDICADOR_VISTORIA_SERVICO_TIPO_NAO);

					AtendimentoMotivoEncerramento motivoEncerramento = fachada.pesquisarAtendimentoMotivoEncerramentoPorId(Integer
									.valueOf(idMotivoEncerramento));

					// Se houve execu��o, verificar se existem Servi�os Associados a serem
					// autorizados
					if(motivoEncerramento.getIndicadorExecucao() == AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_SIM){

						List<ServicoAssociadoAutorizacaoHelper> servicoAssociadoAutorizacaoHelperList = fachada
										.verificarServicosAssociadosParaAutorizacao(servicoTipo,
														EventoGeracaoServico.ENCERRAMENTO_ORDEM_SERVICO,
														OrigemEncerramentoOrdemServico.ENCERRAMENTO_ORDEM_SERVICO, numeroOS);

						if(servicoAssociadoAutorizacaoHelperList != null && !servicoAssociadoAutorizacaoHelperList.isEmpty()){

							// Parametros obrigatorios para a tela de autoriza��o
							autorizarServicoAssociadoHelper = new AutorizarServicoAssociadoHelper();

							Map<String, Object> parametrosCaminhoAutorizacao = new HashMap<String, Object>();
							parametrosCaminhoAutorizacao.put("osEncerramentoHelper", osEncerramentoHelper);
							autorizarServicoAssociadoHelper.setParametrosCaminhoAutorizacao(parametrosCaminhoAutorizacao);
							autorizarServicoAssociadoHelper.setCaminhoAutorizacao(CAMINHO_ENCERRAR_ORDEM_SERVICO);

							Map<String, Object> parametrosCaminhoRetorno = new HashMap<String, Object>();
							parametrosCaminhoRetorno.put("numeroOS", numeroOS);
							autorizarServicoAssociadoHelper.setParametrosCaminhoRetorno(parametrosCaminhoRetorno);
							autorizarServicoAssociadoHelper.setCaminhoRetorno(CAMINHO_RETORNO_CONSULTAR_OS);

							autorizarServicoAssociadoHelper.setServicosParaAutorizacao(servicoAssociadoAutorizacaoHelperList);

							sessao.setAttribute(ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER, autorizarServicoAssociadoHelper);

							retorno = actionMapping.findForward("mostrarAutorizarServicoAssociado");

							return retorno;

						}
					}

					fachada.encerrarOSComExecucaoSemReferencia(osEncerramentoHelper, null,
									OrigemEncerramentoOrdemServico.ENCERRAMENTO_ORDEM_SERVICO, null);

					retorno = actionMapping.findForward("telaSucesso");

				}else{

					if(httpServletRequest.getParameter("confirmado").equalsIgnoreCase("nao")){
						retorno = actionMapping.findForward("telaSucesso");
						// montando p�gina de sucesso
					}
				}
			}

		}else{

			autorizarServicoAssociadoHelper = (AutorizarServicoAssociadoHelper) sessao
							.getAttribute(ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER);
			Map<String, Object> parametrosCaminhoAutorizacao = autorizarServicoAssociadoHelper.getParametrosCaminhoAutorizacao();
			OSEncerramentoHelper osEncerramentoHelper = (OSEncerramentoHelper) parametrosCaminhoAutorizacao.get("osEncerramentoHelper");

			// [SB0002] - Encerrar com execu��o e sem refer�ncia
			fachada.encerrarOSComExecucaoSemReferencia(osEncerramentoHelper, mapServicosAutorizados,
							OrigemEncerramentoOrdemServico.ENCERRAMENTO_ORDEM_SERVICO, null);
		}

		montarPaginaSucesso(httpServletRequest, msgFinal, "Informar outra fiscaliza��o",
						"/exibirInformarRetornoOSFiscalizacaoAction.do?menu=sim");

		return retorno;
	}
}
