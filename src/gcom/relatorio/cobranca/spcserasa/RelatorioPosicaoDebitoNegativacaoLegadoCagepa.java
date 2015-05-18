
package gcom.relatorio.cobranca.spcserasa;

import gcom.arrecadacao.pagamento.FiltroPagamentoHistorico;
import gcom.arrecadacao.pagamento.PagamentoHistorico;
import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.ContaHistorico;
import gcom.faturamento.conta.ContaMotivoCancelamento;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.gui.ActionServletException;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.*;

/**
 * [UC3165] Gerar Relatório Posição do Débito da Negativação - Legado CAGEPA
 * 
 * @author Luciano Galvão
 * @date 07/03/2015
 */
public class RelatorioPosicaoDebitoNegativacaoLegadoCagepa
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioPosicaoDebitoNegativacaoLegadoCagepa(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_POSICAO_DEBITO_NEGATIVACAO_LEGADO_CAGEPA);
	}

	@Deprecated
	public RelatorioPosicaoDebitoNegativacaoLegadoCagepa() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		Integer anoMesFaturamento = sistemaParametro.getAnoMesFaturamento();

		String mesAnoFaturamento = Util.formatarAnoMesParaMesAno(anoMesFaturamento);

		Collection<ContaHistorico> colecaoContaHistorico = fachada.pesquisarContaEmProcessoNegativacaoCagepa(anoMesFaturamento);

		if(Util.isVazioOrNulo(colecaoContaHistorico)){
			throw new ActionServletException("atencao.nao_ha_dados_geracao_relatorio");
			// throw new RelatorioVazioException("atencao.nao_ha_dados_geracao_relatorio");
		}

		String idImovelStr = "";
		String idImovelAnterior = "";
		Imovel imovel = null;
		Integer idImovel = null;
		int anoMesReferenciaConta = -1;
		String mesAnoReferenciaConta = "";
		BigDecimal valorTotal = null;
		Integer idDebitoCreditoSituacao = null;
		String descricaoDebitoCreditoSituacao = "";
		DebitoCreditoSituacao debitoCreditoSituacaoAtual = null;
		String dataPagamentoStr = "";
		Integer idLocalidade = null;
		Localidade localidade = null;
		FiltroPagamentoHistorico filtroPagamentoHistorico = null;
		Collection<PagamentoHistorico> colecaoPagamentoHistorico = null;
		PagamentoHistorico pagamentoHistorico = null;
		Date dataPagamento = null;
		BigDecimal valorPagamento = null;
		String dataCancelamentoStr = "";
		Date dataCancelamento = null;
		String motivoCancelamento = "";
		ContaMotivoCancelamento contaMotivoCancelamento = null;
		String dataParcelamentoStr = "";
		Parcelamento parcelamento = null;
		Date dataParcelamento = null;

		RelatorioPosicaoDebitoNegativacaoLegadoCagepaBean bean = null;

		for(ContaHistorico contaHistorico : colecaoContaHistorico){
			bean = new RelatorioPosicaoDebitoNegativacaoLegadoCagepaBean();

			// Imóvel
			idImovelStr = "";
			imovel = contaHistorico.getImovel();

			if(imovel != null){
				idImovel = imovel.getId();
				idImovelStr = Integer.toString(idImovel);
			}

			bean.setIdImovel(idImovelStr);

			if(Util.isVazioOuBranco(idImovelAnterior)){
				bean.setIdImovelAnterior("");
			}else{
				bean.setIdImovelAnterior(idImovelAnterior);
			}

			idImovelAnterior = idImovelStr;

			// Referência
			anoMesReferenciaConta = contaHistorico.getAnoMesReferenciaConta();
			mesAnoReferenciaConta = Util.formatarAnoMesParaMesAno(anoMesReferenciaConta);
			bean.setReferenciaConta(mesAnoReferenciaConta);

			// Valor da Conta
			valorTotal = contaHistorico.getValorTotal();
			bean.setValorConta(valorTotal);

			// Situação
			idDebitoCreditoSituacao = null;
			descricaoDebitoCreditoSituacao = "";
			debitoCreditoSituacaoAtual = contaHistorico.getDebitoCreditoSituacaoAtual();

			if(debitoCreditoSituacaoAtual != null){
				idDebitoCreditoSituacao = debitoCreditoSituacaoAtual.getId();
				descricaoDebitoCreditoSituacao = debitoCreditoSituacaoAtual.getDescricaoDebitoCreditoSituacao();
			}

			bean.setSituacao(descricaoDebitoCreditoSituacao);

			// Dados Pagamento
			dataPagamentoStr = "";

			idLocalidade = null;
			localidade = contaHistorico.getLocalidade();

			if(localidade != null){
				idLocalidade = localidade.getId();
			}

			filtroPagamentoHistorico = new FiltroPagamentoHistorico();
			filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.ANO_MES_REFERENCIA_PAGAMENTO,
							anoMesReferenciaConta));
			filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.IMOVEL_ID, idImovelStr));
			filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.LOCALIDADE_ID, idLocalidade));

			colecaoPagamentoHistorico = fachada.pesquisar(filtroPagamentoHistorico, PagamentoHistorico.class.getName());

			if(!Util.isVazioOrNulo(colecaoPagamentoHistorico)){
				pagamentoHistorico = (PagamentoHistorico) Util.retonarObjetoDeColecao(colecaoPagamentoHistorico);

				dataPagamento = pagamentoHistorico.getDataPagamento();

				if(dataPagamento != null){
					dataPagamentoStr = Util.formatarData(dataPagamento);
				}

				valorPagamento = pagamentoHistorico.getValorPagamento();
			}

			bean.setDataPagamento(dataPagamentoStr);
			bean.setValorPagamento(valorPagamento);

			// Dt.Cancelamento
			dataCancelamentoStr = "";
			dataCancelamento = contaHistorico.getDataCancelamento();

			if(dataCancelamento != null){
				dataCancelamentoStr = Util.formatarData(dataCancelamento);
			}

			bean.setDataCancelamento(dataCancelamentoStr);

			// Mot.Cancelamento
			motivoCancelamento = "";
			contaMotivoCancelamento = contaHistorico.getContaMotivoCancelamento();

			if(contaMotivoCancelamento != null){
				motivoCancelamento = contaMotivoCancelamento.getDescricaoMotivoCancelamentoConta();
			}

			bean.setMotivoCancelamento(motivoCancelamento);

			// Dt.Parcelamento
			dataParcelamentoStr = "";

			if(idDebitoCreditoSituacao != null && idDebitoCreditoSituacao.equals(DebitoCreditoSituacao.PARCELADA)){
				parcelamento = contaHistorico.getParcelamento();

				if(parcelamento != null){
					dataParcelamento = parcelamento.getParcelamento();
					dataParcelamentoStr = Util.formatarData(dataParcelamento);
				}
			}

			bean.setDataParcelamento(dataParcelamentoStr);

			relatorioBeans.add(bean);
		}

		Map parametros = new HashMap();
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("mesAnoFaturamento", mesAnoFaturamento);

		RelatorioDataSource dataSource = new RelatorioDataSource(relatorioBeans);

		byte[] retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_POSICAO_DEBITO_NEGATIVACAO_LEGADO_CAGEPA, parametros, dataSource,
						tipoFormatoRelatorio);

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_POSICAO_DEBITO_NEGATIVACAO_LEGADO_CAGEPA, idFuncionalidadeIniciada,
							null);
		}catch(ControladorException ex){
			ex.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", ex);
		}

		return retorno;

	}

	public int calcularTotalRegistrosRelatorio(){

		return -1;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioPosicaoDebitoNegativacaoLegadoCagepa", this);
	}

}
