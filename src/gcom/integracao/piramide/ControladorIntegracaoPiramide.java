/*
 * Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 * 
 * 
 * GSANPCG
 * André Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * José Gilberto de França Matos
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
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

package gcom.integracao.piramide;

import gcom.batch.ControladorBatchLocal;
import gcom.batch.UnidadeProcessamento;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.contabil.*;
import gcom.integracao.piramide.bean.LancamentoDeferimentoAnteriorHelper;
import gcom.integracao.piramide.bean.LancamentoDeferimentoHelper;
import gcom.integracao.piramide.bean.RetencaoHelper;
import gcom.integracao.piramide.bean.TabelaIntegracaoConslDiaNfclHelper;
import gcom.util.*;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ExecutorParametro;
import gcom.util.parametrizacao.Parametrizacao;
import gcom.util.parametrizacao.ParametroContabil;
import gcom.util.parametrizacao.batch.ExecutorParametrosBatch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Controlador Cobranca PADRÃO
 * 
 * @author Josenildo Neves
 * @date 15/08/2012
 */

public class ControladorIntegracaoPiramide
				implements SessionBean, IControladorIntegracaoPiramide, Parametrizacao {

	private static final Integer[] EVENTOS_INCLUSAO_GUIA_PGTO = new Integer[] {29};

	private static final Integer[] EVENTOS_FATURAMENTO = new Integer[] {1, 2, 3, 80, 81};

	private static final String P_GERAR_INTEGRACAO_CONTABIL_MODELO2 = "GerarIntegracaoContabilModelo2";

	private static final String P_GERAR_INTEGRACAO_CONTABIL_MODELO1 = "GerarIntegracaoContabilModelo1";

	private static final char VIRGULA = ',';

	private static final Logger LOGGER = Logger.getLogger(ControladorIntegracaoPiramide.class);

	protected static final long serialVersionUID = 1L;

	private static final int NUMERO_SEQUENCIAL_DEBITO = 1;

	private static final int NUMERO_SEQUENCIAL_CREDITO = 2;

	// Integração Retenção
	private static final String DATA_INICIAL_PADRAO = "31/08/2012";

	private static final String CODIGO_REGIONAL_PADRAO = "1";

	private static final String CODIGO_SISTEMA_ORIGEM = "GSA";

	private static final String CODIGO_IMPOSTO_PIS = "5979";

	private static final String CODIGO_IMPOSTO_COFFINS = "5960";

	private static final String CODIGO_CSOCIAL_APURAD = "01";

	private static final String CODIGO_RECEITA = "01";

	private static final String CODIGO_NATUREZA_RETENCAO_FONTE = "01";

	private static final String CODIGO_OPERACAO_REGISTRADA = "I";

	private static final String CODIGO_STATUS_REGISTRADO = "NP";

	private static final String DESCRICAO_ERRO_REGISTRO_VAZIO = " ";

	private static final String CODIGO_UNIDADE_ORIGEM = "000";

	private static final int IMPOSTO_TIPO_ALIQUOTA_PIS = 6;

	private static final int IMPOSTO_TIPO_ALIQUOTA_COFINS = 5;

	private DebugTotalizador DEBUG_TOTALIZADOR;

	SessionContext sessionContext;

	protected IRepositorioIntegracaoPiramide repositorioIntegracaoPiramide = null;

	protected IRepositorioUtil repositorioUtil = null;

	protected IRepositorioContabil repositorioContabil = null;

	private List<TabelaIntegracaoConslDiaNfcl> TI_CONSL_DIA_NFCL;

	private List<TabelaIntegracaoCmConDiaNfcl> TI_CM_CON_DIA_NFCL;

	private List<TabelaIntegracaoConsCanNtfisc> TI_CONS_CAN_NTFISC;

	/**
	 * < <Descrição do método>>
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

		repositorioIntegracaoPiramide = RepositorioIntegracaoPiramideHBM.getInstancia();

		repositorioContabil = RepositorioContabilHBM.getInstancia();

		repositorioUtil = RepositorioUtilHBM.getInstancia();

	}

	/**
	 * < <Descrição do método>>
	 */
	public void ejbRemove(){

	}

	/**
	 * < <Descrição do método>>
	 */
	public void ejbActivate(){

	}

	/**
	 * < <Descrição do método>>
	 */
	public void ejbPassivate(){

	}

	/**
	 * Seta o valor de sessionContext
	 * 
	 * @param sessionContext
	 *            O novo valor de sessionContext
	 */
	public void setSessionContext(SessionContext sessionContext){

		this.sessionContext = sessionContext;
	}

	public ExecutorParametro getExecutorParametro(){

		return ExecutorParametrosBatch.getInstancia();
	}

	/**
	 * Retorna o valor de controladorUtil
	 * 
	 * @return O valor de controladorUtil
	 */
	protected ControladorUtilLocal getControladorUtil(){

		return ServiceLocator.getInstancia().getControladorUtil();
	}

	/**
	 * Retorna o valor do ControladorBatch
	 * 
	 * @return O valor de ControladorBatch
	 */
	private ControladorBatchLocal getControladorBatch(){

		return ServiceLocator.getInstancia().getControladorBatch();
	}

	/**
	 * Retorna o valor do ControladorContabil
	 * 
	 * @return o valor do ControladorContabil
	 */
	private ControladorContabilLocal getControladorContabil(){

		return ServiceLocator.getInstancia().getControladorContabil();

	}

	/**
	 * Este caso de uso permite gerar os dados de integração com o Sistema Contábil da empresa.
	 * [UC3065] Gerar Integração Contábil
	 * 
	 * @author Josenildo Neves
	 * @data 15/08/2012
	 * @param idFuncionalidadeIniciada
	 */
	public void gerarIntegracaoContabil(int idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;

		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.FUNCIONALIDADE, idFuncionalidadeIniciada);

		try{

			String P_CODIGO_ARRECADADOR_INT_CONTABIL = ParametroContabil.P_CODIGO_ARRECADADOR_INT_CONTABIL.executar();
			String P_CODIGO_LOCALIDADE_INT_CONTABIL = ParametroContabil.P_CODIGO_LOCALIDADE_INT_CONTABIL.executar();

			// [SB0001 - Obter data inicial de lançamento para a integração contábil]
			Date dataInicialContabil = this.obterDataInicialLancamentoIntegracaoContabil();
			// [SB0002 - Obter data final de lançamento para a integração contábil]
			Date dataFinalContabil = this.obterDataFinalLancamentoIntegracaoContabil();

			// Fluxo Principal
			List<LancamentoContabilSintetico> listaLancamentoContabeisSintetico = null;
			try{
				listaLancamentoContabeisSintetico = repositorioContabil.pesquisaLancamentosContabeisSintetico(dataInicialContabil,
								dataFinalContabil);
			}catch(ErroRepositorioException e){
				sessionContext.setRollbackOnly();
				throw new ControladorException("erro.sistema", e);
			}

			// [FS0001] - Verificar existência de lançamentos para integração contábil
			if(Util.isVazioOrNulo(listaLancamentoContabeisSintetico)){
				LOGGER.info(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.nenhum.dados.integracao"));
			}else{
				// [SB0003 - Obter o número do último lançamento contábil registrado]
				// [SB0004 - Gerar Dados para Integração Contábil]
				String parametroGerarIntegracaoContabil = ParametroContabil.P_GERAR_INTEGRACAO_CONTABIL.executar();
				if(P_GERAR_INTEGRACAO_CONTABIL_MODELO1.equals(parametroGerarIntegracaoContabil)){
					criarIntegracaoGrupoELancamentoContabilModelo1(listaLancamentoContabeisSintetico, P_CODIGO_ARRECADADOR_INT_CONTABIL,
									P_CODIGO_LOCALIDADE_INT_CONTABIL);
				}else if(P_GERAR_INTEGRACAO_CONTABIL_MODELO2.equals(parametroGerarIntegracaoContabil)){
					criarIntegracaoGrupoELancamentoContabilModelo2(listaLancamentoContabeisSintetico);
				}else{
					String msg = "Modelo ou Parametro 'P_GERAR_INTEGRACAO_CONTABIL' não definido.";
					LOGGER.error(msg);
					throw new ControladorException(msg);

				}
			}

			// Finaliza o processamento batch com sucesso.
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception e){
			sessionContext.setRollbackOnly();
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);
			throw new EJBException(e);
		}

	}

	/**
	 * Metodo temporário para ajuste contábil. Analista de negócio Luís Eduardo
	 * Ocorrência: OC1128813
	 * 
	 * @author Saulo Lima
	 * @since 20/08/2013
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void gerarIntegracaoContabilAjusteTemp(int idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;

		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.FUNCIONALIDADE, idFuncionalidadeIniciada);

		try{

			String P_CODIGO_ARRECADADOR_INT_CONTABIL = ParametroContabil.P_CODIGO_ARRECADADOR_INT_CONTABIL.executar();
			String P_CODIGO_LOCALIDADE_INT_CONTABIL = ParametroContabil.P_CODIGO_LOCALIDADE_INT_CONTABIL.executar();

			String pDataGeracaoAjuste = ParametroContabil.P_DATA_GERACAO_PARA_INTEGRACAO_CONTABIL_AJUSTE.executar();
			Date dataContabil = Util.converteStringParaDate(pDataGeracaoAjuste, false);

			// Fluxo Principal
			List<LancamentoContabilSintetico> listaLancamentoContabeisSintetico = null;
			try{
				listaLancamentoContabeisSintetico = repositorioContabil.pesquisaLancamentosContabeisSinteticoAjusteTemp(dataContabil);
			}catch(ErroRepositorioException e){
				sessionContext.setRollbackOnly();
				throw new ControladorException("erro.sistema", e);
			}

			// [FS0001] - Verificar existência de lançamentos para integração contábil
			if(Util.isVazioOrNulo(listaLancamentoContabeisSintetico)){
				LOGGER.info(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.nenhum.dados.integracao"));
			}else{
				// [SB0003 - Obter o número do último lançamento contábil registrado]
				// [SB0004 - Gerar Dados para Integração Contábil]
				String parametroGerarIntegracaoContabil = ParametroContabil.P_GERAR_INTEGRACAO_CONTABIL.executar();
				if(P_GERAR_INTEGRACAO_CONTABIL_MODELO1.equals(parametroGerarIntegracaoContabil)){
					criarIntegracaoGrupoELancamentoContabilModelo1(listaLancamentoContabeisSintetico, P_CODIGO_ARRECADADOR_INT_CONTABIL,
									P_CODIGO_LOCALIDADE_INT_CONTABIL);
				}else if(P_GERAR_INTEGRACAO_CONTABIL_MODELO2.equals(parametroGerarIntegracaoContabil)){
					criarIntegracaoGrupoELancamentoContabilModelo2(listaLancamentoContabeisSintetico);
				}else{
					String msg = "Modelo ou Parametro 'P_GERAR_INTEGRACAO_CONTABIL' não definido.";
					LOGGER.error(msg);
					throw new ControladorException(msg);

				}
			}

			// Finaliza o processamento batch com sucesso.
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception e){
			sessionContext.setRollbackOnly();
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);
			throw new EJBException(e);
		}

	}

	/**
	 * Método criarIntegracaoGrupoELancamentoContabilModelo2
	 * <p>
	 * Esse método implementa a geração de integração contabil seguindo o modelo 2 : geração de
	 * arquivo CSV com os lançamentos.
	 * </p>
	 * RASTREIO: [OC750003][UC3065][SB0004]
	 * 
	 * @param listaLancamentoContabeisSintetico
	 * @author Marlos Ribeiro
	 * @throws IOException
	 * @throws ControladorException
	 * @since 14/09/2012
	 */
	private void criarIntegracaoGrupoELancamentoContabilModelo2(List<LancamentoContabilSintetico> listaLancamentoContabeisSintetico)
					throws ControladorException{

		String nomeQualificadoArquivo = null;
		try{

			nomeQualificadoArquivo = ConstantesConfig.DIR_RECURSOS_EXTERNOS + "INTEGRACAO_CONTABIL" + System.getProperty("file.separator")
							+ gerarNomeArquivo();
			FileWriter writer = criarArquivo(nomeQualificadoArquivo);
			for(LancamentoContabilSintetico lancamentoContabilSintetico : listaLancamentoContabeisSintetico){
				String numeroContaDebito = obterConta(lancamentoContabilSintetico, ObjetoContabil.DEBITO);
				String numeroContaCredito = obterConta(lancamentoContabilSintetico, ObjetoContabil.CREDITO);

				if(!Util.isVazioOuBranco(numeroContaDebito) && !Util.isVazioOuBranco(numeroContaCredito)){
					// 1 FILIAL
					writer.append(obterCodigoFilial(lancamentoContabilSintetico));
					// 2 SEPARADOR
					writer.append(VIRGULA);
					// 3. DATA LANCAMENTO
					writer.append(Util.formatarData(lancamentoContabilSintetico.getDataContabil()));
					// 4 SEPARADOR
					writer.append(VIRGULA);
					// 5. CONTA DEBITO
					writer.append(obterConta(lancamentoContabilSintetico, ObjetoContabil.DEBITO));
					// 6 SEPARADOR
					writer.append(VIRGULA);
					// 7 CONTA CREDITO
					writer.append(obterConta(lancamentoContabilSintetico, ObjetoContabil.CREDITO));
					// 8 SEPARADOR
					writer.append(VIRGULA);
					// 9 VALOR
					writer.append(lancamentoContabilSintetico.getValor().toString());
					// 10 SEPARADOR
					writer.append(VIRGULA);
					// 11 CODIGO HISTORICO
					if(!Util.isVazioOuBranco(lancamentoContabilSintetico)
									&& !Util.isVazioOuBranco(lancamentoContabilSintetico.getContaBancaria())){
						writer.append(ConstantesSistema.CODIGO_HIST_LANC_CONT_SINTETICO_COM_CONTA_BANCARIA);
					}else{
						writer.append(ConstantesSistema.CODIGO_HIST_LANC_CONT_SINTETICO_SEM_CONTA_BANCARIA);
					}

					// 12 SEPARADOR
					writer.append(VIRGULA);
					// 13 COD_DOCUMENTO
					writer.append("\"" + Util.formataMesAno(lancamentoContabilSintetico.getDataContabil()) + "\"");
					writer.append(System.getProperty("line.separator"));

				}
			}

			writer.flush();
			writer.close();

		}catch(IOException e){
			String msg = "Falha ao criar arquivo: \"" + nomeQualificadoArquivo + "\"";
			LOGGER.error(msg, e);
			throw new ControladorException(e, msg);
		}

	}

	private FileWriter criarArquivo(String nomeQualificadoArquivo) throws IOException{

		File file = new File(nomeQualificadoArquivo);
		file.getParentFile().mkdirs();
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		return writer;
	}

	/**
	 * Método obterConta
	 * <p>
	 * Esse método implementa a regra para obtenção do Número da Conta corespondente ao critério de
	 * pesquisa.
	 * </p>
	 * RASTREIO: [OC750003][UC3065][SB0004][ITEM 5 e 7]
	 * 
	 * @param lancamentoContabilSintetico
	 * @param credito
	 * @return
	 * @author Marlos Ribeiro
	 * @throws ControladorException
	 * @since 14/09/2012
	 */
	private String obterConta(LancamentoContabilSintetico lancamentoContabilSintetico, ObjetoContabil credito) throws ControladorException{

		try{
			String numeroConta = repositorioContabil.obterNumeroConta(credito, lancamentoContabilSintetico.getEventoComercial(),
							lancamentoContabilSintetico.getCategoria(), lancamentoContabilSintetico.getLancamentoItemContabil(),
							lancamentoContabilSintetico.getImpostoTipo());

			return numeroConta == null ? "" : numeroConta.trim();
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Método obterCodigoRegional
	 * <p>
	 * Esse método implementa regra para obtenção do Código da Gerência regional referente a
	 * localidade do lancamento.
	 * </p>
	 * RASTREIO: [OC750003][UC3065][SB0004][ITEM 1]
	 * 
	 * @param lancamentoContabilSintetico
	 * @return
	 * @author Marlos Ribeiro
	 * @throws ControladorException
	 * @since 14/09/2012
	 */
	private String obterCodigoFilial(LancamentoContabilSintetico lancamentoContabilSintetico) throws ControladorException{

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, lancamentoContabilSintetico
						.getIdUnidadeContabilAgrupamento()));
		Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroLocalidade,
						Localidade.class.getName()));
		/*
		 * [FS0005] - Verificar existência da localidade Filial.
		 * Caso o código da localidade seja 0 (zero)
		 * ou não encontre o código da Filial,
		 * então atribuir o valor 1 ao código da Filial
		 */
		return (localidade == null || localidade.getCodigoLocalidadeContabil() == null) ? "1" : localidade.getCodigoLocalidadeContabil()
						.toString();
	}

	/**
	 * Método gerarNomeArquivo
	 * <p>
	 * Esse método implementa a padronização do nome dos arquivos gerados pelo moledo 2 de
	 * integração contábil. 'EMPRESA_Integracao_Contabil_ddmmyyy_hhmm.txt' onde a EMPRESA é
	 * encontrado na tabela SISTEMA_PARAMETROS, coluna PARM_NMABREVIADOEMPRESA
	 * </p>
	 * RASTREIO: [OC750003][UC3065][SB0004]
	 * nomenclatura para os arquivos derados no modelo 2 de integração contábio.
	 * 
	 * @return
	 * @author Marlos Ribeiro
	 * @throws ControladorException
	 * @since 14/09/2012
	 */
	private String gerarNomeArquivo() throws ControladorException{

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
		String empresa = sistemaParametro.getNomeAbreviadoEmpresa();

		return String.format("%s_INTEGRACAO_CONTABIL_%2$tY%2$tm%2$td_%2$tH%2$tM.txt", empresa, Calendar.getInstance().getTime());
	}

	/**
	 * [UC3065] Gerar Integração Contábil
	 * [SB0002] - Obter data final de lançamento para a Integração Contábil
	 * 
	 * @return dataFinal
	 * @throws ControladorException
	 */
	private Date obterDataFinalLancamentoIntegracaoContabil() throws ControladorException{

		Date dataFim = null;
		try{
			/*
			 * [UC3065][FS0004] Caso não exista nenhum lançamento na tabela TI_GRP_CONT_GCS_N, então
			 * atribuir data corrente para a maior data contábil
			 */
			Date maiorDataContabil = repositorioContabil.obterDataFinalLancamentoIntegracaoContabil();
			maiorDataContabil = maiorDataContabil == null ? Util.formatarDataFinal(Calendar.getInstance().getTime()) : maiorDataContabil;

			Integer referenciaMesAnoMaiorData = Util.recuperaAnoMesDaData(maiorDataContabil);
			Integer referenciaContabil = Integer.valueOf(ParametroContabil.P_REFERENCIA_CONTABIL.executar());
			Integer referenciaFaturamento = getControladorUtil().pesquisarParametrosDoSistema().getAnoMesFaturamento();

			if(!referenciaContabil.equals(referenciaFaturamento) && referenciaMesAnoMaiorData.compareTo(referenciaContabil) > 0){
				dataFim = Util.converterAnoMesParaDataFinal(referenciaContabil.toString(), 1);
			}else{
				dataFim = maiorDataContabil;
			}

		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
		if(dataFim != null){
			dataFim = Util.formatarDataFinal(dataFim);
		}
		return dataFim;
	}

	/**
	 * [UC3065] Gerar Integração Contábil
	 * [SB0001] - Obter data inicial de lançamento para a Integração Contábil
	 * 
	 * @return dataInicial
	 * @throws ControladorException
	 */
	private Date obterDataInicialLancamentoIntegracaoContabil() throws ControladorException{

		try{
			Date date = null;
			String parametroGerarIntegracaoContabil = ParametroContabil.P_GERAR_INTEGRACAO_CONTABIL.executar();
			if(P_GERAR_INTEGRACAO_CONTABIL_MODELO1.equals(parametroGerarIntegracaoContabil)){
				date = repositorioIntegracaoPiramide.obterDataInicialLancamentoIntegracaoContabil();
				if(date == null){
					/*
					 * [FS0003] caso não exista nenhum lançamento na tabela TI_GRP_CONT_GCS_N, então
					 * atribuir ‘31-08-2012’ para a data inicial
					 */
					date = Util.converteStringParaDate(DATA_INICIAL_PADRAO);
				}else{
					date = Util.adicionarNumeroDiasDeUmaData(date, 1);
				}
			}else if(P_GERAR_INTEGRACAO_CONTABIL_MODELO2.equals(parametroGerarIntegracaoContabil)){
				date = Util.converterAnoMesParaDataInicial(ParametroContabil.P_REFERENCIA_CONTABIL.executar());
			}else{
				String msg = "Modelo de geração de integração contabil não especificado em 'P_GERAR_INTEGRACAO_CONTABIL'";
				LOGGER.error(msg);
				throw new ControladorException(msg);
			}

			if(date != null){
				date = Util.formatarDataInicial(date);
			}
			return date;
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC3065] Gerar Integração Contábil
	 * 1. Para cada registro retornado da relação de lançamentos contábeis
	 * 
	 * @throws ControladorException
	 */
	private void criarIntegracaoGrupoELancamentoContabilModelo1(List<LancamentoContabilSintetico> lancamentosContabeisSintetico,
					String P_CODIGO_ARRECADADOR_INT_CONTABIL, String P_CODIGO_LOCALIDADE_INT_CONTABIL)
					throws ControladorException{

		int maiorNumeroLanccamento;

		try{
			try{
				maiorNumeroLanccamento = repositorioUtil.valorMaximo(TabelaIntegracaoGrupoContabil.class, "numeroIdLancamento");
			}catch(NullPointerException e){
				// Caso seja o primeira vez que rode ele entra neste catch e inicia com 0 + 1
				maiorNumeroLanccamento = 0;
			}
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
		maiorNumeroLanccamento++;

		List<TabelaIntegracaoGrupoContabil> lancamentosGrupoContabil = new ArrayList<TabelaIntegracaoGrupoContabil>();
		List<TabelaIntegracaoLancamentoContabil> lancamentosContabilDebito = new ArrayList<TabelaIntegracaoLancamentoContabil>();
		List<TabelaIntegracaoLancamentoContabil> lancamentosContabilCredito = new ArrayList<TabelaIntegracaoLancamentoContabil>();

		for(LancamentoContabilSintetico item : lancamentosContabeisSintetico){

			lancamentosGrupoContabil.add(gerarDadosIntegracaoContabilGrupoContabil(maiorNumeroLanccamento, item));

			lancamentosContabilDebito.add(gerarDadosIntegracaoContabilLancamentoContabil(maiorNumeroLanccamento, NUMERO_SEQUENCIAL_DEBITO,
							item, P_CODIGO_ARRECADADOR_INT_CONTABIL, P_CODIGO_LOCALIDADE_INT_CONTABIL));

			lancamentosContabilCredito.add(gerarDadosIntegracaoContabilLancamentoContabil(maiorNumeroLanccamento,
							NUMERO_SEQUENCIAL_CREDITO, item, P_CODIGO_ARRECADADOR_INT_CONTABIL, P_CODIGO_LOCALIDADE_INT_CONTABIL));

			maiorNumeroLanccamento++;
		}

		// Inserir a lista de objetos possivelmente poderia ser implementado a saída em um TXT.
		getControladorUtil().inserirListaObjetos(lancamentosGrupoContabil);
		getControladorUtil().inserirListaObjetos(lancamentosContabilDebito);
		getControladorUtil().inserirListaObjetos(lancamentosContabilCredito);

	}

	/**
	 * [UC3065] Gerar Integração Contábil
	 * 1.1. O sistema gera a tabela de TI_GRP_CONT_GCS_N de acordo com as seguintes regras:
	 * 
	 * @throws ControladorException
	 */
	private TabelaIntegracaoGrupoContabil gerarDadosIntegracaoContabilGrupoContabil(int proximoId,
					LancamentoContabilSintetico lancamentoContabilSintetico) throws ControladorException{

		TabelaIntegracaoGrupoContabil retorno = new TabelaIntegracaoGrupoContabil();

		retorno.setNumeroIdLancamento(proximoId);
		retorno.setCodigoEmpresaOrigem("0");
		retorno.setCodigoFilialOrigem(obterIdGerenciaRegionalPorUnidadeContabilAgrupamento(lancamentoContabilSintetico));
		retorno.setCodigoTipoLancamento(TabelaIntegracaoGrupoContabil.CODIGO_TIPO_LANCAMENTO_CONTABIL);
		retorno.setDataLancamento(lancamentoContabilSintetico.getDataContabil());
		retorno.setCodigoDocumento(obterCodigoDocumento(lancamentoContabilSintetico.getEventoComercial().getId()));
		retorno.setCodigoArquivo(Util.formatarDataAAAAMMDD(new Date()));
		retorno.setCodigoOperacaoRegistro(TabelaIntegracaoGrupoContabil.CODIGO_OPERACAO_REGISTRO);
		retorno.setCodigoStatusRegistro(TabelaIntegracaoGrupoContabil.CODIGO_STATUS_REGISTRO);

		return retorno;
	}

	private String obterCodigoDocumento(int idEventoComercial) throws ControladorException{

		String retorno = null;
		try{
			String retornoTemp = repositorioContabil.obterCampoPorParametroEmEventoComercialLancamentoPorEventoComercial(idEventoComercial,
							"descricaoDocumento");
			if(Util.isVazioOuBranco(retornoTemp)){
				retorno = " ";
			}else if(retornoTemp.length() > 14){
				retorno = retornoTemp.substring(0, 14);
			}else{
				retorno = retornoTemp;
			}
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
		return retorno;
	}

	private String obterIdGerenciaRegionalPorUnidadeContabilAgrupamento(LancamentoContabilSintetico lancamentoContabilSintetico){

		String retorno = "1";
		try{
			int gerenciaRegional = repositorioContabil.obterIdGerenciaRegionalPorUnidadeContabilAgrupamento(lancamentoContabilSintetico
							.getIdUnidadeContabilAgrupamento());
			if(0 == gerenciaRegional){
				retorno = "1";
			}else{
				retorno = String.valueOf(gerenciaRegional);
			}
		}catch(ErroRepositorioException e){
			retorno = "1";
		}
		return retorno;
	}

	/**
	 * [UC3065] Gerar Integração Contábil
	 * 1.1. O sistema gera a tabela de TI_GRP_CONT_GCS_N de acordo com as seguintes regras:
	 * 
	 * @throws ControladorException
	 */
	private TabelaIntegracaoLancamentoContabil gerarDadosIntegracaoContabilLancamentoContabil(int idMax, int numeroSequencial,
					LancamentoContabilSintetico lancamentoContabilSintetico, String P_CODIGO_ARRECADADOR_INT_CONTABIL,
					String P_CODIGO_LOCALIDADE_INT_CONTABIL) throws ControladorException{

		TabelaIntegracaoLancamentoContabil retorno = new TabelaIntegracaoLancamentoContabil();
		retorno.setComp_id(new TabelaIntegracaoLancamentoContabilPK());
		retorno.getComp_id().setNumeroIdLancamento(idMax);
		retorno.getComp_id().setNumeroSequencial(numeroSequencial);
		retorno.setValorLancamento(lancamentoContabilSintetico.getValor());
		retorno.setCodigoUnidadeOrigem(CODIGO_UNIDADE_ORIGEM);
		retorno.setCodigoCcustoOrigem("          ");
		retorno.setCodigoHistoricoOrigem(TabelaIntegracaoLancamentoContabil.CODIGO_HISTORICO_ORIGEM);
		retorno.setDescricaoComplemento(obterDescricaoEventoComercialLancamento(lancamentoContabilSintetico));

		if(NUMERO_SEQUENCIAL_DEBITO == numeroSequencial){ // Débito
			
			retorno.setCodigoOperacao(TabelaIntegracaoLancamentoContabil.CODIGO_OPERACAO_DEBITO);
			retorno.setCodigoContaOrigem(obterCodigoContaOrigem(TabelaIntegracaoLancamentoContabil.CODIGO_OPERACAO_DEBITO,
							lancamentoContabilSintetico));

			String codigoGrupoContaAuxiliarOrigemDebito = null;
			if(this.getControladorContabil().obterCodigoGrupoContaAuxiliarOrigemDebito(lancamentoContabilSintetico) != null){
				codigoGrupoContaAuxiliarOrigemDebito = this.getControladorContabil()
								.obterCodigoGrupoContaAuxiliarOrigemDebito(lancamentoContabilSintetico).trim();
			}

			if(codigoGrupoContaAuxiliarOrigemDebito != null
							&& codigoGrupoContaAuxiliarOrigemDebito.equals(P_CODIGO_ARRECADADOR_INT_CONTABIL)){

				// String idBanco =
				// String.valueOf(lancamentoContabilSintetico.getContaBancaria().getAgencia().getBanco().getId());
				// retorno.setCodigoContaAuxiliarOrigem(Util.completarStringZeroEsquerda(String.valueOf(idBanco),
				// 3));

				if(lancamentoContabilSintetico != null && lancamentoContabilSintetico.getContaBancaria() != null
								&& lancamentoContabilSintetico.getContaBancaria().getNumeroContaContabil() != null){

					// atribuir o código da conta contábil (CTBC_NNCONTACONTABIL da tabela
					// CONTA_BANCARIA com CTCB_ID = CTBC_ID da tabela LANCAMENTO_CONTABIL_SINTETICO
					retorno.setCodigoContaAuxiliarOrigem(Util.completarStringZeroEsquerda(
									String.valueOf(lancamentoContabilSintetico.getContaBancaria().getNumeroContaContabil()), 3));
				}else{
					throw new ControladorException("erro.conta.contabil.para.banco.inexistente");
				}

				retorno.setCodigoGrupoContaAuxiliarOrigem(P_CODIGO_ARRECADADOR_INT_CONTABIL);

			}else{
				retorno.setCodigoContaAuxiliarOrigem(Util.completarStringZeroEsquerda(
								String.valueOf(lancamentoContabilSintetico.getIdUnidadeContabilAgrupamento()), 3));
				retorno.setCodigoGrupoContaAuxiliarOrigem(P_CODIGO_LOCALIDADE_INT_CONTABIL);
			}


		}else{ // Crédito

			String codigoContaOrigem = obterCodigoContaOrigem(TabelaIntegracaoLancamentoContabil.CODIGO_OPERACAO_CREDITO,
							lancamentoContabilSintetico);
			retorno.setCodigoContaOrigem(codigoContaOrigem);
			retorno.setCodigoOperacao(TabelaIntegracaoLancamentoContabil.CODIGO_OPERACAO_CREDITO);

			String codigoGrupoContaAuxiliarOrigemCredito = null;
			if(this.getControladorContabil().obterCodigoGrupoContaAuxiliarOrigemCredito(lancamentoContabilSintetico) != null){
				codigoGrupoContaAuxiliarOrigemCredito = this.getControladorContabil()
								.obterCodigoGrupoContaAuxiliarOrigemCredito(lancamentoContabilSintetico).trim();
			}

			if(codigoGrupoContaAuxiliarOrigemCredito != null
							&& codigoGrupoContaAuxiliarOrigemCredito.equals(P_CODIGO_ARRECADADOR_INT_CONTABIL)){

				// String idBanco =
				// String.valueOf(lancamentoContabilSintetico.getContaBancaria().getAgencia().getBanco().getId());
				// retorno.setCodigoContaAuxiliarOrigem(Util.completarStringZeroEsquerda(String.valueOf(idBanco),
				// 3));

				if(lancamentoContabilSintetico != null && lancamentoContabilSintetico.getContaBancaria() != null
								&& lancamentoContabilSintetico.getContaBancaria().getNumeroContaContabil() != null){

					// atribuir o código da conta contábil (CTBC_NNCONTACONTABIL da tabela
					// CONTA_BANCARIA com CTCB_ID = CTBC_ID da tabela LANCAMENTO_CONTABIL_SINTETICO
					retorno.setCodigoContaAuxiliarOrigem(Util.completarStringZeroEsquerda(
									String.valueOf(lancamentoContabilSintetico.getContaBancaria().getNumeroContaContabil()), 3));
				}else{
					throw new ControladorException("erro.conta.contabil.para.banco.inexistente");
				}

				retorno.setCodigoGrupoContaAuxiliarOrigem(P_CODIGO_ARRECADADOR_INT_CONTABIL);

			}else{
				retorno.setCodigoContaAuxiliarOrigem(Util.completarStringZeroEsquerda(
								String.valueOf(lancamentoContabilSintetico.getIdUnidadeContabilAgrupamento()), 3));
				retorno.setCodigoGrupoContaAuxiliarOrigem(P_CODIGO_LOCALIDADE_INT_CONTABIL);
			}

		}

		return retorno;

	}

	private String obterCodigoContaOrigem(String codigoOperacao, LancamentoContabilSintetico lancamentoContabilSintetico)
					throws ControladorException{

		String retorno;
		try{
			retorno = repositorioContabil.obterCodigoContaOrigem(codigoOperacao, lancamentoContabilSintetico);
			if(retorno == null){
				retorno = " ";
			}
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
		return retorno;
	}

	private String obterDescricaoEventoComercialLancamento(LancamentoContabilSintetico lancamentoContabilSintetico)
					throws ControladorException{

		String retorno = "";
		try{
			String retornoTemp = repositorioContabil.obterCampoPorParametroEmEventoComercialLancamentoPorEventoComercial(
							lancamentoContabilSintetico.getEventoComercial().getId(), "descricaoComplemento");
			if(retornoTemp != null && retornoTemp.length() > 14){
				retorno = retornoTemp.substring(0, 14);
			}else if(retornoTemp == null){
				retorno = " ";
			}else{
				retorno = retornoTemp;
			}
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
		return retorno;
	}

	// [UC3066] Gerar Integração Retenção

	/**
	 * Este caso de uso realiza a geração dos dados de Integração relativos à retenção com o Sistema
	 * Financeiro da empresa através de tabelas de integração.
	 * [UC3066] Gerar Integração Retenção
	 * 
	 * @author Josenildo Neves
	 * @data 15/08/2012
	 * @param
	 * @return void
	 */
	public void gerarIntegracaoRetencao(int idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;

		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.FUNCIONALIDADE, idFuncionalidadeIniciada);

		try{

			List<TabelaIntegracaoApuCsocRetida> listaIntegracaoApuCsocRetidasPIS = null;
			List<TabelaIntegracaoApuCsocRetida> listaIntegracaoApuCsocRetidasCOFFINS = null;

			// [SB0001 - Obter data inicial de pagamento para a integração de retenção]
			Date dataInicialPagamento = this.obterDataInicialPagamentoRetencao();
			// [SB0002 - Obter data final de pagamento para a integração de retenção]
			Date dataFinalPagamento = this.obterDataFinalPagamentoRetencao(dataInicialPagamento);

			// Fluxo Principal
			List<RetencaoHelper> listaRetencoes = null;
			try{
				listaRetencoes = repositorioContabil.pesquisaRelacaoRetencao(dataInicialPagamento, dataFinalPagamento);
			}catch(ErroRepositorioException e){
				sessionContext.setRollbackOnly();
				throw new ControladorException("erro.sistema", e);
			}

			// [FS0001 - Verificar existência de pagamento para a integração de retenção]
			if(Util.isVazioOrNulo(listaRetencoes)){
				throw new ControladorException("atencao.nenhum.dados.integracao");
			}

			// [SB0004 - Gerar Dados para Integração de Retenção]
			this.gerarDadosIntegracaoRetencao(listaIntegracaoApuCsocRetidasPIS, listaIntegracaoApuCsocRetidasCOFFINS, listaRetencoes);

			// Finaliza o processamento batch com sucesso.
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(ControladorException e){
			sessionContext.setRollbackOnly();
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);
			throw new EJBException(e);
		}

	}

	/**
	 * [UC3066] Gerar Integração Retenção
	 * [SB0002 - Obter data final de pagamento para a integração de retenção]
	 * 
	 * @return dataFinal
	 * @throws ControladorException
	 */
	private Date obterDataFinalPagamentoRetencao(Date datainicial) throws ControladorException{

		return Util.retornaDataInicialComUltimoDiaMes(datainicial);
	}

	/**
	 * [UC3066] Gerar Integração Retenção
	 * [SB0001 - Obter data inicial de pagamento para a integração de retenção]
	 * 
	 * @return dataInicial
	 * @throws ControladorException
	 */
	private Date obterDataInicialPagamentoRetencao() throws ControladorException{

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
		Date dateInicialPagamento = null;

		try{
			dateInicialPagamento = repositorioIntegracaoPiramide.obterDataInicialPagamentoRetencao();
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		// [FS0002] - Verificar existência de lançamento na Tabela de TI
		if(!Util.isVazioOuBranco(dateInicialPagamento)){

			dateInicialPagamento = Util.retornaDataInicialComPrimeiroDiaMesSeguinte(dateInicialPagamento);
		}else{
			dateInicialPagamento = Util.converterAnoMesParaDataInicial(DATA_INICIAL_PADRAO);
		}

		// [FS003 - Verificar compatibilidade da data inicial de pagamento]
		int resultado = Util.compararData(dateInicialPagamento,
						Util.converterAnoMesParaDataInicial(sistemaParametro.getAnoMesFaturamento().toString()));
		if(resultado == 1){
			throw new ControladorException("atencao.ult.ref.menor.ref.faturamento");
		}

		return dateInicialPagamento;
	}

	/**
	 * [UC3066] Gerar Integração Retenção
	 * [SB0004 - Gerar Dados para Integração de Retenção]
	 * 
	 * @param ListaIntegracaoApuCsocRetidasPIS
	 * @param ListaIntegracaoApuCsocRetidasCOFFINS
	 * @param listaRetencoes
	 * @throws ControladorException
	 */
	private void gerarDadosIntegracaoRetencao(List<TabelaIntegracaoApuCsocRetida> listaIntegracaoApuCsocRetidasPIS,
					List<TabelaIntegracaoApuCsocRetida> listaIntegracaoApuCsocRetidasCOFFINS, List<RetencaoHelper> listaRetencoes)
					throws ControladorException{

		listaIntegracaoApuCsocRetidasPIS = new ArrayList<TabelaIntegracaoApuCsocRetida>();
		listaIntegracaoApuCsocRetidasCOFFINS = new ArrayList<TabelaIntegracaoApuCsocRetida>();
		String cpfCnpj = null;
		String codigoRegional = null;

		for(RetencaoHelper retencaoHelper : listaRetencoes){

			// [FS0004] - Verificar existência de CNPJ ou CPF
			if(!Util.isVazioOuBranco(retencaoHelper.getCnpjCliente()) || !Util.isVazioOuBranco(retencaoHelper.getCpfCliente())){

				if(!Util.isVazioOuBranco(retencaoHelper.getCnpjCliente())){
					cpfCnpj = retencaoHelper.getCnpjCliente();
				}else{
					if(!Util.isVazioOuBranco(retencaoHelper.getCpfCliente())){
						cpfCnpj = retencaoHelper.getCpfCliente();
					}
				}

				// [FS0005] - Verificar existência da regional
				if(!Util.isVazioOuBranco(retencaoHelper.getCodigoRegional())){
					codigoRegional = retencaoHelper.getCodigoRegional().toString();
				}else{
					codigoRegional = CODIGO_REGIONAL_PADRAO;
				}

				TabelaIntegracaoApuCsocRetida tabelaIntegracaoApuCsocRetidaPis = new TabelaIntegracaoApuCsocRetida();

				TabelaIntegracaoApuCsocRetidaPK tabelaIntegracaoApuCsocRetidaPKPIS = new TabelaIntegracaoApuCsocRetidaPK(codigoRegional,
								CODIGO_SISTEMA_ORIGEM, CODIGO_IMPOSTO_PIS, CODIGO_CSOCIAL_APURAD, cpfCnpj,
								retencaoHelper.getDataPagamento());

				tabelaIntegracaoApuCsocRetidaPis.setComp_id(tabelaIntegracaoApuCsocRetidaPKPIS);
				tabelaIntegracaoApuCsocRetidaPis.setCodigoReceita(CODIGO_RECEITA);
				tabelaIntegracaoApuCsocRetidaPis.setCodigoNaturezaRetencaoFonte(CODIGO_NATUREZA_RETENCAO_FONTE);
				tabelaIntegracaoApuCsocRetidaPis.setValorBaseRetencao(retencaoHelper.getValorConta());
				tabelaIntegracaoApuCsocRetidaPis.setValorRetido(retencaoHelper.getValorRetidoPIS());
				tabelaIntegracaoApuCsocRetidaPis.setCodigoOperacaoRegistrada(CODIGO_OPERACAO_REGISTRADA);
				tabelaIntegracaoApuCsocRetidaPis.setCodigoStatusResgistrado(CODIGO_STATUS_REGISTRADO);
				tabelaIntegracaoApuCsocRetidaPis.setDescricaoErroRegistro(DESCRICAO_ERRO_REGISTRO_VAZIO);

				TabelaIntegracaoApuCsocRetida tabelaIntegracaoApuCsocRetidaCOFFINS = new TabelaIntegracaoApuCsocRetida();

				TabelaIntegracaoApuCsocRetidaPK tabelaIntegracaoApuCsocRetidaPKCOFFINS = new TabelaIntegracaoApuCsocRetidaPK(
								codigoRegional, CODIGO_SISTEMA_ORIGEM, CODIGO_IMPOSTO_COFFINS, CODIGO_CSOCIAL_APURAD, cpfCnpj,
								retencaoHelper.getDataPagamento());

				tabelaIntegracaoApuCsocRetidaCOFFINS.setComp_id(tabelaIntegracaoApuCsocRetidaPKCOFFINS);
				tabelaIntegracaoApuCsocRetidaCOFFINS.setCodigoReceita(CODIGO_RECEITA);
				tabelaIntegracaoApuCsocRetidaCOFFINS.setCodigoNaturezaRetencaoFonte(CODIGO_NATUREZA_RETENCAO_FONTE);
				tabelaIntegracaoApuCsocRetidaCOFFINS.setValorBaseRetencao(retencaoHelper.getValorConta());
				tabelaIntegracaoApuCsocRetidaCOFFINS.setValorRetido(retencaoHelper.getValorRetidoCOFFINS());
				tabelaIntegracaoApuCsocRetidaCOFFINS.setCodigoOperacaoRegistrada(CODIGO_OPERACAO_REGISTRADA);
				tabelaIntegracaoApuCsocRetidaCOFFINS.setCodigoStatusResgistrado(CODIGO_STATUS_REGISTRADO);
				tabelaIntegracaoApuCsocRetidaCOFFINS.setDescricaoErroRegistro(DESCRICAO_ERRO_REGISTRO_VAZIO);

				listaIntegracaoApuCsocRetidasPIS.add(tabelaIntegracaoApuCsocRetidaPis);
				listaIntegracaoApuCsocRetidasCOFFINS.add(tabelaIntegracaoApuCsocRetidaCOFFINS);
			}

		}

		this.getControladorUtil().inserirColecaoObjetos(listaIntegracaoApuCsocRetidasPIS);
		this.getControladorUtil().inserirColecaoObjetos(listaIntegracaoApuCsocRetidasCOFFINS);

	}

	/**
	 * Método gerarIntegracaoDeferimento
	 * <p>
	 * Esse método implementa a integração deferimento.
	 * </p>
	 * RASTREIO: [OC827869][UC3067]
	 * 
	 * @param idFuncionalidadeIniciada
	 *            : id da Funcionalidade Iniciada
	 * @throws ControladorException
	 * @author Marlos Ribeiro
	 * @since 01/10/2012
	 */
	public void gerarIntegracaoDeferimento(Integer idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.FUNCIONALIDADE, idFuncionalidadeIniciada);
		try{
			/*
			 * [OC827869][UC3067][SB0001]: Obter e verificar existencia e compatibilidade da
			 * Referencia Base
			 */
			String referenciaBase = obterReferenciaBase();
			boolean abortarProcessamento = verificarReferenciaBaseDeferimento(referenciaBase);

			/*
			 * [OC827869][UC3067][FS0003 e FS0004]: Aborta o processamento se um dos fluxos for
			 * verdadeiro.
			 */
			if(!abortarProcessamento){
				// [OC827869][UC3067][SB0002]: Obter datas para consulta
				Date dataInicialDeferimento = Util.converterAnoMesParaDataInicial(referenciaBase);
				Date dataFinalDeferimento = Util.formatarDataFinal(Util.converterAnoMesParaDataFinal(referenciaBase));
				// [OC827869][UC3067][SB0003]: Selecionar as CONTAS
				List<LancamentoDeferimentoHelper> lacamentosNoPeriodo = consultarLancamentosNaReferenciaBase(referenciaBase,
								dataFinalDeferimento);
				// [OC827869][UC3067][SB0003.2.1]: Gera deferimento para a Referencia Base
				gerarDeferimentosNoPeriodo(lacamentosNoPeriodo, referenciaBase);
				// [OC827869][UC3067][SB0004]: Selecionar os PAGAMENTOS DE CONTAS
				List<LancamentoDeferimentoAnteriorHelper> lancamentosAnteriores = consultarLancamentosAnterioresReferenciaBase(
								Integer.valueOf(referenciaBase), dataInicialDeferimento, dataFinalDeferimento);
				// [OC827869][UC3067][SB0004.2.1]: Gera deferimento anteriores a Referencia Base
				gerarDeferimentosNoPeriodoAnterior(lancamentosAnteriores);
			}

			// Finaliza o processamento batch com sucesso.
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);
		}catch(Exception e){
			LOGGER.error("Ocorreu uma falha no processamento da integração deferimento", e);
			sessionContext.setRollbackOnly();
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);
			throw new EJBException(e);
		}
	}

	/**
	 * Método gerarDeferimentosNoPeriodoAnterior
	 * <p>
	 * Esse método implementa a geração de deferimentos na tabela TI_DIF_ANT_REC
	 * </p>
	 * RASTREIO: [OC827869][UC3067][SB0004.2.1]
	 * 
	 * @param pagamentos
	 * @author Marlos Ribeiro
	 * @throws ControladorException
	 * @since 03/10/2012
	 */
	private void gerarDeferimentosNoPeriodoAnterior(List<LancamentoDeferimentoAnteriorHelper> lancamentosDeferimentoAnteriorHelper)
					throws ControladorException{

		if(!Util.isVazioOrNulo(lancamentosDeferimentoAnteriorHelper)){

			Integer ultimoCodigoRegional = null;
			Integer ultimaContaReferencia = null;
			String ultimoClienteDoc = null;
			Date ultimaDataPagamento = null;

			// [OC827869][UC3067][SB0004.2][ITEM 20]
			BigDecimal percentualAliquotaPIS = consultarPercentualAliquota(IMPOSTO_TIPO_ALIQUOTA_PIS);

			// [OC827869][UC3067][SB0004.2][ITEM 22]
			BigDecimal percentualAliquotaCOFINS = consultarPercentualAliquota(IMPOSTO_TIPO_ALIQUOTA_COFINS);

			TabelaIntegracaoDeferimentoAnteriorReferenciaBase deferimento = null;
			for(LancamentoDeferimentoAnteriorHelper lancamento : lancamentosDeferimentoAnteriorHelper){
				// Se for o primeiro item da lista, inicializa um item de deferimento
				if(ultimoCodigoRegional == null || ultimaContaReferencia == null || ultimoClienteDoc == null || ultimaDataPagamento == null){
					ultimoCodigoRegional = lancamento.getCodigoRegional();
					ultimaContaReferencia = lancamento.getReferenciaConta();
					ultimoClienteDoc = lancamento.getDocumentoCliente();
					ultimaDataPagamento = lancamento.getDataPagamento();
					deferimento = new TabelaIntegracaoDeferimentoAnteriorReferenciaBase(ultimoCodigoRegional.toString(),
									Util.formatarAnoMesParaMesAnoSemBarra(lancamento.getReferenciaConta()), ultimoClienteDoc,
									ultimaDataPagamento, percentualAliquotaPIS, percentualAliquotaCOFINS);
				}
				/*
				 * Se o item corrente da lista de lancamento for semelhante (cliente / regional /
				 * referencia da conta / data pagamento) ao anteior acumula os valores,
				 * Se não registra o deferimento e inicializa um novo deferimento com base no item
				 * corrente
				 */

				if(lancamento.getDocumentoCliente() != null){

				if(!(ultimoCodigoRegional.equals(lancamento.getCodigoRegional()) //
								&& ultimaContaReferencia.equals(lancamento.getReferenciaConta()) //
								&& ultimoClienteDoc.equals(lancamento.getDocumentoCliente())//
				&& Util.formatarDataInicial(ultimaDataPagamento).equals(Util.formatarDataInicial(lancamento.getDataPagamento())))){

					getControladorUtil().inserir(deferimento);
					LOGGER.debug("novo 'TI_DIF_ANT_REC_PER' => " + deferimento);

					// RESETE deferimento
					ultimoCodigoRegional = lancamento.getCodigoRegional();
					ultimaContaReferencia = lancamento.getReferenciaConta();
					ultimoClienteDoc = lancamento.getDocumentoCliente();
					ultimaDataPagamento = lancamento.getDataPagamento();
					deferimento = new TabelaIntegracaoDeferimentoAnteriorReferenciaBase(ultimoCodigoRegional.toString(),
									Util.formatarAnoMesParaMesAnoSemBarra(lancamento.getReferenciaConta()), ultimoClienteDoc,
									ultimaDataPagamento, percentualAliquotaPIS, percentualAliquotaCOFINS);
				}
				deferimento.add(lancamento);
			}
			}

			// Deferimento para o ultimo lancamento
			getControladorUtil().inserir(deferimento);
			LOGGER.debug("novo 'TI_DIF_ANT_REC_PER' => " + deferimento);
		}
	}

	/**
	 * Método consultarPagamentos
	 * <p>
	 * Esse método implementa consulta de pagamentos de contas com referência anterior a referência
	 * base, cuja data de pagamento esteja entre a data inicial e final do período de deferimento,
	 * categoria Pública e que o CPF/CGC do proprietário ou inquilino estejam preenchidos (ordenados
	 * pelo código da regional, referência da conta, CNPJ / CPF e data de pagamento) <br>
	 * <br>
	 * AGRUPAR e SOMAR <br>
	 * <br>
	 * os valores das contas (valor total de conta), os valores pagos (valor total pago) por código
	 * da regional, referência da conta, CNPJ / CPF e data de pagamento
	 * </p>
	 * RASTREIO: [OC827869][UC3067][SB0004.1] e [SB0004.2]
	 * 
	 * @param referenciaBase
	 * @param dataInicialDeferimento
	 * @param dataFinalDeferimento
	 * @return
	 * @author Marlos Ribeiro
	 * @throws ControladorException
	 * @since 03/10/2012
	 */
	private List<LancamentoDeferimentoAnteriorHelper> consultarLancamentosAnterioresReferenciaBase(Integer referenciaBase,
					Date dataInicialDeferimento, Date dataFinalDeferimento) throws ControladorException{

		List<LancamentoDeferimentoAnteriorHelper> pagamentos;
		try{
			pagamentos = repositorioIntegracaoPiramide.consultarPagamentosParaDeferimento(referenciaBase, Categoria.PUBLICO,
							new Integer[] {ClienteRelacaoTipo.RESPONSAVEL, ClienteRelacaoTipo.USUARIO, ClienteRelacaoTipo.PROPRIETARIO},
							new Integer[] {0, 1, 2},
							dataInicialDeferimento, dataFinalDeferimento);
		}catch(ErroRepositorioException e){
			throw new ControladorException(e, "Erro ao consultar Anteriores ao periodo do deferimento");
		}
		if(pagamentos.isEmpty()){
			LOGGER.info("Não há dados de pagamento para integração de Deferimento no período informado");
		}
		return pagamentos;
	}

	/**
	 * Método gerarDeferimentosNoPeriodo
	 * <p>
	 * Esse método implementa a gravação da tabela TI_DIFER_PERIODO.
	 * </p>
	 * RASTREIO: [OC827869][UC3067][SB0002.1]
	 * 
	 * @param contas
	 * @author Marlos Ribeiro
	 * @param referenciaBase
	 *            no FORMATO AAAAMM
	 * @throws ControladorException
	 * @since 03/10/2012
	 */
	private void gerarDeferimentosNoPeriodo(List<LancamentoDeferimentoHelper> lancamentos, String referenciaBase)
					throws ControladorException{

		// [OC827869][UC3067][SB0003.2][ITEM 9]
		BigDecimal percentualAliquotaPIS = consultarPercentualAliquota(IMPOSTO_TIPO_ALIQUOTA_PIS);

		// [OC827869][UC3067][SB0003.2][ITEM 14]
		BigDecimal percentualAliquotaCOFINS = consultarPercentualAliquota(IMPOSTO_TIPO_ALIQUOTA_COFINS);

		// Acumulando os cliente iguais para uma mesma regional
		Integer ultimoCodReg = null;
		String ultimoCliDoc = null;
		TabelaIntegracaoDeferimentoReferenciaBase deferimento = null;
		BigDecimal valorNaoRecebido = null;

		for(LancamentoDeferimentoHelper lancamento : lancamentos){
			// Se for o primeiro item da lista, inicializa um item de deferimento
			if(ultimoCliDoc == null || ultimoCodReg == null){
				ultimoCliDoc = lancamento.getDocumentoCliente();
				ultimoCodReg = lancamento.getCodigoRegional();
				deferimento = new TabelaIntegracaoDeferimentoReferenciaBase(ultimoCodReg.toString(),
								Util.formatarAnoMesParaMesAnoSemBarra(Integer.valueOf(referenciaBase)), ultimoCliDoc,
								percentualAliquotaPIS, percentualAliquotaCOFINS);
			}

			/*
			 * Se o item corrente da lista de lancamento for semelhante (cliente / regional) ao
			 * anteior acumula os valores,
			 * Se não registra o deferimento e inicializa um novo deferimento com base no item
			 * corrente
			 */
			if(!(ultimoCliDoc.equals(lancamento.getDocumentoCliente()) && ultimoCodReg.equals(lancamento.getCodigoRegional()))){
				// [OC827869][UC3067][SB0003.2][ITEM 16]
				valorNaoRecebido = calcularValorNaoRecebido(ultimoCodReg, ultimoCliDoc, referenciaBase);
				if(!Util.isVazioOuBranco(valorNaoRecebido)){
					deferimento.setValorNaoRecebido(valorNaoRecebido);
				}else{
					deferimento.setValorNaoRecebido(BigDecimal.ZERO);
				}
				getControladorUtil().inserir(deferimento);

				LOGGER.debug("novo 'TI_DIFER_PERIODO' => " + deferimento);

				// RESETANDO agrupamento
				ultimoCliDoc = lancamento.getDocumentoCliente();
				ultimoCodReg = lancamento.getCodigoRegional();
				deferimento = new TabelaIntegracaoDeferimentoReferenciaBase(ultimoCodReg.toString(),
								Util.formatarAnoMesParaMesAnoSemBarra(Integer.valueOf(referenciaBase)), ultimoCliDoc,
								percentualAliquotaPIS, percentualAliquotaCOFINS);
			}

			deferimento.add(lancamento);

		}

		// Deferimento para o ultimo lancamento
		// [OC827869][UC3067][SB0003.2][ITEM 16]
		valorNaoRecebido = calcularValorNaoRecebido(ultimoCodReg, ultimoCliDoc, referenciaBase);
		if(!Util.isVazioOuBranco(valorNaoRecebido)){
			deferimento.setValorNaoRecebido(valorNaoRecebido);
		}else{
			deferimento.setValorNaoRecebido(BigDecimal.ZERO);
		}
		getControladorUtil().inserir(deferimento);

		LOGGER.debug("novo 'TI_DIFER_PERIODO' => " + deferimento);

	}

	private BigDecimal consultarPercentualAliquota(int imposto) throws ControladorException{

		BigDecimal percentualAliquotaPIS;
		try{
			percentualAliquotaPIS = repositorioIntegracaoPiramide.consultarPercentualAliquota(imposto);
		}catch(ErroRepositorioException e){
			throw new ControladorException(e, "Erro ao consultar percentual aliquota do tipo[" + imposto + "]");
		}
		return percentualAliquotaPIS;
	}

	/**
	 * Método calcularValorNaoRecebido
	 * <p>
	 * Esse método implementa a consulta/calculo do valor nao recebido
	 * </p>
	 * RASTREIO: [OC827869][UC3067][SB0003.2][ITEM 16]
	 * 
	 * @param ultimoCodReg
	 * @param ultimoCliDoc
	 * @return
	 * @throws ControladorException
	 * @author Marlos Ribeiro
	 * @since 08/10/2012
	 * @author Josenildo Neves
	 * @since 19/10/2012
	 */
	private BigDecimal calcularValorNaoRecebido(Integer ultimoCodReg, String ultimoCliDoc, String referenciaBase)
					throws ControladorException{

		BigDecimal valorNaoRecebido;
		try{
			valorNaoRecebido = repositorioIntegracaoPiramide.consultaValorNaoRecebido(ultimoCodReg, ultimoCliDoc, referenciaBase,
							new Integer[] {ClienteRelacaoTipo.RESPONSAVEL, ClienteRelacaoTipo.PROPRIETARIO, ClienteRelacaoTipo.USUARIO});
		}catch(ErroRepositorioException e){
			throw new ControladorException(e, "Erro ao consultar valor não recebido para Gerencia:'" + ultimoCodReg
							+ "' e Cliente Documento:'" + ultimoCliDoc + "'");
		}
		return valorNaoRecebido;
	}

	/**
	 * Método consultarContas
	 * <p>
	 * Esse método implementa consulta de : <br>
	 * contas com referência igual a referência base, categoria publica e que o CPF/CGC do
	 * proprietário ou inquilino estejam preenchidos a partir da tabela CONTA com
	 * CNTA_AMREFERENCIACONTA igual referencia base; só exista apenas uma ocorrência na tabela
	 * CONTA_CATEGORIA com CNTA_ID igual a CNTA_ID da tabela CONTA que esta única ocorrência seja
	 * com CATG_ID = 4 <br>
	 * <br>
	 * UNIAO com <br>
	 * <br>
	 * contas em histórico com referência igual a referência base, categoria publica e que o CPF/CGC
	 * do proprietário ou inquilino estejam preenchidos a partir da tabela CONTA-HISTORICO com
	 * CNHI_AMREFERENCIACONTA igual referencia base; sejam de situação Normal, Incluída ou
	 * Retificadas (DCST_IDATUAL igual a 0, 1 ou 2); só exista apenas uma ocorrência na tabela
	 * CONTA_CATEGORIA_HISTORICO com CNTA_ID igual a CNTA_ID da tabela CONTA_HISTORICO que esta
	 * única ocorrência seja com CATG_ID = 4 e que tenham sido pagas depois da data final de
	 * deferimento (PGHI_DTPAGAMENTO > data final deferimento a partir da tabela PAGAMENTO_HISTORICO
	 * com CNTA_ID = CNTA_ID da tabela CONTA_HISTORICO). <br>
	 * <br>
	 * AGRUPAR POR código de regional e CNPJ/CPF
	 * </p>
	 * RASTREIO: [OC827869][UC3067][SB0003.1.1], [SB0003.1.2] e [SB0003.2]
	 * 
	 * @param referenciaBase
	 * @return
	 * @author Marlos Ribeiro
	 * @param dataFinalDeferimento
	 * @throws ControladorException
	 * @since 03/10/2012
	 */
	private List<LancamentoDeferimentoHelper> consultarLancamentosNaReferenciaBase(String referenciaBase, Date dataFinalDeferimento)
					throws ControladorException{

		List<LancamentoDeferimentoHelper> lancamentos;
		try{

			lancamentos = repositorioIntegracaoPiramide.consultarContasDeferimento(referenciaBase, Categoria.PUBLICO,
							new Integer[] {ClienteRelacaoTipo.RESPONSAVEL, ClienteRelacaoTipo.USUARIO, ClienteRelacaoTipo.PROPRIETARIO},
							dataFinalDeferimento,
							new Integer[] {0, 1, 2});
		}catch(ErroRepositorioException e){
			throw new ControladorException(e, "Erro ao consultar contas para deferimento no periodo");
		}
		return lancamentos;
	}

	private boolean verificarReferenciaBaseDeferimento(String referenciaBase) throws ControladorException{

		/*
		 * [OC827869][UC3067][FS0003]: Caso a referência base for maior ou igual a referência de
		 * faturamento.
		 */
		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
		String referenciaFaturamento = sistemaParametro.getAnoMesFaturamento().toString();
		boolean abortarProcessamento = false;
		if(referenciaBase.compareTo(referenciaFaturamento) >= 0){
			LOGGER.debug("Referência do Deferimento: " + referenciaBase);
			LOGGER.debug("Referência do Faturamento: " + referenciaFaturamento);
			LOGGER.info("Última Referência do Deferimento não é menor que a Referência do Faturamento");
			abortarProcessamento = true;
		}

		/*
		 * [OC827869][UC3067][FS0004]: Caso existam lançamentos na tabela TI_DIF_ANT_REC_PER
		 * para a referência base
		 */
		if(isReferenciaDeferimentoProcessada(referenciaBase)){
			LOGGER.debug("Referência do Deferimento: " + referenciaBase);
			LOGGER.info("Referência do Deferimento já foi processada para recebimento");
			abortarProcessamento = true;
		}
		return abortarProcessamento;
	}

	/**
	 * Método isReferenciaDeferimentoProcessada
	 * <p>
	 * Esse método implementa consulta que verifica a existencia de lançamentos na tabela
	 * TI_DIF_ANT_REC_PER para a referência base
	 * </p>
	 * RASTREIO: [OC827869][UC3067][FS0004]
	 * 
	 * @param formatarAnoMesParaMesAno
	 * @return <code>true</code> caso exista.
	 * @author Marlos Ribeiro
	 * @throws ControladorException
	 * @since 03/10/2012
	 */
	private boolean isReferenciaDeferimentoProcessada(String referenciaBase) throws ControladorException{

		Integer qtdLancamentos = null;
		try{
			qtdLancamentos = repositorioIntegracaoPiramide.quantaidadeLancamentosDeferimento(Util.formatarAnoMesParaMesAno(Integer
							.valueOf(referenciaBase)));
		}catch(NumberFormatException e){
			throw new ControladorException(e, "'" + referenciaBase + "' não é um Inteiro válido");
		}catch(ErroRepositorioException e){
			throw new ControladorException(e,
							"Erro ao realizar consulta de quantidade de lancamentos já gerados em TI_DIF_ANT_REC_PER para a referencia '"
											+ referenciaBase + "'");
		}
		return !Integer.valueOf(0).equals(qtdLancamentos);
	}

	/**
	 * Método obterReferenciaBase
	 * <p>
	 * Esse método implementa a busca da maior referência já gerada a partir da tabela
	 * TI_DIFER_PERIODO. Converter a referência obtida do formato MMAAAA para AAAAMM e Adicionar um
	 * mês a referência obtida para gerar a referência base de integração de deferimento
	 * </p>
	 * RASTREIO: [OC827869][UC3067][SB0001]
	 * 
	 * @return referencia no formato: AAAAMM
	 * @author Marlos Ribeiro
	 * @throws ControladorException
	 * @since 03/10/2012
	 */
	private String obterReferenciaBase() throws ControladorException{

		String referenciaBase = null;
		try{
			String maiorReferenciaGerada = repositorioIntegracaoPiramide.obterMaiorReferenciaGerada();
			// [OC827869][UC3067][FS0002]: Caso não exista nenhum lançamento na tabela
			// TI_DIFER_PERIODO, então atribuir ‘082012’ para a referência
			if(Util.isVazioOuBranco(maiorReferenciaGerada)){
				maiorReferenciaGerada = "201208";
			}
			referenciaBase = Util.somaUmMesAnoMesReferencia(Integer.valueOf(maiorReferenciaGerada)).toString();
		}catch(ErroRepositorioException e){
			throw new ControladorException(e, "Erro ao realizar consulta de maior referência já gerada em TI_DIFER_PERIODO");
		}
		return referenciaBase;
	}

	/**
	 * {@inheritDoc}
	 */
	public void gerarIntegracaoSpedPisCofins(Integer idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.FUNCIONALIDADE, idFuncionalidadeIniciada);
		
		boolean temErro = false;
		try{
			TI_CONSL_DIA_NFCL = new ArrayList<TabelaIntegracaoConslDiaNfcl>();
			TI_CM_CON_DIA_NFCL = new ArrayList<TabelaIntegracaoCmConDiaNfcl>();
			TI_CONS_CAN_NTFISC = new ArrayList<TabelaIntegracaoConsCanNtfisc>();
			Integer referenciaBase = Integer.valueOf(ParametroContabil.P_REFERENCIA_INTEGRACAO_SPED_PIS_COFINS.executar());
			// [SB0001] - Obter data inicial e final do período para a integração de Sped
			Date dtInicio = Util.converterAnoMesParaDataInicial(referenciaBase.toString());
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(dtInicio);
			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
			Date dtFim = calendar.getTime();
			dtFim = Util.ajustarHoraMinutoSegundo(dtFim, 23, 59, 59, 999);
			LOGGER.info("PERIODO: " + Util.formatarData(dtInicio) + " a " + Util.formatarData(dtFim));

			// [SB0002] - Processar a integração do Sped a partir do período informado
			Integer ultimoSeqConslDiaNfcl = repositorioIntegracaoPiramide.consultarUltimoSequencialConslDiaNfcl();
			LOGGER.info(">> ultimoSeqConslDiaNfcl >> " + ultimoSeqConslDiaNfcl);

			Collection<Integer> idsMunicipios = ServiceLocator.getInstancia().getControladorGeografico().pesquisarTodosIdsMunicipios();

			DEBUG_TOTALIZADOR = new DebugTotalizador();
			for(Integer idMunicipio : idsMunicipios){

				Collection<Object[]> modulosPorDia = repositorioIntegracaoPiramide.consultarModulosPorDiaPorMunicipio(dtInicio, dtFim,
								idMunicipio);

				if(modulosPorDia.isEmpty()){
					// FS0001 - Verificar existência de lançamento para a integração de Sped
					LOGGER.warn("Não há dados de conta para integração de Sped no período e localidade informado. idLocalidade["
									+ idMunicipio + "] - Período [" + Util.formatarData(dtInicio) + "] à [" + Util.formatarData(dtFim)
									+ "]");
				}else{
					LOGGER.info("### MUNICIPIO[" + idMunicipio + "] - INICIO da Integracao Sped Pis/Cofins");
					processarModulosDiaParaLocalidade(referenciaBase, dtInicio, ultimoSeqConslDiaNfcl, idMunicipio, modulosPorDia);
					LOGGER.info("### MUNICIPIO[" + idMunicipio + "] - FIM da Integracao Sped Pis/Cofins");
				}


			}

			LOGGER.info("COMMITANDO TABELAS DE INTEGRACAO ------");
			getControladorUtil().inserirBatch(TI_CONSL_DIA_NFCL);
			getControladorUtil().inserirBatch(TI_CM_CON_DIA_NFCL);
			getControladorUtil().inserirBatch(TI_CONS_CAN_NTFISC);
			LOGGER.info("FIM OPERACAO DE COMMIT ----------------");
			LOGGER.info("<<<<<<<<<<<<<<<<<<<<<<<<DEBUG>>>>>>>>>>>>>>>>>>>>>>> \n      " + referenciaBase + "      \n"
							+ DEBUG_TOTALIZADOR.toString()
							+ " \n <<<<<<<<<<<<<<<<<<<<<<<<DEBUG>>>>>>>>>>>>>>>>>>>>>>>");
		}catch(Exception e){
			temErro = true;
			LOGGER.error("Falha na Integracao Sped Pis/Cofins", e);
		}finally{
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, temErro);
		}
	}

	/**
	 * @param referenciaBase
	 * @param dtInicio
	 * @param proximoSequancial
	 * @param idMunicipio
	 * @param modulosPorDia
	 * @return
	 * @throws ControladorException
	 */
	private void processarModulosDiaParaLocalidade(Integer referenciaBase, Date dtInicio, Integer ultimoSeqConslDiaNfcl, Integer idMunicipio,
					Collection<Object[]> modulosPorDia) throws ControladorException{

		Date dataContabilCorrente = (Date) ((Object[]) Util.retonarObjetoDeColecaoArray(modulosPorDia))[0];
		Date dtContabil;
		Integer idMuni;
		Integer modulo;
		// BigDecimal base_100 = BigDecimal.valueOf(100);
		BigDecimal vlAliquotaPis = consultarPercentualAliquota(IMPOSTO_TIPO_ALIQUOTA_PIS);
		BigDecimal vlAliquotaCofins = consultarPercentualAliquota(IMPOSTO_TIPO_ALIQUOTA_COFINS);

		TabelaIntegracaoConslDiaNfclHelper helper = new TabelaIntegracaoConslDiaNfclHelper(ultimoSeqConslDiaNfcl, referenciaBase,
						idMunicipio, dataContabilCorrente, getCodigoFilialOrigem(idMunicipio));
		for(Object[] objects : modulosPorDia){
			dtContabil = (Date) objects[0];
			idMuni = (Integer) objects[1];
			modulo = (Integer) objects[2];
			if(!dataContabilCorrente.equals(objects[0])){
				inserirConslDiaNfcl(helper, vlAliquotaPis, vlAliquotaCofins);
				dataContabilCorrente = dtContabil;
				helper = new TabelaIntegracaoConslDiaNfclHelper(referenciaBase, idMuni, dataContabilCorrente, getCodigoFilialOrigem(idMuni));
			}
			LOGGER.info("MUNICIPIO[" + idMuni + "] / DIA [" + Util.formatarData(dtContabil) + "] / MODULO [" + modulo + "]");
			acumularValoresConslDiaNfcl(helper, modulo, vlAliquotaCofins, vlAliquotaPis, dtInicio);
		}
		inserirConslDiaNfcl(helper, vlAliquotaPis, vlAliquotaCofins);
	}

	private String getCodigoFilialOrigem(Integer idMunicipio) throws ControladorException{

		String codigoFilialOrigem = idMunicipio.toString();
		if(Integer.valueOf(0).equals(idMunicipio)){
			codigoFilialOrigem = "1";
		}else{
			Integer idGerenciaParaLocalidade = ServiceLocator.getInstancia().getControladorLocalidade()
							.pesquisarIdGerenciaParaLocalidade(idMunicipio);
			codigoFilialOrigem = Util.isVazioOuBrancoOuZero(idGerenciaParaLocalidade) ? "1" : idGerenciaParaLocalidade.toString();
		}
		return codigoFilialOrigem;
	}

	private void acumularValoresConslDiaNfcl(TabelaIntegracaoConslDiaNfclHelper helper, Integer modulo, BigDecimal vlAliquotaCofins,
					BigDecimal vlAliquotaPis, Date dtVigencia) throws ControladorException{
		switch(modulo){
			case 1: // TabelaIntegracaoConslDiaNfclHelper.MODULO_FATURAMENTO:
				acumularValores(helper, dtVigencia, TabelaIntegracaoConslDiaNfclHelper.MODULO_FATURAMENTO, EVENTOS_FATURAMENTO);
				break;

			case 3: // TabelaIntegracaoConslDiaNfclHelper.MODULO_PARCELAMENTO:
				acumularValores(helper, dtVigencia, TabelaIntegracaoConslDiaNfclHelper.MODULO_PARCELAMENTO, new Integer[] {46, 72});
				break;

			case 4: // TabelaIntegracaoConslDiaNfclHelper.MODULO_FINANCIAMENTO:
				acumularValores(helper, dtVigencia, TabelaIntegracaoConslDiaNfclHelper.MODULO_FINANCIAMENTO, new Integer[] {13, 14});
				break;

			case 5: // TabelaIntegracaoConslDiaNfclHelper.MODULO_CANCELAMENTO:
				// [OC777360][UC3068][SB0007]
				acumularValoresCancelamentoContas(helper, dtVigencia, vlAliquotaPis, vlAliquotaCofins, new Integer[] {7, 8, 9, 11});

				// [OC777360][UC3068][SB0010]
				acumularCancelamentoSaldo(helper, dtVigencia, new Integer[] {15, 16, 70, 71});

				// [OC1145913][UC3068][SB0012]
				acumularCancelamentoGuiaPagamento(helper, dtVigencia, vlAliquotaPis, vlAliquotaCofins, new Integer[] {30});

				// [OC1145913][UC3068][SB0013]
				acumularCancelamentoJurosCorrecoesParcelamento(helper, dtVigencia, new Integer[] {56, 73});
				break;

			case 6: // TabelaIntegracaoConslDiaNfclHelper.MODULO_GUIA_PAGAMENTO:
				acumularValores(helper, dtVigencia, TabelaIntegracaoConslDiaNfclHelper.MODULO_GUIA_PAGAMENTO, EVENTOS_INCLUSAO_GUIA_PGTO);
				break;

			default:
				LOGGER.info("MODULO[" + modulo + "] não entra para a integracao Sped Pis/Cofins.");
				break;
		}

	}


	private void acumularCancelamentoJurosCorrecoesParcelamento(TabelaIntegracaoConslDiaNfclHelper helper, Date dtVigencia,
					Integer[] idsEventosContabil) throws ControladorException{

		try{
			Collection<Object[]> lancamentos = repositorioIntegracaoPiramide.consultarLancamentosContabeis(helper.getDataContabil(),
							helper.getIdMunicipio(), false, 0, idsEventosContabil);
			LOGGER.info("	QTD JUROS CORRECOES DE PARCELAMENTO para processar: [" + lancamentos.size() + "]");

			Integer idCategoria;
			BigDecimal vlLancamento;
			Integer codigoConsumoSped;

			for(Object[] objects : lancamentos){
				vlLancamento = (BigDecimal) objects[1];
				idCategoria = (Integer) objects[2];
				if(Integer.valueOf(3).equals(idCategoria)) idCategoria = 2;
				codigoConsumoSped = repositorioIntegracaoPiramide.consultarClassificacaoLancamento(dtVigencia, idCategoria, vlLancamento);

				helper.acumularCancelamentoDocumentoJuros(codigoConsumoSped, vlLancamento);
				DEBUG_TOTALIZADOR.add(DebugTotalizador.CAN_PARC_JUROS, 1, vlLancamento);
			}

	}catch(ErroRepositorioException e){
			throw new ControladorException(e, "Falha ao consultar Parcelamentos.");
		}

	}

	private void acumularCancelamentoGuiaPagamento(TabelaIntegracaoConslDiaNfclHelper helper, Date dtVigencia, BigDecimal vlAliquotaPis,
					BigDecimal vlAliquotaCofins, Integer[] idsEventosContabil)
					throws ControladorException{
		try{
			Collection<Object[]> lancamentos = repositorioIntegracaoPiramide.consultarLancamentosContabeis(helper.getDataContabil(),
							helper.getIdMunicipio(), true, 0, idsEventosContabil);
			LOGGER.info("	QTD GUIAS DE PAGAMENTO para processar: [" + lancamentos.size() + "]");

			Integer idObjetoContabil;
			Integer idCategoria;
			Integer idItemContabil;
			BigDecimal vlLancamento;
			Integer codigoConsumoSped;

			for(Object[] objects : lancamentos){
				idObjetoContabil = (Integer) objects[0];
				vlLancamento = (BigDecimal) objects[1];
				idCategoria = (Integer) objects[2];
				idItemContabil = (Integer) objects[3];
				if(Integer.valueOf(3).equals(idCategoria)) idCategoria = 2;

				if(Arrays.asList(2, 5).contains(idItemContabil)){
					codigoConsumoSped = repositorioIntegracaoPiramide.consultarClassificacaoLancamento(dtVigencia, idCategoria,
									vlLancamento);
					helper.acumularCancelamentoDocumentoJuros(codigoConsumoSped, vlLancamento);
					DEBUG_TOTALIZADOR.add(DebugTotalizador.CAN_GPGTO_JUROS, 1, vlLancamento);
				}else{
					Object[] dataValorCategoriaCancelamento = repositorioIntegracaoPiramide.consultarDataValorCategoriaOrigemCancelamento(
									helper.getDataContabil(), helper.getIdMunicipio(), idObjetoContabil, idItemContabil, idCategoria,
									EVENTOS_INCLUSAO_GUIA_PGTO);
					if(dataValorCategoriaCancelamento == null || dataValorCategoriaCancelamento[1] == null){
						LOGGER.warn(">>>GUIA PAGAMENTO ORIGEM NÃO ENCONTRADO [ACC MES ANT]: MUNICIPIO[" + helper.getIdMunicipio()
										+ "] ID_OBJ_CONTABIL[" + idObjetoContabil + "] DT_CONTABIL["
										+ Util.formatarData(helper.getDataContabil()) + "] VL_LANCAMENTO[" + vlLancamento + "] EVENTOS"
										+ Arrays.toString(EVENTOS_FATURAMENTO));
						acumularCancelamentoContaAnterior(helper, helper.getDataContabil(), vlLancamento);
						DEBUG_TOTALIZADOR.add(DebugTotalizador.CAN_GPGTO_ANT, 1, vlLancamento);
					}else{
						codigoConsumoSped = repositorioIntegracaoPiramide.consultarClassificacaoLancamento(dtVigencia,
										(Integer) dataValorCategoriaCancelamento[2], (BigDecimal) dataValorCategoriaCancelamento[1]);

						acumularCancelamentoLancamentoMes(helper, (Date) dataValorCategoriaCancelamento[0], codigoConsumoSped,
										(BigDecimal) dataValorCategoriaCancelamento[1], vlAliquotaPis, vlAliquotaCofins);
						DEBUG_TOTALIZADOR.add(DebugTotalizador.CAN_GPGTO_NJUROS_NM, 1, (BigDecimal) dataValorCategoriaCancelamento[1]);
					}
				}
			}

		}catch(ErroRepositorioException e){
			throw new ControladorException(e, "Falha ao consultar Guias de Pagamento.");
		}
	}

	/**
	 * Método acumularCancelamentoSaldo
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OC777360][UC3068][SB0010]
	 * 
	 * @author Marlos Ribeiro
	 * @param helper
	 * @param idsEventosContabil
	 * @param dtVigencia
	 * @throws ControladorException
	 * @since 22/03/2013
	 */
	private void acumularCancelamentoSaldo(TabelaIntegracaoConslDiaNfclHelper helper, Date dtVigencia,Integer[] idsEventosContabil) throws ControladorException{

		try{
			Collection<Object[]> lancamentosParaCancelarSaldo = repositorioIntegracaoPiramide.consultarLancamentosContabeis(
							helper.getDataContabil(), helper.getIdMunicipio(), false, 0, idsEventosContabil);
			Integer idCategoria;
			BigDecimal vlConta;
			LOGGER.info("	QTD Lancamentos para CANCELAR SALDO: [" + lancamentosParaCancelarSaldo.size() + "]");

			for(Object[] objects : lancamentosParaCancelarSaldo){
				vlConta = (BigDecimal) objects[1];
				idCategoria = (Integer) objects[2];
				
				if(Integer.valueOf(3).equals(idCategoria)) idCategoria = 2;

				TabelaIntegracaoConsCanNtfisc tiConsCanNTFISC = obterTIConsCanNtFisc(helper.getCodigoFilialOrigem(),
								helper.getDataContabil(), helper);
				tiConsCanNTFISC.addValorCancelamento(vlConta);
				DEBUG_TOTALIZADOR.add(DebugTotalizador.CAN_SALDO, 1, vlConta);
			}
		}catch(ErroRepositorioException e){
			throw new ControladorException(e,"Falha ao acumular Cancelamento Saldo");
		}
		
	}

	/**
	 * Método acumularValoresCancelamentoContas
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OC777360][UC3068][SB0007]
	 * 
	 * @author Marlos Ribeiro
	 * @param vlAliquotaCofins
	 * @param vlAliquotaPis
	 * @param idsEventosContabil
	 * @throws ControladorException
	 * @since 21/03/2013
	 */
	private void acumularValoresCancelamentoContas(TabelaIntegracaoConslDiaNfclHelper helper, Date dtVigencia, BigDecimal vlAliquotaPis,
					BigDecimal vlAliquotaCofins, Integer[] idsEventosContabil)
					throws ControladorException{

		try{
			Integer idObjetoContabil;
			Integer referenciaConta;
			Integer codigoConsumoSped;
			Integer idCategoria;
			BigDecimal vlLancamento;
			Date dtGeracao;
			Date dtCancelamento;
			Collection<Object[]> lancamentosParaCancelar = repositorioIntegracaoPiramide.consultarLancamentosContas(
							helper.getIdMunicipio(), helper.getDataContabil(), idsEventosContabil);
			LOGGER.info("	QTD CONTAS para CANCELAR: [" + lancamentosParaCancelar.size() + "]");

			for(Object[] dadosConta : lancamentosParaCancelar){
				idObjetoContabil = (Integer) dadosConta[0];
				referenciaConta = (Integer) dadosConta[1];
				dtGeracao = (Date) dadosConta[2];
				dtCancelamento = (Date) dadosConta[3];
				vlLancamento = (BigDecimal) dadosConta[4];
				idCategoria = (Integer) dadosConta[5];
				if(Integer.valueOf(3).equals(idCategoria)) idCategoria = 2;

				if(helper.getReferenciaBase().compareTo(referenciaConta) > 0){
					// [SB0008] - Acumular Cancelamento Conta Anterior
					acumularCancelamentoContaAnterior(helper, dtCancelamento, vlLancamento);
					DEBUG_TOTALIZADOR.add(DebugTotalizador.CAN_CONTA_ANT, 1, vlLancamento);
				}else{
					// [SB0009] - Acumular Cancelamento Conta Mês
					Object[] dataValorCategoriaCancelamento = repositorioIntegracaoPiramide.consultarDataValorCategoriaOrigemCancelamento(dtGeracao,
									helper.getIdMunicipio(), idObjetoContabil,null,null, EVENTOS_FATURAMENTO);
					if(dataValorCategoriaCancelamento == null || dataValorCategoriaCancelamento[1] == null){
						LOGGER.warn(">>>CONTA ORIGEM NÃO ENCONTRADA [ACC MES ANT]: MUNICIPIO[" + helper.getIdMunicipio()
										+ "] ID_OBJ_CONTABIL[" + idObjetoContabil + "] DT_CONTABIL[" + Util.formatarData(dtGeracao)
										+ "] VL_LANCAMENTO[" + vlLancamento + "] EVENTOS" + Arrays.toString(EVENTOS_FATURAMENTO));
						acumularCancelamentoContaAnterior(helper, dtCancelamento, vlLancamento);
						DEBUG_TOTALIZADOR.add(DebugTotalizador.CAN_CONTA_ANT, 1, vlLancamento);
					}else{
						codigoConsumoSped = repositorioIntegracaoPiramide.consultarClassificacaoLancamento(dtVigencia,
										(Integer) dataValorCategoriaCancelamento[2],
										(BigDecimal) dataValorCategoriaCancelamento[1]);

						acumularCancelamentoLancamentoMes(helper, (Date) dataValorCategoriaCancelamento[0], codigoConsumoSped,
										(BigDecimal) dataValorCategoriaCancelamento[1], vlAliquotaPis, vlAliquotaCofins);
						DEBUG_TOTALIZADOR.add(DebugTotalizador.CAN_CONTA_NM, 1, (BigDecimal) dataValorCategoriaCancelamento[1]);
					}
				}
			}
		}catch(ErroRepositorioException e){
			throw new ControladorException(e, "Falha ao consultar Lancamentos Contas.");
		}

	}

	/**
	 * Método acumularCancelamentoContaMes
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OC777360][UC3068][SB0009]
	 * 
	 * @author Marlos Ribeiro
	 * @param codigoConsumoSped
	 * @param dtEmissao
	 * @param helper
	 * @param vlTotalCancelamento
	 * @param vlAliquotaCofins
	 * @param vlAliquotaPis
	 * @param isGuiaPagamento
	 * @throws ControladorException
	 * @since 22/03/2013
	 */
	private void acumularCancelamentoLancamentoMes(TabelaIntegracaoConslDiaNfclHelper helper, Date dtEmissao, Integer codigoConsumoSped,
					BigDecimal vlTotalCancelamento, BigDecimal vlAliquotaPis, BigDecimal vlAliquotaCofins)
					throws ControladorException{

		TabelaIntegracaoConslDiaNfcl tiConslDiaNFCL = null;
		try{
			/*
			 * [SB0009] - Tenta buscar uma registro de TabelaIntegracaoConslDiaNfcl do BANCO
			 */
			String codFilialOrigFormatado = helper.getCodigoFilialOrigem();
			String codConsumoSpedFormatado = Util.completarStringZeroEsquerda(codigoConsumoSped.toString(), 2);

			TabelaIntegracaoConslDiaNfcl tiConslDiaNFCLModelo = new TabelaIntegracaoConslDiaNfcl();
			tiConslDiaNFCLModelo.setCodigoFilialOrigem(codFilialOrigFormatado);
			tiConslDiaNFCLModelo.setCodigoSistemaOrigem(TabelaIntegracaoConslDiaNfclHelper.COD_SIS_ORIGEM);
			tiConslDiaNFCLModelo.setDataDocumentosConsolidado(dtEmissao);
			tiConslDiaNFCLModelo.setCodigoClasseConsumo(codConsumoSpedFormatado);
			tiConslDiaNFCLModelo.setCodigoMunicipioOrigem(Util.completarStringZeroEsquerda(helper.getIdMunicipio().toString(), 10));
			tiConslDiaNFCLModelo.setCodigoOperacaoRegistro("I");
			Collection resultado = getControladorUtil().pesquisar(tiConslDiaNFCLModelo);

			boolean isDaMemoria = false;
			/*
			 * if (Consulta não retorna registro) Buscar da memoria do processamento corrente;
			 * else recupera da consulta;
			 */
			if(Util.isVazioOrNulo(resultado)){
				tiConslDiaNFCL = recuperarDaMemoria(codFilialOrigFormatado, TabelaIntegracaoConslDiaNfclHelper.COD_SIS_ORIGEM, dtEmissao,
								codConsumoSpedFormatado, Util.completarStringZeroEsquerda(helper.getIdMunicipio().toString(), 10));
				if(tiConslDiaNFCL == null && !helper.getDataContabil().equals(dtEmissao)){
					LOGGER.error(">>> TI_CONSL_DIA_NFCL não encontrado com: COD_FILIAL_ORG[" + codFilialOrigFormatado + "], COD_SIS_ORIG["
									+ TabelaIntegracaoConslDiaNfclHelper.COD_SIS_ORIGEM + "], DT_EMISSAO[" + Util.formatarData(dtEmissao)
									+ "], MUNICIPIO[" + Util.completarStringZeroEsquerda(helper.getIdMunicipio().toString(), 10)
									+ "], COD_CONSUMO_SPED[" + codConsumoSpedFormatado + "]");
				}
				isDaMemoria = true;
			}else{
				tiConslDiaNFCL = (TabelaIntegracaoConslDiaNfcl) Util.retonarObjetoDeColecao(resultado);
			}

			/*
			 * if (tiConslDiaNFCL == null) [SB0009] 3;
			 * else [SB0009] 2;
			 */
			if(tiConslDiaNFCL == null){
				helper.acumularCancelamentoContaMes(codigoConsumoSped, vlTotalCancelamento);
			}else{
				acumularCancelamentoLancamentoMesParaConslDiaNFCLExistente(helper, codigoConsumoSped, vlTotalCancelamento, vlAliquotaPis,
								vlAliquotaCofins, tiConslDiaNFCL, isDaMemoria);
			}

		}catch(ErroRepositorioException e){
			throw new ControladorException(e, "Falha ao acumular Cancelamento Conta Mes.");
		}
	}

	private TabelaIntegracaoConslDiaNfcl recuperarDaMemoria(String codigoFilialOrigem, String codSistemaOrigem, Date dtEmissao,
					String codigoCosumoSped, String codigoMunicipioOrigem){

		TabelaIntegracaoConslDiaNfcl retorno = null;
		for(TabelaIntegracaoConslDiaNfcl ti : TI_CONSL_DIA_NFCL){
			if(codigoFilialOrigem.equals(ti.getCodigoFilialOrigem()) //
							&& codSistemaOrigem.equals(ti.getCodigoSistemaOrigem()) //
							&& dtEmissao.equals(ti.getDataDocumentosConsolidado()) //
							&& codigoCosumoSped.equals(ti.getCodigoClasseConsumo()) //
							&& codigoMunicipioOrigem.equals(ti.getCodigoMunicipioOrigem())//
							&& "I".equals(ti.getCodigoOperacaoRegistro())){
				retorno = ti;
				break;
			}
		}
		return retorno;
	}

	private void acumularCancelamentoLancamentoMesParaConslDiaNFCLExistente(TabelaIntegracaoConslDiaNfclHelper helper,
					Integer codigoConsumoSped, BigDecimal vlTotalCancelamento, BigDecimal vlAliquotaPis, BigDecimal vlAliquotaCofins,
					TabelaIntegracaoConslDiaNfcl tiConslDiaNFCL, boolean isDaMemoria)
					throws ErroRepositorioException, ControladorException{

		Integer codigoSequenciaOrigem = tiConslDiaNFCL.getCodigoSequenciaOrigem();
		BigDecimal valItem = BigDecimal.ZERO;
		BigDecimal valBasePis = BigDecimal.ZERO;
		BigDecimal valPis = BigDecimal.ZERO;
		BigDecimal valBaseCofins = BigDecimal.ZERO;
		BigDecimal valCofins = BigDecimal.ZERO;

		// [SB0009] 2.1. - Caso valor total de cancelamento da conta seja maior que
		// VAL_FORNECIMENTO
		if(vlTotalCancelamento.compareTo(tiConslDiaNFCL.getValorAcumuladoFornecimento()) > 0){
			LOGGER.error("VALOR CANCELAMENTO > tiConslDiaNFCL.getValorAcumuladoFornecimento");
			// [SB0009] 2.1.3 - Acumula a diferenca
			helper.acumularCancelamentoContaMes(codigoConsumoSped,
							vlTotalCancelamento.subtract(tiConslDiaNFCL.getValorAcumuladoFornecimento()));

			// [SB0009] 2.1.1. - Zera o fornecimento
			tiConslDiaNFCL.setValorAcumuladoFornecimento(BigDecimal.ZERO);
			tiConslDiaNFCL.setValorAcumuladoPisPasep(BigDecimal.ZERO);
			tiConslDiaNFCL.setValorAcumuladoCofins(BigDecimal.ZERO);
			valItem = BigDecimal.ZERO;
			valBasePis = BigDecimal.ZERO;
			valBaseCofins = BigDecimal.ZERO;
			valPis = BigDecimal.ZERO;
			valCofins = BigDecimal.ZERO;
		}else{
			// [SB0009] 2.2.1.
			BigDecimal valFornecimento = tiConslDiaNFCL.getValorAcumuladoFornecimento().subtract(
							vlTotalCancelamento);
			tiConslDiaNFCL.setValorAcumuladoFornecimento(valFornecimento);
			// [SB0009] 2.2.2.
			tiConslDiaNFCL.setValorAcumuladoPisPasep(tiConslDiaNFCL.getValorAcumuladoFornecimento().multiply(vlAliquotaPis));
			tiConslDiaNFCL.setValorAcumuladoCofins(tiConslDiaNFCL.getValorAcumuladoFornecimento().multiply(vlAliquotaCofins));
			tiConslDiaNFCL.setQtdDocumentosCancelados(tiConslDiaNFCL.getQtdDocumentosCancelados() + 1);

			/*
			 * [SB0009] 2.2.3. O sistema recupera a coluna VAL_ITEM na tabela TI_CM_CON_DIA_NFCL
			 * Se não tiver no banco, busca da memoria.
			 */
			valItem = repositorioIntegracaoPiramide.recuperarValorItemTICmConDiaNFCL(codigoSequenciaOrigem, "01");
			if(valItem == null) valItem = recuperarValorItemTICmConDiaNFCLDaMemoria(codigoSequenciaOrigem, "01");

			if(valItem != null){
				valItem = valItem.subtract(vlTotalCancelamento);
				valBasePis = valItem;
				valBaseCofins = valItem;
				valPis = valItem.multiply(vlAliquotaPis);
				valCofins = valItem.multiply(vlAliquotaCofins);
			}
		}

		/*
		 * [SB0009] 2.1.1. e 2.2.2. -> SE o tiConslDiaNFCL vem da memoria ele ainda será salvo no
		 * final do processo. e não precisa ser atualizado neste momento.
		 */
		if(!isDaMemoria) getControladorUtil().atualizar(tiConslDiaNFCL);

		// [SB0009] 2.1.2. e 2.2.3.2. -> o mesmo com o TabelaIntegracaoCmConDiaNfcl
		if(valItem != null){
			/*
			 * SE esta na memoria não precisa comitar as alteracoes agora. no final do processo tudo
			 * sera salvo.
			 */
			if(isDaMemoria){
				for(TabelaIntegracaoCmConDiaNfcl ti : TI_CM_CON_DIA_NFCL){
					if(ti.getComp_id().getCodigoSequencialOrigem().equals(codigoSequenciaOrigem)
									&& ti.getCodigoSituacaoTributariaPis().equals("01")){
						ti.setValorItem(valItem);
						ti.setValorBasePis(valBasePis);
						ti.setValorBaseConfins(valBaseCofins);
						ti.setValorConfins(valCofins);
						ti.setValorPis(valPis);
						break;
					}
				}
			}else{
				repositorioIntegracaoPiramide.atualizarValoresCmDiaNfcl(codigoSequenciaOrigem, "01", valItem, valBasePis, valPis,
								valBaseCofins, valCofins);
			}
		}
	}

	private BigDecimal recuperarValorItemTICmConDiaNFCLDaMemoria(Integer codigoSequenciaOrigem, String codigoSituacaoPis){

		for(TabelaIntegracaoCmConDiaNfcl ti : TI_CM_CON_DIA_NFCL){
			if(ti.getComp_id().getCodigoSequencialOrigem().equals(codigoSequenciaOrigem)
							&& ti.getCodigoSituacaoTributariaPis().equals(codigoSituacaoPis)){
				return ti.getValorItem();
			}
		}
		return null;
	}

	private void acumularCancelamentoContaAnterior(TabelaIntegracaoConslDiaNfclHelper helper, Date dtCancelamento, BigDecimal vlCancelamento)
					throws ControladorException{

		TabelaIntegracaoConsCanNtfisc tiConsCanNTFISC = obterTIConsCanNtFisc(helper.getCodigoFilialOrigem(), dtCancelamento, helper);
		BigDecimal vlAntigo = tiConsCanNTFISC.getValorCancelamento();
		tiConsCanNTFISC.addValorCancelamento(Util.somarBigDecimal(vlCancelamento));
		LOGGER.debug("### TI_CONS_CAN_NTFISC >> ADD CAN DT[" + Util.formatarData(dtCancelamento) + "] VL_PREV[" + vlAntigo + "] + VL_CAN["
						+ Util.somarBigDecimal(vlCancelamento) + "] = VL_ATUALIZADO[" + tiConsCanNTFISC.getValorCancelamento() + "]");
	}

	private TabelaIntegracaoConsCanNtfisc obterTIConsCanNtFisc(String codigoFilialOrigem, Date dtCancelamento,
					TabelaIntegracaoConslDiaNfclHelper helper) throws ControladorException{

		TabelaIntegracaoConsCanNtfisc tiConsCanNTFISC = null;

		for(TabelaIntegracaoConsCanNtfisc ti : TI_CONS_CAN_NTFISC){
			// Util.completarStringZeroEsquerda(codigoFilialOrigem, 3)
			if(ti.getComp_id().getCodigoFilialOrigem().equals(codigoFilialOrigem)
							&& ti.getComp_id().getCodigoSistemaOrigem().equals(TabelaIntegracaoConslDiaNfclHelper.COD_SIS_ORIGEM)
							&& ti.getComp_id().getDataCancelamento().equals(dtCancelamento)){
				tiConsCanNTFISC = ti;
				LOGGER.debug("	>> EXISTE NA MEMORIA: " + tiConsCanNTFISC.toString());
			}
		}

		if(tiConsCanNTFISC == null){
			try{
				tiConsCanNTFISC = new TabelaIntegracaoConsCanNtfisc(codigoFilialOrigem, TabelaIntegracaoConslDiaNfclHelper.COD_SIS_ORIGEM,
								dtCancelamento);
				Collection<TabelaIntegracaoConsCanNtfisc> resultado;
				tiConsCanNTFISC.formatarDadosEntidade();
				resultado = repositorioIntegracaoPiramide.consultaTIConsCanNTFISC(tiConsCanNTFISC.getComp_id());
				tiConsCanNTFISC = (TabelaIntegracaoConsCanNtfisc) Util.retonarObjetoDeColecao(resultado);
			}catch(ErroRepositorioException e){
				throw new RuntimeException("Falha ao buscar " + tiConsCanNTFISC.toString(), e);
			}
			if(tiConsCanNTFISC == null){
				tiConsCanNTFISC = new TabelaIntegracaoConsCanNtfisc(codigoFilialOrigem, TabelaIntegracaoConslDiaNfclHelper.COD_SIS_ORIGEM,
								dtCancelamento);
				tiConsCanNTFISC.setValorCancelamento(BigDecimal.ZERO);
				tiConsCanNTFISC.setCodigoOperacaoRegistro("I");
				tiConsCanNTFISC.setCodigoStatusRegistro("NP");
				tiConsCanNTFISC.setDescricaoErroRegistro(null);
				tiConsCanNTFISC.setNova(true); // TODO - MGRB -> Verificar se sempre será da memoria
				tiConsCanNTFISC.formatarDadosEntidade();
				LOGGER.debug("	>> NOVO NA MEMORIA: " + tiConsCanNTFISC.toString());
			}else{
				LOGGER.debug("	>> EXISTE NO BD: " + tiConsCanNTFISC.toString());
			}

			TI_CONS_CAN_NTFISC.add(tiConsCanNTFISC);
		}

		return tiConsCanNTFISC;
	}

	private void acumularValores(TabelaIntegracaoConslDiaNfclHelper helper, Date dtVigencia, int tipoAcumulo, Integer[] idsEventosContabil)
					throws ControladorException{

		try{

			// caso 1
			boolean exibirItemContabil = TabelaIntegracaoConslDiaNfclHelper.MODULO_FINANCIAMENTO.equals(tipoAcumulo)
							|| TabelaIntegracaoConslDiaNfclHelper.MODULO_GUIA_PAGAMENTO.equals(tipoAcumulo);


			Collection<Object[]> lancamentos = repositorioIntegracaoPiramide.consultarLancamentosContabeis(helper.getDataContabil(),
							helper.getIdMunicipio(), exibirItemContabil, tipoAcumulo, idsEventosContabil);
			LOGGER.info("	QTD Lancamentos para processar: [" + lancamentos.size() + "]");

			Integer idCategoria;
			Integer idItemContabil;
			BigDecimal vlConta;
			Integer codigoConsumoSped;

			for(Object[] objects : lancamentos){
				vlConta = (BigDecimal) objects[1];
				idCategoria = (Integer) objects[2];
				
				if(exibirItemContabil) idItemContabil = (Integer) objects[3];
				else idItemContabil = null;

				if(Integer.valueOf(3).equals(idCategoria)) idCategoria = 2;

				codigoConsumoSped = repositorioIntegracaoPiramide.consultarClassificacaoLancamento(dtVigencia, idCategoria, vlConta);
				calcularAcumulo(helper, codigoConsumoSped, tipoAcumulo, vlConta, idItemContabil);
			}


			if(TabelaIntegracaoConslDiaNfclHelper.MODULO_FATURAMENTO.equals(tipoAcumulo)){

				// caso 2
				Collection<Object[]> debitosLancamentos = repositorioIntegracaoPiramide
.consultarLancamentosContabeisPorEventoComercial(
								helper.getDataContabil(), helper.getIdMunicipio());
				LOGGER.info("	QTD debitos Lancamentos para processar: [" + debitosLancamentos.size() + "]");

				for(Object[] objects : debitosLancamentos){
					vlConta = (BigDecimal) objects[1];
					idCategoria = (Integer) objects[2];

					idItemContabil = (Integer) objects[3];

					if(Integer.valueOf(3).equals(idCategoria)) idCategoria = 2;

					codigoConsumoSped = repositorioIntegracaoPiramide.consultarClassificacaoLancamento(dtVigencia, idCategoria, vlConta);
					calcularAcumulo(helper, codigoConsumoSped, tipoAcumulo, vlConta, idItemContabil);
				}

			}

		}catch(ErroRepositorioException e){
			throw new ControladorException(e, "Falha ao consultar Lancamentos Analiticos.");
		}
	}

	private void calcularAcumulo(TabelaIntegracaoConslDiaNfclHelper helper, Integer classeConsumo, int modulo, BigDecimal vlConta,
					Integer idItemContabil){

		switch(modulo){
			case 1: // TabelaIntegracaoConslDiaNfclHelper.MODULO_FATURAMENTO:
				if(Arrays.asList(2, 5).contains(idItemContabil)){
					helper.acumularValoresJurosCorrecaoFaturamento(classeConsumo, vlConta);
					DEBUG_TOTALIZADOR.add(DebugTotalizador.INC_CONTA_NORMAL, 1, vlConta);
				}else{
					helper.acumularValoresFaturamento(classeConsumo, vlConta);
					DEBUG_TOTALIZADOR.add(DebugTotalizador.INC_CONTA_JUROS, 1, vlConta);
				}

				break;

			case 3: // TabelaIntegracaoConslDiaNfclHelper.MODULO_PARCELAMENTO:
				helper.acumularValoresParcelamento(classeConsumo, vlConta);
				DEBUG_TOTALIZADOR.add(DebugTotalizador.INC_PARCELAMENTO, 1, vlConta);
				break;

			case 4: // TabelaIntegracaoConslDiaNfclHelper.MODULO_FINANCIAMENTO:
				if(Arrays.asList(2, 5).contains(idItemContabil)){
					helper.acumularValoresParcelamento(classeConsumo, vlConta);
					DEBUG_TOTALIZADOR.add(DebugTotalizador.INC_FINANCIMENTO_JUROS, 1, vlConta);
				}else{
					helper.acumularValoresFaturamento(classeConsumo, vlConta);
					DEBUG_TOTALIZADOR.add(DebugTotalizador.INC_FINANCIAMENTO_NORMAL, 1, vlConta);
				}
				break;

			case 6: // TabelaIntegracaoConslDiaNfclHelper.MODULO_GUIA_PAGAMENTO:
				if(Arrays.asList(2, 5).contains(idItemContabil)){
					helper.acumularValoresParcelamento(classeConsumo, vlConta);
					DEBUG_TOTALIZADOR.add(DebugTotalizador.INC_GPGTO_JUROS, 1, vlConta);
				}else{
					helper.acumularValoresFaturamento(classeConsumo, vlConta);
					DEBUG_TOTALIZADOR.add(DebugTotalizador.INC_GPGTO_NORMAL, 1, vlConta);
				}

				break;

			default:
				LOGGER.info("MODULO[" + modulo + "] não entra para a integracao Sped Pis/Cofins.");
				break;
		}

	}

	private void inserirConslDiaNfcl(TabelaIntegracaoConslDiaNfclHelper helper, BigDecimal aliquotaPis, BigDecimal aliquotaCofins)
					throws ControladorException{

		List<TabelaIntegracaoCmConDiaNfcl> cmConDiaNfcl;
		List<TabelaIntegracaoConslDiaNfcl> tiConslDiaNfclConsolidados = helper.getTIConslDiaNfclConsolidados(aliquotaPis, aliquotaCofins);
		for(TabelaIntegracaoConslDiaNfcl conslDiaNfcl : tiConslDiaNfclConsolidados){
			cmConDiaNfcl = helper.getTICmConDiaNfcl(conslDiaNfcl.getCodigoClasseConsumo(), conslDiaNfcl.getCodigoSequenciaOrigem(),
							aliquotaPis,
							aliquotaCofins);
			conslDiaNfcl.formatarDadosEntidade();
			TI_CONSL_DIA_NFCL.add(conslDiaNfcl);
			TI_CM_CON_DIA_NFCL.addAll(cmConDiaNfcl);
		}
	}

	class DebugTotalizador {

		public static final String INC_GPGTO_NORMAL = "004 INC_GPGTO_NORMAL";

		public static final String INC_GPGTO_JUROS = "004 INC_GPGTO_JUROS";

		public static final String INC_FINANCIAMENTO_NORMAL = "006 INC_FINANCIAMENTO_NORMAL";

		public static final String INC_FINANCIMENTO_JUROS = "006 INC_FINANCIMENTO_JUROS";

		public static final String INC_PARCELAMENTO = "005 INC_PARCELAMENTO";

		public static final String INC_CONTA_NORMAL = "002 INC_CONTA_NORMAL";

		public static final String INC_CONTA_JUROS = "002 INC_CONTA_JUROS";

		public static final String CAN_CONTA_NM = "007 CAN_CONTA_NM";

		public static final String CAN_CONTA_ANT = "007 CAN_CONTA_ANT";

		public static final String CAN_SALDO = "010 CAN_SALDO";

		public static final String CAN_GPGTO_NJUROS_NM = "004a CAN_GPGTO_NJUROS_NM";

		public static final String CAN_GPGTO_ANT = "004a CAN_GPGTO_ANT";

		public static final String CAN_GPGTO_JUROS = "004a CAN_GPGTO_JUROS";

		public static final String CAN_PARC_JUROS = "005 CAN_PARC_JUROS";
		private Map<String, Object[]> totalizador;

		public DebugTotalizador() {

			totalizador = new HashMap<String, Object[]>();
		}

		public void add(String key, int i, BigDecimal vlLancamento){
			if(!totalizador.containsKey(key)) totalizador.put(key, new Object[] {0, BigDecimal.ZERO});
			Object[] sub = totalizador.get(key);
			sub[0] = ((Integer) sub[0]) + i;
			sub[1] = ((BigDecimal) sub[1]).add(vlLancamento);
			totalizador.put(key, sub);
		}

		@Override
		public String toString(){

			StringBuffer buffer = new StringBuffer();
			buffer.append("TOTAIS---\n");
			for(String key : new TreeSet<String>(totalizador.keySet())){
				buffer.append(key);
				buffer.append('\t');
				buffer.append(totalizador.get(key)[0]);
				buffer.append('\t');
				buffer.append(totalizador.get(key)[1]);
				buffer.append('\n');
			}
			buffer.append("---------");
			return buffer.toString();
		}

	}
}
