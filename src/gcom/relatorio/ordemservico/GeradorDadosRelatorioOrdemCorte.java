
package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.IRepositorioClienteImovel;
import gcom.cadastro.cliente.RepositorioClienteImovelHBM;
import gcom.cadastro.endereco.FiltroLogradouroBairro;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaDocumento;
import gcom.cobranca.CobrancaDocumentoItem;
import gcom.cobranca.IRepositorioCobranca;
import gcom.cobranca.RepositorioCobrancaHBM;
import gcom.cobranca.bean.EmitirOrdemCorteCobrancaHelper;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author isilva
 * @date 14/09/2010
 */
public class GeradorDadosRelatorioOrdemCorte
				extends GeradorDadosRelatorioOrdemServico {

	private static final long serialVersionUID = 1L;

	private IRepositorioCobranca repositorioCobranca = null;

	private IRepositorioClienteImovel repositorioClienteImovel = null;

	public GeradorDadosRelatorioOrdemCorte() {

		super();
		repositorioCobranca = RepositorioCobrancaHBM.getInstancia();
		repositorioClienteImovel = RepositorioClienteImovelHBM.getInstancia();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(Collection<OrdemServico> ordensServicos)
					throws GeradorRelatorioOrdemServicoException{

		List<DadosRelatorioOrdemServico> collRelatorioOrdemCorteBean = new ArrayList<DadosRelatorioOrdemServico>();

		List<EmitirOrdemCorteCobrancaHelper> colecaoEmitirOrdemCobrancaHelper = (List<EmitirOrdemCorteCobrancaHelper>) this
						.emitirOrdemCorte(ordensServicos);
		int totalPaginas = colecaoEmitirOrdemCobrancaHelper.size();

		List<EmitirOrdemCorteCobrancaHelper> colecaoOrdenada = (List<EmitirOrdemCorteCobrancaHelper>) this
						.dividirColecaoOrdenando(colecaoEmitirOrdemCobrancaHelper);

		EmitirOrdemCorteCobrancaHelper primeiroHelper = null;
		EmitirOrdemCorteCobrancaHelper segundoHelper = null;

		int pagina1 = 1;
		int pagina2 = 0;

		if((totalPaginas % 2) == 0){
			pagina2 = (totalPaginas / 2) + 1;
		}else{
			pagina2 = (totalPaginas / 2) + 2;
		}

		for(EmitirOrdemCorteCobrancaHelper helper : colecaoOrdenada){

			if((primeiroHelper == null) && (segundoHelper == null)){
				primeiroHelper = helper;
				primeiroHelper.setNumeroPagina("" + pagina1);
				pagina1++;
			}else{
				segundoHelper = helper;
				segundoHelper.setNumeroPagina("" + pagina2);
				pagina2++;
			}

			if((primeiroHelper != null) && (segundoHelper != null)){

				collRelatorioOrdemCorteBean.add(new DadosRelatorioOrdemServico(primeiroHelper, segundoHelper));

				primeiroHelper = null;
				segundoHelper = null;
			}
		}

		if(primeiroHelper != null && segundoHelper == null){
			collRelatorioOrdemCorteBean.add(new DadosRelatorioOrdemServico(primeiroHelper, segundoHelper));
		}

		return collRelatorioOrdemCorteBean;
	}

	@Override
	public List<DadosRelatorioOrdemServico> gerarDados(OrdemServico os) throws GeradorRelatorioOrdemServicoException{

		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Este caso de uso Gera os Relatórios das Ordens de Cortes
	 * 
	 * @author isilva
	 * @data 14/10/2010
	 * @param listPesquisaOrdemServico
	 * @param usuario
	 * @return
	 * @throws ControladorException
	 */
	public List<EmitirOrdemCorteCobrancaHelper> emitirOrdemCorte(Collection<OrdemServico> listPesquisaOrdemServico){

		List<EmitirOrdemCorteCobrancaHelper> colecaoEmitirOrdemCobrancaHelper = new ArrayList<EmitirOrdemCorteCobrancaHelper>();

		CobrancaAcao cobrancaAcao = null;

		if(listPesquisaOrdemServico != null && !listPesquisaOrdemServico.isEmpty()){

			OrdemServico ordem = (OrdemServico) Util.retonarObjetoDeColecao(listPesquisaOrdemServico);

			if(ordem.getCobrancaAcaoAtividadeCronograma() != null && ordem.getCobrancaAcaoAtividadeCronograma().getId() != null){
				if(ordem.getCobrancaAcaoAtividadeCronograma().getCobrancaAcaoCronograma() != null){
					cobrancaAcao = ordem.getCobrancaAcaoAtividadeCronograma().getCobrancaAcaoCronograma().getCobrancaAcao();
				}
			}
			if(ordem.getCobrancaAcaoAtividadeComando() != null && ordem.getCobrancaAcaoAtividadeComando().getId() != null){
				cobrancaAcao = ordem.getCobrancaAcaoAtividadeComando().getCobrancaAcao();
			}

			for(OrdemServico ordemServico : listPesquisaOrdemServico){

				EmitirOrdemCorteCobrancaHelper emitirOrdemCobrancaHelper = new EmitirOrdemCorteCobrancaHelper();

				// ****************************************************
				// carregando os dados no helper do relatorio de aviso de debito
				emitirOrdemCobrancaHelper.setMatricula(ordemServico.getImovel().getIdParametrizado());

				String nomeCliente = null;
				try{
					nomeCliente = repositorioClienteImovel.retornaNomeCliente(ordemServico.getImovel().getId(), ClienteRelacaoTipo.USUARIO);
				}catch(ErroRepositorioException ex){
					ex.printStackTrace();
					try{
						throw new GeradorRelatorioOrdemServicoException(ex.getMessage(), ex);
					}catch(GeradorRelatorioOrdemServicoException e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				emitirOrdemCobrancaHelper.setNomeCliente(nomeCliente);

				String endereco = "";

				try{
					endereco = this.getControladorOrdemServico().obterEnderecoCompletoOS(ordemServico.getId());
				}catch(ControladorException e1){
					e1.printStackTrace();
					try{
						throw new GeradorRelatorioOrdemServicoException(e1.getMessage(), e1);
					}catch(GeradorRelatorioOrdemServicoException e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				emitirOrdemCobrancaHelper.setEndereco(endereco);

				// RA
				if(ordemServico.getRegistroAtendimento() != null){
					emitirOrdemCobrancaHelper.setRa(ordemServico.getRegistroAtendimento().getId().toString());
				}else{
					emitirOrdemCobrancaHelper.setRa("");
				}

				// Número da OS
				// emitirOrdemCobrancaHelper.setNumero(ordemServico.getId().toString());
				emitirOrdemCobrancaHelper.setNumero(Util.adicionarZerosEsquedaNumero(
								ConstantesSistema.TAMANHO_MINIMO_CODIGO_BARRAS_RELATORIO_ORDEM_SERVICO, ordemServico.getId().toString()));

				// RA
				if(ordemServico.getServicoTipo() != null){
					emitirOrdemCobrancaHelper.setCodDescricaoServico(ordemServico.getServicoTipo().getId().toString() + " / "
									+ ordemServico.getServicoTipo().getDescricao());
				}else{
					emitirOrdemCobrancaHelper.setCodDescricaoServico("");
				}

				LogradouroBairro logradouroBairro = null;

				try{
					if(ordemServico.getImovel() != null && ordemServico.getImovel().getLogradouroBairro() != null){
						FiltroLogradouroBairro filtroLogradouroBairro = new FiltroLogradouroBairro();
						filtroLogradouroBairro.adicionarParametro(new ParametroSimples(FiltroLogradouroBairro.ID, ordemServico.getImovel()
										.getLogradouroBairro().getId()));
						filtroLogradouroBairro.adicionarCaminhoParaCarregamentoEntidade(FiltroLogradouroBairro.BAIRRO);
						logradouroBairro = (LogradouroBairro) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
										filtroLogradouroBairro, LogradouroBairro.class.getName()));
					}

				}catch(ControladorException e1){
					e1.printStackTrace();
					try{
						throw new GeradorRelatorioOrdemServicoException(e1.getMessage(), e1);
					}catch(GeradorRelatorioOrdemServicoException e){
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if(logradouroBairro != null){
					emitirOrdemCobrancaHelper.setBairro(logradouroBairro.getBairro().getNome());
				}

				if(ordemServico.getImovel().getLigacaoAgua() != null
								&& ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico() != null){

					if(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getNumeroHidrometro() != null){

						emitirOrdemCobrancaHelper.setHidrometro(ordemServico.getImovel().getLigacaoAgua()
										.getHidrometroInstalacaoHistorico().getNumeroHidrometro());
					}else if(ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro() != null){

						emitirOrdemCobrancaHelper.setHidrometro(ordemServico.getImovel().getLigacaoAgua()
										.getHidrometroInstalacaoHistorico().getHidrometro().getNumero());
					}else{
						try{
							HidrometroInstalacaoHistorico hidi = (HidrometroInstalacaoHistorico) getControladorUtil().pesquisar(
											ordemServico.getImovel().getLigacaoAgua().getHidrometroInstalacaoHistorico().getId(),
											HidrometroInstalacaoHistorico.class, false);
							emitirOrdemCobrancaHelper.setHidrometro(hidi.getNumeroHidrometro());
						}catch(ControladorException e){
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}else{
					emitirOrdemCobrancaHelper.setHidrometro("");
				}

				emitirOrdemCobrancaHelper.setInscricao(ordemServico.getImovel().getInscricaoFormatada());

				Calendar dataAtual = Calendar.getInstance();
				Integer horas = dataAtual.get(Calendar.HOUR_OF_DAY);
				Integer minutos = dataAtual.get(Calendar.MINUTE);
				Integer segundos = dataAtual.get(Calendar.SECOND);
				emitirOrdemCobrancaHelper.setHoraImpressao("" + horas + ":" + minutos + ":" + segundos);
				emitirOrdemCobrancaHelper.setDataImpressao(Util.formatarData(dataAtual.getTime()));

				if(cobrancaAcao != null && cobrancaAcao.getNumeroDiasValidade() != null){
					Util
									.adicionarNumeroDiasDeUmaData(dataAtual.getTime(), Integer
													.valueOf(cobrancaAcao.getNumeroDiasValidade()).intValue());
					emitirOrdemCobrancaHelper.setDataComparecimento(Util.formatarData(dataAtual.getTime()));
				}

				// obtendo o codigo de barras
				String representacaoNumericaCodBarra = ordemServico.getId().toString();
				emitirOrdemCobrancaHelper.setRepresentacaoNumericaCodBarraFormatada(representacaoNumericaCodBarra);
				emitirOrdemCobrancaHelper.setRepresentacaoNumericaCodBarraSemDigito(representacaoNumericaCodBarra);
				// **********************************************************

				CobrancaDocumento cobrancaDocumento = ordemServico.getCobrancaDocumento();

				if(cobrancaDocumento != null){

					Collection colecaoCobrancaDocumentoItemConta = null;
					// Collection colecaoCobrancaDocumentoItemGuiaPagamento = null;
					// Collection<CobrancaDocumentoItem> colecaoCobrancaDocumentoDebitoACobrar =
					// null;
					BigDecimal valorTotal = BigDecimal.ZERO;
					BigDecimal debitosAnteriores = BigDecimal.ZERO;

					if(cobrancaDocumento != null){
						try{
							// pesquisa todas as contas, debitos e guias
							colecaoCobrancaDocumentoItemConta = this.repositorioCobranca
											.selecionarCobrancaDocumentoItemReferenteConta(ordemServico.getCobrancaDocumento());

							// colecaoCobrancaDocumentoItemGuiaPagamento = this.repositorioCobranca
							// .selecionarDadosCobrancaDocumentoItemReferenteGuiaPagamento(ordemServico.getCobrancaDocumento());

							// colecaoCobrancaDocumentoDebitoACobrar = this.repositorioCobranca
							// .selecionarCobrancaDocumentoItemReferenteDebitoACobrar(ordemServico.getCobrancaDocumento());

						}catch(ErroRepositorioException ex){
							ex.printStackTrace();
							try{
								throw new GeradorRelatorioOrdemServicoException(ex.getMessage(), ex);
							}catch(GeradorRelatorioOrdemServicoException e){
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						// contas
						int limitador15Itens = 1;
						Collection<String> mesAno = new ArrayList<String>();
						Collection<String> vencimento = new ArrayList<String>();
						Collection<BigDecimal> valor = new ArrayList<BigDecimal>();

						Iterator<CobrancaDocumentoItem> itContas = colecaoCobrancaDocumentoItemConta.iterator();
						Integer mesAnoMaisAnterior = 300012;
						boolean teveConta = false;
						while(itContas.hasNext()){
							CobrancaDocumentoItem cobrancaDocumentoItem = itContas.next();
							teveConta = true;
							if(limitador15Itens <= 15){
								mesAno.add(Util.formatarAnoMesSemBarraParaMesAnoComBarra(cobrancaDocumentoItem.getContaGeral().getConta()
												.getReferencia()));
								vencimento
												.add(Util.formatarData(cobrancaDocumentoItem.getContaGeral().getConta()
																.getDataVencimentoConta()));
								valor.add(cobrancaDocumentoItem.getContaGeral().getConta().getValorTotal());
							}else{
								debitosAnteriores = debitosAnteriores.add(cobrancaDocumentoItem.getContaGeral().getConta().getValorTotal());
							}
							if(Util.compararAnoMesReferencia(cobrancaDocumentoItem.getContaGeral().getConta().getReferencia(),
											mesAnoMaisAnterior, "<")){
								mesAnoMaisAnterior = cobrancaDocumentoItem.getContaGeral().getConta().getReferencia();
							}
							valorTotal = valorTotal.add(cobrancaDocumentoItem.getContaGeral().getConta().getValorTotal());
							limitador15Itens++;
						}

						if(teveConta){
							emitirOrdemCobrancaHelper.setDataDebitoAnterior(Util
											.formatarAnoMesSemBarraParaMesAnoComBarra(mesAnoMaisAnterior));
						}
						emitirOrdemCobrancaHelper.setMesAno(mesAno);
						emitirOrdemCobrancaHelper.setVencimento(vencimento);
						emitirOrdemCobrancaHelper.setValor(valor);

						// guias
						// Iterator<CobrancaDocumentoItem> itGuias =
						// colecaoCobrancaDocumentoItemGuiaPagamento.iterator();
						// while (itGuias.hasNext()) {
						// CobrancaDocumentoItem cobrancaDocumentoItem = itGuias.next();
						// valorTotal = valorTotal.add(cobrancaDocumentoItem.getValorItemCobrado());
						// debitosAnteriores =
						// debitosAnteriores.add(cobrancaDocumentoItem.getValorItemCobrado());
						// }
						//
						// //debitos a cobrar
						// Iterator<CobrancaDocumentoItem> itDebACob =
						// colecaoCobrancaDocumentoDebitoACobrar.iterator();
						// while (itDebACob.hasNext()) {
						// CobrancaDocumentoItem cobrancaDocumentoItem = itGuias.next();
						// valorTotal =
						// valorTotal.add(cobrancaDocumentoItem.getDebitoACobrarGeral().getDebitoACobrar().getValorTotal());
						// debitosAnteriores =
						// debitosAnteriores.add(cobrancaDocumentoItem.getDebitoACobrarGeral().getDebitoACobrar()
						// .getValorTotal());
						// }
					}

					emitirOrdemCobrancaHelper.setValorDebitosAnteriores(debitosAnteriores);
					emitirOrdemCobrancaHelper.setValorTotal(valorTotal);
				}

				colecaoEmitirOrdemCobrancaHelper.add(emitirOrdemCobrancaHelper);
				emitirOrdemCobrancaHelper = null;
			}

		}
		return colecaoEmitirOrdemCobrancaHelper;
	}
}
