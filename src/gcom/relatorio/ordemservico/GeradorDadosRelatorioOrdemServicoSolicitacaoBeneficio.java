/**
 * 
 */

package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ordemservico.bean.RelatorioOrdemServicoSolicitacaoBeneficioBean;
import gcom.relatorio.ConstantesRelatorios;
import gcom.util.ConstantesSistema;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author isilva
 */
public class GeradorDadosRelatorioOrdemServicoSolicitacaoBeneficio
				extends GeradorDadosRelatorioOrdemServicoEstrutura {

	private static String TITULO_TIPO_SERVICO = "SOLICITAÇÃO DE BENEFÍCIO";

	/**
	 * Construtor padrão
	 */
	public GeradorDadosRelatorioOrdemServicoSolicitacaoBeneficio() {

		super();
		addParametro(ConstantesSistema.NOME_SUBRELATORIO_ORDEM_SERVICO, ConstantesRelatorios.RELATORIO_ORDEM_SERVICO_SOLICITACAO_BENEFICIO);
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

		Collection<RelatorioOrdemServicoSolicitacaoBeneficioBean> colecao = new ArrayList<RelatorioOrdemServicoSolicitacaoBeneficioBean>();
		RelatorioOrdemServicoSolicitacaoBeneficioBean relatorioOrdemServicoSolicitacaoBeneficioBean = new RelatorioOrdemServicoSolicitacaoBeneficioBean();
		colecao.add(relatorioOrdemServicoSolicitacaoBeneficioBean);
		dadosRelatorio.setarBeansDetalheEstrutura(colecao);
	}

	@Override
	public void validarExibicaoRodape(DadosRelatorioOrdemServico dadosRelatorio) throws GeradorRelatorioOrdemServicoException{

		dadosRelatorio.setExibirCodigoBarras("false");
	}
}
