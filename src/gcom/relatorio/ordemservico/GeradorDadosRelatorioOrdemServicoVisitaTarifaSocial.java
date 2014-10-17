/**
 * 
 */

package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ordemservico.bean.RelatorioOrdemServicoVisitaTarifaSocialBean;
import gcom.relatorio.ConstantesRelatorios;
import gcom.util.ConstantesSistema;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Geração dos dados do Relatório de Ordem de Serviço de Visita Tarifa Social
 * 
 * @author Anderson Italo
 * @date 24/05/2011
 */
public class GeradorDadosRelatorioOrdemServicoVisitaTarifaSocial
				extends GeradorDadosRelatorioOrdemServicoEstrutura {

	private static String TITULO_TIPO_SERVICO = "VISITA TARIFA SOCIAL";

	/**
	 * Construtor padrão
	 */
	public GeradorDadosRelatorioOrdemServicoVisitaTarifaSocial() {

		super();
		addParametro(ConstantesSistema.NOME_SUBRELATORIO_ORDEM_SERVICO, ConstantesRelatorios.RELATORIO_ORDEM_SERVICO_VISITA_TARIFA_SOCIAL);
		addParametro(ConstantesSistema.TITULO_TIPO_SERVICO, TITULO_TIPO_SERVICO);
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

		Collection<RelatorioOrdemServicoVisitaTarifaSocialBean> colecao = new ArrayList<RelatorioOrdemServicoVisitaTarifaSocialBean>();
		RelatorioOrdemServicoVisitaTarifaSocialBean bean = new RelatorioOrdemServicoVisitaTarifaSocialBean();
		colecao.add(bean);
		dadosRelatorio.setarBeansDetalheEstrutura(colecao);
	}

	@Override
	public void validarExibicaoRodape(DadosRelatorioOrdemServico dadosRelatorio) throws GeradorRelatorioOrdemServicoException{

		dadosRelatorio.setExibirCodigoBarras("false");
	}
}
