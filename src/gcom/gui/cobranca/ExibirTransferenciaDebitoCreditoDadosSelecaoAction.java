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

package gcom.gui.cobranca;

import gcom.arrecadacao.pagamento.FiltroGuiaPagamentoPrestacao;
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacao;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade exibir para o usuário a tela que exibirá
 * as contas, débitos, créditos e guias para seleção e posteriormente
 * realização da transferência dos selecionados
 * 
 * @author Raphael Rossiter
 * @date 08/06/2007
 */
public class ExibirTransferenciaDebitoCreditoDadosSelecaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("transferenciaDebitoCreditoDadosSelecao");

		TransferenciaDebitoCreditoDadosImovelActionForm form = (TransferenciaDebitoCreditoDadosImovelActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();
		Integer idClienteOrigem = null;
		Integer idRelacaoClienteOrigem = null;
		Integer idImovelOrigem = null;
		Integer idRa = Integer.valueOf(form.getIdRegistroAtendimento());
		Integer idImovelDestino = null;
		Collection<ClienteImovel> colecaoRelacaoImovel = new ArrayList<ClienteImovel>();

		Boolean indicadorFaturamentoTitularDebito = false;
		try{
			if(ParametroFaturamento.P_INDICADOR_FATURAMENTO_ATUAL_TITULAR_DEBITO_IMOVEL.executar().equals("1")){
				indicadorFaturamentoTitularDebito = true;
			}
		}catch(ControladorException e){
			e.printStackTrace();
		}

		if(form.getIdImovelDestino() != null && !form.getIdImovelDestino().equals("")){
			idImovelDestino = Integer.valueOf(form.getIdImovelDestino());
		}

		if(idImovelDestino != null){
			// Validação dos dados informados referentes aos imóveis de origem e destino
			idImovelOrigem = fachada.validarTransferenciaDebitoCreditoDadosImoveis(idRa, idImovelDestino);
		}else{
			idImovelOrigem = Integer.valueOf(form.getIdImovelOrigem());
		}

		if(indicadorFaturamentoTitularDebito){
			// Relacao Guia Clientes
			if(sessao.getAttribute("colecaoRelacaoImovel") == null
							|| ((Collection<ClienteImovel>) sessao.getAttribute("colecaoRelacaoImovel")).isEmpty()){
				colecaoRelacaoImovel = fachada.obterListaClientesRelacaoDevedor(idImovelOrigem, Integer.valueOf("000101"),
								Integer.valueOf("999912"), 1, 1, 1, 1, 1, 1, 1, null, ConstantesSistema.SIM, ConstantesSistema.SIM,
								ConstantesSistema.SIM, 2, null, null);

				if(colecaoRelacaoImovel.size() > 0){
					for(ClienteImovel clienteImovel : colecaoRelacaoImovel){

						if(form.getIdClienteImovelSelecionado() == null){
							form.setIdClienteImovelSelecionado(clienteImovel.getClienteRelacaoTipo().getId().toString() + '.'
											+ clienteImovel.getCliente().getId().toString());

							form.setIdClienteOrigemSelecionado(clienteImovel.getCliente().getId());
							form.setIdClienteRelacaoOrigemSelecionado(clienteImovel.getClienteRelacaoTipo().getId());

							idClienteOrigem = form.getIdClienteOrigemSelecionado();
							idRelacaoClienteOrigem = form.getIdClienteRelacaoOrigemSelecionado();
						}
					}
				}
			}else{
				colecaoRelacaoImovel = (Collection<ClienteImovel>) sessao.getAttribute("colecaoRelacaoImovel");

				if(form.getIdClienteImovelSelecionado() != null && !form.getIdClienteImovelSelecionado().equals("")){
					String[] dados = form.getIdClienteImovelSelecionado().split("\\.");

					idClienteOrigem = form.getIdClienteOrigemSelecionado();
					idRelacaoClienteOrigem = form.getIdClienteRelacaoOrigemSelecionado();

					String idsContas = httpServletRequest.getParameter("conta");
					String idsDebitos = httpServletRequest.getParameter("debito");
					String idsCreditos = httpServletRequest.getParameter("credito");
					String idsGuias = httpServletRequest.getParameter("guiaPagamento");

					form.setIdClienteOrigemSelecionado(Integer.valueOf(dados[1]));
					form.setIdClienteRelacaoOrigemSelecionado(Integer.valueOf(dados[0]));

					if(form.getIdClienteOrigem() != null && form.getIdClienteRelacaoOrigem() != null){
						boolean bFlagItensLista = false;

						for(int cont = 0; cont < form.getIdClienteOrigem().size(); cont++){
							if(form.getIdClienteOrigem().get(cont).equals(idClienteOrigem)
											&& form.getIdClienteRelacaoOrigem().get(cont).equals(idRelacaoClienteOrigem)){

								form.getIdsContas().set(cont, idsContas);
								form.getIdsDebitos().set(cont, idsDebitos);
								form.getIdsCreditos().set(cont, idsCreditos);
								form.getIdsGuias().set(cont, idsGuias);
								
								bFlagItensLista = true;
							}
						}

						if(!bFlagItensLista){

							form.getIdClienteOrigem().add(idClienteOrigem);
							form.getIdClienteRelacaoOrigem().add(idRelacaoClienteOrigem);

							form.getIdsContas().add(idsContas);
							form.getIdsDebitos().add(idsDebitos);
							form.getIdsCreditos().add(idsCreditos);
							form.getIdsGuias().add(idsGuias);

						}

						idClienteOrigem = form.getIdClienteOrigemSelecionado();
						idRelacaoClienteOrigem = form.getIdClienteRelacaoOrigemSelecionado();

						for(int cont = 0; cont < form.getIdClienteOrigem().size(); cont++){
							if(form.getIdClienteOrigem().get(cont).equals(idClienteOrigem)
											&& form.getIdClienteRelacaoOrigem().get(cont).equals(idRelacaoClienteOrigem)){

								form.setIdsContasSelecionadas(form.getIdsContas().get(cont));
								form.setIdsDebitosSelecionadas(form.getIdsDebitos().get(cont));
								form.setIdsCreditosSelecionadas(form.getIdsCreditos().get(cont));
								form.setIdsGuiasSelecionadas(form.getIdsGuias().get(cont));

							}
						}

					}
				}

			}
		}

		// [SB0001] - Apresentar Débitos/Créditos do Imóvel de Origem
		ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper = fachada.apresentarDebitoCreditoImovelOrigem(idImovelOrigem,
						idClienteOrigem, idRelacaoClienteOrigem);
		// CONTA
		sessao.setAttribute("colecaoConta", obterDebitoImovelOuClienteHelper.getColecaoContasValoresImovel());

		if(indicadorFaturamentoTitularDebito){

			Collection<GuiaPagamento> colecaoGuiaPagamento = new ArrayList<GuiaPagamento>();
			Collection<Integer> idsGuiaPagamento = new ArrayList<Integer>();

			if(obterDebitoImovelOuClienteHelper.getColecaoGuiasPagamentoValores() != null){
				for(GuiaPagamentoValoresHelper guiaPagamentoValoreAtual : obterDebitoImovelOuClienteHelper
								.getColecaoGuiasPagamentoValores()){

					if(!idsGuiaPagamento.contains(guiaPagamentoValoreAtual.getIdGuiaPagamento())){
						GuiaPagamento novaGuiaPagamento = new GuiaPagamento();
						novaGuiaPagamento.setId(guiaPagamentoValoreAtual.getIdGuiaPagamento());
						novaGuiaPagamento.setGuiasPagamentoPrestacao(new HashSet<GuiaPagamentoPrestacao>());
						novaGuiaPagamento.setValorDebito(BigDecimal.ZERO);

						colecaoGuiaPagamento.add(novaGuiaPagamento);
						idsGuiaPagamento.add(guiaPagamentoValoreAtual.getIdGuiaPagamento());
					}

					for(GuiaPagamento guiaPagamentoAtual : colecaoGuiaPagamento){
						if(guiaPagamentoAtual.getId().equals(guiaPagamentoValoreAtual.getIdGuiaPagamento())){

							FiltroGuiaPagamentoPrestacao filtroGuiaPagamentoPrestacao = new FiltroGuiaPagamentoPrestacao();
							filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(
											FiltroGuiaPagamentoPrestacao.GUIA_PAGAMENTO_ID, guiaPagamentoValoreAtual.getIdGuiaPagamento()));
							filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(
											FiltroGuiaPagamentoPrestacao.NUMERO_PRESTACAO, guiaPagamentoValoreAtual.getNumeroPrestacao()));
							filtroGuiaPagamentoPrestacao.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoPrestacao.DEBITO_TIPO);

							GuiaPagamentoPrestacao guiaPagamentoPrestacaoPesq = (GuiaPagamentoPrestacao) Util
											.retonarObjetoDeColecao(fachada.pesquisar(filtroGuiaPagamentoPrestacao,
															GuiaPagamentoPrestacao.class.getName()));

							guiaPagamentoAtual.setValorDebito(guiaPagamentoAtual.getValorDebito().add(
											guiaPagamentoValoreAtual.getValorTotalPrestacao()));

							guiaPagamentoAtual.getGuiasPagamentoPrestacao().add(guiaPagamentoPrestacaoPesq);

						}
					}
				}
			}

			// DEBITO_A_COBRAR
			sessao.setAttribute("colecaoDebitoACobrar", obterDebitoImovelOuClienteHelper.getColecaoDebitoACobrar());

			// CREDITO_A_REALIZAR
			sessao.setAttribute("colecaoCreditoARealizar", obterDebitoImovelOuClienteHelper.getColecaoCreditoARealizar());

			// GUIA_PAGAMENTO
			sessao.setAttribute("colecaoGuiaPagamento", colecaoGuiaPagamento);

			httpServletRequest.setAttribute("indicadorFaturamentoTitularDebito", "S");

		}else{
			// DEBITO_A_COBRAR
			// sessao.setAttribute("colecaoDebitoACobrar",
			// obterDebitoImovelOuClienteHelper.getColecaoDebitoACobrar());

			// CREDITO_A_REALIZAR
			// sessao.setAttribute("colecaoCreditoARealizar",
			// obterDebitoImovelOuClienteHelper.getColecaoCreditoARealizar());

			// GUIA_PAGAMENTO
			// sessao.setAttribute("colecaoGuiaPagamento",
			// obterDebitoImovelOuClienteHelper.getColecaoGuiasPagamentoValores());

			httpServletRequest.removeAttribute("indicadorFaturamentoTitularDebito");
		}

		sessao.setAttribute("colecaoRelacaoImovel", colecaoRelacaoImovel);

		if(idImovelDestino != null){
			sessao.setAttribute("transferenciaEntreImovel", "S");
		}else{
			sessao.removeAttribute("transferenciaEntreImovel");
		}

		return retorno;
	}
}