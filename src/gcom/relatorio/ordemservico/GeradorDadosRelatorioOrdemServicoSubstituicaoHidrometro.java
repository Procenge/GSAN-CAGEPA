/**
 * 
 */

package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ordemservico.bean.RelatorioOrdemServicoSubstituicaoHidrometroBean;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.micromedicao.bean.HidrometroRelatorioOSHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Geração dos dados do Relatório de Ordem de Serviço de Substituição de Hidrômetro
 * 
 * @author Anderson Italo
 * @date 24/05/2011
 */
public class GeradorDadosRelatorioOrdemServicoSubstituicaoHidrometro
				extends GeradorDadosRelatorioOrdemServicoEstrutura {

	private static String TITULO_TIPO_SERVICO = "SUBSTITUIÇÃO DE HIDRÔMETRO";

	/**
	 * Construtor padrão
	 */
	public GeradorDadosRelatorioOrdemServicoSubstituicaoHidrometro() {

		super();
		addParametro(ConstantesSistema.NOME_SUBRELATORIO_ORDEM_SERVICO,
						ConstantesRelatorios.RELATORIO_ORDEM_SERVICO_SUBSTITUICAO_HIDROMETRO);
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

		Collection<RelatorioOrdemServicoSubstituicaoHidrometroBean> colecao = new ArrayList<RelatorioOrdemServicoSubstituicaoHidrometroBean>();

		if(dadosRelatorio.getIdImovel() != null){
			try{

				RelatorioOrdemServicoSubstituicaoHidrometroBean bean = new RelatorioOrdemServicoSubstituicaoHidrometroBean();

				// Obter dados do Hidrômetro Anterior
				HidrometroRelatorioOSHelper hidrometroRelatorioOSHelper = getControladorMicromedicao().obterDadosHidrometroPorImovel(
								Integer.valueOf(dadosRelatorio.getIdImovel()));

				if(dadosRelatorio.getDataRoteiroProgramacao() != null){

					bean.setDataRoteiroProgramacao(dadosRelatorio.getDataRoteiroProgramacao());
				}
				if(dadosRelatorio.getNomeEquipe() != null && dadosRelatorio.getNomeEquipe() != ""){

					bean.setIdEquipe(dadosRelatorio.getIdEquipe() + "-" + dadosRelatorio.getNomeEquipe());

				}

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

					if(!Util.isVazioOuBranco(hidrometroRelatorioOSHelper.getIndicadorCavalete())){
						bean.setCavalete(hidrometroRelatorioOSHelper.getIndicadorCavalete());
					}
				}

				Object[] dadosLeituraConsumo = getControladorMicromedicao().pesquisarLeituraConsumoImovel(
								Integer.valueOf(dadosRelatorio.getIdImovel()));

				if(dadosLeituraConsumo != null && dadosLeituraConsumo.length > 0){

					if(!Util.isVazioOuBranco(dadosLeituraConsumo[0])){
						bean.setLeituraAtual(dadosLeituraConsumo[0].toString());
					}

					if(!Util.isVazioOuBranco(dadosLeituraConsumo[1])){
						bean.setDataLeituraAtual(dadosLeituraConsumo[1].toString());
					}

					if(!Util.isVazioOuBranco(dadosLeituraConsumo[2])){
						bean.setAnormalidadeLeituraAtual(dadosLeituraConsumo[2].toString());
					}

					if(!Util.isVazioOuBranco(dadosLeituraConsumo[3])){
						bean.setConsumoMedio(dadosLeituraConsumo[3].toString());
					}
				}

				SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

				if(SistemaParametro.INDICADOR_EMPRESA_DESO.toString().equals(sistemaParametro.getParmId().toString())){
					bean.setEmpresa(sistemaParametro.getNomeAbreviadoEmpresa());
				}else if(SistemaParametro.INDICADOR_EMPRESA_ADA.toString().equals(sistemaParametro.getParmId().toString())){
					bean.setEmpresa(sistemaParametro.getNomeAbreviadoEmpresa());
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
		dadosRelatorio.setExibirRodapePadrao("false");

	}
}
