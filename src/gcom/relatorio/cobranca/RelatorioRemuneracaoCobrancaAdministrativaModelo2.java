
package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.imovel.FiltroImovelCobrancaAdministrivaItem;
import gcom.cadastro.imovel.ImovelCobrancaAdministrivaItem;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaAdministrativaRemuneracaoMensal;
import gcom.cobranca.CobrancaDocumento;
import gcom.cobranca.FiltroCobrancaDocumento;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.ContaHistorico;
import gcom.faturamento.conta.FiltroContaHistorico;
import gcom.faturamento.debito.DebitoACobrarHistorico;
import gcom.faturamento.debito.FiltroDebitoACobrarHistorico;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

import com.ibm.icu.text.DecimalFormat;

public class RelatorioRemuneracaoCobrancaAdministrativaModelo2
				extends TarefaRelatorio {

	public static final String PARAMETRO_INFORMADO_TIPO_RELATORIO = "tipoRelatorio";

	public static final String PARAMETRO_INFORMADO_REFERENCIA_INICIAL = "referenciaInicial";

	public static final String PARAMETRO_INFORMADO_REFERENCIA_FINAL = "referenciaFinal";

	public static final String PARAMETRO_INFORMADO_EMPRESAS = "empresas";

	public static final String PARAMETRO_INFORMADO_SITUACAO_REMUNERACAO = "situacaoRemuneracao";

	public static final String PARAMETRO_INFORMADO_INDICADOR_PAGAMENTO = "indicadorPagamento";

	public static final String FILTRO_ITENS_COBRANCA_ADMINISTRATIVA_DADOS_COMANDOS = "filtroRelatorioDadosComandos";

	public static final String FILTRO_ITENS_COBRANCA_ADMINISTRATIVA = "filtroRelatorio";

	private static final String JASPER_PARAMETRO_IMAGEM = "imagem";

	public static final String FORMATO_RELATORIO = "tipoFormatoRelatorio";

	public RelatorioRemuneracaoCobrancaAdministrativaModelo2(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_REMUNERACAO_COBRANCA_ADMINISTRATIVA_MODELO_2);
	}

	public RelatorioRemuneracaoCobrancaAdministrativaModelo2() {

		super(null, "");
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		FiltroImovelCobrancaAdministrivaItem filtroImovelCobrancaAdministrivaItem = (FiltroImovelCobrancaAdministrivaItem) getParametro(FILTRO_ITENS_COBRANCA_ADMINISTRATIVA);
		int qtd = Fachada.getInstancia().totalRegistrosPesquisa(filtroImovelCobrancaAdministrivaItem,
						ImovelCobrancaAdministrivaItem.class.getName());
		filtroImovelCobrancaAdministrivaItem = (FiltroImovelCobrancaAdministrivaItem) getParametro(FILTRO_ITENS_COBRANCA_ADMINISTRATIVA_DADOS_COMANDOS);
		qtd += Fachada.getInstancia().totalRegistrosPesquisa(filtroImovelCobrancaAdministrivaItem,
						ImovelCobrancaAdministrivaItem.class.getName());
		return qtd;

	}

	@Override
	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		Map<String, Object> parametros = carragarMapaParametrosRelatorio();
		Integer tipoRelatorio = getTipoRelatorio();

		FiltroImovelCobrancaAdministrivaItem filtroImovelCobrancaAdministrivaItem = (FiltroImovelCobrancaAdministrivaItem) getParametro(FILTRO_ITENS_COBRANCA_ADMINISTRATIVA);
		filtroImovelCobrancaAdministrivaItem
						.adicionarCaminhoParaCarregamentoEntidade("imovelCobrancaSituacao.cobrancaAcaoAtividadeComando.empresa");
		filtroImovelCobrancaAdministrivaItem
						.adicionarCaminhoParaCarregamentoEntidade("imovelCobrancaSituacao.cobrancaAcaoAtividadeComando");
		filtroImovelCobrancaAdministrivaItem.adicionarCaminhoParaCarregamentoEntidade("imovelCobrancaSituacao.imovel");
		filtroImovelCobrancaAdministrivaItem.adicionarCaminhoParaCarregamentoEntidade("imovelCobrancaSituacao.cliente");
		filtroImovelCobrancaAdministrivaItem.adicionarCaminhoParaCarregamentoEntidade("cobrancaAdministrativaRemuneracaoMensal");

		Comparator<ImovelCobrancaAdministrivaItem> comparaporId = new Comparator<ImovelCobrancaAdministrivaItem>() {

			public int compare(ImovelCobrancaAdministrivaItem o1, ImovelCobrancaAdministrivaItem o2){

				// TODO Auto-generated method stub
				return o1.getId().compareTo(o2.getId());
			}
		};
		Set<ImovelCobrancaAdministrivaItem> setCobrancaAdministrivaItens = new TreeSet<ImovelCobrancaAdministrivaItem>(comparaporId);
		setCobrancaAdministrivaItens.addAll(Fachada.getInstancia().pesquisar(filtroImovelCobrancaAdministrivaItem,
						ImovelCobrancaAdministrivaItem.class.getName()));

		filtroImovelCobrancaAdministrivaItem = (FiltroImovelCobrancaAdministrivaItem) getParametro(FILTRO_ITENS_COBRANCA_ADMINISTRATIVA_DADOS_COMANDOS);
		filtroImovelCobrancaAdministrivaItem
						.adicionarCaminhoParaCarregamentoEntidade("imovelCobrancaSituacao.cobrancaAcaoAtividadeComando.empresa");
		filtroImovelCobrancaAdministrivaItem
						.adicionarCaminhoParaCarregamentoEntidade("imovelCobrancaSituacao.cobrancaAcaoAtividadeComando");
		filtroImovelCobrancaAdministrivaItem.adicionarCaminhoParaCarregamentoEntidade("imovelCobrancaSituacao.imovel");
		filtroImovelCobrancaAdministrivaItem.adicionarCaminhoParaCarregamentoEntidade("imovelCobrancaSituacao.cliente");
		filtroImovelCobrancaAdministrivaItem.adicionarCaminhoParaCarregamentoEntidade("cobrancaAdministrativaRemuneracaoMensal");

		setCobrancaAdministrivaItens.addAll(Fachada.getInstancia().pesquisar(filtroImovelCobrancaAdministrivaItem,
						ImovelCobrancaAdministrivaItem.class.getName()));

		Comparator<ImovelCobrancaAdministrivaItem> comparator = new Comparator<ImovelCobrancaAdministrivaItem>() {

			public int compare(ImovelCobrancaAdministrivaItem o1, ImovelCobrancaAdministrivaItem o2){

				int retorno = 0;
				retorno = o1.getImovelCobrancaSituacao().getCobrancaAcaoAtividadeComando().getEmpresa().getId()
								.compareTo(o2.getImovelCobrancaSituacao().getCobrancaAcaoAtividadeComando().getEmpresa().getId());
				if(retorno == 0){
					retorno = o1.getAnoMesReferenciaArrecadacao().compareTo(o2.getAnoMesReferenciaArrecadacao());
					if(retorno == 0){
						retorno = o1.getImovelCobrancaSituacao().getCobrancaAcaoAtividadeComando().getId()
										.compareTo(o2.getImovelCobrancaSituacao().getCobrancaAcaoAtividadeComando().getId());
						if(retorno == 0){
							retorno = o1.getDataPagamentoDocumento().compareTo(o2.getDataPagamentoDocumento());
						}
					}
				}
				return retorno; 
			}
		};
		ArrayList<ImovelCobrancaAdministrivaItem> colecaoItens = new ArrayList<ImovelCobrancaAdministrivaItem>();
		colecaoItens.addAll(setCobrancaAdministrivaItens);
		Collections.sort((ArrayList<ImovelCobrancaAdministrivaItem>) colecaoItens, comparator);

		List relatorioBeans = new ArrayList();
		RelatorioRemuneracaoCobrancaAdministrativaModelo2Bean bean = null;
		RelatorioRemuneracaoCobrancaAdministrativaModelo2DetailBean beanDetail = null;
		// int

		for(int i = 0; i < colecaoItens.size();){

			ImovelCobrancaAdministrivaItem imovelCobrancaAdministrivaItem = colecaoItens.get(i);

			bean = new RelatorioRemuneracaoCobrancaAdministrativaModelo2Bean();

			bean.setIdEmpresa(imovelCobrancaAdministrivaItem.getImovelCobrancaSituacao().getCobrancaAcaoAtividadeComando()
							.getEmpresa().getId().toString());
			bean.setDescricaoEmpresa(imovelCobrancaAdministrivaItem.getImovelCobrancaSituacao().getCobrancaAcaoAtividadeComando()
							.getEmpresa().getDescricao());
			bean.setReferenciaArrecadacao(Util.formatarAnoMesParaMesAno(imovelCobrancaAdministrivaItem.getAnoMesReferenciaArrecadacao()));
			bean.setIdLote(imovelCobrancaAdministrivaItem.getImovelCobrancaSituacao().getCobrancaAcaoAtividadeComando().getId().toString());
			bean.setDescricaoTituloLote(imovelCobrancaAdministrivaItem.getImovelCobrancaSituacao().getCobrancaAcaoAtividadeComando()
							.getDescricaoTitulo());
			if(imovelCobrancaAdministrivaItem.getValorRemuneracao() != null){
				bean.setValorRemuneracao(imovelCobrancaAdministrivaItem.getValorRemuneracao().toString());
			}
			if(imovelCobrancaAdministrivaItem.getCobrancaAdministrativaRemuneracaoMensal().getValorArrecadadoLoteAcumulado() != null){
				bean.setValorArrecadadoLoteAcumulado(imovelCobrancaAdministrivaItem.getCobrancaAdministrativaRemuneracaoMensal()
								.getValorArrecadadoLoteAcumulado().toString());
			}
			if(imovelCobrancaAdministrivaItem.getCobrancaAdministrativaRemuneracaoMensal().getValorArrecadadoLoteAcumulado() != null){
				bean.setValorArrecadadoLoteAcumulado(imovelCobrancaAdministrivaItem.getCobrancaAdministrativaRemuneracaoMensal()
								.getValorArrecadadoLoteAcumulado().toString());
			}
			if(imovelCobrancaAdministrivaItem.getCobrancaAdministrativaRemuneracaoMensal().getPercentualDesempenho() != null){
				bean.setPercentualDesempenho(imovelCobrancaAdministrivaItem.getCobrancaAdministrativaRemuneracaoMensal()
								.getPercentualDesempenho().toString());
			}
			if(imovelCobrancaAdministrivaItem.getCobrancaAdministrativaRemuneracaoMensal().getPercentualRemuneracao() != null){
				bean.setPercentualRemuneracao(imovelCobrancaAdministrivaItem.getCobrancaAdministrativaRemuneracaoMensal()
								.getPercentualRemuneracao().toString());
			}
			if(imovelCobrancaAdministrivaItem.getCobrancaAdministrativaRemuneracaoMensal().getValorArrecadadoLote() != null){
				bean.setValorArrecadadoLoteLote(imovelCobrancaAdministrivaItem.getCobrancaAdministrativaRemuneracaoMensal()
								.getValorArrecadadoLote().toString());
			}
			if(imovelCobrancaAdministrivaItem.getCobrancaAdministrativaRemuneracaoMensal().getValorRemuneracao() != null){
				bean.setValorRemuneracaoLote(imovelCobrancaAdministrivaItem.getCobrancaAdministrativaRemuneracaoMensal()
								.getValorRemuneracao().toString());
			}
			if(imovelCobrancaAdministrivaItem.getCobrancaAdministrativaRemuneracaoMensal().getValorArrecadado() != null){
				bean.setValorBaseRemuneracaoLote(imovelCobrancaAdministrivaItem.getCobrancaAdministrativaRemuneracaoMensal()
								.getValorArrecadado().toString());
			}

			Double valorLote = 0.0;
			FiltroCobrancaDocumento filtroCobrancaDocumento = new FiltroCobrancaDocumento();
			filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.ID_COBRANCA_ACAO_ATIVIDADE_COMANDO,
							imovelCobrancaAdministrivaItem.getImovelCobrancaSituacao().getCobrancaAcaoAtividadeComando().getId()));
			Collection<CobrancaDocumento> colecaoCobrancaDocumentos = Fachada.getInstancia().pesquisar(filtroCobrancaDocumento,
							CobrancaDocumento.class.getName());
			for(CobrancaDocumento cobrancaDocumento : colecaoCobrancaDocumentos){
				valorLote += cobrancaDocumento.getValorDocumento().doubleValue() - cobrancaDocumento.getValorAcrescimos().doubleValue()
								+ cobrancaDocumento.getValorDesconto().doubleValue();
			}
			DecimalFormat decimal = new DecimalFormat("0.00");
			bean.setValorLote(decimal.format(valorLote).toString());

			String idEmpresa = bean.getIdEmpresa();
			String referencia = bean.getReferenciaArrecadacao();
			String idLote = bean.getIdLote();

			for(; i < colecaoItens.size(); i++){
				imovelCobrancaAdministrivaItem = colecaoItens.get(i);

				if(!idEmpresa.equals(imovelCobrancaAdministrivaItem.getImovelCobrancaSituacao().getCobrancaAcaoAtividadeComando()
								.getEmpresa().getId().toString())
								|| !referencia.equals(Util.formatarAnoMesParaMesAno(imovelCobrancaAdministrivaItem
												.getAnoMesReferenciaArrecadacao()))
								|| !idLote.equals(imovelCobrancaAdministrivaItem.getImovelCobrancaSituacao()
												.getCobrancaAcaoAtividadeComando().getId().toString())){
					break;
				}

				beanDetail = new RelatorioRemuneracaoCobrancaAdministrativaModelo2DetailBean();

				beanDetail.setDataPagamento(Util.formatarData(imovelCobrancaAdministrivaItem.getDataPagamentoDocumento()));
				beanDetail.setMatricula(imovelCobrancaAdministrivaItem.getImovelCobrancaSituacao().getImovel().getId().toString());
				beanDetail.setCliente(imovelCobrancaAdministrivaItem.getImovelCobrancaSituacao().getCliente().getNome());
				String cpfCnpf = "";
				if(imovelCobrancaAdministrivaItem.getImovelCobrancaSituacao().getCliente().getCpf() != null){
					cpfCnpf = imovelCobrancaAdministrivaItem.getImovelCobrancaSituacao().getCliente().getCpfFormatado();
				}else{
					cpfCnpf = imovelCobrancaAdministrivaItem.getImovelCobrancaSituacao().getCliente().getCnpjFormatado();
				}
				beanDetail.setCpfCnpj(cpfCnpf);
				if(imovelCobrancaAdministrivaItem.getContaGeral() != null){
					Integer idConta = imovelCobrancaAdministrivaItem.getContaGeral().getId();
					FiltroContaHistorico filtroContaHistorico = new FiltroContaHistorico();
					filtroContaHistorico.adicionarParametro(new ParametroSimples(FiltroContaHistorico.ID, idConta));
					ContaHistorico contaHistorico = (ContaHistorico) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(
									filtroContaHistorico, ContaHistorico.class.getName()));
					beanDetail.setConta(Util.formatarAnoMesParaMesAno(contaHistorico.getAnoMesReferenciaConta()));
				}
				if(imovelCobrancaAdministrivaItem.getGuiaPagamentoGeral() != null){
					beanDetail.setGuiaPrestacao(Util.adicionarZerosEsqueda(6, imovelCobrancaAdministrivaItem.getGuiaPagamentoGeral()
									.getId().toString())
									+ "/" + Util.adicionarZerosEsqueda(3, imovelCobrancaAdministrivaItem.getNumeroPrestacao().toString()));
				}
				if(imovelCobrancaAdministrivaItem.getDebitoACobrarGeral() != null){
					Integer idDebitoACobrar = imovelCobrancaAdministrivaItem.getDebitoACobrarGeral().getId();
					FiltroDebitoACobrarHistorico filtroDebitoACobrarHistorico = new FiltroDebitoACobrarHistorico();
					filtroDebitoACobrarHistorico.adicionarParametro(new ParametroSimples(FiltroDebitoACobrarHistorico.ID, idDebitoACobrar));
					DebitoACobrarHistorico debitoACobrarHistorico = (DebitoACobrarHistorico) Util.retonarObjetoDeColecao(Fachada
									.getInstancia().pesquisar(filtroDebitoACobrarHistorico, DebitoACobrarHistorico.class.getName()));
					if(debitoACobrarHistorico != null){
						beanDetail.setDebito(Util.formatarAnoMesParaMesAno(debitoACobrarHistorico.getAnoMesReferenciaDebito()) + " / "
										+ Util.adicionarZerosEsqueda(4, debitoACobrarHistorico.getDebitoTipo().getId().toString()));
					}
				}
				if(imovelCobrancaAdministrivaItem.getValorArrecadadoLote() != null){
					beanDetail.setValorArrecadadoLote(imovelCobrancaAdministrivaItem.getValorArrecadadoLote().toString());
				}
				if(imovelCobrancaAdministrivaItem.getValorBaseRemuneracao() != null){
					beanDetail.setValorBaseRemuneracao(imovelCobrancaAdministrivaItem.getValorBaseRemuneracao().toString());
				}

				bean.getArrayRelatorioRemuneracaoCobrancaAdministrativaModelo2DetailBean().add(beanDetail);

			}

			bean.inicializarArrayJRDetail(bean.getArrayRelatorioRemuneracaoCobrancaAdministrativaModelo2DetailBean());
			relatorioBeans.add(bean);


			if(getParametro(PARAMETRO_INFORMADO_INDICADOR_PAGAMENTO).equals("1")){
				CobrancaAdministrativaRemuneracaoMensal cobrancaAdministrativaRemuneracaoMensal = imovelCobrancaAdministrivaItem
								.getCobrancaAdministrativaRemuneracaoMensal();
				cobrancaAdministrativaRemuneracaoMensal.setIndicadorRemuneracaoPaga(ConstantesSistema.SIM);
				cobrancaAdministrativaRemuneracaoMensal.setDataPagamentoRemuneracao(new Date());
				cobrancaAdministrativaRemuneracaoMensal.setUltimaAlteracao(new Date());
				Fachada.getInstancia().atualizar(cobrancaAdministrativaRemuneracaoMensal);
			}
		}

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		byte[] retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_REMUNERACAO_COBRANCA_ADMINISTRATIVA_MODELO_2, parametros,
 ds,
						tipoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_REMUNERACAO_COBRANCA_ADMINISTRATIVA, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		return retorno;

	}

	private Integer getTipoRelatorio(){

		Integer tipoRelatorio = (Integer) getParametro(FORMATO_RELATORIO);
		tipoRelatorio = tipoRelatorio == null ? TarefaRelatorio.TIPO_PDF : tipoRelatorio;
		return tipoRelatorio;
	}

	private Map<String, Object> carragarMapaParametrosRelatorio(){

		Map<String, Object> parametros = new HashMap<String, Object>();
		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();
		parametros.put(JASPER_PARAMETRO_IMAGEM, sistemaParametro.getImagemRelatorio());
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		// Mapeia os parâmetros
		
		String situacaoReuneracao = "";
		if(getParametro(PARAMETRO_INFORMADO_SITUACAO_REMUNERACAO).equals("1")){
			situacaoReuneracao = "Paga";
		}else if(getParametro(PARAMETRO_INFORMADO_SITUACAO_REMUNERACAO).equals("2")){
			situacaoReuneracao = "Não Paga";
		}else{
			situacaoReuneracao = "Ambas";
		}
		
		String indicadorPagamento = "";
		if(getParametro(PARAMETRO_INFORMADO_INDICADOR_PAGAMENTO).equals("1")){
			indicadorPagamento = "Sim";
		}else{
			indicadorPagamento = "Não";
		}
		
		String empresas = "";
		for(Empresa empresa : ((Collection<Empresa>) getParametro(PARAMETRO_INFORMADO_EMPRESAS))){
			empresas += empresa.getDescricao() + "\n";
		}

		parametros.put("P_TIPO_RELATORIO", getParametro(PARAMETRO_INFORMADO_TIPO_RELATORIO));
		parametros.put("P_REFERENCIA_INICIAL", getParametro(PARAMETRO_INFORMADO_REFERENCIA_INICIAL));
		parametros.put("P_REFERENCIA_FINAL", getParametro(PARAMETRO_INFORMADO_REFERENCIA_FINAL));
		parametros.put("P_SITUACAO_REMUNERACAO", situacaoReuneracao);
		parametros.put("P_INDICADOR_PAGAMENTO", indicadorPagamento);
		parametros.put("P_EMPRESAS", empresas);

		return parametros;
	}

	private String montarDescricaoFiltro(RelatorioRemuneracaoCobrancaAdministrativaHelper parametro){

		StringBuffer buffer = new StringBuffer();
		// TIPO SOLICITACAO
		// buffer.append("\n");
		// buffer.append("Tipo de Solicitação: ");
		// if(parametro.getTiposSolicitacao() == null){
		// buffer.append("TODOS");
		// }else{
		// for(SolicitacaoTipo solicitacao : parametro.getTiposSolicitacao()){
		// buffer.append(solicitacao.getDescricaoComId());
		// buffer.append(", ");
		// }
		// buffer.delete(buffer.length() - 2, buffer.length() - 1);
		// }
		//
		// // ESPECIFICACAO
		// buffer.append("\n");
		// buffer.append("Especificação: ");
		// if(parametro.getEspecificacoes() == null){
		// buffer.append("TODOS");
		// }else{
		// for(SolicitacaoTipoEspecificacao especificacao : parametro.getEspecificacoes()){
		// buffer.append(especificacao.getDescricaoComId());
		// buffer.append(", ");
		// }
		// buffer.delete(buffer.length() - 2, buffer.length() - 1);
		// }
		//
		// // UNIDADE ATUAL
		// buffer.append("\n");
		// buffer.append("Unidade Atual: ");
		// if(parametro.getUnidadeAtual() == null){
		// buffer.append("TODOS");
		// }else{
		// buffer.append(parametro.getUnidadeAtual().getDescricaoComId());
		// }
		//
		// // PERIODO ATENDIMENTO
		// buffer.append("\n");
		// buffer.append("Pedíodo de Atendimento: ");
		// buffer.append(Util.formatarData(parametro.getPeriodoAtendimentoInicial()));
		// buffer.append(" à ");
		// buffer.append(Util.formatarData(parametro.getPeriodoAtendimentoFinal()));
		return buffer.toString();
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa(this.getClass().getName(), this);
	}

}
