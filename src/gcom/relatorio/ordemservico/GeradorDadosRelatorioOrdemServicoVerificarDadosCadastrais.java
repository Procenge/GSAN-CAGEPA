/**
 * 
 */

package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ordemservico.bean.LeituraConsumoHelper;
import gcom.atendimentopublico.ordemservico.bean.RelatorioOrdemServicoVerificarDadosCadastraisBean;
import gcom.micromedicao.bean.HidrometroRelatorioOSHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Geração dos dados do Relatório de Ordem de Serviço de Verificar Dados Cadastrais
 * 
 * @author Anderson Italo
 * @date 24/05/2011
 */
public class GeradorDadosRelatorioOrdemServicoVerificarDadosCadastrais
				extends GeradorDadosRelatorioOrdemServicoEstrutura {

	private static String TITULO_TIPO_SERVICO = "ORDEM DE SERVIÇO";

	/**
	 * Construtor padrão
	 */
	public GeradorDadosRelatorioOrdemServicoVerificarDadosCadastrais() {

		super();
		addParametro(ConstantesSistema.NOME_SUBRELATORIO_ORDEM_SERVICO,
						ConstantesRelatorios.RELATORIO_ORDEM_SERVICO_VERIFICAR_DADO_CADASTRAL);
		addParametro(ConstantesSistema.TITULO_TIPO_SERVICO, TITULO_TIPO_SERVICO);
	}

	/**
	 * Retorna uma coleção com os dados para o detalhe dos relatórios de acordo com o tipo de
	 * serviço
	 * 
	 * @author Anderson Italo
	 * @date 26/05/2011
	 * @throws GeradorRelatorioOrdemServicoException
	 */
	public void gerarDetalhes(DadosRelatorioOrdemServico dadosRelatorio) throws GeradorRelatorioOrdemServicoException{

		Collection<RelatorioOrdemServicoVerificarDadosCadastraisBean> colecao = new ArrayList<RelatorioOrdemServicoVerificarDadosCadastraisBean>();

		if(dadosRelatorio.getIdImovel() != null){
			try{

				RelatorioOrdemServicoVerificarDadosCadastraisBean bean = new RelatorioOrdemServicoVerificarDadosCadastraisBean();

				// Obter dados do Hidrômetro
				HidrometroRelatorioOSHelper hidrometroRelatorioOSHelper = getControladorMicromedicao().obterDadosHidrometroPorImovel(
								Integer.valueOf(dadosRelatorio.getIdImovel()));

				if(hidrometroRelatorioOSHelper != null){
					bean.setCapacidadeHidrometro(hidrometroRelatorioOSHelper.getHidrometroCapacidade());
					bean.setDataInstalacaoHidrometro(hidrometroRelatorioOSHelper.getDataInstalacaoHidrometo());
					bean.setLocalInstalacaoHidrometro(hidrometroRelatorioOSHelper.getHidrometroLocal());
					bean.setMarcaHidrometro(hidrometroRelatorioOSHelper.getHidrometroMarca());
					bean.setNumeroHidrometro(hidrometroRelatorioOSHelper.getHidrometroNumero());

					if(!Util.isVazioOuBranco(hidrometroRelatorioOSHelper.getDescricaoTipoHidrometro())){
						bean.setTipoHidrometro(hidrometroRelatorioOSHelper.getIdTipoHidrometro() + " - "
										+ hidrometroRelatorioOSHelper.getDescricaoTipoHidrometro());
					}

					if(!Util.isVazioOuBranco(hidrometroRelatorioOSHelper.getDescricaoProtecaoHidrometro())){
						bean.setTipoProtecaoHidrometro(hidrometroRelatorioOSHelper.getIdProtecaoHidrometro() + " - "
										+ hidrometroRelatorioOSHelper.getDescricaoProtecaoHidrometro());
					}

					if(!Util.isVazioOuBranco(hidrometroRelatorioOSHelper.getHidrometroDiametro())){
						bean.setDiametroHidrometro(hidrometroRelatorioOSHelper.getHidrometroDiametro());
					}
				}

				// Obter dados das leituras do Seis ùltimos meses
				Collection<LeituraConsumoHelper> listaLeituraConsumoHelpers = getControladorMicromedicao().obterDadosLeituraEConsumo(
								Integer.valueOf(dadosRelatorio.getIdImovel()), 6);

				if(!Util.isVazioOrNulo(listaLeituraConsumoHelpers)){

					int i = 1;

					Iterator<LeituraConsumoHelper> iterLeituraConsumoHelpers = listaLeituraConsumoHelpers.iterator();

					while(iterLeituraConsumoHelpers.hasNext()){

						LeituraConsumoHelper leituraConsumoHelper = iterLeituraConsumoHelpers.next();

						switch(i){
							case 1:
								if(leituraConsumoHelper.getAnoMesReferenciaMedicaoHistorico() != null){
									bean.setReferencia1(Util.formatarAnoMesParaMesAno(leituraConsumoHelper
													.getAnoMesReferenciaMedicaoHistorico()));
								}

								if(leituraConsumoHelper.getLeituraAtualFaturamentoMedicaoHistorico() != null){
									bean.setLeitura1(leituraConsumoHelper.getLeituraAtualFaturamentoMedicaoHistorico().toString());
								}

								if(leituraConsumoHelper.getDescricaoConsumoAnormalidade() != null){
									bean.setAnormalidade1(leituraConsumoHelper.getDescricaoConsumoAnormalidade());
								}

								if(leituraConsumoHelper.getIdLeituraAnormalidade() != null){
									bean.setAnormalidadeLeitura1(leituraConsumoHelper.getIdLeituraAnormalidade().toString());
								}

								break;

							case 2:
								if(leituraConsumoHelper.getAnoMesReferenciaMedicaoHistorico() != null){
									bean.setReferencia2(Util.formatarAnoMesParaMesAno(leituraConsumoHelper
													.getAnoMesReferenciaMedicaoHistorico()));
								}

								if(leituraConsumoHelper.getLeituraAtualFaturamentoMedicaoHistorico() != null){
									bean.setLeitura2(leituraConsumoHelper.getLeituraAtualFaturamentoMedicaoHistorico().toString());
								}

								if(leituraConsumoHelper.getDescricaoConsumoAnormalidade() != null){
									bean.setAnormalidade2(leituraConsumoHelper.getDescricaoConsumoAnormalidade());
								}

								if(leituraConsumoHelper.getIdLeituraAnormalidade() != null){
									bean.setAnormalidadeLeitura2(leituraConsumoHelper.getIdLeituraAnormalidade().toString());
								}

								break;

							case 3:
								if(leituraConsumoHelper.getAnoMesReferenciaMedicaoHistorico() != null){
									bean.setReferencia3(Util.formatarAnoMesParaMesAno(leituraConsumoHelper
													.getAnoMesReferenciaMedicaoHistorico()));
								}

								if(leituraConsumoHelper.getLeituraAtualFaturamentoMedicaoHistorico() != null){
									bean.setLeitura3(leituraConsumoHelper.getLeituraAtualFaturamentoMedicaoHistorico().toString());
								}

								if(leituraConsumoHelper.getDescricaoConsumoAnormalidade() != null){
									bean.setAnormalidade3(leituraConsumoHelper.getDescricaoConsumoAnormalidade());
								}

								if(leituraConsumoHelper.getIdLeituraAnormalidade() != null){
									bean.setAnormalidadeLeitura3(leituraConsumoHelper.getIdLeituraAnormalidade().toString());
								}

								break;
							case 4:
								if(leituraConsumoHelper.getAnoMesReferenciaMedicaoHistorico() != null){
									bean.setReferencia4(Util.formatarAnoMesParaMesAno(leituraConsumoHelper
													.getAnoMesReferenciaMedicaoHistorico()));
								}

								if(leituraConsumoHelper.getLeituraAtualFaturamentoMedicaoHistorico() != null){
									bean.setLeitura4(leituraConsumoHelper.getLeituraAtualFaturamentoMedicaoHistorico().toString());
								}

								if(leituraConsumoHelper.getDescricaoConsumoAnormalidade() != null){
									bean.setAnormalidade4(leituraConsumoHelper.getDescricaoConsumoAnormalidade());
								}

								if(leituraConsumoHelper.getIdLeituraAnormalidade() != null){
									bean.setAnormalidadeLeitura4(leituraConsumoHelper.getIdLeituraAnormalidade().toString());
								}

								break;
							case 5:
								if(leituraConsumoHelper.getAnoMesReferenciaMedicaoHistorico() != null){
									bean.setReferencia5(Util.formatarAnoMesParaMesAno(leituraConsumoHelper
													.getAnoMesReferenciaMedicaoHistorico()));
								}

								if(leituraConsumoHelper.getLeituraAtualFaturamentoMedicaoHistorico() != null){
									bean.setLeitura5(leituraConsumoHelper.getLeituraAtualFaturamentoMedicaoHistorico().toString());
								}

								if(leituraConsumoHelper.getDescricaoConsumoAnormalidade() != null){
									bean.setAnormalidade5(leituraConsumoHelper.getDescricaoConsumoAnormalidade());
								}

								if(leituraConsumoHelper.getIdLeituraAnormalidade() != null){
									bean.setAnormalidadeLeitura5(leituraConsumoHelper.getIdLeituraAnormalidade().toString());
								}

								break;
							case 6:
								if(leituraConsumoHelper.getAnoMesReferenciaMedicaoHistorico() != null){
									bean.setReferencia6(Util.formatarAnoMesParaMesAno(leituraConsumoHelper
													.getAnoMesReferenciaMedicaoHistorico()));
								}

								if(leituraConsumoHelper.getLeituraAtualFaturamentoMedicaoHistorico() != null){
									bean.setLeitura6(leituraConsumoHelper.getLeituraAtualFaturamentoMedicaoHistorico().toString());
								}

								if(leituraConsumoHelper.getDescricaoConsumoAnormalidade() != null){
									bean.setAnormalidade6(leituraConsumoHelper.getDescricaoConsumoAnormalidade());
								}

								if(leituraConsumoHelper.getIdLeituraAnormalidade() != null){
									bean.setAnormalidadeLeitura6(leituraConsumoHelper.getIdLeituraAnormalidade().toString());
								}

								break;

							default:
								break;
						}

						i++;
					}
				}

				// Obter dados das contas em revisão
				Collection<DadosContaEmRevisaoHelper> contasEmRevisaoHelpers = getControladorFaturamento().pesquisarDadosContasEmRevisao(
								Integer.valueOf(dadosRelatorio.getIdImovel()));

				if(!Util.isVazioOrNulo(contasEmRevisaoHelpers)){

					for(Iterator iterator = contasEmRevisaoHelpers.iterator(); iterator.hasNext();){
						DadosContaEmRevisaoHelper dadosContaEmRevisaoHelper = (DadosContaEmRevisaoHelper) iterator.next();

						if(iterator.hasNext() || contasEmRevisaoHelpers.size() == 1){

							if(dadosContaEmRevisaoHelper.getFatura() > 0){
								bean.setFaturaConta1(Util.formatarAnoMesParaMesAno(dadosContaEmRevisaoHelper.getFatura()));
							}

							if(dadosContaEmRevisaoHelper.getVencimento() != null){
								bean.setVencimentoConta1(Util.formatarData(dadosContaEmRevisaoHelper.getVencimento()));
							}

							if(dadosContaEmRevisaoHelper.getValorTotal() != null){
								bean.setValorConta1(Util.formatarMoedaReal(dadosContaEmRevisaoHelper.getValorTotal(), 2));
							}

							if(dadosContaEmRevisaoHelper.getDescricaoMotivo() != null){
								bean.setMotivoRevisaoConta1(dadosContaEmRevisaoHelper.getIdMotivo().toString() + "-"
												+ dadosContaEmRevisaoHelper.getDescricaoMotivo());
							}

						}else{

							if(dadosContaEmRevisaoHelper.getFatura() > 0){
								bean.setFaturaConta2(Util.formatarAnoMesParaMesAno(dadosContaEmRevisaoHelper.getFatura()));
							}

							if(dadosContaEmRevisaoHelper.getVencimento() != null){
								bean.setVencimentoConta2(Util.formatarData(dadosContaEmRevisaoHelper.getVencimento()));
							}

							if(dadosContaEmRevisaoHelper.getValorTotal() != null){
								bean.setValorConta2(Util.formatarMoedaReal(dadosContaEmRevisaoHelper.getValorTotal(), 2));
							}

							if(dadosContaEmRevisaoHelper.getDescricaoMotivo() != null){
								bean.setMotivoRevisaoConta2(dadosContaEmRevisaoHelper.getIdMotivo().toString() + "-"
												+ dadosContaEmRevisaoHelper.getDescricaoMotivo());
							}
						}

					}

				}

				colecao.add(bean);

			}catch(NumberFormatException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(ControladorException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		dadosRelatorio.setarBeansDetalheEstrutura(colecao);
	}

	@Override
	public void validarExibicaoRodape(DadosRelatorioOrdemServico dadosRelatorio) throws GeradorRelatorioOrdemServicoException{

		dadosRelatorio.setExibirCodigoBarras("false");

	}
}
