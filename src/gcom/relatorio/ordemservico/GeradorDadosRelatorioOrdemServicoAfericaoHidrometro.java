/**
 * 
 */

package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ordemservico.bean.LeituraConsumoHelper;
import gcom.atendimentopublico.ordemservico.bean.RelatorioOrdemServicoAfericaoHidrometroBean;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.micromedicao.bean.HidrometroRelatorioOSHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * @author isilva
 */
public class GeradorDadosRelatorioOrdemServicoAfericaoHidrometro
				extends GeradorDadosRelatorioOrdemServicoEstrutura {

	private static String TITULO_TIPO_SERVICO = "AFERIÇÃO DE HIDRÔMETRO";

	/**
	 * Construtor padrão
	 */
	public GeradorDadosRelatorioOrdemServicoAfericaoHidrometro() {

		super();
		addParametro(ConstantesSistema.NOME_SUBRELATORIO_ORDEM_SERVICO, ConstantesRelatorios.RELATORIO_ORDEM_SERVICO_AFERICAO_HIDROMETRO);
		addParametro(ConstantesSistema.TITULO_TIPO_SERVICO, TITULO_TIPO_SERVICO);
	}

	/**
	 * @author isilva
	 * @param os
	 *            A ordem de serviço
	 * @return Uma coleção com os dados para o detalhe dos relatórios de acordo com o tipo de
	 *         servico
	 * @throws GeradorRelatorioOrdemServicoException
	 */
	public void gerarDetalhes(DadosRelatorioOrdemServico dadosRelatorio) throws GeradorRelatorioOrdemServicoException{

		Collection<RelatorioOrdemServicoAfericaoHidrometroBean> colecao = new ArrayList<RelatorioOrdemServicoAfericaoHidrometroBean>();

		if(dadosRelatorio.getIdImovel() != null){
			try{
				HidrometroRelatorioOSHelper hidrometroRelatorioOSHelper = getControladorMicromedicao().obterDadosHidrometroPorImovel(
								Integer.valueOf(dadosRelatorio.getIdImovel()));

				RelatorioOrdemServicoAfericaoHidrometroBean relatorioOrdemServicoAfericaoHidrometroBean = new RelatorioOrdemServicoAfericaoHidrometroBean();

				if(hidrometroRelatorioOSHelper != null){
					relatorioOrdemServicoAfericaoHidrometroBean.setCapacidadeHidrometro(hidrometroRelatorioOSHelper
									.getHidrometroCapacidade());
					relatorioOrdemServicoAfericaoHidrometroBean.setDataInstalacaoHidrometro(hidrometroRelatorioOSHelper
									.getDataInstalacaoHidrometo());
					relatorioOrdemServicoAfericaoHidrometroBean.setLocalInstalacaoHidrometro(hidrometroRelatorioOSHelper
									.getHidrometroLocal());
					relatorioOrdemServicoAfericaoHidrometroBean.setMarcaHidrometro(hidrometroRelatorioOSHelper.getHidrometroMarca());
					relatorioOrdemServicoAfericaoHidrometroBean.setNumeroHidrometro(hidrometroRelatorioOSHelper.getHidrometroNumero());

					if(!Util.isVazioOuBranco(hidrometroRelatorioOSHelper.getDescricaoTipoHidrometro())){
						relatorioOrdemServicoAfericaoHidrometroBean.setTipoHidrometro(hidrometroRelatorioOSHelper.getIdTipoHidrometro()
										+ " - " + hidrometroRelatorioOSHelper.getDescricaoTipoHidrometro());
					}

					if(!Util.isVazioOuBranco(hidrometroRelatorioOSHelper.getDescricaoProtecaoHidrometro())){
						relatorioOrdemServicoAfericaoHidrometroBean.setTipoProtecaoHidrometro(hidrometroRelatorioOSHelper
										.getIdProtecaoHidrometro()
										+ " - " + hidrometroRelatorioOSHelper.getDescricaoProtecaoHidrometro());
					}

					if(!Util.isVazioOuBranco(hidrometroRelatorioOSHelper.getHidrometroDiametro())){
						relatorioOrdemServicoAfericaoHidrometroBean.setDiametroHidrometro(hidrometroRelatorioOSHelper
										.getHidrometroDiametro());
					}

				}

				Collection<LeituraConsumoHelper> listaLeituraConsumoHelpers = getControladorMicromedicao().obterDadosLeituraEConsumo(
								Integer.valueOf(dadosRelatorio.getIdImovel()), 3);

				if(!Util.isVazioOrNulo(listaLeituraConsumoHelpers)){

					int i = 1;

					Iterator<LeituraConsumoHelper> iterLeituraConsumoHelpers = listaLeituraConsumoHelpers.iterator();

					while(iterLeituraConsumoHelpers.hasNext()){

						LeituraConsumoHelper leituraConsumoHelper = iterLeituraConsumoHelpers.next();

						switch(i){
							case 1:
								if(leituraConsumoHelper.getAnoMesReferenciaMedicaoHistorico() != null){
									relatorioOrdemServicoAfericaoHidrometroBean.setReferecia1(Util
													.formatarAnoMesParaMesAno(leituraConsumoHelper.getAnoMesReferenciaMedicaoHistorico()));
								}

								if(leituraConsumoHelper.getLeituraAtualFaturamentoMedicaoHistorico() != null){
									relatorioOrdemServicoAfericaoHidrometroBean.setLeitura1(leituraConsumoHelper
													.getLeituraAtualFaturamentoMedicaoHistorico().toString());
								}

								if(leituraConsumoHelper.getNumeroConsumoFaturadoMesConsumoHistorico() != null){
									relatorioOrdemServicoAfericaoHidrometroBean.setConsumo1(leituraConsumoHelper
													.getNumeroConsumoFaturadoMesConsumoHistorico().toString());
								}

								if(leituraConsumoHelper.getDescricaoConsumoAnormalidade() != null){
									relatorioOrdemServicoAfericaoHidrometroBean.setAnormalidade1(leituraConsumoHelper
													.getDescricaoConsumoAnormalidade());
								}

								break;

							case 2:
								if(leituraConsumoHelper.getAnoMesReferenciaMedicaoHistorico() != null){
									relatorioOrdemServicoAfericaoHidrometroBean.setReferecia2(Util
													.formatarAnoMesParaMesAno(leituraConsumoHelper.getAnoMesReferenciaMedicaoHistorico()));
								}

								if(leituraConsumoHelper.getLeituraAtualFaturamentoMedicaoHistorico() != null){
									relatorioOrdemServicoAfericaoHidrometroBean.setLeitura2(leituraConsumoHelper
													.getLeituraAtualFaturamentoMedicaoHistorico().toString());
								}

								if(leituraConsumoHelper.getNumeroConsumoFaturadoMesConsumoHistorico() != null){
									relatorioOrdemServicoAfericaoHidrometroBean.setConsumo2(leituraConsumoHelper
													.getNumeroConsumoFaturadoMesConsumoHistorico().toString());
								}

								if(leituraConsumoHelper.getDescricaoConsumoAnormalidade() != null){
									relatorioOrdemServicoAfericaoHidrometroBean.setAnormalidade2(leituraConsumoHelper
													.getDescricaoConsumoAnormalidade());
								}

								break;

							case 3:
								if(leituraConsumoHelper.getAnoMesReferenciaMedicaoHistorico() != null){
									relatorioOrdemServicoAfericaoHidrometroBean.setReferecia3(Util
													.formatarAnoMesParaMesAno(leituraConsumoHelper.getAnoMesReferenciaMedicaoHistorico()));
								}

								if(leituraConsumoHelper.getLeituraAtualFaturamentoMedicaoHistorico() != null){
									relatorioOrdemServicoAfericaoHidrometroBean.setLeitura3(leituraConsumoHelper
													.getLeituraAtualFaturamentoMedicaoHistorico().toString());
								}

								if(leituraConsumoHelper.getNumeroConsumoFaturadoMesConsumoHistorico() != null){
									relatorioOrdemServicoAfericaoHidrometroBean.setConsumo3(leituraConsumoHelper
													.getNumeroConsumoFaturadoMesConsumoHistorico().toString());
								}

								if(leituraConsumoHelper.getDescricaoConsumoAnormalidade() != null){
									relatorioOrdemServicoAfericaoHidrometroBean.setAnormalidade3(leituraConsumoHelper
													.getDescricaoConsumoAnormalidade());
								}

								break;

							default:
								break;
						}

						i++;
					}

				}

				Date dataAtual = new Date();
				Integer anoMesVencimentoFinal = Util.formataAnoMes(dataAtual);
				int indicadorCalcularAcrescimosSucumbenciaAnterior = 2;

				SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

				ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper = getControladorCobranca().obterDebitoImovelOuCliente(
								ConstantesSistema.SIM.intValue(), dadosRelatorio.getIdImovel(), null, null, "190001",
								anoMesVencimentoFinal.toString(), Util.criarData(1, 1, 1900), dataAtual, ConstantesSistema.SIM.intValue(),
								ConstantesSistema.NAO.intValue(), ConstantesSistema.NAO.intValue(), ConstantesSistema.NAO.intValue(),
								ConstantesSistema.NAO.intValue(), ConstantesSistema.NAO.intValue(), ConstantesSistema.NAO.intValue(), true,
								sistemaParametro, null, null, null, ConstantesSistema.SIM, ConstantesSistema.SIM, ConstantesSistema.SIM,
								indicadorCalcularAcrescimosSucumbenciaAnterior, null);

				Integer qtdeDebitos = Integer.valueOf("0");
				BigDecimal valorDebitos = new BigDecimal("0.00");

				// Contas
				if(obterDebitoImovelOuClienteHelper.getColecaoContasValoresImovel() != null
								&& !obterDebitoImovelOuClienteHelper.getColecaoContasValoresImovel().isEmpty()){
					qtdeDebitos = qtdeDebitos + obterDebitoImovelOuClienteHelper.getColecaoContasValoresImovel().size();

					Iterator colecaoContaValoresIterator = obterDebitoImovelOuClienteHelper.getColecaoContasValoresImovel().iterator();

					while(colecaoContaValoresIterator.hasNext()){

						ContaValoresHelper contaValoresHelper = (ContaValoresHelper) colecaoContaValoresIterator.next();

						valorDebitos = valorDebitos.add(contaValoresHelper.getValorTotalConta());

					}
				}

				relatorioOrdemServicoAfericaoHidrometroBean.setQtdeDebitosVencidos(qtdeDebitos.toString());
				relatorioOrdemServicoAfericaoHidrometroBean.setValorDebitosVencidos(Util.formatarMoedaReal(valorDebitos));

				colecao.add(relatorioOrdemServicoAfericaoHidrometroBean);

			}catch(NumberFormatException e){
				e.printStackTrace();
				throw new GeradorRelatorioOrdemServicoException(e.getMessage(), e, e.getMessage());
			}catch(ControladorException e){
				e.printStackTrace();
				throw new GeradorRelatorioOrdemServicoException(e.getMessage(), e, e.getMessage());
			}
		}

		dadosRelatorio.setarBeansDetalheEstrutura(colecao);
	}

	@Override
	public void validarExibicaoRodape(DadosRelatorioOrdemServico dadosRelatorio) throws GeradorRelatorioOrdemServicoException{

		dadosRelatorio.setExibirCodigoBarras("false");

	}
}