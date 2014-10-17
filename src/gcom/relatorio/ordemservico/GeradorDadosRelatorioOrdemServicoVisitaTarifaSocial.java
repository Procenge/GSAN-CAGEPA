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
 * Gera��o dos dados do Relat�rio de Ordem de Servi�o de Visita Tarifa Social
 * 
 * @author Anderson Italo
 * @date 24/05/2011
 */
public class GeradorDadosRelatorioOrdemServicoVisitaTarifaSocial
				extends GeradorDadosRelatorioOrdemServicoEstrutura {

	private static String TITULO_TIPO_SERVICO = "VISITA TARIFA SOCIAL";

	/**
	 * Construtor padr�o
	 */
	public GeradorDadosRelatorioOrdemServicoVisitaTarifaSocial() {

		super();
		addParametro(ConstantesSistema.NOME_SUBRELATORIO_ORDEM_SERVICO, ConstantesRelatorios.RELATORIO_ORDEM_SERVICO_VISITA_TARIFA_SOCIAL);
		addParametro(ConstantesSistema.TITULO_TIPO_SERVICO, TITULO_TIPO_SERVICO);
	}

	/**
	 * Retorna uma cole��o com os dados para o detalhe dos relat�rios de acordo com o tipo de
	 * servi�o
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
