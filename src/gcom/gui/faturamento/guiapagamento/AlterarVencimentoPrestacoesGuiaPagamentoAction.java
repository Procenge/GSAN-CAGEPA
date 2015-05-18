/*
 * Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
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

package gcom.gui.faturamento.guiapagamento;

import gcom.arrecadacao.pagamento.FiltroGuiaPagamentoPrestacao;
import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacao;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0188] Manter Guia de Pagamento
 * [SB0009] � Alterar Vencimento Presta��es
 */
public class AlterarVencimentoPrestacoesGuiaPagamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		AtualizarGuiaPagamentoActionForm form = (AtualizarGuiaPagamentoActionForm) actionForm;
		HttpSession sessao = httpServletRequest.getSession(false);
		Fachada fachada = Fachada.getInstancia();
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		HashMap<GuiaPagamentoPrestacaoHelper, Date> hashMapGuiasPrestacoesVencimentoAlterado = null;

		// Recebe o id da guia de pagamento para fazer a consulta
		String idGuiaPagamento = form.getIdRegistroAtualizacao(); // form.getNumeroGuia();

		// Se chegar na funcionalidade sem o par�metro indica situa��o de erro
		if(idGuiaPagamento == null || idGuiaPagamento.trim().equals("")){
			throw new ActionServletException("erro.sistema");

		}

		Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiasPrestacoes = (Collection<GuiaPagamentoPrestacaoHelper>) sessao
						.getAttribute("colecaoGuiasPrestacoes");
		
		// 
		String arDtVencimento[] = form.getDataVencimento();

		// Validar data da presta��o
		if(arDtVencimento.length > 1){
			Date dataAtual = null;
			Date dataAnterior = null;
			for(int i = 1; i < arDtVencimento.length; i++){

				dataAtual = Util.converterStringParaData(arDtVencimento[i]);
				dataAnterior = Util.converterStringParaData(arDtVencimento[i - 1]);

				if(Util.compararData(dataAtual, dataAnterior) != 1){
					throw new ActionServletException("atencao.guia_pagamento.datavencimento.maior.anterior", i+1+"", arDtVencimento[i-1]);
				}
			}
		}

		// Verifica as datas que foram alteradas
		int i = 0;
		hashMapGuiasPrestacoesVencimentoAlterado = new HashMap<GuiaPagamentoPrestacaoHelper, Date>();

		if(arDtVencimento != null && arDtVencimento.length > 0){
			StringBuffer parametroPrestacaoExecucaoFiscal = new StringBuffer();

			for(GuiaPagamentoPrestacaoHelper guiaBase : colecaoGuiasPrestacoes){
				if(!Util.formatarData(guiaBase.getDataVencimento()).equals(arDtVencimento[i])){
					guiaBase.setIdGuiaPagamento(Integer.parseInt(idGuiaPagamento));
					hashMapGuiasPrestacoesVencimentoAlterado.put(guiaBase, Util.converterStringParaData(arDtVencimento[i]));

					/*
					 * [UC0188] Manter Guia de Pagamento
					 * [SB0011] Verificar Exist�ncia de Conta em Execu��o Fiscal
					 */
					boolean temPermissaoAtualizarDebitosExecFiscal = fachada.verificarPermissaoEspecial(
									PermissaoEspecial.ATUALIZAR_DEBITOS_EXECUCAO_FISCAL, this.getUsuarioLogado(httpServletRequest));

					FiltroGuiaPagamentoPrestacao filtroGuiaPagamentoPrestacao = new FiltroGuiaPagamentoPrestacao();
					filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacao.GUIA_PAGAMENTO_ID,
									guiaBase.getIdGuiaPagamento()));
					filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacao.NUMERO_PRESTACAO,
									guiaBase.getNumeroPrestacao()));
					filtroGuiaPagamentoPrestacao.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoPrestacao.DEBITO_TIPO);

					Collection<GuiaPagamentoPrestacao> colecaoGuiaPagamentoPrestacao = fachada.pesquisar(filtroGuiaPagamentoPrestacao,
									GuiaPagamentoPrestacao.class.getName());

					GuiaPagamentoValoresHelper guiaPagamentoValores = new GuiaPagamentoValoresHelper();
					guiaPagamentoValores.setGuiaPagamentoPrestacoes(new HashSet<GuiaPagamentoPrestacao>(colecaoGuiaPagamentoPrestacao));

					Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores = new ArrayList<GuiaPagamentoValoresHelper>();
					colecaoGuiaPagamentoValores.add(guiaPagamentoValores);

					if(fachada.verificarExecucaoFiscal(null, colecaoGuiaPagamentoValores, null) && !temPermissaoAtualizarDebitosExecFiscal){
						parametroPrestacaoExecucaoFiscal.append(guiaBase.getIdGuiaPagamento());
						parametroPrestacaoExecucaoFiscal.append("/");
						parametroPrestacaoExecucaoFiscal.append(guiaBase.getNumeroPrestacao());
						parametroPrestacaoExecucaoFiscal.append(", ");
					}
				}
				i++;
			}

			String parametroMensagemExecFiscal = parametroPrestacaoExecucaoFiscal.toString();

			if(!Util.isVazioOuBranco(parametroMensagemExecFiscal)){
				parametroMensagemExecFiscal = parametroMensagemExecFiscal.substring(0, parametroMensagemExecFiscal.length() - 2);

				throw new ActionServletException("atencao.guia.prestacao.debito.execucao.fiscal", usuario.getNomeUsuario().toString(),
								parametroMensagemExecFiscal);
			}
		}
		
		if(!hashMapGuiasPrestacoesVencimentoAlterado.isEmpty()){
			fachada.atualizarVencimentoGuiaPagamentoPrestacao(hashMapGuiasPrestacoesVencimentoAlterado);

			montarPaginaSucesso(httpServletRequest, "Registro atualizado com sucesso.", "Voltar",
							"exibirAlterarVencimentoPrestacoesGuiaPagamentoAction.do", "", "");
		}else{
			throw new ActionServletException("atencao.guiapagamento.data_vencimento_nao_alterada");
		}


		return retorno;
	}

}