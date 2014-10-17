/**
 * 
 */

package gcom.gui.arrecadacao.pagamento;

import gcom.arrecadacao.pagamento.bean.ClassificarLotePagamentosNaoClassificadosHelper;
import gcom.arrecadacao.pagamento.bean.ClassificarPagamentosNaoClassificadosHelper;
import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.DocumentoTipo;
import gcom.fachada.Fachada;
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
 * Action responsável por exibir a página de Classificar em Lote Pagamentos Não Classificados.
 * [UC3080] Classificar em Lote Pagamentos Não Classificados.
 * 
 * @author Josenildo Neves
 * @date 10/12/2012
 */
public class RelatorioClassificarLotePagamentosNaoClassificados
				extends TarefaRelatorio {

	public RelatorioClassificarLotePagamentosNaoClassificados(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_CLASSIFICAR_LOTE_PAGAMENTO_NAO_CLASSIFICADO);
	}

	@Deprecated
	public RelatorioClassificarLotePagamentosNaoClassificados() {

		super(null, "");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.TarefaRelatorio#calcularTotalRegistrosRelatorio()
	 */
	@Override
	public int calcularTotalRegistrosRelatorio(){

		return ((Integer) this.getParametro("totalRegistrosRelatorio")).intValue();
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#agendarTarefaBatch()
	 */
	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioClassificarLotePagamentosNaoClassificados", this);

	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#executar()
	 */
	@Override
	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		Fachada fachada = Fachada.getInstancia();

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		// valor de retorno
		byte[] retorno = null;

		Collection<ClassificarPagamentosNaoClassificadosHelper> colecaoClassificarPagamentosNaoClassificadosHelper = 
						(Collection<ClassificarPagamentosNaoClassificadosHelper>) getParametro("colecaoClassificarPagamentosNaoClassificadosHelper");
		
		ClassificarLotePagamentosNaoClassificadosHelper helperFiltro = (ClassificarLotePagamentosNaoClassificadosHelper) getParametro("classificarLotePagamentosNaoClassificadosHelper");
		
		List<RelatorioClassificarLotePagamentosNaoClassificadosBean> relatorioBeans = new ArrayList<RelatorioClassificarLotePagamentosNaoClassificadosBean>();

		// se a coleção de parâmetros da analise não for vazia
		if(colecaoClassificarPagamentosNaoClassificadosHelper != null && !colecaoClassificarPagamentosNaoClassificadosHelper.isEmpty()){

			// coloca a coleção de parâmetros da analise no iterator

			RelatorioClassificarLotePagamentosNaoClassificadosBean relatorioBean;
			String matriculaImovel = null;
			String inscricaoImovel = null;
			String referenciaFatura = null;
			int sequencial = 0;
			String dataPagamento = null;
			String valorContaFormatado = null;
			String valorCorrigido = null;
			String valorPagamento = null;
			String ocorrencia = null;
			Integer idLocalidade = null;
			Integer idConta = null;
			Integer idPagamento = null;
			BigDecimal valorConta = null;
			String localidade = null;
			
			for(ClassificarPagamentosNaoClassificadosHelper helper : colecaoClassificarPagamentosNaoClassificadosHelper){
				
				if (!Util.isVazioOuBranco(helper.getPagamento().getImovel())){
					matriculaImovel = helper.getPagamento().getImovel().getId().toString();
					inscricaoImovel = helper.getPagamento().getImovel().getInscricaoFormatada();
				}
				
				if(!Util.isVazioOuBranco(helper.getPagamento().getDocumentoTipo())
								&& helper.getPagamento().getDocumentoTipo().getId().equals(DocumentoTipo.GUIA_PAGAMENTO)
								&& !Util.isVazioOuBranco(helper.getPagamento().getGuiaPagamentoGeral())
								&& !Util.isVazioOuBranco(helper.getPagamento().getNumeroPrestacao())){

					referenciaFatura = helper.getPagamento().getGuiaPagamentoGeral().getId() + "/"
									+ helper.getPagamento().getNumeroPrestacao();

				}else{
					if(!Util.isVazioOuBranco(helper.getPagamento().getDocumentoTipo())
									&& helper.getPagamento().getDocumentoTipo().getId().equals(DocumentoTipo.GUIA_PAGAMENTO)
									&& Util.isVazioOuBranco(helper.getPagamento().getGuiaPagamentoGeral())
									&& !Util.isVazioOuBranco(helper.getPagamento().getCliente())){

						referenciaFatura = helper.getPagamento().getCliente().getId().toString();

					}else{
						if(!Util.isVazioOuBranco(helper.getPagamento().getDocumentoTipo())
										&& helper.getPagamento().getDocumentoTipo().getId().equals(DocumentoTipo.DEBITO_A_COBRAR)
										&& !Util.isVazioOuBranco(helper.getPagamento().getDebitoACobrar())
										&& !Util.isVazioOuBranco(helper.getPagamento().getDebitoTipo())){

							referenciaFatura = helper.getPagamento().getDebitoACobrar().getId() + "/"
											+ helper.getPagamento().getDebitoTipo().getId();

						}else{

							if(!Util.isVazioOuBranco(helper.getPagamento().getDocumentoTipo())
											&& helper.getPagamento().getDocumentoTipo().getId().equals(DocumentoTipo.DEBITO_A_COBRAR)
											&& Util.isVazioOuBranco(helper.getPagamento().getDebitoACobrar())
											&& !Util.isVazioOuBranco(helper.getPagamento().getDebitoTipo())){

								referenciaFatura = helper.getPagamento().getDebitoTipo().getId().toString();

							}else{
								if(!Util.isVazioOuBranco(helper.getPagamento().getAnoMesReferenciaPagamento())){

									referenciaFatura = Util.formatarAnoMesParaMesAno(helper.getPagamento().getAnoMesReferenciaPagamento());

								}
							}
						}
					}
				}
				
				sequencial++;
				
				dataPagamento = Util.formatarData(helper.getPagamento().getDataPagamento());
				
				if(!Util.isVazioOuBranco(helper.getConta())){
					valorContaFormatado = Util.formatarMoedaReal(helper.getConta().getValorTotalContaBigDecimal());
					idConta = helper.getConta().getId();
					valorConta = helper.getConta().getValorTotalContaBigDecimal();

				}else{
					if(!Util.isVazioOuBranco(helper.getContaHistorico())){
						valorContaFormatado = Util.formatarMoedaReal(helper.getContaHistorico().getValorTotalSemImpostos());
						idConta = helper.getContaHistorico().getId();
						valorConta = helper.getContaHistorico().getValorTotalSemImpostos();
					}else{
						if(!Util.isVazioOuBranco(helper.getPagamento().getGuiaPagamentoGeral())
										&& !Util.isVazioOuBranco(helper.getPagamento().getGuiaPagamentoGeral().getGuiaPagamento())){
							valorContaFormatado = Util.formatarMoedaReal(helper.getPagamento().getGuiaPagamentoGeral().getGuiaPagamento()
											.getValorDebito());
							valorConta = helper.getPagamento().getGuiaPagamentoGeral().getGuiaPagamento().getValorDebito();
						}
					}
				}
				
				if(helper.getValorDocumentoReajustado() != null){
					valorCorrigido = Util.formatarMoedaReal(helper.getValorDocumentoReajustado());
				}else{
					valorCorrigido = Util.formatarMoedaReal(BigDecimal.ZERO);
				}
				
				valorPagamento = Util.formatarMoedaReal(helper.getPagamento().getValorPagamento());
				
				if(!Util.isVazioOuBranco(helper.getPagamento().getPagamentoSituacaoAtual())){				
					ocorrencia = helper.getPagamento().getPagamentoSituacaoAtual().getId() + " - " + 
												helper.getPagamento().getPagamentoSituacaoAtual().getDescricao();
				}
				
				idLocalidade = helper.getPagamento().getLocalidade().getId();
				localidade = idLocalidade + " - " + helper.getPagamento().getLocalidade().getDescricao();
				idPagamento = helper.getPagamento().getId();

				relatorioBean = new RelatorioClassificarLotePagamentosNaoClassificadosBean(matriculaImovel, inscricaoImovel,
								referenciaFatura, sequencial + "", dataPagamento, valorContaFormatado, valorCorrigido, valorPagamento,
								ocorrencia, idLocalidade, idConta, idPagamento, valorConta, helper.getPagamento().getValorPagamento(),
								null, null, null, localidade);

				// adiciona o bean a coleção
				relatorioBeans.add(relatorioBean);

				// Limpa as vaiáreis
				matriculaImovel = null;
				inscricaoImovel = null;
				referenciaFatura = null;
				dataPagamento = null;
				valorConta = null;
				valorCorrigido = null;
				valorPagamento = null;
				ocorrencia = null;
				idLocalidade = null;
				idConta = null;
				idPagamento = null;
				localidade = null;
			}
		}

		// Parâmetros do relatório
		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		
		if(!Util.isVazioOuBranco(helperFiltro.getLocalidadeInicial())){
			parametros.put("idLocalidadeInicial", helperFiltro.getLocalidadeInicial().toString());
		}else{
			parametros.put("idLocalidadeInicial", "");
		}
		
		if(!Util.isVazioOuBranco(helperFiltro.getLocalidadeFinal())){
			parametros.put("idLocalidadeFinal", helperFiltro.getLocalidadeFinal().toString());
		}else{
			parametros.put("idLocalidadeFinal", "");
		}
		
		if(!Util.isVazioOuBranco(helperFiltro.getReferenciaArrecadacaoInicial())){
			parametros.put("referenciaArrecadacaoInicial", Util.formatarAnoMesParaMesAno(helperFiltro.getReferenciaArrecadacaoInicial()));
		}else{
			parametros.put("referenciaArrecadacaoInicial", "");
		}
		
		if(!Util.isVazioOuBranco(helperFiltro.getReferenciaArrecadacaoFinal())){
			parametros.put("referenciaArrecadacaoFinal",
							Util.formatarAnoMesParaMesAno(helperFiltro.getReferenciaArrecadacaoFinal().toString()));
		}else{
			parametros.put("referenciaArrecadacaoFinal", "");
		}
		

		if(!Util.isVazioOuBranco(helperFiltro.getReferenciaPagamentoInicial())){
			parametros.put("referenciaPagamentoInicial",
							Util.formatarAnoMesParaMesAno(helperFiltro.getReferenciaPagamentoInicial().toString()));
		}else{
			parametros.put("referenciaPagamentoInicial", "");
		}
		
		if(!Util.isVazioOuBranco(helperFiltro.getReferenciaPagamentoFinal())){
			parametros.put("referenciaPagamentoFinal", Util.formatarAnoMesParaMesAno(helperFiltro.getReferenciaPagamentoFinal().toString()));
		}else{
			parametros.put("referenciaPagamentoFinal", "");
		}
		
		parametros.put("limiteMaximoDiferenca", Util.formatarMoedaReal(helperFiltro.getLimiteMaximoDiferenca()));
		
		if(!Util.isVazioOuBranco(helperFiltro.getSituacaoPagamento())){
			parametros.put("pagamentoSituacao", helperFiltro.getSituacaoPagamento() + " - " + helperFiltro.getDescricaoSitucaoPagamento());
		}else{
			parametros.put("pagamentoSituacao", "");
		}

		if(!Util.isVazioOuBranco(helperFiltro.getDataPagamentoInicial())){
			parametros.put("dataPagamentoInicial", Util.formatarData(helperFiltro.getDataPagamentoInicial()));
		}else{
			parametros.put("dataPagamentoInicial", "");
		}

		if(!Util.isVazioOuBranco(helperFiltro.getDataPagamentoFinal())){
			parametros.put("dataPagamentoFinal", Util.formatarData(helperFiltro.getDataPagamentoFinal()));
		}else{
			parametros.put("dataPagamentoFinal", "");
		}

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_CLASSIFICAR_LOTE_PAGAMENTO_NAO_CLASSIFICADO, parametros, ds, tipoFormatoRelatorio);

		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.CLASSIFICAR_LOTE_PAGAMENTO_NAO_CLASSIFICADO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		// retorna o relatório gerado
		return retorno;
	}
}
