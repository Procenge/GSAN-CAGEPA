/**
 * 
 */
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
 * Action que faz a exibi��o da tela para o usu�rio setar os campos e permitir
 * que ele insera uma resolu��o de diretoria [UC0217] Inserir Resolu��o de
 * Diretoria
 * 
 * @author Rafael Corr�a
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

		// Vari�vel que indica quando o usu�rio fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		httpServletRequest.removeAttribute("idLocalidadeEncontrado");

		// Preenche o filtro de ResolucaoDiretoriaLayout (Termo de Confiss�o de D�vida)
		FiltroResolucaoDiretoriaLayout filtroResolucaoDiretoriaLayout = new FiltroResolucaoDiretoriaLayout();

		filtroResolucaoDiretoriaLayout.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoriaLayout.INDICADOR_USO, 1));

		filtroResolucaoDiretoriaLayout.setCampoOrderBy(FiltroResolucaoDiretoriaLayout.DESCRICAO);

		preencherColecao(filtroResolucaoDiretoriaLayout, ResolucaoDiretoriaLayout.class.getName(), "colecaoResolucaoDiretoriaLayout",
						sessao, inserirResolucaoDiretoriaActionForm, fachada, inserirResolucaoDiretoriaActionForm
										.getIdsResolucaoDiretoriaLayout(), ResolucaoDiretoriaLayout.class);

		// Preenche o filtro de Grupos de Acesso Dispon�veis

		FiltroGrupo filtroGruposAcesso = new FiltroGrupo();

		filtroGruposAcesso.adicionarParametro(new ParametroSimples(FiltroGrupo.INDICADOR_USO, 1));

		filtroGruposAcesso.setCampoOrderBy(FiltroGrupo.DESCRICAO);

		preencherColecao(filtroGruposAcesso, Grupo.class.getName(), "colecaoGruposAcessoDisponiveis", sessao,
						inserirResolucaoDiretoriaActionForm, fachada, inserirResolucaoDiretoriaActionForm.getIdsGruposAcessoDisponiveis(),
						Grupo.class);

		// Adiciona Grupos de Acesso Selecionado � cole��o de Grupos de Acesso Habilitados
		if(httpServletRequest.getParameter("adicionaGrupoAcesso") != null){

			// recupera grupos habilitados
			Collection colecaoGruposAcessoHabilitados = (Collection) sessao.getAttribute("colecaoGruposAcessoHabilitados");
			// recupera id do grupo dispon�vel selecionado
			Integer idGrupoSelecionado = Integer.parseInt(inserirResolucaoDiretoriaActionForm.getIdsGruposAcessoDisponiveis()[0]);
			// recupera grupo dispon�veis
			Collection colecaoGruposAcessoDisponiveis = (Collection) sessao.getAttribute("colecaoGruposAcessoDisponiveis");

			if(colecaoGruposAcessoHabilitados != null && colecaoGruposAcessoHabilitados.size() > 0){
				// verifica se grupo j� est� inserido na lista de habilitados
				for(Object gruposAcessoHabilitados : colecaoGruposAcessoHabilitados){
					Grupo grupoHabilitado = (Grupo) gruposAcessoHabilitados;
					if(grupoHabilitado.getId().equals(idGrupoSelecionado)){
						throw new ActionServletException("atencao.grupo_acesso_ja_associado_resolucao");
					}
				}
				// recupera e adiciona grupo dispon�vel selecionado � lista de Habilitados
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
			// adiciona cole��o � sess�o
			sessao.setAttribute("colecaoGruposAcessoHabilitados", colecaoGruposAcessoHabilitados);
		}

		// Adiciona Grupos de Acesso Selecionado � cole��o de restri��es de d�bitos
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
				// verifica se j� est� inserido na lista
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
			// adiciona cole��o � sess�o
			sessao.setAttribute("colecaoRestricaoDebitoSelecionados", colecaoRestricaoDebitoSelecionados);

			inserirResolucaoDiretoriaActionForm.setIdLocalidade(null);
			inserirResolucaoDiretoriaActionForm.setNomeLocalidade(null);
			inserirResolucaoDiretoriaActionForm.setAnoMesReferenciaDebitoInicio(null);
			inserirResolucaoDiretoriaActionForm.setAnoMesReferenciaDebitoFim(null);
		}

		// Adiciona Grupos de Acesso Selecionado � cole��o de restri��es de d�bitos
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
				throw new ActionServletException("atencao.campo.invalido", "Percentual de Desconto na Corre��o Monet�ria");
			}else{
				percentualDescontoCorrecaoMonetaria = Util.formatarMoedaRealparaBigDecimal(inserirResolucaoDiretoriaActionForm
								.getPercentualDescontoCorrecaoMonetaria());

				if(percentualDescontoCorrecaoMonetaria.compareTo(new BigDecimal("100.00")) > 0){
					throw new ActionServletException("atencao.percentual_invalido", "de Desconto na Corre��o Monet�ria");
				}
			}

			ResolucaoDiretoriaParametrosPagamentoAVista condicaoPagamentoAVista = null;

			if(colecaoCondicaoPagamentoAVistaSelecionados != null && colecaoCondicaoPagamentoAVistaSelecionados.size() > 0){
				// verifica se j� est� inserido na lista
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

			// adiciona cole��o � sess�o
			sessao.setAttribute("colecaoCondicaoPagamentoAVistaSelecionados", colecaoCondicaoPagamentoAVistaSelecionados);

			// limpando os campos do formul�rio
			inserirResolucaoDiretoriaActionForm.setDataPagamentoInicio(null);
			inserirResolucaoDiretoriaActionForm.setDataPagamentoFinal(null);
			inserirResolucaoDiretoriaActionForm.setPercentualDescontoMulta(null);
			inserirResolucaoDiretoriaActionForm.setPercentualDescontoJurosMora(null);
			inserirResolucaoDiretoriaActionForm.setPercentualDescontoCorrecaoMonetaria(null);
			inserirResolucaoDiretoriaActionForm.setDescricaoMensagemExtrato(null);
		}

		// Adiciona condi��es de entrada Selecionadas � cole��o
		if(httpServletRequest.getParameter("adicionaCondicaoValorEntrada") != null){

			Date dataNegociacaoInicio = null;
			Date dataNegociacaoFinal = null;
			BigDecimal percentualMinimoEntrada = BigDecimal.ZERO;

			Collection colecaoCondicaoValorEntradaSelecionados = (Collection) sessao
							.getAttribute("colecaoCondicaoValorEntradaSelecionados");

			// Valida as datas inseridas
			if(inserirResolucaoDiretoriaActionForm.getDataNegociacaoInicio() == null
							|| Util.validarDiaMesAno(inserirResolucaoDiretoriaActionForm.getDataNegociacaoInicio())){
				throw new ActionServletException("atencao.data.invalida", "Data de Negocia��o Inicial");
			}

			if(inserirResolucaoDiretoriaActionForm.getDataNegociacaoFinal() == null
							|| Util.validarDiaMesAno(inserirResolucaoDiretoriaActionForm.getDataNegociacaoFinal())){
				throw new ActionServletException("atencao.data.invalida", "Data de Negocia��o Final");
			}

			dataNegociacaoInicio = Util.converteStringParaDate(inserirResolucaoDiretoriaActionForm.getDataNegociacaoInicio(), false);
			dataNegociacaoFinal = Util.converteStringParaDate(inserirResolucaoDiretoriaActionForm.getDataNegociacaoFinal(), false);

			if(Util.compararData(dataNegociacaoInicio, dataNegociacaoFinal) > 0){
				throw new ActionServletException("atencao.resolucao_diretoria.condicao_valor_entrada.periodo.invalido");
			}

			if(inserirResolucaoDiretoriaActionForm.getPercentualMinimoEntrada() == null
							|| Util.validarValorNaoNumericoComoBigDecimal(inserirResolucaoDiretoriaActionForm.getPercentualMinimoEntrada()
											.replace(',', '.'))){
				throw new ActionServletException("atencao.campo.invalido", "Percentual M�nimo de Entrada");
			}else{
				percentualMinimoEntrada = Util.formatarMoedaRealparaBigDecimal(inserirResolucaoDiretoriaActionForm
								.getPercentualMinimoEntrada());

				if(percentualMinimoEntrada.compareTo(new BigDecimal("100.00")) > 0){
					throw new ActionServletException("atencao.percentual_invalido", "M�nimo de Entrada");
				}
			}

			ResolucaoDiretoriaParametrosValorEntrada condicaoValorEntrada = null;

			if(colecaoCondicaoValorEntradaSelecionados != null && colecaoCondicaoValorEntradaSelecionados.size() > 0){
				// verifica se j� est� inserido na lista
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

			// adiciona cole��o � sess�o
			sessao.setAttribute("colecaoCondicaoValorEntradaSelecionados", colecaoCondicaoValorEntradaSelecionados);

			// limpando os campos do formul�rio
			inserirResolucaoDiretoriaActionForm.setDataNegociacaoInicio(null);
			inserirResolucaoDiretoriaActionForm.setDataNegociacaoFinal(null);
			inserirResolucaoDiretoriaActionForm.setPercentualMinimoEntrada(null);
		}

		// Remove Grupo de Acesso Selecionado da cole��o Grupo de Acesso Habilitados
		if(httpServletRequest.getParameter("removeGrupoAcesso") != null){

			// recupera grupos habilitados
			Collection colecaoGruposAcessoHabilitados = (Collection) sessao.getAttribute("colecaoGruposAcessoHabilitados");
			// recupera id do grupo dispon�vel selecionado
			Integer idGrupoSelecionado = Integer.parseInt(httpServletRequest.getParameter("removeGrupoAcesso"));
			// recupera grupo dispon�veis
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
			// adiciona cole��o � sess�o
			sessao.setAttribute("colecaoGruposAcessoHabilitados", colecaoGruposAcessoHabilitadosNovos);
		}

		// Remove Grupo de Acesso Selecionado da cole��o Grupo de Acesso Habilitados
		if(httpServletRequest.getParameter("removeRestricaoDebito") != null){

			Collection colecaoRestricaoDebitoSelecionados = (Collection) sessao.getAttribute("colecaoRestricaoDebitoSelecionados");
			// recupera id do grupo dispon�vel selecionado
			Integer idLocalidade = Integer.parseInt(httpServletRequest.getParameter("removeRestricaoDebito"));
			// recupera grupo dispon�veis
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
			// adiciona cole��o � sess�o
			sessao.setAttribute("colecaoRestricaoDebitoSelecionados", colecaoRestricaoDebitoSelecionadosNovos);

			inserirResolucaoDiretoriaActionForm.setIdLocalidade(null);
			inserirResolucaoDiretoriaActionForm.setNomeLocalidade(null);
			inserirResolucaoDiretoriaActionForm.setAnoMesReferenciaDebitoInicio(null);
			inserirResolucaoDiretoriaActionForm.setAnoMesReferenciaDebitoFim(null);
		}

		// Remove Condi��o de Pagamento � Vista Selecionado da cole��o
		if(httpServletRequest.getParameter("removeCondicaoPagtoAVista") != null){

			Collection colecaoCondicaoPagamentoAVistaSelecionados = (Collection) sessao
							.getAttribute("colecaoCondicaoPagamentoAVistaSelecionados");

			// recupera a data inicial de pagamento da condi��o removida
			Date dataPagamentoInicio = Util.converteStringInvertidaSemBarraParaDate(httpServletRequest
							.getParameter("removeCondicaoPagtoAVista"));

			// recupera grupo dispon�veis
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
			// adiciona cole��o � sess�o
			sessao.setAttribute("colecaoCondicaoPagamentoAVistaSelecionados", colecaoCondicaoPagamentoAVistaSelecionadosNovos);

			// limpando os campos do formul�rio
			inserirResolucaoDiretoriaActionForm.setDataPagamentoInicio(null);
			inserirResolucaoDiretoriaActionForm.setDataPagamentoFinal(null);
			inserirResolucaoDiretoriaActionForm.setPercentualDescontoMulta(null);
			inserirResolucaoDiretoriaActionForm.setPercentualDescontoJurosMora(null);
			inserirResolucaoDiretoriaActionForm.setPercentualDescontoCorrecaoMonetaria(null);
			inserirResolucaoDiretoriaActionForm.setDescricaoMensagemExtrato(null);
		}

		// Remove Condi��o de Valor de Entrada Selecionado da cole��o
		if(httpServletRequest.getParameter("removeCondicaoValorEntrada") != null){

			Collection colecaoCondicaoValorEntradaSelecionados = (Collection) sessao
							.getAttribute("colecaoCondicaoValorEntradaSelecionados");

			// recupera a data inicial de negocia��o da condi��o removida
			Date dataNegociacaoInicio = Util.converteStringInvertidaSemBarraParaDate(httpServletRequest
							.getParameter("removeCondicaoValorEntrada"));

			// recupera grupo dispon�veis
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
			// adiciona cole��o � sess�o
			sessao.setAttribute("colecaoCondicaoValorEntradaSelecionados", colecaoCondicaoValorEntradaSelecionadosNovos);

			// limpando os campos do formul�rio
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

		// [FS0008 � Verificar exist�ncia da localidade]
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

		// [FS0008 � Verificar exist�ncia da localidade]
		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
}
