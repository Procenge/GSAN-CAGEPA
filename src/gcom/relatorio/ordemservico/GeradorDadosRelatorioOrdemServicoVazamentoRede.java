/**
 * 
 */

package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ordemservico.bean.RelatorioOrdemServicoVazamentoRedeBean;
import gcom.relatorio.ConstantesRelatorios;
import gcom.util.ConstantesSistema;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author isilva
 */
public class GeradorDadosRelatorioOrdemServicoVazamentoRede
				extends GeradorDadosRelatorioOrdemServicoEstrutura {

	private static String TITULO_TIPO_SERVICO = "VAZAMENTO DE REDE";

	/**
	 * Construtor padrão
	 */
	public GeradorDadosRelatorioOrdemServicoVazamentoRede() {

		super();
		addParametro(ConstantesSistema.NOME_SUBRELATORIO_ORDEM_SERVICO, ConstantesRelatorios.RELATORIO_ORDEM_SERVICO_VAZAMENTO_REDE);
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

		Collection<RelatorioOrdemServicoVazamentoRedeBean> colecao = new ArrayList<RelatorioOrdemServicoVazamentoRedeBean>();
		RelatorioOrdemServicoVazamentoRedeBean relatorioOrdemServicoVazamentoRedeBean = new RelatorioOrdemServicoVazamentoRedeBean();
		colecao.add(relatorioOrdemServicoVazamentoRedeBean);
		dadosRelatorio.setarBeansDetalheEstrutura(colecao);
	}

	@Override
	public void validarExibicaoRodape(DadosRelatorioOrdemServico dadosRelatorio) throws GeradorRelatorioOrdemServicoException{

		dadosRelatorio.setExibirCodigoBarras("false");
	}
}
