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

package gcom.gui.arrecadacao.pagamento;

import gcom.arrecadacao.pagamento.FiltroGuiaPagamento;
import gcom.arrecadacao.pagamento.FiltroGuiaPagamentoHistorico;
import gcom.arrecadacao.pagamento.FiltroPagamentoHistorico;
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.arrecadacao.pagamento.GuiaPagamentoHistorico;
import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacao;
import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacaoHistorico;
import gcom.arrecadacao.pagamento.PagamentoHistorico;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoCreditoSituacao;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirConsultarGuiaPagamentoAction
				extends GcomAction {

	/**
	 * @author eduardo henrique
	 * @date 12/08/2008
	 * @since v0.04
	 *        Altera��o na Consulta da Guia para visualiza��o das Presta��es da mesma
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirConsultarGuiaPagamento");

		HttpSession sessao = httpServletRequest.getSession(false);

		// Recebe o id da guia de pagamento para fazer a consulta
		String guiaPagamentoId = httpServletRequest.getParameter("guiaPagamentoId");

		// Se chegar na funcionalidade sem o par�metro indica situa��o de erro
		if(guiaPagamentoId == null || guiaPagamentoId.trim().equals("")){
			throw new ActionServletException("erro.sistema");

		}

		// Consulta do GuiaPagamento
		FiltroGuiaPagamento filtroGuiaPagamento = new FiltroGuiaPagamento();
		filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.ID, guiaPagamentoId));

		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("cliente.clienteTipo");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("localidade");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("guiasPagamentoPrestacao");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("guiasPagamentoPrestacaoHistorico");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("debitoCreditoSituacaoAtual");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("registroAtendimento");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("ordemServico");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("documentoTipo");

		// Para a exibi��o do endere�o do im�vel
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.localidade");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.quadra");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroBairro.bairro.municipio.unidadeFederacao");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.cep");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.logradouro.logradouroTipo");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.logradouro.logradouroTitulo");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("imovel.enderecoReferencia");
		filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade("origem.guiaPagamento.imovel");

		Fachada fachada = Fachada.getInstancia();

		// Orderna as Presta��es por Nr. Presta��o e Tipo de D�bito
		Set colecaoOrdenada = new TreeSet(new Comparator() {

			public int compare(Object a, Object b){

				String codigo1 = "";
				String codigo2 = "";

				if(a instanceof GuiaPagamentoPrestacao){
					codigo1 = ((GuiaPagamentoPrestacao) a).getComp_id().getNumeroPrestacao().toString();
					// + ((GuiaPagamentoPrestacao) a).getComp_id().getDebitoTipoId().toString();
				}else if(a instanceof GuiaPagamentoPrestacaoHistorico){
					codigo1 = ((GuiaPagamentoPrestacaoHistorico) a).getComp_id().getNumeroPrestacao().toString();
					// + ((GuiaPagamentoPrestacaoHistorico)
					// a).getComp_id().getDebitoTipoId().toString();
				}

				if(b instanceof GuiaPagamentoPrestacao){
					codigo2 = ((GuiaPagamentoPrestacao) b).getComp_id().getNumeroPrestacao().toString();
					// + ((GuiaPagamentoPrestacao) b).getComp_id().getDebitoTipoId().toString();
				}else if(b instanceof GuiaPagamentoPrestacaoHistorico){
					codigo2 = ((GuiaPagamentoPrestacaoHistorico) b).getComp_id().getNumeroPrestacao().toString();
					// + ((GuiaPagamentoPrestacaoHistorico)
					// b).getComp_id().getDebitoTipoId().toString();
				}

				return Integer.valueOf(codigo1).compareTo(Integer.valueOf(codigo2));
			}
		});

		Collection colecaoGuias = fachada.pesquisar(filtroGuiaPagamento, GuiaPagamento.class.getName());

		// Caso a consulta n�o retorne nenhum registro isso indica que a guia est� no hist�rico
		if(Util.isVazioOrNulo(colecaoGuias)){
			FiltroGuiaPagamentoHistorico filtroGuiaPagamentoHistorico = new FiltroGuiaPagamentoHistorico();
			filtroGuiaPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoHistorico.ID, guiaPagamentoId));

			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("cliente.clienteTipo");
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("localidade");
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("guiasPagamentoPrestacao");
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("guiasPagamentoPrestacaoHistorico");
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("debitoCreditoSituacaoAtual");
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("registroAtendimento");
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("ordemServico");
			filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade("documentoTipo");

			// Para a exibi��o do endere�o do im�vel
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
		if(guiaPagamento instanceof GuiaPagamento){
			iteratorPrestacoes = ((GuiaPagamento) guiaPagamento).getGuiasPagamentoPrestacao().iterator();
			iteratorPrestacoesHistorico = ((GuiaPagamento) guiaPagamento).getGuiasPagamentoPrestacaoHistorico().iterator();
		}else if(guiaPagamento instanceof GuiaPagamentoHistorico){
			iteratorPrestacoes = ((GuiaPagamentoHistorico) guiaPagamento).getGuiasPagamentoPrestacao().iterator();
			iteratorPrestacoesHistorico = ((GuiaPagamentoHistorico) guiaPagamento).getGuiasPagamentoPrestacaoHistorico().iterator();
		}
		// Necess�rio Iterar nas Presta��es para Carregar os Dados do Tipo de D�bito
		while(iteratorPrestacoes.hasNext()){
			GuiaPagamentoPrestacao guiaPagamentoPrestacao = (GuiaPagamentoPrestacao) iteratorPrestacoes.next();

			FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
			filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, guiaPagamentoPrestacao.getComp_id()
							.getDebitoTipoId()));

			DebitoTipo debitoTipo = (DebitoTipo) Util.retonarObjetoDeColecao(fachada
							.pesquisar(filtroDebitoTipo, DebitoTipo.class.getName()));
			guiaPagamentoPrestacao.setDebitoTipo(debitoTipo);

			FiltroDebitoCreditoSituacao filtroDebitoCreditoSituacao = new FiltroDebitoCreditoSituacao();
			filtroDebitoCreditoSituacao.adicionarParametro(new ParametroSimples(FiltroDebitoCreditoSituacao.ID, guiaPagamentoPrestacao
							.getDebitoCreditoSituacao().getId()));

			DebitoCreditoSituacao debitoCreditoSituacao = (DebitoCreditoSituacao) Util.retonarObjetoDeColecao(fachada.pesquisar(
							filtroDebitoCreditoSituacao, DebitoCreditoSituacao.class.getName()));
			guiaPagamentoPrestacao.setDebitoCreditoSituacao(debitoCreditoSituacao);

			colecaoOrdenada.add(guiaPagamentoPrestacao);
		}

		// Necess�rio Iterar nas Presta��es do hist�rico para Carregar os Dados do Tipo de D�bito
		while(iteratorPrestacoesHistorico.hasNext()){
			GuiaPagamentoPrestacaoHistorico guiaPagamentoPrestacaoHistorico = (GuiaPagamentoPrestacaoHistorico) iteratorPrestacoesHistorico
							.next();

			FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
			filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, guiaPagamentoPrestacaoHistorico.getComp_id()
							.getDebitoTipoId()));

			DebitoTipo debitoTipo = (DebitoTipo) Util.retonarObjetoDeColecao(fachada
							.pesquisar(filtroDebitoTipo, DebitoTipo.class.getName()));
			guiaPagamentoPrestacaoHistorico.setDebitoTipo(debitoTipo);

			FiltroDebitoCreditoSituacao filtroDebitoCreditoSituacao = new FiltroDebitoCreditoSituacao();
			filtroDebitoCreditoSituacao.adicionarParametro(new ParametroSimples(FiltroDebitoCreditoSituacao.ID,
							guiaPagamentoPrestacaoHistorico.getDebitoCreditoSituacao().getId()));

			DebitoCreditoSituacao debitoCreditoSituacao = (DebitoCreditoSituacao) Util.retonarObjetoDeColecao(fachada.pesquisar(
							filtroDebitoCreditoSituacao, DebitoCreditoSituacao.class.getName()));
			guiaPagamentoPrestacaoHistorico.setDebitoCreditoSituacao(debitoCreditoSituacao);

			colecaoOrdenada.add(guiaPagamentoPrestacaoHistorico);
		} // Fim do while

		montarHintGuiaPagamentoPrestacao(colecaoOrdenada);

		/*
		 * // Seta novamente a cole��o, j� ordenada.
		 * if(guiaPagamento instanceof GuiaPagamento){
		 * ((GuiaPagamento) guiaPagamento).setGuiasPagamentoPrestacao(colecaoOrdenada);
		 * }else if(guiaPagamento instanceof GuiaPagamentoHistorico){
		 * ((GuiaPagamentoHistorico) guiaPagamento).setGuiasPagamentoPrestacao(colecaoOrdenada);
		 * }
		 */
		for(Object teste : colecaoOrdenada){
			if(teste instanceof GuiaPagamentoPrestacao){
				GuiaPagamentoPrestacao guia = (GuiaPagamentoPrestacao) teste;
				System.out.println("Atributo indicador = " + guia.getIndicadorPagamentoPendente());
				System.out.println("Atributo indicador STR = " + guia.getIndicadorPagamentoPendenteStr());
				System.out.println("Atributo indicador HINT = " + guia.getIndicadorPagamentoHint());
			}else{
				GuiaPagamentoPrestacaoHistorico guia = (GuiaPagamentoPrestacaoHistorico) teste;
				System.out.println("HISTORICO - Atributo indicador = " + guia.getIndicadorPagamentoPendente());
				System.out.println("HISTORICO - Atributo indicador STR = " + guia.getIndicadorPagamentoPendenteStr());
				System.out.println("HISTORICO - Atributo indicador HINT = " + guia.getIndicadorPagamentoHint());

			}
		}

		// Envia o objeto consultado para a p�gina
		httpServletRequest.setAttribute("guiaPagamento", guiaPagamento);

		// envia uma flag que ser� verificado no cliente_resultado_pesquisa.jsp
		// para saber se ir� usar o enviar dados ou o enviar dados parametros
		if(httpServletRequest.getParameter("caminhoRetornoTelaConsultaGuiaPagamento") != null){
			sessao.setAttribute("caminhoRetornoTelaConsultaGuiaPagamento", httpServletRequest
							.getParameter("caminhoRetornoTelaConsultaGuiaPagamento"));
		}

		return retorno;
	}

	/**
	 * [UC0212] Manter Guia de Pagamento
	 * [3.9.7. Paga ]
	 * ..
	 * C�pia trecho de m�todo em ExibirAtualizarGuiaPagamentoAction.java, foi ajustado para
	 * preencher os campos da entidade para preencher os hint .
	 * ..
	 * [UC0188] Manter Guia de Pagamento
	 * [SB0001 � Exibir Presta��es da Guia de Pagamento]
	 */
	private void montarHintGuiaPagamentoPrestacao(Set colecaoOrdenada){

		for(Object object : colecaoOrdenada){
			if(object instanceof GuiaPagamentoPrestacao){
				GuiaPagamentoPrestacao guiaPagamentoPrestacao = (GuiaPagamentoPrestacao) object;
				preencherParaGuiaDePagamento(guiaPagamentoPrestacao);

			}else{
				GuiaPagamentoPrestacaoHistorico guiaPagamentoPrestacaoHistorico = (GuiaPagamentoPrestacaoHistorico) object;
				preencherParaGuiaDePagamentoHistorico(guiaPagamentoPrestacaoHistorico);
			}
		}

		// INICIO - Rotina que preenche os hints de Indicador de Pagamento
	}

	private void preencherParaGuiaDePagamento(GuiaPagamentoPrestacao guiaPagamentoPrestacao){

		if(guiaPagamentoPrestacao.getIndicadorPagamentoPendente().intValue() == ConstantesSistema.SIM.intValue()){

			FiltroPagamentoHistorico filtroPagamentoHistorico = new FiltroPagamentoHistorico();
			filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.GUIA_PAGAMENTO_ID,
							guiaPagamentoPrestacao.getComp_id().getGuiaPagamentoId()));
			filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.NUMERO_PRESTACAO,
							guiaPagamentoPrestacao.getComp_id().getNumeroPrestacao()));

			Collection colecaoPagamentoHistorico = Fachada.getInstancia().pesquisar(filtroPagamentoHistorico,
							PagamentoHistorico.class.getName());

			if(!Util.isVazioOrNulo(colecaoPagamentoHistorico)){

				guiaPagamentoPrestacao.setIndicadorPagamentoHint(Util.formatarData(((PagamentoHistorico) Util
								.retonarObjetoDeColecao(colecaoPagamentoHistorico)).getDataPagamento())
								+ " - "
								+ Util.formatarMoedaReal(((PagamentoHistorico) Util.retonarObjetoDeColecao(colecaoPagamentoHistorico))
												.getValorPagamento()));
			}

		}
	}

	private void preencherParaGuiaDePagamentoHistorico(GuiaPagamentoPrestacaoHistorico guiaPagamentoPrestacao){

		if(guiaPagamentoPrestacao.getIndicadorPagamentoPendente().intValue() == ConstantesSistema.SIM.intValue()){

			FiltroPagamentoHistorico filtroPagamentoHistorico = new FiltroPagamentoHistorico();
			filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.GUIA_PAGAMENTO_ID,
							guiaPagamentoPrestacao.getComp_id().getGuiaPagamentoId()));
			filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.NUMERO_PRESTACAO,
							guiaPagamentoPrestacao.getComp_id().getNumeroPrestacao()));

			Collection colecaoPagamentoHistorico = Fachada.getInstancia().pesquisar(filtroPagamentoHistorico,
							PagamentoHistorico.class.getName());

			if(!Util.isVazioOrNulo(colecaoPagamentoHistorico)){

				guiaPagamentoPrestacao.setIndicadorPagamentoHint(Util.formatarData(((PagamentoHistorico) Util
								.retonarObjetoDeColecao(colecaoPagamentoHistorico)).getDataPagamento())
								+ " - "
								+ Util.formatarMoedaReal(((PagamentoHistorico) Util.retonarObjetoDeColecao(colecaoPagamentoHistorico))
												.getValorPagamento()));
			}
		}
	}
}
