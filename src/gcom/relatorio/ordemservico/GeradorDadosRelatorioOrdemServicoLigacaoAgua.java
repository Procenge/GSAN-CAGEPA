/**
 * 
 */

package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ordemservico.bean.RelatorioOrdemServicoLigacaoAguaBean;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.micromedicao.bean.HidrometroRelatorioOSHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Geração dos dados do Relatório de Ordem de Serviço de Ligação de Água
 * 
 * @author Anderson Italo
 * @date 24/05/2011
 */
public class GeradorDadosRelatorioOrdemServicoLigacaoAgua
				extends GeradorDadosRelatorioOrdemServicoEstrutura {

	private static String TITULO_TIPO_SERVICO_AGUA = "LIGAÇÃO DE ÁGUA";

	private static String TITULO_TIPO_SERVICO_ESGOTO = "LIGAÇÃO DE ESGOTO";

	private static String TITULO_ESGOTO = "ESGOTO";

	/**
	 * Construtor padrão
	 */
	public GeradorDadosRelatorioOrdemServicoLigacaoAgua() {

		super();
		addParametro(ConstantesSistema.NOME_SUBRELATORIO_ORDEM_SERVICO, ConstantesRelatorios.RELATORIO_ORDEM_SERVICO_LIGACAO_AGUA);
		// addParametro(ConstantesSistema.TITULO_TIPO_SERVICO, TITULO_TIPO_SERVICO_AGUA);
	}

	/**
	 * Retorna uma coleção com os dados para o detalhe dos relatórios de acordo com o tipo de
	 * serviço
	 * 
	 * @author Anderson Italo
	 * @date 24/05/2011
	 * @throws GeradorRelatorioOrdemServicoException
	 */
	public void gerarDetalhes(DadosRelatorioOrdemServico dadosRelatorio) throws GeradorRelatorioOrdemServicoException{

		System.out.println(dadosRelatorio.getTipoLigacao());
		System.out.println(dadosRelatorio.getTipoLigacao2());

		if(dadosRelatorio.getEspecificacao().contains(TITULO_ESGOTO)){

			addParametro(ConstantesSistema.TITULO_TIPO_SERVICO, TITULO_TIPO_SERVICO_ESGOTO);

		}else{

			addParametro(ConstantesSistema.TITULO_TIPO_SERVICO, TITULO_TIPO_SERVICO_AGUA);

		}

		Collection<RelatorioOrdemServicoLigacaoAguaBean> colecao = new ArrayList<RelatorioOrdemServicoLigacaoAguaBean>();

		if(dadosRelatorio.getIdImovel() != null){
			try{

				RelatorioOrdemServicoLigacaoAguaBean bean = new RelatorioOrdemServicoLigacaoAguaBean();

				// Obter quantidade de ecônomia das categorias
				Imovel imovel = new Imovel(Integer.valueOf(dadosRelatorio.getIdImovel()));
				Collection colecaoCategoria = getControladorImovel().obterQuantidadeEconomiasCategoria(imovel);
				Iterator colecaoCategoriaIterator = colecaoCategoria.iterator();

				while(colecaoCategoriaIterator.hasNext()){

					Categoria categoria = (Categoria) colecaoCategoriaIterator.next();
					String quantidadeEconomias = categoria.getQuantidadeEconomiasCategoria().toString();

					if(categoria.getId().intValue() == Categoria.COMERCIAL_INT){
						bean.setQtdEconomiasComercial(quantidadeEconomias);
					}else if(categoria.getId().intValue() == Categoria.PUBLICO_INT){
						bean.setQtdEconomiasPublica(quantidadeEconomias);
					}else if(categoria.getId().intValue() == Categoria.RESIDENCIAL_INT){
						bean.setQtdEconomiasResidencial(quantidadeEconomias);
					}else if(categoria.getId().intValue() == Categoria.INDUSTRIAL_INT){
						bean.setQtdEconomiasIndustrial(quantidadeEconomias);
					}
				}

				// Obter dados complementares do Imóvel
				imovel = getControladorImovel().consultarImovelDadosCadastrais(Integer.valueOf(dadosRelatorio.getIdImovel()));

				if(imovel.getReservatorioVolumeFaixaSuperior() != null || imovel.getReservatorioVolumeFaixaInferior() != null){
					bean.setReservatorio("SIM");
				}else{
					bean.setReservatorio("NÃO");
				}

				if(imovel.getPiscinaVolumeFaixa() != null){
					bean.setPiscina("SIM");
				}else{
					bean.setPiscina("NÃO");
				}

				if(imovel.getFonteAbastecimento() != null){
					bean.setFonteAbastecimento(imovel.getFonteAbastecimento().getDescricao().toUpperCase());
				}

				if(imovel.getNumeroMorador() != null){
					bean.setQtdOcupantes(imovel.getNumeroMorador().toString());
				}

				if(imovel.getNumeroPontosUtilizacao() != null){
					bean.setQtdPontosUtilizados(imovel.getNumeroPontosUtilizacao().toString());
				}

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
