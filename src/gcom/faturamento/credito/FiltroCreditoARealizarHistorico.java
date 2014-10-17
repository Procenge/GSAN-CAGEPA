package gcom.faturamento.credito;

import gcom.util.filtro.Filtro;


public class FiltroCreditoARealizarHistorico
				extends Filtro {

	public FiltroCreditoARealizarHistorico() {

	}

	public FiltroCreditoARealizarHistorico(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;

	}

	public final static String ID = "id";

	public final static String PAGAMENTO_HISTORICO = "pagamentoHistorico";

	public final static String IMOVE_ID = "imovel.id";

	public final static String CREDITO_ORIGEM_ID = "creditoOrigem.id";

	public final static String IC_USO_LIVRE_CREDITO_ORIGEM = "creditoOrigem.indicadorUsoLivre";

	public final static String ID_CREDITO_TIPO = "creditoTipo.id";

	public final static String CREDITO_TIPO = "creditoTipo";

	public final static String CREDITO_A_REALIZAR_GERAL = "creditoARealizarGeral";

}
