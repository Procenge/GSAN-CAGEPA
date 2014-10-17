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
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action de Exibir Atualizar Perfil de Parcelamento
 * 
 * @author Vivianne Sousa
 * @created 12/05/2006
 */

public class ExibirAtualizarPerfilParcelamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Inicializando Variaveis
		ActionForward retorno = actionMapping.findForward("atualizarPerfilParcelamento");
		AtualizarParcelamentoPerfilActionForm atualizarParcelamentoPerfilActionForm = (AtualizarParcelamentoPerfilActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// volta da msg de Perfil de Parcelamento já utilizado, não pode ser alterado nem excluído.
		String confirmado = httpServletRequest.getParameter("confirmado");

		if(httpServletRequest.getParameter("adicionarReparcelamentosConsecutivos") != null){

			if(httpServletRequest.getParameter("primeiraVez") != null){

				sessao.setAttribute("primeiraVez", httpServletRequest.getParameter("primeiraVez"));
			}else{
				sessao.removeAttribute("primeiraVez");
			}

			if(httpServletRequest.getParameter("qtdeMaximaReparcelamento") != null){

				sessao.setAttribute("qtdeMaximaReparcelamento", httpServletRequest.getParameter("qtdeMaximaReparcelamento"));
			}else{

				sessao.removeAttribute("qtdeMaximaReparcelamento");
			}

			if(httpServletRequest.getParameter("adicionarReparcelamento") != null){

				sessao.setAttribute("adicionarReparcelamento", httpServletRequest.getParameter("adicionarReparcelamento"));
			}else{

				sessao.removeAttribute("adicionarReparcelamento");
			}

			if(httpServletRequest.getParameter("desabilitarTaxaJuros") != null){

				sessao.setAttribute("desabilitarTaxaJuros", httpServletRequest.getParameter("desabilitarTaxaJuros"));
			}else{

				sessao.removeAttribute("desabilitarTaxaJuros");
			}

			retorno = actionMapping.findForward("inserirPrestacoesParcelamentoPerfil");
		}

		String idPerfilParcelamento = null;
		if(httpServletRequest.getParameter("reload") == null || httpServletRequest.getParameter("reload").equalsIgnoreCase("")
						&& (confirmado == null || confirmado.equals(""))){
			// Recupera o id do PerfilParcelamento que vai ser atualizado

			if(httpServletRequest.getParameter("idRegistroInseridoAtualizar") != null){
				idPerfilParcelamento = httpServletRequest.getParameter("idRegistroInseridoAtualizar");
				// Definindo a volta do botão Voltar p Filtrar Perfil de Parcelamento
				httpServletRequest.setAttribute("voltar", "filtrar");
				sessao.setAttribute("idRegistroAtualizacao", idPerfilParcelamento);
			}else if(httpServletRequest.getParameter("idRegistroAtualizacao") == null){
				idPerfilParcelamento = (String) sessao.getAttribute("idRegistroAtualizacao");
				// Definindo a volta do botão Voltar p Filtrar Perfil de Parcelamento
				httpServletRequest.setAttribute("voltar", "filtrar");
			}else if(httpServletRequest.getParameter("idRegistroAtualizacao") != null){
				idPerfilParcelamento = httpServletRequest.getParameter("idRegistroAtualizacao");
				// Definindo a volta do botão Voltar p Manter Perfil de Parcelamento
				httpServletRequest.setAttribute("voltar", "manter");
				sessao.setAttribute("idRegistroAtualizacao", idPerfilParcelamento);
			}
		}else{
			idPerfilParcelamento = (String) sessao.getAttribute("idRegistroAtualizacao");
		}

		FiltroSistemaParametro filtroSistemaParametro = new FiltroSistemaParametro();

		Collection colecaoSistemaParametros = fachada.pesquisar(filtroSistemaParametro, SistemaParametro.class.getName());

		SistemaParametro sistemaParametro = (SistemaParametro) colecaoSistemaParametros.iterator().next();
		String percentualMaximoAbatimento = "" + sistemaParametro.getPercentualMaximoAbatimento();
		httpServletRequest.setAttribute("percentualMaximoAbatimento", percentualMaximoAbatimento);

		// Verifica se o usuário está selecionando o Perfil de Parcelamento da página de manter
		// Caso contrário o usuário está teclando enter na página de atualizar
		if((idPerfilParcelamento != null && !idPerfilParcelamento.equals(""))
						&& (httpServletRequest.getParameter("desfazer") == null)
						&& (httpServletRequest.getParameter("reload") == null || httpServletRequest.getParameter("reload")
										.equalsIgnoreCase(""))){
			exibirPerfilParcelamento(idPerfilParcelamento, atualizarParcelamentoPerfilActionForm, fachada, sessao, httpServletRequest);

		}

		if(httpServletRequest.getParameter("adicionarParcelamentoQtdePerfil") != null
						&& httpServletRequest.getParameter("adicionarParcelamentoQtdePerfil").equalsIgnoreCase("S")
						&& atualizarParcelamentoPerfilActionForm.getQtdeMaximaReparcelamento() != null){

			// -------------- bt adicionarParcelamentoQtdePerfil ---------------

			atualizaColecoesNaSessao(sessao, httpServletRequest);

			httpServletRequest.removeAttribute("adicionarParcelamentoQtdePerfil");

		}

		if(httpServletRequest.getParameter("adicionarParcelamentoDescontoAntiguidade") != null
						&& httpServletRequest.getParameter("adicionarParcelamentoDescontoAntiguidade").equalsIgnoreCase("S")
						&& !atualizarParcelamentoPerfilActionForm.getQuantidadeMinimaMesesDebito().equalsIgnoreCase("")){

			// ------------------ bt adicionarParcelamentoDescontoAntiguidade
			// ------------------------

			atualizaColecoesNaSessao(sessao, httpServletRequest);

			adicionarParcelamentoDescontoAntiguidade(atualizarParcelamentoPerfilActionForm, sessao, fachada);

			httpServletRequest.removeAttribute("adicionarParcelamentoDescontoAntiguidade");
		}

		if(httpServletRequest.getParameter("adicionarParcelamentoDescontoInatividade") != null
						&& httpServletRequest.getParameter("adicionarParcelamentoDescontoInatividade").equalsIgnoreCase("S")
						&& !atualizarParcelamentoPerfilActionForm.getQuantidadeMaximaMesesInatividade().equalsIgnoreCase("")){

			// ------------------ bt adicionarParcelamentoDescontoInatividade
			// ------------------------

			atualizaColecoesNaSessao(sessao, httpServletRequest);

			adicionarParcelamentoDescontoInatividade(atualizarParcelamentoPerfilActionForm, sessao, fachada);

			httpServletRequest.removeAttribute("adicionarParcelamentoDescontoInatividade");
		}

		if(httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer").equalsIgnoreCase("S")){

			// -------------- bt DESFAZER ---------------
			exibirPerfilParcelamento(idPerfilParcelamento, atualizarParcelamentoPerfilActionForm, fachada, sessao, httpServletRequest);
			sessao.removeAttribute("collectionParcelamentoQuantidadeReparcelamentoHelperLinhaRemovidas");
			sessao.removeAttribute("collectionParcelamentoDescontoInatividadeLinhaRemovidas");
			sessao.removeAttribute("collectionParcelamentoDescontoAntiguidadeLinhaRemovidas");
			sessao.removeAttribute("collectionParcelamentoQuantidadePrestacaoHelperLinhaRemovidas");
		}

		sessao.removeAttribute("caminhoRetornoTelaPesquisa");
		// DEFINIÇÃO QUE IRÁ AUXILIAR O RETORNO DOS POPUPS
		sessao.setAttribute("UseCase", "ATUALIZARPERFIL");

		// TODO Isilva - Depois ver isso...
		httpServletRequest.setAttribute("reloadPage", "ATUALIZARPERFIL");

		if(sessao.getAttribute("collectionParcelamentoQuantidadeReparcelamentoHelper") == null
						|| ((Collection) sessao.getAttribute("collectionParcelamentoQuantidadeReparcelamentoHelper")).size() == 0){
			sessao.setAttribute("collectionParcelamentoQuantidadeReparcelamentoHelperVazia", "1");
		}else{
			sessao.setAttribute("collectionParcelamentoQuantidadeReparcelamentoHelperVazia", "2");
		}

		if((!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getIndicadorPermitirCobrancaParcelamentoPorGuia()) && atualizarParcelamentoPerfilActionForm
						.getIndicadorPermitirCobrancaParcelamentoPorGuia().equals(ConstantesSistema.NAO))
						|| (!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getIndicadorPermitirInformarNumeroValorParcelas()) && atualizarParcelamentoPerfilActionForm
										.getIndicadorPermitirInformarNumeroValorParcelas().equals(ConstantesSistema.NAO))){

			sessao.removeAttribute("desabilitarTaxaJuros");
		}else if((!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getIndicadorPermitirCobrancaParcelamentoPorGuia()) && atualizarParcelamentoPerfilActionForm
						.getIndicadorPermitirCobrancaParcelamentoPorGuia().equals(ConstantesSistema.SIM))
						&& (!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getIndicadorPermitirInformarNumeroValorParcelas()) && atualizarParcelamentoPerfilActionForm
										.getIndicadorPermitirInformarNumeroValorParcelas().equals(ConstantesSistema.SIM))){

			sessao.setAttribute("desabilitarTaxaJuros", "true");
		}

		return retorno;

	}

	private void exibirPerfilParcelamento(String idPerfilParcelamento,
					AtualizarParcelamentoPerfilActionForm atualizarParcelamentoPerfilActionForm, Fachada fachada, HttpSession sessao,
					HttpServletRequest httpServletRequest){

		// Cria a variável que vai armazenar o ParcelamentoPerfil para ser atualizado
		ParcelamentoPerfil parcelamentoPerfil = null;

		// Cria o filtro de ParcelamentoPerfil e seta o id do ParcelamentoPerfil para ser atualizado
		// no filtro e indica quais objetos devem ser retornados pela pesquisa
		FiltroParcelamentoPerfil filtroParcelamentoPerfil = new FiltroParcelamentoPerfil();
		filtroParcelamentoPerfil.adicionarParametro(new ParametroSimples(FiltroParcelamentoPerfil.ID, idPerfilParcelamento));

		filtroParcelamentoPerfil.adicionarCaminhoParaCarregamentoEntidade("resolucaoDiretoria");
		filtroParcelamentoPerfil.adicionarCaminhoParaCarregamentoEntidade("imovelSituacaoTipo");
		filtroParcelamentoPerfil.adicionarCaminhoParaCarregamentoEntidade("imovelPerfil");
		filtroParcelamentoPerfil.adicionarCaminhoParaCarregamentoEntidade("subcategoria");

		Collection<ParcelamentoPerfil> collectionParcelamentoPerfil = fachada.pesquisar(filtroParcelamentoPerfil, ParcelamentoPerfil.class
						.getName());

		// Caso a pesquisa tenha retornado o ParcelamentoPerfil
		if(collectionParcelamentoPerfil != null && !collectionParcelamentoPerfil.isEmpty()){

			// Recupera da coleção o ParcelamentoPerfil que vai ser atualizado
			parcelamentoPerfil = (ParcelamentoPerfil) Util.retonarObjetoDeColecao(collectionParcelamentoPerfil);

			// Seta no form os dados de ParcelamentoPerfil

			atualizarParcelamentoPerfilActionForm.setNumeroResolucaoDiretoria(""
							+ parcelamentoPerfil.getResolucaoDiretoria().getNumeroResolucaoDiretoria());
			atualizarParcelamentoPerfilActionForm.setImovelSituacaoTipo(""
							+ parcelamentoPerfil.getImovelSituacaoTipo().getDescricaoImovelSituacaoTipo());

			if(parcelamentoPerfil.getSubcategoria() != null && !parcelamentoPerfil.getSubcategoria().equals("")){
				atualizarParcelamentoPerfilActionForm.setSubcategoria("" + parcelamentoPerfil.getSubcategoria().getDescricao());
			}else{
				atualizarParcelamentoPerfilActionForm.setSubcategoria("");
			}

			if(parcelamentoPerfil.getImovelPerfil() != null && !parcelamentoPerfil.getImovelPerfil().equals("")){
				atualizarParcelamentoPerfilActionForm.setImovelPerfil("" + parcelamentoPerfil.getImovelPerfil().getDescricao());
			}else{
				atualizarParcelamentoPerfilActionForm.setImovelPerfil("");
			}

			// atualizarParcelamentoPerfilActionForm.setUltimaAlteracao("" +
			// parcelamentoPerfil.getUltimaAlteracao());

			if(httpServletRequest.getParameter("reload") == null || httpServletRequest.getParameter("reload").equalsIgnoreCase("")){

				if(parcelamentoPerfil.getPercentualDescontoAcrescimo() != null){
					atualizarParcelamentoPerfilActionForm.setPercentualDescontoAcrescimo(""
									+ Util.formatarMoedaReal(parcelamentoPerfil.getPercentualDescontoAcrescimo()));
				}

				if(parcelamentoPerfil.getPercentualTarifaMinimaPrestacao() != null){
					atualizarParcelamentoPerfilActionForm.setPercentualTarifaMinimaPrestacao(""
									+ Util.formatarMoedaReal(parcelamentoPerfil.getPercentualTarifaMinimaPrestacao()));
				}
				if(parcelamentoPerfil.getValorDebitoPrestacao() != null){
					atualizarParcelamentoPerfilActionForm.setValorDebitoPrestacao(Util.formatarMoedaReal(parcelamentoPerfil
									.getValorDebitoPrestacao()));
				}

				atualizarParcelamentoPerfilActionForm.setIndicadorParcelarChequeDevolvido(""
								+ parcelamentoPerfil.getIndicadorChequeDevolvido());
				atualizarParcelamentoPerfilActionForm.setIndicadorParcelarSancoesMaisDeUmaConta(""
								+ parcelamentoPerfil.getIndicadorSancoesUnicaConta());

				if(parcelamentoPerfil.getNumeroConsumoMinimo() != null && !parcelamentoPerfil.getNumeroConsumoMinimo().equals("")){
					atualizarParcelamentoPerfilActionForm.setConsumoMinimo("" + parcelamentoPerfil.getNumeroConsumoMinimo());
				}else{
					atualizarParcelamentoPerfilActionForm.setConsumoMinimo("");
				}
				if(parcelamentoPerfil.getPercentualVariacaoConsumoMedio() != null
								&& !parcelamentoPerfil.getPercentualVariacaoConsumoMedio().equals("")){
					atualizarParcelamentoPerfilActionForm.setPercentualVariacaoConsumoMedio(Util.formatarMoedaReal(parcelamentoPerfil
									.getPercentualVariacaoConsumoMedio()));
				}else{
					atualizarParcelamentoPerfilActionForm.setPercentualVariacaoConsumoMedio("");
				}

				if(!Util.isVazioOuBranco(parcelamentoPerfil.getValorMinimoDebitoAParcelarFaixaDebito())){
					atualizarParcelamentoPerfilActionForm.setValorMinimoDebitoAParcelarFaixaDebito(Util
									.formatarMoedaReal(parcelamentoPerfil.getValorMinimoDebitoAParcelarFaixaDebito()));
				}else{
					atualizarParcelamentoPerfilActionForm.setValorMinimoDebitoAParcelarFaixaDebito("");
				}

				if(!Util.isVazioOuBranco(parcelamentoPerfil.getValorMaximoDebitoAParcelarFaixaDebito())){
					atualizarParcelamentoPerfilActionForm.setValorMaximoDebitoAParcelarFaixaDebito(Util
									.formatarMoedaReal(parcelamentoPerfil.getValorMaximoDebitoAParcelarFaixaDebito()));
				}else{
					atualizarParcelamentoPerfilActionForm.setValorMaximoDebitoAParcelarFaixaDebito("");
				}

				if(!Util.isVazioOuBranco(parcelamentoPerfil.getIndicadorDebitoOriginalOuAtualizadoFaixaDebito())){
					atualizarParcelamentoPerfilActionForm.setIndicadorDebitoOriginalOuAtualizadoFaixaDebito(parcelamentoPerfil
									.getIndicadorDebitoOriginalOuAtualizadoFaixaDebito().toString());
				}

				if(!Util.isVazioOuBranco(parcelamentoPerfil.getPercentualValorDebitoCalculoValorMinimoPrestacao())){
					atualizarParcelamentoPerfilActionForm.setPercentualValorDebitoCalculoValorMinimoPrestacao(Util
									.formatarMoedaReal(parcelamentoPerfil.getPercentualValorDebitoCalculoValorMinimoPrestacao()));
				}

				if(!Util.isVazioOuBranco(parcelamentoPerfil.getIndicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima())){
					atualizarParcelamentoPerfilActionForm.setIndicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima(parcelamentoPerfil
									.getIndicadorOpcoesDebitoOriginalOuAtualizadoPrestacaoMinima().toString());
				}

				if(!Util.isVazioOuBranco(parcelamentoPerfil.getNumeroConsumoEconomia())){
					atualizarParcelamentoPerfilActionForm
									.setNumeroConsumoEconomia(parcelamentoPerfil.getNumeroConsumoEconomia().toString());
				}

				if(!Util.isVazioOuBranco(parcelamentoPerfil.getNumeroAreaConstruida())){
					atualizarParcelamentoPerfilActionForm.setNumeroAreaConstruida(parcelamentoPerfil.getNumeroAreaConstruida().toString());
				}

				if(!Util.isVazioOuBranco(parcelamentoPerfil.getAnoMesReferenciaLimiteInferior())){
					atualizarParcelamentoPerfilActionForm.setAnoMesReferenciaLimiteInferior(Util
									.formatarAnoMesParaMesAno(parcelamentoPerfil.getAnoMesReferenciaLimiteInferior().toString()));
				}

				if(!Util.isVazioOuBranco(parcelamentoPerfil.getAnoMesReferenciaLimiteSuperior())){
					atualizarParcelamentoPerfilActionForm.setAnoMesReferenciaLimiteSuperior(Util
									.formatarAnoMesParaMesAno(parcelamentoPerfil.getAnoMesReferenciaLimiteSuperior().toString()));
				}

				if(!Util.isVazioOuBranco(parcelamentoPerfil.getPercentualDescontoAVista())){
					atualizarParcelamentoPerfilActionForm.setPercentualDescontoAVista(parcelamentoPerfil.getPercentualDescontoAVista()
									.toString());
				}

				if(!Util.isVazioOuBranco(parcelamentoPerfil.getParcelaQuantidadeMinimaFatura())){
					atualizarParcelamentoPerfilActionForm.setParcelaQuantidadeMinimaFatura(parcelamentoPerfil
									.getParcelaQuantidadeMinimaFatura().toString());
				}

				if(!Util.isVazioOuBranco(parcelamentoPerfil.getPercentualDescontoSancao())){
					atualizarParcelamentoPerfilActionForm.setPercentualDescontoSancao(parcelamentoPerfil.getPercentualDescontoSancao()
									.toString());
				}

				if(!Util.isVazioOuBranco(parcelamentoPerfil.getQuantidadeEconomias())){
					atualizarParcelamentoPerfilActionForm.setQuantidadeEconomias(parcelamentoPerfil.getQuantidadeEconomias().toString());
				}

				if(!Util.isVazioOuBranco(parcelamentoPerfil.getCapacidadeHidrometro())){
					atualizarParcelamentoPerfilActionForm.setCapacidadeHidrometro(parcelamentoPerfil.getCapacidadeHidrometro().toString());
				}

				// Cobrar acréscimos por impontualidades
				if(parcelamentoPerfil.getIndicadorCobrarAcrescimosPorImpontualidades() != null){

					atualizarParcelamentoPerfilActionForm.setIndicadorCobrarAcrescimosImpontualidade(parcelamentoPerfil
									.getIndicadorCobrarAcrescimosPorImpontualidades().toString());
				}else{

					atualizarParcelamentoPerfilActionForm.setIndicadorCobrarAcrescimosImpontualidade(ConstantesSistema.SIM.toString());
				}

				// Permitir a cobrança do parcelamento através de Guia de Pagamento
				if(parcelamentoPerfil.getIndicadorPermiteParcelamentoPorGuiaPagamento() != null){

					atualizarParcelamentoPerfilActionForm.setIndicadorPermitirCobrancaParcelamentoPorGuia(parcelamentoPerfil
									.getIndicadorPermiteParcelamentoPorGuiaPagamento().toString());
				}else{

					atualizarParcelamentoPerfilActionForm.setIndicadorPermitirCobrancaParcelamentoPorGuia(ConstantesSistema.SIM.toString());
				}

				if(atualizarParcelamentoPerfilActionForm.getIndicadorPermitirCobrancaParcelamentoPorGuia().equals(
								ConstantesSistema.SIM.toString())){

					atualizarParcelamentoPerfilActionForm.setIndicadorPermitirInformarNumeroValorParcelas(ConstantesSistema.SIM.toString());
				}else{

					atualizarParcelamentoPerfilActionForm.setIndicadorPermitirInformarNumeroValorParcelas(ConstantesSistema.NAO.toString());
				}

				// Cobrança de multa em caso de descumprimento de prestações
				if(parcelamentoPerfil.getNumeroPretacoesDescumpridasParaCobrancaMulta() != null){

					atualizarParcelamentoPerfilActionForm.setIndicadorCobrarMultaPorDescumprimentoPrestacao(ConstantesSistema.SIM
									.toString());

					atualizarParcelamentoPerfilActionForm.setNumeroPrestacacoesDescumpridasConsecutivas(parcelamentoPerfil
									.getNumeroPretacoesDescumpridasParaCobrancaMulta().toString());

					atualizarParcelamentoPerfilActionForm.setPercentualMultaPorPrestacacoesDescumpridas(Util.formatarMoedaReal(
									parcelamentoPerfil.getPercentualMultaPrestacaoDescumprida(), 2));
				}else{

					atualizarParcelamentoPerfilActionForm.setIndicadorCobrarMultaPorDescumprimentoPrestacao(ConstantesSistema.NAO
									.toString());
				}

			}

			atualizarParcelamentoPerfilActionForm.setUltimaAlteracao(Util.formatarDataComHora(parcelamentoPerfil.getUltimaAlteracao()));

			if(sessao.getAttribute("collectionParcelamentoQuantidadeReparcelamentoHelper") == null
							|| (httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer")
											.equalsIgnoreCase("S"))){
				// recupera a coleção de ParcelamentoQuantidadeReparcelamento
				// e tranforma numa coleção de ParcelamentoQuantidadeReparcelamentoHelper
				FiltroParcelamentoQuantidadeReparcelamento filtroParcelamentoQuantidadeReparcelamento = new FiltroParcelamentoQuantidadeReparcelamento();

				filtroParcelamentoQuantidadeReparcelamento.adicionarParametro(new ParametroSimples(
								FiltroParcelamentoDescontoAntiguidade.PARCELAMENTO_PERFIL, idPerfilParcelamento));

				Collection<ParcelamentoQuantidadeReparcelamento> collectionParcelamentoQuantidadeReparcelamento = fachada.pesquisar(
								filtroParcelamentoQuantidadeReparcelamento, ParcelamentoQuantidadeReparcelamento.class.getName());

				Iterator iterator = collectionParcelamentoQuantidadeReparcelamento.iterator();
				Collection collectionParcelamentoQuantidadeReparcelamentoHelper = new ArrayList();
				while(iterator.hasNext()){
					ParcelamentoQuantidadeReparcelamento parcelamentoQtdeReparcelamento = (ParcelamentoQuantidadeReparcelamento) iterator
									.next();

					ParcelamentoQuantidadeReparcelamentoHelper parcelamentoQtdeReparcelamentoHelper = new ParcelamentoQuantidadeReparcelamentoHelper();

					// recupera a coleção de ParcelamentoQuantidadePrestacao
					// e seta no objeto ParcelamentoQtdeReparcelamentoHelper
					FiltroParcelamentoQuantidadePrestacao filtroParcelamentoQuantidadePrestacao = new FiltroParcelamentoQuantidadePrestacao();
					filtroParcelamentoQuantidadePrestacao.adicionarParametro(new ParametroSimples(
									FiltroParcelamentoQuantidadePrestacao.PARCELAMENTO_QUANTIDADE_REPARCELAMENTO,
									parcelamentoQtdeReparcelamento.getId()));
					Collection<ParcelamentoQuantidadePrestacao> collectionParcelamentoQuantidadePrestacao = fachada.pesquisar(
									filtroParcelamentoQuantidadePrestacao, ParcelamentoQuantidadePrestacao.class.getName());

					// //////// n posso setar em CollectionParcelamentoQuantidadePrestacaoHelper uma
					// CollectionParcelamentoQuantidadePrestacao

					Collection collectionParcelamentoQuantidadePrestacaoHelper = new ArrayList();
					Iterator iteratorParcelamentoFaixaValor = collectionParcelamentoQuantidadePrestacao.iterator();

					while(iteratorParcelamentoFaixaValor.hasNext()){
						ParcelamentoQuantidadePrestacao parcelamentoQuantidadePrestacao = (ParcelamentoQuantidadePrestacao) iteratorParcelamentoFaixaValor
										.next();

						FiltroParcelamentoFaixaValor filtroParcelamentoFaixaValor = new FiltroParcelamentoFaixaValor();

						// filtroParcelamentoFaixaValor
						// .adicionarCaminhoParaCarregamentoEntidade("parcelamentoQuantidadePrestacao");
						filtroParcelamentoFaixaValor.adicionarParametro(new ParametroSimples(
										FiltroParcelamentoFaixaValor.PARCELAMENTO_QUANTIDADE_PRESTACAO, parcelamentoQuantidadePrestacao
														.getId()));
						filtroParcelamentoFaixaValor.setCampoOrderBy(FiltroParcelamentoFaixaValor.VALOR_FAIXA);

						Collection collectionParcelamentoFaixaValor = (Collection) fachada.pesquisar(filtroParcelamentoFaixaValor,
										ParcelamentoFaixaValor.class.getName());

						ParcelamentoQuantidadePrestacaoHelper parcelamentoQuantidadePrestacaoHelper = new ParcelamentoQuantidadePrestacaoHelper();

						parcelamentoQuantidadePrestacaoHelper.setParcelamentoQuantidadePrestacao(parcelamentoQuantidadePrestacao);
						parcelamentoQuantidadePrestacaoHelper.setCollectionParcelamentoFaixaValor(collectionParcelamentoFaixaValor);
						collectionParcelamentoQuantidadePrestacaoHelper.add(parcelamentoQuantidadePrestacaoHelper);
					}

					parcelamentoQtdeReparcelamentoHelper
									.setCollectionParcelamentoQuantidadePrestacaoHelper(collectionParcelamentoQuantidadePrestacaoHelper);

					parcelamentoQtdeReparcelamentoHelper.setId(parcelamentoQtdeReparcelamento.getId());
					parcelamentoQtdeReparcelamentoHelper.setQuantidadeMaximaReparcelamento(parcelamentoQtdeReparcelamento
									.getQuantidadeMaximaReparcelamento());
					// TODO ver ValorMinimoPrestacao
					// parcelamentoQtdeReparcelamentoHelper.setValorMinimoPrestacao(parcelamentoQtdeReparcelamento.getValorMinimoPrestacao());
					parcelamentoQtdeReparcelamentoHelper.setInformacaoParcelamentoQtdeReparcelamento("INFORMADAS");
					parcelamentoQtdeReparcelamentoHelper.setUltimaAlteracao(parcelamentoQtdeReparcelamento.getUltimaAlteracao());
					collectionParcelamentoQuantidadeReparcelamentoHelper.add(parcelamentoQtdeReparcelamentoHelper);

				}

				if(collectionParcelamentoQuantidadeReparcelamentoHelper != null
								&& !collectionParcelamentoQuantidadeReparcelamentoHelper.isEmpty()){
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
				}

				// httpServletRequest.setAttribute("collectionParcelamentoQuantidadeReparcelamentoHelper",collectionParcelamentoQuantidadeReparcelamentoHelper);
				sessao.setAttribute("collectionParcelamentoQuantidadeReparcelamentoHelper",
								collectionParcelamentoQuantidadeReparcelamentoHelper);

			}

			if(sessao.getAttribute("collectionParcelamentoDescontoAntiguidade") == null
							|| (httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer")
											.equalsIgnoreCase("S"))){
				// recupera a coleção de ParcelamentoDescontoAntiguidade
				FiltroParcelamentoDescontoAntiguidade filtroParcelamentoDescontoAntiguidade = new FiltroParcelamentoDescontoAntiguidade();

				filtroParcelamentoDescontoAntiguidade.adicionarParametro(new ParametroSimples(
								FiltroParcelamentoDescontoAntiguidade.PARCELAMENTO_PERFIL, idPerfilParcelamento));

				Collection<ParcelamentoDescontoAntiguidade> collectionParcelamentoDescontoAntiguidade = fachada.pesquisar(
								filtroParcelamentoDescontoAntiguidade, ParcelamentoDescontoAntiguidade.class.getName());
				sessao.setAttribute("collectionParcelamentoDescontoAntiguidade", collectionParcelamentoDescontoAntiguidade);
			}

			if(sessao.getAttribute("collectionParcelamentoDescontoInatividade") == null
							|| (httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer")
											.equalsIgnoreCase("S"))){
				// recupera a coleção de ParcelamentoDescontoInatividade
				FiltroParcelamentoDescontoInatividade filtroParcelamentoDescontoInatividade = new FiltroParcelamentoDescontoInatividade();

				filtroParcelamentoDescontoInatividade.adicionarParametro(new ParametroSimples(
								FiltroParcelamentoDescontoInatividade.PARCELAMENTO_PERFIL, idPerfilParcelamento));

				Collection<ParcelamentoDescontoInatividade> collectionParcelamentoDescontoInatividade = fachada.pesquisar(
								filtroParcelamentoDescontoInatividade, ParcelamentoDescontoInatividade.class.getName());
				sessao.setAttribute("collectionParcelamentoDescontoInatividade", collectionParcelamentoDescontoInatividade);
			}
		}

	}

	private void adicionarParcelamentoDescontoAntiguidade(AtualizarParcelamentoPerfilActionForm atualizarParcelamentoPerfilActionForm,
					HttpSession sessao, Fachada fachada){

		if(atualizarParcelamentoPerfilActionForm.getQuantidadeMinimaMesesDebito() == null
						|| atualizarParcelamentoPerfilActionForm.getQuantidadeMinimaMesesDebito().equalsIgnoreCase("")){
			throw new ActionServletException(
			// Informe Qtde. Mínima Meses de Débito p/ Desconto
							"atencao.required", null, " Qtde. Mínima Meses de Débito p/ Desconto");
		}else if(Util.validarValorNaoNumericoComoBigDecimal(atualizarParcelamentoPerfilActionForm.getQuantidadeMinimaMesesDebito())){
			throw new ActionServletException(
			// Qtde. Mínima Meses de Débito p/ Desconto deve ser numerico
							"atencao.integer", null, " Qtde. Mínima Meses de Débito p/ Desconto");
		}

		Collection<ParcelamentoDescontoAntiguidade> collectionParcelamentoDescontoAntiguidade = null;

		if(sessao.getAttribute("collectionParcelamentoDescontoAntiguidade") != null){
			collectionParcelamentoDescontoAntiguidade = (Collection) sessao.getAttribute("collectionParcelamentoDescontoAntiguidade");
		}else{
			collectionParcelamentoDescontoAntiguidade = new ArrayList();
		}

		Integer quantidadeMinimaMesesDebito = new Integer(atualizarParcelamentoPerfilActionForm.getQuantidadeMinimaMesesDebito());

		BigDecimal percentualDescontoSemRestabelecimento = null;
		if(atualizarParcelamentoPerfilActionForm.getPercentualDescontoSemRestabelecimentoAntiguidade() == null
						|| atualizarParcelamentoPerfilActionForm.getPercentualDescontoSemRestabelecimentoAntiguidade().equalsIgnoreCase("")){
			throw new ActionServletException(
			// Informe Percentual de Desconto Sem Restabelecimento
							"atencao.required", null, "  Percentual de Desconto Sem Restabelecimento");

		}else{
			percentualDescontoSemRestabelecimento = Util.formatarMoedaRealparaBigDecimal(atualizarParcelamentoPerfilActionForm
							.getPercentualDescontoSemRestabelecimentoAntiguidade());
		}

		BigDecimal percentualDescontoComRestabelecimento = null;
		if(atualizarParcelamentoPerfilActionForm.getPercentualDescontoComRestabelecimentoAntiguidade() == null
						|| atualizarParcelamentoPerfilActionForm.getPercentualDescontoComRestabelecimentoAntiguidade().equalsIgnoreCase("")){
			throw new ActionServletException(
			// Informe Percentual de Desconto Com Restabelecimento
							"atencao.required", null, "  Percentual de Desconto Com Restabelecimento");

		}else{
			percentualDescontoComRestabelecimento = Util.formatarMoedaRealparaBigDecimal(atualizarParcelamentoPerfilActionForm
							.getPercentualDescontoComRestabelecimentoAntiguidade());
		}

		BigDecimal percentualDescontoAtivo = null;
		if(atualizarParcelamentoPerfilActionForm.getPercentualDescontoAtivo() == null
						|| atualizarParcelamentoPerfilActionForm.getPercentualDescontoAtivo().equalsIgnoreCase("")){
			throw new ActionServletException(
			// Informe Percentual de Desconto Ativo
							"atencao.required", null, "  Percentual de Desconto Ativo");

		}else{
			percentualDescontoAtivo = Util.formatarMoedaRealparaBigDecimal(atualizarParcelamentoPerfilActionForm
							.getPercentualDescontoAtivo());
		}

		ParcelamentoDescontoAntiguidade parcelamentoDescontoAntiguidadeNovo = new ParcelamentoDescontoAntiguidade();

		if(collectionParcelamentoDescontoAntiguidade != null && !collectionParcelamentoDescontoAntiguidade.isEmpty()){
			// se coleção não estiver vazia verificar se a qtd máxima de prestações do parcelamento
			// ja foi informada
			ParcelamentoDescontoAntiguidade parcelamentoDescontoAntiguidadeAntigo = null;
			Iterator iterator = collectionParcelamentoDescontoAntiguidade.iterator();

			while(iterator.hasNext()){
				parcelamentoDescontoAntiguidadeAntigo = (ParcelamentoDescontoAntiguidade) iterator.next();

				// Verificar quantidade mínima meses de debito para desconto
				if(quantidadeMinimaMesesDebito.equals(parcelamentoDescontoAntiguidadeAntigo.getQuantidadeMinimaMesesDebito())){
					// Quantidade Mínima Meses de Debito para Desconto já informada
					throw new ActionServletException("atencao.qtde_minima_meses_debito_desconto_ja_informado");
				}
			}
		}

		parcelamentoDescontoAntiguidadeNovo.setQuantidadeMinimaMesesDebito(quantidadeMinimaMesesDebito);
		parcelamentoDescontoAntiguidadeNovo.setPercentualDescontoSemRestabelecimento(percentualDescontoSemRestabelecimento);
		parcelamentoDescontoAntiguidadeNovo.setPercentualDescontoComRestabelecimento(percentualDescontoComRestabelecimento);
		parcelamentoDescontoAntiguidadeNovo.setPercentualDescontoAtivo(percentualDescontoAtivo);
		parcelamentoDescontoAntiguidadeNovo.setUltimaAlteracao(new Date());

		collectionParcelamentoDescontoAntiguidade.add(parcelamentoDescontoAntiguidadeNovo);

		atualizarParcelamentoPerfilActionForm.setQuantidadeMinimaMesesDebito("");
		atualizarParcelamentoPerfilActionForm.setPercentualDescontoSemRestabelecimentoAntiguidade("");
		atualizarParcelamentoPerfilActionForm.setPercentualDescontoComRestabelecimentoAntiguidade("");
		atualizarParcelamentoPerfilActionForm.setPercentualDescontoAtivo("");

		// Ordena a coleção pela Qtde. Mínima Meses de Débito p/ Desconto
		Collections.sort((List) collectionParcelamentoDescontoAntiguidade, new Comparator() {

			public int compare(Object a, Object b){

				Integer valorMinPrestacao1 = new Integer(((ParcelamentoDescontoAntiguidade) a).getQuantidadeMinimaMesesDebito().toString());
				Integer valorMinPrestacao2 = new Integer(((ParcelamentoDescontoAntiguidade) b).getQuantidadeMinimaMesesDebito().toString());

				return valorMinPrestacao1.compareTo(valorMinPrestacao2);

			}
		});

		// manda para a sessão a coleção de ParcelamentoDescontoAntiguidade
		sessao.setAttribute("collectionParcelamentoDescontoAntiguidade", collectionParcelamentoDescontoAntiguidade);
	}

	private void adicionarParcelamentoDescontoInatividade(AtualizarParcelamentoPerfilActionForm atualizarParcelamentoPerfilActionForm,
					HttpSession sessao, Fachada fachada){

		if(atualizarParcelamentoPerfilActionForm.getQuantidadeMaximaMesesInatividade() == null
						|| atualizarParcelamentoPerfilActionForm.getQuantidadeMaximaMesesInatividade().equalsIgnoreCase("")){
			throw new ActionServletException(
			// Informe Qtde. Máxima Meses de Inatividade da Lig. de Água
							"atencao.required", null, " Qtde. Máxima Meses de Inatividade da Lig. de Água");
		}else if(Util.validarValorNaoNumericoComoBigDecimal(atualizarParcelamentoPerfilActionForm.getQuantidadeMaximaMesesInatividade())){
			throw new ActionServletException(
			// Qtde. Máxima Meses de Inatividade da Lig. de Água deve ser numerico
							"atencao.integer", null, " Qtde. Máxima Meses de Inatividade da Lig. de Água");
		}

		Collection<ParcelamentoDescontoInatividade> collectionParcelamentoDescontoInatividade = null;

		if(sessao.getAttribute("collectionParcelamentoDescontoInatividade") != null){
			collectionParcelamentoDescontoInatividade = (Collection) sessao.getAttribute("collectionParcelamentoDescontoInatividade");
		}else{
			collectionParcelamentoDescontoInatividade = new ArrayList();
		}

		Integer quantidadeMaximaMesesInatividade = new Integer(atualizarParcelamentoPerfilActionForm.getQuantidadeMaximaMesesInatividade());

		BigDecimal percentualDescontoSemRestabelecimento = null;
		if(atualizarParcelamentoPerfilActionForm.getPercentualDescontoSemRestabelecimentoInatividade() == null
						|| atualizarParcelamentoPerfilActionForm.getPercentualDescontoSemRestabelecimentoInatividade().equalsIgnoreCase("")){
			throw new ActionServletException(
			// Informe Percentual de Desconto Sem Restabelecimento
							"atencao.required", null, "  Percentual de Desconto Sem Restabelecimento");

		}else{
			percentualDescontoSemRestabelecimento = Util.formatarMoedaRealparaBigDecimal(atualizarParcelamentoPerfilActionForm
							.getPercentualDescontoSemRestabelecimentoInatividade());
		}

		BigDecimal percentualDescontoJurosMoraSemRestabelecimento = null;
		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getPercentualDescontoJurosMoraSemRestabelecimentoInatividade())){

			percentualDescontoJurosMoraSemRestabelecimento = Util.formatarMoedaRealparaBigDecimal(atualizarParcelamentoPerfilActionForm
							.getPercentualDescontoJurosMoraSemRestabelecimentoInatividade());

			// [FS0010] - Verificar Percentual Máximo
			if(percentualDescontoJurosMoraSemRestabelecimento.compareTo(new BigDecimal(100)) > 0){
				// Caso o percentual informado seja maior que 100%, exibir a mensagem
				// "Percentual não pode ser maior que 100%"
				throw new ActionServletException("atencao.percentual.invalido.maximo", null,
								"Percentual de Desconto de Juros Mora Sem Restabelecimento");
			}
		}

		BigDecimal percentualDescontoMultaSemRestabelecimento = null;
		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getPercentualDescontoMultaSemRestabelecimentoInatividade())){
			percentualDescontoMultaSemRestabelecimento = Util.formatarMoedaRealparaBigDecimal(atualizarParcelamentoPerfilActionForm
							.getPercentualDescontoMultaSemRestabelecimentoInatividade());

			// [FS0010] - Verificar Percentual Máximo
			if(percentualDescontoMultaSemRestabelecimento.compareTo(new BigDecimal(100)) > 0){
				// Caso o percentual informado seja maior que 100%, exibir a mensagem
				// "Percentual não pode ser maior que 100%"
				throw new ActionServletException("atencao.percentual.invalido.maximo", null,
								"Percentual de Desconto de Multa Sem Restabelecimento");
			}
		}

		BigDecimal percentualDescontoComRestabelecimento = null;
		if(atualizarParcelamentoPerfilActionForm.getPercentualDescontoComRestabelecimentoInatividade() == null
						|| atualizarParcelamentoPerfilActionForm.getPercentualDescontoComRestabelecimentoInatividade().equalsIgnoreCase("")){
			throw new ActionServletException(
			// Informe Percentual de Desconto Com Restabelecimento
							"atencao.required", null, "  Percentual de Desconto Com Restabelecimento");

		}else{
			percentualDescontoComRestabelecimento = Util.formatarMoedaRealparaBigDecimal(atualizarParcelamentoPerfilActionForm
							.getPercentualDescontoComRestabelecimentoInatividade());
		}

		BigDecimal percentualDescontoJurosMoraComRestabelecimento = null;
		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getPercentualDescontoJurosMoraComRestabelecimentoInatividade())){
			percentualDescontoJurosMoraComRestabelecimento = Util.formatarMoedaRealparaBigDecimal(atualizarParcelamentoPerfilActionForm
							.getPercentualDescontoJurosMoraComRestabelecimentoInatividade());

			// [FS0010] - Verificar Percentual Máximo
			if(percentualDescontoJurosMoraComRestabelecimento.compareTo(new BigDecimal(100)) > 0){
				// Caso o percentual informado seja maior que 100%, exibir a mensagem
				// "Percentual não pode ser maior que 100%"
				throw new ActionServletException("atencao.percentual.invalido.maximo", null,
								"Percentual de Desconto de Juros Mora Com Restabelecimento");
			}
		}

		BigDecimal percentualDescontoMultaComRestabelecimento = null;
		if(!Util.isVazioOuBranco(atualizarParcelamentoPerfilActionForm.getPercentualDescontoMultaComRestabelecimentoInatividade())){
			percentualDescontoMultaComRestabelecimento = Util.formatarMoedaRealparaBigDecimal(atualizarParcelamentoPerfilActionForm
							.getPercentualDescontoMultaComRestabelecimentoInatividade());

			// [FS0010] - Verificar Percentual Máximo
			if(percentualDescontoMultaComRestabelecimento.compareTo(new BigDecimal(100)) > 0){
				// Caso o percentual informado seja maior que 100%, exibir a mensagem
				// "Percentual não pode ser maior que 100%"
				throw new ActionServletException("atencao.percentual.invalido.maximo", null,
								"Percentual de Desconto de Multa Com Restabelecimento");
			}
		}

		ParcelamentoDescontoInatividade parcelamentoDescontoInatividadeNovo = new ParcelamentoDescontoInatividade();

		if(collectionParcelamentoDescontoInatividade != null && !collectionParcelamentoDescontoInatividade.isEmpty()){
			// se coleção não estiver vazia
			// verificar se a qtd máxima de prestações do parcelamento ja foi informada
			ParcelamentoDescontoInatividade parcelamentoDescontoInatividadeAntigo = null;
			Iterator iterator = collectionParcelamentoDescontoInatividade.iterator();

			while(iterator.hasNext()){
				parcelamentoDescontoInatividadeAntigo = (ParcelamentoDescontoInatividade) iterator.next();

				// [FS0003] Verificar quantidade mínima meses de debito
				if(quantidadeMaximaMesesInatividade.equals(parcelamentoDescontoInatividadeAntigo.getQuantidadeMaximaMesesInatividade())){
					// Quantidade Máxima Meses de Inatividade de Ligação de Água já informada
					throw new ActionServletException("atencao.qtde_maxima_meses_inatividade_ja_informado");
				}
			}
		}

		parcelamentoDescontoInatividadeNovo.setQuantidadeMaximaMesesInatividade(quantidadeMaximaMesesInatividade);
		parcelamentoDescontoInatividadeNovo.setPercentualDescontoSemRestabelecimento(percentualDescontoSemRestabelecimento);
		parcelamentoDescontoInatividadeNovo
						.setPercentualDescontoJurosMoraSemRestabelecimento(percentualDescontoJurosMoraSemRestabelecimento);
		parcelamentoDescontoInatividadeNovo.setPercentualDescontoMultaSemRestabelecimento(percentualDescontoMultaSemRestabelecimento);
		parcelamentoDescontoInatividadeNovo.setPercentualDescontoComRestabelecimento(percentualDescontoComRestabelecimento);
		parcelamentoDescontoInatividadeNovo
						.setPercentualDescontoJurosMoraComRestabelecimento(percentualDescontoJurosMoraComRestabelecimento);
		parcelamentoDescontoInatividadeNovo.setPercentualDescontoMultaComRestabelecimento(percentualDescontoMultaComRestabelecimento);
		parcelamentoDescontoInatividadeNovo.setUltimaAlteracao(new Date());

		collectionParcelamentoDescontoInatividade.add(parcelamentoDescontoInatividadeNovo);

		atualizarParcelamentoPerfilActionForm.setQuantidadeMaximaMesesInatividade("");
		atualizarParcelamentoPerfilActionForm.setPercentualDescontoSemRestabelecimentoInatividade("");
		atualizarParcelamentoPerfilActionForm.setPercentualDescontoJurosMoraSemRestabelecimentoInatividade("");
		atualizarParcelamentoPerfilActionForm.setPercentualDescontoMultaSemRestabelecimentoInatividade("");
		atualizarParcelamentoPerfilActionForm.setPercentualDescontoComRestabelecimentoInatividade("");
		atualizarParcelamentoPerfilActionForm.setPercentualDescontoJurosMoraComRestabelecimentoInatividade("");
		atualizarParcelamentoPerfilActionForm.setPercentualDescontoMultaComRestabelecimentoInatividade("");
		atualizarParcelamentoPerfilActionForm.setPercentualDescontoAtivo("");

		// Ordena a coleção pela Qtde. Máxima Meses de Inatividade da Lig. de Água
		Collections.sort((List) collectionParcelamentoDescontoInatividade, new Comparator() {

			public int compare(Object a, Object b){

				Integer valorMinPrestacao1 = new Integer(((ParcelamentoDescontoInatividade) a).getQuantidadeMaximaMesesInatividade()
								.toString());
				Integer valorMinPrestacao2 = new Integer(((ParcelamentoDescontoInatividade) b).getQuantidadeMaximaMesesInatividade()
								.toString());

				return valorMinPrestacao1.compareTo(valorMinPrestacao2);

			}
		});

		// manda para a sessão a coleção de ParcelamentoDescontoInatividade
		sessao.setAttribute("collectionParcelamentoDescontoInatividade", collectionParcelamentoDescontoInatividade);

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
					parcelamentoDescontoAntiguidade
									.setPercentualDescontoSemRestabelecimento(percentualDescontoSemRestabelecimentoAntiguidade);
				}

				BigDecimal percentualDescontoComRestabelecimentoAntiguidade = null;
				if(vlComRestAntiguidade != null && !vlComRestAntiguidade.equals("")){
					percentualDescontoComRestabelecimentoAntiguidade = Util.formatarMoedaRealparaBigDecimal(vlComRestAntiguidade);
					parcelamentoDescontoAntiguidade
									.setPercentualDescontoComRestabelecimento(percentualDescontoComRestabelecimentoAntiguidade);
				}

				BigDecimal percentualDescontoAtivoAntiguidade = null;
				if(vlDescontoAtivo != null && !vlDescontoAtivo.equals("")){
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
				// parcelamentoQuantidadePrestacao.getUltimaAlteracao()

				parcelamentoQuantidadePrestacaoHelper.setParcelamentoQuantidadePrestacao(parcelamentoQuantidadePrestacao);
			}
		}
	}

}