
package gcom.cobranca;

import gcom.util.filtro.Filtro;

public class FiltroInfracaoPerfilDebitoTipo
				extends Filtro {

	private static final long serialVersionUID = 1L;

	public static String INFRACAO_PERFIL = "infracaoPerfil";

	public static String INFRACAO_PERFIL_ID = "infracaoPerfil.id";

	public static String INDICADOR_LANCAMENTO_ATIVO = "indicadorLancamentoAtivo";

	public static String NUMERO_FATOR_MULTIPLICADOR = "numeroFatorMultiplicador";

	public static String PORCENTAGEM_DESCONTO = "porcentagemDesconto";

	public static String DEBITO_TIPO = "debitoTipo";

	public static String DEBITO_TIPO_ID = "debitoTipo.id";

	public FiltroInfracaoPerfilDebitoTipo() {

	}

	public FiltroInfracaoPerfilDebitoTipo(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

}
