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

package gcom.gui.cobranca.parcelamento;

import gcom.cadastro.sistemaparametro.FiltroSistemaParametro;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.parcelamento.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que define o pré-processamento da página de
 * inserir Informações do Parcelamento por Quantidade de Reparcelamentos
 * 
 * @author Vivianne Sousa
 * @created 03/05/2006
 */

public class ExibirInserirPrestacoesParcelamentoPerfilAction
				extends GcomAction {

	/**
	 * Este caso de uso permite a inclusão de um novo perfil de parcelamento
	 * [UC0220] Inserir Perfil de Parcelamento
	 * 
	 * @author Vivianne Sousa
	 * @date 03/05/2006
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		HttpSession sessao = httpServletRequest.getSession(false);

		String quantidadeReparcelamento = null;
		if(httpServletRequest.getParameter("qtdeMaximaReparcelamento") != null){

			quantidadeReparcelamento = (String) httpServletRequest.getParameter("qtdeMaximaReparcelamento");
		}else{

			quantidadeReparcelamento = (String) sessao.getAttribute("qtdeMaximaReparcelamento");
		}

		String desabilitarTaxaJuros = null;
		if(httpServletRequest.getParameter("desabilitarTaxaJuros") != null){

			desabilitarTaxaJuros = (String) httpServletRequest.getParameter("desabilitarTaxaJuros");
		}else{

			desabilitarTaxaJuros = (String) sessao.getAttribute("desabilitarTaxaJuros");
		}

		String adicionarReparcelamento = null;
		if(httpServletRequest.getParameter("adicionarReparcelamento") != null){

			adicionarReparcelamento = (String) httpServletRequest.getParameter("adicionarReparcelamento");
		}else{

			adicionarReparcelamento = (String) sessao.getAttribute("adicionarReparcelamento");
		}

		String primeiraVez = null;
		if(httpServletRequest.getParameter("primeiraVez") != null){

			primeiraVez = (String) httpServletRequest.getParameter("primeiraVez");
		}else{

			primeiraVez = (String) sessao.getAttribute("primeiraVez");
		}

		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("exibirAdicionarPercentualFaixaValorPopupAction"))
						&& httpServletRequest.getParameter("exibirAdicionarPercentualFaixaValorPopupAction").equalsIgnoreCase("S")){

			this.exibirAdicionarPercentualFaixaValorPopupAction(actionMapping, actionForm, httpServletRequest, httpServletResponse,
							primeiraVez);
		}

		ActionForward retorno = actionMapping.findForward("inserirPrestacoesParcelamentoPerfil");
		InserirPrestacoesParcelamentoPerfilActionForm inserirPrestacoesParcelamentoPerfilActionForm = (InserirPrestacoesParcelamentoPerfilActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		sessao.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper");

		if(Util.isVazioOuBranco(quantidadeReparcelamento)){
			// quantidadeReparcelamento = (String) sessao.getAttribute("qtdeMaxReparcelamento");
			quantidadeReparcelamento = inserirPrestacoesParcelamentoPerfilActionForm.getQtdeMaximaReparcelamentoAux();
		}else{
			inserirPrestacoesParcelamentoPerfilActionForm.setQtdeMaximaReparcelamentoAux(quantidadeReparcelamento);
		}

		httpServletRequest.setAttribute("qtdeMaximaReparcelamento", quantidadeReparcelamento);
		sessao.setAttribute("qtdeMaxReparcelamento", quantidadeReparcelamento);

		Boolean percentualValorReparceladoReadOnly = false;

		// filtro para descobrir o percentual mínimo de entrada permitido para financiamento
		FiltroSistemaParametro filtroSistemaParametro = new FiltroSistemaParametro();
		Collection colecaoSistemaParametros;

		colecaoSistemaParametros = fachada.pesquisar(filtroSistemaParametro, SistemaParametro.class.getName());
		SistemaParametro sistemaParametro = (SistemaParametro) colecaoSistemaParametros.iterator().next();
		String percentualFinanciamentoEntradaMinima = "" + sistemaParametro.getPercentualFinanciamentoEntradaMinima();
		httpServletRequest.setAttribute("percentualFinanciamentoEntradaMinima", percentualFinanciamentoEntradaMinima);

		if(httpServletRequest.getParameter("adicionarPrestacao") != null
						&& httpServletRequest.getParameter("adicionarPrestacao").equalsIgnoreCase("S")
						&& inserirPrestacoesParcelamentoPerfilActionForm.getQuantidadeMaximaPrestacao() != null){

			// -------------------- bt adicionarPrestacao ----------------------

			atualizaColecaoParcelamentoQuantidadePrestacaoNaSessao(sessao, httpServletRequest);

			atualizaColecaoParcelamentoFaixaValorNaSessao(sessao, httpServletRequest);

			adicionarParcelamentoQuantidadePrestacao(sessao, fachada, inserirPrestacoesParcelamentoPerfilActionForm,
							percentualValorReparceladoReadOnly);

			if(!Util.isVazioOuBranco(httpServletRequest.getParameter("limparCampos"))
							&& httpServletRequest.getParameter("limparCampos").equals("S")){

				inserirPrestacoesParcelamentoPerfilActionForm.reset(actionMapping, httpServletRequest);
			}

		}else{

			if(!Util.isVazioOuBranco(quantidadeReparcelamento) && !Util.isVazioOuBranco(primeiraVez) && primeiraVez.equalsIgnoreCase("S")){

				atualizaColecoesNaSessao(sessao, httpServletRequest);

				if(!Util.isVazioOuBranco(adicionarReparcelamento) && adicionarReparcelamento.equals("S")){
					adicionarParcelamentoQuantidadeReparcelamentoHelper(quantidadeReparcelamento, sessao, httpServletRequest,
									adicionarReparcelamento);
				}

				atualizaFormNaSessao(sessao, httpServletRequest);

				// qd entra na tela pela primeira vez
				if(Util.isVazioOuBranco(httpServletRequest.getParameter("exibirAdicionarPercentualFaixaValorPopupAction"))
								|| !httpServletRequest.getParameter("exibirAdicionarPercentualFaixaValorPopupAction").equalsIgnoreCase("S")){

					limparTela(sessao, inserirPrestacoesParcelamentoPerfilActionForm, actionMapping, httpServletRequest);
				}

				// TODO Isilva - Depois ver isso
				// sessao.setAttribute("qtdeMaxReparcelamento", quantidadeReparcelamento);

				if(quantidadeReparcelamento != null && quantidadeReparcelamento.equals("0")){
					// só liberar Percentual Valor Reparcelado para informação
					// caso a qtde de reparcelamentos consecutivos seja maior que zero(0)
					percentualValorReparceladoReadOnly = true;
					sessao.setAttribute("quantidadeReparcelamento", quantidadeReparcelamento);
				}
				sessao.setAttribute("percentualValorReparceladoReadOnly", percentualValorReparceladoReadOnly);
				// httpServletRequest.setAttribute("percentualValorReparceladoReadOnly","" +
				// percentualValorReparceladoReadOnly);
				sessao.setAttribute("desabilitarTaxaJuros", desabilitarTaxaJuros);

				if(desabilitarTaxaJuros != null && desabilitarTaxaJuros.equals("true")){

					inserirPrestacoesParcelamentoPerfilActionForm.setTaxaJuros("0,00");
				}
			}

			Collection collectionParcelamentoQuantidadeReparcelamentoHelper = (Collection) sessao
							.getAttribute("collectionParcelamentoQuantidadeReparcelamentoHelper");

			// TODO Isilva - Depois ver isso
			if(collectionParcelamentoQuantidadeReparcelamentoHelper != null
							&& !collectionParcelamentoQuantidadeReparcelamentoHelper.isEmpty()){
				Iterator iterator = collectionParcelamentoQuantidadeReparcelamentoHelper.iterator();

				while(iterator.hasNext()){
					ParcelamentoQuantidadeReparcelamentoHelper parcelamentoQuantidadeReparcelamentoHelper = (ParcelamentoQuantidadeReparcelamentoHelper) iterator
									.next();

					// TODO Isilva - Depois ver isso
					// if
					// (parcelamentoQuantidadeReparcelamentoHelper.getQuantidadeMaximaReparcelamento().toString()
					// .equals((String)sessao.getAttribute("qtdeMaxReparcelamento"))) {
					if(parcelamentoQuantidadeReparcelamentoHelper.getQuantidadeMaximaReparcelamento().toString().equals(
									quantidadeReparcelamento)){

						Collection collectionParcelamentoQuantidadePrestacaoHelper = null;

						if(sessao.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper") != null){
							// volta do RemoverParcelamentoQuantidadePrestacaoAction
							collectionParcelamentoQuantidadePrestacaoHelper = (Collection) sessao
											.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper");
						}else{
							// chamado a partir da tela do inserir ou atualizar Perfil de
							// Parcelamento
							collectionParcelamentoQuantidadePrestacaoHelper = parcelamentoQuantidadeReparcelamentoHelper
											.getCollectionParcelamentoQuantidadePrestacaoHelper();
						}

						// parcelamentoQuantidadeReparcelamentoHelper.getCollectionParcelamentoQuantidadePrestacao();

						if(collectionParcelamentoQuantidadePrestacaoHelper == null
										|| collectionParcelamentoQuantidadePrestacaoHelper.isEmpty()){
							parcelamentoQuantidadeReparcelamentoHelper.setInformacaoParcelamentoQtdeReparcelamento("NÃO INFORMADAS");
							parcelamentoQuantidadeReparcelamentoHelper.setUltimaAlteracao(new Date());
						}

						httpServletRequest.setAttribute("collectionParcelamentoQuantidadePrestacaoHelper",
										collectionParcelamentoQuantidadePrestacaoHelper);
						sessao.setAttribute("collectionParcelamentoQuantidadePrestacaoHelper",
										collectionParcelamentoQuantidadePrestacaoHelper);
					}
				}
			}

		}

		if(httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer").equalsIgnoreCase("S")){
			// limpar tela

			if(Util.isVazioOuBranco(httpServletRequest.getParameter("exibirAdicionarPercentualFaixaValorPopupAction"))
							|| !httpServletRequest.getParameter("exibirAdicionarPercentualFaixaValorPopupAction").equalsIgnoreCase("S")){

				limparTela(sessao, inserirPrestacoesParcelamentoPerfilActionForm, actionMapping, httpServletRequest);
			}
		}

		if(httpServletRequest.getParameter("fechar") != null && httpServletRequest.getParameter("fechar").equalsIgnoreCase("S")){
			// antes de fechar a tela
			// atualiza a colecão collectionParcelamentoQuantidadePrestacaoHelper na sessao
			atualizaColecaoParcelamentoQuantidadePrestacaoNaSessao(sessao, httpServletRequest);

			// String qtdReparcelamento = (String)
			// httpServletRequest.getParameter("qtdeMaximaReparcelamento");
			//
			// if (qtdReparcelamento == null) {
			// qtdReparcelamento = (String) sessao.getAttribute("quantidadeReparcelamento");
			// }

			Collection<ParcelamentoQuantidadeReparcelamentoHelper> collectionParcelamentoQuantidadeReparcelamentoHelper = null;

			if(sessao.getAttribute("collectionParcelamentoQuantidadeReparcelamentoHelper") != null){
				collectionParcelamentoQuantidadeReparcelamentoHelper = (Collection) sessao
								.getAttribute("collectionParcelamentoQuantidadeReparcelamentoHelper");
			}else{
				collectionParcelamentoQuantidadeReparcelamentoHelper = new ArrayList();
			}

			Short qtdeReparcelamentoShort = new Short(quantidadeReparcelamento);
			/*
			 * BigDecimal valorMinimoPrestacaoBigDecimal = null;
			 * if (valorMinimoPrestacao== null
			 * || valorMinimoPrestacao.equalsIgnoreCase("")){
			 * throw new ActionServletException(
			 * // Informe Valor Mínimo da Prestação
			 * "atencao.required", null, "  Valor Mínimo da Prestação");
			 * }else{
			 * valorMinimoPrestacaoBigDecimal =
			 * Util.formatarMoedaRealparaBigDecimal(valorMinimoPrestacao);
			 * }
			 */

			ParcelamentoQuantidadeReparcelamentoHelper parcelamentoQtdeReparcelamentoHelperNovo = new ParcelamentoQuantidadeReparcelamentoHelper();

			// if (collectionParcelamentoQuantidadeReparcelamentoHelper != null &&
			// !collectionParcelamentoQuantidadeReparcelamentoHelper.isEmpty()){
			// se coleção não estiver vazia
			// verificar se a qtd máxima de prestações do parcelamento ja foi informada
			ParcelamentoQuantidadeReparcelamentoHelper parcelamentoQtdeReparcelamentoHelperAntigo = null;
			Iterator iterator = collectionParcelamentoQuantidadeReparcelamentoHelper.iterator();

			boolean inserirNovoDado = true;

			while(iterator.hasNext()){
				parcelamentoQtdeReparcelamentoHelperAntigo = (ParcelamentoQuantidadeReparcelamentoHelper) iterator.next();

				// [FS0003] Verificar quantidade máxima de reparcelamentos consecutivos
				if(qtdeReparcelamentoShort.equals(parcelamentoQtdeReparcelamentoHelperAntigo.getQuantidadeMaximaReparcelamento())){
					// Quantidade Máxima de Reparcelamentos Consecutivos já informada
					// throw new ActionServletException(
					// "atencao.qtde_maxima_reparcelamento_ja_informado");
					inserirNovoDado = false;
				}
			}

			if(inserirNovoDado){
				parcelamentoQtdeReparcelamentoHelperNovo.setQuantidadeMaximaReparcelamento(qtdeReparcelamentoShort);
				parcelamentoQtdeReparcelamentoHelperNovo.setInformacaoParcelamentoQtdeReparcelamento("NÃO INFORMADA");
				parcelamentoQtdeReparcelamentoHelperNovo.setUltimaAlteracao(new Date());
				collectionParcelamentoQuantidadeReparcelamentoHelper.add(parcelamentoQtdeReparcelamentoHelperNovo);
			}

			// }

			// Ordena a coleção pela qunatidade de reparcelamento
			Collections.sort((List) collectionParcelamentoQuantidadeReparcelamentoHelper, new Comparator() {

				public int compare(Object a, Object b){

					Integer valorMinPrestacao1 = new Integer(((ParcelamentoQuantidadeReparcelamentoHelper) a)
									.getQuantidadeMaximaReparcelamento().toString());
					Integer valorMinPrestacao2 = new Integer(((ParcelamentoQuantidadeReparcelamentoHelper) b)
									.getQuantidadeMaximaReparcelamento().toString());

					return valorMinPrestacao1.compareTo(valorMinPrestacao2);

				}
			});

			// manda para a sessão a coleção de ParcelamentoQuantidadeReparcelamentoHelper
			sessao.setAttribute("collectionParcelamentoQuantidadeReparcelamentoHelper",
							collectionParcelamentoQuantidadeReparcelamentoHelper);

			if(sessao.getAttribute("UseCase") != null && sessao.getAttribute("UseCase").equals("INSERIRPERFIL")){
				httpServletRequest.setAttribute("reloadPage", "FECHARINSERIR");
			}else{
				httpServletRequest.setAttribute("reloadPage", "FECHARATUALIZAR");
			}
		}

		if(httpServletRequest.getAttribute("reloadPage") != null && httpServletRequest.getAttribute("reloadPage").equals("INSERIRPERFIL")){
			sessao.setAttribute(
							"InsPerfilParcHelper",
							new ParcelamentoPerfilHelper((ParcelamentoPerfilActionForm) sessao.getAttribute("ParcelamentoPerfilActionForm")));
		}
		return retorno;
	}

	private void limparTela(HttpSession sessao,
					InserirPrestacoesParcelamentoPerfilActionForm inserirPrestacoesParcelamentoPerfilActionForm,
					ActionMapping actionMapping, HttpServletRequest httpServletRequest){

		inserirPrestacoesParcelamentoPerfilActionForm.reset(actionMapping, httpServletRequest);

		sessao.removeAttribute("collectionParcelamentoQuantidadePrestacaoHelper");
		sessao.removeAttribute("collectionParcelamentoFaixaValor");
	}

	private void limparQuadroEntradaParcelamento(InserirPrestacoesParcelamentoPerfilActionForm inserirPrestacoesParcelamentoPerfilActionForm){

		// Limpa o formulário que adiciona Entrada de Parcelamento
		inserirPrestacoesParcelamentoPerfilActionForm.setValorMinimoEntrada("");
		inserirPrestacoesParcelamentoPerfilActionForm.setPercentualTarifaMinimaImovel("");
		inserirPrestacoesParcelamentoPerfilActionForm.setPercentualMinimoEntrada("");
		inserirPrestacoesParcelamentoPerfilActionForm.setIndicadorOpcoesEntradaParcelamento(null);
	}

	private void limparQuadroPercentualPorFaixaValor(
					InserirPrestacoesParcelamentoPerfilActionForm inserirPrestacoesParcelamentoPerfilActionForm){

		// Limpa o formulário que adiciona Percentual Por Faixa de Valor
		inserirPrestacoesParcelamentoPerfilActionForm.setValorMaxPercFaixaValor("");
		inserirPrestacoesParcelamentoPerfilActionForm.setPercentualPercFaixaValor("");
		inserirPrestacoesParcelamentoPerfilActionForm.setValorFixoEntrada("");
		inserirPrestacoesParcelamentoPerfilActionForm.setIndicadorOpcoesPercentualFaixaValor("1");
	}

	/**
	 * @author isilva
	 * @param inserirPrestacoesParcelamentoPerfilActionForm
	 */
	private void validarDados(InserirPrestacoesParcelamentoPerfilActionForm inserirPrestacoesParcelamentoPerfilActionForm,
					HttpSession sessao){

		if(Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getTaxaJuros())){
			// Informe Taxa de Juros a.m.
			throw new ActionServletException("atencao.required", null, "  Taxa de Juros a.m.");
		}

		// Dias Vencimento da Entrada
		String numeroDiasVencimentoDaEntrada = inserirPrestacoesParcelamentoPerfilActionForm.getNumeroDiasVencimentoDaEntrada();

		if(Util.isVazioOuBranco(numeroDiasVencimentoDaEntrada)){
			throw new ActionServletException("atencao.required", null, "  Dias Vencimento da Entrada");
		}

		if(sessao.getAttribute("collectionParcelamentoFaixaValor") == null){
			int qtdInformada = 0;

			if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getValorMinimoEntrada())){
				qtdInformada++;
			}

			if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getPercentualTarifaMinimaImovel())){
				qtdInformada++;
			}

			if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getPercentualMinimoEntrada())){
				qtdInformada++;
			}

			if(qtdInformada != 1){
				throw new ActionServletException("atencao.informe.tarifa.minima.valor.ou.percentual.valor.debito.ou.valor.minimo.prestacao");
			}

			if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getPercentualMinimoEntrada())
							&& Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getIndicadorOpcoesEntradaParcelamento())){
				throw new ActionServletException("atencao.required", null, "Informe Opções da Prestação Mínima");
			}
		}
	}

	private void adicionarParcelamentoQuantidadePrestacao(HttpSession sessao, Fachada fachada,
					InserirPrestacoesParcelamentoPerfilActionForm inserirPrestacoesParcelamentoPerfilActionForm,
					Boolean percentualValorReparceladoReadOnly){

		if(Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getQuantidadeMaximaPrestacao())){
			// Informe Quantidade Máxima de Prestações
			throw new ActionServletException("atencao.required", null, " Quantidade Máxima de Prestações");
		}else if(Util.validarValorNaoNumericoComoBigDecimal(inserirPrestacoesParcelamentoPerfilActionForm.getQuantidadeMaximaPrestacao())){
			// Quantidade Máxima de Prestações deve ser numerico
			throw new ActionServletException("atencao.integer", null, " Quantidade Máxima de Prestações");
		}

		Collection<ParcelamentoQuantidadePrestacaoHelper> collectionParcelamentoQuantidadePrestacaoHelper = null;

		if(sessao.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper") != null){
			collectionParcelamentoQuantidadePrestacaoHelper = (Collection) sessao
							.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper");
		}else{
			collectionParcelamentoQuantidadePrestacaoHelper = new ArrayList();
		}

		Short qtdeMaximaPrestacao = new Short(inserirPrestacoesParcelamentoPerfilActionForm.getQuantidadeMaximaPrestacao());

		if(collectionParcelamentoQuantidadePrestacaoHelper != null && !collectionParcelamentoQuantidadePrestacaoHelper.isEmpty()){
			// se coleção não estiver vazia
			// verificar se a qtd máxima de prestações do parcelamento ja foi informada
			ParcelamentoQuantidadePrestacaoHelper parcelamentoQuantidadePrestacaoAntigoHelper = null;
			Iterator iterator = collectionParcelamentoQuantidadePrestacaoHelper.iterator();

			while(iterator.hasNext()){
				parcelamentoQuantidadePrestacaoAntigoHelper = (ParcelamentoQuantidadePrestacaoHelper) iterator.next();

				if(qtdeMaximaPrestacao.intValue() == parcelamentoQuantidadePrestacaoAntigoHelper.getParcelamentoQuantidadePrestacao()
								.getQuantidadeMaximaPrestacao().intValue()){
					// Quantidade Máxima de Prestações já informada
					throw new ActionServletException("atencao.qtde_maxima_prestacoes_ja_informado");
				}
			}
		}

		// Valida os dados
		validarDados(inserirPrestacoesParcelamentoPerfilActionForm, sessao);

		Short qtdeMaxPrestacaoPermissaoEspecial = null;
		if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getQuantidadeMaxPrestacaoEspecial())){
			qtdeMaxPrestacaoPermissaoEspecial = new Short(inserirPrestacoesParcelamentoPerfilActionForm.getQuantidadeMaxPrestacaoEspecial());
		}

		BigDecimal taxaJuros = null;
		if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getTaxaJuros())){
			taxaJuros = Util.formatarMoedaRealparaBigDecimal(inserirPrestacoesParcelamentoPerfilActionForm.getTaxaJuros());
		}else if(sessao.getAttribute("desabilitarTaxaJuros") != null
						&& sessao.getAttribute("desabilitarTaxaJuros").toString().equals("true")){

			taxaJuros = Util.formatarMoedaRealparaBigDecimal("0,00");
		}

		BigDecimal valorMinimoEntrada = null;
		if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getValorMinimoEntrada())){
			valorMinimoEntrada = Util.formatarMoedaRealparaBigDecimal(inserirPrestacoesParcelamentoPerfilActionForm.getValorMinimoEntrada()
							.toString(), 2);
		}

		BigDecimal percentualMinimoEntrada = null;
		if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getPercentualMinimoEntrada())){
			percentualMinimoEntrada = Util.formatarMoedaRealparaBigDecimal(inserirPrestacoesParcelamentoPerfilActionForm
							.getPercentualMinimoEntrada().toString(), 2);
		}

		BigDecimal percentualTarifaMinimaImovel = null;
		if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getPercentualTarifaMinimaImovel())){
			percentualTarifaMinimaImovel = Util.formatarMoedaRealparaBigDecimal(inserirPrestacoesParcelamentoPerfilActionForm
							.getPercentualTarifaMinimaImovel().toString(), 2);
		}

		// if ((inserirPrestacoesParcelamentoPerfilActionForm.getPercentualMinimoEntrada()== null
		// ||
		// inserirPrestacoesParcelamentoPerfilActionForm.getPercentualMinimoEntrada().equalsIgnoreCase("")
		// || inserirPrestacoesParcelamentoPerfilActionForm.getPercentualMinimoEntrada().equals(0))
		// &&(inserirPrestacoesParcelamentoPerfilActionForm.getPercentualTarifaMinimaImovel()== null
		// ||
		// inserirPrestacoesParcelamentoPerfilActionForm.getPercentualTarifaMinimaImovel().equalsIgnoreCase("")
		// ||
		// inserirPrestacoesParcelamentoPerfilActionForm.getPercentualTarifaMinimaImovel().equals(0))){
		//	    	
		// percentualMinimoEntrada = new BigDecimal(0);
		// percentualTarifaMinimaImovel = new BigDecimal(0);
		//
		// }else{
		//	    	
		// if (inserirPrestacoesParcelamentoPerfilActionForm.getPercentualMinimoEntrada()!= null
		// &&
		// !inserirPrestacoesParcelamentoPerfilActionForm.getPercentualMinimoEntrada().equalsIgnoreCase("")){
		//	    		 
		// percentualMinimoEntrada = Util.formatarMoedaRealparaBigDecimal
		// (inserirPrestacoesParcelamentoPerfilActionForm.getPercentualMinimoEntrada());
		//	    		 
		// }else if
		// (inserirPrestacoesParcelamentoPerfilActionForm.getPercentualTarifaMinimaImovel()!= null
		// &&
		// !inserirPrestacoesParcelamentoPerfilActionForm.getPercentualTarifaMinimaImovel().equalsIgnoreCase("")){
		//	    		 
		// percentualTarifaMinimaImovel = Util.formatarMoedaRealparaBigDecimal
		// (inserirPrestacoesParcelamentoPerfilActionForm.getPercentualTarifaMinimaImovel());
		// }
		//			
		// }

		Short indicadorOpcoesEntradaParcelamento = null;
		if(!Util.isVazioOuBranco(percentualMinimoEntrada)
						&& !Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getIndicadorOpcoesEntradaParcelamento())){
			indicadorOpcoesEntradaParcelamento = Short.valueOf(inserirPrestacoesParcelamentoPerfilActionForm
							.getIndicadorOpcoesEntradaParcelamento());
		}

		BigDecimal percentualValorReparcelado = null;
		if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getPercentualValorReparcelado())){
			percentualValorReparcelado = Util.formatarMoedaRealparaBigDecimal(inserirPrestacoesParcelamentoPerfilActionForm
							.getPercentualValorReparcelado());
		}

		// Meses entre Parcelas
		Integer numeroMesesEntreParcelas = null;
		String numeroMesesEntreParcelasStr = inserirPrestacoesParcelamentoPerfilActionForm.getNumeroMesesEntreParcelas();

		if(!Util.isVazioOuBranco(numeroMesesEntreParcelasStr)){
			numeroMesesEntreParcelas = Util.converterStringParaInteger(numeroMesesEntreParcelasStr);
		}

		// Parcelas a Lançar
		Integer numeroParcelasALancar = null;
		String numeroParcelasALancarStr = inserirPrestacoesParcelamentoPerfilActionForm.getNumeroParcelasALancar();

		if(!Util.isVazioOuBranco(numeroParcelasALancarStr)){
			numeroParcelasALancar = Util.converterStringParaInteger(numeroParcelasALancarStr);
		}

		// Meses para Início da Cobrança
		Integer numeroMesesInicioCobranca = null;
		String numeroMesesInicioCobrancaStr = inserirPrestacoesParcelamentoPerfilActionForm.getNumeroMesesInicioCobranca();

		if(!Util.isVazioOuBranco(numeroMesesInicioCobrancaStr)){
			numeroMesesInicioCobranca = Util.converterStringParaInteger(numeroMesesInicioCobrancaStr);
		}

		// Dias Vencimento da Entrada
		Integer numeroDiasVencimentoDaEntrada = null;
		String numeroDiasVencimentoDaEntradaStr = inserirPrestacoesParcelamentoPerfilActionForm.getNumeroDiasVencimentoDaEntrada();

		if(!Util.isVazioOuBranco(numeroDiasVencimentoDaEntradaStr)){
			numeroDiasVencimentoDaEntrada = Util.converterStringParaInteger(numeroDiasVencimentoDaEntradaStr);
		}

		// Dias Vencimento da Entrada
		/**
		 * percentualDescontoJurosMora;
		 * percentualDescontoMulta;
		 * percentualDescontoCorrecaoMonetaria;
		 */
		BigDecimal percentualDescontoJurosMora = null;
		String percentualDescontoJurosMoraStr = inserirPrestacoesParcelamentoPerfilActionForm.getPercentualDescontoJurosMora();

		if(!Util.isVazioOuBranco(percentualDescontoJurosMoraStr)){
			percentualDescontoJurosMora = Util.formatarMoedaRealparaBigDecimal(percentualDescontoJurosMoraStr);
		}

		BigDecimal percentualDescontoMulta = null;
		String percentualDescontoMultaStr = inserirPrestacoesParcelamentoPerfilActionForm.getPercentualDescontoMulta();

		if(!Util.isVazioOuBranco(percentualDescontoMultaStr)){
			percentualDescontoMulta = Util.formatarMoedaRealparaBigDecimal(percentualDescontoMultaStr);
		}

		BigDecimal percentualDescontoCorrecaoMonetaria = null;
		String percentualCorrecaoMonetariaStr = inserirPrestacoesParcelamentoPerfilActionForm.getPercentualDescontoCorrecaoMonetaria();

		if(!Util.isVazioOuBranco(percentualCorrecaoMonetariaStr)){
			percentualDescontoCorrecaoMonetaria = Util.formatarMoedaRealparaBigDecimal(percentualCorrecaoMonetariaStr);
		}

		BigDecimal percentualEntradaSugerida = null;
		if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getPercentualEntradaSugerida())){
			percentualEntradaSugerida = Util.formatarMoedaRealparaBigDecimal(inserirPrestacoesParcelamentoPerfilActionForm
							.getPercentualEntradaSugerida().toString(), 2);
		}

		// vivi !!!!!!! aceita zero????
		// insere como null ou zero??
		// if (inserirPrestacoesParcelamentoPerfilActionForm.getPercentualValorReparcelado() != null
		// &&
		// !inserirPrestacoesParcelamentoPerfilActionForm.getPercentualValorReparcelado().equals("")
		// &&
		// !inserirPrestacoesParcelamentoPerfilActionForm.getPercentualValorReparcelado().equals(0)){
		// percentualValorReparcelado =Util.formatarMoedaRealparaBigDecimal(
		// inserirPrestacoesParcelamentoPerfilActionForm.getPercentualValorReparcelado());
		// }

		ParcelamentoQuantidadePrestacao parcelamentoQuantidadePrestacaoNovo = new ParcelamentoQuantidadePrestacao();

		parcelamentoQuantidadePrestacaoNovo.setValorMinimoEntrada(valorMinimoEntrada);
		parcelamentoQuantidadePrestacaoNovo.setIndicadorEntradaParcelamento(indicadorOpcoesEntradaParcelamento);
		parcelamentoQuantidadePrestacaoNovo.setQuantidadeMaximaPrestacao(qtdeMaximaPrestacao);
		parcelamentoQuantidadePrestacaoNovo.setQuantidadeMaxPrestacaoEspecial(qtdeMaxPrestacaoPermissaoEspecial);
		parcelamentoQuantidadePrestacaoNovo.setTaxaJuros(taxaJuros);
		parcelamentoQuantidadePrestacaoNovo.setPercentualMinimoEntrada(percentualMinimoEntrada);
		parcelamentoQuantidadePrestacaoNovo.setPercentualTarifaMinimaImovel(percentualTarifaMinimaImovel);
		parcelamentoQuantidadePrestacaoNovo.setPercentualValorReparcelado(percentualValorReparcelado);
		parcelamentoQuantidadePrestacaoNovo.setUltimaAlteracao(new Date());
		parcelamentoQuantidadePrestacaoNovo.setNumeroMesesEntreParcelas(numeroMesesEntreParcelas);
		parcelamentoQuantidadePrestacaoNovo.setNumeroParcelasALancar(numeroParcelasALancar);
		parcelamentoQuantidadePrestacaoNovo.setNumeroMesesInicioCobranca(numeroMesesInicioCobranca);
		parcelamentoQuantidadePrestacaoNovo.setNumeroDiasVencimentoDaEntrada(numeroDiasVencimentoDaEntrada);
		parcelamentoQuantidadePrestacaoNovo.setPercentualDescontoJurosMora(percentualDescontoJurosMora);
		parcelamentoQuantidadePrestacaoNovo.setPercentualDescontoMulta(percentualDescontoMulta);
		parcelamentoQuantidadePrestacaoNovo.setPercentualDescontoCorrecaoMonetaria(percentualDescontoCorrecaoMonetaria);
		parcelamentoQuantidadePrestacaoNovo.setPercentualEntradaSugerida(percentualEntradaSugerida);

		ParcelamentoQuantidadePrestacaoHelper parcelamentoQuantidadePrestacaoHelperNovo = new ParcelamentoQuantidadePrestacaoHelper();

		parcelamentoQuantidadePrestacaoHelperNovo.setParcelamentoQuantidadePrestacao(parcelamentoQuantidadePrestacaoNovo);

		if(sessao.getAttribute("collectionParcelamentoFaixaValor") != null){
			Collection collectionParcelamentoFaixaValor = (Collection) sessao.getAttribute("collectionParcelamentoFaixaValor");
			parcelamentoQuantidadePrestacaoHelperNovo.setCollectionParcelamentoFaixaValor(collectionParcelamentoFaixaValor);
			sessao.removeAttribute("collectionParcelamentoFaixaValor");
		}

		collectionParcelamentoQuantidadePrestacaoHelper.add(parcelamentoQuantidadePrestacaoHelperNovo);

		inserirPrestacoesParcelamentoPerfilActionForm.setValorMinimoEntrada("");
		inserirPrestacoesParcelamentoPerfilActionForm.setQuantidadeMaximaPrestacao("");
		inserirPrestacoesParcelamentoPerfilActionForm.setQuantidadeMaxPrestacaoEspecial("");
		inserirPrestacoesParcelamentoPerfilActionForm.setTaxaJuros("");
		inserirPrestacoesParcelamentoPerfilActionForm.setPercentualMinimoEntrada("");
		inserirPrestacoesParcelamentoPerfilActionForm.setPercentualTarifaMinimaImovel("");
		inserirPrestacoesParcelamentoPerfilActionForm.setPercentualValorReparcelado("");
		inserirPrestacoesParcelamentoPerfilActionForm.setNumeroMesesEntreParcelas("");
		inserirPrestacoesParcelamentoPerfilActionForm.setNumeroParcelasALancar("");
		inserirPrestacoesParcelamentoPerfilActionForm.setNumeroMesesInicioCobranca("");
		inserirPrestacoesParcelamentoPerfilActionForm.setNumeroDiasVencimentoDaEntrada("");
		inserirPrestacoesParcelamentoPerfilActionForm.setPercentualDescontoJurosMora("");
		inserirPrestacoesParcelamentoPerfilActionForm.setPercentualDescontoMulta("");
		inserirPrestacoesParcelamentoPerfilActionForm.setPercentualDescontoCorrecaoMonetaria("");

		// Ordena a coleção pela Qtde. Máxima Meses de Inatividade da Lig. de Água
		Collections.sort((List) collectionParcelamentoQuantidadePrestacaoHelper, new Comparator() {

			public int compare(Object a, Object b){

				Integer valorMinPrestacao1 = new Integer(((ParcelamentoQuantidadePrestacaoHelper) a).getParcelamentoQuantidadePrestacao()
								.getQuantidadeMaximaPrestacao().toString());
				Integer valorMinPrestacao2 = new Integer(((ParcelamentoQuantidadePrestacaoHelper) b).getParcelamentoQuantidadePrestacao()
								.getQuantidadeMaximaPrestacao().toString());

				return valorMinPrestacao1.compareTo(valorMinPrestacao2);

			}
		});

		// manda para a sessão a coleção de ParcelamentoQuantidadePrestacao
		sessao.setAttribute("collectionParcelamentoQuantidadePrestacaoHelper", collectionParcelamentoQuantidadePrestacaoHelper);
		// sessao.setAttribute("inserirPrestacoesParcelamentoPerfilActionForm",
		// inserirPrestacoesParcelamentoPerfilActionForm);

	}

	private void atualizaColecaoParcelamentoQuantidadePrestacaoNaSessao(HttpSession sessao, HttpServletRequest httpServletRequest){

		// collectionParcelamentoQuantidadePrestacaoHelper
		if(sessao.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper") != null
						&& !sessao.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper").equals("")){

			Collection collectionParcelamentoQuantidadePrestacaoHelper = (Collection) sessao
							.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper");
			// cria as variáveis para recuperar os parâmetros do request e jogar
			// no objeto ParcelamentoQuantidadePrestacao
			Iterator iterator = collectionParcelamentoQuantidadePrestacaoHelper.iterator();

			while(iterator.hasNext()){
				ParcelamentoQuantidadePrestacaoHelper parcelamentoQuantidadePrestacaoHelper = (ParcelamentoQuantidadePrestacaoHelper) iterator
								.next();

				ParcelamentoQuantidadePrestacao parcelamentoQuantidadePrestacao = parcelamentoQuantidadePrestacaoHelper
								.getParcelamentoQuantidadePrestacao();

				long valorTempo = parcelamentoQuantidadePrestacao.getUltimaAlteracao().getTime();

				String indicadorEntradaParcelamento = (String) httpServletRequest.getParameter("indicadorEntradaParcelamento" + valorTempo);
				String vlMinimoEntrada = (String) httpServletRequest.getParameter("vlMinimoEntrada" + valorTempo);
				String txJuros = (String) httpServletRequest.getParameter("txJuros" + valorTempo);
				String percMinEntrada = (String) httpServletRequest.getParameter("percMinEntrada" + valorTempo);
				String percTarMinImovel = (String) httpServletRequest.getParameter("percTarMinImovel" + valorTempo);
				String percVlReparcelado = (String) httpServletRequest.getParameter("percVlReparcelado" + valorTempo);
				String numeroMesesEntreParcelasStr = (String) httpServletRequest.getParameter("numeroMesesEntreParcelas" + valorTempo);
				String numeroParcelasALancarStr = (String) httpServletRequest.getParameter("numeroParcelasALancar" + valorTempo);
				String numeroMesesInicioCobrancaStr = (String) httpServletRequest.getParameter("numeroMesesInicioCobranca" + valorTempo);
				String numeroDiasVencimentoDaEntradaStr = (String) httpServletRequest.getParameter("numeroDiasVencimentoDaEntrada"
								+ valorTempo);

				// insere essas variáveis no objeto ParcelamentoQuantidadePrestacao
				Short indicadorEntradaParcelamentoShort = null;
				if(!Util.isVazioOuBranco(indicadorEntradaParcelamento)){
					indicadorEntradaParcelamentoShort = Short.valueOf(indicadorEntradaParcelamento);
				}

				BigDecimal vlMinimoEntradaBigDecimal = null;
				if(!Util.isVazioOuBranco(vlMinimoEntrada)){
					vlMinimoEntradaBigDecimal = Util.formatarMoedaRealparaBigDecimal(vlMinimoEntrada);
				}

				BigDecimal taxaJuros = null;
				if(!Util.isVazioOuBranco(txJuros)){
					taxaJuros = Util.formatarMoedaRealparaBigDecimal(txJuros);
				}else if(sessao.getAttribute("desabilitarTaxaJuros") != null
								&& sessao.getAttribute("desabilitarTaxaJuros").toString().equals("true")){

					taxaJuros = Util.formatarMoedaRealparaBigDecimal("0,00");
				}

				BigDecimal percentualMinimoEntrada = null;
				if(!Util.isVazioOuBranco(percMinEntrada)){
					percentualMinimoEntrada = Util.formatarMoedaRealparaBigDecimal(percMinEntrada);
				}

				BigDecimal percentualTarifaMinimaImovel = null;
				if(!Util.isVazioOuBranco(percTarMinImovel)){
					percentualTarifaMinimaImovel = Util.formatarMoedaRealparaBigDecimal(percTarMinImovel);
				}

				BigDecimal percentualValorReparcelado = null;
				if(!Util.isVazioOuBranco(percVlReparcelado)){
					percentualValorReparcelado = Util.formatarMoedaRealparaBigDecimal(percVlReparcelado);
				}

				// Meses entre Parcelas
				Integer numeroMesesEntreParcelas = null;
				if(!Util.isVazioOuBranco(numeroMesesEntreParcelasStr)){
					numeroMesesEntreParcelas = Util.converterStringParaInteger(numeroMesesEntreParcelasStr);
				}

				// Parcelas a Lançar
				Integer numeroParcelasALancar = null;
				if(!Util.isVazioOuBranco(numeroParcelasALancarStr)){
					numeroParcelasALancar = Util.converterStringParaInteger(numeroParcelasALancarStr);
				}

				// Meses para Início da Cobrança
				Integer numeroMesesInicioCobranca = null;
				if(!Util.isVazioOuBranco(numeroMesesInicioCobrancaStr)){
					numeroMesesInicioCobranca = Util.converterStringParaInteger(numeroMesesInicioCobrancaStr);
				}

				// Dias Vencimento da Entrada
				Integer numeroDiasVencimentoDaEntrada = null;
				if(!Util.isVazioOuBranco(numeroDiasVencimentoDaEntradaStr)){
					numeroDiasVencimentoDaEntrada = Util.converterStringParaInteger(numeroDiasVencimentoDaEntradaStr);
				}

				parcelamentoQuantidadePrestacao.setIndicadorEntradaParcelamento(indicadorEntradaParcelamentoShort);
				parcelamentoQuantidadePrestacao.setValorMinimoEntrada(vlMinimoEntradaBigDecimal);
				parcelamentoQuantidadePrestacao.setTaxaJuros(taxaJuros);
				parcelamentoQuantidadePrestacao.setPercentualMinimoEntrada(percentualMinimoEntrada);
				parcelamentoQuantidadePrestacao.setPercentualTarifaMinimaImovel(percentualTarifaMinimaImovel);
				parcelamentoQuantidadePrestacao.setPercentualValorReparcelado(percentualValorReparcelado);
				parcelamentoQuantidadePrestacao.setNumeroMesesEntreParcelas(numeroMesesEntreParcelas);
				parcelamentoQuantidadePrestacao.setNumeroParcelasALancar(numeroParcelasALancar);
				parcelamentoQuantidadePrestacao.setNumeroMesesInicioCobranca(numeroMesesInicioCobranca);
				parcelamentoQuantidadePrestacao.setNumeroDiasVencimentoDaEntrada(numeroDiasVencimentoDaEntrada);

				parcelamentoQuantidadePrestacaoHelper.setParcelamentoQuantidadePrestacao(parcelamentoQuantidadePrestacao);
			}
		}
	}

	private void atualizaColecoesNaSessao(HttpSession sessao, HttpServletRequest httpServletRequest){

		// collectionParcelamentoDescontoAntiguidade
		if(sessao.getAttribute("collectionParcelamentoDescontoAntiguidade") != null
						&& !sessao.getAttribute("collectionParcelamentoDescontoAntiguidade").equals("")){

			Collection collectionParcelamentoDescontoAntiguidade = (Collection) sessao
							.getAttribute("collectionParcelamentoDescontoAntiguidade");
			// cria as variáveis para recuperar os parâmetros do request e jogar
			// no objeto ParcelamentoDescontoAntiguidade
			String vlSemRestAntiguidade = null;
			String vlComRestAntiguidade = null;
			String vlDescontoAtivo = null;

			Iterator iteratorParcelamentoDescontoAntiguidade = collectionParcelamentoDescontoAntiguidade.iterator();

			while(iteratorParcelamentoDescontoAntiguidade.hasNext()){
				ParcelamentoDescontoAntiguidade parcelamentoDescontoAntiguidade = (ParcelamentoDescontoAntiguidade) iteratorParcelamentoDescontoAntiguidade
								.next();

				long valorTempo = parcelamentoDescontoAntiguidade.getUltimaAlteracao().getTime();

				vlSemRestAntiguidade = (String) httpServletRequest.getParameter("vlSemRestAntiguidade" + valorTempo);
				vlComRestAntiguidade = httpServletRequest.getParameter("vlComRestAntiguidade" + valorTempo);
				vlDescontoAtivo = httpServletRequest.getParameter("vlDescontoAtivo" + valorTempo);

				// inseri essas variáveis no objeto ParcelamentoDescontoAntiguidade
				BigDecimal percentualDescontoSemRestabelecimentoAntiguidade = null;
				if(!Util.isVazioOuBranco(vlSemRestAntiguidade)){
					percentualDescontoSemRestabelecimentoAntiguidade = Util.formatarMoedaRealparaBigDecimal(vlSemRestAntiguidade);
					parcelamentoDescontoAntiguidade
									.setPercentualDescontoSemRestabelecimento(percentualDescontoSemRestabelecimentoAntiguidade);
				}

				BigDecimal percentualDescontoComRestabelecimentoAntiguidade = null;
				if(!Util.isVazioOuBranco(vlComRestAntiguidade)){
					percentualDescontoComRestabelecimentoAntiguidade = Util.formatarMoedaRealparaBigDecimal(vlComRestAntiguidade);
					parcelamentoDescontoAntiguidade
									.setPercentualDescontoComRestabelecimento(percentualDescontoComRestabelecimentoAntiguidade);
				}

				BigDecimal percentualDescontoAtivoAntiguidade = null;
				if(!Util.isVazioOuBranco(vlDescontoAtivo)){
					percentualDescontoAtivoAntiguidade = Util.formatarMoedaRealparaBigDecimal(vlDescontoAtivo);
					parcelamentoDescontoAntiguidade.setPercentualDescontoAtivo(percentualDescontoAtivoAntiguidade);
				}

			}
		}

		// collectionParcelamentoDescontoInatividade
		if(sessao.getAttribute("collectionParcelamentoDescontoInatividade") != null
						&& !sessao.getAttribute("collectionParcelamentoDescontoInatividade").equals("")){

			Collection collectionParcelamentoDescontoInatividade = (Collection) sessao
							.getAttribute("collectionParcelamentoDescontoInatividade");
			// cria as variáveis para recuperar os parâmetros do request e jogar
			// no objeto ParcelamentoDescontoInatividade
			String vlSemRestInatividade = null;
			String vlComRestInatividade = null;

			Iterator iteratorParcelamentoDescontoInatividade = collectionParcelamentoDescontoInatividade.iterator();

			while(iteratorParcelamentoDescontoInatividade.hasNext()){
				ParcelamentoDescontoInatividade parcelamentoDescontoInatividade = (ParcelamentoDescontoInatividade) iteratorParcelamentoDescontoInatividade
								.next();

				long valorTempo = parcelamentoDescontoInatividade.getUltimaAlteracao().getTime();

				vlSemRestInatividade = (String) httpServletRequest.getParameter("vlSemRestInatividade" + valorTempo);
				vlComRestInatividade = httpServletRequest.getParameter("vlComRestInatividade" + valorTempo);

				// insere essas variáveis no objeto ParcelamentoDescontoInatividade
				BigDecimal percentualDescontoSemRestabelecimentoInatividade = null;
				if(!Util.isVazioOuBranco(vlSemRestInatividade)){
					percentualDescontoSemRestabelecimentoInatividade = Util.formatarMoedaRealparaBigDecimal(vlSemRestInatividade);
					parcelamentoDescontoInatividade
									.setPercentualDescontoSemRestabelecimento(percentualDescontoSemRestabelecimentoInatividade);
				}

				BigDecimal percentualDescontoComRestabelecimentoInatividade = null;
				if(!Util.isVazioOuBranco(vlComRestInatividade)){
					percentualDescontoComRestabelecimentoInatividade = Util.formatarMoedaRealparaBigDecimal(vlComRestInatividade);
					parcelamentoDescontoInatividade
									.setPercentualDescontoComRestabelecimento(percentualDescontoComRestabelecimentoInatividade);
				}
			}
		}

		// atualizaColecaoParcelamentoQuantidadePrestacaoNaSessao(sessao, httpServletRequest);

	}

	private void atualizaFormNaSessao(HttpSession sessao, HttpServletRequest httpServletRequest){

		if(sessao.getAttribute("UseCase").equals("INSERIRPERFIL")){
			ParcelamentoPerfilActionForm parcelamentoPerfilActionForm = (ParcelamentoPerfilActionForm) sessao
							.getAttribute("ParcelamentoPerfilActionForm");

			if(httpServletRequest.getParameter("resolucaoDiretoria") != null){
				parcelamentoPerfilActionForm.setResolucaoDiretoria(httpServletRequest.getParameter("resolucaoDiretoria"));
			}

			if(httpServletRequest.getParameter("imovelSituacaoTipo") != null){
				parcelamentoPerfilActionForm.setImovelSituacaoTipo(httpServletRequest.getParameter("imovelSituacaoTipo"));
			}

			if(httpServletRequest.getParameter("imovelPerfil") != null){
				parcelamentoPerfilActionForm.setImovelPerfil(httpServletRequest.getParameter("imovelPerfil"));
			}
			if(httpServletRequest.getParameter("subcategoria") != null){
				parcelamentoPerfilActionForm.setSubcategoria(httpServletRequest.getParameter("subcategoria"));
			}
			if(httpServletRequest.getParameter("percentualDescontoAcrescimo") != null){
				parcelamentoPerfilActionForm.setPercentualDescontoAcrescimo(httpServletRequest.getParameter("percentualDescontoAcrescimo"));
			}

			if(httpServletRequest.getParameter("percentualTarifaMinimaPrestacao") != null
							&& !httpServletRequest.getParameter("percentualTarifaMinimaPrestacao").toString().equalsIgnoreCase("")){
				parcelamentoPerfilActionForm.setPercentualTarifaMinimaPrestacao(httpServletRequest
								.getParameter("percentualTarifaMinimaPrestacao"));
				parcelamentoPerfilActionForm.setPercentualValorDebitoCalculoValorMinimoPrestacao(null);
				parcelamentoPerfilActionForm.setValorDebitoPrestacao(null);
			}else if(httpServletRequest.getParameter("percentualValorDebitoCalculoValorMinimoPrestacao") != null
							&& !httpServletRequest.getParameter("percentualValorDebitoCalculoValorMinimoPrestacao").toString()
											.equalsIgnoreCase("")){
				parcelamentoPerfilActionForm.setPercentualTarifaMinimaPrestacao(null);
				parcelamentoPerfilActionForm.setPercentualValorDebitoCalculoValorMinimoPrestacao(httpServletRequest
								.getParameter("percentualValorDebitoCalculoValorMinimoPrestacao"));
				parcelamentoPerfilActionForm.setValorDebitoPrestacao(null);
			}else if(httpServletRequest.getParameter("valorDebitoPrestacao") != null
							&& !httpServletRequest.getParameter("valorDebitoPrestacao").toString().equalsIgnoreCase("")){
				parcelamentoPerfilActionForm.setPercentualTarifaMinimaPrestacao(null);
				parcelamentoPerfilActionForm.setPercentualValorDebitoCalculoValorMinimoPrestacao(null);
				parcelamentoPerfilActionForm.setValorDebitoPrestacao(httpServletRequest.getParameter("valorDebitoPrestacao"));
			}

			if(httpServletRequest.getParameter("consumoMinimo") != null){
				parcelamentoPerfilActionForm.setConsumoMinimo(httpServletRequest.getParameter("consumoMinimo"));
			}
			if(httpServletRequest.getParameter("percentualVariacaoConsumoMedio") != null){
				parcelamentoPerfilActionForm.setPercentualVariacaoConsumoMedio(httpServletRequest
								.getParameter("percentualVariacaoConsumoMedio"));
			}
			if(httpServletRequest.getParameter("indicadorParcelarChequeDevolvido") != null){
				parcelamentoPerfilActionForm.setIndicadorParcelarChequeDevolvido(httpServletRequest
								.getParameter("indicadorParcelarChequeDevolvido"));
			}
			if(httpServletRequest.getParameter("indicadorParcelarSancoesMaisDeUmaConta") != null){
				parcelamentoPerfilActionForm.setIndicadorParcelarSancoesMaisDeUmaConta(httpServletRequest
								.getParameter("indicadorParcelarSancoesMaisDeUmaConta"));
			}
			if(httpServletRequest.getParameter("indicadorDebitoOriginalOuAtualizadoFaixaDebito") != null){
				parcelamentoPerfilActionForm.setIndicadorDebitoOriginalOuAtualizadoFaixaDebito(httpServletRequest
								.getParameter("indicadorDebitoOriginalOuAtualizadoFaixaDebito"));
			}
			if(httpServletRequest.getParameter("indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima") != null){
				parcelamentoPerfilActionForm.setIndicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima(httpServletRequest
								.getParameter("indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima"));
			}
			if(httpServletRequest.getParameter("valorMinimoDebitoAParcelarFaixaDebito") != null){
				parcelamentoPerfilActionForm.setValorMinimoDebitoAParcelarFaixaDebito(httpServletRequest
								.getParameter("valorMinimoDebitoAParcelarFaixaDebito"));
			}
			if(httpServletRequest.getParameter("valorMaximoDebitoAParcelarFaixaDebito") != null){
				parcelamentoPerfilActionForm.setValorMaximoDebitoAParcelarFaixaDebito(httpServletRequest
								.getParameter("valorMaximoDebitoAParcelarFaixaDebito"));
			}

			sessao.setAttribute("ParcelamentoPerfilActionForm", parcelamentoPerfilActionForm);

		}else if(sessao.getAttribute("UseCase").equals("ATUALIZARPERFIL")){
			AtualizarParcelamentoPerfilActionForm atualizarParcelamentoPerfilActionForm = (AtualizarParcelamentoPerfilActionForm) sessao
							.getAttribute("AtualizarParcelamentoPerfilActionForm");

			if(httpServletRequest.getParameter("percentualDescontoAcrescimo") != null){
				atualizarParcelamentoPerfilActionForm.setPercentualDescontoAcrescimo(httpServletRequest
								.getParameter("percentualDescontoAcrescimo"));
			}

			if(httpServletRequest.getParameter("percentualTarifaMinimaPrestacao") != null
							&& !httpServletRequest.getParameter("percentualTarifaMinimaPrestacao").toString().equalsIgnoreCase("")){
				atualizarParcelamentoPerfilActionForm.setPercentualTarifaMinimaPrestacao(httpServletRequest
								.getParameter("percentualTarifaMinimaPrestacao"));
				atualizarParcelamentoPerfilActionForm.setPercentualValorDebitoCalculoValorMinimoPrestacao(null);
				atualizarParcelamentoPerfilActionForm.setValorDebitoPrestacao(null);
			}else if(httpServletRequest.getParameter("percentualValorDebitoCalculoValorMinimoPrestacao") != null
							&& !httpServletRequest.getParameter("percentualValorDebitoCalculoValorMinimoPrestacao").toString()
											.equalsIgnoreCase("")){
				atualizarParcelamentoPerfilActionForm.setPercentualTarifaMinimaPrestacao(null);
				atualizarParcelamentoPerfilActionForm.setPercentualValorDebitoCalculoValorMinimoPrestacao(httpServletRequest
								.getParameter("percentualValorDebitoCalculoValorMinimoPrestacao"));
				atualizarParcelamentoPerfilActionForm.setValorDebitoPrestacao(null);
			}else if(httpServletRequest.getParameter("valorDebitoPrestacao") != null
							&& !httpServletRequest.getParameter("valorDebitoPrestacao").toString().equalsIgnoreCase("")){
				atualizarParcelamentoPerfilActionForm.setPercentualTarifaMinimaPrestacao(null);
				atualizarParcelamentoPerfilActionForm.setPercentualValorDebitoCalculoValorMinimoPrestacao(null);
				atualizarParcelamentoPerfilActionForm.setValorDebitoPrestacao(httpServletRequest.getParameter("valorDebitoPrestacao"));
			}

			// if (httpServletRequest.getParameter("percentualTarifaMinimaPrestacao") != null) {
			// atualizarParcelamentoPerfilActionForm.setPercentualTarifaMinimaPrestacao(httpServletRequest.getParameter("percentualTarifaMinimaPrestacao"));
			// }
			// if (httpServletRequest.getParameter("valorDebitoPrestacao") != null) {
			// atualizarParcelamentoPerfilActionForm.setValorDebitoPrestacao(httpServletRequest.getParameter("valorDebitoPrestacao"));
			// }
			if(httpServletRequest.getParameter("consumoMinimo") != null){
				atualizarParcelamentoPerfilActionForm.setConsumoMinimo(httpServletRequest.getParameter("consumoMinimo"));
			}
			if(httpServletRequest.getParameter("percentualVariacaoConsumoMedio") != null){
				atualizarParcelamentoPerfilActionForm.setPercentualVariacaoConsumoMedio(httpServletRequest
								.getParameter("percentualVariacaoConsumoMedio"));
			}
			if(httpServletRequest.getParameter("indicadorParcelarChequeDevolvido") != null){
				atualizarParcelamentoPerfilActionForm.setIndicadorParcelarChequeDevolvido(httpServletRequest
								.getParameter("indicadorParcelarChequeDevolvido"));
			}
			if(httpServletRequest.getParameter("indicadorParcelarSancoesMaisDeUmaConta") != null){
				atualizarParcelamentoPerfilActionForm.setIndicadorParcelarSancoesMaisDeUmaConta(httpServletRequest
								.getParameter("indicadorParcelarSancoesMaisDeUmaConta"));
			}
			if(httpServletRequest.getParameter("indicadorDebitoOriginalOuAtualizadoFaixaDebito") != null){
				atualizarParcelamentoPerfilActionForm.setIndicadorDebitoOriginalOuAtualizadoFaixaDebito(httpServletRequest
								.getParameter("indicadorDebitoOriginalOuAtualizadoFaixaDebito"));
			}
			if(httpServletRequest.getParameter("indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima") != null){
				atualizarParcelamentoPerfilActionForm.setIndicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima(httpServletRequest
								.getParameter("indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima"));
			}
			if(httpServletRequest.getParameter("valorMinimoDebitoAParcelarFaixaDebito") != null){
				atualizarParcelamentoPerfilActionForm.setValorMinimoDebitoAParcelarFaixaDebito(httpServletRequest
								.getParameter("valorMinimoDebitoAParcelarFaixaDebito"));
			}
			if(httpServletRequest.getParameter("valorMaximoDebitoAParcelarFaixaDebito") != null){
				atualizarParcelamentoPerfilActionForm.setValorMaximoDebitoAParcelarFaixaDebito(httpServletRequest
								.getParameter("valorMaximoDebitoAParcelarFaixaDebito"));
			}
			// if
			// (httpServletRequest.getParameter("percentualValorDebitoCalculoValorMinimoPrestacao")
			// != null) {
			// atualizarParcelamentoPerfilActionForm.setPercentualValorDebitoCalculoValorMinimoPrestacao(httpServletRequest.getParameter("percentualValorDebitoCalculoValorMinimoPrestacao"));
			// }
			if(httpServletRequest.getParameter("ultimaAlteracao") != null){
				atualizarParcelamentoPerfilActionForm.setUltimaAlteracao(httpServletRequest.getParameter("ultimaAlteracao"));
			}

			sessao.setAttribute("AtualizarParcelamentoPerfilActionForm", atualizarParcelamentoPerfilActionForm);
		}

		String valorDebitoPrestacao = "";
		if(httpServletRequest.getParameter("valorDebitoPrestacao") != null){
			valorDebitoPrestacao = httpServletRequest.getParameter("valorDebitoPrestacao");
		}

		String percentualTarifaMinimaPrestacao = "";
		if(httpServletRequest.getParameter("percentualTarifaMinimaPrestacao") != null){
			percentualTarifaMinimaPrestacao = httpServletRequest.getParameter("percentualTarifaMinimaPrestacao");
		}

		if(Util.isVazioOuBranco(valorDebitoPrestacao) && !Util.isVazioOuBranco(percentualTarifaMinimaPrestacao)){
			sessao.setAttribute("reloadPageTarifaMinima", false);
			sessao.setAttribute("reloadPageValorDebito", true);
		}else if(!Util.isVazioOuBranco(valorDebitoPrestacao) && Util.isVazioOuBranco(percentualTarifaMinimaPrestacao)){
			sessao.setAttribute("reloadPageTarifaMinima", true);
			sessao.setAttribute("reloadPageValorDebito", false);
		}else{
			sessao.setAttribute("reloadPageTarifaMinima", false);
			sessao.setAttribute("reloadPageValorDebito", false);
		}
	}

	private void adicionarParcelamentoQuantidadeReparcelamentoHelper(String quantidadeReparcelamento, HttpSession sessao,
					HttpServletRequest httpServletRequest, String adicionarReparcelamento){

		if(Util.isVazioOuBranco(quantidadeReparcelamento)){
			// Informe Qtde. Máxima Reparcelamentos Consecutivos
			throw new ActionServletException("atencao.required", null, " Qtde. Máxima Reparcelamentos Consecutivos");
		}else if(Util.validarValorNaoNumericoComoBigDecimal(quantidadeReparcelamento)){
			// Valor Mínimo da Prestação deve ser numerico
			throw new ActionServletException("atencao.integer", null, "  Qtde. Máxima Reparcelamentos Consecutivos");
		}

		Collection<ParcelamentoQuantidadeReparcelamentoHelper> collectionParcelamentoQuantidadeReparcelamentoHelper = null;

		if(sessao.getAttribute("collectionParcelamentoQuantidadeReparcelamentoHelper") != null){
			collectionParcelamentoQuantidadeReparcelamentoHelper = (Collection) sessao
							.getAttribute("collectionParcelamentoQuantidadeReparcelamentoHelper");
		}else{
			collectionParcelamentoQuantidadeReparcelamentoHelper = new ArrayList();
		}

		Short qtdeReparcelamentoShort = new Short(quantidadeReparcelamento);
		/*
		 * BigDecimal valorMinimoPrestacaoBigDecimal = null;
		 * if (valorMinimoPrestacao== null
		 * || valorMinimoPrestacao.equalsIgnoreCase("")){
		 * throw new ActionServletException(
		 * // Informe Valor Mínimo da Prestação
		 * "atencao.required", null, "  Valor Mínimo da Prestação");
		 * }else{
		 * valorMinimoPrestacaoBigDecimal =
		 * Util.formatarMoedaRealparaBigDecimal(valorMinimoPrestacao);
		 * }
		 */

		ParcelamentoQuantidadeReparcelamentoHelper parcelamentoQtdeReparcelamentoHelperNovo = new ParcelamentoQuantidadeReparcelamentoHelper();

		if(collectionParcelamentoQuantidadeReparcelamentoHelper != null && !collectionParcelamentoQuantidadeReparcelamentoHelper.isEmpty()){
			// se coleção não estiver vazia
			// verificar se a qtd máxima de prestações do parcelamento ja foi informada
			ParcelamentoQuantidadeReparcelamentoHelper parcelamentoQtdeReparcelamentoHelperAntigo = null;
			Iterator iterator = collectionParcelamentoQuantidadeReparcelamentoHelper.iterator();

			while(iterator.hasNext()){
				parcelamentoQtdeReparcelamentoHelperAntigo = (ParcelamentoQuantidadeReparcelamentoHelper) iterator.next();

				// [FS0003] Verificar quantidade máxima de reparcelamentos consecutivos
				if(qtdeReparcelamentoShort.equals(parcelamentoQtdeReparcelamentoHelperAntigo.getQuantidadeMaximaReparcelamento())){
					// Quantidade Máxima de Reparcelamentos Consecutivos já informada
					throw new ActionServletException("atencao.qtde_maxima_reparcelamento_ja_informado");
				}
			}

			if(Util.isVazioOuBranco(adicionarReparcelamento)){
				parcelamentoQtdeReparcelamentoHelperNovo.setQuantidadeMaximaReparcelamento(qtdeReparcelamentoShort);
				parcelamentoQtdeReparcelamentoHelperNovo.setInformacaoParcelamentoQtdeReparcelamento("NÃO INFORMADA");
				parcelamentoQtdeReparcelamentoHelperNovo.setUltimaAlteracao(new Date());
				collectionParcelamentoQuantidadeReparcelamentoHelper.add(parcelamentoQtdeReparcelamentoHelperNovo);
			}

		}

		// Ordena a coleção pela qunatidade de reparcelamento
		Collections.sort((List) collectionParcelamentoQuantidadeReparcelamentoHelper, new Comparator() {

			public int compare(Object a, Object b){

				Integer valorMinPrestacao1 = new Integer(((ParcelamentoQuantidadeReparcelamentoHelper) a)
								.getQuantidadeMaximaReparcelamento().toString());
				Integer valorMinPrestacao2 = new Integer(((ParcelamentoQuantidadeReparcelamentoHelper) b)
								.getQuantidadeMaximaReparcelamento().toString());

				return valorMinPrestacao1.compareTo(valorMinPrestacao2);

			}
		});

		// manda para a sessão a coleção de ParcelamentoQuantidadeReparcelamentoHelper
		sessao.setAttribute("collectionParcelamentoQuantidadeReparcelamentoHelper", collectionParcelamentoQuantidadeReparcelamentoHelper);
	}

	private void atualizaColecaoParcelamentoFaixaValorNaSessao(HttpSession sessao, HttpServletRequest httpServletRequest){

		if(sessao.getAttribute("collectionParcelamentoFaixaValor") != null
						&& !sessao.getAttribute("collectionParcelamentoFaixaValor").equals("")){

			Collection collectionParcelamentoFaixaValor = (Collection) sessao.getAttribute("collectionParcelamentoFaixaValor");
			// cria as variáveis para recuperar os parâmetros do request e jogar
			// no objeto ParcelamentoFaixaValor
			String perc = null;
			String vlFixoEntrada = null;
			String indicadorOpcoesPercentualFaixaValor = null;

			Iterator iteratorParcelamentoFaixaValor = collectionParcelamentoFaixaValor.iterator();

			while(iteratorParcelamentoFaixaValor.hasNext()){
				ParcelamentoFaixaValor parcelamentoFaixaValor = (ParcelamentoFaixaValor) iteratorParcelamentoFaixaValor.next();
				long valorTempo = parcelamentoFaixaValor.getUltimaAlteracao().getTime();

				perc = (String) httpServletRequest.getParameter("perc" + valorTempo);
				vlFixoEntrada = (String) httpServletRequest.getParameter("vlFixoEntrada" + valorTempo);
				indicadorOpcoesPercentualFaixaValor = (String) httpServletRequest.getParameter("indicadorOpcoesPercentualFaixaValor"
								+ valorTempo);

				// insere essas variáveis no objeto ParcelamentoFaixaValor
				BigDecimal percentual = null;
				if(!Util.isVazioOuBranco(perc)){
					percentual = Util.formatarMoedaRealparaBigDecimal(perc);
				}

				BigDecimal valorFixoEntrada = null;
				if(!Util.isVazioOuBranco(vlFixoEntrada)){
					valorFixoEntrada = Util.formatarMoedaRealparaBigDecimal(vlFixoEntrada);
				}

				Short indicadorOpcoesPercentualFaixaValorShor = Short.valueOf("1");
				if(!Util.isVazioOuBranco(indicadorOpcoesPercentualFaixaValor)){
					indicadorOpcoesPercentualFaixaValorShor = Short.valueOf(indicadorOpcoesPercentualFaixaValor);
				}

				parcelamentoFaixaValor.setPercentualFaixa(percentual);
				parcelamentoFaixaValor.setValorFixoEntrada(valorFixoEntrada);
				parcelamentoFaixaValor.setIndicadorPercentualFaixaValor(indicadorOpcoesPercentualFaixaValorShor);

			}

		}
	}

	/**
	 * Este caso de uso permite a inclusão de um novo perfil de parcelamento
	 * 
	 * @author isilva
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 */
	private void exibirAdicionarPercentualFaixaValorPopupAction(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String primeiraVez){

		HttpSession sessao = httpServletRequest.getSession(false);
		// Fachada fachada = Fachada.getInstancia();

		if(primeiraVez != null && primeiraVez.equals("S")){
			atualizaColecoesNaSessaoExibir(sessao, httpServletRequest);
		}

		this.adicionarPercentualFaixaValorPopupAction(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * @author isilva
	 * @param sessao
	 * @param httpServletRequest
	 */
	private void atualizaColecoesNaSessaoExibir(HttpSession sessao, HttpServletRequest httpServletRequest){

		if(sessao.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper") != null
						&& !sessao.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper").equals("")){

			Collection collectionParcelamentoQuantidadePrestacaoHelper = (Collection) sessao
							.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper");

			Iterator iteratorParcelamentoQuantidadePrestacaoHelper = collectionParcelamentoQuantidadePrestacaoHelper.iterator();

			while(iteratorParcelamentoQuantidadePrestacaoHelper.hasNext()){
				ParcelamentoQuantidadePrestacaoHelper parcelamentoQuantidadePrestacaoHelper = (ParcelamentoQuantidadePrestacaoHelper) iteratorParcelamentoQuantidadePrestacaoHelper
								.next();

				ParcelamentoQuantidadePrestacao parcelamentoQuantidadePrestacao = parcelamentoQuantidadePrestacaoHelper
								.getParcelamentoQuantidadePrestacao();

				long valorTempo = parcelamentoQuantidadePrestacao.getUltimaAlteracao().getTime();

				// cria as variáveis para recuperar os parâmetros do request e jogar
				// no objeto ParcelamentoQuantidadePrestacao
				String indicadorEntradaParcelamento = (String) httpServletRequest.getParameter("indicadorEntradaParcelamento" + valorTempo);
				String vlMinimoEntrada = (String) httpServletRequest.getParameter("vlMinimoEntrada" + valorTempo);
				String txJuros = (String) httpServletRequest.getParameter("txJuros" + valorTempo);
				String percMinEntrada = (String) httpServletRequest.getParameter("percMinEntrada" + valorTempo);
				String percTarMinImovel = (String) httpServletRequest.getParameter("percTarMinImovel" + valorTempo);
				String percVlReparcelado = (String) httpServletRequest.getParameter("percVlReparcelado" + valorTempo);
				String numeroMesesEntreParcelasStr = (String) httpServletRequest.getParameter("numeroMesesEntreParcelas" + valorTempo);
				String numeroParcelasALancarStr = (String) httpServletRequest.getParameter("numeroParcelasALancar" + valorTempo);
				String numeroMesesInicioCobrancaStr = (String) httpServletRequest.getParameter("numeroMesesInicioCobranca" + valorTempo);
				String numeroDiasVencimentoDaEntradaStr = (String) httpServletRequest.getParameter("numeroDiasVencimentoDaEntrada"
								+ valorTempo);

				// insere essas variáveis no objeto ParcelamentoQuantidadePrestacao
				Short indicadorEntradaParcelamentoShort = null;
				if(!Util.isVazioOuBranco(indicadorEntradaParcelamento)){
					indicadorEntradaParcelamentoShort = Short.valueOf(indicadorEntradaParcelamento);
				}

				BigDecimal vlMinimoEntradaBigDecimal = null;
				if(!Util.isVazioOuBranco(vlMinimoEntrada)){
					vlMinimoEntradaBigDecimal = Util.formatarMoedaRealparaBigDecimal(vlMinimoEntrada);
				}

				BigDecimal taxaJuros = null;
				if(!Util.isVazioOuBranco(txJuros)){
					taxaJuros = Util.formatarMoedaRealparaBigDecimal(txJuros);
				}else if(sessao.getAttribute("desabilitarTaxaJuros") != null
								&& sessao.getAttribute("desabilitarTaxaJuros").toString().equals("true")){

					taxaJuros = Util.formatarMoedaRealparaBigDecimal("0,00");
				}

				BigDecimal percentualMinimoEntrada = null;
				if(!Util.isVazioOuBranco(percMinEntrada)){
					percentualMinimoEntrada = Util.formatarMoedaRealparaBigDecimal(percMinEntrada);
				}

				BigDecimal percentualTarifaMinimaImovel = null;
				if(!Util.isVazioOuBranco(percTarMinImovel)){
					percentualTarifaMinimaImovel = Util.formatarMoedaRealparaBigDecimal(percTarMinImovel);
				}

				BigDecimal percentualValorReparcelado = null;
				if(!Util.isVazioOuBranco(percVlReparcelado)){
					percentualValorReparcelado = Util.formatarMoedaRealparaBigDecimal(percVlReparcelado);
				}

				// Meses entre Parcelas
				Integer numeroMesesEntreParcelas = null;
				if(!Util.isVazioOuBranco(numeroMesesEntreParcelasStr)){
					numeroMesesEntreParcelas = Util.converterStringParaInteger(numeroMesesEntreParcelasStr);
				}

				// Parcelas a Lançar
				Integer numeroParcelasALancar = null;
				if(!Util.isVazioOuBranco(numeroParcelasALancarStr)){
					numeroParcelasALancar = Util.converterStringParaInteger(numeroParcelasALancarStr);
				}

				// Meses para Início da Cobrança
				Integer numeroMesesInicioCobranca = null;
				if(!Util.isVazioOuBranco(numeroMesesInicioCobrancaStr)){
					numeroMesesInicioCobranca = Util.converterStringParaInteger(numeroMesesInicioCobrancaStr);
				}

				// Dias Vencimento da Entrada
				Integer numeroDiasVencimentoDaEntrada = null;
				if(!Util.isVazioOuBranco(numeroDiasVencimentoDaEntradaStr)){
					numeroDiasVencimentoDaEntrada = Util.converterStringParaInteger(numeroDiasVencimentoDaEntradaStr);
				}

				parcelamentoQuantidadePrestacao.setIndicadorEntradaParcelamento(indicadorEntradaParcelamentoShort);
				parcelamentoQuantidadePrestacao.setValorMinimoEntrada(vlMinimoEntradaBigDecimal);
				parcelamentoQuantidadePrestacao.setTaxaJuros(taxaJuros);
				parcelamentoQuantidadePrestacao.setPercentualMinimoEntrada(percentualMinimoEntrada);
				parcelamentoQuantidadePrestacao.setPercentualTarifaMinimaImovel(percentualTarifaMinimaImovel);
				parcelamentoQuantidadePrestacao.setPercentualValorReparcelado(percentualValorReparcelado);
				parcelamentoQuantidadePrestacao.setNumeroMesesEntreParcelas(numeroMesesEntreParcelas);
				parcelamentoQuantidadePrestacao.setNumeroParcelasALancar(numeroParcelasALancar);
				parcelamentoQuantidadePrestacao.setNumeroMesesInicioCobranca(numeroMesesInicioCobranca);
				parcelamentoQuantidadePrestacao.setNumeroDiasVencimentoDaEntrada(numeroDiasVencimentoDaEntrada);

				parcelamentoQuantidadePrestacaoHelper.setParcelamentoQuantidadePrestacao(parcelamentoQuantidadePrestacao);

			}
		}

		if(sessao.getAttribute("collectionParcelamentoFaixaValor") != null
						&& !sessao.getAttribute("collectionParcelamentoFaixaValor").equals("")){

			Collection collectionParcelamentoFaixaValor = (Collection) sessao.getAttribute("collectionParcelamentoFaixaValor");
			// cria as variáveis para recuperar os parâmetros do request e jogar
			// no objeto ParcelamentoFaixaValor
			String perc = null;
			String vlFixoEntrada = null;
			String indicadorOpcoesPercentualFaixaValor = null;

			Iterator iteratorParcelamentoFaixaValor = collectionParcelamentoFaixaValor.iterator();

			while(iteratorParcelamentoFaixaValor.hasNext()){
				ParcelamentoFaixaValor parcelamentoFaixaValor = (ParcelamentoFaixaValor) iteratorParcelamentoFaixaValor.next();
				long valorTempo = parcelamentoFaixaValor.getUltimaAlteracao().getTime();

				perc = (String) httpServletRequest.getParameter("perc" + valorTempo);
				vlFixoEntrada = (String) httpServletRequest.getParameter("vlFixoEntrada" + valorTempo);
				indicadorOpcoesPercentualFaixaValor = (String) httpServletRequest.getParameter("indicadorOpcoesPercentualFaixaValor"
								+ valorTempo);

				// insere essas variáveis no objeto ParcelamentoFaixaValor
				BigDecimal percentual = null;
				if(!Util.isVazioOuBranco(perc)){
					percentual = Util.formatarMoedaRealparaBigDecimal(perc);
				}

				BigDecimal valorFixoEntrada = null;
				if(!Util.isVazioOuBranco(vlFixoEntrada)){
					valorFixoEntrada = Util.formatarMoedaRealparaBigDecimal(vlFixoEntrada);
				}

				Short indicadorOpcoesPercentualFaixaValorShor = Short.valueOf("1");
				if(!Util.isVazioOuBranco(indicadorOpcoesPercentualFaixaValor)){
					indicadorOpcoesPercentualFaixaValorShor = Short.valueOf(indicadorOpcoesPercentualFaixaValor);
				}

				parcelamentoFaixaValor.setPercentualFaixa(percentual);
				parcelamentoFaixaValor.setValorFixoEntrada(valorFixoEntrada);
				parcelamentoFaixaValor.setIndicadorPercentualFaixaValor(indicadorOpcoesPercentualFaixaValorShor);
			}

		}

	}

	/**
	 * @author isilva
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 */
	public void adicionarPercentualFaixaValorPopupAction(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		// Instância do formulário que está sendo utilizado
		InserirPrestacoesParcelamentoPerfilActionForm inserirPrestacoesParcelamentoPerfilActionForm = (InserirPrestacoesParcelamentoPerfilActionForm) actionForm;

		// Limpa Quadro Entrada de Parcelamento
		// limparQuadroEntradaParcelamento(inserirPrestacoesParcelamentoPerfilActionForm);

		// Parãmetros recebidos para adicionar um Percentual Por Faixa de Valor
		String valorMaximo = inserirPrestacoesParcelamentoPerfilActionForm.getValorMaxPercFaixaValor();
		String percentual = inserirPrestacoesParcelamentoPerfilActionForm.getPercentualPercFaixaValor();
		String valorFixoEntrada = inserirPrestacoesParcelamentoPerfilActionForm.getValorFixoEntrada();
		String indicadorOpcoesPercentualFaixaValor = inserirPrestacoesParcelamentoPerfilActionForm.getIndicadorOpcoesPercentualFaixaValor();

		Collection collectionParcelamentoFaixaValor = null;
		ParcelamentoFaixaValor parcelamentoFaixaValor = new ParcelamentoFaixaValor();

		if(sessao.getAttribute("collectionParcelamentoFaixaValor") != null){
			collectionParcelamentoFaixaValor = (Collection) sessao.getAttribute("collectionParcelamentoFaixaValor");
		}else{
			collectionParcelamentoFaixaValor = new ArrayList();
		}

		// Validação dos campos recebidos
		if(valorMaximo == null || valorMaximo.equalsIgnoreCase("")){
			throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "Valor Máximo");
		}

		parcelamentoFaixaValor.setValorFaixa(Util.formatarMoedaRealparaBigDecimal(valorMaximo));

		// if (percentual == null || percentual.equalsIgnoreCase("")){
		// throw new ActionServletException(
		// "atencao.campo_texto.obrigatorio", null, "Percentual");
		// }

		if(collectionParcelamentoFaixaValor != null && !collectionParcelamentoFaixaValor.isEmpty()){
			// se coleção não estiver vazia
			// verificar se Valor Máximo ja foi informada
			ParcelamentoFaixaValor parcelamentoFaixaValorAntigo = null;
			Iterator iterator = collectionParcelamentoFaixaValor.iterator();

			while(iterator.hasNext()){
				parcelamentoFaixaValorAntigo = (ParcelamentoFaixaValor) iterator.next();

				if((Util.formatarMoedaRealparaBigDecimal(valorMaximo)).equals(parcelamentoFaixaValorAntigo.getValorFaixa())){
					// Limpa o formulário que adiciona Percentual Por Faixa de Valor
					limparQuadroPercentualPorFaixaValor(inserirPrestacoesParcelamentoPerfilActionForm);

					// Valor Máximo já informado.
					throw new ActionServletException("atencao.valor_maximo_ja_informado");
				}
			}
		}

		if(!Util.isVazioOuBranco(percentual)){
			parcelamentoFaixaValor.setPercentualFaixa(Util.formatarMoedaRealparaBigDecimal(percentual));
		}

		if(!Util.isVazioOuBranco(valorFixoEntrada)){
			parcelamentoFaixaValor.setValorFixoEntrada(Util.formatarMoedaRealparaBigDecimal(valorFixoEntrada));
		}

		if(!Util.isVazioOuBranco(indicadorOpcoesPercentualFaixaValor)){
			parcelamentoFaixaValor.setIndicadorPercentualFaixaValor(Short.valueOf(indicadorOpcoesPercentualFaixaValor));
		}else{
			parcelamentoFaixaValor.setIndicadorPercentualFaixaValor(Short.valueOf("1"));
		}

		parcelamentoFaixaValor.setUltimaAlteracao(new Date());

		collectionParcelamentoFaixaValor.add(parcelamentoFaixaValor);

		// Ordena a coleção pelo valor máximo
		Collections.sort((List) collectionParcelamentoFaixaValor, new Comparator() {

			public int compare(Object a, Object b){

				BigDecimal valorMax1 = new BigDecimal(((ParcelamentoFaixaValor) a).getValorFaixa().toString());
				BigDecimal valorMax2 = new BigDecimal(((ParcelamentoFaixaValor) b).getValorFaixa().toString());

				return valorMax1.compareTo(valorMax2);

			}
		});

		sessao.setAttribute("collectionParcelamentoFaixaValor", collectionParcelamentoFaixaValor);

		if(collectionParcelamentoFaixaValor == null || collectionParcelamentoFaixaValor.size() == 0){
			sessao.setAttribute("collectionParcelamentoFaixaValorVazia", "1");
		}else{
			sessao.setAttribute("collectionParcelamentoFaixaValorVazia", "2");
		}

		// Limpa o formulário que adiciona Percentual Por Faixa de Valor
		limparQuadroPercentualPorFaixaValor(inserirPrestacoesParcelamentoPerfilActionForm);
	}
}
