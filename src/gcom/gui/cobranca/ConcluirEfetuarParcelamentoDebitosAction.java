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
 * André Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
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
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.NegociacaoOpcoesParcelamentoHelper;
import gcom.cobranca.bean.OpcoesParcelamentoHelper;
import gcom.cobranca.parcelamento.ParcelamentoConfiguracaoPrestacao;
import gcom.fachada.Fachada;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cobranca.ExecutorParametrosCobranca;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

/**
 * UC0214] Efetuar Parcelamento de Débitos
 * 
 * @author Roberta Costa
 * @date 08/03/2006
 */
public class ConcluirEfetuarParcelamentoDebitosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		HttpSession sessao = httpServletRequest.getSession(false);

		DynaActionForm efetuarParcelamentoDebitosActionForm = (DynaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		// ABA 1 - 6.1 Caso o usuario confirme o parecelamento
		Integer situacaoAguaId = null;
		if(efetuarParcelamentoDebitosActionForm.get("situacaoAguaId") != null
						&& !((String) efetuarParcelamentoDebitosActionForm.get("situacaoAguaId")).trim().equals("")){
			situacaoAguaId = Integer.valueOf((String) (efetuarParcelamentoDebitosActionForm.get("situacaoAguaId")));
		}
		String codigoImovel = (String) efetuarParcelamentoDebitosActionForm.get("matriculaImovel");

		Date dataParcelamento = Util.converteStringParaDate((String) efetuarParcelamentoDebitosActionForm.get("dataParcelamento"));
		String resolucaoDiretoria = (String) efetuarParcelamentoDebitosActionForm.get("resolucaoDiretoria");
		String inicioIntervaloParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("inicioIntervaloParcelamento");
		String fimIntervaloParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("fimIntervaloParcelamento");
		String indicadorRestabelecimento = (String) efetuarParcelamentoDebitosActionForm.get("indicadorRestabelecimento");
		String indicadorContasRevisao = (String) efetuarParcelamentoDebitosActionForm.get("indicadorContasRevisao");
		String indicadorGuiasPagamento = (String) efetuarParcelamentoDebitosActionForm.get("indicadorGuiasPagamento");
		String indicadorAcrescimosImpotualidade = (String) efetuarParcelamentoDebitosActionForm.get("indicadorAcrescimosImpotualidade");
		String indicadorDebitosACobrar = (String) efetuarParcelamentoDebitosActionForm.get("indicadorDebitosACobrar");
		String indicadorCreditoARealizar = (String) efetuarParcelamentoDebitosActionForm.get("indicadorCreditoARealizar");
		String indicadorConfirmacaoParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("indicadorConfirmacaoParcelamento");
		String indicadorCobrancaParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("indicadorCobrancaParcelamento");
		String indicadorParcelamentoCobrancaBancaria = (String) efetuarParcelamentoDebitosActionForm
						.get("indicadorParcelamentoCobrancaBancaria");
		String cpfClienteParcelamentoDigitado = (String) efetuarParcelamentoDebitosActionForm.get("cpfClienteParcelamentoDigitado");
		String indicadorPessoaFisicaJuridica = (String) efetuarParcelamentoDebitosActionForm.get("indicadorPessoaFisicaJuridica");
		// Caso o fim do parcelamento não seja informado
		if(fimIntervaloParcelamento == null || fimIntervaloParcelamento.equals("")){
			fimIntervaloParcelamento = "12/9999";
		}

		// [FS0009] - Verificar preenchimento dos campos
		if(codigoImovel == null || codigoImovel.trim().equals("")){
			throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "Matricula do Imóvel");
		}
		if(dataParcelamento == null || dataParcelamento.equals("")){
			throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "Data do Parcelamento");
		}
		if(resolucaoDiretoria == null || resolucaoDiretoria.trim().equals("")){
			throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "RD do Parcelamento");
		}
		// if(situacaoAguaId.equals(LigacaoAguaSituacao.SUPRIMIDO) ||
		// situacaoAguaId.equals(LigacaoAguaSituacao.SUPR_PARC)
		// || situacaoAguaId.equals(LigacaoAguaSituacao.SUPR_PARC_PEDIDO)){
		// if(indicadorRestabelecimento == null || indicadorRestabelecimento.trim().equals("")){
		// throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null,
		// "Com Restabelecimento?");
		// }
		// }

		Collection collIdsSitLigacaoAguaRestabelecimento;
		try{
			collIdsSitLigacaoAguaRestabelecimento = EfetuarParcelamentoDebitosWizardAction.getSitLigacaoAguaRestabelecimento();
			if(collIdsSitLigacaoAguaRestabelecimento != null && collIdsSitLigacaoAguaRestabelecimento.size() > 0){

				if(collIdsSitLigacaoAguaRestabelecimento.contains(situacaoAguaId)){
					if(indicadorRestabelecimento == null || indicadorRestabelecimento.trim().equals("")){
						throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "Com Restabelecimento?");
					}
				}

			}
		}catch(ControladorException e){
			throw new ActionServletException("atencao.sistemaparametro_inexistente", "P_SIT_LIGACAO_AGUA_RESTABELECIMENTO");
		}


		if(indicadorContasRevisao == null || indicadorContasRevisao.trim().equals("")){
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "Considerar Contas em Revisão?");
		}
		if(indicadorGuiasPagamento == null || indicadorGuiasPagamento.trim().equals("")){
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "Considerar Guias de Pagamento?");
		}
		if(indicadorAcrescimosImpotualidade == null || indicadorAcrescimosImpotualidade.trim().equals("")){
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "Considerar Acréscimos por Impontualidade?");
		}
		if(indicadorDebitosACobrar == null || indicadorDebitosACobrar.trim().equals("")){
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "Considerar Débitos a Cobrar?");
		}

		if(indicadorCreditoARealizar == null || indicadorCreditoARealizar.trim().equals("")){
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "'Considerar Créditos a Realizar?'");
		}

		// ABA 3 - Verifica se foi escolhido alguma opção de parcelamento

		Integer numeroMesesEntreParcelas = null;
		Integer numeroParcelasALancar = null;
		Integer numeroMesesInicioCobranca = null;

		Collection<OpcoesParcelamentoHelper> colecaoOpcoesParcelamento = (Collection<OpcoesParcelamentoHelper>) sessao
						.getAttribute("colecaoOpcoesParcelamento");
		
		Collection<ParcelamentoConfiguracaoPrestacao> colecaoParcelamentoConfiguracaoPrestacao = (Collection<ParcelamentoConfiguracaoPrestacao>) sessao
						.getAttribute("colecaoParcelamentoConfiguracaoPrestacao");

		Short numeroPrestacoes = Short.valueOf("0");
		BigDecimal valorPrestacao = BigDecimal.ZERO;
		BigDecimal valorEntradaMinima = BigDecimal.ZERO;
		BigDecimal taxaJuros = BigDecimal.ZERO;
		BigDecimal percentualDescontoJurosMora = BigDecimal.ZERO;
		BigDecimal percentualDescontoMulta = BigDecimal.ZERO;
		BigDecimal percentualDescontoCorrecaoMonetaria = BigDecimal.ZERO;
		BigDecimal valorDescontoAcrescimosImpontualidadeNaPrestacao = null;
		boolean checarValorRestante = false;

		if(colecaoOpcoesParcelamento != null && !colecaoOpcoesParcelamento.isEmpty()
						&& !Util.isVazioOuBranco(efetuarParcelamentoDebitosActionForm.get("indicadorQuantidadeParcelas"))){

			Iterator opcoesParcelamentoValores = colecaoOpcoesParcelamento.iterator();
			boolean opcaoMarcada = false;
			while(opcoesParcelamentoValores.hasNext()){
				OpcoesParcelamentoHelper opcoesParcelamentoHelper = (OpcoesParcelamentoHelper) opcoesParcelamentoValores.next();
				if(((String) efetuarParcelamentoDebitosActionForm.get("indicadorQuantidadeParcelas")).equals(opcoesParcelamentoHelper
								.getQuantidadePrestacao().toString())){
					opcaoMarcada = true;
					// valorJurosParcelamento = opcoesParcelamentoHelper.getTaxaJuros();
					numeroPrestacoes = opcoesParcelamentoHelper.getQuantidadePrestacao();
					valorPrestacao = opcoesParcelamentoHelper.getValorPrestacao();
					valorEntradaMinima = opcoesParcelamentoHelper.getValorEntradaMinima();
					taxaJuros = opcoesParcelamentoHelper.getTaxaJuros();

					numeroMesesEntreParcelas = opcoesParcelamentoHelper.getNumeroMesesEntreParcelas();
					numeroParcelasALancar = opcoesParcelamentoHelper.getNumeroParcelasALancar();
					numeroMesesInicioCobranca = opcoesParcelamentoHelper.getNumeroMesesInicioCobranca();

					percentualDescontoJurosMora = opcoesParcelamentoHelper.getPercentualDescontoJurosMora();
					percentualDescontoMulta = opcoesParcelamentoHelper.getPercentualDescontoMulta();
					percentualDescontoCorrecaoMonetaria = opcoesParcelamentoHelper.getPercentualDescontoCorrecaoMonetaria();

					valorDescontoAcrescimosImpontualidadeNaPrestacao = opcoesParcelamentoHelper
									.getValorDescontoAcrescimosImpontualidadeNaPrestacao();
				}
			}
			if(!opcaoMarcada && Util.isVazioOrNulo(colecaoParcelamentoConfiguracaoPrestacao)){

				throw new ActionServletException("atencao.pelo.menos.uma.opcao.parcelamento.marcada");
			}else{

				if(!opcaoMarcada){

					checarValorRestante = true;
				}
			}
		}else{

			if(Util.isVazioOrNulo(colecaoParcelamentoConfiguracaoPrestacao)){

				throw new ActionServletException("atencao.pelo.menos.uma.opcao.parcelamento.marcada");
			}else{

				checarValorRestante = true;

				for(ParcelamentoConfiguracaoPrestacao parcelamentoConfiguracaoPrestacao : colecaoParcelamentoConfiguracaoPrestacao){

					numeroPrestacoes = (short) (numeroPrestacoes.shortValue() + parcelamentoConfiguracaoPrestacao.getNumeroPrestacao()
									.shortValue());
				}
			}
		}

		if(checarValorRestante){

			// [FS0047] - Verificar valor restante do parcelamento configurável igual a zero
			if(efetuarParcelamentoDebitosActionForm.get("indicadorCobrancaParcelamento").toString()
							.equals(ConstantesSistema.INDICADOR_COBRANCA_PARC_GUIA_PAGAMENTO)){

				BigDecimal valorRestante = Util.formatarMoedaRealparaBigDecimal(efetuarParcelamentoDebitosActionForm.get("valorRestante")
								.toString());

				if(!(valorRestante.compareTo(BigDecimal.ZERO) == 0)){

					throw new ActionServletException("atencao.valor_restante_configuravel_nao_equivalente");
				}
			}
		}

		NegociacaoOpcoesParcelamentoHelper negociacaoOpcoesParcelamento = (NegociacaoOpcoesParcelamentoHelper) sessao
						.getAttribute("opcoesParcelamento");

		if(negociacaoOpcoesParcelamento == null){
			negociacaoOpcoesParcelamento = new NegociacaoOpcoesParcelamentoHelper();
		}

		// //[FS0012] Verificar existência de parcelamento no mês
		// Collection<Parcelamento> colecaoParcelamento = fachada.verificarParcelamentoMesImovel(new
		// Integer(codigoImovel));
		//
		// if (colecaoParcelamento != null && !colecaoParcelamento.isEmpty()) {
		// throw new
		// ActionServletException("atencao.debito.ja.parcelado.mes.faturamento.corrente",null);
		// }

		// Pega os dados do formulário
		BigDecimal valorEntradaInformado = valorEntradaMinima;
		// if(efetuarParcelamentoDebitosActionForm.get("valorEntradaInformado") != null
		// &&
		// !efetuarParcelamentoDebitosActionForm.get("valorEntradaInformado").toString().trim().equals("")){
		// valorEntradaInformado = Util.formatarMoedaRealparaBigDecimal((String)
		// (efetuarParcelamentoDebitosActionForm
		// .get("valorEntradaInformado")));
		// }

		Date dataEntradaParcelamento = null;
		if(efetuarParcelamentoDebitosActionForm.get("dataVencimentoEntradaParcelamento") != null
						&& !efetuarParcelamentoDebitosActionForm.get("dataVencimentoEntradaParcelamento").equals("")){
			dataEntradaParcelamento = Util.converteStringParaDate((String) efetuarParcelamentoDebitosActionForm
							.get("dataVencimentoEntradaParcelamento"));
		}

		BigDecimal valorTotalContaValores = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorTotalContaValores") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorTotalContaValores").equals("")){
			valorTotalContaValores = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorTotalContaValores")));
		}
		BigDecimal valorGuiasPagamento = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorGuiasPagamento") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorGuiasPagamento").equals("")){
			valorGuiasPagamento = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorGuiasPagamento")));
		}

		BigDecimal valorDebitoACobrarServico = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarServico") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarServico").equals("")){
			valorDebitoACobrarServico = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorDebitoACobrarServico")));
		}

		BigDecimal valorDebitoACobrarParcelamento = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarParcelamento") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarParcelamento").equals("")){
			valorDebitoACobrarParcelamento = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorDebitoACobrarParcelamento")));
		}

		BigDecimal valorCreditoARealizar = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorCreditoARealizar") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorCreditoARealizar").equals("")){
			valorCreditoARealizar = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorCreditoARealizar")));
		}

		BigDecimal valorAtualizacaoMonetaria = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorAtualizacaoMonetaria") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorAtualizacaoMonetaria").equals("")){
			valorAtualizacaoMonetaria = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorAtualizacaoMonetaria")));
		}

		BigDecimal valorJurosMora = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorJurosMora") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorJurosMora").equals("")){
			valorJurosMora = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm.get("valorJurosMora")));
		}

		BigDecimal valorMulta = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorMulta") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorMulta").equals("")){
			valorMulta = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm.get("valorMulta")));
		}

		BigDecimal valorDebitoTotalAtualizado = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorDebitoTotalAtualizado") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorDebitoTotalAtualizado").equals("")){
			valorDebitoTotalAtualizado = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorDebitoTotalAtualizado")));
		}

		BigDecimal descontoAcrescimosImpontualidade = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("descontoAcrescimosImpontualidade") != null
						&& !efetuarParcelamentoDebitosActionForm.get("descontoAcrescimosImpontualidade").equals("")){
			descontoAcrescimosImpontualidade = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("descontoAcrescimosImpontualidade")));
		}

		BigDecimal descontoSancoesRDEspecial = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("descontoSancoesRDEspecial") != null
						&& !efetuarParcelamentoDebitosActionForm.get("descontoSancoesRDEspecial").equals("")){
			descontoSancoesRDEspecial = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("descontoSancoesRDEspecial")));
		}

		BigDecimal descontoTarifaSocialRDEspecial = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("descontoTarifaSocialRDEspecial") != null
						&& !efetuarParcelamentoDebitosActionForm.get("descontoTarifaSocialRDEspecial").equals("")){
			descontoTarifaSocialRDEspecial = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("descontoTarifaSocialRDEspecial")));
		}

		BigDecimal descontoAntiguidadeDebito = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("descontoAntiguidadeDebito") != null
						&& !efetuarParcelamentoDebitosActionForm.get("descontoAntiguidadeDebito").equals("")){
			descontoAntiguidadeDebito = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("descontoAntiguidadeDebito")));
		}

		BigDecimal descontoInatividadeLigacaoAgua = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("descontoInatividadeLigacaoAgua") != null
						&& !efetuarParcelamentoDebitosActionForm.get("descontoInatividadeLigacaoAgua").equals("")){
			descontoInatividadeLigacaoAgua = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("descontoInatividadeLigacaoAgua")));
		}

		BigDecimal percentualDescontoAcrescimosImpontualidade = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("percentualDescontoAcrescimosImpontualidade") != null
						&& !efetuarParcelamentoDebitosActionForm.get("percentualDescontoAcrescimosImpontualidade").equals("")){
			percentualDescontoAcrescimosImpontualidade = Util
							.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
											.get("percentualDescontoAcrescimosImpontualidade")));
		}

		BigDecimal percentualDescontoAntiguidadeDebito = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("percentualDescontoAntiguidadeDebito") != null
						&& !efetuarParcelamentoDebitosActionForm.get("percentualDescontoAntiguidadeDebito").equals("")){
			percentualDescontoAntiguidadeDebito = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("percentualDescontoAntiguidadeDebito")));
		}

		BigDecimal percentualDescontoInatividadeLigacaoAgua = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("percentualDescontoInatividadeLigacaoAgua") != null
						&& !efetuarParcelamentoDebitosActionForm.get("percentualDescontoInatividadeLigacaoAgua").equals("")){
			percentualDescontoInatividadeLigacaoAgua = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("percentualDescontoInatividadeLigacaoAgua")));
		}

		BigDecimal valorAcrescimosImpontualidade = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorAcrescimosImpontualidade") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorAcrescimosImpontualidade").equals("")){
			valorAcrescimosImpontualidade = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorAcrescimosImpontualidade")));
		}

		Integer parcelamentoPerfilId = Integer.valueOf((String) (efetuarParcelamentoDebitosActionForm.get("parcelamentoPerfilId")));

		BigDecimal valorDebitoACobrarServicoLongoPrazo = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarServicoLongoPrazo") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarServicoLongoPrazo").equals("")){
			valorDebitoACobrarServicoLongoPrazo = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorDebitoACobrarServicoLongoPrazo")));
		}

		BigDecimal valorDebitoACobrarServicoCurtoPrazo = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarServicoCurtoPrazo") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarServicoCurtoPrazo").equals("")){
			valorDebitoACobrarServicoCurtoPrazo = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorDebitoACobrarServicoCurtoPrazo")));
		}

		BigDecimal valorDebitoACobrarParcelamentoLongoPrazo = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarParcelamentoLongoPrazo") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarParcelamentoLongoPrazo").equals("")){
			valorDebitoACobrarParcelamentoLongoPrazo = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorDebitoACobrarParcelamentoLongoPrazo")));
		}

		BigDecimal valorDebitoACobrarParcelamentoCurtoPrazo = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarParcelamentoCurtoPrazo") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarParcelamentoCurtoPrazo").equals("")){
			valorDebitoACobrarParcelamentoCurtoPrazo = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorDebitoACobrarParcelamentoCurtoPrazo")));
		}

		// Valor a ser Negociado
		BigDecimal valorASerParcelado = BigDecimal.ZERO;
		BigDecimal valorFinalFinanciamento = BigDecimal.ZERO;
		BigDecimal valorASerNegociado = BigDecimal.ZERO;
		boolean existeValor = false;
		if(efetuarParcelamentoDebitosActionForm.get("valorFinalFinanciamento") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorFinalFinanciamento").equals("")){
			valorFinalFinanciamento = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorFinalFinanciamento")));
			existeValor = true;
		}
		if(efetuarParcelamentoDebitosActionForm.get("valorFinalFinanciamento") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorFinalFinanciamento").equals("")){
			valorASerNegociado = Util
							.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm.get("valorNegociado")));
			existeValor = true;
		}
		if(!existeValor){
			if(colecaoOpcoesParcelamento != null && !colecaoOpcoesParcelamento.isEmpty()){
				Iterator opcoesParcelamentoValores = colecaoOpcoesParcelamento.iterator();
				while(opcoesParcelamentoValores.hasNext()){
					OpcoesParcelamentoHelper opcoesParcelamento = (OpcoesParcelamentoHelper) opcoesParcelamentoValores.next();
					if(!Util.isVazioOuBranco(efetuarParcelamentoDebitosActionForm.get("indicadorQuantidadeParcelas"))
									&& ((String) efetuarParcelamentoDebitosActionForm.get("indicadorQuantidadeParcelas"))
													.equals(opcoesParcelamento
									.getQuantidadePrestacao().toString())){
						valorFinalFinanciamento = opcoesParcelamento.getValorPrestacao().multiply(
										new BigDecimal(opcoesParcelamento.getQuantidadePrestacao()));

					}
				}
			}

			BigDecimal valorDesconto = BigDecimal.ZERO;

			if(valorDescontoAcrescimosImpontualidadeNaPrestacao != null){
				valorDesconto = valorDesconto.add(valorDescontoAcrescimosImpontualidadeNaPrestacao);
			}else{
				valorDesconto = valorDesconto.add(descontoAcrescimosImpontualidade);
			}

			valorDesconto = valorDesconto.add(descontoAntiguidadeDebito);
			valorDesconto = valorDesconto.add(descontoInatividadeLigacaoAgua);

			efetuarParcelamentoDebitosActionForm.set("valorDesconto", Util.formatarMoedaReal(valorDesconto));

			valorASerNegociado = valorDebitoTotalAtualizado.subtract(valorDesconto);

			valorASerParcelado = valorDebitoTotalAtualizado.subtract(valorEntradaMinima.add(valorDesconto));

		}

		// Montar o objeto Imovel para as inserções no banco
		Imovel imovel = null;

		FiltroImovel filtroImovel = new FiltroImovel();

		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, codigoImovel));
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.CONSUMO_TARIFA);

		Collection<Imovel> imovelPesquisado = fachada.pesquisar(filtroImovel, Imovel.class.getName());

		if(imovelPesquisado != null && !imovelPesquisado.isEmpty()){
			imovel = imovelPesquisado.iterator().next();
		}

		Collection<ContaValoresHelper> colecaoContaValores = new ArrayList();
		if(sessao.getAttribute("colecaoContaValores") != null){
			colecaoContaValores = (Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValores");
		}else{
			colecaoContaValores = (Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValoresImovel");
		}

		Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores = new ArrayList();
		if(sessao.getAttribute("colecaoGuiaPagamentoValoresSelecionadas") != null || indicadorGuiasPagamento.equals("2")){
			colecaoGuiaPagamentoValores = (Collection<GuiaPagamentoValoresHelper>) sessao
							.getAttribute("colecaoGuiaPagamentoValoresSelecionadas");
		}else{
			colecaoGuiaPagamentoValores = (Collection<GuiaPagamentoValoresHelper>) sessao.getAttribute("colecaoGuiaPagamentoValoresImovel");
		}

		Collection<DebitoACobrar> colecaoDebitoACobrar = new ArrayList();
		if(sessao.getAttribute("colecaoDebitoACobrar") != null || indicadorDebitosACobrar.equals("2")){
			colecaoDebitoACobrar = (Collection<DebitoACobrar>) sessao.getAttribute("colecaoDebitoACobrar");
		}else{
			colecaoDebitoACobrar = (Collection<DebitoACobrar>) sessao.getAttribute("colecaoDebitoACobrarImovel");
		}

		Collection<CreditoARealizar> colecaoCreditoARealizar = new ArrayList();
		if(sessao.getAttribute("colecaoCreditoARealizar") != null || indicadorCreditoARealizar.equals("2")){
			colecaoCreditoARealizar = (Collection<CreditoARealizar>) sessao.getAttribute("colecaoCreditoARealizar");
		}else{
			colecaoCreditoARealizar = (Collection<CreditoARealizar>) sessao.getAttribute("colecaoCreditoARealizarImovel");
		}

		Cliente cliente = new Cliente();
		Integer idCliente = 0;
		// Id do cliente responsável pelo parcelamento, se tiver sido informado
		// caso contrário Id do Cliente usuário do imóvel
		if(efetuarParcelamentoDebitosActionForm.get("idClienteParcelamento") != null
						&& !efetuarParcelamentoDebitosActionForm.get("idClienteParcelamento").equals("")){
			idCliente = Integer.valueOf((String) efetuarParcelamentoDebitosActionForm.get("idClienteParcelamento"));
		}else{
			idCliente = (Integer) sessao.getAttribute("idClienteImovel");
		}
		cliente.setId(idCliente);

		Integer anoMesReferenciaDebitoInicial = null;

		if(!Util.isVazioOuBranco(inicioIntervaloParcelamento)){
			String anoMesReferenciaDebitoInicialStr = Util.formatarMesAnoParaAnoMesSemBarra(inicioIntervaloParcelamento);
			anoMesReferenciaDebitoInicial = Integer.valueOf(anoMesReferenciaDebitoInicialStr);
		}

		Integer anoMesReferenciaDebitoFinal = null;

		if(!Util.isVazioOuBranco(inicioIntervaloParcelamento)){
			String anoMesReferenciaDebitoFinalStr = Util.formatarMesAnoParaAnoMesSemBarra(fimIntervaloParcelamento);
			anoMesReferenciaDebitoFinal = Integer.valueOf(anoMesReferenciaDebitoFinalStr);
		}

		Integer idParcelamento = fachada.concluirParcelamentoDebitos(colecaoContaValores, colecaoGuiaPagamentoValores,
						colecaoDebitoACobrar, colecaoCreditoARealizar, indicadorRestabelecimento, indicadorContasRevisao,
						indicadorGuiasPagamento, indicadorAcrescimosImpotualidade, indicadorDebitosACobrar, indicadorCreditoARealizar,
						imovel, valorEntradaInformado, valorASerNegociado, valorASerParcelado, dataParcelamento, valorTotalContaValores,
						valorGuiasPagamento, valorDebitoACobrarServico, valorDebitoACobrarParcelamento, valorCreditoARealizar,
						valorAtualizacaoMonetaria, valorJurosMora, valorMulta, valorDebitoTotalAtualizado,
						descontoAcrescimosImpontualidade, descontoAntiguidadeDebito, descontoInatividadeLigacaoAgua,
						percentualDescontoAcrescimosImpontualidade, percentualDescontoAntiguidadeDebito,
						percentualDescontoInatividadeLigacaoAgua, parcelamentoPerfilId, valorAcrescimosImpontualidade,
						valorDebitoACobrarServicoLongoPrazo, valorDebitoACobrarServicoCurtoPrazo, valorDebitoACobrarParcelamentoLongoPrazo,
						valorDebitoACobrarParcelamentoCurtoPrazo, numeroPrestacoes, valorPrestacao, valorEntradaMinima, taxaJuros,
						indicadorConfirmacaoParcelamento, cliente, usuarioLogado, cpfClienteParcelamentoDigitado,
						descontoSancoesRDEspecial, descontoTarifaSocialRDEspecial, dataEntradaParcelamento, numeroMesesEntreParcelas,
						numeroParcelasALancar, numeroMesesInicioCobranca, (Integer) sessao.getAttribute("idBoletoBancario"),
						indicadorCobrancaParcelamento, indicadorParcelamentoCobrancaBancaria, negociacaoOpcoesParcelamento
										.getNumeroDiasVencimentoEntrada(), indicadorPessoaFisicaJuridica, anoMesReferenciaDebitoInicial,
						anoMesReferenciaDebitoFinal, percentualDescontoJurosMora, percentualDescontoMulta,
						percentualDescontoCorrecaoMonetaria, valorDescontoAcrescimosImpontualidadeNaPrestacao, valorFinalFinanciamento,
						colecaoParcelamentoConfiguracaoPrestacao);

		sessao.setAttribute("idParcelamento", idParcelamento.toString());
		sessao.removeAttribute("idBoletoBancario");

		// [FS0013] - Verificar sucesso da transação
		// Monta a página de sucesso
		Collection idsContaEP = (Collection) sessao.getAttribute("idsContaEP");

		if(idsContaEP != null && !idsContaEP.isEmpty()){
			/*
			 * montarPaginaSucesso(httpServletRequest, "Parcelamento de Débitos do Imóvel " +
			 * codigoImovel + " efetuado com sucesso.",
			 * "Efetuar outro Parcelamento de Débitos",
			 * "exibirEfetuarParcelamentoDebitosAction.do?menu=sim",
			 * "gerarRelatorioParcelamentoAction.do", "Imprimir Termo", "Imprimir Contas EP",
			 * "gerarRelatorio2ViaContaAction.do");
			 */

			montarPaginaSucesso(httpServletRequest, "Parcelamento de Débitos do Imóvel " + codigoImovel + " efetuado com sucesso.",
							"Efetuar outro Parcelamento de Débitos", "exibirEfetuarParcelamentoDebitosAction.do?menu=sim",
							"gerarRelatorioParcelamentoResolucaoDiretoriaLayoutAction.do?indicadorParcelamentoCobrancaBancaria="
											+ indicadorParcelamentoCobrancaBancaria, "Imprimir Termo", "Imprimir Contas EP",
							"gerarRelatorio2ViaContaAction.do?idNomeRelatorio=2");

		}else{

			FiltroGuiaPagamento filtroGuiaPagamento = new FiltroGuiaPagamento();
			filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.PARCELAMENTO_ID, Integer
							.valueOf(idParcelamento)));

			Collection collectionGuiaPagamento = fachada.pesquisar(filtroGuiaPagamento, GuiaPagamento.class.getName());

			String idGuiaPagamento = "";
			if(collectionGuiaPagamento != null && !collectionGuiaPagamento.isEmpty()){
				GuiaPagamento guiaPagamento = (GuiaPagamento) Util.retonarObjetoDeColecao(collectionGuiaPagamento);
				idGuiaPagamento = "" + guiaPagamento.getId();

				if(retorno.getName().equalsIgnoreCase("telaSucesso")){
					/*
					 * montarPaginaSucesso(httpServletRequest, "Parcelamento de Débitos do Imóvel "
					 * + codigoImovel + " efetuado com sucesso.",
					 * "Efetuar outro Parcelamento de Débitos",
					 * "exibirEfetuarParcelamentoDebitosAction.do?menu=sim",
					 * "gerarRelatorioParcelamentoAction.do", "Imprimir Termo",
					 * "Imprimir Guia Pagto Entrada",
					 * "gerarRelatorioEmitirGuiaPagamentoActionInserir.do?idGuiaPagamento=" +
					 * idGuiaPagamento);
					 */

					// montarPaginaSucesso(httpServletRequest, "Parcelamento de Débitos do Imóvel "
					// + codigoImovel + " efetuado com sucesso.",
					// "Efetuar outro Parcelamento de Débitos",
					// "exibirEfetuarParcelamentoDebitosAction.do?menu=sim",
					// "gerarRelatorioParcelamentoResolucaoDiretoriaLayoutAction.do?indicadorParcelamentoCobrancaBancaria="
					// + indicadorParcelamentoCobrancaBancaria, "Imprimir Termo",
					// "Imprimir Guia Pagto Entrada",
					// "gerarRelatorioEmitirGuiaPagamentoActionInserir.do?idGuiaPagamento="
					// + idGuiaPagamento);

					montarPaginaSucesso(httpServletRequest, "Parcelamento de Débitos do Imóvel " + codigoImovel + " efetuado com sucesso.",
									"Efetuar outro Parcelamento de Débitos", "exibirEfetuarParcelamentoDebitosAction.do?menu=sim",
									"gerarRelatorioParcelamentoResolucaoDiretoriaLayoutAction.do?indicadorParcelamentoCobrancaBancaria="
													+ indicadorParcelamentoCobrancaBancaria, "Imprimir Termo",
									"Imprimir Guia Pagto Entrada",
									"gerarRelatorioEmitirGuiaPagamentoActionParcelamento.do?idGuiaPagamento=" + idGuiaPagamento);

				}

			}else if(retorno.getName().equalsIgnoreCase("telaSucesso")){

				/*
				 * montarPaginaSucesso(httpServletRequest, "Parcelamento de Débitos do Imóvel " +
				 * codigoImovel + " efetuado com sucesso.",
				 * "Efetuar outro Parcelamento de Débitos",
				 * "exibirEfetuarParcelamentoDebitosAction.do?menu=sim",
				 * "gerarRelatorioParcelamentoAction.do", "Imprimir Termo");
				 */

				montarPaginaSucesso(httpServletRequest, "Parcelamento de Débitos do Imóvel " + codigoImovel + " efetuado com sucesso.",
								"Efetuar outro Parcelamento de Débitos", "exibirEfetuarParcelamentoDebitosAction.do?menu=sim",
								"gerarRelatorioParcelamentoResolucaoDiretoriaLayoutAction.do?indicadorParcelamentoCobrancaBancaria="
												+ indicadorParcelamentoCobrancaBancaria, "Imprimir Termo");
			}
		}

		return retorno;
	}

	public static boolean isCobrancaBancaria(String idMotivoRevisao){

		String[] idsMotivoRevisao = null;
		try{
			idsMotivoRevisao = ((String) ParametroCobranca.P_MOTIVO_REVISAO_COBRANCA_BANCARIA.executar(ExecutorParametrosCobranca
							.getInstancia())).split(",");
		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(idsMotivoRevisao != null){

			if(idMotivoRevisao != null){
				for(int i = 0; i < idsMotivoRevisao.length; i++){
					if(idsMotivoRevisao[i].equals(idMotivoRevisao)){
						return true;
					}
				}
			}

		}
		return false;
	}



}