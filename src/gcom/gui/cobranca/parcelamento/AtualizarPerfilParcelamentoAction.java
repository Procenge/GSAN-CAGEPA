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
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action form de manter Perfil de Parcelamento
 * 
 * @author Vivianne Sousa
 * @created 12/05/2006
 */
public class AtualizarPerfilParcelamentoAction
				extends GcomAction {

	/**
	 * @author Vivianne Sousa
	 * @date 12/05/2006
	 * @author Saulo Lima
	 * @date 02/02/2009
	 *       Correção para pegar os dados do objeto ParcelamentoPerfil antes de atualizar
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		AtualizarParcelamentoPerfilActionForm atualizarParcelamentoPerfilActionForm = (AtualizarParcelamentoPerfilActionForm) actionForm;
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		String idPerfilParcelamento = (String) sessao.getAttribute("idRegistroAtualizacao");
		String numeroResolucaoDiretoria = atualizarParcelamentoPerfilActionForm.getNumeroResolucaoDiretoria();

		FiltroParcelamentoPerfil filtroParcelamentoPerfil = new FiltroParcelamentoPerfil();

		// Seta o filtro para buscar o ParcelamentoPerfil na base
		// e recuperar o idImovelSituacaoTipo,idImovelPerfil e o idSubcategoria
		filtroParcelamentoPerfil.adicionarParametro(new ParametroSimples(FiltroParcelamentoPerfil.ID, idPerfilParcelamento));
		filtroParcelamentoPerfil.adicionarCaminhoParaCarregamentoEntidade("imovelSituacaoTipo");
		filtroParcelamentoPerfil.adicionarCaminhoParaCarregamentoEntidade("imovelPerfil");
		filtroParcelamentoPerfil.adicionarCaminhoParaCarregamentoEntidade("subcategoria");
		filtroParcelamentoPerfil.adicionarCaminhoParaCarregamentoEntidade("resolucaoDiretoria.resolucaoDiretoriaLayout");

		// Procura o ParcelamentoPerfil na base
		ParcelamentoPerfil parcelamentoPerfilNaBase = null;
		parcelamentoPerfilNaBase = (ParcelamentoPerfil) ((List) (fachada.pesquisar(filtroParcelamentoPerfil, ParcelamentoPerfil.class
						.getName()))).get(0);

		Integer idImovelSituacaoTipo = parcelamentoPerfilNaBase.getImovelSituacaoTipo().getId();

		Integer idImovelPerfil = null;
		if(parcelamentoPerfilNaBase.getImovelPerfil() != null && !parcelamentoPerfilNaBase.getImovelPerfil().equals("")){
			idImovelPerfil = parcelamentoPerfilNaBase.getImovelPerfil().getId();
		}

		Integer idSubcategoria = null;
		if(parcelamentoPerfilNaBase.getSubcategoria() != null && !parcelamentoPerfilNaBase.getSubcategoria().equals("")){
			idSubcategoria = parcelamentoPerfilNaBase.getSubcategoria().getId();
		}

		String percentualDescontoAcrescimo = null;
		if(atualizarParcelamentoPerfilActionForm.getPercentualDescontoAcrescimo() != null){
			percentualDescontoAcrescimo = atualizarParcelamentoPerfilActionForm.getPercentualDescontoAcrescimo().toString().replace(",",
							".");
		}

		atualizaColecoesNaSessao(sessao, httpServletRequest);

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

		Collection collectionParcelamentoQuantidadeReparcelamentoHelperLinhaRemovidas = (Collection) sessao
						.getAttribute("collectionParcelamentoQuantidadeReparcelamentoHelperLinhaRemovidas");

		Collection collectionParcelamentoDescontoInatividadeLinhaRemovidas = (Collection) sessao
						.getAttribute("collectionParcelamentoDescontoInatividadeLinhaRemovidas");

		Collection collectionParcelamentoDescontoAntiguidadeLinhaRemovidas = (Collection) sessao
						.getAttribute("collectionParcelamentoDescontoAntiguidadeLinhaRemovidas");

		Collection collectionParcelamentoQuantidadePrestacaoHelperLinhaRemovidas = (Collection) sessao
						.getAttribute("collectionParcelamentoQuantidadePrestacaoHelperLinhaRemovidas");

		BigDecimal valorMinimoDebitoAParcelarFaixaDebitoBigDecimal = BigDecimal.ZERO;
		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getValorMinimoDebitoAParcelarFaixaDebito())){
			valorMinimoDebitoAParcelarFaixaDebitoBigDecimal = Util.formatarMoedaRealparaBigDecimal(atualizarParcelamentoPerfilActionForm
							.getValorMinimoDebitoAParcelarFaixaDebito(), 2);
		}else{
			httpServletRequest.setAttribute("nomeCampo", "valorMinimoDebitoAParcelarFaixaDebito");
			throw new ActionServletException("atencao.required", null, "Valor Mínimo de Débito a Parcelar");
		}

		BigDecimal valorMaximoDebitoAParcelarFaixaDebitoBigDecimal = BigDecimal.ZERO;
		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getValorMaximoDebitoAParcelarFaixaDebito())){
			valorMaximoDebitoAParcelarFaixaDebitoBigDecimal = Util.formatarMoedaRealparaBigDecimal(atualizarParcelamentoPerfilActionForm
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
		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getIndicadorDebitoOriginalOuAtualizadoFaixaDebito())){
			indicadorDebitoOriginalOuAtualizadoFaixaDebitoShort = Short.valueOf(atualizarParcelamentoPerfilActionForm
							.getIndicadorDebitoOriginalOuAtualizadoFaixaDebito().toString());
		}else{
			httpServletRequest.setAttribute("nomeCampo", "indicadorDebitoOriginalOuAtualizadoFaixaDebito");
			throw new ActionServletException("atencao.required", null, "Opções da Faixa de Débito a Parcelar");
		}

		validarDadosParcelamentoPerfil(atualizarParcelamentoPerfilActionForm);

		String imovelPerfilId = null;
		if(!Util.isVazioOuBranco(idImovelPerfil)){
			imovelPerfilId = idImovelPerfil.toString();
		}

		String subCategoriaId = null;
		if(!Util.isVazioOuBranco(idSubcategoria)){
			subCategoriaId = idSubcategoria.toString();
		}

		validacaoFinal(numeroResolucaoDiretoria, idImovelSituacaoTipo.toString(), imovelPerfilId, subCategoriaId,
						percentualDescontoAcrescimo, httpServletRequest, collectionParcelamentoQuantidadeReparcelamentoHelper,
						collectionParcelamentoDescontoAntiguidade, collectionParcelamentoDescontoInatividade,
						valorMinimoDebitoAParcelarFaixaDebitoBigDecimal, valorMaximoDebitoAParcelarFaixaDebitoBigDecimal,
						parcelamentoPerfilNaBase);

		ParcelamentoPerfil parcelamentoPerfil = new ParcelamentoPerfil();
		if(parcelamentoPerfilNaBase != null){
			parcelamentoPerfil = parcelamentoPerfilNaBase;
		}else{
			parcelamentoPerfil.setId(Integer.valueOf(idPerfilParcelamento));
		}

		ResolucaoDiretoria resolucaoDiretoria = null;
		if(numeroResolucaoDiretoria != null && !numeroResolucaoDiretoria.equals("")){

			FiltroResolucaoDiretoria filtroResolucaoDiretoria = new FiltroResolucaoDiretoria();
			filtroResolucaoDiretoria.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoria.NUMERO, numeroResolucaoDiretoria));
			filtroResolucaoDiretoria.adicionarCaminhoParaCarregamentoEntidade("resolucaoDiretoriaLayout");
			Collection<ResolucaoDiretoria> collectionResolucaoDiretoria = fachada.pesquisar(filtroResolucaoDiretoria,
							ResolucaoDiretoria.class.getName());

			if(collectionResolucaoDiretoria != null && !collectionResolucaoDiretoria.isEmpty()){
				resolucaoDiretoria = (ResolucaoDiretoria) Util.retonarObjetoDeColecao(collectionResolucaoDiretoria);
				parcelamentoPerfil.setResolucaoDiretoria(resolucaoDiretoria);
			}
		}
		
		ImovelSituacaoTipo imovelSituacaoTipo = null;
		if(idImovelSituacaoTipo != null && !idImovelSituacaoTipo.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			imovelSituacaoTipo = new ImovelSituacaoTipo();
			imovelSituacaoTipo.setId(Integer.valueOf(idImovelSituacaoTipo));
			parcelamentoPerfil.setImovelSituacaoTipo(imovelSituacaoTipo);
		}

		ImovelPerfil imovelPerfil = null;
		if(idImovelPerfil != null && !idImovelPerfil.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			imovelPerfil = new ImovelPerfil();
			imovelPerfil.setId(Integer.valueOf(idImovelPerfil));
			parcelamentoPerfil.setImovelPerfil(imovelPerfil);
		}

		Subcategoria subcategoria = null;
		if(idSubcategoria != null && !idSubcategoria.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			subcategoria = new Subcategoria();
			subcategoria.setId(Integer.valueOf(idSubcategoria));
			parcelamentoPerfil.setSubcategoria(subcategoria);
		}

		BigDecimal percentualDescontoAcrescimoBigDecimal = Util.formatarMoedaRealparaBigDecimal(percentualDescontoAcrescimo, 2);
		BigDecimal percentualDescontoAcrescimoNaBase = parcelamentoPerfilNaBase.getPercentualDescontoAcrescimo();
		if(percentualDescontoAcrescimoBigDecimal != null){
			if(percentualDescontoAcrescimoNaBase != null
						&& percentualDescontoAcrescimoBigDecimal.compareTo(percentualDescontoAcrescimoNaBase) != 0){
				if(percentualDescontoAcrescimoBigDecimal != BigDecimal.ZERO){
					parcelamentoPerfil.setPercentualDescontoAcrescimo(percentualDescontoAcrescimoBigDecimal);
				}else{
					parcelamentoPerfil.setPercentualDescontoAcrescimo(BigDecimal.ZERO);
				}
			}
		}else{
			parcelamentoPerfil.setPercentualDescontoAcrescimo(BigDecimal.ZERO);
		}

		// Cobrar acréscimos por impontualidades (Sim ou Não)
		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getIndicadorCobrarAcrescimosImpontualidade())){

			parcelamentoPerfil.setIndicadorCobrarAcrescimosPorImpontualidades(Util.obterShort(atualizarParcelamentoPerfilActionForm
							.getIndicadorCobrarAcrescimosImpontualidade()));
		}

		// Permitir a cobrança do parcelamento através de Guia de Pagamento (Sim ou Não)
		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getIndicadorPermitirCobrancaParcelamentoPorGuia())){

			parcelamentoPerfil.setIndicadorPermiteParcelamentoPorGuiaPagamento(Util.obterShort(atualizarParcelamentoPerfilActionForm
							.getIndicadorPermitirCobrancaParcelamentoPorGuia()));

			if(parcelamentoPerfil.getIndicadorPermiteParcelamentoPorGuiaPagamento().equals(ConstantesSistema.SIM)){

				if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getIndicadorPermitirInformarNumeroValorParcelas())){

					parcelamentoPerfil.setIndicadorPermiteInformarNumeroValorParcela(Util.obterShort(atualizarParcelamentoPerfilActionForm
								.getIndicadorPermitirInformarNumeroValorParcelas()));
				}else{

					parcelamentoPerfil.setIndicadorPermiteInformarNumeroValorParcela(ConstantesSistema.NAO);
				}
			}else{

				parcelamentoPerfil.setIndicadorPermiteInformarNumeroValorParcela(ConstantesSistema.NAO);
			}
		}

		// Cobrança de multa em caso de descumprimento de prestações (Sim ou Não)
		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getIndicadorCobrarMultaPorDescumprimentoPrestacao())
						&& Util.obterShort(atualizarParcelamentoPerfilActionForm.getIndicadorCobrarMultaPorDescumprimentoPrestacao())
										.shortValue() == ConstantesSistema.SIM.shortValue()){

			// Número de prestações descumpridas consecutivas para cobrança de multa
			parcelamentoPerfil.setNumeroPretacoesDescumpridasParaCobrancaMulta(Util.obterInteger(atualizarParcelamentoPerfilActionForm
							.getNumeroPrestacacoesDescumpridasConsecutivas()));

			// Percentual de multa
			BigDecimal percentualMultaPorPrestacacoesDescumpridasBigDecimal = Util.formatarStringMoedaRealparaBigDecimal(
							atualizarParcelamentoPerfilActionForm.getPercentualMultaPorPrestacacoesDescumpridas(), 2);
			BigDecimal percentualMultaPorPrestacacoesDescumpridas = parcelamentoPerfilNaBase.getPercentualMultaPrestacaoDescumprida();
			if(percentualMultaPorPrestacacoesDescumpridasBigDecimal != null
							&& percentualMultaPorPrestacacoesDescumpridas != null
							&& percentualMultaPorPrestacacoesDescumpridasBigDecimal.compareTo(percentualMultaPorPrestacacoesDescumpridas) != 0){
				parcelamentoPerfil.setPercentualMultaPrestacaoDescumprida(percentualMultaPorPrestacacoesDescumpridasBigDecimal);
			}
			if(percentualMultaPorPrestacacoesDescumpridasBigDecimal != null && percentualMultaPorPrestacacoesDescumpridas == null){
				parcelamentoPerfil.setPercentualMultaPrestacaoDescumprida(percentualMultaPorPrestacacoesDescumpridasBigDecimal);
			}

		}else{

			// Número de prestações descumpridas consecutivas para cobrança de multa
			parcelamentoPerfil.setNumeroPretacoesDescumpridasParaCobrancaMulta(null);

			// Percentual de multa
			parcelamentoPerfil.setPercentualMultaPrestacaoDescumprida(null);
		}

		// if (percentualTarifaMinimaPrestacao != null &&
		// !percentualTarifaMinimaPrestacao.equalsIgnoreCase("0")) {
		// parcelamentoPerfil.setPercentualTarifaMinimaPrestacao(new
		// BigDecimal(percentualTarifaMinimaPrestacao));
		// parcelamentoPerfil.setValorDebitoPrestacao(BigDecimal.ZERO);
		// } else if(valorDebitoPrestacao != null && !valorDebitoPrestacao.equalsIgnoreCase("0")){
		// parcelamentoPerfil.setValorDebitoPrestacao(new BigDecimal(valorDebitoPrestacao));
		// parcelamentoPerfil.setPercentualTarifaMinimaPrestacao(BigDecimal.ZERO);
		// }else{
		// throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null,
		// "Percentual Tarifa Minima Prestacao ou Percentual Valor Debito Prestacao");
		// }

		String percentualTarifaMinimaPrestacaoForm = atualizarParcelamentoPerfilActionForm.getPercentualTarifaMinimaPrestacao();

		if(!Util.isVazioOuBranco(percentualTarifaMinimaPrestacaoForm)){
			BigDecimal percentualTarifaMinimaPrestacaoFormBD = Util.formatarMoedaRealparaBigDecimal(percentualTarifaMinimaPrestacaoForm, 2);

			BigDecimal percentualTarifaMinimaPrestacao = parcelamentoPerfilNaBase.getPercentualTarifaMinimaPrestacao();

			if(percentualTarifaMinimaPrestacaoFormBD.compareTo(percentualTarifaMinimaPrestacao) != 0){
				parcelamentoPerfil.setPercentualTarifaMinimaPrestacao(percentualTarifaMinimaPrestacaoFormBD);
			}
		}else{
			parcelamentoPerfil.setPercentualTarifaMinimaPrestacao(null);
		}

		BigDecimal valorDebitoPrestacaoBigDecimal = Util.formatarStringMoedaRealparaBigDecimal(
						atualizarParcelamentoPerfilActionForm.getValorDebitoPrestacao(), 2);
		BigDecimal valorDebitoPrestacao = parcelamentoPerfilNaBase.getValorDebitoPrestacao();
		if(valorDebitoPrestacaoBigDecimal != null && valorDebitoPrestacao != null
						&& valorDebitoPrestacaoBigDecimal.compareTo(valorDebitoPrestacao) != 0){
			parcelamentoPerfil.setValorDebitoPrestacao(valorDebitoPrestacaoBigDecimal);
		}

		if(atualizarParcelamentoPerfilActionForm.getIndicadorParcelarChequeDevolvido() != null
						&& !atualizarParcelamentoPerfilActionForm.getIndicadorParcelarChequeDevolvido().equals("")){
			parcelamentoPerfil.setIndicadorChequeDevolvido(new Short(atualizarParcelamentoPerfilActionForm
							.getIndicadorParcelarChequeDevolvido()));
		}else{
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "Não parcelar com cheque devolvido");
		}

		if(atualizarParcelamentoPerfilActionForm.getConsumoMinimo() != null
						&& !atualizarParcelamentoPerfilActionForm.getConsumoMinimo().equals("")){
			parcelamentoPerfil.setNumeroConsumoMinimo(Integer.valueOf(atualizarParcelamentoPerfilActionForm.getConsumoMinimo()));
		}

		BigDecimal percentualVariacaoConsumoMedioBigDecimal = Util.formatarStringMoedaRealparaBigDecimal(
						atualizarParcelamentoPerfilActionForm
.getPercentualVariacaoConsumoMedio(), 2);
		BigDecimal percentualVariacaoConsumoMedio = parcelamentoPerfilNaBase.getPercentualVariacaoConsumoMedio();

		if(percentualVariacaoConsumoMedioBigDecimal != null && percentualVariacaoConsumoMedio != null
						&& percentualVariacaoConsumoMedioBigDecimal.compareTo(percentualVariacaoConsumoMedio) != 0){
			parcelamentoPerfil.setPercentualVariacaoConsumoMedio(percentualVariacaoConsumoMedioBigDecimal);
		}

		if(percentualVariacaoConsumoMedioBigDecimal != null && percentualVariacaoConsumoMedio == null){
			parcelamentoPerfil.setPercentualVariacaoConsumoMedio(percentualVariacaoConsumoMedioBigDecimal);
		}

		if(atualizarParcelamentoPerfilActionForm.getIndicadorParcelarSancoesMaisDeUmaConta() != null
						&& !atualizarParcelamentoPerfilActionForm.getIndicadorParcelarSancoesMaisDeUmaConta().equals("")){
			parcelamentoPerfil.setIndicadorSancoesUnicaConta(new Short(atualizarParcelamentoPerfilActionForm
							.getIndicadorParcelarSancoesMaisDeUmaConta()));
		}else{
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "Não parcelar com sanções em mais de uma conta");
		}

		// parcelamentoPerfil.setIndicadorRetroativoTarifaSocial(ConstantesSistema.NAO);
		// parcelamentoPerfil.setIndicadorAlertaParcelaMinima(ConstantesSistema.NAO);

		BigDecimal valorMinimoDebitoAParcelarFaixaDebito = parcelamentoPerfilNaBase.getValorMinimoDebitoAParcelarFaixaDebito();
		if(valorMinimoDebitoAParcelarFaixaDebito != null
						&& valorMinimoDebitoAParcelarFaixaDebitoBigDecimal.compareTo(valorMinimoDebitoAParcelarFaixaDebito) != 0){
			parcelamentoPerfil.setValorMinimoDebitoAParcelarFaixaDebito(valorMinimoDebitoAParcelarFaixaDebitoBigDecimal);
		}

		BigDecimal valorMaximoDebitoAParcelarFaixaDebito = parcelamentoPerfilNaBase.getValorMaximoDebitoAParcelarFaixaDebito();
		if(valorMaximoDebitoAParcelarFaixaDebito != null
						&& valorMaximoDebitoAParcelarFaixaDebitoBigDecimal.compareTo(valorMaximoDebitoAParcelarFaixaDebito) != 0){
			parcelamentoPerfil.setValorMaximoDebitoAParcelarFaixaDebito(valorMaximoDebitoAParcelarFaixaDebitoBigDecimal);
		}

		parcelamentoPerfil.setIndicadorDebitoOriginalOuAtualizadoFaixaDebito(indicadorDebitoOriginalOuAtualizadoFaixaDebitoShort);

		Short indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinimaShort = null;

		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getIndicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima())){
			indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinimaShort = Short.valueOf(atualizarParcelamentoPerfilActionForm
							.getIndicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima());
		}

		parcelamentoPerfil
						.setIndicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima(indicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinimaShort);

		BigDecimal percentualValorDebitoCalculoValorMinimoPrestacaoBigDecimal = Util.formatarStringMoedaRealparaBigDecimal(
						atualizarParcelamentoPerfilActionForm.getPercentualValorDebitoCalculoValorMinimoPrestacao(), 2);
		BigDecimal percentualValorDebitoCalculoValorMinimoPrestacao = parcelamentoPerfilNaBase
						.getPercentualValorDebitoCalculoValorMinimoPrestacao();
		if(percentualValorDebitoCalculoValorMinimoPrestacaoBigDecimal != null
						&& percentualValorDebitoCalculoValorMinimoPrestacao != null
						&& percentualValorDebitoCalculoValorMinimoPrestacaoBigDecimal
										.compareTo(percentualValorDebitoCalculoValorMinimoPrestacao) != 0){
			parcelamentoPerfil
							.setPercentualValorDebitoCalculoValorMinimoPrestacao(percentualValorDebitoCalculoValorMinimoPrestacaoBigDecimal);
		}

		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getNumeroConsumoEconomia())){
			parcelamentoPerfil.setNumeroConsumoEconomia(Integer.valueOf(atualizarParcelamentoPerfilActionForm.getNumeroConsumoEconomia()));
		}

		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getNumeroAreaConstruida())){
			parcelamentoPerfil.setNumeroAreaConstruida(Util.formatarStringParaBigDecimal(atualizarParcelamentoPerfilActionForm
							.getNumeroAreaConstruida()));
		}

		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getAnoMesReferenciaLimiteInferior())){
			parcelamentoPerfil.setAnoMesReferenciaLimiteInferior(Util
							.formatarMesAnoComBarraParaAnoMes(atualizarParcelamentoPerfilActionForm
							.getAnoMesReferenciaLimiteInferior()));
		}

		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getAnoMesReferenciaLimiteSuperior())){
			parcelamentoPerfil.setAnoMesReferenciaLimiteSuperior(Util
							.formatarMesAnoComBarraParaAnoMes(atualizarParcelamentoPerfilActionForm
							.getAnoMesReferenciaLimiteInferior()));
		}

		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getPercentualDescontoAVista())){
			parcelamentoPerfil.setPercentualDescontoAVista(Util.formatarMoedaRealparaBigDecimal(
							atualizarParcelamentoPerfilActionForm.getPercentualDescontoAVista(), 2));
		}

		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getParcelaQuantidadeMinimaFatura())){
			parcelamentoPerfil.setParcelaQuantidadeMinimaFatura(Integer.valueOf(atualizarParcelamentoPerfilActionForm
							.getParcelaQuantidadeMinimaFatura()));
		}

		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getPercentualDescontoSancao())){
			parcelamentoPerfil.setPercentualDescontoSancao(Util.formatarMoedaRealparaBigDecimal(
							atualizarParcelamentoPerfilActionForm.getPercentualDescontoSancao(), 2));
		}

		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getQuantidadeEconomias())){
			parcelamentoPerfil.setQuantidadeEconomias(Integer.valueOf(atualizarParcelamentoPerfilActionForm.getQuantidadeEconomias()));
		}

		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getCapacidadeHidrometro())){
			parcelamentoPerfil.setCapacidadeHidrometro(Integer.valueOf(atualizarParcelamentoPerfilActionForm.getCapacidadeHidrometro()));
		}

		parcelamentoPerfil.setUltimaAlteracao(Util.converteStringParaDateHora(atualizarParcelamentoPerfilActionForm.getUltimaAlteracao()));

		fachada.atualizarPerfilParcelamento(parcelamentoPerfil, collectionParcelamentoQuantidadeReparcelamentoHelper,
						collectionParcelamentoDescontoInatividade, collectionParcelamentoDescontoAntiguidade,
						collectionParcelamentoQuantidadeReparcelamentoHelperLinhaRemovidas,
						collectionParcelamentoDescontoInatividadeLinhaRemovidas, collectionParcelamentoDescontoAntiguidadeLinhaRemovidas,
						collectionParcelamentoQuantidadePrestacaoHelperLinhaRemovidas, this.getUsuarioLogado(httpServletRequest));

		sessao.removeAttribute("collectionParcelamentoQuantidadeReparcelamentoHelper");
		sessao.removeAttribute("collectionParcelamentoDescontoInatividade");
		sessao.removeAttribute("collectionParcelamentoDescontoAntiguidade");
		sessao.removeAttribute("idRegistroAtualizacao");
		sessao.removeAttribute("collectionParcelamentoQuantidadeReparcelamentoHelperLinhaRemovidas");
		sessao.removeAttribute("collectionParcelamentoDescontoInatividadeLinhaRemovidas");
		sessao.removeAttribute("collectionParcelamentoDescontoAntiguidadeLinhaRemovidas");
		sessao.removeAttribute("collectionParcelamentoQuantidadePrestacaoHelperLinhaRemovidas");
		sessao.removeAttribute("collectionParcelamentoQuantidadePrestacaoHelper");

		// Monta a página de sucesso
		// if (retorno.getName().equalsIgnoreCase("telaSucesso")) {
		// montarPaginaSucesso(httpServletRequest,
		// "Perfil de Parcelamento da RD de número " + numeroResolucaoDiretoria +
		// " atualizado com sucesso.",
		// "Realizar outra Manutenção de Perfil de Parcelamento",
		// "exibirFiltrarPerfilParcelamentoAction.do?desfazer=S");
		// }

		if(retorno.getName().equalsIgnoreCase("telaSucesso")){
			montarPaginaSucesso(httpServletRequest, "Perfil de Parcelamento da RD de número " + numeroResolucaoDiretoria
							+ " atualizado com sucesso.", "Realizar outra Manutenção de Perfil de Parcelamento",
							"exibirFiltrarPerfilParcelamentoAction.do?menu=sim&desfazer=S");
		}

		return retorno;
	}

	private void validacaoFinal(String numeroResolucaoDiretoria, String imovelSituacaoTipo, String imovelPerfil, String subcategoria,
					String percentualDescontoAcrescimo, HttpServletRequest httpServletRequest,
					Collection collectionParcelamentoQuantidadeReparcelamentoHelper, Collection collectionParcelamentoDescontoAntiguidade,
					Collection collectionParcelamentoDescontoInatividade, BigDecimal valorMinimoDebitoAParcelarFaixaDebitoBigDecimal,
					BigDecimal valorMaximoDebitoAParcelarFaixaDebitoBigDecimal, ParcelamentoPerfil parcelamentoPerfilNaBase){

		Integer idIimovelPerfil = null;

		if(!Util.isVazioOuBranco(imovelPerfil)){
			idIimovelPerfil = Integer.valueOf(imovelPerfil);
		}

		Integer idSubcategoria = null;
		if(!Util.isVazioOuBranco(subcategoria)){
			idSubcategoria = Integer.valueOf(subcategoria);
		}

		Collection colecaoParcelamentoPerfil = Fachada.getInstancia().pesquisarExstenciaPerfilParcelamento(
						parcelamentoPerfilNaBase.getId(), parcelamentoPerfilNaBase.getResolucaoDiretoria().getId(),
						Integer.valueOf(imovelSituacaoTipo), idIimovelPerfil, idSubcategoria,
						valorMinimoDebitoAParcelarFaixaDebitoBigDecimal, valorMaximoDebitoAParcelarFaixaDebitoBigDecimal, "ALTERAR");

		if(colecaoParcelamentoPerfil != null && !colecaoParcelamentoPerfil.isEmpty()){
			throw new ActionServletException("atencao.perfil_parcelamento_ja_existe");
		}

		if(collectionParcelamentoQuantidadeReparcelamentoHelper == null || collectionParcelamentoQuantidadeReparcelamentoHelper.isEmpty()){
			// Informe os dados de Reparcelamento Consecutivo
			throw new ActionServletException("atencao.required", null, " Reparcelamento Consecutivo");

		}
		Iterator iterator = collectionParcelamentoQuantidadeReparcelamentoHelper.iterator();

		while(iterator.hasNext()){

			ParcelamentoQuantidadeReparcelamentoHelper parcelamentoQuantidadeReparcelamentoHelper = (ParcelamentoQuantidadeReparcelamentoHelper) iterator
							.next();

			Collection collectionParcelamentoQuantidadePrestacaoHelper = parcelamentoQuantidadeReparcelamentoHelper
							.getCollectionParcelamentoQuantidadePrestacaoHelper();

			if(collectionParcelamentoQuantidadePrestacaoHelper == null || collectionParcelamentoQuantidadePrestacaoHelper.isEmpty()){

				// Informações do Parcelamento por Quantidade de Reparcelamentos deve ser informado
				throw new ActionServletException("atencao.campo.informado", null,
								"Informações do Parcelamento por Quantidade de Reparcelamentos");
			}else{
				validarCollectionParcelamentoQuantidadePrestacaoHelper(collectionParcelamentoQuantidadePrestacaoHelper);
			}
		}

		if(collectionParcelamentoDescontoAntiguidade != null && !collectionParcelamentoDescontoAntiguidade.isEmpty()){

			Iterator iteratorParcelamentoDescontoAntiguidade = collectionParcelamentoDescontoAntiguidade.iterator();

			while(iteratorParcelamentoDescontoAntiguidade.hasNext()){

				ParcelamentoDescontoAntiguidade parcelamentoDescontoAntiguidade = (ParcelamentoDescontoAntiguidade) iteratorParcelamentoDescontoAntiguidade
								.next();

				if(parcelamentoDescontoAntiguidade.getPercentualDescontoSemRestabelecimento() == null){
					// Percentual de Desconto Sem Restabelecimento
					throw new ActionServletException("atencao.required", null, "  Percentual de Desconto Sem Restabelecimento");

				}

				if(parcelamentoDescontoAntiguidade.getPercentualDescontoComRestabelecimento() == null){
					// Informe Percentual de Desconto Com Restabelecimento
					throw new ActionServletException("atencao.required", null, "  Percentual de Desconto Com Restabelecimento");

				}

				if(parcelamentoDescontoAntiguidade.getPercentualDescontoAtivo() == null){
					// Informe Percentual de Desconto Ativo
					throw new ActionServletException("atencao.required", null, "  Percentual de Desconto Ativo");

				}
			}
		}

		if(collectionParcelamentoDescontoInatividade != null && !collectionParcelamentoDescontoInatividade.isEmpty()){

			Iterator iteratorParcelamentoDescontoInatividade = collectionParcelamentoDescontoInatividade.iterator();

			while(iteratorParcelamentoDescontoInatividade.hasNext()){

				ParcelamentoDescontoInatividade parcelamentoDescontoInatividade = (ParcelamentoDescontoInatividade) iteratorParcelamentoDescontoInatividade
								.next();

				if(parcelamentoDescontoInatividade.getPercentualDescontoSemRestabelecimento() == null){
					// Percentual de Desconto Sem Restabelecimento
					throw new ActionServletException("atencao.required", null, "  Percentual de Desconto Sem Restabelecimento");
				}

				if(parcelamentoDescontoInatividade.getPercentualDescontoComRestabelecimento() == null){
					// Informe Percentual de Desconto Com Restabelecimento
					throw new ActionServletException("atencao.required", null, "  Percentual de Desconto Com Restabelecimento");

				}

				// [FS0010] - Verificar Percentual Máximo
				BigDecimal percentualDescontoJurosMoraSemRestabelecimento = parcelamentoDescontoInatividade
								.getPercentualDescontoJurosMoraSemRestabelecimento();
				if(percentualDescontoJurosMoraSemRestabelecimento != null
								&& percentualDescontoJurosMoraSemRestabelecimento.compareTo(new BigDecimal(100)) > 0){
					// Caso o percentual informado seja maior que 100%, exibir a mensagem
					// "Percentual não pode ser maior que 100%"
					throw new ActionServletException("atencao.percentual.invalido.maximo", null,
									"Percentual de Desconto de Juros Mora Sem Restabelecimento");
				}

				// [FS0010] - Verificar Percentual Máximo
				BigDecimal percentualDescontoMultaSemRestabelecimento = parcelamentoDescontoInatividade
								.getPercentualDescontoMultaSemRestabelecimento();
				if(percentualDescontoMultaSemRestabelecimento != null
								&& percentualDescontoMultaSemRestabelecimento.compareTo(new BigDecimal(100)) > 0){
					// Caso o percentual informado seja maior que 100%, exibir a mensagem
					// "Percentual não pode ser maior que 100%"
					throw new ActionServletException("atencao.percentual.invalido.maximo", null,
									"Percentual de Desconto de Multa Sem Restabelecimento");
				}

				// [FS0010] - Verificar Percentual Máximo
				BigDecimal percentualDescontoJurosMoraComRestabelecimento = parcelamentoDescontoInatividade
								.getPercentualDescontoJurosMoraComRestabelecimento();
				if(percentualDescontoJurosMoraComRestabelecimento != null
								&& percentualDescontoJurosMoraComRestabelecimento.compareTo(new BigDecimal(100)) > 0){
					// Caso o percentual informado seja maior que 100%, exibir a mensagem
					// "Percentual não pode ser maior que 100%"
					throw new ActionServletException("atencao.percentual.invalido.maximo", null,
									"Percentual de Desconto de Juros Mora Com Restabelecimento");
				}

				// [FS0010] - Verificar Percentual Máximo
				BigDecimal percentualDescontoMultaComRestabelecimento = parcelamentoDescontoInatividade
								.getPercentualDescontoMultaComRestabelecimento();
				if(percentualDescontoMultaComRestabelecimento != null
								&& percentualDescontoMultaComRestabelecimento.compareTo(new BigDecimal(100)) > 0){
					// Caso o percentual informado seja maior que 100%, exibir a mensagem
					// "Percentual não pode ser maior que 100%"
					throw new ActionServletException("atencao.percentual.invalido.maximo", null,
									"Percentual de Desconto de Multa Com Restabelecimento");
				}

			}
		}
	}

	private void atualizaColecoesNaSessao(HttpSession sessao, HttpServletRequest httpServletRequest){

		// collectionParcelamentoDescontoAntiguidade
		if(sessao.getAttribute("collectionParcelamentoDescontoAntiguidade") != null
						&& !sessao.getAttribute("collectionParcelamentoDescontoAntiguidade").equals("")){

			Collection collectionParcelamentoDescontoAntiguidade = (Collection) sessao
							.getAttribute("collectionParcelamentoDescontoAntiguidade");

			// cria as variáveis para recuperar os parâmetros do request e jogar no objeto
			// ParcelamentoDescontoAntiguidade
			String vlSemRestAntiguidade = null;
			String vlComRestAntiguidade = null;
			String vlDescontoAtivo = null;

			Iterator iteratorParcelamentoDescontoAntiguidade = collectionParcelamentoDescontoAntiguidade.iterator();

			while(iteratorParcelamentoDescontoAntiguidade.hasNext()){

				ParcelamentoDescontoAntiguidade parcelamentoDescontoAntiguidade = (ParcelamentoDescontoAntiguidade) iteratorParcelamentoDescontoAntiguidade
								.next();
				long valorTempo = parcelamentoDescontoAntiguidade.getUltimaAlteracao().getTime();
				vlSemRestAntiguidade = httpServletRequest.getParameter("vlSemRestAntiguidade" + valorTempo);
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

			// cria as variáveis para recuperar os parâmetros do request e jogar no objeto
			// ParcelamentoDescontoInatividade
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
				vlSemRestInatividade = httpServletRequest.getParameter("vlSemRestInatividade" + valorTempo);
				vlJurosMoraSemRestInatividade = httpServletRequest.getParameter("vlJurosMoraSemRestInatividade" + valorTempo);
				vlMultaSemRestInatividade = httpServletRequest.getParameter("vlMultaSemRestInatividade" + valorTempo);
				vlComRestInatividade = httpServletRequest.getParameter("vlComRestInatividade" + valorTempo);
				vlJurosMoraComRestInatividade = httpServletRequest.getParameter("vlJurosMoraComRestInatividade" + valorTempo);
				vlMultaComRestInatividade = httpServletRequest.getParameter("vlMultaComRestInatividade" + valorTempo);

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

	/**
	 * @author isilva
	 * @param inserirPrestacoesParcelamentoPerfilActionForm
	 */
	private void validarDadosParcelamentoPerfil(AtualizarParcelamentoPerfilActionForm atualizarParcelamentoPerfilActionForm){

		int qtdInformada = 0;

		BigDecimal percentualTarifaMinimaPrestacao = null;
		BigDecimal percentualValorDebitoCalculoValorMinimoPrestacao = null;
		BigDecimal valorDebitoPrestacao = null;

		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getPercentualTarifaMinimaPrestacao())){
			qtdInformada++;
			percentualTarifaMinimaPrestacao = Util.formatarStringMoedaRealparaBigDecimal(atualizarParcelamentoPerfilActionForm
							.getPercentualTarifaMinimaPrestacao().toString(), 2);
		}

		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getPercentualValorDebitoCalculoValorMinimoPrestacao())){
			qtdInformada++;
			percentualValorDebitoCalculoValorMinimoPrestacao = Util.formatarStringMoedaRealparaBigDecimal(
							atualizarParcelamentoPerfilActionForm.getPercentualValorDebitoCalculoValorMinimoPrestacao().toString(), 2);
		}

		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getValorDebitoPrestacao())){
			qtdInformada++;
			valorDebitoPrestacao = Util.formatarStringMoedaRealparaBigDecimal(atualizarParcelamentoPerfilActionForm
							.getValorDebitoPrestacao().toString(), 2);
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

		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getPercentualValorDebitoCalculoValorMinimoPrestacao())
						&& Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm
										.getIndicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima())){
			throw new ActionServletException("atencao.required", null, "Informe Opções da Prestação Mínima");
		}
	}

	/**
	 * @author isilva
	 * @param collectionParcelamentoQuantidadePrestacaoHelper
	 */
	private void validarCollectionParcelamentoQuantidadePrestacaoHelper(Collection collectionParcelamentoQuantidadePrestacaoHelper){

		// collectionParcelamentoQuantidadePrestacaoHelper
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

					if(parcelamentoQuantidadePrestacao.getTaxaJuros() == null){
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

}
