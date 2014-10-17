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
 * 
 * GSANPCG
 * Eduardo Henrique
 */
package gcom.util.parametrizacao.micromedicao;

import gcom.arrecadacao.debitoautomatico.DebitoAutomatico;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgoto;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.faturamento.FaturamentoSituacaoTipo;
import gcom.faturamento.QualidadeAgua;
import gcom.faturamento.QualidadeAguaPadrao;
import gcom.micromedicao.MovimentoRoteiroEmpresa;
import gcom.micromedicao.bean.DebitoAnteriorHelper;
import gcom.micromedicao.bean.HidrometroRelatorioOSHelper;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraSituacao;
import gcom.relatorio.faturamento.RelatorioOcorrenciaGeracaoPreFatResumoHelper;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author mgrb
 *
 */
public class ArquivoFaturamentoImediatoCaerdHelper {

	private static final List<Integer> SITUACOES_LEITURA_POSITIVAS = Arrays.asList(//
					LeituraSituacao.CONFIRMADA, //
					LeituraSituacao.REALIZADA, //
					LeituraSituacao.LEITURA_ALTERADA, //
					LeituraSituacao.RECONFIRMADA);
	private static final String DATA_INICIAL_LEITURA_YYYYMMDD = "19500101";
	private static final String DATA_FUNDACAO_CASAL_YYYYMMDD = "19621201";

	private StringBuffer conteudoArquivo;
	private String nomeArquivo;

	private int qtdTipo01;

	private int qtdTipo02;
	private int qtdTipo03;
	private int qtdTipo04;
	private int qtdTipo05;
	private int qtdTipo06;
	private int qtdTipo07;

	private Map<Integer, RelatorioOcorrenciaGeracaoPreFatResumoHelper> resumoPreFaturamento;

	/**
	 * ArquivoFaturamentoImediatoCaerdHelper
	 * <p>
	 * Esse método Constroi uma instância de ArquivoFaturamentoImediatoCaerdHelper.
	 * </p>
	 * 
	 * @author Marlos Ribeiro
	 * @param anoMesReferenciaFaturamento
	 * @param idFaturamentoGrupo
	 * @since 12/04/2013
	 */
	public ArquivoFaturamentoImediatoCaerdHelper(Integer idFaturamentoGrupo, Integer anoMesReferenciaFaturamento) {

		conteudoArquivo = new StringBuffer();
		nomeArquivo = Util.formatLPad(idFaturamentoGrupo, 3, '0')
						.concat(Util.formatarAnoMesParaMesAnoSemBarra(anoMesReferenciaFaturamento)).concat(".txt");
	}

	/**
	 * Método addHeader
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param idFaturamentoGrupo
	 * @param anoMesReferenciaFaturamento
	 * @author Marlos Ribeiro
	 * @since 12/04/2013
	 */
	public void addRagistroTipo0(Integer idFaturamentoGrupo, Integer anoMesReferenciaFaturamento){
		StringBuffer buffer = new StringBuffer();
		buffer.append(0);
		buffer.append(Util.formatRPad("", 17, ' '));
		buffer.append(Util.formatRPad(anoMesReferenciaFaturamento, 6, '0'));
		buffer.append(Util.formatLPad(idFaturamentoGrupo, 3, '0'));
		buffer.append(Util.formatRPad("", 376, ' '));
		addLinha(buffer);
	}

	/**
	 * Método addLinha
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param buffer
	 * @author Marlos Ribeiro
	 * @since 12/04/2013
	 */
	private void addLinha(StringBuffer buffer){

		/*
		 * Alteração para contemplar a solicitação da OC1114586.
		 * Josenildo Neves
		 * 02/08/2013
		 * O código original permanecerá comentado, pois,
		 * Alessandro irá rever junto com o cliente se o arquivo ficará com essa alteração, ou
		 * ficará no padrão do GSAN.
		 */
		conteudoArquivo.append(buffer.toString().trim());
		//conteudoArquivo.append(buffer);
		conteudoArquivo.append(System.getProperty("line.separator"));
	}

	/**
	 * Método addRagistroTipo01
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param tarifas
	 * @author Marlos Ribeiro
	 * @since 12/04/2013
	 */
	public void addRagistroTipo1(List<Object[]> colecaoTarifas){

		// Para cada 24 registros selecionados, o sistema grava um registro de tarifa
		StringBuffer tarifas = new StringBuffer("");
		int indexTarifas = 0;
		BigDecimal limiteSuperiorFaixa = BigDecimal.ZERO;
		Integer limiteInferiorFaixa = 0;

		for(int i = 0; i < colecaoTarifas.size(); i++){

			Object[] linhaRetornada = colecaoTarifas.get(i);
			indexTarifas++;

			// Código da Tarifa
			tarifas.append(Util.completarStringComValorEsquerda(linhaRetornada[0].toString(), "0", 2));

			// Código da Categoria
			tarifas.append(Util.completarStringComValorEsquerda(linhaRetornada[1].toString(), "0", 2));

			// Limite Inferior da Faixa
			if(linhaRetornada[4] != null && !linhaRetornada[4].toString().equals("")){

				limiteInferiorFaixa = Util.obterInteger(linhaRetornada[4].toString());
			}

			// Limite Superior da Faixa
			if(linhaRetornada[2] != null && !linhaRetornada[2].toString().equals("")){

				tarifas.append(Util.completarStringComValorEsquerda(linhaRetornada[2].toString(), "0", 5));
				limiteSuperiorFaixa = new BigDecimal(linhaRetornada[2].toString());
			}else{

				tarifas.append(Util.completarStringComValorEsquerda("", "0", 5));
			}

			if(limiteInferiorFaixa.intValue() == 0){

				// Valor da Tarifa
				if(linhaRetornada[3] != null && !linhaRetornada[3].toString().equals("")){

					tarifas.append(Util.completarStringComValorEsquerda(
									(new BigDecimal(linhaRetornada[3].toString()).multiply(limiteSuperiorFaixa))
													.setScale(2, BigDecimal.ROUND_DOWN).toString().replace(".", ""), "0", 7));
				}else{

					tarifas.append(Util.completarStringComValorEsquerda("", "0", 7));
				}
			}else{

				// Valor da Tarifa
				if(linhaRetornada[3] != null && !linhaRetornada[3].toString().equals("")){

					tarifas.append(Util.completarStringComValorEsquerda(
									new BigDecimal(linhaRetornada[3].toString()).setScale(2, BigDecimal.ROUND_DOWN).toString()
													.replace(".", ""), "0", 7));
				}else{

					tarifas.append(Util.completarStringComValorEsquerda("", "0", 7));
				}
			}

			// Dados das tarifas, ocorrendo 24 vezes
			if((i == (colecaoTarifas.size() - 1)) || ((indexTarifas % 24) == 0)){

				StringBuffer arquivoEnvio = new StringBuffer();

				// Grava registro tipo 1
				arquivoEnvio.append("1");

				// Completa com espaços em branco
				arquivoEnvio.append(Util.completaString("", 17));

				// Grava um registro de tarifas
				arquivoEnvio.append(tarifas.toString());

				// Completa com espaços em branco
				arquivoEnvio.append(Util.completaString("", 1));

				addLinha(arquivoEnvio);
				qtdTipo01++;

				// Limpa o buffer
				tarifas.delete(0, tarifas.length());

				// Zera o index
				indexTarifas = 0;
			}
		}
	}

	/**
	 * Método addRagistroTipo02
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param leituras
	 * @author Marlos Ribeiro
	 * @since 15/04/2013
	 */
	public void addRagistroTipo2(Collection<LeituraAnormalidade> leituras){

		qtdTipo02++;

		StringBuffer buffer = new StringBuffer();
		buffer.append(2);
		buffer.append(Util.formatRPad("", 17, ' '));
		for(LeituraAnormalidade leitura : leituras){
			buffer.append(Util.formatLPad(leitura.getId(), 2, '0'));
			buffer.append(Util.formatRPad(leitura.getDescricao(), 30, ' '));
			buffer.append(Util.formatLPad(leitura.getLeituraAnormalidadeConsumoSemleitura().getId(), 1, '0'));
			buffer.append(Util.formatLPad(leitura.getLeituraAnormalidadeConsumoComleitura().getId(), 1, '0'));
			buffer.append(Util.formatLPad(leitura.getLeituraAnormalidadeLeituraSemleitura().getId(), 1, '0'));
			buffer.append(Util.formatLPad(leitura.getLeituraAnormalidadeLeituraComleitura().getId(), 1, '0'));

			if(Util.isVazioOuBranco(leitura.getIndicadorListagemAnormalidadeRelatorios())
							|| leitura.getIndicadorListagemAnormalidadeRelatorios().equals(ConstantesSistema.NAO)){
				buffer.append(0);
			}else{
				buffer.append(1);
			}
		}

		if(leituras.size() < 10) buffer.append(Util.formatRPad("", 37 * (10 - leituras.size()), ' '));
		buffer.append(Util.formatRPad("", 15, ' '));
		addLinha(buffer);
	}

	/**
	 * Método addRagistroTipo03
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param localidadeId
	 * @param localidadeDesc
	 * @param setorCod
	 * @param limiteConsumo
	 * @param qualidadeAgua
	 * @param qualidadeAguaPadrao
	 * @param mensagens
	 * @author Marlos Ribeiro
	 * @param indicadorAjuste
	 * @since 16/04/2013
	 */
	public void addRagistroTipo3(Integer localidadeId, String localidadeDesc, Integer setorCod, Integer limiteConsumo,
					QualidadeAgua qualidadeAgua, QualidadeAguaPadrao qualidadeAguaPadrao, Integer indicadorAjuste, Object[] mensagens){

		qtdTipo03++;

		StringBuffer buffer = new StringBuffer();
		buffer.append(3);
		buffer.append(Util.formatRPad("", 17, ' '));
		buffer.append(Util.formatLPad(localidadeId, 3, '0'));
		buffer.append(Util.formatRPad(localidadeDesc, 20, ' '));
		buffer.append(Util.formatLPad(setorCod, 3, '0'));
		buffer.append(Util.formatLPad(limiteConsumo, 3, '0'));
		buffer.append("000");
		
		if(mensagens[0] != null && !mensagens[0].toString().equals("")){

			if(mensagens[0].toString().length() <= 50){

				buffer.append(Util.completaString(mensagens[0].toString(), 50));
				buffer.append(Util.completaString("", 50));
			}else{

				buffer.append(mensagens[0].toString().substring(0, 50));
				buffer.append(Util.completaString(mensagens[0].toString().substring(50), 50));
			}
		}else{

			buffer.append(Util.completaString("", 100));
		}
		
		if(mensagens[1] != null && !mensagens[1].toString().equals("")){

			if(mensagens[1].toString().length() <= 50){

				buffer.append(Util.completaString(mensagens[1].toString(), 50));
			}else{

				buffer.append(mensagens[1].toString().substring(0, 50));
			}
		}else{

			buffer.append(Util.completaString("", 50));
		}

		buffer.append(ConstantesSistema.SIM.equals(indicadorAjuste) ? 'S' : 'N');

		if(mensagens[1] != null && !mensagens[1].toString().equals("")){

			if(mensagens[1].toString().length() <= 50){

				buffer.append(Util.completaString("", 50));
			}else{

				buffer.append(Util.completaString(mensagens[1].toString().substring(50), 50));
			}
		}else{

			buffer.append(Util.completaString("", 50));
		}

		// DADOS QUALIDADE AGUA
		if(qualidadeAgua == null) buffer.append(Util.formatLPad("", 148, '0'));
		else{
			buffer.append(Util.formatLPad(qualidadeAgua.getNumeroAmostrasExigidasTurbidez(), 3, '0'));
			buffer.append(Util.formatLPad(qualidadeAgua.getNumeroAmostrasExigidasPh(), 3, '0'));
			buffer.append(Util.formatLPad(qualidadeAgua.getNumeroAmostrasExigidasCor(), 3, '0'));
			buffer.append(Util.formatLPad(qualidadeAgua.getNumeroAmostrasExigidasCloro(), 3, '0'));
			buffer.append(Util.formatLPad(qualidadeAgua.getNumeroAmostrasExigidasFluor(), 3, '0'));
			buffer.append(Util.formatLPad(qualidadeAgua.getNumeroAmostrasExigidasColiformesTotais(), 3, '0'));
			buffer.append(Util.formatLPad(qualidadeAgua.getNumeroAmostrasRealizadasTurbidez(), 3, '0'));
			buffer.append(Util.formatLPad(qualidadeAgua.getNumeroAmostrasRealizadasPH(), 3, '0'));
			buffer.append(Util.formatLPad(qualidadeAgua.getNumeroAmostrasRealizadasCor(), 3, '0'));
			buffer.append(Util.formatLPad(qualidadeAgua.getNumeroAmostrasRealizadasCloro(), 3, '0'));
			buffer.append(Util.formatLPad(qualidadeAgua.getNumeroAmostrasRealizadasFluor(), 3, '0'));
			buffer.append(Util.formatLPad(qualidadeAgua.getNumeroAmostrasRealizadasColiformesTotais(), 3, '0'));
			buffer.append(Util.formatLPad(qualidadeAgua.getNumeroAmostrasRealizadasColiformesTermotolerantes(), 3, '0'));
			buffer.append(Util.formatLPad(qualidadeAgua.getNumeroAmostrasConformesTurbidez(), 3, '0'));
			buffer.append(Util.formatLPad(qualidadeAgua.getNumeroAmostrasConformesPH(), 3, '0'));
			buffer.append(Util.formatLPad(qualidadeAgua.getNumeroAmostrasConformesCor(), 3, '0'));
			buffer.append(Util.formatLPad(qualidadeAgua.getNumeroAmostrasConformesCloro(), 3, '0'));
			buffer.append(Util.formatLPad(qualidadeAgua.getNumeroAmostrasConformesFluor(), 3, '0'));
			buffer.append(Util.formatLPad(qualidadeAgua.getNumeroAmostrasConformesColiformesTotais(), 3, '0'));
			buffer.append(Util.formatLPad(qualidadeAgua.getNumeroAmostrasConformesColiformesTermotolerantes(), 3, '0'));

			if(Util.isVazioOuBranco(qualidadeAgua.getDescricaoConclusaoAnalisesRealizadas())) buffer.append(Util.formatLPad("", 91, ' '));
			else buffer.append(Util.formatRPad(qualidadeAgua.getDescricaoConclusaoAnalisesRealizadas(), 91, ' '));
		}
		buffer.append(' ');
		addLinha(buffer);
	}

	/**
	 * Método addRagistroTipo04
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param mre
	 * @author Marlos Ribeiro
	 * @param clienteResponsavel
	 * @param endereco
	 * @param ligacaoAgua
	 * @since 16/04/2013
	 */
	public void addRagistroTipo4(MovimentoRoteiroEmpresa mre, Cliente clienteResponsavel, DebitoAutomatico dadosDebitoAutomatico,
					String endereco, LigacaoAgua ligacaoAgua, LigacaoEsgoto ligacaoEsgoto, String consumoMinimoLigacao,
					String indicadorQuitacaoDebito){

		qtdTipo04++;

		Imovel imovel = mre.getImovel();

		StringBuffer buffer = new StringBuffer();
		// 01 - 04
		buffer.append(4);
		buffer.append(Util.formatLPad(mre.getInscricaoNaoFormatada(3, 2, 3, 4), 17, '0'));
		buffer.append(Util.formatLPad(imovel.getId(), 10, '0'));
		buffer.append(Util.formatRPad(mre.getNomeCliente(), 28, ' '));

		// 05
		if(mre.getIndicadorImpostoFederal() == null || mre.getIndicadorImpostoFederal().equals(ConstantesSistema.NAO)){

			buffer.append(ConstantesSistema.ZERO.toString());

		}else{
			buffer.append(mre.getIndicadorImpostoFederal().toString());
		}

		// 06
		buffer.append(ConstantesSistema.SIM.equals(mre.getIndicadorEmissao()) ? 'S' : 'N');
		// 07
		Integer clienteResponsavelId = 0;
		if(clienteResponsavel != null) clienteResponsavelId = clienteResponsavel.getId();
		buffer.append(Util.formatLPad(clienteResponsavelId, 5, '0'));

		// 08 - 11
		buffer.append(Util.formatLPad(mre.getConsumoTarifa().getId(), 2, '0'));
		buffer.append(Util.formatRPad(mre.getLigacaoAguaSituacao().getId(), 1, '0'));
		buffer.append(Util.formatRPad(mre.getLigacaoEsgotoSituacao().getId(), 1, '0'));
		buffer.append(Arrays.asList(ImovelPerfil.GRANDE_NO_MES, ImovelPerfil.GRANDE).contains(imovel.getImovelPerfil().getId()) ? 1 : 0);

		// 12
		if(ligacaoAgua != null && ligacaoAgua.getCorteTipo() != null
						&& mre.getLigacaoAguaSituacao().getId().equals(LigacaoAguaSituacao.CORTADO)){

			buffer.append(Util.formatRPad(ligacaoAgua.getCorteTipo().getId().toString(), 1, '0'));
		}else{

			buffer.append(Util.formatRPad(ConstantesSistema.ZERO.toString(), 1, '0'));
		}

		// 13
		if(Util.isNaoNuloBrancoZero(mre.getCepEnderecoImovel())){
			buffer.append(Util.formatLPad(mre.getCepEnderecoImovel().trim(), 8, ' '));
		}else{
			buffer.append(Util.formatLPad("", 8, ' '));
		}

		// 14
		if(imovel.getFaturamentoSituacaoTipo() != null
						&& imovel.getFaturamentoSituacaoTipo().getId()
										.equals(FaturamentoSituacaoTipo.PARALISAR_LEITURA_FATURAR_TAXA_MINIMA)){

			buffer.append(Util.formatLPad(consumoMinimoLigacao, 5, '0'));
		}else if(ligacaoAgua != null && ligacaoAgua.getNumeroConsumoMinimoAgua() != null
						&& mre.getLigacaoAguaSituacao().getId().equals(LigacaoAguaSituacao.LIGADO) && mre.getMedicaoTipo() == null){

			buffer.append(Util.formatLPad(ligacaoAgua.getNumeroConsumoMinimoAgua().toString(), 5, '0'));
		}else{

			buffer.append(Util.formatLPad(ConstantesSistema.ZERO.toString(), 5, '0'));
		}

		// 15
		if(ligacaoEsgoto != null && ligacaoEsgoto.getConsumoMinimo() != null
						&& mre.getLigacaoEsgotoSituacao().getId().equals(LigacaoEsgotoSituacao.LIGADO) && mre.getMedicaoTipo() == null){

			buffer.append(Util.formatLPad(ligacaoEsgoto.getConsumoMinimo().toString(), 5, '0'));
		}else{

			buffer.append(Util.formatLPad(ConstantesSistema.ZERO.toString(), 5, '0'));
		}

		// 17 - 19
		Integer bancoId = null;
		String bancoDescricao = "";
		String agenciaCodigo = "";
		if(dadosDebitoAutomatico != null){
			bancoId = dadosDebitoAutomatico.getAgencia().getBanco().getId();
			bancoDescricao = dadosDebitoAutomatico.getAgencia().getBanco().getDescricao();
			agenciaCodigo = dadosDebitoAutomatico.getAgencia().getCodigoAgencia();
		}
		buffer.append(Util.formatLPad(bancoId, 4, '0'));
		buffer.append(Util.formatRPad(bancoDescricao, 20, ' '));
		buffer.append(Util.formatLPad(agenciaCodigo, 5, '0'));

		// 20 - 22
		buffer.append(Util.formatRPad(endereco, 45, ' '));
		buffer.append(Util.formatRPad(mre.getBairroEnderecoImovel(), 20, ' '));
		buffer.append(Util.formatarDataAAAAMMDD(mre.getDataVencimento()));

		// 23
		buffer.append(Util.formatLPad(mre.getQuantidadeEconomiasResidencial(), 3, '0'));
		buffer.append(Util.formatLPad(mre.getQuantidadeEconomiasComercial(), 3, '0'));
		buffer.append(Util.formatLPad(mre.getQuantidadeEconomiasIndustrial(), 3, '0'));
		buffer.append(Util.formatLPad(mre.getQuantidadeEconomiasPublica(), 3, '0'));
		// 24 - 26
		buffer.append(Util.formatLPad(Util.formatarMoedaReal(mre.getPercentualEsgoto(), 2).replace(".", "").replace(",", ""), 5, '0'));
		buffer.append(ConstantesSistema.SIM.equals(imovel.getIndicadorDebitoConta()) ? 'N' : 'S');
		buffer.append(Util.isNaoNuloBrancoZero(mre.getNumeroHidrometro()) ? 'S' : 'N');

		// 27
		if(ConstantesSistema.SIM.equals(imovel.getIndicadorImovelCondominio())) buffer.append(Util.formatLPad(imovel.getId(), 10, '0'));
		else if(imovel.getImovelCondominio() == null) buffer.append(Util.formatLPad("", 10, '0'));
		else buffer.append(Util.formatLPad(imovel.getImovelCondominio().getId(), 10, '0'));

		// 28
		if(ligacaoAgua == null || ligacaoAgua.getRamalLocalInstalacao() == null) buffer.append(Util.formatRPad("", 6, ' '));
		else buffer.append(Util.formatRPad(ligacaoAgua.getRamalLocalInstalacao().getDescricaoAbreviada(), 6, ' '));

		// 29 - 32
		BigDecimal valorCreditos = BigDecimal.ZERO;

		// Colocada condições para não enviar os créditos de parcelamento que já foram deduzidos no
		// valor do débito
		if(mre.getValorCreditado1() == null && mre.getValorCredito1() != null){

			valorCreditos = valorCreditos.add(mre.getValorCredito1());
		}

		if(mre.getValorCreditado2() == null && mre.getValorCredito2() != null){

			valorCreditos = valorCreditos.add(mre.getValorCredito2());
		}

		if(mre.getValorCreditado3() == null && mre.getValorCredito3() != null){

			valorCreditos = valorCreditos.add(mre.getValorCredito3());
		}

		if(mre.getValorCreditado4() == null && mre.getValorCredito4() != null){

			valorCreditos = valorCreditos.add(mre.getValorCredito4());
		}

		if(mre.getValorCreditado5() == null && mre.getValorCredito5() != null){

			valorCreditos = valorCreditos.add(mre.getValorCredito5());
		}

		buffer.append(Util.formatLPad(Util.formatarMoedaReal(valorCreditos, 2).replace(".", "").replace(",", ""), 9, '0'));
		
		buffer.append(indicadorQuitacaoDebito);
		
		if(imovel.getFaturamentoSituacaoTipo() != null
						&& imovel.getFaturamentoSituacaoTipo().getId()
										.equals(FaturamentoSituacaoTipo.PARALISAR_LEITURA_FATURAR_TAXA_MINIMA)){
			buffer.append('T');
		}else{

			buffer.append('N');
		}

		if(ligacaoAgua != null && ligacaoAgua.getDataCorte() != null){

			buffer.append(Util.formatarDataAAAAMMDD(ligacaoAgua.getDataCorte()));
		}else{

			buffer.append(Util.formatRPad(0, 8, '0'));
		}

		buffer.append(Util.formatRPad("", 160, ' '));

		addLinha(buffer);

	}

	/**
	 * Método addRagistroTipo05
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param mre
	 * @author Marlos Ribeiro
	 * @param hidrometroHelper
	 * @param faixaLeituraEsperada
	 * @return
	 * @since 17/04/2013
	 */
	public boolean addRagistroTipo5(MovimentoRoteiroEmpresa mre, HidrometroRelatorioOSHelper hidrometroHelper, int[] faixaLeituraEsperada,
					HashMap<Integer, String> mapDescricaoAbreviadaAnormalidadesConsumo){

		if(Util.isVazioOuBranco(mre.getNumeroHidrometro())) return false;
		qtdTipo05++;

		String strDataLeituraAnterior = Util.formatarDataAAAAMMDD(mre.getDataLeituraAnterior());
		String strDataInstalacaoHidrometro = Util.formatarDataAAAAMMDD(mre.getDataInstalacaoHidrometro());

		StringBuffer buffer = new StringBuffer();
		// 01 - 11
		buffer.append(5);
		buffer.append(Util.formatLPad(mre.getInscricaoNaoFormatada(3, 2, 3, 4), 17, '0'));
		buffer.append(Util.formatRPad(mre.getNumeroHidrometro(), 12, ' '));
		buffer.append(Util.formatRPad(mre.getLocalInstalacaoHidrometro(), 3, ' '));
		buffer.append(Util.formatRPad(hidrometroHelper.getHidrometroMarca(), 3, ' '));
		buffer.append(Util.formatRPad(hidrometroHelper.getHidrometroCapacidade(), 6, ' '));
		buffer.append(Util.formatLPad(mre.getNumeroConsumoMedio(), 6, '0'));
		buffer.append(Util.formatLPad(mre.getNumeroLeituraAnterior(), 8, '0'));

		// 12 - Data Leitura Anterior
		if(Util.isNaoNuloBrancoZero(strDataLeituraAnterior)
						&& Integer.valueOf(strDataLeituraAnterior).compareTo(Integer.valueOf(DATA_INICIAL_LEITURA_YYYYMMDD)) > 0) buffer
						.append(strDataLeituraAnterior);
		else if(Util.isNaoNuloBrancoZero(strDataInstalacaoHidrometro)
						&& Integer.valueOf(strDataInstalacaoHidrometro).compareTo(Integer.valueOf(DATA_INICIAL_LEITURA_YYYYMMDD)) > 0) buffer
						.append(strDataInstalacaoHidrometro);
		else buffer.append(DATA_FUNDACAO_CASAL_YYYYMMDD);

		// 13 - 16
		buffer.append(SITUACOES_LEITURA_POSITIVAS.contains(mre.getIdLeituraSituacaoAnterior()) ? 1 : 0);
		buffer.append(100);
		// buffer.append(Util.formatLPad(mre.getNumeroConsumoMinimo(), 5, '0'));
		buffer.append(Util.formatLPad("0", 5, '0'));
		buffer.append(Util.formatLPad("", 6, ' '));

		// 17 - Data de instalação do Hidrômetro
		if(Util.isNaoNuloBrancoZero(strDataInstalacaoHidrometro)
						&& Integer.valueOf(strDataInstalacaoHidrometro).compareTo(Integer.valueOf(DATA_INICIAL_LEITURA_YYYYMMDD)) > 0) buffer
						.append(strDataInstalacaoHidrometro);
		else buffer.append(DATA_FUNDACAO_CASAL_YYYYMMDD);

		// 18, 19 - Leitura Esperada
		buffer.append(Util.formatLPad(faixaLeituraEsperada[0], 8, '0'));
		buffer.append(Util.formatLPad(faixaLeituraEsperada[1], 8, '0'));

		/*
		 * Últimos consumos, ocorrendo 12 vezes (do item 20 ao 24)
		 * Iniciando pela coluna xxx12 -> xxx1
		 */
		for(int i = 12; i > 0; i--){
			buffer.append(Util.formatLPad(Util.executarMetodo(mre, "getReferenciaConsumo" + i), 6, '0'));
			buffer.append(Util.formatLPad(Util.executarMetodo(mre, "getIdAnormalidadeLeitura" + i), 2, '0'));
			
			if (Util.executarMetodo(mre, "getIdAnormalidadeConsumo" + i) != null){
				
				buffer.append(Util.completarStringComValorEsquerda(
								mapDescricaoAbreviadaAnormalidadesConsumo.get(Util.executarMetodo(mre, "getIdAnormalidadeConsumo" + i)),
								" ", 2));
			}else{
				
				buffer.append(Util.completaString("", 2));
			}
			
			buffer.append(Util.formatLPad(Util.executarMetodo(mre, "getNumeroConsumo" + i), 7, '0'));
			buffer.append(Util.formatLPad(Util.executarMetodo(mre, "getLeituraFaturada" + i), 8, '0'));
		}

		addLinha(buffer);

		return true;
	}

	/**
	 * Método addRagistroTipo06
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param mre
	 * @author Marlos Ribeiro
	 * @param collDebitosAnteriores
	 * @return
	 * @since 17/04/2013
	 */
	public boolean addRagistroTipo6(MovimentoRoteiroEmpresa mre, List<DebitoAnteriorHelper> collDebitosAnteriores,
					String representacaoNumericaCodBarra){

		if(Util.isVazioOrNulo(collDebitosAnteriores)) return false;
		qtdTipo06++;

		StringBuffer buffer = new StringBuffer();

		// 01 - 05
		buffer.append(6);
		buffer.append(Util.formatLPad(mre.getInscricaoNaoFormatada(3, 2, 3, 4), 17, '0'));
		buffer.append(collDebitosAnteriores.size() > 8 ? '*' : ' ');
		buffer.append(Util.formatLPad(mre.getNumeroDocumentoCobranca(), 9, '0'));
		buffer.append(Util.formatLPad(collDebitosAnteriores.size() > 8 ? collDebitosAnteriores.size() - 8 : 0, 3, '0'));

		/*
		 * 06 - Valor dos débitos acumulados
		 * Caso a quantidade de débitos obtida seja maior que 8 (oito), acumula o valor dos
		 * débitos
		 * a partir da nona ocorrência
		 */
		BigDecimal vlDebito = BigDecimal.ZERO;
		DebitoAnteriorHelper debito;
		if(collDebitosAnteriores.size() > 8){
			for(int i = 0; i < (collDebitosAnteriores.size() - 8); i++){
				debito = collDebitosAnteriores.get(i);
				vlDebito = vlDebito.add(debito.getConta().getValorTotalContaBigDecimal());
				if(debito.getValorAcrescimos() != null) vlDebito = vlDebito.add(debito.getValorAcrescimos());
			}
		}
		buffer.append(Util.completarStringComValorEsquerda(Util.formatarMoedaReal(vlDebito, 2).replace(".", "").replace(",", ""), "0", 11));

		/*
		 * 07 - 09 - Débitos anteriores
		 * Preencher com as 8 (oito) primeiras ocorrências de débito obtido
		 */

		if(collDebitosAnteriores.size() > 8){

			for(int i = (collDebitosAnteriores.size() - 8); i < collDebitosAnteriores.size(); i++){

				debito = collDebitosAnteriores.get(i);
				vlDebito = BigDecimal.ZERO;

				buffer.append(debito.getConta().getReferencia());
				buffer.append(Util.recuperaDiaMesAnoCom2DigitosDaData(debito.getConta().getDataVencimentoConta()));

				vlDebito = vlDebito.add(debito.getConta().getValorTotalContaBigDecimal());
				if(debito.getValorAcrescimos() != null) vlDebito = vlDebito.add(debito.getValorAcrescimos());
				buffer.append(Util.completarStringComValorEsquerda(Util.formatarMoedaReal(vlDebito, 2).replace(".", "").replace(",", ""),
								"0", 11));
			}
		}else{

			int posicoesRestantes = 184;
			for(int i = 0; i < collDebitosAnteriores.size(); i++){

				debito = collDebitosAnteriores.get(i);
				vlDebito = BigDecimal.ZERO;

				buffer.append(debito.getConta().getReferencia());
				buffer.append(Util.recuperaDiaMesAnoCom2DigitosDaData(debito.getConta().getDataVencimentoConta()));

				vlDebito = vlDebito.add(debito.getConta().getValorTotalContaBigDecimal());
				if(debito.getValorAcrescimos() != null) vlDebito = vlDebito.add(debito.getValorAcrescimos());
				buffer.append(Util.completarStringComValorEsquerda(Util.formatarMoedaReal(vlDebito, 2).replace(".", "").replace(",", ""),
								"0", 11));
				posicoesRestantes -= 23;
			}

			// Completa demais posições referentes a débitos anteriores com espaço em branco
			buffer.append(Util.completaString("", posicoesRestantes));
		}


		// Código de Barras do Aviso de Corte Gerado
		if(!Util.isVazioOuBranco(representacaoNumericaCodBarra)){

			buffer.append(Util.completarStringZeroDireita(representacaoNumericaCodBarra, 48));
		}

		addLinha(buffer);

		return true;
	}

	/**
	 * Método para emissão arquivo da CAERD. Tipo do Registro: 07
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0102] Gerar Arquivo Texto para Faturamento Imediato - Modelo 4
	 * 
	 * @author Marlos Ribeiro
	 * @since 18/04/2013
	 * @author Saulo Lima
	 * @date 04/11/2013
	 *       Alteração para melhoria do código fonte e correção para soma no sétimo registro
	 * @param MovimentoRoteiroEmpresa
	 * @return boolean
	 */
	public boolean addRegistroTipo7(MovimentoRoteiroEmpresa movimentoRoteiroEmpresa){

		if(Util.isVazioOuBranco(movimentoRoteiroEmpresa.getDescricaoRubrica1())
						&& Util.isVazioOuBranco(movimentoRoteiroEmpresa.getDescricaoCredito1())){

			return false;
		}

		qtdTipo07++;

		String descCredito = "000CRÉDITOS";
		String descRubrica = "000OUTROS";

		StringBuffer buffer = new StringBuffer();
		// 01, 02
		buffer.append(7);
		buffer.append(Util.formatLPad(movimentoRoteiroEmpresa.getInscricaoNaoFormatada(3, 2, 3, 4), 17, '0'));

		boolean possuiCredito = false;
		boolean possuiDebito = false;

		if(movimentoRoteiroEmpresa.getDescricaoRubrica1() != null && movimentoRoteiroEmpresa.getDescricaoCredito1() != null){

			possuiDebito = true;
			possuiCredito = true;
		}else if(movimentoRoteiroEmpresa.getDescricaoRubrica1() != null){

			possuiDebito = true;
		}else if(movimentoRoteiroEmpresa.getDescricaoCredito1() != null){

			possuiCredito = true;
		}

		int ultimoIndiceRubricaDebito = 0;
		int quantidadeMaximaRubricaCredito = 1;
		int quantidadeMaximaRubricaDebitos = 7;
		int ultimoIndiceRubricaCredito = 0;
		int posicoesRestantes = quantidadeMaximaRubricaDebitos;
		BigDecimal valorDebitoRestante = BigDecimal.ZERO;
		BigDecimal valorCreditoRestante = BigDecimal.ZERO;

		if(possuiDebito && possuiCredito){

			// Imprimir os débitos
			for(int j = 1; j <= 15; j++){

				if(Util.executarMetodo(movimentoRoteiroEmpresa, "getDescricaoRubrica" + j, (Object[]) null) != null){

					ultimoIndiceRubricaDebito = j;
				}
			}

			if(ultimoIndiceRubricaDebito >= quantidadeMaximaRubricaDebitos){
				ultimoIndiceRubricaDebito = quantidadeMaximaRubricaDebitos - 2;
			}

			for(int j = 1; j <= 15; j++){

				if(j <= ultimoIndiceRubricaDebito){

					buffer.append(this.montarRegistroTipo7Debito(movimentoRoteiroEmpresa, j));
					posicoesRestantes--;

				}else if(Util.executarMetodo(movimentoRoteiroEmpresa, "getValorRubrica" + j, (Object[]) null) != null){

					valorDebitoRestante = valorDebitoRestante.add(new BigDecimal(Util.executarMetodo(movimentoRoteiroEmpresa,
									"getValorRubrica" + j, (Object[]) null).toString()));
				}
			}

			if(valorDebitoRestante.compareTo(BigDecimal.ZERO) == 1){

				String sinal = "+";
				buffer.append(this.montarRegistroTipo7Restante(movimentoRoteiroEmpresa, valorDebitoRestante, descRubrica, sinal));
				posicoesRestantes--;
			}

			for(int j = 1; j <= 5; j++){

				if(Util.executarMetodo(movimentoRoteiroEmpresa, "getDescricaoCredito" + j, (Object[]) null) != null){

					ultimoIndiceRubricaCredito = j;
				}
			}

			for(int t = 1; t <= 5; t++){

				if(Util.executarMetodo(movimentoRoteiroEmpresa, "getValorCreditado" + t, (Object[]) null) == null){

					if((ultimoIndiceRubricaCredito == quantidadeMaximaRubricaCredito) && t == quantidadeMaximaRubricaCredito){

						buffer.append(this.montarRegistroTipo7Credito(movimentoRoteiroEmpresa, t));
						posicoesRestantes--;

					}else if(Util.executarMetodo(movimentoRoteiroEmpresa, "getValorCredito" + t, (Object[]) null) != null){

						valorCreditoRestante = valorCreditoRestante.add(new BigDecimal(Util.executarMetodo(movimentoRoteiroEmpresa,
										"getValorCredito" + t, (Object[]) null).toString()));
					}
				}
			}

			if(valorCreditoRestante.compareTo(BigDecimal.ZERO) == 1){

				String sinal = "-";
				buffer.append(this.montarRegistroTipo7Restante(movimentoRoteiroEmpresa, valorCreditoRestante, descCredito, sinal));
				posicoesRestantes--;
			}

		}else if(possuiDebito){

			for(int j = 1; j <= 15; j++){

				if(Util.executarMetodo(movimentoRoteiroEmpresa, "getDescricaoRubrica" + j, (Object[]) null) != null){

					ultimoIndiceRubricaDebito = j;
				}
			}

			if(ultimoIndiceRubricaDebito >= quantidadeMaximaRubricaDebitos){
				ultimoIndiceRubricaDebito = quantidadeMaximaRubricaDebitos - 1;
			}

			for(int j = 1; j <= 15; j++){

				if(j <= ultimoIndiceRubricaDebito){

					buffer.append(this.montarRegistroTipo7Debito(movimentoRoteiroEmpresa, j));
					posicoesRestantes--;

				}else if(Util.executarMetodo(movimentoRoteiroEmpresa, "getValorRubrica" + j, (Object[]) null) != null){

					valorDebitoRestante = valorDebitoRestante.add(new BigDecimal(Util.executarMetodo(movimentoRoteiroEmpresa,
									"getValorRubrica" + j, (Object[]) null).toString()));
				}
			}

			if(valorDebitoRestante.compareTo(BigDecimal.ZERO) == 1){

				String sinal = "+";
				buffer.append(this.montarRegistroTipo7Restante(movimentoRoteiroEmpresa, valorDebitoRestante, descRubrica, sinal));
				posicoesRestantes--;
			}

		}else if(possuiCredito){

			for(int j = 1; j <= 6; j++){

				buffer.append(this.montarRegistroTipo7Debito(movimentoRoteiroEmpresa, j));
				posicoesRestantes--;
			}

			for(int j = 1; j <= 5; j++){

				if(Util.executarMetodo(movimentoRoteiroEmpresa, "getDescricaoCredito" + j, (Object[]) null) != null){

					ultimoIndiceRubricaCredito = j;
				}
			}

			for(int t = 1; t <= 5; t++){

				if(Util.executarMetodo(movimentoRoteiroEmpresa, "getValorCreditado" + t, (Object[]) null) == null){

					if((ultimoIndiceRubricaCredito == quantidadeMaximaRubricaCredito) && t == quantidadeMaximaRubricaCredito){

						buffer.append(this.montarRegistroTipo7Credito(movimentoRoteiroEmpresa, t));
						posicoesRestantes--;

					}else if(Util.executarMetodo(movimentoRoteiroEmpresa, "getValorCredito" + t, (Object[]) null) != null){

						valorCreditoRestante = valorCreditoRestante.add(new BigDecimal(Util.executarMetodo(movimentoRoteiroEmpresa,
										"getValorCredito" + t, (Object[]) null).toString()));
					}
				}
			}

			if(valorCreditoRestante.compareTo(BigDecimal.ZERO) == 1){

				String sinal = "-";
				buffer.append(this.montarRegistroTipo7Restante(movimentoRoteiroEmpresa, valorCreditoRestante, descCredito, sinal));
				posicoesRestantes--;
			}
		}

		for(int t = 1; t <= posicoesRestantes; t++){

			descCredito = "        ";
			buffer.append(descCredito.substring(0, 3));
			buffer.append(Util.formatRPad(descCredito.substring(3), 25, ' '));
			buffer.append(Util.formatLPad("", 2, ' '));
			buffer.append(Util.formatLPad("", 2, ' '));
			buffer.append(Util.formatLPad("", 11, ' '));
			buffer.append(Util.formatLPad("", 6, ' '));
			buffer.append(Util.formatRPad("", 6, ' '));
		}

		addLinha(buffer);

		return true;
	}

	/**
	 * Monta uma string para Débitos (Rubrica). Tipo do Registro: 07
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0102] Gerar Arquivo Texto para Faturamento Imediato - Modelo 4
	 * 
	 * @author Saulo Lima
	 * @since 04/11/2013
	 * @param movimentoRoteiroEmpresa
	 * @param numeroSequencial
	 * @return StringBuffer
	 */
	private StringBuffer montarRegistroTipo7Debito(MovimentoRoteiroEmpresa movimentoRoteiroEmpresa, int j){

		StringBuffer retorno = new StringBuffer();

		char caracterCompletar = '0';
		String sinal = "+";

		String descRubrica = (String) Util.executarMetodo(movimentoRoteiroEmpresa, "getDescricaoRubrica" + j);
		Short numPretRubrica = (Short) Util.executarMetodo(movimentoRoteiroEmpresa, "getNumeroPrestacaoRubrica" + j);
		Short numPretCobradaRubrica = (Short) Util.executarMetodo(movimentoRoteiroEmpresa, "getNumeroPrestacaoCobradaRubrica" + j);
		Integer referenciaRubrica = (Integer) Util.executarMetodo(movimentoRoteiroEmpresa, "getReferenciaRubrica" + j);
		BigDecimal vlPrestacaoRubrica = (BigDecimal) Util.executarMetodo(movimentoRoteiroEmpresa, "getValorRubrica" + j);
		if(Util.isVazioOuBranco(descRubrica)){
			descRubrica = "        ";
			caracterCompletar = ' ';
			sinal = " ";
		}
		retorno.append(descRubrica.substring(0, 3));
		retorno.append(Util.formatRPad(descRubrica.substring(3), 25, ' '));
		retorno.append(Util.formatLPad(numPretRubrica, 2, caracterCompletar));
		retorno.append(Util.formatLPad(numPretCobradaRubrica, 2, caracterCompletar));
		retorno.append(Util.formatLPad(sinal, 1, caracterCompletar));
		if(vlPrestacaoRubrica == null){
			retorno.append(Util.formatLPad(" ", 10, caracterCompletar));
		}else{
			// vlPrestacaoRubrica = BigDecimal.ZERO;
			retorno.append(Util.completarStringComValorEsquerda(
							Util.formatarMoedaReal(vlPrestacaoRubrica, 2).replace(".", "").replace(",", ""), "0", 10));
		}

		retorno.append(Util.formatLPad(referenciaRubrica, 6, caracterCompletar));
		retorno.append(Util.formatRPad("", 6, caracterCompletar));

		return retorno;
	}

	/**
	 * Monta uma string para Crédito. Tipo do Registro: 07
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0102] Gerar Arquivo Texto para Faturamento Imediato - Modelo 4
	 * 
	 * @author Saulo Lima
	 * @since 04/11/2013
	 * @param movimentoRoteiroEmpresa
	 * @param numeroSequencial
	 * @return StringBuffer
	 */
	private StringBuffer montarRegistroTipo7Credito(MovimentoRoteiroEmpresa movimentoRoteiroEmpresa, int t){

		StringBuffer retorno = new StringBuffer();

		char caracterCompletar = '0';
		String sinal = "+";

		String descCredito = (String) Util.executarMetodo(movimentoRoteiroEmpresa, "getDescricaoCredito" + t);
		Short numPretCredito = (Short) Util.executarMetodo(movimentoRoteiroEmpresa, "getNumeroPrestacaoCredito" + t);
		Short numPretCobradaCredito = (Short) Util.executarMetodo(movimentoRoteiroEmpresa, "getNumeroPrestacaoCobradaCredito" + t);
		Integer referenciaCredito = (Integer) Util.executarMetodo(movimentoRoteiroEmpresa, "getReferenciaCredito" + t);
		BigDecimal vlPrestacaoCredito = (BigDecimal) Util.executarMetodo(movimentoRoteiroEmpresa, "getValorCredito" + t);
		if(Util.isVazioOuBranco(descCredito)){
			descCredito = "        ";
			caracterCompletar = ' ';
			sinal = " ";
		}else{
			sinal = "-";
		}
		retorno.append(descCredito.substring(0, 3));
		retorno.append(Util.formatRPad(descCredito.substring(3), 25, ' '));
		retorno.append(Util.formatLPad(numPretCredito, 2, caracterCompletar));
		retorno.append(Util.formatLPad(numPretCobradaCredito, 2, caracterCompletar));
		retorno.append(Util.formatLPad(sinal, 1, caracterCompletar));
		if(vlPrestacaoCredito == null){
			retorno.append(Util.formatLPad(" ", 10, caracterCompletar));
		}else{
			// vlPrestacaoRubrica = BigDecimal.ZERO;
			retorno.append(Util.completarStringComValorEsquerda(
							Util.formatarMoedaReal(vlPrestacaoCredito, 2).replace(".", "").replace(",", ""), "0", 10));
		}
		retorno.append(Util.formatLPad(referenciaCredito, 6, caracterCompletar));
		retorno.append(Util.formatRPad("", 6, caracterCompletar));

		return retorno;
	}

	/**
	 * Monta uma string para Débitos (Rubrica) restantes ou Créditos restantes. Tipo do Registro: 07
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * [SB0102] Gerar Arquivo Texto para Faturamento Imediato - Modelo 4
	 * 
	 * @author Saulo Lima
	 * @since 04/11/2013
	 * @param movimentoRoteiroEmpresa
	 * @param valorRestante
	 * @param descricao
	 * @param sinal
	 * @return StringBuffer
	 */
	private StringBuffer montarRegistroTipo7Restante(MovimentoRoteiroEmpresa movimentoRoteiroEmpresa, BigDecimal valorRestante,
					String descricao, String sinal){

		StringBuffer retorno = new StringBuffer();

		Short numPret = 0;
		Short numPretCobrada = 0;
		Integer referencia = 0;
		retorno.append(descricao.substring(0, 3));
		retorno.append(Util.formatRPad(descricao.substring(3), 25, ' '));
		retorno.append(Util.formatLPad(numPret, 2, '0'));
		retorno.append(Util.formatLPad(numPretCobrada, 2, '0'));
		retorno.append(Util.formatLPad(sinal, 1, '0'));
		retorno.append(Util.completarStringComValorEsquerda(Util.formatarMoedaReal(valorRestante, 2).replace(".", "").replace(",", ""),
						"0", 10));
		retorno.append(Util.formatLPad(referencia, 6, '0'));
		retorno.append(Util.formatRPad("", 6, '0'));

		return retorno;
	}

	/**
	 * Método addRagistroTipo09
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param i
	 * @param j
	 * @param k
	 * @param l
	 * @param m
	 * @param n
	 * @author Marlos Ribeiro
	 * @since 18/04/2013
	 */
	public void addRagistroTipo9(){

		StringBuffer buffer = new StringBuffer();
		buffer.append(9);
		buffer.append(Util.formatRPad("", 17, ' '));
		buffer.append(Util.formatLPad(qtdTipo03, 6, '0'));
		buffer.append(Util.formatLPad(qtdTipo04, 6, '0'));
		buffer.append(Util.formatLPad(qtdTipo05, 6, '0'));
		buffer.append(Util.formatLPad(qtdTipo06, 6, '0'));
		buffer.append(Util.formatLPad(qtdTipo07, 6, '0'));
		buffer.append(Util.formatLPad(0, 6, '0'));
		buffer.append(Util.formatLPad("", 11, '0'));
		buffer.append(Util.formatRPad("", 339, ' '));

		addLinha(buffer);
	}

	public int getQtdTipo03(){

		return qtdTipo03;
	}

	public int getQtdTipo04(){

		return qtdTipo04;
	}

	public int getQtdTipo05(){

		return qtdTipo05;
	}

	public int getQtdTipo06(){

		return qtdTipo06;
	}

	public int getQtdTipo07(){

		return qtdTipo07;
	}

	/**
	 * Método addResumo
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param mre
	 * @author Marlos Ribeiro
	 * @param temDebitosAnteriores
	 * @since 19/04/2013
	 */
	public void addResumo(MovimentoRoteiroEmpresa mre, boolean temDebitosAnteriores){

		BigDecimal vlDebitos = mre.getValorDebitos();
		BigDecimal vlCreditos = mre.getValorCreditos();
		RelatorioOcorrenciaGeracaoPreFatResumoHelper resumo = getResumoLocalidade(mre.getLocalidade().getId());
		resumo.addOcorrencia(mre.getCodigoSetorComercial(), qtdTipo01, qtdTipo02, qtdTipo03, vlDebitos, vlCreditos, //
						!Util.isVazioOuBranco(mre.getNumeroHidrometro()),//
						temDebitosAnteriores,//
						!Util.isVazioOuBranco(mre.getDescricaoRubrica1()));

	}

	/**
	 * Método getResumoLocalidade
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param localidadeId
	 * @return
	 * @author Marlos Ribeiro
	 * @since 19/04/2013
	 */
	private RelatorioOcorrenciaGeracaoPreFatResumoHelper getResumoLocalidade(Integer localidadeId){

		if(resumoPreFaturamento == null) resumoPreFaturamento = new HashMap<Integer, RelatorioOcorrenciaGeracaoPreFatResumoHelper>();
		if(!resumoPreFaturamento.containsKey(localidadeId)) resumoPreFaturamento.put(localidadeId,
						new RelatorioOcorrenciaGeracaoPreFatResumoHelper());
		return resumoPreFaturamento.get(localidadeId);
	}

	/**
	 * @return the resumoPreFaturamento
	 */
	public Map<Integer, RelatorioOcorrenciaGeracaoPreFatResumoHelper> getResumoPreFaturamento(){

		return resumoPreFaturamento;
	}

	/**
	 * @return the conteudoArquivo
	 */
	public StringBuffer getConteudoArquivo(){

		return conteudoArquivo;
	}

	/**
	 * @return the nomeArquivo
	 */
	public String getNomeArquivo(){

		return nomeArquivo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void finalize() throws Throwable{

		super.finalize();
		conteudoArquivo = null;
	}
}
