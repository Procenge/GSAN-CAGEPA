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
 * 
 * GSANPCG
 * Eduardo Henrique
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

package gcom.gui.faturamento;

import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0187] - Manter Guia de pagamento
 * 
 * @author eduardo henrique
 * @date 31/07/2008
 * @since v0.04
 */
public class ExibirInserirGuiaPagamentoPrestacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		InserirGuiaPagamentoActionForm form = (InserirGuiaPagamentoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		ActionForward retorno = actionMapping.findForward("inserirGuiaPagamentoPrestacao");

		String retornarTela = httpServletRequest.getParameter("retornarTela");
		if(retornarTela != null && !retornarTela.trim().equalsIgnoreCase("")){
			sessao.setAttribute("retornarTela", retornarTela);
		}

		// Tipo de D�bito
		if(httpServletRequest.getParameter("validaDebitoTipo") != null && !httpServletRequest.getParameter("validaDebitoTipo").equals("")){
			String idTipoDebito = form.getIdTipoDebito();

			if(idTipoDebito != null && !idTipoDebito.trim().equals("")){

				FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
				filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, idTipoDebito));
				// filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoTipo.FINANCIAMENTO_TIPO);
				filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade("financiamentoTipo");

				Collection debitosTipos = fachada.pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());

				if(debitosTipos != null && !debitosTipos.isEmpty()){

					DebitoTipo debitoTipo = (DebitoTipo) debitosTipos.iterator().next();

					/*
					 * if(form.getRegistroAtendimento() != null &&
					 * !form.getRegistroAtendimento().equals("")){
					 * FiltroGuiaPagamento filtro = new FiltroGuiaPagamento();
					 * filtro.adicionarParametro(new
					 * ParametroSimples(FiltroGuiaPagamento.DEBITO_TIPO_ID, idTipoDebito));
					 * filtro.adicionarParametro(new
					 * ParametroSimples(FiltroGuiaPagamento.REGISTRO_ATENDIMENTO_ID,
					 * new Integer(inserirGuiaPagamentoActionForm.getRegistroAtendimento())));
					 * filtro.adicionarParametro(new ParametroSimplesDiferenteDe(
					 * FiltroGuiaPagamento.DEBITO_CREDITO_SITUACAO_ATUAL_ID,DebitoCreditoSituacao.
					 * CANCELADA));
					 * Collection colecao = fachada.pesquisar(filtro,GuiaPagamento.class.getName());
					 * if(colecao != null && !colecao.isEmpty()){
					 * throw new ActionServletException("atencao.guia_pagamento.ja.cadastrado");
					 * }
					 * }
					 */

					/*
					 * //[FS0013] � Verificar tipo de financiamento do tipo de d�bito
					 * if(debitoTipo.getFinanciamentoTipo().getId().intValue() !=
					 * FinanciamentoTipo.SERVICO_NORMAL.intValue() ||
					 * debitoTipo.getIndicadorGeracaoAutomatica().shortValue() == 1){
					 * throw newActionServletException(
					 * "atencao.tipo_financiamento.tipo_debito.nao.permite.debito_a_cobrar",
					 * null,debitoTipo.getFinanciamentoTipo().getDescricao(),debitoTipo.getDescricao(
					 * ));
					 * }
					 */

					form.setDescricaoTipoDebito(debitoTipo.getDescricao());

					String idImovelInformado = form.getIdImovel();
					BigDecimal valorPadraoDebitoTipo = BigDecimal.ZERO;
					// Inclui o valor padr�o do d�bito tipo
					if(!Util.isVazioOuBranco(debitoTipo.getValorPadrao())){
						valorPadraoDebitoTipo = debitoTipo.getValorPadrao();
					}

					// [OC1213341] - Verificar se existe valor do Debito Tipo para a Localidade se
					// positivo substituir o valor padr�o pelo valor encontrado.

					if(Util.isNaoNuloBrancoZero(idImovelInformado)){
						BigDecimal valorDebitoLocalidade = fachada.verificarDebitoTipoValorLocalidade(new Integer(idImovelInformado),
										new Integer(idTipoDebito));

						if(valorDebitoLocalidade != null){
							valorPadraoDebitoTipo = valorDebitoLocalidade;
						}
					}

					form.setValorDebito(Util.formatarMoedaReal(valorPadraoDebitoTipo));

					// if(idRegistroAtendimento != null &&
					// !idRegistroAtendimento.trim().equals("")){
					// httpServletRequest.setAttribute("nomeCampo", "dataVencimento");
					// }

					httpServletRequest.setAttribute("nomeCampo", "valorDebito");
					httpServletRequest.setAttribute("debitoTipoNaoEncontrado", "true");
				}else{

					form.setIdTipoDebito("");
					form.setDescricaoTipoDebito("Tipo de D�bito inexistente");

					httpServletRequest.setAttribute("tipoDebitoInexistente", true);
					httpServletRequest.setAttribute("nomeCampo", "idTipoDebito");
				}
			}
		}else{
			form.setIdTipoDebito("");
			form.setDescricaoTipoDebito("");
			form.setValorDebito("");
		}

		// Par�metros vindos da consulta por Popup
		if(httpServletRequest.getParameter("idCampoEnviarDados") != null
						&& !httpServletRequest.getParameter("idCampoEnviarDados").equals("")){
			String idTipoDebito = (String) httpServletRequest.getParameter("idCampoEnviarDados");

			FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
			filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, idTipoDebito));

			Collection debitosTipos = fachada.pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());
			if(debitosTipos != null && !debitosTipos.isEmpty()){

				DebitoTipo debitoTipo = (DebitoTipo) debitosTipos.iterator().next();
				form.setIdTipoDebito(debitoTipo.getId().toString());
				form.setDescricaoTipoDebito(debitoTipo.getDescricao());

				BigDecimal valorPadraoDebitoTipo = BigDecimal.ZERO;
				// Inclui o valor padr�o do d�bito tipo
				if(!Util.isVazioOuBranco(debitoTipo.getValorPadrao())){
					valorPadraoDebitoTipo = debitoTipo.getValorPadrao();
				}
				form.setValorDebito(Util.formatarMoedaReal(valorPadraoDebitoTipo));
			}

			httpServletRequest.setAttribute("nomeCampo", "valorDebito");
		}

		Short indicador = (Short) sessao.getAttribute("indicadorDadosAlterados");

		if(!Util.isVazioOuBranco(indicador) && indicador.equals(ConstantesSistema.SIM)){
			sessao.setAttribute("indicadorDadosAlterados", ConstantesSistema.NAO);
		}

		return retorno;
	}

}
