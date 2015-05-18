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
 * GSANPCG
 * Virgínia Melo
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

package gcom.relatorio.faturamento.conta;

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.EmitirContaHelper;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaCategoriaConsumoFaixa;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.parametrizacao.ParametroGeral;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;
import gcom.util.parametrizacao.webservice.ParametrosAgenciaVirtual;

import java.text.SimpleDateFormat;
import java.util.*;

import br.com.procenge.comum.exception.NegocioException;
import br.com.procenge.util.Constantes;

/**
 * [UC0482] Emitir 2ª Via de Conta
 * 
 * @author Vivianne Sousa
 * @date 15/09/2006
 * @author Virgínia Melo
 * @date 06/01/2009 Adicionados campos de qualidade da água na geração do relatório.
 * @author Virgínia Melo
 * @date 12/01/2009 Realizadas modificações para melhor exibição dos dados.
 */

public class Relatorio2ViaConta
				extends TarefaRelatorio {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7034984685957706140L;

	/**
	 * Quantidade máxima de linhas do detail da primeira página do relátorio
	 */
	public final static int NUMERO_MAX_lINHAS_DETAIL_PRIMEIRA_PAGINA = 18;

	/**
	 * Quantidade máxima de linhas do detail a partir da segunda página do relátorio
	 */
	public final static int NUMERO_MAX_lINHAS_DETAIL_OUTRAS_PAGINAS = 32;

	public Relatorio2ViaConta(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_2_VIA_CONTA);
	}

	@Deprecated
	public Relatorio2ViaConta() {

		super(null, "");
	}

	private Collection<Relatorio2ViaContaBean> inicializarBeanRelatorio(Collection colecaoEmitirContaHelper){

		Collection<Relatorio2ViaContaBean> retorno = new ArrayList<Relatorio2ViaContaBean>();



		Iterator iter = colecaoEmitirContaHelper.iterator();
		while(iter.hasNext()){

			EmitirContaHelper emitirContaHelper = (EmitirContaHelper) iter.next();

			String codigoRota = null;

			if(emitirContaHelper.getRotaEntrega() != null && !emitirContaHelper.getRotaEntrega().equals("")){
				codigoRota = emitirContaHelper.getRotaEntrega();
			}

			String debitoCreditoSituacaoAtualConta = "";
			if(emitirContaHelper.getDebitoCreditoSituacaoAtualConta() != null){
				debitoCreditoSituacaoAtualConta = emitirContaHelper.getDebitoCreditoSituacaoAtualConta().toString();
			}

			String contaPaga = null;
			if(emitirContaHelper.getContaPaga() != null && !emitirContaHelper.getContaPaga().equals("")){
				contaPaga = emitirContaHelper.getContaPaga();
			}

			// -----------------------------------------------------------------------------------------------
			String enderecoClienteEntrega = "";
			if(emitirContaHelper.getEnderecoClienteEntrega() != null && !emitirContaHelper.getEnderecoClienteEntrega().equals("")){
				enderecoClienteEntrega = emitirContaHelper.getEnderecoClienteEntrega();
			}
			// -----------------------------------------------------------------------------------------------

			Collection colecaolinhasDescricaoServicosTarifasTotal = emitirContaHelper
							.getColecaoContaLinhasDescricaoServicosTarifasTotalHelper();

			Collection<Relatorio2ViaContaDetailBean> colecaoDetail = new ArrayList<Relatorio2ViaContaDetailBean>();

			int totalLinhasRelatorio = 0;
			int totalPaginasRelatorio = 1;
			// String indicadorPrimeiraPagina = "1";


			String indicadorRodape = "0";

			String dataVencimentoFormatada = Util.formatarData(emitirContaHelper.getDataVencimentoConta());
			String anoMesReferenciaFormatada = Util.formatarAnoMesParaMesAno(emitirContaHelper.getAmReferencia());

			// Alterações para exibição do relatório com rodapé na última página
			int qtdItensTotal = colecaolinhasDescricaoServicosTarifasTotal.size();

			if(qtdItensTotal <= NUMERO_MAX_lINHAS_DETAIL_PRIMEIRA_PAGINA){

				// O relatório terá apenas uma página
				indicadorRodape = "1";
				Iterator iterator = colecaolinhasDescricaoServicosTarifasTotal.iterator();
				while(iterator.hasNext()){

					ContaLinhasDescricaoServicosTarifasTotalHelper linhasDescricaoServicosTarifasTotalHelper = (ContaLinhasDescricaoServicosTarifasTotalHelper) iterator
									.next();

					Relatorio2ViaContaDetailBean relatorio2ViaContaDetailBean = new Relatorio2ViaContaDetailBean(
									linhasDescricaoServicosTarifasTotalHelper.getDescricaoServicosTarifas(),
									linhasDescricaoServicosTarifasTotalHelper.getConsumoFaixa(), linhasDescricaoServicosTarifasTotalHelper
													.getValor(), linhasDescricaoServicosTarifasTotalHelper.getSinal());

					colecaoDetail.add(relatorio2ViaContaDetailBean);

					totalLinhasRelatorio = totalLinhasRelatorio + 1;

					// se já tem a quantidade máx pra primeira página..
					if((totalLinhasRelatorio == NUMERO_MAX_lINHAS_DETAIL_PRIMEIRA_PAGINA)
									|| (totalLinhasRelatorio - NUMERO_MAX_lINHAS_DETAIL_PRIMEIRA_PAGINA)
													% NUMERO_MAX_lINHAS_DETAIL_OUTRAS_PAGINAS == 0){

						Relatorio2ViaContaBean relatorio2ViaContaBean = gerarRelatorio2ViaContaBean(emitirContaHelper, indicadorRodape,
										anoMesReferenciaFormatada, colecaoDetail, dataVencimentoFormatada, enderecoClienteEntrega,
										totalPaginasRelatorio, codigoRota, debitoCreditoSituacaoAtualConta, contaPaga);

						retorno.add(relatorio2ViaContaBean);

						colecaoDetail.clear();
					}
				}

				// Não completou nem uma página mas gera
				indicadorRodape = "1";

				Relatorio2ViaContaBean relatorio2ViaContaBean = gerarRelatorio2ViaContaBean(emitirContaHelper, indicadorRodape,
								anoMesReferenciaFormatada, colecaoDetail, dataVencimentoFormatada, enderecoClienteEntrega,
								totalPaginasRelatorio, codigoRota, debitoCreditoSituacaoAtualConta, contaPaga);

				relatorio2ViaContaBean.setIndicadorExibirAcrescimos(emitirContaHelper.getIndicadorExibirAcrescimos());

				retorno.add(relatorio2ViaContaBean);

			}else{

				indicadorRodape = "0";
				int qtdOutrasPaginas = qtdItensTotal / NUMERO_MAX_lINHAS_DETAIL_OUTRAS_PAGINAS;
				if(qtdOutrasPaginas == 0){
					qtdOutrasPaginas = 1;
				}

				int qtdItensOutrasPaginas = qtdOutrasPaginas * NUMERO_MAX_lINHAS_DETAIL_OUTRAS_PAGINAS;

				// Verifica se a quantidade de itens restante cabe na última página (18) senão, será
				// preciso mais uma página.
				/*
				 * if (qtdItensTotal - qtdItensOutrasPaginas >
				 * NUMERO_MAX_lINHAS_DETAIL_PRIMEIRA_PAGINA) { qtdOutrasPaginas++; }
				 */

				Iterator iterator = colecaolinhasDescricaoServicosTarifasTotal.iterator();

				while(iterator.hasNext()){

					// Adiciona o item à COLEÇÃO DE ITENS
					ContaLinhasDescricaoServicosTarifasTotalHelper linhasDescricaoServicosTarifasTotalHelper = (ContaLinhasDescricaoServicosTarifasTotalHelper) iterator
									.next();

					Relatorio2ViaContaDetailBean relatorio2ViaContaDetailBean = new Relatorio2ViaContaDetailBean(
									linhasDescricaoServicosTarifasTotalHelper.getDescricaoServicosTarifas(),
									linhasDescricaoServicosTarifasTotalHelper.getConsumoFaixa(), linhasDescricaoServicosTarifasTotalHelper
													.getValor(), linhasDescricaoServicosTarifasTotalHelper.getSinal());

					colecaoDetail.add(relatorio2ViaContaDetailBean);

					totalLinhasRelatorio = totalLinhasRelatorio + 1;

					if(totalLinhasRelatorio == qtdItensOutrasPaginas){

						// Gera as outras páginas do relatório
						Relatorio2ViaContaBean relatorio2ViaContaBean = gerarRelatorio2ViaContaBean(emitirContaHelper, indicadorRodape,
										anoMesReferenciaFormatada, colecaoDetail, dataVencimentoFormatada, enderecoClienteEntrega,
										totalPaginasRelatorio, codigoRota, debitoCreditoSituacaoAtualConta, contaPaga);

						retorno.add(relatorio2ViaContaBean);

						// Limpa a COLEÇÃO DE ITENS
						colecaoDetail.clear();

					}
				}

				if(totalLinhasRelatorio < qtdItensOutrasPaginas){

					// Gera as outras páginas do relatório
					Relatorio2ViaContaBean relatorio2ViaContaBean = gerarRelatorio2ViaContaBean(emitirContaHelper, indicadorRodape,
									anoMesReferenciaFormatada, colecaoDetail, dataVencimentoFormatada, enderecoClienteEntrega,
									totalPaginasRelatorio, codigoRota, debitoCreditoSituacaoAtualConta, contaPaga);

					retorno.add(relatorio2ViaContaBean);

					// Limpa a COLEÇÃO DE ITENS
					colecaoDetail.clear();
				}

				// Gera a última página do relatório (com itens ou sem itens)
				indicadorRodape = "1";

				Relatorio2ViaContaBean relatorio2ViaContaBean = gerarRelatorio2ViaContaBean(emitirContaHelper, indicadorRodape,
								anoMesReferenciaFormatada, colecaoDetail, dataVencimentoFormatada, enderecoClienteEntrega,
								totalPaginasRelatorio, codigoRota, debitoCreditoSituacaoAtualConta, contaPaga);

				relatorio2ViaContaBean.setIndicadorExibirAcrescimos(emitirContaHelper.getIndicadorExibirAcrescimos());

				retorno.add(relatorio2ViaContaBean);
			}

		}

		return retorno;
	}

	/**
	 * Método que executa a tarefa
	 * 
	 * @return Object
	 */
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Fachada fachada = Fachada.getInstancia();

		// Collection colecaoEmitirContaHelper = (Collection)
		// getParametro("colecaoEmitirContaHelper");
		Collection idsConta = (Collection) getParametro("idsConta");
		boolean cobrarTaxaEmissaoConta = (Boolean) getParametro("cobrarTaxaEmissaoConta");
		Short contaSemCodigoBarras = (Short) getParametro("contaSemCodigoBarras");
		String indicadorEmitido2ViaAgenciaVirtual = (String) getParametro("indicadorEmitido2ViaAgenciaVirtual");

		Integer idContaHistorico = (Integer) getParametro("idContaHistorico");
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		Collection colecaoEmitirContaHelper = new ArrayList();

		// Indicador da operação caucionamento de conta
		String indicadorOperacao = (String) getParametro("indicadorOperacao");

		// Verifica se a operação é de caucionamento de conta
		if(!Util.isVazioOuBranco(indicadorOperacao) && indicadorOperacao.equals("caucionar")){
			// Recupera o caucionamento da conta
			Map<Conta, Collection<Collection<ContaCategoriaConsumoFaixa>>> caucionamento = (Map<Conta, Collection<Collection<ContaCategoriaConsumoFaixa>>>) getParametro("caucionamento");

			if(!Util.isVazioOuBranco(caucionamento)){
				Iterator<Conta> iteratorConta = caucionamento.keySet().iterator();

				Conta contaCaucionamento = iteratorConta.next();

				Collection<Collection<ContaCategoriaConsumoFaixa>> colecaoContaCategoriaConsumoFaixa = caucionamento
								.get(contaCaucionamento);

				Collection<MedicaoHistorico> colecaoDadosMedicaoLeitura = (Collection<MedicaoHistorico>) getParametro("colecaoDadosMedicaoLeitura");

				Collection colecaoContaCaucionamento = new ArrayList();

				colecaoContaCaucionamento.add(contaCaucionamento);

				// Chama o emitir conta caucionada
				colecaoEmitirContaHelper = fachada.emitir2ViaContasCaucionadas(colecaoContaCaucionamento, cobrarTaxaEmissaoConta,
								contaSemCodigoBarras, colecaoDadosMedicaoLeitura, colecaoContaCategoriaConsumoFaixa);
			}
		}else{
			if(idContaHistorico == null){
				colecaoEmitirContaHelper = fachada.emitir2ViaContas(idsConta, cobrarTaxaEmissaoConta, contaSemCodigoBarras);
			}else{
				colecaoEmitirContaHelper = fachada.emitir2ViaContasHistorico(idsConta, cobrarTaxaEmissaoConta, contaSemCodigoBarras);
			}
		}

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		String nomeEmpresa = (String) getParametro("nomeEmpresa");

		// valor de retorno
		byte[] retorno = null;

		// Parâmetros do relatório
		Map<String, String> parametros = new HashMap();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("imagemConta", sistemaParametro.getImagemConta());
		parametros.put("nomeEmpresa", nomeEmpresa);
		parametros.put("P_NM_EMPRESA", sistemaParametro.getNomeEmpresa());

		String pLabelMatriculaDocumentosPagaveis = null;

		try{

			pLabelMatriculaDocumentosPagaveis = (String) ParametroFaturamento.P_LABEL_MATRICULA_DOCUMENTOS_PAGAVEIS.executar();
		}catch(ControladorException e){

			throw new TarefaException(e.getMessage(), e);
		}

		parametros.put("P_LABEL_MATRICULA_DOCUMENTOS_PAGAVEIS", pLabelMatriculaDocumentosPagaveis.toUpperCase());

		try{
			parametros.put("P_ENDERECO", fachada.pesquisarEnderecoFormatadoEmpresa());
		}catch(ControladorException e1){
			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio");
		}
		parametros.put("P_CNPJ", gcom.util.Util.formatarCnpj(sistemaParametro.getCnpjEmpresa()));
		parametros.put("P_FONE", Util.formatarFone(sistemaParametro.getNumeroTelefone()));
		try{
			parametros.put("P_INSC_EST",
							(String) fachada.obterValorDoParametroPorCodigo(ParametroGeral.P_INSCRICAO_ESTADUAL_EMPRESA.getCodigo()));
			parametros.put("P_JUCOR", (String) fachada.obterValorDoParametroPorCodigo(ParametroGeral.P_JUNTA_COMERCIAL_EMPRESA.getCodigo()));
		}catch(NegocioException e1){
			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio");
		}

		/**
		 * [UC0482] Na emissão de 2ª via de conta via web, informar no pdf que a emissão foi via
		 * web.
		 * 
		 * @author Gicevalter Couto
		 * @created 12/08/2014
		 */
		try{
			if(indicadorEmitido2ViaAgenciaVirtual != null && indicadorEmitido2ViaAgenciaVirtual.equals("S")){
				String indicadorEmitirMsg2ViaAgenciaVirtual = (String) ParametrosAgenciaVirtual.P_INDICADOR_MSG_2VIA_CONTA_AGENCIA_VIRTUAL
								.executar();
				parametros.put("P_INDICADOR_MSG_2VIA_CONTA_AGENCIA_VIRTUAL", indicadorEmitirMsg2ViaAgenciaVirtual);
			}else{
				parametros.put("P_INDICADOR_MSG_2VIA_CONTA_AGENCIA_VIRTUAL", "2");
			}
		}catch(ControladorException e1){
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio");
		}

		/**
		 * [UC0482] Na emissão de 2ª via de conta, emitir a mensagem de quitação de débito anual.
		 * 
		 * @author Gicevalter Couto
		 * @created 12/08/2014
		 */
		try{
			String msgQuitacaoDebitoAnual = (String) ParametroFaturamento.P_MENSAGEM_QUITACAO_DEBITO_ANUAL.executar();
			parametros.put("P_MENSAGEM_QUITACAO_DEBITO_ANUAL", msgQuitacaoDebitoAnual);
		}catch(ControladorException e1){
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio");
		}

		Collection dadosRelatorio = colecaoEmitirContaHelper;

		Collection<Relatorio2ViaContaBean> colecaoBean = this.inicializarBeanRelatorio(dadosRelatorio);

		if(colecaoBean == null || colecaoBean.isEmpty()){
			// Não existem dados para a exibição do relatório.
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}

		RelatorioDataSource ds = new RelatorioDataSource((java.util.List) colecaoBean);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_2_VIA_CONTA, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.SEGUNDA_VIA_CONTA, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------
		
		

		// retorna o relatório gerado
		return retorno;

	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Integer qtdeContas = (Integer) getParametro("qtdeContas");

		return qtdeContas;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("Relatorio2ViaConta", this);
	}

	/**
	 * Método responsável por gerar um objeto do tipo Relatorio2ViaContaBean
	 * 
	 * @author Virgínia Melo
	 * @date 12/01/2009
	 * @author Virgínia Melo
	 * @date 27/02/2009 Adicionado parâmetro nº hidrômetro.
	 * @param emitirContaHelper
	 * @param indicadorRodape
	 * @param anoMesReferenciaFormatada
	 * @param colecaoDetail
	 * @param dataVencimentoFormatada
	 * @param enderecoClienteResponsavel
	 * @param totalPaginasRelatorio
	 * @param codigoRota
	 * @param debitoCreditoSituacaoAtualConta
	 * @param contaPaga
	 * @param enderecoClienteResponsavelLinha2
	 * @return
	 */
	private Relatorio2ViaContaBean gerarRelatorio2ViaContaBean(EmitirContaHelper emitirContaHelper, String indicadorRodape,
					String anoMesReferenciaFormatada, Collection<Relatorio2ViaContaDetailBean> colecaoDetail,
					String dataVencimentoFormatada, String enderecoClienteEntrega, int totalPaginasRelatorio, String codigoRota,
					String debitoCreditoSituacaoAtualConta, String contaPaga){

		Relatorio2ViaContaBean relatorio2ViaContaBean = new Relatorio2ViaContaBean(indicadorRodape, colecaoDetail,
						emitirContaHelper.getDescricaoLocalidade(), Imovel.getMatriculaComDigitoVerificadorFormatada(emitirContaHelper
										.getIdImovel().toString()), dataVencimentoFormatada, emitirContaHelper.getNomeCliente(),
						emitirContaHelper.getEnderecoImovel(), anoMesReferenciaFormatada, emitirContaHelper.getInscricaoImovel(),
						enderecoClienteEntrega, emitirContaHelper.getDescricaoLigacaoAguaSituacao(),
						emitirContaHelper.getDescricaoLigacaoEsgotoSituacao(), emitirContaHelper.getDadosConsumoMes1(),
						emitirContaHelper.getDadosConsumoMes4(), emitirContaHelper.getDadosConsumoMes2(),
						emitirContaHelper.getDadosConsumoMes5(), emitirContaHelper.getLeituraAnterior(),
						emitirContaHelper.getLeituraAtual(), emitirContaHelper.getConsumoFaturamento(), emitirContaHelper.getDiasConsumo(),
						emitirContaHelper.getConsumoMedioDiario(), emitirContaHelper.getDadosConsumoMes3(),
						emitirContaHelper.getDadosConsumoMes6(), emitirContaHelper.getDataLeituraAnterior(),
						emitirContaHelper
										.getDataLeituraAtual(), emitirContaHelper.getDescricaoTipoConsumo(), emitirContaHelper
										.getDescricaoAnormalidadeConsumo(), emitirContaHelper.getQuantidadeEconomiaConta(),
						emitirContaHelper.getConsumoEconomia(), emitirContaHelper.getCodigoAuxiliarString(), emitirContaHelper
										.getMensagemConsumoString(), emitirContaHelper.getValorContaString(), emitirContaHelper
										.getPrimeiraParte(), emitirContaHelper.getSegundaParte(), emitirContaHelper.getTerceiraParte(),
						emitirContaHelper.getNomeGerenciaRegional(), emitirContaHelper.getMesAnoFormatado(), emitirContaHelper
										.getNumeroIndiceTurbidez(), emitirContaHelper.getNumeroCloroResidual(), emitirContaHelper
										.getRepresentacaoNumericaCodBarraFormatada(), emitirContaHelper
										.getRepresentacaoNumericaCodBarraSemDigito(), emitirContaHelper.getDataValidade(),
						emitirContaHelper.getIdFaturamentoGrupo().toString(), emitirContaHelper.getIdEmpresa().toString(), ""
										+ totalPaginasRelatorio, emitirContaHelper.getIdConta().toString(), codigoRota, emitirContaHelper
										.getContaSemCodigoBarras(), debitoCreditoSituacaoAtualConta, contaPaga, emitirContaHelper
										.getNumeroAmostrasMediaTurbidez(), emitirContaHelper.getNumeroAmostrasMediaCloro(),
						emitirContaHelper.getNumeroAmostrasMediaCor(), emitirContaHelper.getNumeroAmostrasMediaPH(), emitirContaHelper
										.getNumeroAmostrasMediaBacteriasHeterotroficas(), emitirContaHelper
										.getNumeroAmostrasMediaColiformesTermotolerantes(), emitirContaHelper
										.getNumeroAmostrasMediaColiformesTotais(), emitirContaHelper.getNumeroHidrometro(),
						(emitirContaHelper.getContaMotivoRetificacao() != null) ? emitirContaHelper.getContaMotivoRetificacao().toString()
										+ "-" + emitirContaHelper.getDescricaoContaMotivoRetificacao()
										: "", (emitirContaHelper.getFuncionario() != null) ? emitirContaHelper.getFuncionario().toString()
										: "", (emitirContaHelper.getConsumoMedido() != null) ? emitirContaHelper.getConsumoMedido().toString() : "",
						(emitirContaHelper.getValorImpostoPisCofins() != null) ? Util.formatarMoedaReal(emitirContaHelper
										.getValorImpostoPisCofins())
										: "");

		relatorio2ViaContaBean.setTipoDocCliente(emitirContaHelper.getTipoDocCliente());
		relatorio2ViaContaBean.setCpfCnpjCliente(emitirContaHelper.getCpfCnpjCliente());
		// relatorio2ViaContaBean.setDataEmissaoConta(emitirContaHelper.getDataEmissaoContaFormatada());
		SimpleDateFormat df = new SimpleDateFormat(Constantes.FORMATO_DATA_BR);
		relatorio2ViaContaBean.setDataEmissaoConta(df.format(new Date()));
		relatorio2ViaContaBean.setNomeRelatorio((String) this.getParametro("nomeRelatorio"));

		if(emitirContaHelper.getAnoQuitacaoDebitoAnual() != null){
			relatorio2ViaContaBean.setAnoQuitacaoDebitoAnual(emitirContaHelper.getAnoQuitacaoDebitoAnual().toString());
		}

		if(emitirContaHelper.getMensagemSubstitutaCodigoBarras() != null){

			relatorio2ViaContaBean.setMensagemSubstitutaCodigoBarras(emitirContaHelper.getMensagemSubstitutaCodigoBarras());
		}

		return relatorio2ViaContaBean;
	}
}
