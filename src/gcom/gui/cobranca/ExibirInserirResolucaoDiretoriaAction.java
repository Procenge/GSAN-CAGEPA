/**
 * 
 */
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

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cobranca.FiltroResolucaoDiretoriaLayout;
import gcom.cobranca.ResolucaoDiretoriaLayout;
import gcom.cobranca.ResolucaoDiretoriaParametrosPagamentoAVista;
import gcom.cobranca.ResolucaoDiretoriaParametrosValorEntrada;
import gcom.cobranca.parcelamento.ParcelamentoSituacaoEspecial;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.FiltroGrupo;
import gcom.seguranca.acesso.Grupo;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

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

/**
 * Action que faz a exibição da tela para o usuário setar os campos e permitir
 * que ele insera uma resolução de diretoria [UC0217] Inserir Resolução de
 * Diretoria
 * 
 * @author Rafael Corrêa
 * @since 30/03/2006
 */
public class ExibirInserirResolucaoDiretoriaAction
				extends GcomAction {

	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirInserirResolucaoDiretoria");

		InserirResolucaoDiretoriaActionForm inserirResolucaoDiretoriaActionForm = (InserirResolucaoDiretoriaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		// Variável que indica quando o usuário fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		httpServletRequest.removeAttribute("idLocalidadeEncontrado");

		// Preenche o filtro de ResolucaoDiretoriaLayout (Termo de Confissão de Dívida)
		FiltroResolucaoDiretoriaLayout filtroResolucaoDiretoriaLayout = new FiltroResolucaoDiretoriaLayout();

		filtroResolucaoDiretoriaLayout.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoriaLayout.INDICADOR_USO, 1));

		filtroResolucaoDiretoriaLayout.setCampoOrderBy(FiltroResolucaoDiretoriaLayout.DESCRICAO);

		preencherColecao(filtroResolucaoDiretoriaLayout, ResolucaoDiretoriaLayout.class.getName(), "colecaoResolucaoDiretoriaLayout",
						sessao, inserirResolucaoDiretoriaActionForm, fachada, inserirResolucaoDiretoriaActionForm
										.getIdsResolucaoDiretoriaLayout(), ResolucaoDiretoriaLayout.class);

		// Preenche o filtro de Grupos de Acesso Disponíveis

		FiltroGrupo filtroGruposAcesso = new FiltroGrupo();

		filtroGruposAcesso.adicionarParametro(new ParametroSimples(FiltroGrupo.INDICADOR_USO, 1));

		filtroGruposAcesso.setCampoOrderBy(FiltroGrupo.DESCRICAO);

		preencherColecao(filtroGruposAcesso, Grupo.class.getName(), "colecaoGruposAcessoDisponiveis", sessao,
						inserirResolucaoDiretoriaActionForm, fachada, inserirResolucaoDiretoriaActionForm.getIdsGruposAcessoDisponiveis(),
						Grupo.class);

		// Adiciona Grupos de Acesso Selecionado à coleção de Grupos de Acesso Habilitados
		if(httpServletRequest.getParameter("adicionaGrupoAcesso") != null){

			// recupera grupos habilitados
			Collection colecaoGruposAcessoHabilitados = (Collection) sessao.getAttribute("colecaoGruposAcessoHabilitados");
			// recupera id do grupo disponível selecionado
			Integer idGrupoSelecionado = Integer.parseInt(inserirResolucaoDiretoriaActionForm.getIdsGruposAcessoDisponiveis()[0]);
			// recupera grupo disponíveis
			Collection colecaoGruposAcessoDisponiveis = (Collection) sessao.getAttribute("colecaoGruposAcessoDisponiveis");

			if(colecaoGruposAcessoHabilitados != null && colecaoGruposAcessoHabilitados.size() > 0){
				// verifica se grupo já está inserido na lista de habilitados
				for(Object gruposAcessoHabilitados : colecaoGruposAcessoHabilitados){
					Grupo grupoHabilitado = (Grupo) gruposAcessoHabilitados;
					if(grupoHabilitado.getId().equals(idGrupoSelecionado)){
						throw new ActionServletException("atencao.grupo_acesso_ja_associado_resolucao");
					}
				}
				// recupera e adiciona grupo disponível selecionado à lista de Habilitados
				for(Object gruposAcessoDisponivel : colecaoGruposAcessoDisponiveis){
					Grupo grupoDisponivel = (Grupo) gruposAcessoDisponivel;
					if(grupoDisponivel.getId().equals(idGrupoSelecionado)){
						colecaoGruposAcessoHabilitados.add(grupoDisponivel);
					}
				}
			}else{
				for(Object gruposAcessoDisponivel : colecaoGruposAcessoDisponiveis){
					Grupo grupoDisponivel = (Grupo) gruposAcessoDisponivel;
					if(grupoDisponivel.getId().equals(idGrupoSelecionado)){
						colecaoGruposAcessoHabilitados = new ArrayList();
						colecaoGruposAcessoHabilitados.add(grupoDisponivel);
					}
				}
			}
			// adiciona coleção à sessão
			sessao.setAttribute("colecaoGruposAcessoHabilitados", colecaoGruposAcessoHabilitados);
		}

		// Adiciona Grupos de Acesso Selecionado à coleção de restrições de débitos
		if(httpServletRequest.getParameter("adicionaRestricaoDebito") != null){
			Collection colecaoRestricaoDebitoSelecionados = (Collection) sessao.getAttribute("colecaoRestricaoDebitoSelecionados");

			// Valida as datas inseridas
			if(!Util.validarMesAno(inserirResolucaoDiretoriaActionForm.getAnoMesReferenciaDebitoInicio())
							|| !Util.validarMesAno(inserirResolucaoDiretoriaActionForm.getAnoMesReferenciaDebitoFim())){
				throw new ActionServletException("atencao.ano_mes_referencia.invalida");
			}

			if(Util.formatarMesAnoComBarraParaAnoMes(inserirResolucaoDiretoriaActionForm.getAnoMesReferenciaDebitoFim()) < Util
							.formatarMesAnoComBarraParaAnoMes(inserirResolucaoDiretoriaActionForm.getAnoMesReferenciaDebitoInicio())){
				throw new ActionServletException("atencao.resolucao_diretoria.restricao_debito.periodo.invalido");
			}

			// Valida a localidade
			if(!this.isLocalidadeValida(Integer.valueOf(inserirResolucaoDiretoriaActionForm.getIdLocalidade()))){
				throw new ActionServletException("atencao.localidade.inexistente");
			}

			if(colecaoRestricaoDebitoSelecionados != null && colecaoRestricaoDebitoSelecionados.size() > 0){
				// verifica se já está inserido na lista
				for(Object parcelamentoSituacaoEspecialTemp : colecaoRestricaoDebitoSelecionados){
					ParcelamentoSituacaoEspecial parcelamentoSituacaoEspecial = (ParcelamentoSituacaoEspecial) parcelamentoSituacaoEspecialTemp;
					if(parcelamentoSituacaoEspecial.getLocalidade().getId().equals(
									Integer.valueOf(inserirResolucaoDiretoriaActionForm.getIdLocalidade()))){
						throw new ActionServletException("atencao.pesquisa_localidade_inserida_lista");
					}
				}

				ParcelamentoSituacaoEspecial parcelamentoSituacaoEspecial = new ParcelamentoSituacaoEspecial();
				parcelamentoSituacaoEspecial.setLocalidade(new Localidade(Integer.valueOf(inserirResolucaoDiretoriaActionForm
								.getIdLocalidade())));
				parcelamentoSituacaoEspecial.setAnoMesReferenciaDebitoInicio(Util
								.formatarMesAnoComBarraParaAnoMes(inserirResolucaoDiretoriaActionForm.getAnoMesReferenciaDebitoInicio()));
				if(!Util.isVazioOuBranco(inserirResolucaoDiretoriaActionForm.getAnoMesReferenciaDebitoFim())){
					parcelamentoSituacaoEspecial.setAnoMesReferenciaDebitoFim(Util
									.formatarMesAnoComBarraParaAnoMes(inserirResolucaoDiretoriaActionForm.getAnoMesReferenciaDebitoFim()));
				}

				colecaoRestricaoDebitoSelecionados.add(parcelamentoSituacaoEspecial);

			}else{

				ParcelamentoSituacaoEspecial parcelamentoSituacaoEspecial = new ParcelamentoSituacaoEspecial();
				parcelamentoSituacaoEspecial.setLocalidade(new Localidade(Integer.valueOf(inserirResolucaoDiretoriaActionForm
								.getIdLocalidade())));
				parcelamentoSituacaoEspecial.setAnoMesReferenciaDebitoInicio(Util
								.formatarMesAnoComBarraParaAnoMes(inserirResolucaoDiretoriaActionForm.getAnoMesReferenciaDebitoInicio()));
				if(!Util.isVazioOuBranco(inserirResolucaoDiretoriaActionForm.getAnoMesReferenciaDebitoFim())){
					parcelamentoSituacaoEspecial.setAnoMesReferenciaDebitoFim(Util
									.formatarMesAnoComBarraParaAnoMes(inserirResolucaoDiretoriaActionForm.getAnoMesReferenciaDebitoFim()));
				}

				colecaoRestricaoDebitoSelecionados = new ArrayList();
				colecaoRestricaoDebitoSelecionados.add(parcelamentoSituacaoEspecial);
			}
			// adiciona coleção à sessão
			sessao.setAttribute("colecaoRestricaoDebitoSelecionados", colecaoRestricaoDebitoSelecionados);

			inserirResolucaoDiretoriaActionForm.setIdLocalidade(null);
			inserirResolucaoDiretoriaActionForm.setNomeLocalidade(null);
			inserirResolucaoDiretoriaActionForm.setAnoMesReferenciaDebitoInicio(null);
			inserirResolucaoDiretoriaActionForm.setAnoMesReferenciaDebitoFim(null);
		}

		// Adiciona Grupos de Acesso Selecionado à coleção de restrições de débitos
		if(httpServletRequest.getParameter("adicionaCondicaoPagamentoAVista") != null){

			Date dataPagamentoInicio = null;
			Date dataPagamentoFinal = null;
			BigDecimal percentualDescontoMulta = BigDecimal.ZERO;
			BigDecimal percentualDescontoJurosMora = BigDecimal.ZERO;
			BigDecimal percentualDescontoCorrecaoMonetaria = BigDecimal.ZERO;

			Collection colecaoCondicaoPagamentoAVistaSelecionados = (Collection) sessao
							.getAttribute("colecaoCondicaoPagamentoAVistaSelecionados");

			// Valida as datas inseridas
			if(inserirResolucaoDiretoriaActionForm.getDataPagamentoInicio() == null
							|| Util.validarDiaMesAno(inserirResolucaoDiretoriaActionForm.getDataPagamentoInicio())){
				throw new ActionServletException("atencao.data.invalida", "Data de Pagamento Inicial");
			}

			if(inserirResolucaoDiretoriaActionForm.getDataPagamentoFinal() == null
							|| Util.validarDiaMesAno(inserirResolucaoDiretoriaActionForm.getDataPagamentoFinal())){
				throw new ActionServletException("atencao.data.invalida", "Data de Pagamento Final");
			}

			dataPagamentoInicio = Util.converteStringParaDate(inserirResolucaoDiretoriaActionForm.getDataPagamentoInicio(), false);
			dataPagamentoFinal = Util.converteStringParaDate(inserirResolucaoDiretoriaActionForm.getDataPagamentoFinal(), false);

			if(Util.compararData(dataPagamentoInicio, dataPagamentoFinal) > 0){
				throw new ActionServletException("atencao.resolucao_diretoria.condicao_pagto_vista.periodo.invalido");
			}

			if(inserirResolucaoDiretoriaActionForm.getPercentualDescontoMulta() == null
							|| Util.validarValorNaoNumericoComoBigDecimal(inserirResolucaoDiretoriaActionForm.getPercentualDescontoMulta()
											.replace(',', '.'))){
				throw new ActionServletException("atencao.campo.invalido", "Percentual de Desconto na Multa");
			}else{
				percentualDescontoMulta = Util.formatarMoedaRealparaBigDecimal(inserirResolucaoDiretoriaActionForm
								.getPercentualDescontoMulta());

				if(percentualDescontoMulta.compareTo(new BigDecimal("100.00")) > 0){
					throw new ActionServletException("atencao.percentual_invalido", "de Desconto na Multa");
				}
			}

			if(inserirResolucaoDiretoriaActionForm.getPercentualDescontoJurosMora() == null
							|| Util.validarValorNaoNumericoComoBigDecimal(inserirResolucaoDiretoriaActionForm
											.getPercentualDescontoJurosMora().replace(',', '.'))){
				throw new ActionServletException("atencao.campo.invalido", "Percentual de Desconto nos Juros Mora");
			}else{
				percentualDescontoJurosMora = Util.formatarMoedaRealparaBigDecimal(inserirResolucaoDiretoriaActionForm
								.getPercentualDescontoJurosMora());

				if(percentualDescontoJurosMora.compareTo(new BigDecimal("100.00")) > 0){
					throw new ActionServletException("atencao.percentual_invalido", "de Desconto nos Juros Mora");
				}
			}

			if(inserirResolucaoDiretoriaActionForm.getPercentualDescontoCorrecaoMonetaria() == null
							|| Util.validarValorNaoNumericoComoBigDecimal(inserirResolucaoDiretoriaActionForm
											.getPercentualDescontoCorrecaoMonetaria().replace(',', '.'))){
				throw new ActionServletException("atencao.campo.invalido", "Percentual de Desconto na Correção Monetária");
			}else{
				percentualDescontoCorrecaoMonetaria = Util.formatarMoedaRealparaBigDecimal(inserirResolucaoDiretoriaActionForm
								.getPercentualDescontoCorrecaoMonetaria());

				if(percentualDescontoCorrecaoMonetaria.compareTo(new BigDecimal("100.00")) > 0){
					throw new ActionServletException("atencao.percentual_invalido", "de Desconto na Correção Monetária");
				}
			}

			ResolucaoDiretoriaParametrosPagamentoAVista condicaoPagamentoAVista = null;

			if(colecaoCondicaoPagamentoAVistaSelecionados != null && colecaoCondicaoPagamentoAVistaSelecionados.size() > 0){
				// verifica se já está inserido na lista
				for(Object condicaoPagamentoAVistaTemp : colecaoCondicaoPagamentoAVistaSelecionados){
					condicaoPagamentoAVista = (ResolucaoDiretoriaParametrosPagamentoAVista) condicaoPagamentoAVistaTemp;
					if(Util.periodoContemData(dataPagamentoInicio, dataPagamentoFinal, condicaoPagamentoAVista.getDataPagamentoInicio())
									|| Util.periodoContemData(dataPagamentoInicio, dataPagamentoFinal, condicaoPagamentoAVista
													.getDataPagamentoFinal())){
						throw new ActionServletException("atencao.resolucao_diretoria.periodo_ja_existente", Util.formatarData(Util
										.adicionarNumeroDiasDeUmaData(condicaoPagamentoAVista.getDataPagamentoFinal(), 1)));
					}
				}

			}else{
				colecaoCondicaoPagamentoAVistaSelecionados = new ArrayList();
			}

			condicaoPagamentoAVista = new ResolucaoDiretoriaParametrosPagamentoAVista();
			condicaoPagamentoAVista.setDataPagamentoInicio(dataPagamentoInicio);
			condicaoPagamentoAVista.setDataPagamentoFinal(dataPagamentoFinal);
			condicaoPagamentoAVista.setPercentualDescontoMulta(percentualDescontoMulta);
			condicaoPagamentoAVista.setPercentualDescontoJurosMora(percentualDescontoJurosMora);
			condicaoPagamentoAVista.setPercentualDescontoCorrecaoMonetaria(percentualDescontoCorrecaoMonetaria);
			condicaoPagamentoAVista.setDescricaoMensagemExtrato(inserirResolucaoDiretoriaActionForm.getDescricaoMensagemExtrato());

			colecaoCondicaoPagamentoAVistaSelecionados.add(condicaoPagamentoAVista);

			// adiciona coleção à sessão
			sessao.setAttribute("colecaoCondicaoPagamentoAVistaSelecionados", colecaoCondicaoPagamentoAVistaSelecionados);

			// limpando os campos do formulário
			inserirResolucaoDiretoriaActionForm.setDataPagamentoInicio(null);
			inserirResolucaoDiretoriaActionForm.setDataPagamentoFinal(null);
			inserirResolucaoDiretoriaActionForm.setPercentualDescontoMulta(null);
			inserirResolucaoDiretoriaActionForm.setPercentualDescontoJurosMora(null);
			inserirResolucaoDiretoriaActionForm.setPercentualDescontoCorrecaoMonetaria(null);
			inserirResolucaoDiretoriaActionForm.setDescricaoMensagemExtrato(null);
		}

		// Adiciona condições de entrada Selecionadas à coleção
		if(httpServletRequest.getParameter("adicionaCondicaoValorEntrada") != null){

			Date dataNegociacaoInicio = null;
			Date dataNegociacaoFinal = null;
			BigDecimal percentualMinimoEntrada = BigDecimal.ZERO;

			Collection colecaoCondicaoValorEntradaSelecionados = (Collection) sessao
							.getAttribute("colecaoCondicaoValorEntradaSelecionados");

			// Valida as datas inseridas
			if(inserirResolucaoDiretoriaActionForm.getDataNegociacaoInicio() == null
							|| Util.validarDiaMesAno(inserirResolucaoDiretoriaActionForm.getDataNegociacaoInicio())){
				throw new ActionServletException("atencao.data.invalida", "Data de Negociação Inicial");
			}

			if(inserirResolucaoDiretoriaActionForm.getDataNegociacaoFinal() == null
							|| Util.validarDiaMesAno(inserirResolucaoDiretoriaActionForm.getDataNegociacaoFinal())){
				throw new ActionServletException("atencao.data.invalida", "Data de Negociação Final");
			}

			dataNegociacaoInicio = Util.converteStringParaDate(inserirResolucaoDiretoriaActionForm.getDataNegociacaoInicio(), false);
			dataNegociacaoFinal = Util.converteStringParaDate(inserirResolucaoDiretoriaActionForm.getDataNegociacaoFinal(), false);

			if(Util.compararData(dataNegociacaoInicio, dataNegociacaoFinal) > 0){
				throw new ActionServletException("atencao.resolucao_diretoria.condicao_valor_entrada.periodo.invalido");
			}

			if(inserirResolucaoDiretoriaActionForm.getPercentualMinimoEntrada() == null
							|| Util.validarValorNaoNumericoComoBigDecimal(inserirResolucaoDiretoriaActionForm.getPercentualMinimoEntrada()
											.replace(',', '.'))){
				throw new ActionServletException("atencao.campo.invalido", "Percentual Mínimo de Entrada");
			}else{
				percentualMinimoEntrada = Util.formatarMoedaRealparaBigDecimal(inserirResolucaoDiretoriaActionForm
								.getPercentualMinimoEntrada());

				if(percentualMinimoEntrada.compareTo(new BigDecimal("100.00")) > 0){
					throw new ActionServletException("atencao.percentual_invalido", "Mínimo de Entrada");
				}
			}

			ResolucaoDiretoriaParametrosValorEntrada condicaoValorEntrada = null;

			if(colecaoCondicaoValorEntradaSelecionados != null && colecaoCondicaoValorEntradaSelecionados.size() > 0){
				// verifica se já está inserido na lista
				for(Object condicaoValorEntradaTemp : colecaoCondicaoValorEntradaSelecionados){
					condicaoValorEntrada = (ResolucaoDiretoriaParametrosValorEntrada) condicaoValorEntradaTemp;
					if(Util.periodoContemData(dataNegociacaoInicio, dataNegociacaoFinal, condicaoValorEntrada.getDataNegociacaoInicio())
									|| Util.periodoContemData(dataNegociacaoInicio, dataNegociacaoFinal, condicaoValorEntrada
													.getDataNegociacaoFinal())){
						throw new ActionServletException("atencao.resolucao_diretoria.periodo_ja_existente", Util.formatarData(Util
										.adicionarNumeroDiasDeUmaData(condicaoValorEntrada.getDataNegociacaoFinal(), 1)));
					}
				}

			}else{
				colecaoCondicaoValorEntradaSelecionados = new ArrayList();
			}

			condicaoValorEntrada = new ResolucaoDiretoriaParametrosValorEntrada();
			condicaoValorEntrada.setDataNegociacaoInicio(dataNegociacaoInicio);
			condicaoValorEntrada.setDataNegociacaoFinal(dataNegociacaoFinal);
			condicaoValorEntrada.setPercentualMinimoEntrada(percentualMinimoEntrada);

			colecaoCondicaoValorEntradaSelecionados.add(condicaoValorEntrada);

			// adiciona coleção à sessão
			sessao.setAttribute("colecaoCondicaoValorEntradaSelecionados", colecaoCondicaoValorEntradaSelecionados);

			// limpando os campos do formulário
			inserirResolucaoDiretoriaActionForm.setDataNegociacaoInicio(null);
			inserirResolucaoDiretoriaActionForm.setDataNegociacaoFinal(null);
			inserirResolucaoDiretoriaActionForm.setPercentualMinimoEntrada(null);
		}

		// Remove Grupo de Acesso Selecionado da coleção Grupo de Acesso Habilitados
		if(httpServletRequest.getParameter("removeGrupoAcesso") != null){

			// recupera grupos habilitados
			Collection colecaoGruposAcessoHabilitados = (Collection) sessao.getAttribute("colecaoGruposAcessoHabilitados");
			// recupera id do grupo disponível selecionado
			Integer idGrupoSelecionado = Integer.parseInt(httpServletRequest.getParameter("removeGrupoAcesso"));
			// recupera grupo disponíveis
			Collection colecaoGruposAcessoHabilitadosNovos = new ArrayList();

			if(colecaoGruposAcessoHabilitados != null && colecaoGruposAcessoHabilitados.size() > 0){

				// recupera e remove grupo selecionado da lista de Habilitados
				for(Object grupoAcessoHabilitado : colecaoGruposAcessoHabilitados){
					Grupo grupoHabilitado = (Grupo) grupoAcessoHabilitado;
					if(!grupoHabilitado.getId().equals(idGrupoSelecionado)){
						colecaoGruposAcessoHabilitadosNovos.add(grupoHabilitado);
					}
				}
			}
			// adiciona coleção à sessão
			sessao.setAttribute("colecaoGruposAcessoHabilitados", colecaoGruposAcessoHabilitadosNovos);
		}

		// Remove Grupo de Acesso Selecionado da coleção Grupo de Acesso Habilitados
		if(httpServletRequest.getParameter("removeRestricaoDebito") != null){

			Collection colecaoRestricaoDebitoSelecionados = (Collection) sessao.getAttribute("colecaoRestricaoDebitoSelecionados");
			// recupera id do grupo disponível selecionado
			Integer idLocalidade = Integer.parseInt(httpServletRequest.getParameter("removeRestricaoDebito"));
			// recupera grupo disponíveis
			Collection colecaoRestricaoDebitoSelecionadosNovos = new ArrayList();

			if(colecaoRestricaoDebitoSelecionados != null && colecaoRestricaoDebitoSelecionados.size() > 0){

				// recupera e remove grupo selecionado da lista de Habilitados
				for(Object parcelamentoSituacaoEspecialTemp : colecaoRestricaoDebitoSelecionados){
					ParcelamentoSituacaoEspecial parcelamentoSituacaoEspecial = (ParcelamentoSituacaoEspecial) parcelamentoSituacaoEspecialTemp;
					if(!parcelamentoSituacaoEspecial.getLocalidade().getId().equals(idLocalidade)){
						colecaoRestricaoDebitoSelecionadosNovos.add(parcelamentoSituacaoEspecial);
					}
				}
			}
			// adiciona coleção à sessão
			sessao.setAttribute("colecaoRestricaoDebitoSelecionados", colecaoRestricaoDebitoSelecionadosNovos);

			inserirResolucaoDiretoriaActionForm.setIdLocalidade(null);
			inserirResolucaoDiretoriaActionForm.setNomeLocalidade(null);
			inserirResolucaoDiretoriaActionForm.setAnoMesReferenciaDebitoInicio(null);
			inserirResolucaoDiretoriaActionForm.setAnoMesReferenciaDebitoFim(null);
		}

		// Remove Condição de Pagamento à Vista Selecionado da coleção
		if(httpServletRequest.getParameter("removeCondicaoPagtoAVista") != null){

			Collection colecaoCondicaoPagamentoAVistaSelecionados = (Collection) sessao
							.getAttribute("colecaoCondicaoPagamentoAVistaSelecionados");

			// recupera a data inicial de pagamento da condição removida
			Date dataPagamentoInicio = Util.converteStringInvertidaSemBarraParaDate(httpServletRequest
							.getParameter("removeCondicaoPagtoAVista"));

			// recupera grupo disponíveis
			Collection colecaoCondicaoPagamentoAVistaSelecionadosNovos = new ArrayList();

			if(colecaoCondicaoPagamentoAVistaSelecionados != null && colecaoCondicaoPagamentoAVistaSelecionados.size() > 0){

				// recupera e remove grupo selecionado da lista de Habilitados
				for(Object condicaoPagamentoAVistaTemp : colecaoCondicaoPagamentoAVistaSelecionados){
					ResolucaoDiretoriaParametrosPagamentoAVista condicaoPagamentoAVista = (ResolucaoDiretoriaParametrosPagamentoAVista) condicaoPagamentoAVistaTemp;
					if(Util.compararData(condicaoPagamentoAVista.getDataPagamentoInicio(), dataPagamentoInicio) != 0){
						colecaoCondicaoPagamentoAVistaSelecionadosNovos.add(condicaoPagamentoAVista);
					}
				}
			}
			// adiciona coleção à sessão
			sessao.setAttribute("colecaoCondicaoPagamentoAVistaSelecionados", colecaoCondicaoPagamentoAVistaSelecionadosNovos);

			// limpando os campos do formulário
			inserirResolucaoDiretoriaActionForm.setDataPagamentoInicio(null);
			inserirResolucaoDiretoriaActionForm.setDataPagamentoFinal(null);
			inserirResolucaoDiretoriaActionForm.setPercentualDescontoMulta(null);
			inserirResolucaoDiretoriaActionForm.setPercentualDescontoJurosMora(null);
			inserirResolucaoDiretoriaActionForm.setPercentualDescontoCorrecaoMonetaria(null);
			inserirResolucaoDiretoriaActionForm.setDescricaoMensagemExtrato(null);
		}

		// Remove Condição de Valor de Entrada Selecionado da coleção
		if(httpServletRequest.getParameter("removeCondicaoValorEntrada") != null){

			Collection colecaoCondicaoValorEntradaSelecionados = (Collection) sessao
							.getAttribute("colecaoCondicaoValorEntradaSelecionados");

			// recupera a data inicial de negociação da condição removida
			Date dataNegociacaoInicio = Util.converteStringInvertidaSemBarraParaDate(httpServletRequest
							.getParameter("removeCondicaoValorEntrada"));

			// recupera grupo disponíveis
			Collection colecaoCondicaoValorEntradaSelecionadosNovos = new ArrayList();

			if(colecaoCondicaoValorEntradaSelecionados != null && colecaoCondicaoValorEntradaSelecionados.size() > 0){

				// recupera e remove grupo selecionado da lista de Habilitados
				for(Object condicaoValorEntradaTemp : colecaoCondicaoValorEntradaSelecionados){
					ResolucaoDiretoriaParametrosValorEntrada condicaoValorEntrada = (ResolucaoDiretoriaParametrosValorEntrada) condicaoValorEntradaTemp;
					if(Util.compararData(condicaoValorEntrada.getDataNegociacaoInicio(), dataNegociacaoInicio) != 0){
						colecaoCondicaoValorEntradaSelecionadosNovos.add(condicaoValorEntrada);
					}
				}
			}
			// adiciona coleção à sessão
			sessao.setAttribute("colecaoCondicaoValorEntradaSelecionados", colecaoCondicaoValorEntradaSelecionadosNovos);

			// limpando os campos do formulário
			inserirResolucaoDiretoriaActionForm.setDataNegociacaoInicio(null);
			inserirResolucaoDiretoriaActionForm.setDataNegociacaoFinal(null);
			inserirResolucaoDiretoriaActionForm.setPercentualMinimoEntrada(null);
		}

		if(httpServletRequest.getParameter("desfazer") != null){
			inserirResolucaoDiretoriaActionForm.reset(actionMapping, httpServletRequest);
			sessao.setAttribute("colecaoGruposAcessoHabilitados", null);
			sessao.setAttribute("colecaoRestricaoDebitoSelecionados", null);
			sessao.setAttribute("colecaoCondicaoPagamentoAVistaSelecionados", null);
			sessao.setAttribute("colecaoCondicaoValorEntradaSelecionados", null);
		}

		if(httpServletRequest.getParameter("limparGrupos") != null){
			sessao.setAttribute("colecaoGruposAcessoHabilitados", null);
		}

		// inicializa os radio buttons
		if(Util.isVazioOuBranco(inserirResolucaoDiretoriaActionForm.getIndicadorUsoRDParcImovel())
						&& Util.isVazioOuBranco(inserirResolucaoDiretoriaActionForm.getIndicadorUsoRDUsuarios())
						&& Util.isVazioOuBranco(inserirResolucaoDiretoriaActionForm.getIndicadorUsoRDDebitoCobrar())
						&& Util.isVazioOuBranco(inserirResolucaoDiretoriaActionForm.getIndicadorEmissaoAssuntoConta())
						&& Util.isVazioOuBranco(inserirResolucaoDiretoriaActionForm.getIndicadorTrataMediaAtualizacaoMonetaria())
						&& Util.isVazioOuBranco(inserirResolucaoDiretoriaActionForm.getIndicadorCobrarDescontosArrasto())
						&& Util.isVazioOuBranco(inserirResolucaoDiretoriaActionForm.getIndicadorArrasto())){

			inserirResolucaoDiretoriaActionForm.setIndicadorUsoRDParcImovel(ConstantesSistema.NAO.toString());
			inserirResolucaoDiretoriaActionForm.setIndicadorUsoRDUsuarios(ConstantesSistema.NAO.toString());
			inserirResolucaoDiretoriaActionForm.setIndicadorUsoRDDebitoCobrar(ConstantesSistema.NAO.toString());
			inserirResolucaoDiretoriaActionForm.setIndicadorEmissaoAssuntoConta(ConstantesSistema.NAO.toString());
			inserirResolucaoDiretoriaActionForm.setIndicadorTrataMediaAtualizacaoMonetaria(ConstantesSistema.NAO.toString());
			inserirResolucaoDiretoriaActionForm.setIndicadorCobrarDescontosArrasto(ConstantesSistema.NAO.toString());
			inserirResolucaoDiretoriaActionForm.setIndicadorArrasto(ConstantesSistema.NAO.toString());
		}

		// Localidade
		if((!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("1"))
						|| !Util.isVazioOuBranco(inserirResolucaoDiretoriaActionForm.getIdLocalidade())){

			// [UC0023 - Pesquisar Localidade]
			this.pesquisarLocalidade(inserirResolucaoDiretoriaActionForm, httpServletRequest);
		}

		return retorno;

	}

	public String[] preencherColecao(Filtro filtro, String nomeClasse, String nomeAtributo, HttpSession sessao,
					InserirResolucaoDiretoriaActionForm inserirResolucaoDiretoriaActionForm, Fachada fachada, String[] Ids, Class object){

		Collection colecao = fachada.pesquisar(filtro, nomeClasse);
		sessao.setAttribute(nomeAtributo, colecao);

		String[] idsRetorno = null;
		if(Ids == null || Ids.length == 0){
			Iterator iter = colecao.iterator();
			idsRetorno = new String[colecao.size()];
			int i = 0;
			while(iter.hasNext()){
				Object obj = (Object) iter.next();
				if(obj instanceof ResolucaoDiretoriaLayout){
					idsRetorno[i++] = ((ResolucaoDiretoriaLayout) obj).getId() + "";
				}else if(obj instanceof Grupo){
					idsRetorno[i++] = ((Grupo) obj).getId() + "";
				}
			}
		}
		return idsRetorno;
	}

	private void pesquisarLocalidade(InserirResolucaoDiretoriaActionForm form, HttpServletRequest request){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(form.getIdLocalidade())));

		Collection<Localidade> colecaoLocalidade = Fachada.getInstancia().pesquisar(filtroLocalidade, Localidade.class.getName());

		// [FS0008 – Verificar existência da localidade]
		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			form.setIdLocalidade(localidade.getId().toString());
			form.setNomeLocalidade(localidade.getDescricao());
			request.setAttribute("idLocalidadeEncontrado", true);

		}else{

			form.setIdLocalidade("");
			form.setNomeLocalidade("Localidade Inexistente");
		}
	}

	private boolean isLocalidadeValida(Integer idLocalidade){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));

		Collection<Localidade> colecaoLocalidade = Fachada.getInstancia().pesquisar(filtroLocalidade, Localidade.class.getName());

		// [FS0008 – Verificar existência da localidade]
		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
}
