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
 * 
 * GSANPCG
 * André Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * José Gilberto de França Matos
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. 
 * Consulte a Licença Pública Geral GNU para obter mais detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.bean.OSEncerramentoHelper;
import gcom.atendimentopublico.ordemservico.bean.ServicoAssociadoAutorizacaoHelper;
import gcom.gui.ActionServletException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.StrutsDispatchAction;
import br.com.procenge.util.Util;

/**
 * @author Virgínia Melo
 */
public class AutorizarServicoAssociadoAction
				extends StrutsDispatchAction {

	private static final String CAMPO_DESCRICAO_SERVICO_ASSOCIADO = "descricaoServicoAssociado";

	private static final String CAMPO_ID_EQUIPE_INFORMADA = "idEquipeInformada";

	private static final String CAMPO_DATA_PROGRAMACAO_INFORMADA = "dataProgramacaoInformada";

	private static final String CAMPO_ID_SERVICO_ASSOCIADO = "idServicoAssociado";

	private static final String CAMPO_EXECUCAO_AUTORIZADA = "execucaoAutorizada";

	private static final String CAMPO_TRAMITE_AUTORIZADO = "tramiteAutorizado";

	private static final String ATRIBUTO_SERVICOS_PARA_AUTORIZACAO = "servicosParaAutorizacao";

	private static final String ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER = "autorizarServicoAssociadoHelper";

	private static final String ATRIBUTO_MAP_SERVICOS_AUTORIZADOS = "mapServicosAutorizados";

	private static final String ATRIBUTO_POPUP = "popup";

	private static final String ATRIBUTO_ENCERRADO = "encerrado-";

	private static final String FORWARD_SUCESSO_POPUP = "sucessoPopup";

	/**
	 * Método responsável por exibir a tela de autorização dos serviços associados.
	 * 
	 * @autor Virgínia Melo
	 * @param mapping
	 * @param form
	 *            O formulário
	 * @param request
	 *            O objeto request
	 * @param response
	 *            O objeto response
	 * @return ActionForward O retorno da ação
	 * @throws Exception
	 *             Caso ocorra algum erro
	 */
	public ActionForward mostrarAutorizarServicoAssociado(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception{

		HttpSession sessao = request.getSession(false);
		AutorizarServicoAssociadoHelper autorizarServicoAssociadoHelper = null;
		ActionForward retorno = null;

		autorizarServicoAssociadoHelper = (AutorizarServicoAssociadoHelper) sessao
						.getAttribute(ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER);

		if(autorizarServicoAssociadoHelper != null){
			request.setAttribute(ATRIBUTO_SERVICOS_PARA_AUTORIZACAO, autorizarServicoAssociadoHelper.getServicosParaAutorizacao());
		}

		this.enviarOsEncerradasPorRequest(autorizarServicoAssociadoHelper, request);

		if(sessao.getAttribute("osProgramacao") != null){
			retorno = mapping.findForward(FORWARD_SUCESSO);
			request.setAttribute(ATRIBUTO_POPUP, "false");
		}else{
			retorno = mapping.findForward(FORWARD_SUCESSO_POPUP);
			request.setAttribute(ATRIBUTO_POPUP, "true");
		}

		super.saveToken(request);

		return retorno;
	}

	/**
	 * Método responsável por repassar os serviços que foram autorizados para o gerar/encerrar OS.
	 * 
	 * @autor Virgínia Melo
	 * @author Saulo Lima
	 * @date 03/06/2009 Alteração para se adaptar ao novo formato do
	 *       ServicoAssociadoAutorizacaoHelper.
	 * @param mapping
	 * @param form
	 *            O formulário
	 * @param request
	 *            O objeto request
	 * @param response
	 *            O objeto response
	 * @return ActionForward O retorno da ação
	 * @throws Exception
	 *             Caso ocorra algum erro
	 */
	public ActionForward autorizarServicoAssociado(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception{

		DynaActionForm dynaForm = (DynaActionForm) form;
		Integer[] arrIdServico = (Integer[]) dynaForm.get(CAMPO_ID_SERVICO_ASSOCIADO);
		String[] arrDataProgramacaoInformada = (String[]) dynaForm.get(CAMPO_DATA_PROGRAMACAO_INFORMADA);
		Integer[] arrIdEquipeInformada = (Integer[]) dynaForm.get(CAMPO_ID_EQUIPE_INFORMADA);
		String[] arrDescricaoServicoAssociado = (String[]) dynaForm.get(CAMPO_DESCRICAO_SERVICO_ASSOCIADO);

		// Valida o token para garantir que não aconteça um duplo submit.
		validarToken(request);

		HttpSession sessao = request.getSession(false);
		AutorizarServicoAssociadoHelper autorizarServicoAssociadoHelper = (AutorizarServicoAssociadoHelper) sessao
						.getAttribute(ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER);

		Map<Integer, ServicoAssociadoAutorizacaoHelper> mapServicosAutorizados = new HashMap<Integer, ServicoAssociadoAutorizacaoHelper>();

		for(int i = 0; i < arrIdServico.length; i++){
			ServicoAssociadoAutorizacaoHelper servicoAutorizado = new ServicoAssociadoAutorizacaoHelper();

			servicoAutorizado.setIdServicoAssociado(arrIdServico[i]);
			servicoAutorizado.setDescricaoServicoAssociado(arrDescricaoServicoAssociado[i]);

			servicoAutorizado.setExecucaoAutorizada(obterValorCheckbox(request, CAMPO_EXECUCAO_AUTORIZADA, arrIdServico[i]));

			if(arrDataProgramacaoInformada[i] != null && arrDataProgramacaoInformada[i].length() > 0){
				servicoAutorizado.setDataProgramacaoInformada(Util.converterCampoStringParaData(
								Constantes.SERVICO_ASSOCIADO_ROTULO_DATA_PROGRAMACAO, arrDataProgramacaoInformada[i],
								Constantes.FORMATO_DATA_BR));
			}

			servicoAutorizado.setIdEquipeInformada(arrIdEquipeInformada[i]);

			servicoAutorizado.setTramiteAutorizado(obterValorCheckbox(request, CAMPO_TRAMITE_AUTORIZADO, arrIdServico[i]));

			mapServicosAutorizados.put(arrIdServico[i], servicoAutorizado);
		}

		// Pegar os 'dados de encerramento' das 'OSs Associadas' e adicionar no 'Mapa de Serviços
		// Autorizados'
		Map<Integer, OSEncerramentoHelper> mapaServicosAssociadosEncerrados = autorizarServicoAssociadoHelper
						.getMapaServicosAssociadosEncerrados();
		Set<Integer> servicosTipoIds = mapServicosAutorizados.keySet();
		for(Integer idServicoTipo : servicosTipoIds){

			if(!gcom.util.Util.isVazioOrNulo(autorizarServicoAssociadoHelper.getServicosParaAutorizacao())){

				for(ServicoAssociadoAutorizacaoHelper autorizacaoHelper : autorizarServicoAssociadoHelper.getServicosParaAutorizacao()){

					if(autorizacaoHelper.getIdServicoAssociado().equals(idServicoTipo)){

						if(!autorizacaoHelper.getFormaEncerramentoHelper().isAutomatica()){

							// Caso tenha informado os dados de encerramento, adicionar ao objeto
							// correto.
							if(mapaServicosAssociadosEncerrados != null && mapaServicosAssociadosEncerrados.containsKey(idServicoTipo)){
								OSEncerramentoHelper oSEncerramentoHelper = mapaServicosAssociadosEncerrados.get(idServicoTipo);
								mapServicosAutorizados.get(idServicoTipo).setOSEncerramentoHelper(oSEncerramentoHelper);
								mapServicosAutorizados.get(idServicoTipo).setEncerramentoAutorizado(true);

								// Caso não tenha informado os dados de encerramento e o mesmo seja
								// AUTOMATICO,
								// exibir mensagem p/ usuário
							}else{

								ArrayList<ServicoAssociadoAutorizacaoHelper> listaAutorizacao = (ArrayList<ServicoAssociadoAutorizacaoHelper>) autorizarServicoAssociadoHelper
												.getServicosParaAutorizacao();

								for(ServicoAssociadoAutorizacaoHelper servicoAssociadoAutorizacaoHelper : listaAutorizacao){

									if(servicoAssociadoAutorizacaoHelper.getIdServicoAssociado().equals(idServicoTipo)
													&& servicoAssociadoAutorizacaoHelper.getFormaEncerramentoHelper().isAutomatica()){
										ActionServletException ex = new ActionServletException(
														"atencao.os_associada.encerramento_automatico", null,
														servicoAssociadoAutorizacaoHelper.getDescricaoServicoAssociado());
										ex.setUrlBotaoVoltar("/gsan/encerrarOrdemServicoAction.do");
										throw ex;
									}
								}
							}
						}
					}
				}
			}
		}

		if(!mapServicosAutorizados.isEmpty()){

			autorizarServicoAssociadoHelper.setMapServicosAutorizados(mapServicosAutorizados);
			request.setAttribute(ATRIBUTO_MAP_SERVICOS_AUTORIZADOS, mapServicosAutorizados);
			sessao.setAttribute(ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER, autorizarServicoAssociadoHelper);
		}

		this.enviarParametrosPorRequest(autorizarServicoAssociadoHelper.getParametrosCaminhoAutorizacao(), request);

		return mapping.findForward(autorizarServicoAssociadoHelper.getCaminhoAutorizacao());
	}

	/**
	 * Método responsável por cancelar a autorização.
	 * 
	 * @autor virginia
	 * @param mapping
	 * @param form
	 *            O formulário
	 * @param request
	 *            O objeto request
	 * @param response
	 *            O objeto response
	 * @return ActionForward O retorno da ação
	 * @throws Exception
	 *             Caso ocorra algum erro
	 */
	public ActionForward cancelarAutorizarServicoAssociado(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception{

		HttpSession sessao = request.getSession(false);
		AutorizarServicoAssociadoHelper autorizarServicoAssociadoHelper = (AutorizarServicoAssociadoHelper) sessao
						.getAttribute(ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER);

		this.enviarParametrosPorRequest(autorizarServicoAssociadoHelper.getParametrosCaminhoRetorno(), request);

		return mapping.findForward(autorizarServicoAssociadoHelper.getCaminhoRetorno());
	}

	private void enviarParametrosPorRequest(Map<String, Object> parametros, HttpServletRequest request){

		Entry parametro = null;

		if(parametros != null && !parametros.isEmpty()){
			for(Iterator it = parametros.entrySet().iterator(); it.hasNext();){
				parametro = (Entry) it.next();
				request.setAttribute((String) parametro.getKey(), parametro.getValue());
			}
		}
	}

	private Boolean obterValorCheckbox(HttpServletRequest request, String campo, Integer idServicoAssociado){

		String campoCheck = null;
		String campoValor = null;
		Boolean retorno = Boolean.FALSE;

		campoCheck = campo + idServicoAssociado.toString();
		campoValor = request.getParameter(campoCheck);

		if(campoValor != null){
			retorno = Boolean.valueOf(campoValor);
		}

		return retorno;
	}

	private void enviarOsEncerradasPorRequest(AutorizarServicoAssociadoHelper autorizarServicoAssociadoHelper, HttpServletRequest request){

		Entry<Integer, OSEncerramentoHelper> entry = null;

		if(autorizarServicoAssociadoHelper != null){
			Map<Integer, OSEncerramentoHelper> mapaIdServicoTipoOSEncerramento = autorizarServicoAssociadoHelper
							.getMapaServicosAssociadosEncerrados();

			if(mapaIdServicoTipoOSEncerramento != null){
				for(Iterator<Entry<Integer, OSEncerramentoHelper>> it = mapaIdServicoTipoOSEncerramento.entrySet().iterator(); it.hasNext();){
					entry = (Entry<Integer, OSEncerramentoHelper>) it.next();
					request.setAttribute(ATRIBUTO_ENCERRADO + entry.getKey(), Boolean.TRUE);
				}
			}
		}
	}

}
