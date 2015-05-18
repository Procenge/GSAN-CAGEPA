package gcom.relatorio.ordemservico;

import gcom.atendimentopublico.ordemservico.bean.RelatorioOrdemServicoLigacaoAguaBean;
import gcom.relatorio.ConstantesRelatorios;
import gcom.util.ConstantesSistema;

import java.util.ArrayList;
import java.util.Collection;

public class GeradorDadosSubrelatorioOrdemServicoReparosAterroLimp
				extends GeradorDadosRelatorioOrdemServicoBaseComSubrelatorios {

	private static String TITULO_TIPO_SERVICO = "Reparos - Aterro / Limpeza";

	public GeradorDadosSubrelatorioOrdemServicoReparosAterroLimp() {

		super();
		addParametro(ConstantesSistema.NOME_SUBRELATORIO_ORDEM_SERVICO, ConstantesRelatorios.SUBRELATORIO_ORDEM_SERVICO_REPAROS_ATERRO_LIMP);
		addParametro(ConstantesSistema.TITULO_TIPO_SERVICO, TITULO_TIPO_SERVICO);
	}

	@Override
	public void gerarDetalhes(DadosRelatorioOrdemServico dadosRelatorio) throws GeradorRelatorioOrdemServicoException{

		Collection<RelatorioOrdemServicoLigacaoAguaBean> beans = new ArrayList<RelatorioOrdemServicoLigacaoAguaBean>();

		RelatorioOrdemServicoLigacaoAguaBean bean = new RelatorioOrdemServicoLigacaoAguaBean();
		beans.add(bean);

		dadosRelatorio.setarBeansDetalheEstrutura(beans);
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.relatorio.ordemservico.GeradorDadosRelatorioOrdemServicoBaseComSubrelatorios#
	 * validarExibicaoRodape(gcom.relatorio.ordemservico.DadosRelatorioOrdemServico)
	 */
	@Override
	public void validarExibicaoRodape(DadosRelatorioOrdemServico dadosRelatorio) throws GeradorRelatorioOrdemServicoException{

		dadosRelatorio.setExibirRodapePadrao("true");
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.relatorio.ordemservico.GeradorDadosRelatorioOrdemServicoBaseComSubrelatorios#
	 * validarTipoAssinaturaRodape(gcom.relatorio.ordemservico.DadosRelatorioOrdemServico)
	 */
	@Override
	public void validarTipoAssinaturaRodape(DadosRelatorioOrdemServico dadosRelatorio) throws GeradorRelatorioOrdemServicoException{

		dadosRelatorio.setTipoAssinaturaRodape(GeradorDadosRelatorioOrdemServicoBaseComSubrelatorios.TIPO_ASSINATURA_ENCARREGADO_MOTORISTA_CARROPLACA);
	}

}
