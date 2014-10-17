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

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.CalcularValoresAguaEsgotoHelper;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel pela simula��o do c�lculo da conta.
 * Corre��o do c�lculo ConsumoFaturadoAgua
 * 
 * @author Virg�nia Melo
 * @date 30/12/2008
 */
public class SimularCalculoContaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("simularCalculoConta");
		SimularCalculoContaActionForm simularCalculoContaActionForm = (SimularCalculoContaActionForm) actionForm;
		HttpSession sessao = httpServletRequest.getSession(false);
		Fachada fachada = Fachada.getInstancia();

		// Verifica se a situa��o da liga��o de esgoto � fatur�vel, caso n�o seja limpa e desabilita
		// campos relativos aos dados da liga��o de esgoto.
		if(simularCalculoContaActionForm.getIndicadorEsgotoFaturavel() == null
						|| simularCalculoContaActionForm.getIndicadorEsgotoFaturavel().equals(ConstantesSistema.NAO.toString())){

			simularCalculoContaActionForm.setLigacaoEsgotoPerfilId(null);
			simularCalculoContaActionForm.setConsumoFaturadoEsgoto(null);
			simularCalculoContaActionForm.setPercentualEsgoto(null);
		}

		// Declara��o de objetos
		String anoMesReferencia = null;
		Integer ligacaoAguaSituacaoID = null;
		Integer ligacaoEsgotoSituacaoID = null;
		Integer consumoTarifaID = null;
		BigDecimal percentualEsgoto = null;
		Short indicadorFaturamentoAgua = new Short("1");
		Short indicadorFaturamentoEsgoto = new Short("1");
		Integer consumoFaturadoAgua = null;
		Integer consumoFaturadoEsgoto = null;
		Date dataLeituraAnterior = null;
		Date dataLeituraAtual = null;

		// Verifica qual atributo do action form foi informado no JSP
		if(simularCalculoContaActionForm.getMesAnoReferencia() != null
						&& !simularCalculoContaActionForm.getMesAnoReferencia().equalsIgnoreCase("")){

			String mes = simularCalculoContaActionForm.getMesAnoReferencia().substring(0, 2);
			String ano = simularCalculoContaActionForm.getMesAnoReferencia().substring(3, 7);

			anoMesReferencia = Util.formatarMesAnoParaAnoMes(mes + ano);

			// Obt�m as datas de leitura anterior e atual da tabela
			// FATURAMENTO_ATIVIDADE_CRONOGRAMA.
			if(Util.verificarIdNaoVazio(simularCalculoContaActionForm.getFaturamentoGrupoID())){

				dataLeituraAnterior = this.buscarDataLeituraCronograma(Util.converterStringParaInteger(simularCalculoContaActionForm
								.getFaturamentoGrupoID()), true, Util.formatarMesAnoComBarraParaAnoMes(simularCalculoContaActionForm
								.getMesAnoReferencia()), fachada);
				dataLeituraAtual = this.buscarDataLeituraCronograma(Util.converterStringParaInteger(simularCalculoContaActionForm
								.getFaturamentoGrupoID()), false, Util.formatarMesAnoComBarraParaAnoMes(simularCalculoContaActionForm
								.getMesAnoReferencia()), fachada);
			}

			if(dataLeituraAnterior == null){

				dataLeituraAnterior = Util.converteStringParaDate("01/" + simularCalculoContaActionForm.getMesAnoReferencia());
				simularCalculoContaActionForm.setDataLeituraAnteriorMedicaoHistoricoAgua("01/"
								+ simularCalculoContaActionForm.getMesAnoReferencia());
			}

			if(dataLeituraAtual == null){

				String ultimoDiaMes = Util.obterUltimoDiaMes(Util.converterStringParaInteger(mes).intValue(), Util
								.converterStringParaInteger(ano).intValue());

				dataLeituraAtual = Util.converteStringParaDate(ultimoDiaMes + "/" + simularCalculoContaActionForm.getMesAnoReferencia());
				simularCalculoContaActionForm.setDataLeituraAtualMedicaoHistoricoAgua(ultimoDiaMes + "/"
								+ simularCalculoContaActionForm.getMesAnoReferencia());
			}
		}

		if(simularCalculoContaActionForm.getLigacaoAguaSituacaoID() != null
						&& !simularCalculoContaActionForm.getLigacaoAguaSituacaoID().equalsIgnoreCase("")){
			ligacaoAguaSituacaoID = new Integer(simularCalculoContaActionForm.getLigacaoAguaSituacaoID());
		}

		if(simularCalculoContaActionForm.getLigacaoEsgotoSituacaoID() != null
						&& !simularCalculoContaActionForm.getLigacaoEsgotoSituacaoID().equalsIgnoreCase("")){
			ligacaoEsgotoSituacaoID = new Integer(simularCalculoContaActionForm.getLigacaoEsgotoSituacaoID());
		}

		if(simularCalculoContaActionForm.getConsumoFaturadoAgua() != null
						&& !simularCalculoContaActionForm.getConsumoFaturadoAgua().equalsIgnoreCase("")){
			consumoFaturadoAgua = new Integer(simularCalculoContaActionForm.getConsumoFaturadoAgua());
		}

		if(simularCalculoContaActionForm.getConsumoFaturadoEsgoto() != null
						&& !simularCalculoContaActionForm.getConsumoFaturadoEsgoto().equalsIgnoreCase("")){
			consumoFaturadoEsgoto = new Integer(simularCalculoContaActionForm.getConsumoFaturadoEsgoto());
		}else{
			consumoFaturadoEsgoto = consumoFaturadoAgua;
		}

		if(simularCalculoContaActionForm.getConsumoTarifaID() != null
						&& !simularCalculoContaActionForm.getConsumoTarifaID().equalsIgnoreCase("")){
			consumoTarifaID = new Integer(simularCalculoContaActionForm.getConsumoTarifaID());
		}

		if(simularCalculoContaActionForm.getPercentualEsgoto() != null
						&& !simularCalculoContaActionForm.getPercentualEsgoto().equalsIgnoreCase("")){
			percentualEsgoto = Util.formatarMoedaRealparaBigDecimal((simularCalculoContaActionForm.getPercentualEsgoto()));
		}

		// Cria��o do consumo tarifa
		Imovel imovel = new Imovel();
		ConsumoTarifa consumoTarifa = new ConsumoTarifa();
		consumoTarifa.setId(consumoTarifaID);
		imovel.setConsumoTarifa(consumoTarifa);

		Collection colecaoCategoriaOUSubcategoria = null;
		Integer qtdEconnomia = null;
		int consumoMinimoLigacao = 0;

		if(sessao.getAttribute("colecaoCategoria") != null){

			colecaoCategoriaOUSubcategoria = (Collection) sessao.getAttribute("colecaoCategoria");
			qtdEconnomia = this.atualizarQuantidadeEconomiasCategoria(colecaoCategoriaOUSubcategoria, httpServletRequest);

			// Obt�m o consumo m�nimo liga��o por categoria
			consumoMinimoLigacao = fachada.obterConsumoMinimoLigacao(imovel, colecaoCategoriaOUSubcategoria);

		}else{

			colecaoCategoriaOUSubcategoria = (Collection) sessao.getAttribute("colecaoSubcategoria");
			qtdEconnomia = this.atualizarQuantidadeEconomiasSubcategoria(colecaoCategoriaOUSubcategoria, httpServletRequest);

			// Obt�m o consumo m�nimo liga��o por subcategoria
			consumoMinimoLigacao = fachada.obterConsumoMinimoLigacao(imovel, colecaoCategoriaOUSubcategoria);
		}

		/*
		 * Colocado por Raphael Rossiter em 02/04/2007
		 * [UC0157] - Simular C�lculo da Conta
		 * [FS0003] - Verificar Consumo M�nimo
		 */
		fachada.verificarConsumoFaturadoAgua(ligacaoAguaSituacaoID, consumoFaturadoAgua);

		/*
		 * Colocado por Raphael Rossiter em 02/04/2007
		 * [UC0157] - Simular C�lculo da Conta
		 * [FS0004] - Verificar Volume M�nimo
		 */
		fachada.verificarConsumoFaturadoEsgoto(ligacaoEsgotoSituacaoID, consumoFaturadoEsgoto);

		if(ligacaoEsgotoSituacaoID.intValue() == LigacaoEsgotoSituacao.LIGADO.intValue() && percentualEsgoto == null){
			throw new ActionServletException("atencao.informe.percentualEsgoto");
		}

		// Cria��o do filtro para liga��o �gua situa��o
		FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao(FiltroLigacaoAguaSituacao.DESCRICAO);
		filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, ligacaoAguaSituacaoID));
		filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		// Pesquisa liga��o �gua situa��o
		Collection colecaoLigacaoAguaSituacao = fachada.pesquisar(filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());
		LigacaoAguaSituacao ligacaoAguaSituacao = (LigacaoAguaSituacao) Util.retonarObjetoDeColecao(colecaoLigacaoAguaSituacao);
		// verifica os indicadores de Faturamento baseado no configura��o da Ligacao_Situacao
		indicadorFaturamentoAgua = ligacaoAguaSituacao.getIndicadorFaturamentoSituacao();
		// Caso n�o seja ligado ou cortado o consumo deve ser zero
		// if(ligacaoAguaSituacaoID.intValue() != LigacaoAguaSituacao.LIGADO.intValue()
		// && ligacaoAguaSituacaoID.intValue() != LigacaoAguaSituacao.CORTADO.intValue()){
		if(!indicadorFaturamentoAgua.equals(LigacaoAguaSituacao.FATURAMENTO_ATIVO)){
			consumoFaturadoAgua = new Integer(0);
		}

		FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();
		filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID, ligacaoEsgotoSituacaoID));
		Collection<LigacaoEsgotoSituacao> colecaoLigacaoEsgotoSituacao = fachada.pesquisar(filtroLigacaoEsgotoSituacao,
						LigacaoEsgotoSituacao.class.getName());
		LigacaoEsgotoSituacao ligacaoEsgotoSituacao = colecaoLigacaoEsgotoSituacao.iterator().next();
		indicadorFaturamentoEsgoto = ligacaoEsgotoSituacao.getIndicadorFaturamentoSituacao();

		// Caso n�o seja ligado o consumo deve ser zero
		if(!indicadorFaturamentoEsgoto.equals(LigacaoEsgotoSituacao.FATURAMENTO_ATIVO)){

			consumoFaturadoEsgoto = new Integer(0);
			percentualEsgoto = new BigDecimal("0.0");
		}
		Collection colecaoCalcularValoresAguaEsgotoHelper = null;

		// SB0001 � Ajuste do Consumo para M�ltiplo da Quantidade de Economias
		Map<String, String> consumoFaturadoAguaEsgoto = fachada.ajusteConsumoMultiploQuantidadeEconomia(consumoFaturadoAgua,
						consumoMinimoLigacao, consumoFaturadoEsgoto, qtdEconnomia);

		if(consumoFaturadoAguaEsgoto != null && !consumoFaturadoAguaEsgoto.isEmpty()){

			if(consumoFaturadoAguaEsgoto.get("agua") != null){

				consumoFaturadoAgua = Integer.parseInt(consumoFaturadoAguaEsgoto.get("agua"));
			}

			if(consumoFaturadoAguaEsgoto.get("esgoto") != null){

				consumoFaturadoEsgoto = Integer.parseInt(consumoFaturadoAguaEsgoto.get("esgoto"));
			}
		}

		// [UC0120] - Calcular Valores de �gua e/ou Esgoto
		colecaoCalcularValoresAguaEsgotoHelper = fachada.calcularValoresAguaEsgoto(new Integer(anoMesReferencia), ligacaoAguaSituacaoID,
						ligacaoEsgotoSituacaoID, indicadorFaturamentoAgua, indicadorFaturamentoEsgoto, colecaoCategoriaOUSubcategoria,
						consumoFaturadoAgua, consumoFaturadoEsgoto, consumoMinimoLigacao, dataLeituraAnterior, dataLeituraAtual,
						percentualEsgoto, consumoTarifa.getId(), 21720002);

		if(colecaoCalcularValoresAguaEsgotoHelper != null){

			Integer consumoAgua2 = fachada.calcularConsumoTotalAguaOuEsgotoPorCategoria(colecaoCalcularValoresAguaEsgotoHelper,
							ConstantesSistema.CALCULAR_AGUA);
			if(consumoAgua2 != null && consumoAgua2 > 0){
				simularCalculoContaActionForm.setConsumoFaturadoAgua(consumoAgua2.toString());
			}

			Integer consumoEsgoto2 = fachada.calcularConsumoTotalAguaOuEsgotoPorCategoria(colecaoCalcularValoresAguaEsgotoHelper,
							ConstantesSistema.CALCULAR_ESGOTO);
			if(consumoEsgoto2 != null && consumoEsgoto2 > 0){
				simularCalculoContaActionForm.setConsumoFaturadoEsgoto(consumoEsgoto2.toString());
			}else{
				simularCalculoContaActionForm.setConsumoFaturadoEsgoto("");
			}
		}

		// M�todo repons�vel pela totaliza��o dos valores de �gua e esgoto por
		// categoria, a partir da cole��o
		// retornada pelo [UC0120] - Calcular Valores de �gua e/ou Esgoto
		Collection colecaoCalcularValoresAguaEsgotoHelperPorCategoria = fachada
						.calcularValoresAguaEsgotoTotalizandoPorCategoria(colecaoCalcularValoresAguaEsgotoHelper);

		httpServletRequest.setAttribute("colecaoCalcularValoresAguaEsgotoHelper", colecaoCalcularValoresAguaEsgotoHelperPorCategoria);
		httpServletRequest.setAttribute("tamanhoColecaoValores", colecaoCalcularValoresAguaEsgotoHelperPorCategoria.size());

		BigDecimal valorTotalCategorias = this.obterValorTotalCategorias(colecaoCalcularValoresAguaEsgotoHelperPorCategoria, null);

		BigDecimal valorTotalAgua = this.obterValorTotalCategorias(colecaoCalcularValoresAguaEsgotoHelperPorCategoria,
						ConstantesSistema.CALCULAR_AGUA);

		BigDecimal valorTotalEsgoto = this.obterValorTotalCategorias(colecaoCalcularValoresAguaEsgotoHelperPorCategoria,
						ConstantesSistema.CALCULAR_ESGOTO);

		httpServletRequest.setAttribute("valorTotalAgua", valorTotalAgua);
		httpServletRequest.setAttribute("valorTotalEsgoto", valorTotalEsgoto);
		httpServletRequest.setAttribute("valorTotalCategorias", valorTotalCategorias);

		/*
		 * Integer consumoAgua = 0;
		 * if(consumoFaturadoAgua > consumoMinimoLigacao){
		 * consumoAgua = (consumoFaturadoAgua - consumoMinimoLigacao) / qtdEconnomia;
		 * consumoAgua = (consumoAgua * qtdEconnomia) + consumoMinimoLigacao;
		 * }else{
		 * consumoAgua = consumoMinimoLigacao;
		 * }
		 * simularCalculoContaActionForm.setConsumoFaturadoAgua(consumoAgua.toString());
		 * Integer consumoEsgoto = 0;
		 * if(qtdEconnomia != 0 && consumoFaturadoEsgoto != null){
		 * consumoEsgoto = consumoFaturadoEsgoto / qtdEconnomia;
		 * consumoEsgoto = consumoEsgoto * qtdEconnomia;
		 * simularCalculoContaActionForm.setConsumoFaturadoEsgoto(consumoEsgoto.toString());
		 * }
		 */

		/*
		 * Colocado por Raphael Rossiter em 14/03/2007
		 * Objetivo: Manipula��o dos objetos que ser�o exibidos no formul�rio de acordo com a
		 * empresa
		 */
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		httpServletRequest.setAttribute("empresaNome", sistemaParametro.getNomeAbreviadoEmpresa().trim());

		return retorno;
	}

	/**
	 * Obt�m o valor total para todas as categorias
	 * 
	 * @author Raphael Rossiter
	 * @date 27/03/2006
	 * @param colecaoCalcularValoresAguaEsgotoHelperPorCategoria
	 */
	private BigDecimal obterValorTotalCategorias(
					Collection<CalcularValoresAguaEsgotoHelper> colecaoCalcularValoresAguaEsgotoHelperPorCategoria, String totalizacao){

		BigDecimal retorno = new BigDecimal("0");

		Iterator iteratorCalcularValoresAguaEsgotoHelperPorCategoria = colecaoCalcularValoresAguaEsgotoHelperPorCategoria.iterator();

		while(iteratorCalcularValoresAguaEsgotoHelperPorCategoria.hasNext()){

			CalcularValoresAguaEsgotoHelper objeto = (CalcularValoresAguaEsgotoHelper) iteratorCalcularValoresAguaEsgotoHelperPorCategoria
							.next();

			if(totalizacao == null && objeto.getValorTotalCategoria() != null){
				retorno = retorno.add(objeto.getValorTotalCategoria());
			}else if((totalizacao != null && totalizacao.equals(ConstantesSistema.CALCULAR_AGUA))
							&& objeto.getValorFaturadoAguaCategoria() != null){
				retorno = retorno.add(objeto.getValorFaturadoAguaCategoria());
			}else if((totalizacao != null && totalizacao.equals(ConstantesSistema.CALCULAR_ESGOTO))
							&& objeto.getValorFaturadoEsgotoCategoria() != null){
				retorno = retorno.add(objeto.getValorFaturadoEsgotoCategoria());
			}
		}
		return retorno;
	}

	private Integer atualizarQuantidadeEconomiasCategoria(Collection colecaoCategorias, HttpServletRequest httpServletRequest){

		// Atualizando a quantidade de economias por categoria de acordo com o informado pelo
		// usu�rio
		Integer qtdEconnomia = 0;

		if(colecaoCategorias != null && !colecaoCategorias.isEmpty()){

			Iterator colecaoCategoriaIt = colecaoCategorias.iterator();
			Categoria categoria;
			Map<String, String[]> requestMap = httpServletRequest.getParameterMap();
			String qtdPorEconomia;

			while(colecaoCategoriaIt.hasNext()){
				categoria = (Categoria) colecaoCategoriaIt.next();

				if(requestMap.get("categoria" + categoria.getId().intValue()) != null){
					qtdPorEconomia = (requestMap.get("categoria" + categoria.getId().intValue()))[0];

					if(qtdPorEconomia == null || qtdPorEconomia.equalsIgnoreCase("")){
						throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "Quantidade de economias");
					}

					categoria.setQuantidadeEconomiasCategoria(new Integer(qtdPorEconomia));
					qtdEconnomia = Util.somaInteiros(qtdEconnomia, new Integer(qtdPorEconomia));
				}
			}
		}
		return qtdEconnomia;
	}

	private Integer atualizarQuantidadeEconomiasSubcategoria(Collection colecaoSubcategorias, HttpServletRequest httpServletRequest){

		// Atualizando a quantidade de economias por subcategoria de acordo com o informado pelo
		// usu�rio
		Integer qtdEconnomia = 0;

		if(colecaoSubcategorias != null && !colecaoSubcategorias.isEmpty()){

			Iterator colecaoSubcategoriaIt = colecaoSubcategorias.iterator();
			Subcategoria subcategoria;
			Map<String, String[]> requestMap = httpServletRequest.getParameterMap();
			String qtdPorEconomia;

			while(colecaoSubcategoriaIt.hasNext()){
				subcategoria = (Subcategoria) colecaoSubcategoriaIt.next();

				if(requestMap.get("subcategoria" + subcategoria.getId().intValue()) != null){

					qtdPorEconomia = (requestMap.get("subcategoria" + subcategoria.getId().intValue()))[0];

					if(qtdPorEconomia == null || qtdPorEconomia.equalsIgnoreCase("")){
						throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "Quantidade de economias");
					}

					subcategoria.setQuantidadeEconomias(new Integer(qtdPorEconomia));
					qtdEconnomia = Util.somaInteiros(qtdEconnomia, new Integer(qtdPorEconomia));
				}
			}
		}
		return qtdEconnomia;
	}

	/**
	 * Pesquisa a data de leitura (Campo FTAC_TMREALIZACAO da tabela
	 * FATURAMENTO_ATIVIDADE_CRONOGRAMA)
	 * 
	 * @author Ailton Sousa
	 * @date 22/07/2011
	 * @param imovel
	 * @param situacao
	 * @param anoMesReferencia
	 * @param fachada
	 */
	private Date buscarDataLeituraCronograma(Integer grupoFaturamentoID, boolean situacao, Integer anoMesReferencia, Fachada fachada){

		return fachada.buscarDataLeituraCronograma(grupoFaturamentoID, situacao, anoMesReferencia);
	}
}