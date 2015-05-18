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
 * GSANPCG
 * Virg�nia Melo
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

package gcom.gui.faturamento.conta;

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.CalcularValoresAguaEsgotoHelper;
import gcom.faturamento.bean.FiltroContaSimularCalculoHelper;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.ConsumoTarifaVigencia;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifaVigencia;
import gcom.faturamento.conta.ContaCategoria;
import gcom.faturamento.conta.ContaCategoriaHistorico;
import gcom.faturamento.conta.FiltroContaCategoria;
import gcom.faturamento.conta.FiltroContaCategoriaHistorico;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.faturamento.bean.ContaSimularCalculoDadosReaisHelper;
import gcom.micromedicao.consumo.ConsumoHistorico;
import gcom.micromedicao.consumo.FiltroConsumoHistorico;
import gcom.micromedicao.medicao.FiltroMedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
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
 * [UC3156] Simular C�lculo da Conta Dados Reais
 * 
 * @author Anderson Italo
 * @date 21/09/2014
 */
public class SimularCalculoContaDadosReaisAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("simularCalculoContaDadosReais");
		SimularCalculoContaDadosReaisDadosAdicionaisActionForm formulario = (SimularCalculoContaDadosReaisDadosAdicionaisActionForm) actionForm;
		HttpSession sessao = httpServletRequest.getSession(false);
		Fachada fachada = Fachada.getInstancia();

		ConsumoTarifa consumoTarifaRecalculo = null;

		if(Util.verificarIdNaoVazio(formulario.getIdConsumoTarifa())){

			consumoTarifaRecalculo = (ConsumoTarifa) fachada.pesquisar(Util.obterInteger(formulario.getIdConsumoTarifa()),
							ConsumoTarifa.class);
		}else{

			ActionServletException ex = new ActionServletException("atencao.required", null, "Tarifa de Consumo para Rec�lculo");
			ex.setUrlBotaoVoltar("/gsan/exibirSimularCalculoContaDadosReaisDadosAdicionaisAction.do");
			throw ex;
		}

		ConsumoTarifaVigencia consumoTarifaVigencia = null;
		if(Util.verificarIdNaoVazio(formulario.getIdConsumoTarifaVigencia())){

			FiltroConsumoTarifaVigencia filtroConsumoTarifaVigencia = new FiltroConsumoTarifaVigencia();
			filtroConsumoTarifaVigencia.adicionarParametro(new ParametroSimples(FiltroConsumoTarifaVigencia.ID, Util
							.obterInteger(formulario.getIdConsumoTarifaVigencia())));
			filtroConsumoTarifaVigencia.adicionarCaminhoParaCarregamentoEntidade(FiltroConsumoTarifaVigencia.CONSUMO_TARIFA);

			consumoTarifaVigencia = (ConsumoTarifaVigencia) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroConsumoTarifaVigencia,
							ConsumoTarifaVigencia.class.getName()));
		}else{

			ActionServletException ex = new ActionServletException("atencao.required", null, "Vig�ncias da tarifa");
			ex.setUrlBotaoVoltar("/gsan/exibirSimularCalculoContaDadosReaisDadosAdicionaisAction.do");
			throw ex;
		}

		FiltroContaSimularCalculoHelper filtroContaSimularCalculoHelper = (FiltroContaSimularCalculoHelper) sessao
						.getAttribute("filtroContaSimularCalculoHelper");
		filtroContaSimularCalculoHelper.setIdConsumoTarifaRecalcular(consumoTarifaRecalculo.getId());
		filtroContaSimularCalculoHelper.setIdConsumoTarifaVigenciaRecalcular(consumoTarifaVigencia.getId());

		List<ContaSimularCalculoDadosReaisHelper> colecaoContasSimularCalculoHelper = new ArrayList<ContaSimularCalculoDadosReaisHelper>();
		ContaSimularCalculoDadosReaisHelper contaSimularCalculoHelper = null;

		// Total de registros
		Integer totalRegistros = Util.obterInteger(formulario.getQuantidadeContas());

		// Pagina��o
		retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

		Collection<Object[]> colecaoDadosSimularCalculo = fachada.pesquisarContasSimularCalculoDadosReais(
						filtroContaSimularCalculoHelper, (Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"));

		sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirSimularCalculoContaDadosReaisDadosAdicionaisAction.do");
		sessao.setAttribute("filtroContaSimularCalculoHelper", filtroContaSimularCalculoHelper);

		for(Object[] dadosContaSimularHelper : colecaoDadosSimularCalculo){

			contaSimularCalculoHelper = new ContaSimularCalculoDadosReaisHelper();

			// Id
			contaSimularCalculoHelper.setIdConta(Util.obterInteger(dadosContaSimularHelper[0].toString()));

			// Refer�ncia da Conta
			contaSimularCalculoHelper.setAnoMesReferenciaConta(Util.obterInteger(dadosContaSimularHelper[1].toString()));

			// Situa��o da Conta
			if(dadosContaSimularHelper[18].toString().equals(ConstantesSistema.SIM.toString())){

				contaSimularCalculoHelper.setIndicadorHistorico(ConstantesSistema.SIM);
				contaSimularCalculoHelper.setSituacaoConta("QUITADA");
			}else{

				contaSimularCalculoHelper.setSituacaoConta("EM D�BITO");
				contaSimularCalculoHelper.setIndicadorHistorico(ConstantesSistema.NAO);
			}

			// Valores Originais
			// �gua
			if(dadosContaSimularHelper[5] != null){

				contaSimularCalculoHelper.setValorOriginalAgua(new BigDecimal(dadosContaSimularHelper[5].toString()));
				contaSimularCalculoHelper.setIndicadorFaturamentoAgua(ConstantesSistema.SIM);
			}else{

				contaSimularCalculoHelper.setIndicadorFaturamentoAgua(ConstantesSistema.NAO);
				contaSimularCalculoHelper.setValorOriginalAgua(BigDecimal.ZERO);
			}

			// Esgoto
			if(dadosContaSimularHelper[6] != null){

				contaSimularCalculoHelper.setValorOriginalEsgoto(new BigDecimal(dadosContaSimularHelper[6].toString()));
				contaSimularCalculoHelper.setIndicadorFaturamentoEsgoto(ConstantesSistema.SIM);
			}else{

				contaSimularCalculoHelper.setIndicadorFaturamentoEsgoto(ConstantesSistema.NAO);
				contaSimularCalculoHelper.setValorOriginalEsgoto(BigDecimal.ZERO);
			}

			// Total
			contaSimularCalculoHelper.setValorOriginalTotal(new BigDecimal(dadosContaSimularHelper[2].toString()));

			// D�bitos
			contaSimularCalculoHelper.setValorDebitos(new BigDecimal(dadosContaSimularHelper[7].toString()));

			// Cr�ditos
			contaSimularCalculoHelper.setValorCreditos(new BigDecimal(dadosContaSimularHelper[8].toString()));

			// Impostos
			contaSimularCalculoHelper.setValorImpostos(new BigDecimal(dadosContaSimularHelper[9].toString()));

			// Id Liga��o Agua Situa��o
			if(dadosContaSimularHelper[11] != null){

				contaSimularCalculoHelper.setIdLigacaoAguaSituacao(Util.obterInteger(dadosContaSimularHelper[11].toString()));
			}

			// Id Liga��o Esgoto Situa��o
			if(dadosContaSimularHelper[12] != null){

				contaSimularCalculoHelper.setIdLigacaoEsgotoSituacao(Util.obterInteger(dadosContaSimularHelper[12].toString()));
			}

			// Consumo Faturado de �gua
			if(dadosContaSimularHelper[14] != null){

				contaSimularCalculoHelper.setConsumoFaturadoAgua(Util.obterInteger(dadosContaSimularHelper[14].toString()));
			}else{

				contaSimularCalculoHelper.setConsumoFaturadoAgua(0);
			}

			// Consumo Faturado de Esgoto
			if(dadosContaSimularHelper[15] != null){

				contaSimularCalculoHelper.setConsumoFaturadoEsgoto(Util.obterInteger(dadosContaSimularHelper[15].toString()));
			}else{

				contaSimularCalculoHelper.setConsumoFaturadoEsgoto(0);
			}

			// Percentual de Esgoto
			if(dadosContaSimularHelper[16] != null){

				contaSimularCalculoHelper.setPercentualEsgoto(new BigDecimal(dadosContaSimularHelper[16].toString()));
			}else{

				contaSimularCalculoHelper.setPercentualEsgoto(BigDecimal.ZERO);
			}

			// Im�vel
			contaSimularCalculoHelper.setIdImovel(Util.obterInteger(dadosContaSimularHelper[13].toString()));

			// Tarifa do Im�vel
			if(dadosContaSimularHelper[17] != null){

				contaSimularCalculoHelper.setIdConsumoTarifaImovel(Util.obterInteger(dadosContaSimularHelper[17].toString()));
			}

			colecaoContasSimularCalculoHelper.add(contaSimularCalculoHelper);
		}

		Collection<CalcularValoresAguaEsgotoHelper> colecaoCalcularValoresAguaEsgotoHelper = null;
		MedicaoHistorico medicaoHistorico = null;
		Date dataLeituraAnterior = null;
		Date dataLeituraAtual = null;
		Collection<MedicaoHistorico> colecaoMedicaoHistorico = null;
		FiltroMedicaoHistorico filtroMedicaoHistorico = new FiltroMedicaoHistorico();
		FiltroContaCategoria filtroContaCategoria = new FiltroContaCategoria();
		FiltroContaCategoriaHistorico filtroContaCategoriaHistorico = new FiltroContaCategoriaHistorico();
		Collection<Categoria> colecaoCategoria = new ArrayList<Categoria>();
		Categoria categoria = null;
		FiltroConsumoHistorico filtroConsumoHistorico = new FiltroConsumoHistorico();
		Imovel imovel = null;

		for(ContaSimularCalculoDadosReaisHelper contaHelper : colecaoContasSimularCalculoHelper){

			// Im�vel
			imovel = new Imovel(contaHelper.getIdImovel());
			imovel.setConsumoTarifa(new ConsumoTarifa(contaHelper.getIdConsumoTarifaImovel()));

			// Obtendo as Categorias da Conta
			colecaoCategoria.clear();
			if(contaHelper.getIndicadorHistorico().equals(ConstantesSistema.NAO)){

				filtroContaCategoria.limparListaParametros();
				filtroContaCategoria.adicionarParametro(new ParametroSimples(FiltroContaCategoria.CONTA_ID, contaHelper.getIdConta()));
				filtroContaCategoria.adicionarCaminhoParaCarregamentoEntidade(FiltroContaCategoria.CATEGORIA);
				Collection<ContaCategoria> colecaoContaCategoria = fachada.pesquisar(filtroContaCategoria, ContaCategoria.class.getName());

				categoria = null;
				for(ContaCategoria contaCategoria : colecaoContaCategoria){

					categoria = contaCategoria.getComp_id().getCategoria();
					categoria.setQuantidadeEconomiasCategoria(contaCategoria.getQuantidadeEconomia().intValue());
					colecaoCategoria.add(categoria);
				}
			}else{

				filtroContaCategoriaHistorico.limparListaParametros();
				filtroContaCategoriaHistorico.adicionarParametro(new ParametroSimples(FiltroContaCategoriaHistorico.CONTA_HISTORICO_ID,
								contaHelper
								.getIdConta()));
				filtroContaCategoriaHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroContaCategoriaHistorico.CATEGORIA);
				Collection<ContaCategoriaHistorico> colecaoContaCategoriaHistorico = fachada.pesquisar(filtroContaCategoriaHistorico,
								ContaCategoriaHistorico.class.getName());

				categoria = null;
				for(ContaCategoriaHistorico contaCategoriaHistorico : colecaoContaCategoriaHistorico){

					categoria = contaCategoriaHistorico.getComp_id().getCategoria();
					categoria.setQuantidadeEconomiasCategoria(Integer.valueOf(String.valueOf(contaCategoriaHistorico
									.getQuantidadeEconomia())));
					colecaoCategoria.add(categoria);
				}
			}

			// Obtendo as datas de leitura atual e anterior da referencia da conta
			filtroMedicaoHistorico.limparListaParametros();
			filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.LIGACAO_AGUA_ID, imovel.getId()));
			filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.ANO_MES_REFERENCIA_FATURAMENTO,
							contaHelper.getAnoMesReferenciaConta()));

			colecaoMedicaoHistorico = fachada.pesquisar(filtroMedicaoHistorico,
							MedicaoHistorico.class.getName());

			medicaoHistorico = null;
			dataLeituraAnterior = null;
			dataLeituraAtual = null;

			if(!Util.isVazioOrNulo(colecaoMedicaoHistorico)){

				medicaoHistorico = (MedicaoHistorico) Util.retonarObjetoDeColecao(colecaoMedicaoHistorico);

				if(medicaoHistorico.getDataLeituraAnteriorFaturamento() != null){

					dataLeituraAnterior = medicaoHistorico.getDataLeituraAnteriorFaturamento();

					if(medicaoHistorico.getDataLeituraAtualFaturamento() != null){

						dataLeituraAtual = medicaoHistorico.getDataLeituraAtualFaturamento();
					}else{

						dataLeituraAtual = Util.adicionarNumeroDiasDeUmaData(dataLeituraAnterior, 30);
					}
				}else{

					dataLeituraAnterior = Util.gerarDataInicialApartirAnoMesRefencia(contaHelper.getAnoMesReferenciaConta());
					dataLeituraAtual = Util.adicionarNumeroDiasDeUmaData(dataLeituraAnterior, 30);
				}
			}else{

				dataLeituraAnterior = Util.gerarDataInicialApartirAnoMesRefencia(contaHelper.getAnoMesReferenciaConta());
				dataLeituraAtual = Util.adicionarNumeroDiasDeUmaData(dataLeituraAnterior, 30);
			}

			// Obtendo o consumo m�nimo da refer�ncia da conta
			filtroConsumoHistorico.limparListaParametros();
			filtroConsumoHistorico.adicionarParametro(new ParametroSimples(FiltroConsumoHistorico.IMOVEL_ID, imovel.getId()));
			filtroConsumoHistorico.adicionarParametro(new ParametroSimples(FiltroConsumoHistorico.ANO_MES_FATURAMENTO, contaHelper
							.getAnoMesReferenciaConta()));

			int consumoMinimoLigacao = 0;
			Collection<ConsumoHistorico> colecaoConsumoHistorico = fachada.pesquisar(filtroConsumoHistorico,
							ConsumoHistorico.class.getName());

			if(!Util.isVazioOrNulo(colecaoConsumoHistorico)){

				consumoMinimoLigacao = ((ConsumoHistorico) Util.retonarObjetoDeColecao(colecaoConsumoHistorico)).getConsumoMinimo();
			}

			if(consumoMinimoLigacao == 0){

				consumoMinimoLigacao = fachada.obterConsumoMinimoLigacao(imovel, colecaoCategoria);
			}

			colecaoCalcularValoresAguaEsgotoHelper = fachada.calcularValoresAguaEsgoto(contaHelper.getAnoMesReferenciaConta(),
							contaHelper.getIdLigacaoAguaSituacao(), contaHelper.getIdLigacaoEsgotoSituacao(),
							contaHelper.getIndicadorFaturamentoAgua(), contaHelper.getIndicadorFaturamentoEsgoto(), colecaoCategoria,
							contaHelper.getConsumoFaturadoAgua(), contaHelper.getConsumoFaturadoEsgoto(), consumoMinimoLigacao,
							dataLeituraAnterior, dataLeituraAtual, contaHelper.getPercentualEsgoto(), consumoTarifaRecalculo.getId(),
							imovel.getId(), consumoTarifaVigencia);

			BigDecimal valorRecalculadoAgua = BigDecimal.ZERO;
			BigDecimal valorRecalculadoEsgoto = BigDecimal.ZERO;

			for(CalcularValoresAguaEsgotoHelper calcularValoresAguaEsgotoHelper : colecaoCalcularValoresAguaEsgotoHelper){

				if(calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria() != null){

					valorRecalculadoAgua = valorRecalculadoAgua.add(calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria());
				}

				if(calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria() != null){

					valorRecalculadoEsgoto = valorRecalculadoEsgoto.add(calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria());
				}
			}

			contaHelper.setValorRecalculadoAgua(valorRecalculadoAgua);
			contaHelper.setValorRecalculadoEsgoto(valorRecalculadoEsgoto);

			// Valor da Conta Recalculada
			contaHelper.setValorRecalculadoTotal(contaHelper.getValorTotalContaComDadosRecalculo());

			// Diferen�a (total original - total recalculado)
			contaHelper.setValorDiferenca(contaHelper.getValorOriginalTotal().subtract(contaHelper.getValorRecalculadoTotal()));
		}

		sessao.setAttribute("colecaoContasSimularCalculoHelper", colecaoContasSimularCalculoHelper);

		return retorno;
	}
}