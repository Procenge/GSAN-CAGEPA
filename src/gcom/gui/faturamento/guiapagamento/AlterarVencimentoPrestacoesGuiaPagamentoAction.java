/*
 * Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
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
 * GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
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
 * [SB0009] – Alterar Vencimento Prestações
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

		// Se chegar na funcionalidade sem o parâmetro indica situação de erro
		if(idGuiaPagamento == null || idGuiaPagamento.trim().equals("")){
			throw new ActionServletException("erro.sistema");

		}

		Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiasPrestacoes = (Collection<GuiaPagamentoPrestacaoHelper>) sessao
						.getAttribute("colecaoGuiasPrestacoes");
		
		// 
		String arDtVencimento[] = form.getDataVencimento();

		// Validar data da prestação
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
					 * [SB0011] Verificar Existência de Conta em Execução Fiscal
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