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

import gcom.arrecadacao.pagamento.FiltroGuiaPagamento;
import gcom.arrecadacao.pagamento.FiltroGuiaPagamentoPrestacao;
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacao;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade realizar a transferência de débitos e créditos entre
 * os imóveis de origem e destino
 * 
 * @author Raphael Rossiter
 * @date 12/06/2007
 */
public class TransferirDebitoCreditoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		TransferenciaDebitoCreditoDadosImovelActionForm form = (TransferenciaDebitoCreditoDadosImovelActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		Boolean indicadorFaturamentoTitularDebito = false;
		Boolean indicadorTermoAssuncao = false;
		try{
			if(ParametroFaturamento.P_INDICADOR_FATURAMENTO_ATUAL_TITULAR_DEBITO_IMOVEL.executar().equals(ConstantesSistema.SIM.toString())){
				indicadorFaturamentoTitularDebito = true;
			}
			
			if(ParametroFaturamento.P_MODELO_TERMO_ASSUNCAO_DIVIDA.executar().equals("1")){
				indicadorTermoAssuncao = true;
			}			
		}catch(ControladorException e){
			e.printStackTrace();
		}

		Integer idRa = Integer.valueOf(form.getIdRegistroAtendimento());
		Integer idImovelDestino = null;

		if(form.getIdImovelDestino() != null && !form.getIdImovelDestino().equals("")){
			idImovelDestino = Integer.valueOf(form.getIdImovelDestino());
		}

		Integer idClienteOrigem = null;
		Integer idRelacaoClienteOrigem = null;

		if(form.getIdClienteImovelSelecionado() != null && !form.getIdClienteImovelSelecionado().equals("")){
			String[] dados = form.getIdClienteImovelSelecionado().split("\\.");

			idClienteOrigem = Integer.valueOf(dados[1]);
			idRelacaoClienteOrigem = Integer.valueOf(dados[0]);

			form.setIdClienteOrigemSelecionado(idClienteOrigem);
			form.setIdClienteRelacaoOrigemSelecionado(idRelacaoClienteOrigem);
		}
		
		Integer idClienteDestino = null;
		if(form.getIdClienteDestino() != null && !form.getIdClienteDestino().equals("")){
			idClienteDestino = Integer.valueOf(form.getIdClienteDestino());
		}		

		if(idImovelDestino != null){
			// Validação dos dados informados referentes aos imóveis de origem e destino
			fachada.validarTransferenciaDebitoCreditoDadosImoveis(idRa, idImovelDestino);
		}

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		String idsContas = httpServletRequest.getParameter("conta");
		String idsDebitos = "";
		String idsCreditos = "";
		String idsGuias = "";

		if(indicadorFaturamentoTitularDebito){
			idsDebitos = httpServletRequest.getParameter("debito");
			idsCreditos = httpServletRequest.getParameter("credito");
			idsGuias = httpServletRequest.getParameter("guiaPagamento");

			if(form.getIdClienteOrigem() != null && !form.getIdClienteOrigem().isEmpty() && form.getIdClienteRelacaoOrigem() != null
							&& !form.getIdClienteRelacaoOrigem().isEmpty()){
				boolean bFlagItensLista = false;

				for(int cont = 0; cont < form.getIdClienteOrigem().size(); cont++){
					if(form.getIdClienteOrigem().get(cont) != null && form.getIdClienteOrigem().get(cont).equals(idClienteOrigem)
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
					if(form.getIdClienteOrigem().get(cont) != null && form.getIdClienteOrigem().get(cont).equals(idClienteOrigem)
									&& form.getIdClienteRelacaoOrigem().get(cont).equals(idRelacaoClienteOrigem)){

						form.setIdsContasSelecionadas(form.getIdsContas().get(cont));
						form.setIdsDebitosSelecionadas(form.getIdsDebitos().get(cont));
						form.setIdsCreditosSelecionadas(form.getIdsCreditos().get(cont));
						form.setIdsGuiasSelecionadas(form.getIdsGuias().get(cont));

					}
				}

			}else{
				form.getIdClienteOrigem().add(idClienteOrigem);
				form.getIdClienteRelacaoOrigem().add(idRelacaoClienteOrigem);

				form.getIdsContas().add(idsContas);
				form.getIdsDebitos().add(idsDebitos);
				form.getIdsCreditos().add(idsCreditos);
				form.getIdsGuias().add(idsGuias);

			}
		}else{
			// String idsDebitos = httpServletRequest.getParameter("debito");
			// String idsCreditos = httpServletRequest.getParameter("credito");
			// String idsGuias = httpServletRequest.getParameter("guiaPagamento");
		}


		Integer idTransferencia = null;
		if(indicadorFaturamentoTitularDebito){
			if(idImovelDestino != null){
				// [SB0001] - Apresentar Débitos/Créditos do Imóvel de Origem
				ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper = fachada.apresentarDebitoCreditoImovelOrigem(
								Integer.valueOf(form.getIdImovelOrigem()), idClienteOrigem, idRelacaoClienteOrigem);

				Collection<Conta> colecaoContas = this.obterContasSelecionadas(idsContas,
								obterDebitoImovelOuClienteHelper.getColecaoContasValoresImovel());

				Collection<DebitoACobrar> colecaoDebitos = this.obterDebitosSelecionados(idsDebitos,
								obterDebitoImovelOuClienteHelper.getColecaoDebitoACobrar());

				Collection<CreditoARealizar> colecaoCreditos = this.obterCreditosSelecionadas(idsCreditos,
								obterDebitoImovelOuClienteHelper.getColecaoCreditoARealizar());

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

				Collection<GuiaPagamento> colecaoGuiaPagamentos = this.obterGuiasSelecionadas(idsGuias, colecaoGuiaPagamento, fachada);
				
				idTransferencia = fachada.transferirDebitosCreditos(Integer.valueOf(form.getIdImovelOrigem()), idImovelDestino,
								colecaoContas, colecaoDebitos, colecaoCreditos, colecaoGuiaPagamentos, usuarioLogado, idRa, idsContas);
			}else{

				idTransferencia = fachada.transferirDebitosCreditosCliente(idRa, Integer.valueOf(form.getIdImovelOrigem()),
								form.getIdClienteOrigem(), form.getIdClienteRelacaoOrigem(), idClienteDestino, form.getIdsContas(),
								form.getIdsDebitos(), form.getIdsCreditos(), form.getIdsGuias(), usuarioLogado);
			}

		}else{
			// [SB0001] - Apresentar Débitos/Créditos do Imóvel de Origem
			ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper = fachada.apresentarDebitoCreditoImovelOrigem(
							Integer.valueOf(form.getIdImovelOrigem()), idClienteOrigem, idRelacaoClienteOrigem);

			Collection<Conta> colecaoContas = this.obterContasSelecionadas(idsContas,
							obterDebitoImovelOuClienteHelper.getColecaoContasValoresImovel());

			idTransferencia = fachada.transferirDebitosCreditos(Integer.valueOf(form.getIdImovelOrigem()), idImovelDestino, colecaoContas,
							null, null, null, usuarioLogado, idRa, idsContas);
		}

		if (indicadorTermoAssuncao) {
			montarPaginaSucesso(httpServletRequest, "Transferência realizada com sucesso.", "Transferencia de Débitos",
							"exibirTransferenciaDebitoCreditoDadosImovelAction.do?menu=sim",
							"gerarRelatorioTransferenciaAction.do?idTransferencia=" + idTransferencia, "Imprimir Termo");
		} else {
			montarPaginaSucesso(httpServletRequest, "Transferência realizada com sucesso.", "Transferencia de Débitos",
							"exibirTransferenciaDebitoCreditoDadosImovelAction.do?menu=sim");
		}

		return retorno;
	}

	private Collection<Conta> obterContasSelecionadas(String idsContas, Collection colecaoContasBase){

		Collection<Conta> colecaoContas = null;

		if(idsContas != null && !idsContas.equals("")){

			colecaoContas = new ArrayList();

			Iterator itColecaoContas = colecaoContasBase.iterator();
			ContaValoresHelper contaValoresHelper = null;

			String[] idsContasArray = idsContas.split(",");

			while(itColecaoContas.hasNext()){

				contaValoresHelper = (ContaValoresHelper) itColecaoContas.next();

				for(int x = 0; x < idsContasArray.length; x++){

					if(contaValoresHelper.getConta().getId().equals(Integer.valueOf(idsContasArray[x]))){
						colecaoContas.add(contaValoresHelper.getConta());
					}
				}
			}
		}

		return colecaoContas;
	}

	private Collection<DebitoACobrar> obterDebitosSelecionados(String idsDebitos, Collection colecaoDebitosBase){

		Collection<DebitoACobrar> colecaoDebitos = null;

		if(idsDebitos != null && !idsDebitos.equals("")){

			colecaoDebitos = new ArrayList();

			Iterator itColecaoDebitos = colecaoDebitosBase.iterator();
			DebitoACobrar debitoACobrar = null;

			String[] idsDebitosArray = idsDebitos.split(",");

			while(itColecaoDebitos.hasNext()){

				debitoACobrar = (DebitoACobrar) itColecaoDebitos.next();

				for(int x = 0; x < idsDebitosArray.length; x++){

					if(debitoACobrar.getId().equals(Integer.valueOf(idsDebitosArray[x]))){
						colecaoDebitos.add(debitoACobrar);
					}
				}
			}
		}

		return colecaoDebitos;
	}

	private Collection<CreditoARealizar> obterCreditosSelecionadas(String idsCreditos, Collection colecaoCreditosBase){

		Collection<CreditoARealizar> colecaoCreditos = null;

		if(idsCreditos != null && !idsCreditos.equals("")){

			colecaoCreditos = new ArrayList();

			Iterator itColecaoCreditos = colecaoCreditosBase.iterator();
			CreditoARealizar creditoARealizar = null;

			String[] idsCreditosArray = idsCreditos.split(",");

			while(itColecaoCreditos.hasNext()){

				creditoARealizar = (CreditoARealizar) itColecaoCreditos.next();

				for(int x = 0; x < idsCreditosArray.length; x++){

					if(creditoARealizar.getId().equals(Integer.valueOf(idsCreditosArray[x]))){
						colecaoCreditos.add(creditoARealizar);
					}
				}
			}
		}

		return colecaoCreditos;
	}

	private Collection<GuiaPagamento> obterGuiasSelecionadas(String idsGuias, Collection colecaoGuiasBase, Fachada fachada){

		Collection<GuiaPagamento> colecaoGuias = null;

		if(idsGuias != null && !idsGuias.equals("")){

			colecaoGuias = new ArrayList();

			Iterator itColecaoGuias = colecaoGuiasBase.iterator();
			GuiaPagamento guiaPagamentoAtual = null;

			String[] idsGuiasArray = idsGuias.split(",");

			while(itColecaoGuias.hasNext()){

				guiaPagamentoAtual = (GuiaPagamento) itColecaoGuias.next();

				for(int x = 0; x < idsGuiasArray.length; x++){

					// TODO verificar -> Deverá recuperar as prestações selecionadas, não as guias.
					// Customização.
					if(guiaPagamentoAtual.getId().equals(Integer.valueOf(idsGuiasArray[x]))){
						FiltroGuiaPagamento filtroGuiaPagamento = new FiltroGuiaPagamento();
						filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.ID, guiaPagamentoAtual.getId()));
						Collection<GuiaPagamento> colecaoGuiaPesquisada = fachada.pesquisar(filtroGuiaPagamento,
										GuiaPagamento.class.getName());
						if(colecaoGuiaPesquisada != null && !colecaoGuiaPesquisada.isEmpty()){
							colecaoGuias.add(colecaoGuiaPesquisada.iterator().next());
						}
					}
				}
			}
		}

		return colecaoGuias;
	}

}
