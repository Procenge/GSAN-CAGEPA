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

package gcom.gui.faturamento.debito;

import gcom.arrecadacao.pagamento.FiltroPagamento;
import gcom.arrecadacao.pagamento.Pagamento;
import gcom.cobranca.parcelamento.FiltroParcelamentoItem;
import gcom.cobranca.parcelamento.ParcelamentoItem;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.faturamento.debito.FiltroDebitoACobrar;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.filtro.ParametroNaoNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela exibi��o da tela de consultar d�bito cobrado
 * 
 * @author Fernanda Paiva
 * @created 16 de Janeiro de 2006
 */
public class ExibirConsultarDebitoACobrarAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a vari�vel de retorno e seta o mapeamento para a tela de
		// consultar d�bito cobrado
		ActionForward retorno = actionMapping.findForward("exibirConsultarDebitoACobrar");

		// cria uma inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// cria uma inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		// recupera o c�digo da conta do request
		String idImovel = httpServletRequest.getParameter("imovelID");

		String idDebito = httpServletRequest.getParameter("debitoID");

		String idParcelamento = httpServletRequest.getParameter("parcelamentoID");

		/*
		 * Pesquisando o debito a partir do id imovel
		 * =====================================================================
		 */

		if(idImovel != null && !idImovel.equalsIgnoreCase("")){

			// cria o filtro do imovel
			String imovel = fachada.pesquisarInscricaoImovel(new Integer(idImovel), true);
			String enderecoFormatado = "";
			try{
				enderecoFormatado = fachada.pesquisarEnderecoFormatado(new Integer(idImovel));
			}catch(NumberFormatException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(ControladorException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			httpServletRequest.setAttribute("idImovelConsultado", idImovel);
			httpServletRequest.setAttribute("enderecoFormatado", enderecoFormatado);

			// se n�o encontrou nenhum imovel com o c�digo informado
			if(imovel == null || imovel.equalsIgnoreCase("")){
				throw new ActionServletException("atencao.imovel.inexistente");
			}

			// seta o objeto conta na sess�o
			httpServletRequest.setAttribute("imovelId", idImovel);
		}
		// ====================================================================

		// cria a cole��o de d�bitos a cobrar
		Collection colecaoDebitoACobrarDetalhado;

		/*
		 * D�bitos Cobrados (Carregar cole��o)
		 * ======================================================================
		 */
		// se n�o existir a cole��o na sess�o
		if(idParcelamento != null && !idParcelamento.equals("")){
			FiltroParcelamentoItem filtroParcelamentoItem = new FiltroParcelamentoItem();

			// seta o c�digo do imovel no filtro
			filtroParcelamentoItem.adicionarParametro(new ParametroSimples(FiltroParcelamentoItem.PARCELAMENTO_ID, idParcelamento));

			filtroParcelamentoItem.adicionarParametro(new ParametroNaoNulo(FiltroParcelamentoItem.DEBITO_A_COBRAR_GERAL_ID));

			// carrega o debitoACobrar
			filtroParcelamentoItem.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeral.debitoACobrar.debitoTipo");
			filtroParcelamentoItem.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeral.debitoACobrar.debitoCreditoSituacaoAtual");
			// filtroParcelamentoItem
			// .adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeral.debitoACobrar");
			/*
			 * filtroParcelamentoItem.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeral.debitoACobrar.geracaoDebito"
			 * );
			 * filtroParcelamentoItem.adicionarCaminhoParaCarregamentoEntidade(
			 * "debitoACobrarGeral.debitoACobrar.anoMesReferenciaDebito");
			 * filtroParcelamentoItem.adicionarCaminhoParaCarregamentoEntidade(
			 * "debitoACobrarGeral.debitoACobrar.anoMesCobrancaDebito");
			 * filtroParcelamentoItem.adicionarCaminhoParaCarregamentoEntidade(
			 * "debitoACobrarGeral.debitoACobrar.numeroPrestacaoCobradas");
			 * filtroParcelamentoItem.adicionarCaminhoParaCarregamentoEntidade(
			 * "debitoACobrarGeral.debitoACobrar.numeroPrestacaoDebito");
			 * filtroParcelamentoItem.adicionarCaminhoParaCarregamentoEntidade(
			 * "debitoACobrarGeral.debitoACobrar.valorDebito");
			 * filtroParcelamentoItem.adicionarCaminhoParaCarregamentoEntidade(
			 * "debitoACobrarGeral.debitoACobrar.valorTotal");
			 * filtroParcelamentoItem.adicionarCaminhoParaCarregamentoEntidade(
			 * "debitoACobrarGeral.debitoACobrar.percentualTaxaJurosFinanciamento");
			 */
			filtroParcelamentoItem.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeral.debitoACobrar.registroAtendimento");
			filtroParcelamentoItem.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeral.debitoACobrar.ordemServico");
			filtroParcelamentoItem.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeral.debitoACobrar.financiamentoTipo");
			filtroParcelamentoItem.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeral.debitoACobrar.cobrancaForma");

			filtroParcelamentoItem.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeral.debitoACobrarHistorico.debitoTipo");
			filtroParcelamentoItem
							.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeral.debitoACobrarHistorico.debitoCreditoSituacaoAtual");

			filtroParcelamentoItem
							.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeral.debitoACobrarHistorico.registroAtendimento");
			filtroParcelamentoItem.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeral.debitoACobrarHistorico.ordemServico");
			filtroParcelamentoItem.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeral.debitoACobrarHistorico.financiamentoTipo");
			filtroParcelamentoItem.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeral.debitoACobrarHistorico.cobrancaForma");
			// carrega o parcelamento
			filtroParcelamentoItem.adicionarCaminhoParaCarregamentoEntidade("parcelamento");

			// carrega o im�vel origem do d�bito a cobrar
			filtroParcelamentoItem
							.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeral.debitoACobrar.debitoACobrarGeralOrigem.debitoACobrar.imovel");

			Collection colecaoParcelamentoItem = fachada.pesquisar(filtroParcelamentoItem, ParcelamentoItem.class.getName());

			if(colecaoParcelamentoItem == null || colecaoParcelamentoItem.isEmpty()){
				throw new ActionServletException("atencao.faturamento.debito_a_cobrar.inexistente");
			}else{
				// seta a cole��o de d�bitos cobrados na sess�o
				sessao.setAttribute("colecaoParcelamentoItem", colecaoParcelamentoItem);
			}
		}else{
			// cria o filtro de d�bito a cobrar
			FiltroDebitoACobrar filtroDebitoACobrar = new FiltroDebitoACobrar();

			if(idDebito != null){

				filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.ID, idDebito));

			}

			if(idImovel != null){

				filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.IMOVEL_ID, idImovel));

			}

			// carrega o tipo de d�bito
			filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");

			// carrega o tipo de d�bito
			filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("debitoCreditoSituacaoAtual");

			// carrega o tipo de d�bito
			filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("ordemServico");

			// carrega o tipo de d�bito
			filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("registroAtendimento");

			// carrega o tipo de d�bito
			filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("financiamentoTipo");

			// carrega o tipo de d�bito
			filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("cobrancaForma");

			// carrega o im�vel origem do d�bito a cobrar
			filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeralOrigem.debitoACobrar.imovel");

			filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoACobrar.PARCELAMENTO);

			// pesquisa a cole��o de d�bitos cobrados
			colecaoDebitoACobrarDetalhado = fachada.pesquisar(filtroDebitoACobrar, DebitoACobrar.class.getName());

			if(colecaoDebitoACobrarDetalhado == null || colecaoDebitoACobrarDetalhado.isEmpty()){

				throw new ActionServletException("atencao.faturamento.debito_a_cobrar.inexistente");

			}else{

				Collection<DebitoACobrar> collDebitosAgrupados = this.obterDebitosACobrarAgrupados(httpServletRequest,
								colecaoDebitoACobrarDetalhado);

				if(collDebitosAgrupados != null && !collDebitosAgrupados.isEmpty()){

					colecaoDebitoACobrarDetalhado.addAll(collDebitosAgrupados);

				}

				// seta a cole��o de d�bitos cobrados na sess�o
				sessao.setAttribute("colecaoDebitoACobrarDetalhado", colecaoDebitoACobrarDetalhado);

			}

		}
		// ====================================================================

		// envia uma flag que ser� verificado no cliente_resultado_pesquisa.jsp
		// para saber se ir� usar o enviar dados ou o enviar dados parametros
		if(httpServletRequest.getParameter("caminhoRetornoTelaConsultaDebitoACobrar") != null){
			sessao.setAttribute("caminhoRetornoTelaConsultaDebitoACobrar", httpServletRequest
							.getParameter("caminhoRetornoTelaConsultaDebitoACobrar"));
		}

		// retorna o mapeamento contido na vari�vel retorno
		return retorno;

	}

	private Collection<DebitoACobrar> obterDebitosACobrarAgrupados(HttpServletRequest httpServletRequest,
					Collection<DebitoACobrar> colecaoDebitoACobrarDetalhado){

		Collection<DebitoACobrar> coll = null;
		Collection<DebitoACobrar> collDebitoACobrar = new ArrayList<DebitoACobrar>();

		String debitosAgrupados = httpServletRequest.getParameter(ConstantesSistema.DEBITOS_AGRUPADOS);

		String idDebito = httpServletRequest.getParameter("debitoID");

		if(StringUtils.isNotBlank(idDebito) && StringUtils.isNotBlank(debitosAgrupados)
						&& Short.valueOf(debitosAgrupados).equals(ConstantesSistema.SIM)){

			DebitoACobrar debitoACobrar = colecaoDebitoACobrarDetalhado.iterator().next();

			if(debitoACobrar != null){

				coll = Fachada.getInstancia().pesquisar(this.obterFiltroDebitoACobrar(debitoACobrar), DebitoACobrar.class.getName());

			}

		}

		if(coll != null){

			for(DebitoACobrar debitoACobrar : coll){

				Boolean existePagamento = Boolean.FALSE;
				Boolean numPrestCobradaMenorNumPrestDeb = Boolean.FALSE;

				FiltroPagamento filtroPagamento = new FiltroPagamento();
				filtroPagamento.adicionarParametro(new ParametroSimples(FiltroPagamento.DEBITO_A_COBRAR_ID, debitoACobrar.getId()));

				Collection<Pagamento> pagamentos = Fachada.getInstancia().pesquisar(filtroPagamento, Pagamento.class.getName());

				if(pagamentos != null && !pagamentos.isEmpty()){

					existePagamento = Boolean.TRUE;

				}

				if(debitoACobrar.getNumeroPrestacaoCobradas() < debitoACobrar.getNumeroPrestacaoDebito()){

					numPrestCobradaMenorNumPrestDeb = Boolean.TRUE;

				}

				if(!existePagamento && numPrestCobradaMenorNumPrestDeb){

					collDebitoACobrar.add(debitoACobrar);

				}

			}

		}

		this.ordernarColecaoDebitosACobrar(collDebitoACobrar);

		return collDebitoACobrar;

	}

	private FiltroDebitoACobrar obterFiltroDebitoACobrar(DebitoACobrar debitoACobrar){

		FiltroDebitoACobrar filtroDebitoACobrar = new FiltroDebitoACobrar();
		filtroDebitoACobrar.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroDebitoACobrar.ID, debitoACobrar.getId()));
		filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.IMOVEL_ID, debitoACobrar.getImovel().getId()));
		filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.DEBITO_CREDITO_SITUACAO_ATUAL_ID,
						DebitoCreditoSituacao.NORMAL));
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("debitoCreditoSituacaoAtual");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("ordemServico");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("registroAtendimento");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("financiamentoTipo");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("cobrancaForma");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("debitoACobrarGeralOrigem.debitoACobrar.imovel");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("parcelamento");

		if(debitoACobrar.getParcelamento() != null && debitoACobrar.getDebitoTipo() != null){

			filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.PARCELAMENTO_ID, debitoACobrar
							.getParcelamento().getId()));
			filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.DEBITO_TIPO_ID, debitoACobrar.getDebitoTipo()
							.getId()));

		}else{

			if(debitoACobrar.getDebitoTipo() != null){

				filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.DEBITO_TIPO_ID, debitoACobrar
								.getDebitoTipo().getId()));

			}

			if(debitoACobrar.getAnoMesReferenciaDebito() != null){

				filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.REFERENCIA_DEBITO, debitoACobrar
								.getAnoMesReferenciaDebito()));

			}

			if(debitoACobrar.getAnoMesCobrancaDebito() != null){

				filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.REFERENCIA_COBRANCA_DEBITO, debitoACobrar
								.getAnoMesCobrancaDebito()));

			}

			if(debitoACobrar.getAnoMesReferenciaContabil() != null){

				filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.REFERENCIA_CONTABIL_DEBITO, debitoACobrar
								.getAnoMesReferenciaContabil()));

			}

		}

		return filtroDebitoACobrar;

	}

	private Collection<DebitoACobrar> ordernarColecaoDebitosACobrar(Collection<DebitoACobrar> collDebitoACobrar){

		List<DebitoACobrar> list = new ArrayList<DebitoACobrar>();
		list.addAll(collDebitoACobrar);

		List sortFields = new ArrayList();

		sortFields.add(new BeanComparator("debitoTipo.descricao"));
		sortFields.add(new BeanComparator("anoMesReferenciaDebito"));

		ComparatorChain multiSort = new ComparatorChain(sortFields);
		Collections.sort((List) list, multiSort);

		return list;

	}

}
