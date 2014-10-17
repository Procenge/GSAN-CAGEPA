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

import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.imovel.ImovelSituacaoTipo;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cobranca.FiltroResolucaoDiretoria;
import gcom.cobranca.ResolucaoDiretoria;
import gcom.cobranca.parcelamento.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action responsável por inserir um Perfil de Parcelamento
 * 
 * @author Vivianne Sousa
 * @created 02/05/2006
 */

public class InserirPerfilParcelamentoAction
				extends GcomAction {

	/**
	 * Este caso de uso permite a inclusão de um novo perfil de parcelamento
	 * [UC0220] Inserir Perfil de Parcelamento
	 * 
	 * @author Vivianne Sousa
	 * @date 02/05/2006
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	// Obtém a instância da fachada
	Fachada fachada = Fachada.getInstancia();

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		HttpSession sessao = httpServletRequest.getSession(false);
		ParcelamentoPerfilActionForm parcelamentoPerfilActionForm = (ParcelamentoPerfilActionForm) actionForm;

		String idResolucaoDiretoria = parcelamentoPerfilActionForm.getResolucaoDiretoria();
		String idImovelSituacaoTipo = parcelamentoPerfilActionForm.getImovelSituacaoTipo();

		String idImovelPerfil = null;
		if(parcelamentoPerfilActionForm.getImovelPerfil() != null
						&& !parcelamentoPerfilActionForm.getImovelPerfil().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			idImovelPerfil = parcelamentoPerfilActionForm.getImovelPerfil();
		}

		String idSubcategoria = null;
		if(parcelamentoPerfilActionForm.getSubcategoria() != null
						&& !parcelamentoPerfilActionForm.getSubcategoria().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			idSubcategoria = parcelamentoPerfilActionForm.getSubcategoria();
		}

		String percentualDescontoAcrescimo = null;
		if(parcelamentoPerfilActionForm.getPercentualDescontoAcrescimo() != null
						&& !parcelamentoPerfilActionForm.getPercentualDescontoAcrescimo().equalsIgnoreCase("")){
			percentualDescontoAcrescimo = parcelamentoPerfilActionForm.getPercentualDescontoAcrescimo().toString().replace(",", ".");
		}

		// collectionParcelamentoQuantidadeReparcelamentoHelper
		Collection collectionParcelamentoQuantidadeReparcelamentoHelper = null;
		if(sessao.getAttribute("collectionParcelamentoQuantidadeReparcelamentoHelper") != null){
			collectionParcelamentoQuantidadeReparcelamentoHelper = (Collection) sessao
							.getAttribute("collectionParcelamentoQuantidadeReparcelamentoHelper");
		}

		// collectionParcelamentoDescontoInatividade
		Collection collectionParcelamentoDescontoInatividade = null;
		if(sessao.getAttribute("collectionParcelamentoDescontoInatividade") != null){
			collectionParcelamentoDescontoInatividade = (Collection) sessao.getAttribute("collectionParcelamentoDescontoInatividade");
		}

		// collectionParcelamentoDescontoAntiguidade
		Collection collectionParcelamentoDescontoAntiguidade = null;
		if(sessao.getAttribute("collectionParcelamentoDescontoAntiguidade") != null){
			collectionParcelamentoDescontoAntiguidade = (Collection) sessao.getAttribute("collectionParcelamentoDescontoAntiguidade");
		}

		BigDecimal valorMinimoDebitoAParcelarFaixaDebitoBigDecimal = BigDecimal.ZERO;
		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getValorMinimoDebitoAParcelarFaixaDebito())){
			valorMinimoDebitoAParcelarFaixaDebitoBigDecimal = Util.formatarMoedaRealparaBigDecimal(parcelamentoPerfilActionForm
							.getValorMinimoDebitoAParcelarFaixaDebito(), 2);
		}else{
			httpServletRequest.setAttribute("nomeCampo", "valorMinimoDebitoAParcelarFaixaDebito");
			throw new ActionServletException("atencao.required", null, "Valor Mínimo de Débito a Parcelar");
		}

		BigDecimal valorMaximoDebitoAParcelarFaixaDebitoBigDecimal = BigDecimal.ZERO;
		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getValorMaximoDebitoAParcelarFaixaDebito())){
			valorMaximoDebitoAParcelarFaixaDebitoBigDecimal = Util.formatarMoedaRealparaBigDecimal(parcelamentoPerfilActionForm
							.getValorMaximoDebitoAParcelarFaixaDebito(), 2);
		}else{
			httpServletRequest.setAttribute("nomeCampo", "valorMaximoDebitoAParcelarFaixaDebito");
			throw new ActionServletException("atencao.required", null, "Valor Máximo de Débito a Parcelar");
		}

		if(valorMinimoDebitoAParcelarFaixaDebitoBigDecimal.compareTo(new BigDecimal("999999999.99")) > 0
						|| valorMinimoDebitoAParcelarFaixaDebitoBigDecimal.compareTo(BigDecimal.ZERO) < 0){
			throw new ActionServletException("atencao.valor.minimo.debito.parcelar.dever.maior.igual.0.e.nao.maior.que.999_999_999.99");
		}else if(valorMaximoDebitoAParcelarFaixaDebitoBigDecimal.compareTo(new BigDecimal("999999999.99")) > 0
						|| valorMaximoDebitoAParcelarFaixaDebitoBigDecimal.compareTo(BigDecimal.ZERO) < 0){
			throw new ActionServletException("atencao.valor.maximo.debito.parcelar.dever.maior.igual.0.e.nao.maior.que.999_999_999.99");
		}else if(valorMinimoDebitoAParcelarFaixaDebitoBigDecimal.compareTo(valorMaximoDebitoAParcelarFaixaDebitoBigDecimal) == 0){
			throw new ActionServletException("atencao.valor.minimo.e.maximo.debito.parcelar.nao.podem.ser.iguais");
		}else if(valorMinimoDebitoAParcelarFaixaDebitoBigDecimal.compareTo(valorMaximoDebitoAParcelarFaixaDebitoBigDecimal) > 0){
			throw new ActionServletException("atencao.valor.minimo.nao.pode.ser.maior.valor.maximo.debito.parcelar");
		}

		Short indicadorDebitoOriginalOuAtualizadoFaixaDebitoShort = null;
		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getIndicadorDebitoOriginalOuAtualizadoFaixaDebito())){
			indicadorDebitoOriginalOuAtualizadoFaixaDebitoShort = Short.valueOf(parcelamentoPerfilActionForm
							.getIndicadorDebitoOriginalOuAtualizadoFaixaDebito().toString());
		}else{
			httpServletRequest.setAttribute("nomeCampo", "indicadorDebitoOriginalOuAtualizadoFaixaDebito");
			throw new ActionServletException("atencao.required", null, "Opções da Faixa de Débito a Parcelar");
		}

		validarDadosParcelamentoPerfil(parcelamentoPerfilActionForm, httpServletRequest);

		atualizaColecoesNaSessao(sessao, httpServletRequest);

		validacaoFinal(idResolucaoDiretoria, idImovelSituacaoTipo, idImovelPerfil, idSubcategoria, percentualDescontoAcrescimo,
						httpServletRequest, collectionParcelamentoQuantidadeReparcelamentoHelper,
						collectionParcelamentoDescontoAntiguidade, collectionParcelamentoDescontoInatividade,
						valorMinimoDebitoAParcelarFaixaDebitoBigDecimal, valorMaximoDebitoAParcelarFaixaDebitoBigDecimal,
						parcelamentoPerfilActionForm);

		ParcelamentoPerfil parcelamentoPerfilNova = new ParcelamentoPerfil();

		ResolucaoDiretoria resolucaoDiretoria = null;
		if(idResolucaoDiretoria != null && !idResolucaoDiretoria.equals("")){
			resolucaoDiretoria = new ResolucaoDiretoria();
			resolucaoDiretoria.setId(new Integer(idResolucaoDiretoria));
			parcelamentoPerfilNova.setResolucaoDiretoria(resolucaoDiretoria);
		}

		ImovelSituacaoTipo imovelSituacaoTipo = null;
		if(idImovelSituacaoTipo != null && !idImovelSituacaoTipo.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			imovelSituacaoTipo = new ImovelSituacaoTipo();
			imovelSituacaoTipo.setId(new Integer(idImovelSituacaoTipo));
			parcelamentoPerfilNova.setImovelSituacaoTipo(imovelSituacaoTipo);
		}

		ImovelPerfil imovelPerfil = null;
		if(idImovelPerfil != null && !idImovelPerfil.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			imovelPerfil = new ImovelPerfil();
			imovelPerfil.setId(new Integer(idImovelPerfil));
		}
		parcelamentoPerfilNova.setImovelPerfil(imovelPerfil);

		Subcategoria subcategoria = null;
		if(idSubcategoria != null && !idSubcategoria.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			subcategoria = new Subcategoria();
			subcategoria.setId(new Integer(idSubcategoria));
		}
		parcelamentoPerfilNova.setSubcategoria(subcategoria);

		if(parcelamentoPerfilActionForm.getPercentualDescontoAcrescimo() != null
						&& !parcelamentoPerfilActionForm.getPercentualDescontoAcrescimo().equalsIgnoreCase("")){
			parcelamentoPerfilNova.setPercentualDescontoAcrescimo(new BigDecimal(percentualDescontoAcrescimo));
		}else{
			parcelamentoPerfilNova.setPercentualDescontoAcrescimo(new BigDecimal(0));
		}

		parcelamentoPerfilNova.setPercentualTarifaMinimaPrestacao(Util.formatarStringMoedaRealparaBigDecimal(parcelamentoPerfilActionForm
						.getPercentualTarifaMinimaPrestacao(), 2));
		parcelamentoPerfilNova.setValorDebitoPrestacao(Util.formatarStringMoedaRealparaBigDecimal(parcelamentoPerfilActionForm
						.getValorDebitoPrestacao(), 2));

		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getIndicadorParcelarChequeDevolvido())){
			parcelamentoPerfilNova
							.setIndicadorChequeDevolvido(new Short(parcelamentoPerfilActionForm.getIndicadorParcelarChequeDevolvido()));
		}else{
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "Não parcelar com cheque devolvido");
		}

		// Cobrar acréscimos por impontualidades (Sim ou Não)
		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getIndicadorCobrarAcrescimosImpontualidade())){

			parcelamentoPerfilNova.setIndicadorCobrarAcrescimosPorImpontualidades(Util.obterShort(parcelamentoPerfilActionForm
							.getIndicadorCobrarAcrescimosImpontualidade()));
		}

		// Permitir a cobrança do parcelamento através de Guia de Pagamento (Sim ou Não)
		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getIndicadorPermitirCobrancaParcelamentoPorGuia())){

			parcelamentoPerfilNova.setIndicadorPermiteParcelamentoPorGuiaPagamento(Util.obterShort(parcelamentoPerfilActionForm
							.getIndicadorPermitirCobrancaParcelamentoPorGuia()));

			// Permitir a cobrança do parcelamento através de Guia de Pagamento (Sim ou Não)
			if(parcelamentoPerfilNova.getIndicadorPermiteParcelamentoPorGuiaPagamento().equals(ConstantesSistema.SIM)
							&& !Util.isVazioOuBranco(parcelamentoPerfilActionForm.getIndicadorPermitirInformarNumeroValorParcelas())){

				parcelamentoPerfilNova.setIndicadorPermiteInformarNumeroValorParcela(Util.obterShort(parcelamentoPerfilActionForm
								.getIndicadorPermitirInformarNumeroValorParcelas()));
			}else{

				parcelamentoPerfilNova.setIndicadorPermiteInformarNumeroValorParcela(ConstantesSistema.NAO);
			}
		}

		// Cobrança de multa em caso de descumprimento de prestações (Sim ou Não)
		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getIndicadorCobrarMultaPorDescumprimentoPrestacao())
						&& Util.obterShort(parcelamentoPerfilActionForm.getIndicadorCobrarMultaPorDescumprimentoPrestacao()).shortValue() == ConstantesSistema.SIM
										.shortValue()){

			// Número de prestações descumpridas consecutivas para cobrança de multa
			parcelamentoPerfilNova.setNumeroPretacoesDescumpridasParaCobrancaMulta(Util.obterInteger(parcelamentoPerfilActionForm
							.getNumeroPrestacacoesDescumpridasConsecutivas()));

			// Percentual de multa
			parcelamentoPerfilNova.setPercentualMultaPrestacaoDescumprida(Util.formatarStringMoedaRealparaBigDecimal(
							parcelamentoPerfilActionForm.getPercentualMultaPorPrestacacoesDescumpridas(), 2));
		}

		if(parcelamentoPerfilActionForm.getConsumoMinimo() != null && !parcelamentoPerfilActionForm.getConsumoMinimo().equals("")){
			parcelamentoPerfilNova.setNumeroConsumoMinimo(new Integer(parcelamentoPerfilActionForm.getConsumoMinimo()));
		}

		if(parcelamentoPerfilActionForm.getPercentualVariacaoConsumoMedio() != null
						&& !parcelamentoPerfilActionForm.getPercentualVariacaoConsumoMedio().equals("")){
			parcelamentoPerfilNova.setPercentualVariacaoConsumoMedio(Util.formatarMoedaRealparaBigDecimal(parcelamentoPerfilActionForm
							.getPercentualVariacaoConsumoMedio()));
		}

		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getIndicadorParcelarSancoesMaisDeUmaConta())){
			parcelamentoPerfilNova.setIndicadorSancoesUnicaConta(new Short(parcelamentoPerfilActionForm
							.getIndicadorParcelarSancoesMaisDeUmaConta()));
		}else{
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "Não parcelar com sanções em mais de uma conta");
		}

		parcelamentoPerfilNova.setIndicadorRetroativoTarifaSocial(ConstantesSistema.NAO);
		parcelamentoPerfilNova.setIndicadorAlertaParcelaMinima(ConstantesSistema.NAO);

		parcelamentoPerfilNova.setValorMinimoDebitoAParcelarFaixaDebito(valorMinimoDebitoAParcelarFaixaDebitoBigDecimal);
		parcelamentoPerfilNova.setValorMaximoDebitoAParcelarFaixaDebito(valorMaximoDebitoAParcelarFaixaDebitoBigDecimal);
		parcelamentoPerfilNova.setIndicadorDebitoOriginalOuAtualizadoFaixaDebito(indicadorDebitoOriginalOuAtualizadoFaixaDebitoShort);

		Short indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinimaShort = null;
		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getIndicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima())){
			indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinimaShort = Short.valueOf(parcelamentoPerfilActionForm
							.getIndicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima());
		}
		parcelamentoPerfilNova
						.setIndicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima(indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinimaShort);

		parcelamentoPerfilNova.setPercentualValorDebitoCalculoValorMinimoPrestacao(Util.formatarStringMoedaRealparaBigDecimal(
						parcelamentoPerfilActionForm.getPercentualValorDebitoCalculoValorMinimoPrestacao(), 2));

		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getNumeroConsumoEconomia())){
			parcelamentoPerfilNova.setNumeroConsumoEconomia(Integer.valueOf(parcelamentoPerfilActionForm.getNumeroConsumoEconomia()));
		}

		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getNumeroAreaConstruida())){
			parcelamentoPerfilNova.setNumeroAreaConstruida(Util.formatarStringParaBigDecimal(parcelamentoPerfilActionForm
							.getNumeroAreaConstruida()));
		}
		
		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getAnoMesReferenciaLimiteInferior())){
			parcelamentoPerfilNova.setAnoMesReferenciaLimiteInferior(Util.formatarMesAnoComBarraParaAnoMes(parcelamentoPerfilActionForm
							.getAnoMesReferenciaLimiteInferior()));
		}

		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getAnoMesReferenciaLimiteSuperior())){
			parcelamentoPerfilNova.setAnoMesReferenciaLimiteSuperior(Util.formatarMesAnoComBarraParaAnoMes(parcelamentoPerfilActionForm
							.getAnoMesReferenciaLimiteSuperior()));
		}

		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getPercentualDescontoAVista())){
			parcelamentoPerfilNova.setPercentualDescontoAVista(Util.formatarMoedaRealparaBigDecimal(
							parcelamentoPerfilActionForm.getPercentualDescontoAVista(), 2));
		}

		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getParcelaQuantidadeMinimaFatura())){
			parcelamentoPerfilNova.setParcelaQuantidadeMinimaFatura(Integer.valueOf(parcelamentoPerfilActionForm
							.getParcelaQuantidadeMinimaFatura()));
		}

		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getPercentualDescontoSancao())){
			parcelamentoPerfilNova.setPercentualDescontoSancao(Util.formatarMoedaRealparaBigDecimal(
							parcelamentoPerfilActionForm.getPercentualDescontoSancao(), 2));
		}

		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getQuantidadeEconomias())){
			parcelamentoPerfilNova.setQuantidadeEconomias(Integer.valueOf(parcelamentoPerfilActionForm.getQuantidadeEconomias()));
		}

		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getCapacidadeHidrometro())){
			parcelamentoPerfilNova.setCapacidadeHidrometro(Integer.valueOf(parcelamentoPerfilActionForm.getCapacidadeHidrometro()));
		}

		parcelamentoPerfilNova.setUltimaAlteracao(new Date());

		Integer idPerfilParcelamento = fachada.inserirPerfilParcelamento(parcelamentoPerfilNova,
						collectionParcelamentoQuantidadeReparcelamentoHelper, collectionParcelamentoDescontoInatividade,
						collectionParcelamentoDescontoAntiguidade, this.getUsuarioLogado(httpServletRequest));

		sessao.removeAttribute("collectionParcelamentoQuantidadeReparcelamentoHelper");
		sessao.removeAttribute("collectionParcelamentoDescontoInatividade");
		sessao.removeAttribute("collectionParcelamentoDescontoAntiguidade");

		FiltroResolucaoDiretoria filtroResolucaoDiretoria = new FiltroResolucaoDiretoria();
		filtroResolucaoDiretoria.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoria.CODIGO, idResolucaoDiretoria));
		Collection<ResolucaoDiretoria> collectionResolucaoDiretoria = fachada.pesquisar(filtroResolucaoDiretoria, ResolucaoDiretoria.class
						.getName());
		String numeroResolucaoDiretoria = ((ResolucaoDiretoria) Util.retonarObjetoDeColecao(collectionResolucaoDiretoria))
						.getNumeroResolucaoDiretoria();

		montarPaginaSucesso(httpServletRequest, "Perfil de Parcelamento da RD de número " + numeroResolucaoDiretoria
						+ " inserido com sucesso.", "Inserir outro Perfil de Parcelamento",
						"exibirInserirPerfilParcelamentoAction.do?desfazer=S",
						"exibirAtualizarPerfilParcelamentoAction.do?idRegistroInseridoAtualizar=" + idPerfilParcelamento,
						"Atualizar Perfil de Parcelamento Inserido");

		// devolve o mapeamento de retorno
		return retorno;

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
				if(vlSemRestAntiguidade != null && !vlSemRestAntiguidade.equals("")){

					percentualDescontoSemRestabelecimentoAntiguidade = Util.formatarMoedaRealparaBigDecimal(vlSemRestAntiguidade);
				}

				BigDecimal percentualDescontoComRestabelecimentoAntiguidade = null;
				if(vlComRestAntiguidade != null && !vlComRestAntiguidade.equals("")){

					percentualDescontoComRestabelecimentoAntiguidade = Util.formatarMoedaRealparaBigDecimal(vlComRestAntiguidade);
				}

				BigDecimal percentualDescontoAtivoAntiguidade = null;
				if(vlDescontoAtivo != null && !vlDescontoAtivo.equals("")){

					percentualDescontoAtivoAntiguidade = Util.formatarMoedaRealparaBigDecimal(vlDescontoAtivo);
				}

				parcelamentoDescontoAntiguidade.setPercentualDescontoSemRestabelecimento(percentualDescontoSemRestabelecimentoAntiguidade);
				parcelamentoDescontoAntiguidade.setPercentualDescontoComRestabelecimento(percentualDescontoComRestabelecimentoAntiguidade);
				parcelamentoDescontoAntiguidade.setPercentualDescontoAtivo(percentualDescontoAtivoAntiguidade);

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
			String vlJurosMoraSemRestInatividade = null;
			String vlMultaSemRestInatividade = null;
			String vlComRestInatividade = null;
			String vlJurosMoraComRestInatividade = null;
			String vlMultaComRestInatividade = null;

			Iterator iteratorParcelamentoDescontoInatividade = collectionParcelamentoDescontoInatividade.iterator();

			while(iteratorParcelamentoDescontoInatividade.hasNext()){

				ParcelamentoDescontoInatividade parcelamentoDescontoInatividade = (ParcelamentoDescontoInatividade) iteratorParcelamentoDescontoInatividade
								.next();
				long valorTempo = parcelamentoDescontoInatividade.getUltimaAlteracao().getTime();
				vlSemRestInatividade = (String) httpServletRequest.getParameter("vlSemRestInatividade" + valorTempo);
				vlJurosMoraSemRestInatividade = (String) httpServletRequest.getParameter("vlJurosMoraSemRestInatividade" + valorTempo);
				vlMultaSemRestInatividade = (String) httpServletRequest.getParameter("vlMultaSemRestInatividade" + valorTempo);
				vlComRestInatividade = httpServletRequest.getParameter("vlComRestInatividade" + valorTempo);
				vlJurosMoraComRestInatividade = (String) httpServletRequest.getParameter("vlJurosMoraComRestInatividade" + valorTempo);
				vlMultaComRestInatividade = (String) httpServletRequest.getParameter("vlMultaComRestInatividade" + valorTempo);

				// insere essas variáveis no objeto ParcelamentoDescontoInatividade
				BigDecimal percentualDescontoSemRestabelecimentoInatividade = null;
				if(vlSemRestInatividade != null && !vlSemRestInatividade.equals("")){

					percentualDescontoSemRestabelecimentoInatividade = Util.formatarMoedaRealparaBigDecimal(vlSemRestInatividade);
				}

				BigDecimal percentualDescontoJurosMoraSemRestabelecimentoInatividade = null;
				if(vlJurosMoraSemRestInatividade != null && !vlJurosMoraSemRestInatividade.equals("")){

					percentualDescontoJurosMoraSemRestabelecimentoInatividade = Util
									.formatarMoedaRealparaBigDecimal(vlJurosMoraSemRestInatividade);
				}

				BigDecimal percentualDescontoMultaSemRestabelecimentoInatividade = null;
				if(vlMultaSemRestInatividade != null && !vlMultaSemRestInatividade.equals("")){

					percentualDescontoMultaSemRestabelecimentoInatividade = Util.formatarMoedaRealparaBigDecimal(vlMultaSemRestInatividade);
				}

				BigDecimal percentualDescontoComRestabelecimentoInatividade = null;
				if(vlComRestInatividade != null && !vlComRestInatividade.equals("")){

					percentualDescontoComRestabelecimentoInatividade = Util.formatarMoedaRealparaBigDecimal(vlComRestInatividade);
				}

				BigDecimal percentualDescontoJurosMoraComRestabelecimentoInatividade = null;
				if(vlJurosMoraComRestInatividade != null && !vlJurosMoraComRestInatividade.equals("")){

					percentualDescontoJurosMoraComRestabelecimentoInatividade = Util
									.formatarMoedaRealparaBigDecimal(vlJurosMoraComRestInatividade);
				}

				BigDecimal percentualDescontoMultaComRestabelecimentoInatividade = null;
				if(vlMultaComRestInatividade != null && !vlMultaComRestInatividade.equals("")){

					percentualDescontoMultaComRestabelecimentoInatividade = Util.formatarMoedaRealparaBigDecimal(vlMultaComRestInatividade);
				}

				parcelamentoDescontoInatividade.setPercentualDescontoSemRestabelecimento(percentualDescontoSemRestabelecimentoInatividade);
				parcelamentoDescontoInatividade
								.setPercentualDescontoJurosMoraSemRestabelecimento(percentualDescontoJurosMoraSemRestabelecimentoInatividade);
				parcelamentoDescontoInatividade
								.setPercentualDescontoMultaSemRestabelecimento(percentualDescontoMultaSemRestabelecimentoInatividade);
				parcelamentoDescontoInatividade.setPercentualDescontoComRestabelecimento(percentualDescontoComRestabelecimentoInatividade);
				parcelamentoDescontoInatividade
								.setPercentualDescontoJurosMoraComRestabelecimento(percentualDescontoJurosMoraComRestabelecimentoInatividade);
				parcelamentoDescontoInatividade
								.setPercentualDescontoMultaComRestabelecimento(percentualDescontoMultaComRestabelecimentoInatividade);
			}

		}
	}

	private void validacaoFinal(String numeroResolucaoDiretoria, String imovelSituacaoTipo, String imovelPerfil, String subcategoria,
					String percentualDescontoAcrescimo, HttpServletRequest httpServletRequest,
					Collection collectionParcelamentoQuantidadeReparcelamentoHelper, Collection collectionParcelamentoDescontoAntiguidade,
					Collection collectionParcelamentoDescontoInatividade, BigDecimal valorMinimoDebitoAParcelarFaixaDebitoBigDecimal,
					BigDecimal valorMaximoDebitoAParcelarFaixaDebitoBigDecimal, ParcelamentoPerfilActionForm parcelamentoPerfilActionForm){

		if(Util.isVazioOuBranco(numeroResolucaoDiretoria)
						|| numeroResolucaoDiretoria.equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			httpServletRequest.setAttribute("nomeCampo", "numeroResolucaoDiretoria");
			// Informe Numero da RD.
			throw new ActionServletException("atencao.numero_rd_nao_informado");
		}

		if((imovelSituacaoTipo == null) || (imovelSituacaoTipo.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))){
			httpServletRequest.setAttribute("nomeCampo", "imovelSituacaoTipo");
			// Informe Tipo da Situação do Imóvel
			throw new ActionServletException("atencao.tipo_situacao_imovel_nao_informado");
		}

		Integer idIimovelPerfil = null;

		if(!Util.isVazioOuBranco(imovelPerfil)){
			idIimovelPerfil = Integer.valueOf(imovelPerfil);
		}

		Integer idSubcategoria = null;
		if(!Util.isVazioOuBranco(subcategoria)){
			idSubcategoria = Integer.valueOf(subcategoria);
		}

		Collection colecaoParcelamentoPerfil = fachada.pesquisarExstenciaPerfilParcelamento(null,
						Integer.valueOf(numeroResolucaoDiretoria), Integer.valueOf(imovelSituacaoTipo), idIimovelPerfil, idSubcategoria,
						valorMinimoDebitoAParcelarFaixaDebitoBigDecimal, valorMaximoDebitoAParcelarFaixaDebitoBigDecimal, "INSERIR");

		if(colecaoParcelamentoPerfil != null && !colecaoParcelamentoPerfil.isEmpty()){
			throw new ActionServletException("atencao.perfil_parcelamento_ja_existe");
		}

		if(collectionParcelamentoQuantidadeReparcelamentoHelper == null || collectionParcelamentoQuantidadeReparcelamentoHelper.isEmpty()){
			throw new ActionServletException(
			// Informe os dados de Reparcelamento Consecutivo
							"atencao.required", null, "Reparcelamento Consecutivo");
		}else{
			Iterator iterator = collectionParcelamentoQuantidadeReparcelamentoHelper.iterator();

			while(iterator.hasNext()){

				ParcelamentoQuantidadeReparcelamentoHelper parcelamentoQuantidadeReparcelamentoHelper = (ParcelamentoQuantidadeReparcelamentoHelper) iterator
								.next();

				Collection collectionParcelamentoQuantidadePrestacaoHelper = parcelamentoQuantidadeReparcelamentoHelper
								.getCollectionParcelamentoQuantidadePrestacaoHelper();

				if(collectionParcelamentoQuantidadePrestacaoHelper == null || collectionParcelamentoQuantidadePrestacaoHelper.isEmpty()){
					throw new ActionServletException(
					// Informações do Parcelamento por Quantidade de Reparcelamentos deve ser
									// informado
									"atencao.campo.informado", null, "Informações do Parcelamento por Quantidade de Reparcelamentos");
				}else{
					validarCollectionParcelamentoQuantidadePrestacaoHelper(collectionParcelamentoQuantidadePrestacaoHelper,
									parcelamentoPerfilActionForm);
				}
			}
		}

		if(collectionParcelamentoDescontoAntiguidade != null && !collectionParcelamentoDescontoAntiguidade.isEmpty()){

			Iterator iteratorParcelamentoDescontoAntiguidade = collectionParcelamentoDescontoAntiguidade.iterator();

			while(iteratorParcelamentoDescontoAntiguidade.hasNext()){

				ParcelamentoDescontoAntiguidade parcelamentoDescontoAntiguidade = (ParcelamentoDescontoAntiguidade) iteratorParcelamentoDescontoAntiguidade
								.next();

				if(parcelamentoDescontoAntiguidade.getPercentualDescontoSemRestabelecimento() == null){
					throw new ActionServletException(
					// Percentual de Desconto Sem Restabelecimento
									"atencao.required", null, "  Percentual de Desconto Sem Restabelecimento");
				}

				if(parcelamentoDescontoAntiguidade.getPercentualDescontoComRestabelecimento() == null){
					throw new ActionServletException(
					// Informe Percentual de Desconto Com Restabelecimento
									"atencao.required", null, "  Percentual de Desconto Com Restabelecimento");
				}

				if(parcelamentoDescontoAntiguidade.getPercentualDescontoAtivo() == null){
					throw new ActionServletException(
					// Informe Percentual de Desconto Ativo
									"atencao.required", null, "  Percentual de Desconto Ativo");
				}

			}

		}

		if(collectionParcelamentoDescontoInatividade != null && !collectionParcelamentoDescontoInatividade.isEmpty()){

			Iterator iteratorParcelamentoDescontoInatividade = collectionParcelamentoDescontoInatividade.iterator();

			while(iteratorParcelamentoDescontoInatividade.hasNext()){

				ParcelamentoDescontoInatividade parcelamentoDescontoInatividade = (ParcelamentoDescontoInatividade) iteratorParcelamentoDescontoInatividade
								.next();

				if(parcelamentoDescontoInatividade.getPercentualDescontoSemRestabelecimento() == null){
					throw new ActionServletException(
					// Percentual de Desconto Sem Restabelecimento
									"atencao.required", null, "  Percentual de Desconto Sem Restabelecimento");
				}

				if(parcelamentoDescontoInatividade.getPercentualDescontoJurosMoraSemRestabelecimento() != null){
					BigDecimal percentualDescontoJurosMoraSemRestabelecimento = parcelamentoDescontoInatividade
									.getPercentualDescontoJurosMoraSemRestabelecimento();

					// [FS0010] - Verificar Percentual Máximo
					// Caso o percentual informado seja maior que 100%, exibir a mensagem
					// "Percentual não pode ser maior que 100%"
					if(percentualDescontoJurosMoraSemRestabelecimento.compareTo(new BigDecimal(100)) > 0){
						throw new ActionServletException("atencao.percentual.invalido.maximo", null,
										"Percentual de Desconto de Juros Mora Sem Restabelecimento");
					}
				}

				if(parcelamentoDescontoInatividade.getPercentualDescontoMultaSemRestabelecimento() != null){
					BigDecimal percentualDescontoMultaSemRestabelecimento = parcelamentoDescontoInatividade
									.getPercentualDescontoMultaSemRestabelecimento();

					// [FS0010] - Verificar Percentual Máximo
					// Caso o percentual informado seja maior que 100%, exibir a mensagem
					// "Percentual não pode ser maior que 100%"
					if(percentualDescontoMultaSemRestabelecimento.compareTo(new BigDecimal(100)) > 0){
						throw new ActionServletException("atencao.percentual.invalido.maximo", null,
										"Percentual de Desconto de Multa Sem Restabelecimento");
					}
				}

				if(parcelamentoDescontoInatividade.getPercentualDescontoComRestabelecimento() == null){
					throw new ActionServletException(
					// Informe Percentual de Desconto Com Restabelecimento
									"atencao.required", null, "  Percentual de Desconto Com Restabelecimento");
				}

				if(parcelamentoDescontoInatividade.getPercentualDescontoJurosMoraComRestabelecimento() != null){
					BigDecimal percentualDescontoJurosMoraComRestabelecimento = parcelamentoDescontoInatividade
									.getPercentualDescontoJurosMoraComRestabelecimento();

					// [FS0010] - Verificar Percentual Máximo
					// Caso o percentual informado seja maior que 100%, exibir a mensagem
					// "Percentual não pode ser maior que 100%"
					if(percentualDescontoJurosMoraComRestabelecimento.compareTo(new BigDecimal(100)) > 0){
						throw new ActionServletException("atencao.percentual.invalido.maximo", null,
										"Percentual de Desconto de Juros Mora Com Restabelecimento");
					}
				}

				if(parcelamentoDescontoInatividade.getPercentualDescontoMultaComRestabelecimento() != null){
					BigDecimal percentualDescontoMultaComRestabelecimento = parcelamentoDescontoInatividade
									.getPercentualDescontoMultaComRestabelecimento();

					// [FS0010] - Verificar Percentual Máximo
					// Caso o percentual informado seja maior que 100%, exibir a mensagem
					// "Percentual não pode ser maior que 100%"
					if(percentualDescontoMultaComRestabelecimento.compareTo(new BigDecimal(100)) > 0){
						throw new ActionServletException("atencao.percentual.invalido.maximo", null,
										"Percentual de Desconto de Multa Com Restabelecimento");
					}
				}

			}
		}

	}

	/**
	 * @author isilva
	 * @param collectionParcelamentoQuantidadePrestacaoHelper
	 */
	private void validarCollectionParcelamentoQuantidadePrestacaoHelper(Collection collectionParcelamentoQuantidadePrestacaoHelper,
					ParcelamentoPerfilActionForm parcelamentoPerfilActionForm){

		if(collectionParcelamentoQuantidadePrestacaoHelper != null && !collectionParcelamentoQuantidadePrestacaoHelper.isEmpty()){

			Collection collectionParcelamentoQuantidadePrestacaoHelperAux = collectionParcelamentoQuantidadePrestacaoHelper;
			Iterator iterator = collectionParcelamentoQuantidadePrestacaoHelperAux.iterator();

			while(iterator.hasNext()){
				ParcelamentoQuantidadePrestacaoHelper parcelamentoQuantidadePrestacaoHelper = (ParcelamentoQuantidadePrestacaoHelper) iterator
								.next();

				if(parcelamentoQuantidadePrestacaoHelper.getCollectionParcelamentoFaixaValor() == null
								|| parcelamentoQuantidadePrestacaoHelper.getCollectionParcelamentoFaixaValor().isEmpty()){

					ParcelamentoQuantidadePrestacao parcelamentoQuantidadePrestacao = parcelamentoQuantidadePrestacaoHelper
									.getParcelamentoQuantidadePrestacao();

					if(parcelamentoQuantidadePrestacao.getQuantidadeMaximaPrestacao() == null){
						throw new ActionServletException("atencao.required", null, "Qtde. Máxima Prestações");
					}

					if((parcelamentoPerfilActionForm.getIndicadorPermitirInformarNumeroValorParcelas() == null || parcelamentoPerfilActionForm
									.getIndicadorPermitirInformarNumeroValorParcelas().equals(ConstantesSistema.NAO.toString()))
									&& parcelamentoQuantidadePrestacao.getTaxaJuros() == null){
						throw new ActionServletException("atencao.required", null, "Taxa de Juros a.m.");
					}

					int qtdInformada = 0;

					if(parcelamentoQuantidadePrestacao.getValorMinimoEntrada() != null){
						qtdInformada++;
					}

					if(parcelamentoQuantidadePrestacao.getPercentualTarifaMinimaImovel() != null){
						qtdInformada++;
					}

					if(parcelamentoQuantidadePrestacao.getPercentualMinimoEntrada() != null){
						qtdInformada++;
					}

					if(qtdInformada != 1){
						throw new ActionServletException("atencao.informe.valor.min.entrada.ou.perc.tarifa.min.ou.perc.valor.debito.Opcoes");
					}

					if(parcelamentoQuantidadePrestacao.getPercentualMinimoEntrada() != null){
						if(parcelamentoQuantidadePrestacao.getIndicadorEntradaParcelamento() == null){
							throw new ActionServletException("atencao.required", null, "Opções do Débito");
						}
					}
				}
			}
		}
	}

	/**
	 * @author isilva
	 * @param parcelamentoPerfilActionForm
	 * @param httpServletRequest
	 */
	private void validarDadosParcelamentoPerfil(ParcelamentoPerfilActionForm parcelamentoPerfilActionForm,
					HttpServletRequest httpServletRequest){

		int qtdInformada = 0;

		BigDecimal percentualTarifaMinimaPrestacao = null;
		BigDecimal percentualValorDebitoCalculoValorMinimoPrestacao = null;
		BigDecimal valorDebitoPrestacao = null;

		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getPercentualTarifaMinimaPrestacao())){
			qtdInformada++;
			percentualTarifaMinimaPrestacao = Util.formatarStringMoedaRealparaBigDecimal(parcelamentoPerfilActionForm
							.getPercentualTarifaMinimaPrestacao().toString(), 2);
		}

		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getPercentualValorDebitoCalculoValorMinimoPrestacao())){
			qtdInformada++;
			percentualValorDebitoCalculoValorMinimoPrestacao = Util.formatarStringMoedaRealparaBigDecimal(parcelamentoPerfilActionForm
							.getPercentualValorDebitoCalculoValorMinimoPrestacao().toString(), 2);
		}

		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getValorDebitoPrestacao())){
			qtdInformada++;
			valorDebitoPrestacao = Util.formatarStringMoedaRealparaBigDecimal(parcelamentoPerfilActionForm.getValorDebitoPrestacao()
							.toString(), 2);
		}

		if(qtdInformada != 1){
			throw new ActionServletException("atencao.informe.tarifa.minima.valor.ou.percentual.valor.debito.ou.valor.minimo.prestacao");
		}

		int qtdNulo = 0;

		if(percentualTarifaMinimaPrestacao == null){
			qtdNulo++;
		}

		if(percentualValorDebitoCalculoValorMinimoPrestacao == null){
			qtdNulo++;
		}

		if(valorDebitoPrestacao == null){
			qtdNulo++;
		}

		if(qtdNulo != 2){
			throw new ActionServletException("atencao.informe.tarifa.minima.valor.ou.percentual.valor.debito.ou.valor.minimo.prestacao");
		}

		if(!Util.isVazioOuBranco(parcelamentoPerfilActionForm.getPercentualValorDebitoCalculoValorMinimoPrestacao())
						&& Util.isVazioOuBranco(parcelamentoPerfilActionForm.getIndicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima())){
			throw new ActionServletException("atencao.required", null, "Informe Opções da Prestação Mínima");
		}
	}

}
