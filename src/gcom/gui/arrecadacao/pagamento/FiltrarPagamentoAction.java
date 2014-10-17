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

package gcom.gui.arrecadacao.pagamento;

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.ArrecadadorMovimento;
import gcom.arrecadacao.FiltroArrecadadorMovimento;
import gcom.arrecadacao.FiltroAvisoBancario;
import gcom.arrecadacao.aviso.AvisoBancario;
import gcom.arrecadacao.pagamento.Pagamento;
import gcom.arrecadacao.pagamento.PagamentoHistorico;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Filtra os pagamentos de com os parametros passados
 * [UC0255] Filtrar Pagamentos
 * 
 * @author Rafael Santos
 * @date 07/10/2006
 */
public class FiltrarPagamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = null;

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Pega o formul�rio
		ConsultarPagamentoActionForm consultarPagamentoActionForm = (ConsultarPagamentoActionForm) actionForm;

		Collection<Pagamento> colecaoImoveisPagamentos = null;
		Collection<PagamentoHistorico> colecaoImoveisPagamentosHistorico = null;
		Collection<Pagamento> colecaoPagamentosClienteConta = null;
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoClienteConta = null;
		Collection<Pagamento> colecaoPagamentosClienteGuiaPagamento = null;
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoClienteGuiaPagamento = null;
		Collection<Pagamento> colecaoPagamentosClienteDebitoACobrar = null;
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoClienteDebitoACobrar = null;
		Collection<Pagamento> colecaoPagamentosLocalidade = null;
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoImovel = null;
		Collection<Pagamento> colecaoPagamentosAvisoBancario = null;
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoAvisoBancario = null;
		Collection<Pagamento> colecaoPagamentosMovimentoArrecadador = null;
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoMovimentoArrecadador = null;
		Collection<Pagamento> colecaoPagamentosClientes = null;
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoClientes = null;

		// Recupera os par�metros do form
		String idImovel = (String) consultarPagamentoActionForm.getIdImovel();
		String idCliente = (String) consultarPagamentoActionForm.getIdCliente();
		if(idCliente == null || idCliente.trim().equals("")){
			consultarPagamentoActionForm.setClienteRelacaoTipo("");
		}
		String idTipoRelacao = (String) consultarPagamentoActionForm.getClienteRelacaoTipo();
		String localidadeInicial = (String) consultarPagamentoActionForm.getLocalidadeInicial();
		String localidadeFinal = (String) consultarPagamentoActionForm.getLocalidadeFinal();
		String idAvisoBancario = (String) consultarPagamentoActionForm.getIdAvisoBancario();
		String idArrecadador = (String) consultarPagamentoActionForm.getIdArrecadador();
		String periodoArrecadacaoInicial = (String) consultarPagamentoActionForm.getPeriodoArrecadacaoInicio();
		String periodoArrecadacaoFinal = (String) consultarPagamentoActionForm.getPeriodoArrecadacaoFim();
		String periodoPagamentoInicio = consultarPagamentoActionForm.getPeriodoPagamentoInicio();
		String periodoPagamentoFim = consultarPagamentoActionForm.getPeriodoPagamentoFim();
		String codigoSetorComercialInicial = consultarPagamentoActionForm.getCodigoSetorComercialInicial();
		String codigoSetorComercialFinal = consultarPagamentoActionForm.getCodigoSetorComercialFinal();
		String indicadorTotalizarPorDataPagamento = consultarPagamentoActionForm.getIndicadorTotalizarPorDataPagamento();

		Collection<Integer> idsArrecadadores = null;

		Collection<Arrecadador> colecaoArrecadadores = (Collection<Arrecadador>) sessao.getAttribute("colecaoArrecadadores");

		if(!Util.isVazioOrNulo(colecaoArrecadadores)){
			idsArrecadadores = new ArrayList<Integer>();

			for(Arrecadador arrecadador : colecaoArrecadadores){
				idsArrecadadores.add(arrecadador.getId());
			}
		}

		Date dataPagamentoInicial = null;
		Date dataPagamentoFinal = null;
		if(!Util.isVazioOuBranco(consultarPagamentoActionForm.getDataPagamentoInicio())){

			dataPagamentoInicial = Util.converteStringParaDate((String) consultarPagamentoActionForm.getDataPagamentoInicio());

		}
		if(!Util.isVazioOuBranco(consultarPagamentoActionForm.getDataPagamentoInicio())){

			dataPagamentoFinal = Util.converteStringParaDate((String) consultarPagamentoActionForm.getDataPagamentoFim());

		}
		String[] idsPagamentosSituacoes = (String[]) consultarPagamentoActionForm.getPagamentoSituacao();
		String[] idsDebitosTipos = (String[]) consultarPagamentoActionForm.getDebitoTipoSelecionados();
		String[] idsArrecadacaoForma = (String[]) consultarPagamentoActionForm.getArrecadacaoForma();
		String[] idsDocumentosTipos = (String[]) consultarPagamentoActionForm.getDocumentoTipo();
		String[] idsCategoria = (String[]) consultarPagamentoActionForm.getCategoria();

		String opcaoPagamento = consultarPagamentoActionForm.getOpcaoPagamento();

		// caso venha da tela de manter pagamento o padr�o ser� consultar o atual
		if(opcaoPagamento == null){
			opcaoPagamento = "atual";
		}

		// 1 check --- null uncheck
		String indicadorAtualizar = httpServletRequest.getParameter("indicadorAtualizar");

		String tela = (String) sessao.getAttribute("tela");
		if(tela != null && !tela.equals("")){
			if(tela.equals("manterPagamento")){
				retorno = actionMapping.findForward("exibirManterPagamento");
				sessao.setAttribute("indicadorAtualizar", indicadorAtualizar);
			}else if("estorno".equals(tela)){
				retorno = actionMapping.findForward("estorno");
				sessao.removeAttribute("indicadorAtualizar");
			}
		}else{
			retorno = actionMapping.findForward("consultarPagamentos");
			sessao.removeAttribute("indicadorAtualizar");
		}

		// [FS0003] Verificar se um dos campos obrig�torios foi informado
		if((idImovel == null || idImovel.equalsIgnoreCase("")) && (localidadeInicial == null || localidadeInicial.equalsIgnoreCase(""))
						&& (localidadeFinal == null || localidadeFinal.equalsIgnoreCase(""))
						&& (idCliente == null || idCliente.equalsIgnoreCase(""))
						&& (idAvisoBancario == null || idAvisoBancario.equalsIgnoreCase(""))
						&& (idArrecadador == null || idArrecadador.equalsIgnoreCase(""))){
			throw new ActionServletException("atencao.nenhum_campo_selecionado_consultar_pagamento");
		}

		// Verifica se a localidade final � maior que a inicial
		if(localidadeInicial != null && !localidadeInicial.equals("") && localidadeFinal != null && !localidadeFinal.equals("")){
			if(localidadeInicial.compareTo(localidadeFinal) == 1){
				throw new ActionServletException("atencao.localidade_inicial_maior_final");
			}
		}

		if(!Util.isVazioOuBranco(codigoSetorComercialInicial) && !Util.isVazioOuBranco(codigoSetorComercialFinal)){
			if(codigoSetorComercialInicial.compareTo(codigoSetorComercialFinal) == 1){
				throw new ActionServletException("atencao.setor_comercial_inicial_maior_final");
			}
		}

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		Integer anoMesArrecadacao = sistemaParametro.getAnoMesArrecadacao();
		String mesAnoArrecadacao = Util.formatarAnoMesParaMesAno(anoMesArrecadacao);

		if(periodoArrecadacaoInicial != null && !periodoArrecadacaoInicial.equalsIgnoreCase("")){

			Integer anoMesArrecadacaoInicial = Integer.valueOf(Util.formatarMesAnoParaAnoMesSemBarra(periodoArrecadacaoInicial));
			if(Util.compararAnoMesReferencia(anoMesArrecadacaoInicial, anoMesArrecadacao, ">")){

				// M�s/Ano Inicial do Per�odo Refer. Arrecada��o informado n�o deve ser posterior a
				// << >>, m�s/ano de arracada��o corrente.
				throw new ActionServletException("atencao.mes_ano_inicial.posterior.arrecadacao_corrente", null, "" + mesAnoArrecadacao);
			}
		}

		if(periodoArrecadacaoFinal != null && !periodoArrecadacaoFinal.equalsIgnoreCase("")){
			Integer anoMesArrecadacaoFinal = Integer.valueOf(Util.formatarMesAnoParaAnoMesSemBarra(periodoArrecadacaoFinal));

			if(Util.compararAnoMesReferencia(anoMesArrecadacaoFinal, anoMesArrecadacao, ">")){

				// M�s/Ano Final do Per�odo Refer. Arrecada��o informado n�o deve ser posterior a <<
				// >>, m�s/ano do arracada��o corrente.
				throw new ActionServletException("atencao.mes_ano_final.posterior.arrecadacao_corrente", null, "" + mesAnoArrecadacao);
			}
		}

		if(periodoPagamentoInicio != null && !periodoPagamentoInicio.equalsIgnoreCase("")){

			Integer anoMesPagamentoInicial = Integer.valueOf(Util.formatarMesAnoParaAnoMesSemBarra(periodoPagamentoInicio));

			if(Util.compararAnoMesReferencia(anoMesPagamentoInicial, anoMesArrecadacao, ">")){
				// M�s/Ano Inicial do Per�odo Refer. Pagamento informado n�o
				// deve ser posterior a << >>, m�s/ano da arrecada��o corrente.
				throw new ActionServletException("atencao.mes_ano_inicial.posterior.pagamento_corrente", null, "" + mesAnoArrecadacao);
			}

		}

		if(periodoPagamentoFim != null && !periodoPagamentoFim.equalsIgnoreCase("")){
			Integer anoMesPagamentoFinal = Integer.valueOf(Util.formatarMesAnoParaAnoMesSemBarra(periodoPagamentoFim));

			if(Util.compararAnoMesReferencia(anoMesPagamentoFinal, anoMesArrecadacao, ">")){
				// //M�s/Ano Final do Per�odo Refer. Pagamento informado n�o
				// deve ser posterior a << >>, m�s/ano da arrecada��o corrente.
				throw new ActionServletException("atencao.mes_ano_final.posterior.pagamento_corrente", null, "" + mesAnoArrecadacao);
			}
		}

		if(dataPagamentoInicial != null && dataPagamentoFinal != null){
			if(dataPagamentoInicial.after(dataPagamentoFinal)){
				throw new ActionServletException("atencao.data_final.anterior.data_inicial");
			}
		}

		if(dataPagamentoInicial != null && !dataPagamentoInicial.equals("")){
			Integer anoMesDataPagamentoInicial = Integer.valueOf(Util.getAnoMesComoInt(dataPagamentoInicial));

			if(Util.compararAnoMesReferencia(anoMesDataPagamentoInicial, anoMesArrecadacao, ">")){
				// Data Inicial do Per�odo de Pagamento informado n�o pode ser
				// posterior a << >> ,m�s/ano da arrecada��o corrente.
				throw new ActionServletException("atencao.data_inicial.posterior.arrecadacao_corrente", null, "" + mesAnoArrecadacao);
			}
		}

		if(dataPagamentoFinal != null && !dataPagamentoFinal.equals("")){
			Integer anoMesDataPagamentoFinal = Integer.valueOf(Util.getAnoMesComoInt(dataPagamentoFinal));

			if(Util.compararAnoMesReferencia(anoMesDataPagamentoFinal, anoMesArrecadacao, ">")){
				// Data Final do Per�odo de Pagamento informado n�o pode ser
				// posterior a << >> ,m�s/ano da arrecada��o corrente.
				throw new ActionServletException("atencao.data_final.posterior.arrecadacao_corrente", null, "" + mesAnoArrecadacao);
			}
		}

		boolean peloMenosUmParametroInformado = false;

		// 2.1. Caso tenha sido informado o Im�vel, seleciona os pagamentos do Im�vel
		if(idImovel != null && !idImovel.trim().equalsIgnoreCase("")){

			// [SB0001] Selecionar Pagamentos do Im�vel
			peloMenosUmParametroInformado = true;

			// pesquisa utilizada a partir do Consultar Pagamento ou est� na tela de consulta de
			// estorno
			if(tela == null || tela.equals("") || "estorno".equals(tela)){

				// Pesquisa o im�vel fazendo os caregamentos necess�rios e caso ele exista seta-o na
				// sess�o
				Imovel imovel = fachada.pesquisarImovelPagamento(Integer.valueOf(idImovel));

				if(imovel != null){
					sessao.setAttribute("imovel", imovel);
				}else{
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "imovel");
				}
			}

			/*
			 * 1. O sistema seleciona os pagamentos do im�vel (a partir da tabela PAGAMENTO com
			 * IMOV_ID = Id do im�vel informado e demais par�metros
			 * de sele��o informada)
			 */

			// pesquisa utilizada pelo do Consultar Pagamento ou pela consulta de estorno
			if(tela == null || tela.equals("") /* || "estorno".equals(tela) */){

				// verifica se � para pesquisar no atual, no historico ou em ambos
				if(opcaoPagamento != null && ((opcaoPagamento.equals("atual")) || (opcaoPagamento.equals("ambos")))){

					// OK
					colecaoImoveisPagamentos = fachada.pesquisarPagamentoImovel(idImovel, idCliente, idTipoRelacao, localidadeInicial,
									localidadeFinal, idAvisoBancario, idArrecadador, periodoArrecadacaoInicial, periodoArrecadacaoFinal,
									periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial, dataPagamentoFinal,
									idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos,
									codigoSetorComercialInicial, codigoSetorComercialFinal, indicadorTotalizarPorDataPagamento,
									idsArrecadadores, idsCategoria);

					if(colecaoImoveisPagamentos != null && !colecaoImoveisPagamentos.isEmpty()){
						sessao.setAttribute("colecaoImoveisPagamentos", colecaoImoveisPagamentos);
					}
				}

				if(opcaoPagamento != null && ((opcaoPagamento.equals("historico")) || (opcaoPagamento.equals("ambos")))){

					// OK
					colecaoImoveisPagamentosHistorico = fachada.pesquisarPagamentoHistoricoImovel(idImovel, idCliente, idTipoRelacao,
									localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador, periodoArrecadacaoInicial,
									periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial,
									dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos,
									codigoSetorComercialInicial, codigoSetorComercialFinal, indicadorTotalizarPorDataPagamento,
									idsArrecadadores, idsCategoria);

					if(colecaoImoveisPagamentosHistorico != null && !colecaoImoveisPagamentosHistorico.isEmpty()){
						sessao.setAttribute("colecaoImoveisPagamentosHistorico", colecaoImoveisPagamentosHistorico);
					}

				}

			}else if("estorno".equals(tela)){

				// OK
				// 1� Passo - Pegar o total de registros atrav�s de um count da consulta que
				// aparecer� na tela
				Integer totalRegistros = fachada.pesquisarPagamentoHistoricoImovelCount(idImovel, idCliente, idTipoRelacao,
								localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador, periodoArrecadacaoInicial,
								periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial,
								dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos,
								codigoSetorComercialInicial, codigoSetorComercialFinal, idsArrecadadores, idsCategoria);

				// 2� Passo - Chamar a fun��o de Pagina��o passando o total de registros
				retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

				// OK
				// 3� Passo - Obter a cole��o da consulta que aparecer� na tela passando o numero de
				// paginas da pesquisa que est� no request
				colecaoPagamentosHistoricoImovel = fachada.pesquisarPagamentoHistoricoImovelParaPaginacao(idImovel, idCliente,
								idTipoRelacao, localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador,
								periodoArrecadacaoInicial, periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim,
								dataPagamentoInicial, dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma,
								idsDocumentosTipos, (Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"),
								codigoSetorComercialInicial, codigoSetorComercialFinal, indicadorTotalizarPorDataPagamento,
								idsArrecadadores, idsCategoria);

				if(colecaoPagamentosHistoricoImovel != null && !colecaoPagamentosHistoricoImovel.isEmpty()){
					sessao.setAttribute("colecaoImoveisPagamentosHistorico", colecaoPagamentosHistoricoImovel);
				}

			}else{

				// OK
				// 1� Passo - Pegar o total de registros atrav�s de um count da consulta que
				// aparecer� na tela
				Integer totalRegistros = fachada.pesquisarPagamentoImovelCount(idImovel, idCliente, idTipoRelacao, localidadeInicial,
								localidadeFinal, idAvisoBancario, idArrecadador, periodoArrecadacaoInicial, periodoArrecadacaoFinal,
								periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial, dataPagamentoFinal,
								idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos,
								codigoSetorComercialInicial, codigoSetorComercialFinal, idsArrecadadores, idsCategoria);

				// 2� Passo - Chamar a fun��o de Pagina��o passando o total de registros
				retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

				// OK
				// 3� Passo - Obter a cole��o da consulta que aparecer� na tela passando o numero de
				// paginas da pesquisa que est� no request
				colecaoImoveisPagamentos = fachada.pesquisarPagamentoImovelParaPaginacao(idImovel, idCliente, idTipoRelacao,
								localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador, periodoArrecadacaoInicial,
								periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial,
								dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos,
								(Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"), codigoSetorComercialInicial,
								codigoSetorComercialFinal, indicadorTotalizarPorDataPagamento, idsArrecadadores, idsCategoria);

				if(colecaoImoveisPagamentos != null && !colecaoImoveisPagamentos.isEmpty()){
					sessao.setAttribute("colecaoImoveisPagamentos", colecaoImoveisPagamentos);
				}
			}
		}

		// 2.2. Caso tenha sido informado o Cliente, seleciona os pagamentos do Cliente
		if(idCliente != null && !idCliente.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

			// pesquisa utilizada pelo do Consultar Pagamento ou est� na consulta de estorno
			if(tela == null || tela.equals("")){

				// verifica se � para pesquisar no atual, no historico ou em ambos
				if(opcaoPagamento != null && ((opcaoPagamento.equals("atual")) || (opcaoPagamento.equals("ambos")))){

					// OK
					colecaoPagamentosClienteConta = fachada.pesquisarPagamentoClienteConta(idImovel, idCliente, idTipoRelacao,
									localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador, periodoArrecadacaoInicial,
									periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial,
									dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos,
									codigoSetorComercialInicial, codigoSetorComercialFinal, indicadorTotalizarPorDataPagamento,
									idsArrecadadores, idsCategoria);
					if(colecaoPagamentosClienteConta != null && !colecaoPagamentosClienteConta.isEmpty()){
						sessao.setAttribute("colecaoPagamentosClienteConta", colecaoPagamentosClienteConta);
					}

				}
				if(opcaoPagamento != null && ((opcaoPagamento.equals("historico")) || (opcaoPagamento.equals("ambos")))){

					// OK
					colecaoPagamentosHistoricoClienteConta = fachada.pesquisarPagamentoHistoricoClienteConta(idImovel, idCliente,
									idTipoRelacao, localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador,
									periodoArrecadacaoInicial, periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim,
									dataPagamentoInicial, dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma,
									idsDocumentosTipos, codigoSetorComercialInicial, codigoSetorComercialFinal,
									indicadorTotalizarPorDataPagamento, idsArrecadadores, idsCategoria);
					if(colecaoPagamentosHistoricoClienteConta != null && !colecaoPagamentosHistoricoClienteConta.isEmpty()){
						sessao.setAttribute("colecaoPagamentosHistoricoClienteConta", colecaoPagamentosHistoricoClienteConta);
					}

				}

				// verifica se � para pesquisar no atual, no historico ou em ambos
				if(opcaoPagamento != null && ((opcaoPagamento.equals("atual")) || (opcaoPagamento.equals("ambos")))){

					// OK
					colecaoPagamentosClienteGuiaPagamento = fachada.pesquisarPagamentoClienteGuiaPagamento(idImovel, idCliente,
									idTipoRelacao, localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador,
									periodoArrecadacaoInicial, periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim,
									dataPagamentoInicial, dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma,
									idsDocumentosTipos, codigoSetorComercialInicial, codigoSetorComercialFinal,
									indicadorTotalizarPorDataPagamento, idsArrecadadores, idsCategoria);

					if(colecaoPagamentosClienteGuiaPagamento != null && !colecaoPagamentosClienteGuiaPagamento.isEmpty()){
						sessao.setAttribute("colecaoPagamentosClienteGuiaPagamento", colecaoPagamentosClienteGuiaPagamento);
					}

				}

				if(opcaoPagamento != null && ((opcaoPagamento.equals("historico")) || (opcaoPagamento.equals("ambos")))){

					// OK
					colecaoPagamentosHistoricoClienteGuiaPagamento = fachada.pesquisarPagamentoHistoricoClienteGuiaPagamento(idImovel,
									idCliente, idTipoRelacao, localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador,
									periodoArrecadacaoInicial, periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim,
									dataPagamentoInicial, dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma,
									idsDocumentosTipos, codigoSetorComercialInicial, codigoSetorComercialFinal,
									indicadorTotalizarPorDataPagamento, idsArrecadadores, idsCategoria);

					if(colecaoPagamentosHistoricoClienteGuiaPagamento != null && !colecaoPagamentosHistoricoClienteGuiaPagamento.isEmpty()){
						sessao.setAttribute("colecaoPagamentosHistoricoClienteGuiaPagamento",
										colecaoPagamentosHistoricoClienteGuiaPagamento);
					}

				}

				// verifica se � para pesquisar no atual, no historico ou em ambos
				if(opcaoPagamento != null && ((opcaoPagamento.equals("atual")) || (opcaoPagamento.equals("ambos")))){

					// OK
					colecaoPagamentosClienteDebitoACobrar = fachada.pesquisarPagamentoClienteDebitoACobrar(idImovel, idCliente,
									idTipoRelacao, localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador,
									periodoArrecadacaoInicial, periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim,
									dataPagamentoInicial, dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma,
									idsDocumentosTipos, codigoSetorComercialInicial, codigoSetorComercialFinal,
									indicadorTotalizarPorDataPagamento, idsArrecadadores, idsCategoria);

					if(colecaoPagamentosClienteDebitoACobrar != null && !colecaoPagamentosClienteDebitoACobrar.isEmpty()){
						sessao.setAttribute("colecaoPagamentosClienteDebitoACobrar", colecaoPagamentosClienteDebitoACobrar);
					}

				}

				if(opcaoPagamento != null && ((opcaoPagamento.equals("historico")) || (opcaoPagamento.equals("ambos")))){

					// OK
					colecaoPagamentosHistoricoClienteDebitoACobrar = fachada.pesquisarPagamentoHistoricoClienteDebitoACobrar(idImovel,
									idCliente, idTipoRelacao, localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador,
									periodoArrecadacaoInicial, periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim,
									dataPagamentoInicial, dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma,
									idsDocumentosTipos, codigoSetorComercialInicial, codigoSetorComercialFinal,
									indicadorTotalizarPorDataPagamento, idsArrecadadores, idsCategoria);

					if(colecaoPagamentosHistoricoClienteDebitoACobrar != null && !colecaoPagamentosHistoricoClienteDebitoACobrar.isEmpty()){
						sessao.setAttribute("colecaoPagamentosHistoricoClienteDebitoACobrar",
										colecaoPagamentosHistoricoClienteDebitoACobrar);
					}
				}

			}else if("estorno".equals(tela)){

				// OK
				// 1� Passo - Pegar o total de registros atrav�s de um count da consulta que
				// aparecer� na tela
				Integer totalRegistros = fachada.pesquisarPagamentoHistoricoClienteCount(idImovel, idCliente, idTipoRelacao,
								localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador, periodoArrecadacaoInicial,
								periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial,
								dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos,
								codigoSetorComercialInicial, codigoSetorComercialFinal, idsArrecadadores, idsCategoria);

				// 2� Passo - Chamar a fun��o de Pagina��o passando o total de registros
				retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

				// OK
				// 3� Passo - Obter a cole��o da consulta que aparecer� na tela passando o numero de
				// paginas da pesquisa que est� no request
				colecaoPagamentosHistoricoClientes = fachada.pesquisarPagamentoHistoricoCliente(idImovel, idCliente, idTipoRelacao,
								localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador, periodoArrecadacaoInicial,
								periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial,
								dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos,
								(Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"), codigoSetorComercialInicial,
								codigoSetorComercialFinal, indicadorTotalizarPorDataPagamento, idsArrecadadores, idsCategoria);

				if(colecaoPagamentosHistoricoClientes != null && !colecaoPagamentosHistoricoClientes.isEmpty()){
					sessao.setAttribute("colecaoPagamentosHistoricoClientes", colecaoPagamentosHistoricoClientes);
				}

			}else{

				// OK
				// 1� Passo - Pegar o total de registros atrav�s de um count da consulta que
				// aparecer� na tela
				Integer totalRegistros = fachada.pesquisarPagamentoClienteCount(idImovel, idCliente, idTipoRelacao, localidadeInicial,
								localidadeFinal, idAvisoBancario, idArrecadador, periodoArrecadacaoInicial, periodoArrecadacaoFinal,
								periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial, dataPagamentoFinal,
								idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos,
								codigoSetorComercialInicial, codigoSetorComercialFinal, idsArrecadadores, idsCategoria);

				// 2� Passo - Chamar a fun��o de Pagina��o passando o total de registros
				retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

				// OK
				// 3� Passo - Obter a cole��o da consulta que aparecer� na tela passando o numero de
				// paginas da pesquisa que est� no request
				colecaoPagamentosClientes = fachada.pesquisarPagamentoCliente(idImovel, idCliente, idTipoRelacao, localidadeInicial,
								localidadeFinal, idAvisoBancario, idArrecadador, periodoArrecadacaoInicial, periodoArrecadacaoFinal,
								periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial, dataPagamentoFinal,
								idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos,
								(Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"), codigoSetorComercialInicial,
								codigoSetorComercialFinal, indicadorTotalizarPorDataPagamento, idsArrecadadores, idsCategoria);

				if(colecaoPagamentosClientes != null && !colecaoPagamentosClientes.isEmpty()){
					sessao.setAttribute("colecaoClientesPagamentos", colecaoPagamentosClientes);
				}
			}
		}

		// 2.3. Caso tenha sido informado a Localidade, seleciona os pagamentos da Localidade
		if(localidadeInicial != null && !localidadeInicial.trim().equalsIgnoreCase("") && localidadeFinal != null
						&& !localidadeFinal.trim().equalsIgnoreCase("")){

			peloMenosUmParametroInformado = true;

			// OK
			// 1� Passo - Pegar o total de registros atrav�s de um count da consulta que aparecer�
			// na tela
			Integer totalRegistros = fachada.pesquisarPagamentoLocalidadeCount(idImovel, idCliente, idTipoRelacao, localidadeInicial,
							localidadeFinal, idAvisoBancario, idArrecadador, periodoArrecadacaoInicial, periodoArrecadacaoFinal,
							periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial, dataPagamentoFinal, idsPagamentosSituacoes,
							idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos, codigoSetorComercialInicial,
							codigoSetorComercialFinal, idsArrecadadores, idsCategoria);

			if(tela == null || tela.equals("")){
				if(totalRegistros == null || totalRegistros.intValue() == 0){
					throw new ActionServletException("atencao.pesquisa.nenhumresultado");
				}

				retorno = actionMapping.findForward("gerarRelatorioPagamento");

				return retorno;

			}else if("estorno".equals(tela)){

				if(opcaoPagamento != null && ((opcaoPagamento.equals("historico")) || (opcaoPagamento.equals("ambos")))){

					// OK
					// 1� Passo - Pegar o total de registros atrav�s de um count da consulta que
					// aparecer� na tela
					totalRegistros = fachada.pesquisarPagamentoHistoricoLocalidadeCount(idImovel, idCliente, idTipoRelacao,
									localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador, periodoArrecadacaoInicial,
									periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial,
									dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos,
									codigoSetorComercialInicial, codigoSetorComercialFinal, idsArrecadadores, idsCategoria);

					// 2� Passo - Chamar a fun��o de Pagina��o passando o total de registros
					retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

					// OK
					// 3� Passo - Obter a cole��o da consulta que aparecer� na tela passando o
					// numero de paginas da pesquisa que est� no request
					colecaoPagamentosHistoricoImovel = fachada.pesquisarPagamentoHistoricoLocalidade(idImovel, idCliente, idTipoRelacao,
									localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador, periodoArrecadacaoInicial,
									periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial,
									dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos,
									(Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"), codigoSetorComercialInicial,
									codigoSetorComercialFinal, indicadorTotalizarPorDataPagamento, idsArrecadadores, idsCategoria);

					if(colecaoPagamentosHistoricoImovel != null && !colecaoPagamentosHistoricoImovel.isEmpty()){
						sessao.setAttribute("colecaoPagamentosHistoricoLocalidade", colecaoPagamentosHistoricoImovel);
						// pesquisa utilizada pelo do Consultar Pagamento
						if(tela == null || tela.equals("")){
							retorno = actionMapping.findForward("gerarRelatorioPagamento");
						}
					}
				}

			}else{
				// 2� Passo - Chamar a fun��o de Pagina��o passando o total de registros
				retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

				// OK
				// 3� Passo - Obter a cole��o da consulta que aparecer� na tela
				// passando o numero de paginas da pesquisa que est� no request
				colecaoPagamentosLocalidade = fachada.pesquisarPagamentoLocalidade(idImovel, idCliente, idTipoRelacao, localidadeInicial,
								localidadeFinal, idAvisoBancario, idArrecadador, periodoArrecadacaoInicial, periodoArrecadacaoFinal,
								periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial, dataPagamentoFinal,
								idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos,
								(Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"), codigoSetorComercialInicial,
								codigoSetorComercialFinal, indicadorTotalizarPorDataPagamento, idsArrecadadores, idsCategoria);

				if(colecaoPagamentosLocalidade != null && !colecaoPagamentosLocalidade.isEmpty()){

					sessao.setAttribute("colecaoPagamentosLocalidade", colecaoPagamentosLocalidade);
					sessao.removeAttribute("colecaoPagamentos");
					// pesquisa utilizada pelo do Consultar Pagamento
				}
			}
		}

		// 2.4. Caso tenha sido informado o Aviso Banc�rio, seleciona os pagamentos do Aviso
		// Banc�rio
		if(idAvisoBancario != null && !idAvisoBancario.trim().equalsIgnoreCase("")){

			// [SB0004] Selecionar Pagamentos do Aviso Banc�rio
			peloMenosUmParametroInformado = true;

			FiltroAvisoBancario filtroAvisoBancario = new FiltroAvisoBancario();
			filtroAvisoBancario.adicionarParametro(new ParametroSimples(FiltroAvisoBancario.ID, idAvisoBancario));

			filtroAvisoBancario.adicionarCaminhoParaCarregamentoEntidade("arrecadador.cliente");

			Collection colecaoAvisoBancarios = fachada.pesquisar(filtroAvisoBancario, AvisoBancario.class.getName());

			if(colecaoAvisoBancarios != null && !colecaoAvisoBancarios.isEmpty()){
				AvisoBancario avisoBancario = (AvisoBancario) ((List) colecaoAvisoBancarios).get(0);
				sessao.setAttribute("avisoBancario", avisoBancario);
			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "aviso bancario");
			}

			// 1. O sistema seleciona os pagamentos do avisobanc�rio informado (a partir
			// da tabela PAGAMENTO com AMIT_ID = AMIT_ID da tabela/ ARRECADADOR_MOVIMENTO_ITEM
			// com ARMV_ID = Id do movimento informado e demais par�metros de sele��o informados)
			// peloMenosUmParametroInformado = true;

			// pesquisa utilizada pelo do Consultar Pagamento ou est� na consulta de estorno
			if(tela == null || tela.equals("")){

				// verifica se � para pesquisar no atual, no historico ou em ambos
				if(opcaoPagamento != null && ((opcaoPagamento.equals("atual")) || (opcaoPagamento.equals("ambos")))){

					// OK
					colecaoPagamentosAvisoBancario = fachada.pesquisarPagamentoAvisoBancario(idImovel, idCliente, idTipoRelacao,
									localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador, periodoArrecadacaoInicial,
									periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial,
									dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos,
									codigoSetorComercialInicial, codigoSetorComercialFinal, indicadorTotalizarPorDataPagamento,
									idsArrecadadores, idsCategoria);

					if(colecaoPagamentosAvisoBancario != null && !colecaoPagamentosAvisoBancario.isEmpty()){
						sessao.setAttribute("colecaoPagamentosAvisoBancario", colecaoPagamentosAvisoBancario);
					}

				}
				if(opcaoPagamento != null && ((opcaoPagamento.equals("historico")) || (opcaoPagamento.equals("ambos")))){

					// OK
					colecaoPagamentosHistoricoAvisoBancario = fachada.pesquisarPagamentoHistoricoAvisoBancario(idImovel, idCliente,
									idTipoRelacao, localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador,
									periodoArrecadacaoInicial, periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim,
									dataPagamentoInicial, dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma,
									idsDocumentosTipos, codigoSetorComercialInicial, codigoSetorComercialFinal,
									indicadorTotalizarPorDataPagamento, idsArrecadadores, idsCategoria);

					if(colecaoPagamentosHistoricoAvisoBancario != null && !colecaoPagamentosHistoricoAvisoBancario.isEmpty()){
						sessao.setAttribute("colecaoPagamentosHistoricoAvisoBancario", colecaoPagamentosHistoricoAvisoBancario);
					}

				}

			}else if("estorno".equals(tela)){

				// OK
				// 1� Passo - Pegar o total de registros atrav�s de um count da consulta que
				// aparecer� na tela
				Integer totalRegistros = fachada.pesquisarPagamentoHistoricoAvisoBancarioCount(idImovel, idCliente, idTipoRelacao,
								localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador, periodoArrecadacaoInicial,
								periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial,
								dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos,
								codigoSetorComercialInicial, codigoSetorComercialFinal, idsArrecadadores, idsCategoria);

				// 2� Passo - Chamar a fun��o de Pagina��o passando o total de registros
				retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

				// OK
				// 3� Passo - Obter a cole��o da consulta que aparecer� na tela passando o numero de
				// paginas da pesquisa que est� no request
				colecaoPagamentosHistoricoAvisoBancario = fachada.pesquisarPagamentoHistoricoAvisoBancarioParaPaginacao(idImovel,
								idCliente, idTipoRelacao, localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador,
								periodoArrecadacaoInicial, periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim,
								dataPagamentoInicial, dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma,
								idsDocumentosTipos, (Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"),
								codigoSetorComercialInicial, codigoSetorComercialFinal, indicadorTotalizarPorDataPagamento,
								idsArrecadadores, idsCategoria);

				if(colecaoPagamentosHistoricoAvisoBancario != null && !colecaoPagamentosHistoricoAvisoBancario.isEmpty()){
					sessao.setAttribute("colecaoPagamentosHistoricoAvisoBancario", colecaoPagamentosHistoricoAvisoBancario);
				}

			}else{

				// OK
				// 1� Passo - Pegar o total de registros atrav�s de um count da consulta que
				// aparecer� na tela
				Integer totalRegistros = fachada.pesquisarPagamentoAvisoBancarioCount(idImovel, idCliente, idTipoRelacao,
								localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador, periodoArrecadacaoInicial,
								periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial,
								dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos,
								codigoSetorComercialInicial, codigoSetorComercialFinal, idsArrecadadores, idsCategoria);

				// 2� Passo - Chamar a fun��o de Pagina��o passando o total de registros
				retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

				// OK
				// 3� Passo - Obter a cole��o da consulta que aparecer� na tela passando o numero de
				// paginas da pesquisa que est� no request
				colecaoPagamentosAvisoBancario = fachada.pesquisarPagamentoAvisoBancarioParaPaginacao(idImovel, idCliente, idTipoRelacao,
								localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador, periodoArrecadacaoInicial,
								periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial,
								dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos,
								(Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"), codigoSetorComercialInicial,
								codigoSetorComercialFinal, indicadorTotalizarPorDataPagamento, idsArrecadadores, idsCategoria);

				if(colecaoPagamentosAvisoBancario != null && !colecaoPagamentosAvisoBancario.isEmpty()){
					sessao.setAttribute("colecaoPagamentosAvisoBancario", colecaoPagamentosAvisoBancario);
				}
			}
		}

		// 2.5. Caso tenha sido informado o Movimento de Arrecadador, seleciona
		// os pagamentos do Movimento de Arrecadador
		if(idArrecadador != null && !idArrecadador.trim().equalsIgnoreCase("")){

			// [SB0004] Selecionar Pagamentos do Movimento Arrecadador
			peloMenosUmParametroInformado = true;

			FiltroArrecadadorMovimento filtroArrecadadorMovimento = new FiltroArrecadadorMovimento();
			filtroArrecadadorMovimento.adicionarParametro(new ParametroSimples(FiltroArrecadadorMovimento.ID, idArrecadador));

			Collection colecaoArrecadadorMovimento = fachada.pesquisar(filtroArrecadadorMovimento, ArrecadadorMovimento.class.getName());

			ArrecadadorMovimento arrecadadorMovimento = new ArrecadadorMovimento();

			if(colecaoArrecadadorMovimento != null && !colecaoArrecadadorMovimento.isEmpty()){
				arrecadadorMovimento = (ArrecadadorMovimento) ((List) colecaoArrecadadorMovimento).get(0);
				sessao.setAttribute("arrecadadorMovimento", arrecadadorMovimento);
			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Movimento de Arrecadador");
			}
			if(tela == null || tela.equals("")){

				// verifica se � para pesquisar no atual, no historico ou em ambos
				if(opcaoPagamento != null && ((opcaoPagamento.equals("atual")) || (opcaoPagamento.equals("ambos")))){

					// OK
					colecaoPagamentosMovimentoArrecadador = fachada.pesquisarPagamentoMovimentoArrecadador(null, null, null, null, null,
									null, idArrecadador, periodoArrecadacaoInicial, periodoArrecadacaoFinal, periodoPagamentoInicio,
									periodoPagamentoFim, dataPagamentoInicial, dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos,
									idsArrecadacaoForma, idsDocumentosTipos, null, null, null, idsArrecadadores, idsCategoria);

					if(colecaoPagamentosMovimentoArrecadador != null && !colecaoPagamentosMovimentoArrecadador.isEmpty()){
						sessao.setAttribute("colecaoPagamentosMovimentoArrecadador", colecaoPagamentosMovimentoArrecadador);
					}

				}

				if(opcaoPagamento != null && ((opcaoPagamento.equals("historico")) || (opcaoPagamento.equals("ambos")))){

					// OK
					colecaoPagamentosHistoricoMovimentoArrecadador = fachada.pesquisarPagamentoHistoricoMovimentoArrecadador(null, null,
									null, null, null, null, idArrecadador, periodoArrecadacaoInicial, periodoArrecadacaoFinal,
									periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial, dataPagamentoFinal,
									idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos, null, null, null,
									idsArrecadadores, idsCategoria);

					if(colecaoPagamentosHistoricoMovimentoArrecadador != null && !colecaoPagamentosHistoricoMovimentoArrecadador.isEmpty()){
						sessao.setAttribute("colecaoPagamentosHistoricoMovimentoArrecadador",
										colecaoPagamentosHistoricoMovimentoArrecadador);
					}

				}
			}else if("estorno".equals(tela)){

				// 1� Passo - Pegar o total de registros atrav�s de um count da consulta que
				// aparecer� na tela
				Integer totalRegistros = fachada.pesquisarPagamentoHistoricoMovimentoArrecadadorCount(idImovel, idCliente, idTipoRelacao,
								localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador, periodoArrecadacaoInicial,
								periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial,
								dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos,
								codigoSetorComercialInicial, codigoSetorComercialFinal, idsArrecadadores, idsCategoria);

				// 2� Passo - Chamar a fun��o de Pagina��o passando o total de registros
				retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

				// OK
				// 3� Passo - Obter a cole��o da consulta que aparecer� na tela passando o numero de
				// paginas da pesquisa que est� no request
				colecaoPagamentosHistoricoMovimentoArrecadador = fachada.pesquisarPagamentoHistoricoMovimentoArrecadadorParaPaginacao(null,
								null, null, null, null, null, idArrecadador, periodoArrecadacaoInicial, periodoArrecadacaoFinal,
								periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial, dataPagamentoFinal,
								idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos,
								(Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"), null, null, null, idsArrecadadores,
								idsCategoria);

				if(colecaoPagamentosHistoricoMovimentoArrecadador != null && !colecaoPagamentosHistoricoMovimentoArrecadador.isEmpty()){
					sessao.setAttribute("colecaoPagamentosHistoricoMovimentoArrecadador", colecaoPagamentosHistoricoMovimentoArrecadador);
				}

			}else{

				// OK
				// 1� Passo - Pegar o total de registros atrav�s de um count da consulta que
				// aparecer� na tela
				Integer totalRegistros = fachada.pesquisarPagamentoMovimentoArrecadadorCount(idImovel, idCliente, idTipoRelacao,
								localidadeInicial, localidadeFinal, idAvisoBancario, idArrecadador, periodoArrecadacaoInicial,
								periodoArrecadacaoFinal, periodoPagamentoInicio, periodoPagamentoFim, dataPagamentoInicial,
								dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos, idsArrecadacaoForma, idsDocumentosTipos,
								codigoSetorComercialInicial, codigoSetorComercialFinal, idsArrecadadores, idsCategoria);

				// 2� Passo - Chamar a fun��o de Pagina��o passando o total de registros
				retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

				// 3� Passo - Obter a cole��o da consulta que aparecer� na tela passando o numero de
				// paginas da pesquisa que est� no request
				colecaoPagamentosMovimentoArrecadador = fachada.pesquisarPagamentoMovimentoArrecadadorParaPaginacao(null, null, null, null,
								null, null, idArrecadador, periodoArrecadacaoInicial, periodoArrecadacaoFinal, periodoPagamentoInicio,
								periodoPagamentoFim, dataPagamentoInicial, dataPagamentoFinal, idsPagamentosSituacoes, idsDebitosTipos,
								idsArrecadacaoForma, idsDocumentosTipos,
								(Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"), null, null, null, idsArrecadadores,
								idsCategoria);

				if(colecaoPagamentosMovimentoArrecadador != null && !colecaoPagamentosMovimentoArrecadador.isEmpty()){
					sessao.setAttribute("colecaoPagamentosMovimentoArrecadador", colecaoPagamentosMovimentoArrecadador);
				}
			}
		}

		// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		// [FS0011] Verifica a exist�ncia de Pagamentos
		if(((colecaoImoveisPagamentos == null || colecaoImoveisPagamentos.isEmpty()) && (colecaoImoveisPagamentosHistorico == null || colecaoImoveisPagamentosHistorico
						.isEmpty()))
						&& (((colecaoPagamentosClienteConta == null || colecaoPagamentosClienteConta.isEmpty()) && (colecaoPagamentosHistoricoClienteConta == null || colecaoPagamentosHistoricoClienteConta
										.isEmpty()))
										&& ((colecaoPagamentosClienteGuiaPagamento == null || colecaoPagamentosClienteGuiaPagamento
														.isEmpty()) && (colecaoPagamentosHistoricoClienteGuiaPagamento == null || colecaoPagamentosHistoricoClienteGuiaPagamento
														.isEmpty())) && ((colecaoPagamentosClienteDebitoACobrar == null || colecaoPagamentosClienteDebitoACobrar
										.isEmpty()) && (colecaoPagamentosHistoricoClienteDebitoACobrar == null || colecaoPagamentosHistoricoClienteDebitoACobrar
										.isEmpty())))
						&& ((colecaoPagamentosLocalidade == null || colecaoPagamentosLocalidade.isEmpty()) && (colecaoPagamentosHistoricoImovel == null || colecaoPagamentosHistoricoImovel
										.isEmpty()))
						&& ((colecaoPagamentosAvisoBancario == null || colecaoPagamentosAvisoBancario.isEmpty()) && (colecaoPagamentosHistoricoAvisoBancario == null || colecaoPagamentosHistoricoAvisoBancario
										.isEmpty()))
						&& ((colecaoPagamentosMovimentoArrecadador == null || colecaoPagamentosMovimentoArrecadador.isEmpty()) && (colecaoPagamentosHistoricoMovimentoArrecadador == null || colecaoPagamentosHistoricoMovimentoArrecadador
										.isEmpty()))
						&& ((colecaoPagamentosClientes == null || colecaoPagamentosClientes.isEmpty()) && (colecaoPagamentosHistoricoClientes == null || colecaoPagamentosHistoricoClientes
										.isEmpty()))){

			// Nenhum pagamento cadastrado
			throw new ActionServletException("atencao.pesquisa.nenhumresultado"); // atencao.nao_pagamentos_parametros_informados
		}

		// Devolve o mapeamento de retorno
		return retorno;
	}
}
