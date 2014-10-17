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

import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.bean.OSEncerramentoHelper;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que recupera os dados da página de "Encerrar OS Associada".
 * 
 * @author Saulo Lima
 * @date 28/05/2009
 */
public class EncerrarOrdemServicoRetornarDadosAction
				extends GcomAction {

	private static final String ATRIBUTO_INTEGRACAO_COMERCIAL_HELPER = "integracaoComercialHelper";

	private static final String ATRIBUTO_USUARIO_LOGADO = "usuarioLogado";

	private static final String ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER = "autorizarServicoAssociadoHelper";

	private static final String PARAMETRO_ID_SERVICO_TIPO = "idServicoTipo";

	private static final String FORWARD_MOSTRAR_AUTORIZAR_SERVICO_ASSOCIADO = "mostrarAutorizarServicoAssociado";

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		HttpSession sessao = httpServletRequest.getSession(false);
		EncerrarOrdemServicoActionForm encerrarOrdemServicoActionForm = (EncerrarOrdemServicoActionForm) actionForm;
		ActionForward retorno = actionMapping.findForward(FORWARD_MOSTRAR_AUTORIZAR_SERVICO_ASSOCIADO);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute(ATRIBUTO_USUARIO_LOGADO);
		OSEncerramentoHelper osEncerramentoHelper = new OSEncerramentoHelper();

		String idServicoTipo = httpServletRequest.getParameter(PARAMETRO_ID_SERVICO_TIPO);
		if(idServicoTipo == null || idServicoTipo.equals("")){
			throw new ActionServletException("erro.servidor");
		}

		Integer numeroOS = Util.converterStringParaInteger(encerrarOrdemServicoActionForm.getNumeroOS());
		osEncerramentoHelper.setNumeroOS(numeroOS);

		Date dataEncerramento = null;
		if(encerrarOrdemServicoActionForm.getHoraEncerramento() == null || encerrarOrdemServicoActionForm.getHoraEncerramento().equals("")){
			dataEncerramento = Util.converteStringParaDate(encerrarOrdemServicoActionForm.getDataEncerramento());
		}else{
			dataEncerramento = Util.converteStringParaDateHora(encerrarOrdemServicoActionForm.getDataEncerramento() + " "
							+ encerrarOrdemServicoActionForm.getHoraEncerramento() + ":00");
		}
		osEncerramentoHelper.setDataExecucao(dataEncerramento);

		osEncerramentoHelper.setUsuarioLogado(usuarioLogado);
		osEncerramentoHelper.setIdMotivoEncerramento(encerrarOrdemServicoActionForm.getIdMotivoEncerramento());

		Date dataUltimaAlteracao = encerrarOrdemServicoActionForm.getUltimaAlteracao();
		osEncerramentoHelper.setUltimaAlteracao(dataUltimaAlteracao);

		osEncerramentoHelper.setParecerEncerramento(encerrarOrdemServicoActionForm.getObservacaoEncerramento());
		osEncerramentoHelper.setIndicadorPavimento(encerrarOrdemServicoActionForm.getIndicadorPavimento());
		osEncerramentoHelper.setPavimento(encerrarOrdemServicoActionForm.getPavimento());
		osEncerramentoHelper.setIdTipoRetornoOSReferida(encerrarOrdemServicoActionForm.getIdTipoRetornoReferida());
		osEncerramentoHelper.setIndicadorDeferimento(encerrarOrdemServicoActionForm.getIndicadorDeferimento());
		osEncerramentoHelper.setIndicadorTrocaServicoTipo(encerrarOrdemServicoActionForm.getIndicadorTrocaServico());
		osEncerramentoHelper.setIdServicoTipo(encerrarOrdemServicoActionForm.getIdServicoTipo());
		osEncerramentoHelper.setIdOSReferencia(encerrarOrdemServicoActionForm.getNumeroOSReferencia());
		osEncerramentoHelper.setIdServicoTipoReferenciaOS(encerrarOrdemServicoActionForm.getServicoTipoReferenciaOS());

		IntegracaoComercialHelper integracaoComercialHelper = (IntegracaoComercialHelper) sessao
						.getAttribute(ATRIBUTO_INTEGRACAO_COMERCIAL_HELPER);
		osEncerramentoHelper.setIntegracaoComercialHelper(integracaoComercialHelper);

		osEncerramentoHelper.setTipoServicoOSId(encerrarOrdemServicoActionForm.getTipoServicoOSId());

		OrdemServico osFiscalizacao = (OrdemServico) sessao.getAttribute("osFiscalizacao");
		osEncerramentoHelper.setOsFiscalizacao(osFiscalizacao);

		osEncerramentoHelper.setIndicadorVistoriaServicoTipo(encerrarOrdemServicoActionForm.getIndicadorVistoriaServicoTipo());
		osEncerramentoHelper.setCodigoRetornoVistoriaOs(encerrarOrdemServicoActionForm.getCodigoRetornoVistoriaOs());
		osEncerramentoHelper.setDimensao1(encerrarOrdemServicoActionForm.getDimensao1());
		osEncerramentoHelper.setDimensao2(encerrarOrdemServicoActionForm.getDimensao2());
		osEncerramentoHelper.setDimensao3(encerrarOrdemServicoActionForm.getDimensao3());

		AutorizarServicoAssociadoHelper autorizarServicoAssociadoHelper = (AutorizarServicoAssociadoHelper) sessao
						.getAttribute(ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER);

		if(autorizarServicoAssociadoHelper != null){

			Map<Integer, OSEncerramentoHelper> mapaIdServicoTipoOSEncerramento = (Map<Integer, OSEncerramentoHelper>) autorizarServicoAssociadoHelper
							.getMapaServicosAssociadosEncerrados();

			if(mapaIdServicoTipoOSEncerramento == null){
				mapaIdServicoTipoOSEncerramento = new HashMap<Integer, OSEncerramentoHelper>();
			}

			mapaIdServicoTipoOSEncerramento.put(Integer.valueOf(idServicoTipo), osEncerramentoHelper);
			autorizarServicoAssociadoHelper.setMapaServicosAssociadosEncerrados(mapaIdServicoTipoOSEncerramento);

			sessao.setAttribute(ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER, autorizarServicoAssociadoHelper);

		}

		return retorno;
	}

}