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

package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.bean.GerarRelacaoDebitosHelper;
import gcom.cadastro.imovel.bean.GerarRelacaoDebitosImovelHelper;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.OpcaoAgrupamento;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.gui.ActionServletException;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.math.BigDecimal;
import java.util.*;

/**
 * [UC0227] - Gerar Rela��o de D�bitos
 * 
 * @author Rafael Santos
 * @date 25/05/2006
 */
public class RelatorioGerarRelacaoDebitos
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da classe RelatorioDadosEconomiaImovel
	 */
	public RelatorioGerarRelacaoDebitos(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_GERAR_RELACAO_DEBITOS);
	}

	@Deprecated
	public RelatorioGerarRelacaoDebitos() {

		super(null, "");
	}

	private void ordenarContasPeloValor(String indicadorOrdenacao, final String indicadorOrdenacaoAscDesc,
					RelatorioGerarRelacaoDebitosBean relatorio){

		if(indicadorOrdenacao != null && indicadorOrdenacao.equals("4") && indicadorOrdenacaoAscDesc != null){
			Collections.sort(relatorio.getArrayRelatorioGerarRelacaoDebitosContasBean(),
							new Comparator<RelatorioGerarRelacaoDebitosContasBean>() {

								public int compare(RelatorioGerarRelacaoDebitosContasBean o1, RelatorioGerarRelacaoDebitosContasBean o2){

									if(indicadorOrdenacaoAscDesc.equalsIgnoreCase("ASC")){
										return Double.valueOf(o1.getContaValorOriginal().replace(".", "").replace(",", ".")).compareTo(
														Double.valueOf(o2.getContaValorOriginal().replace(".", "").replace(",", ".")));

									}

									return Double.valueOf(o2.getContaValorOriginal().replace(".", "").replace(",", ".")).compareTo(
													Double.valueOf(o1.getContaValorOriginal().replace(".", "").replace(",", ".")));

								}

							});

		}
	}

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * <Breve descri��o sobre o subfluxo>
	 * <Identificador e nome do subfluxo>
	 * <Breve descri��o sobre o fluxo secund�rio>
	 * <Identificador e nome do fluxo secund�rio>
	 * 
	 * @author Rafael Santos
	 * @date 26/05/2006
	 * @return
	 * @throws TarefaException
	 */
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		// id da genrencia regional
		String gerenciaRegional = (String) getParametro("gerenciaRegional");

		final String idUnidadeNegocio = (String) getParametro("unidadeNegocioID");

		// numero da quadra origem
		String qudraOrigem = (String) getParametro("quadraOrigem");
		// numero quadra destino
		String quadraDestino = (String) getParametro("quadraDestino");
		// lote origem
		String loteOrigem = (String) getParametro("loteOrigem");
		// lote destino
		String loteDestino = (String) getParametro("loteDestino");
		// cep
		String cep = (String) getParametro("cep");
		// id localidade origem
		String localidadeOrigem = (String) getParametro("localidadeOrigem");
		// id localidade destino
		String localidadeDestino = (String) getParametro("localidadeDestino");
		// setor comercial origem CD
		String setorComercialOrigemCD = (String) getParametro("setorComercialOrigemCD");
		// setor comercial destino CD
		String setorComercialDestinoCD = (String) getParametro("setorComercialDestinoCD");
		// cliente ID
		String clienteID = (String) getParametro("clienteID");
		// municipio ID
		String municipioID = (String) getParametro("municipioID");
		// bairro ID
		String bairroID = (String) getParametro("bairroID");
		// logradouro ID
		String logradouroID = (String) getParametro("logradouroID");
		// cliente relacao tipo ID
		String clienteRelacaoTipoID = (String) getParametro("clienteRelacaoTipoID");
		// cliente tipo ID
		String clienteTipoID = (String) getParametro("clienteTipoID");
		// imovel condominio ID
		String imovelCondominioID = (String) getParametro("imovelCondominioID");
		// imovel Principal ID
		String imovelPrincipalID = (String) getParametro("imovelPrincipalID");
		// nome Conta ID
		String nomeContaID = (String) getParametro("nomeContaID");
		// situacao Agua
		String[] situacaoAgua = (String[]) getParametro("situacaoAgua");
		// situacao Ligacao Esgoto
		String[] situacaoLigacaoEsgoto = (String[]) getParametro("situacaoLigacaoEsgoto");
		// consumo Minimo Inicial
		String consumoMinimoInicial = (String) getParametro("consumoMinimoInicial");
		// consumo Minimo Final
		String consumoMinimoFinal = (String) getParametro("consumoMinimoFinal");
		// consumo Minimo Fixado Esgoto Inicial
		String consumoMinimoFixadoEsgotoInicial = (String) getParametro("consumoMinimoFixadoEsgotoInicial");
		// consumo Minimo Fixado Esgoto Final
		String consumoMinimoFixadoEsgotoFinal = (String) getParametro("consumoMinimoFixadoEsgotoFinal");

		String consumoFixadoEsgotoPocoInicial = (String) getParametro("consumoFixadoEsgotoPocoInicial");

		String consumoFixadoEsgotoPocoFinal = (String) getParametro("consumoFixadoEsgotoPocoFinal");

		// intervalo Percentual Esgoto Inicial
		String intervaloPercentualEsgotoInicial = (String) getParametro("intervaloPercentualEsgotoInicial");
		// intervalor Percentual Esgoto Final
		String intervaloPercentualEsgotoFinal = (String) getParametro("intervaloPercentualEsgotoFinal");
		// indicador Medicao
		String indicadorMedicao = (String) getParametro("indicadorMedicao");
		// tipo Medicao ID
		String tipoMedicaoID = (String) getParametro("tipoMedicaoID");
		// intervalo Media Minima Imovel Inicial
		String intervaloMediaMinimaImovelInicial = (String) getParametro("intervaloMediaMinimaImovelInicial");
		// intervalo Media Minima Imovel Final
		String intervaloMediaMinimaImoveFinal = (String) getParametro("intervaloMediaMinimaImoveFinal");
		// intervalo Media Minima Hidrometro Inicial
		String intervaloMediaMinimaHidrometroInicial = (String) getParametro("intervaloMediaMinimaHidrometroInicial");
		// intervalo Media Minima Hidrometro Final
		String intervaloMediaMinimaHidrometroFinal = (String) getParametro("intervaloMediaMinimaHidrometroFinal");
		// perfil Imovel ID
		String perfilImovelID = (String) getParametro("perfilImovelID");
		// categoria Imovel ID
		String categoriaImovelID = (String) getParametro("categoriaImovelID");
		// sub categoria ID
		String subCategoriaID = (String) getParametro("subCategoriaID");
		// quantidade Economias Inicial
		String quantidadeEconomiasInicial = (String) getParametro("quantidadeEconomiasInicial");
		// quantidade Economias Final
		String quantidadeEconomiasFinal = (String) getParametro("quantidadeEconomiasFinal");
		// numero Pontos Inicial
		String numeroPontosInicial = (String) getParametro("numeroPontosInicial");
		// numero Pontos Final
		String numeroPontosFinal = (String) getParametro("numeroPontosFinal");
		// numero Moradores Inicial
		String numeroMoradoresInicial = (String) getParametro("numeroMoradoresInicial");
		// numero Moradoras Final
		String numeroMoradoresFinal = (String) getParametro("numeroMoradoresFinal");
		// area Construida Inicial
		String areaConstruidaInicial = (String) getParametro("areaConstruidaInicial");
		// area Construida Final
		String areaConstruidaFinal = (String) getParametro("areaConstruidaFinal");
		// area Construida Faixa
		String areaConstruidaFaixa = (String) getParametro("areaConstruidaFaixa");
		// poco Tipo ID
		String pocoTipoID = (String) getParametro("pocoTipoID");
		// tipo Situacao Faturamento ID
		String tipoSituacaoFaturamentoID = (String) getParametro("tipoSituacaoFaturamentoID");
		// tipo Situacao Especial Cobranca ID
		String tipoSituacaoEspecialCobrancaID = (String) getParametro("tipoSituacaoEspecialCobrancaID");
		// situacao Cobranca ID
		String situacaoCobrancaID = (String) getParametro("situacaoCobrancaID");
		// dia Vencimento Alternativo
		String diaVencimentoAlternativo = (String) getParametro("diaVencimentoAlternativo");
		// ocorrencia Cadastro
		String ocorrenciaCadastro = (String) getParametro("ocorrenciaCadastro");
		// tarifa Consumo
		String tarifaConsumo = (String) getParametro("tarifaConsumo");
		// anormalidade Elo
		String anormalidadeElo = (String) getParametro("anormalidadeElo");

		// Indicador de Ordena��o
		String indicadorOrdenacao = (String) getParametro("indicadorOrdenacao");

		// Aba de D�bito
		String[] tipoDebito = (String[]) getParametro("tipoDebito");
		String valorDebitoInicial = (String) getParametro("valorDebitoInicial");
		String valorDebitoFinal = (String) getParametro("valorDebitoFinal");
		String qtdContasInicial = (String) getParametro("qtdContasInicial");
		String qtdContasFinal = (String) getParametro("qtdContasFinal");
		String referenciaFaturaInicial = (String) getParametro("referenciaFaturaInicial");
		String referenciaFaturaFinal = (String) getParametro("referenciaFaturaFinal");
		String vencimentoInicial = (String) getParametro("vencimentoInicial");
		String vencimentoFinal = (String) getParametro("vencimentoFinal");
		String qtdImoveis = (String) getParametro("qtdImoveis");
		String qtdMaiores = (String) getParametro("qtdMaiores");
		// Tipo de D�bito
		/*
		 * Collection<Integer> colecaoTipoDebito = new ArrayList();
		 * if (tipoDebito != null && tipoDebito.length > 0) {
		 * String[] tiposDebito = tipoDebito;
		 * for (int i = 0; i < tiposDebito.length; i++) {
		 * if (new Integer(tiposDebito[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO) {
		 * colecaoTipoDebito.add(new Integer(tiposDebito[i]));
		 * }
		 * }
		 * }
		 */

		String indicadorOpcaoAgrupamento = (String) getParametro("indicadorOpcaoAgrupamento");
		String indicadorOrdenacaoAscDesc = (String) getParametro("indicadorOrdenacaoAscDesc");

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		Fachada fachada = Fachada.getInstancia();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("mesAnoArrecadacao", Util.formatarAnoMesParaMesAno(sistemaParametro.getAnoMesArrecadacao().intValue()));

		parametros.put("tipoFormatoRelatorio", "R0610");

		parametros.put("indicadorOpcaoAgrupamento", indicadorOpcaoAgrupamento);

		OpcaoAgrupamento opcaoAgrupamento = OpcaoAgrupamento.valuesOf(indicadorOpcaoAgrupamento);

		switch(opcaoAgrupamento){
			case CATEGORIA:
				parametros.put("agrupamento", "Agrupado por Categoria");
				break;
			case GRUPO_FATURAMENTO:
				parametros.put("agrupamento", "Agrupado por Grupo de Faturamento");
				break;

			case GERENCIA_REGIONAL_LOCALIDADE:
				parametros.put("agrupamento", "Agrupado por Ger�ncia Regional/Localidade");
				break;
			case PERIODO_ANUAL:
				parametros.put("agrupamento", "Agrupado por Per�odo de Refer�ncia Anual do D�bito");
				break;
			case PERIODO_MENSAL:
				parametros.put("agrupamento", "Agrupado por Per�odo de Refer�ncia Mensal do D�bito");
				break;

		}

		byte[] retorno = null;

		Collection colecaoDadosRelatorio = fachada.gerarRelacaoDebitos(imovelCondominioID, imovelPrincipalID, nomeContaID, situacaoAgua,
						consumoMinimoInicial, consumoMinimoFinal, situacaoLigacaoEsgoto, consumoMinimoFixadoEsgotoInicial,
						consumoMinimoFixadoEsgotoFinal, intervaloPercentualEsgotoInicial, intervaloPercentualEsgotoFinal,
						intervaloMediaMinimaImovelInicial, intervaloMediaMinimaImoveFinal, intervaloMediaMinimaHidrometroInicial,
						intervaloMediaMinimaHidrometroFinal, perfilImovelID, pocoTipoID, tipoSituacaoFaturamentoID, situacaoCobrancaID,
						tipoSituacaoEspecialCobrancaID, anormalidadeElo, areaConstruidaInicial, areaConstruidaFinal, ocorrenciaCadastro,
						tarifaConsumo, gerenciaRegional, localidadeOrigem, localidadeDestino, setorComercialOrigemCD,
						setorComercialDestinoCD, qudraOrigem, quadraDestino, loteOrigem, loteDestino, cep, logradouroID, bairroID,
						municipioID, tipoMedicaoID, indicadorMedicao, subCategoriaID, categoriaImovelID, quantidadeEconomiasInicial,
						quantidadeEconomiasFinal, diaVencimentoAlternativo, clienteID, clienteTipoID, clienteRelacaoTipoID,
						numeroPontosInicial, numeroPontosFinal, numeroMoradoresInicial, numeroMoradoresFinal, areaConstruidaFaixa,
						tipoDebito, valorDebitoInicial, valorDebitoFinal, qtdContasInicial, qtdContasFinal, referenciaFaturaInicial,
						referenciaFaturaFinal, vencimentoInicial, vencimentoFinal, qtdImoveis, qtdMaiores, indicadorOrdenacao,
						idUnidadeNegocio, consumoFixadoEsgotoPocoInicial, consumoFixadoEsgotoPocoFinal, indicadorOpcaoAgrupamento,
						indicadorOrdenacaoAscDesc);

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		// bean do relatorio
		RelatorioGerarRelacaoDebitosBean relatorioGerarRelacaoDebitosBean = null;

		// dados para o relatorio
		if(colecaoDadosRelatorio != null && !colecaoDadosRelatorio.isEmpty()){

			Iterator iteratorColecaoDadosRelatorio = colecaoDadosRelatorio.iterator();
			Integer idLocalidade = null;
			Integer idGerencia = null;
			Integer agrupamentoAno = 1500;
			String idImovel = "-1";
			int countContasLocalidade = 0;
			int countImovelLocalidade = 0;
			int countDebitosLocalidade = 0;
			int countContasGerencia = 0;
			int countImovelGerencia = 0;
			int countDebitosGerencia = 0;
			int countContasTotal = 0;
			int countImovelTotal = 0;
			int countDebitosTotal = 0;
			BigDecimal totalDebitosLocalidade = new BigDecimal("0.00");
			BigDecimal totalDebitosGerencia = new BigDecimal("0.00");
			BigDecimal totalDebitosTotal = new BigDecimal("0.00");

			while(iteratorColecaoDadosRelatorio.hasNext()){
				GerarRelacaoDebitosHelper gerarRelacaoDebitosHelper = (GerarRelacaoDebitosHelper) iteratorColecaoDadosRelatorio.next();

				countImovelTotal++;

				String idImovelRelatorio = gerarRelacaoDebitosHelper.getGerarRelacaoDebitosImovelHelper().getIdImovel();
				if(!idImovelRelatorio.equals(idImovel)){
					idImovel = idImovelRelatorio;
					countImovelLocalidade++;
					countImovelGerencia++;
				}

				if(gerarRelacaoDebitosHelper.getTotalGeralAtualizado() != null){
					totalDebitosLocalidade = totalDebitosLocalidade.add(gerarRelacaoDebitosHelper.getTotalGeralAtualizado());
					totalDebitosGerencia = totalDebitosGerencia.add(gerarRelacaoDebitosHelper.getTotalGeralAtualizado());

				}

				Collection colecaoRelatorioGerarRelacaoDebitosGuiasPagamentoBean = null;

				colecaoRelatorioGerarRelacaoDebitosGuiasPagamentoBean = new ArrayList();

				// carregar os dados das guias de pagamento
				if(gerarRelacaoDebitosHelper.getColecaoGuiasPagamento() != null
								&& !gerarRelacaoDebitosHelper.getColecaoGuiasPagamento().isEmpty()){

					Iterator iteratorColecaoGuiasPagamento = gerarRelacaoDebitosHelper.getColecaoGuiasPagamento().iterator();

					RelatorioGerarRelacaoDebitosGuiasPagamentoBean relatorioGerarRelacaoDebitosGuiasPagamentoBean = null;
					RelatorioGerarRelacaoDebitosGuiasPagamentoTotalBean relatorioGerarRelacaoDebitosGuiasPagamentoTotalBean = null;

					Collection colecaoRelatorioGerarRelacaoDebitosGuiaPagamentoTotal = null;

					while(iteratorColecaoGuiasPagamento.hasNext()){

						GuiaPagamentoValoresHelper guiaPagamentoValoresHelper = (GuiaPagamentoValoresHelper) iteratorColecaoGuiasPagamento
										.next();

						colecaoRelatorioGerarRelacaoDebitosGuiaPagamentoTotal = new ArrayList();
						relatorioGerarRelacaoDebitosGuiasPagamentoTotalBean = new RelatorioGerarRelacaoDebitosGuiasPagamentoTotalBean();
						// total da guia de pagamento
						relatorioGerarRelacaoDebitosGuiasPagamentoTotalBean.setGuiaValorTotal(Util
										.formatarMoedaReal(gerarRelacaoDebitosHelper.getTotalGuiasPagamento()));
						colecaoRelatorioGerarRelacaoDebitosGuiaPagamentoTotal.add(relatorioGerarRelacaoDebitosGuiasPagamentoTotalBean);

						// dados da guia de pagamento
						relatorioGerarRelacaoDebitosGuiasPagamentoBean = new RelatorioGerarRelacaoDebitosGuiasPagamentoBean(
										Util.formatarData(new Date()),// guiaPagamentoValoresHelper
										// .getGuiaPagamento()
										// .getDataVencimento()),
										Util.formatarMoedaReal(guiaPagamentoValoresHelper.getValorTotalPrestacao()),
										colecaoRelatorioGerarRelacaoDebitosGuiaPagamentoTotal);

						colecaoRelatorioGerarRelacaoDebitosGuiasPagamentoBean.add(relatorioGerarRelacaoDebitosGuiasPagamentoBean);
					}// fim do while de guias
				}// fim da condi��o das guias

				BigDecimal contaDacGuia = gerarRelacaoDebitosHelper.getTotalContas().add(
								gerarRelacaoDebitosHelper.getTotalDebitosACobrar().add(gerarRelacaoDebitosHelper.getTotalGuiasPagamento()));
				BigDecimal acrescimo = gerarRelacaoDebitosHelper.getTotalMulta().add(
								gerarRelacaoDebitosHelper.getTotalJuros().add(gerarRelacaoDebitosHelper.getTotalAtualizacaoMonetaria()));

				// carrega os dados dos valores totais do imovel
				RelatorioGerarRelacaoDebitosTotaisImovelBean relatorioGerarRelacaoDebitosTotaisImovelBean = new RelatorioGerarRelacaoDebitosTotaisImovelBean(
								Util.formatarMoedaReal(gerarRelacaoDebitosHelper.getTotalContas()),
								Util.formatarMoedaReal(gerarRelacaoDebitosHelper.getTotalDebitosACobrar()),
								Util.formatarMoedaReal(contaDacGuia),// Util.formatarMoedaReal(gerarRelacaoDebitosHelper.getTotalContas()),
								Util.formatarMoedaReal(gerarRelacaoDebitosHelper.getTotalGuiasPagamento()),
								Util.formatarMoedaReal(acrescimo),// Util.formatarMoedaReal(gerarRelacaoDebitosHelper.getTotalMulta()),
								Util.formatarMoedaReal(gerarRelacaoDebitosHelper.getTotalJuros()),
								Util.formatarMoedaReal(gerarRelacaoDebitosHelper.getTotalAtualizacaoMonetaria()),
								Util.formatarMoedaReal(gerarRelacaoDebitosHelper.getTotalGeralAtualizado()),
								colecaoRelatorioGerarRelacaoDebitosGuiasPagamentoBean);

				// adiciona os dados dos valores totais do imovel
				Collection colecaoRelatorioGerarRelacaoDebitosTotaisImovelBean = new ArrayList();

				if(opcaoAgrupamento.equals(OpcaoAgrupamento.PERIODO_ANUAL)){

					String ano = String.valueOf(
									new ArrayList<ContaValoresHelper>(gerarRelacaoDebitosHelper.getColecaoContas()).get(0).getConta()
													.getReferencia()).substring(0, 4);

					Integer agrupamentoAnoAtual = new Integer(ano);

					if(!agrupamentoAno.equals(agrupamentoAnoAtual)){
						agrupamentoAno = agrupamentoAnoAtual;
						colecaoRelatorioGerarRelacaoDebitosTotaisImovelBean.add(relatorioGerarRelacaoDebitosTotaisImovelBean);

					}
				}else{

					colecaoRelatorioGerarRelacaoDebitosTotaisImovelBean.add(relatorioGerarRelacaoDebitosTotaisImovelBean);
				}

				// colecao dos creditos a realizar
				Collection colecaoRelatorioGerarRelacaoDebitosTipoDebitoCreditoBean = null;

				colecaoRelatorioGerarRelacaoDebitosTipoDebitoCreditoBean = new ArrayList();

				// carrega os dados de debito a cobrar e credito a realizar
				if(gerarRelacaoDebitosHelper.getColecaoDebitosACobrarCreditoARealizar() != null
								&& !gerarRelacaoDebitosHelper.getColecaoDebitosACobrarCreditoARealizar().isEmpty()){

					RelatorioGerarRelacaoDebitosTipoDebitoCreditoBean relatorioGerarRelacaoDebitosTipoDebitoCreditoBean = null;

					Iterator iteratorColecaoDebitosACobrarCreditoARealizar = gerarRelacaoDebitosHelper
									.getColecaoDebitosACobrarCreditoARealizar().iterator();
					while(iteratorColecaoDebitosACobrarCreditoARealizar.hasNext()){

						countDebitosLocalidade++;
						countDebitosGerencia++;
						countDebitosTotal++;

						relatorioGerarRelacaoDebitosTipoDebitoCreditoBean = new RelatorioGerarRelacaoDebitosTipoDebitoCreditoBean();

						Object object = iteratorColecaoDebitosACobrarCreditoARealizar.next();
						if(object instanceof DebitoACobrar){
							DebitoACobrar debitoACobrar = (DebitoACobrar) object;

							relatorioGerarRelacaoDebitosTipoDebitoCreditoBean.setDebitoTipoDebito(debitoACobrar.getDebitoTipo()
											.getDescricao());
							relatorioGerarRelacaoDebitosTipoDebitoCreditoBean.setDebitoValor(Util.formatarMoedaReal(debitoACobrar
											.getValorTotal()));

						}else if(object instanceof CreditoARealizar){
							CreditoARealizar creditoARealizar = (CreditoARealizar) object;

							relatorioGerarRelacaoDebitosTipoDebitoCreditoBean.setDebitoTipoDebito(creditoARealizar.getCreditoTipo()
											.getDescricao());
							relatorioGerarRelacaoDebitosTipoDebitoCreditoBean.setDebitoValor(Util.formatarMoedaReal(creditoARealizar
											.getValorTotal()));

						}

						colecaoRelatorioGerarRelacaoDebitosTipoDebitoCreditoBean.add(relatorioGerarRelacaoDebitosTipoDebitoCreditoBean);
					}// fim do whilede creditos
				}// fim if decreditos

				// colecao de contas
				Collection colecaoRelatorioGerarRelacaoDebitosContasBean = null;

				colecaoRelatorioGerarRelacaoDebitosContasBean = new ArrayList();

				// carregar a colecao de contas
				if(gerarRelacaoDebitosHelper.getColecaoContas() != null && !gerarRelacaoDebitosHelper.getColecaoContas().isEmpty()){

					RelatorioGerarRelacaoDebitosContasBean relatorioGerarRelacaoDebitosContasBean = null;

					Iterator iteratorColecaoContas = gerarRelacaoDebitosHelper.getColecaoContas().iterator();

					RelatorioGerarRelacaoDebitosContasTotalBean relatorioGerarRelacaoDebitosContasTotalBean = null;
					Collection colecaoRelatorioGerarRelacaoDebitosContasTotalBean = null;

					while(iteratorColecaoContas.hasNext()){
						ContaValoresHelper contaValoresHelper = (ContaValoresHelper) iteratorColecaoContas.next();

						String revisao = "";
						if(contaValoresHelper.getConta().getContaMotivoRevisao() != null
										&& contaValoresHelper.getConta().getContaMotivoRevisao().getId() != null){
							revisao = new String("R");
						}

						String codigoBarras = "";

						String anoMesFormatado = "";
						String anoMesRecebido = "" + contaValoresHelper.getConta().getReferencia();
						if(anoMesRecebido.length() < 6){
							anoMesFormatado = anoMesRecebido;
						}else{
							String mes = anoMesRecebido.substring(4, 6);
							String ano = anoMesRecebido.substring(0, 4);
							anoMesFormatado = mes + "" + ano;
						}

						codigoBarras = fachada.obterRepresentacaoNumericaCodigoBarra(new Integer(3), new BigDecimal(contaValoresHelper
										.getConta().getValorTotalConta()), new Integer(gerarRelacaoDebitosHelper
										.getGerarRelacaoDebitosImovelHelper().getIdLocalidade()), new Integer(gerarRelacaoDebitosHelper
										.getGerarRelacaoDebitosImovelHelper().getIdImovel()), anoMesFormatado, new Integer(new Short(
										contaValoresHelper.getConta().getDigitoVerificadorConta()).toString()), null, null, null, null,
										null, null, null, null, null);

						// Formata a representa��o n�merica do c�digo de
						// barras
						String representacaoNumericaCodBarraFormatada = codigoBarras.substring(0, 11) + "-"
										+ codigoBarras.substring(11, 12) + " " + codigoBarras.substring(12, 23) + "-"
										+ codigoBarras.substring(23, 24) + " " + codigoBarras.substring(24, 35) + "-"
										+ codigoBarras.substring(35, 36) + " " + codigoBarras.substring(36, 47) + "-"
										+ codigoBarras.substring(47, 48);

						colecaoRelatorioGerarRelacaoDebitosContasTotalBean = new ArrayList();
						// dados do total da conta
						relatorioGerarRelacaoDebitosContasTotalBean = new RelatorioGerarRelacaoDebitosContasTotalBean(
										Util.formatarMoedaReal(gerarRelacaoDebitosHelper.getTotalContas()),
										Util.formatarMoedaReal(gerarRelacaoDebitosHelper.getTotalContaAtualizado()));

						colecaoRelatorioGerarRelacaoDebitosContasTotalBean.add(relatorioGerarRelacaoDebitosContasTotalBean);

						relatorioGerarRelacaoDebitosContasBean = new RelatorioGerarRelacaoDebitosContasBean(revisao,
										Util.formatarAnoMesParaMesAno(contaValoresHelper.getConta().getReferencia()) + "-"
														+ contaValoresHelper.getConta().getDigitoVerificadorConta(),
										Util.formatarData(contaValoresHelper.getConta().getDataVencimentoConta()),
										Util.formatarMoedaReal(new BigDecimal(contaValoresHelper.getConta().getValorTotalConta())),
										representacaoNumericaCodBarraFormatada, colecaoRelatorioGerarRelacaoDebitosContasTotalBean);

						colecaoRelatorioGerarRelacaoDebitosContasBean.add(relatorioGerarRelacaoDebitosContasBean);

					}// fim while de contas

				}// fim if de contas
				GerarRelacaoDebitosImovelHelper gerarRelacaoDebitosImovelHelper = gerarRelacaoDebitosHelper
								.getGerarRelacaoDebitosImovelHelper();

				if(idLocalidade == null){
					idLocalidade = new Integer(gerarRelacaoDebitosImovelHelper.getIdLocalidade());
					countContasTotal += gerarRelacaoDebitosHelper.getColecaoContas().size();

					totalDebitosTotal = totalDebitosTotal.add(totalDebitosLocalidade);

				}else{
					Integer idLocalAtual = new Integer(gerarRelacaoDebitosImovelHelper.getIdLocalidade());
					if(!idLocalidade.equals(idLocalAtual)){
						idLocalidade = new Integer(gerarRelacaoDebitosImovelHelper.getIdLocalidade());
						countContasTotal += gerarRelacaoDebitosHelper.getColecaoContas().size();
						countDebitosLocalidade = 1;

						totalDebitosTotal = totalDebitosTotal.add(totalDebitosLocalidade);

					}
				}

				if(idGerencia == null){
					idGerencia = new Integer(gerarRelacaoDebitosImovelHelper.getIdGerenciaRegional());
				}else{
					Integer idGerenciaAtual = new Integer(gerarRelacaoDebitosImovelHelper.getIdGerenciaRegional());
					if(!idGerencia.equals(idGerenciaAtual)){
						idGerencia = new Integer(gerarRelacaoDebitosImovelHelper.getIdGerenciaRegional());
						countDebitosLocalidade = 1;

					}
				}

				String percentualEsgoto = "";
				if(gerarRelacaoDebitosImovelHelper.getPercentualEsgoto() != null){
					percentualEsgoto = percentualEsgoto + gerarRelacaoDebitosImovelHelper.getPercentualEsgoto() + " %";

				}

				countContasLocalidade = gerarRelacaoDebitosHelper.getColecaoContas().size();
				countContasGerencia = gerarRelacaoDebitosHelper.getColecaoContas().size();

				relatorioGerarRelacaoDebitosBean = new RelatorioGerarRelacaoDebitosBean(gerarRelacaoDebitosImovelHelper.getIdImovel(),
								gerarRelacaoDebitosImovelHelper.getInscricaoImovel(),
								gerarRelacaoDebitosImovelHelper.getNomeClienteUsuario(),
								gerarRelacaoDebitosImovelHelper.getNomeClienteResponsavel(), gerarRelacaoDebitosImovelHelper.getEndereco(),
								gerarRelacaoDebitosImovelHelper.getQuantidadeEconomias(),
								gerarRelacaoDebitosImovelHelper.getCategoriaPrincipal(), gerarRelacaoDebitosImovelHelper.getSituacaoAgua(),
								gerarRelacaoDebitosImovelHelper.getSituacaoEsgoto(), percentualEsgoto,
								gerarRelacaoDebitosImovelHelper.getDataCorte(), gerarRelacaoDebitosImovelHelper.getConsumoMediaImovel(),
								gerarRelacaoDebitosImovelHelper.getNomeGerenciaRegional() + " - "
												+ gerarRelacaoDebitosImovelHelper.getGerenciaRegional(),
								gerarRelacaoDebitosImovelHelper.getIdLocalidade() + " - "
												+ gerarRelacaoDebitosImovelHelper.getDescricaoLocalidade(), "" + countContasLocalidade, ""
												+ countImovelLocalidade, "" + countDebitosLocalidade, "" + countContasGerencia, ""
												+ countImovelGerencia, "" + countDebitosGerencia, "" + countContasTotal, ""
												+ countImovelTotal, "" + countDebitosTotal, Util.formatarMoedaReal(totalDebitosLocalidade),
								Util.formatarMoedaReal(totalDebitosGerencia), Util.formatarMoedaReal(totalDebitosTotal),
								colecaoRelatorioGerarRelacaoDebitosContasBean, colecaoRelatorioGerarRelacaoDebitosTipoDebitoCreditoBean,
								colecaoRelatorioGerarRelacaoDebitosTotaisImovelBean, colecaoRelatorioGerarRelacaoDebitosGuiasPagamentoBean);

				ordenarContasPeloValor(indicadorOrdenacao, indicadorOrdenacaoAscDesc, relatorioGerarRelacaoDebitosBean);

				// add item da colecao
				relatorioBeans.add(relatorioGerarRelacaoDebitosBean);

				countImovelLocalidade = 1;

				totalDebitosLocalidade = new BigDecimal("0.00");

				countImovelGerencia = 1;
				countDebitosGerencia = 1;
				totalDebitosGerencia = new BigDecimal("0.00");

			}
		}else{
			throw new ActionServletException("atencao.relatorio.vazio");
		}

		RelatorioDataSource ds = new RelatorioDataSource((List) relatorioBeans);

		// exporta o relat�rio em pdf e retorna o array de bytes
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_GERAR_RELACAO_DEBITOS, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.GERAR_RELACAO_DEBITOS, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado*/
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		final String idImovelCondominio = (String) getParametro("imovelCondominioID");
		final String idImovelPrincipal = (String) getParametro("imovelPrincipalID");
		final String[] idSituacaoLigacaoAgua = (String[]) getParametro("situacaoAgua");
		final String consumoMinimoInicialAgua = (String) getParametro("consumoMinimoInicial");
		final String consumoMinimoFinalAgua = (String) getParametro("consumoMinimoFinal");
		final String[] idSituacaoLigacaoEsgoto = (String[]) getParametro("situacaoLigacaoEsgoto");
		final String consumoMinimoInicialEsgoto = (String) getParametro("consumoMinimoFixadoEsgotoInicial");
		final String consumoMinimoFinalEsgoto = (String) getParametro("consumoMinimoFixadoEsgotoFinal");
		final String consumoFixadoEsgotoPocoInicial = (String) getParametro("consumoFixadoEsgotoPocoInicial");
		final String consumoFixadoEsgotoPocoFinal = (String) getParametro("consumoFixadoEsgotoPocoFinal");
		final String intervaloValorPercentualEsgotoInicial = (String) getParametro("intervaloPercentualEsgotoInicial");
		final String intervaloValorPercentualEsgotoFinal = (String) getParametro("intervaloPercentualEsgotoFinal");
		final String intervaloMediaMinimaImovelInicial = (String) getParametro("intervaloMediaMinimaImovelInicial");
		final String intervaloMediaMinimaImovelFinal = (String) getParametro("intervaloMediaMinimaImoveFinal");
		final String intervaloMediaMinimaHidrometroInicial = (String) getParametro("intervaloMediaMinimaHidrometroInicial");
		final String intervaloMediaMinimaHidrometroFinal = (String) getParametro("intervaloMediaMinimaHidrometroFinal");
		final String idImovelPerfil = (String) getParametro("perfilImovelID");
		final String idPocoTipo = (String) getParametro("pocoTipoID");
		final String idFaturamentoSituacaoTipo = (String) getParametro("tipoSituacaoFaturamentoID");
		final String idCobrancaSituacaoTipo = (String) getParametro("situacaoCobrancaID");
		final String idSituacaoEspecialCobranca = (String) getParametro("tipoSituacaoEspecialCobrancaID");
		final String idEloAnormalidade = (String) getParametro("anormalidadeElo");
		final String areaConstruidaInicial = (String) getParametro("areaConstruidaInicial");
		final String areaConstruidaFinal = (String) getParametro("areaConstruidaFinal");
		final String idCadastroOcorrencia = (String) getParametro("ocorrenciaCadastro");
		final String idConsumoTarifa = (String) getParametro("tarifaConsumo");
		final String idGerenciaRegional = (String) getParametro("gerenciaRegional");
		final String idLocalidadeInicial = (String) getParametro("localidadeOrigem");
		final String idLocalidadeFinal = (String) getParametro("localidadeDestino");
		final String setorComercialInicial = (String) getParametro("setorComercialOrigemCD");
		final String setorComercialFinal = (String) getParametro("setorComercialDestinoCD");
		final String quadraInicial = (String) getParametro("quadraOrigem");
		final String quadraFinal = (String) getParametro("quadraDestino");
		final String loteOrigem = (String) getParametro("loteOrigem");
		final String loteDestno = (String) getParametro("loteDestino");
		final String cep = (String) getParametro("cep");
		final String logradouro = (String) getParametro("logradouroID");
		final String bairro = (String) getParametro("logradouroID");
		final String municipio = (String) getParametro("municipioID");
		final String idTipoMedicao = (String) getParametro("tipoMedicaoID");
		final String indicadorMedicao = (String) getParametro("indicadorMedicao");
		final String idSubCategoria = (String) getParametro("subCategoriaID");
		final String idCategoria = (String) getParametro("categoriaImovelID");
		final String quantidadeEconomiasInicial = (String) getParametro("quantidadeEconomiasInicial");
		final String quantidadeEconomiasFinal = (String) getParametro("quantidadeEconomiasFinal");
		final String diaVencimento = (String) getParametro("diaVencimentoAlternativo");
		final String idCliente = (String) getParametro("clienteID");
		final String idClienteTipo = (String) getParametro("clienteTipoID");
		final String idClienteRelacaoTipo = (String) getParametro("clienteRelacaoTipoID");
		final String numeroPontosInicial = (String) getParametro("numeroPontosInicial");
		final String numeroPontosFinal = (String) getParametro("numeroPontosFinal");
		final String numeroMoradoresInicial = (String) getParametro("numeroMoradoresInicial");
		final String numeroMoradoresFinal = (String) getParametro("numeroMoradoresFinal");
		final String idAreaConstruidaFaixa = (String) getParametro("numeroMoradoresFinal");
		final String idUnidadeNegocio = (String) getParametro("unidadeNegocioID");
		final Integer relatorio = null; //
		final String cdRotaInicial = (String) getParametro("rotaInicialCD");
		final String cdRotaFinal = (String) getParametro("rotaFinalCD");
		final String sequencialRotaInicial = (String) getParametro("sequencialRotaInicial");
		final String sequencialRotaFinal = (String) getParametro("sequencialRotaFinal");
		final String segmentoInicial = (String) getParametro("segmentoInicial");
		final String segmentoFinal = (String) getParametro("segmentoFinal");
		final String subloteInicial = (String) getParametro("subloteInicial");
		final String subloteFinal = (String) getParametro("subloteFinal");

		Integer retorno = Fachada.getInstancia().obterQuantidadadeRelacaoImoveisDebitos(idImovelCondominio, idImovelPrincipal,
						idSituacaoLigacaoAgua, consumoMinimoInicialAgua, consumoMinimoFinalAgua, idSituacaoLigacaoEsgoto,
						consumoMinimoInicialEsgoto, consumoMinimoFinalEsgoto, intervaloValorPercentualEsgotoInicial,
						intervaloValorPercentualEsgotoFinal, intervaloMediaMinimaImovelInicial, intervaloMediaMinimaImovelFinal,
						intervaloMediaMinimaHidrometroInicial, intervaloMediaMinimaHidrometroFinal, idImovelPerfil, idPocoTipo,
						idFaturamentoSituacaoTipo, idCobrancaSituacaoTipo, idSituacaoEspecialCobranca, idEloAnormalidade,
						areaConstruidaInicial, areaConstruidaFinal, idCadastroOcorrencia, idConsumoTarifa, idGerenciaRegional,
						idLocalidadeInicial, idLocalidadeFinal, setorComercialInicial, setorComercialFinal, quadraInicial, quadraFinal,
						loteOrigem, loteDestno, cep, logradouro, bairro, municipio, idTipoMedicao, indicadorMedicao, idSubCategoria,
						idCategoria, quantidadeEconomiasInicial, quantidadeEconomiasFinal, diaVencimento, idCliente, idClienteTipo,
						idClienteRelacaoTipo, numeroPontosInicial, numeroPontosFinal, numeroMoradoresInicial, numeroMoradoresFinal,
						idAreaConstruidaFaixa, idUnidadeNegocio, relatorio, cdRotaInicial, cdRotaFinal, sequencialRotaInicial,
						sequencialRotaFinal, segmentoInicial, segmentoFinal, subloteInicial, subloteFinal, consumoFixadoEsgotoPocoInicial,
						consumoFixadoEsgotoPocoFinal);

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioGerarRelacaoDebitosResumido", this);
	}
}
