/**
 * 
 */

package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ordemservico.bean.DadosUltimoCorteHelper;
import gcom.atendimentopublico.ordemservico.bean.RelatorioOrdemServicoReligacaoMedicaoIndivBean;
import gcom.micromedicao.bean.HidrometroRelatorioOSHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author isilva
 */
public class GeradorDadosRelatorioOrdemServicoReligacaoMedicaoIndiv
				extends GeradorDadosRelatorioOrdemServicoEstrutura {

	private static String TITULO_TIPO_SERVICO = "ORDEM DE SERVIÇO";

	/**
	 * Construtor padrão
	 */
	public GeradorDadosRelatorioOrdemServicoReligacaoMedicaoIndiv() {

		super();
		addParametro(ConstantesSistema.NOME_SUBRELATORIO_ORDEM_SERVICO,
						ConstantesRelatorios.RELATORIO_ORDEM_SERVICO_RELIGACAO_MEDICAO_INDIV);
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

		Collection<RelatorioOrdemServicoReligacaoMedicaoIndivBean> colecao = new ArrayList<RelatorioOrdemServicoReligacaoMedicaoIndivBean>();

		if(dadosRelatorio.getIdImovel() != null){
			try{
				HidrometroRelatorioOSHelper hidrometroRelatorioOSHelper = getControladorMicromedicao().obterDadosHidrometroPorImovel(
								Integer.valueOf(dadosRelatorio.getIdImovel()));

				RelatorioOrdemServicoReligacaoMedicaoIndivBean relatorioOrdemServicoReligacaoMedicaoIndivBean = new RelatorioOrdemServicoReligacaoMedicaoIndivBean();

				if(hidrometroRelatorioOSHelper != null){
					relatorioOrdemServicoReligacaoMedicaoIndivBean.setCapacidadeHidrometro(hidrometroRelatorioOSHelper
									.getHidrometroCapacidade());
					relatorioOrdemServicoReligacaoMedicaoIndivBean.setDataInstalacaoHidrometro(hidrometroRelatorioOSHelper
									.getDataInstalacaoHidrometo());
					relatorioOrdemServicoReligacaoMedicaoIndivBean.setLocalInstalacaoHidrometro(hidrometroRelatorioOSHelper
									.getHidrometroLocal());
					relatorioOrdemServicoReligacaoMedicaoIndivBean.setMarcaHidrometro(hidrometroRelatorioOSHelper.getHidrometroMarca());
					relatorioOrdemServicoReligacaoMedicaoIndivBean.setNumeroHidrometro(hidrometroRelatorioOSHelper.getHidrometroNumero());

					if(!Util.isVazioOuBranco(hidrometroRelatorioOSHelper.getDescricaoTipoHidrometro())){
						relatorioOrdemServicoReligacaoMedicaoIndivBean.setTipoHidrometro(hidrometroRelatorioOSHelper.getIdTipoHidrometro()
										+ " - " + hidrometroRelatorioOSHelper.getDescricaoTipoHidrometro());
					}

					if(!Util.isVazioOuBranco(hidrometroRelatorioOSHelper.getDescricaoProtecaoHidrometro())){
						relatorioOrdemServicoReligacaoMedicaoIndivBean.setTipoProtecaoHidrometro(hidrometroRelatorioOSHelper
										.getIdProtecaoHidrometro()
										+ " - " + hidrometroRelatorioOSHelper.getDescricaoProtecaoHidrometro());
					}

					if(!Util.isVazioOuBranco(hidrometroRelatorioOSHelper.getHidrometroDiametro())){
						relatorioOrdemServicoReligacaoMedicaoIndivBean.setDiametroHidrometro(hidrometroRelatorioOSHelper
										.getHidrometroDiametro());
					}
				}

				DadosUltimoCorteHelper dadosUltimoCorteHelper = getControladorLigacaoAgua().obterDadosUltimoCorte(
								Integer.valueOf(dadosRelatorio.getIdImovel()));

				if(dadosUltimoCorteHelper != null){
					if(dadosUltimoCorteHelper.getDataCorte() == null){

						if(dadosUltimoCorteHelper.getDataCorteAdministrativo() != null){
							relatorioOrdemServicoReligacaoMedicaoIndivBean.setDataCorte(Util.formatarData(dadosUltimoCorteHelper
											.getDataCorteAdministrativo()));
						}

					}else{
						relatorioOrdemServicoReligacaoMedicaoIndivBean.setDataCorte(Util
										.formatarData(dadosUltimoCorteHelper.getDataCorte()));
					}

					if(dadosUltimoCorteHelper.getNumeroLeituraCorte() != null){
						relatorioOrdemServicoReligacaoMedicaoIndivBean.setNumeroLeituraCorte(dadosUltimoCorteHelper.getNumeroLeituraCorte()
										.toString());
					}

					if(dadosUltimoCorteHelper.getNumeroSeloCorte() != null){
						relatorioOrdemServicoReligacaoMedicaoIndivBean.setNumeroSeloCorte(dadosUltimoCorteHelper.getNumeroSeloCorte()
										.toString());
					}

					relatorioOrdemServicoReligacaoMedicaoIndivBean.setIdDescricaoCorteTipo(dadosUltimoCorteHelper.getIdDescricao());

				}

				colecao.add(relatorioOrdemServicoReligacaoMedicaoIndivBean);

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
