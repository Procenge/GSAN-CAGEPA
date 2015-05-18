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

import gcom.arrecadacao.pagamento.FiltroGuiaPagamento;
import gcom.arrecadacao.pagamento.FiltroGuiaPagamentoHistorico;
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.arrecadacao.pagamento.GuiaPagamentoHistorico;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;

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
public class ExibirAlterarVencimentoPrestacoesGuiaPagamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirAlterarVencimentoPrestacoesGuiaPagamento");
		AtualizarGuiaPagamentoActionForm form = (AtualizarGuiaPagamentoActionForm) actionForm;
		HttpSession sessao = httpServletRequest.getSession(false);
		Fachada fachada = Fachada.getInstancia();
		Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiasPrestacoes = null;

		// Recebe o id da guia de pagamento para fazer a consulta
		colecaoGuiasPrestacoes = (Collection<GuiaPagamentoPrestacaoHelper>) sessao.getAttribute("colecaoGuiasPrestacoes");

		GuiaPagamentoPrestacaoHelper guiaPagamentoPrestacaoHelper = (GuiaPagamentoPrestacaoHelper) Util
						.retonarObjetoDeColecao(colecaoGuiasPrestacoes);
		Integer idGuiaPagamento = guiaPagamentoPrestacaoHelper.getIdGuiaPagamento();

		String idImovel = form.getIdImovel();
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		if(!Util.isVazioOuBranco(idImovel)){
			// [SB0005] – Validar autorização de acesso a prestação da guia de imóvel
			fachada.validarAutorizacaoAcessoPrestacaoGuiaImovel(usuarioLogado,
							Integer.valueOf(idImovel), colecaoGuiasPrestacoes);
		}

		// Se chegar na funcionalidade sem o parâmetro indica situação de erro
		if(idGuiaPagamento == null){
			throw new ActionServletException("erro.sistema");

		}

		if(idGuiaPagamento != null && idGuiaPagamento > 0){

			// Pesquisa as prestações da guia de pagamento
			Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiasPrestacoesAlterarVencimento = fachada
							.pesquisarGuiasPagamentoPrestacaoFiltrar(
							idGuiaPagamento,
							ConstantesSistema.CONSULTA_UC0188_SB0009_ITEM_1_2);

			// [FS0007] – Verificar existência das prestações da guia de pagamento
			if(Util.isVazioOrNulo(colecaoGuiasPrestacoesAlterarVencimento)){
				throw new ActionServletException("atencao.guia_sem_prestacoes");
			}else{

				sessao.setAttribute("colecaoGuiasPrestacoesAlterarVencimento", colecaoGuiasPrestacoesAlterarVencimento);
			}
		}

		// Consulta do GuiaPagamento
		FiltroGuiaPagamento filtroGuiaPagamento = new FiltroGuiaPagamento();
		filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.ID, idGuiaPagamento));

		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("cliente.clienteTipo");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("localidade");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("guiasPagamentoPrestacao");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("guiasPagamentoPrestacaoHistorico");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("debitoCreditoSituacaoAtual");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("registroAtendimento");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("ordemServico");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("documentoTipo");

		// Para a exibição do endereço do imóvel
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.localidade");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.quadra");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroBairro.bairro.municipio.unidadeFederacao");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.cep");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.logradouro.logradouroTipo");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.logradouro.logradouroTitulo");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.enderecoReferencia");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("origem.guiaPagamento.imovel");

		Collection colecaoGuias = fachada.pesquisar(filtroGuiaPagamento, GuiaPagamento.class.getName());

		// Caso a consulta não retorne nenhum registro isso indica que a guia está no histórico
		if(Util.isVazioOrNulo(colecaoGuias)){
			FiltroGuiaPagamentoHistorico filtroGuiaPagamentoHistorico = new FiltroGuiaPagamentoHistorico();
			filtroGuiaPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoHistorico.ID, idGuiaPagamento));

			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("cliente.clienteTipo");
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("localidade");
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("guiasPagamentoPrestacao");
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("guiasPagamentoPrestacaoHistorico");
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("debitoCreditoSituacaoAtual");
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("registroAtendimento");
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("ordemServico");
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("documentoTipo");

			// Para a exibição do endereço do imóvel
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("imovel.localidade");
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial");
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("imovel.quadra");
			filtroGuiaPagamentoHistorico
							.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroBairro.bairro.municipio.unidadeFederacao");
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.cep");
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.logradouro.logradouroTipo");
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.logradouro.logradouroTitulo");
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("imovel.enderecoReferencia");
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("origem.guiaPagamento.imovel");

			colecaoGuias = fachada.pesquisar(filtroGuiaPagamentoHistorico, GuiaPagamentoHistorico.class.getName());
		}

		Iterator iteratorPrestacoes = null;
		Iterator iteratorPrestacoesHistorico = null;
		Object guiaPagamento = Util.retonarObjetoDeColecao(colecaoGuias);

		// Envia o objeto consultado para a página
		httpServletRequest.setAttribute("guiaPagamento", guiaPagamento);

		return retorno;
	}
}