/**
 * 
 */

package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ordemservico.bean.RelatorioOrdemServicoReposicaoPavimentoBean;
import gcom.relatorio.ConstantesRelatorios;
import gcom.util.ConstantesSistema;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author isilva
 */
public class GeradorDadosRelatorioOrdemServicoReposicaoPavimento
				extends GeradorDadosRelatorioOrdemServicoEstrutura {

	private static String TITULO_TIPO_SERVICO = "REPOSICAO PAVIMENTO";

	/**
	 * Construtor padrão
	 */
	public GeradorDadosRelatorioOrdemServicoReposicaoPavimento() {

		super();
		addParametro(ConstantesSistema.NOME_SUBRELATORIO_ORDEM_SERVICO, ConstantesRelatorios.RELATORIO_REPOSICAO_PAVIMENTO);
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

		Collection<RelatorioOrdemServicoReposicaoPavimentoBean> colecao = new ArrayList<RelatorioOrdemServicoReposicaoPavimentoBean>();
		RelatorioOrdemServicoReposicaoPavimentoBean relatorioOrdemServicoReposicaoPavimentoBean = new RelatorioOrdemServicoReposicaoPavimentoBean();
		colecao.add(relatorioOrdemServicoReposicaoPavimentoBean);
		dadosRelatorio.setarBeansDetalheEstrutura(colecao);
	}

	@Override
	public void validarExibicaoRodape(DadosRelatorioOrdemServico dadosRelatorio) throws GeradorRelatorioOrdemServicoException{

		dadosRelatorio.setExibirCodigoBarras("false");
	}
}
