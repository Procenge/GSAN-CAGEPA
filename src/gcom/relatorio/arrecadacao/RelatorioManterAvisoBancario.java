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

package gcom.relatorio.arrecadacao;

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.ArrecadadorMovimento;
import gcom.arrecadacao.aviso.AvisoBancario;
import gcom.arrecadacao.aviso.bean.AvisoBancarioHelper;
import gcom.arrecadacao.banco.ContaBancaria;
import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * classe respons�vel por criar o relat�rio de bairro manter de �gua
 * 
 * @author S�vio Luiz
 * @created 11 de Julho de 2005
 */
public class RelatorioManterAvisoBancario
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioManterAvisoBancario(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_AVISO_BANCARIO_MANTER);
	}

	@Deprecated
	public RelatorioManterAvisoBancario() {

		super(null, "");
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param bairros
	 *            Description of the Parameter
	 * @param bairroParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		AvisoBancarioHelper avisoBancarioHelperParametros = (AvisoBancarioHelper) getParametro("avisoBancarioHelperParametros");
		Arrecadador arrecadadorParametros = (Arrecadador) getParametro("arrecadadorParametros");
		ContaBancaria contaBancariaParametros = (ContaBancaria) getParametro("contaBancariaParametros");
		ArrecadadorMovimento arrecadadorMovimentoParametros = (ArrecadadorMovimento) getParametro("arrecadadorMovimentoParametros");
		String avisosAbertosFechadosParametros = (String) getParametro("avisosAbertosFechadosParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioAvisosBancariosBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioManterAvisoBancarioBean relatorioAvisoBancarioBean = null;

		Collection colecaoAvisosBancarios = (Collection) getParametro("colecaoAvisosBancarios");

		// se a cole��o de par�metros da analise n�o for vazia
		if(colecaoAvisosBancarios != null && !colecaoAvisosBancarios.isEmpty()){

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoAvisosBancariosIterator = colecaoAvisosBancarios.iterator();

			// BigDecimal totalDeducoes = new BigDecimal("0.00");

			// la�o para criar a cole��o de par�metros da analise
			while(colecaoAvisosBancariosIterator.hasNext()){

				AvisoBancarioRelatorioHelper avisoBancarioRelatorioHelper = (AvisoBancarioRelatorioHelper) colecaoAvisosBancariosIterator
								.next();

				BigDecimal totalDeducoes = new BigDecimal("0.00");

				Collection colecaoDeducoes = fachada.pesquisarAvisoDeducoesAvisoBancarioRelatorio(avisoBancarioRelatorioHelper
								.getIdAvisoBancario());

				Collection colecaoRelatorioAvisoBancarioDeducoesBeans = new ArrayList();

				if(colecaoDeducoes != null && !colecaoDeducoes.isEmpty()){
					Iterator colecaoDeducoesIterator = colecaoDeducoes.iterator();

					RelatorioManterAvisoBancarioDeducoesBean relatorioDeducoesBean = null;

					while(colecaoDeducoesIterator.hasNext()){
						DeducoesRelatorioHelper deducoesRelatorioHelper = (DeducoesRelatorioHelper) colecaoDeducoesIterator.next();
						totalDeducoes = totalDeducoes.add(deducoesRelatorioHelper.getValorDeducao());
					}

				}

				relatorioAvisoBancarioBean = new RelatorioManterAvisoBancarioBean(

				// Id Aviso Banc�rio
								avisoBancarioRelatorioHelper.getIdAvisoBancario().toString(),

								// Arrecadador
								avisoBancarioRelatorioHelper.getNomeArrecadador(),

								// Data Lan�amento
								Util.formatarData(avisoBancarioRelatorioHelper.getDataLancamento()),

								// Sequencial
								avisoBancarioRelatorioHelper.getSequencial() == null ? "" : avisoBancarioRelatorioHelper.getSequencial()
												.toString(),

								// Tipo
								avisoBancarioRelatorioHelper.getTipo().shortValue() == AvisoBancario.INDICADOR_CREDITO.shortValue() ? "C"
												: "D",

								// N�mero Documento
								avisoBancarioRelatorioHelper.getNumeroDocumento().toString(),

								// Banco
								avisoBancarioRelatorioHelper.getBanco(),

								// Ag�ncia
								avisoBancarioRelatorioHelper.getAgencia().toString(),

								// N�mero Conta
								avisoBancarioRelatorioHelper.getNumeroConta(),

								// Data Realiza��o
								Util.formatarData(avisoBancarioRelatorioHelper.getDataRealizacao()),

								// Total Arrecada��o
								avisoBancarioRelatorioHelper.getTotalArrecadacao(),

								// Total Dedu��es
								totalDeducoes,

								// Total Devolu��o
								avisoBancarioRelatorioHelper.getTotalDevolucao(),

								// Valor Aviso
								avisoBancarioRelatorioHelper.getValorAviso());

				// Data de pagamento do aviso banc�rio
				relatorioAvisoBancarioBean.setDataPagamentoAvisoBancario(Util.formatarData(avisoBancarioRelatorioHelper
								.getDataPagamentoAvisoBancario()));

				// Valor Arrecadacao Calculado
				relatorioAvisoBancarioBean.setValorArrecadacaoCalculado(avisoBancarioRelatorioHelper.getValorArrecadacaoCalculado());

				// Valor devolucao Calculado
				relatorioAvisoBancarioBean.setValorDevolucaoCalculado(avisoBancarioRelatorioHelper.getValorDevolucaoCalculado());

				// Calcula o valor dos acertos
				Hashtable<Short, BigDecimal> hashValorAcertos = Fachada.getInstancia().obterValorTotalAcertosArrecadacaoAvisoBancario(
								avisoBancarioRelatorioHelper.getIdAvisoBancario());

				// Valor Liquido (Valor realizado)
				BigDecimal valorLiquido = null;
				BigDecimal valorAcerto = null;
				BigDecimal valorDiferenca = null;

				if(avisoBancarioRelatorioHelper.getTipo().shortValue() == AvisoBancario.INDICADOR_CREDITO.shortValue()){

					valorAcerto = hashValorAcertos.get(ConstantesSistema.INDICADOR_AVISO_ACERTO_ARRECADACAO);

					// 1.8.4.1. Se Aviso de Credito (AVBC_ICCREDITODEBITO=1) Ent�o VL.Realizado =
					// VL.Inf.Arrecada��o + VL.Acertos Arrecada��o - VL.Dedu��es.
					valorLiquido = avisoBancarioRelatorioHelper.getTotalArrecadacao().add(valorAcerto).subtract(totalDeducoes);

					// VL.Diferen�a=(VL.Calculado � (VL.Informado + Vl.Acertos) );
					valorDiferenca = avisoBancarioRelatorioHelper.getValorArrecadacaoCalculado().subtract(
									avisoBancarioRelatorioHelper.getTotalArrecadacao().add(valorAcerto));

					relatorioAvisoBancarioBean.setValorDiferencaArrecadacao(valorDiferenca);
					relatorioAvisoBancarioBean.setValorDiferencaDevolucao(BigDecimal.ZERO);
				}else{

					valorAcerto = hashValorAcertos.get(ConstantesSistema.INDICADOR_AVISO_ACERTO_DEVOLUCAO);

					// 1.8.4.2 Se Aviso de Debito (AVBC_ICCREDITODEBITO=2) Ent�o VL.Realizado =
					// VL.Inf.Devolu��o + VL.Acertos Devolu��o - VL.Dedu��es
					valorLiquido = avisoBancarioRelatorioHelper.getTotalDevolucao().add(valorAcerto).subtract(totalDeducoes);

					// VL.Diferen�a=(VL.Calculado � (VL.Informado + Vl.Acertos) );
					valorDiferenca = avisoBancarioRelatorioHelper.getValorDevolucaoCalculado().subtract(
									avisoBancarioRelatorioHelper.getTotalDevolucao().add(valorAcerto));

					relatorioAvisoBancarioBean.setValorDiferencaArrecadacao(BigDecimal.ZERO);
					relatorioAvisoBancarioBean.setValorDiferencaDevolucao(valorDiferenca);
				}

				relatorioAvisoBancarioBean.setValorLiquido(valorLiquido);

				relatorioAvisoBancarioBean.setValorAcerto(valorAcerto);

				relatorioAvisoBancarioBean.setValorDiferenca(valorDiferenca);

				// 2.2.7. Situa��o (Aberto/Fechado) (Se Diferen�a=0, FECHADO sen�o ABERTO);
				if(valorDiferenca.intValue() == 0){
					relatorioAvisoBancarioBean.setSituacao(ConstantesSistema.FECHADO);
				}else{
					relatorioAvisoBancarioBean.setSituacao(ConstantesSistema.ABERTO);
				}

				// adiciona o bean a cole��o
				relatorioAvisosBancariosBeans.add(relatorioAvisoBancarioBean);
			}
		}

		// seta a propria colecao em cada bean para calcular os totais do subrelatorio
		ArrayList<RelatorioManterAvisoBancarioBean> listaClonada = (ArrayList<RelatorioManterAvisoBancarioBean>) ((ArrayList) relatorioAvisosBancariosBeans)
						.clone();
		for(RelatorioManterAvisoBancarioBean relatorioManterAvisoBancarioBean : (ArrayList<RelatorioManterAvisoBancarioBean>) relatorioAvisosBancariosBeans){
			relatorioManterAvisoBancarioBean.setArrayRelatorioManterAvisoBancarioResumoBean(listaClonada);

		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// Id Arrecadador

		if(arrecadadorParametros != null){

			parametros.put("idArrecadador", arrecadadorParametros.getCodigoAgente().toString());

		}else{
			parametros.put("idArrecadador", "");
		}

		// Nome Arrecadador
		if(arrecadadorParametros != null){
			parametros.put("nomeArrecadador", arrecadadorParametros.getCliente().getNome());
		}else{
			parametros.put("nomeArrecadador", "");
		}

		// Per�odo Lan�amento

		if(avisoBancarioHelperParametros.getDataLancamentoInicial() != null){

			parametros.put("periodoLancamentoInicial", Util.formatarData(avisoBancarioHelperParametros.getDataLancamentoInicial()));

		}else{
			parametros.put("periodoLancamentoInicial", null);
		}

		if(avisoBancarioHelperParametros.getDataLancamentoFinal() != null){

			parametros.put("periodoLancamentoFinal", Util.formatarData(avisoBancarioHelperParametros.getDataLancamentoFinal()));

		}else{
			parametros.put("periodoLancamentoFinal", null);
		}

		// Tipo Aviso

		String tipoAviso = "";

		if(avisoBancarioHelperParametros.getTipoAviso() != null && !avisoBancarioHelperParametros.getTipoAviso().equals("")){
			if(avisoBancarioHelperParametros.getTipoAviso().equals(AvisoBancario.INDICADOR_CREDITO.toString())){
				tipoAviso = "Cr�dito";
			}else if(avisoBancarioHelperParametros.getTipoAviso().equals(AvisoBancario.INDICADOR_DEBITO.toString())){
				tipoAviso = "D�bito";
			}
		}

		parametros.put("tipoAviso", tipoAviso);

		// Conta Banc�ria

		if(contaBancariaParametros != null){

			parametros
							.put("contaBancaria", contaBancariaParametros.getAgencia().getBanco().getId().toString()
											+ contaBancariaParametros.getAgencia().getCodigoAgencia().toString()
											+ contaBancariaParametros.getNumeroConta());

		}else{
			parametros.put("contaBancaria", "");
		}

		// Movimento

		if(arrecadadorMovimentoParametros != null){

			parametros.put("movimento", arrecadadorMovimentoParametros.getCodigoBanco().toString()
							+ arrecadadorMovimentoParametros.getCodigoRemessa().toString()
							+ arrecadadorMovimentoParametros.getDescricaoIdentificacaoServico()
							+ arrecadadorMovimentoParametros.getNumeroSequencialArquivo().toString());

		}else{
			parametros.put("movimento", "");
		}

		// Per�odo Arrecada��o

		if(avisoBancarioHelperParametros.getAnoMesReferenciaArrecadacaoInicial() != 0){

			parametros.put("periodoArrecadacaoInicial", Util.formatarAnoMesParaMesAno(avisoBancarioHelperParametros
							.getAnoMesReferenciaArrecadacaoInicial()));

		}else{
			parametros.put("periodoArrecadacaoInicial", null);
		}

		if(avisoBancarioHelperParametros.getAnoMesReferenciaArrecadacaoFinal() != 0){

			parametros.put("periodoArrecadacaoFinal", Util.formatarAnoMesParaMesAno(avisoBancarioHelperParametros
							.getAnoMesReferenciaArrecadacaoFinal()));

		}else{
			parametros.put("periodoArrecadacaoFinal", null);
		}

		// Per�odo Previs�o Cr�dito/D�bito

		if(avisoBancarioHelperParametros.getDataPrevistaInicial() != null){

			parametros
							.put("periodoPrevisaoCreditoDebitoInicial", Util.formatarData(avisoBancarioHelperParametros
											.getDataPrevistaInicial()));

		}else{
			parametros.put("periodoPrevisaoCreditoDebitoInicial", null);
		}

		if(avisoBancarioHelperParametros.getDataPrevistaFinal() != null){

			parametros.put("periodoPrevisaoCreditoDebitoFinal", Util.formatarData(avisoBancarioHelperParametros.getDataPrevistaFinal()));

		}else{
			parametros.put("periodoPrevisaoCreditoDebitoFinal", null);
		}

		// Per�odo Realiza��o Cr�dito/D�bito

		if(avisoBancarioHelperParametros.getDataRealizadaInicial() != null){

			parametros.put("periodoRealizacaoCreditoDebitoInicial", Util.formatarData(avisoBancarioHelperParametros
							.getDataRealizadaInicial()));

		}else{
			parametros.put("periodoRealizacaoCreditoDebitoInicial", null);
		}

		if(avisoBancarioHelperParametros.getDataRealizadaFinal() != null){

			parametros.put("periodoRealizacaoCreditoDebitoFinal", Util.formatarData(avisoBancarioHelperParametros.getDataRealizadaFinal()));

		}else{
			parametros.put("periodoRealizacaoCreditoDebitoFinal", null);
		}

		// Intervalo Valor Realizado

		if(avisoBancarioHelperParametros.getValorRealizadoInicial() != null){

			parametros.put("intervaloValorRealizadoInicial", Util.formatarMoedaReal(avisoBancarioHelperParametros
							.getValorRealizadoInicial()));

		}else{
			parametros.put("intervaloValorRealizadoInicial", null);
		}

		if(avisoBancarioHelperParametros.getValorRealizadoFinal() != null){

			parametros.put("intervaloValorRealizadoFinal", Util.formatarMoedaReal(avisoBancarioHelperParametros.getValorRealizadoFinal()));

		}else{
			parametros.put("intervaloValorRealizadoInicial", null);
		}

		// Avisos Abertos/Fechados

		String avisosAbertosFechados = "";

		if(avisosAbertosFechadosParametros != null && !avisosAbertosFechadosParametros.equals("")
						&& !avisosAbertosFechadosParametros.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			if(avisosAbertosFechadosParametros.equals("2")){
				avisosAbertosFechados = "Aberto";
			}else if(avisosAbertosFechadosParametros.equals("3")){
				avisosAbertosFechados = "Fechado";
			}
		}

		parametros.put("avisosAbertosFechados", avisosAbertosFechados);

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioAvisosBancariosBeans);
		JRBeanCollectionDataSource subRelatorio = new JRBeanCollectionDataSource(relatorioAvisosBancariosBeans);
		parametros.put("subRelatorio", subRelatorio);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_AVISO_BANCARIO_MANTER, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_AVISO_BANCARIO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = (Integer) getParametro("totalRegistrosRelatorio");

		return retorno;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioManterAvisoBancario", this);
	}
}