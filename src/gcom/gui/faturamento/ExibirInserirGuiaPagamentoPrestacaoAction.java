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
 * Eduardo Henrique
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

package gcom.gui.faturamento;

import gcom.cadastro.imovel.FiltroImovelCobrancaSituacao;
import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.cobranca.CobrancaSituacao;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

		sessao.removeAttribute("exibirColunaNumeroProcessoAdministrativo");

		// Tipo de Débito
		if(httpServletRequest.getParameter("validaDebitoTipo") != null && !httpServletRequest.getParameter("validaDebitoTipo").equals("")){

			String idTipoDebito = form.getIdTipoDebito();
			this.validarTipoDebito(sessao, form, httpServletRequest, idTipoDebito);

		}else{
			form.setIdTipoDebito("");
			form.setDescricaoTipoDebito("");
			form.setValorDebito("");
			form.setNumeroProcessoAdministrativoExecucaoFiscal("");
		}

		// Parâmetros vindos da consulta por Popup
		if(httpServletRequest.getParameter("idCampoEnviarDados") != null
						&& !httpServletRequest.getParameter("idCampoEnviarDados").equals("")){
			String idTipoDebito = (String) httpServletRequest.getParameter("idCampoEnviarDados");

			FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
			filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, idTipoDebito));

			Collection debitosTipos = fachada.pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());
			if(debitosTipos != null && !debitosTipos.isEmpty()){

				DebitoTipo debitoTipo = (DebitoTipo) debitosTipos.iterator().next();

				this.validarTipoDebito(sessao, form, httpServletRequest, debitoTipo.getId().toString());

				form.setIdTipoDebito(debitoTipo.getId().toString());
				form.setDescricaoTipoDebito(debitoTipo.getDescricao());

				BigDecimal valorPadraoDebitoTipo = BigDecimal.ZERO;
				// Inclui o valor padrão do débito tipo
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

	private void validarTipoDebito(HttpSession sessao, InserirGuiaPagamentoActionForm form, HttpServletRequest httpServletRequest,
					String idTipoDebito){

		Fachada fachada = Fachada.getInstancia();
		// String idTipoDebito = form.getIdTipoDebito();

		if(idTipoDebito != null && !idTipoDebito.trim().equals("")){
			sessao.removeAttribute("colecaoNumeroProcessoAdministrativo");

			FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
			filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, idTipoDebito));
			// filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoTipo.FINANCIAMENTO_TIPO);
			filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade("financiamentoTipo");

			Collection debitosTipos = fachada.pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());

			if(debitosTipos != null && !debitosTipos.isEmpty()){

				DebitoTipo debitoTipo = (DebitoTipo) debitosTipos.iterator().next();



				form.setDescricaoTipoDebito(debitoTipo.getDescricao());

				String idImovelInformado = form.getIdImovel();
				BigDecimal valorPadraoDebitoTipo = BigDecimal.ZERO;

				String idClienteInformado = form.getCodigoCliente();

				// Inclui o valor padrão do débito tipo
				if(!Util.isVazioOuBranco(debitoTipo.getValorPadrao())){
					valorPadraoDebitoTipo = debitoTipo.getValorPadrao();
				}

				// [OC1213341] - Verificar se existe valor do Debito Tipo para a Localidade se
				// positivo substituir o valor padrão pelo valor encontrado.

				if(Util.isNaoNuloBrancoZero(idImovelInformado)){
					BigDecimal valorDebitoLocalidade = fachada.verificarDebitoTipoValorLocalidade(new Integer(idImovelInformado),
									new Integer(idTipoDebito));

					if(valorDebitoLocalidade != null){
						valorPadraoDebitoTipo = valorDebitoLocalidade;
					}
				}

				form.setValorDebito(Util.formatarMoedaReal(valorPadraoDebitoTipo));

				if(!Util.isVazioOuBranco(idImovelInformado)){
					if(debitoTipo.getId().equals(DebitoTipo.SUCUMBENCIA)){
						FiltroImovelCobrancaSituacao filtroImovelCobrancaSituacao = new FiltroImovelCobrancaSituacao();
						filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.IMOVEL_ID,
										Integer.valueOf(idImovelInformado)));
						filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(
										FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO, CobrancaSituacao.EXECUCAO_FISCAL));

						Collection<ImovelCobrancaSituacao> colecaoImovelCobrancaSituacao = fachada.pesquisar(filtroImovelCobrancaSituacao,
										ImovelCobrancaSituacao.class.getName());
						if(colecaoImovelCobrancaSituacao == null || colecaoImovelCobrancaSituacao.isEmpty()
										|| colecaoImovelCobrancaSituacao.size() == 0){
							throw new ActionServletException("atencao.guia_pagamento_prestacao.sem_imovel_em_execucao_fiscal",
											idImovelInformado);
						}else{

							boolean indicadorAchouProcesso = false;
							for(ImovelCobrancaSituacao imovelCobrancaSituacao : colecaoImovelCobrancaSituacao){
								if(imovelCobrancaSituacao.getNumeroProcessoAdministrativoExecucaoFiscal() != null){
									indicadorAchouProcesso = true;
									break;
								}
							}

							if(!indicadorAchouProcesso){
								throw new ActionServletException("atencao.guia_pagamento_prestacao.sem_imovel_com_numero_processo",
												idImovelInformado);
							}


						}

						Collection<Integer> colecaoNumeroProcessoAdministrativo = fachada
										.pesquisarProcessosExecucaoEspeciaisPendentesCobranca(Integer.valueOf(idImovelInformado));

						if(colecaoNumeroProcessoAdministrativo == null || colecaoNumeroProcessoAdministrativo.isEmpty()){
							throw new ActionServletException("atencao.guia_pagamento_prestacao.sem_imovel_em_exec_fiscal_especial_pend",
											idImovelInformado);
						}

						sessao.setAttribute("exibirColunaNumeroProcessoAdministrativo", "S");

						Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiaPagamentoPrestacaoHelper = null;
						if(sessao.getAttribute("colecaoGuiaPrestacaoHelper") != null){
							colecaoGuiaPagamentoPrestacaoHelper = (Collection<GuiaPagamentoPrestacaoHelper>) sessao
											.getAttribute("colecaoGuiaPrestacaoHelper");
						}

						Map<Integer, String> colecaoNumeroProcessoAdministrativoString = new HashMap<Integer, String>();
						for(Integer numeroProcessoAdministrativo : colecaoNumeroProcessoAdministrativo){

							if(colecaoGuiaPagamentoPrestacaoHelper == null){
								colecaoNumeroProcessoAdministrativoString.put(numeroProcessoAdministrativo,
												numeroProcessoAdministrativo.toString());
							}else{
								Boolean existeProcesso = false;
								for(GuiaPagamentoPrestacaoHelper guiaPagamentoPrestacaoHelper : colecaoGuiaPagamentoPrestacaoHelper){
									if(guiaPagamentoPrestacaoHelper.getNumeroProcessoAdministrativoExecucaoFiscal() != null
													&& guiaPagamentoPrestacaoHelper.getNumeroProcessoAdministrativoExecucaoFiscal()
																	.compareTo(numeroProcessoAdministrativo) == 0){
										existeProcesso = true;
									}
								}

								if(!existeProcesso){
									colecaoNumeroProcessoAdministrativoString.put(numeroProcessoAdministrativo,
													numeroProcessoAdministrativo.toString());
								}
							}
						}

						sessao.setAttribute("colecaoNumeroProcessoAdministrativo", colecaoNumeroProcessoAdministrativoString);

					}else{
						sessao.removeAttribute("exibirColunaNumeroProcessoAdministrativo");
					}
				}else if(!Util.isVazioOuBranco(idClienteInformado)){

					// 7.5.3.1.2. Caso a guia esteja associada a um cliente (campo Matrícula do
					// Imóvel não informado e campo Código do Cliente informado), seleciona os
					// processos das execuções fiscais do cliente pendentes de cobrança do valor de
					// sucumbência (ISCB_NNPROCESADMEXECUCAOFISCAL da tabela
					// IMOVEL_COBRANCA_SITUACAO com IMOV_ID com o valor nulo e CLIE_ID=Id do Cliente
					// informado/selecionado e CBST_ID=CBST_ID da tabela COBRANCA_SITUACAO com
					// CBST_CDCONSTANTE="EXECUCAO_FISCAL" e ISCB_NNPROCESADMEXECUCAOFISCAL com o
					// valor diferente de nulo e ISCB_NNPROCESADMEXECUCAOFISCAL não ocorrendo em
					// GPPR_NNPROCESADMEXECUCAOFISCAL da tabela GUIA_PAGAMENTO_PRESTACAO com
					// DCST_ID=0 (zero) e DBTP_ID=DBTP_ID da tabela DEBITO_TIPO com
					// DBTP_CDCONSTANTE="SUCUMBENCIA" e ISCB_NNPROCESADMEXECUCAOFISCAL não ocorrendo
					// em GPPH_NNPROCESADMEXECUCAOFISCAL da tabela GUIA_PAGAMENTO_PRESTACAO_HIST com
					// DCST_ID=0 (zero) e DBTP_ID=DBTP_ID da tabela DEBITO_TIPO com
					// DBTP_CDCONSTANTE="SUCUMBENCIA", ordenando pelo número do processo
					// administrativo (ISCB_NNPROCESADMEXECUCAOFISCAL) em ordem crescente)
					// [FS0024 - Verificar processo já selecionado].

					if(debitoTipo.getId().equals(DebitoTipo.SUCUMBENCIA)){
						FiltroImovelCobrancaSituacao filtroImovelCobrancaSituacao = new FiltroImovelCobrancaSituacao();
						filtroImovelCobrancaSituacao.adicionarParametro(new ParametroNulo(FiltroImovelCobrancaSituacao.IMOVEL_ID));
						filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.CLIENTE_ID,
										idClienteInformado));
						filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(
										FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO, CobrancaSituacao.EXECUCAO_FISCAL));

						Collection<ImovelCobrancaSituacao> colecaoImovelCobrancaSituacao = fachada.pesquisar(filtroImovelCobrancaSituacao,
										ImovelCobrancaSituacao.class.getName());
						if(colecaoImovelCobrancaSituacao == null || colecaoImovelCobrancaSituacao.isEmpty()
										|| colecaoImovelCobrancaSituacao.size() == 0){
							throw new ActionServletException("atencao.guia_pagamento_prestacao.sem_cliente_em_execucao_fiscal",
											idClienteInformado);
						}else{

							boolean indicadorAchouProcesso = false;
							for(ImovelCobrancaSituacao imovelCobrancaSituacao : colecaoImovelCobrancaSituacao){
								if(imovelCobrancaSituacao.getNumeroProcessoAdministrativoExecucaoFiscal() != null){
									indicadorAchouProcesso = true;
									break;
								}
							}

							if(!indicadorAchouProcesso){
								throw new ActionServletException("atencao.guia_pagamento_prestacao.sem_cliente_com_numero_processo",
												idClienteInformado);
							}

						}


						Collection<Integer> colecaoNumeroProcessoAdministrativo = fachada
										.pesquisarProcessosExecucaoEspeciaisPendentesCobrancaPorCliente(Integer.valueOf(idClienteInformado));

						if(colecaoNumeroProcessoAdministrativo == null || colecaoNumeroProcessoAdministrativo.isEmpty()){
							throw new ActionServletException("atencao.guia_pagamento_prestacao.sem_cliente_em_exec_fiscal_especial_pend",
											idClienteInformado);
						}

						sessao.setAttribute("exibirColunaNumeroProcessoAdministrativo", "S");

						Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiaPagamentoPrestacaoHelper = null;
						if(sessao.getAttribute("colecaoGuiaPrestacaoHelper") != null){
							colecaoGuiaPagamentoPrestacaoHelper = (Collection<GuiaPagamentoPrestacaoHelper>) sessao
											.getAttribute("colecaoGuiaPrestacaoHelper");
						}

						Map<Integer, String> colecaoNumeroProcessoAdministrativoString = new HashMap<Integer, String>();
						for(Integer numeroProcessoAdministrativo : colecaoNumeroProcessoAdministrativo){

							if(colecaoGuiaPagamentoPrestacaoHelper == null){
								colecaoNumeroProcessoAdministrativoString.put(numeroProcessoAdministrativo,
												numeroProcessoAdministrativo.toString());
							}else{
								Boolean existeProcesso = false;
								for(GuiaPagamentoPrestacaoHelper guiaPagamentoPrestacaoHelper : colecaoGuiaPagamentoPrestacaoHelper){
									if(guiaPagamentoPrestacaoHelper.getNumeroProcessoAdministrativoExecucaoFiscal() != null
													&& guiaPagamentoPrestacaoHelper.getNumeroProcessoAdministrativoExecucaoFiscal()
																	.compareTo(numeroProcessoAdministrativo) == 0){
										existeProcesso = true;
									}
								}

								if(!existeProcesso){
									colecaoNumeroProcessoAdministrativoString.put(numeroProcessoAdministrativo,
													numeroProcessoAdministrativo.toString());
								}
							}
						}

						sessao.setAttribute("colecaoNumeroProcessoAdministrativo", colecaoNumeroProcessoAdministrativoString);

					}else{
						sessao.removeAttribute("exibirColunaNumeroProcessoAdministrativo");
					}
				}

				httpServletRequest.setAttribute("nomeCampo", "valorDebito");
				httpServletRequest.setAttribute("debitoTipoNaoEncontrado", "true");
			}else{

				form.setIdTipoDebito("");
				form.setDescricaoTipoDebito("Tipo de Débito inexistente");

				httpServletRequest.setAttribute("tipoDebitoInexistente", true);
				httpServletRequest.setAttribute("nomeCampo", "idTipoDebito");
				httpServletRequest.removeAttribute("exibirColunaNumeroProcesso");
			}
		}

	}

}
