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
 * GSANPCG
 * Virgínia Melo
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

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaDocumento;
import gcom.cobranca.CobrancaForma;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.DebitoCreditoParcelamentoHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.fachada.Fachada;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.credito.CreditoTipo;
import gcom.faturamento.credito.FiltroCreditoARealizar;
import gcom.faturamento.debito.*;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.FiltroParametro;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade a emissão do extrato de débitos do imóvel
 * 
 * @author Vivianne Sousa
 * @date 24/08/2007
 */
public class EmissaoExtratoDebitoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("emissaoExtratoDebito");
		Fachada fachada = Fachada.getInstancia();

		DebitoCreditoDadosSelecaoExtratoActionForm form = (DebitoCreditoDadosSelecaoExtratoActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		String idImovel = form.getIdImovel();
		String indicadorIncluirAcrescimosImpontualidade = form.getIndicadorIncluirAcrescimosImpontualidade();
		String indicadorTaxaCobranca = form.getIndicadorTaxaCobranca();

		Collection<ContaValoresHelper> colecaoContas = null;
		Collection<GuiaPagamentoValoresHelper> colecaoGuiasPagamento = null;
		Collection<DebitoACobrar> colecaoDebitosACobrar = null;
		Collection<CreditoARealizar> colecaoCreditoARealizar = null;
		Collection<DebitoACobrar> colecaoDebitosACobrarParcelamento = null;
		Collection<CreditoARealizar> colecaoCreditoARealizarParcelamento = null;
		BigDecimal valorAcrescimosImpontualidade = BigDecimal.ZERO;
		BigDecimal valorDocumento = BigDecimal.ZERO;
		BigDecimal valorIncluirAcrescimoComoDesconto = BigDecimal.ZERO;
		BigDecimal valorDescontoAntecipacao = BigDecimal.ZERO;



		String idsContas = httpServletRequest.getParameter("conta");
		String idsDebitos = httpServletRequest.getParameter("debito");
		String idsCreditos = httpServletRequest.getParameter("credito");
		String idsGuias = httpServletRequest.getParameter("guiaPagamento");
		String idsParcelamentos = httpServletRequest.getParameter("parcelamento");

		Object[] contas = this.obterContasSelecionadas(idsContas, sessao);
		Object[] debitos = this.obterDebitosSelecionados(idsDebitos, sessao, fachada);
		Object[] creditos = this.obterCreditosSelecionadas(idsCreditos, sessao);
		Object[] guiasParcelas = this.obterGuiasParcelasSelecionadas(idsGuias, sessao);
		Object[] parcelamentos = this.obterParcelamentosSelecionados(idsParcelamentos, sessao, fachada);
		BigDecimal valorDebitosParcelamento = BigDecimal.ZERO;
		BigDecimal valorDebitosNaoParcelamento = BigDecimal.ZERO;

		if(contas != null){

			colecaoContas = (Collection) contas[0];

			if(contas[2] != null && !indicadorIncluirAcrescimosImpontualidade.equals(CobrancaDocumento.NAO_INCLUIR_ACRESCIMOS)){

				valorAcrescimosImpontualidade = (BigDecimal) contas[2];
			}
		}

		if(debitos != null){

			colecaoDebitosACobrar = (Collection) debitos[0];
			valorDebitosNaoParcelamento = (BigDecimal) debitos[1];
		}else{

			valorDebitosNaoParcelamento = BigDecimal.ZERO;
		}

		if(creditos != null){

			colecaoCreditoARealizar = (Collection) creditos[0];
		}

		if(guiasParcelas != null){

			colecaoGuiasPagamento = (Collection) guiasParcelas[0];
		}

		if(parcelamentos != null){

			colecaoDebitosACobrarParcelamento = (Collection) parcelamentos[0];
			valorDebitosParcelamento = (BigDecimal) parcelamentos[1];

			if(colecaoDebitosACobrarParcelamento != null){

				if(colecaoDebitosACobrar == null){

					colecaoDebitosACobrar = new ArrayList();
				}

				colecaoDebitosACobrar.addAll(colecaoDebitosACobrarParcelamento);
			}

			colecaoCreditoARealizarParcelamento = (Collection) parcelamentos[2];

			if(colecaoCreditoARealizarParcelamento != null){

				if(colecaoCreditoARealizar == null){

					colecaoCreditoARealizar = new ArrayList();
				}

				colecaoCreditoARealizar.addAll(colecaoCreditoARealizarParcelamento);
			}

			valorDescontoAntecipacao = (BigDecimal) parcelamentos[5];
		}else{

			valorDebitosParcelamento = BigDecimal.ZERO;
		}

		if(indicadorIncluirAcrescimosImpontualidade.equals(CobrancaDocumento.INCLUIR_ACRESCIMOS_COM_DESCONTO)){

			valorIncluirAcrescimoComoDesconto = valorIncluirAcrescimoComoDesconto.add(valorAcrescimosImpontualidade);
			valorAcrescimosImpontualidade = BigDecimal.ZERO;
		}

		if(indicadorTaxaCobranca != null && indicadorTaxaCobranca.equals(ConstantesSistema.SIM.toString())){

			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, Integer.valueOf(idImovel)));
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");

			filtroImovel.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO, Imovel.IMOVEL_EXCLUIDO,
							FiltroParametro.CONECTOR_OR, 2));

			filtroImovel.adicionarParametro(new ParametroNulo(FiltroImovel.INDICADOR_IMOVEL_EXCLUIDO));

			Collection<Imovel> imovelPesquisado = fachada.pesquisar(filtroImovel, Imovel.class.getName());
			Imovel imovel = imovelPesquisado.iterator().next();

			SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

			BigDecimal valorTaxa = fachada.obterValorTaxaDocumentoCobranca(imovel, sistemaParametro);
			valorDocumento = valorDocumento.add(valorTaxa);

			DebitoTipo debitoTipo = null;

			// Pesquisa o tipo de débito no sistema
			debitoTipo = fachada.pesquisarDebitoTipo(DebitoTipo.TAXA_COBRANCA);

			// Cria a variável que vai armazenar a situação de crédito/débito
			DebitoCreditoSituacao debitoCreditoSituacaoAtual = new DebitoCreditoSituacao();
			debitoCreditoSituacaoAtual.setId(DebitoCreditoSituacao.NORMAL);

			// Cria a variável que vai armazenar a forma de cobrança
			CobrancaForma cobrancaForma = new CobrancaForma();
			cobrancaForma.setId(CobrancaForma.COBRANCA_EM_CONTA);

			/** Cria o débito a cobrar geral */
			DebitoACobrarGeral debitoACobrarGeral = new DebitoACobrarGeral();
			debitoACobrarGeral.setIndicadorHistorico(Short.valueOf("2"));
			debitoACobrarGeral.setUltimaAlteracao(new Date());
			Integer idDebitoACobrarGeral = (Integer) fachada.inserir(debitoACobrarGeral);

			// Cria o débito a cobrar
			// Seta as informações necessárias para criar o débito a cobrar
			DebitoACobrar debitoACobrar = new DebitoACobrar();
			debitoACobrar.setImovel(imovel);
			debitoACobrar.setDebitoTipo(debitoTipo);
			debitoACobrar.setGeracaoDebito(new Date());
			debitoACobrar.setAnoMesReferenciaDebito(null);
			debitoACobrar.setAnoMesCobrancaDebito(sistemaParametro.getAnoMesArrecadacao());
			debitoACobrar.setAnoMesReferenciaContabil(sistemaParametro.getAnoMesFaturamento());
			debitoACobrar.setValorDebito(valorTaxa);
			debitoACobrar.setNumeroPrestacaoDebito((Short.valueOf("1")).shortValue());
			debitoACobrar.setNumeroPrestacaoCobradas((Short.valueOf("0")).shortValue());
			debitoACobrar.setLocalidade(imovel.getLocalidade());
			debitoACobrar.setQuadra(imovel.getQuadra());
			debitoACobrar.setCodigoSetorComercial(imovel.getSetorComercial().getCodigo());
			debitoACobrar.setNumeroQuadra(imovel.getQuadra().getNumeroQuadra());
			debitoACobrar.setNumeroLote(imovel.getLote());
			debitoACobrar.setNumeroSubLote(imovel.getSubLote());
			debitoACobrar.setPercentualTaxaJurosFinanciamento(BigDecimal.ZERO);
			debitoACobrar.setRegistroAtendimento(null);
			debitoACobrar.setOrdemServico(null);
			debitoACobrar.setFinanciamentoTipo(debitoTipo.getFinanciamentoTipo());
			debitoACobrar.setLancamentoItemContabil(debitoTipo.getLancamentoItemContabil());
			debitoACobrar.setDebitoCreditoSituacaoAtual(debitoCreditoSituacaoAtual);
			debitoACobrar.setDebitoCreditoSituacaoAnterior(null);
			debitoACobrar.setParcelamentoGrupo(null);
			debitoACobrar.setCobrancaForma(cobrancaForma);
			debitoACobrar.setUltimaAlteracao(new Date());
			debitoACobrar.setId(idDebitoACobrarGeral);
			debitoACobrar.setDebitoACobrarGeral(debitoACobrarGeral);
			// debitoACobrar.setDataAntecipacao(new Date());
			// debitoACobrar.setQuantidadeParcelasAntecipadas(parcelamentos.length);

			fachada.inserir(debitoACobrar);

			// [UC0108] Obter Quantidade de Economias por Categoria
			Collection<Categoria> colecaoCategoriasImovel = fachada.obterQuantidadeEconomiasCategoria(imovel);

			// [UC0185] Obter Valor por Categoria, passando o valor da taxa
			Collection<BigDecimal> colecaoValorePorCategoria = fachada.obterValorPorCategoria(colecaoCategoriasImovel, valorTaxa);

			// Cria as iterações de categoria e valor
			Iterator iteratorCategoria = colecaoCategoriasImovel.iterator();
			Iterator iteratorValorPorCategoria = colecaoValorePorCategoria.iterator();

			// Inclui na tabela DEBITO_A_COBRAR_CATEGORIA a(s) categoria(s) e
			// sua(s) respectiva(s) quantidade(s) de economia retornados pels
			// [UC0108] e os valores retornados pelo [UC0185] para cada categoria
			while(iteratorCategoria.hasNext()){

				// Recupera a categoria
				Categoria categoria = (Categoria) iteratorCategoria.next();

				// Recupera o valor da categoria
				BigDecimal valorPorCategoria = (BigDecimal) iteratorValorPorCategoria.next();

				// Cria o débito a cobrar categoria
				DebitoACobrarCategoria debitoACobrarCategoria = new DebitoACobrarCategoria();

				DebitoACobrarCategoriaPK debitoACobrarCategoriaPK = new DebitoACobrarCategoriaPK(debitoACobrar, categoria);
				debitoACobrarCategoria.setComp_id(debitoACobrarCategoriaPK);
				debitoACobrarCategoria.setQuantidadeEconomia(categoria.getQuantidadeEconomiasCategoria());
				debitoACobrarCategoria.setValorCategoria(valorPorCategoria);
				debitoACobrarCategoria.setUltimaAlteracao(new Date());

				// Inserindo o DEBITO_A_COBRAR_CATEGORIA no banco de dados
				fachada.inserir(debitoACobrarCategoria);
			}

			if(colecaoDebitosACobrar == null){
				colecaoDebitosACobrar = new ArrayList();
			}
			colecaoDebitosACobrar.add(debitoACobrar);
		}

		sessao.setAttribute("colecaoContasExtrato", colecaoContas);
		sessao.setAttribute("colecaoGuiasPagamentoExtrato", colecaoGuiasPagamento);
		sessao.setAttribute("colecaoDebitosACobrarExtratoSelecao", colecaoDebitosACobrar);
		sessao.setAttribute("colecaoCreditoARealizarExtratoSelecao", colecaoCreditoARealizar);
		sessao.setAttribute("valorAcrescimosImpontualidadeExtrato", valorAcrescimosImpontualidade);
		sessao.setAttribute("valorIncluirAcrescimoComoDesconto", valorIncluirAcrescimoComoDesconto);
		sessao.setAttribute("idImovelExtrato", idImovel);
		sessao.setAttribute("nomeClienteExtrato", form.getNomeClienteUsuarioImovel());
		sessao.setAttribute("valorDebitosExtratoSelecao",
						valorDebitosParcelamento.add(valorDebitosNaoParcelamento).add(valorDescontoAntecipacao));

		if(idsDebitos != null){

			sessao.setAttribute("qtdeParcelasAntecipadasDebitos", debitos[2].toString());

			Map<Integer, Integer> idParacelamentoDebitosMap = new HashMap<Integer, Integer>();
			for(DebitoACobrar debitoACobrar : (Collection<DebitoACobrar>) debitos[0]){
				if(debitoACobrar.getParcelamento() == null){
					idParacelamentoDebitosMap.put(debitoACobrar.getDebitoTipo().getId(), debitoACobrar.getAnoMesCobrancaDebito());
				}
			}
			if(idParacelamentoDebitosMap.size() == 1){
				sessao.setAttribute("quantidadeDebitoACobrar", idParacelamentoDebitosMap.size());
			}

		}else{

			sessao.removeAttribute("qtdeParcelasAntecipadasDebitos");
		}

		if(parcelamentos != null){

			sessao.setAttribute("qtdeParcelasAntecipadas", parcelamentos[4].toString());
			sessao.setAttribute("valorDescontoExtrato", parcelamentos[5]);
			Map<Integer, Integer> idParcelamentoMap = new HashMap<Integer, Integer>();
			for(DebitoACobrar debitoACobrar : colecaoDebitosACobrarParcelamento){
				if(debitoACobrar.getParcelamento() != null){
					if(!idParcelamentoMap.containsKey(debitoACobrar.getParcelamento().getId())){
						idParcelamentoMap.put(debitoACobrar.getParcelamento().getId(), debitoACobrar.getAnoMesCobrancaDebito());
					}
				}
			}
			if(idParcelamentoMap.size() == 1){
				sessao.setAttribute("quantidadeParcelamento", idParcelamentoMap.size());
			}
		}else{

			sessao.removeAttribute("qtdeParcelasAntecipadas");
			sessao.removeAttribute("valorDescontoExtrato");
		}

		return retorno;
	}

	private Object[] obterContasSelecionadas(String idsContas, HttpSession sessao){

		Object[] retorno = null;
		Collection<ContaValoresHelper> colecaoContas = null;
		BigDecimal valorTotalConta = BigDecimal.ZERO;
		BigDecimal valorTotalAcrescimoImpontualidade = BigDecimal.ZERO;

		if(idsContas != null && !idsContas.equals("")){
			retorno = new Object[3];
			colecaoContas = new ArrayList();

			Collection colecaoContasSessao = (Collection) sessao.getAttribute("colecaoContaExtrato");
			Iterator itColecaoContasSessao = colecaoContasSessao.iterator();
			ContaValoresHelper contaValoresHelper = null;

			String[] idsContasArray = idsContas.split(",");

			while(itColecaoContasSessao.hasNext()){

				contaValoresHelper = (ContaValoresHelper) itColecaoContasSessao.next();

				for(int x = 0; x < idsContasArray.length; x++){

					if(contaValoresHelper.getConta().getId().equals(Integer.valueOf(idsContasArray[x]))){
						colecaoContas.add(contaValoresHelper);
						valorTotalConta = valorTotalConta.add(contaValoresHelper.getValorTotalConta());
						valorTotalAcrescimoImpontualidade = valorTotalAcrescimoImpontualidade.add(contaValoresHelper
										.getValorTotalContaValoresParcelamento());

						if(contaValoresHelper.getConta().getValorImposto() != null){
							valorTotalConta = valorTotalConta.subtract(contaValoresHelper.getConta().getValorImposto());
						}

					}
				}
			}
			retorno[0] = colecaoContas;
			retorno[1] = valorTotalConta;
			retorno[2] = valorTotalAcrescimoImpontualidade;
		}

		return retorno;
	}

	private Object[] obterDebitosSelecionados(String idsDebitos, HttpSession sessao, Fachada fachada){

		Object[] retorno = null;
		Collection<DebitoACobrar> colecaoDebitos = null;
		BigDecimal valorTotalDebitoACobrar = BigDecimal.ZERO;
		BigDecimal valorDebitoACobrar = BigDecimal.ZERO;
		Integer totalParcelasAntecipadas = 0;

		if(idsDebitos != null && !idsDebitos.equals("")){
			retorno = new Object[3];
			colecaoDebitos = new ArrayList();

			Collection colecaoDebitosSessao = (Collection) sessao.getAttribute("colecaoDebitoACobrarExtrato");
			Iterator itColecaoDebitosSessao = colecaoDebitosSessao.iterator();
			DebitoACobrar debitoACobrar = null;

			String[] idsDebitosArray = idsDebitos.split(",");
			String[] itemIdsParcelamentoArray;

			while(itColecaoDebitosSessao.hasNext()){

				debitoACobrar = (DebitoACobrar) itColecaoDebitosSessao.next();

				totalParcelasAntecipadas = 0;

				for(int x = 0; x < idsDebitosArray.length; x++){

					itemIdsParcelamentoArray = idsDebitosArray[x].split("_");

					if(debitoACobrar.getId().equals(Integer.valueOf(itemIdsParcelamentoArray[0]))){

						// Verifica se está antecipando todas as pareclas restantes
						if(itemIdsParcelamentoArray[1].equals("todas")){

							totalParcelasAntecipadas = totalParcelasAntecipadas
											+ (debitoACobrar.getNumeroPrestacaoDebito() - debitoACobrar.getNumeroPrestacaoCobradas());
						}else{
							totalParcelasAntecipadas = Integer.valueOf(itemIdsParcelamentoArray[1]);
						}

					}

				}

				if(totalParcelasAntecipadas.intValue() == 0){

					valorTotalDebitoACobrar = valorTotalDebitoACobrar.add(debitoACobrar.getValorTotal()).setScale(
									Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
				}else{

					// Calcula o valor da prestação
					// BigDecimal valorPrestacao = debitoACobrar.getValorDebito().divide(
					// new BigDecimal(debitoACobrar.getNumeroPrestacaoDebito()),
					// Parcelamento.CASAS_DECIMAIS,
					// Parcelamento.TIPO_ARREDONDAMENTO);
					//
					// valorTotalDebitoACobrar =
					// valorTotalDebitoACobrar.add(valorPrestacao.multiply(new BigDecimal(
					// totalParcelasAntecipadas.toString())));

					valorDebitoACobrar = fachada.calcularValorPrestacaoParcelasAntecipadas(debitoACobrar, totalParcelasAntecipadas);

				}

				debitoACobrar.setQuantidadeParcelasAntecipadas(totalParcelasAntecipadas);
				debitoACobrar.setDataAntecipacao(new Date());

				System.out.println("*****obterDebitosSelecionados -> debito : " + debitoACobrar.getId() + " - "
								+ " quantidadeAntecipação :" + totalParcelasAntecipadas);

				colecaoDebitos.add(debitoACobrar);
				valorTotalDebitoACobrar = valorTotalDebitoACobrar.add(valorDebitoACobrar);

			}
			retorno[0] = colecaoDebitos;
			retorno[1] = valorTotalDebitoACobrar;
			retorno[2] = totalParcelasAntecipadas;
		}

		return retorno;
	}

	private Object[] obterCreditosSelecionadas(String idsCreditos, HttpSession sessao){

		Object[] retorno = null;
		Collection<CreditoARealizar> colecaoCreditos = null;
		BigDecimal valorTotalCreditoARealizar = BigDecimal.ZERO;

		if(idsCreditos != null && !idsCreditos.equals("")){
			retorno = new Object[2];
			colecaoCreditos = new ArrayList();

			Collection colecaoCreditosSessao = (Collection) sessao.getAttribute("colecaoCreditoARealizarExtrato");
			colecaoCreditosSessao = this.tratarCreditosDescontoAcrescimoPorImpontualidade(colecaoCreditosSessao);
			Iterator itColecaoCreditosSessao = colecaoCreditosSessao.iterator();
			CreditoARealizar creditoARealizar = null;

			String[] idsCreditosArray = idsCreditos.split(",");

			while(itColecaoCreditosSessao.hasNext()){

				creditoARealizar = (CreditoARealizar) itColecaoCreditosSessao.next();

				for(int x = 0; x < idsCreditosArray.length; x++){

					if(Util.isInteger(idsCreditosArray[x]) && creditoARealizar.getId().equals(Integer.valueOf(idsCreditosArray[x]))){
						colecaoCreditos.add(creditoARealizar);
						valorTotalCreditoARealizar = valorTotalCreditoARealizar.add(creditoARealizar.getValorTotal());

					}
				}
			}
			retorno[0] = colecaoCreditos;
			retorno[1] = valorTotalCreditoARealizar;
		}

		return retorno;
	}

	private Object[] obterGuiasParcelasSelecionadas(String idsGuias, HttpSession sessao){

		Object[] retorno = null;
		Collection<GuiaPagamentoValoresHelper> colecaoGuias = null;
		BigDecimal valorTotalGuiaPagamento = BigDecimal.ZERO;

		retorno = new Object[2];
		colecaoGuias = new ArrayList();

		if(idsGuias != null && !idsGuias.equals("")){

			// Pegando a coleção de Guias que foram exibidas
			Collection colecaoGuiasSessao = (Collection) sessao.getAttribute("colecaoGuiaPagamentoExtrato");
			Iterator itColecaoGuiasSessao = colecaoGuiasSessao.iterator();
			GuiaPagamentoValoresHelper guiaPagamentoValoresHelper = null;

			// Separando linhas selecionadas:
			String[] idsGuiasArray = idsGuias.split(",");

			while(itColecaoGuiasSessao.hasNext()){

				guiaPagamentoValoresHelper = (GuiaPagamentoValoresHelper) itColecaoGuiasSessao.next();

				// Varrendo o array de guias_parcelas
				for(int x = 0; x < idsGuiasArray.length; x++){

					String[] idGuiaUnderlineParcela = idsGuiasArray[x].split("_");

					// Se o id do que foi exibido é igual ao id do que foi selecionado, insere na
					// coleção
					if(guiaPagamentoValoresHelper.getIdGuiaPagamento().equals(Integer.valueOf(idGuiaUnderlineParcela[0]))
									&& guiaPagamentoValoresHelper.getNumeroPrestacao().equals(Short.valueOf(idGuiaUnderlineParcela[1]))){

						colecaoGuias.add(guiaPagamentoValoresHelper);
						valorTotalGuiaPagamento = valorTotalGuiaPagamento.add(guiaPagamentoValoresHelper.getValorTotalPrestacao());
					}
				}
			}
		}

		retorno[0] = colecaoGuias;
		retorno[1] = valorTotalGuiaPagamento;

		return retorno;
	}

	private Integer obterTotalParcelasAntecipadas(Collection colecaoDebitoCreditoParcelamentoSessao){

		Integer quantidadeParcelasAntecipadas = 0;

		Iterator itColecaoDebitoCreditoParcelamentoSessao = colecaoDebitoCreditoParcelamentoSessao.iterator();
		
		if(colecaoDebitoCreditoParcelamentoSessao != null){

			while(itColecaoDebitoCreditoParcelamentoSessao.hasNext()){

				// debitoCreditoParcelamentoHelper = (DebitoCreditoParcelamentoHelper)
				// itColecaoDebitoCreditoParcelamentoSessao.next();

				// Iterator<DebitoACobrar> iterator =
				// colecaoDebitoCreditoParcelamentoSessao.iterator();
				// while(iterator.hasNext()){

				DebitoACobrar debitoACobrarTMP = (DebitoACobrar) itColecaoDebitoCreditoParcelamentoSessao.next();

				if(!debitoACobrarTMP.getDebitoTipo().getId().equals(DebitoTipo.JUROS_SOBRE_PARCELAMENTO)){

					Integer totalParcelas = debitoACobrarTMP.getNumeroPrestacaoDebito() - debitoACobrarTMP.getNumeroPrestacaoCobradas();
						
					quantidadeParcelasAntecipadas = totalParcelas;


				}
			}
		}

		return quantidadeParcelasAntecipadas;

	}

	private Object[] obterParcelamentosSelecionados(String idsParcelamentos, HttpSession sessao, Fachada fachada){
		
		
		
		Object[] retorno = null;
		Collection<DebitoACobrar> colecaoDebitoFinal = null;
		Collection<CreditoARealizar> colecaoCreditoFinal = null;
		BigDecimal valorTotalDebito = BigDecimal.ZERO;
		BigDecimal valorDebito = BigDecimal.ZERO;
		BigDecimal valorTotalCredito = BigDecimal.ZERO;
		BigDecimal valorDesconto = BigDecimal.ZERO;
		BigDecimal valorTotalDesconto = BigDecimal.ZERO;
		Integer totalParcelasAntecipadas = 0;

		if(idsParcelamentos != null && !idsParcelamentos.equals("")){

			retorno = new Object[6];
			colecaoDebitoFinal = new ArrayList();
			colecaoCreditoFinal = new ArrayList();
			Collection colecaoDebitoCreditoParcelamentoSessao = (Collection) sessao.getAttribute("colecaoDebitoCreditoParcelamento");
			Iterator itColecaoDebitoCreditoParcelamentoSessao = colecaoDebitoCreditoParcelamentoSessao.iterator();
			DebitoCreditoParcelamentoHelper debitoCreditoParcelamentoHelper = null;
			String[] idsParcelamentoArray = idsParcelamentos.split(",");
			String[] ItemIdsParcelamentoArray;

			while(itColecaoDebitoCreditoParcelamentoSessao.hasNext()){

				debitoCreditoParcelamentoHelper = (DebitoCreditoParcelamentoHelper) itColecaoDebitoCreditoParcelamentoSessao.next();

				for(int x = 0; x < idsParcelamentoArray.length; x++){

					ItemIdsParcelamentoArray = idsParcelamentoArray[x].split("_");

					if(debitoCreditoParcelamentoHelper.getParcelamento().getId().equals(Integer.valueOf(ItemIdsParcelamentoArray[0]))){

						// Verifica se está antecipando todas as pareclas restantes
						if(ItemIdsParcelamentoArray[1].equals("todas")){

							totalParcelasAntecipadas = this.obterTotalParcelasAntecipadas(debitoCreditoParcelamentoHelper
											.getColecaoDebitoACobrarParcelamento());


						}else{

							totalParcelasAntecipadas = totalParcelasAntecipadas + Integer.valueOf(ItemIdsParcelamentoArray[1]);
						}

						Collection<DebitoACobrar> colecaoDebito = null;
						Collection<CreditoARealizar> colecaoCredito = null;

						if(debitoCreditoParcelamentoHelper.getColecaoCreditoARealizarParcelamento() != null
										&& !debitoCreditoParcelamentoHelper.getColecaoCreditoARealizarParcelamento().isEmpty()){

							colecaoCredito = debitoCreditoParcelamentoHelper.getColecaoCreditoARealizarParcelamento();
							colecaoCredito = this.tratarCreditosDescontoAcrescimoPorImpontualidade(colecaoCredito);

							Iterator iterCredito = colecaoCredito.iterator();
							while(iterCredito.hasNext()){

								CreditoARealizar creditoARealizar = (CreditoARealizar) iterCredito.next();

								CreditoTipo creditoTipo = creditoARealizar.getCreditoTipo();

								if(creditoTipo != null){

									Integer idCreditoTipo = creditoTipo.getId();

									// Verificação necessária, pois esses dois tipos de crédito não
									// são apresentados na tela:
									// ControladorCobranca.apresentarDebitoCreditoImovelExtratoDebito
									if(!idCreditoTipo.equals(CreditoTipo.DESCONTO_INATIVIDADE_LIGACAO_AGUA)
													&& !idCreditoTipo.equals(CreditoTipo.DESCONTO_ANTIGUIDADE_DEBITO)){

										colecaoCreditoFinal.add(creditoARealizar);
										valorTotalCredito = valorTotalCredito.add(creditoARealizar.getValorTotal());
									}
								}
							}

						}

						if(debitoCreditoParcelamentoHelper.getColecaoDebitoACobrarParcelamento() != null
										&& !debitoCreditoParcelamentoHelper.getColecaoDebitoACobrarParcelamento().isEmpty()){
							colecaoDebito = debitoCreditoParcelamentoHelper.getColecaoDebitoACobrarParcelamento();

							this.tratarDebitosParcelamentoComDescontosNosAcrescimosPorImpontualidade(colecaoDebito);

							Iterator<DebitoACobrar> iterDebito = colecaoDebito.iterator();
							while(iterDebito.hasNext()){

								DebitoACobrar debitoACobrar = iterDebito.next();

								if(debitoACobrar.getParcelamento().getId().equals(Integer.valueOf(ItemIdsParcelamentoArray[0]))){

									debitoACobrar.setQuantidadeParcelasAntecipadas(totalParcelasAntecipadas);
									debitoACobrar.setDataAntecipacao(new Date());


									// Caso seja JUROS_SOBRE_PARCELAMENTO, não colocar valor.
									if(!debitoACobrar.getDebitoTipo().getId().equals(DebitoTipo.JUROS_SOBRE_PARCELAMENTO)){

										System.out.println("*****obterParcelamentosSelecionados != JUROS_SOBRE_PARCELAMENTO -> debito : "
														+ debitoACobrar.getId() + " - " + " quantidadeAntecipação :"
														+ totalParcelasAntecipadas);

										colecaoDebitoFinal.add(debitoACobrar);

										if(totalParcelasAntecipadas.intValue() == 0){

											valorTotalDebito = valorTotalDebito.add(debitoACobrar.getValorTotal()).setScale(
															Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
										}else{

											// Calcula o valor da prestação
//											BigDecimal valorPrestacao = debitoACobrar.getValorDebito().divide(
//															new BigDecimal(debitoACobrar.getNumeroPrestacaoDebito()),
//															Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
//
//											valorTotalDebito = valorTotalDebito.add(valorPrestacao.multiply(new BigDecimal(
//															totalParcelasAntecipadas.toString())));
//												    
											// BigDecimal valorPrestacao =
											// fachada.calcularValorPrestacaoParcelasAntecipadas(debitoACobrar,
											// totalParcelasAntecipadas);
											
											valorDebito = fachada.calcularValorPrestacaoParcelasAntecipadas(debitoACobrar,
															totalParcelasAntecipadas);
											
											valorTotalDebito = valorTotalDebito.add(valorDebito);

										}

																				
										
									}else{

										if(totalParcelasAntecipadas.intValue() == 0){

											valorTotalDesconto = valorTotalDesconto.add(debitoACobrar.getValorTotal()).setScale(
															Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
										}else{

											// Calcula o valor da prestação
//											BigDecimal valorPrestacao = debitoACobrar.getValorDebito().divide(
//															new BigDecimal(debitoACobrar.getNumeroPrestacaoDebito()),
//															Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);

//											valorTotalDesconto = valorTotalDesconto.add(valorPrestacao.multiply(new BigDecimal(
//															totalParcelasAntecipadas.toString())));
//											
//											
											valorDesconto = fachada.calcularValorPrestacaoParcelasAntecipadas(
															debitoACobrar, totalParcelasAntecipadas);

											valorTotalDesconto = valorTotalDesconto.add(valorDesconto);


											
											

										}

										System.out.println("*****obterParcelamentosSelecionados == JUROS_SOBRE_PARCELAMENTO -> debito : "
														+ debitoACobrar.getId() + " - " + " quantidadeAntecipação :"
														+ totalParcelasAntecipadas);

									}
								}
							}
						}
					}
				}
			}
			retorno[0] = colecaoDebitoFinal;
			retorno[1] = valorTotalDebito;
			retorno[2] = colecaoCreditoFinal;
			retorno[3] = valorTotalCredito;
			retorno[4] = totalParcelasAntecipadas;
			retorno[5] = valorTotalDesconto;
		}

		return retorno;
	}

	/**
	 * [UC04444][SB0004] – Verificar créditos de descontos nos acréscimos por impontualidade
	 * 
	 * @param collCreditoARealizar
	 * @return
	 */
	private Collection<CreditoARealizar> tratarCreditosDescontoAcrescimoPorImpontualidade(Collection<CreditoARealizar> collCreditoARealizar){

		Collection<CreditoARealizar> novaColecao = new ArrayList<CreditoARealizar>();

		if(collCreditoARealizar != null){

			for(CreditoARealizar creditoARealizar : collCreditoARealizar){

				if(!creditoARealizar.getCreditoTipo().getId().equals(CreditoTipo.DESCONTO_ACRESCIMOS_IMPONTUALIDADE)){

					novaColecao.add(creditoARealizar);

				}

			}

		}

		return novaColecao;

	}

	/**
	 * [UC04444][SB0005] – Verificar débitos de parcelamento com descontos nos acréscimos por
	 * impontualidade
	 * 
	 * @param collDebitosACobrar
	 * @return
	 */
	private Collection<DebitoACobrar> tratarDebitosParcelamentoComDescontosNosAcrescimosPorImpontualidade(
					Collection<DebitoACobrar> collDebitosACobrar){

		Collection<DebitoACobrar> novaColecao = new ArrayList<DebitoACobrar>();
		Map<Integer, Collection<DebitoACobrar>> mapDebitosParcelamento = new HashMap<Integer, Collection<DebitoACobrar>>();

		if(collDebitosACobrar != null){

			for(DebitoACobrar debitoACobrar : collDebitosACobrar){

				if(debitoACobrar.getParcelamento() != null){

					if(mapDebitosParcelamento.containsKey(debitoACobrar.getParcelamento().getId())){

						mapDebitosParcelamento.get(debitoACobrar.getParcelamento().getId()).add(debitoACobrar);

					}else{

						Collection<DebitoACobrar> collDebitoACobrar = new ArrayList<DebitoACobrar>();
						collDebitoACobrar.add(debitoACobrar);

						mapDebitosParcelamento.put(debitoACobrar.getParcelamento().getId(), collDebitoACobrar);

					}

				}else{

					novaColecao.add(debitoACobrar);

				}

			}

		}

		for(Integer key : mapDebitosParcelamento.keySet()){

			FiltroCreditoARealizar filtro = new FiltroCreditoARealizar();
			filtro.adicionarParametro(new ParametroSimples(FiltroCreditoARealizar.PARCELAMENTO_ID, key));
			filtro.adicionarParametro(new ParametroSimples(FiltroCreditoARealizar.ID_CREDITO_TIPO,
							CreditoTipo.DESCONTO_ACRESCIMOS_IMPONTUALIDADE));

			Collection<CreditoARealizar> coll = Fachada.getInstancia().pesquisar(filtro, CreditoARealizar.class.getName());

			if(coll.isEmpty()){

				novaColecao.addAll(mapDebitosParcelamento.get(key));

			}

		}

		return novaColecao;

	}




}
