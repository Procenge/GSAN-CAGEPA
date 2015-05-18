
package gcom.util.parametrizacao.ordemservico;

import gcom.util.parametrizacao.Parametro;

/**
 * Parametrização da Ordem de Serviço
 */
public class ParametroOrdemServico
				extends Parametro {

	public static final Parametro P_COD_TIPO_ARQUIVO_EMISSAO_OS_SELETIVA = new ParametroOrdemServico(
					"P_COD_TIPO_ARQUIVO_EMISSAO_OS_SELETIVA");

	public static final Parametro P_SIT_AGUA_PERMIT_OS_SELETIVA_MANUT_HIDROMETRO = new ParametroOrdemServico(
					"P_SIT_AGUA_PERMIT_OS_SELETIVA_MANUT_HIDROMETRO");

	public static final Parametro P_ORDENACAO_INSCRICAO = new ParametroOrdemServico("P_ORDENACAO_INSCRICAO");

	public static final Parametro P_LAYOUT_ROTEIRO_PROGRAMACAO = new ParametroOrdemServico("P_LAYOUT_ROTEIRO_PROGRAMACAO");

	public static final Parametro P_SIT_AGUA_PERMIT_OS_SELETIVA_CORTE = new ParametroOrdemServico("P_SIT_AGUA_PERMIT_OS_SELETIVA_CORTE");

	public static final Parametro P_SERVICO_TIPO_REPARO = new ParametroOrdemServico("P_SERVICO_TIPO_REPARO");

	private ParametroOrdemServico(String parametro) {

		super(parametro);
	}

}
